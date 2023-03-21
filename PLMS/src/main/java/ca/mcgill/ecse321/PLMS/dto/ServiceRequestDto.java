package ca.mcgill.ecse321.PLMS.dto;
import ca.mcgill.ecse321.PLMS.model.Service;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class ServiceRequestDto {
    @NotNull
    @NotBlank(message = "Service name cannot be blank.")
    private String serviceName;
    @Min(value = 0, message = "Cost cannot be a negative number.")
    private double cost;
    @Min(value = 0, message = "Length cannot be a negative number.")
    private double lengthInHours;

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setLengthInHours(double lengthInHours) {
        this.lengthInHours = lengthInHours;
    }

    public Service toModel(){
        return new Service(serviceName, cost, lengthInHours);
    }
}

