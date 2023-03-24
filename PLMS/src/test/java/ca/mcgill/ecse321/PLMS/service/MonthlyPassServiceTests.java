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
  private MonthlyCustomerRepository MonthlyCustomerRepository;

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
    MonthlyPass output = MonthlyPassService.getMonthlyPassById(id);
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
    MonthlyPass output = MonthlyPassService.getMonthlyPassById(id);
    assertEquals(output.getSpotNumber(), spotNumber);
    assertEquals(output.getStartDate(), startDate);
    assertEquals(output.getEndDate(), endDate);
  }
    
    
}
