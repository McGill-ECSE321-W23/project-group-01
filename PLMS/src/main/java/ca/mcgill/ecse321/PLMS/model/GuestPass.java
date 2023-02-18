package ca.mcgill.ecse321.PLMS.model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/


import java.sql.Date;
import java.sql.Time;

// line 65 "model.ump"
// line 173 "model.ump"
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

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public GuestPass(int aFee, String aSpotNumber, int aId, String aLicensePlate, Date aDate, Time aStartTime, Time aEndTime, boolean aIsLarge)
  {
    super(aFee, aSpotNumber, aId, aLicensePlate);
    date = aDate;
    startTime = aStartTime;
    endTime = aEndTime;
    isLarge = aIsLarge;
  }

  //------------------------
  // INTERFACE
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

  public void delete()
  {
    super.delete();
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