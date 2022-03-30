package com.cgtsi.application;

import com.cgtsi.mcgs.MCGFDetails;
import java.io.Serializable;
import java.util.Date;

public class Application implements Serializable
{
  private String mliID;
  private String mliBranchName;
  private String mliBranchCode;
  private String exposurelmtAmt;
  
  private double sancAmt;
  private double outstandingAmt;
  private String accStatus;
  private double wcSancAmt;
  private double wcOutstandingAmt;
  private String wcAccStatus;
  private double otherSancAmt;
  private double otherOutstandingAmt;
  private String otherAccStatus;
  
  private double otherBanksancAmt;
  private double otherBankoutstandingAmt;
  private String otherBankaccStatus;
  private double otherBankwcSancAmt;
  private double otherBankwcOutstandingAmt;
  private String otherBankwcAccStatus;
  private double otherBankotherSancAmt;
  private double otherBankotherOutstandingAmt;
  private String otherBankotherAccStatus;
  
  private double totalSancAmt;
  private double totalOutstandingAmt; 
  private String otherBankName;
  
  private String npaReason;
  private String isAcctReconstructed;
  private double pmrEquity;
  private double pmrDebt;
  private double pmrStakeTotal;
  private double pmr15Percent;
  private double pmrMinTotal;
  private double presentSalesTurnover;
  
  private String facilityOthrtBnk;
  private String cgPan;    //Diksha
  private String  borrowerRefNo;
  private String constitution;
  private String constitutionOther;
  private String ssiType;
  private String ssiName;
  private String regNo;
  private Date commencementDate;
  private String ssiITPan;
  private String industryNature;
  private String industrySector;
  private String activityType;
  private String mudraType;
  private String mudraITPANChk;
  private int employeeNos;
  private double projectedSalesTurnover;
  private double projectedExports;
  private String address;
  private String state;
  private String district;
  private String city;
  private String pincode;
  private Date sanctionDate;
  private double disbursementAmt;
  private int noOfInstallment;
 

  //****Chief promoters details****
  private String cpTitle;
  private String cpFirstName;
  private String cpMiddleName;
  private String cpLastName;
  private String cpGender;
  private String cpITPAN;
  private Date cpDOB;
  private String cpLegalID;
  private String cpLegalIdValue;
  private String firstName;
  private String firstItpan;
  private Date firstDOB;
  private String secondName;
  private String secondItpan;
  private Date secondDOB;
  private String thirdName;
  private String thirdItpan;
  private Date thirdDOB;
  //added by sukumar@path for fixing the bug in funcInsertApplicationDetail
  private String enterprise;
  private String unitAssisted;
  private String conditionAccepted="Y";
  private String womenOperated;
  private String physicallyHandicapped;
 /*For MCGS*/
  
  private String cpFirstName1;
  private String cpLastName1;
  private String cpGender1;
  private String cpITPAN1;
  private Date cpDOB1;
  private String socialCategory1;
  private String email1;
  private String adhar1;
  private double pmrEquity1;
  private double pmrDebt1;
  
  private String cpFirstName2;
  private String cpLastName2;
  private String cpGender2;
  private String cpITPAN2;
  private Date cpDOB2;
  private String socialCategory2;
  private String email2;
  private String adhar2;
  private double pmrEquity2;
  private double pmrDebt2;
  
  private String cpFirstName3;
  private String cpLastName3;
  private String cpGender3;
  private String cpITPAN3;
  private Date cpDOB3;
  private String socialCategory3;
  private String email3;
  private String adhar3;
  private double pmrEquity3;
  private double pmrDebt3;
  
  
  private String cpFirstName4;
  private String cpLastName4;
  private String cpGender4;
  private String cpITPAN4;
  private Date cpDOB4;
  private String socialCategory4;
  private String email4;
  private String adhar4;
  private double pmrEquity4;
  private double pmrDebt4;
  
  
  private String cpFirstName5;
  private String cpLastName5;
  private String cpGender5;
  private String cpITPAN5;
  private Date cpDOB5;
  private String socialCategory5;
  private String email5;
  private String adhar5;
  private double pmrEquity5;
  private double pmrDebt5;
  
  
  private String cpFirstName6;
  private String cpLastName6;
  private String cpGender6;
  private String cpITPAN6;
  private Date cpDOB6;
  private String socialCategory6;
  private String email6;
  private String adhar6;
  private double pmrEquity6;
  private double pmrDebt6;
  
  
  private String cpFirstName7;
  private String cpLastName7;
  private String cpGender7;
  private String cpITPAN7;
  private Date cpDOB7;
  private String socialCategory7;
  private String email7;
  private String adhar7;
  private double pmrEquity7;
  private double pmrDebt7;
  
  
  private String cpFirstName8;
  private String cpLastName8;
  private String cpGender8;
  private String cpITPAN8;
  private Date cpDOB8;
  private String socialCategory8;
  private String email8;
  private String adhar8;
  private double pmrEquity8;
  private double pmrDebt8;
  
  
  private String cpFirstName9;
  private String cpLastName9;
  private String cpGender9;
  private String cpITPAN9;
  private Date cpDOB9;
  private String socialCategory9;
  private String email9;
  private String adhar9;
  private double pmrEquity9;
  private double pmrDebt9;
  
  
  private String cpFirstName10;
  private String cpLastName10;
  private String cpGender10;
  private String cpITPAN10;
  private Date cpDOB10;
  private String socialCategory10;
  private String email10;
  private String adhar10;
  private double pmrEquity10;
  private double pmrDebt10;
  
  private String securityRemarks;
 
	private double landValue;

	
	private double bldgValue;
	

	private double machineValue;

	
	private double assetsValue;
	
	
	private double currentAssetsValue;
		
	
	private double othersValue;
  
  
  
  

 public double getLandValue() {
		return landValue;
	}

	public void setLandValue(double landValue) {
		this.landValue = landValue;
	}

	public double getBldgValue() {
		return bldgValue;
	}

	public void setBldgValue(double bldgValue) {
		this.bldgValue = bldgValue;
	}

	public double getMachineValue() {
		return machineValue;
	}

	public void setMachineValue(double machineValue) {
		this.machineValue = machineValue;
	}

	public double getAssetsValue() {
		return assetsValue;
	}

	public void setAssetsValue(double assetsValue) {
		this.assetsValue = assetsValue;
	}

	public double getCurrentAssetsValue() {
		return currentAssetsValue;
	}

	public void setCurrentAssetsValue(double currentAssetsValue) {
		this.currentAssetsValue = currentAssetsValue;
	}

	public double getOthersValue() {
		return othersValue;
	}

