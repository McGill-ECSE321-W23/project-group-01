package ca.mcgill.ecse321.PLMS.Tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ca.mcgill.ecse321.PLMS.model.MonthlyCustomer;
import ca.mcgill.ecse321.PLMS.repository.MonthlyCustomerRepository;
import ca.mcgill.ecse321.PLMS.model.MonthlyPass;
import ca.mcgill.ecse321.PLMS.repository.MonthlyPassRepository;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MonthlyPassRepositoryTests {
    @Autowired
    private MonthlyPassRepository monthlyPassRepository;
    @Autowired
    private MonthlyCustomerRepository monthlyCustomerRepository;

    @BeforeEach
    @AfterEach
    public void clearDataBase(){
        monthlyPassRepository.deleteAll();
        monthlyCustomerRepository.deleteAll();
    }    

    @Test
    public void testPersistAndLoadMonthlyPass(){
        //=-=-=-=-=-=- Create MonthlyPass -=-=-=-=-=-=//
        Date startDate = Date.valueOf("2023-02-21");
        Date endDate = Date.valueOf("2023-04-21");

        MonthlyPass monthlyPass = new MonthlyPass();

        //=-=-=-=-=-=- Create MonthlyCustomer -=-=-=-=-=-=//
        String email = "example@email.com";
        
        MonthlyCustomer monthlyCustomer = new MonthlyCustomer();
        
        //Set all parameters
        monthlyCustomer.setEmail(email);

        monthlyPass.setStartDate(startDate);
        monthlyPass.setEndDate(endDate);
        monthlyPass.setCustomer(monthlyCustomer);

        //=-=-=-=-=-=- Save object -=-=-=-=-=-=//
        monthlyCustomer = monthlyCustomerRepository.save(monthlyCustomer);
        String emailInDatabase = monthlyCustomer.getEmail();

        monthlyPass = monthlyPassRepository.save(monthlyPass);
        int id = monthlyPass.getId();

        //=-=-=-=-=-=- Read object -=-=-=-=-=-=//
        monthlyPass = monthlyPassRepository.findMonthlyPassById(id);

        //=-=-=-=-=-=- Asserts -=-=-=-=-=-=//
        assertNotNull(monthlyPass);
        assertEquals(startDate, monthlyPass.getStartDate());
        assertEquals(endDate, monthlyPass.getEndDate());
        assertEquals(email, monthlyCustomer.getEmail());
    }
}
