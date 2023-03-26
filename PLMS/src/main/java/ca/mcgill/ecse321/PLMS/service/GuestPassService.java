package ca.mcgill.ecse321.PLMS.service;

import ca.mcgill.ecse321.PLMS.model.Floor;
import ca.mcgill.ecse321.PLMS.model.ParkingLot;
import ca.mcgill.ecse321.PLMS.repository.FloorRepository;
import ca.mcgill.ecse321.PLMS.repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.PLMS.exception.PLMSException;
import ca.mcgill.ecse321.PLMS.model.GuestPass;
import ca.mcgill.ecse321.PLMS.model.MonthlyPass;
import ca.mcgill.ecse321.PLMS.repository.GuestPassRepository;
import ca.mcgill.ecse321.PLMS.repository.MonthlyPassRepository;
import jakarta.transaction.Transactional;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
public class GuestPassService {

    @Autowired
    GuestPassRepository guestPassRepository;

    @Autowired
    MonthlyPassRepository monthlyPassRepository;

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
    public GuestPass createGuestPass(GuestPass guestPass, int floorNumber, int nrIncrements, LocalDateTime currentTime) {
        //checks on the new object are made in the DTO
        // Get the associated floor from floor number inputted into guestPass
        Floor floor = floorRepository.findFloorByFloorNumber(floorNumber);
        if (floor == null) {
            throw new PLMSException(HttpStatus.NOT_FOUND, "The floor with floor number " + floorNumber + " does not exist.");

        }
        guestPass.setFloor(floor);
        ParkingLot parkingLot = floor.getParkingLot();

        //Check if floor is a member only
        if (floor.getIsMemberOnly()) {
            throw new PLMSException(HttpStatus.BAD_REQUEST, "Floor " + floorNumber + " is reserved for monthly members");
        }
        // Check if the spot is reserved for less than 12 hours
        if (nrIncrements > 12*4){
            throw new PLMSException(HttpStatus.BAD_REQUEST, "Cannot reserve spot for more than 12 hours");
        }

        // Extract start and end time
        LocalDateTime localDateTime = currentTime;
        Time startTime = Time.valueOf(localDateTime.toLocalTime());
        LocalDateTime localEndTime = localDateTime.plusMinutes(nrIncrements*15);
        Time endTime = Time.valueOf(localEndTime.toLocalTime());
        validateGuestPassHours(startTime, endTime, parkingLot.getOpeningTime(), parkingLot.getClosingTime());

        // Check if spot is not occupied
        if (isSpotOccupied(floor.getFloorNumber(), guestPass.getSpotNumber(),startTime, endTime)){
            throw new PLMSException(HttpStatus.BAD_REQUEST, "Spot " + guestPass.getSpotNumber() + " is currently occupied");
        }

        // set start and end time
        guestPass.setStartTime(startTime);
        guestPass.setEndTime(endTime);
        LocalDateTime localDate = LocalDateTime.now();
        guestPass.setDate(Date.valueOf(localDateTime.toLocalDate()));

        // check to see if we've exceed the floor capacity by booking this spot.
        if(hasExceededCapacity(currentTime, localEndTime ,floorNumber, guestPass.getIsLarge())){
            throw new PLMSException(HttpStatus.BAD_REQUEST, "All spots of this size on floor " + floorNumber +" are occupied.");
        }

        // Set fee
        if (guestPass.getIsLarge()){
            guestPass.setFee(nrIncrements*parkingLot.getLargeSpotFee());
        } else{
            guestPass.setFee(nrIncrements*parkingLot.getSmallSpotFee());

        }
        // Create object

        guestPass =  guestPassRepository.save(guestPass);


        // Returned created object
        return guestPass;
    }

