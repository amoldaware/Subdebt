
/*************************************************************
   *
   * Name of the class: Parameters.java
   * This interface holds the global parameter constants.
   *
   * @author : Veldurai T
   * @version:  1.0
   * @since: Oct 30, 2000
   **************************************************************/
package com.cgtsi.admin;


/**
 * @author Veldurai T
 *
 * This interface holds the constants related to global parameters. With these constants
 * parameter values are stored into database. While retrieving from database, these keys
 * are used to get the appropriate values.
 *
 */
public interface Parameters {

	public static final String ACTIVE_USER_LIMIT="ACTIVE_USER_LIMIT";

	public static final String PASSWORD_EXPIRY="PASSWORD_EXPIRY";

	public static final String PASSWORD_DISPLAY_PERIOD="PASSWORD_DISPLAY_PERIOD";

	public static final String WORKING_CAPITAL_TENOR="WORKING_CAPITAL_TENOR";

	public static final String MAXIMUM_INTEREST_RATE="MAXIMUM_INTEREST_RATE";

	public static final String MAXIMUM_APPROVED_AMOUNT="MAXIMUM_APPROVED_AMOUNT";
  
  public static final String MAXIMUM_RSF_APPROVED_AMOUNT="MAXIMUM_RSF_APPROVED_AMOUNT";
  
  public static final String MAXIMUM_RSF2_APPROVED_AMOUNT = "MAXIMUM_RSF2_APPROVED_AMOUNT";

	public static final String THIRD_PARTY_GUARANTEE_ALLOWED="THIRD_PARTY_GUARANTEE_ALLOWED";

	public static final String COLLATERAL_SECURITY_ALLOWED="COLLATERAL_SECURITY_ALLOWED";

	public static final String FIRST_INSTALLMENT_PERCENTAGE="FIRST_INSTALLMENT_PERCENTAGE";

	public static final String LOCK_IN_PERIOD="LOCK_IN_PERIOD";

	public static final String MIN_AMOUNT_FOR_MANDATORY_ITPAN="MIN_AMOUNT_FOR_MANDATORY_ITPAN";

	public static final String CLAIM_SETTLEMENT_WITHOUT_PENALTY="CLAIM_SETTLEMENT_WITHOUT_PENALTY";

	public static final String SERVICE_FEE_RATE="SERVICE_FEE_RATE";

	public static final String SERVICE_FEE_WITHOUT_PENALTY="SERVICE_FEE_WITHOUT_PENALTY";

	public static final String SERVICE_FEE_PENALTY_RATE="SERVICE_FEE_PENALTY_RATE";

	public static final String NO_CGPANS_PER_DAN="NO_CGPANS_PER_DAN";

	public static final String GF_WITHOUT_PENALTY="GF_WITHOUT_PENALTY";

	public static final String SHORT_PAYMENT_LIMIT="SHORT_PAYMENT_LIMIT";

	public static final String EXCESS_PAYMENT_LIMIT="EXCESS_PAYMENT_LIMIT";

	public static final String WAIVE_LIMIT="WAIVE_LIMIT";

	public static final String GF_FIRST_ALERT_DAYS="GF_FIRST_ALERT_DAYS";

	public static final String GF_ALERT_FREQUENCY="GF_ALERT_FREQUENCY";

	public static final String SERVICE_FEE_CALCULATION_DAY="SERVICE_FEE_CALCULATION_DAY";

	public static final String SERVICE_FEE_CALCULATION_MONTH="SERVICE_FEE_CALCULATION_MONTH";

	public static final String SERVICE_FEE_ALERT_FREQUENCY="SERVICE_FEE_ALERT_FREQUENCY";

	public static final String PERIODIC_FREQUENCY="PERIODIC_FREQUENCY";

	public static final String PERIODIC_ALERT_FREQUENCY="PERIODIC_ALERT_FREQUENCY";

	public static final String BANK_RATE="BANK_RATE";

	public static final String GF_PENALTY_RATE="GF_PENALTY_RATE";

	public static final String CLAIM_PENALTY_RATE="CLAIM_PENALTY_RATE";

	public static final String PENALTY_CALCULATION_TYPE="PENALTY_CALCULATION_TYPE";

	public static final String MCGF_SERVICE_FEE="MCGF_SERVICE_FEE";

//	Added guarantee fee parameter on 08-06-2004.
	public static final String GUARANTEE_FEE="GUARANTEE_FEE";


//	Added this attribute on 20-06-2004.
	public static final String CHEQUE_EXPIRY_IN_MONTHS="CHEQUE_EXPIRY_IN_MONTHS";
	public static final String CHEQUE_EXPIRY_WARNING_IN_DAYS="CHEQUE_EXPIRY_WARNING_IN_DAYS";

    /*
     * This property being added for MCGF -> Generate CGDAN
     * Being added on 28-07-2004
     */
	public static final String MCGF_GUARANTEE_FEE_PERCENTAGE="MCGF_GUARANTEE_FEE_PERCENTAGE";


    /*
     * The following properties being added to incorporate Sub-Scheme Parameters in Risk Management
     * Being added on 19-08-2004
     */
    public static final String APPLICATION_FILING_TIME_LIMIT="APPLICATION_FILING_TIME_LIMIT";

	public static final String MINIMUM_SANCTIONED_AMOUNT="MINIMUM_SANCTIONED_AMOUNT";

	public static final String IS_DEFAULT_RATE_APPLICABLE="IS_DEFAULT_RATE_APPLICABLE";

	public static final String DEFAULT_RATE="DEFAULT_RATE";

	public static final String MODERATION_FACTOR="MODERATION_FACTOR";

    public static final String CGTSI_LIABILITY="CGTSI_LIABILITY";

    public static final String HIGH_VALUE_CLEARENCE_AMOUNT="HIGH_VALUE_CLEARENCE_AMOUNT";

	public static final String PERIOD_TENURE_EXPIRY_LODGEMENT_CLAIMS="PERIOD_TENURE_EXPIRY_LODGEMENT_CLAIMS";
}
