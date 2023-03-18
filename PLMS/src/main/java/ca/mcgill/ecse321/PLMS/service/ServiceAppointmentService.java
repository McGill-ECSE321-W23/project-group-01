package ca.mcgill.ecse321.PLMS.service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.PLMS.exception.PLMSException;
import ca.mcgill.ecse321.PLMS.model.ServiceAppointment;
import ca.mcgill.ecse321.PLMS.repository.ServiceAppointmentRepository;
import jakarta.transaction.Transactional;

@Service
public class ServiceAppointmentService {
  /*
   * Functionalities of the service appointment controller class
   * 
   * 1) GET
   * 2) GET all service appointments 
   * 3) GET service appointment by Date
   * 4) GET service appointments by assigned employee
   * 5) GET service appointments by a monthly customer
   * 6) POST service appointment
   * 7) DELETE service appointment by ID
   */
  // going to definitely need to have a service appointment repository
  @Autowired
  ServiceAppointmentRepository serviceAppointmentRepo;

  // 1
  @Transactional
  public ServiceAppointment createServiceAppointment(ServiceAppointment serviceAppointment){
    // first calculate the end time of the service appointment by using the length of the appointment
    LocalTime localEndTime = findEndTime(serviceAppointment.getStartTime().toLocalTime(), serviceAppointment);
    serviceAppointment.setEndTime(Time.valueOf(localEndTime));
    // don't do parking lot check yet
    // don't do employee assignment yet

    ServiceAppointment appointment = serviceAppointmentRepo.save(serviceAppointment);
    return appointment;
  }

  public LocalTime findEndTime(LocalTime startTime, ServiceAppointment serviceAppointment){
    LocalTime localStartTime = serviceAppointment.getStartTime().toLocalTime();
    LocalTime localEndTime = localStartTime.plusHours((long)Math.floor(serviceAppointment.getService().getLengthInHours()));
    localEndTime.plusMinutes((long)(serviceAppointment.getService().getLengthInHours() - Math.floor(serviceAppointment.getService().getLengthInHours())));
    return localEndTime;
  }

  // 2
  @Transactional
  public Iterable<ServiceAppointment> getAllServiceAppointments(){
    return serviceAppointmentRepo.findAll();
  }

  @Transactional
  public ServiceAppointment findServiceAppointmentById(int id){
    ServiceAppointment appointment = serviceAppointmentRepo.findServiceAppointmentById(id);
    if (appointment == null){
      throw new PLMSException(HttpStatus.NOT_FOUND, "Service appointment with ID " + id + " does not exist.");
    }
    return appointment;
  }

  // 3
  @Transactional
  public Iterable<ServiceAppointment> getAllServiceAppointmentsByDate(Date date){
    // first find all the appointments
    // iterate over them and add them to a new iterable list
    // return that list
    Iterable<ServiceAppointment> appointments = serviceAppointmentRepo.findAll();
    ArrayList<ServiceAppointment> appointmentsOnDate = new ArrayList<ServiceAppointment>();
    for(ServiceAppointment appt : appointments){
      if (date.equals(appt.getDate())){
        // date matches, add it
        appointmentsOnDate.add(appt);
      }
    }
    if (appointmentsOnDate.isEmpty()){
      throw new PLMSException(HttpStatus.NOT_FOUND, "There are no appointments on date " + date);
    }
    // return the list
    return appointmentsOnDate;
  }

  // 4; we take in the employee's ID to find their appointments, which is their email
  @Transactional
  public Iterable<ServiceAppointment> getAllServiceAppointmentsByEmployee(String employeeEmail){
    Iterable<ServiceAppointment> appointments = serviceAppointmentRepo.findAll();
    ArrayList<ServiceAppointment> appointmentsForEmployee = new ArrayList<ServiceAppointment>();
    for(ServiceAppointment appt : appointments){
      // check to see if employee is null
      if (appt.getEmployee() != null){
        // check to see if the employee's email matches the request email
        if(appt.getEmployee().getEmail().equals(employeeEmail)){
          appointmentsForEmployee.add(appt);
        }
      }
    }

    if (appointmentsForEmployee.isEmpty()){
      // null means service appointment doesn't exist, throw PLMS error
      throw new PLMSException(HttpStatus.NOT_FOUND, "There are no service appointments for employee " + employeeEmail);
    }
    // return the list
    return appointmentsForEmployee;
  }

  // 4; we take in the monthly customer's ID to find their appointments, which is their email
  @Transactional
  public Iterable<ServiceAppointment> getAllServiceAppointmentsByMonthlyCustomer(String monthlyCustomerEmail){
    Iterable<ServiceAppointment> appointments = serviceAppointmentRepo.findAll();
    ArrayList<ServiceAppointment> appointmentsForCustomer = new ArrayList<ServiceAppointment>();
    for(ServiceAppointment appt : appointments){
      // check to see if employee is null
      if (appt.getCustomer() != null){
        // check to see if the employee's email matches the request email
        if(appt.getCustomer().getEmail().equals(monthlyCustomerEmail)){
          appointmentsForCustomer.add(appt);
        }
      }
    }

    if (appointmentsForCustomer.isEmpty()){
      // null means service appointment doesn't exist, throw PLMS error
      throw new PLMSException(HttpStatus.NOT_FOUND, "There are no service appointments for customer " + monthlyCustomerEmail);
    }
    // return the list
    return appointmentsForCustomer;
  }

  // 7: We are deleting appointment's by their ID
  @Transactional
  public void deleteServiceAppointmentById(int id){
    // first get the service appointment by id
    // ensure the appointment exists; throw error saying it doesn't exist if its not there as we want the user to be notified of this case
    // once ensured its not null, delete the appointmentment from DB
    ServiceAppointment serviceAppointmentToDelete = serviceAppointmentRepo.findServiceAppointmentById(id);
    if (serviceAppointmentToDelete == null){
      // null means service appointment doesn't exist, throw PLMS error
      throw new PLMSException(HttpStatus.NOT_FOUND, "Service appointment with ID " + id + " does not exist.");
    }
    
    // the appointment exists, so delete it
    serviceAppointmentRepo.deleteById(id);
  }

  @Transactional
  public ServiceAppointment updateServiceAppointment(ServiceAppointment serviceAppointment, int id){
    // first calculate the end time of the service appointment by using the length of the appointment
    ServiceAppointment updatedAppointment = serviceAppointmentRepo.findServiceAppointmentById(id);
    if (updatedAppointment == null){
      throw new PLMSException(HttpStatus.NOT_FOUND, "Service appointment is not found.");
    }

    updatedAppointment.setStartTime(serviceAppointment.getStartTime());
    LocalTime localEndTime = findEndTime(serviceAppointment.getStartTime().toLocalTime(), serviceAppointment);
    updatedAppointment.setEndTime(Time.valueOf(localEndTime));
    updatedAppointment.setDate(serviceAppointment.getDate());
    updatedAppointment.setId(id);
    // don't do parking lot check yet
    // don't do employee assignment yet

    ServiceAppointment appointment = serviceAppointmentRepo.save(updatedAppointment);
    return appointment;
  }
}
