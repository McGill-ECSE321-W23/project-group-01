package ca.mcgill.ecse321.PLMS.service;

import ca.mcgill.ecse321.PLMS.exception.PLMSException;
import ca.mcgill.ecse321.PLMS.model.Owner;
import ca.mcgill.ecse321.PLMS.repository.OwnerRepository;
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
public class OwnerServiceTests {

    @Mock
    private OwnerRepository ownerRepository;

    @InjectMocks
    private OwnerService ownerService;


    @Test
    public void testGetAllOwners() {
        final String email = "john.doe@mcgill.ca";
        final String password = "JohnDoe2002";
        final String name = "John Doe";
        final Owner john = new Owner(email, password, name);

        final String email1 = "jane.doe@mcgill.ca";
        final String password1 = "JaneDoe2002";
        final String name1 = "Jane Doe";
        final Owner jane = new Owner(email1, password1, name1);

        ArrayList<Owner> customers = new ArrayList<>();
        customers.add(john);
        customers.add(jane);


        when(ownerRepository.findAll()).thenReturn(customers);
        Iterable<Owner> output = ownerService.getAllOwners();
        Iterator<Owner> i = output.iterator();
        assertEquals(i.next(), john);
        assertEquals(i.next(), jane);
    }

    @Test
    public void testGetAllEmptyOwners() {
        ArrayList<Owner> customers = new ArrayList<>();
        when(ownerRepository.findAll()).thenReturn(customers);
        PLMSException e = assertThrows(PLMSException.class, () -> ownerService.getAllOwners());
        assertEquals(e.getStatus(), HttpStatus.NO_CONTENT);
        assertEquals(e.getMessage(),"There are no owners in the system" );
    }


    @Test
    public void testGetOwnerByValidEmail()
    {
        final String email = "john.doe@mcgill.ca";
        final String password = "JohnDoe2002";
        final String name = "John Doe";
        final Owner john = new Owner(email, password, name);
        when(ownerRepository.findOwnerByEmail(email)).thenReturn(john);

        Owner output = ownerService.getOwnerByEmail(email);

        assertEquals(output, john);

    }

    @Test
    public void testGetOwnerByInvalidEmail()
    {
        final String email = "jane.doe@mcgill.ca";
        final String password = "JohnDoe2002";
        final String name = "John Doe";
        final Owner john = new Owner(email, password, name);
        when(ownerRepository.findOwnerByEmail(email)).thenReturn(null);

        PLMSException e = assertThrows(PLMSException.class, () -> ownerService.getOwnerByEmail(email));
        assertEquals(e.getMessage(), "Owner not found."); //
        assertEquals(e.getStatus(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void testCreateValidOwnerAccount()
    {
        final String email = "john.doe@mcgill.ca";
        final String password = "JohnDoe2002";
        final String name = "John Doe";
        final Owner john = new Owner(email, password, name);
        when(ownerRepository.save(john)).thenReturn(john);

        Owner output = ownerService.createOwnerAccount(john);

        assertNotNull(output);
        assertEquals(john, output);
        verify(ownerRepository, times(1)).save(john);

    }

    @Test
    public void testCreateInvalidOwnerAccount()
    {
        final String email = "john.doe@mcgill.ca";
        final String password = "JohnDoe2002";
        final String name = "John Doe";
        final Owner john = new Owner(email, password, name);
        when(ownerRepository.findOwnerByEmail(email)).thenReturn(john);

        final String password2 = "JaneDoe2002";
        final String name2 = "Jane Doe";
        final Owner jane = new Owner(email, password2, name2);
        PLMSException e = assertThrows(PLMSException.class, () -> ownerService.createOwnerAccount(jane));
        assertEquals(e.getStatus(), HttpStatus.CONFLICT);
        assertEquals(e.getMessage(), "Owner account with this email already exists");



    }






}
