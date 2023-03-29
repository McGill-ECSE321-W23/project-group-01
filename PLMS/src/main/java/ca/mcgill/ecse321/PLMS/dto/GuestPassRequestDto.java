package ca.mcgill.ecse321.PLMS.dto;


import ca.mcgill.ecse321.PLMS.model.GuestPass;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class GuestPassRequestDto {

    @NotNull(message = "Cannot have an empty spot number.")
    private String spotNumber;

    @NotNull(message = "Cannot have an empty confirmation code.")
    private String confirmationCode;

    @NotNull(message = "Cannot have an empty license plate.")
    private String licensePlate;

    @NotNull(message = "Cannot have an empty floor number.")
    private Integer floorNumber;

    @NotNull(message = "Cannot have an empty number of 15 minute increments.")
    @Min(value = 1, message = "Must enter a positive number of 15 minute increments.")
    private Integer numberOfFifteenMinuteIncrements;


//    @NotNull(message = "Must specify whether the pass is small or large.")
    private Boolean isLarge;

    public GuestPass toModel(){
        GuestPass guestPass = new GuestPass();
        guestPass.setSpotNumber(this.spotNumber);
        guestPass.setConfirmationCode(this.confirmationCode);
        guestPass.setLicensePlate(this.licensePlate);
        guestPass.setIsLarge(true);
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

    public Integer getFloorNumber() {
        return floorNumber;
    }

    public Integer getNumberOfFifteenMinuteIncrements() {
        return numberOfFifteenMinuteIncrements;
    }

    public void setIsLarge(Boolean isLarge) {
//        this.isLarge = isLarge;
        this.isLarge = true;
    }

    public void setNumberOfFifteenMinuteIncrements(Integer numberOfFifteenMinuteIncrements) {
        this.numberOfFifteenMinuteIncrements = numberOfFifteenMinuteIncrements;
    }

    public String getSpotNumber() {
        return spotNumber;
    }

    public String getConfirmationCode() {
        return confirmationCode;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public Boolean getLarge() {
        return isLarge;
    }
}