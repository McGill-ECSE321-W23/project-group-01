package ca.mcgill.ecse321.PLMS.repository;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.PLMS.model.MonthlyCustomer;

public interface MonthlyCustomerRepository extends CrudRepository<MonthlyCustomer, String>{

    MonthlyCustomer findMonthlyCustomerByEmail(String email);
    
}
