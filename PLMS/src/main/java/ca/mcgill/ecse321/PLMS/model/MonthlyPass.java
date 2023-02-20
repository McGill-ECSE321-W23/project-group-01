package ca.mcgill.ecse321.PLMS.model;


import java.sql.Date;
import jakarta.persistence.*;

@Entity
public class MonthlyPass extends Pass
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //MonthlyPass Attributes
  private Date startDate;
  private Date endDate;

  //MonthlyPass Associations
  @ManyToOne
  private MonthlyCustomer customer;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public MonthlyPass(double aFee, String aSpotNumber, String aLicensePlate, Date aStartDate, Date aEndDate, String aConfirmationCode)
  {
    super(aFee, aSpotNumber, aLicensePlate, aConfirmationCode);
    startDate = aStartDate;
    endDate = aEndDate;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setStartDate(Date aStartDate)
  {
    boolean wasSet = false;
    startDate = aStartDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setEndDate(Date aEndDate)
  {
    boolean wasSet = false;
    endDate = aEndDate;
    wasSet = true;
    return wasSet;
  }

  public Date getStartDate()
  {
    return startDate;
  }

  public Date getEndDate()
  {
    return endDate;
  }
  /* Code from template association_GetOne */
  public MonthlyCustomer getCustomer()
  {
    return customer;
  }

  public boolean hasCustomer()
  {
    boolean has = customer != null;
    return has;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setCustomer(MonthlyCustomer aNewCustomer)
  {
    boolean wasSet = false;
    customer = aNewCustomer;
    wasSet = true;
    return wasSet;
  }




  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "startDate" + "=" + (getStartDate() != null ? !getStartDate().equals(this)  ? getStartDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endDate" + "=" + (getEndDate() != null ? !getEndDate().equals(this)  ? getEndDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "customer = "+(getCustomer()!=null?Integer.toHexString(System.identityHashCode(getCustomer())):"null");
  }
}