package ca.mcgill.ecse321.PLMS.model;

import jakarta.persistence.*;

/**
 * Class that is part of the domain model of the Parking Lot Management System (PLMS)
 * This class contains information related to each floor
 * 
 * A major part of this class is auto-generated by umple
 * This class is also JPA anotated for ORM
 */
@Entity
public class Floor
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Floor Attributes
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  private int floorNumber; // each floor is uniquely identified by its floor number
  private int largeSpotCapacity;
  private int smallSpotCapacity;
  private int smallSpotCounter;
  private int largeSpotCounter;

  // association to parking lot
  @ManyToOne
  private ParkingLot parkingLot;

 


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

  public int getId() { return id; }

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

 /**
 * toString() helper method
 * Helpful for debugging
 */
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