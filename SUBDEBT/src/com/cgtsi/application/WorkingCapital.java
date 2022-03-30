//Source file: C:\\CGTSI\\Vss\\com\\cgtsi\\application\\WorkingCapital.java

package com.cgtsi.application;
import java.util.Date;
import java.io.Serializable;


/**
 * Has details about the Working capital.
 */
public class WorkingCapital implements Serializable
{
	private double fundBasedLimitSanctioned;
	private double limitFundBasedInterest;
	private Date limitFundBasedSanctionedDate = null;
	
	private double nonFundBasedLimitSantioned;	
	private double limitNonFundBasedCommission;
	private Date limitNonFundBasedSanctionedDate = null;
	
	private double existingTotal;
	
	private double creditFundBased;
	private double creditNonFundBased;
	
	private double wcPlr;
	
	private double osFundBasedPpl;
	private double osFundBasedInterestAmt;
	private Date osFundBasedAsOnDate;
	
	private double osNonFundBasedPpl;
	private double osNonFundBasedCommissionAmt;
	private Date osNonFundBasedAsOnDate;
	
	private double enhancedFundBased;
	private double enhancedNonFundBased;
	private double enhancedFBInterest;
	private double enhancedNFBComission;
	private double enhancedTotal;
	private Date enhancementDate=null;
	
	private double renewalFundBased;
	private double renewalNonFundBased;
	private double renewalFBInterest;
	private double renewalNFBComission;
	private double renewalTotal;
	private Date renewalDate=null;

	private String wcTypeOfPLR;
	private double wcInterestRate;
	private String wcInterestType;
	
	private int wcTenure;
	private String isTLMarginMoney;
	
	private double wcDisbAmt;
	private Date wcFirstDisbDt;
	private Date wcFinalDisbDt;
	
	
  /* private String oldCGPAN = null;
   private String cgpan = null;
   private double assessed = 0;
   private double limitSanctioned = 0;
   private double promoterContribution = 0;
   private double subsidy = 0;
   private double others = 0;
   private double osAmount = 0;
   private double osAmountAsOnDate = 0;
     private double interest = 0;*/
   
   
	
   public String getIsTLMarginMoney() {
		return isTLMarginMoney;
	}

	public double getWcDisbAmt() {
	return wcDisbAmt;
}

public void setWcDisbAmt(double wcDisbAmt) {
	this.wcDisbAmt = wcDisbAmt;
}

public Date getWcFirstDisbDt() {
	return wcFirstDisbDt;
}

public void setWcFirstDisbDt(Date wcFirstDisbDt) {
	this.wcFirstDisbDt = wcFirstDisbDt;
}

public Date getWcFinalDisbDt() {
	return wcFinalDisbDt;
}

public void setWcFinalDisbDt(Date wcFinalDisbDt) {
	this.wcFinalDisbDt = wcFinalDisbDt;
}

	public void setIsTLMarginMoney(String isTLMarginMoney) {
		this.isTLMarginMoney = isTLMarginMoney;
	}

/**
    * @roseuid 39B875C90002
    */
   public WorkingCapital() 
   {
    
   }  
  
   /**
    * Access method for the FundBasedLimitSanctioned property.
    * 
    * @return   the current value of the FundBasedLimitSanctioned property
    */
   public double getFundBasedLimitSanctioned() 
   {
      return fundBasedLimitSanctioned;
   }
   
   /**
    * Sets the value of the FundBasedLimitSanctioned property.
    * 
    * @param aLimitSanctioned the new value of the FundBasedLimitSanctioned property
    */
   public void setFundBasedLimitSanctioned(double aFundBasedLimitSanctioned) 
   {
		fundBasedLimitSanctioned = aFundBasedLimitSanctioned;
   }
   
   /**
   * Access method for the FundBasedLimitSanctioned property.
   * 
   * @return   the current value of the FundBasedLimitSanctioned property
   */
	  public double getLimitFundBasedInterest() 
	  {
		 return limitFundBasedInterest;
	  }
   
