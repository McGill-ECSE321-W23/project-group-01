package ca.mcgill.ecse321.PLMS.service;

import ca.mcgill.ecse321.PLMS.model.Floor;
import ca.mcgill.ecse321.PLMS.model.ParkingLot;
import ca.mcgill.ecse321.PLMS.repository.FloorRepository;
import ca.mcgill.ecse321.PLMS.repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.PLMS.exception.PLMSException;
import ca.mcgill.ecse321.PLMS.model.GuestPass;
import ca.mcgill.ecse321.PLMS.repository.GuestPassRepository;

import jakarta.transaction.Transactional;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class GuestPassService {

    @Autowired
    GuestPassRepository guestPassRepository;

    @Autowired
    FloorRepository floorRepository;

    @Autowired
    ParkingLotRepository parkingLotRepository;

    /**
     * Service method to fetch all guest passes in the database
     */
    @Transactional
    public Iterable<GuestPass> getAllGuestPasses() {
        return guestPassRepository.findAll();
    }

    /**
     * Service method to fetch a guest pass with a specific guest pass id in the database
     */
    @Transactional
    public GuestPass getGuestPassById(int guestPassId) {
        GuestPass guestPass = guestPassRepository.findGuestPassById(guestPassId);
        if (guestPass == null) {
            throw new PLMSException(HttpStatus.NOT_FOUND, "Guest pass with id: " + guestPassId + " does not exist.");
        }
        return guestPass;
    }

    /**
     * Service method to store the created guest pass object into the database
     */
    @Transactional
    public GuestPass createGuestPass(GuestPass guestPass, int floorNumber, int nrIncrements) {
        //checks on the new object are made in the DTO
        //check if the guest pass already exists
        if (guestPassRepository.findGuestPassById(guestPass.getId()) != null) {
            throw new PLMSException(HttpStatus.BAD_REQUEST, "Guest pass with id: " + guestPass.getId() + " already exists.");
        }
        // Get the associated floor from floor number inputted into guestPass
        Floor floor = floorRepository.findFloorByFloorNumber(floorNumber);
        if (floor == null) {
            throw new PLMSException(HttpStatus.NOT_FOUND, "The floor with floor number " + floorNumber + " does not exist.");
        }
//        ParkingLot parkingLot = parkingLotRepository.findParkingLotById(floor.getParkingLot().getId());
        ParkingLot parkingLot = floor.getParkingLot();

        //Check if floor is a member only
        if (floor.getIsMemberOnly()) {
            throw new PLMSException(HttpStatus.BAD_REQUEST, "Floor " + floorNumber + " is reserved for monthly members");
        }

        // Extract start and end time
        LocalDateTime localDateTime = LocalDateTime.now();
        Time startTime = Time.valueOf(localDateTime.toLocalTime());
        LocalDateTime localEndTime = localDateTime.plusMinutes(nrIncrements*15);
        Time endTime = Time.valueOf(localEndTime.toLocalTime());
        validateGuestPassHours(startTime, endTime, parkingLot.getOpeningTime(), parkingLot.getClosingTime());

        // Check if the spot is reserved for less than 12 hours
        if (nrIncrements > 12*4){
            throw new PLMSException(HttpStatus.BAD_REQUEST, "Cannot reserve spot for more than 12 hours");
        }
        // Check if spot is not occupied
        if (!isSpotAvailable(floor, guestPass.getSpotNumber(),startTime, endTime)){
            throw new PLMSException(HttpStatus.BAD_REQUEST, "Spot " + guestPass.getSpotNumber() + " is currently occupied");
        }

        // set start and end time
        guestPass.setStartTime(startTime);
        guestPass.setEndTime(endTime);
        LocalDateTime localDate = LocalDateTime.now();
        guestPass.setDate((Date) Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()));
        // Set fee and increment floor counter
        if (guestPass.getIsLarge()){
            guestPass.setFee(nrIncrements*parkingLot.getLargeSpotFee());
            floor.setLargeSpotCounter(floor.getLargeSpotCounter() + 1);
        } else{
            guestPass.setFee(nrIncrements*parkingLot.getSmallSpotFee());
            floor.setSmallSpotCounter(floor.getSmallSpotCounter() + 1);
        }
        // Create object
        guestPass = guestPassRepository.save(guestPass);

        // Returned created object
        return guestPass;
    }

    /**
     * Service method that updates a guest pass object in the database
     */
    @Transactional
    public GuestPass updateGuestPass(GuestPass guestPass) {
        //check if the guest pass exists (the guest pass has to exist to edit it)
        GuestPass existingGuestPass = getGuestPassById(guestPass.getId());

        // update the properties of the existing GuestPass entity
        existingGuestPass.setStartTime(guestPass.getStartTime());
        existingGuestPass.setEndTime(guestPass.getEndTime());
        // save the changes to the database
        existingGuestPass = guestPassRepository.save(existingGuestPass);
        return existingGuestPass;
    }

    /**
     * Service method that deletes the guest pass with guest pass id guestPassId from the database
     */
    @Transactional
    public void deleteGuestPassById(int guestPassId) {
        //Checks for non null are made in the method already
        GuestPass guestPass = getGuestPassById(guestPassId);
        guestPassRepository.delete(guestPass);

    }

    public List<GuestPass> getGuestPassesByFloor(int floorNumber) {
        List<GuestPass> guestPasses = new ArrayList<GuestPass>();
        Floor floor = floorRepository.findFloorByFloorNumber(floorNumber);
        if (floor == null) {
            throw new PLMSException(HttpStatus.NOT_FOUND, "The floor with floor number " + floorNumber + " does not exist.");
        }
        for (GuestPass pass : guestPassRepository.findAll()) {
            if (pass.getFloor() != null && pass.getFloor().equals(floor)) {
                guestPasses.add(pass);
            }
        }
        if (guestPasses.size() == 0) {
            throw new PLMSException(HttpStatus.NOT_FOUND, "There are no guest passes on floor " + floorNumber);
        }
        return guestPasses;
    }


    public List<GuestPass> getGuestPassesByDate(Date date) {
        List<GuestPass> guestPasses = (List<GuestPass>) guestPassRepository.findAll();
        List<GuestPass> guestPassesByDate = new ArrayList<>();
        for (GuestPass guestPass : guestPasses) {
            if (guestPass.getDate().equals(date)) {
                guestPassesByDate.add(guestPass);
            }
        }
        if (guestPassesByDate.isEmpty()) {
            // null means guestPasses don't exist for that date, throw PLMS error
            throw new PLMSException(HttpStatus.NOT_FOUND, "There are no guest passes for date " + date);
        }
        return guestPassesByDate;
    }


    public void validateGuestPassHours(Time startTime, Time endTime, Time openingTime, Time closingTime) {

        // check to see if start time is before the opening time
        int comparisonResult1 = startTime.compareTo(openingTime);
        if (comparisonResult1 < 0) {
            throw new PLMSException(HttpStatus.BAD_REQUEST, "Cannot have an guest pass beginning before the lot opens.");
        }

        // check to see if the start time is after the lot closes
        int comparisonResult2 = startTime.compareTo(closingTime);
        if (comparisonResult2 > 0) {
            throw new PLMSException(HttpStatus.BAD_REQUEST, "Cannot have a guest pass beginning after the lot closes.");
        }

        // check to see if the end time is after the lot closes
        int comparisonResult3 = endTime.compareTo(closingTime);
        if (comparisonResult3 > 0) {
            throw new PLMSException(HttpStatus.BAD_REQUEST, "Cannot have a guest pass ending after the lot closes.");
        }

    }

    public boolean isSpotAvailable(Floor floor, String spotNumber, Time startTime, Time endTime) {
        List<GuestPass> occupiedGuestPasses = guestPassRepository.findByFloorAndSpotNumberAndTimePeriod(floor.getFloorNumber(), spotNumber, startTime, endTime);
        return occupiedGuestPasses.isEmpty();
    }
}