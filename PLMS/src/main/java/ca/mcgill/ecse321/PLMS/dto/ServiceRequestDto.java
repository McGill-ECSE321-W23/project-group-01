package ca.mcgill.ecse321.PLMS.dto;
import ca.mcgill.ecse321.PLMS.model.Service;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ServiceRequestDto {
    @NotBlank(message = "Service name cannot be blank.")
    private String serviceName;
    @Min(value = 0, message = "Cost needs to be a number greater or equal to 0")
    private double cost;
    @Min(value = 0, message = "Length in hours needs to be a number greater or equal to 0.")
    private double lengthInHours;

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }

    public double getLengthInHours() {
        return lengthInHours;
    }
    
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

