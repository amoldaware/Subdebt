//Source file: D:\\com\\cgtsi\\receiptspayments\\PaymentDetails.java

package com.cgtsi.receiptspayments;

import java.util.Date;

public class PaymentDetails 
{
   private double instrumentAmount;
   private String modeOfPayment;
   private String modeOfDelivery;
   private String instrumentNo;
   private String instrumentType;
   private Date instrumentDate;
   private String payableAt;
   private double receivedAmount;
   //added by shyam
   private Date realisationDate;
   private String paymentId;
   private String collectingBank;
   private String collectingBankBranch;
   private String cgtsiAccountHoldingBranch;
   private Date paymentDate;
   private String drawnAtBranch;
   private String officerName;
   private String drawnAtBank;
   private String userId;
   private double allocatedAmount;
   private String cgtsiAccNumber;
   //added by sukumar@path for separate the Guarantee Fee Appropriation and ASF appropriation
   private String allocationType1;
   
   //added by sukumar@path on 14-jun-2010 for capturing allocate & appropriate Remarks
  private String remarksforAppropriation;
  
  private String newInstrumentNo;
  private Date newInstrumentDt;
  
  private String accountName;
  private String ifscCode;
  private String vaccno;   //
    
//Added by DKR for Rcv total amount
	//private double totalAmountToDisplay=0.0d;
	private double totalPenalBankIntRateDisplay=0.0d;

	private double totalRemettedAmt=0.0d;
	
	public double getTotalPenalBankIntRateDisplay() {
		return totalPenalBankIntRateDisplay;
	}

	public void setTotalPenalBankIntRateDisplay(double totalPenalBankIntRateDisplay) {
		this.totalPenalBankIntRateDisplay = totalPenalBankIntRateDisplay;
	}
	public double getTotalRemettedAmt() {
		return totalRemettedAmt;
	}

	public void setTotalRemettedAmt(double totalRemettedAmt) {
		this.totalRemettedAmt = totalRemettedAmt;
	}

	public String getRemarksforAppropriation() {
		return remarksforAppropriation;
	}

  
 // 01/05/2017  
  public String getVaccno() {
		return vaccno;
	}

	public void setVaccno(String vaccno) {
		this.vaccno = vaccno;
	}

// end
	
	  
	  
   /**
    * @roseuid 39B9CCDA01F4
    */
   public PaymentDetails() 
   {
    
   }
   
   /**
   * 
   * @param remarksforAppropriation
   */
  public void setRemarksforAppropriation(String remarksforAppropriation)
  {
   this.remarksforAppropriation = remarksforAppropriation;
  }
  
  /**
   * 
   * @return remarksforAppropriation
   */
  public String getremarksforAppropriation()
  {
   return this.remarksforAppropriation;
  }
   
   public void setAllocationType1(String type){
     this.allocationType1 = type;
   }
   public String getAllocationType1(){
     return this.allocationType1;
   }
   
   /**
    * This is a constructor to PaymentDetails object. PaymentDetails object will be 
    * instantiated with the parameters passed.
    * @param PaymentId
    * @param ModeOfPayment
    * @param InstrumentType
    * @param InstrumentNumber
    * @param InstrumentDate
    * @param InstrumentAmount
    * @param PayableAt
    * @roseuid 399EAA560271
    */
   public PaymentDetails(String paymentId, String modeOfPayment, String instrumentType, String instrumentNumber, Date instrumentDate, Double instrumentAmount, String payableAt) 
   {
    
   }
   /* Added by Shyam */
    /**
    * Access method for the instrumentDate property.
    * 
    * @return   the current value of the realisationDate property
    */
   public Date getRealisationDate() 
   {
      return realisationDate;
   }
   
   
   public void setRealisationDate(Date TrealisationDate) 
   {
      realisationDate = TrealisationDate;
   }
   
   /* ---- */
   /**
    * Access method for the instrumentAmount property.
    * 
    * @return   the current value of the instrumentAmount property
    */
   public double getInstrumentAmount() 
   {
      return instrumentAmount;
   }
   
   /**
    * Sets the value of the instrumentAmount property.
    * 
    * @param aInstrumentAmount the new value of the instrumentAmount property
    */
   public void setInstrumentAmount(double aInstrumentAmount) 
   {
      instrumentAmount = aInstrumentAmount;
   }
   
