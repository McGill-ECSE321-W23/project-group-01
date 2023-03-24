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
import ca.mcgill.ecse321.PLMS.model.MonthlyPass;
import ca.mcgill.ecse321.PLMS.model.MonthlyCustomer;

import ca.mcgill.ecse321.PLMS.repository.FloorRepository;
import ca.mcgill.ecse321.PLMS.repository.MonthlyPassRepository;
import ca.mcgill.ecse321.PLMS.repository.MonthlyCustomerRepository;


@SpringBootTest
public class MonthlyPassServiceTests {

  @Mock
  private MonthlyPassRepository monthlyPassRepo;

  @Mock
  private FloorRepository floorRepo;

  @Mock
  private MonthlyCustomerRepository monthlyCustomerRepo;

  @InjectMocks
  private MonthlyPassService monthlyPassService;

  @Test
  public void testGetValidWithoutAccountMonthlyPass(){
    double fee = 50.50;
    String spotNumber = "A24";
    String licensePlate = "123ABC123";
    Date startDate = Date.valueOf("2023-02-21");
    Date endDate = Date.valueOf("2023-03-20");
    boolean isLarge = true;
    String confirmationCode = "NeverGonnaGiveYouUp";
    int id = 1;
    MonthlyPass monthlyPass = new MonthlyPass();
    monthlyPass.setFee(fee);
    monthlyPass.setSpotNumber(spotNumber);
    monthlyPass.setConfirmationCode(confirmationCode);
    monthlyPass.setIsLarge(isLarge);
    monthlyPass.setStartDate(startDate);
    monthlyPass.setEndDate(endDate);
    monthlyPass.setLicensePlate(licensePlate);
    when(monthlyPassRepo.findMonthlyPassById(id)).thenReturn(monthlyPass);
    MonthlyPass output = monthlyPassService.getMonthlyPassById(id);
    assertEquals(output.getSpotNumber(), spotNumber);
    assertEquals(output.getStartDate(), startDate);
    assertEquals(output.getEndDate(), endDate);
  }

  @Test
  public void testGetValidWithAccountMonthlyPass(){
    double fee = 50.50;
    String spotNumber = "A24";
    String licensePlate = "123ABC123";
    Date startDate = Date.valueOf("2023-02-21");
    Date endDate = Date.valueOf("2023-03-20");
    boolean isLarge = true;
    String confirmationCode = "NeverGonnaGiveYouUp";
    int id = 1;


    String email = "rick.roll@gmail.com";
    String password = "intelliJLover123";
    String name = "Samer Abdulkarim";
    MonthlyCustomer monthlyCustomer = new MonthlyCustomer();
    monthlyCustomer.setEmail(email);
    monthlyCustomer.setPassword(password);
    monthlyCustomer.setName(name);

    MonthlyPass monthlyPass = new MonthlyPass();
    monthlyPass.setFee(fee);
    monthlyPass.setSpotNumber(spotNumber);
    monthlyPass.setConfirmationCode(confirmationCode);
    monthlyPass.setIsLarge(isLarge);
    monthlyPass.setStartDate(startDate);
    monthlyPass.setEndDate(endDate);
    monthlyPass.setLicensePlate(licensePlate);
    monthlyPass.setCustomer(monthlyCustomer);

    when(monthlyPassRepo.findMonthlyPassById(id)).thenReturn(monthlyPass);
    MonthlyPass output = monthlyPassService.getMonthlyPassById(id);
    assertEquals(output.getSpotNumber(), spotNumber);
    assertEquals(output.getStartDate(), startDate);
    assertEquals(output.getEndDate(), endDate);
    assertEquals(output.getCustomer(), monthlyCustomer);
  }

  @Test
  public void testGetInvalidMonthlyPass(){
    final int invalidPassNumber = 42;
		  when(monthlyPassRepo.findMonthlyPassById(invalidPassNumber)).thenReturn(null);

		PLMSException e = assertThrows(PLMSException.class,
				() -> monthlyPassService.getMonthlyPassById(invalidPassNumber));
		assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
		assertEquals("Monthly pass with id: " + invalidPassNumber + " does not exist.", e.getMessage());
  }

  @Test
    public void testInvalidDeleteMonthlyPass()
    {
        final int invalidPassNumber = 42;
        when(monthlyPassRepo.findMonthlyPassById(invalidPassNumber)).thenReturn(null);

        PLMSException e = assertThrows(PLMSException.class, () -> monthlyPassService.deleteMonthlyPassById(id));
        assertEquals(e.getStatus(), HttpStatus.NOT_FOUND);
        assertEquals(e.getMessage(), "Monthly pass with id: " + invalidPassNumber + " does not exist.");
    }



