package ca.mcgill.ecse321.PLMS.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.PLMS.dto.ServiceAppointmentRequestDto;
import ca.mcgill.ecse321.PLMS.dto.ServiceAppointmentResponseDto;
import ca.mcgill.ecse321.PLMS.model.Employee;
import ca.mcgill.ecse321.PLMS.model.MonthlyCustomer;
import ca.mcgill.ecse321.PLMS.model.ParkingLot;
import ca.mcgill.ecse321.PLMS.model.Service;
import ca.mcgill.ecse321.PLMS.model.ServiceAppointment;
import ca.mcgill.ecse321.PLMS.repository.EmployeeRepository;
import ca.mcgill.ecse321.PLMS.repository.MonthlyCustomerRepository;
import ca.mcgill.ecse321.PLMS.repository.ParkingLotRepository;
import ca.mcgill.ecse321.PLMS.repository.ServiceAppointmentRepository;
import ca.mcgill.ecse321.PLMS.repository.ServiceRepository;

// Start the app for real so that we can send requests to it, but use a random port to avoid conflicts.
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
// Reuse the same class for all the tests (instead of creating a new class each time).
@TestInstance(Lifecycle.PER_CLASS)
// Ensure the tests are run in the right order (e.g., POST before GET)
@TestMethodOrder(OrderAnnotation.class)
public class ServiceAppointmentIntegrationTest {
    @Autowired
    private ServiceAppointmentRepository serviceAppointmentRepository;
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private MonthlyCustomerRepository monthlyCustomerRepository;
    @Autowired
    private ParkingLotRepository parkingLotRepository;
    @Autowired
    private TestRestTemplate client;

    /**
     * A fixed class to get all the data from for all the tests
     */
    private class FixedServiceAppointment{
        public static final ParkingLot parkingLot = new ParkingLot(Time.valueOf("8:00:00"), Time.valueOf("20:00:00"), 15, 10, 250, 250);
        //1st service appointment
        public static final LocalDate validDate = LocalDate.of(2026, 6, 6);
        public static final Time validTime = Time.valueOf("16:00:00");
        public static final Employee validEmployee = new Employee("employee@valid.com", "MyNameIsJohn1!", "John", "Mechanic", 25);
        public static final MonthlyCustomer validMonthlyCustomer = new MonthlyCustomer("customer@valid.com", "NotJohn59!", "NotJohn");
        public static final Service validService = new Service("Mechanical Stuff", 150, 2);
        public static int id1;

        //2nd service appointment
        public static final LocalDate validDate2 = LocalDate.of(2025, 5, 5);
        public static final Time validTime2 = Time.valueOf("15:00:00");
        public static final Service validService2 = new Service("More Mechanical Stuff", 170, 3);
        public static int id2;

        public static ServiceAppointmentRequestDto createValidRequestDto(){
            ServiceAppointmentRequestDto request = new ServiceAppointmentRequestDto();
            request.setDate(validDate);
            request.setServiceName(validService.getServiceName());
            request.setStartTime(validTime);
            request.setUserEmail(validMonthlyCustomer.getEmail());
            return request;
        }
    }

	@AfterAll
	public void clearDatabase() {
		serviceAppointmentRepository.deleteAll();
		serviceRepository.deleteAll();
        employeeRepository.deleteAll();
        monthlyCustomerRepository.deleteAll();
        parkingLotRepository.deleteAll();
	}

    @BeforeAll
    public void setupReposForTests(){
        serviceAppointmentRepository.deleteAll();
		serviceRepository.deleteAll();
        employeeRepository.deleteAll();
        monthlyCustomerRepository.deleteAll();
        parkingLotRepository.deleteAll();

        parkingLotRepository.save(FixedServiceAppointment.parkingLot);
        serviceRepository.save(FixedServiceAppointment.validService);
        serviceRepository.save(FixedServiceAppointment.validService2);
        employeeRepository.save(FixedServiceAppointment.validEmployee);
        monthlyCustomerRepository.save(FixedServiceAppointment.validMonthlyCustomer);
    }

    //0: Create a valid service appointment with a monthlyCustomer
    //1: Create a vaild service appointment with no monthlyCustomer
    //1.a : Try create a service appointment with null parameters
    //1.b : Try create a service appointment with past date
    //1.c : Try create a service appointment with start time before parking lot opening hours
    //1.d : Try create a service appointment with start time after parking lot opening hours
    //1.e : Try create a service appointment with end time after parking lot opening hours
    //1.f : Try create a service appointment with invalid customer email
    //2: Get service appointment from the 0 test
    //2.a: Get service appointment that does not exist
    //3: Get all the service appointments in the database
    //4: Get all the appointment with a date
    //4.a: Try getting all appointments at an invalid date
    //5: Get all the appointment with a employee email
    //5.a: Try getting all appointments at an invalid employee email
    //6: Get all the appointment with a customer email
    //6.a: Try getting all appointments at an invalid customer email
    //7: Modify the 0 appointment to have new valid parameters with same date and customer email
    //7.a : Try modifying a service appointment with null parameters
    //8: Delete the first appointment
    //8.a: Try deleting an appointment that does not exist
    //9: Delete the second appointment (no more service appointments in the system)
    //9.a: Try creating appointment with no parking lot
    //9.b: Try getting all appointments

