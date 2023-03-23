package ca.mcgill.ecse321.PLMS.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
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
	public void testCreateParkingLot() {

        Time openingTime = new Time(0,0,0);
        Time closingTime = new Time(4, 0, 0)
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


		ResponseEntity<ParkingLotResponseDto> response = client.postForEntity("/person", request, ParkingLotResponseDto.class);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(openingTime, response.getBody().getOpeningTime());
        assertEquals(closingTime, response.getBody().getClosingTime());
        assertEquals(largeSpotFee, response.getBody().getLargeSpotFee());
        assertEquals(smallSpotFee, response.getBody().getSmallSpotFee());
        assertEquals(smallSpotMonthlyFlatFee, response.getBody().getSmallSpotMonthlyFlatFee());
        assertEquals(largeSpotMonthlyFlatFee, response.getBody().getLargeSpotMonthlyFlatFee());

		LocalDate today = LocalDateTime.now().toLocalDate();
		//assertEquals(today, response.getBody().getCreationDate());
		assertTrue(response.getBody().getId() >= 1, "Response ID is at least 1.");

		// Save the ID so that later tests can use it
		fixture.setId(response.getBody().getId());
	}

    @Test
	@Order(0)
	public void testCreateInvalidParkingLot() {
		ParkingLotRequestDto request = new ParkingLotRequestDto();

		ResponseEntity<String> response = client.postForEntity("/person", request, String.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertContains("Name cannot be blank.", response.getBody());
		assertContains("Password must not be null.", response.getBody());
	}

    
	@Test
	@Order(1)
	public void testGetPerson() {
		int id = fixture.getId();

		ResponseEntity<PersonResponseDto> response = client.getForEntity("/person/" + id, PersonResponseDto.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(TestFixture.PERSON_NAME, response.getBody().getName());
		LocalDate today = LocalDateTime.now().toLocalDate();
		assertEquals(today, response.getBody().getCreationDate());
		assertEquals(id, response.getBody().getId());
	}

	@Test
	@Order(2)
	public void testCreateInvalidPerson() {
		PersonRequestDto request = new PersonRequestDto();

		ResponseEntity<String> response = client.postForEntity("/person", request, String.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertContains("Name cannot be blank.", response.getBody());
		assertContains("Password must not be null.", response.getBody());
	}

	@Test
	@Order(3)
	public void testGetInvalidPerson() {
		ResponseEntity<String> response = client.getForEntity("/person/" + TestFixture.INVALID_ID, String.class);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals("Not found.", response.getBody());
	}

	private static void assertContains(String expected, String actual) {
		String assertionMessage = String.format("Error message ('%s') contains '%s'.", actual, expected);
		assertTrue(actual.contains(expected), assertionMessage);
	}
}