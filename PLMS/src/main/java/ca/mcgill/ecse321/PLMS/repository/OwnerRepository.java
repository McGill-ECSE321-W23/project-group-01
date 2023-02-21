package ca.mcgill.ecse321.PLMS.repository;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.PLMS.model.Owner;

public interface OwnerRepository extends CrudRepository<Owner, String>{

    Owner findOwnerByEmail(String Email);
    
}
