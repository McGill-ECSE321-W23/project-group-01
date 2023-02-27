package ca.mcgill.ecse321.PLMS.repository;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.PLMS.model.Service;

public interface ServiceRepository extends CrudRepository<Service, String>{

    public Service findServiceByServiceName(String serviceName);
    
}
