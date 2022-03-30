//Source file: C:\\CGTSI\\Vss\\com\\cgtsi\\application\\TermLoan.java

package com.cgtsi.application;
import java.util.Date;
import java.io.Serializable;

/**
 * Has contains details about the Term Loan
 */
public class TermLoan implements Serializable
{   
   private double amountSanctioned;  
   private Date amountSanctionedDate = null;
   private double creditGuaranteed;
   private double amtDisbursed;
   private Date firstDisbursementDate = null;
   private Date finalDisbursementDate = null;
   private int tenure;   
    /**
    * TRUE-Fixed
    * FALSE- Floating
    */
   private String interestType;     
   private double interestRate;
   private double benchMarkPLR;
   private double plr;
   private String typeOfPLR;
   
   private int repaymentMoratorium;
   private Date firstInstallmentDueDate = null;
   private int periodicity;
   private int noOfInstallments;   
   private double pplOS ;
   private double ExistingFacilityAmount;
   private double BureauExistingFacilityAmount;
   private double netOs;
   private Date pplOsAsOnDate = null;
   private int interestMoratorium;
   
/*   public double interestOs = 0;   
   public Date interestOsAsOnDate = null;   
   public int repaymentPeriodicity = 0;
   public int repaymentInEqualInstall = 0;
   public DisbursementDetail disbursementDtl = null;
   private Securitization securitization;*/
   
   /**
    * @roseuid 39B87D930132
    */
   public TermLoan() 
   {   
   }
   
   
   
   public double getNetOs() {
	return netOs;
}



public double getBureauExistingFacilityAmount() {
	return BureauExistingFacilityAmount;
}



public void setBureauExistingFacilityAmount(double bureauExistingFacilityAmount) {
	BureauExistingFacilityAmount = bureauExistingFacilityAmount;
}



public void setNetOs(double netOs) {
	this.netOs = netOs;
}



public double getExistingFacilityAmount() {
	return ExistingFacilityAmount;
}



public void setExistingFacilityAmount(double existingFacilityAmount) {
	ExistingFacilityAmount = existingFacilityAmount;
}



public int getInterestMoratorium() {
   	return interestMoratorium;
   }


   public void setInterestMoratorium(int interestMoratorium) {
   	this.interestMoratorium = interestMoratorium;
   }
   
   /**
    * Access method for the amountSanctioned property.
    * 
    * @return   the current value of the amountSanctioned property
    */
   public double getAmountSanctioned() 
   {
      return amountSanctioned;
   }
   
   /**
    * Sets the value of the amountSanctioned property.
    * 
    * @param aAmountSanctioned the new value of the amountSanctioned property
    */
   public void setAmountSanctioned(double aAmountSanctioned) 
   {
      amountSanctioned = aAmountSanctioned;
   }
   
   /**
    * Access method for the amountSanctionedDate property.
    * 
    * @return   the current value of the amountSanctionedDate property
    */
   public Date getAmountSanctionedDate() 
   {
      return amountSanctionedDate;
   }
   
   /**
    * Sets the value of the amountSanctionedDate property.
    * 
    * @param aAmountSanctionedDate the new value of the amountSanctionedDate property
    */
   public void setAmountSanctionedDate(Date aAmountSanctionedDate) 
   {
      amountSanctionedDate = aAmountSanctionedDate;
   }
   
  /**
  	* Access method for the creditGuaranteed property.
  	* 
  	* @return   the current value of the creditGuaranteed property
  	*/
	public double getCreditGuaranteed() 
	{
		return creditGuaranteed;
	}	
   
 	/**
  	* Sets the value of the creditGuaranteed property.
  	* 
  	* @param aAmountSanctioned the new value of the creditGuaranteed property
  	*/
	public void setCreditGuaranteed(double aCreditGuaranteed) 
	{
		creditGuaranteed = aCreditGuaranteed;
	}
   
   /**
    * Access method for the amtDisbursed property.
    * 
    * @return   the current value of the amtDisbursed property
    */
   	public double getAmtDisbursed() 
   	{
      return amtDisbursed;
   	}
   
   /**
    * Sets the value of the amtDisbursed property.
    * 
    * @param aAmtDisbursed the new value of the amtDisbursed property
    */
   public void setAmtDisbursed(double aAmtDisbursed) 
   {
      amtDisbursed = aAmtDisbursed;
   }
   
