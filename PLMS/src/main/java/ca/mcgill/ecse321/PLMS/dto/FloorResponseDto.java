package ca.mcgill.ecse321.PLMS.dto;

import ca.mcgill.ecse321.PLMS.model.Floor;

public class FloorResponseDto {
  // attributes
  private int floorNumber;
  private boolean isMemberOnly;
  private int smallSpotCapacity;
  private int largeSpotCapacity;

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
    this.isMemberOnly = floor.getIsMemberOnly();
    this.smallSpotCapacity = floor.getSmallSpotCapacity();
    this.largeSpotCapacity = floor.getLargeSpotCapacity();
  }

  // getters

  public int getFloorNumber(){
    return this.floorNumber;
  }

  public boolean getMemberOnly(){
    return this.isMemberOnly;
  }

  public int getSmallSpotCapacity() {
      return smallSpotCapacity;
  }

  public int getLargeSpotCapacity(){
    return this.largeSpotCapacity;
  }


  // need to add setters to responses!

  public void setFloorNumber(int floorNumber){
    this.floorNumber = floorNumber;
  }

  public void setMemberOnly(boolean isMemberOnly) {
      this.isMemberOnly = isMemberOnly;
  }

  public void setSmallSpotCapacity(int smallSpotCapacity){
    this.smallSpotCapacity = smallSpotCapacity;
  }

  public void setLargeSpotCapacity(int largeSpotCapacity){
    this.largeSpotCapacity = largeSpotCapacity;
  }



}

