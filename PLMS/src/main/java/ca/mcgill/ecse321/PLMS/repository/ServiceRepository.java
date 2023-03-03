package ca.mcgill.ecse321.PLMS.repository;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.PLMS.model.Service;

public interface ServiceRepository extends CrudRepository<Service, String>{

    /**
     * Find the service based on its name
     * @param serviceName - name of the service
     * @return Service with serviceName serviceName
     */
    public Service findServiceByServiceName(String serviceName);
    
}
