package ca.mcgill.ecse321.PLMS.model;

import jakarta.persistence.*;

@Entity
public class MonthlyCustomer extends Account
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public MonthlyCustomer(String aEmail, String aPassword, String aName, int aId)
  {
    super(aEmail, aPassword, aName, aId);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {
    super.delete();
  }

}