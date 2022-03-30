//Source file: C:\\CGTSI\\Vss\\com\\cgtsi\\admin\\ParameterMaster.java

package com.cgtsi.admin;
import com.cgtsi.common.DatabaseException;


/**
 * Mater record specialized in industry.
 */
public class ParameterMaster extends Master
{
   private int activeUserLimit=3;
   private int passwordExpiryDays=15;
   private int passwordDisplayPeriod=15;
   private int wcTenorInYrs=5;
   private double maxIntRateApplied=3;
   private double maxApprovedAmt=50000000;
   private double mudraMaxAmt=200000;
   //ADDED BY SUKUMAR@PATH FOR RSF MAXIMUM APPROVED AMOUNT
  private double maxRsfApprovedAmt=5000000;
  
    private double maxRsf2ApprovedAmt=5000000;
  
   private double firstInstallClaim=75;
   private int lockInPeriod=24;
   private double minAmtForMandatoryITPAN=1000000;
   private int claimSettlementWithoutPenalty=30;
   private double serviceFeeRate=1;
   private int serviceFeeWithoutPenalty=30;
   private double serviceFeePenaltyRate=1;
   private int noOfCgpansPerDAN=30;
   private int guaranteeFeeWithoutPenalty=30;
   private double shortLimit=1000;
   private double excessLimit=2000;
   private double waiveLimit=100;
   private int guaranteeFeeFirstAlert=10;
   private int guaranteeFeeAlertFrequency=15;
   private int serviceFeeCalculationDay=31;
   private String serviceFeeCalculationMonth="March";
   private int serviceFeeAlertFreq=5;
   private String periodicInfoFrequency="Monthly";
   private int periodicInfoAlertFreq=5;

   private double bankRate=10;
   private double guaranteeFeePenaltyRate=1;
   private double claimPenaltyRate=1;

   private String penaltyCalculationType="daily";
   private String mcgfServiceFee="NO";

   private String thirdPartyGuarantee="";

   private String collateralTaken="";
// Added guarantee fee parameter on 08-06-2004.
   private double guaranteeFee=1.0;

   private int chequeExpiryInMonths=6;

   private int chequeExpiryWarningInDays=20;

   private double mcgfGuaranteeFeePercentage;

  /*
   * The following properties being added to incorporate Sub-Scheme Parameters in Risk Management
   * Being added on 19-08-2004
   */
   private int applicationFilingTimeLimit;

   private double minimumSanctionedAmount;

   private String isDefaultRateApplicable;

   private double defaultRate;

   private double moderationFactor;

   private double cgtsiLiability;

   private double highValClearanceAmnt;

   private int periodTenureExpiryLodgementClaims; 

   AdminDAO adminDAO;

   /**
    * @roseuid 39B875C40380
    */
   public ParameterMaster()
   {
    	adminDAO=new AdminDAO();
   }

   
   public double getMudraMaxAmt() {
	return mudraMaxAmt;
}


public void setMudraMaxAmt(double mudraMaxAmt) {
	this.mudraMaxAmt = mudraMaxAmt;
}


/**
    * Access method for the activeUserLimit property.
    *
    * @return   the current value of the activeUserLimit property
    */
   public int getActiveUserLimit()
   {
      return activeUserLimit;
   }
   
   

   /**
    * Sets the value of the activeUserLimit property.
    *
    * @param aActiveUserLimit the new value of the activeUserLimit property
    */
   public void setActiveUserLimit(int aActiveUserLimit)
   {
      activeUserLimit = aActiveUserLimit;
   }

   /**
    * Access method for the passwordExpiryDays property.
    *
    * @return   the current value of the passwordExpiryDays property
    */
   public int getPasswordExpiryDays()
   {
      return passwordExpiryDays;
   }

   /**
    * Sets the value of the passwordExpiryDays property.
    *
    * @param aPasswordExpiryDays the new value of the passwordExpiryDays property
    */
   public void setPasswordExpiryDays(int aPasswordExpiryDays)
   {
      passwordExpiryDays = aPasswordExpiryDays;
   }

