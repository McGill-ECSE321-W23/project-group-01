package ca.mcgill.ecse321.PLMS.model;

import jakarta.persistence.*;

@Entity
public class Service
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Service Attributes
  @Id
  private String serviceName;
  private double cost;
  private double lengthInHours;


  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Service(String aServiceName, double aCost, double aLengthInHours)
  {
    serviceName = aServiceName;
    cost = aCost;
    lengthInHours = aLengthInHours;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setServiceName(String aServiceName)
  {
    boolean wasSet = false;
    serviceName = aServiceName;
    wasSet = true;
    return wasSet;
  }

  public boolean setCost(double aCost)
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



  public String getServiceName()
  {
    return serviceName;
  }

  public double getCost()
  {
    return cost;
  }

  public double getLengthInHours()
  {
    return lengthInHours;
  }



  public String toString()
  {
    return super.toString() + "["+
            "description" + ":" + getServiceName()+ "," +
            "cost" + ":" + getCost()+ "," +
            "lengthInHours" + ":" + getLengthInHours()+ "," +"]";
  }
}