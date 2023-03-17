package ca.mcgill.ecse321.PLMS.dto;
import ca.mcgill.ecse321.PLMS.model.MonthlyCustomer;

public class MonthlyCustomerRequestDto {

    private String email;
    private String password;
    private String name;

    public void setEmail(String aEmail)
    { this.email = aEmail; }

    public void setPassword(String aPassword)
    { this.password = aPassword; }

    public void setName(String aName)
    { this.name = aName; }

    public MonthlyCustomer toModel() {
        return new MonthlyCustomer(email, password, name);
    }


}