   /**
    * Access method for the modeOfPayment property.
    * 
    * @return   the current value of the modeOfPayment property
    */
   public String getModeOfPayment() 
   {
      return modeOfPayment;
   }
   
   /**
    * Sets the value of the modeOfPayment property.
    * 
    * @param aModeOfPayment the new value of the modeOfPayment property
    */
   public void setModeOfPayment(String aModeOfPayment) 
   {
      modeOfPayment = aModeOfPayment;
   }
   
   /**
    * Access method for the modeOfDelivery property.
    * 
    * @return   the current value of the modeOfDelivery property
    */
   public String getModeOfDelivery() 
   {
      return modeOfDelivery;
   }
   
   /**
    * Sets the value of the modeOfDelivery property.
    * 
    * @param aModeOfDelivery the new value of the modeOfDelivery property
    */
   public void setModeOfDelivery(String aModeOfDelivery) 
   {
      modeOfDelivery = aModeOfDelivery;
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
    * Access method for the receivedAmount property.
    * 
    * @return   the current value of the receivedAmount property
    */
   public double getReceivedAmount() 
   {
      return receivedAmount;
   }
   
   /**
    * Sets the value of the receivedAmount property.
    * 
    * @param aReceivedAmount the new value of the receivedAmount property
    */
   public void setReceivedAmount(double aReceivedAmount) 
   {
      receivedAmount = aReceivedAmount;
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
    * Access method for the paymentDate property.
    * 
    * @return   the current value of the paymentDate property
    */
   public Date getPaymentDate() 
   {
      return paymentDate;
   }
   
   /**
    * Sets the value of the paymentDate property.
    * 
    * @param aPaymentDate the new value of the paymentDate property
    */
   public void setPaymentDate(Date aPaymentDate) 
   {
      paymentDate = aPaymentDate;
   }
   
   /**
    * Access method for the drawnAtBranch property.
    * 
    * @return   the current value of the drawnAtBranch property
    */
   public String getDrawnAtBranch() 
   {
      return drawnAtBranch;
   }
   
   /**
    * Sets the value of the drawnAtBranch property.
    * 
    * @param aDrawnAtBranch the new value of the drawnAtBranch property
    */
   public void setDrawnAtBranch(String aDrawnAtBranch) 
   {
      drawnAtBranch = aDrawnAtBranch;
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
   
   /**
    * Access method for the drawnAtBank property.
    * 
    * @return   the current value of the drawnAtBank property
    */
   public String getDrawnAtBank() 
   {
      return drawnAtBank;
   }
   
   /**
    * Sets the value of the drawnAtBank property.
    * 
    * @param aDrawnAtBank the new value of the drawnAtBank property
    */
   public void setDrawnAtBank(String aDrawnAtBank) 
   {
      drawnAtBank = aDrawnAtBank;
   }
/**
 * @return
 */
public String getInstrumentNo() {
	return instrumentNo;
}

/**
 * @param string
 */
public void setInstrumentNo(String string) {
	instrumentNo = string;
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
public String getCollectingBank() {
	return collectingBank;
}

/**
 * @param string
 */
public void setCollectingBank(String string) {
	collectingBank = string;
}

/**
 * @return
 */
public double getAllocatedAmount() {
	return allocatedAmount;
}

/**
 * @param d
 */
public void setAllocatedAmount(double d) {
	allocatedAmount = d;
}

/**
 * @return
 */
public String getCgtsiAccNumber() {
	return cgtsiAccNumber;
}

/**
 * @param string
 */
public void setCgtsiAccNumber(String string) {
	cgtsiAccNumber = string;
}


  public void setNewInstrumentNo(String newInstrumentNo)
  {
    this.newInstrumentNo = newInstrumentNo;
  }


  public String getNewInstrumentNo()
  {
    return newInstrumentNo;
  }


  public void setNewInstrumentDt(Date newInstrumentDt)
  {
    this.newInstrumentDt = newInstrumentDt;
  }


  public Date getNewInstrumentDt()
  {
    return newInstrumentDt;
  }


  public void setAccountName(String accountName)
  {
    this.accountName = accountName;
  }


  public String getAccountName()
  {
    return accountName;
  }


  public void setIfscCode(String ifscCode)
  {
    this.ifscCode = ifscCode;
  }


  public String getIfscCode()
  {
    return ifscCode;
  }



}
