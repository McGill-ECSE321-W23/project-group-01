package ca.mcgill.ecse321.PLMS.repository;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.PLMS.model.Floor;

public interface FloorRepository extends CrudRepository<Floor, Integer>{

    /**
     * Find the floor based on its id
     * @param id
     * @return floor with id id
     */
    public Floor findFloorById(int id);
    
}