  @Test
    public void testValidWithoutAccountDeleteMonthlyPass()
    {
        double fee = 50.50;
        String spotNumber = "A24";
        String licensePlate = "123ABC123";
        Date startDate = Date.valueOf("2023-02-21");
        Date endDate = Date.valueOf("2023-03-20");
        boolean isLarge = true;
        String confirmationCode = "NeverGonnaGiveYouUp";
        int id = 1;
    
    
        MonthlyPass monthlyPass = new MonthlyPass();
        monthlyPass.setFee(fee);
        monthlyPass.setSpotNumber(spotNumber);
        monthlyPass.setConfirmationCode(confirmationCode);
        monthlyPass.setIsLarge(isLarge);
        monthlyPass.setStartDate(startDate);
        monthlyPass.setEndDate(endDate);
        monthlyPass.setLicensePlate(licensePlate);

        when(monthlyPassRepo.findMonthlyPassById(id)).thenReturn(monthlyPass);

        monthlyPassService.deleteMonthlyPassById(id);
    }

  @Test
    public void testValidWithAccountDeleteMonthlyPass()
    {
        double fee = 50.50;
        String spotNumber = "A24";
        String licensePlate = "123ABC123";
        Date startDate = Date.valueOf("2023-02-21");
        Date endDate = Date.valueOf("2023-03-20");
        boolean isLarge = true;
        String confirmationCode = "NeverGonnaGiveYouUp";
        int id = 1;
    
    
        String email = "rick.roll@gmail.com";
        String password = "intelliJLover123";
        String name = "Samer Abdulkarim";
        MonthlyCustomer monthlyCustomer = new MonthlyCustomer();
        monthlyCustomer.setEmail(email);
        monthlyCustomer.setPassword(password);
        monthlyCustomer.setName(name);
    
        MonthlyPass monthlyPass = new MonthlyPass();
        monthlyPass.setFee(fee);
        monthlyPass.setSpotNumber(spotNumber);
        monthlyPass.setConfirmationCode(confirmationCode);
        monthlyPass.setIsLarge(isLarge);
        monthlyPass.setStartDate(startDate);
        monthlyPass.setEndDate(endDate);
        monthlyPass.setLicensePlate(licensePlate);
        monthlyPass.setCustomer(monthlyCustomer);

        when(monthlyPassRepo.findMonthlyPassById(id)).thenReturn(monthlyPass);

        monthlyPassService.deleteMonthlyPassById(id);
    }

    @Test
    public void testGetAllMonthlyPasses(){
        double fee = 50.50;
        String spotNumber = "A24";
        String licensePlate = "123ABC123";
        Date startDate = Date.valueOf("2023-02-21");
        Date endDate = Date.valueOf("2023-03-20");
        boolean isLarge = true;
        String confirmationCode = "NeverGonnaGiveYouUp";
        int id = 1;
    
    
        String email = "rick.roll@gmail.com";
        String password = "intelliJLover123";
        String name = "Samer Abdulkarim";
        MonthlyCustomer monthlyCustomer = new MonthlyCustomer();
        monthlyCustomer.setEmail(email);
        monthlyCustomer.setPassword(password);
        monthlyCustomer.setName(name);
        
        MonthlyPass monthlyPass = new MonthlyPass();
        monthlyPass.setFee(fee);
        monthlyPass.setSpotNumber(spotNumber);
        monthlyPass.setConfirmationCode(confirmationCode);
        monthlyPass.setIsLarge(isLarge);
        monthlyPass.setStartDate(startDate);
        monthlyPass.setEndDate(endDate);
        monthlyPass.setLicensePlate(licensePlate);
        monthlyPass.setCustomer(monthlyCustomer);
        
        double fee2 = 50.50;
        String spotNumber2 = "A25";
        String licensePlate2 = "123ABC124";
        Date startDate2 = Date.valueOf("2023-02-22");
        Date endDate2 = Date.valueOf("2023-03-21");
        boolean isLarge2 = true;
        String confirmationCode2 = "NeverGonnaGiveYouUp";
        int id2 = 2;
        
        MonthlyPass monthlyPass2 = new MonthlyPass();
        monthlyPass.setFee(fee);
        monthlyPass.setSpotNumber(spotNumber);
        monthlyPass.setConfirmationCode(confirmationCode);
        monthlyPass.setIsLarge(isLarge);
        monthlyPass.setStartDate(startDate);
        monthlyPass.setEndDate(endDate);
        monthlyPass.setLicensePlate(licensePlate);

        ArrayList<MonthlyPass> monthlyPasses = new ArrayList<>();
        monthlyPasses.add(monthlyPass);
        monthlyPasses.add(monthlyPass2);


        when(monthlyPassRepo.findAll()).thenReturn(monthlyPasses);
        Iterable<MonthlyPass> output = monthlyPassService.getAllMonthlyPasses();
        Iterator<MonthlyPass> i = output.iterator();
        MonthlyPass outputMonthlyPass = i.next();
        assertEquals(outputMonthlyPass.getSpotNumber(), spotNumber);
        assertEquals(outputMonthlyPass.getStartDate(), starDate);
        assertEquals(outputMonthlyPass.getEndDate(), endDate);
        assertEquals(output.getCustomer(), monthlyCustomer);

        outputMonthlyPass = i.next();
        assertEquals(outputMonthlyPass.getSpotNumber(), spotNumber2);
        assertEquals(outputMonthlyPass.getStartDate(), starDate2);
        assertEquals(outputMonthlyPass.getEndDate(), endDate2);
        assertEquals(output.getCustomer(), null);

    }

