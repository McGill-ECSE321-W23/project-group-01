package ca.mcgill.ecse321.PLMS.repository;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.PLMS.model.MonthlyPass;

public interface MonthlyPassRepository extends CrudRepository<MonthlyPass, Integer>{

    public MonthlyPass findMonthlyPassById(Integer id);
    
}
