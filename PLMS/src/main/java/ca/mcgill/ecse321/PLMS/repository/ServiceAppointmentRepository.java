package ca.mcgill.ecse321.PLMS.repository;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.PLMS.model.ServiceAppointment;

public interface ServiceAppointmentRepository extends CrudRepository<ServiceAppointment, Integer>{

    public ServiceAppointment findServiceAppointmentById(Integer id);
    
}
