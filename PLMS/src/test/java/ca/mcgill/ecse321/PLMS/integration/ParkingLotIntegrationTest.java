package ca.mcgill.ecse321.PLMS.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.PLMS.dto.ParkingLotRequestDto;
import ca.mcgill.ecse321.PLMS.dto.ParkingLotResponseDto;
import ca.mcgill.ecse321.PLMS.model.ParkingLot;
import ca.mcgill.ecse321.PLMS.repository.ParkingLotRepository;


// Start the app for real so that we can send requests to it, but use a random port to avoid conflicts.
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
// Reuse the same class for all the tests (instead of creating a new class each time).
@TestInstance(Lifecycle.PER_CLASS)
// Ensure the tests are run in the right order (e.g., POST before GET)
@TestMethodOrder(OrderAnnotation.class)
public class ParkingLotIntegrationTest {

	// Stores state to be shared between tests
	private class TestFixture {
		public static final int INVALID_ID = Integer.MAX_VALUE;

		private int id;
        private Time openingTime;
        private Time closingTime;
        private double largeSpotFee;
        private double smallSpotFee;
        private double smallSpotMonthlyFlatFee;
        private double largeSpotMonthlyFlatFee;


		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}
    
        public Time getOpeningTime() {
            return openingTime;
        }
    
        public Time getClosingTime() {
            return closingTime;
        }
    
        public Double getLargeSpotFee() {
            return largeSpotFee;
        }
    
        public Double getSmallSpotFee() {
            return smallSpotFee;
        }
    
        public Double getSmallSpotMonthlyFlatFee() {
            return smallSpotMonthlyFlatFee;
        }
    
        public Double getLargeSpotMonthlyFlatFee() {
            return largeSpotMonthlyFlatFee;
        }

        public void setOpeningTime(Time openingTime) {
            this.openingTime = openingTime;
        }
    
        public void setClosingTime(Time closingTime) {
            this.closingTime = closingTime;
        }
    
        public void setLargeSpotFee(Double largeSpotFee) {
            this.largeSpotFee = largeSpotFee;
        }
    
        public void setSmallSpotFee(Double smallSpotFee) {
            this.smallSpotFee = smallSpotFee;
        }
    
        public void setSmallSpotMonthlyFlatFee(Double smallSpotMonthlyFlatFee) {
            this.smallSpotMonthlyFlatFee = smallSpotMonthlyFlatFee;
        }
    
