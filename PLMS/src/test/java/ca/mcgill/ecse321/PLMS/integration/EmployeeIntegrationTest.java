package ca.mcgill.ecse321.PLMS.integration;


import ca.mcgill.ecse321.PLMS.dto.EmployeeRequestDto;
import ca.mcgill.ecse321.PLMS.dto.EmployeeResponseDto;
import ca.mcgill.ecse321.PLMS.model.Employee;
import ca.mcgill.ecse321.PLMS.repository.EmployeeRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// Reuse the same class for all the tests (instead of creating a new class each time).
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
// Ensure the tests are run in the right order (e.g., POST before GET)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeIntegrationTest {

    private class EmployeeFixture {
        public String name = "John";
        public String email = "john.doe@mcgill.ca";
        public String password = "Samer+2003";
        public String jobTitle = "Mechanic";
        public Double hourlyWage = 17.5;

        public void setEmail(String email) { this.email = email; }
        public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }
        public void setHourlyWage(Double hourlyWage) { this.hourlyWage = hourlyWage; }
        public void setPassword(String password) { this.password = password; }
        public Double getHourlyWage() { return hourlyWage; }
        public String getJobTitle() { return jobTitle; }
        public String getEmail() { return email; }
        public String getPassword() { return  password; }
    }

    private EmployeeFixture employeeFixture;

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private TestRestTemplate client;

    @BeforeAll
    public void setupTestFixture() { this.employeeFixture = new EmployeeFixture(); }

    @BeforeAll
    @AfterAll
    public void clearDatabase() {
        employeeRepository.deleteAll();
    }

    private EmployeeRequestDto setRequest(String email, String name, String password, String jobTitle, Double hourlyWage) {
        EmployeeRequestDto request = new EmployeeRequestDto();
        request.setHourlyWage(hourlyWage);
        request.setJobTitle(jobTitle);
        request.setName(name);
        request.setPassword(password);
        request.setEmail(email);
        return request;
    }

    private boolean equals(EmployeeResponseDto response, EmployeeFixture e)
    {
        boolean b = response.getEmail().equals(e.email);
        b = b & response.getName().equals(e.name);
        b = b & response.getPassword().equals(e.password);
        b = b & (response.getHourlyWage() == e.hourlyWage);
        return b & (response.getJobTitle().equals(e.jobTitle));
    }


    @Test
    @Order(0)
    public void testCreateEmployee() {
        EmployeeRequestDto request = new EmployeeRequestDto();
        request.setHourlyWage(employeeFixture.hourlyWage);
        request.setJobTitle(employeeFixture.jobTitle);
        request.setName(employeeFixture.name);
        request.setPassword(employeeFixture.password);
        request.setEmail(employeeFixture.email);

        ResponseEntity<EmployeeResponseDto> response =  client.postForEntity("/employee/create", request, EmployeeResponseDto.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(equals(response.getBody(), employeeFixture));

    }



 }
