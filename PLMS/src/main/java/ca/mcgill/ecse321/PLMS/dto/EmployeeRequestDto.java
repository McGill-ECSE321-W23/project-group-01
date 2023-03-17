package ca.mcgill.ecse321.PLMS.dto;

import ca.mcgill.ecse321.PLMS.model.Employee;

public class EmployeeRequestDto {

    private String email;
    private String password;
    private String name;
    private String jobTitle;
    private double hourlyWage;

    public void setEmail(String aEmail)
    { this.email = aEmail; }

    public void setPassword(String aPassword)
    { this.password = aPassword; }

    public void setName(String aName)
    { this.name = aName; }

    public void setHourlyWage(double hourlyWage) {
        this.hourlyWage = hourlyWage;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Employee toModel() {
        return new Employee(email, password, name, jobTitle, hourlyWage);
    }
}
