package ca.mcgill.ecse321.PLMS.model;



import java.sql.Date;
import java.sql.Time;
import jakarta.persistence.*;

@Entity
/**
 * Class that is part of the domain model of the Parking Lot Management System (PLMS)
 * This class contains information related to each guest pass
 * 
 * A major part of this class is auto-generated by umple
 * This class is also JPA anotated for ORM
 */
public class GuestPass extends Pass
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GuestPass Attributes
  private Date date;
  private Time startTime;
  private Time endTime;
  private boolean isLarge;

  /**
   * Constructor for the guest pass class
   * @param aFee - guest pass fee (based on lot fees specified by owner)
   * @param aSpotNumber - spot number associated with this pass
   * @param aLicensePlate - license plate of the car using the pass
   * @param aDate - date on which pass is created
   * @param aStartTime - start time of pass
   * @param aEndTime - end time of pass
   * @param aIsLarge - specifies whether the spot is large or small
   */
  public GuestPass(double aFee, String aSpotNumber, String aLicensePlate, Date aDate, Time aStartTime, Time aEndTime, boolean aIsLarge)
  {
    super(aFee, aSpotNumber, aLicensePlate);
    date = aDate;
    startTime = aStartTime;
    endTime = aEndTime;
    isLarge = aIsLarge;
  }

  //------------------------
  // INTERFACE CONSISTING OF GETTERS AND SETTERS
  //------------------------

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setStartTime(Time aStartTime)
  {
    boolean wasSet = false;
    startTime = aStartTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setEndTime(Time aEndTime)
  {
    boolean wasSet = false;
    endTime = aEndTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsLarge(boolean aIsLarge)
  {
    boolean wasSet = false;
    isLarge = aIsLarge;
    wasSet = true;
    return wasSet;
  }

  public Date getDate()
  {
    return date;
  }

  public Time getStartTime()
  {
    return startTime;
  }

  public Time getEndTime()
  {
    return endTime;
  }

  public boolean getIsLarge()
  {
    return isLarge;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isIsLarge()
  {
    return isLarge;
  }



  public String toString()
  {
    return super.toString() + "["+
            "isLarge" + ":" + getIsLarge()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "startTime" + "=" + (getStartTime() != null ? !getStartTime().equals(this)  ? getStartTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endTime" + "=" + (getEndTime() != null ? !getEndTime().equals(this)  ? getEndTime().toString().replaceAll("  ","    ") : "this" : "null");
  }
}