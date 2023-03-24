package ca.mcgill.ecse321.PLMS.dto;
import ca.mcgill.ecse321.PLMS.model.Owner;

public class OwnerResponseDto {
    private String email;
    private String password;
    private String name;

    public OwnerResponseDto(Owner owner) {
        this.email = owner.getEmail();
        this.password = owner.getPassword();
        this.name = owner.getName();
    }

    public OwnerResponseDto() {}


    public String getEmail()
    { return email; }

    public String getPassword()
    { return password; }

    public String getName()
    { return name; }

}
