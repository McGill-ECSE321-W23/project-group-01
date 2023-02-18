package ca.mcgill.ecse321.PLMS.model;

import jakarta.persistence.*;

@Entity
public class Floor
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Floor Attributes
  @Id
  private int floorNumber;
  private int largeSpotCapacity;
  private int smallSpotCapacity;
  private int smallSpotCounter;
  private int largeSpotCounter;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Floor(int aFloorNumber, int aLargeSpotCapacity, int aSmallSpotCapacity, int aSmallSpotCounter, int aLargeSpotCounter)
  {
    floorNumber = aFloorNumber;
    largeSpotCapacity = aLargeSpotCapacity;
    smallSpotCapacity = aSmallSpotCapacity;
    smallSpotCounter = aSmallSpotCounter;
    largeSpotCounter = aLargeSpotCounter;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setFloorNumber(int aFloorNumber)
  {
    boolean wasSet = false;
    floorNumber = aFloorNumber;
    wasSet = true;
    return wasSet;
  }

  public boolean setLargeSpotCapacity(int aLargeSpotCapacity)
  {
    boolean wasSet = false;
    largeSpotCapacity = aLargeSpotCapacity;
    wasSet = true;
    return wasSet;
  }

  public boolean setSmallSpotCapacity(int aSmallSpotCapacity)
  {
    boolean wasSet = false;
    smallSpotCapacity = aSmallSpotCapacity;
    wasSet = true;
    return wasSet;
  }

  public boolean setSmallSpotCounter(int aSmallSpotCounter)
  {
    boolean wasSet = false;
    smallSpotCounter = aSmallSpotCounter;
    wasSet = true;
    return wasSet;
  }

  public boolean setLargeSpotCounter(int aLargeSpotCounter)
  {
    boolean wasSet = false;
    largeSpotCounter = aLargeSpotCounter;
    wasSet = true;
    return wasSet;
  }

  /**
   * also the floor ID
   */
  public int getFloorNumber()
  {
    return floorNumber;
  }

  public int getLargeSpotCapacity()
  {
    return largeSpotCapacity;
  }

  public int getSmallSpotCapacity()
  {
    return smallSpotCapacity;
  }

  /**
   * employees/owner increment these counts to keep track of vacancy
   */
  public int getSmallSpotCounter()
  {
    return smallSpotCounter;
  }

  public int getLargeSpotCounter()
  {
    return largeSpotCounter;
  }



  public String toString()
  {
    return super.toString() + "["+
            "floorNumber" + ":" + getFloorNumber()+ "," +
            "largeSpotCapacity" + ":" + getLargeSpotCapacity()+ "," +
            "smallSpotCapacity" + ":" + getSmallSpotCapacity()+ "," +
            "smallSpotCounter" + ":" + getSmallSpotCounter()+ "," +
            "largeSpotCounter" + ":" + getLargeSpotCounter()+ "]";
  }
}