package ca.mcgill.ecse321.PLMS.dto;
import ca.mcgill.ecse321.PLMS.model.MonthlyCustomer;
import io.swagger.v3.oas.annotations.media.Schema;

public class MonthlyCustomerResponseDto {
    @Schema(example= "owner@email.com", description = "Email linked to the account of the owner")
    private String email;
    @Schema(example= "Password1!", description = "Password linked to the account of the owner")
    private String password;
    @Schema(example= "owner", description = "Name of the owner")
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
