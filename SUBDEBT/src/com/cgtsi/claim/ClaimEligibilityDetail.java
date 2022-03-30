//Source file: F:\\CGTSI\\Design\\DesignModel\\com\\cgtsi\\claim\\ClaimEligibilityDetail.java

package com.cgtsi.claim;


/**
 * This is a Value Object holds data related to Claim Eligibility Criteria.
 */
public class ClaimEligibilityDetail 
{
   private java.lang.String cgpan;
   private boolean npaDetailAvailable;
   private boolean lockinPeriodExpired;
   private boolean guaranteeFeePaid;
   private boolean guaranteeCoverLive;
   private boolean claimLodgedEarlier;
   private boolean cgtsiResponseForOTS;
   private double outStandingAmountAtNPA;
   private boolean hasCourtProceedingsBeenInitiated;
   private double guaranteeIssuedAmount;
   private java.lang.String mliID;
   private boolean itpanAvailable;
   
   /**
    * This variable holds whether OTS request is made or not
    */
   private boolean otsRequest;
   
   /**
    * This variable holds whether the requested OTS is approved or not
    */
   private boolean otsApproval;
   private ITPANDetail itpanDetails;
   
   public ClaimEligibilityDetail() 
   {
    
   }
   
   /**
    * Access method for the cgpan property.
    * 
    * @return   the current value of the cgpan property
    */
   public java.lang.String getCgpan() 
   {
      return cgpan;
   }
   
   /**
    * Sets the value of the cgpan property.
    * 
    * @param aCgpan the new value of the cgpan property
    */
   public void setCgpan(java.lang.String aCgpan) 
   {
      cgpan = aCgpan;
   }
   
   /**
    * Determines if the npaDetailAvailable property is true.
    * 
    * @return   <code>true<code> if the npaDetailAvailable property is true
    */
   public boolean getNpaDetailAvailable() 
   {
      return npaDetailAvailable;
   }
   
   /**
    * Sets the value of the npaDetailAvailable property.
    * 
    * @param aNpaDetailAvailable the new value of the npaDetailAvailable property
    */
   public void setNpaDetailAvailable(boolean aNpaDetailAvailable) 
   {
      npaDetailAvailable = aNpaDetailAvailable;
   }
   
   /**
    * Determines if the lockinPeriodExpired property is true.
    * 
    * @return   <code>true<code> if the lockinPeriodExpired property is true
    */
   public boolean getLockinPeriodExpired() 
   {
      return lockinPeriodExpired;
   }
   
   /**
    * Sets the value of the lockinPeriodExpired property.
    * 
    * @param aLockinPeriodExpired the new value of the lockinPeriodExpired property
    */
   public void setLockinPeriodExpired(boolean aLockinPeriodExpired) 
   {
      lockinPeriodExpired = aLockinPeriodExpired;
   }
   
   /**
    * Determines if the guaranteeFeePaid property is true.
    * 
    * @return   <code>true<code> if the guaranteeFeePaid property is true
    */
   public boolean getGuaranteeFeePaid() 
   {
      return guaranteeFeePaid;
   }
   
   /**
    * Sets the value of the guaranteeFeePaid property.
    * 
    * @param aGuaranteeFeePaid the new value of the guaranteeFeePaid property
    */
   public void setGuaranteeFeePaid(boolean aGuaranteeFeePaid) 
   {
      guaranteeFeePaid = aGuaranteeFeePaid;
   }
   
   /**
    * Determines if the guaranteeCoverLive property is true.
    * 
    * @return   <code>true<code> if the guaranteeCoverLive property is true
    */
   public boolean getGuaranteeCoverLive() 
   {
      return guaranteeCoverLive;
   }
   
   /**
    * Sets the value of the guaranteeCoverLive property.
    * 
    * @param aGuaranteeCoverLive the new value of the guaranteeCoverLive property
    */
   public void setGuaranteeCoverLive(boolean aGuaranteeCoverLive) 
   {
      guaranteeCoverLive = aGuaranteeCoverLive;
   }
   
   /**
    * Determines if the claimLodgedEarlier property is true.
    * 
    * @return   <code>true<code> if the claimLodgedEarlier property is true
    */
   public boolean getClaimLodgedEarlier() 
   {
      return claimLodgedEarlier;
   }
   
   /**
    * Sets the value of the claimLodgedEarlier property.
    * 
    * @param aClaimLodgedEarlier the new value of the claimLodgedEarlier property
    */
   public void setClaimLodgedEarlier(boolean aClaimLodgedEarlier) 
   {
      claimLodgedEarlier = aClaimLodgedEarlier;
   }
   
   /**
    * Determines if the cgtsiResponseForOTS property is true.
    * 
    * @return   <code>true<code> if the cgtsiResponseForOTS property is true
    */
   public boolean getCgtsiResponseForOTS() 
   {
      return cgtsiResponseForOTS;
   }
   
