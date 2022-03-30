//Source file: D:\\NTS\\CGTSI\\Source\\com\\cgtsi\\receiptspayments\\DemandAdvice.java

package com.cgtsi.receiptspayments;

import java.util.Date ;

public class DemandAdvice 
{
   private String danNo;
   private String danType;
   private String bankId;
   private String zoneId;
   private String branchId;
   private Date danGeneratedDate;
   private Date danDueDate;
   private Date danExpiryDate;
   private String cgpan;
   private String borrowerId ;
   private double amountRaised;
   private String allocated;
   private String appropriated;
   private String reason;
   private String paymentId;
   private double penalty;
   private String userId ;
   private double feeId ; //Stores either the sfe_id or sht_id value from service_fee and short_info tables 
   private double cancelledAmount;
   private Date appropriatedDate=null;
   
   private String newDanNo;
   
   private String ssiRef;
   
   private String status;
   
   
   /**
    * @roseuid 39B9CCDA037A
    */
   public DemandAdvice() 
   {
    
   }
   
   /*
    * Constructor for setting the cldanamt and cldan
    * while calculating cldan amt
    * method calculate cldan amt()
    */
   public DemandAdvice(double cldanAmount, String cldan) {
   		this.amountRaised = cldanAmount ;
   		this.danNo = cldan ; //dd
   }
   
   public DemandAdvice(ServiceFee serviceFee) {
		this.danType = RpConstants.DAN_TYPE_SFDAN;
		this.bankId = serviceFee.getBankId();
		this.zoneId = serviceFee.getZoneId() ;
		this.branchId = serviceFee.getBranchId() ;
		this.cgpan = serviceFee.getCgpan() ;
		this.borrowerId = serviceFee.getBorrowerId() ;
		this.amountRaised = serviceFee.getServiceAmount() ;
		this.feeId = serviceFee.getServiceFeeId() ;
   }
   	
   public DemandAdvice(ShortAmountInfo shortAmountInfo) {
		  this.danType = RpConstants.DAN_TYPE_SHDAN;
		  this.bankId = shortAmountInfo.getBankId();
		  this.zoneId = shortAmountInfo.getZoneId() ;
		  this.branchId = shortAmountInfo.getBranchId() ;
		  this.cgpan = shortAmountInfo.getCgpan() ;
		  this.borrowerId = shortAmountInfo.getBorrowerId() ;
		  this.amountRaised = shortAmountInfo.getShortAmount() ;
		  this.feeId = shortAmountInfo.getShortId() ;
   }	
   
   /**
   * 
   * @return  ssiRef

   */
   public String getSsiRef()
   {
    return this.ssiRef;
   }
   
   /**
   * 
   * @param ssiRef
   */
   public void setSsiRef(String ssiRef)
   {
    this.ssiRef = ssiRef;

   }
   /**
    * Access method for the danNo property.
    * 
    * @return   the current value of the danNo property
    */
   public String getDanNo() 
   {
      return danNo;
   }
   
   /**
    * Sets the value of the danNo property.
    * 
    * @param aDanNo the new value of the danNo property
    */
   public void setDanNo(String aDanNo) 
   {
      danNo = aDanNo;
   }
   
   /**
    * Access method for the danType property.
    * 
    * @return   the current value of the danType property
    */
   public String getDanType() 
   {
      return danType;    
   }
   
   /**
    * Sets the value of the danType property.
    * 
    * @param aDanType the new value of the danType property
    */
   public void setDanType(String aDanType) 
   {
      danType = aDanType;
   }
   
   /**
    * Access method for the bankId property.
    * 
    * @return   the current value of the bankId property
    */
   public String getBankId() 
   {
      return bankId;
   }
   
   /**
    * Sets the value of the bankId property.
    * 
    * @param aBankId the new value of the bankId property
    */
   public void setBankId(String aBankId) 
   {
      bankId = aBankId;
   }
   
   /**
    * Access method for the zoneId property.
    * 
    * @return   the current value of the zoneId property
    */
   public String getZoneId() 
   {
      return zoneId;
   }
   
   /**
    * Sets the value of the zoneId property.
    * 
    * @param aZoneId the new value of the zoneId property
    */
   public void setZoneId(String aZoneId) 
   {
      zoneId = aZoneId;
   }
   
   /**
    * Access method for the branchId property.
    * 
    * @return   the current value of the branchId property
    */
   public String getBranchId() 
   {
      return branchId;
   }
   
   /**
    * Sets the value of the branchId property.
    * 
    * @param aBranchId the new value of the branchId property
    */
   public void setBranchId(String aBranchId) 
   {
      branchId = aBranchId;
   }
   
   /**
    * Access method for the danGeneratedDate property.
    * 
    * @return   the current value of the danGeneratedDate property
    */
   public Date getDanGeneratedDate() 
   {
      return danGeneratedDate;
   }
   
   /**
    * Sets the value of the danGeneratedDate property.
    * 
    * @param aDanGeneratedDate the new value of the danGeneratedDate property
    */
   public void setDanGeneratedDate(Date aDanGeneratedDate) 
   {
      danGeneratedDate = aDanGeneratedDate;
   }
   
