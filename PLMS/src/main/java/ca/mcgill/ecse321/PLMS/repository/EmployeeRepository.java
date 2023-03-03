package ca.mcgill.ecse321.PLMS.repository;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.PLMS.model.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, String>{

    /**
     * Find an employee based on their unique email
     * @param email - email of the employee
     * @return email of the employee with email
     */
    public Employee findEmployeeByEmail(String email);
    
}
