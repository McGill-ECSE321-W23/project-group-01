package ca.mcgill.ecse321.PLMS.dto;

import java.sql.Date;
import java.sql.Time;

import ca.mcgill.ecse321.PLMS.model.Service;
import ca.mcgill.ecse321.PLMS.model.ServiceAppointment;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

public class ServiceAppointmentRequestDto {

    //=-=-=-=-=- jakarta validation of variables -=-=-=-=-=//
    @NotNull(message = "Cannot have an empty date.")
    @FutureOrPresent(message = "Date must be in the future.")
    private Date date;

    @NotNull(message = "Cannot have an empty start time.")
    private Time starTime;

    @NotNull(message = "Cannot have an empty end time.")
    private Time endTime;

    @NotNull(message = "Cannot have an empty service name.")
    private String serviceName;
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=//

    /**
     * Method to construct a serviceAppointment object from a serviceAppointment request dto object
     * 
     * @return serviceAppointment object with all corresponding attributes
     */
	public ServiceAppointment toModel(Service service) {
		ServiceAppointment s = new ServiceAppointment();
		s.setDate(date);
		s.setStartTime(starTime);
        s.setEndTime(endTime);
		s.setService(service);
		
		return s;
	}

    //=-=-=-=-=- Setters -=-=-=-=-=//
    public void setDate(Date date) {
        this.date = date;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setStarTime(Time starTime) {
        this.starTime = starTime;
    }
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=//

    //=-=-=-=-=- Getters -=-=-=-=-=//
    public Date getDate() {
        return date;
    }

    public Time getEndTime() {
        return endTime;
    }

    public Time getStarTime() {
        return starTime;
    }
	
    public String getServiceName() {
        return serviceName;
    }
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=//
}