    //Problem: employee email cannot be modified once set.
    //Potential additional tests
    //10: Try modifying the appointment with invalid employee email

    //0
    @Test
    @Order(0)
    public void testCreateValidAppointmentService1(){
        ServiceAppointmentRequestDto request = FixedServiceAppointment.createValidRequestDto();
        
        ResponseEntity<ServiceAppointmentResponseDto> response = client.postForEntity("/serviceAppointment", request, ServiceAppointmentResponseDto.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());

        assertEquals(FixedServiceAppointment.validDate, response.getBody().getDate());
        assertEquals(FixedServiceAppointment.validTime, response.getBody().getStartTime());
        assertEquals(findEndTime(FixedServiceAppointment.validTime.toLocalTime(), FixedServiceAppointment.validService), response.getBody().getEndTime());
        assertEquals(FixedServiceAppointment.validMonthlyCustomer.getEmail(), response.getBody().getCustomerEmail());
        assertEquals(FixedServiceAppointment.validEmployee.getEmail(), response.getBody().getEmployeeEmail());
        assertEquals(FixedServiceAppointment.validService.getServiceName(), response.getBody().getServiceName());

        assertTrue(response.getBody().getId() >= 1, "Response ID is at least 1.");
        
        //Save Id
        FixedServiceAppointment.id1 = response.getBody().getId();
    }

    //1
    @Test
    @Order(1)
    public void testCreateValidServiceAppointment2(){
        ServiceAppointmentRequestDto request = FixedServiceAppointment.createValidRequestDto();
        request.setDate(FixedServiceAppointment.validDate2);
        request.setStartTime(FixedServiceAppointment.validTime2);
        request.setServiceName(FixedServiceAppointment.validService2.getServiceName());
        request.setUserEmail(null);
        
        ResponseEntity<ServiceAppointmentResponseDto> response = client.postForEntity("/serviceAppointment", request, ServiceAppointmentResponseDto.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());

        assertEquals(FixedServiceAppointment.validDate2, response.getBody().getDate());
        assertEquals(FixedServiceAppointment.validTime2, response.getBody().getStartTime());
        assertEquals(findEndTime(FixedServiceAppointment.validTime2.toLocalTime(), FixedServiceAppointment.validService2), response.getBody().getEndTime());
        assertEquals(null, response.getBody().getCustomerEmail());
        assertEquals(FixedServiceAppointment.validEmployee.getEmail(), response.getBody().getEmployeeEmail());
        assertEquals(FixedServiceAppointment.validService2.getServiceName(), response.getBody().getServiceName());

        assertTrue(response.getBody().getId() >= 1, "Response ID is at least 1.");
        
        //Save Id
        FixedServiceAppointment.id2 = response.getBody().getId();
    }

