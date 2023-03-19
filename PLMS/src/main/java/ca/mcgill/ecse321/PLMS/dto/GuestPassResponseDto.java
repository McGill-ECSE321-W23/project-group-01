package ca.mcgill.ecse321.PLMS.dto;

import ca.mcgill.ecse321.PLMS.model.GuestPass;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;



public class GuestPassResponseDto {


    private Integer id;
    private Double fee;
    private String spotNumber;
    private String confirmationCode;
    private String licensePlate;
    private FloorResponseDto floor;


    /**
     * Constructor for creating a guestpass response transfer object by using the fields of 
     * a guestpass object.
     * @param guestpass - guestpass to turn into a transfer object
     */

    public GuestPassResponseDto(GuestPass guestpass){
        this.id = guestpass.getId();
        this.fee = guestpass.getFee();
        this.spotNumber = guestpass.getSpotNumber();
        this.confirmationCode = guestpass.getConfirmationCode();
        this.licensePlate = guestpass.getLicensePlate();
        this.floor = guestpass.getFloor();
    }

    public Double getFee(){
        return this.fee;
    }

    public Double getSpotNumber(){
        return this.spotNumber;
    }

    public Double getConfirmationCode(){
        return this.confirmationCode;
    }

    public Double getLicensePlate(){
        return this.licensePlate;
    }

    public FloorResponseDTO getFloor(){
        return this.floor;
    }


}
