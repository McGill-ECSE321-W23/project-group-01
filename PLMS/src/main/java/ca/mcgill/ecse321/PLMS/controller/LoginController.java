package ca.mcgill.ecse321.PLMS.controller;


import ca.mcgill.ecse321.PLMS.dto.EmployeeResponseDto;
import ca.mcgill.ecse321.PLMS.dto.MonthlyCustomerResponseDto;
import ca.mcgill.ecse321.PLMS.dto.OwnerResponseDto;
import ca.mcgill.ecse321.PLMS.service.EmployeeService;
import ca.mcgill.ecse321.PLMS.service.MonthlyCustomerService;
import ca.mcgill.ecse321.PLMS.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
public class LoginController {

    @Autowired
    private OwnerService ownerService;
    @Autowired
    private MonthlyCustomerService monthlyCustomerService;
    @Autowired
    private EmployeeService employeeService;

//    @GetMapping("/login")
//    public ResponseEntity<?> getUser(@RequestBody AccountRequestDto accountRequest){
//        if (accountRequest.getUser().equals("Owner"))
//            return new ResponseEntity<OwnerResponseDto>(new OwnerResponseDto(ownerService.getOwnerByEmailAndPassword(accountRequest.getEmail(), accountRequest.getPassword())), HttpStatus.OK);
//        if (accountRequest.getUser().equals("Employee"))
//            return new ResponseEntity<EmployeeResponseDto>(new EmployeeResponseDto(employeeService.getEmployeeByEmailAndPassword(accountRequest.getEmail(), accountRequest.getPassword())), HttpStatus.OK);
//        else
//            return new ResponseEntity<MonthlyCustomerResponseDto>(new MonthlyCustomerResponseDto(monthlyCustomerService.getMonthlyCustomerByEmailAndPassword(accountRequest.getEmail(), accountRequest.getPassword())), HttpStatus.OK);

    @GetMapping("/login/{user}")
    public ResponseEntity<?> getUser(@PathVariable String user, @RequestParam String email, @RequestParam String password){
        if (user.equals("Owner"))
            return new ResponseEntity<OwnerResponseDto>(new OwnerResponseDto(ownerService.getOwnerByEmailAndPassword(email, password)), HttpStatus.OK);
        if (user.equals("Employee"))
            return new ResponseEntity<EmployeeResponseDto>(new EmployeeResponseDto(employeeService.getEmployeeByEmailAndPassword(email, password)), HttpStatus.OK);
        else
            return new ResponseEntity<MonthlyCustomerResponseDto>(new MonthlyCustomerResponseDto(monthlyCustomerService.getMonthlyCustomerByEmailAndPassword(email, password)), HttpStatus.OK);


    }




}
