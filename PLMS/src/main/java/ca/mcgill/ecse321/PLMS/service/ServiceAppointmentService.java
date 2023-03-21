package ca.mcgill.ecse321.PLMS.service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.PLMS.exception.PLMSException;
import ca.mcgill.ecse321.PLMS.model.Employee;
import ca.mcgill.ecse321.PLMS.model.ParkingLot;
import ca.mcgill.ecse321.PLMS.model.ServiceAppointment;
import ca.mcgill.ecse321.PLMS.repository.EmployeeRepository;
import ca.mcgill.ecse321.PLMS.repository.ParkingLotRepository;
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
  @Autowired
  ParkingLotRepository parkingLotRepository;
  @Autowired
  EmployeeRepository employeeRepository;

  // 1
  @Transactional
  public ServiceAppointment createServiceAppointment(ServiceAppointment serviceAppointment){
    // first calculate the end time of the service appointment by using the length of the appointment
    LocalTime localEndTime = findEndTime(serviceAppointment.getStartTime().toLocalTime(), serviceAppointment);
    serviceAppointment.setEndTime(Time.valueOf(localEndTime));
    
    // check for the parking lot in the database, if it doesn't exist yet we cannot create the appointment
    ParkingLot lot = parkingLotAddedToDatabase();

    // check to see if the hours are within lot hours of operation
    validateAppointmentHours(serviceAppointment.getStartTime(), serviceAppointment.getEndTime(), lot.getOpeningTime(), lot.getClosingTime());

    // we randomly assign employees, if there are any.
    serviceAppointment.setEmployee(findEmployeeToAssignToAppointment());

    ServiceAppointment appointment = serviceAppointmentRepo.save(serviceAppointment);
    return appointment;
  }

  public LocalTime findEndTime(LocalTime startTime, ServiceAppointment serviceAppointment){
    LocalTime localStartTime = serviceAppointment.getStartTime().toLocalTime();
    int hours = (int) serviceAppointment.getService().getLengthInHours();
    int minutes = (int) ((serviceAppointment.getService().getLengthInHours() - hours) * 60);
    LocalTime localEndTime = localStartTime.plusHours(hours).plusMinutes(minutes);
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
    // check for the parking lot in the database, if it doesn't exist yet we cannot create the appointment
    ParkingLot lot = parkingLotAddedToDatabase();

    // check to see if the hours are within lot hours of operation
    validateAppointmentHours(updatedAppointment.getStartTime(), updatedAppointment.getEndTime(), lot.getOpeningTime(), lot.getClosingTime());
    

    // we randomly assign employees, if there are any.
    updatedAppointment.setEmployee(findEmployeeToAssignToAppointment());
    ServiceAppointment appointment = serviceAppointmentRepo.save(updatedAppointment);
    return appointment;
  }

  /**
   * Method to find assigned employee to service appointment.
   * Employees are assigned at random, since our application does not need to accomodate schedules (as per Marwan).
   * @return
   */
  public Employee findEmployeeToAssignToAppointment(){
    Iterable<Employee> employees = employeeRepository.findAll();
    // for now, we won't restrict a user from booking an appointment if there aren't any employees
    if (!employees.iterator().hasNext()){
      return null;
    }
    // convert into an array list
    ArrayList<Employee> employeeList = StreamSupport.stream(employees.spliterator(), false).collect(Collectors.toCollection(ArrayList::new));

    // find a random index, and select the employee at that index.
    Random random = new Random();
    int i = random.nextInt(employeeList.size());
    Employee randomEmployee = employeeList.get(i);
    return randomEmployee;
  }
  /**
   * Helper method to find the single parking lot object
   * stored in the database. Throws exception if it isn't there.
   * @return - parking lot object, if it exists
   */
  public ParkingLot parkingLotAddedToDatabase(){
    Iterable<ParkingLot> lots = parkingLotRepository.findAll();
    // if no lot in the system, we cannot book an appointment
    if (!lots.iterator().hasNext()){
        throw new PLMSException(HttpStatus.BAD_REQUEST, "Cannot book appointment since the parking lot has not been created yet. Please try again at a later date.");
    }
    // return the parking lot
    return lots.iterator().next();
  }

  /**
   * Helper function to throw PLMS exceptions related to scheduling errors.
   * Appointments must fall within the hours of operation of the lot.
   * @param apptStartTime
   * @param apptEndTime
   * @param openingTime
   * @param closingTime
   */
  public void validateAppointmentHours(Time apptStartTime, Time apptEndTime, Time openingTime, Time closingTime){

    // check to see if start time is before the opening time
    int comparisonResult1 = apptStartTime.compareTo(openingTime);
    if (comparisonResult1 < 0) {
      throw new PLMSException(HttpStatus.BAD_REQUEST, "Cannot have an appointment beginning before the lot opens.");
    } 

    // check to see if the start time is after the lot closes
    int comparisonResult2 = apptStartTime.compareTo(closingTime);
    if (comparisonResult2 > 0) {
      throw new PLMSException(HttpStatus.BAD_REQUEST, "Cannot have an appointment beginning after the lot closes.");
    } 

    // check to see if the end time is after the lot closes
    int comparisonResult3 = apptEndTime.compareTo(closingTime);
    if (comparisonResult3 > 0) {
      throw new PLMSException(HttpStatus.BAD_REQUEST, "Cannot have an appointment ending after the lot closes.");
    } 

  }
}
