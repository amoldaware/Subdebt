/*
 * Created on Jan 8, 2004 
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.cgtsi.reports;

import java.io.Serializable;

import java.util.Date;
                
//import java.util.Date;

/**
 * @author RT14509
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ApplicationReport implements Serializable 
{
	
	
	private String ssiName;
	private String bid;
	private String address;
	private String district;
	private String state;
	private String unitType;
	private String chiefPromoter;
	private String itpan;
	private String gender;
	private String others;
	private String registrationNumber;
	private Date startDate;
  //added expiryDate by sukumar@path on 30-08-2008
  private Date expiryDate;
  //added closuredate and instrumentnumber by sukumar@path on 16-07-2009
  private Date closureDate;
  private String ddNum;
  
	private int employees;
	private String industryType;
	private String industrySector;
	private double turnover;
	private double export;
	private double outstanding;
	private Date applicationDate;
	private String applicationDateString;
	private String referenceNumber;
	private String cgpan;
  //added dan, payId by sukumar@path
  private String dan;
  private String payId;
  
	private String memberId;
	private String status;
	private String remarks;
	private String loanType;
	private double projectOutlay;
	private String city;
	
	private double tcSanctioned;
	private Date tcSanctionedOn; 
	private double tcRate;
	private double tcOutstanding;
	private double tcPlr;
	private int tcTenure;
	private int repaymentMoratorium;
	private String repaymentPeriodicity;
	private Date firstInstallmentDueDate;
	private int numberOfInstallments;
	private double tcPromoterContribution; 
	private double tcSubsidy;
	private double tcEquity;
	private double tcProjectCost;
	private double disbursementAmount;
	private Date disbursementDate;
	private String finalDisbursement;
	
	private double wcFbSanctioned;
	private double wcNfbSanctioned;
	private double totalSanctioned;
	private Date wcFbSanctionedOn;
	private Date wcNfbSanctionedOn;
	private double wcFbPrincipalOutstanding;
	private double wcFbInterestOutstanding;
	private double wcNfbPrincipalOutstanding;
	private double wcNfbInterestOutstanding; 
	private double wcPlr;
	private int wcTenure;
	private double wcInterest;
	private double wcPromoterContribution;
	private double wcSubsidy;
	private double wcEquity;
	private double wcProjectCost;
 
 //Added by sukant for History Report
 private String appRefNo;
 private String ssiReferenceNumber;
 private String appMliBranchCode;
 private String appBankAppRefNo;
 private String appCompositeLoan;
 private String usrId;
 private String appLoanType;
 private String appCollateralSecurityTaken; 
 private String appThirdPartyGuarTaken; 
 private String appSubsidySchemeName;
 private String appRehabilitation;
 private Date appApprovedDateTime;
 private String appApprovedAmount;
 private String appGuaranteeFee;
 private Date appGuarStartDateTime;
 
 private String dciAmountRaised; 
 private String dciAppropriationFlag;
 private String dbrAmount;
 private String dbrFinalDisbursementFlag;
 private Date dbrDt;
 private String dbrCreatedModifiedBy;
 private Date dbrCreatedModifiedDt;
 private String typeOfActivity;
///End by Sukant //////////////////
//added bank Name,zone Name and Branch Name,promoterDob,socialCategory and Scheme Name by sukumar@path on 25-AUg-2010
private String bankName;
private String zoneName;
private String branchName;

   
private String schemeName;
private String socialCategory;
private Date promoterDob;
//added by sukumar@path for displaying LEGAL, CLAIM and NPA details
private Date npaEffectiveDt;
private Date reportingDt;
private double outstandingAsonNPA;
private String npaReasons;
private String suitNumber;
private String forumName;
private String legalForum;
private String location;
private Date legalFilingDt;
private double legalClaimAmt;

private String claimRefNo;
private String cgClan;
private Date claimFilingDt;
private double claimApprovedAmt;
private Date claimApprovedDt;
private String claimStatus;
private Date claimDate;

    private String handiCraftsAccFlag;
       private String dcHandicraftsReimb;
       private String appDcIcardNo;
       private Date appDcIcardIssueDate;
   	private String revivalStatus;
    private Date revivalDate;
       public String getRevivalStatus() {
		return revivalStatus;
	}
	public void setRevivalStatus(String revivalStatus) {
		this.revivalStatus = revivalStatus;
	}
	public Date getRevivalDate() {
		return revivalDate;
	}
	public void setRevivalDate(Date revivalDate) {
		this.revivalDate = revivalDate;
	}



	
	public ApplicationReport() {
		
	}  
  /**
   * 
   * @return claimDate
   */
  public Date getClaimDate()
  {
    return this.claimDate;
  }
  /**
   * 
   * @param claimDate
   */
  public void setClaimDate(Date claimDate)
  {
   this.claimDate = claimDate;
  }
  /**
   * 
   * @return claimStatus
   */
  public String getClaimStatus()
  {
   return this.claimStatus;
  }
  /**
   * 
   * @param claimStatus
   */
  public void setClaimStatus(String claimStatus)
  {
   this.claimStatus =claimStatus;
  }
  /**
   * 
   * @return claimApprovedDt
   */
  public Date getClaimApprovedDt()
  {
   return this.claimApprovedDt;
  }
  /**
   * 
   * @param claimApprovedDt
   */
  public void setClaimApprovedDt(Date claimApprovedDt)
  {
    this.claimApprovedDt = claimApprovedDt;
  }
  /**
   * 
   * @return claimApprovedAmt
   */
  public double getClaimApprovedAmt()
  {
   return this.claimApprovedAmt;
  }
  /**
   * 
   * @param claimApprovedAmt
   */
  public void setClaimApprovedAmt(double claimApprovedAmt)
  {
   this.claimApprovedAmt=claimApprovedAmt;
  }
  
  
  /**
   * 
   * @return claimFilingDt
   */
  public Date getClaimFilingDt()
  {
   return this.claimFilingDt;
  }
  /**
   * 
   * @param claimFilingDt
   */
  public void setClaimFilingDt(Date claimFilingDt)
  {
   this.claimFilingDt =claimFilingDt;
  }
  
  
  /**
   * 
   * @return cgClan
   */
  public String getCgClan()
  {
   return this.cgClan;
  }
  /**
   * 
   * @param cgClan
   */
  public void setCgClan(String cgClan)
  {
   this.cgClan =cgClan;
  }
  
  /**
   * 
   * @return claimRefNo
   */
  public String getClaimRefNo()
  {
   return this.claimRefNo;
  }
  /**
   * 
   * @param claimRefNo
   */
  public void setClaimRefNo(String claimRefNo)
  {
   this.claimRefNo =claimRefNo;
  }
  
  /**
   * 
   * @return legalClaimAmt
   */
  public double getLegalClaimAmt()
  {
    return this.legalClaimAmt;
  }
  /**
   * 
   * @param legalClaimAmt
   */
  public void setLegalClaimAmt(double legalClaimAmt)
  {
   this.legalClaimAmt  =legalClaimAmt;
  }
  /**
   * 
   * @return legalFilingDt
   */
  public Date getLegalFilingDt()
  {
   return this.legalFilingDt;
  }
  /**
   * 
   * @param legalFilingDt
   */
  public void setLegalFilingDt(Date legalFilingDt)
  {
   this.legalFilingDt = legalFilingDt;
  }
  
  /**
   * 
   * @return location
   */
  public String getLocation()
  {
   return this.location;
  }
  /**
   * 
   * @param location
   */
  public void setLocation(String location)
  {
    this.location = location;
  }
  
  
  /**
   * 
   * @return forumName
   */
  public String getForumName()
  {
   return this.forumName;
  }
  /**
   * 
   * @param forumName
   */
  public void setForumName(String forumName)
  {
   this.forumName=forumName;
  }
  /**
   * 
   * @return legalForum
   */
  public String getLegalForum()
  {
   return this.legalForum;
  }
  /**
   * 
   * @param legalForum
   */
  public void setLegalForum(String legalForum)
  {
   this.legalForum =legalForum;
  }
  
  
  
  
  
  /**
   * 
   * @return suitNumber
   */
  public String getSuitNumber()
  {
   return this.suitNumber = suitNumber;
  }  
  /**
   * 
   * @param suitNumber
   */
  public void setSuitNumber(String suitNumber)
  {
   this.suitNumber = suitNumber;
  }
  
  /**
   * 
   * @return npaReasons
   */
  public String getNpaReasons()
  {
   return this.npaReasons;
  }
  /**
   * 
   * @param npaReasons
   */
  public void setNpaReasons(String npaReasons)
  {
   this.npaReasons = npaReasons;
  }
  /**
   * 
   * @return outstandingAsonNPA
   */
  public double getOutstandingAsonNPA()
  {
   return this.outstandingAsonNPA;
  }
  /**
   * 
   * @param outstandingAsonNPA
   */
  public void setOutstandingAsonNPA(double outstandingAsonNPA)
  {
   this.outstandingAsonNPA = outstandingAsonNPA;
  }
  
  /**
   * 
   * @return reportingDt
   */
  public Date getReportingDt()
  {
   return this.reportingDt;
  }
  /**
   * 
   * @param reportingDt
   */
  public void setReportingDt(Date reportingDt)
  {
   this.reportingDt=reportingDt;
  }
  /**
   * 
   * @return npaEffectiveDt
   */
  public Date getNpaEffectiveDt()
  {
   return this.npaEffectiveDt;
  }
  /**
   * 
   * @param npaEffectiveDt
   */
  public void setNpaEffectiveDt(Date npaEffectiveDt)
  {
   this.npaEffectiveDt = npaEffectiveDt;
  }
  
  /**
   * 
   * @return socialCategory
   */
  public String getSocialCategory()
  {
   return this.socialCategory;
  }
  /**
   * @param socialCategory
   */
  public void setSocialCategory(String socialCategory)
  {
   this.socialCategory = socialCategory;
  }
  /**
   * 
   * @return promoterDob
   */
  public Date getPromoterDob()
  {
   return this.promoterDob;
  }
  /**
   * 
   * @param promoterDob
   */
  public void setPromoterDob(Date promoterDob)
  {
   this.promoterDob = promoterDob;
  }