	public void setOthersValue(double othersValue) {
		this.othersValue = othersValue;
	}

public String getSecurityRemarks() {
	return securityRemarks;
}

public void setSecurityRemarks(String securityRemarks) {
	this.securityRemarks = securityRemarks;
}

public Date getSanctionDate() {
	return sanctionDate;
}

public void setSanctionDate(Date sanctionDate) {
	this.sanctionDate = sanctionDate;
}

public double getDisbursementAmt() {
	return disbursementAmt;
}

public void setDisbursementAmt(double disbursementAmt) {
	this.disbursementAmt = disbursementAmt;
}

public int getNoOfInstallment() {
	return noOfInstallment;
}

public void setNoOfInstallment(int noOfInstallment) {
	this.noOfInstallment = noOfInstallment;
}

public String getCpFirstName1() {
	return cpFirstName1;
}

public void setCpFirstName1(String cpFirstName1) {
	this.cpFirstName1 = cpFirstName1;
}

public String getCpLastName1() {
	return cpLastName1;
}

public void setCpLastName1(String cpLastName1) {
	this.cpLastName1 = cpLastName1;
}

public String getCpGender1() {
	return cpGender1;
}

public void setCpGender1(String cpGender1) {
	this.cpGender1 = cpGender1;
}

public String getCpITPAN1() {
	return cpITPAN1;
}

public void setCpITPAN1(String cpITPAN1) {
	this.cpITPAN1 = cpITPAN1;
}

public Date getCpDOB1() {
	return cpDOB1;
}

public void setCpDOB1(Date cpDOB1) {
	this.cpDOB1 = cpDOB1;
}

public String getSocialCategory1() {
	return socialCategory1;
}

public void setSocialCategory1(String socialCategory1) {
	this.socialCategory1 = socialCategory1;
}

public String getEmail1() {
	return email1;
}

public void setEmail1(String email1) {
	this.email1 = email1;
}

public String getAdhar1() {
	return adhar1;
}

public void setAdhar1(String adhar1) {
	this.adhar1 = adhar1;
}

public double getPmrEquity1() {
	return pmrEquity1;
}

public void setPmrEquity1(double pmrEquity1) {
	this.pmrEquity1 = pmrEquity1;
}

public double getPmrDebt1() {
	return pmrDebt1;
}

public void setPmrDebt1(double pmrDebt1) {
	this.pmrDebt1 = pmrDebt1;
}

public String getCpFirstName2() {
	return cpFirstName2;
}

public void setCpFirstName2(String cpFirstName2) {
	this.cpFirstName2 = cpFirstName2;
}

public String getCpLastName2() {
	return cpLastName2;
}

public void setCpLastName2(String cpLastName2) {
	this.cpLastName2 = cpLastName2;
}

public String getCpGender2() {
	return cpGender2;
}

public void setCpGender2(String cpGender2) {
	this.cpGender2 = cpGender2;
}

public String getCpITPAN2() {
	return cpITPAN2;
}

public void setCpITPAN2(String cpITPAN2) {
	this.cpITPAN2 = cpITPAN2;
}

public Date getCpDOB2() {
	return cpDOB2;
}

public void setCpDOB2(Date cpDOB2) {
	this.cpDOB2 = cpDOB2;
}

public String getSocialCategory2() {
	return socialCategory2;
}

public void setSocialCategory2(String socialCategory2) {
	this.socialCategory2 = socialCategory2;
}

public String getEmail2() {
	return email2;
}

public void setEmail2(String email2) {
	this.email2 = email2;
}

public String getAdhar2() {
	return adhar2;
}

public void setAdhar2(String adhar2) {
	this.adhar2 = adhar2;
}

public double getPmrEquity2() {
	return pmrEquity2;
}

public void setPmrEquity2(double pmrEquity2) {
	this.pmrEquity2 = pmrEquity2;
}

public double getPmrDebt2() {
	return pmrDebt2;
}

public void setPmrDebt2(double pmrDebt2) {
	this.pmrDebt2 = pmrDebt2;
}

public String getCpFirstName3() {
	return cpFirstName3;
}

public void setCpFirstName3(String cpFirstName3) {
	this.cpFirstName3 = cpFirstName3;
}

public String getCpLastName3() {
	return cpLastName3;
}

public void setCpLastName3(String cpLastName3) {
	this.cpLastName3 = cpLastName3;
}

public String getCpGender3() {
	return cpGender3;
}

public void setCpGender3(String cpGender3) {
	this.cpGender3 = cpGender3;
}

public String getCpITPAN3() {
	return cpITPAN3;
}

public void setCpITPAN3(String cpITPAN3) {
	this.cpITPAN3 = cpITPAN3;
}

public Date getCpDOB3() {
	return cpDOB3;
}

public void setCpDOB3(Date cpDOB3) {
	this.cpDOB3 = cpDOB3;
}

public String getSocialCategory3() {
	return socialCategory3;
}

public void setSocialCategory3(String socialCategory3) {
	this.socialCategory3 = socialCategory3;
}

public String getEmail3() {
	return email3;
}

public void setEmail3(String email3) {
	this.email3 = email3;
}

public String getAdhar3() {
	return adhar3;
}

public void setAdhar3(String adhar3) {
	this.adhar3 = adhar3;
}

public double getPmrEquity3() {
	return pmrEquity3;
}

public void setPmrEquity3(double pmrEquity3) {
	this.pmrEquity3 = pmrEquity3;
}

public double getPmrDebt3() {
	return pmrDebt3;
}

public void setPmrDebt3(double pmrDebt3) {
	this.pmrDebt3 = pmrDebt3;
}

public String getCpFirstName4() {
	return cpFirstName4;
}

public void setCpFirstName4(String cpFirstName4) {
	this.cpFirstName4 = cpFirstName4;
}

public String getCpLastName4() {
	return cpLastName4;
}

public void setCpLastName4(String cpLastName4) {
	this.cpLastName4 = cpLastName4;
}

public String getCpGender4() {
	return cpGender4;
}

public void setCpGender4(String cpGender4) {
	this.cpGender4 = cpGender4;
}

public String getCpITPAN4() {
	return cpITPAN4;
}

public void setCpITPAN4(String cpITPAN4) {
	this.cpITPAN4 = cpITPAN4;
}

public Date getCpDOB4() {
	return cpDOB4;
}

public void setCpDOB4(Date cpDOB4) {
	this.cpDOB4 = cpDOB4;
}

public String getSocialCategory4() {
	return socialCategory4;
}

public void setSocialCategory4(String socialCategory4) {
	this.socialCategory4 = socialCategory4;
}

public String getEmail4() {
	return email4;
}

public void setEmail4(String email4) {
	this.email4 = email4;
}

public String getAdhar4() {
	return adhar4;
}

public void setAdhar4(String adhar4) {
	this.adhar4 = adhar4;
}

public double getPmrEquity4() {
	return pmrEquity4;
}

public void setPmrEquity4(double pmrEquity4) {
	this.pmrEquity4 = pmrEquity4;
}

public double getPmrDebt4() {
	return pmrDebt4;
}

public void setPmrDebt4(double pmrDebt4) {
	this.pmrDebt4 = pmrDebt4;
}

public String getCpFirstName5() {
	return cpFirstName5;
}

public void setCpFirstName5(String cpFirstName5) {
	this.cpFirstName5 = cpFirstName5;
}

public String getCpLastName5() {
	return cpLastName5;
}

public void setCpLastName5(String cpLastName5) {
	this.cpLastName5 = cpLastName5;
}

public String getCpGender5() {
	return cpGender5;
}

public void setCpGender5(String cpGender5) {
	this.cpGender5 = cpGender5;
}

public String getCpITPAN5() {
	return cpITPAN5;
}

public void setCpITPAN5(String cpITPAN5) {
	this.cpITPAN5 = cpITPAN5;
}

public Date getCpDOB5() {
	return cpDOB5;
}

public void setCpDOB5(Date cpDOB5) {
	this.cpDOB5 = cpDOB5;
}

public String getSocialCategory5() {
	return socialCategory5;
}

public void setSocialCategory5(String socialCategory5) {
	this.socialCategory5 = socialCategory5;
}

public String getEmail5() {
	return email5;
}

public void setEmail5(String email5) {
	this.email5 = email5;
}

public String getAdhar5() {
	return adhar5;
}

public void setAdhar5(String adhar5) {
	this.adhar5 = adhar5;
}

public double getPmrEquity5() {
	return pmrEquity5;
}

public void setPmrEquity5(double pmrEquity5) {
	this.pmrEquity5 = pmrEquity5;
}

public double getPmrDebt5() {
	return pmrDebt5;
}

public void setPmrDebt5(double pmrDebt5) {
	this.pmrDebt5 = pmrDebt5;
}

public String getCpFirstName6() {
	return cpFirstName6;
}

public void setCpFirstName6(String cpFirstName6) {
	this.cpFirstName6 = cpFirstName6;
}

public String getCpLastName6() {
	return cpLastName6;
}

public void setCpLastName6(String cpLastName6) {
	this.cpLastName6 = cpLastName6;
}

public String getCpGender6() {
	return cpGender6;
}

public void setCpGender6(String cpGender6) {
	this.cpGender6 = cpGender6;
}

public String getCpITPAN6() {
	return cpITPAN6;
}

public void setCpITPAN6(String cpITPAN6) {
	this.cpITPAN6 = cpITPAN6;
}

public Date getCpDOB6() {
	return cpDOB6;
}

public void setCpDOB6(Date cpDOB6) {
	this.cpDOB6 = cpDOB6;
}

public String getSocialCategory6() {
	return socialCategory6;
}

public void setSocialCategory6(String socialCategory6) {
	this.socialCategory6 = socialCategory6;
}

public String getEmail6() {
	return email6;
}

public void setEmail6(String email6) {
	this.email6 = email6;
}

public String getAdhar6() {
	return adhar6;
}

public void setAdhar6(String adhar6) {
	this.adhar6 = adhar6;
}

public double getPmrEquity6() {
	return pmrEquity6;
}

public void setPmrEquity6(double pmrEquity6) {
	this.pmrEquity6 = pmrEquity6;
}

public double getPmrDebt6() {
	return pmrDebt6;
}

public void setPmrDebt6(double pmrDebt6) {
	this.pmrDebt6 = pmrDebt6;
}

public String getCpFirstName7() {
	return cpFirstName7;
}

public void setCpFirstName7(String cpFirstName7) {
	this.cpFirstName7 = cpFirstName7;
}

public String getCpLastName7() {
	return cpLastName7;
}

public void setCpLastName7(String cpLastName7) {
	this.cpLastName7 = cpLastName7;
}

public String getCpGender7() {
	return cpGender7;
}

public void setCpGender7(String cpGender7) {
	this.cpGender7 = cpGender7;
}

public String getCpITPAN7() {
	return cpITPAN7;
}

public void setCpITPAN7(String cpITPAN7) {
	this.cpITPAN7 = cpITPAN7;
}

public Date getCpDOB7() {
	return cpDOB7;
}

public void setCpDOB7(Date cpDOB7) {
	this.cpDOB7 = cpDOB7;
}

public String getSocialCategory7() {
	return socialCategory7;
}

public void setSocialCategory7(String socialCategory7) {
	this.socialCategory7 = socialCategory7;
}

public String getEmail7() {
	return email7;
}

public void setEmail7(String email7) {
	this.email7 = email7;
}

public String getAdhar7() {
	return adhar7;
}

public void setAdhar7(String adhar7) {
	this.adhar7 = adhar7;
}

public double getPmrEquity7() {
	return pmrEquity7;
}

public void setPmrEquity7(double pmrEquity7) {
	this.pmrEquity7 = pmrEquity7;
}

public double getPmrDebt7() {
	return pmrDebt7;
}

public void setPmrDebt7(double pmrDebt7) {
	this.pmrDebt7 = pmrDebt7;
}

public String getCpFirstName8() {
	return cpFirstName8;
}

public void setCpFirstName8(String cpFirstName8) {
	this.cpFirstName8 = cpFirstName8;
}

public String getCpLastName8() {
	return cpLastName8;
}

public void setCpLastName8(String cpLastName8) {
	this.cpLastName8 = cpLastName8;
}

public String getCpGender8() {
	return cpGender8;
}

public void setCpGender8(String cpGender8) {
	this.cpGender8 = cpGender8;
}

public String getCpITPAN8() {
	return cpITPAN8;
}

public void setCpITPAN8(String cpITPAN8) {
	this.cpITPAN8 = cpITPAN8;
}

public Date getCpDOB8() {
	return cpDOB8;
}

public void setCpDOB8(Date cpDOB8) {
	this.cpDOB8 = cpDOB8;
}

public String getSocialCategory8() {
	return socialCategory8;
}

public void setSocialCategory8(String socialCategory8) {
	this.socialCategory8 = socialCategory8;
}

public String getEmail8() {
	return email8;
}

public void setEmail8(String email8) {
	this.email8 = email8;
}

public String getAdhar8() {
	return adhar8;
}

public void setAdhar8(String adhar8) {
	this.adhar8 = adhar8;
}

public double getPmrEquity8() {
	return pmrEquity8;
}

public void setPmrEquity8(double pmrEquity8) {
	this.pmrEquity8 = pmrEquity8;
}

public double getPmrDebt8() {
	return pmrDebt8;
}

public void setPmrDebt8(double pmrDebt8) {
	this.pmrDebt8 = pmrDebt8;
}

public String getCpFirstName9() {
	return cpFirstName9;
}

public void setCpFirstName9(String cpFirstName9) {
	this.cpFirstName9 = cpFirstName9;
}

public String getCpLastName9() {
	return cpLastName9;
}

public void setCpLastName9(String cpLastName9) {
	this.cpLastName9 = cpLastName9;
}

public String getCpGender9() {
	return cpGender9;
}

public void setCpGender9(String cpGender9) {
	this.cpGender9 = cpGender9;
}

public String getCpITPAN9() {
	return cpITPAN9;
}

public void setCpITPAN9(String cpITPAN9) {
	this.cpITPAN9 = cpITPAN9;
}

public Date getCpDOB9() {
	return cpDOB9;
}

public void setCpDOB9(Date cpDOB9) {
	this.cpDOB9 = cpDOB9;
}

public String getSocialCategory9() {
	return socialCategory9;
}

public void setSocialCategory9(String socialCategory9) {
	this.socialCategory9 = socialCategory9;
}

public String getEmail9() {
	return email9;
}

public void setEmail9(String email9) {
	this.email9 = email9;
}

public String getAdhar9() {
	return adhar9;
}

public void setAdhar9(String adhar9) {
	this.adhar9 = adhar9;
}

public double getPmrEquity9() {
	return pmrEquity9;
}

public void setPmrEquity9(double pmrEquity9) {
	this.pmrEquity9 = pmrEquity9;
}

public double getPmrDebt9() {
	return pmrDebt9;
}

public void setPmrDebt9(double pmrDebt9) {
	this.pmrDebt9 = pmrDebt9;
}

public String getCpFirstName10() {
	return cpFirstName10;
}

public void setCpFirstName10(String cpFirstName10) {
	this.cpFirstName10 = cpFirstName10;
}

public String getCpLastName10() {
	return cpLastName10;
}

public void setCpLastName10(String cpLastName10) {
	this.cpLastName10 = cpLastName10;
}

public String getCpGender10() {
	return cpGender10;
}

public void setCpGender10(String cpGender10) {
	this.cpGender10 = cpGender10;
}

public String getCpITPAN10() {
	return cpITPAN10;
}

public void setCpITPAN10(String cpITPAN10) {
	this.cpITPAN10 = cpITPAN10;
}

public Date getCpDOB10() {
	return cpDOB10;
}

public void setCpDOB10(Date cpDOB10) {
	this.cpDOB10 = cpDOB10;
}

public String getSocialCategory10() {
	return socialCategory10;
}

public void setSocialCategory10(String socialCategory10) {
	this.socialCategory10 = socialCategory10;
}

public String getEmail10() {
	return email10;
}

public void setEmail10(String email10) {
	this.email10 = email10;
}

public String getAdhar10() {
	return adhar10;
}

public void setAdhar10(String adhar10) {
	this.adhar10 = adhar10;
}

public double getPmrEquity10() {
	return pmrEquity10;
}

public void setPmrEquity10(double pmrEquity10) {
	this.pmrEquity10 = pmrEquity10;
}

public double getPmrDebt10() {
	return pmrDebt10;
}

public void setPmrDebt10(double pmrDebt10) {
	this.pmrDebt10 = pmrDebt10;
}
private double corpusContributionAmt;

