//Source file: F:\\CGTSI\\Design\\DesignModel\\com\\cgtsi\\claim\\ClaimSummaryDtls.java

package com.cgtsi.claim;


public class ClaimSummaryDtls  implements java.io.Serializable
{
   private String cgpan;
   private String limitCoveredUnderCGFSI;
   private double amount;
   private String typeOfFacility;
   private double amntSettledByCGTSI;
   private java.util.Date dtOfSettlemntOfFirstInstallmentOfClm;
   private double amntClaimedInFinalInstllmnt;

   public ClaimSummaryDtls()
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
    * Access method for the limitCoveredUnderCGFSI property.
    *
    * @return   the current value of the limitCoveredUnderCGFSI property
    */
   public String getLimitCoveredUnderCGFSI()
   {
      return limitCoveredUnderCGFSI;
   }

   /**
    * Sets the value of the limitCoveredUnderCGFSI property.
    *
    * @param aLimitCoveredUnderCGFSI the new value of the limitCoveredUnderCGFSI property
    */
   public void setLimitCoveredUnderCGFSI(String aLimitCoveredUnderCGFSI)
   {
      limitCoveredUnderCGFSI = aLimitCoveredUnderCGFSI;
   }

   /**
    * Access method for the amount property.
    *
    * @return   the current value of the amount property
    */
   public double getAmount()
   {
      return amount;
   }

   /**
    * Sets the value of the amount property.
    *
    * @param aAmount the new value of the amount property
    */
   public void setAmount(double aAmount)
   {
      amount = aAmount;
   }

   public String getTypeOfFacility()
   {
	   return this.typeOfFacility;
   }

   public void setTypeOfFacility(String aString)
   {
	   this.typeOfFacility = aString;
   }

   public double getAmntSettledByCGTSI()
   {
	   return this.amntSettledByCGTSI;
   }

   public void setAmntSettledByCGTSI(double amnt)
   {
	   this.amntSettledByCGTSI = amnt;
   }

   public java.util.Date getDtOfSettlemntOfFirstInstallmentOfClm()
   {
	   return this.dtOfSettlemntOfFirstInstallmentOfClm;
   }

   public void setDtOfSettlemntOfFirstInstallmentOfClm(java.util.Date dt)
   {
	   this.dtOfSettlemntOfFirstInstallmentOfClm = dt;
   }

   public double getAmntClaimedInFinalInstllmnt()
   {
	   return this.amntClaimedInFinalInstllmnt;
   }

   public void setAmntClaimedInFinalInstllmnt(double amnt)
   {
	   this.amntClaimedInFinalInstllmnt = amnt;
   }
}
