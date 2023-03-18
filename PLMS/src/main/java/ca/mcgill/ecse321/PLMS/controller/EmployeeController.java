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

import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class EmployeeController {

    @Autowired
    private EmployeeService EmployeeService;

    /**
     * Returns a list of all Employees
     * @return all Employees
     */

    @GetMapping("/Employees")
    public Iterable<EmployeeResponseDto> getAllEmployees() {
        return StreamSupport.stream(EmployeeService.getAllEmployees().spliterator(), false).map(EmployeeResponseDto::new).collect(Collectors.toList());
    }

    /**
     * Returns the Employee based on their Id
     * Pass in an arguments by using /Employee={email}
     * @return the Employee with Email, Password, Name
     */

    @GetMapping(value = {"/Employee", "/Employee/"})
    public ResponseEntity<EmployeeResponseDto> getEmployeeByEmail(@RequestParam String email) {
        return new ResponseEntity<EmployeeResponseDto>(new EmployeeResponseDto(EmployeeService.getEmployeeByEmail(email)), HttpStatus.ACCEPTED);
    }

    /**
     * Creates a new Employee
     *
     * @return the dto response of the new Employee
     */

    @PostMapping("/Employee")
    public ResponseEntity<EmployeeResponseDto> createEmployee(@Valid @RequestBody EmployeeRequestDto EmployeeRequest)
    {
        Employee Employee = EmployeeRequest.toModel(); // 1. You pass in a request, validates the constraints, creates an Employee if they pass
        Employee =  EmployeeService.createEmployeeAccount(Employee); // 2. You use the service class to check if it exists and save it
        return new ResponseEntity<EmployeeResponseDto>(new EmployeeResponseDto(Employee), HttpStatus.OK); //3. You mask the model by returning a Response
    }

    @PutMapping(value = {"/Employee/{email}"})
    public ResponseEntity<EmployeeResponseDto> updateEmployeeEmail(@PathVariable String email, @RequestParam String n_email)
    {
        Employee o = EmployeeService.getEmployeeByEmail(email);
        EmployeeRequestDto EmployeeRequest = new EmployeeRequestDto();
        EmployeeRequest.setPassword(o.getName());
        EmployeeRequest.setName(o.getPassword());
        EmployeeRequest.setEmail(n_email);
        EmployeeRequest.setHourlyWage(o.getHourlyWage());
        EmployeeRequest.setJobTitle(o.getJobTitle());

        Employee o_updated = EmployeeRequest.toModel();
        o_updated = EmployeeService.updateEmployee(o_updated);
        return new ResponseEntity<EmployeeResponseDto>(new EmployeeResponseDto(o_updated), HttpStatus.OK);

    }

    @PutMapping(value = {"/Employee/{email}"})
    public ResponseEntity<EmployeeResponseDto> updateEmployeePassword(@PathVariable String email, @RequestParam String password)
    {
        Employee o = EmployeeService.getEmployeeByEmail(email);
        EmployeeRequestDto EmployeeRequest = new EmployeeRequestDto();
        EmployeeRequest.setPassword(o.getName());
        EmployeeRequest.setName(password); //Asked TA no need for validation
        EmployeeRequest.setEmail(email);
        EmployeeRequest.setHourlyWage(o.getHourlyWage());
        EmployeeRequest.setJobTitle(o.getJobTitle());

        Employee o_updated = EmployeeRequest.toModel();
        o_updated = EmployeeService.updateEmployee(o_updated);
        return new ResponseEntity<EmployeeResponseDto>(new EmployeeResponseDto(o_updated), HttpStatus.OK);

    }

    @PutMapping(value = {"/Employee/{email}"})
    public ResponseEntity<EmployeeResponseDto> updateEmployeeName(@PathVariable String email, @RequestParam String name)
    {
        Employee o = EmployeeService.getEmployeeByEmail(email);
        EmployeeRequestDto EmployeeRequest = new EmployeeRequestDto();
        EmployeeRequest.setPassword(o.getPassword());
        EmployeeRequest.setName(name); //Asked TA no need for validation
        EmployeeRequest.setEmail(email);
        EmployeeRequest.setHourlyWage(o.getHourlyWage());
        EmployeeRequest.setJobTitle(o.getJobTitle());

        Employee o_updated = EmployeeRequest.toModel();
        o_updated = EmployeeService.updateEmployee(o_updated);
        return new ResponseEntity<EmployeeResponseDto>(new EmployeeResponseDto(o_updated), HttpStatus.OK);

    }

    @PutMapping(value = {"/Employee/{email}"})
    public ResponseEntity<EmployeeResponseDto> updateEmployeeJobTitle(@PathVariable String email, @RequestBody String jobTitle)
    {
        Employee o = EmployeeService.getEmployeeByEmail(email);
        EmployeeRequestDto EmployeeRequest = new EmployeeRequestDto();
        EmployeeRequest.setPassword(o.getName());
        EmployeeRequest.setName(o.getPassword()); //Asked TA no need for validation
        EmployeeRequest.setEmail(email);
        EmployeeRequest.setJobTitle(jobTitle);
        EmployeeRequest.setHourlyWage(o.getHourlyWage());
        Employee o_updated = EmployeeRequest.toModel();
        o_updated = EmployeeService.updateEmployee(o_updated);
        return new ResponseEntity<EmployeeResponseDto>(new EmployeeResponseDto(o_updated), HttpStatus.OK);

    }

    @PutMapping(value = {"/Employee/{email}"})
    public ResponseEntity<EmployeeResponseDto> updateEmployeeHourlyWage(@PathVariable String email, @RequestParam Double hourlyWage)
    {
        Employee o = EmployeeService.getEmployeeByEmail(email);
        EmployeeRequestDto EmployeeRequest = new EmployeeRequestDto();
        EmployeeRequest.setPassword(o.getName());
        EmployeeRequest.setName(o.getPassword()); //Asked TA no need for validation
        EmployeeRequest.setEmail(email);
        EmployeeRequest.setJobTitle(o.getJobTitle());
        EmployeeRequest.setHourlyWage(hourlyWage);
        Employee o_updated = EmployeeRequest.toModel();
        o_updated = EmployeeService.updateEmployee(o_updated);
        return new ResponseEntity<EmployeeResponseDto>(new EmployeeResponseDto(o_updated), HttpStatus.OK);

    }

    @DeleteMapping("/employee/{email}")
    public void deleteEmployee(@PathVariable String email) {
        EmployeeService.deleteEmployeeAccount(email);
    }



}
