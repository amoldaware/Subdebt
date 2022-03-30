package com.cgtsi.actionform;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;

import org.apache.struts.validator.ValidatorActionForm;
import org.json.JSONObject;

public class NpaMarkingActionForm extends ValidatorActionForm{
	/**
 * 
 */
private static final long serialVersionUID = 1L;

private String promoterName1;
private String promoterNameValue;
private Map<String,String>promoterNameList;
private String cgpan1;
private String role;

private ArrayList promoterValues;

private String reason;
private ArrayList reasonvalue;

public String getPromoterName1() {
	return promoterName1;
}
public void setPromoterName1(String promoterName1) {
	this.promoterName1 = promoterName1;
}
public String getCgpan1() {
	return cgpan1;
}
public void setCgpan1(String cgpan1) {
	this.cgpan1 = cgpan1;
}
private String creditRisk;
private ArrayList creditRiskvalue;

private String reproduction;
private ArrayList reproductionValue;



private String isSearchClicked;
private String referanceNo;
private String cgpansearch;
private String pmarCgpan;
private String unitName;
private String typeofEntity;
private String contribution;
private double guaranteeAmount;
private String guaratneeStartDate;
private String lastDisbursementDate;
private String sanctionAmount;
private String sanctionDate;
private String interestRate;

private String promoterNamee;
private String itPAN;
private String adharNumber;
private String investedEquity;
private String investedasdebtunsecuredloan;
private Long total;
private String guaratneeStartDate1;
private double guaranteeAmount1;


private double prinicipleRepayment;
private String interestRepayment;
private String principleOutstanding;
private String interestOutstanding;
private String checkBoxVal;

private BigDecimal landSection;
private String landDataOfNPA;
private String landNetWorth;

private BigDecimal buildingSection;
private String buildingDateOfNpa;
private String buildingNetWorth;

private String plantMachinerySection;
private String plantMachineryDateNpa;
private String plantMachineryNetWorth;

private String otherFixedMovableSection;
private String otherFixedMovableDateOfNpa;
private String otherFixedMovableNetWorth;

private String currentAssetSection;
private String currentAssetDateOfNpa;
private String currentAssetNetWorth;

private String otherSection;
private String otherNpa;
private String otherNetWorth;

private BigDecimal dateOfSectionTotal;
private BigDecimal dateOfNpaTotal;
private BigDecimal netWorthTotal;

private String date;
private String npaDate;


private String landReason;
private String buildingReason;
private String plantMachineryReason;
private String otherFixedReason;
private String currentAssetReson;
private String otherReason;

private String message;

private ArrayList npaPopulateData;


public String getLandDataOfNPA() {
	return landDataOfNPA;
}
public void setLandDataOfNPA(String landDataOfNPA) {
	this.landDataOfNPA = landDataOfNPA;
}
public String getLandNetWorth() {
	return landNetWorth;
}
public void setLandNetWorth(String landNetWorth) {
	this.landNetWorth = landNetWorth;
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
public ArrayList getNpaPopulateData() {
	return npaPopulateData;
}
public void setNpaPopulateData(ArrayList npaPopulateData) {
	this.npaPopulateData = npaPopulateData;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public String getRole() {
	return role;
}
public void setRole(String role) {
	this.role = role;
}
public String getNpaDate() {
	return npaDate;
}
public void setNpaDate(String npaDate) {
	this.npaDate = npaDate;
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
public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
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
public String getCreditRisk() {
	return creditRisk;
}
public void setCreditRisk(String creditRisk) {
	this.creditRisk = creditRisk;
}
public ArrayList getCreditRiskvalue() {
	return creditRiskvalue;
}
public void setCreditRiskvalue(ArrayList creditRiskvalue) {
	this.creditRiskvalue = creditRiskvalue;
}
public String getReason() {
	return reason;
}
public void setReason(String reason) {
	this.reason = reason;
}
public ArrayList getReasonvalue() {
	return reasonvalue;
}
public void setReasonvalue(ArrayList reasonvalue) {
	this.reasonvalue = reasonvalue;
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
public BigDecimal getBuildingSection() {
	return buildingSection;
}
public void setBuildingSection(BigDecimal buildingSection) {
	this.buildingSection = buildingSection;
}
public BigDecimal getLandSection() {
	return landSection;
}
public void setLandSection(BigDecimal landSection) {
	this.landSection = landSection;
}
public String getCheckBoxVal() {
	return checkBoxVal;
}
public void setCheckBoxVal(String checkBoxVal) {
	this.checkBoxVal = checkBoxVal;
}

public double getPrinicipleRepayment() {
	return prinicipleRepayment;
}
public void setPrinicipleRepayment(double prinicipleRepayment) {
	this.prinicipleRepayment = prinicipleRepayment;
}
public String getInterestRepayment() {
	return interestRepayment;
}
public void setInterestRepayment(String interestRepayment) {
	this.interestRepayment = interestRepayment;
}
public String getPrincipleOutstanding() {
	return principleOutstanding;
}
public void setPrincipleOutstanding(String principleOutstanding) {
	this.principleOutstanding = principleOutstanding;
}
public String getInterestOutstanding() {
	return interestOutstanding;
}
public void setInterestOutstanding(String interestOutstanding) {
	this.interestOutstanding = interestOutstanding;
}




public Long getTotal() {
	return total;
}
public void setTotal(Long total) {
	this.total = total;
}
public String getPromoterNamee() {
	return promoterNamee;
}
public void setPromoterNamee(String promoterNamee) {
	this.promoterNamee = promoterNamee;
}
public String getItPAN() {
	return itPAN;
}
public void setItPAN(String itPAN) {
	this.itPAN = itPAN;
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
public String getInvestedasdebtunsecuredloan() {
	return investedasdebtunsecuredloan;
}
public void setInvestedasdebtunsecuredloan(String investedasdebtunsecuredloan) {
	this.investedasdebtunsecuredloan = investedasdebtunsecuredloan;
}
public String getGuaratneeStartDate1() {
	return guaratneeStartDate1;
}
public void setGuaratneeStartDate1(String guaratneeStartDate1) {
	this.guaratneeStartDate1 = guaratneeStartDate1;
}
public double getGuaranteeAmount1() {
	return guaranteeAmount1;
}
public void setGuaranteeAmount1(double guaranteeAmount1) {
	this.guaranteeAmount1 = guaranteeAmount1;
}
public String getCgpansearch() {
	return cgpansearch;
}
public void setCgpansearch(String cgpansearch) {
	this.cgpansearch = cgpansearch;
}
public double getGuaranteeAmount() {
	return guaranteeAmount;
}
public void setGuaranteeAmount(double guaranteeAmount) {
	this.guaranteeAmount = guaranteeAmount;
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
public String getContribution() {
	return contribution;
}
public void setContribution(String contribution) {
	this.contribution = contribution;
}

public String getGuaratneeStartDate() {
	return guaratneeStartDate;
}
public void setGuaratneeStartDate(String guaratneeStartDate) {
	this.guaratneeStartDate = guaratneeStartDate;
}
public String getLastDisbursementDate() {
	return lastDisbursementDate;
}
public void setLastDisbursementDate(String lastDisbursementDate) {
	this.lastDisbursementDate = lastDisbursementDate;
}
public String getSanctionAmount() {
	return sanctionAmount;
}
public void setSanctionAmount(String sanctionAmount) {
	this.sanctionAmount = sanctionAmount;
}
public String getSanctionDate() {
	return sanctionDate;
}
public void setSanctionDate(String sanctionDate) {
	this.sanctionDate = sanctionDate;
}
public String getInterestRate() {
	return interestRate;
}
public void setInterestRate(String interestRate) {
	this.interestRate = interestRate;
}
public String getReferanceNo() {
	return referanceNo;
}
public void setReferanceNo(String referanceNo) {
	this.referanceNo = referanceNo;
}
public String getIsSearchClicked() {
	return isSearchClicked;
}
public void setIsSearchClicked(String isSearchClicked) {
	this.isSearchClicked = isSearchClicked;
}
public ArrayList getPromoterValues() {
	return promoterValues;
}
public void setPromoterValues(ArrayList promoterValues) {
	this.promoterValues = promoterValues;
}
/*public String getCgpan() {
	return cgpan1;
}
public void setCgpan(String cgpan1) {
	this.cgpan1 = cgpan1;
}
public String getPromoterName1() {
	return promoterName1;
}
public void setPromoterName(String promoterName1) {
	this.promoterName1 = promoterName1;
}*/
public String getPromoterNameValue() {
	return promoterNameValue;
}
public void setPromoterNameValue(String promoterNameValue) {
	this.promoterNameValue = promoterNameValue;
}
public Map<String, String> getPromoterNameList() {
	return promoterNameList;
}
public void setPromoterNameList(Map<String, String> promoterNameList) {
	this.promoterNameList = promoterNameList;
}

private JSONObject myObj;

public JSONObject getMyObj() {
	return myObj;
}
public void setMyObj(JSONObject myObj) {
	this.myObj = myObj;
}

private double disbursementAmount;

public static long getSerialversionuid() {
	return serialVersionUID;
}
public double getDisbursementAmount() {
	return disbursementAmount;
}
public void setDisbursementAmount(double disbursementAmount) {
	this.disbursementAmount = disbursementAmount;
}
 
}
