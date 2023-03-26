package ca.mcgill.ecse321.PLMS.service;

import ca.mcgill.ecse321.PLMS.exception.PLMSException;
import ca.mcgill.ecse321.PLMS.model.*;
import ca.mcgill.ecse321.PLMS.repository.FloorRepository;
import ca.mcgill.ecse321.PLMS.repository.GuestPassRepository;
import ca.mcgill.ecse321.PLMS.repository.MonthlyCustomerRepository;
import ca.mcgill.ecse321.PLMS.repository.MonthlyPassRepository;
import ca.mcgill.ecse321.PLMS.repository.ParkingLotRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class MonthlyPassService {

    @Autowired
    MonthlyPassRepository monthlyPassRepository;

    @Autowired
    GuestPassRepository guestPassRepository;

    @Autowired
    FloorRepository floorRepository;

    @Autowired
    ParkingLotRepository parkingLotRepository;

    @Autowired
    MonthlyCustomerRepository monthlyCustomerRepository;

    /**
     * Service method to fetch all monthly passes in the database
     */
    @Transactional
    public Iterable<MonthlyPass> getAllMonthlyPasses() {
        return monthlyPassRepository.findAll();
    }

    /**
     * Service method to fetch a monthly pass with a specific id in the database
     */
    @Transactional
    public MonthlyPass getMonthlyPassById(int monthlyPassId) {
        MonthlyPass monthlyPass = monthlyPassRepository.findMonthlyPassById(monthlyPassId);
        if (monthlyPass == null) {
            throw new PLMSException(HttpStatus.NOT_FOUND, "Monthly pass with id: " + monthlyPassId + " does not exist.");
        }
        return monthlyPass;
    }

    /**
     * Service method to store the created monthly pass object into the database
     */
    @Transactional
    public MonthlyPass createMonthlyPass(MonthlyPass monthlyPass, int floorNumber, int nrMonths) {
        // Get the associated floor from floor number inputted into monthly pass
        Floor floor = floorRepository.findFloorByFloorNumber(floorNumber);
        if (floor == null) {
            throw new PLMSException(HttpStatus.NOT_FOUND, "The floor with floor number " + floorNumber + " does not exist.");
        }
        ParkingLot parkingLot = floor.getParkingLot();

        // Check if floor is a guest only
        if (!floor.getIsMemberOnly()) {
            throw new PLMSException(HttpStatus.BAD_REQUEST, "Floor " + floorNumber + " is reserved for guest passes only.");
        }

        // Get start date and find end date
        Date startDate = monthlyPass.getStartDate();
        LocalDate localStartDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        // Add months to LocalStartDate
        LocalDate LocalEndDate = localStartDate.plusMonths(nrMonths);
        // Convert back to Date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Date endDate = Date.valueOf(formatter.format(LocalEndDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        monthlyPass.setEndDate(endDate);

        // Check if the spot is not occupied
        if (isSpotOccupied(floor.getFloorNumber(), monthlyPass.getSpotNumber(), monthlyPass.getStartDate(), endDate)) {
            throw new PLMSException(HttpStatus.BAD_REQUEST, "Spot " + monthlyPass.getSpotNumber() + " is currently occupied");
        }

        // check to see if the capacity has been reached
        if(hasExceededCapacity(floorNumber, monthlyPass.getIsLarge())){
            throw new PLMSException(HttpStatus.BAD_REQUEST, "All spots of this size on floor " + floorNumber +" are occupied.");
        }


        // Set fee and increment floor counter
        if (monthlyPass.getIsLarge()) {
            monthlyPass.setFee(parkingLot.getLargeSpotFee() * nrMonths);
        } else {
            monthlyPass.setFee(parkingLot.getSmallSpotFee() * nrMonths);
        }
        // Create object
        monthlyPass = monthlyPassRepository.save(monthlyPass);

        // Returned created object
        return monthlyPass;
    }

    /**
     * Helper method to check if we have exceeded capacity on a floor based
     * on the number of guest passes and monthly passes on that floor
     * @param floorNumber - number of the floor
     * @param isLarge - size of the spot
     * @return - true if we've reached capacity for those spots, false otherwise
     */
    public boolean hasExceededCapacity(int floorNumber, boolean isLarge){
        // get all the passes
        ArrayList<GuestPass> guestPasses = (ArrayList<GuestPass>) guestPassRepository.findAll();
        ArrayList<MonthlyPass> monthlyPasses = (ArrayList<MonthlyPass>) monthlyPassRepository.findAll();
        // number of passes on the floor
        int numberOfPasses = 0;
        // filter through the guest passes to find passes that are of the same size and same floor number
        for (GuestPass pass : guestPasses){
            if(pass.getFloor().getFloorNumber() == floorNumber && pass.getIsLarge() == isLarge && isActiveRightNowGuestPass(pass.getDate().toLocalDate(), pass.getStartTime().toLocalTime(), pass.getEndTime().toLocalTime())){

                numberOfPasses += 1;
            }
        }

        for (MonthlyPass pass : monthlyPasses){
            if(pass.getFloor().getFloorNumber() == floorNumber && pass.getIsLarge() == isLarge && isActiveRightNowMonthlyPass(pass.getStartDate().toLocalDate(), pass.getEndDate().toLocalDate())){

                numberOfPasses += 1;
            }
        }

        Floor floor = floorRepository.findFloorByFloorNumber(floorNumber);
        if(isLarge){
            return numberOfPasses >= floor.getLargeSpotCapacity();
        }else{
            return numberOfPasses >= floor.getSmallSpotCapacity();
        }
    }

    /**
     * Function for checking if a pass is active right at this point in time, given the start and end times
     * and date.
     * @param date - date of the pass
     * @param startTime - start time of the pass
     * @param endTime - end time of the pass
     * @return true if the pass is currently active
     */
    public boolean isActiveRightNowGuestPass(LocalDate date, LocalTime startTime, LocalTime endTime) {
        LocalDateTime start = LocalDateTime.of(date, startTime);
        LocalDateTime end = LocalDateTime.of(date, endTime);
        LocalDateTime now = LocalDateTime.now();
        
        return now.isAfter(start) && now.isBefore(end);
    }

    /**
     * Function for checking if a pass is active right at this point in time, given the start and end times
     * and date.
     * @param startDate - start date of the pass
     * @param endDate - end date of the pass
     * @return true if the pass is currently active
     */
    public boolean isActiveRightNowMonthlyPass(LocalDate startDate, LocalDate endDate) {
        LocalDate now = LocalDate.now();
        
        return now.isAfter(startDate) && now.isBefore(endDate);
    }


    public List<MonthlyPass> getMonthlyPassesByFloor(int floorNumber) {
        List<MonthlyPass> monthlyPasses = new ArrayList<>();
        Floor floor = floorRepository.findFloorByFloorNumber(floorNumber);
        if (floor == null) {
            throw new PLMSException(HttpStatus.NOT_FOUND, "The floor with floor number " + floorNumber + " does not exist.");
        }
        for (MonthlyPass pass : monthlyPassRepository.findAll()) {
            if (pass.getFloor() != null && pass.getFloor().equals(floor)) {
                monthlyPasses.add(pass);
            }
        }
        if (monthlyPasses.size() == 0) {
            throw new PLMSException(HttpStatus.NOT_FOUND, "There are no monthly passes on floor " + floorNumber);
        }
        return monthlyPasses;
    }
    /**
     * Service method that deletes the guest pass with guest pass id guestPassId from the database
     */
    @Transactional
    public void deleteMonthlyPassById(int guestPassId) {
        //Checks for non null are made in the method already
        MonthlyPass monthlyPass = getMonthlyPassById(guestPassId);
        monthlyPassRepository.delete(monthlyPass);

    }
    @Transactional
    public List<MonthlyPass> getMonthlyPassesByMonthlyCustomer(String email) {
        List<MonthlyPass> monthlyPassesbyMonthlyCustomer  = new ArrayList<>();
        List<MonthlyPass> monthlyPasses = (List<MonthlyPass>) monthlyPassRepository.findAll();

        MonthlyCustomer monthlyCustomer = monthlyCustomerRepository.findMonthlyCustomerByEmail(email);

        // Check if monthly customer exists
        if (monthlyCustomer == null) {
            throw new PLMSException(HttpStatus.NOT_FOUND, "The account with email " + email + " does not exist.");
        }

        // Loop through all monthly passes in the system
        for (MonthlyPass monthlyPass : monthlyPasses) {
            // Check if the monthly pass belongs to the monthly customer
            if (monthlyPass.getCustomer().equals(monthlyCustomer)) {
                monthlyPassesbyMonthlyCustomer.add(monthlyPass);
            }
        }
        if (monthlyPassesbyMonthlyCustomer.isEmpty()) {
            // null means monthlyPasses don't exist for that date, throw PLMS error
            throw new PLMSException(HttpStatus.NOT_FOUND, "There are no monthly passes for " + monthlyCustomer.getEmail());
        }

        return monthlyPassesbyMonthlyCustomer;
    }

    public List<MonthlyPass> getMonthlyPassesByDate(Date date) {
        List<MonthlyPass> monthlyPasses = (List<MonthlyPass>) monthlyPassRepository.findAll();
        List<MonthlyPass> monthlyPassesByDate = new ArrayList<>();
        for (MonthlyPass monthlyPass : monthlyPasses) {
            if (monthlyPass.getStartDate().compareTo(date) <= 0 && monthlyPass.getEndDate().compareTo(date) >= 0) {
                monthlyPassesByDate.add(monthlyPass);
            }
        }
        if (monthlyPassesByDate.isEmpty()) {
            // null means monthlyPasses don't exist for that date, throw PLMS error
            throw new PLMSException(HttpStatus.NOT_FOUND, "There are no monthly passes for date " + date);
        }
        return monthlyPassesByDate;
    }

    public boolean isSpotOccupied(int floorNumber, String spotNumber, Date startDate, Date endDate) {
        List<MonthlyPass> monthlyPasses = getMonthlyPassesByFloor(floorNumber); // get all monthly passes for the floor
        for (MonthlyPass monthlyPass : monthlyPasses) {
            if (monthlyPass.getSpotNumber().equals(spotNumber)){ // check if spot number matches
                Date passStartDate = monthlyPass.getStartDate();
                Date passEndDate = monthlyPass.getEndDate();
                if (passStartDate.before(endDate) && passEndDate.after(startDate)) {
                    // monthly pass overlaps with the specified date range
                    return true;
                }
            }
        }
        return false; // no overlapping monthly passes found
    }

}

