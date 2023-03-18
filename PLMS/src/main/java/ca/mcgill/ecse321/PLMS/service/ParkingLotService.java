package ca.mcgill.ecse321.PLMS.service;

import java.sql.Time;
import java.time.LocalTime;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.PLMS.exception.PLMSException;

@Service
public class ParkingLotService {

    @Transactional
    public void validateOpeningClosingTime(Time openingTime, Time closingTime){
        int comparison = openingTime.toLocalTime().compareTo(closingTime.toLocalTime());
        if (comparison == 0){
            throw new PLMSException(HttpStatus.BAD_REQUEST, "Opening and closing times cannot be the same.");
        }else if(comparison > 0){
            throw new PLMSException(HttpStatus.BAD_REQUEST, "Opening time cannot be after closing time.");
        }
    }
}
