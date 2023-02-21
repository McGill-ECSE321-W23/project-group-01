package ca.mcgill.ecse321.PLMS.repository;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.PLMS.model.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, String>{

    Employee findEmployeeByEmail(String email);
    
}
