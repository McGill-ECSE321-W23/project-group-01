package ca.mcgill.ecse321.PLMS.dto;

import java.sql.Date;
import java.time.LocalDate;

import ca.mcgill.ecse321.PLMS.model.MonthlyPass;


public class MonthlyPassResponseDto {


    private int id;
    private double fee;
    private String spotNumber;
    private String confirmationCode;
    private String licensePlate;
    private boolean isLarge;
    private LocalDate startDate;
    private LocalDate endDate;
    private int floorNumber;
    private String customerEmail;

    /**
     * Constructor for creating a monthlypass response transfer object by using the fields of 
     * a monthlypass object.
     * @param monthlypass - monthlypass to turn into a transfer object
     */

    public MonthlyPassResponseDto() {

    }
    public MonthlyPassResponseDto(MonthlyPass monthlypass){
        this.id = monthlypass.getId();
        this.fee = monthlypass.getFee();
        this.spotNumber = monthlypass.getSpotNumber();
        this.confirmationCode = monthlypass.getConfirmationCode();
        this.licensePlate = monthlypass.getLicensePlate();
        this.floorNumber = monthlypass.getFloor().getFloorNumber();
        if (monthlypass.getCustomer() != null) this.customerEmail = monthlypass.getCustomer().getEmail();
        this.isLarge = monthlypass.getIsLarge();
        this.startDate = monthlypass.getStartDate();
        this.endDate = monthlypass.getEndDate();
    }
    
    public int getId() {
        return id;
    }

    public double getFee() {
        return fee;
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

    public boolean getLarge() {
        return isLarge;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public void setSpotNumber(String spotNumber) {
        this.spotNumber = spotNumber;
    }

    public void setConfirmationCode(String confirmationCode) {
        this.confirmationCode = confirmationCode;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public void setLarge(boolean large) {
        isLarge = large;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }
}
