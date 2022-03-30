/*************************************************************
   *
   * Name of the class: Privileges.java
   * This file holds the Privilege names.
   *
   *
   * @author : Veldurai T
   * @version:
   * @since: Oct 31, 2003
   **************************************************************/
package com.cgtsi.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * @author VT8150
 *
 * This interface is used to store the Privilege's Constants.
 *
 */
public class Privileges
{
	private Privileges()
	{
	}

	public static final String RECEIVE_SUCCESS_FAILURE_MESSAGE="RECEIVE_SUCCESS_FAILURE_MESSAGE";

	public static final String ADD_APPLICATION="ADD_APPLICATION";

	public static final String MODIFY_APPLICATION="MODIFY_APPLICATION";

	public static final String APPLICATION_APPROVAL="APPLICATION_APPROVAL";

	public static final String APPROVE_INELIGIBLE_APPLICATION="APPROVE_INELIGIBLE_APPLICATION";

	public static final String APPROVE_DUPLICATE_APPLICATION="APPROVE_DUPLICATE_APPLICATION";

	public static final String APPLICATION_RE_APPROVAL="APPLICATION_RE_APPROVAL";

	public static final String FILE_UPLOAD="FILE_UPLOAD";

	public static final String MODIFY_BORROWER_DETAILS="MODIFY_BORROWER_DETAILS";

	public static final String PERIODIC_INFO="PERIODIC_INFO";

	public static final String UPDATE_REPAYMENT_SCHEDULE="UPDATE_REPAYMENT_SCHEDULE";

	public static final String SHIFT_CGPAN_OR_BORROWER="SHIFT_CGPAN_OR_BORROWER";

	public static final String EXPORT_PERIODIC_INFO="EXPORT_PERIDIC_INFO";

	public static final String APPLICATION_CLOSURE="APPLICATION_CLOSURE";

	public static final String ALLOCATE_PAYMENTS="ALLOCATE_PAYMENTS";
  
  //added by sukumar@path for Allocate Payments Of GF,ASF FY2008 and ASF of Previous Years
  
  public static final String GUARANTEE_FEE="GUARANTEE_FEE";
  
  public static final String ASF_FY2008="ASF_2008";
  
  public static final String ASF_OF_PREVIOUS_YEARS="ASF_OF_PREVIOUS_YEARS";
  
  
  //added by sukumar@path for Allocate and Appropriate Payments.
  public static final String APPROPRIATE_ALLOCATE_PAYMENTS="APPROPRIATE_ALLOCATE_PAYMENTS";
	
  public static final String VIEW_PENDING_PAYMENTS="VIEW_PENDING_PAYMENTS";
  
  //ADDED BY sukumar@PATH for Cancellation of Allocation Details
  public static final String DE_ALLOCATE_PAYMENTS="DE_ALLOCATE_PAYMENTS";


	public static final String APPROPRIATE_PAYMENTS="APPROPRIATE_PAYMENTS";

	public static final String RE_ALLOCATE_PAYMENTS="RE_ALLOCATE_PAYMENTS";

	public static final String GENERATE_SHORT_DAN="GENERATE_SHORT_DAN";

	public static final String GENERATE_CGDAN="GENERATE_CGDAN";

	public static final String GENERATE_SFDAN="GENERATE_SFDAN";

	public static final String GENERATE_CLDAN="GENERATE_CLDAN";

	public static final String GENERATE_EXCESS_VOUCHER="GENERATE_EXCESS_VOUCHER";

	public static final String GENERATE_VOUCHER="GENERATE_VOUCHER";

	public static final String WAIVE_SHORT_AMOUNTS="WAIVE_SHORT_AMOUNTS";

	public static final String PRINT_PAY_IN_SLIP="PRINT_PAY_IN_SLIP";
 
	public static final String CLAIM_FIRST_INSTALLMENT="CLAIM_FIRST_INSTALLMENT";

	public static final String CLAIM_SECOND_INSTALLMENT="CLAIM_SECOND_INSTALLMENT";

	public static final String OTS_REQUEST="OTS_REQUEST";

	public static final String OTS_APPROVAL="OTS_APPROVAL";

	public static final String CLAIM_APPROVAL="CLAIM_APPROVAL";

