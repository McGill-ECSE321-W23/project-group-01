package ca.mcgill.ecse321.PLMS.dto;

import java.sql.Date;
import java.sql.Time;

import ca.mcgill.ecse321.PLMS.model.ServiceAppointment;

public class ServiceAppointmentResponseDto {
   
    private int id;
    private Date date;
    private Time startTime;
    private Time endTime;

    //Relationships
    private String customerEmail;
    private String employeeEmail;
    private String serviceName;

    public ServiceAppointmentResponseDto(ServiceAppointment s){
        this.id = s.getId();
        this.date = s.getDate();
        this.startTime = s.getStartTime();
        this.endTime = s.getEndTime();

        this.customerEmail = s.getCustomer().getEmail();
        this.employeeEmail = s.getEmployee().getEmail();
        this.serviceName = s.getService().getServiceName();
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
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
}
