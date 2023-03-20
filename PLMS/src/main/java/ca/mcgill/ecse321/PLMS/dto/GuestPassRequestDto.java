package ca.mcgill.ecse321.PLMS.dto;

import java.sql.Date;
import java.sql.Time;

import ca.mcgill.ecse321.PLMS.model.GuestPass;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class GuestPassRequestDto {

    @NotNull(message = "Cannot have an empty spotNumber.")
    private String spotNumber;

    @NotNull(message = "Cannot have an empty confirmationCode.")
    private String confirmationCode;

    @NotNull(message = "Cannot have an empty licensePlate.")
    private String licensePlate;

    @NotNull(message = "Cannot have an empty floor number.")
    private Integer floorNumber;

    @NotNull(message = "Cannot have an empty start time.")
    private Time startTime;

    @NotNull(message = "Cannot have an empty end time.")
    private Time endTime;

    @NotNull(message = "Must specify whether the pass is small or large.")
    private Boolean isLarge;

    public GuestPass toModel(){
        GuestPass guestPass = new GuestPass();
        guestPass.setSpotNumber(this.spotNumber);
        guestPass.setConfirmationCode(this.confirmationCode);
        guestPass.setLicensePlate(this.licensePlate);
        guestPass.setStartTime(startTime);
        guestPass.setEndTime(endTime);
        guestPass.setIsLarge(isLarge);
        return guestPass;
    }

    public void setSpotNumber(String spotNumber){
        this.spotNumber = spotNumber;
    }

    public void setConfirmationCode(String confirmationCode){
        this.confirmationCode = confirmationCode;
    }

    public void setLicensePlate(String licensePlate){
        this.licensePlate = licensePlate;
    }

    public void setFloorNumber(Integer floorNumber){
        this.floorNumber = floorNumber;
    }

    public void setStartTime(Time startTime){
        this.startTime = startTime;
    }

    public void setEndTime(Time endTime){
        this.endTime = endTime;
    }

    // Getters omitted for brevity
}