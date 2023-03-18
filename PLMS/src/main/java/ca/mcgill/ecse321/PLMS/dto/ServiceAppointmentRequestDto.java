package ca.mcgill.ecse321.PLMS.dto;

import java.sql.Date;
import java.sql.Time;

import com.fasterxml.jackson.annotation.JsonFormat;

import ca.mcgill.ecse321.PLMS.model.MonthlyCustomer;
import ca.mcgill.ecse321.PLMS.model.Service;
import ca.mcgill.ecse321.PLMS.model.ServiceAppointment;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

public class ServiceAppointmentRequestDto {

    //=-=-=-=-=- jakarta validation of variables -=-=-=-=-=//
    @NotNull(message = "Cannot have an empty date.")
    @FutureOrPresent(message = "Date must be in the future.")
    private Date date;
    
    //Format
    @NotNull(message = "Cannot have an empty start time.")
    private Time startTime;

    @NotNull(message = "Cannot have an empty service name.")
    private String serviceName;

    private String userEmail;
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=//

    /**
     * Method to construct a serviceAppointment object from a serviceAppointment request dto object
     * 
     * @return serviceAppointment object with all corresponding attributes
     */
	public ServiceAppointment toModel(Service service, MonthlyCustomer monthlyCustomer) {
		ServiceAppointment s = new ServiceAppointment();
		s.setDate(date);
		s.setStartTime(startTime);
		s.setService(service);
        s.setCustomer(monthlyCustomer);
		
		return s;
	}

    //=-=-=-=-=- Setters -=-=-=-=-=//
    public void setDate(Date date) {
        this.date = date;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setStartTime(Time starTime) {
        this.startTime = starTime;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=//

    //=-=-=-=-=- Getters -=-=-=-=-=//
    public Date getDate() {
        return date;
    }

    public Time getStartTime() {
        return startTime;
    }
	
    public String getServiceName() {
        return serviceName;
    }

    public String getUserEmail() {
        return userEmail;
    }
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=//
}
