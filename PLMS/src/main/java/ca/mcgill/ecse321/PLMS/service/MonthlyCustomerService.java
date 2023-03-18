package ca.mcgill.ecse321.PLMS.service;

import ca.mcgill.ecse321.PLMS.exception.PLMSException;
import ca.mcgill.ecse321.PLMS.model.MonthlyCustomer;
import ca.mcgill.ecse321.PLMS.repository.MonthlyCustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MonthlyCustomerService {

    @Autowired
    MonthlyCustomerRepository monthlyCustomerRepository;

    @Transactional
    public Iterable<MonthlyCustomer> getAllMonthlyCustomers() { return monthlyCustomerRepository.findAll(); }

    @Transactional
    public MonthlyCustomer getMonthlyCustomerByEmail(String email) {
        MonthlyCustomer monthlyCustomer = monthlyCustomerRepository.findMonthlyCustomerByEmail(email);
        if (monthlyCustomer == null) {
            throw new PLMSException(HttpStatus.NOT_FOUND, "Owner not found.");
        }
        return monthlyCustomer;
    }

    @Transactional
    public MonthlyCustomer updateMonthlyCustomer(MonthlyCustomer monthlyCustomer)
    {
        getMonthlyCustomerByEmail(monthlyCustomer.getEmail());
        return monthlyCustomerRepository.save(monthlyCustomer);
    }

    @Transactional
    public MonthlyCustomer createMonthlyCustomerAccount(MonthlyCustomer monthlyCustomer) {
        // Create the account
        if (monthlyCustomerRepository.findMonthlyCustomerByEmail(monthlyCustomer.getEmail()) == null)
            return monthlyCustomerRepository.save(monthlyCustomer);
        else
            throw new PLMSException(HttpStatus.CONFLICT, "Account with this email already exists");

    }


}
