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

    @Transactional
    public Iterable<Employee> getAllEmployees(){
        ArrayList<Employee> arrayList = (ArrayList<Employee>) employeeRepository.findAll();
        if (arrayList.isEmpty())
            throw new PLMSException(HttpStatus.NOT_FOUND, "There are no employees in the system");
        return employeeRepository.findAll(); }

    @Transactional
    public Employee getEmployeeByEmail(String email) {
        Employee employee = employeeRepository.findEmployeeByEmail(email);
        if (employee == null) {
            throw new PLMSException(HttpStatus.NOT_FOUND, "Employee not found.");
        }
        return employee;
    }

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

    @Transactional
    public Employee createEmployeeAccount(Employee employee) {
        if(employee.getHourlyWage() <= 0)
            throw new PLMSException(HttpStatus.BAD_REQUEST, "Hourly wage must be positive.");
        if (employeeRepository.findEmployeeByEmail(employee.getEmail()) == null)
            return employeeRepository.save(employee);
        else
            throw new PLMSException(HttpStatus.CONFLICT, "Employee account with this email already exists");
    }

    @Transactional
    public void deleteEmployeeAccount(String email) {
        employeeRepository.delete(getEmployeeByEmail(email));
    }
}
