//Source file: C:\\CHECKOUT\\28OCT2003\\com\\cgtsi\\securitization\\LoanPool.java

package com.cgtsi.securitization;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;


/**
 * This class holds the details related to the loan pool.
 */
public class LoanPool implements Serializable
{
   private String loanPoolName;
   private String spvName;
   private String ratingAgencyName;
   private double amountSecuritized;
   private double interestRate;
   private Date securitizationIssueDate;
   private String rating;
   
   /**
    * ArrayList of Investor objects
    */
   private ArrayList investors;
   
   /**
    * This variable holds the applications(CGPANs) selected for the loan pool.
    */
   private ArrayList loanPoolApplications;
   public Investor theInvestor;
   public LoanPoolApplication theLoanPoolApplication;
   
   /**
    * @roseuid 3A1647010391
    */
   public LoanPool() 
   {
    
   }
   
   /**
    * Access method for the loanPoolName property.
    * 
    * @return   the current value of the loanPoolName property
    */
   public String getLoanPoolName() 
   {
      return loanPoolName;
   }
   
   /**
    * Sets the value of the loanPoolName property.
    * 
    * @param aLoanPoolName the new value of the loanPoolName property
    */
   public void setLoanPoolName(String aLoanPoolName) 
   {
      loanPoolName = aLoanPoolName;
   }
   
   /**
    * Access method for the spvName property.
    * 
    * @return   the current value of the spvName property
    */
   public String getSpvName() 
   {
      return spvName;
   }
   
   /**
    * Sets the value of the spvName property.
    * 
    * @param aSpvName the new value of the spvName property
    */
   public void setSpvName(String aSpvName) 
   {
      spvName = aSpvName;
   }
   
   /**
    * Access method for the ratingAgencyName property.
    * 
    * @return   the current value of the ratingAgencyName property
    */
   public String getRatingAgencyName() 
   {
      return ratingAgencyName;
   }
   
   /**
    * Sets the value of the ratingAgencyName property.
    * 
    * @param aRatingAgencyName the new value of the ratingAgencyName property
    */
   public void setRatingAgencyName(String aRatingAgencyName) 
   {
      ratingAgencyName = aRatingAgencyName;
   }
   
   /**
    * Access method for the amountSecuritized property.
    * 
    * @return   the current value of the amountSecuritized property
    */
   public double getAmountSecuritized() 
   {
      return amountSecuritized;
   }
   
   /**
    * Sets the value of the amountSecuritized property.
    * 
    * @param aAmountSecuritized the new value of the amountSecuritized property
    */
   public void setAmountSecuritized(double aAmountSecuritized) 
   {
      amountSecuritized = aAmountSecuritized;
   }
   
   /**
    * Access method for the interestRate property.
    * 
    * @return   the current value of the interestRate property
    */
   public double getInterestRate() 
   {
      return interestRate;
   }
   
   /**
    * Sets the value of the interestRate property.
    * 
    * @param aInterestRate the new value of the interestRate property
    */
   public void setInterestRate(double aInterestRate) 
   {
      interestRate = aInterestRate;
   }
   
   /**
    * Access method for the securitizationIssueDate property.
    * 
    * @return   the current value of the securitizationIssueDate property
    */
   public Date getSecuritizationIssueDate() 
   {
      return securitizationIssueDate;
   }
   
   /**
    * Sets the value of the securitizationIssueDate property.
    * 
    * @param aSecuritizationIssueDate the new value of the securitizationIssueDate property
    */
   public void setSecuritizationIssueDate(Date aSecuritizationIssueDate) 
   {
      securitizationIssueDate = aSecuritizationIssueDate;
   }
   
   /**
    * Access method for the rating property.
    * 
    * @return   the current value of the rating property
    */
   public String getRating() 
   {
      return rating;
   }
   
   /**
    * Sets the value of the rating property.
    * 
    * @param aRating the new value of the rating property
    */
   public void setRating(String aRating) 
   {
      rating = aRating;
   }
   
   /**
    * Access method for the investors property.
    * 
    * @return   the current value of the investors property
    */
   public ArrayList getInvestors() 
   {
      return investors;
   }
   
   /**
    * Sets the value of the investors property.
    * 
    * @param aInvestors the new value of the investors property
    */
   public void setInvestors(ArrayList aInvestors) 
   {
      investors = aInvestors;
   }
   
   /**
    * Access method for the loanPoolApplications property.
    * 
    * @return   the current value of the loanPoolApplications property
    */
   public ArrayList getLoanPoolApplications() 
   {
      return loanPoolApplications;
   }
   
   /**
    * Sets the value of the loanPoolApplications property.
    * 
    * @param aLoanPoolApplications the new value of the loanPoolApplications property
    */
   public void setLoanPoolApplications(ArrayList aLoanPoolApplications) 
   {
      loanPoolApplications = aLoanPoolApplications;
   }
}
