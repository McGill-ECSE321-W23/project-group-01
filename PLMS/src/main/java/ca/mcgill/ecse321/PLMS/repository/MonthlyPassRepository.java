package ca.mcgill.ecse321.PLMS.repository;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.PLMS.model.MonthlyPass;

public interface MonthlyPassRepository extends CrudRepository<MonthlyPass, Integer>{

    /**
     * Find a monthly pass based on id
     * @param id - id of monthly pass
     * @return MonthlyPass of id id
     */
    public MonthlyPass findMonthlyPassById(int id);
    
}
