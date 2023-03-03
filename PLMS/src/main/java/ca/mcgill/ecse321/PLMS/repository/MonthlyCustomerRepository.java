package ca.mcgill.ecse321.PLMS.repository;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.PLMS.model.MonthlyCustomer;

public interface MonthlyCustomerRepository extends CrudRepository<MonthlyCustomer, String>{

    /**
     * Find a monthly customer by their email
     * @param email - email of monthly customer
     * @return MonthlyCustomer with email email
     */
    public MonthlyCustomer findMonthlyCustomerByEmail(String email);
    
}
