package ca.mcgill.ecse321.PLMS.model;

import jakarta.persistence.*;

@Entity
/**
 * Class that is part of the domain model of the Parking Lot Management System (PLMS)
 * This class represents a monthly customer's account
 * 
 * A major part of this class is auto-generated by umple
 * This class is also JPA anotated for ORM
 */
public class MonthlyCustomer extends Account
{

  //------------------------
  // MEMBER VARIABLES (all belong to the super class account)
  //------------------------

  
  
  /**
   * Constructor of monthly customer class invokes super class constructor
   * @param aEmail - email of the monthly customer (the ID for the database as well)
   * @param aPassword - password of the monthly customer
   * @param aName - name of the monthly customer
   */
  public MonthlyCustomer(String aEmail, String aPassword, String aName)
  {
    super(aEmail, aPassword, aName);
  }

}