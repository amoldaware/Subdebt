//Source file: C:\\CGTSI\\Vss\\com\\cgtsi\\application\\EligibilityCriteria.java

package com.cgtsi.application;
import java.util.Date;


/**
 * Eligibility criteria details are held in this class
 */
public class EligibilityCriteria 
{
   
   /**
    * Last date for filing the application.It could be within the next calendar 
    * quarter of santioned date.
    */
   private Date lastDateForFiling;
   private double interestRate = 0;
   private boolean isCollateralAllowed = false;
   private int[] sectorsAllowed = {0,1};
   private double minAmount = 0;
   private Date expiryDate = null;
   private SubScheme scheme = null;
   
   /**
    * @roseuid 39B87D910161
    */
   public EligibilityCriteria() 
   {
    
   }
   
   /**
    * Access method for the lastDateForFiling property.
    * 
    * @return   the current value of the lastDateForFiling property
    */
   public Date getLastDateForFiling() 
   {
      return lastDateForFiling;
   }
   
   /**
    * Sets the value of the lastDateForFiling property.
    * 
    * @param aLastDateForFiling the new value of the lastDateForFiling property
    */
   public void setLastDateForFiling(Date aLastDateForFiling) 
   {
      lastDateForFiling = aLastDateForFiling;
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
    * Access method for the isCollateralAllowed property.
    * 
    * @return   the current value of the isCollateralAllowed property
    */
   public boolean getIsCollateralAllowed() 
   {
      return isCollateralAllowed;
   }
   
   /**
    * Sets the value of the isCollateralAllowed property.
    * 
    * @param aIsCollateralAllowed the new value of the isCollateralAllowed property
    */
   public void setIsCollateralAllowed(boolean aIsCollateralAllowed) 
   {
      isCollateralAllowed = aIsCollateralAllowed;
   }
   
   /**
    * Access method for the sectorsAllowed property.
    * 
    * @return   the current value of the sectorsAllowed property
    */
   public int[] getSectorsAllowed() 
   {
      return sectorsAllowed;
   }
   
   /**
    * Sets the value of the sectorsAllowed property.
    * 
    * @param aSectorsAllowed the new value of the sectorsAllowed property
    */
   public void setSectorsAllowed(int[] aSectorsAllowed) 
   {
      sectorsAllowed = aSectorsAllowed;
   }
   
   /**
    * Access method for the minAmount property.
    * 
    * @return   the current value of the minAmount property
    */
   public double getMinAmount() 
   {
      return minAmount;
   }
   
   /**
    * Sets the value of the minAmount property.
    * 
    * @param aMinAmount the new value of the minAmount property
    */
   public void setMinAmount(double aMinAmount) 
   {
      minAmount = aMinAmount;
   }
   
   /**
    * Access method for the expiryDate property.
    * 
    * @return   the current value of the expiryDate property
    */
   public Date getExpiryDate() 
   {
      return expiryDate;
   }
   
   /**
    * Sets the value of the expiryDate property.
    * 
    * @param aExpiryDate the new value of the expiryDate property
    */
   public void setExpiryDate(Date aExpiryDate) 
   {
      expiryDate = aExpiryDate;
   }
   
   /**
    * Access method for the scheme property.
    * 
    * @return   the current value of the scheme property
    */
   public SubScheme getScheme() 
   {
      return scheme;
   }
   
   /**
    * Sets the value of the scheme property.
    * 
    * @param aScheme the new value of the scheme property
    */
   public void setScheme(SubScheme aScheme) 
   {
      scheme = aScheme;
   }
   
   /**
    * This method checks whether the supplied application is eligible or not.
    * @param application
    * @return boolean
    * @roseuid 39825E0702BF
    */
   public boolean isEligible(Application application) 
   {
    return false;
   }
}
