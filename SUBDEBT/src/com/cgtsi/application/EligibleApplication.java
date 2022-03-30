//Source file: C:\\CGTSI\\Vss\\com\\cgtsi\\application\\EligibleApplication.java

package com.cgtsi.application;

/**
 * This class is used to show the eligible and ineligble applications on the 
 * screen.
 */
public class EligibleApplication 
{
   private String submissiondate;
   private String appRefNo;
   private String passedCondition;
   private String failedCondition;
   private String message;
   private double eligibleApprovedAmount;
   private double eligibleCreditAmount;   
   
   private double eligibleCreditGuaranteed;
   /*private boolean thirdPartyGuarantee;
   private boolean collateralTaken;
   private boolean limitExceeded;
   private boolean minSantionedAmt;
   private boolean maxInterestApplied;*/
   private int borrowerRefNo;
   private String cgpan;
   private String status;
   private String eligibleRemarks;
   
   /**
    * @roseuid 39B875CA0393
    */
   public EligibleApplication() 
   {
    
   }
   
   /**
    * Access method for the date property.
    * 
    * @return   the current value of the date property
    */
   public String getSubmissiondate() 
   {
      return submissiondate;
   }
   
   /**
    * Sets the value of the date property.
    * 
    * @param aDate the new value of the date property
    */
   public void setSubmissiondate(String aSubmissiondate) 
   {
	submissiondate = aSubmissiondate;
   }
   
   /**
    * Access method for the appRefNo property.
    * 
    * @return   the current value of the appRefNo property
    */
   public String getAppRefNo() 
   {
      return appRefNo;
   }
   
   /**
    * Sets the value of the appRefNo property.
    * 
    * @param aAppRefNo the new value of the appRefNo property
    */
   public void setAppRefNo(String aAppRefNo) 
   {
      appRefNo = aAppRefNo;
   }
   
   /**
    * Access method for the thridPartyGuaranee property.
    * 
    * @return   the current value of the thridPartyGuaranee property
    *
   public boolean getThirdPartyGuarantee() 
   {
      return thirdPartyGuarantee;
   }
   
   /**
    * Sets the value of the thridPartyGuaranee property.
    * 
    * @param aThridPartyGuaranee the new value of the thridPartyGuaranee property
    *
   public void setThirdPartyGuarantee(boolean aThirdPartyGuarantee) 
   {
		thirdPartyGuarantee = aThirdPartyGuarantee;
   }
   
   /**
    * Access method for the collateralTaken property.
    * 
    * @return   the current value of the collateralTaken property
    *
   public boolean getCollateralTaken() 
   {
      return collateralTaken;
   }
   
   /**
    * Sets the value of the collateralTaken property.
    * 
    * @param aCollateralTaken the new value of the collateralTaken property
    *
   public void setCollateralTaken(boolean aCollateralTaken) 
   {
      collateralTaken = aCollateralTaken;
   }
   
   /**
    * Access method for the limitExceeded property.
    * 
    * @return   the current value of the limitExceeded property
    *
   public boolean getLimitExceeded() 
   {
      return limitExceeded;
   }
   
   /**
    * Sets the value of the limitExceeded property.
    * 
    * @param aLimitExceeded the new value of the limitExceeded property
    *
   public void setLimitExceeded(boolean aLimitExceeded) 
   {
      limitExceeded = aLimitExceeded;
   }
   
   /**
    * Access method for the minSantionedAmt property.
    * 
    * @return   the current value of the minSantionedAmt property
    *
   public boolean getMinSantionedAmt() 
   {
      return minSantionedAmt;
   }
   
   /**
    * Sets the value of the minSantionedAmt property.
    * 
    * @param aMinSantionedAmt the new value of the minSantionedAmt property
    *
   public void setMinSantionedAmt(boolean aMinSantionedAmt) 
   {
      minSantionedAmt = aMinSantionedAmt;
   }
   
   /**
    * Access method for the maxInterestApplied property.
    * 
    * @return   the current value of the maxInterestApplied property
    *
   public boolean getMaxInterestApplied() 
   {
      return maxInterestApplied;
   }
   
   /**
    * Sets the value of the maxInterestApplied property.
    * 
    * @param aMaxInterestApplied the new value of the maxInterestApplied property
    *
   public void setMaxInterestApplied(boolean aMaxInterestApplied) 
   {
      maxInterestApplied = aMaxInterestApplied;
   }
   
   /**
    * Access method for the borrowerRefNo property.
    * 
    * @return   the current value of the borrowerRefNo property
    */
   public int getBorrowerRefNo() 
   {
      return borrowerRefNo;
   }
   
   /**
    * Sets the value of the borrowerRefNo property.
    * 
    * @param aBorrowerRefNo the new value of the borrowerRefNo property
    */
   public void setBorrowerRefNo(int aBorrowerRefNo) 
   {
      borrowerRefNo = aBorrowerRefNo;
   }
/**
 * @return
 */
public String getFailedCondition() {
	return failedCondition;
}

/**
 * @return
 */
public String getMessage() {
	return message;
}

/**
 * @return
 */
public String getPassedCondition() {
	return passedCondition;
}

/**
 * @param strings
 */
public void setFailedCondition(String aFailedCondition) {
	failedCondition = aFailedCondition;
}

/**
 * @param strings
 */
public void setMessage(String aMessage) {
	message = aMessage;
}

/**
 * @param strings
 */
public void setPassedCondition(String aPassedCondition) {
	passedCondition = aPassedCondition;
}

/**
 * @return
 */
public double getEligibleApprovedAmount() {
	return eligibleApprovedAmount;
}

/**
 * @param d
 */
public void setEligibleApprovedAmount(double aEligibleApprovedAmount) {
	eligibleApprovedAmount = aEligibleApprovedAmount;
}

/**
 * @return
 */
public double getEligibleCreditAmount() {
	return eligibleCreditAmount;
}

/**
 * @param d
 */
public void setEligibleCreditAmount(double aEligibleCreditAmount) {
	eligibleCreditAmount = aEligibleCreditAmount;
}

/**
 * @return
 */
public String getCgpan() {
	return cgpan;
}

/**
 * @param string
 */
public void setCgpan(String string) {
	cgpan = string;
}

/**
 * @return
 */
public String getStatus() {
	return status;
}

/**
 * @param string
 */
public void setStatus(String string) {
	status = string;
}

/**
 * @return
 */
public double getEligibleCreditGuaranteed() {
	return eligibleCreditGuaranteed;
}

/**
 * @param d
 */
public void setEligibleCreditGuaranteed(double d) {
	eligibleCreditGuaranteed = d;
}

/**
 * @return
 */
public String getEligibleRemarks() {
	return eligibleRemarks;
}

/**
 * @param string
 */
public void setEligibleRemarks(String string) {
	eligibleRemarks = string;
}

}