	public static final String SETTLEMENT="SETTLEMENT";

	public static final String EXPORT_CLAIM_FILE="EXPORT_CLAIM_FILE";

	public static final String LIMIT_SET_UP="LIMIT_SET_UP";

	public static final String RISK_CALCULATE_EXPOSUER="RISK_CALCULATE_EXPOSUER";

	public static final String GENERATE_EXPOSURE_REPORT="GENERATE_EXPOSURE_REPORT";

	public static final String SET_UPDATE_SUB_SCHEME_PARAMS="SET_UPDATE_SUB_SCHEME_PARAMS";

	public static final String ENTER_BUDGET_DETAILS="ENTER_BUDGET_DETAILS";

	public static final String ENTER_INFLOW_DETAILS="ENTER_INFLOW_DETAILS";

	public static final String ENTER_OUTFLOW_DETAILS="ENTER_OUTFLOW_DETAILS";

	public static final String SET_CEILING_FOR_INVESTMENT="SET_CEILING_FOR_INVESTMENT";

	public static final String ENTER_INVESTMENT_DETAILS="ENTER_INVESTMENT_DETAILS";

	public static final String IF_CALCULATE_EXPOSUER="IF_CALCULATE_EXPOSUER";

	public static final String INVESTMENT_FULFILLMENT="INVESTMENT_FULFILLMENT";

	public static final String IF_UPDATE_MASTER_TABLES="IF_UPDATE_MASTER_TABLES";

	public static final String MAKE_REQUEST="MAKE_REQUEST";

	public static final String TDS_DETAILS="TDS_DETAILS";

	public static final String FORECASTING_DETAILS="FORECASTING_DETAILS";

	public static final String PLAN_INVESTMENT="PLAN_INVESTMENT";

	public static final String PROJECT_EXPECTED_CLAIMS="PROJECT_EXPECTED_CLAIMS";

	public static final String ADD_INWARD="ADD_INWARD";
	
	public static final String AD_MLI_DEFINE_ORG_DASHBOARD="AD_MLI_DEFINE_ORG_DASHBOARD";
	public static final String AD_MLI_DEFINE_ORG_DASHBOARD_MLIR="AD_MLI_DEFINE_ORG_DASHBOARD_MLIR";

	public static final String ADD_OUTWARD="ADD_OUTWARD";

	public static final String UPLOAD_DOCUMENTS="UPLOAD_DOCUMENTS";

	public static final String SEARCH_DOCUMENTS="SEARCH_DOCUMENTS";

	public static final String REGISTER_COLLECTING_BANK="REGISTER_COLLECTING_BANK";

	public static final String MODIFY_COLLECTING_BANK_DETAILS="MODIFY_COLLECTING_BANK_DETAILS";

	public static final String REGISTER_MLI="REGISTER_MLI";

	public static final String ASSIGN_COLLECTING_BANK="ASSIGN_COLLECTING_BANK";

	public static final String MODIFY_COLLECTING_BANK="MODIFY_COLLECTING_BANK";

	public static final String DEFINE_ORGANIZATION_STRUCTURE="DEFINE_ORGANIZATION_STRUCTURE";

	public static final String DEACTIVATE_MEMBER="DEACTIVATE_MEMBER";

	public static final String ACTIVATE_MEMBER="ACTIVATE_MEMBER";

	public static final String ADD_MODIFY_ROLES="ADD_MODIFY_ROLES";

	public static final String CREATE_CGTSI_USER="CREATE_CGTSI_USER";

	public static final String CREATE_MLI_USER="CREATE_MLI_USER";

	public static final String MODIFY_USER_DETAILS="MODIFY_USER_DETAILS";

	public static final String DEACTIVATE_USER="DEACTIVATE_USER";

	public static final String REACTIVATE_USER="REACTIVATE_USER";

	public static final String ASSIGN_MODIFY_ROLES_AND_PRIVILEGES="ASSIGN_MODIFY_ROLES_AND_PRIVILEGES";

	public static final String RESET_PASSWORD="RESET_PASSWORD";

	public static final String ENTER_AUDIT_DETAILS="ENTER_AUDIT_DETAILS";

	public static final String REVIEW_AUDIT_DETAILS="REVIEW_AUDIT_DETAILS";