   /**
    * Access method for the passwordDisplayPeriod property.
    *
    * @return   the current value of the passwordDisplayPeriod property
    */
   public int getPasswordDisplayPeriod()
   {
      return passwordDisplayPeriod;
   }

   /**
    * Sets the value of the passwordDisplayPeriod property.
    *
    * @param aPasswordDisplayPeriod the new value of the passwordDisplayPeriod property
    */
   public void setPasswordDisplayPeriod(int aPasswordDisplayPeriod)
   {
      passwordDisplayPeriod = aPasswordDisplayPeriod;
   }

   /**
    * Access method for the wcTenorInYrs property.
    *
    * @return   the current value of the wcTenorInYrs property
    */
   public int getWcTenorInYrs()
   {
      return wcTenorInYrs;
   }

   /**
    * Sets the value of the wcTenorInYrs property.
    *
    * @param aWcTenorInYrs the new value of the wcTenorInYrs property
    */
   public void setWcTenorInYrs(int aWcTenorInYrs)
   {
      wcTenorInYrs = aWcTenorInYrs;
   }

   /**
    * Access method for the maxIntRateApplied property.
    *
    * @return   the current value of the maxIntRateApplied property
    */
   public double getMaxIntRateApplied()
   {
      return maxIntRateApplied;
   }

   /**
    * Sets the value of the maxIntRateApplied property.
    *
    * @param aMaxIntRateApplied the new value of the maxIntRateApplied property
    */
   public void setMaxIntRateApplied(double aMaxIntRateApplied)
   {
      maxIntRateApplied = aMaxIntRateApplied;
   }
   /**
    * Access method for the maxApprovedAmt property.
    *
    * @return   the current value of the maxApprovedAmt property
    */
   public double getMaxApprovedAmt()
   {
      return maxApprovedAmt;
   }

   /**
    * Sets the value of the maxApprovedAmt property.
    *
    * @param aMaxApprovedAmt the new value of the maxApprovedAmt property
    */
   public void setMaxApprovedAmt(double aMaxApprovedAmt)
   {
      maxApprovedAmt = aMaxApprovedAmt;
   }



public double getMaxRsfApprovedAmt()
   {
      return maxRsfApprovedAmt;
   }

   /**
    * Sets the value of the maxApprovedAmt property.
    *
    * @param aMaxApprovedAmt the new value of the maxApprovedAmt property
    */
   public void setMaxRsfApprovedAmt(double aMaxRsfApprovedAmt)
   {
      maxRsfApprovedAmt = aMaxRsfApprovedAmt;
   }
   /**
    * Access method for the firstInstallClaim property.
    *
    * @return   the current value of the firstInstallClaim property
    */
   public double getFirstInstallClaim()
   {
      return firstInstallClaim;
   }

   /**
    * Sets the value of the firstInstallClaim property.
    *
    * @param aFirstInstallClaim the new value of the firstInstallClaim property
    */
   public void setFirstInstallClaim(double aFirstInstallClaim)
   {
      firstInstallClaim = aFirstInstallClaim;
   }

   /**
    * Access method for the lockInMonths property.
    *
    * @return   the current value of the lockInMonths property
    */
   public int getLockInPeriod()
   {
      return lockInPeriod;
   }

   /**
    * Sets the value of the lockInMonths property.
    *
    * @param aLockInMonths the new value of the lockInMonths property
    */
   public void setLockInPeriod(int aLockInMonths)
   {
      lockInPeriod = aLockInMonths;
   }

   /**
    * Access method for the minAmtForMandatoryITPAN property.
    *
    * @return   the current value of the minAmtForMandatoryITPAN property
    */
   public double getMinAmtForMandatoryITPAN()
   {
      return minAmtForMandatoryITPAN;
   }
   /**
    * Access method for the claimSettlementWithoutPenalty property.
    *
    * @return   the current value of the claimSettlementWithoutPenalty property
    */
   public int getClaimSettlementWithoutPenalty()
   {
      return claimSettlementWithoutPenalty;
   }

