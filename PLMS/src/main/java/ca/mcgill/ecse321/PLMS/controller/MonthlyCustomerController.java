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

@RestController
public class MonthlyCustomerController {
    @Autowired
    private MonthlyCustomerService monthlyCustomerService;

    /**
     * Returns a list of all MonthlyCustomers
     * @return all MonthlyCustomers
     */

    @GetMapping("/monthly/all")
    public Iterable<MonthlyCustomerResponseDto> getAllMonthlyCustomers() {
        return StreamSupport.stream(monthlyCustomerService.getAllMonthlyCustomers().spliterator(), false).map(MonthlyCustomerResponseDto::new).collect(Collectors.toList());
    }

    /**
     * Returns the MonthlyCustomer based on their Id
     * Pass in an arguments by using /MonthlyCustomer={email}
     * @return the MonthlyCustomer with Email, Password, Name
     */

    @GetMapping(value = {"/monthly", "/monthly/"})
    public ResponseEntity<MonthlyCustomerResponseDto> getMonthlyCustomerByEmail(@RequestParam String email) {
        return new ResponseEntity<MonthlyCustomerResponseDto>(new MonthlyCustomerResponseDto(monthlyCustomerService.getMonthlyCustomerByEmail(email)), HttpStatus.ACCEPTED);
    }

    /**
     * Creates a new MonthlyCustomer
     *
     * @return the dto response of the new MonthlyCustomer
     */

    @PostMapping("/monthly/create")
    public ResponseEntity<MonthlyCustomerResponseDto> createMonthlyCustomer(@Valid @RequestBody MonthlyCustomerRequestDto MonthlyCustomerRequest)
    {
        MonthlyCustomer MonthlyCustomer = MonthlyCustomerRequest.toModel(); // 1. You pass in a request, validates the constraints, creates an MonthlyCustomer if they pass
        MonthlyCustomer =  monthlyCustomerService.createMonthlyCustomerAccount(MonthlyCustomer); // 2. You use the service class to check if it exists and save it
        return new ResponseEntity<MonthlyCustomerResponseDto>(new MonthlyCustomerResponseDto(MonthlyCustomer), HttpStatus.OK); //3. You mask the model by returning a Response
    }

    @PutMapping("/monthly/update")
    public ResponseEntity<MonthlyCustomerResponseDto> updateMonthlyCustomer(@Valid @RequestBody MonthlyCustomerRequestDto monthlyCustomerRequest)
    {
        MonthlyCustomer monthlyCustomer = monthlyCustomerRequest.toModel();
        monthlyCustomer = monthlyCustomerService.updateMonthlyCustomer(monthlyCustomer);
        return new ResponseEntity<MonthlyCustomerResponseDto>(new MonthlyCustomerResponseDto(monthlyCustomer), HttpStatus.OK);

    }

    @PutMapping(value = {"/monthly/{email}/{password}"})
    public ResponseEntity<MonthlyCustomerResponseDto> updateMonthlyCustomerPassword(@PathVariable String email, @PathVariable String password)
    {
        MonthlyCustomer o = monthlyCustomerService.getMonthlyCustomerByEmail(email);
        MonthlyCustomerRequestDto MonthlyCustomerRequest = new MonthlyCustomerRequestDto();
        MonthlyCustomerRequest.setPassword(password);
        MonthlyCustomerRequest.setName(o.getName()); //Asked TA no need for validation
        MonthlyCustomerRequest.setEmail(email);
        @Valid MonthlyCustomer s = o;
        MonthlyCustomer o_updated = MonthlyCustomerRequest.toModel();
        o_updated = monthlyCustomerService.updateMonthlyCustomer(o_updated);
        return new ResponseEntity<MonthlyCustomerResponseDto>(new MonthlyCustomerResponseDto(o_updated), HttpStatus.OK);

    }

    @PutMapping(value = {"/monthly/{email}/change/{name}"})
    public ResponseEntity<MonthlyCustomerResponseDto> updateMonthlyCustomerName(@PathVariable String email, @PathVariable String name)
    {
        MonthlyCustomer o = monthlyCustomerService.getMonthlyCustomerByEmail(email);
        MonthlyCustomerRequestDto MonthlyCustomerRequest = new MonthlyCustomerRequestDto();
        MonthlyCustomerRequest.setPassword(o.getPassword());
        MonthlyCustomerRequest.setName(name); //Asked TA no need for validation
        MonthlyCustomerRequest.setEmail(email);
        @Valid MonthlyCustomer s = o;
        MonthlyCustomer o_updated = MonthlyCustomerRequest.toModel();
        o_updated = monthlyCustomerService.updateMonthlyCustomer(o_updated);
        return new ResponseEntity<MonthlyCustomerResponseDto>(new MonthlyCustomerResponseDto(o_updated), HttpStatus.OK);

    }

}
