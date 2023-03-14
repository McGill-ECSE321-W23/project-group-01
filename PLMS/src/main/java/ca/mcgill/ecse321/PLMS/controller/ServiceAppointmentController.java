package ca.mcgill.ecse321.PLMS.controller;

import java.sql.Date;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.PLMS.dto.ServiceAppointmentResponseDto;
import ca.mcgill.ecse321.PLMS.model.ServiceAppointment;
import ca.mcgill.ecse321.PLMS.service.ServiceAppointmentService;

@RestController
public class ServiceAppointmentController {
    
    @Autowired
    private ServiceAppointmentService serviceAppointmentService;

    /**
     *  GET/POST/DELETE Service Appointment by ID
        GET All Service Appointments
        GET service appointment by Date
        GET service appointment by employee
        GET service appointment by monthly customer
     */


     /**
      * Gets all service appointements
      * 
      * @return All service appointments
      */
      @GetMapping("/ServiceAppointment")
      public Iterable<ServiceAppointmentResponseDto> getAllServiceAppointment(){
        return StreamSupport.stream(serviceAppointmentService.getAllServiceAppointment().spliterator(), false)
        .map(s -> new ServiceAppointmentResponseDto(s))
        .collect(Collectors.toList());
      }

      /**
       * Get service appointment by ID
       * 
       * @param id The id of the service appointement to look up.
       * @return The service appointement with the given id.
       */
      @GetMapping("/ServiceAppointment/{id}")
      public ResponseEntity<ServiceAppointmentResponseDto> getServiceAppointmentById(@PathVariable int id) {
        ServiceAppointment serviceAppointment = serviceAppointmentService.getServiceAppointmentById(id);
        ServiceAppointmentResponseDto responseBody = new ServiceAppointmentResponseDto(serviceAppointment);
        return new ResponseEntity<ServiceAppointmentResponseDto>(responseBody, HttpStatus.OK);
      }

      /**
       * Gets all service appointment with in a date
       * 
       * @param date The date at which you want the 
       */
      @GetMapping("/ServiceAppointment/date/{date}")
      public ResponseEntity<ServiceAppointmentResponseDto> getServiceAppointmentByDate(@PathVariable Date date) {
        ServiceAppointment serviceAppointment = serviceAppointmentService.getServiceAppointmentByDate(date);
        ServiceAppointmentResponseDto responseBody = new ServiceAppointmentResponseDto(serviceAppointment);
        return new ResponseEntity<ServiceAppointmentResponseDto>(responseBody, HttpStatus.OK);
      }
      
}
