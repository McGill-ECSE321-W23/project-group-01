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
    public Employee updateEmployee(Employee employee)
    {
        return employeeRepository.save(getEmployeeByEmail(employee.getEmail()));
    }

    @Transactional
    public Employee createEmployeeAccount(Employee employee) {

        if (employeeRepository.findEmployeeByEmail(employee.getEmail()) == null)
            return employeeRepository.save(employee);
        else
            throw new PLMSException(HttpStatus.CONFLICT, "Account with this email already exists");
    }

    @Transactional
    public void deleteEmployeeAccount(String email) {
        employeeRepository.delete(getEmployeeByEmail(email));
    }
}
