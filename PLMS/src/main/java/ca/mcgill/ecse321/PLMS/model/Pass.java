package ca.mcgill.ecse321.PLMS.model;


public abstract class Pass
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Pass Attributes
  private int fee;
  private String spotNumber;
  private int id;
  private String licensePlate;

  //Pass Associations
  private Floor floor;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Pass(int aFee, String aSpotNumber, int aId, String aLicensePlate)
  {
    fee = aFee;
    spotNumber = aSpotNumber;
    id = aId;
    licensePlate = aLicensePlate;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setFee(int aFee)
  {
    boolean wasSet = false;
    fee = aFee;
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

  public boolean setId(int aId)
  {
    boolean wasSet = false;
    id = aId;
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

  public int getFee()
  {
    return fee;
  }

  public String getSpotNumber()
  {
    return spotNumber;
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

  public void delete()
  {
    floor = null;
  }


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