//Source file: F:\\CGTSI\\Design\\DesignModel\\com\\cgtsi\\claim\\SecondClaimDetails.java

package com.cgtsi.claim;


public class SecondClaimDetails
{
   private String cgbid;
   private String claimRefNumber;
   private java.lang.String claimReqDate;
   private String claimAmount;
   private String firstInstalAmount;
   private DecreeDetail decreeDetail;
   private DecreeExecutionDetail decreeExecutionDetail;

   public SecondClaimDetails()
   {

   }

   /**
    * Access method for the cgbid property.
    *
    * @return   the current value of the cgbid property
    */
   public String getCgbid()
   {
      return cgbid;
   }

   /**
    * Sets the value of the cgbid property.
    *
    * @param aCgbid the new value of the cgbid property
    */
   public void setCgbid(String aCgbid)
   {
      cgbid = aCgbid;
   }

   public String getClaimRefNumber()
   {
	   return claimRefNumber;
   }

   public void setClaimRefNumber(String claimRefNumber)
   {
	   claimRefNumber = claimRefNumber;
   }

   /**
    * Access method for the claimReqDate property.
    *
    * @return   the current value of the claimReqDate property
    */
   public java.lang.String getClaimReqDate()
   {
      return claimReqDate;
   }

   /**
    * Sets the value of the claimReqDate property.
    *
    * @param aClaimReqDate the new value of the claimReqDate property
    */
   public void setClaimReqDate(java.lang.String aClaimReqDate)
   {
      claimReqDate = aClaimReqDate;
   }

   /**
    * Access method for the claimAmount property.
    *
    * @return   the current value of the claimAmount property
    */
   public String getClaimAmount()
   {
      return claimAmount;
   }

   /**
    * Sets the value of the claimAmount property.
    *
    * @param aClaimAmount the new value of the claimAmount property
    */
   public void setClaimAmount(String aClaimAmount)
   {
      claimAmount = aClaimAmount;
   }

   /**
    * Access method for the firstInstalAmount property.
    *
    * @return   the current value of the firstInstalAmount property
    */
   public String getFirstInstalAmount()
   {
      return firstInstalAmount;
   }

   /**
    * Sets the value of the firstInstalAmount property.
    *
    * @param aFirstInstalAmount the new value of the firstInstalAmount property
    */
   public void setFirstInstalAmount(String aFirstInstalAmount)
   {
      firstInstalAmount = aFirstInstalAmount;
   }

   /**
    * Access method for the decreeDetail property.
    *
    * @return   the current value of the decreeDetail property
    */
   public DecreeDetail getDecreeDetail()
   {
      return decreeDetail;
   }

   /**
    * Sets the value of the decreeDetail property.
    *
    * @param aDecreeDetail the new value of the decreeDetail property
    */
   public void setDecreeDetail(DecreeDetail aDecreeDetail)
   {
      decreeDetail = aDecreeDetail;
   }

   /**
    * Access method for the decreeExecutionDetail property.
    *
    * @return   the current value of the decreeExecutionDetail property
    */
   public DecreeExecutionDetail getDecreeExecutionDetail()
   {
      return decreeExecutionDetail;
   }

   /**
    * Sets the value of the decreeExecutionDetail property.
    *
    * @param aDecreeExecutionDetail the new value of the decreeExecutionDetail property
    */
   public void setDecreeExecutionDetail(DecreeExecutionDetail aDecreeExecutionDetail)
   {
      decreeExecutionDetail = aDecreeExecutionDetail;
   }
}
