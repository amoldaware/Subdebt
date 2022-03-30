//Source file: D:\\com\\cgtsi\\receiptspayments\\PayInSlip.java

package com.cgtsi.receiptspayments;

import java.util.Date;


public class PayInSlip 
{
   private String paymentId;
   private String payableTo;
   private String instrumentType;
   private String instrumentNo;
   private Double instrumentAmount;
   private Date instrumentDate;
   private String collectingBankBranch;
   private String cgtsiAccountNumber;
   private String cgtsiAccountHoldingBranch;
   private Date dateOfPayment;
   private String drawnAt;
   private String payableAt;
   private String officerName;
   
   /**
    * @roseuid 39B9CCDA02AF
    */
   public PayInSlip() 
   {
    
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
    * Access method for the payableTo property.
    * 
    * @return   the current value of the payableTo property
    */
   public String getPayableTo() 
   {
      return payableTo;
   }
   
   /**
    * Sets the value of the payableTo property.
    * 
    * @param aPayableTo the new value of the payableTo property
    */
   public void setPayableTo(String aPayableTo) 
   {
      payableTo = aPayableTo;
   }
   
   /**
    * Access method for the instrumentType property.
    * 
    * @return   the current value of the instrumentType property
    */
   public String getInstrumentType() 
   {
      return instrumentType;
   }
   
   /**
    * Sets the value of the instrumentType property.
    * 
    * @param aInstrumentType the new value of the instrumentType property
    */
   public void setInstrumentType(String aInstrumentType) 
   {
      instrumentType = aInstrumentType;
   }
   
   /**
    * Access method for the instrumentNo property.
    * 
    * @return   the current value of the instrumentNo property
    */
   public String getInstrumentNo() 
   {
      return instrumentNo;
   }
   
   /**
    * Sets the value of the instrumentNo property.
    * 
    * @param aInstrumentNo the new value of the instrumentNo property
    */
   public void setInstrumentNo(String aInstrumentNo) 
   {
      instrumentNo = aInstrumentNo;
   }
   
   /**
    * Access method for the instrumentAmount property.
    * 
    * @return   the current value of the instrumentAmount property
    */
   public Double getInstrumentAmount() 
   {
      return instrumentAmount;
   }
   
   /**
    * Sets the value of the instrumentAmount property.
    * 
    * @param aInstrumentAmount the new value of the instrumentAmount property
    */
   public void setInstrumentAmount(Double aInstrumentAmount) 
   {
      instrumentAmount = aInstrumentAmount;
   }
   
   /**
    * Access method for the instrumentDate property.
    * 
    * @return   the current value of the instrumentDate property
    */
   public Date getInstrumentDate() 
   {
      return instrumentDate;
   }
   
   /**
    * Sets the value of the instrumentDate property.
    * 
    * @param aInstrumentDate the new value of the instrumentDate property
    */
   public void setInstrumentDate(Date aInstrumentDate) 
   {
      instrumentDate = aInstrumentDate;
   }
   
   /**
    * Access method for the collectingBankBranch property.
    * 
    * @return   the current value of the collectingBankBranch property
    */
   public String getCollectingBankBranch() 
   {
      return collectingBankBranch;
   }
   
   /**
    * Sets the value of the collectingBankBranch property.
    * 
    * @param aCollectingBankBranch the new value of the collectingBankBranch property
    */
   public void setCollectingBankBranch(String aCollectingBankBranch) 
   {
      collectingBankBranch = aCollectingBankBranch;
   }
   
   /**
    * Access method for the cgtsiAccountNumber property.
    * 
    * @return   the current value of the cgtsiAccountNumber property
    */
   public String getCgtsiAccountNumber() 
   {
      return cgtsiAccountNumber;
   }
   
   /**
    * Sets the value of the cgtsiAccountNumber property.
    * 
    * @param aCgtsiAccountNumber the new value of the cgtsiAccountNumber property
    */
   public void setCgtsiAccountNumber(String aCgtsiAccountNumber) 
   {
      cgtsiAccountNumber = aCgtsiAccountNumber;
   }
   
   /**
    * Access method for the cgtsiAccountHoldingBranch property.
    * 
    * @return   the current value of the cgtsiAccountHoldingBranch property
    */
   public String getCgtsiAccountHoldingBranch() 
   {
      return cgtsiAccountHoldingBranch;
   }
   
   /**
    * Sets the value of the cgtsiAccountHoldingBranch property.
    * 
    * @param aCgtsiAccountHoldingBranch the new value of the cgtsiAccountHoldingBranch property
    */
   public void setCgtsiAccountHoldingBranch(String aCgtsiAccountHoldingBranch) 
   {
      cgtsiAccountHoldingBranch = aCgtsiAccountHoldingBranch;
   }
   
   /**
    * Access method for the dateOfPayment property.
    * 
    * @return   the current value of the dateOfPayment property
    */
   public Date getDateOfPayment() 
   {
      return dateOfPayment;
   }
   
   /**
    * Sets the value of the dateOfPayment property.
    * 
    * @param aDateOfPayment the new value of the dateOfPayment property
    */
   public void setDateOfPayment(Date aDateOfPayment) 
   {
      dateOfPayment = aDateOfPayment;
   }
   
   /**
    * Access method for the drawnAt property.
    * 
    * @return   the current value of the drawnAt property
    */
   public String getDrawnAt() 
   {
      return drawnAt;
   }
   
   /**
    * Sets the value of the drawnAt property.
    * 
    * @param aDrawnAt the new value of the drawnAt property
    */
   public void setDrawnAt(String aDrawnAt) 
   {
      drawnAt = aDrawnAt;
   }
   
   /**
    * Access method for the payableAt property.
    * 
    * @return   the current value of the payableAt property
    */
   public String getPayableAt() 
   {
      return payableAt;
   }
   
   /**
    * Sets the value of the payableAt property.
    * 
    * @param aPayableAt the new value of the payableAt property
    */
   public void setPayableAt(String aPayableAt) 
   {
      payableAt = aPayableAt;
   }
   
   /**
    * Access method for the officerName property.
    * 
    * @return   the current value of the officerName property
    */
   public String getOfficerName() 
   {
      return officerName;
   }
   
   /**
    * Sets the value of the officerName property.
    * 
    * @param aOfficerName the new value of the officerName property
    */
   public void setOfficerName(String aOfficerName) 
   {
      officerName = aOfficerName;
   }
}
