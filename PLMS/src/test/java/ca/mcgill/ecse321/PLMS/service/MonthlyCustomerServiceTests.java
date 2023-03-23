package ca.mcgill.ecse321.PLMS.service;

import ca.mcgill.ecse321.PLMS.exception.PLMSException;
import ca.mcgill.ecse321.PLMS.model.MonthlyCustomer;
import ca.mcgill.ecse321.PLMS.repository.MonthlyCustomerRepository;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MonthlyCustomerServiceTests {

    @Mock
    private MonthlyCustomerRepository monthlyCustomerRepository;

    @InjectMocks
    private MonthlyCustomerService monthlyCustomerService;


    @Test
    public void testGetAllMonthlyCustomers() {
        final String email = "john.doe@mcgill.ca";
        final String password = "JohnDoe2002";
        final String name = "John Doe";
        final MonthlyCustomer john = new MonthlyCustomer(email, password, name);

        final String email1 = "jane.doe@mcgill.ca";
        final String password1 = "JaneDoe2002";
        final String name1 = "Jane Doe";
        final MonthlyCustomer jane = new MonthlyCustomer(email1, password1, name1);

        ArrayList<MonthlyCustomer> customers = new ArrayList<>();
        customers.add(john);
        customers.add(jane);


        when(monthlyCustomerRepository.findAll()).thenReturn(customers);
        Iterable<MonthlyCustomer> output = monthlyCustomerService.getAllMonthlyCustomers();
        Iterator<MonthlyCustomer> i = output.iterator();
        assertEquals(i.next(), john);
        assertEquals(i.next(), jane);
    }

    @Test
    public void testGetAllEmptyMonthlyCustomers() {
        ArrayList<MonthlyCustomer> customers = new ArrayList<>();
        when(monthlyCustomerRepository.findAll()).thenReturn(customers);
        PLMSException e = assertThrows(PLMSException.class, () -> monthlyCustomerService.getAllMonthlyCustomers());
        assertEquals(e.getStatus(), HttpStatus.NO_CONTENT);
        assertEquals(e.getMessage(),"There are no monthly customers in the system" );
    }


    @Test
    public void testGetMonthlyCustomerByValidEmail()
    {
        final String email = "john.doe@mcgill.ca";
        final String password = "JohnDoe2002";
        final String name = "John Doe";
        final MonthlyCustomer john = new MonthlyCustomer(email, password, name);
        when(monthlyCustomerRepository.findMonthlyCustomerByEmail(email)).thenReturn(john);

        MonthlyCustomer output = monthlyCustomerService.getMonthlyCustomerByEmail(email);

        assertEquals(output, john);

    }

    @Test
    public void testGetMonthlyCustomerByInvalidEmail()
    {
        final String email = "jane.doe@mcgill.ca";
        final String password = "JohnDoe2002";
        final String name = "John Doe";
        final MonthlyCustomer john = new MonthlyCustomer(email, password, name);
        when(monthlyCustomerRepository.findMonthlyCustomerByEmail(email)).thenReturn(null);

        PLMSException e = assertThrows(PLMSException.class, () -> monthlyCustomerService.getMonthlyCustomerByEmail(email));
        assertEquals(e.getMessage(), "Monthly customer not found.");
        assertEquals(e.getStatus(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void testCreateValidMonthlyCustomerAccount()
    {
        final String email = "john.doe@mcgill.ca";
        final String password = "JohnDoe2002";
        final String name = "John Doe";
        final MonthlyCustomer john = new MonthlyCustomer(email, password, name);
        when(monthlyCustomerRepository.save(john)).thenReturn(john);

        MonthlyCustomer output = monthlyCustomerService.createMonthlyCustomerAccount(john);

        assertNotNull(output);
        assertEquals(john, output);
        verify(monthlyCustomerRepository, times(1)).save(john);

    }

    @Test
    public void testCreateInvalidMonthlyCustomerAccount()
    {
        final String email = "john.doe@mcgill.ca";
        final String password = "JohnDoe2002";
        final String name = "John Doe";
        final MonthlyCustomer john = new MonthlyCustomer(email, password, name);
        when(monthlyCustomerRepository.findMonthlyCustomerByEmail(email)).thenReturn(john);

        final String password2 = "JaneDoe2002";
        final String name2 = "Jane Doe";
        final MonthlyCustomer jane = new MonthlyCustomer(email, password2, name2);
        PLMSException e = assertThrows(PLMSException.class, () -> monthlyCustomerService.createMonthlyCustomerAccount(jane));
        assertEquals(e.getStatus(), HttpStatus.CONFLICT);
        assertEquals(e.getMessage(), "Account with this email already exists");



    }


    



}
