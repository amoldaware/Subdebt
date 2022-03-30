//Source file: C:\\CHECKOUT\\28OCT2003\\com\\cgtsi\\securitization\\SelectCriteria.java

package com.cgtsi.securitization;

import java.io.Serializable;
import java.util.Date;


/**
 * This class holds the details related to the select criteria. The selec criteria 
 * values are encapsulated in this class.
 */
public class SelectCriteria implements Serializable
{
   private String[] mlis;
   private int tenure;
   
   /**
    * From this date, tenure is calculated and the homogenous loans are found out. 
    * This could be back or future date.
    */
   private Date effectiveDate;
   private String[] states;
   private String[] sectors;
   private double interestRate;
   
   /**
    * 0-Fixed
    * 1-Floating
    */
   private String typeOfInterest;
   private int trackRecord;
   private double loanSize;
   
   private double nextInterestRate;
   private double nextLoanSize;
   
   private String loanType;
   
   /**
    * @roseuid 3A1647010193
    */
   public SelectCriteria() 
   {
    
   }
   
   /**
    * Access method for the mlis property.
    * 
    * @return   the current value of the mlis property
    */
   public String[] getMlis() 
   {
      return mlis;
   }
   
   /**
    * Sets the value of the mlis property.
    * 
    * @param aMlis the new value of the mlis property
    */
   public void setMlis(String[] aMlis) 
   {
      mlis = aMlis;
   }
   
   /**
    * Access method for the tenure property.
    * 
    * @return   the current value of the tenure property
    */
   public int getTenure() 
   {
      return tenure;
   }
   
   /**
    * Sets the value of the tenure property.
    * 
    * @param aTenure the new value of the tenure property
    */
   public void setTenure(int aTenure) 
   {
      tenure = aTenure;
   }
   
   /**
    * Access method for the effectiveDate property.
    * 
    * @return   the current value of the effectiveDate property
    */
   public Date getEffectiveDate() 
   {
      return effectiveDate;
   }
   
   /**
    * Sets the value of the effectiveDate property.
    * 
    * @param aEffectiveDate the new value of the effectiveDate property
    */
   public void setEffectiveDate(Date aEffectiveDate) 
   {
      effectiveDate = aEffectiveDate;
   }
   
   /**
    * Access method for the state property.
    * 
    * @return   the current value of the state property
    */
   public String[] getStates() 
   {
      return states;
   }
   
   /**
    * Sets the value of the state property.
    * 
    * @param aState the new value of the state property
    */
   public void setStates(String[] aState) 
   {
      states = aState;
   }
   
   /**
    * Access method for the sector property.
    * 
    * @return   the current value of the sector property
    */
   public String[] getSectors() 
   {
      return sectors;
   }
   
   /**
    * Sets the value of the sector property.
    * 
    * @param aSector the new value of the sector property
    */
   public void setSectors(String[] aSector) 
   {
      sectors = aSector;
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
    * Access method for the typeOfInterest property.
    * 
    * @return   the current value of the typeOfInterest property
    */
   public String getTypeOfInterest() 
   {
      return typeOfInterest;
   }
   
   /**
    * Sets the value of the typeOfInterest property.
    * 
    * @param aTypeOfInterest the new value of the typeOfInterest property
    */
   public void setTypeOfInterest(String aTypeOfInterest) 
   {
      typeOfInterest = aTypeOfInterest;
   }
   
   /**
    * Access method for the trackRecord property.
    * 
    * @return   the current value of the trackRecord property
    */
   public int getTrackRecord() 
   {
      return trackRecord;
   }
   
   /**
    * Sets the value of the trackRecord property.
    * 
    * @param aTrackRecord the new value of the trackRecord property
    */
   public void setTrackRecord(int aTrackRecord) 
   {
      trackRecord = aTrackRecord;
   }
   
   /**
    * Access method for the loanSize property.
    * 
    * @return   the current value of the loanSize property
    */
   public double getLoanSize() 
   {
      return loanSize;
   }
   
   /**
    * Sets the value of the loanSize property.
    * 
    * @param aLoanSize the new value of the loanSize property
    */
   public void setLoanSize(double aLoanSize) 
   {
      loanSize = aLoanSize;
   }
   
   /**
    * Access method for the loanType property.
    * 
    * @return   the current value of the loanType property
    */
   public String getLoanType() 
   {
      return loanType;
   }
   
   /**
    * Sets the value of the loanType property.
    * 
    * @param aLoanType the new value of the loanType property
    */
   public void setLoanType(String aLoanType) 
   {
      loanType = aLoanType;
   }
/**
 * @return
 */
public double getNextInterestRate() {
	return nextInterestRate;
}

/**
 * @return
 */
public double getNextLoanSize() {
	return nextLoanSize;
}

/**
 * @param d
 */
public void setNextInterestRate(double d) {
	nextInterestRate = d;
}

/**
 * @param d
 */
public void setNextLoanSize(double d) {
	nextLoanSize = d;
}

}
