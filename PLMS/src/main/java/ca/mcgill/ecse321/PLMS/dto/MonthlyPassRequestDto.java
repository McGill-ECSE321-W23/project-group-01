package ca.mcgill.ecse321.PLMS.dto;

import ca.mcgill.ecse321.PLMS.model.MonthlyPass;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;


public class MonthlyPassRequestDto {

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

    private MonthlyCustomerResponseDto customer;



    /**
     * Constructor for creating a monthly pass request transfer object by using the fields of 
     * a monthlypass object.
     * @param monthlypass - monthly pass to turn into a request transfer object
     */


    public MonthlyPass toModel(){
        MonthlyPass monthlypass = new MonthlyPass();
        monthlypass.setFee(this.fee);
        monthlypass.setSpotNumber(this.spotNumber);
        monthlypass.setConfirmationCode(this.confirmationCode);
        monthlypass.setLicensePlate(this.licensePlate);
        monthlypass.setFloor(this.floor);
        monthlypass.setCustomer(this.customer);
        return monthlypass;
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

    public void setCustomer(MonthlyCustomerRequestDto customer){
        this.monthlycustomer = customer;
    }



}
