package ca.mcgill.ecse321.PLMS.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import ca.mcgill.ecse321.PLMS.exception.PLMSException;
import ca.mcgill.ecse321.PLMS.model.Floor;
import ca.mcgill.ecse321.PLMS.model.ParkingLot;
import ca.mcgill.ecse321.PLMS.repository.FloorRepository;
import ca.mcgill.ecse321.PLMS.repository.ParkingLotRepository;

@SpringBootTest
public class FloorServiceTests {
    @Mock
    private FloorRepository floorRepository;

    @Mock
    private ParkingLotRepository parkingLotRepository;

    @InjectMocks
    private FloorService floorService;

    
    @Test
    /**
     * test getting a valid floor from our database
     */
    public void testGetValidFloor(){
      // set up a mock floor to be used by floor repo
      final int floorNumber = 1;
		  final int smallSpotCapacity = 70;
      final int largeSpotCapacity = 25;
      final int smallSpotCounter = 0;
      final int largeSpotCounter = 0;
      final boolean isMemberOnly = false;
      final Floor floor = new Floor(floorNumber, largeSpotCapacity, smallSpotCapacity, smallSpotCounter, largeSpotCounter, isMemberOnly);
      when(floorRepository.findFloorByFloorNumber(floorNumber)).thenReturn(floor);

      // Call the component under test
      Floor output = floorService.getFloorByFloorNumber(floorNumber);

      // Check that the output is right
      assertNotNull(output);
      assertEquals(floorNumber, output.getFloorNumber());
      assertEquals(smallSpotCapacity, output.getSmallSpotCapacity());
      assertEquals(largeSpotCapacity, output.getLargeSpotCapacity());
      assertEquals(largeSpotCounter, output.getLargeSpotCounter());
      assertEquals(smallSpotCounter, output.getSmallSpotCounter());
    }

    @Test
    /**
     * test getting a floor that is not in the database
     */
    public void testGetInvalidFloor(){
      final int invalidFloorNumber = 42;
		  when(floorRepository.findFloorByFloorNumber(invalidFloorNumber)).thenReturn(null);

		PLMSException e = assertThrows(PLMSException.class,
				() -> floorService.getFloorByFloorNumber(invalidFloorNumber));
		assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
		assertEquals("Floor with floor number: " + invalidFloorNumber + " does not exist.", e.getMessage());
    }

    @Test
    /**
     * Test getting the floors when there are none in the database
     */
    public void testGetAllInvalidFloors(){
      ArrayList<Floor> floors = new ArrayList<Floor>();
      when(floorRepository.findAll()).thenReturn((Iterable<Floor>)floors);
      PLMSException e = assertThrows(PLMSException.class,
				() -> floorService.getAllFloors());
		assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
		assertEquals("There are no floors in the system", e.getMessage());
    }

    @Test
    /**
     * Test to create a valid floor object in the database
     */
    public void testCreateValidFloor(){
      // set up a mock floor to be used by floor repo
      final int floorNumber = 1;
		  final int smallSpotCapacity = 70;
      final int largeSpotCapacity = 25;
      final int smallSpotCounter = 0;
      final int largeSpotCounter = 0;
      final boolean isMemberOnly = false;
      final Floor floor = new Floor(floorNumber, largeSpotCapacity, smallSpotCapacity, smallSpotCounter, largeSpotCounter, isMemberOnly);
      when(floorRepository.findFloorByFloorNumber(floorNumber)).thenReturn(null);

      //Normal parameters
      Time openingTime = Time.valueOf("6:00:00");
      Time closingTime = Time.valueOf("22:00:00");
      double smallSpotFee = 3.5;
      double largeSpotFee = 4.5;
      double smallSpotMonthlyFlatFee = 150;
      double largeSpotMonthlyFlatFee = 150;
      int id = 10;
      ParkingLot parkingLot = new ParkingLot();
      //--------------------------------//

      //Set all parameters
      parkingLot.setOpeningTime(openingTime);
      parkingLot.setClosingTime(closingTime);
      parkingLot.setSmallSpotFee(smallSpotFee);
      parkingLot.setLargeSpotFee(largeSpotFee);
      parkingLot.setSmallSpotMonthlyFlatFee(smallSpotMonthlyFlatFee);
      parkingLot.setLargeSpotMonthlyFlatFee(largeSpotMonthlyFlatFee);
      parkingLot.setId(id);
      // The parking lot repo should return a single parking lot
      when(parkingLotRepository.findAll()).thenReturn(Collections.singletonList(parkingLot));
      when(floorRepository.save(floor)).thenReturn(floor);
      Floor output = floorService.createFloor(floor);
      
      // Check that the output is right
      assertNotNull(output);
      assertEquals(floorNumber, output.getFloorNumber());
      assertEquals(smallSpotCapacity, output.getSmallSpotCapacity());
      assertEquals(largeSpotCapacity, output.getLargeSpotCapacity());
      assertEquals(largeSpotCounter, output.getLargeSpotCounter());
      assertEquals(smallSpotCounter, output.getSmallSpotCounter());

      // check to see that it was correctly assigned to the parking lot
      assertEquals(id, output.getParkingLot().getId());
      
      // We also want to check that the service actually saved John in the database!
      // personRepo.save() should be called exactly once
      verify(floorRepository, times(1)).save(floor);
    }

