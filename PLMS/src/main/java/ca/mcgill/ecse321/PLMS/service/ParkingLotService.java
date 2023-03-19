package ca.mcgill.ecse321.PLMS.service;

import java.util.List;

import ca.mcgill.ecse321.PLMS.model.ParkingLot;
import ca.mcgill.ecse321.PLMS.repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.PLMS.exception.PLMSException;

@Service
public class ParkingLotService {

    @Autowired
    ParkingLotRepository parkingLotRepository;


    @Transactional
    public ParkingLot getParkingLotById(int id) {
        ParkingLot parkingLot = parkingLotRepository.findParkingLotById(id) ;
        if (parkingLot == null) {
            throw new PLMSException(HttpStatus.NOT_FOUND, "Parking Lot non-existant");
        }

        return parkingLot;
    }

    @Transactional
    public ParkingLot getParkingLot() {
        List<ParkingLot> parkingLot = (List<ParkingLot>) parkingLotRepository.findAll();
        if (parkingLot.isEmpty())
            throw new PLMSException(HttpStatus.NOT_FOUND, "Parking Lot not found");
        return parkingLot.get(0);
    }

    @Transactional
    public ParkingLot createParkingLot(ParkingLot parkingLot) {
        validateOpeningClosingTime(parkingLot);
        if (parkingLotRepository.findParkingLotById(parkingLot.getId()) == null)
            return parkingLotRepository.save(parkingLot);
        else
            throw new PLMSException(HttpStatus.CONFLICT, "Parking Lot with this ID already exists");
    }

    @Transactional
    public ParkingLot updateParkingLot(ParkingLot parkingLot)
    {
        getParkingLotById(parkingLot.getId());
        validateOpeningClosingTime(parkingLot);
        return parkingLotRepository.save(parkingLot);
    }

    public void validateOpeningClosingTime(ParkingLot parkingLot){
        int comparison = (parkingLot.getOpeningTime()).toLocalTime().compareTo((parkingLot.getClosingTime()).toLocalTime());
        if (comparison == 0)
            throw new PLMSException(HttpStatus.BAD_REQUEST, "Opening and closing times cannot be the same.");
        else if(comparison > 0)
            throw new PLMSException(HttpStatus.BAD_REQUEST, "Opening time cannot be after closing time.");

    }
}
