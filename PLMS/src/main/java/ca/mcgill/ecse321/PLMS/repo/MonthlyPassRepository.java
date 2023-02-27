package ca.mcgill.ecse321.PLMS.repo;

import ca.mcgill.ecse321.PLMS.model.MonthlyPass;
import org.springframework.data.repository.CrudRepository;

public interface MonthlyPassRepository extends CrudRepository<MonthlyPass, Integer> {
    public MonthlyPass findMonthlyPassById(int id);
}