	public static final String BROADCAST_MESSAGE="BROADCAST_MESSAGE";

	public static final String UPDATE_MASTER_TABLE="UPDATE_MASTER_TABLE";

	public static final String REPORTS="REPORTS";

	public static final String SECURITIZATION="SECURITIZATION";

	public static final String REGISTER_MCGF="REGISTER_MCGF";

	public static final String ADD_PARTICIPATING_BANKS="ADD_PARTICIPATING_BANKS";

	public static final String ADD_SSI_MEMBERS="ADD_SSI_MEMBERS";

	public static final String ADD_DONOR_DETAILS="ADD_DONOR_DETAILS";

	public static final String SET_PARTICIPATING_BANK_LIMIT="SET_PARTICIPATING_BANK_LIMIT";
  
//  public static final String ASF_ALLOCATED_REPORT="ASF_ALLOCATED_REPORT";
  
  
 //  public static final String GF_ALLOCATED_REPORT="GF_ALLOCATED_REPORT";
 
 
// public static final String ASF_PAYMENT_REPORT="ASF_PAYMENT_REPORT";
	/*
	 * Added on 08-March-2004.
	 */

	public static final String APP_STATUS_WISE_REPORT="APP_STATUS_WISE_REPORT";
	public static final String PERIODIC_INFO_APPROVAL="PERIODIC_INFO_APPROVAL";
	public static final String DAY_END_PROCESS="DAY_END_PROCESS";
	public static final String STATEMENT_DETAILS="STATEMENT_DETAILS";
	public static final String IF_REPORT="IF_REPORT";
	public static final String APP_SPECIAL_MESSAGE="APP_SPECIAL_MESSAGE";
	public static final String UPDATE_RECOVERY="UPDATE_RECOVERY";
  public static final String SPECIAL_REPORTS="SPECIAL_REPORTS";
  public static final String ASF_ARREARS="ASF_ARREARS";
  public static final String APPROVE_NPA_UPGRADATION = "APPROVE_NPA_UPGRADATION";   //Diksha
  //added by shyam 01-04-2008
 // public static final String MINORITY_REPORT = "MINORITY_REPORT";

 // public static final String MINORITY_REPORT_STATE_WISE = "MINORITY_REPORT_STATE_WISE";
 
 // public static final String CATEGORY_WISE_GUARANTEE_ISSUED = "CATEGORY_WISE_GUARANTEE_ISSUED";
 
 // public static final String TURNOVER_AND_EXPORTS = "TURNOVER_AND_EXPORTS";
  
	private static final HashMap PRIVILEGES=new HashMap();

