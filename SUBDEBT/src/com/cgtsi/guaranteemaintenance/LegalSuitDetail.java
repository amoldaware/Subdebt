//Source file: D:\\com\\cgtsi\\guaranteemaintenance\\LegalSuitDetail.java

package com.cgtsi.guaranteemaintenance; 

import java.util.Date;
import java.io.Serializable;

public class LegalSuitDetail implements Serializable 
{
   private String legalSuiteNo;
   private String courtName; 
   private String location;
   private Date dtOfFilingLegalSuit;
   private double amountClaimed;
   private String forumName;
   private String recoveryProceedingsConcluded ;
   private String currentStatus;
   private String npaId;
    
   /**
    * @roseuid 39B9CCDA0119
    */
   public LegalSuitDetail() 
   {
    
   }
   
   /**
    * Access method for the legalSuiteNo property.
    * 
    * @return   the current value of the legalSuiteNo property
    */
   public String getLegalSuiteNo() 
   {
      return legalSuiteNo;
   }
   
   /**
    * Sets the value of the legalSuiteNo property.
    * 
    * @param aLegalSuiteNo the new value of the legalSuiteNo property
    */
   public void setLegalSuiteNo(String aLegalSuiteNo) 
   {
      legalSuiteNo = aLegalSuiteNo;
   }
   
   /**
    * Access method for the courtName property.
    * 
    * @return   the current value of the courtName property
    */
   public String getCourtName() 
   {
      return courtName;
   }
   
   /**
    * Sets the value of the courtName property.
    * 
    * @param aCourtName the new value of the courtName property
    */
   public void setCourtName(String aCourtName) 
   {
      courtName = aCourtName;
   }
   
   /**
    * Access method for the location property.
    * 
    * @return   the current value of the location property
    */
   public String getLocation() 
   {
      return location;
   }
   
   /**
    * Sets the value of the location property.
    * 
    * @param aLocation the new value of the location property
    */
   public void setLocation(String aLocation) 
   {
      location = aLocation;
   }
   
   /**
    * Access method for the dtOfFilingLegalSuit property.
    * 
    * @return   the current value of the dtOfFilingLegalSuit property
    */
   public Date getDtOfFilingLegalSuit() 
   {
      return dtOfFilingLegalSuit;
   }
   
   /**
    * Sets the value of the dtOfFilingLegalSuit property.
    * 
    * @param aDtOfFilingLegalSuit the new value of the dtOfFilingLegalSuit property
    */
   public void setDtOfFilingLegalSuit(Date aDtOfFilingLegalSuit) 
   {
      dtOfFilingLegalSuit = aDtOfFilingLegalSuit;
   }
   
   
/**
 * @return
 */
public double getAmountClaimed() {
	return amountClaimed;
}

/**
 * @return
 */
public String getCurrentStatus() {
	return currentStatus;
}

/**
 * @return
 */
public String getForumName() {
	return forumName;
}

/**
 * @return
 */
public String getRecoveryProceedingsConcluded() {
	return recoveryProceedingsConcluded;
}

/**
 * @param d
 */
public void setAmountClaimed(double aAmountClaimed) {
	amountClaimed = aAmountClaimed;
}

/**
 * @param string
 */
public void setCurrentStatus(String aCurrentStatus) {
	currentStatus = aCurrentStatus;
}

/**
 * @param string
 */
public void setForumName(String aForumName) {
	forumName = aForumName;
}

/**
 * @param string
 */
public void setRecoveryProceedingsConcluded(String aRecoveryProceedingsConcluded) {
	recoveryProceedingsConcluded = aRecoveryProceedingsConcluded;
}

/**
 * @return
 */
public String getNpaId() {
	return npaId;
}

/**
 * @param string
 */
public void setNpaId(String string) {
	npaId = string;
}

}
