package com.cgtsi.actionform;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import org.apache.struts.validator.ValidatorActionForm;

public class APForm extends ValidatorActionForm
{
  private Map clearStatus = new TreeMap();
  private Map duplicateStatus = new TreeMap();
  private Map ineligibleStatus = new TreeMap();
  private Map ineligibleDupStatus = new TreeMap();

  private Map clearRemarks = new TreeMap();
  private Map duplicateRemarks = new TreeMap();
  private Map ineligibleRemarks = new TreeMap();
  private Map ineligibleDupRemarks = new TreeMap();
  

  
private Map clearAppRefNo = new TreeMap();
  private Map duplicateAppRefNo = new TreeMap();
  private Map ineligibleAppRefNo = new TreeMap();
  private Map ineligibleDupAppRefNo = new TreeMap();

  private Map clearApprovedAmt = new TreeMap();
  private Map clearRsfApprovedAmt = new TreeMap();
  private Map duplicateApprovedAmt = new TreeMap();
  private Map ineligibleApprovedAmt = new TreeMap();
  private Map ineligibleDupApprovedAmt = new TreeMap();

  private Map clearCreditAmt = new TreeMap();
  private Map duplicateCreditAmt = new TreeMap();
  private Map ineligibleCreditAmt = new TreeMap();
  private Map ineligibleDupCreditAmt = new TreeMap();

  private Map reApprovedAmt = new TreeMap();
  private Map reApprovalRemarks = new TreeMap();
  private Map cgpanNo = new TreeMap();
  private Map reapprovalStatus = new TreeMap();
  private Map eligibleCreditAmt = new TreeMap();
  private Map creditAmt = new TreeMap();

  private Map tempMap = new TreeMap();
  private Map clearTempMap = new TreeMap();
  private Map clearRsfTempMap = new TreeMap();
  private Map dupTempMap = new TreeMap();
  private Map ineligibleTempMap = new TreeMap();

  private Map tempRemMap = new TreeMap();
  private Map clearRemMap = new TreeMap();
  private Map clearRsfRemMap = new TreeMap();
  private Map dupRemMap = new TreeMap();
  private Map ineligibleRemMap = new TreeMap();

  private Map ineligibleReapproveMap = new TreeMap();
  private Map eligibleReapproveMap = new TreeMap();

  private Map tcAppRefNo = new TreeMap();
  private Map tcCgpan = new TreeMap();
  private Map tcDecision = new TreeMap();

  private Map wcAppRefNo = new TreeMap();
  private Map wcCgpan = new TreeMap();
  private Map wcDecision = new TreeMap();

  private Map appRefNo = new TreeMap();

  private String withinMlis = "";

  private ArrayList duplicateApplications = null;

  private ArrayList duplicateApplication = new ArrayList();

  private ArrayList holdApplications = new ArrayList();
  private ArrayList rejectedApplications = new ArrayList();
  private ArrayList approvedApplications = new ArrayList();
  private ArrayList pendingApplications = new ArrayList();

  private ArrayList tcClearApplications = null;
  private ArrayList wcClearApplications = null;

  private ArrayList specialMessagesList = new ArrayList();

  private ArrayList eligibleAppList = new ArrayList();

  private ArrayList eligibleNonDupApps = new ArrayList();
  private ArrayList eligibleNonDupRsfApps = new ArrayList();
  private ArrayList eligibleDupApps = new ArrayList();
  private ArrayList ineligibleNonDupApps = new ArrayList();
  private ArrayList ineligibleApps = new ArrayList();
  private ArrayList duplicateApps = new ArrayList();
  private ArrayList ineligibleDupApps = new ArrayList();
  private int intApplicationCount;
  private ArrayList clearApprovedApplications = new ArrayList();
  private ArrayList clearHoldApplications = new ArrayList();
  private ArrayList clearRejectedApplications = new ArrayList();
  private ArrayList clearPendingApplications = new ArrayList();

  private ArrayList dupApprovedApplications = new ArrayList();
  private ArrayList dupHoldApplications = new ArrayList();
  private ArrayList dupRejectedApplications = new ArrayList();
  private ArrayList dupPendingApplications = new ArrayList();

  private ArrayList ineligibleApprovedApplications = new ArrayList();
  private ArrayList ineligibleHoldApplications = new ArrayList();
  private ArrayList ineligibleRejectedApplications = new ArrayList();
  private ArrayList ineligiblePendingApplications = new ArrayList();