	static
	{
		PRIVILEGES.put(RECEIVE_SUCCESS_FAILURE_MESSAGE,"Receive Success Failure Message");

		PRIVILEGES.put(ADD_APPLICATION,"Add Application");

		PRIVILEGES.put(MODIFY_APPLICATION,"Modify Application");

		PRIVILEGES.put(APPROVE_INELIGIBLE_APPLICATION,"Approve Ineligible Application");

		PRIVILEGES.put(APPROVE_DUPLICATE_APPLICATION,"Approve Duplicate Application");

		PRIVILEGES.put(APPLICATION_APPROVAL,"Application Approval");

		PRIVILEGES.put(APPLICATION_RE_APPROVAL,"Application Reapproval");

		PRIVILEGES.put(FILE_UPLOAD,"File Upload");

		PRIVILEGES.put(MODIFY_BORROWER_DETAILS,"Modify Borrower Details");

		PRIVILEGES.put(PERIODIC_INFO,"Periodic Info");

		PRIVILEGES.put(UPDATE_REPAYMENT_SCHEDULE,"Update Repayment Schedule");

		PRIVILEGES.put(SHIFT_CGPAN_OR_BORROWER,"Shift Cgpan or Borrower");

		PRIVILEGES.put(EXPORT_PERIODIC_INFO,"Export Peridic Info");

		PRIVILEGES.put(APPLICATION_CLOSURE,"Application Closure");

		PRIVILEGES.put(ALLOCATE_PAYMENTS,"Allocate Payments");
    
    //added by Sukumar@path of Allocate Payments For GF,ASF FY 2008 and ASF of Previous Years
    PRIVILEGES.put(GUARANTEE_FEE,"For Guarantee Fee");
    
    PRIVILEGES.put(ASF_FY2008,"For ASF FY 2008");
      
    PRIVILEGES.put(ASF_OF_PREVIOUS_YEARS,"For ASF of Previous Years");
  
   
  //added by shyam
    PRIVILEGES.put(APPROPRIATE_ALLOCATE_PAYMENTS,"Appropriate Allocate Payments");
		
    PRIVILEGES.put(VIEW_PENDING_PAYMENTS,"View Pending Payments");
    
    //ADDED BY sukumar@PATH for RP Cancellation
    PRIVILEGES.put(DE_ALLOCATE_PAYMENTS,"De-Allocate Payments");

		PRIVILEGES.put(APPROPRIATE_PAYMENTS,"Appropriate Payments");

		PRIVILEGES.put(RE_ALLOCATE_PAYMENTS,"Re-Allocate Payments");

		PRIVILEGES.put(GENERATE_SHORT_DAN,"Generate Short DAN");

		PRIVILEGES.put(GENERATE_CGDAN,"Generate CGDAN");

		PRIVILEGES.put(GENERATE_SFDAN,"Generate SFDAN");

		PRIVILEGES.put(GENERATE_CLDAN,"Generate CLDAN");

		PRIVILEGES.put(GENERATE_EXCESS_VOUCHER,"Generate Excess Voucher");

		PRIVILEGES.put(GENERATE_VOUCHER,"Generate Voucher");

		PRIVILEGES.put(WAIVE_SHORT_AMOUNTS,"Waive Short Amounts");

		PRIVILEGES.put(PRINT_PAY_IN_SLIP,"Print Pay In Slip");
    
   // PRIVILEGES.put(ALLOCATION_CANCELLATION1,"ALLOCATION CANCELLATION1");
		PRIVILEGES.put(CLAIM_FIRST_INSTALLMENT,"Claim First Installment");

		PRIVILEGES.put(CLAIM_SECOND_INSTALLMENT,"Claim Second Installment");

		PRIVILEGES.put(OTS_REQUEST,"OTS Request");

		PRIVILEGES.put(OTS_APPROVAL,"OTS Approval");

		PRIVILEGES.put(CLAIM_APPROVAL,"Claim Approval");

		PRIVILEGES.put(EXPORT_CLAIM_FILE,"Export Claim_File");

		PRIVILEGES.put(SETTLEMENT,"Settlement");

		PRIVILEGES.put(LIMIT_SET_UP,"Limit Set Up");

		PRIVILEGES.put(RISK_CALCULATE_EXPOSUER,"Risk Calculate Exposuer");

		PRIVILEGES.put(GENERATE_EXPOSURE_REPORT,"Generate Exposure Report");

		PRIVILEGES.put(SET_UPDATE_SUB_SCHEME_PARAMS,"Set Update Sub Scheme Params");

		PRIVILEGES.put(ENTER_BUDGET_DETAILS,"Enter Budget Details");

		PRIVILEGES.put(ENTER_INFLOW_DETAILS,"Enter Inflow Details");

		PRIVILEGES.put(ENTER_OUTFLOW_DETAILS,"Enter Outflow Details");

		PRIVILEGES.put(SET_CEILING_FOR_INVESTMENT,"Set Ceiling For Investment");

		PRIVILEGES.put(ENTER_INVESTMENT_DETAILS,"Enter Investment Details");

		PRIVILEGES.put(IF_CALCULATE_EXPOSUER,"Investment Calculate Exposuer");

		PRIVILEGES.put(INVESTMENT_FULFILLMENT,"Investment Fulfillment Details");

		PRIVILEGES.put(IF_UPDATE_MASTER_TABLES,"Update Investment Master Tables");

		PRIVILEGES.put(MAKE_REQUEST,"Request for Buy/Sell");

		PRIVILEGES.put(TDS_DETAILS,"Enter TDS Details");

		PRIVILEGES.put(FORECASTING_DETAILS,"Enter Forecasting Details");

		PRIVILEGES.put(PLAN_INVESTMENT,"Plan Investment");

		PRIVILEGES.put(PROJECT_EXPECTED_CLAIMS,"Project Expected Claims");

		PRIVILEGES.put(ADD_INWARD,"Add Inward");
		
		PRIVILEGES.put(AD_MLI_DEFINE_ORG_DASHBOARD,"Dashboard");
		PRIVILEGES.put(AD_MLI_DEFINE_ORG_DASHBOARD_MLIR,"MLI Registration");

		PRIVILEGES.put(ADD_OUTWARD,"Add Outward");

		PRIVILEGES.put(UPLOAD_DOCUMENTS,"Upload Documents");

		PRIVILEGES.put(SEARCH_DOCUMENTS,"Search Documents");

		PRIVILEGES.put(REGISTER_COLLECTING_BANK,"Register Collecting Bank");

		PRIVILEGES.put(MODIFY_COLLECTING_BANK_DETAILS,"Modify Collecting Bank Details");

		PRIVILEGES.put(REGISTER_MLI,"Register MLI");

		PRIVILEGES.put(ASSIGN_COLLECTING_BANK,"Assign Collecting Bank");

		PRIVILEGES.put(MODIFY_COLLECTING_BANK,"Modify Collecting Bank");

		PRIVILEGES.put(DEFINE_ORGANIZATION_STRUCTURE,"Define Organization Structure");

		PRIVILEGES.put(DEACTIVATE_MEMBER,"Deactivate Member");

		PRIVILEGES.put(ACTIVATE_MEMBER,"Activate Member");

		PRIVILEGES.put(ADD_MODIFY_ROLES,"Add Modify Roles");

		PRIVILEGES.put(CREATE_CGTSI_USER,"Create CGTSI User");

		PRIVILEGES.put(CREATE_MLI_USER,"Create MLI User");

		PRIVILEGES.put(MODIFY_USER_DETAILS,"Modify_User_Details");

		PRIVILEGES.put(DEACTIVATE_USER,"Deactivate User");

		PRIVILEGES.put(REACTIVATE_USER,"Reactivate User");

		PRIVILEGES.put(ASSIGN_MODIFY_ROLES_AND_PRIVILEGES,"Assign Modify Roles And Privileges");

		PRIVILEGES.put(RESET_PASSWORD,"Reset Password");

		PRIVILEGES.put(ENTER_AUDIT_DETAILS,"Enter Audit Details");

		PRIVILEGES.put(REVIEW_AUDIT_DETAILS,"Review Audit Details");

		PRIVILEGES.put(BROADCAST_MESSAGE,"Broadcast Message");

		PRIVILEGES.put(UPDATE_MASTER_TABLE,"Update Admin Master Tables");

		PRIVILEGES.put(REPORTS,"Reports");

		PRIVILEGES.put(SECURITIZATION,"Securitization");

		PRIVILEGES.put(REGISTER_MCGF,"Register MCGF");

		PRIVILEGES.put(ADD_PARTICIPATING_BANKS,"Add Participating Banks");

		PRIVILEGES.put(ADD_SSI_MEMBERS,"Add SSI Members");

		PRIVILEGES.put(ADD_DONOR_DETAILS,"Add Donor Details");

		PRIVILEGES.put(SET_PARTICIPATING_BANK_LIMIT,"Set Participating Bank Limit");


		/*
		 * Added on 08-March-2004.
		 */

		PRIVILEGES.put(APP_STATUS_WISE_REPORT,"Application Status Wise Report");
    
    //ADDED BY SHYAM 01-04-2008
  //  PRIVILEGES.put(MINORITY_REPORT,"MINORITY_REPORT");
    
    
 //   PRIVILEGES.put(MINORITY_REPORT_STATE_WISE,"MINORITY_REPORT_STATE_WISE");
    
     
   // PRIVILEGES.put(CATEGORY_WISE_GUARANTEE_ISSUED,"CATEGORY_WISE_GUARANTEE_ISSUED");  
    
    
 //   PRIVILEGES.put(TURNOVER_AND_EXPORTS,"TURNOVER_AND_EXPORTS");
    
   
 //   PRIVILEGES.put(ASF_ALLOCATED_REPORT,"ASF_ALLOCATED_REPORT");
    
     
  //  PRIVILEGES.put(GF_ALLOCATED_REPORT,"GF_ALLOCATED_REPORT");
   
    
  //  PRIVILEGES.put(ASF_PAYMENT_REPORT,"ASF_PAYMENT_REPORT");
    
		PRIVILEGES.put(PERIODIC_INFO_APPROVAL,"Periodic info approval");

		PRIVILEGES.put(DAY_END_PROCESS,"Day End Processes");

		PRIVILEGES.put(STATEMENT_DETAILS,"Statement Details");

		PRIVILEGES.put(IF_REPORT,"Investment Reports");

		PRIVILEGES.put(APP_SPECIAL_MESSAGE,"Add Modify Special Message");

		PRIVILEGES.put(UPDATE_RECOVERY,"Update Recovery Details");
    
    PRIVILEGES.put(SPECIAL_REPORTS,"Special Reports");
    
    PRIVILEGES.put(ASF_ARREARS,"ASF OF PREVIOUS YEARS");

	}