   /**
    * Sets the value of the claimSettlementWithoutPenalty property.
    *
    * @param aClaimSettlementWithoutPenalty the new value of the claimSettlementWithoutPenalty property
    */
   public void setClaimSettlementWithoutPenalty(int aClaimSettlementWithoutPenalty)
   {
      claimSettlementWithoutPenalty = aClaimSettlementWithoutPenalty;
   }

   /**
    * Access method for the serviceRate property.
    *
    * @return   the current value of the serviceRate property
    */
   public double getServiceFeeRate()
   {
      return serviceFeeRate;
   }

   /**
    * Sets the value of the serviceRate property.
    *
    * @param aServiceRate the new value of the serviceRate property
    */
   public void setServiceFeeRate(double aServiceRate)
   {
      serviceFeeRate = aServiceRate;
   }

   /**
    * Access method for the serviceFeeWithoutPenalty property.
    *
    * @return   the current value of the serviceFeeWithoutPenalty property
    */
   public int getServiceFeeWithoutPenalty()
   {
      return serviceFeeWithoutPenalty;
   }

   /**
    * Sets the value of the serviceFeeWithoutPenalty property.
    *
    * @param aServiceFeeWithoutPenalty the new value of the serviceFeeWithoutPenalty property
    */
   public void setServiceFeeWithoutPenalty(int aServiceFeeWithoutPenalty)
   {
      serviceFeeWithoutPenalty = aServiceFeeWithoutPenalty;
   }

   /**
    * Access method for the shortLimit property.
    *
    * @return   the current value of the shortLimit property
    */
   public double getShortLimit()
   {
      return shortLimit;
   }

   /**
    * Sets the value of the shortLimit property.
    *
    * @param aShortLimit the new value of the shortLimit property
    */
   public void setShortLimit(double aShortLimit)
   {
      shortLimit = aShortLimit;
   }

   /**
    * Access method for the excessLimit property.
    *
    * @return   the current value of the excessLimit property
    */
   public double getExcessLimit()
   {
      return excessLimit;
   }

   /**
    * Sets the value of the excessLimit property.
    *
    * @param aExcessLimit the new value of the excessLimit property
    */
   public void setExcessLimit(double aExcessLimit)
   {
      excessLimit = aExcessLimit;
   }

   /**
    * Access method for the guaranteeFeeFirstAlert property.
    *
    * @return   the current value of the guaranteeFeeFirstAlert property
    */
   public int getGuaranteeFeeFirstAlert()
   {
      return guaranteeFeeFirstAlert;
   }

   /**
    * Sets the value of the guaranteeFeeFirstAlert property.
    *
    * @param aGuaranteeFeeFirstAlert the new value of the guaranteeFeeFirstAlert property
    */
   public void setGuaranteeFeeFirstAlert(int aGuaranteeFeeFirstAlert)
   {
      guaranteeFeeFirstAlert = aGuaranteeFeeFirstAlert;
   }

   /**
    * Access method for the periodicInfoFrequency property.
    *
    * @return   the current value of the periodicInfoFrequency property
    */
   public String getPeriodicInfoFrequency()
   {
      return periodicInfoFrequency;
   }

   /**
    * Sets the value of the periodicInfoFrequency property.
    *
    * @param aPeriodicInfoFrequency the new value of the periodicInfoFrequency property
    */
   public void setPeriodicInfoFrequency(String aPeriodicInfoFrequency)
   {
      periodicInfoFrequency = aPeriodicInfoFrequency;
   }

   /**
    * Access method for the periodicInfoAlertFreq property.
    *
    * @return   the current value of the periodicInfoAlertFreq property
    */
   public int getPeriodicInfoAlertFreq()
   {
      return periodicInfoAlertFreq;
   }

