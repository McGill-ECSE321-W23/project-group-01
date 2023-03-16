package ca.mcgill.ecse321.PLMS.service;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.PLMS.exception.PLMSException;
import ca.mcgill.ecse321.PLMS.model.Account;
import ca.mcgill.ecse321.PLMS.model.Employee;
import ca.mcgill.ecse321.PLMS.model.MonthlyCustomer;
import ca.mcgill.ecse321.PLMS.model.Owner;
import ca.mcgill.ecse321.PLMS.repository.AccountRepository;
import ca.mcgill.ecse321.PLMS.repository.EmployeeRepository;
import ca.mcgill.ecse321.PLMS.repository.MonthlyCustomerRepository;
import ca.mcgill.ecse321.PLMS.repository.OwnerRepository;

/**
 * Service class for the Account model objects in the database
 * @author Karl Bridi Soft. Eng. student
 */
@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    OwnerRepository ownerRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    MonthlyCustomerRepository monthlyCustomerRepository;

    /**
     * Service method to create a new account (employee, owner, or monthly customer)
     * @param email
     * @param name
     * @param password
     * @param type: "owner", "employee", or "monthly customer"
     * @return: instance of the newly created object
     */
    @Transactional
	public Account createAccount(String email, String name, String password, String type) {
        
        // Error message to be thrown as exception in case of failure
        ArrayList<String> errorMessage = new ArrayList<String>();

        // INPUT VALIDATION

        // Validating email
        if (email == null){ // Make sure email is not null
            errorMessage.add("Email cannot be empty.");
        }else{
            email = email.trim();
            if (email.length() == 0) { // Make sure email is not empty
				errorMessage.add("Email cannot be empty.");
			}
        }
        Account accountWithSameEmail = accountRepository.findAccountByEmail(email);
        if(accountWithSameEmail != null){ // Make sure email doesn't already exist
            errorMessage.add("Email already taken.");
        }
        
        // Validating name
        if (name == null){ // Make sure name is not null
            errorMessage.add("Name cannot be empty.");
        }else{
            name = name.trim();
            if (name.length() == 0) { // Make sure name is not empty
				errorMessage.add("Name cannot be empty.");
			}
        }
        Pattern pSpecialChar = Pattern.compile("[^A-Za-z0-9]");
        Pattern pNumber = Pattern.compile("[0-9]");
        Matcher m = pSpecialChar.matcher(name);
        m = pNumber.matcher(name);
        if(m.find()){ // Make sure name doesn't contain number
            errorMessage.add("Name cannot contain number.");
        }
        if(m.find()){ // Make sure name doesn't special chars
            errorMessage.add("Name cannot contain special characters.");
        }
        

        // Validating password
        Pattern pCap = Pattern.compile("[A-Z]");
        Pattern pLow = Pattern.compile("[a-z]");
        if (password == null){ // Make sure password is not null
            errorMessage.add("Password cannot be empty.");
        }else{
            password = password.trim();
            if (password.length() == 0) { // Make sure password is not empty
				errorMessage.add("Password cannot be empty.");
			}else if(password.length() < 8){
                errorMessage.add("Password must be at least 8 characters long.");
            }
        }
        if(!m.find()){ // Make sure password contains special char
            errorMessage.add("Password must contain at least one special character.");
        }
        m = pCap.matcher(password);
        if(!m.find()){ // Make sure password contains capital char
            errorMessage.add("Password must contain at least one capital letter.");
        }
        m = pLow.matcher(password);
        if(!m.find()){ // Make sure password contains lowercase char
            errorMessage.add("Password must contain at least one lowcase letter.");
        }
        m = pNumber.matcher(password);
        if(!m.find()){ // Make sure password contains number
            errorMessage.add("Password must contain at least one number.");
        }

        // Throw exception if validation fails
        if (errorMessage.size() > 0) {
			throw new PLMSException(HttpStatus.NOT_ACCEPTABLE, String.join(" ", errorMessage));
		}
        
        // Instantiation of the new account
        Account account;
        if(type.equals("employee")){
            account = new Employee();
        }else if(type.equals("monthlyCustomer")){
            account = new MonthlyCustomer();
        }else{
            account = new Owner();
        }

        // Initialize values for new account
        account.setEmail(email);
        account.setName(name);
        account.setPassword(password);

        // Save new account
        accountRepository.save(account);
		
		return account;
	}

    /**
     * Service method to create a new employee account
     * @param email
     * @param name
     * @param password
     * @param jobTitle
     * @param hourlyWage
     * @return: instance of the newly created object
     */
    @Transactional
	public Employee createEmployeeAccount(String email, String name, String password, String jobTitle, Double hourlyWage) {

        // Error message to be thrown as exception in case of failure
        ArrayList<String> errorMessage = new ArrayList<String>();

        // INPUT VALIDATION

        // Validating job title
        if (jobTitle == null){ // Make sure name is not null
            errorMessage.add("Job title cannot be empty.");
        }else{
            jobTitle = jobTitle.trim();
            if (jobTitle.length() == 0) { // Make sure email is not empty
				errorMessage.add("Job title cannot be empty.");
			}
        }
        Pattern pSpecialChar = Pattern.compile("[^A-Za-z]");
        Matcher m = pSpecialChar.matcher(jobTitle);
        if(m.find()){
            errorMessage.add("Job title cannot contain special characters or numbers.");
        }

        //Validating hourly wage
        if(hourlyWage == null || hourlyWage < 0){
            errorMessage.add("Hourly wage needs to be a number greater or equal to 0");
        }

        if (errorMessage.size() > 0) {
			throw new PLMSException(HttpStatus.NOT_ACCEPTABLE, String.join(" ", errorMessage));
		}

        // Create the account
        Employee account = (Employee) createAccount(email, name, password, "employee");
        account.setJobTitle(jobTitle);
        account.setHourlyWage(hourlyWage);
        
        // Register the account into database
        employeeRepository.save(account);
		return account;
	}

    /**
     * Service method to create a new owner account
     * @param email
     * @param name
     * @param password
     * @return: instance of the newly created object
     */
    @Transactional
	public Owner createOwnerAccount(String email, String name, String password) {
        // Create the account
        Owner account = (Owner) createAccount(email, name, password, "owner");
        
        // Register the account into database
        ownerRepository.save(account);
		return account;
	}

    /**
     * Service method to create a new monthly customer account
     * @param email
     * @param name
     * @param password
     * @return: instance of the newly created object
     */
    @Transactional
	public MonthlyCustomer createMonthlyCustomerAccount(String email, String name, String password) {
        // Create the account
        MonthlyCustomer account = (MonthlyCustomer) createAccount(email, name, password, "monthlyCustomer");
        
        // Register the account into database
        monthlyCustomerRepository.save(account);
		return account;
	}

}