	public static Set getKeys()
	{
		return PRIVILEGES.keySet();
	}

	public static String getPrivilege(String privilege)
	{
		return (String)PRIVILEGES.get(privilege);
	}

	public static boolean isAPAvailable(ArrayList userPrivileges)
	{
		if(userPrivileges.contains(ADD_APPLICATION) || userPrivileges.contains(APPLICATION_APPROVAL)
			|| userPrivileges.contains(APPLICATION_RE_APPROVAL) || userPrivileges.contains(APPROVE_DUPLICATE_APPLICATION)
			|| userPrivileges.contains(APPROVE_INELIGIBLE_APPLICATION)|| userPrivileges.contains(MODIFY_APPLICATION)
			|| userPrivileges.contains(SECURITIZATION) || userPrivileges.contains(APP_SPECIAL_MESSAGE))
		{
			return true;
		}
		return false;
	}

	public static boolean isGMAvailable(ArrayList userPrivileges)
	{
		if(userPrivileges.contains(APPLICATION_CLOSURE) || userPrivileges.contains(MODIFY_BORROWER_DETAILS)
			|| userPrivileges.contains(PERIODIC_INFO)|| userPrivileges.contains(EXPORT_PERIODIC_INFO)
			|| userPrivileges.contains(UPDATE_REPAYMENT_SCHEDULE)|| userPrivileges.contains(SHIFT_CGPAN_OR_BORROWER)
			|| userPrivileges.contains(UPDATE_RECOVERY) || userPrivileges.contains(PERIODIC_INFO_APPROVAL)|| userPrivileges.contains(APPROVE_NPA_UPGRADATION))
		{
			return true;
		}
		return false;
	}

