package ca.mcgill.ecse321.PLMS.Tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.sql.Time;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ca.mcgill.ecse321.PLMS.model.GuestPass;
import ca.mcgill.ecse321.PLMS.repository.GuestPassRepository;

public class GuestPassRepositoryTests {
    @Autowired
    private GuestPassRepository guestPassRepository;

    @AfterEach
    public void clearDataBase(){
        guestPassRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadGuestPass(){
        //=-=-=-=-=-=- Create object -=-=-=-=-=-=//
        String spotNumber = "A24";
        String licensePlate = "123ABC123";
        Date date = Date.valueOf("2023-02-21");
        Time starTime = Time.valueOf("12:00:00");
        Time endTime = Time.valueOf("18:00:00");
        boolean isLarge = true;
        
        GuestPass guestPass = new GuestPass();
        
        //Set all parameters
        guestPass.setSpotNumber(spotNumber);
        guestPass.setLicensePlate(licensePlate);
        guestPass.setDate(date);
        guestPass.setStartTime(starTime);
        guestPass.setEndTime(endTime);
        guestPass.setIsLarge(isLarge);

        //=-=-=-=-=-=- Save object -=-=-=-=-=-=//
        guestPass = guestPassRepository.save(guestPass);
        int id = guestPass.getId();

        //=-=-=-=-=-=- Read object -=-=-=-=-=-=//
        guestPass = guestPassRepository.findGuestPassById(id);

        //=-=-=-=-=-=- Asserts -=-=-=-=-=-=//
        assertNotNull(guestPass);
        assertEquals(spotNumber, guestPass.getSpotNumber());
        assertEquals(licensePlate, guestPass.getLicensePlate());
        assertEquals(date, guestPass.getDate());
        assertEquals(starTime, guestPass.getStartTime());
        assertEquals(endTime, guestPass.getEndTime());
        assertEquals(isLarge, guestPass.getIsLarge());
    }
}
