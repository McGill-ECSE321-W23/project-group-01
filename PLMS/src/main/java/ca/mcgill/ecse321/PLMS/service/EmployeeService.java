package ca.mcgill.ecse321.PLMS.service;

import ca.mcgill.ecse321.PLMS.exception.PLMSException;
import ca.mcgill.ecse321.PLMS.model.Employee;
import ca.mcgill.ecse321.PLMS.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    /**
     * Service method to fetch all existing employees in the database
     * @throws PLMSException - if no employees exist in the system
     */
    @Transactional
    public Iterable<Employee> getAllEmployees(){
        ArrayList<Employee> arrayList = (ArrayList<Employee>) employeeRepository.findAll();
        if (arrayList.isEmpty())
            throw new PLMSException(HttpStatus.NOT_FOUND, "There are no employees in the system");
        return employeeRepository.findAll(); }

    /**
     * Service method to fetch an existing employee with a specific email from the database
     * @throws PLMSException - If employee does not exist
     */
    @Transactional
    public Employee getEmployeeByEmail(String email) {
        Employee employee = employeeRepository.findEmployeeByEmail(email);
        if (employee == null) {
            throw new PLMSException(HttpStatus.NOT_FOUND, "Employee not found.");
        }
        return employee;
    }


    /**
     * Service method that updates the employee's information in the database
     * @throws PLMSException - If emplooyee does nto exist
     */
    @Transactional
    public Employee updateEmployee(Employee employee)
    {
        Employee e = getEmployeeByEmail(employee.getEmail());
        if(employee.getHourlyWage() <= 0)
            throw new PLMSException(HttpStatus.BAD_REQUEST, "Hourly wage must be positive.");
        e.setHourlyWage(employee.getHourlyWage());
        e.setPassword(employee.getPassword());
        e.setJobTitle(employee.getJobTitle());
        e.setName(employee.getName());
        return employeeRepository.save(e);
    }

    /**
     * Service method to store a created employee in the database
     * @throws PLMSException - If an employee already exists
     */
    @Transactional
    public Employee createEmployeeAccount(Employee employee) {
        if(employee.getHourlyWage() <= 0)
            throw new PLMSException(HttpStatus.BAD_REQUEST, "Hourly wage must be positive.");
        if (employeeRepository.findEmployeeByEmail(employee.getEmail()) == null)
            return employeeRepository.save(employee);
        else
            throw new PLMSException(HttpStatus.CONFLICT, "Employee account with this email already exists");
    }

    /**
     * Service method to delete an employee from the database
     */
    @Transactional
    public void deleteEmployeeAccount(String email) {
        employeeRepository.delete(getEmployeeByEmail(email));
    }
}
