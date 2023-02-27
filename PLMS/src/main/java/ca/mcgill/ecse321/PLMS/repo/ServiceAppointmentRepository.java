package ca.mcgill.ecse321.PLMS.repo;

import ca.mcgill.ecse321.PLMS.model.ServiceAppointment;
import org.springframework.data.repository.CrudRepository;

public interface ServiceAppointmentRepository extends CrudRepository<ServiceAppointment, Integer> {
    public ServiceAppointment findServiceAppointmentById(int id);
}