   /**
    * Sets the value of the cgtsiResponseForOTS property.
    * 
    * @param aCgtsiResponseForOTS the new value of the cgtsiResponseForOTS property
    */
   public void setCgtsiResponseForOTS(boolean aCgtsiResponseForOTS) 
   {
      cgtsiResponseForOTS = aCgtsiResponseForOTS;
   }
   
   /**
    * Access method for the outStandingAmountAtNPA property.
    * 
    * @return   the current value of the outStandingAmountAtNPA property
    */
   public double getOutStandingAmountAtNPA() 
   {
      return outStandingAmountAtNPA;
   }
   
   /**
    * Sets the value of the outStandingAmountAtNPA property.
    * 
    * @param aOutStandingAmountAtNPA the new value of the outStandingAmountAtNPA property
    */
   public void setOutStandingAmountAtNPA(double aOutStandingAmountAtNPA) 
   {
      outStandingAmountAtNPA = aOutStandingAmountAtNPA;
   }
   
   /**
    * Determines if the hasCourtProceedingsBeenInitiated property is true.
    * 
    * @return   <code>true<code> if the hasCourtProceedingsBeenInitiated property is true
    */
   public boolean getHasCourtProceedingsBeenInitiated() 
   {
      return hasCourtProceedingsBeenInitiated;
   }
   
   /**
    * Sets the value of the hasCourtProceedingsBeenInitiated property.
    * 
    * @param aHasCourtProceedingsBeenInitiated the new value of the hasCourtProceedingsBeenInitiated property
    */
   public void setHasCourtProceedingsBeenInitiated(boolean aHasCourtProceedingsBeenInitiated) 
   {
      hasCourtProceedingsBeenInitiated = aHasCourtProceedingsBeenInitiated;
   }
   
   /**
    * Access method for the guaranteeIssuedAmount property.
    * 
    * @return   the current value of the guaranteeIssuedAmount property
    */
   public double getGuaranteeIssuedAmount() 
   {
      return guaranteeIssuedAmount;
   }
   
   /**
    * Sets the value of the guaranteeIssuedAmount property.
    * 
    * @param aGuaranteeIssuedAmount the new value of the guaranteeIssuedAmount property
    */
   public void setGuaranteeIssuedAmount(double aGuaranteeIssuedAmount) 
   {
      guaranteeIssuedAmount = aGuaranteeIssuedAmount;
   }
   
   /**
    * Access method for the mliID property.
    * 
    * @return   the current value of the mliID property
    */
   public java.lang.String getMliID() 
   {
      return mliID;
   }
   
   /**
    * Sets the value of the mliID property.
    * 
    * @param aMliID the new value of the mliID property
    */
   public void setMliID(java.lang.String aMliID) 
   {
      mliID = aMliID;
   }
   
   /**
    * Determines if the itpanAvailable property is true.
    * 
    * @return   <code>true<code> if the itpanAvailable property is true
    */
   public boolean getItpanAvailable() 
   {
      return itpanAvailable;
   }
   
   /**
    * Sets the value of the itpanAvailable property.
    * 
    * @param aItpanAvailable the new value of the itpanAvailable property
    */
   public void setItpanAvailable(boolean aItpanAvailable) 
   {
      itpanAvailable = aItpanAvailable;
   }
   
   /**
    * Determines if the otsRequest property is true.
    * 
    * @return   <code>true<code> if the otsRequest property is true
    */
   public boolean getOtsRequest() 
   {
      return otsRequest;
   }
   
   /**
    * Sets the value of the otsRequest property.
    * 
    * @param aOtsRequest the new value of the otsRequest property
    */
   public void setOtsRequest(boolean aOtsRequest) 
   {
      otsRequest = aOtsRequest;
   }
   
   /**
    * Determines if the otsApproval property is true.
    * 
    * @return   <code>true<code> if the otsApproval property is true
    */
   public boolean getOtsApproval() 
   {
      return otsApproval;
   }
   
   /**
    * Sets the value of the otsApproval property.
    * 
    * @param aOtsApproval the new value of the otsApproval property
    */
   public void setOtsApproval(boolean aOtsApproval) 
   {
      otsApproval = aOtsApproval;
   }
   
   /**
    * Access method for the itpanDetails property.
    * 
    * @return   the current value of the itpanDetails property
    */
   public ITPANDetail getItpanDetails() 
   {
      return itpanDetails;
   }
   
   /**
    * Sets the value of the itpanDetails property.
    * 
    * @param aItpanDetails the new value of the itpanDetails property
    */
   public void setItpanDetails(ITPANDetail aItpanDetails) 
   {
      itpanDetails = aItpanDetails;
   }
   
   /**
    * This method checks whether the claim application is eligible with the 
    * eligibility details available within the object.
    * @return boolean
    */
   public boolean isClaimEligible() 
   {
    return true;
   }
}
