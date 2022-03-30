package com.cgtsi.actionform;

import org.apache.struts.validator.ValidatorActionForm;

public class ClaimProcessingApprovalActionForm extends ValidatorActionForm
{
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	private int srNo;

	public int getSrNo() {
		return srNo;
	}

	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}

	private String message;

	private String bankId;
	private String bankName;
	private String noOfApplication;

	private String selectedClaim;

	public String getSelectedClaim() {
		return selectedClaim;
	}

	public void setSelectedClaim(String selectedClaim) {
		this.selectedClaim = selectedClaim;
	}

	private String cgpan2;

	public String getCgpan2() {
		return cgpan2;
	}

	public void setCgpan2(String cgpan2) {
		this.cgpan2 = cgpan2;
	}

	private String cgpan;
	private String userId;
	private String promotoerReferanceNo;

	// claim processing approval application dtls
	private String memberId;
	private String claimRefNumber;
	private String claimStatus;
	private String unitName;
	private String applicationRemark;
	private String returnRemark;
	private String returnRemarkDate;
	private double approveAmount;
	private double osAmtAsOnNpa;
	private double recoveryAsOnNpa;
	private double elegibleAmtFirstInst;
	private double ded;
	private double refund;
	private double sTax;
	private double sbhcess;
	private double kkCess;
	private String stateCode;
	private String gstNo;
	private String asfRecievedOrNot;
	private double netPaybleAsFirstInst;

	private String decision;
	private double approvedClaimAmnt;
	private String comments;
	private String recomendation;

	private String landReason;
	private String buildingReason;
	private String plantMachineryReason;
	private String otherFixedReason;
	private String currentAssetReson;
	private String otherReason;

	public String getLandReason() {
		return landReason;
	}

	public void setLandReason(String landReason) {
		this.landReason = landReason;
	}

	public String getBuildingReason() {
		return buildingReason;
	}

	public void setBuildingReason(String buildingReason) {
		this.buildingReason = buildingReason;
	}

	public String getPlantMachineryReason() {
		return plantMachineryReason;
	}

	public void setPlantMachineryReason(String plantMachineryReason) {
		this.plantMachineryReason = plantMachineryReason;
	}

	public String getOtherFixedReason() {
		return otherFixedReason;
	}

	public void setOtherFixedReason(String otherFixedReason) {
		this.otherFixedReason = otherFixedReason;
	}

	public String getCurrentAssetReson() {
		return currentAssetReson;
	}

	public void setCurrentAssetReson(String currentAssetReson) {
		this.currentAssetReson = currentAssetReson;
	}

	public String getOtherReason() {
		return otherReason;
	}

	public void setOtherReason(String otherReason) {
		this.otherReason = otherReason;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getNoOfApplication() {
		return noOfApplication;
	}

	public void setNoOfApplication(String noOfApplication) {
		this.noOfApplication = noOfApplication;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getClaimRefNumber() {
		return claimRefNumber;
	}

	public void setClaimRefNumber(String claimRefNumber) {
		this.claimRefNumber = claimRefNumber;
	}

	public String getClaimStatus() {
		return claimStatus;
	}

	public void setClaimStatus(String claimStatus) {
		this.claimStatus = claimStatus;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getApplicationRemark() {
		return applicationRemark;
	}

	public void setApplicationRemark(String applicationRemark) {
		this.applicationRemark = applicationRemark;
	}

	public String getReturnRemark() {
		return returnRemark;
	}

	public void setReturnRemark(String returnRemark) {
		this.returnRemark = returnRemark;
	}

	public String getReturnRemarkDate() {
		return returnRemarkDate;
	}

	public void setReturnRemarkDate(String returnRemarkDate) {
		this.returnRemarkDate = returnRemarkDate;
	}

	public double getApproveAmount() {
		return approveAmount;
	}

	public void setApproveAmount(double approveAmount) {
		this.approveAmount = approveAmount;
	}

	public double getOsAmtAsOnNpa() {
		return osAmtAsOnNpa;
	}

	public void setOsAmtAsOnNpa(double osAmtAsOnNpa) {
		this.osAmtAsOnNpa = osAmtAsOnNpa;
	}

	public double getRecoveryAsOnNpa() {
		return recoveryAsOnNpa;
	}

	public void setRecoveryAsOnNpa(double recoveryAsOnNpa) {
		this.recoveryAsOnNpa = recoveryAsOnNpa;
	}

	public double getElegibleAmtFirstInst() {
		return elegibleAmtFirstInst;
	}

	public void setElegibleAmtFirstInst(double elegibleAmtFirstInst) {
		this.elegibleAmtFirstInst = elegibleAmtFirstInst;
	}

	public double getDed() {
		return ded;
	}

	public void setDed(double ded) {
		this.ded = ded;
	}

	public double getRefund() {
		return refund;
	}

	public void setRefund(double refund) {
		this.refund = refund;
	}

	public double getSbhcess() {
		return sbhcess;
	}

	public void setSbhcess(double sbhcess) {
		this.sbhcess = sbhcess;
	}

	public double getKkCess() {
		return kkCess;
	}

	public void setKkCess(double kkCess) {
		this.kkCess = kkCess;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

	public String getAsfRecievedOrNot() {
		return asfRecievedOrNot;
	}

	public void setAsfRecievedOrNot(String asfRecievedOrNot) {
		this.asfRecievedOrNot = asfRecievedOrNot;
	}

	public double getNetPaybleAsFirstInst() {
		return netPaybleAsFirstInst;
	}

	public void setNetPaybleAsFirstInst(double netPaybleAsFirstInst) {
		this.netPaybleAsFirstInst = netPaybleAsFirstInst;
	}

	public String getDecision() {
		return decision;
	}

	public void setDecision(String decision) {
		this.decision = decision;
	}

	public double getApprovedClaimAmnt() {
		return approvedClaimAmnt;
	}

	public void setApprovedClaimAmnt(double approvedClaimAmnt) {
		this.approvedClaimAmnt = approvedClaimAmnt;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getRecomendation() {
		return recomendation;
	}

	public void setRecomendation(String recomendation) {
		this.recomendation = recomendation;
	}

	public double getsTax() {
		return sTax;
	}

	public void setsTax(double sTax) {
		this.sTax = sTax;
	}

	public String getCgpan() {
		return cgpan;
	}

	public void setCgpan(String cgpan) {
		this.cgpan = cgpan;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPromotoerReferanceNo() {
		return promotoerReferanceNo;
	}

	public void setPromotoerReferanceNo(String promotoerReferanceNo) {
		this.promotoerReferanceNo = promotoerReferanceNo;
	}

	// Added by sarita for Second Claim

	private String zoneName;
	private String promoterName;
	public String getPromoterName() {
		return promoterName;
	}

	public void setPromoterName(String promoterName) {
		this.promoterName = promoterName;
	}

	private double net_Outstanding;
	private String extent_of_guarantee;
	private double cgtmse_liability;
	private double recovery_Recived;
	private double legal_Expenses;
	private double netRecovery_RemittedtoCGTMSE;
	private double net_Amount_in_Default;
	private double CGTMSE_liability_II;
	private double second_installment_payable;
	private double final_Claim_amount;
	private int isRefund;

	public double getNet_Outstanding() {
		return net_Outstanding;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public void setNet_Outstanding(double net_Outstanding) {
		this.net_Outstanding = net_Outstanding;
	}

	

	public String getExtent_of_guarantee() {
		return extent_of_guarantee;
	}

	public void setExtent_of_guarantee(String extent_of_guarantee) {
		this.extent_of_guarantee = extent_of_guarantee;
	}

	public double getCgtmse_liability() {
		return cgtmse_liability;
	}

	public void setCgtmse_liability(double cgtmse_liability) {
		this.cgtmse_liability = cgtmse_liability;
	}

	public double getRecovery_Recived() {
		return recovery_Recived;
	}

	public void setRecovery_Recived(double recovery_Recived) {
		this.recovery_Recived = recovery_Recived;
	}

	public double getLegal_Expenses() {
		return legal_Expenses;
	}

	public void setLegal_Expenses(double legal_Expenses) {
		this.legal_Expenses = legal_Expenses;
	}

	public double getNetRecovery_RemittedtoCGTMSE() {
		return netRecovery_RemittedtoCGTMSE;
	}

	public void setNetRecovery_RemittedtoCGTMSE(double netRecovery_RemittedtoCGTMSE) {
		this.netRecovery_RemittedtoCGTMSE = netRecovery_RemittedtoCGTMSE;
	}

	public double getNet_Amount_in_Default() {
		return net_Amount_in_Default;
	}

	public void setNet_Amount_in_Default(double net_Amount_in_Default) {
		this.net_Amount_in_Default = net_Amount_in_Default;
	}

	public double getCGTMSE_liability_II() {
		return CGTMSE_liability_II;
	}

	public void setCGTMSE_liability_II(double cGTMSE_liability_II) {
		CGTMSE_liability_II = cGTMSE_liability_II;
	}

	public double getSecond_installment_payable() {
		return second_installment_payable;
	}

	public void setSecond_installment_payable(double second_installment_payable) {
		this.second_installment_payable = second_installment_payable;
	}

	public double getFinal_Claim_amount() {
		return final_Claim_amount;
	}

	public void setFinal_Claim_amount(double final_Claim_amount) {
		this.final_Claim_amount = final_Claim_amount;
	}

	public int getIsRefund() {
		return isRefund;
	}

	public void setIsRefund(int isRefund) {
		this.isRefund = isRefund;
	}

}