 private Date corpusContributionDate;

 private String displayDefaultersList="";

 private String cpParTitle;
 private String cpParFirstName;
 private String cpParMiddleName;
 private String cpParLastName;

 private String religion;
 private String mSE;
  
  
  public String getReligion() {
	return religion;
}

public void setReligion(String religion) {
	this.religion = religion;
}

public String getmSE() {
	return mSE;
}

public void setmSE(String mSE) {
	this.mSE = mSE;
}

public String getCgPan() {
	return cgPan;
}

public void setCgPan(String cgPan) {
	this.cgPan = cgPan;
}


public String getBorrowerRefNo() {
	return borrowerRefNo;
}

public void setBorrowerRefNo(String borrowerRefNo) {
	this.borrowerRefNo = borrowerRefNo;
}

public String getConstitution() {
	return constitution;
}

public void setConstitution(String constitution) {
	this.constitution = constitution;
}

public String getConstitutionOther() {
	return constitutionOther;
}

public void setConstitutionOther(String constitutionOther) {
	this.constitutionOther = constitutionOther;
}

public String getSsiType() {
	return ssiType;
}

public void setSsiType(String ssiType) {
	this.ssiType = ssiType;
}

public String getRegNo() {
	return regNo;
}

public void setRegNo(String regNo) {
	this.regNo = regNo;
}

public Date getCommencementDate() {
	return commencementDate;
}

public void setCommencementDate(Date commencementDate) {
	this.commencementDate = commencementDate;
}

public String getSsiITPan() {
	return ssiITPan;
}

public void setSsiITPan(String ssiITPan) {
	this.ssiITPan = ssiITPan;
}

public String getIndustryNature() {
	return industryNature;
}

public void setIndustryNature(String industryNature) {
	this.industryNature = industryNature;
}

public String getIndustrySector() {
	return industrySector;
}

public void setIndustrySector(String industrySector) {
	this.industrySector = industrySector;
}

public String getActivityType() {
	return activityType;
}

public void setActivityType(String activityType) {
	this.activityType = activityType;
}

public String getMudraType() {
	return mudraType;
}

public void setMudraType(String mudraType) {
	this.mudraType = mudraType;
}

public String getMudraITPANChk() {
	return mudraITPANChk;
}

public void setMudraITPANChk(String mudraITPANChk) {
	this.mudraITPANChk = mudraITPANChk;
}

public int getEmployeeNos() {
	return employeeNos;
}

public void setEmployeeNos(int employeeNos) {
	this.employeeNos = employeeNos;
}

public double getProjectedSalesTurnover() {
	return projectedSalesTurnover;
}

public void setProjectedSalesTurnover(double projectedSalesTurnover) {
	this.projectedSalesTurnover = projectedSalesTurnover;
}

public double getProjectedExports() {
	return projectedExports;
}

public void setProjectedExports(double projectedExports) {
	this.projectedExports = projectedExports;
}

public String getCity() {
	return city;
}

public void setCity(String city) {
	this.city = city;
}

public String getPincode() {
	return pincode;
}

public void setPincode(String pincode) {
	this.pincode = pincode;
}

public String getCpTitle() {
	return cpTitle;
}

public void setCpTitle(String cpTitle) {
	this.cpTitle = cpTitle;
}

public String getCpFirstName() {
	return cpFirstName;
}

public void setCpFirstName(String cpFirstName) {
	this.cpFirstName = cpFirstName;
}

public String getCpMiddleName() {
	return cpMiddleName;
}

public void setCpMiddleName(String cpMiddleName) {
	this.cpMiddleName = cpMiddleName;
}

public String getCpLastName() {
	return cpLastName;
}

public void setCpLastName(String cpLastName) {
	this.cpLastName = cpLastName;
}

public String getCpGender() {
	return cpGender;
}

public void setCpGender(String cpGender) {
	this.cpGender = cpGender;
}

public String getCpITPAN() {
	return cpITPAN;
}

public void setCpITPAN(String cpITPAN) {
	this.cpITPAN = cpITPAN;
}

public Date getCpDOB() {
	return cpDOB;
}

public void setCpDOB(Date cpDOB) {
	this.cpDOB = cpDOB;
}

public String getCpLegalID() {
	return cpLegalID;
}

public void setCpLegalID(String cpLegalID) {
	this.cpLegalID = cpLegalID;
}

public String getCpLegalIdValue() {
	return cpLegalIdValue;
}

public void setCpLegalIdValue(String cpLegalIdValue) {
	this.cpLegalIdValue = cpLegalIdValue;
}

public String getFirstName() {
	return firstName;
}

public void setFirstName(String firstName) {
	this.firstName = firstName;
}

public String getFirstItpan() {
	return firstItpan;
}

public void setFirstItpan(String firstItpan) {
	this.firstItpan = firstItpan;
}

public Date getFirstDOB() {
	return firstDOB;
}

public void setFirstDOB(Date firstDOB) {
	this.firstDOB = firstDOB;
}

public String getSecondName() {
	return secondName;
}

public void setSecondName(String secondName) {
	this.secondName = secondName;
}

public String getSecondItpan() {
	return secondItpan;
}

public void setSecondItpan(String secondItpan) {
	this.secondItpan = secondItpan;
}

public Date getSecondDOB() {
	return secondDOB;
}

public void setSecondDOB(Date secondDOB) {
	this.secondDOB = secondDOB;
}

public String getThirdName() {
	return thirdName;
}

public void setThirdName(String thirdName) {
	this.thirdName = thirdName;
}

public String getThirdItpan() {
	return thirdItpan;
}

public void setThirdItpan(String thirdItpan) {
	this.thirdItpan = thirdItpan;
}

public Date getThirdDOB() {
	return thirdDOB;
}

public void setThirdDOB(Date thirdDOB) {
	this.thirdDOB = thirdDOB;
}

public String getEnterprise() {
	return enterprise;
}

public void setEnterprise(String enterprise) {
	this.enterprise = enterprise;
}

public String getUnitAssisted() {
	return unitAssisted;
}

public void setUnitAssisted(String unitAssisted) {
	this.unitAssisted = unitAssisted;
}

public String getConditionAccepted() {
	return conditionAccepted;
}

public void setConditionAccepted(String conditionAccepted) {
	this.conditionAccepted = conditionAccepted;
}

public String getWomenOperated() {
	return womenOperated;
}

public void setWomenOperated(String womenOperated) {
	this.womenOperated = womenOperated;
}

public String getPhysicallyHandicapped() {
	return physicallyHandicapped;
}

public void setPhysicallyHandicapped(String physicallyHandicapped) {
	this.physicallyHandicapped = physicallyHandicapped;
}

public double getCorpusContributionAmt() {
	return corpusContributionAmt;
}

public void setCorpusContributionAmt(double corpusContributionAmt) {
	this.corpusContributionAmt = corpusContributionAmt;
}

public Date getCorpusContributionDate() {
	return corpusContributionDate;
}

public void setCorpusContributionDate(Date corpusContributionDate) {
	this.corpusContributionDate = corpusContributionDate;
}

public String getDisplayDefaultersList() {
	return displayDefaultersList;
}

public void setDisplayDefaultersList(String displayDefaultersList) {
	this.displayDefaultersList = displayDefaultersList;
}

public String getCpParTitle() {
	return cpParTitle;
}

public void setCpParTitle(String cpParTitle) {
	this.cpParTitle = cpParTitle;
}

public String getCpParFirstName() {
	return cpParFirstName;
}

public void setCpParFirstName(String cpParFirstName) {
	this.cpParFirstName = cpParFirstName;
}

public String getCpParMiddleName() {
	return cpParMiddleName;
}

public void setCpParMiddleName(String cpParMiddleName) {
	this.cpParMiddleName = cpParMiddleName;
}

public String getCpParLastName() {
	return cpParLastName;
}

public void setCpParLastName(String cpParLastName) {
	this.cpParLastName = cpParLastName;
}

public String getAddress()
	  {
		 return address;
	  }

