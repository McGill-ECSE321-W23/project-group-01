package ca.mcgill.ecse321.PLMS.repository;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.PLMS.model.ParkingLot;

public interface ParkingLotRepository extends CrudRepository<ParkingLot, Integer>{

    /**
     * Finds the parking lot based on the ID of the parking lot
     * @param id - id of the lot
     * @return ParkingLot with id id
     */
    public ParkingLot findParkingLotById(int id);
    
}
