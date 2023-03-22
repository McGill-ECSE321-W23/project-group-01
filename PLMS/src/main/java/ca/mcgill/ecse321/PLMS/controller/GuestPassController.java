package ca.mcgill.ecse321.PLMS.controller;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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

import static java.util.Arrays.spliterator;


@CrossOrigin(origins = "*")
@RestController
public class GuestPassController {

    @Autowired
    private GuestPassService guestPassService;

    /**
     * Gets all guest passes.
     * 
     * @return All guest passes.
     */
    @GetMapping("/guestPass")
    public Iterable<GuestPassResponseDto> getAllGuestPasses(){
        return StreamSupport.stream(guestPassService.getAllGuestPasses().spliterator(), false).map(f -> new
        GuestPassResponseDto(f)).collect(Collectors.toList());
    }

    /**
     * Gets a guestpass by the guestpass id
     * 
     * @return GuestPassResponseDto of guestpass with id id
     */
    @GetMapping("/guestPass/{id}")
    public ResponseEntity<GuestPassResponseDto> getGuestPassById(@PathVariable int id){
    GuestPass guestPass = guestPassService.getGuestPassById(id);
    GuestPassResponseDto responseBody = new GuestPassResponseDto(guestPass);
    return new ResponseEntity<GuestPassResponseDto>(responseBody, HttpStatus.OK);
    }

    /**
     * Gets all guest passes at the floor floorNumber (inactive passes filtered out in service layer)
     * 
     * @return GuestPassResponseDto of guest passes with floor floorNumber
     */
    @GetMapping("/guestPass/floor/{floorNumber}")
    public Iterable<GuestPassResponseDto> getGuestPassesByFloor(@PathVariable int floorNumber){
        return StreamSupport.stream(guestPassService.getGuestPassesByFloor(floorNumber).spliterator(), false).map(f -> new
        GuestPassResponseDto((GuestPass) f)).collect(Collectors.toList());
    }

    /**
     * Gets all guest passes active on a date
     * 
     * @return GuestPassResponseDto of guest passes with date date
     */
    @GetMapping("/guestPass/date/{date}")
    public Iterable<GuestPassResponseDto> getGuestPassesByDate(@PathVariable Date date){
        List<GuestPassResponseDto> collect = StreamSupport.stream(guestPassService.getGuestPassesByDate(date).spliterator(), false).map(f -> new
                GuestPassResponseDto((GuestPass) f)).collect(Collectors.toList());
        return collect;
    }

    /**
     * Creates a guest pass.
     * 
     * @return GuestPassResponseDto of the created guest pass
     */
    @PostMapping("/guestPass")
    public ResponseEntity<GuestPassResponseDto> createGuestMonthlyPass(@Valid @RequestBody GuestPassRequestDto guestPassRequestDto){
        GuestPass guestPass = guestPassRequestDto.toModel();
        guestPass = guestPassService.createGuestPass(guestPass);
        GuestPassResponseDto responseBody = new GuestPassResponseDto(guestPass);
        return new ResponseEntity<GuestPassResponseDto>(responseBody, HttpStatus.CREATED);

    }

  
}
