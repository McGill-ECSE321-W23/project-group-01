package ca.mcgill.ecse321.PLMS.service;

import java.sql.Date;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

  // 2
  @Transactional
  public Iterable<ServiceAppointment> getAllServiceAppointments(){
    return serviceAppointmentRepo.findAll();
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
    // return the list
    return appointmentsForCustomer;
  }
}
