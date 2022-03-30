package com.cgtsi.thinclient;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.UIManager;
import javax.swing.filechooser.*;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Vector;
import java.util.Set;
import java.util.Iterator;
import java.util.Date;
import java.util.StringTokenizer;

import java.text.SimpleDateFormat;
import java.text.ParsePosition;
import java.text.DecimalFormat;

/*******************************************************************
   *
   * Name of the class: ThinClient.java
   * This class is the main class that implements the functionality
   * of Thin Client module.
   *  
   * @author : Nithyalakshmi P
   ********************************************************************/
public class ThinClient extends JFrame implements ActionListener, ItemListener {
	
	JDialog dialog;
	JDialog abtDialog;
	
	Container container;
	JPanel panel;
	JPanel infoPanel;
	JPanel helpPanel;
	JMenuBar menuBar;
	JMenu menu;
	JMenu subMenu;
	JMenuItem menuItem;
	JScrollPane scrollPane;
	Icon icon;

	int noOfDisbursements[];
	int noOfRepayments[];
	int noOfActions;
	int noOfOutstandings[];
	boolean mcgfFlag;

	//Fields for Application screen
	JPanel commonPanel;
	JPanel borrowerPanel;
	JPanel unitDetailsPanel;
	JPanel promotersPanel;
	JPanel otherPromotersPanel;
	JPanel facilityPanel;
	JPanel tcPanel;
	JPanel wcPanel;
	JPanel wcEnhancementPanel;
	JPanel wcRenewalPanel;
	JPanel securitisationPanel;
	JPanel mcgfPanel;

	JLabel projectDetails;
	JLabel collateralSecurity;
	JLabel collateralSecurityHint;
	JLabel thirdPartyGuarantee;
	JLabel thirdPartyGuaranteeHint;
	JLabel guarantors;
	JLabel guarantorName;
	JLabel guarantorNetworth;
	JLabel supportName;
	JLabel primarySecurity;
	JLabel securityName;
	JLabel particulars;
	JLabel value;
	JLabel land;
	JLabel building;
	JLabel machine;
	JLabel otherAssets;
	JLabel currentAssets;
	JLabel others;
	JLabel securityTotal;
	JLabel securityTotalValue;
	JLabel meansOfFinance;
	JLabel termCredit;
	JLabel workingCapital;
	JLabel fundBased;
	JLabel fbHint;
	JLabel nonFundBased;
	JLabel marginMoney;
	JLabel tcPromotersContribution;
	JLabel wcPromotersContribution;
	JLabel tcSupport;
	JLabel wcSupport;
	JLabel tcOthers;
	JLabel wcOthers;
	JLabel projectCost;
	JLabel wcAssessed;
	JLabel projectOutlay;
	JLabel projectCostHint;
	JLabel projectOutlayHint;
	JLabel blankLabel;

	JLabel appHeading;
	JLabel bankRef;
	JLabel bankBranch;
	JLabel branchCode;
	JLabel borrowerHeading;
	JLabel borrowerCovered;
	JLabel borrowerAssisted;
	JLabel osAmt;
	JLabel uniqueAccNo;
	JLabel npa;
	JLabel unitDetailsHeading;
	JLabel constitution;
	JLabel unitName;
	JLabel ssiRegNo;
	JLabel dateOfCommencement;
	JLabel itpanFirm;
	JLabel indNature;
	JLabel indSector;
	JLabel activityType;
	JLabel noOfEmp;
	JLabel optSalesTurnOver;
	JLabel optExports;
	JLabel address;
	JLabel state;
	JLabel district;
	JLabel city;
	JLabel pinCode;
	JLabel promoterHeading;
	JLabel chiefPromoterInfo;
	JLabel title;
	JLabel firstName;
	JLabel middleName;
	JLabel lastName;
	JLabel name;
	JLabel gender;
	JLabel itpanChiefPromoter;
	JLabel dob;
	JLabel socialCategory;
	JLabel legalId;
	JLabel otherId;
	JLabel id;
	JLabel plsSpecify;
	JLabel otherPromoters;
	JLabel promoterName;
	JLabel itpanPromoter;
	JLabel dobPromoter;

	JComboBox cmbSocialCategory;

	JLabel facilityHeading;
	JLabel rehabilitation;
	JLabel composite;
	JLabel compositeValue;
	JLabel loanFacility;
	JLabel loanFacilityValue;

	JLabel termCreditHeading;
	JLabel amtSanctioned;
	JLabel dateOfSanction;
	JLabel creditGuarantee;
	JLabel amtDisbursed;
	JLabel dateOfFirstDisbursement;
	JLabel dateOfLastDisbursement;
	JLabel tenure;
	JLabel intType;
	JLabel intRate;
	JLabel plr;
	JLabel plrType;
	JLabel repaymentSchedule;
	JLabel moratorium;
	JLabel firstInstDueDate;
	JLabel periodicity;
	JLabel noOfInstallments;

	JLabel wcHeading;
	JLabel limitSanctioned;
	JLabel interest;
	JLabel commission;
	JLabel osFundBased;
	JLabel osNonFundBased;
	JLabel remarks;
	JLabel schemeName;

	JLabel wcEnhancementHeading;
	JLabel enhancedFundBased;
	JLabel enhancedInterest;
	JLabel existingInterestValue;
	JLabel dtOfEnhancement;
	JLabel existingRemarks;
	JLabel enhancedFundBasedValue;
	JLabel enhancedNonFundBasedValue;
	JLabel existingRemarksValue;
	JLabel total;
	JLabel existingTotalValue;
	JLabel enhancedTotalValue;

	JLabel wcRenewalHeading;
	JLabel renewedFundBased;
	JLabel renewedFundBasedValue;
	JLabel renewedInterest;
	JTextField txtRenewedInterest;
	JLabel renewedNonFundBasedValue;
	JTextField txtRenewedCommission;	
	JLabel dtOfRenewal;
	JTextField txtDtOfRenewal;
	JLabel renewedTotalValue;

	JLabel securitisationHeading;
	JLabel spreadOverPLR;
	JTextField txtSpreadOverPLR;
	JLabel principalRepayments;
	JRadioButton repayInEqualInstYes;
	JRadioButton repayInEqualInstNo;
	ButtonGroup repayInEqualInstValue;
	JPanel repayInEqualInstPanel;
	JLabel tangibleNetworth;
	JTextField txtTangibleNetworth;
	JLabel fixedAssetCoverageRatio;
	JTextField txtFixedAssetCoverageRatio;
	JLabel currentRatio;
	JTextField txtCurrentRatio;
	JLabel finPartOfUnit;
	JLabel finPartOfUnitHint;
	JTextField txtFinPartOfUnit;
	JLabel minDSCR;
	JTextField txtMinDSCR;
	JLabel avgDSCR;
	JTextField txtAvgDSCR;

	JLabel mcgfHeading;
	JLabel participatingBankName;
	JComboBox cmbParticipatingBanks;
	JLabel participatingBankBranch;
	JTextField txtPartBankBranch;
	JLabel mcgfId;
	JComboBox cmbMcgfId;
	JLabel mcgfName;
	JLabel mcgfNameValue;
	JLabel amtApprovedByMcgf;
	JTextField txtAmtApprovedByMcgf;
	JTextField txtMcgfDistrict;
	JLabel approvedDate;
	JTextField txtApprovedDate;
	JLabel gCoverStartDate;
	JTextField txtGCoverStartDate;

	JRadioButton collateralYes;
	JRadioButton collateralNo;
	JRadioButton thirdPartyYes;
	JRadioButton thirdPartyNo;
	ButtonGroup collateral;
	ButtonGroup thirdParty;

	JRadioButton borrowerCoveredYes;
	JRadioButton borrowerCoveredNo;
	JRadioButton borrowerAssistedYes;
	JRadioButton borrowerAssistedNo;
	ButtonGroup borrowerCoveredValue;
	ButtonGroup borrowerAssistedValue;
	JPanel borrowerAssistedPanel;
	JPanel borrowerCoveredPanel;

	JRadioButton npaYes;
	JRadioButton npaNo;
	ButtonGroup npaValue;
	JPanel npaPanel;

	JRadioButton cgpan;
	JRadioButton none;
	JRadioButton cgbid;
	ButtonGroup ssiValue;
	JPanel ssiPanel;
	JTextField txtSsiValue;

	JComboBox cmbConstitution;
	JRadioButton proprietary;
	JRadioButton partnership;
	JRadioButton publicLtd;
	JRadioButton pvtLtd;
	JRadioButton otherConst;
	ButtonGroup constitutionValue;
	JPanel constitutionPanel;

	JRadioButton male;
	JRadioButton female;
	ButtonGroup genderValue;
	JPanel genderPanel;

	JRadioButton rehabilitationYes;
	JRadioButton rehabilitationNo;
	ButtonGroup rehabilitationValue;
	JPanel rehabilitationPanel;

	JRadioButton interestFixed;
	JRadioButton interestFloating;
	ButtonGroup interestValue;
	JPanel interestPanel;

	JRadioButton marginMoneyYes;
	JRadioButton marginMoneyNo;
	ButtonGroup marginMoneyValue;
	JPanel marginMoneyPanel;

	JTextField txtBankRefNo;
	JTextField txtBankBranchName;
	JTextField txtBranchCode;
	JTextField txtOSAmt;
	JTextField txtUniqueAccNo;
	JTextField txtCgpanCgbid;
	JTextField txtConstitution;
	JTextField txtUnitName;
	JTextField txtSsiRegNo;
	JTextField txtFirmItpan;
	JTextField txtActivity;
	JTextField txtNoOfEmp;
	JTextField txtOptSalesTurnover;
	JTextField txtOptExports;
	JTextField txtDistrict;
	JTextField txtCity;
	JTextField txtPincode;
	JTextField txtFirstName;
	JTextField txtMiddleName;
	JTextField txtLastName;
	JTextField txtChiefPromoterItpan;
	JTextField txtDOB;
	JTextField txtOtherId;
	JTextField txtLegalValue;
	JTextField txtOtherPromotersName1;
	JTextField txtOtherPromotersName2;
	JTextField txtOtherPromotersName3;
	JTextField txtOtherPromotersItpan1;
	JTextField txtOtherPromotersItpan2;
	JTextField txtOtherPromotersItpan3;
	JTextField txtOtherPromotersDob1;
	JTextField txtOtherPromotersDob2;
	JTextField txtOtherPromotersDob3;
	JTextArea txtAddress;

	JComboBox cmbUnitNames;
	JComboBox cmbIndNature;
	JComboBox cmbIndSector;
	JComboBox cmbState;
	JComboBox cmbDistrict;
	JComboBox cmbTitle;
	JComboBox cmbLegalId;

	JTextField txtGuarantorName1;
	JTextField txtGuarantorName2;
	JTextField txtGuarantorName3;
	JTextField txtGuarantorName4;
	JTextField txtGuarantorNetworth1;
	JTextField txtGuarantorNetworth2;
	JTextField txtGuarantorNetworth3;
	JTextField txtGuarantorNetworth4;
	JTextField txtSupportName;
	JTextField txtLandParticulars;
	JTextField txtBuildingParticulars;
	JTextField txtMachineParticulars;
	JTextField txtOtherAssetsParticulars;
	JTextField txtCurrentAssetsParticulars;
	JTextField txtOthersParticulars;
	JTextField txtLandValue;
	JTextField txtBuildingValue;
	JTextField txtMachineValue;
	JTextField txtOtherAssetsValue;
	JTextField txtCurrentAssetsValue;
	JTextField txtOthersValue;
	JTextField txtTermCredit;
	JTextField txtWCFundBased;
	JTextField txtWCNonFundBased;
	JTextField txtTCPromotersContribution;
	JTextField txtWCPromotersContribution;
	JTextField txtTCSupport;
	JTextField txtWCSupport;
	JTextField txtTCOthers;
	JTextField txtWCOthers;
	JLabel projectCostValue;
	JLabel wcAssessedValue;
	JLabel projectOutlayValue;
	JComboBox cmbSupportNames;
	JPanel collateralPanel;
	JPanel thirdPartyPanel;

	JLabel amtSanctionedValue;
	JTextField txtDateOfSanction;
	JTextField txtCreditToBeGuaranteed;
	JTextField txtAmtDisbursed;
	JTextField txtDateOfFirstDisbursement;
	JTextField txtDateOfLastDisbursement;
	JTextField txtTenure;
	JTextField txtTcInterestRate;
	JTextField txtPlr;
	JTextField txtPlrType;
	JTextField txtMoratorium;
	JTextField txtFirstInstDueDate;
	JTextField txtNoOfInstallments;
	JTextField txtTcOsAmt;
	JTextField txtOsAsOn;

	JLabel fbLimitSanctionedValue;
	JTextField txtFBInterest;
	JTextField txtFBSanctionedDate;
	JLabel nfbLimitSanctionedValue;
	JTextField txtNFBCommission;
	JTextField txtNFBSanctionedDate;
	JTextField txtOsFBPrincipal;
	JTextField txtOsFBAsOn;
	JTextField txtOsNFBPrincipal;
	JTextField txtOsNFBCommission;
	JTextField txtOsNFBAsOn;
	JTextField txtFBCredit;
	JTextField txtNFBCredit;
	JTextField txtWcInterestRate;
	JTextField txtWcPlr;
	JTextField txtWcPlrType;
	JTextArea txtRemarks;

	JTextField txtEnhancedInterest;
	JTextField txtEnhancedCommission;	
	JTextField txtDtOfEnhancement;

	JComboBox cmbPeriodicity;

	//Fields for Verify screen
	JLabel fileDetails;
	JLabel fileLocation;
	JLabel path;
	JRadioButton floppy;
	JRadioButton hardDisk;
	JRadioButton noFile;
	JRadioButton exportFile;
	JRadioButton existingFile;
	ButtonGroup location;
	JFileChooser filePath;
	JTextField txtFilePath;
	JButton browse;

	//Fields for Periodic Info screen
	JLabel borrowerId;
	JLabel borrowerIdValue;
	JLabel borrowerName;
	JLabel borrowerNameValue;
	JLabel pcgpan;
	JLabel osCgpanValue[];
	JLabel disCgpanValue[];
	JLabel repayCgpanValue[];
	JLabel scheme;
	JLabel schemeValue[];
	JLabel outstandingDetails;
	JLabel slNo;
	JLabel outstanding;
	JLabel asOnDate;
	JLabel totalOutstanding;
	JLabel facility;
	JLabel sanctioned;
	JLabel principal;
	JLabel intCommission;
	JLabel slNoValue[];
	JLabel radIdValue[];
	JLabel facilityValue[];
	JLabel tc;
	JLabel wcFB;
	JLabel wcNFB;
	JLabel sanctionedTC[];
	JLabel sanctionedWCFB[];
	JLabel sanctionedWCNFB[];
	JLabel tcoIdValue[];
	JLabel wcoIdValue[];
	JTextField txtTCPrincipal[];
	JTextField txtWCFBPrincipal[];
	JTextField txtWCNFBPrincipal[];
	JTextField txtWCFBIntComm[];
	JTextField txtWCNFBIntComm[];
	JTextField txtTCAsOnDate[];
	JTextField txtWCFBAsOnDate[];
	JTextField txtWCNFBAsOnDate[];
	JLabel totalOutstandingAmtValue[];
	JLabel totalNFBOutstandingAmtValue[];

	//Fields for Application List screen
	JLabel fileList;
	JRadioButton TCApplication;
	JRadioButton WCApplication;
	JRadioButton CCApplication;
	JRadioButton BothApplication;
	ButtonGroup files;

	JLabel recActionsListHeading;
	ButtonGroup recIdGroup;

	JLabel sanctionedAmt;

	//Fields for periodic info outstanding details
	JPanel outstandingPanel;
	JLabel outstandingHeading;
	JLabel outstandingAmt;
	JLabel principalAmt;
	JLabel interestComm;
	JTextField txtTcPrincipal;
	JTextField txtWcFBPrincipal;
	JTextField txtWcNFBPrincipal;
	JTextField txtWcFBInterestComm;
	JTextField txtWcNFBInterestComm;
	JTextField txtTcAsOnDate;
	JTextField txtWcFBAsOnDate;
	JTextField txtWcNFBAsOnDate;
	JTextField txtTotalOutstanding;

	//Fields for periodic info disbursement details
	JPanel disbursementPanel;
	JLabel disbursementHeading;
	JLabel disbursementAmt;
	JLabel finalDisbursement;
	JLabel totalDisbursementAmt;
	JLabel sanctionedAmtValue[];
	JLabel disbursementAmtValue[];
	JLabel disbursementDateValue[];
	JLabel disIdValue[];
	JTextField txtDisbursementAmt[];
	JTextField txtDisbursementDate[];
	JCheckBox chkFinalDisbursement[];
	JTextField txtTotalDisbursementAmt[];
	JLabel totalDisbursementAmtValue[];

	//Fields for repayment details
	JPanel repaymentPanel;
	JLabel repaymentHeading;
	JLabel repaymentAmt;
	JLabel date;
	JLabel totalRepaymentAmt;
	JLabel repaymentAmtValue[];
	JLabel repaymentDate[];
	JLabel repayIdValue[];
	JTextField txtRepaymentAmt[];
	JTextField txtRepaymentDate[];
	JLabel totalRepaymentAmtValue[];

	//Fields for NPA details
	JPanel npaDetailsPanel;
	JPanel npaRecoveryPanel;
	JPanel npaLegalSuitPanel;
	JPanel npaOthersPanel;
	JLabel npaHeading;
	JLabel npaDate;
	JLabel osAmtAsOnDate;
	JLabel npaReported;
	JLabel reportingDate;
	JLabel reasons;
	JLabel willfulDefaulter;
	JLabel efforts;
	JLabel recoveryProcedure;
	JLabel procedureInitiated;
	JLabel actionType;
	JLabel details;
	JLabel attachement;
	JLabel legalSuitDetails;
	JLabel legalProceedingsDetails;
	JLabel forum;
	JLabel suitRegNo;
	JLabel forumName;
	JLabel amtClaimed;
	JLabel currentStatus;
	JLabel proceedingsConcluded;
	JLabel expectedDateOfConclusion;
	JLabel mliComment;
	JLabel financialAssistanceDetails;
	JLabel mliPropose;
	JLabel bankFacilityDetails;
	JLabel mliAdvise;
	JLabel npaIdValue;
	JLabel legalIdValue;
	JTextField txtNpaDate;
	JTextField txtOsAmtAsOnNpaDate;
	JRadioButton npaReportedYes;
	JRadioButton npaReportedNo;
	JPanel npaReportedPanel;
	ButtonGroup npaReportedValue;
	JTextField txtReportingDate;
	JTextArea txtReasons;
	JRadioButton willfulDefaulterYes;
	JRadioButton willfulDefaulterNo;
	JPanel willfulDefaulterPanel;
	ButtonGroup willfulDefaulterValue;
	JTextArea txtEfforts;
	JRadioButton procInitiatedYes;
	JRadioButton procInitiatedNo;
	JPanel procInitiatedPanel;
	ButtonGroup procInitiatedValue;
	JComboBox cmbActionType[];
	JTextArea txtDetails[];
	JTextField txtDate[];
	JTextField txtAttachment[];
	JButton attachBrowse[];
	JComboBox cmbForumNames;
	JTextField txtForumName;
	JTextField txtSuitCaseRegNo;
	JTextField txtLegalProcDate;
	JTextField txtForumNameLoc;
	JTextField txtAmtClaimed;
	JTextArea txtCurrentStatus;
	JRadioButton recoveryProcConcludedYes;
	JRadioButton recoveryProcConcludedNo;
	JPanel recoveryProcConcludedPanel;
	ButtonGroup recoveryProcConcludedValue;
	JTextField txtEffortConclusionDate;
	JTextArea txtMliComment;
	JTextArea txtFinancialAssistanceDetails;
	JRadioButton creditSupportYes;
	JRadioButton creditSupportNo;
	JPanel creditSupportPanel;
	ButtonGroup creditSupportValue;
	JTextArea txtBankFacilityDetails;
	JRadioButton watchListYes;
	JRadioButton watchListNo;
	JPanel watchListPanel;
	ButtonGroup watchListValue;

	//Fields for recovery details
	JPanel recoveryPanel;
	JLabel recoveryHeading;
	JLabel recoveryDate;
	JLabel amtRecovered;
	JLabel legalCharges;
	JLabel recoveryByOTS;
	JLabel recoveryByAssets;
	JLabel assetsDetails;
	JTextField txtRecoveryDate;
	JTextField txtAmtRecovered;
	JTextField txtLegalCharges;
	JRadioButton recoveryOTSYes;
	JRadioButton recoveryOTSNo;
	JPanel recoveryOTSPanel;
	ButtonGroup recoveryOTSValue;
	JRadioButton saleOfAssetsYes;
	JRadioButton saleOfAssetsNo;
	JPanel saleOfAssetsPanel;
	ButtonGroup saleOfAssetsValue;
	JTextArea txtAssetsDetails;
	JLabel recoveryNoValue;

// hidden label to indicate whether the periodic info details was from a export file or by choosing
// No File.
// T - Export file
// F - No File
	JLabel lblExport;

	//Fields for claims screen
	JLabel claimsHeading;
	JLabel claimAppRefNo;
	JTextField txtClaimAppRefNo;
	JTextField txtClaimDate;
	JLabel clpan;
	JTextField txtClpan;
	JLabel OperatingOffHeading;
	JPanel memberInfoPanel;
	JLabel memberId;
	JLabel memberIdValue;
	JLabel lendingBranch;
	JLabel lendingBranchValue;
	JLabel districtValue;
	JLabel cityValue;
	JLabel stateValue;
	JLabel telNo;
	JLabel telNoValue;
	JLabel email;
	JLabel emailValue;
	JPanel borrowerDetailsPanel;
	JLabel unitNameValue;
	JLabel completeAddress;
	JLabel completeAddressValue;
	JLabel accStatusHeading;
	JPanel accStatusPanel;
	JLabel accNPADate;
	JLabel accNPADateValue;
	JLabel reportingDateValue;
	JLabel reasonsValue;
	JLabel dateOfIssueOfRecall;
	JTextField txtDateOfIssueOfRecall;
	JPanel legalProceedingsPanel;
	JTextField txtForumLocation;
	JLabel forumLocation;
	JLabel termCompLoanHeading;
	JPanel termCompPanel;
	JLabel cgpanCol;
	JLabel cgpanColValueTC[];
	JLabel dateOfLastDisbursementCol;
	JLabel dateOfLastDisbursementCol1;
	JTextField txtDateOfLastDisbursementCol[];
	JLabel repaymentCol;
	JLabel principalCol;
	JLabel interestCol;
	JLabel interestCol1;
	JTextField txtPrincipal[];
	JTextField txtInterest[];
	JLabel outstandingOnNpaCol;
	JLabel outstandingOnNpaCol1;
	JLabel outstandingOnNpaCol2;
	JTextField txtOutstandingOnNpaTC[];
	JLabel outstandingInCaseCol;
	JLabel outstandingInCaseCol1;
	JLabel outstandingInCaseCol2;
	JLabel outstandingInCaseCol3;
	JTextField txtOutstandingInCaseTC[];
	JLabel outstandingOnLodgementCol;
	JLabel outstandingOnLodgementCol1;
	JLabel outstandingOnLodgementCol2;
	JTextField txtOutstandingOnLodgementTC[];
	JTextField txtOutstandingOnLodgementTCSecond[];
	JLabel osTermCompHint;
	JLabel firstInstallmentCol;
	JLabel firstInstallmentCol1;
	JLabel secondInstallmentCol;
	JLabel secondInstallmentCol1;
	JLabel firstInstallmentColWC;
	JLabel firstInstallmentColWC1;
	JLabel secondInstallmentColWC;
	JLabel secondInstallmentColWC1;

	JLabel wcLimitHeading;
	JPanel wcLimitPanel;
	JLabel cgpanColValueWC[];
	JTextField txtOutstandingOnNpaWC[];
	JTextField txtOutstandingInCaseWC[];
	JTextField txtOutstandingOnLodegementWC[];
	JTextField txtOutstandingOnLodegementWCSecond[];
	JLabel osWCLimitHint;

	JPanel wcReleasePanel;
	JLabel dateOfRelease;
	JTextField txtDateOfRelease;

	JLabel securityHeading;
	JPanel securityPersonalGuaranteePanel;
	JPanel securityPanel;
	JLabel personalGuaranteeHeading;
	JPanel personalGuaranteePanel;
	JLabel particularsCol;
	JLabel securityCol;
	JLabel amtRealisedSec;
	JLabel amtRealisedSec1;
	JLabel amtRealisedSec2;
	JLabel amtRealisedPg;
	JLabel amtRealisedPg1;
	JLabel amtRealisedPg2;

	JLabel natureCol;
	JLabel natureCol1;
	JLabel valueCol;
	JLabel networthCol;
	JLabel networthCol1;
	JLabel networthCol2;
	JLabel reasonsForReductionCol;
	JLabel reasonsForReductionCol1;
	JLabel reasonsForReductionCol2;
	JLabel asOnSanction;
	JLabel asOnSanction1;
	JTextField txtAsOnSanctionLandValue;
	JTextField txtAsOnSanctionBuildingValue;
	JTextField txtAsOnSanctionMachineValue;
	JTextField txtAsOnSanctionFixedValue;
	JTextField txtAsOnSanctionCurrentValue;
	JTextField txtAsOnSanctionOthersValue;
	JTextField txtAsOnSanctionNetworth;
	JTextArea txtAsOnSanctionReasonsForReduction;
	JLabel asOnNpa;
	JTextField txtAsOnNpaLandValue;
	JTextField txtAsOnNpaBuildingValue;
	JTextField txtAsOnNpaMachineValue;
	JTextField txtAsOnNpaFixedValue;
	JTextField txtAsOnNpaCurrentValue;
	JTextField txtAsOnNpaOthersValue;
	JTextField txtAsOnNpaNetworth;
	JTextArea txtAsOnNpaReasonsForReduction;
	JLabel asOnLodgement;
	JLabel asOnLodgement1;
	JTextField txtAsOnLodgementLandValue;
	JTextField txtAsOnLodgementBuildingValue;
	JTextField txtAsOnLodgementMachineValue;
	JTextField txtAsOnLodgementFixedValue;
	JTextField txtAsOnLodgementCurrentValue;
	JTextField txtAsOnLodgementOthersValue;
	JTextField txtAsOnLodgementNetworth;
	JTextArea txtAsOnLodgementReasonsForReduction;
	JLabel asOnSecondClaim;
	JLabel asOnSecondClaim1;
	JTextField txtAsOnSecondClaimLandValue;
	JTextField txtAsOnSecondClaimBuildingValue;
	JTextField txtAsOnSecondClaimMachineValue;
	JTextField txtAsOnSecondClaimFixedValue;
	JTextField txtAsOnSecondClaimCurrentValue;
	JTextField txtAsOnSecondClaimOthersValue;
	JTextField txtAsOnSecondClaimNetworth;
	JTextArea txtAsOnSecondClaimReasonsLand;
	JTextArea txtAsOnSecondClaimReasonsBuilding;
	JTextArea txtAsOnSecondClaimReasonsMachine;
	JTextArea txtAsOnSecondClaimReasonsFixed;
	JTextArea txtAsOnSecondClaimReasonsCurrent;
	JTextArea txtAsOnSecondClaimReasonsOthers;
	JTextField txtAmtRealisedLand;
	JTextField txtAmtRealisedBuilding;
	JTextField txtAmtRealisedMachine;
	JTextField txtAmtRealisedFixed;
	JTextField txtAmtRealisedCurrent;
	JTextField txtAmtRealisedOthers;
	JTextField txtAmtRealisedPg;

	JLabel recoveryMadeHeading;
	JPanel recoveryMadePanel;
	JLabel termCompLoan;
	JLabel recoveryMode;
	JLabel amountCol;
	JLabel otherCol;
	JLabel cgpanRecoveryValue[];
	JTextField txtPrincipalTCRecovery[];
	JTextField txtInterestTCRecovery[];
	JTextField txtAmountWCRecovery[];
	JTextField txtOthersWCRecovery[];
	JComboBox cmbModeOfRecovery[];

	JPanel otsRecoveryPanel;
	JLabel recoveryOTS;
	JTextField txtOTSApprovalDate;

	JLabel dtOfConclusion;
	JTextField txtConclusionDate;
	JLabel whetherAccWrittenOff;
	JRadioButton accWrittenOffYes;
	JRadioButton accWrittenOffNo;
	JPanel accWrittenOffPanel;
	ButtonGroup accWrittenOffValue;
	JLabel dtOfAccWrittenOff;
	JTextField txtDtOfAccWrittenOff;
	JPanel accWrittenDetailsPanel;

	JLabel claimSummaryHeading;
	JPanel claimSummaryPanel;
	JLabel limitCovered;
	JLabel amountClaimed;
	JLabel claimSummaryCgpan[];
	JLabel limitCoveredValue[];
	JTextField txtAmountClaimed[];
	JLabel limitCovered1;
	JLabel amountClaimed1;
	JLabel amountClaimed2;
	JLabel facilityType;
	JLabel facilityType1;
	JLabel facilityType2;
	JLabel amtSettled;
	JLabel amtSettled1;
	JLabel amtSettled2;
	JLabel dtOfSettlement;
	JLabel dtOfSettlement1;
	JLabel dtOfSettlement2;
	JLabel dtOfSettlement3;
	JLabel amtClaimedSI;
	JLabel amtClaimedSI1;
	JLabel amtClaimedSI2;
	JLabel facilityTypeValue[];
	JTextField txtAmountSettled[];
	JTextField txtAmountClaimedSI[];
	JTextField txtDtOfSettlement[];
	JLabel claimSummaryHint;

	//Fields for input data
	JLabel borrowerIdInput;
	JLabel borrowerIdInputValue;
	JTextField txtBorrowerIdInput;
	JLabel borrowerNameInput;
	JLabel borrowerNameInputValue;	
	JTextField txtBorrowerNameInput;
	JLabel OSCgpansInput;
	JLabel OSCgpansInputValue;
	JTextField txtOSCgpansInput;
	JLabel noOfOsInput;
	JTextField txtNoOfOsInput;
	JLabel disCgpansInput;
	JLabel disCgpansInputValue;
	JTextField txtDisCgpansInput;
	JLabel noOfDisInput;
	JTextField txtNoOfDisInput;	
	JLabel repayCgpansInput;
	JLabel repayCgpansInputValue;
	JTextField txtRepayCgpansInput;
	JLabel noOfRepayInput;
	JTextField txtNoOfRepayInput;	
	JLabel recActionsInput;
	JTextField txtRecActionsInput;
	JLabel inputHint;
	JLabel countHint;
	JLabel hint;
	JTextField txtTCCgpansInput;
	JTextField txtWCCgpansInput;


	JButton reset;
	JButton save;
	JButton cancel;
	JButton ok;
	JButton print;
	JPanel buttonsPanel;

	/**
	 * These two labels are for storing the key of the object detail
	 * and the file name in which the object must be updated.
	 * These labels will be displayed as hidden.
	 */
	JLabel lblKey;
	JLabel lblFileName;
	JLabel lblFlag;
	JLabel lblBrnName;
	JPanel hiddenPanel;
	
	JLabel inRs;
	JLabel perInt;
	JPanel grpPanel;
	
	JLabel brnName;
	JTextField txtBrnName;
	
	JLabel hintMandatory;
	JLabel hintDate;

	int noOfOs = 0;
	int noOfDis = 0;
	int noOfRepay = 0;

	int noOfClaimTC = 0;
	int noOfClaimWC = 0;
	int noOfClaimRec = 0;
	int noOfClaimSummary = 0;

	GridBagLayout gridBag;
	GridBagConstraints constraints;

	Color fieldBg;
	Color dataBg;
	Color headingBg;
	Color fontColor;

	Font headingFont;
	Font dataFont;
	Font hintFont;

	DecimalFormat df;

	/**
	 * The constructor of this class initializes all the panels and sets the menu options
	 */
	public ThinClient()
	{
		df = new DecimalFormat("#############.##");
		df.setDecimalSeparatorAlwaysShown(false);

		headingFont = new Font("Times New Roman", Font.BOLD, 13);
		dataFont=new Font("Tahoma", Font.PLAIN, 11);
		hintFont=new Font("Arial", Font.PLAIN, 10);
		gridBag = new GridBagLayout();
		constraints = new GridBagConstraints();

		fieldBg = new Color(196, 222, 230);
		dataBg = new Color(210, 231, 236);
		headingBg = new Color(204, 204, 204);
		fontColor = new Color(255, 0, 0);

		menuBar = new JMenuBar();
		menu = new JMenu("Application");
		subMenu = new JMenu("New");

		menuItem = new JMenuItem("Term Credit (TC)");
		menuItem.setActionCommand("NewTCApp");
		menuItem.addActionListener(this);
		subMenu.add(menuItem);

		menuItem = new JMenuItem("Working Capital (WC)");
		menuItem.setActionCommand("NewWCApp");
		menuItem.addActionListener(this);
		subMenu.add(menuItem);

		menuItem = new JMenuItem("Composite Credit");
		menuItem.setActionCommand("NewCompositeApp");
		menuItem.addActionListener(this);
		subMenu.add(menuItem);

		menuItem = new JMenuItem("Both TC and WC");
		menuItem.setActionCommand("NewBothApp");
		menuItem.addActionListener(this);
		subMenu.add(menuItem);

		menuItem = new JMenuItem("Additional Term Credit");
		menuItem.setActionCommand("AddTermCredit");
		menuItem.addActionListener(this);
		subMenu.add(menuItem);

		menuItem = new JMenuItem("Working Capital Enhancement");
		menuItem.setActionCommand("WCEnhancement");
		menuItem.addActionListener(this);
		subMenu.add(menuItem);

		menuItem = new JMenuItem("Working Capital Renewal");
		menuItem.setActionCommand("WCRenewal");
		menuItem.addActionListener(this);
		subMenu.add(menuItem);
		menu.add(subMenu);

		menuItem = new JMenuItem("Verify");
		menuItem.setActionCommand("VerifyApp");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem=new JMenuItem("Modify");
		menuItem.setActionCommand("ModifyApp");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menuItem=new JMenuItem("Archive");
		menuItem.setActionCommand("ArchiveAppFiles");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menuItem=new JMenuItem("Exit");
		menuItem.setActionCommand("ExitTC");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuBar.add(menu);

		menu=new JMenu("Periodic Info");
		menuItem=new JMenuItem("New");
		menuItem.setActionCommand("NewPeriodicInfo");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem=new JMenuItem("Verify");
		menuItem.setActionCommand("VerifyPeriodicInfo");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem=new JMenuItem("Modify");
		menuItem.setActionCommand("ModifyPeriodicInfo");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem=new JMenuItem("Update Recovery Details");
		menuItem.setActionCommand("UpdateRecoveryDetails");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem=new JMenuItem("Verify Recovery Details");
		menuItem.setActionCommand("VerifyRecoveryDetails");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem=new JMenuItem("Archive");
		menuItem.setActionCommand("ArchivePerFiles");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuBar.add(menu);

		menu=new JMenu("Claims");
		subMenu = new JMenu("New");

		menuItem = new JMenuItem("First Instalment");
		menuItem.setActionCommand("NewFIClaims");
		menuItem.addActionListener(this);
		subMenu.add(menuItem);

		menuItem = new JMenuItem("Second Instalment");
		menuItem.setActionCommand("NewSIClaims");
		menuItem.addActionListener(this);
		subMenu.add(menuItem);

		menu.add(subMenu);

		menuItem=new JMenuItem("Verify");
		menuItem.setActionCommand("VerifyClaims");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem=new JMenuItem("Modify");
		menuItem.setActionCommand("ModifyClaims");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem=new JMenuItem("Archive");
		menuItem.setActionCommand("ArchiveClmFiles");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuBar.add(menu);

		menu=new JMenu("Help");

		menuItem=new JMenuItem("Help");
		menuItem.setActionCommand("HelpThinClient");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem=new JMenuItem("About Thin Client");
		menuItem.setActionCommand("AboutThinClient");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuBar.add(menu);
		
/*		menu = new JMenu("Exit");
		menu.addMenuListener(this);

		menuBar.add(menu);*/
		
		setJMenuBar(menuBar);

		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		scrollPane =
			new JScrollPane(
				panel,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		outstandingPanel=new JPanel();
		disbursementPanel=new JPanel();
		repaymentPanel=new JPanel();
		npaDetailsPanel=new JPanel();
		recoveryPanel=new JPanel();
		commonPanel=new JPanel();
		borrowerPanel=new JPanel();
		unitDetailsPanel=new JPanel();
		promotersPanel=new JPanel();
		otherPromotersPanel=new JPanel();
		facilityPanel=new JPanel();
		tcPanel=new JPanel();
		wcPanel=new JPanel();
		wcEnhancementPanel=new JPanel();
		wcRenewalPanel=new JPanel();
		securitisationPanel=new JPanel();
		mcgfPanel=new JPanel();
		memberInfoPanel=new JPanel();
		borrowerDetailsPanel=new JPanel();
		accStatusPanel=new JPanel();
		legalProceedingsPanel=new JPanel();
		termCompPanel=new JPanel();
		wcLimitPanel=new JPanel();
		wcReleasePanel = new JPanel();
		securityPersonalGuaranteePanel=new JPanel();
		securityPanel=new JPanel();
		personalGuaranteePanel=new JPanel();
		recoveryMadePanel=new JPanel();
		otsRecoveryPanel = new JPanel();
		claimSummaryPanel=new JPanel();
		accWrittenDetailsPanel=new JPanel();

		mcgfFlag = false;

		container=getContentPane();
		container.setBackground(Color.WHITE);

		displayHomePanel();
	}

	/**
	* This method displays the home screen
	*/
	private void displayHomePanel() {
		panel.removeAll();
		JLabel homeText = new JLabel("CHOOSE FROM MENU", JLabel.CENTER);
		homeText.setFont(headingFont);
		panel.setLayout(new BorderLayout());
		panel.add(homeText, BorderLayout.CENTER);
		container.removeAll();
		container.add(panel, BorderLayout.CENTER);
	}

	/**
	* This method initializes the components for the Term Credit Application and adds them to the Panel.
	*/
	private void displayTCApplication(boolean addTC)
	{
		panel.removeAll();
		panel.setBackground(dataBg);
		panel.setLayout(gridBag);
		displayCommonPanel("TC");
		if (addTC)
		{
			displayFacilityPanel(true);
		}
		else
		{
			displayFacilityPanel(false);
		}
		displayTCPanel();
		displaySecuritisationPanel();
		
		hintMandatory = new JLabel("All Fields marked with * are mandatory");
		hintMandatory.setFont(dataFont);
		hintMandatory.setOpaque(true);
		hintMandatory.setBackground(dataBg);
		addComponent(0, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, hintMandatory, gridBag, constraints, panel);
		
		hintDate = new JLabel("All dates should be in DD/MM/YYYY format");
		hintDate.setFont(dataFont);
		hintDate.setOpaque(true);
		hintDate.setBackground(dataBg);
		addComponent(0, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, hintDate, gridBag, constraints, panel);
		
		addComponent(0, 2, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.NONE, commonPanel, gridBag, constraints, panel);
		addComponent(0, 3, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, facilityPanel, gridBag, constraints, panel);
		addComponent(0, 4, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, tcPanel, gridBag, constraints, panel);
		addComponent(0, 5, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, securitisationPanel, gridBag, constraints, panel);
		if (displayMcgfPanel())
		{
			mcgfFlag = true;
			addComponent(0, 6, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, mcgfPanel, gridBag, constraints, panel);
			schemeName.setText("MCGS");
		}
		else
		{
			mcgfFlag = false;
		}

		remarks = new JLabel("Remarks");
		remarks.setFont(headingFont);
		remarks.setOpaque(true);
		remarks.setBackground(dataBg);
		addComponent(0, 7, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, remarks, gridBag, constraints, panel);

		txtRemarks=new JTextArea(2, 50);
		txtRemarks.setLineWrap(true);
		JScrollPane txtRemarksPane=new JScrollPane(txtRemarks, 								JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		addComponent(1, 7, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtRemarksPane, gridBag, constraints, panel);

		reset = new JButton("Reset");
		reset.setActionCommand("Reset");
		reset.addActionListener(this);

		save = new JButton("Save");
		save.setActionCommand("SaveTCApplication");
		save.addActionListener(this);

		cancel = new JButton("Cancel");
		cancel.setActionCommand("CancelApplication");
		cancel.addActionListener(this);
		
		print = new JButton("Print");
		if (addTC)
		{
			print.setActionCommand("PrintAddTCApplication");
		}
		else
		{
			print.setActionCommand("PrintTCApplication");			
		}
		print.addActionListener(this);

		buttonsPanel=new JPanel();
		buttonsPanel.setLayout(new FlowLayout());
		buttonsPanel.add(save);
		buttonsPanel.add(reset);
		buttonsPanel.add(cancel);
		buttonsPanel.add(print);
		addComponent(0, 8, GridBagConstraints.REMAINDER, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, buttonsPanel, gridBag, constraints, panel);
	}

	/**
	* This method displays the screen for working capital application
	*/
	private void displayWCApplication()
	{
		panel.removeAll();
		panel.setBackground(dataBg);
		panel.setLayout(gridBag);
		displayCommonPanel("WC");
		displayFacilityPanel(false);
		displayWCPanel();
		displaySecuritisationPanel();
		
		hintMandatory = new JLabel("All Fields marked with * are mandatory");
		hintMandatory.setFont(dataFont);
		hintMandatory.setOpaque(true);
		hintMandatory.setBackground(dataBg);
		addComponent(0, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, hintMandatory, gridBag, constraints, panel);
		
		hintDate = new JLabel("All dates should be in DD/MM/YYYY format");
		hintDate.setFont(dataFont);
		hintDate.setOpaque(true);
		hintDate.setBackground(dataBg);
		addComponent(0, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, hintDate, gridBag, constraints, panel);

		
		addComponent(0, 2, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, commonPanel, gridBag, constraints, panel);
		addComponent(0, 3, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, facilityPanel, gridBag, constraints, panel);
		addComponent(0, 4, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, wcPanel, gridBag, constraints, panel);
		addComponent(0, 5, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, securitisationPanel, gridBag, constraints, panel);
		if (displayMcgfPanel())
		{
			mcgfFlag = true;
			addComponent(0, 6, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, mcgfPanel, gridBag, constraints, panel);
			schemeName.setText("MCGS");
		}
		else
		{
			mcgfFlag = false;
		}

		compositeValue.setText("N");
		loanFacilityValue.setText("WC");

		remarks = new JLabel("Remarks");
		remarks.setFont(headingFont);
		remarks.setOpaque(true);
		remarks.setBackground(dataBg);
		addComponent(0, 7, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, remarks, gridBag, constraints, panel);

		txtRemarks=new JTextArea(2, 50);
		txtRemarks.setLineWrap(true);
		JScrollPane txtRemarksPane=new JScrollPane(txtRemarks, 								JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		addComponent(1, 7, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtRemarksPane, gridBag, constraints, panel);

		reset = new JButton("Reset");
		reset.setActionCommand("Reset");
		reset.addActionListener(this);

		save = new JButton("Save");
		save.setActionCommand("SaveWCApplication");
		save.addActionListener(this);

		cancel = new JButton("Cancel");
		cancel.setActionCommand("CancelApplication");
		cancel.addActionListener(this);
		
		print = new JButton("Print");
		print.setActionCommand("PrintWCApplication");
		print.addActionListener(this);

		buttonsPanel=new JPanel();
		buttonsPanel.setLayout(new FlowLayout());
		buttonsPanel.add(save);
		buttonsPanel.add(reset);
		buttonsPanel.add(cancel);
		buttonsPanel.add(print);
		addComponent(0, 8, GridBagConstraints.REMAINDER, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, buttonsPanel, gridBag, constraints, panel);
	}

	/**
	* This method displays the screen for composite application
	*/
	private void displayCompositeApplication()
	{
		panel.removeAll();
		panel.setBackground(dataBg);
		panel.setLayout(gridBag);
		displayCommonPanel("CC");
		displayFacilityPanel(false);
		displayTCPanel();
		displayWCPanel();
		displaySecuritisationPanel();
		
		hintMandatory = new JLabel("All Fields marked with * are mandatory");
		hintMandatory.setFont(dataFont);
		hintMandatory.setOpaque(true);
		hintMandatory.setBackground(dataBg);
		addComponent(0, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, hintMandatory, gridBag, constraints, panel);
		
		hintDate = new JLabel("All dates should be in DD/MM/YYYY format");
		hintDate.setFont(dataFont);
		hintDate.setOpaque(true);
		hintDate.setBackground(dataBg);
		addComponent(0, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, hintDate, gridBag, constraints, panel);
		
		addComponent(0, 2, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, commonPanel, gridBag, constraints, panel);
		addComponent(0, 3, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, facilityPanel, gridBag, constraints, panel);
		addComponent(0, 4, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, tcPanel, gridBag, constraints, panel);
		addComponent(0, 5, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, wcPanel, gridBag, constraints, panel);
		addComponent(0, 6, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, securitisationPanel, gridBag, constraints, panel);
		if (displayMcgfPanel())
		{
			mcgfFlag = true;
			addComponent(0, 7, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, mcgfPanel, gridBag, constraints, panel);
			schemeName.setText("MCGS");
		}
		else
		{
			mcgfFlag = false;
		}

		compositeValue.setText("Y");
		loanFacilityValue.setText("CC");

		remarks = new JLabel("Remarks");
		remarks.setFont(headingFont);
		remarks.setOpaque(true);
		remarks.setBackground(dataBg);
		addComponent(0, 8, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, remarks, gridBag, constraints, panel);

		txtRemarks=new JTextArea(2, 50);
		txtRemarks.setLineWrap(true);
		JScrollPane txtRemarksPane=new JScrollPane(txtRemarks, 								JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		addComponent(1, 8, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtRemarksPane, gridBag, constraints, panel);

		reset = new JButton("Reset");
		reset.setActionCommand("Reset");
		reset.addActionListener(this);

		save = new JButton("Save");
		save.setActionCommand("SaveCompositeApplication");
		save.addActionListener(this);

		cancel = new JButton("Cancel");
		cancel.setActionCommand("CancelApplication");
		cancel.addActionListener(this);
		
		print = new JButton("Print");
		print.setActionCommand("PrintCCApplication");
		print.addActionListener(this);

		buttonsPanel=new JPanel();
		buttonsPanel.setLayout(new FlowLayout());
		buttonsPanel.add(save);
		buttonsPanel.add(reset);
		buttonsPanel.add(cancel);
		buttonsPanel.add(print);
		addComponent(0, 9, GridBagConstraints.REMAINDER, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, buttonsPanel, gridBag, constraints, panel);
	}

	/**
	* This method displays the screen for both application
	*/
	private void displayBothApplication()
	{
		panel.removeAll();
		panel.setBackground(dataBg);
		panel.setLayout(gridBag);
		displayCommonPanel("BO");
		displayFacilityPanel(false);
		displayTCPanel();
		displayWCPanel();
		displaySecuritisationPanel();
		
		hintMandatory = new JLabel("All Fields marked with * are mandatory");
		hintMandatory.setFont(dataFont);
		hintMandatory.setOpaque(true);
		hintMandatory.setBackground(dataBg);
		addComponent(0, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, hintMandatory, gridBag, constraints, panel);
		
		hintDate = new JLabel("All dates should be in DD/MM/YYYY format");
		hintDate.setFont(dataFont);
		hintDate.setOpaque(true);
		hintDate.setBackground(dataBg);
		addComponent(0, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, hintDate, gridBag, constraints, panel);
		
		addComponent(0, 2, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, commonPanel, gridBag, constraints, panel);
		addComponent(0, 3, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, facilityPanel, gridBag, constraints, panel);
		addComponent(0, 4, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, tcPanel, gridBag, constraints, panel);
		addComponent(0, 5, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, wcPanel, gridBag, constraints, panel);
		addComponent(0, 6, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, securitisationPanel, gridBag, constraints, panel);
		if (displayMcgfPanel())
		{
			mcgfFlag = true;
			addComponent(0, 7, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, mcgfPanel, gridBag, constraints, panel);
			schemeName.setText("MCGS");
		}
		else
		{
			mcgfFlag = false;
		}

		compositeValue.setText("N");
		loanFacilityValue.setText("BO");

		remarks = new JLabel("Remarks");
		remarks.setFont(headingFont);
		remarks.setOpaque(true);
		remarks.setBackground(dataBg);
		addComponent(0, 8, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, remarks, gridBag, constraints, panel);

		txtRemarks=new JTextArea(2, 50);
		txtRemarks.setLineWrap(true);
		JScrollPane txtRemarksPane=new JScrollPane(txtRemarks, 								JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		addComponent(1, 8, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtRemarksPane, gridBag, constraints, panel);

		reset = new JButton("Reset");
		reset.setActionCommand("Reset");
		reset.addActionListener(this);

		save = new JButton("Save");
		save.setActionCommand("SaveBothApplication");
		save.addActionListener(this);

		cancel = new JButton("Cancel");
		cancel.setActionCommand("CancelApplication");
		cancel.addActionListener(this);

		print = new JButton("Print");
		print.setActionCommand("PrintBOApplication");
		print.addActionListener(this);

		buttonsPanel=new JPanel();
		buttonsPanel.setLayout(new FlowLayout());
		buttonsPanel.add(save);
		buttonsPanel.add(reset);
		buttonsPanel.add(cancel);
		buttonsPanel.add(print);
		addComponent(0, 9, GridBagConstraints.REMAINDER, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, buttonsPanel, gridBag, constraints, panel);
	}

	/**
	 * This method displays Additional TC application.
	 */
	private void displayAddTCApplication()
	{
		displayTCApplication(true);

//		panel.remove(mcgfPanel);

		compositeValue.setText("N");
		loanFacilityValue.setText("Additional Term Loan");
//		schemeName.setText("CGFSI");

		save.setActionCommand("SaveAddTCApplication");
		npaYes.setEnabled(false);
		npaNo.setEnabled(false);
		borrowerAssistedYes.setSelected(true);
		borrowerAssistedYes.setEnabled(false);
		borrowerAssistedNo.setEnabled(false);
		txtOSAmt.setEnabled(false);
		borrowerCoveredYes.setSelected(true);
		borrowerCoveredYes.setEnabled(false);
		borrowerCoveredNo.setEnabled(false);
		none.setEnabled(false);
		cgbid.setEnabled(false);
		cgpan.setSelected(true);
		enableBorrowerDetails(false);
		cgpan.setEnabled(true);
		txtSsiValue.setEnabled(true);
		ActionListener[] actionListeners = borrowerCoveredYes.getActionListeners();
		borrowerCoveredYes.removeActionListener(actionListeners[0]);
		actionListeners = borrowerCoveredNo.getActionListeners();
		borrowerCoveredNo.removeActionListener(actionListeners[0]);
	}

	/**
	 * This method displays the working capital enhancement screen.
	 */
	private void displayWCEApplication()
	{
		panel.removeAll();
		panel.setBackground(dataBg);
		panel.setLayout(gridBag);
		displayCommonPanel("WCE");
		displayFacilityPanel(false);
		displayWCEnhancementPanel();
		displaySecuritisationPanel();

		fbHint = new JLabel("Enter (Existing + New) Fund Based Value");
		fbHint.setFont(hintFont);
		fbHint.setOpaque(true);
		fbHint.setBackground(dataBg);
		addComponent(3, 51, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, fbHint, gridBag, constraints, commonPanel);
		
		hintMandatory = new JLabel("All Fields marked with * are mandatory");
		hintMandatory.setFont(dataFont);
		hintMandatory.setOpaque(true);
		hintMandatory.setBackground(dataBg);
		addComponent(0, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, hintMandatory, gridBag, constraints, panel);
		
		hintDate = new JLabel("All dates should be in DD/MM/YYYY format");
		hintDate.setFont(dataFont);
		hintDate.setOpaque(true);
		hintDate.setBackground(dataBg);
		addComponent(0, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, hintDate, gridBag, constraints, panel);		

		addComponent(0, 2, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, commonPanel, gridBag, constraints, panel);
		addComponent(0, 3, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, facilityPanel, gridBag, constraints, panel);
		addComponent(0, 4, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, wcEnhancementPanel, gridBag, constraints, panel);
		addComponent(0, 5, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, securitisationPanel, gridBag, constraints, panel);

		compositeValue.setText("N");
		loanFacilityValue.setText("Working Capital Enhancement");

		remarks = new JLabel("Remarks");
		remarks.setFont(headingFont);
		remarks.setOpaque(true);
		remarks.setBackground(dataBg);
		addComponent(0, 7, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, remarks, gridBag, constraints, panel);

		txtRemarks=new JTextArea(2, 50);
		txtRemarks.setLineWrap(true);
		JScrollPane txtRemarksPane=new JScrollPane(txtRemarks, 								JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		addComponent(1, 7, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtRemarksPane, gridBag, constraints, panel);

		reset = new JButton("Reset");
		reset.setActionCommand("Reset");
		reset.addActionListener(this);

		save = new JButton("Save");
		save.setActionCommand("SaveWCEApplication");
		save.addActionListener(this);

		cancel = new JButton("Cancel");
		cancel.setActionCommand("CancelApplication");
		cancel.addActionListener(this);

		print = new JButton("Print");
		print.setActionCommand("PrintWCEApplication");
		print.addActionListener(this);

		buttonsPanel=new JPanel();
		buttonsPanel.setLayout(new FlowLayout());
		buttonsPanel.add(save);
		buttonsPanel.add(reset);
		buttonsPanel.add(cancel);
		buttonsPanel.add(print);
		addComponent(0, 8, GridBagConstraints.REMAINDER, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, buttonsPanel, gridBag, constraints, panel);

		txtBankRefNo.setEnabled(false);
		txtBankBranchName.setEnabled(false);
		txtBranchCode.setEnabled(false);
		borrowerAssistedYes.setEnabled(false);
		borrowerAssistedNo.setEnabled(false);
		npaYes.setEnabled(false);
		npaNo.setEnabled(false);
		borrowerCoveredYes.setSelected(true);
		borrowerCoveredYes.setEnabled(false);
		borrowerCoveredNo.setEnabled(false);
		txtOSAmt.setEnabled(false);		
		none.setEnabled(false);
		cgbid.setEnabled(false);
		cgpan.setSelected(true);
		enableBorrowerDetails(false);
		cgpan.setEnabled(true);
		txtSsiValue.setEnabled(true);
		ActionListener[] actionListeners = borrowerCoveredYes.getActionListeners();
		borrowerCoveredYes.removeActionListener(actionListeners[0]);
		actionListeners = borrowerCoveredNo.getActionListeners();
		borrowerCoveredNo.removeActionListener(actionListeners[0]);
	}

	/**
	 * This method dislays the working capital renewal screen.
	 */
	private void displayWCRApplication()
	{
		panel.removeAll();
		panel.setBackground(dataBg);
		panel.setLayout(gridBag);
		displayCommonPanel("WC");
		displayFacilityPanel(true);
		displaySecuritisationPanel();
		displayWCRenewalPanel();
		if (displayMcgfPanel())
		{
			mcgfFlag = true;
			addComponent(0, 4, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, mcgfPanel, gridBag, constraints, panel);
			schemeName.setText("MCGS");
		}
		else
		{
			mcgfFlag = false;
		}
		
		hintMandatory = new JLabel("All Fields marked with * are mandatory");
		hintMandatory.setFont(dataFont);
		hintMandatory.setOpaque(true);
		hintMandatory.setBackground(dataBg);
		addComponent(0, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, hintMandatory, gridBag, constraints, panel);
		
		hintDate = new JLabel("All dates should be in DD/MM/YYYY format");
		hintDate.setFont(dataFont);
		hintDate.setOpaque(true);
		hintDate.setBackground(dataBg);
		addComponent(0, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, hintDate, gridBag, constraints, panel);
		

		addComponent(0, 2, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, commonPanel, gridBag, constraints, panel);
		addComponent(0, 3, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, facilityPanel, gridBag, constraints, panel);
		addComponent(0, 4, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, wcRenewalPanel, gridBag, constraints, panel);
		addComponent(0, 5, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, securitisationPanel, gridBag, constraints, panel);

		compositeValue.setText("N");
		loanFacilityValue.setText("Working Capital Renewal");

		remarks = new JLabel("Remarks");
		remarks.setFont(headingFont);
		remarks.setOpaque(true);
		remarks.setBackground(dataBg);
		addComponent(0, 7, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, remarks, gridBag, constraints, panel);

		txtRemarks=new JTextArea(2, 50);
		txtRemarks.setLineWrap(true);
		JScrollPane txtRemarksPane=new JScrollPane(txtRemarks, 								JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		addComponent(1, 7, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtRemarksPane, gridBag, constraints, panel);

		reset = new JButton("Reset");
		reset.setActionCommand("Reset");
		reset.addActionListener(this);

		save = new JButton("Save");
		save.setActionCommand("SaveWCRApplication");
		save.addActionListener(this);

		cancel = new JButton("Cancel");
		cancel.setActionCommand("CancelApplication");
		cancel.addActionListener(this);
		
		print = new JButton("Print");
		print.setActionCommand("PrintWCRApplication");
		print.addActionListener(this);

		buttonsPanel=new JPanel();
		buttonsPanel.setLayout(new FlowLayout());
		buttonsPanel.add(save);
		buttonsPanel.add(reset);
		buttonsPanel.add(cancel);
		buttonsPanel.add(print);
		addComponent(0, 8, GridBagConstraints.REMAINDER, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, buttonsPanel, gridBag, constraints, panel);

		txtBankRefNo.setEnabled(true);
		txtBankBranchName.setEnabled(true);
		txtBranchCode.setEnabled(true);
		borrowerAssistedYes.setEnabled(false);
		borrowerAssistedNo.setEnabled(false);
		npaYes.setEnabled(false);
		npaNo.setEnabled(false);
		borrowerCoveredYes.setSelected(true);
		borrowerCoveredYes.setEnabled(false);
		borrowerCoveredNo.setEnabled(false);
		txtOSAmt.setEnabled(false);		
		none.setEnabled(false);
		cgbid.setEnabled(false);
		cgpan.setSelected(true);
		enableBorrowerDetails(false);
		cgpan.setEnabled(true);
		txtSsiValue.setEnabled(true);
		ActionListener[] actionListeners = borrowerCoveredYes.getActionListeners();
		borrowerCoveredYes.removeActionListener(actionListeners[0]);
		actionListeners = borrowerCoveredNo.getActionListeners();
		borrowerCoveredNo.removeActionListener(actionListeners[0]);
	}

	/**
	* This method initializes the components for the Term Credit Application and adds them to the Panel.
	*/
	private void displayCommonPanel(String appType) {
		commonPanel.removeAll();
		commonPanel.setBackground(dataBg);
		commonPanel.setLayout(gridBag);

		appHeading = new JLabel("Application Details");
		appHeading.setFont(headingFont);
		appHeading.setOpaque(true);
		appHeading.setBackground(fieldBg);
		addComponent(0, 0, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, appHeading, gridBag, constraints, commonPanel);

		bankRef = new JLabel("* Bank/Institution Reference No.");
		bankRef.setFont(headingFont);
		bankRef.setOpaque(true);
		bankRef.setBackground(dataBg);
		addComponent(0, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, bankRef, gridBag, constraints, commonPanel);

		txtBankRefNo = new JTextField(new TextFormatField(10),"",10);
		addComponent(1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtBankRefNo, gridBag, constraints, commonPanel);

		bankBranch = new JLabel("* Bank/Institution Branch Name");
		bankBranch.setFont(headingFont);
		bankBranch.setOpaque(true);
		bankBranch.setBackground(dataBg);
		addComponent(0, 2, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, bankBranch, gridBag, constraints, commonPanel);

		txtBankBranchName = new JTextField(new TextFormatField(25),"",10);
		addComponent(1, 2, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtBankBranchName, gridBag, constraints, commonPanel);

		branchCode = new JLabel("Bank/Institution Branch Code");
		branchCode.setFont(headingFont);
		branchCode.setOpaque(true);
		branchCode.setBackground(dataBg);
		addComponent(0, 3, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, branchCode, gridBag, constraints, commonPanel);

		txtBranchCode = new JTextField(new TextFormatField(10),"",10);
		addComponent(1, 3, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtBranchCode, gridBag, constraints, commonPanel);

		displayBorrowerPanel();
		addComponent(0, 4, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, borrowerPanel, gridBag, constraints, commonPanel);

		displayPromotersPanel();
		addComponent(0, 5, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, promotersPanel, gridBag, constraints, commonPanel);

		projectDetails = new JLabel("Project Details - Outlay");
		projectDetails.setFont(headingFont);
		projectDetails.setOpaque(true);
		projectDetails.setBackground(headingBg);
		addComponent(0, 31, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, projectDetails, gridBag, constraints, commonPanel);

		collateralSecurity = new JLabel("* Collateral Security Taken");
		collateralSecurity.setFont(headingFont);
		collateralSecurity.setOpaque(true);
		collateralSecurity.setBackground(dataBg);
		addComponent(0, 32, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, collateralSecurity, gridBag, constraints, commonPanel);

		collateralYes = new JRadioButton("Yes");
		collateralNo = new JRadioButton("No");
		collateralYes.setBackground(dataBg);
		collateralNo.setBackground(dataBg);
		collateralNo.setSelected(true);
		collateral = new ButtonGroup();
		collateral.add(collateralYes);
		collateral.add(collateralNo);
		collateralPanel = new JPanel();
		collateralPanel.add(collateralYes);
		collateralPanel.add(collateralNo);
		collateralPanel.setBackground(dataBg);
		addComponent(1, 32, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, collateralPanel, gridBag, constraints, commonPanel);

		thirdPartyGuarantee = new JLabel("# Third Party Guarantee Taken");
		thirdPartyGuarantee.setFont(headingFont);
		thirdPartyGuarantee.setOpaque(true);
		thirdPartyGuarantee.setBackground(dataBg);
		addComponent(2, 32, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, thirdPartyGuarantee, gridBag, constraints, commonPanel);

		thirdPartyYes = new JRadioButton("Yes");
		thirdPartyNo = new JRadioButton("No");
		thirdPartyYes.setBackground(dataBg);
		thirdPartyNo.setBackground(dataBg);
		thirdPartyNo.setSelected(true);
		thirdParty = new ButtonGroup();
		thirdParty.add(thirdPartyYes);
		thirdParty.add(thirdPartyNo);
		thirdPartyPanel = new JPanel();
		thirdPartyPanel.add(thirdPartyYes);
		thirdPartyPanel.add(thirdPartyNo);
		thirdPartyPanel.setBackground(dataBg);
		addComponent(3, 32, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, thirdPartyPanel, gridBag, constraints, commonPanel);

		collateralSecurityHint = new JLabel("* Collateral security is any other security offered for the said loan. For example, hypothecation of jewellery, mortgage of house, etc.");
		collateralSecurityHint.setFont(hintFont);
		collateralSecurityHint.setOpaque(true);
		collateralSecurityHint.setBackground(dataBg);
		addComponent(0, 33, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, collateralSecurityHint, gridBag, constraints, commonPanel);

		thirdPartyGuaranteeHint = new JLabel("# When the borrower furnishes the guarantee of any other person / corporate not connected with the project, it is considered as Third Party Guarantee.");
		thirdPartyGuaranteeHint.setFont(hintFont);
		thirdPartyGuaranteeHint.setOpaque(true);
		thirdPartyGuaranteeHint.setBackground(dataBg);
		addComponent(0, 34, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, thirdPartyGuaranteeHint, gridBag, constraints, commonPanel);

		guarantors = new JLabel("Guarantors");
		guarantors.setFont(headingFont);
		guarantors.setOpaque(true);
		guarantors.setBackground(headingBg);
		addComponent(0, 35, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, guarantors, gridBag, constraints, commonPanel);

		guarantorName = new JLabel("1. Name");
		guarantorName.setFont(headingFont);
		guarantorName.setOpaque(true);
		guarantorName.setBackground(dataBg);
		addComponent(0, 36, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, guarantorName, gridBag, constraints, commonPanel);

		txtGuarantorName1 = new JTextField(new TextFormatField(100),"",10);
		addComponent(1, 36, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtGuarantorName1, gridBag, constraints, commonPanel);

		guarantorNetworth = new JLabel("Net Worth as on Date of Sanction of Facility");
		guarantorNetworth.setFont(headingFont);
		guarantorNetworth.setOpaque(true);
		guarantorNetworth.setBackground(dataBg);
		addComponent(2, 36, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, guarantorNetworth, gridBag, constraints, commonPanel);

		txtGuarantorNetworth1 = new JTextField(10);
		txtGuarantorNetworth1.setDocument(new DecimalFormatField(13,2));
//		addComponent(3, 36, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtGuarantorNetworth1, gridBag, constraints, commonPanel);
		
		inRs = new JLabel("in Rs.");
		inRs.setFont(hintFont);
		inRs.setOpaque(true);
		inRs.setBackground(dataBg);
		inRs.setHorizontalTextPosition(SwingConstants.LEFT);		
//		addComponent(2, 51, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, inRs, gridBag, constraints, commonPanel);
		
		grpPanel = new JPanel();
		grpPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		grpPanel.setBackground(dataBg);
		grpPanel.add(txtGuarantorNetworth1);
		grpPanel.add(inRs);
		addComponent(3, 36, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, grpPanel, gridBag, constraints, commonPanel);

		guarantorName = new JLabel("2. Name");
		guarantorName.setFont(headingFont);
		guarantorName.setOpaque(true);
		guarantorName.setBackground(dataBg);
		addComponent(0, 37, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, guarantorName, gridBag, constraints, commonPanel);

		txtGuarantorName2 = new JTextField(new TextFormatField(100),"",10);
		addComponent(1, 37, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtGuarantorName2, gridBag, constraints, commonPanel);
		
		guarantorNetworth = new JLabel("Net Worth as on Date of Sanction of Facility");
		guarantorNetworth.setFont(headingFont);
		guarantorNetworth.setOpaque(true);
		guarantorNetworth.setBackground(dataBg);
		addComponent(2, 37, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, guarantorNetworth, gridBag, constraints, commonPanel);

		txtGuarantorNetworth2 = new JTextField(10);
		txtGuarantorNetworth2.setDocument(new DecimalFormatField(13,2));
//		addComponent(3, 37, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtGuarantorNetworth2, gridBag, constraints, commonPanel);
		
		inRs = new JLabel("in Rs.");
		inRs.setFont(hintFont);
		inRs.setOpaque(true);
		inRs.setBackground(dataBg);
		inRs.setHorizontalTextPosition(SwingConstants.LEFT);		
//		addComponent(2, 51, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, inRs, gridBag, constraints, commonPanel);
		
		grpPanel = new JPanel();
		grpPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		grpPanel.setBackground(dataBg);
		grpPanel.add(txtGuarantorNetworth2);
		grpPanel.add(inRs);
		addComponent(3, 37, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, grpPanel, gridBag, constraints, commonPanel);

		guarantorName = new JLabel("3. Name");
		guarantorName.setFont(headingFont);
		guarantorName.setOpaque(true);
		guarantorName.setBackground(dataBg);
		addComponent(0, 38, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, guarantorName, gridBag, constraints, commonPanel);

		txtGuarantorName3 = new JTextField(new TextFormatField(100),"",10);
		addComponent(1, 38, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtGuarantorName3, gridBag, constraints, commonPanel);

		guarantorNetworth = new JLabel("Net Worth as on Date of Sanction of Facility");
		guarantorNetworth.setFont(headingFont);
		guarantorNetworth.setOpaque(true);
		guarantorNetworth.setBackground(dataBg);
		addComponent(2, 38, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, guarantorNetworth, 	gridBag, constraints, commonPanel);

		txtGuarantorNetworth3 = new JTextField(10);
		txtGuarantorNetworth3.setDocument(new DecimalFormatField(13,2));
//		addComponent(3, 38, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtGuarantorNetworth3, gridBag, constraints, commonPanel);
		
		inRs = new JLabel("in Rs.");
		inRs.setFont(hintFont);
		inRs.setOpaque(true);
		inRs.setBackground(dataBg);
		inRs.setHorizontalTextPosition(SwingConstants.LEFT);		
//		addComponent(2, 51, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, inRs, gridBag, constraints, commonPanel);
		
		grpPanel = new JPanel();
		grpPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		grpPanel.setBackground(dataBg);
		grpPanel.add(txtGuarantorNetworth3);
		grpPanel.add(inRs);
		addComponent(3, 38, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, grpPanel, gridBag, constraints, commonPanel);

		guarantorName = new JLabel("4. Name");
		guarantorName.setFont(headingFont);
		guarantorName.setOpaque(true);
		guarantorName.setBackground(dataBg);
		addComponent(0, 39, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, guarantorName, gridBag, constraints, commonPanel);

		txtGuarantorName4 = new JTextField(new TextFormatField(100),"",10);
		addComponent(1, 39, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtGuarantorName4, gridBag, constraints, commonPanel);

		guarantorNetworth = new JLabel("Net Worth as on Date of Sanction of Facility");
		guarantorNetworth.setFont(headingFont);
		guarantorNetworth.setOpaque(true);
		guarantorNetworth.setBackground(dataBg);
		addComponent(2, 39, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, guarantorNetworth, 	gridBag, constraints, commonPanel);

		txtGuarantorNetworth4 = new JTextField(10);
		txtGuarantorNetworth4.setDocument(new DecimalFormatField(13,2));
		addComponent(3, 39, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtGuarantorNetworth4, gridBag, constraints, commonPanel);
		
		inRs = new JLabel("in Rs.");
		inRs.setFont(hintFont);
		inRs.setOpaque(true);
		inRs.setBackground(dataBg);
		inRs.setHorizontalTextPosition(SwingConstants.LEFT);		
//		addComponent(2, 51, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, inRs, gridBag, constraints, commonPanel);
		
		grpPanel = new JPanel();
		grpPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		grpPanel.setBackground(dataBg);
		grpPanel.add(txtGuarantorNetworth4);
		grpPanel.add(inRs);
		addComponent(3, 39, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, grpPanel, gridBag, constraints, commonPanel);

		supportName = new JLabel("Name of Subsidy / Equity Support");
		supportName.setFont(headingFont);
		supportName.setOpaque(true);
		supportName.setBackground(dataBg);
		addComponent(0, 40, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, supportName, gridBag, constraints, commonPanel);

		String[] supportNames = { "Select", "PMRY", "SJRY", "Others" };
		cmbSupportNames = new JComboBox(supportNames);
		cmbSupportNames.addItemListener(new SupportListener());
		addComponent(1, 40, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, cmbSupportNames, gridBag, constraints, commonPanel);

		txtSupportName = new JTextField(new TextFormatField(100),"",10);
		addComponent(2, 40, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtSupportName, gridBag, constraints, commonPanel);

		primarySecurity = new JLabel("Primary Security Details");
		primarySecurity.setFont(headingFont);
		primarySecurity.setOpaque(true);
		primarySecurity.setBackground(headingBg);
		addComponent(0, 41, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, primarySecurity, gridBag, constraints, commonPanel);

		securityName = new JLabel("Security Name", JLabel.CENTER);
		securityName.setFont(headingFont);
		securityName.setOpaque(true);
		securityName.setBackground(dataBg);
		addComponent(0, 42, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, securityName, gridBag, constraints, commonPanel);

		particulars = new JLabel("Particulars", JLabel.CENTER);
		particulars.setFont(headingFont);
		particulars.setOpaque(true);
		particulars.setBackground(dataBg);
		addComponent(1, 42, 2, GridBagConstraints.CENTER, GridBagConstraints.BOTH, particulars, gridBag, constraints, commonPanel);

		value = new JLabel("Value (in Rs.)", JLabel.CENTER);
		value.setFont(headingFont);
		value.setOpaque(true);
		value.setBackground(dataBg);
		addComponent(3, 42, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, value, gridBag, constraints, commonPanel);

		land = new JLabel("Land", JLabel.CENTER);
		land.setFont(headingFont);
		land.setOpaque(true);
		land.setBackground(dataBg);
		addComponent(0, 43, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, land, gridBag, constraints, commonPanel);

		txtLandParticulars = new JTextField(new TextFormatField(200),"",30);
		addComponent(1, 43, 2, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtLandParticulars, gridBag, constraints, commonPanel);

		txtLandValue = new JTextField(10);
		txtLandValue.setDocument(new DecimalFormatField(13,2));
		txtLandValue.addFocusListener(new PrimarySecurityListener());
		addComponent(3, 43, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtLandValue, gridBag, constraints, commonPanel);

		building = new JLabel("Building", JLabel.CENTER);
		building.setFont(headingFont);
		building.setOpaque(true);
		building.setBackground(dataBg);
		addComponent(0, 44, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, building, gridBag, constraints, commonPanel);

		txtBuildingParticulars = new JTextField(new TextFormatField(200),"",30);
		addComponent(1, 44, 2, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtBuildingParticulars, gridBag, constraints, commonPanel);

		txtBuildingValue = new JTextField(10);
		txtBuildingValue.setDocument(new DecimalFormatField(13,2));
		txtBuildingValue.addFocusListener(new PrimarySecurityListener());
		addComponent(3, 44, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtBuildingValue, gridBag, constraints, commonPanel);

		machine = new JLabel("Machine", JLabel.CENTER);
		machine.setFont(headingFont);
		machine.setOpaque(true);
		machine.setBackground(dataBg);
		addComponent(0, 45, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, machine, gridBag, constraints, commonPanel);

		txtMachineParticulars = new JTextField(new TextFormatField(200),"",30);
		addComponent(1, 45, 2, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtMachineParticulars, gridBag, constraints, commonPanel);

		txtMachineValue = new JTextField(10);
		txtMachineValue.setDocument(new DecimalFormatField(13,2));
		txtMachineValue.addFocusListener(new PrimarySecurityListener());
		addComponent(3, 45, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtMachineValue, gridBag, constraints, commonPanel);

		otherAssets = new JLabel("Other Fixed / Movable Assets", JLabel.CENTER);
		otherAssets.setFont(headingFont);
		otherAssets.setOpaque(true);
		otherAssets.setBackground(dataBg);
		addComponent(0, 46, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, otherAssets, gridBag, constraints, commonPanel);

		txtOtherAssetsParticulars = new JTextField(new TextFormatField(200),"",30);
		addComponent(1, 46, 2, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtOtherAssetsParticulars, gridBag, constraints, commonPanel);

		txtOtherAssetsValue = new JTextField(10);
		txtOtherAssetsValue.setDocument(new DecimalFormatField(13,2));
		txtOtherAssetsValue.addFocusListener(new PrimarySecurityListener());
		addComponent(3, 46, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtOtherAssetsValue, gridBag, constraints, commonPanel);

		currentAssets = new JLabel("Current Assets", JLabel.CENTER);
		currentAssets.setFont(headingFont);
		currentAssets.setOpaque(true);
		currentAssets.setBackground(dataBg);
		addComponent(0, 47, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, currentAssets, gridBag, constraints, commonPanel);

		txtCurrentAssetsParticulars = new JTextField(new TextFormatField(200),"",30);
		addComponent(1, 47, 2, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtCurrentAssetsParticulars, gridBag, constraints, commonPanel);

		txtCurrentAssetsValue = new JTextField(10);
		txtCurrentAssetsValue.setDocument(new DecimalFormatField(13,2));
		txtCurrentAssetsValue.addFocusListener(new PrimarySecurityListener());
		addComponent(3, 47, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtCurrentAssetsValue, gridBag, constraints, commonPanel);

		others = new JLabel("Others", JLabel.CENTER);
		others.setFont(headingFont);
		others.setOpaque(true);
		others.setBackground(dataBg);
		addComponent(0, 48, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, others, gridBag, constraints, commonPanel);

		txtOthersParticulars = new JTextField(new TextFormatField(200),"",30);
		addComponent(1, 48, 2, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtOthersParticulars, gridBag, constraints, commonPanel);

		txtOthersValue = new JTextField(10);
		txtOthersValue.setDocument(new DecimalFormatField(13,2));
		txtOthersValue.addFocusListener(new PrimarySecurityListener());
		addComponent(3, 48, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtOthersValue, gridBag, constraints, commonPanel);

		securityTotal = new JLabel("Total", JLabel.CENTER);
		securityTotal.setFont(headingFont);
		securityTotal.setOpaque(true);
		securityTotal.setBackground(dataBg);
		addComponent(0, 49, 2, GridBagConstraints.WEST, GridBagConstraints.BOTH, securityTotal, gridBag, constraints, commonPanel);

		securityTotalValue = new JLabel("", JLabel.CENTER);
		securityTotalValue.setFont(headingFont);
		securityTotalValue.setOpaque(true);
		securityTotalValue.setBackground(dataBg);
		addComponent(2, 49, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, securityTotalValue, gridBag, constraints, commonPanel);

		meansOfFinance = new JLabel("Means of Finance");
		meansOfFinance.setFont(headingFont);
		meansOfFinance.setOpaque(true);
		meansOfFinance.setBackground(headingBg);
		addComponent(0, 50, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, meansOfFinance, gridBag, constraints, commonPanel);

		if (appType.equalsIgnoreCase("TC") || appType.equalsIgnoreCase("CC") || appType.equalsIgnoreCase("BO"))
		{
			termCredit = new JLabel("* Term Credit Sanctioned");
		}
		else
		{
			termCredit = new JLabel("Term Credit Sanctioned");
		}

		termCredit.setFont(headingFont);
		termCredit.setOpaque(true);
		termCredit.setBackground(dataBg);
		termCredit.setVerticalAlignment(JLabel.TOP);
		addComponent(0, 51, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, termCredit, gridBag, constraints, commonPanel);

		txtTermCredit = new JTextField(10);
		txtTermCredit.setDocument(new DecimalFormatField(13,2));
		txtTermCredit.addFocusListener(new ProjectOutlayListener());
//		addComponent(1, 51, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtTermCredit, gridBag, constraints, commonPanel);

		inRs = new JLabel("in Rs.");
		inRs.setFont(hintFont);
		inRs.setOpaque(true);
		inRs.setBackground(dataBg);
		inRs.setHorizontalTextPosition(SwingConstants.LEFT);		
//		addComponent(2, 51, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, inRs, gridBag, constraints, commonPanel);
		
		grpPanel = new JPanel();
		grpPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		grpPanel.setBackground(dataBg);
		grpPanel.add(txtTermCredit);
		grpPanel.add(inRs);
		addComponent(1, 51, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, grpPanel, gridBag, constraints, commonPanel);		

		if (appType.equalsIgnoreCase("WC") || appType.equalsIgnoreCase("CC") || appType.equalsIgnoreCase("BO"))
		{
			workingCapital = new JLabel("* Working Capital Limit Sanctioned");
		}
		else if (appType.equalsIgnoreCase("WCE"))
		{
			workingCapital = new JLabel("* Working Capital Limit Sanctioned (Existing + New)");
		}
		else
		{
			workingCapital = new JLabel("Working Capital Limit Sanctioned");
		}
		workingCapital.setFont(headingFont);
		workingCapital.setOpaque(true);
		workingCapital.setBackground(dataBg);
		addComponent(2, 51, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, workingCapital, gridBag, constraints, commonPanel);

		/*		blankLabel=new JLabel(" ");
				blankLabel.setOpaque(true);
				blankLabel.setBackground(dataBg);
		//		constraints.gridheight=2;
				addComponent(0, 17, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, blankLabel, gridBag, constraints, panel);
				blankLabel.setBackground(dataBg);
				addComponent(1, 17, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, blankLabel, gridBag, constraints, panel);
		*/
		fundBased = new JLabel("Fund Based");
		fundBased.setFont(headingFont);
		fundBased.setOpaque(true);
		fundBased.setBackground(dataBg);
		constraints.gridheight = 1;
		addComponent(2, 52, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, fundBased, gridBag, constraints, commonPanel);

		txtWCFundBased = new JTextField(10);
		txtWCFundBased.setDocument(new DecimalFormatField(13,2));
		txtWCFundBased.addFocusListener(new ProjectOutlayListener());
//		addComponent(4, 52, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtWCFundBased, gridBag, constraints, commonPanel);

		inRs = new JLabel("in Rs.");
		inRs.setFont(hintFont);
		inRs.setOpaque(true);
		inRs.setBackground(dataBg);
		inRs.setHorizontalTextPosition(SwingConstants.LEFT);		
//		addComponent(5, 52, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, inRs, gridBag, constraints, commonPanel);		

		grpPanel = new JPanel();
		grpPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		grpPanel.setBackground(dataBg);
		grpPanel.add(txtWCFundBased);
		grpPanel.add(inRs);
		addComponent(3, 52, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, grpPanel, gridBag, constraints, commonPanel);

		nonFundBased = new JLabel("Non Fund Based");
		nonFundBased.setFont(headingFont);
		nonFundBased.setOpaque(true);
		nonFundBased.setBackground(dataBg);
		addComponent(2, 53, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, nonFundBased, gridBag, constraints, commonPanel);

		txtWCNonFundBased = new JTextField(10);
		txtWCNonFundBased.setDocument(new NumberFormatField(13));
		txtWCNonFundBased.addFocusListener(new ProjectOutlayListener());
//		addComponent(3, 53, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtWCNonFundBased, gridBag, constraints, commonPanel);

		inRs = new JLabel("in Rs.");
		inRs.setFont(hintFont);
		inRs.setOpaque(true);
		inRs.setBackground(dataBg);
		inRs.setHorizontalTextPosition(SwingConstants.LEFT);		
//		addComponent(5, 52, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, inRs, gridBag, constraints, commonPanel);		

		grpPanel = new JPanel();
		grpPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		grpPanel.setBackground(dataBg);
		grpPanel.add(txtWCNonFundBased);
		grpPanel.add(inRs);
		addComponent(3, 53, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, grpPanel, gridBag, constraints, commonPanel);

		marginMoney = new JLabel("Whether Provided as Margin Money as Term Loan");
		marginMoney.setFont(headingFont);
		marginMoney.setOpaque(true);
		marginMoney.setBackground(dataBg);
		constraints.gridheight = 1;
		addComponent(2, 54, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, marginMoney, gridBag, constraints, commonPanel);

		marginMoneyYes = new JRadioButton("Yes");
		marginMoneyNo = new JRadioButton("No");
		marginMoneyYes.setBackground(dataBg);
		marginMoneyNo.setBackground(dataBg);
		marginMoneyYes.setActionCommand("Yes");
		marginMoneyYes.addActionListener(new MarginMoneyListener());
		marginMoneyNo.setActionCommand("No");
		marginMoneyNo.addActionListener(new MarginMoneyListener());
		marginMoneyNo.setSelected(true);
		marginMoneyValue = new ButtonGroup();
		marginMoneyValue.add(marginMoneyYes);
		marginMoneyValue.add(marginMoneyNo);
		marginMoneyPanel = new JPanel();
		marginMoneyPanel.add(marginMoneyYes);
		marginMoneyPanel.add(marginMoneyNo);
		marginMoneyPanel.setBackground(dataBg);
		addComponent(3, 54, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, marginMoneyPanel, gridBag, constraints, commonPanel);

		tcPromotersContribution = new JLabel("Promoters Contribution");
		tcPromotersContribution.setFont(headingFont);
		tcPromotersContribution.setOpaque(true);
		tcPromotersContribution.setBackground(dataBg);
		addComponent(0, 55, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, tcPromotersContribution, gridBag, constraints, commonPanel);

		txtTCPromotersContribution = new JTextField(10);
		txtTCPromotersContribution.setDocument(new DecimalFormatField(13,2));
		txtTCPromotersContribution.addFocusListener(new ProjectOutlayListener());
//		addComponent(1, 55, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtTCPromotersContribution, gridBag, constraints, commonPanel);

		inRs = new JLabel("in Rs.");
		inRs.setFont(hintFont);
		inRs.setOpaque(true);
		inRs.setBackground(dataBg);
		inRs.setHorizontalTextPosition(SwingConstants.LEFT);
//		addComponent(2, 55, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, inRs, gridBag, constraints, commonPanel);

		grpPanel = new JPanel();
		grpPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		grpPanel.setBackground(dataBg);
		grpPanel.add(txtTCPromotersContribution);
		grpPanel.add(inRs);
		addComponent(1, 55, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, grpPanel, gridBag, constraints, commonPanel);

		wcPromotersContribution = new JLabel("Promoters Contribution");
		wcPromotersContribution.setFont(headingFont);
		wcPromotersContribution.setOpaque(true);
		wcPromotersContribution.setBackground(dataBg);
		addComponent(2, 55, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, wcPromotersContribution, gridBag, constraints, commonPanel);

		txtWCPromotersContribution = new JTextField(10);
		txtWCPromotersContribution.setDocument(new DecimalFormatField(13,2));
		txtWCPromotersContribution.addFocusListener(new ProjectOutlayListener());
//		addComponent(4, 55, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtWCPromotersContribution, gridBag, constraints, commonPanel);

		inRs = new JLabel("in Rs.");
		inRs.setFont(hintFont);
		inRs.setOpaque(true);
		inRs.setBackground(dataBg);
		inRs.setHorizontalTextPosition(SwingConstants.LEFT);		
//		addComponent(5, 55, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, inRs, gridBag, constraints, commonPanel);		

		grpPanel = new JPanel();
		grpPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		grpPanel.setBackground(dataBg);
		grpPanel.add(txtWCPromotersContribution);
		grpPanel.add(inRs);
		addComponent(3, 55, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, grpPanel, gridBag, constraints, commonPanel);

		tcSupport = new JLabel("Subsidy / Equity Support");
		tcSupport.setFont(headingFont);
		tcSupport.setOpaque(true);
		tcSupport.setBackground(dataBg);
		addComponent(0, 56, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, tcSupport, gridBag, constraints, commonPanel);

		txtTCSupport = new JTextField(10);
		txtTCSupport.setDocument(new DecimalFormatField(13,2));
		txtTCSupport.addFocusListener(new ProjectOutlayListener());
//		addComponent(1, 56, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtTCSupport, gridBag, constraints, commonPanel);

		inRs = new JLabel("in Rs.");
		inRs.setFont(hintFont);
		inRs.setOpaque(true);
		inRs.setBackground(dataBg);
		inRs.setHorizontalTextPosition(SwingConstants.LEFT);		
//		addComponent(2, 56, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, inRs, gridBag, constraints, commonPanel);

		grpPanel = new JPanel();
		grpPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		grpPanel.setBackground(dataBg);
		grpPanel.add(txtTCSupport);
		grpPanel.add(inRs);
		addComponent(1, 56, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, grpPanel, gridBag, constraints, commonPanel);

		wcSupport = new JLabel("Subsidy / Equity Support");
		wcSupport.setFont(headingFont);
		wcSupport.setOpaque(true);
		wcSupport.setBackground(dataBg);
		addComponent(2, 56, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, wcSupport, gridBag, constraints, commonPanel);

		txtWCSupport = new JTextField(10);
		txtWCSupport.setDocument(new DecimalFormatField(13,2));
		txtWCSupport.addFocusListener(new ProjectOutlayListener());
//		addComponent(3, 56, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtWCSupport, gridBag, constraints, commonPanel);

		inRs = new JLabel("in Rs.");
		inRs.setFont(hintFont);
		inRs.setOpaque(true);
		inRs.setBackground(dataBg);
		inRs.setHorizontalTextPosition(SwingConstants.LEFT);		
//		addComponent(5, 56, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, inRs, gridBag, constraints, commonPanel);

		grpPanel = new JPanel();
		grpPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		grpPanel.setBackground(dataBg);
		grpPanel.add(txtWCSupport);
		grpPanel.add(inRs);
		addComponent(3, 56, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, grpPanel, gridBag, constraints, commonPanel);

		tcOthers = new JLabel("Others");
		tcOthers.setFont(headingFont);
		tcOthers.setOpaque(true);
		tcOthers.setBackground(dataBg);
		addComponent(0, 57, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, tcOthers, gridBag, constraints, commonPanel);

		txtTCOthers = new JTextField(10);
		txtTCOthers.setDocument(new DecimalFormatField(13,2));
		txtTCOthers.addFocusListener(new ProjectOutlayListener());
//		addComponent(1, 57, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtTCOthers, gridBag, constraints, commonPanel);

		inRs = new JLabel("in Rs.");
		inRs.setFont(hintFont);
		inRs.setOpaque(true);
		inRs.setBackground(dataBg);
		inRs.setHorizontalTextPosition(SwingConstants.LEFT);		
//		addComponent(2, 57, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, inRs, gridBag, constraints, commonPanel);

		grpPanel = new JPanel();
		grpPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		grpPanel.setBackground(dataBg);
		grpPanel.add(txtTCOthers);
		grpPanel.add(inRs);
		addComponent(1, 57, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, grpPanel, gridBag, constraints, commonPanel);

		wcOthers = new JLabel("Others");
		wcOthers.setFont(headingFont);
		wcOthers.setOpaque(true);
		wcOthers.setBackground(dataBg);
		addComponent(2, 57, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, wcOthers, gridBag, constraints, commonPanel);

		txtWCOthers = new JTextField(10);
		txtWCOthers.setDocument(new DecimalFormatField(13,2));
		txtWCOthers.addFocusListener(new ProjectOutlayListener());
//		addComponent(4, 57, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtWCOthers, gridBag, constraints, commonPanel);

		inRs = new JLabel("in Rs.");
		inRs.setFont(hintFont);
		inRs.setOpaque(true);
		inRs.setBackground(dataBg);
		inRs.setHorizontalTextPosition(SwingConstants.LEFT);		
//		addComponent(5, 57, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, inRs, gridBag, constraints, commonPanel);*/

		grpPanel = new JPanel();
		grpPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		grpPanel.setBackground(dataBg);
		grpPanel.add(txtWCOthers);
		grpPanel.add(inRs);
		addComponent(3, 57, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, grpPanel, gridBag, constraints, commonPanel);

		projectCost = new JLabel("Project Cost");
		projectCost.setFont(headingFont);
		projectCost.setOpaque(true);
		projectCost.setBackground(dataBg);
		addComponent(0, 58, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, projectCost, gridBag, constraints, commonPanel);

		projectCostValue = new JLabel("");
		addComponent(1, 58, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, projectCostValue, gridBag, constraints, commonPanel);

		wcAssessed = new JLabel("Working Capital Assessed");
		wcAssessed.setFont(headingFont);
		wcAssessed.setOpaque(true);
		wcAssessed.setBackground(dataBg);
		addComponent(2, 58, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, wcAssessed, gridBag, constraints, commonPanel);

		wcAssessedValue = new JLabel("");
		addComponent(3, 58, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, wcAssessedValue, gridBag, constraints, commonPanel);

		projectOutlay = new JLabel("Project Outlay", JLabel.CENTER);
		projectOutlay.setFont(headingFont);
		projectOutlay.setOpaque(true);
		projectOutlay.setBackground(dataBg);
		addComponent(0, 59, 2, GridBagConstraints.EAST, GridBagConstraints.BOTH, projectOutlay, gridBag, constraints, commonPanel);

		projectOutlayValue = new JLabel("");
		addComponent(2, 59, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, projectOutlayValue, gridBag, constraints, commonPanel);

		container.removeAll();
		scrollPane.setViewportView(panel);
		container.add(scrollPane, BorderLayout.CENTER);
	}

	/**
	 * This method displays the borrower details.
	 */
	private void displayBorrowerPanel()
	{
		borrowerPanel.removeAll();
		borrowerPanel.setBackground(dataBg);
		borrowerPanel.setLayout(gridBag);

		borrowerHeading = new JLabel("Borrower Details");
		borrowerHeading.setFont(headingFont);
		borrowerHeading.setOpaque(true);
		borrowerHeading.setBackground(headingBg);
		addComponent(0, 0, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, borrowerHeading, gridBag, constraints, borrowerPanel);

		borrowerAssisted = new JLabel("Whether Already Assisted by Bank");
		borrowerAssisted.setFont(headingFont);
		borrowerAssisted.setOpaque(true);
		borrowerAssisted.setBackground(dataBg);
		addComponent(0, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, borrowerAssisted, gridBag, constraints, borrowerPanel);

		borrowerAssistedYes = new JRadioButton("Yes");
		borrowerAssistedNo = new JRadioButton("No");
		borrowerAssistedYes.setBackground(dataBg);
		borrowerAssistedNo.setBackground(dataBg);
		borrowerAssistedYes.setSelected(false);
		borrowerAssistedNo.setSelected(true);
		borrowerAssistedYes.setActionCommand("Yes");
		borrowerAssistedYes.addActionListener(new BorrowerAssistedListener());
		borrowerAssistedNo.setActionCommand("No");
		borrowerAssistedNo.addActionListener(new BorrowerAssistedListener());
		borrowerAssistedValue = new ButtonGroup();
		borrowerAssistedValue.add(borrowerAssistedYes);
		borrowerAssistedValue.add(borrowerAssistedNo);
		borrowerAssistedPanel = new JPanel();
		borrowerAssistedPanel.add(borrowerAssistedYes);
		borrowerAssistedPanel.add(borrowerAssistedNo);
		borrowerAssistedPanel.setBackground(dataBg);
		addComponent(1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, borrowerAssistedPanel, gridBag, constraints, borrowerPanel);

		osAmt = new JLabel("Outstanding Amount");
		osAmt.setFont(headingFont);
		osAmt.setOpaque(true);
		osAmt.setBackground(dataBg);
		addComponent(0, 2, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, osAmt, gridBag, constraints, borrowerPanel);

		grpPanel = new JPanel();
		grpPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		grpPanel.setBackground(dataBg);

		txtOSAmt = new JTextField(10);
		txtOSAmt.setDocument(new DecimalFormatField(13,2));
//		addComponent(1, 2, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtOSAmt, gridBag, constraints, borrowerPanel);

		inRs = new JLabel("in Rs.");
		inRs.setFont(hintFont);
		inRs.setOpaque(true);
		inRs.setBackground(dataBg);
		inRs.setHorizontalTextPosition(SwingConstants.LEFT);		
//		addComponent(2, 2, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.NONE, inRs, gridBag, constraints, borrowerPanel);
		
		grpPanel.add(txtOSAmt);
		grpPanel.add(inRs);
		addComponent(1, 2, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, grpPanel, gridBag, constraints, borrowerPanel);

		npa = new JLabel("NPA");
		npa.setFont(headingFont);
		npa.setOpaque(true);
		npa.setBackground(dataBg);
		addComponent(0, 3, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, npa, gridBag, constraints, borrowerPanel);

		npaYes = new JRadioButton("Yes");
		npaNo = new JRadioButton("No");
		npaYes.setBackground(dataBg);
		npaNo.setBackground(dataBg);
		npaNo.setSelected(true);
		npaValue = new ButtonGroup();
		npaValue.add(npaYes);
		npaValue.add(npaNo);
		npaPanel = new JPanel();
		npaPanel.add(npaYes);
		npaPanel.add(npaNo);
		npaPanel.setBackground(dataBg);
		addComponent(1, 3, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, npaPanel, gridBag, constraints, borrowerPanel);

		borrowerCovered = new JLabel("Whether Borrower Covered by CGTSI Previously");
		borrowerCovered.setFont(headingFont);
		borrowerCovered.setOpaque(true);
		borrowerCovered.setBackground(dataBg);
		addComponent(0, 4, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, borrowerCovered, gridBag, constraints, borrowerPanel);

		borrowerCoveredYes = new JRadioButton("Yes");
		borrowerCoveredNo = new JRadioButton("No");
		borrowerCoveredYes.setBackground(dataBg);
		borrowerCoveredNo.setBackground(dataBg);
		borrowerCoveredNo.setSelected(true);
		borrowerCoveredYes.setActionCommand("yes");
		borrowerCoveredYes.addActionListener(new BorrowerCoveredActionListener());
		borrowerCoveredNo.setActionCommand("no");
		borrowerCoveredNo.addActionListener(new BorrowerCoveredActionListener());
		borrowerCoveredValue = new ButtonGroup();
		borrowerCoveredValue.add(borrowerCoveredYes);
		borrowerCoveredValue.add(borrowerCoveredNo);
		borrowerCoveredPanel = new JPanel();
		borrowerCoveredPanel.add(borrowerCoveredYes);
		borrowerCoveredPanel.add(borrowerCoveredNo);
		borrowerCoveredPanel.setBackground(dataBg);
		addComponent(1, 4, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, borrowerCoveredPanel, gridBag, constraints, borrowerPanel);

		unitDetailsPanel.removeAll();
		unitDetailsPanel.setBackground(dataBg);
		unitDetailsPanel.setLayout(gridBag);

		none = new JRadioButton("None");
		cgpan = new JRadioButton("CGPAN");
		cgbid = new JRadioButton("CGBID");
		none.setBackground(dataBg);
		none.setSelected(true);
		cgpan.setSelected(false);
		cgbid.setSelected(false);
		cgpan.setEnabled(false);
		cgbid.setEnabled(false);
		cgpan.setBackground(dataBg);
		cgbid.setBackground(dataBg);
		none.setActionCommand("none");
		cgpan.setActionCommand("cgpan");
		cgbid.setActionCommand("cgbid");
		none.addActionListener(new BorrowerListener());
		cgpan.addActionListener(new BorrowerListener());
		cgbid.addActionListener(new BorrowerListener());
		ssiValue = new ButtonGroup();
		ssiValue.add(none);
		ssiValue.add(cgpan);
		ssiValue.add(cgbid);
		ssiPanel = new JPanel();
		ssiPanel.add(none);
		ssiPanel.add(cgpan);
		ssiPanel.add(cgbid);
		ssiPanel.setBackground(dataBg);
		addComponent(0, 0, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, ssiPanel, gridBag, constraints, unitDetailsPanel);

		txtSsiValue = new JTextField(new TextFormatField(13),"",10);
		txtSsiValue.setEnabled(false);
		addComponent(1, 0, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.NONE, txtSsiValue, gridBag, constraints, unitDetailsPanel);

		constitution = new JLabel("* Constitution");
		constitution.setFont(headingFont);
		constitution.setOpaque(true);
		constitution.setBackground(dataBg);
		addComponent(0, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, constitution, gridBag, constraints, unitDetailsPanel);

		String[] constNames = { "Select", "Proprietary", "Partnership", "Public Ltd.", "Private Ltd.", "Others" };
		cmbConstitution = new JComboBox(constNames);
		cmbConstitution.addItemListener(new ConstitutionListener());
		addComponent(1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, cmbConstitution, gridBag, constraints, unitDetailsPanel);

		txtConstitution=new JTextField(new TextFormatField(50),"",10);
		txtConstitution.setEnabled(false);
		addComponent(2, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtConstitution, gridBag, constraints, unitDetailsPanel);

		unitName = new JLabel("* Borrower / Unit Name");
		unitName.setFont(headingFont);
		unitName.setOpaque(true);
		unitName.setBackground(dataBg);
		addComponent(0, 2, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, unitName, gridBag, constraints, unitDetailsPanel);

		String[] unitNames = { "Select", "M/s", "Shri", "Smt", "Ku" };
		cmbUnitNames = new JComboBox(unitNames);
		addComponent(1, 2, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, cmbUnitNames, gridBag, constraints, unitDetailsPanel);

		txtUnitName=new JTextField(new TextFormatField(100),"",30);
		addComponent(2, 2, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtUnitName, gridBag, constraints, unitDetailsPanel);

		address = new JLabel("* Address");
		address.setFont(headingFont);
		address.setOpaque(true);
		address.setBackground(dataBg);
		addComponent(0, 3, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, address, gridBag, constraints, unitDetailsPanel);

		txtAddress=new JTextArea(2, 20);
		txtAddress.setLineWrap(true);
		JScrollPane txtAddressPane=new JScrollPane(txtAddress,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		addComponent(1, 3, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtAddressPane, gridBag, constraints, unitDetailsPanel);
		
		grpPanel = new JPanel();
		grpPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		grpPanel.setBackground(dataBg);

		state = new JLabel("* State");
		state.setFont(headingFont);
		state.setOpaque(true);
		state.setBackground(dataBg);
//		addComponent(2, 3, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, state, gridBag, constraints, unitDetailsPanel);
		
		//String[] states = { "Select", "Tamil Nadu", "Kerala" };
		Vector states = new Vector(getStates());
		cmbState = new JComboBox(states);
		cmbState.addItemListener(new StateListener());
//		addComponent(3, 3, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, cmbState, gridBag, constraints, unitDetailsPanel);
		
		grpPanel.add(state);
		grpPanel.add(cmbState);
		addComponent(2, 3, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, grpPanel, gridBag, constraints, unitDetailsPanel);

		district = new JLabel("* District");
		district.setFont(headingFont);
		district.setOpaque(true);
		district.setBackground(dataBg);
		addComponent(0, 4, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, district, gridBag, constraints, unitDetailsPanel);

		grpPanel = new JPanel();
		grpPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		grpPanel.setBackground(dataBg);

		String[] districts = {"Select"};
		cmbDistrict = new JComboBox(districts);
		cmbDistrict.addItemListener(new DistrictListener());
//		addComponent(1, 4, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, cmbDistrict, gridBag, constraints, unitDetailsPanel);

		txtDistrict=new JTextField(new TextFormatField(100),"",10);
		txtDistrict.setEnabled(false);
//		addComponent(2, 4, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtDistrict, gridBag, constraints, unitDetailsPanel);
		
		grpPanel.add(cmbDistrict);
		grpPanel.add(txtDistrict);
		addComponent(1, 4, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, grpPanel, gridBag, constraints, unitDetailsPanel);

		grpPanel = new JPanel();
		grpPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		grpPanel.setBackground(dataBg);		

		city = new JLabel("* City");
		city.setFont(headingFont);
		city.setOpaque(true);
		city.setBackground(dataBg);
//		addComponent(2, 4, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, city, gridBag, constraints, unitDetailsPanel);

		txtCity=new JTextField(new TextFormatField(100),"",10);
//		addComponent(3, 4, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtCity, gridBag, constraints, unitDetailsPanel);

		grpPanel.add(city);
		grpPanel.add(txtCity);
		addComponent(2, 4, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, grpPanel, gridBag, constraints, unitDetailsPanel);

		pinCode = new JLabel("* PIN code");
		pinCode.setFont(headingFont);
		pinCode.setOpaque(true);
		pinCode.setBackground(dataBg);
		addComponent(0, 5, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, pinCode, gridBag, constraints, unitDetailsPanel);

		txtPincode=new JTextField(10);
		txtPincode.setDocument(new NumberFormatField(6));
		addComponent(1, 5, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtPincode, gridBag, constraints, unitDetailsPanel);

		itpanFirm = new JLabel("ITPAN of Firm");
		itpanFirm.setFont(headingFont);
		itpanFirm.setOpaque(true);
		itpanFirm.setBackground(dataBg);
		addComponent(0, 6, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, itpanFirm, gridBag, constraints, unitDetailsPanel);

		txtFirmItpan=new JTextField(new TextFormatField(10),"",10);
		addComponent(1, 6, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtFirmItpan, gridBag, constraints, unitDetailsPanel);

		ssiRegNo = new JLabel("SSI Registration Number");
		ssiRegNo.setFont(headingFont);
		ssiRegNo.setOpaque(true);
		ssiRegNo.setBackground(dataBg);
//		addComponent(2, 6, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, ssiRegNo, gridBag, constraints, unitDetailsPanel);

		txtSsiRegNo=new JTextField(new TextFormatField(25),"",10);
//		addComponent(3, 6, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtSsiRegNo, gridBag, constraints, unitDetailsPanel);

		grpPanel = new JPanel();
		grpPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		grpPanel.setBackground(dataBg);		
		grpPanel.add(ssiRegNo);
		grpPanel.add(txtSsiRegNo);
		addComponent(2, 6, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, grpPanel, gridBag, constraints, unitDetailsPanel);

		indNature = new JLabel("Nature of Industry");
		indNature.setFont(headingFont);
		indNature.setOpaque(true);
		indNature.setBackground(dataBg);
		addComponent(0, 7, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, indNature, gridBag, constraints, unitDetailsPanel);

//		String[] indNatures = { "Select", "Manufacturing", "Service" };
		Vector indNatures = new Vector(getIndustryNatures());
		cmbIndNature = new JComboBox(indNatures);
		cmbIndNature.addItemListener(new IndustryNatureListener());
		addComponent(1, 7, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, cmbIndNature, gridBag, constraints, unitDetailsPanel);

		indSector = new JLabel("Industry Sector");
		indSector.setFont(headingFont);
		indSector.setOpaque(true);
		indSector.setBackground(dataBg);
//		addComponent(2, 7, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, indSector, gridBag, constraints, unitDetailsPanel);

		String[] indSectors = { "Select" };
		cmbIndSector = new JComboBox(indSectors);
//		addComponent(3, 7, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, cmbIndSector, gridBag, constraints, unitDetailsPanel);

		grpPanel = new JPanel();
		grpPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		grpPanel.setBackground(dataBg);		
		grpPanel.add(indSector);
		grpPanel.add(cmbIndSector);
		addComponent(2, 7, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, grpPanel, gridBag, constraints, unitDetailsPanel);

		activityType = new JLabel("* Type of Activity");
		activityType.setFont(headingFont);
		activityType.setOpaque(true);
		activityType.setBackground(dataBg);
		addComponent(0, 8, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, activityType, gridBag, constraints, unitDetailsPanel);

		txtActivity=new JTextField(new TextFormatField(50),"",10);
		addComponent(1, 8, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtActivity, gridBag, constraints, unitDetailsPanel);

		noOfEmp = new JLabel("No of Employees");
		noOfEmp.setFont(headingFont);
		noOfEmp.setOpaque(true);
		noOfEmp.setBackground(dataBg);
//		addComponent(2, 8, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, noOfEmp, gridBag, constraints, unitDetailsPanel);

		txtNoOfEmp=new JTextField(10);
		txtNoOfEmp.setDocument(new NumberFormatField(10));
//		addComponent(3, 8, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtNoOfEmp, gridBag, constraints, unitDetailsPanel);

		grpPanel = new JPanel();
		grpPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		grpPanel.setBackground(dataBg);		
		grpPanel.add(noOfEmp);
		grpPanel.add(txtNoOfEmp);
		addComponent(2, 8, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, grpPanel, gridBag, constraints, unitDetailsPanel);

		optSalesTurnOver = new JLabel("Projected Optimum Sales Turnover");
		optSalesTurnOver.setFont(headingFont);
		optSalesTurnOver.setOpaque(true);
		optSalesTurnOver.setBackground(dataBg);
		addComponent(0, 9, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, optSalesTurnOver, gridBag, constraints, unitDetailsPanel);

		grpPanel = new JPanel();
		grpPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		grpPanel.setBackground(dataBg);

		txtOptSalesTurnover=new JTextField(10);
		txtOptSalesTurnover.setDocument(new DecimalFormatField(13,2));
//		addComponent(1, 9, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtOptSalesTurnover, gridBag, constraints, unitDetailsPanel);

		inRs = new JLabel("in Rs.");
		inRs.setFont(hintFont);
		inRs.setOpaque(true);
		inRs.setBackground(dataBg);
		inRs.setHorizontalTextPosition(SwingConstants.LEFT);
		
		grpPanel.add(txtOptSalesTurnover);
		grpPanel.add(inRs);
		addComponent(1, 9, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, grpPanel, gridBag, constraints, unitDetailsPanel);

		optExports = new JLabel("Projected Optimum Exports");
		optExports.setFont(headingFont);
		optExports.setOpaque(true);
		optExports.setBackground(dataBg);
//		addComponent(2, 9, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, optExports, gridBag, constraints, unitDetailsPanel);

		txtOptExports=new JTextField(10);
		txtOptExports.setDocument(new DecimalFormatField(13,2));
//		addComponent(3, 9, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtOptExports, gridBag, constraints, unitDetailsPanel);

		inRs = new JLabel("in Rs.");
		inRs.setFont(hintFont);
		inRs.setOpaque(true);
		inRs.setBackground(dataBg);
		inRs.setHorizontalTextPosition(SwingConstants.LEFT);

		grpPanel = new JPanel();
		grpPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		grpPanel.setBackground(dataBg);	
		grpPanel.add(optExports);	
		grpPanel.add(txtOptExports);
		grpPanel.add(inRs);
		addComponent(2, 9, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, grpPanel, gridBag, constraints, unitDetailsPanel);

		addComponent(0, 5, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.NONE, unitDetailsPanel, gridBag, constraints, borrowerPanel);
		
	}

	/**
	 * This method displays the promoters details
	 */
	private void displayPromotersPanel()
	{
		promotersPanel.removeAll();
		promotersPanel.setBackground(dataBg);
		promotersPanel.setLayout(gridBag);

		promoterHeading = new JLabel("Promoter Details");
		promoterHeading.setFont(headingFont);
		promoterHeading.setOpaque(true);
		promoterHeading.setBackground(headingBg);
		addComponent(0, 0, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, promoterHeading, gridBag, constraints, promotersPanel);

		chiefPromoterInfo = new JLabel("Chief Promoter's Information");
		chiefPromoterInfo.setFont(headingFont);
		chiefPromoterInfo.setOpaque(true);
		chiefPromoterInfo.setBackground(dataBg);
		addComponent(0, 1, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, chiefPromoterInfo, gridBag, constraints, promotersPanel);

		title = new JLabel("* Title");
		title.setFont(headingFont);
		title.setOpaque(true);
		title.setBackground(dataBg);
		addComponent(1, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, title, gridBag, constraints, promotersPanel);

		firstName = new JLabel("* First Name");
		firstName.setFont(headingFont);
		firstName.setOpaque(true);
		firstName.setBackground(dataBg);
		addComponent(2, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, firstName, gridBag, constraints, promotersPanel);

		middleName = new JLabel("Middle Name");
		middleName.setFont(headingFont);
		middleName.setOpaque(true);
		middleName.setBackground(dataBg);
		addComponent(3, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, middleName, gridBag, constraints, promotersPanel);

		lastName = new JLabel("* Last Name (Surname)");
		lastName.setFont(headingFont);
		lastName.setOpaque(true);
		lastName.setBackground(dataBg);
		addComponent(4, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, lastName, gridBag, constraints, promotersPanel);

		name = new JLabel("Name");
		name.setFont(headingFont);
		name.setOpaque(true);
		name.setBackground(dataBg);
		addComponent(0, 3, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, name, gridBag, constraints, promotersPanel);

		String[] titles = { "Select", "Mr.", "Smt", "Ku" };
		cmbTitle = new JComboBox(titles);
		cmbTitle.addItemListener(new NameTitleListener());
		addComponent(1, 3, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, cmbTitle, gridBag, constraints, promotersPanel);

		txtFirstName=new JTextField(new TextFormatField(20),"",10);
		addComponent(2, 3, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtFirstName, gridBag, constraints, promotersPanel);

		txtMiddleName=new JTextField(new TextFormatField(20),"",10);
		addComponent(3, 3, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtMiddleName, gridBag, constraints, promotersPanel);

		txtLastName=new JTextField(new TextFormatField(20),"",10);
		addComponent(4, 3, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtLastName, gridBag, constraints, promotersPanel);

		gender = new JLabel("* Gender");
		gender.setFont(headingFont);
		gender.setOpaque(true);
		gender.setBackground(dataBg);
		addComponent(0, 4, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, gender, gridBag, constraints, promotersPanel);

		male = new JRadioButton("M");
		female = new JRadioButton("F");
		male.setBackground(dataBg);
		female.setBackground(dataBg);
		male.setSelected(true);
		genderValue = new ButtonGroup();
		genderValue.add(male);
		genderValue.add(female);
		genderPanel = new JPanel();
		genderPanel.add(male);
		genderPanel.add(female);
		genderPanel.setBackground(dataBg);
		addComponent(1, 4, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, genderPanel, gridBag, constraints, promotersPanel);

		itpanChiefPromoter = new JLabel("ITPAN of Chief Promoter");
		itpanChiefPromoter.setFont(headingFont);
		itpanChiefPromoter.setOpaque(true);
		itpanChiefPromoter.setBackground(dataBg);
		addComponent(2, 4, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, itpanChiefPromoter, gridBag, constraints, promotersPanel);

		txtChiefPromoterItpan=new JTextField(new TextFormatField(10),"",10);
		addComponent(3, 4, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtChiefPromoterItpan, gridBag, constraints, promotersPanel);

		dob = new JLabel("Date of Birth");
		dob.setFont(headingFont);
		dob.setOpaque(true);
		dob.setBackground(dataBg);
		addComponent(0, 5, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, dob, gridBag, constraints, promotersPanel);

		txtDOB=new JTextField(10);
		setDateFieldProperties(txtDOB, "dd/MM/yyyy", false);
		addComponent(1, 5, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtDOB, gridBag, constraints, promotersPanel);

		socialCategory = new JLabel("Social Category");
		socialCategory.setFont(headingFont);
		socialCategory.setOpaque(true);
		socialCategory.setBackground(dataBg);
		addComponent(2, 5, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, socialCategory, gridBag, constraints, promotersPanel);

//		String socialCats[] = {"Select", "BC", "FC", "SC"};
		Vector socialCats = new Vector(getSocialCategories());
		cmbSocialCategory = new JComboBox(socialCats);
		addComponent(3, 5, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, cmbSocialCategory, gridBag, constraints, promotersPanel);

		legalId = new JLabel("Legal ID");
		legalId.setFont(headingFont);
		legalId.setOpaque(true);
		legalId.setBackground(dataBg);
		addComponent(0, 6, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, legalId, gridBag, constraints, promotersPanel);

		String[] legalIds = { "Select", "Voter Identity Card", "Passport Number", "Driving License", "Ration Card Number", "Others" };
		cmbLegalId = new JComboBox(legalIds);
		cmbLegalId.addItemListener(new LegalIdListener());
		addComponent(1, 6, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, cmbLegalId, gridBag, constraints, promotersPanel);

		txtOtherId=new JTextField(new TextFormatField(50),"",10);
		txtOtherId.setEnabled(false);
//		addComponent(2, 6, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtOtherId, gridBag, constraints, promotersPanel);

		otherId = new JLabel("(Other ID)");
		otherId.setFont(headingFont);
		otherId.setOpaque(true);
		otherId.setBackground(dataBg);
		
		grpPanel = new JPanel();
		grpPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		grpPanel.setBackground(dataBg);	
		grpPanel.add(txtOtherId);	
		grpPanel.add(otherId);		
		addComponent(2, 6, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, grpPanel, gridBag, constraints, promotersPanel);

		plsSpecify = new JLabel("Please Specify");
		plsSpecify.setFont(headingFont);
		plsSpecify.setOpaque(true);
		plsSpecify.setBackground(dataBg);
		addComponent(3, 6, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, plsSpecify, gridBag, constraints, promotersPanel);

		txtLegalValue=new JTextField(new TextFormatField(20),"",10);
//		addComponent(4, 6, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtLegalValue, gridBag, constraints, promotersPanel);

		id = new JLabel("ID");
		id.setFont(headingFont);
		id.setOpaque(true);
		id.setBackground(dataBg);

		grpPanel = new JPanel();
		grpPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		grpPanel.setBackground(dataBg);	
		grpPanel.add(txtLegalValue);	
		grpPanel.add(id);
		
		addComponent(4, 6, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, grpPanel, gridBag, constraints, promotersPanel);

		otherPromotersPanel.removeAll();
		otherPromotersPanel.setBackground(dataBg);
		otherPromotersPanel.setLayout(gridBag);

		otherPromoters = new JLabel("Other Main Promoters");
		otherPromoters.setFont(headingFont);
		otherPromoters.setOpaque(true);
		otherPromoters.setBackground(dataBg);
		otherPromoters.setForeground(fontColor);
		addComponent(0, 0, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, otherPromoters, gridBag, constraints, otherPromotersPanel);

		promoterName = new JLabel("1. Name");
		promoterName.setFont(headingFont);
		promoterName.setOpaque(true);
		promoterName.setBackground(dataBg);
		addComponent(0, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, promoterName, gridBag, constraints, otherPromotersPanel);

		txtOtherPromotersName1=new JTextField(new TextFormatField(50),"",10);
		addComponent(1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtOtherPromotersName1, gridBag, constraints, otherPromotersPanel);

		itpanPromoter = new JLabel("ITPAN");
		itpanPromoter.setFont(headingFont);
		itpanPromoter.setOpaque(true);
		itpanPromoter.setBackground(dataBg);
		addComponent(2, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, itpanPromoter, gridBag, constraints, otherPromotersPanel);

		txtOtherPromotersItpan1=new JTextField(new TextFormatField(10),"",10);
		addComponent(3, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtOtherPromotersItpan1, gridBag, constraints, otherPromotersPanel);

		dobPromoter = new JLabel("Date of Birth");
		dobPromoter.setFont(headingFont);
		dobPromoter.setOpaque(true);
		dobPromoter.setBackground(dataBg);
		addComponent(4, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, dobPromoter, gridBag, constraints, otherPromotersPanel);

		txtOtherPromotersDob1=new JTextField(10);
		setDateFieldProperties(txtOtherPromotersDob1, "dd/MM/yyyy", false);
		addComponent(5, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtOtherPromotersDob1, gridBag, constraints, otherPromotersPanel);

		promoterName = new JLabel("2. Name");
		promoterName.setFont(headingFont);
		promoterName.setOpaque(true);
		promoterName.setBackground(dataBg);
		addComponent(0, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, promoterName, gridBag, constraints, otherPromotersPanel);

		txtOtherPromotersName2=new JTextField(new TextFormatField(50),"",10);
		addComponent(1, 2, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtOtherPromotersName2, gridBag, constraints, otherPromotersPanel);

		itpanPromoter = new JLabel("ITPAN");
		itpanPromoter.setFont(headingFont);
		itpanPromoter.setOpaque(true);
		itpanPromoter.setBackground(dataBg);
		addComponent(2, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, itpanPromoter, gridBag, constraints, otherPromotersPanel);

		txtOtherPromotersItpan2=new JTextField(new TextFormatField(10),"",10);
		addComponent(3, 2, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtOtherPromotersItpan2, gridBag, constraints, otherPromotersPanel);

		dobPromoter = new JLabel("Date of Birth");
		dobPromoter.setFont(headingFont);
		dobPromoter.setOpaque(true);
		dobPromoter.setBackground(dataBg);
		addComponent(4, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, dobPromoter, gridBag, constraints, otherPromotersPanel);

		txtOtherPromotersDob2=new JTextField(10);
		setDateFieldProperties(txtOtherPromotersDob2, "dd/MM/yyyy", false);
		addComponent(5, 2, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtOtherPromotersDob2, gridBag, constraints, otherPromotersPanel);

		promoterName = new JLabel("3. Name");
		promoterName.setFont(headingFont);
		promoterName.setOpaque(true);
		promoterName.setBackground(dataBg);
		addComponent(0, 3, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, promoterName, gridBag, constraints, otherPromotersPanel);

		txtOtherPromotersName3=new JTextField(new TextFormatField(50),"",10);
		addComponent(1, 3, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtOtherPromotersName3, gridBag, constraints, otherPromotersPanel);

		itpanPromoter = new JLabel("ITPAN");
		itpanPromoter.setFont(headingFont);
		itpanPromoter.setOpaque(true);
		itpanPromoter.setBackground(dataBg);
		addComponent(2, 3, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, itpanPromoter, gridBag, constraints, otherPromotersPanel);

		txtOtherPromotersItpan3=new JTextField(new TextFormatField(10),"",10);
		addComponent(3, 3, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtOtherPromotersItpan3, gridBag, constraints, otherPromotersPanel);

		dobPromoter = new JLabel("Date of Birth");
		dobPromoter.setFont(headingFont);
		dobPromoter.setOpaque(true);
		dobPromoter.setBackground(dataBg);
		addComponent(4, 3, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, dobPromoter, gridBag, constraints, otherPromotersPanel);

		txtOtherPromotersDob3=new JTextField(10);
		setDateFieldProperties(txtOtherPromotersDob3, "dd/MM/yyyy", false);
		addComponent(5, 3, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtOtherPromotersDob3, gridBag, constraints, otherPromotersPanel);

		addComponent(0, 7, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, otherPromotersPanel, gridBag, constraints, promotersPanel);
	}

	/**
	 * This method displays the facility details of the application.
	 */
	 private void displayFacilityPanel(boolean rehabFlag)
	 {
		facilityPanel.removeAll();
		facilityPanel.setBackground(dataBg);
		facilityPanel.setLayout(gridBag);

		facilityHeading = new JLabel("Facility Details");
		facilityHeading.setFont(headingFont);
		facilityHeading.setOpaque(true);
		facilityHeading.setBackground(headingBg);
		addComponent(0, 0, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, facilityHeading, gridBag, constraints, facilityPanel);

		if (rehabFlag)
		{
			rehabilitation = new JLabel("Whether Rehabilitation");
			rehabilitation.setFont(headingFont);
			rehabilitation.setOpaque(true);
			rehabilitation.setBackground(dataBg);
			addComponent(0, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, rehabilitation, gridBag, constraints, facilityPanel);

			rehabilitationYes = new JRadioButton("Yes");
			rehabilitationNo = new JRadioButton("No");
			rehabilitationYes.setBackground(dataBg);
			rehabilitationNo.setBackground(dataBg);
			rehabilitationNo.setSelected(true);
			rehabilitationValue = new ButtonGroup();
			rehabilitationValue.add(rehabilitationYes);
			rehabilitationValue.add(rehabilitationNo);
			rehabilitationPanel = new JPanel();
			rehabilitationPanel.add(rehabilitationYes);
			rehabilitationPanel.add(rehabilitationNo);
			rehabilitationPanel.setBackground(dataBg);
			addComponent(1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, rehabilitationPanel, gridBag, constraints, facilityPanel);
		}

		composite = new JLabel("Composite Loan");
		composite.setFont(headingFont);
		composite.setOpaque(true);
		composite.setBackground(dataBg);
		addComponent(0, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, composite, gridBag, constraints, facilityPanel);

		compositeValue = new JLabel("N");
		compositeValue.setFont(dataFont);
		compositeValue.setOpaque(true);
		compositeValue.setBackground(dataBg);
		addComponent(1, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, compositeValue, gridBag, constraints, facilityPanel);

		loanFacility = new JLabel("Type of Loan Facility");
		loanFacility.setFont(headingFont);
		loanFacility.setOpaque(true);
		loanFacility.setBackground(dataBg);
		addComponent(0, 3, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, loanFacility, gridBag, constraints, facilityPanel);

		loanFacilityValue = new JLabel("TC");
		loanFacilityValue.setFont(dataFont);
		loanFacilityValue.setOpaque(true);
		loanFacilityValue.setBackground(dataBg);
		addComponent(1, 3, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, loanFacilityValue, gridBag, constraints, facilityPanel);

		scheme =new JLabel("Scheme");
		scheme.setFont(headingFont);
		scheme.setOpaque(true);
		scheme.setBackground(dataBg);
		addComponent(0, 4, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, scheme, gridBag, constraints, facilityPanel);

		schemeName =new JLabel("CGFSI");
		schemeName.setFont(dataFont);
		schemeName.setOpaque(true);
		schemeName.setBackground(dataBg);
		addComponent(1, 4, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, schemeName, gridBag, constraints, facilityPanel);
	}

	/**
	* This method displays the term credit panel.
	*/
	private void displayTCPanel()
	{
		tcPanel.removeAll();
		tcPanel.setBackground(dataBg);
		tcPanel.setLayout(gridBag);

		termCreditHeading = new JLabel("Term Credit Details");
		termCreditHeading.setFont(headingFont);
		termCreditHeading.setOpaque(true);
		termCreditHeading.setBackground(headingBg);
		addComponent(0, 0, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, termCreditHeading, gridBag, constraints, tcPanel);

		amtSanctioned = new JLabel("Amount Sanctioned");
		amtSanctioned.setFont(headingFont);
		amtSanctioned.setOpaque(true);
		amtSanctioned.setBackground(dataBg);
		addComponent(0, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, amtSanctioned, gridBag, constraints, tcPanel);

		amtSanctionedValue = new JLabel();
		addComponent(1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, amtSanctionedValue, gridBag, constraints, tcPanel);

		dateOfSanction = new JLabel("* Date of Sanction");
		dateOfSanction.setFont(headingFont);
		dateOfSanction.setOpaque(true);
		dateOfSanction.setBackground(dataBg);
		addComponent(2, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, dateOfSanction, gridBag, constraints, tcPanel);

		txtDateOfSanction = new JTextField(10);
		setDateFieldProperties(txtDateOfSanction, "dd/MM/yyyy", false);
		addComponent(3, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtDateOfSanction, gridBag, constraints, tcPanel);

		creditGuarantee = new JLabel("* Credit to be Guaranteed");
		creditGuarantee.setFont(headingFont);
		creditGuarantee.setOpaque(true);
		creditGuarantee.setBackground(dataBg);
		addComponent(0, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, creditGuarantee, gridBag, constraints, tcPanel);

		txtCreditToBeGuaranteed = new JTextField(10);
		txtCreditToBeGuaranteed.setDocument(new DecimalFormatField(13,2));
//		addComponent(1, 2, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtCreditToBeGuaranteed, gridBag, constraints, tcPanel);

		inRs = new JLabel("in Rs.");
		inRs.setFont(hintFont);
		inRs.setOpaque(true);
		inRs.setBackground(dataBg);
		inRs.setHorizontalTextPosition(SwingConstants.LEFT);		
//		addComponent(2, 2, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, inRs, gridBag, constraints, tcPanel);

		grpPanel = new JPanel();
		grpPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		grpPanel.setBackground(dataBg);
		grpPanel.add(txtCreditToBeGuaranteed);
		grpPanel.add(inRs);
		addComponent(1, 2, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, grpPanel, gridBag, constraints, tcPanel);

		amtDisbursed = new JLabel("Amount Disbursed");
		amtDisbursed.setFont(headingFont);
		amtDisbursed.setOpaque(true);
		amtDisbursed.setBackground(dataBg);
		addComponent(0, 3, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, amtDisbursed, gridBag, constraints, tcPanel);

		txtAmtDisbursed = new JTextField(10);
		txtAmtDisbursed.setDocument(new DecimalFormatField(13,2));
//		addComponent(1, 3, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtAmtDisbursed, gridBag, constraints, tcPanel);

		inRs = new JLabel("in Rs.");
		inRs.setFont(hintFont);
		inRs.setOpaque(true);
		inRs.setBackground(dataBg);
		inRs.setHorizontalTextPosition(SwingConstants.LEFT);		
		inRs.setHorizontalAlignment(SwingConstants.LEFT);
//		addComponent(2, 3, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, inRs, gridBag, constraints, tcPanel);*/		

		grpPanel = new JPanel();
		grpPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		grpPanel.setBackground(dataBg);
		grpPanel.add(txtAmtDisbursed);
		grpPanel.add(inRs);
		addComponent(1, 3, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, grpPanel, gridBag, constraints, tcPanel);

		dateOfFirstDisbursement = new JLabel("Date of First Disbursement, if already made");
		dateOfFirstDisbursement.setFont(headingFont);
		dateOfFirstDisbursement.setOpaque(true);
		dateOfFirstDisbursement.setBackground(dataBg);
		addComponent(2, 3, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, dateOfFirstDisbursement, gridBag, constraints, tcPanel);

		txtDateOfFirstDisbursement = new JTextField(10);
		setDateFieldProperties(txtDateOfFirstDisbursement, "dd/MM/yyyy", false);
		addComponent(3, 3, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtDateOfFirstDisbursement, gridBag, constraints, tcPanel);

		dateOfLastDisbursement = new JLabel("Date of Last and Final Disbursement, if already made");
		dateOfLastDisbursement.setFont(headingFont);
		dateOfLastDisbursement.setOpaque(true);
		dateOfLastDisbursement.setBackground(dataBg);
		addComponent(2, 4, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, dateOfLastDisbursement, gridBag, constraints, tcPanel);

		txtDateOfLastDisbursement = new JTextField(10);
		setDateFieldProperties(txtDateOfLastDisbursement, "dd/MM/yyyy", false);
		addComponent(3, 4, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtDateOfLastDisbursement, gridBag, constraints, tcPanel);

		tenure = new JLabel("* Tenure (in months)");
		tenure.setFont(headingFont);
		tenure.setOpaque(true);
		tenure.setBackground(dataBg);
		addComponent(0, 5, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, tenure, gridBag, constraints, tcPanel);

		txtTenure = new JTextField(10);
		txtTenure.setDocument(new DecimalFormatField(13,2));
		addComponent(1, 5, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtTenure, gridBag, constraints, tcPanel);

		intType = new JLabel("Interest Type");
		intType.setFont(headingFont);
		intType.setOpaque(true);
		intType.setBackground(dataBg);
		addComponent(0, 6, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, intType, gridBag, constraints, tcPanel);

		interestFixed = new JRadioButton("Fixed");
		interestFloating = new JRadioButton("Floating");
		interestFixed.setBackground(dataBg);
		interestFixed.setSelected(true);
		interestFloating.setBackground(dataBg);
		interestValue = new ButtonGroup();
		interestValue.add(interestFixed);
		interestValue.add(interestFloating);
		interestPanel = new JPanel();
		interestPanel.add(interestFixed);
		interestPanel.add(interestFloating);
		interestPanel.setBackground(dataBg);
		addComponent(1, 6, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, interestPanel, gridBag, constraints, tcPanel);

		plrType = new JLabel("* PLR Type");
		plrType.setFont(headingFont);
		plrType.setOpaque(true);
		plrType.setBackground(dataBg);
		addComponent(2, 6, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, plrType, gridBag, constraints, tcPanel);

		txtPlrType=new JTextField(new TextFormatField(50),"",10);
		addComponent(3, 6, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtPlrType, gridBag, constraints, tcPanel);
		
		plr = new JLabel("* Prime Lending Rate");
		plr.setFont(headingFont);
		plr.setOpaque(true);
		plr.setBackground(dataBg);
		addComponent(0, 7, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, plr, gridBag, constraints, tcPanel);

		txtPlr=new JTextField(10);
		txtPlr.setDocument(new DecimalFormatField(2, 2));
//		addComponent(1, 7, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtPlr, gridBag, constraints, tcPanel);

		perInt = new JLabel("% p.a.");
		perInt.setFont(hintFont);
		perInt.setOpaque(true);
		perInt.setBackground(dataBg);
		perInt.setHorizontalTextPosition(SwingConstants.LEFT);		
//		addComponent(2, 7, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, perInt, gridBag, constraints, tcPanel);*/

		grpPanel = new JPanel();
		grpPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		grpPanel.setBackground(dataBg);
		grpPanel.add(txtPlr);
		grpPanel.add(perInt);
		addComponent(1, 7, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, grpPanel, gridBag, constraints, tcPanel);

		intRate = new JLabel("* Interest rate");
		intRate.setFont(headingFont);
		intRate.setOpaque(true);
		intRate.setBackground(dataBg);
		addComponent(2, 7, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, intRate, gridBag, constraints, tcPanel);

		txtTcInterestRate = new JTextField(10);
		txtTcInterestRate.setDocument(new DecimalFormatField(2, 2));
//		addComponent(4, 7, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtTcInterestRate, gridBag, constraints, tcPanel);

		perInt = new JLabel("% p.a.");
		perInt.setFont(hintFont);
		perInt.setOpaque(true);
		perInt.setBackground(dataBg);
		perInt.setHorizontalTextPosition(SwingConstants.LEFT);		
//		addComponent(5, 7, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, perInt, gridBag, constraints, tcPanel);*/		

		grpPanel = new JPanel();
		grpPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		grpPanel.setBackground(dataBg);
		grpPanel.add(txtTcInterestRate);
		grpPanel.add(perInt);
		addComponent(3, 7, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, grpPanel, gridBag, constraints, tcPanel);

		repaymentSchedule = new JLabel("Repayment Schedule");
		repaymentSchedule.setFont(headingFont);
		repaymentSchedule.setOpaque(true);
		repaymentSchedule.setBackground(dataBg);
		addComponent(0, 7, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, repaymentSchedule, gridBag, constraints, tcPanel);

		moratorium = new JLabel("Moratorium", SwingConstants.CENTER);
		moratorium.setFont(headingFont);
		moratorium.setOpaque(true);
		moratorium.setBackground(dataBg);
		addComponent(0, 9, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, moratorium, gridBag, constraints, tcPanel);

		txtMoratorium = new JTextField(10);
		txtMoratorium.setDocument(new NumberFormatField(3));
//		addComponent(1, 9, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtMoratorium, gridBag, constraints, tcPanel);

		perInt = new JLabel("in Months");
		perInt.setFont(hintFont);
		perInt.setOpaque(true);
		perInt.setBackground(dataBg);
		perInt.setHorizontalTextPosition(SwingConstants.LEFT);

		grpPanel = new JPanel();
		grpPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		grpPanel.setBackground(dataBg);
		grpPanel.add(txtMoratorium);
		grpPanel.add(perInt);
		addComponent(1, 9, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, grpPanel, gridBag, constraints, tcPanel);

		firstInstDueDate = new JLabel("First Instalment Due Date", SwingConstants.CENTER);
		firstInstDueDate.setFont(headingFont);
		firstInstDueDate.setOpaque(true);
		firstInstDueDate.setBackground(dataBg);
		addComponent(0, 10, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, firstInstDueDate, gridBag, constraints, tcPanel);

		txtFirstInstDueDate = new JTextField(10);
		setDateFieldProperties(txtFirstInstDueDate, "dd/MM/yyyy", false);
		addComponent(1, 10, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtFirstInstDueDate, gridBag, constraints, tcPanel);

		periodicity = new JLabel("Periodicity", SwingConstants.CENTER);
		periodicity.setFont(headingFont);
		periodicity.setOpaque(true);
		periodicity.setBackground(dataBg);
		addComponent(0, 11, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, periodicity, gridBag, constraints, tcPanel);

		String[] periodicityVals = { "Select", "Monthly", "Quarterly", "Half-Yearly" };
		cmbPeriodicity = new JComboBox(periodicityVals);
		addComponent(1, 11, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, cmbPeriodicity, gridBag, constraints, tcPanel);

		noOfInstallments = new JLabel("* No of Instalments", SwingConstants.CENTER);
		noOfInstallments.setFont(headingFont);
		noOfInstallments.setOpaque(true);
		noOfInstallments.setBackground(dataBg);
		addComponent(0, 12, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, noOfInstallments, gridBag, constraints, tcPanel);

		txtNoOfInstallments = new JTextField(10);
		txtNoOfInstallments.setDocument(new NumberFormatField(3));
		addComponent(1, 12, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtNoOfInstallments, gridBag, constraints, tcPanel);

		osAmt = new JLabel("Outstanding Amount");
		osAmt.setFont(headingFont);
		osAmt.setOpaque(true);
		osAmt.setBackground(dataBg);
		addComponent(0, 13, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, osAmt, gridBag, constraints, tcPanel);

		txtTcOsAmt = new JTextField(10);
		txtTcOsAmt.setDocument(new DecimalFormatField(13,2));
//		addComponent(1, 13, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtTcOsAmt, gridBag, constraints, tcPanel);
		
		inRs = new JLabel("in Rs.");
		inRs.setFont(hintFont);
		inRs.setOpaque(true);
		inRs.setBackground(dataBg);
		inRs.setHorizontalTextPosition(SwingConstants.LEFT);		
//		addComponent(2, 13, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, inRs, gridBag, constraints, tcPanel);		

		grpPanel = new JPanel();
		grpPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		grpPanel.setBackground(dataBg);
		grpPanel.add(txtTcOsAmt);
		grpPanel.add(inRs);
		addComponent(1, 13, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, grpPanel, gridBag, constraints, tcPanel);

		asOnDate = new JLabel("As On");
		asOnDate.setFont(headingFont);
		asOnDate.setOpaque(true);
		asOnDate.setBackground(dataBg);
		addComponent(2, 13, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, asOnDate, gridBag, constraints, tcPanel);

		txtOsAsOn = new JTextField(10);
		setDateFieldProperties(txtOsAsOn, "dd/MM/yyyy", false);
		addComponent(3, 13, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtOsAsOn, gridBag, constraints, tcPanel);
	}

	/**
	* This method displays the working capital details panel
	*/
	private void displayWCPanel()
	{
		wcPanel.removeAll();
		wcPanel.setBackground(dataBg);
		wcPanel.setLayout(gridBag);

		wcHeading = new JLabel("Working Capital Assistance");
		wcHeading.setFont(headingFont);
		wcHeading.setOpaque(true);
		wcHeading.setBackground(headingBg);
		addComponent(0, 0, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, wcHeading, gridBag, constraints, wcPanel);

		intType = new JLabel("Interest Type");
		intType.setFont(headingFont);
		intType.setOpaque(true);
		intType.setBackground(dataBg);
		addComponent(0, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, intType, gridBag, constraints, wcPanel);

		interestFixed = new JRadioButton("Fixed");
		interestFloating = new JRadioButton("Floating");
		interestFixed.setBackground(dataBg);
		interestFixed.setSelected(true);
		interestFloating.setBackground(dataBg);
		interestValue = new ButtonGroup();
		interestValue.add(interestFixed);
		interestValue.add(interestFloating);
		interestPanel = new JPanel();
		interestPanel.add(interestFixed);
		interestPanel.add(interestFloating);
		interestPanel.setBackground(dataBg);
		addComponent(1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, interestPanel, gridBag, constraints, wcPanel);
		
		plrType = new JLabel("* Type of PLR");
		plrType.setFont(headingFont);
		plrType.setOpaque(true);
		plrType.setBackground(dataBg);
		addComponent(2, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, plrType, gridBag, constraints, wcPanel);

		txtWcPlrType=new JTextField(new TextFormatField(50),"",10);
		addComponent(3, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtWcPlrType, gridBag, constraints, wcPanel);

		plr = new JLabel("* Prime Lending Rate");
		plr.setFont(headingFont);
		plr.setOpaque(true);
		plr.setBackground(dataBg);
		addComponent(0, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, plr, gridBag, constraints, wcPanel);

		txtWcPlr=new JTextField(10);
		txtWcPlr.setDocument(new DecimalFormatField(2, 2));
//		addComponent(1, 2, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtWcPlr, gridBag, constraints, wcPanel);

		perInt = new JLabel("% p.a.");
		perInt.setFont(hintFont);
		perInt.setOpaque(true);
		perInt.setBackground(dataBg);
		perInt.setHorizontalTextPosition(SwingConstants.LEFT);		
//		addComponent(2, 2, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, perInt, gridBag, constraints, wcPanel);

		grpPanel = new JPanel();
		grpPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		grpPanel.setBackground(dataBg);
		grpPanel.add(txtWcPlr);
		grpPanel.add(perInt);
		addComponent(1, 2, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, grpPanel, gridBag, constraints, wcPanel);

		intRate = new JLabel("* Interest rate");
		intRate.setFont(headingFont);
		intRate.setOpaque(true);
		intRate.setBackground(dataBg);
		addComponent(2, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, intRate, gridBag, constraints, wcPanel);

		txtWcInterestRate = new JTextField(10);
		txtWcInterestRate.setDocument(new DecimalFormatField(2, 2));
//		addComponent(3, 2, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtWcInterestRate, gridBag, constraints, wcPanel);

		perInt = new JLabel("% p.a.");
		perInt.setFont(hintFont);
		perInt.setOpaque(true);
		perInt.setBackground(dataBg);
		perInt.setHorizontalTextPosition(SwingConstants.LEFT);		
//		addComponent(5, 2, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, perInt, gridBag, constraints, wcPanel);
		
		grpPanel = new JPanel();
		grpPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		grpPanel.setBackground(dataBg);
		grpPanel.add(txtWcInterestRate);
		grpPanel.add(perInt);
		addComponent(3, 2, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, grpPanel, gridBag, constraints, wcPanel);		

		limitSanctioned = new JLabel("Limit Sanctioned");
		limitSanctioned.setFont(headingFont);
		limitSanctioned.setOpaque(true);
		limitSanctioned.setBackground(dataBg);
		addComponent(0, 3, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, limitSanctioned, gridBag, constraints, wcPanel);

		fundBased = new JLabel("Fund Based       ");
		fundBased.setFont(headingFont);
		fundBased.setOpaque(true);
		fundBased.setBackground(dataBg);
//		addComponent(1, 3, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, fundBased, gridBag, constraints, wcPanel);

		fbLimitSanctionedValue = new JLabel();
//		addComponent(2, 3, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, fbLimitSanctionedValue, gridBag, constraints, wcPanel);

		grpPanel = new JPanel();
		grpPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		grpPanel.setBackground(dataBg);
		grpPanel.add(fundBased);
		grpPanel.add(fbLimitSanctionedValue);
		addComponent(1, 3, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, grpPanel, gridBag, constraints, wcPanel);		

/*		interest = new JLabel("Interest %");
		interest.setFont(headingFont);
		interest.setOpaque(true);
		interest.setBackground(dataBg);
		addComponent(3, 3, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, interest, gridBag, constraints, wcPanel);

		txtFBInterest = new JTextField(10);
		txtFBInterest.setDocument(new DecimalFormatField(2, 2));
		addComponent(4, 3, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtFBInterest, gridBag, constraints, wcPanel);*/

		dateOfSanction = new JLabel("Date of Sanction");
		dateOfSanction.setFont(headingFont);
		dateOfSanction.setOpaque(true);
		dateOfSanction.setBackground(dataBg);
//		addComponent(3, 3, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, dateOfSanction, gridBag, constraints, wcPanel);

		txtFBSanctionedDate = new JTextField(10);
		setDateFieldProperties(txtFBSanctionedDate, "dd/MM/yyyy", false);
//		addComponent(4, 3, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtFBSanctionedDate, gridBag, constraints, wcPanel);
		
		grpPanel = new JPanel();
		grpPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		grpPanel.setBackground(dataBg);
		grpPanel.add(dateOfSanction);
		grpPanel.add(txtFBSanctionedDate);
		addComponent(2, 3, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, grpPanel, gridBag, constraints, wcPanel);

		nonFundBased = new JLabel("Non Fund Based  ");
		nonFundBased.setFont(headingFont);
		nonFundBased.setOpaque(true);
		nonFundBased.setBackground(dataBg);
//		addComponent(1, 4, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, nonFundBased, gridBag, constraints, wcPanel);

		nfbLimitSanctionedValue = new JLabel();
//		addComponent(2, 4, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, nfbLimitSanctionedValue, gridBag, constraints, wcPanel);
		
		grpPanel = new JPanel();
		grpPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		grpPanel.setBackground(dataBg);
		grpPanel.add(nonFundBased);
		grpPanel.add(nfbLimitSanctionedValue);
		addComponent(1, 4, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, grpPanel, gridBag, constraints, wcPanel);		

		commission = new JLabel("Commission %");
		commission.setFont(headingFont);
		commission.setOpaque(true);
		commission.setBackground(dataBg);
//		addComponent(3, 4, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, commission, gridBag, constraints, wcPanel);

		txtNFBCommission = new JTextField(8);
		txtNFBCommission.setDocument(new DecimalFormatField(2, 2));
//		addComponent(4, 4, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtNFBCommission, gridBag, constraints, wcPanel);
		
		grpPanel = new JPanel();
		grpPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		grpPanel.setBackground(dataBg);
		grpPanel.add(commission);
		grpPanel.add(txtNFBCommission);
		addComponent(2, 4, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, grpPanel, gridBag, constraints, wcPanel);

		dateOfSanction = new JLabel("Date of Sanction");
		dateOfSanction.setFont(headingFont);
		dateOfSanction.setOpaque(true);
		dateOfSanction.setBackground(dataBg);
//		addComponent(5, 4, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, dateOfSanction, gridBag, constraints, wcPanel);

		txtNFBSanctionedDate = new JTextField(8);
		setDateFieldProperties(txtNFBSanctionedDate, "dd/MM/yyyy", false);
//		addComponent(6, 4, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtNFBSanctionedDate, gridBag, constraints, wcPanel);
		
		grpPanel = new JPanel();
		grpPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		grpPanel.setBackground(dataBg);
		grpPanel.add(dateOfSanction);
		grpPanel.add(txtNFBSanctionedDate);
		addComponent(3, 4, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, grpPanel, gridBag, constraints, wcPanel);

		creditGuarantee = new JLabel("Credit to be Guaranteed");
		creditGuarantee.setFont(headingFont);
		creditGuarantee.setOpaque(true);
		creditGuarantee.setBackground(dataBg);
		addComponent(0, 5, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, creditGuarantee, gridBag, constraints, wcPanel);

		fundBased = new JLabel("Fund Based");
		fundBased.setFont(headingFont);
		fundBased.setOpaque(true);
		fundBased.setBackground(dataBg);
//		addComponent(1, 5, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, fundBased, gridBag, constraints, wcPanel);

		txtFBCredit = new JTextField(10);
		txtFBCredit.setDocument(new DecimalFormatField(13,2));
//		addComponent(2, 5, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtFBCredit, gridBag, constraints, wcPanel);

		inRs = new JLabel("in Rs.");
		inRs.setFont(hintFont);
		inRs.setOpaque(true);
		inRs.setBackground(dataBg);
		inRs.setHorizontalTextPosition(SwingConstants.LEFT);		
//		addComponent(3, 5, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, inRs, gridBag, constraints, wcPanel);*/		

		grpPanel = new JPanel();
		grpPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		grpPanel.setBackground(dataBg);
		grpPanel.add(fundBased);
		grpPanel.add(txtFBCredit);
		grpPanel.add(inRs);
		addComponent(1, 5, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, grpPanel, gridBag, constraints, wcPanel);

		nonFundBased = new JLabel("Non Fund Based");
		nonFundBased.setFont(headingFont);
		nonFundBased.setOpaque(true);
		nonFundBased.setBackground(dataBg);
//		addComponent(1, 6, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, nonFundBased, gridBag, constraints, wcPanel);

		txtNFBCredit = new JTextField(10);
		txtNFBCredit.setDocument(new NumberFormatField(13));
//		addComponent(2, 6, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtNFBCredit, gridBag, constraints, wcPanel);
		
		inRs = new JLabel("in Rs.");
		inRs.setFont(hintFont);
		inRs.setOpaque(true);
		inRs.setBackground(dataBg);
		inRs.setHorizontalTextPosition(SwingConstants.LEFT);		
//		addComponent(3, 5, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, inRs, gridBag, constraints, wcPanel);*/		

		grpPanel = new JPanel();
		grpPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		grpPanel.setBackground(dataBg);
		grpPanel.add(nonFundBased);
		grpPanel.add(txtNFBCredit);
		grpPanel.add(inRs);
		addComponent(1, 6, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, grpPanel, gridBag, constraints, wcPanel);

		osFundBased = new JLabel("Outstanding Fund Based");
		osFundBased.setFont(headingFont);
		osFundBased.setOpaque(true);
		osFundBased.setBackground(dataBg);
		addComponent(0, 7, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, osFundBased, gridBag, constraints, wcPanel);

		principal = new JLabel("Amount");
		principal.setFont(headingFont);
		principal.setOpaque(true);
		principal.setBackground(dataBg);
//		addComponent(1, 7, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, principal, gridBag, constraints, wcPanel);

		txtOsFBPrincipal = new JTextField(10);
		txtOsFBPrincipal.setDocument(new DecimalFormatField(13,2));
//		addComponent(2, 7, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtOsFBPrincipal, gridBag, constraints, wcPanel);

		inRs = new JLabel("in Rs.");
		inRs.setFont(hintFont);
		inRs.setOpaque(true);
		inRs.setBackground(dataBg);
		inRs.setHorizontalTextPosition(SwingConstants.LEFT);		
//		addComponent(3, 7, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, inRs, gridBag, constraints, wcPanel);

		grpPanel = new JPanel();
		grpPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		grpPanel.setBackground(dataBg);
		grpPanel.add(principal);
		grpPanel.add(txtOsFBPrincipal);
		grpPanel.add(inRs);
		addComponent(1, 7, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, grpPanel, gridBag, constraints, wcPanel);

		asOnDate = new JLabel("As On ");
		asOnDate.setFont(headingFont);
		asOnDate.setOpaque(true);
		asOnDate.setBackground(dataBg);
//		addComponent(3, 7, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, asOnDate, gridBag, constraints, wcPanel);

		txtOsFBAsOn = new JTextField(10);
		setDateFieldProperties(txtOsFBAsOn, "dd/MM/yyyy", false);
//		addComponent(4, 7, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtOsFBAsOn, gridBag, constraints, wcPanel);
		
		grpPanel = new JPanel();
		grpPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		grpPanel.setBackground(dataBg);
		grpPanel.add(asOnDate);
		grpPanel.add(txtOsFBAsOn);
		addComponent(2, 7, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, grpPanel, gridBag, constraints, wcPanel);

		osNonFundBased = new JLabel("Outstanding Non Fund Based");
		osNonFundBased.setFont(headingFont);
		osNonFundBased.setOpaque(true);
		osNonFundBased.setBackground(dataBg);
		addComponent(0, 8, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, osNonFundBased, gridBag, constraints, wcPanel);

		principal = new JLabel("  Principal");
		principal.setFont(headingFont);
		principal.setOpaque(true);
		principal.setBackground(dataBg);
//		addComponent(1, 8, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, principal, gridBag, constraints, wcPanel);

		txtOsNFBPrincipal = new JTextField(10);
		txtOsNFBPrincipal.setDocument(new NumberFormatField(13));
//		addComponent(2, 8, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtOsNFBPrincipal, gridBag, constraints, wcPanel);
		
		inRs = new JLabel("in Rs.");
		inRs.setFont(hintFont);
		inRs.setOpaque(true);
		inRs.setBackground(dataBg);
		inRs.setHorizontalTextPosition(SwingConstants.LEFT);		
//		addComponent(3, 7, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, inRs, gridBag, constraints, wcPanel);

		grpPanel = new JPanel();
		grpPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		grpPanel.setBackground(dataBg);
		grpPanel.add(principal);
		grpPanel.add(txtOsNFBPrincipal);
		grpPanel.add(inRs);
		addComponent(1, 8, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, grpPanel, gridBag, constraints, wcPanel);		

		commission = new JLabel("Commission");
		commission.setFont(headingFont);
		commission.setOpaque(true);
		commission.setBackground(dataBg);
//		addComponent(3, 8, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, commission, gridBag, constraints, wcPanel);

		txtOsNFBCommission = new JTextField(8);
		txtOsNFBCommission.setDocument(new NumberFormatField(13));
//		addComponent(4, 8, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtOsNFBCommission, gridBag, constraints, wcPanel);
		
		inRs = new JLabel("in Rs.");
		inRs.setFont(hintFont);
		inRs.setOpaque(true);
		inRs.setBackground(dataBg);
		inRs.setHorizontalTextPosition(SwingConstants.LEFT);		
//		addComponent(3, 7, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, inRs, gridBag, constraints, wcPanel);

		grpPanel = new JPanel();
		grpPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		grpPanel.setBackground(dataBg);
		grpPanel.add(commission);
		grpPanel.add(txtOsNFBCommission);
		grpPanel.add(inRs);
		addComponent(2, 8, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, grpPanel, gridBag, constraints, wcPanel);

		asOnDate = new JLabel("As On ");
		asOnDate.setFont(headingFont);
		asOnDate.setOpaque(true);
		asOnDate.setBackground(dataBg);
//		addComponent(5, 8, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, asOnDate, gridBag, constraints, wcPanel);

		txtOsNFBAsOn = new JTextField(10);
		setDateFieldProperties(txtOsNFBAsOn, "dd/MM/yyyy", false);
//		addComponent(6, 8, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtOsNFBAsOn, gridBag, constraints, wcPanel);
		
		grpPanel = new JPanel();
		grpPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		grpPanel.setBackground(dataBg);
		grpPanel.add(asOnDate);
		grpPanel.add(txtOsNFBAsOn);
		addComponent(3, 8, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, grpPanel, gridBag, constraints, wcPanel);
	}

	/**
	 * This method displays the Securitisation Details for an Application
	 */
	private void displaySecuritisationPanel()
	{
		securitisationPanel.removeAll();
		securitisationPanel.setBackground(dataBg);
		securitisationPanel.setLayout(gridBag);

/*		securitisationHeading = new JLabel("Securitisation Details");
		securitisationHeading.setFont(headingFont);
		securitisationHeading.setOpaque(true);
		securitisationHeading.setBackground(headingBg);
		addComponent(0, 0, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, securitisationHeading, gridBag, constraints, securitisationPanel);
*/
		spreadOverPLR = new JLabel("Spread Over PLR");
		spreadOverPLR.setFont(headingFont);
		spreadOverPLR.setOpaque(true);
		spreadOverPLR.setBackground(dataBg);
		addComponent(0, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, spreadOverPLR, gridBag, constraints, securitisationPanel);

		txtSpreadOverPLR = new JTextField(10);
		txtSpreadOverPLR.setDocument(new DecimalFormatField(2, 2));
		addComponent(1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtSpreadOverPLR, gridBag, constraints, securitisationPanel);

		principalRepayments = new JLabel("Whether Repayments in Equal Instalments");
		principalRepayments.setFont(headingFont);
		principalRepayments.setOpaque(true);
		principalRepayments.setBackground(dataBg);
		addComponent(2, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, principalRepayments, gridBag, constraints, securitisationPanel);

		repayInEqualInstYes = new JRadioButton("Yes");
		repayInEqualInstNo = new JRadioButton("No");
		repayInEqualInstYes.setBackground(dataBg);
		repayInEqualInstNo.setBackground(dataBg);
		repayInEqualInstNo.setSelected(true);
		repayInEqualInstValue = new ButtonGroup();
		repayInEqualInstValue.add(repayInEqualInstYes);
		repayInEqualInstValue.add(repayInEqualInstNo);
		repayInEqualInstPanel = new JPanel();
		repayInEqualInstPanel.add(repayInEqualInstYes);
		repayInEqualInstPanel.add(repayInEqualInstNo);
		repayInEqualInstPanel.setBackground(dataBg);
		addComponent(3, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, repayInEqualInstPanel, gridBag, constraints, securitisationPanel);

		tangibleNetworth = new JLabel("Tangible Networth");
		tangibleNetworth.setFont(headingFont);
		tangibleNetworth.setOpaque(true);
		tangibleNetworth.setBackground(dataBg);
		addComponent(0, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, tangibleNetworth, gridBag, constraints, securitisationPanel);

		txtTangibleNetworth = new JTextField(10);
		txtTangibleNetworth.setDocument(new DecimalFormatField(13,2));
//		addComponent(1, 2, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtTangibleNetworth, gridBag, constraints, securitisationPanel);

		inRs = new JLabel("in Rs.");
		inRs.setFont(hintFont);
		inRs.setOpaque(true);
		inRs.setBackground(dataBg);
		inRs.setHorizontalTextPosition(SwingConstants.LEFT);		
//		addComponent(2, 13, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, inRs, gridBag, constraints, tcPanel);		

		grpPanel = new JPanel();
		grpPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		grpPanel.setBackground(dataBg);
		grpPanel.add(txtTangibleNetworth);
		grpPanel.add(inRs);
		addComponent(1, 2, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, grpPanel, gridBag, constraints, securitisationPanel);

		fixedAssetCoverageRatio = new JLabel("Fixed Asset Coverage Ratio");
		fixedAssetCoverageRatio.setFont(headingFont);
		fixedAssetCoverageRatio.setOpaque(true);
		fixedAssetCoverageRatio.setBackground(dataBg);
		addComponent(2, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, fixedAssetCoverageRatio, gridBag, constraints, securitisationPanel);

		txtFixedAssetCoverageRatio = new JTextField(10);
		txtFixedAssetCoverageRatio.setDocument(new DecimalFormatField(2, 2));
		addComponent(3, 2, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtFixedAssetCoverageRatio, gridBag, constraints, securitisationPanel);

		currentRatio = new JLabel("Current Ratio");
		currentRatio.setFont(headingFont);
		currentRatio.setOpaque(true);
		currentRatio.setBackground(dataBg);
		addComponent(0, 3, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, currentRatio, gridBag, constraints, securitisationPanel);

		txtCurrentRatio = new JTextField(10);
		txtCurrentRatio.setDocument(new DecimalFormatField(2, 2));
		addComponent(1, 3, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtCurrentRatio, gridBag, constraints, securitisationPanel);

/*		finPartOfUnit = new JLabel("Financial Particular Of the Unit");
		finPartOfUnit.setFont(headingFont);
		finPartOfUnit.setOpaque(true);
		finPartOfUnit.setBackground(dataBg);
		addComponent(2, 3, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, finPartOfUnit, gridBag, constraints, securitisationPanel);

		txtFinPartOfUnit = new JTextField(10);
		txtFinPartOfUnit.setDocument(new NumberFormatField(13));
		addComponent(3, 3, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtFinPartOfUnit, gridBag, constraints, securitisationPanel);

		finPartOfUnitHint = new JLabel("(Give Projected Value for a New Unit)");
		finPartOfUnitHint.setFont(hintFont);
		finPartOfUnitHint.setOpaque(true);
		finPartOfUnitHint.setBackground(dataBg);
		addComponent(2, 4, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, finPartOfUnitHint, gridBag, constraints, securitisationPanel);*/

		minDSCR = new JLabel("Minimum DSCR");
		minDSCR.setFont(headingFont);
		minDSCR.setOpaque(true);
		minDSCR.setBackground(dataBg);
		addComponent(0, 5, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, minDSCR, gridBag, constraints, securitisationPanel);

		txtMinDSCR = new JTextField(10);
		txtMinDSCR.setDocument(new DecimalFormatField(13,2));
		addComponent(1, 5, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtMinDSCR, gridBag, constraints, securitisationPanel);

		avgDSCR = new JLabel("Average DSCR");
		avgDSCR.setFont(headingFont);
		avgDSCR.setOpaque(true);
		avgDSCR.setBackground(dataBg);
		addComponent(2, 5, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, avgDSCR, gridBag, constraints, securitisationPanel);

		txtAvgDSCR = new JTextField(10);
		txtAvgDSCR.setDocument(new DecimalFormatField(13,2));
		addComponent(3, 5, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtAvgDSCR, gridBag, constraints, securitisationPanel);
	}

	/**
	 * This method displays the MCGF Panel.
	 */
	private boolean displayMcgfPanel()
	{
		mcgfPanel.removeAll();
		mcgfPanel.setBackground(dataBg);
		mcgfPanel.setLayout(gridBag);

		mcgfHeading = new JLabel("MCGF Details");
		mcgfHeading.setFont(headingFont);
		mcgfHeading.setOpaque(true);
		mcgfHeading.setBackground(headingBg);
		addComponent(0, 0, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, mcgfHeading, gridBag, constraints, mcgfPanel);

		district = new JLabel("District");
		district.setFont(headingFont);
		district.setOpaque(true);
		district.setBackground(dataBg);
		addComponent(0, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, district, gridBag, constraints, mcgfPanel);

		txtMcgfDistrict = new JTextField(new TextFormatField(100),"",10);
		addComponent(1, 2, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtMcgfDistrict, gridBag, constraints, mcgfPanel);	

		mcgfId = new JLabel("* MCGF Id");
		mcgfId.setFont(headingFont);
		mcgfId.setOpaque(true);
		mcgfId.setBackground(dataBg);
		addComponent(2, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, mcgfId, gridBag, constraints, mcgfPanel);

		Vector mcgfIds = new Vector();
		try
		{ 
			mcgfIds = new Vector(getMcgfIds());
		}
		catch (ThinClientException exp)
		{
			if (exp.getMessage().equalsIgnoreCase("File Not Found"))
			{
				return false;
			}
		}
		cmbMcgfId = new JComboBox(mcgfIds);
		cmbMcgfId.addItemListener(new McgfListener());
		addComponent(3, 2, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, cmbMcgfId, gridBag, constraints, mcgfPanel);

		mcgfName = new JLabel("MCGF Name");
		mcgfName.setFont(headingFont);
		mcgfName.setOpaque(true);
		mcgfName.setBackground(dataBg);
		addComponent(0, 3, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, mcgfName, gridBag, constraints, mcgfPanel);

		mcgfNameValue = new JLabel();
		mcgfNameValue.setFont(dataFont);
		mcgfNameValue.setOpaque(true);
		mcgfNameValue.setBackground(dataBg);
		addComponent(1, 3, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, mcgfNameValue, gridBag, constraints, mcgfPanel);

		amtApprovedByMcgf = new JLabel("Amount Approved by MCGF");
		amtApprovedByMcgf.setFont(headingFont);
		amtApprovedByMcgf.setOpaque(true);
		amtApprovedByMcgf.setBackground(dataBg);
		addComponent(2, 3, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, amtApprovedByMcgf, gridBag, constraints, mcgfPanel);

		txtAmtApprovedByMcgf = new JTextField(10);
		txtAmtApprovedByMcgf.setDocument(new DecimalFormatField(13,2));
		addComponent(3, 3, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtAmtApprovedByMcgf, gridBag, constraints, mcgfPanel);	

		participatingBankName = new JLabel("* Participating Bank Name");
		participatingBankName.setFont(headingFont);
		participatingBankName.setOpaque(true);
		participatingBankName.setBackground(dataBg);
		addComponent(0, 4, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, participatingBankName, gridBag, constraints, mcgfPanel);

		String partBanks[] = {"Select"};
		cmbParticipatingBanks = new JComboBox(partBanks);
		addComponent(1, 4, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, cmbParticipatingBanks, gridBag, constraints, mcgfPanel);

		participatingBankBranch = new JLabel("Participating Bank Branch Name");
		participatingBankBranch.setFont(headingFont);
		participatingBankBranch.setOpaque(true);
		participatingBankBranch.setBackground(dataBg);
		addComponent(2, 4, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, participatingBankBranch, gridBag, constraints, mcgfPanel);

		txtPartBankBranch = new JTextField(new TextFormatField(100),"",10);
		addComponent(3, 4, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtPartBankBranch, gridBag, constraints, mcgfPanel);

		approvedDate = new JLabel("Approved Date");
		approvedDate.setFont(headingFont);
		approvedDate.setOpaque(true);
		approvedDate.setBackground(dataBg);
		addComponent(0, 5, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, approvedDate, gridBag, constraints, mcgfPanel);

		txtApprovedDate = new JTextField(10);
		setDateFieldProperties(txtApprovedDate, "dd/MM/yyyy", false);
		addComponent(1, 5, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtApprovedDate, gridBag, constraints, mcgfPanel);	

		gCoverStartDate = new JLabel("Guarantee Cover Start Date");
		gCoverStartDate.setFont(headingFont);
		gCoverStartDate.setOpaque(true);
		gCoverStartDate.setBackground(dataBg);
		addComponent(2, 5, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, gCoverStartDate, gridBag, constraints, mcgfPanel);

		txtGCoverStartDate = new JTextField(10);
		setDateFieldProperties(txtGCoverStartDate, "dd/MM/yyyy", false);
		addComponent(3, 5, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtGCoverStartDate, gridBag, constraints, mcgfPanel);

		return true;
	}

	/**
	 * This method displays the working capital enhancement details.
	 */
	private void displayWCEnhancementPanel()
	{
		wcEnhancementPanel.removeAll();
		wcEnhancementPanel.setBackground(dataBg);
		wcEnhancementPanel.setLayout(gridBag);

		wcEnhancementHeading = new JLabel("Working Capital Enhancement Details");
		wcEnhancementHeading.setFont(headingFont);
		wcEnhancementHeading.setOpaque(true);
		wcEnhancementHeading.setBackground(headingBg);
		addComponent(0, 0, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, wcEnhancementHeading, gridBag, constraints, wcEnhancementPanel);

		intType = new JLabel("Interest Type");
		intType.setFont(headingFont);
		intType.setOpaque(true);
		intType.setBackground(dataBg);
		addComponent(0, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, intType, gridBag, constraints, wcEnhancementPanel);

		interestFixed = new JRadioButton("Fixed");
		interestFloating = new JRadioButton("Floating");
		interestFixed.setBackground(dataBg);
		interestFixed.setSelected(true);
		interestFloating.setBackground(dataBg);
		interestValue = new ButtonGroup();
		interestValue.add(interestFixed);
		interestValue.add(interestFloating);
		interestPanel = new JPanel();
		interestPanel.add(interestFixed);
		interestPanel.add(interestFloating);
		interestPanel.setBackground(dataBg);
		addComponent(1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, interestPanel, gridBag, constraints, wcEnhancementPanel);

		fundBased = new JLabel("Existing Fund Based");
		fundBased.setFont(headingFont);
		fundBased.setOpaque(true);
		fundBased.setBackground(dataBg);
		addComponent(0, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, fundBased, gridBag, constraints, wcEnhancementPanel);

		fbLimitSanctionedValue = new JLabel();
		addComponent(1, 2, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, fbLimitSanctionedValue, gridBag, constraints, wcEnhancementPanel);

		interest = new JLabel("Interest %");
		interest.setFont(headingFont);
		interest.setOpaque(true);
		interest.setBackground(dataBg);
		addComponent(2, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, interest, gridBag, constraints, wcEnhancementPanel);

		existingInterestValue = new JLabel();
		existingInterestValue.setFont(headingFont);
		existingInterestValue.setOpaque(true);
		existingInterestValue.setBackground(dataBg);
		addComponent(3, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, existingInterestValue, gridBag, constraints, wcEnhancementPanel);

		fundBased = new JLabel("Existing Non Fund Based");
		fundBased.setFont(headingFont);
		fundBased.setOpaque(true);
		fundBased.setBackground(dataBg);
		addComponent(0, 3, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, fundBased, gridBag, constraints, wcEnhancementPanel);

		fbLimitSanctionedValue = new JLabel();
		addComponent(1, 3, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, fbLimitSanctionedValue, gridBag, constraints, wcEnhancementPanel);

		interest = new JLabel("Commission %");
		interest.setFont(headingFont);
		interest.setOpaque(true);
		interest.setBackground(dataBg);
		addComponent(2, 3, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, interest, gridBag, constraints, wcEnhancementPanel);

/*		total = new JLabel("Total");
		total.setFont(headingFont);
		total.setOpaque(true);
		total.setBackground(dataBg);
		addComponent(0, 3, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, total, gridBag, constraints, wcEnhancementPanel);

		existingTotalValue = new JLabel("");
		existingTotalValue.setFont(headingFont);
		existingTotalValue.setOpaque(true);
		existingTotalValue.setBackground(dataBg);
		addComponent(1, 3, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, existingTotalValue, gridBag, constraints, wcEnhancementPanel);*/

		enhancedFundBased = new JLabel("Enhanced Fund Based (Existing + New)");
		enhancedFundBased.setFont(headingFont);
		enhancedFundBased.setOpaque(true);
		enhancedFundBased.setBackground(dataBg);
		addComponent(0, 4, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, enhancedFundBased, gridBag, constraints, wcEnhancementPanel);

		enhancedFundBasedValue = new JLabel();
		addComponent(1, 4, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, enhancedFundBasedValue, gridBag, constraints, wcEnhancementPanel);

		enhancedInterest = new JLabel("Interest %");
		enhancedInterest.setFont(headingFont);
		enhancedInterest.setOpaque(true);
		enhancedInterest.setBackground(dataBg);
		addComponent(2, 4, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, enhancedInterest, gridBag, constraints, wcEnhancementPanel);

		txtEnhancedInterest = new JTextField(10);
		txtEnhancedInterest.setDocument(new DecimalFormatField(2, 2));
		addComponent(3, 4, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtEnhancedInterest, gridBag, constraints, wcEnhancementPanel);

/*		total = new JLabel("Total");
		total.setFont(headingFont);
		total.setOpaque(true);
		total.setBackground(dataBg);
		addComponent(0, 5, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, total, gridBag, constraints, wcEnhancementPanel);

		enhancedTotalValue = new JLabel("");
		enhancedTotalValue.setFont(headingFont);
		enhancedTotalValue.setOpaque(true);
		enhancedTotalValue.setBackground(dataBg);
		addComponent(1, 5, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, enhancedTotalValue, gridBag, constraints, wcEnhancementPanel);*/
		
		enhancedFundBased = new JLabel("Enhanced Non Fund Based (Existing + New)");
		enhancedFundBased.setFont(headingFont);
		enhancedFundBased.setOpaque(true);
		enhancedFundBased.setBackground(dataBg);
		addComponent(0, 5, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, enhancedFundBased, gridBag, constraints, wcEnhancementPanel);

		enhancedNonFundBasedValue = new JLabel();
		addComponent(1, 5, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, enhancedNonFundBasedValue, gridBag, constraints, wcEnhancementPanel);

		enhancedInterest = new JLabel("Commission %");
		enhancedInterest.setFont(headingFont);
		enhancedInterest.setOpaque(true);
		enhancedInterest.setBackground(dataBg);
		addComponent(2, 5, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, enhancedInterest, gridBag, constraints, wcEnhancementPanel);

		txtEnhancedCommission = new JTextField(10);
		txtEnhancedCommission.setDocument(new DecimalFormatField(2, 2));
		addComponent(3, 5, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtEnhancedCommission, gridBag, constraints, wcEnhancementPanel);

		dtOfEnhancement = new JLabel("Date of Enhancement");
		dtOfEnhancement.setFont(headingFont);
		dtOfEnhancement.setOpaque(true);
		dtOfEnhancement.setBackground(dataBg);
		addComponent(0, 6, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, dtOfEnhancement, gridBag, constraints, wcEnhancementPanel);

		txtDtOfEnhancement = new JTextField(10);
		setDateFieldProperties(txtDtOfEnhancement, "dd/MM/yyyy", false);
		addComponent(1, 6, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtDtOfEnhancement, gridBag, constraints, wcEnhancementPanel);

		existingRemarks = new JLabel("Existing Remarks");
		existingRemarks.setFont(headingFont);
		existingRemarks.setOpaque(true);
		existingRemarks.setBackground(dataBg);
		addComponent(0, 7, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, existingRemarks, gridBag, constraints, wcEnhancementPanel);

		existingRemarksValue = new JLabel("");
		existingRemarksValue.setFont(headingFont);
		existingRemarksValue.setOpaque(true);
		existingRemarksValue.setBackground(dataBg);
		addComponent(0, 7, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, existingRemarksValue, gridBag, constraints, wcEnhancementPanel);
	}

	/**
	 * This method displays the working capital renewal application screen.
	 */
	private void displayWCRenewalPanel()
	{
		wcRenewalPanel.removeAll();
		wcRenewalPanel.setBackground(dataBg);
		wcRenewalPanel.setLayout(gridBag);

		wcRenewalHeading = new JLabel("Working Capital Renewal Details");
		wcRenewalHeading.setFont(headingFont);
		wcRenewalHeading.setOpaque(true);
		wcRenewalHeading.setBackground(headingBg);
		addComponent(0, 0, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, wcRenewalHeading, gridBag, constraints, wcRenewalPanel);

		intType = new JLabel("Interest Type");
		intType.setFont(headingFont);
		intType.setOpaque(true);
		intType.setBackground(dataBg);
		addComponent(0, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, intType, gridBag, constraints, wcRenewalPanel);

		interestFixed = new JRadioButton("Fixed");
		interestFloating = new JRadioButton("Floating");
		interestFixed.setBackground(dataBg);
		interestFixed.setSelected(true);
		interestFloating.setBackground(dataBg);
		interestValue = new ButtonGroup();
		interestValue.add(interestFixed);
		interestValue.add(interestFloating);
		interestPanel = new JPanel();
		interestPanel.add(interestFixed);
		interestPanel.add(interestFloating);
		interestPanel.setBackground(dataBg);
		addComponent(1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, interestPanel, gridBag, constraints, wcRenewalPanel);

		fundBased = new JLabel("Existing Fund Based");
		fundBased.setFont(headingFont);
		fundBased.setOpaque(true);
		fundBased.setBackground(dataBg);
		addComponent(0, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, fundBased, gridBag, constraints, wcRenewalPanel);

		fbLimitSanctionedValue = new JLabel();
		addComponent(1, 2, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, fbLimitSanctionedValue, gridBag, constraints, wcRenewalPanel);

		interest = new JLabel("Interest %");
		interest.setFont(headingFont);
		interest.setOpaque(true);
		interest.setBackground(dataBg);
		addComponent(2, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, interest, gridBag, constraints, wcRenewalPanel);

		existingInterestValue = new JLabel();
		existingInterestValue.setFont(headingFont);
		existingInterestValue.setOpaque(true);
		existingInterestValue.setBackground(dataBg);
		addComponent(2, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, existingInterestValue, gridBag, constraints, wcRenewalPanel);
		
		fundBased = new JLabel("Existing Non Fund Based");
		fundBased.setFont(headingFont);
		fundBased.setOpaque(true);
		fundBased.setBackground(dataBg);
		addComponent(0, 3, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, fundBased, gridBag, constraints, wcRenewalPanel);

		fbLimitSanctionedValue = new JLabel();
		addComponent(1, 3, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, fbLimitSanctionedValue, gridBag, constraints, wcRenewalPanel);

		interest = new JLabel("Commission %");
		interest.setFont(headingFont);
		interest.setOpaque(true);
		interest.setBackground(dataBg);
		addComponent(2, 3, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, interest, gridBag, constraints, wcRenewalPanel);

		existingInterestValue = new JLabel();
		existingInterestValue.setFont(headingFont);
		existingInterestValue.setOpaque(true);
		existingInterestValue.setBackground(dataBg);
		addComponent(2, 3, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, existingInterestValue, gridBag, constraints, wcRenewalPanel);

/*		total = new JLabel("Total");
		total.setFont(headingFont);
		total.setOpaque(true);
		total.setBackground(dataBg);
		addComponent(0, 3, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, total, gridBag, constraints, wcRenewalPanel);

		existingTotalValue = new JLabel("");
		existingTotalValue.setFont(headingFont);
		existingTotalValue.setOpaque(true);
		existingTotalValue.setBackground(dataBg);
		addComponent(1, 3, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, existingTotalValue, gridBag, constraints, wcRenewalPanel);*/

		renewedFundBased = new JLabel("Renewed Fund Based");
		renewedFundBased.setFont(headingFont);
		renewedFundBased.setOpaque(true);
		renewedFundBased.setBackground(dataBg);
		addComponent(0, 4, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, renewedFundBased, gridBag, constraints, wcRenewalPanel);

		renewedFundBasedValue = new JLabel();
		renewedFundBasedValue.setFont(headingFont);
		renewedFundBasedValue.setOpaque(true);
		renewedFundBasedValue.setBackground(dataBg);
		addComponent(1, 4, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, renewedFundBasedValue, gridBag, constraints, wcRenewalPanel);

		renewedInterest = new JLabel("Interest %");
		renewedInterest.setFont(headingFont);
		renewedInterest.setOpaque(true);
		renewedInterest.setBackground(dataBg);
		addComponent(2, 4, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, renewedInterest, gridBag, constraints, wcRenewalPanel);

		txtRenewedInterest = new JTextField(10);
		txtRenewedInterest.setDocument(new DecimalFormatField(2, 2));
		addComponent(3, 4, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtRenewedInterest, gridBag, constraints, wcRenewalPanel);

		renewedFundBased = new JLabel("Renewed Non Fund Based");
		renewedFundBased.setFont(headingFont);
		renewedFundBased.setOpaque(true);
		renewedFundBased.setBackground(dataBg);
		addComponent(0, 5, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, renewedFundBased, gridBag, constraints, wcRenewalPanel);

		renewedNonFundBasedValue = new JLabel();
		renewedNonFundBasedValue.setFont(headingFont);
		renewedNonFundBasedValue.setOpaque(true);
		renewedNonFundBasedValue.setBackground(dataBg);
		addComponent(1, 5, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, renewedNonFundBasedValue, gridBag, constraints, wcRenewalPanel);

		renewedInterest = new JLabel("Commission %");
		renewedInterest.setFont(headingFont);
		renewedInterest.setOpaque(true);
		renewedInterest.setBackground(dataBg);
		addComponent(2, 5, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, renewedInterest, gridBag, constraints, wcRenewalPanel);

		txtRenewedCommission = new JTextField(10);
		txtRenewedCommission.setDocument(new DecimalFormatField(2, 2));
		addComponent(3, 5, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtRenewedCommission, gridBag, constraints, wcRenewalPanel);

/*		total = new JLabel("Total");
		total.setFont(headingFont);
		total.setOpaque(true);
		total.setBackground(dataBg);
		addComponent(0, 5, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, total, gridBag, constraints, wcRenewalPanel);

		renewedTotalValue = new JLabel("");
		renewedTotalValue.setFont(headingFont);
		renewedTotalValue.setOpaque(true);
		renewedTotalValue.setBackground(dataBg);
		addComponent(1, 5, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, renewedTotalValue, gridBag, constraints, wcRenewalPanel);*/

		plr = new JLabel("Prime Lending Rate");
		plr.setFont(headingFont);
		plr.setOpaque(true);
		plr.setBackground(dataBg);
		addComponent(0, 6, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, plr, gridBag, constraints, wcRenewalPanel);

		txtWcPlr=new JTextField(10);
		txtWcPlr.setDocument(new DecimalFormatField(2, 2));
		addComponent(1, 6, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtWcPlr, gridBag, constraints, wcRenewalPanel);

		plrType = new JLabel("Prime Lending Rate Type");
		plrType.setFont(headingFont);
		plrType.setOpaque(true);
		plrType.setBackground(dataBg);
		addComponent(2, 6, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, plrType, gridBag, constraints, wcRenewalPanel);

		txtWcPlrType=new JTextField(new TextFormatField(50),"",10);
		addComponent(3, 6, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtWcPlrType, gridBag, constraints, wcRenewalPanel);

		dtOfRenewal = new JLabel("Date of Renewal");
		dtOfRenewal.setFont(headingFont);
		dtOfRenewal.setOpaque(true);
		dtOfRenewal.setBackground(dataBg);
		addComponent(0, 7, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, dtOfRenewal, gridBag, constraints, wcRenewalPanel);

		txtDtOfRenewal = new JTextField(10);
		setDateFieldProperties(txtDtOfRenewal, "dd/MM/yyyy", false);
		addComponent(1, 7, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtDtOfRenewal, gridBag, constraints, wcRenewalPanel);

		existingRemarks = new JLabel("Existing Remarks");
		existingRemarks.setFont(headingFont);
		existingRemarks.setOpaque(true);
		existingRemarks.setBackground(dataBg);
		addComponent(0, 8, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, existingRemarks, gridBag, constraints, wcRenewalPanel);

		existingRemarksValue = new JLabel("");
		existingRemarksValue.setFont(headingFont);
		existingRemarksValue.setOpaque(true);
		existingRemarksValue.setBackground(dataBg);
		addComponent(0, 8, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, existingRemarksValue, gridBag, constraints, wcRenewalPanel);
	}

	/**
	 * This method displays the fields for the periodic info details
	 */
	 private void displayPeriodicInfo(String key, String fileName, String flag) throws ThinClientException
	 {
		 //System.out.println("flag -- " + flag);
		panel.removeAll();
		panel.setBackground(dataBg);
		panel.setLayout(gridBag);

		int index=fileName.lastIndexOf("&");
		String brnName="";
		if (index>0)
		{
			brnName=fileName.substring(index+1, fileName.length());
			fileName=fileName.substring(0, index);
		}
		else if (flag.equalsIgnoreCase("NEW"))
		{
			brnName=lblBrnName.getText().trim();
		}

		Hashtable details=readFromFile(fileName);
		com.cgtsi.guaranteemaintenance.PeriodicInfo periodicInfo=(com.cgtsi.guaranteemaintenance.PeriodicInfo) details.get(key);
		

		hintMandatory = new JLabel("All Fields marked with * are mandatory");
		hintMandatory.setFont(dataFont);
		hintMandatory.setOpaque(true);
		hintMandatory.setBackground(dataBg);
		addComponent(0, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, hintMandatory, gridBag, constraints, panel);
		
		hintDate = new JLabel("All dates should be in DD/MM/YYYY format");
		hintDate.setFont(dataFont);
		hintDate.setOpaque(true);
		hintDate.setBackground(dataBg);
		addComponent(0, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, hintDate, gridBag, constraints, panel);		

		borrowerId = new JLabel("Borrower ID");
		borrowerId.setFont(headingFont);
		borrowerId.setOpaque(true);
		borrowerId.setBackground(dataBg);
//		addComponent(0, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, borrowerId, gridBag, constraints, panel);

		borrowerIdValue = new JLabel("   "+periodicInfo.getBorrowerId());
		borrowerIdValue.setFont(dataFont);
		borrowerIdValue.setOpaque(true);
		borrowerIdValue.setBackground(dataBg);
//		addComponent(1, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, borrowerIdValue, gridBag, constraints, panel);

		grpPanel = new JPanel();
		grpPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		grpPanel.setBackground(dataBg);
		grpPanel.add(borrowerId);
		grpPanel.add(borrowerIdValue);
		addComponent(0, 2, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, grpPanel, gridBag, constraints, panel);

		borrowerName = new JLabel("  Borrower Name");
		borrowerName.setFont(headingFont);
		borrowerName.setOpaque(true);
		borrowerName.setBackground(dataBg);
//		addComponent(2, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, borrowerName, gridBag, constraints, panel);

		borrowerNameValue = new JLabel("  "+periodicInfo.getBorrowerName());
		borrowerNameValue.setFont(dataFont);
		borrowerNameValue.setOpaque(true);
		borrowerNameValue.setBackground(dataBg);
//		addComponent(3, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, borrowerNameValue, gridBag, constraints, panel);
		
		grpPanel = new JPanel();
		grpPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		grpPanel.setBackground(dataBg);
		grpPanel.add(borrowerName);
		grpPanel.add(borrowerNameValue);
		addComponent(1, 2, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, grpPanel, gridBag, constraints, panel);

		ArrayList outstandingDetails;
		ArrayList disbursementDetails;
		ArrayList repaymentDetails;
		if (periodicInfo.getOutstandingDetails() != null)
		{
			outstandingDetails=periodicInfo.getOutstandingDetails();
		}
		else
		 {
			outstandingDetails=new ArrayList();
		 }
		if (periodicInfo.getDisbursementDetails() != null)
		{
			disbursementDetails=periodicInfo.getDisbursementDetails();
		}
		else
		 {
			disbursementDetails=new ArrayList();
		 }
		if (periodicInfo.getRepaymentDetails() != null)
		{
			repaymentDetails=periodicInfo.getRepaymentDetails();
		}
		else
		 {
			repaymentDetails=new ArrayList();
		 }
		com.cgtsi.guaranteemaintenance.NPADetails npaDetails = new com.cgtsi.guaranteemaintenance.NPADetails();
//		System.out.println("null ???? " + periodicInfo.getNpaDetail()==null);
		if (periodicInfo.getNpaDetail() != null)
		{
			npaDetails = periodicInfo.getNpaDetail();
//			System.out.println("npa detail not null " + npaDetails.getLegalSuitDetail()==null);
		}
		com.cgtsi.guaranteemaintenance.Recovery recovery = new com.cgtsi.guaranteemaintenance.Recovery();
//		if ((flag.equals("MOD")) || (flag.equals("VER")))
//		{
			if (periodicInfo.getNpaDetail() != null)
			{
				npaDetails = periodicInfo.getNpaDetail();
			}
			if (periodicInfo.getRecoveryDetails().size() > 0)
			{
				com.cgtsi.guaranteemaintenance.Recovery tempRec = (com.cgtsi.guaranteemaintenance.Recovery) (periodicInfo.getRecoveryDetails()).get(0);
				if (tempRec!=null && (tempRec.getRecoveryNo() == null || tempRec.getRecoveryNo().equals("")))
				{
					recovery = (com.cgtsi.guaranteemaintenance.Recovery) (periodicInfo.getRecoveryDetails()).get(0);
				}
			}
//		}

		ArrayList noOfOsCounts = new ArrayList();
		if (!flag.equalsIgnoreCase("VER"))
		{
			if (txtNoOfOsInput!=null)
			{
			StringTokenizer osCounts = new StringTokenizer(txtNoOfOsInput.getText().trim(), ",");
			while (osCounts.hasMoreTokens())
			{
				noOfOsCounts.add(osCounts.nextToken().trim());
			}
			osCounts=null;
			}
		}
		displayOutstandingDetails(outstandingDetails, flag, noOfOsCounts);
		addComponent(0, 3, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, outstandingPanel, gridBag, constraints, panel);

		ArrayList noOfDisCounts = new ArrayList();
		if (!flag.equalsIgnoreCase("VER"))
		{
			if (txtNoOfDisInput!=null)
			{
			StringTokenizer disCounts = new StringTokenizer(txtNoOfDisInput.getText().trim(), ",");
			while (disCounts.hasMoreTokens())
			{
				noOfDisCounts.add(disCounts.nextToken().trim());
			}
			disCounts=null;		
			}
		}
		displayDisbursementDetails(disbursementDetails, flag, noOfDisCounts);
		addComponent(0, 4, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, disbursementPanel, gridBag, constraints, panel);
		
		ArrayList noOfRepayCounts = new ArrayList();
		if (!flag.equalsIgnoreCase("VER"))
		{
			if (txtNoOfRepayInput!=null)
			{
			StringTokenizer repayCounts = new StringTokenizer(txtNoOfRepayInput.getText().trim(), ",");
			while (repayCounts.hasMoreTokens())
			{
				noOfRepayCounts.add(repayCounts.nextToken().trim());
			}
			repayCounts=null;		
			}
		}
		displayRepaymentDetails(repaymentDetails, flag, noOfRepayCounts);
		addComponent(0, 5, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, repaymentPanel, gridBag, constraints, panel);
		displayNPADetails(periodicInfo.getBorrowerId(), npaDetails, flag);
		addComponent(0, 6, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, npaDetailsPanel, gridBag, constraints, panel);
		displayRecoveryDetails(recovery, flag);
		addComponent(0, 7, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, recoveryPanel, gridBag, constraints, panel);

		lblKey = new JLabel(key);
		lblFileName = new JLabel(fileName);
		lblKey.setVisible(false);
		lblFileName.setVisible(false);
		if (periodicInfo.getExport())
		{
			lblExport = new JLabel("T");
		}
		else
		{
			lblExport = new JLabel("F");
		}

		details=null;
		outstandingDetails=null;
		periodicInfo=null;
		disbursementDetails=null;
		repaymentDetails=null;
		npaDetails=null;
		recovery=null;

		lblExport.setVisible(false);
		hiddenPanel=new JPanel();
		hiddenPanel.add(lblKey);
		hiddenPanel.add(lblFileName);
		hiddenPanel.add(lblExport);
		hiddenPanel.setVisible(false);
		addComponent(0, 8, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, hiddenPanel, gridBag, constraints, panel);

		 reset=new JButton("Reset");
		 reset.setActionCommand("Reset");
		 reset.addActionListener(this);

		 save=new JButton("Save");
		 cancel=new JButton("Cancel");
		 if (flag.equalsIgnoreCase("NEW"))
		 {
			 save.setActionCommand("saveNewPeriodicInfo");
			 cancel.setActionCommand("CancelNewPeriodicInfo");
		 }
		 else if (flag.equalsIgnoreCase("MOD"))
		 {
			 save.setActionCommand("saveModifiedPeriodicInfo");
			 cancel.setActionCommand("CancelModifyPeriodicInfo");
		 }
		 else if (flag.equalsIgnoreCase("VER"))
		 {
			 save.setActionCommand("saveVerifiedPeriodicInfo");
			 cancel.setActionCommand("CancelVerifyPeriodicInfo");
		 }

		 save.addActionListener(this);
		 cancel.addActionListener(this);
		 
		print = new JButton("Print");
		print.setActionCommand("PrintPerApplication");
		print.addActionListener(this);

		 buttonsPanel=new JPanel();
		 buttonsPanel.setLayout(new FlowLayout());
		buttonsPanel.add(save);
		 buttonsPanel.add(reset);
		 buttonsPanel.add(cancel);
		buttonsPanel.add(print);
		 addComponent(0, 9, GridBagConstraints.REMAINDER, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, buttonsPanel, gridBag, constraints, panel);

		container.removeAll();
		scrollPane.setViewportView(panel);
		container.add(scrollPane, BorderLayout.CENTER);
	}
	
	private void displayPeriodicInputPanel(String key, String fileName, String flag) throws ThinClientException
	{
		panel.removeAll();
		panel.setBackground(dataBg);
		panel.setLayout(gridBag);
		String brnName="";		
		
		if (fileName.equals(""))
		{
			borrowerIdInput = new JLabel("Enter the Borrower ID");
			borrowerIdInput.setFont(headingFont);
			borrowerIdInput.setOpaque(true);
			borrowerIdInput.setBackground(dataBg);
			addComponent(0, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, borrowerIdInput, gridBag, constraints, panel);
		
			txtBorrowerIdInput = new JTextField(new TextFormatField(9),"",10);
			addComponent(1, 0, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtBorrowerIdInput, gridBag, constraints, panel);

			borrowerNameInput = new JLabel("Enter the Borrower Name");
			borrowerNameInput.setFont(headingFont);
			borrowerNameInput.setOpaque(true);
			borrowerNameInput.setBackground(dataBg);
			addComponent(0, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, borrowerNameInput, gridBag, constraints, panel);
		
			txtBorrowerNameInput = new JTextField(new TextFormatField(100),"",10);
			addComponent(1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtBorrowerNameInput, gridBag, constraints, panel);

			OSCgpansInput = new JLabel("Enter the CGPANs for Outstanding Details *");
			OSCgpansInput.setFont(headingFont);
			OSCgpansInput.setOpaque(true);
			OSCgpansInput.setBackground(dataBg);
			addComponent(0, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, OSCgpansInput, gridBag, constraints, panel);
			
			txtOSCgpansInput = new JTextField(new TextFormatField(13),"",30);
			addComponent(1, 2, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtOSCgpansInput, gridBag, constraints, panel);

			noOfOsInput = new JLabel("Enter the No of Entries of Outstandings for the list of CGPANs #");
			noOfOsInput.setFont(headingFont);
			noOfOsInput.setOpaque(true);
			noOfOsInput.setBackground(dataBg);
			addComponent(0, 3, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, noOfOsInput, gridBag, constraints, panel);
			
			txtNoOfOsInput = new JTextField(10);
			addComponent(1, 3, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtNoOfOsInput, gridBag, constraints, panel);
	
			disCgpansInput = new JLabel("Enter the CGPANs for Disbursement Details *");
			disCgpansInput.setFont(headingFont);
			disCgpansInput.setOpaque(true);
			disCgpansInput.setBackground(dataBg);
			addComponent(0, 4, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, disCgpansInput, gridBag, constraints, panel);
			
			txtDisCgpansInput = new JTextField(30);
			addComponent(1, 4, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtDisCgpansInput, gridBag, constraints, panel);
	
			noOfDisInput = new JLabel("Enter the No of Entries of Disbursements for the list of CGPANs #");
			noOfDisInput.setFont(headingFont);
			noOfDisInput.setOpaque(true);
			noOfDisInput.setBackground(dataBg);
			addComponent(0, 5, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, noOfDisInput, gridBag, constraints, panel);
			
			txtNoOfDisInput = new JTextField(10);
			addComponent(1, 5, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtNoOfDisInput, gridBag, constraints, panel);

			repayCgpansInput = new JLabel("Enter the CGPANs for Repayment Details *");
			repayCgpansInput.setFont(headingFont);
			repayCgpansInput.setOpaque(true);
			repayCgpansInput.setBackground(dataBg);
			addComponent(0, 6, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, repayCgpansInput, gridBag, constraints, panel);
			
			txtRepayCgpansInput = new JTextField(30);
			addComponent(1, 6, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtRepayCgpansInput, gridBag, constraints, panel);
	
			noOfRepayInput = new JLabel("Enter the No of Entries of Repayments for the list of CGPANs #");
			noOfRepayInput.setFont(headingFont);
			noOfRepayInput.setOpaque(true);
			noOfRepayInput.setBackground(dataBg);
			addComponent(0, 7, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, noOfRepayInput, gridBag, constraints, panel);
			
			txtNoOfRepayInput = new JTextField(10);
			addComponent(1, 7, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtNoOfRepayInput, gridBag, constraints, panel);

			recActionsInput = new JLabel("Enter the No of Entries of Recovery Actions");
			recActionsInput.setFont(headingFont);
			recActionsInput.setOpaque(true);
			recActionsInput.setBackground(dataBg);
			addComponent(0, 8, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, recActionsInput, gridBag, constraints, panel);
			
			txtRecActionsInput = new JTextField(10);
			txtRecActionsInput.setDocument(new NumberFormatField(2));
			addComponent(1, 8, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtRecActionsInput, gridBag, constraints, panel);

			inputHint = new JLabel("* comma seperated");
			inputHint.setFont(hintFont);
			inputHint.setOpaque(true);
			inputHint.setBackground(dataBg);
			addComponent(0, 9, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, inputHint, gridBag, constraints, panel);
			
			countHint = new JLabel("# in the same order of the CGPANs");
			countHint.setFont(hintFont);
			countHint.setOpaque(true);
			countHint.setBackground(dataBg);
			addComponent(0, 10, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, countHint, gridBag, constraints, panel);
		}
		else if (!fileName.equals(""))
		{
			int index=fileName.lastIndexOf("&");
			if (index>0)
			{
				brnName = fileName.substring(index+1, fileName.length());
				fileName=fileName.substring(0, index);
			}
			
			Hashtable details=readFromFile(fileName);
			com.cgtsi.guaranteemaintenance.PeriodicInfo periodicInfo=(com.cgtsi.guaranteemaintenance.PeriodicInfo) details.get(key);
			
			borrowerIdInput = new JLabel("Borrower ID");
			borrowerIdInput.setFont(headingFont);
			borrowerIdInput.setOpaque(true);
			borrowerIdInput.setBackground(dataBg);
			addComponent(0, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, borrowerIdInput, gridBag, constraints, panel);
			
			borrowerIdInputValue = new JLabel(periodicInfo.getBorrowerId());
			borrowerIdInputValue.setFont(headingFont);
			borrowerIdInputValue.setOpaque(true);
			borrowerIdInputValue.setBackground(dataBg);
			addComponent(1, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, borrowerIdInputValue, gridBag, constraints, panel);

			borrowerNameInput = new JLabel("Borrower Name");
			borrowerNameInput.setFont(headingFont);
			borrowerNameInput.setOpaque(true);
			borrowerNameInput.setBackground(dataBg);
			addComponent(0, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, borrowerNameInput, gridBag, constraints, panel);

			borrowerNameInputValue = new JLabel(periodicInfo.getBorrowerName());
			borrowerNameInputValue.setFont(headingFont);
			borrowerNameInputValue.setOpaque(true);
			borrowerNameInputValue.setBackground(dataBg);
			addComponent(1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, borrowerNameInputValue, gridBag, constraints, panel);

			ArrayList osDetails = periodicInfo.getOutstandingDetails();
			if (osDetails != null && osDetails.size()>0)
			{
				String cgpans = "";
				for (int i=0;i<osDetails.size();i++)
				{
					cgpans = cgpans + "," + ((com.cgtsi.guaranteemaintenance.OutstandingDetail) osDetails.get(i)).getCgpan();
				}
				cgpans = cgpans.substring(1);
				OSCgpansInput = new JLabel("CGPANs for Outstanding Details");
				OSCgpansInput.setFont(headingFont);
				OSCgpansInput.setOpaque(true);
				OSCgpansInput.setBackground(dataBg);
				addComponent(0, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, OSCgpansInput, gridBag, constraints, panel);
				
				OSCgpansInputValue = new JLabel(cgpans);
				OSCgpansInputValue.setFont(headingFont);
				OSCgpansInputValue.setOpaque(true);
				OSCgpansInputValue.setBackground(dataBg);
				addComponent(1, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, OSCgpansInputValue, gridBag, constraints, panel);
	
				noOfOsInput = new JLabel("Enter the No of Outstanding Entries for the list of CGPANs #");
				noOfOsInput.setFont(headingFont);
				noOfOsInput.setOpaque(true);
				noOfOsInput.setBackground(dataBg);
				addComponent(0, 3, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, noOfOsInput, gridBag, constraints, panel);
				
				txtNoOfOsInput = new JTextField(10);
				addComponent(1, 3, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtNoOfOsInput, gridBag, constraints, panel);
			}
	
			ArrayList disDetails = periodicInfo.getDisbursementDetails();
			if (disDetails != null && disDetails.size()>0)
			{
				String cgpans = "";
				for (int i=0;i<disDetails.size();i++)
				{
					cgpans = cgpans + "," + ((com.cgtsi.guaranteemaintenance.Disbursement) disDetails.get(i)).getCgpan();
				}
				cgpans =cgpans.substring(1); 
				disCgpansInput = new JLabel("CGPANs for Disbursement Details");
				disCgpansInput.setFont(headingFont);
				disCgpansInput.setOpaque(true);
				disCgpansInput.setBackground(dataBg);
				addComponent(0, 4, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, disCgpansInput, gridBag, constraints, panel);
				
				disCgpansInputValue = new JLabel(cgpans);
				disCgpansInputValue.setFont(headingFont);
				disCgpansInputValue.setOpaque(true);
				disCgpansInputValue.setBackground(dataBg);
				addComponent(1, 4, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, disCgpansInputValue, gridBag, constraints, panel);
		
				noOfDisInput = new JLabel("Enter the No of Disbursement Entries for the list of CGPANs #");
				noOfDisInput.setFont(headingFont);
				noOfDisInput.setOpaque(true);
				noOfDisInput.setBackground(dataBg);
				addComponent(0, 5, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, noOfDisInput, gridBag, constraints, panel);
				
				txtNoOfDisInput = new JTextField(10);
				addComponent(1, 5, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtNoOfDisInput, gridBag, constraints, panel);
			}

			ArrayList repayDetails = periodicInfo.getRepaymentDetails();
			if (repayDetails != null && repayDetails.size()>0)
			{
				String cgpans = "";
				for (int i=0;i<repayDetails.size();i++)
				{
					cgpans = cgpans + "," + ((com.cgtsi.guaranteemaintenance.Repayment) repayDetails.get(i)).getCgpan();
				}
				cgpans =cgpans.substring(1);
				repayCgpansInput = new JLabel("CGPANs for Repayment Details");
				repayCgpansInput.setFont(headingFont);
				repayCgpansInput.setOpaque(true);
				repayCgpansInput.setBackground(dataBg);
				addComponent(0, 6, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, repayCgpansInput, gridBag, constraints, panel);
				
				repayCgpansInputValue = new JLabel(cgpans);
				repayCgpansInputValue.setFont(headingFont);
				repayCgpansInputValue.setOpaque(true);
				repayCgpansInputValue.setBackground(dataBg);
				addComponent(1, 6, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, repayCgpansInputValue, gridBag, constraints, panel);
		
				noOfRepayInput = new JLabel("Enter the No of Repayment Entries for the list of CGPANs #");
				noOfRepayInput.setFont(headingFont);
				noOfRepayInput.setOpaque(true);
				noOfRepayInput.setBackground(dataBg);
				addComponent(0, 7, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, noOfRepayInput, gridBag, constraints, panel);
				
				txtNoOfRepayInput = new JTextField(10);
				addComponent(1, 7, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtNoOfRepayInput, gridBag, constraints, panel);
			}

			recActionsInput = new JLabel("Enter the No of Recovery Action Entries");
			recActionsInput.setFont(headingFont);
			recActionsInput.setOpaque(true);
			recActionsInput.setBackground(dataBg);
			addComponent(0, 8, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, recActionsInput, gridBag, constraints, panel);
			
			txtRecActionsInput = new JTextField(10);
			txtRecActionsInput.setDocument(new NumberFormatField(2));
			addComponent(1, 8, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtRecActionsInput, gridBag, constraints, panel);

			countHint = new JLabel("# in the same order of the CGPANs");
			countHint.setFont(hintFont);
			countHint.setOpaque(true);
			countHint.setBackground(dataBg);
			addComponent(0, 10, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, countHint, gridBag, constraints, panel);

		}
		
		reset=new JButton("Reset");
		reset.setActionCommand("Reset");
		reset.addActionListener(this);

		ok=new JButton("Ok");
		if (fileName.equals("") && flag.equalsIgnoreCase("NEW"))
		{
			ok.setActionCommand("NewPeriodicNoFile");
		}
		else if (!fileName.equals("") && flag.equalsIgnoreCase("MOD"))
		{
			ok.setActionCommand("ModifyPeriodic");
		}
		else if (!fileName.equals("") && flag.equalsIgnoreCase("NEW"))
		{
			ok.setActionCommand("NewPeriodicWithFile");
		}
		ok.addActionListener(this);
//		System.out.println(ok.getActionCommand());
		
		cancel=new JButton("Cancel");
		if (flag.equalsIgnoreCase("NEW"))
		{ 
			cancel.setActionCommand("CancelNewPeriodicInfo");
		}
		cancel.addActionListener(this);		

		buttonsPanel=new JPanel();
		buttonsPanel.setLayout(new FlowLayout());
		buttonsPanel.add(ok);
		buttonsPanel.add(reset);
		addComponent(0, 11, GridBagConstraints.REMAINDER, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, buttonsPanel, gridBag, constraints, panel);
		
		lblKey = new JLabel(key);
		lblFileName = new JLabel(fileName);
		lblFlag = new JLabel(flag);
		lblBrnName = new JLabel(brnName);
		lblKey.setVisible(false);
		lblFileName.setVisible(false);
		lblFlag.setVisible(false);
		lblBrnName.setVisible(false);
		hiddenPanel=new JPanel();
		hiddenPanel.add(lblKey);
		hiddenPanel.add(lblFileName);
		hiddenPanel.add(lblFlag);
		hiddenPanel.add(lblBrnName);
		hiddenPanel.setVisible(false);
		addComponent(0, 13, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, hiddenPanel, gridBag, constraints, panel);
		
		container.removeAll();
		container.add(panel, BorderLayout.NORTH);
	}
	
	private void displayClaimsInputPanel(boolean first) throws ThinClientException
	{
		panel.removeAll();
		panel.setBackground(dataBg);
		panel.setLayout(gridBag);
		
		borrowerIdInput = new JLabel("Enter the Borrower ID");
		borrowerIdInput.setFont(headingFont);
		borrowerIdInput.setOpaque(true);
		borrowerIdInput.setBackground(dataBg);
		addComponent(0, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, borrowerIdInput, gridBag, constraints, panel);
	
		txtBorrowerIdInput = new JTextField(new TextFormatField(13),"",10);
		addComponent(1, 0, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtBorrowerIdInput, gridBag, constraints, panel);

		borrowerNameInput = new JLabel("Enter the Borrower Name");
		borrowerNameInput.setFont(headingFont);
		borrowerNameInput.setOpaque(true);
		borrowerNameInput.setBackground(dataBg);
		addComponent(0, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, borrowerNameInput, gridBag, constraints, panel);
	
		txtBorrowerNameInput = new JTextField(new TextFormatField(100),"",10);
		addComponent(1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtBorrowerNameInput, gridBag, constraints, panel);
		
		OSCgpansInput = new JLabel("Enter the CGPANs for Entering Term Loan / Composite Details *");
		OSCgpansInput.setFont(headingFont);
		OSCgpansInput.setOpaque(true);
		OSCgpansInput.setBackground(dataBg);
		addComponent(0, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, OSCgpansInput, gridBag, constraints, panel);
		
		txtTCCgpansInput = new JTextField(30);
		addComponent(1, 2, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtTCCgpansInput, gridBag, constraints, panel);			

		disCgpansInput = new JLabel("Enter the CGPANs for Entering Working Capital Details *");
		disCgpansInput.setFont(headingFont);
		disCgpansInput.setOpaque(true);
		disCgpansInput.setBackground(dataBg);
		addComponent(0, 4, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, disCgpansInput, gridBag, constraints, panel);
		
		txtWCCgpansInput = new JTextField(30);
		addComponent(1, 4, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtWCCgpansInput, gridBag, constraints, panel);

		inputHint = new JLabel("* comma seperated");
		inputHint.setFont(hintFont);
		inputHint.setOpaque(true);
		inputHint.setBackground(dataBg);
		addComponent(0, 9, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, inputHint, gridBag, constraints, panel);
			
		reset=new JButton("Reset");
		reset.setActionCommand("Reset");
		reset.addActionListener(this);

		ok=new JButton("Ok");
		if (first)
		{
			ok.setActionCommand("NewClaimsFI");
		}
		else if (!first)
		{
			ok.setActionCommand("NewClaimsSI");
		}
		ok.addActionListener(this);
//		System.out.println(ok.getActionCommand());
		
		cancel=new JButton("Cancel");
		cancel.addActionListener(this);		

		buttonsPanel=new JPanel();
		buttonsPanel.setLayout(new FlowLayout());
		buttonsPanel.add(ok);
		buttonsPanel.add(reset);
		addComponent(0, 11, GridBagConstraints.REMAINDER, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, buttonsPanel, gridBag, constraints, panel);
		
		container.removeAll();
		container.add(panel, BorderLayout.NORTH);

	}

	/**
	 * This method displays the Periodic Info screen when the file is not choosen 
	 * and the user wants to enter the periodic info details.
	 */
	private void displayPeriodicInfo()
	{
		panel.removeAll();
		panel.setBackground(dataBg);
		panel.setLayout(gridBag);

		int i = 0;
		int j = 0;
		String borrowerIdInput = "";
		String borrowerNameInput = "";		
		
		borrowerId = new JLabel("Borrower ID");
		borrowerId.setFont(headingFont);
		borrowerId.setOpaque(true);
		borrowerId.setBackground(dataBg);
		addComponent(0, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, borrowerId, gridBag, constraints, panel);

		borrowerIdInput = txtBorrowerIdInput.getText().trim();
		borrowerIdValue = new JLabel(borrowerIdInput);
		borrowerIdValue.setFont(dataFont);
		borrowerIdValue.setOpaque(true);
		borrowerIdValue.setBackground(dataBg);
		addComponent(1, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, borrowerIdValue, gridBag, constraints, panel);

		borrowerName = new JLabel("Borrower Name");
		borrowerName.setFont(headingFont);
		borrowerName.setOpaque(true);
		borrowerName.setBackground(dataBg);
		addComponent(2, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, borrowerName, gridBag, constraints, panel);

		borrowerNameInput = txtBorrowerNameInput.getText().trim();
		borrowerNameValue = new JLabel(borrowerNameInput);
		borrowerNameValue.setFont(dataFont);
		borrowerNameValue.setOpaque(true);
		borrowerNameValue.setBackground(dataBg);
		addComponent(3, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, borrowerNameValue, gridBag, constraints, panel);

		ArrayList outstandingDetails = new ArrayList();
		StringTokenizer osCgpans = new StringTokenizer(txtOSCgpansInput.getText().trim(),",");
		
		while (osCgpans.hasMoreTokens())
		{
			String cgpan = "";
			cgpan = osCgpans.nextToken();
			com.cgtsi.guaranteemaintenance.OutstandingDetail outstandingDetail = new 		com.cgtsi.guaranteemaintenance.OutstandingDetail();
			outstandingDetail.setCgpan(cgpan);
			outstandingDetails.add(outstandingDetail);
			outstandingDetail=null;			
		}
		noOfOs = outstandingDetails.size();
		StringTokenizer osCounts = new StringTokenizer(txtNoOfOsInput.getText().trim(), ",");
		ArrayList noOfOsCounts = new ArrayList();
//		System.out.println(txtNoOfOsInput.getText().trim());
//		System.out.println(osCounts.countTokens());
		while (osCounts.hasMoreTokens())
		{
			noOfOsCounts.add(osCounts.nextToken());
		}
		for (int x=0;x<noOfOsCounts.size();x++)
		{
//			System.out.println(x + " - " + noOfOsCounts.get(x));
		}
		osCounts=null;
		osCgpans=null;
		displayOutstandingDetails(outstandingDetails, "NEW", noOfOsCounts);
		addComponent(0, 1, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, outstandingPanel, gridBag, constraints, panel);
		outstandingDetails=null;

		ArrayList disbursementDetails = new ArrayList();
		StringTokenizer disCgpans = new StringTokenizer(txtDisCgpansInput.getText().trim(),",");
		
		while (disCgpans.hasMoreTokens())
		{
			String cgpan = "";
			cgpan = disCgpans.nextToken();
			com.cgtsi.guaranteemaintenance.Disbursement disbursement = new 		com.cgtsi.guaranteemaintenance.Disbursement();
			disbursement.setCgpan(cgpan);
			ArrayList disbursementAmts = new ArrayList();
			disbursement.setDisbursementAmounts(disbursementAmts);
			disbursementDetails.add(disbursement);
			disbursement=null;
			disbursementAmts=null;			
		}
		noOfDis = disbursementDetails.size();
		StringTokenizer disCounts = new StringTokenizer(txtNoOfDisInput.getText().trim(), ",");
		ArrayList noOfDisCounts = new ArrayList();
		while (disCounts.hasMoreTokens())
		{
			noOfDisCounts.add(disCounts.nextToken());
		}
		disCounts=null;
		disCgpans=null;		
		displayDisbursementDetails(disbursementDetails, "NEW", noOfDisCounts);
		addComponent(0, 2, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, disbursementPanel, gridBag, constraints, panel);
		disbursementDetails=null;

		ArrayList repaymentDetails = new ArrayList();
		StringTokenizer repayCgpans = new StringTokenizer(txtRepayCgpansInput.getText().trim(),",");
		
		while (repayCgpans.hasMoreTokens())
		{
			String cgpan = "";
			cgpan = repayCgpans.nextToken();
			com.cgtsi.guaranteemaintenance.Repayment repayment = new com.cgtsi.guaranteemaintenance.Repayment();
			repayment.setCgpan(cgpan);
			ArrayList repaymentAmts = new ArrayList();
			repayment.setRepaymentAmounts(repaymentAmts);
			repaymentDetails.add(repayment);
			repayment=null;
			repaymentAmts=null;
		}
		noOfRepay = repaymentDetails.size();
		StringTokenizer repayCounts = new StringTokenizer(txtNoOfRepayInput.getText().trim(), ",");
		ArrayList noOfRepayCounts = new ArrayList();
		while (repayCounts.hasMoreTokens())
		{
			noOfRepayCounts.add(repayCounts.nextToken());
		}
		repayCounts=null;
		repayCgpans=null;		
		displayRepaymentDetails(repaymentDetails, "NEW", noOfRepayCounts);
		addComponent(0, 3, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, repaymentPanel, gridBag, constraints, panel);

		com.cgtsi.guaranteemaintenance.NPADetails npaDetails = new com.cgtsi.guaranteemaintenance.NPADetails();
		displayNPADetails(borrowerIdInput, npaDetails, "NEW");
		addComponent(0, 4, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, npaDetailsPanel, gridBag, constraints, panel);

		com.cgtsi.guaranteemaintenance.Recovery recovery = new com.cgtsi.guaranteemaintenance.Recovery();
		displayRecoveryDetails(recovery, "NEW");
		addComponent(0, 5, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, recoveryPanel, gridBag, constraints, panel);

		lblKey = new JLabel(borrowerIdInput);
		lblFileName = new JLabel("");
		lblExport = new JLabel("F");
		lblKey.setVisible(false);
		lblFileName.setVisible(false);
		lblExport.setVisible(false);
		hiddenPanel=new JPanel();
		hiddenPanel.add(lblKey);
		hiddenPanel.add(lblFileName);
		hiddenPanel.add(lblExport);
		hiddenPanel.setVisible(false);
		addComponent(0, 6, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, hiddenPanel, gridBag, constraints, panel);

		reset=new JButton("Reset");
		reset.setActionCommand("Reset");
		reset.addActionListener(this);

		save=new JButton("Save");
		cancel=new JButton("Cancel");
		save.setActionCommand("saveNewPeriodicInfo");
		save.addActionListener(this);
		cancel.setActionCommand("Cancel");
		cancel.addActionListener(this);

		buttonsPanel=new JPanel();
		buttonsPanel.setLayout(new FlowLayout());
		buttonsPanel.add(save);
		buttonsPanel.add(reset);
		buttonsPanel.add(cancel);
		addComponent(0, 7, GridBagConstraints.REMAINDER, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, buttonsPanel, gridBag, constraints, panel);

		container.removeAll();
		scrollPane.setViewportView(panel);
		container.add(scrollPane, BorderLayout.CENTER);
	}

	private void displayUpdateRecovery(String key, String fileName, String flag) throws ThinClientException
	{
		try
		{
			 panel.removeAll();
			 panel.setBackground(Color.WHITE);
			 panel.setLayout(gridBag);

			 recActionsListHeading=new JLabel("List of Recovery Actions");
			 recActionsListHeading.setOpaque(true);
			 recActionsListHeading.setBackground(headingBg);
			 addComponent(0, 1, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, recActionsListHeading, gridBag, constraints, panel);
			 
			int index=fileName.lastIndexOf("&");
			String brnName="";
			if (index>0)
			{
				brnName = fileName.substring(index+1, fileName.length());
				fileName=fileName.substring(0, index);
			}

			 Hashtable objects=readFromFile(fileName);
			 com.cgtsi.guaranteemaintenance.PeriodicInfo periodicInfo = (com.cgtsi.guaranteemaintenance.PeriodicInfo) objects.get(key);
			 ArrayList recs = periodicInfo.getRecoveryDetails();

			 JPanel recIdPanel=new JPanel();
			 recIdPanel.setBackground(Color.WHITE);
			 recIdGroup=new ButtonGroup();

			 for (int i=0;i<recs.size();i++)
			 {
				 com.cgtsi.guaranteemaintenance.Recovery recovery = (com.cgtsi.guaranteemaintenance.Recovery) recs.get(i);
				 String recId = recovery.getRecoveryNo();
				 if (recId==null)
				 {
					recId="new";
				 }
				 JRadioButton keyRecId = new JRadioButton(recId);
				 keyRecId.setActionCommand(flag + "$" + key + "$" + fileName + "&" + brnName + "$" + recId);
//				 System.out.println("rec action command = " + keyRecId.getActionCommand());
				 keyRecId.addActionListener(new UpdRecoveryListener());
				 keyRecId.setBackground(Color.WHITE);
				 recIdGroup.add(keyRecId);
				 recIdPanel.add(keyRecId);
			 }
			 addComponent(0, 2, GridBagConstraints.REMAINDER, GridBagConstraints.CENTER, GridBagConstraints.BOTH, recIdPanel, gridBag, constraints, panel);

			cancel=new JButton("Cancel");
			cancel.setActionCommand("CancelRecIdList");
			cancel.addActionListener(this);

			buttonsPanel=new JPanel();
			buttonsPanel.setLayout(new FlowLayout());
			buttonsPanel.add(cancel);
			addComponent(0, 3, GridBagConstraints.REMAINDER, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, buttonsPanel, gridBag, constraints, panel);

			container.removeAll();
			container.add(panel, BorderLayout.NORTH);

		}
		catch (Exception e)
		{
			throw new ThinClientException(e.getMessage());
		}
	}

	/**
	 * This method displays the outstanding details of the periodic info
	 */
	 private void displayOutstandingDetails(ArrayList osDetails, String flag, ArrayList counts)
	 {
		outstandingPanel.removeAll();
		outstandingPanel.setBackground(dataBg);
		outstandingPanel.setLayout(gridBag);

		String sCgpan;

		int osSize=osDetails.size();
		int osAmtsSize=0;
		noOfOutstandings = new int[osSize];

		int i=0;
		osCgpanValue=new JLabel[osSize];
		schemeValue=new JLabel[osSize];
		slNoValue=new JLabel[osSize];
		facilityValue=new JLabel[osSize];
		sanctionedTC=new JLabel[osSize];
		sanctionedWCFB=new JLabel[osSize];
		sanctionedWCNFB=new JLabel[osSize];
		totalOutstandingAmtValue = new JLabel[osSize];
		totalNFBOutstandingAmtValue = new JLabel[osSize];

		com.cgtsi.guaranteemaintenance.OutstandingDetail outstandingDetail;
		com.cgtsi.guaranteemaintenance.OutstandingAmount outstandingAmount;
		ArrayList osAmts;
		int rowNo=0;

		 int noOfOsMade=0;
		 int noOfOsPending=0;
		 int input = 0;
		 String message;
		 String response;

		if (osSize != 0)
		{
			outstandingHeading=new JLabel("Outstanding Details");
			outstandingHeading.setFont(headingFont);
			outstandingHeading.setOpaque(true);
			outstandingHeading.setBackground(headingBg);
			addComponent(0, 0, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, outstandingHeading, gridBag, constraints, outstandingPanel);
		}

		/**
		 * This for loop calculates the no of outstandings already made (sum of all the oustandings
		 * made for cgpan).
		 * It also gets the input from the user of the no of oustanding that the user wants to enter.
		 */
		for (i=0;i<osSize;i++)
		{
			outstandingDetail=(com.cgtsi.guaranteemaintenance.OutstandingDetail) osDetails.get(i);
			if (outstandingDetail.getOutstandingAmounts() != null)
			{
				osAmts= outstandingDetail.getOutstandingAmounts();
			}
			else
			{
				osAmts=new ArrayList();
			}
			osAmtsSize=osAmts.size();
			noOfOsMade += osAmtsSize;		
			noOfOutstandings[i] = osAmtsSize;

			sCgpan = outstandingDetail.getCgpan();
			if (!(flag.equalsIgnoreCase("VER")))
			{
				noOfOutstandings[i] += Integer.parseInt((String)counts.get(i));
				noOfOsPending += Integer.parseInt((String)counts.get(i));
			}
		}


		int totalOs = noOfOsMade + noOfOsPending;
		tcoIdValue=new JLabel[totalOs];
		wcoIdValue=new JLabel[totalOs];
		txtTCPrincipal=new JTextField[totalOs];
		txtWCFBPrincipal=new JTextField[totalOs];
		txtWCNFBPrincipal=new JTextField[totalOs];
		txtWCFBIntComm=new JTextField[totalOs];
		txtWCNFBIntComm=new JTextField[totalOs];
		txtTCAsOnDate=new JTextField[totalOs];
		txtWCFBAsOnDate=new JTextField[totalOs];
		txtWCNFBAsOnDate=new JTextField[totalOs];

		int iOutstandings = 0;
		int tempIndex=0;
		String text="";
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		for (i=0;i<osSize;i++)
		{
			rowNo++;
			outstandingDetail = (com.cgtsi.guaranteemaintenance.OutstandingDetail) osDetails.get(i);
			sCgpan=outstandingDetail.getCgpan();
			pcgpan=new JLabel("CGPAN");
			pcgpan.setFont(headingFont);
			pcgpan.setOpaque(true);
			pcgpan.setBackground(dataBg);
//			addComponent(0, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, pcgpan, gridBag, constraints, outstandingPanel);

			osCgpanValue[i] = new JLabel("     "+sCgpan, JLabel.CENTER);
			osCgpanValue[i].setFont(dataFont);
			osCgpanValue[i].setOpaque(true);
			osCgpanValue[i].setBackground(dataBg);
//			addComponent(1, rowNo, 3, GridBagConstraints.WEST, GridBagConstraints.BOTH, osCgpanValue[i], gridBag, constraints, outstandingPanel);

			scheme=new JLabel("  Scheme");
			scheme.setFont(headingFont);
			scheme.setOpaque(true);
			scheme.setBackground(dataBg);
//			addComponent(4, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, scheme, gridBag, constraints, outstandingPanel);

			schemeValue[i] = new JLabel("     "+outstandingDetail.getScheme(), JLabel.CENTER);
			schemeValue[i].setFont(dataFont);
			schemeValue[i].setOpaque(true);
			schemeValue[i].setBackground(dataBg);
//			addComponent(5, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, schemeValue[i], gridBag, constraints, outstandingPanel);
			
			grpPanel = new JPanel();
			grpPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
			grpPanel.setBackground(dataBg);	
			grpPanel.add(pcgpan);	
			grpPanel.add(osCgpanValue[i]);
			grpPanel.add(scheme);	
			grpPanel.add(schemeValue[i]);
			addComponent(0, rowNo, 5, GridBagConstraints.WEST, GridBagConstraints.BOTH, grpPanel, gridBag, constraints, outstandingPanel);			

			rowNo++;

			slNo=new JLabel("Sl");
			slNo.setFont(headingFont);
			slNo.setOpaque(true);
			slNo.setBackground(headingBg);
			slNo.setVerticalAlignment(JLabel.TOP);
			constraints.gridheight=1;
			addComponent(0, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, slNo, gridBag, constraints, outstandingPanel);
			
			facility=new JLabel("Facility");
			facility.setFont(headingFont);
			facility.setOpaque(true);
			facility.setBackground(headingBg);
			facility.setVerticalAlignment(JLabel.TOP);
			constraints.gridheight=5;
			addComponent(1, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, facility, gridBag, constraints, outstandingPanel);

			sanctioned=new JLabel("Sanctioned (in Rs)", SwingConstants.CENTER);
			sanctioned.setFont(headingFont);
			sanctioned.setOpaque(true);
			sanctioned.setBackground(headingBg);
			constraints.gridheight=1;
			addComponent(2, rowNo, 2, GridBagConstraints.WEST, GridBagConstraints.BOTH, sanctioned, gridBag, constraints, outstandingPanel);

			outstanding=new JLabel("Outstanding (in Rs)", SwingConstants.CENTER);
			outstanding.setFont(headingFont);
			outstanding.setOpaque(true);
			outstanding.setBackground(headingBg);
			constraints.gridheight=1;
			addComponent(4, rowNo, 3, GridBagConstraints.WEST, GridBagConstraints.BOTH, outstanding, gridBag, constraints, outstandingPanel);

			totalOutstanding=new JLabel("Total");
			totalOutstanding.setFont(headingFont);
			totalOutstanding.setOpaque(true);
			totalOutstanding.setBackground(headingBg);
			constraints.gridheight=1;
			addComponent(7, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, totalOutstanding, gridBag, constraints, outstandingPanel);
			
			outstanding=new JLabel("Non Fund Based Outstanding (in Rs)", SwingConstants.CENTER);
			outstanding.setFont(headingFont);
			outstanding.setOpaque(true);
			outstanding.setBackground(headingBg);
			constraints.gridheight=1;
			addComponent(8, rowNo, 3, GridBagConstraints.WEST, GridBagConstraints.BOTH, outstanding, gridBag, constraints, outstandingPanel);			

			totalOutstanding=new JLabel("Total");
			totalOutstanding.setFont(headingFont);
			totalOutstanding.setOpaque(true);
			totalOutstanding.setBackground(headingBg);
			constraints.gridheight=1;
			addComponent(11, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, totalOutstanding, gridBag, constraints, outstandingPanel);

			rowNo++;

			slNo=new JLabel("No");
			slNo.setFont(headingFont);
			slNo.setOpaque(true);
			slNo.setBackground(headingBg);
			slNo.setVerticalAlignment(JLabel.TOP);
			constraints.gridheight=4;
			addComponent(0, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, slNo, gridBag, constraints, outstandingPanel);

			sanctioned=new JLabel("Sanctioned");
			sanctioned.setFont(headingFont);
			sanctioned.setOpaque(true);
			sanctioned.setBackground(headingBg);
			constraints.gridheight=1;
			addComponent(2, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, sanctioned, gridBag, constraints, outstandingPanel);
			
			sanctioned=new JLabel("Non Fund", JLabel.CENTER);
			sanctioned.setFont(headingFont);
			sanctioned.setOpaque(true);
			sanctioned.setBackground(headingBg);
			constraints.gridheight=1;
			addComponent(3, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, sanctioned, gridBag, constraints, outstandingPanel);			

			principal=new JLabel("Amount", JLabel.CENTER);
			principal.setFont(headingFont);
			principal.setOpaque(true);
			principal.setBackground(headingBg);
			constraints.gridheight=4;
			addComponent(4, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, principal, gridBag, constraints, outstandingPanel);

			intCommission=new JLabel("Interest", JLabel.CENTER);
			intCommission.setFont(headingFont);
			intCommission.setOpaque(true);
			intCommission.setBackground(headingBg);
			addComponent(5, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, intCommission, gridBag, constraints, outstandingPanel);
			
			asOnDate=new JLabel("As On Date", JLabel.CENTER);
			asOnDate.setFont(headingFont);
			asOnDate.setOpaque(true);
			asOnDate.setBackground(headingBg);
			constraints.gridheight=4;
			addComponent(6, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, asOnDate, gridBag, constraints, outstandingPanel);

			totalOutstanding=new JLabel("Outstanding ");
			totalOutstanding.setFont(headingFont);
			totalOutstanding.setOpaque(true);
			totalOutstanding.setBackground(headingBg);
			constraints.gridheight=1;
			addComponent(7, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, totalOutstanding, gridBag, constraints, outstandingPanel);			
			
			principal=new JLabel("Amount", JLabel.CENTER);
			principal.setFont(headingFont);
			principal.setOpaque(true);
			principal.setBackground(headingBg);
			constraints.gridheight=4;
			addComponent(8, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, principal, gridBag, constraints, outstandingPanel);

			intCommission=new JLabel("Commission", JLabel.CENTER);
			intCommission.setFont(headingFont);
			intCommission.setOpaque(true);
			intCommission.setBackground(headingBg);
			addComponent(9, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, intCommission, gridBag, constraints, outstandingPanel);
			
			asOnDate=new JLabel("As On Date", JLabel.CENTER);
			asOnDate.setFont(headingFont);
			asOnDate.setOpaque(true);
			asOnDate.setBackground(headingBg);
			constraints.gridheight=4;
			addComponent(10, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, asOnDate, gridBag, constraints, outstandingPanel);
			
			totalOutstanding=new JLabel("Outstanding ");
			totalOutstanding.setFont(headingFont);
			totalOutstanding.setOpaque(true);
			totalOutstanding.setBackground(headingBg);
			constraints.gridheight=1;
			addComponent(11, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, totalOutstanding, gridBag, constraints, outstandingPanel);
			
			rowNo++;
			
			sanctioned=new JLabel("Amount");
			sanctioned.setFont(headingFont);
			sanctioned.setOpaque(true);
			sanctioned.setBackground(headingBg);
			sanctioned.setVerticalAlignment(JLabel.TOP);
			constraints.gridheight=3;
			addComponent(2, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, sanctioned, gridBag, constraints, outstandingPanel);
			
			sanctioned=new JLabel("Based ");
			sanctioned.setFont(headingFont);
			sanctioned.setOpaque(true);
			sanctioned.setBackground(headingBg);
			constraints.gridheight=1;
			addComponent(3, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, sanctioned, gridBag, constraints, outstandingPanel);
			
			totalOutstanding=new JLabel("Amount");
			totalOutstanding.setFont(headingFont);
			totalOutstanding.setOpaque(true);
			totalOutstanding.setBackground(headingBg);
			constraints.gridheight=1;
			addComponent(7, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, totalOutstanding, gridBag, constraints, outstandingPanel);
			
			totalOutstanding=new JLabel("Amount");
			totalOutstanding.setFont(headingFont);
			totalOutstanding.setOpaque(true);
			totalOutstanding.setBackground(headingBg);
			constraints.gridheight=1;
			addComponent(11, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, totalOutstanding, gridBag, constraints, outstandingPanel);
			
			rowNo++;

			sanctioned=new JLabel("Sanctioned");
			sanctioned.setFont(headingFont);
			sanctioned.setOpaque(true);
			sanctioned.setBackground(headingBg);
			constraints.gridheight=1;
			addComponent(3, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, sanctioned, gridBag, constraints, outstandingPanel);
			
			totalOutstanding=new JLabel("(in Rs)", JLabel.CENTER);
			totalOutstanding.setFont(headingFont);
			totalOutstanding.setOpaque(true);
			totalOutstanding.setBackground(headingBg);
			constraints.gridheight=2;
			addComponent(7, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, totalOutstanding, gridBag, constraints, outstandingPanel);
			
			totalOutstanding=new JLabel("(in Rs)", JLabel.CENTER);
			totalOutstanding.setFont(headingFont);
			totalOutstanding.setOpaque(true);
			totalOutstanding.setBackground(headingBg);
			constraints.gridheight=2;
			addComponent(11, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, totalOutstanding, gridBag, constraints, outstandingPanel);
			
			rowNo++;
			
			sanctioned=new JLabel("Amount");
			sanctioned.setFont(headingFont);
			sanctioned.setOpaque(true);
			sanctioned.setBackground(headingBg);
			constraints.gridheight=1;
			addComponent(3, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, sanctioned, gridBag, constraints, outstandingPanel);

			String appType=sCgpan.substring(sCgpan.length()-2, sCgpan.length()-1);
//			System.out.println("app type -- " + appType);
			String type="";
			rowNo++;

			double totalAmt = 0;
			double totalNFBAmt=0;

			if (appType.toUpperCase().equalsIgnoreCase("T"))
			{
//				totalAmt = displayTCOutstanding(rowNo, outstandingDetail, i, flag);

				slNoValue[i] = new JLabel(""+(i+1));
				slNoValue[i].setFont(dataFont);
				slNoValue[i].setOpaque(true);
				slNoValue[i].setBackground(dataBg);
				addComponent(0, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, slNoValue[i], gridBag, constraints, outstandingPanel);

				type=sCgpan.substring(sCgpan.length()-2, sCgpan.length());
				facilityValue[i] = new JLabel(type);
				tc = new JLabel("TC");
				tc.setFont(dataFont);
				tc.setOpaque(true);
				tc.setBackground(dataBg);
				addComponent(1, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, tc, gridBag, constraints, outstandingPanel);
				
//				System.out.println("cgpan " + outstandingDetail.getCgpan());
//				System.out.println("sanc amt " + outstandingDetail.getTcSanctionedAmount());

				sanctionedTC[i] = new JLabel(""+outstandingDetail.getTcSanctionedAmount());
				sanctionedTC[i].setFont(dataFont);
				sanctionedTC[i].setOpaque(true);
				sanctionedTC[i].setBackground(dataBg);
				addComponent(2, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, sanctionedTC[i], gridBag, constraints, outstandingPanel);

				if (outstandingDetail.getOutstandingAmounts() != null)
				{
					osAmts=outstandingDetail.getOutstandingAmounts();
				}
				else
				{
					osAmts=new ArrayList();
				}
				osAmtsSize=osAmts.size();

				for (int j=0;j<osAmtsSize;j++)
				{
					outstandingAmount=(com.cgtsi.guaranteemaintenance.OutstandingAmount) osAmts.get(j);

					tcoIdValue[iOutstandings] = new JLabel("");
					if (outstandingAmount.getTcoId() != null)
					{
						tcoIdValue[iOutstandings].setText(outstandingAmount.getTcoId());
//						System.out.println("tc index , value -- " + iOutstandings + ", " + outstandingAmount.getTcoId());
					}
					tcoIdValue[iOutstandings].setVisible(false);
					addComponent(4, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, tcoIdValue[iOutstandings], gridBag, constraints, outstandingPanel);

					txtTCPrincipal[iOutstandings] = new JTextField(8);
//					System.out.println("tc iOutstandings -- " + iOutstandings);
					txtTCPrincipal[iOutstandings].setDocument(new DecimalFormatField(13,2));
					txtTCPrincipal[iOutstandings].addFocusListener(new PeriodicOutstandingTotal());
					text = df.format(outstandingAmount.getTcPrincipalOutstandingAmount());
					txtTCPrincipal[iOutstandings].setText(text);
					totalAmt += outstandingAmount.getTcPrincipalOutstandingAmount();
					addComponent(4, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtTCPrincipal[iOutstandings], gridBag, constraints, outstandingPanel);

					intCommission=new JLabel("N/A");
					intCommission.setFont(dataFont);
					intCommission.setOpaque(true);
					intCommission.setBackground(dataBg);
					addComponent(5, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, intCommission, gridBag, constraints, outstandingPanel);

					txtTCAsOnDate[iOutstandings] = new JTextField(8);
					setDateFieldProperties(txtTCAsOnDate[iOutstandings], "dd/MM/yyyy", false);
					txtTCAsOnDate[iOutstandings].setText( dateFormat.format(outstandingAmount.getTcOutstandingAsOnDate()));
					addComponent(6, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtTCAsOnDate[iOutstandings], gridBag, constraints, outstandingPanel);

					iOutstandings++;
					rowNo++;
				}
				tempIndex = noOfOutstandings[i] - osAmtsSize;

				for (int j=0;j<tempIndex;j++)
				{
//					System.out.println("tc iOutstandings -- " + iOutstandings);

					tcoIdValue[iOutstandings] = new JLabel("");
					tcoIdValue[iOutstandings].setVisible(false);
					addComponent(4, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, tcoIdValue[iOutstandings], gridBag, constraints, outstandingPanel);
					
					txtTCPrincipal[iOutstandings] = new JTextField(8);
					txtTCPrincipal[iOutstandings].setDocument(new DecimalFormatField(13,2));
					txtTCPrincipal[iOutstandings].addFocusListener(new PeriodicOutstandingTotal());
					addComponent(4, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtTCPrincipal[iOutstandings], gridBag, constraints, outstandingPanel);

					intCommission=new JLabel("N/A");
					intCommission.setFont(dataFont);
					intCommission.setOpaque(true);
					intCommission.setBackground(dataBg);
					addComponent(5, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, intCommission, gridBag, constraints, outstandingPanel);

					txtTCAsOnDate[iOutstandings] = new JTextField(8);
					setDateFieldProperties(txtTCAsOnDate[iOutstandings], "dd/MM/yyyy", false);
					addComponent(6, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtTCAsOnDate[iOutstandings], gridBag, constraints, outstandingPanel);

					iOutstandings++;
					rowNo++;
				}
			}
			if (appType.toUpperCase().equalsIgnoreCase("W") || appType.toUpperCase().equalsIgnoreCase("R"))
			{
//				totalAmt = displayWCOutstanding(rowNo, outstandingDetail, i, flag);

				slNoValue[i] = new JLabel(""+(i+1));
				slNoValue[i].setFont(dataFont);
				slNoValue[i].setOpaque(true);
				slNoValue[i].setBackground(dataBg);
				addComponent(0, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, slNoValue[i], gridBag, constraints, outstandingPanel);

				type=sCgpan.substring(sCgpan.length()-2, sCgpan.length());
				facilityValue[i] = new JLabel(type);
				wcFB = new JLabel("WC");
				wcFB.setFont(dataFont);
				wcFB.setOpaque(true);
				wcFB.setBackground(dataBg);
				addComponent(1, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, wcFB, gridBag, constraints, outstandingPanel);

				sanctionedWCFB[i] = new JLabel(""+outstandingDetail.getWcFBSanctionedAmount());
				sanctionedWCFB[i].setFont(dataFont);
				sanctionedWCFB[i].setOpaque(true);
				sanctionedWCFB[i].setBackground(dataBg);
				addComponent(2, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, sanctionedWCFB[i], gridBag, constraints, outstandingPanel);
				
				sanctionedWCNFB[i] = new JLabel(""+outstandingDetail.getWcNFBSanctionedAmount());
				sanctionedWCNFB[i].setFont(dataFont);
				sanctionedWCNFB[i].setOpaque(true);
				sanctionedWCNFB[i].setBackground(dataBg);
				addComponent(3, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, sanctionedWCNFB[i], gridBag, constraints, outstandingPanel);

				if (outstandingDetail.getOutstandingAmounts() != null)
				{
					osAmts=outstandingDetail.getOutstandingAmounts();
				}
				else
				{
					osAmts=new ArrayList();
				}
				osAmtsSize=osAmts.size();

				for (int j=0;j<osAmtsSize;j++)
				{
					outstandingAmount=(com.cgtsi.guaranteemaintenance.OutstandingAmount) osAmts.get(j);

					wcoIdValue[iOutstandings] = new JLabel("");
					if (outstandingAmount.getWcoId() != null)
					{
						wcoIdValue[iOutstandings].setText(outstandingAmount.getWcoId());
//						System.out.println("wc index , value -- " + iOutstandings + ", " + outstandingAmount.getWcoId());
					}
					wcoIdValue[iOutstandings].setVisible(false);
					addComponent(4, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, wcoIdValue[iOutstandings], gridBag, constraints, outstandingPanel);

//					System.out.println("wc iOutstandings -- " + iOutstandings);
					txtWCFBPrincipal[iOutstandings] = new JTextField(8);
					txtWCFBPrincipal[iOutstandings].setDocument(new DecimalFormatField(13,2));
					txtWCFBPrincipal[iOutstandings].addFocusListener(new PeriodicOutstandingTotal());
					text = df.format(outstandingAmount.getWcFBPrincipalOutstandingAmount());
					txtWCFBPrincipal[iOutstandings].setText(text);
					if (outstandingDetail.getWcFBSanctionedAmount()==0)
					{
						txtWCFBPrincipal[iOutstandings].setEnabled(false);
					}
					totalAmt += outstandingAmount.getWcFBPrincipalOutstandingAmount();
					addComponent(4, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtWCFBPrincipal[iOutstandings], gridBag, constraints, outstandingPanel);

					txtWCFBIntComm[iOutstandings] = new JTextField(8);
					txtWCFBIntComm[iOutstandings].setDocument(new DecimalFormatField(13,2));
					txtWCFBIntComm[iOutstandings].addFocusListener(new PeriodicOutstandingTotal());
					text = df.format(outstandingAmount.getWcFBInterestOutstandingAmount());
					txtWCFBIntComm[iOutstandings].setText(text);
					if (outstandingDetail.getWcFBSanctionedAmount()==0)
					{
						txtWCFBIntComm[iOutstandings].setEnabled(false);
					}
					totalAmt += outstandingAmount.getWcFBInterestOutstandingAmount();
					addComponent(5, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtWCFBIntComm[iOutstandings], gridBag, constraints, outstandingPanel);

					txtWCFBAsOnDate[iOutstandings] = new JTextField(8);
					setDateFieldProperties(txtWCFBAsOnDate[iOutstandings], "dd/MM/yyyy", false);
					if (outstandingAmount.getWcFBOutstandingAsOnDate() != null)
					{
						txtWCFBAsOnDate[iOutstandings].setText( dateFormat.format(outstandingAmount.getWcFBOutstandingAsOnDate()));
					}
					if (outstandingDetail.getWcFBSanctionedAmount()==0)
					{
						txtWCFBAsOnDate[iOutstandings].setEnabled(false);
					}					
					addComponent(6, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtWCFBAsOnDate[iOutstandings], gridBag, constraints, outstandingPanel);

			/*		rowNo++;

					wcNFB = new JLabel("WC Non Fund Based");
					wcNFB.setFont(dataFont);
					wcNFB.setOpaque(true);
					wcNFB.setBackground(dataBg);
					addComponent(1, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, wcNFB, gridBag, constraints, outstandingPanel);

					sanctionedWCNFB[i] = new JLabel(""+outstandingDetail.getWcNFBSanctionedAmount());
					sanctionedWCNFB[i].setFont(dataFont);
					sanctionedWCNFB[i].setOpaque(true);
					sanctionedWCNFB[i].setBackground(dataBg);
					addComponent(2, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, sanctionedWCNFB[i], gridBag, constraints, outstandingPanel);*/

					txtWCNFBPrincipal[iOutstandings] = new JTextField(8);
					txtWCNFBPrincipal[iOutstandings].setDocument(new DecimalFormatField(13,2));
					txtWCNFBPrincipal[iOutstandings].addFocusListener(new PeriodicOutstandingTotal());
					text = df.format(outstandingAmount.getWcNFBPrincipalOutstandingAmount());
					txtWCNFBPrincipal[iOutstandings].setText(text);
					if (outstandingDetail.getWcNFBSanctionedAmount()==0)
					{
						txtWCNFBPrincipal[iOutstandings].setEnabled(false);
					}
					totalNFBAmt += outstandingAmount.getWcNFBPrincipalOutstandingAmount();
					addComponent(8, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtWCNFBPrincipal[iOutstandings], gridBag, constraints, outstandingPanel);

					txtWCNFBIntComm[iOutstandings] = new JTextField(8);
					txtWCNFBIntComm[iOutstandings].setDocument(new DecimalFormatField(13, 2));
					txtWCNFBIntComm[iOutstandings].addFocusListener(new PeriodicOutstandingTotal());
					text = df.format(outstandingAmount.getWcNFBInterestOutstandingAmount());
					txtWCNFBIntComm[iOutstandings].setText(text);
					if (outstandingDetail.getWcNFBSanctionedAmount()==0)
					{
						txtWCNFBIntComm[iOutstandings].setEnabled(false);
					}
					totalNFBAmt += outstandingAmount.getWcNFBInterestOutstandingAmount();
					addComponent(9, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtWCNFBIntComm[iOutstandings], gridBag, constraints, outstandingPanel);

					txtWCNFBAsOnDate[iOutstandings] = new JTextField(8);
					setDateFieldProperties(txtWCNFBAsOnDate[iOutstandings], "dd/MM/yyyy", false);
					if (outstandingAmount.getWcNFBOutstandingAsOnDate() != null)
					{
						txtWCNFBAsOnDate[iOutstandings].setText( dateFormat.format(outstandingAmount.getWcNFBOutstandingAsOnDate()));
					}
					if (outstandingDetail.getWcNFBSanctionedAmount()==0)
					{
						txtWCNFBAsOnDate[iOutstandings].setEnabled(false);
					}					
					addComponent(10, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtWCNFBAsOnDate[iOutstandings], gridBag, constraints, outstandingPanel);

					iOutstandings++;
					rowNo++;
				}
				tempIndex = noOfOutstandings[i] - osAmtsSize;

				for (int j=0;j<tempIndex;j++)
				{
//					System.out.println("wc iOutstandings -- " + iOutstandings);

					wcoIdValue[iOutstandings] = new JLabel("");
					wcoIdValue[iOutstandings].setVisible(false);
					addComponent(4, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, wcoIdValue[iOutstandings], gridBag, constraints, outstandingPanel);

					txtWCFBPrincipal[iOutstandings] = new JTextField(8);
					txtWCFBPrincipal[iOutstandings].setDocument(new DecimalFormatField(13,2));
					txtWCFBPrincipal[iOutstandings].addFocusListener(new PeriodicOutstandingTotal());
					if (outstandingDetail.getWcFBSanctionedAmount()==0)
					{
						txtWCFBPrincipal[iOutstandings].setEnabled(false);
					}
					addComponent(4, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtWCFBPrincipal[iOutstandings], gridBag, constraints, outstandingPanel);

					txtWCFBIntComm[iOutstandings] = new JTextField(8);
					txtWCFBIntComm[iOutstandings].setDocument(new DecimalFormatField(13,2));
					txtWCFBIntComm[iOutstandings].addFocusListener(new PeriodicOutstandingTotal());
					if (outstandingDetail.getWcFBSanctionedAmount()==0)
					{
						txtWCFBIntComm[iOutstandings].setEnabled(false);
					}
					addComponent(5, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtWCFBIntComm[iOutstandings], gridBag, constraints, outstandingPanel);

					txtWCFBAsOnDate[iOutstandings] = new JTextField(8);
					setDateFieldProperties(txtWCFBAsOnDate[iOutstandings], "dd/MM/yyyy", false);
					if (outstandingDetail.getWcFBSanctionedAmount()==0)
					{
						txtWCFBAsOnDate[iOutstandings].setEnabled(false);
					}
					addComponent(6, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtWCFBAsOnDate[iOutstandings], gridBag, constraints, outstandingPanel);
					
					txtWCNFBPrincipal[iOutstandings] = new JTextField(8);
					txtWCNFBPrincipal[iOutstandings].setDocument(new DecimalFormatField(13,2));
					txtWCNFBPrincipal[iOutstandings].addFocusListener(new PeriodicOutstandingTotal());
					if (outstandingDetail.getWcNFBSanctionedAmount()==0)
					{
						txtWCNFBPrincipal[iOutstandings].setEnabled(false);
					}
					addComponent(8, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtWCNFBPrincipal[iOutstandings], gridBag, constraints, outstandingPanel);

					txtWCNFBIntComm[iOutstandings] = new JTextField(8);
					txtWCNFBIntComm[iOutstandings].setDocument(new DecimalFormatField(13, 2));
					txtWCNFBIntComm[iOutstandings].addFocusListener(new PeriodicOutstandingTotal());
					if (outstandingDetail.getWcNFBSanctionedAmount()==0)
					{
						txtWCNFBIntComm[iOutstandings].setEnabled(false);
					}
					addComponent(9, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtWCNFBIntComm[iOutstandings], gridBag, constraints, outstandingPanel);

					txtWCNFBAsOnDate[iOutstandings] = new JTextField(8);
					setDateFieldProperties(txtWCNFBAsOnDate[iOutstandings], "dd/MM/yyyy", false);
					if (outstandingDetail.getWcNFBSanctionedAmount()==0)
					{
						txtWCNFBAsOnDate[iOutstandings].setEnabled(false);
					}
					addComponent(10, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtWCNFBAsOnDate[iOutstandings], gridBag, constraints, outstandingPanel);					

					iOutstandings++;
					rowNo++;
				}

				rowNo+=2;
			}
/*			if (appType.toUpperCase().equals("CC"))
			{
				displayTCOutstanding(rowNo, outstandingDetail, i, flag);
				rowNo++;
				displayWCOutstanding(rowNo, outstandingDetail, i, flag);
				rowNo+=2;
			}*/

			totalOutstandingAmtValue[i] = new JLabel(""+df.format(totalAmt));
			totalOutstandingAmtValue[i].setFont(headingFont);
			addComponent(7, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, totalOutstandingAmtValue[i], gridBag, constraints, outstandingPanel);
			
			totalNFBOutstandingAmtValue[i] = new JLabel(""+df.format(totalNFBAmt));
			totalNFBOutstandingAmtValue[i].setFont(headingFont);
			addComponent(11, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, totalNFBOutstandingAmtValue[i], gridBag, constraints, outstandingPanel);			
		}
	}

	/**
	 * This method displays the Term Credit details of outstanding details for a CGPAN.
	 * The parameters are
	 *		rowNo - the row number at which the components have to be added.
	 *		outstandingDetail  - the outstanding details object from which the details are displayed.
	 *		index - the loop index of the whole arraylist of the outstanding details.
	 *		rowNo - the row number in whcih the components are to be added.
	 */
	private double displayTCOutstanding(int rowNo, com.cgtsi.guaranteemaintenance.OutstandingDetail outstandingDetail, int i, String flag)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		double totalAmt = 0;
		String text = "";

/*		slNoValue[i] = new JLabel(""+(i+1));
		slNoValue[i].setFont(dataFont);
		slNoValue[i].setOpaque(true);
		slNoValue[i].setBackground(dataBg);
		addComponent(0, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, slNoValue[i], gridBag, constraints, outstandingPanel);

		facilityValue[i] = new JLabel("TC");
		tc = new JLabel("TC");
		tc.setFont(dataFont);
		tc.setOpaque(true);
		tc.setBackground(dataBg);
		addComponent(1, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, tc, gridBag, constraints, outstandingPanel);

		sanctionedTC[i] = new JLabel(""+outstandingDetail.getTcSanctionedAmount());
		sanctionedTC[i].setFont(dataFont);
		sanctionedTC[i].setOpaque(true);
		sanctionedTC[i].setBackground(dataBg);
		addComponent(2, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, sanctionedTC[i], gridBag, constraints, outstandingPanel);

		txtTCPrincipal[i] = new JTextField(10);
		txtTCPrincipal[i].setDocument(new NumberFormatField(13));
		if ((flag.equals("MOD")) || (flag.equals("VER")))
		{
			text = df.format(outstandingDetail.getTcPrincipalOutstandingAmount());
			txtTCPrincipal[i].setText(text);
			totalAmt += outstandingDetail.getTcPrincipalOutstandingAmount();
		}
		addComponent(3, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtTCPrincipal[i], gridBag, constraints, outstandingPanel);

		intCommission=new JLabel("N/A");
		intCommission.setFont(dataFont);
		intCommission.setOpaque(true);
		intCommission.setBackground(dataBg);
		addComponent(4, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, intCommission, gridBag, constraints, outstandingPanel);

		txtTCAsOnDate[i] = new JTextField(10);
		setDateFieldProperties(txtTCAsOnDate[i], "dd/MM/yyyy", true);
		if ((flag.equals("MOD")) || (flag.equals("VER")))
		{
			txtTCAsOnDate[i].setText(dateFormat.format(outstandingDetail.getTcOutstandingAsOnDate()));
		}
		addComponent(5, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtTCAsOnDate[i], gridBag, constraints, outstandingPanel);
*/
		return totalAmt;
	}

	/**
	 * This method displays the Working Capital outstanding details for a CGPAN.
	 * The parameters are
	 *		rowNo - the row number at which the components have to be added.
	 *		outstandingDetail  - the outstanding details object from which the details are displayed.
	 *		i - the loop index of the whole arraylist of the outstanding details.
	 */
	private double displayWCOutstanding(int rowNo, com.cgtsi.guaranteemaintenance.OutstandingDetail outstandingDetail, int i, String flag)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		double totalAmt = 0;
		String text = "";
		
/*		slNoValue[i] = new JLabel(""+(i+1));
		slNoValue[i].setFont(dataFont);
		slNoValue[i].setOpaque(true);
		slNoValue[i].setBackground(dataBg);
		addComponent(0, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, slNoValue[i], gridBag, constraints, outstandingPanel);

		facilityValue[i] = new JLabel("WC");
		wcFB = new JLabel("WC Fund Based");
		wcFB.setFont(dataFont);
		wcFB.setOpaque(true);
		wcFB.setBackground(dataBg);
		addComponent(1, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, wcFB, gridBag, constraints, outstandingPanel);

		sanctionedWCFB[i] = new JLabel(""+outstandingDetail.getWcFBSanctionedAmount());
		sanctionedWCFB[i].setFont(dataFont);
		sanctionedWCFB[i].setOpaque(true);
		sanctionedWCFB[i].setBackground(dataBg);
		addComponent(2, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, sanctionedWCFB[i], gridBag, constraints, outstandingPanel);

		txtWCFBPrincipal[i] = new JTextField(10);
		txtWCFBPrincipal[i].setDocument(new NumberFormatField(13));
		if ((flag.equals("MOD")) || (flag.equals("VER")))
		{
			text = df.format(outstandingDetail.getWcFBPrincipalOutstandingAmount());
			txtWCFBPrincipal[i].setText(text);
			totalAmt += outstandingDetail.getWcFBPrincipalOutstandingAmount();
		}
		addComponent(3, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtWCFBPrincipal[i], gridBag, constraints, outstandingPanel);

		txtWCFBIntComm[i] = new JTextField(10);
		txtWCFBIntComm[i].setDocument(new DecimalFormatField(2, 2));
		if ((flag.equals("MOD")) || (flag.equals("VER")))
		{
			text = df.format(outstandingDetail.getWcFBInterestOutstandingAmount());
			txtWCFBIntComm[i].setText(text);
			totalAmt += outstandingDetail.getWcFBInterestOutstandingAmount();
		}
		addComponent(4, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtWCFBIntComm[i], gridBag, constraints, outstandingPanel);

		txtWCFBAsOnDate[i] = new JTextField(10);
		setDateFieldProperties(txtWCFBAsOnDate[i], "dd/MM/yyyy", true);
		if ((flag.equals("MOD")) || (flag.equals("VER")))
		{
			txtWCFBAsOnDate[i].setText( dateFormat.format(outstandingDetail.getWcFBOutstandingAsOnDate()));
		}
		addComponent(5, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtWCFBAsOnDate[i], gridBag, constraints, outstandingPanel);

/*		rowNo++;

		wcNFB = new JLabel("WC Non Fund Based");
		wcNFB.setFont(dataFont);
		wcNFB.setOpaque(true);
		wcNFB.setBackground(dataBg);
		addComponent(1, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, wcNFB, gridBag, constraints, outstandingPanel);

		sanctionedWCNFB[i] = new JLabel(""+outstandingDetail.getWcNFBSanctionedAmount());
		sanctionedWCNFB[i].setFont(dataFont);
		sanctionedWCNFB[i].setOpaque(true);
		sanctionedWCNFB[i].setBackground(dataBg);
		addComponent(2, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, sanctionedWCNFB[i], gridBag, constraints, outstandingPanel);

		txtWCNFBPrincipal[i] = new JTextField(10);
		txtWCNFBPrincipal[i].setDocument(new NumberFormatField(13));
		if ((flag.equals("MOD")) || (flag.equals("VER")))
		{
			txtWCNFBPrincipal[i].setText(""+outstandingDetail.getWcNFBInterestOutstandingAmount());
			totalAmt += outstandingDetail.getWcNFBInterestOutstandingAmount();
		}
		addComponent(3, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtWCNFBPrincipal[i], gridBag, constraints, outstandingPanel);

		txtWCNFBIntComm[i] = new JTextField(10);
		txtWCNFBIntComm[i].setDocument(new DecimalFormatField(2, 2));
		if ((flag.equals("MOD")) || (flag.equals("VER")))
		{
			txtWCNFBIntComm[i].setText(""+outstandingDetail.getWcNFBPrincipalOutstandingAmount());
			totalAmt += outstandingDetail.getWcNFBPrincipalOutstandingAmount();
		}
		addComponent(4, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtWCNFBIntComm[i], gridBag, constraints, outstandingPanel);

		txtWCNFBAsOnDate[i] = new JTextField(10);
		setDateFieldProperties(txtWCNFBAsOnDate[i], "dd/MM/yyyy", true);
		if ((flag.equals("MOD")) || (flag.equals("VER")))
		{
			txtWCNFBAsOnDate[i].setText( dateFormat.format(outstandingDetail.getWcNFBOutstandingAsOnDate()));
		}
		addComponent(5, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtWCNFBAsOnDate[i], gridBag, constraints, outstandingPanel);
*/
		return totalAmt;
	}

	/**
	 * This method displays the disbursement details of the periodic info
	 */
	 private void displayDisbursementDetails(ArrayList disbursementDetails, String flag, ArrayList counts)
	 {
		 disbursementPanel.removeAll();
		 disbursementPanel.setBackground(dataBg);
		 disbursementPanel.setLayout(gridBag);

		 SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
		 Date utilDate;
		 String sCgpan="";
		 double totalAmt = 0;
		 String text = "";


		 int disbursementSize=disbursementDetails.size();
		 int disbursementAmtsSize=0;
		 int i=0;
		 int j=0;
		 int rowNo=0;
		 disCgpanValue=new JLabel[disbursementSize];
		 schemeValue=new JLabel[disbursementSize];
		 slNoValue=new JLabel[disbursementSize];
		 sanctionedAmtValue=new JLabel[disbursementSize];
		 noOfDisbursements=new int[disbursementSize];
		 totalDisbursementAmtValue=new JLabel[disbursementSize];
		 int noOfDisbursementsMade=0;
		 int noOfDisbursementsPending=0;
		 int input = 0;
		 String message;
		 String response;

		 if (disbursementSize != 0)
		 {
			 disbursementHeading = new JLabel("Disbursement Details");
			 disbursementHeading.setFont(headingFont);
			 disbursementHeading.setOpaque(true);
			 disbursementHeading.setBackground(headingBg);
			 addComponent(0, 0, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, disbursementHeading, gridBag, constraints, disbursementPanel);
		 }

		 com.cgtsi.guaranteemaintenance.Disbursement disbursement;
		 com.cgtsi.guaranteemaintenance.DisbursementAmount disbursementAmount;
		 ArrayList disbursementAmts;

		/**
		 * This for loop calculates the no of disbursements already made (sum of all the disbursements
		 * made for cgpan).
		 * It also gets the input from the user of the no of repayments that the user wants to enter.
		 */
		for (i=0;i<disbursementSize;i++)
		{
			disbursement=(com.cgtsi.guaranteemaintenance.Disbursement) disbursementDetails.get(i);
			disbursementAmts= disbursement.getDisbursementAmounts();
			disbursementAmtsSize=0;
			if (disbursementAmts!=null)
			{
				disbursementAmtsSize=disbursementAmts.size();
			}
			noOfDisbursementsMade += disbursementAmtsSize;		// no of disbursements already made (including all the cgpans).
			noOfDisbursements[i] = disbursementAmtsSize;

			sCgpan = disbursement.getCgpan();
			if (!(flag.equalsIgnoreCase("VER")))
			{
				noOfDisbursements[i] += Integer.parseInt((String) counts.get(i));
				noOfDisbursementsPending += Integer.parseInt((String) counts.get(i));
			}
		}
		
/*		for (int z=0;z<disbursementSize;z++)
		{
			System.out.println("no of dis for z " + z + " " + noOfDisbursements[z]);
		}*/

		int totalDis = noOfDisbursementsMade + noOfDisbursementsPending;
		disIdValue=new JLabel[totalDis];
		txtDisbursementAmt=new JTextField[totalDis];
		txtDisbursementDate=new JTextField[totalDis];
		chkFinalDisbursement=new JCheckBox[totalDis];
		
//		System.out.println("total dis " + totalDis);

		int iDisbursements = 0;
//		int iDisbursementsPending = 0;
		int tempIndex=0;

		for (i=0;i<disbursementSize;i++)
		{
//			if (tempIndex!=0)
//			{
				rowNo++;
				disbursement=(com.cgtsi.guaranteemaintenance.Disbursement) disbursementDetails.get(i);
				sCgpan=disbursement.getCgpan();
				
//				System.out.println("cgpan * " + sCgpan + " i * " + i);
				pcgpan=new JLabel("CGPAN");
				pcgpan.setFont(headingFont);
				pcgpan.setOpaque(true);
				pcgpan.setBackground(dataBg);
//				addComponent(0, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, pcgpan, gridBag, constraints, disbursementPanel);

				disCgpanValue[i] = new JLabel("     "+sCgpan);
				disCgpanValue[i].setFont(dataFont);
				disCgpanValue[i].setOpaque(true);
				disCgpanValue[i].setBackground(dataBg);
//				addComponent(1, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, disCgpanValue[i], gridBag, constraints, disbursementPanel);

				scheme=new JLabel("  Scheme");
				scheme.setFont(headingFont);
				scheme.setOpaque(true);
				scheme.setBackground(dataBg);
//				addComponent(2, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, scheme, gridBag, constraints, disbursementPanel);

				schemeValue[i] = new JLabel("     "+disbursement.getScheme());
				schemeValue[i].setFont(dataFont);
				schemeValue[i].setOpaque(true);
				schemeValue[i].setBackground(dataBg);
//				addComponent(3, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, schemeValue[i], gridBag, constraints, disbursementPanel);
				
			grpPanel = new JPanel();
			grpPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
			grpPanel.setBackground(dataBg);	
			grpPanel.add(pcgpan);	
			grpPanel.add(disCgpanValue[i]);
			grpPanel.add(scheme);	
			grpPanel.add(schemeValue[i]);
			addComponent(0, rowNo, 5, GridBagConstraints.WEST, GridBagConstraints.BOTH, grpPanel, gridBag, constraints, disbursementPanel);

				rowNo++;

				slNo=new JLabel("Sl No");
				slNo.setFont(headingFont);
				slNo.setOpaque(true);
				slNo.setBackground(headingBg);
				addComponent(0, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, slNo, gridBag, constraints, disbursementPanel);

				sanctionedAmt = new JLabel("Sanctioned Amount", SwingConstants.CENTER);
				sanctionedAmt.setFont(headingFont);
				sanctionedAmt.setOpaque(true);
				sanctionedAmt.setBackground(headingBg);
				addComponent(1, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, sanctionedAmt, gridBag, constraints, disbursementPanel);

				disbursementAmt=new JLabel("Disbursement Amount (in Rs.)");
				disbursementAmt.setFont(headingFont);
				disbursementAmt.setOpaque(true);
				disbursementAmt.setBackground(headingBg);
				addComponent(2, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, disbursementAmt, gridBag, constraints, disbursementPanel);

				date=new JLabel("Disbursement Date");
				date.setFont(headingFont);
				date.setOpaque(true);
				date.setBackground(headingBg);
				addComponent(3, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, date, gridBag, constraints, disbursementPanel);

				finalDisbursement = new JLabel("Final Disbursement", SwingConstants.CENTER);
				finalDisbursement.setFont(headingFont);
				finalDisbursement.setOpaque(true);
				finalDisbursement.setBackground(headingBg);
				addComponent(4, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, finalDisbursement, gridBag, constraints, disbursementPanel);

				totalDisbursementAmt=new JLabel("Total Disbursement Amount (in Rs.)");
				totalDisbursementAmt.setFont(headingFont);
				totalDisbursementAmt.setOpaque(true);
				totalDisbursementAmt.setBackground(headingBg);
				addComponent(5, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, totalDisbursementAmt, gridBag, constraints, disbursementPanel);

				rowNo++;

				slNoValue[i] = new JLabel(""+(i+1));
				slNoValue[i].setFont(headingFont);
				slNoValue[i].setOpaque(true);
				slNoValue[i].setBackground(dataBg);
				addComponent(0, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, slNoValue[i], gridBag, constraints, disbursementPanel);

				sanctionedAmtValue[i] = new JLabel(""+disbursement.getSanctionedAmount());
				sanctionedAmtValue[i].setFont(headingFont);
				sanctionedAmtValue[i].setOpaque(true);
				sanctionedAmtValue[i].setBackground(dataBg);
				addComponent(1, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, sanctionedAmtValue[i], gridBag, constraints, disbursementPanel);

				disbursementAmts=disbursement.getDisbursementAmounts();
				disbursementAmtsSize=0;
				if (disbursementAmts!=null)
				{
					disbursementAmtsSize=disbursementAmts.size();
				}
				
//				System.out.println("i size * + " + i + " * " + disbursementAmtsSize);
				
				String chkCommand="";

				for (j=0;j<disbursementAmtsSize;j++)
				{
//					System.out.println("idis * " + iDisbursements);
					disbursementAmount = (com.cgtsi.guaranteemaintenance.DisbursementAmount) disbursementAmts.get(j);

					disIdValue[iDisbursements] = new JLabel("");
					if (disbursementAmount.getDisbursementId() != null)
					{
						disIdValue[iDisbursements].setText(disbursementAmount.getDisbursementId());
//						System.out.println("dis index , value -- " + iDisbursements + ", " + disbursementAmount.getDisbursementId());
					}
					disIdValue[iDisbursements].setVisible(false);
					addComponent(6, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, disIdValue[iDisbursements], gridBag, constraints, disbursementPanel);
					
					txtDisbursementAmt[iDisbursements]=new JTextField(10);
					txtDisbursementAmt[iDisbursements].setDocument(new DecimalFormatField(13,2));
					txtDisbursementAmt[iDisbursements].addFocusListener(new PeriodicDisbursementTotal());
					text = df.format(disbursementAmount.getDisbursementAmount());
					txtDisbursementAmt[iDisbursements].setText(text);
					totalAmt += disbursementAmount.getDisbursementAmount();
					addComponent(2, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtDisbursementAmt[iDisbursements], gridBag, constraints, disbursementPanel);

					txtDisbursementDate[iDisbursements] = new JTextField(10);
					setDateFieldProperties(txtDisbursementDate[iDisbursements], "dd/MM/yyyy", false);
					txtDisbursementDate[iDisbursements].setText( dateFormat.format(disbursementAmount.getDisbursementDate()));
					addComponent(3, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtDisbursementDate[iDisbursements], gridBag, constraints, disbursementPanel);
					
					chkFinalDisbursement[iDisbursements] = new JCheckBox();
					if (disbursementAmount.getFinalDisbursement() != null)
					{
						if (disbursementAmount.getFinalDisbursement().equalsIgnoreCase("Y"))
						{
//							noOfDisbursements[i]=iDisbursements+1;
							chkFinalDisbursement[iDisbursements].setSelected(true);
							chkCommand = iDisbursements + "-" + (noOfDisbursements[i] - j);
						}
						else if (disbursementAmount.getFinalDisbursement().equalsIgnoreCase("N"))
						{
							chkFinalDisbursement[iDisbursements].setSelected(false);
						}
					}
					chkFinalDisbursement[iDisbursements].setBackground(dataBg);
					chkFinalDisbursement[iDisbursements].setActionCommand(iDisbursements + "-" + (noOfDisbursements[i] - j));
					chkFinalDisbursement[iDisbursements].addItemListener(this);
					addComponent(4, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, chkFinalDisbursement[iDisbursements], gridBag, constraints, disbursementPanel);

					iDisbursements++;
					rowNo++;
				}
//				System.out.println("no of dis for i * " + noOfDisbursements[i] + " " + i);
				tempIndex = noOfDisbursements[i] - disbursementAmtsSize;

				for (j=0;j<tempIndex;j++)
				{
//					System.out.println("idis " + iDisbursements);
					disIdValue[iDisbursements] = new JLabel("");
					disIdValue[iDisbursements].setVisible(false);
					addComponent(6, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, disIdValue[iDisbursements], gridBag, constraints, disbursementPanel);

					txtDisbursementAmt[iDisbursements]= new JTextField(10);
					txtDisbursementAmt[iDisbursements].setDocument(new DecimalFormatField(13,2));
					txtDisbursementAmt[iDisbursements].addFocusListener(new PeriodicDisbursementTotal());
					addComponent(2, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtDisbursementAmt[iDisbursements], gridBag, constraints, disbursementPanel);
					
					txtDisbursementDate[iDisbursements]= new JTextField(10);
					setDateFieldProperties(txtDisbursementDate[iDisbursements], "dd/MM/yyyy", false);
					addComponent(3, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtDisbursementDate[iDisbursements], gridBag, constraints, disbursementPanel);
					
					chkFinalDisbursement[iDisbursements] = new JCheckBox();
					chkFinalDisbursement[iDisbursements].setBackground(dataBg);
					chkFinalDisbursement[iDisbursements].setActionCommand(iDisbursements + "-" + (tempIndex - j));
					chkFinalDisbursement[iDisbursements].addItemListener(this);					
					addComponent(4, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, chkFinalDisbursement[iDisbursements], gridBag, constraints, disbursementPanel);
					
					rowNo++;
					iDisbursements++;
				}
				if (!chkCommand.equals(""))
				{
					checkFinalDis(chkCommand);
				}
//			}
			totalDisbursementAmtValue[i]=new JLabel(df.format(totalAmt));
			totalDisbursementAmtValue[i].setFont(headingFont);
			addComponent(5, rowNo-1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, totalDisbursementAmtValue[i], gridBag, constraints, disbursementPanel);
		}
	}

	/**
	 * This method displays the repayment details in the periodic info details.
	 */
	 private void displayRepaymentDetails(ArrayList repaymentDetails, String flag, ArrayList counts)
	 {
		repaymentPanel.removeAll();
		repaymentPanel.setBackground(dataBg);
		repaymentPanel.setLayout(gridBag);

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		double totalAmt = 0;

		String sCgpan;
		int repaymentSize=repaymentDetails.size();
		int i=0;
		int j=0;
		int tempSize=0;
		repayCgpanValue=new JLabel[repaymentSize];
		schemeValue=new JLabel[repaymentSize];
		slNoValue=new JLabel[repaymentSize];
		noOfRepayments=new int[repaymentSize];
		totalRepaymentAmtValue=new JLabel[repaymentSize];

		com.cgtsi.guaranteemaintenance.Repayment repayment;
		ArrayList repaymentAmts;
		int repaymentAmtsSize=0;
		com.cgtsi.guaranteemaintenance.RepaymentAmount repaymentAmount;
		int rowNo=0;
		int noOfRepaymentsPending=0;
		int noOfRepaymentsMade=0;
		int input = 0;
		String message;
		String response;
		String text ="";

		if (repaymentSize != 0)
		{
			repaymentHeading=new JLabel("Repayment Details");
			repaymentHeading.setFont(headingFont);
			repaymentHeading.setOpaque(true);
			repaymentHeading.setBackground(headingBg);
			addComponent(0, 0, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, repaymentHeading, gridBag, constraints, repaymentPanel);
		}

		/**
		 * This for loop calculates the no of repayments already made (sum of all the repayments made
		 * for cgpan).
		 * It also gets the input from the user of the no of repayments that the user wants to enter.
		 */
		for (i=0;i<repaymentSize;i++)
		{
			repayment=(com.cgtsi.guaranteemaintenance.Repayment) repaymentDetails.get(i);
			repaymentAmts= repayment.getRepaymentAmounts();
			repaymentAmtsSize=0;
			if (repaymentAmts!=null)
			{
				repaymentAmtsSize=repaymentAmts.size();
			}
			noOfRepaymentsMade += repaymentAmtsSize;		// no of repayments already made (including all the cgpans).
			noOfRepayments[i] = repaymentAmtsSize;

			sCgpan = repayment.getCgpan();
			if (!(flag.equalsIgnoreCase("VER")))
			{
				noOfRepayments[i] += Integer.parseInt((String) counts.get(i));
				noOfRepaymentsPending += Integer.parseInt((String) counts.get(i));
			}
		}

		int totalRepay = noOfRepaymentsPending + noOfRepaymentsMade;
		repayIdValue=new JLabel[totalRepay];
		txtRepaymentAmt=new JTextField[totalRepay];
		txtRepaymentDate=new JTextField[totalRepay];

		int iRepayments = 0;
		int tempIndex=0;

		for (i=0;i<repaymentSize;i++)
		{
//			if (tempIndex!=0)
//			{
				rowNo++;
				repayment=(com.cgtsi.guaranteemaintenance.Repayment) repaymentDetails.get(i);
				sCgpan=repayment.getCgpan();
				pcgpan=new JLabel("CGPAN");
				pcgpan.setFont(headingFont);
				pcgpan.setOpaque(true);
				pcgpan.setBackground(dataBg);
//				addComponent(0, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, pcgpan, gridBag, constraints, repaymentPanel);

				repayCgpanValue[i] = new JLabel("     "+sCgpan);
				repayCgpanValue[i].setFont(dataFont);
				repayCgpanValue[i].setOpaque(true);
				repayCgpanValue[i].setBackground(dataBg);
//				addComponent(1, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, repayCgpanValue[i], gridBag, constraints, repaymentPanel);

				scheme=new JLabel("  Scheme");
				scheme.setFont(headingFont);
				scheme.setOpaque(true);
				scheme.setBackground(dataBg);
//				addComponent(2, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, scheme, gridBag, constraints, repaymentPanel);

				schemeValue[i] = new JLabel("     "+repayment.getScheme());
				schemeValue[i].setFont(dataFont);
				schemeValue[i].setOpaque(true);
				schemeValue[i].setBackground(dataBg);
//				addComponent(3, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, schemeValue[i], gridBag, constraints, repaymentPanel);
				
			grpPanel = new JPanel();
			grpPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
			grpPanel.setBackground(dataBg);	
			grpPanel.add(pcgpan);	
			grpPanel.add(repayCgpanValue[i]);
			grpPanel.add(scheme);	
			grpPanel.add(schemeValue[i]);
			addComponent(0, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, grpPanel, gridBag, constraints, repaymentPanel);

				rowNo++;

				slNo=new JLabel("Sl No");
				slNo.setFont(headingFont);
				slNo.setOpaque(true);
				slNo.setBackground(headingBg);
				addComponent(0, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, slNo, gridBag, constraints, repaymentPanel);

				repaymentAmt=new JLabel("Repayment Amount (in Rs.)");
				repaymentAmt.setFont(headingFont);
				repaymentAmt.setOpaque(true);
				repaymentAmt.setBackground(headingBg);
				addComponent(1, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, repaymentAmt, gridBag, constraints, repaymentPanel);

				date=new JLabel("Repayment Date");
				date.setFont(headingFont);
				date.setOpaque(true);
				date.setBackground(headingBg);
				addComponent(2, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, date, gridBag, constraints, repaymentPanel);

				totalRepaymentAmt=new JLabel("Total Repayment Amount (in Rs.)");
				totalRepaymentAmt.setFont(headingFont);
				totalRepaymentAmt.setOpaque(true);
				totalRepaymentAmt.setBackground(headingBg);
				addComponent(3, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, totalRepaymentAmt, gridBag, constraints, repaymentPanel);

				rowNo++;

				slNoValue[i] = new JLabel(""+(i+1));
				slNoValue[i].setFont(headingFont);
				slNoValue[i].setOpaque(true);
				slNoValue[i].setBackground(dataBg);
				addComponent(0, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, slNoValue[i], gridBag, constraints, repaymentPanel);

				repaymentAmts=repayment.getRepaymentAmounts();
			repaymentAmtsSize=0;
				if (repaymentAmts!=null)
				{
					repaymentAmtsSize=repaymentAmts.size();
				}

				for (j=0;j<repaymentAmtsSize;j++)
				{
					repaymentAmount = (com.cgtsi.guaranteemaintenance.RepaymentAmount) repaymentAmts.get(j);

					repayIdValue[iRepayments] = new JLabel("");
					if (repaymentAmount.getRepayId() != null)
					{
						repayIdValue[iRepayments].setText(repaymentAmount.getRepayId());
//						System.out.println("repay index , value -- " + iRepayments + ", " + repaymentAmount.getRepayId());
					}
					repayIdValue[iRepayments].setVisible(false);
					addComponent(4, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, repayIdValue[iRepayments], gridBag, constraints, repaymentPanel);

					txtRepaymentAmt[iRepayments]=new JTextField(10);
					txtRepaymentAmt[iRepayments].setDocument(new DecimalFormatField(13,2));
					txtRepaymentAmt[iRepayments].addFocusListener(new PeriodicRepaymentTotal());
					totalAmt += repaymentAmount.getRepaymentAmount();
					text = df.format(repaymentAmount.getRepaymentAmount());
					txtRepaymentAmt[iRepayments].setText(text);
					addComponent(1, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtRepaymentAmt[iRepayments], gridBag, constraints, repaymentPanel);

					txtRepaymentDate[iRepayments]=new JTextField(10);
					setDateFieldProperties(txtRepaymentDate[iRepayments], "dd/MM/yyyy", false);
					txtRepaymentDate[iRepayments].setText( dateFormat.format(repaymentAmount.getRepaymentDate()));
					addComponent(2, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtRepaymentDate[iRepayments], gridBag, constraints, repaymentPanel);
					iRepayments++;
					rowNo++;
				}
				tempIndex = noOfRepayments[i] - repaymentAmtsSize;
				for (j=0;j<tempIndex;j++)
				{
					repayIdValue[iRepayments] = new JLabel("");
					repayIdValue[iRepayments].setVisible(false);
					addComponent(4, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, repayIdValue[iRepayments], gridBag, constraints, repaymentPanel);

					txtRepaymentAmt[iRepayments]= new JTextField(10);
					txtRepaymentAmt[iRepayments].setDocument(new DecimalFormatField(13,2));
					txtRepaymentAmt[iRepayments].addFocusListener(new PeriodicRepaymentTotal());
					addComponent(1, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtRepaymentAmt[iRepayments], gridBag, constraints, repaymentPanel);

					txtRepaymentDate[iRepayments]= new JTextField(10);
					setDateFieldProperties(txtRepaymentDate[iRepayments], "dd/MM/yyyy", false);
					addComponent(2, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtRepaymentDate[iRepayments], gridBag, constraints, repaymentPanel);
					rowNo++;
					iRepayments++;
				}
//			}
			totalRepaymentAmtValue[i]=new JLabel(df.format(totalAmt));
			totalRepaymentAmtValue[i].setFont(headingFont);
			addComponent(3, rowNo-1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, totalRepaymentAmtValue[i], gridBag, constraints, repaymentPanel);
		}
	 }

	 /**
	  * This method displays the npa details for entry.
	  */
	  private void displayNPADetails(String borrowerId, com.cgtsi.guaranteemaintenance.NPADetails npaDetails, String flag)
	  {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String text = "";

		npaDetailsPanel.removeAll();
		npaDetailsPanel.setBackground(dataBg);
		npaDetailsPanel.setLayout(gridBag);

		npaHeading=new JLabel("NPA Details");
		npaHeading.setFont(headingFont);
		npaHeading.setOpaque(true);
		npaHeading.setBackground(headingBg);
		addComponent(0, 0, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, npaHeading, gridBag, constraints, npaDetailsPanel);

		npaDate=new JLabel("Date On Which Account Turned NPA");
		npaDate.setFont(headingFont);
		npaDate.setOpaque(true);
		npaDate.setBackground(dataBg);
		addComponent(0, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, npaDate, gridBag, constraints, npaDetailsPanel);

		txtNpaDate=new JTextField(10);
		setDateFieldProperties(txtNpaDate, "dd/MM/yyyy", false);
		if (npaDetails.getNpaDate() != null)
		{
			txtNpaDate.setText(dateFormat.format(npaDetails.getNpaDate()));
		}
		addComponent(1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtNpaDate, gridBag, constraints, npaDetailsPanel);

		osAmtAsOnDate=new JLabel("Outstanding Amount as on NPA Date");
		osAmtAsOnDate.setFont(headingFont);
		osAmtAsOnDate.setOpaque(true);
		osAmtAsOnDate.setBackground(dataBg);
		addComponent(0, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, osAmtAsOnDate, gridBag, constraints, npaDetailsPanel);

		txtOsAmtAsOnNpaDate=new JTextField(10);
		txtOsAmtAsOnNpaDate.setDocument(new DecimalFormatField(13,2));
		text = df.format(npaDetails.getOsAmtOnNPA());
		txtOsAmtAsOnNpaDate.setText(text);
//		addComponent(1, 2, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtOsAmtAsOnNpaDate, gridBag, constraints, npaDetailsPanel);
		
		inRs = new JLabel("in Rs.");
		inRs.setFont(hintFont);
		inRs.setOpaque(true);
		inRs.setBackground(dataBg);
		inRs.setHorizontalTextPosition(SwingConstants.LEFT);		
//		addComponent(2, 2, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, inRs, gridBag, constraints, npaDetailsPanel);
		
		grpPanel = new JPanel();
		grpPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		grpPanel.setBackground(dataBg);
		grpPanel.add(txtOsAmtAsOnNpaDate);
		grpPanel.add(inRs);
		addComponent(1, 2, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, grpPanel, gridBag, constraints, npaDetailsPanel);

		npaReported=new JLabel("Whether NPA was reported to CGTSI");
		npaReported.setFont(headingFont);
		npaReported.setOpaque(true);
		npaReported.setBackground(dataBg);
		addComponent(0, 3, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, npaReported, gridBag, constraints, npaDetailsPanel);

		npaReportedYes = new JRadioButton("Yes");
		npaReportedNo = new JRadioButton("No");
		npaReportedYes.setBackground(dataBg);
		npaReportedNo.setBackground(dataBg);
		npaReportedValue = new ButtonGroup();
		npaReportedValue.add(npaReportedYes);
		npaReportedValue.add(npaReportedNo);
		npaReportedPanel = new JPanel();
		npaReportedPanel.add(npaReportedYes);
		npaReportedPanel.add(npaReportedNo);
		npaReportedPanel.setBackground(dataBg);
		String reported = npaDetails.getWhetherNPAReported();
		if (reported!=null)
		{
			if (reported.equalsIgnoreCase("Y"))
			{
				npaReportedYes.setSelected(true);
				npaReportedNo.setSelected(false);
			}
			else if (reported.equalsIgnoreCase("N"))
			{
				npaReportedYes.setSelected(false);
				npaReportedNo.setSelected(true);
			}
		}
		addComponent(1, 3, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, npaReportedPanel, gridBag, constraints, npaDetailsPanel);

		reportingDate=new JLabel("Date of Reporting");
		reportingDate.setFont(headingFont);
		reportingDate.setOpaque(true);
		reportingDate.setBackground(dataBg);
		addComponent(0, 4, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, reportingDate, gridBag, constraints, npaDetailsPanel);

		txtReportingDate=new JTextField(10);
		setDateFieldProperties(txtReportingDate, "dd/MM/yyyy", false);
		if (npaDetails.getReportingDate() != null)
		{
			txtReportingDate.setText(dateFormat.format(npaDetails.getReportingDate()));
		}
		addComponent(1, 4, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtReportingDate, gridBag, constraints, npaDetailsPanel);

		reasons=new JLabel("Reasons for Account Turning NPA");
		reasons.setFont(headingFont);
		reasons.setOpaque(true);
		reasons.setBackground(dataBg);
		addComponent(0, 5, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, reasons, gridBag, constraints, npaDetailsPanel);

		txtReasons=new JTextArea(2, 20);
		txtReasons.setLineWrap(true);
		JScrollPane txtReasonsPane=new JScrollPane(txtReasons, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		if (npaDetails.getNpaReason()!=null)
		{
			txtReasons.setText(npaDetails.getNpaReason());
		}
		addComponent(1, 5, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtReasonsPane, gridBag, constraints, npaDetailsPanel);

		willfulDefaulter=new JLabel("Wilful Defaulter");
		willfulDefaulter.setFont(headingFont);
		willfulDefaulter.setOpaque(true);
		willfulDefaulter.setBackground(dataBg);
		addComponent(0, 6, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, willfulDefaulter, gridBag, constraints, npaDetailsPanel);

		willfulDefaulterYes = new JRadioButton("Yes");
		willfulDefaulterNo = new JRadioButton("No");
		willfulDefaulterYes.setBackground(dataBg);
		willfulDefaulterNo.setBackground(dataBg);
		willfulDefaulterValue = new ButtonGroup();
		willfulDefaulterValue.add(willfulDefaulterYes);
		willfulDefaulterValue.add(willfulDefaulterNo);
		willfulDefaulterPanel = new JPanel();
		willfulDefaulterPanel.add(willfulDefaulterYes);
		willfulDefaulterPanel.add(willfulDefaulterNo);
		willfulDefaulterPanel.setBackground(dataBg);
		String willfull = npaDetails.getWillfulDefaulter();
		if (willfull!=null)
		{
			if (willfull.equalsIgnoreCase("Y"))
			{
				willfulDefaulterYes.setSelected(true);
				willfulDefaulterNo.setSelected(false);
			}
			else if (willfull.equalsIgnoreCase("N"))
			{
				willfulDefaulterYes.setSelected(false);
				willfulDefaulterNo.setSelected(true);
			}
		}
		addComponent(1, 6, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, willfulDefaulterPanel, gridBag, constraints, npaDetailsPanel);

		efforts=new JLabel("Ennumerate efforts taken by MLI to prevent account turning NPA and minimising Credit Risk");
		efforts.setFont(headingFont);
		efforts.setOpaque(true);
		efforts.setBackground(dataBg);
		addComponent(0, 7, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, efforts, gridBag, constraints, npaDetailsPanel);

		txtEfforts=new JTextArea(2, 20);
		txtEfforts.setLineWrap(true);
		JScrollPane txtEffortsPane=new JScrollPane(txtEfforts, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		if (npaDetails.getEffortsTaken()!=null)
		{
			txtEfforts.setText(npaDetails.getEffortsTaken());
		}
		addComponent(1, 7, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtEffortsPane, gridBag, constraints, npaDetailsPanel);

		recoveryProcedure=new JLabel("Recovery Procedure");
		recoveryProcedure.setFont(headingFont);
		recoveryProcedure.setOpaque(true);
		recoveryProcedure.setBackground(headingBg);
		addComponent(0, 8, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, recoveryProcedure, gridBag, constraints, npaDetailsPanel);

		procedureInitiated=new JLabel("Recovery Procedure Initiated");
		procedureInitiated.setFont(headingFont);
		procedureInitiated.setOpaque(true);
		procedureInitiated.setBackground(dataBg);
		addComponent(0, 9, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, procedureInitiated, gridBag, constraints, npaDetailsPanel);

		procInitiatedYes = new JRadioButton("Yes");
		procInitiatedNo = new JRadioButton("No");
		procInitiatedYes.setBackground(dataBg);
		procInitiatedYes.addItemListener(new RecoveryProcInitiatedListener());
		procInitiatedNo.setBackground(dataBg);
		procInitiatedNo.addItemListener(new RecoveryProcInitiatedListener());
		procInitiatedValue = new ButtonGroup();
		procInitiatedValue.add(procInitiatedYes);
		procInitiatedValue.add(procInitiatedNo);
		procInitiatedPanel = new JPanel();
		procInitiatedPanel.add(procInitiatedYes);
		procInitiatedPanel.add(procInitiatedNo);
		procInitiatedPanel.setBackground(dataBg);
		String initiated = npaDetails.getIsRecoveryInitiated();
		if (initiated!=null)
		{
			if (initiated.equalsIgnoreCase("Y"))
			{
				procInitiatedYes.setSelected(true);
				procInitiatedNo.setSelected(false);
				procInitiatedNo.setEnabled(false);
			}
			else if (initiated.equalsIgnoreCase("N"))
			{
				procInitiatedYes.setSelected(false);
				procInitiatedNo.setSelected(true);
			}
		}
		addComponent(1, 9, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, procInitiatedPanel, gridBag, constraints, npaDetailsPanel);

		npaRecoveryPanel = new JPanel();
		npaRecoveryPanel.setBackground(dataBg);
		npaRecoveryPanel.setLayout(gridBag);

		ArrayList recProcs = npaDetails.getRecoveryProcedure();
		int size=0;
		noOfActions = 0;
		if (recProcs!=null)
		{
			size = recProcs.size();
			noOfActions = size;
		}
//		System.out.println("no of recovery actions : " + noOfActions);

		if (!(flag.equalsIgnoreCase("VER")))
		{
			String response =txtRecActionsInput.getText().trim();
			if (!response.equalsIgnoreCase(""))
			{
				noOfActions += Integer.parseInt(response);
			}
		}

		int i=0;
		int rowNo=1;
		int number=noOfActions;
//		System.out.println("no of recovery actions total : " + number);

		String[] actionTypes= {"Select", "OTS", "Recall of Credit facility", "Invocation of Personal Guarantee", "Filing of Suit in Civil Court or DRT", "Registration of Case with Lok adalat, revenue recovery authority, etc", "Actions under Securitization Act", "Others"};

		slNoValue = new JLabel[number];
		radIdValue = new JLabel[number];
		cmbActionType = new JComboBox[number];
		txtDetails = new JTextArea[number];
		JScrollPane txtDetailsPane[] = new JScrollPane[number];
		txtDate = new JTextField[number];
		txtAttachment = new JTextField[number];
		attachBrowse = new JButton[number];
		com.cgtsi.guaranteemaintenance.RecoveryProcedure recoveryProc = new com.cgtsi.guaranteemaintenance.RecoveryProcedure();

		if (number > 0)
		{
			slNo=new JLabel("Sl. No");
			slNo.setFont(headingFont);
			slNo.setOpaque(true);
			slNo.setBackground(headingBg);
			addComponent(0, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, slNo, gridBag, constraints, npaRecoveryPanel);

			actionType=new JLabel("Action Type");
			actionType.setFont(headingFont);
			actionType.setOpaque(true);
			actionType.setBackground(headingBg);
			addComponent(1, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, actionType, gridBag, constraints, npaRecoveryPanel);

			details=new JLabel("Details");
			details.setFont(headingFont);
			details.setOpaque(true);
			details.setBackground(headingBg);
			addComponent(2, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, details, gridBag, constraints, npaRecoveryPanel);

			date=new JLabel("Date");
			date.setFont(headingFont);
			date.setOpaque(true);
			date.setBackground(headingBg);
			addComponent(3, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, date, gridBag, constraints, npaRecoveryPanel);

			attachement=new JLabel("Attachement");
			attachement.setFont(headingFont);
			attachement.setOpaque(true);
			attachement.setBackground(headingBg);
			addComponent(4, 0, 2, GridBagConstraints.WEST, GridBagConstraints.BOTH, attachement, gridBag, constraints, npaRecoveryPanel);
		}

		for (i=0;i<number;i++)
		{
			if (i<size)
			{
				recoveryProc = (com.cgtsi.guaranteemaintenance.RecoveryProcedure) recProcs.get(i);
			}

			rowNo++;

			radIdValue[i] = new JLabel("");
			if (recoveryProc.getRadId() != null)
			{
				radIdValue[i].setText(recoveryProc.getRadId());
			}
			radIdValue[i].setFont(dataFont);
			radIdValue[i].setOpaque(true);
			radIdValue[i].setVisible(false);
			radIdValue[i].setBackground(dataBg);
			addComponent(0, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, radIdValue[i], gridBag, constraints, npaRecoveryPanel);

			rowNo++;

			slNoValue[i] = new JLabel(""+(i+1));
			slNoValue[i].setFont(dataFont);
			slNoValue[i].setOpaque(true);
			slNoValue[i].setBackground(dataBg);
			addComponent(0, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, slNoValue[i], gridBag, constraints, npaRecoveryPanel);

			cmbActionType[i] = new JComboBox(actionTypes);
			if (i<size)
			{
				cmbActionType[i].setSelectedItem(recoveryProc.getActionType());
			}
			addComponent(1, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, cmbActionType[i], gridBag, constraints, npaRecoveryPanel);

			txtDetails[i] = new JTextArea(2, 20);
			txtDetails[i].setLineWrap(true);
			txtDetailsPane[i] = new JScrollPane(txtDetails[i], JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			if (i<size)
			{
				if (recoveryProc.getActionDetails()!=null)
				{
					txtDetails[i].setText(recoveryProc.getActionDetails());
				}
			}
			addComponent(2, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtDetailsPane[i], gridBag, constraints, npaRecoveryPanel);

			txtDate[i] = new JTextField(10);
			setDateFieldProperties(txtDate[i], "dd/MM/yyyy", false);
			if (i<size)
			{
				if (recoveryProc.getActionDate() != null)
				{
					txtDate[i].setText(dateFormat.format(recoveryProc.getActionDate()));
				}
			}
			addComponent(3, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtDate[i], gridBag, constraints, npaRecoveryPanel);

			txtAttachment[i] = new JTextField(10);
			if (i<size)
			{
				if (recoveryProc.getAttachmentName()!=null)
				{
				txtAttachment[i].setText(recoveryProc.getAttachmentName());
				}
			}
			addComponent(4, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtAttachment[i], gridBag, constraints, npaRecoveryPanel);

			attachBrowse[i] = new JButton("Browse");
			attachBrowse[i].setActionCommand("BrowseAttachement-"+i);
			attachBrowse[i].addActionListener(this);
			addComponent(5, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, attachBrowse[i], gridBag, constraints, npaRecoveryPanel);
		}

		rowNo=10;
		addComponent(0, rowNo, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, npaRecoveryPanel, gridBag, constraints, npaDetailsPanel);

		rowNo++;

/*		legalSuitDetails=new JLabel("Details of Legal Suit");
		legalSuitDetails.setFont(headingFont);
		legalSuitDetails.setOpaque(true);
		legalSuitDetails.setBackground(headingBg);
		addComponent(0, rowNo, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, legalSuitDetails, gridBag, constraints, npaDetailsPanel);*/

		rowNo=0;

		npaLegalSuitPanel=new JPanel();
		npaLegalSuitPanel.setBackground(dataBg);
		npaLegalSuitPanel.setLayout(gridBag);

		com.cgtsi.guaranteemaintenance.LegalSuitDetail legalSuitDetail = new com.cgtsi.guaranteemaintenance.LegalSuitDetail();
		
//		System.out.println("legal null ??? " + npaDetails.getLegalSuitDetail().getAmountClaimed());
//		if ((flag.equals("MOD")) || (flag.equals("VER")))
//		{
			if(npaDetails.getLegalSuitDetail()!=null)
			{
				legalSuitDetail = npaDetails.getLegalSuitDetail();
			}
//		}

		legalProceedingsDetails=new JLabel("Details of Legal Proceedings:");
		legalProceedingsDetails.setFont(headingFont);
		legalProceedingsDetails.setOpaque(true);
		legalProceedingsDetails.setBackground(headingBg);
		addComponent(0, rowNo, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, legalProceedingsDetails, gridBag, constraints, npaLegalSuitPanel);

		rowNo++;

		forum=new JLabel("Forum through which Legal Proceedings were initiated against borrower");
		forum.setFont(headingFont);
		forum.setOpaque(true);
		forum.setBackground(dataBg);
		addComponent(0, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, forum, gridBag, constraints, npaLegalSuitPanel);

		String[] forumNames = {"Select", "Civil Court", "DRT", "Lok Adalat", "Revenue Recovery Authority", "Securitisation Act, 2002", "Others"};
		cmbForumNames = new JComboBox(forumNames);
		cmbForumNames.addItemListener(new ForumNamesListener());
//		addComponent(1, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, cmbForumNames, gridBag, constraints, npaLegalSuitPanel);

		txtForumName = new JTextField(new TextFormatField(100),"",10);
		txtForumName.setEnabled(false);
//		addComponent(2, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtForumName, gridBag, constraints, npaLegalSuitPanel);
//		if ((flag.equals("MOD")) || (flag.equals("VER")))
//		{
			if (legalSuitDetail.getCourtName()!=null)
			{
				String strForum = legalSuitDetail.getCourtName().trim();
				if (strForum != null)
				{
					if (strForum.equalsIgnoreCase("Civil Court") || strForum.equalsIgnoreCase("DRT") || strForum.equalsIgnoreCase("Lok Adalat") || strForum.equalsIgnoreCase("Revenue Recovery Authority"))
					{
						cmbForumNames.setSelectedItem(strForum);
					}
					else if (strForum.equalsIgnoreCase("Securitisation Act"))
					{
						cmbForumNames.setSelectedItem("Securitisation Act, 2002");
					}
					else
					{
						cmbForumNames.setSelectedItem("Others");
						txtForumName.setText(strForum);
					}
				}
			}
//		}

		grpPanel = new JPanel();
		grpPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		grpPanel.setBackground(dataBg);
		grpPanel.add(cmbForumNames);
		grpPanel.add(txtForumName);
		addComponent(1, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, grpPanel, gridBag, constraints, npaLegalSuitPanel);

		rowNo++;

		suitRegNo=new JLabel("Suit / Case Registration Number");
		suitRegNo.setFont(headingFont);
		suitRegNo.setOpaque(true);
		suitRegNo.setBackground(dataBg);
		addComponent(0, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, suitRegNo, gridBag, constraints, npaLegalSuitPanel);

		txtSuitCaseRegNo = new JTextField(new TextFormatField(50),"",10);
//		txtSuitCaseRegNo.setDocument(new NumberFormatField(10));
//		if ((flag.equals("MOD")) || (flag.equals("VER")))
//		{
			if (legalSuitDetail.getLegalSuiteNo() != null)
			{
				txtSuitCaseRegNo.setText(legalSuitDetail.getLegalSuiteNo());
			}
//		}
//		addComponent(1, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtSuitCaseRegNo, gridBag, constraints, npaLegalSuitPanel);

		date=new JLabel("Date");
		date.setFont(headingFont);
		date.setOpaque(true);
		date.setBackground(dataBg);
//		addComponent(2, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, date, gridBag, constraints, npaLegalSuitPanel);

		txtLegalProcDate = new JTextField(10);
		setDateFieldProperties(txtLegalProcDate, "dd/MM/yyyy", false);
//		if ((flag.equals("MOD")) || (flag.equals("VER")))
//		{
			if (legalSuitDetail.getDtOfFilingLegalSuit() != null)
			{
				txtLegalProcDate.setText(dateFormat.format(legalSuitDetail.getDtOfFilingLegalSuit()));
			}
//		}
//		addComponent(3, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtLegalProcDate, gridBag, constraints, npaLegalSuitPanel);
		
		grpPanel = new JPanel();
		grpPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		grpPanel.setBackground(dataBg);
		grpPanel.add(txtSuitCaseRegNo);
		grpPanel.add(date);
		grpPanel.add(txtLegalProcDate);
		addComponent(1, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, grpPanel, gridBag, constraints, npaLegalSuitPanel);

		rowNo++;

		forumName=new JLabel("Name of the Forum");
		forumName.setFont(headingFont);
		forumName.setOpaque(true);
		forumName.setBackground(dataBg);
		addComponent(0, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, forumName, gridBag, constraints, npaLegalSuitPanel);

		txtForumNameLoc = new JTextField(new TextFormatField(50),"",10);
//		if ((flag.equals("MOD")) || (flag.equals("VER")))
//		{
			if (legalSuitDetail.getForumName() != null)
			{
				txtForumNameLoc.setText(legalSuitDetail.getForumName());
			}
//		}
//		addComponent(1, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtForumNameLoc, gridBag, constraints, npaLegalSuitPanel);

		forumLocation=new JLabel("Location");
		forumLocation.setFont(headingFont);
		forumLocation.setOpaque(true);
		forumLocation.setBackground(dataBg);
//		addComponent(2, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, forumLocation, gridBag, constraints, npaLegalSuitPanel);

		txtForumLocation = new JTextField(new TextFormatField(50),"",10);
//		if ((flag.equals("MOD")) || (flag.equals("VER")))
//		{
			if (legalSuitDetail.getLocation() != null)
			{
				txtForumLocation.setText(legalSuitDetail.getLocation());
			}
//		}
//		addComponent(3, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtForumLocation, gridBag, constraints, npaLegalSuitPanel);
		
		grpPanel = new JPanel();
		grpPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		grpPanel.setBackground(dataBg);
		grpPanel.add(txtForumNameLoc);
		grpPanel.add(forumLocation);
		grpPanel.add(txtForumLocation);
		addComponent(1, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, grpPanel, gridBag, constraints, npaLegalSuitPanel);

		rowNo++;

		amtClaimed=new JLabel("Amount Claimed");
		amtClaimed.setFont(headingFont);
		amtClaimed.setOpaque(true);
		amtClaimed.setBackground(dataBg);
		addComponent(0, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, amtClaimed, gridBag, constraints, npaLegalSuitPanel);

		txtAmtClaimed = new JTextField(10);
		txtAmtClaimed.setDocument(new DecimalFormatField(13,2));
//		if ((flag.equals("MOD")) || (flag.equals("VER")))
//		{
			text = df.format(legalSuitDetail.getAmountClaimed());
			txtAmtClaimed.setText(text);
//		}
		addComponent(1, rowNo, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.NONE, txtAmtClaimed, gridBag, constraints, npaLegalSuitPanel);

		inRs = new JLabel("in Rs.");
		inRs.setFont(hintFont);
		inRs.setOpaque(true);
		inRs.setBackground(dataBg);
		inRs.setHorizontalTextPosition(SwingConstants.LEFT);		
//		addComponent(2, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, inRs, gridBag, constraints, npaLegalSuitPanel);
		
		grpPanel = new JPanel();
		grpPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		grpPanel.setBackground(dataBg);
		grpPanel.add(txtAmtClaimed);
		grpPanel.add(inRs);
		addComponent(1, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, grpPanel, gridBag, constraints, npaLegalSuitPanel);

		rowNo++;

		currentStatus=new JLabel("Current Status / Remarks");
		currentStatus.setFont(headingFont);
		currentStatus.setOpaque(true);
		currentStatus.setBackground(dataBg);
		addComponent(0, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, currentStatus, gridBag, constraints, npaLegalSuitPanel);

		txtCurrentStatus = new JTextArea(2, 20);
		txtCurrentStatus.setLineWrap(true);
		JScrollPane txtCurrentStatusPane = new JScrollPane(txtCurrentStatus, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//		if ((flag.equals("MOD")) || (flag.equals("VER")))
//		{
			if (legalSuitDetail.getCurrentStatus() != null)
			{
				txtCurrentStatus.setText(legalSuitDetail.getCurrentStatus());
			}
//		}
		addComponent(1, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtCurrentStatusPane, gridBag, constraints, npaLegalSuitPanel);

		rowNo++;

		proceedingsConcluded=new JLabel("Whether recovery proceedings have concluded");
		proceedingsConcluded.setFont(headingFont);
		proceedingsConcluded.setOpaque(true);
		proceedingsConcluded.setBackground(dataBg);
		addComponent(0, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, proceedingsConcluded, gridBag, constraints, npaLegalSuitPanel);

		recoveryProcConcludedYes = new JRadioButton("Yes");
		recoveryProcConcludedNo = new JRadioButton("No");
		recoveryProcConcludedYes.setBackground(dataBg);
		recoveryProcConcludedNo.setBackground(dataBg);
		recoveryProcConcludedValue = new ButtonGroup();
		recoveryProcConcludedValue.add(recoveryProcConcludedYes);
		recoveryProcConcludedValue.add(recoveryProcConcludedNo);
		recoveryProcConcludedPanel = new JPanel();
		recoveryProcConcludedPanel.add(recoveryProcConcludedYes);
		recoveryProcConcludedPanel.add(recoveryProcConcludedNo);
		recoveryProcConcludedPanel.setBackground(dataBg);
//		if ((flag.equals("MOD")) || (flag.equals("VER")))
//		{
			String concluded = legalSuitDetail.getRecoveryProceedingsConcluded();
			if (concluded != null)
			{
				if (concluded.equalsIgnoreCase("Y"))
				{
					recoveryProcConcludedYes.setSelected(true);
					recoveryProcConcludedNo.setSelected(false);
				}
				else if (concluded.equalsIgnoreCase("N"))
				{
					recoveryProcConcludedYes.setSelected(false);
					recoveryProcConcludedNo.setSelected(true);
				}
			}
//		}
		addComponent(1, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, recoveryProcConcludedPanel, gridBag, constraints, npaLegalSuitPanel);

		rowNo++;

		expectedDateOfConclusion=new JLabel("Expected Date of Conclusion of Above Efforts");
		expectedDateOfConclusion.setFont(headingFont);
		expectedDateOfConclusion.setOpaque(true);
		expectedDateOfConclusion.setBackground(dataBg);
		addComponent(0, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, expectedDateOfConclusion, gridBag, constraints, npaLegalSuitPanel);

		txtEffortConclusionDate = new JTextField(10);
		setDateFieldProperties(txtEffortConclusionDate, "dd/MM/yyyy", false);
//		if ((flag.equals("MOD")) || (flag.equals("VER")))
//		{
			if (npaDetails.getEffortsConclusionDate() != null)
			{
				txtEffortConclusionDate.setText( dateFormat.format(npaDetails.getEffortsConclusionDate()));
			}
//		}
		addComponent(1, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtEffortConclusionDate, gridBag, constraints, npaLegalSuitPanel);

		rowNo=12;
		addComponent(0, rowNo, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, npaLegalSuitPanel, gridBag, constraints, npaDetailsPanel);
		
		if (procInitiatedYes.isSelected())
		{
			enableLegalSuitDetails(true);
		}
		else if (procInitiatedNo.isSelected())
		{
			enableLegalSuitDetails(false);
		}

		rowNo++;

		others=new JLabel("Others");
		others.setFont(headingFont);
		others.setOpaque(true);
		others.setBackground(headingBg);
		addComponent(0, rowNo, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, others, gridBag, constraints, npaLegalSuitPanel);

		npaOthersPanel = new JPanel();
		npaOthersPanel.setBackground(dataBg);
		npaOthersPanel.setLayout(gridBag);

		mliComment=new JLabel("MLI's Comment on the Financial Position of Borrower / Unit");
		mliComment.setFont(headingFont);
		mliComment.setOpaque(true);
		mliComment.setBackground(dataBg);
		addComponent(0, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, mliComment, gridBag, constraints, npaOthersPanel);

		txtMliComment = new JTextArea(2, 20);
		txtMliComment.setLineWrap(true);
		JScrollPane txtMliCommentPane = new JScrollPane(txtMliComment, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//		if ((flag.equals("MOD")) || (flag.equals("VER")))
//		{
			if (npaDetails.getMliCommentOnFinPosition() != null)
			{
				txtMliComment.setText(npaDetails.getMliCommentOnFinPosition());
			}
//		}
		addComponent(1, 0, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtMliCommentPane, gridBag, constraints, npaOthersPanel);

		financialAssistanceDetails=new JLabel("Details of Financial Assistance provided / being considered by MLI to minimize Default");
		financialAssistanceDetails.setFont(headingFont);
		financialAssistanceDetails.setOpaque(true);
		financialAssistanceDetails.setBackground(dataBg);
		addComponent(0, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, financialAssistanceDetails, gridBag, constraints, npaOthersPanel);

		txtFinancialAssistanceDetails = new JTextArea(2, 20);
		txtFinancialAssistanceDetails.setLineWrap(true);
		JScrollPane txtFinancialAssistanceDetailsPane = new JScrollPane(txtFinancialAssistanceDetails, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//		if ((flag.equals("MOD")) || (flag.equals("VER")))
//		{
			if (npaDetails.getDetailsOfFinAssistance() != null)
			{
				txtFinancialAssistanceDetails.setText(npaDetails.getDetailsOfFinAssistance());
			}
//		}
		addComponent(1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtFinancialAssistanceDetailsPane, gridBag, constraints, npaOthersPanel);

		mliPropose=new JLabel("Does the MLI propose to provide credit support to Chief Promoter / Borrower for any other project");
		mliPropose.setFont(headingFont);
		mliPropose.setOpaque(true);
		mliPropose.setBackground(dataBg);
		addComponent(0, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, mliPropose, gridBag, constraints, npaOthersPanel);

		creditSupportYes = new JRadioButton("Yes");
		creditSupportNo = new JRadioButton("No");
		creditSupportYes.setBackground(dataBg);
		creditSupportNo.setBackground(dataBg);
		creditSupportValue = new ButtonGroup();
		creditSupportValue.add(creditSupportYes);
		creditSupportValue.add(creditSupportNo);
		creditSupportPanel = new JPanel();
		creditSupportPanel.add(creditSupportYes);
		creditSupportPanel.add(creditSupportNo);
		creditSupportPanel.setBackground(dataBg);
//		if ((flag.equals("MOD")) || (flag.equals("VER")))
//		{
			String support=npaDetails.getCreditSupport();
			if (support != null)
			{
				if (support.equalsIgnoreCase("Y"))
				{
					creditSupportYes.setSelected(true);
					creditSupportNo.setSelected(false);
				}
				else if (support.equalsIgnoreCase("N"))
				{
					creditSupportYes.setSelected(false);
					creditSupportNo.setSelected(true);
				}
			}
//		}
		addComponent(1, 2, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, creditSupportPanel, gridBag, constraints, npaOthersPanel);

		bankFacilityDetails=new JLabel("Details of Bank Facility already provided to Borrower");
		bankFacilityDetails.setFont(headingFont);
		bankFacilityDetails.setOpaque(true);
		bankFacilityDetails.setBackground(dataBg);
		addComponent(0, 3, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, bankFacilityDetails, gridBag, constraints, npaOthersPanel);

		txtBankFacilityDetails = new JTextArea(2, 20);
		txtBankFacilityDetails.setLineWrap(true);
		JScrollPane txtBankFacilityDetailsPane = new JScrollPane(txtBankFacilityDetails, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//		if ((flag.equals("MOD")) || (flag.equals("VER")))
//		{
			if (npaDetails.getBankFacilityDetail() != null)
			{
				txtBankFacilityDetails.setText(npaDetails.getBankFacilityDetail());
			}
//		}
		addComponent(1, 3, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtBankFacilityDetailsPane, gridBag, constraints, npaOthersPanel);

		mliAdvise=new JLabel("Does the MLI advise placing the Borrower and / or Chief Promoter under watchlist of CGTSI");
		mliAdvise.setFont(headingFont);
		mliAdvise.setOpaque(true);
		mliAdvise.setBackground(dataBg);
		addComponent(0, 4, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, mliAdvise, gridBag, constraints, npaOthersPanel);

		watchListYes = new JRadioButton("Yes");
		watchListNo = new JRadioButton("No");
		watchListYes.setBackground(dataBg);
		watchListNo.setBackground(dataBg);
		watchListValue = new ButtonGroup();
		watchListValue.add(watchListYes);
		watchListValue.add(watchListNo);
		watchListPanel = new JPanel();
		watchListPanel.add(watchListYes);
		watchListPanel.add(watchListNo);
		watchListPanel.setBackground(dataBg);
//		if ((flag.equals("MOD")) || (flag.equals("VER")))
//		{
			String watchList = npaDetails.getPlaceUnderWatchList();
			if (watchList != null)
			{
				if (watchList.equalsIgnoreCase("Y"))
				{
					watchListYes.setSelected(true);
					watchListNo.setSelected(false);
				}
				else if (watchList.equalsIgnoreCase("N"))
				{
					watchListYes.setSelected(false);
					watchListNo.setSelected(true);
				}
			}
//		}
		addComponent(1, 4, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, watchListPanel, gridBag, constraints, npaOthersPanel);

		remarks = new JLabel("Remarks");
		remarks.setFont(headingFont);
		remarks.setOpaque(true);
		remarks.setBackground(dataBg);
		addComponent(0, 5, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, remarks, gridBag, constraints, npaOthersPanel);

		txtRemarks=new JTextArea(2, 20);
		txtRemarks.setLineWrap(true);
		JScrollPane txtRemarksPane=new JScrollPane(txtRemarks, 								JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//		if ((flag.equals("MOD")) || (flag.equals("VER")))
//		{
			if (npaDetails.getRemarksOnNpa() != null)
			{
				txtRemarks.setText(npaDetails.getRemarksOnNpa());
			}
//		}
		addComponent(1, 5, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtRemarksPane, gridBag, constraints, npaOthersPanel);

		rowNo++;
		addComponent(0, rowNo, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, npaOthersPanel, gridBag, constraints, npaDetailsPanel);
		
		rowNo++;
		npaIdValue=new JLabel("");
		if (npaDetails.getNpaId() != null)
		{
			npaIdValue.setText(npaDetails.getNpaId());
		}
		npaIdValue.setVisible(false);
		addComponent(0, rowNo, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, npaIdValue, gridBag, constraints, npaDetailsPanel);

		rowNo++;
		legalIdValue=new JLabel("");
		if (legalSuitDetail.getLegalSuiteNo() != null)
		{
			legalIdValue.setText(legalSuitDetail.getLegalSuiteNo());
		}
		legalIdValue.setVisible(false);
		addComponent(0, rowNo, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, legalIdValue, gridBag, constraints, npaDetailsPanel);
	  }


	  /**
	   * This method displays the recovery details.
	   */
	   private void displayRecoveryDetails(com.cgtsi.guaranteemaintenance.Recovery recovery, String flag)
	   {
			String text = "";
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			recoveryPanel.removeAll();
			recoveryPanel.setBackground(dataBg);
			recoveryPanel.setLayout(gridBag);

			recoveryHeading=new JLabel("Recovery Details");
			recoveryHeading.setFont(headingFont);
			recoveryHeading.setOpaque(true);
			recoveryHeading.setBackground(headingBg);
			addComponent(0, 0, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, recoveryHeading, gridBag, constraints, recoveryPanel);

			recoveryDate=new JLabel("* Date of Recovery");
			recoveryDate.setFont(headingFont);
			recoveryDate.setOpaque(true);
			recoveryDate.setBackground(dataBg);
			addComponent(0, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, recoveryDate, gridBag, constraints, recoveryPanel);

			txtRecoveryDate=new JTextField(10);
			setDateFieldProperties(txtRecoveryDate, "dd/MM/yyyy", false);
			if ((flag.equalsIgnoreCase("MOD")) || (flag.equalsIgnoreCase("VER")) || (flag.equalsIgnoreCase("UPD")))
			{
				if (recovery != null && recovery.getDateOfRecovery() != null)
				{
					txtRecoveryDate.setText(dateFormat.format(recovery.getDateOfRecovery()));
				}
			}
			addComponent(1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtRecoveryDate, gridBag, constraints, recoveryPanel);

			amtRecovered=new JLabel("* Amount Recovered");
			amtRecovered.setFont(headingFont);
			amtRecovered.setOpaque(true);
			amtRecovered.setBackground(dataBg);
//			addComponent(2, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, amtRecovered, gridBag, constraints, recoveryPanel);

			txtAmtRecovered=new JTextField(10);
			txtAmtRecovered.setDocument(new DecimalFormatField(13,2));
			if ((flag.equalsIgnoreCase("MOD")) || (flag.equalsIgnoreCase("VER")) || (flag.equalsIgnoreCase("UPD")))
			{
				if (recovery!=null)
				{
					text = df.format(recovery.getAmountRecovered());
					txtAmtRecovered.setText(text);
				}
			}
//			addComponent(3, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtAmtRecovered, gridBag, constraints, recoveryPanel);

		inRs = new JLabel("in Rs.");
		inRs.setFont(hintFont);
		inRs.setOpaque(true);
		inRs.setBackground(dataBg);
		inRs.setHorizontalTextPosition(SwingConstants.LEFT);		
//		addComponent(2, 51, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, inRs, gridBag, constraints, commonPanel);
		
		grpPanel = new JPanel();
		grpPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		grpPanel.setBackground(dataBg);
		grpPanel.add(amtRecovered);
		grpPanel.add(txtAmtRecovered);
		grpPanel.add(inRs);
		addComponent(2, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, grpPanel, gridBag, constraints, recoveryPanel);							

			legalCharges=new JLabel("Legal Charges incurred, If Any");
			legalCharges.setFont(headingFont);
			legalCharges.setOpaque(true);
			legalCharges.setBackground(dataBg);
			addComponent(0, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, legalCharges, gridBag, constraints, recoveryPanel);

			txtLegalCharges=new JTextField(10);
			txtLegalCharges.setDocument(new DecimalFormatField(13,2));
			if ((flag.equalsIgnoreCase("MOD")) || (flag.equalsIgnoreCase("VER")) || (flag.equalsIgnoreCase("UPD")))
			{
				if (recovery!=null)
				{
					text = df.format(recovery.getLegalCharges());
					txtLegalCharges.setText(text);
				}
			}
//			addComponent(1, 2, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtLegalCharges, gridBag, constraints, recoveryPanel);
			
		inRs = new JLabel("in Rs.");
		inRs.setFont(hintFont);
		inRs.setOpaque(true);
		inRs.setBackground(dataBg);
		inRs.setHorizontalTextPosition(SwingConstants.LEFT);		
//		addComponent(2, 2, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, inRs, gridBag, constraints, recoveryPanel);
		
		grpPanel = new JPanel();
		grpPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		grpPanel.setBackground(dataBg);
		grpPanel.add(txtLegalCharges);
		grpPanel.add(inRs);
		addComponent(1, 2, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, grpPanel, gridBag, constraints, recoveryPanel);							

			remarks=new JLabel("Remarks");
			remarks.setFont(headingFont);
			remarks.setOpaque(true);
			remarks.setBackground(dataBg);
			addComponent(0, 3, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, remarks, gridBag, constraints, recoveryPanel);

			txtRemarks=new JTextArea(2, 20);
			txtRemarks.setLineWrap(true);
			JScrollPane txtRemarksPane=new JScrollPane(txtRemarks, 								JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			if ((flag.equalsIgnoreCase("MOD")) || (flag.equalsIgnoreCase("VER")) || (flag.equalsIgnoreCase("UPD")))
			{
				if (recovery!=null && recovery.getRemarks() != null)
				{
					txtRemarks.setText(recovery.getRemarks());
				}
			}
			addComponent(1, 3, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtRemarksPane, gridBag, constraints, recoveryPanel);

			recoveryByOTS=new JLabel("* Is Recovery by way of OTS");
			recoveryByOTS.setFont(headingFont);
			recoveryByOTS.setOpaque(true);
			recoveryByOTS.setBackground(dataBg);
			addComponent(0, 4, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, recoveryByOTS, gridBag, constraints, recoveryPanel);

			recoveryOTSYes = new JRadioButton("Yes");
			recoveryOTSNo = new JRadioButton("No");
			recoveryOTSYes.setBackground(dataBg);
			recoveryOTSNo.setBackground(dataBg);
			recoveryOTSValue = new ButtonGroup();
			recoveryOTSValue.add(recoveryOTSYes);
			recoveryOTSValue.add(recoveryOTSNo);
			recoveryOTSPanel = new JPanel();
			recoveryOTSPanel.add(recoveryOTSYes);
			recoveryOTSPanel.add(recoveryOTSNo);
			recoveryOTSPanel.setBackground(dataBg);
			if ((flag.equalsIgnoreCase("MOD")) || (flag.equalsIgnoreCase("VER")) || (flag.equalsIgnoreCase("UPD")))
			{
				if (recovery!=null)
				{
					String ots = recovery.getIsRecoveryByOTS();
					if (ots!=null)
					{
						if (ots.equalsIgnoreCase("Y"))
						{
							recoveryOTSYes.setSelected(true);
							recoveryOTSNo.setSelected(false);
						}
						else if (ots.equalsIgnoreCase("N"))
						{
							recoveryOTSYes.setSelected(false);
							recoveryOTSNo.setSelected(true);
						}
					}
				}
			}
			addComponent(1, 4, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, recoveryOTSPanel, gridBag, constraints, recoveryPanel);

			recoveryByAssets=new JLabel("Is Recovery by Sale of Assets");
			recoveryByAssets.setFont(headingFont);
			recoveryByAssets.setOpaque(true);
			recoveryByAssets.setBackground(dataBg);
			addComponent(0, 5, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, recoveryByAssets, gridBag, constraints, recoveryPanel);

			saleOfAssetsYes = new JRadioButton("Yes");
			saleOfAssetsNo = new JRadioButton("No");
			saleOfAssetsYes.setBackground(dataBg);
			saleOfAssetsNo.setBackground(dataBg);
			saleOfAssetsValue = new ButtonGroup();
			saleOfAssetsValue.add(saleOfAssetsYes);
			saleOfAssetsValue.add(saleOfAssetsNo);
			saleOfAssetsPanel = new JPanel();
			saleOfAssetsPanel.add(saleOfAssetsYes);
			saleOfAssetsPanel.add(saleOfAssetsNo);
			saleOfAssetsPanel.setBackground(dataBg);
			if ((flag.equalsIgnoreCase("MOD")) || (flag.equalsIgnoreCase("VER")) || (flag.equalsIgnoreCase("UPD")))
			{
				if (recovery!=null)
				{
					String sale = recovery.getIsRecoveryBySaleOfAsset();
					if (sale!=null)
					{
						if (sale.equalsIgnoreCase("Y"))
						{
							saleOfAssetsYes.setSelected(true);
							saleOfAssetsNo.setSelected(false);
						}
						else if (sale.equalsIgnoreCase("N"))
						{
							saleOfAssetsYes.setSelected(false);
							saleOfAssetsNo.setSelected(true);
						}
					}
				}
			}
			addComponent(1, 5, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, saleOfAssetsPanel, gridBag, constraints, recoveryPanel);

			assetsDetails=new JLabel("If Yes, Details of Assets Sold");
			assetsDetails.setFont(headingFont);
			assetsDetails.setOpaque(true);
			assetsDetails.setBackground(dataBg);
			addComponent(0, 6, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, assetsDetails, gridBag, constraints, recoveryPanel);

			txtAssetsDetails=new JTextArea(2, 20);
			txtAssetsDetails.setLineWrap(true);
			JScrollPane txtAssetsDetailsPane=new JScrollPane(txtAssetsDetails, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			if ((flag.equalsIgnoreCase("MOD")) || (flag.equalsIgnoreCase("VER")) || (flag.equalsIgnoreCase("UPD")))
			{
				if (recovery!=null && recovery.getDetailsOfAssetSold() != null)
				{
					txtAssetsDetails.setText(recovery.getDetailsOfAssetSold());
				}
			}
			addComponent(1, 6, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtAssetsDetailsPane, gridBag, constraints, recoveryPanel);

//			System.out.println(recovery.getRecoveryNo());
			recoveryNoValue=new JLabel();
			if (recovery!=null)
			{
				recoveryNoValue=new JLabel(recovery.getRecoveryNo());
			}
			recoveryNoValue.setVisible(false);
			addComponent(0, 7, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, recoveryNoValue, gridBag, constraints, recoveryPanel);
	   }

	 /**
	  * This method saves the periodic info details entered.
	  */
	  private com.cgtsi.guaranteemaintenance.PeriodicInfo savePeriodicInfo(String key, String fileName) throws ThinClientException
	  {
		  com.cgtsi.guaranteemaintenance.PeriodicInfo periodicInfo = new com.cgtsi.guaranteemaintenance.PeriodicInfo();
		  if (fileName.equals(""))
		  {
			  periodicInfo.setBorrowerId(borrowerIdValue.getText().trim());
			  periodicInfo.setBorrowerName(borrowerNameValue.getText().trim());
			  periodicInfo.setExport(false);
		  }
		  else
		  {
			  Hashtable files = readFromFile(fileName);
			  periodicInfo = (com.cgtsi.guaranteemaintenance.PeriodicInfo) files.get(key);
		  }
		  
		  ArrayList outstandingDetails = new ArrayList();
		  if (periodicInfo.getOutstandingDetails() != null)
		  {
			  outstandingDetails = periodicInfo.getOutstandingDetails();
		  }
		  else 
		  {
			  com.cgtsi.guaranteemaintenance.OutstandingDetail outstandingDetail = new com.cgtsi.guaranteemaintenance.OutstandingDetail();
			  int i = 0;
			  for (i=0;i<noOfOs;i++)
			  {
				  outstandingDetail.setCgpan(osCgpanValue[i].getText().trim());
				  outstandingDetails.add(outstandingDetail);
			  }
		  }
		  outstandingDetails = saveOutstandingDetails(outstandingDetails);
		  if (outstandingDetails==null)
		  {
		  	return null;
		  }

		  ArrayList disbursementDetails = new ArrayList();
		  if (periodicInfo.getDisbursementDetails() != null)
		  {
			  disbursementDetails = periodicInfo.getDisbursementDetails();
		  }
		  else 
		  {
			  com.cgtsi.guaranteemaintenance.Disbursement disbursement = new com.cgtsi.guaranteemaintenance.Disbursement();
			  int i = 0;
			  for (i=0;i<noOfDis;i++)
			  {
				  disbursement.setCgpan(disCgpanValue[i].getText().trim());
				  disbursementDetails.add(disbursement);
			  }
		  }
		  disbursementDetails = saveDisbursementDetails(disbursementDetails);

		  ArrayList repaymentDetails = new ArrayList();
		  if (periodicInfo.getRepaymentDetails() != null)
		  {
			  repaymentDetails = periodicInfo.getRepaymentDetails();
		  }
		  else 
		  {
			  com.cgtsi.guaranteemaintenance.Repayment repayment = new com.cgtsi.guaranteemaintenance.Repayment();
			  int i = 0;
			  for (i=0;i<noOfRepay;i++)
			  {
				  repayment.setCgpan(repayCgpanValue[i].getText().trim());
				  repaymentDetails.add(repayment);
			  }
		  }
		  repaymentDetails = saveRepaymentDetails(repaymentDetails);

		  com.cgtsi.guaranteemaintenance.NPADetails npaDetails = saveNPADetails();

		  ArrayList recoveryArr = new ArrayList();
		  if (periodicInfo.getRecoveryDetails() != null)
		  {
			  recoveryArr = periodicInfo.getRecoveryDetails();
		  }
		  com.cgtsi.guaranteemaintenance.Recovery recovery = saveRecoveryDetails();
		  recoveryArr.add(recovery);
		  periodicInfo.setRecoveryDetails(recoveryArr);

		  periodicInfo.setOutstandingDetails(outstandingDetails);
		  periodicInfo.setDisbursementDetails(disbursementDetails);
		  periodicInfo.setRepaymentDetails(repaymentDetails);
		  periodicInfo.setNpaDetail(npaDetails);


		  return periodicInfo;
	  }

	 /**
	  * This method saves the outstanding Details entered in the periodic info.
	  */
	  private ArrayList saveOutstandingDetails(ArrayList osDetails)
	  {
		  com.cgtsi.guaranteemaintenance.OutstandingDetail outstandingDetail;
		  com.cgtsi.guaranteemaintenance.OutstandingAmount outstandingAmount;
		  ArrayList osAmts;
		  ArrayList tempOsAmts=new ArrayList();
		  int size=osDetails.size();
		  int i=0;
		  int noOfInputs=0;
		  int totalInputs=0;
		  double value=0;
		  SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
		  Date date;
		  boolean valueZero=false;

		  for (i=0;i<size;i++)
		  {
			  outstandingDetail = (com.cgtsi.guaranteemaintenance.OutstandingDetail) osDetails.get(i);
			  if (outstandingDetail.getOutstandingAmounts() != null)
			  {
				  osAmts = outstandingDetail.getOutstandingAmounts();
			  }
			  else
			  {
				  osAmts = new ArrayList();
			  }
			  //noOfOutstandings[i] has the no of outstandings entered for the ith cgpan.
			  noOfInputs = noOfOutstandings[i];
//			  System.out.println("no of inputs for cgpan " + i + " " + noOfInputs);
			  if (noOfInputs!=0)
			  {
				  int osAmtsSize = osAmts.size();
				  tempOsAmts=new ArrayList();
				  
				  for (int j=0;j<noOfInputs;j++)
				  {
//					  System.out.println("size for " + i + " " + osAmtsSize);
					  String type=facilityValue[i].getText().trim();
					  type=type.substring(type.length()-2, type.length()-1);
					  if (type.equalsIgnoreCase("T"))
					  {
						  if (! tcoIdValue[totalInputs].getText().equals(""))
						  {
							  outstandingAmount = new com.cgtsi.guaranteemaintenance.OutstandingAmount();
							  for (int temp=0;temp<osAmtsSize;temp++)
							  {
								  outstandingAmount = (com.cgtsi.guaranteemaintenance.OutstandingAmount) osAmts.get(temp);
								  outstandingAmount.setCgpan(outstandingDetail.getCgpan());
								  if (tcoIdValue[totalInputs].getText().trim().equals( 	outstandingAmount.getTcoId()))
								  {
//										System.out.println("saving tc index, value -- " + totalInputs + ", " + outstandingAmount.getTcoId());
										value=(double)(Double.parseDouble( txtTCPrincipal[totalInputs].getText().trim()));
										outstandingAmount.setTcPrincipalOutstandingAmount(value);
										if (value==0)
										{
											valueZero=true;
										}
										date=dateFormat.parse(txtTCAsOnDate[totalInputs].getText().trim(), new ParsePosition(0));
										outstandingAmount.setTcOutstandingAsOnDate(date);
										tempOsAmts.add(outstandingAmount);
										break;
								  }
							  }
							}
							else
							{
//								System.out.println("saving new tc index " + totalInputs);
								outstandingAmount = new com.cgtsi.guaranteemaintenance.OutstandingAmount();
								outstandingAmount.setCgpan(outstandingDetail.getCgpan());
								value=(double)(Double.parseDouble( txtTCPrincipal[totalInputs].getText().trim()));
								outstandingAmount.setTcPrincipalOutstandingAmount(value);
								if (value==0)
								{
									valueZero=true;
								}
								date=dateFormat.parse(txtTCAsOnDate[totalInputs].getText().trim(), new ParsePosition(0));
								outstandingAmount.setTcOutstandingAsOnDate(date);
								tempOsAmts.add(outstandingAmount);
							}
						}
						else if (type.equalsIgnoreCase("W") || type.equalsIgnoreCase("R"))
						{
							if (! wcoIdValue[totalInputs].getText().equals(""))
							{
								outstandingAmount = new com.cgtsi.guaranteemaintenance.OutstandingAmount();
								
							  for (int temp=0;temp<osAmtsSize;temp++)
							  {
								  outstandingAmount = (com.cgtsi.guaranteemaintenance.OutstandingAmount) osAmts.get(temp);
								outstandingAmount.setCgpan(outstandingDetail.getCgpan());
								  if (wcoIdValue[totalInputs].getText().trim().equals( outstandingAmount.getWcoId()))
								  {
//									  System.out.println("saving wc index, value -- " + totalInputs + ", " + outstandingAmount.getWcoId());
										if (!txtWCFBPrincipal[totalInputs].getText().trim().equals(""))
										{
											value=(double)(Double.parseDouble( txtWCFBPrincipal[totalInputs].getText().trim()));
											outstandingAmount.setWcFBPrincipalOutstandingAmount(value);
										}
										if (!txtWCFBIntComm[totalInputs].getText().trim().equals(""))
										{
											value=(double)(Double.parseDouble( txtWCFBIntComm[totalInputs].getText().trim()));
											outstandingAmount.setWcFBInterestOutstandingAmount(value);
										}
										if (!txtWCFBAsOnDate[totalInputs].getText().trim().equals("") && !txtWCFBAsOnDate[totalInputs].getText().trim().endsWith("/"))
										{
											date=dateFormat.parse( txtWCFBAsOnDate[totalInputs].getText().trim(), new ParsePosition(0));
											outstandingAmount.setWcFBOutstandingAsOnDate(date);
										}
										if (!txtWCNFBPrincipal[totalInputs].getText().trim().equals(""))
										{
											value=(double)(Double.parseDouble( txtWCNFBPrincipal[totalInputs].getText().trim()));
											outstandingAmount.setWcNFBPrincipalOutstandingAmount(value);
										}
										if (!txtWCNFBIntComm[totalInputs].getText().trim().equals(""))
										{
											value=(double)(Double.parseDouble( txtWCNFBIntComm[totalInputs].getText().trim()));
											outstandingAmount.setWcNFBInterestOutstandingAmount(value);
										}
										if (!txtWCNFBAsOnDate[totalInputs].getText().trim().equals("") && !txtWCNFBAsOnDate[totalInputs].getText().trim().endsWith("/"))
										{
											date=dateFormat.parse( txtWCNFBAsOnDate[totalInputs].getText().trim(), new ParsePosition(0));
											outstandingAmount.setWcNFBOutstandingAsOnDate(date);
										}
										tempOsAmts.add(outstandingAmount);
										break;
								  }
							  }
							}
							else
							{
//								System.out.println("saving new wc index " + totalInputs);
								outstandingAmount = new com.cgtsi.guaranteemaintenance.OutstandingAmount();
								outstandingAmount.setCgpan(outstandingDetail.getCgpan());
								if (!txtWCFBPrincipal[totalInputs].getText().trim().equals(""))
								{
									value=(double)(Double.parseDouble( txtWCFBPrincipal[totalInputs].getText().trim()));
									outstandingAmount.setWcFBPrincipalOutstandingAmount(value);
								}
								if (!txtWCFBIntComm[totalInputs].getText().trim().equals(""))
								{
									value=(double)(Double.parseDouble( txtWCFBIntComm[totalInputs].getText().trim()));
									outstandingAmount.setWcFBInterestOutstandingAmount(value);
								}
								if (!txtWCFBAsOnDate[totalInputs].getText().trim().equals("") && !txtWCFBAsOnDate[totalInputs].getText().trim().endsWith("/"))
								{
									date=dateFormat.parse( txtWCFBAsOnDate[totalInputs].getText().trim(), new ParsePosition(0));
									outstandingAmount.setWcFBOutstandingAsOnDate(date);
								}
								if (!txtWCNFBPrincipal[totalInputs].getText().trim().equals(""))
								{
									value=(double)(Double.parseDouble( txtWCNFBPrincipal[totalInputs].getText().trim()));
									outstandingAmount.setWcNFBPrincipalOutstandingAmount(value);
								}
								if (!txtWCNFBIntComm[totalInputs].getText().trim().equals(""))
								{
									value=(double)(Double.parseDouble( txtWCNFBIntComm[totalInputs].getText().trim()));
									outstandingAmount.setWcNFBInterestOutstandingAmount(value);
								}
								if (!txtWCNFBAsOnDate[totalInputs].getText().trim().equals("") && !txtWCNFBAsOnDate[totalInputs].getText().trim().endsWith("/"))
								{
									date=dateFormat.parse( txtWCNFBAsOnDate[totalInputs].getText().trim(), new ParsePosition(0));
									outstandingAmount.setWcNFBOutstandingAsOnDate(date);
								}								
								/*value=(double)(Double.parseDouble( txtWCFBPrincipal[totalInputs].getText().trim()));
								outstandingAmount.setWcFBPrincipalOutstandingAmount(value);
								value=(double)(Double.parseDouble( txtWCFBIntComm[totalInputs].getText().trim()));
								outstandingAmount.setWcFBInterestOutstandingAmount(value);
								date=dateFormat.parse(txtWCFBAsOnDate[totalInputs].getText().trim(), new ParsePosition(0));
								outstandingAmount.setWcFBOutstandingAsOnDate(date);
								value=(double)(Double.parseDouble( txtWCNFBPrincipal[totalInputs].getText().trim()));
								outstandingAmount.setWcNFBPrincipalOutstandingAmount(value);
								value=(double)(Double.parseDouble( txtWCNFBIntComm[totalInputs].getText().trim()));
								outstandingAmount.setWcNFBInterestOutstandingAmount(value);
								date=dateFormat.parse( txtWCNFBAsOnDate[totalInputs].getText().trim(), new ParsePosition(0));
								outstandingAmount.setWcNFBOutstandingAsOnDate(date);*/
								tempOsAmts.add(outstandingAmount);
							}
						}
					  totalInputs++;
				  }
				  outstandingDetail.setOutstandingAmounts(tempOsAmts);
			  }
			  osDetails.set(i, outstandingDetail);
		  }
		  
		  if (valueZero)
		  {
			int confirmValue = JOptionPane.showConfirmDialog(panel, "TC Application will be closed if Outstanding Amount is entered ZERO", "Confirm Closure", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
//			System.out.println("confirm value " + confirmValue);
			if (confirmValue==1)
			{
			   return null;
			}
		  }

/*		  for (i=0;i<size;i++)
		  {
			  outstandingDetail=(com.cgtsi.guaranteemaintenance.OutstandingDetail) osDetails.get(i);
			  if (((facilityValue[i].getText()).trim()).equals("WC"))
			  {
				  value=(double)(Double.parseDouble(txtWCFBPrincipal[i].getText().trim()));
				  outstandingDetail.setWcFBPrincipalOutstandingAmount(value);
				  value=(double)(Double.parseDouble(txtWCFBIntComm[i].getText().trim()));
				  outstandingDetail.setWcFBInterestOutstandingAmount(value);
				  date=dateFormat.parse(txtWCFBAsOnDate[i].getText().trim(), new ParsePosition(0));
				  outstandingDetail.setWcFBOutstandingAsOnDate(date);
/*				  value=(double)(Double.parseDouble(txtWCNFBPrincipal[i].getText().trim()));
				  outstandingDetail.setWcNFBPrincipalOutstandingAmount(value);
				  value=(double)(Double.parseDouble(txtWCNFBIntComm[i].getText().trim()));
				  outstandingDetail.setWcNFBInterestOutstandingAmount(value);
				  date=dateFormat.parse(txtWCNFBAsOnDate[i].getText().trim(), new ParsePosition(0));
				  outstandingDetail.setWcNFBOutstandingAsOnDate(date);
*			  }
			  if (((facilityValue[i].getText()).trim()).equals("TC"))
			  {
				  value=(double)(Double.parseDouble(txtTCPrincipal[i].getText().trim()));
				  outstandingDetail.setTcPrincipalOutstandingAmount(value);
				  date=dateFormat.parse(txtTCAsOnDate[i].getText().trim(), new ParsePosition(0));
				  outstandingDetail.setTcOutstandingAsOnDate(date);
			  }
			  osDetails.set(i, outstandingDetail);
		  }
*/		  return osDetails;
	  }

	  /**
	   * This method saves the disbursment details entered in the periodic info.
	   */
	  private ArrayList saveDisbursementDetails(ArrayList disbursementDetails)
	  {
		  com.cgtsi.guaranteemaintenance.Disbursement disbursement;
		  com.cgtsi.guaranteemaintenance.DisbursementAmount disbursementAmount;
		  ArrayList disAmts;
		  int size=disbursementDetails.size();
		  int i=0;
		  int j=0;
		  int noOfInputs=0;
		  int totalInputs=0;
		  double value=0;
		  SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
		  Date date;
		  ArrayList tempDisAmts=new ArrayList();

		  /**
		  * the arraylist disbursementDetails is the list of cgpans for whcih the dusbursement
		  * details have been entered.
		  * for every cgpan in the list, the disbursement amounts entered are stored in another 
		  * arraylist disAmts.
		  */
		  for (i=0;i<size;i++)
		  {
			  disbursement = (com.cgtsi.guaranteemaintenance.Disbursement) disbursementDetails.get(i);
			  if (disbursement.getDisbursementAmounts() != null)
			  {
				  disAmts=disbursement.getDisbursementAmounts();
			  }
			  else
			  {
				  disAmts = new ArrayList();
			  }
			  //noOfDisbursements[i] has the no of disbursements entered for the ith cgpan.
			  noOfInputs = noOfDisbursements[i];
//			  System.out.println("for " + i + " " + noOfInputs);
			  if (noOfInputs!=0)
			  {
				  int disAmtsSize = disAmts.size();
				  tempDisAmts=new ArrayList();
				  boolean finalDis = false;
				  for (j=0;j<noOfInputs;j++)
				  {
//					System.out.println("id " + totalInputs + " " + disIdValue[totalInputs].getText());
					  if (! disIdValue[totalInputs].getText().trim().equals(""))
					  {
						  disbursementAmount = new com.cgtsi.guaranteemaintenance.DisbursementAmount();
						  for (int temp=0;temp<disAmtsSize;temp++)
						  {
							  disbursementAmount = (com.cgtsi.guaranteemaintenance.DisbursementAmount) disAmts.get(temp);
							  disbursementAmount.setCgpan(disbursement.getCgpan());
							  if (disIdValue[totalInputs].getText().trim().equals( disbursementAmount.getDisbursementId()))
							  {
//								  System.out.println("saving dis id -- " + disbursementAmount.getDisbursementId());
								  value = (double) Double.parseDouble(txtDisbursementAmt[totalInputs].getText().trim());
								  disbursementAmount.setDisbursementAmount(value);
								  //System.out.println("disbursement amount "+ disbursementAmount.getDisbursementAmount());
								  date=dateFormat.parse(txtDisbursementDate[totalInputs].getText().trim(), new ParsePosition(0));
								  disbursementAmount.setDisbursementDate(date);
								  //System.out.println("disbursement date " + disbursementAmount.getDisbursementDate());
								  if (chkFinalDisbursement[totalInputs].isSelected())
								  {
									  disbursementAmount.setFinalDisbursement("Y");
									  disAmts.add(disbursementAmount);
									  finalDis = true;
								  }
								  else
								  {
									  disbursementAmount.setFinalDisbursement("N");
								  }
//								  System.out.println("adding disbursement amts for " + disbursement.getCgpan() + j);
								  tempDisAmts.add(disbursementAmount);
								  break;
							  }
						  }
					  } 
					  else
					  {
						  disbursementAmount = new com.cgtsi.guaranteemaintenance.DisbursementAmount();
						disbursementAmount.setCgpan(disbursement.getCgpan());
						  value = (double) Double.parseDouble(txtDisbursementAmt[totalInputs].getText().trim());
						  disbursementAmount.setDisbursementAmount(value);
						  //System.out.println("disbursement amount "+ disbursementAmount.getDisbursementAmount());
						  date=dateFormat.parse(txtDisbursementDate[totalInputs].getText().trim(), new ParsePosition(0));
						  disbursementAmount.setDisbursementDate(date);
						  //System.out.println("disbursement date " + disbursementAmount.getDisbursementDate());
						  if (chkFinalDisbursement[totalInputs].isSelected())
						  {
							  disbursementAmount.setFinalDisbursement("Y");
							  disAmts.add(disbursementAmount);
							  finalDis = true;
						  }
						  else
						  {
							  disbursementAmount.setFinalDisbursement("N");
						  }
//						  System.out.println("adding disbursement amts for " + disbursement.getCgpan() + j);
						  tempDisAmts.add(disbursementAmount);
					  }
					  //System.out.println("getting disbursement amts for " + disbursement.getCgpan());
					  if (finalDis)
						break;
					  totalInputs++;
				  }
				  //System.out.println("setting disbursement amts for " + disbursement.getCgpan());
				  disbursement.setDisbursementAmounts(tempDisAmts);
			  }
			  disbursementDetails.set(i, disbursement);
		  }
		  return disbursementDetails;
	  }

	/**
	 * This method saves the repayment details entered in periodic details.
	 */
	private ArrayList saveRepaymentDetails(ArrayList repaymentDetails) throws ThinClientException
	{
		com.cgtsi.guaranteemaintenance.Repayment repayment;
		com.cgtsi.guaranteemaintenance.RepaymentAmount repaymentAmount;
		ArrayList repaymentAmts;
		int size=repaymentDetails.size();
		int i=0;
		int j=0;
		int noOfInputs=0;
		int totalInputs=0;
		double value=0;
		SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
		Date date;
		ArrayList tempRepayAmts=new ArrayList();

		for (i=0;i<size;i++)
		{
			repayment = (com.cgtsi.guaranteemaintenance.Repayment) repaymentDetails.get(i);
			if (repayment.getRepaymentAmounts() != null)
			{
				repaymentAmts = repayment.getRepaymentAmounts();
			}
			else
			{
				repaymentAmts = new ArrayList();
			}
			noOfInputs = noOfRepayments[i];
			if (noOfInputs!=0)
			{
				int repayAmtsSize = repaymentAmts.size();
				tempRepayAmts=new ArrayList();
				for (j=0;j<noOfInputs;j++)
				{
					if (! repayIdValue[totalInputs].getText().trim().equals(""))
					{
						repaymentAmount = new com.cgtsi.guaranteemaintenance.RepaymentAmount();
						for (int temp=0;temp<repayAmtsSize;temp++)
						{
							repaymentAmount = (com.cgtsi.guaranteemaintenance.RepaymentAmount) repaymentAmts.get(temp);
							repaymentAmount.setCgpan(repayment.getCgpan());
							if (repayIdValue[totalInputs].getText().trim().equals( repaymentAmount.getRepayId()))
							{
								value = (double) Double.parseDouble(txtRepaymentAmt[totalInputs].getText().trim());
								repaymentAmount.setRepaymentAmount(value);
								date = dateFormat.parse(txtRepaymentDate[totalInputs].getText().trim(), new ParsePosition(0));
								repaymentAmount.setRepaymentDate(date);
								tempRepayAmts.add(repaymentAmount);
								break;
							}
						}
					}
					else
					{
						repaymentAmount = new com.cgtsi.guaranteemaintenance.RepaymentAmount();
						repaymentAmount.setCgpan(repayment.getCgpan());
						value = (double) Double.parseDouble(txtRepaymentAmt[totalInputs].getText().trim());
						repaymentAmount.setRepaymentAmount(value);
						date = dateFormat.parse(txtRepaymentDate[totalInputs].getText().trim(), new ParsePosition(0));
						repaymentAmount.setRepaymentDate(date);
						tempRepayAmts.add(repaymentAmount);
					}
					totalInputs++;
				}
				repayment.setRepaymentAmounts(tempRepayAmts);
			}
			repaymentDetails.set(i, repayment);
		}
		return repaymentDetails;
	}

	/**
	 * This method saves the npa details in the periodic info.
	 */
	private com.cgtsi.guaranteemaintenance.NPADetails saveNPADetails() throws ThinClientException
	{
		Date date;
		double value;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		com.cgtsi.guaranteemaintenance.NPADetails npaDetails = new com.cgtsi.guaranteemaintenance.NPADetails();
		if (!(txtNpaDate.getText().trim()).endsWith("/") && !(txtNpaDate.getText().trim()).equals(""))
		{
			date = dateFormat.parse(txtNpaDate.getText().trim(), new ParsePosition(0));
			npaDetails.setNpaDate(date);				

		if (!((txtOsAmtAsOnNpaDate.getText().trim()).equals("")))
		{
			value = (double) Double.parseDouble(txtOsAmtAsOnNpaDate.getText().trim());
			npaDetails.setOsAmtOnNPA(value);
		}
		if (npaReportedYes.isSelected())
		{
			npaDetails.setWhetherNPAReported("Y");
		}
		else if (npaReportedNo.isSelected())
		{
			npaDetails.setWhetherNPAReported("N");
		}
		if (!(txtReportingDate.getText().trim()).endsWith("/") && ! (txtReportingDate.getText().trim()).equals(""))
		{
			date = dateFormat.parse(txtReportingDate.getText().trim(), new ParsePosition(0));
			npaDetails.setReportingDate(date);
		}
		npaDetails.setNpaReason(txtReasons.getText().trim());
		if (willfulDefaulterYes.isSelected())
		{
			npaDetails.setWillfulDefaulter("Y");
		}
		else if (willfulDefaulterNo.isSelected())
		{
			npaDetails.setWillfulDefaulter("N");
		}
		npaDetails.setEffortsTaken(txtEfforts.getText().trim());
		if (procInitiatedYes.isSelected())
		{
			npaDetails.setIsRecoveryInitiated("Y");
		}
		else if (procInitiatedNo.isSelected())
		{
			npaDetails.setIsRecoveryInitiated("N");
		}

		com.cgtsi.guaranteemaintenance.RecoveryProcedure recoveryProcedure;
		com.cgtsi.guaranteemaintenance.LegalSuitDetail legalSuitDetail = new com.cgtsi.guaranteemaintenance.LegalSuitDetail();

		// Only if npa details are entered, the legal proceeding details will be allowed to be entered.
		// since npa date is mandatory, checking if that is entered.
		// If entered, save legalsuit details.
		if (procInitiatedYes.isSelected())
		{
//			System.out.println("initiated yes.. saving details");

			ArrayList recoveryProcs = new ArrayList();
			int noOfInputs = noOfActions;
			int i=0;

				for (i=0;i<noOfInputs;i++)
				{
					recoveryProcedure = new com.cgtsi.guaranteemaintenance.RecoveryProcedure();
					recoveryProcedure.setRadId(radIdValue[i].getText());
					recoveryProcedure.setActionType(cmbActionType[i].getSelectedItem().toString());
					recoveryProcedure.setActionDetails(txtDetails[i].getText().trim());
					if (!(txtDate[i].getText().trim()).endsWith("/") && ! (txtDate[i].getText().trim()).equals(""))
					{
						date = dateFormat.parse(txtDate[i].getText().trim(), new ParsePosition(0));
						recoveryProcedure.setActionDate(date);
					}
					recoveryProcedure.setAttachmentName(txtAttachment[i].getText().trim());
					recoveryProcs.add(recoveryProcedure);
				}
			npaDetails.setRecoveryProcedure(recoveryProcs);
			
			if (!(txtNpaDate.getText().trim()).endsWith("/") && ! (txtNpaDate.getText().trim()).equals(""))
			{
				if (cmbForumNames.getSelectedItem().toString().equalsIgnoreCase("Others"))
				{
					legalSuitDetail.setCourtName(txtForumName.getText().trim());
				}
				else
				{
					if (cmbForumNames.getSelectedItem().toString().equalsIgnoreCase("Securitisation Act, 2002"))
					{
						legalSuitDetail.setCourtName("Securitisation Act");
					}
					else
					{
						legalSuitDetail.setCourtName(cmbForumNames.getSelectedItem().toString());
					}
				}
				legalSuitDetail.setLegalSuiteNo(txtSuitCaseRegNo.getText().trim());
				if (!(txtLegalProcDate.getText().trim()).endsWith("/") && ! (txtLegalProcDate.getText().trim()).equals(""))
				{
					date = dateFormat.parse(txtLegalProcDate.getText().trim(), new ParsePosition(0));
					legalSuitDetail.setDtOfFilingLegalSuit(date);
				}
				legalSuitDetail.setForumName(txtForumNameLoc.getText().trim());
				legalSuitDetail.setLocation(txtForumLocation.getText().trim());
				if (!((txtAmtClaimed.getText().trim()).equals("")))
				{
					value = (double) Double.parseDouble(txtAmtClaimed.getText().trim());
					legalSuitDetail.setAmountClaimed(value);
				}
				legalSuitDetail.setCurrentStatus(txtCurrentStatus.getText().trim());
				if (recoveryProcConcludedYes.isSelected())
				{
					legalSuitDetail.setRecoveryProceedingsConcluded("Y");
				}
				else if (recoveryProcConcludedNo.isSelected())
				{
					legalSuitDetail.setRecoveryProceedingsConcluded("N");
				}
				if (!legalIdValue.getText().equals(""))
				{
					legalSuitDetail.setLegalSuiteNo(legalIdValue.getText());
				}
			}
			if (!(txtEffortConclusionDate.getText().trim()).endsWith("/") && ! (txtEffortConclusionDate.getText().trim()).equals(""))
			{
				date = dateFormat.parse(txtEffortConclusionDate.getText().trim(), new 	ParsePosition(0));
				npaDetails.setEffortsConclusionDate(date);
			}
		}
		else
		{
			recoveryProcedure=null;
			legalSuitDetail=null;		
		}
		npaDetails.setLegalSuitDetail(legalSuitDetail);

		npaDetails.setMliCommentOnFinPosition(txtMliComment.getText().trim());
		npaDetails.setDetailsOfFinAssistance(txtFinancialAssistanceDetails.getText().trim());
		if (creditSupportYes.isSelected())
		{
			npaDetails.setCreditSupport("Y");
		}
		else if (creditSupportNo.isSelected())
		{
			npaDetails.setCreditSupport("N");
		}
		npaDetails.setBankFacilityDetail(txtBankFacilityDetails.getText().trim());
		if (watchListYes.isSelected())
		{
			npaDetails.setPlaceUnderWatchList("Y");
		}
		else if (watchListNo.isSelected())
		{
			npaDetails.setPlaceUnderWatchList("N");
		}
		npaDetails.setRemarksOnNpa(txtRemarks.getText().trim());
		if (!npaIdValue.getText().equals(""))
		{
			npaDetails.setNpaId(npaIdValue.getText());
		}
		}
		else
		{
			npaDetails=null;
		}
		return npaDetails;
	}

	/**
	 * This method saves the recovery details in the periodic info.
	 */
	private com.cgtsi.guaranteemaintenance.Recovery saveRecoveryDetails()
	{
		Date date;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		double value;
		boolean data=false;
		com.cgtsi.guaranteemaintenance.Recovery recovery = new com.cgtsi.guaranteemaintenance.Recovery();

		if (! txtRecoveryDate.getText().trim().endsWith("/") && ! txtRecoveryDate.getText().trim().equals(""))
		{
			data=true;
			date = dateFormat.parse(txtRecoveryDate.getText().trim(), new ParsePosition(0));
			recovery.setDateOfRecovery(date);
		}
		if (! txtAmtRecovered.getText().trim().equals(""))
		{
			data=true;
			value = (double) Double.parseDouble(txtAmtRecovered.getText().trim());
			recovery.setAmountRecovered(value);		
		}
		if (! txtLegalCharges.getText().trim().equals(""))
		{
			data=true;
			value = (double) Double.parseDouble(txtLegalCharges.getText().trim());
			recovery.setLegalCharges(value);
		}
		recovery.setRemarks(txtRemarks.getText().trim());
		if (recoveryOTSYes.isSelected())
		{
			data=true;
			recovery.setIsRecoveryByOTS("Y");
		}
		else if (recoveryOTSNo.isSelected())
		{
			data=true;
			recovery.setIsRecoveryByOTS("N");
		}
		if (saleOfAssetsYes.isSelected())
		{
			data=true;
			recovery.setIsRecoveryBySaleOfAsset("Y");
		}
		else if (saleOfAssetsNo.isSelected())
		{
			data=true;
			recovery.setIsRecoveryBySaleOfAsset("N");
		}
		recovery.setDetailsOfAssetSold(txtAssetsDetails.getText().trim());
		if (!data)
		{
			recovery=null;
		}
		return recovery;
	}

	/**
	* This method saves the claims details entered.
	*/
	private com.cgtsi.claim.ClaimApplication saveClaims(String key, String fileName, boolean first) throws ThinClientException
	{
		Date date;
		com.cgtsi.claim.ClaimApplication claimApplication = new com.cgtsi.claim.ClaimApplication();
		if (! fileName.equals(""))
		{
			Hashtable files = readFromFile(fileName);
			claimApplication = (com.cgtsi.claim.ClaimApplication) files.get(key);
		}

		SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
		date=dateFormat.parse(txtDateOfIssueOfRecall.getText().trim(), new ParsePosition(0));
		claimApplication.setDateOfIssueOfRecallNotice(date);
		//System.out.println("date of issue of recall notice set..");

		com.cgtsi.claim.LegalProceedingsDetail legalProceedingsDetail= new com.cgtsi.claim.LegalProceedingsDetail();
		//System.out.println("saving legal proceedings details");
		legalProceedingsDetail = saveClaimsLegalProceedings(legalProceedingsDetail, first);
		legalProceedingsDetail.setBorrowerId(claimApplication.getBorrowerId());
		//System.out.println("saved legal proceedings details");

		if (! first)
		{
			claimApplication.setClaimRefNumber(txtClaimAppRefNo.getText().trim());
			date=dateFormat.parse(txtClaimDate.getText().trim(), new ParsePosition(0));
			claimApplication.setClaimSettlementDate(date);
			claimApplication.setClpan(txtClpan.getText().trim());
			if (willfulDefaulterYes.isSelected())
			{
				claimApplication.setWhetherBorrowerIsWilfulDefaulter("Y");
			}
			else if (willfulDefaulterNo.isSelected())
			{
				claimApplication.setWhetherBorrowerIsWilfulDefaulter("N");
			}
			if (accWrittenOffYes.isSelected())
			{
				claimApplication.setWhetherAccntWrittenOffFromBooksOfMLI("Y");
			}
			else if (accWrittenOffNo.isSelected())
			{
				claimApplication.setWhetherAccntWrittenOffFromBooksOfMLI("N");
			}
			date=dateFormat.parse(txtDtOfAccWrittenOff.getText().trim(), new ParsePosition(0));
			claimApplication.setDtOnWhichAccntWrittenOff(date);
		}

		Vector termCompLoan = new Vector();
		//System.out.println("saving term loan comp loan details");
		if (claimApplication.getTermCapitalDtls() != null)
		{
			termCompLoan = claimApplication.getTermCapitalDtls();
		}
		else
		{
			com.cgtsi.claim.TermLoanCapitalLoanDetail termLoanCapitalLoanDetail = new com.cgtsi.claim.TermLoanCapitalLoanDetail();
			int i = 0;
			for (i=0;i<noOfClaimTC;i++)
			{
				termLoanCapitalLoanDetail.setCgpan(cgpanColValueTC[i].getText().trim());
				termCompLoan.add(termLoanCapitalLoanDetail);
			}
		}
		termCompLoan = saveClaimsTermCompLoan(termCompLoan, first);
		//System.out.println("saved term loan comp loan details");

		ArrayList wcDetails = new ArrayList();
		//System.out.println("saving wc limit  details");
		if (claimApplication.getWorkingCapitalDtls() != null)
		{
			wcDetails = claimApplication.getWorkingCapitalDtls();
		}
		else
		{
			com.cgtsi.claim.WorkingCapitalDetail workingCapitalDetail = new com.cgtsi.claim.WorkingCapitalDetail();
			int i = 0;
			for (i=0;i<noOfClaimWC;i++)
			{
				workingCapitalDetail.setCgpan(cgpanColValueWC[i].getText().trim());
				wcDetails.add(workingCapitalDetail);
			}
			if (first)
			{
				date=dateFormat.parse(txtDateOfRelease.getText().trim(), new ParsePosition(0));
				claimApplication.setDateOfReleaseOfWC(date);
			}
		}
		wcDetails = saveClaimsWCLimitDetails(wcDetails, first);
		//System.out.println("saved wc limit  details");

		com.cgtsi.claim.SecurityAndPersonalGuaranteeDtls securityAndPersonalGuaranteeDtls = new com.cgtsi.claim.SecurityAndPersonalGuaranteeDtls();
		//System.out.println("saving security and personal guarantee details");
		securityAndPersonalGuaranteeDtls = saveClaimsSecurityDetails(securityAndPersonalGuaranteeDtls, first);
		//System.out.println("saved security and personal guarantee details");

		Vector recoveryDetails = new Vector();
		//System.out.println("saving claims recovery details");
		if (claimApplication.getRecoveryDetails() != null)
		{
			recoveryDetails = claimApplication.getRecoveryDetails();
		}
		else
		{
			com.cgtsi.claim.RecoveryDetails rec = new com.cgtsi.claim.RecoveryDetails();
			int i = 0;
			int total = noOfClaimTC + noOfClaimWC;
			for (i=0;i<total;i++)
			{
				rec = new com.cgtsi.claim.RecoveryDetails();
				rec.setCgpan(cgpanRecoveryValue[i].getText().trim());
				recoveryDetails.add(rec);
				rec=null;
			}
		}
		recoveryDetails = saveClaimsRecoveryDetails(recoveryDetails);
		//System.out.println("saved claims recovery details");

		date=dateFormat.parse(txtOTSApprovalDate.getText().trim(), new ParsePosition(0));
		claimApplication.setDateOfSeekingOTS(date);

		ArrayList summary = new ArrayList();;
		//System.out.println("saving claims summary details");
		if (claimApplication.getClaimSummaryDtls() != null)
		{
			summary = claimApplication.getClaimSummaryDtls();
		}
		else
		{
			com.cgtsi.claim.ClaimSummaryDtls claimSummaryDtls = new com.cgtsi.claim.ClaimSummaryDtls();
			int i = 0;
			int total = noOfClaimTC + noOfClaimWC;
			for (i=0;i<total;i++)
			{
				claimSummaryDtls = new com.cgtsi.claim.ClaimSummaryDtls();
				claimSummaryDtls.setCgpan(claimSummaryCgpan[i].getText().trim());
				summary.add(claimSummaryDtls);
				claimSummaryDtls=null;
			}
		}
		summary = saveClaimsSummaryDetails(summary, first);
		//System.out.println("saved claims summary details");

		//System.out.println("setting legal proceedings");
		claimApplication.setLegalProceedingsDetails(legalProceedingsDetail);
		//System.out.println("setting term comp loan");
		claimApplication.setTermCapitalDtls(termCompLoan);
		//System.out.println("setting working capital");
		claimApplication.setWorkingCapitalDtls(wcDetails);
		//System.out.println("setting security details");
		claimApplication.setSecurityAndPersonalGuaranteeDtls(securityAndPersonalGuaranteeDtls);
		//System.out.println("setting recovery");
		claimApplication.setRecoveryDetails(recoveryDetails);
		//System.out.println("setting claims summary");
		claimApplication.setClaimSummaryDtls(summary);

		if (first)
		{
			claimApplication.setFirstInstallment(true);
			claimApplication.setSecondInstallment(false);
		}
		else
		{
			claimApplication.setFirstInstallment(false);
			claimApplication.setSecondInstallment(true);
		}

		return claimApplication;
	}

	/**
	 * This method saves the legal proceedings entered in the claims screen.
	 */
	private com.cgtsi.claim.LegalProceedingsDetail saveClaimsLegalProceedings(com.cgtsi.claim.LegalProceedingsDetail legalProceedingsDetail, boolean first)
	{
		double value;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date;

		if (!((String) cmbForumNames.getSelectedItem()).equalsIgnoreCase("Select") && !((String) cmbForumNames.getSelectedItem()).equalsIgnoreCase("Others"))
		{
			if (((String) cmbForumNames.getSelectedItem()).equalsIgnoreCase("Securitisation Act, 2002"))
			{
				legalProceedingsDetail.setForumRecoveryProceedingsInitiated("Securitisation Act");
			}
			else
			{
				legalProceedingsDetail.setForumRecoveryProceedingsInitiated((String) cmbForumNames.getSelectedItem());
			}
		}
		else if (((String) cmbForumNames.getSelectedItem()).equalsIgnoreCase("Others"))
		{
			legalProceedingsDetail.setForumRecoveryProceedingsInitiated(txtForumName.getText().trim());
		}
		
		legalProceedingsDetail.setSuitCaseRegNumber(txtSuitCaseRegNo.getText().trim());
		legalProceedingsDetail.setNameOfForum(txtForumNameLoc.getText().trim());
		legalProceedingsDetail.setCurrentStatusRemarks(txtCurrentStatus.getText().trim());
		if (recoveryProcConcludedYes.isSelected())
		{
			legalProceedingsDetail.setIsRecoveryProceedingsConcluded("Y");
		}
		else if (recoveryProcConcludedNo.isSelected())
		{
			legalProceedingsDetail.setIsRecoveryProceedingsConcluded("N");
		}
		if (! txtAmtClaimed.getText().trim().equals(""))
		{
			value = (double) (Double.parseDouble(txtAmtClaimed.getText().trim()));
			legalProceedingsDetail.setAmountClaimed(value);
		}
		legalProceedingsDetail.setLocation(txtForumLocation.getText().trim());
		date=dateFormat.parse(txtLegalProcDate.getText().trim(), new ParsePosition(0));
			//System.out.println("date of filing suit -- " + date);
		legalProceedingsDetail.setFilingDate(date);
		//System.out.println("date of filing suit from object-- " + legalProceedingsDetail.getFilingDate());
		if (! first)
		{
			date=dateFormat.parse(txtConclusionDate.getText().trim(), new ParsePosition(0));
			legalProceedingsDetail.setDateOfConclusionOfRecoveryProceedings(date);
		}

		return legalProceedingsDetail;
	}

	/**
	 * This method saves the term / composite loan details entered in the claims screen.
	 */
	private Vector saveClaimsTermCompLoan(Vector termCompLoan, boolean first)
	{
		double value;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date;

		int termSize = termCompLoan.size();
		int i=0;
		com.cgtsi.claim.TermLoanCapitalLoanDetail termLoanCapitalLoanDetail;

		for (i=0;i<termSize;i++)
		{
			termLoanCapitalLoanDetail = (com.cgtsi.claim.TermLoanCapitalLoanDetail) termCompLoan.get(i);
			date = dateFormat.parse(txtDateOfLastDisbursementCol[i].getText().trim(), new ParsePosition(0));
			//System.out.println("date of last disbursement tc  -- " + i + " " + date);
			termLoanCapitalLoanDetail.setLastDisbursementDate(date);
			if (!txtPrincipal[i].getText().trim().equals(""))
			{
				value = (double) (Double.parseDouble(txtPrincipal[i].getText().trim()));
				termLoanCapitalLoanDetail.setPrincipalRepayment(value);
			}
			if (!txtPrincipal[i].getText().trim().equals(""))
			{
				value = (double) (Double.parseDouble(txtInterest[i].getText().trim()));
				termLoanCapitalLoanDetail.setInterestAndOtherCharges(value);
			}
			value = (double) (Double.parseDouble(txtOutstandingOnNpaTC[i].getText().trim()));
			termLoanCapitalLoanDetail.setOutstandingAsOnDateOfNPA(value);
			value = (double) (Double.parseDouble(txtOutstandingInCaseTC[i].getText().trim()));
			termLoanCapitalLoanDetail.setOutstandingStatedInCivilSuit(value);
			value = (double) (Double.parseDouble(txtOutstandingOnLodgementTC[i].getText().trim()));
			termLoanCapitalLoanDetail.setOutstandingAsOnDateOfLodgement(value);
			if (! first)
			{
				value = (double) (Double.parseDouble( txtOutstandingOnLodgementTCSecond[i].getText().trim()));
				termLoanCapitalLoanDetail.setOsAsOnDateOfLodgementOfClmForSecInstllmnt(value);
			}

			termCompLoan.set(i, termLoanCapitalLoanDetail);
		}

		return termCompLoan;
	}

	/**
	 * This method saves the working capital loan details entered in the claims screen.
	 */
	private ArrayList saveClaimsWCLimitDetails(ArrayList wcDetails, boolean first)
	{
		double value;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date;

		int wcSize = wcDetails.size();
		int i = 0;
		com.cgtsi.claim.WorkingCapitalDetail workingCapitalDetail;

		for (i=0;i<wcSize;i++)
		{
			workingCapitalDetail = (com.cgtsi.claim.WorkingCapitalDetail) wcDetails.get(i);

			value = (double) (Double.parseDouble(txtOutstandingOnNpaWC[i].getText().trim()));
			workingCapitalDetail.setOutstandingAsOnDateOfNPA(value);
			value = (double) (Double.parseDouble(txtOutstandingInCaseWC[i].getText().trim()));
			workingCapitalDetail.setOutstandingStatedInCivilSuit(value);
			value = (double) (Double.parseDouble(txtOutstandingOnLodegementWC[i].getText().trim()));
			workingCapitalDetail.setOutstandingAsOnDateOfLodgement(value);
			if (! first)
			{
				value = (double) (Double.parseDouble( txtOutstandingOnLodegementWCSecond[i].getText().trim()));
				workingCapitalDetail.setOsAsOnDateOfLodgementOfClmForSecInstllmnt(value);
			}

			wcDetails.set(i, workingCapitalDetail);
		}

		return wcDetails;
	}

	/**
	 * This method saves the security and personal guarantee details of the claims screen.
	 */
	private com.cgtsi.claim.SecurityAndPersonalGuaranteeDtls saveClaimsSecurityDetails(com.cgtsi.claim.SecurityAndPersonalGuaranteeDtls securityAndPersonalGuaranteeDtls, boolean first)
	{
		double value;

		com.cgtsi.claim.DtlsAsOnDateOfSanction dtlsAsOnDateOfSanction = new com.cgtsi.claim.DtlsAsOnDateOfSanction();

		if (! txtAsOnSanctionLandValue.getText().trim().equals(""))
		{
			value = (double) (Double.parseDouble(txtAsOnSanctionLandValue.getText().trim()));
			dtlsAsOnDateOfSanction.setValueOfLand(value);
		}
		if (! txtAsOnSanctionBuildingValue.getText().trim().equals(""))
		{
			value = (double) (Double.parseDouble(txtAsOnSanctionBuildingValue.getText().trim()));
			dtlsAsOnDateOfSanction.setValueOfBuilding(value);
		}
		if (! txtAsOnSanctionMachineValue.getText().trim().equals(""))
		{
			value = (double) (Double.parseDouble(txtAsOnSanctionMachineValue.getText().trim()));
			dtlsAsOnDateOfSanction.setValueOfMachine(value);
		}
		if (! txtAsOnSanctionFixedValue.getText().trim().equals(""))
		{
			value = (double) (Double.parseDouble(txtAsOnSanctionFixedValue.getText().trim()));
			dtlsAsOnDateOfSanction.setValueOfOtherFixedMovableAssets(value);
		}
		if (! txtAsOnSanctionCurrentValue.getText().trim().equals(""))
		{
			value = (double) (Double.parseDouble(txtAsOnSanctionCurrentValue.getText().trim()));
			dtlsAsOnDateOfSanction.setValueOfCurrentAssets(value);
		}
		if (! txtAsOnSanctionOthersValue.getText().trim().equals(""))
		{
			value = (double) (Double.parseDouble(txtAsOnSanctionOthersValue.getText().trim()));
			dtlsAsOnDateOfSanction.setValueOfOthers(value);
		}
		if (! txtAsOnSanctionNetworth.getText().trim().equals(""))
		{
			value = (double) (Double.parseDouble(txtAsOnSanctionNetworth.getText().trim()));
			dtlsAsOnDateOfSanction.setNetworthOfGuarantors(value);
		}
		dtlsAsOnDateOfSanction.setReasonsForReduction( txtAsOnSanctionReasonsForReduction.getText().trim());

		securityAndPersonalGuaranteeDtls.setDetailsAsOnDateOfSanction(dtlsAsOnDateOfSanction);

		com.cgtsi.claim.DtlsAsOnDateOfNPA dtlsAsOnDateOfNPA = new com.cgtsi.claim.DtlsAsOnDateOfNPA();

		if (! txtAsOnNpaLandValue.getText().trim().equals(""))
		{
			value = (double) (Double.parseDouble(txtAsOnNpaLandValue.getText().trim()));
			dtlsAsOnDateOfNPA.setValueOfLand(value);
		}
		if (! txtAsOnNpaBuildingValue.getText().trim().equals(""))
		{
			value = (double) (Double.parseDouble(txtAsOnNpaBuildingValue.getText().trim()));
			dtlsAsOnDateOfNPA.setValueOfBuilding(value);
		}
		if (! txtAsOnNpaMachineValue.getText().trim().equals(""))
		{
			value = (double) (Double.parseDouble(txtAsOnNpaMachineValue.getText().trim()));
			dtlsAsOnDateOfNPA.setValueOfMachine(value);
		}
		if (! txtAsOnNpaFixedValue.getText().trim().equals(""))
		{
			value = (double) (Double.parseDouble(txtAsOnNpaFixedValue.getText().trim()));
			dtlsAsOnDateOfNPA.setValueOfOtherFixedMovableAssets(value);
		}
		if (! txtAsOnNpaCurrentValue.getText().trim().equals(""))
		{
			value = (double) (Double.parseDouble(txtAsOnNpaCurrentValue.getText().trim()));
			dtlsAsOnDateOfNPA.setValueOfCurrentAssets(value);
		}
		if (! txtAsOnNpaOthersValue.getText().trim().equals(""))
		{
			value = (double) (Double.parseDouble(txtAsOnNpaOthersValue.getText().trim()));
			dtlsAsOnDateOfNPA.setValueOfOthers(value);
		}
		if (! txtAsOnNpaNetworth.getText().trim().equals(""))
		{
			value = (double) (Double.parseDouble(txtAsOnNpaNetworth.getText().trim()));
			dtlsAsOnDateOfNPA.setNetworthOfGuarantors(value);
		}
		dtlsAsOnDateOfNPA.setReasonsForReduction(txtAsOnNpaReasonsForReduction.getText().trim());

		securityAndPersonalGuaranteeDtls.setDetailsAsOnDateOfNPA(dtlsAsOnDateOfNPA);

		com.cgtsi.claim.DtlsAsOnLogdementOfClaim dtlsAsOnLogdementOfClaim = new com.cgtsi.claim.DtlsAsOnLogdementOfClaim();

		if (! txtAsOnLodgementLandValue.getText().trim().equals(""))
		{
			value = (double) (Double.parseDouble(txtAsOnLodgementLandValue.getText().trim()));
			dtlsAsOnLogdementOfClaim.setValueOfLand(value);
		}
		if (! txtAsOnLodgementBuildingValue.getText().trim().equals(""))
		{
			value = (double) (Double.parseDouble(txtAsOnLodgementBuildingValue.getText().trim()));
			dtlsAsOnLogdementOfClaim.setValueOfBuilding(value);
		}
		if (! txtAsOnLodgementMachineValue.getText().trim().equals(""))
		{
			value = (double) (Double.parseDouble(txtAsOnLodgementMachineValue.getText().trim()));
			dtlsAsOnLogdementOfClaim.setValueOfMachine(value);
		}
		if (! txtAsOnLodgementFixedValue.getText().trim().equals(""))
		{
			value = (double) (Double.parseDouble(txtAsOnLodgementFixedValue.getText().trim()));
			dtlsAsOnLogdementOfClaim.setValueOfOtherFixedMovableAssets(value);
		}
		if (! txtAsOnLodgementCurrentValue.getText().trim().equals(""))
		{
			value = (double) (Double.parseDouble(txtAsOnLodgementCurrentValue.getText().trim()));
			dtlsAsOnLogdementOfClaim.setValueOfCurrentAssets(value);
		}
		if (! txtAsOnLodgementOthersValue.getText().trim().equals(""))
		{
			value = (double) (Double.parseDouble(txtAsOnLodgementOthersValue.getText().trim()));
			dtlsAsOnLogdementOfClaim.setValueOfOthers(value);
		}
		if (! txtAsOnLodgementNetworth.getText().trim().equals(""))
		{
			value = (double) (Double.parseDouble(txtAsOnLodgementNetworth.getText().trim()));
			dtlsAsOnLogdementOfClaim.setNetworthOfGuarantors(value);
		}
		dtlsAsOnLogdementOfClaim.setReasonsForReduction( txtAsOnLodgementReasonsForReduction.getText().trim());

		securityAndPersonalGuaranteeDtls.setDetailsAsOnDateOfLodgementOfClaim( dtlsAsOnLogdementOfClaim);

		if (! first)
		{
			com.cgtsi.claim.DtlsAsOnLogdementOfSecondClaim dtlsAsOnLogdementOfSecondClaim = new com.cgtsi.claim.DtlsAsOnLogdementOfSecondClaim();

			if (! txtAsOnSecondClaimLandValue.getText().trim().equals(""))
			{
				value = (double) (Double.parseDouble(txtAsOnSecondClaimLandValue.getText().trim()));
				dtlsAsOnLogdementOfSecondClaim.setValueOfLand(value);
			}
			if (! txtAsOnSecondClaimBuildingValue.getText().trim().equals(""))
			{
				value = (double) 	(Double.parseDouble(txtAsOnSecondClaimBuildingValue.getText().trim()));
				dtlsAsOnLogdementOfSecondClaim.setValueOfBuilding(value);
			}
			if (! txtAsOnSecondClaimMachineValue.getText().trim().equals(""))
			{
				value = (double) (Double.parseDouble(txtAsOnSecondClaimMachineValue.getText().trim()));
				dtlsAsOnLogdementOfSecondClaim.setValueOfMachine(value);
			}
			if (! txtAsOnSecondClaimFixedValue.getText().trim().equals(""))
			{
				value = (double) (Double.parseDouble(txtAsOnSecondClaimFixedValue.getText().trim()));
				dtlsAsOnLogdementOfSecondClaim.setValueOfOtherFixedMovableAssets(value);
			}
			if (! txtAsOnSecondClaimCurrentValue.getText().trim().equals(""))
			{
				value = (double) (Double.parseDouble(txtAsOnSecondClaimCurrentValue.getText().trim()));
				dtlsAsOnLogdementOfSecondClaim.setValueOfCurrentAssets(value);
			}
			if (! txtAsOnSecondClaimOthersValue.getText().trim().equals(""))
			{
				value = (double) (Double.parseDouble(txtAsOnSecondClaimOthersValue.getText().trim()));
				dtlsAsOnLogdementOfSecondClaim.setValueOfOthers(value);
			}

			if (! txtAsOnSecondClaimNetworth.getText().trim().equals(""))
			{
				value = (double) (Double.parseDouble(txtAsOnSecondClaimNetworth.getText().trim()));
				dtlsAsOnLogdementOfSecondClaim.setNetworthOfGuarantors(value);
			}
			if (! txtAmtRealisedPg.getText().trim().equals(""))
			{
				value = (double) (Double.parseDouble(txtAmtRealisedPg.getText().trim()));
				dtlsAsOnLogdementOfSecondClaim.setAmtRealisedPersonalGuarantee(value);
			}

			if (! txtAmtRealisedLand.getText().trim().equals(""))
			{
				value = (double) (Double.parseDouble(txtAmtRealisedLand.getText().trim()));
				dtlsAsOnLogdementOfSecondClaim.setAmtRealisedLand(value);
			}
			if (! txtAmtRealisedBuilding.getText().trim().equals(""))
			{
				value = (double) (Double.parseDouble(txtAmtRealisedBuilding.getText().trim()));
				dtlsAsOnLogdementOfSecondClaim.setAmtRealisedBuilding(value);
			}
			if (! txtAmtRealisedMachine.getText().trim().equals(""))
			{
				value = (double) (Double.parseDouble(txtAmtRealisedMachine.getText().trim()));
				dtlsAsOnLogdementOfSecondClaim.setAmtRealisedMachine(value);
			}
			if (! txtAmtRealisedFixed.getText().trim().equals(""))
			{
				value = (double) (Double.parseDouble(txtAmtRealisedFixed.getText().trim()));
				dtlsAsOnLogdementOfSecondClaim.setAmtRealisedFixed(value);
			}
			if (! txtAmtRealisedCurrent.getText().trim().equals(""))
			{
				value = (double) (Double.parseDouble(txtAmtRealisedCurrent.getText().trim()));
				dtlsAsOnLogdementOfSecondClaim.setAmtRealisedCurrent(value);
			}
			if (! txtAmtRealisedOthers.getText().trim().equals(""))
			{
			value = (double) (Double.parseDouble(txtAmtRealisedOthers.getText().trim()));
			dtlsAsOnLogdementOfSecondClaim.setAmtRealisedOthers(value);
			}

			dtlsAsOnLogdementOfSecondClaim.setReasonsForReductionLand( txtAsOnSecondClaimReasonsLand.getText().trim());
			dtlsAsOnLogdementOfSecondClaim.setReasonsForReductionBuilding( txtAsOnSecondClaimReasonsBuilding.getText().trim());
			dtlsAsOnLogdementOfSecondClaim.setReasonsForReductionMachine( txtAsOnSecondClaimReasonsMachine.getText().trim());
			dtlsAsOnLogdementOfSecondClaim.setReasonsForReductionFixed( txtAsOnSecondClaimReasonsFixed.getText().trim());
			dtlsAsOnLogdementOfSecondClaim.setReasonsForReductionCurrent( txtAsOnSecondClaimReasonsCurrent.getText().trim());
			dtlsAsOnLogdementOfSecondClaim.setReasonsForReductionOthers( txtAsOnSecondClaimReasonsOthers.getText().trim());

			securityAndPersonalGuaranteeDtls.setDetailsAsOnDateOfLodgementOfSecondClaim( dtlsAsOnLogdementOfSecondClaim);
		}

		return securityAndPersonalGuaranteeDtls;
	}

	/**
	 * This method saves the recovery details of the claims screen.
	 */
	private Vector saveClaimsRecoveryDetails(Vector recoveryInfo)
	{
		double value;

		int recoverySize = recoveryInfo.size();
		int i = 0;
		com.cgtsi.claim.RecoveryDetails recoveryDetails;

		for (i=0;i<recoverySize;i++)
		{
			recoveryDetails = (com.cgtsi.claim.RecoveryDetails) recoveryInfo.get(i);

			String text = (String) cmbModeOfRecovery[i].getSelectedItem();
			if (text.equalsIgnoreCase("select"))
			{
				text="";
			}
			recoveryDetails.setModeOfRecovery(text);
			value=0;
			if (! txtPrincipalTCRecovery[i].getText().trim().equals(""))
			{
				value = (double) (Double.parseDouble(txtPrincipalTCRecovery[i].getText().trim()));
			}
			recoveryDetails.setTcPrincipal(value);
			value=0;
			if (! txtInterestTCRecovery[i].getText().trim().equals(""))
			{
				value = (double) (Double.parseDouble(txtInterestTCRecovery[i].getText().trim()));
			}
			recoveryDetails.setTcInterestAndOtherCharges(value);
			value=0;
			if (! txtAmountWCRecovery[i].getText().trim().equals(""))
			{
				value = (double) (Double.parseDouble(txtAmountWCRecovery[i].getText().trim()));
			}
			recoveryDetails.setWcAmount(value);
			value=0;
			if (! txtOthersWCRecovery[i].getText().trim().equals(""))
			{
				value = (double) (Double.parseDouble(txtOthersWCRecovery[i].getText().trim()));
			}
			recoveryDetails.setWcOtherCharges(value);

			recoveryInfo.set(i, recoveryDetails);
			recoveryDetails = null;
		}

		return recoveryInfo;
	}

	/**
	 * This method saves the claim summary details in the claims screen.
	 */
	private ArrayList saveClaimsSummaryDetails(ArrayList summary, boolean first)
	{
		double value;

		int summarySize = summary.size();
		int i = 0;
		com.cgtsi.claim.ClaimSummaryDtls claimSummaryDtls;

		for (i=0;i<summarySize;i++)
		{
			claimSummaryDtls = (com.cgtsi.claim.ClaimSummaryDtls) summary.get(i);

			value = (double) (Double.parseDouble(txtAmountClaimed[i].getText().trim()));
			claimSummaryDtls.setAmount(value);

			if (! first)
			{
				value = (double) (Double.parseDouble(txtAmountSettled[i].getText().trim()));
				claimSummaryDtls.setAmntSettledByCGTSI(value);
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				Date date = dateFormat.parse(txtDtOfSettlement[i].getText().trim(), new ParsePosition(0));
				claimSummaryDtls.setDtOfSettlemntOfFirstInstallmentOfClm(date);
				value = (double) (Double.parseDouble(txtAmountClaimedSI[i].getText().trim()));
				claimSummaryDtls.setAmntClaimedInFinalInstllmnt(value);
			}

			summary.set(i, claimSummaryDtls);
		}

		return summary;
	}

	/**
	 * This method displays the fields for the verify screen
	 */
	private void displayVerifyPanel(String type, String flag)
	{
		panel.removeAll();
		panel.setBackground(Color.WHITE);
		panel.setLayout(gridBag);

		String label = "";
		if (flag.equalsIgnoreCase("NEW"))
		{
			label="New ";
		}
		else if (flag.equalsIgnoreCase("MOD"))
		{
			label="Modify ";
		}
		else if (flag.equalsIgnoreCase("VER"))
		{
			label="Verify ";
		}
		else if (flag.equalsIgnoreCase("REC") || flag.equalsIgnoreCase("VERREC"))
		{
			label="Periodic Info Recovery ";
		}
		
		if (type.equalsIgnoreCase("PER") && !(flag.equalsIgnoreCase("REC") || flag.equalsIgnoreCase("VERREC")))
		{
			label=label + "Periodic Info ";
		}
		else if (type.equalsIgnoreCase("CLAIM"))
		{
			label=label + "Claim ";
		}
		else if (type.equalsIgnoreCase("CLAIM1"))
		{
			label = label + "Claim First Instalment ";
		}
		else if (type.equalsIgnoreCase("CLAIM2"))
		{
			label = label + "Claim Second Instalment ";
		}
		else if (type.equalsIgnoreCase("APP"))
		{
			label = label + "Application ";
		}
		
		label = label + "File Details";
		fileDetails = new JLabel(label);
		fileDetails.setOpaque(true);
		fileDetails.setBackground(headingBg);
		addComponent(0, 6, GridBagConstraints.REMAINDER, GridBagConstraints.CENTER, GridBagConstraints.BOTH, fileDetails, gridBag, constraints, panel);

		if (flag.equalsIgnoreCase("NEW") || flag.equalsIgnoreCase("REC"))
		{
			fileLocation = new JLabel("Whether basic data file received/downloaded");
		}
		else
		{
			fileLocation = new JLabel("File Location");
		}
		fileLocation.setOpaque(true);
		fileLocation.setBackground(dataBg);
		addComponent(0, 7, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, fileLocation, gridBag, constraints, panel);

//		floppy = new JRadioButton("Floppy");
//		floppy.setBackground(Color.WHITE);
		if (flag.equalsIgnoreCase("NEW") || flag.equalsIgnoreCase("REC"))
		{
			hardDisk = new JRadioButton("Yes");
		}
		else
		{
			hardDisk = new JRadioButton("Hard Disk");
		}
		hardDisk.setBackground(Color.WHITE);
		noFile = new JRadioButton("No");
		noFile.setBackground(Color.WHITE);
		noFile.setActionCommand("No File");
		exportFile = new JRadioButton("From Export File");
		exportFile.setBackground(Color.WHITE);
		existingFile = new JRadioButton("From Existing File");
		existingFile.setBackground(Color.WHITE);
		noFile.addActionListener(new BrowseListener());
//		floppy.addActionListener(new BrowseListener());
		hardDisk.addActionListener(new BrowseListener());
		exportFile.addActionListener(new BrowseListener());
		existingFile.addActionListener(new BrowseListener());
		location = new ButtonGroup();
//		location.add(floppy);
		location.add(hardDisk);
		location.add(noFile);
		location.add(exportFile);
		location.add(existingFile);
		JPanel fileLocationPanel = new JPanel();
		fileLocationPanel.setBackground(Color.WHITE);
//		fileLocationPanel.add(floppy);
		fileLocationPanel.add(hardDisk);
		fileLocationPanel.add(noFile);
		fileLocationPanel.add(exportFile);
		fileLocationPanel.add(existingFile);
		if ((type.equalsIgnoreCase("PER") || type.equalsIgnoreCase("CLAIM1") || type.equalsIgnoreCase("CLAIM2")) && (flag.equalsIgnoreCase("NEW")))
		{
			exportFile.setVisible(false);
			existingFile.setVisible(false);
			hardDisk.setVisible(true);
			noFile.setVisible(true);
			noFile.setEnabled(true);
			hardDisk.setSelected(true);
			noFile.setSelected(false);
			exportFile.setSelected(false);
			existingFile.setSelected(false);
		}
		else if (type.equalsIgnoreCase("PER") && flag.equalsIgnoreCase("REC"))
		{
			exportFile.setVisible(true);
			existingFile.setVisible(true);
			hardDisk.setVisible(false);
			noFile.setVisible(false);
			exportFile.setEnabled(true);
			exportFile.setSelected(true);
			existingFile.setSelected(false);
			hardDisk.setSelected(false);
			noFile.setSelected(false);
		}
		else if (type.equalsIgnoreCase("PER") && flag.equalsIgnoreCase("VERREC"))
		{
			exportFile.setVisible(true);
			existingFile.setVisible(true);
			hardDisk.setVisible(false);
			noFile.setVisible(false);
			exportFile.setEnabled(false);
			exportFile.setSelected(false);
			existingFile.setSelected(true);
			hardDisk.setSelected(false);
			noFile.setSelected(false);
		}
		else
		{
			exportFile.setVisible(false);
			existingFile.setVisible(false);
			hardDisk.setVisible(true);
			noFile.setVisible(true);
			noFile.setEnabled(false);
			hardDisk.setSelected(true);
			noFile.setSelected(false);
			exportFile.setSelected(false);
			existingFile.setSelected(false);
		}
		addComponent(1, 7, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, fileLocationPanel, gridBag, constraints, panel);

		path = new JLabel("File Path");
		path.setOpaque(true);
		path.setBackground(dataBg);
		addComponent(0, 8, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, path, gridBag, constraints, panel);

		txtFilePath = new JTextField(10);
		addComponent(1, 8, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, txtFilePath, gridBag, constraints, panel);

		browse = new JButton("Browse");
		browse.setActionCommand(type + "$" + flag);
		browse.addActionListener(this);
		addComponent(2, 8, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, browse, gridBag, constraints, panel);
		
		if ((type.equalsIgnoreCase("PER") || type.equalsIgnoreCase("CLAIM1") || type.equalsIgnoreCase("CLAIM2") || type.equalsIgnoreCase("CLAIM1"))
			&& (flag.equalsIgnoreCase("NEW") || flag.equalsIgnoreCase("REC")))
		{
			brnName = new JLabel("Enter Branch Name");
			brnName.setOpaque(true);
			brnName.setBackground(dataBg);
			addComponent(0, 9, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, brnName, gridBag, constraints, panel);

			txtBrnName = new JTextField(10);
			addComponent(1, 9, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, txtBrnName, gridBag, constraints, panel);
		}

		reset=new JButton("Reset");
		reset.setActionCommand("Reset");
		reset.addActionListener(this);

		ok=new JButton("Ok");
		ok.addActionListener(this);

		buttonsPanel=new JPanel();
		buttonsPanel.setLayout(new FlowLayout());
		buttonsPanel.add(ok);
		buttonsPanel.add(reset);
		addComponent(0, 10, GridBagConstraints.REMAINDER, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, buttonsPanel, gridBag, constraints, panel);
		
		container.removeAll();
		container.add(panel, BorderLayout.NORTH);
		
		if (!type.equalsIgnoreCase("APP"))
		{
			infoPanel = new JPanel();
			infoPanel.setBackground(Color.WHITE);
			infoPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		
			JLabel info1 = new JLabel("			Periodic Information (in structured format) or Claim Form may be submitted to CGTSI by the MLIs by submitting information on-line,", SwingConstants.LEFT);
			info1.setOpaque(true);
			info1.setBackground(dataBg);
//			addComponent(0, 1, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, info1, gridBag, constraints, infoPanel);
		
			JLabel info2 = new JLabel(" on website of CGTSI at www.cgtsi.org.in or by utilizing Thin Client module. Thin Client module may be downloaded from website of CGTSI", SwingConstants.LEFT);
			info2.setOpaque(true);
			info2.setBackground(dataBg);
			info2.setAlignmentX(JLabel.LEFT_ALIGNMENT);
//			addComponent(0, 2, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, info2, gridBag, constraints, infoPanel);

			JLabel info3 = new JLabel(" by using user-id and password issued by CGTSI to the Operating Offices of MLIs. Thin Client module is required to be installed on PC", SwingConstants.LEFT);
			info3.setOpaque(true);
			info3.setBackground(dataBg);
			info3.setAlignmentX(JLabel.LEFT_ALIGNMENT);
//			addComponent(0, 3, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, info3, gridBag, constraints, infoPanel);
		
			JLabel info4 = new JLabel(" before making its use for submission of information. Subsequently, the MLIs are required to download the Basic Data File from website of CGTSI", SwingConstants.LEFT);
			info4.setOpaque(true);
			info4.setBackground(dataBg);
			info4.setAlignmentX(JLabel.LEFT_ALIGNMENT);
//			addComponent(0, 4, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, info4, gridBag, constraints, infoPanel);
		
			JLabel info5 = new JLabel(" and utilize the file to submit additional data through Thin Client module.", SwingConstants.LEFT);
			info5.setOpaque(true);
			info5.setBackground(dataBg);
			info5.setAlignmentX(JLabel.LEFT_ALIGNMENT);
//			addComponent(0, 5, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, info5, gridBag, constraints, infoPanel);
		
			JLabel info6 = new JLabel("			Basic Data File makes the data submission process easy and hassle free. It contains the background data which is already submitted to CGTSI", SwingConstants.LEFT);
			info6.setOpaque(true);
			info6.setBackground(dataBg);
			info6.setAlignmentX(JLabel.LEFT_ALIGNMENT);
//			addComponent(0, 6, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, info6, gridBag, constraints, infoPanel);
		
			JLabel info7 = new JLabel(" by MLIs in the past in respect of various Credit facilities covered by CGTSI. The 'Basic Data File' helps user at MLIs to provide additional", SwingConstants.LEFT);
			info7.setOpaque(true);
			info7.setBackground(dataBg);
			info7.setAlignmentX(JLabel.LEFT_ALIGNMENT);
//			addComponent(0, 7, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, info7, gridBag, constraints, infoPanel);

			JLabel info8 = new JLabel(" information only, thus avoiding submission of data multiple times. The Operating Offices of MLIs may help in installing the Thin Client module at the Branch level.", SwingConstants.LEFT);
			info8.setOpaque(true);
			info8.setBackground(dataBg);
			info8.setAlignmentX(JLabel.LEFT_ALIGNMENT);
//			addComponent(0, 8, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, info8, gridBag, constraints, infoPanel);

			JLabel info9 = new JLabel(" The Operating Offices may subsequently download the 'Basic Data File' from the website of CGTSI and provide it to the Branches to feed the additional data required.", SwingConstants.LEFT);
			info9.setOpaque(true);
			info9.setBackground(dataBg);
			info9.setAlignmentX(JLabel.LEFT_ALIGNMENT);
//			addComponent(0, 9, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, info9, gridBag, constraints, infoPanel);

			JLabel info10 = new JLabel(" The file so updated by the Branches are required to be verified at the Operating Office level before submitting it to CGTSI through its website.", SwingConstants.LEFT);
			info10.setOpaque(true);
			info10.setBackground(dataBg);
			info10.setAlignmentX(JLabel.LEFT_ALIGNMENT);
//			addComponent(0, 10, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, info10, gridBag, constraints, infoPanel);

			infoPanel.setBackground(dataBg);
			infoPanel.setAlignmentX(JPanel.LEFT_ALIGNMENT);
			infoPanel.add(info1);
			infoPanel.add(info2);
			infoPanel.add(info3);
			infoPanel.add(info4);
			infoPanel.add(info5);
			infoPanel.add(info6);
			infoPanel.add(info7);
			infoPanel.add(info8);
			infoPanel.add(info9);
			infoPanel.add(info10);

			container.add(infoPanel, BorderLayout.CENTER);
		}
	}

	/**
	 * This method displays the list of applications that have to be verified or modified
	 * The flag parameter passed identifies whether the files are being displayed
	 * for verification or modification.
	 *	flag "VER" -- Verification
	 *	flag "MOD" -- Modification
	 *	flag "NEW" -- New
	 * The objType parameter passed identifies whether the object is an application or
	 * periodic info or claims.
	 *	objType "APP" -- Application
	 *	objType "PER" -- Periodic Info
	 *	objType "CLAIM" -- Claims
	 */
	private void displayList(String objType, String flag) throws ThinClientException
	{
		try
		{
			 String fileName="";
/*			 if (floppy.isSelected())
			 {
				 if (flag.equals("MOD"))
				 {
					 fileName=getDateString()+ objType + "." + "NEW";
				 }
				 else
				 {
					 fileName=getDateString()+ objType + "." + flag;
				 }
			 }*/
			 if (hardDisk.isSelected())
			 {
				 fileName=txtFilePath.getText().trim();
				 if (fileName.equals(""))
				 {
					 String actionCommand = ok.getActionCommand();
					 JOptionPane.showMessageDialog(panel, "Please Select a File from Hard Disk", "Warning", JOptionPane.INFORMATION_MESSAGE);
					 panel.removeAll();
					 displayVerifyPanel(objType, flag);
					 ok.setActionCommand(actionCommand);
					 validate();
					 return;
				 }
				 else if (objType.equalsIgnoreCase("APP"))
				 {
					 if ((flag.equals("MOD") || (flag.equals("VER"))) && (! fileName.endsWith(".NEW") || fileName.indexOf("APP") == -1))
					 {
						 throw new ThinClientException("Not a Valid Application File");
					 }
				 }
				 else if (objType.equals("PER"))
				 {
					 if (flag.equals("NEW") && (! fileName.endsWith(".EXP") || fileName.indexOf("PER") == -1))
					 {
						 throw new ThinClientException("Not a Valid Periodic Info Export File");
					 }
					 if ((flag.equals("MOD") || (flag.equals("VER"))) && (! fileName.endsWith(".NEW") || fileName.indexOf("PER") == -1))
					 {
						 throw new ThinClientException("Not a Valid Periodic Info File");
					 }
					 if (flag.equalsIgnoreCase("NEW") && txtBrnName.getText().trim().equals(""))
					 {
						String actionCommand = ok.getActionCommand();
						JOptionPane.showMessageDialog(panel, "Please Enter Branch Name", "Warning", JOptionPane.INFORMATION_MESSAGE);
						panel.removeAll();
						displayVerifyPanel(objType, flag);
						ok.setActionCommand(actionCommand);
						validate();
						return;
					 }
				 }
				 else if (objType.equals("CLAIM1") || objType.equals("CLAIM2"))
				 {
					 if (flag.equals("NEW") && (! fileName.endsWith(".EXP") || fileName.indexOf("CLAIM") == -1))
					 {
						 throw new ThinClientException("Not a Valid Claims Export File");
					 }
					 if ((flag.equals("MOD") || (flag.equals("VER"))) && (! fileName.endsWith(".NEW") || fileName.indexOf("CLAIM") == -1))
					 {
						 throw new ThinClientException("Not a Valid Claims File");
					 }
					if (flag.equalsIgnoreCase("NEW") && txtBrnName.getText().trim().equals(""))
					{
					   String actionCommand = ok.getActionCommand();
					   JOptionPane.showMessageDialog(panel, "Please Enter Branch Name", "Warning", JOptionPane.INFORMATION_MESSAGE);
					   panel.removeAll();
					   displayVerifyPanel(objType, flag);
					   ok.setActionCommand(actionCommand);
					   validate();
					   return;
					}
				 }
			 }
			 else if (noFile.isSelected())
			 {
/*				 if (objType.equals("PER"))
				 {
					displayPeriodicInputPanel("","","NEW");
//					 displayPeriodicInfo();
				 }
				 else if (objType.equals("CLAIM1"))
				 {
					displayClaimsInputPanel(true);
//					 displayClaimsFIPanel("", "", flag);
				 }
				 else if (objType.equals("CLAIM2"))
				 {
					displayClaimsInputPanel(false);
//					 displayClaimsSIPanel("", "", flag);
				 }*/
				 JOptionPane.showMessageDialog(new ThinClient(), "Please download the basic data file", "Message", JOptionPane.WARNING_MESSAGE);
				 String actionCommand = ok.getActionCommand();
				 panel.removeAll();
				 displayVerifyPanel(objType, flag);
				 ok.setActionCommand(actionCommand);
				 validate();
				 return;
			 }
			 else if (exportFile.isSelected() || existingFile.isSelected())
			 {
				 fileName=txtFilePath.getText().trim();
				 if (fileName.equals(""))
				 {
					 String actionCommand = ok.getActionCommand();
					 JOptionPane.showMessageDialog(panel, "Please Select a File from Hard Disk", "Warning", JOptionPane.INFORMATION_MESSAGE);
					 panel.removeAll();
					 displayVerifyPanel(objType, flag);
					 ok.setActionCommand(actionCommand);
					 validate();
					 return;
				 }
				 else if (flag.equals("REC") && ((! fileName.endsWith(".EXP") || fileName.indexOf("PER") == -1) && (! fileName.endsWith(".NEW") || fileName.indexOf("PER") == -1)))
				 {
					 throw new ThinClientException("Not a Valid Periodic Info File for Update Recovery Details");
				 }
				 else if (flag.equals("VRE") && ((! fileName.endsWith(".NEW") || fileName.indexOf("PER") == -1)))
				 {
					 throw new ThinClientException("Not a Valid Periodic Info File for Verify Updated Recovery Details");
				 }
			 }
			 else
			 {
				 String actionCommand = ok.getActionCommand();
				 JOptionPane.showMessageDialog(panel, "Please Select a File Location", "Warning", JOptionPane.INFORMATION_MESSAGE);
				 panel.removeAll();
				 displayVerifyPanel(objType, flag);
				 ok.setActionCommand(actionCommand);
				 validate();
				 return;
			 }

			 panel.removeAll();
			 panel.setBackground(Color.WHITE);
			 panel.setLayout(gridBag);

			 fileList=new JLabel("List of Files");
			 fileList.setOpaque(true);
			 fileList.setBackground(headingBg);
			 addComponent(0, 1, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, fileList, gridBag, constraints, panel);

			 Hashtable objects=readFromFile(fileName);
			 JPanel fileListPanel=new JPanel();
			 fileListPanel.setBackground(Color.WHITE);
			 files=new ButtonGroup();
			 
//			 System.out.println("version from file " + objects.get("version"));

			 Set keys=objects.keySet();
			 Iterator iterator=keys.iterator();
			 while (iterator.hasNext())
			 {
				 String keyVal=(String) iterator.next();
				 if (!keyVal.equalsIgnoreCase("version"))
				 {
					if (objType.equals("CLAIM1"))
					{
						try
						{
							com.cgtsi.claim.ClaimApplication claimApplication = (com.cgtsi.claim.ClaimApplication) objects.get(keyVal);
							if (claimApplication.getFirstInstallment())
							{
								JRadioButton AppFile=new JRadioButton(keyVal + "-" + claimApplication.getBorrowerDetails().getBorrowerName());
								AppFile.setActionCommand(objType + "$" + flag + "$" + keyVal + "$" + fileName + "&" + txtBrnName.getText().trim());
								AppFile.addActionListener(this);
								//System.out.println("action command -- " + AppFile.getActionCommand());
								AppFile.setBackground(Color.WHITE);
								files.add(AppFile);
								fileListPanel.add(AppFile);
							}
						}
						catch(ClassCastException ce)
						{
						   throw new ThinClientException("Not a Valid Claim File");
						}
					}
					else if (objType.equals("CLAIM2"))
					{
					   try
					   {
						com.cgtsi.claim.ClaimApplication claimApplication = (com.cgtsi.claim.ClaimApplication) objects.get(keyVal);
						if (claimApplication.getSecondInstallment())
						{
							JRadioButton AppFile=new JRadioButton(keyVal + "-" + claimApplication.getBorrowerDetails().getBorrowerName());
							AppFile.setActionCommand(objType + "$" + flag + "$" + keyVal + "$" + fileName + "&" + txtBrnName.getText().trim());
							AppFile.addActionListener(this);
							//System.out.println("action command -- " + AppFile.getActionCommand());
							AppFile.setBackground(Color.WHITE);
							files.add(AppFile);
							fileListPanel.add(AppFile);
						}
					   }
					   catch(ClassCastException ce)
					   {
						  throw new ThinClientException("Not a Valid Claim File");
					   }
					}
					else if (objType.equals("PER"))
					{
					   try
					   {
					   com.cgtsi.guaranteemaintenance.PeriodicInfo periodicInfo = (com.cgtsi.guaranteemaintenance.PeriodicInfo) objects.get(keyVal);
					   if (periodicInfo.getIsVerified())
					   {
						   throw new ThinClientException("Periodic Info Already Verified");
					   }
					   String brnName = "";
					   if (flag.equalsIgnoreCase("NEW") || flag.equalsIgnoreCase("REC"))
					   {
						   brnName=txtBrnName.getText().trim();
					   }
					   JRadioButton AppFile=new JRadioButton(keyVal + "-" + periodicInfo.getBorrowerName());
					   AppFile.setActionCommand(objType + "$" + flag + "$" + keyVal + "$" + fileName  + "&" + brnName);
					   AppFile.addActionListener(this);
					   //System.out.println("action command -- " + AppFile.getActionCommand());
					   AppFile.setBackground(Color.WHITE);
					   files.add(AppFile);
					   fileListPanel.add(AppFile);
					   }
					   catch(ClassCastException ce)
					   {
						  throw new ThinClientException("Not a Valid Periodic Info File");
					   }
					}
					else
					{
//					   System.out.println("1 " + keyVal);
					   if (keyVal==null || keyVal.equalsIgnoreCase("null"))
					   {
//						   System.out.println("here");
						   keyVal = "new";
					   }
					   if (objType.equalsIgnoreCase("APP"))
					   {
						   try
						   {
							   com.cgtsi.application.Application application = (com.cgtsi.application.Application) objects.get(keyVal);
							   if (application.getIsVerified())
							   {
								   throw new ThinClientException("Applicaiton Already Verified.");
							   }
							   String bName="";
							   if (((com.cgtsi.application.Application)objects.get(keyVal)).getBorrowerDetails()!=null)
							   {
								   bName=((com.cgtsi.application.Application)objects.get(keyVal)).getBorrowerDetails().getSsiDetails().getSsiName();
							   }
							   String dispKeyVal=keyVal;
							   if (bName!=null && !bName.equals(""))
							   {
								   dispKeyVal=keyVal + " (" + bName + ")";
							   }
							
							   JRadioButton AppFile=new JRadioButton(dispKeyVal);
							   AppFile.setActionCommand(objType + "$" + flag + "$" + keyVal + "$" + fileName);
							   AppFile.addActionListener(this);
//							   System.out.println("action command -- " + AppFile.getActionCommand());
							   AppFile.setBackground(Color.WHITE);
							   files.add(AppFile);
							   fileListPanel.add(AppFile);
						   }
						   catch(ClassCastException ce)
						   {
							  throw new ThinClientException("Not a Valid Application File");
						   }
					   }
					   if (objType.equalsIgnoreCase("CLAIM"))
					   {
						   try
						   {
							   com.cgtsi.claim.ClaimApplication claimApplication = (com.cgtsi.claim.ClaimApplication) objects.get(keyVal);
							   if (claimApplication.getIsVerified())
							   {
								   throw new ThinClientException("Claim Applicaiton Already Verified.");
							   }
							   JRadioButton AppFile=new JRadioButton(keyVal+"-"+claimApplication.getBorrowerDetails().getBorrowerName());
							   AppFile.setActionCommand(objType + "$" + flag + "$" + keyVal + "$" + fileName);
							   AppFile.addActionListener(this);
//							   System.out.println("action command -- " + AppFile.getActionCommand());
							   AppFile.setBackground(Color.WHITE);
							   files.add(AppFile);
							   fileListPanel.add(AppFile);
						   }
						   catch(ClassCastException ce)
						   {
							  throw new ThinClientException("Not a Valid Claim File");
						   }
					   }
					}
				 }
			 }
			 addComponent(0, 2, GridBagConstraints.REMAINDER, GridBagConstraints.CENTER, GridBagConstraints.BOTH, fileListPanel, gridBag, constraints, panel);

			 cancel=new JButton("Cancel");
			 cancel.setActionCommand("CancelApplicationList");
			 cancel.addActionListener(this);

			buttonsPanel=new JPanel();
			buttonsPanel.setLayout(new FlowLayout());
			buttonsPanel.add(cancel);
			addComponent(0, 3, GridBagConstraints.REMAINDER, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, buttonsPanel, gridBag, constraints, panel);

			container.removeAll();
			container.add(panel, BorderLayout.NORTH);
		}
		catch (Exception e)
		{
			throw new ThinClientException(e.getMessage());
		}
	}

	/**
	* This method displays the claims first installment screen
	*/
	private void displayClaimsFIPanel(String key, String fileName, String flag) throws ThinClientException
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		panel.removeAll();
		panel.setBackground(dataBg);
		panel.setLayout(gridBag);

		String borrowerIdInput = "";
		String borrowerNameInput = "";
		com.cgtsi.claim.ClaimApplication claimApplication = new com.cgtsi.claim.ClaimApplication();

		int index=fileName.lastIndexOf("&");
		String brnName="";
		if (index>0)
		{
			brnName=fileName.substring(index+1, fileName.length());
			fileName=fileName.substring(0, index);
		}
		else if (flag.equalsIgnoreCase("NEW"))
		{
			brnName=lblBrnName.getText().trim();
		}
		
		if (! fileName.equals(""))
		{
			Hashtable details=readFromFile(fileName);
			claimApplication = (com.cgtsi.claim.ClaimApplication) details.get(key);
		}
		else
		{
			borrowerIdInput = txtBorrowerIdInput.getText().trim();
			borrowerNameInput = txtBorrowerNameInput.getText().trim();
			claimApplication.setBorrowerId(borrowerIdInput);
		}
		
		hintMandatory = new JLabel("All Fields marked with * are mandatory");
		hintMandatory.setFont(dataFont);
		hintMandatory.setOpaque(true);
		hintMandatory.setBackground(dataBg);
		addComponent(0, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, hintMandatory, gridBag, constraints, panel);
		
		hintDate = new JLabel("All dates should be in DD/MM/YYYY format");
		hintDate.setFont(dataFont);
		hintDate.setOpaque(true);
		hintDate.setBackground(dataBg);
		addComponent(0, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, hintDate, gridBag, constraints, panel);

		OperatingOffHeading = new JLabel("Details of Operating Office and Lending Branch");
		OperatingOffHeading.setFont(headingFont);
		OperatingOffHeading.setOpaque(true);
		OperatingOffHeading.setBackground(headingBg);
		addComponent(0, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, OperatingOffHeading, gridBag, constraints, panel);

		com.cgtsi.claim.MemberInfo memberInfo = new com.cgtsi.claim.MemberInfo();
		if (claimApplication.getMemberDetails() != null)
		{
			memberInfo = claimApplication.getMemberDetails();
		}
		displayClaimsMemberInfo(memberInfo);

		addComponent(0, 3, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, memberInfoPanel, gridBag, constraints, panel);

		unitDetailsHeading = new JLabel("Borrower / Unit Details");
		unitDetailsHeading.setFont(headingFont);
		unitDetailsHeading.setOpaque(true);
		unitDetailsHeading.setBackground(headingBg);
		addComponent(0, 4, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, unitDetailsHeading, gridBag, constraints, panel);

		com.cgtsi.claim.BorrowerInfo borrowerInfo = new com.cgtsi.claim.BorrowerInfo();
		if (claimApplication.getBorrowerDetails() != null)
		{
			borrowerInfo = claimApplication.getBorrowerDetails();
		}
		displayClaimsBorrowerInfo(borrowerInfo);

		addComponent(0, 5, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, borrowerDetailsPanel, gridBag, constraints, panel);

		accStatusHeading = new JLabel("Status of Account(s)");
		accStatusHeading.setFont(headingFont);
		accStatusHeading.setOpaque(true);
		accStatusHeading.setBackground(headingBg);
		addComponent(0, 6, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, accStatusHeading, gridBag, constraints, panel);

		displayClaimsAccStatus(claimApplication, flag);

		addComponent(0, 7, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, accStatusPanel, gridBag, constraints, panel);

		legalProceedingsDetails = new JLabel("Details of Legal Proceedings");
		legalProceedingsDetails.setFont(headingFont);
		legalProceedingsDetails.setOpaque(true);
		legalProceedingsDetails.setBackground(headingBg);
		addComponent(0, 8, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, legalProceedingsDetails, gridBag, constraints, panel);

		com.cgtsi.claim.LegalProceedingsDetail legalProceedingsDetail = new com.cgtsi.claim.LegalProceedingsDetail();
		if (claimApplication.getLegalProceedingsDetails() != null)
		{
			legalProceedingsDetail = claimApplication.getLegalProceedingsDetails();
		}
		displayClaimsLegalProceedings(legalProceedingsDetail, flag, true);

		addComponent(0, 9, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, legalProceedingsPanel, gridBag, constraints, panel);

		ArrayList cgpanInput = new ArrayList();
		int i = 0;
		Vector termDetails = new Vector();
		if (! fileName.equals(""))
		{
			termDetails = claimApplication.getTermCapitalDtls();
		}
		else
		{
			StringTokenizer tcCgpans = new StringTokenizer(txtTCCgpansInput.getText().trim(),",");
			while (tcCgpans.hasMoreTokens())
			{
				String cgpan = "";
				cgpan = tcCgpans.nextToken();
				com.cgtsi.claim.TermLoanCapitalLoanDetail termLoanCapitalLoanDetail = new 		com.cgtsi.claim.TermLoanCapitalLoanDetail();
				termLoanCapitalLoanDetail.setCgpan(cgpan);
				cgpanInput.add(cgpan);						// to set the cgpans for recovery and claim summary details.
				termDetails.add(termLoanCapitalLoanDetail);
				termLoanCapitalLoanDetail=null;			
			}
			noOfClaimTC = termDetails.size();
		}
		if (!termDetails.isEmpty())
		{
			termCompLoanHeading=new JLabel("Term Loan (TL) / Composite Loan Details");
			termCompLoanHeading.setFont(headingFont);
			termCompLoanHeading.setOpaque(true);
			termCompLoanHeading.setBackground(headingBg);
			addComponent(0, 10, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, termCompLoanHeading, gridBag, constraints, panel);

			String response = "";

			displayClaimsTermCompLoan(termDetails, flag, true);

			addComponent(0, 11, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, termCompPanel, gridBag, constraints, panel);
		}

		ArrayList wcDetails = new ArrayList();
		if (! fileName.equals(""))
		{
			wcDetails = claimApplication.getWorkingCapitalDtls();
		}
		else
		{
			StringTokenizer wcCgpans = new StringTokenizer(txtWCCgpansInput.getText().trim(),",");
			while (wcCgpans.hasMoreTokens())
			{
				String cgpan = "";
				cgpan = wcCgpans.nextToken();
				com.cgtsi.claim.WorkingCapitalDetail workingCapitalDetail = new 		com.cgtsi.claim.WorkingCapitalDetail();
				workingCapitalDetail.setCgpan(cgpan);
				cgpanInput.add(cgpan);						// to set the cgpans for recovery and claim summary details.
				wcDetails.add(workingCapitalDetail);
				workingCapitalDetail=null;			
			}
			noOfClaimWC = wcDetails.size();
		}
		if (!wcDetails.isEmpty())
		{
			wcLimitHeading=new JLabel("Working Capital (WC) Limit Details");
			wcLimitHeading.setFont(headingFont);
			wcLimitHeading.setOpaque(true);
			wcLimitHeading.setBackground(headingBg);
			addComponent(0, 12, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, wcLimitHeading, gridBag, constraints, panel);

			displayClaimsWCLimitDetails(wcDetails, flag, true);

			addComponent(0, 13, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, wcLimitPanel, gridBag, constraints, panel);

			wcReleasePanel.removeAll();
			wcReleasePanel.setBackground(dataBg);
			wcReleasePanel.setLayout(gridBag);

			dateOfRelease=new JLabel("Date of Release of WC (in case of New Borrowers)");
			dateOfRelease.setFont(headingFont);
			dateOfRelease.setOpaque(true);
			dateOfRelease.setBackground(dataBg);
			addComponent(0, 1, 2, GridBagConstraints.WEST, GridBagConstraints.BOTH, dateOfRelease, gridBag, constraints, wcReleasePanel);

			txtDateOfRelease = new JTextField(10);
			setDateFieldProperties(txtDateOfRelease, "dd/MM/yyyy", false);
			if ((flag.equals("MOD")) || (flag.equals("VER")))
			{
				if (claimApplication.getDateOfReleaseOfWC() != null)
				{
					txtDateOfRelease.setText(dateFormat.format(claimApplication.getDateOfReleaseOfWC()));
				}
			}
			addComponent(2, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtDateOfRelease, gridBag, constraints, wcReleasePanel);

			addComponent(0, 14, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, wcReleasePanel, gridBag, constraints, panel);
		}

		securityHeading=new JLabel("Security and Personal Guarantee Details");
		securityHeading.setFont(headingFont);
		securityHeading.setOpaque(true);
		securityHeading.setBackground(headingBg);
		addComponent(0, 15, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, securityHeading, gridBag, constraints, panel);

		com.cgtsi.claim.SecurityAndPersonalGuaranteeDtls securityAndPersonalGuaranteeDtls = new com.cgtsi.claim.SecurityAndPersonalGuaranteeDtls();
		if ((flag.equals("MOD")) || (flag.equals("VER")))
		{
			 securityAndPersonalGuaranteeDtls = claimApplication.getSecurityAndPersonalGuaranteeDtls();
		}
		displayClaimsSecurityDetails(securityAndPersonalGuaranteeDtls, flag, true);

		addComponent(0, 16, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, securityPersonalGuaranteePanel, gridBag, constraints, panel);

		recoveryMadeHeading=new JLabel("Recovery Made from Borrower / Unit after Account classified as NPA");
		recoveryMadeHeading.setFont(headingFont);
		recoveryMadeHeading.setOpaque(true);
		recoveryMadeHeading.setBackground(headingBg);
		addComponent(0, 17, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, recoveryMadeHeading, gridBag, constraints, panel);

		Vector recoveryDetails = new Vector();
		if (! fileName.equals(""))
		{
			recoveryDetails = claimApplication.getRecoveryDetails();
		}
		else
		{
			int size = cgpanInput.size();
			for (i=0;i<size;i++)
			{
				com.cgtsi.claim.RecoveryDetails rec = new com.cgtsi.claim.RecoveryDetails();
				rec.setCgpan((String) cgpanInput.get(i));
				recoveryDetails.add(rec);
				rec=null;
			}
		}
		displayClaimsRecoveryDetails(recoveryDetails, flag);

		addComponent(0, 18, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, recoveryMadePanel, gridBag, constraints, panel);

		otsRecoveryPanel.removeAll();
		otsRecoveryPanel.setBackground(dataBg);
		otsRecoveryPanel.setLayout(gridBag);

		recoveryOTS = new JLabel("If Recovery done through OTS, indicate the date of seeking approval of CGTSI for OTS.");
		recoveryOTS.setFont(headingFont);
		recoveryOTS.setOpaque(true);
		recoveryOTS.setBackground(dataBg);
		addComponent(0, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, recoveryOTS, gridBag, constraints, otsRecoveryPanel);

		txtOTSApprovalDate = new JTextField(10);
		setDateFieldProperties(txtOTSApprovalDate, "dd/MM/yyyy", false);
		if ((flag.equals("MOD")) || (flag.equals("VER")))
		{
			if (claimApplication.getDateOfSeekingOTS() != null)
			{
				if (claimApplication.getDateOfSeekingOTS() != null)
				{
					txtOTSApprovalDate.setText(dateFormat.format(claimApplication.getDateOfSeekingOTS()));
				}
			}
		}
		addComponent(1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtOTSApprovalDate, gridBag, constraints, otsRecoveryPanel);

		addComponent(0, 19, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, otsRecoveryPanel, gridBag, constraints, panel);

		claimSummaryHeading=new JLabel("Total Amount for which guarantee claim is being preferred");
		claimSummaryHeading.setFont(headingFont);
		claimSummaryHeading.setOpaque(true);
		claimSummaryHeading.setBackground(headingBg);
		addComponent(0, 20, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, claimSummaryHeading, gridBag, constraints, panel);

		ArrayList summary = new ArrayList();
		if (! fileName.equals(""))
		{
			summary = claimApplication.getClaimSummaryDtls();
		}
		else
		{
			int size = cgpanInput.size();
			for (i=0;i<size;i++)
			{
				com.cgtsi.claim.ClaimSummaryDtls claimSummaryDtls = new com.cgtsi.claim.ClaimSummaryDtls();
				claimSummaryDtls.setCgpan((String) cgpanInput.get(i));
				summary.add(claimSummaryDtls);
			}
		}
		displayClaimsSummaryDetails(summary, flag, true);

		addComponent(0, 21, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, claimSummaryPanel, gridBag, constraints, panel);

		if (key.equals(""))
		{
			key = borrowerIdInput;
		}
		lblKey = new JLabel(key);
		lblFileName = new JLabel(fileName);
		lblBrnName=new JLabel(brnName);
		lblKey.setVisible(false);
		lblFileName.setVisible(false);
		lblBrnName.setVisible(false);
		hiddenPanel=new JPanel();
		hiddenPanel.add(lblKey);
		hiddenPanel.add(lblFileName);
		hiddenPanel.add(lblBrnName);
		hiddenPanel.setVisible(false);
		addComponent(0, 22, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, hiddenPanel, gridBag, constraints, panel);

		reset=new JButton("Reset");
		reset.setActionCommand("Reset");
		reset.addActionListener(this);

		save=new JButton("Save");
		cancel=new JButton("Cancel");
		if (flag.equals("NEW"))
		{
			//System.out.println("setting action command as SaveNewClaims");
		 save.setActionCommand("SaveNewFIClaims");
		 cancel.setActionCommand("CancelNewFIClaims");
		}
		else if (flag.equals("MOD"))
		{
		 save.setActionCommand("SaveModifyFIClaims");
		 cancel.setActionCommand("CancelModifyClaims");
		}
		else if (flag.equals("VER"))
		{
		 save.setActionCommand("SaveVerifyFIClaims");
		 cancel.setActionCommand("CancelVerifyClaims");
		}

		save.addActionListener(this);
		cancel.addActionListener(this);
		
		print = new JButton("Print");
		print.setActionCommand("PrintClaimsFIApplication");
		print.addActionListener(this);

		buttonsPanel=new JPanel();
		buttonsPanel.setLayout(new FlowLayout());
		buttonsPanel.add(save);
		buttonsPanel.add(reset);
		buttonsPanel.add(cancel);
		buttonsPanel.add(print);
		addComponent(0, 23, GridBagConstraints.REMAINDER, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, buttonsPanel, gridBag, constraints, panel);

		container.removeAll();
		scrollPane.setViewportView(panel);
		container.add(scrollPane, BorderLayout.CENTER);
	}

	/**
	* This method displays the claims second installmen screen.
	*/
	private void displayClaimsSIPanel(String key, String fileName, String flag) throws ThinClientException
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		panel.removeAll();
		panel.setBackground(dataBg);
		panel.setLayout(gridBag);

		String borrowerIdInput = "";
		String borrowerNameInput = "";
		com.cgtsi.claim.ClaimApplication claimApplication = new com.cgtsi.claim.ClaimApplication();
		
		int index=fileName.lastIndexOf("&");
		String brnName="";
		if (index>0)
		{
			brnName=fileName.substring(index+1, fileName.length());
			fileName=fileName.substring(0, index);
		}
		else if (flag.equalsIgnoreCase("NEW"))
		{
			brnName=lblBrnName.getText().trim();
		}
		
		if (! fileName.equals(""))
		{
			Hashtable details=readFromFile(fileName);
			claimApplication = (com.cgtsi.claim.ClaimApplication) details.get(key);
		}
		else
		{
			borrowerIdInput = txtBorrowerIdInput.getText().trim();
			borrowerNameInput = txtBorrowerNameInput.getText().trim();
			claimApplication.setBorrowerId(borrowerIdInput);
			com.cgtsi.claim.BorrowerInfo borrowerDetails = new com.cgtsi.claim.BorrowerInfo();
			borrowerDetails.setBorrowerName(borrowerNameInput);
			claimApplication.setBorrowerDetails(borrowerDetails);
		}

		hintMandatory = new JLabel("All Fields marked with * are mandatory");
		hintMandatory.setFont(dataFont);
		hintMandatory.setOpaque(true);
		hintMandatory.setBackground(dataBg);
		addComponent(0, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, hintMandatory, gridBag, constraints, panel);
		
		hintDate = new JLabel("All dates should be in DD/MM/YYYY format");
		hintDate.setFont(dataFont);
		hintDate.setOpaque(true);
		hintDate.setBackground(dataBg);
		addComponent(0, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, hintDate, gridBag, constraints, panel);

		claimAppRefNo = new JLabel("Claim Application Ref. No");
		claimAppRefNo.setFont(headingFont);
		claimAppRefNo.setOpaque(true);
		claimAppRefNo.setBackground(dataBg);
		addComponent(0, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, claimAppRefNo, gridBag, constraints, panel);

		txtClaimAppRefNo = new JTextField(new TextFormatField(15),"",15);
//		if ((flag.equals("MOD")) || (flag.equals("VER")))
//		{
			txtClaimAppRefNo.setText(claimApplication.getClaimRefNumber());
//		}
		txtClaimAppRefNo.setEnabled(false);
		addComponent(1, 2, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtClaimAppRefNo, gridBag, constraints, panel);

		date = new JLabel("Date");
		date.setFont(headingFont);
		date.setOpaque(true);
		date.setBackground(dataBg);
		addComponent(2, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, date, gridBag, constraints, panel);

		txtClaimDate = new JTextField(10);
		setDateFieldProperties(txtClaimDate, "dd/MM/yyyy", false);
//		if ((flag.equals("MOD")) || (flag.equals("VER")))
//		{
			if (claimApplication.getClaimSettlementDate() != null)
			{
				txtClaimDate.setText(dateFormat.format(claimApplication.getClaimSettlementDate()));
			}
//		}
		txtClaimDate.setEnabled(false);
		addComponent(3, 2, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtClaimDate, gridBag, constraints, panel);

		clpan = new JLabel("CGCLAN");
		clpan.setFont(headingFont);
		clpan.setOpaque(true);
		clpan.setBackground(dataBg);
		addComponent(0, 3, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, clpan, gridBag, constraints, panel);

		txtClpan = new JTextField(new TextFormatField(11),"",10);
//		if ((flag.equals("MOD")) || (flag.equals("VER")))
//		{
			txtClpan.setText(claimApplication.getClpan());
//		}
		txtClpan.setEnabled(false);
		addComponent(1, 3, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtClpan, gridBag, constraints, panel);

		OperatingOffHeading = new JLabel("Details of Operating Office and Lending Branch");
		OperatingOffHeading.setFont(headingFont);
		OperatingOffHeading.setOpaque(true);
		OperatingOffHeading.setBackground(headingBg);
		addComponent(0, 4, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, OperatingOffHeading, gridBag, constraints, panel);

		com.cgtsi.claim.MemberInfo memberInfo = new com.cgtsi.claim.MemberInfo();
		if (claimApplication.getMemberDetails() != null)
		{
			memberInfo = claimApplication.getMemberDetails();
		}
		displayClaimsMemberInfo(memberInfo);

		addComponent(0, 5, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, memberInfoPanel, gridBag, constraints, panel);

		unitDetailsHeading = new JLabel("Borrower / Unit Details");
		unitDetailsHeading.setFont(headingFont);
		unitDetailsHeading.setOpaque(true);
		unitDetailsHeading.setBackground(headingBg);
		addComponent(0, 6, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, unitDetailsHeading, gridBag, constraints, panel);

		com.cgtsi.claim.BorrowerInfo borrowerInfo = new com.cgtsi.claim.BorrowerInfo();
		if (claimApplication.getBorrowerDetails() != null)
		{
			borrowerInfo = claimApplication.getBorrowerDetails();
		}
		displayClaimsBorrowerInfo(borrowerInfo);

		addComponent(0, 7, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, borrowerDetailsPanel, gridBag, constraints, panel);

		accStatusHeading = new JLabel("Status of Account(s)");
		accStatusHeading.setFont(headingFont);
		accStatusHeading.setOpaque(true);
		accStatusHeading.setBackground(headingBg);
		addComponent(0, 8, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, accStatusHeading, gridBag, constraints, panel);

		displayClaimsAccStatus(claimApplication, flag);

		addComponent(0, 9, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, accStatusPanel, gridBag, constraints, panel);

		legalProceedingsDetails = new JLabel("Details of Legal Proceedings");
		legalProceedingsDetails.setFont(headingFont);
		legalProceedingsDetails.setOpaque(true);
		legalProceedingsDetails.setBackground(headingBg);
		addComponent(0, 10, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, legalProceedingsDetails, gridBag, constraints, panel);

		com.cgtsi.claim.LegalProceedingsDetail legalProceedingsDetail = new com.cgtsi.claim.LegalProceedingsDetail();
		if (claimApplication.getLegalProceedingsDetails() != null)
		{
			legalProceedingsDetail = claimApplication.getLegalProceedingsDetails();
		}
		displayClaimsLegalProceedings(legalProceedingsDetail, flag, false);

		addComponent(0, 11, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, legalProceedingsPanel, gridBag, constraints, panel);

		accWrittenDetailsPanel.removeAll();
		accWrittenDetailsPanel.setBackground(dataBg);
		accWrittenDetailsPanel.setLayout(gridBag);

		whetherAccWrittenOff=new JLabel("Whether the Account has been written off from the books of the MLI (Bank / Institution)");
		whetherAccWrittenOff.setFont(headingFont);
		whetherAccWrittenOff.setOpaque(true);
		whetherAccWrittenOff.setBackground(dataBg);
		addComponent(0, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, whetherAccWrittenOff, gridBag, constraints, accWrittenDetailsPanel);

		accWrittenOffYes = new JRadioButton("Yes");
		accWrittenOffNo = new JRadioButton("No");
		accWrittenOffYes.setBackground(dataBg);
		accWrittenOffNo.setBackground(dataBg);
		accWrittenOffYes.setActionCommand("Yes");
		accWrittenOffNo.setActionCommand("No");
		accWrittenOffYes.addActionListener(new AccountWrittenOffListener());
		accWrittenOffNo.addActionListener(new AccountWrittenOffListener());
		accWrittenOffNo.setSelected(true);
		accWrittenOffValue = new ButtonGroup();
		accWrittenOffValue.add(accWrittenOffYes);
		accWrittenOffValue.add(accWrittenOffNo);
		accWrittenOffPanel = new JPanel();
		accWrittenOffPanel.add(accWrittenOffYes);
		accWrittenOffPanel.add(accWrittenOffNo);
		accWrittenOffPanel.setBackground(dataBg);
		if ((flag.equals("MOD")) || (flag.equals("VER")))
		{
			String concluded = claimApplication.getWhetherAccntWrittenOffFromBooksOfMLI();
			if (concluded.equals("Y"))
			{
				accWrittenOffYes.setSelected(true);
				accWrittenOffNo.setSelected(false);
			}
			else if (concluded.equals("N"))
			{
				accWrittenOffYes.setSelected(false);
				accWrittenOffNo.setSelected(true);
			}
		}
		addComponent(1, 0, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, accWrittenOffPanel, gridBag, constraints, accWrittenDetailsPanel);

		dtOfAccWrittenOff=new JLabel("Date on which Account was Written Off");
		dtOfAccWrittenOff.setFont(headingFont);
		dtOfAccWrittenOff.setOpaque(true);
		dtOfAccWrittenOff.setBackground(dataBg);
		addComponent(0, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, dtOfAccWrittenOff, gridBag, constraints, accWrittenDetailsPanel);

		txtDtOfAccWrittenOff = new JTextField(10);
		setDateFieldProperties(txtDtOfAccWrittenOff, "dd/MM/yyyy", false);
		if ((flag.equals("MOD")) || (flag.equals("VER")))
		{
			String dt = "";
			if ((claimApplication.getDtOnWhichAccntWrittenOff() != null) && (!claimApplication.getDtOnWhichAccntWrittenOff().equals("")))
			{
				dt = dateFormat.format(claimApplication.getDtOnWhichAccntWrittenOff());
			}
			txtDtOfAccWrittenOff.setText(dt);
		}
		addComponent(1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtDtOfAccWrittenOff, gridBag, constraints, accWrittenDetailsPanel);

		addComponent(0, 12, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, accWrittenDetailsPanel, gridBag, constraints, panel);

		Vector termDetails = new Vector();
		ArrayList cgpanInput = new ArrayList();
		int i = 0;
		if (! fileName.equals(""))
		{
			termDetails = claimApplication.getTermCapitalDtls();
		}
		else
		{
			StringTokenizer tcCgpans = new StringTokenizer(txtTCCgpansInput.getText().trim(),",");
			while (tcCgpans.hasMoreTokens())
			{
				String cgpan = "";
				cgpan = tcCgpans.nextToken();
				com.cgtsi.claim.TermLoanCapitalLoanDetail termLoanCapitalLoanDetail = new 		com.cgtsi.claim.TermLoanCapitalLoanDetail();
				termLoanCapitalLoanDetail.setCgpan(cgpan);
				cgpanInput.add(cgpan);						// to set the cgpans for recovery and claim summary details.
				termDetails.add(termLoanCapitalLoanDetail);
				termLoanCapitalLoanDetail=null;			
			}
			noOfClaimTC = termDetails.size();
		}
		if (!termDetails.isEmpty())
		{
			termCompLoanHeading=new JLabel("Term Loan (TL) / Composite Loan Details");
			termCompLoanHeading.setFont(headingFont);
			termCompLoanHeading.setOpaque(true);
			termCompLoanHeading.setBackground(headingBg);
			addComponent(0, 13, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, termCompLoanHeading, gridBag, constraints, panel);

			String response = "";
			displayClaimsTermCompLoan(termDetails, flag, false);

			addComponent(0, 14, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, termCompPanel, gridBag, constraints, panel);
		}

		ArrayList wcDetails = new ArrayList();
		if (! fileName.equals(""))
		{
			wcDetails = claimApplication.getWorkingCapitalDtls();
		}
		else
		{
			StringTokenizer wcCgpans = new StringTokenizer(txtWCCgpansInput.getText().trim(),",");
			while (wcCgpans.hasMoreTokens())
			{
				String cgpan = "";
				cgpan = wcCgpans.nextToken();
				com.cgtsi.claim.WorkingCapitalDetail workingCapitalDetail = new 		com.cgtsi.claim.WorkingCapitalDetail();
				workingCapitalDetail.setCgpan(cgpan);
				cgpanInput.add(cgpan);						// to set the cgpans for recovery and claim summary details.
				wcDetails.add(workingCapitalDetail);
				workingCapitalDetail=null;			
			}
			noOfClaimWC = wcDetails.size();
		}
		if (!wcDetails.isEmpty())
		{		
			wcLimitHeading=new JLabel("Working Capital (WC) Limit Details");
			wcLimitHeading.setFont(headingFont);
			wcLimitHeading.setOpaque(true);
			wcLimitHeading.setBackground(headingBg);
			addComponent(0, 15, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, wcLimitHeading, gridBag, constraints, panel);

			displayClaimsWCLimitDetails(wcDetails, flag, false);

			addComponent(0, 16, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, wcLimitPanel, gridBag, constraints, panel);
		
			wcReleasePanel.removeAll();
			wcReleasePanel.setBackground(dataBg);
			wcReleasePanel.setLayout(gridBag);

			dateOfRelease=new JLabel("Date of Release of WC (in case of New Borrowers)");
			dateOfRelease.setFont(headingFont);
			dateOfRelease.setOpaque(true);
			dateOfRelease.setBackground(dataBg);
			addComponent(0, 1, 2, GridBagConstraints.WEST, GridBagConstraints.BOTH, dateOfRelease, gridBag, constraints, wcReleasePanel);

			txtDateOfRelease = new JTextField(10);
			setDateFieldProperties(txtDateOfRelease, "dd/MM/yyyy", false);
//			if ((flag.equals("MOD")) || (flag.equals("VER")))
//			{
				if (claimApplication.getDateOfReleaseOfWC() != null)
				{
					txtDateOfRelease.setText(dateFormat.format(claimApplication.getDateOfReleaseOfWC()));
				}
//			}
			addComponent(2, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtDateOfRelease, gridBag, constraints, wcReleasePanel);

			addComponent(0, 17, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, wcReleasePanel, gridBag, constraints, panel);		
		}

		recoveryMadeHeading=new JLabel("Recovery Made from Borrower / Unit after Account classified as NPA");
		recoveryMadeHeading.setFont(headingFont);
		recoveryMadeHeading.setOpaque(true);
		recoveryMadeHeading.setBackground(headingBg);
		addComponent(0, 18, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, recoveryMadeHeading, gridBag, constraints, panel);

		Vector recoveryDetails = new Vector();
		if (! fileName.equals(""))
		{
			recoveryDetails = claimApplication.getRecoveryDetails();
		}
		else
		{
			int size = cgpanInput.size();
			for (i=0;i<size;i++)
			{
				com.cgtsi.claim.RecoveryDetails rec = new com.cgtsi.claim.RecoveryDetails();
				rec.setCgpan((String) cgpanInput.get(i));
				recoveryDetails.add(rec);
			}
		}
		displayClaimsRecoveryDetails(recoveryDetails, flag);

		addComponent(0, 19, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, recoveryMadePanel, gridBag, constraints, panel);

		otsRecoveryPanel.removeAll();
		otsRecoveryPanel.setBackground(dataBg);
		otsRecoveryPanel.setLayout(gridBag);

		recoveryOTS = new JLabel("If Recovery done through OTS, indicate the date of seeking approval of CGTSI for OTS.");
		recoveryOTS.setFont(headingFont);
		recoveryOTS.setOpaque(true);
		recoveryOTS.setBackground(dataBg);
		addComponent(0, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, recoveryOTS, gridBag, constraints, otsRecoveryPanel);

		txtOTSApprovalDate = new JTextField(10);
		setDateFieldProperties(txtOTSApprovalDate, "dd/MM/yyyy", false);
//		if ((flag.equals("MOD")) || (flag.equals("VER")))
//		{
			if (claimApplication.getDateOfSeekingOTS() != null)
			{
				txtOTSApprovalDate.setText(dateFormat.format(claimApplication.getDateOfSeekingOTS()));
			}
//		}
		addComponent(1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtOTSApprovalDate, gridBag, constraints, otsRecoveryPanel);

		addComponent(0, 20, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, otsRecoveryPanel, gridBag, constraints, panel);

		securityHeading=new JLabel("Security Details");
		securityHeading.setFont(headingFont);
		securityHeading.setOpaque(true);
		securityHeading.setBackground(headingBg);
		addComponent(0, 21, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, securityHeading, gridBag, constraints, panel);

		com.cgtsi.claim.SecurityAndPersonalGuaranteeDtls securityAndPersonalGuaranteeDtls = new com.cgtsi.claim.SecurityAndPersonalGuaranteeDtls();
//		if ((flag.equals("MOD")) || (flag.equals("VER")))
//		{
			 securityAndPersonalGuaranteeDtls = claimApplication.getSecurityAndPersonalGuaranteeDtls();
//		}
		displayClaimsSecurityDetails(securityAndPersonalGuaranteeDtls, flag, false);

		addComponent(0, 22, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, securityPersonalGuaranteePanel, gridBag, constraints, panel);

		claimSummaryHeading=new JLabel("Total Amount for which guarantee claim is being preferred");
		claimSummaryHeading.setFont(headingFont);
		claimSummaryHeading.setOpaque(true);
		claimSummaryHeading.setBackground(headingBg);
		addComponent(0, 23, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, claimSummaryHeading, gridBag, constraints, panel);

		ArrayList summary = new ArrayList();
		if (! fileName.equals(""))
		{
			summary = claimApplication.getClaimSummaryDtls();
		}
		else
		{
			int size = cgpanInput.size();
			for (i=0;i<size;i++)
			{
				com.cgtsi.claim.ClaimSummaryDtls claimSummaryDtls = new com.cgtsi.claim.ClaimSummaryDtls();
				claimSummaryDtls.setCgpan((String) cgpanInput.get(i));
				summary.add(claimSummaryDtls);
			}
		}
		displayClaimsSummaryDetails(summary, flag, false);

		addComponent(0, 24, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, claimSummaryPanel, gridBag, constraints, panel);

		if (key.equals(""))
		{
			key = borrowerIdInput;
		}
		lblKey = new JLabel(key);
		lblFileName = new JLabel(fileName);
		lblBrnName=new JLabel(brnName);
		lblKey.setVisible(false);
		lblFileName.setVisible(false);
		lblBrnName.setVisible(false);
		hiddenPanel=new JPanel();
		hiddenPanel.add(lblKey);
		hiddenPanel.add(lblFileName);
		hiddenPanel.add(lblBrnName);
		hiddenPanel.setVisible(false);
		addComponent(0, 25, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, hiddenPanel, gridBag, constraints, panel);

		reset=new JButton("Reset");
		reset.setActionCommand("Reset");
		reset.addActionListener(this);

		save=new JButton("Save");
		cancel=new JButton("Cancel");
		if (flag.equals("NEW"))
		{
			//System.out.println("setting action command as SaveNewClaims");
		 save.setActionCommand("SaveNewSIClaims");
		 cancel.setActionCommand("CancelNewSIClaims");
		}
		else if (flag.equals("MOD"))
		{
		 save.setActionCommand("SaveModifySIClaims");
		 cancel.setActionCommand("CancelModifyClaims");
		}
		else if (flag.equals("VER"))
		{
		 save.setActionCommand("SaveVerifySIClaims");
		 cancel.setActionCommand("CancelVerifyClaims");
		}

		save.addActionListener(this);
		cancel.addActionListener(this);
		
		print = new JButton("Print");
		print.setActionCommand("PrintClaimsSIApplication");
		print.addActionListener(this);

		buttonsPanel=new JPanel();
		buttonsPanel.setLayout(new FlowLayout());
		buttonsPanel.add(save);
		buttonsPanel.add(reset);
		buttonsPanel.add(cancel);
		buttonsPanel.add(print);
		addComponent(0, 26, GridBagConstraints.REMAINDER, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, buttonsPanel, gridBag, constraints, panel);

		container.removeAll();
		scrollPane.setViewportView(panel);
		container.add(scrollPane, BorderLayout.CENTER);
	}

	/**
	* This method displays the member details of the claims screen.
	*/
	private void displayClaimsMemberInfo(com.cgtsi.claim.MemberInfo memberInfo)
	{
		memberInfoPanel.removeAll();
		memberInfoPanel.setBackground(dataBg);
		memberInfoPanel.setLayout(gridBag);

		memberId = new JLabel("Member Id");
		memberId.setFont(headingFont);
		memberId.setOpaque(true);
		memberId.setBackground(dataBg);
		addComponent(0, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, memberId, gridBag, constraints, memberInfoPanel);

		String memId = "";
		if (memberInfo.getMemberId() != null)
		{
			memId = memberInfo.getMemberId();
		}
		memberIdValue = new JLabel(memId);
		memberIdValue.setFont(dataFont);
		memberIdValue.setOpaque(true);
		memberIdValue.setBackground(dataBg);
		addComponent(1, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, memberIdValue, gridBag, constraints, memberInfoPanel);

		lendingBranch = new JLabel("Lending Branch Name");
		lendingBranch.setFont(headingFont);
		lendingBranch.setOpaque(true);
		lendingBranch.setBackground(dataBg);
		addComponent(0, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, lendingBranch, gridBag, constraints, memberInfoPanel);

		String memBrnName = "";
		if (memberInfo.getMemberBranchName() != null)
		{
			memBrnName = memberInfo.getMemberBranchName();
		}
		lendingBranchValue = new JLabel(memBrnName);
		lendingBranchValue.setFont(dataFont);
		lendingBranchValue.setOpaque(true);
		lendingBranchValue.setBackground(dataBg);
		addComponent(1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, lendingBranchValue, gridBag, constraints, memberInfoPanel);

		city = new JLabel("City");
		city.setFont(headingFont);
		city.setOpaque(true);
		city.setBackground(dataBg);
		addComponent(2, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, city, gridBag, constraints, memberInfoPanel);

		String memCity = "";
		if (memberInfo.getCity() != null)
		{
			memCity = memberInfo.getCity();
		}
		cityValue = new JLabel(memCity);
		cityValue.setFont(dataFont);
		cityValue.setOpaque(true);
		cityValue.setBackground(dataBg);
		addComponent(3, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, cityValue, gridBag, constraints, memberInfoPanel);

		district = new JLabel("District");
		district.setFont(headingFont);
		district.setOpaque(true);
		district.setBackground(dataBg);
		addComponent(0, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, district, gridBag, constraints, memberInfoPanel);

		String memDist = "";
		if (memberInfo.getDistrict() != null)
		{
			memCity = memberInfo.getDistrict();
		}
		districtValue = new JLabel(memCity);
		districtValue.setFont(dataFont);
		districtValue.setOpaque(true);
		districtValue.setBackground(dataBg);
		addComponent(1, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, districtValue, gridBag, constraints, memberInfoPanel);

		state = new JLabel("State");
		state.setFont(headingFont);
		state.setOpaque(true);
		state.setBackground(dataBg);
		addComponent(2, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, state, gridBag, constraints, memberInfoPanel);

		String memState = "";
		if (memberInfo.getState() != null)
		{
			memState = memberInfo.getState();
		}
		stateValue = new JLabel(memState);
		stateValue.setFont(dataFont);
		stateValue.setOpaque(true);
		stateValue.setBackground(dataBg);
		addComponent(3, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, stateValue, gridBag, constraints, memberInfoPanel);

		telNo = new JLabel("Tel. No.");
		telNo.setFont(headingFont);
		telNo.setOpaque(true);
		telNo.setBackground(dataBg);
		addComponent(0, 3, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, telNo, gridBag, constraints, memberInfoPanel);

		String memTel = "";
		if ((memberInfo.getTelephone() != null) && (memberInfo.getPhoneCode() != null))
		{
			memTel = memberInfo.getPhoneCode() + "-" + memberInfo.getTelephone();
		}
		telNoValue = new JLabel(memTel);
		telNoValue.setFont(dataFont);
		telNoValue.setOpaque(true);
		telNoValue.setBackground(dataBg);
		addComponent(1, 3, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, telNoValue, gridBag, constraints, memberInfoPanel);

		email = new JLabel("E-mail");
		email.setFont(headingFont);
		email.setOpaque(true);
		email.setBackground(dataBg);
		addComponent(0, 4, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, email, gridBag, constraints, memberInfoPanel);

		String memEmail = "";
		if (memberInfo.getEmail() != null)
		{
			memEmail = memberInfo.getEmail();
		}
		emailValue = new JLabel(memEmail);
		emailValue.setFont(dataFont);
		emailValue.setOpaque(true);
		emailValue.setBackground(dataBg);
		addComponent(1, 4, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, emailValue, gridBag, constraints, memberInfoPanel);
	}

	/**
	* This method displays the borrower details of the claims screen.
	*/
	private void displayClaimsBorrowerInfo(com.cgtsi.claim.BorrowerInfo borrowerInfo)
	{
		borrowerDetailsPanel.removeAll();
		borrowerDetailsPanel.setBackground(dataBg);
		borrowerDetailsPanel.setLayout(gridBag);

		unitName = new JLabel("Name");
		unitName.setFont(headingFont);
		unitName.setOpaque(true);
		unitName.setBackground(dataBg);
		addComponent(0, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, unitName, gridBag, constraints, borrowerDetailsPanel);

		String borrowerName = "";
		if (borrowerInfo.getBorrowerName() != null)
		{
			borrowerName = borrowerInfo.getBorrowerName();
		}
		unitNameValue = new JLabel(borrowerInfo.getBorrowerName());
		unitNameValue.setFont(dataFont);
		unitNameValue.setOpaque(true);
		unitNameValue.setBackground(dataBg);
		addComponent(1, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, unitNameValue, gridBag, constraints, borrowerDetailsPanel);

		completeAddress = new JLabel("Complete Address");
		completeAddress.setFont(headingFont);
		completeAddress.setOpaque(true);
		completeAddress.setBackground(dataBg);
		addComponent(0, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, completeAddress, gridBag, constraints, borrowerDetailsPanel);

		String borrowerAdd = "";
		if (borrowerInfo.getAddress() != null)
		{
			borrowerAdd = borrowerInfo.getAddress();
		}
		completeAddressValue = new JLabel(borrowerAdd);
		completeAddressValue.setFont(dataFont);
		completeAddressValue.setOpaque(true);
		completeAddressValue.setBackground(dataBg);
		addComponent(1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, completeAddressValue, gridBag, constraints, borrowerDetailsPanel);

		district = new JLabel("District");
		district.setFont(headingFont);
		district.setOpaque(true);
		district.setBackground(dataBg);
		addComponent(0, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, district, gridBag, constraints, borrowerDetailsPanel);

		String borrowerDist = "";
		if (borrowerInfo.getDistrict() != null)
		{
			borrowerDist = borrowerInfo.getDistrict();
		}
		districtValue = new JLabel(borrowerDist);
		districtValue.setFont(dataFont);
		districtValue.setOpaque(true);
		districtValue.setBackground(dataBg);
		addComponent(1, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, districtValue, gridBag, constraints, borrowerDetailsPanel);

		state = new JLabel("State");
		state.setFont(headingFont);
		state.setOpaque(true);
		state.setBackground(dataBg);
		addComponent(2, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, state, gridBag, constraints, borrowerDetailsPanel);

		String borrowerState = "";
		if (borrowerInfo.getState() != null)
		{
			borrowerState = borrowerInfo.getState();
		}
		stateValue = new JLabel(borrowerState);
		stateValue.setFont(dataFont);
		stateValue.setOpaque(true);
		stateValue.setBackground(dataBg);
		addComponent(3, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, stateValue, gridBag, constraints, borrowerDetailsPanel);

		telNo = new JLabel("Tel. No.");
		telNo.setFont(headingFont);
		telNo.setOpaque(true);
		telNo.setBackground(dataBg);
		addComponent(0, 3, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, telNo, gridBag, constraints, borrowerDetailsPanel);

		String borrowerTel = "";
		if (borrowerInfo.getTelephone() != null)
		{
			borrowerTel = borrowerInfo.getTelephone();
		}
		telNoValue = new JLabel(borrowerTel);
		telNoValue.setFont(dataFont);
		telNoValue.setOpaque(true);
		telNoValue.setBackground(dataBg);
		addComponent(1, 3, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, telNoValue, gridBag, constraints, borrowerDetailsPanel);
	}

	/**
	* This method displays the account status details of the claims screen.
	*/
	private void displayClaimsAccStatus(com.cgtsi.claim.ClaimApplication claimApplication, String flag)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		accStatusPanel.removeAll();
		accStatusPanel.setBackground(dataBg);
		accStatusPanel.setLayout(gridBag);

		accNPADate = new JLabel("Date on which Account was classified as NPA");
		accNPADate.setFont(headingFont);
		accNPADate.setOpaque(true);
		accNPADate.setBackground(dataBg);
		addComponent(0, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, accNPADate, gridBag, constraints, accStatusPanel);

		String accDate = "";
		if ((claimApplication.getDateOnWhichAccountClassifiedNPA() != null) && (! claimApplication.getDateOnWhichAccountClassifiedNPA().equals("")))
		{
			accDate = dateFormat.format(claimApplication.getDateOnWhichAccountClassifiedNPA());
		}
		accNPADateValue = new JLabel(accDate);
		accNPADateValue.setFont(dataFont);
		accNPADateValue.setOpaque(true);
		accNPADateValue.setBackground(dataBg);
		addComponent(1, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, accNPADateValue, gridBag, constraints, accStatusPanel);

		reportingDate = new JLabel("Date of Reporting NPA to CGTSI");
		reportingDate.setFont(headingFont);
		reportingDate.setOpaque(true);
		reportingDate.setBackground(dataBg);
		addComponent(0, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, reportingDate, gridBag, constraints, accStatusPanel);

		String repDate = "";
		if ((claimApplication.getDateOfReportingNpaToCgtsi() != null) && (! claimApplication.getDateOfReportingNpaToCgtsi().equals("")))
		{
			repDate = dateFormat.format(claimApplication.getDateOfReportingNpaToCgtsi());
		}
		reportingDateValue = new JLabel(repDate);
		reportingDateValue.setFont(dataFont);
		reportingDateValue.setOpaque(true);
		reportingDateValue.setBackground(dataBg);
		addComponent(1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, reportingDateValue, gridBag, constraints, accStatusPanel);

		reasons = new JLabel("Reasons for Account Turning NPA");
		reasons.setFont(headingFont);
		reasons.setOpaque(true);
		reasons.setBackground(dataBg);
		addComponent(0, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, reasons, gridBag, constraints, accStatusPanel);

		String accReasons = "";
		if (claimApplication.getReasonsForAccountTurningNPA() != null)
		{
			accReasons = claimApplication.getReasonsForAccountTurningNPA();
		}
		reasonsValue = new JLabel(accReasons);
		reasonsValue.setFont(dataFont);
		reasonsValue.setOpaque(true);
		reasonsValue.setBackground(dataBg);
		addComponent(1, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, reasonsValue, gridBag, constraints, accStatusPanel);

		willfulDefaulter=new JLabel("Whether the Borrower is a wilful defaulter");
		willfulDefaulter.setFont(headingFont);
		willfulDefaulter.setOpaque(true);
		willfulDefaulter.setBackground(dataBg);
		addComponent(0, 3, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, willfulDefaulter, gridBag, constraints, accStatusPanel);

		willfulDefaulterYes = new JRadioButton("Yes");
		willfulDefaulterNo = new JRadioButton("No");
		willfulDefaulterYes.setBackground(dataBg);
		willfulDefaulterNo.setBackground(dataBg);
		willfulDefaulterValue = new ButtonGroup();
		willfulDefaulterValue.add(willfulDefaulterYes);
		willfulDefaulterValue.add(willfulDefaulterNo);
		willfulDefaulterPanel = new JPanel();
		willfulDefaulterPanel.add(willfulDefaulterYes);
		willfulDefaulterPanel.add(willfulDefaulterNo);
		willfulDefaulterPanel.setBackground(dataBg);
		String willfull = claimApplication.getWhetherBorrowerIsWilfulDefaulter();
		if (willfull!=null)
		{
			if (willfull.equals("Y"))
			{
				willfulDefaulterYes.setSelected(true);
				willfulDefaulterNo.setSelected(false);
			}
			else if (willfull.equals("N"))
			{
				willfulDefaulterYes.setSelected(false);
				willfulDefaulterNo.setSelected(true);
			}
		}
		addComponent(1, 3, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, willfulDefaulterPanel, gridBag, constraints, accStatusPanel);

		dateOfIssueOfRecall = new JLabel("Date of Issue of Recall Notice");
		dateOfIssueOfRecall.setFont(headingFont);
		dateOfIssueOfRecall.setOpaque(true);
		dateOfIssueOfRecall.setBackground(dataBg);
		addComponent(0, 4, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, dateOfIssueOfRecall, gridBag, constraints, accStatusPanel);

		txtDateOfIssueOfRecall = new JTextField(10);
		setDateFieldProperties(txtDateOfIssueOfRecall, "dd/MM/yyyy", false);
//		if ((flag.equals("MOD")) || (flag.equals("VER")))
//		{
			if (claimApplication.getDateOfIssueOfRecallNotice() != null)
			{
				txtDateOfIssueOfRecall.setText( dateFormat.format(claimApplication.getDateOfIssueOfRecallNotice()));
			}
//		}
		addComponent(1, 4, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtDateOfIssueOfRecall, gridBag, constraints, accStatusPanel);
	}

	/**
	* This method displays the legal proceeding details of the claims screen.
	*/
	private void displayClaimsLegalProceedings(com.cgtsi.claim.LegalProceedingsDetail legalProceedingsDetail, String flag, boolean first)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String text = "";

		legalProceedingsPanel.removeAll();
		legalProceedingsPanel.setBackground(dataBg);
		legalProceedingsPanel.setLayout(gridBag);

		forum=new JLabel("Forum through which Legal Proceedings were initiated against borrower");
		forum.setFont(headingFont);
		forum.setOpaque(true);
		forum.setBackground(dataBg);
		addComponent(0, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, forum, gridBag, constraints, legalProceedingsPanel);

		String[] forumNames = {"Select", "Civil Court", "DRT", "Lok Adalat", "Revenue Recovery Authority", "Securitisation Act, 2002", "Others"};
		cmbForumNames = new JComboBox(forumNames);
		cmbForumNames.addItemListener(new ForumNamesListener());
		addComponent(1, 0, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, cmbForumNames, gridBag, constraints, legalProceedingsPanel);

		txtForumName = new JTextField(new TextFormatField(100),"",10);
		txtForumName.setEnabled(false);
		addComponent(2, 0, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtForumName, gridBag, constraints, legalProceedingsPanel);
//		if ((flag.equals("MOD")) || (flag.equals("VER")))
//		{
			String strForum = legalProceedingsDetail.getForumRecoveryProceedingsInitiated();
//			System.out.println("forum " + strForum.equalsIgnoreCase("Securitisation Act"));
			if (strForum != null)
			{
				if (strForum.equals("Civil Court") || strForum.equals("DRT") || strForum.equals("Lok Adalat") || strForum.equals("Revenue Recovery Authority"))
				{
					cmbForumNames.setSelectedItem(strForum);
				}
				else if (strForum.equalsIgnoreCase("Securitisation Act"))
				{
					cmbForumNames.setSelectedItem("Securitisation Act, 2002");
				}
				else
				{
					cmbForumNames.setSelectedItem("Others");
					txtForumName.setText(strForum);
				}
			}
//		}

		suitRegNo=new JLabel("Suit / Case Registration Number");
		suitRegNo.setFont(headingFont);
		suitRegNo.setOpaque(true);
		suitRegNo.setBackground(dataBg);
		addComponent(0, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, suitRegNo, gridBag, constraints, legalProceedingsPanel);

		txtSuitCaseRegNo = new JTextField(new TextFormatField(50),"",10);
//		txtSuitCaseRegNo.setDocument(new NumberFormatField(10));
//		if ((flag.equals("MOD")) || (flag.equals("VER")))
//		{
			if (legalProceedingsDetail.getSuitCaseRegNumber()!=null)
			{
				txtSuitCaseRegNo.setText(legalProceedingsDetail.getSuitCaseRegNumber());
			}
//		}
		addComponent(1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtSuitCaseRegNo, gridBag, constraints, legalProceedingsPanel);

		date=new JLabel("Date");
		date.setFont(headingFont);
		date.setOpaque(true);
		date.setBackground(dataBg);
//		addComponent(2, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, date, gridBag, constraints, legalProceedingsPanel);

		txtLegalProcDate = new JTextField(10);
		setDateFieldProperties(txtLegalProcDate, "dd/MM/yyyy", false);
//		if ((flag.equals("MOD")) || (flag.equals("VER")))
//		{
			if (legalProceedingsDetail.getFilingDate() != null)
			{
				txtLegalProcDate.setText(dateFormat.format(legalProceedingsDetail.getFilingDate()));
			}
//		}
//		addComponent(3, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtLegalProcDate, gridBag, constraints, legalProceedingsPanel);
		
		grpPanel = new JPanel();
		grpPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		grpPanel.setBackground(dataBg);
		grpPanel.add(date);
		grpPanel.add(txtLegalProcDate);
		addComponent(2, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, grpPanel, gridBag, constraints, legalProceedingsPanel);

		forumName=new JLabel("Name of the Forum and Location");
		forumName.setFont(headingFont);
		forumName.setOpaque(true);
		forumName.setBackground(dataBg);
		addComponent(0, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, forumName, gridBag, constraints, legalProceedingsPanel);

		txtForumNameLoc = new JTextField(new TextFormatField(50),"",10);
//		if ((flag.equals("MOD")) || (flag.equals("VER")))
//		{
			if (legalProceedingsDetail.getNameOfForum()!=null)
			{
				txtForumNameLoc.setText(legalProceedingsDetail.getNameOfForum());
			}
//		}
		addComponent(1, 2, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtForumNameLoc, gridBag, constraints, legalProceedingsPanel);

		forumLocation=new JLabel("Location");
		forumLocation.setFont(headingFont);
		forumLocation.setOpaque(true);
		forumLocation.setBackground(dataBg);
//		addComponent(2, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, forumLocation, gridBag, constraints, legalProceedingsPanel);

		txtForumLocation = new JTextField(new TextFormatField(50),"",10);
//		if ((flag.equals("MOD")) || (flag.equals("VER")))
//		{
			if (legalProceedingsDetail.getLocation()!=null)
			{
				txtForumLocation.setText(legalProceedingsDetail.getLocation());
			}
//		}
//		addComponent(3, 2, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtForumLocation, gridBag, constraints, legalProceedingsPanel);
		
		grpPanel = new JPanel();
		grpPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		grpPanel.setBackground(dataBg);
		grpPanel.add(forumLocation);
		grpPanel.add(txtForumLocation);
		addComponent(2, 2, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, grpPanel, gridBag, constraints, legalProceedingsPanel);

		amtClaimed=new JLabel("Amount Claimed");
		amtClaimed.setFont(headingFont);
		amtClaimed.setOpaque(true);
		amtClaimed.setBackground(dataBg);
		addComponent(0, 3, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, amtClaimed, gridBag, constraints, legalProceedingsPanel);

		txtAmtClaimed = new JTextField(10);
		txtAmtClaimed.setDocument(new DecimalFormatField(13,2));
//		if ((flag.equals("MOD")) || (flag.equals("VER")))
//		{
				text = df.format(legalProceedingsDetail.getAmountClaimed());
				txtAmtClaimed.setText(text);
//		}
//		addComponent(1, 3, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtAmtClaimed, gridBag, constraints, legalProceedingsPanel);
		
		inRs = new JLabel("in Rs.");
		inRs.setFont(hintFont);
		inRs.setOpaque(true);
		inRs.setBackground(dataBg);
		inRs.setHorizontalTextPosition(SwingConstants.LEFT);		
//		addComponent(2, 3, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, inRs, gridBag, constraints, legalProceedingsPanel);*/

		grpPanel = new JPanel();
		grpPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		grpPanel.setBackground(dataBg);
		grpPanel.add(txtAmtClaimed);
		grpPanel.add(inRs);
		addComponent(1, 3, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, grpPanel, gridBag, constraints, legalProceedingsPanel);


		currentStatus=new JLabel("Current Status / Remarks");
		currentStatus.setFont(headingFont);
		currentStatus.setOpaque(true);
		currentStatus.setBackground(dataBg);
		addComponent(0, 4, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, currentStatus, gridBag, constraints, legalProceedingsPanel);

		txtCurrentStatus = new JTextArea(2, 20);
		txtCurrentStatus.setLineWrap(true);
		JScrollPane txtCurrentStatusPane = new JScrollPane(txtCurrentStatus, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//		if ((flag.equals("MOD")) || (flag.equals("VER")))
//		{
			if (legalProceedingsDetail.getCurrentStatusRemarks()!=null)
			{
				txtCurrentStatus.setText(legalProceedingsDetail.getCurrentStatusRemarks());
			}
//		}
		addComponent(1, 4, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtCurrentStatusPane, gridBag, constraints, legalProceedingsPanel);

		proceedingsConcluded=new JLabel("Whether recovery proceedings have concluded");
		proceedingsConcluded.setFont(headingFont);
		proceedingsConcluded.setOpaque(true);
		proceedingsConcluded.setBackground(dataBg);
		addComponent(0, 5, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, proceedingsConcluded, gridBag, constraints, legalProceedingsPanel);

		recoveryProcConcludedYes = new JRadioButton("Yes");
		recoveryProcConcludedNo = new JRadioButton("No");
		recoveryProcConcludedYes.setBackground(dataBg);
		recoveryProcConcludedNo.setBackground(dataBg);
		recoveryProcConcludedNo.setSelected(true);
		recoveryProcConcludedValue = new ButtonGroup();
		recoveryProcConcludedValue.add(recoveryProcConcludedYes);
		recoveryProcConcludedValue.add(recoveryProcConcludedNo);
		recoveryProcConcludedPanel = new JPanel();
		recoveryProcConcludedPanel.add(recoveryProcConcludedYes);
		recoveryProcConcludedPanel.add(recoveryProcConcludedNo);
		recoveryProcConcludedPanel.setBackground(dataBg);
//		if ((flag.equals("MOD")) || (flag.equals("VER")))
//		{
			String concluded = legalProceedingsDetail.getIsRecoveryProceedingsConcluded();
			if (concluded != null)
			{
				if (concluded.equals("Y"))
				{
					recoveryProcConcludedYes.setSelected(true);
					recoveryProcConcludedNo.setSelected(false);
				}
				else if (concluded.equals("N"))
				{
					recoveryProcConcludedYes.setSelected(false);
					recoveryProcConcludedNo.setSelected(true);
				}
			}
//		}
		addComponent(1, 5, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, recoveryProcConcludedPanel, gridBag, constraints, legalProceedingsPanel);

		if (! first)
		{
			dtOfConclusion=new JLabel("Date of Conclusion of Recovery Proceedings");
			dtOfConclusion.setFont(headingFont);
			dtOfConclusion.setOpaque(true);
			dtOfConclusion.setBackground(dataBg);
			addComponent(0, 6, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, dtOfConclusion, gridBag, constraints, legalProceedingsPanel);

			txtConclusionDate = new JTextField(10);
			setDateFieldProperties(txtConclusionDate, "dd/MM/yyyy", false);
//			if ((flag.equals("MOD")) || (flag.equals("VER")))
//			{
				if (legalProceedingsDetail.getDateOfConclusionOfRecoveryProceedings() != null)
				{
					txtConclusionDate.setText( dateFormat.format(legalProceedingsDetail.getDateOfConclusionOfRecoveryProceedings()));
				}
//			}
			addComponent(1, 6, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, txtConclusionDate, gridBag, constraints, legalProceedingsPanel);
		}
	}

	/**
	* This method displays the term / composite loan details of the claims screen.
	* the boolean parameter indicates whether the claim is for the first or econd installment.
	* true - first installment
	* flase - second installment
	*/
	private void displayClaimsTermCompLoan(Vector termDetails, String flag, boolean first)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String text = "";

		termCompPanel.removeAll();
		termCompPanel.setBackground(dataBg);
		termCompPanel.setLayout(gridBag);

		slNo=new JLabel("Sl No", SwingConstants.CENTER);
		slNo.setFont(headingFont);
		slNo.setOpaque(true);
		slNo.setBackground(headingBg);
		slNo.setVerticalAlignment(JLabel.TOP);
		if (first)
		{
			constraints.gridheight=4;
		}
		else
		{
			constraints.gridheight=5;
		}
		addComponent(0, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, slNo, gridBag, constraints, termCompPanel);

		cgpanCol=new JLabel("CGPAN", SwingConstants.CENTER);
		cgpanCol.setFont(headingFont);
		cgpanCol.setOpaque(true);
		cgpanCol.setBackground(headingBg);
		cgpanCol.setVerticalAlignment(JLabel.TOP);
		addComponent(1, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, cgpanCol, gridBag, constraints, termCompPanel);

		dateOfLastDisbursementCol=new JLabel("Date of Last", SwingConstants.CENTER);
		dateOfLastDisbursementCol.setFont(headingFont);
		dateOfLastDisbursementCol.setOpaque(true);
		dateOfLastDisbursementCol.setBackground(headingBg);
		dateOfLastDisbursementCol.setVerticalAlignment(JLabel.TOP);
		constraints.gridheight=1;
		addComponent(2, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, dateOfLastDisbursementCol, gridBag, constraints, termCompPanel);

		repaymentCol=new JLabel("Repayment (in Rs.)", SwingConstants.CENTER);
		repaymentCol.setFont(headingFont);
		repaymentCol.setOpaque(true);
		repaymentCol.setBackground(headingBg);
		repaymentCol.setVerticalAlignment(JLabel.TOP);
		constraints.gridheight=2;
		addComponent(3, 0, 2, GridBagConstraints.WEST, GridBagConstraints.BOTH, repaymentCol, gridBag, constraints, termCompPanel);

		outstandingOnNpaCol=new JLabel("Outstanding", SwingConstants.CENTER);
		outstandingOnNpaCol.setFont(headingFont);
		outstandingOnNpaCol.setOpaque(true);
		outstandingOnNpaCol.setBackground(headingBg);
		constraints.gridheight=1;
		addComponent(5, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, outstandingOnNpaCol, gridBag, constraints, termCompPanel);

		outstandingInCaseCol=new JLabel("Outstanding stated", SwingConstants.CENTER);
		outstandingInCaseCol.setFont(headingFont);
		outstandingInCaseCol.setOpaque(true);
		outstandingInCaseCol.setBackground(headingBg);
		constraints.gridheight=1;
		addComponent(6, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, outstandingInCaseCol, gridBag, constraints, termCompPanel);

		outstandingOnLodgementCol=new JLabel("Outstanding As On", SwingConstants.CENTER);
		outstandingOnLodgementCol.setFont(headingFont);
		outstandingOnLodgementCol.setOpaque(true);
		outstandingOnLodgementCol.setBackground(headingBg);
		addComponent(7, 0, 2, GridBagConstraints.WEST, GridBagConstraints.BOTH, outstandingOnLodgementCol, gridBag, constraints, termCompPanel);

		dateOfLastDisbursementCol1=new JLabel("Disbursement", SwingConstants.CENTER);
		dateOfLastDisbursementCol1.setFont(headingFont);
		dateOfLastDisbursementCol1.setOpaque(true);
		dateOfLastDisbursementCol1.setBackground(headingBg);
		dateOfLastDisbursementCol1.setVerticalAlignment(JLabel.TOP);
		if (first)
		{
			constraints.gridheight=3;
		}
		else
		{
			constraints.gridheight=4;
		}
		addComponent(2, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, dateOfLastDisbursementCol1, gridBag, constraints, termCompPanel);

		outstandingOnNpaCol1=new JLabel("As On Date", SwingConstants.CENTER);
		outstandingOnNpaCol1.setFont(headingFont);
		outstandingOnNpaCol1.setOpaque(true);
		outstandingOnNpaCol1.setBackground(headingBg);
		outstandingOnNpaCol1.setVerticalAlignment(JLabel.TOP);
		constraints.gridheight = 1;
		addComponent(5, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, outstandingOnNpaCol1, gridBag, constraints, termCompPanel);

		outstandingInCaseCol1=new JLabel("in the Civil Suit /", SwingConstants.CENTER);
		outstandingInCaseCol1.setFont(headingFont);
		outstandingInCaseCol1.setOpaque(true);
		outstandingInCaseCol1.setBackground(headingBg);
		constraints.gridheight=1;
		addComponent(6, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, outstandingInCaseCol1, gridBag, constraints, termCompPanel);

		outstandingOnLodgementCol1=new JLabel("Date of Lodgement", SwingConstants.CENTER);
		outstandingOnLodgementCol1.setFont(headingFont);
		outstandingOnLodgementCol1.setOpaque(true);
		outstandingOnLodgementCol1.setBackground(headingBg);
		constraints.gridheight=1;
		addComponent(7, 1, 2, GridBagConstraints.WEST, GridBagConstraints.BOTH, outstandingOnLodgementCol1, gridBag, constraints, termCompPanel);

		principalCol=new JLabel("Principal", SwingConstants.CENTER);
		principalCol.setFont(headingFont);
		principalCol.setOpaque(true);
		principalCol.setBackground(headingBg);
		principalCol.setVerticalAlignment(JLabel.TOP);
		if (first)
		{
			constraints.gridheight=2;
		}
		else
		{
			constraints.gridheight=3;
		}
		addComponent(3, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, principalCol, gridBag, constraints, termCompPanel);

		interestCol=new JLabel("Interest and", SwingConstants.CENTER);
		interestCol.setFont(headingFont);
		interestCol.setOpaque(true);
		interestCol.setBackground(headingBg);
		constraints.gridheight=1;
		addComponent(4, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, interestCol, gridBag, constraints, termCompPanel);

		outstandingOnNpaCol2=new JLabel("of NPA (in Rs.) #", SwingConstants.CENTER);
		outstandingOnNpaCol2.setFont(headingFont);
		outstandingOnNpaCol2.setOpaque(true);
		outstandingOnNpaCol2.setBackground(headingBg);
		outstandingOnNpaCol2.setVerticalAlignment(JLabel.TOP);
		constraints.gridheight=2;
		addComponent(5, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, outstandingOnNpaCol2, gridBag, constraints, termCompPanel);

		outstandingInCaseCol2=new JLabel("Case filed", SwingConstants.CENTER);
		outstandingInCaseCol2.setFont(headingFont);
		outstandingInCaseCol2.setOpaque(true);
		outstandingInCaseCol2.setBackground(headingBg);
		constraints.gridheight=1;
		addComponent(6, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, outstandingInCaseCol2, gridBag, constraints, termCompPanel);

		outstandingOnLodgementCol2=new JLabel("of claim (in Rs.) #", SwingConstants.CENTER);
		outstandingOnLodgementCol2.setFont(headingFont);
		outstandingOnLodgementCol2.setOpaque(true);
		outstandingOnLodgementCol2.setBackground(headingBg);
		outstandingOnLodgementCol2.setVerticalAlignment(JLabel.TOP);
		if (first)
		{
			constraints.gridheight=2;
		}
		else
		{
			constraints.gridheight=1;
		}
		addComponent(7, 2, 2, GridBagConstraints.WEST, GridBagConstraints.BOTH, outstandingOnLodgementCol2, gridBag, constraints, termCompPanel);

		interestCol1=new JLabel("Other Charges", SwingConstants.CENTER);
		interestCol1.setFont(headingFont);
		interestCol1.setOpaque(true);
		interestCol1.setBackground(headingBg);
		if (first)
		{
			constraints.gridheight=1;
		}
		else
		{
			constraints.gridheight=2;
		}
		addComponent(4, 3, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, interestCol1, gridBag, constraints, termCompPanel);

		outstandingInCaseCol3=new JLabel("(in Rs.) #", SwingConstants.CENTER);
		outstandingInCaseCol3.setFont(headingFont);
		outstandingInCaseCol3.setOpaque(true);
		outstandingInCaseCol3.setBackground(headingBg);
		if (first)
		{
			constraints.gridheight=1;
		}
		else
		{
			constraints.gridheight=2;
			outstandingInCaseCol3.setVerticalAlignment(JLabel.TOP);
		}
		addComponent(6, 3, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, outstandingInCaseCol3, gridBag, constraints, termCompPanel);

		if (!first)
		{
			firstInstallmentCol=new JLabel("First", SwingConstants.CENTER);
			firstInstallmentCol.setFont(headingFont);
			firstInstallmentCol.setOpaque(true);
			firstInstallmentCol.setBackground(headingBg);
			constraints.gridheight=1;
			addComponent(7, 3, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, firstInstallmentCol, gridBag, constraints, termCompPanel);

			secondInstallmentCol=new JLabel("Second", SwingConstants.CENTER);
			secondInstallmentCol.setFont(headingFont);
			secondInstallmentCol.setOpaque(true);
			secondInstallmentCol.setBackground(headingBg);
			addComponent(8, 3, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, secondInstallmentCol, gridBag, constraints, termCompPanel);

			firstInstallmentCol1=new JLabel("Instalment", SwingConstants.CENTER);
			firstInstallmentCol1.setFont(headingFont);
			firstInstallmentCol1.setOpaque(true);
			firstInstallmentCol1.setBackground(headingBg);
			addComponent(7, 4, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, firstInstallmentCol1, gridBag, constraints, termCompPanel);

			secondInstallmentCol1=new JLabel("Instalment", SwingConstants.CENTER);
			secondInstallmentCol1.setFont(headingFont);
			secondInstallmentCol1.setOpaque(true);
			secondInstallmentCol1.setBackground(headingBg);
			addComponent(8, 4, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, secondInstallmentCol1, gridBag, constraints, termCompPanel);

		}

		int termSize= termDetails.size();
		noOfClaimTC = termSize;
		int i=0;
		com.cgtsi.claim.TermLoanCapitalLoanDetail termLoanCapitalLoanDetail;

		slNoValue=new JLabel[termSize];
		cgpanColValueTC=new JLabel[termSize];
		txtDateOfLastDisbursementCol=new JTextField[termSize];
		txtPrincipal=new JTextField[termSize];
		txtInterest=new JTextField[termSize];
		txtOutstandingOnNpaTC=new JTextField[termSize];
		txtOutstandingInCaseTC=new JTextField[termSize];
		txtOutstandingOnLodgementTC=new JTextField[termSize];

		if (! first)
		{
			txtOutstandingOnLodgementTCSecond=new JTextField[termSize];
		}

		int rowNo=5;

		for (i=0;i<termSize;i++)
		{
			termLoanCapitalLoanDetail = (com.cgtsi.claim.TermLoanCapitalLoanDetail) termDetails.get(i);

			slNoValue[i] = new JLabel(""+(i+1), SwingConstants.CENTER);
			slNoValue[i].setFont(dataFont);
			slNoValue[i].setOpaque(true);
			slNoValue[i].setBackground(dataBg);
			addComponent(0, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, slNoValue[i], gridBag, constraints, termCompPanel);

			cgpanColValueTC[i] = new JLabel(termLoanCapitalLoanDetail.getCgpan(), SwingConstants.CENTER);
			cgpanColValueTC[i].setFont(dataFont);
			cgpanColValueTC[i].setOpaque(true);
			cgpanColValueTC[i].setBackground(dataBg);
			addComponent(1, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, cgpanColValueTC[i], gridBag, constraints, termCompPanel);

			txtDateOfLastDisbursementCol[i] = new JTextField(6);
			setDateFieldProperties(txtDateOfLastDisbursementCol[i], "dd/MM/yyyy", false);
//			if ((flag.equals("MOD")) || (flag.equals("VER")))
//			{
				if (termLoanCapitalLoanDetail.getLastDisbursementDate()!= null)
				{
					txtDateOfLastDisbursementCol[i].setText( dateFormat.format(termLoanCapitalLoanDetail.getLastDisbursementDate()));
				}
//			}
			addComponent(2, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtDateOfLastDisbursementCol[i], gridBag, constraints, termCompPanel);

			txtPrincipal[i] = new JTextField(8);
			txtPrincipal[i].setDocument(new DecimalFormatField(13,2));
//			if ((flag.equals("MOD")) || (flag.equals("VER")))
//			{
				text = df.format(termLoanCapitalLoanDetail.getPrincipalRepayment());
				txtPrincipal[i].setText(text);
//			}
			addComponent(3, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtPrincipal[i], gridBag, constraints, termCompPanel);

			txtInterest[i] = new JTextField(6);
			txtInterest[i].setDocument(new DecimalFormatField(13,2));
//			if ((flag.equals("MOD")) || (flag.equals("VER")))
//			{
				text = df.format(termLoanCapitalLoanDetail.getInterestAndOtherCharges());
				txtInterest[i].setText(text);
//			}
			addComponent(4, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtInterest[i], gridBag, constraints, termCompPanel);

			txtOutstandingOnNpaTC[i] = new JTextField(8);
			txtOutstandingOnNpaTC[i].setDocument(new DecimalFormatField(13,2));
//			if ((flag.equals("MOD")) || (flag.equals("VER")))
//			{
				text = df.format(termLoanCapitalLoanDetail.getOutstandingAsOnDateOfNPA());
				txtOutstandingOnNpaTC[i].setText(text);
//			}
			addComponent(5, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtOutstandingOnNpaTC[i], gridBag, constraints, termCompPanel);

			txtOutstandingInCaseTC[i] = new JTextField(8);
			txtOutstandingInCaseTC[i].setDocument(new DecimalFormatField(13,2));
//			if ((flag.equals("MOD")) || (flag.equals("VER")))
//			{
				text = df.format(termLoanCapitalLoanDetail.getOutstandingStatedInCivilSuit());
				txtOutstandingInCaseTC[i].setText(text);
///			}
			addComponent(6, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtOutstandingInCaseTC[i], gridBag, constraints, termCompPanel);

			txtOutstandingOnLodgementTC[i] = new JTextField(8);
			txtOutstandingOnLodgementTC[i].setDocument(new DecimalFormatField(13,2));
//			if ((flag.equals("MOD")) || (flag.equals("VER")))
//			{
				text = df.format(termLoanCapitalLoanDetail.getOutstandingAsOnDateOfLodgement());
				txtOutstandingOnLodgementTC[i].setText(text);
//			}
			addComponent(7, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtOutstandingOnLodgementTC[i], gridBag, constraints, termCompPanel);

			if (! first)
			{
				txtOutstandingOnLodgementTCSecond[i] = new JTextField(8);
				txtOutstandingOnLodgementTCSecond[i].setDocument(new DecimalFormatField(13,2));
//				if ((flag.equals("MOD")) || (flag.equals("VER")))
//				{
					text = df.format(termLoanCapitalLoanDetail.getOsAsOnDateOfLodgementOfClmForSecInstllmnt());
					txtOutstandingOnLodgementTCSecond[i].setText(text);
//				}
				addComponent(8, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtOutstandingOnLodgementTCSecond[i], gridBag, constraints, termCompPanel);
			}

			rowNo++;
		}
		osTermCompHint = new JLabel("# - Mention only Principal Outstanding");
		osTermCompHint.setFont(hintFont);
		osTermCompHint.setOpaque(true);
		osTermCompHint.setBackground(dataBg);
		addComponent(0, rowNo, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, osTermCompHint, gridBag, constraints, termCompPanel);
	}

	/**
	* This method displays the working capital limit detials of the claims screen.
	*/
	private void displayClaimsWCLimitDetails(ArrayList wcDetails, String flag, boolean first)
	{
		String text = "";

		wcLimitPanel.removeAll();
		wcLimitPanel.setBackground(dataBg);
		wcLimitPanel.setLayout(gridBag);

		slNo=new JLabel("Sl No", SwingConstants.CENTER);
		slNo.setFont(headingFont);
		slNo.setOpaque(true);
		slNo.setBackground(headingBg);
		if (first)
		{
			constraints.gridheight=2;
		}
		else
		{
			constraints.gridheight=4;
		}
		slNo.setVerticalAlignment(JLabel.TOP);
		addComponent(0, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, slNo, gridBag, constraints, wcLimitPanel);

		cgpanCol=new JLabel("CGPAN", SwingConstants.CENTER);
		cgpanCol.setFont(headingFont);
		cgpanCol.setOpaque(true);
		cgpanCol.setBackground(headingBg);
		cgpanCol.setVerticalAlignment(JLabel.TOP);
		addComponent(1, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, cgpanCol, gridBag, constraints, wcLimitPanel);

		outstandingOnNpaCol=new JLabel("Outstanding As On Date", SwingConstants.CENTER);
		outstandingOnNpaCol.setFont(headingFont);
		outstandingOnNpaCol.setOpaque(true);
		outstandingOnNpaCol.setBackground(headingBg);
		constraints.gridheight=1;
		addComponent(2, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, outstandingOnNpaCol, gridBag, constraints, wcLimitPanel);

		outstandingInCaseCol=new JLabel("Outstanding stated in the Civil", SwingConstants.CENTER);
		outstandingInCaseCol.setFont(headingFont);
		outstandingInCaseCol.setOpaque(true);
		outstandingInCaseCol.setBackground(headingBg);
		addComponent(3, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, outstandingInCaseCol, gridBag, constraints, wcLimitPanel);

		outstandingOnLodgementCol=new JLabel("Outstanding As On Date of", SwingConstants.CENTER);
		outstandingOnLodgementCol.setFont(headingFont);
		outstandingOnLodgementCol.setOpaque(true);
		outstandingOnLodgementCol.setBackground(headingBg);
		addComponent(4, 0, 2, GridBagConstraints.WEST, GridBagConstraints.BOTH, outstandingOnLodgementCol, gridBag, constraints, wcLimitPanel);

		outstandingOnNpaCol1=new JLabel("of NPA (in Rs.) $", SwingConstants.CENTER);
		outstandingOnNpaCol1.setFont(headingFont);
		outstandingOnNpaCol1.setOpaque(true);
		outstandingOnNpaCol1.setBackground(headingBg);
		if (first)
		{
			constraints.gridheight=1;
		}
		else
		{
			constraints.gridheight=3;
		}
		outstandingOnNpaCol1.setVerticalAlignment(JLabel.TOP);
		addComponent(2, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, outstandingOnNpaCol1, gridBag, constraints, wcLimitPanel);

		outstandingInCaseCol1=new JLabel(" Suit / Case (in Rs.) $", SwingConstants.CENTER);
		outstandingInCaseCol1.setFont(headingFont);
		outstandingInCaseCol1.setOpaque(true);
		outstandingInCaseCol1.setBackground(headingBg);
		outstandingInCaseCol1.setVerticalAlignment(JLabel.TOP);
		addComponent(3, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, outstandingInCaseCol1, gridBag, constraints, wcLimitPanel);

		outstandingOnLodgementCol1=new JLabel("Lodgement of Claim (in Rs.) $", SwingConstants.CENTER);
		outstandingOnLodgementCol1.setFont(headingFont);
		outstandingOnLodgementCol1.setOpaque(true);
		outstandingOnLodgementCol1.setBackground(headingBg);
		constraints.gridheight=1;
		addComponent(4, 1, 2, GridBagConstraints.WEST, GridBagConstraints.BOTH, outstandingOnLodgementCol1, gridBag, constraints, wcLimitPanel);

		if (! first)
		{
			firstInstallmentCol=new JLabel("First", SwingConstants.CENTER);
			firstInstallmentCol.setFont(headingFont);
			firstInstallmentCol.setOpaque(true);
			firstInstallmentCol.setBackground(headingBg);
			addComponent(4, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, firstInstallmentCol, gridBag, constraints, wcLimitPanel);

			secondInstallmentCol=new JLabel("Second", SwingConstants.CENTER);
			secondInstallmentCol.setFont(headingFont);
			secondInstallmentCol.setOpaque(true);
			secondInstallmentCol.setBackground(headingBg);
			addComponent(5, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, secondInstallmentCol, gridBag, constraints, wcLimitPanel);

			firstInstallmentCol1=new JLabel("Instalment", SwingConstants.CENTER);
			firstInstallmentCol1.setFont(headingFont);
			firstInstallmentCol1.setOpaque(true);
			firstInstallmentCol1.setBackground(headingBg);
			addComponent(4, 3, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, firstInstallmentCol1, gridBag, constraints, wcLimitPanel);

			secondInstallmentCol1=new JLabel("Instalment", SwingConstants.CENTER);
			secondInstallmentCol1.setFont(headingFont);
			secondInstallmentCol1.setOpaque(true);
			secondInstallmentCol1.setBackground(headingBg);
			addComponent(5, 3, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, secondInstallmentCol1, gridBag, constraints, wcLimitPanel);

		}

		int wcSize=wcDetails.size();
		noOfClaimWC = wcSize;
		int i=0;
		com.cgtsi.claim.WorkingCapitalDetail workingCapitalDetail;

		slNoValue=new JLabel[wcSize];
		cgpanColValueWC=new JLabel[wcSize];
		txtOutstandingOnNpaWC=new JTextField[wcSize];
		txtOutstandingInCaseWC=new JTextField[wcSize];
		txtOutstandingOnLodegementWC=new JTextField[wcSize];

		if (! first)
		{
			txtOutstandingOnLodegementWCSecond=new JTextField[wcSize];
		}

		int rowNo=4;

		for (i=0;i<wcSize;i++)
		{
			workingCapitalDetail = (com.cgtsi.claim.WorkingCapitalDetail) wcDetails.get(i);

			slNoValue[i] = new JLabel(""+(i+1), SwingConstants.CENTER);
			slNoValue[i].setFont(dataFont);
			slNoValue[i].setOpaque(true);
			slNoValue[i].setBackground(dataBg);
			addComponent(0, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, slNoValue[i], gridBag, constraints, wcLimitPanel);

			cgpanColValueWC[i] = new JLabel(workingCapitalDetail.getCgpan(), SwingConstants.CENTER);
			cgpanColValueWC[i].setFont(dataFont);
			cgpanColValueWC[i].setOpaque(true);
			cgpanColValueWC[i].setBackground(dataBg);
			addComponent(1, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, cgpanColValueWC[i], gridBag, constraints, wcLimitPanel);

			txtOutstandingOnNpaWC[i] = new JTextField(10);
			txtOutstandingOnNpaWC[i].setDocument(new DecimalFormatField(13,2));
//			if ((flag.equals("MOD")) || (flag.equals("VER")))
//			{
				text = df.format(workingCapitalDetail.getOutstandingAsOnDateOfNPA());
				txtOutstandingOnNpaWC[i].setText(text);
//			}
			addComponent(2, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtOutstandingOnNpaWC[i], gridBag, constraints, wcLimitPanel);

			txtOutstandingInCaseWC[i] = new JTextField(10);
			txtOutstandingInCaseWC[i].setDocument(new DecimalFormatField(13,2));
//			if ((flag.equals("MOD")) || (flag.equals("VER")))
//			{
				text = df.format(workingCapitalDetail.getOutstandingStatedInCivilSuit());
				txtOutstandingInCaseWC[i].setText(text);
//			}
			addComponent(3, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtOutstandingInCaseWC[i], gridBag, constraints, wcLimitPanel);

			txtOutstandingOnLodegementWC[i] = new JTextField(10);
			txtOutstandingOnLodegementWC[i].setDocument(new DecimalFormatField(13,2));
//			if ((flag.equals("MOD")) || (flag.equals("VER")))
//			{
				text = df.format(workingCapitalDetail.getOutstandingAsOnDateOfLodgement());
				txtOutstandingOnLodegementWC[i].setText(text);
//			}
			addComponent(4, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtOutstandingOnLodegementWC[i], gridBag, constraints, wcLimitPanel);

			if (! first)
			{
				txtOutstandingOnLodegementWCSecond[i] = new JTextField(10);
				txtOutstandingOnLodegementWCSecond[i].setDocument(new DecimalFormatField(13,2));
//				if ((flag.equals("MOD")) || (flag.equals("VER")))
//				{
					text = df.format(workingCapitalDetail.getOsAsOnDateOfLodgementOfClmForSecInstllmnt());
					txtOutstandingOnLodegementWCSecond[i].setText(text);
//				}
				addComponent(5, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtOutstandingOnLodegementWCSecond[i], gridBag, constraints, wcLimitPanel);
			}

			rowNo++;
		}

		osWCLimitHint = new JLabel("$ - Mention Amount Including Interest");
		osWCLimitHint.setFont(hintFont);
		osWCLimitHint.setOpaque(true);
		osWCLimitHint.setBackground(dataBg);
		addComponent(0, rowNo, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, osWCLimitHint, gridBag, constraints, wcLimitPanel);

		rowNo++;

	}

	/**
	* This method displays the security and personal guarantee details of the claims screen.
	*/
	private void displayClaimsSecurityDetails(com.cgtsi.claim.SecurityAndPersonalGuaranteeDtls securityAndPersonalGuaranteeDtls, String flag, boolean first)
	{
		String text = "";

		securityPersonalGuaranteePanel.removeAll();
		securityPersonalGuaranteePanel.setBackground(dataBg);
		securityPersonalGuaranteePanel.setLayout(gridBag);

		particularsCol=new JLabel("Particulars", SwingConstants.CENTER);
		particularsCol.setFont(headingFont);
		particularsCol.setOpaque(true);
		particularsCol.setBackground(headingBg);
		constraints.gridheight=3;
		addComponent(0, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, particularsCol, gridBag, constraints, securityPersonalGuaranteePanel);

		securityCol=new JLabel("Security", SwingConstants.CENTER);
		securityCol.setFont(headingFont);
		securityCol.setOpaque(true);
		securityCol.setBackground(headingBg);
		constraints.gridheight=2;
		addComponent(1, 0, 2, GridBagConstraints.WEST, GridBagConstraints.BOTH, securityCol, gridBag, constraints, securityPersonalGuaranteePanel);

		if (! first)
		{
			amtRealisedSec=new JLabel("Amount Realised", SwingConstants.CENTER);
			amtRealisedSec.setFont(headingFont);
			amtRealisedSec.setOpaque(true);
			amtRealisedSec.setBackground(headingBg);
			constraints.gridheight=1;
			addComponent(3, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, amtRealisedSec, gridBag, constraints, securityPersonalGuaranteePanel);
		}
		
		networthCol=new JLabel("Networth of", SwingConstants.CENTER);
		networthCol.setFont(headingFont);
		networthCol.setOpaque(true);
		networthCol.setBackground(headingBg);
		constraints.gridheight=1;
		addComponent(4, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, networthCol, gridBag, constraints, securityPersonalGuaranteePanel);

		if (! first)
		{
			amtRealisedPg=new JLabel("Amount Realised", SwingConstants.CENTER);
			amtRealisedPg.setFont(headingFont);
			amtRealisedPg.setOpaque(true);
			amtRealisedPg.setBackground(headingBg);
			constraints.gridheight=1;
			addComponent(5, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, amtRealisedPg, gridBag, constraints, securityPersonalGuaranteePanel);
		}

		reasonsForReductionCol=new JLabel("Reasons for ", SwingConstants.CENTER);
		reasonsForReductionCol.setFont(headingFont);
		reasonsForReductionCol.setOpaque(true);
		reasonsForReductionCol.setBackground(headingBg);
		constraints.gridheight=1;
		addComponent(6, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, reasonsForReductionCol, gridBag, constraints, securityPersonalGuaranteePanel);

		if (! first)
		{
			amtRealisedSec1=new JLabel("through Disposal", SwingConstants.CENTER);
			amtRealisedSec1.setFont(headingFont);
			amtRealisedSec1.setOpaque(true);
			amtRealisedSec1.setBackground(headingBg);
			constraints.gridheight=1;
			addComponent(3, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, amtRealisedSec1, gridBag, constraints, securityPersonalGuaranteePanel);
		}

		networthCol1=new JLabel("Guarantor(s)", SwingConstants.CENTER);
		networthCol1.setFont(headingFont);
		networthCol1.setOpaque(true);
		networthCol1.setBackground(headingBg);
		constraints.gridheight=1;
		addComponent(4, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, networthCol1, gridBag, constraints, securityPersonalGuaranteePanel);

		if (! first)
		{
			amtRealisedPg1=new JLabel("throught Invocation of", SwingConstants.CENTER);
			amtRealisedPg1.setFont(headingFont);
			amtRealisedPg1.setOpaque(true);
			amtRealisedPg1.setBackground(headingBg);
			constraints.gridheight=1;
			addComponent(5, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, amtRealisedPg1, gridBag, constraints, securityPersonalGuaranteePanel);
		}

		reasonsForReductionCol1=new JLabel("Reduction in the value", SwingConstants.CENTER);
		reasonsForReductionCol1.setFont(headingFont);
		reasonsForReductionCol1.setOpaque(true);
		reasonsForReductionCol1.setBackground(headingBg);
		constraints.gridheight=1;
		addComponent(6, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, reasonsForReductionCol1, gridBag, constraints, securityPersonalGuaranteePanel);

		if (! first)
		{
			amtRealisedSec2=new JLabel("of Security", SwingConstants.CENTER);
			amtRealisedSec2.setFont(headingFont);
			amtRealisedSec2.setOpaque(true);
			amtRealisedSec2.setBackground(headingBg);
			constraints.gridheight=1;
			addComponent(3, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, amtRealisedSec2, gridBag, constraints, securityPersonalGuaranteePanel);

			amtRealisedPg2=new JLabel("Personal Guarantees", SwingConstants.CENTER);
			amtRealisedPg2.setFont(headingFont);
			amtRealisedPg2.setOpaque(true);
			amtRealisedPg2.setBackground(headingBg);
			constraints.gridheight=1;
			addComponent(5, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, amtRealisedPg2, gridBag, constraints, securityPersonalGuaranteePanel);
		}

		natureCol=new JLabel("Nature", SwingConstants.CENTER);
		natureCol.setFont(headingFont);
		natureCol.setOpaque(true);
		natureCol.setBackground(headingBg);
		constraints.gridheight=1;
		addComponent(1, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, natureCol, gridBag, constraints, securityPersonalGuaranteePanel);

		valueCol=new JLabel("Value (in Rs.)", SwingConstants.CENTER);
		valueCol.setFont(headingFont);
		valueCol.setOpaque(true);
		valueCol.setBackground(headingBg);
		addComponent(2, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, valueCol, gridBag, constraints, securityPersonalGuaranteePanel);

		networthCol2=new JLabel("(in Rs.)", SwingConstants.CENTER);
		networthCol2.setFont(headingFont);
		networthCol2.setOpaque(true);
		networthCol2.setBackground(headingBg);
		constraints.gridheight=1;
		addComponent(4, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, networthCol2, gridBag, constraints, securityPersonalGuaranteePanel);

		reasonsForReductionCol2=new JLabel("of Security, if any", SwingConstants.CENTER);
		reasonsForReductionCol2.setFont(headingFont);
		reasonsForReductionCol2.setOpaque(true);
		reasonsForReductionCol2.setBackground(headingBg);
		constraints.gridheight=1;
		addComponent(6, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, reasonsForReductionCol2, gridBag, constraints, securityPersonalGuaranteePanel);		

		asOnSanction=new JLabel("As On Date of ");
		asOnSanction.setFont(headingFont);
		asOnSanction.setOpaque(true);
		asOnSanction.setBackground(dataBg);
		constraints.gridheight=1;
		addComponent(0, 4, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, asOnSanction, gridBag, constraints, securityPersonalGuaranteePanel);

		land=new JLabel("Land", SwingConstants.CENTER);
		land.setFont(headingFont);
		land.setOpaque(true);
		land.setBackground(dataBg);
		constraints.gridheight=1;
		addComponent(1, 4, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, land, gridBag, constraints, securityPersonalGuaranteePanel);

		com.cgtsi.claim.DtlsAsOnDateOfSanction dtlsAsOnDateOfSanction = new com.cgtsi.claim.DtlsAsOnDateOfSanction();
//		if ((flag.equals("MOD")) || (flag.equals("VER")))
//		{
		if (securityAndPersonalGuaranteeDtls.getDetailsAsOnDateOfSanction()!=null)
		{
			dtlsAsOnDateOfSanction = securityAndPersonalGuaranteeDtls.getDetailsAsOnDateOfSanction();
		}
//		}

		txtAsOnSanctionLandValue = new JTextField(10);
		txtAsOnSanctionLandValue.setDocument(new DecimalFormatField(13,2));
//		if ((flag.equals("MOD")) || (flag.equals("VER")))
//		{
			text = df.format(dtlsAsOnDateOfSanction.getValueOfLand());
			txtAsOnSanctionLandValue.setText(text);
//		}
		addComponent(2, 4, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAsOnSanctionLandValue, gridBag, constraints, securityPersonalGuaranteePanel);

		txtAsOnSanctionNetworth = new JTextField(8);
		txtAsOnSanctionNetworth.setDocument(new DecimalFormatField(13,2));
		constraints.gridheight=6;
//		if ((flag.equals("MOD")) || (flag.equals("VER")))
//		{
			text = df.format(dtlsAsOnDateOfSanction.getNetworthOfGuarantors());
			txtAsOnSanctionNetworth.setText(text);
//		}
		addComponent(4, 4, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAsOnSanctionNetworth, gridBag, constraints, securityPersonalGuaranteePanel);

		txtAsOnSanctionReasonsForReduction=new JTextArea(2, 10);
		txtAsOnSanctionReasonsForReduction.setLineWrap(true);
		JScrollPane txtAsOnSanctionReasonsForReductionPane=new JScrollPane(txtAsOnSanctionReasonsForReduction, 								JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		constraints.gridheight=6;
//		if ((flag.equals("MOD")) || (flag.equals("VER")))
//		{
		if (dtlsAsOnDateOfSanction.getReasonsForReduction()!=null)
		{
			txtAsOnSanctionReasonsForReduction.setText( dtlsAsOnDateOfSanction.getReasonsForReduction());
		}
//		}
		addComponent(6, 4, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAsOnSanctionReasonsForReductionPane, gridBag, constraints, securityPersonalGuaranteePanel);

		asOnSanction1=new JLabel("Sanction of Credit");
		asOnSanction1.setFont(headingFont);
		asOnSanction1.setOpaque(true);
		asOnSanction1.setBackground(dataBg);
		asOnSanction1.setVerticalAlignment(SwingConstants.TOP);
		constraints.gridheight=5;
		addComponent(0, 5, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, asOnSanction1, gridBag, constraints, securityPersonalGuaranteePanel);

		building=new JLabel("Building", SwingConstants.CENTER);
		building.setFont(headingFont);
		building.setOpaque(true);
		building.setBackground(dataBg);
		constraints.gridheight=1;
		addComponent(1, 5, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, building, gridBag, constraints, securityPersonalGuaranteePanel);

		txtAsOnSanctionBuildingValue = new JTextField(10);
		txtAsOnSanctionBuildingValue.setDocument(new DecimalFormatField(13,2));
//		if ((flag.equals("MOD")) || (flag.equals("VER")))
//		{
			text = df.format(dtlsAsOnDateOfSanction.getValueOfBuilding());
			txtAsOnSanctionBuildingValue.setText(text);
//		}
		addComponent(2, 5, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAsOnSanctionBuildingValue, gridBag, constraints, securityPersonalGuaranteePanel);

		machine=new JLabel("Machine", SwingConstants.CENTER);
		machine.setFont(headingFont);
		machine.setOpaque(true);
		machine.setBackground(dataBg);
		constraints.gridheight=1;
		addComponent(1, 6, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, machine, gridBag, constraints, securityPersonalGuaranteePanel);

		txtAsOnSanctionMachineValue = new JTextField(10);
		txtAsOnSanctionMachineValue.setDocument(new DecimalFormatField(13,2));
//		if ((flag.equals("MOD")) || (flag.equals("VER")))
//		{
			text = df.format(dtlsAsOnDateOfSanction.getValueOfMachine());
			txtAsOnSanctionMachineValue.setText(text);
//		}
		addComponent(2, 6, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAsOnSanctionMachineValue, gridBag, constraints, securityPersonalGuaranteePanel);

		otherAssets=new JLabel("Other Fixed / Movable Assets", SwingConstants.CENTER);
		otherAssets.setFont(headingFont);
		otherAssets.setOpaque(true);
		otherAssets.setBackground(dataBg);
		constraints.gridheight=1;
		addComponent(1, 7, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, otherAssets, gridBag, constraints, securityPersonalGuaranteePanel);

		txtAsOnSanctionFixedValue = new JTextField(10);
		txtAsOnSanctionFixedValue.setDocument(new DecimalFormatField(13,2));
//		if ((flag.equals("MOD")) || (flag.equals("VER")))
//		{
			text = df.format(dtlsAsOnDateOfSanction.getValueOfOtherFixedMovableAssets());
			txtAsOnSanctionFixedValue.setText(text);
//		}
		addComponent(2, 7, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAsOnSanctionFixedValue, gridBag, constraints, securityPersonalGuaranteePanel);

		currentAssets=new JLabel("Current Assets", SwingConstants.CENTER);
		currentAssets.setFont(headingFont);
		currentAssets.setOpaque(true);
		currentAssets.setBackground(dataBg);
		constraints.gridheight=1;
		addComponent(1, 8, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, currentAssets, gridBag, constraints, securityPersonalGuaranteePanel);

		txtAsOnSanctionCurrentValue = new JTextField(10);
		txtAsOnSanctionCurrentValue.setDocument(new DecimalFormatField(13,2));
//		if ((flag.equals("MOD")) || (flag.equals("VER")))
//		{
			text = df.format(dtlsAsOnDateOfSanction.getValueOfCurrentAssets());
			txtAsOnSanctionCurrentValue.setText(text);
//		}
		addComponent(2, 8, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAsOnSanctionCurrentValue, gridBag, constraints, securityPersonalGuaranteePanel);

		others=new JLabel("Others", SwingConstants.CENTER);
		others.setFont(headingFont);
		others.setOpaque(true);
		others.setBackground(dataBg);
		constraints.gridheight=1;
		addComponent(1, 9, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, others, gridBag, constraints, securityPersonalGuaranteePanel);

		txtAsOnSanctionOthersValue = new JTextField(10);
		txtAsOnSanctionOthersValue.setDocument(new DecimalFormatField(13,2));
//		if ((flag.equals("MOD")) || (flag.equals("VER")))
//		{

			text = df.format(dtlsAsOnDateOfSanction.getValueOfOthers());
			txtAsOnSanctionOthersValue.setText(text);
//		}
		addComponent(2, 9, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAsOnSanctionOthersValue, gridBag, constraints, securityPersonalGuaranteePanel);

		asOnNpa=new JLabel("As On Date of NPA");
		asOnNpa.setFont(headingFont);
		asOnNpa.setOpaque(true);
		asOnNpa.setBackground(dataBg);
		asOnNpa.setVerticalAlignment(SwingConstants.TOP);
		constraints.gridheight=6;
		addComponent(0, 10, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, asOnNpa, gridBag, constraints, securityPersonalGuaranteePanel);

		land=new JLabel("Land", SwingConstants.CENTER);
		land.setFont(headingFont);
		land.setOpaque(true);
		land.setBackground(dataBg);
		constraints.gridheight=1;
		addComponent(1, 10, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, land, gridBag, constraints, securityPersonalGuaranteePanel);

		com.cgtsi.claim.DtlsAsOnDateOfNPA dtlsAsOnDateOfNPA = new com.cgtsi.claim.DtlsAsOnDateOfNPA();
//		if ((flag.equals("MOD")) || (flag.equals("VER")))
//		{
		if (securityAndPersonalGuaranteeDtls.getDetailsAsOnDateOfNPA()!=null)
		{
			dtlsAsOnDateOfNPA = securityAndPersonalGuaranteeDtls.getDetailsAsOnDateOfNPA();
		}
//		}

		txtAsOnNpaLandValue = new JTextField(10);
		txtAsOnNpaLandValue.setDocument(new DecimalFormatField(13,2));
//		if ((flag.equals("MOD")) || (flag.equals("VER")))
//		{
			text = df.format(dtlsAsOnDateOfNPA.getValueOfLand());
			txtAsOnNpaLandValue.setText(text);
//		}
		addComponent(2, 10, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAsOnNpaLandValue, gridBag, constraints, securityPersonalGuaranteePanel);

		txtAsOnNpaNetworth = new JTextField(8);
		txtAsOnNpaNetworth.setDocument(new DecimalFormatField(13,2));
		constraints.gridheight=6;
//		if ((flag.equals("MOD")) || (flag.equals("VER")))
//		{
			text = df.format(dtlsAsOnDateOfNPA.getNetworthOfGuarantors());
			txtAsOnNpaNetworth.setText(text);
//		}
		addComponent(4, 10, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAsOnNpaNetworth, gridBag, constraints, securityPersonalGuaranteePanel);

		txtAsOnNpaReasonsForReduction=new JTextArea(2, 10);
		txtAsOnNpaReasonsForReduction.setLineWrap(true);
		JScrollPane txtAsOnNpaReasonsForReductionPane=new JScrollPane(txtAsOnNpaReasonsForReduction, 					JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		constraints.gridheight=6;
//		if ((flag.equals("MOD")) || (flag.equals("VER")))
//		{
			if (dtlsAsOnDateOfNPA.getReasonsForReduction()!=null)
			{
				txtAsOnNpaReasonsForReduction.setText(dtlsAsOnDateOfNPA.getReasonsForReduction());
			}
//		}
		addComponent(6, 10, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAsOnNpaReasonsForReductionPane, gridBag, constraints, securityPersonalGuaranteePanel);

		building=new JLabel("Building", SwingConstants.CENTER);
		building.setFont(headingFont);
		building.setOpaque(true);
		building.setBackground(dataBg);
		constraints.gridheight=1;
		addComponent(1, 11, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, building, gridBag, constraints, securityPersonalGuaranteePanel);

		txtAsOnNpaBuildingValue = new JTextField(10);
		txtAsOnNpaBuildingValue.setDocument(new DecimalFormatField(13,2));
//		if ((flag.equals("MOD")) || (flag.equals("VER")))
//		{
			text = df.format(dtlsAsOnDateOfNPA.getValueOfBuilding());
			txtAsOnNpaBuildingValue.setText(text);
//		}
		addComponent(2, 11, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAsOnNpaBuildingValue, gridBag, constraints, securityPersonalGuaranteePanel);

		machine=new JLabel("Machine", SwingConstants.CENTER);
		machine.setFont(headingFont);
		machine.setOpaque(true);
		machine.setBackground(dataBg);
		constraints.gridheight=1;
		addComponent(1, 12, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, machine, gridBag, constraints, securityPersonalGuaranteePanel);

		txtAsOnNpaMachineValue = new JTextField(10);
		txtAsOnNpaMachineValue.setDocument(new DecimalFormatField(13,2));
//		if ((flag.equals("MOD")) || (flag.equals("VER")))
//		{
			text = df.format(dtlsAsOnDateOfNPA.getValueOfMachine());
			txtAsOnNpaMachineValue.setText(text);
//		}
		addComponent(2, 12, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAsOnNpaMachineValue, gridBag, constraints, securityPersonalGuaranteePanel);

		otherAssets=new JLabel("Other Fixed / Movable Assets", SwingConstants.CENTER);
		otherAssets.setFont(headingFont);
		otherAssets.setOpaque(true);
		otherAssets.setBackground(dataBg);
		constraints.gridheight=1;
		addComponent(1, 13, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, otherAssets, gridBag, constraints, securityPersonalGuaranteePanel);

		txtAsOnNpaFixedValue = new JTextField(10);
		txtAsOnNpaFixedValue.setDocument(new DecimalFormatField(13,2));
//		if ((flag.equals("MOD")) || (flag.equals("VER")))
//		{
			text = df.format(dtlsAsOnDateOfNPA.getValueOfOtherFixedMovableAssets());
			txtAsOnNpaFixedValue.setText(text);
//		}
		addComponent(2, 13, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAsOnNpaFixedValue, gridBag, constraints, securityPersonalGuaranteePanel);

		currentAssets=new JLabel("Current Assets", SwingConstants.CENTER);
		currentAssets.setFont(headingFont);
		currentAssets.setOpaque(true);
		currentAssets.setBackground(dataBg);
		constraints.gridheight=1;
		addComponent(1, 14, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, currentAssets, gridBag, constraints, securityPersonalGuaranteePanel);

		txtAsOnNpaCurrentValue = new JTextField(10);
		txtAsOnNpaCurrentValue.setDocument(new DecimalFormatField(13,2));
//		if ((flag.equals("MOD")) || (flag.equals("VER")))
//		{
			text = df.format(dtlsAsOnDateOfNPA.getValueOfCurrentAssets());
			txtAsOnNpaCurrentValue.setText(text);
//		}
		addComponent(2, 14, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAsOnNpaCurrentValue, gridBag, constraints, securityPersonalGuaranteePanel);

		others=new JLabel("Others", SwingConstants.CENTER);
		others.setFont(headingFont);
		others.setOpaque(true);
		others.setBackground(dataBg);
		constraints.gridheight=1;
		addComponent(1, 15, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, others, gridBag, constraints, securityPersonalGuaranteePanel);

		txtAsOnNpaOthersValue = new JTextField(10);
		txtAsOnNpaOthersValue.setDocument(new DecimalFormatField(13,2));
//		if ((flag.equals("MOD")) || (flag.equals("VER")))
//		{
			text = df.format(dtlsAsOnDateOfNPA.getValueOfOthers());
			txtAsOnNpaOthersValue.setText(text);
//		}
		addComponent(2, 15, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAsOnNpaOthersValue, gridBag, constraints, securityPersonalGuaranteePanel);

		asOnLodgement=new JLabel("As On Date of ");
		asOnLodgement.setFont(headingFont);
		asOnLodgement.setOpaque(true);
		asOnLodgement.setBackground(dataBg);
		constraints.gridheight=1;
		addComponent(0, 16, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, asOnLodgement, gridBag, constraints, securityPersonalGuaranteePanel);

		land=new JLabel("Land", SwingConstants.CENTER);
		land.setFont(headingFont);
		land.setOpaque(true);
		land.setBackground(dataBg);
		constraints.gridheight=1;
		addComponent(1, 16, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, land, gridBag, constraints, securityPersonalGuaranteePanel);

		com.cgtsi.claim.DtlsAsOnLogdementOfClaim dtlsAsOnLogdementOfClaim = new com.cgtsi.claim.DtlsAsOnLogdementOfClaim();
//		if ((flag.equals("MOD")) || (flag.equals("VER")))
//		{
		if (securityAndPersonalGuaranteeDtls.getDetailsAsOnDateOfLodgementOfClaim()!=null)
		{
			dtlsAsOnLogdementOfClaim = securityAndPersonalGuaranteeDtls.getDetailsAsOnDateOfLodgementOfClaim();
		}
//		}

		txtAsOnLodgementLandValue = new JTextField(10);
		txtAsOnLodgementLandValue.setDocument(new DecimalFormatField(13,2));
//		if ((flag.equals("MOD")) || (flag.equals("VER")))
//		{
			text = df.format(dtlsAsOnLogdementOfClaim.getValueOfLand());
			txtAsOnLodgementLandValue.setText(text);
//		}
		addComponent(2, 16, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAsOnLodgementLandValue, gridBag, constraints, securityPersonalGuaranteePanel);

		txtAsOnLodgementNetworth = new JTextField(8);
		txtAsOnLodgementNetworth.setDocument(new DecimalFormatField(13,2));
		constraints.gridheight=6;
//		if ((flag.equals("MOD")) || (flag.equals("VER")))
//		{
			text = df.format(dtlsAsOnLogdementOfClaim.getNetworthOfGuarantors());
			txtAsOnLodgementNetworth.setText(text);
//		}
		addComponent(4, 16, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAsOnLodgementNetworth, gridBag, constraints, securityPersonalGuaranteePanel);

		txtAsOnLodgementReasonsForReduction=new JTextArea(2, 10);
		txtAsOnLodgementReasonsForReduction.setLineWrap(true);
		JScrollPane txtAsOnLodgementReasonsForReductionPane=new JScrollPane(txtAsOnLodgementReasonsForReduction, 					JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		constraints.gridheight=6;
//		if ((flag.equals("MOD")) || (flag.equals("VER")))
//		{
		if (dtlsAsOnLogdementOfClaim.getReasonsForReduction()!=null)
		{
			txtAsOnLodgementReasonsForReduction.setText( dtlsAsOnLogdementOfClaim.getReasonsForReduction());
		}
//		}
		addComponent(6, 16, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAsOnLodgementReasonsForReductionPane, gridBag, constraints, securityPersonalGuaranteePanel);

		asOnLodgement1=new JLabel("Lodgement of Claim");
		asOnLodgement1.setFont(headingFont);
		asOnLodgement1.setOpaque(true);
		asOnLodgement1.setBackground(dataBg);
		asOnLodgement1.setVerticalAlignment(SwingConstants.TOP);
		constraints.gridheight=5;
		addComponent(0, 17, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, asOnLodgement1, gridBag, constraints, securityPersonalGuaranteePanel);

		building=new JLabel("Building", SwingConstants.CENTER);
		building.setFont(headingFont);
		building.setOpaque(true);
		building.setBackground(dataBg);
		constraints.gridheight=1;
		addComponent(1, 17, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, building, gridBag, constraints, securityPersonalGuaranteePanel);

		txtAsOnLodgementBuildingValue = new JTextField(10);
		txtAsOnLodgementBuildingValue.setDocument(new DecimalFormatField(13,2));
//		if ((flag.equals("MOD")) || (flag.equals("VER")))
//		{
			text = df.format(dtlsAsOnLogdementOfClaim.getValueOfBuilding());
			txtAsOnLodgementBuildingValue.setText(text);
//		}
		addComponent(2, 17, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAsOnLodgementBuildingValue, gridBag, constraints, securityPersonalGuaranteePanel);

		machine=new JLabel("Machine", SwingConstants.CENTER);
		machine.setFont(headingFont);
		machine.setOpaque(true);
		machine.setBackground(dataBg);
		constraints.gridheight=1;
		addComponent(1, 18, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, machine, gridBag, constraints, securityPersonalGuaranteePanel);

		txtAsOnLodgementMachineValue = new JTextField(10);
		txtAsOnLodgementMachineValue.setDocument(new DecimalFormatField(13,2));
//		if ((flag.equals("MOD")) || (flag.equals("VER")))
//		{
			text = df.format(dtlsAsOnLogdementOfClaim.getValueOfMachine());
			txtAsOnLodgementMachineValue.setText(text);
//		}
		addComponent(2, 18, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAsOnLodgementMachineValue, gridBag, constraints, securityPersonalGuaranteePanel);

		otherAssets=new JLabel("Other Fixed / Movable Assets", SwingConstants.CENTER);
		otherAssets.setFont(headingFont);
		otherAssets.setOpaque(true);
		otherAssets.setBackground(dataBg);
		constraints.gridheight=1;
		addComponent(1, 19, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, otherAssets, gridBag, constraints, securityPersonalGuaranteePanel);

		txtAsOnLodgementFixedValue = new JTextField(10);
		txtAsOnLodgementFixedValue.setDocument(new DecimalFormatField(13,2));
//		if ((flag.equals("MOD")) || (flag.equals("VER")))
//		{
			text = df.format(dtlsAsOnLogdementOfClaim.getValueOfOtherFixedMovableAssets());
			txtAsOnLodgementFixedValue.setText(text);
//		}
		addComponent(2, 19, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAsOnLodgementFixedValue, gridBag, constraints, securityPersonalGuaranteePanel);

		currentAssets=new JLabel("Current Assets", SwingConstants.CENTER);
		currentAssets.setFont(headingFont);
		currentAssets.setOpaque(true);
		currentAssets.setBackground(dataBg);
		constraints.gridheight=1;
		addComponent(1, 20, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, currentAssets, gridBag, constraints, securityPersonalGuaranteePanel);

		txtAsOnLodgementCurrentValue = new JTextField(10);
		txtAsOnLodgementCurrentValue.setDocument(new DecimalFormatField(13,2));
//		if ((flag.equals("MOD")) || (flag.equals("VER")))
//		{
			text = df.format(dtlsAsOnLogdementOfClaim.getValueOfCurrentAssets());
			txtAsOnLodgementCurrentValue.setText(text);
//		}
		addComponent(2, 20, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAsOnLodgementCurrentValue, gridBag, constraints, securityPersonalGuaranteePanel);

		others=new JLabel("Others", SwingConstants.CENTER);
		others.setFont(headingFont);
		others.setOpaque(true);
		others.setBackground(dataBg);
		constraints.gridheight=1;
		addComponent(1, 21, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, others, gridBag, constraints, securityPersonalGuaranteePanel);

		txtAsOnLodgementOthersValue = new JTextField(10);
		txtAsOnLodgementOthersValue.setDocument(new DecimalFormatField(13,2));
//		if ((flag.equals("MOD")) || (flag.equals("VER")))
//		{
			text = df.format(dtlsAsOnLogdementOfClaim.getValueOfOthers());
			txtAsOnLodgementOthersValue.setText(text);
//		}
		addComponent(2, 21, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAsOnLodgementOthersValue, gridBag, constraints, securityPersonalGuaranteePanel);

		if (! first)
		{
			asOnSecondClaim=new JLabel("As On Date of ");
			asOnSecondClaim.setFont(headingFont);
			asOnSecondClaim.setOpaque(true);
			asOnSecondClaim.setBackground(dataBg);
			constraints.gridheight=1;
			addComponent(0, 22, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, asOnSecondClaim, gridBag, constraints, securityPersonalGuaranteePanel);

			land=new JLabel("Land", SwingConstants.CENTER);
			land.setFont(headingFont);
			land.setOpaque(true);
			land.setBackground(dataBg);
			constraints.gridheight=1;
			addComponent(1, 22, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, land, gridBag, constraints, securityPersonalGuaranteePanel);

			com.cgtsi.claim.DtlsAsOnLogdementOfSecondClaim dtlsAsOnLogdementOfSecondClaim = new com.cgtsi.claim.DtlsAsOnLogdementOfSecondClaim();
//			if ((flag.equals("MOD")) || (flag.equals("VER")))
//			{
			if (securityAndPersonalGuaranteeDtls.getDetailsAsOnDateOfLodgementOfSecondClaim()!=null)
			{
				dtlsAsOnLogdementOfSecondClaim = securityAndPersonalGuaranteeDtls.getDetailsAsOnDateOfLodgementOfSecondClaim();
			}
//			}

			txtAsOnSecondClaimLandValue = new JTextField(10);
			txtAsOnSecondClaimLandValue.setDocument(new DecimalFormatField(13,2));
//			if ((flag.equals("MOD")) || (flag.equals("VER")))
//			{
				text = df.format(dtlsAsOnLogdementOfSecondClaim.getValueOfLand());
				txtAsOnSecondClaimLandValue.setText(text);
//			}
			addComponent(2, 22, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAsOnSecondClaimLandValue, gridBag, constraints, securityPersonalGuaranteePanel);

			txtAmtRealisedLand = new JTextField(8);
			txtAmtRealisedLand.setDocument(new DecimalFormatField(13,2));
//			if ((flag.equals("MOD")) || (flag.equals("VER")))
//			{
				text = df.format(dtlsAsOnLogdementOfSecondClaim.getAmtRealisedLand());
				txtAmtRealisedLand.setText(text);
//			}
			addComponent(3, 22, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAmtRealisedLand, gridBag, constraints, securityPersonalGuaranteePanel);

			txtAsOnSecondClaimNetworth = new JTextField(8);
			txtAsOnSecondClaimNetworth.setDocument(new DecimalFormatField(13,2));
			constraints.gridheight=6;
//			if ((flag.equals("MOD")) || (flag.equals("VER")))
//			{
				text = df.format(dtlsAsOnLogdementOfSecondClaim.getNetworthOfGuarantors());
				txtAsOnSecondClaimNetworth.setText(text);
//			}
			addComponent(4, 22, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAsOnSecondClaimNetworth, gridBag, constraints, securityPersonalGuaranteePanel);

			txtAmtRealisedPg = new JTextField(8);
			txtAmtRealisedPg.setDocument(new DecimalFormatField(13,2));
			constraints.gridheight=6;
//			if ((flag.equals("MOD")) || (flag.equals("VER")))
//			{
				text = df.format(dtlsAsOnLogdementOfSecondClaim.getAmtRealisedPersonalGuarantee());
				txtAmtRealisedPg.setText(text);
//			}
			addComponent(5, 22, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAmtRealisedPg, gridBag, constraints, securityPersonalGuaranteePanel);

			txtAsOnSecondClaimReasonsLand=new JTextArea(2, 10);
			txtAsOnSecondClaimReasonsLand.setLineWrap(true);
			JScrollPane txtAsOnSecondClaimReasonsLandPane=new JScrollPane(txtAsOnSecondClaimReasonsLand, 					JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			constraints.gridheight=1;
//			if ((flag.equals("MOD")) || (flag.equals("VER")))
//			{
			if (dtlsAsOnLogdementOfSecondClaim.getReasonsForReductionLand()!=null)
			{
				txtAsOnSecondClaimReasonsLand.setText( dtlsAsOnLogdementOfSecondClaim.getReasonsForReductionLand());
			}
//			}
			addComponent(6, 22, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAsOnSecondClaimReasonsLandPane, gridBag, constraints, securityPersonalGuaranteePanel);

			asOnSecondClaim1=new JLabel("Second Claim");
			asOnSecondClaim1.setFont(headingFont);
			asOnSecondClaim1.setOpaque(true);
			asOnSecondClaim1.setBackground(dataBg);
			asOnSecondClaim1.setVerticalAlignment(SwingConstants.TOP);
			constraints.gridheight=5;
			addComponent(0, 23, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, asOnSecondClaim1, gridBag, constraints, securityPersonalGuaranteePanel);

			building=new JLabel("Building", SwingConstants.CENTER);
			building.setFont(headingFont);
			building.setOpaque(true);
			building.setBackground(dataBg);
			constraints.gridheight=1;
			addComponent(1, 23, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, building, gridBag, constraints, securityPersonalGuaranteePanel);

			txtAsOnSecondClaimBuildingValue = new JTextField(10);
			txtAsOnSecondClaimBuildingValue.setDocument(new DecimalFormatField(13,2));
//			if ((flag.equals("MOD")) || (flag.equals("VER")))
//			{
				text = df.format(dtlsAsOnLogdementOfSecondClaim.getValueOfBuilding());
				txtAsOnSecondClaimBuildingValue.setText(text);
//			}
			addComponent(2, 23, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAsOnSecondClaimBuildingValue, gridBag, constraints, securityPersonalGuaranteePanel);

			txtAmtRealisedBuilding = new JTextField(8);
			txtAmtRealisedBuilding.setDocument(new DecimalFormatField(13,2));
//			if ((flag.equals("MOD")) || (flag.equals("VER")))
//			{
				text = df.format(dtlsAsOnLogdementOfSecondClaim.getAmtRealisedBuilding());
				txtAmtRealisedBuilding.setText(text);
//			}
			addComponent(3, 23, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAmtRealisedBuilding, gridBag, constraints, securityPersonalGuaranteePanel);

			txtAsOnSecondClaimReasonsBuilding=new JTextArea(2, 10);
			txtAsOnSecondClaimReasonsBuilding.setLineWrap(true);
			JScrollPane txtAsOnSecondClaimReasonsBuildingPane=new JScrollPane(txtAsOnSecondClaimReasonsBuilding, 					JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//			if ((flag.equals("MOD")) || (flag.equals("VER")))
//			{
			if (dtlsAsOnLogdementOfSecondClaim.getReasonsForReductionBuilding()!=null)
			{
				txtAsOnSecondClaimReasonsBuilding.setText( dtlsAsOnLogdementOfSecondClaim.getReasonsForReductionBuilding());
			}
//			}
			addComponent(6, 23, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAsOnSecondClaimReasonsBuildingPane, gridBag, constraints, securityPersonalGuaranteePanel);

			machine=new JLabel("Machine", SwingConstants.CENTER);
			machine.setFont(headingFont);
			machine.setOpaque(true);
			machine.setBackground(dataBg);
			constraints.gridheight=1;
			addComponent(1, 24, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, machine, gridBag, constraints, securityPersonalGuaranteePanel);

			txtAsOnSecondClaimMachineValue = new JTextField(10);
			txtAsOnSecondClaimMachineValue.setDocument(new DecimalFormatField(13,2));
//			if ((flag.equals("MOD")) || (flag.equals("VER")))
//			{
				text = df.format(dtlsAsOnLogdementOfSecondClaim.getValueOfMachine());
				txtAsOnSecondClaimMachineValue.setText(text);
//			}
			addComponent(2, 24, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAsOnSecondClaimMachineValue, gridBag, constraints, securityPersonalGuaranteePanel);

			txtAmtRealisedMachine = new JTextField(8);
			txtAmtRealisedMachine.setDocument(new DecimalFormatField(13,2));
//			if ((flag.equals("MOD")) || (flag.equals("VER")))
//			{
				text = df.format(dtlsAsOnLogdementOfSecondClaim.getAmtRealisedMachine());
				txtAmtRealisedMachine.setText(text);
//			}
			addComponent(3, 24, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAmtRealisedMachine, gridBag, constraints, securityPersonalGuaranteePanel);

			txtAsOnSecondClaimReasonsMachine=new JTextArea(2, 10);
			txtAsOnSecondClaimReasonsMachine.setLineWrap(true);
			JScrollPane txtAsOnSecondClaimReasonsMachinePane=new JScrollPane(txtAsOnSecondClaimReasonsMachine, 					JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//			if ((flag.equals("MOD")) || (flag.equals("VER")))
//			{
			if (dtlsAsOnLogdementOfSecondClaim.getReasonsForReductionMachine()!=null)
			{
				txtAsOnSecondClaimReasonsMachine.setText( dtlsAsOnLogdementOfSecondClaim.getReasonsForReductionMachine());
			}
//			}
			addComponent(6, 24, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAsOnSecondClaimReasonsMachinePane, gridBag, constraints, securityPersonalGuaranteePanel);

			otherAssets=new JLabel("Other Fixed / Movable Assets", SwingConstants.CENTER);
			otherAssets.setFont(headingFont);
			otherAssets.setOpaque(true);
			otherAssets.setBackground(dataBg);
			constraints.gridheight=1;
			addComponent(1, 25, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, otherAssets, gridBag, constraints, securityPersonalGuaranteePanel);

			txtAsOnSecondClaimFixedValue = new JTextField(10);
			txtAsOnSecondClaimFixedValue.setDocument(new DecimalFormatField(13,2));
//			if ((flag.equals("MOD")) || (flag.equals("VER")))
//			{
				text = df.format( dtlsAsOnLogdementOfSecondClaim.getValueOfOtherFixedMovableAssets());
				txtAsOnSecondClaimFixedValue.setText(text);
//			}
			addComponent(2, 25, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAsOnSecondClaimFixedValue, gridBag, constraints, securityPersonalGuaranteePanel);

			txtAmtRealisedFixed = new JTextField(8);
			txtAmtRealisedFixed.setDocument(new DecimalFormatField(13,2));
//			if ((flag.equals("MOD")) || (flag.equals("VER")))
//			{
				text = df.format(dtlsAsOnLogdementOfSecondClaim.getAmtRealisedFixed());
				txtAmtRealisedFixed.setText(text);
//			}
			addComponent(3, 25, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAmtRealisedFixed, gridBag, constraints, securityPersonalGuaranteePanel);

			txtAsOnSecondClaimReasonsFixed=new JTextArea(2, 10);
			txtAsOnSecondClaimReasonsFixed.setLineWrap(true);
			JScrollPane txtAsOnSecondClaimReasonsFixedPane=new JScrollPane(txtAsOnSecondClaimReasonsFixed, 					JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//			if ((flag.equals("MOD")) || (flag.equals("VER")))
//			{
			if (dtlsAsOnLogdementOfSecondClaim.getReasonsForReductionFixed()!=null)
			{
				txtAsOnSecondClaimReasonsFixed.setText( dtlsAsOnLogdementOfSecondClaim.getReasonsForReductionFixed());
			}
//			}
			addComponent(6, 25, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAsOnSecondClaimReasonsFixedPane, gridBag, constraints, securityPersonalGuaranteePanel);

			currentAssets=new JLabel("Current Assets", SwingConstants.CENTER);
			currentAssets.setFont(headingFont);
			currentAssets.setOpaque(true);
			currentAssets.setBackground(dataBg);
			constraints.gridheight=1;
			addComponent(1, 26, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, currentAssets, gridBag, constraints, securityPersonalGuaranteePanel);

			txtAsOnSecondClaimCurrentValue = new JTextField(10);
			txtAsOnSecondClaimCurrentValue.setDocument(new DecimalFormatField(13,2));
//			if ((flag.equals("MOD")) || (flag.equals("VER")))
//			{
				text = df.format(dtlsAsOnLogdementOfSecondClaim.getValueOfCurrentAssets());
				txtAsOnSecondClaimCurrentValue.setText(text);
//			}
			addComponent(2, 26, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAsOnSecondClaimCurrentValue, gridBag, constraints, securityPersonalGuaranteePanel);

			txtAmtRealisedCurrent = new JTextField(8);
			txtAmtRealisedCurrent.setDocument(new DecimalFormatField(13,2));
//			if ((flag.equals("MOD")) || (flag.equals("VER")))
//			{
				text = df.format(dtlsAsOnLogdementOfSecondClaim.getAmtRealisedCurrent());
				txtAmtRealisedCurrent.setText(text);
//			}
			addComponent(3, 26, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAmtRealisedCurrent, gridBag, constraints, securityPersonalGuaranteePanel);

			txtAsOnSecondClaimReasonsCurrent=new JTextArea(2, 10);
			txtAsOnSecondClaimReasonsCurrent.setLineWrap(true);
			JScrollPane txtAsOnSecondClaimReasonsCurrentPane=new JScrollPane(txtAsOnSecondClaimReasonsCurrent, 					JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//			if ((flag.equals("MOD")) || (flag.equals("VER")))
//			{
			if (dtlsAsOnLogdementOfSecondClaim.getReasonsForReductionCurrent()!=null)
			{
				txtAsOnSecondClaimReasonsCurrent.setText( dtlsAsOnLogdementOfSecondClaim.getReasonsForReductionCurrent());
			}
//			}
			addComponent(6, 26, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAsOnSecondClaimReasonsCurrentPane, gridBag, constraints, securityPersonalGuaranteePanel);

			others=new JLabel("Others", SwingConstants.CENTER);
			others.setFont(headingFont);
			others.setOpaque(true);
			others.setBackground(dataBg);
			constraints.gridheight=1;
			addComponent(1, 27, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, others, gridBag, constraints, securityPersonalGuaranteePanel);

			txtAsOnSecondClaimOthersValue = new JTextField(10);
			txtAsOnSecondClaimOthersValue.setDocument(new DecimalFormatField(13,2));
//			if ((flag.equals("MOD")) || (flag.equals("VER")))
//			{
				text = df.format(dtlsAsOnLogdementOfSecondClaim.getValueOfOthers());
				txtAsOnSecondClaimOthersValue.setText(text);
//			}
			addComponent(2, 27, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAsOnSecondClaimOthersValue, gridBag, constraints, securityPersonalGuaranteePanel);

			txtAmtRealisedOthers = new JTextField(8);
			txtAmtRealisedOthers.setDocument(new DecimalFormatField(13,2));
//			if ((flag.equals("MOD")) || (flag.equals("VER")))
//			{
				text = df.format(dtlsAsOnLogdementOfSecondClaim.getAmtRealisedOthers());
				txtAmtRealisedOthers.setText(text);
//			}
			addComponent(3, 27, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAmtRealisedOthers, gridBag, constraints, securityPersonalGuaranteePanel);

			txtAsOnSecondClaimReasonsOthers=new JTextArea(2, 10);
			txtAsOnSecondClaimReasonsOthers.setLineWrap(true);
			JScrollPane txtAsOnSecondClaimReasonsOthersPane=new JScrollPane(txtAsOnSecondClaimReasonsOthers, 					JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//			if ((flag.equals("MOD")) || (flag.equals("VER")))
//			{
			if (dtlsAsOnLogdementOfSecondClaim.getReasonsForReductionOthers()!=null)
			{
				txtAsOnSecondClaimReasonsOthers.setText( dtlsAsOnLogdementOfSecondClaim.getReasonsForReductionOthers());
			}
//			}
			addComponent(6, 27, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAsOnSecondClaimReasonsOthersPane, gridBag, constraints, securityPersonalGuaranteePanel);
		}
	}
	/**
	* This method displays the security details of the claims screen.
	*
	private void displayClaimsSecurityDetailsSI(com.cgtsi.claim.SecondInstallmentSecurityDetails securityDetails, String flag)
	{
		securityPanel.removeAll();
		securityPanel.setBackground(dataBg);
		securityPanel.setLayout(gridBag);

		natureCol=new JLabel("Nature Of", SwingConstants.CENTER);
		natureCol.setFont(headingFont);
		natureCol.setOpaque(true);
		natureCol.setBackground(headingBg);
		addComponent(1, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, natureCol, gridBag, constraints, securityPanel);

		nameOfOwner=new JLabel("Name Of", SwingConstants.CENTER);
		nameOfOwner.setFont(headingFont);
		nameOfOwner.setOpaque(true);
		nameOfOwner.setBackground(headingBg);
		addComponent(2, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, nameOfOwner, gridBag, constraints, securityPanel);

		orderOfCharge=new JLabel("Order Of", SwingConstants.CENTER);
		orderOfCharge.setFont(headingFont);
		orderOfCharge.setOpaque(true);
		orderOfCharge.setBackground(headingBg);
		addComponent(3, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, orderOfCharge, gridBag, constraints, securityPanel);

		partOfSecurity=new JLabel("Particulars", SwingConstants.CENTER);
		partOfSecurity.setFont(headingFont);
		partOfSecurity.setOpaque(true);
		partOfSecurity.setBackground(headingBg);
		addComponent(4, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, partOfSecurity, gridBag, constraints, securityPanel);

		otherPartOfSecurity=new JLabel("Location", SwingConstants.CENTER);
		otherPartOfSecurity.setFont(headingFont);
		otherPartOfSecurity.setOpaque(true);
		otherPartOfSecurity.setBackground(headingBg);
		addComponent(5, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, otherPartOfSecurity, gridBag, constraints, securityPanel);

		valueCol=new JLabel("Value of Security As On", SwingConstants.CENTER);
		valueCol.setFont(headingFont);
		valueCol.setOpaque(true);
		valueCol.setBackground(headingBg);
		addComponent(6, 0, 4, GridBagConstraints.WEST, GridBagConstraints.BOTH, valueCol, gridBag, constraints, securityPanel);

		amtRealised=new JLabel("Amount ", SwingConstants.CENTER);
		amtRealised.setFont(headingFont);
		amtRealised.setOpaque(true);
		amtRealised.setBackground(headingBg);
		addComponent(10, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, amtRealised, gridBag, constraints, securityPanel);

		reasonsForLowRealisation=new JLabel("Reasons for", SwingConstants.CENTER);
		reasonsForLowRealisation.setFont(headingFont);
		reasonsForLowRealisation.setOpaque(true);
		reasonsForLowRealisation.setBackground(headingBg);
		addComponent(11, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, reasonsForLowRealisation, gridBag, constraints, securityPanel);

		natureCol1=new JLabel("Security", SwingConstants.CENTER);
		natureCol1.setFont(headingFont);
		natureCol1.setOpaque(true);
		natureCol1.setBackground(headingBg);
		addComponent(1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, natureCol1, gridBag, constraints, securityPanel);

		nameOfOwner1=new JLabel("Owner Of", SwingConstants.CENTER);
		nameOfOwner1.setFont(headingFont);
		nameOfOwner1.setOpaque(true);
		nameOfOwner1.setBackground(headingBg);
		addComponent(2, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, nameOfOwner1, gridBag, constraints, securityPanel);

		orderOfCharge1=new JLabel("Charge", SwingConstants.CENTER);
		orderOfCharge1.setFont(headingFont);
		orderOfCharge1.setOpaque(true);
		orderOfCharge1.setBackground(headingBg);
		addComponent(3, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, orderOfCharge1, gridBag, constraints, securityPanel);

		partOfSecurity1=new JLabel("Of Security", SwingConstants.CENTER);
		partOfSecurity1.setFont(headingFont);
		partOfSecurity1.setOpaque(true);
		partOfSecurity1.setBackground(headingBg);
		addComponent(4, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, partOfSecurity1, gridBag, constraints, securityPanel);

		otherPartOfSecurity1=new JLabel("Address /", SwingConstants.CENTER);
		otherPartOfSecurity1.setFont(headingFont);
		otherPartOfSecurity1.setOpaque(true);
		otherPartOfSecurity1.setBackground(headingBg);
		addComponent(5, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, otherPartOfSecurity1, gridBag, constraints, securityPanel);

		preferrment=new JLabel("Preferrment of Claim", SwingConstants.CENTER);
		preferrment.setFont(headingFont);
		preferrment.setOpaque(true);
		preferrment.setBackground(headingBg);
		addComponent(8, 1, 2, GridBagConstraints.WEST, GridBagConstraints.BOTH, preferrment, gridBag, constraints, securityPanel);

		amtRealised1=new JLabel("Realized", SwingConstants.CENTER);
		amtRealised1.setFont(headingFont);
		amtRealised1.setOpaque(true);
		amtRealised1.setBackground(headingBg);
		addComponent(10, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, amtRealised1, gridBag, constraints, securityPanel);

		reasonsForLowRealisation1=new JLabel("low realization", SwingConstants.CENTER);
		reasonsForLowRealisation1.setFont(headingFont);
		reasonsForLowRealisation1.setOpaque(true);
		reasonsForLowRealisation1.setBackground(headingBg);
		addComponent(11, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, reasonsForLowRealisation1, gridBag, constraints, securityPanel);

		nameOfOwner2=new JLabel("Security", SwingConstants.CENTER);
		nameOfOwner2.setFont(headingFont);
		nameOfOwner2.setOpaque(true);
		nameOfOwner2.setBackground(headingBg);
		addComponent(2, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, nameOfOwner2, gridBag, constraints, securityPanel);

		otherPartOfSecurity2=new JLabel("Other", SwingConstants.CENTER);
		otherPartOfSecurity2.setFont(headingFont);
		otherPartOfSecurity2.setOpaque(true);
		otherPartOfSecurity2.setBackground(headingBg);
		addComponent(5, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, otherPartOfSecurity2, gridBag, constraints, securityPanel);

		sanction=new JLabel("Sanction", SwingConstants.CENTER);
		sanction.setFont(headingFont);
		sanction.setOpaque(true);
		sanction.setBackground(headingBg);
		addComponent(6, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, sanction, gridBag, constraints, securityPanel);

		npa=new JLabel("NPA", SwingConstants.CENTER);
		npa.setFont(headingFont);
		npa.setOpaque(true);
		npa.setBackground(headingBg);
		addComponent(7, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, npa, gridBag, constraints, securityPanel);

		firstInstallmentCol=new JLabel("First", SwingConstants.CENTER);
		firstInstallmentCol.setFont(headingFont);
		firstInstallmentCol.setOpaque(true);
		firstInstallmentCol.setBackground(headingBg);
		addComponent(8, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, firstInstallmentCol, gridBag, constraints, securityPanel);

		secondInstallmentCol=new JLabel("Second", SwingConstants.CENTER);
		secondInstallmentCol.setFont(headingFont);
		secondInstallmentCol.setOpaque(true);
		secondInstallmentCol.setBackground(headingBg);
		addComponent(9, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, secondInstallmentCol, gridBag, constraints, securityPanel);

		amtRealised2=new JLabel("through", SwingConstants.CENTER);
		amtRealised2.setFont(headingFont);
		amtRealised2.setOpaque(true);
		amtRealised2.setBackground(headingBg);
		addComponent(10, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, amtRealised2, gridBag, constraints, securityPanel);

		reasonsForLowRealisation2=new JLabel("if Any, On", SwingConstants.CENTER);
		reasonsForLowRealisation2.setFont(headingFont);
		reasonsForLowRealisation2.setOpaque(true);
		reasonsForLowRealisation2.setBackground(headingBg);
		addComponent(11, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, reasonsForLowRealisation2, gridBag, constraints, securityPanel);

		otherPartOfSecurity3=new JLabel("Particulars", SwingConstants.CENTER);
		otherPartOfSecurity3.setFont(headingFont);
		otherPartOfSecurity3.setOpaque(true);
		otherPartOfSecurity3.setBackground(headingBg);
		addComponent(5, 3, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, otherPartOfSecurity3, gridBag, constraints, securityPanel);

		firstInstallmentCol1=new JLabel("Installment", SwingConstants.CENTER);
		firstInstallmentCol1.setFont(headingFont);
		firstInstallmentCol1.setOpaque(true);
		firstInstallmentCol1.setBackground(headingBg);
		addComponent(8, 3, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, firstInstallmentCol1, gridBag, constraints, securityPanel);

		secondInstallmentCol1=new JLabel("Installment", SwingConstants.CENTER);
		secondInstallmentCol1.setFont(headingFont);
		secondInstallmentCol1.setOpaque(true);
		secondInstallmentCol1.setBackground(headingBg);
		addComponent(9, 3, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, secondInstallmentCol1, gridBag, constraints, securityPanel);

		amtRealised3=new JLabel("Disposal Of", SwingConstants.CENTER);
		amtRealised3.setFont(headingFont);
		amtRealised3.setOpaque(true);
		amtRealised3.setBackground(headingBg);
		addComponent(10, 3, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, amtRealised3, gridBag, constraints, securityPanel);

		reasonsForLowRealisation3=new JLabel("Disposal Of", SwingConstants.CENTER);
		reasonsForLowRealisation3.setFont(headingFont);
		reasonsForLowRealisation3.setOpaque(true);
		reasonsForLowRealisation3.setBackground(headingBg);
		addComponent(11, 3, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, reasonsForLowRealisation3, gridBag, constraints, securityPanel);

		otherPartOfSecurity4=new JLabel("of Security", SwingConstants.CENTER);
		otherPartOfSecurity4.setFont(headingFont);
		otherPartOfSecurity4.setOpaque(true);
		otherPartOfSecurity4.setBackground(headingBg);
		addComponent(5, 4, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, otherPartOfSecurity4, gridBag, constraints, securityPanel);

		amtRealised4=new JLabel("Security", SwingConstants.CENTER);
		amtRealised4.setFont(headingFont);
		amtRealised4.setOpaque(true);
		amtRealised4.setBackground(headingBg);
		addComponent(10, 4, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, amtRealised4, gridBag, constraints, securityPanel);

		reasonsForLowRealisation4=new JLabel("Security", SwingConstants.CENTER);
		reasonsForLowRealisation4.setFont(headingFont);
		reasonsForLowRealisation4.setOpaque(true);
		reasonsForLowRealisation4.setBackground(headingBg);
		addComponent(11, 4, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, reasonsForLowRealisation4, gridBag, constraints, securityPanel);

		txtNameOfOwnerLand = new JTextField;
		txtNameOfOwnerBuilding = new JTextField;
		txtNameOfOwnerMachine = new JTextField;
		txtNameOfOwnerFixed = new JTextField;
		txtNameOfOwnerCurrent = new JTextField;
		txtNameOfOwnerOthers = new JTextField;
		cmbOrderOfChargeLand = new JComboBox;
		cmbOrderOfChargeBuilding = new JComboBox;
		cmbOrderOfChargeMachine = new JComboBox;
		cmbOrderOfChargeFixed = new JComboBox;
		cmbOrderOfChargeCurrent = new JComboBox;
		cmbOrderOfChargeOthers = new JComboBox;
		txtPartOfSecurityLand = new JTextField;
		txtPartOfSecurityBuilding = new JTextField;
		txtPartOfSecurityMachine = new JTextField;
		txtPartOfSecurityFixed = new JTextField;
		txtPartOfSecurityCurrent = new JTextField;
		txtPartOfSecurityOthers = new JTextField;
		txtOtherPartOfSecurityLand = new JTextField;
		txtOtherPartOfSecurityBuilding = new JTextField;
		txtOtherPartOfSecurityMachine = new JTextField;
		txtOtherPartOfSecurityFixed = new JTextField;
		txtOtherPartOfSecurityCurrent = new JTextField;
		txtOtherPartOfSecurityOthers = new JTextField;
		txtAsOnSanctionLandValueSI = new JTextField;
		txtAsOnSanctionBuildingValueSI = new JTextField;
		txtAsOnSanctionMachineValueSI = new JTextField;
		txtAsOnSanctionFixedValueSI = new JTextField;
		txtAsOnSanctionCurrentValueSI = new JTextField;
		txtAsOnSanctionOthersValueSI = new JTextField;
		txtAsOnNpaLandValueSI = new JTextField;
		txtAsOnNpaBuildingValueSI = new JTextField;
		txtAsOnNpaMachineValueSI = new JTextField;
		txtAsOnNpaFixedValueSI = new JTextField;
		txtAsOnNpaCurrentValueSI = new JTextField;
		txtAsOnNpaOthersValueSI = new JTextField;
		txtAsOnPreferrmentFILandValue = new JTextField;
		txtAsOnPreferrmentFIBuildingValue = new JTextField;
		txtAsOnPreferrmentFIMachineValue = new JTextField;
		txtAsOnPreferrmentFIFixedValue = new JTextField;
		txtAsOnPreferrmentFICurrentValue = new JTextField;
		txtAsOnPreferrmentFIOthersValue = new JTextField;
		txtAsOnPreferrmentFINetworth = new JTextField;
		txtAsOnPreferrmentSILandValue = new JTextField;
		txtAsOnPreferrmentSIBuildingValue = new JTextField;
		txtAsOnPreferrmentSIMachineValue = new JTextField;
		txtAsOnPreferrmentSIFixedValue = new JTextField;
		txtAsOnPreferrmentSICurrentValue = new JTextField;
		txtAsOnPreferrmentSIOthersValue = new JTextField;
		txtAsOnPreferrmentSINetworth = new JTextField;
		txtAmtRealisedLand = new JTextField;
		txtAmtRealisedBuilding = new JTextField;
		txtAmtRealisedMachine = new JTextField;
		txtAmtRealisedFixed = new JTextField;
		txtAmtRealisedCurrent = new JTextField;
		txtAmtRealisedOthers = new JTextField;
		txtReasonsForLowRealisationLand = new JTextArea;
		txtReasonsForLowRealisationBuilding = new JTextArea;
		txtReasonsForLowRealisationMachine = new JTextArea;
		txtReasonsForLowRealisationFixed = new JTextArea;
		txtReasonsForLowRealisationCurrent = new JTextArea;
		txtReasonsForLowRealisationOthers = new JTextArea;

		JScrollPane txtReasonsForLowRealisationLandPane[] = new JScrollPane;
		JScrollPane txtReasonsForLowRealisationBuildingPane[] = new JScrollPane;
		JScrollPane txtReasonsForLowRealisationMachinePane[] = new JScrollPane;
		JScrollPane txtReasonsForLowRealisationFixedPane[] = new JScrollPane;
		JScrollPane txtReasonsForLowRealisationCurrentPane[] = new JScrollPane;
		JScrollPane txtReasonsForLowRealisationOthersPane[] = new JScrollPane;

		int rowNo = 5;

		land=new JLabel("Land", SwingConstants.CENTER);
		land.setFont(headingFont);
		land.setOpaque(true);
		land.setBackground(dataBg);
		constraints.gridheight=1;
		addComponent(1, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, land, gridBag, constraints, securityPanel);

		txtNameOfOwnerLand[i] = new JTextField(10);
		if ((flag.equals("MOD")) || (flag.equals("VER")))
		{
			txtNameOfOwnerLand.setText(""+ secondInstallmentSecurityDetails.getOwnerNameOfLand());
		}
		addComponent(2, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtNameOfOwnerLand, gridBag, constraints, securityPanel);

		String[] orderOfCharges = { "Select", "First", "Second", "Exclusive", "Pari Passu" };
		cmbOrderOfChargeLand = new JComboBox(orderOfCharges);
		if ((flag.equals("MOD")) || (flag.equals("VER")))
		{
			cmbOrderOfChargeLand.setSelectedItem( secondInstallmentSecurityDetails.getOrderOfChargeLand());
		}
		addComponent(3, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, cmbOrderOfChargeLand, gridBag, constraints, securityPanel);

		txtPartOfSecurityLand = new JTextField(10);
		if ((flag.equals("MOD")) || (flag.equals("VER")))
		{
			txtPartOfSecurityLand.setText(""+ secondInstallmentSecurityDetails.getParticularsLand());
		}
		addComponent(4, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtPartOfSecurityLand, gridBag, constraints, securityPanel);

		txtOtherPartOfSecurityLand = new JTextField(10);
		if ((flag.equals("MOD")) || (flag.equals("VER")))
		{
			txtOtherPartOfSecurityLand.setText(""+ secondInstallmentSecurityDetails.getLocationAddressLand());
		}
		addComponent(5, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtOtherPartOfSecurityLand, gridBag, constraints, securityPanel);

		txtAsOnSanctionLandValueSI = new JTextField(8);
		txtAsOnSanctionLandValueSI.setDocument(new NumberFormatField(13));
		if (secondInstallmentSecurityDetails.getValueAsOnSanctionLand() != null)
		{
			txtAsOnSanctionLandValueSI.setText(""+ secondInstallmentSecurityDetails.getValueAsOnSanctionLand());
		}
		addComponent(6, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAsOnSanctionLandValueSI, gridBag, constraints, securityPanel);

		txtAsOnNpaLandValueSI = new JTextField(8);
		txtAsOnNpaLandValueSI.setDocument(new NumberFormatField(13));
		if (secondInstallmentSecurityDetails.getValueAsOnNpaLand() != null)
		{
			txtAsOnNpaLandValueSI.setText(""+ secondInstallmentSecurityDetails.getValueAsOnNpaLand());
		}
		addComponent(7, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAsOnNpaLandValueSI, gridBag, constraints, securityPanel);

		txtAsOnPreferrmentFILandValue = new JTextField(8);
		txtAsOnPreferrmentFILandValue.setDocument(new NumberFormatField(13));
		if (secondInstallmentSecurityDetails.getValueAsOnFirstInstLand() != null)
		{
			txtAsOnPreferrmentFILandValue.setText(""+ secondInstallmentSecurityDetails.getValueAsOnFirstInstLand());
		}
		addComponent(8, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAsOnPreferrmentFILandValue, gridBag, constraints, securityPanel);

		txtAsOnPreferrmentSILandValue = new JTextField(8);
		txtAsOnPreferrmentSILandValue.setDocument(new NumberFormatField(13));
		if ((flag.equals("MOD")) || (flag.equals("VER")))
		{
			txtAsOnPreferrmentSILandValue.setText(""+ secondInstallmentSecurityDetails.getValueAsOnSecondInstLand());
		}
		addComponent(9, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAsOnPreferrmentSILandValue, gridBag, constraints, securityPanel);

		txtAmtRealisedLand = new JTextField(8);
		txtAmtRealisedLand.setDocument(new NumberFormatField(13));
		if ((flag.equals("MOD")) || (flag.equals("VER")))
		{
			txtAmtRealisedLand.setText(""+ secondInstallmentSecurityDetails.getAmtRealisedLand());
		}
		addComponent(10, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAmtRealisedLand, gridBag, constraints, securityPanel);

		txtReasonsForLowRealisationLand = new JTextArea(2, 10);
		txtReasonsForLowRealisationLand.setLineWrap(true);
		txtReasonsForLowRealisationLandPane = new JScrollPane(txtReasonsForLowRealisationLand, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		if ((flag.equals("MOD")) || (flag.equals("VER")))
		{
			txtReasonsForLowRealisationLand.setText( secondInstallmentSecurityDetails.getLowRealizationReasonsLand());
		}
		addComponent(11, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtReasonsForLowRealisationLandPane, gridBag, constraints, securityPanel);

		rowNo++;

		building=new JLabel("Land", SwingConstants.CENTER);
		building.setFont(headingFont);
		building.setOpaque(true);
		building.setBackground(dataBg);
		constraints.gridheight=1;
		addComponent(1, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, building, gridBag, constraints, securityPanel);

		txtNameOfOwnerBuilding = new JTextField(10);
		if ((flag.equals("MOD")) || (flag.equals("VER")))
		{
			txtNameOfOwnerBuilding.setText(""+ secondInstallmentSecurityDetails.getOwnerNameOfBuilding());
		}
		addComponent(2, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtNameOfOwnerBuilding, gridBag, constraints, securityPanel);

		cmbOrderOfChargeBuilding = new JComboBox(orderOfCharges);
		if ((flag.equals("MOD")) || (flag.equals("VER")))
		{
			cmbOrderOfChargeBuilding.setSelectedItem( secondInstallmentSecurityDetails.getOrderOfChargeBuilding());
		}
		addComponent(3, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, cmbOrderOfChargeBuilding, gridBag, constraints, securityPanel);

		txtPartOfSecurityBuilding = new JTextField(10);
		if ((flag.equals("MOD")) || (flag.equals("VER")))
		{
			txtPartOfSecurityBuilding.setText(""+ secondInstallmentSecurityDetails.getParticularsBuilding());
		}
		addComponent(4, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtPartOfSecurityBuilding, gridBag, constraints, securityPanel);

		txtOtherPartOfSecurityBuilding = new JTextField(10);
		if ((flag.equals("MOD")) || (flag.equals("VER")))
		{
			txtOtherPartOfSecurityBuilding.setText(""+ secondInstallmentSecurityDetails.getLocationAddressBuilding());
		}
		addComponent(5, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtOtherPartOfSecurityBuilding, gridBag, constraints, securityPanel);

		txtAsOnSanctionBuildingValueSI = new JTextField(8);
		txtAsOnSanctionBuildingValueSI.setDocument(new NumberFormatField(13));
		if (secondInstallmentSecurityDetails.getValueAsOnSanctionBuilding() != null)
		{
			txtAsOnSanctionBuildingValueSI.setText(""+ secondInstallmentSecurityDetails.getValueAsOnSanctionBuilding());
		}
		addComponent(6, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAsOnSanctionBuildingValueSI, gridBag, constraints, securityPanel);

		txtAsOnNpaBuildingValueSI = new JTextField(8);
		txtAsOnNpaBuildingValueSI.setDocument(new NumberFormatField(13));
		if (secondInstallmentSecurityDetails.getValueAsOnNpaBuilding() != null)
		{
			txtAsOnNpaBuildingValueSI.setText(""+ secondInstallmentSecurityDetails.getValueAsOnNpaBuilding());
		}
		addComponent(7, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAsOnNpaBuildingValueSI, gridBag, constraints, securityPanel);

		txtAsOnPreferrmentFIBuildingValue = new JTextField(8);
		txtAsOnPreferrmentFIBuildingValue.setDocument(new NumberFormatField(13));
		if (secondInstallmentSecurityDetails.getValueAsOnFirstInstBuilding() != null)
		{
			txtAsOnPreferrmentFIBuildingValue.setText(""+ secondInstallmentSecurityDetails.getValueAsOnFirstInstBuilding());
		}
		addComponent(8, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAsOnPreferrmentFIBuildingValue, gridBag, constraints, securityPanel);

		txtAsOnPreferrmentSIBuildingValue = new JTextField(8);
		txtAsOnPreferrmentSIBuildingValue.setDocument(new NumberFormatField(13));
		if ((flag.equals("MOD")) || (flag.equals("VER")))
		{
			txtAsOnPreferrmentSIBuildingValue.setText(""+ secondInstallmentSecurityDetails.getValueAsOnSecondInstBuilding());
		}
		addComponent(9, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAsOnPreferrmentSIBuildingValue, gridBag, constraints, securityPanel);

		txtAmtRealisedBuilding = new JTextField(8);
		txtAmtRealisedBuilding.setDocument(new NumberFormatField(13));
		if ((flag.equals("MOD")) || (flag.equals("VER")))
		{
			txtAmtRealisedBuilding.setText(""+ secondInstallmentSecurityDetails.getAmtRealisedBuilding());
		}
		addComponent(10, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAmtRealisedBuilding, gridBag, constraints, securityPanel);

		txtReasonsForLowRealisationBuilding = new JTextArea(2, 10);
		txtReasonsForLowRealisationBuilding.setLineWrap(true);
		txtReasonsForLowRealisationBuildingPane = new JScrollPane(txtReasonsForLowRealisationBuilding, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		if ((flag.equals("MOD")) || (flag.equals("VER")))
		{
			txtReasonsForLowRealisationBuilding.setText( secondInstallmentSecurityDetails.getLowRealizationReasonsBuilding());
		}
		addComponent(11, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtReasonsForLowRealisationBuildingPane, gridBag, constraints, securityPanel);

		rowNo++;

		machine=new JLabel("Machine", SwingConstants.CENTER);
		machine.setFont(headingFont);
		machine.setOpaque(true);
		machine.setBackground(dataBg);
		constraints.gridheight=1;
		addComponent(1, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, machine, gridBag, constraints, securityPanel);

		txtNameOfOwnerMachine = new JTextField(10);
		if ((flag.equals("MOD")) || (flag.equals("VER")))
		{
			txtNameOfOwnerMachine.setText(""+ secondInstallmentSecurityDetails.getOwnerNameOfMachine());
		}
		addComponent(2, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtNameOfOwnerMachine, gridBag, constraints, securityPanel);

		cmbOrderOfChargeMachine = new JComboBox(orderOfCharges);
		if ((flag.equals("MOD")) || (flag.equals("VER")))
		{
			cmbOrderOfChargeMachine.setSelectedItem( secondInstallmentSecurityDetails.getOrderOfChargeMachine());
		}
		addComponent(3, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, cmbOrderOfChargeMachine, gridBag, constraints, securityPanel);

		txtPartOfSecurityMachine = new JTextField(10);
		if ((flag.equals("MOD")) || (flag.equals("VER")))
		{
			txtPartOfSecurityMachine.setText(""+ secondInstallmentSecurityDetails.getParticularsMachine());
		}
		addComponent(4, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtPartOfSecurityMachine, gridBag, constraints, securityPanel);

		txtOtherPartOfSecurityMachine = new JTextField(10);
		if ((flag.equals("MOD")) || (flag.equals("VER")))
		{
			txtOtherPartOfSecurityMachine.setText(""+ secondInstallmentSecurityDetails.getLocationAddressMachine());
		}
		addComponent(5, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtOtherPartOfSecurityMachine, gridBag, constraints, securityPanel);

		txtAsOnSanctionMachineValueSI = new JTextField(8);
		txtAsOnSanctionMachineValueSI.setDocument(new NumberFormatField(13));
		if (secondInstallmentSecurityDetails.getValueAsOnSanctionMachine() != null)
		{
			txtAsOnSanctionMachineValueSI.setText(""+ secondInstallmentSecurityDetails.getValueAsOnSanctionMachine());
		}
		addComponent(6, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAsOnSanctionMachineValueSI, gridBag, constraints, securityPanel);

		txtAsOnNpaMachineValueSI = new JTextField(8);
		txtAsOnNpaMachineValueSI.setDocument(new NumberFormatField(13));
		if (secondInstallmentSecurityDetails.getValueAsOnNpaMachine() != null)
		{
			txtAsOnNpaMachineValueSI.setText(""+ secondInstallmentSecurityDetails.getValueAsOnNpaMachine());
		}
		addComponent(7, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAsOnNpaMachineValueSI, gridBag, constraints, securityPanel);

		txtAsOnPreferrmentFIMachineValue = new JTextField(8);
		txtAsOnPreferrmentFIMachineValue.setDocument(new NumberFormatField(13));
		if (secondInstallmentSecurityDetails.getValueAsOnFirstInstMachine() != null)
		{
			txtAsOnPreferrmentFIMachineValue.setText(""+ secondInstallmentSecurityDetails.getValueAsOnFirstInstMachine());
		}
		addComponent(8, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAsOnPreferrmentFIMachineValue, gridBag, constraints, securityPanel);

		txtAsOnPreferrmentSIMachineValue = new JTextField(8);
		txtAsOnPreferrmentSIMachineValue.setDocument(new NumberFormatField(13));
		if ((flag.equals("MOD")) || (flag.equals("VER")))
		{
			txtAsOnPreferrmentSIMachineValue.setText(""+ secondInstallmentSecurityDetails.getValueAsOnSecondInstMachine());
		}
		addComponent(9, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAsOnPreferrmentSIMachineValue, gridBag, constraints, securityPanel);

		txtAmtRealisedMachine = new JTextField(8);
		txtAmtRealisedMachine.setDocument(new NumberFormatField(13));
		if ((flag.equals("MOD")) || (flag.equals("VER")))
		{
			txtAmtRealisedMachine.setText(""+ secondInstallmentSecurityDetails.getAmtRealisedMachine());
		}
		addComponent(10, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAmtRealisedMachine, gridBag, constraints, securityPanel);

		txtReasonsForLowRealisationMachine = new JTextArea(2, 10);
		txtReasonsForLowRealisationMachine.setLineWrap(true);
		txtReasonsForLowRealisationMachinePane = new JScrollPane(txtReasonsForLowRealisationMachine, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		if ((flag.equals("MOD")) || (flag.equals("VER")))
		{
			txtReasonsForLowRealisationMachine.setText( secondInstallmentSecurityDetails.getLowRealizationReasonsMachine());
		}
		addComponent(11, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtReasonsForLowRealisationMachinePane, gridBag, constraints, securityPanel);

		rowNo++;

		otherAssets=new JLabel("Machine", SwingConstants.CENTER);
		otherAssets.setFont(headingFont);
		otherAssets.setOpaque(true);
		otherAssets.setBackground(dataBg);
		constraints.gridheight=1;
		addComponent(1, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, otherAssets, gridBag, constraints, securityPanel);

		txtNameOfOwnerFixed = new JTextField(10);
		if ((flag.equals("MOD")) || (flag.equals("VER")))
		{
			txtNameOfOwnerFixed.setText(""+ secondInstallmentSecurityDetails.getOwnerNameOfOtherFixedMovableAssets());
		}
		addComponent(2, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtNameOfOwnerFixed, gridBag, constraints, securityPanel);

		cmbOrderOfChargeFixed = new JComboBox(orderOfCharges);
		if ((flag.equals("MOD")) || (flag.equals("VER")))
		{
			cmbOrderOfChargeFixed.setSelectedItem( secondInstallmentSecurityDetails.getOrderOfChargeOtherFixedMovableAssets());
		}
		addComponent(3, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, cmbOrderOfChargeFixed, gridBag, constraints, securityPanel);

		txtPartOfSecurityFixed = new JTextField(10);
		if ((flag.equals("MOD")) || (flag.equals("VER")))
		{
			txtPartOfSecurityFixed.setText(""+ secondInstallmentSecurityDetails.getParticularsOtherFixedMovableAssets());
		}
		addComponent(4, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtPartOfSecurityFixed, gridBag, constraints, securityPanel);

		txtOtherPartOfSecurityFixed = new JTextField(10);
		if ((flag.equals("MOD")) || (flag.equals("VER")))
		{
			txtOtherPartOfSecurityFixed.setText(""+ secondInstallmentSecurityDetails.getLocationAddressOtherFixedMovableAssets());
		}
		addComponent(5, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtOtherPartOfSecurityFixed, gridBag, constraints, securityPanel);

		txtAsOnSanctionFixedValueSI = new JTextField(8);
		txtAsOnSanctionFixedValueSI.setDocument(new NumberFormatField(13));
		if (secondInstallmentSecurityDetails.getValueAsOnSanctionOtherFixedMovableAssets() != null)
		{
			txtAsOnSanctionFixedValueSI.setText(""+ secondInstallmentSecurityDetails.getValueAsOnSanctionOtherFixedMovableAssets());
		}
		addComponent(6, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAsOnSanctionFixedValueSI, gridBag, constraints, securityPanel);

		txtAsOnNpaFixedValueSI = new JTextField(8);
		txtAsOnNpaFixedValueSI.setDocument(new NumberFormatField(13));
		if (secondInstallmentSecurityDetails.getValueAsOnNpaOtherFixedMovableAssets() != null)
		{
			txtAsOnNpaFixedValueSI.setText(""+ secondInstallmentSecurityDetails.getValueAsOnNpaOtherFixedMovableAssets());
		}
		addComponent(7, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAsOnNpaFixedValueSI, gridBag, constraints, securityPanel);

		txtAsOnPreferrmentFIFixedValue = new JTextField(8);
		txtAsOnPreferrmentFIFixedValue.setDocument(new NumberFormatField(13));
		if (secondInstallmentSecurityDetails.getValueAsOnFirstInstOtherFixedMovableAssets() != null)
		{
			txtAsOnPreferrmentFIFixedValue.setText(""+ secondInstallmentSecurityDetails.getValueAsOnFirstInstOtherFixedMovableAssets());
		}
		addComponent(8, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAsOnPreferrmentFIFixedValue, gridBag, constraints, securityPanel);

		txtAsOnPreferrmentSIFixedValue = new JTextField(8);
		txtAsOnPreferrmentSIFixedValue.setDocument(new NumberFormatField(13));
		if ((flag.equals("MOD")) || (flag.equals("VER")))
		{
			txtAsOnPreferrmentSIFixedValue.setText(""+ secondInstallmentSecurityDetails.getValueAsOnSecondInstOtherFixedMovableAssets());
		}
		addComponent(9, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAsOnPreferrmentSIFixedValue, gridBag, constraints, securityPanel);

		txtAmtRealisedFixed = new JTextField(8);
		txtAmtRealisedFixed.setDocument(new NumberFormatField(13));
		if ((flag.equals("MOD")) || (flag.equals("VER")))
		{
			txtAmtRealisedFixed.setText(""+ secondInstallmentSecurityDetails.getAmtRealisedOtherFixedMovableAssets());
		}
		addComponent(10, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAmtRealisedFixed, gridBag, constraints, securityPanel);

		txtReasonsForLowRealisationFixed = new JTextArea(2, 10);
		txtReasonsForLowRealisationFixed.setLineWrap(true);
		txtReasonsForLowRealisationFixedPane = new JScrollPane(txtReasonsForLowRealisationFixed, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		if ((flag.equals("MOD")) || (flag.equals("VER")))
		{
			txtReasonsForLowRealisationFixed.setText( secondInstallmentSecurityDetails.getLowRealizationReasonsOtherFixedMovableAssets());
		}
		addComponent(11, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtReasonsForLowRealisationFixedPane, gridBag, constraints, securityPanel);

		rowNo++;

		currentAssets=new JLabel("Current Assets", SwingConstants.CENTER);
		currentAssets.setFont(headingFont);
		currentAssets.setOpaque(true);
		currentAssets.setBackground(dataBg);
		constraints.gridheight=1;
		addComponent(1, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, currentAssets, gridBag, constraints, securityPanel);

		txtNameOfOwnerCurrent = new JTextField(10);
		if ((flag.equals("MOD")) || (flag.equals("VER")))
		{
			txtNameOfOwnerCurrent.setText(""+ secondInstallmentSecurityDetails.getOwnerNameOfCurrentAssets());
		}
		addComponent(2, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtNameOfOwnerCurrent, gridBag, constraints, securityPanel);

		cmbOrderOfChargeCurrent = new JComboBox(orderOfCharges);
		if ((flag.equals("MOD")) || (flag.equals("VER")))
		{
			cmbOrderOfChargeCurrent.setSelectedItem( secondInstallmentSecurityDetails.getOrderOfChargeCurrentAssets());
		}
		addComponent(3, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, cmbOrderOfChargeCurrent, gridBag, constraints, securityPanel);

		txtPartOfSecurityCurrent = new JTextField(10);
		if ((flag.equals("MOD")) || (flag.equals("VER")))
		{
			txtPartOfSecurityCurrent.setText(""+ secondInstallmentSecurityDetails.getParticularsCurrentAssets());
		}
		addComponent(4, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtPartOfSecurityCurrent, gridBag, constraints, securityPanel);

		txtOtherPartOfSecurityCurrent = new JTextField(10);
		if ((flag.equals("MOD")) || (flag.equals("VER")))
		{
			txtOtherPartOfSecurityCurrent.setText(""+ secondInstallmentSecurityDetails.getLocationAddressCurrentAssets());
		}
		addComponent(5, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtOtherPartOfSecurityCurrent, gridBag, constraints, securityPanel);

		txtAsOnSanctionCurrentValueSI = new JTextField(8);
		txtAsOnSanctionCurrentValueSI.setDocument(new NumberFormatField(13));
		if (secondInstallmentSecurityDetails.getValueAsOnSanctionCurrentAssets() != null)
		{
			txtAsOnSanctionCurrentValueSI.setText(""+ secondInstallmentSecurityDetails.getValueAsOnSanctionCurrentAssets());
		}
		addComponent(6, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAsOnSanctionCurrentValueSI, gridBag, constraints, securityPanel);

		txtAsOnNpaCurrentValueSI = new JTextField(8);
		txtAsOnNpaCurrentValueSI.setDocument(new NumberFormatField(13));
		if (secondInstallmentSecurityDetails.getValueAsOnNpaCurrentAssets() != null)
		{
			txtAsOnNpaCurrentValueSI.setText(""+ secondInstallmentSecurityDetails.getValueAsOnNpaCurrentAssets());
		}
		addComponent(7, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAsOnNpaCurrentValueSI, gridBag, constraints, securityPanel);

		txtAsOnPreferrmentFICurrentValue = new JTextField(8);
		txtAsOnPreferrmentFICurrentValue.setDocument(new NumberFormatField(13));
		if (secondInstallmentSecurityDetails.getValueAsOnFirstInstCurrentAssets() != null)
		{
			txtAsOnPreferrmentFICurrentValue.setText(""+ secondInstallmentSecurityDetails.getValueAsOnFirstInstCurrentAssets());
		}
		addComponent(8, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAsOnPreferrmentFICurrentValue, gridBag, constraints, securityPanel);

		txtAsOnPreferrmentSICurrentValue = new JTextField(8);
		txtAsOnPreferrmentSICurrentValue.setDocument(new NumberFormatField(13));
		if ((flag.equals("MOD")) || (flag.equals("VER")))
		{
			txtAsOnPreferrmentSICurrentValue.setText(""+ secondInstallmentSecurityDetails.getValueAsOnSecondInstCurrentAssets());
		}
		addComponent(9, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAsOnPreferrmentSICurrentValue, gridBag, constraints, securityPanel);

		txtAmtRealisedCurrent = new JTextField(8);
		txtAmtRealisedCurrent.setDocument(new NumberFormatField(13));
		if ((flag.equals("MOD")) || (flag.equals("VER")))
		{
			txtAmtRealisedCurrent.setText(""+ secondInstallmentSecurityDetails.getAmtRealisedCurrentAssets());
		}
		addComponent(10, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAmtRealisedCurrent, gridBag, constraints, securityPanel);

		txtReasonsForLowRealisationCurrent = new JTextArea(2, 10);
		txtReasonsForLowRealisationCurrent.setLineWrap(true);
		txtReasonsForLowRealisationCurrentPane = new JScrollPane(txtReasonsForLowRealisationCurrent, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		if ((flag.equals("MOD")) || (flag.equals("VER")))
		{
			txtReasonsForLowRealisationCurrent.setText( secondInstallmentSecurityDetails.getLowRealizationReasonsCurrentAssets());
		}
		addComponent(11, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtReasonsForLowRealisationCurrentPane, gridBag, constraints, securityPanel);

		rowNo++;

		others=new JLabel("Others", SwingConstants.CENTER);
		others.setFont(headingFont);
		others.setOpaque(true);
		others.setBackground(dataBg);
		constraints.gridheight=1;
		addComponent(1, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, others, gridBag, constraints, securityPanel);

		txtNameOfOwnerOthers = new JTextField(10);
		if ((flag.equals("MOD")) || (flag.equals("VER")))
		{
			txtNameOfOwnerOthers.setText(""+ secondInstallmentSecurityDetails.getOwnerNameOfOthers());
		}
		addComponent(2, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtNameOfOwnerOthers, gridBag, constraints, securityPanel);

		cmbOrderOfChargeOthers = new JComboBox(orderOfCharges);
		if ((flag.equals("MOD")) || (flag.equals("VER")))
		{
			cmbOrderOfChargeOthers.setSelectedItem( secondInstallmentSecurityDetails.getOrderOfChargeOthers());
		}
		addComponent(3, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, cmbOrderOfChargeOthers, gridBag, constraints, securityPanel);

		txtPartOfSecurityOthers = new JTextField(10);
		if ((flag.equals("MOD")) || (flag.equals("VER")))
		{
			txtPartOfSecurityOthers.setText(""+ secondInstallmentSecurityDetails.getParticularsOthers());
		}
		addComponent(4, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtPartOfSecurityOthers, gridBag, constraints, securityPanel);

		txtOtherPartOfSecurityOthers = new JTextField(10);
		if ((flag.equals("MOD")) || (flag.equals("VER")))
		{
			txtOtherPartOfSecurityOthers.setText(""+ secondInstallmentSecurityDetails.getLocationAddressOthers());
		}
		addComponent(5, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtOtherPartOfSecurityOthers, gridBag, constraints, securityPanel);

		txtAsOnSanctionOthersValueSI = new JTextField(8);
		txtAsOnSanctionOthersValueSI.setDocument(new NumberFormatField(13));
		if (secondInstallmentSecurityDetails.getValueAsOnSanctionOthers() != null)
		{
			txtAsOnSanctionOthersValueSI.setText(""+ secondInstallmentSecurityDetails.getValueAsOnSanctionOthers());
		}
		addComponent(6, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAsOnSanctionOthersValueSI, gridBag, constraints, securityPanel);

		txtAsOnNpaOthersValueSI = new JTextField(8);
		txtAsOnNpaOthersValueSI.setDocument(new NumberFormatField(13));
		if (secondInstallmentSecurityDetails.getValueAsOnNpaOthers() != null)
		{
			txtAsOnNpaOthersValueSI.setText(""+ secondInstallmentSecurityDetails.getValueAsOnNpaOthers());
		}
		addComponent(7, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAsOnNpaOthersValueSI, gridBag, constraints, securityPanel);

		txtAsOnPreferrmentFIOthersValue = new JTextField(8);
		txtAsOnPreferrmentFIOthersValue.setDocument(new NumberFormatField(13));
		if (secondInstallmentSecurityDetails.getValueAsOnFirstInstOthers() != null)
		{
			txtAsOnPreferrmentFIOthersValue.setText(""+ secondInstallmentSecurityDetails.getValueAsOnFirstInstOthers());
		}
		addComponent(8, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAsOnPreferrmentFIOthersValue, gridBag, constraints, securityPanel);

		txtAsOnPreferrmentSIOthersValue = new JTextField(8);
		txtAsOnPreferrmentSIOthersValue.setDocument(new NumberFormatField(13));
		if ((flag.equals("MOD")) || (flag.equals("VER")))
		{
			txtAsOnPreferrmentSIOthersValue.setText(""+ secondInstallmentSecurityDetails.getValueAsOnSecondInstOthers());
		}
		addComponent(9, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAsOnPreferrmentSIOthersValue, gridBag, constraints, securityPanel);

		txtAmtRealisedOthers = new JTextField(8);
		txtAmtRealisedOthers.setDocument(new NumberFormatField(13));
		if ((flag.equals("MOD")) || (flag.equals("VER")))
		{
			txtAmtRealisedOthers.setText(""+ secondInstallmentSecurityDetails.getAmtRealisedOthers());
		}
		addComponent(10, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAmtRealisedOthers, gridBag, constraints, securityPanel);

		txtReasonsForLowRealisationOthers = new JTextArea(2, 10);
		txtReasonsForLowRealisationOthers.setLineWrap(true);
		txtReasonsForLowRealisationOthersPane = new JScrollPane(txtReasonsForLowRealisationOthers, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		if ((flag.equals("MOD")) || (flag.equals("VER")))
		{
			txtReasonsForLowRealisationOthers.setText( secondInstallmentSecurityDetails.getLowRealizationReasonsOthers());
		}
		addComponent(11, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtReasonsForLowRealisationOthersPane, gridBag, constraints, securityPanel);

		rowNo++;
	}

	/**
	* This method displays the personal guarantee details in the second installment claim screen.
	*
	private void displayClaimsPersonalGuaranteeDetailsSI(ArrayList personalGuaranteeDetails, String flag)
	{
		personalGuaraneePanel.removeAll();
		personalGuaraneePanel.setBackground(dataBg);
		personalGuaraneePanel.setLayout(gridBag);

		nameOfGuarantors=new JLabel("Name Of Guarantors", SwingConstants.CENTER);
		nameOfGuarantors.setFont(headingFont);
		nameOfGuarantors.setOpaque(true);
		nameOfGuarantors.setBackground(headingBg);
		addComponent(1, 0, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, nameOfGuarantors, gridBag, constraints, personalGuaraneePanel);

		promotersOfUnit1=new JLabel("Proprietor / Partner /", SwingConstants.CENTER);
		promotersOfUnit1.setFont(headingFont);
		promotersOfUnit1.setOpaque(true);
		promotersOfUnit1.setBackground(headingBg);
		addComponent(2, 0, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, promotersOfUnit1, gridBag, constraints, personalGuaraneePanel);

		networthCol=new JLabel("Networth of Guarantors As On", SwingConstants.CENTER);
		networthCol.setFont(headingFont);
		networthCol.setOpaque(true);
		networthCol.setBackground(headingBg);
		addComponent(3, 0, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, networthCol, gridBag, constraints, personalGuaraneePanel);

		amtRealised1=new JLabel("Amount Realised through", SwingConstants.CENTER);
		amtRealised1.setFont(headingFont);
		amtRealised1.setOpaque(true);
		amtRealised1.setBackground(headingBg);
		addComponent(4, 0, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, amtRealised1, gridBag, constraints, personalGuaraneePanel);

		promotersOfUnit2=new JLabel("Promoter Directors", SwingConstants.CENTER);
		promotersOfUnit2.setFont(headingFont);
		promotersOfUnit2.setOpaque(true);
		promotersOfUnit2.setBackground(headingBg);
		addComponent(2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, promotersOfUnit2, gridBag, constraints, personalGuaraneePanel);

		preferrment=new JLabel("Preferrment of Claim", SwingConstants.CENTER);
		preferrment.setFont(headingFont);
		preferrment.setOpaque(true);
		preferrment.setBackground(headingBg);
		addComponent(8, 1, 2, GridBagConstraints.WEST, GridBagConstraints.BOTH, preferrment, gridBag, constraints, securityPanel);
	}

	/**
	* This method displays the recovery made details in the claims screen.
	*/
	private void displayClaimsRecoveryDetails(Vector recoveryInfo, String flag)
	{
		String text = "";

		recoveryMadePanel.removeAll();
		recoveryMadePanel.setBackground(dataBg);
		recoveryMadePanel.setLayout(gridBag);

		slNo=new JLabel("Sl No", SwingConstants.CENTER);
		slNo.setFont(headingFont);
		slNo.setOpaque(true);
		slNo.setBackground(headingBg);
		constraints.gridheight=3;
		addComponent(0, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, slNo, gridBag, constraints, recoveryMadePanel);

		cgpanCol=new JLabel("CGPAN", SwingConstants.CENTER);
		cgpanCol.setFont(headingFont);
		cgpanCol.setOpaque(true);
		cgpanCol.setBackground(headingBg);
		addComponent(1, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, cgpanCol, gridBag, constraints, recoveryMadePanel);

		termCompLoan=new JLabel("Term / Composite Loan (in Rs.)", SwingConstants.CENTER);
		termCompLoan.setFont(headingFont);
		termCompLoan.setOpaque(true);
		termCompLoan.setBackground(headingBg);
		constraints.gridheight=1;
		addComponent(2, 0, 2, GridBagConstraints.WEST, GridBagConstraints.BOTH, termCompLoan, gridBag, constraints, recoveryMadePanel);

		workingCapital=new JLabel("Working Capital (in Rs.)", SwingConstants.CENTER);
		workingCapital.setFont(headingFont);
		workingCapital.setOpaque(true);
		workingCapital.setBackground(headingBg);
		addComponent(4, 0, 2, GridBagConstraints.WEST, GridBagConstraints.BOTH, workingCapital, gridBag, constraints, recoveryMadePanel);

		recoveryMode=new JLabel("Mode of Recovery", SwingConstants.CENTER);
		recoveryMode.setFont(headingFont);
		recoveryMode.setOpaque(true);
		recoveryMode.setBackground(headingBg);
		constraints.gridheight=3;
		addComponent(6, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, recoveryMode, gridBag, constraints, recoveryMadePanel);

		principalCol=new JLabel("Principal", SwingConstants.CENTER);
		principalCol.setFont(headingFont);
		principalCol.setOpaque(true);
		principalCol.setBackground(headingBg);
		constraints.gridheight=2;
		addComponent(2, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, principalCol, gridBag, constraints, recoveryMadePanel);

		interestCol=new JLabel("Interest and ", SwingConstants.CENTER);
		interestCol.setFont(headingFont);
		interestCol.setOpaque(true);
		interestCol.setBackground(headingBg);
		constraints.gridheight=1;
		addComponent(3, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, interestCol, gridBag, constraints, recoveryMadePanel);

		amountCol=new JLabel("Amount Including ", SwingConstants.CENTER);
		amountCol.setFont(headingFont);
		amountCol.setOpaque(true);
		amountCol.setBackground(headingBg);
		constraints.gridheight=1;
		addComponent(4, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, amountCol, gridBag, constraints, recoveryMadePanel);

		otherCol=new JLabel("Other Charges", SwingConstants.CENTER);
		otherCol.setFont(headingFont);
		otherCol.setOpaque(true);
		otherCol.setBackground(headingBg);
		constraints.gridheight=2;
		addComponent(5, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, otherCol, gridBag, constraints, recoveryMadePanel);
		
		interestCol=new JLabel("Other Charges", SwingConstants.CENTER);
		interestCol.setFont(headingFont);
		interestCol.setOpaque(true);
		interestCol.setBackground(headingBg);
		constraints.gridheight=1;
		addComponent(3, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, interestCol, gridBag, constraints, recoveryMadePanel);
		
		amountCol=new JLabel("Interest", SwingConstants.CENTER);
		amountCol.setFont(headingFont);
		amountCol.setOpaque(true);
		amountCol.setBackground(headingBg);
		constraints.gridheight=1;
		addComponent(4, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, amountCol, gridBag, constraints, recoveryMadePanel);

		int recoverySize=recoveryInfo.size();
		noOfClaimRec = recoverySize;
		int i=0;
		com.cgtsi.claim.RecoveryDetails recoveryDetails;

		slNoValue=new JLabel[recoverySize];
		cgpanRecoveryValue=new JLabel[recoverySize];
		txtPrincipalTCRecovery=new JTextField[recoverySize];
		txtInterestTCRecovery=new JTextField[recoverySize];
		txtAmountWCRecovery=new JTextField[recoverySize];
		txtOthersWCRecovery=new JTextField[recoverySize];
		cmbModeOfRecovery=new JComboBox[recoverySize];

		int rowNo=3;

		for (i=0;i<recoverySize;i++)
		{
			recoveryDetails = (com.cgtsi.claim.RecoveryDetails) recoveryInfo.get(i);

			slNoValue[i] = new JLabel(""+(i+1), SwingConstants.CENTER);
			slNoValue[i].setFont(dataFont);
			slNoValue[i].setOpaque(true);
			slNoValue[i].setBackground(dataBg);
			addComponent(0, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, slNoValue[i], gridBag, constraints, recoveryMadePanel);

			cgpanRecoveryValue[i] = new JLabel(recoveryDetails.getCgpan(), SwingConstants.CENTER);
			cgpanRecoveryValue[i].setFont(dataFont);
			cgpanRecoveryValue[i].setOpaque(true);
			cgpanRecoveryValue[i].setBackground(dataBg);
			addComponent(1, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, cgpanRecoveryValue[i], gridBag, constraints, recoveryMadePanel);
			
			String cgpan = recoveryDetails.getCgpan();
			String type ="";
			if (cgpan.substring(11, cgpan.length()-1).equalsIgnoreCase("T"))
			{
				type="TC";
			}
			else if (cgpan.substring(11, cgpan.length()-1).equalsIgnoreCase("W") || cgpan.substring(11, cgpan.length()-1).equalsIgnoreCase("R"))
			{
				type="WC";
			}

			txtPrincipalTCRecovery[i] = new JTextField(10);
			txtPrincipalTCRecovery[i].setDocument(new DecimalFormatField(13,2));
			if (type.equalsIgnoreCase("WC"))
			{
				txtPrincipalTCRecovery[i].setEnabled(false);
			}
			else
			{
//				if ((flag.equals("MOD")) || (flag.equals("VER")))
//				{
					text = df.format(recoveryDetails.getTcPrincipal());
					txtPrincipalTCRecovery[i].setText(text);
//				}
			}
			addComponent(2, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtPrincipalTCRecovery[i], gridBag, constraints, recoveryMadePanel);

			txtInterestTCRecovery[i] = new JTextField(10);
			txtInterestTCRecovery[i].setDocument(new DecimalFormatField(13,2));
			if (type.equalsIgnoreCase("WC"))
			{
				txtInterestTCRecovery[i].setEnabled(false);
			}
			else
			{
//				if ((flag.equals("MOD")) || (flag.equals("VER")))
//				{
					text = df.format(recoveryDetails.getTcInterestAndOtherCharges());
					txtInterestTCRecovery[i].setText(text);
//				}
			}
			addComponent(3, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtInterestTCRecovery[i], gridBag, constraints, recoveryMadePanel);

			txtAmountWCRecovery[i] = new JTextField(10);
			txtAmountWCRecovery[i].setDocument(new DecimalFormatField(13,2));
			if (type.equalsIgnoreCase("TC"))
			{
				txtAmountWCRecovery[i].setEnabled(false);
			}
			else
			{
//				if ((flag.equals("MOD")) || (flag.equals("VER")))
//				{
					text = df.format(recoveryDetails.getWcAmount());
					txtAmountWCRecovery[i].setText(text);
//				}
			}
			addComponent(4, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAmountWCRecovery[i], gridBag, constraints, recoveryMadePanel);

			txtOthersWCRecovery[i] = new JTextField(10);
			txtOthersWCRecovery[i].setDocument(new DecimalFormatField(13,2));
			if (type.equalsIgnoreCase("TC"))
			{
				txtOthersWCRecovery[i].setEnabled(false);
			}
			else
			{
//				if ((flag.equals("MOD")) || (flag.equals("VER")))
//				{
					text = df.format(recoveryDetails.getWcOtherCharges());
					txtOthersWCRecovery[i].setText(text);
//				}
			}
			addComponent(5, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtOthersWCRecovery[i], gridBag, constraints, recoveryMadePanel);

			String[] recoveryModes = {"Select", "OTS", "Recall of Credit facility", "Invocation of Personal Guarantee", "Filing of Suit in Civil Court or DRT", "Registration of Case with Lok adalat etc", "Actions under Securitization Act", "Others"};
			cmbModeOfRecovery[i] = new JComboBox(recoveryModes);
//			if ((flag.equals("MOD")) || (flag.equals("VER")))
//			{
				text = recoveryDetails.getModeOfRecovery();
				if (text!=null && !text.equals(""))
				{
					cmbModeOfRecovery[i].setSelectedItem(text);
				}
//			}
			addComponent(6, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, cmbModeOfRecovery[i], gridBag, constraints, recoveryMadePanel);

			rowNo++;
			recoveryDetails = null;
		}
	}

	/**
	* This method displays the claims summary detail in the claims screen.
	*/
	private void displayClaimsSummaryDetails(ArrayList summary, String flag, boolean first)
	{
		String text = "";

		claimSummaryPanel.removeAll();
		claimSummaryPanel.setBackground(dataBg);
		claimSummaryPanel.setLayout(gridBag);

		slNo=new JLabel("Sl No", SwingConstants.CENTER);
		slNo.setFont(headingFont);
		slNo.setOpaque(true);
		slNo.setBackground(headingBg);
		if (! first)
		{
			constraints.gridheight=4;
			slNo.setVerticalAlignment(JLabel.TOP);
		}
		addComponent(0, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, slNo, gridBag, constraints, claimSummaryPanel);

		cgpanCol=new JLabel("CGPAN", SwingConstants.CENTER);
		cgpanCol.setFont(headingFont);
		cgpanCol.setOpaque(true);
		cgpanCol.setBackground(headingBg);
		if (! first)
		{
			constraints.gridheight=4;
			cgpanCol.setVerticalAlignment(JLabel.TOP);
		}
		addComponent(1, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, cgpanCol, gridBag, constraints, claimSummaryPanel);

		if (! first)
		{
			facilityType=new JLabel("Type of ", SwingConstants.CENTER);
			facilityType.setFont(headingFont);
			facilityType.setOpaque(true);
			facilityType.setBackground(headingBg);
			constraints.gridheight=1;
			addComponent(2, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, facilityType, gridBag, constraints, claimSummaryPanel);
		}

		if (first)
		{
			limitCovered=new JLabel("Loan / Limit Covered under CGFSI", SwingConstants.CENTER);
		}
		else
		{
			limitCovered=new JLabel("Loan / Limit Covered", SwingConstants.CENTER);
		}
		limitCovered.setFont(headingFont);
		limitCovered.setOpaque(true);
		limitCovered.setBackground(headingBg);
		addComponent(3, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, limitCovered, gridBag, constraints, claimSummaryPanel);

		if (first)
		{
			amountClaimed=new JLabel("* Amount Claimed (in Rs.)", SwingConstants.CENTER);
		}
		else
		{
			amountClaimed=new JLabel("Amount Claimed in", SwingConstants.CENTER);
		}
		amountClaimed.setFont(headingFont);
		amountClaimed.setOpaque(true);
		amountClaimed.setBackground(headingBg);
		addComponent(4, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, amountClaimed, gridBag, constraints, claimSummaryPanel);

		if (! first)
		{
			amtSettled=new JLabel("Amount Settled", SwingConstants.CENTER);
			amtSettled.setFont(headingFont);
			amtSettled.setOpaque(true);
			amtSettled.setBackground(headingBg);
			addComponent(5, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, amtSettled, gridBag, constraints, claimSummaryPanel);

			dtOfSettlement=new JLabel("Date of Settlement", SwingConstants.CENTER);
			dtOfSettlement.setFont(headingFont);
			dtOfSettlement.setOpaque(true);
			dtOfSettlement.setBackground(headingBg);
			addComponent(6, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, dtOfSettlement, gridBag, constraints, claimSummaryPanel);

			amtClaimedSI=new JLabel("Amount Claimed", SwingConstants.CENTER);
			amtClaimedSI.setFont(headingFont);
			amtClaimedSI.setOpaque(true);
			amtClaimedSI.setBackground(headingBg);
			addComponent(7, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, amtClaimedSI, gridBag, constraints, claimSummaryPanel);

			facilityType1=new JLabel("Facility", SwingConstants.CENTER);
			facilityType1.setFont(headingFont);
			facilityType1.setOpaque(true);
			facilityType1.setBackground(headingBg);
			addComponent(2, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, facilityType1, gridBag, constraints, claimSummaryPanel);

			limitCovered1=new JLabel("Under CGFSI", SwingConstants.CENTER);
			limitCovered1.setFont(headingFont);
			limitCovered1.setOpaque(true);
			limitCovered1.setBackground(headingBg);
			constraints.gridheight=3;
			limitCovered1.setVerticalAlignment(JLabel.TOP);
			addComponent(3, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, limitCovered1, gridBag, constraints, claimSummaryPanel);

			amountClaimed1=new JLabel("First Instalment", SwingConstants.CENTER);
			amountClaimed1.setFont(headingFont);
			amountClaimed1.setOpaque(true);
			amountClaimed1.setBackground(headingBg);
			amountClaimed1.setVerticalAlignment(JLabel.TOP);
			addComponent(4, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, amountClaimed1, gridBag, constraints, claimSummaryPanel);

			amtSettled1=new JLabel("by CGTSI", SwingConstants.CENTER);
			amtSettled1.setFont(headingFont);
			amtSettled1.setOpaque(true);
			amtSettled1.setBackground(headingBg);
			constraints.gridheight=1;
			amtSettled1.setVerticalAlignment(JLabel.TOP);
			addComponent(5, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, amtSettled1, gridBag, constraints, claimSummaryPanel);

			dtOfSettlement1=new JLabel("of First Instalment", SwingConstants.CENTER);
			dtOfSettlement1.setFont(headingFont);
			dtOfSettlement1.setOpaque(true);
			dtOfSettlement1.setBackground(headingBg);
			dtOfSettlement1.setVerticalAlignment(JLabel.TOP);
			constraints.gridheight=1;
			addComponent(6, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, dtOfSettlement1, gridBag, constraints, claimSummaryPanel);

			amtClaimedSI1=new JLabel("in Second and", SwingConstants.CENTER);
			amtClaimedSI1.setFont(headingFont);
			amtClaimedSI1.setOpaque(true);
			amtClaimedSI1.setBackground(headingBg);
			constraints.gridheight=1;
			amtClaimedSI1.setVerticalAlignment(JLabel.TOP);
			addComponent(7, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, amtClaimedSI1, gridBag, constraints, claimSummaryPanel);

			facilityType2=new JLabel("(TL / WC)", SwingConstants.CENTER);
			facilityType2.setFont(headingFont);
			facilityType2.setOpaque(true);
			facilityType2.setBackground(headingBg);
			constraints.gridheight=2;
			facilityType2.setVerticalAlignment(JLabel.TOP);
			addComponent(2, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, facilityType2, gridBag, constraints, claimSummaryPanel);

			amountClaimed2=new JLabel("of Claim", SwingConstants.CENTER);
			amountClaimed2.setFont(headingFont);
			amountClaimed2.setOpaque(true);
			amountClaimed2.setBackground(headingBg);
			constraints.gridheight=2;
			amountClaimed2.setVerticalAlignment(JLabel.TOP);
			addComponent(4, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, amountClaimed2, gridBag, constraints, claimSummaryPanel);

			amtSettled2=new JLabel("if Any", SwingConstants.CENTER);
			amtSettled2.setFont(headingFont);
			amtSettled2.setOpaque(true);
			amtSettled2.setBackground(headingBg);
			constraints.gridheight=2;
			amtSettled2.setVerticalAlignment(JLabel.TOP);
			addComponent(5, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, amtSettled2, gridBag, constraints, claimSummaryPanel);

			dtOfSettlement2=new JLabel("of claim by CGTSI", SwingConstants.CENTER);
			dtOfSettlement2.setFont(headingFont);
			dtOfSettlement2.setOpaque(true);
			dtOfSettlement2.setBackground(headingBg);
			constraints.gridheight=1;
			addComponent(6, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, dtOfSettlement2, gridBag, constraints, claimSummaryPanel);

			amtClaimedSI2=new JLabel("final Intallment", SwingConstants.CENTER);
			amtClaimedSI2.setFont(headingFont);
			amtClaimedSI2.setOpaque(true);
			amtClaimedSI2.setBackground(headingBg);
			constraints.gridheight=2;
			amtClaimedSI2.setVerticalAlignment(JLabel.TOP);
			addComponent(7, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, amtClaimedSI2, gridBag, constraints, claimSummaryPanel);

			dtOfSettlement3=new JLabel("if Any", SwingConstants.CENTER);
			dtOfSettlement3.setFont(headingFont);
			dtOfSettlement3.setOpaque(true);
			dtOfSettlement3.setBackground(headingBg);
			constraints.gridheight=1;
			addComponent(6, 3, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, dtOfSettlement3, gridBag, constraints, claimSummaryPanel);
		}

		int summarySize = summary.size();
		noOfClaimSummary = summarySize;
		int i=0;
		com.cgtsi.claim.ClaimSummaryDtls claimSummaryDtls;

		slNoValue=new JLabel[summarySize];
		claimSummaryCgpan=new JLabel[summarySize];
		limitCoveredValue=new JLabel[summarySize];
		txtAmountClaimed=new JTextField[summarySize];
		facilityTypeValue=new JLabel[summarySize];
		txtAmountSettled=new JTextField[summarySize];
		txtAmountClaimedSI=new JTextField[summarySize];
		txtDtOfSettlement=new JTextField[summarySize];

		int rowNo=4;

		for (i=0;i<summarySize;i++)
		{
			claimSummaryDtls = (com.cgtsi.claim.ClaimSummaryDtls) summary.get(i);

			slNoValue[i] = new JLabel(""+(i+1), SwingConstants.CENTER);
			slNoValue[i].setFont(dataFont);
			slNoValue[i].setOpaque(true);
			slNoValue[i].setBackground(dataBg);
			addComponent(0, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, slNoValue[i], gridBag, constraints, claimSummaryPanel);

			claimSummaryCgpan[i] = new JLabel(claimSummaryDtls.getCgpan(), SwingConstants.CENTER);
			claimSummaryCgpan[i].setFont(dataFont);
			claimSummaryCgpan[i].setOpaque(true);
			claimSummaryCgpan[i].setBackground(dataBg);
			addComponent(1, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, claimSummaryCgpan[i], gridBag, constraints, claimSummaryPanel);

			String amt = "";
			if (claimSummaryDtls.getLimitCoveredUnderCGFSI() != null)
			{
				amt = claimSummaryDtls.getLimitCoveredUnderCGFSI();
			}
			limitCoveredValue[i] = new JLabel(amt, SwingConstants.CENTER);
			limitCoveredValue[i].setFont(dataFont);
			limitCoveredValue[i].setOpaque(true);
			limitCoveredValue[i].setBackground(dataBg);
			addComponent(3, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, limitCoveredValue[i], gridBag, constraints, claimSummaryPanel);

			txtAmountClaimed[i] = new JTextField(10);
			txtAmountClaimed[i].setDocument(new DecimalFormatField(13,2));
//			if ((flag.equals("MOD")) || (flag.equals("VER")))
//			{
				text = df.format(claimSummaryDtls.getAmount());
				txtAmountClaimed[i].setText(text);
				if (!first)
				{
					txtAmountClaimed[i].setEnabled(false);
				}
//			}
			addComponent(4, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAmountClaimed[i], gridBag, constraints, claimSummaryPanel);

			if (! first)
			{
				String facilityVal = claimSummaryDtls.getTypeOfFacility();

				facilityTypeValue[i]=new JLabel(facilityVal, SwingConstants.CENTER);
				facilityTypeValue[i].setFont(headingFont);
				facilityTypeValue[i].setOpaque(true);
				facilityTypeValue[i].setBackground(dataBg);
				addComponent(2, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, facilityTypeValue[i], gridBag, constraints, claimSummaryPanel);

				txtAmountSettled[i] = new JTextField(10);
				txtAmountSettled[i].setDocument(new DecimalFormatField(13,2));
//				if ((flag.equals("MOD")) || (flag.equals("VER")))
//				{
					text = df.format(claimSummaryDtls.getAmntSettledByCGTSI());
					txtAmountSettled[i].setText(text);
					txtAmountSettled[i].setEnabled(false);
//				}
				addComponent(5, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAmountSettled[i], gridBag, constraints, claimSummaryPanel);

				txtDtOfSettlement[i] = new JTextField(10);
				setDateFieldProperties(txtDtOfSettlement[i], "dd/MM/yyyy", false);
//				if ((flag.equals("MOD")) || (flag.equals("VER")))
//				{
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
					if (claimSummaryDtls.getDtOfSettlemntOfFirstInstallmentOfClm() != null)
					{
						txtDtOfSettlement[i].setText( dateFormat.format(claimSummaryDtls.getDtOfSettlemntOfFirstInstallmentOfClm()));
					}
				txtDtOfSettlement[i].setEnabled(false);
//				}
				addComponent(6, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtDtOfSettlement[i], gridBag, constraints, claimSummaryPanel);

				txtAmountClaimedSI[i] = new JTextField(10);
				txtAmountClaimedSI[i].setDocument(new DecimalFormatField(13,2));
//				if ((flag.equals("MOD")) || (flag.equals("VER")))
//				{
					text = df.format(claimSummaryDtls.getAmntClaimedInFinalInstllmnt());
					txtAmountClaimedSI[i].setText(text);
//				}
				addComponent(7, rowNo, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, txtAmountClaimedSI[i], gridBag, constraints, claimSummaryPanel);
			}

			rowNo++;
		}

/*		claimSummaryHint = new JLabel("* - Amount Eligible for Claim is 75%");
		claimSummaryHint.setFont(dataFont);
		claimSummaryHint.setOpaque(true);
		claimSummaryHint.setBackground(dataBg);
		addComponent(1, rowNo, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, claimSummaryHint, gridBag, constraints, claimSummaryPanel);*/
	}

	/**
	* This method displays the application selected from the list of files displayed for verification.
	*/
	private void displayAppFile(String key, String fileName) throws ThinClientException
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//		System.out.println("disp filename " + fileName);

		int index=fileName.lastIndexOf("&");
		String brnName="";
		if (index>0)
		{
			brnName=fileName.substring(index+1, fileName.length());
			fileName=fileName.substring(0, index);
		}

		Hashtable files=readFromFile(fileName);
		com.cgtsi.application.Application application=(com.cgtsi.application.Application) files.get(key);

		String appType=application.getLoanType();
//		System.out.println(appType);
		if (appType.equals("TC") && !application.getAdditionalTC())
		{
			displayTCApplication(false);
		}
		else if (appType.equals("WC") && !application.getWcEnhancement() && !application.getWcRenewal())
		{
			displayWCApplication();
		}
		else if (appType.equals("CC"))
		{
			displayCompositeApplication();
		}
		else if (appType.equals("BO"))
		{
			displayBothApplication();
		}
		else if (application.getAdditionalTC())
		{
			displayAddTCApplication();
		}
		else if (application.getWcEnhancement())
		{
			displayWCEApplication();
		}
		else if (application.getWcRenewal())
		{
			displayWCRApplication();
		}

		String text = "";
		if (!application.getWcEnhancement())
		{
			txtBankRefNo.setText(application.getMliRefNo());
			txtBankBranchName.setText(application.getMliBranchName());
			text = application.getMliBranchCode();
			if ((text != null) && (!(text.equals(""))))
			{
				txtBranchCode.setText(application.getMliBranchCode());
			}
		}

		if (! application.getAdditionalTC() && ! application.getWcEnhancement() && ! application.getWcRenewal())
		{
			com.cgtsi.application.BorrowerDetails borrowerDetails=application.getBorrowerDetails();
			com.cgtsi.application.SSIDetails ssiDetails=borrowerDetails.getSsiDetails();

			if (borrowerDetails.getAssistedByBank().equals("Y"))
			{
				borrowerAssistedYes.setSelected(true);
				activateBorrowerAssisted(true);
			}
			else if (borrowerDetails.getAssistedByBank().equals("N"))
			{
				borrowerAssistedNo.setSelected(true);
				activateBorrowerAssisted(false);
			}
			text = df.format(borrowerDetails.getOsAmt());
			txtOSAmt.setText(text);

			if (borrowerDetails.getNpa().equals("Y"))
			{
				npaYes.setSelected(true);
			}
			else if (borrowerDetails.getNpa().equals("N"))
			{
				npaNo.setSelected(true);
			}

			if (borrowerDetails.getPreviouslyCovered().equals("Y"))
			{
				borrowerCoveredYes.setSelected(true);
				enableBorrowerDetails(false);
				if (application.getCgpanReference() != null && ! application.getCgpanReference().equals(""))
				{
					cgpan.setSelected(true);
					cgbid.setSelected(false);
					none.setSelected(false);
					none.setEnabled(false);
					cgbid.setEnabled(false);
					txtSsiValue.setText(application.getCgpanReference());
				}
				else if (ssiDetails.getCgbid() != null && ! ssiDetails.getCgbid().equals(""))
				{
					cgpan.setSelected(false);
					cgbid.setSelected(true);
					none.setSelected(false);
					none.setEnabled(false);
					cgpan.setEnabled(false);
					txtSsiValue.setText(ssiDetails.getCgbid());
				}
			}
			else if (borrowerDetails.getPreviouslyCovered().equals("N"))
			{
				borrowerCoveredNo.setSelected(true);
				enableBorrowerDetails(true);

				String constitution=ssiDetails.getConstitution();
				if (constitution != null)
				{
					if (constitution.equals("proprietary"))
					{
						cmbConstitution.setSelectedItem("Proprietary");
					}
					else if (constitution.equals("partnership"))
					{
						cmbConstitution.setSelectedItem("Partnership");
					}
					else if (constitution.equals("public"))
					{
						cmbConstitution.setSelectedItem("Public Ltd.");
					}
					else if (constitution.equals("private"))
					{
						cmbConstitution.setSelectedItem("Private Ltd.");
					}					
					else
					{
						cmbConstitution.setSelectedItem("Others");
						txtConstitution.setText(constitution);
					}
				}
				String ssiType=ssiDetails.getSsiType();
				cmbUnitNames.setSelectedItem(ssiType);
				txtUnitName.setText(ssiDetails.getSsiName());
				txtSsiRegNo.setText(ssiDetails.getRegNo());
				txtFirmItpan.setText(ssiDetails.getSsiITPan());
				text = ssiDetails.getIndustryNature();
				if ((text != null)
					&& (!(text.equals(""))))
				{
					cmbIndNature.setSelectedItem(text);
				}
				text = ssiDetails.getIndustrySector();
				if ((text != null)
					&& (!(text.equals(""))))
				{
					cmbIndSector.setSelectedItem(text);
				}

				txtActivity.setText(ssiDetails.getActivityType());
				text = df.format(ssiDetails.getEmployeeNos());
				txtNoOfEmp.setText(text);
				text = df.format(ssiDetails.getProjectedSalesTurnover());
				txtOptSalesTurnover.setText(text);
				text = df.format(ssiDetails.getProjectedExports());
				txtOptExports.setText(text);
				txtAddress.setText(ssiDetails.getAddress());
				cmbState.setSelectedItem(ssiDetails.getState());
				String dist=ssiDetails.getDistrict();

				if (getDistricts(ssiDetails.getState()).contains(dist))
				{
					cmbDistrict.setSelectedItem(ssiDetails.getDistrict());
				}
				else
				{
					cmbDistrict.setSelectedItem("Others");
					txtDistrict.setText(ssiDetails.getDistrict());
				}
				txtCity.setText(ssiDetails.getCity());
				txtPincode.setText(ssiDetails.getPincode());
				cmbTitle.setSelectedItem(ssiDetails.getCpTitle());
				if (ssiDetails.getCpFirstName() != null && ! ssiDetails.getCpFirstName().trim().equals(""))
				{
					txtFirstName.setText(ssiDetails.getCpFirstName());
				}
				if (ssiDetails.getCpMiddleName() != null && ! ssiDetails.getCpMiddleName().trim().equals(""))
				{
					txtMiddleName.setText(ssiDetails.getCpMiddleName());
				}
				if (ssiDetails.getCpLastName() != null && ! ssiDetails.getCpLastName().trim().equals(""))
				{				
				txtLastName.setText(ssiDetails.getCpLastName());
				}
				if (ssiDetails.getCpGender().equals("M"))
				{
					male.setSelected(true);
				}
				else if (ssiDetails.getCpGender().equals("F"))
				{
					female.setSelected(true);
				}
				if (ssiDetails.getCpITPAN() != null && ! ssiDetails.getCpITPAN().trim().equals(""))
				{
				txtChiefPromoterItpan.setText(ssiDetails.getCpITPAN());
				}
				if (ssiDetails.getCpDOB() != null)
				{
					txtDOB.setText(dateFormat.format(ssiDetails.getCpDOB()));
				}
				if (ssiDetails.getSocialCategory()!=null)
				{
					cmbSocialCategory.setSelectedItem(ssiDetails.getSocialCategory());
				}

				text = ssiDetails.getCpLegalID();
				if ((text.equals("Passport Number"))
					|| (text.equals("Driving License"))
					|| (text.equals("Voter Identity Card"))
					|| (text.equals("Ration Card Number"))
					|| (text.equals("Select")))
				{
					cmbLegalId.setSelectedItem(text);
				}
				else
				{
					cmbLegalId.setSelectedItem("Others");
					txtOtherId.setText(text);
				}
				if (ssiDetails.getCpLegalIdValue() != null && ! ssiDetails.getCpLegalIdValue().trim().equals(""))
				{				
				txtLegalValue.setText(ssiDetails.getCpLegalIdValue());
				}
				if (ssiDetails.getFirstName() != null && ! ssiDetails.getFirstName().trim().equals(""))
				{					
				txtOtherPromotersName1.setText(ssiDetails.getFirstName());
				}
				if (ssiDetails.getFirstItpan() != null && ! ssiDetails.getFirstItpan().trim().equals(""))
				{					
				txtOtherPromotersItpan1.setText(ssiDetails.getFirstItpan());
				}
				if (ssiDetails.getFirstDOB() != null)
				{
					txtOtherPromotersDob1.setText(dateFormat.format(ssiDetails.getFirstDOB()));
				}
				if (ssiDetails.getSecondName() != null && ! ssiDetails.getSecondName().trim().equals(""))
				{
				txtOtherPromotersName2.setText(ssiDetails.getSecondName());
				}
				if (ssiDetails.getSecondItpan() != null && ! ssiDetails.getSecondItpan().trim().equals(""))
				{				
				txtOtherPromotersItpan2.setText(ssiDetails.getSecondItpan());
				}
				if (ssiDetails.getSecondDOB() != null)
				{
					txtOtherPromotersDob2.setText(dateFormat.format(ssiDetails.getSecondDOB()));
				}
				if (ssiDetails.getThirdName() != null && ! ssiDetails.getThirdName().trim().equals(""))
				{
				txtOtherPromotersName3.setText(ssiDetails.getThirdName());
				}
				if (ssiDetails.getThirdItpan() != null && ! ssiDetails.getThirdItpan().trim().equals(""))
				{				
				txtOtherPromotersItpan3.setText(ssiDetails.getThirdItpan());
				}
				if (ssiDetails.getThirdDOB() != null)
				{
					txtOtherPromotersDob3.setText(dateFormat.format(ssiDetails.getThirdDOB()));
				}
			}
		}

		com.cgtsi.application.ProjectOutlayDetails projectOutlayDetails=application.getProjectOutlayDetails();
		com.cgtsi.application.PrimarySecurityDetails primarySecurityDetails=projectOutlayDetails.getPrimarySecurityDetails();
		String guarantorName="";
//		String text = "";
		double guarantorNetWorth=0;
		String support="";
		String particulars="";
		double value=0;

		if ((projectOutlayDetails.getCollateralSecurityTaken()).equals("Y"))
		{
			collateralYes.setSelected(true);
		}
		else
		{
			collateralNo.setSelected(true);
		}
		if ((projectOutlayDetails.getThirdPartyGuaranteeTaken()).equals("Y"))
		{
			thirdPartyYes.setSelected(true);
		}
		else
		{
			thirdPartyNo.setSelected(true);
		}

		guarantorName=projectOutlayDetails.getGuarantorsName1();
		if ((guarantorName!=null)
			&& (!(guarantorName.equals(""))))
		{
			guarantorNetWorth=projectOutlayDetails.getGuarantorsNetWorth1();
			txtGuarantorName1.setText(guarantorName);
			text = df.format(guarantorNetWorth);
			txtGuarantorNetworth1.setText(text);
		}

		guarantorName=projectOutlayDetails.getGuarantorsName2();
		if ((guarantorName!=null)
			&& (!(guarantorName.equals(""))))
		{
			guarantorNetWorth=projectOutlayDetails.getGuarantorsNetWorth2();
			txtGuarantorName2.setText(guarantorName);
			text = df.format(guarantorNetWorth);
			txtGuarantorNetworth2.setText(text);
		}

		guarantorName=projectOutlayDetails.getGuarantorsName3();
		if ((guarantorName!=null)
			&& (!(guarantorName.equals(""))))
		{
			guarantorNetWorth=projectOutlayDetails.getGuarantorsNetWorth3();
			txtGuarantorName3.setText(guarantorName);
			text = df.format(guarantorNetWorth);
			txtGuarantorNetworth3.setText(text);
		}

		guarantorName=projectOutlayDetails.getGuarantorsName4();
		if ((guarantorName!=null)
			&& (!(guarantorName.equals(""))))
		{
			guarantorNetWorth=projectOutlayDetails.getGuarantorsNetWorth4();
			txtGuarantorName4.setText(guarantorName);
			text = df.format(guarantorNetWorth);
			txtGuarantorNetworth4.setText(text);
		}

		support=projectOutlayDetails.getSubsidyName();
		if ((support!=null) && (!(support.equals(""))))
		{
			if (support.equals("PMRY"))
			{
				cmbSupportNames.setSelectedIndex(1);
			}
			else if (support.equals("SJRY"))
			{
				cmbSupportNames.setSelectedIndex(2);
			}
			else
			{
				cmbSupportNames.setSelectedIndex(3);
				txtSupportName.setText(support);
			}
		}

		particulars=primarySecurityDetails.getLandParticulars();
		if ((particulars!=null)
			&& (!(particulars.equals(""))))
		{
			value=primarySecurityDetails.getLandValue();
			txtLandParticulars.setText(particulars);
			text = df.format(value);
			txtLandValue.setText(text);
		}

		particulars=primarySecurityDetails.getBldgParticulars();
		if ((particulars!=null)
			&& (!(particulars.equals(""))))
		{
			value=primarySecurityDetails.getBldgValue();
			txtBuildingParticulars.setText(particulars);
			text = df.format(value);
			txtBuildingValue.setText(text);
		}

		particulars=primarySecurityDetails.getMachineParticulars();
		if ((particulars!=null)
			&& (!(particulars.equals(""))))
		{
			value=primarySecurityDetails.getMachineValue();
			txtMachineParticulars.setText(particulars);
			text = df.format(value);
			txtMachineValue.setText(text);
		}

		particulars=primarySecurityDetails.getAssetsParticulars();
		if ((particulars!=null)
			&& (!(particulars.equals(""))))
		{
			value=primarySecurityDetails.getAssetsValue();
			txtOtherAssetsParticulars.setText(particulars);
			text = df.format(value);
			txtOtherAssetsValue.setText(text);
		}

		particulars=primarySecurityDetails.getCurrentAssetsParticulars();
		if ((particulars!=null)
			&& (!(particulars.equals(""))))
		{
			value=primarySecurityDetails.getCurrentAssetsValue();
			txtCurrentAssetsParticulars.setText(particulars);
			text = df.format(value);
			txtCurrentAssetsValue.setText(text);
		}

		particulars=primarySecurityDetails.getOthersParticulars();
		if ((particulars!=null)
			&& (!(particulars.equals(""))))
		{
			value=primarySecurityDetails.getOthersValue();
			txtOthersParticulars.setText(particulars);
			text = df.format(value);
			txtOthersValue.setText(text);
		}

		text = df.format(projectOutlayDetails.getTermCreditSanctioned());
		txtTermCredit.setText(text);
		text = df.format(projectOutlayDetails.getTcPromoterContribution());
		txtTCPromotersContribution.setText(text);
		text = df.format(projectOutlayDetails.getTcSubsidyOrEquity());
		txtTCSupport.setText(text);
		text = df.format(projectOutlayDetails.getTcOthers());
		txtTCOthers.setText(text);
		
		text = df.format(projectOutlayDetails.getWcFundBasedSanctioned());
		txtWCFundBased.setText(text);
		text = df.format(projectOutlayDetails.getWcNonFundBasedSanctioned());
		txtWCNonFundBased.setText(text);
		text = df.format(projectOutlayDetails.getWcPromoterContribution());
		txtWCPromotersContribution.setText(text);
		text = df.format(projectOutlayDetails.getWcSubsidyOrEquity());
		txtWCSupport.setText(text);
		text = df.format(projectOutlayDetails.getWcOthers());
		txtWCOthers.setText(text);		
		
		text = df.format(projectOutlayDetails.getProjectCost());
		projectCostValue.setText(text);
		text = df.format(projectOutlayDetails.getWcAssessed());
		wcAssessedValue.setText(text);
		text = df.format(projectOutlayDetails.getProjectOutlay());
		projectOutlayValue.setText(text);
		
		if (application.getAdditionalTC() || application.getWcRenewal())
		{
			String rehabilitation = application.getRehabilitation();
			if (rehabilitation != null)
			{
				if (rehabilitation.equals("Y"))
				{
					rehabilitationYes.setSelected(true);
				}
				else if (rehabilitation.equals("N"))
				{
					rehabilitationNo.setSelected(false);
				}
			}
		}

		if (application.getTermLoan()!=null)
		{
			com.cgtsi.application.TermLoan termLoan=application.getTermLoan();

			text = df.format(termLoan.getAmountSanctioned());
			amtSanctionedValue.setText(text);
			txtDateOfSanction.setText(dateFormat.format(termLoan.getAmountSanctionedDate()));
			text = df.format(termLoan.getCreditGuaranteed());
			txtCreditToBeGuaranteed.setText(text);
			text = df.format(termLoan.getAmtDisbursed());
			txtAmtDisbursed.setText(text);
			if (termLoan.getFirstDisbursementDate() != null)
			{
				txtDateOfFirstDisbursement.setText( dateFormat.format(termLoan.getFirstDisbursementDate()));
			}
			if (termLoan.getFinalDisbursementDate() != null)
			{
				txtDateOfLastDisbursement.setText(dateFormat.format(termLoan.getFinalDisbursementDate()));
			}
			text = df.format(termLoan.getTenure());
			txtTenure.setText(text);
			if (termLoan.getInterestType().equals("F"))
			{
				interestFixed.setSelected(true);
			}
			else if (termLoan.getInterestType().equals("F"))
			{
				interestFloating.setSelected(true);
			}
			text = df.format(termLoan.getInterestRate());
			txtTcInterestRate.setText(text);
			text = df.format(termLoan.getPlr());
			txtPlr.setText(text);

			txtPlrType.setText(""+termLoan.getTypeOfPLR());
			text = df.format(termLoan.getRepaymentMoratorium());
			txtMoratorium.setText(text);
			if (termLoan.getFirstInstallmentDueDate() != null)
			{
				txtFirstInstDueDate.setText( dateFormat.format(termLoan.getFirstInstallmentDueDate()));
			}

//				if (termLoan.getPeriodicity() != null)
//				{
				if (termLoan.getPeriodicity()==1)
				{
					cmbPeriodicity.setSelectedItem("Monthly");
				}
				else if (termLoan.getPeriodicity()==2)
				{
					cmbPeriodicity.setSelectedItem("Quarterly");
				}
				else if (termLoan.getPeriodicity()==3)
				{
					cmbPeriodicity.setSelectedItem("Half-Yearly");
				}				
//				}
			text = df.format(termLoan.getNoOfInstallments());
			txtNoOfInstallments.setText(text);
			Double val = new Double(termLoan.getPplOS());
			if (val != null)
			{
				text = df.format(val);
				txtTcOsAmt.setText(text);
			}
			if (termLoan.getPplOsAsOnDate() != null)
			{
				txtOsAsOn.setText(dateFormat.format(termLoan.getPplOsAsOnDate()));
			}
		}
		if (application.getWc()!=null)
		{
			com.cgtsi.application.WorkingCapital workingCapital=application.getWc();

			if (workingCapital.getWcInterestType().equals("T"))
			{
				interestFixed.setSelected(true);
			}
			else if (workingCapital.getWcInterestType().equals("F"))
			{
				interestFloating.setSelected(true);
			}

			if (application.getWcEnhancement())
			{
				txtSsiValue.setText(application.getCgpanReference());
				text = df.format(workingCapital.getEnhancedFundBased());
				enhancedFundBasedValue.setText(text);
				text = df.format(workingCapital.getLimitFundBasedInterest());
				txtEnhancedInterest.setText(text);
				text = df.format(workingCapital.getEnhancedFBInterest());
				txtEnhancedInterest.setText(text);
				text = df.format(workingCapital.getEnhancedTotal());
//				enhancedTotalValue.setText(text);
				text = df.format(workingCapital.getEnhancedNonFundBased());
				enhancedNonFundBasedValue.setText(text);
				text = df.format(workingCapital.getLimitNonFundBasedCommission());
				txtEnhancedCommission.setText(text);
				text = df.format(workingCapital.getEnhancedNFBComission());
				txtEnhancedCommission.setText(text);
				txtDtOfEnhancement.setText(dateFormat.format( workingCapital.getEnhancementDate()));
			}
			else if (application.getWcRenewal())
			{
				txtSsiValue.setText(application.getCgpanReference());
				text = df.format(workingCapital.getRenewalFundBased());
				renewedFundBasedValue.setText(text);
				text = df.format(workingCapital.getRenewalFBInterest());
				txtRenewedInterest.setText(text);
				text = df.format(workingCapital.getRenewalTotal());
//				renewedTotalValue.setText(text);
				text = df.format(workingCapital.getRenewalNonFundBased());
				renewedNonFundBasedValue.setText(text);
				text = df.format(workingCapital.getRenewalNFBComission());
				txtRenewedCommission.setText(text);
				txtDtOfRenewal.setText(dateFormat.format(workingCapital.getRenewalDate()));
				text = df.format(workingCapital.getWcPlr());
				txtWcPlr.setText(text);
				txtWcPlrType.setText(""+workingCapital.getWcTypeOfPLR());
			}
			else
			{
				text = df.format(workingCapital.getFundBasedLimitSanctioned());
				fbLimitSanctionedValue.setText(text);
				text = df.format(workingCapital.getNonFundBasedLimitSanctioned());
				nfbLimitSanctionedValue.setText(text);				
//				text = df.format(workingCapital.getLimitFundBasedInterest());
//				txtFBInterest.setText(text);
				if (workingCapital.getLimitFundBasedSanctionedDate() != null)
				{
					txtFBSanctionedDate.setText(dateFormat.format( workingCapital.getLimitFundBasedSanctionedDate()));
				}
				
				text = df.format(workingCapital.getLimitNonFundBasedCommission());
				txtNFBCommission.setText(text);
				if (workingCapital.getLimitNonFundBasedSanctionedDate() != null)
				{
					txtNFBSanctionedDate.setText( dateFormat.format(workingCapital.getLimitNonFundBasedSanctionedDate()));
				}				

				text = df.format(workingCapital.getCreditFundBased());
				txtFBCredit.setText(text);
				text = df.format(workingCapital.getOsFundBasedPpl());
				txtOsFBPrincipal.setText(text);
				if (workingCapital.getOsFundBasedAsOnDate() != null)
				{
					txtOsFBAsOn.setText(dateFormat.format( workingCapital.getOsFundBasedAsOnDate()));
				}
				
				text = df.format(workingCapital.getCreditNonFundBased());
				txtNFBCredit.setText(text);
				text = df.format(workingCapital.getOsNonFundBasedPpl());
				txtOsNFBPrincipal.setText(text);
				text = df.format(workingCapital.getOsNonFundBasedCommissionAmt());
				txtOsNFBCommission.setText(text);
				if (workingCapital.getOsNonFundBasedAsOnDate() != null)
				{
					txtOsNFBAsOn.setText( dateFormat.format(workingCapital.getOsNonFundBasedAsOnDate()));
				}
				
				text = df.format(workingCapital.getLimitFundBasedInterest());
				txtWcInterestRate.setText(text);
				text = df.format(workingCapital.getWcPlr());
				txtWcPlr.setText(text);
				txtWcPlrType.setText(""+workingCapital.getWcTypeOfPLR());
			}


//				if (workingCapital.getFundBasedLimitSanctioned() != null)
//				{
/*					nfbLimitSanctionedValue.setText(""+ workingCapital.getNonFundBasedLimitSanctioned());
				txtNFBCommission.setText(""+workingCapital.getLimitNonFundBasedCommission());
				if (workingCapital.getLimitNonFundBasedSanctionedDate() != null)
				{
					txtNFBSanctionedDate.setText( dateFormat.format(workingCapital.getLimitNonFundBasedSanctionedDate()));
				}
				txtNFBCredit.setText(""+workingCapital.getCreditNonFundBased());*/
//					txtOsNFBPrincipal.setText(""+workingCapital.getOsNonFundBasedPpl());
//					txtOsNFBCommission.setText(""+workingCapital.getOsNonFundBasedCommissionAmt());
/*					if (workingCapital.getOsNonFundBasedAsOnDate() != null)
				{
					txtOsNFBAsOn.setText( dateFormat.format(workingCapital.getOsNonFundBasedAsOnDate()));
				}*/

//				}
		}
	
		if (application.getSecuritization() != null)
		{
			com.cgtsi.application.Securitization securitization = new com.cgtsi.application.Securitization();

			securitization = application.getSecuritization();

			text = df.format(securitization.getSpreadOverPLR());
			txtSpreadOverPLR.setText(text);
			
			if (securitization.getPplRepaymentInEqual() != null)
			{
				if (securitization.getPplRepaymentInEqual().equals("Y"))
				{
					repayInEqualInstYes.setSelected(true);
				}
				else if (securitization.getPplRepaymentInEqual().equals("N"))
				{
					repayInEqualInstNo.setSelected(true);
				}
			}

			text = df.format(securitization.getTangibleNetWorth());
			txtTangibleNetworth.setText(text);
			text = df.format(securitization.getFixedACR());
			txtFixedAssetCoverageRatio.setText(text);
			text = df.format(securitization.getCurrentRatio());
			txtCurrentRatio.setText(text);
			text = df.format(securitization.getMinimumDSCR());
			txtMinDSCR.setText(text);
			text = df.format(securitization.getAvgDSCR());
			txtAvgDSCR.setText(text);
//			text = df.format(securitization.getFinancialPartUnit());
//			txtFinPartOfUnit.setText(text);
		}

		if (mcgfFlag)
		{
			com.cgtsi.mcgs.MCGFDetails mcgfDetails = new com.cgtsi.mcgs.MCGFDetails();
			mcgfDetails = application.getMCGFDetails();

			if (mcgfDetails!=null)
			{
				if (mcgfDetails.getMcgfId() != null)
				{
					cmbMcgfId.setSelectedItem(mcgfDetails.getMcgfId());
				}
				if (mcgfDetails.getParticipatingBank() != null)
				{
					if (mcgfDetails.getParticipatingBank().equals(""))
					{
						cmbParticipatingBanks.setSelectedItem("Select");
					}
					else
					{
						cmbParticipatingBanks.setSelectedItem(mcgfDetails.getParticipatingBank());
					}
				}
				if (mcgfDetails.getParticipatingBankBranch() != null)
				{
					txtPartBankBranch.setText(mcgfDetails.getParticipatingBankBranch());
				}
				if (mcgfDetails.getMcgfDistrict() != null)
				{
					txtMcgfDistrict.setText(mcgfDetails.getMcgfDistrict());
				}
				text = df.format(mcgfDetails.getMcgfApprovedAmt());
				txtAmtApprovedByMcgf.setText(text);
				if (mcgfDetails.getMcgfApprovedDate() != null)
				{
					txtApprovedDate.setText(dateFormat.format(mcgfDetails.getMcgfApprovedDate()));
				}
				if (mcgfDetails.getMcgfGuaranteeCoverStartDate() != null)
				{
					txtGCoverStartDate.setText(dateFormat.format( mcgfDetails.getMcgfGuaranteeCoverStartDate()));
				}
				if (mcgfDetails.getMcgfName() != null)
				{
					mcgfNameValue.setText(mcgfDetails.getMcgfName());
				}
			}
		}

		if (application.getAdditionalTC() || application.getWcEnhancement() || application.getWcRenewal())
		{
			txtSsiValue.setText(application.getCgpanReference());				
		}
		if (application.getRemarks() != null)
		{
			txtRemarks.setText(application.getRemarks());
		}

		lblKey=new JLabel(key);
		lblFileName=new JLabel(fileName);
		lblBrnName=new JLabel(brnName);
		lblKey.setVisible(false);
		lblFileName.setVisible(false);
		lblBrnName.setVisible(false);
		hiddenPanel=new JPanel();
		hiddenPanel.add(lblKey);
		hiddenPanel.add(lblFileName);
		hiddenPanel.add(lblBrnName);
		hiddenPanel.setVisible(false);
		addComponent(0, 6, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, hiddenPanel, gridBag, constraints, panel);
	}

	/**
	 * This method saves the borrower details, promoter details of the application
	 */
	 private com.cgtsi.application.Application saveCommonAppDetails(com.cgtsi.application.Application application)
	{
		 String text ="";
		 double value=0;
		 Date date;
		 SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");

		 com.cgtsi.application.BorrowerDetails borrowerDetails=new com.cgtsi.application.BorrowerDetails();
		 com.cgtsi.application.SSIDetails ssiDetails=new com.cgtsi.application.SSIDetails();

		 application.setMliRefNo(txtBankRefNo.getText().trim());
		 //System.out.println("bank reference number -- " + txtBankRefNo.getText().trim());
		 application.setMliBranchName(txtBankBranchName.getText().trim());
		 //System.out.println("bank branch name -- " + txtBankBranchName.getText().trim());
		 text = txtBranchCode.getText().trim();
		 //System.out.println("bank branch code -- " + text);
		 if (text.equals(""))
		 {
			 application.setMliBranchCode(null);
		 }
		 else
		 {
			 application.setMliBranchCode(text);
		 }

		 if (borrowerAssistedYes.isSelected())
		 {
			 borrowerDetails.setAssistedByBank("Y");
			 //System.out.println("borrower assisted -- Y");
		 }
		 else if (borrowerAssistedNo.isSelected())
		 {
			 borrowerDetails.setAssistedByBank("N");
			 //System.out.println("borrower assisted -- N");
		 }

		 text = txtOSAmt.getText().trim();
		 //System.out.println("borrower outstanding amt -- " + text);
		 if (!(text.equals("")))
		 {
			 value=(double) (Double.parseDouble(text));
			 borrowerDetails.setOsAmt(value);
		 }

		 if (npaYes.isSelected())
		 {
			 borrowerDetails.setNpa("Y");
		 }
		 else if (npaNo.isSelected())
		 {
			 borrowerDetails.setNpa("N");
		 }

		 if (borrowerCoveredYes.isSelected())
		 {
			 borrowerDetails.setPreviouslyCovered("Y");
			 if (cgpan.isSelected())
			 {
				 application.setCgpanReference(txtSsiValue.getText().trim());
			 }
			 else if (cgbid.isSelected())
			 {
				 ssiDetails.setCgbid(txtSsiValue.getText().trim());
			 }
			 //System.out.println("borrower covered -- Y");
		 }
		 else if (borrowerCoveredNo.isSelected())
		 {
			 borrowerDetails.setPreviouslyCovered("N");
			 //System.out.println("borrower covered -- N");

			 String constName = cmbConstitution.getSelectedItem().toString();
			 if (constName.equals("Proprietary"))
			 {
				 ssiDetails.setConstitution("proprietary");
			 }
			 else if (constName.equals("Partnership"))
			 {
				 ssiDetails.setConstitution("partnership");
			 }
			 else if (constName.equals("Public Ltd."))
			 {
				 ssiDetails.setConstitution("public");
			 }
			 else if (constName.equals("Private Ltd."))
			 {
				 ssiDetails.setConstitution("private");
			 }
			 else if (constName.equals("Others"))
			 {
				 ssiDetails.setConstitution(txtConstitution.getText().trim());
			 }

			 ssiDetails.setSsiType(cmbUnitNames.getSelectedItem().toString());
			 ssiDetails.setSsiName(txtUnitName.getText().trim());
			 ssiDetails.setRegNo(txtSsiRegNo.getText().trim());
			 ssiDetails.setSsiITPan(txtFirmItpan.getText().trim());
			 text = cmbIndNature.getSelectedItem().toString();
			 if (text.equals("Select"))
			 {
				 ssiDetails.setIndustryNature(null);
			 }
			 else
			 {
				 ssiDetails.setIndustryNature(text);
			 }

			 text = cmbIndSector.getSelectedItem().toString();
			 if (text.equals("Select"))
			 {
				 ssiDetails.setIndustrySector(null);
			 }
			 else
			 {
				 ssiDetails.setIndustrySector(text);
			 }

			 ssiDetails.setActivityType(txtActivity.getText().trim());
			 text = txtNoOfEmp.getText().trim();
			 if (!(text.equals("")))
			 {
				 ssiDetails.setEmployeeNos(Integer.parseInt(text));
			 }

			 text = txtOptSalesTurnover.getText().trim();
			 if (!(text.equals("")))
			 {
				 value=(double) Double.parseDouble(text);
				 ssiDetails.setProjectedSalesTurnover(value);
			 }

			 text = txtOptExports.getText().trim();
			 if (!(text.equals("")))
			 {
				 value=(double) Double.parseDouble(text);
				 ssiDetails.setProjectedExports(value);
			 }

			 ssiDetails.setAddress(txtAddress.getText().trim());
			 ssiDetails.setState(cmbState.getSelectedItem().toString());
			 if ((cmbDistrict.getSelectedItem().toString()).equals("Others"))
			 {
				 ssiDetails.setDistrict(txtDistrict.getText().trim());
			 }
			 else
			 {
				ssiDetails.setDistrict(cmbDistrict.getSelectedItem().toString());
			 }
			 ssiDetails.setCity(txtCity.getText().trim());
			 ssiDetails.setPincode(txtPincode.getText().trim());
			 ssiDetails.setCpTitle(cmbTitle.getSelectedItem().toString());
			 ssiDetails.setCpFirstName(txtFirstName.getText().trim());
			 text = txtMiddleName.getText().trim();
			 if (text.equals(""))
			 {
				 ssiDetails.setCpMiddleName(null);
			 }
			 else
			 {
				 ssiDetails.setCpMiddleName(text);
			 }

			 ssiDetails.setCpLastName(txtLastName.getText().trim());
			 if (male.isSelected())
			 {
				 ssiDetails.setCpGender("M");
			 }
			 else if (female.isSelected())
			 {
				 ssiDetails.setCpGender("F");
			 }

			 text = txtChiefPromoterItpan.getText().trim();
			 if (text.equals(""))
			 {
				 ssiDetails.setCpITPAN(null);
			 }
			 else
			 {
				 ssiDetails.setCpITPAN(text);
			 }

			 text = txtDOB.getText().trim();
			 if (text.equals(""))
			 {
				 ssiDetails.setCpDOB(null);
			 }
			 else
			 {
				date=dateFormat.parse(text, new ParsePosition(0));
				ssiDetails.setCpDOB(date);
			 }
			 if (((String)cmbSocialCategory.getSelectedItem()).equals(""))
			 {
				ssiDetails.setSocialCategory(null);
			 }
			 else
			 {
				ssiDetails.setSocialCategory((String)cmbSocialCategory.getSelectedItem());
			 }

			 if ((cmbLegalId.getSelectedItem().toString()).equals("Others"))
			 {
				ssiDetails.setCpLegalID(txtOtherId.getText().trim());
			 }
			 else
			 {
				ssiDetails.setCpLegalID(cmbLegalId.getSelectedItem().toString());
			 }
			 ssiDetails.setCpLegalIdValue(txtLegalValue.getText().trim());

			 text = txtOtherPromotersName1.getText().trim();
			 if (text.equals(""))
			 {
				 ssiDetails.setFirstName(null);
			 }
			 else
			 {
				 ssiDetails.setFirstName(text);
			 }

			 text = txtOtherPromotersItpan1.getText().trim();
			 if (text.equals(""))
			 {
				 ssiDetails.setFirstItpan(null);
			 }
			 else
			 {
				 ssiDetails.setFirstItpan(text);
			 }

			 text = txtOtherPromotersDob1.getText().trim();
			 if (text.equals(""))
			 {
				 ssiDetails.setFirstDOB(null);
			 }
			 else
			 {
				date=dateFormat.parse(text, new ParsePosition(0));
				ssiDetails.setFirstDOB(date);
			 }

			 text = txtOtherPromotersName2.getText().trim();
			 if (text.equals(""))
			 {
				 ssiDetails.setSecondName(null);
			 }
			 else
			 {
				 ssiDetails.setSecondName(text);
			 }

			 text = txtOtherPromotersItpan2.getText().trim();
			 if (text.equals(""))
			 {
				 ssiDetails.setSecondItpan(null);
			 }
			 else
			 {
				 ssiDetails.setSecondItpan(text);
			 }

			 text = txtOtherPromotersDob2.getText().trim();
			 if (text.equals(""))
			 {
				 ssiDetails.setSecondDOB(null);
			 }
			 else
			 {
				 date=dateFormat.parse(text, new ParsePosition(0));
				 ssiDetails.setSecondDOB(date);
			 }

			 text = txtOtherPromotersName3.getText().trim();
			 if (text.equals(""))
			 {
				 ssiDetails.setThirdName(null);
			 }
			 else
			 {
				 ssiDetails.setThirdName(text);
			 }

			 text = txtOtherPromotersItpan3.getText().trim();
			 if (text.equals(""))
			 {
				 ssiDetails.setThirdItpan(null);
			 }
			 else
			 {
				 ssiDetails.setThirdItpan(text);
			 }

			 text = txtOtherPromotersDob3.getText().trim();
			 if (text.equals(""))
			 {
				 ssiDetails.setThirdDOB(null);
			 }
			 else
			 {
				 date=dateFormat.parse(text, new ParsePosition(0));
				 ssiDetails.setThirdDOB(date);
			 }
		 }

		 borrowerDetails.setSsiDetails(ssiDetails);
		 application.setBorrowerDetails(borrowerDetails);

		 application=saveProjectOutlayDetails(application);

		 if (application.getAdditionalTC() || application.getWcRenewal())
		 {
			 if (rehabilitationYes.isSelected())
			 {
				 application.setRehabilitation("Y");
			 }
			 else if (rehabilitationNo.isSelected())
			 {
				 application.setRehabilitation("N");
			 }
		 }
		 else
		 {
			application.setRehabilitation("N");
		 }

		 application.setCompositeLoan(compositeValue.getText().trim());
		 application.setLoanType(loanFacilityValue.getText().trim());
		 application.setScheme(schemeName.getText().trim());

		 if (! txtRemarks.getText().trim().equals(""))
		 {
			 application.setRemarks(txtRemarks.getText().trim());
		 }

		 return application;
	}


	/**
	 * This method save the project outlay details of the application
	 */
	private com.cgtsi.application.Application saveProjectOutlayDetails(com.cgtsi.application.Application application)
	{
		com.cgtsi.application.ProjectOutlayDetails projectOutlayDetails=new com.cgtsi.application.ProjectOutlayDetails();
		com.cgtsi.application.PrimarySecurityDetails primarySecurityDetails=new com.cgtsi.application.PrimarySecurityDetails();

		 SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
		int index=0;											//array index used for all the arrays
		String guarantorName;
		double netWorth;
		String securityParticular;
		double securityValue;
		double termCreditSanctioned;
		double wcFundBasedSanctioned;
		double wcNonFundBasedSanctioned;
		double promotersContribution;
		double subsidyEquity;
		double others;
		double projectCost;
		double wcAssessed;
		double projectOutlay;
		String text;

		if (collateralYes.isSelected())
		{
			projectOutlayDetails.setCollateralSecurityTaken("Y");
		}
		else if (collateralNo.isSelected())
		{
			projectOutlayDetails.setCollateralSecurityTaken("N");
		}
		if (thirdPartyYes.isSelected())
		{
			projectOutlayDetails.setThirdPartyGuaranteeTaken("Y");
		}
		else if (thirdPartyNo.isSelected())
		{
			projectOutlayDetails.setThirdPartyGuaranteeTaken("N");
		}

		guarantorName=txtGuarantorName1.getText().trim();
		if (!(guarantorName.equals("")))
		{
			netWorth=(double) (Double.parseDouble(txtGuarantorNetworth1.getText().trim()));
			projectOutlayDetails.setGuarantorsName1(guarantorName);
			projectOutlayDetails.setGuarantorsNetWorth1(netWorth);
		}

		guarantorName=txtGuarantorName2.getText().trim();
		if (!(guarantorName.equals("")))
		{
			netWorth=(double) (Double.parseDouble(txtGuarantorNetworth2.getText().trim()));
			projectOutlayDetails.setGuarantorsName2(guarantorName);
			projectOutlayDetails.setGuarantorsNetWorth2(netWorth);
		}

		guarantorName=txtGuarantorName3.getText().trim();
		if (!(guarantorName.equals("")))
		{
			netWorth=(double) (Double.parseDouble(txtGuarantorNetworth3.getText().trim()));
			projectOutlayDetails.setGuarantorsName3(guarantorName);
			projectOutlayDetails.setGuarantorsNetWorth3(netWorth);
		}

		guarantorName=txtGuarantorName4.getText().trim();
		if (!(guarantorName.equals("")))
		{
			netWorth=(double) (Double.parseDouble(txtGuarantorNetworth4.getText().trim()));
			projectOutlayDetails.setGuarantorsName4(guarantorName);
			projectOutlayDetails.setGuarantorsNetWorth4(netWorth);
		}

		if (cmbSupportNames.getSelectedIndex()==1)
		{
			projectOutlayDetails.setSubsidyName("PMRY");
		}
		else if (cmbSupportNames.getSelectedIndex()==2)
		{
			projectOutlayDetails.setSubsidyName("SJRY");
		}
		else if (cmbSupportNames.getSelectedIndex()==3)
		{
			projectOutlayDetails.setSubsidyName(txtSupportName.getText().trim());
		}

		securityParticular=txtLandParticulars.getText().trim();
		if (!(securityParticular.equals("")))
		{
			securityValue=(double) (Double.parseDouble(txtLandValue.getText().trim()));
			primarySecurityDetails.setLandParticulars(securityParticular);
			primarySecurityDetails.setLandValue(securityValue);
		}

		securityParticular=txtBuildingParticulars.getText().trim();
		if (!(securityParticular.equals("")))
		{
			securityValue=(double) (Double.parseDouble(txtBuildingValue.getText().trim()));
			primarySecurityDetails.setBldgParticulars(securityParticular);
			primarySecurityDetails.setBldgValue(securityValue);
		}

		securityParticular=txtMachineParticulars.getText().trim();
		if (!(securityParticular.equals("")))
		{
			securityValue=(double) (Double.parseDouble(txtMachineValue.getText().trim()));
			primarySecurityDetails.setMachineParticulars(securityParticular);
			primarySecurityDetails.setMachineValue(securityValue);
		}

		securityParticular=txtOtherAssetsParticulars.getText().trim();
		if (!(securityParticular.equals("")))
		{
			securityValue=(double) (Double.parseDouble(txtOtherAssetsValue.getText().trim()));
			primarySecurityDetails.setAssetsParticulars(securityParticular);
			primarySecurityDetails.setAssetsValue(securityValue);
		}

		securityParticular=txtCurrentAssetsParticulars.getText().trim();
		if (!(securityParticular.equals("")))
		{
			securityValue=(double) (Double.parseDouble(txtCurrentAssetsValue.getText().trim()));
			primarySecurityDetails.setCurrentAssetsParticulars(securityParticular);
			primarySecurityDetails.setCurrentAssetsValue(securityValue);
		}

		securityParticular=txtOthersParticulars.getText().trim();
		if (!(securityParticular.equals("")))
		{
			securityValue=(double) (Double.parseDouble(txtOthersValue.getText().trim()));
			primarySecurityDetails.setOthersParticulars(securityParticular);
			primarySecurityDetails.setOthersValue(securityValue);
		}

		projectOutlayDetails.setPrimarySecurityDetails(primarySecurityDetails);

		text = txtTermCredit.getText().trim();
		if (!(text.equals("")))
		{
			termCreditSanctioned=(double) (Double.parseDouble(text));
			projectOutlayDetails.setTermCreditSanctioned(termCreditSanctioned);
		}

		text = txtWCFundBased.getText().trim();
		if (!(text.equals("")))
		{
			wcFundBasedSanctioned=(double) (Double.parseDouble(text));
			projectOutlayDetails.setWcFundBasedSanctioned(wcFundBasedSanctioned);
		}

		text = txtWCNonFundBased.getText().trim();
		if (!(text.equals("")))
		{
			wcNonFundBasedSanctioned=(double) (Double.parseDouble(text));
			projectOutlayDetails.setWcNonFundBasedSanctioned(wcNonFundBasedSanctioned);
		}

		text = txtTCPromotersContribution.getText().trim();
		if (!(text.equals("")))
		{
			promotersContribution=(double) (Double.parseDouble(text));
			projectOutlayDetails.setTcPromoterContribution(promotersContribution);
		}

		text = txtWCPromotersContribution.getText().trim();
		if (!(text.equals("")))
		{
			promotersContribution=(double) (Double.parseDouble(text));
			projectOutlayDetails.setWcPromoterContribution(promotersContribution);
		}

		text = txtTCSupport.getText().trim();
		if (!(text.equals("")))
		{
			subsidyEquity=(double) (Double.parseDouble(text));
			projectOutlayDetails.setTcSubsidyOrEquity(subsidyEquity);
		}

		text = txtWCSupport.getText().trim();
		if (!(text.equals("")))
		{
			subsidyEquity=(double) (Double.parseDouble(text));
			projectOutlayDetails.setWcSubsidyOrEquity(subsidyEquity);
		}

		text = txtTCOthers.getText().trim();
		if (!(text.equals("")))
		{
			others=(double) (Double.parseDouble(text));
			projectOutlayDetails.setTcOthers(others);
		}

		text = txtWCOthers.getText().trim();
		if (!(text.equals("")))
		{
			others=(double) (Double.parseDouble(text));
			projectOutlayDetails.setWcOthers(others);
		}

		text = projectCostValue.getText().trim();
		projectCost=(double) (Double.parseDouble(text));
		projectOutlayDetails.setProjectCost(projectCost);

		text = wcAssessedValue.getText().trim();
		wcAssessed=(double) (Double.parseDouble(text));
		projectOutlayDetails.setWcAssessed(wcAssessed);

		text = projectOutlayValue.getText().trim();
		projectOutlay=(double) (Double.parseDouble(text));
		projectOutlayDetails.setProjectOutlay(projectOutlay);

		application.setProjectOutlayDetails(projectOutlayDetails);

		return application;
	}

	/**
	 * This method stores the term credit details of an application
	 */
	 private com.cgtsi.application.Application saveTermCreditDetails(com.cgtsi.application.Application application)
	{
		 com.cgtsi.application.TermLoan termLoan=new com.cgtsi.application.TermLoan();
		 double value;
		 Date date;
		 String text = "";
		 SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");

		 text = txtTermCredit.getText().trim();
		 if (!(text.equals("")))
		 {
			 value=(double) Double.parseDouble(text);
			 termLoan.setAmountSanctioned(value);
			 text = txtDateOfSanction.getText().trim();
			 date=dateFormat.parse(text, new ParsePosition(0));
			 termLoan.setAmountSanctionedDate(date);
			 text = txtCreditToBeGuaranteed.getText().trim();
			 if (!(text.equals("")))
			 {
				 value=(double) Double.parseDouble(text);
				 termLoan.setCreditGuaranteed(value);
			 }
			 text = txtAmtDisbursed.getText().trim();
			 if (!(text.equals("")))
			 {
				 value=(double) Double.parseDouble(text);
				 termLoan.setAmtDisbursed(value);
			 }
			 text = txtDateOfFirstDisbursement.getText().trim();
			 if (!(text.equals("")))
			 {
				 date=dateFormat.parse(text, new ParsePosition(0));
				 termLoan.setFirstDisbursementDate(date);
			 }
			 text = txtDateOfLastDisbursement.getText().trim();
			 if (!(text.equals("")))
			 {
				 date=dateFormat.parse(text, new ParsePosition(0));
				 termLoan.setFinalDisbursementDate(date);
			 }
			 termLoan.setTenure(Integer.parseInt(txtTenure.getText().trim()));
			 if (interestFixed.isSelected())
			 {
				 termLoan.setInterestType("T");
			 }
			 else if (interestFloating.isSelected())
			 {
				 termLoan.setInterestType("F");
			 }
			 value=(double) Double.parseDouble(txtTcInterestRate.getText().trim());
			 termLoan.setInterestRate(value);
			 value=(double) Double.parseDouble(txtPlr.getText().trim());
			 termLoan.setPlr(value);
			 termLoan.setTypeOfPLR(txtPlrType.getText().trim());
			 text = txtMoratorium.getText().trim();
			 if (!(text.equals("")))
			 {
				 termLoan.setRepaymentMoratorium(Integer.parseInt(text));
			 }
			 text = txtFirstInstDueDate.getText().trim();
			 date=dateFormat.parse(text, new ParsePosition(0));
			 termLoan.setFirstInstallmentDueDate(date);
			 if ((cmbPeriodicity.getSelectedItem().toString()).equals("Monthly"))
			 {
				 termLoan.setPeriodicity(1);
			 }
			 else if ((cmbPeriodicity.getSelectedItem().toString()).equals("Quarterly"))
			 {
				 termLoan.setPeriodicity(2);
			 }
			else if ((cmbPeriodicity.getSelectedItem().toString()).equals("Half-Yearly"))
			{
				termLoan.setPeriodicity(3);
			}
			 termLoan.setNoOfInstallments(Integer.parseInt(txtNoOfInstallments.getText().trim()));
			 if (! txtTcOsAmt.getText().trim().equals(""))
			 {
				 value=(double) Double.parseDouble(txtTcOsAmt.getText().trim());
				 termLoan.setPplOS(value);

				 //System.out.println("os as on date" + txtOsAsOn.getText().trim());
				 date=dateFormat.parse(txtOsAsOn.getText().trim(), new ParsePosition(0));
				 termLoan.setPplOsAsOnDate(date);
			 }

			 application.setTermLoan(termLoan);
		 }
		 return application;
	}

	/**
	 * This method saves the working capital details of the application.
	 */
	 private com.cgtsi.application.Application saveWorkingCapitalDetails(com.cgtsi.application.Application application)
	{
		 com.cgtsi.application.WorkingCapital workingCapital=new com.cgtsi.application.WorkingCapital();
		 double value;
		 Date date;
		 String text = "";
		 SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");

		 if (interestFixed.isSelected())
		 {
			 workingCapital.setWcInterestType("T");
		 }
		 else if (interestFloating.isSelected())
		 {
			 workingCapital.setWcInterestType("F");
		 }
		 value=(double) Double.parseDouble(txtWcInterestRate.getText().trim());
		 workingCapital.setLimitFundBasedInterest(value);

		 text = txtWCFundBased.getText().trim();
		 if (!(text.equals("")) && Double.parseDouble(text)!=0)
		 {
			 value=(double) Double.parseDouble(text);
			 workingCapital.setFundBasedLimitSanctioned(value);
//			 text = txtFBInterest.getText().trim();
//			 value=(double) Double.parseDouble(text);
//			 workingCapital.setLimitFundBasedInterest(value);
			 text = txtFBSanctionedDate.getText().trim();
			 date=dateFormat.parse(text, new ParsePosition(0));
			 workingCapital.setLimitFundBasedSanctionedDate(date);
			 text = txtFBCredit.getText().trim();
			 if (!(text.equals("")))
			 {
				 value=(double) Double.parseDouble(txtFBCredit.getText().trim());
				 workingCapital.setCreditFundBased(value);
			 }
			 text = txtOsFBPrincipal.getText().trim();
			 value=0;
			 if (!text.equals(""))
			 {
				value=(double) Double.parseDouble(text);
			 }
			 workingCapital.setOsFundBasedPpl(value);
			 text = txtOsFBAsOn.getText().trim();
			 if (!text.equals(""))
			 {
				date=dateFormat.parse(text, new ParsePosition(0));
				workingCapital.setOsFundBasedAsOnDate(date);
			 }
		 }

		 text = txtWCNonFundBased.getText().trim();
		 if (!(text.equals("")) && Double.parseDouble(text)!=0)
		 {
			 value=(double) Double.parseDouble(text);
			 workingCapital.setNonFundBasedLimitSanctioned(value);
			 text = txtNFBCommission.getText().trim();
			 value=(double) Double.parseDouble(text);
			 workingCapital.setLimitNonFundBasedCommission(value);
			 text = txtNFBSanctionedDate.getText().trim();
			 date=dateFormat.parse(text, new ParsePosition(0));
			 workingCapital.setLimitNonFundBasedSanctionedDate(date);
			 text = txtNFBCredit.getText().trim();
			 if (!(text.equals("")))
			 {
				 value=(double) Double.parseDouble(text);
				 workingCapital.setCreditNonFundBased(value);
			 }
			text = txtOsNFBPrincipal.getText().trim();
			value=0;
			if (!text.equals(""))
			{
			   value=(double) Double.parseDouble(text);
			}
			workingCapital.setOsNonFundBasedPpl(value);
			text = txtOsNFBCommission.getText().trim();
			value=0;
			if (!text.equals(""))
			{
			   value=(double) Double.parseDouble(text);
			}
			workingCapital.setOsNonFundBasedCommissionAmt(value);			
			text = txtOsNFBAsOn.getText().trim();
			if (!text.equals(""))
			{
			   date=dateFormat.parse(text, new ParsePosition(0));
			   workingCapital.setOsNonFundBasedAsOnDate(date);
			}			 
		 }

		 value=(double) Double.parseDouble(txtWcPlr.getText().trim());
		 workingCapital.setWcPlr(value);

		 workingCapital.setWcTypeOfPLR(txtWcPlrType.getText().trim());

		 application.setWc(workingCapital);
		 return application;
	}

	/**
	 * This method saves the Securitisation Details.
	 */
	private com.cgtsi.application.Application saveSecuritisationDetails(com.cgtsi.application.Application application)
	{
		com.cgtsi.application.Securitization securitisation = new com.cgtsi.application.Securitization();
		double value;
		String text = "";

		text = txtSpreadOverPLR.getText().trim();
		if (! text.equals(""))
		{
			value = (double) (Double.parseDouble(text));
			securitisation.setSpreadOverPLR(value);
		}

		if (repayInEqualInstYes.isSelected())
		{
			securitisation.setPplRepaymentInEqual("Y");
		}
		else if (repayInEqualInstNo.isSelected())
		{
			securitisation.setPplRepaymentInEqual("N");
		}
		
		text = txtTangibleNetworth.getText().trim();
		if (! text.equals(""))
		{
			value = (double) (Double.parseDouble(text));
			securitisation.setTangibleNetWorth(value);
		}

		text = txtFixedAssetCoverageRatio.getText().trim();
		if (! text.equals(""))
		{
			value = (double) (Double.parseDouble(text));
			securitisation.setFixedACR(value);
		}

		text = txtCurrentRatio.getText().trim();
		if (! text.equals(""))
		{
			value = (double) (Double.parseDouble(text));
			securitisation.setCurrentRatio(value);
		}

/*		text = txtFinPartOfUnit.getText().trim();
		if (! text.equals(""))
		{
			value = (double) (Double.parseDouble(text));
			securitisation.setFinancialPartUnit(value);
		}*/

		text = txtMinDSCR.getText().trim();
		if (! text.equals(""))
		{
			value = (double) (Double.parseDouble(text));
			securitisation.setMinimumDSCR(value);
		}

		text = txtAvgDSCR.getText().trim();
		if (! text.equals(""))
		{
			value = (double) (Double.parseDouble(text));
			securitisation.setAvgDSCR(value);
		}

		application.setSecuritization(securitisation);

		return application;
	}

	/**
	 * This method saves the MCGF Details if the member is a MCGF member.
	 */
	private com.cgtsi.application.Application saveMCGSDetails(com.cgtsi.application.Application application)
	{
		com.cgtsi.mcgs.MCGFDetails mcgfDetails = new com.cgtsi.mcgs.MCGFDetails();
		double value;
		Date date;
		String text="";
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		text = (String) cmbParticipatingBanks.getSelectedItem();
		if (text.equals("Select"))
		{
			text = "";
		}
		mcgfDetails.setParticipatingBank(text);
//		System.out.println(mcgfDetails.getParticipatingBank());
		text = txtPartBankBranch.getText().trim();
		mcgfDetails.setParticipatingBankBranch(text);
		text = txtMcgfDistrict.getText().trim();
		mcgfDetails.setMcgfDistrict(text);
		mcgfDetails.setMcgfId((String) cmbMcgfId.getSelectedItem());
		mcgfDetails.setMcgfName(mcgfNameValue.getText().trim());
		if (!((txtAmtApprovedByMcgf.getText().trim()).equals("")))
		{
			value = (double) (Double.parseDouble(txtAmtApprovedByMcgf.getText().trim()));
			mcgfDetails.setMcgfApprovedAmt(value);
		}
		if (!(txtApprovedDate.getText().trim()).endsWith("/") && ! (txtApprovedDate.getText().trim()).equals(""))
		{
			date = dateFormat.parse(txtApprovedDate.getText().trim(), new ParsePosition(0));
			mcgfDetails.setMcgfApprovedDate(date);
		}
		if (!(txtGCoverStartDate.getText().trim()).endsWith("/") && ! (txtGCoverStartDate.getText().trim()).equals(""))
		{
			date = dateFormat.parse(txtGCoverStartDate.getText().trim(), new ParsePosition(0));
			mcgfDetails.setMcgfGuaranteeCoverStartDate(date);
		}

		application.setMCGFDetails(mcgfDetails);
		return application;
	}

	/**
	* This method saves the working capital enhancement details.
	*/
	private com.cgtsi.application.Application saveWCEDetails(com.cgtsi.application.Application application)
	{
		double value;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		com.cgtsi.application.WorkingCapital wc = new com.cgtsi.application.WorkingCapital();
		 if (interestFixed.isSelected())
		 {
			 wc.setWcInterestType("T");
		 }
		 else if (interestFloating.isSelected())
		 {
			 wc.setWcInterestType("F");
		 }
		 value=0;
		 if (!txtEnhancedInterest.getText().trim().equals(""))
		 {
			value=(double) Double.parseDouble(txtEnhancedInterest.getText().trim());
		 }
		 wc.setLimitFundBasedInterest(value);
		 value=0;
		 if (!enhancedFundBasedValue.getText().trim().equals(""))
		 {
			value = (double)(Double.parseDouble(enhancedFundBasedValue.getText().trim()));
		 }
		wc.setEnhancedFundBased(value);
		value=0;
		if (!txtEnhancedInterest.getText().trim().equals(""))
		{
			value = (double)(Double.parseDouble(txtEnhancedInterest.getText().trim()));
		}
		wc.setEnhancedFBInterest(value);
		value=0;
		if (!enhancedNonFundBasedValue.getText().trim().equals(""))
		{
			value = (double)(Double.parseDouble(enhancedNonFundBasedValue.getText().trim()));
		}
		wc.setEnhancedNonFundBased(value);
		value=0;
		if (!txtEnhancedCommission.getText().trim().equals(""))
		{
			value = (double)(Double.parseDouble(txtEnhancedCommission.getText().trim()));
		}
		wc.setEnhancedNFBComission(value);
		wc.setLimitNonFundBasedCommission(value);
//		value = (double)(Double.parseDouble(enhancedTotalValue.getText().trim()));
//		wc.setEnhancedTotal(value);
		if (!txtDtOfEnhancement.getText().trim().equals("") && !txtDtOfEnhancement.getText().trim().endsWith("/"))
		{
			Date date=dateFormat.parse(txtDtOfEnhancement.getText().trim(), new ParsePosition(0));
			wc.setEnhancementDate(date);
		}
		application.setWc(wc);
		application.setWcEnhancement(true);
		return application;
	}

	/**
	* This method saves the working capital enhancement details.
	*/
	private com.cgtsi.application.Application saveWCRDetails(com.cgtsi.application.Application application)
	{
		double value;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		com.cgtsi.application.WorkingCapital wc = new com.cgtsi.application.WorkingCapital();
		if (interestFixed.isSelected())
		{
			wc.setWcInterestType("T");
		}
		else if (interestFloating.isSelected())
		{
			wc.setWcInterestType("F");
		}
		value=0;
		if (!renewedFundBasedValue.getText().trim().equals(""))
		{
			value = (double)(Double.parseDouble(renewedFundBasedValue.getText().trim()));
		}
		wc.setRenewalFundBased(value);
		value=0;
		if (!txtRenewedInterest.getText().trim().equals(""))
		{
			value = (double)(Double.parseDouble(txtRenewedInterest.getText().trim()));
		}
		wc.setRenewalFBInterest(value);
		value=0;
		if (!renewedNonFundBasedValue.getText().trim().equals(""))
		{
			value = (double)(Double.parseDouble(renewedNonFundBasedValue.getText().trim()));
		}
		wc.setRenewalNonFundBased(value);
		value=0;
		if (!txtRenewedCommission.getText().trim().equals(""))
		{
			value = (double)(Double.parseDouble(txtRenewedCommission.getText().trim()));
		}
		wc.setRenewalNFBCommission(value);		
//		value = (double)(Double.parseDouble(renewedTotalValue.getText().trim()));
//		wc.setRenewalTotal(value);
		value=(double) Double.parseDouble(txtWcPlr.getText().trim());
		wc.setWcPlr(value);
		wc.setWcTypeOfPLR(txtWcPlrType.getText().trim());
		Date date=dateFormat.parse(txtDtOfRenewal.getText().trim(), new ParsePosition(0));
		wc.setRenewalDate(date);
		application.setWc(wc);
		application.setWcRenewal(true);
		return application;
	}

	/**
	* This methods save the term credit application details entered by the user.
	* The details are stored in a file as a serialized object.
	*/
	private void saveTCApp() throws ThinClientException
	{
		com.cgtsi.application.Application application=new com.cgtsi.application.Application();
		application=saveCommonAppDetails(application);
		application=saveTermCreditDetails(application);
		application = saveSecuritisationDetails(application);
		if (mcgfFlag)
		{
			application=saveMCGSDetails(application);
		}
		application.setIsVerified(false);

		String fileName=getFileName("APP", "NEW", application.getMliBranchName());
//		fileName=application.getMliBranchName()+fileName;
		
//		System.out.println("saving " + fileName);

		writeToFile(application, "TC", fileName);
	}

	/**
	* This methods save the working capital application details entered by the user.
	* The details are stored in a file as a serialized object.
	*/
	private void saveWCApp() throws ThinClientException
	{
		com.cgtsi.application.Application application=new com.cgtsi.application.Application();
		application=saveCommonAppDetails(application);
		application=saveWorkingCapitalDetails(application);
		application = saveSecuritisationDetails(application);
		if (mcgfFlag)
		{
			application=saveMCGSDetails(application);
		}
		application.setIsVerified(false);

		String fileName=getFileName("APP", "NEW", application.getMliBranchName());
//		fileName=application.getMliBranchName()+fileName;

		writeToFile(application, "WC", fileName);
	}

	/**
	* This methods save the working capital enhancement application details entered by the user.
	* The details are stored in a file as a serialized object.
	*/
	private void saveWCEApp() throws ThinClientException
	{
		com.cgtsi.application.Application application=new com.cgtsi.application.Application();
		application=saveCommonAppDetails(application);
		application=saveWCEDetails(application);
		application=saveSecuritisationDetails(application);

		application.setLoanType("WC");
		application.setIsVerified(false);

		String fileName=getFileName("APP", "NEW", application.getMliBranchName());
//		fileName=application.getMliBranchName()+fileName;

		writeToFile(application, "WCE", fileName);
	}

	/**
	* This methods save the working capital renewal application details entered by the user.
	* The details are stored in a file as a serialized object.
	*/
	private void saveWCRApp() throws ThinClientException
	{
		com.cgtsi.application.Application application=new com.cgtsi.application.Application();
		application=saveCommonAppDetails(application);
		application=saveWCRDetails(application);
		application = saveSecuritisationDetails(application);
		if (mcgfFlag)
		{
			application=saveMCGSDetails(application);
		}
		application.setLoanType("WC");
		application.setIsVerified(false);

		String fileName=getFileName("APP", "NEW", application.getMliBranchName());
//		fileName=application.getMliBranchName()+fileName;

		writeToFile(application, "WCR", fileName);
	}

	/**
	* This methods save the composite application details entered by the user.
	* The details are stored in a file as a serialized object.
	*/
	private void saveCompositeApp() throws ThinClientException
	{
		com.cgtsi.application.Application application=new com.cgtsi.application.Application();
		application=saveCommonAppDetails(application);
		application=saveTermCreditDetails(application);
		application=saveWorkingCapitalDetails(application);
		application = saveSecuritisationDetails(application);
		if (mcgfFlag)
		{
			application=saveMCGSDetails(application);
		}
		application.setIsVerified(false);

		String fileName=getFileName("APP", "NEW", application.getMliBranchName());
//		fileName=application.getMliBranchName()+fileName;

		writeToFile(application, "CC", fileName);
	}

	/**
	* This methods save the both application details entered by the user.
	* The details are stored in a file as a serialized object.
	*/
	private void saveBothApp() throws ThinClientException
	{
		com.cgtsi.application.Application application=new com.cgtsi.application.Application();
		application=saveCommonAppDetails(application);
		application=saveTermCreditDetails(application);
		application=saveWorkingCapitalDetails(application);
		application = saveSecuritisationDetails(application);
		if (mcgfFlag)
		{
			application=saveMCGSDetails(application);
		}
		application.setIsVerified(false);

		String fileName=getFileName("APP", "NEW", application.getMliBranchName());
//		fileName=application.getMliBranchName()+fileName;

		writeToFile(application, "BO", fileName);
	}

	/**
	* This methods save the additional term credit application details entered by the user.
	* The details are stored in a file as a serialized object.
	*/
	private void saveAddTCApp() throws ThinClientException
	{
		com.cgtsi.application.Application application=new com.cgtsi.application.Application();
		application.setAdditionalTC(true);
		application=saveCommonAppDetails(application);
		application=saveTermCreditDetails(application);
		application = saveSecuritisationDetails(application);
		if (mcgfFlag)
		{
			application=saveMCGSDetails(application);
		}
		application.setLoanType("TC");
		application.setIsVerified(false);

		String fileName=getFileName("APP", "NEW", application.getMliBranchName());
//		fileName=application.getMliBranchName()+fileName;

		writeToFile(application, "ATC", fileName);
	}

	/**
	 * This method is invoked for saving the verified or modified details.
	 * The parameter objType identifies the type of object  - APP, PER, CLAIMS
	 * The parameter flag identifies whether MOD - modify or VER - verify or NEW - new.
	 */
	 private boolean saveVerifiedModifiedDetails(String objType, String flag) throws ThinClientException
	 {
		 boolean success = false;
		 try
		 {
			 String key=lblKey.getText();
			 String fileName=lblFileName.getText();
			String brnName="";
			 if(lblBrnName!=null)
			 {
				brnName=lblBrnName.getText();
			 }
//			 System.out.println("ver filename " + fileName);
//			System.out.println("ver brnname " + brnName);
			 if (objType.equals("APP"))
			 {
				 com.cgtsi.application.Application application=new com.cgtsi.application.Application();
				 application=saveCommonAppDetails(application);
				 String appType=application.getLoanType();
//				 System.out.println(" 1 " + appType);
				 if (mcgfFlag)
				 {
					 application = saveMCGSDetails(application);
				 }
				 application = saveSecuritisationDetails(application);
				 if (appType.equals("TC"))
				 {
					 if (validateTCApp())
					 {
						 application=saveTermCreditDetails(application);
						 if (flag.equals("VER"))
						 {
							application.setIsVerified(true);
							 int confirmValue = JOptionPane.showConfirmDialog(panel, "Verification can be done only by the operating office (Zone / Region) concerned. Do you want to continue?", "Confirm Verification", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
//							 System.out.println("confirm value " + confirmValue);
							 if (confirmValue == 0)
							 {
								 writeVerifiedModifiedDetails(application, objType, key, fileName, flag);
							 }else if (confirmValue==2)
							 {
								return false;
							 }
						 }
						 else
						 {
							application.setIsVerified(false);
							 writeVerifiedModifiedDetails(application, objType, key, fileName, flag);
						 }
						 success = true;
					 }
				 }
				 else if (appType.equals("WC"))
				 {
					 if (validateWCApp())
					 {
						 application=saveWorkingCapitalDetails(application);
						 if (flag.equals("VER"))
						 {
							application.setIsVerified(true);
							 int confirmValue = JOptionPane.showConfirmDialog(panel, "Verification can be done only by the operating office (Zone / Region) concerned. Do you want to continue?", "Confirm Verification", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
							 if (confirmValue == 0)
							 {
								 writeVerifiedModifiedDetails(application, objType, key, fileName, flag);
							 }else if (confirmValue==2)
							{
							   return false;
							}
						 }
						 else
						 {
							application.setIsVerified(false);
							 writeVerifiedModifiedDetails(application, objType, key, fileName, flag);
						 }
						 success = true;
					 }
				 }
				 else if (appType.equals("CC") || appType.equals("BO"))
				 {
					 if (validateCCBOApp())
					 {
						 application=saveTermCreditDetails(application);
						 application=saveWorkingCapitalDetails(application);
						 if (flag.equals("VER"))
						 {
							application.setIsVerified(true);
							 int confirmValue = JOptionPane.showConfirmDialog(panel, "Verification can be done only by the operating office (Zone / Region) concerned. Do you want to continue?", "Confirm Verification", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
							 if (confirmValue == 0)
							 {
								 writeVerifiedModifiedDetails(application, objType, key, fileName, flag);
							 }else if (confirmValue==2)
							{
							   return false;
							}
						 }
						 else
						 {
							application.setIsVerified(false);
							 writeVerifiedModifiedDetails(application, objType, key, fileName, flag);
						 }
						 success = true;
					 }
				 }
				 else if (appType.equals("Additional Term Loan"))
				 {
						String error = "";
					if (txtBankRefNo.getText().trim().equals(""))
					{
						error = error + "\n" + "Enter Bank Reference Number";
					}
					if (txtBankBranchName.getText().trim().equals(""))
					{
						error = error + "\n" + "Enter Bank Branch Name";
					}
						if (txtSsiValue.getText().trim().equals(""))
						{
							error = error + "\n" + "Enter CGPAN";
						}
						else
						{
							String cgpan=txtSsiValue.getText().trim();
							if (cgpan.length()!=13 || !cgpan.substring(11, 12).equalsIgnoreCase("T"))
							{
								error = error + "\n" + "Enter Valid CGPAN";
							}
						}
						error = error + validateTermCreditDetails();					
						if (error.equals(""))
						{
							application=saveTermCreditDetails(application);
							application = saveSecuritisationDetails(application);
							if (mcgfFlag)
							{
								application=saveMCGSDetails(application);
							}
							application.setAdditionalTC(true);
							application.setLoanType("TC");
							 if (flag.equals("VER"))
							 {
								application.setIsVerified(true);
								 int confirmValue = JOptionPane.showConfirmDialog(panel, "Verification can be done only by the operating office (Zone / Region) concerned. Do you want to continue?", "Confirm Verification", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
								 if (confirmValue == 0)
								 {
									 writeVerifiedModifiedDetails(application, objType, key, fileName, flag);
								 }else if (confirmValue==2)
								{
								   return false;
								}
							 }
							 else
							 {
								application.setIsVerified(false);
								 writeVerifiedModifiedDetails(application, objType, key, fileName, flag);
							 }
							 success = true;
						}
						else
						{
							save.setEnabled(false);
							error = "Please Correct the Following:" + error;
		//					System.out.println("Errors Found...!!!!");
				//			JOptionPane.showMessageDialog(panel, error, "Error", JOptionPane.ERROR_MESSAGE);
							JButton test = new JButton("OK");
							test.setActionCommand("ErrorOk");
							test.addActionListener(this);
							Object[] options = {test};
							Icon icon=null;
							JOptionPane optionPane = new JOptionPane(error, JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, icon, options);
							dialog = new JDialog(this, "Error", false);
							dialog = optionPane.createDialog(panel, "Error");
							dialog.setModal(false);
							dialog.show();
						}
				 }
				 else if (appType.equals("Working Capital Enhancement"))
				 {
						String error = "";
						double fb=0;
						double nfb=0;
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
						if (txtSsiValue.getText().trim().equals(""))
						{
							error = error + "\n" + "Enter CGPAN";
						}
						else
						{
							String cgpan=txtSsiValue.getText().trim();
							if (cgpan.length()!=13 || (!cgpan.substring(11, 12).equalsIgnoreCase("W") && !cgpan.substring(11, 12).equalsIgnoreCase("R")))
							{
								error = error + "\n" + "Enter Valid CGPAN";
							}
						}
					if (!txtWCFundBased.getText().trim().equals(""))
					{
						fb=Double.parseDouble(txtWCFundBased.getText().trim());
					}
					if (!txtWCNonFundBased.getText().trim().equals(""))
					{
						nfb=Double.parseDouble(txtWCNonFundBased.getText().trim());
					}				
					if (fb==0 && nfb==0)
					{
						error = error + "\n" + "Enter Either Enhanced Fund Based Amount or Non Fund Based Amount";
					}
					if (fb>0 && (txtEnhancedInterest.getText().trim().equals("") || Double.parseDouble(txtEnhancedInterest.getText().trim())==0))
					{
						error = error + "\n" + "Enter Enhanced Fund Based Interest";
					}
					else if (fb==0 && (!txtEnhancedInterest.getText().trim().equals("") && Double.parseDouble(txtEnhancedInterest.getText().trim())>0))
					{
						error = error + "\n" + "Enhanced Interest cannot be entered if Enhanced Fund Based Amount is not entered";
					}
					if (nfb>0 && (txtEnhancedCommission.getText().trim().equals("") || Double.parseDouble(txtEnhancedCommission.getText().trim())==0))
					{
						error = error + "\n" + "Enter Enhanced Non Fund Based Commission";
					}
					else if (nfb==0 && (!txtEnhancedCommission.getText().trim().equals("") && Double.parseDouble(txtEnhancedCommission.getText().trim())>0))
					{
						error = error + "\n" + "Enhanced Commission cannot be entered if Enhanced Non Fund Based Amount is not entered";
					}				
					if (txtDtOfEnhancement.getText().trim().endsWith("/") || txtDtOfEnhancement.getText().trim().equals(""))
					{
						error = error + "\n" + "Enter Date of Enhancement";
					}
					else
					{
						Date sancDate = dateFormat.parse(txtDtOfEnhancement.getText().trim(), new ParsePosition(0));
						Date currDate = dateFormat.parse(dateFormat.format(new java.util.Date()), new ParsePosition(0));
						if ((currDate.compareTo(sancDate))<0)
						{
							error = error + "\n" + "Date of Enhancement should not be later than Current Date";
						}
					}
						if (error.equals(""))
						{
							application=saveWCEDetails(application);
							application.setLoanType("WC");

							 if (flag.equals("VER"))
							 {
								application.setIsVerified(true);
								 int confirmValue = JOptionPane.showConfirmDialog(panel, "Verification can be done only by the operating office (Zone / Region) concerned. Do you want to continue?", "Confirm Verification", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
								 if (confirmValue == 0)
								 {
									 writeVerifiedModifiedDetails(application, objType, key, fileName, flag);
								 }else if (confirmValue==2)
								{
								   return false;
								}
							 }
							 else
							 {
								application.setIsVerified(false);
								 writeVerifiedModifiedDetails(application, objType, key, fileName, flag);
							 }
							 success = true;
						}
						else
						{
							save.setEnabled(false);
							error = "Please Correct the Following:" + error;
							JButton test = new JButton("OK");
							test.setActionCommand("ErrorOk");
							test.addActionListener(this);
							Object[] options = {test};
							Icon icon=null;
							JOptionPane optionPane = new JOptionPane(error, JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, icon, options);
							dialog = new JDialog(this, "Error", false);
							dialog = optionPane.createDialog(panel, "Error");
							dialog.setModal(false);
							dialog.show();
						}
				 }
				 else if (appType.equals("Working Capital Renewal"))
				 {
						String error = "";
						double fb=0;
						double nfb=0;
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
					if (txtBankRefNo.getText().trim().equals(""))
					{
						error = error + "\n" + "Enter Bank Reference Number";
					}
					if (txtBankBranchName.getText().trim().equals(""))
					{
						error = error + "\n" + "Enter Bank Branch Name";
					}
						if (txtSsiValue.getText().trim().equals(""))
						{
							error = error + "\n" + "Enter CGPAN";
						}
						else
						{
							String cgpan=txtSsiValue.getText().trim();
							if (cgpan.length()!=13 || (!cgpan.substring(11, 12).equalsIgnoreCase("W") && !cgpan.substring(11, 12).equalsIgnoreCase("R")))
							{
								error = error + "\n" + "Enter Valid CGPAN";
							}
						}						
					if (!txtWCFundBased.getText().trim().equals(""))
					{
						fb=Double.parseDouble(txtWCFundBased.getText().trim());
					}
					if (!txtWCNonFundBased.getText().trim().equals(""))
					{
						nfb=Double.parseDouble(txtWCNonFundBased.getText().trim());
					}				
					if (fb==0 && nfb==0)
					{
						error = error + "\n" + "Enter Either Renewed Fund Based Amount or Non Fund Based Amount";
					}
					if (fb>0 && (txtRenewedInterest.getText().trim().equals("") || Double.parseDouble(txtRenewedInterest.getText().trim())==0))
					{
						error = error + "\n" + "Enter Renewed Fund Based Interest";
					}
					else if (fb==0 && (!txtRenewedInterest.getText().trim().equals("") && Double.parseDouble(txtRenewedInterest.getText().trim())>0))
					{
						error = error + "\n" + "Renewed Interest cannot be entered if Renewed Fund Based Amount is not entered";
					}
					if (nfb>0 && (txtRenewedCommission.getText().trim().equals("") || Double.parseDouble(txtRenewedCommission.getText().trim())==0))
					{
						error = error + "\n" + "Enter Renewed Non Fund Based Commission";
					}
					else if (nfb==0 && (!txtRenewedCommission.getText().trim().equals("") && Double.parseDouble(txtRenewedCommission.getText().trim())>0))
					{
						error = error + "\n" + "Renewed Commission cannot be entered if Renewed Non Fund Based Amount is not entered";
					}				
					if (txtDtOfRenewal.getText().trim().endsWith("/") || txtDtOfRenewal.getText().trim().equals(""))
					{
						error = error + "\n" + "Enter Date of Renewal";
					}
					else
					{
						Date sancDate = dateFormat.parse(txtDtOfRenewal.getText().trim(), new ParsePosition(0));
						Date currDate = dateFormat.parse(dateFormat.format(new java.util.Date()), new ParsePosition(0));
						if ((currDate.compareTo(sancDate))<0)
						{
							error = error + "\n" + "Date of Renewal should not be later than Current Date";
						}
					}
					if (txtWcPlr.getText().trim().equals("") || Double.parseDouble(txtWcPlr.getText().trim())==0.0)
					{
						error = error + "\n" + "Enter PLR";
					}
					if (txtWcPlrType.getText().trim().equals(""))
					{
						error = error + "\n" + "Enter Type of PLR";
					}	
						if (error.equals(""))
						{
							application=saveWCRDetails(application);
							application.setLoanType("WC");

							 if (flag.equals("VER"))
							 {
								application.setIsVerified(true);
								 int confirmValue = JOptionPane.showConfirmDialog(panel, "Verification can be done only by the operating office (Zone / Region) concerned. Do you want to continue?", "Confirm Verification", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
								 if (confirmValue == 0)
								 {
									 writeVerifiedModifiedDetails(application, objType, key, fileName, flag);
								 }else if (confirmValue==2)
								{
								   return false;
								}
							 }
							 else
							 {
								application.setIsVerified(false);
								 writeVerifiedModifiedDetails(application, objType, key, fileName, flag);
							 }
							 success = true;
						}
						else
						{
							save.setEnabled(false);
							error = "Please Correct the Following:" + error;
							JButton test = new JButton("OK");
							test.setActionCommand("ErrorOk");
							test.addActionListener(this);
							Object[] options = {test};
							Icon icon=null;
							JOptionPane optionPane = new JOptionPane(error, JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, icon, options);
							dialog = new JDialog(this, "Error", false);
							dialog = optionPane.createDialog(panel, "Error");
							dialog.setModal(false);
							dialog.show();
						}
				 }
			 }
			 else if (objType.equals("PER"))
			 {
				 com.cgtsi.guaranteemaintenance.PeriodicInfo periodicInfo=new com.cgtsi.guaranteemaintenance.PeriodicInfo();
				 periodicInfo = savePeriodicInfo(key, fileName);
				 if (periodicInfo==null)
				 {
				 	return false;
				 }
				 if (fileName.equals(""))
				 {
					 fileName = getFileName("PER", "NEW", brnName);
					 periodicInfo.setIsVerified(false);
					 writeToFile(periodicInfo, periodicInfo.getBorrowerId(), fileName);
					 success = true;
				 }
				 else
				 {
					 if (flag.equals("VER"))
					 {
						periodicInfo.setIsVerified(true);
						 int confirmValue = JOptionPane.showConfirmDialog(panel, "Verification can be done only by the operating office (Zone / Region) concerned. Do you want to continue?", "Confirm Verification", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
						 if (confirmValue == 0)
						 {
							 writeVerifiedModifiedDetails(periodicInfo, objType, key, fileName, flag);
						 }else if (confirmValue==2)
						{
						   return false;
						}
					 }
					 else
					 {
						periodicInfo.setIsVerified(false);
						 writeVerifiedModifiedDetails(periodicInfo, objType, key, fileName, flag);
					 }
					 success = true;
				 }
			 }
			 else if (objType.equals("PERUPDREC"))
			 {
					String recNo = recoveryNoValue.getText();
//					System.out.println("id " + recNo);
					com.cgtsi.guaranteemaintenance.Recovery recovery = saveRecoveryDetails();
//					System.out.println("1 " + recovery.getDateOfRecovery());
//					System.out.println("2 " + recovery.getAmountRecovered());
//					System.out.println("3 " + recovery.getLegalCharges());
//					System.out.println("4 " + recovery.getRecoveryNo());
					recovery.setRecoveryNo(recNo);
//					System.out.println("5 " + recovery.getRecoveryNo());

					Hashtable objects = readFromFile(fileName);
					com.cgtsi.guaranteemaintenance.PeriodicInfo periodicInfo = (com.cgtsi.guaranteemaintenance.PeriodicInfo) objects.get(key);
					ArrayList recDetails = periodicInfo.getRecoveryDetails();
					for (int i=0;i<recDetails.size();i++)
					{
						com.cgtsi.guaranteemaintenance.Recovery tempRecovery = (com.cgtsi.guaranteemaintenance.Recovery) recDetails.get(i);
//						System.out.println("id " + i + " " + tempRecovery.getRecoveryNo());
						if (recNo==null || tempRecovery.getRecoveryNo().equals(recNo))
						{
//							System.out.println("found equal " + recNo);
							recDetails.set(i, recovery);
							break;
						}
					}
					com.cgtsi.guaranteemaintenance.PeriodicInfo writePeriodicInfo = new com.cgtsi.guaranteemaintenance.PeriodicInfo();
					writePeriodicInfo.setBorrowerId(periodicInfo.getBorrowerId());
					writePeriodicInfo.setBorrowerName(periodicInfo.getBorrowerName());
					writePeriodicInfo.setRecoveryDetails(recDetails);
					if (flag.equals("NEW"))
					{
						String newFileName = getFileName("PERUPDREC", "NEW", brnName);
						periodicInfo.setIsVerified(false);
						writeToFile(writePeriodicInfo, key, newFileName);
						success=true;
					}
				 else
				 {
					 if (flag.equals("VER"))
					 {
						periodicInfo.setIsVerified(true);
						 int confirmValue = JOptionPane.showConfirmDialog(panel, "Verification can be done only by the operating office (Zone / Region) concerned. Do you want to continue?", "Confirm Verification", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
						 if (confirmValue == 0)
						 {
							 writeVerifiedModifiedDetails(writePeriodicInfo, objType, key, fileName, flag);
						 }else if (confirmValue==2)
						{
						   return false;
						}
					 }
					 else
					 {
						periodicInfo.setIsVerified(false);
						 writeVerifiedModifiedDetails(writePeriodicInfo, objType, key, fileName, flag);
					 }
					 success = true;
				 }
			 }
			 else if (objType.equals("CLAIM1"))
			 {
				 objType = "CLAIM";
				 com.cgtsi.claim.ClaimApplication claimApplication = new com.cgtsi.claim.ClaimApplication();
				 //System.out.println("getting claim application object");
				 claimApplication = saveClaims(key, fileName, true);
				 //System.out.println("got claim application object");
				 if (fileName.equals(""))
				 {
					 fileName = getFileName("CLAIM", "NEW", brnName);
					 claimApplication.setIsVerified(false);
					 writeToFile(claimApplication, key, fileName);
					 success = true;
				 }
				 else
				 {
					 if (flag.equals("VER"))
					 {
						claimApplication.setIsVerified(true);
						 int confirmValue = JOptionPane.showConfirmDialog(panel, "Verification can be done only by the operating office (Zone / Region) concerned. Do you want to continue?", "Confirm Verification", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
						 if (confirmValue == 0)
						 {
							 writeVerifiedModifiedDetails(claimApplication, objType, key, fileName, flag);
						 }else if (confirmValue==2)
						{
						   return false;
						}
					 }
					 else
					 {
						claimApplication.setIsVerified(false);
						 writeVerifiedModifiedDetails(claimApplication, objType, key, fileName, flag);
					 }
					 success = true;
				 }
			 }
			 else if (objType.equals("CLAIM2"))
			 {
				 objType = "CLAIM";
				 com.cgtsi.claim.ClaimApplication claimApplication = new com.cgtsi.claim.ClaimApplication();
				 //System.out.println("getting claim application object");
				 claimApplication = saveClaims(key, fileName, false);
				 //System.out.println("got claim application object");
				 if (fileName.equals(""))
				 {
					 fileName = getFileName("CLAIM", "NEW", brnName);
					claimApplication.setIsVerified(false);
					 writeToFile(claimApplication, key, fileName);
					 success = true;
				 }
				 else
				 {
					 if (flag.equals("VER"))
					 {
						claimApplication.setIsVerified(true);
						 int confirmValue = JOptionPane.showConfirmDialog(panel, "Verification can be done only by the operating office (Zone / Region) concerned. Do you want to continue?", "Confirm Verification", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
						 if (confirmValue == 0)
						 {
							 writeVerifiedModifiedDetails(claimApplication, objType, key, fileName, flag);
						 }else if (confirmValue==2)
						{
						   return false;
						}
					 }
					 else
					 {
						claimApplication.setIsVerified(false);
						 writeVerifiedModifiedDetails(claimApplication, objType, key, fileName, flag);
					 }
					 success = true;
				 }
			 }
		 }
		 catch (ThinClientException exception)
		 {
			 displayError(exception.getMessage());
		 }
		 validate();
		 return success;
	 }

	/**
	 * This method constructs a panel to display an error message
	 * when an exception occurs in the processing.
	 */
	private void displayError(String expMessage)
	{
		panel.removeAll();
		panel.setLayout(new BorderLayout());
		JLabel errorHeading = new JLabel("Encountered Error", JLabel.CENTER);
		errorHeading.setFont(headingFont);
		panel.add(errorHeading, BorderLayout.NORTH);
		JLabel errorText = new JLabel(expMessage, JLabel.CENTER);
		errorText.setFont(headingFont);
		panel.add(errorText, BorderLayout.CENTER);
		container.removeAll();
		container.add(panel, BorderLayout.CENTER);
	}

	private void enableBorrowerDetails(boolean active)
	{
		txtSsiValue.setText("");
		txtSsiValue.setEnabled(! active);
		cmbConstitution.setSelectedItem("Select");
		cmbConstitution.setEnabled(active);
		txtConstitution.setText("");
		txtConstitution.setEnabled(active);
		cmbUnitNames.setSelectedItem("Select");
		cmbUnitNames.setEnabled(active);
		txtUnitName.setText("");
		txtUnitName.setEnabled(active);
		txtSsiRegNo.setText("");
		txtSsiRegNo.setEnabled(active);
		txtFirmItpan.setText("");
		txtFirmItpan.setEnabled(active);
		cmbIndNature.setSelectedItem("Select");
		cmbIndNature.setEnabled(active);
		cmbIndSector.setSelectedItem("Select");
		cmbIndSector.setEnabled(active);
		txtActivity.setText("");
		txtActivity.setEnabled(active);
		txtNoOfEmp.setText("");
		txtNoOfEmp.setEnabled(active);
		txtOptSalesTurnover.setText("");
		txtOptSalesTurnover.setEnabled(active);
		txtOptExports.setText("");
		txtOptExports.setEnabled(active);
		txtAddress.setText("");
		txtAddress.setEnabled(active);
		cmbState.setSelectedItem("Select");
		cmbState.setEnabled(active);
		cmbDistrict.setSelectedItem("Select");
		cmbDistrict.setEnabled(active);
		txtDistrict.setText("");
		txtDistrict.setEnabled(active);
		txtCity.setText("");
		txtCity.setEnabled(active);
		txtPincode.setText("");
		txtPincode.setEnabled(active);

		cmbTitle.setSelectedItem("Select");
		cmbTitle.setEnabled(active);
		txtFirstName.setText("");
		txtFirstName.setEnabled(active);
		txtMiddleName.setText("");
		txtMiddleName.setEnabled(active);
		txtLastName.setText("");
		txtLastName.setEnabled(active);
		male.setEnabled(active);
		female.setEnabled(active);
		txtChiefPromoterItpan.setText("");
		txtChiefPromoterItpan.setEnabled(active);
		txtDOB.setText("");
		txtDOB.setEnabled(active);
		cmbSocialCategory.setSelectedItem("Select");
		cmbSocialCategory.setEnabled(active);
		cmbLegalId.setSelectedItem("Select");
		cmbLegalId.setEnabled(active);
		txtOtherId.setText("");
		txtOtherId.setEnabled(active);
		txtLegalValue.setText("");
		txtLegalValue.setEnabled(active);
		txtOtherPromotersName1.setText("");
		txtOtherPromotersName1.setEnabled(active);
		txtOtherPromotersItpan1.setText("");
		txtOtherPromotersItpan1.setEnabled(active);
		txtOtherPromotersDob1.setText("");
		txtOtherPromotersDob1.setEnabled(active);
		txtOtherPromotersName2.setText("");
		txtOtherPromotersName2.setEnabled(active);
		txtOtherPromotersItpan2.setText("");
		txtOtherPromotersItpan2.setEnabled(active);
		txtOtherPromotersDob2.setText("");
		txtOtherPromotersDob2.setEnabled(active);
		txtOtherPromotersName3.setText("");
		txtOtherPromotersName3.setEnabled(active);
		txtOtherPromotersName3.setText("");
		txtOtherPromotersItpan3.setEnabled(active);
		txtOtherPromotersDob3.setText("");
		txtOtherPromotersDob3.setEnabled(active);

		validate();
	}

	private void enableLegalSuitDetails(boolean active)
	{
		int i = 0;
		for (i=0;i<noOfActions;i++)
		{
			cmbActionType[i].setSelectedIndex(0);
			if (!active)
			{
				txtDetails[i].setText("");
				txtDate[i].setText("");
				txtAttachment[i].setText("");
			}
			cmbActionType[i].setEnabled(active);
			txtDetails[i].setEnabled(active);
			txtDate[i].setEnabled(active);
			txtAttachment[i].setEnabled(active);
			attachBrowse[i].setEnabled(active);
		}
		if (cmbForumNames != null)
		{
			cmbForumNames.setEnabled(active);
		}
		if (txtForumName != null)
		{
			txtForumName.setEnabled(active);
		}
		if (txtSuitCaseRegNo != null)
		{
			txtSuitCaseRegNo.setEnabled(active);
		}
		if (txtLegalProcDate != null)
		{
			txtLegalProcDate.setEnabled(active);
		}
		if (txtForumNameLoc != null)
		{
			txtForumNameLoc.setEnabled(active);
		}
		if (txtForumLocation != null)
		{
			txtForumLocation.setEnabled(active);
		}
		if (txtAmtClaimed != null)
		{
			txtAmtClaimed.setEnabled(active);
		}
		if (txtCurrentStatus != null)
		{
			txtCurrentStatus.setEnabled(active);
		}
		if (recoveryProcConcludedYes != null)
		{
			recoveryProcConcludedYes.setEnabled(active);
		}
		if (recoveryProcConcludedNo != null)
		{
			recoveryProcConcludedNo.setEnabled(active);
		}
		if (txtEffortConclusionDate != null)
		{
			txtEffortConclusionDate.setEnabled(active);
		}
/*		if (txtMliComment != null)
		{
			txtMliComment.setText("");
			txtMliComment.setEnabled(active);
		}
		if (txtFinancialAssistanceDetails != null)
		{
			txtFinancialAssistanceDetails.setText("");
			txtFinancialAssistanceDetails.setEnabled(active);
		}
		if (creditSupportYes != null)
		{
			creditSupportYes.setEnabled(active);
		}
		if (creditSupportNo != null)
		{
			creditSupportNo.setEnabled(active);
		}
		if (txtBankFacilityDetails != null)
		{
			txtBankFacilityDetails.setText("");
			txtBankFacilityDetails.setEnabled(active);
		}
		if (watchListYes != null)
		{
			watchListYes.setEnabled(active);
		}
		if (watchListNo != null)
		{
			watchListNo.setEnabled(active);
		}
		if (txtRemarks != null)
		{
			txtRemarks.setText("");
			txtRemarks.setEnabled(active);
		}*/
		if (!active)
		{
			cmbForumNames.setSelectedIndex(0);
			txtForumName.setText("");
			txtSuitCaseRegNo.setText("");
			txtLegalProcDate.setText("");
			txtForumNameLoc.setText("");
			txtForumLocation.setText("");
			txtAmtClaimed.setText("");
			txtCurrentStatus.setText("");
			txtEffortConclusionDate.setText("");
		}
	}
	
	private boolean validateBeforeDisplayList(String objType, String flag) throws ThinClientException
	{
		String fileName="";
			 if (hardDisk.isSelected())
			 {
				 fileName=txtFilePath.getText().trim();
				 if (fileName.equals(""))
				 {
					 String actionCommand = ok.getActionCommand();
					 JOptionPane.showMessageDialog(panel, "Please Select a File from Hard Disk", "Warning", JOptionPane.INFORMATION_MESSAGE);
//					 panel.removeAll();
//					 displayVerifyPanel(objType, flag);
//					 ok.setActionCommand(actionCommand);
//					 validate();
					 return false;
				 }
				 else if (objType.equals("APP"))
				 {
					 if ((flag.equals("MOD") || (flag.equals("VER"))) && (! fileName.endsWith(".NEW") || fileName.indexOf("APP") == -1))
					 {
//						 throw new ThinClientException("Not a Valid Application File");
						JOptionPane.showMessageDialog(panel, "Not a Valid Application File", "Warning", JOptionPane.ERROR_MESSAGE);
						return false;
					 }
				 }
				 else if (objType.equals("PER"))
				 {
					 if (flag.equals("NEW") && (! fileName.endsWith(".EXP") || fileName.indexOf("PER") == -1))
					 {
//						 throw new ThinClientException("Not a Valid Periodic Info Export File");
						JOptionPane.showMessageDialog(panel, "Not a Valid Periodic Info Export File", "Warning", JOptionPane.ERROR_MESSAGE);
						return false;
					 }
					 if ((flag.equals("MOD") || (flag.equals("VER"))) && (! fileName.endsWith(".NEW") || fileName.indexOf("PER") == -1))
					 {
//						 throw new ThinClientException("Not a Valid Periodic Info File");
						JOptionPane.showMessageDialog(panel, "Not a Valid Periodic Info Export File", "Warning", JOptionPane.ERROR_MESSAGE);
						return false;
					 }
					 if (flag.equalsIgnoreCase("NEW") && txtBrnName.getText().trim().equals(""))
					 {
						String actionCommand = ok.getActionCommand();
						JOptionPane.showMessageDialog(panel, "Please Enter Branch Name", "Warning", JOptionPane.INFORMATION_MESSAGE);
//						panel.removeAll();
//						displayVerifyPanel(objType, flag);
//						ok.setActionCommand(actionCommand);
//						validate();
						return false;
					 }
				 }
				 else if (objType.equals("CLAIM1") || objType.equals("CLAIM2"))
				 {
					 if (flag.equals("NEW") && (! fileName.endsWith(".EXP") || fileName.indexOf("CLAIM") == -1))
					 {
//						 throw new ThinClientException("Not a Valid Claims Export File");
						JOptionPane.showMessageDialog(panel, "Not a Valid Claims Export File", "Warning", JOptionPane.ERROR_MESSAGE);
						return false;
					 }
					 if ((flag.equals("MOD") || (flag.equals("VER"))) && (! fileName.endsWith(".NEW") || fileName.indexOf("CLAIM") == -1))
					 {
//						 throw new ThinClientException("Not a Valid Claims File");
						JOptionPane.showMessageDialog(panel, "Not a Valid Claims Export File", "Warning", JOptionPane.ERROR_MESSAGE);
						return false;
					 }
					if (flag.equalsIgnoreCase("NEW") && txtBrnName.getText().trim().equals(""))
					{
					   String actionCommand = ok.getActionCommand();
					   JOptionPane.showMessageDialog(panel, "Please Enter Branch Name", "Warning", JOptionPane.INFORMATION_MESSAGE);
//					   panel.removeAll();
//					   displayVerifyPanel(objType, flag);
//					   ok.setActionCommand(actionCommand);
//					   validate();
					   return false;
					}
				 }
			 }
			 else if (noFile.isSelected())
			 {
/*				 if (objType.equals("PER"))
				 {
					displayPeriodicInputPanel("","","NEW");
//					 displayPeriodicInfo();
				 }
				 else if (objType.equals("CLAIM1"))
				 {
					displayClaimsInputPanel(true);
//					 displayClaimsFIPanel("", "", flag);
				 }
				 else if (objType.equals("CLAIM2"))
				 {
					displayClaimsInputPanel(false);
//					 displayClaimsSIPanel("", "", flag);
				 }*/
				 JOptionPane.showMessageDialog(new ThinClient(), "Please download the basic data file", "Message", JOptionPane.WARNING_MESSAGE);
				 String actionCommand = ok.getActionCommand();
//				 panel.removeAll();
//				 displayVerifyPanel(objType, flag);
//				 ok.setActionCommand(actionCommand);
//				 validate();
				 return false;
			 }
			 else if (exportFile.isSelected() || existingFile.isSelected())
			 {
				 fileName=txtFilePath.getText().trim();
				 if (fileName.equals(""))
				 {
					 String actionCommand = ok.getActionCommand();
					 JOptionPane.showMessageDialog(panel, "Please Select a File from Hard Disk", "Warning", JOptionPane.INFORMATION_MESSAGE);
//					 panel.removeAll();
//					 displayVerifyPanel(objType, flag);
//					 ok.setActionCommand(actionCommand);
//					 validate();
					 return false;
				 }
				 else if (flag.equals("REC") && ((! fileName.endsWith(".EXP") || fileName.indexOf("PER") == -1) && (! fileName.endsWith(".NEW") || fileName.indexOf("PER") == -1)))
				 {
//					 throw new ThinClientException("Not a Valid Periodic Info File for Update Recovery Details");
					JOptionPane.showMessageDialog(panel, "Not a Valid Periodic Info File for Update Recovery Details", "Warning", JOptionPane.ERROR_MESSAGE);
					return false;
				 }
				 else if (flag.equals("VRE") && ((! fileName.endsWith(".NEW") || fileName.indexOf("PER") == -1)))
				 {
//					 throw new ThinClientException("Not a Valid Periodic Info File for Verify Updated Recovery Details");
					JOptionPane.showMessageDialog(panel, "Not a Valid Periodic Info File for Update Recovery Details", "Warning", JOptionPane.ERROR_MESSAGE);
					return false;
				 }
			 }
			 else
			 {
				 String actionCommand = ok.getActionCommand();
				 JOptionPane.showMessageDialog(panel, "Please Select a File Location", "Warning", JOptionPane.INFORMATION_MESSAGE);
//				 panel.removeAll();
//				 displayVerifyPanel(objType, flag);
//				 ok.setActionCommand(actionCommand);
//				 validate();
				 return false;
			 }
			 return true;
	}

	/**
	* This method validates the common details for an application.
	*/
	private String validateCommonAppDetails()
	{
		String error = "";
		String txtValue = "";
		String txtValue1 = "";
		String txtValue2 = "";

		txtValue = txtBankRefNo.getText().trim();
		if (txtValue.equals(""))
		{
			error = error + "\n" + "Enter Bank Reference Number";
		}

		txtValue = txtBankBranchName.getText().trim();
		if (txtValue.equals(""))
		{
			error = error + "\n" + "Enter Bank Branch Name";
		}

		if (cgpan.isSelected())
		{
			if (txtSsiValue.getText().trim().equals(""))
			{
				error = error + "\n" + "Enter CGPAN";
			}
			else if (txtSsiValue.getText().trim().length() != 13)
			{
				error = error + "\n" + "CGPAN should be 13 Characters";
			}
		}

		if (cgbid.isSelected())
		{
			if (txtSsiValue.getText().trim().equals(""))
			{
				error = error + "\n" + "Enter CGPAN";
			}
			else if (txtSsiValue.getText().trim().length() != 9)
			{
				error = error + "\n" + "CGBID should be 9 Characters";
			}
		}

		if (none.isSelected())
		{
			if (npaYes.isSelected())
			{
				error = error + "\n" + "Application Cannot be Submitted if NPA is Yes.";
			}
			
			txtValue = cmbConstitution.getSelectedItem().toString();
			if (txtValue.equals("Select"))
			{
				error = error + "\n" + "Enter Constitution";
			}
			else if ((txtValue.equals("Others")) && ((txtConstitution.getText().trim()).equals("")))
			{
				error = error + "\n" + "Enter Constitution Others";
			}

			txtValue = cmbUnitNames.getSelectedItem().toString();
			if (txtValue.equals("Select"))
			{
				error = error + "\n" + "Select Unit Type";
			}

			txtValue = txtUnitName.getText().trim();
			if (txtValue.equals(""))
			{
				error = error + "\n" + "Enter Borrower Name";
			}

/*			txtValue = txtSsiRegNo.getText().trim();
			if (txtValue.equals(""))
			{
				error = error + "\n" + "Enter SSI Registration Number";
			}*/

/*			txtValue = txtFirmItpan.getText().trim();
			if (txtValue.equals(""))
			{
				error = error + "\n" + "Enter ITPAN";
			}*/

			txtValue = txtActivity.getText().trim();
			if (txtValue.equals(""))
			{
				error = error + "\n" + "Enter Type of Activity";
			}

			txtValue = txtAddress.getText().trim();
			if (txtValue.equals(""))
			{
				error = error + "\n" + "Enter Address";
			}

			txtValue = cmbState.getSelectedItem().toString();
			if (txtValue.equals("Select"))
			{
				error = error + "\n" + "Select State";
			}

			txtValue = cmbDistrict.getSelectedItem().toString();
			if (txtValue.equals("Select"))
			{
				error = error + "\n" + "Select District";
			}
			else if ((txtValue.equals("Others"))&& ((txtDistrict.getText().trim()).equals("")))
			{
				error = error + "\n" + "Enter Other District";
			}
			
			txtValue = txtCity.getText().trim();
			if (txtValue.equals(""))
			{
				error = error + "\n" + "Enter City";
			}

			txtValue = txtPincode.getText().trim();
			if (txtValue.equals(""))
			{
				error = error + "\n" + "Enter Pincode";
			}
			else if (txtValue.length() < 6)
			{
				error = error + "\n" + "Pincode Should not be Less than 6 characters";
			}

			txtValue = cmbTitle.getSelectedItem().toString();
			if (txtValue.equals("Select"))
			{
				error = error + "\n" + "Select Promoter Title";
			}

			txtValue = txtFirstName.getText().trim();
			if (txtValue.equals(""))
			{
				error = error + "\n" + "Enter Promoter First Name";
			}

			txtValue = txtLastName.getText().trim();
			if (txtValue.equals(""))
			{
				error = error + "\n" + "Enter Promoter Last Name";
			}

			txtValue = cmbLegalId.getSelectedItem().toString();
			if (txtValue.equals("Select"))
			{
				txtValue = "";
			}
			else if ((txtValue.equals("Others")) && ((txtOtherId.getText().trim()).equals("")))
			{
				error = error + "\n" + "Enter Other Legal Id";
			}

			if (!txtValue.equals("") && txtLegalValue.getText().trim().equals(""))
			{
				error = error + "\n" + "Enter Legal Id Value";
			}

			txtValue1 = txtGuarantorName1.getText().trim();
			txtValue2 = txtGuarantorNetworth1.getText().trim();
			if (!txtValue2.equals("") && Double.parseDouble(txtValue2) == 0.0)
			{
				txtValue2 = "";
			}
			if ((txtValue1.equals("")) && (!(txtValue2.equals(""))))
			{
				error = error + "\n" + "Enter Gurantor Name 1";
			}
			if ((txtValue2.equals("")) && (!(txtValue1.equals(""))))
			{
				error = error + "\n" + "Enter Gurantor Networth 1";
			}

			txtValue1 = txtGuarantorName2.getText().trim();
			txtValue2 = txtGuarantorNetworth2.getText().trim();
			if (!txtValue2.equals("") && Double.parseDouble(txtValue2) == 0.0)
			{
				txtValue2 = "";
			}
			if ((txtValue1.equals("")) && (!(txtValue2.equals(""))))
			{
				error = error + "\n" + "Enter Gurantor Name 2";
			}
			if ((txtValue2.equals("")) && (!(txtValue1.equals(""))))
			{
				error = error + "\n" + "Enter Gurantor Networth 2";
			}

			txtValue1 = txtGuarantorName3.getText().trim();
			txtValue2 = txtGuarantorNetworth3.getText().trim();
			if (!txtValue2.equals("") && Double.parseDouble(txtValue2) == 0.0)
			{
				txtValue2 = "";
			}
			if ((txtValue1.equals("")) && (!(txtValue2.equals(""))))
			{
				error = error + "\n" + "Enter Gurantor Name 3";
			}
			if ((txtValue2.equals("")) && (!(txtValue1.equals(""))))
			{
				error = error + "\n" + "Enter Gurantor Networth 3";
			}

			txtValue1 = txtGuarantorName4.getText().trim();
			txtValue2 = txtGuarantorNetworth4.getText().trim();
			if (!txtValue2.equals("") && Double.parseDouble(txtValue2) == 0.0)
			{
				txtValue2 = "";
			}
			if ((txtValue1.equals("")) && (!(txtValue2.equals(""))))
			{
				error = error + "\n" + "Enter Gurantor Name 4";
			}
			if ((txtValue2.equals("")) && (!(txtValue1.equals(""))))
			{
				error = error + "\n" + "Enter Gurantor Networth 4";
			}
			
/*			txtValue1 = txtFirmItpan.getText();
			txtValue2 = txtChiefPromoterItpan.getText();
			if ((!txtValue1.equals("") && !txtValue2.equals(""))&& (txtValue1.equalsIgnoreCase(txtValue2)))
			{
				error = error + "\n" + "Unit ITPAN and Chief Promoter ITPAN cannot be same";
			}*/

			txtValue1 = txtOtherPromotersItpan1.getText().trim();
			txtValue2 = txtOtherPromotersDob1.getText().trim();
			if (txtValue2.endsWith("/"))
			{
				txtValue2="";
			}
//			System.out.println("name 1 -" + txtOtherPromotersName1.getText().trim() + "-");
//			System.out.println("itpan 1 -" + txtValue1 + "-");
//			System.out.println("dob 1 -" + txtValue2 + "-");
			if ((! txtValue1.equals("") || ! txtValue2.equals("")) && txtOtherPromotersName1.getText().trim().equals(""))
			{
//				System.out.println("Enter Other Promoters Name 1");
				error = error + "\n" + "Enter Other Promoters Name 1";
			}
			
			txtValue1 = txtOtherPromotersItpan1.getText().trim();
			txtValue2 = txtFirmItpan.getText();
			if ((!txtValue1.equals("") && !txtValue2.equals(""))&& (txtValue1.equalsIgnoreCase(txtValue2)))
			{
				error = error + "\n" + "Unit ITPAN and Other Promoters ITPAN 1 cannot be same";
			}

			txtValue1 = txtOtherPromotersItpan1.getText().trim();
			txtValue2 = txtChiefPromoterItpan.getText();
			if ((!txtValue1.equals("") && !txtValue2.equals(""))&& (txtValue1.equalsIgnoreCase(txtValue2)))
			{
				error = error + "\n" + "Chief Promoter ITPAN and Other Promoters ITPAN 1 cannot be same";
			}

			txtValue1 = txtOtherPromotersItpan2.getText().trim();
			txtValue2 = txtOtherPromotersDob2.getText().trim();
			if (txtValue2.endsWith("/"))
			{
				txtValue2="";
			}
			if ((! txtValue1.equals("") || ! txtValue2.equals("")) && txtOtherPromotersName2.getText().trim().equals(""))
			{
//				System.out.println("Enter Other Promoters Name 2");
				error = error + "\n" + "Enter Other Promoters Name 2";
			}

			txtValue1 = txtOtherPromotersItpan2.getText().trim();
			txtValue2 = txtFirmItpan.getText();
			if ((!txtValue1.equals("") && !txtValue2.equals(""))&& (txtValue1.equalsIgnoreCase(txtValue2)))
			{
				error = error + "\n" + "Unit ITPAN and Other Promoters ITPAN 2 cannot be same";
			}

			txtValue1 = txtOtherPromotersItpan2.getText().trim();
			txtValue2 = txtChiefPromoterItpan.getText();
			if ((!txtValue1.equals("") && !txtValue2.equals(""))&& (txtValue1.equalsIgnoreCase(txtValue2)))
			{
				error = error + "\n" + "Chief Promoter ITPAN and Other Promoters ITPAN 2 cannot be same";
			}

			txtValue1 = txtOtherPromotersItpan3.getText().trim();
			txtValue2 = txtOtherPromotersDob3.getText().trim();
			if (txtValue2.endsWith("/"))
			{
				txtValue2="";
			}
			if ((! txtValue1.equals("") || ! txtValue2.equals("")) && txtOtherPromotersName3.getText().trim().equals(""))
			{
//				System.out.println("Enter Other Promoters Name 3");
				error = error + "\n" + "Enter Other Promoters Name 3";
			}

			txtValue1 = txtOtherPromotersItpan3.getText().trim();
			txtValue2 = txtFirmItpan.getText();
			if ((!txtValue1.equals("") && !txtValue2.equals(""))&& (txtValue1.equalsIgnoreCase(txtValue2)))
			{
				error = error + "\n" + "Unit ITPAN and Other Promoters ITPAN 3 cannot be same";
			}

			txtValue1 = txtOtherPromotersItpan3.getText().trim();
			txtValue2 = txtChiefPromoterItpan.getText();
			if ((!txtValue1.equals("") && !txtValue2.equals(""))&& (txtValue1.equalsIgnoreCase(txtValue2)))
			{
				error = error + "\n" + "Chief Promoter ITPAN and Other Promoters ITPAN 3 cannot be same";
			}

			txtValue1 = txtOtherPromotersItpan1.getText().trim();
			txtValue2 = txtOtherPromotersItpan2.getText();
			if ((!txtValue1.equals("") && !txtValue2.equals(""))&& (txtValue1.equalsIgnoreCase(txtValue2)))
			{
				error = error + "\n" + "Other Promoters ITPAN 1 and Other Promoters ITPAN 2 cannot be same";
			}

			txtValue1 = txtOtherPromotersItpan1.getText().trim();
			txtValue2 = txtOtherPromotersItpan3.getText();
			if ((!txtValue1.equals("") && !txtValue2.equals(""))&& (txtValue1.equalsIgnoreCase(txtValue2)))
			{
				error = error + "\n" + "Other Promoters ITPAN 1 and Other Promoters ITPAN 3 cannot be same";
			}
			
			txtValue1 = txtOtherPromotersItpan2.getText().trim();
			txtValue2 = txtOtherPromotersItpan3.getText();
			if ((!txtValue1.equals("") && !txtValue2.equals(""))&& (txtValue1.equalsIgnoreCase(txtValue2)))
			{
				error = error + "\n" + "Other Promoters ITPAN 2 and Other Promoters ITPAN 3 cannot be same";
			}

			txtValue1 = txtLandParticulars.getText().trim();
			txtValue2 = txtLandValue.getText().trim();
			if (!txtValue2.equals("") && Double.parseDouble(txtValue2) == 0.0)
			{
				txtValue2 = "";
			}
			if ((txtValue1.equals("")) && (!(txtValue2.equals(""))))
			{
				error = error + "\n" + "Enter Land Particulars";
			}
			if ((txtValue2.equals("")) && (!(txtValue1.equals(""))))
			{
				error = error + "\n" + "Enter Land Value";
			}

			txtValue1 = txtBuildingParticulars.getText().trim();
			txtValue2 = txtBuildingValue.getText().trim();
			if (!txtValue2.equals("") && Double.parseDouble(txtValue2) == 0.0)
			{
				txtValue2 = "";
			}
			if ((txtValue1.equals("")) && (!(txtValue2.equals(""))))
			{
				error = error + "\n" + "Enter Building Particulars";
			}
			if ((txtValue2.equals("")) && (!(txtValue1.equals(""))))
			{
				error = error + "\n" + "Enter Building Value";
			}

			txtValue1 = txtMachineParticulars.getText().trim();
			txtValue2 = txtMachineValue.getText().trim();
			if (!txtValue2.equals("") && Double.parseDouble(txtValue2) == 0.0)
			{
				txtValue2 = "";
			}
			if ((txtValue1.equals("")) && (!(txtValue2.equals(""))))
			{
				error = error + "\n" + "Enter Machine Particulars";
			}
			if ((txtValue2.equals("")) && (!(txtValue1.equals(""))))
			{
				error = error + "\n" + "Enter Machine Value";
			}

			txtValue1 = txtOtherAssetsParticulars.getText().trim();
			txtValue2 = txtOtherAssetsValue.getText().trim();
			if (!txtValue2.equals("") && Double.parseDouble(txtValue2) == 0.0)
			{
				txtValue2 = "";
			}
			if ((txtValue1.equals("")) && (!(txtValue2.equals(""))))
			{
				error = error + "\n" + "Enter Other Fixed / Movable Assets Particulars";
			}
			if ((txtValue2.equals("")) && (!(txtValue1.equals(""))))
			{
				error = error + "\n" + "Enter Other Fixed / Movable Assets Value";
			}

			txtValue1 = txtCurrentAssetsParticulars.getText().trim();
			txtValue2 = txtCurrentAssetsValue.getText().trim();
			if (!txtValue2.equals("") && Double.parseDouble(txtValue2) == 0.0)
			{
				txtValue2 = "";
			}
			if ((txtValue1.equals("")) && (!(txtValue2.equals(""))))
			{
				error = error + "\n" + "Enter Current Assets Particulars";
			}
			if ((txtValue2.equals("")) && (!(txtValue1.equals(""))))
			{
				error = error + "\n" + "Enter Current Assets Value";
			}

			txtValue1 = txtOthersParticulars.getText().trim();
			txtValue2 = txtOthersValue.getText().trim();
			if (!txtValue2.equals("") && Double.parseDouble(txtValue2) == 0.0)
			{
				txtValue2 = "";
			}
			if ((txtValue1.equals("")) && (!(txtValue2.equals(""))))
			{
				error = error + "\n" + "Enter Others Particulars";
			}
			if ((txtValue2.equals("")) && (!(txtValue1.equals(""))))
			{
				error = error + "\n" + "Enter Others Value";
			}
		}

		return error;
	}

	/**
	* This method validates the project outlay details.
	*/
	private String validateProjectOutlayDetails(String flag)
	{
		String error = "";
		String termCredit = txtTermCredit.getText().trim();
		if (!termCredit.equals("") && Double.parseDouble(termCredit) == 0.0)
		{
			termCredit = "";
		}
		String wcFundBased = txtWCFundBased.getText().trim();
		if (!wcFundBased.equals("") && Double.parseDouble(wcFundBased) == 0.0)
		{
			wcFundBased = "";
		}
		String wcNFundBased = txtWCNonFundBased.getText().trim();
		if (!wcNFundBased.equals("") && Double.parseDouble(wcNFundBased) == 0.0)
		{
			wcNFundBased = "";
		}		
		String tcPromoterCont = txtTCPromotersContribution.getText().trim();
		if (!tcPromoterCont.equals("") && Double.parseDouble(tcPromoterCont) == 0.0)
		{
			tcPromoterCont = "";
		}
		String wcPromoterCont = txtWCPromotersContribution.getText().trim();
		if (!wcPromoterCont.equals("") && Double.parseDouble(wcPromoterCont) == 0.0)
		{
			wcPromoterCont = "";
		}
		String tcSubsidy = txtTCSupport.getText().trim();
		if (!tcSubsidy.equals("") && Double.parseDouble(tcSubsidy) == 0.0)
		{
			tcSubsidy = "";
		}
		String wcSubsidy = txtWCSupport.getText().trim();
		if (!wcSubsidy.equals("") && Double.parseDouble(wcSubsidy) == 0.0)
		{
			wcSubsidy = "";
		}
		String tcOthers = txtTCOthers.getText().trim();
		if (!tcOthers.equals("") && Double.parseDouble(tcOthers) == 0.0)
		{
			tcOthers = "";
		}
		String wcOthers = txtWCOthers.getText().trim();
		if (!wcOthers.equals("") && Double.parseDouble(wcOthers) == 0.0)
		{
			wcOthers = "";
		}

		if (flag.equals("TC") || flag.equals("CC") || flag.equals("BO"))
		{
			if (termCredit.equals(""))
			{
				error = error + "\n" + "Enter Term Credit Sanctioned";
			}
			if (((!(wcPromoterCont.equals("")))
				|| (!(wcSubsidy.equals("")))
				|| (!(wcOthers.equals(""))))
				&& (wcFundBased.equals("") && wcNFundBased.equals("")))
			{
				error = error + "\n" + "Enter Either WC Fund Based or Non Fund Based Sanctioned Amount";
			}
		}
		if (flag.equals("WC") || flag.equals("CC") || flag.equals("BO"))
		{
			if (wcFundBased.equals("") && wcNFundBased.equals(""))
			{
				error = error + "\n" + "Enter Either WC Fund Based or Non Fund Based Sanctioned Amount";
			}
			if (((!(tcPromoterCont.equals("")))
				|| (!(tcSubsidy.equals("")))
				|| (!(tcOthers.equals(""))))
				&& (termCredit.equals("")))
			{
				error = error + "\n" + "Enter Term Credit Sanctioned Amount";
			}
		}
		
		double tcamt = 0;
		if (!termCredit.equals(""))
		{
			tcamt = Double.parseDouble(termCredit);
		}
		double wcamt = 0;
		if (!wcFundBased.equals(""))
		{
			wcamt = Double.parseDouble(wcFundBased);
		}
		double wcNFamt = 0;
		if (!wcNFundBased.equals(""))
		{
			wcNFamt = Double.parseDouble(wcNFundBased);
		}		
		
/*		double sancAmt = tcamt + wcamt + wcNFamt;
		
		if (sancAmt > 100000 && txtChiefPromoterItpan.getText().trim().equals(""))
		{
			error = error + "\n" + "Enter Chief Promoter ITPAN since sanctioned amount is greater than 1 lakh";
		}*/
		

		return error;
	}

	/**
	* This method validates the term credit details.
	*/
	private String validateTermCreditDetails()
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String error = "";
		String amtSanc = txtTermCredit.getText().trim();
		if (! amtSanc.equals("") && Double.parseDouble(amtSanc) == 0)
		{
			amtSanc = "";
		}
		String dtOfSanc = txtDateOfSanction.getText().trim();
		if (dtOfSanc.endsWith("/"))
		{
			dtOfSanc = "";
		}
		String credit = txtCreditToBeGuaranteed.getText().trim();
		if (! credit.equals("") && Double.parseDouble(credit) == 0)
		{
			credit = "";
		}
		String amtDisbursed = txtAmtDisbursed.getText().trim();
		if (! amtDisbursed.equals("") && Double.parseDouble(amtDisbursed) == 0)
		{
			amtDisbursed = "";
		}
		String dtOfFirstDis = txtDateOfFirstDisbursement.getText().trim();
		if (dtOfFirstDis.endsWith("/"))
		{
			dtOfFirstDis = "";
		}
		String dtOfFinalDis = txtDateOfLastDisbursement.getText().trim();
		if (dtOfFinalDis.endsWith("/"))
		{
			dtOfFinalDis = "";
		}
		String firstInstDueDate = txtFirstInstDueDate.getText().trim();
		if (firstInstDueDate.endsWith("/"))
		{
			firstInstDueDate = "";
		}
		String osAsOn = txtOsAsOn.getText().trim();
		if (osAsOn.endsWith("/"))
		{
			osAsOn = "";
		}
		
		String tenure = txtTenure.getText().trim();
		if (! tenure.equals("") && Double.parseDouble(tenure) == 0)
		{
			tenure = "";
		}
		String interest = txtTcInterestRate.getText().trim();
		if (! interest.equals("") && Double.parseDouble(interest) == 0)
		{
			interest = "";
		}
		String plr = txtPlr.getText().trim();
		if (! plr.equals("") && Double.parseDouble(plr) == 0)
		{
			plr = "";
		}
		String plrType = txtPlrType.getText().trim();
		String noOfInst = txtNoOfInstallments.getText().trim();
		if (! noOfInst.equals("") && Double.parseDouble(noOfInst) == 0)
		{
			noOfInst = "";
		}
		String osAmt = txtTcOsAmt.getText().trim();
		if (! osAmt.equals("") && Double.parseDouble(osAmt) == 0)
		{
			osAmt = "";
		}

/*		if (amtSanc.equals(""))
		{
			error = error + "\n" + "Enter Amount Sanctioned";
		}*/

		if (dtOfSanc.equals(""))
		{
			error = error + "\n" + "Enter Amount Sanctioned Date";
		}
		else
		{
			Date sancDate = dateFormat.parse(dtOfSanc, new ParsePosition(0));
			Date currDate = dateFormat.parse(dateFormat.format(new java.util.Date()), new ParsePosition(0));
			if ((currDate.compareTo(sancDate))<0)
			{
				error = error + "\n" + "Date of Sanction should not be later than Current Date";
			}
		}

		if (credit.equals("") || (!credit.equals("") && Double.parseDouble(credit)==0.0))
		{
			error = error + "\n" + "Enter Credit to be Guaranteed";
		}

		if (! credit.equals("") && ! amtSanc.equals(""))
		{
			if (Double.parseDouble(credit) > Double.parseDouble(amtSanc))
			{
				error = error + "\n" + "Credit to be Guaranteed cannot be Greater than Amount Sancitoned";
			}
		}
		
		if (! amtDisbursed.equals("") && ! amtSanc.equals(""))
		{
			if (Double.parseDouble(amtDisbursed) > Double.parseDouble(amtSanc))
			{
				error = error + "\n" + "Amount Disbursed cannot be Greater than Amount Sancitoned";
			}
		}

		if ((amtDisbursed.equals(""))
			&& (!(dtOfFirstDis.equals("")) || !(dtOfFinalDis.equals(""))))
		{
			error = error + "\n" + "Enter Amount Disbursed";
		}
		
		/*if (!(amtDisbursed.equals(""))
			&& (!(dtOfFirstDis.equals("")) && !dtOfFinalDis.equals("")))
		{
			error = error + "\n" + "Both Date of First Disbursement and Date of Final Disbursement cannot be entered";
		}
		else if ((!(amtDisbursed.equals(""))
		&& ((dtOfFirstDis.equals("")) && dtOfFinalDis.equals(""))))
		{
			error = error + "\n" + "Either Date of First Disbursement or Date of Final Disbursement should be entered";
		}*/
		if ((dtOfFirstDis.equals(""))
			&& (!(amtDisbursed.equals(""))))
		{
			error = error + "\n" + "Enter Date of First Disbursement";
		}
		else if((!dtOfFirstDis.equals(""))
		&& (!(amtDisbursed.equals(""))))
		{
			Date firstDis = dateFormat.parse(dtOfFirstDis, new ParsePosition(0));
			Date currDate = dateFormat.parse(dateFormat.format(new java.util.Date()), new ParsePosition(0));
			if ((currDate.compareTo(firstDis))<0)
			{
				error = error + "\n" + "Date of First Disbursement should not be later than Current Date";
			}
		}


		if ((!(dtOfFirstDis.equals(""))) && (!(dtOfSanc.equals(""))))
		{
			Date firstDisDate = dateFormat.parse(dtOfFirstDis, new ParsePosition(0));
			Date sancDate = dateFormat.parse(dtOfSanc, new ParsePosition(0));
			if ((firstDisDate.compareTo(sancDate))<0)
			{
				error = error + "\n" + "First Disbursement Date should be later than Date of Sanction";
			}
		}

/*		if ((!(dtOfFinalDis.equals("")))
			&& ((dtOfFirstDis.equals("")) || (amtDisbursed.equals(""))))
		{
			error = error + "\n" + "Enter Both Amount Disbursed and Date of First Disbursement";
		}*/

		if ((!(dtOfFinalDis.equals(""))) && (!(dtOfFirstDis.equals(""))))
		{
			Date firstDisDate = dateFormat.parse(dtOfFirstDis, new ParsePosition(0));
			Date finalDisDate = dateFormat.parse(dtOfFinalDis, new ParsePosition(0));
			if ((finalDisDate.compareTo(firstDisDate))<0)
			{
				error = error + "\n" + "Final Disbursement Date should be later than First Disbursement Date";
			}
		}
		if ((!(dtOfFinalDis.equals(""))) && (!(dtOfSanc.equals(""))))
		{
			Date finalDisDate = dateFormat.parse(dtOfFinalDis, new ParsePosition(0));
			Date sancDate = dateFormat.parse(dtOfSanc, new ParsePosition(0));
			if ((finalDisDate.compareTo(sancDate))<0)
			{
				error = error + "\n" + "Final Disbursement Date should be later than Date of Sanction";
			}
		}

		if (osAmt.equals("") && (!(amtDisbursed.equals("") || Double.parseDouble(amtDisbursed) == 0.0)))
		{
			error = error + "\n" + "Enter Term Credit Outstanding Amount";
		}
		
		if (! osAmt.equals("") && ! amtSanc.equals(""))
		{
			if (Double.parseDouble(osAmt) > Double.parseDouble(amtSanc))
			{
				error = error + "\n" + "Term Credit Oustanding Amount cannot be Greater than Amount Sanctioned";
			}
		}

		if (! osAmt.equals("") && ! amtDisbursed.equals(""))
		{
			if (Double.parseDouble(osAmt) > Double.parseDouble(amtDisbursed))
			{
				error = error + "\n" + "Term Credit Oustanding Amount cannot be Greater than Amount Disbursed";
			}
		}

		if (!osAmt.equals("") && osAsOn.equals(""))
		{
			error = error + "\n" + "Enter Term Credit Outstanding As On Date";
		}

		if (! osAmt.equals("") && (amtDisbursed.equals("") || Double.parseDouble(amtDisbursed) == 0.0))
		{
			error = error + "\n" + "Term Credit Outstanding Amount Cannot be Entered When Amount is Not Disbursed";
		}

		if (! osAsOn.equals("") && (amtDisbursed.equals("") || Double.parseDouble(amtDisbursed) == 0.0))
		{
			error = error + "\n" + "Term Credit Outstanding As on Date Cannot be Entered When Amount is Not Disbursed";
		}

		if ((!(osAsOn.equals(""))) && (!(dtOfSanc.equals(""))))
		{
			Date osAsOnDate = dateFormat.parse(osAsOn, new ParsePosition(0));
			Date sancDate = dateFormat.parse(dtOfSanc, new ParsePosition(0));
			if ((osAsOnDate.compareTo(sancDate))<0)
			{
				error = error + "\n" + "Outstanding As On Date should be later than Date of Sanction";
			}
		}

		if ((!(osAsOn.equals(""))) && (!(dtOfFirstDis.equals(""))))
		{
			Date osAsOnDate = dateFormat.parse(osAsOn, new ParsePosition(0));
			Date firstDisDate = dateFormat.parse(dtOfFirstDis, new ParsePosition(0));
			if ((osAsOnDate.compareTo(firstDisDate))<0)
			{
				error = error + "\n" + "Outstanding As On Date should be later than Date of First Disbursement Date";
			}
		}
		
		if (!osAmt.equals("") && !osAsOn.equals(""))
		{
			Date osDate = dateFormat.parse(osAsOn, new ParsePosition(0));
			Date currDate = dateFormat.parse(dateFormat.format(new java.util.Date()), new ParsePosition(0));
			if ((currDate.compareTo(osDate))<0)
			{
				error = error + "\n" + "Term Credit Outstanding Date should not be later than Current Date";
			}
		}

		if (interest.equals("") || Double.parseDouble(interest) == 0.0)
		{
			error = error + "\n" + "Enter Interest in Term Credit Details";
		}

		if (plr.equals("") || Double.parseDouble(plr) == 0.0)
		{
			error = error + "\n" + "Enter PLR in Term Credit Details";
		}

		if (plrType.equals(""))
		{
			error = error + "\n" + "Enter PLR Type in Term Credit Details";
		}

		if (tenure.equals(""))
		{
			error = error + "\n" + "Enter Tenure in Term Credit Details";
		}

		String noOfInstallments = txtNoOfInstallments.getText().trim();
		if (noOfInstallments.equals("") || Double.parseDouble(noOfInstallments) == 0)
		{
			error = error + "\n" + "Enter No of Instalment in the Repayment Schedule";
		}

		if (amtDisbursed.equals("") && !firstInstDueDate.equals(""))
		{
			error = error + "\n" + "First Installment Due Date cannot be entered when Amount Disbursed is not entered";
		}
		else if (!amtDisbursed.equals("") && firstInstDueDate.equals(""))
		{
			error = error + "\n" + "Enter First Installment Due Date";
		}
		
		if ((!(firstInstDueDate.equals(""))) && (!(dtOfFirstDis.equals(""))))
		{
			Date dtfirstInstDueDate = dateFormat.parse(firstInstDueDate, new ParsePosition(0));
			Date firstDisDate = dateFormat.parse(dtOfFirstDis, new ParsePosition(0));
			if ((dtfirstInstDueDate.compareTo(firstDisDate))<0)
			{
				error = error + "\n" + "First Instalment Due Date should be later than Date of First Disbursement";
			}
		}

		return error;
	}

	/**
	* This method validates the working capital details.
	*/
	private String validateWorkingCapitalDetails()
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String error = "";
		String intRate = txtWcInterestRate.getText().trim();
		if (!intRate.equals("") && Double.parseDouble(intRate) == 0.0)
		{
			intRate="";
		}
		String fbSancAmt = txtWCFundBased.getText().trim();
		if (!fbSancAmt.equals("") && Double.parseDouble(fbSancAmt) == 0.0)
		{
			fbSancAmt="";
		}
		String nfbSancAmt = txtWCNonFundBased.getText().trim();
		if (!nfbSancAmt.equals("") && Double.parseDouble(nfbSancAmt) == 0.0)
		{
			nfbSancAmt="";
		}
/*		String fbInt = txtFBInterest.getText().trim();
		if (!fbInt.equals("") && Double.parseDouble(fbInt) == 0.0)
		{
			fbInt="";
		}*/
		String fbSancDt = txtFBSanctionedDate.getText().trim();
		if (fbSancDt.endsWith("/"))
		{
			fbSancDt = "";
		}
		String nfbCommission = txtNFBCommission.getText().trim();
		if (!nfbCommission.equals("") && Double.parseDouble(nfbCommission) == 0.0)
		{
			nfbCommission="";
		}		
		String nfbSancDt = txtNFBSanctionedDate.getText().trim();
		if (nfbSancDt.endsWith("/"))
		{
			nfbSancDt = "";
		}
		String fbCredit = txtFBCredit.getText().trim();
		if (!fbCredit.equals("") && Double.parseDouble(fbCredit) == 0.0)
		{
			fbCredit="";
		}
		String nfbCredit = txtNFBCredit.getText().trim();
		if (!nfbCredit.equals("") && Double.parseDouble(nfbCredit) == 0.0)
		{
			nfbCredit="";
		}		
		String plr = txtWcPlr.getText().trim();
		if (!plr.equals("") && Double.parseDouble(plr) == 0.0)
		{
			plr="";
		}
		String plrType = txtWcPlrType.getText().trim();
		String fbOsPrincipal = txtOsFBPrincipal.getText().trim();
		if (!fbOsPrincipal.equals("") && Double.parseDouble(fbOsPrincipal) == 0.0)
		{
			fbOsPrincipal="";
		}
		String fbOsAsOn = txtOsFBAsOn.getText().trim();
		if (fbOsAsOn.endsWith("/"))
		{
			fbOsAsOn = "";
		}
		String nfbOsPrincipal = txtOsNFBPrincipal.getText().trim();
		if (!nfbOsPrincipal.equals("") && Double.parseDouble(nfbOsPrincipal) == 0.0)
		{
			nfbOsPrincipal="";
		}
		String nfbOsInt = txtOsNFBCommission.getText().trim();
		if (!nfbOsInt.equals("") && Double.parseDouble(nfbOsInt) == 0.0)
		{
			nfbOsInt="";
		}
		String nfbOsAsOn = txtOsNFBAsOn.getText().trim();
		if (nfbOsAsOn.endsWith("/"))
		{
			nfbOsAsOn = "";
		}


		if (intRate.equals(""))
		{
			error = error + "\n" + "Enter Working Capital Interest Rate";
		}
/*		if (!fbInt.equals("") && fbSancAmt.equals(""))
		{
			error = error + "\n" + "Fund Based Interest cannot be entered if Fund Based Sanctioned Amount is not entered";
		}*/
		if (!fbSancDt.equals("") && fbSancAmt.equals(""))
		{
			error = error + "\n" + "Fund Based Sanctioned Date cannot be entered if Fund Based Sanctioned Amount is not entered"; 
		}

		if (!nfbCommission.equals("") && nfbSancAmt.equals(""))
		{
			error = error + "\n" + "Non Fund Based Commission cannot be entered if Non Fund Based Sanctioned Amount is not entered";
		}
		if (!nfbSancDt.equals("") && nfbSancAmt.equals(""))
		{
			error = error + "\n" + "Non Fund Based Sanctioned Date cannot be entered if Non Fund Based Sanctioned Amount is not entered";
		}

		if (fbSancDt.equals("") && !fbSancAmt.equals(""))
		{
			error = error + "\n" + "Enter Fund Based Sanctioned Date";
		}
		else if (!fbSancDt.equals(""))
		{
			Date sancDate = dateFormat.parse(fbSancDt, new ParsePosition(0));
			Date currDate = dateFormat.parse(dateFormat.format(new java.util.Date()), new ParsePosition(0));
			if ((currDate.compareTo(sancDate))<0)
			{
				error = error + "\n" + "Fund Based Sanctioned Date should not be later than Current Date";
			}
		}
		
		if ((fbCredit.equals("") || (!fbCredit.equals("") && Double.parseDouble(fbCredit)==0.0)) && !fbSancAmt.equals(""))
		{
			error = error + "\n" + "Enter Fund Based Credit to be Guaranteed";
		}

		if ((!nfbSancAmt.equals(""))
			&& ((nfbCommission.equals("")) || (nfbSancDt.equals(""))))
		{
			error = error + "\n" + "Enter Both Non Fund Based Commission and Sanctioned Date";
		}

		if ((!fbCredit.equals(""))
			&& (fbSancAmt.equals("")))
		{
			error = error + "\n" + "Fund Based Credit to be Guaranteed cannot be entered if Fund Based Sanctioned Amount is not entered";
		}

		if ((!nfbCredit.equals(""))
			&& (nfbSancAmt.equals("")))
		{
			error = error + "\n" + "Non Fund Based Credit to be Guaranteed cannot be entered if Non Fund Based Sanctioned Amount is not entered";
		}

		if (plr.equals(""))
		{
			error = error + "\n" + "Enter PLR in Working Capital Details";
		}

		if (plrType.equals(""))
		{
			error = error + "\n" + "Enter Type of PLR in Working Capital Details";
		}

/*		if (!(fbSancAmt.equals("")))
		{
			if (fbOsPrincipal.equals(""))
			{
				error = error + "\n" + "Enter Fund Based Outstanding Principal";
			}
			if (fbOsAsOn.equals(""))
			{
				error = error + "\n" + "Enter Fund Based Outstanding Date";
			}
		}*/

/*		if (!(nfbSancAmt.equals("")))
		{
			if (nfbOsPrincipal.equals(""))
			{
				error = error + "\n" + "Enter Non Fund Based Outstanding Principal";
			}
			if (nfbOsInt.equals(""))
			{
				error = error + "\n" + "Enter Non Fund Based Outstanding Interest";
			}
			if (nfbOsAsOn.equals(""))
			{
				error = error + "\n" + "Enter Non Fund Based Outstanding Date";
			}
		}
*/
		if (!fbOsPrincipal.equals("") && fbSancAmt.equals(""))
		{
			error = error + "\n" + "Fund Based Outstanding Amount cannot be entered if Fund Based Sanctioned Amount is not entered";
		}


		if (!fbOsAsOn.equals("") && fbSancAmt.equals(""))
		{
			error = error + "\n" + "Fund Based Outstanding Date cannot be entered if Fund Based Sanctioned Amount is not entered";
		}
		

/*		if (((!(nfbOsPrincipal.equals("")))
			|| (!(nfbOsInt.equals("")))
			|| (!(nfbOsAsOn.equals("")))) && (nfbSancAmt.equals("")))
		{
			error = error + "\n" + "Enter Non Fund Based Sanctioned Amount";
		}
*/

		if (!fbSancAmt.equals("") && (!fbOsPrincipal.equals("") && fbOsAsOn.equals("")))
		{
			error = error + "\n" + "Enter Fund Based Outstanding Date";
		}

		if (!fbSancAmt.equals("") && (!fbOsAsOn.equals("") && fbOsPrincipal.equals("")))
		{
			error = error + "\n" + "Enter Fund Based Outstanding Amount";
		}

		if (!fbSancAmt.equals("") && ((!(fbSancDt.equals(""))) && (!(fbOsAsOn.equals("")))))
		{
			Date sancDate = dateFormat.parse(fbSancDt, new ParsePosition(0));
			Date asOnDate = dateFormat.parse(fbOsAsOn, new ParsePosition(0));
			if ((asOnDate.compareTo(sancDate))<0)
			{
				error = error + "\n" + "Outstanding Fund Based As On Date should be later than Fund Based Sanctioned Date";
			}
			Date osDate = dateFormat.parse(fbOsAsOn, new ParsePosition(0));
			Date currDate = dateFormat.parse(dateFormat.format(new java.util.Date()), new ParsePosition(0));
			if ((currDate.compareTo(osDate))<0)
			{
				error = error + "\n" + "Fund Based Outstanding Date should not be later than Current Date";
			}
		}
		
		if (!nfbOsPrincipal.equals("") && nfbSancAmt.equals(""))
		{
			error = error + "\n" + "Non Fund Based Outstanding Amount cannot be entered if Non Fund Based Sanctioned Amount is not entered";
		}


		if (!nfbOsAsOn.equals("") && nfbSancAmt.equals(""))
		{
			error = error + "\n" + "Non Fund Based Outstanding Date cannot be entered if Non Fund Based Sanctioned Amount is not entered";
		}

		if (!fbSancAmt.equals("") && ((!(nfbSancDt.equals(""))) && (!(nfbOsAsOn.equals("")))))
		{
			Date sancDate = dateFormat.parse(nfbSancDt, new ParsePosition(0));
			Date asOnDate = dateFormat.parse(nfbOsAsOn, new ParsePosition(0));
			if ((asOnDate.compareTo(sancDate))<0)
			{
				error = error + "\n" + "Outstanding Non Fund Based As On Date should be later than Non Fund Based Sanctioned Date";
			}
			Date osDate = dateFormat.parse(fbOsAsOn, new ParsePosition(0));
			Date currDate = dateFormat.parse(dateFormat.format(new java.util.Date()), new ParsePosition(0));
			if ((currDate.compareTo(osDate))<0)
			{
				error = error + "\n" + "Non Fund Based Outstanding Date should not be later than Current Date";
			}
		}
		
		if (!fbOsPrincipal.equals("") && !fbSancAmt.equals(""))
		{
			if (Double.parseDouble(fbOsPrincipal)>Double.parseDouble(fbSancAmt))
			{
				error = error + "\n" + "Fund Based Outstanding Amount cannot be greater than Fund Based Sanctioned Amount";
			}
		}
		
		if (!nfbOsPrincipal.equals("") && !nfbSancAmt.equals(""))
		{
			if (Double.parseDouble(nfbOsPrincipal)>Double.parseDouble(nfbSancAmt))
			{
				error = error + "\n" + "Non Fund Based Outstanding Amount cannot be greater than Non Fund Based Sanctioned Amount";
			}
		}

		return error;
	}

	/**
	* This method validates the mcgs details.
	*/
	private String validateMcgsDetails()
	{
		String error = "";
		if (((String)cmbMcgfId.getSelectedItem()).equals("Select"))
		{
			error = error + "\n" + "Select MCGF Id";
		}
		if (((String)cmbParticipatingBanks.getSelectedItem()).equals("Select"))
		{
			error = error + "\n" + "Select Participating Bank";
		}

		return error;
	}

	/**
	* This method validates the details entered in a working capital application.
	*/
	private boolean validateWCApp()
	{
		String error="";
		boolean valid = true;

		error = error + validateCommonAppDetails();
		error = error + validateProjectOutlayDetails("WC");
		error = error + validateWorkingCapitalDetails();
		if (mcgfFlag)
		{
			error = error + validateMcgsDetails();
		}

		if (! (error.equals("")))
		{
			save.setEnabled(false);
			error = "Please Correct the Following:" + error;
			valid = false;
//			System.out.println("Errors Found...!!!!");
//			JOptionPane.showMessageDialog(panel, error, "Error", JOptionPane.ERROR_MESSAGE);
			JButton test = new JButton("OK");
			test.setActionCommand("ErrorOk");
			test.addActionListener(this);
			Object[] options = {test};
			Icon icon=null;
			JOptionPane optionPane = new JOptionPane(error, JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, icon, options);
			dialog = new JDialog(this, "Error", false);
			dialog = optionPane.createDialog(panel, "Error");
			dialog.setModal(false);
			dialog.show();
		}

		return valid;
	}

	/**
	* This method validates the details entered in a Term Credit application.
	*/
	private boolean validateTCApp()
	{
		String error="";
		boolean valid = true;

		error = error + validateCommonAppDetails();
		error = error + validateProjectOutlayDetails("TC");
		error = error + validateTermCreditDetails();
		if (mcgfFlag)
		{
			error = error + validateMcgsDetails();
		}

		if (! (error.equals("")))
		{
			save.setEnabled(false);
			error = "Please Correct the Following:" + error;
			valid = false;
//			System.out.println("Errors Found...!!!!");
//			JOptionPane.showMessageDialog(panel, error, "Error", JOptionPane.ERROR_MESSAGE);
			JButton test = new JButton("OK");
			test.setActionCommand("ErrorOk");
			test.addActionListener(this);
			Object[] options = {test};
			Icon icon=null;
			JOptionPane optionPane = new JOptionPane(error, JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, icon, options);
			dialog = new JDialog(this, "Error", false);
			dialog = optionPane.createDialog(panel, "Error");
			dialog.setModal(false);
			dialog.show();
		}

		return valid;
	}

	/**
	* This method validates the details entered in a Both application.
	*/
	private boolean validateBothApp()
	{
		String error="";
		boolean valid = true;

		error = error + validateCommonAppDetails();
		error = error + validateProjectOutlayDetails("BO");
		error = error + validateWorkingCapitalDetails();
		error = error + validateTermCreditDetails();
		if (mcgfFlag)
		{
			error = error + validateMcgsDetails();
		}

		if (! (error.equals("")))
		{
			save.setEnabled(false);
			error = "Please Correct the Following:" + error;
			valid = false;
//			System.out.println("Errors Found...!!!!");
//			JOptionPane.showMessageDialog(panel, error, "Error", JOptionPane.ERROR_MESSAGE);
			JButton test = new JButton("OK");
			test.setActionCommand("ErrorOk");
			test.addActionListener(this);
			Object[] options = {test};
			Icon icon=null;
			JOptionPane optionPane = new JOptionPane(error, JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, icon, options);
			dialog = new JDialog(this, "Error", false);
			dialog = optionPane.createDialog(panel, "Error");
			dialog.setModal(false);
			dialog.show();
		}

		return valid;
	}

	/**
	* This method validates the details entered in a Composite application.
	*/
	private boolean validateCCBOApp()
	{
		String error="";
		boolean valid = true;

		error = error + validateCommonAppDetails();
		error = error + validateProjectOutlayDetails("CC");
		error = error + validateWorkingCapitalDetails();
		error = error + validateTermCreditDetails();
		if (mcgfFlag)
		{
			error = error + validateMcgsDetails();
		}

		if (! (error.equals("")))
		{
			save.setEnabled(false);
			error = "Please Correct the Following:" + error;
			valid = false;
//			System.out.println("Errors Found...!!!!");
//			JOptionPane.showMessageDialog(panel, error, "Error", JOptionPane.ERROR_MESSAGE);
			JButton test = new JButton("OK");
			test.setActionCommand("ErrorOk");
			test.addActionListener(this);
			Object[] options = {test};
			Icon icon=null;
			JOptionPane optionPane = new JOptionPane(error, JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, icon, options);
			dialog = new JDialog(this, "Error", false);
			dialog = optionPane.createDialog(panel, "Error");
			dialog.setModal(false);
			dialog.show();
		}

		return valid;
	}

	/**
	*This method validates the claims details entered.
	*/
	private boolean validateClaims(boolean first)
	{
		String text = "";
		String error = "";
		boolean valid = true;

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		text = txtDateOfIssueOfRecall.getText().trim();
		if (text.equals("") || text.endsWith("/"))
		{
			error = error + "\n" + "Enter Date of Issue of Recall Notice";
		}
		else
		{
			Date issueRecall = dateFormat.parse(text, new ParsePosition(0));
			Date npaDate = dateFormat.parse(accNPADateValue.getText().trim(), new ParsePosition(0));
			if ((issueRecall.compareTo(npaDate))<0)
			{
				error = error + "\n" + "Date of Issue of Recall should be later than NPA Date";
			}
			Date currDate = dateFormat.parse(dateFormat.format(new java.util.Date()), new ParsePosition(0));
			if ((currDate.compareTo(issueRecall))<0)
			{
				error = error + "\n" + "Date of Issue of Recall should be earlier than Current Date";
			}
		}

		if (! first)
		{
			if (!willfulDefaulterYes.isSelected() && !willfulDefaulterNo.isSelected())
			{
				error = error + "\n" + "Enter whether borrower is a Wilful Defaulter";
			}
			if (txtClaimAppRefNo.getText().trim().equals(""))
			{
				error = error + "\n" + "Enter Claim Application Reference Number";
			}
			if (txtClaimDate.getText().trim().equals("") || txtClaimDate.getText().trim().endsWith("/"))
			{
				error = error + "\n" + "Enter Claim Date";
			}
			else
			{
				Date claimDate = dateFormat.parse(txtClaimDate.getText().trim(), new ParsePosition(0));
				Date currDate = dateFormat.parse(dateFormat.format(new java.util.Date()), new ParsePosition(0));
				if ((currDate.compareTo(claimDate))<0)
				{
					error = error + "\n" + "Claim Date should not be later than Current Date";
				}
			}
			if (txtClpan.getText().trim().equals(""))
			{
				error = error + "\n" + "Enter CLPAN";
			}
		}

		error = error + validateClaimsLegalProceedings(first);
		error = error + validateClaimsTermLoanDetails(first);
		error = error + validateClaimsWCDetails(first);
		error = error + validateClaimsRecoveryDetails();
		error = error + validateClaimsSecurityPGDetails(first);
		error = error + validateClaimsSummaryDetails(first);

		if (! (error.equals("")))
		{
			save.setEnabled(false);
			error = "Please Correct the Following:" + error;
			valid = false;
//			System.out.println("Errors Found...!!!!");
//			JOptionPane.showMessageDialog(panel, error, "Error", JOptionPane.ERROR_MESSAGE);
			JButton test = new JButton("OK");
			test.setActionCommand("ErrorOk");
			test.addActionListener(this);
			Object[] options = {test};
			Icon icon=null;
			JOptionPane optionPane = new JOptionPane(error, JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, icon, options);
			dialog = new JDialog(this, "Error", false);
			dialog = optionPane.createDialog(panel, "Error");
			dialog.setModal(false);
			dialog.show();
		}

		return valid;
	}

	/**
	 * This method validates the legal proceedings details of the claim application.
	 * The parameter "first" indicates whether the claim application is for first installment
	 * or second installment.
	 * true - first installment
	 * false - second installment
	 * Accordingly certain fields will be validated or not validated.
	 */
	private String validateClaimsLegalProceedings(boolean first)
	{
		String text = "";
		String error = "";
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		text = cmbForumNames.getSelectedItem().toString();
		if (text.equals("Select"))
		{
			error = error + "\n" + "Select Forum through which Legal Proceedings were Initiated by Borrower";
		}
		else if (text.equals("Others") && txtForumName.getText().trim().equals(""))
		{
			error = error + "\n" + "Enter Other Forum through which Legal Proceedings were Initiated by Borrower";
		}

		text = txtSuitCaseRegNo.getText().trim();
		if (text.equals(""))
		{
			error = error + "\n" + "Enter Suit / Case Registration Number";
		}

		text = txtLegalProcDate.getText().trim();
		if (text.endsWith("/") || text.equals(""))
		{
			error = error + "\n" + "Enter Legal Proceeding Date";
		}
		else
		{
			Date legalProcDate = dateFormat.parse(text, new ParsePosition(0));
			Date currDate = dateFormat.parse(dateFormat.format(new java.util.Date()), new ParsePosition(0));
			if ((currDate.compareTo(legalProcDate))<0)
			{
				error = error + "\n" + "Legal Proceeding Date should not be later than Current Date";
			}
		}

		text = txtForumNameLoc.getText().trim();
		if (text.equals(""))
		{
			error = error + "\n" + "Enter Forum Name";
		}
		text = txtForumLocation.getText().trim();
		if (text.equals(""))
		{
			error = error + "\n" + "Enter Forum Location";
		}

		if (! first)
		{
			text = txtAmtClaimed.getText().trim();
			if (text.equals("") || (!text.equals("") && Double.parseDouble(text) == 0.0))
			{
				error = error + "\n" + "Enter Amount Claimed";
			}

			if (recoveryProcConcludedYes.isSelected() && (txtConclusionDate.getText().trim().endsWith("/") || txtConclusionDate.getText().trim().equals("")))
			{
				error = error + "\n" + "Enter Date of Conclusion of Recovery Proceedings";
			}

			if (!(accWrittenOffYes.isSelected()) && !(accWrittenOffNo.isSelected()))
			{
				error = error + "\n" + "Enter Whether Account has been Written off from the Books of the MLI";
			}
			if (accWrittenOffYes.isSelected() && (txtDtOfAccWrittenOff.getText().trim().endsWith("/") || txtDtOfAccWrittenOff.getText().trim().equals("")))
			{
				error = error + "\n" + "Enter Date on which Account was Written off";
			}
			else if (accWrittenOffYes.isSelected() && (!txtDtOfAccWrittenOff.getText().trim().endsWith("/") && !txtDtOfAccWrittenOff.getText().trim().equals("")))
			{
				Date accWrittenOffDate = dateFormat.parse(txtDtOfAccWrittenOff.getText().trim(), new ParsePosition(0));
				Date currDate = dateFormat.parse(dateFormat.format(new java.util.Date()), new ParsePosition(0));
				if ((currDate.compareTo(accWrittenOffDate))<0)
				{
					error = error + "\n" + "Account Written Off Date should not be later than Current Date";
				}
			}
		}

		return error;
	}

	/**
	 * This method validates the term loan details in the claim application.
	 * The parameter first indicates whether the claim application is for first installment or 
	 * second installment.
	 * true - first installment
	 * false - second installment
	 */
	private String validateClaimsTermLoanDetails(boolean first)
	{
		String text = "";
		String error = "";

		int total = noOfClaimTC;
		int i = 0;
		for (i=0;i<total;i++)
		{
			text = txtDateOfLastDisbursementCol[i].getText().trim();
			if (text.endsWith("/") || text.equals(""))
			{
				error = error + "\n" + "Enter Date of Last Disbursement for " + cgpanColValueTC[i].getText().trim();
			}
/*			text = txtPrincipal[i].getText().trim();
			if (text.equals(""))
			{
				error = error + "\n" + "Enter Principal Repayment for " + cgpanColValueTC[i].getText().trim();
			}
			text = txtInterest[i].getText().trim();
			if (text.equals(""))
			{
				error = error + "\n" + "Enter Interest Repayment for " + cgpanColValueTC[i].getText().trim();
			}*/
			if (txtPrincipal[i].getText().trim().equals("") && !txtInterest[i].getText().trim().equals(""))
			{
				error = error + "\n" + "Enter Principal Repayment for " + cgpanColValueTC[i].getText().trim();
			}
			if (!txtPrincipal[i].getText().trim().equals("") && txtInterest[i].getText().trim().equals(""))
			{
				error = error + "\n" + "Enter Interest Repayment for " + cgpanColValueTC[i].getText().trim();
			}
			text = txtOutstandingOnNpaTC[i].getText().trim();
			if (text.equals("") || Double.parseDouble(text) == 0.0)
			{
				error = error + "\n" + "Enter Outstanding As On Date of NPA for " + cgpanColValueTC[i].getText().trim();
			}
			text = txtOutstandingInCaseTC[i].getText().trim();
			if (text.equals("") || Double.parseDouble(text) == 0.0)
			{
				error = error + "\n" + "Enter Outstanding stated in Civil Suit / Case Filed for " + cgpanColValueTC[i].getText().trim();
			}
			text = txtOutstandingOnLodgementTC[i].getText().trim();
			if (text.equals("") || Double.parseDouble(text) == 0.0)
			{
				if (first)
				{
					error = error + "\n" + "Enter Outstanding As on Lodgement of Claim for " + cgpanColValueTC[i].getText().trim();
				}
				else
				{
					error = error + "\n" + "Enter Outstanding As on Lodgement of Claim of First Instalment for " + cgpanColValueTC[i].getText().trim();
				}
			}
			if (! first)
			{
				text = txtOutstandingOnLodgementTCSecond[i].getText().trim();
				if (text.equals("") || Double.parseDouble(text) == 0.0)
				{
					error = error + "\n" + "Enter Outstanding As on Lodgement of Second Instalment for " + cgpanColValueTC[i].getText().trim();
				}
			}
		}

		return error;
	}

	/**
	 * This method validates the working capital details in the claim application.
	 * The parameter first indicates whether the claim application is for first installment or 
	 * second installment.
	 * true - first installment
	 * false - second installment
	 */
	private String validateClaimsWCDetails(boolean first)
	{
		String text = "";
		String error = "";

		int total = noOfClaimWC;
		int i = 0;
		for (i=0;i<total;i++)
		{
			text = txtOutstandingOnNpaWC[i].getText().trim();
			if (text.equals("") || Double.parseDouble(text) == 0.0)
			{
				error = error + "\n" + "Enter Outstanding As On Date of NPA for " + cgpanColValueWC[i].getText().trim();
			}
			text = txtOutstandingInCaseWC[i].getText().trim();
			if (text.equals("") || Double.parseDouble(text) == 0.0)
			{
				error = error + "\n" + "Enter Outstanding stated in Civil Suit / Case Filed for " + cgpanColValueWC[i].getText().trim();
			}
			text = txtOutstandingOnLodegementWC[i].getText().trim();
			if (text.equals("") || Double.parseDouble(text) == 0.0)
			{
				if (first)
				{
					error = error + "\n" + "Enter Outstanding As on Lodgement of Claim for " + cgpanColValueWC[i].getText().trim();
				}
				else
				{
					error = error + "\n" + "Enter Outstanding As on Lodgement of Claim of First Instalment for " + cgpanColValueWC[i].getText().trim();
				}
			}
			if (! first)
			{
				text = txtOutstandingOnLodegementWCSecond[i].getText().trim();
				if (text.equals("") || Double.parseDouble(text) == 0.0)
				{
					error = error + "\n" + "Enter Outstanding As on Lodgement of Second Instalment for " + cgpanColValueWC[i].getText().trim();
				}
			}
		}

		return error;
	}

	/**
	 * This method validates the recovery details in the claim application.
	 */
	private String validateClaimsRecoveryDetails()
	{
		String text = "";
		String text1 = "";
		String text2 = "";
		String error = "";
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		int total = noOfClaimRec;
		int i = 0;
		for (i=0;i<total;i++)
		{
			if ( ! txtPrincipalTCRecovery[i].getText().trim().equals("") || !txtPrincipalTCRecovery[i].getText().trim().equals("0") ||
			!txtInterestTCRecovery[i].getText().trim().equals("") || !txtInterestTCRecovery[i].getText().trim().equals("0") ||
			!txtAmountWCRecovery[i].getText().trim().equals("") || !txtAmountWCRecovery[i].getText().trim().equals("0") ||
			!txtOthersWCRecovery[i].getText().trim().equals("") || !txtOthersWCRecovery[i].getText().trim().equals("0") ||
			!cmbModeOfRecovery[i].getSelectedItem().toString().equals("Select"))
			{
				String cgpan = cgpanRecoveryValue[i].getText();
				text = cmbModeOfRecovery[i].getSelectedItem().toString();
				if (cgpan.substring(11,13).equalsIgnoreCase("TC"))
				{
					text1 = txtPrincipalTCRecovery[i].getText().trim();
					if (text1.equals("") || Double.parseDouble(text1) == 0.0)
					{
						text1="";
					}
					text2 = txtInterestTCRecovery[i].getText().trim();
					if (text2.equals("") || Double.parseDouble(text2) == 0.0)
					{
						text2="";
					}
					
					if (text1.equals("") && !text2.equals(""))
					{
						error = error + "\n" + "Enter Pricipal Recovery made for " + cgpanRecoveryValue[i].getText().trim();
					}
					if (!text1.equals("") && text2.equals(""))
					{
						error = error + "\n" + "Enter Interest Recovery made for " + cgpanRecoveryValue[i].getText().trim();
					}
					if ((!text1.equals("") || !text2.equals("")) && text.equals("Select"))
					{
						error = error + "\n" + "Enter Mode of Recovery for " + cgpanRecoveryValue[i].getText().trim();
					}
					if (!text.equalsIgnoreCase("select") && (text1.equals("") || text2.equals("")))
					{
						error = error + "\n" + "Enter both Pricipal Recovery and Interest Recovery made for " + cgpanRecoveryValue[i].getText().trim();
					}
				}
				else if (cgpan.substring(11,12).equalsIgnoreCase("W") || cgpan.substring(11,12).equalsIgnoreCase("R"))
				{
					text1 = txtAmountWCRecovery[i].getText().trim();
					if (text1.equals("") || Double.parseDouble(text1) == 0.0)
					{
						text1="";
					}
					text2 = txtOthersWCRecovery[i].getText().trim();
					if (text2.equals("") || Double.parseDouble(text2) == 0.0)
					{
						text2="";
					}
					
					if (text1.equals("") && !text2.equals(""))
					{
						error = error + "\n" + "Enter Amount Including Interest Recovered for " + cgpanRecoveryValue[i].getText().trim();
					}
					if (!text1.equals("") && text2.equals(""))
					{
						error = error + "\n" + "Enter Other Charges Recovered for " + cgpanRecoveryValue[i].getText().trim();
					}
					if ((!text1.equals("") || !text2.equals("")) && text.equals("Select"))
					{
						error = error + "\n" + "Enter Mode of Recovery for " + cgpanRecoveryValue[i].getText().trim();
					}
					if (!text.equalsIgnoreCase("select") && (text1.equals("") || text2.equals("")))
					{
						error = error + "\n" + "Enter both Amount Including Interest and Other Charges Recovered for " + cgpanRecoveryValue[i].getText().trim();
					}
				}

				if (text.equals("OTS"))
				{
					text = txtOTSApprovalDate.getText().trim();
					if (text.endsWith("/") || text.equals(""))
					{
						error = error + "\n" + "Enter Date of Seeking Approval of CGTSI for OTS";
					}
					else
					{
						Date otsAppDate = dateFormat.parse(text, new ParsePosition(0));
						Date currDate = dateFormat.parse(dateFormat.format(new java.util.Date()), new ParsePosition(0));
						if ((currDate.compareTo(otsAppDate))<0)
						{
							error = error + "\n" + "Date of Seeking OTS approval should not be later than Current Date";
						}
					}
				}
			}
		}

		return error;
	}

	/**
	 * This method validates the security and personal guarantee details in the claim application.
	 * The parameter first indicates whether the claim application is for first installment or 
	 * second installment.
	 * true - first installment
	 * false - second installment
	 */
	private String validateClaimsSecurityPGDetails(boolean first)
	{
		String text = "";
		String error = "";
		String text1 = "";
		String text2 = "";
		String text3 = "";
		String text4 = "";
		boolean land = false;
		boolean building = false;
		boolean machine = false;
		boolean fixed = false;
		boolean current = false;
		boolean others = false;

		text1 = txtAsOnSanctionLandValue.getText().trim();
		if (!text1.equals("") && Double.parseDouble(text1) == 0.0)
		{
			text1 = "";
		}
		text2 = txtAsOnNpaLandValue.getText().trim();
		if (!text2.equals("") && Double.parseDouble(text2) == 0.0)
		{
			text2 = "";
		}
		text3 = txtAsOnLodgementLandValue.getText().trim();
		if (!text3.equals("") && Double.parseDouble(text3) == 0.0)
		{
			text3 = "";
		}
		if (!first)
		{
			text4 = txtAsOnSecondClaimLandValue.getText().trim();
			if (!text4.equals("") && Double.parseDouble(text4) == 0.0)
			{
				text4 = "";
			}
		}
		if (first)
		{
			if (!text1.equals("") && !text2.equals("") && !text3.equals(""))
			{
				land = true;
			}
			else if ((text1.equals("") && text2.equals("") && !text3.equals("")) ||
				(text1.equals("") && !text2.equals("") && text3.equals("")) ||
				(text1.equals("") && !text2.equals("") && !text3.equals("")) ||
				(!text1.equals("") && text2.equals("") && text3.equals("")) ||
				(!text1.equals("") && text2.equals("") && !text3.equals("")) ||
				(!text1.equals("") && !text2.equals("") && text3.equals("")))
			{
				land = true;
				error = error + "\n" + "Enter Security Value of Land for All sections";
			}
		}
		else
		{
			if (!text1.equals("") && !text2.equals("") && !text3.equals("") && !text4.equals(""))
			{
				land = true;
			}
			else if ((text1.equals("") && text2.equals("") && text3.equals("") && !text4.equals("")) ||
				(text1.equals("") && text2.equals("") && !text3.equals("") && text4.equals("")) ||
				(text1.equals("") && text2.equals("") && !text3.equals("") && !text4.equals("")) ||
				(text1.equals("") && !text2.equals("") && text3.equals("") && text4.equals("")) ||
				(text1.equals("") && !text2.equals("") && text3.equals("") && !text4.equals("")) ||
				(text1.equals("") && !text2.equals("") && !text3.equals("") && text4.equals("")) ||
				(text1.equals("") && !text2.equals("") && !text3.equals("") && !text4.equals("")) ||
				(!text1.equals("") && text2.equals("") && text3.equals("") && text4.equals("")) ||
				(!text1.equals("") && text2.equals("") && text3.equals("") && !text4.equals("")) ||
				(!text1.equals("") && text2.equals("") && !text3.equals("") && text4.equals("")) ||
				(!text1.equals("") && text2.equals("") && !text3.equals("") && !text4.equals("")) ||
				(!text1.equals("") && !text2.equals("") && text3.equals("") && text4.equals("")) ||
				(!text1.equals("") && !text2.equals("") && text3.equals("") && !text4.equals("")) ||
				(!text1.equals("") && !text2.equals("") && !text3.equals("") && text4.equals("")))
			{
				land = true;
				error = error + "\n" + "Enter Security Value of Land for All sections";
			}
		}
		
		text1 = txtAsOnSanctionBuildingValue.getText().trim();
		if (!text1.equals("") && Double.parseDouble(text1) == 0.0)
		{
			text1 = "";
		}
		text2 = txtAsOnNpaBuildingValue.getText().trim();
		if (!text2.equals("") && Double.parseDouble(text2) == 0.0)
		{
			text2 = "";
		}
		text3 = txtAsOnLodgementBuildingValue.getText().trim();
		if (!text3.equals("") && Double.parseDouble(text3) == 0.0)
		{
			text3 = "";
		}
		if (!first)
		{
			text4 = txtAsOnSecondClaimBuildingValue.getText().trim();
			if (!text4.equals("") && Double.parseDouble(text4) == 0.0)
			{
				text4 = "";
			}
		}
		if (first)
		{
			if (!text1.equals("") && !text2.equals("") && !text3.equals(""))
			{
				building = true;
			}
			else if ((text1.equals("") && text2.equals("") && !text3.equals("")) ||
				(text1.equals("") && !text2.equals("") && text3.equals("")) ||
				(text1.equals("") && !text2.equals("") && !text3.equals("")) ||
				(!text1.equals("") && text2.equals("") && text3.equals("")) ||
				(!text1.equals("") && text2.equals("") && !text3.equals("")) ||
				(!text1.equals("") && !text2.equals("") && text3.equals("")))
			{
				building = true;
				error = error + "\n" + "Enter Security Value of Building for All sections";
			}
		}
		else
		{
			if (!text1.equals("") && !text2.equals("") && !text3.equals("") && !text4.equals(""))
			{
				building = true;
			}
			else if ((text1.equals("") && text2.equals("") && text3.equals("") && !text4.equals("")) ||
				(text1.equals("") && text2.equals("") && !text3.equals("") && text4.equals("")) ||
				(text1.equals("") && text2.equals("") && !text3.equals("") && !text4.equals("")) ||
				(text1.equals("") && !text2.equals("") && text3.equals("") && text4.equals("")) ||
				(text1.equals("") && !text2.equals("") && text3.equals("") && !text4.equals("")) ||
				(text1.equals("") && !text2.equals("") && !text3.equals("") && text4.equals("")) ||
				(text1.equals("") && !text2.equals("") && !text3.equals("") && !text4.equals("")) ||
				(!text1.equals("") && text2.equals("") && text3.equals("") && text4.equals("")) ||
				(!text1.equals("") && text2.equals("") && text3.equals("") && !text4.equals("")) ||
				(!text1.equals("") && text2.equals("") && !text3.equals("") && text4.equals("")) ||
				(!text1.equals("") && text2.equals("") && !text3.equals("") && !text4.equals("")) ||
				(!text1.equals("") && !text2.equals("") && text3.equals("") && text4.equals("")) ||
				(!text1.equals("") && !text2.equals("") && text3.equals("") && !text4.equals("")) ||
				(!text1.equals("") && !text2.equals("") && !text3.equals("") && text4.equals("")))
			{
				building = true;
				error = error + "\n" + "Enter Security Value of Building for All sections";
			}
		}
		
		text1 = txtAsOnSanctionMachineValue.getText().trim();
		if (!text1.equals("") && Double.parseDouble(text1) == 0.0)
		{
			text1 = "";
		}
		text2 = txtAsOnNpaMachineValue.getText().trim();
		if (!text2.equals("") && Double.parseDouble(text2) == 0.0)
		{
			text2 = "";
		}
		text3 = txtAsOnLodgementMachineValue.getText().trim();
		if (!text3.equals("") && Double.parseDouble(text3) == 0.0)
		{
			text3 = "";
		}
		if (!first)
		{
			text4 = txtAsOnSecondClaimMachineValue.getText().trim();
			if (!text4.equals("") && Double.parseDouble(text4) == 0.0)
			{
				text4 = "";
			}
		}
		if (first)
		{
			if (!text1.equals("") && !text2.equals("") && !text3.equals(""))
			{
				machine = true;
			}
			else if ((text1.equals("") && text2.equals("") && !text3.equals("")) ||
				(text1.equals("") && !text2.equals("") && text3.equals("")) ||
				(text1.equals("") && !text2.equals("") && !text3.equals("")) ||
				(!text1.equals("") && text2.equals("") && text3.equals("")) ||
				(!text1.equals("") && text2.equals("") && !text3.equals("")) ||
				(!text1.equals("") && !text2.equals("") && text3.equals("")))
			{
				machine = true;
				error = error + "\n" + "Enter Security Value of Machine Assets for All sections";
			}
		}
		else
		{
			if (!text1.equals("") && !text2.equals("") && !text3.equals("") && !text4.equals(""))
			{
				machine = true;
			}
			else if ((text1.equals("") && text2.equals("") && text3.equals("") && !text4.equals("")) ||
				(text1.equals("") && text2.equals("") && !text3.equals("") && text4.equals("")) ||
				(text1.equals("") && text2.equals("") && !text3.equals("") && !text4.equals("")) ||
				(text1.equals("") && !text2.equals("") && text3.equals("") && text4.equals("")) ||
				(text1.equals("") && !text2.equals("") && text3.equals("") && !text4.equals("")) ||
				(text1.equals("") && !text2.equals("") && !text3.equals("") && text4.equals("")) ||
				(text1.equals("") && !text2.equals("") && !text3.equals("") && !text4.equals("")) ||
				(!text1.equals("") && text2.equals("") && text3.equals("") && text4.equals("")) ||
				(!text1.equals("") && text2.equals("") && text3.equals("") && !text4.equals("")) ||
				(!text1.equals("") && text2.equals("") && !text3.equals("") && text4.equals("")) ||
				(!text1.equals("") && text2.equals("") && !text3.equals("") && !text4.equals("")) ||
				(!text1.equals("") && !text2.equals("") && text3.equals("") && text4.equals("")) ||
				(!text1.equals("") && !text2.equals("") && text3.equals("") && !text4.equals("")) ||
				(!text1.equals("") && !text2.equals("") && !text3.equals("") && text4.equals("")))
			{
				machine = true;
				error = error + "\n" + "Enter Security Value of Machine Assets for All sections";
			}
		}
		
		text1 = txtAsOnSanctionFixedValue.getText().trim();
		if (!text1.equals("") && Double.parseDouble(text1) == 0.0)
		{
			text1 = "";
		}
		text2 = txtAsOnNpaFixedValue.getText().trim();
		if (!text2.equals("") && Double.parseDouble(text2) == 0.0)
		{
			text2 = "";
		}
		text3 = txtAsOnLodgementFixedValue.getText().trim();
		if (!text3.equals("") && Double.parseDouble(text3) == 0.0)
		{
			text3 = "";
		}
		if (!first)
		{
			text4 = txtAsOnSecondClaimFixedValue.getText().trim();
			if (!text4.equals("") && Double.parseDouble(text4) == 0.0)
			{
				text4 = "";
			}
		}
		if (first)
		{
			if (!text1.equals("") && !text2.equals("") && !text3.equals(""))
			{
				fixed = true;
			}
			else if ((text1.equals("") && text2.equals("") && !text3.equals("")) ||
				(text1.equals("") && !text2.equals("") && text3.equals("")) ||
				(text1.equals("") && !text2.equals("") && !text3.equals("")) ||
				(!text1.equals("") && text2.equals("") && text3.equals("")) ||
				(!text1.equals("") && text2.equals("") && !text3.equals("")) ||
				(!text1.equals("") && !text2.equals("") && text3.equals("")))
			{
				fixed = true;
				error = error + "\n" + "Enter Security Value of Other Fixed / Current Assets for All sections";
			}
		}
		else
		{
			if (!text1.equals("") && !text2.equals("") && !text3.equals("") && !text4.equals(""))
			{
				fixed = true;
			}
			else if ((text1.equals("") && text2.equals("") && text3.equals("") && !text4.equals("")) ||
				(text1.equals("") && text2.equals("") && !text3.equals("") && text4.equals("")) ||
				(text1.equals("") && text2.equals("") && !text3.equals("") && !text4.equals("")) ||
				(text1.equals("") && !text2.equals("") && text3.equals("") && text4.equals("")) ||
				(text1.equals("") && !text2.equals("") && text3.equals("") && !text4.equals("")) ||
				(text1.equals("") && !text2.equals("") && !text3.equals("") && text4.equals("")) ||
				(text1.equals("") && !text2.equals("") && !text3.equals("") && !text4.equals("")) ||
				(!text1.equals("") && text2.equals("") && text3.equals("") && text4.equals("")) ||
				(!text1.equals("") && text2.equals("") && text3.equals("") && !text4.equals("")) ||
				(!text1.equals("") && text2.equals("") && !text3.equals("") && text4.equals("")) ||
				(!text1.equals("") && text2.equals("") && !text3.equals("") && !text4.equals("")) ||
				(!text1.equals("") && !text2.equals("") && text3.equals("") && text4.equals("")) ||
				(!text1.equals("") && !text2.equals("") && text3.equals("") && !text4.equals("")) ||
				(!text1.equals("") && !text2.equals("") && !text3.equals("") && text4.equals("")))
			{
				fixed = true;
				error = error + "\n" + "Enter Security Value of Fixed / Current Assets for All sections";
			}
		}
		
		text1 = txtAsOnSanctionCurrentValue.getText().trim();
		if (!text1.equals("") && Double.parseDouble(text1) == 0.0)
		{
			text1 = "";
		}
		text2 = txtAsOnNpaCurrentValue.getText().trim();
		if (!text2.equals("") && Double.parseDouble(text2) == 0.0)
		{
			text2 = "";
		}
		text3 = txtAsOnLodgementCurrentValue.getText().trim();
		if (!text3.equals("") && Double.parseDouble(text3) == 0.0)
		{
			text3 = "";
		}
		if (!first)
		{
			text4 = txtAsOnSecondClaimCurrentValue.getText().trim();
			if (!text4.equals("") && Double.parseDouble(text4) == 0.0)
			{
				text4 = "";
			}
		}
		if (first)
		{
			if (!text1.equals("") && !text2.equals("") && !text3.equals(""))
			{
				current = true;
			}
			else if ((text1.equals("") && text2.equals("") && !text3.equals("")) ||
				(text1.equals("") && !text2.equals("") && text3.equals("")) ||
				(text1.equals("") && !text2.equals("") && !text3.equals("")) ||
				(!text1.equals("") && text2.equals("") && text3.equals("")) ||
				(!text1.equals("") && text2.equals("") && !text3.equals("")) ||
				(!text1.equals("") && !text2.equals("") && text3.equals("")))
			{
				current = true;
				error = error + "\n" + "Enter Security Value of Current Assets for All sections";
			}
		}
		else
		{
			if (!text1.equals("") && !text2.equals("") && !text3.equals("") && !text4.equals(""))
			{
				current = true;
			}
			else if ((text1.equals("") && text2.equals("") && text3.equals("") && !text4.equals("")) ||
				(text1.equals("") && text2.equals("") && !text3.equals("") && text4.equals("")) ||
				(text1.equals("") && text2.equals("") && !text3.equals("") && !text4.equals("")) ||
				(text1.equals("") && !text2.equals("") && text3.equals("") && text4.equals("")) ||
				(text1.equals("") && !text2.equals("") && text3.equals("") && !text4.equals("")) ||
				(text1.equals("") && !text2.equals("") && !text3.equals("") && text4.equals("")) ||
				(text1.equals("") && !text2.equals("") && !text3.equals("") && !text4.equals("")) ||
				(!text1.equals("") && text2.equals("") && text3.equals("") && text4.equals("")) ||
				(!text1.equals("") && text2.equals("") && text3.equals("") && !text4.equals("")) ||
				(!text1.equals("") && text2.equals("") && !text3.equals("") && text4.equals("")) ||
				(!text1.equals("") && text2.equals("") && !text3.equals("") && !text4.equals("")) ||
				(!text1.equals("") && !text2.equals("") && text3.equals("") && text4.equals("")) ||
				(!text1.equals("") && !text2.equals("") && text3.equals("") && !text4.equals("")) ||
				(!text1.equals("") && !text2.equals("") && !text3.equals("") && text4.equals("")))
			{
				current = true;
				error = error + "\n" + "Enter Security Value of Current Assets for All sections";
			}
		}
		
		text = txtAsOnSanctionOthersValue.getText().trim();
		text1 = txtAsOnSanctionOthersValue.getText().trim();
		if (!text1.equals("") && Double.parseDouble(text1) == 0.0)
		{
			text1 = "";
		}
		text2 = txtAsOnNpaOthersValue.getText().trim();
		if (!text2.equals("") && Double.parseDouble(text2) == 0.0)
		{
			text2 = "";
		}
		text3 = txtAsOnLodgementOthersValue.getText().trim();
		if (!text3.equals("") && Double.parseDouble(text3) == 0.0)
		{
			text3 = "";
		}
		if (!first)
		{
			text4 = txtAsOnSecondClaimOthersValue.getText().trim();
			if (!text4.equals("") && Double.parseDouble(text4) == 0.0)
			{
				text4 = "";
			}
		}
		if (first)
		{
			if (!text1.equals("") && !text2.equals("") && !text3.equals(""))
			{
				others = true;
			}
			else if ((text1.equals("") && text2.equals("") && !text3.equals("")) ||
				(text1.equals("") && !text2.equals("") && text3.equals("")) ||
				(text1.equals("") && !text2.equals("") && !text3.equals("")) ||
				(!text1.equals("") && text2.equals("") && text3.equals("")) ||
				(!text1.equals("") && text2.equals("") && !text3.equals("")) ||
				(!text1.equals("") && !text2.equals("") && text3.equals("")))
			{
				others = true;
				error = error + "\n" + "Enter Security Value of Others for All sections";
			}
		}
		else
		{
			if (!text1.equals("") && !text2.equals("") && !text3.equals("") && !text4.equals(""))
			{
				others = true;
			}
			else if ((text1.equals("") && text2.equals("") && text3.equals("") && !text4.equals("")) ||
				(text1.equals("") && text2.equals("") && !text3.equals("") && text4.equals("")) ||
				(text1.equals("") && text2.equals("") && !text3.equals("") && !text4.equals("")) ||
				(text1.equals("") && !text2.equals("") && text3.equals("") && text4.equals("")) ||
				(text1.equals("") && !text2.equals("") && text3.equals("") && !text4.equals("")) ||
				(text1.equals("") && !text2.equals("") && !text3.equals("") && text4.equals("")) ||
				(text1.equals("") && !text2.equals("") && !text3.equals("") && !text4.equals("")) ||
				(!text1.equals("") && text2.equals("") && text3.equals("") && text4.equals("")) ||
				(!text1.equals("") && text2.equals("") && text3.equals("") && !text4.equals("")) ||
				(!text1.equals("") && text2.equals("") && !text3.equals("") && text4.equals("")) ||
				(!text1.equals("") && text2.equals("") && !text3.equals("") && !text4.equals("")) ||
				(!text1.equals("") && !text2.equals("") && text3.equals("") && text4.equals("")) ||
				(!text1.equals("") && !text2.equals("") && text3.equals("") && !text4.equals("")) ||
				(!text1.equals("") && !text2.equals("") && !text3.equals("") && text4.equals("")))
			{
				others = true;
				error = error + "\n" + "Enter Security Value of Others for All sections";
			}
		}

/*		if (!(land || building || machine || fixed || current || others))
		{
			error = error + "\n" + "Enter Security Value of atleast 1";
		}

		text = txtAsOnSanctionNetworth.getText().trim();
		if (text.equals("") || Double.parseDouble(text) == 0.0)
		{
			error = error + "\n" + "Enter Networth of Guarantor(s) As on Date of Sanction of Credit";
		}
		text = txtAsOnNpaNetworth.getText().trim();
		if (text.equals("") || Double.parseDouble(text) == 0.0)
		{
			error = error + "\n" + "Enter Networth of Guarantor(s) As on Date of NPA";
		}
		text = txtAsOnLodgementNetworth.getText().trim();
		if (text.equals("") || Double.parseDouble(text) == 0.0)
		{
			if (first)
			{
				error = error + "\n" + "Enter Networth of Guarantor(s) As on Date of Lodgement of Claim";
			}
			else
			{
				error = error + "\n" + "Enter Networth of Guarantor(s) As on Date of Lodgement of First Installment Claim";
			}
		}
		if (! first)
		{
			text = txtAsOnSecondClaimNetworth.getText().trim();
			if (text.equals("") || Double.parseDouble(text) == 0.0)
			{
				error = error + "\n" + "Enter Networth of Guarantor(s) As on Date of Lodgement of Second Claim Installment";
			}
			text = txtAmtRealisedPg.getText().trim();
			if (text.equals("") || Double.parseDouble(text) == 0.0)
			{
				error = error + "\n" + "Enter Amount Realised thorugh Invocation of Personal Guarantee";
			}
			if (land)
			{
				text = txtAmtRealisedLand.getText().trim();
				if (text.equals("") || (!text.equals("") && Double.parseDouble(text) == 0.0))
				{
					error = error + "\n" + "Enter Amount Realised thorugh Disposal of Land";
				}
			}
			if (building)
			{
				text = txtAmtRealisedBuilding.getText().trim();
				if (text.equals("") || (!text.equals("") && Double.parseDouble(text) == 0.0))
				{
					error = error + "\n" + "Enter Amount Realised thorugh Disposal of Building";
				}
			}
			if (machine)
			{
				text = txtAmtRealisedMachine.getText().trim();
				if (text.equals("") || (!text.equals("") && Double.parseDouble(text) == 0.0))
				{
					error = error + "\n" + "Enter Amount Realised thorugh Disposal of Machine";
				}
			}
			if (fixed)
			{
				text = txtAmtRealisedFixed.getText().trim();
				if (text.equals("") || (!text.equals("") && Double.parseDouble(text) == 0.0))
				{
					error = error + "\n" + "Enter Amount Realised thorugh Disposal of Other Fixed / Current Assets";
				}
			}
			if (current)
			{
				text = txtAmtRealisedCurrent.getText().trim();
				if (text.equals("") || (!text.equals("") && Double.parseDouble(text) == 0.0))
				{
					error = error + "\n" + "Enter Amount Realised thorugh Disposal of Current Assets";
				}
			}
			if (others)
			{
				text = txtAmtRealisedOthers.getText().trim();
				if (text.equals("") || (!text.equals("") && Double.parseDouble(text) == 0.0))
				{
					error = error + "\n" + "Enter Amount Realised thorugh Disposal of Others";
				}
			}
		}*/

		return error;
	}

	/**
	 * This method validates the claim summary details in the claim application.
	 * The parameter first indicates whether the claim application is for first installment or 
	 * second installment.
	 * true - first installment
	 * false - second installment
	 */
	private String validateClaimsSummaryDetails(boolean first)
	{
		String text = "";
		String error = "";

		int total = noOfClaimSummary;
		int i = 0;
		for (i=0;i<total;i++)
		{
			if (! first)
			{
/*				if ( ! txtAmountClaimed[i].getText().trim().equals("") || !txtAmountClaimed[i].getText().trim().equals("0") || !txtAmountSettled[i].getText().trim().equals("") || !txtAmountSettled[i].getText().trim().equals("0") || !txtDtOfSettlement[i].getText().trim().equals("") || !txtDtOfSettlement[i].getText().trim().endsWith("/") || !txtAmountClaimedSI[i].getText().trim().equals("") || !txtAmountClaimedSI[i].getText().trim().equals("0"))
				{
					text = txtAmountClaimed[i].getText().trim();
					if (text.equals("") || Double.parseDouble(text) == 0.0)
					{
						error = error + "\n" + "Enter Amount Claimed for " + claimSummaryCgpan[i].getText().trim();
					}
					text = txtAmountSettled[i].getText().trim();
					if (!text.equals("") && Double.parseDouble(text) != 0.0)
					{
						if (txtDtOfSettlement[i].getText().trim().endsWith("/") || txtDtOfSettlement[i].getText().trim().equals(""))
						{
							error = error + "\n" + "Enter Date of Settlement of First Instalment for " + claimSummaryCgpan[i].getText().trim();
						}
					}
					if (!txtDtOfSettlement[i].getText().trim().endsWith("/") && !txtDtOfSettlement[i].getText().trim().equals(""))
					{
						text = txtAmountSettled[i].getText().trim();
						if (text.equals("") || (!text.equals("") && Double.parseDouble(text) == 0.0))
						{
							error = error + "\n" + "Enter Amount Settled by CGTSI for " + claimSummaryCgpan[i].getText().trim();
						}
					}*/
					text = txtAmountClaimedSI[i].getText().trim();
					if (text.equals("") || Double.parseDouble(text) == 0.0)
					{
						error = error + "\n" + "Enter Amount Claimed in Second Instalment for " + claimSummaryCgpan[i].getText().trim();
					}
					
//				}
			}
			else
			{
				text = txtAmountClaimed[i].getText().trim();
				if (text.equals("") || Double.parseDouble(text) == 0.0)
				{
					error = error + "\n" + "Enter Amount Claimed for " + claimSummaryCgpan[i].getText().trim();
				}
			}
		}

		return error;
	}
	
	private boolean validatePeriodicInput(String fileName)
	{
		String text = "";
		String error = "";
		boolean valid = true;
		StringTokenizer tokenizer;
		
		if (fileName.equals(""))
		{
			text = txtBorrowerIdInput.getText().trim();
			if (text.equals(""))
			{
				error = error + "\n" + "Enter Borrower ID";
			}
			text = txtBorrowerNameInput.getText().trim();
			if (text.equals(""))
			{
				error = error + "\n" + "Enter Borrower Name";
			}
			text = txtOSCgpansInput.getText().trim();
			if (text.endsWith(","))
			{
				text = text.substring(0, text.length()-1);
			}
			tokenizer = new StringTokenizer(text, ",");
			int cgpansCount = tokenizer.countTokens();
			if (cgpansCount == 0)
			{
				if (text.length()!=0 && text.length()!=13)
				{
					error = error + "\n" + "Enter valid list of CGPANs for Outstanding Details";
				}
				else if (text.length()!=0 && (!text.endsWith("TC") && !text.endsWith("WC")))
				{
					error = error + "\n" + "Enter valid list of CGPANs for Outstanding Details";
				}
			}
			else
			{
				while (tokenizer.hasMoreTokens())
				{
					text = tokenizer.nextToken();
					if (text.length()!=0 && text.length()!=13)
					{
						error = error + "\n" + "Enter valid list of CGPANs for Outstanding Details";
					}
					else if (text.length()!=0 && (!text.endsWith("TC") && !text.endsWith("WC")))
					{
						error = error + "\n" + "Enter valid list of CGPANs for Outstanding Details";
					}
				}
			}
			text = txtNoOfOsInput.getText().trim();
			if (text.equals(""))
			{
				for (int i=0;i<cgpansCount;i++)
				{
					text=text + "0,";
				}
			}
			if (text.endsWith(","))
			{
				text = text.substring(0, text.length()-1);
			}
			txtNoOfOsInput.setText(text);
			tokenizer = new StringTokenizer(text, ",");
			int noCount = tokenizer.countTokens();
//			System.out.println("cgpan count " + cgpansCount);
//			System.out.println("ip count " + noCount);
			if (cgpansCount != noCount)
			{
				error = error + "\n" + "No of CGPANs for Outstanding Details and No of Outstandings should match";
			}
			else
			{
				while (tokenizer.hasMoreTokens())
				{
					text = tokenizer.nextToken().trim();
					try
					{
						if (Integer.parseInt(text) > 10)
						{
							error = error + "\n" + "No of Outstandings for a CGPAN cannot be more than 10";
							break;
						}
					}
					catch (NumberFormatException nfException)
					{
						error = error + "\n" + "Enter valid No of Outstandings";
						break;
					}
				}
			}
			
			text = txtDisCgpansInput.getText().trim();
			if (text.endsWith(","))
			{
				text = text.substring(0, text.length()-1);
			}
			tokenizer = new StringTokenizer(text, ",");
			cgpansCount = tokenizer.countTokens();
			if (tokenizer.countTokens()==0)
			{
				if (text.length()!=0 && text.length()!=13)
				{
					error = error + "\n" + "Enter valid list of CGPANs for Disbursement Details";
				}
				else if (text.length()!=0 && (!text.endsWith("TC") && !text.endsWith("WC")))
				{
					error = error + "\n" + "Enter valid list of CGPANs for Disbursement Details";
				}
			}
			else
			{
				while (tokenizer.hasMoreTokens())
				{
					text = tokenizer.nextToken();
					if (text.length()!=0 && text.length()!=13)
					{
						error = error + "\n" + "Enter valid list of CGPANs for Disbursement Details";
					}
					else if (text.length()!=0 && (!text.endsWith("TC") && !text.endsWith("WC")))
					{
						error = error + "\n" + "Enter valid list of CGPANs for Disbursement Details";
					}
				}
			}
			text = txtNoOfDisInput.getText().trim();
			if (text.endsWith(","))
			{
				text = text.substring(0, text.length()-1);
			}
			tokenizer = new StringTokenizer(text, ",");
			noCount = tokenizer.countTokens();
			if (cgpansCount != noCount)
			{
				error = error + "\n" + "No of CGPANs for Disbursement Details and No of Disbursments should match";
			}
			else
			{
				while (tokenizer.hasMoreTokens())
				{
					text = tokenizer.nextToken();
					try
					{
						if (Integer.parseInt(text) > 10)
						{
							error = error + "\n" + "No of Disbursements for a CGPAN cannot be more than 10";
							break;
						}
					}
					catch (NumberFormatException nfException)
					{
						error = error + "\n" + "Enter valid No of Disbursements";
						break;
					}
				}
			}
			
			text = txtRepayCgpansInput.getText().trim();
			if (text.endsWith(","))
			{
				text = text.substring(0, text.length()-1);
			}
			tokenizer = new StringTokenizer(text, ",");
			cgpansCount = tokenizer.countTokens();
			if (tokenizer.countTokens()==0)
			{
				if (text.length()!=0 && text.length()!=13)
				{
					error = error + "\n" + "Enter valid list of CGPANs for Repayment Details";
				}
				else if (text.length()!=0 && (!text.endsWith("TC") && !text.endsWith("WC")))
				{
					error = error + "\n" + "Enter valid list of CGPANs for Repayment Details";
				}
			}
			else
			{
				while (tokenizer.hasMoreTokens())
				{
					text = tokenizer.nextToken();
					if (text.length()!=0 && text.length()!=13)
					{
						error = error + "\n" + "Enter valid list of CGPANs for Repayment Details";
					}
					else if (text.length()!=0 && (!text.endsWith("TC") && !text.endsWith("WC")))
					{
						error = error + "\n" + "Enter valid list of CGPANs for Repayment Details";
					}
				}
			}
			text = txtNoOfRepayInput.getText().trim();
			if (text.endsWith(","))
			{
				text = text.substring(0, text.length()-1);
			}
			tokenizer = new StringTokenizer(text, ",");
			noCount = tokenizer.countTokens();
			if (cgpansCount != noCount)
			{
				error = error + "\n" + "No of CGPANs for Repayment Details and No of Repayments should match";
			}
			else
			{
				while (tokenizer.hasMoreTokens())
				{
					text = tokenizer.nextToken();
					try
					{
						if (Integer.parseInt(text) > 10)
						{
							error = error + "\n" + "No of Repayments for a CGPAN cannot be more than 10";
							break;
						}
					}
					catch (NumberFormatException nfException)
					{
						error = error + "\n" + "Enter valid No of Repayments";
						break;
					}
				}
			}			
		}
		else if (!fileName.equals(""))
		{
			int cgpansCount = 0;
			int noCount = 0;
			if (OSCgpansInputValue!=null)
			{
				text = OSCgpansInputValue.getText().trim();
				tokenizer = new StringTokenizer(text, ",");
				cgpansCount = tokenizer.countTokens();
				
				text = txtNoOfOsInput.getText().trim();
				if (text.endsWith(","))
				{
					text = text.substring(0, text.length()-1);
				}
				if (text.equals(""))
				{
					for (int i=0;i<cgpansCount;i++)
					{
						text=text + "0,";
					}
				}
				txtNoOfOsInput.setText(text);
				tokenizer = new StringTokenizer(text, ",");
				noCount = tokenizer.countTokens();
				if (cgpansCount != noCount)
				{
					error = error + "\n" + "No of CGPANs for Outstanding Details and No of Outstandings should match";
				}
				else
				{
					while (tokenizer.hasMoreTokens())
					{
						text = tokenizer.nextToken().trim();
						try
						{
							if (Integer.parseInt(text) > 10)
							{
								error = error + "\n" + "No of Outstandings for a CGPAN cannot be more than 10";
								break;
							}
						}
						catch (NumberFormatException nfException)
						{
							error = error + "\n" + "Enter valid No of Outstandings";
							break;
						}
					}
				}
			}
			
			if (disCgpansInputValue!=null)
			{
				text = disCgpansInputValue.getText().trim();
				tokenizer = new StringTokenizer(text, ",");
				cgpansCount = tokenizer.countTokens();
				text = txtNoOfDisInput.getText().trim();
				if (text.endsWith(","))
				{
					text = text.substring(0, text.length()-1);
				}
				if (text.equals(""))
				{
					for (int i=0;i<cgpansCount;i++)
					{
						text=text + "0,";
					}
				}
				txtNoOfDisInput.setText(text);
				tokenizer = new StringTokenizer(text, ",");
				noCount = tokenizer.countTokens();
				if (cgpansCount != noCount)
				{
					error = error + "\n" + "No of CGPANs for Disbursement Details and No of Disbursments should match";
				}
				else
				{
					while (tokenizer.hasMoreTokens())
					{
						text = tokenizer.nextToken();
						try
						{
							if (Integer.parseInt(text) > 10)
							{
								error = error + "\n" + "No of Disbursements for a CGPAN cannot be more than 10";
								break;
							}
						}
						catch (NumberFormatException nfException)
						{
							error = error + "\n" + "Enter valid No of Disbursements";
							break;
						}
					}
				}
			}
			
			if (repayCgpansInputValue!=null)
			{
				text = repayCgpansInputValue.getText().trim();
				tokenizer = new StringTokenizer(text, ",");
				cgpansCount = tokenizer.countTokens();
				text = txtNoOfRepayInput.getText().trim();
				if (text.endsWith(","))
				{
					text = text.substring(0, text.length()-1);
				}
				if (text.equals(""))
				{
					for (int i=0;i<cgpansCount;i++)
					{
						text=text + "0,";
					}
				}
				txtNoOfRepayInput.setText(text);
				tokenizer = new StringTokenizer(text, ",");
				noCount = tokenizer.countTokens();
				if (cgpansCount != noCount)
				{
					error = error + "\n" + "No of CGPANs for Repayment Details and No of Repayments should match";
				}
				else
				{
					while (tokenizer.hasMoreTokens())
					{
						text = tokenizer.nextToken();
						try
						{
							if (Integer.parseInt(text) > 10)
							{
								error = error + "\n" + "No of Repayments for a CGPAN cannot be more than 10";
								break;
							}
						}
						catch (NumberFormatException nfException)
						{
							error = error + "\n" + "Enter valid No of Repayments";
							break;
						}
					}
				}			
			}
		}
		
		if (! error.equals(""))
		{
//			save.setEnabled(false);
			error = "Please Correct the Following:" + error;
			valid = false;
//			System.out.println("Errors Found...!!!!");
//			JOptionPane.showMessageDialog(panel, error, "Error", JOptionPane.ERROR_MESSAGE);
			JOptionPane optionPane = new JOptionPane(error, JOptionPane.ERROR_MESSAGE);
			JDialog dialog = new JDialog(this, "Error", false);
			dialog = optionPane.createDialog(panel, "Error");
			dialog.setModal(false);
			dialog.show();
		}
		
		return valid;
	}
	
	private boolean validateClaimsInput()
	{
		String text = "";
		String error = "";
		boolean valid = true;
		StringTokenizer tokenizer;
		
		text = txtBorrowerIdInput.getText().trim();
		if (text.equals(""))
		{
			error = error + "\n" + "Enter Borrower ID";
		}
		text = txtBorrowerNameInput.getText().trim();
		if (text.equals(""))
		{
			error = error + "\n" + "Enter Borrower Name";
		}

		text = txtWCCgpansInput.getText().trim();
		if (text.endsWith(","))
		{
			text = text.substring(0, text.length()-1);
		}
		tokenizer = new StringTokenizer(text, ",");
		int wccgpansCount = tokenizer.countTokens();
				
		text = txtTCCgpansInput.getText().trim();
		if (text.endsWith(","))
		{
			text = text.substring(0, text.length()-1);
		}
		tokenizer = new StringTokenizer(text, ",");
		int tccgpansCount = tokenizer.countTokens();
		
		if (tccgpansCount == 0 && wccgpansCount == 0)
		{
			error = error + "\n" + "Enter either CGPANs for Term Loan / Composite Loan Details or Working Capital Details";
		}
		else
		{
			if (tccgpansCount == 0)
			{
				if (text.length()!=0 && text.length()!=13)
				{
					error = error + "\n" + "Enter valid list of CGPANs for Term Loan / Composite Loan Details";
				}
				else if (text.length()!=0 && !text.endsWith("TC"))
				{
					error = error + "\n" + "Enter valid list of CGPANs for Term Loan / Composite Loan Details";
				}
			}
			else
			{
				while (tokenizer.hasMoreTokens())
				{
					text = tokenizer.nextToken();
					if (text.length()!=0 && text.length()!=13)
					{
						error = error + "\n" + "Enter valid list of CGPANs for Term Loan / Composite Loan Details";
					}
					else if (text.length()!=0 && !text.endsWith("TC"))
					{
						error = error + "\n" + "Enter valid list of CGPANs for Term Loan / Composite Loan Details";
					}
				}
			}

			if (wccgpansCount == 0)
			{
				if (text.length()!=0 && text.length()!=13)
				{
					error = error + "\n" + "Enter valid list of CGPANs for Working Capital Details";
				}
				else if (text.length()!=0 && !text.endsWith("WC"))
				{
					error = error + "\n" + "Enter valid list of CGPANs for Working Capital Details";
				}
			}
			else
			{
				while (tokenizer.hasMoreTokens())
				{
					text = tokenizer.nextToken();
					if (text.length()!=0 && text.length()!=13)
					{
						error = error + "\n" + "Enter valid list of CGPANs for Working Capital Details";
					}
					else if (text.length()!=0 && !text.endsWith("WC"))
					{
						error = error + "\n" + "Enter valid list of CGPANs for Working Capital Details";
					}
				}
			}
		}

		if (! error.equals(""))
		{
//			save.setEnabled(false);
			error = "Please Correct the Following:" + error;
			valid = false;
//			System.out.println("Errors Found...!!!!");
//			JOptionPane.showMessageDialog(panel, error, "Error", JOptionPane.ERROR_MESSAGE);
			JOptionPane optionPane = new JOptionPane(error, JOptionPane.ERROR_MESSAGE);
			JDialog dialog = new JDialog(this, "Error", false);
			dialog = optionPane.createDialog(panel, "Error");
			dialog.setModal(false);
			dialog.show();
		}
		
		return valid;		
	}

	/**
	 * This method validates the periodic info details entered.
	 */
	private boolean validatePeriodicInfo()
	{
		String text = "";
		String error = "";
		boolean valid = true;

		error = error + validateOSDetails();
		error = error + validateDisDetails();
		error = error + validateRepaymentDetails();
		if (lblExport.getText().equals("F"))
		{
			error = error + validateNPALegalRecDetails(false);				//File Not Present
		}
		else
		{
			error = error + validateNPALegalRecDetails(true);				//File Present
		}
		
		
		if (!(error.equals("")))
		{
			save.setEnabled(false);
			error = "Please Correct the Following:" + error;
			valid = false;
//			System.out.println("Errors Found...!!!!");
//			JOptionPane.showMessageDialog(panel, error, "Error", JOptionPane.ERROR_MESSAGE);
			JButton test = new JButton("OK");
			test.setActionCommand("ErrorOk");
			test.addActionListener(this);
			Object[] options = {test};
			Icon icon=null;
			JOptionPane optionPane = new JOptionPane(error, JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, icon, options);
			dialog = new JDialog(this, "Error", false);
			dialog = optionPane.createDialog(panel, "Error");
			dialog.setModal(false);
			dialog.show();
		}

		return valid;
	}

	/**
	 * This method validates the outstanding details of the Periodic Info.
	 */
	private String validateOSDetails()
	{
		int noOfOs = noOfOutstandings.length;
		String error = "";
		int i = 0;
		int txtIndex=0;
		double totalOs=0;
		double totalSancAmt=0;
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		for (i=0;i<noOfOs;i++)
		{
			totalOs=0;
			totalSancAmt=0;
			for (int j=0;j<noOfOutstandings[i];j++)
			{
				String type=facilityValue[i].getText().trim();
				type=type.substring(type.length()-2, type.length()-1);
				if (type.equalsIgnoreCase("T"))
				{
//					System.out.println("tc txtIndex -- " + txtIndex);
					if ((txtTCPrincipal[txtIndex].getText().trim()).equals(""))
					{
						error = error + "\n" + "Enter Outstanding Principal for " + osCgpanValue[i].getText().trim();
					}
					if (txtTCAsOnDate[txtIndex].getText().trim().endsWith("/") || txtTCAsOnDate[txtIndex].getText().trim().equals(""))
					{
						error = error + "\n" + "Enter Outstanding Date for " + osCgpanValue[i].getText().trim();
					}
					else
					{
						Date osDate = dateFormat.parse(txtTCAsOnDate[txtIndex].getText().trim(), new ParsePosition(0));
						Date currDate = dateFormat.parse(dateFormat.format(new java.util.Date()), new ParsePosition(0));
						if ((currDate.compareTo(osDate))<0)
						{
							error = error + "\n" + "Outstanding Date for " + osCgpanValue[i].getText().trim() + " should not be later than Current Date";
						}
					}
				}
				else if (type.equalsIgnoreCase("W") || type.equalsIgnoreCase("R"))
				{
					double wcfbamt = Double.parseDouble(sanctionedWCFB[i].getText().trim());
					double wcnfbamt = Double.parseDouble(sanctionedWCNFB[i].getText().trim());
					totalSancAmt=wcfbamt+wcnfbamt;
					if (wcfbamt!=0)
					{
//						System.out.println("wc txtIndex -- " + txtIndex);
						if ((txtWCFBPrincipal[txtIndex].getText().trim()).equals(""))
						{
							error = error + "\n" + "Enter WC Fund Based Principal Outstanding for " + osCgpanValue[i].getText().trim();
						}
						else
						{
							totalOs+=Double.parseDouble(txtWCFBPrincipal[txtIndex].getText().trim());
						}
						if ((txtWCFBIntComm[txtIndex].getText().trim()).equals(""))
						{
							error = error + "\n" + "Enter WC Fund Based Interest/Commission Outstanding for " + osCgpanValue[i].getText().trim();
						}
						else
						{
							totalOs+=Double.parseDouble(txtWCFBIntComm[txtIndex].getText().trim());
						}
						if (((txtWCFBAsOnDate[txtIndex].getText().trim()).equals("")) || ((txtWCFBAsOnDate[txtIndex].getText().trim()).endsWith("/")))
						{
							error = error + "\n" + "Enter WC Fund Based As On Date for " + osCgpanValue[i].getText().trim();
						}
						else
						{
							Date osDate = dateFormat.parse(txtWCFBAsOnDate[txtIndex].getText().trim(), new ParsePosition(0));
							Date currDate = dateFormat.parse(dateFormat.format(new java.util.Date()), new ParsePosition(0));
							if ((currDate.compareTo(osDate))<0)
							{
								error = error + "\n" + "WC Fund Based As On Date for " + osCgpanValue[i].getText().trim() + " should not be later than Current Date";
							}							
						}
					}
					else if ((!(txtWCFBPrincipal[txtIndex].getText().trim()).equals("") && Double.parseDouble(txtWCFBPrincipal[txtIndex].getText().trim())>0) || 
							(!(txtWCFBIntComm[txtIndex].getText().trim()).equals("") && Double.parseDouble(txtWCFBIntComm[txtIndex].getText().trim())>0) ||
							(!((txtWCFBAsOnDate[txtIndex].getText().trim()).equals("")) && !((txtWCFBAsOnDate[txtIndex].getText().trim()).endsWith("/"))))
					{
						error = error + "\n" + "Fund Based Outstanding Details for " + osCgpanValue[i].getText().trim() + " cannot be entered";
					}
					if (wcnfbamt!=0)
					{
						if ((txtWCNFBPrincipal[txtIndex].getText().trim()).equals(""))
						{
							error = error + "\n" + "Enter WC Non Fund Based Principal Outstanding for " + osCgpanValue[i].getText().trim();
						}
						else
						{
							totalOs+=Double.parseDouble(txtWCNFBPrincipal[txtIndex].getText().trim());
						}						
						if ((txtWCNFBIntComm[txtIndex].getText().trim()).equals(""))
						{
							error = error + "\n" + "Enter WC Non Fund Based Interest/Commission Outstanding for " + osCgpanValue[i].getText().trim();
						}
						else
						{
							totalOs+=Double.parseDouble(txtWCNFBIntComm[txtIndex].getText().trim());
						}						
						if (((txtWCNFBAsOnDate[txtIndex].getText().trim()).equals("")) || ((txtWCNFBAsOnDate[txtIndex].getText().trim()).endsWith("/")))
						{
							error = error + "\n" + "Enter WC Non Fund Based As On Date for " + osCgpanValue[i].getText().trim();
						}
						else
						{
							Date osDate = dateFormat.parse(txtWCNFBAsOnDate[txtIndex].getText().trim(), new ParsePosition(0));
							Date currDate = dateFormat.parse(dateFormat.format(new java.util.Date()), new ParsePosition(0));
							if ((currDate.compareTo(osDate))<0)
							{
								error = error + "\n" + "WC Non Fund Based As On Date for " + osCgpanValue[i].getText().trim() + " should not be later than Current Date";
							}							
						}
					}
					else if ((!(txtWCNFBPrincipal[txtIndex].getText().trim()).equals("") && Double.parseDouble(txtWCNFBPrincipal[txtIndex].getText().trim())>0) || 
							(!(txtWCNFBIntComm[txtIndex].getText().trim()).equals("") && Double.parseDouble(txtWCNFBIntComm[txtIndex].getText().trim())>0) ||
							(!((txtWCNFBAsOnDate[txtIndex].getText().trim()).equals("")) && !((txtWCNFBAsOnDate[txtIndex].getText().trim()).endsWith("/"))))
					{
						error = error + "\n" + "Non Fund Based Outstanding Details for " + osCgpanValue[i].getText().trim() + " cannot be entered";
					}
				}
				txtIndex++;
			}
			if (totalOs > totalSancAmt)
			{
				error = error + "\n" + "Outstanding Amount for " + osCgpanValue[i].getText().trim() + " cannot be greater than the Sanctioned Amount";
			}
		}
		return error;
	}

	/**
	 * This method validates the disbursement details of the periodic info
	 */
	private String validateDisDetails()
	{
		String error = "";
		int noOfDis = noOfDisbursements.length;
		int i = 0;
		int j = 0;
		int txtIndex = 0;
		double disAmt=0;
		double sancAmt =0; 
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		for (i=0;i<noOfDis;i++)
		{
			boolean finalDis = false;
			disAmt=0;
			for (j=0;j<noOfDisbursements[i];j++)
			{
				if (finalDis && ! (txtDisbursementAmt[txtIndex].getText().trim()).equals(""))
				{
					error = error + "\n" + "Disbursement Amount " + (j+1) + " Cannot be Entered for " + disCgpanValue[i].getText().trim() + " since Final Disbursement is made";
				}
				else if (! finalDis && ((txtDisbursementAmt[txtIndex].getText().trim()).equals("") || Double.parseDouble(txtDisbursementAmt[txtIndex].getText().trim())==0))
				{
					error = error + "\n" + "Enter Disbursement Amount " + (j+1) + " for " + disCgpanValue[i].getText().trim();
				}
				else if (! finalDis && (!(txtDisbursementAmt[txtIndex].getText().trim()).equals("")))
				{
					disAmt+=Double.parseDouble(txtDisbursementAmt[txtIndex].getText().trim());
				}
				if (finalDis && ! (((txtDisbursementDate[txtIndex].getText().trim()).equals("")) || ((txtDisbursementDate[txtIndex].getText().trim()).endsWith("/"))))
				{
					error = error + "\n" + "Disbursement Date " + (j+1) + " Cannot be Entered for " + disCgpanValue[i].getText().trim() + " since Final Disbursement is made";
				}
				else if (! finalDis && (((txtDisbursementDate[txtIndex].getText().trim()).equals("")) || ((txtDisbursementDate[txtIndex].getText().trim()).endsWith("/"))))
				{
					error = error + "\n" + "Enter Disbursement Date " + (j+1) + " for " + disCgpanValue[i].getText().trim();
				}
				else if (! finalDis && ((!(txtDisbursementDate[txtIndex].getText().trim()).equals("")) || !((txtDisbursementDate[txtIndex].getText().trim()).endsWith("/"))))
				{
					Date disDate = dateFormat.parse(txtDisbursementDate[txtIndex].getText().trim(), new ParsePosition(0));
					Date currDate = dateFormat.parse(dateFormat.format(new java.util.Date()), new ParsePosition(0));
					if ((currDate.compareTo(disDate))<0)
					{
						error = error + "\n" + "Disbursement Date " + (j+1) + " for " + disCgpanValue[i].getText().trim() + " should not be later than Current Date";
					}
				}
				if (chkFinalDisbursement[txtIndex].isSelected())
				{
					finalDis = true;
				}
				txtIndex+=1;
			}
			sancAmt = Double.parseDouble(sanctionedAmtValue[i].getText());
			if (disAmt > sancAmt)
			{
				error = error + "\n" + "Disbursement Amount for " + disCgpanValue[i].getText().trim() + " cannot be greater than Sanctioned Amount";
			}
		}

		return error;
	}

	/**
	 * This method validates the repayment details.
	 */
	private String validateRepaymentDetails()
	{
		String error = "";
		int noOfRepay = noOfRepayments.length;
		int i = 0;
		int j = 0;
		int txtIndex = 0;
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		for (i=0;i<noOfRepay;i++)
		{
			for (j=0;j<noOfRepayments[i];j++)
			{
				if ((txtRepaymentAmt[txtIndex].getText().trim()).equals("") || Double.parseDouble(txtRepaymentAmt[txtIndex].getText().trim())==0)
				{
					error = error + "\n" + "Enter Repayment Amount " + (j+1) + " for " + repayCgpanValue[i].getText().trim();
				}
				if (((txtRepaymentDate[txtIndex].getText().trim()).equals("")) || ((txtRepaymentDate[txtIndex].getText().trim()).endsWith("/")))
				{
					error = error + "\n" + "Enter Repayment Date " + (j+1) + " for " + repayCgpanValue[i].getText().trim();
				}
				else
				{
					Date repayDate = dateFormat.parse(txtRepaymentDate[txtIndex].getText().trim(), new ParsePosition(0));
					Date currDate = dateFormat.parse(dateFormat.format(new java.util.Date()), new ParsePosition(0));
					if ((currDate.compareTo(repayDate))<0)
					{
						error = error + "\n" + "Repayment Date " + (j+1) + " for " + repayCgpanValue[i].getText().trim() + " should not be later than Current Date";
					}
				}
				txtIndex+=1;
			}
		}

		return error;
	}

	/**
	* Validates the NPA details, legal suit details and recovery details in the Periodic Info.
	*/
	private String validateNPALegalRecDetails(boolean filePresent)
	{
		String error = "";
		boolean npa = false;

		//NPA Details validation

		String npaDate = txtNpaDate.getText().trim();
		String text = "";
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		if (npaDate.endsWith("/"))
		{
			npaDate = "";
		}
		if (!npaDate.equals(""))
		{
			npa = true;
		}

		text = txtOsAmtAsOnNpaDate.getText().trim();
		if (npa && text.equals(""))
		{
			error = error + "\n" + "Enter Outstanding Amount as on NPA";
		}
		else if (!npa && (!text.equals("") && Double.parseDouble(text) != 0.0))
		{
			error = error + "\n" + "Outstanding Amount as on NPA cannot be entered when NPA Date is not entered";
		}

		if (!npa && npaReportedYes.isSelected())
		{
			error = error + "\n" + "NPA Reported cannot be Yes when NPA Date is not entered";
		}
		else if (npa)
		{
			text = txtReportingDate.getText().trim();
			if (text.endsWith("/"))
			{
				text = "";
			}
			if (npaReportedYes.isSelected() && text.equals(""))
			{
				error = error + "\n" + "Enter Date of Reporting NPA to CGTSI";
			}
			else if (npaReportedNo.isSelected() && ! text.equals(""))
			{
				error = error + "\n" + "Date of Reporting NPA to CGTSI cannot be enetered when NPA is not Reported";
			}
			else
			{
				if (! text.equals(""))
				{
					Date reportDate = dateFormat.parse(text, new ParsePosition(0));
					Date npaDateValue = dateFormat.parse(npaDate, new ParsePosition(0));
					if ((reportDate.compareTo(npaDateValue))<0)
					{
						error = error + "\n" + "Date of Reporting NPA to CGTSI should be later than NPA Date";
					}
					Date currDate = dateFormat.parse(dateFormat.format(new java.util.Date()), new ParsePosition(0));
					if ((currDate.compareTo(reportDate))<0)
					{
						error = error + "\n" + "Date of Reporting NPA to CGTSI should be earlier than Current Date";
					}
				}
			}
			if (!watchListYes.isSelected() && !watchListNo.isSelected())
			{
				error = error + "\n" + "Enter whether to place under WatchList";
			}			
		}

		//Legal Suit Validations

//		if (filePresent)
//		{
			if (! npa && procInitiatedYes.isSelected())
			{
				error = error + "\n" + "Recovery Procedures cannot be Initiated when NPA Details are not entered";
			}
			if (! npa || (npa && procInitiatedNo.isSelected()))
			{
				int i = 0;
//				System.out.println("no of action " + noOfActions);
				for (i=0;i<noOfActions;i++)
				{
					if (!((String) cmbActionType[i].getSelectedItem()).equals("Select") ||
						! txtDetails[i].getText().trim().equals("") || 
						 (!txtDate[i].getText().trim().endsWith("/") && !txtDate[i].getText().trim().equals("")) ||
						! txtAttachment[i].getText().trim().equals(""))
					{
						error = error + "\n" + "Recovery Action Details cannot be entered";
					}
				}
			}
			if (! npa)
			{
				if ((! ((String) cmbForumNames.getSelectedItem()).equals("Select")) ||
					(! txtForumName.getText().trim().equals("")) ||
					(! txtSuitCaseRegNo.getText().trim().equals("")) ||
					(! txtLegalProcDate.getText().trim().endsWith("/") && ! txtLegalProcDate.getText().trim().equals("")) ||
					(! txtForumNameLoc.getText().trim().equals("")) ||
					(! txtForumLocation.getText().trim().equals("")) ||
					((! txtAmtClaimed.getText().trim().equals("")) &&
					(Double.parseDouble(txtAmtClaimed.getText().trim()) != 0.0)) ||
					(! txtCurrentStatus.getText().trim().equals("")) ||
					(recoveryProcConcludedYes.isSelected()) ||
					(recoveryProcConcludedNo.isSelected()) ||
					(! txtEffortConclusionDate.getText().trim().endsWith("/") && ! txtEffortConclusionDate.getText().trim().equals("")) ||
					(! txtMliComment.getText().trim().equals("")) ||
					(! txtFinancialAssistanceDetails.getText().trim().equals("")) ||
					(creditSupportYes.isSelected()) ||
					(creditSupportNo.isSelected()) ||
					(! txtBankFacilityDetails.getText().trim().equals("")) ||
					(watchListYes.isSelected()) ||
					(watchListNo.isSelected()) ||
					(! txtRemarks.getText().trim().equals("")))
				{
					error = error + "\n" + "Details of Legal Proceedings and Others cannot be entered when NPA Details are not entered";
				}

				if ((! txtRecoveryDate.getText().trim().endsWith("/") && ! txtRecoveryDate.getText().trim().equals("")) ||
					((! txtAmtRecovered.getText().trim().equals("")) &&
					(Double.parseDouble(txtAmtRecovered.getText().trim()) != 0)) ||
					((! txtLegalCharges.getText().trim().equals("")) &&
					(Double.parseDouble(txtLegalCharges.getText().trim()) != 0)) ||
					(! txtRemarks.getText().trim().equals("")) ||
					(recoveryOTSYes.isSelected()) ||
					(recoveryOTSNo.isSelected()) ||
					(saleOfAssetsYes.isSelected()) ||
					(saleOfAssetsNo.isSelected()) ||
					(! txtAssetsDetails.getText().trim().equals("")))
				{
					error = error + "\n" + "Recovery Details cannot be entered when NPA Details are not entered";
				}
			}
//		}
		else if (npa)
		{
			if (procInitiatedYes.isSelected())
			{
				int i = 0;
				for (i=0;i<noOfActions;i++)
				{
					if (((String) cmbActionType[i].getSelectedItem()).equals("Select"))
					{
						error = error + "\n" + "Select Action Type " + (i+1);
					}
					if (txtDetails[i].getText().trim().equals(""))
					{
						error = error + "\n" + "Enter Action Details " + (i+1);
					}
					if (txtDate[i].getText().trim().endsWith("/") || txtDate[i].getText().trim().equals(""))
					{
						error = error + "\n" + "Enter Action Date " + (i+1);
					}
					else
					{
						Date actionDate = dateFormat.parse(txtDate[i].getText().trim(), new ParsePosition(0));
						Date currDate = dateFormat.parse(dateFormat.format(new java.util.Date()), new ParsePosition(0));
						if ((currDate.compareTo(actionDate))<0)
						{
							error = error + "\n" + "Action Date " + (i+1) + " should not be later than Current Date";
						}
					}
				}

				text = (String) cmbForumNames.getSelectedItem();
				if (text.equals("Select"))
				{
					error = error + "\n" + "Enter Forum through which Legal Proceedings were initiated";
				}
				else if (text.equals("Others") && txtForumName.getText().trim().equals(""))
				{
					error = error + "\n" + "Enter Other Forum through which Legal Proceedings were initiated";
				}
				if (txtForumNameLoc.getText().trim().equals(""))
				{
					error = error + "\n" + "Enter Forum Name";
				}
				if (txtForumLocation.getText().trim().equals(""))
				{
					error = error + "\n" + "Enter Forum Location";
				}
				if (txtLegalProcDate.getText().trim().endsWith("/") || txtLegalProcDate.getText().trim().equals(""))
				{
					error = error + "\n" + "Enter Suit Filing date";
				}
				else
				{
					if (!txtLegalProcDate.getText().trim().equals("") && !txtLegalProcDate.getText().trim().endsWith("/"))
					{
						Date filingDate = dateFormat.parse(txtLegalProcDate.getText().trim(), new ParsePosition(0));
						Date currDate = dateFormat.parse(dateFormat.format(new java.util.Date()), new ParsePosition(0));
						if ((currDate.compareTo(filingDate))<0)
						{
							error = error + "\n" + "Suit Filing Date should not be later than Current Date";
						}
					}
				}
			}


		if ((! txtRecoveryDate.getText().trim().endsWith("/") && ! txtRecoveryDate.getText().trim().equals("")) ||
			((! txtAmtRecovered.getText().trim().equals("")) &&
			(Double.parseDouble(txtAmtRecovered.getText().trim()) != 0)) ||
			((! txtLegalCharges.getText().trim().equals("")) &&
			(Double.parseDouble(txtLegalCharges.getText().trim()) != 0)) ||
			(! txtRemarks.getText().trim().equals("")) ||
			(recoveryOTSYes.isSelected()) ||
			(recoveryOTSNo.isSelected()) ||
			(saleOfAssetsYes.isSelected()) ||
			(saleOfAssetsNo.isSelected()) ||
			(! txtAssetsDetails.getText().trim().equals("")))
		{
			if (txtRecoveryDate.getText().trim().endsWith("/") || txtRecoveryDate.getText().trim().equals(""))
			{
				error = error + "\n" + "Enter Date of Recovery";
			}
			if (txtAmtRecovered.getText().trim().equals("") || 
			(Double.parseDouble(txtAmtRecovered.getText().trim()) == 0))
			{
				error = error + "\n" + "Enter Amount Recovered";
			}
			if (! recoveryOTSYes.isSelected() &&
			!recoveryOTSNo.isSelected())
			{
				error = error + "\n" + "Enter whether Recovery is by way of OTS";
			}
		}
		
		}

		return error;
	}
	
	private boolean validateRecovery()
	{
		String error = "";
		boolean valid = true;
		
		if (txtRecoveryDate.getText().trim().endsWith("/") || txtRecoveryDate.getText().trim().equals(""))
		{
			error = error + "\n" + "Enter Date of Recovery";
		}
		if (txtAmtRecovered.getText().trim().equals("") || 
		(Double.parseDouble(txtAmtRecovered.getText().trim()) == 0))
		{
			error = error + "\n" + "Enter Amount Recovered";
		}
		if (! recoveryOTSYes.isSelected() &&
		!recoveryOTSNo.isSelected())
		{
			error = error + "\n" + "Enter whether Recovery is by way of OTS";
		}

		if (!(error.equals("")))
		{
			save.setEnabled(false);
			error = "Please Correct the Following:" + error;
			valid = false;
//			System.out.println("Errors Found...!!!!");
//			JOptionPane.showMessageDialog(panel, error, "Error", JOptionPane.ERROR_MESSAGE);
			JButton test = new JButton("OK");
			test.setActionCommand("ErrorOk");
			test.addActionListener(this);
			Object[] options = {test};
			Icon icon=null;
			JOptionPane optionPane = new JOptionPane(error, JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, icon, options);
			dialog = new JDialog(this, "Error", false);
			dialog = optionPane.createDialog(panel, "Error");
			dialog.setModal(false);
			dialog.show();
		}
		
		return valid;
	}

	/**
	* This method sets the passed values to the GridBagConstraints.
	* These constraints are applied to the component and added to the container.
	*/
	private void addComponent(
		int gridX,
		int gridY,
		int width,
		int anchor,
		int fillMode,
		JComponent component,
		GridBagLayout gridBag,
		GridBagConstraints constraints,
		JPanel panel) {
		constraints.gridx = gridX;
		constraints.gridy = gridY;
		constraints.gridwidth = width;
		constraints.anchor = anchor;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		constraints.fill = fillMode;
		gridBag.setConstraints(component, constraints);
		panel.add(component);
	}

	/**
	* This method resets all the components of the panel passed
	*/
	private void resetPanel(JPanel jpanel) {
		Component[] components;
		Component[] scrollPaneContents;
		int noOfComponents = 0;
		int noOfScrollPaneComponents=0;
		int i = 0;
		int j = 0;

		components = jpanel.getComponents();
		noOfComponents = components.length;
		for (i = 0; i < noOfComponents; i++) {
			if (components[i] instanceof JTextField) {
				((JTextField) components[i]).setText("");
			} else if (components[i] instanceof JComboBox) {
				((JComboBox) components[i]).setSelectedIndex(0);
			}
			else if (components[i] instanceof JScrollPane)
			{
				scrollPaneContents=((JScrollPane) components[i]).getComponents();
				noOfScrollPaneComponents=scrollPaneContents.length;
				for (j=0;j<noOfScrollPaneComponents;j++)
				{
					if (scrollPaneContents[j] instanceof JViewport)
					{
						JViewport viewport=(JViewport) scrollPaneContents[j];
						((JTextArea) viewport.getView()).setText("");
					}
				}
			}
			else if (components[i] instanceof JPanel)
			{
				resetPanel((JPanel) components[i]);
			}
		}
	}

	/**
	* This method disables all the controls in case of verification or modification.
	* The logic is to retrieve all the components in the main panel and disable all the controls except
	* labels.
	*
	* If the component is a scrollpane, the viewport component from the scollpane is retrieved.
	* The view of the viewport is disabled.
	*
	* If the component is a panel, the components from the panel is retrieved.
	* If the component in the panel is a radio button, then it is disabled.
	*/
	private void disableAll()
	{
		Component[] components;
		Component[] panelContents;
		int noOfComponents = 0;
		int noOfPanelComponents=0;
		int i = 0;
		int j=0;

		components = panel.getComponents();
		noOfComponents = components.length;

		// process each component in the main panel
		for (i=0;i<noOfComponents;i++)
		{
			if (!(components[i] instanceof JLabel) &&
				!(components[i] instanceof JButton))
			{
				if (components[i] instanceof JScrollPane)
				{
					panelContents=((JScrollPane) components[i]).getComponents();
					noOfPanelComponents=panelContents.length;

					// process each component of a scroll pane
					for (j=0;j<noOfPanelComponents;j++)
					{
						if (panelContents[j] instanceof JViewport)
						{
							JViewport viewport=(JViewport) panelContents[j];
							(viewport.getView()).setEnabled(false);
						}
					}
				}
				else if (components[i] instanceof JPanel)
				{
					panelContents=((JPanel) components[i]).getComponents();
					noOfPanelComponents=panelContents.length;

					//process each component of a panel
					for (j=0;j<noOfPanelComponents;j++)
					{
						if (!(components[i] instanceof JLabel) &&
							!(components[i] instanceof JButton))
						{
							panelContents[j].setEnabled(false);
						}
					}
				}
				else
				{
					components[i].setEnabled(false);
				}
			}
		}
	}

	/**
	* This method write the passed object to a flat file.
	*/
	private void writeToFile(Object object, String sKey, String fileName) throws ThinClientException
	{
		Hashtable files=new Hashtable();
		boolean fileFound=false;
		String key="";

			try
			{
				files=readFromFile(fileName);
			}
			catch (ThinClientException exception)
			{
				if (! exception.getMessage().equals("File Not Found"))
				{
					throw exception;
				}
			}

			if (object instanceof com.cgtsi.application.Application)
			{
				String bName="";
				if (((com.cgtsi.application.Application)object).getBorrowerDetails()!=null)
				{
					bName=((com.cgtsi.application.Application)object).getBorrowerDetails().getSsiDetails().getSsiName();
				}
				if (files.isEmpty())
				{
/*					if (bName==null || bName.equals(""))
					{
						key="1-"+sKey;
					}
					else if (bName!=null && !bName.equals(""))
					{
						key="1-"+sKey+ " (" + bName + ")";
					}*/
					key="1-"+sKey;
				}
				else
				{
					int size=files.size();
/*					if (bName==null || bName.equals(""))
					{
						key=(size+1)+"-"+sKey;
					}
					else if (bName!=null && !bName.equals(""))
					{
						key=(size+1)+"-"+sKey+ " (" + bName + ")";
					}*/
					key=(size+1)+"-"+sKey;
				}
			}
			else
			{
				key=sKey;
			}

			files.put(key, object);

			try
			{
				writeDetails(fileName, files);
			}
			catch (ThinClientException exception)
			{
				if (! exception.getMessage().equals("File Not Found"))
				{
					throw exception;
				}
			}

			if (fileSizeLimit(fileName))
			{
				files=readFromFile(fileName);
				files.remove(key);

				int fileNo=Integer.parseInt(fileName.charAt(fileName.indexOf("."))+"");
				int newFileNo=fileNo+1;

				writeDetails(fileName, files);

				fileName=fileName.replaceAll(fileNo+".", newFileNo+".");
				files.clear();
				files.put(key, object);
				try
				{
					writeDetails(fileName, files);
				}
				catch (ThinClientException exception)
				{
					if (! exception.getMessage().equals("File Not Found"))
					{
						throw exception;
					}
				}
			}
	}

	/**
	 * This method writes the hashtable object to the file specified by the fileName.
	 */
	private void writeDetails(String fileName, Hashtable files) throws ThinClientException
	{
		try
		{
			FileOutputStream fileOutputStream;
			ObjectOutputStream objectOutputStream;
//			System.out.println("anme  " + fileName);
			fileOutputStream=new FileOutputStream(fileName);
//			System.out.println("anme  " + fileName);
			objectOutputStream=new ObjectOutputStream(fileOutputStream);
			if (!files.containsKey("version"))
			{
				files.put("version", getVersion());
			}
			objectOutputStream.writeObject(files);
			objectOutputStream.close();
			fileOutputStream.close();			
		}
		catch (FileNotFoundException exception)
		{
			throw new ThinClientException("File Not Found");
		}
		catch (SecurityException exception)
		{
			throw new ThinClientException("Write Access Denied to File " + fileName);
		}
		catch (IOException exception)
		{
			throw new ThinClientException("Error Writing Details to File" + fileName);
		}
	}

	/**
	* This method reads a specific flat file and returns the object from the file
	*/
	private Hashtable readFromFile(String fileName) throws ThinClientException
	{
		Hashtable objects=new Hashtable();

		try
		{
			FileInputStream fileInputStream=new FileInputStream(fileName);
			ObjectInputStream objectInputStream=new ObjectInputStream(fileInputStream);

			objects=(Hashtable) objectInputStream.readObject();

			objectInputStream.close();
			fileInputStream.close();
		}
		catch(FileNotFoundException exception)
		{
			throw new ThinClientException("File Not Found");
		}
		catch(SecurityException exception)
		{
			throw new ThinClientException("Read Access Denied for File " + fileName);
		}
		catch(IOException exception)
		{
			exception.printStackTrace();
			throw new ThinClientException("Error Reading File");
		}
		catch (ClassNotFoundException exception)
		{
			throw new ThinClientException("Class Not Found");
		}

		return objects;
	}

	/**
	 * This method checks if the file size is greater then 1.44 mb.
	 * @returns true if the size is equal to or above 1.44 mb
	 *			false if the size is less than 1.44 mb
	 */
	private boolean fileSizeLimit(String fileName)
	{
		boolean isAboveLimit=false;
		File file=new File(fileName);
		long fileSize=file.length();
		double fileSizeInMb=fileSize/1048576.0;					//1048576 bytes is 1 mb

		if (fileSizeInMb >= 1.44)
		{
			isAboveLimit=true;
		}

		return isAboveLimit;
	}

	/**
	* This method handles all the events generated in this application.
	*/
	public void actionPerformed(ActionEvent actionEvent) {
		//action event from the menu option - application -- New -- term credit
		
		//System.out.println(actionEvent.getActionCommand());
		if (actionEvent.getActionCommand().equals("NewTCApp"))
		{
			displayTCApplication(false);
			validate();
		}
		//action event from the menu option - application -- new -- working capital
		else if (actionEvent.getActionCommand().equals("NewWCApp"))
		{
			displayWCApplication();
			validate();
		}
		//action event from the menu option - application -- new -- composite
		else if (actionEvent.getActionCommand().equals("NewCompositeApp"))
		{
			displayCompositeApplication();
			validate();
		}
		//action event from the menu option - application -- new -- both
		else if (actionEvent.getActionCommand().equals("NewBothApp"))
		{
			displayBothApplication();
			validate();
		}
		//action event from the menu option - application -- new -- Additional Term Credit
		else if (actionEvent.getActionCommand().equals("AddTermCredit"))
		{
			displayAddTCApplication();
			validate();
		}
		//action event from the menu option - application -- new -- Working Capital Enhancement
		else if (actionEvent.getActionCommand().equals("WCEnhancement"))
		{
			displayWCEApplication();
			validate();
		}
		//action event from the menu option - application -- new -- Working Capital Enhancement
		else if (actionEvent.getActionCommand().equals("WCRenewal"))
		{
			displayWCRApplication();
			validate();
		}
		//action event from the menu option - application -- verify app
		// it displays the verify panel to get the file path.
		else if (actionEvent.getActionCommand().equals("VerifyApp"))
		{
			displayVerifyPanel("APP", "VER");
			ok.setActionCommand("ToVerifyApp");
			validate();
		}
		//action event from the menu option application -- modify
		// it displays the verify panel to get the file path
		else if (actionEvent.getActionCommand().equals("ModifyApp"))
		{
			displayVerifyPanel("APP", "MOD");
			ok.setActionCommand("ToModifyApp");
			validate();
		}
		//action event from any panel if the reset button is clicked
		else if (actionEvent.getActionCommand().equals("Reset"))
		{
			resetPanel(panel);
			validate();
		}
		// action event from the term credit application panel if the cancel button is clicked
		// it displays the home panel.
		else if (actionEvent.getActionCommand().equals("CancelApplication"))
		{
			displayHomePanel();
			validate();
		}
		//action event from the verify panel screen
		//while specifying the file to be opened, if the browse button is clicked, this event is fired.
		// it displays the file chooser dialog box.
		else if (actionEvent.getSource() ==  browse)
		{
			String actionCommand = actionEvent.getActionCommand();
			int index = actionCommand.indexOf("$");
			String type = actionCommand.substring(0, index);
			String flag = actionCommand.substring(index+1, actionCommand.length());
			showFileChooser(type, flag); 
			validate();
		}
		//action event from the application screen that dispalys the application for verification,
			//if the cancel button is clicked or
		//action event from the screen when the file is specified for verification
			//and ok button is clicked

		// the list of applications for verification is displayed.
		else if ((actionEvent.getActionCommand().equals("ToVerifyApp"))
				|| (actionEvent.getActionCommand().equals("CancelVerifyApp")))
		{
			try
			{
				if (validateBeforeDisplayList("APP", "VER"))
				{
					displayList("APP", "VER");
					cancel.setActionCommand("VerifyApp");
				}
			}
			catch (ThinClientException exception)
			{
				displayError(exception.getMessage());
			}
			validate();
		}
		//action event from the application screen that dispalys the application for modification,
			//if the cancel button is clicked or
		//action event from the screen when the file is specified for modificaiton
			//and ok button is clicked

		// the list of applications for modification is displayed.
		else if ((actionEvent.getActionCommand().equals("ToModifyApp"))
				|| (actionEvent.getActionCommand().equals("CancelModifyApp")))
		{
			try
			{
				displayList("APP", "MOD");
				cancel.setActionCommand("ModifyApp");
			}
			catch (ThinClientException exception)
			{
				displayError(exception.getMessage());
			}
			validate();
		}
		//action event fired from the term credit application screen when the save button is clicked.
		else if (actionEvent.getActionCommand().equals("SaveTCApplication"))
		{
			try
			{
				if (validateTCApp())
				{
					saveTCApp();
					displayHomePanel();
				}
			}
			catch (ThinClientException exception)
			{
				displayError(exception.getMessage());
			}
			validate();
		}
		//action fired when Save is clicked on additional term credit application screen.
		else if (actionEvent.getActionCommand().equals("SaveAddTCApplication"))
		{
			try
			{
				String error = "";
				if (txtBankRefNo.getText().trim().equals(""))
				{
					error = error + "\n" + "Enter Bank Reference Number";
				}
				if (txtBankBranchName.getText().trim().equals(""))
				{
					error = error + "\n" + "Enter Bank Branch Name";
				}				
				if (txtSsiValue.getText().trim().equals(""))
				{
					error = error + "\n" + "Enter CGPAN";
				}
				else
				{
					String cgpan=txtSsiValue.getText().trim();
					if (cgpan.length()!=13 || !cgpan.substring(11, 12).equalsIgnoreCase("T"))
					{
						error = error + "\n" + "Enter Valid CGPAN";
					}
				}
				error = error + validateTermCreditDetails();
				if (error.equals(""))
				{
					saveAddTCApp();
					displayHomePanel();
				}
				else
				{
					save.setEnabled(false);
					error = "Please Correct the Following:" + error;
//					System.out.println("Errors Found...!!!!");
		//			JOptionPane.showMessageDialog(panel, error, "Error", JOptionPane.ERROR_MESSAGE);
					JButton test = new JButton("OK");
					test.setActionCommand("ErrorOk");
					test.addActionListener(this);
					Object[] options = {test};
					Icon icon=null;
					JOptionPane optionPane = new JOptionPane(error, JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, icon, options);
					dialog = new JDialog(this, "Error", false);
					dialog = optionPane.createDialog(panel, "Error");
					dialog.setModal(false);
					dialog.show();
				}
			}
			catch (ThinClientException exception)
			{
				displayError(exception.getMessage());
			}
			validate();
		}
		// action fired from the verify application details screen
		else if (actionEvent.getActionCommand().equals("SaveVerifyApp"))
		{
			try
			{
//				System.out.println("calling verify save");
				if (saveVerifiedModifiedDetails("APP", "VER"))
				{
					displayHomePanel();
				}
			 }
			 catch (ThinClientException exception)
			 {
				 displayError(exception.getMessage());
			 }
			 validate();
		}
		// action fired from the modify application details screen
		else if (actionEvent.getActionCommand().equals("SaveModifyApp"))
		{
			try
			{
				if (saveVerifiedModifiedDetails("APP", "MOD"))
				{
					displayHomePanel();
				}
			 }
			 catch (ThinClientException exception)
			 {
				 displayError(exception.getMessage());
			 }
			validate();
		}
		//action event fired from the working capital application screen when the save button is clicked.
		else if (actionEvent.getActionCommand().equals("SaveWCApplication"))
		{
			try
			{
				if (validateWCApp())
				{
					saveWCApp();
					displayHomePanel();
				}
			 }
			 catch (ThinClientException exception)
			 {
				 displayError(exception.getMessage());
			 }
			validate();
		}
		//action event fired from the enhancement working capital application screen when the save button is clicked.
		else if (actionEvent.getActionCommand().equals("SaveWCEApplication"))
		{
			try
			{
				String error = "";
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				double fb=0;
				double nfb=0;

				if (txtSsiValue.getText().trim().equals(""))
				{
					error = error + "\n" + "Enter CGPAN";
				}
				else
				{
					String cgpan=txtSsiValue.getText().trim();
					if (cgpan.length()!=13 || (!cgpan.substring(11, 12).equalsIgnoreCase("W") && !cgpan.substring(11, 12).equalsIgnoreCase("R")))
					{
						error = error + "\n" + "Enter Valid CGPAN";
					}
				}				
				if (!txtWCFundBased.getText().trim().equals(""))
				{
					fb=Double.parseDouble(txtWCFundBased.getText().trim());
				}
				if (!txtWCNonFundBased.getText().trim().equals(""))
				{
					nfb=Double.parseDouble(txtWCNonFundBased.getText().trim());
				}				
				if (fb==0 && nfb==0)
				{
					error = error + "\n" + "Enter Either Enhanced Fund Based Amount or Non Fund Based Amount";
				}
				if (fb>0 && (txtEnhancedInterest.getText().trim().equals("") || Double.parseDouble(txtEnhancedInterest.getText().trim())==0))
				{
					error = error + "\n" + "Enter Enhanced Fund Based Interest";
				}
				else if (fb==0 && (!txtEnhancedInterest.getText().trim().equals("") && Double.parseDouble(txtEnhancedInterest.getText().trim())>0))
				{
					error = error + "\n" + "Enhanced Interest cannot be entered if Enhanced Fund Based Amount is not entered";
				}
				if (nfb>0 && (txtEnhancedCommission.getText().trim().equals("") || Double.parseDouble(txtEnhancedCommission.getText().trim())==0))
				{
					error = error + "\n" + "Enter Enhanced Non Fund Based Commission";
				}
				else if (nfb==0 && (!txtEnhancedCommission.getText().trim().equals("") && Double.parseDouble(txtEnhancedCommission.getText().trim())>0))
				{
					error = error + "\n" + "Enhanced Commission cannot be entered if Enhanced Non Fund Based Amount is not entered";
				}				
				if (txtDtOfEnhancement.getText().trim().endsWith("/") || txtDtOfEnhancement.getText().trim().equals(""))
				{
					error = error + "\n" + "Enter Date of Enhancement";
				}
				else
				{
					Date sancDate = dateFormat.parse(txtDtOfEnhancement.getText().trim(), new ParsePosition(0));
					Date currDate = dateFormat.parse(dateFormat.format(new java.util.Date()), new ParsePosition(0));
					if ((currDate.compareTo(sancDate))<0)
					{
						error = error + "\n" + "Date of Enhancement should not be later than Current Date";
					}
				}
				if (error.equals(""))
				{
					saveWCEApp();
					displayHomePanel();
				}
				else
				{
					save.setEnabled(false);
					error = "Please Correct the Following:" + error;
					JButton test = new JButton("OK");
					test.setActionCommand("ErrorOk");
					test.addActionListener(this);
					Object[] options = {test};
					Icon icon=null;
					JOptionPane optionPane = new JOptionPane(error, JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, icon, options);
					dialog = new JDialog(this, "Error", false);
					dialog = optionPane.createDialog(panel, "Error");
					dialog.setModal(false);
					dialog.show();
				}
			 }
			 catch (ThinClientException exception)
			 {
				 displayError(exception.getMessage());
			 }
			validate();
		}
		//action event fired from the working capital renewal application screen when the save button is clicked.
		else if (actionEvent.getActionCommand().equals("SaveWCRApplication"))
		{
			try
			{
				String error = "";
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				double fb=0;
				double nfb=0;
				
				if (txtBankRefNo.getText().trim().equals(""))
				{
					error = error + "\n" + "Enter Bank Reference Number";
				}
				if (txtBankBranchName.getText().trim().equals(""))
				{
					error = error + "\n" + "Enter Bank Branch Name";
				}
				if (txtSsiValue.getText().trim().equals(""))
				{
					error = error + "\n" + "Enter CGPAN";
				}
				else
				{
					String cgpan=txtSsiValue.getText().trim();
					if (cgpan.length()!=13 || (!cgpan.substring(11, 12).equalsIgnoreCase("W") && !cgpan.substring(11, 12).equalsIgnoreCase("R")))
					{
						error = error + "\n" + "Enter Valid CGPAN";
					}
				}				
				if (!txtWCFundBased.getText().trim().equals(""))
				{
					fb=Double.parseDouble(txtWCFundBased.getText().trim());
				}
				if (!txtWCNonFundBased.getText().trim().equals(""))
				{
					nfb=Double.parseDouble(txtWCNonFundBased.getText().trim());
				}				
				if (fb==0 && nfb==0)
				{
					error = error + "\n" + "Enter Either Renewed Fund Based Amount or Non Fund Based Amount";
				}
				if (fb>0 && (txtRenewedInterest.getText().trim().equals("") || Double.parseDouble(txtRenewedInterest.getText().trim())==0))
				{
					error = error + "\n" + "Enter Renewed Fund Based Interest";
				}
				else if (fb==0 && (!txtRenewedInterest.getText().trim().equals("") && Double.parseDouble(txtRenewedInterest.getText().trim())>0))
				{
					error = error + "\n" + "Renewed Interest cannot be entered if Renewed Fund Based Amount is not entered";
				}
				if (nfb>0 && (txtRenewedCommission.getText().trim().equals("") || Double.parseDouble(txtRenewedCommission.getText().trim())==0))
				{
					error = error + "\n" + "Enter Renewed Non Fund Based Commission";
				}
				else if (nfb==0 && (!txtRenewedCommission.getText().trim().equals("") && Double.parseDouble(txtRenewedCommission.getText().trim())>0))
				{
					error = error + "\n" + "Renewed Commission cannot be entered if Renewed Non Fund Based Amount is not entered";
				}				
				if (txtDtOfRenewal.getText().trim().endsWith("/") || txtDtOfRenewal.getText().trim().equals(""))
				{
					error = error + "\n" + "Enter Date of Renewal";
				}
				else
				{
					Date sancDate = dateFormat.parse(txtDtOfRenewal.getText().trim(), new ParsePosition(0));
					Date currDate = dateFormat.parse(dateFormat.format(new java.util.Date()), new ParsePosition(0));
					if ((currDate.compareTo(sancDate))<0)
					{
						error = error + "\n" + "Date of Renewal should not be later than Current Date";
					}
				}
				if (txtWcPlr.getText().trim().equals("") || Double.parseDouble(txtWcPlr.getText().trim())==0.0)
				{
					error = error + "\n" + "Enter PLR";
				}
				if (txtWcPlrType.getText().trim().equals(""))
				{
					error = error + "\n" + "Enter Type of PLR";
				}				
				if (error.equals(""))
				{
					saveWCRApp();
					displayHomePanel();
				}
				else
				{
					save.setEnabled(false);
					error = "Please Correct the Following:" + error;
					JButton test = new JButton("OK");
					test.setActionCommand("ErrorOk");
					test.addActionListener(this);
					Object[] options = {test};
					Icon icon=null;
					JOptionPane optionPane = new JOptionPane(error, JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, icon, options);
					dialog = new JDialog(this, "Error", false);
					dialog = optionPane.createDialog(panel, "Error");
					dialog.setModal(false);
					dialog.show();
				}
			 }
			 catch (ThinClientException exception)
			 {
				 displayError(exception.getMessage());
			 }
			validate();
		}
		//action event fired from the composite application screen when the save button is clicked.
		else if (actionEvent.getActionCommand().equals("SaveCompositeApplication"))
		{
			try
			{
				if (validateCCBOApp())
				{
					saveCompositeApp();
					displayHomePanel();
				}
			 }
			 catch (ThinClientException exception)
			 {
				 displayError(exception.getMessage());
			 }
			validate();
		}
		//action event fired from the both application screen when the save button is clicked.
		else if (actionEvent.getActionCommand().equals("SaveBothApplication"))
		{
			try
			{
				if (validateBothApp())
				{
					saveBothApp();
					displayHomePanel();
				}
			 }
			 catch (ThinClientException exception)
			 {
				 displayError(exception.getMessage());
			 }
			validate();
		}
		//action event fired from the list screen when a key is choosen
		// from the list displayed.
		else if (actionEvent.getSource() instanceof JRadioButton)
		{
			try
			{
				String actionCommand=(actionEvent.getActionCommand()).toString();
//				System.out.println("filenaem " + actionCommand);
				int firstIndex=actionCommand.indexOf("$");
				int lastIndex=actionCommand.lastIndexOf("$");
				String objType=actionCommand.substring(0,firstIndex);
				String flag=actionCommand.substring(firstIndex+1, firstIndex+4);
				String key=actionCommand.substring(firstIndex+5,lastIndex);
				firstIndex=actionCommand.lastIndexOf("&");
				String fileName=actionCommand.substring(lastIndex+1, actionCommand.length());
/*				if (firstIndex>0)
				{
					fileName=actionCommand.substring(lastIndex+1, firstIndex);
				}
				else
				{
					fileName=actionCommand.substring(lastIndex+1, actionCommand.length());
				}*/
//				System.out.println("filenaem " + fileName);
				if (objType.equals("APP"))
				{
					displayAppFile(key, fileName);
					if (flag.equals("VER"))
					{
						reset.setEnabled(false);
						save.setActionCommand("SaveVerifyApp");
						cancel.setActionCommand("CancelVerifyApp");
					}
					else if (flag.equals("MOD"))
					{
						reset.setEnabled(false);
						save.setActionCommand("SaveModifyApp");
						cancel.setActionCommand("CancelModifyApp");
					}
				}
				else if (objType.equals("PER"))
				{
					if (flag.equals("VER"))
					{
						displayPeriodicInfo(key, fileName, flag);
						reset.setEnabled(false);
						cancel.setActionCommand("CancelVerifyPeriodicInfo");
						validate();
					}
					else if (flag.equals("NEW"))
					{
						displayPeriodicInputPanel(key, fileName, flag);
//						displayPeriodicInfo(key, fileName, flag);
						reset.setEnabled(false);
						cancel.setActionCommand("CancelNewPeriodicInfo");
						validate();
					}
					else if (flag.equals("MOD"))
					{
						displayPeriodicInputPanel(key, fileName, flag);
//						displayPeriodicInfo(key, fileName, flag);
						reset.setEnabled(false);
						cancel.setActionCommand("CancelModifyPeriodicInfo");
						validate();
					}
					else if (flag.equals("REC"))
					{
						try
						{
							displayUpdateRecovery(key, fileName, flag);
							reset.setEnabled(false);
							cancel.setActionCommand("CancelUpdateRecovery");
						}
						catch (ThinClientException exception)
						{
							displayError(exception.getMessage());
						}
						validate();
					}
					else if (flag.equals("VRE"))
					{
						try
						{
							displayUpdateRecovery(key, fileName, flag);
							reset.setEnabled(false);
							cancel.setActionCommand("CancelVerifyRecovery");
						}
						catch (ThinClientException exception)
						{
							displayError(exception.getMessage());
						}
						validate();
					}
				}
				else if (objType.equals("CLAIM1"))
				{
					displayClaimsFIPanel(key, fileName, flag);
					validate();
				}
				else if (objType.equals("CLAIM2"))
				{
					displayClaimsSIPanel(key, fileName, flag);
					validate();
				}
				else if (objType.equals("CLAIM"))
				{
					Hashtable details=readFromFile(fileName);
					com.cgtsi.claim.ClaimApplication claimApplication = (com.cgtsi.claim.ClaimApplication) details.get(key);

					if (claimApplication.getFirstInstallment())
					{
						displayClaimsFIPanel(key, fileName, flag);
					}
					else if (claimApplication.getSecondInstallment())
					{
						displayClaimsSIPanel(key, fileName, flag);
					}
					if (flag.equals("VER"))
					{
						reset.setEnabled(false);
						cancel.setActionCommand("CancelVerifyClaims");
					}
					else if (flag.equals("MOD"))
					{
						reset.setEnabled(false);
						cancel.setActionCommand("CancelVerifyClaims");
					}
					validate();
				}
			}
			catch (ThinClientException exception)
			{
				displayError(exception.getMessage());
			}
			validate();
		}
		else if ((actionEvent.getActionCommand().equals("VerifyPeriodicInfo"))
				|| (actionEvent.getActionCommand().equals("CancelVerifyPeriodicInfo")))
		{
			displayVerifyPanel("PER", "VER");
			ok.setActionCommand("ToVerifyPeriodicInfo");
			validate();
		}
		else if (actionEvent.getActionCommand().equals("ToNewPeriodicInfo"))
		{
			try
			{
				if (validateBeforeDisplayList("PER", "NEW"))
				{
					displayList("PER", "NEW");
					cancel.setActionCommand("NewPeriodicInfo");
				}
			}
			catch (ThinClientException exception)
			{
				displayError(exception.getMessage());
			}
			validate();
		}
		else if ((actionEvent.getActionCommand().equals("NewPeriodicInfo"))
				|| (actionEvent.getActionCommand().equals("CancelNewPeriodicInfo")))
		{
			displayVerifyPanel("PER", "NEW");
			ok.setActionCommand("ToNewPeriodicInfo");
			validate();
		}
		else if (actionEvent.getActionCommand().equals("NewPeriodicNoFile"))
		{
			if (validatePeriodicInput(""))
			{
				displayPeriodicInfo();
			}
			validate();
		}
		else if (actionEvent.getActionCommand().equals("NewPeriodicWithFile") || actionEvent.getActionCommand().equals("ModifyPeriodic"))
		{
			String fileName = lblFileName.getText().trim();
			String key = lblKey.getText().trim();
			String flag = lblFlag.getText().trim();
			
			if (validatePeriodicInput(fileName))
			{
				try
				{
					displayPeriodicInfo(key, fileName, flag);
				}
				catch (ThinClientException exception)
				{
					displayError(exception.getMessage());
				}
			}
			validate();
		}	
		else if ((actionEvent.getActionCommand().equals("ModifyPeriodicInfo"))
				|| (actionEvent.getActionCommand().equals("CancelModifyPeriodicInfo")))
		{
			displayVerifyPanel("PER", "MOD");
			ok.setActionCommand("ToModifyPeriodicInfo");
			validate();
		}
		else if (actionEvent.getActionCommand().equals("ToVerifyPeriodicInfo"))
		{
			try
			{
				displayList("PER", "VER");
				cancel.setActionCommand("VerifyPeriodicInfo");
			}
			catch (ThinClientException exception)
			{
				displayError(exception.getMessage());
			}
			validate();
		}
		else if (actionEvent.getActionCommand().equals("ToModifyPeriodicInfo"))
		{
			try
			{
				displayList("PER", "MOD");
				cancel.setActionCommand("ModifyPeriodicInfo");
			}
			catch (ThinClientException exception)
			{
				displayError(exception.getMessage());
			}
			validate();
		}
		//action fired when the ok button is clicked in the new periodic screen
		else if (actionEvent.getActionCommand().equals("saveNewPeriodicInfo"))
		{
			try
			{
				if (validatePeriodicInfo())
				{
					if (saveVerifiedModifiedDetails("PER", "NEW"))
					{
						displayHomePanel();
					}
				}
			 }
			 catch (ThinClientException exception)
			 {
				 displayError(exception.getMessage());
			 }
			validate();
		}
		//action fired when the ok button is clicked in the modify periodic screen
		else if (actionEvent.getActionCommand().equals("saveModifiedPeriodicInfo"))
		{
//			System.out.println("action perform");
			try
			{
				if (validatePeriodicInfo())
				{
//					System.out.println("validated");
					if (saveVerifiedModifiedDetails("PER", "MOD"))
					{
						displayHomePanel();
					}
				}
			 }
			 catch (ThinClientException exception)
			 {
				 displayError(exception.getMessage());
			 }
			validate();
		}
		//action fired when the ok button is clicked in the verify periodic screen
		else if (actionEvent.getActionCommand().equals("saveVerifiedPeriodicInfo"))
		{
			try
			{
				if (validatePeriodicInfo())
				{
					if (saveVerifiedModifiedDetails("PER", "VER"))
					{
						displayHomePanel();
					}
				}
			 }
			 catch (ThinClientException exception)
			 {
				 displayError(exception.getMessage());
			 }
			validate();
		}
		else if ((actionEvent.getActionCommand().equals("NewFIClaims"))
					|| (actionEvent.getActionCommand().equals("CancelNewFIClaims")))
		{
			displayVerifyPanel("CLAIM1", "NEW");
			ok.setActionCommand("ToNewFIClaims");
			validate();
		}
		else if (actionEvent.getActionCommand().equals("NewClaimsFI"))
		{
			try
			{
				if (validateClaimsInput())
				{
					displayClaimsFIPanel("", "", "NEW");
				}
			}
			catch (ThinClientException exception)
			{
				displayError(exception.getMessage());
			}
			validate();
		}
		else if (actionEvent.getActionCommand().equals("NewClaimsSI"))
		{
			try
			{
				if (validateClaimsInput())
				{
					displayClaimsSIPanel("", "", "NEW");
				}
			}
			catch (ThinClientException exception)
			{
				displayError(exception.getMessage());
			}
			validate();
		}
		else if ((actionEvent.getActionCommand().equals("NewSIClaims"))
					|| (actionEvent.getActionCommand().equals("CancelNewSIClaims")))
		{
			displayVerifyPanel("CLAIM2", "NEW");
			ok.setActionCommand("ToNewSIClaims");
			validate();
		}
		else if ((actionEvent.getActionCommand().equals("VerifyClaims"))
					|| (actionEvent.getActionCommand().equals("CancelVerifyClaims")))
		{
			displayVerifyPanel("CLAIM", "VER");
			ok.setActionCommand("ToVerifyClaims");
			validate();
		}
		else if ((actionEvent.getActionCommand().equals("ModifyClaims"))
					|| (actionEvent.getActionCommand().equals("CancelModifyClaims")))
		{
			displayVerifyPanel("CLAIM", "MOD");
			ok.setActionCommand("ToModifyClaims");
			validate();
		}
		else if (actionEvent.getActionCommand().equals("ToNewFIClaims"))
		{
			try
			{
				if (validateBeforeDisplayList("CLAIM1", "NEW"))
				{
					displayList("CLAIM1", "NEW");
					cancel.setActionCommand("NewFIClaims");
				}
			}
			catch (ThinClientException exception)
			{
				displayError(exception.getMessage());
			}
			validate();
		}
		else if (actionEvent.getActionCommand().equals("ToNewSIClaims"))
		{
			try
			{
				if (validateBeforeDisplayList("CLAIM1", "NEW"))
				{
					displayList("CLAIM2", "NEW");
					cancel.setActionCommand("NewSIClaims");
				}
			}
			catch (ThinClientException exception)
			{
				displayError(exception.getMessage());
			}
			validate();
		}
		else if (actionEvent.getActionCommand().equals("ToVerifyClaims"))
		{
			try
			{
				displayList("CLAIM", "VER");
				cancel.setActionCommand("VerifyClaims");
			}
			catch (ThinClientException exception)
			{
				displayError(exception.getMessage());
			}
			validate();
		}
		else if (actionEvent.getActionCommand().equals("ToModifyClaims"))
		{
			try
			{
				displayList("CLAIM", "MOD");
				cancel.setActionCommand("ModifyClaims");
			}
			catch (ThinClientException exception)
			{
				displayError(exception.getMessage());
			}
			validate();
		}
		else if (actionEvent.getActionCommand().equals("SaveNewFIClaims"))
		{
			try
			{
				//System.out.println("saving new claims");
				if (validateClaims(true))
				{
					if (saveVerifiedModifiedDetails("CLAIM1", "NEW"))
					{
						displayHomePanel();
					}
				}
			 }
			 catch (ThinClientException exception)
			 {
				 displayError(exception.getMessage());
			 }
			validate();
		}
		else if (actionEvent.getActionCommand().equals("SaveNewSIClaims"))
		{
			try
			{
				//System.out.println("saving new claims");
				if (validateClaims(false))
				{
					if (saveVerifiedModifiedDetails("CLAIM2", "NEW"))
					{
						displayHomePanel();
					}
				}
			 }
			 catch (ThinClientException exception)
			 {
				 displayError(exception.getMessage());
			 }
			validate();
		}
		else if (actionEvent.getActionCommand().equals("SaveVerifyFIClaims"))
		{
			try
			{
				//System.out.println("saving verified claims");
				if (validateClaims(true))
				{
					if (saveVerifiedModifiedDetails("CLAIM1", "VER"))
					{
						displayHomePanel();
					}
				}
			 }
			 catch (ThinClientException exception)
			 {
				 displayError(exception.getMessage());
			 }
			validate();
		}
		else if (actionEvent.getActionCommand().equals("SaveVerifySIClaims"))
		{
			try
			{
				//System.out.println("saving verified claims");
				if (validateClaims(false))
				{
					if (saveVerifiedModifiedDetails("CLAIM2", "VER"))
					{
						displayHomePanel();
					}
				}
			 }
			 catch (ThinClientException exception)
			 {
				 displayError(exception.getMessage());
			 }
			validate();
		}
		else if (actionEvent.getActionCommand().equals("SaveModifyFIClaims"))
		{
			try
			{
				//System.out.println("saving modified claims");
				if (validateClaims(true))
				{
					if (saveVerifiedModifiedDetails("CLAIM1", "MOD"))
					{
						displayHomePanel();
					}
				}
			 }
			 catch (ThinClientException exception)
			 {
				 displayError(exception.getMessage());
			 }
			validate();
		}
		else if (actionEvent.getActionCommand().equals("SaveModifySIClaims"))
		{
			try
			{
				//System.out.println("saving modified claims");
				if (validateClaims(false))
				{
					if (saveVerifiedModifiedDetails("CLAIM2", "MOD"))
					{
						displayHomePanel();
					}
				}
			 }
			 catch (ThinClientException exception)
			 {
				 displayError(exception.getMessage());
			 }
			validate();
		}
		/*
			In the Claims screen, in entering recovery action details, if the browse button is pressed to add any file.
		*/
		else if (actionEvent.getActionCommand().indexOf("BrowseAttachement") != -1)
		{
			String actionCommand = actionEvent.getActionCommand();
			int index = actionCommand.indexOf("-");
			int buttonIndex = Integer.parseInt(actionCommand.substring(index+1));

			filePath=new JFileChooser();
			try
			{
				File f = new File(new File(".").getCanonicalPath());
				filePath.setCurrentDirectory(f);
			}
			catch (IOException ioException)
			{}

			int selected=filePath.showOpenDialog(panel);
			if (selected==JFileChooser.APPROVE_OPTION){
				File file=filePath.getSelectedFile();
				String fileName=file.getAbsolutePath();
				txtAttachment[buttonIndex].setText(fileName);
			}
			else if (selected==JFileChooser.CANCEL_OPTION){
				txtAttachment[buttonIndex].setText("");
			}
		}
		else if (actionEvent.getActionCommand().equals("UpdateRecoveryDetails") || actionEvent.getActionCommand().equals("CancelUpdateRecovery"))
		{
			displayVerifyPanel("PER", "REC");
			ok.setActionCommand("ToUpdateRecovery");
			validate();
		}
		else if (actionEvent.getActionCommand().equals("ToUpdateRecovery"))
		{
			try
			{
				displayList("PER", "REC");
				cancel.setActionCommand("UpdateRecoveryDetails");
			}
			catch (ThinClientException exception)
			{
				displayError(exception.getMessage());
			}
			validate();
		}
		else if (actionEvent.getActionCommand().equals("VerifyRecoveryDetails") || actionEvent.getActionCommand().equals("CancelVerifyRecovery"))
		{
			displayVerifyPanel("PER", "VERREC");
			ok.setActionCommand("ToVerifyRecovery");
			validate();
		}
		else if (actionEvent.getActionCommand().equals("ToVerifyRecovery"))
		{
			try
			{
				displayList("PER", "VRE");
				cancel.setActionCommand("VerifyRecoveryDetails");
			}
			catch (ThinClientException exception)
			{
				displayError(exception.getMessage());
			}
			validate();
		}
		else if (actionEvent.getActionCommand().equals("ExitTC"))
		{
//			System.out.println("here");
			int confirmValue = JOptionPane.showConfirmDialog(this, "Do you want to Exit? If you choose Yes, all unsaved Data will be discarded.", "Confirm Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (confirmValue==0)
			{
				System.exit(0);
			}
		}
		else if (actionEvent.getActionCommand().equals("ArchiveAppFiles"))
		{
			int confirmValue = JOptionPane.showConfirmDialog(this, "Do you want to continue Archival?", "Confirm Archival", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (confirmValue==0)
			{
				try
				{
					if (archiveFiles("APP"))
					{
						displayHomePanel();
					}
				}catch (Exception exp)
				{
					displayError(exp.getMessage());
				}
				validate();
			}
		}
		else if (actionEvent.getActionCommand().equals("ArchivePerFiles"))
		{
			int confirmValue = JOptionPane.showConfirmDialog(this, "Do you want to continue Archival?", "Confirm Archival", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (confirmValue==0)
			{
				try
				{
					if (archiveFiles("PER"))
					{
						displayHomePanel();
					}
				}catch (Exception exp)
				{
					displayError(exp.getMessage());
				}
				validate();
			}
		}
		else if (actionEvent.getActionCommand().equals("ArchiveClmFiles"))
		{
			int confirmValue = JOptionPane.showConfirmDialog(this, "Do you want to continue Archival?", "Confirm Archival", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (confirmValue==0)
			{
				try
				{
					if (archiveFiles("CLAIM"))
					{
						displayHomePanel();
					}
				}catch (Exception exp)
				{
					displayError(exp.getMessage());
				}
				validate();
			}
		}
		else if (actionEvent.getActionCommand().equals("PrintWCApplication"))
		{
			print.setEnabled(false);
			try
			{
				print("WC");
				print.setEnabled(true);
			}
			catch(IOException ioe)
			{
				displayError(ioe.getMessage());
			}
			catch(InterruptedException ie)
			{
				displayError(ie.getMessage());
			}
		}
		else if (actionEvent.getActionCommand().equals("PrintTCApplication"))
		{
			print.setEnabled(false);
			try
			{
				print("TC");
				print.setEnabled(true);
			}
			catch(IOException ioe)
			{
				displayError(ioe.getMessage());
			}
			catch(InterruptedException ie)
			{
				displayError(ie.getMessage());
			}
		}
		else if (actionEvent.getActionCommand().equals("PrintCCApplication"))
		{
			print.setEnabled(false);
			try
			{
				print("CC");
				print.setEnabled(true);
			}
			catch(IOException ioe)
			{
				displayError(ioe.getMessage());
			}
			catch(InterruptedException ie)
			{
				displayError(ie.getMessage());
			}
		}
		else if (actionEvent.getActionCommand().equals("PrintBOApplication"))
		{
			print.setEnabled(false);
			try
			{
				print("BO");
				print.setEnabled(true);
			}
			catch(IOException ioe)
			{
				displayError(ioe.getMessage());
			}
			catch(InterruptedException ie)
			{
				displayError(ie.getMessage());
			}
		}
		else if (actionEvent.getActionCommand().equals("PrintAddTCApplication"))
		{
			print.setEnabled(false);
			try
			{
				print("ATC");
				print.setEnabled(true);
			}
			catch(IOException ioe)
			{
				displayError(ioe.getMessage());
			}
			catch(InterruptedException ie)
			{
				displayError(ie.getMessage());
			}
		}
		else if (actionEvent.getActionCommand().equals("PrintWCEApplication"))
		{
			print.setEnabled(false);
			try
			{
				print("WCE");
				print.setEnabled(true);
			}
			catch(IOException ioe)
			{
				displayError(ioe.getMessage());
			}
			catch(InterruptedException ie)
			{
				displayError(ie.getMessage());
			}
		}
		else if (actionEvent.getActionCommand().equals("PrintWCRApplication"))
		{
			print.setEnabled(false);
			try
			{
				print("WCR");
				print.setEnabled(true);
			}
			catch(IOException ioe)
			{
				displayError(ioe.getMessage());
			}
			catch(InterruptedException ie)
			{
				displayError(ie.getMessage());
			}
		}
		else if (actionEvent.getActionCommand().equals("PrintClaimsFIApplication"))
		{
			print.setEnabled(false);
			try
			{
				print("CL1");
				print.setEnabled(true);
			}
			catch(IOException ioe)
			{
				displayError(ioe.getMessage());
			}
			catch(InterruptedException ie)
			{
				displayError(ie.getMessage());
			}
		}
		else if (actionEvent.getActionCommand().equals("PrintClaimsSIApplication"))
		{
			print.setEnabled(false);
			try
			{
				print("CL2");
				print.setEnabled(true);
			}
			catch(IOException ioe)
			{
				displayError(ioe.getMessage());
			}
			catch(InterruptedException ie)
			{
				displayError(ie.getMessage());
			}
		}
		else if (actionEvent.getActionCommand().equals("PrintPerApplication"))
		{
			print.setEnabled(false);
			try
			{
				print("PER");
				print.setEnabled(true);
			}
			catch(IOException ioe)
			{
				displayError(ioe.getMessage());
			}
			catch(InterruptedException ie)
			{
				displayError(ie.getMessage());
			}
		}
		else if (actionEvent.getActionCommand().equalsIgnoreCase("ErrorOk"))
		{
			save.setEnabled(true);
			dialog.dispose();
		}
		else if (actionEvent.getActionCommand().equalsIgnoreCase("HelpThinClient"))
		{
			displayHelp();
			validate();
		}
		else if (actionEvent.getActionCommand().equalsIgnoreCase("AboutThinClient"))
		{
//			System.out.println("About.. ");
			displayAboutThinClient();
			validate();
		}
		else if (actionEvent.getActionCommand().equalsIgnoreCase("CloseAbtDialog"))
		{
//			System.out.println("ss ");
			
//			abtDialog.removeAll();
			abtDialog.dispose();
			validate();
		}
	}
	
	public void itemStateChanged(ItemEvent ie)
	{
		String actionCommand = ((JCheckBox)ie.getItem()).getActionCommand();
		int index = actionCommand.indexOf("-");
		int disIndex = Integer.parseInt(actionCommand.substring(0,index));
		int totalDis = Integer.parseInt(actionCommand.substring(index+1,actionCommand.length()));
		
//		System.out.println("index " + disIndex);
//		System.out.println("total " + totalDis);
		
		if (chkFinalDisbursement[disIndex].isSelected())
		{
			for (int i=disIndex+1;i<(disIndex+totalDis);i++)
			{
//				System.out.println("index " + i);
				if (txtDisbursementAmt[i]!=null)
				{
					txtDisbursementAmt[i].setText("");
					txtDisbursementDate[i].setText("");
					chkFinalDisbursement[i].setSelected(false);
					txtDisbursementAmt[i].setEnabled(false);
					txtDisbursementDate[i].setEnabled(false);
					chkFinalDisbursement[i].setEnabled(false);
				}
			}
		}
		else
		{
			for (int i=disIndex+1;i<(disIndex+totalDis);i++)
			{
				if (txtDisbursementAmt[i]!=null)
				{
					txtDisbursementAmt[i].setText("");
					txtDisbursementDate[i].setText("");
					chkFinalDisbursement[i].setSelected(false);
					txtDisbursementAmt[i].setEnabled(true);
					txtDisbursementDate[i].setEnabled(true);
					chkFinalDisbursement[i].setEnabled(true);
				}
			}
		}
	}
	
	private void checkFinalDis(String actionCommand)
	{
		int index = actionCommand.indexOf("-");
		int disIndex = Integer.parseInt(actionCommand.substring(0,index));
		int totalDis = Integer.parseInt(actionCommand.substring(index+1,actionCommand.length()));
		
//		System.out.println("index " + disIndex);
//		System.out.println("total " + totalDis);
		
		if (chkFinalDisbursement[disIndex].isSelected())
		{
			for (int i=disIndex+1;i<(disIndex+totalDis);i++)
			{
//				System.out.println("index " + i);
				if (txtDisbursementAmt[i]!=null)
				{
					txtDisbursementAmt[i].setText("");
					txtDisbursementDate[i].setText("");
					chkFinalDisbursement[i].setSelected(false);
					txtDisbursementAmt[i].setEnabled(false);
					txtDisbursementDate[i].setEnabled(false);
					chkFinalDisbursement[i].setEnabled(false);
				}
			}
		}
		else
		{
			for (int i=disIndex+1;i<(disIndex+totalDis);i++)
			{
				if (txtDisbursementAmt[i]!=null)
				{
					txtDisbursementAmt[i].setText("");
					txtDisbursementDate[i].setText("");
					chkFinalDisbursement[i].setSelected(false);
					txtDisbursementAmt[i].setEnabled(true);
					txtDisbursementDate[i].setEnabled(true);
					chkFinalDisbursement[i].setEnabled(true);
				}
			}
		}
	}

	/**
	*
	*/
	private void showFileChooser(String type, String flag)
	{
		filePath=new JFileChooser();
		try
		{
			File f = new File(new File(".").getCanonicalPath());
			filePath.setCurrentDirectory(f);
		}
		catch (IOException ioException)
		{}

		if ((type.equals("APP")) && ((flag.equals("VER"))|| (flag.equals("MOD"))))
		{
			filePath.setFileFilter(new AppFileNewFilter());
		}
		if (type.equals("PER"))
		{
			if ((flag.equals("VER"))|| (flag.equals("MOD")) || flag.equals("VERREC"))
			{
				filePath.setFileFilter(new PeriodicFileNewFilter());
			}
			if (flag.equals("NEW") || flag.equals("REC"))
			{
				filePath.setFileFilter(new PeriodicFileExpFilter());
			}
		}
		if (type.equals("CLAIM") || type.equals("CLAIM1") || type.equals("CLAIM2"))
		{
			if ((flag.equals("VER"))|| (flag.equals("MOD")))
			{
				filePath.setFileFilter(new ClaimFileNewFilter());
			}
			if (flag.equals("NEW"))
			{
				filePath.setFileFilter(new ClaimFileExpFilter());
			}
		}
		int selected=filePath.showOpenDialog(panel);
		if (selected==JFileChooser.APPROVE_OPTION){
			File file=filePath.getSelectedFile();
			String fileName=file.getAbsolutePath();
			txtFilePath.setText(fileName);
		}
		else if (selected==JFileChooser.CANCEL_OPTION){
			txtFilePath.setText("");
		}
	}

	/**
	 * This method reads a file districts.txt and returns all the states.
	 */
	private ArrayList getStates()
	{
		ArrayList states = new ArrayList();
		try
		{
			File file = new File(".\\master files\\districts.txt");
			FileReader fileReader = new FileReader(file);
			BufferedReader buffReader = new BufferedReader(fileReader);

			StringTokenizer statesToken;

			String line = "";
			states.add("Select");
			while (line != null)
			{
				if (!(line.equals("")))
				{
					statesToken = new StringTokenizer(line, "|");
					states.add(statesToken.nextToken());
				}
				line = buffReader.readLine();
			}
			buffReader.close();
			fileReader.close();
		}
		catch (FileNotFoundException fileNotFoundException)
		{
//			System.out.println(fileNotFoundException.getMessage());
		}
		catch (IOException ioException)
		{
//			System.out.println(ioException.getMessage());
		}
		return states;
	}

	/**
	 * This method returns the districts for a particular state.
	 */
	private ArrayList getDistricts(String state)
	{
		ArrayList districts = new ArrayList();
		try
		{
			File file = new File(".\\master files\\districts.txt");
			FileReader fileReader = new FileReader(file);
			BufferedReader buffReader = new BufferedReader(fileReader);

			StringTokenizer districtsToken;

			String line = "";
			districts.add("Select");
			while (line != null)
			{
				if (!(line.equals("")))
				{
					districtsToken = new StringTokenizer(line, "|");
					if ((districtsToken.nextToken()).equals(state))
					{
						while (districtsToken.hasMoreTokens())
						{
							districts.add(districtsToken.nextToken());
						}
						districts.add("Others");
						return districts;
					}
				}
				line = buffReader.readLine();
			}
			districts.add("Others");
			buffReader.close();
			fileReader.close();
		}
		catch (FileNotFoundException fileNotFoundException)
		{
//			System.out.println(fileNotFoundException.getMessage());
		}
		catch (IOException ioException)
		{
//			System.out.println(ioException.getMessage());
		}
		return districts;
	}

	/**
	 * This method retrieves the industry natures from the file.
	 */
	private ArrayList getIndustryNatures()
	{
		ArrayList indNatures = new ArrayList();
		try
		{
			File file = new File(".\\master files\\industrysectors.txt");
			FileReader fileReader = new FileReader(file);
			BufferedReader buffReader = new BufferedReader(fileReader);

			StringTokenizer indNaturesToken;

			String line = "";
			indNatures.add("Select");
			while (line != null)
			{
				if (!(line.equals("")))
				{
					indNaturesToken = new StringTokenizer(line, "|");
					indNatures.add(indNaturesToken.nextToken());
				}
				line = buffReader.readLine();
			}
			buffReader.close();
			fileReader.close();
		}
		catch (FileNotFoundException fileNotFoundException)
		{
//			System.out.println(fileNotFoundException.getMessage());
		}
		catch (IOException ioException)
		{
//			System.out.println(ioException.getMessage());
		}
		return indNatures;
	}

	/**
	 * This method rettrieves the industry sectors for a particular industry nature.
	 */
	private ArrayList getIndSectors(String indNature)
	{
		ArrayList indSectors = new ArrayList();
		try
		{
			File file = new File(".\\master files\\industrysectors.txt");
			FileReader fileReader = new FileReader(file);
			BufferedReader buffReader = new BufferedReader(fileReader);

			StringTokenizer indSectorsToken;

			String line = "";
			indSectors.add("Select");
			while (line != null)
			{
				if (!(line.equals("")))
				{
					indSectorsToken = new StringTokenizer(line, "|");
					if ((indSectorsToken.nextToken()).equals(indNature))
					{
						while (indSectorsToken.hasMoreTokens())
						{
							indSectors.add(indSectorsToken.nextToken());
						}
						return indSectors;
					}
				}
				line = buffReader.readLine();
			}
			buffReader.close();
			fileReader.close();
		}
		catch (FileNotFoundException fileNotFoundException)
		{
//			System.out.println(fileNotFoundException.getMessage());
		}
		catch (IOException ioException)
		{
//			System.out.println(ioException.getMessage());
		}
		return indSectors;
	}

	/**
	 * This method retrieves the social categories from the file.
	 */
	private ArrayList getSocialCategories()
	{
		ArrayList socialCategories = new ArrayList();
		try
		{
			File file = new File(".\\master files\\socialcategories.txt");
			FileReader fileReader = new FileReader(file);
			BufferedReader buffReader = new BufferedReader(fileReader);

			StringTokenizer socialCategoriesToken;

			String line = "";
			socialCategories.add("Select");
			while (line != null)
			{
				if (!(line.equals("")))
				{
					socialCategoriesToken = new StringTokenizer(line, "|");
					socialCategories.add(socialCategoriesToken.nextToken());
				}
				line = buffReader.readLine();
			}
			buffReader.close();
			fileReader.close();
		}
		catch (FileNotFoundException fileNotFoundException)
		{
//			System.out.println(fileNotFoundException.getMessage());
		}
		catch (IOException ioException)
		{
//			System.out.println(ioException.getMessage());
		}
		return socialCategories;
	}

	/**
	* This method reads the file MCGFDetails.txt and returns the mcgf ids.
	*/
	private ArrayList getMcgfIds() throws ThinClientException
	{
		ArrayList mcgfIds = new ArrayList();
		try
		{
			File file = new File(".\\MCGF\\MCGFDetails.txt");
			FileReader fileReader = new FileReader(file);
			BufferedReader buffReader = new BufferedReader(fileReader);

			StringTokenizer mcgfIdsToken;

			String line = "";
			mcgfIds.add("Select");
			while (line != null)
			{
				if (!(line.equals("")))
				{
					mcgfIdsToken = new StringTokenizer(line, "|");
					mcgfIds.add(mcgfIdsToken.nextToken());
				}
				line = buffReader.readLine();
			}
			buffReader.close();
			fileReader.close();
		}
		catch (FileNotFoundException fileNotFoundException)
		{
//			System.out.println(fileNotFoundException.getMessage());
			throw new ThinClientException("File Not Found");
		}
		catch (IOException ioException)
		{
//			System.out.println(ioException.getMessage());
			throw new ThinClientException("Error Reading MCGF Details");
		}
		return mcgfIds;
	}

	/**
	* This method returns the participating bank names for the mcgf id passed.
	* The element of the arraylist stores the mcgf name corresponding to the mcgf id.
	* The rest elements are the participating bank names.
	*/
	private ArrayList getParticipatingBankNames(String mcgfId)
	{
		ArrayList partBankNames = new ArrayList();
		try
		{
			File file = new File(".\\MCGF\\MCGFDetails.txt");
			FileReader fileReader = new FileReader(file);
			BufferedReader buffReader = new BufferedReader(fileReader);

			StringTokenizer partBanksToken;

			String line = "";
			while (line != null)
			{
				if (!(line.equals("")))
				{
					partBanksToken = new StringTokenizer(line, "|");
					if ((partBanksToken.nextToken()).equals(mcgfId))
					{
						partBankNames.add(partBanksToken.nextToken());		// mcgf name
						partBankNames.add("Select");
						while (partBanksToken.hasMoreTokens())
						{
							partBankNames.add(partBanksToken.nextToken());
						}
						return partBankNames;
					}
				}
				line = buffReader.readLine();
			}
			buffReader.close();
			fileReader.close();
		}
		catch (FileNotFoundException fileNotFoundException)
		{
//			System.out.println(fileNotFoundException.getMessage());
		}
		catch (IOException ioException)
		{
//			System.out.println(ioException.getMessage());
		}
		return partBankNames;
	}

	/**
	 * This method returns the current date in the format ddmmyyyy.
	 */
	private String getDateString()
	{
		String dateString="";
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
		dateString = dateFormat.format(date);

		return dateString;
	}

	/**
	 * This method returns the file name.
	 * the parameter sFlag denotes if the file is of extn NEW or VER.
	 * the parameter sType denotes if the file is for an application or periodic info or claims.
	 * APP - application
	 * PER - periodic info
	 * CLAIMS - claims
	 * If in the directory NEW or VER, there are more than one file,
	 *		the method will return the latest file created.
	 * If the size of the latest file created, is more than 1.44 mb,
	 *		the method will create a new file and return the file name.
	 */
	private String getFileName(String sType, String sFlag, String branchName)
	{
		File file=new File(sFlag);
		int fileNo=0;
		String name="";
		String nameToReturn="";
		String temp=getDateString()+ "-" + sType;
		String fileName="";

		if (file.exists())
		{
			String fileNames[]=file.list();
			int arrSize=fileNames.length;
			int i, j=0;
			for (i=0;i<arrSize;i++)
			{
				name=fileNames[i];
				if (name.indexOf(temp)>=0)
				{
					j = Integer.parseInt(name.charAt(name.indexOf(".")-1)+"");
					if (j > fileNo)
					{
						fileNo = j;
						fileName=name.substring(0,name.indexOf(temp)-1);
					}
				}
			}
		}
		else
		{
			file.mkdir();
		}

//		nameToReturn = sFlag + "\\" + temp;
//		System.out.println("fileno " + fileNo);

		if (fileNo == 0)
		{
			nameToReturn = sFlag + "\\" + branchName + "-" + temp;
			nameToReturn = nameToReturn + "1." + sFlag;
		}
		else
		{
			nameToReturn = sFlag + "\\" + fileName + "-" + temp;
			nameToReturn = nameToReturn + fileNo + "." + sFlag;
		}

		if (fileSizeLimit(nameToReturn))
		{
			fileNo++;
			nameToReturn=sFlag + "\\" + fileName + "-" + temp + fileNo + "." + sFlag;
		}
		
//		System.out.println("from getfilename " + nameToReturn);
		return nameToReturn;
	}

	/**
	 * This method writes the object to a file in case of verified or modified operations.
	 * The parameters
	 *		object - object to be written to the file
	 *		objType - type of the object APP - application
	 *									 PER - periodic info
	 *									 CLAIM - claims
	 *		key - key to identify the object in the existing file
	 *		fileName - name of the file in the which the object is present
	 *		flag - VER for Verify
	 *			   MOD for Modify
	 */
	 private void writeVerifiedModifiedDetails(Object object, String objType, String key, String fileName, String flag) throws ThinClientException
	 {
		 Hashtable files=readFromFile(fileName);
//		 System.out.println("file name  " + fileName);
//		System.out.println("file name  " + fileName.lastIndexOf('\\'));
//		System.out.println("file name  " + fileName.indexOf(getDateString()));
		String branchName ="";
		
		if (flag.equalsIgnoreCase("VER") || flag.equalsIgnoreCase("MOD"))
		{
			int start=fileName.lastIndexOf('\\');
			int index=fileName.indexOf("-");
			int lastIndex=fileName.lastIndexOf("-");
//			System.out.println("index  " + index);
//			System.out.println("last index  " + lastIndex);
			int end=index;
			String temp = fileName.substring(index+1);
//			System.out.println("temp  " + temp);
			while (index!=lastIndex)
			{
				end=index;
				index=fileName.indexOf("-", index+1);
			}
			 branchName = fileName.substring(start+1, end);
		}
		else
		{
			branchName=lblBrnName.getText().trim();
		}
//		System.out.println("branch name  " + branchName);

		 if (flag.equals("VER"))
		 {
			 String verFileName=getFileName(objType, flag, branchName);
			 Hashtable verFiles=new Hashtable();
			 try
			 {
				
				 verFiles=readFromFile(verFileName);
			 }
			 catch (ThinClientException exception)
			 {
				 if ( ! exception.getMessage().equals("File Not Found"))
				 {
					 throw exception;
				 }
			 }
			 if (! verFiles.isEmpty())
			 {
				int confirmValue = JOptionPane.showConfirmDialog(panel, "Verify File Already exists. Click Yes to add to the file. Click No to overwrite the File", "Confirm Verification", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (confirmValue==1)
				{
					verFiles.clear();
				}
			 }
			 verFiles.put(key, object);
			 files.remove(key);
			 writeDetails(verFileName, verFiles);
			 checkRewriteFile(verFileName, key, object);
			 System.out.println(fileName + " " + files.isEmpty());
			 if (files.size()==1 && files.containsKey("version"))
			 {
			 	File file = new File(fileName);
			 	System.out.println(fileName + " " + file.exists());
			 	if (!file.delete())
			 	{
			 		throw new ThinClientException("Could not delete empty .NEW File.");
			 	}
			 }
			 else
			 {
				writeDetails(fileName, files);
				checkRewriteFile(fileName, key, object);
			 }
		 }
		 else if (flag.equals("MOD"))
		 {
			 files.put(key, object);
			 writeDetails(fileName, files);
			 checkRewriteFile(fileName, key, object);
		 }
		 else if (flag.equals("NEW"))
		 {
			 String newFileName=getFileName(objType, flag, branchName);
			 Hashtable newFiles=new Hashtable();
			 try
			 {
				 newFiles=readFromFile(newFileName);
			 }
			 catch (ThinClientException exception)
			 {
				 if ( ! exception.getMessage().equals("File Not Found"))
				 {
					 throw exception;
				 }
			 }
			 newFiles.put(key, object);
//			 files.remove(key);
			 try
			 {
				 writeDetails(newFileName, newFiles);
			 }
			 catch (ThinClientException exception)
			 {
				 if ( ! exception.getMessage().equals("File Not Found"))
				 {
					 throw exception;
				 }
			 }
			 checkRewriteFile(newFileName, key, object);
		 }
	 }

	 /**
	  * This method checks for the file size and rewrites the file if neccessary.
	  * The parameters
	  *		fileName - file to be checked
	  *		key - the key of the latest object that was written into the file.
	  *		object - latest object written into the file
	  */
	  private void checkRewriteFile(String fileName, String key, Object object) throws ThinClientException
		{
			if (fileSizeLimit(fileName))
			{
				Hashtable files=readFromFile(fileName);
				files.remove(key);

				int fileNo=Integer.parseInt(fileName.charAt(fileName.indexOf("."))+"");
				int newFileNo=fileNo+1;

				writeDetails(fileName, files);

				fileName=fileName.replaceAll(fileNo+".", newFileNo+".");
				files.clear();
				files.put(key, object);
				writeDetails(fileName, files);
			}
		}
		
	private boolean archiveFiles(String type) throws ThinClientException
	{
		File file = new File("VER");
		File floppyFile = new File("a:\\");

//		System.out.println("type " + type);
		
		File temp1 = new File("Archive");
		if (!temp1.exists())
		{
			temp1.mkdir();
		}
		temp1=null;

		String archName="Archive\\"; 
		if (type.equalsIgnoreCase("APP"))
		{
			archName = archName + "Application";
		}
		else if (type.equalsIgnoreCase("PER"))
		{
			archName = archName + "PeriodicInfo";
		}
		else if (type.equalsIgnoreCase("CLAIM"))
		{
			archName = archName + "Claims";
		}
//		System.out.println("arch name 1 " + archName);
		File archiveFile = new File(archName);
//		System.out.println("arch name 2 " + archiveFile.exists());
		
		try
		{
			if (file.exists())
			{
				if (!archiveFile.exists())
				{
					archiveFile.mkdir();
				}
//				System.out.println("arch name 3 " + archiveFile.exists());
				File[] files = file.listFiles();
				int len = files.length;
				
				if (len ==0)
				{
					JOptionPane.showMessageDialog(this, "No files available to Archive", "Message", JOptionPane.INFORMATION_MESSAGE);
					return false;
				}
				
				for (int i=0;i<len;i++)
				{
					File temp = (File)files[i];
					String fileName = temp.getName();
//					System.out.println("filename " + fileName);
					if (fileName.indexOf(type)>0 && fileName.endsWith(".VER"))
					{
						String newFileName = "Archive\\";
						
						if (type.equalsIgnoreCase("APP"))
						{
							newFileName = newFileName + "Application\\";
						}
						else if (type.equalsIgnoreCase("PER"))
						{
							newFileName = newFileName + "PeriodicInfo\\";
						}
						else if (type.equalsIgnoreCase("CLAIM"))
						{
							newFileName = newFileName + "Claims\\";
						}
						
						newFileName = newFileName + fileName.substring(0,fileName.length()-4)+".ARC";
						System.out.println("filename 1 " + newFileName);
	
						Hashtable newFiles=new Hashtable();
						try
						{
							newFiles=readFromFile(temp.toString());
							Set keys = newFiles.keySet();
							Iterator iterator = keys.iterator();
							Object object=new Object();
//							System.out.println(newFiles.size());
							while (iterator.hasNext())
							{
								String key =(String) iterator.next();
								object = newFiles.get(key);
//								System.out.println("type " + object.getClass());
								if ((type.equalsIgnoreCase("APP") && object instanceof com.cgtsi.application.Application) ||
									(type.equalsIgnoreCase("PER") && object instanceof com.cgtsi.guaranteemaintenance.PeriodicInfo) ||
									(type.equalsIgnoreCase("CLAIM") && object instanceof com.cgtsi.claim.ClaimApplication))
								{
//									System.out.println("type " + object.getClass());
									File file1 = new File(newFileName);
									writeDetails(newFileName, newFiles);
//									System.out.println("after writing");
									break;
								}

							}
/*							System.out.println((String) object);
							System.out.println(type.equalsIgnoreCase("CLAIM"));
							System.out.println(object instanceof com.cgtsi.claim.ClaimApplication);
							if ((type.equalsIgnoreCase("APP") && object instanceof com.cgtsi.application.Application) ||
								(type.equalsIgnoreCase("PER") && object instanceof com.cgtsi.guaranteemaintenance.PeriodicInfo) ||
								(type.equalsIgnoreCase("CLAIM") && object instanceof com.cgtsi.claim.ClaimApplication))
							{
							}*/
						}
						catch (ThinClientException exception)
						{
//							System.out.println(exception.getMessage());
							throw exception;
						}
						catch(Exception exp)
						{
//							System.out.println(exp.getMessage());
							throw new ThinClientException("Error in File " + temp.toString());
						}
					}
				}
				for (int i=0;i<len;i++)
				{
					File temp = (File)files[i];
					String fileName = temp.getName();
//					System.out.println("temp 1 " + temp.getAbsolutePath());
//					System.out.println("temp 2 " + fileName);
					if (fileName.indexOf(type)>0 && fileName.endsWith(".VER"))
					{
						Hashtable newFiles=new Hashtable();
						try
						{
							newFiles=readFromFile(temp.toString());
							Set keys = newFiles.keySet();
							Iterator iterator = keys.iterator();
							Object object=new Object();
							while (iterator.hasNext())
							{
								String key =(String) iterator.next();
								object = newFiles.get(key);
//								System.out.println("type 2 " + object.getClass());
								if ((type.equalsIgnoreCase("APP") && object instanceof com.cgtsi.application.Application) ||
									(type.equalsIgnoreCase("PER") && object instanceof com.cgtsi.guaranteemaintenance.PeriodicInfo) ||
									(type.equalsIgnoreCase("CLAIM") && object instanceof com.cgtsi.claim.ClaimApplication))
								{
//									System.out.println("deleting " + temp.getAbsolutePath());
									temp.delete();
									break;
								}
							}
						}
						catch (ThinClientException exception)
						{
							throw exception;
						}
					}
				}

				if (floppyFile.exists())
				{
					files = archiveFile.listFiles();
					len=files.length;			
					for (int i=0;i<len;i++)
					{
						File temp = (File)files[i];
						String fileName = temp.getName();
//						String newFileName = "A:\\" + archName + "\\" + fileName;
						String newFileName = "A:\\" + fileName;
	
						if (fileName.indexOf(type)>0 && fileName.endsWith(".ARC"))
						{
							Hashtable newFiles=new Hashtable();
							try
							{
								newFiles=readFromFile(temp.toString());
								Set keys = newFiles.keySet();
								Iterator iterator = keys.iterator();
								Object object=new Object();
								while (iterator.hasNext())
								{
									String key =(String) iterator.next();
									object = newFiles.get(key);
									if ((type.equalsIgnoreCase("APP") && object instanceof com.cgtsi.application.Application) ||
										(type.equalsIgnoreCase("PER") && object instanceof com.cgtsi.guaranteemaintenance.PeriodicInfo) ||
										(type.equalsIgnoreCase("CLAIM") && object instanceof com.cgtsi.claim.ClaimApplication))
									{
										writeDetails(newFileName, newFiles);
										break;
									}
								}
							}
							catch (ThinClientException exception)
							{
								throw exception;
							}
						}
					}
				}
				else
				{
					JOptionPane.showMessageDialog(this, "Archival Successfull. Floppy does not Exist to Copy.", "Message", JOptionPane.WARNING_MESSAGE);
				}
				return true;
			}
			else
			{
				JOptionPane.showMessageDialog(this, "No files available to Archive", "Message", JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		}catch (Exception e)
		{
			throw new ThinClientException(e.getMessage());
		}
	}
	
	private String getVersion() throws ThinClientException
	{
		String version = "";
		
		File file = new File("Version.properties");
		try
		{
			Properties versionProp=new Properties();
			File versionFile = new File(file.getAbsolutePath());
			try
			{
				FileInputStream fin = new FileInputStream(versionFile);
				versionProp.load(fin);
			}
			catch(FileNotFoundException fe)
			{
				throw new ThinClientException("Could not load Version.");
			}
			catch(IOException ie)
			{
				throw new ThinClientException("Could not load Version.");
			}
			
			version = versionProp.getProperty("version");
			
//			System.out.println("version " + version);
		}
		catch(Exception exp)
		{
			throw new ThinClientException(exp.getMessage());
		}
		return version;
	}
	
	private void print(String type) throws IOException, InterruptedException
	{
		String fileName="";
		if (type.equalsIgnoreCase("WC"))
		{
			fileName="Screens\\WCAPP.html";
		}
		else if (type.equalsIgnoreCase("TC"))
		{
			fileName="Screens\\TCAPP.html";
		}
		else if (type.equalsIgnoreCase("CC"))
		{
			fileName="Screens\\COMPAPP.html";
		}
		else if (type.equalsIgnoreCase("BO"))
		{
			fileName="Screens\\BOTHAPP.html";
		}
		else if (type.equalsIgnoreCase("ATC"))
		{
			fileName="Screens\\AddTCAPP.html";
		}
		else if (type.equalsIgnoreCase("WCE"))
		{
			fileName="Screens\\WCEAPP.html";
		}
		else if (type.equalsIgnoreCase("WCR"))
		{
			fileName="Screens\\WCRAPP.html";
		}
		else if (type.equalsIgnoreCase("CL1"))
		{
			fileName="Screens\\FIRSTCLAIMAPP.html";
		}
		else if (type.equalsIgnoreCase("CL2"))
		{
			fileName="Screens\\SECONDCLAIMAPP.html";
		}
		else if (type.equalsIgnoreCase("PER"))
		{
			fileName="Screens\\PERIODICINFO.html";
		}
		File file=new File(fileName);
		//String seperator = File.pathSeparator;
//		System.out.println("exists "  + file.exists());
		
		if(!file.exists())
		{
			//show error message...
		}
		
//		System.out.println("abs path "  + file.getAbsolutePath());
		//fileName = file.getAbsolutePath().replaceAll("\\", seperator);
//		System.out.println("name  " +fileName);
		Runtime runtime=Runtime.getRuntime();
		print.setEnabled(false);
		Process process = runtime.exec("print "+file.getAbsolutePath());
//		System.out.println(process.exitValue());
		if (process.waitFor()==0)
		{
			print.setEnabled(true);
		}
		
		//Thread.sleep(10000);
	}

	class StateListener implements ItemListener
	{
		public void itemStateChanged(ItemEvent ie)
		{
			String text = (String) ie.getItem();
			ArrayList districts = new ArrayList();
			if (!text.equals("Select"))
			{
				districts = getDistricts(text);
			}
			else
			{
				districts.add("Select");
			}
			int size = districts.size();
			int i = 0;
			cmbDistrict.removeAllItems();

			for (i=0;i<size;i++)
			{
				cmbDistrict.addItem(districts.get(i));
			}
		}
	}

	class DistrictListener implements ItemListener
	{
		public void itemStateChanged(ItemEvent ie)
		{
			String text = (String) ie.getItem();
			if (text.equals("Others"))
			{
				txtDistrict.setEnabled(true);
			}
			else
			{
				txtDistrict.setText("");
				txtDistrict.setEnabled(false);
			}
		}
	}

	class SupportListener implements ItemListener
	{
		public void itemStateChanged(ItemEvent ie)
		{
			String text = (String) ie.getItem();
			if (text.equals("Others"))
			{
				txtSupportName.setEnabled(true);
			}
			else
			{
				txtSupportName.setText("");
				txtSupportName.setEnabled(false);
			}
		}
	}

	class ConstitutionListener implements ItemListener
	{
		public void itemStateChanged(ItemEvent ie)
		{
			String text = (String) ie.getItem();
			if (text.equals("Others"))
			{
				txtOtherPromotersName1.setText("");
				txtOtherPromotersDob1.setText("");
				txtOtherPromotersItpan1.setText("");
				txtOtherPromotersName2.setText("");
				txtOtherPromotersDob2.setText("");
				txtOtherPromotersItpan2.setText("");
				txtOtherPromotersName3.setText("");
				txtOtherPromotersDob3.setText("");
				txtOtherPromotersItpan3.setText("");
				txtOtherPromotersName1.setEnabled(true);
				txtOtherPromotersDob1.setEnabled(true);
				txtOtherPromotersItpan1.setEnabled(true);
				txtOtherPromotersName2.setEnabled(true);
				txtOtherPromotersDob2.setEnabled(true);
				txtOtherPromotersItpan2.setEnabled(true);
				txtOtherPromotersName3.setEnabled(true);
				txtOtherPromotersDob3.setEnabled(true);
				txtOtherPromotersItpan3.setEnabled(true);
				txtConstitution.setEnabled(true);
			}
			else if (text.equalsIgnoreCase("proprietary"))
			{
				txtOtherPromotersName1.setText("");
				txtOtherPromotersDob1.setText("");
				txtOtherPromotersItpan1.setText("");
				txtOtherPromotersName2.setText("");
				txtOtherPromotersDob2.setText("");
				txtOtherPromotersItpan2.setText("");
				txtOtherPromotersName3.setText("");
				txtOtherPromotersDob3.setText("");
				txtOtherPromotersItpan3.setText("");
				txtOtherPromotersName1.setEnabled(false);
				txtOtherPromotersDob1.setEnabled(false);
				txtOtherPromotersItpan1.setEnabled(false);
				txtOtherPromotersName2.setEnabled(false);
				txtOtherPromotersDob2.setEnabled(false);
				txtOtherPromotersItpan2.setEnabled(false);
				txtOtherPromotersName3.setEnabled(false);
				txtOtherPromotersDob3.setEnabled(false);
				txtOtherPromotersItpan3.setEnabled(false);
				txtConstitution.setText("");
				txtConstitution.setEnabled(false);
			}
			else
			{
				txtOtherPromotersName1.setText("");
				txtOtherPromotersDob1.setText("");
				txtOtherPromotersItpan1.setText("");
				txtOtherPromotersName2.setText("");
				txtOtherPromotersDob2.setText("");
				txtOtherPromotersItpan2.setText("");
				txtOtherPromotersName3.setText("");
				txtOtherPromotersDob3.setText("");
				txtOtherPromotersItpan3.setText("");
				txtOtherPromotersName1.setEnabled(true);
				txtOtherPromotersDob1.setEnabled(true);
				txtOtherPromotersItpan1.setEnabled(true);
				txtOtherPromotersName2.setEnabled(true);
				txtOtherPromotersDob2.setEnabled(true);
				txtOtherPromotersItpan2.setEnabled(true);
				txtOtherPromotersName3.setEnabled(true);
				txtOtherPromotersDob3.setEnabled(true);
				txtOtherPromotersItpan3.setEnabled(true);
				txtConstitution.setText("");
				txtConstitution.setEnabled(false);
			}
		}
	}

	class IndustryNatureListener implements ItemListener
	{
		public void itemStateChanged(ItemEvent ie)
		{
			String text = (String) ie.getItem();
			ArrayList indSectors = getIndSectors(text);
			int size = indSectors.size();
			int i = 0;
			cmbIndSector.removeAllItems();

			for (i=0;i<size;i++)
			{
				cmbIndSector.addItem(indSectors.get(i));
			}
		}
	}

	class LegalIdListener implements ItemListener
	{
		public void itemStateChanged(ItemEvent itemEvent)
		{
			String selectedText = cmbLegalId.getSelectedItem().toString();
			if (selectedText.equals("Others"))
			{
				txtOtherId.setText("");
				txtOtherId.setEnabled(true);
				txtLegalValue.setText("");
			}
			else
			{
				txtOtherId.setText("");
				txtOtherId.setEnabled(false);
				txtLegalValue.setText("");
			}

			validate();
		}
	}

	class ForumNamesListener implements ItemListener
	{
		public void itemStateChanged(ItemEvent itemEvent)
		{
			String selectedText = cmbForumNames.getSelectedItem().toString();
			if (selectedText.equals("Others"))
			{
				txtForumName.setText("");
				txtForumName.setEnabled(true);
			}
			else
			{
				txtForumName.setText("");
				txtForumName.setEnabled(false);
			}
			validate();
		}
	}

	class McgfListener implements ItemListener
	{
		public void itemStateChanged(ItemEvent ie)
		{
			String text = (String) ie.getItem();
			if (! text.equals("Select"))
			{
				ArrayList partBankNames = getParticipatingBankNames(text);
				int size = partBankNames.size();
				mcgfNameValue.setText((String) partBankNames.get(0));

				int i = 0;
				cmbParticipatingBanks.removeAllItems();

				for (i=1;i<size;i++)
				{
					cmbParticipatingBanks.addItem(partBankNames.get(i));
				}
			}
		}
	}
	
	class NameTitleListener implements ItemListener
	{
		public void itemStateChanged(ItemEvent ie)
		{
			String text = (String) ie.getItem();
			if (text.equalsIgnoreCase("Smt") || text.equalsIgnoreCase("Ku"))
			{
				male.setSelected(false);
				female.setSelected(true);
				male.setEnabled(false);
				female.setEnabled(false);
			}
			else if (text.equalsIgnoreCase("Mr."))
			{
				male.setSelected(true);
				female.setSelected(false);
				male.setEnabled(false);
				female.setEnabled(false);
			}
			else
			{
				male.setSelected(true);
				female.setSelected(false);
				male.setEnabled(true);
				female.setEnabled(true);				
			}
		}
	}

	class ProjectOutlayListener implements FocusListener
	{
		/**
		* Handles the focus lost event.
		*/
		public void focusLost(FocusEvent fe)
		{
			if (!fe.isTemporary())
			{
				displayPjtOutlayAmounts();
			}
		}

		public void focusGained(FocusEvent fe)
		{
		}
	}

	private void displayPjtOutlayAmounts()
	{
		double tcSanc = 0;
		double wcFb = 0;
		double wcNfb = 0;
		double tcPromoterContribution = 0;
		double wcPromoterContribution = 0;
		double tcSubsidy = 0;
		double wcSubsidy = 0;
		double tcOthers = 0;
		double wcOthers = 0;
		double projectCost = 0;
		double wcAssessed = 0;
		double projectOutlay = 0;

		df = new DecimalFormat("#############.##");
		df.setDecimalSeparatorAlwaysShown(false);

			if (!((txtTermCredit.getText().trim()).equals("")))
			{
				tcSanc = Double.parseDouble(txtTermCredit.getText().trim());
			}
			if (!((txtWCFundBased.getText().trim()).equals("")))
			{
				wcFb = Double.parseDouble(txtWCFundBased.getText().trim());
			}
			if (!((txtWCNonFundBased.getText().trim()).equals("")))
			{
				wcNfb = Double.parseDouble(txtWCNonFundBased.getText().trim());
			}
			if (!((txtTCPromotersContribution.getText().trim()).equals("")))
			{
				tcPromoterContribution = Double.parseDouble(txtTCPromotersContribution.getText().trim());
			}
			if (!((txtWCPromotersContribution.getText().trim()).equals("")))
			{
				wcPromoterContribution = Double.parseDouble(txtWCPromotersContribution.getText().trim());
			}
			if (!((txtTCSupport.getText().trim()).equals("")))
			{
				tcSubsidy = Double.parseDouble(txtTCSupport.getText().trim());
			}
			if (!((txtWCSupport.getText().trim()).equals("")))
			{
				wcSubsidy = Double.parseDouble(txtWCSupport.getText().trim());
			}
			if (!((txtTCOthers.getText().trim()).equals("")))
			{
				tcOthers = Double.parseDouble(txtTCOthers.getText().trim());
			}
			if (!((txtWCOthers.getText().trim()).equals("")))
			{
				wcOthers = Double.parseDouble(txtWCOthers.getText().trim());
			}
			projectCost = tcSanc + tcPromoterContribution + tcSubsidy + tcOthers;
			wcAssessed = wcFb + wcNfb + wcPromoterContribution + wcSubsidy + wcOthers;

			if (marginMoneyYes.isSelected())
			{
				projectOutlay = wcAssessed;
			}
			else if (marginMoneyNo.isSelected())
			{
				projectOutlay = projectCost + wcAssessed;
			}


			projectCostValue.setText(""+df.format(projectCost));

			wcAssessedValue.setText(""+df.format(wcAssessed));
			projectOutlayValue.setText(""+df.format(projectOutlay));

			if (amtSanctionedValue != null)
			{
				amtSanctionedValue.setText(""+df.format(tcSanc));
			}

			if (fbLimitSanctionedValue != null)
			{
				if (loanFacilityValue.getText().trim().equals("WC") || loanFacilityValue.getText().trim().equals("BO") || loanFacilityValue.getText().trim().equals("CC"))
				{
					fbLimitSanctionedValue.setText(""+df.format(wcFb));
				}
				else if (loanFacilityValue.getText().trim().equals("Working Capital Enhancement"))
				{
					enhancedFundBasedValue.setText(""+df.format(wcFb));
					enhancedNonFundBasedValue.setText(""+df.format(wcNfb));
//					enhancedTotalValue.setText(""+df.format(wcFb));
				}
				else if (loanFacilityValue.getText().trim().equals("Working Capital Renewal"))
				{
					renewedFundBasedValue.setText(""+df.format(wcFb));
					renewedNonFundBasedValue.setText(""+df.format(wcNfb));
//					renewedTotalValue.setText(""+df.format(wcFb));
				}
			}
			if (nfbLimitSanctionedValue != null)
			{
				nfbLimitSanctionedValue.setText(""+wcNfb);
			}

//			validate();
	}

	class PrimarySecurityListener implements FocusListener
	{
		public void focusLost(FocusEvent fe)
		{
			double landValue = 0;
			double buildingValue = 0;
			double machineValue = 0;
			double otherAssetsValue = 0;
			double currentValue = 0;
			double othersValue = 0;
			double total = 0;

			if (! fe.isTemporary())
			{
				if (!((txtLandValue.getText().trim()).equals("")))
				{
					landValue = Double.parseDouble(txtLandValue.getText().trim());
				}
				if (!((txtBuildingValue.getText().trim()).equals("")))
				{
					buildingValue = Double.parseDouble(txtBuildingValue.getText().trim());
				}
				if (!((txtMachineValue.getText().trim()).equals("")))
				{
					machineValue = Double.parseDouble(txtMachineValue.getText().trim());
				}
				if (!((txtOtherAssetsValue.getText().trim()).equals("")))
				{
					otherAssetsValue = Double.parseDouble(txtOtherAssetsValue.getText().trim());
				}
				if (!((txtCurrentAssetsValue.getText().trim()).equals("")))
				{
					currentValue = Double.parseDouble(txtCurrentAssetsValue.getText().trim());
				}
				if (!((txtOthersValue.getText().trim()).equals("")))
				{
					othersValue = Double.parseDouble(txtOthersValue.getText().trim());
				}
				total = landValue + buildingValue + machineValue + otherAssetsValue + currentValue + othersValue;

				securityTotalValue.setText(""+df.format(total));
			}
		}

		public void focusGained(FocusEvent fe)
		{}
	}

	class BrowseListener implements ActionListener
	{
		public void actionPerformed(ActionEvent actionEvent)
		{
			if (actionEvent.getActionCommand().equals("No File"))
			{
//				browse.setEnabled(false);
//				txtFilePath.setText("");
//				txtFilePath.setEnabled(false);
				JOptionPane.showMessageDialog(new ThinClient(), "Please download the basic data file", "Message", JOptionPane.WARNING_MESSAGE);
				hardDisk.setSelected(true);
			}
			else
			{
				browse.setEnabled(true);
				txtFilePath.setText("");
				txtFilePath.setEnabled(true);
			}
		}
	}

	class BorrowerCoveredActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent actionEvent)
		{
			if (actionEvent.getActionCommand().equals("yes"))
			{
				none.setEnabled(false);
				cgpan.setEnabled(true);
				cgbid.setEnabled(true);
				cgpan.setSelected(true);
				enableBorrowerDetails(false);
			}
			else if (actionEvent.getActionCommand().equals("no"))
			{
				none.setEnabled(true);
				none.setSelected(true);
				cgpan.setEnabled(false);
				cgbid.setEnabled(false);
				enableBorrowerDetails(true);
			}
		}
	}

	class BorrowerAssistedListener implements ActionListener
	{
		public void actionPerformed(ActionEvent actionEvent)
		{
			if (actionEvent.getActionCommand().equals("Yes"))
			{
				activateBorrowerAssisted(true);
			}
			else if (actionEvent.getActionCommand().equals("No"))
			{
				activateBorrowerAssisted(false);
			}

			validate();
		}
	}
	
	private void activateBorrowerAssisted(boolean active)
	{
		enableBorrowerDetails(true);
		txtOSAmt.setEnabled(active);
		npaYes.setSelected(false);
		npaNo.setSelected(true);
		npaYes.setEnabled(active);
		npaNo.setEnabled(active);
		borrowerCoveredYes.setSelected(false);
		borrowerCoveredNo.setSelected(true);
		borrowerCoveredYes.setEnabled(active);
		borrowerCoveredNo.setEnabled(active);
		none.setSelected(true);
		cgpan.setSelected(false);
		cgbid.setSelected(false);
		none.setEnabled(false);
		cgpan.setEnabled(false);
		cgbid.setEnabled(false);
		txtSsiValue.setEnabled(false);
	}
	
	class MarginMoneyListener implements ActionListener
	{
		public void actionPerformed(ActionEvent actionEvent)
		{
			if (actionEvent.getActionCommand().equals("Yes"))
			{
				activateMarginMoney(false);
			}
			else if (actionEvent.getActionCommand().equals("No"))
			{
				activateMarginMoney(true);
			}

			validate();
		}
	}
	
	private void activateMarginMoney(boolean active)
	{
		txtWCPromotersContribution.setText("");
		displayPjtOutlayAmounts();
		txtWCPromotersContribution.setEnabled(active);
	}

	class BorrowerListener implements ActionListener
	{
		public void actionPerformed(ActionEvent actionEvent)
		{
			if (actionEvent.getActionCommand().equals("none"))
			{
				enableBorrowerDetails(true);
			}
			else if ((actionEvent.getActionCommand().equals("cgpan")) || (actionEvent.getActionCommand().equals("cgbid")))
			{
				enableBorrowerDetails(false);
			}
		}
	}

	class AccountWrittenOffListener implements ActionListener
	{
		public void actionPerformed(ActionEvent actionEvent)
		{
			if (actionEvent.getActionCommand().equals("Yes"))
			{
				txtDtOfAccWrittenOff.setText("");
				txtDtOfAccWrittenOff.setEnabled(true);
			}
			else if (actionEvent.getActionCommand().equals("No"))
			{
				txtDtOfAccWrittenOff.setText("");
				txtDtOfAccWrittenOff.setEnabled(false);
			}
		}
	}
	
	class RecoveryProcInitiatedListener implements ItemListener
	{
		public void itemStateChanged(ItemEvent ie)
		{
			if (procInitiatedYes.isSelected())
			{
				enableLegalSuitDetails(true);
			}
			else if (procInitiatedNo.isSelected())
			{
				enableLegalSuitDetails(false);
			}
		}
	}
	
	class PeriodicOutstandingTotal implements FocusListener
	{
		public void focusLost(FocusEvent fe)
		{
			df = new DecimalFormat("#############.##");
			df.setDecimalSeparatorAlwaysShown(false);

			int noOfOs = noOfOutstandings.length;
			int i = 0;
			int txtIndex=0;
			String value1="";
			String value2="";
			double total=0.0;
			double nfbtotal=0.0;

			if (! fe.isTemporary())
			{
				for (i=0;i<noOfOs;i++)
				{
					total=0;
					nfbtotal=0;
					for (int j=0;j<noOfOutstandings[i];j++)
					{
						String type=facilityValue[i].getText().trim();
						type=type.substring(type.length()-2, type.length()-1);
						if (type.equalsIgnoreCase("T"))
						{
							value1 = txtTCPrincipal[txtIndex].getText().trim();
							if (! value1.equals(""))
							{
								total = total + Double.parseDouble(value1);
							}
						}
						else if (type.equalsIgnoreCase("W") || type.equalsIgnoreCase("R"))
						{
							value1 = txtWCFBPrincipal[txtIndex].getText().trim();
							value2 = txtWCFBIntComm[txtIndex].getText().trim();
							if (! value1.equals(""))
							{
								total = total + Double.parseDouble(value1);
							}
							if (! value2.equals(""))
							{
								total = total + Double.parseDouble(value2);
							}
							value1 = txtWCNFBPrincipal[txtIndex].getText().trim();
							value2 = txtWCNFBIntComm[txtIndex].getText().trim();
							if (! value1.equals(""))
							{
								nfbtotal = nfbtotal + Double.parseDouble(value1);
							}
							if (! value2.equals(""))
							{
								nfbtotal = nfbtotal + Double.parseDouble(value2);
							}							
						}
						txtIndex++;
					}
					totalOutstandingAmtValue[i].setText(df.format(total));
					totalNFBOutstandingAmtValue[i].setText(df.format(nfbtotal));
				}
			}
		}

		public void focusGained(FocusEvent fe)
		{}
	}

	class PeriodicDisbursementTotal implements FocusListener
	{
		public void focusLost(FocusEvent fe)
		{
			df = new DecimalFormat("#############.##");
			df.setDecimalSeparatorAlwaysShown(false);

			int noOfDis = noOfDisbursements.length;
			int i = 0;
			int txtIndex=0;
			String value1="";
			double total=0.0;

			if (! fe.isTemporary())
			{
				for (i=0;i<noOfDis;i++)
				{
					total=0;
					for (int j=0;j<noOfDisbursements[i];j++)
					{
						value1 = txtDisbursementAmt[txtIndex].getText().trim();
						if (! value1.equals(""))
						{
							total = total + Double.parseDouble(value1);
						}
						txtIndex++;
					}
					totalDisbursementAmtValue[i].setText(df.format(total));
				}
			}
		}

		public void focusGained(FocusEvent fe)
		{}
	}

	class PeriodicRepaymentTotal implements FocusListener
	{
		public void focusLost(FocusEvent fe)
		{
			df = new DecimalFormat("#############.##");
			df.setDecimalSeparatorAlwaysShown(false);

			int noOfRepay = noOfRepayments.length;
			int i = 0;
			int txtIndex=0;
			String value1="";
			double total=0.0;

			if (! fe.isTemporary())
			{
				for (i=0;i<noOfRepay;i++)
				{
					total=0;
					for (int j=0;j<noOfRepayments[i];j++)
					{
						value1 = txtRepaymentAmt[txtIndex].getText().trim();
						if (! value1.equals(""))
						{
							total = total + Double.parseDouble(value1);
						}
						txtIndex++;
					}
					totalRepaymentAmtValue[i].setText(df.format(total));
				}
			}
		}

		public void focusGained(FocusEvent fe)
		{}
	}

	class UpdRecoveryListener implements ActionListener
	{
		public void actionPerformed(ActionEvent actionEvent)
		{
			if (actionEvent.getActionCommand().equals("SaveUpdatedRecovery"))
			{
				try
				{
					if (validateRecovery())
					{
						boolean success = saveVerifiedModifiedDetails("PERUPDREC", "NEW");
						displayHomePanel();
					}
				}
				catch (ThinClientException exception)
				{
					displayError(exception.getMessage());
				}
				validate();
			}
			else if (actionEvent.getActionCommand().equals("VerifyUpdatedRecovery"))
			{
				try
				{
					if (validateRecovery())
					{
						boolean success = saveVerifiedModifiedDetails("PERUPDREC", "VER");
						displayHomePanel();
					}
				}
				catch (ThinClientException exception)
				{
					displayError(exception.getMessage());
				}
				validate();
			}
			else if (actionEvent.getSource() instanceof JRadioButton)
			{
				try
				{
					String command = actionEvent.getActionCommand();
					int index1 = command.indexOf("$");
					String flag = command.substring(0, index1);
					String key = command.substring(index1+1, index1+10);
					int index2 = command.lastIndexOf("$");
					String fileName = command.substring(index1+11, index2);
					String recId = command.substring(index2+1, command.length());

					int firstIndex=fileName.indexOf("&");
					String brnName="";
					if (firstIndex>0)
					{
						brnName = fileName.substring(firstIndex+1, fileName.length());
						fileName=fileName.substring(0, firstIndex);
					}
//					System.out.println("brn anme " + brnName);

					Hashtable objects = readFromFile(fileName);
					com.cgtsi.guaranteemaintenance.PeriodicInfo periodicInfo = (com.cgtsi.guaranteemaintenance.PeriodicInfo) objects.get(key);
					ArrayList recProcs = periodicInfo.getRecoveryDetails();
					for (int i=0;i<recProcs.size();i++)
					{
						com.cgtsi.guaranteemaintenance.Recovery recovery = (com.cgtsi.guaranteemaintenance.Recovery) recProcs.get(i);
						if (recId.equalsIgnoreCase("new") || recovery.getRecoveryNo().equals(recId))
						{
							panel.removeAll();
							panel.setBackground(dataBg);
							panel.setLayout(gridBag);

							displayRecoveryDetails(recovery, "UPD");
							addComponent(0, 5, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, recoveryPanel, gridBag, constraints, panel);

							lblKey = new JLabel(key);
							lblFileName = new JLabel(fileName);
							lblBrnName=new JLabel(brnName);
							lblKey.setVisible(false);
							lblFileName.setVisible(false);
							lblBrnName.setVisible(false);
							if (periodicInfo.getExport())
							{
								lblExport = new JLabel("T");
							}
							else
							{
								lblExport = new JLabel("F");
							}

							lblExport.setVisible(false);
							hiddenPanel=new JPanel();
							hiddenPanel.add(lblKey);
							hiddenPanel.add(lblFileName);
							hiddenPanel.add(lblExport);
							hiddenPanel.add(lblBrnName);
							hiddenPanel.setVisible(false);
							addComponent(0, 6, GridBagConstraints.REMAINDER, GridBagConstraints.WEST, GridBagConstraints.BOTH, hiddenPanel, gridBag, constraints, panel);

							 reset=new JButton("Reset");
							 reset.setActionCommand("Reset");
							 reset.addActionListener(this);
							 reset.setEnabled(false);

							 save=new JButton("Save");
							cancel=new JButton("Cancel");
							 if (flag.equals("REC"))
							 {
								 save.setActionCommand("SaveUpdatedRecovery");
								cancel.setActionCommand("CancelUpdatedRecovery");
							 }
							 else
							 {
								 save.setActionCommand("VerifyUpdatedRecovery");
								cancel.setActionCommand("CancelVerifiedRecovery");
							 }

							 save.addActionListener(this);
							 cancel.addActionListener(this);

							 buttonsPanel=new JPanel();
							 buttonsPanel.setLayout(new FlowLayout());
							buttonsPanel.add(save);
							 buttonsPanel.add(reset);
							 buttonsPanel.add(cancel);
							 addComponent(0, 7, GridBagConstraints.REMAINDER, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, buttonsPanel, gridBag, constraints, panel);

							container.removeAll();
							scrollPane.setViewportView(panel);
							container.add(scrollPane, BorderLayout.CENTER);

							break;
						}
					}
				}
				catch (ThinClientException exception)
				{
					displayError(exception.getMessage());
				}
				validate();
			}
			else if (actionEvent.getActionCommand().equals("CancelUpdatedRecovery"))
			{
				displayVerifyPanel("PER", "REC");
				ok.setActionCommand("ToUpdateRecovery");
				validate();
			}
			else if (actionEvent.getActionCommand().equals("CancelVerifiedRecovery"))
			{
				displayVerifyPanel("PER", "VERREC");
				ok.setActionCommand("ToVerifyRecovery");
				validate();
			}
		}
	}

	class AppFileNewFilter extends FileFilter
	{
		/**
		* This method will accept .NEW files in the type of files option.
		*/
		public boolean accept(File f)
		{
			String fileName = f.getName();
			if (((fileName.endsWith(".NEW")) && (fileName.indexOf("APP") != -1)) || (f.isDirectory()))
			{
				return true;
			}
			return false;
		}

		public String getDescription()
		{
			return "New Application Files";
		}
	}

	class PeriodicFileNewFilter extends FileFilter
	{
		/**
		* This method will accept .NEW Periodic files in the type of files option.
		*/
		public boolean accept(File f)
		{
			String fileName = f.getName();
			if (((fileName.endsWith(".NEW")) && (fileName.indexOf("PER") != -1)) || (f.isDirectory()))
			{
				return true;
			}
			return false;
		}

		public String getDescription()
		{
			return "New PeriodicInfo Files";
		}
	}

	class PeriodicFileExpFilter extends FileFilter
	{
		/**
		* This method will accept .EXP Periodic files in the type of files option.
		*/
		public boolean accept(File f)
		{
			String fileName = f.getName();
			if (((fileName.endsWith(".EXP")) && (fileName.indexOf("PER") != -1)) || (f.isDirectory()))
			{
				return true;
			}
			return false;
		}

		public String getDescription()
		{
			return "PeriodicInfo Export Files";
		}
	}

	class ClaimFileNewFilter extends FileFilter
	{
		/**
		* This method will accept .NEW Claim files in the type of files option.
		*/
		public boolean accept(File f)
		{
			String fileName = f.getName();
			if (((fileName.endsWith(".NEW")) && (fileName.indexOf("CLAIM") != -1)) || (f.isDirectory()))
			{
				return true;
			}
			return false;
		}

		public String getDescription()
		{
			return "New Claims Files";
		}
	}

	class ClaimFileExpFilter extends FileFilter
	{
		/**
		* This method will accept .EXP Claim files in the type of files option.
		*/
		public boolean accept(File f)
		{
			String fileName = f.getName();
			if (((fileName.endsWith(".EXP")) && (fileName.indexOf("CLAIM") != -1)) || (f.isDirectory()))
			{
				return true;
			}
			return false;
		}

		public String getDescription()
		{
			return "Claims Export Files";
		}
	}

	private void setDateFieldProperties(final JTextField txtField, final String dateFormat, final boolean isMandatory)
	{
		String defaultValue = txtField.getText();
		txtField.setDocument(new DateFormatField(txtField, dateFormat));
		txtField.setText(defaultValue);
		txtField.setColumns(7);
		txtField.addFocusListener(new FocusAdapter()
		{
			public void focusGained(FocusEvent loFocusEvent)
			{
				txtField.setCaretPosition(0);
			}

			public void focusLost(FocusEvent loFocusEvent)
			{
				String lsEnteredDate = txtField.getText();
				try
				{
					String lsValue = "";
					try
					{
						lsValue = lsEnteredDate.substring(0, 2);
						if(lsValue.trim().length() > 0)
						{
							if((new Integer(lsValue.trim())).intValue() < 10)
							{
								lsValue       = "0" + (new Integer(lsValue.trim())).intValue();
								lsEnteredDate = lsValue + lsEnteredDate.substring(2, lsEnteredDate.length());
							}
						}
					}
					catch(Throwable loIgnoreException) {}
					try
					{
						lsValue = lsEnteredDate.substring(3, 5);
						if(lsValue.trim().length() > 0)
						{
							if((new Integer(lsValue.trim())).intValue() < 10)
							{
								lsValue = "0" + (new Integer(lsValue.trim())).intValue();
								lsEnteredDate = lsEnteredDate.substring(0, 3) + lsValue
												+ lsEnteredDate.substring(5, lsEnteredDate.length());
							}
						}
					}
					catch(Throwable loIgnoreException) {}
					try
					{
						lsValue = lsEnteredDate.substring(6, 10);
						if(lsValue.trim().length() > 0)
						{
							String lsNewValue = "";
							for(int liCtr = 0; liCtr < trimRight(lsValue).length(); liCtr++)
							{
								if(lsValue.charAt(liCtr) != ' ')
								{
									lsNewValue = lsNewValue + lsValue.charAt(liCtr);
								}
								else
								{
									//lsNewValue = lsNewValue + "0";
								}
							}
							for(int liCtr = lsNewValue.length(); liCtr < 4; liCtr++)
							{
								lsNewValue = lsNewValue + " ";
							}
							lsValue       = lsNewValue;
							lsEnteredDate = lsEnteredDate.substring(0, 6) + lsValue;
						}
					}
					catch(Throwable loIgnoreException) {}
/*                    if(lbPrefixYear)
					{
						String lsYear = lsEnteredDate.substring(6, 10);
						int    liYear = 0;
						try
						{
							liYear = new Integer(lsYear.trim()).intValue();
						}
						catch(Throwable loIgnoreException) {}
						if((liYear >= 0) && (liYear <= 59))
						{
							liYear += 2000;
						}
						else if((liYear >= 60) && (liYear <= 99))
						{
							liYear += 1900;
						}
						//else
						//{
						//   //No Change in the Year Entered
						//}
						lsEnteredDate = lsEnteredDate.substring(0, 6) + liYear;
				   }*/
				}
				catch(Throwable IgnoreException) {}
//				System.out.println("Entered date : " + lsEnteredDate);
				if( !bisValidDate(lsEnteredDate, dateFormat))
				{
					if (isMandatory)
					{
						Toolkit.getDefaultToolkit().beep();
						txtField.requestFocus();
						txtField.setText("");
					}
					else
					{
						txtField.setText("");
					}
				}
				else
				{
					txtField.setText(lsEnteredDate);
				}
			}
		});
	}

	private boolean bisValidDate(String date, String dateFormat)
	{
		java.util.Date utilDate = null;
		if((dateFormat == null) || (dateFormat.trim().length() == 0))
		{
			dateFormat = "dd/MM/yyyy";
		}
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(dateFormat);
		sdf.setLenient(false);
		utilDate = sdf.parse(date, new java.text.ParsePosition(0));

		if(utilDate == null)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	private String trimRight(String string)
	{
		while((string != null) && (string.length() > 0) && string.endsWith(" "))
		{
			string = string.substring(0, string.length() - 1);
		}
		return string;
	}
	
	private void displayHelp()
	{
		panel.removeAll();
		panel.setBackground(dataBg);
		panel.setLayout(gridBag);
		
		JLabel info1 = new JLabel("New Files: ", SwingConstants.LEFT);
		info1.setOpaque(true);
		info1.setBackground(dataBg);
		info1.setFont(headingFont);
		addComponent(0, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, info1, gridBag, constraints, panel);
		
		JLabel info2 = new JLabel("The files created using the New option under Application/Periodic Info/Claims or Update Recovery Details option under Periodic Info ", SwingConstants.LEFT);
		info2.setOpaque(true);
		info2.setBackground(dataBg);
		addComponent(0, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, info2, gridBag, constraints, panel);

		JLabel info3 = new JLabel("will be stored in \\NEW folder with filename of format <branch name>-ddmmyyyy-<APP/PER/CLAIM/PERUPDREC><slno>.NEW.", SwingConstants.LEFT);
		info3.setOpaque(true);
		info3.setBackground(dataBg);
		addComponent(0, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, info3, gridBag, constraints, panel);
		
		JLabel info14 = new JLabel(" In case of Applications, the <branch name> is the Bank / Institution Branch Name. ", SwingConstants.LEFT);
		info14.setOpaque(true);
		info14.setBackground(dataBg);
		addComponent(0, 3, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, info14, gridBag, constraints, panel);
		
		JLabel info4 = new JLabel(" In case of Periodic Info and Claims the <branch name> will be input by the User.", SwingConstants.LEFT);
		info4.setOpaque(true);
		info4.setBackground(dataBg);
		addComponent(0, 4, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, info4, gridBag, constraints, panel);
		
		JLabel info5 = new JLabel("Modify Files: ", SwingConstants.LEFT);
		info5.setOpaque(true);
		info5.setBackground(dataBg);
		info5.setFont(headingFont);
		addComponent(0, 5, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, info5, gridBag, constraints, panel);

		JLabel info6 = new JLabel("Only the .NEW files can be modified. The data in the respective file is modified.", SwingConstants.LEFT);
		info6.setOpaque(true);
		info6.setBackground(dataBg);
		addComponent(0, 6, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, info6, gridBag, constraints, panel);
		
		JLabel info7 = new JLabel("Verify Files: ", SwingConstants.LEFT);
		info7.setOpaque(true);
		info7.setBackground(dataBg);
		info7.setFont(headingFont);
		addComponent(0, 7, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, info7, gridBag, constraints, panel);

		JLabel info8 = new JLabel("Only the .NEW files can be verified. The Verification can be done only by HO. ", SwingConstants.LEFT);
		info8.setOpaque(true);
		info8.setBackground(dataBg);
		addComponent(0, 8, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, info8, gridBag, constraints, panel);
		
		JLabel info9 = new JLabel("The verified files are stored in \\VER folder with filename of format <branch name>-ddmmyyyy-<APP/PER/CLAIM/PERUPDREC><slno>.VER", SwingConstants.LEFT);
		info9.setOpaque(true);
		info9.setBackground(dataBg);
		addComponent(0, 9, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, info9, gridBag, constraints, panel);
		
		JLabel info10 = new JLabel("The data once verified will not be available in the corresponding .NEW file. These verified data are uploaded into the web application.", SwingConstants.LEFT);
		info10.setOpaque(true);
		info10.setBackground(dataBg);
		addComponent(0, 10, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, info10, gridBag, constraints, panel);
		
		JLabel info11 = new JLabel("Archive Files: ", SwingConstants.LEFT);
		info11.setOpaque(true);
		info11.setBackground(dataBg);
		info11.setFont(headingFont);
		addComponent(0, 11, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, info11, gridBag, constraints, panel);
		
		JLabel info12 = new JLabel("The verified application / peroidic info / claims files in the \\VER folder will be moved to \\Archive\\<Application/Periodic Info/Claims> respectively.", SwingConstants.LEFT);
		info12.setOpaque(true);
		info12.setBackground(dataBg);
		addComponent(0, 12, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, info12, gridBag, constraints, panel);

		JLabel info13 = new JLabel("Also the files will be copied to a floppy, if available.", SwingConstants.LEFT);
		info13.setOpaque(true);
		info13.setBackground(dataBg);
		addComponent(0, 13, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, info13, gridBag, constraints, panel);
		
		container.removeAll();
		container.setBackground(dataBg);
		container.add(panel, BorderLayout.NORTH);
	}
	
	private void displayAboutThinClient()
	{
		abtDialog=new JDialog(this, " ", true);
		//abtDialog=new JDialog(this, "Emergency Credit Guarantee Fund Trust for Small Industries", true);
		JPanel abtPanel=new JPanel();
		abtPanel.setLayout(gridBag);

		JLabel abtLabel=new JLabel("                CGTSIClient Version 1.1.2");
		abtLabel.setOpaque(true);
		addComponent(0, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, abtLabel, gridBag, constraints, abtPanel);
		
		Image image=Toolkit.getDefaultToolkit().createImage("satyam.gif");
		ImageIcon imageIcon = new ImageIcon(image);
		
		JLabel abtLabel1=new JLabel(new ImageIcon(image));
//		abtLabel1.setOpaque(true);
		constraints.gridheight=5;
		addComponent(1, 0, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, abtLabel1, gridBag, constraints, abtPanel);
		
		JLabel abtLabel2=new JLabel("                Copyright [C] 2004");
		abtLabel2.setOpaque(true);
		constraints.gridheight=1;
		addComponent(0, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, abtLabel2, gridBag, constraints, abtPanel);
		
		JLabel abtLabel3=new JLabel("                This Software has been");
		abtLabel3.setOpaque(true);
		constraints.gridheight=1;
		addComponent(0, 2, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, abtLabel3, gridBag, constraints, abtPanel);

		JLabel abtLabel4=new JLabel("                developed by the");
		abtLabel4.setOpaque(true);
		constraints.gridheight=1;
		addComponent(0, 3, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, abtLabel4, gridBag, constraints, abtPanel);
		
		JLabel abtLabel5=new JLabel("                Banking and Financial Division");
		abtLabel5.setOpaque(true);
		constraints.gridheight=1;
		addComponent(0, 4, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, abtLabel5, gridBag, constraints, abtPanel);
		
		JLabel abtLabel6=new JLabel("                Satyam Computer Services Ltd");
		abtLabel6.setOpaque(true);
		constraints.gridheight=1;
		addComponent(0, 5, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, abtLabel6, gridBag, constraints, abtPanel);
		
		abtLabel6=new JLabel("");
		abtLabel6.setOpaque(true);
		constraints.gridheight=1;
		addComponent(0, 6, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, abtLabel6, gridBag, constraints, abtPanel);

		JLabel abtLabel7=new JLabel("                12, CP. Ramaswamy Road, Alwarpet");
		abtLabel7.setOpaque(true);
		constraints.gridheight=1;
		addComponent(0, 7, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, abtLabel7, gridBag, constraints, abtPanel);
		
		JButton button=new JButton("OK");
		button.setActionCommand("CloseAbtDialog");
		button.addActionListener(this);
		constraints.gridheight=5;
		addComponent(1, 7, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, button, gridBag, constraints, abtPanel);
		
		JLabel abtLabel8=new JLabel("                Chennai-600018, India");
		abtLabel8.setOpaque(true);
		constraints.gridheight=1;
		addComponent(0, 8, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, abtLabel8, gridBag, constraints, abtPanel);
		
		JLabel abtLabel9=new JLabel("                Ph: 24983221");
		abtLabel9.setOpaque(true);
		constraints.gridheight=1;
		addComponent(0, 9, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, abtLabel9, gridBag, constraints, abtPanel);
		
		JLabel abtLabel10=new JLabel("                Fax: 24983108");
		abtLabel10.setOpaque(true);
		constraints.gridheight=1;
		addComponent(0, 10, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, abtLabel10, gridBag, constraints, abtPanel);
		
		JLabel abtLabel11=new JLabel("                Email: http://www.satyam.com");
		abtLabel11.setOpaque(true);
		constraints.gridheight=1;
		addComponent(0, 11, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, abtLabel11, gridBag, constraints, abtPanel);
		
	
		abtDialog.getContentPane().add(abtPanel);
		abtDialog.setSize(400,250);
		abtDialog.setVisible(true);
		//abtDialog.show();
	}


	public static void main(String args[]) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception exception) {
			//System.out.println("Exception in setting the Look and Feel");
		}
		ThinClient thinClient = new ThinClient();
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		thinClient.setSize(screenSize);
//		thinClient.setSize(800, 800);
		thinClient.setVisible(true);
		//		thinClient.setResizable(false);
		thinClient.setTitle("Thin Client");
		

		thinClient.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}
}