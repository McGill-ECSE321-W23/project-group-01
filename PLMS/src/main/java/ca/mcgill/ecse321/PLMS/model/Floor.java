package ca.mcgill.ecse321.PLMS.model;

import jakarta.persistence.*;

@Entity
/**
 * Class that is part of the domain model of the Parking Lot Management System (PLMS)
 * This class contains information related to each floor
 * 
 * A major part of this class is auto-generated by umple
 * This class is also JPA anotated for ORM
 */
public class Floor
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Floor Attributes
  @Id
  private int floorNumber; // each floor is uniquely identified by its floor number
  private int largeSpotCapacity;
  private int smallSpotCapacity;
  private int smallSpotCounter;
  private int largeSpotCounter;

  // association to parking lot
  @ManyToOne
  private ParkingLot parkingLot;

 
  /**
   * Constructor of the floor class
   * @param aFloorNumber - Floor number (also the ID of the floor)
   * @param aLargeSpotCapacity - how many large spots are on this floor
   * @param aSmallSpotCapacity - how small spots are on this floor
   */
  public Floor(int aFloorNumber, int aLargeSpotCapacity, int aSmallSpotCapacity, ParkingLot aParkingLot)
  {
    floorNumber = aFloorNumber;
    largeSpotCapacity = aLargeSpotCapacity;
    smallSpotCapacity = aSmallSpotCapacity;
    // initialize counters to 0 as no passes are associated with the floor upon creation
    if (!setParkingLot(aParkingLot))
    {
      throw new RuntimeException("Unable to create Floor due to null ParkingLot");
    }
    smallSpotCounter = 0;
    largeSpotCounter = 0;
  }

  //------------------------
  // INTERFACE CONSISTING OF GETTERS AND SETTERS
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

  public boolean setParkingLot(ParkingLot aNewParkingLot)
  {
    boolean wasSet = false;
    if (aNewParkingLot != null)
    {
      parkingLot = aNewParkingLot;
      wasSet = true;
    }
    return wasSet;
  }

  public ParkingLot getParkingLot(){
    return parkingLot;
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