    //1.a
    @Test
    @Order(2)
    public void testCreateServiceAppointmentWithNullParameters(){
        ServiceAppointmentRequestDto request = new ServiceAppointmentRequestDto();

        ResponseEntity<String> response = client.postForEntity("/serviceAppointment", request, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        assertContains("Cannot have an empty date." , response.getBody());
        assertContains("Cannot have an empty start time." , response.getBody());
        assertContains("Cannot have an empty service name." , response.getBody());
    }

    //1.b
    @Test
    @Order(3)
    public void testCreateServiceAppointmentWithPastDate(){
        ServiceAppointmentRequestDto request = FixedServiceAppointment.createValidRequestDto();
        request.setDate(LocalDate.of(2020,02,02));
        
        ResponseEntity<String> response = client.postForEntity("/serviceAppointment", request, String.class);
    
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    
        assertContains("Date must be in the future." , response.getBody());
    }

    //1.c
    @Test
    @Order(4)
    public void testCreateServiceAppointmentWithEarlyStartTime(){
        ServiceAppointmentRequestDto request = FixedServiceAppointment.createValidRequestDto();
        request.setStartTime(Time.valueOf("4:00:00"));;
        
        ResponseEntity<String> response = client.postForEntity("/serviceAppointment", request, String.class);
    
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    
        assertContains("Cannot have an appointment beginning before the lot opens." , response.getBody());
    }

    //1.d
    @Test
    @Order(5)
    public void testCreateServiceAppointmentWithLateStartTime(){
        ServiceAppointmentRequestDto request = FixedServiceAppointment.createValidRequestDto();
        request.setStartTime(Time.valueOf("22:00:00"));;
        
        ResponseEntity<String> response = client.postForEntity("/serviceAppointment", request, String.class);
    
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    
        assertContains("Cannot have an appointment beginning after the lot closes." , response.getBody());
    }

    //1.e
    @Test
    @Order(6)
    public void testCreateServiceAppointmentWithLateEndTime(){
        ServiceAppointmentRequestDto request = FixedServiceAppointment.createValidRequestDto();
        request.setStartTime(Time.valueOf("19:00:00"));;
        
        ResponseEntity<String> response = client.postForEntity("/serviceAppointment", request, String.class);
    
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    
        assertContains("Cannot have an appointment ending after the lot closes." , response.getBody());
    }

    //1.f
    @Test
    @Order(7)
    public void testCreateServiceAppointmentWithInvalidCustomerEmail(){
        ServiceAppointmentRequestDto request = FixedServiceAppointment.createValidRequestDto();
        request.setUserEmail("invalid@email.invalid");
        
        ResponseEntity<String> response = client.postForEntity("/serviceAppointment", request, String.class);
    
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    
        assertContains("Monthly customer not found." , response.getBody());
    }

    //2
    @Test
    @Order(8)
    public void testGetServiceAppointmentWithId(){
        ResponseEntity<ServiceAppointmentResponseDto> response = client.getForEntity("/serviceAppointment/"+FixedServiceAppointment.id1, ServiceAppointmentResponseDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        assertEquals(Date.valueOf(FixedServiceAppointment.validDate).toString(), response.getBody().getDate().toString());
        assertEquals(FixedServiceAppointment.validTime, response.getBody().getStartTime());
        assertEquals(findEndTime(FixedServiceAppointment.validTime.toLocalTime(), FixedServiceAppointment.validService), response.getBody().getEndTime());
        assertEquals(FixedServiceAppointment.validMonthlyCustomer.getEmail(), response.getBody().getCustomerEmail());
        assertEquals(FixedServiceAppointment.validEmployee.getEmail(), response.getBody().getEmployeeEmail());
        assertEquals(FixedServiceAppointment.validService.getServiceName(), response.getBody().getServiceName());
        assertEquals(FixedServiceAppointment.id1, response.getBody().getId());
    }

    //2.a
    @Test
    @Order(9)
    public void testGetServiceAppointmentWithInvalidId(){
        ResponseEntity<String> response = client.getForEntity("/serviceAppointment/"+Integer.MAX_VALUE, String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        assertContains("Service appointment with ID " + Integer.MAX_VALUE + " does not exist.", response.getBody());
    }
    
    //3
    @Test
    @Order(10)
    public void testGetAllServiceAppointments(){
        ResponseEntity<List> response = client.getForEntity("/serviceAppointment", List.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        List<Map<String, Object>> responseBody = response.getBody();
        
        assertEquals(2, responseBody.size());

        assertEquals(Date.valueOf(FixedServiceAppointment.validDate).toString(), responseBody.get(0).get("date").toString());
        assertEquals(FixedServiceAppointment.validTime, responseBody.get(0).get("startTime"));
        assertEquals(findEndTime(FixedServiceAppointment.validTime.toLocalTime(), FixedServiceAppointment.validService2), responseBody.get(0).get("endTime"));
        assertEquals(FixedServiceAppointment.validMonthlyCustomer.getEmail(), responseBody.get(0).get("customerEmail"));
        assertEquals(FixedServiceAppointment.validEmployee.getEmail(), responseBody.get(0).get("employeeEmail"));
        assertEquals(FixedServiceAppointment.validService.getServiceName(), responseBody.get(0).get("serviceName"));
        assertEquals(FixedServiceAppointment.id1, responseBody.get(0).get("id"));

        assertEquals(Date.valueOf(FixedServiceAppointment.validDate).toString(), responseBody.get(1).get("date").toString());
        assertEquals(FixedServiceAppointment.validTime, responseBody.get(1).get("startTime"));
        assertEquals(findEndTime(FixedServiceAppointment.validTime.toLocalTime(), FixedServiceAppointment.validService2), responseBody.get(1).get("endTime"));
        assertEquals(FixedServiceAppointment.validMonthlyCustomer.getEmail(), responseBody.get(1).get("customerEmail"));
        assertEquals(FixedServiceAppointment.validEmployee.getEmail(), responseBody.get(1).get("employeeEmail"));
        assertEquals(FixedServiceAppointment.validService.getServiceName(), responseBody.get(1).get("serviceName"));
        assertEquals(FixedServiceAppointment.id1, responseBody.get(1).get("id"));

    }

    private void assertContains(String expected, String actual) {
		String assertionMessage = String.format("Error message ('%s') contains '%s'.", actual, expected);
		assertTrue(actual.contains(expected), assertionMessage);
	}

    public Time findEndTime(LocalTime startTime, Service service){
        int hours = (int) service.getLengthInHours();
        int minutes = (int) ((service.getLengthInHours() - hours) * 60);
        LocalTime localEndTime = startTime.plusHours(hours).plusMinutes(minutes);
        return Time.valueOf(localEndTime);
      }
}