 /**
  * Sets the value of the address property.
  *
  * @param aAddress the new value of the address property
  */
 public void setAddress(String aAddress)
 {
	 address = aAddress;
 }
  


public String getSsiName() {
	return ssiName;
}

public void setSsiName(String ssiName) {
	this.ssiName = ssiName;
}

public double getPresentSalesTurnover() {
	return presentSalesTurnover;
}

public void setPresentSalesTurnover(double presentSalesTurnover) {
	this.presentSalesTurnover = presentSalesTurnover;
}

public String getFacilityOthrtBnk() {
	return facilityOthrtBnk;
}

public void setFacilityOthrtBnk(String facilityOthrtBnk) {
	this.facilityOthrtBnk = facilityOthrtBnk;
}

public double getOtherBanksancAmt() {
	return otherBanksancAmt;
}

public void setOtherBanksancAmt(double otherBanksancAmt) {
	this.otherBanksancAmt = otherBanksancAmt;
}

public double getOtherBankoutstandingAmt() {
	return otherBankoutstandingAmt;
}

public void setOtherBankoutstandingAmt(double otherBankoutstandingAmt) {
	this.otherBankoutstandingAmt = otherBankoutstandingAmt;
}

public String getOtherBankaccStatus() {
	return otherBankaccStatus;
}

public void setOtherBankaccStatus(String otherBankaccStatus) {
	this.otherBankaccStatus = otherBankaccStatus;
}

public double getOtherBankwcSancAmt() {
	return otherBankwcSancAmt;
}

public void setOtherBankwcSancAmt(double otherBankwcSancAmt) {
	this.otherBankwcSancAmt = otherBankwcSancAmt;
}

public double getOtherBankwcOutstandingAmt() {
	return otherBankwcOutstandingAmt;
}

public void setOtherBankwcOutstandingAmt(double otherBankwcOutstandingAmt) {
	this.otherBankwcOutstandingAmt = otherBankwcOutstandingAmt;
}

public String getOtherBankwcAccStatus() {
	return otherBankwcAccStatus;
}

public void setOtherBankwcAccStatus(String otherBankwcAccStatus) {
	this.otherBankwcAccStatus = otherBankwcAccStatus;
}

public double getOtherBankotherSancAmt() {
	return otherBankotherSancAmt;
}

public void setOtherBankotherSancAmt(double otherBankotherSancAmt) {
	this.otherBankotherSancAmt = otherBankotherSancAmt;
}

public double getOtherBankotherOutstandingAmt() {
	return otherBankotherOutstandingAmt;
}

public void setOtherBankotherOutstandingAmt(double otherBankotherOutstandingAmt) {
	this.otherBankotherOutstandingAmt = otherBankotherOutstandingAmt;
}

public String getOtherBankotherAccStatus() {
	return otherBankotherAccStatus;
}

public void setOtherBankotherAccStatus(String otherBankotherAccStatus) {
	this.otherBankotherAccStatus = otherBankotherAccStatus;
}

public String getOtherBankName() {
	return otherBankName;
}

public void setOtherBankName(String otherBankName) {
	this.otherBankName = otherBankName;
}



public String getNpaReason() {
	return npaReason;
}

public void setNpaReason(String npaReason) {
	this.npaReason = npaReason;
}

public String getIsAcctReconstructed() {
	return isAcctReconstructed;
}

public void setIsAcctReconstructed(String isAcctReconstructed) {
	this.isAcctReconstructed = isAcctReconstructed;
}

public double getPmrEquity() {
	return pmrEquity;
}

public void setPmrEquity(double pmrEquity) {
	this.pmrEquity = pmrEquity;
}

public double getPmrDebt() {
	return pmrDebt;
}

public void setPmrDebt(double pmrDebt) {
	this.pmrDebt = pmrDebt;
}

public double getPmrStakeTotal() {
	return pmrStakeTotal;
}

public void setPmrStakeTotal(double pmrStakeTotal) {
	this.pmrStakeTotal = pmrStakeTotal;
}

public double getPmr15Percent() {
	return pmr15Percent;
}

public void setPmr15Percent(double pmr15Percent) {
	this.pmr15Percent = pmr15Percent;
}

public double getPmrMinTotal() {
	return pmrMinTotal;
}

public void setPmrMinTotal(double pmrMinTotal) {
	this.pmrMinTotal = pmrMinTotal;
}

public double getSancAmt() {
	return sancAmt;
}

public void setSancAmt(double sancAmt) {
	this.sancAmt = sancAmt;
}

public double getOutstandingAmt() {
	return outstandingAmt;
}

public void setOutstandingAmt(double outstandingAmt) {
	this.outstandingAmt = outstandingAmt;
}



public String getAccStatus() {
	return accStatus;
}

public void setAccStatus(String accStatus) {
	this.accStatus = accStatus;
}

public double getWcSancAmt() {
	return wcSancAmt;
}

public void setWcSancAmt(double wcSancAmt) {
	this.wcSancAmt = wcSancAmt;
}

public double getWcOutstandingAmt() {
	return wcOutstandingAmt;
}

public void setWcOutstandingAmt(double wcOutstandingAmt) {
	this.wcOutstandingAmt = wcOutstandingAmt;
}

public String getWcAccStatus() {
	return wcAccStatus;
}

public void setWcAccStatus(String wcAccStatus) {
	this.wcAccStatus = wcAccStatus;
}

public double getOtherSancAmt() {
	return otherSancAmt;
}

public void setOtherSancAmt(double otherSancAmt) {
	this.otherSancAmt = otherSancAmt;
}

public double getOtherOutstandingAmt() {
	return otherOutstandingAmt;
}

public void setOtherOutstandingAmt(double otherOutstandingAmt) {
	this.otherOutstandingAmt = otherOutstandingAmt;
}

public String getOtherAccStatus() {
	return otherAccStatus;
}

public void setOtherAccStatus(String otherAccStatus) {
	this.otherAccStatus = otherAccStatus;
}

public double getTotalSancAmt() {
	return totalSancAmt;
}

public void setTotalSancAmt(double totalSancAmt) {
	this.totalSancAmt = totalSancAmt;
}

public double getTotalOutstandingAmt() {
	return totalOutstandingAmt;
}

public void setTotalOutstandingAmt(double totalOutstandingAmt) {
	this.totalOutstandingAmt = totalOutstandingAmt;
}

public String getExposurelmtAmt() {
	return exposurelmtAmt;
}
  private String ssiConstitution;
public String getSsiConstitution() {
	return ssiConstitution;
}

public void setSsiConstitution(String ssiConstitution) {
	this.ssiConstitution = ssiConstitution;
}

public void setExposurelmtAmt(String exposurelmtAmt) {
	this.exposurelmtAmt = exposurelmtAmt;
}

private String mliRefNo;
  private BorrowerDetails borrowerDetails;
  private ProjectOutlayDetails projectOutlayDetails;
  private String rehabilitation;
  private String compositeLoan;
  private String loanType;
  private String TypeLoan;
  private double TypeLoanTC;
  private double TypeLoanWC;
  private double TCTYPE;
  private double wCTYPE;
  private String BorrowergstNo;
  private String applicationType;
  private String scheme;
  private TermLoan termLoan;
  private WorkingCapital wc;
  private double approvedAmount;
  private double sanctionedAmount;
  private double reapprovedAmount;
  private double enhancementAmount;
  private String docRefNo;
  private String reapprovalRemarks;
  private String cgpan;
  private String cgpanReference;
  private String userId;
  private String bankId;
  private String zoneId;
  private String branchId;
  private String appRefNo;
  private String wcAppRefNo;
  private String regionId;
  private String NPA;
  private String ssiRef;
  private String collateralSecDtls;//CHECK
  private double outstandingAmount;
  private String ITPAN;
  private int subsidyProvided;
  private Date submittedDate;
  private Date sanctionedDate;
  private String activity;
  private Date approvedDate;
  private Date guaranteeStartDate;
  private Date appExpiryDate;
  private String remarks;
  private String prevSSI;
  private String existSSI;
  private String status;
  private int projectType;
  private RepaymentDetail theRepaymentDetail;
  private Securitization securitization;
  private MCGFDetails mcgfDetails;
  private double guaranteeFee;
  private String subSchemeName;
  private String existingRemarks;
  private boolean additionalTC;
  private boolean wcEnhancement;
  private boolean wcRenewal;
  private boolean isVerified;
  private String zoneName;
  private String coFinanceTaken1;
  private String sex;
  private String socialCategory;
  private String internalRate;
  private String externalRate;
  private String handiCrafts;
  private String dcHandicrafts;
  private String icardNo;
  private Date icardIssueDate;
  private String jointFinance;
  private String jointcgpan;
  private String activityConfirm;
  private String handiCraftsStatus;
  private String dcHandicraftsStatus;
  private String dcHandlooms;
  private String dcHandloomsStatus;
  private String WeaverCreditScheme;
  private String handloomchk;

  private String  isPrimarySecurity;
  private String email;
  
  private String handloomSchName;
  private String internalRating;
  private String internalratingProposal;
  private String investmentGrade;
  private String subsidyType;
  private String subsidyOther;
  private String udyogAdharNo;
  private String bankAcNo; 
  private String FBammtlimit; 
  
  private int sanctionTenure; 
  private int DPDStatus;
  private double osMLI;
  
  private String gstNo;
  private String gstState;
  private String stateCode;
  private String gst;
  private String exposureFbId;
  private String exposureFbIdY;
  private String exposureFbIdN;

	private String hybridSecurity;
	private Double movCollateratlSecurityAmt = 0.0d;
	private Double immovCollateratlSecurityAmt = 0.0d;
	private Double totalMIcollatSecAmt = 0.0d;
    private Long proMobileNo;
    private Long proMobileNo1;
    private Long proMobileNo2;
    private Long proMobileNo3;
    private Long proMobileNo4;
    
    private Long proMobileNo5;
    private Long proMobileNo6;
    private Long proMobileNo7;
    private Long proMobileNo8;
    private Long proMobileNo9;
    

