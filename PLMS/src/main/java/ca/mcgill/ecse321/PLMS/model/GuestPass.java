package ca.mcgill.ecse321.PLMS.model;



import java.sql.Date;
import java.sql.Time;
import jakarta.persistence.*;

/**
 * Class that is part of the domain model of the Parking Lot Management System (PLMS)
 * This class contains information related to each guest pass
 * 
 * A major part of this class is auto-generated by umple
 * This class is also JPA anotated for ORM
 */
@Entity
public class GuestPass extends Pass
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GuestPass Attributes
  private Date date;
  private Time startTime;
  private Time endTime;


  //------------------------
  // INTERFACE CONSISTING OF GETTERS AND SETTERS
  //------------------------

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false; //first push
    date = aDate;
    wasSet = true; //
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


 /**
 * toString() helper method
 * Helpful for debugging
 */
  public String toString()
  {
    return super.toString() + "["+
            "isLarge" + ":" + getIsLarge()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "startTime" + "=" + (getStartTime() != null ? !getStartTime().equals(this)  ? getStartTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endTime" + "=" + (getEndTime() != null ? !getEndTime().equals(this)  ? getEndTime().toString().replaceAll("  ","    ") : "this" : "null");
  }
}