      public void setLargeSpotMonthlyFlatFee(Double largeSpotMonthlyFlatFee) {
            this.largeSpotMonthlyFlatFee = largeSpotMonthlyFlatFee;
        }
	}

	private TestFixture fixture;
	@Autowired
	private ParkingLotRepository parkingLotRepository;
	@Autowired
	private TestRestTemplate client;

	@BeforeAll
	public void setupTestFixture() {
		this.fixture = new TestFixture();
	}

	@BeforeAll
	@AfterAll
	public void clearDatabase() {
		parkingLotRepository.deleteAll();
	}

    @Test
	@Order(0)
	public void testGetInvalidPerson() {
		ResponseEntity<String> response = client.getForEntity("/parkingLot", String.class);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals("Parking Lot not found.", response.getBody());
	}

	@Test
	@Order(1)
	public void testCreateParkingLot() {

        Time openingTime = new Time(0,0,0);
        Time closingTime = new Time(4, 0, 0);
        double largeSpotFee = 4;
        double smallSpotFee = 5;
        double smallSpotMonthlyFlatFee = 6;
        double largeSpotMonthlyFlatFee = 9;

		ParkingLotRequestDto request = new ParkingLotRequestDto();
		request.setOpeningTime(openingTime);
		request.setClosingTime(closingTime);
        request.setLargeSpotFee(largeSpotFee);
        request.setSmallSpotFee(smallSpotFee);
        request.setSmallSpotMonthlyFlatFee(smallSpotMonthlyFlatFee);
        request.setLargeSpotMonthlyFlatFee(largeSpotMonthlyFlatFee);


		ResponseEntity<ParkingLotResponseDto> response = client.postForEntity("/parkingLot/creation", request, ParkingLotResponseDto.class);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(openingTime, response.getBody().getOpeningTime());
        assertEquals(closingTime, response.getBody().getClosingTime());
        assertEquals(largeSpotFee, response.getBody().getLargeSpotFee());
        assertEquals(smallSpotFee, response.getBody().getSmallSpotFee());
        assertEquals(smallSpotMonthlyFlatFee, response.getBody().getSmallSpotMonthlyFlatFee());
        assertEquals(largeSpotMonthlyFlatFee, response.getBody().getLargeSpotMonthlyFlatFee());
		assertTrue(response.getBody().getId() >= 1, "Response ID is at least 1.");

		// Save the ID so that later tests can use it
		fixture.setId(response.getBody().getId());
	}

    @Test
	@Order(2)
	public void testCreateInvalidParkingLot() {
		ParkingLotRequestDto request = new ParkingLotRequestDto();

		ResponseEntity<String> response = client.postForEntity("/parkingLot/creation", request, String.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertContains("Opening time must not be null.", response.getBody());
        assertContains("Closing time must not be null.", response.getBody());
        assertContains("Large spot fee must not be null.", response.getBody());
        assertContains("Small spot fee must not be null.", response.getBody());
        assertContains("monthly flat fee must not be null.", response.getBody());
	}

    @Test
	@Order(3)
	public void testCreateInvalidParkingLot2() {
		ParkingLotRequestDto request = new ParkingLotRequestDto();
        request.setClosingTime(new Time(5, 0, 0));
        request.setOpeningTime(new Time(0, 0, 0));
        request.setLargeSpotFee(-2.0);
        request.setLargeSpotMonthlyFlatFee(-3.3);
        request.setSmallSpotFee(-32.3);
        request.setLargeSpotMonthlyFlatFee(-32.3);

		ResponseEntity<String> response = client.postForEntity("/parkingLot/creation", request, String.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertContains("Large spot fee must be non-negative.", response.getBody());
        assertContains("Small spot fee must be non-negative.", response.getBody());
        assertContains("Small spot monthly flat fee must be non-negative.", response.getBody());
        assertContains("Large spot monthly flat fee must be non-negative.", response.getBody());
	}

    @Test
	@Order(4)
	public void testCreateAnotherParkingLot() {
		ParkingLotRequestDto request = new ParkingLotRequestDto();

        Time openingTime = new Time(5,0,0);
        Time closingTime = new Time(2, 0, 0);
        double largeSpotFee = 42;
        double smallSpotFee = 54;
        double smallSpotMonthlyFlatFee = 613;
        double largeSpotMonthlyFlatFee = 9321;

		request.setOpeningTime(openingTime);
		request.setClosingTime(closingTime);
        request.setLargeSpotFee(largeSpotFee);
        request.setSmallSpotFee(smallSpotFee);
        request.setSmallSpotMonthlyFlatFee(smallSpotMonthlyFlatFee);
        request.setLargeSpotMonthlyFlatFee(largeSpotMonthlyFlatFee);


		ResponseEntity<String> response = client.postForEntity("/parkingLot/creation", request, String.class);

		assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        ArrayList<ParkingLot> parkingLots = (ArrayList<ParkingLot>) parkingLotRepository.findAll();
		assertEquals(1, parkingLots.size());
        assertEquals(fixture.getId(), parkingLots.get(0));
        assertContains("Parking Lot already exists", response.getBody());
	}


	@Test
	@Order(5)
	public void testGetParkingLot() {
		int id = fixture.getId();

		ResponseEntity<ParkingLotResponseDto> response = client.getForEntity("/parkingLot" + id, ParkingLotResponseDto.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(fixture.getId(), response.getBody().getId());
        assertEquals(fixture.getOpeningTime(), response.getBody().getOpeningTime());
        assertEquals(fixture.getClosingTime(), response.getBody().getClosingTime());
        assertEquals(fixture.getSmallSpotFee(), response.getBody().getSmallSpotFee());
        assertEquals(fixture.getLargeSpotFee(), response.getBody().getLargeSpotFee());
        assertEquals(fixture.getSmallSpotMonthlyFlatFee(), response.getBody().getSmallSpotMonthlyFlatFee());
        assertEquals(fixture.getLargeSpotMonthlyFlatFee(), response.getBody().getLargeSpotMonthlyFlatFee());
	}

    @Test
	@Order(6)
	public void testUpdateParkingLot() {

        Time openingTime = new Time(2,0,0);
        Time closingTime = new Time(6, 0, 0);
        double largeSpotFee = 5;
        double smallSpotFee = 6;
        double smallSpotMonthlyFlatFee = 7;
        double largeSpotMonthlyFlatFee = 10;

		ParkingLotRequestDto request = new ParkingLotRequestDto();
		request.setOpeningTime(openingTime);
		request.setClosingTime(closingTime);
        request.setLargeSpotFee(largeSpotFee);
        request.setSmallSpotFee(smallSpotFee);
        request.setSmallSpotMonthlyFlatFee(smallSpotMonthlyFlatFee);
        request.setLargeSpotMonthlyFlatFee(largeSpotMonthlyFlatFee);


		ResponseEntity<ParkingLotResponseDto> response = client.postForEntity("/parkingLot/update", request, ParkingLotResponseDto.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(openingTime, response.getBody().getOpeningTime());
        assertEquals(closingTime, response.getBody().getClosingTime());
        assertEquals(largeSpotFee, response.getBody().getLargeSpotFee());
        assertEquals(smallSpotFee, response.getBody().getSmallSpotFee());
        assertEquals(smallSpotMonthlyFlatFee, response.getBody().getSmallSpotMonthlyFlatFee());
        assertEquals(largeSpotMonthlyFlatFee, response.getBody().getLargeSpotMonthlyFlatFee());
		assertEquals(fixture.getId(), response.getBody().getId());
	}

    // @Test
	// @Order(7)
	// public void testUpdateOpeningClosingParkingLot() {

    //     Time openingTime = new Time(2,0,0);
    //     Time closingTime = new Time(6, 0, 0);

	// 	ParkingLotRequestDto request = new ParkingLotRequestDto();
	// 	request.setOpeningTime(openingTime);
	// 	request.setClosingTime(closingTime);
    //     request.setLargeSpotFee(largeSpotFee);
    //     request.setSmallSpotFee(smallSpotFee);
    //     request.setSmallSpotMonthlyFlatFee(smallSpotMonthlyFlatFee);
    //     request.setLargeSpotMonthlyFlatFee(largeSpotMonthlyFlatFee);

	// 	ResponseEntity<ParkingLotResponseDto> response = client.putForEntity("/parkingLot/update/opening/{openingTime}/closing/{closingTime}", ParkingLotResponseDto.class);

	// 	assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	// }

    @Test
	@Order(8)
	public void testInvalidUpdateParkingLot() {

        Time openingTime = new Time(2,0,0);
        Time closingTime = new Time(6, 0, 0);
        double largeSpotFee = -5;
        double smallSpotFee = 6;
        double smallSpotMonthlyFlatFee = -7;
        double largeSpotMonthlyFlatFee = 10;

		ParkingLotRequestDto request = new ParkingLotRequestDto();
		request.setOpeningTime(openingTime);
		request.setClosingTime(closingTime);
        request.setLargeSpotFee(largeSpotFee);
        request.setSmallSpotFee(smallSpotFee);
        request.setSmallSpotMonthlyFlatFee(smallSpotMonthlyFlatFee);
        request.setLargeSpotMonthlyFlatFee(largeSpotMonthlyFlatFee);

		ResponseEntity<String> response = client.postForEntity("/parkingLot/update", request, String.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}


	private static void assertContains(String expected, String actual) {
		String assertionMessage = String.format("Error message ('%s') contains '%s'.", actual, expected);
		assertTrue(actual.contains(expected), assertionMessage);
	}
}