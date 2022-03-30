package com.cgtsi.application;

import com.cgtsi.actionform.WcValidateBean;
import com.cgtsi.admin.Administrator;
import com.cgtsi.admin.Message;
import com.cgtsi.admin.ParameterMaster;
import com.cgtsi.admin.User;
import com.cgtsi.claim.CPDAO;
import com.cgtsi.claim.ClaimsProcessor;
import com.cgtsi.common.Constants;
import com.cgtsi.common.DatabaseException;
import com.cgtsi.common.Log;
import com.cgtsi.common.Mailer;
import com.cgtsi.common.MessageException;
import com.cgtsi.common.NoUserFoundException;
import com.cgtsi.mcgs.MCGFDetails;
import com.cgtsi.mcgs.MCGSProcessor;
import com.cgtsi.receiptspayments.DemandAdvice;
import com.cgtsi.receiptspayments.RpDAO;
import com.cgtsi.receiptspayments.RpHelper;
import com.cgtsi.receiptspayments.RpProcessor;
import com.cgtsi.registration.MLIInfo;
import com.cgtsi.registration.RegistrationDAO;
import com.cgtsi.risk.RiskManagementProcessor;
import com.cgtsi.risk.SubSchemeValues;
import com.cgtsi.util.ApplicationRenewValidator;
import com.cgtsi.util.DBConnection;
import com.cgtsi.util.DateConverter;
import com.cgtsi.util.DateHelper;
import java.io.PrintStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.poi.util.SystemOutLogger;

public class ApplicationDAO {
	public SSIDetails submitApp(Application apps, String createdBy, Connection connection) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "submitApp", "Entered");

		SSIDetails ssiDetail = new SSIDetails();

		String appLoanType = apps.getLoanType();
		try {
			String coveredValue = apps.getBorrowerDetails().getPreviouslyCovered();
			if (coveredValue.equals("Y")) {
				String cgbid = apps.getBorrowerDetails().getSsiDetails().getCgbid();
				String cgpan = apps.getCgpan();
				if ((cgbid != null) && (!cgbid.equals(""))) {
					CallableStatement ssiRefNoForCgbid = connection
							.prepareCall("{call funcGetSSIRefNoforBID(?,?,?,?)}");
					ssiRefNoForCgbid.registerOutParameter(1, 4);
					ssiRefNoForCgbid.registerOutParameter(2, 4);
					ssiRefNoForCgbid.registerOutParameter(4, 12);
					ssiRefNoForCgbid.setString(3, apps.getBorrowerDetails().getSsiDetails().getCgbid());
					ssiRefNoForCgbid.executeQuery();
					int ssiRefNoForCgbidValue = ssiRefNoForCgbid.getInt(1);

					if (ssiRefNoForCgbidValue == 1) {
						String error = ssiRefNoForCgbid.getString(4);
						ssiRefNoForCgbid.close();
						ssiRefNoForCgbid = null;
						Log.log(2, "ApplicationDAO", "submitApp", error);
						connection.rollback();
						throw new DatabaseException(ssiRefNoForCgbid.getString(4));
					}

					apps.getBorrowerDetails().getSsiDetails().setBorrowerRefNo(ssiRefNoForCgbid.getInt(2));

					ssiRefNoForCgbid.close();
					ssiRefNoForCgbid = null;
				} else if ((cgpan != null) && (!cgpan.equals(""))) {
					CallableStatement ssiRefNoForCgpan = connection
							.prepareCall("{call funcGetSSIRefNoforCGPAN(?,?,?,?)}");

					ssiRefNoForCgpan.registerOutParameter(1, 4);
					ssiRefNoForCgpan.registerOutParameter(2, 4);
					ssiRefNoForCgpan.registerOutParameter(4, 12);

					ssiRefNoForCgpan.setString(3, apps.getCgpan());

					ssiRefNoForCgpan.executeQuery();
					int ssiRefNoForCgpanValue = ssiRefNoForCgpan.getInt(1);

					if (ssiRefNoForCgpanValue == 1) {
						String error = ssiRefNoForCgpan.getString(4);
						ssiRefNoForCgpan.close();
						ssiRefNoForCgpan = null;

						connection.rollback();

						Log.log(2, "ApplicationDAO", "submitApp", error);
						throw new DatabaseException(error);
					}

					apps.getBorrowerDetails().getSsiDetails().setBorrowerRefNo(ssiRefNoForCgpan.getInt(2));

					ssiRefNoForCgpan.close();
					ssiRefNoForCgpan = null;
				}

				Log.log(4, "ApplicationDAO", "submitApp", "Entering the loop for gettin the Sub Scheme");

				RiskManagementProcessor rpProcessor = new RiskManagementProcessor();
				SubSchemeValues subSchemeValues = new SubSchemeValues();

				BorrowerDetails borrowerDetails = viewBorrowerDetails(
						apps.getBorrowerDetails().getSsiDetails().getBorrowerRefNo());
				ssiDetail = borrowerDetails.getSsiDetails();
				Application tempApplication = new Application();
				tempApplication.setBorrowerDetails(borrowerDetails);
				tempApplication.setMliID(apps.getMliID());
				double balanceAppAmt = getBalanceApprovedAmt(tempApplication);

				if ((apps.getLoanType().equals("TC")) || (apps.getLoanType().equals("CC"))) {
					Log.log(5, "ApplicationDAO", "submitApp",
							"tc crdit amount :" + apps.getTermLoan().getCreditGuaranteed());
					if (apps.getTermLoan().getCreditGuaranteed() > balanceAppAmt) {
						throw new DatabaseException(
								"Credit to be Guaranteed Amount should be within the eligible amount available for Guarantee :"
										+ balanceAppAmt);
					}
				} else if (apps.getLoanType().equals("WC")) {
					Log.log(5, "ApplicationDAO", "submitApp", "wc enhancecrdit amount :"
							+ apps.getWc().getEnhancedFundBased() + apps.getWc().getEnhancedNonFundBased());
					Log.log(5, "ApplicationDAO", "submitApp", "wc crdit amount :" + apps.getWc().getCreditFundBased()
							+ apps.getWc().getCreditNonFundBased());
					if ((apps.getWc().getEnhancedFundBased() != 0.0D)
							|| (apps.getWc().getEnhancedNonFundBased() != 0.0D)) {
						Log.log(5, "ApplicationDAO", "submitApp",
								"entering enhanced " + apps.getWc().getEnhancementDate());
						if (apps.getWc().getEnhancedFundBased()
								+ apps.getWc().getEnhancedNonFundBased() > balanceAppAmt) {
							throw new DatabaseException(
									"Total Credit to be Guaranteed Amount should be within the eligible amount available for Guarantee :"
											+ balanceAppAmt);
						}

					} else if (apps.getWc().getCreditFundBased()
							+ apps.getWc().getCreditNonFundBased() > balanceAppAmt) {
						throw new DatabaseException(
								"Credit to be Guaranteed8 Amount should be within the eligible amount available for Guarantee :"
										+ balanceAppAmt);
					}
				} else if (apps.getLoanType().equals("BO")) {
					Log.log(5, "ApplicationDAO", "submitApp",
							"both crdit amount :" + apps.getTermLoan().getCreditGuaranteed()
									+ apps.getWc().getCreditFundBased() + apps.getWc().getCreditNonFundBased());
					if (apps.getTermLoan().getCreditGuaranteed() + apps.getWc().getCreditFundBased()
							+ apps.getWc().getCreditNonFundBased() > balanceAppAmt) {
						throw new DatabaseException(
								"Credit to be Guaranteed11 Amount should be within the eligible amount available for Guarantee :"
										+ balanceAppAmt);
					}

				}

			} else {
				Log.log(4, "ApplicationDAO", "submitApp", "Entering the loop for SSI Detail Insertion");

				double balanceAppAmt = getBalanceApprovedAmt(apps);

				if ((apps.getLoanType().equals("TC")) || (apps.getLoanType().equals("CC"))) {
					if (apps.getTermLoan().getCreditGuaranteed() > balanceAppAmt) {
						throw new DatabaseException(
								"Credit to be Guaranteed22 Amount should be within the eligible amount available for Guarantee :"
										+ balanceAppAmt);
					}
				} else if (apps.getLoanType().equals("WC")) {
					if (apps.getWc().getCreditFundBased() + apps.getWc().getCreditNonFundBased() > balanceAppAmt) {
						throw new DatabaseException(
								"Credit to be Guaranteed33 Amount should be within the eligible amount available for Guarantee :"
										+ balanceAppAmt);
					}
				} else if (apps.getLoanType().equals("BO")) {
					if (apps.getTermLoan().getCreditGuaranteed() + apps.getWc().getCreditFundBased()
							+ apps.getWc().getCreditNonFundBased() > balanceAppAmt) {
						throw new DatabaseException(
								"Credit to be Guaranteed Amount should be within the eligible amount available for Guarantee :"
										+ balanceAppAmt);
					}

				}

				CallableStatement ssiDetails = connection
						.prepareCall("{call funcInsertSSIDetailEGS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

				ssiDetails.registerOutParameter(1, 4);
				ssiDetails.registerOutParameter(2, 4);
				ssiDetails.registerOutParameter(3, 12);

				
				if ((apps.getBorrowerDetails().getSsiDetails().getConstitution() != null)
						&& (!apps.getBorrowerDetails().getSsiDetails().getConstitution().equals(""))) {
					ssiDetails.setString(4, apps.getBorrowerDetails().getSsiDetails().getConstitution());
				} else {
					ssiDetails.setString(4, null);
				}
				Log.log(5, "ApplicationDAO", "submitApp",
						"Const :" + apps.getBorrowerDetails().getSsiDetails().getConstitution());

				ssiDetails.setString(5, apps.getBorrowerDetails().getSsiDetails().getSsiType());

				Log.log(5, "ApplicationDAO", "submitApp",
						"ssi Type :" + apps.getBorrowerDetails().getSsiDetails().getSsiType());

				ssiDetails.setString(6, apps.getBorrowerDetails().getSsiDetails().getSsiName().toUpperCase());

				Log.log(5, "ApplicationDAO", "submitApp",
						"ssi name :" + apps.getBorrowerDetails().getSsiDetails().getSsiName());

				

				if ((apps.getBorrowerDetails().getSsiDetails().getSsiITPan() != null)
						&& (!apps.getBorrowerDetails().getSsiDetails().getSsiITPan().equals(""))) {
					ssiDetails.setString(7, apps.getBorrowerDetails().getSsiDetails().getSsiITPan());
				} else {
					ssiDetails.setString(7, null);
				}
				Log.log(5, "ApplicationDAO", "submitApp",
						"ssi ITPAN :" + apps.getBorrowerDetails().getSsiDetails().getSsiITPan());

				if (apps.getBorrowerDetails().getSsiDetails().getEmployeeNos() == 0) {
					ssiDetails.setNull(8, 4);
				} else
					ssiDetails.setInt(8, apps.getBorrowerDetails().getSsiDetails().getEmployeeNos());

				Log.log(5, "ApplicationDAO", "submitApp",
						"employee nos :" + apps.getBorrowerDetails().getSsiDetails().getEmployeeNos());

			
				ssiDetails.setString(9, apps.getBorrowerDetails().getSsiDetails().getAddress().toUpperCase());
				Log.log(5, "ApplicationDAO", "submitApp",
						"Address :" + apps.getBorrowerDetails().getSsiDetails().getAddress());

				if ((apps.getBorrowerDetails().getSsiDetails().getCity() != null)
						&& (!apps.getBorrowerDetails().getSsiDetails().getCity().equals(""))) {
					ssiDetails.setString(10, apps.getBorrowerDetails().getSsiDetails().getCity().toUpperCase());
				} else {
					ssiDetails.setString(10, null);
				}
				Log.log(5, "ApplicationDAO", "submitApp",
						"city :" + apps.getBorrowerDetails().getSsiDetails().getCity());

				ssiDetails.setString(11, apps.getBorrowerDetails().getSsiDetails().getPincode());
				Log.log(5, "ApplicationDAO", "submitApp",
						"pincode :" + apps.getBorrowerDetails().getSsiDetails().getPincode());

				/* ssiDetails.setString(20, "Y"); */

				if ((apps.getBorrowerDetails().getSsiDetails().getDistrict() != null)
						&& (!apps.getBorrowerDetails().getSsiDetails().getDistrict().equals(""))) {
					ssiDetails.setString(12, apps.getBorrowerDetails().getSsiDetails().getDistrict());
				} else {
					ssiDetails.setString(12, null);
				}

				Log.log(5, "ApplicationDAO", "submitApp",
						"district :" + apps.getBorrowerDetails().getSsiDetails().getDistrict());

				ssiDetails.setString(13, apps.getBorrowerDetails().getSsiDetails().getState());
				Log.log(5, "ApplicationDAO", "submitApp",
						"State :" + apps.getBorrowerDetails().getSsiDetails().getState());

				if ((apps.getBorrowerDetails().getSsiDetails().getIndustryNature() != null)
						&& (!apps.getBorrowerDetails().getSsiDetails().getIndustryNature().equals(""))) {
					ssiDetails.setString(14, apps.getBorrowerDetails().getSsiDetails().getIndustryNature());
				} else {
					ssiDetails.setString(14, null);
				}
				Log.log(5, "ApplicationDAO", "submitApp",
						"nature :" + apps.getBorrowerDetails().getSsiDetails().getIndustryNature());

				if ((apps.getBorrowerDetails().getSsiDetails().getIndustrySector() != null)
						&& (!apps.getBorrowerDetails().getSsiDetails().getIndustrySector().equals(""))) {
					ssiDetails.setString(15, apps.getBorrowerDetails().getSsiDetails().getIndustrySector());
				} else {
					ssiDetails.setString(15, null);
				}
				Log.log(5, "ApplicationDAO", "submitApp",
						"sector : " + apps.getBorrowerDetails().getSsiDetails().getIndustrySector());

				
				

				ssiDetails.setString(16, createdBy);
				Log.log(5, "ApplicationDAO", "submitApp", "userId :" + createdBy);

				if ((apps.getBorrowerDetails().getSsiDetails().getEnterprise() != null)
						&& (!apps.getBorrowerDetails().getSsiDetails().getEnterprise().equals(""))) {
					ssiDetails.setString(17, apps.getBorrowerDetails().getSsiDetails().getEnterprise());
				} else {
					ssiDetails.setString(17, "N");
				}

				if (apps.getBorrowerDetails().getSsiDetails().getProjectedSalesTurnover() == 0.0D) {
					ssiDetails.setNull(18, 8);
				} else
					ssiDetails.setDouble(18, apps.getBorrowerDetails().getSsiDetails().getProjectedSalesTurnover());

				Log.log(5, "ApplicationDAO", "submitApp",
						"sales :" + apps.getBorrowerDetails().getSsiDetails().getProjectedSalesTurnover());

				if (apps.getOsMLI() == 0.0D) {
					ssiDetails.setNull(19, 8);
				} else
					ssiDetails.setDouble(19, apps.getOsMLI());

				Log.log(5, "ApplicationDAO", "submitApp", "sales :" + apps.getOsMLI());

				if ((apps.getBorrowerDetails().getSsiDetails().getActivityType() != null)
						&& (!apps.getBorrowerDetails().getSsiDetails().getActivityType().equals(""))) {
					ssiDetails.setString(20, apps.getBorrowerDetails().getSsiDetails().getActivityType());
				} else {
					ssiDetails.setString(20, null);
				}
				Log.log(5, "ApplicationDAO", "submitApp",
						"activity Type :" + apps.getBorrowerDetails().getSsiDetails().getActivityType());

			
				ssiDetails.executeQuery();

				int ssiDetailsValue = ssiDetails.getInt(1);
				Log.log(5, "ApplicationDAO", "submitApp", "SSi Details result :" + ssiDetailsValue);

				if (ssiDetailsValue == 1) {
					String error = ssiDetails.getString(3);

					ssiDetails.close();
					ssiDetails = null;

					connection.rollback();

					Log.log(2, "ApplicationDAO", "submitApp", "SSI Detail Exception :" + error);

					throw new DatabaseException(error);
				}
				int ssiRefNo = ssiDetails.getInt(2);
				apps.getBorrowerDetails().getSsiDetails().setBorrowerRefNo(ssiRefNo);

				ssiDetails.close();
				ssiDetails = null;

				ssiDetail = null;
				System.out.println("Diksha---4");
			}
		} catch (SQLException sqlException) {
			Log.log(2, "ApplicationDAO", "submitApp", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(2, "ApplicationDAO", "submitApp", ignore.getMessage());
			}

			throw new DatabaseException(sqlException.getMessage());
		}

		return ssiDetail;
	}
	
	public SSIDetails submitMudraApp(Application apps, String createdBy, Connection connection) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "submitApp", "Entered");

		SSIDetails ssiDetail = new SSIDetails();

		String appLoanType = apps.getLoanType();
		try {
			String coveredValue = apps.getBorrowerDetails().getPreviouslyCovered();
			if (coveredValue.equals("Y")) {
				String cgbid = apps.getBorrowerDetails().getSsiDetails().getCgbid();
				String cgpan = apps.getCgpan();
				if ((cgbid != null) && (!cgbid.equals(""))) {
					CallableStatement ssiRefNoForCgbid = connection
							.prepareCall("{call funcGetSSIRefNoforBID(?,?,?,?)}");
					ssiRefNoForCgbid.registerOutParameter(1, 4);
					ssiRefNoForCgbid.registerOutParameter(2, 4);
					ssiRefNoForCgbid.registerOutParameter(4, 12);
					ssiRefNoForCgbid.setString(3, apps.getBorrowerDetails().getSsiDetails().getCgbid());
					ssiRefNoForCgbid.executeQuery();
					int ssiRefNoForCgbidValue = ssiRefNoForCgbid.getInt(1);

					if (ssiRefNoForCgbidValue == 1) {
						String error = ssiRefNoForCgbid.getString(4);
						ssiRefNoForCgbid.close();
						ssiRefNoForCgbid = null;
						Log.log(2, "ApplicationDAO", "submitApp", error);
						connection.rollback();
						throw new DatabaseException(ssiRefNoForCgbid.getString(4));
					}

					apps.getBorrowerDetails().getSsiDetails().setBorrowerRefNo(ssiRefNoForCgbid.getInt(2));

					ssiRefNoForCgbid.close();
					ssiRefNoForCgbid = null;
				} else if ((cgpan != null) && (!cgpan.equals(""))) {
					CallableStatement ssiRefNoForCgpan = connection
							.prepareCall("{call funcGetSSIRefNoforCGPAN(?,?,?,?)}");

					ssiRefNoForCgpan.registerOutParameter(1, 4);
					ssiRefNoForCgpan.registerOutParameter(2, 4);
					ssiRefNoForCgpan.registerOutParameter(4, 12);

					ssiRefNoForCgpan.setString(3, apps.getCgpan());

					ssiRefNoForCgpan.executeQuery();
					int ssiRefNoForCgpanValue = ssiRefNoForCgpan.getInt(1);

					if (ssiRefNoForCgpanValue == 1) {
						String error = ssiRefNoForCgpan.getString(4);
						ssiRefNoForCgpan.close();
						ssiRefNoForCgpan = null;

						connection.rollback();

						Log.log(2, "ApplicationDAO", "submitApp", error);
						throw new DatabaseException(error);
					}

					apps.getBorrowerDetails().getSsiDetails().setBorrowerRefNo(ssiRefNoForCgpan.getInt(2));

					ssiRefNoForCgpan.close();
					ssiRefNoForCgpan = null;
				}

				Log.log(4, "ApplicationDAO", "submitApp", "Entering the loop for gettin the Sub Scheme");

				RiskManagementProcessor rpProcessor = new RiskManagementProcessor();
				SubSchemeValues subSchemeValues = new SubSchemeValues();

				BorrowerDetails borrowerDetails = viewBorrowerDetails(
						apps.getBorrowerDetails().getSsiDetails().getBorrowerRefNo());
				ssiDetail = borrowerDetails.getSsiDetails();
				Application tempApplication = new Application();
				tempApplication.setBorrowerDetails(borrowerDetails);
				tempApplication.setMliID(apps.getMliID());
				double balanceAppAmt = getBalanceApprovedAmt(tempApplication);

				if ((apps.getLoanType().equals("TC")) || (apps.getLoanType().equals("CC"))) {
					Log.log(5, "ApplicationDAO", "submitApp",
							"tc crdit amount :" + apps.getTermLoan().getCreditGuaranteed());
					if (apps.getTermLoan().getCreditGuaranteed() > balanceAppAmt) {
						throw new DatabaseException(
								"Credit to be Guaranteed Amount should be within the eligible amount available for Guarantee :"
										+ balanceAppAmt);
					}
				} else if (apps.getLoanType().equals("WC")) {
					Log.log(5, "ApplicationDAO", "submitApp", "wc enhancecrdit amount :"
							+ apps.getWc().getEnhancedFundBased() + apps.getWc().getEnhancedNonFundBased());
					Log.log(5, "ApplicationDAO", "submitApp", "wc crdit amount :" + apps.getWc().getCreditFundBased()
							+ apps.getWc().getCreditNonFundBased());
					if ((apps.getWc().getEnhancedFundBased() != 0.0D)
							|| (apps.getWc().getEnhancedNonFundBased() != 0.0D)) {
						Log.log(5, "ApplicationDAO", "submitApp",
								"entering enhanced " + apps.getWc().getEnhancementDate());
						if (apps.getWc().getEnhancedFundBased()
								+ apps.getWc().getEnhancedNonFundBased() > balanceAppAmt) {
							throw new DatabaseException(
									"Total Credit to be Guaranteed Amount should be within the eligible amount available for Guarantee :"
											+ balanceAppAmt);
						}

					} else if (apps.getWc().getCreditFundBased()
							+ apps.getWc().getCreditNonFundBased() > balanceAppAmt) {
						throw new DatabaseException(
								"Credit to be Guaranteed8 Amount should be within the eligible amount available for Guarantee :"
										+ balanceAppAmt);
					}
				} else if (apps.getLoanType().equals("BO")) {
					Log.log(5, "ApplicationDAO", "submitApp",
							"both crdit amount :" + apps.getTermLoan().getCreditGuaranteed()
									+ apps.getWc().getCreditFundBased() + apps.getWc().getCreditNonFundBased());
					if (apps.getTermLoan().getCreditGuaranteed() + apps.getWc().getCreditFundBased()
							+ apps.getWc().getCreditNonFundBased() > balanceAppAmt) {
						throw new DatabaseException(
								"Credit to be Guaranteed11 Amount should be within the eligible amount available for Guarantee :"
										+ balanceAppAmt);
					}

				}

			} else {
				Log.log(4, "ApplicationDAO", "submitApp", "Entering the loop for SSI Detail Insertion");

				double balanceAppAmt = getBalanceApprovedAmt(apps);

				if ((apps.getLoanType().equals("TC")) || (apps.getLoanType().equals("CC"))) {
					if (apps.getTermLoan().getCreditGuaranteed() > balanceAppAmt) {
						throw new DatabaseException(
								"Credit to be Guaranteed22 Amount should be within the eligible amount available for Guarantee :"
										+ balanceAppAmt);
					}
				} else if (apps.getLoanType().equals("WC")) {
					if (apps.getWc().getCreditFundBased() + apps.getWc().getCreditNonFundBased() > balanceAppAmt) {
						throw new DatabaseException(
								"Credit to be Guaranteed33 Amount should be within the eligible amount available for Guarantee :"
										+ balanceAppAmt);
					}
				} else if (apps.getLoanType().equals("BO")) {
					if (apps.getTermLoan().getCreditGuaranteed() + apps.getWc().getCreditFundBased()
							+ apps.getWc().getCreditNonFundBased() > balanceAppAmt) {
						throw new DatabaseException(
								"Credit to be Guaranteed Amount should be within the eligible amount available for Guarantee :"
										+ balanceAppAmt);
					}

				}

				CallableStatement ssiDetails = connection
						.prepareCall("{call FUNCINSERTSSIDETAIL_MUDRAEGS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

				ssiDetails.registerOutParameter(1, 4);
				ssiDetails.registerOutParameter(2, 4);
				ssiDetails.registerOutParameter(3, 12);

				/*
				 * ssiDetails.setString(3, coveredValue);
				 * 
				 * ssiDetails.setString(4, apps.getBorrowerDetails() .getAssistedByBank());
				 * 
				 * Log.log(5, "ApplicationDAO", "submitApp",
				 * "apps.getBorrowerDetails().getAssistedByBank()" +
				 * apps.getBorrowerDetails().getAssistedByBank());
				 * 
				 * ssiDetails.setNull(5, 12);
				 * 
				 * String npaValue = apps.getBorrowerDetails().getNpa(); ssiDetails.setString(6,
				 * npaValue);
				 * 
				 * Log.log(5, "ApplicationDAO", "submitApp", "NPA :" + npaValue);
				 */
				if ((apps.getBorrowerDetails().getSsiDetails().getConstitution() != null)
						&& (!apps.getBorrowerDetails().getSsiDetails().getConstitution().equals(""))) {
					ssiDetails.setString(4, apps.getBorrowerDetails().getSsiDetails().getConstitution());
				} else {
					ssiDetails.setString(4, null);
				}
				Log.log(5, "ApplicationDAO", "submitApp",
						"Const :" + apps.getBorrowerDetails().getSsiDetails().getConstitution());

				ssiDetails.setString(5, apps.getBorrowerDetails().getSsiDetails().getSsiType());

				Log.log(5, "ApplicationDAO", "submitApp",
						"ssi Type :" + apps.getBorrowerDetails().getSsiDetails().getSsiType());

				ssiDetails.setString(6, apps.getBorrowerDetails().getSsiDetails().getSsiName().toUpperCase());

				Log.log(5, "ApplicationDAO", "submitApp",
						"ssi name :" + apps.getBorrowerDetails().getSsiDetails().getSsiName());

				/*
				 * if ((apps.getBorrowerDetails().getSsiDetails().getRegNo() != null) &&
				 * (!apps.getBorrowerDetails().getSsiDetails() .getRegNo().equals(""))) {
				 * ssiDetails.setString(10, apps.getBorrowerDetails()
				 * .getSsiDetails().getRegNo()); } else { ssiDetails.setString(10, null); }
				 */
				/*
				 * Log.log(5, "ApplicationDAO", "submitApp", "reg no :" +
				 * apps.getBorrowerDetails().getSsiDetails().getRegNo());
				 * 
				 * ssiDetails.setNull(11, 91);
				 */

				if ((apps.getBorrowerDetails().getSsiDetails().getSsiITPan() != null)
						&& (!apps.getBorrowerDetails().getSsiDetails().getSsiITPan().equals(""))) {
					ssiDetails.setString(7, apps.getBorrowerDetails().getSsiDetails().getSsiITPan());
				} else {
					ssiDetails.setString(7, null);
				}
				Log.log(5, "ApplicationDAO", "submitApp",
						"ssi ITPAN :" + apps.getBorrowerDetails().getSsiDetails().getSsiITPan());

				if (apps.getBorrowerDetails().getSsiDetails().getEmployeeNos() == 0) {
					ssiDetails.setNull(8, 4);
				} else
					ssiDetails.setInt(8, apps.getBorrowerDetails().getSsiDetails().getEmployeeNos());

				Log.log(5, "ApplicationDAO", "submitApp",
						"employee nos :" + apps.getBorrowerDetails().getSsiDetails().getEmployeeNos());

				/*
				 * if (apps.getBorrowerDetails().getSsiDetails() .getProjectedExports() == 0.0D)
				 * { ssiDetails.setNull(16, 8); } else ssiDetails.setDouble(16,
				 * apps.getBorrowerDetails() .getSsiDetails().getProjectedExports());
				 * 
				 * Log.log(5, "ApplicationDAO", "submitApp", "exports :" +
				 * apps.getBorrowerDetails().getSsiDetails() .getProjectedExports());
				 */

				ssiDetails.setString(9, apps.getBorrowerDetails().getSsiDetails().getAddress().toUpperCase());
				Log.log(5, "ApplicationDAO", "submitApp",
						"Address :" + apps.getBorrowerDetails().getSsiDetails().getAddress());

				if ((apps.getBorrowerDetails().getSsiDetails().getCity() != null)
						&& (!apps.getBorrowerDetails().getSsiDetails().getCity().equals(""))) {
					ssiDetails.setString(10, apps.getBorrowerDetails().getSsiDetails().getCity().toUpperCase());
				} else {
					ssiDetails.setString(10, null);
				}
				Log.log(5, "ApplicationDAO", "submitApp",
						"city :" + apps.getBorrowerDetails().getSsiDetails().getCity());

				ssiDetails.setString(11, apps.getBorrowerDetails().getSsiDetails().getPincode());
				Log.log(5, "ApplicationDAO", "submitApp",
						"pincode :" + apps.getBorrowerDetails().getSsiDetails().getPincode());

				/* ssiDetails.setString(20, "Y"); */

				if ((apps.getBorrowerDetails().getSsiDetails().getDistrict() != null)
						&& (!apps.getBorrowerDetails().getSsiDetails().getDistrict().equals(""))) {
					ssiDetails.setString(12, apps.getBorrowerDetails().getSsiDetails().getDistrict());
				} else {
					ssiDetails.setString(12, null);
				}

				Log.log(5, "ApplicationDAO", "submitApp",
						"district :" + apps.getBorrowerDetails().getSsiDetails().getDistrict());

				ssiDetails.setString(13, apps.getBorrowerDetails().getSsiDetails().getState());
				Log.log(5, "ApplicationDAO", "submitApp",
						"State :" + apps.getBorrowerDetails().getSsiDetails().getState());

				if ((apps.getBorrowerDetails().getSsiDetails().getIndustryNature() != null)
						&& (!apps.getBorrowerDetails().getSsiDetails().getIndustryNature().equals(""))) {
					ssiDetails.setString(14, apps.getBorrowerDetails().getSsiDetails().getIndustryNature());
				} else {
					ssiDetails.setString(14, null);
				}
				Log.log(5, "ApplicationDAO", "submitApp",
						"nature :" + apps.getBorrowerDetails().getSsiDetails().getIndustryNature());

				if ((apps.getBorrowerDetails().getSsiDetails().getIndustrySector() != null)
						&& (!apps.getBorrowerDetails().getSsiDetails().getIndustrySector().equals(""))) {
					ssiDetails.setString(15, apps.getBorrowerDetails().getSsiDetails().getIndustrySector());
				} else {
					ssiDetails.setString(15, null);
				}
				Log.log(5, "ApplicationDAO", "submitApp",
						"sector : " + apps.getBorrowerDetails().getSsiDetails().getIndustrySector());

				/*
				 * if (apps.getBorrowerDetails().getOsAmt() == 0.0D) { ssiDetails.setNull(25,
				 * 8); } else ssiDetails.setDouble(25, apps.getBorrowerDetails() .getOsAmt());
				 * 
				 * Log.log(5, "ApplicationDAO", "submitApp", "os amt :" +
				 * apps.getBorrowerDetails().getOsAmt());
				 */

				/*
				 * MCGFDetails mcgfDetails = apps.getMCGFDetails(); if (mcgfDetails != null) {
				 * ssiDetails.setString(26, "Y"); } else { ssiDetails.setString(26, "N"); }
				 */

				ssiDetails.setString(16, createdBy);
				Log.log(5, "ApplicationDAO", "submitApp", "userId :" + createdBy);

				if ((apps.getBorrowerDetails().getSsiDetails().getEnterprise() != null)
						&& (!apps.getBorrowerDetails().getSsiDetails().getEnterprise().equals(""))) {
					ssiDetails.setString(17, apps.getBorrowerDetails().getSsiDetails().getEnterprise());
				} else {
					ssiDetails.setString(17, "N");
				}

				if (apps.getBorrowerDetails().getSsiDetails().getProjectedSalesTurnover() == 0.0D) {
					ssiDetails.setNull(18, 8);
				} else
					ssiDetails.setDouble(18, apps.getBorrowerDetails().getSsiDetails().getProjectedSalesTurnover());

				Log.log(5, "ApplicationDAO", "submitApp",
						"sales :" + apps.getBorrowerDetails().getSsiDetails().getProjectedSalesTurnover());

				if (apps.getOsMLI() == 0.0D) {
					ssiDetails.setNull(19, 8);
				} else
					ssiDetails.setDouble(19, apps.getOsMLI());

				Log.log(5, "ApplicationDAO", "submitApp", "sales :" + apps.getOsMLI());

				if ((apps.getBorrowerDetails().getSsiDetails().getActivityType() != null)
						&& (!apps.getBorrowerDetails().getSsiDetails().getActivityType().equals(""))) {
					ssiDetails.setString(20, apps.getBorrowerDetails().getSsiDetails().getActivityType());
				} else {
					ssiDetails.setString(20, null);
				}
				Log.log(5, "ApplicationDAO", "submitApp",
						"activity Type :" + apps.getBorrowerDetails().getSsiDetails().getActivityType());

				
				if ((apps.getBorrowerDetails().getSsiDetails().getMudraType() != null)
						&& (!apps.getBorrowerDetails().getSsiDetails().getMudraType().equals(""))) {
					ssiDetails.setString(21, apps.getBorrowerDetails().getSsiDetails().getMudraType());
				} else {
					ssiDetails.setString(21, null);
				}
				Log.log(5, "ApplicationDAO", "submitApp",
						"activity Type :" + apps.getBorrowerDetails().getSsiDetails().getMudraType());
				
				if ((apps.getBorrowerDetails().getSsiDetails().getMudraITPANChk() != null)
						&& (!apps.getBorrowerDetails().getSsiDetails().getMudraITPANChk().equals(""))) {
					ssiDetails.setString(22, apps.getBorrowerDetails().getSsiDetails().getMudraITPANChk());
				} else {
					ssiDetails.setString(22, null);
				}
				Log.log(5, "ApplicationDAO", "submitApp",
						"activity Type :" + apps.getBorrowerDetails().getSsiDetails().getMudraITPANChk());
				
				
				

				
				/*
				 * if ((apps.getBorrowerDetails().getSsiDetails() .getUnitAssisted() != null) &&
				 * (!apps.getBorrowerDetails().getSsiDetails() .getUnitAssisted().equals(""))) {
				 * ssiDetails.setString(29, apps.getBorrowerDetails()
				 * .getSsiDetails().getUnitAssisted()); } else { ssiDetails.setString(29, "N");
				 * } if ((apps.getBorrowerDetails().getSsiDetails() .getConditionAccepted() !=
				 * null) && (!apps.getBorrowerDetails().getSsiDetails()
				 * .getConditionAccepted().equals(""))) { ssiDetails.setString(30,
				 * apps.getBorrowerDetails() .getSsiDetails().getConditionAccepted()); } else {
				 * ssiDetails.setString(30, "Y"); } if
				 * ((apps.getBorrowerDetails().getSsiDetails() .getWomenOperated() != null) &&
				 * (!apps.getBorrowerDetails().getSsiDetails() .getWomenOperated().equals("")))
				 * { ssiDetails.setString(31, apps.getBorrowerDetails()
				 * .getSsiDetails().getWomenOperated()); } else { ssiDetails.setString(31, "N");
				 * } Log.log(5, "ApplicationDAO", "submitApp", "SSI Details object :" +
				 * ssiDetails);
				 */
				ssiDetails.executeQuery();

				int ssiDetailsValue = ssiDetails.getInt(1);
				Log.log(5, "ApplicationDAO", "submitApp", "SSi Details result :" + ssiDetailsValue);

				if (ssiDetailsValue == 1) {
					String error = ssiDetails.getString(3);

					ssiDetails.close();
					ssiDetails = null;

					connection.rollback();

					Log.log(2, "ApplicationDAO", "submitApp", "SSI Detail Exception :" + error);

					throw new DatabaseException(error);
				}
				int ssiRefNo = ssiDetails.getInt(2);
				apps.getBorrowerDetails().getSsiDetails().setBorrowerRefNo(ssiRefNo);

				ssiDetails.close();
				ssiDetails = null;

				ssiDetail = null;
				System.out.println("Diksha---4");
			}
		} catch (SQLException sqlException) {
			Log.log(2, "ApplicationDAO", "submitApp", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(2, "ApplicationDAO", "submitApp", ignore.getMessage());
			}

			throw new DatabaseException(sqlException.getMessage());
		}

		return ssiDetail;
	}

	public SSIDetails submitRSFApp(Application apps, String createdBy, Connection connection) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "submitRSFApp", "Entered");

		SSIDetails ssiDetail = new SSIDetails();

		String appLoanType = apps.getLoanType();
		try {
			String coveredValue = apps.getBorrowerDetails().getPreviouslyCovered();

			if (coveredValue.equals("Y")) {
				String cgbid = apps.getBorrowerDetails().getSsiDetails().getCgbid();
				String cgpan = apps.getCgpan();
				if ((cgbid != null) && (!cgbid.equals(""))) {
					CallableStatement ssiRefNoForCgbid = connection
							.prepareCall("{call funcGetSSIRefNoforBID(?,?,?,?)}");

					ssiRefNoForCgbid.registerOutParameter(1, 4);
					ssiRefNoForCgbid.registerOutParameter(2, 4);
					ssiRefNoForCgbid.registerOutParameter(4, 12);

					ssiRefNoForCgbid.setString(3, apps.getBorrowerDetails().getSsiDetails().getCgbid());

					ssiRefNoForCgbid.executeQuery();
					int ssiRefNoForCgbidValue = ssiRefNoForCgbid.getInt(1);

					if (ssiRefNoForCgbidValue == 1) {
						String error = ssiRefNoForCgbid.getString(4);

						ssiRefNoForCgbid.close();
						ssiRefNoForCgbid = null;

						Log.log(2, "ApplicationDAO", "submitRSFApp", error);

						connection.rollback();

						throw new DatabaseException(ssiRefNoForCgbid.getString(4));
					}

					apps.getBorrowerDetails().getSsiDetails().setBorrowerRefNo(ssiRefNoForCgbid.getInt(2));

					ssiRefNoForCgbid.close();
					ssiRefNoForCgbid = null;
				} else if ((cgpan != null) && (!cgpan.equals(""))) {
					CallableStatement ssiRefNoForCgpan = connection
							.prepareCall("{call funcGetSSIRefNoforCGPAN(?,?,?,?)}");

					ssiRefNoForCgpan.registerOutParameter(1, 4);
					ssiRefNoForCgpan.registerOutParameter(2, 4);
					ssiRefNoForCgpan.registerOutParameter(4, 12);

					ssiRefNoForCgpan.setString(3, apps.getCgpan());

					ssiRefNoForCgpan.executeQuery();
					int ssiRefNoForCgpanValue = ssiRefNoForCgpan.getInt(1);

					if (ssiRefNoForCgpanValue == 1) {
						String error = ssiRefNoForCgpan.getString(4);
						ssiRefNoForCgpan.close();
						ssiRefNoForCgpan = null;

						connection.rollback();

						Log.log(2, "ApplicationDAO", "submitRSFApp", error);
						throw new DatabaseException(error);
					}

					apps.getBorrowerDetails().getSsiDetails().setBorrowerRefNo(ssiRefNoForCgpan.getInt(2));

					ssiRefNoForCgpan.close();
					ssiRefNoForCgpan = null;
				}

				Log.log(4, "ApplicationDAO", "submitRSFApp", "Entering the loop for gettin the Sub Scheme");

				RiskManagementProcessor rpProcessor = new RiskManagementProcessor();
				SubSchemeValues subSchemeValues = new SubSchemeValues();

				BorrowerDetails borrowerDetails = viewBorrowerDetails(
						apps.getBorrowerDetails().getSsiDetails().getBorrowerRefNo());
				ssiDetail = borrowerDetails.getSsiDetails();
				Application tempApplication = new Application();
				tempApplication.setBorrowerDetails(borrowerDetails);
				tempApplication.setMliID(apps.getMliID());
				double balanceAppAmt = getBalanceRsfApprovedAmt(tempApplication);

				if ((apps.getLoanType().equals("TC")) || (apps.getLoanType().equals("CC"))) {
					Log.log(5, "ApplicationDAO", "submitRSFApp",
							"tc crdit amount :" + apps.getTermLoan().getCreditGuaranteed());
					if (apps.getTermLoan().getCreditGuaranteed() > balanceAppAmt) {
						throw new DatabaseException(
								"Credit to be Guaranteed44 Amount should be within the eligible amount available for Guarantee :"
										+ balanceAppAmt);
					}
				} else if (apps.getLoanType().equals("WC")) {
					Log.log(5, "ApplicationDAO", "submitRSFApp", "wc enhancecrdit amount :"
							+ apps.getWc().getEnhancedFundBased() + apps.getWc().getEnhancedNonFundBased());
					Log.log(5, "ApplicationDAO", "submitRSFApp", "wc crdit amount :" + apps.getWc().getCreditFundBased()
							+ apps.getWc().getCreditNonFundBased());
					if ((apps.getWc().getEnhancedFundBased() != 0.0D)
							|| (apps.getWc().getEnhancedNonFundBased() != 0.0D)) {
						Log.log(5, "ApplicationDAO", "submitRSFApp",
								"entering enhanced " + apps.getWc().getEnhancementDate());
						if (apps.getWc().getEnhancedFundBased()
								+ apps.getWc().getEnhancedNonFundBased() > balanceAppAmt) {
							throw new DatabaseException(
									"Total Credit to be Guaranteed Amount should be within the eligible amount available for Guarantee :"
											+ balanceAppAmt);
						}

					} else if (apps.getWc().getCreditFundBased()
							+ apps.getWc().getCreditNonFundBased() > balanceAppAmt) {
						throw new DatabaseException(
								"Credit to be Guaranteed 90 Amount should be within the eligible amount available for Guarantee :"
										+ balanceAppAmt);
					}
				} else if (apps.getLoanType().equals("BO")) {
					Log.log(5, "ApplicationDAO", "submitRSFApp",
							"both crdit amount :" + apps.getTermLoan().getCreditGuaranteed()
									+ apps.getWc().getCreditFundBased() + apps.getWc().getCreditNonFundBased());
					if (apps.getTermLoan().getCreditGuaranteed() + apps.getWc().getCreditFundBased()
							+ apps.getWc().getCreditNonFundBased() > balanceAppAmt) {
						throw new DatabaseException(
								"Credit to be Guaranteed777 Amount should be within the eligible amount available for Guarantee :"
										+ balanceAppAmt);
					}

				}

			} else {
				Log.log(4, "ApplicationDAO", "submitRSFApp", "Entering the loop for SSI Detail Insertion");

				double balanceAppAmt = getBalanceRsfApprovedAmt(apps);

				if ((apps.getLoanType().equals("TC")) || (apps.getLoanType().equals("CC"))) {
					if (apps.getTermLoan().getCreditGuaranteed() > balanceAppAmt) {
						throw new DatabaseException(
								"Credit to be Guaranteed999 Amount should be within the eligible amount available for Guarantee :"
										+ balanceAppAmt);
					}
				} else if (apps.getLoanType().equals("WC")) {
					if (apps.getWc().getCreditFundBased() + apps.getWc().getCreditNonFundBased() > balanceAppAmt) {
						throw new DatabaseException(
								"Credit to be Guaranteed5656 Amount should be within the eligible amount available for Guarantee :"
										+ balanceAppAmt);
					}
				} else if (apps.getLoanType().equals("BO")) {
					if (apps.getTermLoan().getCreditGuaranteed() + apps.getWc().getCreditFundBased()
							+ apps.getWc().getCreditNonFundBased() > balanceAppAmt) {
						throw new DatabaseException(
								"Credit to be Guaranteed Amount should be within the eligible amount available for Guarantee :"
										+ balanceAppAmt);
					}

				}

				CallableStatement ssiDetails = connection.prepareCall(
						"{call funcInsertSSIDetail(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

				ssiDetails.registerOutParameter(1, 4);
				ssiDetails.registerOutParameter(2, 4);
				ssiDetails.registerOutParameter(3, 12);

				ssiDetails.setString(4, coveredValue);

				ssiDetails.setString(5, apps.getAssistedByBank());

				Log.log(5, "ApplicationDAO", "submitRSFApp", "apps.getBorrowerDetails().getAssistedByBank()"
						+ apps.getAssistedByBank());

				ssiDetails.setNull(6, 12);

				String npaValue = apps.getNpa();
				ssiDetails.setString(7, npaValue);

				Log.log(5, "ApplicationDAO", "submitRSFApp", "NPA :" + npaValue);

				if ((apps.getConstitution() != null)
						&& (!apps.getConstitution().equals(""))) {
					ssiDetails.setString(8, apps.getConstitution());
				} else {
					ssiDetails.setString(8, null);
				}
				Log.log(5, "ApplicationDAO", "submitRSFApp",
						"Const :" + apps.getConstitution());

				ssiDetails.setString(9, apps.getSsiType());

				Log.log(5, "ApplicationDAO", "submitRSFApp",
						"ssi Type :" + apps.getSsiType());

				ssiDetails.setString(10, apps.getSsiName().toUpperCase());

				Log.log(5, "ApplicationDAO", "submitRSFApp",
						"ssi name :" + apps.getSsiName());

				if ((apps.getRegNo() != null)
						&& (!apps.getRegNo().equals(""))) {
					ssiDetails.setString(11, apps.getRegNo());
				} else {
					ssiDetails.setString(11, null);
				}

				Log.log(5, "ApplicationDAO", "submitRSFApp",
						"reg no :" + apps.getRegNo());

				ssiDetails.setNull(12, 91);

				if ((apps.getSsiITPan() != null)
						&& (!apps.getSsiITPan().equals(""))) {
					ssiDetails.setString(13, apps.getSsiITPan());
				} else {
					ssiDetails.setString(13, null);
				}
				Log.log(5, "ApplicationDAO", "submitRSFApp",
						"ssi ITPAN :" + apps.getSsiITPan());

				if ((apps.getActivityType() != null)
						&& (!apps.getActivityType().equals(""))) {
					ssiDetails.setString(14, apps.getActivityType());
				} else {
					ssiDetails.setString(14, null);
				}
				Log.log(5, "ApplicationDAO", "submitRSFApp",
						"activity Type :" + apps.getActivityType());

				if (apps.getEmployeeNos() == 0) {
					ssiDetails.setNull(15, 4);
				} else
					ssiDetails.setInt(15, apps.getEmployeeNos());

				Log.log(5, "ApplicationDAO", "submitRSFApp",
						"employee nos :" + apps.getEmployeeNos());

				if (apps.getProjectedSalesTurnover() == 0.0D) {
					ssiDetails.setNull(16, 8);
				} else
					ssiDetails.setDouble(16, apps.getProjectedSalesTurnover());

				Log.log(5, "ApplicationDAO", "submitRSFApp",
						"sales :" + apps.getProjectedSalesTurnover());

				if (apps.getProjectedExports() == 0.0D) {
					ssiDetails.setNull(17, 8);
				} else
					ssiDetails.setDouble(17, apps.getProjectedExports());

				Log.log(5, "ApplicationDAO", "submitRSFApp",
						"exports :" + apps.getProjectedExports());

				ssiDetails.setString(18, apps.getAddress().toUpperCase());
				Log.log(5, "ApplicationDAO", "submitRSFApp",
						"Address :" + apps.getAddress());

				if ((apps.getCity() != null)
						&& (!apps.getCity().equals(""))) {
					ssiDetails.setString(19, apps.getCity().toUpperCase());
				} else {
					ssiDetails.setString(19, null);
				}
				Log.log(5, "ApplicationDAO", "submitRSFApp",
						"city :" + apps.getCity());

				ssiDetails.setString(20, apps.getPincode());
				Log.log(5, "ApplicationDAO", "submitRSFApp",
						"pincode :" + apps.getPincode());

				ssiDetails.setString(21, "Y");

				if ((apps.getDistrict() != null)
						&& (!apps.getDistrict().equals(""))) {
					ssiDetails.setString(22, apps.getDistrict());
				} else {
					ssiDetails.setString(22, null);
				}

				Log.log(5, "ApplicationDAO", "submitRSFApp",
						"district :" + apps.getDistrict());

				ssiDetails.setString(23, apps.getState());
				Log.log(5, "ApplicationDAO", "submitRSFApp",
						"State :" + apps.getState());

				if ((apps.getIndustryNature() != null)
						&& (!apps.getIndustryNature().equals(""))) {
					ssiDetails.setString(24, apps.getIndustryNature());
				} else {
					ssiDetails.setString(24, null);
				}
				Log.log(5, "ApplicationDAO", "submitRSFApp",
						"nature :" + apps.getBorrowerDetails().getSsiDetails().getIndustryNature());

				if ((apps.getBorrowerDetails().getSsiDetails().getIndustrySector() != null)
						&& (!apps.getBorrowerDetails().getSsiDetails().getIndustrySector().equals(""))) {
					ssiDetails.setString(25, apps.getIndustrySector());
				} else {
					ssiDetails.setString(25, null);
				}
				Log.log(5, "ApplicationDAO", "submitRSFApp",
						"sector : " + apps.getIndustrySector());

				if (apps.getBorrowerDetails().getOsAmt() == 0.0D) {
					ssiDetails.setNull(26, 8);
				} else
					ssiDetails.setDouble(26, apps.getBorrowerDetails().getOsAmt());

				Log.log(5, "ApplicationDAO", "submitRSFApp", "os amt :" + apps.getBorrowerDetails().getOsAmt());

				MCGFDetails mcgfDetails = apps.getMCGFDetails();
				if (mcgfDetails != null) {
					ssiDetails.setString(27, "Y");
				} else {
					ssiDetails.setString(27, "N");
				}

				ssiDetails.setString(28, createdBy);
				Log.log(5, "ApplicationDAO", "submitRSFApp", "userId :" + createdBy);

				if ((apps.getEnterprise() != null)
						&& (!apps.getEnterprise().equals(""))) {
					ssiDetails.setString(29, apps.getBorrowerDetails().getSsiDetails().getEnterprise());
				} else {
					ssiDetails.setString(29, "N");
				}
				if ((apps.getBorrowerDetails().getSsiDetails().getUnitAssisted() != null)
						&& (!apps.getBorrowerDetails().getSsiDetails().getUnitAssisted().equals(""))) {
					ssiDetails.setString(30, apps.getBorrowerDetails().getSsiDetails().getUnitAssisted());
				} else {
					ssiDetails.setString(30, "N");
				}
				if ((apps.getBorrowerDetails().getSsiDetails().getConditionAccepted() != null)
						&& (!apps.getBorrowerDetails().getSsiDetails().getConditionAccepted().equals(""))) {
					ssiDetails.setString(31, apps.getBorrowerDetails().getSsiDetails().getConditionAccepted());
				} else {
					ssiDetails.setString(31, "Y");
				}
				if ((apps.getBorrowerDetails().getSsiDetails().getWomenOperated() != null)
						&& (!apps.getBorrowerDetails().getSsiDetails().getWomenOperated().equals(""))) {
					ssiDetails.setString(32, apps.getBorrowerDetails().getSsiDetails().getWomenOperated());
				} else {
					ssiDetails.setString(32, "N");
				}
				Log.log(5, "ApplicationDAO", "submitRSFApp", "SSI Details object :" + ssiDetails);

				ssiDetails.executeQuery();

				int ssiDetailsValue = ssiDetails.getInt(1);
				Log.log(5, "ApplicationDAO", "submitRSFApp", "SSi Details result :" + ssiDetailsValue);

				if (ssiDetailsValue == 1) {
					String error = ssiDetails.getString(3);

					ssiDetails.close();
					ssiDetails = null;

					connection.rollback();

					Log.log(2, "ApplicationDAO", "submitRSFApp", "SSI Detail Exception :" + error);

					throw new DatabaseException(error);
				}
				int ssiRefNo = ssiDetails.getInt(2);
				apps.getBorrowerDetails().getSsiDetails().setBorrowerRefNo(ssiRefNo);

				ssiDetails.close();
				ssiDetails = null;

				ssiDetail = null;
			}
		} catch (SQLException sqlException) {
			Log.log(2, "ApplicationDAO", "submitRSFApp", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(2, "ApplicationDAO", "submitRSFApp", ignore.getMessage());
			}

			throw new DatabaseException(sqlException.getMessage());
		}

		return ssiDetail;
	}

	public SSIDetails submitRSF2App(Application apps, String createdBy, Connection connection)
			throws DatabaseException {
		Log.log(4, "ApplicationDAO", "submitRSFApp", "Entered");
		// System.out.println("ApplicationDAO Line number 577submitRSFAppEntered");

		SSIDetails ssiDetail = new SSIDetails();

		String appLoanType = apps.getLoanType();
		// System.out.println("Line number applicationdao 1061 in ApplicationDAO"
		// + appLoanType);
		try {
			String coveredValue = apps.getBorrowerDetails().getPreviouslyCovered();

			if (coveredValue.equals("Y")) {
				String cgbid = apps.getBorrowerDetails().getSsiDetails().getCgbid();
				String cgpan = apps.getCgpan();
				if ((cgbid != null) && (!cgbid.equals(""))) {
					CallableStatement ssiRefNoForCgbid = connection
							.prepareCall("{?=call funcGetSSIRefNoforBID(?,?,?)}");

					ssiRefNoForCgbid.registerOutParameter(1, 4);
					ssiRefNoForCgbid.registerOutParameter(2, 4);
					ssiRefNoForCgbid.registerOutParameter(4, 12);

					ssiRefNoForCgbid.setString(3, apps.getBorrowerDetails().getSsiDetails().getCgbid());

					ssiRefNoForCgbid.executeQuery();
					int ssiRefNoForCgbidValue = ssiRefNoForCgbid.getInt(1);

					if (ssiRefNoForCgbidValue == 1) {
						String error = ssiRefNoForCgbid.getString(4);

						ssiRefNoForCgbid.close();
						ssiRefNoForCgbid = null;

						Log.log(2, "ApplicationDAO", "submitRSFApp", error);

						connection.rollback();

						throw new DatabaseException(ssiRefNoForCgbid.getString(4));
					}

					apps.getBorrowerDetails().getSsiDetails().setBorrowerRefNo(ssiRefNoForCgbid.getInt(2));

					ssiRefNoForCgbid.close();
					ssiRefNoForCgbid = null;
				} else if ((cgpan != null) && (!cgpan.equals(""))) {
					CallableStatement ssiRefNoForCgpan = connection
							.prepareCall("{?=call funcGetSSIRefNoforCGPAN(?,?,?)}");

					ssiRefNoForCgpan.registerOutParameter(1, 4);
					ssiRefNoForCgpan.registerOutParameter(2, 4);
					ssiRefNoForCgpan.registerOutParameter(4, 12);

					ssiRefNoForCgpan.setString(3, apps.getCgpan());

					ssiRefNoForCgpan.executeQuery();
					int ssiRefNoForCgpanValue = ssiRefNoForCgpan.getInt(1);

					if (ssiRefNoForCgpanValue == 1) {
						String error = ssiRefNoForCgpan.getString(4);
						ssiRefNoForCgpan.close();
						ssiRefNoForCgpan = null;

						connection.rollback();

						Log.log(2, "ApplicationDAO", "submitRSFApp", error);
						throw new DatabaseException(error);
					}

					apps.getBorrowerDetails().getSsiDetails().setBorrowerRefNo(ssiRefNoForCgpan.getInt(2));

					ssiRefNoForCgpan.close();
					ssiRefNoForCgpan = null;
				}

				Log.log(4, "ApplicationDAO", "submitRSFApp", "Entering the loop for gettin the Sub Scheme");

				RiskManagementProcessor rpProcessor = new RiskManagementProcessor();
				SubSchemeValues subSchemeValues = new SubSchemeValues();

				BorrowerDetails borrowerDetails = viewBorrowerDetails(
						apps.getBorrowerDetails().getSsiDetails().getBorrowerRefNo());
				ssiDetail = borrowerDetails.getSsiDetails();
				Application tempApplication = new Application();
				tempApplication.setBorrowerDetails(borrowerDetails);
				tempApplication.setMliID(apps.getMliID());
				double balanceAppAmt = getBalanceRsfApprovedAmt(tempApplication);

				if ((apps.getLoanType().equals("TC")) || (apps.getLoanType().equals("CC"))) {
					Log.log(5, "ApplicationDAO", "submitRSFApp",
							"tc crdit amount :" + apps.getTermLoan().getCreditGuaranteed());
					if (apps.getTermLoan().getCreditGuaranteed() > balanceAppAmt) {
						throw new DatabaseException(
								"Credit to be Guaranteed44 Amount should be within the eligible amount available for Guarantee :"
										+ balanceAppAmt);
					}
				} else if (apps.getLoanType().equals("WC")) {
					Log.log(5, "ApplicationDAO", "submitRSFApp", "wc enhancecrdit amount :"
							+ apps.getWc().getEnhancedFundBased() + apps.getWc().getEnhancedNonFundBased());
					Log.log(5, "ApplicationDAO", "submitRSFApp", "wc crdit amount :" + apps.getWc().getCreditFundBased()
							+ apps.getWc().getCreditNonFundBased());
					if ((apps.getWc().getEnhancedFundBased() != 0.0D)
							|| (apps.getWc().getEnhancedNonFundBased() != 0.0D)) {
						Log.log(5, "ApplicationDAO", "submitRSFApp",
								"entering enhanced " + apps.getWc().getEnhancementDate());
						if (apps.getWc().getEnhancedFundBased()
								+ apps.getWc().getEnhancedNonFundBased() > balanceAppAmt) {
							throw new DatabaseException(
									"Total Credit to be Guaranteed Amount should be within the eligible amount available for Guarantee :"
											+ balanceAppAmt);
						}

					} else if (apps.getWc().getCreditFundBased()
							+ apps.getWc().getCreditNonFundBased() > balanceAppAmt) {
						throw new DatabaseException(
								"Credit to be Guaranteed 90 Amount should be within the eligible amount available for Guarantee :"
										+ balanceAppAmt);
					}
				} else if (apps.getLoanType().equals("BO")) {
					Log.log(5, "ApplicationDAO", "submitRSFApp",
							"both crdit amount :" + apps.getTermLoan().getCreditGuaranteed()
									+ apps.getWc().getCreditFundBased() + apps.getWc().getCreditNonFundBased());
					if (apps.getTermLoan().getCreditGuaranteed() + apps.getWc().getCreditFundBased()
							+ apps.getWc().getCreditNonFundBased() > balanceAppAmt) {
						throw new DatabaseException(
								"Credit to be Guaranteed777 Amount should be within the eligible amount available for Guarantee :"
										+ balanceAppAmt);
					}

				}

			} else {
				Log.log(4, "ApplicationDAO", "submitRSFApp", "Entering the loop for SSI Detail Insertion");

				double balanceAppAmt = getBalanceRsf2ApprovedAmt(apps);

				if ((apps.getLoanType().equals("TC")) || (apps.getLoanType().equals("CC"))) {
					if (apps.getTermLoan().getCreditGuaranteed() > balanceAppAmt) {
						throw new DatabaseException(
								"Credit to be Guaranteed9991 Amount should be within the eligible amount available for Guarantee :"
										+ balanceAppAmt);
					}
				} else if (apps.getLoanType().equals("WC")) {
					if (apps.getWc().getCreditFundBased() + apps.getWc().getCreditNonFundBased() > balanceAppAmt) {
						throw new DatabaseException(
								"Credit to be Guaranteed5656 Amount should be within the eligible amount available for Guarantee :"
										+ balanceAppAmt);
					}
				} else if (apps.getLoanType().equals("BO")) {
					if (apps.getTermLoan().getCreditGuaranteed() + apps.getWc().getCreditFundBased()
							+ apps.getWc().getCreditNonFundBased() > balanceAppAmt) {
						throw new DatabaseException(
								"Credit to be Guaranteed Amount should be within the eligible amount available for Guarantee :"
										+ balanceAppAmt);
					}

				}

				CallableStatement ssiDetails = connection.prepareCall(
						"{?=call funcInsertSSIDetail(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

				ssiDetails.registerOutParameter(1, 4);
				ssiDetails.registerOutParameter(2, 4);
				ssiDetails.registerOutParameter(32, 12);

				ssiDetails.setString(3, coveredValue);

				ssiDetails.setString(4, apps.getBorrowerDetails().getAssistedByBank());

				Log.log(5, "ApplicationDAO", "submitRSFApp", "apps.getBorrowerDetails().getAssistedByBank()"
						+ apps.getBorrowerDetails().getAssistedByBank());

				ssiDetails.setNull(5, 12);

				String npaValue = apps.getBorrowerDetails().getNpa();
				ssiDetails.setString(6, npaValue);

				Log.log(5, "ApplicationDAO", "submitRSFApp", "NPA :" + npaValue);

				if ((apps.getBorrowerDetails().getSsiDetails().getConstitution() != null)
						&& (!apps.getBorrowerDetails().getSsiDetails().getConstitution().equals(""))) {
					ssiDetails.setString(7, apps.getBorrowerDetails().getSsiDetails().getConstitution());
				} else {
					ssiDetails.setString(7, null);
				}
				Log.log(5, "ApplicationDAO", "submitRSFApp",
						"Const :" + apps.getBorrowerDetails().getSsiDetails().getConstitution());

				ssiDetails.setString(8, apps.getBorrowerDetails().getSsiDetails().getSsiType());

				Log.log(5, "ApplicationDAO", "submitRSFApp",
						"ssi Type :" + apps.getBorrowerDetails().getSsiDetails().getSsiType());

				ssiDetails.setString(9, apps.getBorrowerDetails().getSsiDetails().getSsiName().toUpperCase());

				Log.log(5, "ApplicationDAO", "submitRSFApp",
						"ssi name :" + apps.getBorrowerDetails().getSsiDetails().getSsiName());

				if ((apps.getBorrowerDetails().getSsiDetails().getRegNo() != null)
						&& (!apps.getBorrowerDetails().getSsiDetails().getRegNo().equals(""))) {
					ssiDetails.setString(10, apps.getBorrowerDetails().getSsiDetails().getRegNo());
				} else {
					ssiDetails.setString(10, null);
				}

				Log.log(5, "ApplicationDAO", "submitRSFApp",
						"reg no :" + apps.getBorrowerDetails().getSsiDetails().getRegNo());

				ssiDetails.setNull(11, 91);

				if ((apps.getBorrowerDetails().getSsiDetails().getSsiITPan() != null)
						&& (!apps.getBorrowerDetails().getSsiDetails().getSsiITPan().equals(""))) {
					ssiDetails.setString(12, apps.getBorrowerDetails().getSsiDetails().getSsiITPan());
				} else {
					ssiDetails.setString(12, null);
				}
				Log.log(5, "ApplicationDAO", "submitRSFApp",
						"ssi ITPAN :" + apps.getBorrowerDetails().getSsiDetails().getSsiITPan());

				if ((apps.getBorrowerDetails().getSsiDetails().getActivityType() != null)
						&& (!apps.getBorrowerDetails().getSsiDetails().getActivityType().equals(""))) {
					ssiDetails.setString(13, apps.getBorrowerDetails().getSsiDetails().getActivityType());
				} else {
					ssiDetails.setString(13, null);
				}
				Log.log(5, "ApplicationDAO", "submitRSFApp",
						"activity Type :" + apps.getBorrowerDetails().getSsiDetails().getActivityType());

				if (apps.getBorrowerDetails().getSsiDetails().getEmployeeNos() == 0) {
					ssiDetails.setNull(14, 4);
				} else
					ssiDetails.setInt(14, apps.getBorrowerDetails().getSsiDetails().getEmployeeNos());

				Log.log(5, "ApplicationDAO", "submitRSFApp",
						"employee nos :" + apps.getBorrowerDetails().getSsiDetails().getEmployeeNos());

				if (apps.getBorrowerDetails().getSsiDetails().getProjectedSalesTurnover() == 0.0D) {
					ssiDetails.setNull(15, 8);
				} else
					ssiDetails.setDouble(15, apps.getBorrowerDetails().getSsiDetails().getProjectedSalesTurnover());

				Log.log(5, "ApplicationDAO", "submitRSFApp",
						"sales :" + apps.getBorrowerDetails().getSsiDetails().getProjectedSalesTurnover());

				if (apps.getBorrowerDetails().getSsiDetails().getProjectedExports() == 0.0D) {
					ssiDetails.setNull(16, 8);
				} else
					ssiDetails.setDouble(16, apps.getBorrowerDetails().getSsiDetails().getProjectedExports());

				Log.log(5, "ApplicationDAO", "submitRSFApp",
						"exports :" + apps.getBorrowerDetails().getSsiDetails().getProjectedExports());

				ssiDetails.setString(17, apps.getBorrowerDetails().getSsiDetails().getAddress().toUpperCase());
				Log.log(5, "ApplicationDAO", "submitRSFApp",
						"Address :" + apps.getBorrowerDetails().getSsiDetails().getAddress());

				if ((apps.getBorrowerDetails().getSsiDetails().getCity() != null)
						&& (!apps.getBorrowerDetails().getSsiDetails().getCity().equals(""))) {
					ssiDetails.setString(18, apps.getBorrowerDetails().getSsiDetails().getCity().toUpperCase());
				} else {
					ssiDetails.setString(18, null);
				}
				Log.log(5, "ApplicationDAO", "submitRSFApp",
						"city :" + apps.getBorrowerDetails().getSsiDetails().getCity());

				ssiDetails.setString(19, apps.getBorrowerDetails().getSsiDetails().getPincode());
				Log.log(5, "ApplicationDAO", "submitRSFApp",
						"pincode :" + apps.getBorrowerDetails().getSsiDetails().getPincode());

				ssiDetails.setString(20, "Y");

				if ((apps.getBorrowerDetails().getSsiDetails().getDistrict() != null)
						&& (!apps.getBorrowerDetails().getSsiDetails().getDistrict().equals(""))) {
					ssiDetails.setString(21, apps.getBorrowerDetails().getSsiDetails().getDistrict());
				} else {
					ssiDetails.setString(21, null);
				}

				Log.log(5, "ApplicationDAO", "submitRSFApp",
						"district :" + apps.getBorrowerDetails().getSsiDetails().getDistrict());

				ssiDetails.setString(22, apps.getBorrowerDetails().getSsiDetails().getState());
				Log.log(5, "ApplicationDAO", "submitRSFApp",
						"State :" + apps.getBorrowerDetails().getSsiDetails().getState());

				if ((apps.getBorrowerDetails().getSsiDetails().getIndustryNature() != null)
						&& (!apps.getBorrowerDetails().getSsiDetails().getIndustryNature().equals(""))) {
					ssiDetails.setString(23, apps.getBorrowerDetails().getSsiDetails().getIndustryNature());
				} else {
					ssiDetails.setString(23, null);
				}
				Log.log(5, "ApplicationDAO", "submitRSFApp",
						"nature :" + apps.getBorrowerDetails().getSsiDetails().getIndustryNature());

				if ((apps.getBorrowerDetails().getSsiDetails().getIndustrySector() != null)
						&& (!apps.getBorrowerDetails().getSsiDetails().getIndustrySector().equals(""))) {
					ssiDetails.setString(24, apps.getBorrowerDetails().getSsiDetails().getIndustrySector());
				} else {
					ssiDetails.setString(24, null);
				}
				Log.log(5, "ApplicationDAO", "submitRSFApp",
						"sector : " + apps.getBorrowerDetails().getSsiDetails().getIndustrySector());

				if (apps.getBorrowerDetails().getOsAmt() == 0.0D) {
					ssiDetails.setNull(25, 8);
				} else
					ssiDetails.setDouble(25, apps.getBorrowerDetails().getOsAmt());

				Log.log(5, "ApplicationDAO", "submitRSFApp", "os amt :" + apps.getBorrowerDetails().getOsAmt());

				MCGFDetails mcgfDetails = apps.getMCGFDetails();
				if (mcgfDetails != null) {
					ssiDetails.setString(26, "Y");
				} else {
					ssiDetails.setString(26, "N");
				}

				ssiDetails.setString(27, createdBy);
				Log.log(5, "ApplicationDAO", "submitRSFApp", "userId :" + createdBy);

				if ((apps.getBorrowerDetails().getSsiDetails().getEnterprise() != null)
						&& (!apps.getBorrowerDetails().getSsiDetails().getEnterprise().equals(""))) {
					ssiDetails.setString(28, apps.getBorrowerDetails().getSsiDetails().getEnterprise());
				} else {
					ssiDetails.setString(28, "N");
				}
				if ((apps.getBorrowerDetails().getSsiDetails().getUnitAssisted() != null)
						&& (!apps.getBorrowerDetails().getSsiDetails().getUnitAssisted().equals(""))) {
					ssiDetails.setString(29, apps.getBorrowerDetails().getSsiDetails().getUnitAssisted());
				} else {
					ssiDetails.setString(29, "N");
				}
				if ((apps.getBorrowerDetails().getSsiDetails().getConditionAccepted() != null)
						&& (!apps.getBorrowerDetails().getSsiDetails().getConditionAccepted().equals(""))) {
					ssiDetails.setString(30, apps.getBorrowerDetails().getSsiDetails().getConditionAccepted());
				} else {
					ssiDetails.setString(30, "Y");
				}
				if ((apps.getBorrowerDetails().getSsiDetails().getWomenOperated() != null)
						&& (!apps.getBorrowerDetails().getSsiDetails().getWomenOperated().equals(""))) {
					ssiDetails.setString(31, apps.getBorrowerDetails().getSsiDetails().getWomenOperated());
				} else {
					ssiDetails.setString(31, "N");
				}
				Log.log(5, "ApplicationDAO", "submitRSFApp", "SSI Details object :" + ssiDetails);

				ssiDetails.executeQuery();

				int ssiDetailsValue = ssiDetails.getInt(1);
				Log.log(5, "ApplicationDAO", "submitRSFApp", "SSi Details result :" + ssiDetailsValue);

				if (ssiDetailsValue == 1) {
					String error = ssiDetails.getString(32);

					ssiDetails.close();
					ssiDetails = null;

					connection.rollback();

					Log.log(2, "ApplicationDAO", "submitRSFApp", "SSI Detail Exception :" + error);

					throw new DatabaseException(error);
				}
				int ssiRefNo = ssiDetails.getInt(2);
				apps.getBorrowerDetails().getSsiDetails().setBorrowerRefNo(ssiRefNo);

				ssiDetails.close();
				ssiDetails = null;

				ssiDetail = null;
			}
		} catch (SQLException sqlException) {
			Log.log(2, "ApplicationDAO", "submitRSFApp", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(2, "ApplicationDAO", "submitRSFApp", ignore.getMessage());
			}

			throw new DatabaseException(sqlException.getMessage());
		}

		return ssiDetail;
	}

	public Application submitApplication(Application apps, String createdBy) throws DatabaseException {
		Connection connection = DBConnection.getConnection(false);
		// System.out.println("submitApplication ApplicationDAO called ");
		//String appLoanType = apps.getLoanType();
		//MCGSProcessor mcgsProcessor = new MCGSProcessor();
		//RiskManagementProcessor rpProcessor = new RiskManagementProcessor();
		//SSIDetails ssiDetails = new SSIDetails();
		try {
			String applicationRefNo = submitEGSApplicationDetails(apps, createdBy, connection);
			apps.setAppRefNo(applicationRefNo);
			
			 submitPromotersDetails(apps, connection);
			
			connection.commit();
		} catch (SQLException e) {
			Log.log(2, "ApplicationDAO", "submitApp", e.getMessage());
			Log.logException(e);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(2, "ApplicationDAO", "submitApp", ignore.getMessage());
			}

			throw new DatabaseException("Unable to submit Application");
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(4, "ApplicationDAO", "submitApp", "Exited");

		return apps;
	}
	
	public Application submitMudraApplication(Application apps, String createdBy) throws DatabaseException {
		
		Connection connection = DBConnection.getConnection(false);
		// System.out.println("submitApplication ApplicationDAO called ");
		//String appLoanType = apps.getLoanType();
		//MCGSProcessor mcgsProcessor = new MCGSProcessor();
		//RiskManagementProcessor rpProcessor = new RiskManagementProcessor();
		//SSIDetails ssiDetails = new SSIDetails();
		try {
			String applicationRefNo = submitEGSApplicationDetails(apps, createdBy, connection);
			apps.setAppRefNo(applicationRefNo);
			
			connection.commit();
		} catch (SQLException e) {
			Log.log(2, "ApplicationDAO", "submitApp", e.getMessage());
			Log.logException(e);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(2, "ApplicationDAO", "submitApp", ignore.getMessage());
			}

			throw new DatabaseException("Unable to submit Application");
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(4, "ApplicationDAO", "submitApp", "Exited");

		return apps;
		/*Connection connection = DBConnection.getConnection(false);
		// System.out.println("submitApplication ApplicationDAO called ");
		String appLoanType = apps.getLoanType();
		MCGSProcessor mcgsProcessor = new MCGSProcessor();
		RiskManagementProcessor rpProcessor = new RiskManagementProcessor();
		SSIDetails ssiDetails = new SSIDetails();
		try {
			if ((appLoanType.equals("TC")) || (appLoanType.equals("CC")) || (appLoanType.equals("WC"))) {
				// System.out.println("submitApplication ApplicationDAO appLoanType
				// "+appLoanType);
				if (!apps.getScheme().equals("MCGS")) {
					ssiDetails = submitMudraApp(apps, createdBy, connection);
					submitPromotersMudraDetails(apps, connection);

					if (ssiDetails != null) {
						apps.getBorrowerDetails().setSsiDetails(ssiDetails);
						String subSchemeName = rpProcessor.getSubScheme(apps);
						apps.setSubSchemeName(subSchemeName);
					}

				} 
					 * else if (apps.getScheme().equals("MCGS")) { String subSchemeName =
					 * rpProcessor.getSubScheme(apps); apps.setSubSchemeName(subSchemeName);
					 * 
					 * if (apps.getBorrowerDetails().getSsiDetails() .getBorrowerRefNo() == 0) {
					 * ssiDetails = submitApp(apps, createdBy, connection);
					 * submitPromotersDetails(apps, connection); CallableStatement callable =
					 * connection .prepareCall("{?= call funcInsertMCGFMemBorrower(?,?,?,?,?,?) }");
					 * callable.registerOutParameter(1, 4); callable.setString(2, apps.getBankId());
					 * callable.setString(3, apps.getZoneId()); callable.setString(4,
					 * apps.getBranchId()); callable.setInt(5, apps.getBorrowerDetails()
					 * .getSsiDetails().getBorrowerRefNo()); callable.setString(6, null);
					 * 
					 * callable.registerOutParameter(7, 12);
					 * 
					 * callable.execute();
					 * 
					 * int errorCode = callable.getInt(1); String error = callable.getString(7);
					 * 
					 * if (errorCode == 1) { callable.close(); callable = null;
					 * 
					 * connection.rollback();
					 * 
					 * Log.log(2, "MCGSDAO", "addSSIMembers", error);
					 * //System.out.println("submitApplication 1 error="+error); throw new
					 * DatabaseException(error); }
					 * 
					 * callable.close(); callable = null;
					 * 
					 * if (ssiDetails != null) {
					 * apps.getBorrowerDetails().setSsiDetails(ssiDetails); }
					 * 
					 * }
					 * 
					 * }
					 

				int temssiRef = apps.getBorrowerDetails().getSsiDetails().getBorrowerRefNo();
				System.out.println("temssiRef ===SSI_DETAIL=="+temssiRef);

				String ssiRefNumber = Integer.toString(temssiRef);
				RpDAO rpDAO1 = new RpDAO();
				double prevTotalSancAmt = rpDAO1.getTotalSanctionedAmountNew(ssiRefNumber);
				ApplicationDAO appdao = new ApplicationDAO();

				double prevTotalHandloomSancAmt = appdao.getTotalSanctionedHandloomAmountNew(ssiRefNumber);

				double currentCreditAmount = 0.0D;
				if (apps.getLoanType().equals("TC")) {
					currentCreditAmount = apps.getTermLoan().getCreditGuaranteed();
				} else if (apps.getLoanType().equals("CC")) {
					currentCreditAmount = apps.getTermLoan().getCreditGuaranteed() + apps.getWc().getCreditFundBased()
							+ apps.getWc().getCreditNonFundBased();
				} else if (apps.getLoanType().equals("WC")) {
					currentCreditAmount = apps.getTermLoan().getCreditGuaranteed();

					
					 * currentCreditAmount = apps.getWc().getCreditFundBased() +
					 * apps.getWc().getCreditNonFundBased();
					 
				}
				System.out.println("currentCreditAmount-----"
				 + currentCreditAmount);

				 System.out.println("prevTotalSancAmt-----" + prevTotalSancAmt);
				// if (currentCreditAmount + prevTotalSancAmt > 10000000.0D) {
				if (currentCreditAmount + prevTotalSancAmt > 200000.0D) {
					throw new DatabaseException(" Guarantee of Rs. " + prevTotalSancAmt
							+ " is already available for the Borrower. Total Credit to be Guaranteed Amount should be within the eligible amount available for Guarantee upto"
							// + 10000000);
							+ 200000);
				}
				
				 * if ((currentCreditAmount + prevTotalHandloomSancAmt > 200000.0D) &&
				 * (apps.getDcHandlooms().equals("Y"))) { throw new DatabaseException(
				 * "Guarantee of Rs. " + prevTotalHandloomSancAmt +
				 * " is already available for the Borrower. Total Credit to be Guaranteed Amount should be within the eligible amount available for Guarantee upto  Rs. 200000 as per ceiling fixed by Office of DC Handlooms"
				 * ); }
				 

				String applicationRefNo = submitMudraApplicationDetails(apps, createdBy, connection);
				apps.setAppRefNo(applicationRefNo);

				// submitGuarantorSecurityDetails(apps, connection);
				// System.out.println("submitApplication ApplicationDAO before
				// submitTermCreditDetails ");
				submitMudraCreditDetails(apps, createdBy, connection);
				// System.out.println("submitApplication ApplicationDAO after
				// submitTermCreditDetails ");

				// WorkingCapital tempWc = apps.getWc();

				Administrator admin = new Administrator();
				ParameterMaster param = admin.getParameter();

				// DIKSHA
				
				 * if (appLoanType.equals("WC")) { tempWc.setWcTenure(param.getWcTenorInYrs() *
				 * 12); } apps.setWc(tempWc);
				 * 
				 * apps.setWc(tempWc);
				 

				submitWCDetails(apps, createdBy, connection);
				// submitSecDetails(apps, connection);

				
				 * if (apps.getScheme().equals("MCGS")) { MCGFDetails mcgfDetails =
				 * apps.getMCGFDetails();
				 * mcgfDetails.setApplicationReferenceNumber(applicationRefNo);
				 * apps.setMCGFDetails(mcgfDetails);
				 * mcgsProcessor.updateMCGSDetails(mcgfDetails, createdBy, connection); }
				 

			} else if (appLoanType.equals("BO")) {
				Log.log(4, "ApplicationDAO", "submitApp", "Entering if it is a Both Application..");

				apps.setLoanType("TC");
				if (!apps.getScheme().equals("MCGS")) {
					ssiDetails = submitApp(apps, createdBy, connection);
					submitPromotersDetails(apps, connection);
					if (ssiDetails != null) {
						apps.getBorrowerDetails().setSsiDetails(ssiDetails);
						String subSchemeName = rpProcessor.getSubScheme(apps);
						apps.setSubSchemeName(subSchemeName);
					}
				} else if (apps.getScheme().equals("MCGS")) {
					String subSchemeName = rpProcessor.getSubScheme(apps);
					apps.setSubSchemeName(subSchemeName);

					if (apps.getBorrowerDetails().getSsiDetails().getBorrowerRefNo() == 0) {
						ssiDetails = submitApp(apps, createdBy, connection);
						submitPromotersDetails(apps, connection);

						CallableStatement callable = connection
								.prepareCall("{?= call funcInsertMCGFMemBorrower(?,?,?,?,?,?) }");
						callable.registerOutParameter(1, 4);
						callable.setString(2, apps.getBankId());
						callable.setString(3, apps.getZoneId());
						callable.setString(4, apps.getBranchId());
						callable.setInt(5, apps.getBorrowerDetails().getSsiDetails().getBorrowerRefNo());

						callable.setString(6, null);

						callable.registerOutParameter(7, 12);

						callable.execute();

						int errorCode = callable.getInt(1);
						String error = callable.getString(7);

						if (errorCode == 1) {
							callable.close();
							callable = null;

							connection.rollback();

							Log.log(2, "MCGSDAO", "addSSIMembers", error);
							// System.out.println("submitApplication 2 error="+error);
							throw new DatabaseException(error);
						}

						callable.close();
						callable = null;

						if (ssiDetails != null) {
							apps.getBorrowerDetails().setSsiDetails(ssiDetails);
						}
					}

				}

				String tcAppRefNo = submitApplicationDetails(apps, createdBy, connection); // **************************
																							// DKR
																							// **********************
				apps.setWcAppRefNo(tcAppRefNo);

				Log.log(5, "ApplicationDAO", "submitApp", "First Reference Number :" + apps.getWcAppRefNo());
				apps.setAppRefNo(tcAppRefNo);

				Log.log(5, "ApplicationDAO", "submitApp", "Application Reference Number :" + apps.getAppRefNo());

				submitGuarantorSecurityDetails(apps, connection);
				submitTermCreditDetails(apps, createdBy, connection);
				submitWCDetails(apps, createdBy, connection);
				submitSecDetails(apps, connection);

				if (apps.getMCGFDetails() != null) {
					MCGFDetails mcgfDetails = apps.getMCGFDetails();
					mcgfDetails.setApplicationReferenceNumber(tcAppRefNo);
					mcgsProcessor.updateMCGSDetails(mcgfDetails, createdBy, connection);
				}

				apps.setLoanType("WC");

				apps.setCgpanReference(tcAppRefNo);
				Log.log(5, "ApplicationDAO", "submitApp",
						"TC App Ref No For Both Application:" + apps.getCgpanReference());

				if (!apps.getScheme().equals("MCGS")) {
					if (ssiDetails != null) {
						apps.getBorrowerDetails().setSsiDetails(ssiDetails);
						String subSchemeName = rpProcessor.getSubScheme(apps);
						apps.setSubSchemeName(subSchemeName);
					}

				} else if (apps.getScheme().equals("MCGS")) {
					String subSchemeName = rpProcessor.getSubScheme(apps);
					apps.setSubSchemeName(subSchemeName);
				}

				String wcAppRefNo = submitApplicationDetails(apps, createdBy, connection);
				apps.setAppRefNo(wcAppRefNo);

				submitGuarantorSecurityDetails(apps, connection);

				Log.log(5, "ApplicationDAO", "submitApp", "Both Application Reference Number :" + apps.getAppRefNo());
				submitTermCreditDetails(apps, createdBy, connection);

				WorkingCapital tempWc = apps.getWc();
				Log.log(4, "ApplicationDAO", "submitApp", "Term Loan Tenure :" + apps.getTermLoan().getTenure());
				tempWc.setWcTenure(apps.getTermLoan().getTenure());
				apps.setWc(tempWc);

				submitWCDetails(apps, createdBy, connection);
				submitSecDetails(apps, connection);

				if (apps.getMCGFDetails() != null) {
					MCGFDetails mcgfDetails = apps.getMCGFDetails();
					mcgfDetails.setApplicationReferenceNumber(wcAppRefNo);
					mcgsProcessor.updateMCGSDetails(mcgfDetails, createdBy, connection);
				}

				Log.log(4, "ApplicationDAO", "submitApp", "tcapprefno :" + tcAppRefNo);
				Log.log(4, "ApplicationDAO", "submitApp", "wcapprefno :" + wcAppRefNo);

				updateAppReference(tcAppRefNo, wcAppRefNo, connection);

				Log.log(4, "ApplicationDAO", "submitApp", "Submitted a BO application");
			}

			connection.commit();
		} catch (SQLException e) {
			Log.log(2, "ApplicationDAO", "submitApp", e.getMessage());
			Log.logException(e);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(2, "ApplicationDAO", "submitApp", ignore.getMessage());
			}

			throw new DatabaseException("Unable to submit Application");
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(4, "ApplicationDAO", "submitApp", "Exited");

		return apps;*/
	}

	public double getTotalSanctionedHandloomAmountNew(String ssiRef) throws DatabaseException {
		Log.log(4, "RpProcessor", "getTotalSanctionedAmountNew", "Entered");
		PreparedStatement sanctiondAmountStatement = null;
		ResultSet sanctionedAmountResult = null;
		double tempTotalAmount = 0.0D;
		Connection connection = DBConnection.getConnection();
		try {
			String query = " select Sum(IFNULL(APPROVED_AMT,0)) TEMPAMT  from VIEW_APPL_HANDLOOMAMOUNT_MOD  where SSI_REFERENCE_NUMBER = ? ";

			sanctiondAmountStatement = connection.prepareStatement(query);
			sanctiondAmountStatement.setString(1, ssiRef);

			sanctionedAmountResult = sanctiondAmountStatement.executeQuery();
			while (sanctionedAmountResult.next()) {
				tempTotalAmount = sanctionedAmountResult.getDouble(1);
			}

			sanctionedAmountResult.close();
			sanctionedAmountResult = null;
			sanctiondAmountStatement.close();
			sanctiondAmountStatement = null;
		} catch (Exception exception) {
			Log.logException(exception);
			throw new DatabaseException(exception.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		return tempTotalAmount;
	}

	public Application submitRSF2Application(Application apps, String createdBy) throws DatabaseException {
		Connection connection = DBConnection.getConnection(false);

		String appLoanType = apps.getLoanType();
		// System.out.println("Line number applicationdao 1911 in ApplicationDAO"
		// + appLoanType);
		MCGSProcessor mcgsProcessor = new MCGSProcessor();
		RiskManagementProcessor rpProcessor = new RiskManagementProcessor();

		SSIDetails ssiDetails = new SSIDetails();
		try {
			if ((appLoanType.equals("TC")) || (appLoanType.equals("CC")) || (appLoanType.equals("WC"))) {
				if (!apps.getScheme().equals("MCGS")) {
					ssiDetails = submitRSF2App(apps, createdBy, connection);
					submitPromotersDetails(apps, connection);

					if (ssiDetails != null) {
						apps.getBorrowerDetails().setSsiDetails(ssiDetails);
						String subSchemeName = rpProcessor.getSubScheme(apps);
						apps.setSubSchemeName(subSchemeName);
					}

				} else if (apps.getScheme().equals("MCGS")) {
					String subSchemeName = rpProcessor.getSubScheme(apps);
					apps.setSubSchemeName(subSchemeName);

					if (apps.getBorrowerDetails().getSsiDetails().getBorrowerRefNo() == 0) {
						ssiDetails = submitRSFApp(apps, createdBy, connection);
						submitPromotersDetails(apps, connection);
						CallableStatement callable = connection
								.prepareCall("{?= call funcInsertMCGFMemBorrower(?,?,?,?,?,?) }");
						callable.registerOutParameter(1, 4);
						callable.setString(2, apps.getBankId());
						callable.setString(3, apps.getZoneId());
						callable.setString(4, apps.getBranchId());
						callable.setInt(5, apps.getBorrowerDetails().getSsiDetails().getBorrowerRefNo());
						callable.setString(6, null);

						callable.registerOutParameter(7, 12);

						callable.execute();

						int errorCode = callable.getInt(1);
						String error = callable.getString(7);

						if (errorCode == 1) {
							callable.close();
							callable = null;

							connection.rollback();

							Log.log(2, "MCGSDAO", "addSSIMembers", error);

							throw new DatabaseException(error);
						}

						callable.close();
						callable = null;

						if (ssiDetails != null) {
							apps.getBorrowerDetails().setSsiDetails(ssiDetails);
						}

					}

				}

				String applicationRefNo = submitRsf2ApplicationDetails(apps, createdBy, connection);
				apps.setAppRefNo(applicationRefNo);

				submitGuarantorSecurityDetails(apps, connection);
				submitTermCreditDetails(apps, createdBy, connection);

				WorkingCapital tempWc = apps.getWc();

				Administrator admin = new Administrator();
				ParameterMaster param = admin.getParameter();

				if (appLoanType.equals("WC")) {
					tempWc.setWcTenure(param.getWcTenorInYrs() * 12);
				}
				apps.setWc(tempWc);

				apps.setWc(tempWc);

				submitWCDetails(apps, createdBy, connection);
				submitSecDetails(apps, connection);

				if (apps.getScheme().equals("MCGS")) {
					MCGFDetails mcgfDetails = apps.getMCGFDetails();
					mcgfDetails.setApplicationReferenceNumber(applicationRefNo);
					apps.setMCGFDetails(mcgfDetails);
					mcgsProcessor.updateMCGSDetails(mcgfDetails, createdBy, connection);
				}

			} else if (appLoanType.equals("BO")) {
				Log.log(4, "ApplicationDAO", "submitApp", "Entering if it is a Both Application..");

				apps.setLoanType("TC");
				if (!apps.getScheme().equals("MCGS")) {
					ssiDetails = submitRSFApp(apps, createdBy, connection);

					submitPromotersDetails(apps, connection);
					if (ssiDetails != null) {
						apps.getBorrowerDetails().setSsiDetails(ssiDetails);
						String subSchemeName = rpProcessor.getSubScheme(apps);
						apps.setSubSchemeName(subSchemeName);
					}
				} else if (apps.getScheme().equals("MCGS")) {
					String subSchemeName = rpProcessor.getSubScheme(apps);
					apps.setSubSchemeName(subSchemeName);

					if (apps.getBorrowerDetails().getSsiDetails().getBorrowerRefNo() == 0) {
						ssiDetails = submitApp(apps, createdBy, connection);
						submitPromotersDetails(apps, connection);

						CallableStatement callable = connection
								.prepareCall("{?= call funcInsertMCGFMemBorrower(?,?,?,?,?,?) }");
						callable.registerOutParameter(1, 4);
						callable.setString(2, apps.getBankId());
						callable.setString(3, apps.getZoneId());
						callable.setString(4, apps.getBranchId());
						callable.setInt(5, apps.getBorrowerDetails().getSsiDetails().getBorrowerRefNo());

						callable.setString(6, null);

						callable.registerOutParameter(7, 12);

						callable.execute();

						int errorCode = callable.getInt(1);
						String error = callable.getString(7);

						if (errorCode == 1) {
							callable.close();
							callable = null;

							connection.rollback();

							Log.log(2, "MCGSDAO", "addSSIMembers", error);

							throw new DatabaseException(error);
						}

						callable.close();
						callable = null;

						if (ssiDetails != null) {
							apps.getBorrowerDetails().setSsiDetails(ssiDetails);
						}

					}

				}

				String tcAppRefNo = submitRsf2ApplicationDetails(apps, createdBy, connection);
				apps.setWcAppRefNo(tcAppRefNo);
				// System.out.println("tcAppRefNo is " + tcAppRefNo);

				Log.log(5, "ApplicationDAO", "submitRSF2Application",
						"First Reference Number :" + apps.getWcAppRefNo());
				apps.setAppRefNo(tcAppRefNo);

				Log.log(5, "ApplicationDAO", "submitRSF2Application",
						"Application Reference Number :" + apps.getAppRefNo());

				submitGuarantorSecurityDetails(apps, connection);
				submitTermCreditDetails(apps, createdBy, connection);
				submitWCDetails(apps, createdBy, connection);
				submitSecDetails(apps, connection);

				if (apps.getMCGFDetails() != null) {
					MCGFDetails mcgfDetails = apps.getMCGFDetails();
					mcgfDetails.setApplicationReferenceNumber(tcAppRefNo);
					mcgsProcessor.updateMCGSDetails(mcgfDetails, createdBy, connection);
				}

				apps.setLoanType("WC");

				apps.setCgpanReference(tcAppRefNo);
				Log.log(5, "ApplicationDAO", "submitRSF2Application",
						"TC App Ref No For Both Application:" + apps.getCgpanReference());

				if (!apps.getScheme().equals("MCGS")) {
					if (ssiDetails != null) {
						apps.getBorrowerDetails().setSsiDetails(ssiDetails);
						String subSchemeName = rpProcessor.getSubScheme(apps);
						apps.setSubSchemeName(subSchemeName);
					}

				} else if (apps.getScheme().equals("MCGS")) {
					String subSchemeName = rpProcessor.getSubScheme(apps);
					apps.setSubSchemeName(subSchemeName);
				}

				String wcAppRefNo = submitRsfApplicationDetails(apps, createdBy, connection);

				apps.setAppRefNo(wcAppRefNo);

				submitGuarantorSecurityDetails(apps, connection);

				Log.log(5, "ApplicationDAO", "submitRSF2Application",
						"Both Application Reference Number :" + apps.getAppRefNo());
				submitTermCreditDetails(apps, createdBy, connection);

				WorkingCapital tempWc = apps.getWc();
				Log.log(4, "ApplicationDAO", "submitRSF2Application",
						"Term Loan Tenure :" + apps.getTermLoan().getTenure());
				tempWc.setWcTenure(apps.getTermLoan().getTenure());
				apps.setWc(tempWc);

				submitWCDetails(apps, createdBy, connection);
				submitSecDetails(apps, connection);

				if (apps.getMCGFDetails() != null) {
					MCGFDetails mcgfDetails = apps.getMCGFDetails();
					mcgfDetails.setApplicationReferenceNumber(wcAppRefNo);
					mcgsProcessor.updateMCGSDetails(mcgfDetails, createdBy, connection);
				}

				Log.log(4, "ApplicationDAO", "submitRSF2Application", "tcapprefno :" + tcAppRefNo);
				Log.log(4, "ApplicationDAO", "submitRSF2Application", "wcapprefno :" + wcAppRefNo);

				updateAppReference(tcAppRefNo, wcAppRefNo, connection);

				Log.log(4, "ApplicationDAO", "submitRSF2Application", "Submitted a BO application");
			}

			connection.commit();
		} catch (SQLException e) {
			Log.log(2, "ApplicationDAO", "submitRSF2Application", e.getMessage());
			Log.logException(e);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(2, "ApplicationDAO", "submitRSF2Application", ignore.getMessage());
			}

			throw new DatabaseException("Unable to submit Application");
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(4, "ApplicationDAO", "submitRSF2Application", "Exited");

		return apps;
	}

	public Application submitRSFApplication(Application apps, String createdBy) throws DatabaseException {
		Connection connection = DBConnection.getConnection(false);

		String appLoanType = apps.getLoanType();

		MCGSProcessor mcgsProcessor = new MCGSProcessor();
		RiskManagementProcessor rpProcessor = new RiskManagementProcessor();

		SSIDetails ssiDetails = new SSIDetails();
		try {
			if ((appLoanType.equals("TC")) || (appLoanType.equals("CC")) || (appLoanType.equals("WC"))) {
				if (!apps.getScheme().equals("MCGS")) {
					ssiDetails = submitRSFApp(apps, createdBy, connection);
					submitPromotersDetails(apps, connection);

					if (ssiDetails != null) {
						apps.getBorrowerDetails().setSsiDetails(ssiDetails);
						String subSchemeName = rpProcessor.getSubScheme(apps);
						apps.setSubSchemeName(subSchemeName);
					}

				} else if (apps.getScheme().equals("MCGS")) {
					String subSchemeName = rpProcessor.getSubScheme(apps);
					apps.setSubSchemeName(subSchemeName);

					if (apps.getBorrowerDetails().getSsiDetails().getBorrowerRefNo() == 0) {
						ssiDetails = submitRSFApp(apps, createdBy, connection);
						submitPromotersDetails(apps, connection);
						CallableStatement callable = connection
								.prepareCall("{?= call funcInsertMCGFMemBorrower(?,?,?,?,?,?) }");
						callable.registerOutParameter(1, 4);
						callable.setString(2, apps.getBankId());
						callable.setString(3, apps.getZoneId());
						callable.setString(4, apps.getBranchId());
						callable.setInt(5, apps.getBorrowerDetails().getSsiDetails().getBorrowerRefNo());
						callable.setString(6, null);

						callable.registerOutParameter(7, 12);

						callable.execute();

						int errorCode = callable.getInt(1);
						String error = callable.getString(7);

						if (errorCode == 1) {
							callable.close();
							callable = null;

							connection.rollback();

							Log.log(2, "MCGSDAO", "addSSIMembers", error);

							throw new DatabaseException(error);
						}

						callable.close();
						callable = null;

						if (ssiDetails != null) {
							apps.getBorrowerDetails().setSsiDetails(ssiDetails);
						}

					}

				}

				String applicationRefNo = submitRsfApplicationDetails(apps, createdBy, connection);
				apps.setAppRefNo(applicationRefNo);

				submitGuarantorSecurityDetails(apps, connection);
				submitTermCreditDetails(apps, createdBy, connection);

				WorkingCapital tempWc = apps.getWc();

				Administrator admin = new Administrator();
				ParameterMaster param = admin.getParameter();

				if (appLoanType.equals("WC")) {
					tempWc.setWcTenure(param.getWcTenorInYrs() * 12);
				}
				apps.setWc(tempWc);

				apps.setWc(tempWc);

				submitWCDetails(apps, createdBy, connection);
				submitSecDetails(apps, connection);

				if (apps.getScheme().equals("MCGS")) {
					MCGFDetails mcgfDetails = apps.getMCGFDetails();
					mcgfDetails.setApplicationReferenceNumber(applicationRefNo);
					apps.setMCGFDetails(mcgfDetails);
					mcgsProcessor.updateMCGSDetails(mcgfDetails, createdBy, connection);
				}

			} else if (appLoanType.equals("BO")) {
				Log.log(4, "ApplicationDAO", "submitApp", "Entering if it is a Both Application..");

				apps.setLoanType("TC");
				if (!apps.getScheme().equals("MCGS")) {
					ssiDetails = submitRSFApp(apps, createdBy, connection);

					submitPromotersDetails(apps, connection);
					if (ssiDetails != null) {
						apps.getBorrowerDetails().setSsiDetails(ssiDetails);
						String subSchemeName = rpProcessor.getSubScheme(apps);
						apps.setSubSchemeName(subSchemeName);
					}
				} else if (apps.getScheme().equals("MCGS")) {
					String subSchemeName = rpProcessor.getSubScheme(apps);
					apps.setSubSchemeName(subSchemeName);

					if (apps.getBorrowerDetails().getSsiDetails().getBorrowerRefNo() == 0) {
						ssiDetails = submitApp(apps, createdBy, connection);
						submitPromotersDetails(apps, connection);

						CallableStatement callable = connection
								.prepareCall("{?= call funcInsertMCGFMemBorrower(?,?,?,?,?,?) }");
						callable.registerOutParameter(1, 4);
						callable.setString(2, apps.getBankId());
						callable.setString(3, apps.getZoneId());
						callable.setString(4, apps.getBranchId());
						callable.setInt(5, apps.getBorrowerDetails().getSsiDetails().getBorrowerRefNo());

						callable.setString(6, null);

						callable.registerOutParameter(7, 12);

						callable.execute();

						int errorCode = callable.getInt(1);
						String error = callable.getString(7);

						if (errorCode == 1) {
							callable.close();
							callable = null;

							connection.rollback();

							Log.log(2, "MCGSDAO", "addSSIMembers", error);

							throw new DatabaseException(error);
						}

						callable.close();
						callable = null;

						if (ssiDetails != null) {
							apps.getBorrowerDetails().setSsiDetails(ssiDetails);
						}

					}

				}

				String tcAppRefNo = submitRsfApplicationDetails(apps, createdBy, connection);
				apps.setWcAppRefNo(tcAppRefNo);

				Log.log(5, "ApplicationDAO", "submitApp", "First Reference Number :" + apps.getWcAppRefNo());
				apps.setAppRefNo(tcAppRefNo);

				Log.log(5, "ApplicationDAO", "submitApp", "Application Reference Number :" + apps.getAppRefNo());

				submitGuarantorSecurityDetails(apps, connection);
				submitTermCreditDetails(apps, createdBy, connection);
				submitWCDetails(apps, createdBy, connection);
				submitSecDetails(apps, connection);

				if (apps.getMCGFDetails() != null) {
					MCGFDetails mcgfDetails = apps.getMCGFDetails();
					mcgfDetails.setApplicationReferenceNumber(tcAppRefNo);
					mcgsProcessor.updateMCGSDetails(mcgfDetails, createdBy, connection);
				}

				apps.setLoanType("WC");

				apps.setCgpanReference(tcAppRefNo);
				Log.log(5, "ApplicationDAO", "submitApp",
						"TC App Ref No For Both Application:" + apps.getCgpanReference());

				if (!apps.getScheme().equals("MCGS")) {
					if (ssiDetails != null) {
						apps.getBorrowerDetails().setSsiDetails(ssiDetails);
						String subSchemeName = rpProcessor.getSubScheme(apps);
						apps.setSubSchemeName(subSchemeName);
					}

				} else if (apps.getScheme().equals("MCGS")) {
					String subSchemeName = rpProcessor.getSubScheme(apps);
					apps.setSubSchemeName(subSchemeName);
				}

				String wcAppRefNo = submitRsfApplicationDetails(apps, createdBy, connection);

				apps.setAppRefNo(wcAppRefNo);

				submitGuarantorSecurityDetails(apps, connection);

				Log.log(5, "ApplicationDAO", "submitApp", "Both Application Reference Number :" + apps.getAppRefNo());
				submitTermCreditDetails(apps, createdBy, connection);

				WorkingCapital tempWc = apps.getWc();
				Log.log(4, "ApplicationDAO", "submitApp", "Term Loan Tenure :" + apps.getTermLoan().getTenure());
				tempWc.setWcTenure(apps.getTermLoan().getTenure());
				apps.setWc(tempWc);

				submitWCDetails(apps, createdBy, connection);
				submitSecDetails(apps, connection);

				if (apps.getMCGFDetails() != null) {
					MCGFDetails mcgfDetails = apps.getMCGFDetails();
					mcgfDetails.setApplicationReferenceNumber(wcAppRefNo);
					mcgsProcessor.updateMCGSDetails(mcgfDetails, createdBy, connection);
				}

				Log.log(4, "ApplicationDAO", "submitApp", "tcapprefno :" + tcAppRefNo);
				Log.log(4, "ApplicationDAO", "submitApp", "wcapprefno :" + wcAppRefNo);

				updateAppReference(tcAppRefNo, wcAppRefNo, connection);

				Log.log(4, "ApplicationDAO", "submitApp", "Submitted a BO application");
			}

			connection.commit();
		} catch (SQLException e) {
			Log.log(2, "ApplicationDAO", "submitApp", e.getMessage());
			Log.logException(e);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(2, "ApplicationDAO", "submitApp", ignore.getMessage());
			}

			throw new DatabaseException("Unable to submit Application");
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(4, "ApplicationDAO", "submitApp", "Exited");

		return apps;
	}

	public void updateAppReference(String appRefNo, String appReference, Connection connection)
			throws DatabaseException {
		Log.log(4, "ApplicationDAO", "updateAppReference", "Entered");
		try {
			CallableStatement updateRef = connection.prepareCall("{?=call funcUpdateAppRef(?,?,?)}");

			updateRef.registerOutParameter(1, 4);
			updateRef.registerOutParameter(4, 12);

			updateRef.setString(2, appRefNo);
			updateRef.setString(3, appReference);

			updateRef.executeQuery();
			int functionReturnValue = updateRef.getInt(1);
			Log.log(5, "ApplicationDAO", "updateAppReference", "update App Reference :" + functionReturnValue);

			if (functionReturnValue == 1) {
				String error = updateRef.getString(4);

				updateRef.close();
				updateRef = null;

				connection.rollback();

				Log.log(2, "ApplicationDAO", "updateAppReference", "updateAppReference Exception" + error);
				throw new DatabaseException(error);
			}

		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "updateAppReference", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(4, "ApplicationDAO", "updateAppReference", ignore.getMessage());
			}

			throw new DatabaseException(sqlException.getMessage());
		}
	}

//DKR WORK.....FINAL........... for new application
	public String submitMudraApplicationDetails(Application apps, String createdBy, Connection connection)
			throws DatabaseException {
		Log.log(4, "ApplicationDAO", "submitApplicationDetails", "Exited");

		String appLoanType = apps.getLoanType();
		Log.log(4, "ApplicationDAO", "submitApplicationDetails", "Entering Application Detail method...");
		try {
			RiskManagementProcessor rpProcessor = new RiskManagementProcessor();
			MLIInfo mliInfo = new MLIInfo(); // Added by DKR
			String subSchemeName = rpProcessor.getSubScheme(apps);
			apps.setSubSchemeName(subSchemeName);

			CallableStatement applicationDetails = connection.prepareCall(
					"{call FUNCINSERTAPPLICATIONDTL_MUDRAEGS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			/*
			 * .prepareCall("{?=call funcInsertApplicationDtlNew(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?"
			 * +
			 * ",?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}"
			 * );
			 */ // comment 32 col added

			/*
			 * .prepareCall("{?=call funcInsertApplicationDtlNew(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}"
			 * );
			 */
			applicationDetails.registerOutParameter(1, 4);
			applicationDetails.registerOutParameter(2, 12);
			applicationDetails.registerOutParameter(27, 12);
			applicationDetails.setInt(3, apps.getBorrowerDetails().getSsiDetails().getBorrowerRefNo());
			Log.log(5, "ApplicationDAO", "submitApplicationDetails",
					"SSI Ref No:" + apps.getBorrowerDetails().getSsiDetails().getBorrowerRefNo());

			/*
			 * applicationDetails.setString(4, apps.getScheme()); Log.log(5,
			 * "ApplicationDAO", "submitApplicationDetails", "Scheme Name:" +
			 * apps.getScheme());
			 */

			applicationDetails.setString(4, apps.getBankId());
			Log.log(5, "ApplicationDAO", "submitApplicationDetails", "Bank Id :" + apps.getBankId());

			applicationDetails.setString(5, apps.getZoneId());
			Log.log(5, "ApplicationDAO", "submitApplicationDetails", "Zone id :" + apps.getZoneId());

			applicationDetails.setString(6, apps.getBranchId());
			Log.log(5, "ApplicationDAO", "submitApplicationDetails", "Branch id :" + apps.getBranchId());

			/*
			 * applicationDetails.setString(8, apps.getMliBranchName()); Log.log(5,
			 * "ApplicationDAO", "submitApplicationDetails", "Branch name :" +
			 * apps.getMliBranchName());
			 * 
			 * Log.log(5, "ApplicationDAO", "submitApplicationDetails", "Branch Code :" +
			 * apps.getMliBranchCode()); if ((apps.getMliBranchCode() != null) &&
			 * (!apps.getMliBranchCode().equals(""))) { applicationDetails.setString(9,
			 * apps.getMliBranchCode()); } else applicationDetails.setString(9, null);
			 * 
			 * applicationDetails.setString(10, apps.getMliRefNo()); Log.log(5,
			 * "ApplicationDAO", "submitApplicationDetails", "Bank Ref No :" +
			 * apps.getMliRefNo());
			 */
			/*
			 * if (appLoanType.equals("CC")) applicationDetails.setString(11, "Y"); else {
			 * applicationDetails.setString(11, "N"); }
			 */
			applicationDetails.setString(7, createdBy);
			Log.log(5, "ApplicationDAO", "submitApplicationDetails", "user id :" + createdBy);

			applicationDetails.setString(8, apps.getLoanType());
			Log.log(5, "ApplicationDAO", "submitApplicationDetails", "Loan type :" + apps.getLoanType());

			/*
			 * String collateralSecurityValue = apps.getProjectOutlayDetails()
			 * .getCollateralSecurityTaken(); applicationDetails.setString(14,
			 * collateralSecurityValue); Log.log(5, "ApplicationDAO",
			 * "submitApplicationDetails", "Collateral Security: " +
			 * collateralSecurityValue);
			 * 
			 * String thirdPartyValue = apps.getProjectOutlayDetails()
			 * .getThirdPartyGuaranteeTaken(); applicationDetails.setString(15,
			 * thirdPartyValue); Log.log(5, "ApplicationDAO", "submitApplicationDetails",
			 * "Third party taken : " + thirdPartyValue);
			 * 
			 * if ((apps.getProjectOutlayDetails().getSubsidyName() != null) &&
			 * (!apps.getProjectOutlayDetails().getSubsidyName() .equals(""))) {
			 * applicationDetails.setString(16, apps.getProjectOutlayDetails()
			 * .getSubsidyName()); } else { applicationDetails.setString(16, null); }
			 * Log.log(5, "ApplicationDAO", "submitApplicationDetails", "SubsidyName :" +
			 * apps.getProjectOutlayDetails().getSubsidyName());
			 * 
			 * if (apps.getRehabilitation() == null) { applicationDetails.setString(17,
			 * "N"); } else { String rehabilitationValue = apps.getRehabilitation();
			 * applicationDetails.setString(17, rehabilitationValue); }
			 * 
			 * Log.log(5, "ApplicationDAO", "submitApplicationDetails", "Rehabilitation :" +
			 * apps.getRehabilitation());
			 * 
			 * applicationDetails.setDouble(18, apps.getProjectOutlayDetails()
			 * .getProjectOutlay()); Log.log(5, "ApplicationDAO",
			 * "submitApplicationDetails", "Project Oultay :" +
			 * apps.getProjectOutlayDetails().getProjectOutlay());
			 * 
			 * String cgpanVal = apps.getCgpanReference(); Log.log(5, "ApplicationDAO",
			 * "submitApplicationDetails", "Cgpan reference:" + cgpanVal);
			 * 
			 * if ((cgpanVal != null) && (!cgpanVal.equals(""))) {
			 * applicationDetails.setString(19, cgpanVal); Log.log(5, "ApplicationDAO",
			 * "submitApplicationDetails", "cgpan :" + cgpanVal); } else {
			 * applicationDetails.setNull(19, 12); }
			 */
			applicationDetails.setString(9, createdBy);
			Log.log(5, "ApplicationDAO", "submitApplicationDetails", "User :" + createdBy);

			if ((apps.getRemarks() != null) && (!apps.getRemarks().equals(""))) {
				applicationDetails.setString(10, apps.getRemarks());
			} else {
				applicationDetails.setString(10, null);
			}
			Log.log(5, "ApplicationDAO", "submitApplicationDetails", "Remarks :" + apps.getRemarks());

			/*
			 * applicationDetails.setString(22, apps.getSubSchemeName());
			 * 
			 * Log.log(5, "ApplicationDAO", "submitApplicationDetails", "Sub scheme Name:" +
			 * apps.getSubSchemeName());
			 * 
			 * if ((apps.getBorrowerDetails().getSsiDetails().getMSE() != null) &&
			 * (!apps.getBorrowerDetails().getSsiDetails().getMSE() .equals(""))) {
			 * applicationDetails.setString(23, apps.getBorrowerDetails()
			 * .getSsiDetails().getMSE()); } else { applicationDetails.setString(23, "N"); }
			 * Log.log(5, "ApplicationDAO", "submitApplicationDetails", " MSE:" +
			 * apps.getBorrowerDetails().getSsiDetails().getMSE());
			 * applicationDetails.setString(24, apps.getInternalRate());
			 * 
			 * if ((apps.getHandiCrafts() != null) && (!apps.getHandiCrafts().equals(""))) {
			 * applicationDetails.setString(25, apps.getHandiCrafts()); } else {
			 * applicationDetails.setString(25, "N"); } if ((apps.getDcHandicrafts() !=
			 * null) && (!apps.getDcHandicrafts().equals(""))) {
			 * applicationDetails.setString(26, apps.getDcHandicrafts()); } else {
			 * applicationDetails.setString(26, "N"); }
			 * 
			 * applicationDetails.setString(27, apps.getIcardNo()); if
			 * ((apps.getIcardIssueDate() != null) &&
			 * (!apps.getIcardIssueDate().equals(""))) { applicationDetails.setDate(28, new
			 * java.sql.Date(apps .getIcardIssueDate().getTime())); } else
			 * applicationDetails.setDate(28, null);
			 * 
			 * if ((apps.getJointFinance() != null) && (!apps.getJointFinance().equals("")))
			 * { applicationDetails.setString(29, apps.getJointFinance()); } else
			 * applicationDetails.setString(29, "N");
			 */

			if ((apps.getAppExpiryDate() != null) && (!apps.getAppExpiryDate().equals(""))) {
				applicationDetails.setDate(11, new java.sql.Date(apps.getAppExpiryDate().getTime()));
			} else
				applicationDetails.setDate(11, null);

			System.out.println("EGS Sanction date ===" + apps.getSanctionedDate());
			if ((apps.getSanctionedDate() != null) && (!apps.getSanctionedDate().equals(""))) {
				applicationDetails.setDate(12, new java.sql.Date(apps.getSanctionedDate().getTime()));
			} else
				applicationDetails.setDate(12, null);

			/*
			 * if ((apps.getJointcgpan() != null) && (!apps.getJointcgpan().equals(""))) {
			 * applicationDetails.setString(31, apps.getJointcgpan()); } else
			 * applicationDetails.setDate(31, null);
			 * 
			 * if (apps.getActivityConfirm() == null) { applicationDetails.setString(32,
			 * "N"); } else { String activityConfirm = apps.getActivityConfirm();
			 * applicationDetails.setString(32, activityConfirm); }
			 * 
			 * if ((apps.getDcHandlooms() != null) && (!apps.getDcHandlooms().equals(""))) {
			 * applicationDetails.setString(33, apps.getDcHandlooms()); } else {
			 * applicationDetails.setString(33, "N"); } applicationDetails.setString(34,
			 * apps.getWeaverCreditScheme()); applicationDetails.setString(35,
			 * apps.getHandloomchk()); //bhu 25-May-2015 String isPrimarySecurity =
			 * apps.getProjectOutlayDetails().getIsPrimarySecurity();
			 * applicationDetails.setString(36, isPrimarySecurity);
			 */

			applicationDetails.setString(13, apps.getGstState()); // D STATE_NAME
			applicationDetails.setString(14, apps.getGstNo()); // D Gst
			// sayali

			if ((apps.getExposureFbId() != null && apps.getExposureFbId().equals("") == false)) {
				applicationDetails.setString(15, apps.getExposureFbId()); // D Gst
			} else {
				applicationDetails.setString(15, "0");
			}

			if (apps.getSanctionTenure() == 0) {
				applicationDetails.setNull(16, 4);
			} else {
				applicationDetails.setInt(16, apps.getSanctionTenure());
				Log.log(5, "ApplicationDAO", "submitTermCreditDetails", "tenire:" + apps.getSanctionTenure());
			}

			if (apps.getDPDStatus() == 0) {
				applicationDetails.setNull(17, 4);
			} else {
				applicationDetails.setInt(17, apps.getDPDStatus());
				Log.log(5, "ApplicationDAO", "submitTermCreditDetails", "tenire:" + apps.getDPDStatus());
			}

			applicationDetails.setString(18, apps.getPromDirDefaltFlg());
			

			applicationDetails.setDouble(19, apps.getTypeLoanTC());
			System.out.println("TYPE LOAN TC====" + apps.getTypeLoanTC());
			applicationDetails.setDouble(20, apps.getTypeLoanWC());
			System.out.println("TYPE LOAN WC====" + apps.getTypeLoanWC());
			applicationDetails.setString(21, apps.getActivityConfirm());

			if (apps.getTotalMIcollatSecAmt() == 0) {
				applicationDetails.setNull(22, 4);
			} else {
				applicationDetails.setDouble(22, apps.getTotalMIcollatSecAmt());
				Log.log(5, "ApplicationDAO", "submitTermCreditDetails", "tenire:" + apps.getTotalMIcollatSecAmt());
			}

			applicationDetails.setDouble(23, apps.getTCTYPE());
			System.out.println("TYPE LOAN TC====" + apps.getTCTYPE());
			applicationDetails.setDouble(24, apps.getwCTYPE());
			System.out.println("TYPE LOAN WC====" + apps.getwCTYPE());

			if ((apps.getBorrowerDetails().getSsiDetails().getMSE() != null)
					&& (!apps.getBorrowerDetails().getSsiDetails().getMSE().equals(""))) {
				applicationDetails.setString(25, apps.getBorrowerDetails().getSsiDetails().getMSE());
			} else {
				applicationDetails.setString(25, "N");
			}
			Log.log(5, "ApplicationDAO", "submitApplicationDetails",
					" MSE:" + apps.getBorrowerDetails().getSsiDetails().getMSE());
			
			System.out.println("Mudra flag=="+apps.getBorrowerDetails().getSsiDetails().getMSE());

			applicationDetails.setLong(26, apps.getProMobileNo());

			// Hybrid security

			/*
			 * if (apps.getHybridSecurity() !=null){
			 * System.out.println(apps.getHybridSecurity()+"-"+apps.
			 * getMovCollateratlSecurityAmt()+"-DAO Y-"+apps.getImmovCollateratlSecurityAmt(
			 * )+"-"+apps.getTotalMIcollatSecAmt()+"-"); //applicationDetails.setString(41,
			 * apps.getHybridSecurity()); applicationDetails.setDouble(15,
			 * apps.getMovCollateratlSecurityAmt()); applicationDetails.setDouble(43,
			 * apps.getImmovCollateratlSecurityAmt()); applicationDetails.setDouble(44,
			 * apps.getTotalMIcollatSecAmt()); }else{
			 * System.out.println("--DAO N-"+apps.getImmovCollateratlSecurityAmt()+"-"+apps.
			 * getTotalMIcollatSecAmt()+"-"); applicationDetails.setString(41, "N");
			 * applicationDetails.setDouble(42, 0.0); applicationDetails.setDouble(43, 0.0);
			 * applicationDetails.setDouble(44, 0.0); } applicationDetails.setLong(45,
			 * apps.getProMobileNo()); // Add new Column applicationDetails.setString(46,
			 * apps.getPromDirDefaltFlg()); applicationDetails.setInt(47,
			 * apps.getCredBureKeyPromScor()); applicationDetails.setInt(48,
			 * apps.getCredBurePromScor2()); applicationDetails.setInt(49,
			 * apps.getCredBurePromScor3()); applicationDetails.setInt(50,
			 * apps.getCredBurePromScor4()); applicationDetails.setInt(51,
			 * apps.getCredBurePromScor5()); applicationDetails.setString(52,
			 * apps.getCredBureName1()); applicationDetails.setString(53,
			 * apps.getCredBureName2()); applicationDetails.setString(54,
			 * apps.getCredBureName3()); applicationDetails.setString(55,
			 * apps.getCredBureName4()); applicationDetails.setString(56,
			 * apps.getCredBureName5()); applicationDetails.setInt(57,
			 * apps.getCibilFirmMsmeRank()); applicationDetails.setInt(58,
			 * apps.getExpCommerScor()); applicationDetails.setFloat(59,
			 * apps.getPromBorrNetWorth()); applicationDetails.setInt(60,
			 * apps.getPromContribution()); applicationDetails.setString(61,
			 * apps.getPromGAssoNPA1YrFlg()); applicationDetails.setInt(62,
			 * apps.getPromBussExpYr()); applicationDetails.setFloat(63,
			 * apps.getSalesRevenue()); applicationDetails.setFloat(64, apps.getTaxPBIT());
			 * applicationDetails.setFloat(65, apps.getInterestPayment());
			 * applicationDetails.setFloat(66, apps.getTaxCurrentProvisionAmt());
			 * applicationDetails.setFloat(67, apps.getTotCurrentAssets());
			 * applicationDetails.setFloat(68, apps.getTotCurrentLiability());
			 * applicationDetails.setFloat(69, apps.getTotTermLiability());
			 * applicationDetails.setFloat(70, apps.getExuityCapital());
			 * applicationDetails.setFloat(71, apps.getPreferenceCapital());
			 * applicationDetails.setFloat(72, apps.getReservesSurplus());
			 * applicationDetails.setFloat(73, apps.getRepaymentDueNyrAmt());
			 * 
			 * applicationDetails.setFloat(74, apps.getOpratIncome());
			 * applicationDetails.setFloat(75, apps.getProfAftTax());
			 * applicationDetails.setFloat(76, apps.getNetworth());
			 * applicationDetails.setFloat(77, apps.getDebitEqtRatioUnt());
			 * applicationDetails.setFloat(78, apps.getDebitSrvCoverageRatioTl());
			 * applicationDetails.setFloat(79, apps.getCurrentRatioWc());
			 * applicationDetails.setFloat(80, apps.getDebitEqtRatio());
			 * applicationDetails.setFloat(81, apps.getDebitSrvCoverageRatio());
			 * applicationDetails.setFloat(82, apps.getCurrentRatios());
			 * applicationDetails.setInt(83, apps.getCreditBureauChiefPromScor());
			 * applicationDetails.setFloat(84, apps.getTotalAssets());
			 * applicationDetails.setString(85, apps.getExistGreenFldUnitType());
			 */

			applicationDetails.executeQuery();
			int functionReturnValue = applicationDetails.getInt(1);
			Log.log(5, "ApplicationDAO", "submitApplicationDetails", "Application Details :" + functionReturnValue);
			// if(functionReturnValue != 1){
			// updateAadharDetailsRenew(apps, connection); //Updated aadhar
			// }
			if (functionReturnValue == 1) {
				String error = applicationDetails.getString(27);

				applicationDetails.close();
				applicationDetails = null;

				connection.rollback();

				Log.log(2, "ApplicationDAO", "submitApp", "Application Detail Exception" + error);
				// System.out.println("submitApplication 4 error="+error);
				throw new DatabaseException(error);
			}

			apps.setAppRefNo(applicationDetails.getString(2));

			applicationDetails.close();
			applicationDetails = null;
			System.out.println("Diksha---5");
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			Log.log(4, "ApplicationDAO", "submitApplicationDetails", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();

			} catch (SQLException ignore) {
				sqlException.printStackTrace();
				Log.log(4, "ApplicationDAO", "submitApplicationDetails", ignore.getMessage());
			}
			// System.out.println("submitApplication 5 error="+sqlException.getMessage());
			// throw new DatabaseException(sqlException.getMessage());
		}

		String applicationRefNo = apps.getAppRefNo();

		Log.log(4, "ApplicationDAO", "submitApplicationDetails", "Application Reference No. :" + applicationRefNo);

		Log.log(4, "ApplicationDAO", "submitApplicationDetails", "Exited");

		return applicationRefNo;
	}
	
	public String submitApplicationDetails(Application apps, String createdBy, Connection connection)
			throws DatabaseException {
		Log.log(4, "ApplicationDAO", "submitApplicationDetails", "Exited");

		String appLoanType = apps.getLoanType();
		Log.log(4, "ApplicationDAO", "submitApplicationDetails", "Entering Application Detail method...");
		try {
			RiskManagementProcessor rpProcessor = new RiskManagementProcessor();
			MLIInfo mliInfo = new MLIInfo(); // Added by DKR
			String subSchemeName = rpProcessor.getSubScheme(apps);
			apps.setSubSchemeName(subSchemeName);

			CallableStatement applicationDetails = connection.prepareCall(
					"{call funcInsertApplicationDtlNewEGS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			/*
			 * .prepareCall("{?=call funcInsertApplicationDtlNew(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?"
			 * +
			 * ",?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}"
			 * );
			 */ // comment 32 col added

			/*
			 * .prepareCall("{?=call funcInsertApplicationDtlNew(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}"
			 * );
			 */
			applicationDetails.registerOutParameter(1, 4);
			applicationDetails.registerOutParameter(2, 12);
			applicationDetails.registerOutParameter(27, 12);
			applicationDetails.setInt(3, apps.getBorrowerDetails().getSsiDetails().getBorrowerRefNo());
			Log.log(5, "ApplicationDAO", "submitApplicationDetails",
					"SSI Ref No:" + apps.getBorrowerDetails().getSsiDetails().getBorrowerRefNo());

			/*
			 * applicationDetails.setString(4, apps.getScheme()); Log.log(5,
			 * "ApplicationDAO", "submitApplicationDetails", "Scheme Name:" +
			 * apps.getScheme());
			 */

			applicationDetails.setString(4, apps.getBankId());
			Log.log(5, "ApplicationDAO", "submitApplicationDetails", "Bank Id :" + apps.getBankId());

			applicationDetails.setString(5, apps.getZoneId());
			Log.log(5, "ApplicationDAO", "submitApplicationDetails", "Zone id :" + apps.getZoneId());

			applicationDetails.setString(6, apps.getBranchId());
			Log.log(5, "ApplicationDAO", "submitApplicationDetails", "Branch id :" + apps.getBranchId());

			/*
			 * applicationDetails.setString(8, apps.getMliBranchName()); Log.log(5,
			 * "ApplicationDAO", "submitApplicationDetails", "Branch name :" +
			 * apps.getMliBranchName());
			 * 
			 * Log.log(5, "ApplicationDAO", "submitApplicationDetails", "Branch Code :" +
			 * apps.getMliBranchCode()); if ((apps.getMliBranchCode() != null) &&
			 * (!apps.getMliBranchCode().equals(""))) { applicationDetails.setString(9,
			 * apps.getMliBranchCode()); } else applicationDetails.setString(9, null);
			 * 
			 * applicationDetails.setString(10, apps.getMliRefNo()); Log.log(5,
			 * "ApplicationDAO", "submitApplicationDetails", "Bank Ref No :" +
			 * apps.getMliRefNo());
			 */
			/*
			 * if (appLoanType.equals("CC")) applicationDetails.setString(11, "Y"); else {
			 * applicationDetails.setString(11, "N"); }
			 */
			applicationDetails.setString(7, createdBy);
			Log.log(5, "ApplicationDAO", "submitApplicationDetails", "user id :" + createdBy);

			applicationDetails.setString(8, apps.getLoanType());
			Log.log(5, "ApplicationDAO", "submitApplicationDetails", "Loan type :" + apps.getLoanType());

			/*
			 * String collateralSecurityValue = apps.getProjectOutlayDetails()
			 * .getCollateralSecurityTaken(); applicationDetails.setString(14,
			 * collateralSecurityValue); Log.log(5, "ApplicationDAO",
			 * "submitApplicationDetails", "Collateral Security: " +
			 * collateralSecurityValue);
			 * 
			 * String thirdPartyValue = apps.getProjectOutlayDetails()
			 * .getThirdPartyGuaranteeTaken(); applicationDetails.setString(15,
			 * thirdPartyValue); Log.log(5, "ApplicationDAO", "submitApplicationDetails",
			 * "Third party taken : " + thirdPartyValue);
			 * 
			 * if ((apps.getProjectOutlayDetails().getSubsidyName() != null) &&
			 * (!apps.getProjectOutlayDetails().getSubsidyName() .equals(""))) {
			 * applicationDetails.setString(16, apps.getProjectOutlayDetails()
			 * .getSubsidyName()); } else { applicationDetails.setString(16, null); }
			 * Log.log(5, "ApplicationDAO", "submitApplicationDetails", "SubsidyName :" +
			 * apps.getProjectOutlayDetails().getSubsidyName());
			 * 
			 * if (apps.getRehabilitation() == null) { applicationDetails.setString(17,
			 * "N"); } else { String rehabilitationValue = apps.getRehabilitation();
			 * applicationDetails.setString(17, rehabilitationValue); }
			 * 
			 * Log.log(5, "ApplicationDAO", "submitApplicationDetails", "Rehabilitation :" +
			 * apps.getRehabilitation());
			 * 
			 * applicationDetails.setDouble(18, apps.getProjectOutlayDetails()
			 * .getProjectOutlay()); Log.log(5, "ApplicationDAO",
			 * "submitApplicationDetails", "Project Oultay :" +
			 * apps.getProjectOutlayDetails().getProjectOutlay());
			 * 
			 * String cgpanVal = apps.getCgpanReference(); Log.log(5, "ApplicationDAO",
			 * "submitApplicationDetails", "Cgpan reference:" + cgpanVal);
			 * 
			 * if ((cgpanVal != null) && (!cgpanVal.equals(""))) {
			 * applicationDetails.setString(19, cgpanVal); Log.log(5, "ApplicationDAO",
			 * "submitApplicationDetails", "cgpan :" + cgpanVal); } else {
			 * applicationDetails.setNull(19, 12); }
			 */
			applicationDetails.setString(9, createdBy);
			Log.log(5, "ApplicationDAO", "submitApplicationDetails", "User :" + createdBy);

			if ((apps.getRemarks() != null) && (!apps.getRemarks().equals(""))) {
				applicationDetails.setString(10, apps.getRemarks());
			} else {
				applicationDetails.setString(10, null);
			}
			Log.log(5, "ApplicationDAO", "submitApplicationDetails", "Remarks :" + apps.getRemarks());

			/*
			 * applicationDetails.setString(22, apps.getSubSchemeName());
			 * 
			 * Log.log(5, "ApplicationDAO", "submitApplicationDetails", "Sub scheme Name:" +
			 * apps.getSubSchemeName());
			 * 
			 * if ((apps.getBorrowerDetails().getSsiDetails().getMSE() != null) &&
			 * (!apps.getBorrowerDetails().getSsiDetails().getMSE() .equals(""))) {
			 * applicationDetails.setString(23, apps.getBorrowerDetails()
			 * .getSsiDetails().getMSE()); } else { applicationDetails.setString(23, "N"); }
			 * Log.log(5, "ApplicationDAO", "submitApplicationDetails", " MSE:" +
			 * apps.getBorrowerDetails().getSsiDetails().getMSE());
			 * applicationDetails.setString(24, apps.getInternalRate());
			 * 
			 * if ((apps.getHandiCrafts() != null) && (!apps.getHandiCrafts().equals(""))) {
			 * applicationDetails.setString(25, apps.getHandiCrafts()); } else {
			 * applicationDetails.setString(25, "N"); } if ((apps.getDcHandicrafts() !=
			 * null) && (!apps.getDcHandicrafts().equals(""))) {
			 * applicationDetails.setString(26, apps.getDcHandicrafts()); } else {
			 * applicationDetails.setString(26, "N"); }
			 * 
			 * applicationDetails.setString(27, apps.getIcardNo()); if
			 * ((apps.getIcardIssueDate() != null) &&
			 * (!apps.getIcardIssueDate().equals(""))) { applicationDetails.setDate(28, new
			 * java.sql.Date(apps .getIcardIssueDate().getTime())); } else
			 * applicationDetails.setDate(28, null);
			 * 
			 * if ((apps.getJointFinance() != null) && (!apps.getJointFinance().equals("")))
			 * { applicationDetails.setString(29, apps.getJointFinance()); } else
			 * applicationDetails.setString(29, "N");
			 */

			if ((apps.getAppExpiryDate() != null) && (!apps.getAppExpiryDate().equals(""))) {
				applicationDetails.setDate(11, new java.sql.Date(apps.getAppExpiryDate().getTime()));
			} else
				applicationDetails.setDate(11, null);

			System.out.println("EGS Sanction date ===" + apps.getSanctionedDate());
			if ((apps.getSanctionedDate() != null) && (!apps.getSanctionedDate().equals(""))) {
				applicationDetails.setDate(12, new java.sql.Date(apps.getSanctionedDate().getTime()));
			} else
				applicationDetails.setDate(12, null);

			/*
			 * if ((apps.getJointcgpan() != null) && (!apps.getJointcgpan().equals(""))) {
			 * applicationDetails.setString(31, apps.getJointcgpan()); } else
			 * applicationDetails.setDate(31, null);
			 * 
			 * if (apps.getActivityConfirm() == null) { applicationDetails.setString(32,
			 * "N"); } else { String activityConfirm = apps.getActivityConfirm();
			 * applicationDetails.setString(32, activityConfirm); }
			 * 
			 * if ((apps.getDcHandlooms() != null) && (!apps.getDcHandlooms().equals(""))) {
			 * applicationDetails.setString(33, apps.getDcHandlooms()); } else {
			 * applicationDetails.setString(33, "N"); } applicationDetails.setString(34,
			 * apps.getWeaverCreditScheme()); applicationDetails.setString(35,
			 * apps.getHandloomchk()); //bhu 25-May-2015 String isPrimarySecurity =
			 * apps.getProjectOutlayDetails().getIsPrimarySecurity();
			 * applicationDetails.setString(36, isPrimarySecurity);
			 */

			applicationDetails.setString(13, apps.getGstState()); // D STATE_NAME
			applicationDetails.setString(14, apps.getGstNo()); // D Gst
			// sayali

			if ((apps.getExposureFbId() != null && apps.getExposureFbId().equals("") == false)) {
				applicationDetails.setString(15, apps.getExposureFbId()); // D Gst
			} else {
				applicationDetails.setString(15, "0");
			}

			if (apps.getSanctionTenure() == 0) {
				applicationDetails.setNull(16, 4);
			} else {
				applicationDetails.setInt(16, apps.getSanctionTenure());
				Log.log(5, "ApplicationDAO", "submitTermCreditDetails", "tenire:" + apps.getSanctionTenure());
			}

			if (apps.getDPDStatus() == 0) {
				applicationDetails.setNull(17, 4);
			} else {
				applicationDetails.setInt(17, apps.getDPDStatus());
				Log.log(5, "ApplicationDAO", "submitTermCreditDetails", "tenire:" + apps.getDPDStatus());
			}

			applicationDetails.setString(18, apps.getPromDirDefaltFlg());
			

			applicationDetails.setDouble(19, apps.getTypeLoanTC());
			System.out.println("TYPE LOAN TC====" + apps.getTypeLoanTC());
			applicationDetails.setDouble(20, apps.getTypeLoanWC());
			System.out.println("TYPE LOAN WC====" + apps.getTypeLoanWC());
			applicationDetails.setString(21, apps.getActivityConfirm());

			if (apps.getTotalMIcollatSecAmt() == 0) {
				applicationDetails.setNull(22, 4);
			} else {
				applicationDetails.setDouble(22, apps.getTotalMIcollatSecAmt());
				Log.log(5, "ApplicationDAO", "submitTermCreditDetails", "tenire:" + apps.getTotalMIcollatSecAmt());
			}

			applicationDetails.setDouble(23, apps.getTCTYPE());
			System.out.println("TYPE LOAN TC====" + apps.getTCTYPE());
			applicationDetails.setDouble(24, apps.getwCTYPE());
			System.out.println("TYPE LOAN WC====" + apps.getwCTYPE());

			if ((apps.getBorrowerDetails().getSsiDetails().getMSE() != null)
					&& (!apps.getBorrowerDetails().getSsiDetails().getMSE().equals(""))) {
				applicationDetails.setString(25, apps.getBorrowerDetails().getSsiDetails().getMSE());
			} else {
				applicationDetails.setString(25, "N");
			}
			Log.log(5, "ApplicationDAO", "submitApplicationDetails",
					" MSE:" + apps.getBorrowerDetails().getSsiDetails().getMSE());
			
			System.out.println("Mudra flag=="+apps.getBorrowerDetails().getSsiDetails().getMSE());

			applicationDetails.setLong(26, apps.getProMobileNo());

			// Hybrid security

			/*
			 * if (apps.getHybridSecurity() !=null){
			 * System.out.println(apps.getHybridSecurity()+"-"+apps.
			 * getMovCollateratlSecurityAmt()+"-DAO Y-"+apps.getImmovCollateratlSecurityAmt(
			 * )+"-"+apps.getTotalMIcollatSecAmt()+"-"); //applicationDetails.setString(41,
			 * apps.getHybridSecurity()); applicationDetails.setDouble(15,
			 * apps.getMovCollateratlSecurityAmt()); applicationDetails.setDouble(43,
			 * apps.getImmovCollateratlSecurityAmt()); applicationDetails.setDouble(44,
			 * apps.getTotalMIcollatSecAmt()); }else{
			 * System.out.println("--DAO N-"+apps.getImmovCollateratlSecurityAmt()+"-"+apps.
			 * getTotalMIcollatSecAmt()+"-"); applicationDetails.setString(41, "N");
			 * applicationDetails.setDouble(42, 0.0); applicationDetails.setDouble(43, 0.0);
			 * applicationDetails.setDouble(44, 0.0); } applicationDetails.setLong(45,
			 * apps.getProMobileNo()); // Add new Column applicationDetails.setString(46,
			 * apps.getPromDirDefaltFlg()); applicationDetails.setInt(47,
			 * apps.getCredBureKeyPromScor()); applicationDetails.setInt(48,
			 * apps.getCredBurePromScor2()); applicationDetails.setInt(49,
			 * apps.getCredBurePromScor3()); applicationDetails.setInt(50,
			 * apps.getCredBurePromScor4()); applicationDetails.setInt(51,
			 * apps.getCredBurePromScor5()); applicationDetails.setString(52,
			 * apps.getCredBureName1()); applicationDetails.setString(53,
			 * apps.getCredBureName2()); applicationDetails.setString(54,
			 * apps.getCredBureName3()); applicationDetails.setString(55,
			 * apps.getCredBureName4()); applicationDetails.setString(56,
			 * apps.getCredBureName5()); applicationDetails.setInt(57,
			 * apps.getCibilFirmMsmeRank()); applicationDetails.setInt(58,
			 * apps.getExpCommerScor()); applicationDetails.setFloat(59,
			 * apps.getPromBorrNetWorth()); applicationDetails.setInt(60,
			 * apps.getPromContribution()); applicationDetails.setString(61,
			 * apps.getPromGAssoNPA1YrFlg()); applicationDetails.setInt(62,
			 * apps.getPromBussExpYr()); applicationDetails.setFloat(63,
			 * apps.getSalesRevenue()); applicationDetails.setFloat(64, apps.getTaxPBIT());
			 * applicationDetails.setFloat(65, apps.getInterestPayment());
			 * applicationDetails.setFloat(66, apps.getTaxCurrentProvisionAmt());
			 * applicationDetails.setFloat(67, apps.getTotCurrentAssets());
			 * applicationDetails.setFloat(68, apps.getTotCurrentLiability());
			 * applicationDetails.setFloat(69, apps.getTotTermLiability());
			 * applicationDetails.setFloat(70, apps.getExuityCapital());
			 * applicationDetails.setFloat(71, apps.getPreferenceCapital());
			 * applicationDetails.setFloat(72, apps.getReservesSurplus());
			 * applicationDetails.setFloat(73, apps.getRepaymentDueNyrAmt());
			 * 
			 * applicationDetails.setFloat(74, apps.getOpratIncome());
			 * applicationDetails.setFloat(75, apps.getProfAftTax());
			 * applicationDetails.setFloat(76, apps.getNetworth());
			 * applicationDetails.setFloat(77, apps.getDebitEqtRatioUnt());
			 * applicationDetails.setFloat(78, apps.getDebitSrvCoverageRatioTl());
			 * applicationDetails.setFloat(79, apps.getCurrentRatioWc());
			 * applicationDetails.setFloat(80, apps.getDebitEqtRatio());
			 * applicationDetails.setFloat(81, apps.getDebitSrvCoverageRatio());
			 * applicationDetails.setFloat(82, apps.getCurrentRatios());
			 * applicationDetails.setInt(83, apps.getCreditBureauChiefPromScor());
			 * applicationDetails.setFloat(84, apps.getTotalAssets());
			 * applicationDetails.setString(85, apps.getExistGreenFldUnitType());
			 */

			applicationDetails.executeQuery();
			int functionReturnValue = applicationDetails.getInt(1);
			Log.log(5, "ApplicationDAO", "submitApplicationDetails", "Application Details :" + functionReturnValue);
			// if(functionReturnValue != 1){
			// updateAadharDetailsRenew(apps, connection); //Updated aadhar
			// }
			if (functionReturnValue == 1) {
				String error = applicationDetails.getString(27);

				applicationDetails.close();
				applicationDetails = null;

				connection.rollback();

				Log.log(2, "ApplicationDAO", "submitApp", "Application Detail Exception" + error);
				// System.out.println("submitApplication 4 error="+error);
				throw new DatabaseException(error);
			}

			apps.setAppRefNo(applicationDetails.getString(2));

			applicationDetails.close();
			applicationDetails = null;
			System.out.println("Diksha---5");
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			Log.log(4, "ApplicationDAO", "submitApplicationDetails", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();

			} catch (SQLException ignore) {
				sqlException.printStackTrace();
				Log.log(4, "ApplicationDAO", "submitApplicationDetails", ignore.getMessage());
			}
			// System.out.println("submitApplication 5 error="+sqlException.getMessage());
			// throw new DatabaseException(sqlException.getMessage());
		}

		String applicationRefNo = apps.getAppRefNo();

		Log.log(4, "ApplicationDAO", "submitApplicationDetails", "Application Reference No. :" + applicationRefNo);

		Log.log(4, "ApplicationDAO", "submitApplicationDetails", "Exited");

		return applicationRefNo;
	}

//bhu
	public String submitRsfApplicationDetails(Application apps, String createdBy, Connection connection)
			throws DatabaseException {
		Log.log(4, "ApplicationDAO", "submitRsfApplicationDetails", "Exited");

		String appLoanType = apps.getLoanType();
		Log.log(4, "ApplicationDAO", "submitRsfApplicationDetails", "Entering Application Detail method...");
		try {
			// System.out.println("submitRsfApplicationDetails from dao 2823");
			RiskManagementProcessor rpProcessor = new RiskManagementProcessor();
			String subSchemeName = rpProcessor.getSubScheme(apps);
			apps.setSubSchemeName(subSchemeName);

			CallableStatement applicationDetails = connection.prepareCall(
					"{?=call funcInsertApplicationDtlMod(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			applicationDetails.registerOutParameter(1, 4);
			applicationDetails.registerOutParameter(2, 12);
			applicationDetails.registerOutParameter(26, 12);

			applicationDetails.setInt(3, apps.getBorrowerDetails().getSsiDetails().getBorrowerRefNo());
			Log.log(5, "ApplicationDAO", "submitRsfApplicationDetails",
					"SSI Ref No:" + apps.getBorrowerDetails().getSsiDetails().getBorrowerRefNo());

			applicationDetails.setString(4, apps.getScheme());
			Log.log(5, "ApplicationDAO", "submitRsfApplicationDetails", "Scheme Name:" + apps.getScheme());

			applicationDetails.setString(5, apps.getBankId());
			Log.log(5, "ApplicationDAO", "submitRsfApplicationDetails", "Bank Id :" + apps.getBankId());

			applicationDetails.setString(6, apps.getZoneId());
			Log.log(5, "ApplicationDAO", "submitRsfApplicationDetails", "Zone id :" + apps.getZoneId());

			applicationDetails.setString(7, apps.getBranchId());
			Log.log(5, "ApplicationDAO", "submitRsfApplicationDetails", "Branch id :" + apps.getBranchId());

			applicationDetails.setString(8, apps.getMliBranchName());
			Log.log(5, "ApplicationDAO", "submitRsfApplicationDetails", "Branch name :" + apps.getMliBranchName());

			Log.log(5, "ApplicationDAO", "submitRsfApplicationDetails", "Branch Code :" + apps.getMliBranchCode());
			if ((apps.getMliBranchCode() != null) && (!apps.getMliBranchCode().equals(""))) {
				applicationDetails.setString(9, apps.getMliBranchCode());
			} else
				applicationDetails.setString(9, null);

			applicationDetails.setString(10, apps.getMliRefNo());
			Log.log(5, "ApplicationDAO", "submitRsfApplicationDetails", "Bank Ref No :" + apps.getMliRefNo());

			if (appLoanType.equals("CC"))
				applicationDetails.setString(11, "Y");
			else {
				applicationDetails.setString(11, "N");
			}
			applicationDetails.setString(12, createdBy);
			Log.log(5, "ApplicationDAO", "submitRsfApplicationDetails", "user id :" + createdBy);

			applicationDetails.setString(13, apps.getLoanType());
			Log.log(5, "ApplicationDAO", "submitRsfApplicationDetails", "Loan type :" + apps.getLoanType());

			String collateralSecurityValue = apps.getProjectOutlayDetails().getCollateralSecurityTaken();
			applicationDetails.setString(14, collateralSecurityValue);
			Log.log(5, "ApplicationDAO", "submitRsfApplicationDetails",
					"Collateral Security: " + collateralSecurityValue);

			String thirdPartyValue = apps.getProjectOutlayDetails().getThirdPartyGuaranteeTaken();
			applicationDetails.setString(15, thirdPartyValue);
			Log.log(5, "ApplicationDAO", "submitRsfApplicationDetails", "Third party taken : " + thirdPartyValue);

			if ((apps.getProjectOutlayDetails().getSubsidyName() != null)
					&& (!apps.getProjectOutlayDetails().getSubsidyName().equals(""))) {
				applicationDetails.setString(16, apps.getProjectOutlayDetails().getSubsidyName());
			} else {
				applicationDetails.setString(16, null);
			}
			Log.log(5, "ApplicationDAO", "submitRsfApplicationDetails",
					"SubsidyName :" + apps.getProjectOutlayDetails().getSubsidyName());

			if (apps.getRehabilitation() == null) {
				applicationDetails.setString(17, "N");
			} else {
				String rehabilitationValue = apps.getRehabilitation();
				applicationDetails.setString(17, rehabilitationValue);
			}

			Log.log(5, "ApplicationDAO", "submitRsfApplicationDetails", "Rehabilitation :" + apps.getRehabilitation());

			applicationDetails.setDouble(18, apps.getProjectOutlayDetails().getProjectOutlay());
			Log.log(5, "ApplicationDAO", "submitRsfApplicationDetails",
					"Project Oultay :" + apps.getProjectOutlayDetails().getProjectOutlay());

			String cgpanVal = apps.getCgpanReference();
			Log.log(5, "ApplicationDAO", "submitRsfApplicationDetails", "Cgpan reference:" + cgpanVal);

			if ((cgpanVal != null) && (!cgpanVal.equals(""))) {
				applicationDetails.setString(19, cgpanVal);
				Log.log(5, "ApplicationDAO", "submitRsfApplicationDetails", "cgpan :" + cgpanVal);
			} else {
				applicationDetails.setNull(19, 12);
			}

			applicationDetails.setString(20, createdBy);
			Log.log(5, "ApplicationDAO", "submitRsfApplicationDetails", "User :" + createdBy);

			if ((apps.getRemarks() != null) && (!apps.getRemarks().equals(""))) {
				applicationDetails.setString(21, apps.getRemarks());
			} else {
				applicationDetails.setString(21, null);
			}
			Log.log(5, "ApplicationDAO", "submitRsfApplicationDetails", "Remarks :" + apps.getRemarks());

			applicationDetails.setString(22, apps.getSubSchemeName());

			Log.log(5, "ApplicationDAO", "submitRsfApplicationDetails", "Sub scheme Name:" + apps.getSubSchemeName());

			if ((apps.getBorrowerDetails().getSsiDetails().getMSE() != null)
					&& (!apps.getBorrowerDetails().getSsiDetails().getMSE().equals(""))) {
				applicationDetails.setString(23, apps.getBorrowerDetails().getSsiDetails().getMSE());
			} else {
				applicationDetails.setString(23, "N");
			}
			Log.log(5, "ApplicationDAO", "submitRsfApplicationDetails",
					" MSE:" + apps.getBorrowerDetails().getSsiDetails().getMSE());
			applicationDetails.setString(24, apps.getInternalRate());
			applicationDetails.setString(25, apps.getExternalRate());
			applicationDetails.executeQuery();
			int functionReturnValue = applicationDetails.getInt(1);
			Log.log(5, "ApplicationDAO", "submitRsfApplicationDetails", "Application Details :" + functionReturnValue);

			if (functionReturnValue == 1) {
				String error = applicationDetails.getString(24);

				applicationDetails.close();
				applicationDetails = null;

				connection.rollback();

				Log.log(2, "ApplicationDAO", "submitApp", "Application Detail Exception" + error);
				throw new DatabaseException(error);
			}

			apps.setAppRefNo(applicationDetails.getString(2));

			applicationDetails.close();
			applicationDetails = null;
		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "submitRsfApplicationDetails", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(4, "ApplicationDAO", "submitRsfApplicationDetails", ignore.getMessage());
			}

			throw new DatabaseException(sqlException.getMessage());
		}

		String applicationRefNo = apps.getAppRefNo();

		Log.log(4, "ApplicationDAO", "submitRsfApplicationDetails", "Application Reference No. :" + applicationRefNo);

		Log.log(4, "ApplicationDAO", "submitRsfApplicationDetails", "Exited");

		return applicationRefNo;
	}

	// Added by DKR update Aadhar in Promoter
	public void updateAadharDetailsRenew(Application app, Connection connection) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "updateAadharDetails", "Entered");
		try {
			PreparedStatement pStmt = null;
			ResultSet rsSet = null;
			if (!app.getAppRefNo().equals("")) {
				// System.out.println(app.getAppRefNo()+"...........................()Renew2.................updateAadharNoPROMOTER_ADHAR_NO=
				// "+app.getAdharNo());
				String query = "UPDATE PROMOTER_DETAIL set PROMOTER_ADHAR_NO='" + app.getAdharNo() + "' where"
						+ " SSI_REFERENCE_NUMBER IN (select pd.SSI_REFERENCE_NUMBER from application_detail ap,PROMOTER_DETAIL pd "
						+ "where ap.SSI_REFERENCE_NUMBER=pd.SSI_REFERENCE_NUMBER and ap.APP_REF_NO='"
						+ app.getAppRefNo() + "' AND pd.PROMOTER_ADHAR_NO IS NULL)";
				pStmt = connection.prepareStatement(query);
				rsSet = pStmt.executeQuery();
				// System.out.println("DKR.>>ren>>>DAO>>:: "+query);

			}
			pStmt.close();
			rsSet.close();
		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "updateAadharDetails", sqlException.getMessage());
			Log.logException(sqlException);
			sqlException.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException ignore) {
			}
			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);

		}
	}

	// End update Aadhar in Promoter
	public String submitRsf2ApplicationDetails(Application apps, String createdBy, Connection connection)
			throws DatabaseException {
		Log.log(4, "ApplicationDAO", "submitRsf2ApplicationDetails", "Exited");

		String appLoanType = apps.getLoanType();
		Log.log(4, "ApplicationDAO", "submitRsf2ApplicationDetails", "Entering Application Detail method...");
		try {
			RiskManagementProcessor rpProcessor = new RiskManagementProcessor();
			String subSchemeName = rpProcessor.getSubScheme(apps);
			apps.setSubSchemeName(subSchemeName);

			// System.out.println(" apps.getScheme()" + apps.getScheme());

			CallableStatement applicationDetails = connection.prepareCall(
					"{?=call funcInsertApplicationDtlMod(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			applicationDetails.registerOutParameter(1, 4);
			applicationDetails.registerOutParameter(2, 12);
			applicationDetails.registerOutParameter(26, 12);

			applicationDetails.setInt(3, apps.getBorrowerDetails().getSsiDetails().getBorrowerRefNo());
			Log.log(5, "ApplicationDAO", "submitRsf2ApplicationDetails",
					"SSI Ref No:" + apps.getBorrowerDetails().getSsiDetails().getBorrowerRefNo());

			applicationDetails.setString(4, apps.getScheme());

			Log.log(5, "ApplicationDAO", "submitRsf2ApplicationDetails", "Scheme Name:" + apps.getScheme());

			applicationDetails.setString(5, apps.getBankId());
			Log.log(5, "ApplicationDAO", "submitRsf2ApplicationDetails", "Bank Id :" + apps.getBankId());

			applicationDetails.setString(6, apps.getZoneId());
			Log.log(5, "ApplicationDAO", "submitRsf2ApplicationDetails", "Zone id :" + apps.getZoneId());

			applicationDetails.setString(7, apps.getBranchId());
			Log.log(5, "ApplicationDAO", "submitRsf2ApplicationDetails", "Branch id :" + apps.getBranchId());

			applicationDetails.setString(8, apps.getMliBranchName());
			Log.log(5, "ApplicationDAO", "submitRsf2ApplicationDetails", "Branch name :" + apps.getMliBranchName());

			Log.log(5, "ApplicationDAO", "submitRsf2ApplicationDetails", "Branch Code :" + apps.getMliBranchCode());
			if ((apps.getMliBranchCode() != null) && (!apps.getMliBranchCode().equals(""))) {
				applicationDetails.setString(9, apps.getMliBranchCode());
			} else
				applicationDetails.setString(9, null);

			applicationDetails.setString(10, apps.getMliRefNo());
			Log.log(5, "ApplicationDAO", "submitRsf2ApplicationDetails", "Bank Ref No :" + apps.getMliRefNo());

			if (appLoanType.equals("CC"))
				applicationDetails.setString(11, "Y");
			else {
				applicationDetails.setString(11, "N");
			}
			applicationDetails.setString(12, createdBy);
			Log.log(5, "ApplicationDAO", "submitRsf2ApplicationDetails", "user id :" + createdBy);

			applicationDetails.setString(13, apps.getLoanType());
			Log.log(5, "ApplicationDAO", "submitRsf2ApplicationDetails", "Loan type :" + apps.getLoanType());

			String collateralSecurityValue = apps.getProjectOutlayDetails().getCollateralSecurityTaken();
			applicationDetails.setString(14, collateralSecurityValue);
			Log.log(5, "ApplicationDAO", "submitRsf2ApplicationDetails",
					"Collateral Security: " + collateralSecurityValue);

			String thirdPartyValue = apps.getProjectOutlayDetails().getThirdPartyGuaranteeTaken();
			applicationDetails.setString(15, thirdPartyValue);
			Log.log(5, "ApplicationDAO", "submitRsf2ApplicationDetails", "Third party taken : " + thirdPartyValue);

			if ((apps.getProjectOutlayDetails().getSubsidyName() != null)
					&& (!apps.getProjectOutlayDetails().getSubsidyName().equals(""))) {
				applicationDetails.setString(16, apps.getProjectOutlayDetails().getSubsidyName());
			} else {
				applicationDetails.setString(16, null);
			}
			Log.log(5, "ApplicationDAO", "submitRsf2ApplicationDetails",
					"SubsidyName :" + apps.getProjectOutlayDetails().getSubsidyName());

			if (apps.getRehabilitation() == null) {
				applicationDetails.setString(17, "N");
			} else {
				String rehabilitationValue = apps.getRehabilitation();
				applicationDetails.setString(17, rehabilitationValue);
			}

			Log.log(5, "ApplicationDAO", "submitRsf2ApplicationDetails", "Rehabilitation :" + apps.getRehabilitation());

			applicationDetails.setDouble(18, apps.getProjectOutlayDetails().getProjectOutlay());
			Log.log(5, "ApplicationDAO", "submitRsfApplicationDetails",
					"Project Oultay :" + apps.getProjectOutlayDetails().getProjectOutlay());

			String cgpanVal = apps.getCgpanReference();
			Log.log(5, "ApplicationDAO", "submitRsf2ApplicationDetails", "Cgpan reference:" + cgpanVal);

			if ((cgpanVal != null) && (!cgpanVal.equals(""))) {
				applicationDetails.setString(19, cgpanVal);
				Log.log(5, "ApplicationDAO", "submitRsf2ApplicationDetails", "cgpan :" + cgpanVal);
			} else {
				applicationDetails.setNull(19, 12);
			}

			applicationDetails.setString(20, createdBy);
			Log.log(5, "ApplicationDAO", "submitRsf2ApplicationDetails", "User :" + createdBy);

			if ((apps.getRemarks() != null) && (!apps.getRemarks().equals(""))) {
				applicationDetails.setString(21, apps.getRemarks());
			} else {
				applicationDetails.setString(21, null);
			}
			Log.log(5, "ApplicationDAO", "submitRsf2ApplicationDetails", "Remarks :" + apps.getRemarks());

			applicationDetails.setString(22, apps.getSubSchemeName());

			Log.log(5, "ApplicationDAO", "submitRsf2ApplicationDetails", "Sub scheme Name:" + apps.getSubSchemeName());

			if ((apps.getBorrowerDetails().getSsiDetails().getMSE() != null)
					&& (!apps.getBorrowerDetails().getSsiDetails().getMSE().equals(""))) {
				applicationDetails.setString(23, apps.getBorrowerDetails().getSsiDetails().getMSE());
			} else {
				applicationDetails.setString(23, "N");
			}
			Log.log(5, "ApplicationDAO", "submitRsfApplicationDetails",
					" MSE:" + apps.getBorrowerDetails().getSsiDetails().getMSE());
			applicationDetails.setString(24, apps.getInternalRate());
			applicationDetails.setString(25, apps.getExternalRate());
			applicationDetails.executeQuery();
			int functionReturnValue = applicationDetails.getInt(1);
			Log.log(5, "ApplicationDAO", "submitRsf2ApplicationDetails", "Application Details :" + functionReturnValue);

			if (functionReturnValue == 1) {
				String error = applicationDetails.getString(24);

				applicationDetails.close();
				applicationDetails = null;

				connection.rollback();

				Log.log(2, "ApplicationDAO", "submitRsf2ApplicationDetails", "Application Detail Exception" + error);
				throw new DatabaseException(error);
			}

			apps.setAppRefNo(applicationDetails.getString(2));

			applicationDetails.close();
			applicationDetails = null;
		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "submitRsf2ApplicationDetails", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(4, "ApplicationDAO", "submitRsf2ApplicationDetails", ignore.getMessage());
			}

			throw new DatabaseException(sqlException.getMessage());
		}

		String applicationRefNo = apps.getAppRefNo();

		Log.log(4, "ApplicationDAO", "submitRsf2ApplicationDetails", "Application Reference No. :" + applicationRefNo);

		Log.log(4, "ApplicationDAO", "submitRsf2ApplicationDetails", "Exited");

		return applicationRefNo;
	}

// TEST
	public void submitPromotersDetails(Application apps, Connection connection) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "submitPromotersDetails", "Entered");
		try {
			//String coveredValue = apps.getBorrowerDetails().getPreviouslyCovered();

			//if(apps.getCpFirstName()!=null||apps.getCpFirstName()!="")
				
				//System.out.println("@@@@@@@ inside apps.getCpFirstName()");
			
				CallableStatement promotersDetails = connection
						.prepareCall("{call FUN_INSERT_PMR_DETAIL(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

				promotersDetails.registerOutParameter(1, 4);
				promotersDetails.registerOutParameter(3, 12);
				promotersDetails.registerOutParameter(15, 12);

				promotersDetails.setString(2, apps.getAppRefNo());

				promotersDetails.setString(4, apps.getCpFirstName());
				
				promotersDetails.setString(5, apps.getCpLastName());
				  
				
				if ((apps.getCpITPAN() != null)
						&& (!apps.getCpITPAN().equals(""))) {
					promotersDetails.setString(6, apps.getCpITPAN());

					Log.log(5, "ApplicationDAO", "submitPromotersDetails",
							"cp it pan:" + apps.getCpITPAN());
				} else {
					promotersDetails.setString(6, null);

					Log.log(5, "ApplicationDAO", "submitPromotersDetails",
							"cp promoter first DOB :" + apps.getCpITPAN());
				}

				promotersDetails.setString(7, apps.getCpGender());
				Log.log(5, "ApplicationDAO", "submitPromotersDetails",
						"cp gender:" + apps.getCpGender());

				
				if ((apps.getCpDOB() != null)
						&& (!apps.getCpDOB().toString().equals(""))) {
					promotersDetails.setDate(8, new java.sql.Date(apps.getCpDOB().getTime()));
					Log.log(5, "ApplicationDAO", "submitPromotersDetails", "cp dob:"
							+ new java.sql.Date(apps.getCpDOB().getTime()));
				} else {
					promotersDetails.setDate(8, null);
				}
				
				if ((apps.getSocialCategory() != null)
						&& (!apps.getSocialCategory().equals(""))) {
					promotersDetails.setString(9, apps.getSocialCategory());
				} else {
					promotersDetails.setString(9, null);
				}
				
				
				
				if ((apps.getEmail() != null)
						&& (!apps.getEmail().equals(""))) {
					promotersDetails.setString(10, apps.getEmail());
				} else {
					promotersDetails.setString(10, null);
				}
				
				
				if ((apps.getUdyogAdharNo()) != null && (!apps.getUdyogAdharNo().equals(""))) {
					promotersDetails.setString(11, apps.getUdyogAdharNo());
				} else {
					promotersDetails.setString(11, null);
				}
				
				

				if ((apps.getAdharNo() != null) && (!apps.getAdharNo().equals(""))) {
					promotersDetails.setString(12, apps.getAdharNo());
				} else {
					promotersDetails.setString(12, null);

				}

				promotersDetails.setDouble(13, apps.getPmrEquity() );
				
				promotersDetails.setDouble(14, apps.getPmrDebt() );
				

				promotersDetails.executeQuery();
				
				System.out.println("promotersDetails==="+promotersDetails);

				int promotersDetailsValue = promotersDetails.getInt(1);
				Log.log(5, "ApplicationDAO", "submitPromotersDetails",
						"promoters Details result" + promotersDetailsValue);
				System.out.println("check promotersDetailsValue>>>>>>>>>>>>>>>>>>>>" + promotersDetailsValue);
				if (promotersDetailsValue == 1) {
					String error = promotersDetails.getString(15);

					promotersDetails.close();
					promotersDetails = null;

					connection.rollback();

					Log.log(2, "ApplicationDAO", "submitPromotersDetails", "Promoter Detail Exception" + error);
					System.out.println("error>>>>>>>3567>>>>>>>>>>>>" + error);
					throw new DatabaseException(error);
				}

				apps.setBorrowerRefNo(promotersDetails.getString(3));
				
				promotersDetails.close();
				promotersDetails = null;
				
				
			//}
			System.out.println("Diksha---1");
			
			//}
			
		//	if(apps.getCpFirstName1()!=null||apps.getCpFirstName1()!="")
			if(!apps.getCpFirstName1().isEmpty()||apps.getCpFirstName1()!=null ||apps.getCpFirstName1()!="")
			{
			
					System.out.println(" apps.getCpFirstName1()==="+apps.getCpFirstName1());
			//String coveredValue = apps.getBorrowerDetails().getPreviouslyCovered();

			
				CallableStatement promotersDetails1 = connection
						.prepareCall("{call FUN_INSERT_PMR_DETAIL(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

				promotersDetails1.registerOutParameter(1, 4);
				promotersDetails1.registerOutParameter(3, 12);
				promotersDetails1.registerOutParameter(15, 12);

				promotersDetails1.setString(2, apps.getAppRefNo());

				promotersDetails1.setString(4, apps.getCpFirstName1());
				
				promotersDetails1.setString(5, apps.getCpLastName1());
				  
				
				if ((apps.getCpITPAN() != null)
						&& (!apps.getCpITPAN().equals(""))) {
					promotersDetails1.setString(6, apps.getCpITPAN1());

					Log.log(5, "ApplicationDAO", "submitpromotersDetails1",
							"cp it pan:" + apps.getCpITPAN1());
				} else {
					promotersDetails1.setString(6, null);

					Log.log(5, "ApplicationDAO", "submitpromotersDetails1",
							"cp promoter first DOB :" + apps.getCpITPAN1());
				}

				promotersDetails1.setString(7, apps.getCpGender1());
				Log.log(5, "ApplicationDAO", "submitpromotersDetails1",
						"cp gender:" + apps.getCpGender1());

				
				if ((apps.getCpDOB1() != null)
						&& (!apps.getCpDOB1().toString().equals(""))) {
					promotersDetails1.setDate(8, new java.sql.Date(apps.getCpDOB1().getTime()));
					Log.log(5, "ApplicationDAO", "submitpromotersDetails1", "cp dob:"
							+ new java.sql.Date(apps.getCpDOB1().getTime()));
				} else {
					promotersDetails1.setDate(8, null);
				}
				
				if ((apps.getSocialCategory1() != null)
						&& (!apps.getSocialCategory1().equals(""))) {
					promotersDetails1.setString(9, apps.getSocialCategory1());
				} else {
					promotersDetails1.setString(9, null);
				}
				
				
				
				if ((apps.getEmail1() != null)
						&& (!apps.getEmail1().equals(""))) {
					promotersDetails1.setString(10, apps.getEmail1());
				} else {
					promotersDetails1.setString(10, null);
				}
				
				
				if ((apps.getUdyogAdharNo()) != null && (!apps.getUdyogAdharNo().equals(""))) {
					promotersDetails1.setString(11, apps.getUdyogAdharNo());
				} else {
					promotersDetails1.setString(11, null);
				}
				
				

				if ((apps.getAdhar1() != null) && (!apps.getAdhar1().equals(""))) {
					promotersDetails1.setString(12, apps.getAdhar1());
				} else {
					promotersDetails1.setString(12, null);

				}

				promotersDetails1.setDouble(13, apps.getPmrEquity1() );
				
				promotersDetails1.setDouble(14, apps.getPmrDebt1() );
				

				promotersDetails1.executeQuery();

				int promotersDetailsValue1 = promotersDetails1.getInt(1);
				Log.log(5, "ApplicationDAO", "submitPromotersDetails",
						"promoters Details result" + promotersDetailsValue1);
				System.out.println("check promotersDetailsValue>>>>>>>>>>>>>>>>>>>>" + promotersDetailsValue1);
				if (promotersDetailsValue1 == 1) {
					String error = promotersDetails1.getString(15);

					promotersDetails1.close();
					promotersDetails1 = null;

					connection.rollback();

					Log.log(2, "ApplicationDAO", "submitPromotersDetails", "Promoter Detail Exception" + error);
					System.out.println("error>>>>>>>3567>>>>>>>>>>>>" + error);
					throw new DatabaseException(error);
				}

				apps.setBorrowerRefNo(promotersDetails1.getString(3));
				
				promotersDetails1.close();
				promotersDetails1 = null;

				
				
				//3
			}
			//if(apps.getCpFirstName2()!=null||apps.getCpFirstName2()!="")
			if(!apps.getCpFirstName2().isEmpty()||apps.getCpFirstName2()!=null ||apps.getCpFirstName2()!="")
				//if(!apps.getCpFirstName2().equalsIgnoreCase(null)||!apps.getCpFirstName2().equalsIgnoreCase(""))
			{
					System.out.println("apps.getCpFirstName2()=="+apps.getCpFirstName2());
				
				CallableStatement promotersDetails2 = connection
						.prepareCall("{call FUN_INSERT_PMR_DETAIL(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

				promotersDetails2.registerOutParameter(1, 4);
				promotersDetails2.registerOutParameter(3, 12);
				promotersDetails2.registerOutParameter(15, 12);

				promotersDetails2.setString(2, apps.getAppRefNo());

				promotersDetails2.setString(4, apps.getCpFirstName2());
				
				promotersDetails2.setString(5, apps.getCpLastName2());
				  
				
				if ((apps.getCpITPAN() != null)
						&& (!apps.getCpITPAN().equals(""))) {
					promotersDetails2.setString(6, apps.getCpITPAN2());

					Log.log(5, "ApplicationDAO", "submitpromotersDetails2",
							"cp it pan:" + apps.getCpITPAN2());
				} else {
					promotersDetails2.setString(6, null);

					Log.log(5, "ApplicationDAO", "submitpromotersDetails2",
							"cp promoter first DOB :" + apps.getCpITPAN2());
				}

				promotersDetails2.setString(7, apps.getCpGender2());
				Log.log(5, "ApplicationDAO", "submitpromotersDetails2",
						"cp gender:" + apps.getCpGender2());

				
				if ((apps.getCpDOB2() != null)
						&& (!apps.getCpDOB2().toString().equals(""))) {
					promotersDetails2.setDate(8, new java.sql.Date(apps.getCpDOB2().getTime()));
					Log.log(5, "ApplicationDAO", "submitpromotersDetails2", "cp dob:"
							+ new java.sql.Date(apps.getCpDOB2().getTime()));
				} else {
					promotersDetails2.setDate(8, null);
				}
				
				if ((apps.getSocialCategory2() != null)
						&& (!apps.getSocialCategory2().equals(""))) {
					promotersDetails2.setString(9, apps.getSocialCategory2());
				} else {
					promotersDetails2.setString(9, null);
				}
				
				
				
				if ((apps.getEmail2() != null)
						&& (!apps.getEmail2().equals(""))) {
					promotersDetails2.setString(10, apps.getEmail2());
				} else {
					promotersDetails2.setString(10, null);
				}
				
				
				if ((apps.getUdyogAdharNo()) != null && (!apps.getUdyogAdharNo().equals(""))) {
					promotersDetails2.setString(11, apps.getUdyogAdharNo());
				} else {
					promotersDetails2.setString(11, null);
				}
				
				

				if ((apps.getAdhar2() != null) && (!apps.getAdhar2().equals(""))) {
					promotersDetails2.setString(12, apps.getAdhar2());
				} else {
					promotersDetails2.setString(12, null);

				}

				promotersDetails2.setDouble(13, apps.getPmrEquity2() );
				
				promotersDetails2.setDouble(14, apps.getPmrDebt1() );
				

				promotersDetails2.executeQuery();

				int promotersDetailsValue2 = promotersDetails2.getInt(1);
				Log.log(5, "ApplicationDAO", "submitPromotersDetails",
						"promoters Details result" + promotersDetailsValue2);
				System.out.println("check promotersDetailsValue>>>>>>>>>>>>>>>>>>>>" + promotersDetailsValue2);
				if (promotersDetailsValue2 == 1) {
					String error = promotersDetails2.getString(15);

					promotersDetails2.close();
					promotersDetails2 = null;

					connection.rollback();

					Log.log(2, "ApplicationDAO", "submitPromotersDetails", "Promoter Detail Exception" + error);
					System.out.println("error>>>>>>>3567>>>>>>>>>>>>" + error);
					throw new DatabaseException(error);
				}

				apps.setBorrowerRefNo(promotersDetails2.getString(3));

				
				promotersDetails2.close();
				promotersDetails2 = null;
				
				
			//}
			System.out.println("Diksha---2");
			
			
			//4
			}
			if(!apps.getCpFirstName3().isEmpty()||apps.getCpFirstName3()!=null)
				
			//	if(!apps.getCpFirstName3().equalsIgnoreCase(null)||!apps.getCpFirstName3().equalsIgnoreCase(""))
				{
			
				System.out.println("apps.getCpFirstName3()=="+apps.getCpFirstName3());
			
			CallableStatement promotersDetails3 = connection
					.prepareCall("{call FUN_INSERT_PMR_DETAIL(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			promotersDetails3.registerOutParameter(1, 4);
			promotersDetails3.registerOutParameter(3, 12);
			promotersDetails3.registerOutParameter(15, 12);

			promotersDetails3.setString(2, apps.getAppRefNo());

			promotersDetails3.setString(4, apps.getCpFirstName3());
			
			promotersDetails3.setString(5, apps.getCpLastName3());
			  
			
			if ((apps.getCpITPAN() != null)
					&& (!apps.getCpITPAN().equals(""))) {
				promotersDetails3.setString(6, apps.getCpITPAN3());

				Log.log(5, "ApplicationDAO", "submitpromotersDetails3",
						"cp it pan:" + apps.getCpITPAN3());
			} else {
				promotersDetails3.setString(6, null);

				Log.log(5, "ApplicationDAO", "submitpromotersDetails3",
						"cp promoter first DOB :" + apps.getCpITPAN3());
			}

			promotersDetails3.setString(7, apps.getCpGender3());
			Log.log(5, "ApplicationDAO", "submitpromotersDetails3",
					"cp gender:" + apps.getCpGender3());

			
			if ((apps.getCpDOB3() != null)
					&& (!apps.getCpDOB3().toString().equals(""))) {
				promotersDetails3.setDate(8, new java.sql.Date(apps.getCpDOB3().getTime()));
				Log.log(5, "ApplicationDAO", "submitpromotersDetails3", "cp dob:"
						+ new java.sql.Date(apps.getCpDOB3().getTime()));
			} else {
				promotersDetails3.setDate(8, null);
			}
			
			if ((apps.getSocialCategory3() != null)
					&& (!apps.getSocialCategory3().equals(""))) {
				promotersDetails3.setString(9, apps.getSocialCategory3());
			} else {
				promotersDetails3.setString(9, null);
			}
			
			
			
			if ((apps.getEmail3() != null)
					&& (!apps.getEmail3().equals(""))) {
				promotersDetails3.setString(10, apps.getEmail3());
			} else {
				promotersDetails3.setString(10, null);
			}
			
			
			if ((apps.getUdyogAdharNo()) != null && (!apps.getUdyogAdharNo().equals(""))) {
				promotersDetails3.setString(11, apps.getUdyogAdharNo());
			} else {
				promotersDetails3.setString(11, null);
			}
			
			

			if ((apps.getAdhar3() != null) && (!apps.getAdhar3().equals(""))) {
				promotersDetails3.setString(12, apps.getAdhar3());
			} else {
				promotersDetails3.setString(12, null);

			}

			promotersDetails3.setDouble(13, apps.getPmrEquity3());
			
			promotersDetails3.setDouble(14, apps.getPmrDebt3() );
			

			promotersDetails3.executeQuery();

			int promotersDetailsValue3 = promotersDetails3.getInt(1);
			Log.log(5, "ApplicationDAO", "submitPromotersDetails",
					"promoters Details result" + promotersDetailsValue3);
			System.out.println("check promotersDetailsValue>>>>>>>>>>>>>>>>>>>>" + promotersDetailsValue3);
			if (promotersDetailsValue3 == 1) {
				String error = promotersDetails3.getString(15);

				promotersDetails3.close();
				promotersDetails3 = null;

				connection.rollback();

				Log.log(2, "ApplicationDAO", "submitPromotersDetails", "Promoter Detail Exception" + error);
				System.out.println("error>>>>>>>3567>>>>>>>>>>>>" + error);
				throw new DatabaseException(error);
			}

			apps.setBorrowerRefNo(promotersDetails3.getString(3));

			
			promotersDetails3.close();
			promotersDetails3 = null;
			
			
		//}
		System.out.println("Diksha---4");
		
		//5
		
			}
			if(!apps.getCpFirstName4().isEmpty()||apps.getCpFirstName4()!=null)
			{
		
		CallableStatement promotersDetails4 = connection
				.prepareCall("{call FUN_INSERT_PMR_DETAIL(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

		promotersDetails4.registerOutParameter(1, 4);
		promotersDetails4.registerOutParameter(3, 12);
		promotersDetails4.registerOutParameter(15, 12);

		promotersDetails4.setString(2, apps.getAppRefNo());

		promotersDetails4.setString(4, apps.getCpFirstName4());
		
		promotersDetails4.setString(5, apps.getCpLastName4());
		  
		
		if ((apps.getCpITPAN() != null)
				&& (!apps.getCpITPAN().equals(""))) {
			promotersDetails4.setString(6, apps.getCpITPAN4());

			Log.log(5, "ApplicationDAO", "submitpromotersDetails4",
					"cp it pan:" + apps.getCpITPAN4());
		} else {
			promotersDetails4.setString(6, null);

			Log.log(5, "ApplicationDAO", "submitpromotersDetails4",
					"cp promoter first DOB :" + apps.getCpITPAN4());
		}

		promotersDetails4.setString(7, apps.getCpGender4());
		Log.log(5, "ApplicationDAO", "submitpromotersDetails4",
				"cp gender:" + apps.getCpGender4());

		
		if ((apps.getCpDOB4() != null)
				&& (!apps.getCpDOB4().toString().equals(""))) {
			promotersDetails4.setDate(8, new java.sql.Date(apps.getCpDOB4().getTime()));
			Log.log(5, "ApplicationDAO", "submitpromotersDetails4", "cp dob:"
					+ new java.sql.Date(apps.getCpDOB4().getTime()));
		} else {
			promotersDetails4.setDate(8, null);
		}
		
		if ((apps.getSocialCategory4() != null)
				&& (!apps.getSocialCategory4().equals(""))) {
			promotersDetails4.setString(9, apps.getSocialCategory4());
		} else {
			promotersDetails4.setString(9, null);
		}
		
		
		
		if ((apps.getEmail4() != null)
				&& (!apps.getEmail4().equals(""))) {
			promotersDetails4.setString(10, apps.getEmail4());
		} else {
			promotersDetails4.setString(10, null);
		}
		
		
		if ((apps.getUdyogAdharNo()) != null && (!apps.getUdyogAdharNo().equals(""))) {
			promotersDetails4.setString(11, apps.getUdyogAdharNo());
		} else {
			promotersDetails4.setString(11, null);
		}
		
		

		if ((apps.getAdhar4() != null) && (!apps.getAdhar4().equals(""))) {
			promotersDetails4.setString(12, apps.getAdhar4());
		} else {
			promotersDetails4.setString(12, null);

		}

		promotersDetails4.setDouble(13, apps.getPmrEquity4( ));
		
		promotersDetails4.setDouble(14, apps.getPmrDebt4() );
		

		promotersDetails4.executeQuery();

		int promotersDetailsValue4 = promotersDetails4.getInt(1);
		Log.log(5, "ApplicationDAO", "submitPromotersDetails",
				"promoters Details result" + promotersDetailsValue4);
		System.out.println("check promotersDetailsValue>>>>>>>>>>>>>>>>>>>>" + promotersDetailsValue4);
		if (promotersDetailsValue4 == 1) {
			String error = promotersDetails4.getString(15);

			promotersDetails4.close();
			promotersDetails4 = null;

			connection.rollback();

			Log.log(2, "ApplicationDAO", "submitPromotersDetails", "Promoter Detail Exception" + error);
			System.out.println("error>>>>>>>3567>>>>>>>>>>>>" + error);
			throw new DatabaseException(error);
		}

		apps.setBorrowerRefNo(promotersDetails4.getString(3));

		
		promotersDetails4.close();
		promotersDetails4 = null;
		
		
	//}
	System.out.println("Diksha---5");
	
			}
			if(!apps.getCpFirstName5().isEmpty()||apps.getCpFirstName5()!=null)
			{
	//5
	
	CallableStatement promotersDetails5 = connection
			.prepareCall("{call FUN_INSERT_PMR_DETAIL(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

	promotersDetails5.registerOutParameter(1, 4);
	promotersDetails5.registerOutParameter(3, 12);
	promotersDetails5.registerOutParameter(15, 12);

	promotersDetails5.setString(2, apps.getAppRefNo());

	promotersDetails5.setString(4, apps.getCpFirstName5());
	
	promotersDetails5.setString(5, apps.getCpLastName5());
	  
	
	if ((apps.getCpITPAN() != null)
			&& (!apps.getCpITPAN().equals(""))) {
		promotersDetails5.setString(6, apps.getCpITPAN5());

		Log.log(5, "ApplicationDAO", "submitpromotersDetails5",
				"cp it pan:" + apps.getCpITPAN5());
	} else {
		promotersDetails5.setString(6, null);

		Log.log(5, "ApplicationDAO", "submitpromotersDetails5",
				"cp promoter first DOB :" + apps.getCpITPAN5());
	}

	promotersDetails5.setString(7, apps.getCpGender5());
	Log.log(5, "ApplicationDAO", "submitpromotersDetails5",
			"cp gender:" + apps.getCpGender5());

	
	if ((apps.getCpDOB5() != null)
			&& (!apps.getCpDOB5().toString().equals(""))) {
		promotersDetails5.setDate(8, new java.sql.Date(apps.getCpDOB5().getTime()));
		Log.log(5, "ApplicationDAO", "submitpromotersDetails5", "cp dob:"
				+ new java.sql.Date(apps.getCpDOB5().getTime()));
	} else {
		promotersDetails5.setDate(8, null);
	}
	
	if ((apps.getSocialCategory5() != null)
			&& (!apps.getSocialCategory5().equals(""))) {
		promotersDetails5.setString(9, apps.getSocialCategory5());
	} else {
		promotersDetails5.setString(9, null);
	}
	
	
	
	if ((apps.getEmail5() != null)
			&& (!apps.getEmail5().equals(""))) {
		promotersDetails5.setString(10, apps.getEmail5());
	} else {
		promotersDetails5.setString(10, null);
	}
	
	
	if ((apps.getUdyogAdharNo()) != null && (!apps.getUdyogAdharNo().equals(""))) {
		promotersDetails5.setString(11, apps.getUdyogAdharNo());
	} else {
		promotersDetails5.setString(11, null);
	}
	
	

	if ((apps.getAdhar5() != null) && (!apps.getAdhar5().equals(""))) {
		promotersDetails5.setString(12, apps.getAdhar5());
	} else {
		promotersDetails5.setString(12, null);

	}

	promotersDetails5.setDouble(13, apps.getPmrEquity5( ));
	
	promotersDetails5.setDouble(14, apps.getPmrDebt5() );
	

	promotersDetails5.executeQuery();

	int promotersDetailsValue5 = promotersDetails5.getInt(1);
	Log.log(5, "ApplicationDAO", "submitPromotersDetails",
			"promoters Details result" + promotersDetailsValue5);
	System.out.println("check promotersDetailsValue>>>>>>>>>>>>>>>>>>>>" + promotersDetailsValue5);
	if (promotersDetailsValue5 == 1) {
		String error = promotersDetails5.getString(15);

		promotersDetails5.close();
		promotersDetails5 = null;

		connection.rollback();

		Log.log(2, "ApplicationDAO", "submitPromotersDetails", "Promoter Detail Exception" + error);
		System.out.println("error>>>>>>>3567>>>>>>>>>>>>" + error);
		throw new DatabaseException(error);
	}

	apps.setBorrowerRefNo(promotersDetails5.getString(3));

	
	promotersDetails5.close();
	promotersDetails5 = null;
	
	
//}
System.out.println("Diksha---6");

			}
			if(!apps.getCpFirstName6().isEmpty()||apps.getCpFirstName6()!=null)
			{
//6
//5

	CallableStatement promotersDetails6 = connection
			.prepareCall("{call FUN_INSERT_PMR_DETAIL(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

	promotersDetails6.registerOutParameter(1, 4);
	promotersDetails6.registerOutParameter(3, 12);
	promotersDetails6.registerOutParameter(15, 12);

	promotersDetails6.setString(2, apps.getAppRefNo());

	promotersDetails6.setString(4, apps.getCpFirstName6());
	
	promotersDetails6.setString(5, apps.getCpLastName6());
	  
	
	if ((apps.getCpITPAN() != null)
			&& (!apps.getCpITPAN().equals(""))) {
		promotersDetails6.setString(6, apps.getCpITPAN6());

		Log.log(5, "ApplicationDAO", "submitpromotersDetails6",
				"cp it pan:" + apps.getCpITPAN6());
	} else {
		promotersDetails6.setString(6, null);

		Log.log(5, "ApplicationDAO", "submitpromotersDetails6",
				"cp promoter first DOB :" + apps.getCpITPAN6());
	}

	promotersDetails6.setString(7, apps.getCpGender6());
	Log.log(5, "ApplicationDAO", "submitpromotersDetails6",
			"cp gender:" + apps.getCpGender6());

	
	if ((apps.getCpDOB6() != null)
			&& (!apps.getCpDOB6().toString().equals(""))) {
		promotersDetails6.setDate(8, new java.sql.Date(apps.getCpDOB6().getTime()));
		Log.log(5, "ApplicationDAO", "submitpromotersDetails6", "cp dob:"
				+ new java.sql.Date(apps.getCpDOB6().getTime()));
	} else {
		promotersDetails6.setDate(8, null);
	}
	
	if ((apps.getSocialCategory6() != null)
			&& (!apps.getSocialCategory6().equals(""))) {
		promotersDetails6.setString(9, apps.getSocialCategory6());
	} else {
		promotersDetails6.setString(9, null);
	}
	
	
	
	if ((apps.getEmail6() != null)
			&& (!apps.getEmail6().equals(""))) {
		promotersDetails6.setString(10, apps.getEmail6());
	} else {
		promotersDetails6.setString(10, null);
	}
	
	
	if ((apps.getUdyogAdharNo()) != null && (!apps.getUdyogAdharNo().equals(""))) {
		promotersDetails6.setString(11, apps.getUdyogAdharNo());
	} else {
		promotersDetails6.setString(11, null);
	}
	
	

	if ((apps.getAdhar6() != null) && (!apps.getAdhar6().equals(""))) {
		promotersDetails6.setString(12, apps.getAdhar6());
	} else {
		promotersDetails6.setString(12, null);

	}

	promotersDetails6.setDouble(13, apps.getPmrEquity6( ));
	
	promotersDetails6.setDouble(14, apps.getPmrDebt6() );
	

	promotersDetails6.executeQuery();

	int promotersDetailsValue6 = promotersDetails6.getInt(1);
	Log.log(5, "ApplicationDAO", "submitPromotersDetails",
			"promoters Details result" + promotersDetailsValue6);
	System.out.println("check promotersDetailsValue>>>>>>>>>>>>>>>>>>>>" + promotersDetailsValue6);
	if (promotersDetailsValue6 == 1) {
		String error = promotersDetails6.getString(15);

		promotersDetails6.close();
		promotersDetails6 = null;

		connection.rollback();

		Log.log(2, "ApplicationDAO", "submitPromotersDetails", "Promoter Detail Exception" + error);
		System.out.println("error>>>>>>>3567>>>>>>>>>>>>" + error);
		throw new DatabaseException(error);
	}

	apps.setBorrowerRefNo(promotersDetails6.getString(3));

	
	promotersDetails6.close();
	promotersDetails6 = null;
	
	
//}
System.out.println("Diksha---7");

			}
			if(!apps.getCpFirstName7().isEmpty()||apps.getCpFirstName7()!=null)
			{

	
	//8//

				CallableStatement promotersDetails7 = connection
				.prepareCall("{call FUN_INSERT_PMR_DETAIL(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				
				promotersDetails7.registerOutParameter(1, 4);
				promotersDetails7.registerOutParameter(3, 12);
				promotersDetails7.registerOutParameter(15, 12);
				
				promotersDetails7.setString(2, apps.getAppRefNo());
				
				promotersDetails7.setString(4, apps.getCpFirstName7());
				
				promotersDetails7.setString(5, apps.getCpLastName7());
				
				
				if ((apps.getCpITPAN() != null)
				&& (!apps.getCpITPAN().equals(""))) {
				promotersDetails7.setString(6, apps.getCpITPAN7());
				
				Log.log(5, "ApplicationDAO", "submitpromotersDetails7",
					"cp it pan:" + apps.getCpITPAN7());
				} else {
				promotersDetails7.setString(6, null);
				
				Log.log(5, "ApplicationDAO", "submitpromotersDetails7",
					"cp promoter first DOB :" + apps.getCpITPAN7());
				}
				
				promotersDetails7.setString(7, apps.getCpGender7());
				Log.log(5, "ApplicationDAO", "submitpromotersDetails7",
				"cp gender:" + apps.getCpGender7());
				
				
				if ((apps.getCpDOB7() != null)
				&& (!apps.getCpDOB7().toString().equals(""))) {
				promotersDetails7.setDate(8, new java.sql.Date(apps.getCpDOB7().getTime()));
				Log.log(5, "ApplicationDAO", "submitpromotersDetails7", "cp dob:"
					+ new java.sql.Date(apps.getCpDOB7().getTime()));
				} else {
				promotersDetails7.setDate(8, null);
				}
				
				if ((apps.getSocialCategory7() != null)
				&& (!apps.getSocialCategory7().equals(""))) {
				promotersDetails7.setString(9, apps.getSocialCategory7());
				} else {
				promotersDetails7.setString(9, null);
				}
				
				
				
				if ((apps.getEmail7() != null)
				&& (!apps.getEmail7().equals(""))) {
				promotersDetails7.setString(10, apps.getEmail7());
				} else {
				promotersDetails7.setString(10, null);
				}
				
				
				if ((apps.getUdyogAdharNo()) != null && (!apps.getUdyogAdharNo().equals(""))) {
				promotersDetails7.setString(11, apps.getUdyogAdharNo());
				} else {
				promotersDetails7.setString(11, null);
				}
				
				
				
				if ((apps.getAdhar7() != null) && (!apps.getAdhar7().equals(""))) {
				promotersDetails7.setString(12, apps.getAdhar7());
				} else {
				promotersDetails7.setString(12, null);
				
				}
				
				promotersDetails7.setDouble(13, apps.getPmrEquity7( ));
				
				promotersDetails7.setDouble(14, apps.getPmrDebt7() );
				
				
				promotersDetails7.executeQuery();
				
				int promotersDetailsValue7 = promotersDetails7.getInt(1);
				Log.log(5, "ApplicationDAO", "submitPromotersDetails",
				"promoters Details result" + promotersDetailsValue7);
				System.out.println("check promotersDetailsValue>>>>>>>>>>>>>>>>>>>>" + promotersDetailsValue7);
				if (promotersDetailsValue7 == 1) {
				String error = promotersDetails7.getString(15);
				
				promotersDetails7.close();
				promotersDetails7 = null;
				
				connection.rollback();
				
				Log.log(2, "ApplicationDAO", "submitPromotersDetails", "Promoter Detail Exception" + error);
				System.out.println("error>>>>>>>3567>>>>>>>>>>>>" + error);
				throw new DatabaseException(error);
				}
				
				apps.setBorrowerRefNo(promotersDetails7.getString(3));
				
				
				promotersDetails7.close();
				promotersDetails7 = null;
				
				
				//}
				System.out.println("Diksha---7");
				
			}
			if(!apps.getCpFirstName8().isEmpty()||apps.getCpFirstName8()!=null)
			{
				//9//
				
				CallableStatement promotersDetails8 = connection
						.prepareCall("{call FUN_INSERT_PMR_DETAIL(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

						promotersDetails8.registerOutParameter(1, 4);
						promotersDetails8.registerOutParameter(3, 12);
						promotersDetails8.registerOutParameter(15, 12);

						promotersDetails8.setString(2, apps.getAppRefNo());

						promotersDetails8.setString(4, apps.getCpFirstName8());

						promotersDetails8.setString(5, apps.getCpLastName8());


						if ((apps.getCpITPAN() != null)
						&& (!apps.getCpITPAN().equals(""))) {
						promotersDetails8.setString(6, apps.getCpITPAN8());

						Log.log(5, "ApplicationDAO", "submitpromotersDetails8",
							"cp it pan:" + apps.getCpITPAN8());
						} else {
						promotersDetails8.setString(6, null);

						Log.log(5, "ApplicationDAO", "submitpromotersDetails8",
							"cp promoter first DOB :" + apps.getCpITPAN8());
						}

						promotersDetails8.setString(7, apps.getCpGender8());
						Log.log(5, "ApplicationDAO", "submitpromotersDetails8",
						"cp gender:" + apps.getCpGender8());


						if ((apps.getCpDOB8() != null)
						&& (!apps.getCpDOB8().toString().equals(""))) {
						promotersDetails8.setDate(8, new java.sql.Date(apps.getCpDOB8().getTime()));
						Log.log(5, "ApplicationDAO", "submitpromotersDetails8", "cp dob:"
							+ new java.sql.Date(apps.getCpDOB8().getTime()));
						} else {
						promotersDetails8.setDate(8, null);
						}

						if ((apps.getSocialCategory8() != null)
						&& (!apps.getSocialCategory8().equals(""))) {
						promotersDetails8.setString(9, apps.getSocialCategory8());
						} else {
						promotersDetails8.setString(9, null);
						}



						if ((apps.getEmail8() != null)
						&& (!apps.getEmail8().equals(""))) {
						promotersDetails8.setString(10, apps.getEmail8());
						} else {
						promotersDetails8.setString(10, null);
						}


						if ((apps.getUdyogAdharNo()) != null && (!apps.getUdyogAdharNo().equals(""))) {
						promotersDetails8.setString(11, apps.getUdyogAdharNo());
						} else {
						promotersDetails8.setString(11, null);
						}



						if ((apps.getAdhar8() != null) && (!apps.getAdhar8().equals(""))) {
						promotersDetails8.setString(12, apps.getAdhar8());
						} else {
						promotersDetails8.setString(12, null);

						}

						promotersDetails8.setDouble(13, apps.getPmrEquity8( ));

						promotersDetails8.setDouble(14, apps.getPmrDebt8() );


						promotersDetails8.executeQuery();

						int promotersDetailsValue8 = promotersDetails8.getInt(1);
						Log.log(5, "ApplicationDAO", "submitPromotersDetails",
						"promoters Details result" + promotersDetailsValue8);
						System.out.println("check promotersDetailsValue>>>>>>>>>>>>>>>>>>>>" + promotersDetailsValue8);
						if (promotersDetailsValue8 == 1) {
						String error = promotersDetails8.getString(15);

						promotersDetails8.close();
						promotersDetails8 = null;

						connection.rollback();

						Log.log(2, "ApplicationDAO", "submitPromotersDetails", "Promoter Detail Exception" + error);
						System.out.println("error>>>>>>>3567>>>>>>>>>>>>" + error);
						throw new DatabaseException(error);
						}

						apps.setBorrowerRefNo(promotersDetails8.getString(3));


						promotersDetails8.close();
						promotersDetails8 = null;


						//}
						System.out.println("Diksha---9");

			}
			//if(apps.getCpFirstName9()!=null||apps.getCpFirstName9()!="")
				if(!apps.getCpFirstName9().isEmpty()||apps.getCpFirstName9()!=null)
			{
						//9//
						
						CallableStatement promotersDetails9 = connection
								.prepareCall("{call FUN_INSERT_PMR_DETAIL(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

								promotersDetails9.registerOutParameter(1, 4);
								promotersDetails9.registerOutParameter(3, 12);
								promotersDetails9.registerOutParameter(15, 12);

								promotersDetails9.setString(2, apps.getAppRefNo());

								promotersDetails9.setString(4, apps.getCpFirstName9());

								promotersDetails9.setString(5, apps.getCpLastName9());


								if ((apps.getCpITPAN() != null)
								&& (!apps.getCpITPAN().equals(""))) {
								promotersDetails9.setString(6, apps.getCpITPAN9());

								Log.log(5, "ApplicationDAO", "submitpromotersDetails9",
									"cp it pan:" + apps.getCpITPAN9());
								} else {
								promotersDetails9.setString(6, null);

								Log.log(5, "ApplicationDAO", "submitpromotersDetails9",
									"cp promoter first DOB :" + apps.getCpITPAN9());
								}

								promotersDetails9.setString(7, apps.getCpGender9());
								Log.log(5, "ApplicationDAO", "submitpromotersDetails9",
								"cp gender:" + apps.getCpGender9());


								if ((apps.getCpDOB9() != null)
								&& (!apps.getCpDOB9().toString().equals(""))) {
								promotersDetails9.setDate(8, new java.sql.Date(apps.getCpDOB9().getTime()));
								Log.log(5, "ApplicationDAO", "submitpromotersDetails9", "cp dob:"
									+ new java.sql.Date(apps.getCpDOB9().getTime()));
								} else {
								promotersDetails9.setDate(8, null);
								}

								if ((apps.getSocialCategory9() != null)
								&& (!apps.getSocialCategory9().equals(""))) {
								promotersDetails9.setString(9, apps.getSocialCategory9());
								} else {
								promotersDetails9.setString(9, null);
								}



								if ((apps.getEmail9() != null)
								&& (!apps.getEmail9().equals(""))) {
								promotersDetails9.setString(10, apps.getEmail9());
								} else {
								promotersDetails9.setString(10, null);
								}


								if ((apps.getUdyogAdharNo()) != null && (!apps.getUdyogAdharNo().equals(""))) {
								promotersDetails9.setString(11, apps.getUdyogAdharNo());
								} else {
								promotersDetails9.setString(11, null);
								}



								if ((apps.getAdhar9() != null) && (!apps.getAdhar9().equals(""))) {
								promotersDetails9.setString(12, apps.getAdhar9());
								} else {
								promotersDetails9.setString(12, null);

								}

								promotersDetails9.setDouble(13, apps.getPmrEquity9( ));

								promotersDetails9.setDouble(14, apps.getPmrDebt9() );


								promotersDetails9.executeQuery();

								int promotersDetailsValue9 = promotersDetails9.getInt(1);
								Log.log(5, "ApplicationDAO", "submitPromotersDetails",
								"promoters Details result" + promotersDetailsValue9);
								System.out.println("check promotersDetailsValue>>>>>>>>>>>>>>>>>>>>" + promotersDetailsValue9);
								if (promotersDetailsValue9 == 1) {
								String error = promotersDetails9.getString(15);

								promotersDetails9.close();
								promotersDetails9 = null;

								connection.rollback();

								Log.log(2, "ApplicationDAO", "submitPromotersDetails", "Promoter Detail Exception" + error);
								System.out.println("error>>>>>>>3567>>>>>>>>>>>>" + error);
								throw new DatabaseException(error);
								}

								apps.setBorrowerRefNo(promotersDetails9.getString(3));


								promotersDetails9.close();
								promotersDetails9 = null;


								//}
								System.out.println("Diksha---10");

			}
		
		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "submitPromotersDetails", sqlException.getMessage());
			sqlException.printStackTrace();
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(4, "ApplicationDAO", "submitPromotersDetails", ignore.getMessage());
			}

			// throw new DatabaseException(sqlException.getMessage());
		}
		String pmrRefNo = apps.getBorrowerRefNo();
		
		System.out.println("pmrRefNo===="+pmrRefNo);
		
		
	}
	
	public void submitPromotersMudraDetails(Application apps, Connection connection) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "submitPromotersDetails", "Entered");
		try {
			String coveredValue = apps.getBorrowerDetails().getPreviouslyCovered();

			if (coveredValue.equals("N")) {
				CallableStatement promotersDetails = connection
						.prepareCall("{call FUNCINSERTPROMOTERDTL_MUDRAEGS(?,?,?,?,?,?,?,?,?,?)}");

				promotersDetails.registerOutParameter(1, 4);
				promotersDetails.registerOutParameter(10, 12);

				promotersDetails.setInt(2, apps.getBorrowerDetails().getSsiDetails().getBorrowerRefNo());

				Log.log(5, "ApplicationDAO", "submitPromotersDetails",
						"borrower ref no:" + apps.getBorrowerDetails().getSsiDetails().getBorrowerRefNo());
				promotersDetails.setString(3, apps.getBorrowerDetails().getSsiDetails().getCpTitle());

				Log.log(5, "ApplicationDAO", "submitPromotersDetails",
						"cp title:" + apps.getBorrowerDetails().getSsiDetails().getCpTitle());
				/*
				 * promotersDetails.setString(4, apps.getBorrowerDetails()
				 * .getSsiDetails().getCpFirstName());
				 * 
				 * Log.log(5, "ApplicationDAO", "submitPromotersDetails", "cp forst name:" +
				 * apps.getBorrowerDetails().getSsiDetails() .getCpFirstName());
				 * 
				 * if ((apps.getBorrowerDetails().getSsiDetails() .getCpMiddleName() != null) &&
				 * (!apps.getBorrowerDetails().getSsiDetails() .getCpMiddleName().equals(""))) {
				 * promotersDetails.setString(5, apps.getBorrowerDetails()
				 * .getSsiDetails().getCpMiddleName());
				 * 
				 * Log.log(5, "ApplicationDAO", "submitPromotersDetails", "cp middle name:" +
				 * apps.getBorrowerDetails().getSsiDetails() .getCpMiddleName()); } else {
				 * promotersDetails.setString(5, null); }
				 * 
				 * promotersDetails.setString(6, apps.getBorrowerDetails()
				 * .getSsiDetails().getCpLastName());
				 * 
				 * Log.log(5, "ApplicationDAO", "submitPromotersDetails", "cp last name:" +
				 * apps.getBorrowerDetails().getSsiDetails() .getCpLastName());
				 */
				if ((apps.getBorrowerDetails().getSsiDetails().getCpITPAN() != null)
						&& (!apps.getBorrowerDetails().getSsiDetails().getCpITPAN().equals(""))) {
					promotersDetails.setString(4, apps.getBorrowerDetails().getSsiDetails().getCpITPAN());

					Log.log(5, "ApplicationDAO", "submitPromotersDetails",
							"cp it pan:" + apps.getBorrowerDetails().getSsiDetails().getCpITPAN());
				} else {
					promotersDetails.setString(4, null);

					Log.log(5, "ApplicationDAO", "submitPromotersDetails",
							"cp promoter first DOB :" + apps.getBorrowerDetails().getSsiDetails().getCpITPAN());
				}

				promotersDetails.setString(5, apps.getBorrowerDetails().getSsiDetails().getCpGender());
				Log.log(5, "ApplicationDAO", "submitPromotersDetails",
						"cp gender:" + apps.getBorrowerDetails().getSsiDetails().getCpGender());

				/*
				 * if ((apps.getBorrowerDetails().getSsiDetails().getCpDOB() != null) &&
				 * (!apps.getBorrowerDetails().getSsiDetails()
				 * .getCpDOB().toString().equals(""))) { promotersDetails.setDate(9, new
				 * java.sql.Date(apps .getBorrowerDetails().getSsiDetails().getCpDOB()
				 * .getTime())); Log.log(5, "ApplicationDAO", "submitPromotersDetails",
				 * "cp dob:" + new java.sql.Date(apps .getBorrowerDetails()
				 * .getSsiDetails().getCpDOB() .getTime())); } else {
				 * promotersDetails.setDate(9, null); }
				 * 
				 * if ((apps.getBorrowerDetails().getSsiDetails().getCpLegalID() != null) &&
				 * (!apps.getBorrowerDetails().getSsiDetails() .getCpLegalID().equals(""))) {
				 * promotersDetails.setString(10, apps.getBorrowerDetails()
				 * .getSsiDetails().getCpLegalID()); } else { promotersDetails.setString(10,
				 * null); }
				 * 
				 * if ((apps.getBorrowerDetails().getSsiDetails() .getCpLegalIdValue() != null)
				 * && (!apps.getBorrowerDetails().getSsiDetails()
				 * .getCpLegalIdValue().equals(""))) { promotersDetails.setString(11,
				 * apps.getBorrowerDetails() .getSsiDetails().getCpLegalIdValue()); } else {
				 * promotersDetails.setString(11, null); }
				 * 
				 * if ((apps.getBorrowerDetails().getSsiDetails().getFirstName() != null) &&
				 * (!apps.getBorrowerDetails().getSsiDetails() .getFirstName().equals(""))) {
				 * promotersDetails.setString(12, apps.getBorrowerDetails()
				 * .getSsiDetails().getFirstName()); } else { promotersDetails.setString(12,
				 * null); }
				 * 
				 * if ((apps.getBorrowerDetails().getSsiDetails().getFirstDOB() != null) &&
				 * (!apps.getBorrowerDetails().getSsiDetails()
				 * .getFirstDOB().toString().equals(""))) { promotersDetails.setDate(13, new
				 * java.sql.Date(apps .getBorrowerDetails().getSsiDetails().getFirstDOB()
				 * .getTime())); Log.log(5, "ApplicationDAO", "submitPromotersDetails",
				 * "cp promoter first DOB :" + new java.sql.Date(apps .getBorrowerDetails()
				 * .getSsiDetails().getFirstDOB() .getTime())); } else {
				 * promotersDetails.setDate(13, null); }
				 * 
				 * if ((apps.getBorrowerDetails().getSsiDetails().getFirstItpan() != null) &&
				 * (!apps.getBorrowerDetails().getSsiDetails() .getFirstItpan().equals(""))) {
				 * promotersDetails.setString(14, apps.getBorrowerDetails()
				 * .getSsiDetails().getFirstItpan()); } else { promotersDetails.setString(14,
				 * null); }
				 * 
				 * if ((apps.getBorrowerDetails().getSsiDetails().getSecondName() != null) &&
				 * (!apps.getBorrowerDetails().getSsiDetails() .getSecondName().equals(""))) {
				 * promotersDetails.setString(15, apps.getBorrowerDetails()
				 * .getSsiDetails().getSecondName()); } else { promotersDetails.setString(15,
				 * null); }
				 * 
				 * if ((apps.getBorrowerDetails().getSsiDetails().getSecondDOB() != null) &&
				 * (!apps.getBorrowerDetails().getSsiDetails()
				 * .getSecondDOB().toString().equals(""))) { promotersDetails.setDate(16, new
				 * java.sql.Date(apps .getBorrowerDetails().getSsiDetails()
				 * .getSecondDOB().getTime())); } else { promotersDetails.setDate(16, null); }
				 * 
				 * if ((apps.getBorrowerDetails().getSsiDetails().getSecondItpan() != null) &&
				 * (!apps.getBorrowerDetails().getSsiDetails() .getSecondItpan().equals(""))) {
				 * promotersDetails.setString(17, apps.getBorrowerDetails()
				 * .getSsiDetails().getSecondItpan()); } else { promotersDetails.setString(17,
				 * null); }
				 * 
				 * if ((apps.getBorrowerDetails().getSsiDetails().getThirdName() != null) &&
				 * (!apps.getBorrowerDetails().getSsiDetails() .getThirdName().equals(""))) {
				 * promotersDetails.setString(18, apps.getBorrowerDetails()
				 * .getSsiDetails().getThirdName()); } else { promotersDetails.setString(18,
				 * null); }
				 * 
				 * if ((apps.getBorrowerDetails().getSsiDetails().getThirdDOB() != null) &&
				 * (!apps.getBorrowerDetails().getSsiDetails()
				 * .getThirdDOB().toString().equals(""))) { promotersDetails.setDate(19, new
				 * java.sql.Date(apps .getBorrowerDetails().getSsiDetails().getThirdDOB()
				 * .getTime())); } else { promotersDetails.setDate(19, null); }
				 * 
				 * if ((apps.getBorrowerDetails().getSsiDetails().getThirdItpan() != null) &&
				 * (!apps.getBorrowerDetails().getSsiDetails() .getThirdItpan().equals(""))) {
				 * promotersDetails.setString(20, apps.getBorrowerDetails()
				 * .getSsiDetails().getThirdItpan()); } else { promotersDetails.setString(20,
				 * null); }
				 * 
				 * if ((apps.getBorrowerDetails().getSsiDetails() .getSocialCategory() != null)
				 * && (!apps.getBorrowerDetails().getSsiDetails()
				 * .getSocialCategory().equals(""))) { promotersDetails.setString(21,
				 * apps.getBorrowerDetails() .getSsiDetails().getSocialCategory()); } else {
				 * promotersDetails.setString(21, null); } if
				 * ((apps.getBorrowerDetails().getSsiDetails().getReligion() != null) &&
				 * (!apps.getBorrowerDetails().getSsiDetails() .getReligion().equals(""))) {
				 * promotersDetails.setString(22, apps.getBorrowerDetails()
				 * .getSsiDetails().getReligion()); } else { promotersDetails.setString(22,
				 * null); }
				 * 
				 * if ((apps.getBorrowerDetails().getSsiDetails() .getPhysicallyHandicapped() !=
				 * null) && (!apps.getBorrowerDetails().getSsiDetails()
				 * .getPhysicallyHandicapped().equals(""))) { promotersDetails.setString(23,
				 * apps.getBorrowerDetails() .getSsiDetails().getPhysicallyHandicapped()); }
				 * else { promotersDetails.setString(23, null); }
				 */

				if ((apps.getUdyogAdharNo()) != null && (!apps.getUdyogAdharNo().equals(""))) {
					promotersDetails.setString(6, apps.getUdyogAdharNo());
				} else {
					promotersDetails.setString(6, null);
				}
				if ((apps.getBankAcNo() != null) && (!apps.getBankAcNo().equals(""))) {
					promotersDetails.setString(7, apps.getBankAcNo());
				} else {
					promotersDetails.setString(7, null);

				}

				if ((apps.getAdharNo() != null) && (!apps.getAdharNo().equals(""))) {
					promotersDetails.setString(8, apps.getAdharNo());
				} else {
					promotersDetails.setString(8, null);

				}

				if ((apps.getBorrowergstNo() != null) && (!apps.getBorrowergstNo().equals(""))) {
					promotersDetails.setString(9, apps.getBorrowergstNo());
				} else {
					promotersDetails.setString(9, null);

				}

				promotersDetails.executeQuery();

				int promotersDetailsValue = promotersDetails.getInt(1);
				Log.log(5, "ApplicationDAO", "submitPromotersDetails",
						"promoters Details result" + promotersDetailsValue);
				System.out.println("check promotersDetailsValue>>>>>>>>>>>>>>>>>>>>" + promotersDetailsValue);
				if (promotersDetailsValue == 1) {
					String error = promotersDetails.getString(10);

					promotersDetails.close();
					promotersDetails = null;

					connection.rollback();

					Log.log(2, "ApplicationDAO", "submitPromotersDetails", "Promoter Detail Exception" + error);
					System.out.println("error>>>>>>>3567>>>>>>>>>>>>" + error);
					throw new DatabaseException(error);
				}

				promotersDetails.close();
				promotersDetails = null;
			}
			System.out.println("Diksha---1");
		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "submitPromotersDetails", sqlException.getMessage());
			sqlException.printStackTrace();
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(4, "ApplicationDAO", "submitPromotersDetails", ignore.getMessage());
			}

			// throw new DatabaseException(sqlException.getMessage());
		}
	}

	public void submitGuarantorSecurityDetails(Application apps, Connection connection) throws DatabaseException {
		try {
			if ((apps.getProjectOutlayDetails().getGuarantorsName1() != null)
					&& (!apps.getProjectOutlayDetails().getGuarantorsName1().equals(""))) {
				CallableStatement guarantorsDetails1 = connection
						.prepareCall("{?=call funcInsertPersonalGuar(?,?,?,?)}");

				guarantorsDetails1.registerOutParameter(1, 4);
				guarantorsDetails1.registerOutParameter(5, 12);

				guarantorsDetails1.setString(2, apps.getAppRefNo());

				guarantorsDetails1.setString(3, apps.getProjectOutlayDetails().getGuarantorsName1());

				guarantorsDetails1.setDouble(4, apps.getProjectOutlayDetails().getGuarantorsNetWorth1());

				guarantorsDetails1.executeQuery();

				int guarantorsDetailsValue1 = guarantorsDetails1.getInt(1);
				Log.log(5, "ApplicationDAO", "submitPromotersDetails",
						"Guarantors Details1 result :" + guarantorsDetailsValue1);

				if (guarantorsDetailsValue1 == 1) {
					String error = guarantorsDetails1.getString(5);

					guarantorsDetails1.close();
					guarantorsDetails1 = null;

					connection.rollback();

					Log.log(2, "ApplicationDAO", "submitPromotersDetails", "Guarantor Detail Exception" + error);
					// System.out.println("submitApplication 6 error="+error);
					throw new DatabaseException(error);
				}
				guarantorsDetails1.close();
				guarantorsDetails1 = null;
			}

			if ((apps.getProjectOutlayDetails().getGuarantorsName2() != null)
					&& (!apps.getProjectOutlayDetails().getGuarantorsName2().equals(""))) {
				CallableStatement guarantorsDetails2 = connection
						.prepareCall("{?=call funcInsertPersonalGuar(?,?,?,?)}");

				guarantorsDetails2.registerOutParameter(1, 4);
				guarantorsDetails2.registerOutParameter(5, 12);

				guarantorsDetails2.setString(2, apps.getAppRefNo());

				guarantorsDetails2.setString(3, apps.getProjectOutlayDetails().getGuarantorsName2());

				guarantorsDetails2.setDouble(4, apps.getProjectOutlayDetails().getGuarantorsNetWorth2());

				guarantorsDetails2.executeQuery();

				int guarantorsDetailsValue2 = guarantorsDetails2.getInt(1);
				Log.log(5, "ApplicationDAO", "submitPromotersDetails",
						"Guarantors Details result :" + guarantorsDetailsValue2);

				if (guarantorsDetailsValue2 == 1) {
					String error = guarantorsDetails2.getString(5);

					guarantorsDetails2.close();
					guarantorsDetails2 = null;

					connection.rollback();

					Log.log(2, "ApplicationDAO", "submitPromotersDetails", "Guarantor2 Detail Exception" + error);
					// System.out.println("submitApplication 7 error="+error);
					throw new DatabaseException(error);
				}

				guarantorsDetails2.close();
				guarantorsDetails2 = null;
			}

			if ((apps.getProjectOutlayDetails().getGuarantorsName3() != null)
					&& (!apps.getProjectOutlayDetails().getGuarantorsName3().equals(""))) {
				CallableStatement guarantorsDetails3 = connection
						.prepareCall("{?=call funcInsertPersonalGuar(?,?,?,?)}");

				guarantorsDetails3.registerOutParameter(1, 4);
				guarantorsDetails3.registerOutParameter(5, 12);

				guarantorsDetails3.setString(2, apps.getAppRefNo());

				guarantorsDetails3.setString(3, apps.getProjectOutlayDetails().getGuarantorsName3());

				guarantorsDetails3.setDouble(4, apps.getProjectOutlayDetails().getGuarantorsNetWorth3());

				guarantorsDetails3.executeQuery();

				int guarantorsDetailsValue3 = guarantorsDetails3.getInt(1);
				Log.log(5, "ApplicationDAO", "submitPromotersDetails",
						"Guarantors Details3 result :" + guarantorsDetailsValue3);

				if (guarantorsDetailsValue3 == 1) {
					String error = guarantorsDetails3.getString(5);

					guarantorsDetails3.close();
					guarantorsDetails3 = null;

					connection.rollback();

					Log.log(2, "ApplicationDAO", "submitPromotersDetails", "Guarantor3 Detail Exception" + error);
					// System.out.println("submitApplication 8 error="+error);
					throw new DatabaseException(error);
				}
				guarantorsDetails3.close();
				guarantorsDetails3 = null;
			}

			if ((apps.getProjectOutlayDetails().getGuarantorsName4() != null)
					&& (!apps.getProjectOutlayDetails().getGuarantorsName4().equals(""))) {
				CallableStatement guarantorsDetails4 = connection
						.prepareCall("{?=call funcInsertPersonalGuar(?,?,?,?)}");

				guarantorsDetails4.registerOutParameter(1, 4);
				guarantorsDetails4.registerOutParameter(5, 12);

				guarantorsDetails4.setString(2, apps.getAppRefNo());

				guarantorsDetails4.setString(3, apps.getProjectOutlayDetails().getGuarantorsName4());

				guarantorsDetails4.setDouble(4, apps.getProjectOutlayDetails().getGuarantorsNetWorth4());

				guarantorsDetails4.executeQuery();

				int guarantorsDetailsValue4 = guarantorsDetails4.getInt(1);
				Log.log(5, "ApplicationDAO", "submitPromotersDetails",
						"Guarantors Details4 result :" + guarantorsDetailsValue4);

				if (guarantorsDetailsValue4 == 1) {
					String error = guarantorsDetails4.getString(5);

					guarantorsDetails4.close();
					guarantorsDetails4 = null;

					connection.rollback();
					// System.out.println("submitApplication 9 error="+error);
					Log.log(2, "ApplicationDAO", "submitPromotersDetails", "Guarantor4 Detail Exception" + error);
					throw new DatabaseException(error);
				}
				guarantorsDetails4.close();
				guarantorsDetails4 = null;
			}

			if ((apps.getProjectOutlayDetails().getPrimarySecurityDetails().getLandParticulars() != null)
					&& (!apps.getProjectOutlayDetails().getPrimarySecurityDetails().getLandParticulars().equals(""))) {
				CallableStatement psLandDetails = connection
						.prepareCall("{?=call funcInsertPrimarySecurity(?,?,?,?,?)}");

				psLandDetails.registerOutParameter(1, 4);
				psLandDetails.registerOutParameter(6, 12);

				psLandDetails.setString(2, apps.getAppRefNo());
				psLandDetails.setString(3, "Land");

				psLandDetails.setString(4,
						apps.getProjectOutlayDetails().getPrimarySecurityDetails().getLandParticulars());

				psLandDetails.setDouble(5, apps.getProjectOutlayDetails().getPrimarySecurityDetails().getLandValue());

				psLandDetails.executeQuery();

				int psLandDetailsValue = psLandDetails.getInt(1);
				Log.log(5, "ApplicationDAO", "submitPromotersDetails", "Land Details1 result :" + psLandDetailsValue);

				if (psLandDetailsValue == 1) {
					String error = psLandDetails.getString(5);

					psLandDetails.close();
					psLandDetails = null;

					connection.rollback();
					// System.out.println("submitApplication 10 error="+error);
					Log.log(2, "ApplicationDAO", "submitPromotersDetails", "Land Detail Exception" + error);
					throw new DatabaseException(error);
				}
				psLandDetails.close();
				psLandDetails = null;
			}

			if ((apps.getProjectOutlayDetails().getPrimarySecurityDetails().getBldgParticulars() != null)
					&& (!apps.getProjectOutlayDetails().getPrimarySecurityDetails().getBldgParticulars().equals(""))) {
				CallableStatement psBldgDetails = connection
						.prepareCall("{?=call funcInsertPrimarySecurity(?,?,?,?,?)}");

				psBldgDetails.registerOutParameter(1, 4);
				psBldgDetails.registerOutParameter(6, 12);

				psBldgDetails.setString(2, apps.getAppRefNo());
				psBldgDetails.setString(3, "Building");

				psBldgDetails.setString(4,
						apps.getProjectOutlayDetails().getPrimarySecurityDetails().getBldgParticulars());

				psBldgDetails.setDouble(5, apps.getProjectOutlayDetails().getPrimarySecurityDetails().getBldgValue());

				psBldgDetails.executeQuery();

				int psBldgDetailsValue = psBldgDetails.getInt(1);
				Log.log(5, "ApplicationDAO", "submitPromotersDetails",
						"Building Details1 result :" + psBldgDetailsValue);

				if (psBldgDetailsValue == 1) {
					String error = psBldgDetails.getString(6);

					psBldgDetails.close();
					psBldgDetails = null;

					connection.rollback();

					Log.log(2, "ApplicationDAO", "submitPromotersDetails", "Bldg Detail Exception" + error);
					// System.out.println("submitApplication 11 error="+error);
					throw new DatabaseException(error);
				}
				psBldgDetails.close();
				psBldgDetails = null;
			}

			if ((apps.getProjectOutlayDetails().getPrimarySecurityDetails().getMachineParticulars() != null) && (!apps
					.getProjectOutlayDetails().getPrimarySecurityDetails().getMachineParticulars().equals(""))) {
				CallableStatement psMachineDetails = connection
						.prepareCall("{?=call funcInsertPrimarySecurity(?,?,?,?,?)}");

				psMachineDetails.registerOutParameter(1, 4);
				psMachineDetails.registerOutParameter(6, 12);

				psMachineDetails.setString(2, apps.getAppRefNo());
				psMachineDetails.setString(3, "Machinery");

				psMachineDetails.setString(4,
						apps.getProjectOutlayDetails().getPrimarySecurityDetails().getMachineParticulars());

				psMachineDetails.setDouble(5,
						apps.getProjectOutlayDetails().getPrimarySecurityDetails().getMachineValue());

				psMachineDetails.executeQuery();

				int psMachineDetailsValue = psMachineDetails.getInt(1);
				Log.log(5, "ApplicationDAO", "submitPromotersDetails",
						"Machine Details result :" + psMachineDetailsValue);

				if (psMachineDetailsValue == 1) {
					String error = psMachineDetails.getString(6);

					psMachineDetails.close();
					psMachineDetails = null;

					connection.rollback();

					Log.log(2, "ApplicationDAO", "submitPromotersDetails", "Machine Detail Exception" + error);
					// System.out.println("submitApplication 12 error="+error);
					throw new DatabaseException(error);
				}
				psMachineDetails.close();
				psMachineDetails = null;
			}

			if ((apps.getProjectOutlayDetails().getPrimarySecurityDetails().getAssetsParticulars() != null) && (!apps
					.getProjectOutlayDetails().getPrimarySecurityDetails().getAssetsParticulars().equals(""))) {
				CallableStatement psAssetsDetails = connection
						.prepareCall("{?=call funcInsertPrimarySecurity(?,?,?,?,?)}");

				psAssetsDetails.registerOutParameter(1, 4);
				psAssetsDetails.registerOutParameter(6, 12);

				psAssetsDetails.setString(2, apps.getAppRefNo());
				psAssetsDetails.setString(3, "Fixed Assets");

				psAssetsDetails.setString(4,
						apps.getProjectOutlayDetails().getPrimarySecurityDetails().getAssetsParticulars());

				psAssetsDetails.setDouble(5,
						apps.getProjectOutlayDetails().getPrimarySecurityDetails().getAssetsValue());

				psAssetsDetails.executeQuery();

				int psAssetsDetailsValue = psAssetsDetails.getInt(1);
				Log.log(5, "ApplicationDAO", "submitPromotersDetails",
						"Assets Details result :" + psAssetsDetailsValue);

				if (psAssetsDetailsValue == 1) {
					String error = psAssetsDetails.getString(6);

					psAssetsDetails.close();
					psAssetsDetails = null;

					connection.rollback();

					Log.log(2, "ApplicationDAO", "submitPromotersDetails", "Assets Detail Exception" + error);
					// System.out.println("submitApplication 13 error="+error);
					throw new DatabaseException(error);
				}
				psAssetsDetails.close();
				psAssetsDetails = null;
			}

			if ((apps.getProjectOutlayDetails().getPrimarySecurityDetails().getCurrentAssetsParticulars() != null)
					&& (!apps.getProjectOutlayDetails().getPrimarySecurityDetails().getCurrentAssetsParticulars()
							.equals(""))) {
				CallableStatement psCurrentAssetsDetails = connection
						.prepareCall("{?=call funcInsertPrimarySecurity(?,?,?,?,?)}");

				psCurrentAssetsDetails.registerOutParameter(1, 4);
				psCurrentAssetsDetails.registerOutParameter(6, 12);

				psCurrentAssetsDetails.setString(2, apps.getAppRefNo());
				psCurrentAssetsDetails.setString(3, "Current Assets");

				psCurrentAssetsDetails.setString(4,
						apps.getProjectOutlayDetails().getPrimarySecurityDetails().getCurrentAssetsParticulars());

				psCurrentAssetsDetails.setDouble(5,
						apps.getProjectOutlayDetails().getPrimarySecurityDetails().getCurrentAssetsValue());

				psCurrentAssetsDetails.executeQuery();

				int psCurrentAssetsDetailsValue = psCurrentAssetsDetails.getInt(1);
				Log.log(5, "ApplicationDAO", "submitPromotersDetails",
						"Current Assets Details result :" + psCurrentAssetsDetailsValue);

				if (psCurrentAssetsDetailsValue == 1) {
					String error = psCurrentAssetsDetails.getString(6);

					psCurrentAssetsDetails.close();
					psCurrentAssetsDetails = null;

					connection.rollback();

					Log.log(2, "ApplicationDAO", "submitPromotersDetails", "Current Assets Detail Exception" + error);
					// System.out.println("submitApplication 14 error="+error);
					throw new DatabaseException(error);
				}
				psCurrentAssetsDetails.close();
				psCurrentAssetsDetails = null;
			}

			if ((apps.getProjectOutlayDetails().getPrimarySecurityDetails().getOthersParticulars() != null) && (!apps
					.getProjectOutlayDetails().getPrimarySecurityDetails().getOthersParticulars().equals(""))) {
				CallableStatement psOthersDetails = connection
						.prepareCall("{?=call funcInsertPrimarySecurity(?,?,?,?,?)}");

				psOthersDetails.registerOutParameter(1, 4);
				psOthersDetails.registerOutParameter(6, 12);

				psOthersDetails.setString(2, apps.getAppRefNo());
				psOthersDetails.setString(3, "Others");

				psOthersDetails.setString(4,
						apps.getProjectOutlayDetails().getPrimarySecurityDetails().getOthersParticulars());

				psOthersDetails.setDouble(5,
						apps.getProjectOutlayDetails().getPrimarySecurityDetails().getOthersValue());

				psOthersDetails.executeQuery();

				int psOthersDetailsValue = psOthersDetails.getInt(1);
				Log.log(5, "ApplicationDAO", "submitPromotersDetails",
						"Others Details result :" + psOthersDetailsValue);

				if (psOthersDetailsValue == 1) {
					String error = psOthersDetails.getString(6);

					psOthersDetails.close();
					psOthersDetails = null;

					connection.rollback();

					Log.log(2, "ApplicationDAO", "submitPromotersDetails", "Other Detail Exception" + error);
					// System.out.println("submitApplication 15 error="+error);
					throw new DatabaseException(error);
				}
				psOthersDetails.close();
				psOthersDetails = null;
			}

		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "submitPromotersDetails", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(4, "ApplicationDAO", "submitPromotersDetails", ignore.getMessage());
			}
			// System.out.println("submitApplication 16 error="+sqlException.getMessage());
			throw new DatabaseException(sqlException.getMessage());
		}

		Log.log(4, "ApplicationDAO", "submitPromotersDetails", "Exited");
	}

	public void submitTermCreditDetails(Application apps, String createdBy, Connection connection)
			throws DatabaseException {
		// System.out.println("submitTermCreditDetails called");
		Log.log(4, "ApplicationDAO", "submitTermCreditDetails", "Entered");
		String appLoanType = apps.getLoanType();
		System.out.println("3814===" + appLoanType);
		try {
			if ((appLoanType.equals("TC")) || (appLoanType.equals("CC")) || (appLoanType.equals("BO"))) {

				// System.out.println("submitTermCreditDetails appLoanType "+appLoanType);
				CallableStatement termLoanDetails = connection
						.prepareCall("{call funcInsertTermLoanEGS(?,?,?,?,?,?,?,?,?)}");

				termLoanDetails.registerOutParameter(1, 4);
				termLoanDetails.registerOutParameter(9, 12);

				termLoanDetails.setString(2, apps.getAppRefNo());
				Log.log(5, "ApplicationDAO", "submitTermCreditDetails", "app ref no :" + apps.getAppRefNo());

				/*
				 * termLoanDetails.setDouble(3, apps.getProjectOutlayDetails()
				 * .getTermCreditSanctioned()); Log.log(5, "ApplicationDAO",
				 * "submitTermCreditDetails", "trm sancitoned:" + apps.getProjectOutlayDetails()
				 * .getTermCreditSanctioned());
				 */

				termLoanDetails.setDouble(3, apps.getTermLoan().getPplOS());
				Log.log(5, "ApplicationDAO", "submitTermCreditDetails", "pplos:" + apps.getTermLoan().getPplOS());

				if ((apps.getTermLoan().getAmountSanctionedDate() != null)
						&& (!apps.getTermLoan().getAmountSanctionedDate().toString().equals(""))) {
					termLoanDetails.setDate(4,
							new java.sql.Date(apps.getTermLoan().getAmountSanctionedDate().getTime()));
					Log.log(5, "ApplicationDAO", "submitTermCreditDetails", "trm sancitoned Date:"
							+ new java.sql.Date(apps.getTermLoan().getAmountSanctionedDate().getTime()));
				} else {
					termLoanDetails.setDate(4, null);
				}

				

				if (apps.getTermLoan().getCreditGuaranteed() == 0.0D) {
					termLoanDetails.setNull(5, 8);
				} else {
					termLoanDetails.setDouble(5, apps.getTermLoan().getCreditGuaranteed());
					Log.log(5, "ApplicationDAO", "submitTermCreditDetails",
							"CG:" + apps.getTermLoan().getCreditGuaranteed());
				}

				if (apps.getTermLoan().getTenure() == 0) {
					termLoanDetails.setNull(6, 4);
				} else {
					termLoanDetails.setInt(6, apps.getTermLoan().getTenure());
					Log.log(5, "ApplicationDAO", "submitTermCreditDetails", "tenire:" + apps.getTermLoan().getTenure());
				}

				
				if (apps.getTermLoan().getInterestRate() == 0.0D) {
					termLoanDetails.setNull(7, 8);
				} else {
					termLoanDetails.setDouble(7, apps.getTermLoan().getInterestRate());
					Log.log(5, "ApplicationDAO", "submitTermCreditDetails",
							"interest type:" + apps.getTermLoan().getInterestRate());
				}

			
				if (apps.getTermLoan().getRepaymentMoratorium() == 0) {
					termLoanDetails.setNull(8, 4);
				} else {
					termLoanDetails.setInt(8, apps.getTermLoan().getRepaymentMoratorium());
					Log.log(5, "ApplicationDAO", "submitTermCreditDetails",
							"moratorium:" + apps.getTermLoan().getRepaymentMoratorium());
				}

			
				termLoanDetails.executeQuery();
				int termLoanDetailsValue = termLoanDetails.getInt(1);
				Log.log(5, "ApplicationDAO", "submitTermCreditDetails", "TC Details result :" + termLoanDetailsValue);

				if (termLoanDetailsValue == 1) {
					String error = termLoanDetails.getString(9);

					termLoanDetails.close();
					termLoanDetails = null;

					connection.rollback();

					Log.log(2, "ApplicationDAO", "submitTermCreditDetails", "Term Credit Dtl Exception" + error);
					// System.out.println("submitApplication 17 error="+error);
					throw new DatabaseException(error);
				}

				termLoanDetails.close();
				termLoanDetails = null;

				CallableStatement repaymentDtls = connection.prepareCall("{call funcInsTRMRepayment(?,?,?,?,?,?,?,?)}");

				repaymentDtls.registerOutParameter(1, 4);
				repaymentDtls.registerOutParameter(8, 12);

				repaymentDtls.setString(2, apps.getAppRefNo());

				if (apps.getTermLoan().getRepaymentMoratorium() == 0) {
					repaymentDtls.setNull(3, 4);
				} else {
					repaymentDtls.setInt(3, apps.getTermLoan().getRepaymentMoratorium());
					Log.log(5, "ApplicationDAO", "submitTermCreditDetails",
							"moratorium:" + apps.getTermLoan().getRepaymentMoratorium());
				}
				Log.log(5, "ApplicationDAO", "submitTermCreditDetails",
						"apps.getTermLoan()).getFirstInstallmentDueDate()):"
								+ apps.getTermLoan().getFirstInstallmentDueDate());

				if ((apps.getTermLoan().getFirstInstallmentDueDate() != null)
						&& (!apps.getTermLoan().getFirstInstallmentDueDate().toString().equals(""))) {
					Log.log(5, "ApplicationDAO", "submitTermCreditDetails1",
							"apps.getTermLoan()).getFirstInstallmentDueDate()):"
									+ apps.getTermLoan().getFirstInstallmentDueDate());

					repaymentDtls.setDate(4,
							new java.sql.Date(apps.getTermLoan().getFirstInstallmentDueDate().getTime()));
					Log.log(5, "ApplicationDAO", "submitTermCreditDetails", "install du date:"
							+ new java.sql.Date(apps.getTermLoan().getFirstInstallmentDueDate().getTime()));
				} else {
					Log.log(5, "ApplicationDAO", "submitTermCreditDetails2",
							"apps.getTermLoan()).getFirstInstallmentDueDate()):"
									+ apps.getTermLoan().getFirstInstallmentDueDate());

					repaymentDtls.setDate(4, null);
				}

				if ((apps.getTermLoan().getPeriodicity() != 1) && (apps.getTermLoan().getPeriodicity() != 2)
						&& (apps.getTermLoan().getPeriodicity() != 3)) {
					repaymentDtls.setNull(5, 4);
				} else {
					repaymentDtls.setInt(5, apps.getTermLoan().getPeriodicity());
					Log.log(5, "ApplicationDAO", "submitTermCreditDetails",
							"periodicity:" + apps.getTermLoan().getPeriodicity());
				}

				if (apps.getTermLoan().getNoOfInstallments() == 0) {
					repaymentDtls.setNull(6, 4);
				} else {
					repaymentDtls.setInt(6, apps.getTermLoan().getNoOfInstallments());
					Log.log(5, "ApplicationDAO", "submitTermCreditDetails",
							"installs:" + apps.getTermLoan().getNoOfInstallments());
				}

				repaymentDtls.setString(7, createdBy);

				repaymentDtls.executeQuery();
				int repaymentDtlsValue = repaymentDtls.getInt(1);
				Log.log(5, "ApplicationDAO", "submitTermCreditDetails", "TC Details result :" + repaymentDtlsValue);

				if (repaymentDtlsValue == 1) {
					String error = repaymentDtls.getString(8);

					repaymentDtls.close();
					repaymentDtls = null;

					connection.rollback();

					Log.log(2, "ApplicationDAO", "submitTermCreditDetails", "Repayment Dtl Exception" + error);
					// System.out.println("submitApplication 18 error="+error);
					throw new DatabaseException(error);
				}

				repaymentDtls.close();
				repaymentDtls = null;

				System.out.println("Amount disb===" + apps.getTermLoan().getAmtDisbursed());
//System.out.println("submitTermCreditDetails "+apps.getTermLoan().getAmtDisbursed());
				// if (apps.getTermLoan().getAmtDisbursed() != 0.0D) {
				Log.log(5, "ApplicationDAO", "submitTermCreditDetails",
						"Amount disb" + apps.getTermLoan().getAmtDisbursed());

				System.out.println("Amount disb===" + apps.getTermLoan().getAmtDisbursed());

				/*
				 * CallableStatement termLoanOutDetails = connection
				 * .prepareCall("{?=call funcInsertTCOutStand(?,?,?,?)}");
				 * 
				 * termLoanOutDetails.registerOutParameter(1, 4);
				 * termLoanOutDetails.registerOutParameter(5, 12);
				 * 
				 * termLoanOutDetails.setString(2, apps.getAppRefNo());
				 * 
				 * termLoanOutDetails.setDouble(3, apps.getTermLoan() .getPplOS());
				 * 
				 * termLoanOutDetails.setDate(4, new java.sql.Date(apps
				 * .getTermLoan().getPplOsAsOnDate().getTime())); //
				 * System.out.println("submitTermCreditDetails getPplOsAsOnDate "+apps.
				 * getTermLoan().getPplOsAsOnDate()); termLoanOutDetails.executeQuery(); int
				 * termLoanOutDetailsValue = termLoanOutDetails.getInt(1);
				 * 
				 * Log.log(5, "ApplicationDAO", "submitTermCreditDetails",
				 * "TCWC Details result :" + termLoanOutDetailsValue);
				 * 
				 * if (termLoanOutDetailsValue == 1) { String error =
				 * termLoanOutDetails.getString(5);
				 * 
				 * termLoanOutDetails.close(); termLoanOutDetails = null;
				 * 
				 * connection.rollback();
				 * 
				 * Log.log(2, "ApplicationDAO", "submitTermCreditDetails",
				 * "Term Credit Dtl Exception" + error); //
				 * System.out.println("submitApplication 19 error="+error); throw new
				 * DatabaseException(error); //
				 */// }

				// termLoanOutDetails.close();
				// termLoanOutDetails = null;

				CallableStatement termLoanDBRDetails = connection.prepareCall("{call funcInsertDBRDtl(?,?,?,?,?,?,?)}");

				System.out.println("Disbursement Detail====");

				termLoanDBRDetails.registerOutParameter(1, 4);
				termLoanDBRDetails.registerOutParameter(7, 12);

				termLoanDBRDetails.setString(2, apps.getAppRefNo());
				Log.log(5, "ApplicationDAO", "submitTermCreditDetails", "App ref no" + apps.getAppRefNo());

				termLoanDBRDetails.setDouble(3, apps.getTermLoan().getAmtDisbursed());
				Log.log(5, "ApplicationDAO", "submitTermCreditDetails",
						"Amount disb" + apps.getTermLoan().getAmtDisbursed());

				System.out.println("Disbursement Amount==" + apps.getTermLoan().getAmtDisbursed());
				termLoanDBRDetails.setDate(4,
						new java.sql.Date(apps.getTermLoan().getFirstDisbursementDate().getTime()));
				Log.log(5, "ApplicationDAO", "submitTermCreditDetails",
						"First DBR Date" + new java.sql.Date(apps.getTermLoan().getFirstDisbursementDate().getTime()));

				System.out
						.println("First Disbursement Date==" + apps.getTermLoan().getFirstDisbursementDate().getTime());

				if ((apps.getTermLoan().getFinalDisbursementDate() != null)
						&& (!apps.getTermLoan().getFinalDisbursementDate().toString().equals(""))) {
					termLoanDBRDetails.setDate(5,
							new java.sql.Date(apps.getTermLoan().getFinalDisbursementDate().getTime()));
				} else {
					termLoanDBRDetails.setNull(5, 91);
				}

				termLoanDBRDetails.setString(6, createdBy);

				termLoanDBRDetails.execute();

				int termLoanDBRDetailsValue = termLoanDBRDetails.getInt(1);
				Log.log(5, "ApplicationDAO", "submitTermCreditDetails",
						"TCWC Details result :" + termLoanDBRDetailsValue);

				if (termLoanDBRDetailsValue == 1) {
					String error = termLoanDBRDetails.getString(7);

					termLoanDBRDetails.close();
					termLoanDBRDetails = null;

					connection.rollback();

					Log.log(2, "ApplicationDAO", "submitTermCreditDetails", "Term Credit Dtl Exception" + error);
					// System.out.println("submitApplication 20 error="+error);
					throw new DatabaseException(error);
				}
				termLoanDBRDetails.close();
				termLoanDBRDetails = null;
			}

			// }
			/*
			 * else if (appLoanType.equals("WC")) { CallableStatement termLoanWCDetails =
			 * connection
			 * .prepareCall("{?=call funcInsertTermLoan(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}"
			 * );
			 * 
			 * termLoanWCDetails.registerOutParameter(1, 4);
			 * termLoanWCDetails.registerOutParameter(19, 12);
			 * 
			 * termLoanWCDetails.setString(2, apps.getAppRefNo()); if
			 * (apps.getProjectOutlayDetails().getTermCreditSanctioned() != 0.0D) {
			 * termLoanWCDetails.setDouble(3, apps .getProjectOutlayDetails()
			 * .getTermCreditSanctioned()); } else { termLoanWCDetails.setDouble(3, 0.0D); }
			 * 
			 * termLoanWCDetails.setNull(4, 91);
			 * 
			 * if (apps.getProjectOutlayDetails().getTcPromoterContribution() == 0.0D) {
			 * termLoanWCDetails.setNull(5, 8); } else termLoanWCDetails.setDouble(5, apps
			 * .getProjectOutlayDetails() .getTcPromoterContribution());
			 * 
			 * if (apps.getProjectOutlayDetails().getTcSubsidyOrEquity() == 0.0D) {
			 * termLoanWCDetails.setNull(6, 8); } else termLoanWCDetails.setDouble(6, apps
			 * .getProjectOutlayDetails().getTcSubsidyOrEquity());
			 * 
			 * if (apps.getProjectOutlayDetails().getTcOthers() == 0.0D) {
			 * termLoanWCDetails.setNull(7, 8); } else termLoanWCDetails.setDouble(7, apps
			 * .getProjectOutlayDetails().getTcOthers());
			 * 
			 * termLoanWCDetails.setNull(8, 8); termLoanWCDetails.setNull(9, 4);
			 * termLoanWCDetails.setNull(10, 12);
			 * 
			 * termLoanWCDetails.setNull(11, 8); termLoanWCDetails.setNull(12, 8);
			 * termLoanWCDetails.setNull(13, 8); termLoanWCDetails.setNull(14, 4);
			 * termLoanWCDetails.setNull(15, 91); termLoanWCDetails.setNull(16, 4);
			 * termLoanWCDetails.setNull(17, 4); termLoanWCDetails.setNull(18, 12);
			 * 
			 * termLoanWCDetails.executeQuery();
			 * 
			 * int termLoanWCDetailsValue = termLoanWCDetails.getInt(1); Log.log(5,
			 * "ApplicationDAO", "submitTermCreditDetails", "TCWC Details result :" +
			 * termLoanWCDetailsValue);
			 * 
			 * if (termLoanWCDetailsValue == 1) { String error =
			 * termLoanWCDetails.getString(19);
			 * 
			 * termLoanWCDetails.close(); termLoanWCDetails = null;
			 * 
			 * connection.rollback();
			 * 
			 * Log.log(2, "ApplicationDAO", "submitTermCreditDetails",
			 * "Term Credit Dtl Exception" + error); //
			 * System.out.println("submitApplication 21 error="+error); throw new
			 * DatabaseException(error); } termLoanWCDetails.close(); termLoanWCDetails =
			 * null; }
			 */

			System.out.println("Diksha---2");
		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "submitTermCreditDetails", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(4, "ApplicationDAO", "submitTermCreditDetails", ignore.getMessage());
			}
			// System.out.println("submitApplication 22 error="+sqlException.getMessage());
			throw new DatabaseException(sqlException.getMessage());
		}

		Log.log(4, "ApplicationDAO", "submitTermCreditDetails", "Exited");
	}
	
	public void submitMudraCreditDetails(Application apps, String createdBy, Connection connection)
			throws DatabaseException {
		// System.out.println("submitTermCreditDetails called");
		Log.log(4, "ApplicationDAO", "submitTermCreditDetails", "Entered");
		String appLoanType = apps.getLoanType();
		System.out.println("3814===" + appLoanType);
		try {
			if ((appLoanType.equals("TC")) || (appLoanType.equals("CC")) || (appLoanType.equals("BO"))) {

				// System.out.println("submitTermCreditDetails appLoanType "+appLoanType);
				CallableStatement termLoanDetails = connection
						.prepareCall("{call FUNCINSERTTERMLOAN_MUDRAEGS(?,?,?,?,?,?,?,?,?)}");

				termLoanDetails.registerOutParameter(1, 4);
				termLoanDetails.registerOutParameter(9, 12);

				termLoanDetails.setString(2, apps.getAppRefNo());
				Log.log(5, "ApplicationDAO", "submitTermCreditDetails", "app ref no :" + apps.getAppRefNo());

				/*
				 * termLoanDetails.setDouble(3, apps.getProjectOutlayDetails()
				 * .getTermCreditSanctioned()); Log.log(5, "ApplicationDAO",
				 * "submitTermCreditDetails", "trm sancitoned:" + apps.getProjectOutlayDetails()
				 * .getTermCreditSanctioned());
				 */

				termLoanDetails.setDouble(3, apps.getTermLoan().getPplOS());
				Log.log(5, "ApplicationDAO", "submitTermCreditDetails", "pplos:" + apps.getTermLoan().getPplOS());

				if ((apps.getTermLoan().getAmountSanctionedDate() != null)
						&& (!apps.getTermLoan().getAmountSanctionedDate().toString().equals(""))) {
					termLoanDetails.setDate(4,
							new java.sql.Date(apps.getTermLoan().getAmountSanctionedDate().getTime()));
					Log.log(5, "ApplicationDAO", "submitTermCreditDetails", "trm sancitoned Date:"
							+ new java.sql.Date(apps.getTermLoan().getAmountSanctionedDate().getTime()));
				} else {
					termLoanDetails.setDate(4, null);
				}

				/*
				 * if (apps.getProjectOutlayDetails().getTcPromoterContribution() == 0.0D) {
				 * termLoanDetails.setNull(5, 8); } else { termLoanDetails.setDouble(5,
				 * apps.getProjectOutlayDetails() .getTcPromoterContribution()); Log.log(5,
				 * "ApplicationDAO", "submitTermCreditDetails", "promoter cont:" +
				 * apps.getProjectOutlayDetails() .getTcPromoterContribution()); }
				 * 
				 * if (apps.getProjectOutlayDetails().getTcSubsidyOrEquity() == 0.0D) {
				 * termLoanDetails.setNull(6, 8); } else { termLoanDetails.setDouble(6,
				 * apps.getProjectOutlayDetails() .getTcSubsidyOrEquity()); Log.log(5,
				 * "ApplicationDAO", "submitTermCreditDetails", "subsidy:" +
				 * apps.getProjectOutlayDetails() .getTcSubsidyOrEquity()); }
				 * 
				 * if (apps.getProjectOutlayDetails().getTcOthers() == 0.0D) {
				 * termLoanDetails.setNull(7, 8); } else { termLoanDetails.setDouble(7,
				 * apps.getProjectOutlayDetails() .getTcOthers()); Log.log(5, "ApplicationDAO",
				 * "submitTermCreditDetails", "others:" + apps.getProjectOutlayDetails()
				 * .getTcOthers()); }
				 */

				if (apps.getTermLoan().getCreditGuaranteed() == 0.0D) {
					termLoanDetails.setNull(5, 8);
				} else {
					termLoanDetails.setDouble(5, apps.getTermLoan().getCreditGuaranteed());
					Log.log(5, "ApplicationDAO", "submitTermCreditDetails",
							"CG:" + apps.getTermLoan().getCreditGuaranteed());
				}

				if (apps.getTermLoan().getTenure() == 0) {
					termLoanDetails.setNull(6, 4);
				} else {
					termLoanDetails.setInt(6, apps.getTermLoan().getTenure());
					Log.log(5, "ApplicationDAO", "submitTermCreditDetails", "tenire:" + apps.getTermLoan().getTenure());
				}

				/*
				 * if ((apps.getTermLoan().getInterestType() != null) &&
				 * (!apps.getTermLoan().getInterestType().equals(""))) {
				 * termLoanDetails.setString(10, apps.getTermLoan() .getInterestType()); } else
				 * { termLoanDetails.setString(10, null); Log.log(5, "ApplicationDAO",
				 * "submitTermCreditDetails", "interest type:" +
				 * apps.getTermLoan().getInterestType()); }
				 */

				if (apps.getTermLoan().getInterestRate() == 0.0D) {
					termLoanDetails.setNull(7, 8);
				} else {
					termLoanDetails.setDouble(7, apps.getTermLoan().getInterestRate());
					Log.log(5, "ApplicationDAO", "submitTermCreditDetails",
							"interest type:" + apps.getTermLoan().getInterestRate());
				}

				/*
				 * if (apps.getTermLoan().getBenchMarkPLR() == 0.0D) {
				 * termLoanDetails.setNull(12, 8); } else { termLoanDetails.setDouble(12,
				 * apps.getTermLoan() .getBenchMarkPLR()); Log.log(5, "ApplicationDAO",
				 * "submitTermCreditDetails", "B plr:" + apps.getTermLoan().getBenchMarkPLR());
				 * } //
				 * System.out.println("submitTermCreditDetails ="+apps.getTermLoan().getPlr());
				 * if (apps.getTermLoan().getPlr() == 0.0D) { termLoanDetails.setNull(13, 8); //
				 * termLoanDetails.setNull(13, 0); } else { termLoanDetails.setDouble(13,
				 * apps.getTermLoan().getPlr()); Log.log(5, "ApplicationDAO",
				 * "submitTermCreditDetails", "B plr:" + apps.getTermLoan().getPlr()); }
				 */
				if (apps.getTermLoan().getRepaymentMoratorium() == 0) {
					termLoanDetails.setNull(8, 4);
				} else {
					termLoanDetails.setInt(8, apps.getTermLoan().getRepaymentMoratorium());
					Log.log(5, "ApplicationDAO", "submitTermCreditDetails",
							"moratorium:" + apps.getTermLoan().getRepaymentMoratorium());
				}

				/*
				 * if ((apps.getTermLoan().getFirstInstallmentDueDate() != null) &&
				 * (!apps.getTermLoan().getFirstInstallmentDueDate() .toString().equals(""))) {
				 * termLoanDetails.setDate(15, new java.sql.Date(apps
				 * .getTermLoan().getFirstInstallmentDueDate() .getTime())); Log.log(5,
				 * "ApplicationDAO", "submitTermCreditDetails", "install du date:" + new
				 * java.sql.Date(apps.getTermLoan() .getFirstInstallmentDueDate() .getTime()));
				 * } else { termLoanDetails.setDate(15, null); }
				 * 
				 * if (apps.getTermLoan().getNoOfInstallments() == 0) {
				 * termLoanDetails.setNull(16, 4); } else { termLoanDetails.setInt(16,
				 * apps.getTermLoan() .getNoOfInstallments()); Log.log(5, "ApplicationDAO",
				 * "submitTermCreditDetails", "installs:" +
				 * apps.getTermLoan().getNoOfInstallments()); }
				 * 
				 * if ((apps.getTermLoan().getPeriodicity() != 1) &&
				 * (apps.getTermLoan().getPeriodicity() != 4) &&
				 * (apps.getTermLoan().getPeriodicity() != 5) &&
				 * (apps.getTermLoan().getPeriodicity() != 2) &&
				 * (apps.getTermLoan().getPeriodicity() != 3)) { termLoanDetails.setNull(17, 4);
				 * } else { termLoanDetails.setInt(17, apps.getTermLoan() .getPeriodicity());
				 * Log.log(5, "ApplicationDAO", "submitTermCreditDetails", "periodicity:" +
				 * apps.getTermLoan().getPeriodicity()); }
				 * 
				 * if ((apps.getTermLoan().getTypeOfPLR() != null) &&
				 * (!apps.getTermLoan().getTypeOfPLR().equals(""))) {
				 * termLoanDetails.setString(18, apps.getTermLoan() .getTypeOfPLR()); } else {
				 * termLoanDetails.setNull(18, 12); //termLoanDetails.setString(18, ""); }
				 */

				termLoanDetails.executeQuery();
				int termLoanDetailsValue = termLoanDetails.getInt(1);
				Log.log(5, "ApplicationDAO", "submitTermCreditDetails", "TC Details result :" + termLoanDetailsValue);

				if (termLoanDetailsValue == 1) {
					String error = termLoanDetails.getString(9);

					termLoanDetails.close();
					termLoanDetails = null;

					connection.rollback();

					Log.log(2, "ApplicationDAO", "submitTermCreditDetails", "Term Credit Dtl Exception" + error);
					// System.out.println("submitApplication 17 error="+error);
					throw new DatabaseException(error);
				}

				termLoanDetails.close();
				termLoanDetails = null;

				CallableStatement repaymentDtls = connection.prepareCall("{call funcInsTRMRepayment(?,?,?,?,?,?,?,?)}");

				repaymentDtls.registerOutParameter(1, 4);
				repaymentDtls.registerOutParameter(8, 12);

				repaymentDtls.setString(2, apps.getAppRefNo());

				if (apps.getTermLoan().getRepaymentMoratorium() == 0) {
					repaymentDtls.setNull(3, 4);
				} else {
					repaymentDtls.setInt(3, apps.getTermLoan().getRepaymentMoratorium());
					Log.log(5, "ApplicationDAO", "submitTermCreditDetails",
							"moratorium:" + apps.getTermLoan().getRepaymentMoratorium());
				}
				Log.log(5, "ApplicationDAO", "submitTermCreditDetails",
						"apps.getTermLoan()).getFirstInstallmentDueDate()):"
								+ apps.getTermLoan().getFirstInstallmentDueDate());

				if ((apps.getTermLoan().getFirstInstallmentDueDate() != null)
						&& (!apps.getTermLoan().getFirstInstallmentDueDate().toString().equals(""))) {
					Log.log(5, "ApplicationDAO", "submitTermCreditDetails1",
							"apps.getTermLoan()).getFirstInstallmentDueDate()):"
									+ apps.getTermLoan().getFirstInstallmentDueDate());

					repaymentDtls.setDate(4,
							new java.sql.Date(apps.getTermLoan().getFirstInstallmentDueDate().getTime()));
					Log.log(5, "ApplicationDAO", "submitTermCreditDetails", "install du date:"
							+ new java.sql.Date(apps.getTermLoan().getFirstInstallmentDueDate().getTime()));
				} else {
					Log.log(5, "ApplicationDAO", "submitTermCreditDetails2",
							"apps.getTermLoan()).getFirstInstallmentDueDate()):"
									+ apps.getTermLoan().getFirstInstallmentDueDate());

					repaymentDtls.setDate(4, null);
				}

				if ((apps.getTermLoan().getPeriodicity() != 1) && (apps.getTermLoan().getPeriodicity() != 2)
						&& (apps.getTermLoan().getPeriodicity() != 3)) {
					repaymentDtls.setNull(5, 4);
				} else {
					repaymentDtls.setInt(5, apps.getTermLoan().getPeriodicity());
					Log.log(5, "ApplicationDAO", "submitTermCreditDetails",
							"periodicity:" + apps.getTermLoan().getPeriodicity());
				}

				if (apps.getTermLoan().getNoOfInstallments() == 0) {
					repaymentDtls.setNull(6, 4);
				} else {
					repaymentDtls.setInt(6, apps.getTermLoan().getNoOfInstallments());
					Log.log(5, "ApplicationDAO", "submitTermCreditDetails",
							"installs:" + apps.getTermLoan().getNoOfInstallments());
				}

				repaymentDtls.setString(7, createdBy);

				repaymentDtls.executeQuery();
				int repaymentDtlsValue = repaymentDtls.getInt(1);
				Log.log(5, "ApplicationDAO", "submitTermCreditDetails", "TC Details result :" + repaymentDtlsValue);

				if (repaymentDtlsValue == 1) {
					String error = repaymentDtls.getString(8);

					repaymentDtls.close();
					repaymentDtls = null;

					connection.rollback();

					Log.log(2, "ApplicationDAO", "submitTermCreditDetails", "Repayment Dtl Exception" + error);
					// System.out.println("submitApplication 18 error="+error);
					throw new DatabaseException(error);
				}

				repaymentDtls.close();
				repaymentDtls = null;

				System.out.println("Amount disb===" + apps.getTermLoan().getAmtDisbursed());
//System.out.println("submitTermCreditDetails "+apps.getTermLoan().getAmtDisbursed());
				// if (apps.getTermLoan().getAmtDisbursed() != 0.0D) {
				Log.log(5, "ApplicationDAO", "submitTermCreditDetails",
						"Amount disb" + apps.getTermLoan().getAmtDisbursed());

				System.out.println("Amount disb===" + apps.getTermLoan().getAmtDisbursed());

				/*
				 * CallableStatement termLoanOutDetails = connection
				 * .prepareCall("{?=call funcInsertTCOutStand(?,?,?,?)}");
				 * 
				 * termLoanOutDetails.registerOutParameter(1, 4);
				 * termLoanOutDetails.registerOutParameter(5, 12);
				 * 
				 * termLoanOutDetails.setString(2, apps.getAppRefNo());
				 * 
				 * termLoanOutDetails.setDouble(3, apps.getTermLoan() .getPplOS());
				 * 
				 * termLoanOutDetails.setDate(4, new java.sql.Date(apps
				 * .getTermLoan().getPplOsAsOnDate().getTime())); //
				 * System.out.println("submitTermCreditDetails getPplOsAsOnDate "+apps.
				 * getTermLoan().getPplOsAsOnDate()); termLoanOutDetails.executeQuery(); int
				 * termLoanOutDetailsValue = termLoanOutDetails.getInt(1);
				 * 
				 * Log.log(5, "ApplicationDAO", "submitTermCreditDetails",
				 * "TCWC Details result :" + termLoanOutDetailsValue);
				 * 
				 * if (termLoanOutDetailsValue == 1) { String error =
				 * termLoanOutDetails.getString(5);
				 * 
				 * termLoanOutDetails.close(); termLoanOutDetails = null;
				 * 
				 * connection.rollback();
				 * 
				 * Log.log(2, "ApplicationDAO", "submitTermCreditDetails",
				 * "Term Credit Dtl Exception" + error); //
				 * System.out.println("submitApplication 19 error="+error); throw new
				 * DatabaseException(error); //
				 */// }

				// termLoanOutDetails.close();
				// termLoanOutDetails = null;

				CallableStatement termLoanDBRDetails = connection.prepareCall("{call FUNCINSERTDBRDTL_MUDRAEGS(?,?,?,?,?,?,?)}");

				System.out.println("Disbursement Detail====");

				termLoanDBRDetails.registerOutParameter(1, 4);
				termLoanDBRDetails.registerOutParameter(7, 12);

				termLoanDBRDetails.setString(2, apps.getAppRefNo());
				Log.log(5, "ApplicationDAO", "submitTermCreditDetails", "App ref no" + apps.getAppRefNo());

				termLoanDBRDetails.setDouble(3, apps.getTermLoan().getAmtDisbursed());
				Log.log(5, "ApplicationDAO", "submitTermCreditDetails",
						"Amount disb" + apps.getTermLoan().getAmtDisbursed());

				System.out.println("Disbursement Amount==" + apps.getTermLoan().getAmtDisbursed());
				termLoanDBRDetails.setDate(4,
						new java.sql.Date(apps.getTermLoan().getFirstDisbursementDate().getTime()));
				Log.log(5, "ApplicationDAO", "submitTermCreditDetails",
						"First DBR Date" + new java.sql.Date(apps.getTermLoan().getFirstDisbursementDate().getTime()));

				System.out
						.println("First Disbursement Date==" + apps.getTermLoan().getFirstDisbursementDate().getTime());

				if ((apps.getTermLoan().getFinalDisbursementDate() != null)
						&& (!apps.getTermLoan().getFinalDisbursementDate().toString().equals(""))) {
					termLoanDBRDetails.setDate(5,
							new java.sql.Date(apps.getTermLoan().getFinalDisbursementDate().getTime()));
				} else {
					termLoanDBRDetails.setNull(5, 91);
				}

				termLoanDBRDetails.setString(6, createdBy);

				termLoanDBRDetails.execute();

				int termLoanDBRDetailsValue = termLoanDBRDetails.getInt(1);
				Log.log(5, "ApplicationDAO", "submitTermCreditDetails",
						"TCWC Details result :" + termLoanDBRDetailsValue);

				if (termLoanDBRDetailsValue == 1) {
					String error = termLoanDBRDetails.getString(7);

					termLoanDBRDetails.close();
					termLoanDBRDetails = null;

					connection.rollback();

					Log.log(2, "ApplicationDAO", "submitTermCreditDetails", "Term Credit Dtl Exception" + error);
					// System.out.println("submitApplication 20 error="+error);
					throw new DatabaseException(error);
				}
				termLoanDBRDetails.close();
				termLoanDBRDetails = null;
			}

			// }
			/*
			 * else if (appLoanType.equals("WC")) { CallableStatement termLoanWCDetails =
			 * connection
			 * .prepareCall("{?=call funcInsertTermLoan(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}"
			 * );
			 * 
			 * termLoanWCDetails.registerOutParameter(1, 4);
			 * termLoanWCDetails.registerOutParameter(19, 12);
			 * 
			 * termLoanWCDetails.setString(2, apps.getAppRefNo()); if
			 * (apps.getProjectOutlayDetails().getTermCreditSanctioned() != 0.0D) {
			 * termLoanWCDetails.setDouble(3, apps .getProjectOutlayDetails()
			 * .getTermCreditSanctioned()); } else { termLoanWCDetails.setDouble(3, 0.0D); }
			 * 
			 * termLoanWCDetails.setNull(4, 91);
			 * 
			 * if (apps.getProjectOutlayDetails().getTcPromoterContribution() == 0.0D) {
			 * termLoanWCDetails.setNull(5, 8); } else termLoanWCDetails.setDouble(5, apps
			 * .getProjectOutlayDetails() .getTcPromoterContribution());
			 * 
			 * if (apps.getProjectOutlayDetails().getTcSubsidyOrEquity() == 0.0D) {
			 * termLoanWCDetails.setNull(6, 8); } else termLoanWCDetails.setDouble(6, apps
			 * .getProjectOutlayDetails().getTcSubsidyOrEquity());
			 * 
			 * if (apps.getProjectOutlayDetails().getTcOthers() == 0.0D) {
			 * termLoanWCDetails.setNull(7, 8); } else termLoanWCDetails.setDouble(7, apps
			 * .getProjectOutlayDetails().getTcOthers());
			 * 
			 * termLoanWCDetails.setNull(8, 8); termLoanWCDetails.setNull(9, 4);
			 * termLoanWCDetails.setNull(10, 12);
			 * 
			 * termLoanWCDetails.setNull(11, 8); termLoanWCDetails.setNull(12, 8);
			 * termLoanWCDetails.setNull(13, 8); termLoanWCDetails.setNull(14, 4);
			 * termLoanWCDetails.setNull(15, 91); termLoanWCDetails.setNull(16, 4);
			 * termLoanWCDetails.setNull(17, 4); termLoanWCDetails.setNull(18, 12);
			 * 
			 * termLoanWCDetails.executeQuery();
			 * 
			 * int termLoanWCDetailsValue = termLoanWCDetails.getInt(1); Log.log(5,
			 * "ApplicationDAO", "submitTermCreditDetails", "TCWC Details result :" +
			 * termLoanWCDetailsValue);
			 * 
			 * if (termLoanWCDetailsValue == 1) { String error =
			 * termLoanWCDetails.getString(19);
			 * 
			 * termLoanWCDetails.close(); termLoanWCDetails = null;
			 * 
			 * connection.rollback();
			 * 
			 * Log.log(2, "ApplicationDAO", "submitTermCreditDetails",
			 * "Term Credit Dtl Exception" + error); //
			 * System.out.println("submitApplication 21 error="+error); throw new
			 * DatabaseException(error); } termLoanWCDetails.close(); termLoanWCDetails =
			 * null; }
			 */

			System.out.println("Diksha---2");
		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "submitTermCreditDetails", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(4, "ApplicationDAO", "submitTermCreditDetails", ignore.getMessage());
			}
			// System.out.println("submitApplication 22 error="+sqlException.getMessage());
			throw new DatabaseException(sqlException.getMessage());
		}

		Log.log(4, "ApplicationDAO", "submitTermCreditDetails", "Exited");
	}



	public void submitWCDetails(Application apps, String createdBy, Connection connection) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "submitWCDetails", "Entered");

		String appLoanType = apps.getLoanType();
		System.out.println("4260===" + appLoanType);
		try {
			if ((appLoanType.equals("WC")) || (appLoanType.equals("CC")) || (appLoanType.equals("BO"))) {

				// System.out.println("submitTermCreditDetails appLoanType "+appLoanType);
				CallableStatement termLoanDetails = connection
						.prepareCall("{call FUNCINSERTWORKINGCAPITALEGS(?,?,?,?,?,?,?,?,?)}");

				termLoanDetails.registerOutParameter(1, 4);
				termLoanDetails.registerOutParameter(9, 12);

				termLoanDetails.setString(2, apps.getAppRefNo());
				Log.log(5, "ApplicationDAO", "submitTermCreditDetails", "app ref no :" + apps.getAppRefNo());

				termLoanDetails.setDouble(3, apps.getProjectOutlayDetails().getTermCreditSanctioned());
				Log.log(5, "ApplicationDAO", "submitTermCreditDetails",
						"trm sancitoned:" + apps.getProjectOutlayDetails().getTermCreditSanctioned());

				if ((apps.getTermLoan().getAmountSanctionedDate() != null)
						&& (!apps.getTermLoan().getAmountSanctionedDate().toString().equals(""))) {
					termLoanDetails.setDate(4,
							new java.sql.Date(apps.getTermLoan().getAmountSanctionedDate().getTime()));
					Log.log(5, "ApplicationDAO", "submitTermCreditDetails", "trm sancitoned Date:"
							+ new java.sql.Date(apps.getTermLoan().getAmountSanctionedDate().getTime()));
				} else {
					termLoanDetails.setDate(4, null);
				}

				/*
				 * if (apps.getProjectOutlayDetails().getTcPromoterContribution() == 0.0D) {
				 * termLoanDetails.setNull(5, 8); } else { termLoanDetails.setDouble(5,
				 * apps.getProjectOutlayDetails() .getTcPromoterContribution()); Log.log(5,
				 * "ApplicationDAO", "submitTermCreditDetails", "promoter cont:" +
				 * apps.getProjectOutlayDetails() .getTcPromoterContribution()); }
				 * 
				 * if (apps.getProjectOutlayDetails().getTcSubsidyOrEquity() == 0.0D) {
				 * termLoanDetails.setNull(6, 8); } else { termLoanDetails.setDouble(6,
				 * apps.getProjectOutlayDetails() .getTcSubsidyOrEquity()); Log.log(5,
				 * "ApplicationDAO", "submitTermCreditDetails", "subsidy:" +
				 * apps.getProjectOutlayDetails() .getTcSubsidyOrEquity()); }
				 * 
				 * if (apps.getProjectOutlayDetails().getTcOthers() == 0.0D) {
				 * termLoanDetails.setNull(7, 8); } else { termLoanDetails.setDouble(7,
				 * apps.getProjectOutlayDetails() .getTcOthers()); Log.log(5, "ApplicationDAO",
				 * "submitTermCreditDetails", "others:" + apps.getProjectOutlayDetails()
				 * .getTcOthers()); }
				 */

				if (apps.getTermLoan().getCreditGuaranteed() == 0.0D) {
					termLoanDetails.setNull(5, 8);
				} else {
					termLoanDetails.setDouble(5, apps.getTermLoan().getCreditGuaranteed());
					Log.log(5, "ApplicationDAO", "submitTermCreditDetails",
							"CG:" + apps.getTermLoan().getCreditGuaranteed());
				}

				if (apps.getTermLoan().getTenure() == 0) {
					termLoanDetails.setNull(6, 4);
				} else {
					termLoanDetails.setInt(6, apps.getTermLoan().getTenure());
					Log.log(5, "ApplicationDAO", "submitTermCreditDetails", "tenire:" + apps.getTermLoan().getTenure());
				}

				/*
				 * if ((apps.getTermLoan().getInterestType() != null) &&
				 * (!apps.getTermLoan().getInterestType().equals(""))) {
				 * termLoanDetails.setString(10, apps.getTermLoan() .getInterestType()); } else
				 * { termLoanDetails.setString(10, null); Log.log(5, "ApplicationDAO",
				 * "submitTermCreditDetails", "interest type:" +
				 * apps.getTermLoan().getInterestType()); }
				 */

				if (apps.getTermLoan().getInterestRate() == 0.0D) {
					termLoanDetails.setNull(7, 8);
				} else {
					termLoanDetails.setDouble(7, apps.getTermLoan().getInterestRate());
					Log.log(5, "ApplicationDAO", "submitTermCreditDetails",
							"interest type:" + apps.getTermLoan().getInterestRate());
				}

				/*
				 * if (apps.getTermLoan().getBenchMarkPLR() == 0.0D) {
				 * termLoanDetails.setNull(12, 8); } else { termLoanDetails.setDouble(12,
				 * apps.getTermLoan() .getBenchMarkPLR()); Log.log(5, "ApplicationDAO",
				 * "submitTermCreditDetails", "B plr:" + apps.getTermLoan().getBenchMarkPLR());
				 * } //
				 * System.out.println("submitTermCreditDetails ="+apps.getTermLoan().getPlr());
				 * if (apps.getTermLoan().getPlr() == 0.0D) { termLoanDetails.setNull(13, 8); //
				 * termLoanDetails.setNull(13, 0); } else { termLoanDetails.setDouble(13,
				 * apps.getTermLoan().getPlr()); Log.log(5, "ApplicationDAO",
				 * "submitTermCreditDetails", "B plr:" + apps.getTermLoan().getPlr()); }
				 */
				if (apps.getTermLoan().getRepaymentMoratorium() == 0) {
					termLoanDetails.setNull(8, 4);
				} else {
					termLoanDetails.setInt(8, apps.getTermLoan().getRepaymentMoratorium());
					Log.log(5, "ApplicationDAO", "submitTermCreditDetails",
							"moratorium:" + apps.getTermLoan().getRepaymentMoratorium());
				}

				/*
				 * if ((apps.getTermLoan().getFirstInstallmentDueDate() != null) &&
				 * (!apps.getTermLoan().getFirstInstallmentDueDate() .toString().equals(""))) {
				 * termLoanDetails.setDate(15, new java.sql.Date(apps
				 * .getTermLoan().getFirstInstallmentDueDate() .getTime())); Log.log(5,
				 * "ApplicationDAO", "submitTermCreditDetails", "install du date:" + new
				 * java.sql.Date(apps.getTermLoan() .getFirstInstallmentDueDate() .getTime()));
				 * } else { termLoanDetails.setDate(15, null); }
				 * 
				 * if (apps.getTermLoan().getNoOfInstallments() == 0) {
				 * termLoanDetails.setNull(16, 4); } else { termLoanDetails.setInt(16,
				 * apps.getTermLoan() .getNoOfInstallments()); Log.log(5, "ApplicationDAO",
				 * "submitTermCreditDetails", "installs:" +
				 * apps.getTermLoan().getNoOfInstallments()); }
				 * 
				 * if ((apps.getTermLoan().getPeriodicity() != 1) &&
				 * (apps.getTermLoan().getPeriodicity() != 4) &&
				 * (apps.getTermLoan().getPeriodicity() != 5) &&
				 * (apps.getTermLoan().getPeriodicity() != 2) &&
				 * (apps.getTermLoan().getPeriodicity() != 3)) { termLoanDetails.setNull(17, 4);
				 * } else { termLoanDetails.setInt(17, apps.getTermLoan() .getPeriodicity());
				 * Log.log(5, "ApplicationDAO", "submitTermCreditDetails", "periodicity:" +
				 * apps.getTermLoan().getPeriodicity()); }
				 * 
				 * if ((apps.getTermLoan().getTypeOfPLR() != null) &&
				 * (!apps.getTermLoan().getTypeOfPLR().equals(""))) {
				 * termLoanDetails.setString(18, apps.getTermLoan() .getTypeOfPLR()); } else {
				 * termLoanDetails.setNull(18, 12); //termLoanDetails.setString(18, ""); }
				 */

				termLoanDetails.executeQuery();
				int termLoanDetailsValue = termLoanDetails.getInt(1);
				Log.log(5, "ApplicationDAO", "submitTermCreditDetails", "TC Details result :" + termLoanDetailsValue);

				if (termLoanDetailsValue == 1) {
					String error = termLoanDetails.getString(9);

					termLoanDetails.close();
					termLoanDetails = null;

					connection.rollback();

					Log.log(2, "ApplicationDAO", "submitTermCreditDetails", "Term Credit Dtl Exception" + error);
					// System.out.println("submitApplication 17 error="+error);
					throw new DatabaseException(error);
				}

				termLoanDetails.close();
				termLoanDetails = null;

				/*
				 * CallableStatement wcDetails = connection
				 * .prepareCall("{?=call funcInsertWorkingCapital(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}"
				 * );
				 * 
				 * wcDetails.registerOutParameter(1, 4); wcDetails.registerOutParameter(18, 12);
				 * 
				 * wcDetails.setString(2, apps.getAppRefNo()); if
				 * (apps.getProjectOutlayDetails().getWcFundBasedSanctioned() == 0.0D) {
				 * wcDetails.setNull(3, 8); } else wcDetails.setDouble(3,
				 * apps.getProjectOutlayDetails() .getWcFundBasedSanctioned());
				 * 
				 * if (apps.getProjectOutlayDetails() .getWcNonFundBasedSanctioned() == 0.0D) {
				 * wcDetails.setDouble(4, 0.0D); } else wcDetails.setDouble(4,
				 * apps.getProjectOutlayDetails() .getWcNonFundBasedSanctioned());
				 * 
				 * if (apps.getProjectOutlayDetails().getWcPromoterContribution() == 0.0D) {
				 * wcDetails.setNull(5, 8); } else wcDetails.setDouble(5,
				 * apps.getProjectOutlayDetails() .getWcPromoterContribution());
				 * 
				 * if (apps.getProjectOutlayDetails().getWcSubsidyOrEquity() == 0.0D) {
				 * wcDetails.setNull(6, 8); } else wcDetails.setDouble(6,
				 * apps.getProjectOutlayDetails() .getWcSubsidyOrEquity());
				 * 
				 * if (apps.getProjectOutlayDetails().getWcOthers() == 0.0D) {
				 * wcDetails.setNull(7, 8); } else wcDetails.setDouble(7,
				 * apps.getProjectOutlayDetails() .getWcOthers());
				 * 
				 * wcDetails .setDouble(8, apps.getWc().getLimitFundBasedInterest()); Log.log(4,
				 * "ApplicationDAO", "submitWCDetails", "Interest :" +
				 * apps.getWc().getLimitFundBasedInterest());
				 * 
				 * if (apps.getWc().getLimitNonFundBasedCommission() == 0.0D) {
				 * wcDetails.setDouble(9, 0.0D); } else { wcDetails.setDouble(9, apps.getWc()
				 * .getLimitNonFundBasedCommission()); } Log.log(4, "ApplicationDAO",
				 * "submitWCDetails", "Commission :" +
				 * apps.getWc().getLimitNonFundBasedCommission());
				 * 
				 * if ((apps.getWc().getLimitFundBasedSanctionedDate() != null) &&
				 * (!apps.getWc().getLimitFundBasedSanctionedDate() .toString().equals(""))) {
				 * wcDetails.setDate(10, new java.sql.Date(apps.getWc()
				 * .getLimitFundBasedSanctionedDate().getTime())); } else {
				 * wcDetails.setNull(10, 91); }
				 * 
				 * Log.log(4, "ApplicationDAO", "submitWCDetails",
				 * "Fund Based Sanctioned Date :" + apps.getWc()
				 * .getLimitFundBasedSanctionedDate());
				 * 
				 * if ((apps.getWc().getLimitNonFundBasedSanctionedDate() != null) &&
				 * (!apps.getWc().getLimitNonFundBasedSanctionedDate() .toString().equals("")))
				 * { wcDetails.setDate(11, new java.sql.Date(apps.getWc()
				 * .getLimitNonFundBasedSanctionedDate().getTime())); } else {
				 * wcDetails.setNull(11, 91); }
				 * 
				 * Log.log(4, "ApplicationDAO", "submitWCDetails",
				 * "Non Fund Based Sanctioned Date : " + apps.getWc()
				 * .getLimitNonFundBasedSanctionedDate());
				 * 
				 * if (apps.getWc().getCreditFundBased() == 0.0D) { wcDetails.setNull(12, 8); }
				 * else wcDetails.setDouble(12, apps.getWc().getCreditFundBased());
				 * 
				 * Log.log(4, "ApplicationDAO", "submitWCDetails", "Credit Fund Based :" +
				 * apps.getWc().getCreditFundBased());
				 * 
				 * if (apps.getWc().getCreditNonFundBased() == 0.0D) { wcDetails.setDouble(13,
				 * 0.0D); } else wcDetails.setDouble(13, apps.getWc() .getCreditNonFundBased());
				 * 
				 * Log.log(4, "ApplicationDAO", "submitWCDetails", "NonFundBased :" +
				 * apps.getWc().getCreditNonFundBased());
				 * //System.out.println("submitWCDetails ="+apps.getWc().getWcPlr()); if
				 * (apps.getWc().getWcPlr() == 0.0D) { wcDetails.setNull(14, 8); } else
				 * wcDetails.setDouble(14, apps.getWc().getWcPlr());
				 * 
				 * Log.log(4, "ApplicationDAO", "submitWCDetails", "PLR :" +
				 * apps.getWc().getWcPlr());
				 * 
				 * if ((apps.getWc().getWcTypeOfPLR() != null) &&
				 * (!apps.getWc().getWcTypeOfPLR().equals(""))) { wcDetails.setString(15,
				 * apps.getWc().getWcTypeOfPLR()); } else { wcDetails.setNull(15, 12); //
				 * wcDetails.setString(15, ""); }
				 * 
				 * if ((apps.getWc().getWcInterestType() != null) &&
				 * (!apps.getWc().getWcInterestType().equals(""))) { wcDetails.setString(16,
				 * apps.getWc().getWcInterestType()); } else { wcDetails.setNull(16, 12); }
				 * 
				 * if (apps.getWc().getWcTenure() != 0) { wcDetails.setInt(17,
				 * apps.getWc().getWcTenure()); } else { wcDetails.setNull(17, 4); }
				 * 
				 * wcDetails.executeQuery(); int wcDetailsValue = wcDetails.getInt(1);
				 * 
				 * Log.log(5, "ApplicationDAO", "submitWCDetails", "WC Details result :" +
				 * wcDetailsValue);
				 * 
				 * if (wcDetailsValue == 1) { String error = wcDetails.getString(18);
				 * 
				 * wcDetails.close(); wcDetails = null;
				 * 
				 * connection.rollback();
				 * 
				 * Log.log(2, "ApplicationDAO", "submitWCDetails",
				 * "Working Credit Dtl Exception" + error); throw new DatabaseException(error);
				 * } wcDetails.close(); wcDetails = null;
				 * 
				 * Log.log(5, "ApplicationDAO", "submitWCDetails", "Fund Based Principal :" +
				 * apps.getWc().getOsFundBasedPpl()); if (apps.getWc().getOsFundBasedPpl() !=
				 * 0.0D) { Log.log(5, "ApplicationDAO", "submitWCDetails",
				 * "Fund Based Principal is not zero");
				 */
				CallableStatement wcOutDetails = connection
						.prepareCall("{?=call funcInsertWCOutStand(?,?,?,?,?,?,?,?)}");

				wcOutDetails.registerOutParameter(1, 4);
				wcOutDetails.registerOutParameter(9, 12);

				wcOutDetails.setString(2, apps.getAppRefNo());

				if (apps.getWc().getOsFundBasedPpl() == 0.0D) {
					wcOutDetails.setNull(3, 8);
				} else
					wcOutDetails.setDouble(3, apps.getWc().getOsFundBasedPpl());

				Log.log(5, "ApplicationDAO", "submitWCDetails",
						"Fund Based Principal :" + apps.getWc().getOsFundBasedPpl());

				if (apps.getWc().getOsFundBasedInterestAmt() == 0.0D) {
					wcOutDetails.setDouble(4, 0.0D);
				} else {
					wcOutDetails.setDouble(4, apps.getWc().getOsFundBasedInterestAmt());
				}
				Log.log(5, "ApplicationDAO", "submitWCDetails",
						"Interest Fund Based :" + apps.getWc().getOsFundBasedInterestAmt());

				if ((apps.getWc().getOsFundBasedAsOnDate() != null)
						&& (!apps.getWc().getOsFundBasedAsOnDate().toString().equals(""))) {
					wcOutDetails.setDate(5, new java.sql.Date(apps.getWc().getOsFundBasedAsOnDate().getTime()));
				} else {
					wcOutDetails.setDate(5, null);
				}
				Log.log(5, "ApplicationDAO", "submitWCDetails",
						"Sanctioned Date :" + apps.getWc().getOsFundBasedAsOnDate());

				if (apps.getWc().getOsNonFundBasedPpl() == 0.0D) {
					wcOutDetails.setDouble(6, 0.0D);
				} else
					wcOutDetails.setDouble(6, apps.getWc().getOsNonFundBasedPpl());

				Log.log(5, "ApplicationDAO", "submitWCDetails",
						"NonFundBased Principal :" + apps.getWc().getOsNonFundBasedPpl());

				if (apps.getWc().getOsNonFundBasedCommissionAmt() == 0.0D) {
					wcOutDetails.setDouble(7, 0.0D);
				} else {
					wcOutDetails.setDouble(7, apps.getWc().getOsNonFundBasedCommissionAmt());
				}
				Log.log(5, "ApplicationDAO", "submitWCDetails",
						"NonFundBasedCommission :" + apps.getWc().getOsNonFundBasedCommissionAmt());

				if ((apps.getWc().getOsNonFundBasedAsOnDate() != null)
						&& (!apps.getWc().getOsNonFundBasedAsOnDate().toString().equals(""))) {
					wcOutDetails.setDate(8, new java.sql.Date(apps.getWc().getOsNonFundBasedAsOnDate().getTime()));
				} else {
					wcOutDetails.setDate(8, null);
				}

				Log.log(5, "ApplicationDAO", "submitWCDetails",
						"NonFundBased Sanctioned Date :" + apps.getWc().getOsNonFundBasedAsOnDate());

				wcOutDetails.executeQuery();
				int wcOutDetailsValue = wcOutDetails.getInt(1);

				Log.log(5, "ApplicationDAO", "submitWCDetails", "WC Details result :" + wcOutDetailsValue);

				if (wcOutDetailsValue == 1) {
					String error = wcOutDetails.getString(9);

					wcOutDetails.close();
					wcOutDetails = null;

					connection.rollback();

					Log.log(2, "ApplicationDAO", "submitWCDetails", "Working Credit Dtl Exception" + error);
					throw new DatabaseException(error);
				}
				wcOutDetails.close();
				wcOutDetails = null;
			}

			if ((appLoanType.equals("WC")) || (appLoanType.equals("BO"))) {
				CallableStatement wcDBRDetails = connection.prepareCall("{?=call funcInsertDBRDtl(?,?,?,?,?,?)}");

				wcDBRDetails.registerOutParameter(1, 4);
				wcDBRDetails.registerOutParameter(7, 12);

				wcDBRDetails.setString(2, apps.getAppRefNo());
				wcDBRDetails.setDouble(3, apps.getTermLoan().getAmtDisbursed());

				if ((apps.getTermLoan().getFirstDisbursementDate() != null)
						&& (!apps.getTermLoan().getFirstDisbursementDate().toString().equals(""))) {
					wcDBRDetails.setDate(4, new java.sql.Date(apps.getTermLoan().getFirstDisbursementDate().getTime()));
				} else {
					wcDBRDetails.setNull(4, 91);
				}

				if ((apps.getTermLoan().getFinalDisbursementDate() != null)
						&& (!apps.getTermLoan().getFinalDisbursementDate().toString().equals(""))) {
					wcDBRDetails.setDate(5, new java.sql.Date(apps.getTermLoan().getFinalDisbursementDate().getTime()));
				} else {
					wcDBRDetails.setNull(5, 91);
				}

				wcDBRDetails.setString(6, createdBy);

				wcDBRDetails.execute();

				int wcDBRDetailsValue = wcDBRDetails.getInt(1);
				if (wcDBRDetailsValue == 1) {
					String error = wcDBRDetails.getString(7);

					wcDBRDetails.close();
					wcDBRDetails = null;
					connection.rollback();

					Log.log(2, "ApplicationDAO", "submitTermCreditDetails", "Term Credit Dtl Exception" + error);
					throw new DatabaseException(error);
				}
				wcDBRDetails.close();
				wcDBRDetails = null;

			}

			// } //Diksha 02052020

			/*
			 * else if (appLoanType.equals("TC")) { CallableStatement wcTCDetails =
			 * connection
			 * .prepareCall("{?=call funcInsertWorkingCapital(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}"
			 * );
			 * 
			 * wcTCDetails.registerOutParameter(1, 4); wcTCDetails.registerOutParameter(18,
			 * 12);
			 * 
			 * wcTCDetails.setString(2, apps.getAppRefNo()); if
			 * (apps.getProjectOutlayDetails().getWcFundBasedSanctioned() != 0.0D) {
			 * wcTCDetails.setDouble(3, apps.getProjectOutlayDetails()
			 * .getWcFundBasedSanctioned()); } else { wcTCDetails.setDouble(3, 0.0D); }
			 * 
			 * Log.log(5, "ApplicationDAO", "submitWCDetails", "Fund BAsed Sanctioned :" +
			 * apps.getProjectOutlayDetails() .getWcFundBasedSanctioned());
			 * 
			 * if (apps.getProjectOutlayDetails() .getWcNonFundBasedSanctioned() == 0.0D) {
			 * wcTCDetails.setDouble(4, 0.0D); } else { wcTCDetails.setDouble(4,
			 * apps.getProjectOutlayDetails() .getWcNonFundBasedSanctioned()); } Log.log(5,
			 * "ApplicationDAO", "submitWCDetails", "Fund Non BAsed Sanctioned :" +
			 * apps.getProjectOutlayDetails() .getWcNonFundBasedSanctioned());
			 * 
			 * if (apps.getProjectOutlayDetails().getWcPromoterContribution() == 0.0D) {
			 * wcTCDetails.setNull(5, 8); } else wcTCDetails.setDouble(5,
			 * apps.getProjectOutlayDetails() .getWcPromoterContribution());
			 * 
			 * if (apps.getProjectOutlayDetails().getWcSubsidyOrEquity() == 0.0D) {
			 * wcTCDetails.setNull(6, 8); } else wcTCDetails.setDouble(6,
			 * apps.getProjectOutlayDetails() .getWcSubsidyOrEquity());
			 * 
			 * if (apps.getProjectOutlayDetails().getWcOthers() == 0.0D) {
			 * wcTCDetails.setNull(7, 8); } else wcTCDetails.setDouble(7,
			 * apps.getProjectOutlayDetails() .getWcOthers());
			 * 
			 * wcTCDetails.setNull(8, 8); wcTCDetails.setDouble(9, 0.0D);
			 * wcTCDetails.setNull(10, 91); wcTCDetails.setNull(11, 91);
			 * wcTCDetails.setNull(12, 8); wcTCDetails.setDouble(13, 0.0D);
			 * wcTCDetails.setNull(14, 8); wcTCDetails.setNull(15, 12);
			 * wcTCDetails.setString(16, "T"); wcTCDetails.setNull(17, 4);
			 * 
			 * wcTCDetails.execute();
			 * 
			 * int wcTCDetailsValue = wcTCDetails.getInt(1); Log.log(5, "ApplicationDAO",
			 * "submitWCDetails", "TCWC Details result :" + wcTCDetailsValue);
			 * 
			 * if (wcTCDetailsValue == 1) { String error = wcTCDetails.getString(18);
			 * 
			 * wcTCDetails.close(); wcTCDetails = null;
			 * 
			 * connection.rollback();
			 * 
			 * Log.log(2, "ApplicationDAO", "submitWCDetails", "WCTC Dtl Exception" +
			 * error); throw new DatabaseException(error); } wcTCDetails.close();
			 * wcTCDetails = null; }
			 */
			// }
			System.out.println("Diksha---3");
		}

		catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "submitWCDetails", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(4, "ApplicationDAO", "submitWCDetails", ignore.getMessage());
			}

			throw new DatabaseException(sqlException.getMessage());
		}

		Log.log(4, "ApplicationDAO", "submitWCDetails", "Exited");
	}

	/*
	 * catch (SQLException sqlException) { Log.log(4, "ApplicationDAO",
	 * "submitWCDetails", sqlException.getMessage());
	 * Log.logException(sqlException); try { connection.rollback(); } catch
	 * (SQLException ignore) { Log.log(4, "ApplicationDAO", "submitWCDetails",
	 * ignore.getMessage()); }
	 * 
	 * throw new DatabaseException(sqlException.getMessage()); }
	 * 
	 * Log.log(4, "ApplicationDAO", "submitWCDetails", "Exited"); }
	 */

	public void submitWCDetailsNew(Application apps, String createdBy, Connection connection) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "submitWCDetailsNew", "Entered");

		String appLoanType = apps.getLoanType();
		try {
			if ((appLoanType.equals("WC")) || (appLoanType.equals("CC")) || (appLoanType.equals("BO"))) {
				CallableStatement wcDetails = connection
						.prepareCall("{?=call funcInsertWorkingCapital(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

				wcDetails.registerOutParameter(1, 4);
				wcDetails.registerOutParameter(18, 12);

				wcDetails.setString(2, apps.getAppRefNo());
				if (apps.getProjectOutlayDetails().getWcFundBasedSanctioned() == 0.0D) {
					wcDetails.setNull(3, 8);
				} else
					wcDetails.setDouble(3, apps.getProjectOutlayDetails().getWcFundBasedSanctioned());

				if (apps.getProjectOutlayDetails().getWcNonFundBasedSanctioned() == 0.0D) {
					wcDetails.setDouble(4, 0.0D);
				} else
					wcDetails.setDouble(4, apps.getProjectOutlayDetails().getWcNonFundBasedSanctioned());

				if (apps.getProjectOutlayDetails().getWcPromoterContribution() == 0.0D) {
					wcDetails.setNull(5, 8);
				} else
					wcDetails.setDouble(5, apps.getProjectOutlayDetails().getWcPromoterContribution());

				if (apps.getProjectOutlayDetails().getWcSubsidyOrEquity() == 0.0D) {
					wcDetails.setNull(6, 8);
				} else
					wcDetails.setDouble(6, apps.getProjectOutlayDetails().getWcSubsidyOrEquity());

				if (apps.getProjectOutlayDetails().getWcOthers() == 0.0D) {
					wcDetails.setNull(7, 8);
				} else
					wcDetails.setDouble(7, apps.getProjectOutlayDetails().getWcOthers());

				wcDetails.setDouble(8, apps.getWc().getLimitFundBasedInterest());
				Log.log(4, "ApplicationDAO", "submitWCDetailsNew",
						"Interest :" + apps.getWc().getLimitFundBasedInterest());

				if (apps.getWc().getLimitNonFundBasedCommission() == 0.0D) {
					wcDetails.setDouble(9, 0.0D);
				} else {
					wcDetails.setDouble(9, apps.getWc().getLimitNonFundBasedCommission());
				}
				Log.log(4, "ApplicationDAO", "submitWCDetailsNew",
						"Commission :" + apps.getWc().getLimitNonFundBasedCommission());

				if ((apps.getWc().getLimitFundBasedSanctionedDate() != null)
						&& (!apps.getWc().getLimitFundBasedSanctionedDate().toString().equals(""))) {
					wcDetails.setDate(10, new java.sql.Date(apps.getWc().getLimitFundBasedSanctionedDate().getTime()));
				} else {
					wcDetails.setNull(10, 91);
				}

				Log.log(4, "ApplicationDAO", "submitWCDetailsNew",
						"Fund Based Sanctioned Date :" + apps.getWc().getLimitFundBasedSanctionedDate());

				if ((apps.getWc().getLimitNonFundBasedSanctionedDate() != null)
						&& (!apps.getWc().getLimitNonFundBasedSanctionedDate().toString().equals(""))) {
					wcDetails.setDate(11,
							new java.sql.Date(apps.getWc().getLimitNonFundBasedSanctionedDate().getTime()));
				} else {
					wcDetails.setNull(11, 91);
				}

				Log.log(4, "ApplicationDAO", "submitWCDetailsNew",
						"Non Fund Based Sanctioned Date : " + apps.getWc().getLimitNonFundBasedSanctionedDate());

				if (apps.getWc().getCreditFundBased() == 0.0D) {
					wcDetails.setNull(12, 8);
				} else
					wcDetails.setDouble(12, apps.getWc().getCreditFundBased());

				Log.log(4, "ApplicationDAO", "submitWCDetailsNew",
						"Credit Fund Based :" + apps.getWc().getCreditFundBased());

				if (apps.getWc().getCreditNonFundBased() == 0.0D) {
					wcDetails.setDouble(13, 0.0D);
				} else
					wcDetails.setDouble(13, apps.getWc().getCreditNonFundBased());

				Log.log(4, "ApplicationDAO", "submitWCDetailsNew",
						"NonFundBased :" + apps.getWc().getCreditNonFundBased());

				if (apps.getWc().getWcPlr() == 0.0D) {
					wcDetails.setNull(14, 8);
				} else
					wcDetails.setDouble(14, apps.getWc().getWcPlr());

				Log.log(4, "ApplicationDAO", "submitWCDetailsNew", "PLR :" + apps.getWc().getWcPlr());

				if ((apps.getWc().getWcTypeOfPLR() != null) && (!apps.getWc().getWcTypeOfPLR().equals(""))) {
					wcDetails.setString(15, apps.getWc().getWcTypeOfPLR());
				} else {
					wcDetails.setNull(15, 12);
				}

				if ((apps.getWc().getWcInterestType() != null) && (!apps.getWc().getWcInterestType().equals(""))) {
					wcDetails.setString(16, apps.getWc().getWcInterestType());
				} else {
					wcDetails.setNull(16, 12);
				}

				if (apps.getWc().getWcTenure() != 0) {
					wcDetails.setInt(17, apps.getWc().getWcTenure());
				} else {
					wcDetails.setNull(17, 4);
				}

				wcDetails.executeQuery();
				int wcDetailsValue = wcDetails.getInt(1);

				Log.log(5, "ApplicationDAO", "submitWCDetailsNew", "WC Details result :" + wcDetailsValue);

				if (wcDetailsValue == 1) {
					String error = wcDetails.getString(18);

					wcDetails.close();
					wcDetails = null;

					connection.rollback();

					Log.log(2, "ApplicationDAO", "submitWCDetailsNew", "Working Credit Dtl Exception" + error);
					throw new DatabaseException(error);
				}
				wcDetails.close();
				wcDetails = null;

				Log.log(5, "ApplicationDAO", "submitWCDetailsNew",
						"Fund Based Principal :" + apps.getWc().getOsFundBasedPpl());
				if (apps.getWc().getOsFundBasedPpl() != 0.0D) {
					Log.log(5, "ApplicationDAO", "submitWCDetailsNew", "Fund Based Principal is not zero");

					CallableStatement wcOutDetails = connection
							.prepareCall("{?=call funcInsertWCOutStand(?,?,?,?,?,?,?,?)}");

					wcOutDetails.registerOutParameter(1, 4);
					wcOutDetails.registerOutParameter(9, 12);

					wcOutDetails.setString(2, apps.getAppRefNo());

					if (apps.getWc().getOsFundBasedPpl() == 0.0D) {
						wcOutDetails.setNull(3, 8);
					} else
						wcOutDetails.setDouble(3, apps.getWc().getOsFundBasedPpl());

					Log.log(5, "ApplicationDAO", "submitWCDetailsNew",
							"Fund Based Principal :" + apps.getWc().getOsFundBasedPpl());

					if (apps.getWc().getOsFundBasedInterestAmt() == 0.0D) {
						wcOutDetails.setDouble(4, 0.0D);
					} else {
						wcOutDetails.setDouble(4, apps.getWc().getOsFundBasedInterestAmt());
					}
					Log.log(5, "ApplicationDAO", "submitWCDetailsNew",
							"Interest Fund Based :" + apps.getWc().getOsFundBasedInterestAmt());

					if ((apps.getWc().getOsFundBasedAsOnDate() != null)
							&& (!apps.getWc().getOsFundBasedAsOnDate().toString().equals(""))) {
						wcOutDetails.setDate(5, new java.sql.Date(apps.getWc().getOsFundBasedAsOnDate().getTime()));
					} else {
						wcOutDetails.setDate(5, null);
					}
					Log.log(5, "ApplicationDAO", "submitWCDetailsNew",
							"Sanctioned Date :" + apps.getWc().getOsFundBasedAsOnDate());

					if (apps.getWc().getOsNonFundBasedPpl() == 0.0D) {
						wcOutDetails.setDouble(6, 0.0D);
					} else
						wcOutDetails.setDouble(6, apps.getWc().getOsNonFundBasedPpl());

					Log.log(5, "ApplicationDAO", "submitWCDetailsNew",
							"NonFundBased Principal :" + apps.getWc().getOsNonFundBasedPpl());

					if (apps.getWc().getOsNonFundBasedCommissionAmt() == 0.0D) {
						wcOutDetails.setDouble(7, 0.0D);
					} else {
						wcOutDetails.setDouble(7, apps.getWc().getOsNonFundBasedCommissionAmt());
					}
					Log.log(5, "ApplicationDAO", "submitWCDetailsNew",
							"NonFundBasedCommission :" + apps.getWc().getOsNonFundBasedCommissionAmt());

					if ((apps.getWc().getOsNonFundBasedAsOnDate() != null)
							&& (!apps.getWc().getOsNonFundBasedAsOnDate().toString().equals(""))) {
						wcOutDetails.setDate(8, new java.sql.Date(apps.getWc().getOsNonFundBasedAsOnDate().getTime()));
					} else {
						wcOutDetails.setDate(8, null);
					}

					Log.log(5, "ApplicationDAO", "submitWCDetailsNew",
							"NonFundBased Sanctioned Date :" + apps.getWc().getOsNonFundBasedAsOnDate());

					wcOutDetails.executeQuery();
					int wcOutDetailsValue = wcOutDetails.getInt(1);

					Log.log(5, "ApplicationDAO", "submitWCDetailsNew", "WC Details result :" + wcOutDetailsValue);

					if (wcOutDetailsValue == 1) {
						String error = wcOutDetails.getString(9);

						wcOutDetails.close();
						wcOutDetails = null;

						connection.rollback();

						Log.log(2, "ApplicationDAO", "submitWCDetailsNew", "Working Credit Dtl Exception" + error);
						throw new DatabaseException(error);
					}
					wcOutDetails.close();
					wcOutDetails = null;
				}
			} else if (appLoanType.equals("TC")) {
				CallableStatement wcTCDetails = connection
						.prepareCall("{?=call funcInsertWorkingCapital(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

				wcTCDetails.registerOutParameter(1, 4);
				wcTCDetails.registerOutParameter(18, 12);

				wcTCDetails.setString(2, apps.getAppRefNo());
				if (apps.getProjectOutlayDetails().getWcFundBasedSanctioned() != 0.0D) {
					wcTCDetails.setDouble(3, apps.getProjectOutlayDetails().getWcFundBasedSanctioned());
				} else {
					wcTCDetails.setDouble(3, 0.0D);
				}

				Log.log(5, "ApplicationDAO", "submitWCDetailsNew",
						"Fund BAsed Sanctioned :" + apps.getProjectOutlayDetails().getWcFundBasedSanctioned());

				if (apps.getProjectOutlayDetails().getWcNonFundBasedSanctioned() == 0.0D) {
					wcTCDetails.setDouble(4, 0.0D);
				} else {
					wcTCDetails.setDouble(4, apps.getProjectOutlayDetails().getWcNonFundBasedSanctioned());
				}
				Log.log(5, "ApplicationDAO", "submitWCDetailsNew",
						"Fund Non BAsed Sanctioned :" + apps.getProjectOutlayDetails().getWcNonFundBasedSanctioned());

				if (apps.getProjectOutlayDetails().getWcPromoterContribution() == 0.0D) {
					wcTCDetails.setNull(5, 8);
				} else
					wcTCDetails.setDouble(5, apps.getProjectOutlayDetails().getWcPromoterContribution());

				if (apps.getProjectOutlayDetails().getWcSubsidyOrEquity() == 0.0D) {
					wcTCDetails.setNull(6, 8);
				} else
					wcTCDetails.setDouble(6, apps.getProjectOutlayDetails().getWcSubsidyOrEquity());

				if (apps.getProjectOutlayDetails().getWcOthers() == 0.0D) {
					wcTCDetails.setNull(7, 8);
				} else
					wcTCDetails.setDouble(7, apps.getProjectOutlayDetails().getWcOthers());

				wcTCDetails.setNull(8, 8);
				wcTCDetails.setDouble(9, 0.0D);
				wcTCDetails.setNull(10, 91);
				wcTCDetails.setNull(11, 91);
				wcTCDetails.setNull(12, 8);
				wcTCDetails.setDouble(13, 0.0D);
				wcTCDetails.setNull(14, 8);
				wcTCDetails.setNull(15, 12);
				wcTCDetails.setString(16, "T");
				wcTCDetails.setNull(17, 4);

				wcTCDetails.execute();

				int wcTCDetailsValue = wcTCDetails.getInt(1);
				Log.log(5, "ApplicationDAO", "submitWCDetailsNew", "TCWC Details result :" + wcTCDetailsValue);

				if (wcTCDetailsValue == 1) {
					String error = wcTCDetails.getString(18);

					wcTCDetails.close();
					wcTCDetails = null;

					connection.rollback();

					Log.log(2, "ApplicationDAO", "submitWCDetailsNew", "WCTC Dtl Exception" + error);
					throw new DatabaseException(error);
				}
				wcTCDetails.close();
				wcTCDetails = null;
			}

		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "submitWCDetailsNew", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(4, "ApplicationDAO", "submitWCDetailsNew", ignore.getMessage());
			}

			throw new DatabaseException(sqlException.getMessage());
		}

		Log.log(4, "ApplicationDAO", "submitWCDetailsNew", "Exited");
	}

	public void submitSecDetails(Application app, Connection connection) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "submitSecDetails", "Entered");
		try {
			CallableStatement secDetails = connection.prepareCall("{?=call funcInsertSecDetails(?,?,?,?,?,?,?,?,?,?)}");

			secDetails.registerOutParameter(1, 4);
			secDetails.registerOutParameter(11, 12);

			secDetails.setString(2, app.getAppRefNo());

			if (app.getSecuritization().getSpreadOverPLR() == 0.0D) {
				secDetails.setNull(3, 8);
			} else {
				secDetails.setDouble(3, app.getSecuritization().getSpreadOverPLR());
				Log.log(4, "ApplicationDAO", "submitSecDetails",
						"Spread over PLR :" + app.getSecuritization().getSpreadOverPLR());
			}

			if ((app.getSecuritization().getPplRepaymentInEqual() == null)
					|| (app.getSecuritization().getPplRepaymentInEqual().equals(""))) {
				secDetails.setNull(4, 12);
			} else {
				secDetails.setString(4, app.getSecuritization().getPplRepaymentInEqual());
				Log.log(4, "ApplicationDAO", "submitSecDetails",
						"Repayment in equal inst :" + app.getSecuritization().getPplRepaymentInEqual());
			}

			if (app.getSecuritization().getTangibleNetWorth() == 0.0D) {
				secDetails.setNull(5, 8);
			} else {
				secDetails.setDouble(5, app.getSecuritization().getTangibleNetWorth());
				Log.log(4, "ApplicationDAO", "submitSecDetails",
						"tangible worth:" + app.getSecuritization().getTangibleNetWorth());
			}

			if (app.getSecuritization().getFixedACR() == 0.0D) {
				secDetails.setNull(6, 8);
			} else {
				secDetails.setDouble(6, app.getSecuritization().getFixedACR());
				Log.log(4, "ApplicationDAO", "submitSecDetails", "fixed ACR:" + app.getSecuritization().getFixedACR());
			}

			if (app.getSecuritization().getCurrentRatio() == 0.0D) {
				secDetails.setNull(7, 8);
			} else {
				secDetails.setDouble(7, app.getSecuritization().getCurrentRatio());
				Log.log(4, "ApplicationDAO", "submitSecDetails",
						"Current Ratio:" + app.getSecuritization().getCurrentRatio());
			}

			if (app.getSecuritization().getMinimumDSCR() == 0.0D) {
				secDetails.setNull(8, 12);
			} else {
				secDetails.setDouble(8, app.getSecuritization().getMinimumDSCR());
				Log.log(4, "ApplicationDAO", "submitSecDetails",
						"Min dscr:" + app.getSecuritization().getMinimumDSCR());
			}

			if (app.getSecuritization().getAvgDSCR() == 0.0D) {
				secDetails.setNull(9, 8);
			} else {
				secDetails.setDouble(9, app.getSecuritization().getAvgDSCR());
				Log.log(4, "ApplicationDAO", "submitSecDetails", "Avg dscr:" + app.getSecuritization().getAvgDSCR());
			}

			if (app.getSecuritization().getFinancialPartUnit() == 0.0D) {
				secDetails.setNull(10, 8);
			} else {
				secDetails.setDouble(10, app.getSecuritization().getFinancialPartUnit());
				Log.log(4, "ApplicationDAO", "submitSecDetails",
						"Financial part of a unit:" + app.getSecuritization().getFinancialPartUnit());
			}

			secDetails.execute();
			int secDetailsValue = secDetails.getInt(1);
			Log.log(4, "ApplicationDAO", "submitSecDetails", "secDetailsValue:" + secDetailsValue);

			if (secDetailsValue == 1) {
				String error = secDetails.getString(11);

				secDetails.close();
				secDetails = null;

				connection.rollback();
//System.out.println("submitSecDetails 26 error "+error);
				Log.log(2, "ApplicationDAO", "submitSecDetails", error);
				throw new DatabaseException(error);
			}
			secDetails.close();
			secDetails = null;
		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "submitSecDetails", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(4, "ApplicationDAO", "submitSecDetails", ignore.getMessage());
			}
			// System.out.println("submitSecDetails 27 error "+sqlException.getMessage());
			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

	}

	public void rejectApplication(String cgpan, String remarks, String userId) throws DatabaseException {
		Connection connection = null;
		CallableStatement stmt = null;
		try {
			connection = DBConnection.getConnection();

			stmt = connection.prepareCall("{?=call FuncRejectAppl(?,?,?,?)}");
			stmt.registerOutParameter(1, 4);

			stmt.setString(2, cgpan);
			stmt.setString(3, remarks);
			stmt.setString(4, userId);
			stmt.registerOutParameter(5, 12);
			stmt.execute();

			int status = stmt.getInt(1);

			if (status == 1) {
				String err = stmt.getString(5);

				stmt.close();
				stmt = null;
				throw new DatabaseException(err);
			}
			stmt.close();
			stmt = null;

			Log.log(4, "ApplicationDAO", "rejectApplication", "Application Status Changed Successfully");
		} catch (SQLException exp) {
			Log.log(4, "ApplicationDAO", "rejectApplication", "sql exception " + exp.getMessage());

			throw new DatabaseException(exp.getMessage());
		} finally {
			DBConnection.freeConnection(connection);

		}
	}

	public BorrowerDetails fetchBorrowerDtls(String cgbid, String cgpan) throws DatabaseException {

		// System.out.println("ADAO fetchBorrowerDtls....... S");
		Log.log(4, "ApplicationDAO", "fetchBorrowerDtls", "Entered");

		Connection connection = DBConnection.getConnection(false);

		Application application = new Application();
		BorrowerDetails borrowerDetails = new BorrowerDetails();
		SSIDetails ssiDetails = new SSIDetails();
		try {
			if (cgpan.equals("")) {
				Log.log(4, "ApplicationDAO", "fetchBorrowerDtls", "Entered");

				CallableStatement ssiDtlForCgbid = connection.prepareCall(
						"{?=call funcGetSSIDetailforBorId(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				// System.out.println("funcGetSSIDetailforBorId S");
				Log.log(4, "ApplicationDAO", "fetchBorrowerDtls", "CGBID" + cgbid);

				ssiDtlForCgbid.setString(2, cgbid);

				ssiDtlForCgbid.registerOutParameter(1, 4);
				ssiDtlForCgbid.registerOutParameter(30, 12);

				ssiDtlForCgbid.registerOutParameter(3, 4);
				ssiDtlForCgbid.registerOutParameter(4, 12);
				ssiDtlForCgbid.registerOutParameter(5, 12);
				ssiDtlForCgbid.registerOutParameter(6, 12);
				ssiDtlForCgbid.registerOutParameter(7, 12);
				ssiDtlForCgbid.registerOutParameter(8, 12);
				ssiDtlForCgbid.registerOutParameter(9, 12);
				ssiDtlForCgbid.registerOutParameter(10, 12);
				ssiDtlForCgbid.registerOutParameter(11, 12);
				ssiDtlForCgbid.registerOutParameter(12, 91);
				ssiDtlForCgbid.registerOutParameter(13, 12);
				ssiDtlForCgbid.registerOutParameter(14, 12);
				ssiDtlForCgbid.registerOutParameter(15, 4);
				ssiDtlForCgbid.registerOutParameter(16, 8);
				ssiDtlForCgbid.registerOutParameter(17, 8);
				ssiDtlForCgbid.registerOutParameter(18, 12);
				ssiDtlForCgbid.registerOutParameter(19, 12);
				ssiDtlForCgbid.registerOutParameter(20, 12);
				ssiDtlForCgbid.registerOutParameter(21, 12);
				ssiDtlForCgbid.registerOutParameter(22, 12);
				ssiDtlForCgbid.registerOutParameter(23, 12);
				ssiDtlForCgbid.registerOutParameter(24, 12);
				ssiDtlForCgbid.registerOutParameter(25, 12);
				ssiDtlForCgbid.registerOutParameter(26, 12);
				ssiDtlForCgbid.registerOutParameter(27, 12);
				ssiDtlForCgbid.registerOutParameter(28, 8);
				ssiDtlForCgbid.registerOutParameter(29, 12);

				ssiDtlForCgbid.executeQuery();
				int ssiDtlForCgbidValue = ssiDtlForCgbid.getInt(1);

				Log.log(5, "ApplicationDAO", "fetchBorrowerDtls", "SSI Details for CGBID :" + ssiDtlForCgbidValue);

				if (ssiDtlForCgbidValue == 1) {
					String error = ssiDtlForCgbid.getString(30);

					ssiDtlForCgbid.close();
					ssiDtlForCgbid = null;

					connection.rollback();

					Log.log(5, "ApplicationDAO", "fetchBorrowerDtls", "SSI Exception message:" + error);

					throw new DatabaseException(error);
				}

				ssiDetails.setBorrowerRefNo(ssiDtlForCgbid.getInt(3));
				borrowerDetails.setPreviouslyCovered(ssiDtlForCgbid.getString(4).trim());
				borrowerDetails.setAssistedByBank(ssiDtlForCgbid.getString(5).trim());
				borrowerDetails.setNpa(ssiDtlForCgbid.getString(7).trim());
				ssiDetails.setConstitution(ssiDtlForCgbid.getString(8));
				ssiDetails.setSsiType(ssiDtlForCgbid.getString(9).trim());
				ssiDetails.setSsiName(ssiDtlForCgbid.getString(10));
				ssiDetails.setRegNo(ssiDtlForCgbid.getString(11));
				ssiDetails.setSsiITPan(ssiDtlForCgbid.getString(13));
				ssiDetails.setActivityType(ssiDtlForCgbid.getString(14));
				ssiDetails.setEmployeeNos(ssiDtlForCgbid.getInt(15));
				ssiDetails.setProjectedSalesTurnover(ssiDtlForCgbid.getDouble(16));
				ssiDetails.setProjectedExports(ssiDtlForCgbid.getDouble(17));
				ssiDetails.setAddress(ssiDtlForCgbid.getString(18));
				ssiDetails.setCity(ssiDtlForCgbid.getString(19));
				ssiDetails.setPincode(ssiDtlForCgbid.getString(20).trim());
				ssiDetails.setDistrict(ssiDtlForCgbid.getString(22));
				ssiDetails.setState(ssiDtlForCgbid.getString(23));
				ssiDetails.setIndustryNature(ssiDtlForCgbid.getString(24));
				ssiDetails.setIndustrySector(ssiDtlForCgbid.getString(25));
				ssiDetails.setCgbid(ssiDtlForCgbid.getString(27));
				borrowerDetails.setOsAmt(ssiDtlForCgbid.getDouble(28));

				ssiDtlForCgbid.close();
				ssiDtlForCgbid = null;
			} else if (cgbid.equals("")) {
				Log.log(4, "ApplicationDAO", "fetchBorrowerDtls",
						"Entering the method in DAO to fetch dtls for cgpan..");

				CallableStatement ssiDtlForCgpan = connection.prepareCall(
						"{?=call funcGetSSIDetailforCGPAN(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				// System.out.println("ADAO funcGetSSIDetailforCGPAN S ");
				Log.log(5, "ApplicationDAO", "fetchBorrowerDtls", "CGPAN" + cgpan);

				ssiDtlForCgpan.setString(2, null);
				ssiDtlForCgpan.setString(3, null);
				ssiDtlForCgpan.setString(4, null);
				ssiDtlForCgpan.setString(5, cgpan);

				ssiDtlForCgpan.registerOutParameter(1, 4);
				ssiDtlForCgpan.registerOutParameter(32, 12);

				ssiDtlForCgpan.registerOutParameter(6, 4);
				ssiDtlForCgpan.registerOutParameter(7, 12);
				ssiDtlForCgpan.registerOutParameter(8, 12);
				ssiDtlForCgpan.registerOutParameter(9, 12);
				ssiDtlForCgpan.registerOutParameter(10, 12);
				ssiDtlForCgpan.registerOutParameter(11, 12);
				ssiDtlForCgpan.registerOutParameter(12, 12);
				ssiDtlForCgpan.registerOutParameter(13, 12);
				ssiDtlForCgpan.registerOutParameter(14, 12);
				ssiDtlForCgpan.registerOutParameter(15, 91);
				ssiDtlForCgpan.registerOutParameter(16, 12);
				ssiDtlForCgpan.registerOutParameter(17, 12);
				ssiDtlForCgpan.registerOutParameter(18, 4);
				ssiDtlForCgpan.registerOutParameter(19, 8);
				ssiDtlForCgpan.registerOutParameter(20, 8);
				ssiDtlForCgpan.registerOutParameter(21, 12);
				ssiDtlForCgpan.registerOutParameter(22, 12);
				ssiDtlForCgpan.registerOutParameter(23, 12);
				ssiDtlForCgpan.registerOutParameter(24, 12);
				ssiDtlForCgpan.registerOutParameter(25, 12);
				ssiDtlForCgpan.registerOutParameter(26, 12);
				ssiDtlForCgpan.registerOutParameter(27, 12);
				ssiDtlForCgpan.registerOutParameter(28, 12);
				ssiDtlForCgpan.registerOutParameter(29, 12);
				ssiDtlForCgpan.registerOutParameter(30, 12);
				ssiDtlForCgpan.registerOutParameter(31, 8);

				ssiDtlForCgpan.executeQuery();
				int ssiDtlForCgpanValue = ssiDtlForCgpan.getInt(1);
				// System.out.println("ssiDtlForCgpanValue "+ssiDtlForCgpanValue);
				Log.log(5, "ApplicationDAO", "fetchBorrowerDtls", "SSI Details for CGPAN :" + ssiDtlForCgpanValue);

				if (ssiDtlForCgpanValue == 1) {
					String error = ssiDtlForCgpan.getString(32);

					ssiDtlForCgpan.close();
					ssiDtlForCgpan = null;

					connection.rollback();

					Log.log(5, "ApplicationDAO", "fetchBorrowerDtls", "SSI Exception message:" + error);

					throw new DatabaseException(error);
				}

				ssiDetails.setBorrowerRefNo(ssiDtlForCgpan.getInt(6));
				borrowerDetails.setPreviouslyCovered(ssiDtlForCgpan.getString(7).trim());
				borrowerDetails.setAssistedByBank(ssiDtlForCgpan.getString(8).trim());
				borrowerDetails.setNpa(ssiDtlForCgpan.getString(10).trim());
				ssiDetails.setConstitution(ssiDtlForCgpan.getString(11));
				ssiDetails.setSsiType(ssiDtlForCgpan.getString(12).trim());
				ssiDetails.setSsiName(ssiDtlForCgpan.getString(13));
				ssiDetails.setRegNo(ssiDtlForCgpan.getString(14));
				ssiDetails.setSsiITPan(ssiDtlForCgpan.getString(16));
				ssiDetails.setActivityType(ssiDtlForCgpan.getString(17));
				ssiDetails.setEmployeeNos(ssiDtlForCgpan.getInt(18));
				ssiDetails.setProjectedSalesTurnover(ssiDtlForCgpan.getDouble(19));
				ssiDetails.setProjectedExports(ssiDtlForCgpan.getDouble(20));
				ssiDetails.setAddress(ssiDtlForCgpan.getString(21));
				ssiDetails.setCity(ssiDtlForCgpan.getString(22));
				ssiDetails.setPincode(ssiDtlForCgpan.getString(23).trim());
				ssiDetails.setDistrict(ssiDtlForCgpan.getString(25));
				ssiDetails.setState(ssiDtlForCgpan.getString(26));
				ssiDetails.setIndustryNature(ssiDtlForCgpan.getString(27));
				ssiDetails.setIndustrySector(ssiDtlForCgpan.getString(28));
				ssiDetails.setCgbid(ssiDtlForCgpan.getString(30));
				borrowerDetails.setOsAmt(ssiDtlForCgpan.getDouble(31));

				ssiDtlForCgpan.close();
				ssiDtlForCgpan = null;
			}

			int ssiRefNo = ssiDetails.getBorrowerRefNo();

			CallableStatement ssiDtlForSsiRef = connection
					.prepareCall("{?=call funcGetPromoterDtlforSSIRef(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			ssiDtlForSsiRef.setInt(2, ssiRefNo);

			ssiDtlForSsiRef.registerOutParameter(1, 4);
			ssiDtlForSsiRef.registerOutParameter(23, 12);

			ssiDtlForSsiRef.registerOutParameter(3, 4);
			ssiDtlForSsiRef.registerOutParameter(4, 12);
			ssiDtlForSsiRef.registerOutParameter(5, 12);
			ssiDtlForSsiRef.registerOutParameter(6, 12);
			ssiDtlForSsiRef.registerOutParameter(7, 12);
			ssiDtlForSsiRef.registerOutParameter(8, 12);
			ssiDtlForSsiRef.registerOutParameter(9, 12);
			ssiDtlForSsiRef.registerOutParameter(10, 91);
			ssiDtlForSsiRef.registerOutParameter(11, 12);
			ssiDtlForSsiRef.registerOutParameter(12, 12);
			ssiDtlForSsiRef.registerOutParameter(13, 12);
			ssiDtlForSsiRef.registerOutParameter(14, 91);
			ssiDtlForSsiRef.registerOutParameter(15, 12);
			ssiDtlForSsiRef.registerOutParameter(16, 12);
			ssiDtlForSsiRef.registerOutParameter(17, 91);
			ssiDtlForSsiRef.registerOutParameter(18, 12);
			ssiDtlForSsiRef.registerOutParameter(19, 12);
			ssiDtlForSsiRef.registerOutParameter(20, 91);
			ssiDtlForSsiRef.registerOutParameter(21, 12);
			ssiDtlForSsiRef.registerOutParameter(22, 12);

			ssiDtlForSsiRef.executeQuery();
			int ssiDtlForSsiRefValue = ssiDtlForSsiRef.getInt(1);
			Log.log(4, "ApplicationDAO", "getApplication", "Promoters Details :" + ssiDtlForSsiRefValue);

			if (ssiDtlForSsiRefValue == 1) {
				String error = ssiDtlForSsiRef.getString(23);

				ssiDtlForSsiRef.close();
				ssiDtlForSsiRef = null;

				connection.rollback();

				Log.log(2, "ApplicationDAO", "getApplication", "Promoter Exception message:" + error);

				throw new DatabaseException(error);
			}
			ssiDetails.setCpTitle(ssiDtlForSsiRef.getString(4));
			ssiDetails.setCpFirstName(ssiDtlForSsiRef.getString(5));
			if ((ssiDtlForSsiRef.getString(6) != null) && (!ssiDtlForSsiRef.getString(6).equals(""))) {
				ssiDetails.setCpMiddleName(ssiDtlForSsiRef.getString(6));
			} else {
				ssiDetails.setCpMiddleName("");
			}

			ssiDetails.setCpLastName(ssiDtlForSsiRef.getString(7));
			ssiDetails.setCpITPAN(ssiDtlForSsiRef.getString(8));
			ssiDetails.setCpGender(ssiDtlForSsiRef.getString(9).trim());
			ssiDetails.setCpDOB(DateHelper.sqlToUtilDate(ssiDtlForSsiRef.getDate(10)));
			ssiDetails.setCpLegalID(ssiDtlForSsiRef.getString(11));
			ssiDetails.setCpLegalIdValue(ssiDtlForSsiRef.getString(12));
			ssiDetails.setFirstName(ssiDtlForSsiRef.getString(13));
			ssiDetails.setFirstDOB(DateHelper.sqlToUtilDate(ssiDtlForSsiRef.getDate(14)));
			ssiDetails.setFirstItpan(ssiDtlForSsiRef.getString(15));
			ssiDetails.setSecondName(ssiDtlForSsiRef.getString(16));
			ssiDetails.setSecondDOB(DateHelper.sqlToUtilDate(ssiDtlForSsiRef.getDate(17)));
			ssiDetails.setSecondItpan(ssiDtlForSsiRef.getString(18));
			ssiDetails.setThirdName(ssiDtlForSsiRef.getString(19));
			ssiDetails.setThirdDOB(DateHelper.sqlToUtilDate(ssiDtlForSsiRef.getDate(20)));
			ssiDetails.setThirdItpan(ssiDtlForSsiRef.getString(21));
			ssiDetails.setSocialCategory(ssiDtlForSsiRef.getString(22));

			ssiDtlForSsiRef.close();
			ssiDtlForSsiRef = null;

			borrowerDetails.setSsiDetails(ssiDetails);
			application.setBorrowerDetails(borrowerDetails);

			Log.log(5, "ApplicationDAO", "fetchBorrowerDtls", "Fetching BorrowerDetails over...");

			connection.commit();
		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "fetchBorrowerDtls", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(4, "ApplicationDAO", "fetchBorrowerDtls", ignore.getMessage());
			}

			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(4, "ApplicationDAO", "fetchBorrowerDtls", "Exited");

		return borrowerDetails;
	}

	public BorrowerDetails fetchBorrowerDtlsNew(String cgbid, String cgpan, String loanType) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "fetchBorrowerDtlsNew", "Entered");

		Connection connection = DBConnection.getConnection(false);

		Application application = new Application();
		BorrowerDetails borrowerDetails = new BorrowerDetails();
		SSIDetails ssiDetails = new SSIDetails();
		try {
			if (cgpan.equals("")) {
				Log.log(4, "ApplicationDAO", "fetchBorrowerDtlsNew", "Entered");

				CallableStatement ssiDtlForCgbid = connection.prepareCall(
						"{?=call funcGetSSIDetailforBorIdNew(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

				Log.log(4, "ApplicationDAO", "fetchBorrowerDtlsNew", "CGBID" + cgbid);

				ssiDtlForCgbid.setString(2, cgbid);

				ssiDtlForCgbid.registerOutParameter(1, 4);
				ssiDtlForCgbid.registerOutParameter(31, 12);
				ssiDtlForCgbid.setString(30, loanType);
				ssiDtlForCgbid.registerOutParameter(3, 4);
				ssiDtlForCgbid.registerOutParameter(4, 12);
				ssiDtlForCgbid.registerOutParameter(5, 12);
				ssiDtlForCgbid.registerOutParameter(6, 12);
				ssiDtlForCgbid.registerOutParameter(7, 12);
				ssiDtlForCgbid.registerOutParameter(8, 12);
				ssiDtlForCgbid.registerOutParameter(9, 12);
				ssiDtlForCgbid.registerOutParameter(10, 12);
				ssiDtlForCgbid.registerOutParameter(11, 12);
				ssiDtlForCgbid.registerOutParameter(12, 91);
				ssiDtlForCgbid.registerOutParameter(13, 12);
				ssiDtlForCgbid.registerOutParameter(14, 12);
				ssiDtlForCgbid.registerOutParameter(15, 4);
				ssiDtlForCgbid.registerOutParameter(16, 8);
				ssiDtlForCgbid.registerOutParameter(17, 8);
				ssiDtlForCgbid.registerOutParameter(18, 12);
				ssiDtlForCgbid.registerOutParameter(19, 12);
				ssiDtlForCgbid.registerOutParameter(20, 12);
				ssiDtlForCgbid.registerOutParameter(21, 12);
				ssiDtlForCgbid.registerOutParameter(22, 12);
				ssiDtlForCgbid.registerOutParameter(23, 12);
				ssiDtlForCgbid.registerOutParameter(24, 12);
				ssiDtlForCgbid.registerOutParameter(25, 12);
				ssiDtlForCgbid.registerOutParameter(26, 12);
				ssiDtlForCgbid.registerOutParameter(27, 12);
				ssiDtlForCgbid.registerOutParameter(28, 8);
				ssiDtlForCgbid.registerOutParameter(29, 12);

				ssiDtlForCgbid.executeQuery();
				int ssiDtlForCgbidValue = ssiDtlForCgbid.getInt(1);

				Log.log(5, "ApplicationDAO", "fetchBorrowerDtlsNew", "SSI Details for CGBID :" + ssiDtlForCgbidValue);

				if (ssiDtlForCgbidValue == 1) {
					String error = ssiDtlForCgbid.getString(31);

					ssiDtlForCgbid.close();
					ssiDtlForCgbid = null;

					connection.rollback();

					Log.log(5, "ApplicationDAO", "fetchBorrowerDtlsNew", "SSI Exception message:" + error);

					throw new DatabaseException(error);
				}

				ssiDetails.setBorrowerRefNo(ssiDtlForCgbid.getInt(3));
				borrowerDetails.setPreviouslyCovered(ssiDtlForCgbid.getString(4).trim());
				borrowerDetails.setAssistedByBank(ssiDtlForCgbid.getString(5).trim());
				borrowerDetails.setNpa(ssiDtlForCgbid.getString(7).trim());
				ssiDetails.setConstitution(ssiDtlForCgbid.getString(8));
				ssiDetails.setSsiType(ssiDtlForCgbid.getString(9).trim());
				ssiDetails.setSsiName(ssiDtlForCgbid.getString(10));
				ssiDetails.setRegNo(ssiDtlForCgbid.getString(11));
				ssiDetails.setSsiITPan(ssiDtlForCgbid.getString(13));
				ssiDetails.setActivityType(ssiDtlForCgbid.getString(14));
				ssiDetails.setEmployeeNos(ssiDtlForCgbid.getInt(15));
				ssiDetails.setProjectedSalesTurnover(ssiDtlForCgbid.getDouble(16));
				ssiDetails.setProjectedExports(ssiDtlForCgbid.getDouble(17));
				ssiDetails.setAddress(ssiDtlForCgbid.getString(18));
				ssiDetails.setCity(ssiDtlForCgbid.getString(19));
				ssiDetails.setPincode(ssiDtlForCgbid.getString(20).trim());
				ssiDetails.setDistrict(ssiDtlForCgbid.getString(22));
				ssiDetails.setState(ssiDtlForCgbid.getString(23));
				ssiDetails.setIndustryNature(ssiDtlForCgbid.getString(24));
				ssiDetails.setIndustrySector(ssiDtlForCgbid.getString(25));
				ssiDetails.setCgbid(ssiDtlForCgbid.getString(27));
				borrowerDetails.setOsAmt(ssiDtlForCgbid.getDouble(28));

				ssiDtlForCgbid.close();
				ssiDtlForCgbid = null;
			} else if (cgbid.equals("")) {
				Log.log(4, "ApplicationDAO", "fetchBorrowerDtlsNew",
						"Entering the method in DAO to fetch dtls for cgpan..");

				CallableStatement ssiDtlForCgpan = connection.prepareCall(
						"{?=call funcGetSSIDetailforCGPANNew(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

				Log.log(5, "ApplicationDAO", "fetchBorrowerDtls", "CGPAN" + cgpan);

				ssiDtlForCgpan.setString(2, null);
				ssiDtlForCgpan.setString(3, null);
				ssiDtlForCgpan.setString(4, null);
				ssiDtlForCgpan.setString(5, cgpan);

				ssiDtlForCgpan.registerOutParameter(1, 4);
				ssiDtlForCgpan.registerOutParameter(33, 12);
				ssiDtlForCgpan.setString(32, loanType);
				ssiDtlForCgpan.registerOutParameter(6, 4);
				ssiDtlForCgpan.registerOutParameter(7, 12);
				ssiDtlForCgpan.registerOutParameter(8, 12);
				ssiDtlForCgpan.registerOutParameter(9, 12);
				ssiDtlForCgpan.registerOutParameter(10, 12);
				ssiDtlForCgpan.registerOutParameter(11, 12);
				ssiDtlForCgpan.registerOutParameter(12, 12);
				ssiDtlForCgpan.registerOutParameter(13, 12);
				ssiDtlForCgpan.registerOutParameter(14, 12);
				ssiDtlForCgpan.registerOutParameter(15, 91);
				ssiDtlForCgpan.registerOutParameter(16, 12);
				ssiDtlForCgpan.registerOutParameter(17, 12);
				ssiDtlForCgpan.registerOutParameter(18, 4);
				ssiDtlForCgpan.registerOutParameter(19, 8);
				ssiDtlForCgpan.registerOutParameter(20, 8);
				ssiDtlForCgpan.registerOutParameter(21, 12);
				ssiDtlForCgpan.registerOutParameter(22, 12);
				ssiDtlForCgpan.registerOutParameter(23, 12);
				ssiDtlForCgpan.registerOutParameter(24, 12);
				ssiDtlForCgpan.registerOutParameter(25, 12);
				ssiDtlForCgpan.registerOutParameter(26, 12);
				ssiDtlForCgpan.registerOutParameter(27, 12);
				ssiDtlForCgpan.registerOutParameter(28, 12);
				ssiDtlForCgpan.registerOutParameter(29, 12);
				ssiDtlForCgpan.registerOutParameter(30, 12);
				ssiDtlForCgpan.registerOutParameter(31, 8);

				ssiDtlForCgpan.executeQuery();
				int ssiDtlForCgpanValue = ssiDtlForCgpan.getInt(1);

				Log.log(5, "ApplicationDAO", "fetchBorrowerDtlsNew", "SSI Details for CGPAN :" + ssiDtlForCgpanValue);

				if (ssiDtlForCgpanValue == 1) {
					String error = ssiDtlForCgpan.getString(33);

					ssiDtlForCgpan.close();
					ssiDtlForCgpan = null;

					connection.rollback();

					Log.log(5, "ApplicationDAO", "fetchBorrowerDtlsNew", "SSI Exception message:" + error);

					throw new DatabaseException(error);
				}

				ssiDetails.setBorrowerRefNo(ssiDtlForCgpan.getInt(6));
				borrowerDetails.setPreviouslyCovered(ssiDtlForCgpan.getString(7).trim());
				borrowerDetails.setAssistedByBank(ssiDtlForCgpan.getString(8).trim());
				borrowerDetails.setNpa(ssiDtlForCgpan.getString(10).trim());
				ssiDetails.setConstitution(ssiDtlForCgpan.getString(11));
				ssiDetails.setSsiType(ssiDtlForCgpan.getString(12).trim());
				ssiDetails.setSsiName(ssiDtlForCgpan.getString(13));
				ssiDetails.setRegNo(ssiDtlForCgpan.getString(14));
				ssiDetails.setSsiITPan(ssiDtlForCgpan.getString(16));
				ssiDetails.setActivityType(ssiDtlForCgpan.getString(17));
				ssiDetails.setEmployeeNos(ssiDtlForCgpan.getInt(18));
				ssiDetails.setProjectedSalesTurnover(ssiDtlForCgpan.getDouble(19));
				ssiDetails.setProjectedExports(ssiDtlForCgpan.getDouble(20));
				ssiDetails.setAddress(ssiDtlForCgpan.getString(21));
				ssiDetails.setCity(ssiDtlForCgpan.getString(22));
				ssiDetails.setPincode(ssiDtlForCgpan.getString(23).trim());
				ssiDetails.setDistrict(ssiDtlForCgpan.getString(25));
				ssiDetails.setState(ssiDtlForCgpan.getString(26));
				ssiDetails.setIndustryNature(ssiDtlForCgpan.getString(27));
				ssiDetails.setIndustrySector(ssiDtlForCgpan.getString(28));
				ssiDetails.setCgbid(ssiDtlForCgpan.getString(30));
				borrowerDetails.setOsAmt(ssiDtlForCgpan.getDouble(31));

				ssiDtlForCgpan.close();
				ssiDtlForCgpan = null;
			}

			int ssiRefNo = ssiDetails.getBorrowerRefNo();

			CallableStatement ssiDtlForSsiRef = connection
					.prepareCall("{?=call funcGetPromoterDtlforSSIRef(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			ssiDtlForSsiRef.setInt(2, ssiRefNo);

			ssiDtlForSsiRef.registerOutParameter(1, 4);
			ssiDtlForSsiRef.registerOutParameter(23, 12);

			ssiDtlForSsiRef.registerOutParameter(3, 4);
			ssiDtlForSsiRef.registerOutParameter(4, 12);
			ssiDtlForSsiRef.registerOutParameter(5, 12);
			ssiDtlForSsiRef.registerOutParameter(6, 12);
			ssiDtlForSsiRef.registerOutParameter(7, 12);
			ssiDtlForSsiRef.registerOutParameter(8, 12);
			ssiDtlForSsiRef.registerOutParameter(9, 12);
			ssiDtlForSsiRef.registerOutParameter(10, 91);
			ssiDtlForSsiRef.registerOutParameter(11, 12);
			ssiDtlForSsiRef.registerOutParameter(12, 12);
			ssiDtlForSsiRef.registerOutParameter(13, 12);
			ssiDtlForSsiRef.registerOutParameter(14, 91);
			ssiDtlForSsiRef.registerOutParameter(15, 12);
			ssiDtlForSsiRef.registerOutParameter(16, 12);
			ssiDtlForSsiRef.registerOutParameter(17, 91);
			ssiDtlForSsiRef.registerOutParameter(18, 12);
			ssiDtlForSsiRef.registerOutParameter(19, 12);
			ssiDtlForSsiRef.registerOutParameter(20, 91);
			ssiDtlForSsiRef.registerOutParameter(21, 12);
			ssiDtlForSsiRef.registerOutParameter(22, 12);

			ssiDtlForSsiRef.executeQuery();
			int ssiDtlForSsiRefValue = ssiDtlForSsiRef.getInt(1);
			Log.log(4, "ApplicationDAO", "getApplication", "Promoters Details :" + ssiDtlForSsiRefValue);

			if (ssiDtlForSsiRefValue == 1) {
				String error = ssiDtlForSsiRef.getString(23);

				ssiDtlForSsiRef.close();
				ssiDtlForSsiRef = null;

				connection.rollback();

				Log.log(2, "ApplicationDAO", "getApplication", "Promoter Exception message:" + error);

				throw new DatabaseException(error);
			}
			ssiDetails.setCpTitle(ssiDtlForSsiRef.getString(4));
			ssiDetails.setCpFirstName(ssiDtlForSsiRef.getString(5));
			if ((ssiDtlForSsiRef.getString(6) != null) && (!ssiDtlForSsiRef.getString(6).equals(""))) {
				ssiDetails.setCpMiddleName(ssiDtlForSsiRef.getString(6));
			} else {
				ssiDetails.setCpMiddleName("");
			}

			ssiDetails.setCpLastName(ssiDtlForSsiRef.getString(7));
			ssiDetails.setCpITPAN(ssiDtlForSsiRef.getString(8));
			ssiDetails.setCpGender(ssiDtlForSsiRef.getString(9).trim());
			ssiDetails.setCpDOB(DateHelper.sqlToUtilDate(ssiDtlForSsiRef.getDate(10)));
			ssiDetails.setCpLegalID(ssiDtlForSsiRef.getString(11));
			ssiDetails.setCpLegalIdValue(ssiDtlForSsiRef.getString(12));
			ssiDetails.setFirstName(ssiDtlForSsiRef.getString(13));
			ssiDetails.setFirstDOB(DateHelper.sqlToUtilDate(ssiDtlForSsiRef.getDate(14)));
			ssiDetails.setFirstItpan(ssiDtlForSsiRef.getString(15));
			ssiDetails.setSecondName(ssiDtlForSsiRef.getString(16));
			ssiDetails.setSecondDOB(DateHelper.sqlToUtilDate(ssiDtlForSsiRef.getDate(17)));
			ssiDetails.setSecondItpan(ssiDtlForSsiRef.getString(18));
			ssiDetails.setThirdName(ssiDtlForSsiRef.getString(19));
			ssiDetails.setThirdDOB(DateHelper.sqlToUtilDate(ssiDtlForSsiRef.getDate(20)));
			ssiDetails.setThirdItpan(ssiDtlForSsiRef.getString(21));
			ssiDetails.setSocialCategory(ssiDtlForSsiRef.getString(22));

			ssiDtlForSsiRef.close();
			ssiDtlForSsiRef = null;

			borrowerDetails.setSsiDetails(ssiDetails);
			application.setBorrowerDetails(borrowerDetails);

			Log.log(5, "ApplicationDAO", "fetchBorrowerDtlsNew", "Fetching BorrowerDetails over...");

			connection.commit();
		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "fetchBorrowerDtlsNew", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(4, "ApplicationDAO", "fetchBorrowerDtlsNew", ignore.getMessage());
			}

			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(4, "ApplicationDAO", "fetchBorrowerDtlsNew", "Exited");

		return borrowerDetails;
	}

	public void updateAppStatus(String appRefNo, int status) throws DatabaseException {
	}

	// Update aadhar ENHANCE by DKR
	public void storeWcEnhancement(Application app, String createdBy) throws DatabaseException {
		String appRefNo = app.getAppRefNo();
		Log.log(4, "ApplicationDAO", "storeWcEnhancement", "app ref no :" + appRefNo);
		Log.log(4, "ApplicationDAO", "storeWcEnhancement", "Entered");

		RiskManagementProcessor rpProcessor = new RiskManagementProcessor();

		Connection connection = DBConnection.getConnection(false);
		try {
			String subSchemeName = rpProcessor.getSubScheme(app);
			app.setSubSchemeName(subSchemeName);

			submitApp(app, createdBy, connection);
			int temssiRef = app.getBorrowerDetails().getSsiDetails().getBorrowerRefNo();
			String ssiRefNumber = Integer.toString(temssiRef);
			// System.out.println("ADAO ssiRefNumber : "+ssiRefNumber);
			RpDAO rpDAO1 = new RpDAO();
			double prevTotalSancAmt = rpDAO1.getTotalSanctionedAmountNew(ssiRefNumber);
			// System.out.println("ADAO prevTotalSancAmt : "+prevTotalSancAmt);
			ApplicationDAO appdao = new ApplicationDAO();

			double prevTotalHandloomSancAmt = appdao.getTotalSanctionedHandloomAmountNew(ssiRefNumber);
			// System.out.println("ADAO prevTotalHandloomSancAmt :
			// "+prevTotalHandloomSancAmt);
			double currentCreditAmount = 0.0D;
			if (app.getLoanType().equals("TC")) {
				currentCreditAmount = app.getTermLoan().getCreditGuaranteed();
				// System.out.println("ADAO currentCreditAmount TC : "+currentCreditAmount);
			} else if (app.getLoanType().equals("CC")) {
				currentCreditAmount = app.getTermLoan().getCreditGuaranteed() + app.getWc().getCreditFundBased()
						+ app.getWc().getCreditNonFundBased();
				// System.out.println("ADAO currentCreditAmount CC : "+currentCreditAmount);
			} else if (app.getLoanType().equals("WC")) {
				currentCreditAmount = app.getWc().getEnhancedFundBased() + app.getWc().getEnhancedNonFundBased();
				// System.out.println("ADAO currentCreditAmount WC : "+currentCreditAmount);
				// System.out.println("ADAO currentCreditAmount+prevTotalHandloomSancAmt :
				// "+currentCreditAmount+prevTotalHandloomSancAmt);
			}

			if ((currentCreditAmount > 200000.0D) && (app.getDcHandlooms().equals("Y"))) {
				throw new DatabaseException(
						"Credit to be Guaranteed Amount should be within the eligible amount available for Guarantee upto  Rs. 200000 as per ceiling fixed by Office of DC Handlooms");
			}
			if ((currentCreditAmount + prevTotalHandloomSancAmt > 200000.0D) && (app.getDcHandlooms().equals("Y"))) {
				throw new DatabaseException( // 10-03-2017 done on the suggestion of vinay
						"Guarantee of Rs. " + prevTotalHandloomSancAmt
								+ " is already available for the Borrower. Total Credit to be Guaranteed Amount should be within the eligible amount available for Guarantee upto Rs. 200000 as per ceiling fixed by Office of DC Handlooms");
			}
			// System.out.println("ADAO currentCreditAmount + prevTotalSancAmt :
			// "+currentCreditAmount + prevTotalSancAmt);
			// if (currentCreditAmount + prevTotalSancAmt > 10000000.0D) { // 1 to 2 cr
			// changes
			if (currentCreditAmount + prevTotalSancAmt > 50000000.0D) {
				// throw new DatabaseException(
				// " Guarantee of Rs. "
				// + prevTotalSancAmt
				// + " is already available for the Borrower. Total Credit to be Guaranteed
				// Amount should be within the eligible amount available for Guarantee upto"
				// + 20000000);
			}

			// bhu update here primarysecurityDetails
			updateApplicationDtl(app, createdBy, connection);
			updateGuarantorDtls(app, createdBy, connection);
			updateTermLoanDtl(app, createdBy, connection);
			updateWCDtl(app, createdBy, connection);
			updateSecDetails(app, createdBy, connection);

			updateAadharDetailsRenew(app, connection); // Added by dKR

			MCGSProcessor mcgsProcessor = new MCGSProcessor();
			MCGFDetails mcgfDetails = app.getMCGFDetails();
			if (mcgfDetails != null) {
				mcgfDetails.setApplicationReferenceNumber(appRefNo);
				mcgsProcessor.updateMCGSDetails(mcgfDetails, createdBy, connection);
			}

			CallableStatement updateWEnhanceDtl = connection
					.prepareCall("{?=call packWCEnhancement.fucInsertWCEnhancement(?,?,?,?,?,?,?,?)}");

			updateWEnhanceDtl.registerOutParameter(1, 4);
			updateWEnhanceDtl.registerOutParameter(9, 12);

			updateWEnhanceDtl.setString(2, appRefNo);
			Log.log(5, "ApplicationDAO", "storeWcEnhancement", "app ref no :" + appRefNo);
			updateWEnhanceDtl.setDouble(3, app.getWc().getEnhancedFundBased());
			Log.log(5, "ApplicationDAO", "storeWcEnhancement", "fund based :" + app.getWc().getEnhancedFundBased());

			if (app.getWc().getEnhancedNonFundBased() == 0.0D) {
				updateWEnhanceDtl.setDouble(4, 0.0D);
			} else {
				updateWEnhanceDtl.setDouble(4, app.getWc().getEnhancedNonFundBased());
			}
			Log.log(5, "ApplicationDAO", "storeWcEnhancement",
					"fund non based :" + app.getWc().getEnhancedNonFundBased());
			updateWEnhanceDtl.setDate(5, new java.sql.Date(app.getWc().getEnhancementDate().getTime()));
			Log.log(5, "ApplicationDAO", "storeWcEnhancement",
					"date:" + new java.sql.Date(app.getWc().getEnhancementDate().getTime()));
			updateWEnhanceDtl.setDouble(6, app.getWc().getEnhancedFBInterest());
			Log.log(5, "ApplicationDAO", "storeWcEnhancement", "interest:" + app.getWc().getEnhancedFBInterest());

			if (app.getWc().getEnhancedNFBComission() == 0.0D) {
				updateWEnhanceDtl.setDouble(7, 0.0D);
			} else {
				updateWEnhanceDtl.setDouble(7, app.getWc().getEnhancedNFBComission());
			}
			Log.log(5, "ApplicationDAO", "storeWcEnhancement", "commission:" + app.getWc().getEnhancedNFBComission());

			updateWEnhanceDtl.setString(8, app.getWc().getWcInterestType());
			Log.log(5, "ApplicationDAO", "storeWcEnhancement", "Interest Type:" + app.getWc().getWcInterestType());

			updateWEnhanceDtl.executeQuery();
			int updateWEnhanceDtlValue = updateWEnhanceDtl.getInt(1);

			Log.log(5, "ApplicationDAO", "storeWcEnhancement", "WC Details result :" + updateWEnhanceDtlValue);

			if (updateWEnhanceDtlValue == 1) {
				String error = updateWEnhanceDtl.getString(9);

				updateWEnhanceDtl.close();
				updateWEnhanceDtl = null;

				connection.rollback();

				throw new DatabaseException(error);
			}
			updateWEnhanceDtl.close();
			updateWEnhanceDtl = null;

			// koteswar start for capturing full 22 may 2015 enhancement details

			CallableStatement updateWEnhanceFullDtl = connection
					.prepareCall("{?=call fucInsertWCEnhancementFulDet(?,?,?,?,?,?,?,?,?,?)}");

			updateWEnhanceFullDtl.registerOutParameter(1, 4);
			updateWEnhanceFullDtl.registerOutParameter(11, 12);

			updateWEnhanceFullDtl.setString(2, appRefNo);
			Log.log(5, "ApplicationDAO", "storeWcEnhancement", "app ref no :" + appRefNo);
			updateWEnhanceFullDtl.setDouble(3, app.getWc().getEnhancedFundBased());
			Log.log(5, "ApplicationDAO", "storeWcEnhancement", "fund based :" + app.getWc().getEnhancedFundBased());

			if (app.getWc().getEnhancedNonFundBased() == 0.0D) {
				updateWEnhanceFullDtl.setDouble(4, 0.0D);
			} else {
				updateWEnhanceFullDtl.setDouble(4, app.getWc().getEnhancedNonFundBased());
			}
			Log.log(5, "ApplicationDAO", "storeWcEnhancement",
					"fund non based :" + app.getWc().getEnhancedNonFundBased());
			updateWEnhanceFullDtl.setDate(5, new java.sql.Date(app.getWc().getEnhancementDate().getTime()));
			Log.log(5, "ApplicationDAO", "storeWcEnhancement",
					"date:" + new java.sql.Date(app.getWc().getEnhancementDate().getTime()));
			updateWEnhanceFullDtl.setDouble(6, app.getWc().getEnhancedFBInterest());
			Log.log(5, "ApplicationDAO", "storeWcEnhancement", "interest:" + app.getWc().getEnhancedFBInterest());

			if (app.getWc().getEnhancedNFBComission() == 0.0D) {
				updateWEnhanceFullDtl.setDouble(7, 0.0D);
			} else {
				updateWEnhanceFullDtl.setDouble(7, app.getWc().getEnhancedNFBComission());
			}
			Log.log(5, "ApplicationDAO", "storeWcEnhancement", "commission:" + app.getWc().getEnhancedNFBComission());

			updateWEnhanceFullDtl.setString(8, app.getWc().getWcInterestType());
			Log.log(5, "ApplicationDAO", "storeWcEnhancement", "Interest Type:" + app.getWc().getWcInterestType());

			// wcFundBasedSanctionedVal

			if (app.getWc().getCreditFundBased() == 0.0D) {
				updateWEnhanceFullDtl.setDouble(9, 0.0D);
			} else {
				updateWEnhanceFullDtl.setDouble(9, app.getWc().getCreditFundBased());
			}

			if (app.getWc().getCreditNonFundBased() == 0.0D) {
				updateWEnhanceFullDtl.setDouble(10, 0.0D);
			} else {
				updateWEnhanceFullDtl.setDouble(10, app.getWc().getCreditNonFundBased());
			}

			updateWEnhanceFullDtl.executeQuery();
			int updateWEnhanceDtlValue1 = updateWEnhanceFullDtl.getInt(1);

			Log.log(5, "ApplicationDAO", "storeWcEnhancement", "WC Details result :" + updateWEnhanceDtlValue1);

			if (updateWEnhanceDtlValue1 == 1) {
				String error = updateWEnhanceFullDtl.getString(11);

				updateWEnhanceFullDtl.close();
				updateWEnhanceFullDtl = null;

				connection.rollback();

				throw new DatabaseException(error);
			}
			updateWEnhanceFullDtl.close();
			updateWEnhanceFullDtl = null;

			connection.commit();
		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "storeWcEnhancement", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(4, "ApplicationDAO", "storeWcEnhancement", ignore.getMessage());
			}

			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}
	}

	// Aadhar updated by DKR
	public String storeWcRenewal(Application app, String createdBy) throws DatabaseException, MessageException {
		Connection connection = DBConnection.getConnection(false);

		String appRefNo = "";
		try {
			MCGSProcessor mcgsProcessor = new MCGSProcessor();
			RiskManagementProcessor rpProcessor = new RiskManagementProcessor();

			updateAadharDetailsRenew(app, connection); // UPDATE AADHAER

			Log.log(4, "ApplicationDAO", "storeWcRenewal", "Entered");

			Log.log(4, "ApplicationDAO", "storeWcRenewal", "before submitting application details");
			String subSchemeName = rpProcessor.getSubScheme(app);
			app.setSubSchemeName(subSchemeName);

			double balanceAppAmt = getBalanceApprovedAmt(app);

			int ssiRefNo = app.getBorrowerDetails().getSsiDetails().getBorrowerRefNo();
			String ssiRefNumber = Integer.toString(ssiRefNo);
			RpDAO rpDAO1 = new RpDAO();
			double prevTotalSancAmt = rpDAO1.getTotalSanctionedAmountNew(ssiRefNumber);
			ApplicationDAO appdao = new ApplicationDAO();
			double prevTotalHandloomSancAmt = appdao.getTotalSanctionedHandloomAmountNew(ssiRefNumber);

			if ((prevTotalHandloomSancAmt > 200000.0D) && (app.getDcHandlooms().equals("Y"))) {
				throw new DatabaseException("Guarantee of Rs. " + prevTotalHandloomSancAmt
						+ " is already available for the Borrower. Total Credit to be Guaranteed Amount should be within the eligible amount available for Guarantee upto Rs. 200000 as per ceiling fixed by Office of DC Handlooms");
			}

			if ((app.getLoanType().equals("TC")) || (app.getLoanType().equals("CC"))) {
				if (app.getTermLoan().getCreditGuaranteed() > balanceAppAmt) {
					throw new DatabaseException(
							"Credit to be Guaranteed Amount should be within the eligible amount available for Guarantee :"
									+ balanceAppAmt);
				}
			} else if (app.getLoanType().equals("WC")) {
				balanceAppAmt += app.getWc().getCreditFundBased() + app.getWc().getCreditNonFundBased();
				if (app.getWc().getCreditFundBased() + app.getWc().getCreditNonFundBased() > balanceAppAmt) {
					throw new DatabaseException(
							"Credit to be Guaranteed Amount should be within the eligible amount available for Guarantee :"
									+ balanceAppAmt);
				}
			} else if (app.getLoanType().equals("BO")) {
				if (app.getTermLoan().getCreditGuaranteed() + app.getWc().getCreditFundBased()
						+ app.getWc().getCreditNonFundBased() > balanceAppAmt) {
					throw new DatabaseException(
							"Credit to be Guaranteed Amount should be within the eligible amount available for Guarantee :"
									+ balanceAppAmt);
				}

			}

			appRefNo = submitApplicationDetails(app, createdBy, connection);
			Log.log(4, "ApplicationDAO", "storeWcRenewal", "after submitting application details");
			Log.log(4, "ApplicationDAO", "storeWcRenewal", "renewal app ref no :" + appRefNo);
			app.setAppRefNo(appRefNo);
			submitGuarantorSecurityDetails(app, connection);
			submitTermCreditDetails(app, createdBy, connection);

			WorkingCapital tempWc = app.getWc();

			Administrator admin = new Administrator();
			ParameterMaster param = admin.getParameter();
			WcValidateBean wcValidateBean = null;
			/*
			 * tempWc.setWcTenure(param.getWcTenorInYrs() * 12);
			 * System.out.println(""+param.getWcTenorInYrs()); app.setWc(tempWc);
			 */
			int tcRemainExpMonth = 0;
			int wcRemainExpMonth = 0;
			// ********************** 10Y added by DKR
			// *****************************************
			String cgpann = app.getCgpan();
			wcValidateBean = getBothTCDetail(cgpann, connection);
			if (app.getLoanType().equals("WC")
					|| (wcValidateBean.getAppLoanType().equals("CC") || wcValidateBean.getAppLoanType().equals("BO"))) {
				tcRemainExpMonth = ApplicationRenewValidator.validateRenewal(wcValidateBean);
				System.out.println(tcRemainExpMonth + "............0......tcRemainExpMonth");
				wcValidateBean = null;
				System.out.println(wcValidateBean);
				wcValidateBean = new WcValidateBean();
				wcValidateBean = getCgpanWithDate(app.getBankId() + "" + app.getZoneId() + "" + app.getBranchId(),
						app.getCgpan(), "CGPAN");
				if (wcValidateBean != null)
					wcRemainExpMonth = ApplicationRenewValidator.validateRenewal(wcValidateBean);
				if (wcRemainExpMonth > 0) {
					wcRemainExpMonth = wcRemainExpMonth + tcRemainExpMonth;

					System.out.println(wcRemainExpMonth + "..............1........wcRemainExpMonth");
					System.out.println(
							param.getWcTenorInYrs() * 12 + "..........2............param.getWcTenorInYrs() * 12");
					System.out.println(wcRemainExpMonth / 12 + "................3......wcRemainExpMonth / 12");
					System.out.println(wcRemainExpMonth + "...............4.......wcRemainExpMonth");

					tempWc.setWcTenure((param.getWcTenorInYrs() * 12) + (Math.round(wcRemainExpMonth / 12)));

					System.out.println(tempWc.getWcTenure() + "...............5.......tempWc.getWcTenure()");

					app.setWc(tempWc);
					System.out.println("Now, application renewal period remain only for" + app.getWc() + " months.");
				}
			} else {
				tempWc.setWcTenure(param.getWcTenorInYrs() * 12);
				tempWc.getWcTenure();
				System.out.println("" + param.getWcTenorInYrs() + "raaaa" + tempWc.getWcTenure());
				app.setWc(tempWc);
			}

			submitWCDetailsNew(app, createdBy, connection);
			submitSecDetails(app, connection);
			/*
			 * tempWc.setWcTenure(param.getWcTenorInYrs() * 12);
			 * 
			 * app.setWc(tempWc);
			 * 
			 * submitWCDetailsNew(app, createdBy, connection); submitSecDetails(app,
			 * connection);
			 */
			if (app.getMCGFDetails() != null) {
				MCGFDetails mcgfDetails = app.getMCGFDetails();
				mcgfDetails.setApplicationReferenceNumber(appRefNo);
				app.setMCGFDetails(mcgfDetails);
				mcgsProcessor.updateMCGSDetails(mcgfDetails, createdBy, connection);
			}

			connection.commit();
		} catch (SQLException exception) {
			Log.log(4, "ApplicationDAO", "submitApplicationDetails", exception.getMessage());
			Log.logException(exception);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(4, "ApplicationDAO", "submitApplicationDetails", ignore.getMessage());
			}

			throw new DatabaseException(exception.getMessage());
		} finally {
			DBConnection.freeConnection(connection);

		}
		Log.log(4, "ApplicationDAO", "storeWcRenewal", "Exited");

		return appRefNo;
	}

	public void uploadApplications(ArrayList apps) {
	}

	public EligibilityCriteria getEligibilityCriteria(SubScheme subscheme) throws DatabaseException {
		return null;
	}

	public boolean isAppLive(String cgpan) {
		return false;
	}

	public HashMap checkDuplicatePath(String bankName) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "checkDuplicatePath", "Entered");
		Connection connection = DBConnection.getConnection(false);
		String bankId = "";
		String branchId = "";
		String zoneId = "";
		ArrayList tcApprovedAppList = null;
		ArrayList wcApprovedAppList = null;
		ArrayList tcPendingAppList = new ArrayList();
		ArrayList wcPendingAppList = new ArrayList();
		HashMap approvedPendingApplications = new HashMap();
		try {
			tcApprovedAppList = new ArrayList();
			wcApprovedAppList = new ArrayList();
			CallableStatement approvedApps = connection
					.prepareCall("{?=call packGetPackagePath.funcGetDuplicateAppPath(?,?,?)}");

			approvedApps.registerOutParameter(1, 4);
			approvedApps.registerOutParameter(2, -10);
			approvedApps.registerOutParameter(3, 12);
			approvedApps.setString(4, bankName);
			approvedApps.execute();
			int functionReturnValue = approvedApps.getInt(1);
			if (functionReturnValue == 1) {
				String error = approvedApps.getString(3);

				approvedApps.close();
				approvedApps = null;
				connection.rollback();
				throw new DatabaseException(error);
			}

			ResultSet approvedAppsResults = (ResultSet) approvedApps.getObject(2);
			Application aApplication = null;
			BorrowerDetails aBorrowerDetails = null;
			SSIDetails aSsiDetails = null;
			Log.log(4, "ApplicationDAO", "checkDuplicatePath", "Just Before ");
			int ritesh = 0;
			while (approvedAppsResults.next()) {
				aApplication = new Application();
				aBorrowerDetails = new BorrowerDetails();
				aSsiDetails = new SSIDetails();
				aApplication.setCgpan(approvedAppsResults.getString(1));
				aApplication.setAppRefNo(approvedAppsResults.getString(2));

				aSsiDetails.setCgbid(approvedAppsResults.getString(3));
				aSsiDetails.setSsiName(approvedAppsResults.getString(4));
				aSsiDetails.setRegNo(approvedAppsResults.getString(5));
				aSsiDetails.setCpFirstName(approvedAppsResults.getString(6));
				aSsiDetails.setCpMiddleName(approvedAppsResults.getString(7));
				aSsiDetails.setCpLastName(approvedAppsResults.getString(8));
				aSsiDetails.setAddress(approvedAppsResults.getString(9));
				aApplication.setBankId(approvedAppsResults.getString(10));
				aApplication.setZoneId("0000");
				aApplication.setBranchId("0000");
				aApplication.setLoanType(approvedAppsResults.getString(13));
				aSsiDetails.setCpITPAN(approvedAppsResults.getString(14));
				aSsiDetails.setState(approvedAppsResults.getString(15));
				aSsiDetails.setDistrict(approvedAppsResults.getString(16));
				aApplication.setMliRefNo(approvedAppsResults.getString(17));
				aBorrowerDetails.setSsiDetails(aSsiDetails);
				aApplication.setBorrowerDetails(aBorrowerDetails);
				aApplication.setExistSSI(approvedAppsResults.getString(18));
				if ((approvedAppsResults.getString(13).equals("TC"))
						|| (approvedAppsResults.getString(13).equals("CC"))) {
					tcApprovedAppList.add(aApplication);
				} else if (approvedAppsResults.getString(13).equals("WC")) {
					wcApprovedAppList.add(aApplication);
				}
			}

			Log.log(4, "ApplicationDAO", "checkDuplicatePath", "After");
			approvedAppsResults.close();
			aApplication = null;
			aBorrowerDetails = null;
			aSsiDetails = null;
			approvedAppsResults = null;
			approvedApps.close();
			approvedApps = null;

			Log.log(5, "ApplicationDAO", "checkDuplicatePath", "After getting all approved applications");

			CallableStatement pendingApps = connection
					.prepareCall("{?=call packGetPackagePath.funcGetAllAppPath(?,?,?)}");

			pendingApps.registerOutParameter(1, 4);
			pendingApps.registerOutParameter(2, -10);
			pendingApps.registerOutParameter(3, 12);
			pendingApps.setString(4, bankName);
			pendingApps.execute();
			int functionReturnValues = pendingApps.getInt(1);
			if (functionReturnValues == 1) {
				String error = pendingApps.getString(3);
				pendingApps.close();
				pendingApps = null;
				connection.rollback();
				throw new DatabaseException(error);
			}

			ResultSet pendingAppsResults = (ResultSet) pendingApps.getObject(2);
			Application pApplication = null;
			BorrowerDetails pBorrowerDetails = null;
			SSIDetails pSsiDetails = null;
			while (pendingAppsResults.next()) {
				pApplication = new Application();
				pBorrowerDetails = new BorrowerDetails();
				pSsiDetails = new SSIDetails();
				if ((pendingAppsResults.getString(1) != null) && (!pendingAppsResults.getString(1).equals(""))) {
					pApplication.setCgpan(pendingAppsResults.getString(1));
				}
				pApplication.setAppRefNo(pendingAppsResults.getString(2));
				pSsiDetails.setCgbid(pendingAppsResults.getString(3));
				pSsiDetails.setSsiName(pendingAppsResults.getString(4));
				pSsiDetails.setRegNo(pendingAppsResults.getString(5));
				pSsiDetails.setCpFirstName(pendingAppsResults.getString(6));
				pSsiDetails.setCpMiddleName(pendingAppsResults.getString(7));
				pSsiDetails.setCpLastName(pendingAppsResults.getString(8));
				pSsiDetails.setAddress(pendingAppsResults.getString(9));
				pApplication.setBankId(pendingAppsResults.getString(10));
				pApplication.setZoneId("0000");
				pApplication.setBranchId("0000");
				pApplication.setLoanType(pendingAppsResults.getString(13));
				pSsiDetails.setCpITPAN(pendingAppsResults.getString(14));
				pSsiDetails.setState(pendingAppsResults.getString(15));
				pSsiDetails.setDistrict(pendingAppsResults.getString(16));
				pApplication.setMliRefNo(pendingAppsResults.getString(17));
				pApplication.setPrevSSI(pendingAppsResults.getString(18));
				pBorrowerDetails.setSsiDetails(pSsiDetails);
				pApplication.setBorrowerDetails(pBorrowerDetails);
				if ((pendingAppsResults.getString(13).equals("TC"))
						|| (pendingAppsResults.getString(13).equals("CC"))) {
					tcPendingAppList.add(pApplication);
				} else if (pendingAppsResults.getString(13).equals("WC")) {
					wcPendingAppList.add(pApplication);
				}
			}
			pendingAppsResults.close();
			pendingAppsResults = null;
			pApplication = null;
			pBorrowerDetails = null;
			pSsiDetails = null;
			pendingApps.close();
			pendingApps = null;

			CallableStatement pendingStatusApps = connection
					.prepareCall("{?=call packGetPackagePath.funcGetPendingStatusPath(?,?,?)}");

			pendingStatusApps.registerOutParameter(1, 4);
			pendingStatusApps.registerOutParameter(2, -10);
			pendingStatusApps.registerOutParameter(3, 12);
			pendingStatusApps.setString(4, bankName);
			pendingStatusApps.execute();
			int functionReturnVal = pendingStatusApps.getInt(1);
			if (functionReturnVal == 1) {
				String error = pendingStatusApps.getString(3);
				pendingStatusApps.close();
				pendingStatusApps = null;
				connection.rollback();
				throw new DatabaseException(error);
			}

			ResultSet pendingAppsStatusResults = (ResultSet) pendingStatusApps.getObject(2);
			// Application pApplication = null;
			// BorrowerDetails pBorrowerDetails = null;
			// SSIDetails pSsiDetails = null;
			while (pendingAppsStatusResults.next()) {
				pApplication = new Application();
				pBorrowerDetails = new BorrowerDetails();
				pSsiDetails = new SSIDetails();
				pApplication.setAppRefNo(pendingAppsStatusResults.getString(2));
				pSsiDetails.setCgbid(pendingAppsStatusResults.getString(3));
				pSsiDetails.setSsiName(pendingAppsStatusResults.getString(4));
				pSsiDetails.setRegNo(pendingAppsStatusResults.getString(5));
				pSsiDetails.setCpFirstName(pendingAppsStatusResults.getString(6));
				pSsiDetails.setCpMiddleName(pendingAppsStatusResults.getString(7));
				pSsiDetails.setCpLastName(pendingAppsStatusResults.getString(8));
				pSsiDetails.setAddress(pendingAppsStatusResults.getString(9));
				pApplication.setBankId(pendingAppsStatusResults.getString(10));
				pApplication.setZoneId("0000");
				pApplication.setBranchId("0000");
				pApplication.setLoanType(pendingAppsStatusResults.getString(13));
				pSsiDetails.setCpITPAN(pendingAppsStatusResults.getString(14));
				pSsiDetails.setState(pendingAppsStatusResults.getString(15));
				pSsiDetails.setDistrict(pendingAppsStatusResults.getString(16));
				pApplication.setMliRefNo(pendingAppsStatusResults.getString(17));
				pApplication.setPrevSSI(pendingAppsStatusResults.getString(18));
				pBorrowerDetails.setSsiDetails(pSsiDetails);
				pApplication.setBorrowerDetails(pBorrowerDetails);
				if ((pendingAppsStatusResults.getString(13).equals("TC"))
						|| (pendingAppsStatusResults.getString(13).equals("CC"))) {
					tcPendingAppList.add(pApplication);
				} else if (pendingAppsStatusResults.getString(13).equals("WC")) {
					wcPendingAppList.add(pApplication);
				}
			}
			pendingAppsStatusResults.close();
			pendingAppsStatusResults = null;
			pApplication = null;
			pBorrowerDetails = null;
			pSsiDetails = null;
			pendingStatusApps.close();
			pendingStatusApps = null;

			HashMap tcApprovedApplications = new HashMap();
			HashMap wcApprovedApplications = new HashMap();
			HashMap tcPendingApplications = new HashMap();
			HashMap wcPendingApplications = new HashMap();

			ArrayList tcApprovedApplicationsList = new ArrayList();
			ArrayList wcApprovedApplicationsList = new ArrayList();
			ArrayList tcPendingApplicationsList = new ArrayList();
			ArrayList wcPendingApplicationsList = new ArrayList();

			int tcApprovedListSize = tcApprovedAppList.size();
			int wcApprovedListSize = wcApprovedAppList.size();

			int tcPendingListSize = tcPendingAppList.size();
			int wcPendingListSize = wcPendingAppList.size();

			for (int i = 0; i < tcApprovedListSize; i++) {
				Application tcApprovedApplication = (Application) tcApprovedAppList.get(i);
				bankId = tcApprovedApplication.getBankId();
				zoneId = tcApprovedApplication.getZoneId();
				branchId = tcApprovedApplication.getBranchId();
				String mliId = bankId + zoneId + branchId;
				String mliIdString = new String(mliId);
				if (tcApprovedApplications.containsKey(mliIdString)) {
					tcApprovedApplicationsList = (ArrayList) tcApprovedApplications.get(mliIdString);
				} else {
					tcApprovedApplicationsList = new ArrayList();
				}
				tcApprovedApplicationsList.add(tcApprovedApplication);
				tcApprovedApplications.put(mliIdString, tcApprovedApplicationsList);
			}

			for (int j = 0; j < wcApprovedListSize; j++) {
				Application wcApprovedApplication = (Application) wcApprovedAppList.get(j);
				bankId = wcApprovedApplication.getBankId();
				zoneId = wcApprovedApplication.getZoneId();
				branchId = wcApprovedApplication.getBranchId();
				String mliId = bankId + zoneId + branchId;
				String mliIdString = new String(mliId);
				if (wcApprovedApplications.containsKey(mliIdString)) {
					wcApprovedApplicationsList = (ArrayList) wcApprovedApplications.get(mliIdString);
				} else {
					wcApprovedApplicationsList = new ArrayList();
				}
				wcApprovedApplicationsList.add(wcApprovedApplication);
				wcApprovedApplications.put(mliIdString, wcApprovedApplicationsList);
			}

			for (int k = 0; k < tcPendingListSize; k++) {
				Application tcPendingApplication = (Application) tcPendingAppList.get(k);

				bankId = tcPendingApplication.getBankId();
				zoneId = tcPendingApplication.getZoneId();
				branchId = tcPendingApplication.getBranchId();
				String mliId = bankId + zoneId + branchId;
				String mliIdString = new String(mliId);
				if (tcPendingApplications.containsKey(mliIdString)) {
					tcPendingApplicationsList = (ArrayList) tcPendingApplications.get(mliIdString);
				} else {
					tcPendingApplicationsList = new ArrayList();
				}
				tcPendingApplicationsList.add(tcPendingApplication);
				tcPendingApplications.put(mliIdString, tcPendingApplicationsList);
			}

			for (int l = 0; l < wcPendingListSize; l++) {
				Application wcPendingApplication = (Application) wcPendingAppList.get(l);
				bankId = wcPendingApplication.getBankId();
				zoneId = wcPendingApplication.getZoneId();
				branchId = wcPendingApplication.getBranchId();
				String mliId = bankId + zoneId + branchId;
				String mliIdString = new String(mliId);
				if (wcPendingApplications.containsKey(mliIdString)) {
					wcPendingApplicationsList = (ArrayList) wcPendingApplications.get(mliIdString);
				} else {
					wcPendingApplicationsList = new ArrayList();
				}
				wcPendingApplicationsList.add(wcPendingApplication);
				wcPendingApplications.put(mliIdString, wcPendingApplicationsList);
			}
			approvedPendingApplications.put("tcApproved", tcApprovedApplications);
			approvedPendingApplications.put("wcApproved", wcApprovedApplications);
			approvedPendingApplications.put("tcPending", tcPendingApplications);
			approvedPendingApplications.put("wcPending", wcPendingApplications);

			tcApprovedApplications = null;
			wcApprovedApplications = null;
			tcPendingApplications = null;
			wcPendingApplications = null;
			tcApprovedApplicationsList = null;
			wcApprovedApplicationsList = null;
			tcPendingApplicationsList = null;
			wcPendingApplicationsList = null;
			bankId = null;
			zoneId = null;
			branchId = null;
			tcApprovedAppList = null;
			wcApprovedAppList = null;
			tcPendingAppList = null;
			wcPendingAppList = null;
			connection.commit();
		} catch (SQLException sqlException) {
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(4, "ApplicationDAO", "checkDuplicatePath", "Exception :" + ignore.getMessage());
			}
			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(4, "ApplicationDAO", "checkDuplicatePath", "Exited");

		return approvedPendingApplications;
	}

	public HashMap checkDuplicate() throws DatabaseException {
		Log.log(4, "ApplicationDAO", "checkDuplicate", "Entered");

		Connection connection = DBConnection.getConnection(false);

		String bankId = "";
		String branchId = "";
		String zoneId = "";

		ArrayList tcApprovedAppList = null;
		ArrayList wcApprovedAppList = null;

		ArrayList tcPendingAppList = new ArrayList();
		ArrayList wcPendingAppList = new ArrayList();

		HashMap approvedPendingApplications = new HashMap();
		try {
			CallableStatement applicationCount = connection
					.prepareCall("{?=call packGetAppCount.funcGetAppCount(?,?)}");

			applicationCount.registerOutParameter(1, 4);
			applicationCount.registerOutParameter(3, 12);

			applicationCount.registerOutParameter(2, -10);

			applicationCount.executeQuery();
			int applicationCountValue = applicationCount.getInt(1);

			Log.log(5, "ApplicationDAO", "checkDuplicate", "Application Count value :" + applicationCountValue);

			if (applicationCountValue == 1) {
				String error = applicationCount.getString(4);

				applicationCount.close();
				applicationCount = null;

				connection.rollback();

				Log.log(5, "ApplicationDAO", "checkDuplicate", "Application Count message:" + error);

				throw new DatabaseException(error);
			}

			ResultSet results = (ResultSet) applicationCount.getObject(2);

			int Tccount = 0;
			int Wccount = 0;

			while (results.next()) {
				if (results.getString(2).equals("WC")) {
					Wccount += results.getInt(1);
				} else {
					Tccount += results.getInt(1);
				}
			}

			results.close();
			applicationCount.close();
			applicationCount = null;

			Log.log(4, "ApplicationDAO", "checkDuplicate", "Tccount,Wccount " + Tccount + ", " + Wccount);

			tcApprovedAppList = new ArrayList(Tccount);
			wcApprovedAppList = new ArrayList(Wccount);
			CallableStatement approvedApps = connection
					.prepareCall("{?=call packGetApplications.funcGetApprovedApp(?,?)}");

			approvedApps.registerOutParameter(1, 4);
			approvedApps.registerOutParameter(2, -10);
			approvedApps.registerOutParameter(3, 12);

			approvedApps.execute();

			int functionReturnValue = approvedApps.getInt(1);
			if (functionReturnValue == 1) {
				String error = approvedApps.getString(3);

				approvedApps.close();
				approvedApps = null;

				connection.rollback();

				throw new DatabaseException(error);
			}

			ResultSet approvedAppsResults = (ResultSet) approvedApps.getObject(2);

			Application aApplication = null;
			BorrowerDetails aBorrowerDetails = null;
			SSIDetails aSsiDetails = null;
			Log.log(4, "ApplicationDAO", "checkDuplicate", "Just Before ");
			int ritu = 0;

			while (approvedAppsResults.next()) {
				ritu++;

				aApplication = new Application();
				aBorrowerDetails = new BorrowerDetails();
				aSsiDetails = new SSIDetails();

				aApplication.setCgpan(approvedAppsResults.getString(1));

				aApplication.setAppRefNo(approvedAppsResults.getString(2));

				aSsiDetails.setCgbid(approvedAppsResults.getString(3));

				aSsiDetails.setSsiName(approvedAppsResults.getString(4));

				aSsiDetails.setRegNo(approvedAppsResults.getString(5));

				aSsiDetails.setCpFirstName(approvedAppsResults.getString(6));

				aSsiDetails.setCpMiddleName(approvedAppsResults.getString(7));

				aSsiDetails.setCpLastName(approvedAppsResults.getString(8));

				aSsiDetails.setAddress(approvedAppsResults.getString(9));

				aApplication.setBankId(approvedAppsResults.getString(10));

				aApplication.setZoneId("0000");
				aApplication.setBranchId("0000");
				aApplication.setLoanType(approvedAppsResults.getString(13));

				aSsiDetails.setCpITPAN(approvedAppsResults.getString(14));

				aSsiDetails.setState(approvedAppsResults.getString(15));

				aSsiDetails.setDistrict(approvedAppsResults.getString(16));

				aApplication.setMliRefNo(approvedAppsResults.getString(17));
				aApplication.setPrevSSI(approvedAppsResults.getString(18));

				// System.out.println(" aApplicat123ion.setPrevSSI"
				// + aApplication.getPrevSSI());
				aBorrowerDetails.setSsiDetails(aSsiDetails);
				aApplication.setBorrowerDetails(aBorrowerDetails);

				Log.log(4, "ApplicationDAO", "checkDuplicate", "approvedAppsResults" + approvedAppsResults);
				if ((approvedAppsResults.getString(13).equals("TC"))
						|| (approvedAppsResults.getString(13).equals("CC"))) {
					tcApprovedAppList.add(aApplication);
				} else if (approvedAppsResults.getString(13).equals("WC")) {
					wcApprovedAppList.add(aApplication);
				}

			}

			Log.log(4, "ApplicationDAO", "checkDuplicate", "After");
			approvedAppsResults.close();
			aApplication = null;
			aBorrowerDetails = null;
			aSsiDetails = null;
			approvedAppsResults = null;
			approvedApps.close();
			approvedApps = null;

			Log.log(5, "ApplicationDAO", "checkDuplicate", "After getting all approved applications");
			CallableStatement pendingApps = connection
					.prepareCall("{?=call packGetApplications.funcGetPendingApp(?,?)}");

			pendingApps.registerOutParameter(1, 4);
			pendingApps.registerOutParameter(2, -10);
			pendingApps.registerOutParameter(3, 12);

			pendingApps.execute();

			int functionReturnValues = pendingApps.getInt(1);
			if (functionReturnValues == 1) {
				String error = pendingApps.getString(3);

				pendingApps.close();
				pendingApps = null;

				connection.rollback();

				throw new DatabaseException(error);
			}
			ResultSet pendingAppsResults = (ResultSet) pendingApps.getObject(2);
			Application pApplication = null;
			BorrowerDetails pBorrowerDetails = null;
			SSIDetails pSsiDetails = null;
			int ritu1 = 0;
			while (pendingAppsResults.next()) {
				ritu1++;
				pApplication = new Application();
				pBorrowerDetails = new BorrowerDetails();
				pSsiDetails = new SSIDetails();
				if ((pendingAppsResults.getString(1) != null) && (!pendingAppsResults.getString(1).equals(""))) {
					pApplication.setCgpan(pendingAppsResults.getString(1));
				}
				pApplication.setAppRefNo(pendingAppsResults.getString(2));
				pSsiDetails.setCgbid(pendingAppsResults.getString(3));
				pSsiDetails.setSsiName(pendingAppsResults.getString(4));
				pSsiDetails.setRegNo(pendingAppsResults.getString(5));
				pSsiDetails.setCpFirstName(pendingAppsResults.getString(6));
				pSsiDetails.setCpMiddleName(pendingAppsResults.getString(7));
				pSsiDetails.setCpLastName(pendingAppsResults.getString(8));
				pSsiDetails.setAddress(pendingAppsResults.getString(9));
				pApplication.setBankId(pendingAppsResults.getString(10));
				pApplication.setZoneId("0000");
				pApplication.setBranchId("0000");
				pApplication.setLoanType(pendingAppsResults.getString(13));
				pSsiDetails.setCpITPAN(pendingAppsResults.getString(14));
				pSsiDetails.setState(pendingAppsResults.getString(15));
				pSsiDetails.setDistrict(pendingAppsResults.getString(16));
				pApplication.setMliRefNo(pendingAppsResults.getString(17));
				pApplication.setExistSSI(pendingAppsResults.getString(18));

				pBorrowerDetails.setSsiDetails(pSsiDetails);
				pApplication.setBorrowerDetails(pBorrowerDetails);
				if ((pendingAppsResults.getString(13).equals("TC"))
						|| (pendingAppsResults.getString(13).equals("CC"))) {
					tcPendingAppList.add(pApplication);
				} else if (pendingAppsResults.getString(13).equals("WC")) {
					wcPendingAppList.add(pApplication);
				}

			}

			pendingAppsResults.close();
			pendingAppsResults = null;
			pApplication = null;
			pBorrowerDetails = null;
			pSsiDetails = null;
			pendingApps.close();
			pendingApps = null;

			Log.log(5, "ApplicationDAO", "checkDuplicate", "After getting all pending applications");

			CallableStatement pendingStatusApps = connection
					.prepareCall("{?=call packGetApplications.funcGetPendingAppStatus(?,?)}");

			pendingStatusApps.registerOutParameter(1, 4);
			pendingStatusApps.registerOutParameter(2, -10);
			pendingStatusApps.registerOutParameter(3, 12);

			pendingStatusApps.execute();

			int functionReturnVal = pendingStatusApps.getInt(1);
			if (functionReturnVal == 1) {
				String error = pendingStatusApps.getString(3);

				pendingStatusApps.close();
				pendingStatusApps = null;

				connection.rollback();

				throw new DatabaseException(error);
			}
			ResultSet pendingAppsStatusResults = (ResultSet) pendingStatusApps.getObject(2);
			// Application pApplication = null;
			// BorrowerDetails pBorrowerDetails = null;
			// SSIDetails pSsiDetails = null;
			int ritu2 = 0;
			while (pendingAppsStatusResults.next()) {
				ritu2++;
				pApplication = new Application();
				pBorrowerDetails = new BorrowerDetails();
				pSsiDetails = new SSIDetails();
				pApplication.setAppRefNo(pendingAppsStatusResults.getString(2));
				pSsiDetails.setCgbid(pendingAppsStatusResults.getString(3));
				pSsiDetails.setSsiName(pendingAppsStatusResults.getString(4));
				pSsiDetails.setRegNo(pendingAppsStatusResults.getString(5));
				pSsiDetails.setCpFirstName(pendingAppsStatusResults.getString(6));
				pSsiDetails.setCpMiddleName(pendingAppsStatusResults.getString(7));
				pSsiDetails.setCpLastName(pendingAppsStatusResults.getString(8));
				pSsiDetails.setAddress(pendingAppsStatusResults.getString(9));
				pApplication.setBankId(pendingAppsStatusResults.getString(10));
				pApplication.setZoneId("0000");
				pApplication.setBranchId("0000");
				pApplication.setLoanType(pendingAppsStatusResults.getString(13));
				pSsiDetails.setCpITPAN(pendingAppsStatusResults.getString(14));
				pSsiDetails.setState(pendingAppsStatusResults.getString(15));
				pSsiDetails.setDistrict(pendingAppsStatusResults.getString(16));
				pApplication.setMliRefNo(pendingAppsStatusResults.getString(17));

				pBorrowerDetails.setSsiDetails(pSsiDetails);
				pApplication.setBorrowerDetails(pBorrowerDetails);

				if ((pendingAppsStatusResults.getString(13).equals("TC"))
						|| (pendingAppsStatusResults.getString(13).equals("CC"))) {
					tcPendingAppList.add(pApplication);
				} else if (pendingAppsStatusResults.getString(13).equals("WC")) {
					wcPendingAppList.add(pApplication);
				}

			}

			pendingAppsStatusResults.close();
			pendingAppsStatusResults = null;
			pApplication = null;
			pBorrowerDetails = null;
			pSsiDetails = null;
			pendingStatusApps.close();
			pendingStatusApps = null;

			Log.log(5, "ApplicationDAO", "checkDuplicate", "After getting all pending status");

			HashMap tcApprovedApplications = new HashMap();
			HashMap wcApprovedApplications = new HashMap();

			HashMap tcPendingApplications = new HashMap();
			HashMap wcPendingApplications = new HashMap();

			ArrayList tcApprovedApplicationsList = new ArrayList();
			ArrayList wcApprovedApplicationsList = new ArrayList();

			ArrayList tcPendingApplicationsList = new ArrayList();
			ArrayList wcPendingApplicationsList = new ArrayList();

			int tcApprovedListSize = tcApprovedAppList.size();
			int wcApprovedListSize = wcApprovedAppList.size();

			int tcPendingListSize = tcPendingAppList.size();
			int wcPendingListSize = wcPendingAppList.size();

			for (int i = 0; i < tcApprovedListSize; i++) {
				Application tcApprovedApplication = (Application) tcApprovedAppList.get(i);

				bankId = tcApprovedApplication.getBankId();
				zoneId = tcApprovedApplication.getZoneId();
				branchId = tcApprovedApplication.getBranchId();

				String mliId = bankId + zoneId + branchId;
				String mliIdString = new String(mliId);

				if (tcApprovedApplications.containsKey(mliIdString)) {
					tcApprovedApplicationsList = (ArrayList) tcApprovedApplications.get(mliIdString);
				} else {
					tcApprovedApplicationsList = new ArrayList();
				}

				tcApprovedApplicationsList.add(tcApprovedApplication);
				tcApprovedApplications.put(mliIdString, tcApprovedApplicationsList);
			}

			for (int j = 0; j < wcApprovedListSize; j++) {
				Application wcApprovedApplication = (Application) wcApprovedAppList.get(j);

				bankId = wcApprovedApplication.getBankId();
				zoneId = wcApprovedApplication.getZoneId();
				branchId = wcApprovedApplication.getBranchId();

				String mliId = bankId + zoneId + branchId;

				String mliIdString = new String(mliId);

				if (wcApprovedApplications.containsKey(mliIdString)) {
					wcApprovedApplicationsList = (ArrayList) wcApprovedApplications.get(mliIdString);
				} else {
					wcApprovedApplicationsList = new ArrayList();
				}

				wcApprovedApplicationsList.add(wcApprovedApplication);
				wcApprovedApplications.put(mliIdString, wcApprovedApplicationsList);
			}

			for (int k = 0; k < tcPendingListSize; k++) {
				Application tcPendingApplication = (Application) tcPendingAppList.get(k);

				bankId = tcPendingApplication.getBankId();
				zoneId = tcPendingApplication.getZoneId();
				branchId = tcPendingApplication.getBranchId();

				String mliId = bankId + zoneId + branchId;

				String mliIdString = new String(mliId);

				if (tcPendingApplications.containsKey(mliIdString)) {
					tcPendingApplicationsList = (ArrayList) tcPendingApplications.get(mliIdString);
				} else {
					tcPendingApplicationsList = new ArrayList();
				}

				tcPendingApplicationsList.add(tcPendingApplication);
				tcPendingApplications.put(mliIdString, tcPendingApplicationsList);
			}

			for (int l = 0; l < wcPendingListSize; l++) {
				Application wcPendingApplication = (Application) wcPendingAppList.get(l);

				bankId = wcPendingApplication.getBankId();
				zoneId = wcPendingApplication.getZoneId();
				branchId = wcPendingApplication.getBranchId();

				String mliId = bankId + zoneId + branchId;

				String mliIdString = new String(mliId);

				if (wcPendingApplications.containsKey(mliIdString)) {
					wcPendingApplicationsList = (ArrayList) wcPendingApplications.get(mliIdString);
				} else {
					wcPendingApplicationsList = new ArrayList();
				}

				wcPendingApplicationsList.add(wcPendingApplication);

				wcPendingApplications.put(mliIdString, wcPendingApplicationsList);
			}

			approvedPendingApplications.put("tcApproved", tcApprovedApplications);
			approvedPendingApplications.put("wcApproved", wcApprovedApplications);

			approvedPendingApplications.put("tcPending", tcPendingApplications);
			approvedPendingApplications.put("wcPending", wcPendingApplications);

			tcApprovedApplications = null;
			wcApprovedApplications = null;

			tcPendingApplications = null;
			wcPendingApplications = null;

			tcApprovedApplicationsList = null;
			wcApprovedApplicationsList = null;
			tcPendingApplicationsList = null;
			wcPendingApplicationsList = null;

			bankId = null;
			zoneId = null;
			branchId = null;
			tcApprovedAppList = null;
			wcApprovedAppList = null;
			tcPendingAppList = null;
			wcPendingAppList = null;

			connection.commit();
		} catch (SQLException sqlException) {
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(4, "ApplicationDAO", "checkDuplicate", "Exception :" + ignore.getMessage());
			}

			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(4, "ApplicationDAO", "checkDuplicate", "Exited");

		return approvedPendingApplications;
	}

	public Collection getPendingApps() throws DatabaseException {
		Connection connection = DBConnection.getConnection(false);

		ArrayList pendingAppsList = new ArrayList();
		try {
			CallableStatement pendingApps = connection
					.prepareCall("{?=call packGetApplications.funcGetPendingApp(?,?)}");

			pendingApps.registerOutParameter(1, 4);
			pendingApps.registerOutParameter(2, -10);
			pendingApps.registerOutParameter(3, 12);

			pendingApps.execute();

			int functionReturnValues = pendingApps.getInt(1);
			if (functionReturnValues == 1) {
				String error = pendingApps.getString(3);

				pendingApps.close();
				pendingApps = null;

				connection.rollback();

				throw new DatabaseException(error);
			}
			ResultSet pendingAppsResults = (ResultSet) pendingApps.getObject(2);
			while (pendingAppsResults.next()) {
				Application pApplication = new Application();
				BorrowerDetails pBorrowerDetails = new BorrowerDetails();
				SSIDetails pSsiDetails = new SSIDetails();
				pApplication.setAppRefNo(pendingAppsResults.getString(2));
				pSsiDetails.setCgbid(pendingAppsResults.getString(3));
				pSsiDetails.setSsiName(pendingAppsResults.getString(4));
				pSsiDetails.setRegNo(pendingAppsResults.getString(5));
				pSsiDetails.setCpFirstName(pendingAppsResults.getString(6));
				pSsiDetails.setCpMiddleName(pendingAppsResults.getString(7));
				pSsiDetails.setCpLastName(pendingAppsResults.getString(8));
				pSsiDetails.setAddress(pendingAppsResults.getString(9));

				pBorrowerDetails.setSsiDetails(pSsiDetails);
				pApplication.setBorrowerDetails(pBorrowerDetails);
				pendingAppsList.add(pApplication);
			}

			pendingAppsResults.close();
			pendingAppsResults = null;
			pendingApps.close();
			pendingApps = null;

			CallableStatement pendingStatusApps = connection
					.prepareCall("{?=call packGetApplications.funcGetPendingAppStatus(?,?)}");

			pendingStatusApps.registerOutParameter(1, 4);
			pendingStatusApps.registerOutParameter(2, -10);
			pendingStatusApps.registerOutParameter(3, 12);

			pendingStatusApps.execute();

			int functionReturnVal = pendingStatusApps.getInt(1);
			if (functionReturnVal == 1) {
				String error = pendingStatusApps.getString(3);

				pendingStatusApps.close();
				pendingStatusApps = null;

				connection.rollback();

				throw new DatabaseException(error);
			}
			ResultSet pendingAppsStatusResults = (ResultSet) pendingStatusApps.getObject(2);
			Application pApplication = null;
			BorrowerDetails pBorrowerDetails = null;
			SSIDetails pSsiDetails = null;
			while (pendingAppsStatusResults.next()) {
				pApplication = new Application();
				pBorrowerDetails = new BorrowerDetails();
				pSsiDetails = new SSIDetails();
				pApplication.setAppRefNo(pendingAppsStatusResults.getString(2));
				pSsiDetails.setCgbid(pendingAppsStatusResults.getString(3));
				pSsiDetails.setSsiName(pendingAppsStatusResults.getString(4));
				pSsiDetails.setRegNo(pendingAppsStatusResults.getString(5));
				pSsiDetails.setCpFirstName(pendingAppsStatusResults.getString(6));
				pSsiDetails.setCpMiddleName(pendingAppsStatusResults.getString(7));
				pSsiDetails.setCpLastName(pendingAppsStatusResults.getString(8));
				pSsiDetails.setAddress(pendingAppsStatusResults.getString(9));

				pBorrowerDetails.setSsiDetails(pSsiDetails);
				pApplication.setBorrowerDetails(pBorrowerDetails);
				pendingAppsList.add(pApplication);
			}

			pendingAppsStatusResults.close();
			pendingAppsStatusResults = null;
			pApplication = null;
			pBorrowerDetails = null;
			pSsiDetails = null;
			pendingStatusApps.close();
			pendingStatusApps = null;

			connection.commit();
		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "getPendingApps", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(4, "ApplicationDAO", "getPendingApps", ignore.getMessage());
			}

			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		return pendingAppsList;
	}

	public EligibleApplication getAppsForEligibilityCheck(String appRefNo) throws DatabaseException {
		Connection connection = DBConnection.getConnection(false);

		EligibleApplication eligibleApplication = new EligibleApplication();
		try {
			CallableStatement ineligibleApps = connection
					.prepareCall("{?=call funcEvaluateEligibilyCriteria(?,?,?,?,?)}");

			ineligibleApps.registerOutParameter(1, 4);
			ineligibleApps.registerOutParameter(6, 12);
			ineligibleApps.registerOutParameter(3, 12);
			ineligibleApps.registerOutParameter(4, 12);
			ineligibleApps.registerOutParameter(5, 12);

			ineligibleApps.setString(2, appRefNo);

			ineligibleApps.execute();

			int functionReturnValues = ineligibleApps.getInt(1);

			if (functionReturnValues == 1) {
				String error = ineligibleApps.getString(3);

				ineligibleApps.close();
				ineligibleApps = null;

				connection.rollback();

				throw new DatabaseException(error);
			}

			if (ineligibleApps.getString(3) != null) {
				eligibleApplication.setPassedCondition(ineligibleApps.getString(3).substring(1));
			} else {
				eligibleApplication.setPassedCondition("");
			}

			if (ineligibleApps.getString(4) != null) {
				eligibleApplication.setFailedCondition(ineligibleApps.getString(4).substring(1));
			} else {
				eligibleApplication.setFailedCondition("");
			}

			if (ineligibleApps.getString(5) != null) {
				eligibleApplication.setMessage(ineligibleApps.getString(5).substring(1));
			} else {
				eligibleApplication.setMessage("");
			}
			ineligibleApps.close();
			ineligibleApps = null;

			connection.commit();
		} catch (SQLException sqlException) {
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(4, "ApplicationDAO", "getAppsForEligibilityCheck", "Exception ;" + ignore.getMessage());
			}

			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		return eligibleApplication;
	}

	public void saveAuthorizedApps(ArrayList apps) throws DatabaseException {
	}

	public boolean moveAppToIntranet(Application apps) throws DatabaseException {
		return true;
	}

	public Application getPartApplicationPath(String mliID, String cgpan, String appRefNo)
			throws NoApplicationFoundException, DatabaseException {
		Application application = new Application();
		BorrowerDetails borrowerDetails = new BorrowerDetails();
		SSIDetails ssiDetails = new SSIDetails();
		ProjectOutlayDetails projectOutlayDetails = new ProjectOutlayDetails();

		Connection connection = DBConnection.getConnection(false);
		try {
			if (((mliID == null) && (!appRefNo.equals("")))
					|| ((mliID != null) && (!mliID.equals("")) && (!appRefNo.equals("")))) {
				application = getAppForAppRef(mliID, appRefNo);
			} else if (((mliID == null) && (!cgpan.equals("")))
					|| ((mliID != null) && (!mliID.equals("")) && (!cgpan.equals("")))) {
				application = getAppForCgpan(mliID, cgpan);
			}

			projectOutlayDetails = application.getProjectOutlayDetails();
			appRefNo = application.getAppRefNo();

			CallableStatement ssiObj = connection.prepareCall(
					"{?=call funcGetSSIDetailforAppRefPath(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			ssiObj.setString(2, appRefNo);

			ssiObj.registerOutParameter(1, 4);
			ssiObj.registerOutParameter(30, 12);

			ssiObj.registerOutParameter(3, 4);
			ssiObj.registerOutParameter(4, 12);
			ssiObj.registerOutParameter(5, 12);
			ssiObj.registerOutParameter(6, 12);
			ssiObj.registerOutParameter(7, 12);
			ssiObj.registerOutParameter(8, 12);
			ssiObj.registerOutParameter(9, 12);
			ssiObj.registerOutParameter(10, 12);
			ssiObj.registerOutParameter(11, 12);
			ssiObj.registerOutParameter(12, 91);
			ssiObj.registerOutParameter(13, 12);
			ssiObj.registerOutParameter(14, 12);
			ssiObj.registerOutParameter(15, 4);
			ssiObj.registerOutParameter(16, 8);
			ssiObj.registerOutParameter(17, 8);
			ssiObj.registerOutParameter(18, 12);
			ssiObj.registerOutParameter(19, 12);
			ssiObj.registerOutParameter(20, 12);
			ssiObj.registerOutParameter(21, 12);
			ssiObj.registerOutParameter(22, 12);
			ssiObj.registerOutParameter(23, 12);
			ssiObj.registerOutParameter(24, 12);
			ssiObj.registerOutParameter(25, 12);
			ssiObj.registerOutParameter(26, 12);
			ssiObj.registerOutParameter(27, 12);
			ssiObj.registerOutParameter(28, 8);
			ssiObj.registerOutParameter(29, 12);

			ssiObj.registerOutParameter(31, 12);

			ssiObj.executeQuery();
			int ssiObjValue = ssiObj.getInt(1);

			if (ssiObjValue == 1) {
				String error = ssiObj.getString(30);

				ssiObj.close();
				ssiObj = null;

				connection.rollback();

				throw new DatabaseException("Application does not exist");
			}
			ssiDetails.setBorrowerRefNo(ssiObj.getInt(3));
			borrowerDetails.setPreviouslyCovered(ssiObj.getString(4).trim());
			borrowerDetails.setAssistedByBank(ssiObj.getString(5).trim());
			borrowerDetails.setNpa(ssiObj.getString(7).trim());
			ssiDetails.setConstitution(ssiObj.getString(8));
			ssiDetails.setSsiType(ssiObj.getString(9).trim());
			ssiDetails.setSsiName(ssiObj.getString(10));
			ssiDetails.setRegNo(ssiObj.getString(11));
			ssiDetails.setSsiITPan(ssiObj.getString(13));
			ssiDetails.setActivityType(ssiObj.getString(14));
			ssiDetails.setEmployeeNos(ssiObj.getInt(15));
			ssiDetails.setProjectedSalesTurnover(ssiObj.getDouble(16));
			ssiDetails.setProjectedExports(ssiObj.getDouble(17));
			ssiDetails.setAddress(ssiObj.getString(18));
			ssiDetails.setCity(ssiObj.getString(19));
			ssiDetails.setPincode(ssiObj.getString(20).trim());
			ssiDetails.setDistrict(ssiObj.getString(22));
			ssiDetails.setState(ssiObj.getString(23));
			ssiDetails.setIndustryNature(ssiObj.getString(24));
			ssiDetails.setIndustrySector(ssiObj.getString(25));
			ssiDetails.setCgbid(ssiObj.getString(27));

			ssiDetails.setSancDate_new(ssiObj.getString(30));

			borrowerDetails.setOsAmt(ssiObj.getDouble(28));

			borrowerDetails.setSsiDetails(ssiDetails);
			application.setBorrowerDetails(borrowerDetails);
			ssiObj.close();
			ssiObj = null;

			ssiDetails = application.getBorrowerDetails().getSsiDetails();
			borrowerDetails.setSsiDetails(ssiDetails);
			application.setBorrowerDetails(borrowerDetails);
			ssiDetails = null;
			borrowerDetails = null;

			connection.commit();
		} catch (SQLException sqlException) {
			Log.log(2, "ApplicationDAO", "getPartApplicationPath", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(2, "ApplicationDAO", "getPartApplicationPath", ignore.getMessage());
			}

			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		return application;
	}

	public Application getPartApplication(String mliID, String cgpan, String appRefNo)
			throws NoApplicationFoundException, DatabaseException {
		Log.log(4, "ApplicationDAO", "getPartApplication", "Entered. Memory : " + Runtime.getRuntime().freeMemory());

		Application application = new Application();
		BorrowerDetails borrowerDetails = new BorrowerDetails();
		SSIDetails ssiDetails = new SSIDetails();
		ProjectOutlayDetails projectOutlayDetails = new ProjectOutlayDetails();

		Connection connection = DBConnection.getConnection(false);

		Log.log(5, "ApplicationDAO", "getPartApplication",
				"connection in Application DAO in get Part Application" + connection);

		Log.log(5, "ApplicationDAO", "getPartApplication", "After entering in the method");
		try {
			if (((mliID == null) && (!appRefNo.equals("")))
					|| ((mliID != null) && (!mliID.equals("")) && (!appRefNo.equals("")))) {
				Log.log(5, "ApplicationDAO", "getPartApplication", "when mliID and app ref No are entered");

				application = getAppForAppRef(mliID, appRefNo);
			} else if (((mliID == null) && (!cgpan.equals("")))
					|| ((mliID != null) && (!mliID.equals("")) && (!cgpan.equals("")))) {
				Log.log(5, "ApplicationDAO", "getPartApplication", "when mliID and cgpan are entered");
				application = getAppForCgpan(mliID, cgpan);
			}

			projectOutlayDetails = application.getProjectOutlayDetails();
			appRefNo = application.getAppRefNo();

			Log.log(5, "ApplicationDAO", "getPartApplication", "values for application object set successfully...");

			CallableStatement ssiObj = connection.prepareCall(
					"{?=call funcGetSSIDetailforAppRef(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			ssiObj.setString(2, appRefNo);

			ssiObj.registerOutParameter(1, 4);
			ssiObj.registerOutParameter(30, 12);

			ssiObj.registerOutParameter(3, 4);
			ssiObj.registerOutParameter(4, 12);
			ssiObj.registerOutParameter(5, 12);
			ssiObj.registerOutParameter(6, 12);
			ssiObj.registerOutParameter(7, 12);
			ssiObj.registerOutParameter(8, 12);
			ssiObj.registerOutParameter(9, 12);
			ssiObj.registerOutParameter(10, 12);
			ssiObj.registerOutParameter(11, 12);
			ssiObj.registerOutParameter(12, 91);
			ssiObj.registerOutParameter(13, 12);
			ssiObj.registerOutParameter(14, 12);
			ssiObj.registerOutParameter(15, 4);
			ssiObj.registerOutParameter(16, 8);
			ssiObj.registerOutParameter(17, 8);
			ssiObj.registerOutParameter(18, 12);
			ssiObj.registerOutParameter(19, 12);
			ssiObj.registerOutParameter(20, 12);
			ssiObj.registerOutParameter(21, 12);
			ssiObj.registerOutParameter(22, 12);
			ssiObj.registerOutParameter(23, 12);
			ssiObj.registerOutParameter(24, 12);
			ssiObj.registerOutParameter(25, 12);
			ssiObj.registerOutParameter(26, 12);
			ssiObj.registerOutParameter(27, 12);
			ssiObj.registerOutParameter(28, 8);
			ssiObj.registerOutParameter(29, 12);
			ssiObj.executeQuery();
			int ssiObjValue = ssiObj.getInt(1);

			Log.log(5, "ApplicationDAO", "getPartApplication", "SSI Details :" + ssiObjValue);

			if (ssiObjValue == 1) {
				String error = ssiObj.getString(30);

				ssiObj.close();
				ssiObj = null;

				connection.rollback();

				throw new DatabaseException(error);
			}
			ssiDetails.setBorrowerRefNo(ssiObj.getInt(3));
			borrowerDetails.setPreviouslyCovered(ssiObj.getString(4).trim());
			borrowerDetails.setAssistedByBank(ssiObj.getString(5).trim());
			borrowerDetails.setNpa(ssiObj.getString(7).trim());
			ssiDetails.setConstitution(ssiObj.getString(8));
			ssiDetails.setSsiType(ssiObj.getString(9).trim());
			ssiDetails.setSsiName(ssiObj.getString(10));
			ssiDetails.setRegNo(ssiObj.getString(11));
			ssiDetails.setSsiITPan(ssiObj.getString(13));
			ssiDetails.setActivityType(ssiObj.getString(14));
			ssiDetails.setEmployeeNos(ssiObj.getInt(15));
			ssiDetails.setProjectedSalesTurnover(ssiObj.getDouble(16));
			ssiDetails.setProjectedExports(ssiObj.getDouble(17));
			ssiDetails.setAddress(ssiObj.getString(18));
			ssiDetails.setCity(ssiObj.getString(19));
			ssiDetails.setPincode(ssiObj.getString(20).trim());
			ssiDetails.setDistrict(ssiObj.getString(22));
			ssiDetails.setState(ssiObj.getString(23));
			ssiDetails.setIndustryNature(ssiObj.getString(24));
			ssiDetails.setIndustrySector(ssiObj.getString(25));
			ssiDetails.setCgbid(ssiObj.getString(27));
			borrowerDetails.setOsAmt(ssiObj.getDouble(28));
			Log.log(5, "ApplicationDAO", "getPartApplication", "OS Amount :" + ssiObj.getDouble(28));
			borrowerDetails.setSsiDetails(ssiDetails);
			application.setBorrowerDetails(borrowerDetails);
			ssiObj.close();
			ssiObj = null;

			ssiDetails = application.getBorrowerDetails().getSsiDetails();
			borrowerDetails.setSsiDetails(ssiDetails);
			application.setBorrowerDetails(borrowerDetails);
			ssiDetails = null;
			borrowerDetails = null;

			Log.log(4, "ApplicationDAO", "getPartApplication", "values of ssi object set successfully....");

			connection.commit();
		} catch (SQLException sqlException) {
			Log.log(2, "ApplicationDAO", "getPartApplication", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(2, "ApplicationDAO", "getPartApplication", ignore.getMessage());
			}

			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(4, "ApplicationDAO", "getPartApplication", "Exited. Memory : " + Runtime.getRuntime().freeMemory());

		return application;
	}

	public TermLoan getTermLoan(String appRefNo, String applicationLoanType)
			throws NoApplicationFoundException, DatabaseException {
		TermLoan termLoan = new TermLoan();
		Connection connection = DBConnection.getConnection(false);
		try {
			CallableStatement termLoanObj = connection
					.prepareCall("{?=call funcGetTermLoanforAppRef(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			termLoanObj.setString(2, appRefNo);

			termLoanObj.registerOutParameter(1, 4);
			termLoanObj.registerOutParameter(22, 12);

			termLoanObj.registerOutParameter(3, 8);
			termLoanObj.registerOutParameter(4, 91);
			termLoanObj.registerOutParameter(5, 8);
			termLoanObj.registerOutParameter(6, 8);
			termLoanObj.registerOutParameter(7, 8);

			termLoanObj.registerOutParameter(8, 8);
			termLoanObj.registerOutParameter(9, 4);
			termLoanObj.registerOutParameter(10, 12);
			termLoanObj.registerOutParameter(11, 8);
			termLoanObj.registerOutParameter(12, 8);
			termLoanObj.registerOutParameter(13, 8);
			termLoanObj.registerOutParameter(14, 4);
			termLoanObj.registerOutParameter(15, 91);
			termLoanObj.registerOutParameter(16, 4);
			termLoanObj.registerOutParameter(17, 4);
			termLoanObj.registerOutParameter(18, 12);
			termLoanObj.registerOutParameter(19, 8);
			termLoanObj.registerOutParameter(20, 91);
			termLoanObj.registerOutParameter(21, 91);

			termLoanObj.executeQuery();
			int termLoanObjValue = termLoanObj.getInt(1);

			if ((termLoanObjValue != 0) && (termLoanObjValue != 2)) {
				String error = termLoanObj.getString(22);

				termLoanObj.close();
				termLoanObj = null;

				connection.rollback();

				throw new DatabaseException(error);
			}

			java.sql.Date sanctionedDate = termLoanObj.getDate(4);
			// System.out.println("In getTermLoan sanctionedDate "+sanctionedDate);
			termLoan.setAmountSanctionedDate(DateHelper.sqlToUtilDate(sanctionedDate));
			sanctionedDate = null;

			if ((applicationLoanType.equals("TC")) || (applicationLoanType.equals("CC"))
					|| (applicationLoanType.equals("BO"))) {
				termLoan.setCreditGuaranteed(termLoanObj.getDouble(8));
				termLoan.setTenure(termLoanObj.getInt(9));
				termLoan.setInterestType(termLoanObj.getString(10));
				termLoan.setInterestRate(termLoanObj.getDouble(11));
				termLoan.setBenchMarkPLR(termLoanObj.getDouble(12));
				termLoan.setPlr(termLoanObj.getDouble(13));
				termLoan.setRepaymentMoratorium(termLoanObj.getInt(14));
				termLoan.setFirstInstallmentDueDate(DateHelper.sqlToUtilDate(termLoanObj.getDate(15)));
				termLoan.setNoOfInstallments(termLoanObj.getInt(16));
				termLoan.setPeriodicity(termLoanObj.getInt(17));
				termLoan.setTypeOfPLR(termLoanObj.getString(18));
				termLoan.setAmtDisbursed(termLoanObj.getDouble(19));
				if ((termLoanObj.getDate(20) != null) && (termLoanObj.getDate(21) != null)) {
					if (DateHelper.sqlToUtilDate(termLoanObj.getDate(20))
							.equals(DateHelper.sqlToUtilDate(termLoanObj.getDate(21)))) {
						termLoan.setFirstDisbursementDate(DateHelper.sqlToUtilDate(termLoanObj.getDate(20)));
						termLoan.setFinalDisbursementDate(DateHelper.sqlToUtilDate(termLoanObj.getDate(21)));
					} else {
						termLoan.setFirstDisbursementDate(DateHelper.sqlToUtilDate(termLoanObj.getDate(20)));
						termLoan.setFinalDisbursementDate(DateHelper.sqlToUtilDate(termLoanObj.getDate(21)));
					}

				}

			}

			termLoanObj.close();
			termLoanObj = null;
			Log.log(5, "ApplicationDAO", "getTermLoan", "values of term loan object set successfully....");

			connection.commit();
		} catch (SQLException sqlException) {
			Log.log(2, "ApplicationDAO", "getTermLoan", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(2, "ApplicationDAO", "getTermLoan", ignore.getMessage());
			}

			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		return termLoan;
	}

	public WorkingCapital getWorkingCapital(String appRefNo, String applicationLoanType)
			throws NoApplicationFoundException, DatabaseException {
		WorkingCapital workingCapital = new WorkingCapital();

		Connection connection = DBConnection.getConnection(false);
		try {
			CallableStatement wcObj = connection
					.prepareCall("{?=call funcGetWorkingCapitalforAppRef(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			wcObj.setString(2, appRefNo);

			wcObj.registerOutParameter(1, 4);
			wcObj.registerOutParameter(17, 12);
			wcObj.registerOutParameter(3, 8);
			wcObj.registerOutParameter(4, 8);
			wcObj.registerOutParameter(5, 8);
			wcObj.registerOutParameter(6, 8);
			wcObj.registerOutParameter(7, 8);
			wcObj.registerOutParameter(8, 8);
			wcObj.registerOutParameter(9, 8);
			wcObj.registerOutParameter(10, 91);
			wcObj.registerOutParameter(11, 91);
			wcObj.registerOutParameter(12, 8);
			wcObj.registerOutParameter(13, 8);
			wcObj.registerOutParameter(14, 8);
			wcObj.registerOutParameter(15, 12);
			wcObj.registerOutParameter(16, 12);

			wcObj.executeQuery();
			int wcObjValue = wcObj.getInt(1);

			if ((wcObjValue != 0) && (wcObjValue != 2)) {
				String error = wcObj.getString(17);

				wcObj.close();
				wcObj = null;

				connection.rollback();

				throw new DatabaseException(error);
			}

			if ((applicationLoanType.equals("WC")) || (applicationLoanType.equals("CC"))
					|| (applicationLoanType.equals("BO"))) {
				workingCapital.setLimitFundBasedInterest(wcObj.getDouble(8));
				workingCapital.setLimitNonFundBasedCommission(wcObj.getDouble(9));
				workingCapital.setLimitFundBasedSanctionedDate(DateHelper.sqlToUtilDate(wcObj.getDate(10)));
				workingCapital.setLimitNonFundBasedSanctionedDate(DateHelper.sqlToUtilDate(wcObj.getDate(11)));
				workingCapital.setCreditFundBased(wcObj.getDouble(12));
				workingCapital.setCreditNonFundBased(wcObj.getDouble(13));
				workingCapital.setWcPlr(wcObj.getDouble(14));
				workingCapital.setWcTypeOfPLR(wcObj.getString(15));

				workingCapital.setWcInterestType(wcObj.getString(16).trim());
			}

			wcObj.close();
			wcObj = null;

			connection.commit();
		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "getWorkingCapital", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(4, "ApplicationDAO", "getWorkingCapital", ignore.getMessage());
			}

			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		return workingCapital;
	}

	public Application getApplication(String mliID, String cgpan, String appRefNo)
			throws NoApplicationFoundException, DatabaseException {
		Log.log(4, "ApplicationDAO", "getApplication", "Entered. Memory : " + Runtime.getRuntime().freeMemory());

		Application application = new Application();
		BorrowerDetails borrowerDetails = new BorrowerDetails();
		SSIDetails ssiDetails = new SSIDetails();
		ProjectOutlayDetails projectOutlayDetails = new ProjectOutlayDetails();
		PrimarySecurityDetails primarySecurityDetails = new PrimarySecurityDetails();
		TermLoan termLoan = new TermLoan();
		WorkingCapital workingCapital = new WorkingCapital();
		Securitization securitization = new Securitization();

		Connection connection = DBConnection.getConnection(false);

		Log.log(5, "ApplicationDAO", "getApplication", "After entering in the method");
		try {
			if (((mliID == null) && (!appRefNo.equals("")))
					|| ((mliID != null) && (!mliID.equals("")) && (!appRefNo.equals("")))) {
				Log.log(5, "ApplicationDAO", "getApplication", "when mliID and app ref No are entered");
				application = getAppForAppRef(mliID, appRefNo);
			} else if (((mliID == null) && (!cgpan.equals("")))
					|| ((mliID != null) && (!mliID.equals("")) && (!cgpan.equals("")))) {
				Log.log(5, "ApplicationDAO", "getApplication", "when mliID and cgpan are entered");
				application = getAppForCgpan(mliID, cgpan);
			}

			projectOutlayDetails = application.getProjectOutlayDetails();
			appRefNo = application.getAppRefNo();

			Log.log(5, "ApplicationDAO", "getApplication", "values for application object set successfully...");

			CallableStatement ssiObj = connection.prepareCall(
					"{?=call funcGetSSIDetailforAppRef(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			ssiObj.setString(2, appRefNo);

			ssiObj.registerOutParameter(1, 4);
			ssiObj.registerOutParameter(30, 12);

			ssiObj.registerOutParameter(3, 4);
			ssiObj.registerOutParameter(4, 12);
			ssiObj.registerOutParameter(5, 12);
			ssiObj.registerOutParameter(6, 12);
			ssiObj.registerOutParameter(7, 12);
			ssiObj.registerOutParameter(8, 12);
			ssiObj.registerOutParameter(9, 12);
			ssiObj.registerOutParameter(10, 12);
			ssiObj.registerOutParameter(11, 12);
			ssiObj.registerOutParameter(12, 91);
			ssiObj.registerOutParameter(13, 12);
			ssiObj.registerOutParameter(14, 12);
			ssiObj.registerOutParameter(15, 4);
			ssiObj.registerOutParameter(16, 8);
			ssiObj.registerOutParameter(17, 8);
			ssiObj.registerOutParameter(18, 12);
			ssiObj.registerOutParameter(19, 12);
			ssiObj.registerOutParameter(20, 12);
			ssiObj.registerOutParameter(21, 12);
			ssiObj.registerOutParameter(22, 12);
			ssiObj.registerOutParameter(23, 12);
			ssiObj.registerOutParameter(24, 12);
			ssiObj.registerOutParameter(25, 12);
			ssiObj.registerOutParameter(26, 12);
			ssiObj.registerOutParameter(27, 12);
			ssiObj.registerOutParameter(28, 8);
			ssiObj.registerOutParameter(29, 12);
			ssiObj.executeQuery();
			int ssiObjValue = ssiObj.getInt(1);

			Log.log(5, "ApplicationDAO", "submitPromotersDetails", "SSI Details :" + ssiObjValue);

			if (ssiObjValue == 1) {
				String error = ssiObj.getString(30);

				ssiObj.close();
				ssiObj = null;

				connection.rollback();

				throw new DatabaseException(error);
			}
			ssiDetails.setBorrowerRefNo(ssiObj.getInt(3));
			borrowerDetails.setPreviouslyCovered(ssiObj.getString(4).trim());
			borrowerDetails.setAssistedByBank(ssiObj.getString(5).trim());
			borrowerDetails.setNpa(ssiObj.getString(7).trim());
			ssiDetails.setConstitution(ssiObj.getString(8));
			ssiDetails.setSsiType(ssiObj.getString(9).trim());
			ssiDetails.setSsiName(ssiObj.getString(10));
			ssiDetails.setRegNo(ssiObj.getString(11));
			ssiDetails.setSsiITPan(ssiObj.getString(13));
			ssiDetails.setActivityType(ssiObj.getString(14));
			ssiDetails.setEmployeeNos(ssiObj.getInt(15));
			ssiDetails.setProjectedSalesTurnover(ssiObj.getDouble(16));
			ssiDetails.setProjectedExports(ssiObj.getDouble(17));
			ssiDetails.setAddress(ssiObj.getString(18));
			ssiDetails.setCity(ssiObj.getString(19));
			ssiDetails.setPincode(ssiObj.getString(20).trim());
			ssiDetails.setDistrict(ssiObj.getString(22));
			ssiDetails.setState(ssiObj.getString(23));
			ssiDetails.setIndustryNature(ssiObj.getString(24));
			ssiDetails.setIndustrySector(ssiObj.getString(25));
			ssiDetails.setCgbid(ssiObj.getString(27));

			borrowerDetails.setOsAmt(ssiObj.getDouble(28));
			Log.log(5, "ApplicationDAO", "submitPromotersDetails", "OS Amount :" + ssiObj.getDouble(28));
			borrowerDetails.setSsiDetails(ssiDetails);
			application.setBorrowerDetails(borrowerDetails);
			ssiObj.close();
			ssiObj = null;

			ssiDetails = application.getBorrowerDetails().getSsiDetails();

			Log.log(5, "ApplicationDAO", "getApplication", "values of ssi object set successfully....");

			CallableStatement promoterObj = connection
					.prepareCall("{?=call funcGetPromoterDtlforAppRef(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			promoterObj.setString(2, appRefNo);

			promoterObj.registerOutParameter(1, 4);
			promoterObj.registerOutParameter(23, 12);

			promoterObj.registerOutParameter(3, 4);
			promoterObj.registerOutParameter(4, 12);
			promoterObj.registerOutParameter(5, 12);
			promoterObj.registerOutParameter(6, 12);
			promoterObj.registerOutParameter(7, 12);
			promoterObj.registerOutParameter(8, 12);
			promoterObj.registerOutParameter(9, 12);
			promoterObj.registerOutParameter(10, 91);
			promoterObj.registerOutParameter(11, 12);
			promoterObj.registerOutParameter(12, 12);
			promoterObj.registerOutParameter(13, 12);
			promoterObj.registerOutParameter(14, 91);
			promoterObj.registerOutParameter(15, 12);
			promoterObj.registerOutParameter(16, 12);
			promoterObj.registerOutParameter(17, 91);
			promoterObj.registerOutParameter(18, 12);
			promoterObj.registerOutParameter(19, 12);
			promoterObj.registerOutParameter(20, 91);
			promoterObj.registerOutParameter(21, 12);
			promoterObj.registerOutParameter(22, 12);

			promoterObj.executeQuery();
			int promoterObjValue = promoterObj.getInt(1);
			Log.log(5, "ApplicationDAO", "getApplication", "Promoters Details :" + promoterObjValue);

			if (promoterObjValue == 1) {
				String error = promoterObj.getString(23);

				promoterObj.close();
				promoterObj = null;

				connection.rollback();

				Log.log(5, "ApplicationDAO", "getApplication", "Promoter Exception message:" + error);

				throw new DatabaseException(error);
			}
			ssiDetails.setCpTitle(promoterObj.getString(4));
			ssiDetails.setCpFirstName(promoterObj.getString(5));
			if ((promoterObj.getString(6) != null) && (!promoterObj.getString(6).equals(""))) {
				ssiDetails.setCpMiddleName(promoterObj.getString(6));
			} else {
				ssiDetails.setCpMiddleName("");
			}

			ssiDetails.setCpLastName(promoterObj.getString(7));
			ssiDetails.setCpITPAN(promoterObj.getString(8));
			ssiDetails.setCpGender(promoterObj.getString(9).trim());
			ssiDetails.setCpDOB(DateHelper.sqlToUtilDate(promoterObj.getDate(10)));
			ssiDetails.setCpLegalID(promoterObj.getString(11));
			ssiDetails.setCpLegalIdValue(promoterObj.getString(12));
			ssiDetails.setFirstName(promoterObj.getString(13));
			ssiDetails.setFirstDOB(DateHelper.sqlToUtilDate(promoterObj.getDate(14)));
			ssiDetails.setFirstItpan(promoterObj.getString(15));
			ssiDetails.setSecondName(promoterObj.getString(16));
			ssiDetails.setSecondDOB(DateHelper.sqlToUtilDate(promoterObj.getDate(17)));
			ssiDetails.setSecondItpan(promoterObj.getString(18));
			ssiDetails.setThirdName(promoterObj.getString(19));
			ssiDetails.setThirdDOB(DateHelper.sqlToUtilDate(promoterObj.getDate(20)));
			ssiDetails.setThirdItpan(promoterObj.getString(21));
			ssiDetails.setSocialCategory(promoterObj.getString(22));

			application.getBorrowerDetails().setSsiDetails(ssiDetails);
			promoterObj.close();
			promoterObj = null;

			Log.log(5, "ApplicationDAO", "getApplication", "values of promoter object set successfully....");

			CallableStatement guarantorObj = connection
					.prepareCall("{?=call packGetPersonalGuarantee.funcGetPerGuarforAppRef(?,?,?)}");

			guarantorObj.setString(2, appRefNo);

			guarantorObj.registerOutParameter(1, 4);
			guarantorObj.registerOutParameter(4, 12);

			guarantorObj.registerOutParameter(3, -10);

			guarantorObj.executeQuery();
			int guarantorObjValue = guarantorObj.getInt(1);

			Log.log(5, "ApplicationDAO", "getApplication", "Guarantors Details :" + guarantorObjValue);

			if (guarantorObjValue == 1) {
				String error = guarantorObj.getString(4);

				guarantorObj.close();
				guarantorObj = null;

				connection.rollback();

				Log.log(5, "ApplicationDAO", "getApplication", "Gurantor Exception message:" + error);

				throw new DatabaseException(error);
			}
			ResultSet guarantorsResults = (ResultSet) guarantorObj.getObject(3);
			int i = 0;
			while (guarantorsResults.next()) {
				if (i == 0) {
					projectOutlayDetails.setGuarantorsName1(guarantorsResults.getString(1));
					projectOutlayDetails.setGuarantorsNetWorth1(guarantorsResults.getDouble(2));
				}
				if (i == 1) {
					projectOutlayDetails.setGuarantorsName2(guarantorsResults.getString(1));
					projectOutlayDetails.setGuarantorsNetWorth2(guarantorsResults.getDouble(2));
				}
				if (i == 2) {
					projectOutlayDetails.setGuarantorsName3(guarantorsResults.getString(1));
					projectOutlayDetails.setGuarantorsNetWorth3(guarantorsResults.getDouble(2));
				}
				if (i == 3) {
					projectOutlayDetails.setGuarantorsName4(guarantorsResults.getString(1));
					projectOutlayDetails.setGuarantorsNetWorth4(guarantorsResults.getDouble(2));
				}
				i++;
			}

			guarantorObj.close();
			guarantorObj = null;
			guarantorsResults.close();
			guarantorsResults = null;

			Log.log(5, "ApplicationDAO", "getApplication", "values of project guarantor object set successfully....");

			CallableStatement psObj = connection
					.prepareCall("{?=call packGetPrimarySecurity.funcGetPriSecforAppRef(?,?,?)}");

			psObj.setString(2, appRefNo);

			psObj.registerOutParameter(1, 4);
			psObj.registerOutParameter(4, 12);

			psObj.registerOutParameter(3, -10);

			psObj.executeQuery();
			int psObjValue = psObj.getInt(1);

			Log.log(5, "ApplicationDAO", "getApplication", "Primary Security Details :" + psObjValue);

			if (psObjValue == 1) {
				String error = psObj.getString(4);

				psObj.close();
				psObj = null;

				connection.rollback();
				Log.log(5, "ApplicationDAO", "getApplication", "Primary Security Exception message:" + error);

				throw new DatabaseException(error);
			}
			ResultSet psResults = (ResultSet) psObj.getObject(3);
			while (psResults.next()) {
				if (psResults.getString(1).equals("Land")) {
					primarySecurityDetails.setLandParticulars(psResults.getString(2));
					primarySecurityDetails.setLandValue(psResults.getDouble(3));
				}
				if (psResults.getString(1).equals("Building")) {
					primarySecurityDetails.setBldgParticulars(psResults.getString(2));
					primarySecurityDetails.setBldgValue(psResults.getDouble(3));
				}
				if (psResults.getString(1).equals("Machinery")) {
					primarySecurityDetails.setMachineParticulars(psResults.getString(2));
					primarySecurityDetails.setMachineValue(psResults.getDouble(3));
				}
				if (psResults.getString(1).equals("Fixed Assets")) {
					primarySecurityDetails.setAssetsParticulars(psResults.getString(2));
					primarySecurityDetails.setAssetsValue(psResults.getDouble(3));
				}
				if (psResults.getString(1).equals("Current Assets")) {
					primarySecurityDetails.setCurrentAssetsParticulars(psResults.getString(2));
					primarySecurityDetails.setCurrentAssetsValue(psResults.getDouble(3));
				}
				if (psResults.getString(1).equals("Others")) {
					primarySecurityDetails.setOthersParticulars(psResults.getString(2));
					primarySecurityDetails.setOthersValue(psResults.getDouble(3));
				}

			}

			psResults.close();
			psResults = null;
			psObj.close();
			psObj = null;

			Log.log(5, "ApplicationDAO", "getApplication", "values of ps object set successfully....");

			String applicationLoanType = application.getLoanType();

			CallableStatement termLoanObj = connection
					.prepareCall("{?=call funcGetTermLoanforAppRef(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			termLoanObj.setString(2, appRefNo);

			termLoanObj.registerOutParameter(1, 4);
			termLoanObj.registerOutParameter(22, 12);

			termLoanObj.registerOutParameter(3, 8);
			termLoanObj.registerOutParameter(4, 91);
			termLoanObj.registerOutParameter(5, 8);
			termLoanObj.registerOutParameter(6, 8);
			termLoanObj.registerOutParameter(7, 8);

			termLoanObj.registerOutParameter(8, 8);
			termLoanObj.registerOutParameter(9, 4);
			termLoanObj.registerOutParameter(10, 12);
			termLoanObj.registerOutParameter(11, 8);
			termLoanObj.registerOutParameter(12, 8);
			termLoanObj.registerOutParameter(13, 8);
			termLoanObj.registerOutParameter(14, 4);
			termLoanObj.registerOutParameter(15, 91);
			termLoanObj.registerOutParameter(16, 4);
			termLoanObj.registerOutParameter(17, 4);
			termLoanObj.registerOutParameter(18, 12);
			termLoanObj.registerOutParameter(19, 8);
			termLoanObj.registerOutParameter(20, 91);
			termLoanObj.registerOutParameter(21, 91);

			termLoanObj.executeQuery();
			int termLoanObjValue = termLoanObj.getInt(1);

			Log.log(5, "ApplicationDAO", "getApplication", "Term Loan Details :" + termLoanObjValue);

			if ((termLoanObjValue != 0) && (termLoanObjValue != 2)) {
				String error = termLoanObj.getString(22);

				termLoanObj.close();
				termLoanObj = null;

				connection.rollback();

				Log.log(2, "ApplicationDAO", "getApplication", "Term Loan Exception :" + error);

				throw new DatabaseException(error);
			}
			if (termLoanObjValue == 2) {
				projectOutlayDetails.setTermCreditSanctioned(0.0D);
				java.sql.Date sanctionedDate = termLoanObj.getDate(4);
				termLoan.setAmountSanctionedDate(null);
				sanctionedDate = null;
				projectOutlayDetails.setTcPromoterContribution(0.0D);
				projectOutlayDetails.setTcSubsidyOrEquity(0.0D);
				projectOutlayDetails.setTcOthers(0.0D);
				if ((applicationLoanType.equals("TC")) || (applicationLoanType.equals("CC"))
						|| (applicationLoanType.equals("BO"))) {
					termLoan.setCreditGuaranteed(0.0D);
					termLoan.setTenure(0);
					termLoan.setInterestType("T");
					termLoan.setInterestRate(0.0D);
					termLoan.setBenchMarkPLR(0.0D);
					termLoan.setPlr(0.0D);
					termLoan.setRepaymentMoratorium(0);
					termLoan.setFirstInstallmentDueDate(null);
					termLoan.setNoOfInstallments(0);
					termLoan.setPeriodicity(0);
					termLoan.setTypeOfPLR(null);
					termLoan.setAmtDisbursed(0.0D);

					termLoan.setFirstDisbursementDate(null);
					termLoan.setFinalDisbursementDate(null);
				}

				Log.log(4, "ApplicationDAO", "getApplication", "termloan set....");

				termLoanObj.close();
				termLoanObj = null;
			} else {
				projectOutlayDetails.setTermCreditSanctioned(termLoanObj.getDouble(3));
				java.sql.Date sanctionedDate = termLoanObj.getDate(4);
				termLoan.setAmountSanctionedDate(DateHelper.sqlToUtilDate(sanctionedDate));
				sanctionedDate = null;
				projectOutlayDetails.setTcPromoterContribution(termLoanObj.getDouble(5));
				projectOutlayDetails.setTcSubsidyOrEquity(termLoanObj.getDouble(6));
				projectOutlayDetails.setTcOthers(termLoanObj.getDouble(7));
				if ((applicationLoanType.equals("TC")) || (applicationLoanType.equals("CC"))
						|| (applicationLoanType.equals("BO"))) {
					termLoan.setCreditGuaranteed(termLoanObj.getDouble(8));
					termLoan.setTenure(termLoanObj.getInt(9));
					termLoan.setInterestType(termLoanObj.getString(10));
					termLoan.setInterestRate(termLoanObj.getDouble(11));
					termLoan.setBenchMarkPLR(termLoanObj.getDouble(12));
					termLoan.setPlr(termLoanObj.getDouble(13));
					termLoan.setRepaymentMoratorium(termLoanObj.getInt(14));
					termLoan.setFirstInstallmentDueDate(DateHelper.sqlToUtilDate(termLoanObj.getDate(15)));
					termLoan.setNoOfInstallments(termLoanObj.getInt(16));
					termLoan.setPeriodicity(termLoanObj.getInt(17));
					termLoan.setTypeOfPLR(termLoanObj.getString(18));
					termLoan.setAmtDisbursed(termLoanObj.getDouble(19));
					if (termLoanObj.getDate(20) != null) {
						if (DateHelper.sqlToUtilDate(termLoanObj.getDate(20))
								.equals(DateHelper.sqlToUtilDate(termLoanObj.getDate(21)))) {
							termLoan.setFirstDisbursementDate(DateHelper.sqlToUtilDate(termLoanObj.getDate(20)));
							termLoan.setFinalDisbursementDate(null);
						} else {
							termLoan.setFirstDisbursementDate(DateHelper.sqlToUtilDate(termLoanObj.getDate(20)));
							termLoan.setFinalDisbursementDate(DateHelper.sqlToUtilDate(termLoanObj.getDate(21)));
						}

					}

					Log.log(4, "ApplicationDAO", "getApplication", "termloan set....");
				}

				termLoanObj.close();
				termLoanObj = null;
			}

			if ((applicationLoanType.equals("TC")) || (applicationLoanType.equals("CC"))
					|| (applicationLoanType.equals("BO"))) {
				CallableStatement repaymentObj = connection.prepareCall("{?=call funcGetRepSchForAppRef(?,?,?,?,?,?)}");

				repaymentObj.setString(2, appRefNo);

				repaymentObj.registerOutParameter(1, 4);
				repaymentObj.registerOutParameter(7, 12);

				repaymentObj.registerOutParameter(3, 4);
				repaymentObj.registerOutParameter(4, 91);
				repaymentObj.registerOutParameter(5, 4);
				repaymentObj.registerOutParameter(6, 4);

				repaymentObj.executeQuery();
				int repaymentObjValue = repaymentObj.getInt(1);

				Log.log(5, "ApplicationDAO", "getApplication", "Term Loan Details :" + repaymentObjValue);

				if (repaymentObjValue != 0) {
					String error = repaymentObj.getString(7);

					repaymentObj.close();
					repaymentObj = null;

					connection.rollback();

					Log.log(2, "ApplicationDAO", "getApplication", "Term Loan Exception :" + error);

					throw new DatabaseException(error);
				}
				termLoan.setRepaymentMoratorium(repaymentObj.getInt(3));
				termLoan.setFirstInstallmentDueDate(DateHelper.sqlToUtilDate(repaymentObj.getDate(4)));
				termLoan.setPeriodicity(repaymentObj.getInt(5));
				termLoan.setNoOfInstallments(repaymentObj.getInt(6));
				repaymentObj.close();
				repaymentObj = null;
			}

			Log.log(4, "ApplicationDAO", "getApplication", "values of term loan object set successfully....");

			if ((applicationLoanType.equals("TC")) || (applicationLoanType.equals("CC"))
					|| (applicationLoanType.equals("BO"))) {
				CallableStatement tcOutObj = connection.prepareCall("{?=call funcGetTCOutStanding(?,?,?,?)}");

				tcOutObj.setString(2, appRefNo);

				tcOutObj.registerOutParameter(1, 4);
				tcOutObj.registerOutParameter(5, 12);
				tcOutObj.registerOutParameter(3, 8);
				tcOutObj.registerOutParameter(4, 91);

				tcOutObj.executeQuery();
				int tcOutObjValue = tcOutObj.getInt(1);

				Log.log(5, "ApplicationDAO", "getApplication", "TWorking Capital Loan Details :" + tcOutObjValue);

				if (tcOutObjValue == 1) {
					String error = tcOutObj.getString(5);

					tcOutObj.close();
					tcOutObj = null;

					connection.rollback();

					Log.log(2, "ApplicationDAO", "getApplication", "Working Capital Exception :" + error);

					throw new DatabaseException(error);
				}
				if (tcOutObjValue == 2) {
					termLoan.setPplOS(0.0D);

					termLoan.setPplOS(tcOutObj.getDouble(3));
					termLoan.setPplOsAsOnDate(DateHelper.sqlToUtilDate(tcOutObj.getDate(4)));

					tcOutObj.close();
					tcOutObj = null;
				} else {
					if (tcOutObj.getDouble(3) == -1.0D) {
						termLoan.setPplOS(0.0D);
					} else {
						termLoan.setPplOS(tcOutObj.getDouble(3));
						termLoan.setPplOsAsOnDate(DateHelper.sqlToUtilDate(tcOutObj.getDate(4)));
					}

					tcOutObj.close();
					tcOutObj = null;
				}

			}

			CallableStatement wcObj = connection
					.prepareCall("{?=call funcGetWorkingCapitalforAppRef(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			wcObj.setString(2, appRefNo);

			wcObj.registerOutParameter(1, 4);
			wcObj.registerOutParameter(17, 12);

			wcObj.registerOutParameter(3, 8);
			wcObj.registerOutParameter(4, 8);
			wcObj.registerOutParameter(5, 8);
			wcObj.registerOutParameter(6, 8);
			wcObj.registerOutParameter(7, 8);
			wcObj.registerOutParameter(8, 8);
			wcObj.registerOutParameter(9, 8);
			wcObj.registerOutParameter(10, 91);
			wcObj.registerOutParameter(11, 91);
			wcObj.registerOutParameter(12, 8);
			wcObj.registerOutParameter(13, 8);
			wcObj.registerOutParameter(14, 8);
			wcObj.registerOutParameter(15, 12);
			wcObj.registerOutParameter(16, 12);

			wcObj.executeQuery();
			int wcObjValue = wcObj.getInt(1);

			Log.log(5, "ApplicationDAO", "getApplication", "Working Capital Loan Details :" + wcObjValue);

			if ((wcObjValue != 0) && (wcObjValue != 2)) {
				String error = wcObj.getString(17);

				wcObj.close();
				wcObj = null;

				connection.rollback();

				Log.log(2, "ApplicationDAO", "getApplication", "Working Capital Exception :" + error);

				throw new DatabaseException(error);
			}
			if (wcObjValue == 2) {
				projectOutlayDetails.setWcFundBasedSanctioned(0.0D);
				projectOutlayDetails.setWcNonFundBasedSanctioned(0.0D);
				projectOutlayDetails.setWcPromoterContribution(0.0D);
				projectOutlayDetails.setWcSubsidyOrEquity(0.0D);
				projectOutlayDetails.setWcOthers(0.0D);
				if ((applicationLoanType.equals("WC")) || (applicationLoanType.equals("CC"))
						|| (applicationLoanType.equals("BO"))) {
					workingCapital.setLimitFundBasedInterest(0.0D);
					workingCapital.setLimitNonFundBasedCommission(0.0D);
					workingCapital.setLimitFundBasedSanctionedDate(null);
					workingCapital.setLimitNonFundBasedSanctionedDate(null);
					workingCapital.setCreditFundBased(0.0D);
					workingCapital.setCreditNonFundBased(0.0D);
					workingCapital.setWcPlr(0.0D);
					workingCapital.setWcTypeOfPLR(null);
					workingCapital.setWcInterestType("T");
					Log.log(2, "ApplicationDAO", "getApplication",
							"Working Interest type:" + workingCapital.getWcInterestType());
				}
			} else {
				projectOutlayDetails.setWcFundBasedSanctioned(wcObj.getDouble(3));
				projectOutlayDetails.setWcNonFundBasedSanctioned(wcObj.getDouble(4));
				projectOutlayDetails.setWcPromoterContribution(wcObj.getDouble(5));
				projectOutlayDetails.setWcSubsidyOrEquity(wcObj.getDouble(6));
				projectOutlayDetails.setWcOthers(wcObj.getDouble(7));
				if ((applicationLoanType.equals("WC")) || (applicationLoanType.equals("CC"))
						|| (applicationLoanType.equals("BO"))) {
					workingCapital.setLimitFundBasedInterest(wcObj.getDouble(8));
					workingCapital.setLimitNonFundBasedCommission(wcObj.getDouble(9));
					workingCapital.setLimitFundBasedSanctionedDate(DateHelper.sqlToUtilDate(wcObj.getDate(10)));
					workingCapital.setLimitNonFundBasedSanctionedDate(DateHelper.sqlToUtilDate(wcObj.getDate(11)));
					workingCapital.setCreditFundBased(wcObj.getDouble(12));
					workingCapital.setCreditNonFundBased(wcObj.getDouble(13));
					workingCapital.setWcPlr(wcObj.getDouble(14));
					workingCapital.setWcTypeOfPLR(wcObj.getString(15));
					workingCapital.setWcInterestType(wcObj.getString(16).trim());
					Log.log(2, "ApplicationDAO", "getApplication",
							"Working Interest type:" + workingCapital.getWcInterestType());
				}
				wcObj.close();
				wcObj = null;
			}

			Log.log(4, "ApplicationDAO", "getApplication", "values of working capital object set successfully....");

			CallableStatement getSecDtls = connection.prepareCall("{?=call funcGetSecDetails(?,?,?,?,?,?,?,?,?,?)}");

			getSecDtls.setString(2, appRefNo);

			getSecDtls.registerOutParameter(1, 4);
			getSecDtls.registerOutParameter(11, 12);

			getSecDtls.registerOutParameter(3, 8);
			getSecDtls.registerOutParameter(4, 12);
			getSecDtls.registerOutParameter(5, 8);
			getSecDtls.registerOutParameter(6, 8);
			getSecDtls.registerOutParameter(7, 8);
			getSecDtls.registerOutParameter(8, 8);
			getSecDtls.registerOutParameter(9, 8);
			getSecDtls.registerOutParameter(10, 8);

			getSecDtls.executeQuery();

			int getSecDtlsValue = getSecDtls.getInt(1);

			Log.log(5, "ApplicationDAO", "getApplication", "Securitization Details :" + getSecDtlsValue);

			if (getSecDtlsValue == 1) {
				String error = getSecDtls.getString(11);

				getSecDtls.close();
				getSecDtls = null;

				connection.rollback();

				Log.log(2, "ApplicationDAO", "getApplication", "Securitization Detail Exception :" + error);

				throw new DatabaseException(error);
			}
			if (getSecDtlsValue == 2) {
				securitization.setSpreadOverPLR(0.0D);
				securitization.setPplRepaymentInEqual("Y");
				securitization.setTangibleNetWorth(0.0D);
				securitization.setFixedACR(0.0D);
				securitization.setCurrentRatio(0.0D);
				securitization.setMinimumDSCR(0.0D);
				securitization.setAvgDSCR(0.0D);
				securitization.setFinancialPartUnit(0.0D);
				getSecDtls.close();
				getSecDtls = null;
			} else {
				securitization.setSpreadOverPLR(getSecDtls.getDouble(3));
				securitization.setPplRepaymentInEqual(getSecDtls.getString(4));
				securitization.setTangibleNetWorth(getSecDtls.getDouble(5));
				securitization.setFixedACR(getSecDtls.getDouble(6));
				securitization.setCurrentRatio(getSecDtls.getDouble(7));
				securitization.setMinimumDSCR(getSecDtls.getDouble(8));
				securitization.setAvgDSCR(getSecDtls.getDouble(9));
				securitization.setFinancialPartUnit(getSecDtls.getDouble(10));
				getSecDtls.close();
				getSecDtls = null;
			}

			MCGSProcessor mcgsProcessor = new MCGSProcessor();
			MCGFDetails mcgfDetails = mcgsProcessor.getMcgsDetails(appRefNo, connection);
			if (mcgfDetails != null) {
				application.setMCGFDetails(mcgfDetails);
			}

			mcgsProcessor = null;
			mcgfDetails = null;

			borrowerDetails.setSsiDetails(ssiDetails);
			application.setBorrowerDetails(borrowerDetails);

			application.setTermLoan(termLoan);

			application.setWc(workingCapital);
			application.setSecuritization(securitization);

			projectOutlayDetails.setPrimarySecurityDetails(primarySecurityDetails);
			application.setProjectOutlayDetails(projectOutlayDetails);

			borrowerDetails = null;
			ssiDetails = null;
			projectOutlayDetails = null;
			primarySecurityDetails = null;
			termLoan = null;
			workingCapital = null;
			securitization = null;

			Log.log(4, "ApplicationDAO", "getApplication", "Exited from appDAO successfully....");

			connection.commit();
		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "getApplication", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(4, "ApplicationDAO", "getApplication", ignore.getMessage());
			}

			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(4, "ApplicationDAO", "getApplication", "Exited. Memory : " + Runtime.getRuntime().freeMemory());

		return application;
	}

	public Application getOldApplication(String mliID, String cgpan, String appRefNo)
			throws NoApplicationFoundException, DatabaseException {
		Log.log(4, "ApplicationDAO", "getApplication", "Entered. Memory : " + Runtime.getRuntime().freeMemory());

		Application application = new Application();
		BorrowerDetails borrowerDetails = new BorrowerDetails();
		SSIDetails ssiDetails = new SSIDetails();
		ProjectOutlayDetails projectOutlayDetails = new ProjectOutlayDetails();
		PrimarySecurityDetails primarySecurityDetails = new PrimarySecurityDetails();
		TermLoan termLoan = new TermLoan();
		WorkingCapital workingCapital = new WorkingCapital();
		Securitization securitization = new Securitization();

		Connection connection = DBConnection.getConnection(false);

		Log.log(4, "ApplicationDAO", "getApplication", "After entering in the method");
		try {
			if (((mliID == null) && (!cgpan.equals("")))
					|| ((mliID != null) && (!mliID.equals("")) && (!cgpan.equals("")))) {
				Log.log(4, "ApplicationDAO", "getApplication", "when mliID and cgpan are entered");
				application = getAppForCgpan(mliID, cgpan);
			}

			appRefNo = application.getAppRefNo();

			CallableStatement appForAppRef = connection.prepareCall(
					"{?=call funcGetOldAppDtlforAppRef(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			appForAppRef.setString(2, appRefNo);
			appForAppRef.setString(3, mliID);

			Log.log(5, "ApplicationDAO", "getAppForAppRef", "App ref no from getAppForAppRef:" + appRefNo);

			appForAppRef.registerOutParameter(1, 4);
			appForAppRef.registerOutParameter(29, 12);

			appForAppRef.registerOutParameter(4, 12);
			appForAppRef.registerOutParameter(5, 4);
			appForAppRef.registerOutParameter(6, 12);
			appForAppRef.registerOutParameter(7, 12);
			appForAppRef.registerOutParameter(8, 12);
			appForAppRef.registerOutParameter(9, 12);
			appForAppRef.registerOutParameter(10, 12);
			appForAppRef.registerOutParameter(11, 12);
			appForAppRef.registerOutParameter(12, 12);
			appForAppRef.registerOutParameter(13, 12);
			appForAppRef.registerOutParameter(14, 12);
			appForAppRef.registerOutParameter(15, 12);
			appForAppRef.registerOutParameter(16, 12);
			appForAppRef.registerOutParameter(17, 12);
			appForAppRef.registerOutParameter(18, 12);
			appForAppRef.registerOutParameter(19, 12);
			appForAppRef.registerOutParameter(20, 12);
			appForAppRef.registerOutParameter(21, 8);
			appForAppRef.registerOutParameter(22, 12);
			appForAppRef.registerOutParameter(23, 12);
			appForAppRef.registerOutParameter(24, 91);
			appForAppRef.registerOutParameter(25, 12);
			appForAppRef.registerOutParameter(26, 12);
			appForAppRef.registerOutParameter(27, 8);
			appForAppRef.registerOutParameter(28, 8);

			Log.log(4, "ApplicationDAO", "getAppForAppRef", "Before Executing");

			appForAppRef.execute();

			Log.log(4, "ApplicationDAO", "getAppForAppRef", "After Executing");

			int appForAppRefValue = appForAppRef.getInt(1);
			Log.log(5, "ApplicationDAO", "getAppForAppRef", "Application Details :" + appForAppRefValue);

			if (appForAppRefValue == 1) {
				String error = appForAppRef.getString(29);

				appForAppRef.close();
				appForAppRef = null;

				connection.rollback();

				Log.log(2, "ApplicationDAO", "getAppForAppRef", "Application Detail exception :" + error);

				throw new DatabaseException(error);
			}
			application.setAppRefNo(appForAppRef.getString(4));
			application.setScheme(appForAppRef.getString(7));
			mliID = appForAppRef.getString(8) + appForAppRef.getString(9) + appForAppRef.getString(10);
			application.setMliID(mliID);
			application.setMliBranchName(appForAppRef.getString(11));
			application.setMliBranchCode(appForAppRef.getString(12));
			application.setMliRefNo(appForAppRef.getString(13));
			application.setCompositeLoan(appForAppRef.getString(14).trim());

			application.setLoanType(appForAppRef.getString(16).trim());
			projectOutlayDetails.setCollateralSecurityTaken(appForAppRef.getString(17).trim());
			projectOutlayDetails.setThirdPartyGuaranteeTaken(appForAppRef.getString(18).trim());
			projectOutlayDetails.setSubsidyName(appForAppRef.getString(19));
			application.setRehabilitation(appForAppRef.getString(20).trim());
			projectOutlayDetails.setProjectOutlay(appForAppRef.getDouble(21));
			application.setCgpanReference(appForAppRef.getString(22));
			application.setRemarks(appForAppRef.getString(23));
			application.setSubmittedDate(appForAppRef.getDate(24));
			application.setStatus(appForAppRef.getString(25));
			application.setSubSchemeName(appForAppRef.getString(26));
			application.setApprovedAmount(appForAppRef.getDouble(27));
			application.setReapprovedAmount(appForAppRef.getDouble(28));

			application.setProjectOutlayDetails(projectOutlayDetails);

			appForAppRef.close();
			appForAppRef = null;

			projectOutlayDetails = application.getProjectOutlayDetails();
			appRefNo = application.getAppRefNo();

			Log.log(4, "ApplicationDAO", "getApplication", "values for application object set successfully...");

			CallableStatement ssiObj = connection.prepareCall(
					"{?=call funcGetSSIDetailforAppRef(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			ssiObj.setString(2, appRefNo);

			ssiObj.registerOutParameter(1, 4);
			ssiObj.registerOutParameter(30, 12);

			ssiObj.registerOutParameter(3, 4);
			ssiObj.registerOutParameter(4, 12);
			ssiObj.registerOutParameter(5, 12);
			ssiObj.registerOutParameter(6, 12);
			ssiObj.registerOutParameter(7, 12);
			ssiObj.registerOutParameter(8, 12);
			ssiObj.registerOutParameter(9, 12);
			ssiObj.registerOutParameter(10, 12);
			ssiObj.registerOutParameter(11, 12);
			ssiObj.registerOutParameter(12, 91);
			ssiObj.registerOutParameter(13, 12);
			ssiObj.registerOutParameter(14, 12);
			ssiObj.registerOutParameter(15, 4);
			ssiObj.registerOutParameter(16, 8);
			ssiObj.registerOutParameter(17, 8);
			ssiObj.registerOutParameter(18, 12);
			ssiObj.registerOutParameter(19, 12);
			ssiObj.registerOutParameter(20, 12);
			ssiObj.registerOutParameter(21, 12);
			ssiObj.registerOutParameter(22, 12);
			ssiObj.registerOutParameter(23, 12);
			ssiObj.registerOutParameter(24, 12);
			ssiObj.registerOutParameter(25, 12);
			ssiObj.registerOutParameter(26, 12);
			ssiObj.registerOutParameter(27, 12);
			ssiObj.registerOutParameter(28, 8);
			ssiObj.registerOutParameter(29, 12);
			ssiObj.executeQuery();
			int ssiObjValue = ssiObj.getInt(1);

			Log.log(5, "ApplicationDAO", "submitPromotersDetails", "SSI Details :" + ssiObjValue);

			if (ssiObjValue == 1) {
				String error = ssiObj.getString(30);

				ssiObj.close();
				ssiObj = null;

				connection.rollback();

				throw new DatabaseException(error);
			}
			ssiDetails.setBorrowerRefNo(ssiObj.getInt(3));
			borrowerDetails.setPreviouslyCovered(ssiObj.getString(4).trim());
			borrowerDetails.setAssistedByBank(ssiObj.getString(5).trim());
			borrowerDetails.setNpa(ssiObj.getString(7).trim());
			ssiDetails.setConstitution(ssiObj.getString(8));
			ssiDetails.setSsiType(ssiObj.getString(9).trim());
			ssiDetails.setSsiName(ssiObj.getString(10));
			ssiDetails.setRegNo(ssiObj.getString(11));
			ssiDetails.setSsiITPan(ssiObj.getString(13));
			ssiDetails.setActivityType(ssiObj.getString(14));
			ssiDetails.setEmployeeNos(ssiObj.getInt(15));
			ssiDetails.setProjectedSalesTurnover(ssiObj.getDouble(16));
			ssiDetails.setProjectedExports(ssiObj.getDouble(17));
			ssiDetails.setAddress(ssiObj.getString(18));
			ssiDetails.setCity(ssiObj.getString(19));
			ssiDetails.setPincode(ssiObj.getString(20).trim());
			ssiDetails.setDistrict(ssiObj.getString(22));
			ssiDetails.setState(ssiObj.getString(23));
			ssiDetails.setIndustryNature(ssiObj.getString(24));
			ssiDetails.setIndustrySector(ssiObj.getString(25));
			ssiDetails.setCgbid(ssiObj.getString(27));

			borrowerDetails.setOsAmt(ssiObj.getDouble(28));
			Log.log(5, "ApplicationDAO", "submitPromotersDetails", "OS Amount :" + ssiObj.getDouble(28));
			borrowerDetails.setSsiDetails(ssiDetails);
			application.setBorrowerDetails(borrowerDetails);
			ssiObj.close();
			ssiObj = null;

			ssiDetails = application.getBorrowerDetails().getSsiDetails();

			Log.log(4, "ApplicationDAO", "getApplication", "values of ssi object set successfully....");

			CallableStatement promoterObj = connection
					.prepareCall("{?=call funcGetPromoterDtlforAppRef(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			promoterObj.setString(2, appRefNo);

			promoterObj.registerOutParameter(1, 4);
			promoterObj.registerOutParameter(23, 12);

			promoterObj.registerOutParameter(3, 4);
			promoterObj.registerOutParameter(4, 12);
			promoterObj.registerOutParameter(5, 12);
			promoterObj.registerOutParameter(6, 12);
			promoterObj.registerOutParameter(7, 12);
			promoterObj.registerOutParameter(8, 12);
			promoterObj.registerOutParameter(9, 12);
			promoterObj.registerOutParameter(10, 91);
			promoterObj.registerOutParameter(11, 12);
			promoterObj.registerOutParameter(12, 12);
			promoterObj.registerOutParameter(13, 12);
			promoterObj.registerOutParameter(14, 91);
			promoterObj.registerOutParameter(15, 12);
			promoterObj.registerOutParameter(16, 12);
			promoterObj.registerOutParameter(17, 91);
			promoterObj.registerOutParameter(18, 12);
			promoterObj.registerOutParameter(19, 12);
			promoterObj.registerOutParameter(20, 91);
			promoterObj.registerOutParameter(21, 12);
			promoterObj.registerOutParameter(22, 12);

			promoterObj.executeQuery();
			int promoterObjValue = promoterObj.getInt(1);
			Log.log(4, "ApplicationDAO", "getApplication", "Promoters Details :" + promoterObjValue);

			if (promoterObjValue == 1) {
				String error = promoterObj.getString(23);

				promoterObj.close();
				promoterObj = null;

				connection.rollback();

				Log.log(2, "ApplicationDAO", "getApplication", "Promoter Exception message:" + error);

				throw new DatabaseException(error);
			}
			ssiDetails.setCpTitle(promoterObj.getString(4));
			ssiDetails.setCpFirstName(promoterObj.getString(5));
			if ((promoterObj.getString(6) != null) && (!promoterObj.getString(6).equals(""))) {
				ssiDetails.setCpMiddleName(promoterObj.getString(6));
			} else {
				ssiDetails.setCpMiddleName("");
			}

			ssiDetails.setCpLastName(promoterObj.getString(7));
			ssiDetails.setCpITPAN(promoterObj.getString(8));
			ssiDetails.setCpGender(promoterObj.getString(9).trim());
			ssiDetails.setCpDOB(DateHelper.sqlToUtilDate(promoterObj.getDate(10)));
			ssiDetails.setCpLegalID(promoterObj.getString(11));
			ssiDetails.setCpLegalIdValue(promoterObj.getString(12));
			ssiDetails.setFirstName(promoterObj.getString(13));
			ssiDetails.setFirstDOB(DateHelper.sqlToUtilDate(promoterObj.getDate(14)));
			ssiDetails.setFirstItpan(promoterObj.getString(15));
			ssiDetails.setSecondName(promoterObj.getString(16));
			ssiDetails.setSecondDOB(DateHelper.sqlToUtilDate(promoterObj.getDate(17)));
			ssiDetails.setSecondItpan(promoterObj.getString(18));
			ssiDetails.setThirdName(promoterObj.getString(19));
			ssiDetails.setThirdDOB(DateHelper.sqlToUtilDate(promoterObj.getDate(20)));
			ssiDetails.setThirdItpan(promoterObj.getString(21));
			ssiDetails.setSocialCategory(promoterObj.getString(22));

			application.getBorrowerDetails().setSsiDetails(ssiDetails);
			promoterObj.close();
			promoterObj = null;

			Log.log(4, "ApplicationDAO", "getApplication", "values of promoter object set successfully....");

			CallableStatement guarantorObj = connection
					.prepareCall("{?=call packGetOldPersonalGuarantee.funcGetOldPerGuarforAppRef(?,?,?)}");

			guarantorObj.setString(2, appRefNo);

			guarantorObj.registerOutParameter(1, 4);
			guarantorObj.registerOutParameter(4, 12);

			guarantorObj.registerOutParameter(3, -10);

			guarantorObj.executeQuery();
			int guarantorObjValue = guarantorObj.getInt(1);

			Log.log(5, "ApplicationDAO", "getApplication", "Guarantors Details :" + guarantorObjValue);

			if (guarantorObjValue == 1) {
				String error = guarantorObj.getString(4);

				guarantorObj.close();
				guarantorObj = null;

				connection.rollback();

				Log.log(2, "ApplicationDAO", "getApplication", "Gurantor Exception message:" + error);

				throw new DatabaseException(error);
			}
			ResultSet guarantorsResults = (ResultSet) guarantorObj.getObject(3);
			int i = 0;
			while (guarantorsResults.next()) {
				if (i == 0) {
					projectOutlayDetails.setGuarantorsName1(guarantorsResults.getString(1));
					projectOutlayDetails.setGuarantorsNetWorth1(guarantorsResults.getDouble(2));
				}
				if (i == 1) {
					projectOutlayDetails.setGuarantorsName2(guarantorsResults.getString(1));
					projectOutlayDetails.setGuarantorsNetWorth2(guarantorsResults.getDouble(2));
				}
				if (i == 2) {
					projectOutlayDetails.setGuarantorsName3(guarantorsResults.getString(1));
					projectOutlayDetails.setGuarantorsNetWorth3(guarantorsResults.getDouble(2));
				}
				if (i == 3) {
					projectOutlayDetails.setGuarantorsName4(guarantorsResults.getString(1));
					projectOutlayDetails.setGuarantorsNetWorth4(guarantorsResults.getDouble(2));
				}
				i++;
			}

			guarantorObj.close();
			guarantorObj = null;
			guarantorsResults.close();
			guarantorsResults = null;

			Log.log(5, "ApplicationDAO", "getApplication", "values of project guarantor object set successfully....");

			CallableStatement psObj = connection
					.prepareCall("{?=call packGetOldPrimarySecurity.funcGetOldPriSecforAppRef(?,?,?)}");

			psObj.setString(2, appRefNo);

			psObj.registerOutParameter(1, 4);
			psObj.registerOutParameter(4, 12);

			psObj.registerOutParameter(3, -10);

			psObj.executeQuery();
			int psObjValue = psObj.getInt(1);

			Log.log(5, "ApplicationDAO", "getApplication", "Primary Security Details :" + psObjValue);

			if (psObjValue == 1) {
				String error = psObj.getString(4);

				psObj.close();
				psObj = null;

				connection.rollback();
				Log.log(2, "ApplicationDAO", "getApplication", "Primary Security Exception message:" + error);

				throw new DatabaseException(error);
			}
			ResultSet psResults = (ResultSet) psObj.getObject(3);
			while (psResults.next()) {
				if (psResults.getString(1).equals("Land")) {
					primarySecurityDetails.setLandParticulars(psResults.getString(2));
					primarySecurityDetails.setLandValue(psResults.getDouble(3));
				}
				if (psResults.getString(1).equals("Building")) {
					primarySecurityDetails.setBldgParticulars(psResults.getString(2));
					primarySecurityDetails.setBldgValue(psResults.getDouble(3));
				}
				if (psResults.getString(1).equals("Machinery")) {
					primarySecurityDetails.setMachineParticulars(psResults.getString(2));
					primarySecurityDetails.setMachineValue(psResults.getDouble(3));
				}
				if (psResults.getString(1).equals("Fixed Assets")) {
					primarySecurityDetails.setAssetsParticulars(psResults.getString(2));
					primarySecurityDetails.setAssetsValue(psResults.getDouble(3));
				}
				if (psResults.getString(1).equals("Current Assets")) {
					primarySecurityDetails.setCurrentAssetsParticulars(psResults.getString(2));
					primarySecurityDetails.setCurrentAssetsValue(psResults.getDouble(3));
				}
				if (psResults.getString(1).equals("Others")) {
					primarySecurityDetails.setOthersParticulars(psResults.getString(2));
					primarySecurityDetails.setOthersValue(psResults.getDouble(3));
				}

			}

			psResults.close();
			psResults = null;
			psObj.close();
			psObj = null;

			Log.log(4, "ApplicationDAO", "getApplication", "values of ps object set successfully....");

			String applicationLoanType = application.getLoanType();

			CallableStatement termLoanObj = connection
					.prepareCall("{?=call funcGetOldTermLoanforAppRef(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			termLoanObj.setString(2, appRefNo);

			termLoanObj.registerOutParameter(1, 4);
			termLoanObj.registerOutParameter(22, 12);

			termLoanObj.registerOutParameter(3, 8);
			termLoanObj.registerOutParameter(4, 91);
			termLoanObj.registerOutParameter(5, 8);
			termLoanObj.registerOutParameter(6, 8);
			termLoanObj.registerOutParameter(7, 8);

			termLoanObj.registerOutParameter(8, 8);
			termLoanObj.registerOutParameter(9, 4);
			termLoanObj.registerOutParameter(10, 12);
			termLoanObj.registerOutParameter(11, 8);
			termLoanObj.registerOutParameter(12, 8);
			termLoanObj.registerOutParameter(13, 8);
			termLoanObj.registerOutParameter(14, 4);
			termLoanObj.registerOutParameter(15, 91);
			termLoanObj.registerOutParameter(16, 4);
			termLoanObj.registerOutParameter(17, 4);
			termLoanObj.registerOutParameter(18, 12);
			termLoanObj.registerOutParameter(19, 8);
			termLoanObj.registerOutParameter(20, 91);
			termLoanObj.registerOutParameter(21, 91);

			termLoanObj.executeQuery();
			int termLoanObjValue = termLoanObj.getInt(1);

			Log.log(5, "ApplicationDAO", "getApplication", "Term Loan Details :" + termLoanObjValue);

			if ((termLoanObjValue != 0) && (termLoanObjValue != 2)) {
				String error = termLoanObj.getString(22);

				termLoanObj.close();
				termLoanObj = null;

				connection.rollback();

				Log.log(2, "ApplicationDAO", "getApplication", "Term Loan Exception :" + error);

				throw new DatabaseException(error);
			}
			if (termLoanObjValue == 2) {
				projectOutlayDetails.setTermCreditSanctioned(0.0D);
				java.sql.Date sanctionedDate = termLoanObj.getDate(4);
				termLoan.setAmountSanctionedDate(null);
				sanctionedDate = null;
				projectOutlayDetails.setTcPromoterContribution(0.0D);
				projectOutlayDetails.setTcSubsidyOrEquity(0.0D);
				projectOutlayDetails.setTcOthers(0.0D);
				if ((applicationLoanType.equals("TC")) || (applicationLoanType.equals("CC"))
						|| (applicationLoanType.equals("BO"))) {
					termLoan.setCreditGuaranteed(0.0D);
					termLoan.setTenure(0);
					termLoan.setInterestType("T");
					termLoan.setInterestRate(0.0D);
					termLoan.setBenchMarkPLR(0.0D);
					termLoan.setPlr(0.0D);
					termLoan.setRepaymentMoratorium(0);
					termLoan.setFirstInstallmentDueDate(null);
					termLoan.setNoOfInstallments(0);
					termLoan.setPeriodicity(0);
					termLoan.setTypeOfPLR(null);
					termLoan.setAmtDisbursed(0.0D);

					termLoan.setFirstDisbursementDate(null);
					termLoan.setFinalDisbursementDate(null);
				}

				Log.log(4, "ApplicationDAO", "getApplication", "termloan set....");

				termLoanObj.close();
				termLoanObj = null;
			} else {
				projectOutlayDetails.setTermCreditSanctioned(termLoanObj.getDouble(3));
				java.sql.Date sanctionedDate = termLoanObj.getDate(4);
				termLoan.setAmountSanctionedDate(DateHelper.sqlToUtilDate(sanctionedDate));
				sanctionedDate = null;
				projectOutlayDetails.setTcPromoterContribution(termLoanObj.getDouble(5));
				projectOutlayDetails.setTcSubsidyOrEquity(termLoanObj.getDouble(6));
				projectOutlayDetails.setTcOthers(termLoanObj.getDouble(7));
				if ((applicationLoanType.equals("TC")) || (applicationLoanType.equals("CC"))
						|| (applicationLoanType.equals("BO"))) {
					termLoan.setCreditGuaranteed(termLoanObj.getDouble(8));
					termLoan.setTenure(termLoanObj.getInt(9));
					termLoan.setInterestType(termLoanObj.getString(10));
					termLoan.setInterestRate(termLoanObj.getDouble(11));
					termLoan.setBenchMarkPLR(termLoanObj.getDouble(12));
					termLoan.setPlr(termLoanObj.getDouble(13));
					termLoan.setRepaymentMoratorium(termLoanObj.getInt(14));
					termLoan.setFirstInstallmentDueDate(DateHelper.sqlToUtilDate(termLoanObj.getDate(15)));
					termLoan.setNoOfInstallments(termLoanObj.getInt(16));
					termLoan.setPeriodicity(termLoanObj.getInt(17));
					termLoan.setTypeOfPLR(termLoanObj.getString(18));
					termLoan.setAmtDisbursed(termLoanObj.getDouble(19));
					if (termLoanObj.getDate(20) != null) {
						if (DateHelper.sqlToUtilDate(termLoanObj.getDate(20))
								.equals(DateHelper.sqlToUtilDate(termLoanObj.getDate(21)))) {
							termLoan.setFirstDisbursementDate(DateHelper.sqlToUtilDate(termLoanObj.getDate(20)));
							termLoan.setFinalDisbursementDate(null);
						} else {
							termLoan.setFirstDisbursementDate(DateHelper.sqlToUtilDate(termLoanObj.getDate(20)));
							termLoan.setFinalDisbursementDate(DateHelper.sqlToUtilDate(termLoanObj.getDate(21)));
						}

					}

					Log.log(4, "ApplicationDAO", "getApplication", "termloan set....");
				}

				termLoanObj.close();
				termLoanObj = null;
			}

			if ((applicationLoanType.equals("TC")) || (applicationLoanType.equals("CC"))
					|| (applicationLoanType.equals("BO"))) {
				CallableStatement repaymentObj = connection.prepareCall("{?=call funcGetRepSchForAppRef(?,?,?,?,?,?)}");

				repaymentObj.setString(2, appRefNo);

				repaymentObj.registerOutParameter(1, 4);
				repaymentObj.registerOutParameter(7, 12);

				repaymentObj.registerOutParameter(3, 4);
				repaymentObj.registerOutParameter(4, 91);
				repaymentObj.registerOutParameter(5, 4);
				repaymentObj.registerOutParameter(6, 4);

				repaymentObj.executeQuery();
				int repaymentObjValue = repaymentObj.getInt(1);

				Log.log(5, "ApplicationDAO", "getApplication", "Term Loan Details :" + repaymentObjValue);

				if (repaymentObjValue != 0) {
					String error = repaymentObj.getString(7);

					repaymentObj.close();
					repaymentObj = null;

					connection.rollback();

					Log.log(2, "ApplicationDAO", "getApplication", "Term Loan Exception :" + error);

					throw new DatabaseException(error);
				}
				termLoan.setRepaymentMoratorium(repaymentObj.getInt(3));
				termLoan.setFirstInstallmentDueDate(DateHelper.sqlToUtilDate(repaymentObj.getDate(4)));
				termLoan.setPeriodicity(repaymentObj.getInt(5));
				termLoan.setNoOfInstallments(repaymentObj.getInt(6));
				repaymentObj.close();
				repaymentObj = null;
			}

			Log.log(4, "ApplicationDAO", "getApplication", "values of term loan object set successfully....");

			if ((applicationLoanType.equals("TC")) || (applicationLoanType.equals("CC"))
					|| (applicationLoanType.equals("BO"))) {
				CallableStatement tcOutObj = connection.prepareCall("{?=call funcGetTCOutStanding(?,?,?,?)}");

				tcOutObj.setString(2, appRefNo);

				tcOutObj.registerOutParameter(1, 4);
				tcOutObj.registerOutParameter(5, 12);
				tcOutObj.registerOutParameter(3, 8);
				tcOutObj.registerOutParameter(4, 91);

				tcOutObj.executeQuery();
				int tcOutObjValue = tcOutObj.getInt(1);

				Log.log(5, "ApplicationDAO", "getApplication", "TWorking Capital Loan Details :" + tcOutObjValue);

				if (tcOutObjValue == 1) {
					String error = tcOutObj.getString(5);

					tcOutObj.close();
					tcOutObj = null;

					connection.rollback();

					Log.log(2, "ApplicationDAO", "getApplication", "Working Capital Exception :" + error);

					throw new DatabaseException(error);
				}
				if (tcOutObjValue == 2) {
					termLoan.setPplOS(0.0D);

					termLoan.setPplOS(tcOutObj.getDouble(3));
					termLoan.setPplOsAsOnDate(DateHelper.sqlToUtilDate(tcOutObj.getDate(4)));

					tcOutObj.close();
					tcOutObj = null;
				} else {
					if (tcOutObj.getDouble(3) == -1.0D) {
						termLoan.setPplOS(0.0D);
					} else {
						termLoan.setPplOS(tcOutObj.getDouble(3));
						termLoan.setPplOsAsOnDate(DateHelper.sqlToUtilDate(tcOutObj.getDate(4)));
					}

					tcOutObj.close();
					tcOutObj = null;
				}

			}

			CallableStatement wcObj = connection
					.prepareCall("{?=call funcGetOldWCforAppRef(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			wcObj.setString(2, appRefNo);

			wcObj.registerOutParameter(1, 4);
			wcObj.registerOutParameter(17, 12);

			wcObj.registerOutParameter(3, 8);
			wcObj.registerOutParameter(4, 8);
			wcObj.registerOutParameter(5, 8);
			wcObj.registerOutParameter(6, 8);
			wcObj.registerOutParameter(7, 8);
			wcObj.registerOutParameter(8, 8);
			wcObj.registerOutParameter(9, 8);
			wcObj.registerOutParameter(10, 91);
			wcObj.registerOutParameter(11, 91);
			wcObj.registerOutParameter(12, 8);
			wcObj.registerOutParameter(13, 8);
			wcObj.registerOutParameter(14, 8);
			wcObj.registerOutParameter(15, 12);
			wcObj.registerOutParameter(16, 12);

			wcObj.executeQuery();
			int wcObjValue = wcObj.getInt(1);

			Log.log(5, "ApplicationDAO", "getApplication", "TWorking Capital Loan Details :" + wcObjValue);

			if ((wcObjValue != 0) && (wcObjValue != 2)) {
				String error = wcObj.getString(17);

				wcObj.close();
				wcObj = null;

				connection.rollback();

				Log.log(2, "ApplicationDAO", "getApplication", "Working Capital Exception :" + error);

				throw new DatabaseException(error);
			}
			if (wcObjValue == 2) {
				projectOutlayDetails.setWcFundBasedSanctioned(0.0D);
				projectOutlayDetails.setWcNonFundBasedSanctioned(0.0D);
				projectOutlayDetails.setWcPromoterContribution(0.0D);
				projectOutlayDetails.setWcSubsidyOrEquity(0.0D);
				projectOutlayDetails.setWcOthers(0.0D);
				if ((applicationLoanType.equals("WC")) || (applicationLoanType.equals("CC"))
						|| (applicationLoanType.equals("BO"))) {
					workingCapital.setLimitFundBasedInterest(0.0D);
					workingCapital.setLimitNonFundBasedCommission(0.0D);
					workingCapital.setLimitFundBasedSanctionedDate(null);
					workingCapital.setLimitNonFundBasedSanctionedDate(null);
					workingCapital.setCreditFundBased(0.0D);
					workingCapital.setCreditNonFundBased(0.0D);
					workingCapital.setWcPlr(0.0D);
					workingCapital.setWcTypeOfPLR(null);
					workingCapital.setWcInterestType("T");
					Log.log(2, "ApplicationDAO", "getApplication",
							"Working Interest type:" + workingCapital.getWcInterestType());
				}
			} else {
				projectOutlayDetails.setWcFundBasedSanctioned(wcObj.getDouble(3));
				projectOutlayDetails.setWcNonFundBasedSanctioned(wcObj.getDouble(4));
				projectOutlayDetails.setWcPromoterContribution(wcObj.getDouble(5));
				projectOutlayDetails.setWcSubsidyOrEquity(wcObj.getDouble(6));
				projectOutlayDetails.setWcOthers(wcObj.getDouble(7));
				if ((applicationLoanType.equals("WC")) || (applicationLoanType.equals("CC"))
						|| (applicationLoanType.equals("BO"))) {
					workingCapital.setLimitFundBasedInterest(wcObj.getDouble(8));
					workingCapital.setLimitNonFundBasedCommission(wcObj.getDouble(9));
					workingCapital.setLimitFundBasedSanctionedDate(DateHelper.sqlToUtilDate(wcObj.getDate(10)));
					workingCapital.setLimitNonFundBasedSanctionedDate(DateHelper.sqlToUtilDate(wcObj.getDate(11)));
					workingCapital.setCreditFundBased(wcObj.getDouble(12));
					workingCapital.setCreditNonFundBased(wcObj.getDouble(13));
					workingCapital.setWcPlr(wcObj.getDouble(14));
					workingCapital.setWcTypeOfPLR(wcObj.getString(15));
					workingCapital.setWcInterestType(wcObj.getString(16).trim());
					Log.log(2, "ApplicationDAO", "getApplication",
							"Working Interest type:" + workingCapital.getWcInterestType());
				}
				wcObj.close();
				wcObj = null;
			}

			if ((applicationLoanType.equals("WC")) || (applicationLoanType.equals("CC"))
					|| (applicationLoanType.equals("BO"))) {
				CallableStatement wcOutObj = connection.prepareCall("{?=call funcGetWCOutStanding(?,?,?,?,?,?,?,?)}");

				wcOutObj.setString(2, appRefNo);

				wcOutObj.registerOutParameter(1, 4);
				wcOutObj.registerOutParameter(9, 12);

				wcOutObj.registerOutParameter(3, 8);
				wcOutObj.registerOutParameter(4, 8);
				wcOutObj.registerOutParameter(5, 91);
				wcOutObj.registerOutParameter(6, 8);
				wcOutObj.registerOutParameter(7, 8);
				wcOutObj.registerOutParameter(8, 91);

				wcOutObj.executeQuery();
				int wcOutObjValue = wcOutObj.getInt(1);

				Log.log(5, "ApplicationDAO", "getApplication", "TWorking Capital Loan Details :" + wcOutObjValue);

				if (wcOutObjValue == 1) {
					String error = wcOutObj.getString(9);

					wcOutObj.close();
					wcOutObj = null;

					connection.rollback();

					Log.log(2, "ApplicationDAO", "getApplication", "Working Capital Exception :" + error);

					throw new DatabaseException(error);
				}
				if (wcOutObjValue == 2) {
					workingCapital.setOsFundBasedPpl(0.0D);
					workingCapital.setOsFundBasedInterestAmt(0.0D);
					workingCapital.setOsFundBasedAsOnDate(null);
					workingCapital.setOsNonFundBasedPpl(0.0D);
					workingCapital.setOsNonFundBasedCommissionAmt(0.0D);
					workingCapital.setOsNonFundBasedAsOnDate(null);
					wcOutObj.close();
					wcOutObj = null;
				} else {
					workingCapital.setOsFundBasedPpl(wcOutObj.getDouble(3));
					workingCapital.setOsFundBasedInterestAmt(wcOutObj.getDouble(4));
					workingCapital.setOsFundBasedAsOnDate(DateHelper.sqlToUtilDate(wcOutObj.getDate(5)));
					workingCapital.setOsNonFundBasedPpl(wcOutObj.getDouble(6));
					workingCapital.setOsNonFundBasedCommissionAmt(wcOutObj.getDouble(7));
					workingCapital.setOsNonFundBasedAsOnDate(DateHelper.sqlToUtilDate(wcOutObj.getDate(8)));
					wcOutObj.close();
					wcOutObj = null;
				}
			}

			Log.log(4, "ApplicationDAO", "getApplication", "values of working capital object set successfully....");

			CallableStatement getSecDtls = connection.prepareCall("{?=call funcGetOldSecDetails(?,?,?,?,?,?,?,?,?,?)}");

			getSecDtls.setString(2, appRefNo);

			getSecDtls.registerOutParameter(1, 4);
			getSecDtls.registerOutParameter(11, 12);

			getSecDtls.registerOutParameter(3, 8);
			getSecDtls.registerOutParameter(4, 12);
			getSecDtls.registerOutParameter(5, 8);
			getSecDtls.registerOutParameter(6, 8);
			getSecDtls.registerOutParameter(7, 8);
			getSecDtls.registerOutParameter(8, 8);
			getSecDtls.registerOutParameter(9, 8);
			getSecDtls.registerOutParameter(10, 8);

			getSecDtls.executeQuery();

			int getSecDtlsValue = getSecDtls.getInt(1);

			Log.log(5, "ApplicationDAO", "getApplication", "Securitization Details :" + getSecDtlsValue);

			if (getSecDtlsValue == 1) {
				String error = getSecDtls.getString(11);

				getSecDtls.close();
				getSecDtls = null;

				connection.rollback();

				Log.log(2, "ApplicationDAO", "getApplication", "Securitization Detail Exception :" + error);

				throw new DatabaseException(error);
			}
			if (getSecDtlsValue == 2) {
				securitization.setSpreadOverPLR(0.0D);
				securitization.setPplRepaymentInEqual("Y");
				securitization.setTangibleNetWorth(0.0D);
				securitization.setFixedACR(0.0D);
				securitization.setCurrentRatio(0.0D);
				securitization.setMinimumDSCR(0.0D);
				securitization.setAvgDSCR(0.0D);
				securitization.setFinancialPartUnit(0.0D);
				getSecDtls.close();
				getSecDtls = null;
			} else {
				securitization.setSpreadOverPLR(getSecDtls.getDouble(3));
				securitization.setPplRepaymentInEqual(getSecDtls.getString(4));
				securitization.setTangibleNetWorth(getSecDtls.getDouble(5));
				securitization.setFixedACR(getSecDtls.getDouble(6));
				securitization.setCurrentRatio(getSecDtls.getDouble(7));
				securitization.setMinimumDSCR(getSecDtls.getDouble(8));
				securitization.setAvgDSCR(getSecDtls.getDouble(9));
				securitization.setFinancialPartUnit(getSecDtls.getDouble(10));
				getSecDtls.close();
				getSecDtls = null;
			}

			MCGSProcessor mcgsProcessor = new MCGSProcessor();
			MCGFDetails mcgfDetails = mcgsProcessor.getOldMcgsDetails(appRefNo, connection);
			if (mcgfDetails != null) {
				application.setMCGFDetails(mcgfDetails);
			}

			mcgsProcessor = null;
			mcgfDetails = null;

			borrowerDetails.setSsiDetails(ssiDetails);
			application.setBorrowerDetails(borrowerDetails);

			application.setTermLoan(termLoan);

			application.setWc(workingCapital);
			application.setSecuritization(securitization);

			projectOutlayDetails.setPrimarySecurityDetails(primarySecurityDetails);
			application.setProjectOutlayDetails(projectOutlayDetails);

			borrowerDetails = null;
			ssiDetails = null;
			projectOutlayDetails = null;
			primarySecurityDetails = null;
			termLoan = null;
			workingCapital = null;
			securitization = null;

			Log.log(4, "ApplicationDAO", "getApplication", "Exited from appDAO successfully....");

			connection.commit();
		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "getApplication", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(4, "ApplicationDAO", "getApplication", ignore.getMessage());
			}

			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(4, "ApplicationDAO", "getApplication", "Exited. Memory : " + Runtime.getRuntime().freeMemory());

		return application;
	}

	public ArrayList getCgpans(String mliId, String borrowerId, int type, String borrowerName)
			throws DatabaseException {
		ArrayList cgpansList = new ArrayList();
		Connection connection = DBConnection.getConnection(false);

		String bankId = "";
		String zoneId = "";
		String branchId = "";

		Log.log(5, "ApplicationDAO", "getCgpans", "mliID ;" + mliId);
		Log.log(5, "ApplicationDAO", "getCgpans", "borrowerId;" + borrowerId);
		Log.log(5, "ApplicationDAO", "getCgpans", "type ;" + type);
		Log.log(5, "ApplicationDAO", "getCgpans", " borrower name;" + borrowerName);

		if ((mliId != "") || (borrowerId != "")) {
			Log.log(4, "ApplicationDAO", "getCgpans", "Entering into the method for gettin cgpans....");
			try {
				CallableStatement cgpanList = connection
						.prepareCall("{?=call packGetCGPANEnhancement.funcGetCGPANEnhancement(?,?,?,?,?,?,?)}");

				cgpanList.registerOutParameter(1, 4);
				cgpanList.registerOutParameter(7, -10);
				cgpanList.registerOutParameter(8, 12);

				if (type == 0) {
					cgpanList.setString(2, "TCE");
					Log.log(4, "ApplicationDAO", "getCgpans", "Setting TCE..");
				} else if (type == 1) {
					cgpanList.setString(2, "WCE");
					Log.log(4, "ApplicationDAO", "getCgpans", "Setting WCE..");
				} else if (type == 2) {
					cgpanList.setString(2, "WCR");
					Log.log(4, "ApplicationDAO", "getCgpans", "Setting WCR..");
				}

				bankId = mliId.substring(0, 4);

				zoneId = mliId.substring(4, 8);

				branchId = mliId.substring(8, 12);

				if (bankId.equals("")) {
					cgpanList.setString(3, null);
				} else
					cgpanList.setString(3, bankId);

				if (zoneId.equals("")) {
					cgpanList.setString(4, null);
				} else
					cgpanList.setString(4, zoneId);

				if (branchId.equals("")) {
					cgpanList.setString(5, null);
				} else
					cgpanList.setString(5, branchId);

				if (borrowerId.equals("")) {
					cgpanList.setString(6, null);
				} else
					cgpanList.setString(6, borrowerId);

				cgpanList.execute();

				int functionReturnValue = cgpanList.getInt(1);
				Log.log(5, "ApplicationDAO", "getCgpans", "Get Cgpans Result :" + functionReturnValue);

				if (functionReturnValue == 1) {
					String error = cgpanList.getString(8);

					cgpanList.close();
					cgpanList = null;

					connection.rollback();

					throw new DatabaseException(error);
				}
				ResultSet cgpanResults = (ResultSet) cgpanList.getObject(7);

				while (cgpanResults.next()) {
					String cgPAN = cgpanResults.getString(1);

					Log.log(5, "ApplicationDAO", "getCgpans", "CGPAN retrieved.." + cgPAN);

					cgpansList.add(cgPAN);
				}
				cgpanResults.close();
				cgpanResults = null;
				cgpanList.close();
				cgpanList = null;

				connection.commit();
			} catch (SQLException sqlException) {
				Log.log(4, "ApplicationDAO", "getCgpans", sqlException.getMessage());
				Log.logException(sqlException);
				try {
					connection.rollback();
				} catch (SQLException ignore) {
					Log.log(4, "ApplicationDAO", "getCgpans", ignore.getMessage());
				}

				throw new DatabaseException(sqlException.getMessage());
			} finally {
				DBConnection.freeConnection(connection);
			}

		}

		Log.log(4, "ApplicationDAO", "getCgpans", "Exited");

		return cgpansList;
	}

	public void updateApplication(Application application, String createdBy) throws DatabaseException {
		MCGSProcessor mcgsProcessor = new MCGSProcessor();
		RiskManagementProcessor rpProcessor = new RiskManagementProcessor();

		Connection connection = DBConnection.getConnection(false);
		try {
			String subSchemeName = rpProcessor.getSubScheme(application);
			application.setSubSchemeName(subSchemeName);

			int ssiRefNo = application.getBorrowerDetails().getSsiDetails().getBorrowerRefNo();

			String ssiRefNumber = Integer.toString(ssiRefNo);
			RpDAO rpDAO1 = new RpDAO();
			double prevTotalSancAmt = rpDAO1.getTotalSanctionedAmountNew(ssiRefNumber);
			ApplicationDAO appdao = new ApplicationDAO();

			double prevTotalHandloomSancAmt = appdao.getTotalSanctionedHandloomAmountNew(ssiRefNumber);

			double currentCreditAmount = 0.0D;
			if (application.getLoanType().equals("TC")) {
				currentCreditAmount = application.getTermLoan().getCreditGuaranteed();
			} else if (application.getLoanType().equals("CC")) {
				currentCreditAmount = application.getTermLoan().getCreditGuaranteed()
						+ application.getWc().getCreditFundBased() + application.getWc().getCreditNonFundBased();
			} else if (application.getLoanType().equals("WC")) {
				currentCreditAmount = application.getWc().getCreditFundBased()
						+ application.getWc().getCreditNonFundBased();
			}

			if ((currentCreditAmount > 200000.0D) && (application.getDcHandlooms().equals("Y"))) {
				throw new DatabaseException(
						"Credit to be Guaranteed Amount should be within the eligible amount available for Guarantee upto  Rs. 200000 as per ceiling fixed by Office of DC Handlooms");
			}
			if ((currentCreditAmount + prevTotalHandloomSancAmt > 200000.0D)
					&& (application.getDcHandlooms().equals("Y"))) {
				throw new DatabaseException("Guarantee of Rs. " + prevTotalHandloomSancAmt
						+ " is already available for the Borrower. Total Credit to be Guaranteed Amount should be within the eligible amount available for Guarantee upto upto Rs. 200000 as per ceiling fixed by Office of DC Handlooms");
			}

			updateApplicationDtl(application, createdBy, connection);

			if (application.getStatus().equals("RE")) {
				updateSsiDtl(application, createdBy, connection);
			}

			updateGuarantorDtls(application, createdBy, connection);

			updateTermLoanDtl(application, createdBy, connection);
			updateWCDtl(application, createdBy, connection);
			updateSecDetails(application, createdBy, connection);
			MCGFDetails mcgfDetails = application.getMCGFDetails();
			if (mcgfDetails != null) {
				String appRefNo = application.getAppRefNo();
				mcgfDetails.setApplicationReferenceNumber(appRefNo);
				mcgsProcessor.updateMCGSDetails(mcgfDetails, createdBy, connection);
			}

			connection.commit();
		} catch (SQLException e) {
			Log.log(2, "ApplicationDAO", "submitApp", e.getMessage());
			Log.logException(e);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(2, "ApplicationDAO", "submitApp", ignore.getMessage());
			}

			throw new DatabaseException("Unable to submit Application");
		} finally {
			DBConnection.freeConnection(connection);
		}
	}

	// bhu update here primarysecurityDetails
	public void updateApplicationDtl(Application application, String createdBy, Connection connection)
			throws DatabaseException {
		Log.log(4, "ApplicationDAO", "updateApplicationDtl", "Entered");

		String appLoanType = application.getLoanType();
		// System.out.println("In AppDAO appLoanType:" + appLoanType);

		Log.log(5, "ApplicationDAO", "updateApplicationDtl", "Application Loan type in DAO class :" + appLoanType);
		try {
			RiskManagementProcessor rpProcessor = new RiskManagementProcessor();
			SubSchemeValues subSchemeValues = new SubSchemeValues();

			String subSchemeName = rpProcessor.getSubScheme(application);
			if (!subSchemeName.equals("GLOBAL")) {
				subSchemeValues = rpProcessor.getSubSchemeValues(subSchemeName);
				if (subSchemeValues == null) {
					double exposureAmount = subSchemeValues.getMaxBorrowerExposureAmount();

					CallableStatement approvedAmount = connection.prepareCall("{?=call funcGetApprovedAmt(?,?,?)}");

					approvedAmount.registerOutParameter(1, 4);
					Log.log(5, "ApplicationDAO", "updateApplicationDtl",
							"SSi Ref No :" + application.getBorrowerDetails().getSsiDetails().getBorrowerRefNo());
					approvedAmount.setDouble(2, application.getBorrowerDetails().getSsiDetails().getBorrowerRefNo());
					approvedAmount.registerOutParameter(3, 8);
					approvedAmount.registerOutParameter(4, 12);

					approvedAmount.executeQuery();

					int approvedAmountValue = approvedAmount.getInt(1);
					Log.log(5, "ApplicationDAO", "updateApplicationDtl",
							"SSi Details Approved Amount result :" + approvedAmountValue);

					if (approvedAmountValue == 1) {
						String error = approvedAmount.getString(4);

						approvedAmount.close();
						approvedAmount = null;

						connection.rollback();

						Log.log(2, "ApplicationDAO", "updateApplicationDtl",
								"SSI Detail Approved Amount Exception :" + error);

						throw new DatabaseException(error);
					}
					int approvedAmt = approvedAmount.getInt(3);

					approvedAmount.close();
					approvedAmount = null;

					if (approvedAmt >= exposureAmount) {
						throw new DatabaseException(
								"Borrower has crossed his exposure limit.Hence ineligible to submit a new application");
					}

				}

			}

			Log.log(4, "ApplicationDAO", "updateApplicationDtl", "Before updating application in the DAO class....");

			// .prepareCall("{?=call
			// funcUpdatetApplicationDtl(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");//32
			// ENTRIES

			CallableStatement updateAppDtl = connection.prepareCall(
					"{?=call funcUpdatetApplicationDtl(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			Log.log(4, "ApplicationDAO", "updateApplicationDtl", "After connecting");

			updateAppDtl.registerOutParameter(1, 4);
			updateAppDtl.registerOutParameter(32, 12);// changed-->31->32

			updateAppDtl.setString(2, application.getAppRefNo());
			Log.log(4, "ApplicationDAO", "updateApplication", "app ref no :" + application.getAppRefNo());

			updateAppDtl.setInt(3, application.getBorrowerDetails().getSsiDetails().getBorrowerRefNo());
			Log.log(4, "ApplicationDAO", "updateApplication",
					"borr ref no :" + application.getBorrowerDetails().getSsiDetails().getBorrowerRefNo());

			updateAppDtl.setString(4, application.getScheme());
			Log.log(4, "ApplicationDAO", "updateApplication", "scheme :" + application.getScheme());
			updateAppDtl.setString(5, application.getBankId());
			Log.log(4, "ApplicationDAO", "updateApplication", "bank id:" + application.getBankId());

			updateAppDtl.setString(6, application.getZoneId());
			Log.log(4, "ApplicationDAO", "updateApplication", "zone id:" + application.getZoneId());

			updateAppDtl.setString(7, application.getBranchId());
			Log.log(4, "ApplicationDAO", "updateApplication", "branch id:" + application.getBranchId());

			updateAppDtl.setString(8, application.getMliBranchName());
			Log.log(4, "ApplicationDAO", "updateApplication", "branch name:" + application.getMliBranchName());

			if ((application.getMliBranchCode() != null) && (!application.getMliBranchCode().equals(""))) {
				updateAppDtl.setString(9, application.getMliBranchCode());
			} else
				updateAppDtl.setString(9, null);

			Log.log(4, "ApplicationDAO", "updateApplication", "branch code:" + application.getMliBranchCode());

			updateAppDtl.setString(10, application.getMliRefNo());
			Log.log(4, "ApplicationDAO", "updateApplication", "mli ref no:" + application.getMliRefNo());

			if (appLoanType.equals("CC")) {
				updateAppDtl.setString(11, "Y");
			} else {
				updateAppDtl.setString(11, "N");
			}

			updateAppDtl.setString(12, createdBy);
			Log.log(4, "ApplicationDAO", "updateApplication", "created by:" + createdBy);

			updateAppDtl.setString(13, application.getLoanType());
			Log.log(4, "ApplicationDAO", "updateApplication", "loan type:" + application.getLoanType());

			String collateralSecurityValue = application.getProjectOutlayDetails().getCollateralSecurityTaken();

			updateAppDtl.setString(14, collateralSecurityValue);
			Log.log(4, "ApplicationDAO", "updateApplication", "collateralSecurityValue:" + collateralSecurityValue);

			String thirdPartyValue = application.getProjectOutlayDetails().getThirdPartyGuaranteeTaken();

			updateAppDtl.setString(15, thirdPartyValue);
			Log.log(4, "ApplicationDAO", "updateApplication", "thirdPartyValue:" + thirdPartyValue);

			if ((application.getProjectOutlayDetails().getSubsidyName() != null)
					&& (!application.getProjectOutlayDetails().getSubsidyName().equals(""))) {
				updateAppDtl.setString(16, application.getProjectOutlayDetails().getSubsidyName());
			} else {
				updateAppDtl.setString(16, null);
			}

			Log.log(4, "ApplicationDAO", "updateApplication",
					"(application.getProjectOutlayDetails()).getSubsidyName():"
							+ application.getProjectOutlayDetails().getSubsidyName());

			String rehabilitationValue = application.getRehabilitation();

			if ((rehabilitationValue != null) && (!rehabilitationValue.equals(""))) {
				updateAppDtl.setString(17, rehabilitationValue);
			} else {
				updateAppDtl.setString(17, "N");
			}
			Log.log(4, "ApplicationDAO", "updateApplication", "rehabilitationValue:" + rehabilitationValue);

			updateAppDtl.setDouble(18, application.getProjectOutlayDetails().getProjectOutlay());
			Log.log(4, "ApplicationDAO", "updateApplication",
					"project outlay:" + application.getProjectOutlayDetails().getProjectOutlay());

			String cgpanVal = application.getCgpanReference();

			if ((cgpanVal != null) && (!cgpanVal.equals(""))) {
				updateAppDtl.setString(19, cgpanVal);
			} else {
				updateAppDtl.setNull(19, 12);
			}

			Log.log(4, "ApplicationDAO", "updateApplication", "cgpanVal:" + cgpanVal);

			updateAppDtl.setString(20, createdBy);
			Log.log(4, "ApplicationDAO", "updateApplication", "createdBy:" + createdBy);

			if ((application.getRemarks() != null) && (!application.getRemarks().equals(""))) {
				updateAppDtl.setString(21, application.getRemarks());
			} else
				updateAppDtl.setString(21, null);

			Log.log(4, "ApplicationDAO", "updateApplication", "remarks:" + application.getRemarks());

			if (application.getWcEnhancement()) {
				updateAppDtl.setString(22, "EN");
			} else if ((application.getStatus().equals("AP")) || (application.getStatus().equals("AM"))) {
				updateAppDtl.setString(22, "AM");
			} else if (application.getStatus().equals("HO")) {
				updateAppDtl.setString(22, "HO");
			} else if (application.getStatus().equals("EH")) {
				updateAppDtl.setString(22, "EH");
			} else if (application.getStatus().equals("EN")) {
				updateAppDtl.setString(22, "EN");
			} else {
				updateAppDtl.setString(22, "MO");
			}
			updateAppDtl.setString(23, application.getSubSchemeName());
			updateAppDtl.setString(24, application.getHandiCrafts());
			updateAppDtl.setString(25, application.getDcHandicrafts());
			updateAppDtl.setString(26, application.getIcardNo());

			if (application.getIcardIssueDate() != null) {
				updateAppDtl.setDate(27, new java.sql.Date(application.getIcardIssueDate().getTime()));
			} else {
				updateAppDtl.setDate(27, null);
			}
			updateAppDtl.setString(28, application.getDcHandlooms());
			updateAppDtl.setString(29, application.getWeaverCreditScheme());
			System.out.println(application.getHybridSecurity() + "-" + application.getMovCollateratlSecurityAmt()
					+ "OUT -AP >>Guarantee For  >>Enhancement in WC DAO Y-"
					+ application.getImmovCollateratlSecurityAmt() + "-" + application.getTotalMIcollatSecAmt() + "-");
			updateAppDtl.setString(30, application.getHandloomchk());
			Log.log(4, "ApplicationDAO", "updateApplication", "status:" + application.getStatus());
			// BHU
			/*
			 * String isPrimarySecurity= application.getIsPrimarySecurity();
			 * 
			 * updateAppDtl.setString(31, application.getIsPrimarySecurity()); Log.log(4,
			 * "ApplicationDAO", "updateApplication","isPrimarySecurity:"
			 * +application.getIsPrimarySecurity());
			 */
			String isPrimarySecurity = application.getProjectOutlayDetails().getIsPrimarySecurity();
			updateAppDtl.setString(31, isPrimarySecurity);
			// Added for gst changes
			updateAppDtl.setString(33, application.getGstNo());
			updateAppDtl.setString(34, application.getGstState());
			// updateAppDtl.setString(35, application.getMliBranchCode());
			Log.log(4, "ApplicationDAO", "updateApplication", "isPrimarySecurity:" + isPrimarySecurity);

// added for Hybrid security
			if (null != application.getHybridSecurity()) {
				System.out.println(application.getHybridSecurity() + "-" + application.getMovCollateratlSecurityAmt()
						+ "-AP >>Guarantee For  >>Enhancement in WC DAO Y-"
						+ application.getImmovCollateratlSecurityAmt() + "-" + application.getTotalMIcollatSecAmt()
						+ "-");
				updateAppDtl.setString(35, application.getHybridSecurity());
				updateAppDtl.setDouble(36, application.getMovCollateratlSecurityAmt());
				updateAppDtl.setDouble(37, application.getImmovCollateratlSecurityAmt());
				updateAppDtl.setDouble(38, application.getTotalMIcollatSecAmt());
			} else {
				System.out.println("-" + application.getMovCollateratlSecurityAmt()
						+ "- AP >>Guarantee For  >>Enhancement in WC DAO N-"
						+ application.getImmovCollateratlSecurityAmt() + "-" + application.getTotalMIcollatSecAmt()
						+ "-");
				updateAppDtl.setString(35, " ");
				updateAppDtl.setDouble(36, 0.0);
				updateAppDtl.setDouble(37, 0.0);
				updateAppDtl.setDouble(38, 0.0);
			}
			updateAppDtl.setDouble(39, application.getProMobileNo());

			// Hybrid security

			// =====================================================================

			updateAppDtl.executeQuery();
			int functionReturnValue = updateAppDtl.getInt(1);

			if (functionReturnValue == 1) {
				String error = updateAppDtl.getString(32);// 31-->32

				updateAppDtl.close();
				updateAppDtl = null;

				connection.rollback();

				throw new DatabaseException(error);
			}
			updateAppDtl.close();
			updateAppDtl = null;

		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			Log.log(4, "ApplicationDAO", "updateApplication", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				ignore.printStackTrace();
				Log.log(4, "ApplicationDAO", "updateApplication", ignore.getMessage());
			}

			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);

		}
		Log.log(4, "ApplicationDAO", "updateApplication", "Exited");
	}

	public void updateSsiDtl(Application apps, String createdBy, Connection connection) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "updateSsiDtl", "Entered");

		int ssiRefNo = apps.getBorrowerDetails().getSsiDetails().getBorrowerRefNo();
		try {
			String cgbid = apps.getBorrowerDetails().getSsiDetails().getCgbid();

			String statusofApp = apps.getStatus();

			Log.log(5, "ApplicationDAO", "updateSsiDtl", "CGBID from borrowerDetails :" + cgbid);

			String cgpan = apps.getCgpan();

			Log.log(5, "ApplicationDAO", "updateSsiDtl", "CGPAN from borrowerDetails :" + cgpan);

			if ((cgpan != null) && (!cgpan.equals(""))) {
				Log.log(4, "ApplicationDAO", "updateSsiDtl", "Entering the method if cgbid is not null");

				CallableStatement ssiRefNoForCgbid = connection.prepareCall("{?=call funcGetSSIRefNoforBID(?,?,?)}");

				ssiRefNoForCgbid.registerOutParameter(1, 4);
				ssiRefNoForCgbid.registerOutParameter(2, 4);
				ssiRefNoForCgbid.registerOutParameter(4, 12);

				ssiRefNoForCgbid.setString(3, apps.getBorrowerDetails().getSsiDetails().getCgbid());

				ssiRefNoForCgbid.executeQuery();
				int ssiRefNoForCgbidValue = ssiRefNoForCgbid.getInt(1);

				if (ssiRefNoForCgbidValue == 1) {
					String error = ssiRefNoForCgbid.getString(4);

					ssiRefNoForCgbid.close();
					ssiRefNoForCgbid = null;

					connection.rollback();

					throw new DatabaseException(error);
				}
				apps.getBorrowerDetails().getSsiDetails().setBorrowerRefNo(ssiRefNoForCgbid.getInt(2));

				ssiRefNoForCgbid.close();
				ssiRefNoForCgbid = null;
			} else if ((cgbid != null) && (!cgbid.equals("")) && (!statusofApp.equals("RE"))) {
				Log.log(4, "ApplicationDAO", "updateSsiDtl", "Entering teh method if cgpan is not null");

				CallableStatement ssiRefNoForCgpan = connection.prepareCall("{?=call funcGetSSIRefNoforCGPAN(?,?,?)}");

				ssiRefNoForCgpan.registerOutParameter(1, 4);
				ssiRefNoForCgpan.registerOutParameter(2, 4);
				ssiRefNoForCgpan.registerOutParameter(4, 12);

				ssiRefNoForCgpan.setString(3, apps.getCgpan());

				ssiRefNoForCgpan.executeQuery();
				int ssiRefNoForCgpanValue = ssiRefNoForCgpan.getInt(1);

				if (ssiRefNoForCgpanValue == 1) {
					String error = ssiRefNoForCgpan.getString(4);

					ssiRefNoForCgpan.close();
					ssiRefNoForCgpan = null;

					connection.rollback();

					throw new DatabaseException(error);
				}
				apps.getBorrowerDetails().getSsiDetails().setBorrowerRefNo(ssiRefNoForCgpan.getInt(2));

				ssiRefNoForCgpan.close();
				ssiRefNoForCgpan = null;
			}

			Log.log(4, "ApplicationDAO", "updateSsiDtl", "Entering teh method if both are is not null");

			CallableStatement updateSsiDtl = connection.prepareCall(
					"{?=call funcUpdateSSIDetail(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			updateSsiDtl.registerOutParameter(1, 4);
			updateSsiDtl.registerOutParameter(29, 12);

			updateSsiDtl.setInt(2, ssiRefNo);

			Log.log(5, "ApplicationDAO", "updateSsiDtl", "SSI RefNo :" + ssiRefNo);

			updateSsiDtl.setString(4, "N");

			String assistedValue = apps.getBorrowerDetails().getAssistedByBank();
			Log.log(5, "ApplicationDAO", "updateSsiDtl", "bank Assistance :" + assistedValue);

			updateSsiDtl.setString(3, apps.getBorrowerDetails().getPreviouslyCovered());
			Log.log(5, "ApplicationDAO", "updateSsiDtl",
					"covered value :" + apps.getBorrowerDetails().getPreviouslyCovered());

			updateSsiDtl.setNull(5, 12);

			String npaValue = apps.getBorrowerDetails().getNpa();

			updateSsiDtl.setString(6, npaValue);
			Log.log(5, "ApplicationDAO", "updateSsiDtl", "NPA" + apps.getBorrowerDetails().getNpa());

			if ((apps.getBorrowerDetails().getSsiDetails().getConstitution() != null)
					&& (!apps.getBorrowerDetails().getSsiDetails().getConstitution().equals(""))) {
				updateSsiDtl.setString(7, apps.getBorrowerDetails().getSsiDetails().getConstitution());
			} else {
				updateSsiDtl.setString(7, null);
			}
			Log.log(5, "ApplicationDAO", "updateSsiDtl",
					"const :" + apps.getBorrowerDetails().getSsiDetails().getConstitution());

			updateSsiDtl.setString(8, apps.getBorrowerDetails().getSsiDetails().getSsiType());
			Log.log(5, "ApplicationDAO", "updateSsiDtl",
					"ssi type :" + apps.getBorrowerDetails().getSsiDetails().getSsiType());

			updateSsiDtl.setString(9, apps.getBorrowerDetails().getSsiDetails().getSsiName());
			Log.log(5, "ApplicationDAO", "updateSsiDtl",
					"ssi name : " + apps.getBorrowerDetails().getSsiDetails().getSsiName());

			updateSsiDtl.setString(10, apps.getBorrowerDetails().getSsiDetails().getRegNo());
			Log.log(5, "ApplicationDAO", "updateSsiDtl",
					"reg no :" + apps.getBorrowerDetails().getSsiDetails().getRegNo());

			updateSsiDtl.setNull(11, 91);

			if ((apps.getBorrowerDetails().getSsiDetails().getSsiITPan() != null)
					&& (!apps.getBorrowerDetails().getSsiDetails().getSsiITPan().equals(""))) {
				updateSsiDtl.setString(12, apps.getBorrowerDetails().getSsiDetails().getSsiITPan());
			} else {
				updateSsiDtl.setString(12, null);
			}
			Log.log(5, "ApplicationDAO", "updateSsiDtl",
					"ITPAN :" + apps.getBorrowerDetails().getSsiDetails().getSsiITPan());

			if ((apps.getBorrowerDetails().getSsiDetails().getActivityType() != null)
					&& (!apps.getBorrowerDetails().getSsiDetails().getActivityType().equals(""))) {
				updateSsiDtl.setString(13, apps.getBorrowerDetails().getSsiDetails().getActivityType());
			} else {
				updateSsiDtl.setString(13, null);
			}
			Log.log(5, "ApplicationDAO", "updateSsiDtl",
					"Activity Type :" + apps.getBorrowerDetails().getSsiDetails().getActivityType());

			if (apps.getBorrowerDetails().getSsiDetails().getEmployeeNos() == 0) {
				updateSsiDtl.setNull(14, 4);
			} else
				updateSsiDtl.setInt(14, apps.getBorrowerDetails().getSsiDetails().getEmployeeNos());

			Log.log(5, "ApplicationDAO", "updateSsiDtl",
					"No of employees :" + apps.getBorrowerDetails().getSsiDetails().getEmployeeNos());

			if (apps.getBorrowerDetails().getSsiDetails().getProjectedSalesTurnover() == 0.0D) {
				updateSsiDtl.setNull(15, 8);
			} else
				updateSsiDtl.setDouble(15, apps.getBorrowerDetails().getSsiDetails().getProjectedSalesTurnover());

			Log.log(5, "ApplicationDAO", "updateSsiDtl",
					"Turnover :" + apps.getBorrowerDetails().getSsiDetails().getProjectedSalesTurnover());

			if (apps.getBorrowerDetails().getSsiDetails().getProjectedExports() == 0.0D) {
				updateSsiDtl.setNull(16, 8);
			} else
				updateSsiDtl.setDouble(16, apps.getBorrowerDetails().getSsiDetails().getProjectedExports());

			Log.log(5, "ApplicationDAO", "updateSsiDtl",
					"Exports :" + apps.getBorrowerDetails().getSsiDetails().getProjectedExports());

			updateSsiDtl.setString(17, apps.getBorrowerDetails().getSsiDetails().getAddress());
			Log.log(5, "ApplicationDAO", "updateSsiDtl",
					"Address :" + apps.getBorrowerDetails().getSsiDetails().getAddress());

			if ((apps.getBorrowerDetails().getSsiDetails().getCity() != null)
					&& (!apps.getBorrowerDetails().getSsiDetails().getCity().equals(""))) {
				updateSsiDtl.setString(18, apps.getBorrowerDetails().getSsiDetails().getCity());
			} else {
				updateSsiDtl.setString(18, null);
			}
			Log.log(5, "ApplicationDAO", "updateSsiDtl",
					"City :" + apps.getBorrowerDetails().getSsiDetails().getCity());

			updateSsiDtl.setString(19, apps.getBorrowerDetails().getSsiDetails().getPincode());
			Log.log(5, "ApplicationDAO", "updateSsiDtl",
					"Pincode :" + apps.getBorrowerDetails().getSsiDetails().getPincode());

			updateSsiDtl.setString(20, "Y");
			updateSsiDtl.setString(21, apps.getBorrowerDetails().getSsiDetails().getDistrict());
			Log.log(5, "ApplicationDAO", "updateSsiDtl",
					"District :" + apps.getBorrowerDetails().getSsiDetails().getDistrict());

			updateSsiDtl.setString(22, apps.getBorrowerDetails().getSsiDetails().getState());
			Log.log(5, "ApplicationDAO", "updateSsiDtl",
					"State :" + apps.getBorrowerDetails().getSsiDetails().getState());

			if ((apps.getBorrowerDetails().getSsiDetails().getIndustryNature() != null)
					&& (!apps.getBorrowerDetails().getSsiDetails().getIndustryNature().equals(""))) {
				updateSsiDtl.setString(23, apps.getBorrowerDetails().getSsiDetails().getIndustryNature());
			} else {
				updateSsiDtl.setString(23, null);
			}
			Log.log(5, "ApplicationDAO", "updateSsiDtl",
					"Industry Name :" + apps.getBorrowerDetails().getSsiDetails().getIndustryNature());

			updateSsiDtl.setString(24, "A");

			if ((apps.getBorrowerDetails().getSsiDetails().getIndustrySector() != null)
					&& (!apps.getBorrowerDetails().getSsiDetails().getIndustrySector().equals(""))) {
				updateSsiDtl.setString(25, apps.getBorrowerDetails().getSsiDetails().getIndustrySector());
			} else {
				updateSsiDtl.setString(25, null);
			}
			Log.log(5, "ApplicationDAO", "updateSsiDtl",
					"Industry Sector :" + apps.getBorrowerDetails().getSsiDetails().getIndustrySector());

			if (apps.getBorrowerDetails().getOsAmt() == 0.0D) {
				updateSsiDtl.setNull(26, 8);
			} else
				updateSsiDtl.setDouble(26, apps.getBorrowerDetails().getOsAmt());

			Log.log(5, "ApplicationDAO", "updateSsiDtl", "Os Amt :" + apps.getBorrowerDetails().getOsAmt());

			MCGFDetails mcgfDetails = apps.getMCGFDetails();
			if (mcgfDetails != null) {
				updateSsiDtl.setString(27, "Y");
			} else {
				updateSsiDtl.setString(27, "N");
			}

			updateSsiDtl.setString(28, createdBy);
			updateSsiDtl.executeQuery();
			int updateSsiDtlValue = updateSsiDtl.getInt(1);
			Log.log(5, "ApplicationDAO", "updateSsiDtl", "Updating SSI Detail value" + updateSsiDtlValue);

			if (updateSsiDtlValue == 1) {
				String error = updateSsiDtl.getString(29);

				updateSsiDtl.close();
				updateSsiDtl = null;

				connection.rollback();

				Log.log(2, "ApplicationDAO", "updateSsiDtl", "Update SSi Detail exception :" + error);

				throw new DatabaseException(error);
			}

			updateSsiDtl.close();
			updateSsiDtl = null;
		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "updateSsiDtl", sqlException.getMessage());

			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
			}
			throw new DatabaseException(sqlException.getMessage());
		}
		Log.log(4, "ApplicationDAO", "updateSsiDtl", "Exited");
	}

	public void updateOtherDtls(Application apps, String userId, Connection connection) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "updateOtherDtls", "Entered");
		try {
			Log.log(4, "ApplicationDAO", "updateOtherDtls", "Entering Updating Other Details method");

			CallableStatement updatePromoterDtls = connection
					.prepareCall("{?=call funcUpdatePromoterDtl(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			updatePromoterDtls.registerOutParameter(1, 4);
			updatePromoterDtls.registerOutParameter(23, 12);

			updatePromoterDtls.setInt(2, apps.getBorrowerDetails().getSsiDetails().getBorrowerRefNo());
			updatePromoterDtls.setString(3, apps.getBorrowerDetails().getSsiDetails().getCpTitle());
			updatePromoterDtls.setString(4, apps.getBorrowerDetails().getSsiDetails().getCpFirstName());

			if ((apps.getBorrowerDetails().getSsiDetails().getCpMiddleName() != null)
					&& (!apps.getBorrowerDetails().getSsiDetails().getCpMiddleName().equals(""))) {
				updatePromoterDtls.setString(5, apps.getBorrowerDetails().getSsiDetails().getCpMiddleName());
			} else {
				updatePromoterDtls.setString(5, null);
			}

			updatePromoterDtls.setString(6, apps.getBorrowerDetails().getSsiDetails().getCpLastName());

			if ((apps.getBorrowerDetails().getSsiDetails().getCpITPAN() != null)
					&& (!apps.getBorrowerDetails().getSsiDetails().getCpITPAN().equals(""))) {
				updatePromoterDtls.setString(7, apps.getBorrowerDetails().getSsiDetails().getCpITPAN());
			} else {
				updatePromoterDtls.setString(7, null);
			}

			updatePromoterDtls.setString(8, apps.getBorrowerDetails().getSsiDetails().getCpGender());

			if ((!apps.getBorrowerDetails().getSsiDetails().getCpDOB().toString().equals(""))
					&& (apps.getBorrowerDetails().getSsiDetails().getCpDOB() != null)) {
				updatePromoterDtls.setDate(9,
						new java.sql.Date(apps.getBorrowerDetails().getSsiDetails().getCpDOB().getTime()));
			} else
				updatePromoterDtls.setDate(9, null);

			if ((apps.getBorrowerDetails().getSsiDetails().getCpLegalID() != null)
					&& (!apps.getBorrowerDetails().getSsiDetails().getCpLegalID().equals(""))) {
				updatePromoterDtls.setString(10, apps.getBorrowerDetails().getSsiDetails().getCpLegalID());
			} else {
				updatePromoterDtls.setString(10, null);
			}

			if ((apps.getBorrowerDetails().getSsiDetails().getCpLegalIdValue() != null)
					&& (!apps.getBorrowerDetails().getSsiDetails().getCpLegalIdValue().equals(""))) {
				updatePromoterDtls.setString(11, apps.getBorrowerDetails().getSsiDetails().getCpLegalIdValue());
			} else {
				updatePromoterDtls.setString(11, null);
			}

			if ((apps.getBorrowerDetails().getSsiDetails().getFirstName() != null)
					&& (!apps.getBorrowerDetails().getSsiDetails().getFirstName().equals(""))) {
				updatePromoterDtls.setString(12, apps.getBorrowerDetails().getSsiDetails().getFirstName());
			} else {
				updatePromoterDtls.setString(12, null);
			}

			if ((apps.getBorrowerDetails().getSsiDetails().getFirstDOB() != null)
					&& (!apps.getBorrowerDetails().getSsiDetails().getFirstDOB().toString().equals(""))) {
				updatePromoterDtls.setDate(13,
						new java.sql.Date(apps.getBorrowerDetails().getSsiDetails().getFirstDOB().getTime()));
			} else
				updatePromoterDtls.setDate(13, null);

			if ((apps.getBorrowerDetails().getSsiDetails().getFirstItpan() != null)
					&& (!apps.getBorrowerDetails().getSsiDetails().getFirstItpan().equals(""))) {
				updatePromoterDtls.setString(14, apps.getBorrowerDetails().getSsiDetails().getFirstItpan());
			} else {
				updatePromoterDtls.setString(14, null);
			}

			if ((apps.getBorrowerDetails().getSsiDetails().getSecondName() != null)
					&& (!apps.getBorrowerDetails().getSsiDetails().getSecondName().equals(""))) {
				updatePromoterDtls.setString(15, apps.getBorrowerDetails().getSsiDetails().getSecondName());
			} else {
				updatePromoterDtls.setString(15, null);
			}

			if ((apps.getBorrowerDetails().getSsiDetails().getSecondDOB() != null)
					&& (!apps.getBorrowerDetails().getSsiDetails().getSecondDOB().toString().equals(""))) {
				updatePromoterDtls.setDate(16,
						new java.sql.Date(apps.getBorrowerDetails().getSsiDetails().getSecondDOB().getTime()));
			} else
				updatePromoterDtls.setDate(16, null);

			if ((apps.getBorrowerDetails().getSsiDetails().getSecondItpan() != null)
					&& (!apps.getBorrowerDetails().getSsiDetails().getSecondItpan().equals(""))) {
				updatePromoterDtls.setString(17, apps.getBorrowerDetails().getSsiDetails().getSecondItpan());
			} else {
				updatePromoterDtls.setString(17, null);
			}

			if ((apps.getBorrowerDetails().getSsiDetails().getThirdName() != null)
					&& (!apps.getBorrowerDetails().getSsiDetails().getThirdName().equals(""))) {
				updatePromoterDtls.setString(18, apps.getBorrowerDetails().getSsiDetails().getThirdName());
			} else {
				updatePromoterDtls.setString(18, null);
			}

			if ((apps.getBorrowerDetails().getSsiDetails().getThirdDOB() != null)
					&& (!apps.getBorrowerDetails().getSsiDetails().getThirdDOB().toString().equals(""))) {
				updatePromoterDtls.setDate(19,
						new java.sql.Date(apps.getBorrowerDetails().getSsiDetails().getThirdDOB().getTime()));
			} else
				updatePromoterDtls.setDate(19, null);

			if ((apps.getBorrowerDetails().getSsiDetails().getThirdItpan() != null)
					&& (!apps.getBorrowerDetails().getSsiDetails().getThirdItpan().equals(""))) {
				updatePromoterDtls.setString(20, apps.getBorrowerDetails().getSsiDetails().getThirdItpan());
			} else {
				updatePromoterDtls.setString(20, null);
			}

			if ((apps.getBorrowerDetails().getSsiDetails().getSocialCategory() != null)
					&& (!apps.getBorrowerDetails().getSsiDetails().getSocialCategory().equals(""))) {
				updatePromoterDtls.setString(21, apps.getBorrowerDetails().getSsiDetails().getSocialCategory());
			} else {
				updatePromoterDtls.setString(21, null);
			}

			updatePromoterDtls.setString(22, userId);

			updatePromoterDtls.executeQuery();
			int updatePromoterDtlsValue = updatePromoterDtls.getInt(1);
			Log.log(5, "ApplicationDAO", "updateOtherDtls", "promoters Details result" + updatePromoterDtlsValue);

			if (updatePromoterDtlsValue == 1) {
				String error = updatePromoterDtls.getString(23);

				updatePromoterDtls.close();
				updatePromoterDtls = null;

				connection.rollback();

				throw new DatabaseException(error);
			}

			updatePromoterDtls.close();
			updatePromoterDtls = null;
		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "updateOtherDtls", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
			}
			throw new DatabaseException(sqlException.getMessage());
		}
	}

	public void updateGuarantorDtls(Application apps, String userId, Connection connection) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "updateGuarantorDtls", "Entered");
		try {
			if ((apps.getProjectOutlayDetails().getGuarantorsName1() != null)
					&& (!apps.getProjectOutlayDetails().getGuarantorsName1().equals(""))) {
				CallableStatement updateGuarantorDtl1 = connection
						.prepareCall("{?=call funcUpdatePersonalGuar(?,?,?,?,?)}");

				updateGuarantorDtl1.registerOutParameter(1, 4);
				updateGuarantorDtl1.registerOutParameter(6, 12);

				updateGuarantorDtl1.setString(2, apps.getAppRefNo());

				updateGuarantorDtl1.setString(3, apps.getProjectOutlayDetails().getGuarantorsName1());

				updateGuarantorDtl1.setDouble(4, apps.getProjectOutlayDetails().getGuarantorsNetWorth1());

				updateGuarantorDtl1.setString(5, userId);

				updateGuarantorDtl1.executeQuery();
				int updateGuarantorDtl1Value = updateGuarantorDtl1.getInt(1);
				Log.log(5, "ApplicationDAO", "updateOtherDtls",
						"Guarantors Details1 result :" + updateGuarantorDtl1Value);

				if (updateGuarantorDtl1Value == 1) {
					String error = updateGuarantorDtl1.getString(6);

					updateGuarantorDtl1.close();
					updateGuarantorDtl1 = null;

					connection.rollback();

					throw new DatabaseException(error);
				}

				updateGuarantorDtl1.close();
				updateGuarantorDtl1 = null;
			}

			if ((apps.getProjectOutlayDetails().getGuarantorsName2() != null)
					&& (!apps.getProjectOutlayDetails().getGuarantorsName2().equals(""))) {
				CallableStatement updateGuarantorDtl2 = connection
						.prepareCall("{?=call funcUpdatePersonalGuar(?,?,?,?,?)}");

				updateGuarantorDtl2.registerOutParameter(1, 4);
				updateGuarantorDtl2.registerOutParameter(6, 12);

				updateGuarantorDtl2.setString(2, apps.getAppRefNo());

				updateGuarantorDtl2.setString(3, apps.getProjectOutlayDetails().getGuarantorsName2());

				updateGuarantorDtl2.setDouble(4, apps.getProjectOutlayDetails().getGuarantorsNetWorth2());

				updateGuarantorDtl2.setString(5, userId);

				updateGuarantorDtl2.executeQuery();
				int updateGuarantorDtl2Value = updateGuarantorDtl2.getInt(1);
				Log.log(5, "ApplicationDAO", "updateOtherDtls",
						"Guarantors Details result :" + updateGuarantorDtl2Value);

				if (updateGuarantorDtl2Value == 1) {
					String error = updateGuarantorDtl2.getString(6);

					updateGuarantorDtl2.close();
					updateGuarantorDtl2 = null;

					connection.rollback();

					throw new DatabaseException(error);
				}
				updateGuarantorDtl2.close();
				updateGuarantorDtl2 = null;
			}

			if ((apps.getProjectOutlayDetails().getGuarantorsName3() != null)
					&& (!apps.getProjectOutlayDetails().getGuarantorsName3().equals(""))) {
				CallableStatement updateGuarantorDtl3 = connection
						.prepareCall("{?=call funcUpdatePersonalGuar(?,?,?,?,?)}");

				updateGuarantorDtl3.registerOutParameter(1, 4);
				updateGuarantorDtl3.registerOutParameter(6, 12);

				updateGuarantorDtl3.setString(2, apps.getAppRefNo());

				updateGuarantorDtl3.setString(3, apps.getProjectOutlayDetails().getGuarantorsName3());

				updateGuarantorDtl3.setDouble(4, apps.getProjectOutlayDetails().getGuarantorsNetWorth3());

				updateGuarantorDtl3.setString(5, userId);

				updateGuarantorDtl3.executeQuery();
				int updateGuarantorDtl3Value = updateGuarantorDtl3.getInt(1);
				Log.log(5, "ApplicationDAO", "updateOtherDtls",
						"Guarantors Details3 result :" + updateGuarantorDtl3Value);

				if (updateGuarantorDtl3Value == 1) {
					String error = updateGuarantorDtl3.getString(6);

					updateGuarantorDtl3.close();
					updateGuarantorDtl3 = null;

					connection.rollback();

					throw new DatabaseException(error);
				}
				updateGuarantorDtl3.close();
				updateGuarantorDtl3 = null;
			}

			if ((apps.getProjectOutlayDetails().getGuarantorsName4() != null)
					&& (!apps.getProjectOutlayDetails().getGuarantorsName4().equals(""))) {
				CallableStatement updateGuarantorDtl4 = connection
						.prepareCall("{?=call funcUpdatePersonalGuar(?,?,?,?,?)}");

				updateGuarantorDtl4.registerOutParameter(1, 4);
				updateGuarantorDtl4.registerOutParameter(6, 12);

				updateGuarantorDtl4.setString(2, apps.getAppRefNo());

				updateGuarantorDtl4.setString(3, apps.getProjectOutlayDetails().getGuarantorsName4());

				updateGuarantorDtl4.setDouble(4, apps.getProjectOutlayDetails().getGuarantorsNetWorth4());

				updateGuarantorDtl4.setString(5, userId);

				updateGuarantorDtl4.executeQuery();
				int updateGuarantorDtl4Value = updateGuarantorDtl4.getInt(1);
				Log.log(5, "ApplicationDAO", "updateOtherDtls",
						"Guarantors Details4 result :" + updateGuarantorDtl4Value);

				if (updateGuarantorDtl4Value == 1) {
					String error = updateGuarantorDtl4.getString(6);

					updateGuarantorDtl4.close();
					updateGuarantorDtl4 = null;

					connection.rollback();

					throw new DatabaseException(error);
				}
				updateGuarantorDtl4.close();
				updateGuarantorDtl4 = null;
			}

			if ((apps.getProjectOutlayDetails().getPrimarySecurityDetails().getLandParticulars() != null)
					&& (!apps.getProjectOutlayDetails().getPrimarySecurityDetails().getLandParticulars().equals(""))) {
				CallableStatement updatePsLandDetails = connection
						.prepareCall("{?=call funcUpdatePrimarySecurity(?,?,?,?,?,?)}");

				updatePsLandDetails.registerOutParameter(1, 4);
				updatePsLandDetails.registerOutParameter(7, 12);

				updatePsLandDetails.setString(2, apps.getAppRefNo());
				updatePsLandDetails.setString(3, "Land");

				updatePsLandDetails.setString(4,
						apps.getProjectOutlayDetails().getPrimarySecurityDetails().getLandParticulars());

				updatePsLandDetails.setDouble(5,
						apps.getProjectOutlayDetails().getPrimarySecurityDetails().getLandValue());

				updatePsLandDetails.setString(6, userId);

				updatePsLandDetails.executeQuery();
				int updatePsLandDetailsValue = updatePsLandDetails.getInt(1);
				Log.log(5, "ApplicationDAO", "updateOtherDtls", "Land Details1 result :" + updatePsLandDetailsValue);

				if (updatePsLandDetailsValue == 1) {
					String error = updatePsLandDetails.getString(6);

					updatePsLandDetails.close();
					updatePsLandDetails = null;

					connection.rollback();

					throw new DatabaseException(error);
				}
				updatePsLandDetails.close();
				updatePsLandDetails = null;
			}

			if ((apps.getProjectOutlayDetails().getPrimarySecurityDetails().getBldgParticulars() != null)
					&& (!apps.getProjectOutlayDetails().getPrimarySecurityDetails().getBldgParticulars().equals(""))) {
				CallableStatement updatePsBldgDetails = connection
						.prepareCall("{?=call funcUpdatePrimarySecurity(?,?,?,?,?,?)}");

				updatePsBldgDetails.registerOutParameter(1, 4);
				updatePsBldgDetails.registerOutParameter(7, 12);

				updatePsBldgDetails.setString(2, apps.getAppRefNo());
				updatePsBldgDetails.setString(3, "Building");

				updatePsBldgDetails.setString(4,
						apps.getProjectOutlayDetails().getPrimarySecurityDetails().getBldgParticulars());

				updatePsBldgDetails.setDouble(5,
						apps.getProjectOutlayDetails().getPrimarySecurityDetails().getBldgValue());

				updatePsBldgDetails.setString(6, userId);

				updatePsBldgDetails.executeQuery();
				int updatePsBldgDetailsValue = updatePsBldgDetails.getInt(1);
				Log.log(5, "ApplicationDAO", "updateOtherDtls",
						"Building Details1 result :" + updatePsBldgDetailsValue);

				if (updatePsBldgDetailsValue == 1) {
					String error = updatePsBldgDetails.getString(7);

					updatePsBldgDetails.close();
					updatePsBldgDetails = null;

					connection.rollback();

					throw new DatabaseException(error);
				}
				updatePsBldgDetails.close();
				updatePsBldgDetails = null;
			}

			if ((apps.getProjectOutlayDetails().getPrimarySecurityDetails().getMachineParticulars() != null) && (!apps
					.getProjectOutlayDetails().getPrimarySecurityDetails().getMachineParticulars().equals(""))) {
				CallableStatement updatePsMachineDetails = connection
						.prepareCall("{?=call funcUpdatePrimarySecurity(?,?,?,?,?,?)}");

				updatePsMachineDetails.registerOutParameter(1, 4);
				updatePsMachineDetails.registerOutParameter(7, 12);

				updatePsMachineDetails.setString(2, apps.getAppRefNo());
				updatePsMachineDetails.setString(3, "Machinery");

				updatePsMachineDetails.setString(4,
						apps.getProjectOutlayDetails().getPrimarySecurityDetails().getMachineParticulars());

				updatePsMachineDetails.setDouble(5,
						apps.getProjectOutlayDetails().getPrimarySecurityDetails().getMachineValue());

				updatePsMachineDetails.setString(6, userId);

				updatePsMachineDetails.executeQuery();
				int updatePsMachineDetailsValue = updatePsMachineDetails.getInt(1);
				Log.log(5, "ApplicationDAO", "updateOtherDtls",
						"Machine Details result :" + updatePsMachineDetailsValue);

				if (updatePsMachineDetailsValue == 1) {
					String error = updatePsMachineDetails.getString(7);

					updatePsMachineDetails.close();
					updatePsMachineDetails = null;

					connection.rollback();

					throw new DatabaseException(error);
				}
				updatePsMachineDetails.close();
				updatePsMachineDetails = null;
			}

			if ((apps.getProjectOutlayDetails().getPrimarySecurityDetails().getAssetsParticulars() != null) && (!apps
					.getProjectOutlayDetails().getPrimarySecurityDetails().getAssetsParticulars().equals(""))) {
				CallableStatement updatePsAssetsDetails = connection
						.prepareCall("{?=call funcUpdatePrimarySecurity(?,?,?,?,?,?)}");

				updatePsAssetsDetails.registerOutParameter(1, 4);
				updatePsAssetsDetails.registerOutParameter(7, 12);

				updatePsAssetsDetails.setString(2, apps.getAppRefNo());
				updatePsAssetsDetails.setString(3, "Fixed Assets");

				updatePsAssetsDetails.setString(4,
						apps.getProjectOutlayDetails().getPrimarySecurityDetails().getAssetsParticulars());

				updatePsAssetsDetails.setDouble(5,
						apps.getProjectOutlayDetails().getPrimarySecurityDetails().getAssetsValue());

				updatePsAssetsDetails.setString(6, userId);

				updatePsAssetsDetails.executeQuery();
				int updatePsAssetsDetailsValue = updatePsAssetsDetails.getInt(1);
				Log.log(5, "ApplicationDAO", "updateOtherDtls", "Assets Details result :" + updatePsAssetsDetailsValue);

				if (updatePsAssetsDetailsValue == 1) {
					String error = updatePsAssetsDetails.getString(7);

					updatePsAssetsDetails.close();
					updatePsAssetsDetails = null;

					connection.rollback();

					throw new DatabaseException(error);
				}
				updatePsAssetsDetails.close();
				updatePsAssetsDetails = null;
			}

			if ((apps.getProjectOutlayDetails().getPrimarySecurityDetails().getCurrentAssetsParticulars() != null)
					&& (!apps.getProjectOutlayDetails().getPrimarySecurityDetails().getCurrentAssetsParticulars()
							.equals(""))) {
				CallableStatement updatePsCurrentAssetsDetails = connection
						.prepareCall("{?=call funcUpdatePrimarySecurity(?,?,?,?,?,?)}");

				updatePsCurrentAssetsDetails.registerOutParameter(1, 4);
				updatePsCurrentAssetsDetails.registerOutParameter(7, 12);

				updatePsCurrentAssetsDetails.setString(2, apps.getAppRefNo());
				updatePsCurrentAssetsDetails.setString(3, "Current Assets");

				updatePsCurrentAssetsDetails.setString(4,
						apps.getProjectOutlayDetails().getPrimarySecurityDetails().getCurrentAssetsParticulars());

				updatePsCurrentAssetsDetails.setDouble(5,
						apps.getProjectOutlayDetails().getPrimarySecurityDetails().getCurrentAssetsValue());

				updatePsCurrentAssetsDetails.setString(6, userId);

				updatePsCurrentAssetsDetails.executeQuery();
				int updatePsCurrentAssetsDetailsValue = updatePsCurrentAssetsDetails.getInt(1);
				Log.log(5, "ApplicationDAO", "updateOtherDtls",
						"Current Assets Details result :" + updatePsCurrentAssetsDetailsValue);

				if (updatePsCurrentAssetsDetailsValue == 1) {
					String error = updatePsCurrentAssetsDetails.getString(7);

					updatePsCurrentAssetsDetails.close();
					updatePsCurrentAssetsDetails = null;

					connection.rollback();

					throw new DatabaseException(error);
				}
				updatePsCurrentAssetsDetails.close();
				updatePsCurrentAssetsDetails = null;
			}

			if ((apps.getProjectOutlayDetails().getPrimarySecurityDetails().getOthersParticulars() != null) && (!apps
					.getProjectOutlayDetails().getPrimarySecurityDetails().getOthersParticulars().equals(""))) {
				CallableStatement updatePsOthersDetails = connection
						.prepareCall("{?=call funcUpdatePrimarySecurity(?,?,?,?,?,?)}");

				updatePsOthersDetails.registerOutParameter(1, 4);
				updatePsOthersDetails.registerOutParameter(7, 12);

				updatePsOthersDetails.setString(2, apps.getAppRefNo());
				updatePsOthersDetails.setString(3, "Others");

				updatePsOthersDetails.setString(4,
						apps.getProjectOutlayDetails().getPrimarySecurityDetails().getOthersParticulars());

				updatePsOthersDetails.setDouble(5,
						apps.getProjectOutlayDetails().getPrimarySecurityDetails().getOthersValue());

				updatePsOthersDetails.setString(6, userId);

				updatePsOthersDetails.executeQuery();
				int updatePsOthersDetailsValue = updatePsOthersDetails.getInt(1);
				Log.log(5, "ApplicationDAO", "updateOtherDtls", "Others Details result :" + updatePsOthersDetailsValue);

				if (updatePsOthersDetailsValue == 1) {
					String error = updatePsOthersDetails.getString(7);

					updatePsOthersDetails.close();
					updatePsOthersDetails = null;

					connection.rollback();

					throw new DatabaseException(error);
				}
				updatePsOthersDetails.close();
				updatePsOthersDetails = null;
			}

		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "updateOtherDtls", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
			}
			throw new DatabaseException(sqlException.getMessage());
		}
	}

	public void updateTermLoanDtl(Application apps, String createdBy, Connection connection) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "updateTermLoanDtl", "Entered");

		String appLoanType = apps.getLoanType();
		try {
			if ((appLoanType.equals("TC")) || (appLoanType.equals("CC")) || (appLoanType.equals("BO"))) {
				CallableStatement updateTcDetails = connection
						.prepareCall("{?=call funcUpdateTermLoan(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

				updateTcDetails.registerOutParameter(1, 4);
				updateTcDetails.registerOutParameter(21, 12);

				updateTcDetails.setString(2, apps.getAppRefNo());

				updateTcDetails.setDouble(3, apps.getProjectOutlayDetails().getTermCreditSanctioned());

				if ((!apps.getTermLoan().getAmountSanctionedDate().toString().equals(""))
						&& (apps.getTermLoan().getAmountSanctionedDate() != null)) {
					updateTcDetails.setDate(4,
							new java.sql.Date(apps.getTermLoan().getAmountSanctionedDate().getTime()));
				} else
					updateTcDetails.setDate(4, null);

				if (apps.getProjectOutlayDetails().getTcPromoterContribution() == 0.0D) {
					updateTcDetails.setNull(5, 8);
				} else
					updateTcDetails.setDouble(5, apps.getProjectOutlayDetails().getTcPromoterContribution());

				if (apps.getProjectOutlayDetails().getTcSubsidyOrEquity() == 0.0D) {
					updateTcDetails.setNull(6, 8);
				} else
					updateTcDetails.setDouble(6, apps.getProjectOutlayDetails().getTcSubsidyOrEquity());

				if (apps.getProjectOutlayDetails().getTcOthers() == 0.0D) {
					updateTcDetails.setNull(7, 8);
				} else
					updateTcDetails.setDouble(7, apps.getProjectOutlayDetails().getTcOthers());

				if (apps.getTermLoan().getCreditGuaranteed() == 0.0D) {
					updateTcDetails.setNull(8, 8);
				} else
					updateTcDetails.setDouble(8, apps.getTermLoan().getCreditGuaranteed());

				if (apps.getTermLoan().getTenure() == 0) {
					updateTcDetails.setNull(9, 4);
				} else
					updateTcDetails.setInt(9, apps.getTermLoan().getTenure());

				if ((apps.getTermLoan().getInterestType() != null)
						&& (!apps.getTermLoan().getInterestType().equals(""))) {
					updateTcDetails.setString(10, apps.getTermLoan().getInterestType());
				} else {
					updateTcDetails.setString(10, null);
				}

				if (apps.getTermLoan().getInterestRate() == 0.0D) {
					updateTcDetails.setNull(11, 8);
				} else
					updateTcDetails.setDouble(11, apps.getTermLoan().getInterestRate());

				if (apps.getTermLoan().getBenchMarkPLR() == 0.0D) {
					updateTcDetails.setNull(12, 8);
				} else
					updateTcDetails.setDouble(12, apps.getTermLoan().getBenchMarkPLR());

				if (apps.getTermLoan().getPlr() == 0.0D) {
					updateTcDetails.setNull(13, 8);
				} else
					updateTcDetails.setDouble(13, apps.getTermLoan().getPlr());

				if (apps.getTermLoan().getRepaymentMoratorium() == 0) {
					updateTcDetails.setNull(14, 4);
				} else
					updateTcDetails.setInt(14, apps.getTermLoan().getRepaymentMoratorium());

				if ((apps.getTermLoan().getFirstInstallmentDueDate() != null)
						&& (!apps.getTermLoan().getFirstInstallmentDueDate().toString().equals(""))) {
					updateTcDetails.setDate(15,
							new java.sql.Date(apps.getTermLoan().getFirstInstallmentDueDate().getTime()));
				} else
					updateTcDetails.setNull(15, 91);

				if (apps.getTermLoan().getNoOfInstallments() == 0) {
					updateTcDetails.setNull(16, 4);
				} else
					updateTcDetails.setInt(16, apps.getTermLoan().getNoOfInstallments());

				if ((apps.getTermLoan().getPeriodicity() != 1) || (apps.getTermLoan().getPeriodicity() != 2)
						|| (apps.getTermLoan().getPeriodicity() != 3)) {
					updateTcDetails.setNull(17, 4);
				} else
					updateTcDetails.setInt(17, apps.getTermLoan().getPeriodicity());

				if ((apps.getTermLoan().getTypeOfPLR() != null) && (!apps.getTermLoan().getTypeOfPLR().equals(""))) {
					updateTcDetails.setString(18, apps.getTermLoan().getTypeOfPLR());
				} else {
					updateTcDetails.setNull(18, 12);
				}

				updateTcDetails.setString(19, createdBy);
				updateTcDetails.setString(20, null);

				updateTcDetails.executeQuery();
				int updateTcDetailsValue = updateTcDetails.getInt(1);
				Log.log(5, "ApplicationDAO", "updateTermLoanDtl", "TC Details result :" + updateTcDetailsValue);

				if (updateTcDetailsValue == 1) {
					String error = updateTcDetails.getString(21);

					updateTcDetails.close();
					updateTcDetails = null;

					connection.rollback();

					throw new DatabaseException(error);
				}
				updateTcDetails.close();
				updateTcDetails = null;
			} else if (appLoanType.equals("WC")) {
				double tcPromoterCont = apps.getProjectOutlayDetails().getTcPromoterContribution();
				double tcSubsidy = apps.getProjectOutlayDetails().getTcSubsidyOrEquity();
				double tcOthers = apps.getProjectOutlayDetails().getTcOthers();
				double tcAmtSanctioned = apps.getProjectOutlayDetails().getTermCreditSanctioned();

				CallableStatement updateTCWCDetails = connection
						.prepareCall("{?=call funcUpdateTermLoan(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

				updateTCWCDetails.registerOutParameter(1, 4);
				updateTCWCDetails.registerOutParameter(21, 12);

				updateTCWCDetails.setString(2, apps.getAppRefNo());
				updateTCWCDetails.setDouble(3, apps.getProjectOutlayDetails().getTermCreditSanctioned());
				updateTCWCDetails.setNull(4, 91);

				if (apps.getProjectOutlayDetails().getTcPromoterContribution() == 0.0D) {
					updateTCWCDetails.setNull(5, 8);
				} else
					updateTCWCDetails.setDouble(5, apps.getProjectOutlayDetails().getTcPromoterContribution());

				if (apps.getProjectOutlayDetails().getTcSubsidyOrEquity() == 0.0D) {
					updateTCWCDetails.setNull(6, 8);
				} else
					updateTCWCDetails.setDouble(6, apps.getProjectOutlayDetails().getTcSubsidyOrEquity());

				if (apps.getProjectOutlayDetails().getTcOthers() == 0.0D) {
					updateTCWCDetails.setNull(7, 8);
				} else
					updateTCWCDetails.setDouble(7, apps.getProjectOutlayDetails().getTcOthers());

				updateTCWCDetails.setNull(8, 8);
				updateTCWCDetails.setNull(9, 4);
				updateTCWCDetails.setNull(10, 12);
				updateTCWCDetails.setNull(11, 8);
				updateTCWCDetails.setNull(12, 8);
				updateTCWCDetails.setNull(13, 8);
				updateTCWCDetails.setNull(14, 4);
				updateTCWCDetails.setNull(15, 91);
				updateTCWCDetails.setNull(16, 4);
				updateTCWCDetails.setNull(17, 4);
				updateTCWCDetails.setNull(18, 12);
				updateTCWCDetails.setString(19, createdBy);

				updateTCWCDetails.setNull(20, 12);

				updateTCWCDetails.executeQuery();
				int updateTCWCDetailsValue = updateTCWCDetails.getInt(1);
				Log.log(5, "ApplicationDAO", "updateTermLoanDtl", "TCWC Details result :" + updateTCWCDetailsValue);

				if (updateTCWCDetailsValue == 1) {
					String error = updateTCWCDetails.getString(21);

					updateTCWCDetails.close();
					updateTCWCDetails = null;

					connection.rollback();

					throw new DatabaseException(error);
				}
				updateTCWCDetails.close();
				updateTCWCDetails = null;
			}

		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "updateTermLoanDtl", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
			}
			throw new DatabaseException(sqlException.getMessage());
		}

		Log.log(4, "ApplicationDAO", "updateTermLoanDtl", "Exited");
	}

	public void updateWCDtl(Application apps, String userId, Connection connection) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "updateWCDtl", "Entered");

		String appLoanType = apps.getLoanType();
		try {
			if ((appLoanType.equals("WC")) || (appLoanType.equals("CC")) || (appLoanType.equals("BO"))) {
				CallableStatement updateWCDetails = connection
						.prepareCall("{?=call funcUpdatetWorkingCapital(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

				updateWCDetails.registerOutParameter(1, 4);
				updateWCDetails.registerOutParameter(19, 12);

				updateWCDetails.setString(2, apps.getAppRefNo());
				updateWCDetails.setDouble(3, apps.getProjectOutlayDetails().getWcFundBasedSanctioned());

				if (apps.getProjectOutlayDetails().getWcNonFundBasedSanctioned() == 0.0D) {
					updateWCDetails.setDouble(4, 0.0D);
				} else {
					updateWCDetails.setDouble(4, apps.getProjectOutlayDetails().getWcNonFundBasedSanctioned());
				}
				Log.log(4, "ApplicationDAO", "updateWCDtl",
						"Non Fund BAsed Sanctioned :" + apps.getProjectOutlayDetails().getWcNonFundBasedSanctioned());

				if (apps.getProjectOutlayDetails().getWcPromoterContribution() == 0.0D) {
					updateWCDetails.setNull(5, 8);
				} else
					updateWCDetails.setDouble(5, apps.getProjectOutlayDetails().getWcPromoterContribution());

				Log.log(4, "ApplicationDAO", "updateWCDtl",
						"promoter Cont :" + apps.getProjectOutlayDetails().getWcPromoterContribution());

				if (apps.getProjectOutlayDetails().getWcSubsidyOrEquity() == 0.0D) {
					updateWCDetails.setNull(6, 8);
				} else
					updateWCDetails.setDouble(6, apps.getProjectOutlayDetails().getWcSubsidyOrEquity());

				Log.log(4, "ApplicationDAO", "updateWCDtl",
						"Subsidy or equity :" + apps.getProjectOutlayDetails().getWcSubsidyOrEquity());

				if (apps.getProjectOutlayDetails().getWcOthers() == 0.0D) {
					updateWCDetails.setNull(7, 8);
				} else
					updateWCDetails.setDouble(7, apps.getProjectOutlayDetails().getWcOthers());

				Log.log(4, "ApplicationDAO", "updateWCDtl", "Others:" + apps.getProjectOutlayDetails().getWcOthers());

				updateWCDetails.setDouble(8, apps.getWc().getLimitFundBasedInterest());

				if (apps.getWc().getLimitNonFundBasedCommission() == 0.0D) {
					updateWCDetails.setDouble(9, 0.0D);
				} else {
					updateWCDetails.setDouble(9, apps.getWc().getLimitNonFundBasedCommission());
				}

				if ((apps.getWc().getLimitFundBasedSanctionedDate() != null)
						&& (!apps.getWc().getLimitFundBasedSanctionedDate().toString().equals(""))) {
					updateWCDetails.setDate(10,
							new java.sql.Date(apps.getWc().getLimitFundBasedSanctionedDate().getTime()));
				} else {
					updateWCDetails.setDate(10, null);
				}
				if ((apps.getWc().getLimitNonFundBasedSanctionedDate() != null)
						&& (!apps.getWc().getLimitNonFundBasedSanctionedDate().toString().equals(""))) {
					updateWCDetails.setDate(11,
							new java.sql.Date(apps.getWc().getLimitNonFundBasedSanctionedDate().getTime()));
				} else {
					updateWCDetails.setDate(11, null);
				}

				if (apps.getWc().getCreditFundBased() == 0.0D) {
					updateWCDetails.setNull(12, 8);
				} else
					updateWCDetails.setDouble(12, apps.getWc().getCreditFundBased());

				if (apps.getWc().getCreditNonFundBased() == 0.0D) {
					updateWCDetails.setDouble(13, 0.0D);
				} else {
					updateWCDetails.setDouble(13, apps.getWc().getCreditNonFundBased());
				}

				if (apps.getWc().getWcPlr() == 0.0D) {
					updateWCDetails.setNull(14, 8);
				} else
					updateWCDetails.setDouble(14, apps.getWc().getWcPlr());

				if ((apps.getWc().getWcTypeOfPLR() != null) && (!apps.getWc().getWcTypeOfPLR().equals(""))) {
					updateWCDetails.setString(15, apps.getWc().getWcTypeOfPLR());
				} else {
					updateWCDetails.setNull(15, 12);
				}

				if ((apps.getWc().getWcInterestType() != null) && (!apps.getWc().getWcInterestType().equals(""))) {
					updateWCDetails.setString(16, apps.getWc().getWcInterestType());
				} else {
					updateWCDetails.setNull(16, 12);
				}

				if ((apps.getRemarks() == null) || (apps.getRemarks().equals(""))) {
					updateWCDetails.setNull(17, 12);
				} else {
					updateWCDetails.setString(17, null);
				}

				updateWCDetails.setString(18, userId);

				updateWCDetails.executeQuery();
				int updateWCDetailsValue = updateWCDetails.getInt(1);
				Log.log(5, "ApplicationDAO", "updateWCDtl", "WC Details result :" + updateWCDetailsValue);

				if (updateWCDetailsValue == 1) {
					String error = updateWCDetails.getString(19);

					updateWCDetails.close();
					updateWCDetails = null;

					connection.rollback();

					throw new DatabaseException(error);
				}
				updateWCDetails.close();
				updateWCDetails = null;
			} else if (appLoanType.equals("TC")) {
				double wcFBSanctioned = apps.getProjectOutlayDetails().getWcFundBasedSanctioned();
				double wcNFBSanctioned = apps.getProjectOutlayDetails().getWcNonFundBasedSanctioned();
				double wcPromoterCont = apps.getProjectOutlayDetails().getWcPromoterContribution();
				double wcSubsidy = apps.getProjectOutlayDetails().getWcSubsidyOrEquity();
				double wcOthers = apps.getProjectOutlayDetails().getWcOthers();

				CallableStatement updateWCTCDetails = connection
						.prepareCall("{?=call funcUpdatetWorkingCapital(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

				updateWCTCDetails.registerOutParameter(1, 4);
				updateWCTCDetails.registerOutParameter(19, 12);

				updateWCTCDetails.setString(2, apps.getAppRefNo());
				updateWCTCDetails.setDouble(3, apps.getProjectOutlayDetails().getWcFundBasedSanctioned());
				Log.log(5, "ApplicationDAO", "updateWCDtl",
						"wc fund based:" + apps.getProjectOutlayDetails().getWcFundBasedSanctioned());

				if (apps.getProjectOutlayDetails().getWcNonFundBasedSanctioned() == 0.0D) {
					updateWCTCDetails.setDouble(4, 0.0D);
				} else
					updateWCTCDetails.setDouble(4, apps.getProjectOutlayDetails().getWcNonFundBasedSanctioned());

				Log.log(5, "ApplicationDAO", "updateWCDtl",
						"wc non fund based:" + apps.getProjectOutlayDetails().getWcNonFundBasedSanctioned());

				if (apps.getProjectOutlayDetails().getWcPromoterContribution() == 0.0D) {
					updateWCTCDetails.setNull(5, 8);
				} else
					updateWCTCDetails.setDouble(5, apps.getProjectOutlayDetails().getWcPromoterContribution());

				Log.log(5, "ApplicationDAO", "updateWCDtl",
						"wc non fund based:" + apps.getProjectOutlayDetails().getWcNonFundBasedSanctioned());

				if (apps.getProjectOutlayDetails().getWcSubsidyOrEquity() == 0.0D) {
					updateWCTCDetails.setNull(6, 8);
				} else
					updateWCTCDetails.setDouble(6, apps.getProjectOutlayDetails().getWcSubsidyOrEquity());

				Log.log(5, "ApplicationDAO", "updateWCDtl",
						"wc non fund based:" + apps.getProjectOutlayDetails().getWcSubsidyOrEquity());

				if (apps.getProjectOutlayDetails().getWcOthers() == 0.0D) {
					updateWCTCDetails.setNull(7, 8);
				} else
					updateWCTCDetails.setDouble(7, apps.getProjectOutlayDetails().getWcOthers());

				Log.log(5, "ApplicationDAO", "updateWCDtl",
						"wc non fund based:" + apps.getProjectOutlayDetails().getWcOthers());

				updateWCTCDetails.setNull(8, 8);
				updateWCTCDetails.setDouble(9, 0.0D);
				updateWCTCDetails.setNull(10, 91);
				updateWCTCDetails.setNull(11, 91);
				updateWCTCDetails.setNull(12, 8);
				updateWCTCDetails.setDouble(13, 0.0D);
				updateWCTCDetails.setNull(14, 8);
				updateWCTCDetails.setNull(15, 12);
				updateWCTCDetails.setString(16, "T");
				updateWCTCDetails.setNull(17, 12);
				updateWCTCDetails.setString(18, userId);

				updateWCTCDetails.executeQuery();
				int updateWCTCDetailsValue = updateWCTCDetails.getInt(1);
				Log.log(5, "ApplicationDAO", "updateWCDtl", "WC Details result :" + updateWCTCDetailsValue);

				if (updateWCTCDetailsValue == 1) {
					String error = updateWCTCDetails.getString(19);

					updateWCTCDetails.close();
					updateWCTCDetails = null;

					connection.rollback();

					Log.log(5, "ApplicationDAO", "updateWCDtl", "WC Details exception :" + error);
					throw new DatabaseException(error);
				}

				updateWCTCDetails.close();
				updateWCTCDetails = null;
			}

		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "updateWCDtl", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
			}
			throw new DatabaseException(sqlException.getMessage());
		}

		Log.log(4, "ApplicationDAO", "updateWCDtl", "Exited");
	}

	public void updateSecDetails(Application app, String createdBy, Connection connection) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "updateSecDetails", "Entered");
		try {
			CallableStatement updateSecDtls = connection
					.prepareCall("{?=call funcUpdateSecDetails(?,?,?,?,?,?,?,?,?,?,?)}");

			updateSecDtls.registerOutParameter(1, 4);
			updateSecDtls.registerOutParameter(12, 12);

			updateSecDtls.setString(2, app.getAppRefNo());

			if (app.getSecuritization().getSpreadOverPLR() == 0.0D) {
				updateSecDtls.setNull(3, 8);
			} else
				updateSecDtls.setDouble(3, app.getSecuritization().getSpreadOverPLR());

			if ((app.getSecuritization().getPplRepaymentInEqual() == null)
					&& (!app.getSecuritization().getPplRepaymentInEqual().equals(""))) {
				updateSecDtls.setNull(4, 12);
			} else
				updateSecDtls.setString(4, app.getSecuritization().getPplRepaymentInEqual());

			if (app.getSecuritization().getTangibleNetWorth() == 0.0D) {
				updateSecDtls.setNull(5, 8);
			} else
				updateSecDtls.setDouble(5, app.getSecuritization().getTangibleNetWorth());

			if (app.getSecuritization().getFixedACR() == 0.0D) {
				updateSecDtls.setNull(6, 8);
			} else
				updateSecDtls.setDouble(6, app.getSecuritization().getFixedACR());

			if (app.getSecuritization().getCurrentRatio() == 0.0D) {
				updateSecDtls.setNull(7, 8);
			} else
				updateSecDtls.setDouble(7, app.getSecuritization().getCurrentRatio());

			if (app.getSecuritization().getMinimumDSCR() == 0.0D) {
				updateSecDtls.setNull(8, 12);
			} else
				updateSecDtls.setDouble(8, app.getSecuritization().getMinimumDSCR());

			if (app.getSecuritization().getAvgDSCR() == 0.0D) {
				updateSecDtls.setNull(9, 8);
			} else
				updateSecDtls.setDouble(9, app.getSecuritization().getAvgDSCR());

			if (app.getSecuritization().getFinancialPartUnit() == 0.0D) {
				updateSecDtls.setNull(10, 8);
			} else
				updateSecDtls.setDouble(10, app.getSecuritization().getFinancialPartUnit());

			updateSecDtls.setString(11, createdBy);

			updateSecDtls.execute();
			int updateSecDtlsValue = updateSecDtls.getInt(1);

			if (updateSecDtlsValue == 1) {
				String error = updateSecDtls.getString(12);

				updateSecDtls.close();
				updateSecDtls = null;

				connection.rollback();

				Log.log(2, "ApplicationDAO", "updateSecDetails", error);
				throw new DatabaseException(error);
			}

			updateSecDtls.close();
			updateSecDtls = null;
		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "updateSecDetails", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
			}
			throw new DatabaseException(sqlException.getMessage());
		}
	}

	public ArrayList getApplicationsForReapproval(String userId) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "getApplicationsForReapproval", "Entered");

		ArrayList tcReApprovedList = new ArrayList();
		ArrayList wcReApprovedList = new ArrayList();
		ArrayList clearAppsList = new ArrayList();

		Connection connection = DBConnection.getConnection(false);
		try {
			CallableStatement reApprovalList = connection
					.prepareCall("{?=call packGetAppForReApproval.funcGetAppForReApproval(?,?,?,?,?)}");
			reApprovalList.registerOutParameter(1, 4);
			reApprovalList.registerOutParameter(3, -10);
			reApprovalList.registerOutParameter(4, -10);
			reApprovalList.registerOutParameter(5, 4);
			reApprovalList.registerOutParameter(6, 12);

			reApprovalList.setString(2, userId);

			reApprovalList.execute();
			int functionReturnValue = reApprovalList.getInt(1);
			if (functionReturnValue == 1) {
				String error = reApprovalList.getString(6);

				reApprovalList.close();
				reApprovalList = null;

				connection.rollback();

				throw new DatabaseException(error);
			}

			ResultSet tcClearApplications = (ResultSet) reApprovalList.getObject(3);
			while (tcClearApplications.next()) {
				Application application = new Application();
				BorrowerDetails borrowerDetails = new BorrowerDetails();
				SSIDetails ssiDetails = new SSIDetails();
				TermLoan termLoan = new TermLoan();
				WorkingCapital workingCapital = new WorkingCapital();
				borrowerDetails.setSsiDetails(ssiDetails);
				application.setBorrowerDetails(borrowerDetails);
				application.setTermLoan(termLoan);
				application.setWc(workingCapital);

				application.setAppRefNo(tcClearApplications.getString(1));
				Log.log(5, "ApplicationDAO", "getApplicationsForReapproval",
						"App ref no1:" + application.getAppRefNo());
				// System.out
				// .println("App Ref No is " + application.getAppRefNo());
				application.setCgpan(tcClearApplications.getString(2));
				application.getBorrowerDetails().getSsiDetails().setBorrowerRefNo(tcClearApplications.getInt(3));
				Log.log(5, "ApplicationDAO", "getApplicationsForReapproval",
						"SSI Ref No1:" + application.getBorrowerDetails().getSsiDetails().getBorrowerRefNo());
				application.setSubmittedDate(tcClearApplications.getDate(4));
				if (tcClearApplications.getDouble(6) == 0.0D) {
					application.setApprovedAmount(tcClearApplications.getDouble(5));
				} else {
					application.setApprovedAmount(tcClearApplications.getDouble(6));
				}
				Log.log(4, "ApplicationDAO", "getApplicationsForReapproval",
						"re approve 5" + tcClearApplications.getDouble(5));
				Log.log(4, "ApplicationDAO", "getApplicationsForReapproval",
						"re approve 6" + tcClearApplications.getDouble(6));
				Log.log(4, "ApplicationDAO", "getApplicationsForReapproval",
						"re approve" + application.getApprovedAmount());

				application.setStatus(tcClearApplications.getString(7));
				Log.log(5, "ApplicationDAO", "getApplicationsForReapproval",
						"status" + tcClearApplications.getString(6));

				tcReApprovedList.add(application);
			}
			tcClearApplications.close();
			tcClearApplications = null;

			ResultSet wcClearApplications = (ResultSet) reApprovalList.getObject(4);
			while (wcClearApplications.next()) {
				Application application = new Application();
				BorrowerDetails borrowerDetails = new BorrowerDetails();
				SSIDetails ssiDetails = new SSIDetails();
				TermLoan termLoan = new TermLoan();
				WorkingCapital workingCapital = new WorkingCapital();
				borrowerDetails.setSsiDetails(ssiDetails);
				application.setBorrowerDetails(borrowerDetails);
				application.setTermLoan(termLoan);
				application.setWc(workingCapital);

				application.setAppRefNo(wcClearApplications.getString(1));
				Log.log(5, "ApplicationDAO", "viewApplicationsForApproval",
						"App ref no 2:" + application.getAppRefNo());
				// System.out.println("WC Appref no is "
				// + application.getAppRefNo());
				application.setCgpan(wcClearApplications.getString(2));
				application.getBorrowerDetails().getSsiDetails().setBorrowerRefNo(wcClearApplications.getInt(3));
				Log.log(5, "ApplicationDAO", "viewApplicationsForApproval",
						"SSI Ref No 2:" + application.getBorrowerDetails().getSsiDetails().getBorrowerRefNo());
				application.setSubmittedDate(wcClearApplications.getDate(4));

				if (wcClearApplications.getDouble(6) == 0.0D) {
					application.setApprovedAmount(wcClearApplications.getDouble(5));
				} else {
					application.setApprovedAmount(wcClearApplications.getDouble(6));
				}

				application.setStatus(wcClearApplications.getString(7));

				wcReApprovedList.add(application);
			}
			wcClearApplications.close();
			wcClearApplications = null;

			reApprovalList.close();
			reApprovalList = null;

			clearAppsList.add(tcReApprovedList);
			clearAppsList.add(wcReApprovedList);

			connection.commit();
		} catch (SQLException sqlException) {
			try {
				connection.rollback();
			} catch (SQLException ignore) {
			}
			throw new DatabaseException(sqlException.getMessage());
		} catch (Exception exception) {
			throw new DatabaseException(exception.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(4, "ApplicationDAO", "getApplicationsForReapproval", "Exited");

		return clearAppsList;
	}

	public void updateApplicationStatus(Application application, String createdBy) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "updateApplicationStatus", "Entered");

		Connection connection = DBConnection.getNewConnection(false);

		java.util.Date utilDate = new java.util.Date();
		try {
			connection.commit();

			CallableStatement updateAppStatus = connection.prepareCall("{?=call funcUpdateAppStatus(?,?,?,?,?,?,?,?)}");

			updateAppStatus.registerOutParameter(1, 4);
			updateAppStatus.registerOutParameter(9, 12);

			updateAppStatus.setString(2, application.getAppRefNo());

			Log.log(5, "ApplicationDAO", "updateApplicationStatus",
					"Application app ref no :" + application.getAppRefNo());
			updateAppStatus.setString(3, application.getCgpan());

			Log.log(5, "ApplicationDAO", "updateApplicationStatus", "Application Status :" + application.getCgpan());
			updateAppStatus.setString(4, createdBy);

			if (application.getApprovedAmount() != 0.0D) {
				updateAppStatus.setDouble(5, application.getApprovedAmount());
			} else {
				updateAppStatus.setNull(5, 8);
			}
			Log.log(5, "ApplicationDAO", "updateApplicationStatus",
					"Application Amount :" + application.getApprovedAmount());
			updateAppStatus.setDate(6, new java.sql.Date(utilDate.getTime()));

			Log.log(5, "ApplicationDAO", "updateApplicationStatus", "Application Status :" + application.getStatus());
			updateAppStatus.setString(7, application.getStatus());

			Log.log(5, "ApplicationDAO", "updateApplicationStatus", "Application Renmarks:" + application.getRemarks());
			if ((application.getRemarks() != null) && (!application.getRemarks().equals(""))) {
				updateAppStatus.setString(8, application.getRemarks());
			} else {
				updateAppStatus.setString(8, null);
			}

			try {
				updateAppStatus.execute();
			} catch (Throwable e) {
				e.printStackTrace();
			}

			int functionReturnValue = updateAppStatus.getInt(1);

			Log.log(5, "ApplicationDAO", "updateApplicationStatus",
					"Get update App Status Result :" + functionReturnValue);

			if (functionReturnValue == 1) {
				String error = updateAppStatus.getString(9);

				updateAppStatus.close();
				updateAppStatus = null;

				connection.rollback();

				throw new DatabaseException(error);
			}

			updateAppStatus.close();
			updateAppStatus = null;

			updateCgpan(application, connection);

			if ((!application.getAdditionalTC()) && (!application.getWcRenewal())) {
				int ssiRefNo = application.getBorrowerDetails().getSsiDetails().getBorrowerRefNo();
				String cgbid = generateCgbid(ssiRefNo, connection);
				if ((cgbid != null) && (!cgbid.equals(""))) {
					application.getBorrowerDetails().getSsiDetails().setCgbid(cgbid);

					updateCgbid(ssiRefNo, cgbid, connection);
				}

			}

			if (application.getLoanType().equals("TC")) {
				updateWcTenure(application, connection);
			}

			connection.commit();
			connection.close();
			connection = null;
		} catch (Exception sqlException) {
			Log.log(4, "ApplicationDAO", "updateApplicationStatus", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (Exception ignore) {
			}
			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);

		}
		Log.log(4, "ApplicationDAO", "updateApplicationStatus", "Exited");
	}

	public void updateRejectedApplicationStatus(Application application, String createdBy) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "updateRejectedApplicationStatus", "Entered");

		Connection connection = DBConnection.getNewConnection(false);

		java.util.Date utilDate = new java.util.Date();
		try {
			connection.commit();

			CallableStatement updateAppStatus = connection
					.prepareCall("{?=call funcUpdateReAppStatusForRejApp(?,?,?,?,?,?,?,?)}");

			updateAppStatus.registerOutParameter(1, 4);
			updateAppStatus.registerOutParameter(9, 12);

			updateAppStatus.setString(2, application.getAppRefNo());

			Log.log(5, "ApplicationDAO", "updateRejectedApplicationStatus",
					"Application app ref no :" + application.getAppRefNo());
			updateAppStatus.setString(3, application.getCgpan());

			Log.log(5, "ApplicationDAO", "updateRejectedApplicationStatus",
					"Application Status :" + application.getCgpan());
			updateAppStatus.setString(4, createdBy);

			if (application.getApprovedAmount() != 0.0D) {
				updateAppStatus.setDouble(5, application.getApprovedAmount());
			} else {
				updateAppStatus.setNull(5, 8);
			}
			Log.log(5, "ApplicationDAO", "updateRejectedApplicationStatus",
					"Application Amount :" + application.getApprovedAmount());
			updateAppStatus.setDate(6, new java.sql.Date(utilDate.getTime()));

			Log.log(5, "ApplicationDAO", "updateRejectedApplicationStatus",
					"Application Status :" + application.getStatus());
			updateAppStatus.setString(7, application.getStatus());

			Log.log(5, "ApplicationDAO", "updateRejectedApplicationStatus",
					"Application Renmarks:" + application.getRemarks());
			if ((application.getRemarks() != null) && (!application.getRemarks().equals(""))) {
				updateAppStatus.setString(8, application.getRemarks());
			} else {
				updateAppStatus.setString(8, null);
			}

			try {
				updateAppStatus.execute();
			} catch (Throwable e) {
				e.printStackTrace();
			}

			int functionReturnValue = updateAppStatus.getInt(1);

			Log.log(5, "ApplicationDAO", "updateRejectedApplicationStatus",
					"Get update App Status Result :" + functionReturnValue);

			if (functionReturnValue == 1) {
				String error = updateAppStatus.getString(9);

				updateAppStatus.close();
				updateAppStatus = null;

				connection.rollback();

				throw new DatabaseException(error);
			}

			updateAppStatus.close();
			updateAppStatus = null;

			updateCgpan(application, connection);

			if ((!application.getAdditionalTC()) && (!application.getWcRenewal())) {
				int ssiRefNo = application.getBorrowerDetails().getSsiDetails().getBorrowerRefNo();
				String cgbid = generateCgbid(ssiRefNo, connection);
				if ((cgbid != null) && (!cgbid.equals(""))) {
					application.getBorrowerDetails().getSsiDetails().setCgbid(cgbid);

					updateCgbid(ssiRefNo, cgbid, connection);
				}

			}

			if (application.getLoanType().equals("TC")) {
				updateWcTenure(application, connection);
			}

			connection.commit();
			connection.close();
			connection = null;
		} catch (Exception sqlException) {
			Log.log(4, "ApplicationDAO", "updateRejectedApplicationStatus", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (Exception ignore) {
			}
			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);

		}
		Log.log(4, "ApplicationDAO", "updateRejectedApplicationStatus", "Exited");
	}

//bhu
	public String submitAddlTermCredit(Application application, String createdBy) throws DatabaseException {
		Connection connection = DBConnection.getConnection(false);

		String appRefNo = "";
		try {
			MCGSProcessor mcgsProcessor = new MCGSProcessor();
			RiskManagementProcessor rpProcessor = new RiskManagementProcessor();

			Log.log(4, "ApplicationDAO", "submitAddlTermCredit", "Entered");
			int ssiRefNo = application.getBorrowerDetails().getSsiDetails().getBorrowerRefNo();
			application.getBorrowerDetails().getSsiDetails().setBorrowerRefNo(ssiRefNo);

			String subSchemeName = rpProcessor.getSubScheme(application);
			application.setSubSchemeName(subSchemeName);

			double balanceAppAmt = getBalanceApprovedAmt(application);

			String ssiRefNumber = Integer.toString(ssiRefNo);
			RpDAO rpDAO1 = new RpDAO();
			double prevTotalSancAmt = rpDAO1.getTotalSanctionedAmountNew(ssiRefNumber);
			ApplicationDAO appdao = new ApplicationDAO();

			double prevTotalHandloomSancAmt = appdao.getTotalSanctionedHandloomAmountNew(ssiRefNumber);

			double currentCreditAmount = 0.0D;
			if (application.getLoanType().equals("TC")) {
				currentCreditAmount = application.getTermLoan().getCreditGuaranteed();
			} else if (application.getLoanType().equals("CC")) {
				currentCreditAmount = application.getTermLoan().getCreditGuaranteed()
						+ application.getWc().getCreditFundBased() + application.getWc().getCreditNonFundBased();
			} else if (application.getLoanType().equals("WC")) {
				currentCreditAmount = application.getWc().getCreditFundBased()
						+ application.getWc().getCreditNonFundBased();
			}

			if ((currentCreditAmount > 200000.0D) && (application.getDcHandlooms().equals("Y"))) {
				throw new DatabaseException(
						"Credit to be Guaranteed Amount should be within the eligible amount available for Guarantee upto Rs. 200000 as per ceiling fixed by Office of DC Handlooms");
			}
			if ((currentCreditAmount + prevTotalHandloomSancAmt > 200000.0D)
					&& (application.getDcHandlooms().equals("Y"))) {
				throw new DatabaseException("Guarantee of Rs. " + prevTotalHandloomSancAmt
						+ " is already available for the Borrower. Total Credit to be Guaranteed Amount should be within the eligible amount available for Guarantee upto  Rs. 200000 as per ceiling fixed by Office of DC Handlooms");
			}

			if ((application.getLoanType().equals("TC")) || (application.getLoanType().equals("CC"))) {
				if (application.getTermLoan().getCreditGuaranteed() > balanceAppAmt) {
					throw new DatabaseException(
							"Credit to be Guaranteed Amount should be within the eligible amount available for Guarantee :"
									+ balanceAppAmt);
				}
			} else if (application.getLoanType().equals("WC")) {
				if (application.getWc().getCreditFundBased()
						+ application.getWc().getCreditNonFundBased() > balanceAppAmt) {
					throw new DatabaseException(
							"Credit to be Guaranteed Amount should be within the eligible amount available for Guarantee :"
									+ balanceAppAmt);
				}
			} else if (application.getLoanType().equals("BO")) {
				if (application.getTermLoan().getCreditGuaranteed() + application.getWc().getCreditFundBased()
						+ application.getWc().getCreditNonFundBased() > balanceAppAmt) {
					throw new DatabaseException(
							"Credit to be Guaranteed Amount should be within the eligible amount available for Guarantee :"
									+ balanceAppAmt);
				}

			}

			appRefNo = submitApplicationDetails(application, createdBy, connection); // DKR FINAL
			Log.log(4, "ApplicationDAO", "submitAddlTermCredit", "addtl term credit app ref no :" + appRefNo);
			application.setAppRefNo(appRefNo);
			submitGuarantorSecurityDetails(application, connection);
			submitTermCreditDetails(application, createdBy, connection);
			submitWCDetails(application, createdBy, connection);
			submitSecDetails(application, connection);

			if (application.getMCGFDetails() != null) {
				MCGFDetails mcgfDetails = application.getMCGFDetails();
				mcgfDetails.setApplicationReferenceNumber(appRefNo);
				application.setMCGFDetails(mcgfDetails);
				mcgsProcessor.updateMCGSDetails(mcgfDetails, createdBy, connection);
			}

			connection.commit();
		} catch (SQLException e) {
			Log.log(2, "ApplicationDAO", "submitApp", e.getMessage());
			Log.logException(e);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
			}
			throw new DatabaseException("Unable to submit Application");
		} finally {
			DBConnection.freeConnection(connection);

		}
		Log.log(4, "ApplicationDAO", "submitAddlTermCredit", "Exited");

		return appRefNo;
	}

	public ArrayList getAppRefNos(String mliId, String borrowerId, String borrowerName)
			throws DatabaseException, NoApplicationFoundException {
		Log.log(4, "ApplicationDAO", "getAppRefNos", "Entered");

		Connection connection = DBConnection.getConnection();

		ArrayList cgpanAppRefNoList = null;
		String bankId = "";
		String zoneId = "";
		String branchId = "";

		bankId = mliId.substring(0, 4);

		zoneId = mliId.substring(4, 8);

		branchId = mliId.substring(8, 12);
		try {
			if ((!mliId.equals("")) && (!borrowerName.equals(""))) {
				cgpanAppRefNoList = getDtlForBorMem(borrowerName, bankId, zoneId, branchId);
			} else if ((!borrowerId.equals("")) && (!mliId.equals(""))) {
				cgpanAppRefNoList = getDtlForBIDMem(borrowerId, bankId, zoneId, branchId);
			}

			connection.commit();
		} catch (DatabaseException dbException) {
			Log.log(4, "ApplicationDAO", "getAppRefNos", dbException.getMessage());
			Log.logException(dbException);
			throw new DatabaseException(dbException.getMessage());
		} catch (SQLException sqlException) {
			Log.log(2, "ApplicationDAO", "getAppRefNos", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
			}
			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(4, "ApplicationDAO", "getAppRefNos", "Exited");

		return cgpanAppRefNoList;
	}

	public ArrayList viewApplicationsForApprovalPath(String userId, String bankName) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "viewApplicationsForApprovalPath", "Entered");

		ArrayList tcApprovedList = new ArrayList();
		ArrayList wcApprovedList = new ArrayList();
		ArrayList clearAppsList = new ArrayList();
		Connection connection = DBConnection.getConnection(false);
		try {
			CallableStatement approvalList = connection
					.prepareCall("{?=call packGetPackagePath1.funcGetAppForApprovalPath1(?,?,?,?,?,?)}");

			approvalList.registerOutParameter(1, 4);
			approvalList.registerOutParameter(3, -10);
			approvalList.registerOutParameter(4, -10);
			approvalList.registerOutParameter(5, 4);
			approvalList.registerOutParameter(6, 12);

			approvalList.setString(2, userId);
			approvalList.setString(7, bankName);
			approvalList.execute();
			int functionReturnValue = approvalList.getInt(1);
			if (functionReturnValue == 1) {
				String error = approvalList.getString(6);
				approvalList.close();
				approvalList = null;
				connection.rollback();
				throw new DatabaseException(error);
			}

			ResultSet tcClearApplications = (ResultSet) approvalList.getObject(3);
			Application application = null;
			while (tcClearApplications.next()) {
				application = new Application();
				BorrowerDetails borrowerDetails = new BorrowerDetails();
				borrowerDetails.setSsiDetails(new SSIDetails());
				application.setBorrowerDetails(borrowerDetails);
				application.setAppRefNo(tcClearApplications.getString(1));
				Log.log(5, "ApplicationDAO", "n", "App ref no1:" + application.getAppRefNo());
				application.getBorrowerDetails().getSsiDetails().setBorrowerRefNo(tcClearApplications.getInt(2));
				Log.log(5, "ApplicationDAO", "viewApplicationsForApprovalPath",
						"SSI Ref No1:" + application.getBorrowerDetails().getSsiDetails().getBorrowerRefNo());
				application.setSubmittedDate(tcClearApplications.getDate(3));
				tcApprovedList.add(application);
				application = null;
			}
			tcClearApplications.close();
			tcClearApplications = null;
			ResultSet wcClearApplications = (ResultSet) approvalList.getObject(4);
			while (wcClearApplications.next()) {
				application = new Application();
				BorrowerDetails borrowerDetails = new BorrowerDetails();
				borrowerDetails.setSsiDetails(new SSIDetails());
				application.setBorrowerDetails(borrowerDetails);
				application.setAppRefNo(wcClearApplications.getString(1));
				Log.log(5, "ApplicationDAO", "viewApplicationsForApprovalPath",
						"App ref no 2:" + application.getAppRefNo());
				application.getBorrowerDetails().getSsiDetails().setBorrowerRefNo(wcClearApplications.getInt(2));
				Log.log(5, "ApplicationDAO", "viewApplicationsForApprovalPath",
						"SSI Ref No 2:" + application.getBorrowerDetails().getSsiDetails().getBorrowerRefNo());
				application.setSubmittedDate(wcClearApplications.getDate(3));
				wcApprovedList.add(application);
				application = null;
			}
			wcClearApplications.close();
			wcClearApplications = null;

			int appCount = approvalList.getInt(5);
			approvalList.close();
			approvalList = null;

			CallableStatement pendingApprovalList = connection
					.prepareCall("{?=call packGetPackagePath.funcGetPendingAppForApproval1(?,?,?,?,?,?)}");

			pendingApprovalList.registerOutParameter(1, 4);
			pendingApprovalList.registerOutParameter(3, -10);
			pendingApprovalList.registerOutParameter(4, -10);
			pendingApprovalList.registerOutParameter(5, 4);
			pendingApprovalList.registerOutParameter(6, 12);
			pendingApprovalList.setString(2, userId);
			pendingApprovalList.setString(7, bankName);
			pendingApprovalList.execute();
			int functionReturnValue1 = pendingApprovalList.getInt(1);
			if (functionReturnValue1 == 1) {
				String error = pendingApprovalList.getString(6);
				pendingApprovalList.close();
				pendingApprovalList = null;
				connection.rollback();
				throw new DatabaseException(error);
			}

			ResultSet tcPendingApplications = (ResultSet) pendingApprovalList.getObject(3);
			// Application application = null;
			while (tcPendingApplications.next()) {
				application = new Application();
				BorrowerDetails borrowerDetails = new BorrowerDetails();
				borrowerDetails.setSsiDetails(new SSIDetails());
				application.setBorrowerDetails(borrowerDetails);

				application.setAppRefNo(tcPendingApplications.getString(1));

				application.getBorrowerDetails().getSsiDetails().setBorrowerRefNo(tcPendingApplications.getInt(2));

				application.setSubmittedDate(tcPendingApplications.getDate(3));
				tcApprovedList.add(application);
				application = null;
			}
			tcPendingApplications.close();
			tcPendingApplications = null;
			ResultSet wcPendingApplications = (ResultSet) pendingApprovalList.getObject(4);
			while (wcPendingApplications.next()) {
				application = new Application();
				BorrowerDetails borrowerDetails = new BorrowerDetails();
				borrowerDetails.setSsiDetails(new SSIDetails());
				application.setBorrowerDetails(borrowerDetails);
				application.setAppRefNo(wcPendingApplications.getString(1));

				application.getBorrowerDetails().getSsiDetails().setBorrowerRefNo(wcPendingApplications.getInt(2));

				application.setSubmittedDate(wcPendingApplications.getDate(3));
				wcApprovedList.add(application);
				application = null;
			}
			wcPendingApplications.close();
			wcPendingApplications = null;

			int pendingCount = pendingApprovalList.getInt(5);

			int applicationCount = appCount + pendingCount;
			Log.log(4, "ApplicationDAO", "viewApplicationsForApprovalPath", "applicationCount" + applicationCount);
			Integer intCount = new Integer(applicationCount);
			clearAppsList.add(tcApprovedList);
			clearAppsList.add(wcApprovedList);
			clearAppsList.add(intCount);
			tcApprovedList = null;
			wcApprovedList = null;
			connection.commit();
		} catch (SQLException sqlException) {
			Log.log(2, "ApplicationDAO", "viewApplicationsForApprovalPath", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
			}
			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(4, "ApplicationDAO", "viewApplicationsForApprovalPath", "Exited");

		return clearAppsList;
	}

	public ArrayList viewApplicationsForApproval(String userId) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "viewApplicationsForApproval", "Entered");
		ArrayList tcApprovedList = new ArrayList();
		ArrayList wcApprovedList = new ArrayList();
		ArrayList clearAppsList = new ArrayList();

		Connection connection = DBConnection.getConnection(false);
		try {
			CallableStatement approvalList = connection
					.prepareCall("{?=call packGetAppForApproval.funcGetAppForApproval(?,?,?,?,?)}");

			approvalList.registerOutParameter(1, 4);
			approvalList.registerOutParameter(3, -10);
			approvalList.registerOutParameter(4, -10);
			approvalList.registerOutParameter(5, 4);
			approvalList.registerOutParameter(6, 12);

			approvalList.setString(2, userId);

			approvalList.execute();
			int functionReturnValue = approvalList.getInt(1);
			if (functionReturnValue == 1) {
				String error = approvalList.getString(6);

				approvalList.close();
				approvalList = null;

				connection.rollback();

				throw new DatabaseException(error);
			}

			ResultSet tcClearApplications = (ResultSet) approvalList.getObject(3);
			Application application = null;
			while (tcClearApplications.next()) {
				application = new Application();
				BorrowerDetails borrowerDetails = new BorrowerDetails();

				borrowerDetails.setSsiDetails(new SSIDetails());
				application.setBorrowerDetails(borrowerDetails);

				application.setAppRefNo(tcClearApplications.getString(1));
				// System.out.println("tc|" + tcClearApplications.getString(1));
				Log.log(5, "ApplicationDAO", "n", "App ref no1:" + application.getAppRefNo());
				application.getBorrowerDetails().getSsiDetails().setBorrowerRefNo(tcClearApplications.getInt(2));

				Log.log(5, "ApplicationDAO", "viewApplicationsForApproval",
						"SSI Ref No1:" + application.getBorrowerDetails().getSsiDetails().getBorrowerRefNo());
				application.setSubmittedDate(tcClearApplications.getDate(3));

				tcApprovedList.add(application);
				application = null;
			}

			tcClearApplications.close();
			tcClearApplications = null;

			ResultSet wcClearApplications = (ResultSet) approvalList.getObject(4);

			while (wcClearApplications.next()) {
				application = new Application();
				BorrowerDetails borrowerDetails = new BorrowerDetails();

				borrowerDetails.setSsiDetails(new SSIDetails());
				application.setBorrowerDetails(borrowerDetails);

				application.setAppRefNo(wcClearApplications.getString(1));

				// System.out.println("wc||" + wcClearApplications.getString(1));
				Log.log(5, "ApplicationDAO", "viewApplicationsForApproval",
						"App ref no 2:" + application.getAppRefNo());
				application.getBorrowerDetails().getSsiDetails().setBorrowerRefNo(wcClearApplications.getInt(2));

				Log.log(5, "ApplicationDAO", "viewApplicationsForApproval",
						"SSI Ref No 2:" + application.getBorrowerDetails().getSsiDetails().getBorrowerRefNo());
				application.setSubmittedDate(wcClearApplications.getDate(3));

				wcApprovedList.add(application);
				application = null;
			}

			wcClearApplications.close();
			wcClearApplications = null;

			int appCount = approvalList.getInt(5);

			approvalList.close();
			approvalList = null;

			CallableStatement pendingApprovalList = connection
					.prepareCall("{?=call packGetPendingAppForApproval.funcGetPendingAppForApproval(?,?,?,?,?)}");

			pendingApprovalList.registerOutParameter(1, 4);
			pendingApprovalList.registerOutParameter(3, -10);
			pendingApprovalList.registerOutParameter(4, -10);
			pendingApprovalList.registerOutParameter(5, 4);
			pendingApprovalList.registerOutParameter(6, 12);

			pendingApprovalList.setString(2, userId);

			pendingApprovalList.execute();

			int functionReturnValue1 = pendingApprovalList.getInt(1);
			if (functionReturnValue1 == 1) {
				String error = pendingApprovalList.getString(6);

				pendingApprovalList.close();
				pendingApprovalList = null;

				connection.rollback();

				throw new DatabaseException(error);
			}

			ResultSet tcPendingApplications = (ResultSet) pendingApprovalList.getObject(3);
			// Application application = null;
			while (tcPendingApplications.next()) {
				application = new Application();
				BorrowerDetails borrowerDetails = new BorrowerDetails();

				borrowerDetails.setSsiDetails(new SSIDetails());
				application.setBorrowerDetails(borrowerDetails);

				application.setAppRefNo(tcPendingApplications.getString(1));
				Log.log(5, "ApplicationDAO", "n", "App ref no1:" + application.getAppRefNo());
				application.getBorrowerDetails().getSsiDetails().setBorrowerRefNo(tcPendingApplications.getInt(2));
				Log.log(5, "ApplicationDAO", "viewApplicationsForApproval",
						"SSI Ref No1:" + application.getBorrowerDetails().getSsiDetails().getBorrowerRefNo());
				application.setSubmittedDate(tcPendingApplications.getDate(3));

				tcApprovedList.add(application);
				application = null;
			}

			tcPendingApplications.close();
			tcPendingApplications = null;

			ResultSet wcPendingApplications = (ResultSet) pendingApprovalList.getObject(4);

			while (wcPendingApplications.next()) {
				application = new Application();
				BorrowerDetails borrowerDetails = new BorrowerDetails();

				borrowerDetails.setSsiDetails(new SSIDetails());
				application.setBorrowerDetails(borrowerDetails);

				application.setAppRefNo(wcPendingApplications.getString(1));
				Log.log(5, "ApplicationDAO", "viewApplicationsForApproval",
						"App ref no 2:" + application.getAppRefNo());
				application.getBorrowerDetails().getSsiDetails().setBorrowerRefNo(wcPendingApplications.getInt(2));
				Log.log(5, "ApplicationDAO", "viewApplicationsForApproval",
						"SSI Ref No 2:" + application.getBorrowerDetails().getSsiDetails().getBorrowerRefNo());
				application.setSubmittedDate(wcPendingApplications.getDate(3));

				wcApprovedList.add(application);
				application = null;
			}

			wcPendingApplications.close();
			wcPendingApplications = null;

			int pendingCount = pendingApprovalList.getInt(5);
			Log.log(4, "ApplicationDAO", "viewApplicationsForApproval", "pendingCount" + pendingCount);

			int applicationCount = appCount + pendingCount;

			Log.log(4, "ApplicationDAO", "viewApplicationsForApproval", "applicationCount" + applicationCount);

			Integer intCount = new Integer(applicationCount);

			clearAppsList.add(tcApprovedList);
			clearAppsList.add(wcApprovedList);
			clearAppsList.add(intCount);
			tcApprovedList = null;
			wcApprovedList = null;

			connection.commit();
		} catch (SQLException sqlException) {
			Log.log(2, "ApplicationDAO", "viewApplicationsForApproval", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
			}
			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(4, "ApplicationDAO", "viewApplicationsForApproval", "Exited");

		return clearAppsList;
	}

	public ArrayList getCgpansForRenewal(String id, String type) {
		return null;
	}

	public void updateLoanAmount(String CGPAN, Double ApprovedAmount, String ReapprovalComments)
			throws DatabaseException {
	}

	public void changeApplicationStatus(String cgpan, String status) throws DatabaseException {
	}

	public ArrayList getLiveApplications(String bankId, String zoneId, String branchId) throws DatabaseException {
		String methodName = "getLiveApplications";

		Connection connection = null;
		CallableStatement getliveApplicationStmt = null;
		ArrayList liveApplications = null;
		ResultSet resultSet = null;

		Application application = null;
		SSIDetails ssiDetails = null;
		BorrowerDetails borrowerDetails = null;

		int status = 0;
		Log.log(4, "ApplicationDAO", methodName, "Entering");
		try {
			String exception = "";

			connection = DBConnection.getConnection(false);
			if (bankId == null) {
				getliveApplicationStmt = connection.prepareCall("{?=call packGetLiveApp.funcGetLiveApp(?,?)}");
			} else {
				getliveApplicationStmt = connection
						.prepareCall("{?=call packGetMemberLiveApppath.funcGetMemberLiveApp(?,?,?,?,?)}");

				getliveApplicationStmt.setString(4, bankId);
				getliveApplicationStmt.setString(5, zoneId);
				getliveApplicationStmt.setString(6, branchId);
			}

			getliveApplicationStmt.registerOutParameter(1, 4);

			getliveApplicationStmt.registerOutParameter(2, -10);

			getliveApplicationStmt.registerOutParameter(3, 12);

			Log.log(4, "ApplicationDAO", methodName, "Before executing Stored Procedure");
			getliveApplicationStmt.execute();
			Log.log(4, "ApplicationDAO", methodName, "After executing Stored Procedure");

			resultSet = (ResultSet) getliveApplicationStmt.getObject(2);
			Log.log(4, "ApplicationDAO", methodName, "ResultSet = " + resultSet.toString());

			status = getliveApplicationStmt.getInt(1);
			Log.log(4, "ApplicationDAO", methodName, "Status = " + status);
			if (status != 0) {
				String error = getliveApplicationStmt.getString(3);
				getliveApplicationStmt.close();
				getliveApplicationStmt = null;

				throw new DatabaseException(error);
			}

			int recordCount = 0;

			while (resultSet.next()) {
				if (recordCount == 0) {
					liveApplications = new ArrayList();
				}
				application = new Application();

				Log.log(4, "ApplicationDAO", methodName, "Inside While : CGPAN=" + resultSet.getString(1));
				application.setCgpan(resultSet.getString(1));

				Log.log(4, "ApplicationDAO", methodName, "Inside While : Bank Id=" + resultSet.getString(2));
				application.setBankId(resultSet.getString(2));

				Log.log(4, "ApplicationDAO", methodName, "Inside While : Zone Id=" + resultSet.getString(3));
				application.setZoneId(resultSet.getString(3));

				Log.log(4, "ApplicationDAO", methodName, "Inside While : Branch Id=" + resultSet.getString(4));
				application.setBranchId(resultSet.getString(4));

				Log.log(4, "ApplicationDAO", methodName, "Inside While : Guarantee Fee=" + resultSet.getDouble(5));
				application.setGuaranteeAmount(resultSet.getDouble(5));

				ssiDetails = new SSIDetails();
				ssiDetails.setCgbid(resultSet.getString(6));

				Log.log(4, "ApplicationDAO", methodName, "Inside While : Approved Amount=" + resultSet.getDouble(7));
				application.setApprovedAmount(resultSet.getDouble(7));

				int schemeId = resultSet.getInt(8);

				if (schemeId == 1) {
					application.setScheme("CGFSI");
				} else if (schemeId == 2) {
					application.setScheme("MCGS");
				} else if (schemeId == 3) {
					application.setScheme("RSF");
				}

				Log.log(4, "ApplicationDAO", methodName, "Inside While : district=" + resultSet.getString(9));
				application.setDistrict(resultSet.getString(9));

				Log.log(4, "ApplicationDAO", methodName, "Inside While : State=" + resultSet.getString(10));
				application.setState(resultSet.getString(10));

				Log.log(4, "ApplicationDAO", methodName, "Inside While : SocialCategory =" + resultSet.getString(11));
				application.setSocialCategory(resultSet.getString(11));

				Log.log(4, "ApplicationDAO", methodName, "Inside While : sex=" + resultSet.getString(12));
				application.setSex(resultSet.getString(12));

				Log.log(4, "ApplicationDAO", methodName, "Inside While : ssiRef=" + resultSet.getString(13));
				application.setSsiRef(resultSet.getString(13));

				borrowerDetails = new BorrowerDetails();
				borrowerDetails.setSsiDetails(ssiDetails);

				application.setBorrowerDetails(borrowerDetails);

				liveApplications.add(application);
				recordCount++;
			}

			resultSet.close();
			resultSet = null;
			getliveApplicationStmt.close();
			getliveApplicationStmt = null;
		} catch (Exception exception) {
			throw new DatabaseException(exception.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		return liveApplications;
	}

	public ArrayList getExpApplications(String bankId, String zoneId, String branchId) throws DatabaseException {
		String methodName = "getExpApplications";

		Connection connection = null;
		CallableStatement getliveApplicationStmt = null;
		ArrayList liveApplications = null;
		ResultSet resultSet = null;

		Application application = null;
		SSIDetails ssiDetails = null;
		BorrowerDetails borrowerDetails = null;

		int status = 0;
		Log.log(4, "ApplicationDAO", methodName, "Entering");
		try {
			String exception = "";

			connection = DBConnection.getConnection(false);
			if (bankId == null) {
				getliveApplicationStmt = connection.prepareCall("{?=call packGetExpApp.funcGetExpApp(?,?)}");
			} else {
				getliveApplicationStmt = connection
						.prepareCall("{?=call packGetMemberExpAppPath.funcGetMemberExpApp(?,?,?,?,?)}");

				getliveApplicationStmt.setString(4, bankId);
				getliveApplicationStmt.setString(5, zoneId);
				getliveApplicationStmt.setString(6, branchId);
			}

			getliveApplicationStmt.registerOutParameter(1, 4);

			getliveApplicationStmt.registerOutParameter(2, -10);

			getliveApplicationStmt.registerOutParameter(3, 12);

			Log.log(4, "ApplicationDAO", methodName, "Before executing Stored Procedure");
			getliveApplicationStmt.execute();
			Log.log(4, "ApplicationDAO", methodName, "After executing Stored Procedure");

			resultSet = (ResultSet) getliveApplicationStmt.getObject(2);
			Log.log(4, "ApplicationDAO", methodName, "ResultSet = " + resultSet.toString());

			status = getliveApplicationStmt.getInt(1);
			Log.log(4, "ApplicationDAO", methodName, "Status = " + status);
			if (status != 0) {
				String error = getliveApplicationStmt.getString(3);
				getliveApplicationStmt.close();
				getliveApplicationStmt = null;

				throw new DatabaseException(error);
			}

			int recordCount = 0;

			while (resultSet.next()) {
				if (recordCount == 0) {
					liveApplications = new ArrayList();
				}
				application = new Application();

				Log.log(4, "ApplicationDAO", methodName, "Inside While : CGPAN=" + resultSet.getString(1));
				application.setCgpan(resultSet.getString(1));

				Log.log(4, "ApplicationDAO", methodName, "Inside While : Bank Id=" + resultSet.getString(2));
				application.setBankId(resultSet.getString(2));

				Log.log(4, "ApplicationDAO", methodName, "Inside While : Zone Id=" + resultSet.getString(3));
				application.setZoneId(resultSet.getString(3));

				Log.log(4, "ApplicationDAO", methodName, "Inside While : Branch Id=" + resultSet.getString(4));
				application.setBranchId(resultSet.getString(4));

				Log.log(4, "ApplicationDAO", methodName, "Inside While : Guarantee Fee=" + resultSet.getDouble(5));
				application.setGuaranteeAmount(resultSet.getDouble(5));

				ssiDetails = new SSIDetails();
				ssiDetails.setCgbid(resultSet.getString(6));

				Log.log(4, "ApplicationDAO", methodName, "Inside While : Approved Amount=" + resultSet.getDouble(7));
				application.setApprovedAmount(resultSet.getDouble(7));

				int schemeId = resultSet.getInt(8);

				if (schemeId == 1) {
					application.setScheme("CGFSI");
				} else if (schemeId == 2) {
					application.setScheme("MCGS");
				}

				Log.log(4, "ApplicationDAO", methodName, "Inside While : district=" + resultSet.getString(9));
				application.setDistrict(resultSet.getString(9));

				Log.log(4, "ApplicationDAO", methodName, "Inside While : State=" + resultSet.getString(10));
				application.setState(resultSet.getString(10));

				Log.log(4, "ApplicationDAO", methodName, "Inside While : SocialCategory =" + resultSet.getString(11));
				application.setSocialCategory(resultSet.getString(11));

				Log.log(4, "ApplicationDAO", methodName, "Inside While : sex=" + resultSet.getString(12));
				application.setSex(resultSet.getString(12));

				Log.log(4, "ApplicationDAO", methodName, "Inside While : ssiRef=" + resultSet.getString(13));
				application.setSsiRef(resultSet.getString(13));

				borrowerDetails = new BorrowerDetails();
				borrowerDetails.setSsiDetails(ssiDetails);

				application.setBorrowerDetails(borrowerDetails);

				liveApplications.add(application);
				recordCount++;
			}

			resultSet.close();
			resultSet = null;
			getliveApplicationStmt.close();
			getliveApplicationStmt = null;
		} catch (Exception exception) {
			throw new DatabaseException(exception.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		return liveApplications;
	}

	public ArrayList getExpiredCloseApplications(String bankId, String zoneId, String branchId)
			throws DatabaseException {
		String methodName = "getExpiredCloseApplications";
		Connection connection = null;
		CallableStatement getExpiredApplicationStmt = null;
		ArrayList expiredApplications = null;
		ResultSet resultSet = null;

		Application application = null;
		SSIDetails ssiDetails = null;
		BorrowerDetails borrowerDetails = null;

		int status = 0;
		Log.log(4, "ApplicationDAO", methodName, "Entering");
		try {
			String exception = "";

			connection = DBConnection.getConnection(false);
			if (bankId == null) {
				getExpiredApplicationStmt = connection.prepareCall("{?=call packGetExpiredApp.funcGetExpiredApp(?,?)}");
			} else {
				getExpiredApplicationStmt = connection
						.prepareCall("{?=call packGetMemberExpApp.funcGetMemberExpApp(?,?,?,?,?)}");

				getExpiredApplicationStmt.setString(4, bankId);
				getExpiredApplicationStmt.setString(5, zoneId);
				getExpiredApplicationStmt.setString(6, branchId);
			}

			getExpiredApplicationStmt.registerOutParameter(1, 4);

			getExpiredApplicationStmt.registerOutParameter(2, -10);

			getExpiredApplicationStmt.registerOutParameter(3, 12);

			Log.log(4, "ApplicationDAO", methodName, "Before executing Stored Procedure");
			getExpiredApplicationStmt.execute();
			Log.log(4, "ApplicationDAO", methodName, "After executing Stored Procedure");

			resultSet = (ResultSet) getExpiredApplicationStmt.getObject(2);
			Log.log(4, "ApplicationDAO", methodName, "ResultSet = " + resultSet.toString());

			status = getExpiredApplicationStmt.getInt(1);
			Log.log(4, "ApplicationDAO", methodName, "Status = " + status);
			if (status != 0) {
				String error = getExpiredApplicationStmt.getString(3);
				getExpiredApplicationStmt.close();
				getExpiredApplicationStmt = null;

				throw new DatabaseException(error);
			}

			int recordCount = 0;

			while (resultSet.next()) {
				if (recordCount == 0) {
					expiredApplications = new ArrayList();
				}
				application = new Application();

				Log.log(4, "ApplicationDAO", methodName, "Inside While : CGPAN=" + resultSet.getString(1));
				application.setCgpan(resultSet.getString(1));

				Log.log(4, "ApplicationDAO", methodName, "Inside While : Bank Id=" + resultSet.getString(2));
				application.setBankId(resultSet.getString(2));

				Log.log(4, "ApplicationDAO", methodName, "Inside While : Zone Id=" + resultSet.getString(3));
				application.setZoneId(resultSet.getString(3));

				Log.log(4, "ApplicationDAO", methodName, "Inside While : Branch Id=" + resultSet.getString(4));
				application.setBranchId(resultSet.getString(4));

				Log.log(4, "ApplicationDAO", methodName, "Inside While : Guarantee Fee=" + resultSet.getDouble(5));
				application.setGuaranteeAmount(resultSet.getDouble(5));

				ssiDetails = new SSIDetails();
				ssiDetails.setCgbid(resultSet.getString(6));

				Log.log(4, "ApplicationDAO", methodName, "Inside While : Approved Amount=" + resultSet.getDouble(7));
				application.setApprovedAmount(resultSet.getDouble(7));

				application.setSubmittedDate(DateHelper.sqlToUtilDate(resultSet.getDate(8)));

				borrowerDetails = new BorrowerDetails();
				borrowerDetails.setSsiDetails(ssiDetails);

				application.setBorrowerDetails(borrowerDetails);

				expiredApplications.add(application);
				recordCount++;
			}

			resultSet.close();
			resultSet = null;
			getExpiredApplicationStmt.close();
			getExpiredApplicationStmt = null;
		} catch (Exception exception) {
			throw new DatabaseException(exception.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		return expiredApplications;
	}

	public double getOutstandingAmount(String cgpan) throws DatabaseException {
		return 0.0D;
	}

	public ArrayList getAllApplications(int flag, String mliId) {
		return null;
	}

	public ArrayList getMessageTitleContent() throws DatabaseException {
		Log.log(4, "ApplicationDAO", "getMessageTitleContent", "Entered");

		Connection connection = DBConnection.getConnection(false);
		ArrayList msgTitles = new ArrayList();
		ArrayList msgContents = new ArrayList();
		ArrayList msgTitleContent = new ArrayList();
		try {
			CallableStatement messageTitleList = connection
					.prepareCall("{?=call packgetInsUpdSpecialMsg.funcGetAllMsgTitles(?,?)}");

			messageTitleList.registerOutParameter(1, 4);
			messageTitleList.registerOutParameter(2, -10);
			messageTitleList.registerOutParameter(3, 12);

			messageTitleList.execute();

			int functionReturnValue = messageTitleList.getInt(1);
			Log.log(5, "ApplicationDAO", "getMessageTitleContent", "Special Message Result :" + functionReturnValue);

			if (functionReturnValue == 1) {
				String error = messageTitleList.getString(3);

				messageTitleList.close();
				messageTitleList = null;

				connection.rollback();

				throw new DatabaseException(error);
			}
			ResultSet messageTitles = (ResultSet) messageTitleList.getObject(2);
			while (messageTitles.next()) {
				String title = messageTitles.getString(1);
				Log.log(5, "ApplicationDAO", "getMessageTitleContent", "Title from the result Set :" + title);

				String titleMessage = messageTitles.getString(2);
				Log.log(5, "ApplicationDAO", "getMessageTitleContent",
						"Title Message from the result Set :" + titleMessage);

				msgTitles.add(title);
				msgContents.add(titleMessage);
			}
			messageTitles.close();
			messageTitles = null;
			messageTitleList.close();
			messageTitleList = null;

			connection.commit();
		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "getMessageTitleContent", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
			}
			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		msgTitleContent.add(msgTitles);
		msgTitleContent.add(msgContents);

		Log.log(4, "ApplicationDAO", "getMessageTitleContent", "Exited");

		return msgTitleContent;
	}

	public SpecialMessage getMessageDesc(String msgTitle) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "getMessageDesc", "Entered");

		Connection connection = DBConnection.getConnection(false);
		SpecialMessage specialMessage = new SpecialMessage();
		DateConverter dateConverter = new DateConverter();
		try {
			CallableStatement messageDescList = connection
					.prepareCall("{?=call packgetInsUpdSpecialMsg.funcGetMsgForTitle(?,?,?,?,?)}");

			messageDescList.registerOutParameter(1, 4);
			messageDescList.registerOutParameter(3, 12);
			messageDescList.registerOutParameter(4, 91);
			messageDescList.registerOutParameter(5, 91);
			messageDescList.registerOutParameter(6, 12);

			messageDescList.setString(2, msgTitle);

			messageDescList.execute();

			int functionReturnValue = messageDescList.getInt(1);
			Log.log(5, "ApplicationDAO", "getMessageDesc", "Special Message Result :" + functionReturnValue);

			if (functionReturnValue == 1) {
				String error = messageDescList.getString(6);

				messageDescList.close();
				messageDescList = null;

				connection.rollback();

				throw new DatabaseException(error);
			}
			specialMessage.setMessageDesc(messageDescList.getString(3));
			specialMessage.setValidityFromDate(DateHelper.sqlToUtilDate(messageDescList.getDate(4)));
			specialMessage.setValidityToDate(DateHelper.sqlToUtilDate(messageDescList.getDate(5)));

			messageDescList.close();
			messageDescList = null;

			connection.commit();
		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "getMessageDesc", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
			}
			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(4, "ApplicationDAO", "getMessageDesc", "Exited");

		return specialMessage;
	}

	public void addSpecialMessage(SpecialMessage specialMessage) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "addSpecialMessage", "Entered");

		Connection connection = DBConnection.getConnection(false);
		try {
			CallableStatement insertSplMsg = connection
					.prepareCall("{?=call packgetInsUpdSpecialMsg.funcInsertSplMsg(?,?,?,?,?)}");

			insertSplMsg.registerOutParameter(1, 4);
			insertSplMsg.registerOutParameter(6, 12);

			insertSplMsg.setString(2, specialMessage.getMsgTitle());
			insertSplMsg.setString(3, specialMessage.getMessageDesc());
			insertSplMsg.setDate(4, new java.sql.Date(specialMessage.getValidityFromDate().getTime()));
			Log.log(5, "ApplicationDAO", "addSpecialMessage", "to date :" + specialMessage.getValidityToDate());
			if ((specialMessage.getValidityToDate() != null)
					&& (!specialMessage.getValidityToDate().toString().equals(""))) {
				insertSplMsg.setDate(5, new java.sql.Date(specialMessage.getValidityToDate().getTime()));
			} else {
				insertSplMsg.setDate(5, null);
			}

			insertSplMsg.execute();

			int functionReturnValue = insertSplMsg.getInt(1);
			Log.log(5, "ApplicationDAO", "addSpecialMessage", "Insert Special Message Result :" + functionReturnValue);

			if (functionReturnValue == 1) {
				String error = insertSplMsg.getString(6);

				insertSplMsg.close();
				insertSplMsg = null;

				connection.rollback();

				throw new DatabaseException(error);
			}
			insertSplMsg.close();
			insertSplMsg = null;

			connection.commit();
		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "addSpecialMessage", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
			}
			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(4, "ApplicationDAO", "addSpecialMessage", "Exited");
	}

	public void updateSpecialMessage(SpecialMessage specialMessage) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "updateSpecialMessage", "Entered");

		Connection connection = DBConnection.getConnection(false);
		try {
			CallableStatement updateSplMsg = connection
					.prepareCall("{?=call packgetInsUpdSpecialMsg.funcUpdateSplMsg(?,?,?,?,?)}");

			updateSplMsg.registerOutParameter(1, 4);
			updateSplMsg.registerOutParameter(6, 12);

			updateSplMsg.setString(2, specialMessage.getMsgTitle());
			updateSplMsg.setString(3, specialMessage.getMessageDesc());
			updateSplMsg.setDate(4, new java.sql.Date(specialMessage.getValidityFromDate().getTime()));
			if ((specialMessage.getValidityToDate() != null)
					&& (!specialMessage.getValidityToDate().toString().equals(""))) {
				updateSplMsg.setDate(5, new java.sql.Date(specialMessage.getValidityToDate().getTime()));
			} else {
				updateSplMsg.setDate(5, null);
			}

			updateSplMsg.execute();

			int functionReturnValue = updateSplMsg.getInt(1);
			Log.log(5, "ApplicationDAO", "updateSpecialMessage",
					"Update Special Message Result :" + functionReturnValue);

			if (functionReturnValue == 1) {
				String error = updateSplMsg.getString(6);

				updateSplMsg.close();
				updateSplMsg = null;

				connection.rollback();

				throw new DatabaseException(error);
			}
			updateSplMsg.close();
			updateSplMsg = null;

			connection.commit();
		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "updateSpecialMessage", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
			}
			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(4, "ApplicationDAO", "updateSpecialMessage", "Exited");
	}

	public String generateCgpan(Application application) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "generateCgpan", "Entered");

		String cgpan = "";
		Connection connection = DBConnection.getConnection(false);
		try {
			String loanType = application.getLoanType();

			Log.log(4, "ApplicationDAO", "generateCgpan", "CGPAN Reference :" + application.getCgpanReference());

			if ((application.getCgpanReference() != null) && (!application.getCgpanReference().equals(""))
					&& (!application.getCgpanReference().substring(0, 2).equals("CG"))) {
				Log.log(4, "ApplicationDAO", "generateCgpan", "cgpan reference not null");
				CallableStatement bothCgpan = connection.prepareCall("{?=call funcGenCGPANforBoth(?,?,?)}");

				bothCgpan.registerOutParameter(1, 4);
				bothCgpan.setString(2, application.getAppRefNo());
				bothCgpan.registerOutParameter(3, 12);
				bothCgpan.registerOutParameter(4, 12);

				bothCgpan.execute();

				int functionReturnValue = bothCgpan.getInt(1);
				Log.log(5, "ApplicationDAO", "generateCgpan", "generate cgpan:" + functionReturnValue);

				if (functionReturnValue == 1) {
					String error = bothCgpan.getString(4);

					bothCgpan.close();
					bothCgpan = null;

					connection.rollback();

					throw new DatabaseException(error);
				}

				cgpan = bothCgpan.getString(3);

				bothCgpan.close();
				bothCgpan = null;
			} else if ((loanType.equals("TC")) || (loanType.equals("CC"))) {
				CallableStatement tcCgpan = connection.prepareCall("{?=call funcGenCGPANforTC(?,?)}");

				tcCgpan.registerOutParameter(1, 4);
				tcCgpan.registerOutParameter(2, 12);
				tcCgpan.registerOutParameter(3, 12);

				tcCgpan.execute();

				int functionReturnValue = tcCgpan.getInt(1);
				Log.log(5, "ApplicationDAO", "generateCgpan", "generate cgpan:" + functionReturnValue);

				if (functionReturnValue == 1) {
					String error = tcCgpan.getString(3);

					tcCgpan.close();
					tcCgpan = null;

					connection.rollback();

					throw new DatabaseException(error);
				}

				cgpan = tcCgpan.getString(2);

				tcCgpan.close();
				tcCgpan = null;
			} else if (loanType.equals("WC")) {
				CallableStatement wcCgpan = connection.prepareCall("{?=call funcGenCGPANforWC(?,?)}");

				wcCgpan.registerOutParameter(1, 4);
				wcCgpan.registerOutParameter(2, 12);
				wcCgpan.registerOutParameter(3, 12);

				wcCgpan.execute();

				int functionReturnValue = wcCgpan.getInt(1);
				Log.log(5, "ApplicationDAO", "generateCgpan", "generate cgpan:" + functionReturnValue);

				if (functionReturnValue == 1) {
					String error = wcCgpan.getString(3);

					wcCgpan.close();
					wcCgpan = null;

					connection.rollback();

					throw new DatabaseException(error);
				}

				cgpan = wcCgpan.getString(2);

				wcCgpan.close();
				wcCgpan = null;
			}

			connection.commit();
		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "generateCgpan", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
			}
			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(4, "ApplicationDAO", "generateCgpan", "Exited");

		return cgpan;
	}

	public void updateCgpan(Application application, Connection connection) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "updateCgpan", "Entered");
		try {
			CallableStatement cgpanUpdate = connection.prepareCall("{?=call funcUpdateCGPAN(?,?,?)}");

			cgpanUpdate.registerOutParameter(1, 4);
			cgpanUpdate.registerOutParameter(4, 12);

			cgpanUpdate.setString(2, application.getAppRefNo());
			cgpanUpdate.setString(3, application.getCgpan());

			cgpanUpdate.execute();

			int functionReturnValue = cgpanUpdate.getInt(1);
			Log.log(5, "ApplicationDAO", "updateCgpan", "update cgpan:" + functionReturnValue);

			if (functionReturnValue == 1) {
				String error = cgpanUpdate.getString(4);

				cgpanUpdate.close();
				cgpanUpdate = null;

				connection.rollback();

				throw new DatabaseException(error);
			}
			cgpanUpdate.close();
			cgpanUpdate = null;
		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "updateCgpan", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
			}
			throw new DatabaseException(sqlException.getMessage());
		}

		Log.log(4, "ApplicationDAO", "updateCgpan", "Exited");
	}

	public void updateGeneralStatus(Application application, String userId) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "updateGeneralStatus", "Entered");
		Connection connection = DBConnection.getConnection(false);
		try {
			CallableStatement generalUpdate = connection.prepareCall("{?=call funcUpdateGeneralStatus(?,?,?,?,?)}");

			generalUpdate.registerOutParameter(1, 4);
			generalUpdate.registerOutParameter(6, 12);

			generalUpdate.setString(2, application.getAppRefNo());
			Log.log(5, "ApplicationDAO", "updateGeneralStatus", "app ref no:" + application.getAppRefNo());
			generalUpdate.setString(3, application.getStatus());
			Log.log(5, "ApplicationDAO", "updateGeneralStatus", "status:" + application.getStatus());
			if ((application.getRemarks() != null) && (!application.getRemarks().equals(""))) {
				generalUpdate.setString(4, application.getRemarks());
			} else {
				generalUpdate.setString(4, null);
			}
			Log.log(5, "ApplicationDAO", "updateGeneralStatus", "remarks:" + application.getRemarks());

			generalUpdate.setString(5, userId);
			Log.log(5, "ApplicationDAO", "updateGeneralStatus", "user id:" + userId);

			generalUpdate.execute();

			int functionReturnValue = generalUpdate.getInt(1);
			Log.log(5, "ApplicationDAO", "updateGeneralStatus", "update general status:" + functionReturnValue);

			if (functionReturnValue == 1) {
				String error = generalUpdate.getString(6);

				generalUpdate.close();
				generalUpdate = null;

				connection.rollback();

				throw new DatabaseException(error);
			}
			generalUpdate.close();
			generalUpdate = null;

			connection.commit();
		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "updateGeneralStatus", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
			}
			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(4, "ApplicationDAO", "updateGeneralStatus", "Exited");
	}

	public String generateCgbid(int ssiRefNo, Connection connection) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "generateCgbid", "Entered");

		String cgbid = "";
		try {
			CallableStatement cgbidValue = connection.prepareCall("{?=call funcGenerateBID(?,?,?)}");

			cgbidValue.registerOutParameter(1, 4);
			cgbidValue.registerOutParameter(3, 12);
			cgbidValue.registerOutParameter(4, 12);

			cgbidValue.setInt(2, ssiRefNo);

			cgbidValue.execute();

			int functionReturnValue = cgbidValue.getInt(1);
			Log.log(5, "ApplicationDAO", "generateCgbid", "generation of cgbid:" + functionReturnValue);

			if (functionReturnValue == 1) {
				String error = cgbidValue.getString(4);

				cgbidValue.close();
				cgbidValue = null;

				connection.rollback();

				throw new DatabaseException(error);
			}

			cgbid = cgbidValue.getString(3);

			cgbidValue.close();
			cgbidValue = null;

			Log.log(4, "ApplicationDAO", "cgbid value :", cgbid);
		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "generateCgbid", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
			}
			throw new DatabaseException(sqlException.getMessage());
		}

		Log.log(4, "ApplicationDAO", "generateCgbid", "Exited");

		return cgbid;
	}

	public void updateCgbid(int ssiRefNo, String cgbid, Connection connection) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "updateCgbid", "Entered");
		try {
			CallableStatement cgbidUpdate = connection.prepareCall("{?=call funcUpdateBID(?,?,?)}");

			cgbidUpdate.registerOutParameter(1, 4);
			cgbidUpdate.registerOutParameter(4, 12);

			cgbidUpdate.setInt(2, ssiRefNo);
			cgbidUpdate.setString(3, cgbid);

			cgbidUpdate.execute();

			int functionReturnValue = cgbidUpdate.getInt(1);
			Log.log(5, "ApplicationDAO", "updateCgbid", "update cgbid:" + functionReturnValue);

			if (functionReturnValue == 1) {
				String error = cgbidUpdate.getString(4);

				cgbidUpdate.close();
				cgbidUpdate = null;

				connection.rollback();

				throw new DatabaseException(error);
			}
			cgbidUpdate.close();
			cgbidUpdate = null;
		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "updateCgbid", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
			}
			throw new DatabaseException(sqlException.getMessage());
		}

		Log.log(4, "ApplicationDAO", "updateCgbid", "Exited");
	}

	public BorrowerDetails viewBorrowerDetails(int ssiRefNo) throws DatabaseException {
		Application application = new Application();
		BorrowerDetails borrowerDetails = new BorrowerDetails();
		SSIDetails ssiDetails = new SSIDetails();
		Log.log(4, "ApplicationDAO", "viewBorrowerDetails", "Entered");
		Connection connection = DBConnection.getConnection(false);
		try {
			CallableStatement ssiDtlForSsiRefNo = connection.prepareCall(
					"{?=call funcGetSSIDetailforSSIRef(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			Log.log(4, "ApplicationDAO", "fetchBorrowerDtls", "SSI REF No" + ssiRefNo);
			ssiDtlForSsiRefNo.setInt(2, ssiRefNo);
			ssiDtlForSsiRefNo.registerOutParameter(1, 4);
			ssiDtlForSsiRefNo.registerOutParameter(30, 12);
			ssiDtlForSsiRefNo.registerOutParameter(3, 4);
			ssiDtlForSsiRefNo.registerOutParameter(4, 12);
			ssiDtlForSsiRefNo.registerOutParameter(5, 12);
			ssiDtlForSsiRefNo.registerOutParameter(6, 12);
			ssiDtlForSsiRefNo.registerOutParameter(7, 12);
			ssiDtlForSsiRefNo.registerOutParameter(8, 12);
			ssiDtlForSsiRefNo.registerOutParameter(9, 12);
			ssiDtlForSsiRefNo.registerOutParameter(10, 12);
			ssiDtlForSsiRefNo.registerOutParameter(11, 12);
			ssiDtlForSsiRefNo.registerOutParameter(12, 91);
			ssiDtlForSsiRefNo.registerOutParameter(13, 12);
			ssiDtlForSsiRefNo.registerOutParameter(14, 12);
			ssiDtlForSsiRefNo.registerOutParameter(15, 4);
			ssiDtlForSsiRefNo.registerOutParameter(16, 8);
			ssiDtlForSsiRefNo.registerOutParameter(17, 8);
			ssiDtlForSsiRefNo.registerOutParameter(18, 12);
			ssiDtlForSsiRefNo.registerOutParameter(19, 12);
			ssiDtlForSsiRefNo.registerOutParameter(20, 12);
			ssiDtlForSsiRefNo.registerOutParameter(21, 12);
			ssiDtlForSsiRefNo.registerOutParameter(22, 12);
			ssiDtlForSsiRefNo.registerOutParameter(23, 12);
			ssiDtlForSsiRefNo.registerOutParameter(24, 12);
			ssiDtlForSsiRefNo.registerOutParameter(25, 12);
			ssiDtlForSsiRefNo.registerOutParameter(26, 12);
			ssiDtlForSsiRefNo.registerOutParameter(27, 12);
			ssiDtlForSsiRefNo.registerOutParameter(28, 8);
			ssiDtlForSsiRefNo.registerOutParameter(29, 12);
			ssiDtlForSsiRefNo.executeQuery();
			int ssiDtlForSsiRefNoValue = ssiDtlForSsiRefNo.getInt(1);
			Log.log(5, "ApplicationDAO", "fetchBorrowerDtls", "SSI Details for CGBID :" + ssiDtlForSsiRefNoValue);

			if (ssiDtlForSsiRefNoValue == 1) {
				String error = ssiDtlForSsiRefNo.getString(30);
				ssiDtlForSsiRefNo.close();
				ssiDtlForSsiRefNo = null;
				Log.log(5, "ApplicationDAO", "fetchBorrowerDtls", "SSI Exception message:" + error);
				throw new DatabaseException(error);
			}

			ssiDetails.setBorrowerRefNo(ssiDtlForSsiRefNo.getInt(3));
			borrowerDetails.setPreviouslyCovered(ssiDtlForSsiRefNo.getString(4).trim());
			borrowerDetails.setAssistedByBank(ssiDtlForSsiRefNo.getString(5).trim());
			borrowerDetails.setNpa(ssiDtlForSsiRefNo.getString(7).trim());
			ssiDetails.setConstitution(ssiDtlForSsiRefNo.getString(8));
			ssiDetails.setSsiType(ssiDtlForSsiRefNo.getString(9).trim());
			ssiDetails.setSsiName(ssiDtlForSsiRefNo.getString(10));
			ssiDetails.setRegNo(ssiDtlForSsiRefNo.getString(11));
			ssiDetails.setSsiITPan(ssiDtlForSsiRefNo.getString(13));
			ssiDetails.setActivityType(ssiDtlForSsiRefNo.getString(14));
			ssiDetails.setEmployeeNos(ssiDtlForSsiRefNo.getInt(15));
			ssiDetails.setProjectedSalesTurnover(ssiDtlForSsiRefNo.getDouble(16));
			ssiDetails.setProjectedExports(ssiDtlForSsiRefNo.getDouble(17));
			ssiDetails.setAddress(ssiDtlForSsiRefNo.getString(18));
			ssiDetails.setCity(ssiDtlForSsiRefNo.getString(19));
			ssiDetails.setPincode(ssiDtlForSsiRefNo.getString(20).trim());
			ssiDetails.setDistrict(ssiDtlForSsiRefNo.getString(22));
			ssiDetails.setState(ssiDtlForSsiRefNo.getString(23));
			ssiDetails.setIndustryNature(ssiDtlForSsiRefNo.getString(24));
			ssiDetails.setIndustrySector(ssiDtlForSsiRefNo.getString(25));
			ssiDetails.setCgbid(ssiDtlForSsiRefNo.getString(27));
			borrowerDetails.setOsAmt(ssiDtlForSsiRefNo.getDouble(28));

			ssiDtlForSsiRefNo.close();
			ssiDtlForSsiRefNo = null;

			CallableStatement ssiDtlForSsiRef = connection
					.prepareCall("{?=call funcGetPromoterDtlforSSIRef(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			ssiDtlForSsiRef.setInt(2, ssiRefNo);
			ssiDtlForSsiRef.registerOutParameter(1, 4);
			ssiDtlForSsiRef.registerOutParameter(23, 12);
			ssiDtlForSsiRef.registerOutParameter(3, 4);
			ssiDtlForSsiRef.registerOutParameter(4, 12);
			ssiDtlForSsiRef.registerOutParameter(5, 12);
			ssiDtlForSsiRef.registerOutParameter(6, 12);
			ssiDtlForSsiRef.registerOutParameter(7, 12);
			ssiDtlForSsiRef.registerOutParameter(8, 12);
			ssiDtlForSsiRef.registerOutParameter(9, 12);
			ssiDtlForSsiRef.registerOutParameter(10, 91);
			ssiDtlForSsiRef.registerOutParameter(11, 12);
			ssiDtlForSsiRef.registerOutParameter(12, 12);
			ssiDtlForSsiRef.registerOutParameter(13, 12);
			ssiDtlForSsiRef.registerOutParameter(14, 91);
			ssiDtlForSsiRef.registerOutParameter(15, 12);
			ssiDtlForSsiRef.registerOutParameter(16, 12);
			ssiDtlForSsiRef.registerOutParameter(17, 91);
			ssiDtlForSsiRef.registerOutParameter(18, 12);
			ssiDtlForSsiRef.registerOutParameter(19, 12);
			ssiDtlForSsiRef.registerOutParameter(20, 91);
			ssiDtlForSsiRef.registerOutParameter(21, 12);
			ssiDtlForSsiRef.registerOutParameter(22, 12);

			ssiDtlForSsiRef.executeQuery();
			int ssiDtlForSsiRefValue = ssiDtlForSsiRef.getInt(1);
			Log.log(4, "ApplicationDAO", "getApplication", "Promoters Details :" + ssiDtlForSsiRefValue);

			if (ssiDtlForSsiRefValue == 1) {
				String error = ssiDtlForSsiRef.getString(23);
				ssiDtlForSsiRef.close();
				ssiDtlForSsiRef = null;
				connection.rollback();
				Log.log(2, "ApplicationDAO", "getApplication", "Promoter Exception message:" + error);
				throw new DatabaseException(error);
			}
			ssiDetails.setCpTitle(ssiDtlForSsiRef.getString(4));
			ssiDetails.setCpFirstName(ssiDtlForSsiRef.getString(5));
			ssiDetails.setCpMiddleName(ssiDtlForSsiRef.getString(6));
			ssiDetails.setCpLastName(ssiDtlForSsiRef.getString(7));
			ssiDetails.setCpITPAN(ssiDtlForSsiRef.getString(8));
			ssiDetails.setCpGender(ssiDtlForSsiRef.getString(9).trim());
			ssiDetails.setCpDOB(DateHelper.sqlToUtilDate(ssiDtlForSsiRef.getDate(10)));
			ssiDetails.setCpLegalID(ssiDtlForSsiRef.getString(11));
			ssiDetails.setCpLegalIdValue(ssiDtlForSsiRef.getString(12));
			ssiDetails.setFirstName(ssiDtlForSsiRef.getString(13));
			ssiDetails.setFirstDOB(DateHelper.sqlToUtilDate(ssiDtlForSsiRef.getDate(14)));
			ssiDetails.setFirstItpan(ssiDtlForSsiRef.getString(15));
			ssiDetails.setSecondName(ssiDtlForSsiRef.getString(16));
			ssiDetails.setSecondDOB(DateHelper.sqlToUtilDate(ssiDtlForSsiRef.getDate(17)));
			ssiDetails.setSecondItpan(ssiDtlForSsiRef.getString(18));
			ssiDetails.setThirdName(ssiDtlForSsiRef.getString(19));
			ssiDetails.setThirdDOB(DateHelper.sqlToUtilDate(ssiDtlForSsiRef.getDate(20)));
			ssiDetails.setThirdItpan(ssiDtlForSsiRef.getString(21));
			ssiDetails.setSocialCategory(ssiDtlForSsiRef.getString(22));

			ssiDtlForSsiRef.close();
			ssiDtlForSsiRef = null;

			borrowerDetails.setSsiDetails(ssiDetails);

			application.setBorrowerDetails(borrowerDetails);
			application.getBorrowerDetails().setSsiDetails(ssiDetails);

			Log.log(5, "ApplicationDAO", "fetchBorrowerDtls", "Fetching BorrowerDetails over...");

			connection.commit();
		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "updateCgbid", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
			}
			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(4, "ApplicationDAO", "updateCgbid", "Exited");

		return borrowerDetails;
	}

	public double getCorpusAmount() throws DatabaseException {
		Log.log(4, "ApplicationDAO", "getCorpusAmount", "Entered");

		Connection connection = DBConnection.getConnection(false);
		Application application = new Application();

		double corpusAmount = 0.0D;
		try {
			CallableStatement calCorpusAmount = connection.prepareCall("{?=call funcGetCorpusAmt(?,?)}");

			calCorpusAmount.registerOutParameter(1, 4);
			calCorpusAmount.registerOutParameter(2, 8);
			calCorpusAmount.registerOutParameter(3, 12);

			calCorpusAmount.execute();

			int functionReturnValue = calCorpusAmount.getInt(1);

			Log.log(5, "ApplicationDAO", "getCorpusAmount", "corpus Amount:" + functionReturnValue);

			if (functionReturnValue == 1) {
				String error = calCorpusAmount.getString(3);

				calCorpusAmount.close();
				calCorpusAmount = null;

				connection.rollback();

				Log.log(5, "ApplicationDAO", "getCorpusAmount", "corpus Amount exception:" + error);

				throw new DatabaseException(error);
			}

			corpusAmount = calCorpusAmount.getDouble(2);
			calCorpusAmount.close();
			calCorpusAmount = null;

			connection.commit();
		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "getCorpusAmount", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
			}
			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(4, "ApplicationDAO", "getCorpusAmount", "Exited");

		return corpusAmount;
	}

	public void checkCgpanPool(String appRefNo) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "checkCgpanPool", "Entered");
		Connection connection = DBConnection.getConnection();

		Application application = new Application();
		try {
			CallableStatement checkCgpanModify = connection.prepareCall("{?=call funcCheckCGPANModify(?,?)}");

			checkCgpanModify.registerOutParameter(1, 4);
			checkCgpanModify.registerOutParameter(3, 12);

			checkCgpanModify.setString(2, appRefNo);

			checkCgpanModify.execute();

			int functionReturnValue = checkCgpanModify.getInt(1);

			Log.log(5, "ApplicationDAO", "checkCgpanPool", "cgpan pool:" + functionReturnValue);

			if (functionReturnValue == 1) {
				String error = checkCgpanModify.getString(3);

				checkCgpanModify.close();
				checkCgpanModify = null;

				connection.rollback();

				Log.log(5, "ApplicationDAO", "checkCgpanPool", "checkCgpanPool exception:" + error);

				throw new DatabaseException(error);
			}

			checkCgpanModify.close();
			checkCgpanModify = null;
		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "checkCgpanPool", sqlException.getMessage());
			Log.logException(sqlException);

			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(4, "ApplicationDAO", "checkCgpanPool", "Exited");
	}

	public void updateReapprovalStatus(Application application, String userId) throws DatabaseException {
		Connection connection = DBConnection.getConnection(false);

		java.util.Date sysDate = new java.util.Date();
		try {
			CallableStatement updateReapproveStatus = connection
					.prepareCall("{?=call funcUpdateReApprovalStatus(?,?,?,?,?,?,?)}");

			updateReapproveStatus.registerOutParameter(1, 4);
			updateReapproveStatus.registerOutParameter(8, 12);

			updateReapproveStatus.setString(2, application.getCgpan());
			updateReapproveStatus.setString(3, userId);
			if (application.getReapprovedAmount() != 0.0D) {
				updateReapproveStatus.setDouble(4, application.getReapprovedAmount());
			} else {
				updateReapproveStatus.setNull(4, 8);
			}
			updateReapproveStatus.setDate(5, new java.sql.Date(sysDate.getTime()));
			updateReapproveStatus.setString(6, application.getStatus());
			if ((application.getReapprovalRemarks() != null) && (!application.getReapprovalRemarks().equals(""))) {
				updateReapproveStatus.setString(7, application.getReapprovalRemarks());
			} else {
				updateReapproveStatus.setString(7, null);
			}

			updateReapproveStatus.execute();

			int functionReturnValue = updateReapproveStatus.getInt(1);

			Log.log(5, "ApplicationDAO", "updateReapprovalStatus", "update reapprove status:" + functionReturnValue);

			if (functionReturnValue == 1) {
				String error = updateReapproveStatus.getString(8);

				updateReapproveStatus.close();
				updateReapproveStatus = null;

				connection.rollback();

				Log.log(5, "ApplicationDAO", "updateReapprovalStatus", "updateReapprovalStatus exception:" + error);

				throw new DatabaseException(error);
			}

			updateReapproveStatus.close();
			updateReapproveStatus = null;

			if (application.getCgpan().substring(11, 13).equals("TC")) {
				CPDAO cpDAO = new CPDAO();
				String appRefNumber = cpDAO.getAppRefNumber(application.getCgpan());
				application.setAppRefNo(appRefNumber);
				updateWcTenure(application, connection);
			}

			connection.commit();
		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "updateReapprovalStatus", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
			}
			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(4, "ApplicationDAO", "updateReapprovalStatus", "Exited");
	}

	public String generateRenewCgpan(String renewalCgpan) throws DatabaseException {
		Connection connection = DBConnection.getConnection(false);

		java.util.Date sysDate = new java.util.Date();

		String renewCgpan = "";
		try {
			CallableStatement cgpanRenew = connection.prepareCall("{?=call funcGenCGPANforWCRenewal(?,?,?)}");

			cgpanRenew.registerOutParameter(1, 4);
			cgpanRenew.registerOutParameter(4, 12);

			cgpanRenew.registerOutParameter(3, 12);

			cgpanRenew.setString(2, renewalCgpan);

			cgpanRenew.execute();

			int functionReturnValue = cgpanRenew.getInt(1);

			Log.log(5, "ApplicationDAO", "generateRenewCgpan", "ugenerateRenewCgpan:" + functionReturnValue);

			if (functionReturnValue == 1) {
				String error = cgpanRenew.getString(4);

				cgpanRenew.close();
				cgpanRenew = null;

				connection.rollback();

				Log.log(5, "ApplicationDAO", "generateRenewCgpan", "generateRenewCgpan" + error);

				throw new DatabaseException(error);
			}

			renewCgpan = cgpanRenew.getString(3);

			cgpanRenew.close();
			cgpanRenew = null;

			Log.log(5, "ApplicationDAO", "generateRenewCgpan", "generated cgpan" + renewCgpan);

			connection.commit();
		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "generateRenewCgpan", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
			}
			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(4, "ApplicationDAO", "generateRenewCgpan", "Exited");

		return renewCgpan;
	}

	public String checkRenewCgpan(String cgpan) throws DatabaseException {
		Connection connection = DBConnection.getConnection(false);

		String stringVal = "";
		try {
			CallableStatement checkCgpan = connection.prepareCall("{?=call funcCheckRenewal(?,?,?)}");

			checkCgpan.registerOutParameter(1, 4);
			checkCgpan.registerOutParameter(4, 12);
			checkCgpan.registerOutParameter(3, 12);

			checkCgpan.setString(2, cgpan);

			checkCgpan.execute();

			int functionReturnValue = checkCgpan.getInt(1);

			Log.log(5, "ApplicationDAO", "checkCgpan", "checkrenewCgpan:" + functionReturnValue);

			if (functionReturnValue == 1) {
				String error = checkCgpan.getString(4);

				checkCgpan.close();
				checkCgpan = null;

				connection.rollback();

				Log.log(5, "ApplicationDAO", "checkRenewCgpan", "checkCgpan" + error);

				throw new DatabaseException(error);
			}

			stringVal = checkCgpan.getString(3);
			checkCgpan.close();
			checkCgpan = null;

			connection.commit();
		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "checkRenewCgpan", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
			}
			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(4, "ApplicationDAO", "checkRenewCgpan", "Exited");

		return stringVal;
	}

	public ArrayList getSsiRefNosForMcgf(String memberId) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "getSsiRefNosForMcgf", "Entered");

		Connection connection = DBConnection.getConnection(false);

		ArrayList ssiRefNosList = new ArrayList();
		ArrayList bidList = new ArrayList();
		ArrayList ssiRefNumberList = new ArrayList();
		try {
			CallableStatement ssiRefNoList = connection
					.prepareCall("{call packGetSsiRefNoForMcgf.funcGetSsiRefNoForMcgf(?,?,?,?,?)}");

			ssiRefNoList.registerOutParameter(1, 4);
			ssiRefNoList.registerOutParameter(3, -10);
			ssiRefNoList.registerOutParameter(4, -10);
			ssiRefNoList.registerOutParameter(5, 12);
			ssiRefNoList.setString(2, memberId);

			ssiRefNoList.execute();

			int functionReturnValue = ssiRefNoList.getInt(1);
			Log.log(5, "ApplicationDAO", "getSsiRefNosForMcgf", "Get Ssi ref nos Result :" + functionReturnValue);

			if (functionReturnValue == 1) {
				String error = ssiRefNoList.getString(5);

				ssiRefNoList.close();
				ssiRefNoList = null;

				connection.rollback();

				throw new DatabaseException(error);
			}
			ResultSet ssiRefNoResults = (ResultSet) ssiRefNoList.getObject(3);
			ResultSet ssiRefNoTempResults = (ResultSet) ssiRefNoList.getObject(4);

			while (ssiRefNoResults.next()) {
				String ssiRefNumber = ssiRefNoResults.getString(1);
				String ssiName = ssiRefNoResults.getString(2);
				String bid = ssiRefNoResults.getString(3);

				String ssiDtl = "";

				if ((bid != null) && (!bid.equals(""))) {
					Log.log(5, "ApplicationDAO", "getSsiRefNosForMcgf", "bid:" + bid);

					ssiDtl = bid + "" + "(" + ssiName + ")";
					Log.log(5, "ApplicationDAO", "getSsiRefNosForMcgf", "ssi dtl:" + ssiDtl);
				} else {
					ssiDtl = ssiRefNumber + "" + "(" + ssiName + ")";
				}
				ssiRefNosList.add(ssiDtl);
			}

			while (ssiRefNoTempResults.next()) {
				String ssiRefNumber = ssiRefNoTempResults.getString(1);
				String ssiName = ssiRefNoTempResults.getString(2);
				String bid = ssiRefNoTempResults.getString(3);

				String ssiDtl = "";

				if ((bid != null) && (!bid.equals(""))) {
					Log.log(5, "ApplicationDAO", "getSsiRefNosForMcgf", "bid:" + bid);

					ssiDtl = bid + "" + "(" + ssiName + ")";
					Log.log(5, "ApplicationDAO", "getSsiRefNosForMcgf", "ssi dtl:" + ssiDtl);
				} else {
					ssiDtl = ssiRefNumber + "" + "(" + ssiName + ")";
				}
				ssiRefNosList.add(ssiDtl);
			}

			ssiRefNoResults.close();
			ssiRefNoResults = null;
			ssiRefNoList.close();
			ssiRefNoList = null;

			connection.commit();
		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "getSsiRefNosForMcgf", sqlException.getMessage());
			Log.logException(sqlException);
			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(4, "ApplicationDAO", "getSsiRefNosForMcgf", "Exited");

		return ssiRefNosList;
	}

	public ArrayList getAllCgpans() throws DatabaseException {
		Log.log(4, "ApplicationProcessor", "getAllCgpans", "entered");
		Connection connection = DBConnection.getConnection(false);

		ArrayList cgpanList = new ArrayList();
		try {
			CallableStatement allCgpans = connection.prepareCall("{?=call packGetAllCgpans.funcGetAllCgpans(?,?)}");

			allCgpans.registerOutParameter(1, 4);
			allCgpans.registerOutParameter(2, -10);
			allCgpans.registerOutParameter(3, 12);

			allCgpans.execute();

			int functionReturnValue = allCgpans.getInt(1);

			if (functionReturnValue == 1) {
				String error = allCgpans.getString(3);

				allCgpans.close();
				allCgpans = null;

				connection.rollback();

				throw new DatabaseException(error);
			}

			ResultSet allCgpansResult = (ResultSet) allCgpans.getObject(2);

			while (allCgpansResult.next()) {
				String cgpan = allCgpansResult.getString(1);

				cgpanList.add(cgpan);
			}
			allCgpansResult.close();
			allCgpansResult = null;
			allCgpans.close();
			allCgpans = null;

			connection.commit();
		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "getAllCgpans", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(4, "ApplicationDAO", "getAllCgpans", ignore.getMessage());
			}
			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(4, "ApplicationProcessor", "getAllCgpans", "Exited");

		return cgpanList;
	}

	public ArrayList getAllBids() throws DatabaseException {
		Connection connection = DBConnection.getConnection(false);

		ArrayList bidList = new ArrayList();
		try {
			CallableStatement allBids = connection.prepareCall("{?=call packGetAllBids.funcGetAllBids(?,?)}");

			allBids.registerOutParameter(1, 4);
			allBids.registerOutParameter(2, -10);
			allBids.registerOutParameter(3, 12);

			allBids.execute();

			int functionReturnValue = allBids.getInt(1);

			if (functionReturnValue == 1) {
				String error = allBids.getString(3);
				allBids.close();
				allBids = null;

				connection.rollback();

				throw new DatabaseException(error);
			}

			ResultSet allBidsResult = (ResultSet) allBids.getObject(2);

			while (allBidsResult.next()) {
				String bid = allBidsResult.getString(1);

				bidList.add(bid);
			}
			allBidsResult.close();
			allBidsResult = null;
			allBids.close();
			allBids = null;

			connection.commit();
		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "getAllBids", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(4, "ApplicationDAO", "getAllBids", ignore.getMessage());
			}

			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		return bidList;
	}

	public void updatePendingRejectedStatus(Application application, String userId) throws DatabaseException {
		Connection connection = DBConnection.getConnection(false);
		try {
			CallableStatement pendingRejectUpdate = connection
					.prepareCall("{?=call funcUpdatePendingRejectStatus(?,?,?,?,?)}");

			pendingRejectUpdate.registerOutParameter(1, 4);
			pendingRejectUpdate.registerOutParameter(6, 12);

			pendingRejectUpdate.setString(2, application.getAppRefNo());

			pendingRejectUpdate.setString(3, application.getStatus());

			if ((application.getRemarks() != null) && (!application.getRemarks().equals(""))) {
				pendingRejectUpdate.setString(4, application.getRemarks());
			} else {
				pendingRejectUpdate.setString(4, null);
			}

			pendingRejectUpdate.setString(5, userId);

			pendingRejectUpdate.execute();

			int functionReturnValue = pendingRejectUpdate.getInt(1);

			if (functionReturnValue == 1) {
				String error = pendingRejectUpdate.getString(6);

				pendingRejectUpdate.close();
				pendingRejectUpdate = null;

				connection.rollback();

				throw new DatabaseException(error);
			}
			pendingRejectUpdate.close();
			pendingRejectUpdate = null;

			connection.commit();
		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "updatePendingRejectedStatus", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(4, "ApplicationDAO", "updatePendingRejectedStatus", ignore.getMessage());
			}

			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}
	}

	public void updateEnhanceAppStatus(Application application, String userId) throws DatabaseException {
		Connection connection = DBConnection.getConnection(false);

		java.util.Date utilDate = new java.util.Date();
		try {
			connection.commit();

			CallableStatement updateEnhanceStatus = connection
					.prepareCall("{?=call funcUpdateEnhanceStatus(?,?,?,?,?,?,?)}");

			updateEnhanceStatus.registerOutParameter(1, 4);
			updateEnhanceStatus.registerOutParameter(8, 12);

			updateEnhanceStatus.setString(2, application.getAppRefNo());
			Log.log(5, "ApplicationDAO", "updateEnhanceAppStatus",
					"Application app ref no :" + application.getAppRefNo());

			updateEnhanceStatus.setString(3, userId);
			if (application.getApprovedAmount() != 0.0D) {
				updateEnhanceStatus.setDouble(4, application.getApprovedAmount());
			} else {
				updateEnhanceStatus.setNull(4, 8);
			}
			Log.log(5, "ApplicationDAO", "updateEnhanceAppStatus",
					"Application Amount :" + application.getApprovedAmount());
			updateEnhanceStatus.setDate(5, new java.sql.Date(utilDate.getTime()));
			Log.log(5, "ApplicationDAO", "updateEnhanceAppStatus", "Application Status :" + application.getStatus());
			updateEnhanceStatus.setString(6, application.getStatus());
			Log.log(5, "ApplicationDAO", "updateEnhanceAppStatus", "Application Renmarks:" + application.getRemarks());
			if ((application.getRemarks() != null) && (!application.getRemarks().equals(""))) {
				updateEnhanceStatus.setString(7, application.getRemarks());
			} else {
				updateEnhanceStatus.setString(7, null);
			}

			try {
				updateEnhanceStatus.execute();
			} catch (Throwable e) {
				e.printStackTrace();
			}

			int functionReturnValue = updateEnhanceStatus.getInt(1);
			Log.log(5, "ApplicationDAO", "updateEnhanceAppStatus",
					"Get update App Status Result :" + functionReturnValue);

			if (functionReturnValue == 1) {
				String error = updateEnhanceStatus.getString(8);

				updateEnhanceStatus.close();
				updateEnhanceStatus = null;

				connection.rollback();

				throw new DatabaseException(error);
			}

			updateEnhanceStatus.close();
			updateEnhanceStatus = null;

			connection.commit();
			connection.close();
			connection = null;
		} catch (Exception sqlException) {
			Log.log(4, "ApplicationDAO", "updateEnhanceAppStatus", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (Exception ignore) {
				Log.log(4, "ApplicationDAO", "updateEnhanceAppStatus", ignore.getMessage());
			}

			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);

		}
		Log.log(4, "ApplicationDAO", "updateApplicationStatus", "Exited");
	}

	public void updateRejectStatus(Application application, String userId) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "updateRejectStatus", "Entered");
		Connection connection = DBConnection.getConnection(false);
		try {
			connection.commit();

			CallableStatement updateRejectStatus = connection.prepareCall("{?=call funcUpdateRejectStatus(?,?,?,?,?)}");

			updateRejectStatus.registerOutParameter(1, 4);
			updateRejectStatus.registerOutParameter(6, 12);

			Log.log(5, "ApplicationDAO", "updateRejectStatus", "Application app ref no :" + application.getAppRefNo());
			updateRejectStatus.setString(2, application.getAppRefNo());

			Log.log(5, "ApplicationDAO", "updateRejectStatus", "Application Status :" + application.getStatus());
			updateRejectStatus.setString(3, application.getStatus());
			Log.log(5, "ApplicationDAO", "updateRejectStatus", "Application Renmarks:" + application.getRemarks());
			if ((application.getRemarks() != null) && (!application.getRemarks().equals(""))) {
				updateRejectStatus.setString(4, application.getRemarks());
			} else {
				updateRejectStatus.setString(4, null);
			}
			updateRejectStatus.setString(5, userId);
			try {
				updateRejectStatus.execute();
			} catch (Throwable e) {
				e.printStackTrace();
			}

			int functionReturnValue = updateRejectStatus.getInt(1);
			Log.log(5, "ApplicationDAO", "updateEnhanceAppStatus",
					"Get update App Status Result :" + functionReturnValue);

			if (functionReturnValue == 1) {
				String error = updateRejectStatus.getString(6);

				updateRejectStatus.close();
				updateRejectStatus = null;

				connection.rollback();

				throw new DatabaseException(error);
			}

			updateRejectStatus.close();
			updateRejectStatus = null;

			connection.commit();
			connection.close();
			connection = null;
		} catch (Exception sqlException) {
			Log.log(4, "ApplicationDAO", "updateEnhanceAppStatus", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (Exception ignore) {
				Log.log(4, "ApplicationDAO", "updateEnhanceAppStatus", ignore.getMessage());
			}

			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);

		}
		Log.log(4, "ApplicationDAO", "updateApplicationStatus", "Exited");
	}

	public ArrayList getDtlForBorMem(String borrowerName, String bankId, String zoneId, String branchId)
			throws DatabaseException, NoApplicationFoundException {
		Log.log(4, "ApplicationDAO", "getDtlForBorMem", "Entered");
		Log.log(4, "ApplicationDAO", "getDtlForBorMem", "borrower name :" + borrowerName);
		Connection connection = DBConnection.getConnection(false);
		ArrayList appRefNos = new ArrayList();
		ArrayList cgpanList = new ArrayList();
		ArrayList cgpanAppRefNo = new ArrayList();
		try {
			CallableStatement dtlForBorMem = connection
					.prepareCall("{?=call packGetAppRefForModification.funcGetDtlforBorMem(?,?,?,?,?,?,?,?)}");

			dtlForBorMem.registerOutParameter(1, 4);
			dtlForBorMem.registerOutParameter(6, -10);
			dtlForBorMem.registerOutParameter(7, -10);
			dtlForBorMem.registerOutParameter(8, -10);
			dtlForBorMem.registerOutParameter(9, 12);

			dtlForBorMem.setString(2, borrowerName);
			dtlForBorMem.setString(3, bankId);
			dtlForBorMem.setString(4, zoneId);
			dtlForBorMem.setString(5, branchId);

			dtlForBorMem.execute();

			int functionReturnValue = dtlForBorMem.getInt(1);
			Log.log(4, "ApplicationDAO", "getDtlForBorMem", "return value :" + functionReturnValue);

			if (functionReturnValue == 1) {
				String error = dtlForBorMem.getString(9);

				dtlForBorMem.close();
				dtlForBorMem = null;

				connection.rollback();

				throw new DatabaseException(error);
			}
			ResultSet appRefNosResult = (ResultSet) dtlForBorMem.getObject(6);
			Log.log(4, "ApplicationDAO", "getDtlForBorMem", "appRefNo size:" + appRefNosResult.getFetchSize());
			ResultSet appRefNosTempResult = (ResultSet) dtlForBorMem.getObject(7);
			ResultSet appRefCgpanTempResult = (ResultSet) dtlForBorMem.getObject(8);

			while (appRefNosResult.next()) {
				String appRefNo = appRefNosResult.getString(1);
				Log.log(4, "ApplicationDAO", "getDtlForBorMem", "appRefNo:" + appRefNo);
				String cgpan = appRefNosResult.getString(2);

				Log.log(4, "ApplicationDAO", "getDtlForBorMem", "cgpan:" + cgpan);

				if ((cgpan == null) || (cgpan.equals(""))) {
					cgpan = "";
					if (!appRefNos.contains(appRefNo)) {
						appRefNos.add(appRefNo);
					}
				} else if ((cgpan != null) && (!cgpan.equals(""))) {
					try {
						Application application = getPartApplication(null, cgpan, "");
						if ((!application.getStatus().equals("EX")) && (!application.getStatus().equals("RE"))) {
							if (!cgpanList.contains(cgpan)) {
								cgpanList.add(cgpan);
							}
							if (!appRefNos.contains(appRefNo)) {
								appRefNos.add(appRefNo);
							}

						}

					} catch (DatabaseException databaseException) {
						if (!databaseException.getMessage().equals("Application does not exist.")) {
							if (!cgpanList.contains(cgpan)) {
								cgpanList.add(cgpan);
							}
							if (!appRefNos.contains(appRefNo)) {
								appRefNos.add(appRefNo);
							}
						}
					}
				}
			}

			while (appRefNosTempResult.next()) {
				String appRefNo = appRefNosTempResult.getString(1);
				String cgpan = appRefNosTempResult.getString(2);

				if ((cgpan == null) || (cgpan.equals(""))) {
					cgpan = "";
					if (!appRefNos.contains(appRefNo)) {
						appRefNos.add(appRefNo);
					}
				} else if ((cgpan != null) && (!cgpan.equals(""))) {
					try {
						Application application = getPartApplication(null, cgpan, "");

						if ((!application.getStatus().equals("EX")) && (!application.getStatus().equals("RE"))) {
							if (!cgpanList.contains(cgpan)) {
								cgpanList.add(cgpan);
							}
							if (!appRefNos.contains(appRefNo)) {
								appRefNos.add(appRefNo);
							}

						}

					} catch (DatabaseException databaseException) {
						if (!databaseException.getMessage().equals("Application does not exist.")) {
							if (!cgpanList.contains(cgpan)) {
								cgpanList.add(cgpan);
							}
							if (!appRefNos.contains(appRefNo)) {
								appRefNos.add(appRefNo);
							}
						}
					}
				}

			}

			while (appRefCgpanTempResult.next()) {
				String appRefNo = appRefCgpanTempResult.getString(1);
				String cgpan = appRefCgpanTempResult.getString(2);

				if ((cgpan == null) || (cgpan.equals(""))) {
					cgpan = "";
					if (!appRefNos.contains(appRefNo)) {
						appRefNos.add(appRefNo);
					}
				} else if ((cgpan != null) && (!cgpan.equals(""))) {
					try {
						Application application = getPartApplication(null, cgpan, "");
						if ((!application.getStatus().equals("EX")) && (!application.getStatus().equals("RE"))) {
							if (!cgpanList.contains(cgpan)) {
								cgpanList.add(cgpan);
							}
							if (!appRefNos.contains(appRefNo)) {
								appRefNos.add(appRefNo);
							}

						}

					} catch (DatabaseException databaseException) {
						if (!databaseException.getMessage().equals("Application does not exist.")) {
							if (!cgpanList.contains(cgpan)) {
								cgpanList.add(cgpan);
							}
							if (!appRefNos.contains(appRefNo)) {
								appRefNos.add(appRefNo);
							}
						}
					}

				}

			}

			appRefNosResult.close();
			appRefNosResult = null;
			appRefNosTempResult.close();
			appRefNosTempResult = null;
			appRefCgpanTempResult.close();
			appRefCgpanTempResult = null;

			dtlForBorMem.close();
			dtlForBorMem = null;

			connection.commit();
		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "getDtlForBorMem", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(4, "ApplicationDAO", "getDtlForBorMem", ignore.getMessage());
			}

			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}
		cgpanAppRefNo.add(appRefNos);
		cgpanAppRefNo.add(cgpanList);

		Log.log(4, "ApplicationDAO", "getDtlForBorMem", "Exited");

		return cgpanAppRefNo;
	}

	public ArrayList getDtlForBIDMem(String borrowerId, String bankId, String zoneId, String branchId)
			throws DatabaseException, NoApplicationFoundException {
		Connection connection = DBConnection.getConnection(false);
		ArrayList appRefNos = new ArrayList();
		ArrayList cgpanList = new ArrayList();
		ArrayList cgpanAppRefNo = new ArrayList();
		try {
			CallableStatement dtlForBIDMem = connection
					.prepareCall("{?=call packGetAppRefForModification.funcGetDtlforBIDMem(?,?,?,?,?,?,?,?)}");

			dtlForBIDMem.registerOutParameter(1, 4);
			dtlForBIDMem.registerOutParameter(6, -10);
			dtlForBIDMem.registerOutParameter(7, -10);
			dtlForBIDMem.registerOutParameter(8, -10);
			dtlForBIDMem.registerOutParameter(9, 12);

			dtlForBIDMem.setString(2, borrowerId);
			dtlForBIDMem.setString(3, bankId);
			dtlForBIDMem.setString(4, zoneId);
			dtlForBIDMem.setString(5, branchId);

			dtlForBIDMem.execute();

			int functionReturnValue = dtlForBIDMem.getInt(1);

			if (functionReturnValue == 1) {
				String error = dtlForBIDMem.getString(9);

				dtlForBIDMem.close();
				dtlForBIDMem = null;

				connection.rollback();

				throw new DatabaseException(error);
			}
			ResultSet appRefNosResult = (ResultSet) dtlForBIDMem.getObject(6);
			ResultSet appRefNosTempResult = (ResultSet) dtlForBIDMem.getObject(7);
			ResultSet appRefCgpanTempResult = (ResultSet) dtlForBIDMem.getObject(8);

			while (appRefNosResult.next()) {
				String appRefNo = appRefNosResult.getString(1);

				String cgpan = appRefNosResult.getString(2);
				if ((cgpan == null) || (cgpan.equals(""))) {
					cgpan = "";
					appRefNos.add(appRefNo);
				}
				if ((cgpan != null) && (!cgpan.equals(""))) {
					try {
						Application application = getPartApplication(null, cgpan, "");

						if (!application.getStatus().equals("EX")) {
							cgpanList.add(cgpan);
							appRefNos.add(appRefNo);
						}

					} catch (DatabaseException databaseException) {
						if (!databaseException.getMessage().equals("Application does not exist.")) {
							cgpanList.add(cgpan);
							appRefNos.add(appRefNo);
						}

					}

				}

			}

			while (appRefCgpanTempResult.next()) {
				String appRefNo = appRefCgpanTempResult.getString(1);

				String cgpan = appRefCgpanTempResult.getString(2);
				if ((cgpan == null) || (cgpan.equals(""))) {
					cgpan = "";
					if (!appRefNos.contains(appRefNo)) {
						appRefNos.add(appRefNo);
					}
				}

				if ((cgpan != null) && (!cgpan.equals(""))) {
					try {
						Application application = getPartApplication(null, cgpan, "");

						if (!application.getStatus().equals("EX")) {
							if (!cgpanList.contains(cgpan)) {
								cgpanList.add(cgpan);
								appRefNos.add(appRefNo);
							}

						}

					} catch (DatabaseException databaseException) {
						if (!databaseException.getMessage().equals("Application does not exist.")) {
							cgpanList.add(cgpan);
							appRefNos.add(appRefNo);
						}
					}

				}

			}

			appRefNosResult.close();
			appRefNosResult = null;
			appRefCgpanTempResult.close();
			appRefCgpanTempResult = null;

			dtlForBIDMem.close();
			dtlForBIDMem = null;

			connection.commit();
		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "getDtlForBIDMem", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
			}
			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		cgpanAppRefNo.add(appRefNos);
		cgpanAppRefNo.add(cgpanList);

		return cgpanAppRefNo;
	}

	public Application getAppForMliRef(String bankId, String zoneId, String branchId, String appRefNo)
			throws DatabaseException {
		Connection connection = DBConnection.getConnection();

		Application application = new Application();
		ProjectOutlayDetails projectOutlayDetails = new ProjectOutlayDetails();
		User user = new User();
		try {
			CallableStatement appForMliRef = connection.prepareCall(
					"{?=call funcGetAppDetailforMLIAppRef(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			appForMliRef.setString(2, appRefNo);
			appForMliRef.setString(3, bankId);
			appForMliRef.setString(4, zoneId);
			appForMliRef.setString(5, branchId);

			appForMliRef.registerOutParameter(1, 4);
			appForMliRef.registerOutParameter(28, 12);

			appForMliRef.registerOutParameter(6, 12);
			appForMliRef.registerOutParameter(7, 4);
			appForMliRef.registerOutParameter(8, 12);
			appForMliRef.registerOutParameter(9, 12);
			appForMliRef.registerOutParameter(10, 12);
			appForMliRef.registerOutParameter(11, 12);
			appForMliRef.registerOutParameter(12, 12);
			appForMliRef.registerOutParameter(13, 12);
			appForMliRef.registerOutParameter(14, 12);
			appForMliRef.registerOutParameter(15, 12);
			appForMliRef.registerOutParameter(16, 12);
			appForMliRef.registerOutParameter(17, 12);
			appForMliRef.registerOutParameter(18, 12);
			appForMliRef.registerOutParameter(19, 12);
			appForMliRef.registerOutParameter(20, 12);
			appForMliRef.registerOutParameter(21, 12);
			appForMliRef.registerOutParameter(22, 12);
			appForMliRef.registerOutParameter(23, 8);
			appForMliRef.registerOutParameter(24, 12);
			appForMliRef.registerOutParameter(25, 12);
			appForMliRef.registerOutParameter(26, 91);
			appForMliRef.registerOutParameter(27, 12);

			appForMliRef.execute();

			int appForMliRefValue = appForMliRef.getInt(1);

			if (appForMliRefValue == 1) {
				String error = appForMliRef.getString(28);

				appForMliRef.close();
				appForMliRef = null;

				connection.rollback();

				throw new DatabaseException(error);
			}
			application.setAppRefNo(appForMliRef.getString(6));

			application.setCgpan(appForMliRef.getString(7));

			application.setScheme(appForMliRef.getString(9));

			application.setBankId(appForMliRef.getString(10).trim());

			application.setZoneId(appForMliRef.getString(11).trim());

			application.setBranchId(appForMliRef.getString(12).trim());

			application.setMliBranchName(appForMliRef.getString(13));

			application.setMliBranchCode(appForMliRef.getString(14));

			application.setMliRefNo(appForMliRef.getString(15));

			application.setCompositeLoan(appForMliRef.getString(16).trim());

			user.setUserId(appForMliRef.getString(17));

			application.setLoanType(appForMliRef.getString(18).trim());

			projectOutlayDetails.setCollateralSecurityTaken(appForMliRef.getString(19).trim());

			projectOutlayDetails.setThirdPartyGuaranteeTaken(appForMliRef.getString(20).trim());

			projectOutlayDetails.setSubsidyName(appForMliRef.getString(21));

			application.setRehabilitation(appForMliRef.getString(22).trim());

			projectOutlayDetails.setProjectOutlay(appForMliRef.getDouble(23));

			application.setRemarks(appForMliRef.getString(25));

			application.setStatus(appForMliRef.getString(27).trim());

			application.setProjectOutlayDetails(projectOutlayDetails);

			appForMliRef.close();
			appForMliRef = null;

			connection.commit();
		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "getAppForMliRef", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(4, "ApplicationDAO", "getAppForMliRef", ignore.getMessage());
			}

			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		return application;
	}

	public Application getAppForAppRef(String mliId, String appRefNo) throws DatabaseException {
		Connection connection = DBConnection.getConnection(false);

		Application application = new Application();
		ProjectOutlayDetails projectOutlayDetails = new ProjectOutlayDetails();
		try {
			CallableStatement appForAppRef = connection.prepareCall(
					"{?=call funcGetApplicationDtlforAppRef(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");// used
																																		// for
																																		// internetuser
			// CallableStatement appForAppRef =
			// connection.prepareCall("{?=call
			// funcGetApplicationDtlforAppRef(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");//used
			// for maindb of intranetuser
			appForAppRef.registerOutParameter(1, Types.INTEGER);
			System.out.println("appRefNo" + appRefNo);
			appForAppRef.setString(2, appRefNo);
			appForAppRef.setString(3, mliId);
			appForAppRef.registerOutParameter(4, Types.VARCHAR); // App Ref No
			appForAppRef.registerOutParameter(5, Types.INTEGER); // SSI Ref No
			appForAppRef.registerOutParameter(6, Types.VARCHAR); // CGPAN
			appForAppRef.registerOutParameter(7, Types.VARCHAR); // Scheme Name
			appForAppRef.registerOutParameter(8, Types.VARCHAR); // Bank ID
			appForAppRef.registerOutParameter(9, Types.VARCHAR); // Zone Id
			appForAppRef.registerOutParameter(10, Types.VARCHAR); // Branch Id
			appForAppRef.registerOutParameter(11, Types.VARCHAR); // MLI Branch
																	// name
			appForAppRef.registerOutParameter(12, Types.VARCHAR); // Branch Code
			appForAppRef.registerOutParameter(13, Types.VARCHAR); // App Bank
																	// Ref No
			appForAppRef.registerOutParameter(14, Types.VARCHAR); // Composite
																	// Loan
			appForAppRef.registerOutParameter(15, Types.VARCHAR); // User Id
			appForAppRef.registerOutParameter(16, Types.VARCHAR); // Loan Type
			appForAppRef.registerOutParameter(17, Types.VARCHAR); // Collateral
																	// Security
			appForAppRef.registerOutParameter(18, Types.VARCHAR); // Third Party
																	// Gurantee
			appForAppRef.registerOutParameter(19, Types.VARCHAR); // Subsidy
																	// Scheme
																	// Name
			appForAppRef.registerOutParameter(20, Types.VARCHAR); // Rehabilitation
			appForAppRef.registerOutParameter(21, Types.DOUBLE); // Project
																	// Outlay
			appForAppRef.registerOutParameter(22, Types.VARCHAR); // App CGPAN
																	// Ref No
			appForAppRef.registerOutParameter(23, Types.VARCHAR); // Remarks
			appForAppRef.registerOutParameter(24, Types.DATE); // Submitted Date
			appForAppRef.registerOutParameter(25, Types.VARCHAR); // Status
			appForAppRef.registerOutParameter(26, Types.VARCHAR); // Sub scheme
																	// name
			appForAppRef.registerOutParameter(27, Types.DOUBLE); // Approved
																	// Amount
			appForAppRef.registerOutParameter(28, Types.DOUBLE); // ReApproved
																	// Amount
			appForAppRef.registerOutParameter(29, Types.VARCHAR); // ReApproved
																	// Amount
			// added by sukumar@path for displaying Handicraft details
			appForAppRef.registerOutParameter(30, Types.VARCHAR);// Handicraft
																	// Flag
			appForAppRef.registerOutParameter(31, Types.VARCHAR);// DC
																	// Handicraft
																	// Flag
			appForAppRef.registerOutParameter(32, Types.VARCHAR);
			appForAppRef.registerOutParameter(33, Types.DATE);// DC Handicraft
																// icardDate
			// Log.log(Log.INFO,"ApplicationDAO","getAppForAppRef","Before Executing");
			appForAppRef.registerOutParameter(34, Types.VARCHAR);// handloom
			appForAppRef.registerOutParameter(35, Types.VARCHAR);// weaver
			appForAppRef.registerOutParameter(36, Types.VARCHAR);// hanloomcheck

			// appForAppRef.registerOutParameter(37,Types.VARCHAR);//mseType
			// appForAppRef.registerOutParameter(38,Types.DATE);//sanction date
			// appForAppRef.registerOutParameter(39,Types.DATE);
			// appForAppRef.registerOutParameter(40,Types.VARCHAR);//errorcode
			appForAppRef.registerOutParameter(37, Types.VARCHAR);// errorcode
			appForAppRef.execute();

			int appForAppRefValue = appForAppRef.getInt(1);

			if (appForAppRefValue == Constants.FUNCTION_FAILURE) {

				// String error = appForAppRef.getString(40);
				String error = appForAppRef.getString(37);
				appForAppRef.close();
				appForAppRef = null;

				connection.rollback();

				throw new DatabaseException(error);
			}
			application.setAppRefNo(appForAppRef.getString(4));
			application.setCgpan(appForAppRef.getString(6));
			application.setScheme(appForAppRef.getString(7));
			String mliID = appForAppRef.getString(8) + appForAppRef.getString(9) + appForAppRef.getString(10);
			application.setMliID(mliID);
			application.setMliBranchName(appForAppRef.getString(11));
			application.setMliBranchCode(appForAppRef.getString(12));
			application.setMliRefNo(appForAppRef.getString(13));
			application.setCompositeLoan(appForAppRef.getString(14).trim());

			application.setLoanType(appForAppRef.getString(16).trim());
			projectOutlayDetails.setCollateralSecurityTaken(appForAppRef.getString(17).trim());
			projectOutlayDetails.setThirdPartyGuaranteeTaken(appForAppRef.getString(18).trim());
			projectOutlayDetails.setSubsidyName(appForAppRef.getString(19));
			application.setRehabilitation(appForAppRef.getString(20).trim());
			projectOutlayDetails.setProjectOutlay(appForAppRef.getDouble(21));
			application.setCgpanReference(appForAppRef.getString(22));
			application.setRemarks(appForAppRef.getString(23));
			application.setSubmittedDate(appForAppRef.getDate(24));
			System.out.println("appForAppRef.getString(25) " + appForAppRef.getString(25));
			application.setStatus(appForAppRef.getString(25));
			application.setSubSchemeName(appForAppRef.getString(26));
			application.setApprovedAmount(appForAppRef.getDouble(27));
			application.setReapprovedAmount(appForAppRef.getDouble(28));
			application.setReapprovalRemarks(appForAppRef.getString(29));
			application.setHandiCrafts(appForAppRef.getString(30));
			application.setHandiCraftsStatus(appForAppRef.getString(30));
			application.setDcHandicraftsStatus(appForAppRef.getString(31));
			application.setDcHandicrafts(appForAppRef.getString(31));
			application.setIcardNo(appForAppRef.getString(32));
			application.setIcardIssueDate(appForAppRef.getDate(33));
			application.setProjectOutlayDetails(projectOutlayDetails);
			application.setDcHandlooms(appForAppRef.getString(34));
			application.setDcHandloomsStatus(appForAppRef.getString(34));
			application.setWeaverCreditScheme(appForAppRef.getString(35));
			application.setHandloomchk(appForAppRef.getString(36));
			appForAppRef.close();
			appForAppRef = null;

			connection.commit();
		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "getAppForAppRef", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(4, "ApplicationDAO", "getAppForAppRef", ignore.getMessage());
			}

			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}
		projectOutlayDetails = null;

		return application;
	}

	public Application getAppForCgpan(String mliID, String cgpan) throws DatabaseException {
		Connection connection = DBConnection.getConnection(false);
		// System.out.println("in getAppForCgpan DAO");
		Application application = new Application();
		String appRefNo = "";
		try {
			Log.log(5, "ApplicationDAO", "getAppForCgpan", "CGPAN After entering getAppforCgpan in DAO :" + cgpan);

			CallableStatement appForCgpan = connection.prepareCall("{?=call funcGetAppRefNoforCGPAN(?,?,?,?)}");
			System.out.println("mliID" + mliID);
			System.out.println("cgpan" + cgpan);
			appForCgpan.setString(3, mliID);
			appForCgpan.setString(4, cgpan);

			appForCgpan.registerOutParameter(1, 4);
			appForCgpan.registerOutParameter(5, 12);

			appForCgpan.registerOutParameter(2, 12);

			Log.log(4, "ApplicationDAO", "getAppForCgpan", "Before Executing");

			appForCgpan.execute();

			Log.log(4, "ApplicationDAO", "getAppForCgpan", "After Executing");

			int appForCgpanValue = appForCgpan.getInt(1);

			Log.log(5, "ApplicationDAO", "getAppForCgpan", "Application Details :" + appForCgpanValue);

			if (appForCgpanValue == 1) {
				String error = appForCgpan.getString(5);

				appForCgpan.close();
				appForCgpan = null;

				connection.rollback();
				// System.out.println("in getAppForCgpan DAO error "+error);
				throw new DatabaseException(error);
			}

			application.setAppRefNo(appForCgpan.getString(2));
			System.out.println("appForCgpan.getString(2" + appForCgpan.getString(2));
			Log.log(4, "ApplicationDAO", "getAppForCgpan", "Setting values for the application Object");
			// System.out.println("CHeck arn " + application.getAppRefNo());
			appForCgpan.close();
			appForCgpan = null;

			appRefNo = application.getAppRefNo();
			System.out.println("appRefNo" + appRefNo);
			application = getAppForAppRef(mliID, appRefNo);

			connection.commit();
		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "getAppForCgpan", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(4, "ApplicationDAO", "getAppForCgpan", ignore.getMessage());
			}
			// System.out.println("in getAppForCgpan DAO DatabaseException
			// "+sqlException.getMessage());
			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(4, "ApplicationDAO", "getAppForCgpan", "Exited");

		return application;
	}

	public double getTotalApprovedAmt(Application application) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "getTotalApprovedAmt", "Entered");

		Connection connection = DBConnection.getConnection();
		double totalApprovedAmt;
		try {
			CallableStatement totalApprovedStmt = connection
					.prepareCall("{?=call funcGetTotalApprovedAmount(?,?,?,?)}");

			totalApprovedStmt.setString(2, application.getScheme());
			Log.log(4, "ApplicationDAO", "getTotalApprovedAmt", "application.getScheme() :" + application.getScheme());

			totalApprovedStmt.setString(3, application.getSubSchemeName());
			Log.log(4, "ApplicationDAO", "getTotalApprovedAmt",
					"application.getSubSchemeName() :" + application.getSubSchemeName());

			totalApprovedStmt.registerOutParameter(1, 4);
			totalApprovedStmt.registerOutParameter(4, 8);
			totalApprovedStmt.registerOutParameter(5, 12);

			totalApprovedStmt.execute();

			int totalApprovedStmtValue = totalApprovedStmt.getInt(1);

			if (totalApprovedStmtValue == 1) {
				String error = totalApprovedStmt.getString(5);

				totalApprovedStmt.close();
				totalApprovedStmt = null;

				connection.rollback();

				throw new DatabaseException(error);
			}

			totalApprovedAmt = totalApprovedStmt.getDouble(4);

			Log.log(4, "ApplicationDAO", "getTotalApprovedAmt", "totalApprovedAmt :" + totalApprovedAmt);

			totalApprovedStmt.close();
			totalApprovedStmt = null;
		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "getAppForCgpan", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(4, "ApplicationDAO", "getAppForCgpan", ignore.getMessage());
			}

			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		return totalApprovedAmt;
	}

	public int getClaimCount(String bid) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "getTotalApprovedAmt", "Entered");

		int claimCount = 0;

		Connection connection = DBConnection.getConnection();
		try {
			CallableStatement totalClaimCount = connection.prepareCall("{call FUNCGETCOUNTFORCLAIM(?,?,?,?)}");

			totalClaimCount.setString(2, bid);
			Log.log(4, "ApplicationDAO", "getClaimCount", "getClaimCount");

			totalClaimCount.registerOutParameter(1, 4);
			totalClaimCount.registerOutParameter(3, 4);
			totalClaimCount.registerOutParameter(4, 12);

			totalClaimCount.execute();

			int totalApprovedStmtValue = totalClaimCount.getInt(1);

			if (totalApprovedStmtValue == 1) {
				String error = totalClaimCount.getString(4);

				totalClaimCount.close();
				totalClaimCount = null;

				connection.rollback();
//System.out.println("getClaimCount error "+error);
				throw new DatabaseException(error);
			}

			claimCount = totalClaimCount.getInt(3);

			Log.log(4, "ApplicationDAO", "getClaimCount", "getClaimCount :" + claimCount);

			totalClaimCount.close();
			totalClaimCount = null;
		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "getClaimCount", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(4, "ApplicationDAO", "getClaimCount", ignore.getMessage());
			}

			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		return claimCount;
	}

	// DIKSHA DUP ITPAN
	public int getDupItpanCount(String ssiITPan, String cpITPAN) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "getTotalApprovedAmt", "Entered");

		int dupItpanCnt = 0;
		double appApprovedAmt = 0.0;
		// ==================================================================================
		CallableStatement callableStmt2 = null;
		int status2 = -1;
		String errorCode2 = null;
		Connection con = DBConnection.getConnection();
		try {

			System.out.println("check ......................3..........validateTempData..........");
			callableStmt2 = con.prepareCall("{call FUNCDUPITPANCOUNT(?,?,?,?,?)}");
			callableStmt2.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt2.setString(2, ssiITPan);
			callableStmt2.setString(3, cpITPAN);
			callableStmt2.registerOutParameter(4, java.sql.Types.INTEGER);
			callableStmt2.registerOutParameter(5, java.sql.Types.VARCHAR);
			callableStmt2.execute();
			status2 = callableStmt2.getInt(1);
			errorCode2 = callableStmt2.getString(5);
			if (status2 == Constants.FUNCTION_FAILURE) {
				// Log.log(Log.ERROR, "ClaimAction","getClaimSettlePaymentReportData()","SP
				// returns a 1. Error code is :" + errorCode);
				callableStmt2.close();
				// throw new DatabaseException(errorCode);
			} else if (status2 == Constants.FUNCTION_SUCCESS) {
				//System.out.println("Data moved into main table successfully>>>>>>>>>>>>>>>>>>>>>>>");
				dupItpanCnt = callableStmt2.getInt(4);

				System.out.println("dupItpanCnt==15074. " + dupItpanCnt);
				//System.out.println("appApprovedAmt>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>. " + appApprovedAmt);
				Log.log(4, "ApplicationDAO", "getClaimCount", "getClaimCount :" + dupItpanCnt);
			}
			callableStmt2.close();
			callableStmt2 = null;
		} catch (SQLException sqlexception) {
			// Log.log(Log.ERROR,
			// "ClaimAction","getClaimSettlePaymentSavedMLIWiseCK2Data()","Error retrieving
			// all Claim settled Payment Process Data!");
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(con);
			// con.commit();
		}
		/**
		 * ============================================================================
		 * Connection connection = DBConnection.getConnection(); try { CallableStatement
		 * totalDupItpanCnt = connection .prepareCall("{?=call
		 * FUNCDUPITPANCOUNT_G(?,?,?,?)}");
		 * 
		 * totalDupItpanCnt.setString(2, ssiITPan); totalDupItpanCnt.setString(3,
		 * cpITPAN); Log.log(4, "ApplicationDAO", "dupItpanCnt", "dupItpanCnt");
		 * 
		 * totalDupItpanCnt.registerOutParameter(1, 4);
		 * totalDupItpanCnt.registerOutParameter(4, 4);
		 * totalDupItpanCnt.registerOutParameter(5, 12);
		 * 
		 * totalDupItpanCnt.execute();
		 * 
		 * int totalApprovedStmtValue = totalDupItpanCnt.getInt(1);
		 * 
		 * if (totalApprovedStmtValue == 1) { String errorMsg =
		 * totalDupItpanCnt.getString(5); errorMsg="dup..";
		 * System.out.println("error==== "+errorMsg); totalDupItpanCnt.close();
		 * totalDupItpanCnt = null;
		 * 
		 * connection.rollback(); System.out.println("dupItpanCnt error "+errorMsg);
		 * throw new DatabaseException(errorMsg); }
		 * 
		 * dupItpanCnt = totalDupItpanCnt.getInt(4); System.out.println("dupItpanCnt
		 * "+dupItpanCnt); Log.log(4, "ApplicationDAO", "getClaimCount", "getClaimCount
		 * :" + dupItpanCnt);
		 * 
		 * totalDupItpanCnt.close(); totalDupItpanCnt = null; } catch (SQLException
		 * sqlException) { Log.log(4, "ApplicationDAO", "getClaimCount",
		 * sqlException.getMessage()); Log.logException(sqlException); try {
		 * connection.rollback(); } catch (SQLException ignore) { Log.log(4,
		 * "ApplicationDAO", "getClaimCount", ignore.getMessage()); }
		 * 
		 * throw new DatabaseException(sqlException.getMessage()); } finally {
		 * DBConnection.freeConnection(connection); }
		 */

		return dupItpanCnt;
	}

	public double getItpanAmount(String ssiITPan, String cpITPAN, String memberId) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "getTotalApprovedAmt", "Entered");

		int dupItpanCnt = 0;
		double appApprovedAmt = 0.0;
		// ==================================================================================
		CallableStatement callableStmt2 = null;
		int status2 = -1;
		String errorCode2 = null;
		Connection con = DBConnection.getConnection();
		try {

			System.out.println("check ......................3..getItpanAmount..................");
			callableStmt2 = con.prepareCall("{call FUNCITPANAMOUNT(?,?,?,?,?,?,?)}");
			callableStmt2.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt2.setString(2, ssiITPan);
			callableStmt2.setString(3, cpITPAN);
			callableStmt2.setString(4, memberId);
			callableStmt2.registerOutParameter(5, java.sql.Types.INTEGER);
			callableStmt2.registerOutParameter(6, java.sql.Types.INTEGER);
			callableStmt2.registerOutParameter(7, java.sql.Types.VARCHAR);
			callableStmt2.execute();
			status2 = callableStmt2.getInt(1);
			errorCode2 = callableStmt2.getString(7);
			if (status2 == Constants.FUNCTION_FAILURE) {
				// Log.log(Log.ERROR, "ClaimAction","getClaimSettlePaymentReportData()","SP
				// returns a 1. Error code is :" + errorCode);
				callableStmt2.close();
				// throw new DatabaseException(errorCode);
			} else if (status2 == Constants.FUNCTION_SUCCESS) {
				System.out.println("Data moved into main table successfully>>>>>>>>>>>>>>>>>>>>>>>");
				appApprovedAmt = callableStmt2.getInt(5);

				System.out.println("dupItpanCnt>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>. " + dupItpanCnt);
				System.out.println("appApprovedAmt>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>. " + appApprovedAmt);
				Log.log(4, "ApplicationDAO", "getClaimCount", "getClaimCount :" + dupItpanCnt);
			}
			callableStmt2.close();
			callableStmt2 = null;
		} catch (SQLException sqlexception) {
			// Log.log(Log.ERROR,
			// "ClaimAction","getClaimSettlePaymentSavedMLIWiseCK2Data()","Error retrieving
			// all Claim settled Payment Process Data!");
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(con);
			// con.commit();
		}

		return appApprovedAmt;
	}

	public double getTotalItpanAmount(String ssiITPan, String cpITPAN, String memberId) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "getTotalApprovedAmt", "Entered");

		int dupItpanCnt = 0;
		double appApprovedAmt = 0.0;
		// ==================================================================================
		CallableStatement callableStmt2 = null;
		int status2 = -1;
		String errorCode2 = null;
		Connection con = DBConnection.getConnection();
		try {

			System.out.println("check ......................3..getItpanAmount..................");
			callableStmt2 = con.prepareCall("{call FUNCTOTALITPANAMOUNT(?,?,?,?,?,?,?)}");
			callableStmt2.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt2.setString(2, ssiITPan);
			callableStmt2.setString(3, cpITPAN);
			callableStmt2.setString(4, memberId);
			callableStmt2.registerOutParameter(5, java.sql.Types.INTEGER);
			callableStmt2.registerOutParameter(6, java.sql.Types.INTEGER);
			callableStmt2.registerOutParameter(7, java.sql.Types.VARCHAR);
			callableStmt2.execute();
			status2 = callableStmt2.getInt(1);
			errorCode2 = callableStmt2.getString(7);
			if (status2 == Constants.FUNCTION_FAILURE) {
				// Log.log(Log.ERROR, "ClaimAction","getClaimSettlePaymentReportData()","SP
				// returns a 1. Error code is :" + errorCode);
				callableStmt2.close();
				// throw new DatabaseException(errorCode);
			} else if (status2 == Constants.FUNCTION_SUCCESS) {
				System.out.println("Data moved into main table successfully>>>>>>>>>>>>>>>>>>>>>>>");
				appApprovedAmt = callableStmt2.getInt(5);

				System.out.println("dupItpanCnt>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>. " + dupItpanCnt);
				System.out.println("appApprovedAmt>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>. " + appApprovedAmt);
				Log.log(4, "ApplicationDAO", "getClaimCount", "getClaimCount :" + dupItpanCnt);
			}
			callableStmt2.close();
			callableStmt2 = null;
		} catch (SQLException sqlexception) {
			// Log.log(Log.ERROR,
			// "ClaimAction","getClaimSettlePaymentSavedMLIWiseCK2Data()","Error retrieving
			// all Claim settled Payment Process Data!");
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(con);
			// con.commit();
		}
		return appApprovedAmt;
	}

	public double getPplosItpanAmount(String ssiITPan, String cpITPAN, String memberId) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "getTotalApprovedAmt", "Entered");

		int dupItpanCnt = 0;
		double appApprovedAmt = 0.0;
//==================================================================================
		CallableStatement callableStmt2 = null;
		int status2 = -1;
		String errorCode2 = null;
		Connection con = DBConnection.getConnection();
		try {

			System.out.println("check ......................3..getPplosItpanAmount..................");
			callableStmt2 = con.prepareCall("{call FUNCTOTALBUREAUITPANAMOUNT(?,?,?,?,?,?,?)}");
			callableStmt2.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt2.setString(2, ssiITPan);
			callableStmt2.setString(3, cpITPAN);
			callableStmt2.setString(4, memberId);
			callableStmt2.registerOutParameter(5, java.sql.Types.INTEGER);
			callableStmt2.registerOutParameter(6, java.sql.Types.INTEGER);
			callableStmt2.registerOutParameter(7, java.sql.Types.VARCHAR);
			callableStmt2.execute();
			status2 = callableStmt2.getInt(1);
			errorCode2 = callableStmt2.getString(7);
			if (status2 == Constants.FUNCTION_FAILURE) {
				// Log.log(Log.ERROR, "ClaimAction","getClaimSettlePaymentReportData()","SP
				// returns a 1. Error code is :" + errorCode);
				callableStmt2.close();
				// throw new DatabaseException(errorCode);
			} else if (status2 == Constants.FUNCTION_SUCCESS) {
				System.out.println("Data moved into main table successfully>>>>>>>>>>>>>>>>>>>>>>>");
				appApprovedAmt = callableStmt2.getInt(5);

				System.out.println("dupItpanCnt>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>. " + dupItpanCnt);
				System.out.println("appApprovedAmt>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>. " + appApprovedAmt);
				Log.log(4, "ApplicationDAO", "getClaimCount", "getClaimCount :" + dupItpanCnt);
			}
			callableStmt2.close();
			callableStmt2 = null;
		} catch (SQLException sqlexception) {
			// Log.log(Log.ERROR,
			// "ClaimAction","getClaimSettlePaymentSavedMLIWiseCK2Data()","Error retrieving
			// all Claim settled Payment Process Data!");
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(con);
			// con.commit();
		}
		return appApprovedAmt;
	}

	public int getDPDValue(String ssiITPan, String cpITPAN, String memberId) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "getTotalApprovedAmt", "Entered");

		int dupItpanCnt = 0;
		int appApprovedAmt = 0;
//==================================================================================
		CallableStatement callableStmt2 = null;
		int status2 = -1;
		String errorCode2 = null;
		Connection con = DBConnection.getConnection();
		try {

			System.out.println("check ......................3..FUNCGETDPDVALUE..................");
			callableStmt2 = con.prepareCall("{call FUNCGETDPDVALUE(?,?,?,?,?,?,?)}");
			callableStmt2.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt2.setString(2, ssiITPan);
			callableStmt2.setString(3, cpITPAN);
			callableStmt2.setString(4, memberId);
			callableStmt2.registerOutParameter(5, java.sql.Types.INTEGER);
			callableStmt2.registerOutParameter(6, java.sql.Types.INTEGER);
			callableStmt2.registerOutParameter(7, java.sql.Types.VARCHAR);
			callableStmt2.execute();
			status2 = callableStmt2.getInt(1);
			errorCode2 = callableStmt2.getString(7);
			if (status2 == Constants.FUNCTION_FAILURE) {
				// Log.log(Log.ERROR, "ClaimAction","getClaimSettlePaymentReportData()","SP
				// returns a 1. Error code is :" + errorCode);
				callableStmt2.close();
				// throw new DatabaseException(errorCode);
			} else if (status2 == Constants.FUNCTION_SUCCESS) {
				System.out.println("Data moved into main table successfully>>>>>>>>>>>>>>>>>>>>>>>");
				appApprovedAmt = callableStmt2.getInt(5);

				System.out.println("dupItpanCnt>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>. " + dupItpanCnt);
				System.out.println("appApprovedAmt>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>. " + appApprovedAmt);
				Log.log(4, "ApplicationDAO", "getClaimCount", "getClaimCount :" + dupItpanCnt);
			}
			callableStmt2.close();
			callableStmt2 = null;
		} catch (SQLException sqlexception) {
			// Log.log(Log.ERROR,
			// "ClaimAction","getClaimSettlePaymentSavedMLIWiseCK2Data()","Error retrieving
			// all Claim settled Payment Process Data!");
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(con);
// con.commit();
		}

		return appApprovedAmt;
	}

	public int getMemIdType(String memid) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "getMemIdType", "Entered");

		int dupItpanCnt = 0;
		// ==================================================================================
		CallableStatement callableStmt2 = null;
		int status2 = -1;
		String errorCode2 = null;
		Connection con = DBConnection.getConnection();
		try {

			System.out.println("check dkr......................3..........validateTempData..........");
			callableStmt2 = con.prepareCall("{call FUNGETMLITYPE(?,?,?,?)}");
			callableStmt2.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt2.setString(2, memid);
			callableStmt2.registerOutParameter(3, java.sql.Types.INTEGER);
			callableStmt2.registerOutParameter(4, java.sql.Types.VARCHAR);
			callableStmt2.execute();
			status2 = callableStmt2.getInt(1);
			errorCode2 = callableStmt2.getString(4);
			if (status2 == Constants.FUNCTION_FAILURE) {
				// Log.log(Log.ERROR, "ClaimAction","getClaimSettlePaymentReportData()","SP
				// returns a 1. Error code is :" + errorCode);
				callableStmt2.close();
				// throw new DatabaseException(errorCode);
			} else if (status2 == Constants.FUNCTION_SUCCESS) {
				System.out.println("Data moved into main table successfully>>>>>>>>>>>>>>>>>>>>>>>");
				dupItpanCnt = callableStmt2.getInt(3);
				System.out.println("dupItpanCnt>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>. " + dupItpanCnt);
				Log.log(4, "ApplicationDAO", "getClaimCount", "getClaimCount :" + dupItpanCnt);
			}
			callableStmt2.close();
			callableStmt2 = null;
		} catch (SQLException sqlexception) {
			// Log.log(Log.ERROR,
			// "ClaimAction","getClaimSettlePaymentSavedMLIWiseCK2Data()","Error retrieving
			// all Claim settled Payment Process Data!");
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(con);
			// con.commit();
		}

		return dupItpanCnt;
	}

	public int getmudraMaxLimit(String memid,String mudraTypeVal,Double creditGuaranteed) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "mudraMaxLimit", "Entered");

		int dupItpanCnt = 0;
		// ==================================================================================
		CallableStatement callableStmt2 = null;
		int status2 = -1;
		String errorCode2 = null;
		Connection con = DBConnection.getConnection();
		try {

			System.out.println(".........mudraMaxLimit..........");
			callableStmt2 = con.prepareCall("{call FUNGETMUDRAMAXLIMITCOUNTNEW(?,?,?,?,?,?)}");
			callableStmt2.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt2.setString(2, memid);
			callableStmt2.setString(3,mudraTypeVal);
			callableStmt2.setDouble(4,creditGuaranteed);
			callableStmt2.registerOutParameter(5, java.sql.Types.INTEGER);
			callableStmt2.registerOutParameter(6, java.sql.Types.VARCHAR);
			callableStmt2.execute();
			status2 = callableStmt2.getInt(1);
			errorCode2 = callableStmt2.getString(6);
			if (status2 == Constants.FUNCTION_FAILURE) {
				// Log.log(Log.ERROR, "ClaimAction","getClaimSettlePaymentReportData()","SP
				// returns a 1. Error code is :" + errorCode);
				callableStmt2.close();
				// throw new DatabaseException(errorCode);
			} else if (status2 == Constants.FUNCTION_SUCCESS) {
				
				dupItpanCnt = callableStmt2.getInt(5);
				System.out.println(">>>>>>>>>>>>>>>mudraMaxLimit>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>. " + dupItpanCnt);
				Log.log(4, "ApplicationDAO", "getClaimCount", "getClaimCount :" + dupItpanCnt);
			}
			callableStmt2.close();
			callableStmt2 = null;
		} catch (SQLException sqlexception) {
			// Log.log(Log.ERROR,
			// "ClaimAction","getClaimSettlePaymentSavedMLIWiseCK2Data()","Error retrieving
			// all Claim settled Payment Process Data!");
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(con);
			// con.commit();
		}

		return dupItpanCnt;
	}
	
	
	public int getmudraLoanAccNo(String pmrLoanAccNo) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "mudraMaxLimit", "Entered");

		int dupItpanCnt = 0;
		// ==================================================================================
		CallableStatement callableStmt2 = null;
		int status2 = -1;
		String errorCode2 = null;
		Connection con = DBConnection.getConnection();
		try {

			System.out.println(".........mudraMaxLimit..........");
			callableStmt2 = con.prepareCall("{call FUNGETMUDRALOANACCNO(?,?,?,?)}");
			callableStmt2.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt2.setString(2, pmrLoanAccNo);
			callableStmt2.registerOutParameter(3, java.sql.Types.INTEGER);
			callableStmt2.registerOutParameter(4, java.sql.Types.VARCHAR);
			callableStmt2.execute();
			status2 = callableStmt2.getInt(1);
			errorCode2 = callableStmt2.getString(4);
			if (status2 == Constants.FUNCTION_FAILURE) {
				// Log.log(Log.ERROR, "ClaimAction","getClaimSettlePaymentReportData()","SP
				// returns a 1. Error code is :" + errorCode);
				callableStmt2.close();
				// throw new DatabaseException(errorCode);
			} else if (status2 == Constants.FUNCTION_SUCCESS) {
				
				dupItpanCnt = callableStmt2.getInt(3);
				System.out.println(">>>>>>>>>>>>>>>mudraMaxLimit>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>. " + dupItpanCnt);
				Log.log(4, "ApplicationDAO", "getClaimCount", "getClaimCount :" + dupItpanCnt);
			}
			callableStmt2.close();
			callableStmt2 = null;
		} catch (SQLException sqlexception) {
			// Log.log(Log.ERROR,
			// "ClaimAction","getClaimSettlePaymentSavedMLIWiseCK2Data()","Error retrieving
			// all Claim settled Payment Process Data!");
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(con);
			// con.commit();
		}

		return dupItpanCnt;
	}
	
	public int getAppRefNoCount(String appRefNo) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "getAppRefNoCount", "Entered");

		int appRefNumberCount = 0;
		PreparedStatement pStmt = null;
		ArrayList aList = new ArrayList();
		ResultSet rsSet = null;
		Connection connection = DBConnection.getConnection();
		try {
			String query = "SELECT COUNT(APP_REF_NO) FROM APPLICATION_DETAIL WHERE APP_REF_NO=?";
			pStmt = connection.prepareStatement(query);
			pStmt.setString(1, appRefNo);
			rsSet = pStmt.executeQuery();
			while (rsSet.next()) {
				appRefNumberCount = rsSet.getInt(1);
			}
			rsSet.close();
			pStmt.close();
		} catch (Exception exception) {
			Log.logException(exception);
			throw new DatabaseException(exception.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		return appRefNumberCount;
	}

	public String getSSIRefNo(String appRefNo) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "getSSIRefNo", "Entered");

		String ssiRef = "";
		PreparedStatement pStmt = null;
		ArrayList aList = new ArrayList();
		ResultSet rsSet = null;
		Connection connection = DBConnection.getConnection();
		try {
			String query = "SELECT UNIQUE SSI_REFERENCE_NUMBER FROM APPLICATION_DETAIL WHERE (CGPAN=? OR APP_REF_NO=? ) ";
			pStmt = connection.prepareStatement(query);
			pStmt.setString(1, appRefNo);
			pStmt.setString(2, appRefNo);
			rsSet = pStmt.executeQuery();
			while (rsSet.next()) {
				ssiRef = rsSet.getString(1);
			}
			rsSet.close();
			pStmt.close();
		} catch (Exception exception) {
			Log.logException(exception);
			throw new DatabaseException(exception.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		return ssiRef;
	}

	public String getSSIRefNoNew(String appRefNo) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "getSSIRefNo", "Entered");

		String ssiRef = "";
		PreparedStatement pStmt = null;
		ArrayList aList = new ArrayList();
		ResultSet rsSet = null;
		Connection connection = DBConnection.getConnection();
		try {
			String query = "SELECT UNIQUE SSI_REFERENCE_NUMBER FROM APPLICATION_DETAIL_TEMP WHERE (CGPAN=? OR APP_REF_NO=? ) union   SELECT UNIQUE SSI_REFERENCE_NUMBER FROM APPLICATION_DETAIL WHERE (CGPAN=? OR APP_REF_NO=? ) ";

			pStmt = connection.prepareStatement(query);
			pStmt.setString(1, appRefNo);
			pStmt.setString(2, appRefNo);
			pStmt.setString(3, appRefNo);
			pStmt.setString(4, appRefNo);

			rsSet = pStmt.executeQuery();
			while (rsSet.next()) {
				ssiRef = rsSet.getString(1);
			}
			rsSet.close();
			pStmt.close();
		} catch (Exception exception) {
			Log.logException(exception);
			throw new DatabaseException(exception.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		return ssiRef;
	}

	public String getMemberIdforAppRef(String appRefNo) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "getMemberIdforAppRef", "Entered");
		String memberId = "";
		Connection connection = DBConnection.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rsSet = null;
		try {
			String query = "SELECT MEM_BNK_ID||MEM_ZNE_ID||MEM_BRN_ID FROM APPLICATION_DETAIL WHERE APP_REF_NO=?";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, appRefNo);
			rsSet = pstmt.executeQuery();
			while (rsSet.next()) {
				memberId = rsSet.getString(1);
			}
			rsSet.close();
			pstmt.close();
		} catch (Exception exception) {
			Log.logException(exception);
			throw new DatabaseException(exception.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(4, "ApplicationDAO", "getMemberIdforAppRef", "Exited");

		return memberId;
	}

	public String getBranchName(String cgpan) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "getBranchName", "Entered");

		String branchName = "";
		PreparedStatement pStmt = null;
		ArrayList aList = new ArrayList();
		ResultSet rsSet = null;
		Connection connection = DBConnection.getConnection();
		try {
			String query = "SELECT UPPER(APP_MLI_BRANCH_NAME) FROM APPLICATION_DETAIL WHERE CGPAN=?";
			pStmt = connection.prepareStatement(query);
			pStmt.setString(1, cgpan);
			rsSet = pStmt.executeQuery();
			while (rsSet.next()) {
				branchName = rsSet.getString(1);
			}
			rsSet.close();
			pStmt.close();
		} catch (Exception exception) {
			Log.logException(exception);
			throw new DatabaseException(exception.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		return branchName;
	}

	public String getAppRefNo(String cgpan) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "getAppRefNo", "Entered");

		String appRefNo = "";
		PreparedStatement pStmt = null;
		ArrayList aList = new ArrayList();
		ResultSet rsSet = null;
		Connection connection = DBConnection.getConnection();
		try {
			String query = "SELECT APP_REF_NO FROM APPLICATION_DETAIL WHERE CGPAN=?";
			pStmt = connection.prepareStatement(query);
			pStmt.setString(1, cgpan);
			rsSet = pStmt.executeQuery();
			while (rsSet.next()) {
				appRefNo = rsSet.getString(1);
			}
			rsSet.close();
			pStmt.close();
		} catch (Exception exception) {
			Log.logException(exception);
			throw new DatabaseException(exception.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		return appRefNo;
	}

	public void updateBranchNameForCgpan(String memberId, String appRefNo, String cgpan, String branchName,
			String userId) throws DatabaseException {
		String methodName = "submitClosureDetails";

		Log.log(4, "ApplicationDAO", methodName, "Entered");
		String bankId = memberId.substring(0, 4);
		String zoneId = memberId.substring(4, 8);
		String branchId = memberId.substring(8, 12);

		Connection connection = DBConnection.getConnection();
		CallableStatement callable = null;
		int status = 0;
		try {
			callable = connection.prepareCall("{?=call funcInsBranchNameDet(?,?,?,?,?,?,?,?)}");

			callable.registerOutParameter(1, 4);
			callable.setString(2, bankId);
			callable.setString(3, zoneId);
			callable.setString(4, branchId);
			callable.setString(5, appRefNo);

			callable.setString(6, cgpan);

			callable.setString(7, userId);

			callable.setString(8, branchName);

			callable.registerOutParameter(9, 12);
			callable.executeQuery();
			status = callable.getInt(1);

			String error = callable.getString(9);

			Log.log(5, "ApplicationDAO", methodName, "error code and error" + status + "," + error);

			if (status == 1) {
				Log.log(2, "ApplicationDAO", methodName, error);

				callable.close();
				callable = null;
				throw new DatabaseException(error);
			}

			callable.close();
			callable = null;
			connection.commit();
		} catch (SQLException e) {
			Log.log(2, "ApplicationDAO", methodName, e.getMessage());

			Log.logException(e);

			throw new DatabaseException("Unable to insert app_branchname_update details.");
		} finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(4, "ApplicationDAO", methodName, "Exited");
	}

	public double getExposureLimit(String bankId) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "getExposureLimit", "Entered");

		double exposureLimit = 0.0D;
		PreparedStatement pStmt = null;
		ResultSet rsSet = null;
		Connection connection = DBConnection.getConnection();
		try {
			String query = "SELECT IFNULL(CUMAMT,0)+IFNULL(UNPROCAMT,0) FROM VIEW_EXPOSURE_STATUS WHERE MEM_BNK_ID=?";
			pStmt = connection.prepareStatement(query);
			pStmt.setString(1, bankId);
			rsSet = pStmt.executeQuery();
			while (rsSet.next()) {
				exposureLimit = rsSet.getDouble(1);
			}
			rsSet.close();
			pStmt.close();
		} catch (Exception exception) {
			Log.logException(exception);
			throw new DatabaseException(exception.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		return exposureLimit;
	}

	public double getMaxExposureLimit(String bankId) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "getMaxExposureLimit", "Entered");

		double maxExposureLimit = 0.0D;
		PreparedStatement pStmt = null;
		ResultSet rsSet = null;
		Connection connection = DBConnection.getConnection();
		try {
			String query = "SELECT IFNULL(TOTAL_LIMIT,0) FROM EXPOSURE_LIMITS WHERE MEM_BNK_ID=?";
			pStmt = connection.prepareStatement(query);
			pStmt.setString(1, bankId);
			rsSet = pStmt.executeQuery();
			while (rsSet.next()) {
				maxExposureLimit = rsSet.getDouble(1);
			}
			rsSet.close();
			pStmt.close();
		} catch (Exception exception) {
			Log.logException(exception);
			throw new DatabaseException(exception.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		return maxExposureLimit;
	}

	public int getPaymentStatus(String cgpan) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "getPaymentStatus", "Entered");

		int count = 0;
		PreparedStatement pStmt = null;
		ResultSet rsSet = null;
		Connection connection = DBConnection.getConnection();
		try {
			String query = "SELECT COUNT(CGPAN) FROM DAN_CGPAN_INFO WHERE CGPAN=? AND DCI_ALLOCATION_FLAG='N'  AND DAN_ID LIKE 'CG%' AND (DCI_AMOUNT_RAISED-IFNULL(DCI_AMOUNT_CANCELLED,0))>0  HAVING COUNT(DAN_ID)=COUNT(CGPAN)";

			pStmt = connection.prepareStatement(query);
			pStmt.setString(1, cgpan);

			rsSet = pStmt.executeQuery();
			while (rsSet.next()) {
				count = rsSet.getInt(1);
			}
			rsSet.close();
			pStmt.close();
		} catch (Exception exception) {
			Log.logException(exception);
			throw new DatabaseException(exception.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		return count;
	}

	public String getClassificationMLI(String bankId) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "getClassificationMLI", "Entered");

		String mliType = null;
		PreparedStatement pStmt = null;
		ResultSet rsSet = null;
		Connection connection = DBConnection.getConnection();
		try {
			String query = "SELECT IFNULL(MLI_TYPE,NULL) FROM EXPOSURE_LIMITS WHERE MEM_BNK_ID=?";
			pStmt = connection.prepareStatement(query);
			pStmt.setString(1, bankId);
			rsSet = pStmt.executeQuery();
			while (rsSet.next()) {
				mliType = rsSet.getString(1);
			}
			rsSet.close();
			pStmt.close();
			// System.out.println("classification of MLI- " + bankId + "---"
			// + mliType);
		} catch (Exception exception) {
			Log.logException(exception);
			throw new DatabaseException(exception.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		return mliType;
	}

	public double getCorpusContAmt(int ssiRefNumber) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "getCorpusContAmt", "Entered");

		Connection connection = DBConnection.getConnection();
		double corpusContAmt;
		try {
			CallableStatement totalCorpusCont = connection.prepareCall("{?=call funcGetCorpusContamt(?,?,?)}");

			totalCorpusCont.setInt(2, ssiRefNumber);
			Log.log(4, "ApplicationDAO", "getCorpusContAmt", "getClaimCount");

			totalCorpusCont.registerOutParameter(1, 4);
			totalCorpusCont.registerOutParameter(3, 8);
			totalCorpusCont.registerOutParameter(4, 12);

			totalCorpusCont.execute();

			int totalApprovedStmtValue = totalCorpusCont.getInt(1);

			if (totalApprovedStmtValue == 1) {
				String error = totalCorpusCont.getString(4);

				totalCorpusCont.close();
				totalCorpusCont = null;

				connection.rollback();

				throw new DatabaseException(error);
			}

			corpusContAmt = totalCorpusCont.getDouble(3);

			Log.log(4, "ApplicationDAO", "getCorpusContAmt", "getClaimCount :" + corpusContAmt);

			totalCorpusCont.close();
			totalCorpusCont = null;
		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "getCorpusContAmt", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(4, "ApplicationDAO", "getCorpusContAmt", ignore.getMessage());
			}

			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		return corpusContAmt;
	}

	public double getPartBankAmount(String bnkId, String zoneId, String branchId) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "getPartBankAmount", "Entered");

		Connection connection = DBConnection.getConnection();
		double partBankAmount;
		try {
			CallableStatement partBankAmt = connection.prepareCall("{?=call funcGetPartBankAmount(?,?,?,?,?)}");

			partBankAmt.setString(2, bnkId);
			partBankAmt.setString(3, zoneId);
			partBankAmt.setString(4, branchId);

			partBankAmt.registerOutParameter(1, 4);
			partBankAmt.registerOutParameter(5, 8);
			partBankAmt.registerOutParameter(6, 12);

			partBankAmt.execute();

			int totalApprovedStmtValue = partBankAmt.getInt(1);

			if (totalApprovedStmtValue == 1) {
				String error = partBankAmt.getString(6);

				partBankAmt.close();
				partBankAmt = null;

				connection.rollback();

				throw new DatabaseException(error);
			}

			partBankAmount = partBankAmt.getDouble(5);

			Log.log(4, "ApplicationDAO", "getPartBankAmount", "getClaimCount :" + partBankAmount);

			partBankAmt.close();
			partBankAmt = null;
		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "getPartBankAmount", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(4, "ApplicationDAO", "getPartBankAmount", sqlException.getMessage());
			}

			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		return partBankAmount;
	}

	public double getMcgsApprovedAmount(String bnkId, String zoneId, String branchId) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "getMcgsApprovedAmount", "Entered");

		Connection connection = DBConnection.getConnection();
		double partBankAmount;
		try {
			CallableStatement partBankAmt = connection.prepareCall("{?=call funcGetMcgsApprovedAmt(?,?,?,?,?)}");

			partBankAmt.setString(2, bnkId);
			partBankAmt.setString(3, zoneId);
			partBankAmt.setString(4, branchId);

			partBankAmt.registerOutParameter(1, 4);
			partBankAmt.registerOutParameter(5, 8);
			partBankAmt.registerOutParameter(6, 12);

			partBankAmt.execute();

			int totalApprovedStmtValue = partBankAmt.getInt(1);

			if (totalApprovedStmtValue == 1) {
				String error = partBankAmt.getString(6);

				partBankAmt.close();
				partBankAmt = null;

				connection.rollback();

				throw new DatabaseException(error);
			}

			partBankAmount = partBankAmt.getDouble(5);
			partBankAmt.close();
			partBankAmt = null;

			Log.log(4, "ApplicationDAO", "getMcgsApprovedAmount", "getClaimCount :" + partBankAmount);
		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "getMcgsApprovedAmount", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(4, "ApplicationDAO", "getMcgsApprovedAmount", ignore.getMessage());
			}

			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		return partBankAmount;
	}

	public ArrayList getCountForTCConv() throws DatabaseException {
		Log.log(4, "ApplicationDAO", "getCountForTCConv", "Entered");

		Connection connection = DBConnection.getConnection();
		ArrayList countTCApp = new ArrayList();
		try {
			CallableStatement countForTC = connection
					.prepareCall("{?=call packGetTCAppForConv.funcGetTCAppForConv(?,?)}");

			countForTC.registerOutParameter(1, 4);
			countForTC.registerOutParameter(2, -10);
			countForTC.registerOutParameter(3, 12);

			countForTC.execute();

			int totalApprovedStmtValue = countForTC.getInt(1);

			if (totalApprovedStmtValue == 1) {
				String error = countForTC.getString(3);

				countForTC.close();
				countForTC = null;

				connection.rollback();

				throw new DatabaseException(error);
			}

			ResultSet tcResults = (ResultSet) countForTC.getObject(2);
			while (tcResults.next()) {
				String appRefNo = tcResults.getString(1);
				countTCApp.add(appRefNo);
			}

			tcResults.close();
			tcResults = null;
			countForTC.close();
			countForTC = null;
		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "getCountForTCConv", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(4, "ApplicationDAO", "getCountForTCConv", ignore.getMessage());
			}

			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		return countTCApp;
	}

	public ArrayList getCountForWCConv() throws DatabaseException {
		Log.log(4, "ApplicationDAO", "getCountForWCConv", "Entered");

		Connection connection = DBConnection.getConnection();
		ArrayList countWCApp = new ArrayList();
		try {
			CallableStatement countForWC = connection
					.prepareCall("{?=call packGetWCAppForConv.funcGetWCAppForConv(?,?)}");

			countForWC.registerOutParameter(1, 4);
			countForWC.registerOutParameter(2, -10);
			countForWC.registerOutParameter(3, 12);

			countForWC.execute();

			int totalApprovedStmtValue = countForWC.getInt(1);

			if (totalApprovedStmtValue == 1) {
				String error = countForWC.getString(3);

				countForWC.close();
				countForWC = null;

				connection.rollback();

				throw new DatabaseException(error);
			}

			ResultSet wcResults = (ResultSet) countForWC.getObject(2);
			while (wcResults.next()) {
				String appRefNo = wcResults.getString(1);
				countWCApp.add(appRefNo);
			}

			wcResults.close();
			wcResults = null;

			countForWC.close();
			countForWC = null;
		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "getCountForWCConv", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(4, "ApplicationDAO", "getCountForWCConv", ignore.getMessage());
			}

			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		return countWCApp;
	}

	public void updateTCConv(Application application, int appSSIRef) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "updateTCConv", "Entered");

		Connection connection = DBConnection.getConnection();
		try {
			CallableStatement updateRef = connection.prepareCall("{?=call funcUpdateTCConv(?,?,?,?,?,?)}");

			updateRef.registerOutParameter(1, 4);
			updateRef.registerOutParameter(7, 12);

			updateRef.setString(2, application.getAppRefNo());
			updateRef.setString(3, application.getCgpan());
			updateRef.setInt(4, appSSIRef);
			updateRef.setInt(5, application.getBorrowerDetails().getSsiDetails().getBorrowerRefNo());
			updateRef.setString(6, application.getSubSchemeName());

			updateRef.executeQuery();
			int functionReturnValue = updateRef.getInt(1);
			Log.log(5, "ApplicationDAO", "updateTCConv", "update App Reference :" + functionReturnValue);

			if (functionReturnValue == 1) {
				String error = updateRef.getString(7);

				updateRef.close();
				updateRef = null;

				connection.rollback();

				Log.log(2, "ApplicationDAO", "updateTCConv", "updateAppReference Exception" + error);
				throw new DatabaseException(error);
			}

			updateRef.close();
			updateRef = null;
			connection.commit();
		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "updateTCConv", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(4, "ApplicationDAO", "updateTCConv", ignore.getMessage());
			}

			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}
	}

	public void updateWCConv(Application application, int appSsiRef) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "updateWCConv", "Entered");

		Connection connection = DBConnection.getConnection();
		try {
			CallableStatement updateRef = connection.prepareCall("{?=call funcUpdateWCConv(?,?,?,?,?,?,?,?,?,?,?,?)}");

			updateRef.registerOutParameter(1, 4);
			updateRef.registerOutParameter(13, 12);

			updateRef.setString(2, application.getAppRefNo());
			updateRef.setString(3, application.getCgpan());
			updateRef.setInt(4, appSsiRef);
			updateRef.setInt(5, application.getBorrowerDetails().getSsiDetails().getBorrowerRefNo());
			updateRef.setString(6, application.getSubSchemeName());
			updateRef.setString(7, application.getStatus());
			updateRef.setDouble(8, application.getWc().getFundBasedLimitSanctioned());
			updateRef.setDouble(9, application.getWc().getNonFundBasedLimitSanctioned());
			updateRef.setDouble(10, application.getWc().getWcInterestRate());
			updateRef.setDouble(11, application.getWc().getLimitNonFundBasedCommission());
			updateRef.setString(12, application.getWc().getWcInterestType());

			updateRef.executeQuery();
			int functionReturnValue = updateRef.getInt(1);
			Log.log(5, "ApplicationDAO", "updateWCConv", "update App Reference :" + functionReturnValue);

			if (functionReturnValue == 1) {
				String error = updateRef.getString(13);

				updateRef.close();
				updateRef = null;

				connection.rollback();

				Log.log(2, "ApplicationDAO", "updateWCConv", "updateAppReference Exception" + error);
				throw new DatabaseException(error);
			}

			updateRef.close();
			updateRef = null;
			connection.commit();
		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "updateWCConv", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(4, "ApplicationDAO", "updateWCConv", ignore.getMessage());
			}

			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}
	}

	public ArrayList getCountForDanGen(String appRefNo) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "getCountForDanGen", "Entered");

		Connection connection = DBConnection.getConnection();

		ArrayList countAmount = new ArrayList();
		try {
			CallableStatement countForDan = connection.prepareCall("{?=call funcGetDanGenValue(?,?,?,?,?)}");

			countForDan.registerOutParameter(1, 4);
			countForDan.registerOutParameter(3, 4);
			countForDan.registerOutParameter(4, 8);
			countForDan.registerOutParameter(5, 12);
			countForDan.registerOutParameter(6, 12);

			countForDan.setString(2, appRefNo);

			countForDan.executeQuery();
			int functionReturnValue = countForDan.getInt(1);
			Log.log(5, "ApplicationDAO", "getCountForDanGen", "update App Reference :" + functionReturnValue);

			if (functionReturnValue == 1) {
				String error = countForDan.getString(6);

				countForDan.close();
				countForDan = null;

				connection.rollback();

				Log.log(2, "ApplicationDAO", "getCountForDanGen", "updateAppReference Exception" + error);
				throw new DatabaseException(error);
			}

			countAmount.add(new Integer(countForDan.getInt(3)));
			countAmount.add(new Double(countForDan.getDouble(4)));
			countAmount.add(countForDan.getString(5));

			countForDan.close();
			countForDan = null;

			connection.commit();
		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "getCountForDanGen", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(4, "ApplicationDAO", "getCountForDanGen", ignore.getMessage());
			}

			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		return countAmount;
	}

	public void generateDanForEnhance(Application application, User user) throws DatabaseException {
		Connection connection = DBConnection.getConnection(false);

		RpHelper rpHelper = new RpHelper();
		RegistrationDAO registrationDAO = new RegistrationDAO();
		Mailer mailer = new Mailer();
		DemandAdvice demandAdvice = new DemandAdvice();
		RpProcessor rpProcessor = new RpProcessor();
		RpDAO rpDAO = new RpDAO();
		Administrator administrator = new Administrator();

		String subject = "";
		String emailMessage = "";
		boolean mailStatus = false;
		ArrayList users = null;
		ArrayList emailIds = null;
		MLIInfo mliInfo = null;
		String mailPrivelege = "";
		String emailPrivelege = "";
		String hardCopyPrivelege = "";
		String userId = user.getUserId();
		String fromEmail = user.getUserId();
		User mailUser = null;

		java.util.Date danGeneratedDate = new java.util.Date();
		try {
			application.setReapprovedAmount(0.0D);

			String cgdanNo = rpHelper.generateCGDANId(application.getMliID().substring(0, 4),
					application.getMliID().substring(4, 8), application.getMliID().substring(8, 12), connection);

			Log.log(4, "application DAO", "generateDanForEnhance", "cgdanNo" + cgdanNo);

			demandAdvice.setBankId(application.getMliID().substring(0, 4));
			demandAdvice.setZoneId(application.getMliID().substring(4, 8));
			demandAdvice.setBranchId(application.getMliID().substring(8, 12));

			demandAdvice.setDanNo(cgdanNo);
			demandAdvice.setDanType("CG");
			demandAdvice.setDanGeneratedDate(danGeneratedDate);
			Log.log(4, "application DAO", "generateDanForEnhance", "cgpan" + application.getCgpan());

			demandAdvice.setCgpan(application.getCgpan());

			java.util.Date dueDate = rpProcessor.getPANDueDate(demandAdvice, null);
			demandAdvice.setDanDueDate(dueDate);

			demandAdvice.setUserId(user.getUserId());

			rpDAO.insertDANDetails(demandAdvice, connection);

			emailMessage = emailMessage + "DAN No : " + demandAdvice.getDanNo() + "\n";

			double guaranteeAmount = rpProcessor.calculateGuaranteeFee(application);
			Log.log(4, "application DAO", "generateDanForEnhance", "guaranteeAmount" + guaranteeAmount);
			java.util.Date guarStartDate = rpDAO.getGuarStartDate(application);
			Log.log(4, "application DAO", "generateDanForEnhance", "guarStartDate" + guarStartDate);

			if (guarStartDate != null) {
				ClaimsProcessor cpProcessor = new ClaimsProcessor();
				HashMap wcDetail = cpProcessor.getWorkingCapital(application.getCgpan());
				Integer applicationTenureObj = (Integer) wcDetail.get("WC_TENURE");
				int tenorMonths = applicationTenureObj.intValue();
				Log.log(4, "application DAO", "generateDanForEnhance", "tenorMonths" + tenorMonths);

				Calendar calendar = Calendar.getInstance();
				calendar.setTime(guarStartDate);

				calendar.add(2, tenorMonths);

				Calendar sysdate = Calendar.getInstance();
				sysdate.setTime(new java.util.Date());

				long monthDiff = DateHelper.getMonthDifference(sysdate, calendar);

				if (monthDiff <= 0L) {
					return;
				}

				Log.log(4, "application DAO", "generateDanForEnhance", "monthDiff" + monthDiff);

				double monthDiffDouble = new Long(monthDiff).doubleValue() / 12.0D;
				double tenorDouble = new Integer(tenorMonths).doubleValue() / 12.0D;

				Log.log(4, "application DAO", "generateDanForEnhance", "monthDiffDouble" + monthDiffDouble);
				Log.log(4, "application DAO", "generateDanForEnhance", "tenorDouble" + tenorDouble);

				guaranteeAmount *= Math.ceil(monthDiffDouble) / Math.ceil(tenorDouble);

				guaranteeAmount = Math.round(guaranteeAmount);

				// System.out.println("guaranteeAmount:" + guaranteeAmount);
				Log.log(4, "application DAO", "generateDanForEnhance", "guaranteeAmount" + guaranteeAmount);
			}

			Log.log(4, "application DAO", "generateDanForEnhance", "guaranteeAmount" + guaranteeAmount);

			demandAdvice.setAmountRaised(Math.round(guaranteeAmount));

			rpDAO.insertPANDetailsForDAN(demandAdvice, connection);

			emailMessage = emailMessage + "CGPAN - " + demandAdvice.getCgpan() + ", GuaranteeAmount - "
					+ demandAdvice.getAmountRaised() + "\n";
			try {
				users = administrator.getAllUsers(application.getMliID());
			} catch (NoUserFoundException exception) {
				Log.log(3, "application DAO", "generateDanForEnhance",
						"Exception getting user details for the MLI. Error=" + exception.getMessage());
			} catch (DatabaseException exception) {
				Log.log(3, "application DAO", "generateDanForEnhance",
						"Exception getting user details for the MLI. Error=" + exception.getMessage());
			}

			mailer = new Mailer();

			mliInfo = registrationDAO.getMemberDetails(application.getMliID().substring(0, 4),
					application.getMliID().substring(4, 8), application.getMliID().substring(8, 12));

			mailPrivelege = mliInfo.getMail();
			emailPrivelege = mliInfo.getEmail();
			hardCopyPrivelege = mliInfo.getHardCopy();

			Log.log(5, "application DAO", "generateDanForEnhance", "Getting Email Id for MLI id completed");
			int userSize = users.size();
			emailIds = new ArrayList();
			for (int j = 0; j < userSize; j++) {
				mailUser = (User) users.get(j);
				emailIds.add(mailUser.getUserId());
			}

			if (emailIds != null) {
				subject = "New Demand Advice(" + cgdanNo + ") generated";
				Message message = new Message(emailIds, null, null, subject, emailMessage);

				message.setFrom(fromEmail);

				administrator.sendMail(message);
			}

			connection.commit();
		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "getCountForDanGen", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(4, "ApplicationDAO", "getCountForDanGen", ignore.getMessage());
			}

			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}
	}

	public double getBalanceApprovedAmt(Application application) throws DatabaseException {
		RiskManagementProcessor rmProcessor = new RiskManagementProcessor();
		String subSchemeName = rmProcessor.getSubScheme(application);

		Log.log(4, "ApplicationDAO", "submitApp",
				"state" + application.getBorrowerDetails().getSsiDetails().getState());
		Log.log(4, "ApplicationDAO", "submitApp",
				"industry nature" + application.getBorrowerDetails().getSsiDetails().getIndustryNature());
		Log.log(4, "ApplicationDAO", "submitApp",
				"Gender" + application.getBorrowerDetails().getSsiDetails().getCpGender());
		Log.log(4, "ApplicationDAO", "submitApp", "mli ID" + application.getMliID());
		Log.log(4, "ApplicationDAO", "submitApp",
				"Social Cat" + application.getBorrowerDetails().getSsiDetails().getSocialCategory());

		double approvedAmt = 0.0D;
		double balanceAppAmt = 0.0D;
		double exposureAmount = 0.0D;

		Connection connection = DBConnection.getConnection();
		CallableStatement approvedAmount = null;
		try {
			approvedAmount = connection.prepareCall("{call funcGetApprovedAmt(?,?,?,?)}");

			approvedAmount.registerOutParameter(1, 4);
			// System.out.println("getBalanceApprovedAmt
			// getBorrowerRefNo"+application.getBorrowerDetails()
			// .getSsiDetails().getBorrowerRefNo());
			approvedAmount.setDouble(2, application.getBorrowerDetails().getSsiDetails().getBorrowerRefNo());
			approvedAmount.registerOutParameter(3, 8);
			approvedAmount.registerOutParameter(4, 12);

			approvedAmount.executeQuery();

			int approvedAmountValue = approvedAmount.getInt(1);

			Log.log(5, "ApplicationDAO", "submitApp", "SSi Details Approved Amount result :" + approvedAmountValue);

			if (approvedAmountValue == 1) {
				String error = approvedAmount.getString(4);

				approvedAmount.close();
				approvedAmount = null;

				Log.log(2, "ApplicationDAO", "submitApp", "SSI Detail Approved Amount Exception :" + error);

				throw new DatabaseException(error);
			}
			approvedAmt = approvedAmount.getInt(3);
			// System.out.println("getBalanceApprovedAmt approvedAmt"+approvedAmt);
			approvedAmount.close();
			approvedAmount = null;
			double rsfMaximumAmount;
			if (!subSchemeName.equals("GLOBAL")) {
				SubSchemeValues subSchemeValues = rmProcessor.getSubSchemeValues(subSchemeName);

				if (subSchemeValues != null) {
					exposureAmount = subSchemeValues.getMaxBorrowerExposureAmount();
					// System.out.println("getBalanceApprovedAmt exposureAmount if
					// "+exposureAmount);

					Log.log(5, "ApplicationDAO", "submitApp", "exposureAmount :" + exposureAmount);
				}

			} else {
				Administrator admin = new Administrator();
				ParameterMaster param = admin.getParameter();
				exposureAmount = param.getMaxApprovedAmt();
				// System.out.println("getBalanceApprovedAmt exposureAmount else
				// "+exposureAmount);
				Log.log(4, "ApplicationDAO", "submitApp", "exposureAmount :" + exposureAmount);
				rsfMaximumAmount = param.getMaxRsfApprovedAmt();
			}

			if (approvedAmt >= exposureAmount) {
				throw new DatabaseException(
						"Borrower has crossed his exposure limit.Hence ineligible to submit a new application");
			}

			balanceAppAmt = exposureAmount - approvedAmt;
			// System.out.println("getBalanceApprovedAmt balanceAppAmt "+balanceAppAmt);
			Log.log(4, "ApplicationDAO", "submitApp", "balanceAppAmt :" + balanceAppAmt);
		} catch (SQLException se) {
			throw new DatabaseException(se.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		return balanceAppAmt;
	}

	public double getBalanceRsfApprovedAmt(Application application) throws DatabaseException {
		RiskManagementProcessor rmProcessor = new RiskManagementProcessor();
		String subSchemeName = rmProcessor.getSubScheme(application);

		Log.log(4, "ApplicationDAO", "getBalanceRsfApprovedAmt",
				"state" + application.getBorrowerDetails().getSsiDetails().getState());
		Log.log(4, "ApplicationDAO", "getBalanceRsfApprovedAmt",
				"industry nature" + application.getBorrowerDetails().getSsiDetails().getIndustryNature());
		Log.log(4, "ApplicationDAO", "getBalanceRsfApprovedAmt",
				"Gender" + application.getBorrowerDetails().getSsiDetails().getCpGender());
		Log.log(4, "ApplicationDAO", "getBalanceRsfApprovedAmt", "mli ID" + application.getMliID());
		Log.log(4, "ApplicationDAO", "getBalanceRsfApprovedAmt",
				"Social Cat" + application.getBorrowerDetails().getSsiDetails().getSocialCategory());

		double approvedAmt = 0.0D;
		double balanceAppAmt = 0.0D;
		double exposureAmount = 0.0D;

		Connection connection = DBConnection.getConnection();
		CallableStatement approvedAmount = null;
		try {
			approvedAmount = connection.prepareCall("{?=call funcGetApprovedAmt(?,?,?)}");

			approvedAmount.registerOutParameter(1, 4);
			approvedAmount.setDouble(2, application.getBorrowerDetails().getSsiDetails().getBorrowerRefNo());

			approvedAmount.registerOutParameter(3, 8);
			approvedAmount.registerOutParameter(4, 12);

			approvedAmount.executeQuery();

			int approvedAmountValue = approvedAmount.getInt(1);

			Log.log(5, "ApplicationDAO", "submitApp", "SSi Details Approved Amount result :" + approvedAmountValue);

			if (approvedAmountValue == 1) {
				String error = approvedAmount.getString(4);

				approvedAmount.close();
				approvedAmount = null;

				Log.log(2, "ApplicationDAO", "submitApp", "SSI Detail Approved Amount Exception :" + error);

				throw new DatabaseException(error);
			}
			approvedAmt = approvedAmount.getInt(3);

			approvedAmount.close();
			approvedAmount = null;

			if (!subSchemeName.equals("GLOBAL")) {
				SubSchemeValues subSchemeValues = rmProcessor.getSubSchemeValues(subSchemeName);

				if (subSchemeValues != null) {
					exposureAmount = subSchemeValues.getMaxBorrowerExposureAmount();

					Log.log(5, "ApplicationDAO", "submitApp", "exposureAmount :" + exposureAmount);
				}

			} else {
				Administrator admin = new Administrator();
				ParameterMaster param = admin.getParameter();
				exposureAmount = param.getMaxRsfApprovedAmt();

				Log.log(4, "ApplicationDAO", "submitApp", "exposureAmount :" + exposureAmount);
			}

			balanceAppAmt = exposureAmount - approvedAmt;

			Log.log(4, "ApplicationDAO", "submitApp", "balanceAppAmt :" + balanceAppAmt);
		} catch (SQLException se) {
			throw new DatabaseException(se.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		return balanceAppAmt;
	}

	public double getBalanceRsf2ApprovedAmt(Application application) throws DatabaseException {
		RiskManagementProcessor rmProcessor = new RiskManagementProcessor();
		String subSchemeName = rmProcessor.getSubScheme(application);

		Log.log(4, "ApplicationDAO", "getBalanceRsfApprovedAmt",
				"state" + application.getBorrowerDetails().getSsiDetails().getState());
		Log.log(4, "ApplicationDAO", "getBalanceRsfApprovedAmt",
				"industry nature" + application.getBorrowerDetails().getSsiDetails().getIndustryNature());
		Log.log(4, "ApplicationDAO", "getBalanceRsfApprovedAmt",
				"Gender" + application.getBorrowerDetails().getSsiDetails().getCpGender());
		Log.log(4, "ApplicationDAO", "getBalanceRsfApprovedAmt", "mli ID" + application.getMliID());
		Log.log(4, "ApplicationDAO", "getBalanceRsfApprovedAmt",
				"Social Cat" + application.getBorrowerDetails().getSsiDetails().getSocialCategory());

		double approvedAmt = 0.0D;
		double balanceAppAmt = 0.0D;
		double exposureAmount = 0.0D;

		Connection connection = DBConnection.getConnection();
		CallableStatement approvedAmount = null;
		try {
			approvedAmount = connection.prepareCall("{?=call funcGetApprovedAmt(?,?,?)}");

			approvedAmount.registerOutParameter(1, 4);
			approvedAmount.setDouble(2, application.getBorrowerDetails().getSsiDetails().getBorrowerRefNo());

			approvedAmount.registerOutParameter(3, 8);
			approvedAmount.registerOutParameter(4, 12);

			approvedAmount.executeQuery();

			int approvedAmountValue = approvedAmount.getInt(1);

			Log.log(5, "ApplicationDAO", "submitApp", "SSi Details Approved Amount result :" + approvedAmountValue);

			if (approvedAmountValue == 1) {
				String error = approvedAmount.getString(4);

				approvedAmount.close();
				approvedAmount = null;

				Log.log(2, "ApplicationDAO", "submitApp", "SSI Detail Approved Amount Exception :" + error);

				throw new DatabaseException(error);
			}
			approvedAmt = approvedAmount.getInt(3);

			approvedAmount.close();
			approvedAmount = null;

			if (!subSchemeName.equals("GLOBAL")) {
				SubSchemeValues subSchemeValues = rmProcessor.getSubSchemeValues(subSchemeName);

				if (subSchemeValues != null) {
					exposureAmount = subSchemeValues.getMaxBorrowerExposureAmount();

					Log.log(5, "ApplicationDAO", "submitApp", "exposureAmount :" + exposureAmount);
				}

			} else {
				Administrator admin = new Administrator();
				ParameterMaster param = admin.getParameter();
				exposureAmount = param.getMaxRsf2ApprovedAmt();

				Log.log(4, "ApplicationDAO", "submitApp", "exposureAmount :" + exposureAmount);
			}

			balanceAppAmt = exposureAmount - approvedAmt;

			Log.log(4, "ApplicationDAO", "submitApp", "balanceAppAmt :" + balanceAppAmt);
		} catch (SQLException se) {
			throw new DatabaseException(se.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		return balanceAppAmt;
	}

	public void updateWcTenure(Application application, Connection connection) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "updateWcTenure", "Entered");
		try {
			CallableStatement updateTenure = connection.prepareCall("{?=call funcUpdateWCTenure(?,?)}");

			updateTenure.registerOutParameter(1, 4);
			updateTenure.registerOutParameter(3, 12);

			updateTenure.setString(2, application.getAppRefNo());

			Log.log(4, "ApplicationDAO", "updateWcTenure", "app ref no :" + application.getAppRefNo());

			updateTenure.executeQuery();
			int functionReturnValue = updateTenure.getInt(1);
			Log.log(4, "ApplicationDAO", "updateWcTenure", "updateTenure :" + functionReturnValue);

			if (functionReturnValue == 1) {
				String error = updateTenure.getString(3);

				updateTenure.close();
				updateTenure = null;

				connection.rollback();

				Log.log(2, "ApplicationDAO", "updateWcTenure", "updateAppReference Exception" + error);
				throw new DatabaseException(error);
			}

			updateTenure.close();
			updateTenure = null;
		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "updateWcTenure", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(4, "ApplicationDAO", "updateWcTenure", ignore.getMessage());
			}

			throw new DatabaseException(sqlException.getMessage());
		}
	}

	public void updateAppCgpanReference(Application application) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "updateAppCgpanReference", "Entered");
		Connection connection = DBConnection.getConnection();
		try {
			CallableStatement updateCgpanRef = connection.prepareCall("{?=call funcUpdateAppCgpanRef(?,?)}");

			updateCgpanRef.registerOutParameter(1, 4);
			updateCgpanRef.registerOutParameter(3, 12);

			updateCgpanRef.setString(2, application.getAppRefNo());

			Log.log(4, "ApplicationDAO", "updateAppCgpanReference", "app ref no :" + application.getAppRefNo());

			updateCgpanRef.executeQuery();
			int functionReturnValue = updateCgpanRef.getInt(1);
			Log.log(4, "ApplicationDAO", "updateAppCgpanReference", "updateTenure :" + functionReturnValue);

			if (functionReturnValue == 1) {
				String error = updateCgpanRef.getString(3);

				updateCgpanRef.close();
				updateCgpanRef = null;

				connection.rollback();

				Log.log(2, "ApplicationDAO", "updateAppCgpanReference", "updateAppReference Exception" + error);
				throw new DatabaseException(error);
			}

			updateCgpanRef.close();
			updateCgpanRef = null;
		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "updateAppCgpanReference", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(4, "ApplicationDAO", "updateAppCgpanReference", ignore.getMessage());
			}

			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(4, "ApplicationDAO", "updateAppCgpanReference", "Exited");
	}

	/*
	 * 
	 * */
	public ArrayList getBankAppRefNumbers(String memberId) throws DatabaseException, Exception {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList numbers = new ArrayList();
		String query = " SELECT DISTINCT APP_BANK_APP_REF_NO FROM (SELECT APP_BANK_APP_REF_NO FROM INTERFACE_UPLOAD_NEW "
				+ " WHERE APP_BANK_APP_REF_NO IS NOT NULL AND MEM_BNK_ID||MEM_ZNE_ID||MEM_BRN_ID = ?)";

		try {
			conn = DBConnection.getConnection();
			stmt = conn.prepareStatement(query);
			stmt.setString(1, memberId);
			rs = stmt.executeQuery();
			while (rs.next()) {
				String appRefNo = rs.getString(1);
				numbers.add(appRefNo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Problem in fetching bank app reference number.");
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException("Problem in fetching bank app reference number.");
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				DBConnection.freeConnection(conn);
			}
		}
		return numbers;
	}

	// DKR insertCSVFileData both insertXLSFileData DONE
	public HashMap uploadAppsIntoInterface(ArrayList<Application> apps) throws SQLException, Exception {

		HashMap map = new HashMap();
		ArrayList clearapps = new ArrayList();
		ArrayList dupapps = new ArrayList();
		Connection conn = null;
		CallableStatement stmt = null;
		ArrayList validappsSummaryDetails = new ArrayList();
		if (conn == null) {
			conn = DBConnection.getConnection();
		}
		/*
		 * Code for checking apps with existing apps in interface. And add them in
		 * dupapps.
		 */
		/* db function to check duplicate application. */
		for (Application app : apps) {
			System.out.println(
					"apps.size().......................................FROM uploadAppsIntoInterface DAO>>>>>>>>>>>>>>>>>>>"
							+ apps.size());
			boolean isDuplicate = false;
			if (isDuplicate) {
				dupapps.add(app);
			} else {
				/* db function to insert new app into interface. */
				try {
					stmt = conn
							/*
							 * .prepareCall("{?=call FUNCINTERFACEUPLOADXLSNEW(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
							 * +
							 * "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
							 * +
							 * "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
							 * + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
							 */
							.prepareCall(
									"{?=call FUNCINTERFACEUPLOADXLSNEW(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
											+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
											+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
											+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
											+ "?,?,?,?,?,?,?,?,?,?)}");

					stmt.registerOutParameter(1, Types.INTEGER);
					stmt.setString(2, app.getBankId());
					stmt.setString(3, app.getZoneId());
					stmt.setString(4, app.getBranchId());
					stmt.setString(5, app.getLoanType());
					stmt.setString(6, app.getMliRefNo());
					stmt.setString(7, app.getMliBranchName());
					stmt.setString(8, app.getMliBranchCode());
					stmt.setString(9, app.getBorrowerDetails().getPreviouslyCovered());
					// stmt.setDouble(10, app.getBorrowerDetails().getOsAmt());
					// stmt.setString(11, app.getBorrowerDetails().getNpa());
					// stmt.setString(12,
					// app.getBorrowerDetails().getPreviouslyCovered());
					// stmt.setString(13,
					// app.getBorrowerDetails().getSsiDetails().getCgbid());
					stmt.setString(10, app.getBorrowerDetails().getSsiDetails().getConstitution());
					stmt.setString(11, app.getBorrowerDetails().getSsiDetails().getSsiType());
					stmt.setString(12, app.getBorrowerDetails().getSsiDetails().getSsiName());
					stmt.setString(13, app.getBorrowerDetails().getSsiDetails().getAddress());
					stmt.setString(14, app.getBorrowerDetails().getSsiDetails().getState());
					stmt.setString(15, app.getBorrowerDetails().getSsiDetails().getDistrict());
					stmt.setString(16, app.getBorrowerDetails().getSsiDetails().getCity());
					stmt.setString(17, app.getBorrowerDetails().getSsiDetails().getPincode());
					// stmt.setDouble(18,
					// app.getProjectOutlayDetails().getProjectOutlay());
					stmt.setString(18, app.getBorrowerDetails().getSsiDetails().getSsiITPan());
					stmt.setString(19, app.getBorrowerDetails().getSsiDetails().getRegNo());
					stmt.setString(20, app.getBorrowerDetails().getSsiDetails().getIndustryNature());
					stmt.setString(21, app.getBorrowerDetails().getSsiDetails().getIndustrySector());
					stmt.setString(22, app.getBorrowerDetails().getSsiDetails().getActivityType());
					stmt.setInt(23, app.getBorrowerDetails().getSsiDetails().getEmployeeNos());
					stmt.setDouble(24, app.getBorrowerDetails().getSsiDetails().getProjectedSalesTurnover());
					stmt.setDouble(25, app.getBorrowerDetails().getSsiDetails().getProjectedExports());
					stmt.setString(26, app.getBorrowerDetails().getSsiDetails().getCpTitle());
					stmt.setString(27, app.getBorrowerDetails().getSsiDetails().getCpFirstName());
					stmt.setString(28, app.getBorrowerDetails().getSsiDetails().getCpMiddleName());
					stmt.setString(29, app.getBorrowerDetails().getSsiDetails().getCpLastName());
					stmt.setString(30, app.getBorrowerDetails().getSsiDetails().getCpParTitle());
					stmt.setString(31, app.getBorrowerDetails().getSsiDetails().getCpParFirstName());
					stmt.setString(32, app.getBorrowerDetails().getSsiDetails().getCpParMiddleName());
					stmt.setString(33, app.getBorrowerDetails().getSsiDetails().getCpParLastName());
					stmt.setString(34, app.getBorrowerDetails().getSsiDetails().getCpGender());
					stmt.setString(35, app.getBorrowerDetails().getSsiDetails().getCpITPAN());
					stmt.setString(36, app.getBorrowerDetails().getSsiDetails().getReligion());
					if (app.getBorrowerDetails().getSsiDetails().getCpDOB() != null) {
						stmt.setDate(37,
								new java.sql.Date(app.getBorrowerDetails().getSsiDetails().getCpDOB().getTime()));
					} else {
						stmt.setNull(37, Types.DATE);
					}
					// stmt.setString(43,
					// app.getBorrowerDetails().getSsiDetails().getSocialCategory());
					stmt.setString(38, app.getBorrowerDetails().getSsiDetails().getCpLegalID());
					stmt.setString(39, app.getBorrowerDetails().getSsiDetails().getCpLegalIdValue());
					stmt.setString(40, app.getBorrowerDetails().getSsiDetails().getFirstName());
					if (app.getBorrowerDetails().getSsiDetails().getFirstDOB() != null) {
						stmt.setDate(41,
								new java.sql.Date(app.getBorrowerDetails().getSsiDetails().getFirstDOB().getTime()));
					} else {
						stmt.setNull(41, Types.DATE);
					}
					stmt.setString(42, app.getBorrowerDetails().getSsiDetails().getFirstItpan());
					stmt.setString(43, app.getBorrowerDetails().getSsiDetails().getSecondName());
					if (app.getBorrowerDetails().getSsiDetails().getSecondDOB() != null) {
						stmt.setDate(44,
								new java.sql.Date(app.getBorrowerDetails().getSsiDetails().getSecondDOB().getTime()));
					} else {
						stmt.setNull(44, Types.DATE);
					}
					stmt.setString(45, app.getBorrowerDetails().getSsiDetails().getSecondItpan());
					stmt.setString(46, app.getBorrowerDetails().getSsiDetails().getThirdName());
					if (app.getBorrowerDetails().getSsiDetails().getThirdDOB() != null) {
						stmt.setDate(47,
								new java.sql.Date(app.getBorrowerDetails().getSsiDetails().getThirdDOB().getTime()));
					} else {
						stmt.setNull(47, Types.DATE);
					}
					stmt.setString(48, app.getBorrowerDetails().getSsiDetails().getThirdItpan());
					stmt.setString(49, app.getPrevSSI());
					stmt.setString(50, app.getBorrowerDetails().getSsiDetails().getWomenOperated());
					stmt.setString(51, app.getBorrowerDetails().getSsiDetails().getMSE());
					stmt.setString(52, app.getBorrowerDetails().getSsiDetails().getEnterprise());
					stmt.setString(53, app.getProjectOutlayDetails().getCollateralSecurityTaken());
					stmt.setString(54, app.getProjectOutlayDetails().getThirdPartyGuaranteeTaken());
					stmt.setString(55, app.getDcHandicrafts());
					stmt.setString(56, app.getDcHandicraftsStatus());
					stmt.setString(57, app.getIcardNo());
					if (app.getIcardIssueDate() != null) {
						stmt.setDate(58, new java.sql.Date(app.getIcardIssueDate().getTime()));// null pointer
					} else {
						stmt.setNull(58, Types.DATE);
					}
					stmt.setString(59, app.getBorrowerDetails().getSsiDetails().getPhysicallyHandicapped());
					stmt.setString(60, app.getJointFinance());
					stmt.setString(61, app.getJointcgpan());
					stmt.setString(62, app.getDcHandlooms());
					stmt.setString(63, app.getWeaverCreditScheme());
					stmt.setString(64, app.getHandloomchk());
					stmt.setString(65, app.getHandloomSchName());
					stmt.setString(66, app.getInternalRating());
					stmt.setString(67, app.getInternalratingProposal());
					stmt.setString(68, app.getInvestmentGrade());
					stmt.setString(69, app.getSubsidyType());
					stmt.setString(70, app.getSubsidyOther());

					// stmt.setString(70,
					// app.getProjectOutlayDetails().getPrimarySecurityDetails().getLandParticulars());
					// stmt.setDouble(71,
					// app.getProjectOutlayDetails().getPrimarySecurityDetails().getLandValue());
					// stmt.setString(72,
					// app.getProjectOutlayDetails().getPrimarySecurityDetails().getBldgParticulars());
					// stmt.setDouble(73,
					// app.getProjectOutlayDetails().getPrimarySecurityDetails().getBldgValue());
					// stmt.setString(74,
					// app.getProjectOutlayDetails().getPrimarySecurityDetails().getMachineParticulars());
					// stmt.setDouble(75,
					// app.getProjectOutlayDetails().getPrimarySecurityDetails().getMachineValue());
					// stmt.setString(76,
					// app.getProjectOutlayDetails().getPrimarySecurityDetails().getOthersParticulars());
					// stmt.setDouble(77,
					// app.getProjectOutlayDetails().getPrimarySecurityDetails().getOthersValue());
					// stmt.setString(78,
					// app.getProjectOutlayDetails().getPrimarySecurityDetails().getCurrentAssetsParticulars());
					// stmt.setDouble(79,
					// app.getProjectOutlayDetails().getPrimarySecurityDetails().getCurrentAssetsValue());

					stmt.setDouble(71, app.getTermLoan().getAmountSanctioned());
					stmt.setDouble(72, app.getProjectOutlayDetails().getTcPromoterContribution());
					stmt.setDouble(73, app.getProjectOutlayDetails().getTcSubsidyOrEquity());
					stmt.setDouble(74, app.getProjectOutlayDetails().getTcOthers());
					if (app.getTermLoan().getAmountSanctionedDate() != null) {
						stmt.setDate(75, new java.sql.Date(app.getTermLoan().getAmountSanctionedDate().getTime()));
					} else {
						stmt.setNull(75, Types.DATE);
					}
					stmt.setDouble(76, app.getTermLoan().getCreditGuaranteed());

					System.out.println("hybridSec_Flag.Y..from Dao.. app.getTermLoan().getCreditGuaranteed()........."
							+ app.getTermLoan().getCreditGuaranteed());

					stmt.setDouble(77, app.getTermLoan().getAmtDisbursed());
					if (app.getTermLoan().getFirstDisbursementDate() != null) {
						stmt.setDate(78, new java.sql.Date(app.getTermLoan().getFirstDisbursementDate().getTime()));
					} else {
						stmt.setNull(78, Types.DATE);
					}
					if (app.getTermLoan().getFinalDisbursementDate() != null) {
						stmt.setDate(79, new java.sql.Date(app.getTermLoan().getFinalDisbursementDate().getTime()));
					} else {
						stmt.setNull(79, Types.DATE);
					}
					stmt.setString(80, app.getTermLoan().getTypeOfPLR());
					stmt.setDouble(81, app.getTermLoan().getPlr());
					stmt.setString(82, app.getTermLoan().getInterestType());
					stmt.setDouble(83, app.getTermLoan().getInterestRate());
					stmt.setInt(84, app.getTermLoan().getTenure());
					stmt.setInt(85, app.getTermLoan().getRepaymentMoratorium());
					stmt.setInt(86, app.getTermLoan().getInterestMoratorium());
					stmt.setString(87, String.valueOf(app.getTermLoan().getPeriodicity()));
					stmt.setInt(88, app.getTermLoan().getNoOfInstallments());
					if (app.getTermLoan().getFirstInstallmentDueDate() != null) {
						stmt.setDate(89, new java.sql.Date(app.getTermLoan().getFirstInstallmentDueDate().getTime()));
					} else {
						stmt.setNull(89, Types.DATE);
					}
					stmt.setDouble(90, app.getTermLoan().getPplOS());
					if (app.getTermLoan().getPplOsAsOnDate() != null) {
						stmt.setDate(91, new java.sql.Date(app.getTermLoan().getPplOsAsOnDate().getTime()));
					} else {
						stmt.setNull(91, Types.DATE);
					}

					// WC
					stmt.setDouble(92, app.getWc().getFundBasedLimitSanctioned());
					stmt.setDouble(93, app.getWc().getCreditFundBased());
					stmt.setDouble(94, app.getWc().getNonFundBasedLimitSanctioned());
					stmt.setDouble(95, app.getWc().getCreditNonFundBased());
					stmt.setString(96, app.getWc().getIsTLMarginMoney());
					stmt.setDouble(97, app.getProjectOutlayDetails().getWcPromoterContribution());
					stmt.setDouble(98, app.getProjectOutlayDetails().getWcSubsidyOrEquity());
					stmt.setDouble(99, app.getProjectOutlayDetails().getWcOthers());
					stmt.setString(100, app.getWc().getWcTypeOfPLR());
					stmt.setDouble(101, app.getWc().getWcPlr());
					stmt.setString(102, app.getWc().getWcInterestType());
					stmt.setDouble(103, app.getWc().getWcInterestRate());
					if (app.getWc().getLimitFundBasedSanctionedDate() != null) {
						stmt.setDate(104, new java.sql.Date(app.getWc().getLimitFundBasedSanctionedDate().getTime()));
					} else {
						stmt.setNull(104, Types.DATE);
					}
					stmt.setDouble(105, app.getWc().getLimitNonFundBasedCommission());
					if (app.getWc().getLimitNonFundBasedSanctionedDate() != null) {
						stmt.setDate(106,
								new java.sql.Date(app.getWc().getLimitNonFundBasedSanctionedDate().getTime()));
					} else {
						stmt.setNull(106, Types.DATE);
					}
					stmt.setDouble(107, app.getWc().getOsFundBasedPpl());
					if (app.getWc().getOsFundBasedAsOnDate() != null) {
						stmt.setDate(108, new java.sql.Date(app.getWc().getOsFundBasedAsOnDate().getTime()));
					} else {
						stmt.setNull(108, Types.DATE);
					}
					stmt.setDouble(109, app.getWc().getOsNonFundBasedPpl());
					if (app.getWc().getOsNonFundBasedAsOnDate() != null) {
						stmt.setDate(110, new java.sql.Date(app.getWc().getOsNonFundBasedAsOnDate().getTime()));
					} else {
						stmt.setNull(110, Types.DATE);
					}
					stmt.setDouble(111, app.getWc().getWcDisbAmt());
					if (app.getWc().getWcFirstDisbDt() != null) {
						stmt.setDate(112, new java.sql.Date(app.getWc().getWcFirstDisbDt().getTime()));
					} else {
						stmt.setNull(112, Types.DATE);
					}
					if (app.getWc().getWcFinalDisbDt() != null) {
						stmt.setDate(113, new java.sql.Date(app.getWc().getWcFinalDisbDt().getTime()));
					} else {
						stmt.setNull(113, Types.DATE);
					}
					if (app.getAppExpiryDate() != null) {
						stmt.setDate(114, new java.sql.Date(app.getAppExpiryDate().getTime()));
					} else {
						stmt.setNull(114, Types.DATE);
					}
					stmt.setDouble(115, app.getSecuritization().getSpreadOverPLR());
					stmt.setString(116, app.getSecuritization().getPplRepaymentInEqual());
					stmt.setDouble(117, app.getSecuritization().getTangibleNetWorth());
					stmt.setDouble(118, app.getSecuritization().getFixedACR());
					stmt.setDouble(119, app.getSecuritization().getCurrentRatio());
					stmt.setDouble(120, app.getSecuritization().getMinimumDSCR());
					stmt.setDouble(121, app.getSecuritization().getAvgDSCR());
					stmt.setString(122, app.getProjectOutlayDetails().getIsPrimarySecurity());
					stmt.setString(123, app.getRemarks());
					stmt.setString(124, app.getBorrowerDetails().getSsiDetails().getConditionAccepted());
					stmt.setString(125, app.getUserId());
					stmt.setString(126, (String) app.getUdyogAdharNo());
					stmt.setString(127, app.getBankAcNo());

					stmt.registerOutParameter(128, Types.VARCHAR);
					stmt.setString(129, app.getStateCode());
					stmt.setString(130, app.getGstNo());

					/* add 30 col by DKR */
					// Added by Dkr collatral
					stmt.setInt(131, 1);// Integer.parseInt(app.getExposureFbId())); // NEED TO REMOVE
					stmt.setString(132, app.getHybridSecurity());
					stmt.setDouble(133, app.getMovCollateratlSecurityAmt());
					stmt.setDouble(134, app.getImmovCollateratlSecurityAmt());
					System.out.println(
							app.getTotalMIcollatSecAmt() + "<<<<<<<<<<<<<<<<<<<<<<<<<app.getTotalMIcollatSecAmt()");
					stmt.setDouble(135, app.getTotalMIcollatSecAmt());
					System.out.println(app.getProMobileNo() + "<<<<<<<<<<<<<<<<<<<<<app.getProMobileNo()()");
					stmt.setLong(136, app.getProMobileNo());
					// System.out.println("Integer.parseInt(app.getExposureFbId())"+Integer.parseInt(app.getExposureFbId())+"......................app.getHybridSecurity()......"+app.getHybridSecurity());

					stmt.setString(137, app.getPromDirDefaltFlg());
					stmt.setInt(138, app.getCredBureKeyPromScor());
					stmt.setInt(139, app.getCredBurePromScor2());
					stmt.setInt(140, app.getCredBurePromScor3());
					stmt.setInt(141, app.getCredBurePromScor4());
					stmt.setInt(142, app.getCredBurePromScor5());
					stmt.setString(143, app.getCredBureName1());
					stmt.setString(144, app.getCredBureName2());
					stmt.setString(145, app.getCredBureName3());
					stmt.setString(146, app.getCredBureName4());
					stmt.setString(147, app.getCredBureName5());
					stmt.setInt(148, app.getCibilFirmMsmeRank());
					stmt.setInt(149, app.getExpCommerScor());
					stmt.setFloat(150, app.getPromBorrNetWorth());
					stmt.setInt(151, app.getPromContribution());
					stmt.setString(152, app.getPromGAssoNPA1YrFlg());
					stmt.setInt(153, app.getPromBussExpYr());
					stmt.setFloat(154, app.getSalesRevenue());
					stmt.setFloat(155, app.getTaxPBIT());
					stmt.setFloat(156, app.getTaxCurrentProvisionAmt());
					stmt.setFloat(157, app.getTotCurrentAssets());
					stmt.setFloat(158, app.getTotCurrentLiability());
					stmt.setFloat(159, app.getTotTermLiability());
					stmt.setFloat(160, app.getExuityCapital());
					stmt.setFloat(161, app.getPreferenceCapital());
					stmt.setFloat(162, app.getReservesSurplus());
					stmt.setFloat(163, app.getRepaymentDueNyrAmt());
					stmt.setFloat(164, app.getInterestPayment());

					stmt.setFloat(165, app.getOpratIncome());
					stmt.setFloat(166, app.getProfAftTax());
					stmt.setFloat(167, app.getNetworth());
					stmt.setFloat(168, app.getDebitEqtRatioUnt());
					stmt.setFloat(169, app.getDebitSrvCoverageRatioTl());
					stmt.setFloat(170, app.getCurrentRatioWc());
					stmt.setFloat(171, app.getDebitEqtRatio());
					stmt.setFloat(172, app.getDebitSrvCoverageRatio());
					stmt.setFloat(173, app.getCurrentRatios());
					stmt.setInt(174, app.getCreditBureauChiefPromScor());
					stmt.setFloat(175, app.getTotalAssets());
					stmt.setString(176, app.getExistGreenFldUnitType());
					// ******************************************END*************************************************

					stmt.execute();

					int errorcode = stmt.getInt(1);
					// System.out.println("error code:" + errorcode);
					String error = stmt.getString(128);
					if (errorcode == 0) {
						conn.commit();
					} else {
						throw new DatabaseException(error);
					}
				} catch (SQLException e) {
					try {
						conn.rollback();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					e.printStackTrace();
					throw e;
				} catch (Exception e) {
					try {
						conn.rollback();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					e.printStackTrace();
					throw e;
				} finally {
					try {
						stmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					DBConnection.freeConnection(conn);
				}
				// System.out.println("uploadAppsIntoInterface app"+app);
				clearapps.add(app);
			}
			if (app.getLoanType() != null && app.getLoanType().equalsIgnoreCase("TC")) {

				validappsSummaryDetails.add("BANK APPLICATION REFERENCE NUMBER:-" + app.getMliRefNo() + " BRANCH NAME:-"
						+ app.getMliBranchName() + " SSI NAME:-" + app.getBorrowerDetails().getSsiDetails().getSsiName()
						+ " TL_SANCTIONED AMOUNT:-" + app.getTermLoan().getAmountSanctioned()
						+ " TL_CREDIT_TO_GUARANTEE:- " + app.getTermLoan().getAmountSanctioned());
			} else if (app.getLoanType() != null && app.getLoanType().equalsIgnoreCase("WC")) {

				validappsSummaryDetails.add("BANK APPLICATION REFERENCE NUMBER:-" + app.getMliRefNo() + " BRANCH NAME:-"
						+ app.getMliBranchName() + " SSI NAME:-" + app.getBorrowerDetails().getSsiDetails().getSsiName()
						+ " WC_FB_LIMIT_SANCTIONED:-" + app.getTermLoan().getAmountSanctioned()
						+ " WC_FB_CREDIT_TO_GUARANTEE:- " + app.getTermLoan().getAmountSanctioned());
			}
		}
		map.put("CLEARAPPS", clearapps);
		map.put("DUPAPPS", dupapps);
		map.put("VALIDAPPSUMMARYDETAIL", validappsSummaryDetails);
		return map;
	}

	// END BY DKR

	// 24-02-2016 start
	/*
	 * public int checkDublicateRecordAppDAO(Application application,TermLoan
	 * termLoan,SSIDetails ssiDetails, String createdBy,String mliId,long
	 * longTermCreditSanctioned,long longwcFundBasedSanctioned,long
	 * longwcNonFundBasedSanctioned) throws DatabaseException { int result=0;
	 * Statement stmt=null; ResultSet rs= null; ProjectOutlayDetails p=
	 * application.getProjectOutlayDetails(); java.util.Date date= new
	 * java.util.Date(); Date parsedstrModfiedDate =null; Date
	 * parsedcurrentDateTime=null;
	 * 
	 * SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	 * 
	 * try {
	 * 
	 * //TC String
	 * SanctionedDate=application.getTermLoan().getAmountSanctionedDate().toString()
	 * ; double creditGuaranteed = application.getTermLoan().getCreditGuaranteed();
	 * long longCreditGuaranteed=Math.round(creditGuaranteed); int
	 * tcTenure=application.getTermLoan().getTenure();
	 * 
	 * 
	 * //WC double creditFundBased = application.getWc().getCreditFundBased(); long
	 * longcreditFundBased=Math.round(creditFundBased); double creditNonFundBased =
	 * application.getWc().getCreditNonFundBased(); long
	 * longcreditNonFundBased=Math.round(creditNonFundBased); int
	 * wcTenure=application.getWc().getWcTenure();
	 * 
	 * 
	 * // SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy"); //
	 * SimpleDateFormat format2 = new SimpleDateFormat("dd/MMM/yyyy"); String
	 * query="";
	 * 
	 * if(application.getLoanType().equalsIgnoreCase("TC")) {
	 * query="select A.APP_CREATED_MODIFIED_DT , A.SSI_REFERENCE_NUMBER , P.PMR_CHIEF_FIRST_NAME, P.PMR_CHIEF_LAST_NAME,P.PMR_CHIEF_IT_PAN from application_detail_temp "
	 * +
	 * " A,promoter_detail_temp P , ssi_detail_temp S , TERM_LOAN_DETAIL_TEMP  T    where A.MEM_BNK_ID||A.MEM_ZNE_ID||A.MEM_BRN_ID='"
	 * +mliId+"' and A.APP_LOAN_TYPE ='"+application.getLoanType()
	 * +"' and UPPER(A.USR_ID)=UPPER('"+createdBy+"') "+
	 * " and UPPER(A.APP_BANK_APP_REF_NO)=UPPER('"+application.getMliRefNo()
	 * +"') AND A.APP_INTERNAL_RATING='"+application.getInternalRating()+"' "+
	 * " and A.SSI_REFERENCE_NUMBER=P.SSI_REFERENCE_NUMBER and UPPER(P.PMR_CHIEF_FIRST_NAME)=UPPER('"
	 * +ssiDetails.getCpFirstName()+"') and UPPER(P.PMR_CHIEF_LAST_NAME)=UPPER('"
	 * +ssiDetails.getCpLastName()+"') and UPPER(P.PMR_CHIEF_GENDER)=UPPER('"
	 * +ssiDetails.getCpGender()+"') "+
	 * " and A.SSI_REFERENCE_NUMBER=S.SSI_REFERENCE_NUMBER and UPPER(S.SSI_UNIT_NAME)=UPPER('"
	 * +ssiDetails.getSsiName()
	 * +"') and  A.USR_ID=S.SSI_CREATED_MODIFIED_BY and UPPER(S.SSI_STATE_NAME)=UPPER('"
	 * +ssiDetails.getState()+"') "+
	 * " and UPPER(S.SSI_DISTRICT_NAME)=UPPER('"+ssiDetails.getDistrict()
	 * +"') and UPPER(S.SSI_CONSTITUTION)=UPPER('"+ssiDetails.getConstitution()
	 * +"') and A.app_status='NE' AND TRUNC(A.APP_CREATED_MODIFIED_DT)='"+format2.
	 * format(date)+"'" +
	 * " and T.TRM_AMOUNT_SANCTIONED='"+longTermCreditSanctioned+"' " +
	 * " and T.TRM_AMOUNT_SANCTIONED_DT='"+format2.format(SanctionedDate)+"'" +
	 * " and T.TRM_CREDIT_TO_GUARANTEE='"+longCreditGuaranteed+"'" +
	 * " and T.TRM_TENURE='"+tcTenure+"' " + " and T.app_ref_no=A.app_ref_no";
	 * 
	 * 
	 * query = (new
	 * StringBuilder(" select count(*) from application_detail_temp a,promoter_detail_temp b,ssi_detail_temp s,term_loan_detail_temp t  where   a.app_ref_no=t.app_ref_no and a.ssi_reference_number=s.ssi_reference_number and s.ssi_reference_number=b.ssi_reference_number  and  UPPER(PMR_CHIEF_FIRST_NAME)=upper('"
	 * )).append(ssiDetails.getCpFirstName()).
	 * append("')  and   UPPER(PMR_CHIEF_LAST_NAME)=UPPER('").append(ssiDetails.
	 * getCpLastName()).append("') ").append(" and UPPER(PMR_CHIEF_GENDER)=UPPER('")
	 * .append(ssiDetails.getCpGender()).
	 * append("')   and  UPPER(SSI_DISTRICT_NAME)=UPPER('").append(ssiDetails.
	 * getDistrict()).append("') and UPPER(SSI_CONSTITUTION)=UPPER('").append(
	 * ssiDetails.getConstitution()).append("') ").
	 * append("  and UPPER(SSI_UNIT_NAME)=UPPER('").append(ssiDetails.getSsiName()).
	 * append("') and    A.USR_ID=UPPER('").append(createdBy).
	 * append("') and UPPER(S.SSI_STATE_NAME)=UPPER('").append(ssiDetails.getState()
	 * ).append("') ").append("  and  TRM_AMOUNT_SANCTIONED='").append(
	 * longTermCreditSanctioned).append("' ").
	 * append("and  TRM_CREDIT_TO_GUARANTEE='").append(longCreditGuaranteed).
	 * append("'  and app_status='NE'  and TRM_TENURE='").append(tcTenure).
	 * append("' and ").append(" A.MEM_BNK_ID||A.MEM_ZNE_ID||A.MEM_BRN_ID='").append
	 * (mliId).append("' and A.APP_LOAN_TYPE ='").append(application.getLoanType()).
	 * append("'  ").toString(); } else if
	 * (application.getLoanType().equalsIgnoreCase("WC")) {
	 * query="select A.APP_CREATED_MODIFIED_DT , A.SSI_REFERENCE_NUMBER , P.PMR_CHIEF_FIRST_NAME, P.PMR_CHIEF_LAST_NAME,P.PMR_CHIEF_IT_PAN from application_detail_temp "
	 * +
	 * " A,promoter_detail_temp P , ssi_detail_temp S , WORKING_CAPITAL_DETAIL_TEMP  W    where A.MEM_BNK_ID||A.MEM_ZNE_ID||A.MEM_BRN_ID='"
	 * +mliId+"' and A.APP_LOAN_TYPE ='"+application.getLoanType()
	 * +"' and UPPER(A.USR_ID)=UPPER('"+createdBy+"') "+
	 * " and UPPER(A.APP_BANK_APP_REF_NO)=UPPER('"+application.getMliRefNo()
	 * +"') AND A.APP_INTERNAL_RATING='"+application.getInternalRating()+"' "+
	 * " and A.SSI_REFERENCE_NUMBER=P.SSI_REFERENCE_NUMBER and UPPER(P.PMR_CHIEF_FIRST_NAME)=UPPER('"
	 * +ssiDetails.getCpFirstName()+"') and UPPER(P.PMR_CHIEF_LAST_NAME)=UPPER('"
	 * +ssiDetails.getCpLastName()+"') and UPPER(P.PMR_CHIEF_GENDER)=UPPER('"
	 * +ssiDetails.getCpGender()+"') "+
	 * " and A.SSI_REFERENCE_NUMBER=S.SSI_REFERENCE_NUMBER and UPPER(S.SSI_UNIT_NAME)=UPPER('"
	 * +ssiDetails.getSsiName()
	 * +"') and  A.USR_ID=S.SSI_CREATED_MODIFIED_BY and UPPER(S.SSI_STATE_NAME)=UPPER('"
	 * +ssiDetails.getState()+"') "+
	 * " and UPPER(S.SSI_DISTRICT_NAME)=UPPER('"+ssiDetails.getDistrict()
	 * +"') and UPPER(S.SSI_CONSTITUTION)=UPPER('"+ssiDetails.getConstitution()
	 * +"') and A.app_status='NE' AND TRUNC(A.APP_CREATED_MODIFIED_DT)='"+format2.
	 * format(date)+"'" +
	 * " and NVL(W.WCP_FB_LIMIT_SANCTIONED,0)+ NVL(W.WCP_NFB_LIMIT_SANCTIONED,0)='"+
	 * (longwcFundBasedSanctioned+longwcNonFundBasedSanctioned)+"'" +
	 * " and NVL(W.WCP_FB_CREDIT_TO_GUARANTEE,0)+ NVL(W.WCP_NFB_CREDIT_TO_GUARANTEE,0)='"
	 * +(longcreditFundBased+longcreditNonFundBased)+"'" +
	 * " and W.WCP_TENURE='"+wcTenure+"' " + " and W.app_ref_no=A.app_ref_no"; }
	 * 
	 * LogClass.StepWritter("dublicate date query "+query); Connection connection =
	 * DBConnection.getConnection(false); stmt=connection.createStatement(); rs=
	 * stmt.executeQuery(query); if(rs.next()) { result=1;
	 * LogClass.StepWritter("dublicate record"); if(rs.getString(1)!=null) {
	 * if(rs.getString(1).length() >=19) {
	 * LogClass.StepWritter("dublicate record date length is > 19"); //
	 * System.out.println("Record exist 11 =="+rs.getString(1)); String
	 * strModfiedDate=rs.getString(1);
	 * LogClass.StepWritter("dublicate strModfiedDate ="+strModfiedDate); String
	 * currentDateTime=new Timestamp(date.getTime()).toString();
	 * LogClass.StepWritter("dublicate currentDateTime ="+currentDateTime); String
	 * validdateCurrentDateTime="",validdatedStrModfiedDate=""; String[]
	 * arrvaliddateCurrentDateTime=null,arrvaliddatedStrModfiedDate=null; int
	 * lastIndexOfStrModfiedDate=0,lastIndexOfcurrentDateTime=0;
	 * if(strModfiedDate.length() > 19 ) {
	 * LogClass.StepWritter("dublicate strModfiedDate > 19"); int
	 * strModfiedDateIndex=strModfiedDate.indexOf(" ");
	 * lastIndexOfStrModfiedDate=strModfiedDate.indexOf(":");
	 * validdatedStrModfiedDate=strModfiedDate.substring(0, strModfiedDateIndex); //
	 * System.out.println("validdate1 "+validdatedStrModfiedDate);
	 * parsedstrModfiedDate = sdf.parse(validdatedStrModfiedDate.trim());
	 * LogClass.StepWritter("dublicate validdatedStrModfiedDate="
	 * +validdatedStrModfiedDate.trim()); int
	 * currentDateTimeIndex=currentDateTime.indexOf(" ");
	 * lastIndexOfcurrentDateTime=currentDateTime.indexOf(":");
	 * validdateCurrentDateTime=currentDateTime.substring(0, currentDateTimeIndex);
	 * // System.out.println("validdate2 "+validdateCurrentDateTime);
	 * parsedcurrentDateTime = sdf.parse(validdateCurrentDateTime.trim());
	 * LogClass.StepWritter("dublicate validdateCurrentDateTime="
	 * +validdateCurrentDateTime.trim());
	 * 
	 * if(parsedstrModfiedDate.compareTo(parsedcurrentDateTime)==0) {
	 * LogClass.StepWritter("dublicate 2 dates are equal =");
	 * arrvaliddatedStrModfiedDate=strModfiedDate.substring(strModfiedDateIndex,
	 * lastIndexOfStrModfiedDate+6).trim().split(":");
	 * arrvaliddateCurrentDateTime=currentDateTime.substring(currentDateTimeIndex,
	 * lastIndexOfcurrentDateTime+6).trim().split(":");
	 * 
	 * if(arrvaliddatedStrModfiedDate[0].equals(arrvaliddateCurrentDateTime[0])) {
	 * LogClass.StepWritter("dublicate 2 dates HH are equal =");
	 * if(arrvaliddatedStrModfiedDate[1].equals(arrvaliddateCurrentDateTime[1])) {
	 * LogClass.StepWritter("dublicate 2 dates MM not are equal =");
	 * if(Integer.parseInt(arrvaliddateCurrentDateTime[2])-Integer.parseInt(
	 * arrvaliddatedStrModfiedDate[2]) < 6) {
	 * LogClass.StepWritter("dublicate 2 dates SS is less than 6"); result=1; //
	 * System.out.println("dont allow to save"); } else {
	 * LogClass.StepWritter("dublicate 2 dates SS is not less than 6"); //
	 * System.out.println("allow to save"); } } else {
	 * LogClass.StepWritter("dublicate 2 dates MM not are equal ="); } } else {
	 * LogClass.StepWritter("dublicate 2 dates HH not are equal ="); } } else {
	 * LogClass.StepWritter("dublicate 2 dates are not equal ="); } } else {
	 * LogClass.StepWritter("dublicate strModfiedDate < 19"); }
	 * 
	 * } else { LogClass.StepWritter("dublicate record date length is not > 19"); }
	 * } }
	 * 
	 * } catch(Exception e) { result=0;
	 * LogClass.StepWritter("dublicate date exception"); //
	 * LogClass.writeExceptionOnFile(e); //e.printStackTrace(); }
	 * LogClass.StepWritter("dublicate date final result "+result); return result; }
	 * 
	 * // 24-02-2016 end
	 */

	public int checkDublicateRecordAppDAO(Application application, TermLoan termLoan, SSIDetails ssiDetails,
			String createdBy, String mliId, long longTermCreditSanctioned, long longwcFundBasedSanctioned,
			long longwcNonFundBasedSanctioned) throws DatabaseException {
		int result = 0;
		Statement stmt = null;
		ResultSet rs = null;
		ProjectOutlayDetails p = application.getProjectOutlayDetails();
		java.util.Date date = new java.util.Date();
		Date parsedstrModfiedDate = null;
		Date parsedcurrentDateTime = null;
		Connection connection = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try {

			// TC
			String SanctionedDate = application.getTermLoan().getAmountSanctionedDate().toString();
			double creditGuaranteed = application.getTermLoan().getCreditGuaranteed();
			long longCreditGuaranteed = Math.round(creditGuaranteed);
			int tcTenure = application.getTermLoan().getTenure();

			// WC
			double creditFundBased = application.getWc().getCreditFundBased();
			long longcreditFundBased = Math.round(creditFundBased);
			double creditNonFundBased = application.getWc().getCreditNonFundBased();
			long longcreditNonFundBased = Math.round(creditNonFundBased);
			int wcTenure = application.getWc().getWcTenure();

			// SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
			// SimpleDateFormat format2 = new SimpleDateFormat("dd/MMM/yyyy");
			String query = "";
			connection = DBConnection.getConnection(false);
			stmt = connection.createStatement();

			if (application.getLoanType().equalsIgnoreCase("TC")) {
				/*
				 * query="select A.APP_CREATED_MODIFIED_DT , A.SSI_REFERENCE_NUMBER , P.PMR_CHIEF_FIRST_NAME, P.PMR_CHIEF_LAST_NAME,P.PMR_CHIEF_IT_PAN from application_detail_temp "
				 * +
				 * " A,promoter_detail_temp P , ssi_detail_temp S , TERM_LOAN_DETAIL_TEMP  T    where A.MEM_BNK_ID||A.MEM_ZNE_ID||A.MEM_BRN_ID='"
				 * +mliId+"' and A.APP_LOAN_TYPE ='"+application.getLoanType()
				 * +"' and UPPER(A.USR_ID)=UPPER('"+createdBy+"') "+
				 * " and UPPER(A.APP_BANK_APP_REF_NO)=UPPER('"+application.getMliRefNo()
				 * +"') AND A.APP_INTERNAL_RATING='"+application.getInternalRating()+"' "+
				 * " and A.SSI_REFERENCE_NUMBER=P.SSI_REFERENCE_NUMBER and UPPER(P.PMR_CHIEF_FIRST_NAME)=UPPER('"
				 * +ssiDetails.getCpFirstName()+"') and UPPER(P.PMR_CHIEF_LAST_NAME)=UPPER('"
				 * +ssiDetails.getCpLastName()+"') and UPPER(P.PMR_CHIEF_GENDER)=UPPER('"
				 * +ssiDetails.getCpGender()+"') "+
				 * " and A.SSI_REFERENCE_NUMBER=S.SSI_REFERENCE_NUMBER and UPPER(S.SSI_UNIT_NAME)=UPPER('"
				 * +ssiDetails.getSsiName()
				 * +"') and  A.USR_ID=S.SSI_CREATED_MODIFIED_BY and UPPER(S.SSI_STATE_NAME)=UPPER('"
				 * +ssiDetails.getState()+"') "+
				 * " and UPPER(S.SSI_DISTRICT_NAME)=UPPER('"+ssiDetails.getDistrict()
				 * +"') and UPPER(S.SSI_CONSTITUTION)=UPPER('"+ssiDetails.getConstitution()
				 * +"') and A.app_status='NE' AND TRUNC(A.APP_CREATED_MODIFIED_DT)='"+format2.
				 * format(date)+"'" +
				 * " and T.TRM_AMOUNT_SANCTIONED='"+longTermCreditSanctioned+"' " +
				 * " and T.TRM_AMOUNT_SANCTIONED_DT='"+format2.format(SanctionedDate)+"'" +
				 * " and T.TRM_CREDIT_TO_GUARANTEE='"+longCreditGuaranteed+"'" +
				 * " and T.TRM_TENURE='"+tcTenure+"' " + " and T.app_ref_no=A.app_ref_no";
				 */

				query = (new StringBuilder(
						" select count(*) from application_detail a,promoter_detail b,ssi_detail s,term_loan_detail t  where   a.app_ref_no=t.app_ref_no and a.ssi_reference_number=s.ssi_reference_number and s.ssi_reference_number=b.ssi_reference_number  and  UPPER(PMR_CHIEF_FIRST_NAME)=upper('"))
								.append(ssiDetails.getCpFirstName())
								.append("')  and   UPPER(PMR_CHIEF_LAST_NAME)=UPPER('")
								.append(ssiDetails.getCpLastName()).append("') ")
								.append(" and UPPER(PMR_CHIEF_GENDER)=UPPER('").append(ssiDetails.getCpGender())
								.append("')   and  UPPER(SSI_DISTRICT_NAME)=UPPER('").append(ssiDetails.getDistrict())
								.append("') and UPPER(SSI_CONSTITUTION)=UPPER('").append(ssiDetails.getConstitution())
								.append("') ").append("  and UPPER(SSI_UNIT_NAME)=UPPER('")
								.append(ssiDetails.getSsiName()).append("') and    A.USR_ID=UPPER('").append(createdBy)
								.append("') and UPPER(S.SSI_STATE_NAME)=UPPER('").append(ssiDetails.getState())
								.append("') ").append("  and  TRM_AMOUNT_SANCTIONED='").append(longTermCreditSanctioned)
								.append("' ").append("and  TRM_CREDIT_TO_GUARANTEE='").append(longCreditGuaranteed)
								.append("'  and app_status='NE'  and TRM_TENURE='").append(tcTenure).append("' and ")
								.append(" concat(A.MEM_BNK_ID,A.MEM_ZNE_ID,A.MEM_BRN_ID)='").append(mliId)
								.append("' and A.APP_LOAN_TYPE ='").append(application.getLoanType()).append("'  ")
								.toString();
				System.out.println("query==17420==" + query);
				rs = stmt.executeQuery(query);
				if (rs.next())
					result = rs.getInt(1);

			} else

			if (application.getLoanType().equalsIgnoreCase("WC")) {
				query = (new StringBuilder(
						"SELECT COUNT(*) from application_detail  A,promoter_detail P , ssi_detail S , WORKING_CAPITAL_DETAIL  W    where concat(A.MEM_BNK_ID,A.MEM_ZNE_ID,A.MEM_BRN_ID)='"))
								.append(mliId).append("' and A.APP_LOAN_TYPE ='").append(application.getLoanType())
								.append("' and UPPER(A.USR_ID)=UPPER('").append(createdBy)
								.append("') and UPPER(A.APP_BANK_APP_REF_NO)=UPPER('").append(application.getMliRefNo())
								.append("') AND A.APP_INTERNAL_RATING='").append(application.getInternalRating())
								.append("'  ")
								.append(" and A.SSI_REFERENCE_NUMBER=P.SSI_REFERENCE_NUMBER and UPPER(P.PMR_CHIEF_FIRST_NAME)=UPPER('")
								.append(ssiDetails.getCpFirstName())
								.append("') and UPPER(P.PMR_CHIEF_LAST_NAME)=UPPER('")
								.append(ssiDetails.getCpLastName()).append("') and UPPER(P.PMR_CHIEF_GENDER)=UPPER('")
								.append(ssiDetails.getCpGender())
								.append("')  and A.SSI_REFERENCE_NUMBER=S.SSI_REFERENCE_NUMBER ")
								.append(" and UPPER(S.SSI_UNIT_NAME)=UPPER('").append(ssiDetails.getSsiName())
								.append("') and  UPPER(S.SSI_STATE_NAME)=UPPER('").append(ssiDetails.getState())
								.append("')  and UPPER(S.SSI_DISTRICT_NAME)=UPPER('").append(ssiDetails.getDistrict())
								.append("') and UPPER(S.SSI_CONSTITUTION)=UPPER('").append(ssiDetails.getConstitution())
								.append("') ")
								.append(" and A.app_status='NE' AND TRUNC(A.APP_CREATED_MODIFIED_DT)=trunc(sysdate)  and IFNULL(W.WCP_FB_LIMIT_SANCTIONED,0)+ IFNULL(W.WCP_NFB_LIMIT_SANCTIONED,0)='")
								.append(longwcFundBasedSanctioned + longwcNonFundBasedSanctioned)
								.append("' and IFNULL(W.WCP_FB_CREDIT_TO_GUARANTEE,0)+ IFNULL(W.WCP_NFB_CREDIT_TO_GUARANTEE,0)='")
								.append(longcreditFundBased + longcreditNonFundBased).append("' ")
								.append(" and W.app_ref_no=A.app_ref_no  ").toString();
				rs = stmt.executeQuery(query);
				if (rs.next())
					result = rs.getInt(1);
			}

			if (result <= 0) {
				// break ;
			}

		} catch (Exception e) {
			result = 0;
			LogClass.StepWritter("dublicate date exception");
			// LogClass.writeExceptionOnFile(e);
			// e.printStackTrace();
		} finally {
			DBConnection.freeConnection(connection);

		}
		LogClass.StepWritter("dublicate date final result " + result);
		return result;
	}

	// added by DKR for 10Y Valid Renew
	public WcValidateBean getCgpanWithDate(String memberId, String cgpanbid, String cgpanbidType)
			throws DatabaseException {
		Log.log(4, "ApplicationDAO", "getCgpanWithDate", "Entered");
		Connection connection = null; // String fetRecordStatus="";
		if (connection == null) {
			connection = DBConnection.getConnection();
		}
		PreparedStatement pst1 = null;
		ResultSet rs1 = null;
		WcValidateBean wcValidateBean = new WcValidateBean();
		try {
			/*
			 * String cgSql =
			 * "select max(a.cgpan) as AP_CGPAN,to_char(max(a.APP_EXPIRY_DT), 'dd/MM/yyyy') as AP_EXPIRY_DT, to_char(min(a.APP_GUAR_START_DATE_TIME),'dd/MM/yyyy') as AP_GUAR_START_DATE, "
			 * +
			 * "max(a.app_status) as AP_STATUS from application_detail a, ssi_detail s where a.SSI_REFERENCE_NUMBER =s.SSI_REFERENCE_NUMBER and a.app_status not in ('RE')"
			 * +
			 * "and MEM_BNK_ID||MEM_ZNE_ID||MEM_BRN_ID= ? and a.SSI_REFERENCE_NUMBER in (select att.SSI_REFERENCE_NUMBER from application_detail att where att.cgpan = ?)"
			 * ;
			 */

			String cgSql = "select max(a.cgpan) as AP_CGPAN,to_char(max(a.APP_EXPIRY_DT), 'dd/MM/yyyy') as AP_EXPIRY_DT, to_char(min(a.APP_GUAR_START_DATE_TIME),'dd/MM/yyyy') as AP_GUAR_START_DATE, max(a.app_status) as AP_STATUS from application_detail a, ssi_detail s where a.SSI_REFERENCE_NUMBER =s.SSI_REFERENCE_NUMBER and a.app_status not in ('RE')and CONCAT(MEM_BNK_ID,MEM_ZNE_ID,MEM_BRN_ID)= ? and a.SSI_REFERENCE_NUMBER in (select att.SSI_REFERENCE_NUMBER from application_detail att where att.cgpan = ?) and substr(cgpan,-2) <> 'TC'";
			pst1 = connection.prepareStatement(cgSql);
			/*
			 * String bidSql =
			 * "select max(a.cgpan) as AP_CGPAN, to_char(max(a.APP_EXPIRY_DT), 'dd/MM/yyyy') as AP_EXPIRY_DT, to_char(min(a.APP_GUAR_START_DATE_TIME),'dd/MM/yyyy') as AP_GUAR_START_DATE, "
			 * +
			 * "max(a.app_status) as AP_STATUS from application_detail a, ssi_detail s where a.SSI_REFERENCE_NUMBER = s.SSI_REFERENCE_NUMBER and a.app_status not in ('RE') and "
			 * + "MEM_BNK_ID||MEM_ZNE_ID||MEM_BRN_ID= ? and s.bid = ? ";
			 */
			/*
			 * if ((cgpanbid != null && !cgpanbid.isEmpty()) && (memberId != null &&
			 * !memberId.isEmpty()) && cgpanbidType.equals("CGPAN")) { pst1 =
			 * connection.prepareStatement(cgSql); }
			 */
			/*
			 * if ((cgpanbid != null && !cgpanbid.isEmpty()) && (memberId != null &&
			 * !memberId.isEmpty()) && cgpanbidType.equals("BID")){ pst1 = null; rs1 = null;
			 * pst1 = connection.prepareStatement(bidSql); }
			 */
			pst1.setString(1, memberId);
			pst1.setString(2, cgpanbid);
			rs1 = pst1.executeQuery();
			System.out.println("bidSql.......DR.............." + cgSql);

			if (rs1.next()) {
				/*
				 * wcValidateBean.setCgpan(rs1.getString("AP_CGPAN"));
				 * wcValidateBean.setGuaranteeStartDate(new
				 * java.util.Date(rs1.getString("AP_GUAR_START_DATE")));
				 * wcValidateBean.setAppExpiryDate(new
				 * java.util.Date(rs1.getString("AP_EXPIRY_DT")));
				 * wcValidateBean.setStatus(rs1.getString("AP_STATUS"));
				 */
				wcValidateBean.setCgpan(rs1.getString("AP_CGPAN"));
				wcValidateBean.setAppGurStartDt(rs1.getString("AP_GUAR_START_DATE"));
				wcValidateBean.setAppExpDt(rs1.getString("AP_EXPIRY_DT"));
				wcValidateBean.setStatus(rs1.getString("AP_STATUS"));
				// System.out.println("AP_CGPAN....................."+rs1.getString("AP_CGPAN"));
				// System.out.println("AP_GUAR_START_DATE..........."+rs1.getString("AP_GUAR_START_DATE"));
				// System.out.println("AP_EXPIRY_DT................."+rs1.getString("AP_EXPIRY_DT"));
				// System.out.println("AP_STATUS...................."+rs1.getString("AP_STATUS"));
			} /*
				 * else{ fetRecordStatus="Record not available."; }
				 */
			pst1.close();
			rs1.close();
		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "getCgpanWithDate", sqlException.getMessage());
			Log.logException(sqlException);
			sqlException.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(4, "ApplicationDAO", "getCgpanWithDate", sqlException.getMessage());
			}
			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}
		return wcValidateBean;
	}
	// END

	public String getforeignbankschemeAmtMethod(String creditammt, String bankid, String aamt) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String message = "";
		ArrayList numbers = new ArrayList();
		String chkflag = "";

		double Ammt = Double.parseDouble(aamt);
		double creditammt1 = Double.parseDouble(creditammt);

		if (creditammt1 > Ammt) {
			message = "Ammount Should be less than limit ammount";

		} else {
			message = "";
		}

		return message;

	}

	public WcValidateBean getBothTCDetail(String appCgpan, Connection connection) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "getBothTCDetail", "Entered");
		if (connection == null) {
			connection = DBConnection.getConnection();
		}
		System.out.println("getBothTCDetail()....................." + appCgpan);
		WcValidateBean wcValidateBean = new WcValidateBean();
		SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
		try {
			ResultSet rs2 = null;
			wcValidateBean = new WcValidateBean();
			String tcCCSql = "select cgpan,APP_GUAR_START_DATE_TIME,APP_EXPIRY_DT,APP_STATUS,APP_LOAN_TYPE from application_detail where substr(cgpan,0,length(cgpan)-2)=substr('"
					+ appCgpan + "',0,length('" + appCgpan
					+ "')-2) and   (cgpan like '%WC%' or cgpan like '%R%') and app_status!='RE' ";
			Statement stmt = connection.createStatement();
			rs2 = stmt.executeQuery(tcCCSql);
			while (rs2.next()) {
				wcValidateBean.setCgpan(rs2.getString("cgpan"));

				try {
					wcValidateBean.setAppGurStartDt(
							myFormat.format(fromUser.parse(rs2.getString("APP_GUAR_START_DATE_TIME"))));
					wcValidateBean.setAppExpDt(myFormat.format(fromUser.parse(rs2.getString("APP_EXPIRY_DT"))));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				wcValidateBean.setStatus(rs2.getString("APP_STATUS"));
				wcValidateBean.setAppLoanType(rs2.getString("APP_LOAN_TYPE"));
				System.out.println(wcValidateBean.getAppGurStartDt() + "-----" + wcValidateBean.getAppExpDt());
			}
			stmt.close();
			rs2.close();
		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "getBothTCDetail", sqlException.getMessage());
			Log.logException(sqlException);
			sqlException.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(4, "ApplicationDAO", "getBothTCDetail", sqlException.getMessage());
			}
			throw new DatabaseException("Something wrong with getBothTCDetail();" + sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}
		return wcValidateBean;
	}
	/*
	 * // Updated by DKR public int validateCgpanOrBidDAO(String cgpanOrBidVal,
	 * String inputType)throws DatabaseException {
	 * 
	 * Connection connection = DBConnection.getConnection(); int returnVal = 0; try
	 * { if((cgpanOrBidVal != null && !cgpanOrBidVal.isEmpty()) &&
	 * inputType.equals("CGPAN")) { System.out.println(
	 * "cgpanOrBidVal.......................DAO...........CGPAN........"+
	 * cgpanOrBidVal); if (loan_type.equals("TC")) { PreparedStatement pst1 =
	 * connection
	 * .prepareStatement("select count(app_ref_no) from WORKING_CAPITAL_ENHANCE_TEMP where app_ref_no in "
	 * +
	 * "(select app_ref_no from application_detail a , ssi_detail s where a.SSI_REFERENCE_NUMBER = "
	 * +
	 * "s.SSI_REFERENCE_NUMBER and a.cgpan =? and UPPER(WCE_OPERATION_TYPE) = 'I')"
	 * );
	 * 
	 * pst1.setString(1, cgpanOrBidVal); ResultSet rs1 = pst1.executeQuery();
	 * if(rs1.next()){ returnVal = rs1.getInt(1); System.out.println(returnVal+
	 * "cgpanOrBidVal.......................DAO..........CGPAN........."+
	 * cgpanOrBidVal);
	 * 
	 * } } else if((cgpanOrBidVal != null && !cgpanOrBidVal.isEmpty()) &&
	 * inputType.equals("BID")) { System.out.println(
	 * "cgpanOrBidVal.......................DAO..........BID........."+cgpanOrBidVal
	 * ); if (loan_type.equals("TC")) { PreparedStatement pst = connection
	 * .prepareStatement("select count(app_ref_no) from WORKING_CAPITAL_ENHANCE_TEMP where app_ref_no in "
	 * +
	 * "(select app_ref_no from application_detail a , ssi_detail s where a.SSI_REFERENCE_NUMBER = "
	 * +
	 * "s.SSI_REFERENCE_NUMBER and s.bid = ? and UPPER(WCE_OPERATION_TYPE) = 'I')");
	 * 
	 * pst.setString(1, cgpanOrBidVal); ResultSet rs = pst.executeQuery();
	 * if(rs.next()){ returnVal = rs.getInt(1); System.out.println(returnVal+
	 * "cgpanOrBidVal.......................DAO..........BID........."+cgpanOrBidVal
	 * );
	 * 
	 * } } } catch (Exception exception) { Log.logException(exception);
	 * System.out.println("DKR EXCPETON .......validateCgpanOrBidDAO........"
	 * +exception); throw new DatabaseException(exception.getMessage()); } finally {
	 * DBConnection.freeConnection(connection); } return returnVal; }
	 */

	public double getExposuredetails(String bankId, HttpServletRequest request) {
		System.out.println("----fbid---");
		Connection conn = null;
		if (conn == null) {
			conn = DBConnection.getConnection();
		}
		HttpSession session = request.getSession(false);
		Log.log(4, "ApplicationDAO", "getExposuredetails",
				"sessionuserid=" + session.getAttribute("USER_ID") + "" + session.getValueNames());
		double finalammt = 0.0;
		double ammt1 = 0.0;
		try {

			Statement stmt = conn.createStatement();

			String Qry = "select EXPOSURE_FB_ID,IFNULL(INDIVISUAL_LIMIT_AMT,0) from EXPOSURE_FOREIGN_BANK_LIMIT where STATUS='A' and MEM_BNK_ID="
					+ bankId;
			ResultSet rs = stmt.executeQuery(Qry);
			if (rs.next() == false) {
				session.setAttribute("expoid", "");
			} else {
				do {
					String ID = rs.getString(1);
					// request.setAttribute("expoid1", ID);
					session.setAttribute("expoid", ID);
					ammt1 = rs.getFloat(2);
					finalammt = ammt1 * 10000000;
//	        	    	 double dynaForm.set("exposurelmtAmt", finalammt);
				} while (rs.next());

			}
			Log.log(4, "ApplicationDAO", "getExposuredetails", "expolmtammt");
		} catch (Exception e) {
			Log.logException(e);
			e.printStackTrace();
			System.out.println("Say EXCPETON .......getExposuredetails........" + e);
			// TODO: handle excep
		} finally {
			DBConnection.freeConnection(conn);

		}
		return finalammt;
	}

	public String getExposureID(String bankId) {
		System.out.println("----fbid---");
		String expoid = "";
		Connection conn = null;
		if (conn == null) {
			conn = DBConnection.getConnection();
		}

		try {

			Statement stmt = conn.createStatement();

			String Qry = "select EXPOSURE_FB_ID,IFNULL(INDIVISUAL_LIMIT_AMT,0) from EXPOSURE_FOREIGN_BANK_LIMIT where STATUS='A' and MEM_BNK_ID="
					+ bankId;
			ResultSet rs = stmt.executeQuery(Qry);
			if (rs.next() == false) {
				expoid = "";
			} else {
				do {
					expoid = rs.getString(1);

				} while (rs.next());

			}
			Log.log(4, "ApplicationDAO", "getExposuredetails", "expoid");
		} catch (Exception e) {
			Log.logException(e);
			e.printStackTrace();
			System.out.println("EXCPETON .......getExposureID........" + e);
		} finally {
			DBConnection.freeConnection(conn);

		}
		return expoid;
	}

//Updated by DKR for Hybrid sceurity enable
	public Date getSensionDatebyCGBID(String cgpan) throws DatabaseException {// String cgpanOrBidVal, String
																				// inputType)throws DatabaseException {
		Date appSenDate = null;
		Connection connection = null;
		try {
			if (cgpan == null || !cgpan.equals("")) {
				if (connection == null)
					connection = DBConnection.getConnection();
				Statement stmt = connection.createStatement();
				String qry = "select APP_SANCTION_DT from application_detail where cgpan='" + cgpan + "'";// and
																											// APP_SANCTION_DT
																											// >='01-Apr-2018'");
				ResultSet rs = stmt.executeQuery(qry);
				if (rs.next() == true) {
					appSenDate = rs.getDate("APP_SANCTION_DT");
					System.out.println("Query:" + qry);
				} else {
					System.out.println("Query:" + qry);
					throw new DatabaseException("App Sanction date not available");
				}
			}
		} catch (Exception exception) {
			Log.logException(exception);
			System.out.println("DKR EXCPETON .......getSensionDatebyCGBID........" + exception);
			throw new DatabaseException(exception.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}
		return appSenDate;
		// }
		// }
	}

	public HashMap uploadOutstandingIntoInterface(ArrayList<Application> apps) throws SQLException, Exception {

		HashMap map = new HashMap();
		ArrayList clearapps = new ArrayList();
		ArrayList dupapps = new ArrayList();
		Connection conn = null;
		CallableStatement stmt = null;
		ArrayList validappsSummaryDetails = new ArrayList();
		if (conn == null) {
			conn = DBConnection.getConnection();
		}
		// Statement str11 = connection.createStatement();
		String query2 = "SELECT  CURRENT_DATE FROM DUAL";

		Statement str2 = conn.createStatement();
		ResultSet rs1 = str2.executeQuery(query2);

		Date sysdate = null;

		while (rs1.next()) {

			sysdate = rs1.getDate(1);

		}

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
		String sydate = sdf.format((java.sql.Date) sysdate);

		for (Application app : apps) {
			System.out.println(
					"apps.size().......................................FROM uploadAppsIntoInterface DAO>>>>>>>>>>>>>>>>>>>"
							+ apps.size());
			String mli_id = app.getMliID();
			String cgpan = app.getCgpan();
			String query = "select MEM_BNK_ID||MEM_ZNE_ID||MEM_BRN_ID as member_id  from application_detail where cgpan='"
					+ cgpan + "' ";

			Statement str3 = conn.createStatement();
			ResultSet rs3 = str3.executeQuery(query);
			String mliid = "";
			// Date sysdate = null;

			while (rs3.next()) {

				mliid = rs3.getString(1);
				System.out.println("cgpans==" + mliid);

			}

			System.out.println("mli_id==" + mli_id);
			boolean isDuplicate = false;
			if (isDuplicate) {
				dupapps.add(app);
			} else {

				try {

					stmt = conn.prepareCall("{?=call FuncOutstandingUploadXls(?,?,?,?,?,?,?)}");
					stmt.registerOutParameter(1, Types.INTEGER);
					stmt.setString(2, mliid);
					stmt.setString(3, app.getCgpan());
					stmt.setDouble(4, app.getOutstandingAmount());
					stmt.setDate(5, new java.sql.Date(app.getOutstandingDate().getTime()));
					System.out.println("");
					stmt.setString(6, app.getUserId());
					stmt.setString(7, sydate);
					stmt.registerOutParameter(8, Types.VARCHAR);

					System.out.println("uploadOutstandingIntoInterface from dao raju....................." + dupapps);
					stmt.execute();

					int errorcode = stmt.getInt(1);
					// System.out.println("error code:" + errorcode);
					String error = stmt.getString(8);
					if (errorcode == 0) {
						conn.commit();
					} else {
						throw new DatabaseException(error);
					}
				} catch (SQLException e) {
					try {
						conn.rollback();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					e.printStackTrace();
					throw e;
				} catch (Exception e) {
					try {
						conn.rollback();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					e.printStackTrace();
					throw e;
				} finally {
					try {
						stmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					DBConnection.freeConnection(conn);
				}
				// System.out.println("uploadAppsIntoInterface app"+app);
				clearapps.add(app);
			}
			if (app.getLoanType() != null && app.getLoanType().equalsIgnoreCase("TC")) {

				validappsSummaryDetails.add("CGPAN:-" + app.getCgpan() + " OUTSTANDING:-" + app.getOutstandingAmount()
						+ " DATE:-" + app.getOutstandingDate());
				// System.out.println("RRAAAJUU"+app.getCgpan());
			} else if (app.getLoanType() != null && app.getLoanType().equalsIgnoreCase("WC")) {

				validappsSummaryDetails.add("CGPAN:-" + app.getCgpan() + " OUTSTANDING:-" + app.getOutstandingAmount()
						+ " DATE:-" + app.getOutstandingDate());
				// System.out.println("RRAAAJUU"+app.getOutstandingAmount());
			}
		}
		map.put("CLEARAPPS", clearapps);
		map.put("DUPAPPS", dupapps);
		map.put("VALIDAPPSUMMARYDETAIL", validappsSummaryDetails);
		return map;

	}

	public Application validateCgpanFieldNotDuplicate(String cgpan) throws DatabaseException, SQLException {
		Connection connection = DBConnection.getConnection(false);
		System.out.println("in getAppForCgpan DAO======Entered====RAJUK");
		Application application = new Application();
		String appRefNo = "";
		Statement str1 = connection.createStatement();
		ResultSet rs = null;
		int count = 0;
		try {
			String query = "SELECT COUNT(*) FROM OUTSTANDING_UPLOAD_NEW WHERE CGPAN='" + cgpan + "' ";
//System.out.println("testing quryforSelect4 " + query);
			rs = str1.executeQuery(query);

			while (rs.next()) {
				count = rs.getInt(1);
			}
			if (count == 1) {
				// System.out.println("RRRRRRRRRRSSSSSSSSSSS====");
				String error = "";

				// appForCgpan.close();
				// appForCgpan = null;

				// connection.rollback();
//	System.out.println("in getAppForCgpan DAO error "+error);
				throw new DatabaseException(error);
			}
		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "getAppForCgpan", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(4, "ApplicationDAO", "getAppForCgpan", ignore.getMessage());
			}
//		System.out.println("in getAppForCgpan DAO DatabaseException "+sqlException.getMessage());
			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(4, "ApplicationDAO", "getAppForCgpan", "Exited");

		return application;
	}

	public Application validateCgpandate(String cgpan) throws DatabaseException, SQLException {
		// System.out.println("=ENTERED FOR SANCTION DATE====="+cgpan );
		Connection connection = DBConnection.getConnection();
		Application application = new Application();
		try {

			Statement str1 = connection.createStatement();
			int count = 0;

			String query_sanction_dt = " select count(cgpan)  from application_detail  where cgpan='" + cgpan
					+ "' and trunc(APP_SANCTION_DT) <'01-apr-2018' ";
			/*
			 * while (rs.next()) {
			 * 
			 * count = rs.getDate(1); System.out.println("=npaOutStandingAmount IS=====" +
			 * sanctionDate);
			 * 
			 * }
			 */

			System.out.println("query====" + query_sanction_dt);
			/*
			 * Date sanctionDate = null; String comparedate="01-Apr-2018";
			 */

			ResultSet rs = str1.executeQuery(query_sanction_dt);

			while (rs.next()) {
				count = rs.getInt(1);
			}
			if (count == 1) {
				// System.out.println("RRRRRRRRRRSSSSSSSSSSS====");
				String error = "";

				// appForCgpan.close();
				// appForCgpan = null;

//	connection.rollback();
//	System.out.println("in getAppForCgpan DAO error "+error);
				throw new DatabaseException(error);
			}
		}

		catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "getAppForCgpan", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(4, "ApplicationDAO", "getAppForCgpan", ignore.getMessage());
			}
//		System.out.println("in getAppForCgpan DAO DatabaseException "+sqlException.getMessage());
			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(4, "ApplicationDAO", "getAppForCgpan", "Exited");

		return application;
	}

	/*
	 * public HashMap<String, Double> getExposuredetailsForFileUpload(String bankId)
	 * { System.out.println("----fbid---"); Connection conn=null; if(conn==null){
	 * conn = DBConnection.getConnection(); } HashMap<String, Double> map=new
	 * HashMap<String, Double>(); // HttpSession session =
	 * request.getSession(false); // Log.log(4, "ApplicationDAO",
	 * "getExposuredetailsForFileUpload",
	 * "sessionuserid="+session.getAttribute("USER_ID")+""+session.getValueNames());
	 * double finalammt=0.0; double ammt1=0.0; try { Statement stmt =
	 * conn.createStatement(); String
	 * Qry="select EXPOSURE_FB_ID,nvl(INDIVISUAL_LIMIT_AMT,35000) from repuser.EXPOSURE_FOREIGN_BANK_LIMIT where STATUS='A' and MEM_BNK_ID="
	 * +bankId; ResultSet rs = stmt.executeQuery(Qry); if(rs.next()==false){ //
	 * session.setAttribute("expoid", ""); //String expoid=rs.getString(1); } else{
	 * do { String expoMliId=rs.getString(1);
	 * 
	 * // request.setAttribute("expoid1", ID); // session.setAttribute("expoid",
	 * ID); ammt1=rs.getFloat(2);
	 * 
	 * finalammt =ammt1*10000000;
	 * 
	 * 
	 * 
	 * map.put(expoMliId, finalammt); // double dynaForm.set("exposurelmtAmt",
	 * finalammt); } while (rs.next());
	 * 
	 * } Log.log(4, "ApplicationDAO", "getExposuredetailsForFileUpload",
	 * "expolmtammt"); } catch (Exception e) { Log.logException(e);
	 * e.printStackTrace(); System.out.
	 * println("Say EXCPETON .......getExposuredetailsForFileUpload........"+e); //
	 * TODO: handle excep } finally { DBConnection.freeConnection(conn);
	 * 
	 * } return map; }
	 */
	public HashMap<String, Double> getExposuredetailsForFileUpload(String bankId) {
		System.out.println("----fbid---");
		Connection conn = null;
		if (conn == null) {
			conn = DBConnection.getConnection();
		}
		HashMap<String, Double> map = new HashMap<String, Double>();
//	 HttpSession session = request.getSession(false);
//	  Log.log(4, "ApplicationDAO", "getExposuredetailsForFileUpload", "sessionuserid="+session.getAttribute("USER_ID")+""+session.getValueNames());
		double finalammt = 0.0;
		double ammt1 = 0.0;
		try {

			Statement stmt = conn.createStatement();

			String Qry = "select EXPOSURE_FB_ID,IFNULL(INDIVISUAL_LIMIT_AMT,35000) from repuser.EXPOSURE_FOREIGN_BANK_LIMIT where STATUS='A' and MEM_BNK_ID="
					+ bankId;
			ResultSet rs = stmt.executeQuery(Qry);
			if (rs.next() == false) {
				// session.setAttribute("expoid", "");
				// String expoid=rs.getString(1);
			} else {
				do {
					String expoMliId = rs.getString(1);

					// request.setAttribute("expoid1", ID);
					// session.setAttribute("expoid", ID);
					ammt1 = rs.getFloat(2);

					finalammt = ammt1 * 10000000;

					map.put(expoMliId, finalammt);
//	        	    	 double dynaForm.set("exposurelmtAmt", finalammt);
				} while (rs.next());

			}
			Log.log(4, "ApplicationDAO", "getExposuredetailsForFileUpload", "expolmtammt");
		} catch (Exception e) {
			Log.logException(e);
			e.printStackTrace();
			System.out.println("Say EXCPETON .......getExposuredetailsForFileUpload........" + e);
			// TODO: handle excep
		} finally {
			DBConnection.freeConnection(conn);

		}
		return map;
	}

	public int getHistoryCount(String memberId) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "getTotalApprovedAmt", "Entered");

		int historyCount = 0;

		Connection connection = DBConnection.getConnection();
		try {
			CallableStatement totalCount = connection.prepareCall("{call funcGetCountHistoryCount(?,?,?,?)}");

			totalCount.setString(2, memberId);
			Log.log(4, "ApplicationDAO", "getClaimCount", "getClaimCount");

			totalCount.registerOutParameter(1, 4);
			totalCount.registerOutParameter(3, 4);
			totalCount.registerOutParameter(4, 12);

			totalCount.execute();

			int totalApprovedStmtValue = totalCount.getInt(1);

			if (totalApprovedStmtValue == 1) {
				String error = totalCount.getString(4);

				totalCount.close();
				totalCount = null;

				connection.rollback();
				System.out.println("historyCount error " + error);
				throw new DatabaseException(error);
			}

			historyCount = totalCount.getInt(3);

			System.out.println("historyCount===" + historyCount);

			Log.log(4, "ApplicationDAO", "getClaimCount", "getClaimCount :" + historyCount);

			totalCount.close();
			totalCount = null;
		} catch (SQLException sqlException) {
			Log.log(4, "ApplicationDAO", "getClaimCount", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(4, "ApplicationDAO", "getClaimCount", ignore.getMessage());
			}

			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		return historyCount;
	}

	public double getEligibleBorrowerAmt(Double eligibleBorrower, String memberId) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "getTotalApprovedAmt", "Entered");

		int dupItpanCnt = 0;
		double appApprovedAmt = 0.0;
//==================================================================================
		CallableStatement callableStmt2 = null;
		int status2 = -1;
		String errorCode2 = null;
		Connection con = DBConnection.getConnection();
		try {

			System.out.println("check ......................3..getEligibleBorrowerAmt..................");
			callableStmt2 = con.prepareCall("{call FUNCELIGIBLEBORROWERAMT(?,?,?,?,?,?)}");
			callableStmt2.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt2.setDouble(2, eligibleBorrower);
			callableStmt2.setString(3, memberId);

			callableStmt2.registerOutParameter(4, java.sql.Types.INTEGER);
			callableStmt2.registerOutParameter(5, java.sql.Types.INTEGER);
			callableStmt2.registerOutParameter(6, java.sql.Types.VARCHAR);
			callableStmt2.execute();
			status2 = callableStmt2.getInt(1);
			errorCode2 = callableStmt2.getString(6);
			if (status2 == Constants.FUNCTION_FAILURE) {
				// Log.log(Log.ERROR, "ClaimAction","getClaimSettlePaymentReportData()","SP
				// returns a 1. Error code is :" + errorCode);
				callableStmt2.close();
				// throw new DatabaseException(errorCode);
			} else if (status2 == Constants.FUNCTION_SUCCESS) {
				System.out.println("Data moved into main table successfully>>>>>>>>>>>>>>>>>>>>>>>");
				appApprovedAmt = callableStmt2.getInt(4);

				System.out
						.println("getEligibleBorrowerAmt>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>. " + dupItpanCnt);
				System.out.println(
						"getEligibleBorrowerAmt>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>. " + appApprovedAmt);
				Log.log(4, "ApplicationDAO", "getClaimCount", "getClaimCount :" + dupItpanCnt);
			}
			callableStmt2.close();
			callableStmt2 = null;
		} catch (SQLException sqlexception) {
			// Log.log(Log.ERROR,
			// "ClaimAction","getClaimSettlePaymentSavedMLIWiseCK2Data()","Error retrieving
			// all Claim settled Payment Process Data!");
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(con);
			// con.commit();
		}
		return appApprovedAmt;
	}

	public double gethoReportTotalOs(Double eligibleBorrower, String memberId) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "getTotalApprovedAmt", "Entered");

		int dupItpanCnt = 0;
		double appApprovedAmt = 0.0;
//==================================================================================
		CallableStatement callableStmt2 = null;
		int status2 = -1;
		String errorCode2 = null;
		Connection con = DBConnection.getConnection();
		try {

			System.out.println("check ......................3..getEligibleBorrowerAmt..................");
			callableStmt2 = con.prepareCall("{call funhoReportTotalOs(?,?,?,?,?,?)}");
			callableStmt2.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt2.setDouble(2, eligibleBorrower);
			callableStmt2.setString(3, memberId);

			callableStmt2.registerOutParameter(4, java.sql.Types.INTEGER);
			callableStmt2.registerOutParameter(5, java.sql.Types.INTEGER);
			callableStmt2.registerOutParameter(6, java.sql.Types.VARCHAR);
			callableStmt2.execute();
			status2 = callableStmt2.getInt(1);
			errorCode2 = callableStmt2.getString(6);
			if (status2 == Constants.FUNCTION_FAILURE) {
				// Log.log(Log.ERROR, "ClaimAction","getClaimSettlePaymentReportData()","SP
				// returns a 1. Error code is :" + errorCode);
				callableStmt2.close();
				// throw new DatabaseException(errorCode);
			} else if (status2 == Constants.FUNCTION_SUCCESS) {
				System.out.println("Data moved into main table successfully>>>>>>>>>>>>>>>>>>>>>>>");
				appApprovedAmt = callableStmt2.getInt(4);

				System.out
						.println("getEligibleBorrowerAmt>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>. " + dupItpanCnt);
				System.out.println(
						"getEligibleBorrowerAmt>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>. " + appApprovedAmt);
				Log.log(4, "ApplicationDAO", "getClaimCount", "getClaimCount :" + dupItpanCnt);
			}
			callableStmt2.close();
			callableStmt2 = null;
		} catch (SQLException sqlexception) {
			// Log.log(Log.ERROR,
			// "ClaimAction","getClaimSettlePaymentSavedMLIWiseCK2Data()","Error retrieving
			// all Claim settled Payment Process Data!");
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(con);
			// con.commit();
		}
		return appApprovedAmt;
	}

	public double gethoReport20EligOsAmt(Double eligibleBorrower, String memberId) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "getTotalApprovedAmt", "Entered");

		int dupItpanCnt = 0;
		double appApprovedAmt = 0.0;
//==================================================================================
		CallableStatement callableStmt2 = null;
		int status2 = -1;
		String errorCode2 = null;
		Connection con = DBConnection.getConnection();
		try {

			System.out.println("check ......................3..getEligibleBorrowerAmt..................");
			callableStmt2 = con.prepareCall("{call funhoReport20EligOsAmt(?,?,?,?,?,?)}");
			callableStmt2.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt2.setDouble(2, eligibleBorrower);
			callableStmt2.setString(3, memberId);

			callableStmt2.registerOutParameter(4, java.sql.Types.INTEGER);
			callableStmt2.registerOutParameter(5, java.sql.Types.INTEGER);
			callableStmt2.registerOutParameter(6, java.sql.Types.VARCHAR);
			callableStmt2.execute();
			status2 = callableStmt2.getInt(1);
			errorCode2 = callableStmt2.getString(6);
			if (status2 == Constants.FUNCTION_FAILURE) {
				// Log.log(Log.ERROR, "ClaimAction","getClaimSettlePaymentReportData()","SP
				// returns a 1. Error code is :" + errorCode);
				callableStmt2.close();
				// throw new DatabaseException(errorCode);
			} else if (status2 == Constants.FUNCTION_SUCCESS) {
				System.out.println("Data moved into main table successfully>>>>>>>>>>>>>>>>>>>>>>>");
				appApprovedAmt = callableStmt2.getInt(4);

				System.out
						.println("getEligibleBorrowerAmt>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>. " + dupItpanCnt);
				System.out.println(
						"getEligibleBorrowerAmt>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>. " + appApprovedAmt);
				Log.log(4, "ApplicationDAO", "getClaimCount", "getClaimCount :" + dupItpanCnt);
			}
			callableStmt2.close();
			callableStmt2 = null;
		} catch (SQLException sqlexception) {
			// Log.log(Log.ERROR,
			// "ClaimAction","getClaimSettlePaymentSavedMLIWiseCK2Data()","Error retrieving
			// all Claim settled Payment Process Data!");
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(con);
			// con.commit();
		}
		return appApprovedAmt;
	}

	public double gethoReportNoBorrowerOfferAmt(Double eligibleBorrower, String memberId) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "getTotalApprovedAmt", "Entered");

		int dupItpanCnt = 0;
		double appApprovedAmt = 0.0;
//==================================================================================
		CallableStatement callableStmt2 = null;
		int status2 = -1;
		String errorCode2 = null;
		Connection con = DBConnection.getConnection();
		try {

			System.out.println("check ......................3..getEligibleBorrowerAmt..................");
			callableStmt2 = con.prepareCall("{call funhoReportNoBOfferAmt(?,?,?,?,?,?)}");
			callableStmt2.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt2.setDouble(2, eligibleBorrower);
			callableStmt2.setString(3, memberId);

			callableStmt2.registerOutParameter(4, java.sql.Types.INTEGER);
			callableStmt2.registerOutParameter(5, java.sql.Types.INTEGER);
			callableStmt2.registerOutParameter(6, java.sql.Types.VARCHAR);
			callableStmt2.execute();
			status2 = callableStmt2.getInt(1);
			errorCode2 = callableStmt2.getString(6);
			if (status2 == Constants.FUNCTION_FAILURE) {
				// Log.log(Log.ERROR, "ClaimAction","getClaimSettlePaymentReportData()","SP
				// returns a 1. Error code is :" + errorCode);
				callableStmt2.close();
				// throw new DatabaseException(errorCode);
			} else if (status2 == Constants.FUNCTION_SUCCESS) {
				System.out.println("Data moved into main table successfully>>>>>>>>>>>>>>>>>>>>>>>");
				appApprovedAmt = callableStmt2.getInt(4);

				System.out
						.println("getEligibleBorrowerAmt>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>. " + dupItpanCnt);
				System.out.println(
						"getEligibleBorrowerAmt>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>. " + appApprovedAmt);
				Log.log(4, "ApplicationDAO", "getClaimCount", "getClaimCount :" + dupItpanCnt);
			}
			callableStmt2.close();
			callableStmt2 = null;
		} catch (SQLException sqlexception) {
			// Log.log(Log.ERROR,
			// "ClaimAction","getClaimSettlePaymentSavedMLIWiseCK2Data()","Error retrieving
			// all Claim settled Payment Process Data!");
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(con);
			// con.commit();
		}
		return appApprovedAmt;
	}

	public double gethoReportNoBorrowerOptAmt(Double eligibleBorrower, String memberId) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "getTotalApprovedAmt", "Entered");

		int dupItpanCnt = 0;
		double appApprovedAmt = 0.0;
//==================================================================================
		CallableStatement callableStmt2 = null;
		int status2 = -1;
		String errorCode2 = null;
		Connection con = DBConnection.getConnection();
		try {

			System.out.println("check ......................3..getEligibleBorrowerAmt..................");
			callableStmt2 = con.prepareCall("{call funhoReportNoBOptAmt(?,?,?,?,?,?)}");
			callableStmt2.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt2.setDouble(2, eligibleBorrower);
			callableStmt2.setString(3, memberId);

			callableStmt2.registerOutParameter(4, java.sql.Types.INTEGER);
			callableStmt2.registerOutParameter(5, java.sql.Types.INTEGER);
			callableStmt2.registerOutParameter(6, java.sql.Types.VARCHAR);
			callableStmt2.execute();
			status2 = callableStmt2.getInt(1);
			errorCode2 = callableStmt2.getString(6);
			if (status2 == Constants.FUNCTION_FAILURE) {
				// Log.log(Log.ERROR, "ClaimAction","getClaimSettlePaymentReportData()","SP
				// returns a 1. Error code is :" + errorCode);
				callableStmt2.close();
				// throw new DatabaseException(errorCode);
			} else if (status2 == Constants.FUNCTION_SUCCESS) {
				System.out.println("Data moved into main table successfully>>>>>>>>>>>>>>>>>>>>>>>");
				appApprovedAmt = callableStmt2.getInt(4);

				System.out
						.println("getEligibleBorrowerAmt>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>. " + dupItpanCnt);
				System.out.println(
						"getEligibleBorrowerAmt>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>. " + appApprovedAmt);
				Log.log(4, "ApplicationDAO", "getClaimCount", "getClaimCount :" + dupItpanCnt);
			}
			callableStmt2.close();
			callableStmt2 = null;
		} catch (SQLException sqlexception) {
			// Log.log(Log.ERROR,
			// "ClaimAction","getClaimSettlePaymentSavedMLIWiseCK2Data()","Error retrieving
			// all Claim settled Payment Process Data!");
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(con);
			// con.commit();
		}
		return appApprovedAmt;
	}

	public double gethoReportSanctionNoAmt(Double eligibleBorrower, String memberId) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "getTotalApprovedAmt", "Entered");

		int dupItpanCnt = 0;
		double appApprovedAmt = 0.0;
//==================================================================================
		CallableStatement callableStmt2 = null;
		int status2 = -1;
		String errorCode2 = null;
		Connection con = DBConnection.getConnection();
		try {

			System.out.println("check ......................3..getEligibleBorrowerAmt..................");
			callableStmt2 = con.prepareCall("{call funhoReportSanctionNoAmt(?,?,?,?,?,?)}");
			callableStmt2.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt2.setDouble(2, eligibleBorrower);
			callableStmt2.setString(3, memberId);

			callableStmt2.registerOutParameter(4, java.sql.Types.INTEGER);
			callableStmt2.registerOutParameter(5, java.sql.Types.INTEGER);
			callableStmt2.registerOutParameter(6, java.sql.Types.VARCHAR);
			callableStmt2.execute();
			status2 = callableStmt2.getInt(1);
			errorCode2 = callableStmt2.getString(6);
			if (status2 == Constants.FUNCTION_FAILURE) {
				// Log.log(Log.ERROR, "ClaimAction","getClaimSettlePaymentReportData()","SP
				// returns a 1. Error code is :" + errorCode);
				callableStmt2.close();
				// throw new DatabaseException(errorCode);
			} else if (status2 == Constants.FUNCTION_SUCCESS) {
				System.out.println("Data moved into main table successfully>>>>>>>>>>>>>>>>>>>>>>>");
				appApprovedAmt = callableStmt2.getInt(4);

				System.out
						.println("getEligibleBorrowerAmt>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>. " + dupItpanCnt);
				System.out.println(
						"getEligibleBorrowerAmt>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>. " + appApprovedAmt);
				Log.log(4, "ApplicationDAO", "getClaimCount", "getClaimCount :" + dupItpanCnt);
			}
			callableStmt2.close();
			callableStmt2 = null;
		} catch (SQLException sqlexception) {
			// Log.log(Log.ERROR,
			// "ClaimAction","getClaimSettlePaymentSavedMLIWiseCK2Data()","Error retrieving
			// all Claim settled Payment Process Data!");
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(con);
			// con.commit();
		}
		return appApprovedAmt;
	}

	public double gethoReportSanctionAmt(Double eligibleBorrower, String memberId) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "getTotalApprovedAmt", "Entered");

		int dupItpanCnt = 0;
		double appApprovedAmt = 0.0;
//==================================================================================
		CallableStatement callableStmt2 = null;
		int status2 = -1;
		String errorCode2 = null;
		Connection con = DBConnection.getConnection();
		try {

			System.out.println("check ......................3..getEligibleBorrowerAmt..................");
			callableStmt2 = con.prepareCall("{call funhoReportSanctionAmt(?,?,?,?,?,?)}");
			callableStmt2.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt2.setDouble(2, eligibleBorrower);
			callableStmt2.setString(3, memberId);

			callableStmt2.registerOutParameter(4, java.sql.Types.INTEGER);
			callableStmt2.registerOutParameter(5, java.sql.Types.INTEGER);
			callableStmt2.registerOutParameter(6, java.sql.Types.VARCHAR);
			callableStmt2.execute();
			status2 = callableStmt2.getInt(1);
			errorCode2 = callableStmt2.getString(6);
			if (status2 == Constants.FUNCTION_FAILURE) {
				// Log.log(Log.ERROR, "ClaimAction","getClaimSettlePaymentReportData()","SP
				// returns a 1. Error code is :" + errorCode);
				callableStmt2.close();
				// throw new DatabaseException(errorCode);
			} else if (status2 == Constants.FUNCTION_SUCCESS) {
				System.out.println("Data moved into main table successfully>>>>>>>>>>>>>>>>>>>>>>>");
				appApprovedAmt = callableStmt2.getInt(4);

				System.out
						.println("getEligibleBorrowerAmt>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>. " + dupItpanCnt);
				System.out.println(
						"getEligibleBorrowerAmt>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>. " + appApprovedAmt);
				Log.log(4, "ApplicationDAO", "getClaimCount", "getClaimCount :" + dupItpanCnt);
			}
			callableStmt2.close();
			callableStmt2 = null;
		} catch (SQLException sqlexception) {
			// Log.log(Log.ERROR,
			// "ClaimAction","getClaimSettlePaymentSavedMLIWiseCK2Data()","Error retrieving
			// all Claim settled Payment Process Data!");
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(con);
			// con.commit();
		}
		return appApprovedAmt;
	}

	public double gethoReportDisburseNoAmt(Double eligibleBorrower, String memberId) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "getTotalApprovedAmt", "Entered");

		int dupItpanCnt = 0;
		double appApprovedAmt = 0.0;
//==================================================================================
		CallableStatement callableStmt2 = null;
		int status2 = -1;
		String errorCode2 = null;
		Connection con = DBConnection.getConnection();
		try {

			System.out.println("check ......................3..getEligibleBorrowerAmt..................");
			callableStmt2 = con.prepareCall("{call funhoReportDisburseNoAmt(?,?,?,?,?,?)}");
			callableStmt2.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt2.setDouble(2, eligibleBorrower);
			callableStmt2.setString(3, memberId);

			callableStmt2.registerOutParameter(4, java.sql.Types.INTEGER);
			callableStmt2.registerOutParameter(5, java.sql.Types.INTEGER);
			callableStmt2.registerOutParameter(6, java.sql.Types.VARCHAR);
			callableStmt2.execute();
			status2 = callableStmt2.getInt(1);
			errorCode2 = callableStmt2.getString(6);
			if (status2 == Constants.FUNCTION_FAILURE) {
				// Log.log(Log.ERROR, "ClaimAction","getClaimSettlePaymentReportData()","SP
				// returns a 1. Error code is :" + errorCode);
				callableStmt2.close();
				// throw new DatabaseException(errorCode);
			} else if (status2 == Constants.FUNCTION_SUCCESS) {
				System.out.println("Data moved into main table successfully>>>>>>>>>>>>>>>>>>>>>>>");
				appApprovedAmt = callableStmt2.getInt(4);

				System.out
						.println("getEligibleBorrowerAmt>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>. " + dupItpanCnt);
				System.out.println(
						"getEligibleBorrowerAmt>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>. " + appApprovedAmt);
				Log.log(4, "ApplicationDAO", "getClaimCount", "getClaimCount :" + dupItpanCnt);
			}
			callableStmt2.close();
			callableStmt2 = null;
		} catch (SQLException sqlexception) {
			// Log.log(Log.ERROR,
			// "ClaimAction","getClaimSettlePaymentSavedMLIWiseCK2Data()","Error retrieving
			// all Claim settled Payment Process Data!");
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(con);
			// con.commit();
		}
		return appApprovedAmt;
	}

	public double gethoReportDisburseAmt(Double eligibleBorrower, String memberId) throws DatabaseException {
		Log.log(4, "ApplicationDAO", "getTotalApprovedAmt", "Entered");

		int dupItpanCnt = 0;
		double appApprovedAmt = 0.0;
//==================================================================================
		CallableStatement callableStmt2 = null;
		int status2 = -1;
		String errorCode2 = null;
		Connection con = DBConnection.getConnection();
		try {

			System.out.println("check ......................3..getEligibleBorrowerAmt..................");
			callableStmt2 = con.prepareCall("{call funhoReportDisburseAmt(?,?,?,?,?,?)}");
			callableStmt2.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt2.setDouble(2, eligibleBorrower);
			callableStmt2.setString(3, memberId);

			callableStmt2.registerOutParameter(4, java.sql.Types.INTEGER);
			callableStmt2.registerOutParameter(5, java.sql.Types.INTEGER);
			callableStmt2.registerOutParameter(6, java.sql.Types.VARCHAR);
			callableStmt2.execute();
			status2 = callableStmt2.getInt(1);
			errorCode2 = callableStmt2.getString(6);
			if (status2 == Constants.FUNCTION_FAILURE) {
				// Log.log(Log.ERROR, "ClaimAction","getClaimSettlePaymentReportData()","SP
				// returns a 1. Error code is :" + errorCode);
				callableStmt2.close();
				// throw new DatabaseException(errorCode);
			} else if (status2 == Constants.FUNCTION_SUCCESS) {
				System.out.println("Data moved into main table successfully>>>>>>>>>>>>>>>>>>>>>>>");
				appApprovedAmt = callableStmt2.getInt(4);

				System.out
						.println("getEligibleBorrowerAmt>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>. " + dupItpanCnt);
				System.out.println(
						"getEligibleBorrowerAmt>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>. " + appApprovedAmt);
				Log.log(4, "ApplicationDAO", "getClaimCount", "getClaimCount :" + dupItpanCnt);
			}
			callableStmt2.close();
			callableStmt2 = null;
		} catch (SQLException sqlexception) {
			// Log.log(Log.ERROR,
			// "ClaimAction","getClaimSettlePaymentSavedMLIWiseCK2Data()","Error retrieving
			// all Claim settled Payment Process Data!");
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(con);
			// con.commit();
		}
		return appApprovedAmt;
	}
	
	
	
	
	public String submitEGSApplicationDetails(Application apps, String createdBy, Connection connection)
			throws DatabaseException {
		Log.log(4, "ApplicationDAO", "submitEGSApplicationDetails", "Exited");

		String appLoanType = apps.getLoanType();
		Log.log(4, "ApplicationDAO", "submitEGSApplicationDetails", "Entering Application Detail method...");
		System.out.println("===submitEGSApplicationDetails====");
		try {
			
			MLIInfo mliInfo = new MLIInfo(); // Added by DKR
			
			CallableStatement applicationDetails = connection.prepareCall(
					"{call FUNC_INSERT_EGS_APPL_DTL( ?,?,?,?,?,?,?,?,?,?,"
														+ "?,?,?,?,?,?,?,?,?,?,"
														+ "?,?,?,?,?,?,?,?,?,?,"
														+ "?,?,?,?,?,?,?,?,?,?,"
														+ "?,?,?,?,?,?,?,?,?,?,"
														+ "?,?,?,?,?,?,?,?,?,?,"
														+ "?,?,?,?,?,?,?,?,?,?,"
														+ "?,?,?,?,?,?,?,?,?,?,"
														+ "?,?)}");

			applicationDetails.registerOutParameter(1, 4);
			applicationDetails.registerOutParameter(81, 12);
			applicationDetails.registerOutParameter(82, 12);
			
			applicationDetails.setString(2, apps.getBankId());
			Log.log(5, "ApplicationDAO", "submitApplicationDetails", "Bank Id :" + apps.getBankId());

			applicationDetails.setString(3, apps.getZoneId());
			Log.log(5, "ApplicationDAO", "submitApplicationDetails", "Zone id :" + apps.getZoneId());

			applicationDetails.setString(4, apps.getBranchId());
			Log.log(5, "ApplicationDAO", "submitApplicationDetails", "Branch id :" + apps.getBranchId());

			applicationDetails.setString(5, createdBy);
			Log.log(5, "ApplicationDAO", "submitApplicationDetails", "user id :" + createdBy);

			applicationDetails.setString(6, apps.getLoanType());
			Log.log(5, "ApplicationDAO", "submitApplicationDetails", "Loan type :" + apps.getLoanType());

			if ((apps.getRemarks() != null) && (!apps.getRemarks().equals(""))) {
				applicationDetails.setString(7, apps.getRemarks());
			} else {
				applicationDetails.setString(7, null);
			}
			Log.log(5, "ApplicationDAO", "submitApplicationDetails", "Remarks :" + apps.getRemarks());

			
			applicationDetails.setString(8, apps.getGstState()); 
			
			applicationDetails.setString(9, apps.getGstNo()); 
			
			applicationDetails.setLong(10, apps.getProMobileNo());

			
			applicationDetails.setInt(11, apps.getDPDStatus());
			Log.log(5, "ApplicationDAO", "submitTermCreditDetails", "getDPDStatus:" + apps.getDPDStatus());
			
			applicationDetails.setDouble(12, apps.getTypeLoanWC());
			
			applicationDetails.setDouble(13, apps.getTypeLoanTC());
			
			applicationDetails.setString(14, apps.getActivityConfirm());
			
			if (apps.getTermLoan().getCreditGuaranteed() == 0.0D) {
				applicationDetails.setNull(15, 8);
			} else {
				applicationDetails.setDouble(15, apps.getTermLoan().getCreditGuaranteed());
				Log.log(5, "ApplicationDAO", "submitTermCreditDetails",
						"CG:" + apps.getTermLoan().getCreditGuaranteed());
			}
			
			applicationDetails.setDouble(16, apps.getTotalMIcollatSecAmt());
				Log.log(5, "ApplicationDAO", "submitTermCreditDetails", "tenire:" + apps.getTotalMIcollatSecAmt());
			
			applicationDetails.setString(17, apps.getPromDirDefaltFlg());
			
			applicationDetails.setDouble(18, apps.getDisbursementAmt());
			Log.log(5, "ApplicationDAO", "submitTermCreditDetails",
					"Amount disb" + apps.getDisbursementAmt());
			
			
			if ((apps.getUdyogAdharNo()) != null && (!apps.getUdyogAdharNo().equals(""))) {
				applicationDetails.setString(19, apps.getUdyogAdharNo());
			} else {
				applicationDetails.setString(19, null);
			}
			
			if ((apps.getBankAcNo() != null) && (!apps.getBankAcNo().equals(""))) {
				applicationDetails.setString(20, apps.getBankAcNo());
			} else {
				applicationDetails.setString(20, null);
			}

			if ((apps.getAdharNo() != null) && (!apps.getAdharNo().equals(""))) {
				applicationDetails.setString(21, apps.getAdharNo());
			} else {
				applicationDetails.setString(21, null);

			}

			if ((apps.getBorrowergstNo() != null) && (!apps.getBorrowergstNo().equals(""))) {
				applicationDetails.setString(22, apps.getBorrowergstNo());
			} else {
				applicationDetails.setString(22, null);

			}
			
			if ((apps.getSanctionDate() != null)
					&& (!apps.getSanctionDate().toString().equals(""))) {
				applicationDetails.setDate(23,
						new java.sql.Date(apps.getSanctionDate().getTime()));
				Log.log(5, "ApplicationDAO", "submitTermCreditDetails", "trm sancitoned Date:"
						+ new java.sql.Date(apps.getSanctionDate().getTime()));
			} else {
				applicationDetails.setDate(23, null);
			}
			
			applicationDetails.setDouble(24, apps.getTermLoan().getPplOS());
			Log.log(5, "ApplicationDAO", "submitTermCreditDetails", "pplos:" + apps.getTermLoan().getPplOS());

			if (apps.getTermLoan().getTenure() == 0) {
				applicationDetails.setNull(25, 4);
			} else {
				applicationDetails.setInt(25, apps.getTermLoan().getTenure());
				Log.log(5, "ApplicationDAO", "submitTermCreditDetails", "tenire:" + apps.getTermLoan().getTenure());
			}

			if (apps.getTermLoan().getInterestRate() == 0.0D) {
				applicationDetails.setNull(26, 8);
			} else {
				applicationDetails.setDouble(26, apps.getTermLoan().getInterestRate());
				Log.log(5, "ApplicationDAO", "submitTermCreditDetails",
						"interest type:" + apps.getTermLoan().getInterestRate());
			}

		
			if (apps.getTermLoan().getRepaymentMoratorium() == 0) {
				applicationDetails.setNull(27, 4);
			} else {
				applicationDetails.setInt(27, apps.getTermLoan().getRepaymentMoratorium());
				Log.log(5, "ApplicationDAO", "submitTermCreditDetails",
						"moratorium:" + apps.getTermLoan().getRepaymentMoratorium());
			}
			

			if (apps.getTermLoan().getCreditGuaranteed() == 0.0D) {
				applicationDetails.setNull(28, 8);
			} else {
				applicationDetails.setDouble(28, apps.getTermLoan().getCreditGuaranteed());
				Log.log(5, "ApplicationDAO", "submitTermCreditDetails",
						"CG:" + apps.getTermLoan().getCreditGuaranteed());
			}
			
			
			applicationDetails.setString(29, apps.getSsiName().toUpperCase());
			
			Log.log(5, "ApplicationDAO", "submitApp",
						"ssi name :" + apps.getSsiName());
			
				applicationDetails.setString(30, apps.getAddress().toUpperCase());
			
			
			if ((apps.getCity() != null)
					&& (!apps.getCity().equals(""))) {
				applicationDetails.setString(31, apps.getCity().toUpperCase());
			} else {
				applicationDetails.setString(31, null);
			}
			
			
			applicationDetails.setString(32, apps.getState());
			Log.log(5, "ApplicationDAO", "submitApp",
					"State :" + apps.getState());
			
			applicationDetails.setString(33, apps.getDistrict());
			Log.log(5, "ApplicationDAO", "submitApp",
					"district :" + apps.getDistrict());
			
			
			applicationDetails.setString(34, apps.getPincode());
			Log.log(5, "ApplicationDAO", "submitApp",
					"pincode :" + apps.getPincode());

			applicationDetails.setString(35, apps.getSsiType());
			Log.log(5, "ApplicationDAO", "submitApp",
					"ssi Type :" + apps.getSsiType());


			if ((apps.getIndustryNature() != null)
					&& (!apps.getIndustryNature().equals(""))) {
				applicationDetails.setString(36, apps.getIndustryNature());
			} else {
				applicationDetails.setString(36, null);
			}
			Log.log(5, "ApplicationDAO", "submitApp",
					"nature :" + apps.getIndustryNature());

			if ((apps.getIndustrySector() != null)
					&& (!apps.getIndustrySector().equals(""))) {
				applicationDetails.setString(37, apps.getIndustrySector());
			} else {
				applicationDetails.setString(37, null);
			}
			Log.log(5, "ApplicationDAO", "submitApp",
					"sector : " + apps.getIndustrySector());

			if ((apps.getConstitution() != null)
					&& (!apps.getConstitution().equals(""))) {
				applicationDetails.setString(38, apps.getConstitution());
			} else {
				applicationDetails.setString(38, null);
			}
			Log.log(5, "ApplicationDAO", "submitApp",
					"Const :" + apps.getConstitution());
			

			if (apps.getEmployeeNos() == 0) {
				applicationDetails.setNull(39, 4);
			} else
				applicationDetails.setInt(39, apps.getEmployeeNos());

			Log.log(5, "ApplicationDAO", "submitApp",
					"employee nos :" + apps.getEmployeeNos());
			
			
			if (apps.getProjectedSalesTurnover() == 0.0D) {
				applicationDetails.setNull(40, 8);
			} else
				applicationDetails.setDouble(40, apps.getProjectedSalesTurnover());

			Log.log(5, "ApplicationDAO", "submitApp",
					"sales :" + apps.getProjectedSalesTurnover());
			

			if ((apps.getSsiITPan() != null)
					&& (!apps.getSsiITPan().equals(""))) {
				applicationDetails.setString(41, apps.getSsiITPan());
			} else {
				applicationDetails.setString(41, null);
			}
			Log.log(5, "ApplicationDAO", "submitApp",
					"ssi ITPAN :" + apps.getSsiITPan());
			
			
			if ((apps.getActivityType() != null)
					&& (!apps.getActivityType().equals(""))) {
				applicationDetails.setString(42, apps.getActivityType());
			} else {
				applicationDetails.setString(42, null);
			}
			Log.log(5, "ApplicationDAO", "submitApp",
					"activity Type :" + apps.getActivityType());

			
			if (apps.getOsMLI() == 0.0D) {
				applicationDetails.setNull(43, 8);
			} else
				applicationDetails.setDouble(43, apps.getOsMLI());
			Log.log(5, "ApplicationDAO", "submitApp", "sales :" + apps.getOsMLI());
			
		
			if ((apps.getEnterprise() != null)
					&& (!apps.getEnterprise().equals(""))) {
				applicationDetails.setString(44, apps.getEnterprise());
			} else {
				applicationDetails.setString(44, "N");
			}

			
			if ((apps.getmSE() != null)
					&& (!apps.getmSE().equals(""))) {
				applicationDetails.setString(45, apps.getmSE());
			} else {
				applicationDetails.setString(45, "N");
			}
			Log.log(5, "ApplicationDAO", "submitApplicationDetails",
					" MSE:" + apps.getmSE());
			

			if ((apps.getMudraType() != null)
					&& (!apps.getMudraType().equals(""))) {
				applicationDetails.setString(46, apps.getMudraType());
			} else {
				applicationDetails.setString(46, null);
			}
			Log.log(5, "ApplicationDAO", "submitApp",
					"activity Type :" + apps.getMudraType());
			
			if ((apps.getMudraITPANChk() != null)
					&& (!apps.getMudraITPANChk().equals(""))) {
				applicationDetails.setString(47, apps.getMudraITPANChk());
			} else {
				applicationDetails.setString(47, null);
			}
			Log.log(5, "ApplicationDAO", "submitApp",
					"activity Type :" + apps.getMudraITPANChk());
			
			if ((apps.getCpITPAN() != null)
					&& (!apps.getCpITPAN().equals(""))) {
				applicationDetails.setString(48, apps.getCpITPAN());
			} else {
				applicationDetails.setString(48, null);
			}

			applicationDetails.setString(49, apps.getCpGender());
			Log.log(5, "ApplicationDAO", "submitPromotersDetails",
					"cp gender:" + apps.getCpGender());
			
			applicationDetails.setDouble(50, apps.getSancAmt());
			applicationDetails.setDouble(51,apps.getOutstandingAmt());
			applicationDetails.setString(52,apps.getAccStatus());
			
			applicationDetails.setDouble(53, apps.getWcSancAmt());
			applicationDetails.setDouble(54,apps.getWcOutstandingAmt());
			applicationDetails.setString(55,apps.getWcAccStatus());
			
			applicationDetails.setDouble(56, apps.getOtherSancAmt());
			applicationDetails.setDouble(57,apps.getOtherOutstandingAmt());
			applicationDetails.setString(58,apps.getOtherAccStatus());
			
			applicationDetails.setDate(59,new java.sql.Date(apps.getNpaDate().getTime()));
			
			applicationDetails.setString(60,apps.getNpaReason());
			
			applicationDetails.setString(61,apps.getIsAcctReconstructed());
			
			//applicationDetails.setDouble(62,apps.getPmrEquity());
			
			
			//applicationDetails.setDouble(63,apps.getPmrDebt());
			
			applicationDetails.setDouble(62, apps.getOtherBanksancAmt());
			applicationDetails.setDouble(63,apps.getOtherBankoutstandingAmt());
			applicationDetails.setString(64,apps.getOtherBankaccStatus());
			
			applicationDetails.setDouble(65, apps.getOtherBankwcSancAmt());
			applicationDetails.setDouble(66,apps.getOtherBankwcOutstandingAmt());
			applicationDetails.setString(67,apps.getOtherBankwcAccStatus());
			
			applicationDetails.setDouble(68, apps.getOtherBankotherSancAmt());
			applicationDetails.setDouble(69,apps.getOtherBankotherOutstandingAmt());
			applicationDetails.setString(70,apps.getOtherBankotherAccStatus());
			
			applicationDetails.setDouble(71, apps.getLandValue());
			applicationDetails.setDouble(72, apps.getMachineValue());
			applicationDetails.setDouble(73, apps.getBldgValue());
			applicationDetails.setDouble(74, apps.getCurrentAssetsValue());
			applicationDetails.setDouble(75, apps.getAssetsValue());
			applicationDetails.setDouble(76, apps.getOthersValue());
			applicationDetails.setDouble(77, apps.getPresentSalesTurnover());
			applicationDetails.setString(78, apps.getSecurityRemarks());
			applicationDetails.setString(79, apps.getCgpan());
			applicationDetails.setDouble(80, apps.getSanctionedAmount());
			



			

			applicationDetails.executeQuery();
			System.out.println("SUBDEBT TABLE INSERT=="+applicationDetails);
			int functionReturnValue = applicationDetails.getInt(1);
			Log.log(5, "ApplicationDAO", "submitApplicationDetails", "Application Details :" + functionReturnValue);
			
			if (functionReturnValue == 1) {
				String error = applicationDetails.getString(82);

				applicationDetails.close();
				applicationDetails = null;

				connection.rollback();

				Log.log(2, "ApplicationDAO", "submitApp", "Application Detail Exception" + error);
				// System.out.println("submitApplication 4 error="+error);
				throw new DatabaseException(error);
			}

			apps.setAppRefNo(applicationDetails.getString(81));

			applicationDetails.close();
			applicationDetails = null;
			System.out.println("Diksha---5");
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			Log.log(4, "ApplicationDAO", "submitApplicationDetails", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();

			} catch (SQLException ignore) {
				sqlException.printStackTrace();
				Log.log(4, "ApplicationDAO", "submitApplicationDetails", ignore.getMessage());
			}
			// System.out.println("submitApplication 5 error="+sqlException.getMessage());
			// throw new DatabaseException(sqlException.getMessage());
		}

		//String applicationRefNo = apps.getAppRefNo();
		
		String applicationRefNo = apps.getAppRefNo();

		Log.log(4, "ApplicationDAO", "submitApplicationDetails", "Application Reference No. :" + applicationRefNo);

		Log.log(4, "ApplicationDAO", "submitApplicationDetails", "Exited");

		return applicationRefNo;
	}
	
	public String getsanctionDateError(String mliId, Date date) throws DatabaseException
	   {
			String errorMessage = null;
			System.out.println("DATE:::::"+date);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
 		    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
 		    String date1 = null;
 		    try {
				 date1 = sdf2.format(sdf.parse(date.toString()));
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			Connection connection=DBConnection.getConnection();
			try{
					CallableStatement sanctionDateError=connection.prepareCall(
												"{call FUNC_SANCTION_DATE_VALIDATE(?,?,?,?)}");
					sanctionDateError.registerOutParameter(1,Types.INTEGER);
					sanctionDateError.setString(2, mliId);
					sanctionDateError.setString(3,date1);
					sanctionDateError.registerOutParameter(4, Types.VARCHAR);
					sanctionDateError.execute();

					int functionReturn=sanctionDateError.getInt(1);
					String error=sanctionDateError.getString(4).substring(0,10);
					//String val = error.;

					if(functionReturn==Constants.FUNCTION_SUCCESS){

						sanctionDateError.close();
						sanctionDateError=null;
						errorMessage = null;
					}
					else{
						errorMessage = error;
					
						}
				
			}catch(SQLException e){

						Log.logException(e);

						throw new DatabaseException("error");
			}finally{
				DBConnection.freeConnection(connection);
			}

			return errorMessage;
	   }

}