    @Test
    /**
     * Test to create a floor with a duplicate floor number. Our system
     * should not allow the owner to create duplicate floor numbers
     */
    public void testCreateDuplicateFloor(){
      // set up a mock floor to be used by floor repo
      final int floorNumber = 1;
		  final int smallSpotCapacity = 70;
      final int largeSpotCapacity = 25;
      final int smallSpotCounter = 0;
      final int largeSpotCounter = 0;
      final boolean isMemberOnly = false;
      final Floor floor = new Floor(floorNumber, largeSpotCapacity, smallSpotCapacity, smallSpotCounter, largeSpotCounter, isMemberOnly);
      when(floorRepository.findFloorByFloorNumber(floorNumber)).thenReturn(floor);
		  final int smallSpotCapacity2 = 60;
      final int largeSpotCapacity2 = 5;
      final int smallSpotCounter2 = 70;
      final int largeSpotCounter2 = 1;
      final boolean isMemberOnly2 = true;
      final Floor floor2 = new Floor(floorNumber, largeSpotCapacity2, smallSpotCapacity2, smallSpotCounter2, largeSpotCounter2, isMemberOnly2);

      PLMSException e = assertThrows(PLMSException.class,
				() -> floorService.createFloor(floor2));
		assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
		assertEquals("Floor with floor number: " + floor.getFloorNumber() + " already exists.", e.getMessage());
      
    }

    @Test
    /**
     * Floors cannot be in the database if the base parking lot object
     * has not yet been created.
     */
    public void testCreateFloorWithoutParkingLot(){
      // set up a mock floor to be used by floor repo
      final int floorNumber = 1;
		  final int smallSpotCapacity = 70;
      final int largeSpotCapacity = 25;
      final int smallSpotCounter = 0;
      final int largeSpotCounter = 0;
      final boolean isMemberOnly = false;
      final Floor floor = new Floor(floorNumber, largeSpotCapacity, smallSpotCapacity, smallSpotCounter, largeSpotCounter, isMemberOnly);
      when(floorRepository.findFloorByFloorNumber(floorNumber)).thenReturn(null);
      when(parkingLotRepository.findAll()).thenReturn(null);
      PLMSException e = assertThrows(PLMSException.class,
				() -> floorService.createFloor(floor));
		assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
		assertEquals("Cannot create floor since the parking lot has not been created", e.getMessage());
    }

