package ca.mcgill.ecse321.PLMS.model;

import jakarta.persistence.*;

/**
 * Abstract class that is part of the domain model of the Parking Lot Management System (PLMS)
 * This abstract class contains information common to all the account types for all types of users
 * 
 * A major part of this class is auto-generated by umple
 * This class is also JPA anotated for ORM
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Account
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Account Attributes
  @Id
  private String email; // each account will be identified by a unique email address
  private String password;
  private String name;

  public Account() {}
  public Account(String email, String password, String name)
  {
    this.email = email;
    this.password = password;
    this.name = name;
  }
  //------------------------
  // INTERFACE CONSISTING OF GETTERS AND SETTERS
  //------------------------

  public boolean setEmail(String aEmail)
  {
    boolean wasSet = false;
    email = aEmail;
    wasSet = true;
    return wasSet;
  }

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public String getEmail()
  {
    return email;
  }

  public String getPassword()
  {
    return password;
  }

  public String getName()
  {
    return name;
  }

/**
 * toString() helper method
 * Helpful for debugging
 */
  public String toString()
  {
    return super.toString() + "["+
            "email" + ":" + getEmail()+ "," +
            "password" + ":" + getPassword()+ "," +
            "name" + ":" + getName()+ "]";
  }
}