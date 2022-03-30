/*
 * Created on Dec 30, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.cgtsi.receiptspayments;

/**
 * @author NS30571
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface RpConstants {

	public static final String DAN_TYPE_CGDAN="CG";

	public static final String DAN_TYPE_SFDAN="SF";

	public static final String DAN_TYPE_CLDAN="CL";

	public static final String DAN_TYPE_SHDAN="SH";

	public static final String PAYMENT_VOUCHER="PAYMENT VOUCHER";

	public static final String RECEIPT_VOUCHER="RECEIPT VOUCHER";

	public static final String JOURNAL_VOUCHER="JOURNAL VOUCHER";

	public static final String RP_DAN_ID="DAN_ID";

	public static final String RP_DAN_GEN_DATE="DAN_GEN_DATE";

	public static final String RP_CGPAN="CGPAN";

	public static final String RP_AMOUNT_RAISED="AMOUNT_RAISED";

	public static final String RP_PENALTY="PENALTY";

	public static final String RP_MEMBER_ID="MEMBER_ID";

	public static final String RP_TOTAL_AMOUNT_RAISED="TOTAL_AMOUNT_RAISED";

	public static final String RP_CGTSI="CG";

	public static final String SMILE_DELIMITER = String.copyValueOf(new char[]{ 1 });

	public static final String AC_CODE_FILE_PATH="/tomcat/webapps/CGTSI/WEB-INF/classes";

	public static final String AC_CODE_FILE_NAME="AccountCodes.properties";

	public static final String BANK_AC="bank_ac";

	public static final String GF_AC="gf_ac";

	public static final String SF_AC="sf_ac";

	public static final String SHORT_AC="short_ac";

	public static final String EXCESS_AC="excess_ac";

	public static final String RECOVERY_AC="recovery_ac";

	public static final String CLM_FIRST_INST_AC="clm_first_inst_ac";

	public static final String CLM_SECOND_INST_AC="clm_second_inst_ac";

	public static final String INVESTMENT_AC="investment_ac";

	public static final String PAYMENT_AC="payment_ac";

	public static final String PENDING_DETAILS="pending_dtls";

	public static final String PENDINGSFEE_DETAILS="pendingsfee_dtls";

	public static final String RP_TOTAL_PENDING_AMNT="RP_TOTAL_PENDING_AMNT";
	
	public static final String INVESTMENT_INTEREST_AC="investment_interest_ac";
	
	public static final String INVESTMENT_TDS_AC="investment_tds_ac";
}
