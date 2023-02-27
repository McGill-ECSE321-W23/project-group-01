package ca.mcgill.ecse321.PLMS.repository;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.PLMS.model.ParkingLot;

public interface ParkingLotRepository extends CrudRepository<ParkingLot, Integer>{

    ParkingLot findParkingLotById(Integer id);
    
}
