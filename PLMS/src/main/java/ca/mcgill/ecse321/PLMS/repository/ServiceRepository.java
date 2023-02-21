package ca.mcgill.ecse321.PLMS.repository;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.PLMS.model.Service;

public interface ServiceRepository extends CrudRepository<Pass, int>{

    Service findServiceById(int id);
    
}