	// add 30 column 
     private String promDirDefaltFlg="";       
     private int credBureKeyPromScor=0;       
     private int credBurePromScor2=0;         
     private int credBurePromScor3=0;         
     private int credBurePromScor4=0;         
     private int credBurePromScor5=0;         
     private String credBureName1="";                 
     private String credBureName2="";          	   
     private String credBureName3="";       		   
     private String credBureName4="";        		   
     private String credBureName5="";       		   
     private int cibilFirmMsmeRank=0;        
     private int expCommerScor=0;             
     private float promBorrNetWorth=0.0f;        
     private int promContribution=0;          
     private String promGAssoNPA1YrFlg;     
     private int promBussExpYr=0;             
     private float salesRevenue=0.0f;             
     private float taxPBIT=0.0f;                  
     private float interestPayment=0.0f;          
     private float taxCurrentProvisionAmt=0.0f;   
     private float totCurrentAssets=0.0f;         
     private float totCurrentLiability=0.0f;      
     private float totTermLiability=0.0f;         
     private float exuityCapital=0.0f;            
     private float preferenceCapital=0.0f;        
     private float reservesSurplus=0.0f;          
     private float repaymentDueNyrAmt=0.0f;       
     private String existGreenFldUnitType="";               				
     private float opratIncome=0.0f;               			
     private float profAftTax=0.0f;                           
     private float networth=0.0f;       
     private float debitEqtRatioUnt=0.0f;
     private float debitSrvCoverageRatioTl=0.0f;
     private float currentRatioWc=0.0f;    					
     private float debitEqtRatio=0.0f;
     private float debitSrvCoverageRatio=0.0f;		
     private float currentRatios=0.0f;	   					
     private int creditBureauChiefPromScor=0;			
     private float totalAssets=0.0f;
     
     
     
     
     public Long getProMobileNo1() {
		return proMobileNo1;
	}

	public void setProMobileNo1(Long proMobileNo1) {
		this.proMobileNo1 = proMobileNo1;
	}

	public Long getProMobileNo2() {
		return proMobileNo2;
	}

	public void setProMobileNo2(Long proMobileNo2) {
		this.proMobileNo2 = proMobileNo2;
	}

	public Long getProMobileNo3() {
		return proMobileNo3;
	}

	public void setProMobileNo3(Long proMobileNo3) {
		this.proMobileNo3 = proMobileNo3;
	}

	public Long getProMobileNo4() {
		return proMobileNo4;
	}

	public void setProMobileNo4(Long proMobileNo4) {
		this.proMobileNo4 = proMobileNo4;
	}

	public Long getProMobileNo5() {
		return proMobileNo5;
	}

	public void setProMobileNo5(Long proMobileNo5) {
		this.proMobileNo5 = proMobileNo5;
	}

	public Long getProMobileNo6() {
		return proMobileNo6;
	}

	public void setProMobileNo6(Long proMobileNo6) {
		this.proMobileNo6 = proMobileNo6;
	}

	public Long getProMobileNo7() {
		return proMobileNo7;
	}

	public void setProMobileNo7(Long proMobileNo7) {
		this.proMobileNo7 = proMobileNo7;
	}

	public Long getProMobileNo8() {
		return proMobileNo8;
	}

	public void setProMobileNo8(Long proMobileNo8) {
		this.proMobileNo8 = proMobileNo8;
	}

	public Long getProMobileNo9() {
		return proMobileNo9;
	}

	public void setProMobileNo9(Long proMobileNo9) {
		this.proMobileNo9 = proMobileNo9;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	private Date outstandingDate;     
		public Date getOutstandingDate() {
		return outstandingDate;
	}

	public void setOutstandingDate(Date outstandingDate) {
		this.outstandingDate = outstandingDate;
	}

     

    public double getTCTYPE() {
		return TCTYPE;
	}

	public void setTCTYPE(double tCTYPE) {
		this.TCTYPE = tCTYPE;
	}
 
	public double getwCTYPE() {
		return wCTYPE;
	}

	public void setwCTYPE(double wCTYPE) {
		this.wCTYPE = wCTYPE;
	}

	public String getBorrowergstNo() {
		return BorrowergstNo;
	}

	public void setBorrowergstNo(String borrowergstNo) {
		BorrowergstNo = borrowergstNo;
	}

	public int getDPDStatus() {
		return DPDStatus;
	}

	public void setDPDStatus(int dPDStatus) {
		DPDStatus = dPDStatus;
	}

	public int getSanctionTenure() {
		return sanctionTenure;
	}

	public void setSanctionTenure(int sanctionTenure) {
		this.sanctionTenure = sanctionTenure;
	}



	public double getTypeLoanTC() {
		return TypeLoanTC;
	}

	public void setTypeLoanTC(double typeLoanTC) {
		this.TypeLoanTC = typeLoanTC;
	}

	public double getTypeLoanWC() {
		return TypeLoanWC;
	}

	public void setTypeLoanWC(double typeLoanWC) {
		this.TypeLoanWC = typeLoanWC;
	}

	public float getDebitEqtRatioUnt() {
		return debitEqtRatioUnt;
	}

	public void setDebitEqtRatioUnt(float debitEqtRatioUnt) {
		this.debitEqtRatioUnt = debitEqtRatioUnt;
	}

	public float getDebitSrvCoverageRatioTl() {
		return debitSrvCoverageRatioTl;
	}

	public void setDebitSrvCoverageRatioTl(float debitSrvCoverageRatioTl) {
		this.debitSrvCoverageRatioTl = debitSrvCoverageRatioTl;
	}

	public String getTypeLoan() {
		return TypeLoan;
	}

	public void setTypeLoan(String typeLoan) {
		TypeLoan = typeLoan;
	}

	public float getCurrentRatioWc() {
		return currentRatioWc;
	}

	public double getOsMLI() {
		return osMLI;
	}

	public void setOsMLI(double osMLI) {
		this.osMLI = osMLI;
	}

	public void setCurrentRatioWc(float currentRatioWc) {
		this.currentRatioWc = currentRatioWc;
	}

	public float getDebitEqtRatio() {
		return debitEqtRatio;
	}

	public void setDebitEqtRatio(float debitEqtRatio) {
		this.debitEqtRatio = debitEqtRatio;
	}

	public float getDebitSrvCoverageRatio() {
		return debitSrvCoverageRatio;
	}

	public void setDebitSrvCoverageRatio(float debitSrvCoverageRatio) {
		this.debitSrvCoverageRatio = debitSrvCoverageRatio;
	}

	public float getCurrentRatios() {
		return currentRatios;
	}

	public void setCurrentRatios(float currentRatios) {
		this.currentRatios = currentRatios;
	}

	public String getExistGreenFldUnitType() {
		return existGreenFldUnitType;
	}

	public void setExistGreenFldUnitType(String existGreenFldUnitType) {
		this.existGreenFldUnitType = existGreenFldUnitType;
	}

	/*public String getExistUnit() {
		return existUnit;
	}

	public void setExistUnit(String existUnit) {
		this.existUnit = existUnit;
	}

	public String getGreenUnit() {
		return GreenUnit;
	}

	public void setGreenUnit(String greenUnit) {
		GreenUnit = greenUnit;
	}*/

	public float getOpratIncome() {
		return opratIncome;
	}

	public void setOpratIncome(float opratIncome) {
		this.opratIncome = opratIncome;
	}

	public float getProfAftTax() {
		return profAftTax;
	}

	public void setProfAftTax(float profAftTax) {
		this.profAftTax = profAftTax;
	}

	public float getNetworth() {
		return networth;
	}

	public void setNetworth(float networth) {
		this.networth = networth;
	}

	

	public void setDebitSrvCoverageRatio(int debitSrvCoverageRatio) {
		this.debitSrvCoverageRatio = debitSrvCoverageRatio;
	}

	

	public void setDebitSrvCoverageRatioTl(int debitSrvCoverageRatioTl) {
		this.debitSrvCoverageRatioTl = debitSrvCoverageRatioTl;
	}

	

	public void setCurrentRatios(int currentRatios) {
		this.currentRatios = currentRatios;
	}



	public void setCurrentRatioWc(int currentRatioWc) {
		this.currentRatioWc = currentRatioWc;
	}

	public int getCreditBureauChiefPromScor() {
		return creditBureauChiefPromScor;
	}

	public void setCreditBureauChiefPromScor(int creditBureauChiefPromScor) {
		this.creditBureauChiefPromScor = creditBureauChiefPromScor;
	}

	public float getTotalAssets() {
		return totalAssets;
	}

	public void setTotalAssets(float totalAssets) {
		this.totalAssets = totalAssets;
	}

	public MCGFDetails getMcgfDetails() {
		return mcgfDetails;
	}

	public void setMcgfDetails(MCGFDetails mcgfDetails) {
		this.mcgfDetails = mcgfDetails;
	}

	public double getGuaranteeFee() {
		return guaranteeFee;
	}

	public void setGuaranteeFee(double guaranteeFee) {
		this.guaranteeFee = guaranteeFee;
	}

	public String getFBammtlimit() {
		return FBammtlimit;
	}

	public void setFBammtlimit(String fBammtlimit) {
		FBammtlimit = fBammtlimit;
	}

	public String getPromDirDefaltFlg() {
		return promDirDefaltFlg;
	}

	public void setPromDirDefaltFlg(String promDirDefaltFlg) {
		this.promDirDefaltFlg = promDirDefaltFlg;
	}

	public int getCredBureKeyPromScor() {
		return credBureKeyPromScor;
	}

	public void setCredBureKeyPromScor(int credBureKeyPromScor) {
		this.credBureKeyPromScor = credBureKeyPromScor;
	}

	public int getCredBurePromScor2() {
		return credBurePromScor2;
	}

	public void setCredBurePromScor2(int credBurePromScor2) {
		this.credBurePromScor2 = credBurePromScor2;
	}

	public int getCredBurePromScor3() {
		return credBurePromScor3;
	}

	public void setCredBurePromScor3(int credBurePromScor3) {
		this.credBurePromScor3 = credBurePromScor3;
	}

	public int getCredBurePromScor4() {
		return credBurePromScor4;
	}

	public void setCredBurePromScor4(int credBurePromScor4) {
		this.credBurePromScor4 = credBurePromScor4;
	}

	public int getCredBurePromScor5() {
		return credBurePromScor5;
	}

	public void setCredBurePromScor5(int credBurePromScor5) {
		this.credBurePromScor5 = credBurePromScor5;
	}

	public String getCredBureName1() {
		return credBureName1;
	}

	public void setCredBureName1(String credBureName1) {
		this.credBureName1 = credBureName1;
	}

	public String getCredBureName2() {
		return credBureName2;
	}

	public void setCredBureName2(String credBureName2) {
		this.credBureName2 = credBureName2;
	}

	public String getCredBureName3() {
		return credBureName3;
	}

	public void setCredBureName3(String credBureName3) {
		this.credBureName3 = credBureName3;
	}

	public String getCredBureName4() {
		return credBureName4;
	}

	public void setCredBureName4(String credBureName4) {
		this.credBureName4 = credBureName4;
	}

	public String getCredBureName5() {
		return credBureName5;
	}

	public void setCredBureName5(String credBureName5) {
		this.credBureName5 = credBureName5;
	}

	public int getCibilFirmMsmeRank() {
		return cibilFirmMsmeRank;
	}

	public void setCibilFirmMsmeRank(int cibilFirmMsmeRank) {
		this.cibilFirmMsmeRank = cibilFirmMsmeRank;
	}

	public int getExpCommerScor() {
		return expCommerScor;
	}

	public void setExpCommerScor(int expCommerScor) {
		this.expCommerScor = expCommerScor;
	}

	public float getPromBorrNetWorth() {
		return promBorrNetWorth;
	}

	public void setPromBorrNetWorth(float promBorrNetWorth) {
		this.promBorrNetWorth = promBorrNetWorth;
	}

	public int getPromContribution() {
		return promContribution;
	}

	public void setPromContribution(int promContribution) {
		this.promContribution = promContribution;
	}

	public String getPromGAssoNPA1YrFlg() {
		return promGAssoNPA1YrFlg;
	}

	public void setPromGAssoNPA1YrFlg(String promGAssoNPA1YrFlg) {
		this.promGAssoNPA1YrFlg = promGAssoNPA1YrFlg;
	}

	public int getPromBussExpYr() {
		return promBussExpYr;
	}

	public void setPromBussExpYr(int promBussExpYr) {
		this.promBussExpYr = promBussExpYr;
	}

	public float getSalesRevenue() {
		return salesRevenue;
	}

	public void setSalesRevenue(float salesRevenue) {
		this.salesRevenue = salesRevenue;
	}

	public float getTaxPBIT() {
		return taxPBIT;
	}

	public void setTaxPBIT(float taxPBIT) {
		this.taxPBIT = taxPBIT;
	}

	public float getInterestPayment() {
		return interestPayment;
	}

	public void setInterestPayment(float interestPayment) {
		this.interestPayment = interestPayment;
	}

	public float getTaxCurrentProvisionAmt() {
		return taxCurrentProvisionAmt;
	}

	public void setTaxCurrentProvisionAmt(float taxCurrentProvisionAmt) {
		this.taxCurrentProvisionAmt = taxCurrentProvisionAmt;
	}

	public float getTotCurrentAssets() {
		return totCurrentAssets;
	}

	public void setTotCurrentAssets(float totCurrentAssets) {
		this.totCurrentAssets = totCurrentAssets;
	}

	public float getTotCurrentLiability() {
		return totCurrentLiability;
	}

	public void setTotCurrentLiability(float totCurrentLiability) {
		this.totCurrentLiability = totCurrentLiability;
	}

	public float getTotTermLiability() {
		return totTermLiability;
	}

	public void setTotTermLiability(float totTermLiability) {
		this.totTermLiability = totTermLiability;
	}

	public float getExuityCapital() {
		return exuityCapital;
	}

	public void setExuityCapital(float exuityCapital) {
		this.exuityCapital = exuityCapital;
	}

	public float getPreferenceCapital() {
		return preferenceCapital;
	}

	public void setPreferenceCapital(float preferenceCapital) {
		this.preferenceCapital = preferenceCapital;
	}

	public float getReservesSurplus() {
		return reservesSurplus;
	}

	public void setReservesSurplus(float reservesSurplus) {
		this.reservesSurplus = reservesSurplus;
	}

	public float getRepaymentDueNyrAmt() {
		return repaymentDueNyrAmt;
	}

	public void setRepaymentDueNyrAmt(float repaymentDueNyrAmt) {
		this.repaymentDueNyrAmt = repaymentDueNyrAmt;
	}

	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}
  
    
		public Long getProMobileNo() {
		return proMobileNo;
	}

