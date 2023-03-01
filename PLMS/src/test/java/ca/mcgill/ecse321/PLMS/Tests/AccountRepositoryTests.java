package ca.mcgill.ecse321.PLMS.Tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.PLMS.model.Account;
import ca.mcgill.ecse321.PLMS.model.Employee;
import ca.mcgill.ecse321.PLMS.model.MonthlyCustomer;
import ca.mcgill.ecse321.PLMS.model.Owner;
import ca.mcgill.ecse321.PLMS.repository.AccountRepository;
import ca.mcgill.ecse321.PLMS.repository.EmployeeRepository;
import ca.mcgill.ecse321.PLMS.repository.MonthlyCustomerRepository;
import ca.mcgill.ecse321.PLMS.repository.OwnerRepository;

@SpringBootTest
public class AccountRepositoryTests {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private MonthlyCustomerRepository monthlyCustomerRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private OwnerRepository ownerRepository;

    @BeforeEach
    @AfterEach
    public void clearDataBase(){
        monthlyCustomerRepository.deleteAll();
        employeeRepository.deleteAll();
        ownerRepository.deleteAll();
        accountRepository.deleteAll();

    }

    @Test
    public void testPersistAndLoadCustomerAccount(){
        //=-=-=-=-=-=- Create monthly customer -=-=-=-=-=-=//
        String mEmail = "teta.saniya@teta.com";
        String mPassword = "PasswordSoSuperSecured12345";
        String mName = "Saniya";
    
        MonthlyCustomer monthlyCustomer = new MonthlyCustomer();
        //Set all parameters
        monthlyCustomer.setEmail(mEmail);
        monthlyCustomer.setPassword(mPassword);
        monthlyCustomer.setName(mName);

        //=-=-=-=-=-=- Save account -=-=-=-=-=-=//
        monthlyCustomer = accountRepository.save(monthlyCustomer);
        monthlyCustomer = monthlyCustomerRepository.save(monthlyCustomer);
        String accountEmail = monthlyCustomer.getEmail();

        //=-=-=-=-=-=- Read account -=-=-=-=-=-=//
        monthlyCustomer = (MonthlyCustomer) accountRepository.findAccountByEmail(accountEmail);

        //=-=-=-=-=-=- Asserts -=-=-=-=-=-=//
        assertNotNull(monthlyCustomer);
        assertEquals(mEmail, monthlyCustomer.getEmail());
        assertEquals(mPassword, monthlyCustomer.getPassword());
        assertEquals(mName, monthlyCustomer.getName());
    }
    @Test
    void testPersistAndLoadEmployeeAccount(){
        //=-=-=-=-=-=- Create employee -=-=-=-=-=-=//
        String eEmail = "hello123@hi.com";
        String ePassword = "weeeee";
        String eName = "Samer";

        Employee employee = new Employee();
        //Set all parameters
        employee.setEmail(eEmail);
        employee.setPassword(ePassword);
        employee.setName(eName);

        //=-=-=-=-=-=- Save account to both repositories -=-=-=-=-=-=//
        employee = accountRepository.save(employee);
        employee = employeeRepository.save(employee);
        String eAccountEmail = employee.getEmail();

        //=-=-=-=-=-=- Read account -=-=-=-=-=-=//
        employee = (Employee) accountRepository.findAccountByEmail(eAccountEmail);

        //=-=-=-=-=-=- Asserts -=-=-=-=-=-=//
        assertNotNull(employee);
        assertEquals(eEmail, employee.getEmail());
        assertEquals(ePassword, employee.getPassword());
        assertEquals(eName, employee.getName());
    }

    @Test
    void testPersistAndLoadOwnerAccount(){
        //=-=-=-=-=-=- Create owner -=-=-=-=-=-=//
        String oEmail = "owner@dorsia.com";
        String oPassword = "ihavetoreturnsomevideotapes";
        String oName = "Patrick Bateman";
    
        Owner owner = new Owner();
        //Set all parameters
        owner.setEmail(oEmail);
        owner.setPassword(oPassword);
        owner.setName(oName);

        //=-=-=-=-=-=- Save account to both repositories -=-=-=-=-=-=//
        owner = accountRepository.save(owner);
        owner = ownerRepository.save(owner);
        String oAccountEmail = owner.getEmail();

        //=-=-=-=-=-=- Read account -=-=-=-=-=-=//
        owner = (Owner) accountRepository.findAccountByEmail(oAccountEmail);

        //=-=-=-=-=-=- Asserts -=-=-=-=-=-=//
        assertNotNull(owner);
        assertEquals(oEmail, owner.getEmail());
        assertEquals(oPassword, owner.getPassword());
        assertEquals(oName, owner.getName());
    }
}
