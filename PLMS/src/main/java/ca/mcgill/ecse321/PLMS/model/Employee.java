package ca.mcgill.ecse321.PLMS.model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/



// line 11 "model.ump"
// line 120 "model.ump"
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

  public void delete()
  {
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "jobDescription" + ":" + getJobDescription()+ "," +
            "hourlyWage" + ":" + getHourlyWage()+ "]";
  }
}