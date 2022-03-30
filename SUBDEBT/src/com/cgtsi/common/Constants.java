/*************************************************************
   *
   * Name of the Interface: Constants.java
   * This interface holds the constants used across the system.
   *
   * @author : Veldurai T
   * @version:
   * @since: Sep 24, 2003
   **************************************************************/

package com.cgtsi.common;
import java.sql.*;
import java.sql.Types;



/**
 * @author Veldurai T
 *
 * This interface holds the constants used across the system.
 */
public interface Constants
{

	public static final int CURSOR=Types.REF_CURSOR;

	public static final int FUNCTION_SUCCESS=0;

	public static final int FUNCTION_FAILURE=1;

	public static final int FUNCTION_NO_DATA=2;

	public static final String YES="Y";

	public static final String NO="N";

	public static final String CGTSI_USER_BANK_ID="0000";

	public static final String CGTSI_USER_ZONE_ID="0000";

	public static final String CGTSI_USER_BRANCH_ID="0000";

	public static final int AUDIT_ID=-1;

	public static final String FILE_UPLOAD_DIRECTORY="WEB-INF/FileUpload";

	public static final String FILE_DOWNLOAD_DIRECTORY="Download";

	public static final int FILE_UPLOAD_SUCCESS=0;

	public static final int FILE_UPLOAD_FAILED=1;

	public static final int FILE_UPLOAD_DEFAULT_STATUS=-1;

	public static final String SUCCESS="success";

	public static final String ERROR="ERROR";

	public static final String FAILED="FAILED";

	public static final String ALL="All";

	public static final int FUND_BASED=0;

	public static final int NON_FUND_BASED=1;

	public static final int BOTH_FUND_BASED_NON_FUND_BASED=2;

	public static final String WITHIN_MLIS="W";

	public static final String ACROSS_MLIS="A";

	public static final String GLOBAL_SUB_SCHEME="GLOBAL";
	
	public static final String CGTSI_MEMBERID="000000000000";
	
	public static final String MODIFY_BORROWER_DETAILS="MODIFY";
	
	public static final String APPROVE_BORROWER_DETAILS="APPROVE";
	
	public static final String CGDAN="CGDAN";
	
	public static final String SFDAN="SFDAN";
  
 // public static final String BATCHSFDAN="BATCHSFDAN"; //
 //public static final String SFDANEXP = "SFDANEXP";    //
	
	public static final String SHDAN="SHDAN";
	
	public static final String CLDAN="CLDAN";

}
