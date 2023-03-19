package ca.mcgill.ecse321.PLMS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.PLMS.dto.ParkingLotResponseDto;
import ca.mcgill.ecse321.PLMS.model.ParkingLot;
import ca.mcgill.ecse321.PLMS.service.ParkingLotService;

@RestController
public class ParkingLotController {

    @Autowired
	private ParkingLotService parkingLotService;

    @GetMapping("/parkingLot")
	public ResponseEntity<ParkingLotResponseDto> getParkingLot() {
		ParkingLot p = parkingLotService.getPersonById(id);
		ParkingLotResponseDto responseBody = new ParkingLotResponseDto(person);
		return new ResponseEntity<ParkingLotResponseDto>(responseBody, HttpStatus.OK);
	}

    @PostMapping("/person")
	public ResponseEntity<ParkingLotResponseDto> createPerson(@Valid @RequestBody PersonRequestDto personDto) {
		Person person = personDto.toModel();
		person = personService.createPerson(person);
		PersonResponseDto responseBody = new PersonResponseDto(person);
		return new ResponseEntity<PersonResponseDto>(responseBody, HttpStatus.CREATED);
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

}
