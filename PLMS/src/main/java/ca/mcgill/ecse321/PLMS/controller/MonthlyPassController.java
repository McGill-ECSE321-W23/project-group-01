package ca.mcgill.ecse321.PLMS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.PLMS.dto.MonthlyPassDto;
import ca.mcgill.ecse321.PLMS.model.MonthlyPass;
import ca.mcgill.ecse321.PLMS.service.MonthlyPassService;
import jakarta.validation.Valid;


@CrossOrigin(origins = "*")
@RestController
public class MonthlyPassController {

    @Autowired
    private MonthlyPassService monthlyPassService;

    /**
     * Gets all monthly passes.
     * 
     * @return All monthly passes.
     */
    @GetMapping("/pass")
    public Iterable<MonthlyPassDto> getAllMonthlyPasses(){
        return StreamSupport.stream(monthlypassService.getAllMonthlyPasses().spliterator(), false).map(f -> new
         MonthlyPassDto(f)).collect(Collectors.toList());
    }

    /**
     * Gets a monthlypass by the monthlypass id
     * 
     * @return monthlypass with id id
     */
    @GetMapping("/monthlypass/{id}")
    public ResponseEntity<PassDto> getPassById(@PathVariable int id){
    Pass pass = monthlyPassService.getMonthlyPassById(id);
    PassDto responseBody = new MonthlyPassDto(pass);
    return new ResponseEntity<PassDto>(responseBody, HttpStatus.OK);
    }

    /**
     * Gets all monthly passes at the floor floorNumber (inactive passes filtered out in service layer)
     * 
     * @return monthly passes with floor floorNumber
     */
    @GetMapping("/monthlypass{floorNumber}")
    public Iterable<MonthlyPassDto> getMonthlyPassesByFloor(){
        return StreamSupport.stream(monthlyPassService.getMonthlyPassesByFloor().spliterator(), false).map(f -> new
        MonthlyPassDto(f)).collect(Collectors.toList());
    }

    /**
     * Gets all monthly passes by monthly customer
     * 
     * @return monthly passes of monthly customer with email email
     */
    @GetMapping("/monthlypass{email}")
    public Iterable<MonthlyPassDto> getMonthlyPassesByMonthlyCustomer(){
        return StreamSupport.stream(monthlyPassService.getMonthlyPassesByMonthlyCustomer().spliterator(), false).map(f -> new
        MonthlyPassDto(f)).collect(Collectors.toList());
    }

    /**
     * Creates a monthly pass.
     * 
     * @return MonthlyPassDto of the created monthly pass
     */
    @PostMapping("/monthlypass")
    public ResponseEntity<PassDto> createMonthlyPass(@Valid @RequestBody MonthlyPassDto monthlyPassDto){
        MonthlyPass monthlyPass = monthlyPassDto.toModel();
        monthlyPass = monthlyPassService.createMonthlyPass(monthlyPass);
        MonthlyPassDto responseBody = new MonthlyPassDto(pass);
        return new ResponseEntity<MonthlyPassDto>(responseBody, HttpStatus.CREATED);

    }

  
}
