package ca.mcgill.ecse321.PLMS.dto;

import ca.mcgill.ecse321.PLMS.model.Floor;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class FloorDto {
  @NotNull(message = "Cannot have an empty floor number.")
  @Min(value = 0, message = "The floor number must be a non negative number.")
  private int floorNumber;

  @NotNull(message = "Cannot have an empty number of small spots on a floor.")
  @Min(value = 0, message = "Cannot be a negative number of small parking spots on a floor.")
  private int smallSpotCapacity;

  @NotNull(message = "Cannot have an empty number of large spots on a floor.")
  @Min(value = 0, message = "Cannot be a negative number of large parking spots on a floor.")
  private int largeSpotCapacity;

  @NotNull(message = "Cannot have an empty number of small spots occupied.")
  @Min(value = 0, message = "Cannot be a negative number of small parking spots occupied on a floor.")
  private int smallSpotCounter;

  @NotNull(message = "Cannot have an empty number of large spots occupied.")
  @Min(value = 0, message = "Cannot be a negative number of large parking spots occupied on a floor.")
  private int largeSpotCounter;

  /**
   * Constructor for creating a floor transfer object by using the fields of 
   * a floor object.
   * @param floor - floor to turn into a transfer object
   */
  public FloorDto(Floor floor){
    this.floorNumber = floor.getFloorNumber();
    this.smallSpotCapacity = floor.getSmallSpotCapacity();
    this.largeSpotCapacity = floor.getLargeSpotCapacity();
    this.smallSpotCounter = floor.getSmallSpotCounter();
    this.largeSpotCounter = floor.getLargeSpotCounter();
  }

  public Floor toModel(){
    Floor floor = new Floor();
    floor.setFloorNumber(this.floorNumber);
    floor.setSmallSpotCapacity(this.smallSpotCapacity);
    floor.setLargeSpotCapacity(this.largeSpotCapacity);
    floor.setSmallSpotCounter(this.smallSpotCounter);
    floor.setLargeSpotCounter(this.largeSpotCounter);
    return floor;
  }

  public int getFloorNumber(){
    return this.floorNumber;
  }

  public void setFloorNumber(int floorNumber){
    this.floorNumber = floorNumber;
  }

  public int getSmallCapacity(){
    return this.floorNumber;
  }

  public void setSmallSpotCapacity(int smallSpotCapacity){
    this.smallSpotCapacity = smallSpotCapacity;
  }

  public int getLargeSpotCapacity(){
    return this.floorNumber;
  }

  public void setLargeSpotCapacity(int largeSpotCapacity){
    this.largeSpotCapacity = largeSpotCapacity;
  }

  public int getSmallSpotCounter(){
    return this.floorNumber;
  }

  public void setSmallSpotCounter(int smallSpotCounter){
    this.smallSpotCounter = smallSpotCounter;
  }

  public int getLargeSpotCounter(){
    return this.floorNumber;
  }

  public void setLargeSpotCounter(int largeSpotCounter){
    this.largeSpotCounter = largeSpotCounter;
  }

}
