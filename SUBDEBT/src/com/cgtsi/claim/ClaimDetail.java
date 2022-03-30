// FrontEnd Plus GUI for JAD
// DeCompiled : ClaimDetail.class

package com.cgtsi.claim;

import java.io.Serializable;
import java.util.*;

public class ClaimDetail
    implements Serializable
{

    private String mliId;
    private String mliName;
    private String borrowerId;
    private String ssiUnitName;
    private String claimRefNum;
    private double approvedAppAmt;
    private Date npaDate;
    private String npaDateStr;
    private String reasonForTurningNPA;
    private Date dtOfNPAReportedToCGTSI;
    private String dtOfNPAReportedToCGTSIStr;
    private double outstandingAmntAsOnNPADate;
    private Date dateOfIssueOfRecallNotice;
    private String dateOfIssueOfRecallNoticeStr;
    private double appliedClaimAmt;
    private String decision;
    private String comments;
    private String standardRemarks;
    private double eligibleClaimAmt;
    private String cgclan;
    private double approvedClaimAmount;
    private boolean systemDecisionIfEligibleClaim;
    private Vector cgpanDetails;
    private Vector danSummary;
    private boolean hasLockInPeriodExpired;
    private Date clmApprvdDt;
    private Date clmSubmittedDt;
    private double totalTCOSAmountAsOnNPA;
    private double totalWCOSAmountAsOnNPA;
    private String whichInstallemnt;
    private String totalAmtPayNow;
    private String clmStatus;
    private Vector tcDetails;
    private Vector wcDetails;
    private String createdModifiedBy;
    private String forwaredToUser;
    private String legalForum;
    private String legalForumName;
    private String legalSuitNumber;
    private String legalLocation;
    private Date legalFilingDate;
    private Date dateofReceipt;
    private double asfDeductableforTC;
    private double asfDeductableforWC;
    private double tcClaimEligibleAmt;
    private double wcClaimEligibleAmt;
    private double tcFirstInstallment;
    private double wcFirstInstallment;
    private double tcApprovedAmt;
    private double wcApprovedAmt;
    private double tcOutstanding;
    private double wcOutstanding;
    private double tcrecovery;
    private double wcrecovery;
    private String womenOperated;
    private double serviceFee;
    private String nerFlag;
    private String typeofActivity;
    private String schemeName;
    private String stateName;
    private String Handicraft;
    private String DcHandicraft;
    private String zoneName;
    private String branchName;
    private Date claimLodgmentDate;
    private double claimDecRecvdAmt;
    private double claimForwdAmt;
    private String claimDecRecvdflag;
    private Date claimDecRecvdDt;
    private Date claimForwardedDt;
    private Date claimRejectedDt;
    private Date claimApprovedDt;
    private String cgpan;
    private double claimApprovedAmount;
    private double claimAppliedAmt;
    private String clmDeclRecvdFlag;
    private Date clmDeclRecvdDt;
    private double totalGuarnteAmt;
    private double totgrandTotal;
    public String returnRemarks;
    private double totGrandClmApprovedAmt;
    private String returnRemark;
    private String iseligact;
    private String iseligactcomm;
    private String whetcibildone;
    private String whetcibildonecomm;
    private String isrataspercgs;
    private String isrataspercgscomm;
    private String isthirdcollattaken;
    private String isthirdcollattakencomm;
    private String isnpadtasperguid;
    private String isnpadtasperguidcomm;
    private String isclmoswrtnpadt;
    private String isclmoswrtnpadtcomm;
    private String whetseriousdeficinvol;
    private String whetseriousdeficinvolcomm;
    private String whetmajordeficinvolvd;
    private String whetmajordeficinvolvdcomm;
    private String whetdeficinvolbystaff;
    private String whetdeficinvolbystaffcomm;
    private String isinternratinvestgrad;
    private String isinternratinvestgradcomm;
    private String isallrecinclmform;
    private String isallrecinclmformcomm;
    private Date dateofClaim;
    private String viewDu;
    private String claimRemarks;
    private Date clamQryDate;
    private Date tempClosedDate;
    private String clmQryRefNumber;
    private Date claimReplyRecvdDate;
    private Date replyInwardDt;
    private String replyInwardId;
    private Date claimReturnDate;
    private Date claimWithDrawnDate;
    private String claimRetRemarks;
    private Date tempRejectedDate;
    private Date tempRejOrRejDt;
    private Date lastActionTakenDt;
    private String memberZoneName;
    private Map danSummaryReportDetails;
    private double asfRefundableForTC;
    private double asfRefundableForWC;
    private String refundFlag;
    private String recommendation;
    private String recommendationData;
    private String rejectionReason;

    public String getIseligact()
    {
        return iseligact;
    }

    public void setIseligact(String iseligact)
    {
        this.iseligact = iseligact;
    }

    public String getIseligactcomm()
    {
        return iseligactcomm;
    }

    public void setIseligactcomm(String iseligactcomm)
    {
        this.iseligactcomm = iseligactcomm;
    }

    public String getWhetcibildone()
    {
        return whetcibildone;
    }

    public void setWhetcibildone(String whetcibildone)
    {
        this.whetcibildone = whetcibildone;
    }

    public String getWhetcibildonecomm()
    {
        return whetcibildonecomm;
    }

    public void setWhetcibildonecomm(String whetcibildonecomm)
    {
        this.whetcibildonecomm = whetcibildonecomm;
    }

    public String getIsrataspercgs()
    {
        return isrataspercgs;
    }

    public void setIsrataspercgs(String isrataspercgs)
    {
        this.isrataspercgs = isrataspercgs;
    }

    public String getIsrataspercgscomm()
    {
        return isrataspercgscomm;
    }

    public void setIsrataspercgscomm(String isrataspercgscomm)
    {
        this.isrataspercgscomm = isrataspercgscomm;
    }

    public String getIsthirdcollattaken()
    {
        return isthirdcollattaken;
    }

    public void setIsthirdcollattaken(String isthirdcollattaken)
    {
        this.isthirdcollattaken = isthirdcollattaken;
    }

    public String getIsthirdcollattakencomm()
    {
        return isthirdcollattakencomm;
    }

    public void setIsthirdcollattakencomm(String isthirdcollattakencomm)
    {
        this.isthirdcollattakencomm = isthirdcollattakencomm;
    }

    public String getIsnpadtasperguid()
    {
        return isnpadtasperguid;
    }

    public void setIsnpadtasperguid(String isnpadtasperguid)
    {
        this.isnpadtasperguid = isnpadtasperguid;
    }

    public String getIsnpadtasperguidcomm()
    {
        return isnpadtasperguidcomm;
    }

    public void setIsnpadtasperguidcomm(String isnpadtasperguidcomm)
    {
        this.isnpadtasperguidcomm = isnpadtasperguidcomm;
    }

    public String getIsclmoswrtnpadt()
    {
        return isclmoswrtnpadt;
    }

    public void setIsclmoswrtnpadt(String isclmoswrtnpadt)
    {
        this.isclmoswrtnpadt = isclmoswrtnpadt;
    }

    public String getIsclmoswrtnpadtcomm()
    {
        return isclmoswrtnpadtcomm;
    }

    public void setIsclmoswrtnpadtcomm(String isclmoswrtnpadtcomm)
    {
        this.isclmoswrtnpadtcomm = isclmoswrtnpadtcomm;
    }

    public String getWhetseriousdeficinvol()
    {
        return whetseriousdeficinvol;
    }

    public void setWhetseriousdeficinvol(String whetseriousdeficinvol)
    {
        this.whetseriousdeficinvol = whetseriousdeficinvol;
    }

    public String getWhetseriousdeficinvolcomm()
    {
        return whetseriousdeficinvolcomm;
    }

    public void setWhetseriousdeficinvolcomm(String whetseriousdeficinvolcomm)
    {
        this.whetseriousdeficinvolcomm = whetseriousdeficinvolcomm;
    }

    public String getWhetmajordeficinvolvd()
    {
        return whetmajordeficinvolvd;
    }

    public void setWhetmajordeficinvolvd(String whetmajordeficinvolvd)
    {
        this.whetmajordeficinvolvd = whetmajordeficinvolvd;
    }

    public String getWhetmajordeficinvolvdcomm()
    {
        return whetmajordeficinvolvdcomm;
    }

    public void setWhetmajordeficinvolvdcomm(String whetmajordeficinvolvdcomm)
    {
        this.whetmajordeficinvolvdcomm = whetmajordeficinvolvdcomm;
    }

    public String getWhetdeficinvolbystaff()
    {
        return whetdeficinvolbystaff;
    }

    public void setWhetdeficinvolbystaff(String whetdeficinvolbystaff)
    {
        this.whetdeficinvolbystaff = whetdeficinvolbystaff;
    }

    public String getWhetdeficinvolbystaffcomm()
    {
        return whetdeficinvolbystaffcomm;
    }

    public void setWhetdeficinvolbystaffcomm(String whetdeficinvolbystaffcomm)
    {
        this.whetdeficinvolbystaffcomm = whetdeficinvolbystaffcomm;
    }

    public String getIsinternratinvestgrad()
    {
        return isinternratinvestgrad;
    }

    public void setIsinternratinvestgrad(String isinternratinvestgrad)
    {
        this.isinternratinvestgrad = isinternratinvestgrad;
    }

    public String getIsinternratinvestgradcomm()
    {
        return isinternratinvestgradcomm;
    }

    public void setIsinternratinvestgradcomm(String isinternratinvestgradcomm)
    {
        this.isinternratinvestgradcomm = isinternratinvestgradcomm;
    }

    public String getIsallrecinclmform()
    {
        return isallrecinclmform;
    }

    public void setIsallrecinclmform(String isallrecinclmform)
    {
        this.isallrecinclmform = isallrecinclmform;
    }

    public String getIsallrecinclmformcomm()
    {
        return isallrecinclmformcomm;
    }

    public void setIsallrecinclmformcomm(String isallrecinclmformcomm)
    {
        this.isallrecinclmformcomm = isallrecinclmformcomm;
    }

    public String getViewDu()
    {
        return viewDu;
    }

    public void setViewDu(String viewDu)
    {
        this.viewDu = viewDu;
    }

    public Date getDateofClaim()
    {
        return dateofClaim;
    }

    public void setDateofClaim(Date dateofClaim)
    {
        this.dateofClaim = dateofClaim;
    }

    public String getReturnRemark()
    {
        return returnRemark;
    }

    public void setReturnRemark(String returnRemark)
    {
        this.returnRemark = returnRemark;
    }

    public String getMemberZoneName()
    {
        return memberZoneName;
    }

    public void setMemberZoneName(String memberZoneName)
    {
        this.memberZoneName = memberZoneName;
    }

    public ClaimDetail()
    {
        eligibleClaimAmt = 0.0D;
        nerFlag = "N";
        danSummaryReportDetails = new HashMap();
    }

    public String getWomenOperated()
    {
        return womenOperated;
    }

    public void setWomenOperated(String womenOperated)
    {
        this.womenOperated = womenOperated;
    }

    public double getTcApprovedAmt()
    {
        return tcApprovedAmt;
    }

    public void setTcApprovedAmt(double tcApprovedAmt)
    {
        this.tcApprovedAmt = tcApprovedAmt;
    }

    public double getWcApprovedAmt()
    {
        return wcApprovedAmt;
    }

    public void setWcApprovedAmt(double wcApprovedAmt)
    {
        this.wcApprovedAmt = wcApprovedAmt;
    }

    public double getTcOutstanding()
    {
        return tcOutstanding;
    }

    public void setTcOutstanding(double tcOutstanding)
    {
        this.tcOutstanding = tcOutstanding;
    }

    public double getWcOutstanding()
    {
        return wcOutstanding;
    }

    public void setWcOutstanding(double wcOutstanding)
    {
        this.wcOutstanding = wcOutstanding;
    }

    public double getTcrecovery()
    {
        return tcrecovery;
    }

    public void setTcrecovery(double tcrecovery)
    {
        this.tcrecovery = tcrecovery;
    }

    public double getWcrecovery()
    {
        return wcrecovery;
    }

    public void setWcrecovery(double wcrecovery)
    {
        this.wcrecovery = wcrecovery;
    }

    public double getTcClaimEligibleAmt()
    {
        return tcClaimEligibleAmt;
    }

    public void setTcClaimEligibleAmt(double tcClaimEligibleAmt)
    {
        this.tcClaimEligibleAmt = tcClaimEligibleAmt;
    }

    public double getWcClaimEligibleAmt()
    {
        return wcClaimEligibleAmt;
    }

    public void setWcClaimEligibleAmt(double wcClaimEligibleAmt)
    {
        this.wcClaimEligibleAmt = wcClaimEligibleAmt;
    }

    public double getTcFirstInstallment()
    {
        return tcFirstInstallment;
    }

    public void setTcFirstInstallment(double tcFirstInstallment)
    {
        this.tcFirstInstallment = tcFirstInstallment;
    }

    public double getWcFirstInstallment()
    {
        return wcFirstInstallment;
    }

    public void setWcFirstInstallment(double wcFirstInstallment)
    {
        this.wcFirstInstallment = wcFirstInstallment;
    }

    public double getAsfDeductableforTC()
    {
        return asfDeductableforTC;
    }

    public void setAsfDeductableforTC(double asfDeductableforTC)
    {
        this.asfDeductableforTC = asfDeductableforTC;
    }

    public double getAsfDeductableforWC()
    {
        return asfDeductableforWC;
    }

    public void setAsfDeductableforWC(double asfDeductableforWC)
    {
        this.asfDeductableforWC = asfDeductableforWC;
    }

    public double getServiceFee()
    {
        return serviceFee;
    }

    public void setServiceFee(double serviceFee)
    {
        this.serviceFee = serviceFee;
    }

    public Date getDateofReceipt()
    {
        return dateofReceipt;
    }

    public void setDateofReceipt(Date dateofReceipt)
    {
        this.dateofReceipt = dateofReceipt;
    }

    public Date getLegalFilingDate()
    {
        return legalFilingDate;
    }

    public void setLegalFilingDate(Date legalFilingDate)
    {
        this.legalFilingDate = legalFilingDate;
    }

    public String getLegalLocation()
    {
        return legalLocation;
    }

    public void setLegalLocation(String legalLocation)
    {
        this.legalLocation = legalLocation;
    }

    public String getLegalSuitNumber()
    {
        return legalSuitNumber;
    }

    public void setLegalSuitNumber(String legalSuitNumber)
    {
        this.legalSuitNumber = legalSuitNumber;
    }

    public String getLegalForumName()
    {
        return legalForumName;
    }

    public void setLegalForumName(String legalForumName)
    {
        this.legalForumName = legalForumName;
    }

    public String getLegalForum()
    {
        return legalForum;
    }

    public void setLegalForum(String legalForum)
    {
        this.legalForum = legalForum;
    }

    public Map getDanSummaryReportDetails()
    {
        return danSummaryReportDetails;
    }

    public void setDanSummaryReportDetails(Map danSummaryReportDetails)
    {
        this.danSummaryReportDetails = danSummaryReportDetails;
    }

    public Object getDanSummaryReportDetails(String key)
    {
        return danSummaryReportDetails.get(key);
    }

    public void setDanSummaryReportDetails(String key, Object value)
    {
        danSummaryReportDetails.put(key, value);
    }

    public String getMliId()
    {
        return mliId;
    }

    public void setMliId(String aMliId)
    {
        mliId = aMliId;
    }

    public String getBorrowerId()
    {
        return borrowerId;
    }

    public void setBorrowerId(String bid)
    {
        borrowerId = bid;
    }

    public String getClaimRefNum()
    {
        return claimRefNum;
    }

    public void setClaimRefNum(String aClaimRefNum)
    {
        claimRefNum = aClaimRefNum;
    }

    public Date getNpaDate()
    {
        return npaDate;
    }

    public void setNpaDate(Date aNpaDate)
    {
        npaDate = aNpaDate;
    }

    public double getAppliedClaimAmt()
    {
        return appliedClaimAmt;
    }

    public void setAppliedClaimAmt(double aAppliedClaimAmt)
    {
        appliedClaimAmt = aAppliedClaimAmt;
    }

    public String getDecision()
    {
        return decision;
    }

    public void setDecision(String aDecision)
    {
        decision = aDecision;
    }

    public String getComments()
    {
        return comments;
    }

    public void setComments(String acomments)
    {
        comments = acomments;
    }

    public double getEligibleClaimAmt()
    {
        return eligibleClaimAmt;
    }

    public void setEligibleClaimAmt(double eligibleClaimAmt)
    {
        this.eligibleClaimAmt = eligibleClaimAmt;
    }

    public double getOutstandingAmntAsOnNPADate()
    {
        return outstandingAmntAsOnNPADate;
    }

    public void setOutstandingAmntAsOnNPADate(double amount)
    {
        outstandingAmntAsOnNPADate = amount;
    }

    public double getApplicationApprovedAmount()
    {
        return approvedAppAmt;
    }

    public void setApplicationApprovedAmount(double aapprovedAppAmt)
    {
        approvedAppAmt = aapprovedAppAmt;
    }

    public String getCGCLAN()
    {
        return cgclan;
    }

    public void setCGCLAN(String acgclan)
    {
        cgclan = acgclan;
    }

    public double getApprovedClaimAmount()
    {
        return approvedClaimAmount;
    }

    public void setApprovedClaimAmount(double amount)
    {
        approvedClaimAmount = amount;
    }

    public boolean getSystemDecision()
    {
        return systemDecisionIfEligibleClaim;
    }

    public void setSystemDecision(boolean flag)
    {
        systemDecisionIfEligibleClaim = flag;
    }

    public String getMliName()
    {
        return mliName;
    }

    public void setMliName(String name)
    {
        mliName = name;
    }

    public String getSsiUnitName()
    {
        return ssiUnitName;
    }

    public void setSsiUnitName(String name)
    {
        ssiUnitName = name;
    }

    public Date getDtOfNPAReportedToCGTSI()
    {
        return dtOfNPAReportedToCGTSI;
    }

    public void setDtOfNPAReportedToCGTSI(Date date)
    {
        dtOfNPAReportedToCGTSI = date;
    }

    public String getReasonForTurningNPA()
    {
        return reasonForTurningNPA;
    }

    public void setReasonForTurningNPA(String reason)
    {
        reasonForTurningNPA = reason;
    }

    public Date getDateOfIssueOfRecallNotice()
    {
        return dateOfIssueOfRecallNotice;
    }

    public void setDateOfIssueOfRecallNotice(Date date)
    {
        dateOfIssueOfRecallNotice = date;
    }

    public Vector getDanSummary()
    {
        return danSummary;
    }

    public void setDanSummary(Vector dtls)
    {
        danSummary = dtls;
    }

    public Vector getCgpanDetails()
    {
        return cgpanDetails;
    }

    public void setCgpanDetails(Vector dtls)
    {
        cgpanDetails = dtls;
    }

    public boolean getHasLockInPeriodExpired()
    {
        return hasLockInPeriodExpired;
    }

    public void setHasLockInPeriodExpired(boolean flag)
    {
        hasLockInPeriodExpired = flag;
    }

    public Date getClmSubmittedDt()
    {
        return clmSubmittedDt;
    }

    public void setClmSubmittedDt(Date date)
    {
        clmSubmittedDt = date;
    }

    public Date getClmApprvdDt()
    {
        return clmApprvdDt;
    }

    public void setClmApprvdDt(Date date)
    {
        clmApprvdDt = date;
    }

    public double getTotalTCOSAmountAsOnNPA()
    {
        return totalTCOSAmountAsOnNPA;
    }

    public void setTotalTCOSAmountAsOnNPA(double amount)
    {
        totalTCOSAmountAsOnNPA = amount;
    }

    public double getTotalWCOSAmountAsOnNPA()
    {
        return totalWCOSAmountAsOnNPA;
    }

    public void setTotalWCOSAmountAsOnNPA(double amount)
    {
        totalWCOSAmountAsOnNPA = amount;
    }

    public String getWhichInstallemnt()
    {
        return whichInstallemnt;
    }

    public void setWhichInstallemnt(String flag)
    {
        whichInstallemnt = flag;
    }

    public String getDateOfIssueOfRecallNoticeStr()
    {
        return dateOfIssueOfRecallNoticeStr;
    }

    public void setDateOfIssueOfRecallNoticeStr(String aStr)
    {
        dateOfIssueOfRecallNoticeStr = aStr;
    }

    public String getNpaDateStr()
    {
        return npaDateStr;
    }

    public void setNpaDateStr(String aStr)
    {
        npaDateStr = aStr;
    }

    public String getDtOfNPAReportedToCGTSIStr()
    {
        return dtOfNPAReportedToCGTSIStr;
    }

    public void setDtOfNPAReportedToCGTSIStr(String aStr)
    {
        dtOfNPAReportedToCGTSIStr = aStr;
    }

    public String getTotalAmtPayNow()
    {
        return totalAmtPayNow;
    }

    public void setTotalAmtPayNow(String amnt)
    {
        totalAmtPayNow = amnt;
    }

    public String getClmStatus()
    {
        return clmStatus;
    }

    public void setClmStatus(String status)
    {
        clmStatus = status;
    }

    public Vector getTcDetails()
    {
        return tcDetails;
    }

    public void setTcDetails(Vector dtl)
    {
        tcDetails = dtl;
    }

    public Vector getWcDetails()
    {
        return wcDetails;
    }

    public void setWcDetails(Vector dtl)
    {
        wcDetails = dtl;
    }

    public String getCreatedModifiedBy()
    {
        return createdModifiedBy;
    }

    public void setCreatedModifiedBy(String id)
    {
        createdModifiedBy = id;
    }

    public String getForwaredToUser()
    {
        return forwaredToUser;
    }

    public void setForwaredToUser(String id)
    {
        forwaredToUser = id;
    }

    public void setTypeofActivity(String typeofActivity)
    {
        this.typeofActivity = typeofActivity;
    }

    public String getTypeofActivity()
    {
        return typeofActivity;
    }

    public void setSchemeName(String schemeName)
    {
        this.schemeName = schemeName;
    }

    public String getSchemeName()
    {
        return schemeName;
    }

    public void setStandardRemarks(String standardRemarks)
    {
        this.standardRemarks = standardRemarks;
    }

    public String getStandardRemarks()
    {
        return standardRemarks;
    }

    public void setStateName(String stateName)
    {
        this.stateName = stateName;
    }

    public String getStateName()
    {
        return stateName;
    }

    public void setNerFlag(String nerFlag)
    {
        this.nerFlag = nerFlag;
    }

    public String getNerFlag()
    {
        return nerFlag;
    }

    public void setHandicraft(String handicraft)
    {
        Handicraft = handicraft;
    }

    public String getHandicraft()
    {
        return Handicraft;
    }

    public void setDcHandicraft(String dcHandicraft)
    {
        DcHandicraft = dcHandicraft;
    }

    public String getDcHandicraft()
    {
        return DcHandicraft;
    }

    public void setAsfRefundableForTC(double asfRefundableForTC)
    {
        this.asfRefundableForTC = asfRefundableForTC;
    }

    public double getAsfRefundableForTC()
    {
        return asfRefundableForTC;
    }

    public void setAsfRefundableForWC(double asfRefundableForWC)
    {
        this.asfRefundableForWC = asfRefundableForWC;
    }

    public double getAsfRefundableForWC()
    {
        return asfRefundableForWC;
    }

    public void setRefundFlag(String refundFlag)
    {
        this.refundFlag = refundFlag;
    }

    public String getRefundFlag()
    {
        return refundFlag;
    }

    public void setRecommendation(String recommendation)
    {
        this.recommendation = recommendation;
    }

    public String getRecommendation()
    {
        return recommendation;
    }

    public void setRecommendationData(String recommendationData)
    {
        this.recommendationData = recommendationData;
    }

    public String getRecommendationData()
    {
        return recommendationData;
    }

    public void setRejectionReason(String rejectionReason)
    {
        this.rejectionReason = rejectionReason;
    }

    public String getRejectionReason()
    {
        return rejectionReason;
    }

    public void setZoneName(String zoneName)
    {
        this.zoneName = zoneName;
    }

    public String getZoneName()
    {
        return zoneName;
    }

    public void setBranchName(String branchName)
    {
        this.branchName = branchName;
    }

    public String getBranchName()
    {
        return branchName;
    }

    public void setClaimLodgmentDate(Date claimLodgmentDate)
    {
        this.claimLodgmentDate = claimLodgmentDate;
    }

    public Date getClaimLodgmentDate()
    {
        return claimLodgmentDate;
    }

    public void setClaimDecRecvdAmt(double claimDecRecvdAmt)
    {
        this.claimDecRecvdAmt = claimDecRecvdAmt;
    }

    public double getClaimDecRecvdAmt()
    {
        return claimDecRecvdAmt;
    }

    public void setClaimDecRecvdflag(String claimDecRecvdflag)
    {
        this.claimDecRecvdflag = claimDecRecvdflag;
    }

    public String getClaimDecRecvdflag()
    {
        return claimDecRecvdflag;
    }

    public void setClaimDecRecvdDt(Date claimDecRecvdDt)
    {
        this.claimDecRecvdDt = claimDecRecvdDt;
    }

    public Date getClaimDecRecvdDt()
    {
        return claimDecRecvdDt;
    }

    public void setClaimForwardedDt(Date claimForwardedDt)
    {
        this.claimForwardedDt = claimForwardedDt;
    }

    public Date getClaimForwardedDt()
    {
        return claimForwardedDt;
    }

    public void setClaimRemarks(String claimRemarks)
    {
        this.claimRemarks = claimRemarks;
    }

    public String getClaimRemarks()
    {
        return claimRemarks;
    }

    public void setClaimRejectedDt(Date claimRejectedDt)
    {
        this.claimRejectedDt = claimRejectedDt;
    }

    public Date getClaimRejectedDt()
    {
        return claimRejectedDt;
    }

    public void setClaimApprovedDt(Date claimApprovedDt)
    {
        this.claimApprovedDt = claimApprovedDt;
    }

    public Date getClaimApprovedDt()
    {
        return claimApprovedDt;
    }

    public void setCgpan(String cgpan)
    {
        this.cgpan = cgpan;
    }

    public String getCgpan()
    {
        return cgpan;
    }

    public void setClaimApprovedAmount(double claimApprovedAmount)
    {
        this.claimApprovedAmount = claimApprovedAmount;
    }

    public double getClaimApprovedAmount()
    {
        return claimApprovedAmount;
    }

    public void setClaimForwdAmt(double claimForwdAmt)
    {
        this.claimForwdAmt = claimForwdAmt;
    }

    public double getClaimForwdAmt()
    {
        return claimForwdAmt;
    }

    public void setClaimAppliedAmt(double claimAppliedAmt)
    {
        this.claimAppliedAmt = claimAppliedAmt;
    }

    public double getClaimAppliedAmt()
    {
        return claimAppliedAmt;
    }

    public void setClmDeclRecvdFlag(String clmDeclRecvdFlag)
    {
        this.clmDeclRecvdFlag = clmDeclRecvdFlag;
    }

    public String getClmDeclRecvdFlag()
    {
        return clmDeclRecvdFlag;
    }

    public void setClmDeclRecvdDt(Date clmDeclRecvdDt)
    {
        this.clmDeclRecvdDt = clmDeclRecvdDt;
    }

    public Date getClmDeclRecvdDt()
    {
        return clmDeclRecvdDt;
    }

    public void setClamQryDate(Date clamQryDate)
    {
        this.clamQryDate = clamQryDate;
    }

    public Date getClamQryDate()
    {
        return clamQryDate;
    }

    public void setClmQryRefNumber(String clmQryRefNumber)
    {
        this.clmQryRefNumber = clmQryRefNumber;
    }

    public String getClmQryRefNumber()
    {
        return clmQryRefNumber;
    }

    public void setReplyInwardDt(Date replyInwardDt)
    {
        this.replyInwardDt = replyInwardDt;
    }

    public Date getReplyInwardDt()
    {
        return replyInwardDt;
    }

    public void setReplyInwardId(String replyInwardId)
    {
        this.replyInwardId = replyInwardId;
    }

    public String getReplyInwardId()
    {
        return replyInwardId;
    }

    public void setClaimReturnDate(Date claimReturnDate)
    {
        this.claimReturnDate = claimReturnDate;
    }

    public Date getClaimReturnDate()
    {
        return claimReturnDate;
    }

    public void setClaimRetRemarks(String claimRetRemarks)
    {
        this.claimRetRemarks = claimRetRemarks;
    }

    public String getClaimRetRemarks()
    {
        return claimRetRemarks;
    }

    public void setTotalGuarnteAmt(double totalGuarnteAmt)
    {
        this.totalGuarnteAmt = totalGuarnteAmt;
    }

    public double getTotalGuarnteAmt()
    {
        return totalGuarnteAmt;
    }

    public void setTotgrandTotal(double totgrandTotal)
    {
        this.totgrandTotal = totgrandTotal;
    }

    public double getTotgrandTotal()
    {
        return totgrandTotal;
    }

    public void setTotGrandClmApprovedAmt(double totGrandClmApprovedAmt)
    {
        this.totGrandClmApprovedAmt = totGrandClmApprovedAmt;
    }

    public double getTotGrandClmApprovedAmt()
    {
        return totGrandClmApprovedAmt;
    }

    public void setClaimWithDrawnDate(Date claimWithDrawnDate)
    {
        this.claimWithDrawnDate = claimWithDrawnDate;
    }

    public Date getClaimWithDrawnDate()
    {
        return claimWithDrawnDate;
    }

    public void setClaimReplyRecvdDate(Date claimReplyRecvdDate)
    {
        this.claimReplyRecvdDate = claimReplyRecvdDate;
    }

    public Date getClaimReplyRecvdDate()
    {
        return claimReplyRecvdDate;
    }

    public void setTempClosedDate(Date tempClosedDate)
    {
        this.tempClosedDate = tempClosedDate;
    }

    public Date getTempClosedDate()
    {
        return tempClosedDate;
    }

    public void setTempRejectedDate(Date tempRejectedDate)
    {
        this.tempRejectedDate = tempRejectedDate;
    }

    public Date getTempRejectedDate()
    {
        return tempRejectedDate;
    }

    public void setTempRejOrRejDt(Date tempRejOrRejDt)
    {
        this.tempRejOrRejDt = tempRejOrRejDt;
    }

    public Date getTempRejOrRejDt()
    {
        return tempRejOrRejDt;
    }

    public void setLastActionTakenDt(Date lastActionTakenDt)
    {
        this.lastActionTakenDt = lastActionTakenDt;
    }

    public Date getLastActionTakenDt()
    {
        return lastActionTakenDt;
    }

    public void setReturnRemarks(String returnRemarks)
    {
        this.returnRemarks = returnRemarks;
    }

    public String getReturnRemarks()
    {
        return returnRemarks;
    }
}