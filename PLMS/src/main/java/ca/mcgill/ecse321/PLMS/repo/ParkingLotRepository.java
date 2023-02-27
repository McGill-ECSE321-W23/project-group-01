package ca.mcgill.ecse321.PLMS.repo;

import ca.mcgill.ecse321.PLMS.model.ParkingLot;
import org.springframework.data.repository.CrudRepository;

public interface ParkingLotRepository extends CrudRepository<ParkingLot, Integer> {
    public ParkingLot findParkingLotById(int id);
}
