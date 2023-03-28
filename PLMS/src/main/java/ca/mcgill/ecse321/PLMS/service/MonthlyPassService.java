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
import java.time.LocalDate;
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
        ArrayList<MonthlyPass> arrayList = (ArrayList<MonthlyPass>) monthlyPassRepository.findAll();
        if (arrayList.isEmpty()){
            throw new PLMSException(HttpStatus.NOT_FOUND, "There are no monthly passes in the system.");
        }
        return arrayList;
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
        LocalDate localStartDate = LocalDate.parse(startDate.toString());
        // Add months to LocalStartDate
        LocalDate localEndDate = localStartDate.plusMonths(nrMonths);
        // Convert back to Date
        Date endDate = Date.valueOf(localEndDate);
        monthlyPass.setEndDate(endDate);

        // Check if the spot is not occupied
        if (isSpotOccupied(floor.getFloorNumber(), monthlyPass.getSpotNumber(), monthlyPass.getStartDate(), endDate)) {
            throw new PLMSException(HttpStatus.BAD_REQUEST, "Spot " + monthlyPass.getSpotNumber() + " is currently occupied");
        }

        // check to see if we've exceed the floor capacity by booking this spot.
        if(hasExceededCapacity(localStartDate, localEndDate ,floorNumber, monthlyPass.getIsLarge())){
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
    public boolean hasExceededCapacity(LocalDate newPassStartDate, LocalDate newPassEndDate, int floorNumber, boolean isLarge){
        // get all the passes
        ArrayList<GuestPass> guestPasses = (ArrayList<GuestPass>) guestPassRepository.findAll();
        ArrayList<MonthlyPass> monthlyPasses = (ArrayList<MonthlyPass>) monthlyPassRepository.findAll();
        // number of passes on the floor
        int numberOfPasses = 0;
        // filter through the guest passes to find passes that are of the same size and same floor number
        for (GuestPass pass : guestPasses){
            if(pass.getFloor().getFloorNumber() == floorNumber && pass.getIsLarge() == isLarge && isActiveRightNowGuestPass(newPassStartDate, newPassEndDate,pass.getDate().toLocalDate())){

                numberOfPasses += 1;
            }
        }

        for (MonthlyPass pass : monthlyPasses){
            if(pass.getFloor().getFloorNumber() == floorNumber && pass.getIsLarge() == isLarge && isActiveRightNowMonthlyPass(newPassStartDate, newPassEndDate, pass.getStartDate().toLocalDate(), pass.getEndDate().toLocalDate())){

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
     * Checks to see overlap between the newly created monthly pass
     * and any guest pass.
     * @param newPassStartDate
     * @param newPassEndDate
     * @param guestPassDate
     * @return - true if there is time overlap
     */
    public boolean isActiveRightNowGuestPass(LocalDate newPassStartDate, LocalDate newPassEndDate, LocalDate guestPassDate) {
        return guestPassDate.isBefore(newPassEndDate) && newPassEndDate.isAfter(newPassStartDate);
    }

    /**
     * Checks to see overlap between two monthly passes
     * @param newPassStartDate
     * @param newPassEndDate
     * @param otherPassStartDate
     * @param otherPassEndDate
     * @return
     */
    public boolean isActiveRightNowMonthlyPass(LocalDate newPassStartDate, LocalDate newPassEndDate,LocalDate otherPassStartDate, LocalDate otherPassEndDate) {
        return (newPassStartDate.isBefore(otherPassEndDate) && newPassEndDate.isAfter(otherPassStartDate)) || (otherPassStartDate.isBefore(newPassEndDate) && otherPassEndDate.isAfter(newPassStartDate));
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

    @Transactional
    /**
     * Return all of the monthly passes that are active on a given date.
     * @param date - date we want to search for
     * @return - all passes active on that date
     */
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
        try {
            List<MonthlyPass> monthlyPasses = getMonthlyPassesByFloor(floorNumber); // get all monthly passes for the floor
            for (MonthlyPass monthlyPass : monthlyPasses) {
                if (monthlyPass.getSpotNumber().equals(spotNumber)) { // check if spot number matches
                    Date passStartDate = monthlyPass.getStartDate();
                    Date passEndDate = monthlyPass.getEndDate();
                    if (passStartDate.before(endDate) && passEndDate.after(startDate)) {
                        // monthly pass overlaps with the specified date range
                        return true;
                    }
                }
            }
            return false; // no overlapping monthly passes found

        } catch (PLMSException e) { // In case no guest passes existed prior
            return false;
        }

    }

}

