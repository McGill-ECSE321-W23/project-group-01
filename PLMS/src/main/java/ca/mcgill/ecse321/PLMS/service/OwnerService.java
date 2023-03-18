package ca.mcgill.ecse321.PLMS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.PLMS.exception.PLMSException;
import ca.mcgill.ecse321.PLMS.model.Employee;
import ca.mcgill.ecse321.PLMS.model.MonthlyCustomer;
import ca.mcgill.ecse321.PLMS.model.Owner;
import ca.mcgill.ecse321.PLMS.repository.OwnerRepository;

/**
 * Service class for the Account model objects in the database
 * @author Karl Bridi Soft. Eng. student
 */
@Service
public class OwnerService {

    @Autowired
    OwnerRepository ownerRepository;

    @Transactional
    public Iterable<Owner> getAllOwners() { return ownerRepository.findAll(); }

    @Transactional
    public Owner getOwnerByEmail(String email) {
        Owner owner = ownerRepository.findOwnerByEmail(email);
        if (owner == null) {
            throw new PLMSException(HttpStatus.NOT_FOUND, "Owner not found.");
        }
        return owner;
    }

    @Transactional
    public Owner updateOwnerAccount(Owner owner)
    {
       return ownerRepository.save(getOwnerByEmail(owner.getEmail()));

    }

    @Transactional
	public Owner createOwnerAccount(Owner owner) {
        // Register the owner account into database
        if (ownerRepository.findOwnerByEmail(owner.getEmail()) == null)
		    return ownerRepository.save(owner);
        else
            throw new PLMSException(HttpStatus.CONFLICT, "Account with this email already exists");

    }


}
