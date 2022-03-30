/*
 * Created on Sep 26, 2000
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.cgtsi.claim;

/**
 * @author VT8150
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface ClaimConstants {

public static final String ACCEPT="Approve";
public static final String REJECT="Reject";
public static final String FORWARD="Forward";
public static final String HOLD="Hold";
public static final String PENDING="Pending";

public static final String INSTALLMENT_FLAG = "INSTALLMENTFLAG";
public static final String FIRST_INSTALLMENT= "F";
public static final String SECOND_INSTALLMENT= "S";

public static final String CLM_BANK_ID = "BANKID";
public static final String CLM_MEMBER_ID = "MEMBERID";
public static final String CLM_MEMBER_NAME = "MEMBERNAME";
public static final String CLM_BORROWER_ID = "BORROWERID";
public static final String CLM_CLAIM_REF_NUMBER = "ClaimRefNumber";
public static final String CLM_CGCLAN = "cgclan";
public static final String CLM_CGPAN = "CGPAN";

// For OTS Request and Claim Ref Number Details
public static final String CLM_APPLICATION_APPRVD_AMNT = "ApprovedAmount";
public static final String CLM_ENHANCED_APPRVD_AMNT = "EnhancedApprovedAmount";
public static final String CLM_TC_SANCTIONED_AMNT = "TCSanctionedAmnt";
public static final String CLM_WC_FB_SANCTIONED_AMNT = "WCFundBasedSanctionedAmnt";
public static final String CLM_WC_NFB_SANCTIONED_AMNT = "WCNFBSanctionedAmnt";
public static final String CLM_APPLICATION_APPRVD_DT = "APPLICATIONAPPROVEDDT";
public static final String CLM_GUARANTEE_START_DT = "GUARANTEESTARTDT";
public static final String CLM_TOTAL_DISBURSEMENT_AMNT = "TotalDisbursementAmnt";
public static final String CLM_LAST_DISBURSEMENT_DT = "LASTDSBRSMNTDT";
public static final String CLM_DT_OF_RELEASE_OF_WC = "DTOFRELEASEOFWC";
public static final String CLM_TOTAL_SERVICE_FEE = "TOTALSERVICEFEE";

    public static final String CLM_TOTAL_SERVICE_FEE_FOR_ONE_YEAR = "TOTALSERVICEFEEFORONEYEAR";


public static final String CLM_OTS_PROPOSED_AMNT_TOBEPAID_BY_BORROWER = "ProposedAmntPaid";
public static final String CLM_OTS_PROPOSED_AMNT_TOBESACRIFICED = "ProposedAmntSacrificed";
public static final String CLM_OTS_OS_AMNT_AS_ON_DT = "OutstandingAmntAsOnDate";
public static final String CLM_OTS_REASON_FOR_OTS = "ReasonForOTS";
public static final String CLM_OTS_WILLFUL_DEFAULTER ="WillfulDefaulter";
public static final String CLM_OTS_REQUEST_DATE = "OTSRequestDate";
public static final String CLM_OTS_TOTAL_BORROWER_PROPOSED_AMNT = "TotalBorrowerProposedAmnt";
public static final String CLM_OTS_TOTAL_PROPOSED_SCRFCE_AMNT = "TotalProposedSacrificeAmt";
public static final String CLM_OTS_TOTAL_OS_AMNT = "TotalOSAmnt";
public static final String CLM_RP_AMOUNT_RAISED = "ServiceFee";
public static final String CLM_RP_APPROPRIATION_DT = "ServiceFeeDate";
public static final String CLM_OS_AMT_AS_ON_NPA_DATE = "OSAmntAsOnNPADate";
public static final String CLM_LOCK_IN_PERIOD_END_DATE = "LockInPeriodEndDt";
public static final String CLM_FIRST_OTS_FROM_REC = "OTSFirstDtlFromRecovery";
public static final String CLM_SECOND_OTS_FROM_REC = "OTSSecondDtlFromRecovery";
public static final String CLM_ITPAN_OF_CHIEF_PROMOTER = "Clm_ITPAN_of_Chief_Promoter";

// For capturing NPA Details
public static final String NPA_CLASSIFIED_DT = "NPAClassifiedDate";
public static final String NPA_REPORTING_DT = "NPAReportingDate";
public static final String REASONS_FOR_TURNING_NPA = "ReasonforturningNPA";
public static final String WILLFUL_DEFAULTER = "WillFulDefaulter";
public static final String WHETHER_NPA_WRITTEN_OFF = "WhetherNPAWrittenOff";
public static final String NPA_WRITTEN_OFF_DATE = "NPAWrittenOffDt";
public static final String NPA_REC_CONCLUSION_DT = "NPARecConclusionDt";

// For capturing Recovery Details
public static final String RECOVERY_RCVRD_AMNT = "RecoveredAmount";
public static final String RECOVERY_DT_OF_RECOVERY = "DateOfRecovery";
public static final String RECOVERY_LEGAL_CHARGES_INCURRED = "LegalChargesIncurred";
public static final String RECOVERY_IS_RECOVERY_THRU_OTS = "IsRecoverybywayofOTS";
public static final String RECOVERY_ISRECOVERY_THRU_ASSETS = "IsRecoverybywayofAssets";
public static final String RECOVERY_DTLS_OF_ASSETS_SOLD = "Detailsofassetssold";
public static final String RECOVERY_REMARKS = "remarks";
public static final String TOTAL_AMNT_REPAID = "AMNT_REPAID";

// For capturing cgpan details
public static final String CGPAN_APPRVD_AMNT = "ApprovedAmount";
public static final String CGPAN_ENHANCED_APPRVD_AMNT = "EnhancedApprovedAmount";
public static final String CGPAN_LOAN_TYPE = "LoanType";
public static final String CGPAN_TC_LOAN_TYPE = "TC";
public static final String CGPAN_WC_LOAN_TYPE = "WC";
public static final String CGPAN_CC_LOAN_TYPE = "CC";
public static final String CGPAN_BO_LOAN_TYPE = "BO";
public static final String CGPAN_CLM_APPLIED_AMNT = "ClaimAppliedAmnt";
public static final String CGPAN_SEC_CLM_APPLIED_AMNT = "SECClaimAppliedAmnt";

// For capturing Disbursement details
public static final String DISBRSMNT_YES_FLAG = "Y";
public static final String DISBRSMNT_NO_FLAG = "N";

// For capturing Security and Personal Guarantee Details
public static final String CLM_SAPGD_AS_ON_SANCTION_CODE ="SAN";
public static final String CLM_SAPGD_AS_ON_NPA_CODE="NPA";
public static final String CLM_SAPGD_AS_ON_LODGE_OF_CLM = "CLM";
public static final String CLM_SAPGD_AS_ON_LODGE_OF_SEC_CLM = "SCLM";
public static final String CLM_SAPGD_PARTICULAR_LAND = "LAND";
public static final String CLM_SAPGD_PARTICULAR_BLDG = "BUILDING";
public static final String CLM_SAPGD_PARTICULAR_MC = "MACHINE";
public static final String CLM_SAPGD_PARTICULAR_OTHER_FIXED_MOV_ASSETS = "OTHER FIXED MOVABLE ASSETS";
public static final String CLM_SAPGD_PARTICULAR_CUR_ASSETS = "CURRENT ASSETS";
public static final String CLM_SAPGD_PARTICULAR_OTHERS = "OTHERS";
public static final String CLM_SAPGD_REASONS_FOR_REDUCTION = "reasonReduction";
public static final String CLM_SAPGD_NETWORTH_OF_GUARANTOR = "networth";
public static final String CLM_SECURITY_ID = "SecurityId";
public static final String CLM_SECURITY_PARTICULAR_FLAG = "SecurityParticularFlag";
public static final String CLM_AMOUNT_REALIZED_THRU_SEC = "AmntRealizedThruSecurity";


// For Claim Approval Page
public static final String APPLICATION_STATUS = "APPLICATION_STATUS";
public static final String CLM_APPROVAL_STATUS = "AP";
public static final String CLM_REJECT_STATUS = "RE";

//added by CLM_TEMPORARY_CLOSE and CLM_TEMPORARY_REJECT by sukumar@pathinfotech for temporary rejection and closure of claim applications
public static final String CLM_TEMPORARY_CLOSE = "TC";
public static final String CLM_TEMPORARY_REJECT = "TR";

//added by CLM_WITHDRAWN by sukumar@pathinfotech for claim withdrawn of claim applications
public static final String CLM_WITHDRAWN = "WD";


public static final String CLM_HOLD_STATUS = "HO";
public static final String CLM_FORWARD_STATUS = "FW";
public static final String CLM_PENDING_STATUS = "NE";
public static final String CLM_FIRST_CLM_APPRVD_AMT = "CLMAPPRVDAMT";
public static final String CLM_FIRST_CLM_APPRVD_DT = "CLMAPPRVDDT";

// For Displaying values in the Claim Approval Screen
public static final String CLM_AS_ON_NPA = "As on NPA";
public static final String CLM_RECOVERIES_AFTER_NPA = "Recoveries after NPA";

// For Storing the Recovery object in the session
public static final String CLM_RECOVERY_OBJECT = "RECOVERYOBJECT";

// For Settlement Details page
public static final String CLM_NO_VALUE = "-";

// For Settlement Advice Filter Page
public static final String CLM_STLMNT_MEMBER_ALL = "All";
public static final String CLM_STLMNT_MEMBER_SPECIFIC = "Specific";

// Delimiters and Flags
public static final String CLM_DELIMITER1 = "#";
public static final String CLM_DELIMITER4 = "$";
public static final String CLM_DELIMITER2 = "-";
public static final String CLM_DELIMITER3 = "/";
public static final String CLM_FLAG = "FLAG";
public static final String CLM_TAG = "CLAIM";
public static final String CLM_EXTENSION = ".EXP";
public static final String CLM_DELIMITER5 = "^";

// For Settlement Advice Details
public static final String CLM_SETTLMNT_FIRST_SETTLMNT_AMNT = "FirstSettlmntAmnt";
public static final String CLM_SETTLMNT_FIRST_SETTLMNT_DT = "FirstSettlmntDt";
public static final String CLM_SETTLMNT_SECOND_SETTLMNT_AMNT = "SecondSettlmntAmnt";
public static final String CLM_TOTAL_SETTLMNT_AMNT = "TotalSettlementAmnt";
public static final String CLM_SETTLMNT_SECOND_SETTLMNT_DT = "SecondSettlmntDt";
public static final String CLM_CGCSA = "CGCSA";
public static final String CLM_VOUCHER_ID = "VOUCHERID";
public static final String CLM_SETTLMNT_ADVICE_GENERATE_OPTION = "SettlmntAdviceGenerateOption";
public static final String CLM_SETTLMNT_OF_1st_CLM = "Towards Settlement of 1st Claim for :";
public static final String CLM_SETTLMNT_OF_2nd_CLM = "Towards Settlement of 2nd Claim for :";
public static final String CLM_PAID_TO = "PAID TO :";

// For capturing Settlement Advice Checkbox
public static final String CLM_CHECKBOX_YES = "on";
public static final String CLM_CHECKBOX_NO = "off";

// For uploading the attachments
public static final String CLM_RECALL_NOTICE = "RECALL-";
public static final String CLM_LEGAL_ATTACHMENT = "LEGAL-";

//Diksha 30/05/2017
//bhu upload in claim form staffAc 23062015
/*public static final String CLM_STAFF_AC_ATTACHMENT = "STAFFAC";


public static final String CLM_SANCTION_LETTER_ATTACHMENT = "SANCTIONLETTER";


public static final String CLM_SANCTION_LETTER_ATTACHMENT1 = "SANCTIONLETTER1";//bhu

public static final String CLM_SANCTION_LETTER_ATTACHMENT2 = "SANCTIONLETTER2";//bhu

*/

