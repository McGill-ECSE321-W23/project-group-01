package ca.mcgill.ecse321.PLMS.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import ca.mcgill.ecse321.PLMS.exception.PLMSException;
import ca.mcgill.ecse321.PLMS.model.Service;
import ca.mcgill.ecse321.PLMS.repository.ServiceRepository;

@SpringBootTest
public class ServiceServiceTests {
    @Mock
    private ServiceRepository serviceRepository;

    @InjectMocks
    private ServiceService serviceService;

   
    @Test
    public void testGetAllServices() {
        final String serviceName = "Car wash";
        final double fee = 35.5;
        final double lengthInHours = 1;
        final Service serv = new Service(serviceName, fee, lengthInHours);

        final String serviceName2 = "Oil change";
        final double fee2 = 40;
        final double lengthInHours2 = 3;
        final Service serv2 = new Service(serviceName2, fee2, lengthInHours2);

        ArrayList<Service> services = new ArrayList<Service>();
        services.add(serv);
        services.add(serv2);


        when(serviceRepository.findAll()).thenReturn(services);
        Iterable<Service> output = serviceService.getAllServices();
        Iterator<Service> i = output.iterator();
        Service output1 = i.next();
        Service output2 = i.next();
        assertEquals(output1.getServiceName(), serviceName);
        assertEquals(output2.getServiceName(), serviceName2);
    }

    @Test
    public void testGetServiceByServiceName() {
        final String serviceName = "Car wash";
        final double fee = 35.5;
        final double lengthInHours = 1;
        final Service serv = new Service(serviceName, fee, lengthInHours);
        when(serviceRepository.findServiceByServiceName(serviceName)).thenReturn(serv);
        Service output = serviceService.getServiceByServiceName(serviceName);
        assertEquals(output.getServiceName(), serviceName);
    }

    @Test
    public void testGetInvalidServiceByServiceName() {
        final String string = "hehe";
        when(serviceRepository.findAll()).thenReturn(null);
        PLMSException e = assertThrows(PLMSException.class, () -> serviceService.getServiceByServiceName(string));
        assertEquals(e.getStatus(), HttpStatus.NOT_FOUND);
        assertEquals(e.getMessage(), "Service with name " + string + " does not exists.");
    }



    @Test
    public void testCreateService()
    {
        final String serviceName = "Car wash";
        final double fee = 35.5;
        final double lengthInHours = 1;
        final Service serv = new Service(serviceName, fee, lengthInHours);
        when(serviceRepository.findServiceByServiceName(serviceName)).thenReturn(null);
        when(serviceRepository.save(serv)).thenReturn(serv);

        Service output = serviceService.createService(serv);
        assertEquals(serviceName, output.getServiceName());
        verify(serviceRepository, times(1)).save(serv);

    }

    @Test
    public void testInvalidDelete()
    {
        final String string = "hehe";
        when(serviceRepository.findAll()).thenReturn(null);
        PLMSException e = assertThrows(PLMSException.class, () -> serviceService.deleteServiceByServiceName(string));
        assertEquals(e.getStatus(), HttpStatus.NOT_FOUND);
        assertEquals(e.getMessage(), "Service with name " + string + " does not exists.");
    }

    @Test
    public void testCreateInvalidService()
    {
        final String serviceName = "Car wash";
        final double fee = 35.5;
        final double lengthInHours = 1;
        final Service serv = new Service(serviceName, fee, lengthInHours);
        when(serviceRepository.findServiceByServiceName(serviceName)).thenReturn(serv);

        PLMSException e = assertThrows(PLMSException.class, () -> serviceService.createService(serv));
        assertEquals(e.getStatus(), HttpStatus.BAD_REQUEST);
        assertEquals(e.getMessage(), "Service with service name: " + serviceName + " already exists.");

    }

    @Test
    public void testValidDelete()
    {
        final String serviceName = "Car wash";
        final double fee = 35.5;
        final double lengthInHours = 1;
        final Service serv = new Service(serviceName, fee, lengthInHours);
        when(serviceRepository.findServiceByServiceName(serviceName)).thenReturn(serv);
        doNothing().when(serviceRepository).deleteById(serviceName);

        serviceService.deleteServiceByServiceName(serviceName);
        verify(serviceRepository).deleteById(serviceName);

    }

}
