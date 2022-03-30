/*************************************************************
   *
   * Name of the class: MLIWise.java
   * This class holds the MLI wise homogenous loan details.
   *  
   * @author : Veldurai T
   * @version:  
   * @since: Sep 24, 2003
   **************************************************************/

package com.cgtsi.securitization;

import java.io.Serializable;


/**
 * This class holds the MLI wise homogenous loan details.
 */
public class MLIWise implements Serializable
{
   private String mliName;
   private int noOfLoans;
   private double totalSanctionedAmt;
   private double totalGC;
   
   /**
    * @roseuid 3A1647020130
    */
   public MLIWise() 
   {
    
   }
   
   /**
    * Access method for the mliName property.
    * 
    * @return   the current value of the mliName property
    */
   public String getMliName() 
   {
      return mliName;
   }
   
   /**
    * Sets the value of the mliName property.
    * 
    * @param aMliName the new value of the mliName property
    */
   public void setMliName(String aMliName) 
   {
      mliName = aMliName;
   }
   
   /**
    * Access method for the noOfLoans property.
    * 
    * @return   the current value of the noOfLoans property
    */
   public int getNoOfLoans() 
   {
      return noOfLoans;
   }
   
   /**
    * Sets the value of the noOfLoans property.
    * 
    * @param aNoOfLoans the new value of the noOfLoans property
    */
   public void setNoOfLoans(int aNoOfLoans) 
   {
      noOfLoans = aNoOfLoans;
   }
   
   /**
    * Access method for the totalSanctionedAmt property.
    * 
    * @return   the current value of the totalSanctionedAmt property
    */
   public double getTotalSanctionedAmt() 
   {
      return totalSanctionedAmt;
   }
   
   /**
    * Sets the value of the totalSanctionedAmt property.
    * 
    * @param aTotalSanctionedAmt the new value of the totalSanctionedAmt property
    */
   public void setTotalSanctionedAmt(double aTotalSanctionedAmt) 
   {
      totalSanctionedAmt = aTotalSanctionedAmt;
   }
   
   /**
    * Access method for the totalGC property.
    * 
    * @return   the current value of the totalGC property
    */
   public double getTotalGC() 
   {
      return totalGC;
   }
   
   /**
    * Sets the value of the totalGC property.
    * 
    * @param aTotalGC the new value of the totalGC property
    */
   public void setTotalGC(double aTotalGC) 
   {
      totalGC = aTotalGC;
   }
}
