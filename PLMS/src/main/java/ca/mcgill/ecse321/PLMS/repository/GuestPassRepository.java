package ca.mcgill.ecse321.PLMS.repository;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.PLMS.model.GuestPass;

public interface GuestPassRepository extends CrudRepository<GuestPass, Integer>{

    public GuestPass findGuestPassById(Integer id);
    
}
