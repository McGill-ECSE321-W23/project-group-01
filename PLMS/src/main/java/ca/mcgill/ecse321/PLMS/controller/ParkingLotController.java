package ca.mcgill.ecse321.PLMS.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.PLMS.dto.ParkingLotRequestDto;
import ca.mcgill.ecse321.PLMS.dto.ParkingLotResponseDto;
import ca.mcgill.ecse321.PLMS.model.ParkingLot;
import ca.mcgill.ecse321.PLMS.service.ParkingLotService;
import jakarta.validation.Valid;

@RestController
public class ParkingLotController {

    @Autowired
	private ParkingLotService parkingLotService;

    @GetMapping("/parkingLot")
	public ResponseEntity<ParkingLotResponseDto> getParkingLot() {
		ParkingLot p = parkingLotService.getParkingLot();
		ParkingLotResponseDto responseBody = new ParkingLotResponseDto(p);
		return new ResponseEntity<ParkingLotResponseDto>(responseBody, HttpStatus.OK);
	}

    @PostMapping("/parkingLot/creation")
	public ResponseEntity<ParkingLotResponseDto> createParkingLot(@Valid @RequestBody ParkingLotRequestDto parkingLotRequestDto) {
		ParkingLot p = parkingLotRequestDto.toModel();
		p = parkingLotService.createParkingLot(p);
		ParkingLotResponseDto responseBody = new ParkingLotResponseDto(p);
		return new ResponseEntity<ParkingLotResponseDto>(responseBody, HttpStatus.CREATED);
	}

    @PutMapping(value = {"/parkingLot/update"})
    public ResponseEntity<ParkingLotResponseDto> updateParkingLot(@Valid @RequestBody ParkingLotRequestDto parkingLotRequestDto)
    {
        ParkingLot p = parkingLotRequestDto.toModel();
        p = parkingLotService.updateParkingLot(p);
        return new ResponseEntity<ParkingLotResponseDto>(new ParkingLotResponseDto(p), HttpStatus.OK);

    }
}
