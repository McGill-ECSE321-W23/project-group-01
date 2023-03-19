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

import ca.mcgill.ecse321.PLMS.dto.GuestPassRequestDto;
import ca.mcgill.ecse321.PLMS.dto.GuestPassResponseDto;
import ca.mcgill.ecse321.PLMS.model.GuestPass;
import ca.mcgill.ecse321.PLMS.service.GuestPassService;
import jakarta.validation.Valid;


@CrossOrigin(origins = "*")
@RestController
public class GuestPassController {

    @Autowired
    private GuestPassService GuestPassService;

    /**
     * Gets all guest passes.
     * 
     * @return All guest passes.
     */
    @GetMapping("/guestpass")
    public Iterable<GuestPassDto> getAllGuestPasses(){
        return StreamSupport.stream(guestpassService.getAllGuestPasses().spliterator(), false).map(f -> new
        GuestPassDto(f)).collect(Collectors.toList());
    }

    /**
     * Gets a guestpass by the guestpass id
     * 
     * @return guestpass with id id
     */
    @GetMapping("/guestpass/{id}")
    public ResponseEntity<PassDto> getGuestPassById(@PathVariable int id){
    Pass pass = monthlyPassService.getGuestPassById(id);
    PassDto responseBody = new MonthlyPassDto(pass);
    return new ResponseEntity<PassDto>(responseBody, HttpStatus.OK);
    }

    /**
     * Gets all guest passes at the floor floorNumber (inactive passes filtered out in service layer)
     * 
     * @return guest passes with floor floorNumber
     */
    @GetMapping("/guestpass{floorNumber}")
    public Iterable<GuestPassDto> getGuestPassesByFloor(){
        return StreamSupport.stream(guestPassService.getGuestPassesByFloor().spliterator(), false).map(f -> new
        GuestPassDto(f)).collect(Collectors.toList());
    }

    /**
     * Gets all guest passes active on a date
     * 
     * @return guest passes with date date
     */
    @GetMapping("/guestpass{date}")
    public Iterable<GuestPassDto> getGuestPassesByDate(){
        return StreamSupport.stream(guestPassService.getGuestPassesByDate().spliterator(), false).map(f -> new
        GuestPassDto(f)).collect(Collectors.toList());
    }

    /**
     * Creates a guest pass.
     * 
     * @return GuestPassDto of the created guest pass
     */
    @PostMapping("/guestpass")
    public ResponseEntity<GuestPassDto> createGuestMonthlyPass(@Valid @RequestBody GuestPassDto guestPassDto){
        GuestPass guestPass = guestPassDto.toModel();
        guestPass = guestPassService.createGuestPass(guestPass);
        GuestPassDto responseBody = new GuestPassDto(pass);
        return new ResponseEntity<GuestPassDto>(responseBody, HttpStatus.CREATED);

    }

  
}
