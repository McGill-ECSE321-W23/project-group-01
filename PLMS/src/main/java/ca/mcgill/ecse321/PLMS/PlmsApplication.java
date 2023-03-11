package ca.mcgill.ecse321.PLMS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Application class for the PLMS software system
 * It's the class that activates the whole software system when run
 * The PLMS software system implements the Spring framework
 */
@SpringBootApplication
public class PlmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlmsApplication.class, args);
	}

}
