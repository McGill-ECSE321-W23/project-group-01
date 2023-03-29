package ca.mcgill.ecse321.PLMS.dto;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;

import ca.mcgill.ecse321.PLMS.model.GuestPass;


public class GuestPassResponseDto {


    private int id;
    private double fee;
    private String spotNumber;
    private String confirmationCode;
    private String licensePlate;
    private boolean isLarge;
    private LocalDate date;
    private Time startTime;
    private Time endTime;
    private int floorNumber;

    /**
     * Constructor for creating a guestPass response transfer object by using the fields of 
     * a guestPass object.
     * @param guestPass - guestPass to turn into a transfer object
     */

    public GuestPassResponseDto(GuestPass guestPass){
        this.id = guestPass.getId();
        this.fee = guestPass.getFee();
        this.spotNumber = guestPass.getSpotNumber();
        this.confirmationCode = guestPass.getConfirmationCode();
        this.licensePlate = guestPass.getLicensePlate();
        this.floorNumber = guestPass.getFloor().getFloorNumber();
        this.isLarge = guestPass.getIsLarge();
        this.date = guestPass.getDate();
        this.startTime = guestPass.getStartTime();
        this.endTime = guestPass.getEndTime();
    }

    public GuestPassResponseDto() {
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

    public boolean isLarge() {
        return isLarge;
    }

    public LocalDate getDate() {
        return date;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public int getFloorNumber() {
        return floorNumber;
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

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }
}