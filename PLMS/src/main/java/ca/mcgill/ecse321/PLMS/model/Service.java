package ca.mcgill.ecse321.PLMS.model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/



// line 43 "model.ump"
// line 154 "model.ump"
public class Service
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Service Attributes
  private String description;
  private int cost;
  private double lengthInHours;
  private int id;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Service(String aDescription, int aCost, double aLengthInHours, int aId)
  {
    description = aDescription;
    cost = aCost;
    lengthInHours = aLengthInHours;
    id = aId;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDescription(String aDescription)
  {
    boolean wasSet = false;
    description = aDescription;
    wasSet = true;
    return wasSet;
  }

  public boolean setCost(int aCost)
  {
    boolean wasSet = false;
    cost = aCost;
    wasSet = true;
    return wasSet;
  }

  public boolean setLengthInHours(double aLengthInHours)
  {
    boolean wasSet = false;
    lengthInHours = aLengthInHours;
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

  public String getDescription()
  {
    return description;
  }

  public int getCost()
  {
    return cost;
  }

  public double getLengthInHours()
  {
    return lengthInHours;
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
            "description" + ":" + getDescription()+ "," +
            "cost" + ":" + getCost()+ "," +
            "lengthInHours" + ":" + getLengthInHours()+ "," +
            "id" + ":" + getId()+ "]";
  }
}