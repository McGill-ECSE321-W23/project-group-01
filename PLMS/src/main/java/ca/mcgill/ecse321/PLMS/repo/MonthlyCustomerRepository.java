package ca.mcgill.ecse321.PLMS.repo;

import ca.mcgill.ecse321.PLMS.model.MonthlyCustomer;
import org.springframework.data.repository.CrudRepository;

public interface MonthlyCustomerRepository extends CrudRepository<MonthlyCustomer, String> {
    public MonthlyCustomer findMonthlyCustomerByEmail(String email);

}
