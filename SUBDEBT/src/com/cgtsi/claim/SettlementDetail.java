//Source file: F:\\CGTSI\\Design\\DesignModel\\com\\cgtsi\\claim\\SettlementDetail.java

package com.cgtsi.claim;


public class SettlementDetail
{
   private java.lang.String mliId;
   private java.lang.String cgbid;

   // typeOfSettlement denotes First(F) Settlement or Second(S) Settlement
   private java.lang.String typeOfSettlement;
   private double osAmtAsonNPA;
   private double approvedClaimAmt;
   private java.util.Date clmApprvdDate;
   private double otsSettlement;
   private double tierOneSettlement;
   private java.util.Date tierOneSettlementDt;
   private double tierTwoSettlement;
   private java.util.Date tierTwoSettlementDt;
   private double recoveryAmt;
   private double pendingFromMLI;
   private double payableAmt;
   private String cgclan;

   // finalSettlementFlag will be "Y" or "N"
   private String finalSettlementFlag;
   private String whichInstallment;
   private double penaltyAmnt;
   private String voucherId;

   public SettlementDetail()
   {

   }

   /**
    * Access method for the mliId property.
    *
    * @return   the current value of the mliId property
    */
   public java.lang.String getMliId()
   {
      return mliId;
   }

   /**
    * Sets the value of the mliId property.
    *
    * @param aMliId the new value of the mliId property
    */
   public void setMliId(java.lang.String aMliId)
   {
      mliId = aMliId;
   }

   /**
    * Access method for the cgbid property.
    *
    * @return   the current value of the cgbid property
    */
   public java.lang.String getCgbid()
   {
      return cgbid;
   }

   /**
    * Sets the value of the cgbid property.
    *
    * @param aCgbid the new value of the cgbid property
    */
   public void setCgbid(java.lang.String aCgbid)
   {
      cgbid = aCgbid;
   }

   /**
    * Access method for the typeOfSettlement property.
    *
    * @return   the current value of the typeOfSettlement property
    */
   public java.lang.String getTypeOfSettlement()
   {
      return typeOfSettlement;
   }

   /**
    * Sets the value of the typeOfSettlement property.
    *
    * @param aTypeOfSettlement the new value of the typeOfSettlement property
    */
   public void setTypeOfSettlement(java.lang.String aTypeOfSettlement)
   {
      typeOfSettlement = aTypeOfSettlement;
   }

   /**
    * Access method for the osAmtAsonNPA property.
    *
    * @return   the current value of the osAmtAsonNPA property
    */
   public double getOsAmtAsonNPA()
   {
      return osAmtAsonNPA;
   }

   /**
    * Sets the value of the osAmtAsonNPA property.
    *
    * @param aOsAmtAsonNPA the new value of the osAmtAsonNPA property
    */
   public void setOsAmtAsonNPA(double aOsAmtAsonNPA)
   {
      osAmtAsonNPA = aOsAmtAsonNPA;
   }

   /**
    * Access method for the approvedClaimAmt property.
    *
    * @return   the current value of the approvedClaimAmt property
    */
   public double getApprovedClaimAmt()
   {
      return approvedClaimAmt;
   }

   /**
    * Sets the value of the approvedClaimAmt property.
    *
    * @param aApprovedClaimAmt the new value of the approvedClaimAmt property
    */
   public void setApprovedClaimAmt(double aApprovedClaimAmt)
   {
      approvedClaimAmt = aApprovedClaimAmt;
   }

   /**
    * Access method for the otsSettlement property.
    *
    * @return   the current value of the otsSettlement property
    */
   public double getOtsSettlement()
   {
      return otsSettlement;
   }

   /**
    * Sets the value of the otsSettlement property.
    *
    * @param aOtsSettlement the new value of the otsSettlement property
    */
   public void setOtsSettlement(double aOtsSettlement)
   {
      otsSettlement = aOtsSettlement;
   }

   /**
    * Access method for the tierOneSettlement property.
    *
    * @return   the current value of the tierOneSettlement property
    */
   public double getTierOneSettlement()
   {
      return tierOneSettlement;
   }

