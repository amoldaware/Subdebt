package com.cgtsi.actionform;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;

import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorActionForm;

public class ClaimProcessingActionForm extends ValidatorActionForm {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	private String promoterNamee;
	private String cgpan2;

	private String promoterNameValue;
	private String role;
	private String userId;
	private Map<String, String> promoterNameList;
	private String isSearchClicked;
	private String message;
	private ArrayList promoterValues;

	// promoter details
	private String promoterNameValuee;
	private String promoterUnitName;
	private double promoterGuranteeAmount;
	private String promoterSactionDate;
	private String promoterGuaranteeStartDate;

	// Branch Details

	private String branchMemberId;
	private String branchMliName;
	private String branchCity;
	private String branchDistrict;
	private String branchState;
	private String branchDealingOfficerName;
	private String branchGstNo;

	// Borrower Details

	private String borrowerCompleteAddress;
	private String borrowerCity;
	private String borrowerDistrict;
	private String borrowerState;
	private String borrowerPinCode;

	// Account Details

	private String nPADate;
	private String accountsWilFulDefaulter;
	private String accountsClassified;
	private String accountsEnquiry;
	private String accountsMliReported;
	private String accountReasonTurning;
	private String accountSatisfactoryReason;
	private String dateofIssueofRecallNotice;

	// Legal Section

	private String legalOtherForms;
	private String legalRegistration;
	private String legalSatisfactoryRreason;
	private String legalsuitFilingDate;
	private String legalLocation;
	private Double legalAmountClaimed;
	private String dateofpossessionofassets;
	private String legalProceedings;
	private ArrayList legalProceedingsValue;
	private String filenameName;
	private FormFile nocDocument1;
	// private File file;
	// attachment,

	// Term Loans (TC)

	private String tcTotalDisbursement;
	private String tcDateofLastDisbursement;
	private String tcPrincipalAmount;
	private String tcRepaymentsInterest;
	private String tcOutstandingAmount;
	private String tcOutstandingInterest;
	private String tcOutstandingStatedCivil;
	private String tcOutstandingLodgement;
	private String tcAccountRestructed;

	// Recovery Details

	private String recoveryPrincipal;
	private String recoveryInterest;
	private String recoveryMode;

	// Security and Personal Guarantee
	private String personalAmountclaim;
	private String recoveryafterNPAindicated;
	private String valueasrecoveriesafterNPA;

	// general Information
	private String generalMLIComment;
	private String generalMLIFinancial;
	private String generalMLIBank;
	private String generalMLIRemark;
	private String creditsupportforanyotherproject;
	private String promoterunderwatchListofCGTMSE;
	// MLI advise placing,Does the MLI propose

	private BigDecimal landSection;
	private String landDataOfNPA;
	private String landNetWorth;
	private String landDataOfClaim;

	private BigDecimal buildingSection;
	private String buildingDateOfNpa;
	private String buildingNetWorth;
	private String buildingDateOfClaim;

	private String plantMachinerySection;
	private String plantMachineryDateNpa;
	private String plantMachineryNetWorth;
	private String plantMachineryClaim;

	private String otherFixedMovableSection;
	private String otherFixedMovableDateOfNpa;
	private String otherFixedMovableNetWorth;
	private String otherFixedMovableClaim;

	private String currentAssetSection;
	private String currentAssetDateOfNpa;
	private String currentAssetNetWorth;
	private String currentAssetClaim;

	private String otherSection;
	private String otherNpa;
	private String otherNetWorth;
	private String otherClaim;

	private BigDecimal dateOfSectionTotal;
	private BigDecimal dateOfNpaTotal;
	private BigDecimal netWorthTotal;
	private BigDecimal dateOfClaimTotal;

	private String landReason;
	private String buildingReason;
	private String plantMachineryReason;
	private String otherFixedReason;
	private String currentAssetReson;
	private String otherReason;

	private String reproduction;
	private ArrayList reproductionValue;

	private String checkList1;
	private String checkList2;
	private String checkList3;
	private String checkList4;
	private String checkList5;

	private String checkList6;
	private String checkList7;
	private String checkList8;
	private String checkList9;
	private String checkList10;
	private String checkList11;

	private String checkerReturn;

	private String checkerApprove;

	
	public String getLandDataOfNPA() {
		return landDataOfNPA;
	}

