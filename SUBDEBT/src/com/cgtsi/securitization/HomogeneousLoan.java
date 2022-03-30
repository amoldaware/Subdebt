/*************************************************************
   *
   * Name of the class: HomogeneousLoan.java
   * This class holds the homogenous loan details which would be displayed to the 
   * user based on the select criteria.
   * @author : Veldurai T
   * @version:  
   * @since: Sep 24, 2003
   **************************************************************/


package com.cgtsi.securitization;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * This class holds the homogenous loan details which would be displayed to the 
 * user based on the select criteria.
 */
public class HomogeneousLoan implements Serializable
{
   private String sector;
   
   /**
    * ArrayList of StateWise objects
    */
   private ArrayList states;
      
   /**
    * @roseuid 3A1647020068
    */
   public HomogeneousLoan() 
   {
    
   }
   
   /**
    * Access method for the sector property.
    * 
    * @return   the current value of the sector property
    */
   public String getSector() 
   {
      return sector;
   }
   
   /**
    * Sets the value of the sector property.
    * 
    * @param aSector the new value of the sector property
    */
   public void setSector(String aSector) 
   {
      sector = aSector;
   }
   
   /**
    * Access method for the states property.
    * 
    * @return   the current value of the states property
    */
   public ArrayList getStates() 
   {
      return states;
   }
   
   /**
    * Sets the value of the states property.
    * 
    * @param aStates the new value of the states property
    */
   public void setStates(ArrayList aStates) 
   {
      states = aStates;
   }
}
