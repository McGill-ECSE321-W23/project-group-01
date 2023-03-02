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
import ca.mcgill.ecse321.PLMS.model.GuestPass;
import ca.mcgill.ecse321.PLMS.model.ParkingLot;
import ca.mcgill.ecse321.PLMS.model.Pass;
import ca.mcgill.ecse321.PLMS.repository.FloorRepository;
import ca.mcgill.ecse321.PLMS.repository.GuestPassRepository;
import ca.mcgill.ecse321.PLMS.repository.PassRepository;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PassRepositoryTests {
    @Autowired
    private GuestPassRepository guestPassRepository;
    @Autowired
    private FloorRepository floorRepository;

    @BeforeEach
    @AfterEach
    public void clearDataBase(){
        guestPassRepository.deleteAll();

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
        ParkingLot parkingLot = new ParkingLot();

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
        GuestPass guestPass = new GuestPass();

        //Set all parameters
        guestPass.setFee(fee);
        guestPass.setSpotNumber(spotNumber);
        guestPass.setLicensePlate(licensePlate);
        guestPass.setConfirmationCode(confirmationCode);

        guestPass.setFloor(mainFloor);

        //=-=-=-=-=-=- Save guest pass -=-=-=-=-=-=//
        guestPassRepository.save(guestPass);
        int id = guestPass.getId();
    
        //=-=-=-=-=-=- Read guest pass -=-=-=-=-=-=//
        guestPass = guestPassRepository.findGuestPassById(id);

        //=-=-=-=-=-=- Asserts -=-=-=-=-=-=//
        assertNotNull(guestPass);
        assertEquals(fee, guestPass.getFee());
        assertEquals(spotNumber, guestPass.getSpotNumber());
        assertEquals(licensePlate, guestPass.getLicensePlate());
        assertEquals(confirmationCode, guestPass.getConfirmationCode());

        assertEquals(floorId, guestPass.getFloor().getId());
    }
}
