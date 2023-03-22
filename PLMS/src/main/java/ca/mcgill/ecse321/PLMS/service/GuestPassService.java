package ca.mcgill.ecse321.PLMS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.PLMS.exception.PLMSException;
import ca.mcgill.ecse321.PLMS.model.GuestPass;
import ca.mcgill.ecse321.PLMS.repository.GuestPassRepository;

import jakarta.transaction.Transactional;

@Service
public class GuestPassService {

    @Autowired
    GuestPassRepository guestPassRepository;


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
    public GuestPass getGuestPassByGuestPassId(int guestPassId){
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
        GuestPass existingGuestPass = getGuestPassByGuestPassId(guestPass.getId());

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
        GuestPass guestPass = getGuestPassByGuestPassId(guestPassId);
        guestPassRepository.delete(guestPass);

    }

}
