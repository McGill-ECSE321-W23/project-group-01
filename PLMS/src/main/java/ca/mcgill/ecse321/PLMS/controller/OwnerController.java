package ca.mcgill.ecse321.PLMS.controller;

import ca.mcgill.ecse321.PLMS.dto.OwnerRequestDto;
import ca.mcgill.ecse321.PLMS.dto.OwnerResponseDto;
import ca.mcgill.ecse321.PLMS.model.Owner;
import ca.mcgill.ecse321.PLMS.service.OwnerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    /**
     * Returns a list of all owners
     * @return all owners
     */

    @GetMapping("/owners")
    public Iterable<OwnerResponseDto> getAllOwners() {
        return StreamSupport.stream(ownerService.getAllOwners().spliterator(), false).map(OwnerResponseDto::new).collect(Collectors.toList());
    }

    /**
     * Returns the owner based on their Id
     * Pass in an arguments by using /owner={email}
     * @return the owner with Email, Password, Name
     */

    @GetMapping(value = {"/owner", "/owner/"})
    public ResponseEntity<OwnerResponseDto> getOwnerByEmail(@RequestParam String email) {
        return new ResponseEntity<OwnerResponseDto>(new OwnerResponseDto(ownerService.getOwnerByEmail(email)), HttpStatus.OK);
    }

    /**
     * Creates a new owner
     *
     * @return the dto response of the new owner
     */

    @PostMapping("/owner/create")
    public ResponseEntity<OwnerResponseDto> createOwner(@Valid @RequestBody OwnerRequestDto ownerRequest)
    {
        Owner owner = ownerRequest.toModel(); // 1. You pass in a request, validates the constraints, creates an owner if they pass
        owner =  ownerService.createOwnerAccount(owner); // 2. You use the service class to check if it exists and save it
        return new ResponseEntity<OwnerResponseDto>(new OwnerResponseDto(owner), HttpStatus.CREATED); //3. You mask the model by returning a Response
    }

    @PutMapping("/owner/update")
    public ResponseEntity<OwnerResponseDto> updateOwner(@Valid @RequestBody OwnerRequestDto ownerRequest) {

        Owner owner = ownerRequest.toModel();
        owner = ownerService.updateOwnerAccount(owner);
        return new ResponseEntity<OwnerResponseDto>(new OwnerResponseDto(owner), HttpStatus.OK);
    }







}
