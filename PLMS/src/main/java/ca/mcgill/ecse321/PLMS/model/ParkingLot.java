package ca.mcgill.ecse321.PLMS.model;



import java.sql.Time;


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
  private int id;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ParkingLot(Time aOpeningTime, Time aClosingTime, double aLargeSpotFee, double aSmallSpotFee, double aMonthlyFlatFee, int aId)
  {
    openingTime = aOpeningTime;
    closingTime = aClosingTime;
    largeSpotFee = aLargeSpotFee;
    smallSpotFee = aSmallSpotFee;
    monthlyFlatFee = aMonthlyFlatFee;
    id = aId;
  }

  //------------------------
  // INTERFACE
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

  public void delete()
  {}


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