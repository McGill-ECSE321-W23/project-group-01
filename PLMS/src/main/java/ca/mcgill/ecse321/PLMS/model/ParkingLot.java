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
  private double monthlyFlatFee;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  /**
   * Constructor of the parking lot class
   * @param aOpeningTime - Opening hours all week of the parking lot
   * @param aClosingTime - Closing hour all week of the parking lot
   * @param aLargeSpotFee - Fee for large spots
   * @param aSmallSpotFee - Fee for small spots
   * @param aMonthlyFlatFee - Fee for monthly payment
   */
  public ParkingLot(Time aOpeningTime, Time aClosingTime, double aLargeSpotFee, double aSmallSpotFee, double aMonthlyFlatFee)
  {
    openingTime = aOpeningTime;
    closingTime = aClosingTime;
    largeSpotFee = aLargeSpotFee;
    smallSpotFee = aSmallSpotFee;
    monthlyFlatFee = aMonthlyFlatFee;
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

  public boolean setMonthlyFlatFee(double aMonthlyFlatFee)
  {
    boolean wasSet = false;
    monthlyFlatFee = aMonthlyFlatFee;
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

  public double getMonthlyFlatFee()
  {
    return monthlyFlatFee;
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
            "monthlyFlatFee" + ":" + getMonthlyFlatFee()+ "," +
            "id" + ":" + getId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "openingTime" + "=" + (getOpeningTime() != null ? !getOpeningTime().equals(this)  ? getOpeningTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "closingTime" + "=" + (getClosingTime() != null ? !getClosingTime().equals(this)  ? getClosingTime().toString().replaceAll("  ","    ") : "this" : "null");
  }
}