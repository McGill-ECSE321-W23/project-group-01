package ca.mcgill.ecse321.PLMS.repository;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.PLMS.model.Pass;

public interface PassRepository extends CrudRepository<Pass, Integer>{

    /**
	 * Find a pass by id
	 * @param id
	 * @return Pass found
	 */
    Pass findPassById(Integer id);

    /**
	 * @return all passes
	 */
    List<Pass> findAll();

    /**
	 * Delete the pass with the given id
	 * @param id
	 */
    void deletePassById(Integer id);

}
