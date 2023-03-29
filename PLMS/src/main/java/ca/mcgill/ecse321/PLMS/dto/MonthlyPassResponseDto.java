package ca.mcgill.ecse321.PLMS.dto;

import java.sql.Date;

import ca.mcgill.ecse321.PLMS.model.MonthlyPass;
import io.swagger.v3.oas.annotations.media.Schema;


public class MonthlyPassResponseDto {

    @Schema(example = "5", description = "The id of the monthly pass")
    private Integer id;
    @Schema(example= "1.25", description = "The fee assiated to the pass")
    private Double fee;
    @Schema(example= "A24", description = "The spot number")
    private String spotNumber;
    @Schema(example= "JK95HO95T3", description = "Code that confirms the payment (will be null before payment)")
    private String confirmationCode;
    @Schema(example= "T3ST41", description = "License plate of the car linked to this pass")
    private String licensePlate;
    @Schema(example= "false", description = "Is the spot a spot that can hold large vehicles")
    private Boolean isLarge;
    @Schema(example= "2024-05-05", description = "Start date of the pass")
    private Date startDate;
    @Schema(example= "2024-06-05", description = "End date of the pass")
    private Date endDate;
    @Schema(example= "1", description = "Floor number where the spot is located at")
    private Integer floorNumber;
    @Schema(example= "user@email.com", description = "Email linked to the account of the customer")
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
        if (monthlypass.getCustomer() != null) this.monthlyCustomerEmail = monthlypass.getCustomer().getEmail();
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