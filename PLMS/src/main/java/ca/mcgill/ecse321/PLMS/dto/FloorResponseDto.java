package ca.mcgill.ecse321.PLMS.dto;

import ca.mcgill.ecse321.PLMS.model.Floor;

public class FloorResponseDto {
  // attributes
  private int floorNumber;
  private int smallSpotCapacity;
  private int largeSpotCapacity;
  private int smallSpotCounter;
  private int largeSpotCounter;

  /**
   * Default constructor
   */
  public FloorResponseDto(){}
  
  /**
   * Constructor for creating a floor transfer object by using the fields of 
   * a floor object.
   * @param floor - floor to turn into a transfer object
   */
  public FloorResponseDto(Floor floor){
    this.floorNumber = floor.getFloorNumber();
    this.smallSpotCapacity = floor.getSmallSpotCapacity();
    this.largeSpotCapacity = floor.getLargeSpotCapacity();
    this.smallSpotCounter = floor.getSmallSpotCounter();
    this.largeSpotCounter = floor.getLargeSpotCounter();
  }

  // getters

  public int getFloorNumber(){
    return this.floorNumber;
  }

  public int getSmallCapacity(){
    return this.smallSpotCapacity;
  }

  public int getLargeSpotCapacity(){
    return this.largeSpotCapacity;
  }

  public int getSmallSpotCounter(){
    return this.smallSpotCounter;
  }

  public int getLargeSpotCounter(){
    return this.largeSpotCounter;
  }

  // need to add setters to responses!

  public void setFloorNumber(int floorNumber){
    this.floorNumber = floorNumber;
  }

  public void setSmallSpotCapacity(int smallSpotCapacity){
    this.smallSpotCapacity = smallSpotCapacity;
  }

  public void setLargeSpotCapacity(int largeSpotCapacity){
    this.largeSpotCapacity = largeSpotCapacity;
  }

  public void setSmallSpotCounter(int smallSpotCounter){
    this.smallSpotCounter = smallSpotCounter;
  }

  public void setLargeSpotCounter(int largeSpotCounter){
    this.largeSpotCounter = largeSpotCounter;
  }


}

