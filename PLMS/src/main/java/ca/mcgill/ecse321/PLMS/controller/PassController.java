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

import ca.mcgill.ecse321.PLMS.dto.PassDto;
import ca.mcgill.ecse321.PLMS.model.Pass;
import jakarta.validation.Valid;


@CrossOrigin(origins = "*")
@RestController
public class PassController {

    @Autowired
    private PassService passService;

    /**
     * Gets all passes.
     * 
     * @return All passes.
     */
    @GetMapping("/pass")
    public Iterable<PassDto> getAllPasses(){
        return StreamSupport.stream(passService.getAllPasses().spliterator(), false).map(f -> new
         PassDto(f)).collect(Collectors.toList());
    }

    /**
     * Gets a pass by the pass id
     * 
     * @return pass with id
     */
    @GetMapping("/pass/{id}")
    public ResponseEntity<PassDto> getPassById(@PathVariable int id){
    Pass pass = passService.getPassById(id);
    PassDto responseBody = new PassDto(pass);
    return new ResponseEntity<PassDto>(responseBody, HttpStatus.OK);
    }

    /**
     * Gets all passes at the floor floorNumber (nactive passes filtered out in service layer)
     * 
     * @return passes with floor floorNumber
     */
    @GetMapping("/pass{floorNumber}")
    public Iterable<PassDto> getPassesByFloor(){
        return StreamSupport.stream(passService.getPassesByFloor().spliterator(), false).map(f -> new
         PassDto(f)).collect(Collectors.toList());
    }

    /**
     * Creates a pass.
     * 
     * @return PassDto of the created pass
     */
    @PostMapping("/pass")
    public ResponseEntity<FloorDto> createPass(@Valid @RequestBody PassDto passDto){
        Pass pass = passDto.toModel();
        pass = passService.createPass(pass);
        PassDto responseBody = new PassDto(pass);
        return new ResponseEntity<PassDto>(responseBody, HttpStatus.CREATED);

    }

    /**
     * Allows updates for all pass variables
     * 
     * @return pass with updated values
     */
    @PutMapping("/pass/{id}")
    public ResponseEntity<PassDto> updatePassInfo(@PathVariable int id, RequestBody PassDto passDto){
        Pass pass = passDto.toModel();
        pass = passService.updatePass(pass);
        PassDto responseBody = new PassDto(pass);
        return new ResponseEntity<PassDto>(responseBody, HttpStatus.CREATED);
    }
    



    
}
