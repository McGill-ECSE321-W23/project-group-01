package ca.mcgill.ecse321.PLMS;

import java.sql.Time;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import ca.mcgill.ecse321.PLMS.model.Owner;
import ca.mcgill.ecse321.PLMS.model.ParkingLot;
import ca.mcgill.ecse321.PLMS.repository.OwnerRepository;
import ca.mcgill.ecse321.PLMS.repository.ParkingLotRepository;

/**
 * Main Application class for the PLMS software system
 * It's the class that activates the whole software system when run
 * The PLMS software system implements the Spring framework
 */
@SpringBootApplication
public class PlmsApplication {

	@Bean
    CommandLineRunner initDatabase(@Autowired OwnerRepository ownerRepository,@Autowired ParkingLotRepository parkingLotRepository) {
         
        return args -> {
			if (ownerRepository.count() == 0){
				ownerRepository.save(new Owner("admin@mail.com", "MyParking1ot$", "Admin"));
				parkingLotRepository.save(new ParkingLot(new Time(8, 0, 0), new Time(17, 0, 0), 20, 10, 30, 50));
			}
        };
    }
	
	/**
	 * Main method, start point of the application
	 */
	public static void main(String[] args) {
		SpringApplication.run(PlmsApplication.class, args);
	}

}
