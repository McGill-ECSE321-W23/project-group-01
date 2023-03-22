package ca.mcgill.ecse321.PLMS.dto;


import ca.mcgill.ecse321.PLMS.model.MonthlyPass;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;


public class MonthlyPassRequestDto {

    //=-=-=-=-=- jakarta validation of variables -=-=-=-=-=//
    @NotNull(message = "Cannot have an empty number of months.")
    @Min(value = 1, message = "Must enter a positive number of months.")
    private Integer numberOfMonths;
    

    @NotNull(message = "Cannot have an empty spot number.")
    private String spotNumber;

    @NotNull(message = "Cannot have an empty confirmation code.")
    private String confirmationCode;

    @NotNull(message = "Cannot have an empty license plate.")
    private String licensePlate;

    @NotNull(message = "Cannot have an empty floor number.")
    private Integer floorNumber;

    @NotNull(message = "Must specify whether the pass is for a small or large car")
    private Boolean isLarge;

    // this is allowed to be null, as you don't need an account to register
    private String customerEmail;



    /**
     * Constructor for creating a monthly pass request transfer object by using the fields of 
     * a monthlypass object.
     * @param monthlypass - monthly pass to turn into a request transfer object
     */


    public MonthlyPass toModel(){
        MonthlyPass monthlypass = new MonthlyPass();
        monthlypass.setSpotNumber(this.spotNumber);
        monthlypass.setConfirmationCode(this.confirmationCode);
        monthlypass.setLicensePlate(this.licensePlate);
        monthlypass.setIsLarge(this.isLarge);
        return monthlypass;
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

    public void setNumberOfMonths(Integer numberOfMonths){
        this.numberOfMonths = numberOfMonths;
    }

    public void setFloorNumber(Integer floorNumber){
        this.floorNumber = floorNumber;
    }

    public void setIsLarge(Boolean isLarge){
        this.isLarge = isLarge;
    }

    public void setCustomerEmail(String customerEmail){
        this.customerEmail = customerEmail;
    }
}