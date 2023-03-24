package ca.mcgill.ecse321.PLMS.dto;

import ca.mcgill.ecse321.PLMS.model.Employee;

public class EmployeeResponseDto {

    private String email;
    private String password;
    private String name;
    private String jobTitle;
    private double hourlyWage;

    public EmployeeResponseDto(Employee employee) {
        this.email = employee.getEmail();
        this.password = employee.getPassword();
        this.name = employee.getName();
        this.jobTitle = employee.getJobTitle();
        this.hourlyWage = employee.getHourlyWage();
    }

    EmployeeResponseDto() {}

    public String getEmail()
    { return email; }

    public String getPassword()
    { return password; }

    public String getName()
    { return name; }

    public String getJobTitle()
    { return jobTitle; }

    public double getHourlyWage()
    { return hourlyWage; }
}