/**
   * 
   * @return bankName
   */
public String getBankName()
{
 return this.bankName;
}
/**
   * 
   * @param bankName
   */
public void setBankName(String bankName)
{
 this.bankName=bankName;
}
/**
   * 
   * @return zoneName
   */
public String getZoneName()
{
 return this.zoneName;
}
/**
   * 
   * @param zoneName
   */
public void setZoneName(String zoneName)
{
 this.zoneName=zoneName;
}
/**
   * 
   * @return branchName
   */
public String getBranchName()
{
 return this.branchName;
}
/**
   * 
   * @param branchName
   */
public void setBranchName(String branchName)
{
 this.branchName=branchName;
}
   
  /**
   * 
   * @return schemeName
   */
public String getSchemeName()
{
 return this.schemeName;
}
/**
   * 
   * @param schemeName
   */
public void setSchemeName(String schemeName)
{
 this.schemeName= schemeName;
}
public String getTypeOfActivity()
{
  return typeOfActivity;
}
public void setTypeOfActivity(String typeOfAct)
{
  typeOfActivity = typeOfAct;
}
	/**
	 * @return
	 */
	public String getSsiName() {
		return ssiName;
	}

	/**
	 * @param string
	 */
	public void setSsiName(String string) {
		ssiName = string;
	}

	/**
	 * @return
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param string
	 */
	public void setAddress(String string) {
		address = string;
	}

	/**
	 * @return
	 */
	public String getDistrict() {
		return district;
	}

	/**
	 * @param string
	 */
	public void setDistrict(String string) {
		district = string;
	}

	/**
	 * @return
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param string
	 */
	public void setState(String string) {
		state = string;
	}

	/**
	 * @return
	 */
	public String getUnitType() {
		return unitType;
	}

	/**
	 * @param string
	 */
	public void setUnitType(String string) {
		unitType = string;
	}

	/**
	 * @return
	 */
	public String getChiefPromoter() {
		return chiefPromoter;
	}

	/**
	 * @param string
	 */
	public void setChiefPromoter(String string) {
		chiefPromoter = string;
	}

	/**
	 * @return
	 */
	public String getItpan() {
		return itpan;
	}

	/**
	 * @param i
	 */
	public void setItpan(String i) {
		itpan = i;
	}

	/**
	 * @return
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param string
	 */
	public void setGender(String string) {
		gender = string;
	}

	/**
	 * @return
	 */
	public String getOthers() {
		return others;
	}

	/**
	 * @param string
	 */
	public void setOthers(String string) {
		others = string;
	}

	/**
	 * @return
	 */
	public String getRegistrationNumber() {
		return registrationNumber;
	}

	/**
	 * @param i
	 */
	public void setRegistrationNumber(String i) {
		registrationNumber = i;
	}

	/**
	 * @return
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param date
	 */
	public void setStartDate(Date date) {
		startDate = date;
	}

