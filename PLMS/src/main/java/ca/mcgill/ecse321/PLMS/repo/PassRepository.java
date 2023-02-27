package ca.mcgill.ecse321.PLMS.repo;

import ca.mcgill.ecse321.PLMS.model.Pass;
import org.springframework.data.repository.CrudRepository;

public interface PassRepository extends CrudRepository<Pass, Integer> {
    public Pass findPassById(int id);
}
