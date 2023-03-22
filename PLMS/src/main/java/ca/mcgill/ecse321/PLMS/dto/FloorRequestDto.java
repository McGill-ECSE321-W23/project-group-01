package ca.mcgill.ecse321.PLMS.dto;

import ca.mcgill.ecse321.PLMS.model.Floor;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class FloorRequestDto {
  @NotNull(message = "Cannot have an empty floor number.")
  @Min(value = 0, message = "The floor number must be a non negative number.")
  private Integer floorNumber;

  @NotNull(message = "Cannot have an empty number of small spots on a floor.")
  @Min(value = 0, message = "Cannot be a negative number of small parking spots on a floor.")
  private Integer smallSpotCapacity;

  @NotNull(message = "Cannot have an empty number of large spots on a floor.")
  @Min(value = 0, message = "Cannot be a negative number of large parking spots on a floor.")
  private Integer largeSpotCapacity;

  @NotNull(message = "Member only must be true or false.")
  private Boolean isMemberOnly;

  /**
   * Method to construct a floor object from a floor request dto object
   * @return floor object with all corresponding attributes
   */
  public Floor toModel(){
    Floor floor = new Floor();
    floor.setFloorNumber(this.floorNumber);
    floor.setSmallSpotCapacity(this.smallSpotCapacity);
    floor.setLargeSpotCapacity(this.largeSpotCapacity);
    // initialize counters to zero. These will get incremented with each pass that is registered to a floor
    floor.setSmallSpotCounter(0);
    floor.setLargeSpotCounter(0);
    return floor;
  }

  public void setFloorNumber(int floorNumber){
    this.floorNumber = floorNumber;
  }

  public void setSmallSpotCapacity(int smallSpotCapacity){
    this.smallSpotCapacity = smallSpotCapacity;
  }

  public void setLargeSpotCapacity(int largeSpotCapacity){
    this.largeSpotCapacity = largeSpotCapacity;
  }

  public void setIsMemberOnly(boolean isMemberOnly){
    this.isMemberOnly = isMemberOnly;
  }

  // NEED TO ADD GETTERS TO REQUEST DTOs!
  public Integer getFloorNumber(){
    return this.floorNumber;
  }

  public Integer getSmallSpotCapacity(){
    return this.smallSpotCapacity;
  }

  public Integer getLargeSpotCapacity(){
    return this.largeSpotCapacity;
  }

  public Boolean getIsMemberOnly(){
    return this.isMemberOnly;
  }

}