   /**
    * Access method for the firstDisbursementDate property.
    * 
    * @return   the current value of the firstDisbursementDate property
    */
   public Date getFirstDisbursementDate() 
   {
      return firstDisbursementDate;
   }
   
   /**
    * Sets the value of the firstDisbursementDate property.
    * 
    * @param aFirstDisbursementDate the new value of the firstDisbursementDate property
    */
   public void setFirstDisbursementDate(Date aFirstDisbursementDate) 
   {
      firstDisbursementDate = aFirstDisbursementDate;
   }
   
   /**
    * Access method for the finalDisbursementDate property.
    * 
    * @return   the current value of the finalDisbursementDate property
    */
   public Date getFinalDisbursementDate() 
   {
      return finalDisbursementDate;
   }
   
   /**
    * Sets the value of the finalDisbursementDate property.
    * 
    * @param aFinalDisbursementDate the new value of the finalDisbursementDate property
    */
   public void setFinalDisbursementDate(Date aFinalDisbursementDate) 
   {
      finalDisbursementDate = aFinalDisbursementDate;
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
  	* Access method for the InterestType property.
  	* 
  	* @return   the current value of the InterestType property
  	*/
	public String getInterestType() 
	{
		return interestType;
	}
   
	/**
	 * Sets the value of the InterestType property.
	 * 
	 * @param InterestType the new value of the InterestType property
	 */
	public void setInterestType(String aInterestType) 
	{
		interestType = aInterestType;
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
  	* Access method for the benchMarkPLR property.
  	* 
  	* @return   the current value of the benchMarkPLR property
  	*/
 	public double getBenchMarkPLR() 
 	{
		return benchMarkPLR;
	}
   
 	/**
  	* Sets the value of the benchMarkPLR property.
  	* 
  	* @param aBenchMarkPLR the new value of the benchMarkPLR property
  	*/
	public void setBenchMarkPLR(double aBenchMarkPLR) 
	{
		benchMarkPLR = aBenchMarkPLR;
	}
	
	/**
	* Access method for the plr property.
	* 
	* @return   the current value of the plr property
	*/
	public double getPlr() 
	{
	 	return plr;
	}
   
	/**
	* Sets the value of the plr property.
	*
	* @param aPLR the new value of the plr property
	*/
	public void setPlr(double aPlr) 
	{
		plr = aPlr;
	}
   
   /**
    * Access method for the repaymentMoratorium property.
    * 
    * @return   the current value of the repaymentMoratorium property
    */
   public int getRepaymentMoratorium() 
   {
      return repaymentMoratorium;
   }
   
   /**
    * Sets the value of the repaymentMoratorium property.
    * 
    * @param aRepaymentMoratorium the new value of the repaymentMoratorium property
    */
   public void setRepaymentMoratorium(int aRepaymentMoratorium) 
   {
      repaymentMoratorium = aRepaymentMoratorium;
   }
   
   /**
    * Access method for the firstInstallmentDueDate property.
    * 
    * @return   the current value of the firstInstallmentDueDate property
    */
   public Date getFirstInstallmentDueDate() 
   {
      return firstInstallmentDueDate;
   }
   
   /**
    * Sets the value of the firstInstallmentDueDate property.
    * 
    * @param aFirstInstallmentDueDate the new value of the firstInstallmentDueDate property
    */
   public void setFirstInstallmentDueDate(Date aFirstInstallmentDueDate) 
   {
      firstInstallmentDueDate = aFirstInstallmentDueDate;
   }
   
   /**
    * Access method for the periodicity property.
    * 
    * @return   the current value of the periodicity property
    */
   public int getPeriodicity() 
   {
      return periodicity;
   }
   
   /**
    * Sets the value of the periodicity property.
    * 
    * @param aPeriodicity the new value of the periodicity property
    */
   public void setPeriodicity(int aPeriodicity) 
   {
      periodicity = aPeriodicity;
   }
   
   /**
    * Access method for the pplOS property.
    * 
    * @return   the current value of the pplOS property
    */
   public double getPplOS() 
   {
      return pplOS;
   }
   
   /**
    * Sets the value of the pplOS property.
    * 
    * @param aPplOS the new value of the pplOS property
    */
   public void setPplOS(double aPplOS) 
   {
      pplOS = aPplOS;
   }
   
	/**
	  * Access method for the pplOsAsOnDate property.
	  * 
	  * @return   the current value of the pplOsAsOnDate property
	  */
	public Date getPplOsAsOnDate() 
	{
		return pplOsAsOnDate;
	}
	   
	/**
	* Sets the value of the pplOsAsOnDate property.
	* 
	* @param aPplOsAsOnDate the new value of the pplOsAsOnDate property
	*/
	public void setPplOsAsOnDate(Date aPplOsAsOnDate) 
	{
		pplOsAsOnDate = aPplOsAsOnDate;
	}
 	
	/**
	* Access method for the noOfInstallments property.
	* 
	* @return   the current value of the noOfInstallments property
	*/
   public int getNoOfInstallments() 
   {
	  return noOfInstallments;
   }
   
	/**
	* Sets the value of the noOfInstallments property.
	* 
	* @param aNoOfInstallments the new value of the noOfInstallments property
	*/
   public void setNoOfInstallments(int aNoOfInstallments) 
   {
	  noOfInstallments = aNoOfInstallments;
   }

   public String getTypeOfPLR() 
   {
	  return typeOfPLR;
   }

    public void setTypeOfPLR(String aTypeOfPLR) 
   {
	  typeOfPLR = aTypeOfPLR;
   }
   
   /**
    * Access method for the interestOs property.
    * 
    * @return   the current value of the interestOs property
    *
   public double getInterestOs() 
   {
      return interestOs;
   }
   
   /**
    * Sets the value of the interestOs property.
    * 
    * @param aInterestOs the new value of the interestOs property
    *
   public void setInterestOs(double aInterestOs) 
   {
      interestOs = aInterestOs;
   }
   
  
   
   /**
    * Access method for the interestOsAsOnDate property.
    * 
    * @return   the current value of the interestOsAsOnDate property
    *
   public Date getInterestOsAsOnDate() 
   {
      return interestOsAsOnDate;
   }
   
   /**
    * Sets the value of the interestOsAsOnDate property.
    * 
    * @param aInterestOsAsOnDate the new value of the interestOsAsOnDate property
    *
   public void setInterestOsAsOnDate(Date aInterestOsAsOnDate) 
   {
      interestOsAsOnDate = aInterestOsAsOnDate;
   }
   
   
   
  /* 
    * Access method for the repaymentPeriodicity property.
    * 
    * @return   the current value of the repaymentPeriodicity property
    
   public int getRepaymentPeriodicity() 
   {
      return repaymentPeriodicity;
   }
   
   /**
    * Sets the value of the repaymentPeriodicity property.
    * 
    * @param aRepaymentPeriodicity the new value of the repaymentPeriodicity property
    
   public void setRepaymentPeriodicity(int aRepaymentPeriodicity) 
   {
      repaymentPeriodicity = aRepaymentPeriodicity;
   }
   
   /**
    * Access method for the repaymentInEqualInstall property.
    * 
    * @return   the current value of the repaymentInEqualInstall property
    
   public int getRepaymentInEqualInstall() 
   {
      return repaymentInEqualInstall;
   }
   
   /**
    * Sets the value of the repaymentInEqualInstall property.
    * 
    * @param aRepaymentInEqualInstall the new value of the repaymentInEqualInstall property
    
   public void setRepaymentInEqualInstall(int aRepaymentInEqualInstall) 
   {
      repaymentInEqualInstall = aRepaymentInEqualInstall;
   }
   
   /**
    * Access method for the disbursementDtl property.
    * 
    * @return   the current value of the disbursementDtl property
    
   public DisbursementDetail getDisbursementDtl() 
   {
      return disbursementDtl;
   }
   
   /**
    * Sets the value of the disbursementDtl property.
    * 
    * @param aDisbursementDtl the new value of the disbursementDtl property
    
   public void setDisbursementDtl(DisbursementDetail aDisbursementDtl) 
   {
      disbursementDtl = aDisbursementDtl;
   }
   
   /**
    * Access method for the securitization property.
    * 
    * @return   the current value of the securitization property
    
   public Securitization getSecuritization() 
   {
      return securitization;
   }
   
   /**
    * Sets the value of the securitization property.
    * 
    * @param aSecuritization the new value of the securitization property
    
   public void setSecuritization(Securitization aSecuritization) 
   {
      securitization = aSecuritization;
   }*/
   
}
