//Source file: C:\\CGTSI\\Vss\\com\\cgtsi\\application\\Securitization.java

package com.cgtsi.application;
import java.io.Serializable;

/**
 * This class holds the data related to securitization.
 */
public class Securitization implements Serializable
{
   //private Date firstDisbursementDate = null;
   //private Date lastDisbursementDate;
   //private double interestRate;
   
   /**
    * TRUE-Fixed
    * FALSE- Floating
    */
   //private boolean fixedIntRate;
   //private double benchMarkPLR;
   private double spreadOverPLR;
   //private Date firstRepaymentDate;
   
   /**
    * 0-Monthly
    * 1-Quarterly
    * 2- Half-Yearly
    * 3- Yearly
    */
   //private integer principalPaymentPeriodicity;
   private String pplRepaymentInEqual;
   //private integer noOfInstallments;
   //private double pplRepaid;
   //private double pplDue;
   private double tangibleNetWorth;
   private double fixedACR;
   private double currentRatio;
   //private double dscr;
   private double minimumDSCR;
   private double avgDSCR;
   private double financialPartUnit;
   
   /**
    * @roseuid 39B875CA01B2
    */
   public Securitization() 
   {
    
   }
   
   /**
    * Access method for the firstDisbursementDate property.
    * 
    * @return   the current value of the firstDisbursementDate property
    *
   public Date getFirstDisbursementDate() 
   {
      return firstDisbursementDate;
   }
   
   /**
    * Sets the value of the firstDisbursementDate property.
    * 
    * @param aFirstDisbursementDate the new value of the firstDisbursementDate property
    *
   public void setFirstDisbursementDate(Date aFirstDisbursementDate) 
   {
      firstDisbursementDate = aFirstDisbursementDate;
   }
   
   /**
    * Access method for the lastDisbursementDate property.
    * 
    * @return   the current value of the lastDisbursementDate property
    *
   public Date getLastDisbursementDate() 
   {
      return lastDisbursementDate;
   }
   
   /**
    * Sets the value of the lastDisbursementDate property.
    * 
    * @param aLastDisbursementDate the new value of the lastDisbursementDate property
    *
   public void setLastDisbursementDate(Date aLastDisbursementDate) 
   {
      lastDisbursementDate = aLastDisbursementDate;
   }
   
   /**
    * Access method for the interestRate property.
    * 
    * @return   the current value of the interestRate property
    *
   public double getInterestRate() 
   {
      return interestRate;
   }
   
   /**
    * Sets the value of the interestRate property.
    * 
    * @param aInterestRate the new value of the interestRate property
    *
   public void setInterestRate(double aInterestRate) 
   {
      interestRate = aInterestRate;
   }
   
   /**
    * Access method for the fixedIntRate property.
    * 
    * @return   the current value of the fixedIntRate property
    *
   public boolean getFixedIntRate() 
   {
      return fixedIntRate;
   }
   
   /**
    * Sets the value of the fixedIntRate property.
    * 
    * @param aFixedIntRate the new value of the fixedIntRate property
    *
   public void setFixedIntRate(boolean aFixedIntRate) 
   {
      fixedIntRate = aFixedIntRate;
   }
   
   /**
    * Access method for the benchMarkPLR property.
    * 
    * @return   the current value of the benchMarkPLR property
    *
   public double getBenchMarkPLR() 
   {
      return benchMarkPLR;
   }
   
   /**
    * Sets the value of the benchMarkPLR property.
    * 
    * @param aBenchMarkPLR the new value of the benchMarkPLR property
    *
   public void setBenchMarkPLR(double aBenchMarkPLR) 
   {
      benchMarkPLR = aBenchMarkPLR;
   }
   
   /**
    * Access method for the spreadOverPLR property.
    * 
    * @return   the current value of the spreadOverPLR property
    */
   public double getSpreadOverPLR() 
   {
      return spreadOverPLR;
   }
   
   /**
    * Sets the value of the spreadOverPLR property.
    * 
    * @param aSpreadOverPLR the new value of the spreadOverPLR property
    */
   public void setSpreadOverPLR(double aSpreadOverPLR) 
   {
      spreadOverPLR = aSpreadOverPLR;
   }
   
   /**
    * Access method for the firstRepaymentDate property.
    * 
    * @return   the current value of the firstRepaymentDate property
    *
   public Date getFirstRepaymentDate() 
   {
      return firstRepaymentDate;
   }
   
   /**
    * Sets the value of the firstRepaymentDate property.
    * 
    * @param aFirstRepaymentDate the new value of the firstRepaymentDate property
    *
   public void setFirstRepaymentDate(Date aFirstRepaymentDate) 
   {
      firstRepaymentDate = aFirstRepaymentDate;
   }
   
   /**
    * Access method for the principalPaymentPeriodicity property.
    * 
    * @return   the current value of the principalPaymentPeriodicity property
    *
   public integer getPrincipalPaymentPeriodicity() 
   {
      return principalPaymentPeriodicity;
   }
   
   /**
    * Sets the value of the principalPaymentPeriodicity property.
    * 
    * @param aPrincipalPaymentPeriodicity the new value of the principalPaymentPeriodicity property
    *
   public void setPrincipalPaymentPeriodicity(integer aPrincipalPaymentPeriodicity) 
   {
      principalPaymentPeriodicity = aPrincipalPaymentPeriodicity;
   }
   
   /**
    * Access method for the pplRepaymentInEqual property.
    * 
    * @return   the current value of the pplRepaymentInEqual property
    */
   public String getPplRepaymentInEqual() 
   {
      return pplRepaymentInEqual;
   }
   
