package ca.mcgill.ecse321.PLMS.controller;

import ca.mcgill.ecse321.PLMS.dto.MonthlyCustomerRequestDto;
import ca.mcgill.ecse321.PLMS.dto.MonthlyCustomerResponseDto;
import ca.mcgill.ecse321.PLMS.model.MonthlyCustomer;
import ca.mcgill.ecse321.PLMS.service.MonthlyCustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MonthlyCustomerController {
    @Autowired
    private MonthlyCustomerService MonthlyCustomerService;

    /**
     * Returns a list of all MonthlyCustomers
     * @return all MonthlyCustomers
     */

    @GetMapping("/MonthlyCustomers")
    public Iterable<MonthlyCustomerResponseDto> getAllMonthlyCustomers() {
        return StreamSupport.stream(MonthlyCustomerService.getAllMonthlyCustomers().spliterator(), false).map(MonthlyCustomerResponseDto::new).collect(Collectors.toList());
    }

    /**
     * Returns the MonthlyCustomer based on their Id
     * Pass in an arguments by using /MonthlyCustomer={email}
     * @return the MonthlyCustomer with Email, Password, Name
     */

    @GetMapping(value = {"/MonthlyCustomer", "/MonthlyCustomer/"})
    public ResponseEntity<MonthlyCustomerResponseDto> getMonthlyCustomerByEmail(@RequestParam String email) {
        return new ResponseEntity<MonthlyCustomerResponseDto>(new MonthlyCustomerResponseDto(MonthlyCustomerService.getMonthlyCustomerByEmail(email)), HttpStatus.ACCEPTED);
    }

    /**
     * Creates a new MonthlyCustomer
     *
     * @return the dto response of the new MonthlyCustomer
     */

    @PostMapping("/MonthlyCustomer")
    public ResponseEntity<MonthlyCustomerResponseDto> createMonthlyCustomer(@Valid @RequestBody MonthlyCustomerRequestDto MonthlyCustomerRequest)
    {
        MonthlyCustomer MonthlyCustomer = MonthlyCustomerRequest.toModel(); // 1. You pass in a request, validates the constraints, creates an MonthlyCustomer if they pass
        MonthlyCustomer =  MonthlyCustomerService.createMonthlyCustomerAccount(MonthlyCustomer); // 2. You use the service class to check if it exists and save it
        return new ResponseEntity<MonthlyCustomerResponseDto>(new MonthlyCustomerResponseDto(MonthlyCustomer), HttpStatus.OK); //3. You mask the model by returning a Response
    }

    @PutMapping(value = {"/MonthlyCustomer/{email}"})
    public ResponseEntity<MonthlyCustomerResponseDto> updateMonthlyCustomerEmail(@PathVariable String email, @RequestParam String n_email)
    {
        MonthlyCustomer o = MonthlyCustomerService.getMonthlyCustomerByEmail(email);
        MonthlyCustomerRequestDto MonthlyCustomerRequest = new MonthlyCustomerRequestDto();
        MonthlyCustomerRequest.setPassword(o.getName());
        MonthlyCustomerRequest.setName(o.getPassword());
        MonthlyCustomerRequest.setEmail(n_email);

        MonthlyCustomer o_updated = MonthlyCustomerRequest.toModel();
        o_updated = MonthlyCustomerService.updateMonthlyCustomer(o_updated);
        return new ResponseEntity<MonthlyCustomerResponseDto>(new MonthlyCustomerResponseDto(o_updated), HttpStatus.OK);

    }

    @PutMapping(value = {"/MonthlyCustomer/{email}"})
    public ResponseEntity<MonthlyCustomerResponseDto> updateMonthlyCustomerPassword(@PathVariable String email, @RequestParam String password)
    {
        MonthlyCustomer o = MonthlyCustomerService.getMonthlyCustomerByEmail(email);
        MonthlyCustomerRequestDto MonthlyCustomerRequest = new MonthlyCustomerRequestDto();
        MonthlyCustomerRequest.setPassword(o.getName());
        MonthlyCustomerRequest.setName(password); //Asked TA no need for validation
        MonthlyCustomerRequest.setEmail(email);

        MonthlyCustomer o_updated = MonthlyCustomerRequest.toModel();
        o_updated = MonthlyCustomerService.updateMonthlyCustomer(o_updated);
        return new ResponseEntity<MonthlyCustomerResponseDto>(new MonthlyCustomerResponseDto(o_updated), HttpStatus.OK);

    }

    @PutMapping(value = {"/MonthlyCustomer/{email}"})
    public ResponseEntity<MonthlyCustomerResponseDto> updateMonthlyCustomerName(@PathVariable String email, @RequestParam String name)
    {
        MonthlyCustomer o = MonthlyCustomerService.getMonthlyCustomerByEmail(email);
        MonthlyCustomerRequestDto MonthlyCustomerRequest = new MonthlyCustomerRequestDto();
        MonthlyCustomerRequest.setPassword(o.getPassword());
        MonthlyCustomerRequest.setName(name); //Asked TA no need for validation
        MonthlyCustomerRequest.setEmail(email);

        MonthlyCustomer o_updated = MonthlyCustomerRequest.toModel();
        o_updated = MonthlyCustomerService.updateMonthlyCustomer(o_updated);
        return new ResponseEntity<MonthlyCustomerResponseDto>(new MonthlyCustomerResponseDto(o_updated), HttpStatus.OK);

    }

}
