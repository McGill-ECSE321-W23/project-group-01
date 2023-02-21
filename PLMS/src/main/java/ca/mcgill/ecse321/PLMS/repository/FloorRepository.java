package ca.mcgill.ecse321.PLMS.repository;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.PLMS.model.Floor;

public interface FloorRepository extends CrudRepository<Floor, int>{

    Floor findFloorByFloorNumber(int floorNumber);
    
}
