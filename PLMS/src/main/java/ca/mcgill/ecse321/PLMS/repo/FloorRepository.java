package ca.mcgill.ecse321.PLMS.repo;

import ca.mcgill.ecse321.PLMS.model.Floor;
import org.springframework.data.repository.CrudRepository;

public interface FloorRepository extends CrudRepository<Floor, Integer> {
    public Floor findFloorByFloorNumber(int floorNumber);
}
