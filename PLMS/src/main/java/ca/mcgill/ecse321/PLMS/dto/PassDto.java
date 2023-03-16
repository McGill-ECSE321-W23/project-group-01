package ca.mcgill.ecse321.PLMS.dto;

import ca.mcgill.ecse321.PLMS.model.Pass;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;


public class PassDto {

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
   * Constructor for creating a pass transfer object by using the fields of 
   * a pass object.
   * @param pass - pass to turn into a transfer object
   */

    public PassDto(Pass pass){
        this.id = pass.getId();
        this.fee = pass.getFee();
        this.spotNumber = pass.getSpotNumber();
        this.confirmationCode = pass.getConfirmationCode();
        this.licensePlate = pass.getLicensePlate();
        this.floor = pass.getFloor();
    }

    public Pass toModel(){
        Pass pass = new Pass();
        pass.setFee(this.fee);
        pass.setSpotNumber(this.spotNumber);
        pass.setConfirmationCode(this.confirmationCode);
        pass.setLicensePlate(this.licensePlate);
        pass.setFloor(this.floor);
        return pass;
    }

    public Double getFee(){
        return this.fee;
    }

    public void setFee(Double fee){
        this.fee = fee;
    }

    public Double getSpotNumber(){
        return this.spotNumber;
    }

    public void setSpotNumber(String spotNumber){
        this.spotNumber = spotNumber;
    }

    public Double getConfirmationCode(){
        return this.confirmationCode;
    }

    public void setConfirmationCode(String confirmationCode){
        this.confirmationCode = confirmationCode;
    }

    public Double getLicensePlate(){
        return this.licensePlate;
    }

    public void setLicensePlate(String LicensePlate){
        this.licensePlate = licensePlate;
    }

    public FloorDTO getFloor(){
        return this.floor;
    }

    public void setFloor(FloorDto floor){
        this.floor = floor;
    }



}
