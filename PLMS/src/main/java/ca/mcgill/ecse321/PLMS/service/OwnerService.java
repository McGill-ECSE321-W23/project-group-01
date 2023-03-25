package ca.mcgill.ecse321.PLMS.service;

import ca.mcgill.ecse321.PLMS.model.MonthlyCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.PLMS.exception.PLMSException;
import ca.mcgill.ecse321.PLMS.model.Owner;
import ca.mcgill.ecse321.PLMS.repository.OwnerRepository;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Service class for the Account model objects in the database
 * @author Karl Bridi Soft. Eng. student
 */
@Service
public class OwnerService {

    @Autowired
    OwnerRepository ownerRepository;

    /**
     * Service method to fetch all existing owners in the database
     * @throws PLMSException - if no owners exist in the system
     */
    @Transactional
    public Iterable<Owner> getAllOwners() {
        Iterable<Owner> owners = ownerRepository.findAll();
        Iterator<Owner> iterator = owners.iterator();
        if (!iterator.hasNext())
            throw new PLMSException(HttpStatus.NOT_FOUND, "There are no owners in the system");
        return ownerRepository.findAll(); }

    /**
     * Service method to fetch an existing owner with a specific email from the database
     * @throws PLMSException - If the owner does not exist
     */
    @Transactional
    public Owner getOwnerByEmail(String email) {
        Owner owner = ownerRepository.findOwnerByEmail(email);
        if (owner == null) {
            throw new PLMSException(HttpStatus.NOT_FOUND, "Owner not found.");
        }
        return owner;
    }

    /**
     * Service method that updates the owner's information in the database
     * @throws PLMSException - If owner does not exist
     */
    @Transactional
    public Owner updateOwnerAccount(Owner owner)
    {
        Owner o = getOwnerByEmail(owner.getEmail());
        o.setPassword(owner.getPassword());
        o.setName(owner.getName());
        return ownerRepository.save(o);

    }

    /**
     * Service method to store a created owner in the database
     * @throws PLMSException - If an owner already exists
     */
    @Transactional
	public Owner createOwnerAccount(Owner owner) {
        // Register the owner account into database
        if (ownerRepository.findOwnerByEmail(owner.getEmail()) == null)
		    return ownerRepository.save(owner);
        else
            throw new PLMSException(HttpStatus.CONFLICT, "Owner account with this email already exists");

    }


}
