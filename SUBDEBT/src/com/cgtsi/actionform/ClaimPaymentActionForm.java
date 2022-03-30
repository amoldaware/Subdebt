package com.cgtsi.actionform;

import org.apache.struts.validator.ValidatorActionForm;

public class ClaimPaymentActionForm extends ValidatorActionForm{
	/**
 * 
 */
private static final long serialVersionUID = 1L;

private String message;


private String claimType;
private String memberId;
private String isSearchClicked;
private String role;

private String ccgpan;
private String referanceNumber;
private String claimTypee;
private String netPaidAmountt;
private String details;
/*PMR_REF_NO, CGPAN, MEMBER_ID, BANK_NAME, ZONE_NAME, UNIT_NAME, PROMOTERNAME,APPROVED_CLAIM_AMOUNT, 
CLAIMTYPE, APPROVED_AMT, REVISED_NPA_APPROVED_AMOUNT, REVISED_NPA_RECOVERED_AMOUNT, NET_OUTSTANDING_AMOUNT, 
CLAIM_APPLIED_AMOUNT, CLAIM_ELIGIBLE_AMOUNT, FIRST_INSTALLMENT_PAY_AMOUNT, ASF_DEDUCTABLE_REFUNDABLE, 
NET_PAID_AMOUNT, beneficiary_bank_name, Account_type, Account_Number, ISDISABLE*/



private int srNo;
private String refNumber;
private String  zoneName;
private String  bankName;
private String  cGPAN;
private String  unitName;
private String  promoterName;
private String  claimApprovedDate;
private double approveAmount;
private double revisedNPAApprovedAmount;
private double revisedNPARecoveredAmount;
private double netOutstandingAmount;
private double claimAppliedAmount;
private double approveClaimAmount;
private double claimeligibleAmount;
private double firstInstallmentPayAmount;
private double aSFDeductableRefundable;
private double netPaidAmount;
private String beneficiaryBankName;
private String accounttype;
private String accountNumber;
private String mARKAll;
private String iSDISABLE;


private String checkerReturn;







public String getCheckerReturn() {
	return checkerReturn;
}

public void setCheckerReturn(String checkerReturn) {
	this.checkerReturn = checkerReturn;
}

public String getDetails() {
	return details;
}

public void setDetails(String details) {
	this.details = details;
}

public String getReferanceNumber() {
	return referanceNumber;
}

public void setReferanceNumber(String referanceNumber) {
	this.referanceNumber = referanceNumber;
}

public String getClaimTypee() {
	return claimTypee;
}

public void setClaimTypee(String claimTypee) {
	this.claimTypee = claimTypee;
}

public String getNetPaidAmountt() {
	return netPaidAmountt;
}

public void setNetPaidAmountt(String netPaidAmountt) {
	this.netPaidAmountt = netPaidAmountt;
}

public String getCcgpan() {
	return ccgpan;
}

public void setCcgpan(String ccgpan) {
	this.ccgpan = ccgpan;
}

public String getRole() {
	return role;
}

public void setRole(String role) {
	this.role = role;
}

public String getIsSearchClicked() {
	return isSearchClicked;
}

public void setIsSearchClicked(String isSearchClicked) {
	this.isSearchClicked = isSearchClicked;
}

public String getiSDISABLE() {
	return iSDISABLE;
}

public void setiSDISABLE(String iSDISABLE) {
	this.iSDISABLE = iSDISABLE;
}

public double getApproveClaimAmount() {
	return approveClaimAmount;
}

public void setApproveClaimAmount(double approveClaimAmount) {
	this.approveClaimAmount = approveClaimAmount;
}

public int getSrNo() {
	return srNo;
}

public void setSrNo(int srNo) {
	this.srNo = srNo;
}

public String getRefNumber() {
	return refNumber;
}

public void setRefNumber(String refNumber) {
	this.refNumber = refNumber;
}

public String getZoneName() {
	return zoneName;
}

public void setZoneName(String zoneName) {
	this.zoneName = zoneName;
}

public String getBankName() {
	return bankName;
}

public void setBankName(String bankName) {
	this.bankName = bankName;
}

public String getcGPAN() {
	return cGPAN;
}

public void setcGPAN(String cGPAN) {
	this.cGPAN = cGPAN;
}

public String getUnitName() {
	return unitName;
}

public void setUnitName(String unitName) {
	this.unitName = unitName;
}

public String getPromoterName() {
	return promoterName;
}

public void setPromoterName(String promoterName) {
	this.promoterName = promoterName;
}

public String getClaimApprovedDate() {
	return claimApprovedDate;
}

public void setClaimApprovedDate(String claimApprovedDate) {
	this.claimApprovedDate = claimApprovedDate;
}

public double getApproveAmount() {
	return approveAmount;
}

public void setApproveAmount(double approveAmount) {
	this.approveAmount = approveAmount;
}

public double getRevisedNPAApprovedAmount() {
	return revisedNPAApprovedAmount;
}

public void setRevisedNPAApprovedAmount(double revisedNPAApprovedAmount) {
	this.revisedNPAApprovedAmount = revisedNPAApprovedAmount;
}

public double getRevisedNPARecoveredAmount() {
	return revisedNPARecoveredAmount;
}

public void setRevisedNPARecoveredAmount(double revisedNPARecoveredAmount) {
	this.revisedNPARecoveredAmount = revisedNPARecoveredAmount;
}

public double getNetOutstandingAmount() {
	return netOutstandingAmount;
}

public void setNetOutstandingAmount(double netOutstandingAmount) {
	this.netOutstandingAmount = netOutstandingAmount;
}

public double getClaimAppliedAmount() {
	return claimAppliedAmount;
}

public void setClaimAppliedAmount(double claimAppliedAmount) {
	this.claimAppliedAmount = claimAppliedAmount;
}

public double getClaimeligibleAmount() {
	return claimeligibleAmount;
}

public void setClaimeligibleAmount(double claimeligibleAmount) {
	this.claimeligibleAmount = claimeligibleAmount;
}

public double getFirstInstallmentPayAmount() {
	return firstInstallmentPayAmount;
}

public void setFirstInstallmentPayAmount(double firstInstallmentPayAmount) {
	this.firstInstallmentPayAmount = firstInstallmentPayAmount;
}

public double getaSFDeductableRefundable() {
	return aSFDeductableRefundable;
}

public void setaSFDeductableRefundable(double aSFDeductableRefundable) {
	this.aSFDeductableRefundable = aSFDeductableRefundable;
}

public double getNetPaidAmount() {
	return netPaidAmount;
}

public void setNetPaidAmount(double netPaidAmount) {
	this.netPaidAmount = netPaidAmount;
}

public String getBeneficiaryBankName() {
	return beneficiaryBankName;
}

public void setBeneficiaryBankName(String beneficiaryBankName) {
	this.beneficiaryBankName = beneficiaryBankName;
}

public String getAccounttype() {
	return accounttype;
}

public void setAccounttype(String accounttype) {
	this.accounttype = accounttype;
}

public String getAccountNumber() {
	return accountNumber;
}

public void setAccountNumber(String accountNumber) {
	this.accountNumber = accountNumber;
}

public String getmARKAll() {
	return mARKAll;
}

public void setmARKAll(String mARKAll) {
	this.mARKAll = mARKAll;
}

public String getClaimType() {
	return claimType;
}

public void setClaimType(String claimType) {
	this.claimType = claimType;
}

public String getMemberId() {
	return memberId;
}

public void setMemberId(String memberId) {
	this.memberId = memberId;
}

public String getMessage() {
	return message;
}

public void setMessage(String message) {
	this.message = message;
}





}
