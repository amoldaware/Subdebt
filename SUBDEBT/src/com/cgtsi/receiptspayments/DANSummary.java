package com.cgtsi.receiptspayments;

import java.util.Date;

public class DANSummary
{
  private String danId;
  private String cgpan;
  private String unitname;
  private Date danDate;
  private int noOfCGPANs;
  private double amountDue;
  private double amountPaid;
  
  private String danid1; private String borrowerDate;
  private Date dtFromDate;
  private Date dtToDate;
  private Date dtpaymentDate;
  private Date bankClearingDate;
  private double guaranteeFee;
  private double sanctionedAmount;
  private String danChecked;
  private String danIdTxt;
  private String unitNameRP;
  private String loanAccountNoRP;
  private String paymentModeRP;
  
  private String reason;
  private boolean isAllocated;
  private double amountBeingPaid;
  private String branchName;
  private String memberId;
  private Date closureDate;
  private String appStatus;
  private int originalTenure;
  private int revisedTenure;
  private String lastDateOfRePayment;
  private String appExpiryDate;
  private String termAmountSanctionedtDate;
  private String requestCreatedUserId;
  private String requestCreatedDate;
  private double inclSTaxAmnt;
  private double inclECESSAmnt;
  private double inclHECESSAmnt;
  private double  swBhaCessDed;
  private double krishiKalCess;
 

 
   

  public String getDanid1() {
	return danid1;
}

public void setDanid1(String danid1) {
	this.danid1 = danid1;
}

public String getBorrowerDate() {
	return borrowerDate;
}

public void setBorrowerDate(String borrowerDate) {
	this.borrowerDate = borrowerDate;
}

public Date getDtFromDate() {
	return dtFromDate;
}

public void setDtFromDate(Date dtFromDate) {
	this.dtFromDate = dtFromDate;
}

public Date getDtToDate() {
	return dtToDate;
}

public void setDtToDate(Date dtToDate) {
	this.dtToDate = dtToDate;
}

public Date getDtpaymentDate() {
	return dtpaymentDate;
}

public void setDtpaymentDate(Date dtpaymentDate) {
	this.dtpaymentDate = dtpaymentDate;
}

public Date getBankClearingDate() {
	return bankClearingDate;
}

public void setBankClearingDate(Date bankClearingDate) {
	this.bankClearingDate = bankClearingDate;
}

public double getGuaranteeFee() {
	return guaranteeFee;
}

public void setGuaranteeFee(double guaranteeFee) {
	this.guaranteeFee = guaranteeFee;
}

public double getSanctionedAmount() {
	return sanctionedAmount;
}

public void setSanctionedAmount(double sanctionedAmount) {
	this.sanctionedAmount = sanctionedAmount;
}

public String getDanChecked() {
	return danChecked;
}

public void setDanChecked(String danChecked) {
	this.danChecked = danChecked;
}

public String getDanIdTxt() {
	return danIdTxt;
}

public void setDanIdTxt(String danIdTxt) {
	this.danIdTxt = danIdTxt;
}

public String getUnitNameRP() {
	return unitNameRP;
}

public void setUnitNameRP(String unitNameRP) {
	this.unitNameRP = unitNameRP;
}

public String getLoanAccountNoRP() {
	return loanAccountNoRP;
}

public void setLoanAccountNoRP(String loanAccountNoRP) {
	this.loanAccountNoRP = loanAccountNoRP;
}

public String getPaymentModeRP() {
	return paymentModeRP;
}

public void setPaymentModeRP(String paymentModeRP) {
	this.paymentModeRP = paymentModeRP;
}

public void setAllocated(boolean isAllocated) {
	this.isAllocated = isAllocated;
}

public void setAmountBeingPaid(double amountBeingPaid) {
	this.amountBeingPaid = amountBeingPaid;
}

public double getKrishiKalCess() {
	return krishiKalCess;
}

public void setKrishiKalCess(double krishiKalCess) {
	this.krishiKalCess = krishiKalCess;
}

public double getSwBhaCessDed() {
	return swBhaCessDed;
}

public void setSwBhaCessDed(double swBhaCessDed) {
	this.swBhaCessDed = swBhaCessDed;
}

public String getMemberId()
  {
    return this.memberId;
  }

  public void setMemberId(String memberId)
  {
    this.memberId = memberId;
  }

  public Date getClosureDate()
  {
    return this.closureDate;
  }

