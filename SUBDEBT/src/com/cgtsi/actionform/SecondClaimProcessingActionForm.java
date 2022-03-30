package com.cgtsi.actionform;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;

import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorActionForm;

public class SecondClaimProcessingActionForm extends ValidatorActionForm{
	/**
 * 
 */
private static final long serialVersionUID = 1L;

private String promoterNamee;
private String cgpan2;

private String promoterNameValue;
private String role;
private String userId;
private Map<String,String>promoterNameList;
private String isSearchClicked;
private String message;
private ArrayList promoterValues;

private String claimReferenceNumber;
private String applicationReferenceNumber;
private String claimRefNumber;



private String cGPan;
private String pmarCgpan;
private String unitName;
private String typeofEntity;
private String constitution;
private double guaranteeAmount;
private String guaranteeStartDate;
private String lastDisbursementDate;
private double sanctionAmount;
private String sanctionDate;

private String iTPAN;
private String adharNumber;
private String investedEquity;
private String investedDebtUnsecuredLoan;
private Long total;
private String nPADate;
private String promoterNameValuee;



private double firstInstallmentsSettledAmount;
private double recoveryAmount;
private String typeOfRecovery;
private double  netOutstandingAmount;
private double  claimEligibleAmount;
private double firstInstallmentPayAmount;
private String legalForumAsPerFirstClaim;






public double getFirstInstallmentsSettledAmount() {
	return firstInstallmentsSettledAmount;
}

public void setFirstInstallmentsSettledAmount(double firstInstallmentsSettledAmount) {
	this.firstInstallmentsSettledAmount = firstInstallmentsSettledAmount;
}

public double getRecoveryAmount() {
	return recoveryAmount;
}

public void setRecoveryAmount(double recoveryAmount) {
	this.recoveryAmount = recoveryAmount;
}

public String getTypeOfRecovery() {
	return typeOfRecovery;
}

public void setTypeOfRecovery(String typeOfRecovery) {
	this.typeOfRecovery = typeOfRecovery;
}

public double getNetOutstandingAmount() {
	return netOutstandingAmount;
}

public void setNetOutstandingAmount(double netOutstandingAmount) {
	this.netOutstandingAmount = netOutstandingAmount;
}

public double getClaimEligibleAmount() {
	return claimEligibleAmount;
}

public void setClaimEligibleAmount(double claimEligibleAmount) {
	this.claimEligibleAmount = claimEligibleAmount;
}

public double getFirstInstallmentPayAmount() {
	return firstInstallmentPayAmount;
}

public void setFirstInstallmentPayAmount(double firstInstallmentPayAmount) {
	this.firstInstallmentPayAmount = firstInstallmentPayAmount;
}

public String getLegalForumAsPerFirstClaim() {
	return legalForumAsPerFirstClaim;
}

public void setLegalForumAsPerFirstClaim(String legalForumAsPerFirstClaim) {
	this.legalForumAsPerFirstClaim = legalForumAsPerFirstClaim;
}

public String getcGPan() {
	return cGPan;
}

public void setcGPan(String cGPan) {
	this.cGPan = cGPan;
}

public String getPmarCgpan() {
	return pmarCgpan;
}

public void setPmarCgpan(String pmarCgpan) {
	this.pmarCgpan = pmarCgpan;
}

public String getUnitName() {
	return unitName;
}

public void setUnitName(String unitName) {
	this.unitName = unitName;
}

public String getTypeofEntity() {
	return typeofEntity;
}

public void setTypeofEntity(String typeofEntity) {
	this.typeofEntity = typeofEntity;
}

public String getConstitution() {
	return constitution;
}

public void setConstitution(String constitution) {
	this.constitution = constitution;
}



public double getGuaranteeAmount() {
	return guaranteeAmount;
}

public void setGuaranteeAmount(double guaranteeAmount) {
	this.guaranteeAmount = guaranteeAmount;
}

public String getGuaranteeStartDate() {
	return guaranteeStartDate;
}

public void setGuaranteeStartDate(String guaranteeStartDate) {
	this.guaranteeStartDate = guaranteeStartDate;
}

public String getLastDisbursementDate() {
	return lastDisbursementDate;
}

public void setLastDisbursementDate(String lastDisbursementDate) {
	this.lastDisbursementDate = lastDisbursementDate;
}



public double getSanctionAmount() {
	return sanctionAmount;
}

public void setSanctionAmount(double sanctionAmount) {
	this.sanctionAmount = sanctionAmount;
}

public String getSanctionDate() {
	return sanctionDate;
}

public void setSanctionDate(String sanctionDate) {
	this.sanctionDate = sanctionDate;
}

public String getiTPAN() {
	return iTPAN;
}

public void setiTPAN(String iTPAN) {
	this.iTPAN = iTPAN;
}

public String getAdharNumber() {
	return adharNumber;
}

public void setAdharNumber(String adharNumber) {
	this.adharNumber = adharNumber;
}

public String getInvestedEquity() {
	return investedEquity;
}

public void setInvestedEquity(String investedEquity) {
	this.investedEquity = investedEquity;
}

public String getInvestedDebtUnsecuredLoan() {
	return investedDebtUnsecuredLoan;
}

public void setInvestedDebtUnsecuredLoan(String investedDebtUnsecuredLoan) {
	this.investedDebtUnsecuredLoan = investedDebtUnsecuredLoan;
}

public Long getTotal() {
	return total;
}

public void setTotal(Long total) {
	this.total = total;
}

private String promoterUnitName;
private double promoterGuranteeAmount;
private String promoterSactionDate;
private String promoterGuaranteeStartDate;


//Branch Details

private String branchMemberId;
private String branchMliName;
private String branchCity;
private String branchDistrict;
private String branchState;
private String branchDealingOfficerName;
private String branchGstNo;

//Borrower Details

private String borrowerCompleteAddress;
private String borrowerCity;
private String borrowerDistrict;
private String borrowerState;
private String borrowerPinCode;

//Account Details


private String accountsWilFulDefaulter;
private String accountsClassified;
private String accountsEnquiry;
private String accountsMliReported;
private String accountReasonTurning;
private String accountSatisfactoryReason;
private String dateofIssueofRecallNotice;

//Legal Section

private String legalOtherForms;
private String legalRegistration;
private String legalSatisfactoryRreason;
private String legalsuitFilingDate;
private String legalLocation;
private Double legalAmountClaimed;
private String dateofpossessionofassets;
private String legalProceedings;
private ArrayList legalProceedingsValue;
private String  filenameName;
private FormFile nocDocument1;
//private File file;
//attachment,


//Term Loans (TC)


private String tcTotalDisbursement;
private String tcDateofLastDisbursement;
private String tcPrincipalAmount;
private String tcRepaymentsInterest;
private String tcOutstandingAmount;
private String tcOutstandingInterest;
private String tcOutstandingStatedCivil;
private String tcOutstandingLodgement;
private String tcAccountRestructed;


//Recovery Details

private String recoveryPrincipal;
private String recoveryInterest;
private String recoveryMode;

//Security and Personal Guarantee
private String personalAmountclaim;
private String recoveryafterNPAindicated;
private String valueasrecoveriesafterNPA;


//general Information
private String generalMLIComment;
private String generalMLIFinancial;
private String generalMLIBank;
private String generalMLIRemark;
private String creditsupportforanyotherproject;
private String promoterunderwatchListofCGTMSE;
//MLI advise placing,Does the MLI propose 



private BigDecimal landSection;
private double landDataOfNPA;
private double landNetWorth;
private double landDataOfClaim;


private BigDecimal buildingSection;
private double buildingDateOfNpa;
private double buildingNetWorth;
private double buildingDateOfClaim;

private double plantMachinerySection;
private double plantMachineryDateNpa;
private double plantMachineryNetWorth;
private double plantMachineryClaim;

private double otherFixedMovableSection;
private double otherFixedMovableDateOfNpa;
private double otherFixedMovableNetWorth;
private double otherFixedMovableClaim;

private double currentAssetSection;
private double currentAssetDateOfNpa;
private double currentAssetNetWorth;
private double currentAssetClaim;

private double otherSection;
private double otherNpa;
private double otherNetWorth;
private double otherClaim;

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








public String getClaimReferenceNumber() {
	return claimReferenceNumber;
}

public void setClaimReferenceNumber(String claimReferenceNumber) {
	this.claimReferenceNumber = claimReferenceNumber;
}

public String getApplicationReferenceNumber() {
	return applicationReferenceNumber;
}

public void setApplicationReferenceNumber(String applicationReferenceNumber) {
	this.applicationReferenceNumber = applicationReferenceNumber;
}

public String getClaimRefNumber() {
	return claimRefNumber;
}

public void setClaimRefNumber(String claimRefNumber) {
	this.claimRefNumber = claimRefNumber;
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

public String getUserId() {
	return userId;
}

public void setUserId(String userId) {
	this.userId = userId;
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

public String getMessage() {
	return message;
}

public void setMessage(String message) {
	this.message = message;
}

public ArrayList getPromoterValues() {
	return promoterValues;
}

public void setPromoterValues(ArrayList promoterValues) {
	this.promoterValues = promoterValues;
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

public String getBorrowerCompleteAddress() {
	return borrowerCompleteAddress;
}

public void setBorrowerCompleteAddress(String borrowerCompleteAddress) {
	this.borrowerCompleteAddress = borrowerCompleteAddress;
}

public String getBorrowerCity() {
	return borrowerCity;
}

public void setBorrowerCity(String borrowerCity) {
	this.borrowerCity = borrowerCity;
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

public String getDateofIssueofRecallNotice() {
	return dateofIssueofRecallNotice;
}

public void setDateofIssueofRecallNotice(String dateofIssueofRecallNotice) {
	this.dateofIssueofRecallNotice = dateofIssueofRecallNotice;
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

public String getLegalsuitFilingDate() {
	return legalsuitFilingDate;
}

public void setLegalsuitFilingDate(String legalsuitFilingDate) {
	this.legalsuitFilingDate = legalsuitFilingDate;
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

public String getDateofpossessionofassets() {
	return dateofpossessionofassets;
}

public void setDateofpossessionofassets(String dateofpossessionofassets) {
	this.dateofpossessionofassets = dateofpossessionofassets;
}

public String getLegalProceedings() {
	return legalProceedings;
}

public void setLegalProceedings(String legalProceedings) {
	this.legalProceedings = legalProceedings;
}

public ArrayList getLegalProceedingsValue() {
	return legalProceedingsValue;
}

public void setLegalProceedingsValue(ArrayList legalProceedingsValue) {
	this.legalProceedingsValue = legalProceedingsValue;
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

public BigDecimal getLandSection() {
	return landSection;
}

public void setLandSection(BigDecimal landSection) {
	this.landSection = landSection;
}

public double getLandDataOfNPA() {
	return landDataOfNPA;
}

public void setLandDataOfNPA(double landDataOfNPA) {
	this.landDataOfNPA = landDataOfNPA;
}

public double getLandNetWorth() {
	return landNetWorth;
}

public void setLandNetWorth(double landNetWorth) {
	this.landNetWorth = landNetWorth;
}

public double getLandDataOfClaim() {
	return landDataOfClaim;
}

public void setLandDataOfClaim(double landDataOfClaim) {
	this.landDataOfClaim = landDataOfClaim;
}

public BigDecimal getBuildingSection() {
	return buildingSection;
}

public void setBuildingSection(BigDecimal buildingSection) {
	this.buildingSection = buildingSection;
}

public double getBuildingDateOfNpa() {
	return buildingDateOfNpa;
}

public void setBuildingDateOfNpa(double buildingDateOfNpa) {
	this.buildingDateOfNpa = buildingDateOfNpa;
}

public double getBuildingNetWorth() {
	return buildingNetWorth;
}

public void setBuildingNetWorth(double buildingNetWorth) {
	this.buildingNetWorth = buildingNetWorth;
}

public double getBuildingDateOfClaim() {
	return buildingDateOfClaim;
}

public void setBuildingDateOfClaim(double buildingDateOfClaim) {
	this.buildingDateOfClaim = buildingDateOfClaim;
}

public double getPlantMachinerySection() {
	return plantMachinerySection;
}

public void setPlantMachinerySection(double plantMachinerySection) {
	this.plantMachinerySection = plantMachinerySection;
}

public double getPlantMachineryDateNpa() {
	return plantMachineryDateNpa;
}

public void setPlantMachineryDateNpa(double plantMachineryDateNpa) {
	this.plantMachineryDateNpa = plantMachineryDateNpa;
}

public double getPlantMachineryNetWorth() {
	return plantMachineryNetWorth;
}

public void setPlantMachineryNetWorth(double plantMachineryNetWorth) {
	this.plantMachineryNetWorth = plantMachineryNetWorth;
}

public double getPlantMachineryClaim() {
	return plantMachineryClaim;
}

public void setPlantMachineryClaim(double plantMachineryClaim) {
	this.plantMachineryClaim = plantMachineryClaim;
}

public double getOtherFixedMovableSection() {
	return otherFixedMovableSection;
}

public void setOtherFixedMovableSection(double otherFixedMovableSection) {
	this.otherFixedMovableSection = otherFixedMovableSection;
}

public double getOtherFixedMovableDateOfNpa() {
	return otherFixedMovableDateOfNpa;
}

public void setOtherFixedMovableDateOfNpa(double otherFixedMovableDateOfNpa) {
	this.otherFixedMovableDateOfNpa = otherFixedMovableDateOfNpa;
}

public double getOtherFixedMovableNetWorth() {
	return otherFixedMovableNetWorth;
}

public void setOtherFixedMovableNetWorth(double otherFixedMovableNetWorth) {
	this.otherFixedMovableNetWorth = otherFixedMovableNetWorth;
}

public double getOtherFixedMovableClaim() {
	return otherFixedMovableClaim;
}

public void setOtherFixedMovableClaim(double otherFixedMovableClaim) {
	this.otherFixedMovableClaim = otherFixedMovableClaim;
}

public double getCurrentAssetSection() {
	return currentAssetSection;
}

public void setCurrentAssetSection(double currentAssetSection) {
	this.currentAssetSection = currentAssetSection;
}

public double getCurrentAssetDateOfNpa() {
	return currentAssetDateOfNpa;
}

public void setCurrentAssetDateOfNpa(double currentAssetDateOfNpa) {
	this.currentAssetDateOfNpa = currentAssetDateOfNpa;
}

public double getCurrentAssetNetWorth() {
	return currentAssetNetWorth;
}

public void setCurrentAssetNetWorth(double currentAssetNetWorth) {
	this.currentAssetNetWorth = currentAssetNetWorth;
}

public double getCurrentAssetClaim() {
	return currentAssetClaim;
}

public void setCurrentAssetClaim(double currentAssetClaim) {
	this.currentAssetClaim = currentAssetClaim;
}

public double getOtherSection() {
	return otherSection;
}

public void setOtherSection(double otherSection) {
	this.otherSection = otherSection;
}

public double getOtherNpa() {
	return otherNpa;
}

public void setOtherNpa(double otherNpa) {
	this.otherNpa = otherNpa;
}

public double getOtherNetWorth() {
	return otherNetWorth;
}

public void setOtherNetWorth(double otherNetWorth) {
	this.otherNetWorth = otherNetWorth;
}

public double getOtherClaim() {
	return otherClaim;
}

public void setOtherClaim(double otherClaim) {
	this.otherClaim = otherClaim;
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

public BigDecimal getDateOfClaimTotal() {
	return dateOfClaimTotal;
}

public void setDateOfClaimTotal(BigDecimal dateOfClaimTotal) {
	this.dateOfClaimTotal = dateOfClaimTotal;
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




}
