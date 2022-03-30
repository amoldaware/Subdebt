//Source file: D:\\com\\cgtsi\\receiptspayments\\PendingPayments.java

package com.cgtsi.receiptspayments;

import java.util.Date ;

public class PendingPayments 
{
   private String mliId;
   private Date danDate;
   private String cgpan;
   private double amount;
   private double penalty;
   
   /**
    * @roseuid 39BA2A1B038A
    */
   public PendingPayments() 
   {
    
   }
   
   /**
    * @param MLIId
    * @param DANDate
    * @param CGPAN
    * @param Amount
    * @param Penalty
    * @roseuid 39BA2A08038C
    */
   public PendingPayments(String mliId, Date danDate, String cgpan, double amount, double penalty) 
   {
    	this.mliId = mliId;
    	this.danDate = danDate ;
    	this.cgpan = cgpan ;
    	this.amount = amount ;
    	this.penalty = penalty ;
    	
   }
   
   /**
    * Access method for the mliId property.
    * 
    * @return   the current value of the mliId property
    */
   public String getMliId() 
   {
      return mliId;
   }
   
   /**
    * Sets the value of the mliId property.
    * 
    * @param aMliId the new value of the mliId property
    */
   public void setMliId(String aMliId) 
   {
      mliId = aMliId;
   }
   
   /**
    * Access method for the danDate property.
    * 
    * @return   the current value of the danDate property
    */
   public Date getDanDate() 
   {
      return danDate;
   }
   
   /**
    * Sets the value of the danDate property.
    * 
    * @param aDanDate the new value of the danDate property
    */
   public void setDanDate(Date aDanDate) 
   {
      danDate = aDanDate;
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
   
   /**
    * Access method for the penalty property.
    * 
    * @return   the current value of the penalty property
    */
   public double getPenalty() 
   {
      return penalty;
   }
   
   /**
    * Sets the value of the penalty property.
    * 
    * @param aPenalty the new value of the penalty property
    */
   public void setPenalty(double aPenalty) 
   {
      penalty = aPenalty;
   }
}
