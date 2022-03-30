// FrontEnd Plus GUI for JAD
// DeCompiled : ClaimApplication.class

package com.cgtsi.claim;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

// Referenced classes of package com.cgtsi.claim:
//            MemberInfo, BorrowerInfo, LegalProceedingsDetail, SecurityAndPersonalGuaranteeDtls

public class ClaimApplication
    implements Serializable
{

    private String memberId;
    private String borrowerId;
    private MemberInfo memberDetails;
    private BorrowerInfo borrowerDetails;
    private Date dateOnWhichAccountClassifiedNPA;
    private Date dateOfReportingNpaToCgtsi;
    private String reasonsForAccountTurningNPA;
    private Date dateOfIssueOfRecallNotice;
    private LegalProceedingsDetail legalProceedingsDetails;
    private Date dateOfReleaseOfWC;
    private SecurityAndPersonalGuaranteeDtls securityAndPersonalGuaranteeDtls;
    private Date dateOfSeekingOTS;
    private Vector recoveryDetails;
    private String nameOfOfficial;
    private String designationOfOfficial;
    private Date claimSubmittedDate;
    private String place;
    private String designation;
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
    private String view;
    private ArrayList claimSummaryDtls;
    private ArrayList workingCapitalDtls;
    private Vector termCapitalDtls;
    private String participatingBank;
    private String claimRefNumber;
    private String whetherBorrowerIsWilfulDefaulter;
    private String whetherAccntWrittenOffFromBooksOfMLI;
    private Date dtOnWhichAccntWrittenOff;
    private Date dtOfConclusionOfRecoveryProc;
    private Date claimApprovedDt;
    private String cgclan;
    private String clpan;
    private boolean firstInstallment;
    private boolean secondInstallment;
    private boolean isVerified;
    private String dateOnWhichAccountClassifiedNPAStr;
    private String dateOfReportingNpaToCgtsiStr;
    private String dateOfIssueOfRecallNoticeStr;
    private String dateOfReleaseOfWCStr;
    private String dateOfSeekingOTSStr;
    private String claimSubmittedDateStr;
    private String claimApprovedDtStr;
    private String dtOnWhichAccntWrittenOffStr;
    private Date claimSettlementDate;
    private SimpleDateFormat sdf;
    private String createdModifiedy;
    private String legalDetailsFileName;
    private byte legalDetailsFileData[];
    private String recalNoticeFileName;
    private byte recallNoticeFileData[];
    private String isActivityEligibleVal;
    private String isActivityEligibleValflag;
    private String whetherCibilval;
    private String whetherCibilvalflag;
    private String rateChargeVal;
    private String rateChargeValflag;
    private String thirdpartyGuaranteeVal;
    private String thirdpartyGuaranteeValflag;
    private String dateofNPAval;
    private String dateofNPAvalflag;
    private String outstandingAmountVal;
    private String outstandingAmountValflag;
    private String seriousDeficieniesVal;
    private String seriousDeficieniesValflag;
    private String majorDeficienciesObservedVal;
    private String majorDeficienciesObservedValflag;
    private String deficienciesObservedVal;
    private String deficienciesObservedValflag;
    private String internalRatingVal;
    private String internalRatingValflag;
    private String alltheRecoveriesVal;
    private String alltheRecoveriesValflag;
    private Date subsidyDate;
    private double subsidyAmt;
    private String ifsCode;
    private String neftCode;
    private String rtgsBankName;
    private String rtgsBankNumber;
    private String rtgsBranchName;
    private String unitName;
    private String microCategory;
    private String claimProceedings;
    private String unitAssistedMSE;
    private String wilful;
    private String fraudFlag;
    private String enquiryFlag;
    private String mliInvolvementFlag;
    private String reasonForRecall;
    private String reasonForFilingSuit;
    private Date assetPossessionDt;
    private String inclusionOfReceipt;
    private String confirmRecoveryFlag;
    private String subsidyFlag;
    private String isSubsidyAdjustedOnDues;
    private String isSubsidyRcvdAfterNpa;
    private String mliCommentOnFinPosition;
    private String detailsOfFinAssistance;
    private String creditSupport;
    private String bankFacilityDetail;
    private String placeUnderWatchList;
    private String remarksOnNpa;
    private String returnRemarks;
    private String dealingOfficerName;
    private Vector workingCapitalDtlsVector;
/*Added for GST chagnes*/
    private String gstStateCode;
    public String getGstStateCode() {
		return gstStateCode;
	}

	public void setGstStateCode(String gstStateCode) {
		this.gstStateCode = gstStateCode;
	}

	public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

	private String gstNo;

    
    
    public String getDesignation()
    {
        return designation;
    }

    public void setDesignation(String designation)
    {
        this.designation = designation;
    }

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

    public String getView()
    {
        return view;
    }

    public void setView(String view)
    {
        this.view = view;
    }

    public String getIsActivityEligibleVal()
    {
        return isActivityEligibleVal;
    }

    public void setIsActivityEligibleVal(String isActivityEligibleVal)
    {
        this.isActivityEligibleVal = isActivityEligibleVal;
    }

    public String getIsActivityEligibleValflag()
    {
        return isActivityEligibleValflag;
    }

    public void setIsActivityEligibleValflag(String isActivityEligibleValflag)
    {
        this.isActivityEligibleValflag = isActivityEligibleValflag;
    }

    public String getWhetherCibilval()
    {
        return whetherCibilval;
    }

    public void setWhetherCibilval(String whetherCibilval)
    {
        this.whetherCibilval = whetherCibilval;
    }

    public String getWhetherCibilvalflag()
    {
        return whetherCibilvalflag;
    }

    public void setWhetherCibilvalflag(String whetherCibilvalflag)
    {
        this.whetherCibilvalflag = whetherCibilvalflag;
    }

    public String getRateChargeVal()
    {
        return rateChargeVal;
    }

    public void setRateChargeVal(String rateChargeVal)
    {
        this.rateChargeVal = rateChargeVal;
    }

    public String getRateChargeValflag()
    {
        return rateChargeValflag;
    }

    public void setRateChargeValflag(String rateChargeValflag)
    {
        this.rateChargeValflag = rateChargeValflag;
    }

    public String getThirdpartyGuaranteeVal()
    {
        return thirdpartyGuaranteeVal;
    }

    public void setThirdpartyGuaranteeVal(String thirdpartyGuaranteeVal)
    {
        this.thirdpartyGuaranteeVal = thirdpartyGuaranteeVal;
    }

    public String getThirdpartyGuaranteeValflag()
    {
        return thirdpartyGuaranteeValflag;
    }

    public void setThirdpartyGuaranteeValflag(String thirdpartyGuaranteeValflag)
    {
        this.thirdpartyGuaranteeValflag = thirdpartyGuaranteeValflag;
    }

    public String getDateofNPAval()
    {
        return dateofNPAval;
    }

    public void setDateofNPAval(String dateofNPAval)
    {
        this.dateofNPAval = dateofNPAval;
    }

    public String getDateofNPAvalflag()
    {
        return dateofNPAvalflag;
    }

    public void setDateofNPAvalflag(String dateofNPAvalflag)
    {
        this.dateofNPAvalflag = dateofNPAvalflag;
    }

    public String getOutstandingAmountVal()
    {
        return outstandingAmountVal;
    }

    public void setOutstandingAmountVal(String outstandingAmountVal)
    {
        this.outstandingAmountVal = outstandingAmountVal;
    }

    public String getOutstandingAmountValflag()
    {
        return outstandingAmountValflag;
    }

    public void setOutstandingAmountValflag(String outstandingAmountValflag)
    {
        this.outstandingAmountValflag = outstandingAmountValflag;
    }

    public String getSeriousDeficieniesVal()
    {
        return seriousDeficieniesVal;
    }

    public void setSeriousDeficieniesVal(String seriousDeficieniesVal)
    {
        this.seriousDeficieniesVal = seriousDeficieniesVal;
    }

    public String getSeriousDeficieniesValflag()
    {
        return seriousDeficieniesValflag;
    }

    public void setSeriousDeficieniesValflag(String seriousDeficieniesValflag)
    {
        this.seriousDeficieniesValflag = seriousDeficieniesValflag;
    }

    public String getMajorDeficienciesObservedVal()
    {
        return majorDeficienciesObservedVal;
    }

    public void setMajorDeficienciesObservedVal(String majorDeficienciesObservedVal)
    {
        this.majorDeficienciesObservedVal = majorDeficienciesObservedVal;
    }

    public String getMajorDeficienciesObservedValflag()
    {
        return majorDeficienciesObservedValflag;
    }

    public void setMajorDeficienciesObservedValflag(String majorDeficienciesObservedValflag)
    {
        this.majorDeficienciesObservedValflag = majorDeficienciesObservedValflag;
    }

    public String getDeficienciesObservedVal()
    {
        return deficienciesObservedVal;
    }

    public void setDeficienciesObservedVal(String deficienciesObservedVal)
    {
        this.deficienciesObservedVal = deficienciesObservedVal;
    }

    public String getDeficienciesObservedValflag()
    {
        return deficienciesObservedValflag;
    }

    public void setDeficienciesObservedValflag(String deficienciesObservedValflag)
    {
        this.deficienciesObservedValflag = deficienciesObservedValflag;
    }

    public String getInternalRatingVal()
    {
        return internalRatingVal;
    }

    public void setInternalRatingVal(String internalRatingVal)
    {
        this.internalRatingVal = internalRatingVal;
    }

    public String getInternalRatingValflag()
    {
        return internalRatingValflag;
    }

    public void setInternalRatingValflag(String internalRatingValflag)
    {
        this.internalRatingValflag = internalRatingValflag;
    }

    public String getAlltheRecoveriesVal()
    {
        return alltheRecoveriesVal;
    }

    public void setAlltheRecoveriesVal(String alltheRecoveriesVal)
    {
        this.alltheRecoveriesVal = alltheRecoveriesVal;
    }

    public String getAlltheRecoveriesValflag()
    {
        return alltheRecoveriesValflag;
    }

    public void setAlltheRecoveriesValflag(String alltheRecoveriesValflag)
    {
        this.alltheRecoveriesValflag = alltheRecoveriesValflag;
    }

    public ClaimApplication()
    {
        sdf = null;
        legalDetailsFileName = null;
        legalDetailsFileData = null;
        recalNoticeFileName = null;
        recallNoticeFileData = null;
    }

    public String getClaimProceedings()
    {
        return claimProceedings;
    }

    public void setClaimProceedings(String claimProceedings)
    {
        this.claimProceedings = claimProceedings;
    }

    public String getMicroCategory()
    {
        return microCategory;
    }

    public void setMicroCategory(String microCategory)
    {
        this.microCategory = microCategory;
    }

    public String getUnitName()
    {
        return unitName;
    }

    public void setUnitName(String unitName)
    {
        this.unitName = unitName;
    }

    public Date getSubsidyDate()
    {
        return subsidyDate;
    }

    public void setSubsidyDate(Date date)
    {
        subsidyDate = date;
    }

    public double getSubsidyAmt()
    {
        return subsidyAmt;
    }

    public void setSubsidyAmt(double subsidyAmt)
    {
        this.subsidyAmt = subsidyAmt;
    }

    public String getIfsCode()
    {
        return ifsCode;
    }

    public void setIfsCode(String ifsCode)
    {
        this.ifsCode = ifsCode;
    }

    public String getNeftCode()
    {
        return neftCode;
    }

    public void setNeftCode(String neftCode)
    {
        this.neftCode = neftCode;
    }

    public String getRtgsBankName()
    {
        return rtgsBankName;
    }

    public void setRtgsBankName(String rtgsBankName)
    {
        this.rtgsBankName = rtgsBankName;
    }

    public String getRtgsBankNumber()
    {
        return rtgsBankNumber;
    }

    public void setRtgsBankNumber(String rtgsBankNumber)
    {
        this.rtgsBankNumber = rtgsBankNumber;
    }

    public String getRtgsBranchName()
    {
        return rtgsBranchName;
    }

    public void setRtgsBranchName(String rtgsBranchName)
    {
        this.rtgsBranchName = rtgsBranchName;
    }

    public String getMemberId()
    {
        return memberId;
    }

    public void setMemberId(String aMemberId)
    {
        memberId = aMemberId;
    }

    public String getBorrowerId()
    {
        return borrowerId;
    }

    public void setBorrowerId(String aBorrowerId)
    {
        borrowerId = aBorrowerId;
    }

    public MemberInfo getMemberDetails()
    {
        return memberDetails;
    }

    public void setMemberDetails(MemberInfo aMemberDetails)
    {
        memberDetails = aMemberDetails;
    }

    public BorrowerInfo getBorrowerDetails()
    {
        return borrowerDetails;
    }

    public void setBorrowerDetails(BorrowerInfo aBorrowerDetails)
    {
        borrowerDetails = aBorrowerDetails;
    }

    public Date getDateOnWhichAccountClassifiedNPA()
    {
        return dateOnWhichAccountClassifiedNPA;
    }

    public void setDateOnWhichAccountClassifiedNPA(Date aDateOnWhichAccountClassifiedNPA)
    {
        dateOnWhichAccountClassifiedNPA = aDateOnWhichAccountClassifiedNPA;
    }

    public Date getDateOfReportingNpaToCgtsi()
    {
        return dateOfReportingNpaToCgtsi;
    }

    public void setDateOfReportingNpaToCgtsi(Date aDateOfReportingNpaToCgtsi)
    {
        dateOfReportingNpaToCgtsi = aDateOfReportingNpaToCgtsi;
    }

    public String getReasonsForAccountTurningNPA()
    {
        return reasonsForAccountTurningNPA;
    }

    public void setReasonsForAccountTurningNPA(String aReasonsForAccountTurningNPA)
    {
        reasonsForAccountTurningNPA = aReasonsForAccountTurningNPA;
    }

    public Date getDateOfIssueOfRecallNotice()
    {
        return dateOfIssueOfRecallNotice;
    }

    public void setDateOfIssueOfRecallNotice(Date aDateOfIssueOfRecallNotice)
    {
        dateOfIssueOfRecallNotice = aDateOfIssueOfRecallNotice;
    }

    public LegalProceedingsDetail getLegalProceedingsDetails()
    {
        return legalProceedingsDetails;
    }

    public void setLegalProceedingsDetails(LegalProceedingsDetail aLegalProceedingsDetails)
    {
        legalProceedingsDetails = aLegalProceedingsDetails;
    }

    public Date getDateOfReleaseOfWC()
    {
        return dateOfReleaseOfWC;
    }

    public void setDateOfReleaseOfWC(Date aDateOfReleaseOfWC)
    {
        dateOfReleaseOfWC = aDateOfReleaseOfWC;
    }

    public SecurityAndPersonalGuaranteeDtls getSecurityAndPersonalGuaranteeDtls()
    {
        return securityAndPersonalGuaranteeDtls;
    }

    public void setSecurityAndPersonalGuaranteeDtls(SecurityAndPersonalGuaranteeDtls aSecurityAndPersonalGuaranteeDtls)
    {
        securityAndPersonalGuaranteeDtls = aSecurityAndPersonalGuaranteeDtls;
    }

    public Date getDateOfSeekingOTS()
    {
        return dateOfSeekingOTS;
    }

    public void setDateOfSeekingOTS(Date aDateOfSeekingOTS)
    {
        dateOfSeekingOTS = aDateOfSeekingOTS;
    }

    public Vector getRecoveryDetails()
    {
        return recoveryDetails;
    }

    public void setRecoveryDetails(Vector aRecoveryDetails)
    {
        recoveryDetails = aRecoveryDetails;
    }

    public String getNameOfOfficial()
    {
        return nameOfOfficial;
    }

    public void setNameOfOfficial(String aNameOfOfficial)
    {
        nameOfOfficial = aNameOfOfficial;
    }

    public String getDesignationOfOfficial()
    {
        return designationOfOfficial;
    }

    public void setDesignationOfOfficial(String aDesignationOfOfficial)
    {
        designationOfOfficial = aDesignationOfOfficial;
    }

    public Date getClaimSubmittedDate()
    {
        return claimSubmittedDate;
    }

    public void setClaimSubmittedDate(Date aClaimSubmittedDate)
    {
        claimSubmittedDate = aClaimSubmittedDate;
    }

    public String getPlace()
    {
        return place;
    }

    public void setPlace(String aPlace)
    {
        place = aPlace;
    }

    public ArrayList getClaimSummaryDtls()
    {
        return claimSummaryDtls;
    }

    public void setClaimSummaryDtls(ArrayList aClaimSummaryDtls)
    {
        claimSummaryDtls = aClaimSummaryDtls;
    }

    public ArrayList getWorkingCapitalDtls()
    {
        return workingCapitalDtls;
    }

    public void setWorkingCapitalDtls(ArrayList aWorkingCapitalDtls)
    {
        workingCapitalDtls = aWorkingCapitalDtls;
    }

    public Vector getTermCapitalDtls()
    {
        return termCapitalDtls;
    }

    public void setTermCapitalDtls(Vector aTermCapitalDtls)
    {
        termCapitalDtls = aTermCapitalDtls;
    }

    public String getParticipatingBank()
    {
        return participatingBank;
    }

    public void setParticipatingBank(String aParticipatingBank)
    {
        participatingBank = aParticipatingBank;
    }

    public String getClaimRefNumber()
    {
        return claimRefNumber;
    }

    public void setClaimRefNumber(String aClaimRefNumber)
    {
        claimRefNumber = aClaimRefNumber;
    }

    public String getWhetherBorrowerIsWilfulDefaulter()
    {
        return whetherBorrowerIsWilfulDefaulter;
    }

    public void setWhetherBorrowerIsWilfulDefaulter(String flag)
    {
        whetherBorrowerIsWilfulDefaulter = flag;
    }

    public Date getDtOnWhichAccntWrittenOff()
    {
        return dtOnWhichAccntWrittenOff;
    }

    public void setDtOnWhichAccntWrittenOff(Date date)
    {
        dtOnWhichAccntWrittenOff = date;
    }

    public String getClpan()
    {
        return clpan;
    }

    public void setClpan(String aClpan)
    {
        clpan = aClpan;
    }

    public boolean getFirstInstallment()
    {
        return firstInstallment;
    }

    public void setFirstInstallment(boolean aFirstInstallment)
    {
        firstInstallment = aFirstInstallment;
    }

    public boolean getSecondInstallment()
    {
        return secondInstallment;
    }

    public void setSecondInstallment(boolean aSecondInstallment)
    {
        secondInstallment = aSecondInstallment;
    }

    public Date getDtOfConclusionOfRecoveryProc()
    {
        return dtOfConclusionOfRecoveryProc;
    }

    public void setDtOfConclusionOfRecoveryProc(Date dt)
    {
        dtOfConclusionOfRecoveryProc = dt;
    }

    public String getWhetherAccntWrittenOffFromBooksOfMLI()
    {
        return whetherAccntWrittenOffFromBooksOfMLI;
    }

    public void setWhetherAccntWrittenOffFromBooksOfMLI(String flag)
    {
        whetherAccntWrittenOffFromBooksOfMLI = flag;
    }

    public Date getClaimApprovedDt()
    {
        return claimApprovedDt;
    }

    public void setClaimApprovedDt(Date dt)
    {
        claimApprovedDt = dt;
    }

    public String getCgclan()
    {
        return cgclan;
    }

    public void setCgclan(String aCgclan)
    {
        cgclan = aCgclan;
    }

    public boolean getIsVerified()
    {
        return isVerified;
    }

    public void setIsVerified(boolean b)
    {
        isVerified = b;
    }

    public String getDateOnWhichAccountClassifiedNPAStr()
    {
        sdf = new SimpleDateFormat("dd/MM/yyyy");
        if(dateOnWhichAccountClassifiedNPA != null)
            dateOnWhichAccountClassifiedNPAStr = sdf.format(dateOnWhichAccountClassifiedNPA);
        return dateOnWhichAccountClassifiedNPAStr;
    }

    public void setDateOnWhichAccountClassifiedNPAStr(String str)
    {
        dateOnWhichAccountClassifiedNPAStr = str;
    }

    public String getDateOfReportingNpaToCgtsiStr()
    {
        sdf = new SimpleDateFormat("dd/MM/yyyy");
        if(dateOfReportingNpaToCgtsi != null)
            dateOfReportingNpaToCgtsiStr = sdf.format(dateOfReportingNpaToCgtsi);
        return dateOfReportingNpaToCgtsiStr;
    }

    public void setDateOfReportingNpaToCgtsiStr(String str)
    {
        dateOfReportingNpaToCgtsiStr = str;
    }

    public String getDateOfIssueOfRecallNoticeStr()
    {
        sdf = new SimpleDateFormat("dd/MM/yyyy");
        if(dateOfIssueOfRecallNotice != null)
            dateOfIssueOfRecallNoticeStr = sdf.format(dateOfIssueOfRecallNotice);
        return dateOfIssueOfRecallNoticeStr;
    }

    public void setDateOfIssueOfRecallNoticeStr(String str)
    {
        dateOfIssueOfRecallNoticeStr = str;
    }

    public String getDateOfReleaseOfWCStr()
    {
        sdf = new SimpleDateFormat("dd/MM/yyyy");
        if(dateOfReleaseOfWC != null)
            dateOfReleaseOfWCStr = sdf.format(dateOfReleaseOfWC);
        return dateOfReleaseOfWCStr;
    }

    public void setDateOfReleaseOfWC(String str)
    {
        dateOfReleaseOfWCStr = str;
    }

    public String getDateOfSeekingOTSStr()
    {
        sdf = new SimpleDateFormat("dd/MM/yyyy");
        if(dateOfSeekingOTS != null)
            dateOfSeekingOTSStr = sdf.format(dateOfSeekingOTS);
        return dateOfSeekingOTSStr;
    }

    public void setDateOfSeekingOTS(String str)
    {
        dateOfSeekingOTSStr = str;
    }

    public String getClaimSubmittedDateStr()
    {
        sdf = new SimpleDateFormat("dd/MM/yyyy");
        if(claimSubmittedDate != null)
            claimSubmittedDateStr = sdf.format(claimSubmittedDate);
        return claimSubmittedDateStr;
    }

    public void setClaimSubmittedDateStr(String str)
    {
        claimSubmittedDateStr = str;
    }

    public String getClaimApprovedDtStr()
    {
        sdf = new SimpleDateFormat("dd/MM/yyyy");
        if(claimApprovedDt != null)
            claimApprovedDtStr = sdf.format(claimApprovedDt);
        return claimApprovedDtStr;
    }

    public void setClaimApprovedDtStr(String str)
    {
        claimApprovedDtStr = str;
    }

    public String getDtOnWhichAccntWrittenOffStr()
    {
        sdf = new SimpleDateFormat("dd/MM/yyyy");
        if(dtOnWhichAccntWrittenOff != null)
            dtOnWhichAccntWrittenOffStr = sdf.format(dtOnWhichAccntWrittenOff);
        return dtOnWhichAccntWrittenOffStr;
    }

    public void setDtOnWhichAccntWrittenOffStr(String str)
    {
        dtOnWhichAccntWrittenOffStr = str;
    }

    public Date getClaimSettlementDate()
    {
        return claimSettlementDate;
    }

    public void setClaimSettlementDate(Date date)
    {
        claimSettlementDate = date;
    }

    public byte[] getLegalDetailsFileData()
    {
        return legalDetailsFileData;
    }

    public String getLegalDetailsFileName()
    {
        return legalDetailsFileName;
    }

    public byte[] getRecallNoticeFileData()
    {
        return recallNoticeFileData;
    }

    public String getRecalNoticeFileName()
    {
        return recalNoticeFileName;
    }

    public void setLegalDetailsFileData(byte bs[])
    {
        legalDetailsFileData = bs;
    }

    public void setLegalDetailsFileName(String string)
    {
        legalDetailsFileName = string;
    }

    public void setRecallNoticeFileData(byte bs[])
    {
        recallNoticeFileData = bs;
    }

    public void setRecalNoticeFileName(String string)
    {
        recalNoticeFileName = string;
    }

    public String getCreatedModifiedy()
    {
        return createdModifiedy;
    }

    public void setCreatedModifiedy(String id)
    {
        createdModifiedy = id;
    }

    public void setUnitAssistedMSE(String unitAssistedMSE)
    {
        this.unitAssistedMSE = unitAssistedMSE;
    }

    public String getUnitAssistedMSE()
    {
        return unitAssistedMSE;
    }

    public void setWilful(String wilful)
    {
        this.wilful = wilful;
    }

    public String getWilful()
    {
        return wilful;
    }

    public void setFraudFlag(String fraudFlag)
    {
        this.fraudFlag = fraudFlag;
    }

    public String getFraudFlag()
    {
        return fraudFlag;
    }

    public void setEnquiryFlag(String enquiryFlag)
    {
        this.enquiryFlag = enquiryFlag;
    }

    public String getEnquiryFlag()
    {
        return enquiryFlag;
    }

    public void setMliInvolvementFlag(String mliInvolvementFlag)
    {
        this.mliInvolvementFlag = mliInvolvementFlag;
    }

    public String getMliInvolvementFlag()
    {
        return mliInvolvementFlag;
    }

    public void setReasonForRecall(String reasonForRecall)
    {
        this.reasonForRecall = reasonForRecall;
    }

    public String getReasonForRecall()
    {
        return reasonForRecall;
    }

    public void setReasonForFilingSuit(String reasonForFilingSuit)
    {
        this.reasonForFilingSuit = reasonForFilingSuit;
    }

    public String getReasonForFilingSuit()
    {
        return reasonForFilingSuit;
    }

    public void setAssetPossessionDt(Date assetPossessionDt)
    {
        this.assetPossessionDt = assetPossessionDt;
    }

    public Date getAssetPossessionDt()
    {
        return assetPossessionDt;
    }

    public void setInclusionOfReceipt(String inclusionOfReceipt)
    {
        this.inclusionOfReceipt = inclusionOfReceipt;
    }

    public String getInclusionOfReceipt()
    {
        return inclusionOfReceipt;
    }

    public void setSubsidyFlag(String subsidyFlag)
    {
        this.subsidyFlag = subsidyFlag;
    }

    public String getSubsidyFlag()
    {
        return subsidyFlag;
    }

    public void setCreditSupport(String creditSupport)
    {
        this.creditSupport = creditSupport;
    }

    public String getCreditSupport()
    {
        return creditSupport;
    }

    public void setConfirmRecoveryFlag(String confirmRecoveryFlag)
    {
        this.confirmRecoveryFlag = confirmRecoveryFlag;
    }

    public String getConfirmRecoveryFlag()
    {
        return confirmRecoveryFlag;
    }

    public void setIsSubsidyAdjustedOnDues(String isSubsidyAdjustedOnDues)
    {
        this.isSubsidyAdjustedOnDues = isSubsidyAdjustedOnDues;
    }

    public String getIsSubsidyAdjustedOnDues()
    {
        return isSubsidyAdjustedOnDues;
    }

    public void setIsSubsidyRcvdAfterNpa(String isSubsidyRcvdAfterNpa)
    {
        this.isSubsidyRcvdAfterNpa = isSubsidyRcvdAfterNpa;
    }

    public String getIsSubsidyRcvdAfterNpa()
    {
        return isSubsidyRcvdAfterNpa;
    }

    public void setMliCommentOnFinPosition(String mliCommentOnFinPosition)
    {
        this.mliCommentOnFinPosition = mliCommentOnFinPosition;
    }

    public String getMliCommentOnFinPosition()
    {
        return mliCommentOnFinPosition;
    }

    public void setDetailsOfFinAssistance(String detailsOfFinAssistance)
    {
        this.detailsOfFinAssistance = detailsOfFinAssistance;
    }

    public String getDetailsOfFinAssistance()
    {
        return detailsOfFinAssistance;
    }

    public void setBankFacilityDetail(String bankFacilityDetail)
    {
        this.bankFacilityDetail = bankFacilityDetail;
    }

    public String getBankFacilityDetail()
    {
        return bankFacilityDetail;
    }

    public void setPlaceUnderWatchList(String placeUnderWatchList)
    {
        this.placeUnderWatchList = placeUnderWatchList;
    }

    public String getPlaceUnderWatchList()
    {
        return placeUnderWatchList;
    }

    public void setRemarksOnNpa(String remarksOnNpa)
    {
        this.remarksOnNpa = remarksOnNpa;
    }

    public String getRemarksOnNpa()
    {
        return remarksOnNpa;
    }

    public void setDealingOfficerName(String dealingOfficerName)
    {
        this.dealingOfficerName = dealingOfficerName;
    }

    public String getDealingOfficerName()
    {
        return dealingOfficerName;
    }

    public void setWorkingCapitalDtlsVector(Vector workingCapitalDtlsVector)
    {
        this.workingCapitalDtlsVector = workingCapitalDtlsVector;
    }

    public Vector getWorkingCapitalDtlsVector()
    {
        return workingCapitalDtlsVector;
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