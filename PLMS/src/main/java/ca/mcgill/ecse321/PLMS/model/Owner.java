package ca.mcgill.ecse321.PLMS.model;

import jakarta.persistence.*;

@Entity
public class Owner extends Account
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Owner(String aEmail, String aPassword, String aName)
  {
    super(aEmail, aPassword, aName);
  }



}