   /**
    * Sets the value of the pplRepaymentInEqual property.
    * 
    * @param aPplRepaymentInEqual the new value of the pplRepaymentInEqual property
    */
   public void setPplRepaymentInEqual(String aPplRepaymentInEqual) 
   {
      pplRepaymentInEqual = aPplRepaymentInEqual;
   }
   
   /**
    * Access method for the noOfInstallments property.
    * 
    * @return   the current value of the noOfInstallments property
    *
   public integer getNoOfInstallments() 
   {
      return noOfInstallments;
   }
   
   /**
    * Sets the value of the noOfInstallments property.
    * 
    * @param aNoOfInstallments the new value of the noOfInstallments property
    *
   public void setNoOfInstallments(integer aNoOfInstallments) 
   {
      noOfInstallments = aNoOfInstallments;
   }
   
   /**
    * Access method for the pplRepaid property.
    * 
    * @return   the current value of the pplRepaid property
    *
   public double getPplRepaid() 
   {
      return pplRepaid;
   }
   
   /**
    * Sets the value of the pplRepaid property.
    * 
    * @param aPplRepaid the new value of the pplRepaid property
    *
   public void setPplRepaid(double aPplRepaid) 
   {
      pplRepaid = aPplRepaid;
   }
   
   /**
    * Access method for the pplDue property.
    * 
    * @return   the current value of the pplDue property
    *
   public double getPplDue() 
   {
      return pplDue;
   }
   
   /**
    * Sets the value of the pplDue property.
    * 
    * @param aPplDue the new value of the pplDue property
    *
   public void setPplDue(double aPplDue) 
   {
      pplDue = aPplDue;
   }
   
   /**
    * Access method for the tangibleNetWorth property.
    * 
    * @return   the current value of the tangibleNetWorth property
    */
   public double getTangibleNetWorth() 
   {
      return tangibleNetWorth;
   }
   
   /**
    * Sets the value of the tangibleNetWorth property.
    * 
    * @param aTangibleNetWorth the new value of the tangibleNetWorth property
    */
   public void setTangibleNetWorth(double aTangibleNetWorth) 
   {
      tangibleNetWorth = aTangibleNetWorth;
   }
   
   /**
    * Access method for the fixedACR property.
    * 
    * @return   the current value of the fixedACR property
    */
   public double getFixedACR() 
   {
      return fixedACR;
   }
   
   /**
    * Sets the value of the fixedACR property.
    * 
    * @param aFixedACR the new value of the fixedACR property
    */
   public void setFixedACR(double aFixedACR) 
   {
      fixedACR = aFixedACR;
   }
   
   /**
    * Access method for the currentRatio property.
    * 
    * @return   the current value of the currentRatio property
    */
   public double getCurrentRatio() 
   {
      return currentRatio;
   }
   
   /**
    * Sets the value of the currentRatio property.
    * 
    * @param aCurrentRatio the new value of the currentRatio property
    */
   public void setCurrentRatio(double aCurrentRatio) 
   {
      currentRatio = aCurrentRatio;
   }
   
   /**
    * Access method for the dscr property.
    * 
    * @return   the current value of the dscr property
    *
   public double getDscr() 
   {
      return dscr;
   }
   
   /**
    * Sets the value of the dscr property.
    * 
    * @param aDscr the new value of the dscr property
    *
   public void setDscr(double aDscr) 
   {
      dscr = aDscr;
   }
   
   /**
    * Access method for the minimumDSCR property.
    * 
    * @return   the current value of the minimumDSCR property
    */
   public double getMinimumDSCR() 
   {
      return minimumDSCR;
   }
   
   /**
    * Sets the value of the minimumDSCR property.
    * 
    * @param aMinimumDSCR the new value of the minimumDSCR property
    */
   public void setMinimumDSCR(double aMinimumDSCR) 
   {
      minimumDSCR = aMinimumDSCR;
   }
   
   /**
    * Access method for the avgDSCR property.
    * 
    * @return   the current value of the avgDSCR property
    */
   public double getAvgDSCR() 
   {
      return avgDSCR;
   }
   
   /**
    * Sets the value of the avgDSCR property.
    * 
    * @param aAvgDSCR the new value of the avgDSCR property
    */
   public void setAvgDSCR(double aAvgDSCR) 
   {
      avgDSCR = aAvgDSCR;
   }

	public double getFinancialPartUnit() 
   {
      return financialPartUnit;
   }

   public void setFinancialPartUnit(double aFinancialPartUnit) 
   {
      financialPartUnit = aFinancialPartUnit;
   }
}