/**
	 * @return
	 */
	public Date getExpiryDate() {
		return expiryDate;
	}

	/**
	 * @param date
	 */
	public void setExpiryDate(Date date) {
		expiryDate = date;
	}
  
  /**
	 * @return
	 */
	public Date getClosureDate() {
		return closureDate;
	}

	/**
	 * @param date
	 */
	public void setClosureDate(Date date) {
		closureDate = date;
	}
  
  /**
   * return ddNum
   */
  public String getDdNum(){
   return ddNum;
  }
  /**
   * @param ddNum
   */
  public void setDdNum(String dd){
   ddNum=dd;
  }
  
  
	/**
	 * @return
	 */
	public int getEmployees() {
		return employees;
	}

	/**
	 * @param i
	 */
	public void setEmployees(int i) {
		employees = i;
	}

	/**
	 * @return
	 */
	public String getIndustryType() {
		return industryType;
	}

	/**
	 * @param string
	 */
	public void setIndustryType(String string) {
		industryType = string;
	}

	/**
	 * @return
	 */
	public double getTurnover() {
		return turnover;
	}

	/**
	 * @param d
	 */
	public void setTurnover(double d) {
		turnover = d;
	}

	/**
	 * @return
	 */
	public double getExport() {
		return export;
	}

	/**
	 * @param d
	 */
	public void setExport(double d) {
		export = d;
	}



	/**
	 * @return
	 */
	public String getCgpan() {
		return cgpan;
	}

	/**
	 * @param string
	 */
	public void setCgpan(String string) {
		cgpan = string;
	}