  public void setClosureDate(Date closureDate)
  {
    this.closureDate = closureDate;
  }

  public String getBranchName()
  {
    return this.branchName;
  }

  public void setBranchName(String abranchName)
  {
    this.branchName = abranchName;
  }

  public String getDanId()
  {
    return this.danId;
  }

  public void setDanId(String aDanId)
  {
    this.danId = aDanId;
  }

  public String getCgpan()
  {
    return this.cgpan;
  }

  public void setCgpan(String pan)
  {
    this.cgpan = pan;
  }

  public String getUnitname()
  {
    return this.unitname;
  }

  public void setUnitname(String aunit)
  {
    this.unitname = aunit;
  }

  public Date getDanDate()
  {
    return this.danDate;
  }

  public void setDanDate(Date aDanDate)
  {
    this.danDate = aDanDate;
  }

  public int getNoOfCGPANs()
  {
    return this.noOfCGPANs;
  }

  public void setNoOfCGPANs(int aNoOfCGPANs)
  {
    this.noOfCGPANs = aNoOfCGPANs;
  }

  public double getAmountDue()
  {
    return this.amountDue;
  }

  public void setAmountDue(double aAmountDue)
  {
    this.amountDue = aAmountDue;
  }

  public double getAmountPaid()
  {
    return this.amountPaid;
  }

  public void setAmountPaid(double aAmountPaid)
  {
    this.amountPaid = aAmountPaid;
  }

  public String getReason()
  {
    return this.reason;
  }

  public void setReason(String aReason)
  {
    this.reason = aReason;
  }

  public double getAmountBeingPaid()
  {
    return this.amountBeingPaid;
  }

  public void setAmountBengPaid(double aAmountBeingPaid)
  {
    this.amountBeingPaid = aAmountBeingPaid;
  }

  public boolean getIsAllocated()
  {
    return this.isAllocated;
  }

  public void setIsAllocated(boolean aIsAllocated)
  {
    this.isAllocated = aIsAllocated;
  }

  public void setAppStatus(String appStatus)
  {
    this.appStatus = appStatus;
  }

  public String getAppStatus()
  {
    return this.appStatus;
  }

  public void setOriginalTenure(int originalTenure) {
    this.originalTenure = originalTenure;
  }

  public int getOriginalTenure()
  {
    return this.originalTenure;
  }

  public void setLastDateOfRePayment(String lastDateOfRePayment) {
    this.lastDateOfRePayment = lastDateOfRePayment;
  }

  public String getLastDateOfRePayment()
  {
    return this.lastDateOfRePayment;
  }

  public void setAppExpiryDate(String appExpiryDate) {
    this.appExpiryDate = appExpiryDate;
  }

  public String getAppExpiryDate()
  {
    return this.appExpiryDate;
  }

  public void setTermAmountSanctionedtDate(String termAmountSanctionedtDate) {
    this.termAmountSanctionedtDate = termAmountSanctionedtDate;
  }

  public String getTermAmountSanctionedtDate()
  {
    return this.termAmountSanctionedtDate;
  }

  public void setRequestCreatedUserId(String requestCreatedUserId) {
    this.requestCreatedUserId = requestCreatedUserId;
  }

  public String getRequestCreatedUserId()
  {
    return this.requestCreatedUserId;
  }

  public void setRequestCreatedDate(String requestCreatedDate) {
    this.requestCreatedDate = requestCreatedDate;
  }

  public String getRequestCreatedDate()
  {
    return this.requestCreatedDate;
  }

  public void setRevisedTenure(int revisedTenure) {
    this.revisedTenure = revisedTenure;
  }

  public int getRevisedTenure()
  {
    return this.revisedTenure;
  }

  public void setInclSTaxAmnt(double inclSTaxAmnt)
  {
    this.inclSTaxAmnt = inclSTaxAmnt;
  }

  public double getInclSTaxAmnt()
  {
    return this.inclSTaxAmnt;
  }

  public void setInclECESSAmnt(double inclECESSAmnt) {
    this.inclECESSAmnt = inclECESSAmnt;
  }

  public double getInclECESSAmnt()
  {
    return this.inclECESSAmnt;
  }

  public void setInclHECESSAmnt(double inclHECESSAmnt) {
    this.inclHECESSAmnt = inclHECESSAmnt;
  }

  public double getInclHECESSAmnt()
  {
    return this.inclHECESSAmnt;
  }
}