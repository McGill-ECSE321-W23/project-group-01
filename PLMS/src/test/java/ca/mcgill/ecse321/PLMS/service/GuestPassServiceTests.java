package ca.mcgill.ecse321.PLMS.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;

import ca.mcgill.ecse321.PLMS.model.ParkingLot;
import ca.mcgill.ecse321.PLMS.model.Pass;
import ca.mcgill.ecse321.PLMS.repository.MonthlyPassRepository;
import ca.mcgill.ecse321.PLMS.repository.ParkingLotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import ca.mcgill.ecse321.PLMS.exception.PLMSException;
import ca.mcgill.ecse321.PLMS.model.Floor;
import ca.mcgill.ecse321.PLMS.model.GuestPass;
import ca.mcgill.ecse321.PLMS.repository.FloorRepository;
import ca.mcgill.ecse321.PLMS.repository.GuestPassRepository;

@SpringBootTest
public class GuestPassServiceTests {

  @Mock
  private GuestPassRepository guestPassRepo;

  @Mock
  private FloorRepository floorRepo;

  @Mock
  private ParkingLotRepository parkingLotRepo;

  @Mock
  private MonthlyPassRepository monthlyPassRepository;

  @InjectMocks
  private GuestPassService guestPassService;
  private String spotNumber1;
  private GuestPass guestPass1;
  private GuestPass guestPass2;
  private Time starTime1;
  private Time endTime1;
  private String spotNumber2;
  private Time starTime2;
  private Time endTime2;

  @BeforeEach
  public void setUp(){
//    MockitoAnnotations.initMocks(this);

    final double fee1 = 50.50;
    final String spotNumber1 = "A24";
    final Date date1 = Date.valueOf("2023-02-21");
    final Time starTime1 = Time.valueOf("12:00:00");
    final Time endTime1 = Time.valueOf("18:00:00");
    boolean isLarge1 = true;
    final String confirmationCode1 = "NeverGonnaGiveYouUp";
    guestPass1 = new GuestPass(null, spotNumber1, null, isLarge1, date1, starTime1, endTime1);

    final double fee2 = 50.50;
    final String spotNumber2 = "A38";
    final Date date2 = Date.valueOf("2023-02-21");
    final Time starTime2 = Time.valueOf("14:00:00");
    final Time endTime2 = Time.valueOf("19:00:00");
    boolean isLarge2 = true;
    final String confirmationCode2 = "NeverGonnaGiveYouUp2";
    guestPass2 = new GuestPass(null, spotNumber2, null, isLarge2, date2, starTime2, endTime2);

  }

  @Test
  public void testGetValidGuestPass(){

    int id  = 1;
    when(guestPassRepo.findGuestPassById(id)).thenReturn(guestPass1);
    GuestPass output = guestPassService.getGuestPassById(id);
    assertEquals(output.getSpotNumber(), spotNumber1);
    assertEquals(output.getStartTime(), starTime1);
    assertEquals(output.getEndTime(), endTime1);
  }

  @Test
  public void testGetInvalidGuestPass(){
    final int invalidPassNumber = 42;
    when(guestPassRepo.findGuestPassById(invalidPassNumber)).thenReturn(null);

    PLMSException e = assertThrows(PLMSException.class,
            () -> guestPassService.getGuestPassById(invalidPassNumber));
    assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
    assertEquals("Guest pass with id: " + invalidPassNumber + " does not exist.", e.getMessage());
  }


    @Test
    public void testGetAllGuestPasses(){


      ArrayList<GuestPass> guestPasses = new ArrayList<>();
      guestPasses.add(guestPass1);
      guestPasses.add(guestPass2);

      when(guestPassRepo.findAll()).thenReturn(guestPasses);
      Iterable<GuestPass> output = guestPassService.getAllGuestPasses();
      Iterator<GuestPass> i = output.iterator();
      GuestPass outputGuestPass = i.next();
      assertEquals(outputGuestPass.getSpotNumber(), spotNumber1);
      assertEquals(outputGuestPass.getStartTime(), starTime1);
      assertEquals(outputGuestPass.getEndTime(), endTime1);

      outputGuestPass = i.next();
      assertEquals(outputGuestPass.getSpotNumber(), spotNumber2);
      assertEquals(outputGuestPass.getStartTime(), starTime2);
      assertEquals(outputGuestPass.getEndTime(), endTime2);


    }