	public static boolean isRPAvailable(ArrayList userPrivileges)
	{
		if(userPrivileges.contains(ALLOCATE_PAYMENTS) 
      || userPrivileges.contains(GUARANTEE_FEE)
      || userPrivileges.contains(ASF_FY2008)
      || userPrivileges.contains(ASF_OF_PREVIOUS_YEARS)
      || userPrivileges.contains(APPROPRIATE_ALLOCATE_PAYMENTS)
      || userPrivileges.contains(DE_ALLOCATE_PAYMENTS)
      || userPrivileges.contains(APPROPRIATE_PAYMENTS)
			|| userPrivileges.contains(GENERATE_CGDAN)|| userPrivileges.contains(GENERATE_CLDAN)
			|| userPrivileges.contains(GENERATE_EXCESS_VOUCHER) || userPrivileges.contains(GENERATE_SFDAN)
			|| userPrivileges.contains(GENERATE_SHORT_DAN)|| userPrivileges.contains(GENERATE_VOUCHER)
			|| userPrivileges.contains(RE_ALLOCATE_PAYMENTS)|| userPrivileges.contains(VIEW_PENDING_PAYMENTS)
			|| userPrivileges.contains(WAIVE_SHORT_AMOUNTS)|| userPrivileges.contains(PRINT_PAY_IN_SLIP))
   	{
			return true;
		}
		return false;
	}
	
	public static boolean isDBAvailable(ArrayList userPrivileges)
	{
		if(userPrivileges.contains(AD_MLI_DEFINE_ORG_DASHBOARD))
		{
			return true;
		}
		return false;
	}
	
	/*public static boolean isDBAvailable(ArrayList userPrivileges)
	{
		if(userPrivileges.contains(AD_MLI_DEFINE_ORG_DASHBOARD_MLIR))
		{
			return true;
		}
		return false;
	}*/
	//