	  /**
   * Sets the value of the LimitFundBasedInterest property.
   * 
   * @param aLimitSanctioned the new value of the LimitFundBasedInterest property
   */
  public void setLimitFundBasedInterest(double aLimitFundBasedInterest) 
  {
	   limitFundBasedInterest = aLimitFundBasedInterest;
  }
  
  /**
	 * Access method for the limitFundbasedSanctionedDate property.
	 * 
	 * @return   the current value of the limitFundbasedSanctionedDate property
	 */
	public Date getLimitFundBasedSanctionedDate() 
	{
	   return limitFundBasedSanctionedDate;
	}
   
	/**
	 * Sets the value of the limitFundbasedSanctionedDate property.
	 * 
	 * @param aLimitSanctioned the new value of the limitFundbasedSanctionedDate property
	 */
	public void setLimitFundBasedSanctionedDate(Date aLimitFundBasedSanctionedDate) 
	{
		 limitFundBasedSanctionedDate = aLimitFundBasedSanctionedDate;
	}

   
	/**
	* Access method for the nonFundBasedLimitSanctioned property.
	* 
	* @return   the current value of the nonFundBasedLimitSanctioned property
	*/
	public double getNonFundBasedLimitSanctioned() 
	{
	return nonFundBasedLimitSantioned;
	}
   
	 /**
 	 * Sets the value of the nonFundBasedLimitSanctioned property.
 	 * 
 	 * @param aFundBasedLimitSanctioned the new value of the nonFundBasedLimitSanctioned property
 	 */
 	public void setNonFundBasedLimitSanctioned(double aNonFundBasedLimitSanctioned) 
 	{
	  nonFundBasedLimitSantioned = aNonFundBasedLimitSanctioned;
 	}
	 
	/**
	  * Access method for the limitNonFundBasedCommission property.
	  * 
	  * @return   the current value of the limitNonFundBasedCommission property
	  */
		 public double getLimitNonFundBasedCommission() 
		 {
			return limitNonFundBasedCommission;
		 }
   
		 /**
	  * Sets the value of the limitNonFundBasedCommission property.
	  * 
	  * @param aLimitSanctioned the new value of the limitNonFundBasedCommission property
	  */
	 public void setLimitNonFundBasedCommission(double aLimitNonFundBasedCommission) 
	 {
		limitNonFundBasedCommission = aLimitNonFundBasedCommission;
	 }
  
	 /**
	* Access method for the limitFundbasedSanctionedDate property.
	* 
	* @return   the current value of the limitFundbasedSanctionedDate property
	*/
   public Date getLimitNonFundBasedSanctionedDate() 
   {
	  return limitNonFundBasedSanctionedDate;
   }
   
	   /**
	* Sets the value of the limitFundbasedSanctionedDate property.
	* 
	* @param aLimitSanctioned the new value of the limitFundbasedSanctionedDate property
	*/
   public void setLimitNonFundBasedSanctionedDate(Date aLimitNonFundBasedSanctionedDate) 
   {
		limitNonFundBasedSanctionedDate = aLimitNonFundBasedSanctionedDate;
   }
   
   /**
   * Access method for the creditFundBased property.
   * 
   * @return   the current value of the creditFundBased property
   */
  public double getCreditFundBased() 
  {
	 return creditFundBased;
  }
   
	  /**
   * Sets the value of the creditFundBased property.
   * 
   * @param aLimitSanctioned the new value of the creditFundBased property
   */
  public void setCreditFundBased(double aCreditFundBased) 
  {
	   creditFundBased = aCreditFundBased;
  }
  
  /**
	* Access method for the creditNonFundBased property.
	* 
	* @return   the current value of the creditNonFundBased property
	*/
   public double getCreditNonFundBased() 
   {
	  return creditNonFundBased;
   }
   
