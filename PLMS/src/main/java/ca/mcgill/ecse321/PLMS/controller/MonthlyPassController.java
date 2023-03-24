// package ca.mcgill.ecse321.PLMS.controller;

// import java.util.stream.Collectors;
// import java.util.stream.StreamSupport;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RestController;

// import ca.mcgill.ecse321.PLMS.dto.MonthlyPassRequestDto;
// import ca.mcgill.ecse321.PLMS.dto.MonthlyPassResponseDto;

// import ca.mcgill.ecse321.PLMS.model.MonthlyPass;
// //import ca.mcgill.ecse321.PLMS.service.MonthlyPassService;
// import jakarta.validation.Valid;


// @CrossOrigin(origins = "*")
// @RestController
// public class MonthlyPassController {

//     @Autowired
//     private MonthlyPassService monthlyPassService;

//     /**
//      * Gets all monthly passes.
//      *
//      * @return MonthlyPassResponseDto of all monthly passes.
//      */
//     @GetMapping("/pass")
//     public Iterable<MonthlyPassResponseDto> getAllMonthlyPasses(){
//         return StreamSupport.stream(monthlypassService.getAllMonthlyPasses().spliterator(), false).map(f -> new
//         MonthlyPassResponseDto(f)).collect(Collectors.toList());
//     }

//     /**
//      * Gets a monthlypass by the monthlypass id
//      *
//      * @return monthlypass with id id
//      */
//     @GetMapping("/monthlypass/{id}")
//     public ResponseEntity<MonthlyPassResponseDto> getPassById(@PathVariable int id){
//     MonthlyPass monthlyPass = monthlyPassService.getMonthlyPassById(id);
//     MonthlyPassResponseDto responseBody = new MonthlyPassResponseDto(monthlyPass);
//     return new ResponseEntity<MonthlyPassResponseDto>(responseBody, HttpStatus.OK);
//     }

//     /**
//      * Gets all monthly passes at the floor floorNumber (inactive passes filtered out in service layer)
//      *
//      * @return MonthlyPassResponseDto of monthly passes with floor floorNumber
//      */
//     @GetMapping("/monthlypass/floor/{floorNumber}")
//     public Iterable<MonthlyPassResponseDto> getMonthlyPassesByFloor(){
//         return StreamSupport.stream(monthlyPassService.getMonthlyPassesByFloor().spliterator(), false).map(f -> new
//         MonthlyPassResponseDto(f)).collect(Collectors.toList());
//     }

//     /**
//      * Gets all monthly passes by monthly customer
//      *
//      * @return MonthlyPassResponseDto of monthly passes of monthly customer with email email
//      */
//     @GetMapping("/monthlypass/customer/{email}")
//     public Iterable<MonthlyPassResponseDto> getMonthlyPassesByMonthlyCustomer(){
//         return StreamSupport.stream(monthlyPassService.getMonthlyPassesByMonthlyCustomer().spliterator(), false).map(f -> new
//         MonthlyPassResponseDto(f)).collect(Collectors.toList());
//     }

//     /**
//      * Creates a monthly pass.
//      *
//      * @return MonthlyPassResponseDto of the created monthly pass
//      */
//     @PostMapping("/monthlypass")
//     public ResponseEntity<MonthlyPassResponseDto> createMonthlyPass(@Valid @RequestBody MonthlyPassRequestDto monthlyPassRequestDto){
//         MonthlyPass monthlyPass = monthlyPassRequestDto.toModel();
//         monthlyPass = monthlyPassService.createMonthlyPass(monthlyPass);
//         MonthlyPassResponseDto responseBody = new MonthlyPassResponseDto(monthlyPass);
//         return new ResponseEntity<MonthlyPassResponseDto>(responseBody, HttpStatus.CREATED);

//     }


// }
