package ca.mcgill.ecse321.PLMS.service;

import ca.mcgill.ecse321.PLMS.exception.PLMSException;
import ca.mcgill.ecse321.PLMS.model.*;
import ca.mcgill.ecse321.PLMS.repository.FloorRepository;
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
import java.util.ArrayList;
import java.util.List;

@Service
public class MonthlyPassService {

    @Autowired
    MonthlyPassRepository monthlyPassRepository;

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
        // Check if the monthly pass already exists
        if (monthlyPassRepository.findMonthlyPassById(monthlyPass.getId()) != null) {
            throw new PLMSException(HttpStatus.BAD_REQUEST, "Monthly pass with id: " + monthlyPass.getId() + " already exists.");
        }
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
//        LocalDate localStartDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localStartDate = LocalDate.parse(startDate.toString());
        // Add months to LocalStartDate
        LocalDate localEndDate = localStartDate.plusMonths(nrMonths);
        // Convert back to Date
//        Date endDate = (Date) Date.from(LocalEndDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.valueOf(localEndDate);
        monthlyPass.setEndDate(endDate);

        // Check if the spot is not occupied
        if (isSpotOccupied(floor.getFloorNumber(), monthlyPass.getSpotNumber(), monthlyPass.getStartDate(), endDate)) {
            throw new PLMSException(HttpStatus.BAD_REQUEST, "Spot " + monthlyPass.getSpotNumber() + " is currently occupied");
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

