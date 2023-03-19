package ca.mcgill.ecse321.PLMS.model;



import java.sql.Time;
import jakarta.persistence.*;

/**
 * Class that is part of the domain model of the Parking Lot Management System (PLMS)
 * This class represents the parking lot
 * It contains metadata related to the whole parking lot
 * 
 * A major part of this class is auto-generated by umple
 * This class is also JPA anotated for ORM
 */
@Entity
public class ParkingLot
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ParkingLot Attributes
  private Time openingTime;
  private Time closingTime;
  private double largeSpotFee;
  private double smallSpotFee;
  private double smallSpotMonthlyFlatFee;
  private double largeSpotMonthlyFlatFee;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  @SuppressWarnings("unused")
  public ParkingLot(){}

  public ParkingLot(Time openingTime, Time closingTime, double largeSpotFee, double smallSpotFee, double smallSpotMonthlyFlatFee, double largeSpotMonthlyFlatFee){
    this.openingTime = openingTime;
    this.closingTime = closingTime;
    this.largeSpotFee = largeSpotFee;
    this.smallSpotFee = smallSpotFee;
    this.smallSpotMonthlyFlatFee = smallSpotMonthlyFlatFee;
    this.largeSpotMonthlyFlatFee = largeSpotMonthlyFlatFee;
  }

  //------------------------
  // INTERFACE CONSISTING OF GETTERS AND SETTERS
  //------------------------

  public boolean setOpeningTime(Time aOpeningTime)
  {
    boolean wasSet = false;
    openingTime = aOpeningTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setClosingTime(Time aClosingTime)
  {
    boolean wasSet = false;
    closingTime = aClosingTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setLargeSpotFee(double aLargeSpotFee)
  {
    boolean wasSet = false;
    largeSpotFee = aLargeSpotFee;
    wasSet = true;
    return wasSet;
  }

  public boolean setSmallSpotFee(double aSmallSpotFee)
  {
    boolean wasSet = false;
    smallSpotFee = aSmallSpotFee;
    wasSet = true;
    return wasSet;
  }

  public boolean setSmallSpotMonthlyFlatFee(double aMonthlyFlatFee)
  {
    boolean wasSet = false;
    smallSpotMonthlyFlatFee = aMonthlyFlatFee;
    wasSet = true;
    return wasSet;
  }

  public boolean setLargeSpotMonthlyFlatFee(double aMonthlyFlatFee)
  {
    boolean wasSet = false;
    largeSpotMonthlyFlatFee = aMonthlyFlatFee;
    wasSet = true;
    return wasSet;
  }

  public boolean setId(int aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public Time getOpeningTime()
  {
    return openingTime;
  }

  public Time getClosingTime()
  {
    return closingTime;
  }

  public double getLargeSpotFee()
  {
    return largeSpotFee;
  }

  public double getSmallSpotFee()
  {
    return smallSpotFee;
  }

  public double getSmallSpotMonthlyFlatFee()
  {
    return smallSpotMonthlyFlatFee;
  }

  public double getLargeSpotMonthlyFlatFee()
  {
    return largeSpotMonthlyFlatFee;
  }

  public int getId()
  {
    return id;
  }


 /**
 * toString() helper method
 * Helpful for debugging
 */
  public String toString()
  {
    return super.toString() + "["+
            "largeSpotFee" + ":" + getLargeSpotFee()+ "," +
            "smallSpotFee" + ":" + getSmallSpotFee()+ "," +
            "smallSpotMonthlyFlatFee" + ":" + getSmallSpotMonthlyFlatFee()+ "," + "largeSpotMonthlyFlatFee" + ":" + getLargeSpotMonthlyFlatFee()+ "," +
            "id" + ":" + getId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "openingTime" + "=" + (getOpeningTime() != null ? !getOpeningTime().equals(this)  ? getOpeningTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "closingTime" + "=" + (getClosingTime() != null ? !getClosingTime().equals(this)  ? getClosingTime().toString().replaceAll("  ","    ") : "this" : "null");
  }
}