package ca.mcgill.ecse321.PLMS.dto;

import java.sql.Date;

import ca.mcgill.ecse321.PLMS.model.MonthlyPass;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;


public class MonthlyPassResponseDto {


    private Integer id;
    private Double fee;
    private String spotNumber;
    private String confirmationCode;
    private String licensePlate;
    private Boolean isLarge;
    private Date startDate;
    private Date endDate;
    private Integer floorNumber;
    private String monthlyCustomerEmail;

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
        this.floorNumber = monthlypass.getFloor().getFloorNumber();
        this.monthlyCustomerEmail = monthlypass.getCustomer().getEmail();
        this.isLarge = monthlypass.getIsLarge();
        this.startDate = monthlypass.getStartDate();
        this.endDate = monthlypass.getEndDate();
    }

    public Double getFee(){
        return this.fee;
    }

    public Integer getId(){
        return this.id;
    }

    public Boolean getIsLarge(){
        return this.isLarge;
    }

    public Date getStartDate(){
        return this.startDate;
    }

    public Date getEndDate(){
        return this.endDate;
    }

    public String getSpotNumber(){
        return this.spotNumber;
    }

    public String getConfirmationCode(){
        return this.confirmationCode;
    }

    public String getLicensePlate(){
        return this.licensePlate;
    }

    public Integer getFloor(){
        return this.floorNumber;
    }

    public String getMonthlyCustomerEmail(){
        return this.monthlyCustomerEmail;
    }


}