	   /**
	* Sets the value of the creditNonFundBased property.
	* 
	* @param aLimitSanctioned the new value of the creditNonFundBased property
	*/
   public void setCreditNonFundBased(double aCreditNonFundBased) 
   {
		creditNonFundBased = aCreditNonFundBased;
   }
   
   /**
   * Access method for the plr property.
   * 
   * @return   the current value of the plr property
   */
	public double getWcPlr() 
	{
	   return wcPlr;
	}
   
 /**
   * Sets the value of the plr property.
   *
   * @param aPLR the new value of the plr property
   */
   public void setWcPlr(double aWcPlr) 
   {
	   wcPlr = aWcPlr;
   }
   
   /**
  	* Access method for the osFundBasedPpl property.
  	* 
  	* @return   the current value of the osFundBasedPpl property
  	*/
 	public double getOsFundBasedPpl() 
 	{
		return osFundBasedPpl;
 	}
   
	/**
	  * Sets the value of the osFundBasedPpl property.
	  * 
	  * @param aLimitSanctioned the new value of the osFundBasedPpl property
	  */
	 public void setOsFundBasedPpl(double aOsFundBasedPpl) 
	 {
		  osFundBasedPpl = aOsFundBasedPpl;
	 }
 
	/**
	* Access method for the osNonFundBasedPpl property.
	* 
	* @return   the current value of the osNonFundBasedPpl property
	*/
	public double getOsNonFundBasedPpl() 
	{
		return osNonFundBasedPpl;
	}
   
	 /**
  * Sets the value of the osNonFundBasedPpl property.
  * 
  * @param aLimitSanctioned the new value of the osNonFundBasedPpl property
  */
 public void setOsNonFundBasedPpl(double aOsNonFundBasedPpl) 
 {
	  osNonFundBasedPpl = aOsNonFundBasedPpl;
 }
 
 /**
 * Access method for the osFundBasedInterest property.
 * 
 * @return   the current value of the osFundBasedInterest property
 */
	 public double getOsFundBasedInterestAmt() 
	 {
		 return osFundBasedInterestAmt;
	 }
   
	 /**
   * Sets the value of the osFundBasedInterest property.
   * 
   * @param aLimitSanctioned the new value of the osFundBasedInterest property
   */
	  public void setOsFundBasedInterestAmt(double aOsFundBasedInterest) 
	  {
		   osFundBasedInterestAmt = aOsFundBasedInterest;
	  }
 
	 /**
 * Access method for the osNonFundBasedCommission property.
 * 
 * @return   the current value of the osNonFundBasedCommission property
 */
	 public double getOsNonFundBasedCommissionAmt() 
	 {
		 return osNonFundBasedCommissionAmt;
	 }
   
	  /**
   * Sets the value of the osNonFundBasedCommission property.
   * 
   * @param aOsNonFundBasedCommission the new value of the osNonFundBasedCommission property
   */
  public void setOsNonFundBasedCommissionAmt(double aOsNonFundBasedCommission) 
  {
	   osNonFundBasedCommissionAmt = aOsNonFundBasedCommission;
  }
   
   
  /**
   * Access method for the OsFundBasedAsOnDate property.
   * 
   * @return   the current value of the OsFundBasedAsOnDate property
   */
	   public Date getOsFundBasedAsOnDate() 
	   {
		   return osFundBasedAsOnDate;
	   }
   
	   /**
	 * Sets the value of the OsFundBasedAsOnDate property.
	 * 
	 * @param aLimitSanctioned the new value of the OsFundBasedAsOnDate property
	 */
	public void setOsFundBasedAsOnDate(Date aOsFundBasedAsOnDate) 
	{
		 osFundBasedAsOnDate = aOsFundBasedAsOnDate;
	}
 
	   /**
   * Access method for the OsNonFundBasedAsOnDate property.
   * 
   * @return   the current value of the OsNonFundBasedAsOnDate property
   */
	   public Date getOsNonFundBasedAsOnDate() 
	   {
		   return osNonFundBasedAsOnDate;
	   }
   