	public void setProMobileNo(Long proMobileNo) {
		this.proMobileNo = proMobileNo;
	}

	public String getHybridSecurity() {
		return hybridSecurity;
	}

	public void setHybridSecurity(String hybridSecurity) {
		this.hybridSecurity = hybridSecurity;
	}

	public Double getMovCollateratlSecurityAmt() {
		return movCollateratlSecurityAmt;
	}

	public void setMovCollateratlSecurityAmt(Double movCollateratlSecurityAmt) {
		this.movCollateratlSecurityAmt = movCollateratlSecurityAmt;
	}

	public Double getImmovCollateratlSecurityAmt() {
		return immovCollateratlSecurityAmt;
	}

	public void setImmovCollateratlSecurityAmt(Double immovCollateratlSecurityAmt) {
		this.immovCollateratlSecurityAmt = immovCollateratlSecurityAmt;
	}

	public Double getTotalMIcollatSecAmt() {
		return totalMIcollatSecAmt;
	}

	public void setTotalMIcollatSecAmt(Double totalMIcollatSecAmt) {
		this.totalMIcollatSecAmt = totalMIcollatSecAmt;
	}

	

  public String getExposureFbIdY() {
	return exposureFbIdY;
}

public void setExposureFbIdY(String exposureFbIdY) {
	this.exposureFbIdY = exposureFbIdY;
}

public String getExposureFbIdN() {
	return exposureFbIdN;
}

public void setExposureFbIdN(String exposureFbIdN) {
	this.exposureFbIdN = exposureFbIdN;
}

public String getExposureFbId() {
	return exposureFbId;
}

public void setExposureFbId(String exposureFbId) {
	this.exposureFbId = exposureFbId;
}

public String getItpan() {
	return itpan;
}

public void setItpan(String itpan) {
	this.itpan = itpan;
}

public String getSsiUnitName() {
	return ssiUnitName;
}

public void setSsiUnitName(String ssiUnitName) {
	this.ssiUnitName = ssiUnitName;
}

public String getGurAmt() {
	return gurAmt;
}

public void setGurAmt(String gurAmt) {
	this.gurAmt = gurAmt;
}



public Date getNpaDate() {
	return npaDate;
}

public void setNpaDate(Date npaDate) {
	this.npaDate = npaDate;
}
private String itpan;
  private String ssiUnitName;
  private String gurAmt;
  private Date npaDate;
  private String npa;
  private String assistedByBank;
  
  
public String getNpa() {
	return npa;
}

public void setNpa(String npa) {
	this.npa = npa;
}

public String getAssistedByBank() {
	return assistedByBank;
}

public void setAssistedByBank(String assistedByBank) {
	this.assistedByBank = assistedByBank;
}
private String adharNo;//raju
 

public String getAdharNo() {
	return adharNo;
}

public void setAdharNo(String adharNo) {
	this.adharNo = adharNo;
}



public String getGst() {
	return gst;
}

public void setGst(String gst) {
	this.gst = gst;
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

public String getGstState() {
	return gstState;
}

public void setGstState(String gstState) {
	this.gstState = gstState;
}

public String getSubsidyType() {
	return subsidyType;
}

public void setSubsidyType(String subsidyType) {
	this.subsidyType = subsidyType;
}

public String getSubsidyOther() {
	return subsidyOther;
}

public void setSubsidyOther(String subsidyOther) {
	this.subsidyOther = subsidyOther;
}

public String getHandloomSchName() {
	return handloomSchName;
}

public void setHandloomSchName(String handloomSchName) {
	this.handloomSchName = handloomSchName;
}

public String getInternalRating() {
	return internalRating;
}

public void setInternalRating(String internalRating) {
	this.internalRating = internalRating;
}

public String getInternalratingProposal() {
	return internalratingProposal;
}

public void setInternalratingProposal(String internalratingProposal) {
	this.internalratingProposal = internalratingProposal;
}

public String getInvestmentGrade() {
	return investmentGrade;
}

public void setInvestmentGrade(String investmentGrade) {
	this.investmentGrade = investmentGrade;
}

public String getIsPrimarySecurity() {
	return isPrimarySecurity;
}

public void setIsPrimarySecurity(String isPrimarySecurity) {
	this.isPrimarySecurity = isPrimarySecurity;
}

public void setSsiRef(String ssiRef)
  {
    this.ssiRef = ssiRef;
  }

  public String getSsiRef()
  {
    return this.ssiRef;
  }

  public void setActivity(String act)
  {
    this.activity = act;
  }

  public String getActivity()
  {
    return this.activity;
  }

  public void setZoneName(String name)
  {
    this.zoneName = name;
  }

  public String getZoneName()
  {
    return this.zoneName;
  }

  public void setCoFinanceTaken1(String coFinanceTaken1)
  {
    this.coFinanceTaken1 = coFinanceTaken1;
  }

  public String getCoFinanceTaken1()
  {
    return this.coFinanceTaken1;
  }

  public void setIcardIssueDate(Date icardIssueDate)
  {
    this.icardIssueDate = icardIssueDate;
  }

  public Date getIcardIssueDate()
  {
    return this.icardIssueDate;
  }

  public void setIcardNo(String icardNo)
  {
    this.icardNo = icardNo;
  }

  public String getIcardNo()
  {
    return this.icardNo;
  }

  public void setHandiCrafts(String handiCrafts)
  {
    this.handiCrafts = handiCrafts;
  }

  public String getHandiCrafts()
  {
    return this.handiCrafts;
  }

  public void setJointFinance(String jointFinance)
  {
    this.jointFinance = jointFinance;
  }

  public String getJointFinance()
  {
    return this.jointFinance;
  }

  public void setDcHandicrafts(String dcHandicrafts)
  {
    this.dcHandicrafts = dcHandicrafts;
  }

  public String getDcHandicrafts()
  {
    return this.dcHandicrafts;
  }

  public void setInternalRate(String internalRate)
  {
    this.internalRate = internalRate;
  }

  public String getInternalRate()
  {
    return this.internalRate;
  }

  public void setExternalRate(String externalRate)
  {
    this.externalRate = externalRate;
  }

  public String getExternalRate()
  {
    return this.externalRate;
  }

  public void setDistrict(String district)
  {
    this.district = district;
  }

  public String getDistrict()
  {
    return this.district;
  }

  public void setState(String state)
  {
    this.state = state;
  }

  public String getState()
  {
    return this.state;
  }

  public void setSex(String sex)
  {
    this.sex = sex;
  }

  public String getSex()
  {
    return this.sex;
  }

  public void setSocialCategory(String socialCategory)
  {
    this.socialCategory = socialCategory;
  }

  public String getSocialCategory()
  {
    return this.socialCategory;
  }
  

  public Application()
  {
    this.borrowerDetails = null;
    this.projectOutlayDetails = null;
    this.loanType = null;
    this.applicationType = null;
    this.termLoan = null;
    this.wc = null;
    this.approvedAmount = 0.0D;
    this.sanctionedAmount = 0.0D;
    this.reapprovedAmount = 0.0D;
    this.enhancementAmount = 0.0D;
    this.docRefNo = null;
    this.reapprovalRemarks = null;
    this.cgpan = null;
    this.cgpanReference = null;
    this.userId = null;
    this.bankId = null;
    this.zoneId = null;
    this.branchId = null;
    this.appRefNo = null;
    this.wcAppRefNo = null;
    this.regionId = null;
    this.collateralSecDtls = null;
    this.subsidyProvided = 0;
    this.submittedDate = null;
    this.sanctionedDate = null;
    this.approvedDate = null;
    this.guaranteeStartDate = null;
    this.appExpiryDate = null;
    this.remarks = null;
    this.prevSSI = null;
    this.existSSI = null;
    this.status = null;
    this.projectType = 0;
    this.securitization = null;
    this.mcgfDetails = null;
    this.guaranteeFee = 0.0D;
    this.coFinanceTaken1 = "N";
    this.district = "";
    this.state = "";
    this.sex = "";
    this.socialCategory = "";
    this.internalRate = null;
    this.externalRate = null;
    this.handiCrafts = null;
    this.dcHandicrafts = null;
    this.icardNo = null;
    this.icardIssueDate = null;
    this.jointFinance = null;
    this.jointcgpan = null;
    this.activityConfirm = null;
  //  this.isPrimarySecurity = null;
  }

  public String getMliID()
  {
    return this.mliID;
  }

  public void setMliID(String aMliID)
  {
    this.mliID = aMliID;
  }

  public String getMliBranchName()
  {
    return this.mliBranchName;
  }

  public void setMliBranchName(String aMliBranchName)
  {
    this.mliBranchName = aMliBranchName;
  }

  public BorrowerDetails getBorrowerDetails()
  {
    return this.borrowerDetails;
  }

  public void setBorrowerDetails(BorrowerDetails aBorrowerDetails)
  {
    this.borrowerDetails = aBorrowerDetails;
  }

  public TermLoan getTermLoan()
  {
    return this.termLoan;
  }

  public void setTermLoan(TermLoan aTermLoan)
  {
    this.termLoan = aTermLoan;
  }

  public WorkingCapital getWc()
  {
    return this.wc;
  }

  public void setWc(WorkingCapital aWc)
  {
    this.wc = aWc;
  }

  public String getLoanType()
  {
    return this.loanType;
  }

  public void setLoanType(String aLoanType)
  {
    this.loanType = aLoanType;
  }

  public String getCgpan()
  {
    return this.cgpan;
  }

  public void setCgpan(String aCgpan)
  {
    this.cgpan = aCgpan;
  }

  public String getUserId()
  {
    return this.userId;
  }

  public void setUserId(String aUserId)
  {
    this.userId = aUserId;
  }

  public String getBankId()
  {
    return this.bankId;
  }

  public void setBankId(String aBankId)
  {
    this.bankId = aBankId;
  }

  public String getZoneId()
  {
    return this.zoneId;
  }

  public void setZoneId(String aZoneId)
  {
    this.zoneId = aZoneId;
  }

  public String getBranchId()
  {
    return this.branchId;
  }

  public void setBranchId(String aBranchId)
  {
    this.branchId = aBranchId;
  }

  public String getAppRefNo()
  {
    return this.appRefNo;
  }

  public void setAppRefNo(String aAppRefNo)
  {
    this.appRefNo = aAppRefNo;
  }

  public String getWcAppRefNo()
  {
    return this.wcAppRefNo;
  }

  public void setWcAppRefNo(String aWcAppRefNo)
  {
    this.wcAppRefNo = aWcAppRefNo;
  }

  public String getNPA()
  {
    return this.NPA;
  }

  public void setNPA(String aNPA)
  {
    this.NPA = aNPA;
  }

  public String getCompositeLoan()
  {
    return this.compositeLoan;
  }

  public void setCompositeLoan(String aCompositeLoan)
  {
    this.compositeLoan = aCompositeLoan;
  }

  public String getCollateralSecDtls()
  {
    return this.collateralSecDtls;
  }

  public void setCollateralSecDtls(String aCollateralSecDtls)
  {
    this.collateralSecDtls = aCollateralSecDtls;
  }

  public int getSubsidyProvided()
  {
    return this.subsidyProvided;
  }

  public void setSubsidyProvided(int aSubsidyProvided)
  {
    this.subsidyProvided = aSubsidyProvided;
  }

  public Date getSubmittedDate()
  {
    return this.submittedDate;
  }

  public void setSubmittedDate(Date aSubmittedDate)
  {
    this.submittedDate = aSubmittedDate;
  }

  public Date getSanctionedDate()
  {
    return this.sanctionedDate;
  }

  public void setSanctionedDate(Date aSanctionedDate)
  {
    this.sanctionedDate = aSanctionedDate;
  }

  public String getRehabilitation()
  {
    return this.rehabilitation;
  }

  public void setRehabilitation(String aRehabilitation)
  {
    this.rehabilitation = aRehabilitation;
  }

  public Date getApprovedDate()
  {
    return this.approvedDate;
  }

  public void setApprovedDate(Date aApprovedDate)
  {
    this.approvedDate = aApprovedDate;
  }

  public Date getGuaranteeStartDate()
  {
    return this.guaranteeStartDate;
  }

  public Date getAppExpiryDate()
  {
    return this.appExpiryDate;
  }

  public void setAppExpiryDate(Date bappExpiryDate)
  {
    this.appExpiryDate = bappExpiryDate;
  }

  public void setGuaranteeStartDate(Date aGuaranteeStartDate)
  {
    this.guaranteeStartDate = aGuaranteeStartDate;
  }

  public String getRemarks()
  {
    return this.remarks;
  }

  public void setRemarks(String aRemarks)
  {
    this.remarks = aRemarks;
  }

  public String getStatus()
  {
    return this.status;
  }

  public void setStatus(String aStatus)
  {
    this.status = aStatus;
  }

  public int getProjectType()
  {
    return this.projectType;
  }

  public void setProjectType(int aProjectType)
  {
    this.projectType = aProjectType;
  }

  public double getOutstandingAmount()
  {
    return this.outstandingAmount;
  }

  public void setOutstandingAmount(double aOutstandingAmount)
  {
    this.outstandingAmount = aOutstandingAmount;
  }

  public double getApprovedAmount()
  {
    return this.approvedAmount;
  }

  public void setApprovedAmount(double aApprovedAmount)
  {
    this.approvedAmount = aApprovedAmount;
  }

  public double getSantionedAmount()
  {
    return this.sanctionedAmount;
  }

  public void setSanctionedAmount(double aSanctionedAmount)
  {
    this.sanctionedAmount = aSanctionedAmount;
  }

  public String getITPAN()
  {
    return this.ITPAN;
  }

  public void setITPAN(String aITPAN)
  {
    this.ITPAN = aITPAN;
  }

  public double getEnhancementAmount()
  {
    return this.enhancementAmount;
  }

  public void setEnhancementAmount(double aEnhancementAmount)
  {
    this.enhancementAmount = aEnhancementAmount;
  }

  public double getReapprovedAmount()
  {
    return this.reapprovedAmount;
  }

  public void setReapprovedAmount(double aReapprovedAmount)
  {
    this.reapprovedAmount = aReapprovedAmount;
  }

  public String getDocRefNo()
  {
    return this.docRefNo;
  }

  public void setDocRefNo(String aDocRefNo)
  {
    this.docRefNo = aDocRefNo;
  }

  public String getReapprovalRemarks()
  {
    return this.reapprovalRemarks;
  }

  public void setReapprovalRemarks(String aReapprovalRemarks)
  {
    this.reapprovalRemarks = aReapprovalRemarks;
  }

  public ProjectOutlayDetails getProjectOutlayDetails()
  {
    return this.projectOutlayDetails;
  }

  public double getSanctionedAmount()
  {
    return this.sanctionedAmount;
  }

  public String getScheme()
  {
    return this.scheme;
  }

  public RepaymentDetail getTheRepaymentDetail()
  {
    return this.theRepaymentDetail;
  }

  public void setProjectOutlayDetails(ProjectOutlayDetails details)
  {
    this.projectOutlayDetails = details;
  }

  public void setScheme(String string)
  {
    this.scheme = string;
  }

  public void setTheRepaymentDetail(RepaymentDetail detail)
  {
    this.theRepaymentDetail = detail;
  }

  public String getRegionId()
  {
    return this.regionId;
  }

  public void setRegionId(String aRegionId)
  {
    this.regionId = aRegionId;
  }

  public String getMliBranchCode()
  {
    return this.mliBranchCode;
  }

  public void setMliBranchCode(String aMliBranchCode)
  {
    this.mliBranchCode = aMliBranchCode;
  }

  public void setGuaranteeAmount(double guaranteeFee)
  {
    this.guaranteeFee = guaranteeFee;
  }

  public double getGuaranteeAmount()
  {
    return this.guaranteeFee;
  }

  public String getMliRefNo()
  {
    return this.mliRefNo;
  }

  public void setMliRefNo(String aMliRefNo)
  {
    this.mliRefNo = aMliRefNo;
  }

  public Securitization getSecuritization()
  {
    return this.securitization;
  }

  public void setSecuritization(Securitization aSecuritization)
  {
    this.securitization = aSecuritization;
  }

  public MCGFDetails getMCGFDetails()
  {
    return this.mcgfDetails;
  }

  public void setMCGFDetails(MCGFDetails aMCGFDetails)
  {
    this.mcgfDetails = aMCGFDetails;
  }

  public String getSubSchemeName()
  {
    return this.subSchemeName;
  }

  public void setSubSchemeName(String aSubSchemeName)
  {
    this.subSchemeName = aSubSchemeName;
  }

  public String getExistingRemarks()
  {
    return this.existingRemarks;
  }

  public void setExistingRemarks(String aExistingRemarks)
  {
    this.existingRemarks = aExistingRemarks;
  }

  public boolean getAdditionalTC()
  {
    return this.additionalTC;
  }

  public void setAdditionalTC(boolean aAdditionalTC)
  {
    this.additionalTC = aAdditionalTC;
  }

  public boolean getWcEnhancement()
  {
    return this.wcEnhancement;
  }

  public void setWcEnhancement(boolean aWcEnhancement)
  {
    this.wcEnhancement = aWcEnhancement;
  }

  public boolean getWcRenewal()
  {
    return this.wcRenewal;
  }

  public void setWcRenewal(boolean aWcRenewal)
  {
    this.wcRenewal = aWcRenewal;
  }

  public String getCgpanReference()
  {
    return this.cgpanReference;
  }

  public void setCgpanReference(String aCgpanReference)
  {
    this.cgpanReference = aCgpanReference;
  }

  public boolean getIsVerified()
  {
    return this.isVerified;
  }

  public void setIsVerified(boolean b)
  {
    this.isVerified = b;
  }

  public void setJointcgpan(String jointcgpan)
  {
    this.jointcgpan = jointcgpan;
  }

  public String getJointcgpan()
  {
    return this.jointcgpan;
  }

  public void setApplicationType(String applicationType)
  {
    this.applicationType = applicationType;
  }

  public String getApplicationType()
  {
    return this.applicationType;
  }

  public void setActivityConfirm(String activityConfirm)
  {
    this.activityConfirm = activityConfirm;
  }

  public String getActivityConfirm()
  {
    return this.activityConfirm;
  }

  public void setPrevSSI(String prevSSI)
  {
    this.prevSSI = prevSSI;
  }

  public String getPrevSSI()
  {
    return this.prevSSI;
  }

  public void setExistSSI(String existSSI)
  {
    this.existSSI = existSSI;
  }

  public String getExistSSI()
  {
    return this.existSSI;
  }

  public void setHandiCraftsStatus(String handiCraftsStatus)
  {
    this.handiCraftsStatus = handiCraftsStatus;
  }

  public String getHandiCraftsStatus()
  {
    return this.handiCraftsStatus;
  }

  public void setDcHandicraftsStatus(String dcHandicraftsStatus) {
    this.dcHandicraftsStatus = dcHandicraftsStatus;
  }

  public String getDcHandicraftsStatus()
  {
    return this.dcHandicraftsStatus;
  }

  public void setDcHandlooms(String dcHandlooms) {
    this.dcHandlooms = dcHandlooms;
  }

  public String getDcHandlooms()
  {
    return this.dcHandlooms;
  }

  public void setDcHandloomsStatus(String dcHandloomsStatus) {
    this.dcHandloomsStatus = dcHandloomsStatus;
  }

  public String getDcHandloomsStatus()
  {
    return this.dcHandloomsStatus;
  }

  public void setWeaverCreditScheme(String weaverCreditScheme) {
    this.WeaverCreditScheme = weaverCreditScheme;
  }

  public String getWeaverCreditScheme()
  {
    return this.WeaverCreditScheme;
  }

  public void setHandloomchk(String handloomchk) {
    this.handloomchk = handloomchk;
  }

  public String getHandloomchk()
  {
    return this.handloomchk;
  }
  public String getUdyogAdharNo() {
		return udyogAdharNo;
	}

	public void setUdyogAdharNo(String udyogAdharNo) {
		this.udyogAdharNo = udyogAdharNo;
	}

	public String getBankAcNo() {
		return bankAcNo;
	}

	public void setBankAcNo(String bankAcNo) {
		this.bankAcNo = bankAcNo;
	}

	@Override
	public String toString() {
		System.out.println("DKR");
		return "Application [mliID=" + mliID + ", mliBranchName=" + mliBranchName + ", mliBranchCode=" + mliBranchCode
				+ ", exposurelmtAmt=" + exposurelmtAmt + ", mliRefNo=" + mliRefNo + ", borrowerDetails="
				+ borrowerDetails + ", projectOutlayDetails=" + projectOutlayDetails + ", rehabilitation="
				+ rehabilitation + ", compositeLoan=" + compositeLoan + ", loanType=" + loanType + ", applicationType="
				+ applicationType + ", scheme=" + scheme + ", termLoan=" + termLoan + ", wc=" + wc + ", approvedAmount="
				+ approvedAmount + ", sanctionedAmount=" + sanctionedAmount + ", reapprovedAmount=" + reapprovedAmount
				+ ", enhancementAmount=" + enhancementAmount + ", docRefNo=" + docRefNo + ", reapprovalRemarks="
				+ reapprovalRemarks + ", cgpan=" + cgpan + ", cgpanReference=" + cgpanReference + ", userId=" + userId
				+ ", bankId=" + bankId + ", zoneId=" + zoneId + ", branchId=" + branchId + ", appRefNo=" + appRefNo
				+ ", wcAppRefNo=" + wcAppRefNo + ", regionId=" + regionId + ", NPA=" + NPA + ", ssiRef=" + ssiRef
				+ ", collateralSecDtls=" + collateralSecDtls + ", outstandingAmount=" + outstandingAmount + ", ITPAN="
				+ ITPAN + ", subsidyProvided=" + subsidyProvided + ", submittedDate=" + submittedDate
				+ ", sanctionedDate=" + sanctionedDate + ", activity=" + activity + ", approvedDate=" + approvedDate
				+ ", guaranteeStartDate=" + guaranteeStartDate + ", appExpiryDate=" + appExpiryDate + ", remarks="
				+ remarks + ", prevSSI=" + prevSSI + ", existSSI=" + existSSI + ", status=" + status + ", projectType="
				+ projectType + ", theRepaymentDetail=" + theRepaymentDetail + ", securitization=" + securitization
				+ ", mcgfDetails=" + mcgfDetails + ", guaranteeFee=" + guaranteeFee + ", subSchemeName=" + subSchemeName
				+ ", existingRemarks=" + existingRemarks + ", additionalTC=" + additionalTC + ", wcEnhancement="
				+ wcEnhancement + ", wcRenewal=" + wcRenewal + ", isVerified=" + isVerified + ", zoneName=" + zoneName
				+ ", coFinanceTaken1=" + coFinanceTaken1 + ", district=" + district + ", state=" + state + ", sex="
				+ sex + ", socialCategory=" + socialCategory + ", internalRate=" + internalRate + ", externalRate="
				+ externalRate + ", handiCrafts=" + handiCrafts + ", dcHandicrafts=" + dcHandicrafts + ", icardNo="
				+ icardNo + ", icardIssueDate=" + icardIssueDate + ", jointFinance=" + jointFinance + ", jointcgpan="
				+ jointcgpan + ", activityConfirm=" + activityConfirm + ", handiCraftsStatus=" + handiCraftsStatus
				+ ", dcHandicraftsStatus=" + dcHandicraftsStatus + ", dcHandlooms=" + dcHandlooms
				+ ", dcHandloomsStatus=" + dcHandloomsStatus + ", WeaverCreditScheme=" + WeaverCreditScheme
				+ ", handloomchk=" + handloomchk + ", isPrimarySecurity=" + isPrimarySecurity + ", handloomSchName="
				+ handloomSchName + ", internalRating=" + internalRating + ", internalratingProposal="
				+ internalratingProposal + ", investmentGrade=" + investmentGrade + ", subsidyType=" + subsidyType
				+ ", subsidyOther=" + subsidyOther + ", udyogAdharNo=" + udyogAdharNo + ", bankAcNo=" + bankAcNo
				+ ", FBammtlimit=" + FBammtlimit + ", gstNo=" + gstNo + ", gstState=" + gstState + ", stateCode="
				+ stateCode + ", gst=" + gst + ", exposureFbId=" + exposureFbId + ", exposureFbIdY=" + exposureFbIdY
				+ ", exposureFbIdN=" + exposureFbIdN + ", hybridSecurity=" + hybridSecurity
				+ ", movCollateratlSecurityAmt=" + movCollateratlSecurityAmt + ", immovCollateratlSecurityAmt="
				+ immovCollateratlSecurityAmt + ", totalMIcollatSecAmt=" + totalMIcollatSecAmt + ", proMobileNo="
				+ proMobileNo + ", promDirDefaltFlg=" + promDirDefaltFlg + ", credBureKeyPromScor="
				+ credBureKeyPromScor + ", credBurePromScor2=" + credBurePromScor2 + ", credBurePromScor3="
				+ credBurePromScor3 + ", credBurePromScor4=" + credBurePromScor4 + ", credBurePromScor5="
				+ credBurePromScor5 + ", credBureName1=" + credBureName1 + ", credBureName2=" + credBureName2
				+ ", credBureName3=" + credBureName3 + ", credBureName4=" + credBureName4 + ", credBureName5="
				+ credBureName5 + ", cibilFirmMsmeRank=" + cibilFirmMsmeRank + ", expCommerScor=" + expCommerScor
				+ ", promBorrNetWorth=" + promBorrNetWorth + ", promContribution=" + promContribution
				+ ", promGAssoNPA1YrFlg=" + promGAssoNPA1YrFlg + ", promBussExpYr=" + promBussExpYr + ", salesRevenue="
				+ salesRevenue + ", taxPBIT=" + taxPBIT + ", interestPayment=" + interestPayment
				+ ", taxCurrentProvisionAmt=" + taxCurrentProvisionAmt + ", totCurrentAssets=" + totCurrentAssets
				+ ", totCurrentLiability=" + totCurrentLiability + ", totTermLiability=" + totTermLiability
				+ ", exuityCapital=" + exuityCapital + ", preferenceCapital=" + preferenceCapital + ", reservesSurplus="
				+ reservesSurplus + ", repaymentDueNyrAmt=" + repaymentDueNyrAmt + ", existGreenFldUnitType="
				+ existGreenFldUnitType + ", opratIncome=" + opratIncome + ", profAftTax=" + profAftTax + ", networth="
				+ networth + ", debitEqtRatioUnt=" + debitEqtRatioUnt + ", debitSrvCoverageRatioTl="
				+ debitSrvCoverageRatioTl + ", currentRatioWc=" + currentRatioWc + ", debitEqtRatio=" + debitEqtRatio
				+ ", debitSrvCoverageRatio=" + debitSrvCoverageRatio + ", currentRatios=" + currentRatios
				+ ", creditBureauChiefPromScor=" + creditBureauChiefPromScor + ", totalAssets=" + totalAssets
				+ ", itpan=" + itpan + ", ssiUnitName=" + ssiUnitName + ", gurAmt=" + gurAmt + ", npaDate=" + npaDate
				+ ", adharNo=" + adharNo + "]";
	}



	
}