package ca.mcgill.ecse321.PLMS.repository;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.PLMS.model.Pass;

public interface PassRepository extends CrudRepository<Pass, Integer>{

    /**
	 * Find a pass by id
	 * @param id
	 * @return Pass found
	 */
    public Pass findPassById(Integer id);
}