  private ArrayList ineligibleDupApprovedApplications = new ArrayList();
  private ArrayList ineligibleDupHoldApplications = new ArrayList();
  private ArrayList ineligibleDupRejectedApplications = new ArrayList();
  private ArrayList ineligibleDupPendingApplications = new ArrayList();

  private ArrayList tcApplications = new ArrayList();
  private ArrayList wcApplications = new ArrayList();

  private ArrayList tcConvertedApplications = new ArrayList();
  private ArrayList enhanceConvertedApplications = new ArrayList();
  private ArrayList renewConvertedApplications = new ArrayList();
  private ArrayList branchCodeList = new ArrayList();

  private int tcEntryIndex;
  private int wcEntryIndex;
  private String cgpan;
  private String remarks;
  private String exposureschemeid ;
  public String getExposureschemeid() {
	return exposureschemeid;
}

public void setExposureschemeid(String exposureschemeid) {
	this.exposureschemeid = exposureschemeid;
}

private String udyogAdharNo;
  private String bankAcNo;
  private String gst;

  private String branchName;
  private String stateName;
  

  private String termsAndConditionCheck;
  
  private String aadharNos;
  public String getAadharNos() {
	return aadharNos;
}

public void setAadharNos(String aadharNos) {
	this.aadharNos = aadharNos;
}

public String getTermsAndConditionCheck() {
	return termsAndConditionCheck;
}

public void setTermsAndConditionCheck(String termsAndConditionCheck) {
	this.termsAndConditionCheck = termsAndConditionCheck;
}

public String getBranchName() {
	return branchName;
}

public void setBranchName(String branchName) {
	this.branchName = branchName;
}
public String getGst() {
	return gst;
}

public void setGst(String gst) {
	this.gst = gst;
}
public String getStateName() {
	return stateName;
}

public void setStateName(String stateName) {
	this.stateName = stateName;
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

public ArrayList getDuplicateApplications()
  {
    return this.duplicateApplications;
  }

  public void setDuplicateApplications(ArrayList duplicateList)
  {
    this.duplicateApplications = duplicateList;
  }

  public void setCgpan(String cgpan)
  {
    this.cgpan = cgpan;
  }

  public String getCgpan()
  {
    return this.cgpan;
  }

  public void setRemarks(String remark)
  {
    this.remarks = remark;
  }

  public String getRemarks()
  {
    return this.remarks;
  }

  public String getWithinMlis()
  {
    return this.withinMlis;
  }

  public void setWithinMlis(String aWithinMlis)
  {
    this.withinMlis = aWithinMlis;
  }

  public Map getAppRefNo()
  {
    return this.appRefNo;
  }

  public void setAppRefNo(Map aAppRefNo)
  {
    this.appRefNo = aAppRefNo;
  }

  public Map getReApprovedAmt()
  {
    return this.reApprovedAmt;
  }

  public void setReApprovedAmt(Map aReApprovedAmt)
  {
    this.reApprovedAmt = aReApprovedAmt;
  }

  public void setDuplicateApplication(ArrayList aDuplicateApplication)
  {
    this.duplicateApplication = aDuplicateApplication;
  }

  public ArrayList getDuplicateApplication()
  {
    return this.duplicateApplication;
  }

  public void setHoldApplications(ArrayList aHoldApplications)
  {
    this.holdApplications = aHoldApplications;
  }

  public ArrayList getHoldApplications()
  {
    return this.holdApplications;
  }

  public void setRejectedApplications(ArrayList aRejectedApplications)
  {
    this.rejectedApplications = aRejectedApplications;
  }

  public ArrayList getRejectedApplications()
  {
    return this.rejectedApplications;
  }

  public void setApprovedApplications(ArrayList aApprovedApplications)
  {
    this.approvedApplications = aApprovedApplications;
  }

  public ArrayList getApprovedApplications()
  {
    return this.approvedApplications;
  }

  public void setTcClearApplications(ArrayList aTcClearApplications)
  {
    this.tcClearApplications = aTcClearApplications;
  }

  public ArrayList getTcClearApplications()
  {
    return this.tcClearApplications;
  }

  public void setWcClearApplications(ArrayList aWcClearApplications)
  {
    this.wcClearApplications = aWcClearApplications;
  }

  public ArrayList getWcClearApplications()
  {
    return this.wcClearApplications;
  }

  public void setSpecialMessagesList(ArrayList aSpecialMessagesList)
  {
    this.specialMessagesList = aSpecialMessagesList;
  }

  public ArrayList getSpecialMessagesList()
  {
    return this.specialMessagesList;
  }

  public ArrayList getEligibleAppList()
  {
    return this.eligibleAppList;
  }

  public void setEligibleAppList(ArrayList aEligibleAppList)
  {
    this.eligibleAppList = aEligibleAppList;
  }

  public ArrayList getEligibleDupApps()
  {
    return this.eligibleDupApps;
  }

  public ArrayList getEligibleNonDupApps()
  {
    return this.eligibleNonDupApps;
  }

  public ArrayList getEligibleNonDupRsfApps()
  {
    return this.eligibleNonDupRsfApps;
  }

  public ArrayList getIneligibleApps()
  {
    return this.ineligibleApps;
  }

  public ArrayList getIneligibleNonDupApps()
  {
    return this.ineligibleNonDupApps;
  }

  public void setEligibleDupApps(ArrayList list)
  {
    this.eligibleDupApps = list;
  }

  public void setEligibleNonDupApps(ArrayList list)
  {
    this.eligibleNonDupApps = list;
  }

  public void setEligibleNonDupRsfApps(ArrayList list)
  {
    this.eligibleNonDupRsfApps = list;
  }

  public void setIneligibleApps(ArrayList list)
  {
    this.ineligibleApps = list;
  }

  public void setIneligibleNonDupApps(ArrayList list)
  {
    this.ineligibleNonDupApps = list;
  }

  public ArrayList getDuplicateApps()
  {
    return this.duplicateApps;
  }

  public void setDuplicateApps(ArrayList list)
  {
    this.duplicateApps = list;
  }

  public ArrayList getIneligibleDupApps()
  {
    return this.ineligibleDupApps;
  }

  public void setIneligibleDupApps(ArrayList list)
  {
    this.ineligibleDupApps = list;
  }

  public int getIntApplicationCount()
  {
    return this.intApplicationCount;
  }

  public void setIntApplicationCount(int aIntApplicationCount)
  {
    this.intApplicationCount = aIntApplicationCount;
  }

  public Map getClearAppRefNo()
  {
    return this.clearAppRefNo;
  }

  public Map getClearApprovedAmt()
  {
    return this.clearApprovedAmt;
  }

  public Map getClearRsfApprovedAmt()
  {
    return this.clearRsfApprovedAmt;
  }

  public Map getClearRemarks()
  {
    return this.clearRemarks;
  }

  public Map getClearStatus()
  {
    return this.clearStatus;
  }

  public Map getDuplicateAppRefNo()
  {
    return this.duplicateAppRefNo;
  }

  public Map getDuplicateApprovedAmt()
  {
    return this.duplicateApprovedAmt;
  }

  public Map getDuplicateRemarks()
  {
    return this.duplicateRemarks;
  }

  public Map getDuplicateStatus()
  {
    return this.duplicateStatus;
  }

  public Map getIneligibleAppRefNo()
  {
    return this.ineligibleAppRefNo;
  }

  public Map getIneligibleApprovedAmt()
  {
    return this.ineligibleApprovedAmt;
  }

  public Map getIneligibleDupAppRefNo()
  {
    return this.ineligibleDupAppRefNo;
  }

  public Map getIneligibleDupApprovedAmt()
  {
    return this.ineligibleDupApprovedAmt;
  }

  public Map getIneligibleDupRemarks()
  {
    return this.ineligibleDupRemarks;
  }

  public Map getIneligibleDupStatus()
  {
    return this.ineligibleDupStatus;
  }

  public Map getIneligibleRemarks()
  {
    return this.ineligibleRemarks;
  }

  public Map getIneligibleStatus()
  {
    return this.ineligibleStatus;
  }

  public void setClearAppRefNo(Map map)
  {
    this.clearAppRefNo = map;
  }

  public void setClearApprovedAmt(Map map)
  {
    this.clearApprovedAmt = map;
  }

  public void setClearRsfApprovedAmt(Map map)
  {
    this.clearRsfApprovedAmt = map;
  }

  public void setClearRemarks(Map map)
  {
    this.clearRemarks = map;
  }

  public void setClearStatus(Map map)
  {
    this.clearStatus = map;
  }

  public void setDuplicateAppRefNo(Map map)
  {
    this.duplicateAppRefNo = map;
  }

  public void setDuplicateApprovedAmt(Map map)
  {
    this.duplicateApprovedAmt = map;
  }

  public void setDuplicateRemarks(Map map)
  {
    this.duplicateRemarks = map;
  }

  public void setDuplicateStatus(Map map)
  {
    this.duplicateStatus = map;
  }

  public void setIneligibleAppRefNo(Map map)
  {
    this.ineligibleAppRefNo = map;
  }

  public void setIneligibleApprovedAmt(Map map)
  {
    this.ineligibleApprovedAmt = map;
  }

  public void setIneligibleDupAppRefNo(Map map)
  {
    this.ineligibleDupAppRefNo = map;
  }

  public void setIneligibleDupApprovedAmt(Map map)
  {
    this.ineligibleDupApprovedAmt = map;
  }

  public void setIneligibleDupRemarks(Map map)
  {
    this.ineligibleDupRemarks = map;
  }

  public void setIneligibleDupStatus(Map map)
  {
    this.ineligibleDupStatus = map;
  }

  public void setIneligibleRemarks(Map map)
  {
    this.ineligibleRemarks = map;
  }

  public void setIneligibleStatus(Map map)
  {
    this.ineligibleStatus = map;
  }

  public ArrayList getClearApprovedApplications()
  {
    return this.clearApprovedApplications;
  }

  public ArrayList getClearHoldApplications()
  {
    return this.clearHoldApplications;
  }

  public ArrayList getClearRejectedApplications()
  {
    return this.clearRejectedApplications;
  }

  public ArrayList getDupApprovedApplications()
  {
    return this.dupApprovedApplications;
  }

  public ArrayList getDupHoldApplications()
  {
    return this.dupHoldApplications;
  }

  public ArrayList getDupRejectedApplications()
  {
    return this.dupRejectedApplications;
  }

  public ArrayList getIneligibleApprovedApplications()
  {
    return this.ineligibleApprovedApplications;
  }

  public ArrayList getIneligibleDupApprovedApplications()
  {
    return this.ineligibleDupApprovedApplications;
  }

  public ArrayList getIneligibleDupHoldApplications()
  {
    return this.ineligibleDupHoldApplications;
  }

  public ArrayList getIneligibleDupRejectedApplications()
  {
    return this.ineligibleDupRejectedApplications;
  }

  public ArrayList getIneligibleHoldApplications()
  {
    return this.ineligibleHoldApplications;
  }

  public ArrayList getIneligibleRejectedApplications()
  {
    return this.ineligibleRejectedApplications;
  }

  public void setClearApprovedApplications(ArrayList aClearApprovedApplications)
  {
    this.clearApprovedApplications = aClearApprovedApplications;
  }

  public void setClearHoldApplications(ArrayList aClearHoldApplications)
  {
    this.clearHoldApplications = aClearHoldApplications;
  }

  public void setClearRejectedApplications(ArrayList aClearRejectedApplications)
  {
    this.clearRejectedApplications = aClearRejectedApplications;
  }

  public void setDupApprovedApplications(ArrayList aDupApprovedApplications)
  {
    this.dupApprovedApplications = aDupApprovedApplications;
  }

  public void setDupHoldApplications(ArrayList aDupHoldApplications)
  {
    this.dupHoldApplications = aDupHoldApplications;
  }

  public void setDupRejectedApplications(ArrayList aDupRejectedApplications)
  {
    this.dupRejectedApplications = aDupRejectedApplications;
  }

  public void setIneligibleApprovedApplications(ArrayList aIneligibleApprovedApplications)
  {
    this.ineligibleApprovedApplications = aIneligibleApprovedApplications;
  }

  public void setIneligibleDupApprovedApplications(ArrayList aIneligibleDupApprovedApplications)
  {
    this.ineligibleDupApprovedApplications = aIneligibleDupApprovedApplications;
  }

  public void setIneligibleDupHoldApplications(ArrayList aIneligibleDupHoldApplications)
  {
    this.ineligibleDupHoldApplications = aIneligibleDupHoldApplications;
  }

  public void setIneligibleDupRejectedApplications(ArrayList aIneligibleDupRejectedApplications)
  {
    this.ineligibleDupRejectedApplications = aIneligibleDupRejectedApplications;
  }

  public void setIneligibleHoldApplications(ArrayList aIneligibleHoldApplications)
  {
    this.ineligibleHoldApplications = aIneligibleHoldApplications;
  }

  public void setIneligibleRejectedApplications(ArrayList list)
  {
    this.ineligibleRejectedApplications = list;
  }

  public Map getReApprovalRemarks()
  {
    return this.reApprovalRemarks;
  }

  public void setReApprovalRemarks(Map aReApprovalRemarks)
  {
    this.reApprovalRemarks = aReApprovalRemarks;
  }

  public Map getCgpanNo()
  {
    return this.cgpanNo;
  }

  public void setCgpanNo(Map aCgpanNo)
  {
    this.cgpanNo = aCgpanNo;
  }

  public Map getReapprovalStatus()
  {
    return this.reapprovalStatus;
  }

  public void setReapprovalStatus(Map aReapprovalStatus)
  {
    this.reapprovalStatus = aReapprovalStatus;
  }

  public Map getClearCreditAmt()
  {
    return this.clearCreditAmt;
  }

  public Map getDuplicateCreditAmt()
  {
    return this.duplicateCreditAmt;
  }

  public Map getIneligibleCreditAmt()
  {
    return this.ineligibleCreditAmt;
  }

  public Map getIneligibleDupCreditAmt()
  {
    return this.ineligibleDupCreditAmt;
  }

  public void setClearCreditAmt(Map aClearCreditAmt)
  {
    this.clearCreditAmt = aClearCreditAmt;
  }

  public void setDuplicateCreditAmt(Map aDuplicateCreditAmt)
  {
    this.duplicateCreditAmt = aDuplicateCreditAmt;
  }

  public void setIneligibleCreditAmt(Map aIneligibleCreditAmt)
  {
    this.ineligibleCreditAmt = aIneligibleCreditAmt;
  }

  public void setIneligibleDupCreditAmt(Map aIneligibleDupCreditAmt)
  {
    this.ineligibleDupCreditAmt = aIneligibleDupCreditAmt;
  }

  public ArrayList getPendingApplications()
  {
    return this.pendingApplications;
  }

  public void setPendingApplications(ArrayList list)
  {
    this.pendingApplications = list;
  }

  public ArrayList getClearPendingApplications()
  {
    return this.clearPendingApplications;
  }

  public ArrayList getDupPendingApplications()
  {
    return this.dupPendingApplications;
  }

  public ArrayList getIneligibleDupPendingApplications()
  {
    return this.ineligibleDupPendingApplications;
  }

  public ArrayList getIneligiblePendingApplications()
  {
    return this.ineligiblePendingApplications;
  }

  public void setClearPendingApplications(ArrayList list)
  {
    this.clearPendingApplications = list;
  }

  public void setDupPendingApplications(ArrayList list)
  {
    this.dupPendingApplications = list;
  }

  public void setIneligibleDupPendingApplications(ArrayList list)
  {
    this.ineligibleDupPendingApplications = list;
  }

  public void setIneligiblePendingApplications(ArrayList list)
  {
    this.ineligiblePendingApplications = list;
  }

  public void resetMaps()
  {
    this.clearStatus.clear();
    this.duplicateStatus.clear();
    this.ineligibleStatus.clear();
    this.ineligibleDupStatus.clear();

    this.clearRemarks.clear();
    this.duplicateRemarks.clear();
    this.ineligibleRemarks.clear();
    this.ineligibleDupRemarks.clear();

    this.clearAppRefNo.clear();
    this.duplicateAppRefNo.clear();
    this.ineligibleAppRefNo.clear();
    this.ineligibleDupAppRefNo.clear();

    this.clearApprovedAmt.clear();
    this.duplicateApprovedAmt.clear();
    this.ineligibleApprovedAmt.clear();
    this.ineligibleDupApprovedAmt.clear();
  }

  public void resetReApproveMaps()
  {
    this.reApprovedAmt.clear();
    this.reApprovalRemarks.clear();
    this.reapprovalStatus.clear();
  }

  public void resetTCConvMaps()
  {
    this.tcCgpan.clear();
    this.tcDecision.clear();
  }

  public void resetWCConvMaps()
  {
    this.wcCgpan.clear();
    this.wcDecision.clear();
  }

  public Map getTempMap()
  {
    return this.tempMap;
  }

  public void setTempMap(Map map)
  {
    this.tempMap = map;
  }

  public Map getClearTempMap()
  {
    return this.clearTempMap;
  }

  public Map getClearRsfTempMap()
  {
    return this.clearRsfTempMap;
  }

  public Map getDupTempMap()
  {
    return this.dupTempMap;
  }

  public Map getIneligibleTempMap()
  {
    return this.ineligibleTempMap;
  }

  public void setClearTempMap(Map map)
  {
    this.clearTempMap = map;
  }

  public void setClearRsfTempMap(Map map)
  {
    this.clearRsfTempMap = map;
  }

  public void setDupTempMap(Map map)
  {
    this.dupTempMap = map;
  }

  public void setIneligibleTempMap(Map map)
  {
    this.ineligibleTempMap = map;
  }

  public Map getCreditAmt()
  {
    return this.creditAmt;
  }

  public Map getEligibleCreditAmt()
  {
    return this.eligibleCreditAmt;
  }

  public void setCreditAmt(Map map)
  {
    this.creditAmt = map;
  }

  public void setEligibleCreditAmt(Map map)
  {
    this.eligibleCreditAmt = map;
  }

  public Map getEligibleReapproveMap()
  {
    return this.eligibleReapproveMap;
  }

  public Map getIneligibleReapproveMap()
  {
    return this.ineligibleReapproveMap;
  }

  public void setEligibleReapproveMap(Map map)
  {
    this.eligibleReapproveMap = map;
  }

  public void setIneligibleReapproveMap(Map map)
  {
    this.ineligibleReapproveMap = map;
  }

  public Map getClearRemMap()
  {
    return this.clearRemMap;
  }

  public Map getClearRsfRemMap()
  {
    return this.clearRsfRemMap;
  }

  public Map getDupRemMap()
  {
    return this.dupRemMap;
  }

  public Map getIneligibleRemMap()
  {
    return this.ineligibleRemMap;
  }

  public Map getTempRemMap()
  {
    return this.tempRemMap;
  }

  public void setClearRemMap(Map map)
  {
    this.clearRemMap = map;
  }

  public void setClearRsfRemMap(Map map)
  {
    this.clearRsfRemMap = map;
  }

  public void setDupRemMap(Map map)
  {
    this.dupRemMap = map;
  }

  public void setIneligibleRemMap(Map map)
  {
    this.ineligibleRemMap = map;
  }

  public void setTempRemMap(Map map)
  {
    this.tempRemMap = map;
  }

  public ArrayList getTcApplications()
  {
    return this.tcApplications;
  }

  public ArrayList getWcApplications()
  {
    return this.wcApplications;
  }

  public void setTcApplications(ArrayList list)
  {
    this.tcApplications = list;
  }

  public void setWcApplications(ArrayList list)
  {
    this.wcApplications = list;
  }

  public Map getTcAppRefNo()
  {
    return this.tcAppRefNo;
  }

  public Map getTcCgpan()
  {
    return this.tcCgpan;
  }

  public Map getTcDecision()
  {
    return this.tcDecision;
  }

  public void setTcAppRefNo(Map map)
  {
    this.tcAppRefNo = map;
  }

  public void setTcCgpan(Map map)
  {
    this.tcCgpan = map;
  }

  public void setTcDecision(Map map)
  {
    this.tcDecision = map;
  }

  public Map getWcAppRefNo()
  {
    return this.wcAppRefNo;
  }

  public Map getWcCgpan()
  {
    return this.wcCgpan;
  }

  public Map getWcDecision()
  {
    return this.wcDecision;
  }

  public void setWcAppRefNo(Map map)
  {
    this.wcAppRefNo = map;
  }

  public void setWcCgpan(Map map)
  {
    this.wcCgpan = map;
  }

  public void setWcDecision(Map map)
  {
    this.wcDecision = map;
  }

  public int getTcEntryIndex()
  {
    return this.tcEntryIndex;
  }

  public int getWcEntryIndex()
  {
    return this.wcEntryIndex;
  }

  public void setTcEntryIndex(int i)
  {
    this.tcEntryIndex = i;
  }

  public void setWcEntryIndex(int i)
  {
    this.wcEntryIndex = i;
  }

  public ArrayList getTcConvertedApplications()
  {
    return this.tcConvertedApplications;
  }

  public void setTcConvertedApplications(ArrayList list)
  {
    this.tcConvertedApplications = list;
  }

  public ArrayList getEnhanceConvertedApplications()
  {
    return this.enhanceConvertedApplications;
  }

  public ArrayList getRenewConvertedApplications()
  {
    return this.renewConvertedApplications;
  }
  public ArrayList getBranchCodeList()
  {
    return this.branchCodeList;
  }
  public void setBranchCodeList(ArrayList list)
  {
    this.branchCodeList = list;
  }
  public void setEnhanceConvertedApplications(ArrayList list)
  {
    this.enhanceConvertedApplications = list;
  }

  public void setRenewConvertedApplications(ArrayList list)
  {
    this.renewConvertedApplications = list;
  }
}