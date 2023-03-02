package ca.mcgill.ecse321.PLMS.Tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.sql.Time;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ca.mcgill.ecse321.PLMS.model.Floor;
import ca.mcgill.ecse321.PLMS.model.Pass;
import ca.mcgill.ecse321.PLMS.repository.FloorRepository;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PassRepositoryTests {
    @Autowired
    private PassRepository passRepository;
    @Autowired
    private FloorRepository floorRepository;

    @BeforeEach
    @AfterEach
    public void clearDataBase(){
        passRepository.deleteAll();

        floorRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadPass(){
        //=-=-=-=-=-=- Create object -=-=-=-=-=-=//
        double fee = 50.50;
        String spotNumber = "A24";
        String licensePlate = "123ABC123";
        String confirmationCode = "NeverGonnaGiveYouUp";
       
        //To create the object we need to first create a floor
        //---------  Floor  ---------//
        int floorNumber = 0;
        int largeSpotCapacity = 15;
        int smallSpotCapacity = 60;
        Floor mainFloor = new Floor();

        //Set all the attributes
        mainFloor.setFloorNumber(floorNumber);
        mainFloor.setLargeSpotCapacity(largeSpotCapacity);
        mainFloor.setSmallSpotCapacity(smallSpotCapacity);
        mainFloor.setParkingLot(parkingLot);

        //Save floor
        mainFloor = floorRepository.save(mainFloor);
        int floorId = mainFloor.getId();
        //---------------------------//

        //Create Pass
        Pass pass = new Pass();

        //Set all parameters
        pass.setFee(fee);
        pass.setSpotNumber(spotNumber);
        pass.setLicensePlate(licensePlate);
        pass.setConfirmationCode(confirmationCode);

        pass.setFloor(mainFloor);

        //=-=-=-=-=-=- Save guest pass -=-=-=-=-=-=//
        passRepository.save(guestPass);
        int id = pass.getId();
    
        //=-=-=-=-=-=- Read guest pass -=-=-=-=-=-=//
        pass = passRepository.findPassById(id);

        //=-=-=-=-=-=- Asserts -=-=-=-=-=-=//
        assertNotNull(pass);
        assertEquals(fee, pass.getFee());
        assertEquals(spotNumber, pass.getSpotNumber());
        assertEquals(licensePlate, pass.getLicensePlate());
        assertEquals(confirmationCode, pass.getConfirmationCode());

        assertEquals(floorId, pass.getFloor().getId());
    }
}