    @Test
    public void testGetGuestPassesByFloor(){

      Floor floor = new Floor();
      floor.setFloorNumber(1);
      Floor floor2 = new Floor();
      floor2.setFloorNumber(2);

      guestPass1.setFloor(floor);
      guestPass2.setFloor(floor2);

      ArrayList<GuestPass> guestPasses = new ArrayList<>();
      guestPasses.add(guestPass1);
      guestPasses.add(guestPass2);
      when(guestPassRepo.findAll()).thenReturn(guestPasses);
      when(floorRepo.findFloorByFloorNumber(1)).thenReturn(floor);

      ArrayList<GuestPass> output = (ArrayList<GuestPass>) guestPassService.getGuestPassesByFloor(1);
        Iterator<GuestPass> i = output.iterator();
        GuestPass outputGuestPass = i.next();
      assertEquals(output.size(), 1);
      assertEquals(outputGuestPass.getFloor().getFloorNumber(), 1);
    }

    @Test
    public void testGetAllGuestPassesByFloorInvalid1(){
      // test for the case in which there is no floor that exists with this number
      when(floorRepo.findFloorByFloorNumber(1)).thenReturn(null);
      PLMSException e = assertThrows(PLMSException.class, () -> guestPassService.getGuestPassesByFloor(1));
        assertEquals(e.getStatus(), HttpStatus.NOT_FOUND);
        assertEquals(e.getMessage(), "The floor with floor number " + 1 + " does not exist.");
    }

    @Test
    public void testGetAllGuestPassesByFloorInvalid2(){
      guestPass2.setFloor(null);
      ArrayList<GuestPass> guestPasses = new ArrayList<>();
      guestPasses.add(guestPass2);
      when(guestPassRepo.findAll()).thenReturn(guestPasses);
      Floor floor = new Floor();
      floor.setFloorNumber(1);
      when(floorRepo.findFloorByFloorNumber(1)).thenReturn(floor);
      PLMSException e = assertThrows(PLMSException.class, () -> guestPassService.getGuestPassesByFloor(1));
        assertEquals(e.getStatus(), HttpStatus.NOT_FOUND);
        assertEquals(e.getMessage(), "There are no guest passes on floor " + 1);
    }

  @Test
  public void testValidDeleteGuessPass(){
    int id = 1;
    when(guestPassRepo.findGuestPassById(id)).thenReturn(guestPass1);
    guestPassService.deleteGuestPassById(id);


//    PLMSException e = assertThrows(PLMSException.class, () -> guestPassService.getGuestPassById(id));
//    assertEquals(e.getStatus(), HttpStatus.NOT_FOUND);
//    assertEquals(e.getMessage(),   "Guest pass with id: " + id + " does not exist.");

  }

  @Test
  public void testInvalidDeleteMonthlyPass()
  {
    final int invalidPassNumber = 42;
    when(guestPassRepo.findGuestPassById(invalidPassNumber)).thenReturn(null);
    PLMSException e = assertThrows(PLMSException.class, () -> guestPassService.deleteGuestPassById(invalidPassNumber));
    assertEquals(e.getStatus(), HttpStatus.NOT_FOUND);
    assertEquals(e.getMessage(), "Guest pass with id: " + invalidPassNumber + " does not exist.");
  }

  @Test
  public void testHasExceededCapacity(){

  }

  @Test
  public void testCreateValidGuestPass()
  {
    int floorNumber = 1;
    int nrIncrements  = 2;
    int smallCarFee = 15;

    // Initialize parking lot
    ParkingLot parkingLot = new ParkingLot();
    parkingLot.setOpeningTime(Time.valueOf("8:00:00"));
    parkingLot.setClosingTime(Time.valueOf("22:00:00"));
    parkingLot.setSmallSpotFee(smallCarFee);

    // Initailize floor
    Floor floor = new Floor();
    floor.setFloorNumber(floorNumber);
    floor.setIsMemberOnly(false);
    floor.setSmallSpotCapacity(3);
    floor.setLargeSpotCapacity(2);
    floor.setParkingLot(parkingLot);
    when(floorRepo.findFloorByFloorNumber(floorNumber)).thenReturn(floor);

    // Initialize guest pass from dto
    GuestPass guestPass = new GuestPass();
    spotNumber1 = "A24";
    String confirmationCode = "dead";
    boolean isLarge = false;
    guestPass.setSpotNumber("A24");
    guestPass.setIsLarge(isLarge);

    // Change local time to within operating hours
    LocalDateTime currentTime  = LocalDateTime.parse("2023-03-25T09:00:00");
    Time endTime = Time.valueOf("9:30:00");
    when(guestPassRepo.save(guestPass)).thenReturn(guestPass);

    GuestPass createdGuestPass = guestPassService.createGuestPass(guestPass, floorNumber, nrIncrements, currentTime);
    // check to see that we've actually saved the floor in the DB
    verify(guestPassRepo, times(1)).save(guestPass);

    assertNotNull(createdGuestPass);
    assertEquals(spotNumber1, createdGuestPass.getSpotNumber());
    assertEquals(smallCarFee*nrIncrements, createdGuestPass.getFee());
    assertEquals(endTime, createdGuestPass.getEndTime());




  }



}
