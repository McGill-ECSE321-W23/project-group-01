package ca.mcgill.ecse321.PLMS.dto;

import ca.mcgill.ecse321.PLMS.model.Employee;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Range;
import org.springframework.boot.context.properties.bind.Name;

public class EmployeeRequestDto {

    @NotNull
    @NotBlank(message = "Email cannot be blank.")
    @Email
    private String email;

    @NotNull
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[!@#$%^&+=]).+$", message = "Password contains at least one uppercase, lowercase and special character [!@#$%^&+=]")
    @Size(min = 5, max = 13, message = "Password must have 5-13 character" )
    @NotBlank(message = "Password cannot be blank.")
    private String password;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z\s]+$", message = "Name can only have letters")
    @NotBlank(message = "Name cannot be blank.")
    private String name;

    @Pattern(regexp = "^[a-zA-Z\s]+$", message = "Job title can only have letters")
    private String jobTitle;

    @Positive
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
