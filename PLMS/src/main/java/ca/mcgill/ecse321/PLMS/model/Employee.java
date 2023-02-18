package ca.mcgill.ecse321.PLMS.model;

import jakarta.persistence.*;

@Entity
public class Employee extends Account
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Employee Attributes
  private String jobDescription;
  private int hourlyWage;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Employee(String aEmail, String aPassword, String aName, int aId, String aJobDescription, int aHourlyWage)
  {
    super(aEmail, aPassword, aName, aId);
    jobDescription = aJobDescription;
    hourlyWage = aHourlyWage;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setJobDescription(String aJobDescription)
  {
    boolean wasSet = false;
    jobDescription = aJobDescription;
    wasSet = true;
    return wasSet;
  }

  public boolean setHourlyWage(int aHourlyWage)
  {
    boolean wasSet = false;
    hourlyWage = aHourlyWage;
    wasSet = true;
    return wasSet;
  }

  public String getJobDescription()
  {
    return jobDescription;
  }

  public int getHourlyWage()
  {
    return hourlyWage;
  }


  public String toString()
  {
    return super.toString() + "["+
            "jobDescription" + ":" + getJobDescription()+ "," +
            "hourlyWage" + ":" + getHourlyWage()+ "]";
  }
}