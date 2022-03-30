// FrontEnd Plus GUI for JAD
// DeCompiled : ClaimActionForm.class

package com.cgtsi.actionform;

import com.cgtsi.claim.*;
import com.cgtsi.common.Log;
import com.cgtsi.registration.CollectingBank;
import com.cgtsi.reports.ApplicationReport;
import java.io.PrintStream;
import java.util.*;
import javax.servlet.ServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorActionForm;

public class ClaimActionForm extends ValidatorActionForm
{

    private String bankId;
    private String memberId;
    private String borrowerID;
    private String cgpan;
    private String recoveryFlag;
    private String otsFlag;
    private String microFlag;
    private String userId;
    private MemberInfo memberDetails;
    private BorrowerInfo borrowerDetails;
    private HashMap npaDetails;
    private Date dateOnWhichAccountClassifiedNPA;
    private String dateOfRecallNotice;
    private FormFile recallnoticefilepath;
    private String forumthrulegalinitiated;
    private String otherforums;
    private String caseregnumber;
    private String legaldate;
    private String npaDateNew;
    private String nameofforum;
    private String location;
    private String amountclaimed;
    private FormFile legalAttachmentPath;
    private FormFile staffAcAttachmentPath;
    private String isActivityEligible;
    private String whetherCibil;
    private String rateCharge;
    private String thirdpartyGuarantee;
    private String dateofNPA;
    private String outstandingAmount;
    private String seriousDeficienies;
    private String majorDeficienciesObserved;
    private String deficienciesObserved;
    private String internalRating;
    private String alltheRecoveries;
    private String designa;
    private String nameOfOffi;
    private String places;
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
    private String whetherCibilflag;
    private String isActivityEligibleflag;
    private String rateChargeflag;
    private String thirdpartyGuaranteeflag;
    private String dateofNPAflag;
    private String outstandingAmountflag;
    private String seriousDeficieniesflag;
    private String majorDeficienciesObservedflag;
    private String deficienciesObservedflag;
    private String internalRatingflag;
    private String alltheRecoveriesflag;
    private String currentstatusremarks;
    private String proceedingsConcluded;
    private Vector tcCgpansVector;
    private Map cgpandetails;
    private Map lastDisbursementDate;
    private Map totalDisbursementAmt;
    private Map appApprovedAmt;
    private Map tcprincipal;
    private Map tcInterestCharge;
    private Map tcOsAsOnDateOfNPA;
    private Map tcOsAsStatedInCivilSuit;
    private Map tcOsAsOnLodgementOfClaim;
    private Map tcOsAsOnLodgementOfSecondClaim;
    private Vector wcCgpansVector;
    private Map wcCgpan;
    private Map wcOsAsOnDateOfNPA;
    private Map wcOsAsStatedInCivilSuit;
    private Map wcOsAsOnLodgementOfClaim;
    private Map wcOsAsOnLodgementOfSecondClaim;
    private Map claimFlagsTc;
    private Map claimFlagsWc;
    private String dateOfReleaseOfWC;
    private Map asOnDtOfSanctionDtl;
    private Map asOnDtOfNPA;
    private Map asOnLodgemntOfCredit;
    private Map asOnDateOfLodgemntOfSecondClm;
    private Vector cgpansVector;
    private Vector recoveryModes;
    private String dateOfSeekingOTS;
    private Map claimSummaryDetails;
    private Vector cgpnDetails;
    private HashMap securityDetails;
    private ApplicationReport appReport;
    private ClaimApplication claimapplication;
    private String itpanOfChiefPromoter;
    private HashMap itpanDetails;
    private Map danSummaryReportDetails;
    private HashMap clmDtlForFirstInstllmnt;
    private String dtOfConclusionOfRecoveryProc;
    private String whetherAccntWasWrittenOffBooks;
    private String dtOnWhichAccntWrittenOff;
    private Map amntRealizedThruDisposalOfSecurity;
    private String amntRealizedThruInvocationOfPerGuarantees;
    private String nameOfOfficial;
    private String claimSubmittedDate;
    private String designationOfOfficial;
    private String place;
    private Vector otsRequestDtls;
    private String reasonForOTS;
    private String wilfullDefaulter;
    private Map proposedAmntPaidByBorrower;
    private Map proposedAmntSacrificed;
    private Map osAmntOnDateForOTS;
    private Vector otsprocessdetails;
    private Map decision;
    private Map remarks;
    private Vector otsReferenceDetails;
    private String userRemarks;
    private Vector firstInstallmentClaims;
    private Vector secondInstallmentClaims;
    private Map approvedClaimAmount;
    private Map asfToGenerate;
    private HashMap claimSummaries;
    private ClaimDetail claimdetail;
    private Vector settlementdetail;
    private String whtherDisbursemntProcHasConcluded;
    private String servicefee;
    private String dtofservicefee;
    private String wcOtherChargesAsOnNPA;
    private String totalAmntAsOnNPA;
    private String tcInterestChargeForThisBorrower;
    private String wcintrstchrgsrecafternpa;
    private String tcPrinRecoveriesAfterNPA;
    private String tcInterestChargesRecovAfterNPA;
    private String wcPrincipalRecoveAfterNPA;
    private String wcothercgrgsRecAfterNPA;
    private String totalrecoveriesafternpa;
    private String totalAmntPayableNow;
    private String paymentAmnt;
    private String paymentVoucherId;
    private Vector settlementsOfFirstClaim;
    private Vector settlementsOfSecondClaim;
    private Map settlementAmounts;
    private Map finalSettlementFlags;
    private Map pendingAmntsFromMLI;
    private Map penaltyFees;
    private Map settlementDates;
    private Vector settledClms;
    private String memberIdFlag;
    private Map paymentVoucherIds;
    private Vector settlmntAdviceDtlsFirstSttlmnt;
    private Vector settlmntAdviceDtlsSecondSttlmnt;
    private Map settlementAdviceFlags;
    private Vector checkedFirstSettlmntAdviceDtls;
    private Vector checkedSecondSettlmntAdviceDtls;
    private Date toDate;
    private Date fromDate;
    private String clmApplicationStatus;
    private Vector listOfClmRefNumbers;
    private String firstSettlementIndexValue;
    private String secondSettlementIndexValue;
    private Vector updatedClaimDtls;
    private String statusFlag;
    private int firstCounter;
    private int secondCounter;
    private int tcCounter;
    private int wcCounter;
    private int apprvdVectorSize;
    private int rejectdVectorSize;
    private int heldVectorSize;
    private int forwardedVectorSize;
    private int tempclosedVectorSize;
    private ArrayList instrumentTypes;
    private String modeOfPayment;
    private CollectingBank collectingBank;
    private Date paymentDate;
    private String instrumentDate;
    private double instrumenAmount;
    private String subsidyDate;
    private double subsidyAmt;
    private String ifsCode;
    private String neftCode;
    private String rtgsBankName;
    private String rtgsBankNumber;
    private String rtgsBranchName;
    private ArrayList danSummary;
    private String clmRefNumber;
    private String unitName;
    private double totalLaibilityforTC;
    private double totalLiablityforWC;
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
    private double totalRecovery;
    private double totalOsAmntAsOnNPA;
    private double tcIssued;
    private double wcIssued;
    private double tcNetOutstanding;
    private double wcNetOutstanding;
    private double totalNetOutstanding;
    private double totalClaimEligibleAmt;
    private double totalFirstInstalment;
    private String drawnAtBank;
    private String drawnAtBranch;
    private String payableAt;
    private String instrumentNo;
    private String clmRefDtlSet;
    private int limit;
    private Vector perGaurDtls;
    private ArrayList userIds;
    private Map forwardedToIds;
    private String test;
    private ArrayList banksList;
    private String bnkName;
    private ArrayList historyReport;
    private Date dateofReceipt;
    private String dateOfTheDocument36;
    private String dateOfTheDocument37;
    private String microCategory;
    private String isClaimProceedings;
    private Map approveClaims;
    private double plrorBaseIntestRate;
    private TreeMap comments;
    private String claimsubmitDU;
    private TreeMap duCertifyDecisionYes;
    private TreeMap duCertifyDecisionNo;
    private TreeMap whetherCibilYes;
    private TreeMap whetherCibilNo;
    private TreeMap rateChargeYes;
    private TreeMap rateChargeNo;
    private TreeMap thirdpartyGuaranteeYes;
    private TreeMap thirdpartyGuaranteeNo;
    private TreeMap dateofNPAYes;
    private TreeMap dateofNPANo;
    private TreeMap outstandingAmountYes;
    private TreeMap outstandingAmountNo;
    private TreeMap seriousDeficieniesYes;
    private TreeMap seriousDeficieniesNo;
    private TreeMap majorDeficienciesObservedYes;
    private TreeMap majorDeficienciesObservedNo;
    private TreeMap deficienciesObservedYes;
    private TreeMap deficienciesObservedNo;
    private TreeMap internalRatingYes;
    private TreeMap internalRatingNo;
    private TreeMap alltheRecoveriesYes;
    private TreeMap alltheRecoveriesNo;
    private String isNorthEastRegion;
    private String isWomenorNot;
    private double totGuaranteeAmt;
    private String view;
    private String clmreferenceNumber;
    private String unitname;
    private String branchname;
    private double guranteeApprovedamt;
    private Date dateclaim;
    private String viewdu;
    private String contentofdu;
    private String unitNAME;
    private String bankName;
    private String memberids;
    private String zone;
    private String cgPAN;
    private String claimRefNo;
    private String guaranteedAmount;
    private String claimSubmitCGTMSE;
    private String claimReturnedForLodgement;
    private String claimReturnedRemarks;
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
    private String cp_ots_enterCgpan;
    private double cp_ots_totAmount;
    private String cp_ots_liabtext;
    private String cp_ots_seconInsatlText;
    private double cp_ots_cgpanGaurnteeAmt;
    private double cp_ots_npaAmount;
    private double cp_ots_recoveryAmt;
    private double cp_ots_netOutstanding;
    private double cp_ots_liableamount;
    private double cp_ots_firstIntalpaidAmount;
    private double cp_ots_totRecAftFirInst;
    private double cp_ots_totDedtctAmt;
    private double cp_ots_totNotDedtctAmt;
    private double cp_ots_netRecovAmt;
    private double cp_ots_netAmountInDefoault;
    private double cp_ots_secIntalAMt;
    private double cp_ots_finalPayout;
    private String cp_ots_enterMember;
    private String cp_ots_appRefNo;
    private String cp_ots_mliName;
    private String cp_ots_unitName;
    private String cp_ots_gender;
    private String cp_ots_UnitAssitByMSE;
    private Date cp_ots_npaDate;
    private Date cp_ots_firstInstallDate;
    private Date cp_ots_clmappDate;
    ArrayList claimdeatilforOts;
    private double cp_ots_Total;
    private double cp_ots_npatotal;
    private double cp_ots_cp_ots_totRecAMt;
    ArrayList LoopobjtData;
    private double cp_ots_guarnteetotal;
    private double cp_ots_npatotalAmt;
    private double cp_ots_recovTotal;
    private double cp_ots_netOutstandingTotal;
    private double cp_ots_liavleAmtTotal;
    private double cp_ots_firstInstallmentPaidTotal;
    private double cp_ots_recafterfirstinstallTotal;
    private double cp_ots_totaDecdectAMt;
    private double cp_ots_totNotdedctAmt;
    private double cp_ots_totnetRecovAmt;
    private double cp_ots_netAmtindefoultTotal;
    private double cp_ots_secinstamentAmtTotal;
    private double cp_ots_finalPayoutAmtTotal;
    private String cp_ots_remarks2;
    private String ots_memberId;
    private String ots_clmRefNo;
    private String ots_unitName;
    private double ots_gaurnteeAmt;
    private double ots_amtInDefault;
    private double ots_recoveryPrimary;
    private double ots_netOutstanding;
    private double ots_liableAmt;
    private double ots_firstInstallPaidAmt;
    private double ots_recoveryAfterPrimary;
    private double ots_legalExpencesNotDeducted;
    private double ots_legalExpencesDeducted;
    private double ots_netRecovery;
    private double ots_netDefaultAmt;
    private double ots_secondInstallmentAmt;
    private double ots_finalPayout;
    private String ots_decisionTaken;
    private String ots_remark;
    private ArrayList otsClmProcess;
    private String cp_ots_userChoice;
    private String cp_ots_user;
    private ArrayList cp_ots_userList;
    private String clm_decision;
    private String cp_ots_fromDate;
    private String cp_ots_toDate;
    private double cp_ots_clmeligibleamt;
    private ArrayList claimformdataReport;
    private String claimRefNum;
    private String enterCgpan;
    private String enterMember;
    private String appRefNo;
    private String memberID;
    private String ssiUnitName;
    private Date dtOfNPAReportedToCGTSI;
    private String cgpanNo;
    private Date appApproveDate;
    private String appAmount;
    private ArrayList claimdeatil;
    private Date dateOfdeclartionRecive;
    private boolean booleanProperty;
    private String mliName;
    private String mliId;
    private String placeforClmRecovery;
    private String cgpanforclamRecovery;
    private double clmEligibleAmt;
    private double firstinstalmentrelease;
    private double amtRecipt;
    private Date dateOfreciept;
    private double expIncforRecovery;
    private String expDeducted;
    private double netRecovery;
    private String ddNo;
    private Date ddDate;
    private String remark;
    ArrayList viewRecArr;
    private boolean booleanExpInc;
    private boolean booleanFinalRecovery;
    private String falgforCasesafet;
    private String womenOperated;
    private String nerFlag;
    private Map claimRefSet;
    private Map q1Flags;
    private Map q2Flags;
    private Map q3Flags;
    private Map q4Flags;
    private Map q5Flags;
    private Map q6Flags;
    private Map q7Flags;
    private Map q8Flags;
    private Map q9Flags;
    private Map q10Flags;
    private Map q11Flags;
    private Map q12Flags;
    private Map q13Flags;
    private Map q14Flags;
    private Map ltrRefNoSet;
    private Map ltrDtSet;
    private Vector claimProcessingDetails;
    private Vector aVector;
    private Vector rVector;
    private Vector hVector;
    private Vector fVector;
    private Vector tVector;
    private Vector trVector;
    private Vector wdVector;
    private int temprejectedVectorSize;
    private int claimwithdrawnVectorSize;
    private int claimPendingVectorSize;
    private String newChipParmoGender;
    private String newborowerState;
    private String newTypeActivity;
    private String newSchemName;
    private String newNERFlag;
    private String newCGPAN;
    private double newGuarnteeIssueAmt;
    private double newAmtOutstandAsOnNPA;
    private double newAmtRecoverAfterNPA;
    private double newAmtClaimByMli;
    private double newAmtDeductedFromMli;
    private boolean cp_ots_microfalg;
    private double cp_ots_liablePercent;
    private Date firstInsatllDate;
    private double totalNetRecovery;
    private String closerrequest;
    private String ltr_ref;
    private ArrayList forCGPANWiseDataArray;
    private double testFirstInstalment;
    private String unitAssistedMSE;
    private String isAcctFraud;
    private String isEnquiryConcluded;
    private String isMLIInvolved;
    private String reasonForFilingSuit;
    private String assetPossessionDate;
    private String subsidyFlag;
    private String isSubsidyRcvdAfterNpa;
    private String isSubsidyAdjustedOnDues;
    private String mliCommentOnFinPosition;
    private String detailsOfFinAssistance;
    private String creditSupport;
    private String bankFacilityDetail;
    private String placeUnderWatchList;
    private String remarksOnNpa;
    private String reasonForIssueRecallNotice;
    private String inclusionOfReciept;
    private String confirmRecoveryValues;
    private double totSecAsOnSanc;
    private double totSecAsOnNpa;
    private double totSecAsOnClaim;
    private String dealingOfficerName;
    private String clmRefNumberNew;
    private FormFile npaReportFile;
    private FormFile diligenceReportFile;
    private FormFile postInspectionReportFile;
    private FormFile postNpaReportFile;
    private String insuranceFileFlag;
    private String insuranceReason;
    private FormFile suitReportFile;
    private FormFile finalVerdictFile;
    private FormFile idProofFile;
    private FormFile otherFile;
    private FormFile staffReportFile;
    private String bankRateType;
    private String securityRemarks;
    private String recoveryEffortsTaken;
    private String rating;
    private String branchAddress;
    private FormFile branchAddress1[];
    private String investGrad;
    private Map cgpans;
    private Map statementReportFiles;
    private Map appraisalReportFiles;
    private Map sanctionLetterFiles;
    private Map complianceReportFiles;
    private Map preInspectionReportFiles;
    private Map insuranceCopyFiles;
    private Map repayBeforeNpaAmts;
    private Map recoveryAfterNpaAmts;
    private Map interestRates;
    private Map additionalAttachFiles;
    private FormFile additionalstatementReportFiles1;
    private FormFile additionalAppraisalReportFiles1;
    private FormFile additionalSanctionLetterFiles1;
    private FormFile additionalComplianceReportFiles1;
    private FormFile additionalPreInspectionReportFiles1;
    private FormFile additionalInsuranceCopyFiles1;
    private FormFile additionalstatementReportFiles2;
    private FormFile additionalAppraisalReportFiles2;
    private FormFile additionalSanctionLetterFiles2;
    private FormFile additionalComplianceReportFiles2;
    private FormFile additionalPreInspectionReportFiles2;
    private FormFile additionalInsuranceCopyFiles2;
    private FormFile additionalstatementReportFiles3;
    private FormFile additionalAppraisalReportFiles3;
    private FormFile additionalSanctionLetterFiles3;
    private FormFile additionalComplianceReportFiles3;
    private FormFile additionalPreInspectionReportFiles3;
    private FormFile additionalInsuranceCopyFiles3;
    private double plr;
    private double rate;
    private String investmentGradeFlag;
    private String commHeadedByOfficerOrAbove;
    private FormFile diligenceReportFiles[];
    private FormFile postInspectionReportFiles[];
    private FormFile postNpaReportFiles[];
    private FormFile idProofFiles[];
    private FormFile otherFiles[];
    private ArrayList claimDandU;
    private ArrayList claimLodgedReport;
    private FormFile internalRatingFile;
/*Added for GST*/
    private String branchState;
    public String getBranchState() {
		return branchState;
	}

