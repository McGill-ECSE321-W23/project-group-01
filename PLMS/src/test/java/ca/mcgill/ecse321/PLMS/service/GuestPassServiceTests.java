package ca.mcgill.ecse321.PLMS.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Iterator;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
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

  @InjectMocks
  private GuestPassService guestPassService;

  @Test
  public void testGetValidGuestPass(){
    double fee = 50.50;
    String spotNumber = "A24";
    String licensePlate = "123ABC123";
    Date date = Date.valueOf("2023-02-21");
    Time starTime = Time.valueOf("12:00:00");
    Time endTime = Time.valueOf("18:00:00");
    boolean isLarge = true;
    String confirmationCode = "NeverGonnaGiveYouUp";
    int id  = 1;
    GuestPass guestPass = new GuestPass();
    guestPass.setFee(fee);
    guestPass.setSpotNumber(spotNumber);
    guestPass.setConfirmationCode(confirmationCode);
    guestPass.setIsLarge(isLarge);
    guestPass.setDate(date);
    guestPass.setStartTime(starTime);
    guestPass.setEndTime(endTime);
    when(guestPassRepo.findGuestPassById(id)).thenReturn(guestPass);
    GuestPass output = guestPassService.getGuestPassById(id);
    assertEquals(output.getSpotNumber(), spotNumber);
    assertEquals(output.getStartTime(), starTime);
    assertEquals(output.getEndTime(), endTime);


        
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
    public void testInvalidDeleteGuestPass()
    {
        final int id = 42;
        when(guestPassRepo.findGuestPassById(id)).thenReturn(null);
        PLMSException e = assertThrows(PLMSException.class, () -> guestPassService.deleteGuestPassById(id));
        assertEquals(e.getStatus(), HttpStatus.NOT_FOUND);
        assertEquals(e.getMessage(), "Guest pass with id: " + id + " does not exist.");
    }

    @Test
    public void testValidDeleteGuestPass()
    {
      double fee = 50.50;
      String spotNumber = "A24";
      String licensePlate = "123ABC123";
      Date date = Date.valueOf("2023-02-21");
      Time starTime = Time.valueOf("12:00:00");
      Time endTime = Time.valueOf("18:00:00");
      boolean isLarge = true;
      String confirmationCode = "NeverGonnaGiveYouUp";
      int id  = 1;
      GuestPass guestPass = new GuestPass();
      guestPass.setFee(fee);
      guestPass.setSpotNumber(spotNumber);
      guestPass.setConfirmationCode(confirmationCode);
      guestPass.setIsLarge(isLarge);
      guestPass.setDate(date);
      guestPass.setStartTime(starTime);
      guestPass.setEndTime(endTime);
        when(guestPassRepo.findGuestPassById(id)).thenReturn(guestPass);

        guestPassService.deleteGuestPassById(id);

    }

    @Test
    public void testGetAllGuestPasses(){
      double fee = 50.50;
      String spotNumber = "A24";
      String licensePlate = "123ABC123";
      Date date = Date.valueOf("2023-02-21");
      Time starTime = Time.valueOf("12:00:00");
      Time endTime = Time.valueOf("18:00:00");
      boolean isLarge = true;
      String confirmationCode = "NeverGonnaGiveYouUp";
      int id  = 1;
      GuestPass guestPass = new GuestPass();
      guestPass.setFee(fee);
      guestPass.setSpotNumber(spotNumber);
      guestPass.setConfirmationCode(confirmationCode);
      guestPass.setIsLarge(isLarge);
      guestPass.setDate(date);
      guestPass.setStartTime(starTime);
      guestPass.setEndTime(endTime);
      ArrayList<GuestPass> guestPasses = new ArrayList<>();
      guestPasses.add(guestPass);


        when(guestPassRepo.findAll()).thenReturn(guestPasses);
        Iterable<GuestPass> output = guestPassService.getAllGuestPasses();
        Iterator<GuestPass> i = output.iterator();
        GuestPass outputGuestPass = i.next();
        assertEquals(outputGuestPass.getSpotNumber(), spotNumber);
    assertEquals(outputGuestPass.getStartTime(), starTime);
    assertEquals(outputGuestPass.getEndTime(), endTime);
    }

    @Test
    public void testGetGuestPassesByFloor(){
      double fee = 50.50;
      String spotNumber = "A24";
      String licensePlate = "123ABC123";
      Date date = Date.valueOf("2023-02-21");
      Time starTime = Time.valueOf("12:00:00");
      Time endTime = Time.valueOf("18:00:00");
      boolean isLarge = true;
      String confirmationCode = "NeverGonnaGiveYouUp";
      int id  = 1;
      GuestPass guestPass = new GuestPass();
      guestPass.setFee(fee);
      guestPass.setSpotNumber(spotNumber);
      guestPass.setConfirmationCode(confirmationCode);
      guestPass.setIsLarge(isLarge);
      guestPass.setDate(date);
      guestPass.setStartTime(starTime);
      guestPass.setEndTime(endTime);

      double fee2 = 50.50;
      String spotNumber2 = "A24";
      String licensePlate2 = "123ABC123";
      Date date2 = Date.valueOf("2023-02-21");
      Time starTime2 = Time.valueOf("12:00:00");
      Time endTime2 = Time.valueOf("18:00:00");
      boolean isLarge2 = true;
      String confirmationCode2 = "NeverGonnaGiveYouUp";
      int id2  = 2;
      GuestPass guestPass2 = new GuestPass();
      guestPass2.setFee(fee2);
      guestPass2.setSpotNumber(spotNumber2);
      guestPass2.setConfirmationCode(confirmationCode2);
      guestPass2.setIsLarge(isLarge2);
      guestPass2.setDate(date2);
      guestPass2.setStartTime(starTime2);
      guestPass2.setEndTime(endTime2);

      Floor floor = new Floor();
      floor.setFloorNumber(1);
      Floor floor2 = new Floor();
      floor2.setFloorNumber(2);
      guestPass.setFloor(floor);
      guestPass2.setFloor(floor2);
      ArrayList<GuestPass> guestPasses = new ArrayList<>();
      guestPasses.add(guestPass);
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
    public void testGetAllGuestPassesInvalid1(){
      // test for the case in which there is no floor that exists with this number
      when(floorRepo.findFloorByFloorNumber(1)).thenReturn(null);
      PLMSException e = assertThrows(PLMSException.class, () -> guestPassService.getGuestPassesByFloor(1));
        assertEquals(e.getStatus(), HttpStatus.NOT_FOUND);
        assertEquals(e.getMessage(), "The floor with floor number " + 1 + " does not exist.");
    }

    @Test
    public void testGetAllGuestPassesInvalid2(){
      GuestPass guestPass2 = new GuestPass();
      double fee2 = 50.50;
      String spotNumber2 = "A24";
      String licensePlate2 = "123ABC123";
      Date date2 = Date.valueOf("2023-02-21");
      Time starTime2 = Time.valueOf("12:00:00");
      Time endTime2 = Time.valueOf("18:00:00");
      boolean isLarge2 = true;
      String confirmationCode2 = "NeverGonnaGiveYouUp";
      int id2  = 2;
      guestPass2.setFee(fee2);
      guestPass2.setSpotNumber(spotNumber2);
      guestPass2.setConfirmationCode(confirmationCode2);
      guestPass2.setIsLarge(isLarge2);
      guestPass2.setDate(date2);
      guestPass2.setStartTime(starTime2);
      guestPass2.setEndTime(endTime2);
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

}
