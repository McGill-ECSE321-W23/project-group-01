package ca.mcgill.ecse321.PLMS.service;

import ca.mcgill.ecse321.PLMS.exception.PLMSException;
import ca.mcgill.ecse321.PLMS.model.MonthlyCustomer;
import ca.mcgill.ecse321.PLMS.model.ParkingLot;
import ca.mcgill.ecse321.PLMS.repository.MonthlyCustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class MonthlyCustomerService {

    @Autowired
    MonthlyCustomerRepository monthlyCustomerRepository;

    @Transactional
    public Iterable<MonthlyCustomer> getAllMonthlyCustomers() {
        ArrayList<MonthlyCustomer> arrayList = (ArrayList<MonthlyCustomer>) monthlyCustomerRepository.findAll();
        if (arrayList.isEmpty())
            throw new PLMSException(HttpStatus.NOT_FOUND, "There are no monthly customers in the system");
        return monthlyCustomerRepository.findAll(); }

    @Transactional
    public MonthlyCustomer getMonthlyCustomerByEmail(String email) {
        MonthlyCustomer monthlyCustomer = monthlyCustomerRepository.findMonthlyCustomerByEmail(email);
        if (monthlyCustomer == null) {
            throw new PLMSException(HttpStatus.NOT_FOUND, "Monthly customer not found.");
        }
        return monthlyCustomer;
    }

    @Transactional
    public MonthlyCustomer updateMonthlyCustomer(MonthlyCustomer monthlyCustomer)
    {
        MonthlyCustomer customer = getMonthlyCustomerByEmail(monthlyCustomer.getEmail());
        customer.setName(monthlyCustomer.getName());
        customer.setPassword(monthlyCustomer.getPassword());
        return monthlyCustomerRepository.save(customer);
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
