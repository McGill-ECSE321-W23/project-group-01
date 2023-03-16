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
    return this.floorNumber;
  }

  public int getLargeSpotCapacity(){
    return this.floorNumber;
  }

  public int getSmallSpotCounter(){
    return this.floorNumber;
  }

  public int getLargeSpotCounter(){
    return this.floorNumber;
  }


}

