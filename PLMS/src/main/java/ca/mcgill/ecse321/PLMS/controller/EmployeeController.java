package ca.mcgill.ecse321.PLMS.controller;

import ca.mcgill.ecse321.PLMS.dto.EmployeeRequestDto;
import ca.mcgill.ecse321.PLMS.dto.EmployeeResponseDto;
import ca.mcgill.ecse321.PLMS.model.Employee;
import ca.mcgill.ecse321.PLMS.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * Returns a list of all Employees
     * @return all Employees
     */

    @GetMapping("/employees")
    public Iterable<EmployeeResponseDto> getAllEmployees() {
        return StreamSupport.stream(employeeService.getAllEmployees().spliterator(), false).map(EmployeeResponseDto::new).collect(Collectors.toList());
    }

    /**
     * Returns the Employee based on their Id
     * Pass in an arguments by using /Employee={email}
     * @return the Employee with Email, Password, Name
     */

    @GetMapping(value = {"/employee", "/employee/"})
    public ResponseEntity<EmployeeResponseDto> getEmployeeByEmail(@RequestParam String email) {
        return new ResponseEntity<EmployeeResponseDto>(new EmployeeResponseDto(employeeService.getEmployeeByEmail(email)), HttpStatus.OK);
    }

    /**
     * Creates a new Employee
     *
     * @return the dto response of the new Employee
     */

    @PostMapping("/employee/create")
    public ResponseEntity<EmployeeResponseDto> createEmployee(@Valid @RequestBody EmployeeRequestDto EmployeeRequest)
    {
        Employee Employee = EmployeeRequest.toModel(); // 1. You pass in a request, validates the constraints, creates an Employee if they pass
        Employee =  employeeService.createEmployeeAccount(Employee); // 2. You use the service class to check if it exists and save it
        EmployeeResponseDto responseBody = new EmployeeResponseDto(Employee);
        return new ResponseEntity<EmployeeResponseDto>(responseBody, HttpStatus.CREATED); //3. You mask the model by returning a Response
    }



    @PutMapping(value = {"/employee/update"})
    public ResponseEntity<EmployeeResponseDto> updateEmployee(@Valid @RequestBody EmployeeRequestDto employeeRequest)
    {
       Employee employee = employeeRequest.toModel();
       employee = employeeService.updateEmployee(employee);
       return new ResponseEntity<EmployeeResponseDto>(new EmployeeResponseDto(employee), HttpStatus.OK);

    }

    @PutMapping(value = {"/employee/update/password/{email}"})
    public ResponseEntity<EmployeeResponseDto> updateEmployeePassword(@PathVariable String email, @RequestParam String password)
    {
        Employee o = employeeService.getEmployeeByEmail(email);
        EmployeeRequestDto EmployeeRequest = new EmployeeRequestDto();
        EmployeeRequest.setPassword(o.getName());
        EmployeeRequest.setName(password); //Asked TA no need for validation
        EmployeeRequest.setEmail(email);
        EmployeeRequest.setHourlyWage(o.getHourlyWage());
        EmployeeRequest.setJobTitle(o.getJobTitle());
        @Valid EmployeeRequestDto s = EmployeeRequest;
        Employee o_updated = EmployeeRequest.toModel();
        o_updated = employeeService.updateEmployee(o_updated);
        return new ResponseEntity<EmployeeResponseDto>(new EmployeeResponseDto(o_updated), HttpStatus.OK);

    }

    @PutMapping(value = {"/employee/update/name/{email}"})
    public ResponseEntity<EmployeeResponseDto> updateEmployeeName(@PathVariable String email, @RequestParam String name)
    {
        Employee o = employeeService.getEmployeeByEmail(email);
        EmployeeRequestDto EmployeeRequest = new EmployeeRequestDto();
        EmployeeRequest.setPassword(o.getPassword());
        EmployeeRequest.setName(name); //Asked TA no need for validation
        EmployeeRequest.setEmail(email);
        EmployeeRequest.setHourlyWage(o.getHourlyWage());
        EmployeeRequest.setJobTitle(o.getJobTitle());
        @Valid EmployeeRequestDto s = EmployeeRequest;
        Employee o_updated = EmployeeRequest.toModel();
        o_updated = employeeService.updateEmployee(o_updated);
        return new ResponseEntity<EmployeeResponseDto>(new EmployeeResponseDto(o_updated), HttpStatus.OK);

    }

    @PutMapping(value = {"/employee/update/job/{email}"})
    public ResponseEntity<EmployeeResponseDto> updateEmployeeJobTitle(@PathVariable String email, @RequestParam String jobTitle)
    {
        Employee o = employeeService.getEmployeeByEmail(email);
        EmployeeRequestDto EmployeeRequest = new EmployeeRequestDto();
        EmployeeRequest.setPassword(o.getPassword());
        EmployeeRequest.setName(o.getName()); //Asked TA no need for validation
        EmployeeRequest.setEmail(email);
        EmployeeRequest.setJobTitle(jobTitle);
        EmployeeRequest.setHourlyWage(o.getHourlyWage());
        @Valid EmployeeRequestDto s = EmployeeRequest;
        Employee o_updated = EmployeeRequest.toModel();
        o_updated = employeeService.updateEmployee(o_updated);
        return new ResponseEntity<EmployeeResponseDto>(new EmployeeResponseDto(o_updated), HttpStatus.OK);

    }

    @PutMapping(value = {"/employee/update/wage/{email}"})
    public ResponseEntity<EmployeeResponseDto> updateEmployeeHourlyWage(@PathVariable String email, @RequestParam Double hourlyWage)
    {
        Employee o = employeeService.getEmployeeByEmail(email);
        EmployeeRequestDto employeeRequest = new EmployeeRequestDto();
        employeeRequest.setPassword(o.getPassword());
        employeeRequest.setName(o.getName()); //Asked TA no need for validation
        employeeRequest.setEmail(email);
        employeeRequest.setJobTitle(o.getJobTitle());
        employeeRequest.setHourlyWage(hourlyWage);
        @Valid EmployeeRequestDto s = employeeRequest;
        Employee o_updated = employeeRequest.toModel();
        o_updated = employeeService.updateEmployee(o_updated);
        return new ResponseEntity<EmployeeResponseDto>(new EmployeeResponseDto(o_updated), HttpStatus.OK);

    }

    @DeleteMapping("/employee/delete/{email}")
    public void deleteEmployee(@PathVariable String email) {
        employeeService.deleteEmployeeAccount(email);
    }



}
