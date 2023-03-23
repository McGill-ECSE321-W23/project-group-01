package ca.mcgill.ecse321.PLMS.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.PLMS.exception.PLMSException;
import ca.mcgill.ecse321.PLMS.model.Floor;
import ca.mcgill.ecse321.PLMS.model.ParkingLot;
import ca.mcgill.ecse321.PLMS.repository.FloorRepository;
import ca.mcgill.ecse321.PLMS.repository.ParkingLotRepository;
import jakarta.transaction.Transactional;

@Service
public class FloorService {

    @Autowired
    FloorRepository floorRepository;

    @Autowired
    ParkingLotRepository parkingLotRepository;

    /**
     * Service method to fetch all floors in the database
     */
    @Transactional
    public Iterable<Floor> getAllFloors(){
       return floorRepository.findAll();
    }

    /**
     * Service method to fetch a floor with a specific floor number in the database
     */
    @Transactional
    public Floor getFloorByFloorNumber(int floorNumber){
        Floor floor = floorRepository.findFloorByFloorNumber(floorNumber);
        if (floor == null){
            throw new PLMSException(HttpStatus.NOT_FOUND, "Floor with floor number: " + floorNumber + " does not exist.");
        }
        return floor;
    }

    /**
     * Service method to store the created floor object into the database
     */
    @Transactional
    public Floor createFloor(Floor floor){
        //checks on the new object are made in the DTO
        //check if the floor already exists
        if (floorRepository.findFloorByFloorNumber(floor.getFloorNumber()) != null){
            throw new PLMSException(HttpStatus.BAD_REQUEST, "Floor with floor number: " + floor.getFloorNumber() + " already exists.");
        }

        // check for the parking lot in the database, if it doesn't exist yet we cannot create the floor
        Iterable<ParkingLot> lots = parkingLotRepository.findAll();
        if (lots == null || !lots.iterator().hasNext()){
            throw new PLMSException(HttpStatus.BAD_REQUEST, "Cannot create floor since the parking lot has not been created.");
        }
        ParkingLot lot = lots.iterator().next();
        floor.setParkingLot(lot);

        //create object
        floor = floorRepository.save(floor);
        //returned created object
        return floor;
    }

    /**
     * Service method that updates a floor object in the database
     */
    @Transactional
    public Floor updateFloor(Floor floor){
        //check if the floor exists (the floor has to exist to edit it)
        Floor existingFloor = getFloorByFloorNumber(floor.getFloorNumber());

        // counter cannot be larger than capacity
        if(floor.getLargeSpotCapacity() < existingFloor.getLargeSpotCounter()){
            throw new PLMSException(HttpStatus.BAD_REQUEST, "The large spots occupied exceeds the capacity.");
        }

        // counter cannot be larger than capacity
        if(floor.getSmallSpotCapacity() < existingFloor.getSmallSpotCounter()){
            throw new PLMSException(HttpStatus.BAD_REQUEST, "The small spots occupied exceeds the capacity.");
        }

        // update the properties of the existing Floor entity
        existingFloor.setIsMemberOnly(floor.getIsMemberOnly());
        existingFloor.setLargeSpotCapacity(floor.getLargeSpotCapacity());
        existingFloor.setSmallSpotCapacity(floor.getSmallSpotCapacity());
        //In update we dont want to reset the counter to 0, the floor given by the user automaticly has the counters at 0s
        // existingFloor.setLargeSpotCounter(floor.getLargeSpotCounter());
        // existingFloor.setSmallSpotCounter(floor.getSmallSpotCounter());

        // save the changes to the database
        existingFloor = floorRepository.save(existingFloor);
        return existingFloor;
    }

    /**
     * Service method that deletes the floor with floor number floorNumber from the database
     */
    @Transactional
    public void deleteFloorByFloorNumber(int floorNumber){
        //Checks for non null are made in the method already
        Floor floor = getFloorByFloorNumber(floorNumber);
        floorRepository.delete(floor);
    }



}
