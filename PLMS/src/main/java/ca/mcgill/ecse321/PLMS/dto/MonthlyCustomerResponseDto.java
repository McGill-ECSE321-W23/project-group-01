package ca.mcgill.ecse321.PLMS.dto;
import ca.mcgill.ecse321.PLMS.model.MonthlyCustomer;

public class MonthlyCustomerResponseDto {
    private String email;
    private String password;
    private String name;

    public MonthlyCustomerResponseDto(MonthlyCustomer monthlyCustomer) {
        this.email = monthlyCustomer.getEmail();
        this.password = monthlyCustomer.getPassword();
        this.name = monthlyCustomer.getName();
    }

    public MonthlyCustomerResponseDto() {}

    public String getEmail()
    { return email; }

    public String getPassword()
    { return password; }

    public String getName()
    { return name; }

}
