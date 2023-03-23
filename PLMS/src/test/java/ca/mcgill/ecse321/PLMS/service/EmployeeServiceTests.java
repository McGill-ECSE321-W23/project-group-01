package ca.mcgill.ecse321.PLMS.service;

import ca.mcgill.ecse321.PLMS.exception.PLMSException;
import ca.mcgill.ecse321.PLMS.model.Employee;
import ca.mcgill.ecse321.PLMS.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class EmployeeServiceTests {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;


    @Test
    public void testGetAllEmployees() {
        final String email = "john.doe@mcgill.ca";
        final String password = "JohnDoe2002";
        final String name = "John Doe";
        final double wage = 12.0;
        final String description = "Parking Cashier";
        final Employee john = new Employee(email, password, name, description, wage);

        final String email1 = "jane.doe@mcgill.ca";
        final String password1 = "JaneDoe2002";
        final String name1 = "Jane Doe";
        final double wage1 = 12.0; //# say no to gender pay gap
        final String description1 = "Mechanic";
        final Employee jane = new Employee(email1, password1, name1, description1, wage1);

        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(john);
        employees.add(jane);


        when(employeeRepository.findAll()).thenReturn(employees);
        Iterable<Employee> output = employeeService.getAllEmployees();
        Iterator<Employee> i = output.iterator();
        assertEquals(i.next(), john);
        assertEquals(i.next(), jane);
    }

    @Test
    public void testGetAllEmptyEmployees() {
        ArrayList<Employee> customers = new ArrayList<>();
        when(employeeRepository.findAll()).thenReturn(customers);
        PLMSException e = assertThrows(PLMSException.class, () -> employeeService.getAllEmployees());
        assertEquals(e.getStatus(), HttpStatus.NO_CONTENT);
        assertEquals(e.getMessage(),"There are no employees in the system" );
    }


    @Test
    public void testGetEmployeeByValidEmail()
    {
        final String email = "john.doe@mcgill.ca";
        final String password = "JohnDoe2002";
        final String name = "John Doe";
        final double wage = 12.0;
        final String description = "Parking Cashier";
        final Employee john = new Employee(email, password, name, description, wage);

        when(employeeRepository.findEmployeeByEmail(email)).thenReturn(john);

        Employee output = employeeService.getEmployeeByEmail(email);

        assertEquals(output, john);

    }

    @Test
    public void testGetEmployeeByInvalidEmail()
    {
        final String email = "jane.doe@mcgill.ca";
        final String password = "JohnDoe2002";
        final String name = "John Doe";
        final double wage = 12.0;
        final String description = "Parking Cashier";
        final Employee john = new Employee(email, password, name, description, wage);

        when(employeeRepository.findEmployeeByEmail(email)).thenReturn(null);

        PLMSException e = assertThrows(PLMSException.class, () -> employeeService.getEmployeeByEmail(email));
        assertEquals(e.getMessage(), "Employee not found.");
        assertEquals(e.getStatus(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void testCreateValidEmployeeAccount()
    {
        final String email = "john.doe@mcgill.ca";
        final String password = "JohnDoe2002";
        final String name = "John Doe";
        final double wage = 12.0;
        final String description = "Parking Cashier";
        final Employee john = new Employee(email, password, name, description, wage);

        when(employeeRepository.save(john)).thenReturn(john);

        Employee output = employeeService.createEmployeeAccount(john);

        assertNotNull(output);
        assertEquals(john, output);

    }

    @Test
    public void testCreateInvalidByDuplicateEmailEmployeeAccount()
    {
        final String email = "john.doe@mcgill.ca";
        final String password = "JohnDoe2002";
        final String name = "John Doe";
        final double wage = 12.0;
        final String description = "Parking Cashier";
        final Employee john = new Employee(email, password, name, description, wage);

        when(employeeRepository.findEmployeeByEmail(email)).thenReturn(john);

        final String password1 = "JaneDoe2002";
        final String name1 = "Jane Doe";
        final double wage1 = 12.0; //# say no to gender pay gap
        final String description1 = "Mechanic";
        final Employee jane = new Employee(email, password1, name1, description1, wage1);

        PLMSException e = assertThrows(PLMSException.class, () -> employeeService.createEmployeeAccount(jane));
        assertEquals(e.getStatus(), HttpStatus.CONFLICT);
        assertEquals(e.getMessage(), "Employee account with this email already exists");
    }

    @Test
    public void testCreateInvalidHourlyWageEmployeeAccount()
    {
        final String email = "john.doe@mcgill.ca";
        final String password = "JohnDoe2002";
        final String name = "John Doe";
        final double wage = 0;
        final String description = "Parking Cashier";
        final Employee john = new Employee(email, password, name, description, wage);

        PLMSException e = assertThrows(PLMSException.class, () -> employeeService.createEmployeeAccount(john));
        assertEquals(e.getStatus(), HttpStatus.BAD_REQUEST);
        assertEquals(e.getMessage(), "Hourly wage must be positive.");
    }

    @Test
    public void testUpdateValidWageEmployeeAccount()
    {
        final String email = "john.doe@mcgill.ca";
        final String password = "JohnDoe2002";
        final String name = "John Doe";
        final double wage = 12.0;
        final String description = "Parking Cashier";
        final Employee john = new Employee(email, password, name, description, wage);

        when(employeeRepository.findEmployeeByEmail(email)).thenReturn(john);

        final String password1 = "JaneDoe2002";
        final String name1 = "Jane Doe";
        final double wage1 = 14.0; //# say no to gender pay gap
        final String description1 = "Mechanic";
        final Employee jane = new Employee(email, password1, name1, description1, wage1);
        when(employeeRepository.save(john)).thenReturn(jane);
        Employee output = employeeService.updateEmployee(jane);

        assertEquals(output, jane);


    }

    @Test
    public void testUpdateInvalidWageEmployeeAccount()
    {
        final String email = "john.doe@mcgill.ca";
        final String password = "JohnDoe2002";
        final String name = "John Doe";
        final double wage = 12.0;
        final String description = "Parking Cashier";
        final Employee john = new Employee(email, password, name, description, wage);

        when(employeeRepository.findEmployeeByEmail(email)).thenReturn(john);

        final String password1 = "JaneDoe2002";
        final String name1 = "Jane Doe";
        final double wage1 = -12.0; //# say no to gender pay gap
        final String description1 = "Mechanic";
        final Employee jane = new Employee(email, password1, name1, description1, wage1);

        PLMSException e = assertThrows(PLMSException.class, () -> employeeService.updateEmployee(jane));
        assertEquals(e.getStatus(), HttpStatus.BAD_REQUEST);
        assertEquals(e.getMessage(), "Hourly wage must be positive.");
    }

    @Test
    public void testUpdateInvalidEmailEmployeeAccount() {
        final String email = "john.doe@mcgill.ca";
        final String password = "JohnDoe2002";
        final String name = "John Doe";
        final double wage = 12.0;
        final String description = "Parking Cashier";
        final Employee john = new Employee(email, password, name, description, wage);

        when(employeeRepository.findEmployeeByEmail(email)).thenReturn(null);

        PLMSException e = assertThrows(PLMSException.class, () -> employeeService.updateEmployee(john));
        assertEquals(e.getStatus(), HttpStatus.NOT_FOUND);
        assertEquals(e.getMessage(), "Employee not found.");
    }

    @Test
    public void testDeleteEmployeeAccount() {
        final String email = "john.doe@mcgill.ca";
        final String password = "JohnDoe2002";
        final String name = "John Doe";
        final double wage = 12.0;
        final String description = "Parking Cashier";
        final Employee john = new Employee(email, password, name, description, wage);

        when(employeeRepository.findEmployeeByEmail(email)).thenReturn(john);
        employeeService.deleteEmployeeAccount(email);
        verify(employeeRepository, times(1)).delete(argThat((Employee e) -> email.equals(e.getEmail())));
        verify(employeeRepository, times(0)).delete(argThat((Employee e) -> !email.equals(e.getEmail())));

    }

    @Test
    public void testInvalidDeleteEmployeeAccount() {
        final String email = "john.doe@mcgill.ca";
        final String password = "JohnDoe2002";
        final String name = "John Doe";
        final double wage = 12.0;
        final String description = "Parking Cashier";
        final Employee john = new Employee(email, password, name, description, wage);

        when(employeeRepository.findEmployeeByEmail(email)).thenReturn(null);
        PLMSException e = assertThrows(PLMSException.class, () -> employeeService.deleteEmployeeAccount(email));

        assertEquals(e.getMessage(), "Employee not found.");
        assertEquals(e.getStatus(), HttpStatus.NOT_FOUND);

    }







}

