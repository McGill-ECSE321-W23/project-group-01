package ca.mcgill.ecse321.PLMS.controller;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.PLMS.dto.ServiceRequestDto;
import ca.mcgill.ecse321.PLMS.dto.ServiceResponseDto;
// import ca.mcgill.ecse321.PLMS.model.MonthlyCustomer;
import ca.mcgill.ecse321.PLMS.model.Service;
// import ca.mcgill.ecse321.PLMS.model.ServiceAppointment;
// import ca.mcgill.ecse321.PLMS.service.MonthlyCustomerService;
// import ca.mcgill.ecse321.PLMS.service.ServiceAppointmentService;
import ca.mcgill.ecse321.PLMS.service.ServiceService;
import jakarta.validation.Valid;


@RestController
public class ServiceController {
    @Autowired
    private ServiceService serviceService;

    /**
     * Gets all services.
     *
     * @return All services.
     */
    @GetMapping("/service")
    public Iterable<ServiceResponseDto> getAllServices(){
        return StreamSupport.stream(serviceService.getAllServices().spliterator(), false).map(s -> new ServiceResponseDto(s)).collect(Collectors.toList());
    }

    /**
     * Gets a service by the service number
     *
     * @return service with serviceName
     */
    @GetMapping("/service/{serviceName}")
    public ResponseEntity<ServiceResponseDto> getServiceByServiceNumber(@PathVariable String serviceName){
        Service service = serviceService.getServiceByServiceName(serviceName);
        ServiceResponseDto responseBody = new ServiceResponseDto(service);
        return new ResponseEntity<ServiceResponseDto>(responseBody, HttpStatus.OK);
    }

    /**
     * Creates a service.
     *
     * @return serviceDto of the created service
     */
    @PostMapping("/service/create")
    public ResponseEntity<ServiceResponseDto> createService(@Valid @RequestBody ServiceRequestDto serviceDto){
        Service service = serviceDto.toModel();
        service = serviceService.createService(service);
        ServiceResponseDto responseBody = new ServiceResponseDto(service);
        return new ResponseEntity<ServiceResponseDto>(responseBody, HttpStatus.CREATED);
    }


    /**
     * Allows updates for all service variables
     *
     * @return service with updated values
     */
    @PutMapping("/service/{serviceName}")
    public ResponseEntity<ServiceResponseDto> updateServiceInfo(@PathVariable String serviceName, @RequestBody @Valid ServiceRequestDto serviceDto){
        Service service = serviceDto.toModel();
        service.setServiceName(serviceName);
        service = serviceService.updateService(service);
        ServiceResponseDto responseBody = new ServiceResponseDto(service);
        return new ResponseEntity<ServiceResponseDto>(responseBody, HttpStatus.CREATED);
    }

    /**
     * Deletes a service
     */
    @DeleteMapping("/service/{serviceName}")
    public void deleteService(@PathVariable String serviceName){
        serviceService.deleteServiceByServiceName(serviceName);
    }

}


