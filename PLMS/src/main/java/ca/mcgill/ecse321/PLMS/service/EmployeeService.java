package ca.mcgill.ecse321.PLMS.service;

import ca.mcgill.ecse321.PLMS.exception.PLMSException;
import ca.mcgill.ecse321.PLMS.model.Employee;
import ca.mcgill.ecse321.PLMS.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Transactional
    public Iterable<Employee> getAllEmployees(){ return employeeRepository.findAll(); }
    @Transactional
    public Employee getEmployeeByEmail(String email) {
        Employee employee = employeeRepository.findEmployeeByEmail(email);
        if (employee == null) {
            throw new PLMSException(HttpStatus.NOT_FOUND, "Emplpyee not found.");
        }
        return employee;
    }

    @Transactional
    public Employee createEmployeeAccount(Employee employee) {
        // Create the account
        return employeeRepository.save(employee);
    }
}
