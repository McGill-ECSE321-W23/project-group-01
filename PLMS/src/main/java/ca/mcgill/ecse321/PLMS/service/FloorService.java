package ca.mcgill.ecse321.PLMS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.PLMS.exception.PLMSException;
import ca.mcgill.ecse321.PLMS.model.Floor;
import ca.mcgill.ecse321.PLMS.repository.FloorRepository;
import jakarta.transaction.Transactional;

@Service
public class FloorService {

    @Autowired
    FloorRepository floorRepository;

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
        //!!!! THIS METHOD IS NOT IMPLEMENTED YET !!!!!
        floor = floorRepository.save(floor);
        return floor;
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