	   /**
	 * Sets the value of the OsNonFundBasedAsOnDate property.
	 * 
	 * @param osNonFundBasedAsOnDate the new value of the OsNonFundBasedAsOnDate property
	 */
	public void setOsNonFundBasedAsOnDate(Date aOsNonFundBasedAsOnDate) 
	{
		 osNonFundBasedAsOnDate = aOsNonFundBasedAsOnDate;
	}
   
	/**
   * Access method for the enhancedFBInterest property.
   * 
   * @return   the current value of the enhancedFBInterest property
   */
   public double getEnhancedFBInterest() {
	   return enhancedFBInterest;
   }
   /**
  * Access method for the enhancedFundBased property.
  * 
  * @return   the current value of the enhancedFundBased property
  */
   public double getEnhancedFundBased() {
	   return enhancedFundBased;
   }

	/**
   * Access method for the enhancedNFBComission property.
   * 
   * @return   the current value of the enhancedNFBComission property
   */
   public double getEnhancedNFBComission() {
	   return enhancedNFBComission;
   }

   /**
  * Access method for the enhancedNonFundBased property.
  * 
  * @return   the current value of the enhancedNonFundBased property
  */
   public double getEnhancedNonFundBased() {
	   return enhancedNonFundBased;
   }

   /**
  * Access method for the enhancedTotal property.
  * 
  * @return   the current value of the enhancedTotal property
  */
   public double getEnhancedTotal() {
	   return enhancedTotal;
   }

   /**
  * Access method for the enhancementDate property.
  * 
  * @return   the current value of the enhancementDate property
  */
   public Date getEnhancementDate() {
	   return enhancementDate;
   }

   /**
  * Access method for the existingTotal property.
  * 
  * @return   the current value of the existingTotal property
  */
   public double getExistingTotal() {
	   return existingTotal;
   }

   /**
  * Sets the value of the enhancedFBInterest property.
  *
  * @param aEnhancedFBInterest the new value of the enhancedFBInterest property
  */
   public void setEnhancedFBInterest(double aEnhancedFBInterest) {
	   enhancedFBInterest = aEnhancedFBInterest;
   }

   /**
  * Sets the value of the enhancedFundBased property.
  *
  * @param aEnhancedFundBased the new value of the enhancedFundBased property
  */
   public void setEnhancedFundBased(double aEnhancedFundBased) {
	   enhancedFundBased = aEnhancedFundBased;
   }

   /**
  * Sets the value of the enhancedNFBComission property.
  *
  * @param aEnhancedNFBComission the new value of the enhancedNFBComission property
  */
   public void setEnhancedNFBComission(double aEnhancedNFBComission) {
	   enhancedNFBComission = aEnhancedNFBComission;
   }

   /**
  * Sets the value of the enhancedNonFundBased property.
  *
  * @param aEnhancedNonFundBased the new value of the enhancedNonFundBased property
  */
   public void setEnhancedNonFundBased(double aEnhancedNonFundBased) {
	   enhancedNonFundBased = aEnhancedNonFundBased;
   }

   /**
  * Sets the value of the enhancedTotal property.
  *
  * @param aEnhancedTotal the new value of the enhancedTotal property
  */
   public void setEnhancedTotal(double aEnhancedTotal) {
	   enhancedTotal = aEnhancedTotal;
   }

   /**
  * Sets the value of the enhancementDate property.
  *
  * @param aEnhancementDate the new value of the enhancementDate property
  */
   public void setEnhancementDate(Date aEnhancementDate) {
	   enhancementDate = aEnhancementDate;
   }
   /**
  * Sets the value of the existingTotal property.
  *
  * @param aExistingTotal the new value of the existingTotal property
  */
   public void setExistingTotal(double aExistingTotal) {
	   existingTotal = aExistingTotal;
   }

   /**
   * Access method for the renewalDate property.
   * 
   * @return   the current value of the renewalDate property
   */
   public Date getRenewalDate() {
	   return renewalDate;
   }