   /**
    * Sets the value of the periodicInfoAlertFreq property.
    *
    * @param aPeriodicInfoAlertFreq the new value of the periodicInfoAlertFreq property
    */
   public void setPeriodicInfoAlertFreq(int aPeriodicInfoAlertFreq)
   {
      periodicInfoAlertFreq = aPeriodicInfoAlertFreq;
   }

   /**
    * @return int
    * @roseuid 39B8969B01AF
    */
   public int getPasswordExpiry()
   {
    	return passwordExpiryDays;
   }

   /**
    * @return Integer
    * @roseuid 39B9BE8303B5
    */
   public int getNoOfCGPANsLimit()
   {
   		return noOfCgpansPerDAN;
   }
   /**
   * Updates the parameter master details by calling the DBClass in AdminDAO
   */

	public void updateMaster(String userId) throws DatabaseException
	{
	   adminDAO.updateParameter(this,userId);
	}

	/**
	 * @return
	 */
	public double getBankRate() {
		return bankRate;
	}

	/**
	 * @return
	 */
	public double getClaimPenaltyRate() {
		return claimPenaltyRate;
	}

	/**
	 * @return
	 */
	public int getGuaranteeFeeAlertFrequency() {
		return guaranteeFeeAlertFrequency;
	}

	/**
	 * @return
	 */
	public double getGuaranteeFeePenaltyRate() {
		return guaranteeFeePenaltyRate;
	}

	/**
	 * @return
	 */
	public int getGuaranteeFeeWithoutPenalty() {
		return guaranteeFeeWithoutPenalty;
	}

	/**
	 * @return
	 */
	public int getNoOfCgpansPerDAN() {
		return noOfCgpansPerDAN;
	}

	/**
	 * @return
	 */
	public String getPenaltyCalculationType() {
		return penaltyCalculationType;
	}

	/**
	 * @return
	 */
	public int getServiceFeeAlertFreq() {
		return serviceFeeAlertFreq;
	}

	/**
	 * @return
	 */
	public int getServiceFeeCalculationDay() {
		return serviceFeeCalculationDay;
	}

	/**
	 * @return
	 */
	public String getServiceFeeCalculationMonth() {
		return serviceFeeCalculationMonth;
	}

	/**
	 * @return
	 */
	public double getServiceFeePenaltyRate() {
		return serviceFeePenaltyRate;
	}

	/**
	 * @return
	 */
	public double getWaiveLimit() {
		return waiveLimit;
	}

	/**
	 * @param d
	 */
	public void setBankRate(double d) {
		bankRate = d;
	}

	/**
	 * @param d
	 */
	public void setClaimPenaltyRate(double d) {
		claimPenaltyRate = d;
	}

	/**
	 * @param i
	 */
	public void setGuaranteeFeeAlertFrequency(int i) {
		guaranteeFeeAlertFrequency = i;
	}

	/**
	 * @param d
	 */
	public void setGuaranteeFeePenaltyRate(double d) {
		guaranteeFeePenaltyRate = d;
	}

	/**
	 * @param i
	 */
	public void setGuaranteeFeeWithoutPenalty(int i) {
		guaranteeFeeWithoutPenalty = i;
	}

	/**
	 * @param i
	 */
	public void setMinAmtForMandatoryITPAN(double i) {
		minAmtForMandatoryITPAN = i;
	}

	/**
	 * @param i
	 */
	public void setNoOfCgpansPerDAN(int i) {
		noOfCgpansPerDAN = i;
	}

	/**
	 * @param string
	 */
	public void setPenaltyCalculationType(String string) {
		penaltyCalculationType = string;
	}

	/**
	 * @param i
	 */
	public void setServiceFeeAlertFreq(int i) {
		serviceFeeAlertFreq = i;
	}

	/**
	 * @param i
	 */
	public void setServiceFeeCalculationDay(int i) {
		serviceFeeCalculationDay = i;
	}

	/**
	 * @param string
	 */
	public void setServiceFeeCalculationMonth(String string) {
		serviceFeeCalculationMonth = string;
	}

	/**
	 * @param d
	 */
	public void setServiceFeePenaltyRate(double d) {
		serviceFeePenaltyRate = d;
	}

