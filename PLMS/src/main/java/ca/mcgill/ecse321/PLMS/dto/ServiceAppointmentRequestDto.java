package ca.mcgill.ecse321.PLMS.dto;

import java.sql.Date;
import java.sql.Time;

import ca.mcgill.ecse321.PLMS.model.Service;
import ca.mcgill.ecse321.PLMS.model.ServiceAppointment;

public class ServiceAppointmentRequestDto {

    private Date date;
    private Time starTime;
    private Time endTime;
    private String serviceName;

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
	
    public String getServiceName() {
        return serviceName;
    }

	public ServiceAppointment toModel(Service service) {
		ServiceAppointment s = new ServiceAppointment();
		s.setDate(date);
		s.setStartTime(starTime);
        s.setEndTime(endTime);
		s.setService(service);
		
		return s;
	}
}