   /**
   * Access method for the renewalFBInterest property.
   * 
   * @return   the current value of the renewalFBInterest property
   */
   public double getRenewalFBInterest() {
	   return renewalFBInterest;
   }

   /**
   * Access method for the renewalFundBased property.
   * 
   * @return   the current value of the renewalFundBased property
   */
   public double getRenewalFundBased() {
	   return renewalFundBased;
   }

   /**
   * Access method for the renewalNFBComission property.
   * 
   * @return   the current value of the renewalNFBComission property
   */
   public double getRenewalNFBComission() {
	   return renewalNFBComission;
   }

   /**
   * Access method for the renewalNonFundBased property.
   * 
   * @return   the current value of the renewalNonFundBased property
   */
   public double getRenewalNonFundBased() {
	   return renewalNonFundBased;
   }

   /**
   * Access method for the renewalTotal property.
   * 
   * @return   the current value of the renewalTotal property
   */
   public double getRenewalTotal() {
	   return renewalTotal;
   }

   /**
  * Sets the value of the renewalDate property.
  *
  * @param aRenewalDate the new value of the renewalDate property
  */
   public void setRenewalDate(Date aRenewalDate) {
	   renewalDate = aRenewalDate;
   }

   /**
  * Sets the value of the renewalFBInterest property.
  *
  * @param aRenewalFBInterest the new value of the renewalFBInterest property
  */
   public void setRenewalFBInterest(double aRenewalFBInterest) {
	   renewalFBInterest =aRenewalFBInterest;
   }

   /**
  * Sets the value of the renewalFundBased property.
  *
  * @param aRenewalFundBased the new value of the renewalFundBased property
  */
   public void setRenewalFundBased(double aRenewalFundBased) {
	   renewalFundBased = aRenewalFundBased;
   }

   /**
  * Sets the value of the renewalNFBComission property.
  *
  * @param aRenewalNFBCommission the new value of the renewalNFBComission property
	  */
   public void setRenewalNFBCommission(double aRenewalNFBCommission) {
	   renewalNFBComission = aRenewalNFBCommission;
   }

   /**
  * Sets the value of the renewalNonFundBased property.
  *
  * @param aRenewalNonFundBased the new value of the renewalNonFundBased property
  */
   public void setRenewalNonFundBased(double aRenewalNonFundBased) {
	   renewalNonFundBased = aRenewalNonFundBased;
   }

   /**
  * Sets the value of the renewalTotal property.
  *
  * @param aRenewalTotal the new value of the renewalTotal property
  */
   public void setRenewalTotal(double aRenewalTotal) {
	   renewalTotal = aRenewalTotal;
   }

 public double getWcInterestRate() 
   	{
    	return wcInterestRate;
   	}

