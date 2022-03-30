//Source file: C:\\CHECKOUT\\28OCT2003\\com\\cgtsi\\securitization\\Investor.java

package com.cgtsi.securitization;

import java.io.Serializable;


public class Investor implements Serializable
{
   private String investorName;
   private double investedAmount;
   private String loanPoolName;
   
   
   /**
    * @roseuid 3A16470201BC
    */
   public Investor() 
   {
    
   }
   
   /**
    * Access method for the investorName property.
    * 
    * @return   the current value of the investorName property
    */
   public String getInvestorName() 
   {
      return investorName;
   }
   
   /**
    * Sets the value of the investorName property.
    * 
    * @param aInvestorName the new value of the investorName property
    */
   public void setInvestorName(String aInvestorName) 
   {
      investorName = aInvestorName;
   }

/**
 * @return
 */
public double getInvestedAmount() {
	return investedAmount;
}

/**
 * @param double1
 */
public void setInvestedAmount(double double1) {
	investedAmount = double1;
}

/**
 * @return
 */
public String getLoanPoolName() {
	return loanPoolName;
}

/**
 * @param string
 */
public void setLoanPoolName(String string) {
	loanPoolName = string;
}

}
