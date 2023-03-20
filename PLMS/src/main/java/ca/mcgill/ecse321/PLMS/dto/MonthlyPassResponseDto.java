package ca.mcgill.ecse321.PLMS.dto;

import ca.mcgill.ecse321.PLMS.model.MonthlyPass;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;


public class MonthlyPassResponseDto {


    private Integer id;
    private Double fee;
    private String spotNumber;
    private String confirmationCode;
    private String licensePlate;
    private FloorResponseDto floor;
    private MonthlyCustomerResponseDto monthlycustomer;

    /**
     * Constructor for creating a monthlypass response transfer object by using the fields of 
     * a monthlypass object.
     * @param monthlypass - monthlypass to turn into a transfer object
     */

    public MonthlyPassResponseDto(MonthlyPass monthlypass){
        this.id = monthlypass.getId();
        this.fee = monthlypass.getFee();
        this.spotNumber = monthlypass.getSpotNumber();
        this.confirmationCode = monthlypass.getConfirmationCode();
        this.licensePlate = monthlypass.getLicensePlate();
        this.floor = monthlypass.getFloor();
        this.monthlycustomer = monthlypass.getCustomer();
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

    public MonthlyCustomerResponseDto getMonthlyCustomer(){
        return this.monthlycustomer;
    }


}