	public void setBranchState(String branchState) {
		this.branchState = branchState;
	}

	public String getGstState() {
		return gstState;
	}

	public void setGstState(String gstState) {
		this.gstState = gstState;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getGstCode() {
		return gstCode;
	}

	public void setGstCode(String gstCode) {
		this.gstCode = gstCode;
	}

	public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

	public List getBranchStateList() {
		return branchStateList;
	}

	public void setBranchStateList(List branchStateList) {
		this.branchStateList = branchStateList;
	}

	private String gstState;
    private String stateName;
    private String gstCode;
    private String mliBranchCode;
    private String stateCode;

    public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getMliBranchCode() {
		return mliBranchCode;
	}

	public void setMliBranchCode(String mliBranchCode) {
		this.mliBranchCode = mliBranchCode;
	}

	private String gstNo;

    private List branchStateList;

    
    public ClaimActionForm()
    {
        claimdeatilforOts = new ArrayList();
        LoopobjtData = new ArrayList();
        otsClmProcess = new ArrayList();
        cp_ots_userList = new ArrayList();
        claimformdataReport = new ArrayList();
        claimdeatil = new ArrayList();
        booleanProperty = false;
        viewRecArr = new ArrayList();
        booleanExpInc = false;
        booleanFinalRecovery = false;
        nerFlag = "N";
        claimRefSet = new HashMap();
        q1Flags = new HashMap();
        q2Flags = new HashMap();
        q3Flags = new HashMap();
        q4Flags = new HashMap();
        q5Flags = new HashMap();
        q6Flags = new HashMap();
        q7Flags = new HashMap();
        q8Flags = new HashMap();
        q9Flags = new HashMap();
        q10Flags = new HashMap();
        q11Flags = new HashMap();
        q12Flags = new HashMap();
        q13Flags = new HashMap();
        q14Flags = new HashMap();
        ltrRefNoSet = new HashMap();
        ltrDtSet = new HashMap();
        claimProcessingDetails = new Vector();
        aVector = new Vector();
        rVector = new Vector();
        hVector = new Vector();
        fVector = new Vector();
        tVector = new Vector();
        trVector = new Vector();
        wdVector = new Vector();
        closerrequest = "false";
        forCGPANWiseDataArray = new ArrayList();
        diligenceReportFiles = new FormFile[3];
        postInspectionReportFiles = new FormFile[3];
        postNpaReportFiles = new FormFile[3];
        idProofFiles = new FormFile[3];
        otherFiles = new FormFile[3];
        duCertifyDecisionYes = new TreeMap();
        duCertifyDecisionNo = new TreeMap();
        whetherCibilYes = new TreeMap();
        whetherCibilNo = new TreeMap();
        rateChargeYes = new TreeMap();
        rateChargeNo = new TreeMap();
        thirdpartyGuaranteeYes = new TreeMap();
        thirdpartyGuaranteeNo = new TreeMap();
        dateofNPAYes = new TreeMap();
        dateofNPANo = new TreeMap();
        outstandingAmountYes = new TreeMap();
        outstandingAmountNo = new TreeMap();
        seriousDeficieniesYes = new TreeMap();
        seriousDeficieniesNo = new TreeMap();
        majorDeficienciesObservedYes = new TreeMap();
        majorDeficienciesObservedNo = new TreeMap();
        deficienciesObservedYes = new TreeMap();
        deficienciesObservedNo = new TreeMap();
        internalRatingYes = new TreeMap();
        internalRatingNo = new TreeMap();
        alltheRecoveriesYes = new TreeMap();
        alltheRecoveriesNo = new TreeMap();
        comments = new TreeMap();
        tcCgpansVector = new Vector();
        cgpandetails = new HashMap();
        lastDisbursementDate = new HashMap();
        totalDisbursementAmt = new HashMap();
        appApprovedAmt = new HashMap();
        tcprincipal = new HashMap();
        tcInterestCharge = new HashMap();
        tcOsAsOnDateOfNPA = new HashMap();
        tcOsAsStatedInCivilSuit = new HashMap();
        tcOsAsOnLodgementOfClaim = new HashMap();
        tcOsAsOnLodgementOfSecondClaim = new HashMap();
        wcCgpan = new HashMap();
        wcOsAsOnDateOfNPA = new HashMap();
        wcOsAsStatedInCivilSuit = new HashMap();
        wcOsAsOnLodgementOfClaim = new HashMap();
        wcOsAsOnLodgementOfSecondClaim = new HashMap();
        asOnDtOfSanctionDtl = new HashMap();
        asOnDtOfNPA = new HashMap();
        asOnLodgemntOfCredit = new HashMap();
        asOnDateOfLodgemntOfSecondClm = new HashMap();
        claimSummaryDetails = new HashMap();
        danSummaryReportDetails = new HashMap();
        amntRealizedThruDisposalOfSecurity = new HashMap();
        proposedAmntPaidByBorrower = new HashMap();
        proposedAmntSacrificed = new HashMap();
        osAmntOnDateForOTS = new HashMap();
        decision = new HashMap();
        remarks = new HashMap();
        approvedClaimAmount = new HashMap();
        asfToGenerate = new HashMap();
        settlementAmounts = new HashMap();
        finalSettlementFlags = new HashMap();
        pendingAmntsFromMLI = new HashMap();
        penaltyFees = new HashMap();
        settlementDates = new HashMap();
        paymentVoucherIds = new HashMap();
        settlementAdviceFlags = new HashMap();
        forwardedToIds = new HashMap();
        banksList = new ArrayList();
        historyReport = new ArrayList();
        approveClaims = new TreeMap();
        claimFlagsTc = new HashMap();
        claimFlagsWc = new HashMap();
        dealingOfficerName = null;
        cgpans = new HashMap();
        statementReportFiles = new HashMap();
        additionalAttachFiles = new HashMap();
        appraisalReportFiles = new HashMap();
        sanctionLetterFiles = new HashMap();
        complianceReportFiles = new HashMap();
        preInspectionReportFiles = new HashMap();
        insuranceCopyFiles = new HashMap();
        repayBeforeNpaAmts = new HashMap();
        recoveryAfterNpaAmts = new HashMap();
        interestRates = new HashMap();
        claimDandU = new ArrayList();
        claimLodgedReport = new ArrayList();
    }

    public String getDateOfTheDocument36()
    {
        return dateOfTheDocument36;
    }

    public String getDateOfTheDocument37()
    {
        return dateOfTheDocument37;
    }

    public void setDateOfTheDocument36(String date)
    {
        dateOfTheDocument36 = date;
    }

    public void setDateOfTheDocument37(String date)
    {
        dateOfTheDocument37 = date;
    }

    public String getMicroCategory()
    {
        return microCategory;
    }

    public void setMicroCategory(String microCategory)
    {
        this.microCategory = microCategory;
    }

    public Map getApproveClaims()
    {
        return approveClaims;
    }

    public void setApproveClaims(Map map)
    {
        approveClaims = map;
    }

    public String getUnitName()
    {
        return unitName;
    }

    public void setUnitName(String unitName)
    {
        this.unitName = unitName;
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

    public double getTotalOsAmntAsOnNPA()
    {
        return totalOsAmntAsOnNPA;
    }

    public void setTotalOsAmntAsOnNPA(double totalOsAmntAsOnNPA)
    {
        this.totalOsAmntAsOnNPA = totalOsAmntAsOnNPA;
    }

    public double getTcNetOutstanding()
    {
        return tcNetOutstanding;
    }

    public double getWcNetOutstanding()
    {
        return wcNetOutstanding;
    }

    public double getTotalNetOutstanding()
    {
        return totalNetOutstanding;
    }

    public double getTotalFirstInstalment()
    {
        return totalFirstInstalment;
    }

    public void setTotalFirstInstalment(double totalFirstInstalment)
    {
        this.totalFirstInstalment = totalFirstInstalment;
    }

    public double getTotalClaimEligibleAmt()
    {
        return totalClaimEligibleAmt;
    }

    public void setTotalClaimEligibleAmt(double totalClaimEligibleAmt)
    {
        this.totalClaimEligibleAmt = totalClaimEligibleAmt;
    }

    public void setTcNetOutstanding(double tcNetOutstanding)
    {
        this.tcNetOutstanding = tcNetOutstanding;
    }

    public void setWcNetOutstanding(double wcNetOutstanding)
    {
        this.wcNetOutstanding = wcNetOutstanding;
    }

    public void setTotalNetOutstanding(double totalNetOutstanding)
    {
        this.totalNetOutstanding = totalNetOutstanding;
    }

    public double getTcIssued()
    {
        return tcIssued;
    }

    public void setTcIssued(double tcIssued)
    {
        this.tcIssued = tcIssued;
    }

    public double getWcIssued()
    {
        return wcIssued;
    }

    public void setWcIssued(double wcIssued)
    {
        this.wcIssued = wcIssued;
    }

    public double getTotalRecovery()
    {
        return totalRecovery;
    }

    public void setTotalRecovery(double totalRecovery)
    {
        this.totalRecovery = totalRecovery;
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

    public double getTotalLaibilityforTC()
    {
        return totalLaibilityforTC;
    }

    public void setTotalLaibilityforTC(double totalLaibilityforTC)
    {
        this.totalLaibilityforTC = totalLaibilityforTC;
    }

    public double getTotalLiablityforWC()
    {
        return totalLiablityforWC;
    }

    public void setTotalLiablityforWC(double totalLiablityforWC)
    {
        this.totalLiablityforWC = totalLiablityforWC;
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

    public String getUserRemarks()
    {
        return userRemarks;
    }

    public void setUserRemarks(String userRemarks)
    {
        this.userRemarks = userRemarks;
    }

    public Date getDateofReceipt()
    {
        return dateofReceipt;
    }

    public void setDateofReceipt(Date dateofReceipt)
    {
        this.dateofReceipt = dateofReceipt;
    }

    public ArrayList getDanSummary()
    {
        return danSummary;
    }

    public void setDanSummary(ArrayList danSummary)
    {
        this.danSummary = danSummary;
    }

    public String getClmRefNumber()
    {
        return clmRefNumber;
    }

    public void setClmRefNumber(String clmRefNumber)
    {
        this.clmRefNumber = clmRefNumber;
    }

    public String getBankId()
    {
        return bankId;
    }

    public void setBankId(String id)
    {
        bankId = id;
    }

    public void setMemberId(String memId)
    {
        memberId = memId;
    }

    public String getMemberId()
    {
        return memberId;
    }

    public void setBorrowerID(String bid)
    {
        borrowerID = bid;
    }

    public String getBorrowerID()
    {
        return borrowerID;
    }

    public Date getDateOnWhichAccountClassifiedNPA()
    {
        return dateOnWhichAccountClassifiedNPA;
    }

    public void setDateOnWhichAccountClassifiedNPA(Date aDateOnWhichAccountClassifiedNPA)
    {
        dateOnWhichAccountClassifiedNPA = aDateOnWhichAccountClassifiedNPA;
    }

    public Map getTcprincipal()
    {
        return tcprincipal;
    }

    public void setTcprincipal(Map tcPrincipal)
    {
        tcprincipal = tcPrincipal;
    }

    public Map getTcInterestCharge()
    {
        return tcInterestCharge;
    }

    public void setTcInterestCharge(Map tcInterestCharge)
    {
        this.tcInterestCharge = tcInterestCharge;
    }

    public Map getTcOsAsOnDateOfNPA()
    {
        return tcOsAsOnDateOfNPA;
    }

    public void setTcOsAsOnDateOfNPA(Map tcOsAsOnDateOfNPA)
    {
        this.tcOsAsOnDateOfNPA = tcOsAsOnDateOfNPA;
    }

    public Map getTcOsAsStatedInCivilSuit()
    {
        return tcOsAsStatedInCivilSuit;
    }

    public void setTcOsAsStatedInCivilSuit(Map tcOsAsStatedInCivilSuit)
    {
        this.tcOsAsStatedInCivilSuit = tcOsAsStatedInCivilSuit;
    }

    public Map getTcOsAsOnLodgementOfClaim()
    {
        return tcOsAsOnLodgementOfClaim;
    }

    public void setTcOsAsOnLodgementOfClaim(Map tcOsAsOnLodgementOfClaim)
    {
        this.tcOsAsOnLodgementOfClaim = tcOsAsOnLodgementOfClaim;
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

    public Map getCgpandetails()
    {
        return cgpandetails;
    }

    public void setCgpandetails(Map cgpandetails)
    {
        this.cgpandetails = cgpandetails;
    }

    public Object getCgpandetails(String key)
    {
        return cgpandetails.get(key);
    }

    public void setCgpandetails(String key, Object value)
    {
        cgpandetails.put(key, value);
    }

    public Map getLastDisbursementDate()
    {
        return lastDisbursementDate;
    }

    public void setLastDisbursementDate(Map disbursementDate)
    {
        lastDisbursementDate = disbursementDate;
    }

    public Object getLastDisbursementDate(String key)
    {
        return lastDisbursementDate.get(key);
    }

    public void setLastDisbursementDate(String key, Object value)
    {
        lastDisbursementDate.put(key, value);
    }

    public Object getTcprincipal(String key)
    {
        return tcprincipal.get(key);
    }

    public void setTcprincipal(String key, Object value)
    {
        tcprincipal.put(key, value);
    }

    public Object getTcInterestCharge(String key)
    {
        return tcInterestCharge.get(key);
    }

    public void setTcInterestCharge(String key, Object value)
    {
        tcInterestCharge.put(key, value);
    }

    public Object getTcOsAsOnDateOfNPA(String key)
    {
        return tcOsAsOnDateOfNPA.get(key);
    }

    public void setTcOsAsOnDateOfNPA(String key, Object value)
    {
        tcOsAsOnDateOfNPA.put(key, value);
    }

    public Object getTcOsAsStatedInCivilSuit(String key)
    {
        return tcOsAsStatedInCivilSuit.get(key);
    }

    public void setTcOsAsStatedInCivilSuit(String key, Object value)
    {
        tcOsAsStatedInCivilSuit.put(key, value);
    }

    public Object getTcOsAsOnLodgementOfClaim(String key)
    {
        return tcOsAsOnLodgementOfClaim.get(key);
    }

    public void setTcOsAsOnLodgementOfClaim(String key, Object value)
    {
        tcOsAsOnLodgementOfClaim.put(key, value);
    }

    public Vector getCgpansVector()
    {
        return cgpansVector;
    }

    public void setCgpansVector(Vector cgpans)
    {
        cgpansVector = cgpans;
    }

    public Vector getTcCgpansVector()
    {
        return tcCgpansVector;
    }

    public void setTcCgpansVector(Vector tcVector)
    {
        tcCgpansVector = tcVector;
    }

    public Vector getWcCgpansVector()
    {
        return wcCgpansVector;
    }

    public void setWcCgpansVector(Vector wcVector)
    {
        System.out.println((new StringBuilder("setWcCgpansVector executed")).append(wcVector).toString());
        wcCgpansVector = wcVector;
    }

    public MemberInfo getMemberDetails()
    {
        return memberDetails;
    }

    public void setMemberDetails(MemberInfo memberDetails)
    {
        this.memberDetails = memberDetails;
    }

    public BorrowerInfo getBorrowerDetails()
    {
        return borrowerDetails;
    }

    public void setBorrowerDetails(BorrowerInfo borrowerDetails)
    {
        this.borrowerDetails = borrowerDetails;
    }

    public HashMap getNpaDetails()
    {
        return npaDetails;
    }

    public void setNpaDetails(HashMap npaDetails)
    {
        this.npaDetails = npaDetails;
    }

    public Map getWcOsAsOnDateOfNPA()
    {
        return wcOsAsOnDateOfNPA;
    }

    public void setWcOsAsOnDateOfNPA(Map wcOsAsOnDateOfNPA)
    {
        this.wcOsAsOnDateOfNPA = wcOsAsOnDateOfNPA;
    }

    public Object getWcOsAsOnDateOfNPA(String key)
    {
        return wcOsAsOnDateOfNPA.get(key);
    }

    public void setWcOsAsOnDateOfNPA(String key, Object value)
    {
        wcOsAsOnDateOfNPA.put(key, value);
    }

    public Map getWcOsAsStatedInCivilSuit()
    {
        return wcOsAsStatedInCivilSuit;
    }

    public void setWcOsAsStatedInCivilSuit(Map wcOsAsStatedInCivilSuit)
    {
        this.wcOsAsStatedInCivilSuit = wcOsAsStatedInCivilSuit;
    }

    public Object getWcOsAsStatedInCivilSuit(String key)
    {
        return wcOsAsStatedInCivilSuit.get(key);
    }

    public void setWcOsAsStatedInCivilSuit(String key, Object value)
    {
        wcOsAsStatedInCivilSuit.put(key, value);
    }

    public Map getWcOsAsOnLodgementOfClaim()
    {
        return wcOsAsOnLodgementOfClaim;
    }

    public void setWcOsAsOnLodgementOfClaim(Map wcOsAsOnLodgementOfClaim)
    {
        this.wcOsAsOnLodgementOfClaim = wcOsAsOnLodgementOfClaim;
    }

    public Object getWcOsAsOnLodgementOfClaim(String key)
    {
        return wcOsAsOnLodgementOfClaim.get(key);
    }

    public void setWcOsAsOnLodgementOfClaim(String key, Object value)
    {
        wcOsAsOnLodgementOfClaim.put(key, value);
    }

    public Map getWcCgpan()
    {
        return wcCgpan;
    }

    public void setWcCgpan(Map wcCgpan)
    {
        this.wcCgpan = wcCgpan;
    }

    public Object getWcCgpan(String key)
    {
        return wcCgpan.get(key);
    }

    public void setWcCgpan(String key, Object value)
    {
        wcCgpan.put(key, value);
    }

    public Map getAsOnDtOfSanctionDtl()
    {
        return asOnDtOfSanctionDtl;
    }

    public void setAsOnDtOfSanctionDtl(Map asOnDtOfSanctionDtl)
    {
        this.asOnDtOfSanctionDtl = asOnDtOfSanctionDtl;
    }

    public Object getAsOnDtOfSanctionDtl(String key)
    {
        return asOnDtOfSanctionDtl.get(key);
    }

    public void setAsOnDtOfSanctionDtl(String key, Object value)
    {
        asOnDtOfSanctionDtl.put(key, value);
    }

    public Map getAsOnDtOfNPA()
    {
        return asOnDtOfNPA;
    }

    public void setAsOnDtOfNPA(Map asOnDtOfNPA)
    {
        this.asOnDtOfNPA = asOnDtOfNPA;
    }

    public Object getAsOnDtOfNPA(String key)
    {
        return asOnDtOfNPA.get(key);
    }

    public void setAsOnDtOfNPA(String key, Object value)
    {
        asOnDtOfNPA.put(key, value);
    }

    public Map getAsOnLodgemntOfCredit()
    {
        return asOnLodgemntOfCredit;
    }

    public void setAsOnLodgemntOfCredit(Map asOnLodgemntOfCredit)
    {
        this.asOnLodgemntOfCredit = asOnLodgemntOfCredit;
    }

    public Object getAsOnLodgemntOfCredit(String key)
    {
        return asOnLodgemntOfCredit.get(key);
    }

    public void setAsOnLodgemntOfCredit(String key, Object value)
    {
        asOnLodgemntOfCredit.put(key, value);
    }

    public Vector getRecoveryModes()
    {
        return recoveryModes;
    }

    public void setRecoveryModes(Vector recModes)
    {
        recoveryModes = recModes;
    }

    public Vector getCgpnDetails()
    {
        return cgpnDetails;
    }

    public void setCgpnDetails(Vector dtls)
    {
        cgpnDetails = dtls;
    }

    public Map getClaimSummaryDetails()
    {
        return claimSummaryDetails;
    }

    public void setClaimSummaryDetails(Map summaryDtls)
    {
        claimSummaryDetails = summaryDtls;
    }

    public Object getClaimSummaryDetails(String key)
    {
        return claimSummaryDetails.get(key);
    }

    public void setClaimSummaryDetails(String key, Object value)
    {
        claimSummaryDetails.put(key, value);
    }

    public String getNameOfOfficial()
    {
        return nameOfOfficial;
    }

    public void setNameOfOfficial(String name)
    {
        nameOfOfficial = name;
    }

    public String getClaimSubmittedDate()
    {
        return claimSubmittedDate;
    }

    public void setClaimSubmittedDate(String date)
    {
        claimSubmittedDate = date;
    }

    public String getDesignationOfOfficial()
    {
        return designationOfOfficial;
    }

    public void setDesignationOfOfficial(String designation)
    {
        designationOfOfficial = designation;
    }

    public String getPlace()
    {
        return place;
    }

    public void setPlace(String place)
    {
        this.place = place;
    }

    public String getDateOfSeekingOTS()
    {
        return dateOfSeekingOTS;
    }

    public void setDateOfSeekingOTS(String date)
    {
        dateOfSeekingOTS = date;
    }

    public Vector getOtsRequestDtls()
    {
        return otsRequestDtls;
    }

    public void setOtsRequestDtls(Vector details)
    {
        otsRequestDtls = details;
    }

    public String getReasonForOTS()
    {
        return reasonForOTS;
    }

    public void setReasonForOTS(String reason)
    {
        reasonForOTS = reason;
    }

    public String getWilfullDefaulter()
    {
        return wilfullDefaulter;
    }

    public void setWilfullDefaulter(String willfulDefaulter)
    {
        wilfullDefaulter = willfulDefaulter;
    }

    public Map getProposedAmntPaidByBorrower()
    {
        return proposedAmntPaidByBorrower;
    }

    public void setProposedAmntPaidByBorrower(Map amnt)
    {
        proposedAmntPaidByBorrower = amnt;
    }

    public Object getProposedAmntPaidByBorrower(String key)
    {
        return proposedAmntPaidByBorrower.get(key);
    }

    public void setProposedAmntPaidByBorrower(String key, Object value)
    {
        proposedAmntPaidByBorrower.put(key, value);
    }

    public Map getProposedAmntSacrificed()
    {
        return proposedAmntSacrificed;
    }

    public void setProposedAmntSacrificed(Map amnt)
    {
        proposedAmntSacrificed = amnt;
    }

    public Object getProposedAmntSacrificed(String key)
    {
        return proposedAmntSacrificed.get(key);
    }

    public void setProposedAmntSacrificed(String key, Object value)
    {
        proposedAmntSacrificed.put(key, value);
    }

    public Map getOsAmntOnDateForOTS()
    {
        return osAmntOnDateForOTS;
    }

    public void setOsAmntOnDateForOTS(Map amnt)
    {
        osAmntOnDateForOTS = amnt;
    }

    public Object getOsAmntOnDateForOTS(String key)
    {
        return osAmntOnDateForOTS.get(key);
    }

    public void setOsAmntOnDateForOTS(String key, Object value)
    {
        osAmntOnDateForOTS.put(key, value);
    }

    public Vector getOtsprocessdetails()
    {
        return otsprocessdetails;
    }

    public void setOtsprocessdetails(Vector dtls)
    {
        otsprocessdetails = dtls;
    }

    public Map getDecision()
    {
        return decision;
    }

    public void setDecision(Map decision)
    {
        this.decision = decision;
    }

    public Object getDecision(String key)
    {
        return decision.get(key);
    }

    public void setDecision(String key, Object value)
    {
        decision.put(key, value);
    }

    public Map getRemarks()
    {
        return remarks;
    }

    public void setRemarks(Map remarks)
    {
        this.remarks = remarks;
    }

    public Object getRemarks(String key)
    {
        return remarks.get(key);
    }

    public void setRemarks(String key, Object value)
    {
        remarks.put(key, value);
    }

    public String getDateOfRecallNotice()
    {
        return dateOfRecallNotice;
    }

    public void setDateOfRecallNotice(String date)
    {
        dateOfRecallNotice = date;
    }

    public Map getApprovedClaimAmount()
    {
        return approvedClaimAmount;
    }

    public void setApprovedClaimAmount(Map claimAmnt)
    {
        approvedClaimAmount = claimAmnt;
    }

    public Object getApprovedClaimAmount(String key)
    {
        return approvedClaimAmount.get(key);
    }

    public void setApprovedClaimAmount(String key, Object value)
    {
        approvedClaimAmount.put(key, value);
    }

    public Map getAsfToGenerate()
    {
        return asfToGenerate;
    }

    public void setAsfToGenerate(Map asfToGenerate)
    {
        this.asfToGenerate = asfToGenerate;
    }

    public Object getAsfToGenerate(String key)
    {
        return asfToGenerate.get(key);
    }

    public void setAsfToGenerate(String key, Object value)
    {
        asfToGenerate.put(key, value);
    }

    public Vector getFirstInstallmentClaims()
    {
        return firstInstallmentClaims;
    }

    public void setFirstInstallmentClaims(Vector dtls)
    {
        firstInstallmentClaims = dtls;
    }

    public ClaimDetail getClaimdetail()
    {
        String totalAmtPayNow = getTotalAmntPayableNow();
        if(totalAmtPayNow != null)
            claimdetail.setTotalAmtPayNow(totalAmtPayNow);
        return claimdetail;
    }

    public void setClaimdetail(ClaimDetail aClaimDetail)
    {
        claimdetail = aClaimDetail;
    }

    public Vector getSettlementdetail()
    {
        return settlementdetail;
    }

    public void setSettlementdetail(Vector dtl)
    {
        settlementdetail = dtl;
    }

    public String getForumthrulegalinitiated()
    {
        return forumthrulegalinitiated;
    }

    public void setForumthrulegalinitiated(String aString)
    {
        forumthrulegalinitiated = aString;
    }

    public String getCaseregnumber()
    {
        return caseregnumber;
    }

    public void setCaseregnumber(String aString)
    {
        caseregnumber = aString;
    }

    public String getLegaldate()
    {
        return legaldate;
    }

    public void setLegaldate(String aDate)
    {
        legaldate = aDate;
    }

    public String getNpaDateNew()
    {
        return npaDateNew;
    }

    public void setNpaDateNew(String aDate)
    {
        npaDateNew = aDate;
    }

    public String getNameofforum()
    {
        return nameofforum;
    }

    public void setNameofforum(String aName)
    {
        nameofforum = aName;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String aString)
    {
        location = aString;
    }

    public String getAmountclaimed()
    {
        return amountclaimed;
    }

    public void setAmountclaimed(String amntclaimed)
    {
        amountclaimed = amntclaimed;
    }

    public String getCurrentstatusremarks()
    {
        return currentstatusremarks;
    }

    public void setCurrentstatusremarks(String aString)
    {
        currentstatusremarks = aString;
    }

    public String getWhtherDisbursemntProcHasConcluded()
    {
        return whtherDisbursemntProcHasConcluded;
    }

    public void setWhtherDisbursemntProcHasConcluded(String flag)
    {
        whtherDisbursemntProcHasConcluded = flag;
    }

    public String getDateOfReleaseOfWC()
    {
        return dateOfReleaseOfWC;
    }

    public void setDateOfReleaseOfWC(String aDate)
    {
        dateOfReleaseOfWC = aDate;
    }

    public ClaimApplication getClaimapplication()
    {
        return claimapplication;
    }

    public void setClaimapplication(ClaimApplication aClaimApplication)
    {
        claimapplication = aClaimApplication;
    }

    public String getCgpan()
    {
        return cgpan;
    }

    public void setCgpan(String pan)
    {
        cgpan = pan;
    }

    public String getOtherforums()
    {
        return otherforums;
    }

    public void setOtherforums(String forum)
    {
        otherforums = forum;
    }

    public FormFile getRecallnoticefilepath()
    {
        return recallnoticefilepath;
    }

    public void setRecallnoticefilepath(FormFile path)
    {
        recallnoticefilepath = path;
    }

    public String getIsClaimProceedings()
    {
        return isClaimProceedings;
    }

    public void setIsClaimProceedings(String isClaimProceedings)
    {
        this.isClaimProceedings = isClaimProceedings;
    }

    public String getRecoveryFlag()
    {
        return recoveryFlag;
    }

    public void setRecoveryFlag(String flag)
    {
        recoveryFlag = flag;
    }

    public String getMicroFlag()
    {
        return microFlag;
    }

    public void setMicroFlag(String microFlag)
    {
        this.microFlag = microFlag;
    }

    public String getOtsFlag()
    {
        return otsFlag;
    }

    public void setOtsFlag(String flag)
    {
        otsFlag = flag;
    }

    public String getServicefee()
    {
        return servicefee;
    }

    public void setServicefee(String fee)
    {
        servicefee = fee;
    }

    public String getDtofservicefee()
    {
        return dtofservicefee;
    }

    public void setDtofservicefee(String dt)
    {
        dtofservicefee = dt;
    }

    public String getWcOtherChargesAsOnNPA()
    {
        return wcOtherChargesAsOnNPA;
    }

    public void setWcOtherChargesAsOnNPA(String otherCharges)
    {
        wcOtherChargesAsOnNPA = otherCharges;
    }

    public String getTotalAmntAsOnNPA()
    {
        return totalAmntAsOnNPA;
    }

    public void setTotalAmntAsOnNPA(String amnt)
    {
        totalAmntAsOnNPA = amnt;
    }

    public String getWcintrstchrgsrecafternpa()
    {
        return wcintrstchrgsrecafternpa;
    }

    public void setWcintrstchrgsrecafternpa(String charges)
    {
        wcintrstchrgsrecafternpa = charges;
    }

    public String getTotalrecoveriesafternpa()
    {
        return totalrecoveriesafternpa;
    }

    public void setTotalrecoveriesafternpa(String chrgs)
    {
        totalrecoveriesafternpa = chrgs;
    }

    public String getTotalAmntPayableNow()
    {
        return totalAmntPayableNow;
    }

    public void setTotalAmntPayableNow(String amnt)
    {
        totalAmntPayableNow = amnt;
    }

    public String getPaymentAmnt()
    {
        return paymentAmnt;
    }

    public void setPaymentAmnt(String amnt)
    {
        paymentAmnt = amnt;
    }

    public String getPaymentVoucherId()
    {
        return paymentVoucherId;
    }

    public void setPaymentVoucherId(String id)
    {
        paymentVoucherId = id;
    }

    public String getProceedingsConcluded()
    {
        return proceedingsConcluded;
    }

    public void setProceedingsConcluded(String flag)
    {
        proceedingsConcluded = flag;
    }

    public Vector getSecondInstallmentClaims()
    {
        return secondInstallmentClaims;
    }

    public void setSecondInstallmentClaims(Vector claimsvector)
    {
        secondInstallmentClaims = claimsvector;
    }

    public Vector getSettlementsOfFirstClaim()
    {
        return settlementsOfFirstClaim;
    }

    public void setSettlementsOfFirstClaim(Vector aVector)
    {
        settlementsOfFirstClaim = aVector;
    }

    public Vector getSettlementsOfSecondClaim()
    {
        return settlementsOfSecondClaim;
    }

    public void setSettlementsOfSecondClaim(Vector aVector)
    {
        settlementsOfSecondClaim = aVector;
    }

    public String getMemberIdFlag()
    {
        return memberIdFlag;
    }

    public void setMemberIdFlag(String flag)
    {
        memberIdFlag = flag;
    }

    public FormFile getLegalAttachmentPath()
    {
        return legalAttachmentPath;
    }

    public void setLegalAttachmentPath(FormFile file)
    {
        legalAttachmentPath = file;
    }

    public Map getFinalSettlementFlags()
    {
        return finalSettlementFlags;
    }

    public void setFinalSettlementFlags(Map flags)
    {
        finalSettlementFlags = flags;
    }

    public Object getFinalSettlementFlags(String key)
    {
        return finalSettlementFlags.get(key);
    }

    public void setFinalSettlementFlags(String key, Object value)
    {
        finalSettlementFlags.put(key, value);
    }

    public Map getSettlementAmounts()
    {
        return settlementAmounts;
    }

    public void setSettlementAmounts(Map amnts)
    {
        settlementAmounts = amnts;
    }

    public Object getSettlementAmounts(String key)
    {
        return settlementAmounts.get(key);
    }

    public void setSettlementAmounts(String key, Object value)
    {
        settlementAmounts.put(key, value);
    }

    public Map getPenaltyFees()
    {
        return penaltyFees;
    }

    public void setPenaltyFees(Map amnts)
    {
        penaltyFees = amnts;
    }

    public Object getPenaltyFees(String key)
    {
        return penaltyFees.get(key);
    }

    public void setPenaltyFees(String key, Object value)
    {
        penaltyFees.put(key, value);
    }

    public Map getPaymentVoucherIds()
    {
        return paymentVoucherIds;
    }

    public void setPaymentVoucherIds(Map Ids)
    {
        paymentVoucherIds = Ids;
    }

    public Object getPaymentVoucherIds(String key)
    {
        return paymentVoucherIds.get(key);
    }

    public void setPaymentVoucherIds(String key, Object value)
    {
        paymentVoucherIds.put(key, value);
    }

    public Map getSettlementDates()
    {
        return settlementDates;
    }

    public void setSettlementDates(Map dates)
    {
        settlementDates = dates;
    }

    public Object getSettlementDates(String key)
    {
        return settlementDates.get(key);
    }

    public void setSettlementDates(String key, Object value)
    {
        settlementDates.put(key, value);
    }

    public Vector getSettlmntAdviceDtlsFirstSttlmnt()
    {
        return settlmntAdviceDtlsFirstSttlmnt;
    }

    public void setSettlmntAdviceDtlsFirstSttlmnt(Vector dtls)
    {
        settlmntAdviceDtlsFirstSttlmnt = dtls;
    }

    public Vector getSettlmntAdviceDtlsSecondSttlmnt()
    {
        return settlmntAdviceDtlsSecondSttlmnt;
    }

    public void setSettlmntAdviceDtlsSecondSttlmnt(Vector dtls)
    {
        settlmntAdviceDtlsSecondSttlmnt = dtls;
    }

    public Map getSettlementAdviceFlags()
    {
        return settlementAdviceFlags;
    }

    public void setSettlementAdviceFlags(Map flags)
    {
        settlementAdviceFlags = flags;
    }

    public Object getSettlementAdviceFlags(String key)
    {
        return settlementAdviceFlags.get(key);
    }

    public void setSettlementAdviceFlags(String key, Object value)
    {
        settlementAdviceFlags.put(key, value);
    }

    public Vector getCheckedFirstSettlmntAdviceDtls()
    {
        return checkedFirstSettlmntAdviceDtls;
    }

    public void setCheckedFirstSettlmntAdviceDtls(Vector dtls)
    {
        checkedFirstSettlmntAdviceDtls = dtls;
    }

    public Vector getCheckedSecondSettlmntAdviceDtls()
    {
        return checkedSecondSettlmntAdviceDtls;
    }

    public void setCheckedSecondSettlmntAdviceDtls(Vector dtls)
    {
        checkedSecondSettlmntAdviceDtls = dtls;
    }

    public Map getTcOsAsOnLodgementOfSecondClaim()
    {
        return tcOsAsOnLodgementOfSecondClaim;
    }

    public void setTcOsAsOnLodgementOfSecondClaim(Map dtls)
    {
        tcOsAsOnLodgementOfSecondClaim = dtls;
    }

    public Object getTcOsAsOnLodgementOfSecondClaim(String key)
    {
        return tcOsAsOnLodgementOfSecondClaim.get(key);
    }

    public void setTcOsAsOnLodgementOfSecondClaim(String key, Object value)
    {
        tcOsAsOnLodgementOfSecondClaim.put(key, value);
    }

    public Map getWcOsAsOnLodgementOfSecondClaim()
    {
        return wcOsAsOnLodgementOfSecondClaim;
    }

    public void setWcOsAsOnLodgementOfSecondClaim(Map dtls)
    {
        wcOsAsOnLodgementOfSecondClaim = dtls;
    }

    public Object getWcOsAsOnLodgementOfSecondClaim(String key)
    {
        return wcOsAsOnLodgementOfSecondClaim.get(key);
    }

    public void setWcOsAsOnLodgementOfSecondClaim(String key, Object value)
    {
        wcOsAsOnLodgementOfSecondClaim.put(key, value);
    }

    public Map getAsOnDateOfLodgemntOfSecondClm()
    {
        return asOnDateOfLodgemntOfSecondClm;
    }

    public void setAsOnDateOfLodgemntOfSecondClm(Map dtls)
    {
        asOnDateOfLodgemntOfSecondClm = dtls;
    }

    public Object getAsOnDateOfLodgemntOfSecondClm(String key)
    {
        return asOnDateOfLodgemntOfSecondClm.get(key);
    }

    public void setAsOnDateOfLodgemntOfSecondClm(String key, Object value)
    {
        asOnDateOfLodgemntOfSecondClm.put(key, value);
    }

    public String getDtOfConclusionOfRecoveryProc()
    {
        return dtOfConclusionOfRecoveryProc;
    }

    public void setDtOfConclusionOfRecoveryProc(String dt)
    {
        dtOfConclusionOfRecoveryProc = dt;
    }

    public String getWhetherAccntWasWrittenOffBooks()
    {
        return whetherAccntWasWrittenOffBooks;
    }

    public void setWhetherAccntWasWrittenOffBooks(String flag)
    {
        whetherAccntWasWrittenOffBooks = flag;
    }

    public String getDtOnWhichAccntWrittenOff()
    {
        return dtOnWhichAccntWrittenOff;
    }

    public void setDtOnWhichAccntWrittenOff(String date)
    {
        dtOnWhichAccntWrittenOff = date;
    }

    public HashMap getClaimSummaries()
    {
        return claimSummaries;
    }

    public void setClaimSummaries(HashMap summaries)
    {
        claimSummaries = summaries;
    }

    public Map getPendingAmntsFromMLI()
    {
        return pendingAmntsFromMLI;
    }

    public void setPendingAmntsFromMLI(Map amnts)
    {
        pendingAmntsFromMLI = amnts;
    }

    public Object getPendingAmntsFromMLI(String key)
    {
        return pendingAmntsFromMLI.get(key);
    }

    public void setPendingAmntsFromMLI(String key, Object value)
    {
        Log.log(2, "ClaimActionForm", "setPendingAmntsFromMLI", (new StringBuilder("Printing pendingAmntsFromMLI :")).append(pendingAmntsFromMLI).toString());
        pendingAmntsFromMLI.put(key, value);
        Log.log(2, "ClaimActionForm", "setPendingAmntsFromMLI", (new StringBuilder("Printing pendingAmntsFromMLI :")).append(pendingAmntsFromMLI).toString());
    }

    public HashMap getClmDtlForFirstInstllmnt()
    {
        return clmDtlForFirstInstllmnt;
    }

    public void setClmDtlForFirstInstllmnt(HashMap dtl)
    {
        clmDtlForFirstInstllmnt = dtl;
    }

    public Map getAmntRealizedThruDisposalOfSecurity()
    {
        return amntRealizedThruDisposalOfSecurity;
    }

    public void setAmntRealizedThruDisposalOfSecurity(Map amnts)
    {
        amntRealizedThruDisposalOfSecurity = amnts;
    }

    public Object getAmntRealizedThruDisposalOfSecurity(String key)
    {
        return amntRealizedThruDisposalOfSecurity.get(key);
    }

    public void setAmntRealizedThruDisposalOfSecurity(String key, Object value)
    {
        amntRealizedThruDisposalOfSecurity.put(key, value);
    }

    public String getAmntRealizedThruInvocationOfPerGuarantees()
    {
        return amntRealizedThruInvocationOfPerGuarantees;
    }

    public void setAmntRealizedThruInvocationOfPerGuarantees(String amnt)
    {
        amntRealizedThruInvocationOfPerGuarantees = amnt;
    }

    public Vector getOtsReferenceDetails()
    {
        return otsReferenceDetails;
    }

    public void setOtsReferenceDetails(Vector details)
    {
        otsReferenceDetails = details;
    }

    public void resetTheForm(ActionMapping arg0, ServletRequest arg1)
    {
        memberId = "";
        borrowerID = "";
        cgpan = "";
    }

    public void resetTheMemberId(ActionMapping arg0, ServletRequest arg1)
    {
        memberId = "";
    }

    public void resetTheMemberIdAndFlag(ActionMapping arg0, ServletRequest arg1)
    {
        memberId = "";
        memberIdFlag = "";
    }

    public void resetGenerateOptionFlag(ActionMapping arg0, ServletRequest arg1)
    {
        settlementAdviceFlags.clear();
    }

    public Date getToDate()
    {
        return toDate;
    }

    public void setToDate(Date aToDate)
    {
        toDate = aToDate;
    }

    public Date getFromDate()
    {
        return fromDate;
    }

    public void setFromDate(Date aFromDate)
    {
        fromDate = aFromDate;
    }

    public String getClmApplicationStatus()
    {
        return clmApplicationStatus;
    }

    public void setClmApplicationStatus(String aStatus)
    {
        clmApplicationStatus = aStatus;
    }

    public Vector getListOfClmRefNumbers()
    {
        return listOfClmRefNumbers;
    }

    public void setListOfClmRefNumbers(Vector aVector)
    {
        listOfClmRefNumbers = aVector;
    }

    public String getFirstSettlementIndexValue()
    {
        return firstSettlementIndexValue;
    }

    public void setFirstSettlementIndexValue(String index)
    {
        firstSettlementIndexValue = index;
    }

    public String getSecondSettlementIndexValue()
    {
        return secondSettlementIndexValue;
    }

    public void setSecondSettlementIndexValue(String value)
    {
        secondSettlementIndexValue = value;
    }

    public void resetTheFirstClaimApplication(ActionMapping arg0, ServletRequest arg1)
    {
        dateOfRecallNotice = "";
        if(lastDisbursementDate != null)
            lastDisbursementDate.clear();
        if(tcprincipal != null)
            tcprincipal.clear();
        if(tcInterestCharge != null)
            tcInterestCharge.clear();
        if(tcOsAsOnDateOfNPA != null)
            tcOsAsOnDateOfNPA.clear();
        if(tcOsAsStatedInCivilSuit != null)
            tcOsAsStatedInCivilSuit.clear();
        if(tcOsAsOnLodgementOfClaim != null)
            tcOsAsOnLodgementOfClaim.clear();
        if(wcOsAsOnDateOfNPA != null)
            wcOsAsOnDateOfNPA.clear();
        if(wcOsAsStatedInCivilSuit != null)
            wcOsAsStatedInCivilSuit.clear();
        if(wcOsAsOnLodgementOfClaim != null)
            wcOsAsOnLodgementOfClaim.clear();
        dateOfReleaseOfWC = "";
        if(asOnDtOfSanctionDtl != null)
            asOnDtOfSanctionDtl.clear();
        if(asOnDtOfNPA != null)
            asOnDtOfNPA.clear();
        if(asOnLodgemntOfCredit != null)
            asOnLodgemntOfCredit.clear();
        if(cgpandetails != null)
            cgpandetails.clear();
        dateOfSeekingOTS = "";
        if(claimSummaryDetails != null)
            claimSummaryDetails.clear();
        isAcctFraud = null;
        isEnquiryConcluded = null;
        isMLIInvolved = null;
        reasonForFilingSuit = null;
        assetPossessionDate = null;
        subsidyFlag = null;
        isSubsidyRcvdAfterNpa = null;
        isSubsidyAdjustedOnDues = null;
        mliCommentOnFinPosition = null;
        detailsOfFinAssistance = null;
        bankFacilityDetail = null;
        placeUnderWatchList = null;
        remarksOnNpa = null;
        reasonForIssueRecallNotice = null;
        inclusionOfReciept = null;
        confirmRecoveryValues = null;
        totSecAsOnSanc = 0.0D;
        totSecAsOnNpa = 0.0D;
        totSecAsOnClaim = 0.0D;
        dealingOfficerName = null;
        clmRefNumberNew = null;
        claimapplication = null;
        creditSupport = null;
        if(totalDisbursementAmt != null)
            totalDisbursementAmt.clear();
        if(claimFlagsTc != null)
            claimFlagsTc.clear();
        if(claimFlagsWc != null)
            claimFlagsWc.clear();
        microCategory = null;
        wilfullDefaulter = null;
    }

    public void resetTheSecondClaimApplication(ActionMapping arg0, ServletRequest arg1)
    {
        dateOfRecallNotice = "";
        if(lastDisbursementDate != null)
            lastDisbursementDate.clear();
        if(tcprincipal != null)
            tcprincipal.clear();
        if(tcInterestCharge != null)
            tcInterestCharge.clear();
        if(tcOsAsOnDateOfNPA != null)
            tcOsAsOnDateOfNPA.clear();
        if(tcOsAsStatedInCivilSuit != null)
            tcOsAsStatedInCivilSuit.clear();
        if(tcOsAsOnLodgementOfClaim != null)
            tcOsAsOnLodgementOfClaim.clear();
        if(wcOsAsOnDateOfNPA != null)
            wcOsAsOnDateOfNPA.clear();
        if(wcOsAsStatedInCivilSuit != null)
            wcOsAsStatedInCivilSuit.clear();
        if(wcOsAsOnLodgementOfClaim != null)
            wcOsAsOnLodgementOfClaim.clear();
        dateOfReleaseOfWC = "";
        if(asOnDtOfSanctionDtl != null)
            asOnDtOfSanctionDtl.clear();
        if(asOnDtOfNPA != null)
            asOnDtOfNPA.clear();
        if(asOnLodgemntOfCredit != null)
            asOnLodgemntOfCredit.clear();
        if(cgpandetails != null)
            cgpandetails.clear();
        dateOfSeekingOTS = "";
        if(claimSummaryDetails != null)
            claimSummaryDetails.clear();
        if(tcOsAsOnLodgementOfSecondClaim != null)
            tcOsAsOnLodgementOfSecondClaim.clear();
        if(wcOsAsOnLodgementOfSecondClaim != null)
            wcOsAsOnLodgementOfSecondClaim.clear();
        if(amntRealizedThruDisposalOfSecurity != null)
            amntRealizedThruDisposalOfSecurity.clear();
    }

    public void resetOTSRequestPage(ActionMapping arg0, ServletRequest arg1)
    {
        proposedAmntPaidByBorrower.clear();
        proposedAmntSacrificed.clear();
        osAmntOnDateForOTS.clear();
        reasonForOTS = "";
    }

    public void resetDisclaimerPage(ActionMapping arg0, ServletRequest arg1)
    {
        nameOfOfficial = "";
        designationOfOfficial = "";
        claimSubmittedDate = "";
        place = "";
        view = "";
    }

    public void resetOTSProcessPage(ActionMapping arg0, ServletRequest arg1)
    {
        decision.clear();
        remarks.clear();
    }

    public void resetClaimsProcessPage(ActionMapping arg0, ServletRequest arg1)
    {
        approvedClaimAmount.clear();
        decision.clear();
        remarks.clear();
    }

    public void resetSettlementProcessPage(ActionMapping arg0, ServletRequest arg1)
    {
        settlementAmounts.clear();
        settlementDates.clear();
        finalSettlementFlags.clear();
    }

    public String getTcInterestChargeForThisBorrower()
    {
        return tcInterestChargeForThisBorrower;
    }

    public void setTcInterestChargeForThisBorrower(String charges)
    {
        tcInterestChargeForThisBorrower = charges;
    }

    public String getTcPrinRecoveriesAfterNPA()
    {
        return tcPrinRecoveriesAfterNPA;
    }

    public void setTcPrinRecoveriesAfterNPA(String recoveriesAfterNPA)
    {
        tcPrinRecoveriesAfterNPA = recoveriesAfterNPA;
    }

    public String gettcInterestChargesRecovAfterNPA()
    {
        return tcInterestChargesRecovAfterNPA;
    }

    public void setTcInterestChargesRecovAfterNPA(String charges)
    {
        tcInterestChargesRecovAfterNPA = charges;
    }

    public String getWcPrincipalRecoveAfterNPA()
    {
        return wcPrincipalRecoveAfterNPA;
    }

    public void setWcPrincipalRecoveAfterNPA(String recoveries)
    {
        wcPrincipalRecoveAfterNPA = recoveries;
    }

    public String getWcothercgrgsRecAfterNPA()
    {
        return wcothercgrgsRecAfterNPA;
    }

    public void setWcothercgrgsRecAfterNPA(String charges)
    {
        wcothercgrgsRecAfterNPA = charges;
    }

    public Vector getUpdatedClaimDtls()
    {
        return updatedClaimDtls;
    }

    public void setUpdatedClaimDtls(Vector dtls)
    {
        updatedClaimDtls = dtls;
    }

    public Vector getSettledClms()
    {
        return settledClms;
    }

    public void setSettledClms(Vector dtls)
    {
        settledClms = dtls;
    }

    public int getFirstCounter()
    {
        return firstCounter;
    }

    public void setFirstCounter(int val)
    {
        firstCounter = val;
    }

    public int getSecondCounter()
    {
        return secondCounter;
    }

    public void setSecondCounter(int val)
    {
        secondCounter = val;
    }

    public int getApprvdVectorSize()
    {
        return apprvdVectorSize;
    }

    public void setApprvdVectorSize(int val)
    {
        apprvdVectorSize = val;
    }

    public int getRejectdVectorSize()
    {
        return rejectdVectorSize;
    }

    public void setRejectdVectorSize(int val)
    {
        rejectdVectorSize = val;
    }

    public int getHeldVectorSize()
    {
        return heldVectorSize;
    }

    public void setHeldVectorSize(int val)
    {
        heldVectorSize = val;
    }

    public int getForwardedVectorSize()
    {
        return forwardedVectorSize;
    }

    public void setForwardedVectorSize(int val)
    {
        forwardedVectorSize = val;
    }

    public int getTempclosedVectorSize()
    {
        return tempclosedVectorSize;
    }

    public void setTempclosedVectorSize(int tempclosedVectorSize)
    {
        this.tempclosedVectorSize = tempclosedVectorSize;
    }

    public HashMap getSecurityDetails()
    {
        return securityDetails;
    }

    public void setSecurityDetails(HashMap dtls)
    {
        securityDetails = dtls;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String user)
    {
        userId = user;
    }

    public ArrayList getInstrumentTypes()
    {
        return instrumentTypes;
    }

    public void setInstrumentTypes(ArrayList dtls)
    {
        instrumentTypes = dtls;
    }

    public String getModeOfPayment()
    {
        return modeOfPayment;
    }

    public void setModeOfPayment(String mode)
    {
        modeOfPayment = mode;
    }

    public CollectingBank getCollectingBank()
    {
        return collectingBank;
    }

    public void setCollectingBank(CollectingBank bank)
    {
        collectingBank = bank;
    }

    public Date getPaymentDate()
    {
        return paymentDate;
    }

    public void setPaymentDate(Date date)
    {
        paymentDate = date;
    }

    public String getInstrumentNo()
    {
        return instrumentNo;
    }

    public void setInstrumentNo(String num)
    {
        instrumentNo = num;
    }

    public String getInstrumentDate()
    {
        return instrumentDate;
    }

    public void setInstrumentDate(String date)
    {
        instrumentDate = date;
    }

    public String getSubsidyDate()
    {
        return subsidyDate;
    }

    public void setSubsidyDate(String date)
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

    public double getInstrumenAmount()
    {
        return instrumenAmount;
    }

    public void setInstrumenAmount(double amnt)
    {
        instrumenAmount = amnt;
    }

    public String getDrawnAtBank()
    {
        return drawnAtBank;
    }

    public void setDrawnAtBank(String drawnAt)
    {
        drawnAtBank = drawnAt;
    }

    public String getDrawnAtBranch()
    {
        return drawnAtBranch;
    }

    public void setDrawnAtBranch(String drawnAtBrnch)
    {
        drawnAtBranch = drawnAtBrnch;
    }

    public String getPayableAt()
    {
        return payableAt;
    }

    public void setPayableAt(String payAt)
    {
        payableAt = payAt;
    }

    public String getClmRefDtlSet()
    {
        return clmRefDtlSet;
    }

    public void setClmRefDtlSet(String flag)
    {
        clmRefDtlSet = flag;
    }

    public int getTcCounter()
    {
        return tcCounter;
    }

    public void setTcCounter(int value)
    {
        tcCounter = value;
    }

    public int getWcCounter()
    {
        return wcCounter;
    }

    public void setWcCounter(int value)
    {
        wcCounter = value;
    }

    public String getStatusFlag()
    {
        return statusFlag;
    }

    public void setstatusFlag(String flag)
    {
        statusFlag = flag;
    }

    public int getLimit()
    {
        return limit;
    }

    public void setLimit(int num)
    {
        limit = num;
    }

    public Vector getPerGaurDtls()
    {
        return perGaurDtls;
    }

    public void setPerGaurDtls(Vector dtls)
    {
        perGaurDtls = dtls;
    }

    public ArrayList getUserIds()
    {
        return userIds;
    }

    public void setUserIds(ArrayList list)
    {
        userIds = list;
    }

    public Map getForwardedToIds()
    {
        return forwardedToIds;
    }

    public void setForwardedToIds(Map Ids)
    {
        forwardedToIds = Ids;
    }

    public Object getForwardedToIds(String key)
    {
        return forwardedToIds.get(key);
    }

    public void setForwardedToIds(String key, Object value)
    {
        Log.log(4, "ClaimActionForm", "setForwardedToIds()", (new StringBuilder("key :")).append(key).append("value :").append(value).toString());
        forwardedToIds.put(key, value);
    }

    public String getItpanOfChiefPromoter()
    {
        return itpanOfChiefPromoter;
    }

    public void setItpanOfChiefPromoter(String pan)
    {
        itpanOfChiefPromoter = pan;
    }

    public HashMap getItpanDetails()
    {
        return itpanDetails;
    }

    public void setItpanDetails(HashMap obj)
    {
        itpanDetails = obj;
    }

    public String getTest()
    {
        return test;
    }

    public void setTest(String str)
    {
        test = str;
    }

    public ArrayList getBanksList()
    {
        return banksList;
    }

    public void setBanksList(ArrayList list)
    {
        banksList = list;
    }

    public String getBnkName()
    {
        return bnkName;
    }

    public void setBnkName(String string)
    {
        bnkName = string;
    }

    public void setHistoryReport(ArrayList historyReport)
    {
        this.historyReport = historyReport;
    }

    public ArrayList getHistoryReport()
    {
        return historyReport;
    }

    public void setAppReport(ApplicationReport appReport)
    {
        this.appReport = appReport;
    }

    public ApplicationReport getAppReport()
    {
        return appReport;
    }

    public String getRateCharge()
    {
        return rateCharge;
    }

    public void setRateCharge(String rateCharge)
    {
        this.rateCharge = rateCharge;
    }

    public String getThirdpartyGuarantee()
    {
        return thirdpartyGuarantee;
    }

    public void setThirdpartyGuarantee(String thirdpartyGuarantee)
    {
        this.thirdpartyGuarantee = thirdpartyGuarantee;
    }

    public String getDateofNPA()
    {
        return dateofNPA;
    }

    public void setDateofNPA(String dateofNPA)
    {
        this.dateofNPA = dateofNPA;
    }

    public String getOutstandingAmount()
    {
        return outstandingAmount;
    }

    public void setOutstandingAmount(String outstandingAmount)
    {
        this.outstandingAmount = outstandingAmount;
    }

    public String getSeriousDeficienies()
    {
        return seriousDeficienies;
    }

    public void setSeriousDeficienies(String seriousDeficienies)
    {
        this.seriousDeficienies = seriousDeficienies;
    }

    public String getMajorDeficienciesObserved()
    {
        return majorDeficienciesObserved;
    }

    public void setMajorDeficienciesObserved(String majorDeficienciesObserved)
    {
        this.majorDeficienciesObserved = majorDeficienciesObserved;
    }

    public String getDeficienciesObserved()
    {
        return deficienciesObserved;
    }

    public void setDeficienciesObserved(String deficienciesObserved)
    {
        this.deficienciesObserved = deficienciesObserved;
    }

    public String getInternalRating()
    {
        return internalRating;
    }

    public void setInternalRating(String internalRating)
    {
        this.internalRating = internalRating;
    }

    public String getAlltheRecoveries()
    {
        return alltheRecoveries;
    }

    public void setAlltheRecoveries(String alltheRecoveries)
    {
        this.alltheRecoveries = alltheRecoveries;
    }

    public String getIsActivityEligible()
    {
        return isActivityEligible;
    }

    public void setIsActivityEligible(String isActivityEligible)
    {
        this.isActivityEligible = isActivityEligible;
    }

    public String getWhetherCibil()
    {
        return whetherCibil;
    }

    public void setWhetherCibil(String whetherCibil)
    {
        this.whetherCibil = whetherCibil;
    }

    public String getDesigna()
    {
        return designa;
    }

    public void setDesigna(String designa)
    {
        this.designa = designa;
    }

    public String getNameOfOffi()
    {
        return nameOfOffi;
    }

    public void setNameOfOffi(String nameOfOffi)
    {
        this.nameOfOffi = nameOfOffi;
    }

    public String getPlaces()
    {
        return places;
    }

    public void setPlaces(String places)
    {
        this.places = places;
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

    public String getRateChargeflag()
    {
        return rateChargeflag;
    }

    public void setRateChargeflag(String rateChargeflag)
    {
        this.rateChargeflag = rateChargeflag;
    }

    public String getThirdpartyGuaranteeflag()
    {
        return thirdpartyGuaranteeflag;
    }

    public void setThirdpartyGuaranteeflag(String thirdpartyGuaranteeflag)
    {
        this.thirdpartyGuaranteeflag = thirdpartyGuaranteeflag;
    }

    public String getDateofNPAflag()
    {
        return dateofNPAflag;
    }

    public void setDateofNPAflag(String dateofNPAflag)
    {
        this.dateofNPAflag = dateofNPAflag;
    }

    public String getOutstandingAmountflag()
    {
        return outstandingAmountflag;
    }

    public void setOutstandingAmountflag(String outstandingAmountflag)
    {
        this.outstandingAmountflag = outstandingAmountflag;
    }

    public String getSeriousDeficieniesflag()
    {
        return seriousDeficieniesflag;
    }

    public void setSeriousDeficieniesflag(String seriousDeficieniesflag)
    {
        this.seriousDeficieniesflag = seriousDeficieniesflag;
    }

    public String getMajorDeficienciesObservedflag()
    {
        return majorDeficienciesObservedflag;
    }

    public void setMajorDeficienciesObservedflag(String majorDeficienciesObservedflag)
    {
        this.majorDeficienciesObservedflag = majorDeficienciesObservedflag;
    }

    public String getDeficienciesObservedflag()
    {
        return deficienciesObservedflag;
    }

    public void setDeficienciesObservedflag(String deficienciesObservedflag)
    {
        this.deficienciesObservedflag = deficienciesObservedflag;
    }

    public String getInternalRatingflag()
    {
        return internalRatingflag;
    }

    public void setInternalRatingflag(String internalRatingflag)
    {
        this.internalRatingflag = internalRatingflag;
    }

    public String getAlltheRecoveriesflag()
    {
        return alltheRecoveriesflag;
    }

    public void setAlltheRecoveriesflag(String alltheRecoveriesflag)
    {
        this.alltheRecoveriesflag = alltheRecoveriesflag;
    }

    public String getWhetherCibilflag()
    {
        return whetherCibilflag;
    }

    public void setWhetherCibilflag(String whetherCibilflag)
    {
        this.whetherCibilflag = whetherCibilflag;
    }

    public String getIsActivityEligibleflag()
    {
        return isActivityEligibleflag;
    }

    public void setIsActivityEligibleflag(String isActivityEligibleflag)
    {
        this.isActivityEligibleflag = isActivityEligibleflag;
    }

    public FormFile getStaffAcAttachmentPath()
    {
        return staffAcAttachmentPath;
    }

    public void setStaffAcAttachmentPath(FormFile staffAcAttachmentPath)
    {
        this.staffAcAttachmentPath = staffAcAttachmentPath;
    }

    public String getIsNorthEastRegion()
    {
        return isNorthEastRegion;
    }

    public void setIsNorthEastRegion(String isNorthEastRegion)
    {
        this.isNorthEastRegion = isNorthEastRegion;
    }

    public String getIsWomenorNot()
    {
        return isWomenorNot;
    }

    public void setIsWomenorNot(String isWomenorNot)
    {
        this.isWomenorNot = isWomenorNot;
    }

    public double getTotGuaranteeAmt()
    {
        return totGuaranteeAmt;
    }

    public void setTotGuaranteeAmt(double totGuaranteeAmt)
    {
        this.totGuaranteeAmt = totGuaranteeAmt;
    }

    public String getView()
    {
        return view;
    }

    public void setView(String view)
    {
        this.view = view;
    }

    public TreeMap getWhetherCibilYes()
    {
        return whetherCibilYes;
    }

    public void setWhetherCibilYes(TreeMap whetherCibilYes)
    {
        this.whetherCibilYes = whetherCibilYes;
    }

    public TreeMap getWhetherCibilNo()
    {
        return whetherCibilNo;
    }

    public void setWhetherCibilNo(TreeMap whetherCibilNo)
    {
        this.whetherCibilNo = whetherCibilNo;
    }

    public TreeMap getRateChargeYes()
    {
        return rateChargeYes;
    }

    public void setRateChargeYes(TreeMap rateChargeYes)
    {
        this.rateChargeYes = rateChargeYes;
    }

    public TreeMap getRateChargeNo()
    {
        return rateChargeNo;
    }

    public void setRateChargeNo(TreeMap rateChargeNo)
    {
        this.rateChargeNo = rateChargeNo;
    }

    public TreeMap getThirdpartyGuaranteeYes()
    {
        return thirdpartyGuaranteeYes;
    }

    public void setThirdpartyGuaranteeYes(TreeMap thirdpartyGuaranteeYes)
    {
        this.thirdpartyGuaranteeYes = thirdpartyGuaranteeYes;
    }

    public TreeMap getThirdpartyGuaranteeNo()
    {
        return thirdpartyGuaranteeNo;
    }

    public void setThirdpartyGuaranteeNo(TreeMap thirdpartyGuaranteeNo)
    {
        this.thirdpartyGuaranteeNo = thirdpartyGuaranteeNo;
    }

    public TreeMap getDateofNPAYes()
    {
        return dateofNPAYes;
    }

    public void setDateofNPAYes(TreeMap dateofNPAYes)
    {
        this.dateofNPAYes = dateofNPAYes;
    }

    public TreeMap getDateofNPANo()
    {
        return dateofNPANo;
    }

    public void setDateofNPANo(TreeMap dateofNPANo)
    {
        this.dateofNPANo = dateofNPANo;
    }

    public TreeMap getOutstandingAmountYes()
    {
        return outstandingAmountYes;
    }

    public void setOutstandingAmountYes(TreeMap outstandingAmountYes)
    {
        this.outstandingAmountYes = outstandingAmountYes;
    }

    public TreeMap getOutstandingAmountNo()
    {
        return outstandingAmountNo;
    }

    public void setOutstandingAmountNo(TreeMap outstandingAmountNo)
    {
        this.outstandingAmountNo = outstandingAmountNo;
    }

    public TreeMap getSeriousDeficieniesYes()
    {
        return seriousDeficieniesYes;
    }

    public void setSeriousDeficieniesYes(TreeMap seriousDeficieniesYes)
    {
        this.seriousDeficieniesYes = seriousDeficieniesYes;
    }

    public TreeMap getSeriousDeficieniesNo()
    {
        return seriousDeficieniesNo;
    }

    public void setSeriousDeficieniesNo(TreeMap seriousDeficieniesNo)
    {
        this.seriousDeficieniesNo = seriousDeficieniesNo;
    }

    public TreeMap getMajorDeficienciesObservedYes()
    {
        return majorDeficienciesObservedYes;
    }

    public void setMajorDeficienciesObservedYes(TreeMap majorDeficienciesObservedYes)
    {
        this.majorDeficienciesObservedYes = majorDeficienciesObservedYes;
    }

    public TreeMap getMajorDeficienciesObservedNo()
    {
        return majorDeficienciesObservedNo;
    }

    public void setMajorDeficienciesObservedNo(TreeMap majorDeficienciesObservedNo)
    {
        this.majorDeficienciesObservedNo = majorDeficienciesObservedNo;
    }

    public TreeMap getDeficienciesObservedYes()
    {
        return deficienciesObservedYes;
    }

    public void setDeficienciesObservedYes(TreeMap deficienciesObservedYes)
    {
        this.deficienciesObservedYes = deficienciesObservedYes;
    }

    public TreeMap getDeficienciesObservedNo()
    {
        return deficienciesObservedNo;
    }

    public void setDeficienciesObservedNo(TreeMap deficienciesObservedNo)
    {
        this.deficienciesObservedNo = deficienciesObservedNo;
    }

    public TreeMap getAlltheRecoveriesYes()
    {
        return alltheRecoveriesYes;
    }

    public void setAlltheRecoveriesYes(TreeMap alltheRecoveriesYes)
    {
        this.alltheRecoveriesYes = alltheRecoveriesYes;
    }

    public TreeMap getAlltheRecoveriesNo()
    {
        return alltheRecoveriesNo;
    }

    public void setAlltheRecoveriesNo(TreeMap alltheRecoveriesNo)
    {
        this.alltheRecoveriesNo = alltheRecoveriesNo;
    }

    public TreeMap getComments()
    {
        return comments;
    }

    public void setComments(TreeMap comments)
    {
        this.comments = comments;
    }

    public TreeMap getDuCertifyDecisionYes()
    {
        return duCertifyDecisionYes;
    }

    public void setDuCertifyDecisionYes(TreeMap duCertifyDecisionYes)
    {
        this.duCertifyDecisionYes = duCertifyDecisionYes;
    }

    public TreeMap getDuCertifyDecisionNo()
    {
        return duCertifyDecisionNo;
    }

    public void setDuCertifyDecisionNo(TreeMap duCertifyDecisionNo)
    {
        this.duCertifyDecisionNo = duCertifyDecisionNo;
    }

    public String getClaimsubmitDU()
    {
        return claimsubmitDU;
    }

    public void setClaimsubmitDU(String claimsubmitDU)
    {
        this.claimsubmitDU = claimsubmitDU;
    }

    public String getMemberids()
    {
        return memberids;
    }

    public void setMemberids(String memberids)
    {
        this.memberids = memberids;
    }

    public String getUnitNAME()
    {
        return unitNAME;
    }

    public void setUnitNAME(String unitNAME)
    {
        this.unitNAME = unitNAME;
    }

    public String getBankName()
    {
        return bankName;
    }

    public void setBankName(String bankName)
    {
        this.bankName = bankName;
    }

    public String getZone()
    {
        return zone;
    }

    public void setZone(String zone)
    {
        this.zone = zone;
    }

    public String getCgPAN()
    {
        return cgPAN;
    }

    public void setCgPAN(String cgPAN)
    {
        this.cgPAN = cgPAN;
    }

    public String getClaimRefNo()
    {
        return claimRefNo;
    }

    public void setClaimRefNo(String claimRefNo)
    {
        this.claimRefNo = claimRefNo;
    }

    public String getGuaranteedAmount()
    {
        return guaranteedAmount;
    }

    public void setGuaranteedAmount(String guaranteedAmount)
    {
        this.guaranteedAmount = guaranteedAmount;
    }

    public String getClaimSubmitCGTMSE()
    {
        return claimSubmitCGTMSE;
    }

    public void setClaimSubmitCGTMSE(String claimSubmitCGTMSE)
    {
        this.claimSubmitCGTMSE = claimSubmitCGTMSE;
    }

    public String getClaimReturnedForLodgement()
    {
        return claimReturnedForLodgement;
    }

    public void setClaimReturnedForLodgement(String claimReturnedForLodgement)
    {
        this.claimReturnedForLodgement = claimReturnedForLodgement;
    }

    public String getClaimReturnedRemarks()
    {
        return claimReturnedRemarks;
    }

    public void setClaimReturnedRemarks(String claimReturnedRemarks)
    {
        this.claimReturnedRemarks = claimReturnedRemarks;
    }

    public Date getDateclaim()
    {
        return dateclaim;
    }

    public void setDateclaim(Date dateclaim)
    {
        this.dateclaim = dateclaim;
    }

    public String getClmreferenceNumber()
    {
        return clmreferenceNumber;
    }

    public void setClmreferenceNumber(String clmreferenceNumber)
    {
        this.clmreferenceNumber = clmreferenceNumber;
    }

    public String getUnitname()
    {
        return unitname;
    }

    public void setUnitname(String unitname)
    {
        this.unitname = unitname;
    }

    public String getBranchname()
    {
        return branchname;
    }

    public void setBranchname(String branchname)
    {
        this.branchname = branchname;
    }

    public double getGuranteeApprovedamt()
    {
        return guranteeApprovedamt;
    }

    public void setGuranteeApprovedamt(double guranteeApprovedamt)
    {
        this.guranteeApprovedamt = guranteeApprovedamt;
    }

    public String getViewdu()
    {
        return viewdu;
    }

    public void setViewdu(String viewdu)
    {
        this.viewdu = viewdu;
    }

    public String getContentofdu()
    {
        return contentofdu;
    }

    public void setContentofdu(String contentofdu)
    {
        this.contentofdu = contentofdu;
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

    public void setCp_ots_enterCgpan(String cp_ots_enterCgpan)
    {
        this.cp_ots_enterCgpan = cp_ots_enterCgpan;
    }

    public String getCp_ots_enterCgpan()
    {
        return cp_ots_enterCgpan;
    }

    public void setCp_ots_totAmount(double cp_ots_totAmount)
    {
        this.cp_ots_totAmount = cp_ots_totAmount;
    }

    public double getCp_ots_totAmount()
    {
        return cp_ots_totAmount;
    }

    public void setCp_ots_liabtext(String cp_ots_liabtext)
    {
        this.cp_ots_liabtext = cp_ots_liabtext;
    }

    public String getCp_ots_liabtext()
    {
        return cp_ots_liabtext;
    }

    public void setCp_ots_seconInsatlText(String cp_ots_seconInsatlText)
    {
        this.cp_ots_seconInsatlText = cp_ots_seconInsatlText;
    }

    public String getCp_ots_seconInsatlText()
    {
        return cp_ots_seconInsatlText;
    }

    public void setCp_ots_cgpanGaurnteeAmt(double cp_ots_cgpanGaurnteeAmt)
    {
        this.cp_ots_cgpanGaurnteeAmt = cp_ots_cgpanGaurnteeAmt;
    }

    public double getCp_ots_cgpanGaurnteeAmt()
    {
        return cp_ots_cgpanGaurnteeAmt;
    }

    public void setCp_ots_npaAmount(double cp_ots_npaAmount)
    {
        this.cp_ots_npaAmount = cp_ots_npaAmount;
    }

    public double getCp_ots_npaAmount()
    {
        return cp_ots_npaAmount;
    }

    public void setCp_ots_recoveryAmt(double cp_ots_recoveryAmt)
    {
        this.cp_ots_recoveryAmt = cp_ots_recoveryAmt;
    }

    public double getCp_ots_recoveryAmt()
    {
        return cp_ots_recoveryAmt;
    }

    public void setCp_ots_netOutstanding(double cp_ots_netOutstanding)
    {
        this.cp_ots_netOutstanding = cp_ots_netOutstanding;
    }

    public double getCp_ots_netOutstanding()
    {
        return cp_ots_netOutstanding;
    }

    public void setCp_ots_liableamount(double cp_ots_liableamount)
    {
        this.cp_ots_liableamount = cp_ots_liableamount;
    }

    public double getCp_ots_liableamount()
    {
        return cp_ots_liableamount;
    }

    public void setCp_ots_firstIntalpaidAmount(double cp_ots_firstIntalpaidAmount)
    {
        this.cp_ots_firstIntalpaidAmount = cp_ots_firstIntalpaidAmount;
    }

    public double getCp_ots_firstIntalpaidAmount()
    {
        return cp_ots_firstIntalpaidAmount;
    }

    public void setCp_ots_totRecAftFirInst(double cp_ots_totRecAftFirInst)
    {
        this.cp_ots_totRecAftFirInst = cp_ots_totRecAftFirInst;
    }

    public double getCp_ots_totRecAftFirInst()
    {
        return cp_ots_totRecAftFirInst;
    }

    public void setCp_ots_totDedtctAmt(double cp_ots_totDedtctAmt)
    {
        this.cp_ots_totDedtctAmt = cp_ots_totDedtctAmt;
    }

    public double getCp_ots_totDedtctAmt()
    {
        return cp_ots_totDedtctAmt;
    }

    public void setCp_ots_totNotDedtctAmt(double cp_ots_totNotDedtctAmt)
    {
        this.cp_ots_totNotDedtctAmt = cp_ots_totNotDedtctAmt;
    }

    public double getCp_ots_totNotDedtctAmt()
    {
        return cp_ots_totNotDedtctAmt;
    }

    public void setCp_ots_netRecovAmt(double cp_ots_netRecovAmt)
    {
        this.cp_ots_netRecovAmt = cp_ots_netRecovAmt;
    }

    public double getCp_ots_netRecovAmt()
    {
        return cp_ots_netRecovAmt;
    }

    public void setCp_ots_netAmountInDefoault(double cp_ots_netAmountInDefoault)
    {
        this.cp_ots_netAmountInDefoault = cp_ots_netAmountInDefoault;
    }

    public double getCp_ots_netAmountInDefoault()
    {
        return cp_ots_netAmountInDefoault;
    }

    public void setCp_ots_secIntalAMt(double cp_ots_secIntalAMt)
    {
        this.cp_ots_secIntalAMt = cp_ots_secIntalAMt;
    }

    public double getCp_ots_secIntalAMt()
    {
        return cp_ots_secIntalAMt;
    }

    public void setCp_ots_finalPayout(double cp_ots_finalPayout)
    {
        this.cp_ots_finalPayout = cp_ots_finalPayout;
    }

    public double getCp_ots_finalPayout()
    {
        return cp_ots_finalPayout;
    }

    public void setCp_ots_enterMember(String cp_ots_enterMember)
    {
        this.cp_ots_enterMember = cp_ots_enterMember;
    }

    public String getCp_ots_enterMember()
    {
        return cp_ots_enterMember;
    }

    public void setCp_ots_appRefNo(String cp_ots_appRefNo)
    {
        this.cp_ots_appRefNo = cp_ots_appRefNo;
    }

    public String getCp_ots_appRefNo()
    {
        return cp_ots_appRefNo;
    }

    public void setCp_ots_mliName(String cp_ots_mliName)
    {
        this.cp_ots_mliName = cp_ots_mliName;
    }

    public String getCp_ots_mliName()
    {
        return cp_ots_mliName;
    }

    public void setCp_ots_unitName(String cp_ots_unitName)
    {
        this.cp_ots_unitName = cp_ots_unitName;
    }

    public String getCp_ots_unitName()
    {
        return cp_ots_unitName;
    }

    public void setCp_ots_gender(String cp_ots_gender)
    {
        this.cp_ots_gender = cp_ots_gender;
    }

    public String getCp_ots_gender()
    {
        return cp_ots_gender;
    }

    public void setCp_ots_UnitAssitByMSE(String cp_ots_UnitAssitByMSE)
    {
        this.cp_ots_UnitAssitByMSE = cp_ots_UnitAssitByMSE;
    }

    public String getCp_ots_UnitAssitByMSE()
    {
        return cp_ots_UnitAssitByMSE;
    }

    public void setCp_ots_npaDate(Date cp_ots_npaDate)
    {
        this.cp_ots_npaDate = cp_ots_npaDate;
    }

    public Date getCp_ots_npaDate()
    {
        return cp_ots_npaDate;
    }

    public void setCp_ots_firstInstallDate(Date cp_ots_firstInstallDate)
    {
        this.cp_ots_firstInstallDate = cp_ots_firstInstallDate;
    }

    public Date getCp_ots_firstInstallDate()
    {
        return cp_ots_firstInstallDate;
    }

    public void setCp_ots_clmappDate(Date cp_ots_clmappDate)
    {
        this.cp_ots_clmappDate = cp_ots_clmappDate;
    }

    public Date getCp_ots_clmappDate()
    {
        return cp_ots_clmappDate;
    }

    public void setClaimdeatilforOts(ArrayList claimdeatilforOts)
    {
        this.claimdeatilforOts = claimdeatilforOts;
    }

    public ArrayList getClaimdeatilforOts()
    {
        return claimdeatilforOts;
    }

    public void setCp_ots_Total(double cp_ots_Total)
    {
        this.cp_ots_Total = cp_ots_Total;
    }

    public double getCp_ots_Total()
    {
        return cp_ots_Total;
    }

    public void setCp_ots_npatotal(double cp_ots_npatotal)
    {
        this.cp_ots_npatotal = cp_ots_npatotal;
    }

    public double getCp_ots_npatotal()
    {
        return cp_ots_npatotal;
    }

    public void setCp_ots_cp_ots_totRecAMt(double cp_ots_cp_ots_totRecAMt)
    {
        this.cp_ots_cp_ots_totRecAMt = cp_ots_cp_ots_totRecAMt;
    }

    public double getCp_ots_cp_ots_totRecAMt()
    {
        return cp_ots_cp_ots_totRecAMt;
    }

    public void setLoopobjtData(ArrayList loopobjtData)
    {
        LoopobjtData = loopobjtData;
    }

    public ArrayList getLoopobjtData()
    {
        return LoopobjtData;
    }

    public void setCp_ots_guarnteetotal(double cp_ots_guarnteetotal)
    {
        this.cp_ots_guarnteetotal = cp_ots_guarnteetotal;
    }

    public double getCp_ots_guarnteetotal()
    {
        return cp_ots_guarnteetotal;
    }

    public void setCp_ots_npatotalAmt(double cp_ots_npatotalAmt)
    {
        this.cp_ots_npatotalAmt = cp_ots_npatotalAmt;
    }

    public double getCp_ots_npatotalAmt()
    {
        return cp_ots_npatotalAmt;
    }

    public void setCp_ots_recovTotal(double cp_ots_recovTotal)
    {
        this.cp_ots_recovTotal = cp_ots_recovTotal;
    }

    public double getCp_ots_recovTotal()
    {
        return cp_ots_recovTotal;
    }

    public void setCp_ots_netOutstandingTotal(double cp_ots_netOutstandingTotal)
    {
        this.cp_ots_netOutstandingTotal = cp_ots_netOutstandingTotal;
    }

    public double getCp_ots_netOutstandingTotal()
    {
        return cp_ots_netOutstandingTotal;
    }

    public void setCp_ots_liavleAmtTotal(double cp_ots_liavleAmtTotal)
    {
        this.cp_ots_liavleAmtTotal = cp_ots_liavleAmtTotal;
    }

    public double getCp_ots_liavleAmtTotal()
    {
        return cp_ots_liavleAmtTotal;
    }

    public void setCp_ots_firstInstallmentPaidTotal(double cp_ots_firstInstallmentPaidTotal)
    {
        this.cp_ots_firstInstallmentPaidTotal = cp_ots_firstInstallmentPaidTotal;
    }

    public double getCp_ots_firstInstallmentPaidTotal()
    {
        return cp_ots_firstInstallmentPaidTotal;
    }

    public void setCp_ots_recafterfirstinstallTotal(double cp_ots_recafterfirstinstallTotal)
    {
        this.cp_ots_recafterfirstinstallTotal = cp_ots_recafterfirstinstallTotal;
    }

    public double getCp_ots_recafterfirstinstallTotal()
    {
        return cp_ots_recafterfirstinstallTotal;
    }

    public void setCp_ots_totaDecdectAMt(double cp_ots_totaDecdectAMt)
    {
        this.cp_ots_totaDecdectAMt = cp_ots_totaDecdectAMt;
    }

    public double getCp_ots_totaDecdectAMt()
    {
        return cp_ots_totaDecdectAMt;
    }

    public void setCp_ots_totNotdedctAmt(double cp_ots_totNotdedctAmt)
    {
        this.cp_ots_totNotdedctAmt = cp_ots_totNotdedctAmt;
    }

    public double getCp_ots_totNotdedctAmt()
    {
        return cp_ots_totNotdedctAmt;
    }

    public void setCp_ots_totnetRecovAmt(double cp_ots_totnetRecovAmt)
    {
        this.cp_ots_totnetRecovAmt = cp_ots_totnetRecovAmt;
    }

    public double getCp_ots_totnetRecovAmt()
    {
        return cp_ots_totnetRecovAmt;
    }

    public void setCp_ots_netAmtindefoultTotal(double cp_ots_netAmtindefoultTotal)
    {
        this.cp_ots_netAmtindefoultTotal = cp_ots_netAmtindefoultTotal;
    }

    public double getCp_ots_netAmtindefoultTotal()
    {
        return cp_ots_netAmtindefoultTotal;
    }

    public void setCp_ots_secinstamentAmtTotal(double cp_ots_secinstamentAmtTotal)
    {
        this.cp_ots_secinstamentAmtTotal = cp_ots_secinstamentAmtTotal;
    }

    public double getCp_ots_secinstamentAmtTotal()
    {
        return cp_ots_secinstamentAmtTotal;
    }

    public void setCp_ots_finalPayoutAmtTotal(double cp_ots_finalPayoutAmtTotal)
    {
        this.cp_ots_finalPayoutAmtTotal = cp_ots_finalPayoutAmtTotal;
    }

    public double getCp_ots_finalPayoutAmtTotal()
    {
        return cp_ots_finalPayoutAmtTotal;
    }

    public void setCp_ots_remarks2(String cp_ots_remarks2)
    {
        this.cp_ots_remarks2 = cp_ots_remarks2;
    }

    public String getCp_ots_remarks2()
    {
        return cp_ots_remarks2;
    }

    public void setOts_memberId(String ots_memberId)
    {
        this.ots_memberId = ots_memberId;
    }

    public String getOts_memberId()
    {
        return ots_memberId;
    }

    public void setOts_clmRefNo(String ots_clmRefNo)
    {
        this.ots_clmRefNo = ots_clmRefNo;
    }

    public String getOts_clmRefNo()
    {
        return ots_clmRefNo;
    }

    public void setOts_unitName(String ots_unitName)
    {
        this.ots_unitName = ots_unitName;
    }

    public String getOts_unitName()
    {
        return ots_unitName;
    }

    public void setOts_gaurnteeAmt(double ots_gaurnteeAmt)
    {
        this.ots_gaurnteeAmt = ots_gaurnteeAmt;
    }

    public double getOts_gaurnteeAmt()
    {
        return ots_gaurnteeAmt;
    }

    public void setOts_amtInDefault(double ots_amtInDefault)
    {
        this.ots_amtInDefault = ots_amtInDefault;
    }

    public double getOts_amtInDefault()
    {
        return ots_amtInDefault;
    }

    public void setOts_recoveryPrimary(double ots_recoveryPrimary)
    {
        this.ots_recoveryPrimary = ots_recoveryPrimary;
    }

    public double getOts_recoveryPrimary()
    {
        return ots_recoveryPrimary;
    }

    public void setOts_netOutstanding(double ots_netOutstanding)
    {
        this.ots_netOutstanding = ots_netOutstanding;
    }

    public double getOts_netOutstanding()
    {
        return ots_netOutstanding;
    }

    public void setOts_liableAmt(double ots_liableAmt)
    {
        this.ots_liableAmt = ots_liableAmt;
    }

    public double getOts_liableAmt()
    {
        return ots_liableAmt;
    }

    public void setOts_firstInstallPaidAmt(double ots_firstInstallPaidAmt)
    {
        this.ots_firstInstallPaidAmt = ots_firstInstallPaidAmt;
    }

    public double getOts_firstInstallPaidAmt()
    {
        return ots_firstInstallPaidAmt;
    }

    public void setOts_recoveryAfterPrimary(double ots_recoveryAfterPrimary)
    {
        this.ots_recoveryAfterPrimary = ots_recoveryAfterPrimary;
    }

    public double getOts_recoveryAfterPrimary()
    {
        return ots_recoveryAfterPrimary;
    }

    public void setOts_legalExpencesNotDeducted(double ots_legalExpencesNotDeducted)
    {
        this.ots_legalExpencesNotDeducted = ots_legalExpencesNotDeducted;
    }

    public double getOts_legalExpencesNotDeducted()
    {
        return ots_legalExpencesNotDeducted;
    }

    public void setOts_legalExpencesDeducted(double ots_legalExpencesDeducted)
    {
        this.ots_legalExpencesDeducted = ots_legalExpencesDeducted;
    }

    public double getOts_legalExpencesDeducted()
    {
        return ots_legalExpencesDeducted;
    }

    public void setOts_netRecovery(double ots_netRecovery)
    {
        this.ots_netRecovery = ots_netRecovery;
    }

    public double getOts_netRecovery()
    {
        return ots_netRecovery;
    }

    public void setOts_netDefaultAmt(double ots_netDefaultAmt)
    {
        this.ots_netDefaultAmt = ots_netDefaultAmt;
    }

    public double getOts_netDefaultAmt()
    {
        return ots_netDefaultAmt;
    }

    public void setOts_secondInstallmentAmt(double ots_secondInstallmentAmt)
    {
        this.ots_secondInstallmentAmt = ots_secondInstallmentAmt;
    }

    public double getOts_secondInstallmentAmt()
    {
        return ots_secondInstallmentAmt;
    }

    public void setOts_finalPayout(double ots_finalPayout)
    {
        this.ots_finalPayout = ots_finalPayout;
    }

    public double getOts_finalPayout()
    {
        return ots_finalPayout;
    }

    public void setOts_decisionTaken(String ots_decisionTaken)
    {
        this.ots_decisionTaken = ots_decisionTaken;
    }

    public String getOts_decisionTaken()
    {
        return ots_decisionTaken;
    }

    public void setOts_remark(String ots_remark)
    {
        this.ots_remark = ots_remark;
    }

    public String getOts_remark()
    {
        return ots_remark;
    }

    public void setOtsClmProcess(ArrayList otsClmProcess)
    {
        this.otsClmProcess = otsClmProcess;
    }

    public ArrayList getOtsClmProcess()
    {
        return otsClmProcess;
    }

    public void setCp_ots_userChoice(String cp_ots_userChoice)
    {
        this.cp_ots_userChoice = cp_ots_userChoice;
    }

    public String getCp_ots_userChoice()
    {
        return cp_ots_userChoice;
    }

    public void setCp_ots_user(String cp_ots_user)
    {
        this.cp_ots_user = cp_ots_user;
    }

    public String getCp_ots_user()
    {
        return cp_ots_user;
    }

    public void setCp_ots_userList(ArrayList cp_ots_userList)
    {
        this.cp_ots_userList = cp_ots_userList;
    }

    public ArrayList getCp_ots_userList()
    {
        return cp_ots_userList;
    }

    public void setClm_decision(String clm_decision)
    {
        this.clm_decision = clm_decision;
    }

    public String getClm_decision()
    {
        return clm_decision;
    }

    public void setCp_ots_fromDate(String cp_ots_fromDate)
    {
        this.cp_ots_fromDate = cp_ots_fromDate;
    }

    public String getCp_ots_fromDate()
    {
        return cp_ots_fromDate;
    }

    public void setCp_ots_toDate(String cp_ots_toDate)
    {
        this.cp_ots_toDate = cp_ots_toDate;
    }

    public String getCp_ots_toDate()
    {
        return cp_ots_toDate;
    }

    public void setCp_ots_clmeligibleamt(double cp_ots_clmeligibleamt)
    {
        this.cp_ots_clmeligibleamt = cp_ots_clmeligibleamt;
    }

    public double getCp_ots_clmeligibleamt()
    {
        return cp_ots_clmeligibleamt;
    }

    public void setClaimformdataReport(ArrayList claimformdataReport)
    {
        this.claimformdataReport = claimformdataReport;
    }

    public ArrayList getClaimformdataReport()
    {
        return claimformdataReport;
    }

    public void setClaimRefNum(String claimRefNum)
    {
        this.claimRefNum = claimRefNum;
    }

    public String getClaimRefNum()
    {
        return claimRefNum;
    }

    public void setEnterCgpan(String enterCgpan)
    {
        this.enterCgpan = enterCgpan;
    }

    public String getEnterCgpan()
    {
        return enterCgpan;
    }

    public void setEnterMember(String enterMember)
    {
        this.enterMember = enterMember;
    }

    public String getEnterMember()
    {
        return enterMember;
    }

    public void setAppRefNo(String appRefNo)
    {
        this.appRefNo = appRefNo;
    }

    public String getAppRefNo()
    {
        return appRefNo;
    }

    public void setMemberID(String memberID)
    {
        this.memberID = memberID;
    }

    public String getMemberID()
    {
        return memberID;
    }

    public void setSsiUnitName(String ssiUnitName)
    {
        this.ssiUnitName = ssiUnitName;
    }

    public String getSsiUnitName()
    {
        return ssiUnitName;
    }

    public void setDtOfNPAReportedToCGTSI(Date dtOfNPAReportedToCGTSI)
    {
        this.dtOfNPAReportedToCGTSI = dtOfNPAReportedToCGTSI;
    }

    public Date getDtOfNPAReportedToCGTSI()
    {
        return dtOfNPAReportedToCGTSI;
    }

    public void setCgpanNo(String cgpanNo)
    {
        this.cgpanNo = cgpanNo;
    }

    public String getCgpanNo()
    {
        return cgpanNo;
    }

    public void setAppApproveDate(Date appApproveDate)
    {
        this.appApproveDate = appApproveDate;
    }

    public Date getAppApproveDate()
    {
        return appApproveDate;
    }

    public void setAppAmount(String appAmount)
    {
        this.appAmount = appAmount;
    }

    public String getAppAmount()
    {
        return appAmount;
    }

    public void setClaimdeatil(ArrayList claimdeatil)
    {
        this.claimdeatil = claimdeatil;
    }

    public ArrayList getClaimdeatil()
    {
        return claimdeatil;
    }

    public void setDateOfdeclartionRecive(Date dateOfdeclartionRecive)
    {
        this.dateOfdeclartionRecive = dateOfdeclartionRecive;
    }

    public Date getDateOfdeclartionRecive()
    {
        return dateOfdeclartionRecive;
    }

    public void setBooleanProperty(boolean booleanProperty)
    {
        this.booleanProperty = booleanProperty;
    }

    public boolean getBooleanProperty()
    {
        return booleanProperty;
    }

    public boolean isBooleanProperty()
    {
        return booleanProperty;
    }

    public void setMliName(String mliName)
    {
        this.mliName = mliName;
    }

    public String getMliName()
    {
        return mliName;
    }

    public void setMliId(String mliId)
    {
        this.mliId = mliId;
    }

    public String getMliId()
    {
        return mliId;
    }

    public void setPlaceforClmRecovery(String placeforClmRecovery)
    {
        this.placeforClmRecovery = placeforClmRecovery;
    }

    public String getPlaceforClmRecovery()
    {
        return placeforClmRecovery;
    }

    public void setCgpanforclamRecovery(String cgpanforclamRecovery)
    {
        this.cgpanforclamRecovery = cgpanforclamRecovery;
    }

    public String getCgpanforclamRecovery()
    {
        return cgpanforclamRecovery;
    }

    public void setClmEligibleAmt(double clmEligibleAmt)
    {
        this.clmEligibleAmt = clmEligibleAmt;
    }

    public double getClmEligibleAmt()
    {
        return clmEligibleAmt;
    }

    public void setFirstinstalmentrelease(double firstinstalmentrelease)
    {
        this.firstinstalmentrelease = firstinstalmentrelease;
    }

    public double getFirstinstalmentrelease()
    {
        return firstinstalmentrelease;
    }

    public void setAmtRecipt(double amtRecipt)
    {
        this.amtRecipt = amtRecipt;
    }

    public double getAmtRecipt()
    {
        return amtRecipt;
    }

    public void setDateOfreciept(Date dateOfreciept)
    {
        this.dateOfreciept = dateOfreciept;
    }

    public Date getDateOfreciept()
    {
        return dateOfreciept;
    }

    public void setExpIncforRecovery(double expIncforRecovery)
    {
        this.expIncforRecovery = expIncforRecovery;
    }

    public double getExpIncforRecovery()
    {
        return expIncforRecovery;
    }

    public void setExpDeducted(String expDeducted)
    {
        this.expDeducted = expDeducted;
    }

    public String getExpDeducted()
    {
        return expDeducted;
    }

    public void setNetRecovery(double netRecovery)
    {
        this.netRecovery = netRecovery;
    }

    public double getNetRecovery()
    {
        return netRecovery;
    }

    public void setDdNo(String ddNo)
    {
        this.ddNo = ddNo;
    }

    public String getDdNo()
    {
        return ddNo;
    }

    public void setDdDate(Date ddDate)
    {
        this.ddDate = ddDate;
    }

    public Date getDdDate()
    {
        return ddDate;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setViewRecArr(ArrayList viewRecArr)
    {
        this.viewRecArr = viewRecArr;
    }

    public ArrayList getViewRecArr()
    {
        return viewRecArr;
    }

    public void setBooleanExpInc(boolean booleanExpInc)
    {
        this.booleanExpInc = booleanExpInc;
    }

    public boolean isBooleanExpInc()
    {
        return booleanExpInc;
    }

    public void setBooleanFinalRecovery(boolean booleanFinalRecovery)
    {
        this.booleanFinalRecovery = booleanFinalRecovery;
    }

    public boolean isBooleanFinalRecovery()
    {
        return booleanFinalRecovery;
    }

    public void setFalgforCasesafet(String falgforCasesafet)
    {
        this.falgforCasesafet = falgforCasesafet;
    }

    public String getFalgforCasesafet()
    {
        return falgforCasesafet;
    }

    public void setWomenOperated(String womenOperated)
    {
        this.womenOperated = womenOperated;
    }

    public String getWomenOperated()
    {
        return womenOperated;
    }

    public void setNerFlag(String nerFlag)
    {
        this.nerFlag = nerFlag;
    }

    public String getNerFlag()
    {
        return nerFlag;
    }

    public void setClaimRefSet(Map claimRefSet)
    {
        this.claimRefSet = claimRefSet;
    }

    public Map getClaimRefSet()
    {
        return claimRefSet;
    }

    public void setQ1Flags(Map q1Flags)
    {
        this.q1Flags = q1Flags;
    }

    public Map getQ1Flags()
    {
        return q1Flags;
    }

    public void setQ2Flags(Map q2Flags)
    {
        this.q2Flags = q2Flags;
    }

    public Map getQ2Flags()
    {
        return q2Flags;
    }

    public void setQ3Flags(Map q3Flags)
    {
        this.q3Flags = q3Flags;
    }

    public Map getQ3Flags()
    {
        return q3Flags;
    }

    public void setQ4Flags(Map q4Flags)
    {
        this.q4Flags = q4Flags;
    }

    public Map getQ4Flags()
    {
        return q4Flags;
    }

    public void setQ5Flags(Map q5Flags)
    {
        this.q5Flags = q5Flags;
    }

    public Map getQ5Flags()
    {
        return q5Flags;
    }

    public void setQ6Flags(Map q6Flags)
    {
        this.q6Flags = q6Flags;
    }

    public Map getQ6Flags()
    {
        return q6Flags;
    }

    public void setQ7Flags(Map q7Flags)
    {
        this.q7Flags = q7Flags;
    }

    public Map getQ7Flags()
    {
        return q7Flags;
    }

    public void setQ8Flags(Map q8Flags)
    {
        this.q8Flags = q8Flags;
    }

    public Map getQ8Flags()
    {
        return q8Flags;
    }

    public void setQ9Flags(Map q9Flags)
    {
        this.q9Flags = q9Flags;
    }

    public Map getQ9Flags()
    {
        return q9Flags;
    }

    public void setQ10Flags(Map q10Flags)
    {
        this.q10Flags = q10Flags;
    }

    public Map getQ10Flags()
    {
        return q10Flags;
    }

    public void setQ11Flags(Map q11Flags)
    {
        this.q11Flags = q11Flags;
    }

    public Map getQ11Flags()
    {
        return q11Flags;
    }

    public void setQ12Flags(Map q12Flags)
    {
        this.q12Flags = q12Flags;
    }

    public Map getQ12Flags()
    {
        return q12Flags;
    }

    public void setQ13Flags(Map q13Flags)
    {
        this.q13Flags = q13Flags;
    }

    public Map getQ13Flags()
    {
        return q13Flags;
    }

    public void setQ14Flags(Map q14Flags)
    {
        this.q14Flags = q14Flags;
    }

    public Map getQ14Flags()
    {
        return q14Flags;
    }

    public void setLtrRefNoSet(Map ltrRefNoSet)
    {
        this.ltrRefNoSet = ltrRefNoSet;
    }

    public Map getLtrRefNoSet()
    {
        return ltrRefNoSet;
    }

    public void setLtrDtSet(Map ltrDtSet)
    {
        this.ltrDtSet = ltrDtSet;
    }

    public Map getLtrDtSet()
    {
        return ltrDtSet;
    }

    public void setClaimProcessingDetails(Vector claimProcessingDetails)
    {
        this.claimProcessingDetails = claimProcessingDetails;
    }

    public Vector getClaimProcessingDetails()
    {
        return claimProcessingDetails;
    }

    public void resetTCClaimsProcessPage(ActionMapping arg0, ServletRequest arg1)
    {
        approvedClaimAmount.clear();
        decision.clear();
        remarks.clear();
        claimRefSet.clear();
        q1Flags.clear();
        q2Flags.clear();
        q3Flags.clear();
        q4Flags.clear();
        q5Flags.clear();
        q6Flags.clear();
        q7Flags.clear();
        q8Flags.clear();
        q9Flags.clear();
        q10Flags.clear();
        q11Flags.clear();
        q12Flags.clear();
        q13Flags.clear();
        q14Flags.clear();
        ltrRefNoSet.clear();
        ltrDtSet.clear();
    }

    public void setAVector(Vector aVector)
    {
        this.aVector = aVector;
    }

    public Vector getAVector()
    {
        return aVector;
    }

    public void setRVector(Vector rVector)
    {
        this.rVector = rVector;
    }

    public Vector getRVector()
    {
        return rVector;
    }

    public void setHVector(Vector hVector)
    {
        this.hVector = hVector;
    }

    public Vector getHVector()
    {
        return hVector;
    }

    public void setFVector(Vector fVector)
    {
        this.fVector = fVector;
    }

    public Vector getFVector()
    {
        return fVector;
    }

    public void setTVector(Vector tVector)
    {
        this.tVector = tVector;
    }

    public Vector getTVector()
    {
        return tVector;
    }

    public void setTrVector(Vector trVector)
    {
        this.trVector = trVector;
    }

    public Vector getTrVector()
    {
        return trVector;
    }

    public void setWdVector(Vector wdVector)
    {
        this.wdVector = wdVector;
    }

    public Vector getWdVector()
    {
        return wdVector;
    }

    public void setTemprejectedVectorSize(int temprejectedVectorSize)
    {
        this.temprejectedVectorSize = temprejectedVectorSize;
    }

    public int getTemprejectedVectorSize()
    {
        return temprejectedVectorSize;
    }

    public void setClaimwithdrawnVectorSize(int claimwithdrawnVectorSize)
    {
        this.claimwithdrawnVectorSize = claimwithdrawnVectorSize;
    }

    public int getClaimwithdrawnVectorSize()
    {
        return claimwithdrawnVectorSize;
    }

    public void setClaimPendingVectorSize(int claimPendingVectorSize)
    {
        this.claimPendingVectorSize = claimPendingVectorSize;
    }

    public int getClaimPendingVectorSize()
    {
        return claimPendingVectorSize;
    }

    public void setNewChipParmoGender(String newChipParmoGender)
    {
        this.newChipParmoGender = newChipParmoGender;
    }

    public String getNewChipParmoGender()
    {
        return newChipParmoGender;
    }

    public void setNewborowerState(String newborowerState)
    {
        this.newborowerState = newborowerState;
    }

    public String getNewborowerState()
    {
        return newborowerState;
    }

    public void setNewTypeActivity(String newTypeActivity)
    {
        this.newTypeActivity = newTypeActivity;
    }

    public String getNewTypeActivity()
    {
        return newTypeActivity;
    }

    public void setNewSchemName(String newSchemName)
    {
        this.newSchemName = newSchemName;
    }

    public String getNewSchemName()
    {
        return newSchemName;
    }

    public void setNewNERFlag(String newNERFlag)
    {
        this.newNERFlag = newNERFlag;
    }

    public String getNewNERFlag()
    {
        return newNERFlag;
    }

    public void setNewCGPAN(String newCGPAN)
    {
        this.newCGPAN = newCGPAN;
    }

    public String getNewCGPAN()
    {
        return newCGPAN;
    }

    public void setNewGuarnteeIssueAmt(double newGuarnteeIssueAmt)
    {
        this.newGuarnteeIssueAmt = newGuarnteeIssueAmt;
    }

    public double getNewGuarnteeIssueAmt()
    {
        return newGuarnteeIssueAmt;
    }

    public void setNewAmtOutstandAsOnNPA(double newAmtOutstandAsOnNPA)
    {
        this.newAmtOutstandAsOnNPA = newAmtOutstandAsOnNPA;
    }

    public double getNewAmtOutstandAsOnNPA()
    {
        return newAmtOutstandAsOnNPA;
    }

    public void setNewAmtRecoverAfterNPA(double newAmtRecoverAfterNPA)
    {
        this.newAmtRecoverAfterNPA = newAmtRecoverAfterNPA;
    }

    public double getNewAmtRecoverAfterNPA()
    {
        return newAmtRecoverAfterNPA;
    }

    public void setNewAmtClaimByMli(double newAmtClaimByMli)
    {
        this.newAmtClaimByMli = newAmtClaimByMli;
    }

    public double getNewAmtClaimByMli()
    {
        return newAmtClaimByMli;
    }

    public void setNewAmtDeductedFromMli(double newAmtDeductedFromMli)
    {
        this.newAmtDeductedFromMli = newAmtDeductedFromMli;
    }

    public double getNewAmtDeductedFromMli()
    {
        return newAmtDeductedFromMli;
    }

    public void setCp_ots_microfalg(boolean cp_ots_microfalg)
    {
        this.cp_ots_microfalg = cp_ots_microfalg;
    }

    public boolean isCp_ots_microfalg()
    {
        return cp_ots_microfalg;
    }

    public void setCp_ots_liablePercent(double cp_ots_liablePercent)
    {
        this.cp_ots_liablePercent = cp_ots_liablePercent;
    }

    public double getCp_ots_liablePercent()
    {
        return cp_ots_liablePercent;
    }

    public void setFirstInsatllDate(Date firstInsatllDate)
    {
        this.firstInsatllDate = firstInsatllDate;
    }

    public Date getFirstInsatllDate()
    {
        return firstInsatllDate;
    }

    public void setTotalNetRecovery(double totalNetRecovery)
    {
        this.totalNetRecovery = totalNetRecovery;
    }

    public double getTotalNetRecovery()
    {
        return totalNetRecovery;
    }

    public void setCloserrequest(String closerrequest)
    {
        this.closerrequest = closerrequest;
    }

    public String getCloserrequest()
    {
        return closerrequest;
    }

    public void setLtr_ref(String ltr_ref)
    {
        this.ltr_ref = ltr_ref;
    }

    public String getLtr_ref()
    {
        return ltr_ref;
    }

    public void setForCGPANWiseDataArray(ArrayList forCGPANWiseDataArray)
    {
        this.forCGPANWiseDataArray = forCGPANWiseDataArray;
    }

    public ArrayList getForCGPANWiseDataArray()
    {
        return forCGPANWiseDataArray;
    }

    public void setTestFirstInstalment(double testFirstInstalment)
    {
        this.testFirstInstalment = testFirstInstalment;
    }

    public double getTestFirstInstalment()
    {
        return testFirstInstalment;
    }

    public void setUnitAssistedMSE(String unitAssistedMSE)
    {
        this.unitAssistedMSE = unitAssistedMSE;
    }

    public String getUnitAssistedMSE()
    {
        return unitAssistedMSE;
    }

    public void setIsAcctFraud(String isAcctFraud)
    {
        this.isAcctFraud = isAcctFraud;
    }

    public String getIsAcctFraud()
    {
        return isAcctFraud;
    }

    public void setIsEnquiryConcluded(String isEnquiryConcluded)
    {
        this.isEnquiryConcluded = isEnquiryConcluded;
    }

    public String getIsEnquiryConcluded()
    {
        return isEnquiryConcluded;
    }

    public void setIsMLIInvolved(String isMLIInvolved)
    {
        this.isMLIInvolved = isMLIInvolved;
    }

    public String getIsMLIInvolved()
    {
        return isMLIInvolved;
    }

    public void setReasonForFilingSuit(String reasonForFilingSuit)
    {
        this.reasonForFilingSuit = reasonForFilingSuit;
    }

    public String getReasonForFilingSuit()
    {
        return reasonForFilingSuit;
    }

    public void setSubsidyFlag(String subsidyFlag)
    {
        this.subsidyFlag = subsidyFlag;
    }

    public String getSubsidyFlag()
    {
        return subsidyFlag;
    }

    public void setIsSubsidyRcvdAfterNpa(String isSubsidyRcvdAfterNpa)
    {
        this.isSubsidyRcvdAfterNpa = isSubsidyRcvdAfterNpa;
    }

    public String getIsSubsidyRcvdAfterNpa()
    {
        return isSubsidyRcvdAfterNpa;
    }

    public void setIsSubsidyAdjustedOnDues(String isSubsidyAdjustedOnDues)
    {
        this.isSubsidyAdjustedOnDues = isSubsidyAdjustedOnDues;
    }

    public String getIsSubsidyAdjustedOnDues()
    {
        return isSubsidyAdjustedOnDues;
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

    public void setCreditSupport(String creditSupport)
    {
        this.creditSupport = creditSupport;
    }

    public String getCreditSupport()
    {
        return creditSupport;
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

    public void setReasonForIssueRecallNotice(String reasonForIssueRecallNotice)
    {
        this.reasonForIssueRecallNotice = reasonForIssueRecallNotice;
    }

    public String getReasonForIssueRecallNotice()
    {
        return reasonForIssueRecallNotice;
    }

    public void setInclusionOfReciept(String inclusionOfReciept)
    {
        this.inclusionOfReciept = inclusionOfReciept;
    }

    public String getInclusionOfReciept()
    {
        return inclusionOfReciept;
    }

    public void setConfirmRecoveryValues(String confirmRecoveryValues)
    {
        this.confirmRecoveryValues = confirmRecoveryValues;
    }

    public String getConfirmRecoveryValues()
    {
        return confirmRecoveryValues;
    }

    public void setAssetPossessionDate(String assetPossessionDate)
    {
        this.assetPossessionDate = assetPossessionDate;
    }

    public String getAssetPossessionDate()
    {
        return assetPossessionDate;
    }

    public void setTotSecAsOnSanc(double totSecAsOnSanc)
    {
        this.totSecAsOnSanc = totSecAsOnSanc;
    }

    public double getTotSecAsOnSanc()
    {
        return totSecAsOnSanc;
    }

    public void setTotSecAsOnNpa(double totSecAsOnNpa)
    {
        this.totSecAsOnNpa = totSecAsOnNpa;
    }

    public double getTotSecAsOnNpa()
    {
        return totSecAsOnNpa;
    }

    public void setTotSecAsOnClaim(double totSecAsOnClaim)
    {
        this.totSecAsOnClaim = totSecAsOnClaim;
    }

    public double getTotSecAsOnClaim()
    {
        return totSecAsOnClaim;
    }

    public void setTotalDisbursementAmt(Map totalDisbursementAmt)
    {
        this.totalDisbursementAmt = totalDisbursementAmt;
    }

    public Map getTotalDisbursementAmt()
    {
        return totalDisbursementAmt;
    }

    public void setTotalDisbursementAmt(String key, Object value)
    {
        totalDisbursementAmt.put(key, value);
    }

    public Object getTotalDisbursementAmt(String key)
    {
        return totalDisbursementAmt.get(key);
    }

    public void setAppApprovedAmt(Map appApprovedAmt)
    {
        this.appApprovedAmt = appApprovedAmt;
    }

    public Map getAppApprovedAmt()
    {
        return appApprovedAmt;
    }

    public void setAppApprovedAmt(String key, Object value)
    {
        appApprovedAmt.put(key, value);
    }

    public Object getAppApprovedAmt(String key)
    {
        return appApprovedAmt.get(key);
    }

    public void setClaimFlagsTc(Map claimFlagsTc)
    {
        this.claimFlagsTc = claimFlagsTc;
    }

    public Map getClaimFlagsTc()
    {
        return claimFlagsTc;
    }

    public void setClaimFlagsTc(String key, Object value)
    {
        claimFlagsTc.put(key, value);
    }

    public Object getClaimFlagsTc(String key)
    {
        return claimFlagsTc.get(key);
    }

    public void setClaimFlagsWc(Map claimFlagsWc)
    {
        this.claimFlagsWc = claimFlagsWc;
    }

    public Map getClaimFlagsWc()
    {
        return claimFlagsWc;
    }

    public void setClaimFlagsWc(String key, Object value)
    {
        claimFlagsWc.put(key, value);
    }

    public Object getClaimFlagsWc(String key)
    {
        return claimFlagsWc.get(key);
    }

    public void setDealingOfficerName(String dealingOfficerName)
    {
        this.dealingOfficerName = dealingOfficerName;
    }

    public String getDealingOfficerName()
    {
        return dealingOfficerName;
    }

    public void setClmRefNumberNew(String clmRefNumberNew)
    {
        this.clmRefNumberNew = clmRefNumberNew;
    }

    public String getClmRefNumberNew()
    {
        return clmRefNumberNew;
    }

    public FormFile getNpaReportFile()
    {
        return npaReportFile;
    }

    public void setNpaReportFile(FormFile npaReportFile)
    {
        this.npaReportFile = npaReportFile;
    }

    public FormFile getDiligenceReportFile()
    {
        return diligenceReportFile;
    }

    public void setDiligenceReportFile(FormFile diligenceReportFile)
    {
        this.diligenceReportFile = diligenceReportFile;
    }

    public FormFile getPostInspectionReportFile()
    {
        return postInspectionReportFile;
    }

    public void setPostInspectionReportFile(FormFile postInspectionReportFile)
    {
        this.postInspectionReportFile = postInspectionReportFile;
    }

    public FormFile getPostNpaReportFile()
    {
        return postNpaReportFile;
    }

    public void setPostNpaReportFile(FormFile postNpaReportFile)
    {
        this.postNpaReportFile = postNpaReportFile;
    }

    public String getInsuranceFileFlag()
    {
        return insuranceFileFlag;
    }

    public void setInsuranceFileFlag(String insuranceFileFlag)
    {
        this.insuranceFileFlag = insuranceFileFlag;
    }

    public String getInsuranceReason()
    {
        return insuranceReason;
    }

    public void setInsuranceReason(String insuranceReason)
    {
        this.insuranceReason = insuranceReason;
    }

    public FormFile getSuitReportFile()
    {
        return suitReportFile;
    }

    public void setSuitReportFile(FormFile suitReportFile)
    {
        this.suitReportFile = suitReportFile;
    }

    public FormFile getFinalVerdictFile()
    {
        return finalVerdictFile;
    }

    public void setFinalVerdictFile(FormFile finalVerdictFile)
    {
        this.finalVerdictFile = finalVerdictFile;
    }

    public FormFile getIdProofFile()
    {
        return idProofFile;
    }

    public void setIdProofFile(FormFile idProofFile)
    {
        this.idProofFile = idProofFile;
    }

    public FormFile getOtherFile()
    {
        return otherFile;
    }

    public void setOtherFile(FormFile otherFile)
    {
        this.otherFile = otherFile;
    }

    public FormFile getStaffReportFile()
    {
        return staffReportFile;
    }

    public void setStaffReportFile(FormFile staffReportFile)
    {
        this.staffReportFile = staffReportFile;
    }

    public String getBankRateType()
    {
        return bankRateType;
    }

    public void setBankRateType(String bankRateType)
    {
        this.bankRateType = bankRateType;
    }

    public String getSecurityRemarks()
    {
        return securityRemarks;
    }

    public void setSecurityRemarks(String securityRemarks)
    {
        this.securityRemarks = securityRemarks;
    }

    public String getRecoveryEffortsTaken()
    {
        return recoveryEffortsTaken;
    }

    public void setRecoveryEffortsTaken(String recoveryEffortsTaken)
    {
        this.recoveryEffortsTaken = recoveryEffortsTaken;
    }

    public String getRating()
    {
        return rating;
    }

    public void setRating(String rating)
    {
        this.rating = rating;
    }

    public String getBranchAddress()
    {
        return branchAddress;
    }

    public void setBranchAddress(String branchAddress)
    {
        this.branchAddress = branchAddress;
    }

    public Object getCgpans(String key)
    {
        return cgpans.get(key);
    }

    public void setCgpans(String key, Object value)
    {
        cgpans.put(key, value);
    }

    public Object getStatementReportFiles(String key)
    {
        return statementReportFiles.get(key);
    }

    public void setStatementReportFiles(String key, Object value)
    {
        statementReportFiles.put(key, value);
    }

    public Object getAppraisalReportFiles(String key)
    {
        return appraisalReportFiles.get(key);
    }

    public void setAppraisalReportFiles(String key, Object value)
    {
        appraisalReportFiles.put(key, value);
    }

    public Object getSanctionLetterFiles(String key)
    {
        return sanctionLetterFiles.get(key);
    }

    public void setSanctionLetterFiles(String key, Object value)
    {
        sanctionLetterFiles.put(key, value);
    }

    public Object getComplianceReportFiles(String key)
    {
        return complianceReportFiles.get(key);
    }

    public void setComplianceReportFiles(String key, Object value)
    {
        complianceReportFiles.put(key, value);
    }

    public Object getPreInspectionReportFiles(String key)
    {
        return preInspectionReportFiles.get(key);
    }

    public void setPreInspectionReportFiles(String key, Object value)
    {
        preInspectionReportFiles.put(key, value);
    }

    public Object getInsuranceCopyFiles(String key)
    {
        return insuranceCopyFiles.get(key);
    }

    public void setInsuranceCopyFiles(String key, Object value)
    {
        insuranceCopyFiles.put(key, value);
    }

    public Object getRepayBeforeNpaAmts(String key)
    {
        return repayBeforeNpaAmts.get(key);
    }

    public void setRepayBeforeNpaAmts(String key, Object value)
    {
        repayBeforeNpaAmts.put(key, value);
    }

    public Object getRecoveryAfterNpaAmts(String key)
    {
        return recoveryAfterNpaAmts.get(key);
    }

    public void setRecoveryAfterNpaAmts(String key, Object value)
    {
        recoveryAfterNpaAmts.put(key, value);
    }

    public Object getInterestRates(String key)
    {
        return interestRates.get(key);
    }

    public void setInterestRates(String key, Object value)
    {
        interestRates.put(key, value);
    }

    public Map getRepayBeforeNpaAmts()
    {
        return repayBeforeNpaAmts;
    }

    public void setRepayBeforeNpaAmts(Map repayBeforeNpaAmts)
    {
        this.repayBeforeNpaAmts = repayBeforeNpaAmts;
    }

    public Map getRecoveryAfterNpaAmts()
    {
        return recoveryAfterNpaAmts;
    }

    public void setRecoveryAfterNpaAmts(Map recoveryAfterNpaAmts)
    {
        this.recoveryAfterNpaAmts = recoveryAfterNpaAmts;
    }

    public Map getInterestRates()
    {
        return interestRates;
    }

    public void setInterestRates(Map interestRates)
    {
        this.interestRates = interestRates;
    }

    public double getPlr()
    {
        return plr;
    }

    public void setPlr(double plr)
    {
        this.plr = plr;
    }

    public double getRate()
    {
        return rate;
    }

    public void setRate(double rate)
    {
        this.rate = rate;
    }

    public String getInvestmentGradeFlag()
    {
        return investmentGradeFlag;
    }

    public void setInvestmentGradeFlag(String investmentGradeFlag)
    {
        this.investmentGradeFlag = investmentGradeFlag;
    }

    public void setAdditionalAttachFiles(Map additionalAttachFiles)
    {
        this.additionalAttachFiles = additionalAttachFiles;
    }

    public Map getAdditionalAttachFiles()
    {
        return additionalAttachFiles;
    }

    public void setPlrorBaseIntestRate(double plrorBaseIntestRate)
    {
        this.plrorBaseIntestRate = plrorBaseIntestRate;
    }

    public double getPlrorBaseIntestRate()
    {
        return plrorBaseIntestRate;
    }

    public void setInvestGrad(String investGrad)
    {
        this.investGrad = investGrad;
    }

    public String getInvestGrad()
    {
        return investGrad;
    }

    public void setAdditionalstatementReportFiles1(FormFile additionalstatementReportFiles1)
    {
        this.additionalstatementReportFiles1 = additionalstatementReportFiles1;
    }

    public FormFile getAdditionalstatementReportFiles1()
    {
        return additionalstatementReportFiles1;
    }

    public void setAdditionalAppraisalReportFiles1(FormFile additionalAppraisalReportFiles1)
    {
        this.additionalAppraisalReportFiles1 = additionalAppraisalReportFiles1;
    }

    public FormFile getAdditionalAppraisalReportFiles1()
    {
        return additionalAppraisalReportFiles1;
    }

    public void setAdditionalSanctionLetterFiles1(FormFile additionalSanctionLetterFiles1)
    {
        this.additionalSanctionLetterFiles1 = additionalSanctionLetterFiles1;
    }

    public FormFile getAdditionalSanctionLetterFiles1()
    {
        return additionalSanctionLetterFiles1;
    }

    public void setAdditionalComplianceReportFiles1(FormFile additionalComplianceReportFiles1)
    {
        this.additionalComplianceReportFiles1 = additionalComplianceReportFiles1;
    }

    public FormFile getAdditionalComplianceReportFiles1()
    {
        return additionalComplianceReportFiles1;
    }

    public void setAdditionalPreInspectionReportFiles1(FormFile additionalPreInspectionReportFiles1)
    {
        this.additionalPreInspectionReportFiles1 = additionalPreInspectionReportFiles1;
    }

    public FormFile getAdditionalPreInspectionReportFiles1()
    {
        return additionalPreInspectionReportFiles1;
    }

    public void setAdditionalInsuranceCopyFiles1(FormFile additionalInsuranceCopyFiles1)
    {
        this.additionalInsuranceCopyFiles1 = additionalInsuranceCopyFiles1;
    }

    public FormFile getAdditionalInsuranceCopyFiles1()
    {
        return additionalInsuranceCopyFiles1;
    }

    public void setAdditionalstatementReportFiles2(FormFile additionalstatementReportFiles2)
    {
        this.additionalstatementReportFiles2 = additionalstatementReportFiles2;
    }

    public FormFile getAdditionalstatementReportFiles2()
    {
        return additionalstatementReportFiles2;
    }

    public void setAdditionalAppraisalReportFiles2(FormFile additionalAppraisalReportFiles2)
    {
        this.additionalAppraisalReportFiles2 = additionalAppraisalReportFiles2;
    }

    public FormFile getAdditionalAppraisalReportFiles2()
    {
        return additionalAppraisalReportFiles2;
    }

    public void setAdditionalSanctionLetterFiles2(FormFile additionalSanctionLetterFiles2)
    {
        this.additionalSanctionLetterFiles2 = additionalSanctionLetterFiles2;
    }

    public FormFile getAdditionalSanctionLetterFiles2()
    {
        return additionalSanctionLetterFiles2;
    }

    public void setAdditionalComplianceReportFiles2(FormFile additionalComplianceReportFiles2)
    {
        this.additionalComplianceReportFiles2 = additionalComplianceReportFiles2;
    }

    public FormFile getAdditionalComplianceReportFiles2()
    {
        return additionalComplianceReportFiles2;
    }

    public void setAdditionalPreInspectionReportFiles2(FormFile additionalPreInspectionReportFiles2)
    {
        this.additionalPreInspectionReportFiles2 = additionalPreInspectionReportFiles2;
    }

    public FormFile getAdditionalPreInspectionReportFiles2()
    {
        return additionalPreInspectionReportFiles2;
    }

    public void setAdditionalInsuranceCopyFiles2(FormFile additionalInsuranceCopyFiles2)
    {
        this.additionalInsuranceCopyFiles2 = additionalInsuranceCopyFiles2;
    }

    public FormFile getAdditionalInsuranceCopyFiles2()
    {
        return additionalInsuranceCopyFiles2;
    }

    public void setAdditionalstatementReportFiles3(FormFile additionalstatementReportFiles3)
    {
        this.additionalstatementReportFiles3 = additionalstatementReportFiles3;
    }

    public FormFile getAdditionalstatementReportFiles3()
    {
        return additionalstatementReportFiles3;
    }

    public void setAdditionalAppraisalReportFiles3(FormFile additionalAppraisalReportFiles3)
    {
        this.additionalAppraisalReportFiles3 = additionalAppraisalReportFiles3;
    }

    public FormFile getAdditionalAppraisalReportFiles3()
    {
        return additionalAppraisalReportFiles3;
    }

    public void setAdditionalSanctionLetterFiles3(FormFile additionalSanctionLetterFiles3)
    {
        this.additionalSanctionLetterFiles3 = additionalSanctionLetterFiles3;
    }

    public FormFile getAdditionalSanctionLetterFiles3()
    {
        return additionalSanctionLetterFiles3;
    }

    public void setAdditionalComplianceReportFiles3(FormFile additionalComplianceReportFiles3)
    {
        this.additionalComplianceReportFiles3 = additionalComplianceReportFiles3;
    }

    public FormFile getAdditionalComplianceReportFiles3()
    {
        return additionalComplianceReportFiles3;
    }

    public void setAdditionalPreInspectionReportFiles3(FormFile additionalPreInspectionReportFiles3)
    {
        this.additionalPreInspectionReportFiles3 = additionalPreInspectionReportFiles3;
    }

    public FormFile getAdditionalPreInspectionReportFiles3()
    {
        return additionalPreInspectionReportFiles3;
    }

    public void setAdditionalInsuranceCopyFiles3(FormFile additionalInsuranceCopyFiles3)
    {
        this.additionalInsuranceCopyFiles3 = additionalInsuranceCopyFiles3;
    }

    public FormFile getAdditionalInsuranceCopyFiles3()
    {
        return additionalInsuranceCopyFiles3;
    }

    public void setBranchAddress1(FormFile branchAddress1[])
    {
        this.branchAddress1 = branchAddress1;
    }

    public FormFile[] getBranchAddress1()
    {
        return branchAddress1;
    }

    public String getCommHeadedByOfficerOrAbove()
    {
        return commHeadedByOfficerOrAbove;
    }

    public void setCommHeadedByOfficerOrAbove(String commHeadedByOfficerOrAbove)
    {
        this.commHeadedByOfficerOrAbove = commHeadedByOfficerOrAbove;
    }

    public ArrayList getClaimDandU()
    {
        return claimDandU;
    }

    public void setClaimDandU(ArrayList claimDandU)
    {
        this.claimDandU = claimDandU;
    }

    public ArrayList getClaimLodgedReport()
    {
        return claimLodgedReport;
    }

    public void setClaimLodgedReport(ArrayList claimLodgedReport)
    {
        this.claimLodgedReport = claimLodgedReport;
    }

    public FormFile[] getDiligenceReportFiles()
    {
        return diligenceReportFiles;
    }

    public void setDiligenceReportFiles(FormFile diligenceReportFiles[])
    {
        this.diligenceReportFiles = diligenceReportFiles;
    }

    public FormFile[] getPostInspectionReportFiles()
    {
        return postInspectionReportFiles;
    }

    public void setPostInspectionReportFiles(FormFile postInspectionReportFiles[])
    {
        this.postInspectionReportFiles = postInspectionReportFiles;
    }

    public FormFile[] getPostNpaReportFiles()
    {
        return postNpaReportFiles;
    }

    public void setPostNpaReportFiles(FormFile postNpaReportFiles[])
    {
        this.postNpaReportFiles = postNpaReportFiles;
    }

    public FormFile[] getIdProofFiles()
    {
        return idProofFiles;
    }

    public void setIdProofFiles(FormFile idProofFiles[])
    {
        this.idProofFiles = idProofFiles;
    }

    public FormFile[] getOtherFiles()
    {
        return otherFiles;
    }

    public void setOtherFiles(FormFile otherFiles[])
    {
        this.otherFiles = otherFiles;
    }

    public FormFile getInternalRatingFile()
    {
        return internalRatingFile;
    }

    public void setInternalRatingFile(FormFile internalRatingFile)
    {
        this.internalRatingFile = internalRatingFile;
    }
}