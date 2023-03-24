package ca.mcgill.ecse321.PLMS.service;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.PLMS.repository.ServiceRepository;
import ca.mcgill.ecse321.PLMS.exception.PLMSException;
import ca.mcgill.ecse321.PLMS.model.Service;

<<<<<<< HEAD
=======
//@org.springframework.stereotype.Service
>>>>>>> ea1e6dfd056e871e575557b37e51e46f7a1c33b7
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
     * Service method to store the created service object into the database
     */
    @Transactional
    public Service createService(Service service){
        //checks on the new object are made in the DTO
        //check if the service already exists
        if (serviceRepository.findServiceByServiceName(service.getServiceName()) != null){
            throw new PLMSException(HttpStatus.BAD_REQUEST, "Service with service name: " + service.getServiceName() + " already exists.");
        }
<<<<<<< HEAD
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
=======
        //create object
        service = serviceRepository.save(service);
        //returned created object
>>>>>>> ea1e6dfd056e871e575557b37e51e46f7a1c33b7
        return service;
    }

    /**
     * Service method that updates a floor object in the database
     */
    @jakarta.transaction.Transactional
    public Service updateService(Service service){
        //check if the service exists (the service has to exist to edit it)
        Service existingService = getServiceByServiceName(service.getServiceName());

        // save the changes to the database
        existingService = serviceRepository.save(existingService);
        return existingService;
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
        ArrayList<Service> arrayList = (ArrayList<Service>) serviceRepository.findAll();
        if (arrayList.isEmpty())
            throw new PLMSException(HttpStatus.NOT_FOUND, "There are no services in the system");
        return serviceRepository.findAll();
    }

<<<<<<< HEAD

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



    
=======
>>>>>>> ea1e6dfd056e871e575557b37e51e46f7a1c33b7
}
