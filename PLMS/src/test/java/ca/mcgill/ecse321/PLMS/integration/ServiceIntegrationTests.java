package ca.mcgill.ecse321.PLMS.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.ClassOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.PLMS.dto.ServiceRequestDto;
import ca.mcgill.ecse321.PLMS.dto.ServiceResponseDto;
import ca.mcgill.ecse321.PLMS.model.Service;
import ca.mcgill.ecse321.PLMS.repository.ServiceRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// Reuse the same class for all the tests (instead of creating a new class each time).
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
// Ensure the tests are run in the right order (e.g., POST before GET)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ServiceIntegrationTests {
  @Autowired
  ServiceRepository serviceRepo;
  @Autowired
    private TestRestTemplate client;
    @LocalServerPort
    private int port;

    /**
     * Creates a default valid floor object
     * @return default valid floor object
     */
    private class FixedValidService {

        public static final String serviceName = "Car wash";
        public static final double cost = 17.5;
        public static final double lengthInHours = 0.5;

        public static Service createValidService(){
            Service validService = new Service(serviceName, cost, lengthInHours);
            return validService;
        }
        
    }

    /**
     * Method that transforms a floor object into a floorRequestDto
     * @param floor floor object that is going to be transformed into a floorRequestDto
     * @return the corresponding floorRequestDto
     */
    private ServiceRequestDto serviceToServiceRequestDto(Service service){
        
        ServiceRequestDto serviceRequestDto = new ServiceRequestDto();

        serviceRequestDto.setServiceName(service.getServiceName());
        serviceRequestDto.setCost(service.getCost());
        serviceRequestDto.setLengthInHours(service.getLengthInHours());

        return serviceRequestDto;
    }

    @BeforeAll
	@AfterAll
	public void clearDatabase() {
		serviceRepo.deleteAll();
	}

    @Test
	@Order(0)
	public void testValidCreateService() {


        Service validService = FixedValidService.createValidService();
        ServiceRequestDto request = serviceToServiceRequestDto(validService);

        ResponseEntity<ServiceResponseDto> response = client.postForEntity("/service/create", request, ServiceResponseDto.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(FixedValidService.serviceName, response.getBody().getServiceName());
        assertEquals(FixedValidService.cost, response.getBody().getCost());
        assertEquals(FixedValidService.lengthInHours, response.getBody().getLengthInHours());
    }

    @Test
    @Order(1)
    public void testGetValidService(){
        String serviceName = FixedValidService.serviceName;

        ResponseEntity<ServiceResponseDto> response = client.getForEntity("/service/" + serviceName, ServiceResponseDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(FixedValidService.serviceName, response.getBody().getServiceName());
        assertEquals(FixedValidService.cost, response.getBody().getCost());
        assertEquals(FixedValidService.lengthInHours, response.getBody().getLengthInHours());
    }

    @Test
    @Order(2)
    public void testCreateDuplicateFloor(){
        // Attempt to create a service with a duplicate service name
        Service duplicatedService = FixedValidService.createValidService();
        ServiceRequestDto request = serviceToServiceRequestDto(duplicatedService);

        ResponseEntity<String> response = client.postForEntity("/floor", request, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertContains("Floor with floor number: " + FixedValidFloor.floorNumber + " already exists." , response.getBody());
    }

  //   @Test
  //   @Order(3)
  //   public void testCreateFloorWithNullParamaters(){
  //       FloorRequestDto request = new FloorRequestDto();

  //       ResponseEntity<String> response = client.postForEntity("/floor", request, String.class);

  //       assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  //       assertContains("Cannot have an empty floor number." , response.getBody());
  //       assertContains("Cannot have an empty number of small spots on a floor." , response.getBody());
  //       assertContains("Cannot have an empty number of large spots on a floor." , response.getBody());
  //       assertContains("Member only must be true or false." , response.getBody());
  //   }

  //   @Test
  //   @Order(4)
  //   public void testCreateFloorWithNegativeParameters(){
        
  //       Floor validFloor = FixedValidFloor.createValidFloor();
  //       FloorRequestDto request = floorToFloorRequestDto(validFloor);

  //       request.setFloorNumber(-1);
  //       request.setLargeSpotCapacity(-1);
  //       request.setSmallSpotCapacity(-1);

  //       ResponseEntity<String> response = client.postForEntity("/floor", request, String.class);

  //       assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  //       assertContains("The floor number must be a non negative number." , response.getBody());
  //       assertContains("Cannot be a negative number of small parking spots on a floor." , response.getBody());
  //       assertContains("Cannot be a negative number of large parking spots on a floor." , response.getBody());
  //   }

  //   @Test
  //   @Order(5)
  //   public void testModifyFloorWithValidParameters(){

  //       Floor validFloor = FixedValidFloor.createValidFloor();
  //       FloorRequestDto request = floorToFloorRequestDto(validFloor);

  //       request.setLargeSpotCapacity(FixedValidFloor.largeSpotCapacityUpdated);
  //       request.setSmallSpotCapacity(FixedValidFloor.smallSpotCapacityUpdated);
  //       request.setIsMemberOnly(FixedValidFloor.isMemberOnlyUpdated);

  //       HttpEntity<FloorRequestDto> requestEntity = new HttpEntity<>(request);

  //       ResponseEntity<FloorResponseDto> response = client.exchange(createURLWithPort("/floor"), HttpMethod.PUT, requestEntity, FloorResponseDto.class);

  //       assertEquals(HttpStatus.CREATED, response.getStatusCode());
  //       assertNotNull(response.getBody());
        
  //       assertEquals(FixedValidFloor.floorNumber, response.getBody().getFloorNumber());
  //       assertEquals(FixedValidFloor.isMemberOnlyUpdated, response.getBody().getMemberOnly());
  //       assertEquals(FixedValidFloor.largeSpotCapacityUpdated, response.getBody().getLargeSpotCapacity());
  //       assertEquals(FixedValidFloor.smallSpotCapacityUpdated, response.getBody().getSmallSpotCapacity());
  //   }

  //   @Test
  //   @Order(6)
  //   public void testGetModifiedFloor(){
  //       int floorNumber = FixedValidFloor.floorNumber;

  //       ResponseEntity<FloorResponseDto> response = client.getForEntity("/floor/" + floorNumber, FloorResponseDto.class);

  //       assertEquals(HttpStatus.OK, response.getStatusCode());
  //       assertNotNull(response.getBody());

  //       assertEquals(FixedValidFloor.floorNumber, response.getBody().getFloorNumber());
  //       assertEquals(FixedValidFloor.isMemberOnlyUpdated, response.getBody().getMemberOnly());
  //       assertEquals(FixedValidFloor.largeSpotCapacityUpdated, response.getBody().getLargeSpotCapacity());
  //       assertEquals(FixedValidFloor.smallSpotCapacityUpdated, response.getBody().getSmallSpotCapacity());
  //   }

  //   @Test
  //   @Order(7)
  //   public void testModifyFloorWithNullParameters(){
  //       FloorRequestDto request = new FloorRequestDto();

  //       HttpEntity<FloorRequestDto> requestEntity = new HttpEntity<>(request);

  //       ResponseEntity<String> response = client.exchange(createURLWithPort("/floor"), HttpMethod.PUT, requestEntity, String.class);

  //       assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  //       assertContains("Cannot have an empty floor number." , response.getBody());
  //       assertContains("Cannot have an empty number of small spots on a floor." , response.getBody());
  //       assertContains("Cannot have an empty number of large spots on a floor." , response.getBody());
  //       assertContains("Member only must be true or false." , response.getBody());
  //   }

  //   @Test
  //   @Order(8)
  //   public void testModifyFloorWithNegativeParameters(){
  //       FloorRequestDto request = new FloorRequestDto();

  //       request.setFloorNumber(-1);
  //       request.setLargeSpotCapacity(-1);
  //       request.setSmallSpotCapacity(-1);

  //       HttpEntity<FloorRequestDto> requestEntity = new HttpEntity<>(request);

  //       ResponseEntity<String> response = client.exchange(createURLWithPort("/floor"), HttpMethod.PUT, requestEntity, String.class);

  //       assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  //       assertContains("The floor number must be a non negative number." , response.getBody());
  //       assertContains("Cannot be a negative number of small parking spots on a floor." , response.getBody());
  //       assertContains("Cannot be a negative number of large parking spots on a floor." , response.getBody());
  //   }

  //   @Test
  //   @Order(11)
  //   public void testCreateSecondValidFloor(){
  //       Floor validFloor = FixedValidFloor.createValidFloor();
  //       FloorRequestDto request = floorToFloorRequestDto(validFloor);

  //       request.setFloorNumber(FixedValidFloor.secondFloorNumber);

  //       ResponseEntity<FloorResponseDto> response = client.postForEntity("/floor", request, FloorResponseDto.class);

  //       assertEquals(HttpStatus.CREATED, response.getStatusCode());
  //       assertNotNull(response.getBody());
        
  //       assertEquals(FixedValidFloor.secondFloorNumber, response.getBody().getFloorNumber());
  //       assertEquals(FixedValidFloor.isMemberOnly, response.getBody().getMemberOnly());
  //       assertEquals(FixedValidFloor.largeSpotCapacity, response.getBody().getLargeSpotCapacity());
  //       assertEquals(FixedValidFloor.smallSpotCapacity, response.getBody().getSmallSpotCapacity());
  //   }

  //   @Test
  //   @Order(12)
  //   public void testGetValidFloors(){

  //       ResponseEntity<List> response = client.getForEntity("/floor", List.class);

  //       assertEquals(HttpStatus.OK, response.getStatusCode());
  //       assertNotNull(response.getBody());

  //       List<Map<String, Object>> responseBody = response.getBody();
        
  //       assertEquals(2, responseBody.size());
        
  //       assertEquals(FixedValidFloor.floorNumber, responseBody.get(0).get("floorNumber"));
  //       assertEquals(FixedValidFloor.isMemberOnlyUpdated, responseBody.get(0).get("memberOnly"));
  //       assertEquals(FixedValidFloor.largeSpotCapacityUpdated, responseBody.get(0).get("largeSpotCapacity"));
  //       assertEquals(FixedValidFloor.smallSpotCapacityUpdated, responseBody.get(0).get("smallSpotCapacity"));

  //       assertEquals(FixedValidFloor.secondFloorNumber, responseBody.get(1).get("floorNumber"));
  //       assertEquals(FixedValidFloor.isMemberOnly, responseBody.get(1).get("memberOnly"));
  //       assertEquals(FixedValidFloor.largeSpotCapacity, responseBody.get(1).get("largeSpotCapacity"));
  //       assertEquals(FixedValidFloor.smallSpotCapacity, responseBody.get(1).get("smallSpotCapacity"));
  //   }

  //   @Test
  //   @Order(13)
  //   public void testDeleteFloorThatDoesNotExist(){
  //       HttpEntity<String> requestEntity = new HttpEntity<>(null);

  //       ResponseEntity<String> response = client.exchange(createURLWithPort("/floor/"+Integer.MAX_VALUE), HttpMethod.DELETE, requestEntity, String.class);
  //       assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  //       assertContains("Floor with floor number: " + Integer.MAX_VALUE + " does not exist." , response.getBody());
  //   }

  //   @Test
  //   @Order(14)
  //   public void testDeleteFloor(){
  //       HttpEntity<String> requestEntity = new HttpEntity<>(null);

  //       ResponseEntity<String> response = client.exchange(createURLWithPort("/floor/"+FixedValidFloor.floorNumber), HttpMethod.DELETE, requestEntity, String.class);
  //       assertEquals(HttpStatus.OK, response.getStatusCode());

  //       //Test if the getter of all floors reads one less floor
  //       ResponseEntity<List> getResponse = client.getForEntity("/floor", List.class);

  //       assertEquals(HttpStatus.OK, getResponse.getStatusCode());
  //       assertNotNull(getResponse.getBody());

  //       List<Map<String, Object>> responseBody = getResponse.getBody();
        
  //       assertEquals(1, responseBody.size());
  //   }

  //   @Test
  //   @Order(15)
  //   public void testGetInvalidFloor(){
  //       HttpEntity<String> requestEntity = new HttpEntity<>(null);

  //       ResponseEntity<String> response = client.exchange(createURLWithPort("/floor/"+FixedValidFloor.floorNumber), HttpMethod.DELETE, requestEntity, String.class);
  //       assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  //       assertContains("Floor with floor number: " + FixedValidFloor.floorNumber + " does not exist.", response.getBody());
  //   }

  //   @Test
  //   @Order(16)
  //   public void testDeleteLastFloor(){
  //       HttpEntity<String> requestEntity = new HttpEntity<>(null);

  //       ResponseEntity<String> response = client.exchange(createURLWithPort("/floor/"+FixedValidFloor.secondFloorNumber), HttpMethod.DELETE, requestEntity, String.class);
  //       assertEquals(HttpStatus.OK, response.getStatusCode());

  //       //Test if the getter of all floors reads one less floor
  //       ResponseEntity<String> getResponse = client.getForEntity("/floor", String.class);

  //       assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
  //       assertNotNull(getResponse.getBody());
        
  //       assertEquals("There are no floors in the system.", getResponse.getBody());
  //   }


  //   @Test
  //   @Order(17)
  //   public void testCreateFloorWithNoParkingLot(){
  //       //This test has to be passed only after the delete test is passed
  //       parkingLotRepository.deleteAll();

  //       Floor validFloor = FixedValidFloor.createValidFloor();
  //       FloorRequestDto request = floorToFloorRequestDto(validFloor);

  //       ResponseEntity<String> response = client.postForEntity("/floor", request, String.class);

  //       // assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  //       assertContains("Cannot create floor since the parking lot has not been created." , response.getBody());
  //   }

  //   private void assertContains(String expected, String actual) {
	// 	String assertionMessage = String.format("Error message ('%s') contains '%s'.", actual, expected);
	// 	assertTrue(actual.contains(expected), assertionMessage);
	// }

  //   private URI createURLWithPort(String endpoint){
  //       String baseUrl = "http://localhost:" + port + endpoint;
  //       URI uri = URI.create(baseUrl);

  //       return uri;
  //   }
}

