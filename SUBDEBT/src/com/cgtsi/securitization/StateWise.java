/*************************************************************
   *
   * Name of the class: StateWise.java
   * This class holds the state wise homogeneous loan details.
   *  
   * @author : Veldurai T
   * @version:  
   * @since: Sep 24, 2003
   **************************************************************/

package com.cgtsi.securitization;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * This class holds the state wise homogeneous loan details.
 */
public class StateWise implements Serializable
{
   private String state;
  
   //This ArrayList holds the mliwise loan details.  
   private ArrayList mliWise;
   
   /**
    * @roseuid 3A16470200D6
    */
   public StateWise() 
   {
    
   }
   
   /**
    * Access method for the state property.
    * 
    * @return   the current value of the state property
    */
   public String getState() 
   {
      return state;
   }
   
   /**
    * Sets the value of the state property.
    * 
    * @param aState the new value of the state property
    */
   public void setState(String aState) 
   {
      state = aState;
   }
 
/**
 * @return
 */
public ArrayList getMliWise() {
	return mliWise;
}

/**
 * @param list
 */
public void setMliWise(ArrayList list) {
	mliWise = list;
}

}