    @Test
    public void testGetMonthlyPassesByMonthlyCustomer(){
        double fee = 50.50;
        String spotNumber = "A24";
        String licensePlate = "123ABC123";
        Date startDate = Date.valueOf("2023-02-21");
        Date endDate = Date.valueOf("2023-03-20");
        boolean isLarge = true;
        String confirmationCode = "NeverGonnaGiveYouUp";
        int id = 1;

        double fee2 = 50.50;
        String spotNumber2 = "A25";
        String licensePlate2 = "123ABC124";
        Date startDate2 = Date.valueOf("2023-02-22");
        Date endDate2 = Date.valueOf("2023-03-21");
        boolean isLarge2 = true;
        String confirmationCode2 = "NeverGonnaGiveYouUp";
        int id2 = 2;
    
    
        String email = "rick.roll@gmail.com";
        String password = "intelliJLover123";
        String name = "Samer Abdulkarim";
        MonthlyCustomer monthlyCustomer = new MonthlyCustomer();
        monthlyCustomer.setEmail(email);
        monthlyCustomer.setPassword(password);
        monthlyCustomer.setName(name);
        
        MonthlyPass monthlyPass = new MonthlyPass();
        monthlyPass.setFee(fee);
        monthlyPass.setSpotNumber(spotNumber);
        monthlyPass.setConfirmationCode(confirmationCode);
        monthlyPass.setIsLarge(isLarge);
        monthlyPass.setStartDate(startDate);
        monthlyPass.setEndDate(endDate);
        monthlyPass.setLicensePlate(licensePlate);
        monthlyPass.setCustomer(monthlyCustomer);

        MonthlyPass monthlyPass2 = new MonthlyPass();
        monthlyPass2.setFee(fee2);
        monthlyPass2.setSpotNumber(spotNumber2);
        monthlyPass2.setConfirmationCode(confirmationCode2);
        monthlyPass2.setIsLarge(isLarge2);
        monthlyPass2.setStartDate(startDate2);
        monthlyPass2.setEndDate(endDate2);
        monthlyPass2.setLicensePlate(licensePlate2);
        monthlyPass2.setCustomer(monthlyCustomer);

        ArrayList<MonthlyPass> monthlyPasses = new ArrayList<>();
        monthlyPasses.add(monthlyPass1);
        monthlyPasses.add(monthlyPass2);

        when(MonthlyPassRepo.findAll()).thenReturn(monthlyPasses);
        when(monthlyCustomerRepo.findMonthlyCustomerByEmail(email)).thenReturn(monthlyCustomer);

        ArrayList<MonthlyPass> output = (ArrayList<MonthlyPass>) monthlyPassService.getMonthlyPassesByMonthlyCustomer(email);
        Iterator<MonthlyPass> i = output.iterator();
        MonthlyPass outputMonthlyPass = i.next();

        assertEquals(outputMonthlyPass.getSpotNumber(), spotNumber);
        assertEquals(outputMonthlyPass.getStartDate(), starDate);
        assertEquals(outputMonthlyPass.getEndDate(), endDate);
        assertEquals(output.getCustomer(), monthlyCustomer);

        outputMonthlyPass = i.next();
        assertEquals(outputMonthlyPass.getSpotNumber(), spotNumber2);
        assertEquals(outputMonthlyPass.getStartDate(), starDate2);
        assertEquals(outputMonthlyPass.getEndDate(), endDate2);
        assertEquals(output.getCustomer(), monthlyCustomer);
    }