/**
	 * @return
	 */
	public String getPayId() {
		return payId;
	}

	/**
	 * @param string
	 */
	public void setPayId(String string) {
		payId = string;
	}

	/**
	 * @return
	 */
	public String getDan() {
		return dan;
	}

	/**
	 * @param string
	 */
	public void setDan(String string) {
		dan = string;
	}
	/**
	 * @return
	 */
	public Date getApplicationDate() {
		return applicationDate;
	}

	/**
	 * @param date
	 */
	public void setApplicationDate(Date date) {
		applicationDate = date;
	}

	/**
	 * @return
	 */
	public String getReferenceNumber() {
		return referenceNumber;
	}

	/**
	 * @param string
	 */
	public void setReferenceNumber(String string) {
		referenceNumber = string;
	}

	/**
	 * @return
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param string
	 */
	public void setStatus(String string) {
		status = string;
	}

	/**
	 * @return
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param string
	 */
	public void setRemarks(String string) {
		remarks = string;
	}

	/**
	 * @return
	 */
	public String getMemberId() {
		return memberId;
	}

	/**
	 * @param string
	 */
	public void setMemberId(String string) {
		memberId = string;
	}

	/**
	 * @return
	 */
	public String getLoanType() {
		return loanType;
	}

	/**
	 * @param string
	 */
	public void setLoanType(String string) {
		loanType = string;
	}

	/**
	 * @return
	 */
	public double getTcSanctioned() {
		return tcSanctioned;
	}

	/**
	 * @param d
	 */
	public void setTcSanctioned(double d) {
		tcSanctioned = d;
	}

	/**
	 * @return
	 */
	public Date getTcSanctionedOn() {
		return tcSanctionedOn;
	}

	/**
	 * @param date
	 */
	public void setTcSanctionedOn(Date date) {
		tcSanctionedOn = date;
	}



	/**
	 * @return
	 */
	public double getTcOutstanding() {
		return tcOutstanding;
	}

	/**
	 * @param d
	 */
	public void setTcOutstanding(double d) {
		tcOutstanding = d;
	}

	/**
	 * @return
	 */
	public double getDisbursementAmount() {
		return disbursementAmount;
	}

	/**
	 * @param d
	 */
	public void setDisbursementAmount(double d) {
		disbursementAmount = d;
	}

	/**
	 * @return
	 */
	public Date getDisbursementDate() {
		return disbursementDate;
	}

	/**
	 * @param date
	 */
	public void setDisbursementDate(Date date) {
		disbursementDate = date;
	}

	/**
	 * @return
	 */
	public String getFinalDisbursement() {
		return finalDisbursement;
	}

	/**
	 * @param string
	 */
	public void setFinalDisbursement(String string) {
		finalDisbursement = string;
	}

	/**
	 * @return
	 */
	public double getWcFbSanctioned() {
		return wcFbSanctioned;
	}

	/**
	 * @param d
	 */
	public void setWcFbSanctioned(double d) {
		wcFbSanctioned = d;
	}

	/**
	 * @return
	 */
	public double getWcNfbSanctioned() {
		return wcNfbSanctioned;
	}

	/**
	 * @param d
	 */
	public void setWcNfbSanctioned(double d) {
		wcNfbSanctioned = d;
	}

	/**
	 * @return
	 */
	public Date getWcFbSanctionedOn() {
		return wcFbSanctionedOn;
	}

	/**
	 * @param date
	 */
	public void setWcFbSanctionedOn(Date date) {
		wcFbSanctionedOn = date;
	}

	/**
	 * @return
	 */
	public Date getWcNfbSanctionedOn() {
		return wcNfbSanctionedOn;
	}

	/**
	 * @param date
	 */
	public void setWcNfbSanctionedOn(Date date) {
		wcNfbSanctionedOn = date;
	}



	/**
	 * @return
	 */
	public double getWcFbPrincipalOutstanding() {
		return wcFbPrincipalOutstanding;
	}

	/**
	 * @param d
	 */
	public void setWcFbPrincipalOutstanding(double d) {
		wcFbPrincipalOutstanding = d;
	}

	/**
	 * @return
	 */
	public double getWcFbInterestOutstanding() {
		return wcFbInterestOutstanding;
	}

	/**
	 * @param d
	 */
	public void setWcFbInterestOutstanding(double d) {
		wcFbInterestOutstanding = d;
	}

	/**
	 * @return
	 */
	public double getWcNfbPrincipalOutstanding() {
		return wcNfbPrincipalOutstanding;
	}

	/**
	 * @param d
	 */
	public void setWcNfbPrincipalOutstanding(double d) {
		wcNfbPrincipalOutstanding = d;
	}

	/**
	 * @return
	 */
	public double getWcNfbInterestOutstanding() {
		return wcNfbInterestOutstanding;
	}

	/**
	 * @param d
	 */
	public void setWcNfbInterestOutstanding(double d) {
		wcNfbInterestOutstanding = d;
	}

	/**
	 * @return
	 */
	public double getOutstanding() {
		return outstanding;
	}

	/**
	 * @param d
	 */
	public void setOutstanding(double d) {
		outstanding = d;
	}


	/**
	 * @return
	 */
	public Date getFirstInstallmentDueDate() {
		return firstInstallmentDueDate;
	}

	/**
	 * @param date
	 */
	public void setFirstInstallmentDueDate(Date date) {
		firstInstallmentDueDate = date;
	}

	/**
	 * @return
	 */
	public int getNumberOfInstallments() {
		return numberOfInstallments;
	}

	/**
	 * @param i
	 */
	public void setNumberOfInstallments(int i) {
		numberOfInstallments = i;
	}



	/**
	 * @return
	 */
	public double getTcPromoterContribution() {
		return tcPromoterContribution;
	}

	
	/**
	 * @param d
	 */
	public void setTcPromoterContribution(double d) {
		tcPromoterContribution = d;
	}


	/**
	 * @return
	 */
	public double getTcEquity() {
		return tcEquity;
	}

	/**
	 * @param d
	 */
	public void setTcEquity(double d) {
		tcEquity = d;
	}

	/**
	 * @return
	 */
	public double getTcSubsidy() {
		return tcSubsidy;
	}

	/**
	 * @param d
	 */
	public void setTcSubsidy(double d) {
		tcSubsidy = d;
	}

	/**
	 * @return
	 */
	public double getWcPromoterContribution() {
		return wcPromoterContribution;
	}

	/**
	 * @param d
	 */
	public void setWcPromoterContribution(double d) {
		wcPromoterContribution = d;
	}

	/**
	 * @return
	 */
	public double getWcSubsidy() {
		return wcSubsidy;
	}

	/**
	 * @param d
	 */
	public void setWcSubsidy(double d) {
		wcSubsidy = d;
	}

	/**
	 * @return
	 */
	public double getWcEquity() {
		return wcEquity;
	}

	/**
	 * @param d
	 */
	public void setWcEquity(double d) {
		wcEquity = d;
	}

	/**
	 * @return
	 */
	public double getWcProjectCost() {
		return wcProjectCost;
	}

	/**
	 * @param d
	 */
	public void setWcProjectCost(double d) {
		wcProjectCost = d;
	}

	/**
	 * @return
	 */
	public double getTcProjectCost() {
		return tcProjectCost;
	}

	/**
	 * @param d
	 */
	public void setTcProjectCost(double d) {
		tcProjectCost = d;
	}

	/**
	 * @return
	 */
	public int getTcTenure() {
		return tcTenure;
	}

	/**
	 * @return
	 */
	public int getWcTenure() {
		return wcTenure;
	}

	/**
	 * @param i
	 */
	public void setTcTenure(int i) {
		tcTenure = i;
	}

	/**
	 * @param i
	 */
	public void setWcTenure(int i) {
		wcTenure = i;
	}

	/**
	 * @return
	 */
	public double getTotalSanctioned() {
		return totalSanctioned;
	}

	/**
	 * @param d
	 */
	public void setTotalSanctioned(double d) {
		totalSanctioned = d;
	}

	/**
	 * @return
	 */
	public String getApplicationDateString() {
		return applicationDateString;
	}

	/**
	 * @param string
	 */
	public void setApplicationDateString(String string) {
		applicationDateString = string;
	}

	/**
	 * @return
	 */
	public String getIndustrySector() {
		return industrySector;
	}

	/**
	 * @param string
	 */
	public void setIndustrySector(String string) {
		industrySector = string;
	}

	/**
	 * @return
	 */
	public String getBid() {
		return bid;
	}

	/**
	 * @param string
	 */
	public void setBid(String string) {
		bid = string;
	}

	/**
	 * @return
	 */
	public double getTcPlr() {
		return tcPlr;
	}

	/**
	 * @return
	 */
	public double getTcRate() {
		return tcRate;
	}

	/**
	 * @return
	 */
	public double getWcInterest() {
		return wcInterest;
	}

	/**
	 * @return
	 */
	public double getWcPlr() {
		return wcPlr;
	}

	/**
	 * @param d
	 */
	public void setTcPlr(double d) {
		tcPlr = d;
	}

	/**
	 * @param d
	 */
	public void setTcRate(double d) {
		tcRate = d;
	}

	/**
	 * @param d
	 */
	public void setWcInterest(double d) {
		wcInterest = d;
	}

	/**
	 * @param d
	 */
	public void setWcPlr(double d) {
		wcPlr = d;
	}

	/**
	 * @return
	 */
	public String getRepaymentPeriodicity() {
		return repaymentPeriodicity;
	}

	/**
	 * @param string
	 */
	public void setRepaymentPeriodicity(String string) {
		repaymentPeriodicity = string;
	}

	/**
	 * @return
	 */
	public int getRepaymentMoratorium() {
		return repaymentMoratorium;
	}

	/**
	 * @param i
	 */
	public void setRepaymentMoratorium(int i) {
		repaymentMoratorium = i;
	}

	/**
	 * @return
	 */
	public double getProjectOutlay() {
		return projectOutlay;
	}

	/**
	 * @param d
	 */
	public void setProjectOutlay(double d) {
		projectOutlay = d;
	}

	/**
	 * @return
	 */
	public String getCity() {  
		return city;
	}

	/**
	 * @param string
	 */
	public void setCity(String string) {
		city = string;
	}


  public void setAppRefNo(String appRefNo)
  {
    this.appRefNo = appRefNo;
  }


  public String getAppRefNo()
  {
    return appRefNo;
  }


  public void setSsiReferenceNumber(String ssiReferenceNumber)
  {
    this.ssiReferenceNumber = ssiReferenceNumber;
  }


  public String getSsiReferenceNumber()
  {
    return ssiReferenceNumber;
  }


  public void setAppMliBranchCode(String appMliBranchCode)
  {
    this.appMliBranchCode = appMliBranchCode;
  }


  public String getAppMliBranchCode()
  {
    return appMliBranchCode;
  }


  public void setAppBankAppRefNo(String appBankAppRefNo)
  {
    this.appBankAppRefNo = appBankAppRefNo;
  }


  public String getAppBankAppRefNo()
  {
    return appBankAppRefNo;
  }


  public void setAppCompositeLoan(String appCompositeLoan)
  {
    this.appCompositeLoan = appCompositeLoan;
  }


  public String getAppCompositeLoan()
  {
    return appCompositeLoan;
  }


  public void setUsrId(String usrId)
  {
    this.usrId = usrId;
  }


  public String getUsrId()
  {
    return usrId;
  }


  public void setAppLoanType(String appLoanType)
  {
    this.appLoanType = appLoanType;
  }


  public String getAppLoanType()
  {
    return appLoanType;
  }


  public void setAppCollateralSecurityTaken(String appCollateralSecurityTaken)
  {
    this.appCollateralSecurityTaken = appCollateralSecurityTaken;
  }


  public String getAppCollateralSecurityTaken()
  {
    return appCollateralSecurityTaken;
  }


  public void setAppThirdPartyGuarTaken(String appThirdPartyGuarTaken)
  {
    this.appThirdPartyGuarTaken = appThirdPartyGuarTaken;
  }


  public String getAppThirdPartyGuarTaken()
  {
    return appThirdPartyGuarTaken;
  }


  public void setAppSubsidySchemeName(String appSubsidySchemeName)
  {
    this.appSubsidySchemeName = appSubsidySchemeName;
  }


  public String getAppSubsidySchemeName()
  {
    return appSubsidySchemeName;
  }


  public void setAppRehabilitation(String appRehabilitation)
  {
    this.appRehabilitation = appRehabilitation;
  }


  public String getAppRehabilitation()
  {
    return appRehabilitation;
  }


  public void setAppApprovedDateTime(Date appApprovedDateTime)
  {
    this.appApprovedDateTime = appApprovedDateTime;
  }


  public Date getAppApprovedDateTime()
  {
    return appApprovedDateTime;
  }


  public void setAppApprovedAmount(String appApprovedAmount)
  {
    this.appApprovedAmount = appApprovedAmount;
  }


  public String getAppApprovedAmount()
  {
    return appApprovedAmount;
  }


  public void setAppGuaranteeFee(String appGuaranteeFee)
  {
    this.appGuaranteeFee = appGuaranteeFee;
  }


  public String getAppGuaranteeFee()
  {
    return appGuaranteeFee;
  }


  public void setAppGuarStartDateTime(Date appGuarStartDateTime)
  {
    this.appGuarStartDateTime = appGuarStartDateTime;
  }


  public Date getAppGuarStartDateTime()
  {
    return appGuarStartDateTime;
  }


  public void setDciAmountRaised(String dciAmountRaised)
  {
    this.dciAmountRaised = dciAmountRaised;
  }


  public String getDciAmountRaised()
  {
    return dciAmountRaised;
  }


  public void setDciAppropriationFlag(String dciAppropriationFlag)
  {
    this.dciAppropriationFlag = dciAppropriationFlag;
  }


  public String getDciAppropriationFlag()
  {
    return dciAppropriationFlag;
  }


  public void setDbrAmount(String dbrAmount)
  {
    this.dbrAmount = dbrAmount;
  }


  public String getDbrAmount()
  {
    return dbrAmount;
  }


  public void setDbrFinalDisbursementFlag(String dbrFinalDisbursementFlag)
  {
    this.dbrFinalDisbursementFlag = dbrFinalDisbursementFlag;
  }


  public String getDbrFinalDisbursementFlag()
  {
    return dbrFinalDisbursementFlag;
  }


  public void setDbrDt(Date dbrDt)
  {
    this.dbrDt = dbrDt;
  }


  public Date getDbrDt()
  {
    return dbrDt;
  }


  public void setDbrCreatedModifiedBy(String dbrCreatedModifiedBy)
  {
    this.dbrCreatedModifiedBy = dbrCreatedModifiedBy;
  }


  public String getDbrCreatedModifiedBy()
  {
    return dbrCreatedModifiedBy;
  }


  public void setDbrCreatedModifiedDt(Date dbrCreatedModifiedDt)
  {
    this.dbrCreatedModifiedDt = dbrCreatedModifiedDt;
  }


  public Date getDbrCreatedModifiedDt()
  {
    return dbrCreatedModifiedDt;
  }
    public void setHandiCraftsAccFlag(String handiCraftsAccFlag) {
          this.handiCraftsAccFlag = handiCraftsAccFlag;
      }

      public String getHandiCraftsAccFlag() {
          return handiCraftsAccFlag;
      }

      public void setDcHandicraftsReimb(String dcHandicraftsReimb) {
          this.dcHandicraftsReimb = dcHandicraftsReimb;
      }

      public String getDcHandicraftsReimb() {
          return dcHandicraftsReimb;
      }

      public void setAppDcIcardNo(String appDcIcardNo) {
          this.appDcIcardNo = appDcIcardNo;
      }

      public String getAppDcIcardNo() {
          return appDcIcardNo;
      }

      public void setAppDcIcardIssueDate(Date appDcIcardIssueDate) {
          this.appDcIcardIssueDate = appDcIcardIssueDate;
      }

      public Date getAppDcIcardIssueDate() {
          return appDcIcardIssueDate;
      }

    
}
