package ca.mcgill.ecse321.PLMS.dto;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;

import ca.mcgill.ecse321.PLMS.model.GuestPass;


public class GuestPassResponseDto {


    private Integer id;
    private Double fee;
    private String spotNumber;
    private String confirmationCode;
    private String licensePlate;
    private Boolean isLarge;
    private LocalDate date;
    private Time startTime;
    private Time endTime;
    private Integer floorNumber;

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

    public Double getFee(){
        return this.fee;
    }

    public Integer getId(){
        return this.id;
    }

    public Boolean getIsLarge(){
        return this.isLarge;
    }

    public LocalDate getDate(){
        return this.date;
    }

    public Time getStartTime(){
        return this.startTime;
    }

    public Time getEndTime(){
        return this.endTime;
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

}