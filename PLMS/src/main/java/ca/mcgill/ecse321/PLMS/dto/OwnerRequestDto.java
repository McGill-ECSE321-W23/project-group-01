package ca.mcgill.ecse321.PLMS.dto;
import ca.mcgill.ecse321.PLMS.model.Owner;
import jakarta.validation.constraints.*;

public class OwnerRequestDto {

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


    public String getEmail()
    { return email; }

    public void setEmail(String aEmail)
    { this.email = aEmail; }

    public void setPassword(String aPassword)
    { this.password = aPassword; }

    public void setName(String aName)
    { this.name = aName; }

    public Owner toModel() {
        return new Owner(email, password, name);
    }

}