   /**
    * Sets the value of the tierOneSettlement property.
    *
    * @param aTierOneSettlement the new value of the tierOneSettlement property
    */
   public void setTierOneSettlement(double aTierOneSettlement)
   {
      tierOneSettlement = aTierOneSettlement;
   }

   /**
    * Access method for the tierTwoSettlement property.
    *
    * @return   the current value of the tierTwoSettlement property
    */
   public double getTierTwoSettlement()
   {
      return tierTwoSettlement;
   }

   /**
    * Sets the value of the tierTwoSettlement property.
    *
    * @param aTierTwoSettlement the new value of the tierTwoSettlement property
    */
   public void setTierTwoSettlement(double aTierTwoSettlement)
   {
      tierTwoSettlement = aTierTwoSettlement;
   }

   /**
    * Access method for the recoveryAmt property.
    *
    * @return   the current value of the recoveryAmt property
    */
   public double getRecoveryAmt()
   {
      return recoveryAmt;
   }

   /**
    * Sets the value of the recoveryAmt property.
    *
    * @param aRecoveryAmt the new value of the recoveryAmt property
    */
   public void setRecoveryAmt(double aRecoveryAmt)
   {
      recoveryAmt = aRecoveryAmt;
   }

   /**
    * Access method for the pendingFromMLI property.
    *
    * @return   the current value of the pendingFromMLI property
    */
   public double getPendingFromMLI()
   {
      return pendingFromMLI;
   }

   /**
    * Sets the value of the pendingFromMLI property.
    *
    * @param aPendingFromMLI the new value of the pendingFromMLI property
    */
   public void setPendingFromMLI(double aPendingFromMLI)
   {
      pendingFromMLI = aPendingFromMLI;
   }

   /**
    * Access method for the payableAmt property.
    *
    * @return   the current value of the payableAmt property
    */
   public double getPayableAmt()
   {
      return payableAmt;
   }

   /**
    * Sets the value of the payableAmt property.
    *
    * @param aPayableAmt the new value of the payableAmt property
    */
   public void setPayableAmt(double aPayableAmt)
   {
      payableAmt = aPayableAmt;
   }

   /**
    * Access method for the cgclan property.
    *
    * @return   the current value of the cgclan property
    */
   public String getCgclan()
   {
      return cgclan;
   }

   /**
    * Sets the value of the cgclan property.
    *
    * @param aCgclan the new value of the cgclan property
    */
   public void setCgclan(String aCgclan)
   {
      cgclan = aCgclan;
   }

   public String getFinalSettlementFlag()
   {
	   return this.finalSettlementFlag;
   }

   public void setFinalSettlementFlag(String flag)
   {
	   this.finalSettlementFlag = flag;
   }

   public double getPenaltyAmnt()
   {
	   return this.penaltyAmnt;
   }

   public void setPenaltyAmnt(double amnt)
   {
	   this.penaltyAmnt = amnt;
   }

   public java.util.Date getTierOneSettlementDt()
   {
	   return this.tierOneSettlementDt;
   }

   public void setTierOneSettlementDt(java.util.Date dt)
   {
	   this.tierOneSettlementDt = dt;
   }

   public java.util.Date getTierTwoSettlementDt()
   {
	   return this.tierTwoSettlementDt;
   }

   public void setTierTwoSettlementDt(java.util.Date dt)
   {
	   this.tierTwoSettlementDt = dt;
   }

   public String getVoucherId()
   {
	   return this.voucherId;
   }

   public void setVoucherId(String id)
   {
	   this.voucherId = id;
   }

   public String getWhichInstallment()
   {
	   return this.whichInstallment;
   }

   public void setWhichInstallment(String flag)
   {
	   this.whichInstallment = flag;
   }

   public java.util.Date getClmApprvdDate()
   {
	   return this.clmApprvdDate;
   }

   public void setClmApprvdDate(java.util.Date aDate)
   {
	   this.clmApprvdDate = aDate;
   }
}
