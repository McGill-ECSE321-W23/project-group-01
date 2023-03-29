package ca.mcgill.ecse321.PLMS.dto;

import ca.mcgill.ecse321.PLMS.model.Service;

public class ServiceResponseDto {
    private String serviceName;
    private double cost;
    private double lengthInHours;

    public ServiceResponseDto(Service service) {
        this.serviceName = service.getServiceName();
        this.cost = service.getCost();
        this.lengthInHours = service.getLengthInHours();
    }

    public ServiceResponseDto(){}
    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }

    public double getLengthInHours() {
        return lengthInHours;
    }

    public void setServiceName(String serviceName){
        this.serviceName = serviceName;
    }

    public void setCost(double cost){
        this.cost = cost;
    }

    public void setLengthInHours(double lengthInHours){
        this.lengthInHours = lengthInHours;
    }
}
