package ca.mcgill.ecse321.PLMS.integration;

import ca.mcgill.ecse321.PLMS.controller.GuestPassController;
import ca.mcgill.ecse321.PLMS.dto.*;
import ca.mcgill.ecse321.PLMS.model.Floor;
import ca.mcgill.ecse321.PLMS.model.GuestPass;
import ca.mcgill.ecse321.PLMS.model.ParkingLot;
import ca.mcgill.ecse321.PLMS.repository.FloorRepository;
import ca.mcgill.ecse321.PLMS.repository.GuestPassRepository;
import ca.mcgill.ecse321.PLMS.repository.ParkingLotRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// Reuse the same class for all the tests (instead of creating a new class each time).
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
// Ensure the tests are run in the right order (e.g., POST before GET)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GuestPassIntegrationTests {

    private class GuestPassFixture {
        public  static Integer id = 1;
        public static String spotNumber = "A24";
        public static String confirmationCode = "NeverGonnaGiveYouUp";
        public static String licensePlate = "12345678";
        public static Boolean isLarge = true;
        public static Integer floorNumber = 0;
        public static Integer nrFifteenMinuteIntervals = 3;

        public static GuestPassRequestDto createValidGuestPass(){
            GuestPassRequestDto request = new GuestPassRequestDto();

            request.setSpotNumber(spotNumber);
            request.setConfirmationCode(confirmationCode);
            request.setLicensePlate(licensePlate);
            request.setIsLarge(isLarge);
            request.setFloorNumber(floorNumber);
            request.setNumberOfFifteenMinuteIncrements(nrFifteenMinuteIntervals);

            return request;
        }
        public void setSpotNumber(String spotNumber) {
            this.spotNumber = spotNumber;
        }

        public void setConfirmationCode(String confirmationCode) {
            this.confirmationCode = confirmationCode;
        }

        public void setLicensePlate(String licensePlate) {
            this.licensePlate = licensePlate;
        }

        public void setLarge(Boolean isLarge) {
            this.isLarge = isLarge;
        }


        public void setFloor(Integer floorNumber) {
            this.floorNumber = floorNumber;
        }

    }

    private class FixedValidFloor {

        public static final int floorNumber = 0;
        public static final boolean isMemberOnly = false;
        public static final int largeSpotCapacity = 10;
        public static final int smallSpotCapacity = 10;

        public static final boolean isMemberOnlyUpdated = false;
        public static final int largeSpotCapacityUpdated = 30;
        public static final int smallSpotCapacityUpdated = 40;

        public static final int secondFloorNumber = 2;

        public static Floor createValidFloor() {
            Floor validFloor = new Floor(floorNumber, largeSpotCapacity, smallSpotCapacity, isMemberOnly);
            return validFloor;
        }

    }

    private GuestPassFixture guestPassFixture;

    @Autowired
    private FloorRepository floorRepository;

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private GuestPassRepository guestPassRepository;

    @Autowired
    private TestRestTemplate client;

    @BeforeAll
    public void setupTestFixture(){
        this.guestPassFixture = new GuestPassFixture();
    }

    @BeforeAll
    @AfterAll
    public void clearDatabase() {
        floorRepository.deleteAll();
        parkingLotRepository.deleteAll();
        guestPassRepository.deleteAll();
    }

//    private GuestPassRequestDto setRequest(String spotNumber, String confirmationCode,
//                                           String licensePlate, int floorNumber, boolean isLarge, int numberOfFifteenMinuteIncrements) {
//        ParkingLot parkingLot = new ParkingLot(Time.valueOf("8:00:00"), Time.valueOf("20:00:00"), 15, 10, 250, 250);
//        GuestPassRequestDto request = new GuestPassRequestDto();
//
//        request.setSpotNumber(spotNumber);
//        request.setConfirmationCode(confirmationCode);
//        request.setLicensePlate(licensePlate);
//        request.setIsLarge(isLarge);
//        request.setFloorNumber(floorNumber);
//        request.setNumberOfFifteenMinuteIncrements(numberOfFifteenMinuteIncrements);
//        return request;
//
//    }

    @Test
    @Order(0)
    public void testCreateGuestPass(){
        ParkingLot parkingLot = new ParkingLot(Time.valueOf("0:00:00"), Time.valueOf("23:59:59"), 15, 10, 250, 250);
        parkingLotRepository.save(parkingLot);
        Floor validFloor = FixedValidFloor.createValidFloor();
        validFloor.setParkingLot(parkingLot);
        floorRepository.save(validFloor);

        GuestPassRequestDto request = GuestPassFixture.createValidGuestPass();
        request.setIsLarge(true);
        ResponseEntity<GuestPassResponseDto> response = client.postForEntity("/guestPass", request, GuestPassResponseDto.class);
//        assertNull(response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

//    @Test
//    @Order(1)
//    public void testCreateGuestPassAccount() {
//        GuestPassRequestDto request = setRequest(guestPassFixture.spotNumber, guestPassFixture.confirmationCode,
//                guestPassFixture.licensePlate, guestPassFixture.startDate, guestPassFixture.endDate,
//                guestPassFixture.floorNumber, guestPassFixture.isLarge, guestPassFixture.guestEmail);
//        ResponseEntity<String> response = client.postForEntity("/guestpass", request, String.class);
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//    }

    @Test
    @Order(1)
    public void testGetGuestPass(){
        ResponseEntity<GuestPassResponseDto> response = client.getForEntity("/guestpass/" + guestPassFixture.id, GuestPassResponseDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @Order(2)
    public void testGetInvalidGuestPass(){
        Integer invalidId = 2;
        ResponseEntity<String> response = client.getForEntity("/guestpass/" + invalidId, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(response.getBody(), "Guest pass with id: " + invalidId + " does not exist.");
    }


}