    /**
     * Helper method to check if we have exceeded capacity on a floor based
     * on the number of guest passes and monthly passes on that floor
     * @param floorNumber - number of the floor
     * @param isLarge - size of the spot
     * @return - true if we've reached capacity for those spots, false otherwise
     */
    public boolean hasExceededCapacity(LocalDateTime currentTime, LocalDateTime endTime, int floorNumber, boolean isLarge){
        // get all the passes
        ArrayList<GuestPass> guestPasses = (ArrayList<GuestPass>) guestPassRepository.findAll();
        ArrayList<MonthlyPass> monthlyPasses = (ArrayList<MonthlyPass>) monthlyPassRepository.findAll();
        // number of passes on the floor
        int numberOfPasses = 0;
        // filter through the guest passes to find passes that are of the same size and same floor number
        for (GuestPass pass : guestPasses){
            if(pass.getFloor().getFloorNumber() == floorNumber && pass.getIsLarge() == isLarge && isActiveRightNowGuestPass(currentTime, endTime,pass.getDate().toLocalDate(), pass.getStartTime().toLocalTime(), pass.getEndTime().toLocalTime())){

                numberOfPasses += 1;
            }
        }

        for (MonthlyPass pass : monthlyPasses){
            if(pass.getFloor().getFloorNumber() == floorNumber && pass.getIsLarge() == isLarge && isActiveRightNowMonthlyPass(currentTime, pass.getStartDate().toLocalDate(), pass.getEndDate().toLocalDate())){

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
    public boolean isActiveRightNowGuestPass(LocalDateTime currentTime, LocalDateTime guestPassEndTime, LocalDate date, LocalTime startTime, LocalTime endTime) {
        LocalDateTime start = LocalDateTime.of(date, startTime);
        LocalDateTime end = LocalDateTime.of(date, endTime);
        return currentTime.isBefore(end) && guestPassEndTime.isAfter(start);
//        return (currentTime.isEqual(start) || currentTime.isAfter(start)) && (currentTime.isEqual(end)|| currentTime.isBefore(end)) ;
    }

    /**
     * Function for checking if a pass is active right at this point in time, given the start and end times
     * and date.
     *
     * @param startDate - start date of the pass
     * @param endDate - end date of the pass
     * @return true if the pass is currently active
     */
    public boolean isActiveRightNowMonthlyPass(LocalDateTime currentTime,LocalDate startDate, LocalDate endDate) {
        LocalDate currentDate = currentTime.toLocalDate();
        return (currentDate.isEqual(startDate) || currentDate.isAfter(startDate)) && (currentDate.isEqual(endDate)|| currentDate.isBefore(endDate)) ;
    }

    /**

     * Service method that updates a guest pass object in the database
     */
//    @Transactional
//    public GuestPass updateGuestPass(GuestPass guestPass) {
//        //check if the guest pass exists (the guest pass has to exist to edit it)
//        GuestPass existingGuestPass = getGuestPassById(guestPass.getId());
//
//        // update the properties of the existing GuestPass entity
//        existingGuestPass.setStartTime(guestPass.getStartTime());
//        existingGuestPass.setEndTime(guestPass.getEndTime());
//        // save the changes to the database
//        existingGuestPass = guestPassRepository.save(existingGuestPass);
//        return existingGuestPass;
//    }

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
            throw new PLMSException(HttpStatus.BAD_REQUEST, "Cannot have a guest pass beginning before the lot opens.");
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


    public boolean isSpotOccupied(int floorNumber, String spotNumber, Time startTime, Time endTime) {
        try {
            List<GuestPass> guestPasses = getGuestPassesByFloor(floorNumber); // get all guest passes for the floor
            for (GuestPass guestPass : guestPasses) {
                if (guestPass.getSpotNumber().equals(spotNumber)) { // check if spot number matches
                    Time guestPassStartTime = guestPass.getStartTime();
                    Time guestPassEndTime = guestPass.getEndTime();
                    if ((guestPassStartTime.before(endTime) && guestPassEndTime.after(startTime))) {

                        // guest pass overlaps with the specified time range
                        return true;
                    }
                }
            }
            return false; // guest pass not found

        } catch (PLMSException e){ // In case no guest passes existed prior
            return false;
        }
    }
}