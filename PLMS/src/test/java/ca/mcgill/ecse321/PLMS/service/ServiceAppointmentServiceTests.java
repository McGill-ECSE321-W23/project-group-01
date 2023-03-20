package ca.mcgill.ecse321.PLMS.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import ca.mcgill.ecse321.PLMS.exception.PLMSException;
import ca.mcgill.ecse321.PLMS.model.Service;
import ca.mcgill.ecse321.PLMS.model.ServiceAppointment;
import ca.mcgill.ecse321.PLMS.repository.EmployeeRepository;
import ca.mcgill.ecse321.PLMS.repository.MonthlyCustomerRepository;
import ca.mcgill.ecse321.PLMS.repository.ServiceAppointmentRepository;

@SpringBootTest
public class ServiceAppointmentServiceTests {
  @Mock
  private ServiceAppointmentRepository serviceAppointmentRepository;

  @Mock
  private EmployeeRepository employeeRepository;

  @Mock
  private MonthlyCustomerRepository monthlyCustomerRepository;

  @InjectMocks
  private ServiceAppointmentService serviceAppointmentService;

  @Test
  public void testGetAllAppointments(){
    //=-=-=-=-=-=- Create object -=-=-=-=-=-=//
    String serviceName = "30 min Car Wash";
    int serviceCost = 30;
    double serviceLengthInHours = 0.5;
    Service service = new Service(serviceName, serviceCost, serviceLengthInHours);
    //normal parameters
    Date date = Date.valueOf("2023-02-21");
    Time startTime = Time.valueOf("12:00:00");
    Time endTime = Time.valueOf("18:00:00");
    ServiceAppointment appt = new ServiceAppointment(date, startTime, endTime, service);
    // The parking lot repo should return a single parking lot
    ArrayList<ServiceAppointment> appts = new ArrayList<ServiceAppointment>();
    appts.add(appt);
    when(serviceAppointmentRepository.findAll()).thenReturn((Iterable<ServiceAppointment>) appts);
    Iterable<ServiceAppointment> output = serviceAppointmentService.getAllServiceAppointments();
    assertEquals(output.iterator().next(), appt);
        
  }

  @Test
  public void testGetValidAppointment(){
    // //=-=-=-=-=-=- Create object -=-=-=-=-=-=//
    int id = 4;
    String serviceName = "30 min Car Wash";
    int serviceCost = 30;
    double serviceLengthInHours = 0.5;
    Service service = new Service(serviceName, serviceCost, serviceLengthInHours);
    //normal parameters
    Date date = Date.valueOf("2023-02-21");
    Time startTime = Time.valueOf("12:00:00");
    Time endTime = Time.valueOf("18:00:00");
    ServiceAppointment appt = new ServiceAppointment(date, startTime, endTime, service);
    when(serviceAppointmentRepository.findServiceAppointmentById(4)).thenReturn(appt);
    ServiceAppointment output = serviceAppointmentService.findServiceAppointmentById(id);
    assertEquals(date, output.getDate());
    assertEquals(startTime, output.getStartTime());
    assertEquals(endTime, output.getEndTime());
    assertEquals(serviceName, output.getService().getServiceName());
  }


  @Test
  public void testGetInvalidAppointment(){
    // //=-=-=-=-=-=- Create object -=-=-=-=-=-=//
    int id = 4;
    when(serviceAppointmentRepository.findServiceAppointmentById(4)).thenReturn(null);
    PLMSException e = assertThrows(PLMSException.class,
				() -> serviceAppointmentService.findServiceAppointmentById(id));
		assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
		assertEquals("Service appointment with ID " + id + " does not exist.", e.getMessage());
  }
}
