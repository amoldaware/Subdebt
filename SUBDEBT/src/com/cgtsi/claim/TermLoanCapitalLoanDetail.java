//Source file: F:\\CGTSI\\Design\\DesignModel\\com\\cgtsi\\claim\\TermLoanCapitalLoanDetail.java

package com.cgtsi.claim;
import java.util.Date;

public class TermLoanCapitalLoanDetail  implements java.io.Serializable
{
   private String cgpan;
   private Date lastDisbursementDate;
   private double principalRepayment;
   private double interestAndOtherCharges;
   private double outstandingAsOnDateOfNPA;
   private double outstandingStatedInCivilSuit;
   private double outstandingAsOnDateOfLodgement;
   private double osAsOnDateOfLodgementOfClmForSecInstllmnt;

   public TermLoanCapitalLoanDetail()
   {

   }

   /**
    * Access method for the cgpan property.
    *
    * @return   the current value of the cgpan property
    */
   public String getCgpan()
   {
      return cgpan;
   }

   /**
    * Sets the value of the cgpan property.
    *
    * @param aCgpan the new value of the cgpan property
    */
   public void setCgpan(String aCgpan)
   {
      cgpan = aCgpan;
   }

   /**
    * Access method for the lastDisbursementDate property.
    *
    * @return   the current value of the lastDisbursementDate property
    */
   public Date getLastDisbursementDate()
   {
      return lastDisbursementDate;
   }

   /**
    * Sets the value of the lastDisbursementDate property.
    *
    * @param aLastDisbursementDate the new value of the lastDisbursementDate property
    */
   public void setLastDisbursementDate(Date aLastDisbursementDate)
   {
      lastDisbursementDate = aLastDisbursementDate;
   }

   /**
    * Access method for the principalRepayment property.
    *
    * @return   the current value of the principalRepayment property
    */
   public double getPrincipalRepayment()
   {
      return principalRepayment;
   }

   /**
    * Sets the value of the principalRepayment property.
    *
    * @param aPrincipalRepayment the new value of the principalRepayment property
    */
   public void setPrincipalRepayment(double aPrincipalRepayment)
   {
      principalRepayment = aPrincipalRepayment;
   }

   /**
    * Access method for the interestAndOtherCharges property.
    *
    * @return   the current value of the interestAndOtherCharges property
    */
   public double getInterestAndOtherCharges()
   {
      return interestAndOtherCharges;
   }

   /**
    * Sets the value of the interestAndOtherCharges property.
    *
    * @param aInterestAndOtherCharges the new value of the interestAndOtherCharges property
    */
   public void setInterestAndOtherCharges(double aInterestAndOtherCharges)
   {
      interestAndOtherCharges = aInterestAndOtherCharges;
   }

   /**
    * Access method for the outstandingAsOnDateOfNPA property.
    *
    * @return   the current value of the outstandingAsOnDateOfNPA property
    */
   public double getOutstandingAsOnDateOfNPA()
   {
      return outstandingAsOnDateOfNPA;
   }

   /**
    * Sets the value of the outstandingAsOnDateOfNPA property.
    *
    * @param aOutstandingAsOnDateOfNPA the new value of the outstandingAsOnDateOfNPA property
    */
   public void setOutstandingAsOnDateOfNPA(double aOutstandingAsOnDateOfNPA)
   {
      outstandingAsOnDateOfNPA = aOutstandingAsOnDateOfNPA;
   }

   /**
    * Access method for the outstandingStatedInCivilSuit property.
    *
    * @return   the current value of the outstandingStatedInCivilSuit property
    */
   public double getOutstandingStatedInCivilSuit()
   {
      return outstandingStatedInCivilSuit;
   }

   /**
    * Sets the value of the outstandingStatedInCivilSuit property.
    *
    * @param aOutstandingStatedInCivilSuit the new value of the outstandingStatedInCivilSuit property
    */
   public void setOutstandingStatedInCivilSuit(double aOutstandingStatedInCivilSuit)
   {
      outstandingStatedInCivilSuit = aOutstandingStatedInCivilSuit;
   }

   /**
    * Access method for the outstandingAsOnDateOfLodgement property.
    *
    * @return   the current value of the outstandingAsOnDateOfLodgement property
    */
   public double getOutstandingAsOnDateOfLodgement()
   {
      return outstandingAsOnDateOfLodgement;
   }

   /**
    * Sets the value of the outstandingAsOnDateOfLodgement property.
    *
    * @param aOutstandingAsOnDateOfLodgement the new value of the outstandingAsOnDateOfLodgement property
    */
   public void setOutstandingAsOnDateOfLodgement(double aOutstandingAsOnDateOfLodgement)
   {
      outstandingAsOnDateOfLodgement = aOutstandingAsOnDateOfLodgement;
   }

   public double getOsAsOnDateOfLodgementOfClmForSecInstllmnt()
   {
	   return this.osAsOnDateOfLodgementOfClmForSecInstllmnt;
   }

   public void setOsAsOnDateOfLodgementOfClmForSecInstllmnt(double amnt)
   {
	   this.osAsOnDateOfLodgementOfClmForSecInstllmnt = amnt;
   }
   
    private double totaDisbAmnt;
    private String tcClaimFlag;

     public void setTotaDisbAmnt(double totaDisbAmnt) {
         this.totaDisbAmnt = totaDisbAmnt;
     }

     public double getTotaDisbAmnt() {
         return totaDisbAmnt;
     }

     public void setTcClaimFlag(String tcClaimFlag) {
         this.tcClaimFlag = tcClaimFlag;
     }

     public String getTcClaimFlag() {
         return tcClaimFlag;
     }
  
//Diksha 30/05/2017
/*     private double sancInterestRate;

	public double getSancInterestRate() {
		return sancInterestRate;
	}

	public void setSancInterestRate(double sancInterestRate) {
		this.sancInterestRate = sancInterestRate;
	}*/
}