// For storing NPA Details
public static final String CLM_MAIN_TABLE = "MAIN";
public static final String CLM_TEMP_TABLE = "TEMP";

// For Validating Recovery Details
public static final String CLM_REC_TC_PRINCIPAL = "TCPRINCIPAL";
public static final String CLM_REC_TC_INTEREST = "TCINTEREST";
public static final String CLM_REC_WC_AMOUNT = "WC_AMOUNT";
public static final String CLM_REC_WC_OTHER = "WC_OTHER";
public static final String CLM_REC_MODE = "REC_MODE";
public static final String CLM_REC_FLAG = "REC_FLAG";
public static final String WC_TENURE = "WC_TENURE";

public static final String CLM_MAX_APPROVAL_AMOUNT = "Max Claim Approved Amount";
public static final String CLM_VALID_FROM_DT = "Claim Valid From Date";
public static final String CLM_VALID_TO_DT = "Claim Valid To Date";
public static final String CLM_CP_FLAG = "Claims Processing";
public static final String CLM_WHICH_MENU = "Which Menu";

public static final String CLM_OS_AS_ON_NPA = "OS as on NPA";
public static final String CLM_OS_AS_ON_CIVIL_SUIT = "OS as in Civil Suit";
public static final String CLM_OS_AS_IN_CLM_LODGMNT = "OS as in Clm Logdmnt";

    //added by upchar@path on 03/07/2013
     public static final String CLM_REPLY_RECEIVED="RR";
}