	public void setLandDataOfNPA(String landDataOfNPA) {
		this.landDataOfNPA = landDataOfNPA;
	}

	public String getLandDataOfClaim() {
		return landDataOfClaim;
	}

	public void setLandDataOfClaim(String landDataOfClaim) {
		this.landDataOfClaim = landDataOfClaim;
	}

	public String getBuildingDateOfNpa() {
		return buildingDateOfNpa;
	}

	public void setBuildingDateOfNpa(String buildingDateOfNpa) {
		this.buildingDateOfNpa = buildingDateOfNpa;
	}

	public String getBuildingNetWorth() {
		return buildingNetWorth;
	}

	public void setBuildingNetWorth(String buildingNetWorth) {
		this.buildingNetWorth = buildingNetWorth;
	}

	public String getBuildingDateOfClaim() {
		return buildingDateOfClaim;
	}

	public void setBuildingDateOfClaim(String buildingDateOfClaim) {
		this.buildingDateOfClaim = buildingDateOfClaim;
	}

	public String getPlantMachinerySection() {
		return plantMachinerySection;
	}

	public void setPlantMachinerySection(String plantMachinerySection) {
		this.plantMachinerySection = plantMachinerySection;
	}

	public String getPlantMachineryDateNpa() {
		return plantMachineryDateNpa;
	}

	public void setPlantMachineryDateNpa(String plantMachineryDateNpa) {
		this.plantMachineryDateNpa = plantMachineryDateNpa;
	}

	public String getPlantMachineryNetWorth() {
		return plantMachineryNetWorth;
	}

	public void setPlantMachineryNetWorth(String plantMachineryNetWorth) {
		this.plantMachineryNetWorth = plantMachineryNetWorth;
	}

	public String getPlantMachineryClaim() {
		return plantMachineryClaim;
	}

	public void setPlantMachineryClaim(String plantMachineryClaim) {
		this.plantMachineryClaim = plantMachineryClaim;
	}

	public String getOtherFixedMovableSection() {
		return otherFixedMovableSection;
	}

	public void setOtherFixedMovableSection(String otherFixedMovableSection) {
		this.otherFixedMovableSection = otherFixedMovableSection;
	}

	public String getOtherFixedMovableDateOfNpa() {
		return otherFixedMovableDateOfNpa;
	}

	public void setOtherFixedMovableDateOfNpa(String otherFixedMovableDateOfNpa) {
		this.otherFixedMovableDateOfNpa = otherFixedMovableDateOfNpa;
	}

	public String getOtherFixedMovableNetWorth() {
		return otherFixedMovableNetWorth;
	}

	public void setOtherFixedMovableNetWorth(String otherFixedMovableNetWorth) {
		this.otherFixedMovableNetWorth = otherFixedMovableNetWorth;
	}

	public String getOtherFixedMovableClaim() {
		return otherFixedMovableClaim;
	}

	public void setOtherFixedMovableClaim(String otherFixedMovableClaim) {
		this.otherFixedMovableClaim = otherFixedMovableClaim;
	}

	public String getCurrentAssetSection() {
		return currentAssetSection;
	}

	public void setCurrentAssetSection(String currentAssetSection) {
		this.currentAssetSection = currentAssetSection;
	}

	public String getCurrentAssetDateOfNpa() {
		return currentAssetDateOfNpa;
	}

	public void setCurrentAssetDateOfNpa(String currentAssetDateOfNpa) {
		this.currentAssetDateOfNpa = currentAssetDateOfNpa;
	}

	public String getCurrentAssetNetWorth() {
		return currentAssetNetWorth;
	}

	public void setCurrentAssetNetWorth(String currentAssetNetWorth) {
		this.currentAssetNetWorth = currentAssetNetWorth;
	}

	public String getCurrentAssetClaim() {
		return currentAssetClaim;
	}

	public void setCurrentAssetClaim(String currentAssetClaim) {
		this.currentAssetClaim = currentAssetClaim;
	}

	public String getOtherSection() {
		return otherSection;
	}

	public void setOtherSection(String otherSection) {
		this.otherSection = otherSection;
	}

	public String getOtherNpa() {
		return otherNpa;
	}

	public void setOtherNpa(String otherNpa) {
		this.otherNpa = otherNpa;
	}

	public String getOtherNetWorth() {
		return otherNetWorth;
	}