	public static boolean isCPAvailable(ArrayList userPrivileges)
	{
		if(userPrivileges.contains(CLAIM_APPROVAL) || userPrivileges.contains(CLAIM_FIRST_INSTALLMENT)
			|| userPrivileges.contains(CLAIM_FIRST_INSTALLMENT)|| userPrivileges.contains(CLAIM_SECOND_INSTALLMENT)
			|| userPrivileges.contains(EXPORT_CLAIM_FILE) || userPrivileges.contains(OTS_APPROVAL)
			|| userPrivileges.contains(OTS_REQUEST)|| userPrivileges.contains(SETTLEMENT))
		{
			return true;
		}
		return false;
	}

	public static boolean isRIAvailable(ArrayList userPrivileges)
	{
		if(userPrivileges.contains(LIMIT_SET_UP) || userPrivileges.contains(RISK_CALCULATE_EXPOSUER)
			|| userPrivileges.contains(SET_PARTICIPATING_BANK_LIMIT)
			|| userPrivileges.contains(SET_UPDATE_SUB_SCHEME_PARAMS)
			|| userPrivileges.contains(GENERATE_EXPOSURE_REPORT))
		{
			return true;
		}
		return false;
	}

	public static boolean isIFAvailable(ArrayList userPrivileges)
	{
		if(userPrivileges.contains(IF_CALCULATE_EXPOSUER) || userPrivileges.contains(IF_UPDATE_MASTER_TABLES)
			|| userPrivileges.contains(INVESTMENT_FULFILLMENT)
			|| userPrivileges.contains(ENTER_BUDGET_DETAILS)|| userPrivileges.contains(ENTER_INFLOW_DETAILS)
			|| userPrivileges.contains(ENTER_INVESTMENT_DETAILS)|| userPrivileges.contains(ENTER_OUTFLOW_DETAILS)
			|| userPrivileges.contains(SET_CEILING_FOR_INVESTMENT) || userPrivileges.contains(STATEMENT_DETAILS)
			|| userPrivileges.contains(IF_REPORT) || userPrivileges.contains(PROJECT_EXPECTED_CLAIMS))
		{
			return true;
		}
		return false;
	}

	public static boolean isReportsAvailable(ArrayList userPrivileges)
	{
		//if(userPrivileges.contains(REPORTS)  || userPrivileges.contains(APP_STATUS_WISE_REPORT)||userPrivileges.contains(MINORITY_REPORT)||userPrivileges.contains(MINORITY_REPORT_STATE_WISE)||userPrivileges.contains(CATEGORY_WISE_GUARANTEE_ISSUED)||userPrivileges.contains(GF_ALLOCATED_REPORT)||userPrivileges.contains(ASF_ALLOCATED_REPORT)||userPrivileges.contains(ASF_PAYMENT_REPORT)||userPrivileges.contains(TURNOVER_AND_EXPORTS))
		if(userPrivileges.contains(REPORTS)  || userPrivileges.contains(APP_STATUS_WISE_REPORT)|| userPrivileges.contains(SPECIAL_REPORTS)|| userPrivileges.contains(ASF_ARREARS))
	
    {
			return true;   
		}
		return false;
	}
  
  
	public static boolean isIOAvailable(ArrayList userPrivileges)
	{
		if(userPrivileges.contains(ADD_INWARD) ||userPrivileges.contains(ADD_OUTWARD)
		||userPrivileges.contains(SEARCH_DOCUMENTS)||userPrivileges.contains(UPLOAD_DOCUMENTS))
		{
			return true;
		}
		return false;
	}

	public static boolean isSCAvailable(ArrayList userPrivileges)
	{
		if(userPrivileges.contains(SECURITIZATION) || userPrivileges.contains(ADD_DONOR_DETAILS)
		||userPrivileges.contains(ADD_PARTICIPATING_BANKS) || userPrivileges.contains(ADD_SSI_MEMBERS))
		{

			return true;
		}
		return false;
	}

	public static boolean isTCAvailable(ArrayList userPrivileges)
	{
		if(userPrivileges.contains(FILE_UPLOAD))
		{
			return true;
		}
		return false;
	}

}
