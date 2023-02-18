package ca.mcgill.ecse321.PLMS.model;



import java.sql.Date;
import java.sql.Time;
import jakarta.persistence.*;

@Entity
public class ServiceAppointment
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ServiceAppointment Attributes
  private Date date;
  private Time startTime;
  private Time endTime;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  //ServiceAppointment Associations
  @ManyToOne
  private Employee employee;
  @ManyToOne
  private MonthlyCustomer customer;
  @ManyToOne
  private Service service;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ServiceAppointment(Date aDate, Time aStartTime, Time aEndTime, int aId, Service aService)
  {
    date = aDate;
    startTime = aStartTime;
    endTime = aEndTime;
    id = aId;
    if (!setService(aService))
    {
      throw new RuntimeException("Unable to create ServiceAppointment due to null Service");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setStartTime(Time aStartTime)
  {
    boolean wasSet = false;
    startTime = aStartTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setEndTime(Time aEndTime)
  {
    boolean wasSet = false;
    endTime = aEndTime;
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

  public Date getDate()
  {
    return date;
  }

  public Time getStartTime()
  {
    return startTime;
  }

  public Time getEndTime()
  {
    return endTime;
  }

  public int getId()
  {
    return id;
  }
  /* Code from template association_GetOne */
  public Employee getEmployee()
  {
    return employee;
  }

  public boolean hasEmployee()
  {
    boolean has = employee != null;
    return has;
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
  /* Code from template association_GetOne */
  public Service getService()
  {
    return service;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setEmployee(Employee aNewEmployee)
  {
    boolean wasSet = false;
    employee = aNewEmployee;
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setCustomer(MonthlyCustomer aNewCustomer)
  {
    boolean wasSet = false;
    customer = aNewCustomer;
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setService(Service aNewService)
  {
    boolean wasSet = false;
    if (aNewService != null)
    {
      service = aNewService;
      wasSet = true;
    }
    return wasSet;
  }



  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "startTime" + "=" + (getStartTime() != null ? !getStartTime().equals(this)  ? getStartTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endTime" + "=" + (getEndTime() != null ? !getEndTime().equals(this)  ? getEndTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "employee = "+(getEmployee()!=null?Integer.toHexString(System.identityHashCode(getEmployee())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "customer = "+(getCustomer()!=null?Integer.toHexString(System.identityHashCode(getCustomer())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "service = "+(getService()!=null?Integer.toHexString(System.identityHashCode(getService())):"null");
  }
}