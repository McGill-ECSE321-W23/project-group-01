package ca.mcgill.ecse321.PLMS.dto;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;

import ca.mcgill.ecse321.PLMS.model.ServiceAppointment;

public class ServiceAppointmentResponseDto {
   
    private int id;
    private LocalDate date;
    private Time startTime;
    private Time endTime;

    //Relationships
    private String customerEmail;
    private String employeeEmail;
    private String serviceName;

    ServiceAppointmentResponseDto() {}

    public ServiceAppointmentResponseDto(ServiceAppointment s){
        this.id = s.getId();
        this.date = s.getDate();
        this.startTime = s.getStartTime();
        this.endTime = s.getEndTime();

        if (s.getCustomer() != null) this.customerEmail = s.getCustomer().getEmail();
        if (s.getEmployee() != null) this.employeeEmail = s.getEmployee().getEmail();
        this.serviceName = s.getService().getServiceName();
    }

    //=-=-=-=-=- Getters -=-=-=-=-=//
    public int getId() {
        return id;
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

    public String getCustomerEmail() {
        return customerEmail;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public String getServiceName() {
        return serviceName;
    }
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=//

    //=-=-=-=-=- Setters -=-=-=-=-=//
    public void setId(int id) {
        this.id = id;
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

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=//
}