   /**
    * Access method for the danDueDate property.
    * 
    * @return   the current value of the danDueDate property
    */
   public Date getDanDueDate() 
   {
      return danDueDate;
   }
   
   /**
    * Sets the value of the danDueDate property.
    * 
    * @param aDanDueDate the new value of the danDueDate property
    */
   public void setDanDueDate(Date aDanDueDate) 
   {
      danDueDate = aDanDueDate;
   }
   
   /**
    * Access method for the danExpiryDate property.
    * 
    * @return   the current value of the danExpiryDate property
    */
   public Date getDanExpiryDate() 
   {
      return danExpiryDate;
   }
   
   /**
    * Sets the value of the danExpiryDate property.
    * 
    * @param aDanExpiryDate the new value of the danExpiryDate property
    */
   public void setDanExpiryDate(Date aDanExpiryDate) 
   {
      danExpiryDate = aDanExpiryDate;
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
    * Access method for the amountRaised property.
    * 
    * @return   the current value of the amountRaised property
    */
   public double getAmountRaised() 
   {
      return amountRaised;
   }
   
   /**
    * Sets the value of the amountRaised property.
    * 
    * @param aAmountRaised the new value of the amountRaised property
    */
   public void setAmountRaised(double aAmountRaised) 
   {
      amountRaised = aAmountRaised;
   }
   
   /**
    * Access method for the allocated property.
    * 
    * @return   the current value of the allocated property
    */
   public String getAllocated() 
   {
      return allocated;    
   }
   
   /**
    * Sets the value of the allocated property.
    * 
    * @param aAllocated the new value of the allocated property
    */
   public void setAllocated(String aAllocated) 
   {
      allocated = aAllocated;
   }
   
   /**
    * Access method for the appropriated property.
    * 
    * @return   the current value of the appropriated property
    */
   public String getAppropriated() 
   {
      return appropriated;    
   }
   
   /**
    * Sets the value of the appropriated property.
    * 
    * @param aAppropriated the new value of the appropriated property
    */
   public void setAppropriated(String aAppropriated) 
   {
      appropriated = aAppropriated;
   }
   
   /**
    * Access method for the reason property.
    * 
    * @return   the current value of the reason property
    */
   public String getReason() 
   {
      return reason;    
   }
   
   /**
    * Sets the value of the reason property.
    * 
    * @param aReason the new value of the reason property
    */
   public void setReason(String aReason) 
   {
      reason = aReason;
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
   
   /**
    * @return Boolean
    * @roseuid 39A52CC0005D
    */
   public Boolean getAllocatedValue() 
   {
    return null;
   }
   
/**
 * @return
 */
public String getUserId() {
	return userId;
}

/**
 * @param string
 */
public void setUserId(String string) {
	userId = string;
}

/**
 * @return
 */
public String getBorrowerId() {
	return borrowerId;
}

/**
 * @param string
 */
public void setBorrowerId(String string) {
	borrowerId = string;
}
/**
 * @return
 */
public double getFeeId() {
	return feeId;
}

/**
 * @param string
 */
public void setFeeId(double d) {
	feeId = d;
}

/**
 * @return
 */
public double getCancelledAmount() {
	return cancelledAmount;
}

/**
 * @param d
 */
public void setCancelledAmount(double d) {
	cancelledAmount = d;
}

/**
 * @return
 */
public Date getAppropriatedDate() {
	return appropriatedDate;
}

/**
 * @param date
 */
public void setAppropriatedDate(Date date) {
	appropriatedDate = date;
}

/**
 * @return
 */
public String getNewDanNo() {
	return newDanNo;
}

/**
 * @param string
 */
public void setNewDanNo(String string) {
	newDanNo = string;
}


  public void setStatus(String status)
  {
    this.status = status;
  }


  public String getStatus()
  {
    return status;
  }

}
/* 
DemandAdvice.setMliId(String){
   		mliId = aMliId ;
   		
   }
 */
/* 
DemandAdvice.setDanDate(Date){
      danDate = aDanDate;
   }
 */
/* 
DemandAdvice.setPaymentID(String){
      paymentID = aPaymentID;
   }
 */
/* 
DemandAdvice.setDanId(String){
      danId = aDanId;
   }
 */
/* 
DemandAdvice.getDanId(){
      return danId;    
   }
 */
/* 
DemandAdvice.getAmount(){
      return amount;    
   }
 */
/* 
DemandAdvice.DemandAdvice(double,String){
		String dantype = "";
		this.amount = cldanAmount ;
		this.danId = cldan ;
		this.danDate = new Date() ;
		dantype = cldan.substring(0, 2); //gets the first 2 letters of the string
		this.danType = dantype ;
   }
 */
/* 
DemandAdvice.getPaymentID(){
      return paymentID;    
   }
 */
/* 
DemandAdvice.getMliId(){
   	return mliId ;
   }
 */
/* 
DemandAdvice.setAmount(double){
      amount = aAmount;
   }
 */
/* 
DemandAdvice.getDanDate(){
      return danDate;    
   }
 */