	public void setOtherNetWorth(String otherNetWorth) {
		this.otherNetWorth = otherNetWorth;
	}

	public String getOtherClaim() {
		return otherClaim;
	}

	public void setOtherClaim(String otherClaim) {
		this.otherClaim = otherClaim;
	}

	public String getCheckerReturn() {
		return checkerReturn;
	}

	public void setCheckerReturn(String checkerReturn) {
		this.checkerReturn = checkerReturn;
	}

	public String getCheckerApprove() {
		return checkerApprove;
	}

	public void setCheckerApprove(String checkerApprove) {
		this.checkerApprove = checkerApprove;
	}

	public String getLegalsuitFilingDate() {
		return legalsuitFilingDate;
	}

	public void setLegalsuitFilingDate(String legalsuitFilingDate) {
		this.legalsuitFilingDate = legalsuitFilingDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFilenameName() {
		return filenameName;
	}

	public void setFilenameName(String filenameName) {
		this.filenameName = filenameName;
	}

	public FormFile getNocDocument1() {
		return nocDocument1;
	}

	public void setNocDocument1(FormFile nocDocument1) {
		this.nocDocument1 = nocDocument1;
	}

	public BigDecimal getDateOfClaimTotal() {
		return dateOfClaimTotal;
	}

	public void setDateOfClaimTotal(BigDecimal dateOfClaimTotal) {
		this.dateOfClaimTotal = dateOfClaimTotal;
	}

	public ArrayList getLegalProceedingsValue() {
		return legalProceedingsValue;
	}

	public void setLegalProceedingsValue(ArrayList legalProceedingsValue) {
		this.legalProceedingsValue = legalProceedingsValue;
	}

	public String getLegalProceedings() {
		return legalProceedings;
	}

	public void setLegalProceedings(String legalProceedings) {
		this.legalProceedings = legalProceedings;
	}

	public String getCreditsupportforanyotherproject() {
		return creditsupportforanyotherproject;
	}

	public void setCreditsupportforanyotherproject(String creditsupportforanyotherproject) {
		this.creditsupportforanyotherproject = creditsupportforanyotherproject;
	}

	public String getPromoterunderwatchListofCGTMSE() {
		return promoterunderwatchListofCGTMSE;
	}

	public void setPromoterunderwatchListofCGTMSE(String promoterunderwatchListofCGTMSE) {
		this.promoterunderwatchListofCGTMSE = promoterunderwatchListofCGTMSE;
	}

	public String getRecoveryafterNPAindicated() {
		return recoveryafterNPAindicated;
	}

	public void setRecoveryafterNPAindicated(String recoveryafterNPAindicated) {
		this.recoveryafterNPAindicated = recoveryafterNPAindicated;
	}

	public String getValueasrecoveriesafterNPA() {
		return valueasrecoveriesafterNPA;
	}

	public void setValueasrecoveriesafterNPA(String valueasrecoveriesafterNPA) {
		this.valueasrecoveriesafterNPA = valueasrecoveriesafterNPA;
	}

	public String getDateofIssueofRecallNotice() {
		return dateofIssueofRecallNotice;
	}

	public void setDateofIssueofRecallNotice(String dateofIssueofRecallNotice) {
		this.dateofIssueofRecallNotice = dateofIssueofRecallNotice;
	}

	public String getDateofpossessionofassets() {
		return dateofpossessionofassets;
	}

	public void setDateofpossessionofassets(String dateofpossessionofassets) {
		this.dateofpossessionofassets = dateofpossessionofassets;
	}

	public String getTcTotalDisbursement() {
		return tcTotalDisbursement;
	}

	public void setTcTotalDisbursement(String tcTotalDisbursement) {
		this.tcTotalDisbursement = tcTotalDisbursement;
	}

	public String getTcDateofLastDisbursement() {
		return tcDateofLastDisbursement;
	}

	public void setTcDateofLastDisbursement(String tcDateofLastDisbursement) {
		this.tcDateofLastDisbursement = tcDateofLastDisbursement;
	}

	public String getTcPrincipalAmount() {
		return tcPrincipalAmount;
	}

	public void setTcPrincipalAmount(String tcPrincipalAmount) {
		this.tcPrincipalAmount = tcPrincipalAmount;
	}

	public String getTcRepaymentsInterest() {
		return tcRepaymentsInterest;
	}

	public void setTcRepaymentsInterest(String tcRepaymentsInterest) {
		this.tcRepaymentsInterest = tcRepaymentsInterest;
	}

	public String getTcOutstandingAmount() {
		return tcOutstandingAmount;
	}

	public void setTcOutstandingAmount(String tcOutstandingAmount) {
		this.tcOutstandingAmount = tcOutstandingAmount;
	}

	public String getTcOutstandingInterest() {
		return tcOutstandingInterest;
	}

	public void setTcOutstandingInterest(String tcOutstandingInterest) {
		this.tcOutstandingInterest = tcOutstandingInterest;
	}

	public String getTcOutstandingStatedCivil() {
		return tcOutstandingStatedCivil;
	}

	public void setTcOutstandingStatedCivil(String tcOutstandingStatedCivil) {
		this.tcOutstandingStatedCivil = tcOutstandingStatedCivil;
	}

	public String getTcOutstandingLodgement() {
		return tcOutstandingLodgement;
	}

	public void setTcOutstandingLodgement(String tcOutstandingLodgement) {
		this.tcOutstandingLodgement = tcOutstandingLodgement;
	}

	public String getTcAccountRestructed() {
		return tcAccountRestructed;
	}

	public void setTcAccountRestructed(String tcAccountRestructed) {
		this.tcAccountRestructed = tcAccountRestructed;
	}

	public String getRecoveryPrincipal() {
		return recoveryPrincipal;
	}

	public void setRecoveryPrincipal(String recoveryPrincipal) {
		this.recoveryPrincipal = recoveryPrincipal;
	}

	public String getRecoveryInterest() {
		return recoveryInterest;
	}

	public void setRecoveryInterest(String recoveryInterest) {
		this.recoveryInterest = recoveryInterest;
	}

	public String getRecoveryMode() {
		return recoveryMode;
	}

	public void setRecoveryMode(String recoveryMode) {
		this.recoveryMode = recoveryMode;
	}

	public String getPersonalAmountclaim() {
		return personalAmountclaim;
	}

	public void setPersonalAmountclaim(String personalAmountclaim) {
		this.personalAmountclaim = personalAmountclaim;
	}

	public String getGeneralMLIComment() {
		return generalMLIComment;
	}

	public void setGeneralMLIComment(String generalMLIComment) {
		this.generalMLIComment = generalMLIComment;
	}

	public String getGeneralMLIFinancial() {
		return generalMLIFinancial;
	}

	public void setGeneralMLIFinancial(String generalMLIFinancial) {
		this.generalMLIFinancial = generalMLIFinancial;
	}

	public String getGeneralMLIBank() {
		return generalMLIBank;
	}

	public void setGeneralMLIBank(String generalMLIBank) {
		this.generalMLIBank = generalMLIBank;
	}

	public String getGeneralMLIRemark() {
		return generalMLIRemark;
	}

	public void setGeneralMLIRemark(String generalMLIRemark) {
		this.generalMLIRemark = generalMLIRemark;
	}

	public String getLegalOtherForms() {
		return legalOtherForms;
	}

	public void setLegalOtherForms(String legalOtherForms) {
		this.legalOtherForms = legalOtherForms;
	}

	public String getLegalRegistration() {
		return legalRegistration;
	}

	public void setLegalRegistration(String legalRegistration) {
		this.legalRegistration = legalRegistration;
	}

	public String getLegalSatisfactoryRreason() {
		return legalSatisfactoryRreason;
	}

	public void setLegalSatisfactoryRreason(String legalSatisfactoryRreason) {
		this.legalSatisfactoryRreason = legalSatisfactoryRreason;
	}

	public String getLegalLocation() {
		return legalLocation;
	}

	public void setLegalLocation(String legalLocation) {
		this.legalLocation = legalLocation;
	}

	public Double getLegalAmountClaimed() {
		return legalAmountClaimed;
	}

	public void setLegalAmountClaimed(Double legalAmountClaimed) {
		this.legalAmountClaimed = legalAmountClaimed;
	}

	public String getAccountsClassified() {
		return accountsClassified;
	}

	public void setAccountsClassified(String accountsClassified) {
		this.accountsClassified = accountsClassified;
	}

	public String getAccountsEnquiry() {
		return accountsEnquiry;
	}

	public void setAccountsEnquiry(String accountsEnquiry) {
		this.accountsEnquiry = accountsEnquiry;
	}

	public String getAccountsMliReported() {
		return accountsMliReported;
	}

	public void setAccountsMliReported(String accountsMliReported) {
		this.accountsMliReported = accountsMliReported;
	}

	public String getAccountReasonTurning() {
		return accountReasonTurning;
	}

	public void setAccountReasonTurning(String accountReasonTurning) {
		this.accountReasonTurning = accountReasonTurning;
	}

	public String getAccountSatisfactoryReason() {
		return accountSatisfactoryReason;
	}

	public void setAccountSatisfactoryReason(String accountSatisfactoryReason) {
		this.accountSatisfactoryReason = accountSatisfactoryReason;
	}

	public String getnPADate() {
		return nPADate;
	}

	public void setnPADate(String nPADate) {
		this.nPADate = nPADate;
	}

	public String getAccountsWilFulDefaulter() {
		return accountsWilFulDefaulter;
	}

	public void setAccountsWilFulDefaulter(String accountsWilFulDefaulter) {
		this.accountsWilFulDefaulter = accountsWilFulDefaulter;
	}

	public String getBorrowerCity() {
		return borrowerCity;
	}

	public void setBorrowerCity(String borrowerCity) {
		this.borrowerCity = borrowerCity;
	}

	public String getBorrowerCompleteAddress() {
		return borrowerCompleteAddress;
	}

	public void setBorrowerCompleteAddress(String borrowerCompleteAddress) {
		this.borrowerCompleteAddress = borrowerCompleteAddress;
	}

	public String getBorrowerDistrict() {
		return borrowerDistrict;
	}

	public void setBorrowerDistrict(String borrowerDistrict) {
		this.borrowerDistrict = borrowerDistrict;
	}

	public String getBorrowerState() {
		return borrowerState;
	}

	public void setBorrowerState(String borrowerState) {
		this.borrowerState = borrowerState;
	}

	public String getBorrowerPinCode() {
		return borrowerPinCode;
	}

	public void setBorrowerPinCode(String borrowerPinCode) {
		this.borrowerPinCode = borrowerPinCode;
	}

	public String getBranchMemberId() {
		return branchMemberId;
	}

	public void setBranchMemberId(String branchMemberId) {
		this.branchMemberId = branchMemberId;
	}

	public String getBranchMliName() {
		return branchMliName;
	}

	public void setBranchMliName(String branchMliName) {
		this.branchMliName = branchMliName;
	}

	public String getBranchCity() {
		return branchCity;
	}

	public void setBranchCity(String branchCity) {
		this.branchCity = branchCity;
	}

	public String getBranchDistrict() {
		return branchDistrict;
	}

	public void setBranchDistrict(String branchDistrict) {
		this.branchDistrict = branchDistrict;
	}

	public String getBranchState() {
		return branchState;
	}

	public void setBranchState(String branchState) {
		this.branchState = branchState;
	}

	public String getBranchDealingOfficerName() {
		return branchDealingOfficerName;
	}

	public void setBranchDealingOfficerName(String branchDealingOfficerName) {
		this.branchDealingOfficerName = branchDealingOfficerName;
	}

	public String getBranchGstNo() {
		return branchGstNo;
	}

	public void setBranchGstNo(String branchGstNo) {
		this.branchGstNo = branchGstNo;
	}

	public String getPromoterNameValuee() {
		return promoterNameValuee;
	}

	public void setPromoterNameValuee(String promoterNameValuee) {
		this.promoterNameValuee = promoterNameValuee;
	}

	public String getPromoterUnitName() {
		return promoterUnitName;
	}

	public void setPromoterUnitName(String promoterUnitName) {
		this.promoterUnitName = promoterUnitName;
	}

	public double getPromoterGuranteeAmount() {
		return promoterGuranteeAmount;
	}

	public void setPromoterGuranteeAmount(double promoterGuranteeAmount) {
		this.promoterGuranteeAmount = promoterGuranteeAmount;
	}

	public String getPromoterSactionDate() {
		return promoterSactionDate;
	}

	public void setPromoterSactionDate(String promoterSactionDate) {
		this.promoterSactionDate = promoterSactionDate;
	}

	public String getPromoterGuaranteeStartDate() {
		return promoterGuaranteeStartDate;
	}

	public void setPromoterGuaranteeStartDate(String promoterGuaranteeStartDate) {
		this.promoterGuaranteeStartDate = promoterGuaranteeStartDate;
	}

	public BigDecimal getLandSection() {
		return landSection;
	}

	public void setLandSection(BigDecimal landSection) {
		this.landSection = landSection;
	}

	public BigDecimal getBuildingSection() {
		return buildingSection;
	}

	public void setBuildingSection(BigDecimal buildingSection) {
		this.buildingSection = buildingSection;
	}

	public String getLandNetWorth() {
		return landNetWorth;
	}

	public void setLandNetWorth(String landNetWorth) {
		this.landNetWorth = landNetWorth;
	}

	public BigDecimal getDateOfSectionTotal() {
		return dateOfSectionTotal;
	}

	public void setDateOfSectionTotal(BigDecimal dateOfSectionTotal) {
		this.dateOfSectionTotal = dateOfSectionTotal;
	}

	public BigDecimal getDateOfNpaTotal() {
		return dateOfNpaTotal;
	}

	public void setDateOfNpaTotal(BigDecimal dateOfNpaTotal) {
		this.dateOfNpaTotal = dateOfNpaTotal;
	}

	public BigDecimal getNetWorthTotal() {
		return netWorthTotal;
	}

	public void setNetWorthTotal(BigDecimal netWorthTotal) {
		this.netWorthTotal = netWorthTotal;
	}

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

	public String getReproduction() {
		return reproduction;
	}

	public void setReproduction(String reproduction) {
		this.reproduction = reproduction;
	}

	public ArrayList getReproductionValue() {
		return reproductionValue;
	}

	public void setReproductionValue(ArrayList reproductionValue) {
		this.reproductionValue = reproductionValue;
	}

	public ArrayList getPromoterValues() {
		return promoterValues;
	}

	public void setPromoterValues(ArrayList promoterValues) {
		this.promoterValues = promoterValues;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPromoterNamee() {
		return promoterNamee;
	}

	public void setPromoterNamee(String promoterNamee) {
		this.promoterNamee = promoterNamee;
	}

	public String getCgpan2() {
		return cgpan2;
	}

	public void setCgpan2(String cgpan2) {
		this.cgpan2 = cgpan2;

	}

	public String getPromoterNameValue() {
		return promoterNameValue;
	}

	public void setPromoterNameValue(String promoterNameValue) {
		this.promoterNameValue = promoterNameValue;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Map<String, String> getPromoterNameList() {
		return promoterNameList;
	}

	public void setPromoterNameList(Map<String, String> promoterNameList) {
		this.promoterNameList = promoterNameList;
	}

	public String getIsSearchClicked() {
		return isSearchClicked;
	}

	public void setIsSearchClicked(String isSearchClicked) {
		this.isSearchClicked = isSearchClicked;
	}

	public String getCheckList1() {
		return checkList1;
	}

	public void setCheckList1(String checkList1) {
		this.checkList1 = checkList1;
	}

	public String getCheckList2() {
		return checkList2;
	}

	public void setCheckList2(String checkList2) {
		this.checkList2 = checkList2;
	}

	public String getCheckList3() {
		return checkList3;
	}

	public void setCheckList3(String checkList3) {
		this.checkList3 = checkList3;
	}

	public String getCheckList4() {
		return checkList4;
	}

	public void setCheckList4(String checkList4) {
		this.checkList4 = checkList4;
	}

	public String getCheckList5() {
		return checkList5;
	}

	public void setCheckList5(String checkList5) {
		this.checkList5 = checkList5;
	}

	public String getCheckList6() {
		return checkList6;
	}

	public void setCheckList6(String checkList6) {
		this.checkList6 = checkList6;
	}

	public String getCheckList7() {
		return checkList7;
	}

	public void setCheckList7(String checkList7) {
		this.checkList7 = checkList7;
	}

	public String getCheckList8() {
		return checkList8;
	}

	public void setCheckList8(String checkList8) {
		this.checkList8 = checkList8;
	}

	public String getCheckList9() {
		return checkList9;
	}

	public void setCheckList9(String checkList9) {
		this.checkList9 = checkList9;
	}

	public String getCheckList10() {
		return checkList10;
	}

	public void setCheckList10(String checkList10) {
		this.checkList10 = checkList10;
	}

	public String getCheckList11() {
		return checkList11;
	}

	public void setCheckList11(String checkList11) {
		this.checkList11 = checkList11;
	}

}