    @Test
    /**
     * Test to get multiple floors all at once from the service class
     */
    public void testGetAllFloors(){
      final int floorNumber = 1;
		  final int smallSpotCapacity = 70;
      final int largeSpotCapacity = 25;
      final int smallSpotCounter = 0;
      final int largeSpotCounter = 0;
      final boolean isMemberOnly = false;
      final Floor floor = new Floor(floorNumber, largeSpotCapacity, smallSpotCapacity, smallSpotCounter, largeSpotCounter, isMemberOnly);
		  final int smallSpotCapacity2 = 60;
      final int largeSpotCapacity2 = 5;
      final int smallSpotCounter2 = 70;
      final int largeSpotCounter2 = 1;
      final boolean isMemberOnly2 = true;
      final Floor floor2 = new Floor(floorNumber, largeSpotCapacity2, smallSpotCapacity2, smallSpotCounter2, largeSpotCounter2, isMemberOnly2);

      //Normal parameters
      Time openingTime = Time.valueOf("6:00:00");
      Time closingTime = Time.valueOf("22:00:00");
      double smallSpotFee = 3.5;
      double largeSpotFee = 4.5;
      double smallSpotMonthlyFlatFee = 150;
      double largeSpotMonthlyFlatFee = 150;
      int id = 10;
      ParkingLot parkingLot = new ParkingLot();
      //--------------------------------//

      //Set all parameters
      parkingLot.setOpeningTime(openingTime);
      parkingLot.setClosingTime(closingTime);
      parkingLot.setSmallSpotFee(smallSpotFee);
      parkingLot.setLargeSpotFee(largeSpotFee);
      parkingLot.setSmallSpotMonthlyFlatFee(smallSpotMonthlyFlatFee);
      parkingLot.setLargeSpotMonthlyFlatFee(largeSpotMonthlyFlatFee);
      parkingLot.setId(id);
      floor.setParkingLot(parkingLot);
      floor2.setParkingLot(parkingLot);
      ArrayList<Floor> floors = new ArrayList<Floor>();
      floors.add(floor);
      floors.add(floor2);

      // perform get all floors
      when(floorRepository.findAll()).thenReturn(floors);
      Iterable<Floor> output = floorService.getAllFloors();
      Floor floorOutput1 =  floors.iterator().next();
      Floor floorOutput2 = floors.iterator().next();

      // check that we got floors
      assertNotNull(output);

      // Check that the output is right for floorOutput1
      assertNotNull(floorOutput1);
      assertEquals(floorNumber, floorOutput1.getFloorNumber());
      assertEquals(smallSpotCapacity, floorOutput1.getSmallSpotCapacity());
      assertEquals(largeSpotCapacity, floorOutput1.getLargeSpotCapacity());
      assertEquals(largeSpotCounter, floorOutput1.getLargeSpotCounter());
      assertEquals(smallSpotCounter, floorOutput1.getSmallSpotCounter());

      // check to see that it was correctly assigned to the parking lot
      assertEquals(id, floorOutput1.getParkingLot().getId());

      // Check that the output is right for floorOutput2
      assertNotNull(floorOutput2);
      assertEquals(floorNumber, floorOutput2.getFloorNumber());
      assertEquals(smallSpotCapacity, floorOutput2.getSmallSpotCapacity());
      assertEquals(largeSpotCapacity, floorOutput2.getLargeSpotCapacity());
      assertEquals(largeSpotCounter, floorOutput2.getLargeSpotCounter());
      assertEquals(smallSpotCounter, floorOutput2.getSmallSpotCounter());

      // check to see that it was correctly assigned to the parking lot
      assertEquals(id, floorOutput2.getParkingLot().getId());
    }


    @Test
    /**
     * Test to update a floor in our database
     */
    public void updateValidFloor(){
      // create mock floors
      final int floorNumber = 1;
		  final int smallSpotCapacity = 70;
      final int largeSpotCapacity = 25;
      final int smallSpotCounter = 0;
      final int largeSpotCounter = 0;
      final boolean isMemberOnly = false;
      final Floor floor = new Floor(floorNumber, largeSpotCapacity, smallSpotCapacity, smallSpotCounter, largeSpotCounter, isMemberOnly);
      // you cannot update floor counters as these are simply used by the pass class to keep track of how many passes are on the floor
		  final int smallSpotCapacity2 = 60;
      final int largeSpotCapacity2 = 5;
      final boolean isMemberOnly2 = true;
      final Floor floor2 = new Floor(floorNumber, largeSpotCapacity2, smallSpotCapacity2, smallSpotCounter, largeSpotCounter, isMemberOnly2);

      // load first floor into repo
      when(floorRepository.findFloorByFloorNumber(floorNumber)).thenReturn(floor);
      when(floorService.getFloorByFloorNumber(floorNumber)).thenReturn(floor);
      when(floorRepository.save(floor)).thenReturn(floor2);
      Floor updated = floorService.updateFloor(floor2);
      System.out.println("Mock repository returned: " + floorRepository.findFloorByFloorNumber(floorNumber));

      // assert the newly updated floor values
      assertNotNull(updated);
      assertEquals(floorNumber, updated.getFloorNumber());
      assertEquals(smallSpotCapacity2, updated.getSmallSpotCapacity());
      assertEquals(largeSpotCapacity2, updated.getLargeSpotCapacity());
      assertEquals(largeSpotCounter, updated.getLargeSpotCounter());
      assertEquals(smallSpotCounter, updated.getSmallSpotCounter());
      assertEquals(isMemberOnly2, updated.getIsMemberOnly());
    }

    @Test
    /**
     * Attempt to update a floor that doesn't already exist in the DB.
     */
    public void testInvalidUpdate(){
      // create mock floors, with invalid small spot counter
      final int floorNumber = 1;
		  final int smallSpotCapacity = 70;
      final int largeSpotCapacity = 25;
      final int smallSpotCounter = 0;
      final int largeSpotCounter = 0;
      final boolean isMemberOnly = false;
      final Floor floor = new Floor(floorNumber, largeSpotCapacity, smallSpotCapacity, smallSpotCounter, largeSpotCounter, isMemberOnly);

      when(floorRepository.findFloorByFloorNumber(floorNumber)).thenReturn(null);

      PLMSException e = assertThrows(PLMSException.class,
				() -> floorService.updateFloor(floor));
		assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
		assertEquals("Floor with floor number: " + floorNumber + " does not exist.", e.getMessage());
    }

}