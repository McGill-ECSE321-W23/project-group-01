package ca.mcgill.ecse321.PLMS.Tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.PLMS.model.Floor;
import ca.mcgill.ecse321.PLMS.repository.FloorRepository;

@SpringBootTest
public class FloorRepositoryTests {
    @Autowired
    private FloorRepository floorRepository;

    @AfterEach
    public void clearDataBase(){
        floorRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadFloor(){
        //Create object
        int floorNumber = 0;
        int largeSpotCapacity = 15;
        int smallSpotCapacity = 60;
        Floor mainFloor = new Floor();
        //Set all the attributes
        mainFloor.setFloorNumber(floorNumber);
        mainFloor.setLargeSpotCounter(largeSpotCapacity);
        mainFloor.setSmallSpotCounter(smallSpotCapacity);

        //Save object
        mainFloor = floorRepository.save(mainFloor);
        int id = mainFloor.getId();

        //Read object from database
        mainFloor = floorRepository.findFloorById(id);

        //Asserts to check correct attributes
        assertNotNull(mainFloor);
        assertEquals(floorNumber, mainFloor.getFloorNumber());
        assertEquals(largeSpotCapacity, mainFloor.getLargeSpotCapacity());
        assertEquals(smallSpotCapacity, mainFloor.getSmallSpotCapacity());
    }
}
