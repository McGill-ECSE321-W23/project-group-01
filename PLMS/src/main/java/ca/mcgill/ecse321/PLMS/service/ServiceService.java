package ca.mcgill.ecse321.PLMS.service;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.PLMS.repository.ServiceRepository;
import ca.mcgill.ecse321.PLMS.exception.PLMSException;
import ca.mcgill.ecse321.PLMS.model.Service;

@org.springframework.stereotype.Service
public class ServiceService {

    /*
   * Functionalities of the service service class
   * 
   * 1) GET all services 
   * 2) GET service appointment by service name
   * 3) POST service
   * 4) DELETE service appointment by service name
   * 5) PUT service
   */

    @Autowired
    ServiceRepository serviceRepository;


    /**
	 * creates a new service.
	 *
	 * The service Name is trimmed before being saved.
	 *
	 * Throws an PLMSException if the serviceName is empty, if the
	 * cost is null or < 0, if the serviceName is already taken by another service,
	 * or if the lengthInHours is null or < 0.
	 *
	 * @param serviceName
	 *            Name of the new service (primary key)
	 * @param cost
	 *            Cost for the service
	 * @param lengthInHours
	 *            Length in hours for the service
	 * @return
	 */
    @Transactional
    public Service createService(String serviceName, Double cost, Double lengthInHours){

        ArrayList<String> errorMessage = new ArrayList<String>();
        Service service = new Service();


        if (serviceName == null){
            errorMessage.add("Service name cannot be empty");
        }
        else {
            serviceName = serviceName.trim();
            if (serviceName.length() == 0) {
				errorMessage.add("Service name cannot be empty.");
			}
            Service serviceWithSameName = serviceRepository.findServiceByServiceName(serviceName);
            if(serviceWithSameName != null){
                errorMessage.add("Service name already taken");
            }
        }

        if(cost == null || cost < 0){
            errorMessage.add("Cost needs to be a number greater or equal to 0");
        }

        if(lengthInHours == null || lengthInHours < 0){
            errorMessage.add("Length in hours needs to be a number greater or equal to 0");
        }
        

        if (errorMessage.size() > 0) {
			throw new PLMSException(HttpStatus.NOT_ACCEPTABLE, String.join(" ", errorMessage));
		}

        serviceName = serviceName.trim();

        service.setServiceName(serviceName);
        service.setCost(cost);
        service.setLengthInHours(lengthInHours);
        serviceRepository.save(service);
        return service;
    }

    /**
	 * Returns the service information for the service with the given
	 * name.
	 *
	 * Throws a PLMSException if there is no service with the
	 * given name.
	 *
	 * @param serviceName
	 *            Name of an existing service (primary key)
	 * @return service information for the service
	 */
    @Transactional
    public Service getServiceByServiceName(String serviceName){
        Service service = serviceRepository.findServiceByServiceName(serviceName);

        if(service == null){
            throw new PLMSException(HttpStatus.NOT_FOUND, "Service with name " + serviceName + " does not exists.");
        }
        return service;
    }

    /**
	 * Deletes the service with the given serviceName.
	 *
	 * Throws a PLMSException if there is no service with the
	 * given name.
	 *
	 * @param serviceName
	 *            Name of an existing service (primary key)
	 */
    @Transactional
    public void deleteServiceByServiceName(String serviceName){
        Service serviceToDelete = serviceRepository.findServiceByServiceName(serviceName);

        if(serviceToDelete == null){
            throw new PLMSException(HttpStatus.NOT_FOUND, "Service with name " + serviceName + " does not exists.");
        }

        serviceRepository.deleteById(serviceName);
    }

    /**
	 * Returns the service information for all services.
	 *
	 * @return List of all services
	 */
    @Transactional
    public Iterable<Service> getAllServices(){
        return serviceRepository.findAll();
    }


    /**
	 * Updates the service information for the service with the given name.
	 *
	 * Throws a PLMSException if there is no service with the
	 * given name.
	 *
	 * @param serviceName
	 *            Name of an existing service (primary key)
	 * @param newCost
	 *            New cost for the service
	 * @param newLengthInHours
	 *            New lengthInHours for the service
	 * @return Updated service
	 */
    @Transactional
    public Service updateService(String serviceName, Double newCost, Double newLengthInHours){
        Service serviceToUpdate = serviceRepository.findServiceByServiceName(serviceName);

        ArrayList<String> errorMessage = new ArrayList<String>();
        
        if(serviceToUpdate == null){
            throw new PLMSException(HttpStatus.NOT_FOUND, "Service with name " + serviceName + " does not exists.");
        }

        if(newCost == null || newCost < 0){
            errorMessage.add("Cost needs to be a number greater or equal to 0");
        }

        if(newLengthInHours == null || newLengthInHours < 0){
            errorMessage.add("Length in hours needs to be a number greater or equal to 0");
        }
        

        if (errorMessage.size() > 0) {
			throw new PLMSException(HttpStatus.BAD_REQUEST, String.join(" ", errorMessage));
		}

        serviceToUpdate.setCost(newCost);
        serviceToUpdate.setLengthInHours(newLengthInHours);
        serviceRepository.save(serviceToUpdate);

        return serviceToUpdate;
    }



    
}