 public void setWcInterestRate(double aWcInterestRate) 
   {
      wcInterestRate = aWcInterestRate;
   }

public String getWcTypeOfPLR() 
{
  return wcTypeOfPLR;
}

public void setWcTypeOfPLR(String aWcTypeOfPLR) 
{
  wcTypeOfPLR = aWcTypeOfPLR;
}

public String getWcInterestType() 
{
  return wcInterestType;
}

public void setWcInterestType(String aWcInterestType) 
{
  wcInterestType = aWcInterestType;
}

   

   
   /**
    * Access method for the promoterContribution property.
    * 
    * @return   the current value of the promoterContribution property
    
   public double getPromoterContribution() 
   {
      return promoterContribution;
   }
   
   /**
    * Sets the value of the promoterContribution property.
    * 
    * @param aPromoterContribution the new value of the promoterContribution property
    *   
     public void setPromoterContribution(double aPromoterContribution) 
   {
      promoterContribution = aPromoterContribution;
   }
   
   /**
    * Access method for the subsidy property.
    * 
    * @return   the current value of the subsidy property
    *
   public double getSubsidy() 
   {
      return subsidy;
   }
   
   /**
    * Sets the value of the subsidy property.
    * 
    * @param aSubsidy the new value of the subsidy property
    *
   public void setSubsidy(double aSubsidy) 
   {
      subsidy = aSubsidy;
   }
   
   /**
    * Access method for the others property.
    * 
    * @return   the current value of the others property
    *
   public double getOthers() 
   {
      return others;
   }
   
   /**
    * Sets the value of the others property.
    * 
    * @param aOthers the new value of the others property
    *
   public void setOthers(double aOthers) 
   {
      others = aOthers;
   }
   
   /**
    * Access method for the osAmount property.
    * 
    * @return   the current value of the osAmount property
    *
   public double getOsAmount() 
   {
      return osAmount;
   }
   
   /**
    * Sets the value of the osAmount property.
    * 
    * @param aOsAmount the new value of the osAmount property
    *
   public void setOsAmount(double aOsAmount) 
   {
      osAmount = aOsAmount;
   }
   
   /**
    * Access method for the osAmountAsOnDate property.
    * 
    * @return   the current value of the osAmountAsOnDate property
    *
   public double getOsAmountAsOnDate() 
   {
      return osAmountAsOnDate;
   }
   
   /**
    * Sets the value of the osAmountAsOnDate property.
    * 
    * @param aOsAmountAsOnDate the new value of the osAmountAsOnDate property
    *
   public void setOsAmountAsOnDate(double aOsAmountAsOnDate) 
   {
      osAmountAsOnDate = aOsAmountAsOnDate;
   }
   
  
  
   
   /**
    * Access method for the interest property.
    * 
    * @return   the current value of the interest property
    *
   public double getInterest() 
   {
      return interest;
   }
   
   /**
    * Sets the value of the interest property.
    * 
    * @param aInterest the new value of the interest property
    *
   public void setInterest(double aInterest) 
   {
      interest = aInterest;
   }
   
   /**
    * Access method for the limitSanctionedDate property.
    * 
    * @return   the current value of the limitSanctionedDate property
    *
   public Date getLimitSanctionedDate() 
   {
      return limitSanctionedDate;
   }
   
   /**
    * Sets the value of the limitSanctionedDate property.
    * 
    * @param aLimitSanctionedDate the new value of the limitSanctionedDate property
    *
   public void setLimitSanctionedDate(Date aLimitSanctionedDate) 
   {
      limitSanctionedDate = aLimitSanctionedDate;
   }
   
   /**
	  * Access method for the oldCGPAN property.
	  * 
	  * @return   the current value of the oldCGPAN property
	  *
	 public String getOldCGPAN() 
	 {
		return oldCGPAN;
	 }
   
	 /**
	  * Sets the value of the oldCGPAN property.
	  * 
	  * @param aOldCGPAN the new value of the oldCGPAN property
	  *
	 public void setOldCGPAN(String aOldCGPAN) 
	  {
		oldCGPAN = aOldCGPAN;
	 }
   
	 /**
	  * Access method for the cgpan property.
	  * 
	  * @return   the current value of the cgpan property
	  *
	 public String getCgpan() 
	 {
		return cgpan;
	 }
   
	 /**
	  * Sets the value of the cgpan property.
	  * 
	  * @param aCgpan the new value of the cgpan property
	  *
	 public void setCgpan(String aCgpan) 
	 {
		cgpan = aCgpan;
	 }
   
	 /**
	  * Access method for the assessed property.
	  * 
	  * @return   the current value of the assessed property
	  *
	 public double getAssessed() 
	 {
		return assessed;
	 }
   
	 /**
	  * Sets the value of the assessed property.
	  * 
	  * @param aAssessed the new value of the assessed property
	  *
	 public void setAssessed(double aAssessed) 
	 {
		assessed = aAssessed;
	 }*/
   
	
	/**
	 * @return
	 */
	public int getWcTenure() {
		return wcTenure;
	}

	/**
	 * @param i
	 */
	public void setWcTenure(int i) {
		wcTenure = i;
	}

}
