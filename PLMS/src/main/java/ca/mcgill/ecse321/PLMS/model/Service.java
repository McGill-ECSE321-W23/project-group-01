package ca.mcgill.ecse321.PLMS.model;

import jakarta.persistence.*;

/**
 * Class that is part of the domain model of the Parking Lot Management System (PLMS)
 * This class represents a service available in the parking lot
 * 
 * A major part of this class is auto-generated by umple
 * This class is also JPA anotated for ORM
 */
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

  public Service(){}

  public Service(String serviceName, double cost, double lengthInHours){
    this.serviceName = serviceName;
    this.cost = cost;
    this.lengthInHours = lengthInHours;
  }
  //------------------------
  // INTERFACE CONSISTING OF GETTERS AND SETTERS
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

 /**
 * toString() helper method
 * Helpful for debugging
 */
  public String toString()
  {
    return super.toString() + "["+
            "description" + ":" + getServiceName()+ "," +
            "cost" + ":" + getCost()+ "," +
            "lengthInHours" + ":" + getLengthInHours()+ "," +"]";
  }
}