//Source file: D:\\com\\cgtsi\\receiptspayments\\ClaimFee.java

package com.cgtsi.receiptspayments;

import java.util.Date;


/**
 * This class contains data members and methods that deal with the details 
 * regarding Claim Fee.
 */
public class ClaimFee 
{
   private String cldan;
   private double claimAmount;
   private Date cldanGeneratedDate;
   private String status;
   private String paid;
   private Date paidDate;
   private String paymentId;
   private double paidAmount;
   
   /**
    * @roseuid 39B9CCDA0148
    */
   public ClaimFee() 
   {
    
   }
   
   /**
    * Access method for the cldan property.
    * 
    * @return   the current value of the cldan property
    */
   public String getCldan() 
   {
      return cldan;
   }
   
   /**
    * Sets the value of the cldan property.
    * 
    * @param aCldan the new value of the cldan property
    */
   public void setCldan(String aCldan) 
   {
      cldan = aCldan;
   }
   
   /**
    * Access method for the claimAmount property.
    * 
    * @return   the current value of the claimAmount property
    */
   public double getClaimAmount() 
   {
      return claimAmount;
   }
   
   /**
    * Sets the value of the claimAmount property.
    * 
    * @param aClaimAmount the new value of the claimAmount property
    */
   public void setClaimAmount(double aClaimAmount) 
   {
      claimAmount = aClaimAmount;
   }
   
   /**
    * Access method for the cldanGeneratedDate property.
    * 
    * @return   the current value of the cldanGeneratedDate property
    */
   public Date getCldanGeneratedDate() 
   {
      return cldanGeneratedDate;
   }
   
   /**
    * Sets the value of the cldanGeneratedDate property.
    * 
    * @param aCldanGeneratedDate the new value of the cldanGeneratedDate property
    */
   public void setCldanGeneratedDate(Date aCldanGeneratedDate) 
   {
      cldanGeneratedDate = aCldanGeneratedDate;
   }
   
   /**
    * Access method for the status property.
    * 
    * @return   the current value of the status property
    */
   public String getStatus() 
   {
      return status;
   }
   
   /**
    * Sets the value of the status property.
    * 
    * @param aStatus the new value of the status property
    */
   public void setStatus(String aStatus) 
   {
      status = aStatus;
   }
   
   /**
    * Access method for the paid property.
    * 
    * @return   the current value of the paid property
    */
   public String getPaid() 
   {
      return paid;
   }
   
   /**
    * Sets the value of the paid property.
    * 
    * @param aPaid the new value of the paid property
    */
   public void setPaid(String aPaid) 
   {
      paid = aPaid;
   }
   
   /**
    * Access method for the paidDate property.
    * 
    * @return   the current value of the paidDate property
    */
   public Date getPaidDate() 
   {
      return paidDate;
   }
   
   /**
    * Sets the value of the paidDate property.
    * 
    * @param aPaidDate the new value of the paidDate property
    */
   public void setPaidDate(Date aPaidDate) 
   {
      paidDate = aPaidDate;
   }
   
   /**
    * Access method for the paymentId property.
    * 
    * @return   the current value of the paymentId property
    */
   public String getPaymentId() 
   {
      return paymentId;
   }
   
   /**
    * Sets the value of the paymentId property.
    * 
    * @param aPaymentId the new value of the paymentId property
    */
   public void setPaymentId(String aPaymentId) 
   {
      paymentId = aPaymentId;
   }
   
   /**
    * Access method for the paidAmount property.
    * 
    * @return   the current value of the paidAmount property
    */
   public double getPaidAmount() 
   {
      return paidAmount;
   }
   
   /**
    * Sets the value of the paidAmount property.
    * 
    * @param aPaidAmount the new value of the paidAmount property
    */
   public void setPaidAmount(double aPaidAmount) 
   {
      paidAmount = aPaidAmount;
   }
}
