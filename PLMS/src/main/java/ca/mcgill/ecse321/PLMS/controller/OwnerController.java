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
        return new ResponseEntity<OwnerResponseDto>(new OwnerResponseDto(ownerService.getOwnerByEmail(email)), HttpStatus.ACCEPTED);
    }

    /**
     * Creates a new owner
     *
     * @return the dto response of the new owner
     */

    @PostMapping("/owner")
    public ResponseEntity<OwnerResponseDto> createOwner(@Valid @RequestBody OwnerRequestDto ownerRequest)
    {
        Owner owner = ownerRequest.toModel(); // 1. You pass in a request, validates the constraints, creates an owner if they pass
        owner =  ownerService.createOwnerAccount(owner); // 2. You use the service class to check if it exists and save it
        return new ResponseEntity<OwnerResponseDto>(new OwnerResponseDto(owner), HttpStatus.OK); //3. You mask the model by returning a Response
    }

    // @PutMapping(value = {"/owner/{email}"})
    // public ResponseEntity<OwnerResponseDto> updateOwnerEmail(@PathVariable String email, @RequestParam String n_email)
    // {
    //     Owner o = ownerService.getOwnerByEmail(email);
    //     OwnerRequestDto ownerRequest = new OwnerRequestDto();
    //     ownerRequest.setPassword(o.getName());
    //     ownerRequest.setName(o.getPassword());
    //     ownerRequest.setEmail(n_email);

    //     Owner o_updated = ownerRequest.toModel();
    //     o_updated = ownerService.updateOwnerAccount(o_updated);
    //     return new ResponseEntity<OwnerResponseDto>(new OwnerResponseDto(o_updated), HttpStatus.OK);

    // }

    // @PutMapping(value = {"/owner/{email}"})
    // public ResponseEntity<OwnerResponseDto> updateOwnerPassword(@PathVariable String email, @RequestParam String password)
    // {
    //     Owner o = ownerService.getOwnerByEmail(email);
    //     OwnerRequestDto ownerRequest = new OwnerRequestDto();
    //     ownerRequest.setPassword(password);
    //     ownerRequest.setName(o.getName()); //Asked TA no need for validation
    //     ownerRequest.setEmail(email);

    //     Owner o_updated = ownerRequest.toModel();
    //     o_updated = ownerService.updateOwnerAccount(o_updated);
    //     return new ResponseEntity<OwnerResponseDto>(new OwnerResponseDto(o_updated), HttpStatus.OK);

    // }

    @PutMapping(value = {"/owner/{email}"})
    public ResponseEntity<OwnerResponseDto> updateOwnerName(@PathVariable String email, @RequestParam String name)
    {
        Owner o = ownerService.getOwnerByEmail(email);
        OwnerRequestDto ownerRequest = new OwnerRequestDto();
        ownerRequest.setPassword(o.getPassword());
        ownerRequest.setName(name); //Asked TA no need for validation
        ownerRequest.setEmail(email);

        Owner o_updated = ownerRequest.toModel();
        o_updated = ownerService.updateOwnerAccount(o_updated);
        return new ResponseEntity<OwnerResponseDto>(new OwnerResponseDto(o_updated), HttpStatus.OK);

    }







}
