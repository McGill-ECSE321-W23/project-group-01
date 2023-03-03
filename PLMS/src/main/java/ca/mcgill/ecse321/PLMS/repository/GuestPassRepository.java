package ca.mcgill.ecse321.PLMS.repository;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.PLMS.model.GuestPass;

public interface GuestPassRepository extends CrudRepository<GuestPass, Integer>{

    /**
     * Find the guest pass based on its id
     * @param id - id of guest pass
     * @return guestpass with id id
     */
    public GuestPass findGuestPassById(int id);
    
}
