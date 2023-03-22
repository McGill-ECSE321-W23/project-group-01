package ca.mcgill.ecse321.PLMS.service;

import ca.mcgill.ecse321.PLMS.model.Floor;
import ca.mcgill.ecse321.PLMS.repository.FloorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.PLMS.exception.PLMSException;
import ca.mcgill.ecse321.PLMS.model.GuestPass;
import ca.mcgill.ecse321.PLMS.repository.GuestPassRepository;

import jakarta.transaction.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class GuestPassService {

    @Autowired
    GuestPassRepository guestPassRepository;

    @Autowired
    FloorRepository floorRepository;

    /**
     * Service method to fetch all guest passes in the database
     */
    @Transactional
    public Iterable<GuestPass> getAllGuestPasses(){
        return guestPassRepository.findAll();
    }

    /**
     * Service method to fetch a guest pass with a specific guest pass id in the database
     */
    @Transactional
    public GuestPass getGuestPassById(int guestPassId){
        GuestPass guestPass = guestPassRepository.findGuestPassById(guestPassId);
        if (guestPass == null){
            throw new PLMSException(HttpStatus.NOT_FOUND, "Guest pass with id: " + guestPassId + " does not exist.");
        }
        return guestPass;
    }

    /**
     * Service method to store the created guest pass object into the database
     */
    @Transactional
    public GuestPass createGuestPass(GuestPass guestPass){
        //checks on the new object are made in the DTO
        //check if the guest pass already exists
        if (guestPassRepository.findGuestPassById(guestPass.getId()) != null){
            throw new PLMSException(HttpStatus.BAD_REQUEST, "Guest pass with id: " + guestPass.getId() + " already exists.");
        }

        //create object
        guestPass = guestPassRepository.save(guestPass);
        //returned created object
        return guestPass;
    }

    /**
     * Service method that updates a guest pass object in the database
     */
    @Transactional
    public GuestPass updateGuestPass(GuestPass guestPass){
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
    public void deleteGuestPassByGuestPassId(int guestPassId) {
        //Checks for non null are made in the method already
        GuestPass guestPass = getGuestPassById(guestPassId);
        guestPassRepository.delete(guestPass);

    }

    public List<GuestPass> getGuestPassesByFloor(int floorNumber) {
        List<GuestPass> guestPasses = new ArrayList<GuestPass>();
        Floor floor = floorRepository.findFloorByFloorNumber(floorNumber);
        for (GuestPass pass : guestPassRepository.findAll()) {
            if (pass.getFloor() != null && pass.getFloor().equals(floor)) {
                guestPasses.add(pass);
            }
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
        if (guestPassesByDate.isEmpty()){
            // null means guestPasses don't exist for that date, throw PLMS error
            throw new PLMSException(HttpStatus.NOT_FOUND, "There are no guest passes for date " + date);
        }
        return guestPassesByDate;
    }
}
