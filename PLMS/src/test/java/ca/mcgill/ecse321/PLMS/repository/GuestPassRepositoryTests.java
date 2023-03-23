package ca.mcgill.ecse321.PLMS.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ca.mcgill.ecse321.PLMS.model.Floor;
import ca.mcgill.ecse321.PLMS.model.GuestPass;
import ca.mcgill.ecse321.PLMS.model.ParkingLot;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Testing suite for the persistence of the GuestPass class in the PLMS software system
 * Tests ensure that attributes, references and the instance itself can be read and written
 * in the persistence database
 */
@SpringBootTest
public class GuestPassRepositoryTests {
    @Autowired
    private GuestPassRepository guestPassRepository;
    @Autowired
    private FloorRepository floorRepository;
    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @BeforeEach
    @AfterEach
    public void clearDataBase(){
        guestPassRepository.deleteAll();

        floorRepository.deleteAll();

        parkingLotRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadGuestPass(){
        //=-=-=-=-=-=- Create object -=-=-=-=-=-=//
        double fee = 50.50;
        String spotNumber = "A24";
        String licensePlate = "123ABC123";
        Date date = Date.valueOf("2023-02-21");
        Time starTime = Time.valueOf("12:00:00");
        Time endTime = Time.valueOf("18:00:00");
        boolean isLarge = true;
        String confirmationCode = "NeverGonnaGiveYouUp";
       
        //To create the object we need to first create a floor
        //---------  Floor  ---------//
        int floorNumber = 0;
        int largeSpotCapacity = 15;
        int smallSpotCapacity = 60;

        //To create the floor we need to first create a parking lot
        //  Parking lot  //
        Time openingTime = Time.valueOf("8:00:00");
        Time closingTime = Time.valueOf("20:00:00");
        double largeSpotFee = 10.10;
        double smallSpotFee = 5.5;
        double monthlyFlatFee = 50.50;
        ParkingLot parkingLot = new ParkingLot();
        //Set all parameters
        parkingLot.setOpeningTime(openingTime);
        parkingLot.setClosingTime(closingTime);
        parkingLot.setLargeSpotFee(largeSpotFee);
        parkingLot.setSmallSpotFee(smallSpotFee);
        parkingLot.setSmallSpotMonthlyFlatFee(monthlyFlatFee);
        parkingLot.setLargeSpotMonthlyFlatFee(monthlyFlatFee + 10);
        //Save parking lot
        parkingLot = parkingLotRepository.save(parkingLot);
        parkingLot = parkingLotRepository.findParkingLotById(parkingLot.getId());
        int parkingLotId = parkingLot.getId();
        //---------------//
        
        //back to creating the floor
        Floor mainFloor = new Floor();
        //Set all the attributes
        mainFloor.setFloorNumber(floorNumber);
        mainFloor.setLargeSpotCapacity(largeSpotCapacity);
        mainFloor.setSmallSpotCapacity(smallSpotCapacity);
        mainFloor.setParkingLot(parkingLot);
        //Save floor
        mainFloor = floorRepository.save(mainFloor);
        int floorId = mainFloor.getFloorNumber();
        //---------------------------//

        GuestPass guestPass = new GuestPass();

        //Set all parameters
        guestPass.setFee(fee);
        guestPass.setSpotNumber(spotNumber);
        guestPass.setLicensePlate(licensePlate);
        guestPass.setDate(date);
        guestPass.setStartTime(starTime);
        guestPass.setEndTime(endTime);
        guestPass.setIsLarge(isLarge);
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
        assertEquals(date, guestPass.getDate());
        assertEquals(starTime, guestPass.getStartTime());
        assertEquals(endTime, guestPass.getEndTime());
        assertEquals(isLarge, guestPass.getIsLarge());
        assertEquals(confirmationCode, guestPass.getConfirmationCode());

        assertEquals(floorId, guestPass.getFloor().getFloorNumber());

        assertEquals(parkingLotId, guestPass.getFloor().getParkingLot().getId());
    }

    @Test
    public void testFindByFloorAndSpotNumberAndTimePeriod() {

        Floor Floor1 = new Floor( 1, 10, 10, 10, 10, false);

        // Create some test data
        GuestPass gp1 = new GuestPass();
        gp1.setFloor(Floor1);
        gp1.setSpotNumber("1A");
        gp1.setStartTime(Time.valueOf(LocalTime.of(9, 0)));
        gp1.setEndTime(Time.valueOf(LocalTime.of(10, 0)));
        guestPassRepository.save(gp1);

        GuestPass gp2 = new GuestPass();
        gp2.setFloor(Floor1);
        gp2.setSpotNumber("1A");
        gp2.setStartTime(Time.valueOf(LocalTime.of(11, 0)));
        gp2.setEndTime(Time.valueOf(LocalTime.of(12, 0)));
        guestPassRepository.save(gp2);

        GuestPass gp3 = new GuestPass();
        gp3.setFloor(Floor1);
        gp3.setSpotNumber("2A");
        gp3.setStartTime(Time.valueOf(LocalTime.of(13, 0)));
        gp3.setEndTime(Time.valueOf(LocalTime.of(14, 0)));
        guestPassRepository.save(gp3);

        // Test the method
        List<GuestPass> result = guestPassRepository.findByFloorAndSpotNumberAndTimePeriod(1, "1A",
                Time.valueOf(LocalTime.of(9, 30)), Time.valueOf(LocalTime.of(11, 30)));

        assertThat(result).containsExactly(gp1, gp2);
    }
}
