package ca.mcgill.ecse321.PLMS.controller;

import java.sql.Time;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	public ResponseEntity<ParkingLotResponseDto> createPerson(@Valid @RequestBody ParkingLotRequestDto parkingLotRequestDto) {
		ParkingLot p = parkingLotRequestDto.toModel();
		p = parkingLotService.createParkingLot(p);
		ParkingLotResponseDto responseBody = new ParkingLotResponseDto(p);
		return new ResponseEntity<ParkingLotResponseDto>(responseBody, HttpStatus.CREATED);
	}

    @PutMapping(value = {"/parkingLot/update"})
    public ResponseEntity<ParkingLotResponseDto> updateEmployee(@Valid @RequestBody ParkingLotRequestDto parkingLotRequestDto)
    {
        ParkingLot p = parkingLotRequestDto.toModel();
        p = parkingLotService.updateParkingLot(p);
        return new ResponseEntity<ParkingLotResponseDto>(new ParkingLotResponseDto(p), HttpStatus.OK);

    }

    @PutMapping(value = {"/parkingLot/update/opening/{openingTime}/closing/{closingTime}"})
    public ResponseEntity<ParkingLotResponseDto> updateOpeningClosingTimes(@PathVariable @DateTimeFormat(pattern = "HH-MM-SS") LocalTime openingTime, @PathVariable @DateTimeFormat(pattern = "HH-MM-SS") LocalTime closingTime)
    {
        ParkingLot p = parkingLotService.getParkingLot();
        ParkingLotRequestDto parkingLotRequestDto = new ParkingLotRequestDto();
        parkingLotRequestDto.setOpeningTime(Time.valueOf(openingTime));
        parkingLotRequestDto.setClosingTime(Time.valueOf(closingTime));
        parkingLotRequestDto.setLargeSpotFee(p.getLargeSpotFee());
        parkingLotRequestDto.setSmallSpotFee(p.getSmallSpotFee());
        parkingLotRequestDto.setMonthlyFlatFee(p.getMonthlyFlatFee());
        @Valid ParkingLotRequestDto s = parkingLotRequestDto;
        ParkingLot parkingLot = parkingLotRequestDto.toModel();
        parkingLot = parkingLotService.updateParkingLot(parkingLot);
        return new ResponseEntity<ParkingLotResponseDto>(new ParkingLotResponseDto(parkingLot), HttpStatus.OK);
    }

    @PutMapping(value = {"/parkingLot/update/small/{smallSpotFee}/large/{largeSpotFee}"})
    public ResponseEntity<ParkingLotResponseDto> updateSmallLargeSpotFees(@PathVariable Double smallSpotFee, @PathVariable Double largeSpotFee)
    {
        ParkingLot p = parkingLotService.getParkingLot();
        ParkingLotRequestDto parkingLotRequestDto = new ParkingLotRequestDto();
        parkingLotRequestDto.setOpeningTime(p.getOpeningTime());
        parkingLotRequestDto.setClosingTime(p.getClosingTime());
        parkingLotRequestDto.setLargeSpotFee(smallSpotFee);
        parkingLotRequestDto.setSmallSpotFee(largeSpotFee);
        parkingLotRequestDto.setMonthlyFlatFee(p.getMonthlyFlatFee());
        @Valid ParkingLotRequestDto s = parkingLotRequestDto;
        ParkingLot parkingLot = parkingLotRequestDto.toModel();
        parkingLot = parkingLotService.updateParkingLot(parkingLot);
        return new ResponseEntity<ParkingLotResponseDto>(new ParkingLotResponseDto(parkingLot), HttpStatus.OK);
    }

    @PutMapping(value = {"/parkingLot/update/monthly/{monthlyFlatFee}"})
    public ResponseEntity<ParkingLotResponseDto> updateMonthlyFlatFee(@PathVariable Double monthlyFlatFee)
    {
        ParkingLot p = parkingLotService.getParkingLot();
        ParkingLotRequestDto parkingLotRequestDto = new ParkingLotRequestDto();
        parkingLotRequestDto.setOpeningTime(p.getOpeningTime());
        parkingLotRequestDto.setClosingTime(p.getClosingTime());
        parkingLotRequestDto.setLargeSpotFee(p.getLargeSpotFee());
        parkingLotRequestDto.setSmallSpotFee(p.getSmallSpotFee());
        parkingLotRequestDto.setMonthlyFlatFee(monthlyFlatFee);
        @Valid ParkingLotRequestDto s = parkingLotRequestDto;
        ParkingLot parkingLot = parkingLotRequestDto.toModel();
        parkingLot = parkingLotService.updateParkingLot(parkingLot);
        return new ResponseEntity<ParkingLotResponseDto>(new ParkingLotResponseDto(parkingLot), HttpStatus.OK);
    }



}