    @Test
    public void testGetInvalidMonthlyPassesByMonthlyCustomer1(){
        double fee = 50.50;
        String spotNumber = "A24";
        String licensePlate = "123ABC123";
        Date startDate = Date.valueOf("2023-02-21");
        Date endDate = Date.valueOf("2023-03-20");
        boolean isLarge = true;
        String confirmationCode = "NeverGonnaGiveYouUp";
        int id = 1;

        double fee2 = 50.50;
        String spotNumber2 = "A25";
        String licensePlate2 = "123ABC124";
        Date startDate2 = Date.valueOf("2023-02-22");
        Date endDate2 = Date.valueOf("2023-03-21");
        boolean isLarge2 = true;
        String confirmationCode2 = "NeverGonnaGiveYouUp";
        int id2 = 2;
    
    
        String invalidEmail = "rick.roll@gmail.com";
        
        MonthlyPass monthlyPass = new MonthlyPass();
        monthlyPass.setFee(fee);
        monthlyPass.setSpotNumber(spotNumber);
        monthlyPass.setConfirmationCode(confirmationCode);
        monthlyPass.setIsLarge(isLarge);
        monthlyPass.setStartDate(startDate);
        monthlyPass.setEndDate(endDate);
        monthlyPass.setLicensePlate(licensePlate);

        MonthlyPass monthlyPass2 = new MonthlyPass();
        monthlyPass2.setFee(fee2);
        monthlyPass2.setSpotNumber(spotNumber2);
        monthlyPass2.setConfirmationCode(confirmationCode2);
        monthlyPass2.setIsLarge(isLarge2);
        monthlyPass2.setStartDate(startDate2);
        monthlyPass2.setEndDate(endDate2);
        monthlyPass2.setLicensePlate(licensePlate2);

        ArrayList<MonthlyPass> monthlyPasses = new ArrayList<>();
        monthlyPasses.add(monthlyPass1);
        monthlyPasses.add(monthlyPass2);

        when(MonthlyPassRepo.findAll()).thenReturn(monthlyPasses);
        when(monthlyCustomerRepo.findMonthlyCustomerByEmail(email)).thenReturn(null);

        
        PLMSException e = assertThrows(PLMSException.class, () -> monthlyPassService.getMonthlyPassesByMonthlyCustomer(email));
        assertEquals(e.getStatus(), HttpStatus.NOT_FOUND);
        assertEquals(e.getMessage(), "The account with email " + email + " does not exist.");
    }

    @Test
    public void testGetInvalidMonthlyPassesByMonthlyCustomer2(){
        
    
        String email = "rick.roll@gmail.com";
        String password = "intelliJLover123";
        String name = "Samer Abdulkarim";
        MonthlyCustomer monthlyCustomer = new MonthlyCustomer();
        monthlyCustomer.setEmail(email);
        monthlyCustomer.setPassword(password);
        monthlyCustomer.setName(name);

        String email2 = "no.pass@gmail.com";
        String password2 = "VsCodeLover123";
        String name2 = "Karl Bridi";
        MonthlyCustomer monthlyCustomer2 = new MonthlyCustomer();
        monthlyCustomer2.setEmail(email2);
        monthlyCustomer2.setPassword(password2);
        monthlyCustomer2.setName(name2);
        
        MonthlyPass monthlyPass = new MonthlyPass();
        monthlyPass.setFee(fee);
        monthlyPass.setSpotNumber(spotNumber);
        monthlyPass.setConfirmationCode(confirmationCode);
        monthlyPass.setIsLarge(isLarge);
        monthlyPass.setStartDate(startDate);
        monthlyPass.setEndDate(endDate);
        monthlyPass.setLicensePlate(licensePlate);
        monthlyPass.setCustomer(monthlyCustomer);

        ArrayList<MonthlyPass> monthlyPasses = new ArrayList<>();
        monthlyPasses.add(monthlyPass1);

        when(MonthlyPassRepo.findAll()).thenReturn(monthlyPasses);
        when(monthlyCustomerRepo.findMonthlyCustomerByEmail(email2)).thenReturn(monthlyCustomer2);

        PLMSException e = assertThrows(PLMSException.class, () -> monthlyPassService.getMonthlyPassesByMonthlyCustomer(email2));
        assertEquals(e.getStatus(), HttpStatus.NOT_FOUND);
        assertEquals(e.getMessage(), "There are no monthly passes for " + monthlyCustomer2.getEmail());
    }

    


    











    
    
}
