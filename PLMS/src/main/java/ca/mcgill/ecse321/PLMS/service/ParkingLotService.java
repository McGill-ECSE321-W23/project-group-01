package ca.mcgill.ecse321.PLMS.service;

import java.util.ArrayList;
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
    public ParkingLot getParkingLot() {
        List<ParkingLot> parkingLot = (ArrayList<ParkingLot>) parkingLotRepository.findAll();
        if (parkingLot.isEmpty())
            throw new PLMSException(HttpStatus.NOT_FOUND, "Parking Lot not found.");
        return parkingLot.get(0);
    }

    @Transactional
    public ParkingLot createParkingLot(ParkingLot parkingLot) {
        validateOpeningClosingTime(parkingLot);
        if ( ((List<ParkingLot>) parkingLotRepository.findAll()).isEmpty())
            return parkingLotRepository.save(parkingLot);
        else
            throw new PLMSException(HttpStatus.CONFLICT, "Parking Lot already exists");
    }

    @Transactional
    public ParkingLot updateParkingLot(ParkingLot parkingLot)
    {
        validateOpeningClosingTime(parkingLot);
        ParkingLot p = getParkingLot();
        p.setClosingTime(parkingLot.getClosingTime());
        p.setOpeningTime(parkingLot.getOpeningTime());
        p.setLargeSpotFee(parkingLot.getLargeSpotFee());
        p.setSmallSpotFee(parkingLot.getSmallSpotFee());
        p.setSmallSpotMonthlyFlatFee(parkingLot.getSmallSpotMonthlyFlatFee());
        p.setLargeSpotMonthlyFlatFee(parkingLot.getLargeSpotMonthlyFlatFee());
        return parkingLotRepository.save(p);
    }

    public void validateOpeningClosingTime(ParkingLot parkingLot){
        int comparison = (parkingLot.getOpeningTime()).toLocalTime().compareTo((parkingLot.getClosingTime()).toLocalTime());
        if (comparison == 0)
            throw new PLMSException(HttpStatus.BAD_REQUEST, "Opening and closing times cannot be the same.");
        else if(comparison > 0)
            throw new PLMSException(HttpStatus.BAD_REQUEST, "Opening time cannot be after closing time.");

    }
}
