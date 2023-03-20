package ca.mcgill.ecse321.PLMS.dto;

import ca.mcgill.ecse321.PLMS.model.MonthlyPass;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;


public class GuestPassRequestDto {

    @NotNull(message = "Cannot have an empty id.")
    @Min(value = 0, message = "The id must be a non negative number.")
    private Integer id;

    @NotNull(message = "Cannot have an empty fee.")
    @Min(value = 0, message = "The fee must be a non negative number.")
    private Double fee;

    @NotNull(message = "Cannot have an empty spotNumber.")
    private String spotNumber;

    @NotNull(message = "Cannot have an empty confirmationCode.")
    private String confirmationCode;

    @NotNull(message = "Cannot have an empty licensePlate.")
    private String licensePlate;

    private FloorDto floor;



    /**
     * Constructor for creating a guest pass request transfer object by using the fields of 
     * a guestPass object.
     * @param guestPass - guest pass to turn into a request transfer object
     */


    public MonthlyPass toModel(){
        GuestPass guestPass = new MonthlyPass();
        guestpass.setFee(this.fee);
        guestPass.setSpotNumber(this.spotNumber);
        guestPass.setConfirmationCode(this.confirmationCode);
        guestPass.setLicensePlate(this.licensePlate);
        guestPass.setFloor(this.floor);
        return guestPass;
    }

    public void setFee(Double fee){
        this.fee = fee;
    }


    public void setSpotNumber(String spotNumber){
        this.spotNumber = spotNumber;
    }


    public void setConfirmationCode(String confirmationCode){
        this.confirmationCode = confirmationCode;
    }


    public void setLicensePlate(String LicensePlate){
        this.licensePlate = licensePlate;
    }


    public void setFloor(FloorDto floor){
        this.floor = floor;
    }


}