	/**
	 * @param d
	 */
	public void setWaiveLimit(double d) {
		waiveLimit = d;
	}

	/**
	 * @return
	 */
	public String getMcgfServiceFee() {
		return mcgfServiceFee;
	}

	/**
	 * @param string
	 */
	public void setMcgfServiceFee(String string) {
		mcgfServiceFee = string;
	}

	public int getApplicationFilingTimeLimit()
	{
		return this.applicationFilingTimeLimit;
	}

	public void setApplicationFilingTimeLimit(int val)
	{
		this.applicationFilingTimeLimit = val;
	}

	/**
	 * @return
	 */
	public String getCollateralTaken() {
		return collateralTaken;
	}

	/**
	 * @return
	 */
	public String getThirdPartyGuarantee() {
		return thirdPartyGuarantee;
	}

	/**
	 * @param string
	 */
	public void setCollateralTaken(String string) {
		collateralTaken = string;
	}

	/**
	 * @param string
	 */
	public void setThirdPartyGuarantee(String string) {
		thirdPartyGuarantee = string;
	}

	/**
	 * @return
	 */
	public double getGuaranteeFee() {
		return guaranteeFee;
	}

	/**
	 * @param d
	 */
	public void setGuaranteeFee(double d) {
		guaranteeFee = d;
	}

	/**
	 * @return
	 */
	public int getChequeExpiryInMonths() {
		return chequeExpiryInMonths;
	}

	/**
	 * @return
	 */
	public int getChequeExpiryWarningInDays() {
		return chequeExpiryWarningInDays;
	}

	/**
	 * @param i
	 */
	public void setChequeExpiryInMonths(int i) {
		chequeExpiryInMonths = i;
	}

	/**
	 * @param i
	 */
	public void setChequeExpiryWarningInDays(int i) {
		chequeExpiryWarningInDays = i;
	}

	public double getMcgfGuaranteeFeePercentage()
	{
		return this.mcgfGuaranteeFeePercentage;
	}

	public void setMcgfGuaranteeFeePercentage(double percentage)
	{
		this.mcgfGuaranteeFeePercentage = percentage;
	}


	public double getMinimumSanctionedAmount()
	{
		return this.minimumSanctionedAmount;
	}

	public void setMinimumSanctionedAmount(double amount)
	{
		this.minimumSanctionedAmount = amount;
	}

	public String getIsDefaultRateApplicable()
	{
		return this.isDefaultRateApplicable;
	}

	public void setIsDefaultRateApplicable(String str)
	{
		this.isDefaultRateApplicable = str;
	}

	public double getDefaultRate()
	{
		return this.defaultRate;
	}

	public void setDefaultRate(double rate)
	{
		this.defaultRate = rate;
	}

	public double getModerationFactor()
	{
		return this.moderationFactor;
	}

	public void setModerationFactor(double factor)
	{
		this.moderationFactor = factor;
	}

	public double getCgtsiLiability()
	{
		return this.cgtsiLiability;
	}

	public void setCgtsiLiability(double val)
	{
		this.cgtsiLiability = val;
	}

	public double getHighValClearanceAmnt()
	{
		return this.highValClearanceAmnt;
	}

	public void setHighValClearanceAmnt(double val)
	{
		this.highValClearanceAmnt = val;
	}

	/**
	 * @return
	 */
	public int getPeriodTenureExpiryLodgementClaims() {
		return periodTenureExpiryLodgementClaims;
	}
	
	/**
	 * @param i
	 */
	public void setPeriodTenureExpiryLodgementClaims(int i) {
		periodTenureExpiryLodgementClaims = i;
	}

    public void setMaxRsf2ApprovedAmt(double maxRsf2ApprovedAmt) {
        this.maxRsf2ApprovedAmt = maxRsf2ApprovedAmt;
    }

    public double getMaxRsf2ApprovedAmt() {
        return maxRsf2ApprovedAmt;
    }
}
