package ca.mcgill.ecse321.PLMS.model;

import jakarta.persistence.*;

/**
 * Abstract class that is part of the domain model of the Parking Lot Management System (PLMS)
 * This abstract class represents a pass/ticket that a customer can purchase
 * 
 * A major part of this class is auto-generated by umple
 * This class is also JPA anotated for ORM
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Pass
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Pass Attributes
  private double fee;
  private String spotNumber;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  private String licensePlate;
  private String confirmationCode;

  //Pass Associations
  @ManyToOne
  private Floor floor;

  /**
   * Constructor of the pass abstract class
   * @param aFee - fee of the pass
   * @param aSpotNumber - spot ID number
   * @param aLicensePlate - License plate associated to the pass
   * @param aConfirmationCode - Confirmation number related to payment procedure
   */
  public Pass(double aFee, String aSpotNumber, String aLicensePlate, String aConfirmationCode)
  {
    fee = aFee;
    spotNumber = aSpotNumber;
    licensePlate = aLicensePlate;
    confirmationCode = aConfirmationCode;
  }

  //------------------------
  // INTERFACE CONSISTING OF GETTERS AND SETTERS
  //------------------------

  public boolean setFee(double aFee)
  {
    boolean wasSet = false;
    fee = aFee;
    wasSet = true;
    return wasSet;
  }

  public boolean setConfirmationCode(String aConfirmationCode)
  {
    boolean wasSet = false;
    confirmationCode = aConfirmationCode;
    wasSet = true;
    return wasSet;
  }

  public boolean setSpotNumber(String aSpotNumber)
  {
    boolean wasSet = false;
    spotNumber = aSpotNumber;
    wasSet = true;
    return wasSet;
  }


  public boolean setLicensePlate(String aLicensePlate)
  {
    boolean wasSet = false;
    licensePlate = aLicensePlate;
    wasSet = true;
    return wasSet;
  }

  public double getFee()
  {
    return fee;
  }

  public String getSpotNumber()
  {
    return spotNumber;
  }

  public String getConfirmationCode(){
    return confirmationCode;
  }

  public int getId()
  {
    return id;
  }

  public String getLicensePlate()
  {
    return licensePlate;
  }
  /* Code from template association_GetOne */
  public Floor getFloor()
  {
    return floor;
  }

  public boolean hasFloor()
  {
    boolean has = floor != null;
    return has;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setFloor(Floor aNewFloor)
  {
    boolean wasSet = false;
    floor = aNewFloor;
    wasSet = true;
    return wasSet;
  }

 /**
 * toString() helper method
 * Helpful for debugging
 */
  public String toString()
  {
    return super.toString() + "["+
            "fee" + ":" + getFee()+ "," +
            "spotNumber" + ":" + getSpotNumber()+ "," +
            "id" + ":" + getId()+ "," +
            "licensePlate" + ":" + getLicensePlate()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "floor = "+(getFloor()!=null?Integer.toHexString(System.identityHashCode(getFloor())):"null");
  }
}