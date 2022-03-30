package com.cgtsi.claim;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;
import org.apache.struts.upload.FormFile;
import com.cgtsi.actionform.RecoveryActionForm;
import com.cgtsi.admin.Administrator;
import com.cgtsi.admin.Message;
import com.cgtsi.admin.User;
import com.cgtsi.application.Application;
import com.cgtsi.application.ApplicationDAO;
import com.cgtsi.common.Constants;
import com.cgtsi.common.DatabaseException;
import com.cgtsi.common.Log;
import com.cgtsi.common.Mailer;
import com.cgtsi.common.MailerException;
import com.cgtsi.common.MessageException;
import com.cgtsi.guaranteemaintenance.Disbursement;
import com.cgtsi.guaranteemaintenance.DisbursementAmount;
import com.cgtsi.guaranteemaintenance.GMProcessor;
import com.cgtsi.guaranteemaintenance.NPADetails;
import com.cgtsi.guaranteemaintenance.PeriodicInfo;
import com.cgtsi.guaranteemaintenance.Repayment;
import com.cgtsi.guaranteemaintenance.RepaymentAmount;
import com.cgtsi.investmentfund.ChequeDetails;
import com.cgtsi.investmentfund.IFDAO;
import com.cgtsi.receiptspayments.PaymentDetails;
import com.cgtsi.receiptspayments.RpDAO;
import com.cgtsi.receiptspayments.RpProcessor;
import com.cgtsi.receiptspayments.VoucherDetail;
import com.cgtsi.registration.MLIInfo;
import com.cgtsi.registration.NoMemberFoundException;
import com.cgtsi.registration.RegistrationDAO;
import com.cgtsi.reports.ReportDAO;
import com.cgtsi.util.DBConnection;
import com.cgtsi.util.DateHelper;

public class CPDAO {

	public String createPayId(PaymentDetails paymentDetails) throws DatabaseException {

		String payID = "";
		Connection connection = null;
		try {

			connection = DBConnection.getConnection();
			RpDAO rpDao = new RpDAO();
			payID = rpDao.insertInstrumentDetails(paymentDetails, connection);
		} catch (Exception e) {
			throw new DatabaseException(
					"Error while generating PayId , kindly contact to support[support@cgtmse.in] team");
		} finally {
			DBConnection.freeConnection(connection);
		}
		return payID;
	}

	public int saveRecoveryDetails(RecoveryActionForm objRecoveryActionForm, String payID, String userid,
			String claimSettledFlag) throws DatabaseException {
		int result = 0;
		Connection conn = null;
		try {

			conn = DBConnection.getConnection();
			CallableStatement callableStmt = null;
			java.util.Date currentDate = new java.util.Date();
			java.sql.Date currentSqlDate = new java.sql.Date(currentDate.getTime());
			callableStmt = conn.prepareCall(
					"{?=call PACKRECOVERYAFTRBEFORFRSTCLAIM.FUNRECOVERYAFTRBEFORFRSTCLAIM(?,?,?,?,?,?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			callableStmt.setString(2, payID);
			callableStmt.setString(3, objRecoveryActionForm.getClaimRefNo());
			callableStmt.setString(4, objRecoveryActionForm.getCgpan());
			callableStmt.setString(5, objRecoveryActionForm.getTypeOfRecovery());
			callableStmt.setString(6, objRecoveryActionForm.getRecoveredAmout());
			callableStmt.setString(7, objRecoveryActionForm.getLegalExpenses());
			callableStmt.setString(8, objRecoveryActionForm.getAmoutRemitted());
			// System.out.println("userid"+userid);
			// System.out.println("objRecoveryActionForm.getTypeOfRecovery()"+objRecoveryActionForm.getClaimRefNo());
			callableStmt.setString(9, userid);
			callableStmt.setDate(10, currentSqlDate);
			callableStmt.setString(11, claimSettledFlag);
			callableStmt.registerOutParameter(12, Types.VARCHAR);
			callableStmt.execute();

			String errorCode = callableStmt.getString(12);
			// System.out.println("saveRecoveryDetails errorCode "+errorCode);
		} catch (Exception e) {
			// e.printStackTrace();
			result = 1;
		} finally {
			DBConnection.freeConnection(conn);
		}
		return result;
	}

	public String daoFetchRecoveryDetails(String clmRefNo) throws DatabaseException {
		Log.log(4, "CPDAO", "getAllBorrowerIDs()", "Entered!");
		CallableStatement callableStmt = null;
		Connection conn = null;
		ResultSet resultset = null;
		String responseStr = "";
		String finalResponseStr = "";
		String errorCode = null;
		String claimSettledDecision = "";
		try {
			conn = DBConnection.getConnection();
			callableStmt = conn
					.prepareCall("{?=call PACKRECOVERYAFTRBEFORFRSTCLAIM.funcGetClaimApprovedDetails(?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			callableStmt.setString(2, clmRefNo);
			callableStmt.registerOutParameter(3, Constants.CURSOR);
			callableStmt.registerOutParameter(4, Types.VARCHAR);
			callableStmt.execute();
			errorCode = callableStmt.getString(4);
			// System.out.println("updateRecoveryInfoActionJsonCall errorCode "+errorCode);
			if (errorCode == null) {

				resultset = (ResultSet) callableStmt.getObject(3);
				// System.out.println("updateRecoveryInfoActionJsonCall errorCode is
				// resultset"+resultset);
				int ClaimRefNoCount = 0;
				while (resultset.next()) {
					// System.out.println(resultset.getInt(6)+"updateRecoveryInfoActionJsonCall
					// errorCode is resultset.next"+resultset.getString(1));
					String ClaimRefNo = resultset.getString(1);
					String cgpan = resultset.getString(2);
					String unitName = resultset.getString(3);
					String firstInstallmentAmot = resultset.getString(4);
					String previousRecoveredAmt = resultset.getString(5);
					claimSettledDecision = resultset.getString(6);
					ClaimRefNoCount++;
					responseStr += ClaimRefNo + "===" + cgpan + "===" + unitName + "===" + firstInstallmentAmot + "==="
							+ previousRecoveredAmt + "===";
					// System.out.println("updateRecoveryInfoActionJsonCall responseStr
					// "+responseStr);
				}
				if (ClaimRefNoCount > 0) {
					responseStr += Integer.toString(ClaimRefNoCount).concat("@").concat(claimSettledDecision);
					finalResponseStr += responseStr;
				} else {
					finalResponseStr = "0";
				}
				// System.out.println("updateRecoveryInfoActionJsonCall fullResponseStr
				// "+finalResponseStr);
			} else {
				// System.out.println("updateRecoveryInfoActionJsonCall errorCode is not null");
				finalResponseStr = "0";
			}

		} catch (SQLException sqlexception) {
			finalResponseStr = "0";
			throw new DatabaseException(
					"Error while fetching recovery details , kindly contact to support[support@cgtmse.in] team");
		} finally {
			DBConnection.freeConnection(conn);
		}

		return finalResponseStr;
	}

	public Vector getAllMemberIds() throws DatabaseException {
		Log.log(4, "CPDAO", "getAllMemberIds()", "Entered!");
		Vector memberIds = new Vector();
		RegistrationDAO regDAO = new RegistrationDAO();
		ArrayList memberInfoDetails = regDAO.getAllMembers();
		if (memberInfoDetails != null) {
			for (int i = 0; i < memberInfoDetails.size(); i++) {
				MLIInfo mliInfo = (MLIInfo) memberInfoDetails.get(i);
				String bankId = mliInfo.getBankId();
				String zoneId = mliInfo.getZoneId();
				String branchId = mliInfo.getBranchId();
				String memberId = bankId + zoneId + branchId;
				if (memberId != null) {
					if (!memberIds.contains(memberId)) {
						memberIds.add(memberId);
					}
				}
			}
		}
		if (memberIds.size() == 0) {
			Log.log(2, "CPDAO", "getAllMemberIds()", "No Member Ids in the database!");
			throw new DatabaseException("No Member Ids in the database");
		}
		Log.log(4, "CPDAO", "getAllMemberIds()", "Exited!");

		return memberIds;
	}

	public String getBorowwerForCGPAN(String cgpan) throws DatabaseException {
		Log.log(4, "CPDAO", "getBorowwerForCGPAN()", "Entered!");
		CallableStatement callableStmt = null;
		Connection conn = null;
		String borrowerid = null;

		int status = -1;
		String errorCode = null;
		try {
			conn = DBConnection.getConnection();
			callableStmt = conn.prepareCall("{? = call funcGetBIDforCGPAN(?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			callableStmt.setString(2, cgpan);
			callableStmt.registerOutParameter(3, Types.VARCHAR);
			callableStmt.registerOutParameter(4, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(4);

			if (status == 1) {
				Log.log(2, "CPDAO", "getBorowwerForCGPAN()", "SP returns a 1. Error code is :" + errorCode);
				callableStmt.close();
				throw new DatabaseException(errorCode);
			}
			if (status == 0) {
				borrowerid = callableStmt.getString(3);

				callableStmt.close();
			}
		} catch (SQLException sqlexception) {
			Log.log(2, "CPDAO", "getBorowwerForCGPAN()", "Error retrieving Borrower for the CGPAN!");
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}

		if (borrowerid == null) {
			Log.log(4, "CPDAO", "getBorowwerForCGPAN()", "There is no Borrower Id for the given CGPAN.");
			throw new DatabaseException("Please enter correct Borrower Id and(or) CGPAN");
		}
		Log.log(4, "CPDAO", "getBorowwerForCGPAN()", "Exited!");

		return borrowerid;
	}

	public ArrayList getAllBorrowerIDs(String bankId, String zoneId, String branchId) throws DatabaseException {
		Log.log(4, "CPDAO", "getAllBorrowerIDs()", "Entered!");
		CallableStatement callableStmt = null;
		Connection conn = null;
		ResultSet resultset = null;
		ArrayList borrowerIds = new ArrayList();

		int status = -1;
		String errorCode = null;
		try {
			conn = DBConnection.getConnection();
			callableStmt = conn.prepareCall("{?=call packGetBIDForMember.funcGetBIDForMember(?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			callableStmt.setString(2, bankId);
			callableStmt.setString(3, zoneId);
			callableStmt.setString(4, branchId);
			callableStmt.registerOutParameter(5, Constants.CURSOR);
			callableStmt.registerOutParameter(6, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(6);

			if (status == 1) {
				Log.log(2, "CPDAO", "getAllBorrowerIDs()", "SP returns a 1. Error code is :" + errorCode);
				callableStmt.close();
				throw new DatabaseException(errorCode);
			}
			if (status == 0) {
				resultset = (ResultSet) callableStmt.getObject(5);

				while (resultset.next()) {
					String borrowerid = resultset.getString(1);
					if (borrowerid != null) {
						if (!borrowerIds.contains(borrowerid)) {
							borrowerIds.add(borrowerid);
						}
					}
				}
				resultset.close();
				resultset = null;
				callableStmt.close();
			}
		} catch (SQLException sqlexception) {
			Log.log(2, "CPDAO", "getAllBorrowerIDs()", "Error retrieving all Borrower Ids!");
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}
		if (borrowerIds.size() == 0) {
			Log.log(4, "CPDAO", "getAllBorrowerIDs()", "There are no Borrower Ids for this Member Id!");
			throw new DatabaseException("There are no Borrower Ids for this Member Id!");
		}
		Log.log(4, "CPDAO", "getAllBorrowerIDs()", "Exited!");

		return borrowerIds;
	}

	public MemberInfo getMemberInfoDetails(String bankId, String zoneId, String branchId) throws DatabaseException {
		Log.log(4, "CPDAO", "getMemberInfoDetails()", "Entered!");

		String bankName = null;
		String branchName = null;
		String zoneName = null;
		String shortName = null;
		String city = null;
		String district = null;
		String stateName = null;
		String phoneCode = null;
		String phoneNumber = null;
		String email = null;
		String mcgf = null;
		String dandelivery = null;
		String memberStatus = null;
		String memberAddress = null;

		CallableStatement callableStmt = null;
		int status = -1;
		String errorCode = null;

		Connection conn = null;
		MemberInfo memberInfo = new MemberInfo();
		try {
			conn = DBConnection.getConnection();
			callableStmt = conn
					.prepareCall("{?=call funcGetDetailsForMember(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			callableStmt.setString(2, bankId);
			callableStmt.setString(3, zoneId);
			callableStmt.setString(4, branchId);
			callableStmt.registerOutParameter(5, Types.VARCHAR);
			callableStmt.registerOutParameter(6, Types.VARCHAR);
			callableStmt.registerOutParameter(7, Types.VARCHAR);
			callableStmt.registerOutParameter(8, Types.VARCHAR);
			callableStmt.registerOutParameter(9, Types.VARCHAR);
			callableStmt.registerOutParameter(10, Types.VARCHAR);
			callableStmt.registerOutParameter(11, Types.VARCHAR);
			callableStmt.registerOutParameter(12, Types.VARCHAR);
			callableStmt.registerOutParameter(13, Types.VARCHAR);
			callableStmt.registerOutParameter(14, Types.VARCHAR);
			callableStmt.registerOutParameter(15, Types.VARCHAR);
			callableStmt.registerOutParameter(16, Types.VARCHAR);
			callableStmt.registerOutParameter(17, Types.VARCHAR);

			callableStmt.registerOutParameter(18, Types.VARCHAR);
			callableStmt.registerOutParameter(19, Types.VARCHAR);
			callableStmt.registerOutParameter(20, Types.VARCHAR);
			callableStmt.registerOutParameter(21, Types.VARCHAR);
			callableStmt.registerOutParameter(22, Types.VARCHAR);

			callableStmt.registerOutParameter(23, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(23);

			if (status == 1) {
				Log.log(2, "CPDAO", "getAllBorrowerIDs()", "SP returns a 1. Error code is :" + errorCode);

				callableStmt.close();
				throw new DatabaseException(errorCode);
			}
			if (status == 0) {
				bankName = callableStmt.getString(5);
				branchName = callableStmt.getString(6);
				zoneName = callableStmt.getString(7);
				shortName = callableStmt.getString(8);
				city = callableStmt.getString(9);
				district = callableStmt.getString(10);
				stateName = callableStmt.getString(11);
				phoneCode = callableStmt.getString(12);
				phoneNumber = callableStmt.getString(13);
				email = callableStmt.getString(14);
				mcgf = callableStmt.getString(15);
				dandelivery = callableStmt.getString(16);
				memberStatus = callableStmt.getString(17);
				memberAddress = callableStmt.getString(18);

				memberInfo.setMemberBankName(bankName);
				memberInfo.setMemberBranchName(branchName);
				memberInfo.setCity(city);
				memberInfo.setDistrict(district);
				memberInfo.setState(stateName);
				memberInfo.setPhoneCode(phoneCode);
				memberInfo.setTelephone(phoneNumber);
				memberInfo.setMemberId(bankId + zoneId + branchId);
				memberInfo.setEmail(email);
				memberInfo.setMemberAddress(memberAddress);

				callableStmt.close();
			}
		} catch (SQLException sqlexception) {
			Log.log(2, "CPDAO", "getMemberInfoDetails()", "Error retrieving member info details for the member Id!");
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(4, "CPDAO", "getMemberInfoDetails()", "Exited!");

		return memberInfo;
	}

	public BorrowerInfo getBorrowerDetails(String borrowerId) throws DatabaseException {
		Log.log(Log.INFO, "CPDAO", "getBorrowerDetails()", "Entered!");
		String borrowerName = null;
		String address = null;
		String cityName = null;
		String districtName = null;
		String stateName = null;
		String pinCode = null;
		String activity = null;
		CallableStatement callableStmt = null;
		int status = -1;
		String errorCode = null;

		Connection conn = null;
		BorrowerInfo borrowerInfo = new BorrowerInfo();

		try {
			conn = DBConnection.getConnection();
			callableStmt = conn.prepareCall("{?=call funcGetDetailsForBorrower(?,?,?,?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt.setString(2, borrowerId);
			callableStmt.registerOutParameter(3, java.sql.Types.VARCHAR);
			callableStmt.registerOutParameter(4, java.sql.Types.VARCHAR);
			callableStmt.registerOutParameter(5, java.sql.Types.VARCHAR);
			callableStmt.registerOutParameter(6, java.sql.Types.VARCHAR);
			callableStmt.registerOutParameter(7, java.sql.Types.VARCHAR);
			callableStmt.registerOutParameter(8, java.sql.Types.CHAR);
			callableStmt.registerOutParameter(9, java.sql.Types.VARCHAR);
			callableStmt.registerOutParameter(10, java.sql.Types.VARCHAR);
			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(10);

			if (status == Constants.FUNCTION_FAILURE) {
				Log.log(Log.ERROR, "CPDAO", "getBorrowerDetails()", "SP returns a 1. Error code is :" + errorCode);

				// Closing the callable statement
				callableStmt.close();
				throw new DatabaseException(errorCode);
			} else if (status == Constants.FUNCTION_SUCCESS) {
				// Extracting the values from the Stored Procedure
				borrowerName = callableStmt.getString(3);
				address = callableStmt.getString(4);
				cityName = callableStmt.getString(5);
				districtName = callableStmt.getString(6);
				stateName = callableStmt.getString(7);
				pinCode = callableStmt.getString(8);
				activity = callableStmt.getString(9);
				// Setting the values in the borrower info object
				borrowerInfo.setBorrowerName(borrowerName);
				borrowerInfo.setAddress(address);
				borrowerInfo.setCity(cityName);
				borrowerInfo.setDistrict(districtName);
				borrowerInfo.setState(stateName);
				borrowerInfo.setPinCode(pinCode);
				// borrowerInfo.setActivity(activity);
				// Closing the callable statement
				callableStmt.close();
				callableStmt = null;
			}
		} catch (SQLException sqlexception) {
			Log.log(Log.ERROR, "CPDAO", "getBorrowerDetails()",
					"Error retrieving borrower details for the borrower Id!");
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(Log.INFO, "CPDAO", "getBorrowerDetails()", "Exited!");
		return borrowerInfo;
	}

	public HashMap isNPADetailsAvailable(String cgbid) throws DatabaseException {
		Log.log(Log.INFO, "CPDAO", "isNPADetailsAvailable()", "Entered!");
		Log.log(Log.INFO, "CPDAO", "isNPADetailsAvailable()", "Borrower Id :" + cgbid);
		CallableStatement callableStmt = null;
		Connection conn = null;
		HashMap npadetails = new HashMap();
		ResultSet resultset = null;
		HashMap npadetail = null;

		int status = -1;
		String errorCode = null;

		try {
			conn = DBConnection.getConnection();
			callableStmt = conn.prepareCall("{? = call packGetNPADetails.funcGetNPADetailForBID(?,?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt.setString(2, cgbid);
			callableStmt.registerOutParameter(3, Constants.CURSOR);
			callableStmt.registerOutParameter(4, Constants.CURSOR);
			callableStmt.registerOutParameter(5, java.sql.Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(5);

			if (status == Constants.FUNCTION_FAILURE) {
				Log.log(Log.ERROR, "CPDAO", "isNPADetailsAvailable()", "SP returns a 1. Error code is :" + errorCode);
				callableStmt.close();
				throw new DatabaseException(errorCode);
			} else if (status == Constants.FUNCTION_SUCCESS) {
				// Retrieving the resultset from the intranet db
				resultset = (ResultSet) callableStmt.getObject(3);

				java.util.Date npaEffectiveDt = null;
				java.util.Date cgtsiReportingDt = null;
				String reasonForNPA = null;
				String willfulDefaulter = null;
				String whetherWrittenOff = null;
				java.util.Date writtenOffDate = null;
				java.util.Date recConclusionDt = null;
				String npaId = null;
				java.util.Date npaCreatedDate = null;
				String subsidyFlag = null;
				String isSubsidyRcvdFlag = null;
				String isSubsidyAdjustedFlag = null;
				java.util.Date subsidyDt = null;
				double subsidyAmt = 0.0;

				// Reading the resultset
				while (resultset.next()) {
					npadetail = new HashMap();
					npaEffectiveDt = resultset.getDate(1);
					cgtsiReportingDt = resultset.getDate(2);
					reasonForNPA = resultset.getString(3);
					willfulDefaulter = resultset.getString(4);
					whetherWrittenOff = resultset.getString(5);
					writtenOffDate = resultset.getDate(6);
					recConclusionDt = resultset.getDate(7);
					npaId = String.valueOf(resultset.getInt(8));
					npaCreatedDate = resultset.getDate(9);
					subsidyFlag = resultset.getString(10);
					isSubsidyRcvdFlag = resultset.getString(11);
					isSubsidyAdjustedFlag = resultset.getString(12);
					subsidyAmt = resultset.getDouble(13);
					subsidyDt = resultset.getDate(14);

					// Populating the npadetails HashMap
					npadetail.put(ClaimConstants.NPA_CLASSIFIED_DT, npaEffectiveDt);
					npadetail.put(ClaimConstants.NPA_REPORTING_DT, cgtsiReportingDt);
					npadetail.put(ClaimConstants.REASONS_FOR_TURNING_NPA, reasonForNPA);
					npadetail.put(ClaimConstants.WILLFUL_DEFAULTER, willfulDefaulter);
					npadetail.put(ClaimConstants.WHETHER_NPA_WRITTEN_OFF, whetherWrittenOff);
					npadetail.put(ClaimConstants.NPA_WRITTEN_OFF_DATE, writtenOffDate);
					npadetail.put(ClaimConstants.NPA_REC_CONCLUSION_DT, recConclusionDt);
					npadetail.put("npaId", npaId);
					npadetail.put("npaCreatedDate", npaCreatedDate);
					npadetail.put("SUBSIDYFLAG", subsidyFlag);
					npadetail.put("SUBSIDYRCVDFLAG", isSubsidyRcvdFlag);
					npadetail.put("SUBSIDYADJUSTEDFLAG", isSubsidyAdjustedFlag);
					npadetail.put("SUBSIDYAMT", subsidyAmt);
					npadetail.put("SUBSIDYDATE", subsidyDt);

					// Adding the hashmap to the vector
					if (npadetail != null) {
						npadetails.put(ClaimConstants.CLM_MAIN_TABLE, npadetail);
					}
				}

				resultset.close();
				resultset = null;
				// Intializing the variables
				npadetail = null;
				npaEffectiveDt = null;
				cgtsiReportingDt = null;
				reasonForNPA = null;
				willfulDefaulter = null;
				whetherWrittenOff = null;
				writtenOffDate = null;

				// Retrieving the resultset from the temp db
				resultset = (ResultSet) callableStmt.getObject(4);

				// Reading the resultset
				while (resultset.next()) {
					npadetail = new HashMap();
					npaEffectiveDt = resultset.getDate(1);
					cgtsiReportingDt = resultset.getDate(2);
					reasonForNPA = resultset.getString(3);
					willfulDefaulter = resultset.getString(4);
					whetherWrittenOff = resultset.getString(5);
					writtenOffDate = resultset.getDate(6);
					recConclusionDt = resultset.getDate(7);
					npaId = String.valueOf(resultset.getInt(8));
					npaCreatedDate = resultset.getDate(9);
					subsidyFlag = resultset.getString(10);
					isSubsidyRcvdFlag = resultset.getString(11);
					isSubsidyAdjustedFlag = resultset.getString(12);
					subsidyAmt = resultset.getDouble(13);
					subsidyDt = resultset.getDate(14);

					// Populating the npadetails HashMap
					npadetail.put(ClaimConstants.NPA_CLASSIFIED_DT, npaEffectiveDt);
					npadetail.put(ClaimConstants.NPA_REPORTING_DT, cgtsiReportingDt);
					npadetail.put(ClaimConstants.REASONS_FOR_TURNING_NPA, reasonForNPA);
					npadetail.put(ClaimConstants.WILLFUL_DEFAULTER, willfulDefaulter);
					npadetail.put(ClaimConstants.WHETHER_NPA_WRITTEN_OFF, whetherWrittenOff);
					npadetail.put(ClaimConstants.NPA_WRITTEN_OFF_DATE, writtenOffDate);
					npadetail.put(ClaimConstants.NPA_REC_CONCLUSION_DT, recConclusionDt);
					npadetail.put("npaId", npaId);
					npadetail.put("npaCreatedDate", npaCreatedDate);
					npadetail.put("SUBSIDYFLAG", subsidyFlag);
					npadetail.put("SUBSIDYRCVDFLAG", isSubsidyRcvdFlag);
					npadetail.put("SUBSIDYADJUSTEDFLAG", isSubsidyAdjustedFlag);
					npadetail.put("SUBSIDYAMT", subsidyAmt);
					npadetail.put("SUBSIDYDATE", subsidyDt);

					// Adding the hashmap to the vector
					if (npadetail != null) {
						npadetails.put(ClaimConstants.CLM_TEMP_TABLE, npadetail);
					}
				}
				resultset.close();
				resultset = null;
				callableStmt.close();
				callableStmt = null;
			}
		} catch (SQLException sqlexception) {
			Log.log(Log.ERROR, "CPDAO", "isNPADetailsAvailable()", "Error retrieving NPA details for the borrower Id!");
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(Log.INFO, "CPDAO", "isNPADetailsAvailable()", "Exited!");
		return npadetails;
	}

	public Vector getOTSDetails(String bid) throws DatabaseException {
		Log.log(4, "CPDAO", "getOTSDetails()", "Entered!");
		CallableStatement callableStmt = null;
		Connection conn = null;
		HashMap otsdetails = new HashMap();
		ResultSet resultset = null;
		Vector otsdetailsvec = new Vector();

		int status = -1;
		String errorCode = null;

		try {
			conn = DBConnection.getConnection();
			callableStmt = conn.prepareCall("{? = call packGetOTSDetail.funcGetOTSDetail(?,?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt.setString(2, bid);
			callableStmt.registerOutParameter(3, Constants.CURSOR);
			callableStmt.registerOutParameter(4, Constants.CURSOR);
			callableStmt.registerOutParameter(5, java.sql.Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(5);

			if (status == Constants.FUNCTION_FAILURE) {
				Log.log(Log.ERROR, "CPDAO", "getOTSDetails()", "SP returns a 1. Error code is :" + errorCode);
				callableStmt.close();
				throw new DatabaseException(errorCode);
			} else if (status == Constants.FUNCTION_SUCCESS) {
				// Retrieving the resultset from the intranet db
				resultset = (ResultSet) callableStmt.getObject(3);

				String reasonforots = null;
				String willfuldefaulter = null;
				double totalBorrowerProposedAmnt = 0.0D;
				double totalProposedSacrificeAmnt = 0.0D;
				double totalOSAmnt = 0.0D;
				java.util.Date otsReqDate = null;

				while (resultset.next()) {
					reasonforots = resultset.getString(2);
					willfuldefaulter = resultset.getString(3);
					otsReqDate = resultset.getDate(4);
					totalBorrowerProposedAmnt = resultset.getDouble(5);
					totalProposedSacrificeAmnt = resultset.getDouble(6);
					totalOSAmnt = resultset.getDouble(7);

					// Populating the otsdetails HashMap
					otsdetails.put(ClaimConstants.CLM_OTS_REASON_FOR_OTS, reasonforots);
					otsdetails.put(ClaimConstants.CLM_OTS_WILLFUL_DEFAULTER, willfuldefaulter);
					otsdetails.put(ClaimConstants.CLM_OTS_REQUEST_DATE, otsReqDate);
					otsdetails.put(ClaimConstants.CLM_OTS_TOTAL_BORROWER_PROPOSED_AMNT,
							new Double(totalBorrowerProposedAmnt));
					otsdetails.put(ClaimConstants.CLM_OTS_TOTAL_PROPOSED_SCRFCE_AMNT,
							new Double(totalProposedSacrificeAmnt));
					otsdetails.put(ClaimConstants.CLM_OTS_TOTAL_OS_AMNT, new Double(totalOSAmnt));

					if (otsdetails != null) {
						otsdetailsvec.addElement(otsdetails);
					}
				}

				resultset.close();
				resultset = null;

				reasonforots = null;
				willfuldefaulter = null;
				otsReqDate = null;
				totalBorrowerProposedAmnt = 0.0D;
				totalProposedSacrificeAmnt = 0.0D;
				totalOSAmnt = 0.0D;

				resultset = (ResultSet) callableStmt.getObject(4);

				while (resultset.next()) {
					reasonforots = resultset.getString(2);
					willfuldefaulter = resultset.getString(3);
					otsReqDate = resultset.getDate(4);
					totalBorrowerProposedAmnt = resultset.getDouble(5);
					totalProposedSacrificeAmnt = resultset.getDouble(6);
					totalOSAmnt = resultset.getDouble(7);

					// Populating the otsdetails HashMap
					otsdetails.put(ClaimConstants.CLM_OTS_REASON_FOR_OTS, reasonforots);
					otsdetails.put(ClaimConstants.CLM_OTS_WILLFUL_DEFAULTER, willfuldefaulter);
					otsdetails.put(ClaimConstants.CLM_OTS_REQUEST_DATE, otsReqDate);
					otsdetails.put(ClaimConstants.CLM_OTS_TOTAL_BORROWER_PROPOSED_AMNT,
							new Double(totalBorrowerProposedAmnt));
					otsdetails.put(ClaimConstants.CLM_OTS_TOTAL_PROPOSED_SCRFCE_AMNT,
							new Double(totalProposedSacrificeAmnt));
					otsdetails.put(ClaimConstants.CLM_OTS_TOTAL_OS_AMNT, new Double(totalOSAmnt));

					if (otsdetails != null) {
						if (!otsdetailsvec.contains(otsdetails)) {
							otsdetailsvec.addElement(otsdetails);
						}
					}
				}
				resultset.close();
				resultset = null;
				callableStmt.close();
			}
		} catch (SQLException sqlexception) {
			Log.log(2, "CPDAO", "getOTSDetails()", "Error retrieving OTS details for the borrower Id!");
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(4, "CPDAO", "getOTSDetails()", "Exited!");

		return otsdetailsvec;
	}

	public HashMap isRecoveryDetailsAvailable(String clmRefNum) throws DatabaseException {
		Log.log(4, "CPDAO", "isRecoveryDetailsAvailable()", "Entered!");
		CallableStatement callableStmt = null;
		Connection conn = null;
		ResultSet resultset = null;
		HashMap recoveryDetail = null;
		Vector recoveryDetails = null;
		HashMap totalRecDetails = new HashMap();

		String cgpan = null;
		double tcPrincipalAmt = 0.0D;
		double tcInterestAmt = 0.0D;
		double wcAmount = 0.0D;
		double wcOtherAmount = 0.0D;

		int status = -1;
		String errorCode = null;
		String modeOfRecovery = null;

		try {
			conn = DBConnection.getConnection();
			callableStmt = conn.prepareCall("{? = call packGetClaimRecoveryDtls.funcGetClaimRecoveryDtls(?,?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt.setString(2, clmRefNum);
			callableStmt.registerOutParameter(3, Constants.CURSOR);
			callableStmt.registerOutParameter(4, Constants.CURSOR);
			callableStmt.registerOutParameter(5, java.sql.Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(5);

			if (status == Constants.FUNCTION_FAILURE) {
				Log.log(Log.ERROR, "CPDAO", "isRecoveryDetailsAvailable()",
						"SP returns a 1. Error code is :" + errorCode);

				// closing the callable statement
				callableStmt.close();
				throw new DatabaseException(errorCode);
			} else if (status == Constants.FUNCTION_SUCCESS) {
				// Extracting the resultset from the callable statement
				resultset = (ResultSet) callableStmt.getObject(3);
				recoveryDetails = new Vector();
				while (resultset.next()) {
					recoveryDetail = new HashMap();
					cgpan = resultset.getString(1);
					tcPrincipalAmt = resultset.getDouble(2);
					tcInterestAmt = resultset.getDouble(3);
					wcAmount = resultset.getDouble(4);
					wcOtherAmount = resultset.getDouble(5);
					modeOfRecovery = resultset.getString(6);

					recoveryDetail.put("CGPAN", cgpan);
					recoveryDetail.put("TCPRINCIPAL", new Double(tcPrincipalAmt));

					recoveryDetail.put("TCINTEREST", new Double(tcInterestAmt));
					recoveryDetail.put("WC_AMOUNT", new Double(wcAmount));
					recoveryDetail.put("WC_OTHER", new Double(wcOtherAmount));
					recoveryDetail.put("REC_MODE", modeOfRecovery);

					if (!recoveryDetails.contains(recoveryDetail)) {
						recoveryDetails.addElement(recoveryDetail);
					}
				}
				totalRecDetails.put("TEMP", recoveryDetails);
				resultset.close();
				resultset = null;

				resultset = (ResultSet) callableStmt.getObject(4);
				recoveryDetails = new Vector();
				while (resultset.next()) {
					recoveryDetail = new HashMap();
					cgpan = resultset.getString(1);
					tcPrincipalAmt = resultset.getDouble(2);
					tcInterestAmt = resultset.getDouble(3);
					wcAmount = resultset.getDouble(4);
					wcOtherAmount = resultset.getDouble(5);
					modeOfRecovery = resultset.getString(6);

					recoveryDetail.put("CGPAN", cgpan);
					recoveryDetail.put("TCPRINCIPAL", new Double(tcPrincipalAmt));
					recoveryDetail.put("TCINTEREST", new Double(tcInterestAmt));
					recoveryDetail.put("WC_AMOUNT", new Double(wcAmount));
					recoveryDetail.put("WC_OTHER", new Double(wcOtherAmount));
					recoveryDetail.put("REC_MODE", modeOfRecovery);

					if (!recoveryDetails.contains(recoveryDetail)) {
						recoveryDetails.addElement(recoveryDetail);
					}
				}
				totalRecDetails.put("MAIN", recoveryDetails);

				resultset.close();
				resultset = null;

				callableStmt.close();
			}
		} catch (SQLException sqlexception) {
			Log.log(2, "CPDAO", "isRecoveryDetailsAvailable()",
					"Error retrieving Recovery details for the borrower Id!");
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(4, "CPDAO", "isRecoveryDetailsAvailable()", "Exited!");

		return totalRecDetails;
	}

	public Hashtable isDisbursementDetailsAvl(String borrowerId) throws DatabaseException {
		Log.log(4, "CPDAO", "isDisbursementDetailsAvl()", "Entered!");
		CallableStatement callableStmt = null;
		ResultSet resultset = null;
		Connection conn = null;
		Vector cgpans = new Vector();

		int status = -1;
		String errorCode = null;
		try {
			conn = DBConnection.getConnection();
			callableStmt = conn.prepareCall("{? = call packGetDtlsforDBR.funcGetDtlsForBid(?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			callableStmt.setString(2, borrowerId);
			callableStmt.registerOutParameter(3, Constants.CURSOR);
			callableStmt.registerOutParameter(4, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(4);

			if (status == 1) {
				Log.log(2, "CPDAO", "isDisbursementDetailsAvl()", "SP returns a 1. Error code is :" + errorCode);
				callableStmt.close();
				throw new DatabaseException(errorCode);
			}
			if (status == 0) {
				resultset = (ResultSet) callableStmt.getObject(3);

				while (resultset.next()) {
					String bid = null;
					String cgpan = null;
					String schemeName = null;
					String unitName = null;
					double tcSanctionedAmnt = 0.0D;

					bid = resultset.getString(1);
					cgpan = resultset.getString(2);
					schemeName = resultset.getString(3);
					unitName = resultset.getString(4);
					tcSanctionedAmnt = resultset.getDouble(5);

					if (cgpan != null) {
						if (!cgpans.contains(cgpan)) {
							cgpans.addElement(cgpan);
						}
					}
				}
				resultset.close();
				resultset = null;
				callableStmt.close();
			}
		} catch (SQLException sqlexception) {
			Log.log(2, "CPDAO", "isDisbursementDetailsAvl()", "Error retrieving Disbursement Details for Borrower!");
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}

		Hashtable disbursementStatuses = new Hashtable();
		try {
			conn = DBConnection.getConnection();

			boolean isFinal = false;
			String disbursementFlag = null;

			if (cgpans != null) {

				for (int i = 0; i < cgpans.size(); i++) {
					String cgpan = (String) cgpans.elementAt(i);

					if (cgpan != null) {
						if ("TC".equals(cgpan.substring(cgpan.length() - 2))) {
							callableStmt = conn
									.prepareCall("{? = call packGetPIDBRDtlsCGPAN.funcDBRDetailsForCGPAN(?,?,?)}");
							callableStmt.registerOutParameter(1, Types.INTEGER);
							callableStmt.setString(2, cgpan);
							callableStmt.registerOutParameter(3, Constants.CURSOR);
							callableStmt.registerOutParameter(4, Types.VARCHAR);

							callableStmt.execute();
							status = callableStmt.getInt(1);
							errorCode = callableStmt.getString(4);

							if (status == 1) {
								Log.log(2, "CPDAO", "isDisbursementDetailsAvl()",
										"SP returns a 1. Error code is :" + errorCode);
								callableStmt.close();
								throw new DatabaseException(errorCode);
							}
							if (status == 0) {
								resultset = (ResultSet) callableStmt.getObject(3);
								while (resultset.next()) {
									disbursementFlag = resultset.getString(4);

									if (disbursementFlag != null) {
										disbursementFlag = disbursementFlag.trim();
									}
									if ("Y".equals(disbursementFlag)) {
										isFinal = true;
									}
								}
								if (isFinal) {
									disbursementStatuses.put(cgpan, "Y");
								} else {
									disbursementStatuses.put(cgpan, "N");
								}
								resultset.close();
								resultset = null;

								callableStmt.close();
							}

						} else {
							disbursementStatuses.put(cgpan, "N");
						}
					} // if-end
				} // for-end
			} // if-end

		} catch (SQLException sqlexception) {
			Log.log(2, "CPDAO", "isDisbursementDetailsAvl()",
					"Error retrieving Disbursement Details for each cgpan for the Borrower!");
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(4, "CPDAO", "isDisbursementDetailsAvl()", "Disbursement Details :" + disbursementStatuses);
		Log.log(4, "CPDAO", "isDisbursementDetailsAvl()", "Exited!");

		return disbursementStatuses;
	}

	public LegalProceedingsDetail isLegalProceedingsDetailAvl(String borrowerId) throws DatabaseException {
		Log.log(4, "CPDAO", "isLegalProceedingsDetailAvl()", "Entered!");
		CallableStatement callableStmt = null;
		Connection conn = null;
		LegalProceedingsDetail legalProceedingsDetail = new LegalProceedingsDetail();

		String legalForum = null;
		String registrationSuitNumber = null;
		java.util.Date filingDate = null;
		String forumName = null;
		String location = null;
		double amount = 0.0D;
		String statusRemarks = null;
		String recvryProceedingsCompleteFlag = null;

		int status = -1;
		String errorCode = null;
		DecimalFormat decimalFormat = new DecimalFormat("##########.00##");
		try {
			conn = DBConnection.getConnection();
			callableStmt = conn
					.prepareCall("{? = call packGetInsUpdLegalDetail.funcGetLegalDtlForBID(?,?,?,?,?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt.setString(2, borrowerId);
			callableStmt.registerOutParameter(3, java.sql.Types.VARCHAR);
			callableStmt.registerOutParameter(4, java.sql.Types.VARCHAR);
			callableStmt.registerOutParameter(5, java.sql.Types.DATE);
			callableStmt.registerOutParameter(6, java.sql.Types.VARCHAR);
			callableStmt.registerOutParameter(7, java.sql.Types.VARCHAR);
			callableStmt.registerOutParameter(8, java.sql.Types.DOUBLE);
			callableStmt.registerOutParameter(9, java.sql.Types.VARCHAR);
			callableStmt.registerOutParameter(10, java.sql.Types.VARCHAR);
			callableStmt.registerOutParameter(11, java.sql.Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(11);

			if (status == 1) {
				Log.log(2, "CPDAO", "isLegalProceedingsDetailAvl()", "SP returns a 1. Error code is :" + errorCode);

				callableStmt.close();
				throw new DatabaseException(errorCode);
			}
			if (status == 0) {
				legalForum = callableStmt.getString(3);
				registrationSuitNumber = callableStmt.getString(4);
				filingDate = callableStmt.getDate(5);

				forumName = callableStmt.getString(6);
				location = callableStmt.getString(7);
				amount = callableStmt.getDouble(8);
				statusRemarks = callableStmt.getString(9);
				recvryProceedingsCompleteFlag = callableStmt.getString(10);

				errorCode = callableStmt.getString(11);

				legalProceedingsDetail.setForumRecoveryProceedingsInitiated(legalForum);
				legalProceedingsDetail.setSuitCaseRegNumber(registrationSuitNumber);
				legalProceedingsDetail.setFilingDate(filingDate);
				legalProceedingsDetail.setNameOfForum(forumName);
				legalProceedingsDetail.setLocation(location);
				legalProceedingsDetail.setAmountClaimed(Double.parseDouble(decimalFormat.format(amount)));
				legalProceedingsDetail.setCurrentStatusRemarks(statusRemarks);
				legalProceedingsDetail.setIsRecoveryProceedingsConcluded(recvryProceedingsCompleteFlag);

				callableStmt.close();
			}
		} catch (SQLException sqlexception) {
			Log.log(2, "CPDAO", "isLegalProceedingsDetailAvl()", "Error retrieving Legal Details for the Borrower!");
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(4, "CPDAO", "isLegalProceedingsDetailAvl()", "Exited!");

		return legalProceedingsDetail;
	}

	public Vector getCGPANDetailsForBorrowerId(String borrowerId, String memberId) throws DatabaseException {
		Log.log(4, "CPDAO", "getCGPANDetailsForBorrowerId()", "Entered!");
		ResultSet rs = null;
		HashMap cgpandetails = null;
		Vector allcgpandetails = new Vector();
		ApplicationDAO appDAO = new ApplicationDAO();
		Application application = null;

		CallableStatement callableStmt = null;
		Connection conn = null;

		int status = -1;
		String errorCode = null;

		try {
			conn = DBConnection.getConnection();
			callableStmt = conn.prepareCall("{? = call packGetCGPANForBorrower.funcGetCGPANForBorrower(?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt.setString(2, borrowerId);
			callableStmt.registerOutParameter(3, Constants.CURSOR);
			callableStmt.registerOutParameter(4, java.sql.Types.VARCHAR);
			// System.out.println("before call
			// packGetCGPANForBorrower.funcGetCGPANForBorrower:"+
			// new java.util.Date());
			callableStmt.execute();
			// System.out.println("after call
			// packGetCGPANForBorrower.funcGetCGPANForBorrower:"+
			// new java.util.Date());
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(4);

			if (status == Constants.FUNCTION_FAILURE) {
				Log.log(Log.ERROR, "CPDAO", "getCGPANDetailsForBorrowerId()",
						"SP returns a 1. Error code is :" + errorCode);

				callableStmt.close();
				throw new DatabaseException(errorCode);
			}
			if (status == 0) {
				rs = (ResultSet) callableStmt.getObject(3);
				while (rs.next()) {
					String cgpan = null;
					double approvedAmount = 0.0D;
					double enhancedApprovedAmount = 0.0D;
					double reapproveAmount = 0.0D;
					String appRefNum = null;
					String loantype = null;
					java.util.Date guarStartDt = null;
					String applicationStatus = null;

					cgpan = rs.getString(1);
					if (cgpan != null) {
						appRefNum = getAppRefNumber(cgpan);
						application = appDAO.getAppForAppRef(null, appRefNum);
						reapproveAmount = application.getReapprovedAmount();
						approvedAmount = rs.getDouble(2);
						enhancedApprovedAmount = rs.getDouble(3);
						loantype = rs.getString(4);
						guarStartDt = rs.getDate(5);
						applicationStatus = rs.getString(6);

						if (cgpan != null) {
							cgpandetails = new HashMap();
							cgpandetails.put("CGPAN", cgpan);
							if (reapproveAmount == 0.0D) {
								cgpandetails.put("ApprovedAmount", new Double(approvedAmount));
							}
							if (reapproveAmount > 0.0D) {
								cgpandetails.put("ApprovedAmount", new Double(reapproveAmount));
							}
							cgpandetails.put("EnhancedApprovedAmount", new Double(enhancedApprovedAmount));
							cgpandetails.put("LoanType", loantype);
							cgpandetails.put("GUARANTEESTARTDT", guarStartDt);
							cgpandetails.put("APPLICATION_STATUS", applicationStatus);
							allcgpandetails.add(cgpandetails);
						}
					}
				}

				rs.close();
				rs = null;

				callableStmt.close();
			}
		} catch (SQLException sqlexception) {
			Log.log(2, "CPDAO", "getCGPANDetailsForBorrowerId()", "Error retrieving CGPAN Details for the Borrower!");
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}

		Vector clmsFiled = getAllClaimsFiled();
		String clmRefNumber = null;
		for (int k = 0; k < clmsFiled.size(); k++) {
			HashMap mp = (HashMap) clmsFiled.elementAt(k);
			if (mp != null) {
				String mpMemberId = (String) mp.get("MEMBERID");
				String mpbid = (String) mp.get("BORROWERID");
				if (mpMemberId != null) {
					if (mpbid != null) {
						if ((mpMemberId.equals(memberId)) && (mpbid.equals(borrowerId))) {
							clmRefNumber = (String) mp.get("ClaimRefNumber");
						}
					}
				}
			}
		}
		/* DISBURSEMENT CHECKING */
		int type = 0;
		ArrayList disbursementDetails = null;
		ArrayList repaymentDetails = null;
		ArrayList disbDtls = null;
		ArrayList nestedDtls = null;
		PeriodicInfo pi = null;
		if (clmRefNumber == null) {
			disbursementDetails = getDisbursementDetails(borrowerId, type);
			if (disbursementDetails != null) {
				if (disbursementDetails.size() > 0) {
					Log.log(4, "CPDAO", "getCGPANDetailsForBorrowerId()",
							"Size of disbursementDetails Dtls :" + disbursementDetails.size());
					for (int i = 0; i < disbursementDetails.size(); i++) {
						pi = (PeriodicInfo) disbursementDetails.get(i);
						if (pi != null) {
							disbDtls = pi.getDisbursementDetails();
							if (disbDtls != null) {
								Log.log(4, "CPDAO", "getCGPANDetailsForBorrowerId()",
										"Size of disbDtls Dtls :" + disbDtls.size());
								if (disbDtls.size() > 0) {
									for (int j = 0; j < disbDtls.size(); j++) {
										Disbursement dsbrsmnt = (Disbursement) disbDtls.get(j);
										if (dsbrsmnt != null) {
											nestedDtls = dsbrsmnt.getDisbursementAmounts();
											if ((nestedDtls == null) || (nestedDtls.size() != 0)) {
												String lastcgpan = null;
												java.util.Date lastDsbrsmntDt = null;
												java.util.Date presentDsbrsmntDt = null;
												Log.log(4, "CPDAO", "getCGPANDetailsForBorrowerId()",
														"Size of Nested Dtls :" + nestedDtls.size());
												for (int k = 0; k < nestedDtls.size(); k++) {
													DisbursementAmount dbamnt = (DisbursementAmount) nestedDtls.get(k);
													if (dbamnt != null) {
														String cgpan = dbamnt.getCgpan();
														Log.log(4, "CPDAO", "getCGPANDetailsForBorrowerId()",
																"cgpan :" + cgpan);

														presentDsbrsmntDt = dbamnt.getDisbursementDate();
														Log.log(4, "CPDAO", "getCGPANDetailsForBorrowerId()",
																"presentDsbrsmntDt :" + presentDsbrsmntDt);
														if (k == 0) {
															lastcgpan = cgpan;
															lastDsbrsmntDt = presentDsbrsmntDt;
														}
														if (k > 0) {
															if (cgpan.equals(lastcgpan)) {
																if (presentDsbrsmntDt != null) {
																	if (presentDsbrsmntDt
																			.compareTo(lastDsbrsmntDt) > 0) {
																		lastDsbrsmntDt = presentDsbrsmntDt;
																	}
																	if (presentDsbrsmntDt
																			.compareTo(lastDsbrsmntDt) == 0) {
																		lastDsbrsmntDt = presentDsbrsmntDt;
																	}

																}

															} else {
																continue;
															}

														}

														HashMap hashmap = null;
														Log.log(4, "CPDAO", "getCGPANDetailsForBorrowerId()",
																"allcgpandetails size :" + allcgpandetails.size());
														for (int m = 0; m < allcgpandetails.size(); m++) {
															hashmap = (HashMap) allcgpandetails.elementAt(m);
															Log.log(4, "CPDAO", "getCGPANDetailsForBorrowerId()",
																	"priting hasmap in allcgpandetails vector :"
																			+ hashmap);
															if (hashmap != null) {
																if (hashmap.containsKey("CGPAN")) {
																	String cgpn = (String) hashmap.get("CGPAN");

																	if ((cgpn != null) && (!cgpn.equals(""))) {
																		if (cgpn.equals(lastcgpan)) {
																			hashmap = (HashMap) allcgpandetails
																					.remove(m);
																			hashmap.put("LASTDSBRSMNTDT",
																					lastDsbrsmntDt);
																			allcgpandetails.add(m, hashmap);
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
			disbDtls = null;
			nestedDtls = null;
		}
		/* CLAIM DETAIL CHECKING */
		if (clmRefNumber != null) {
			Vector tcDetails = null;
			ClaimDetail cd = getDetailsForClaimRefNumber(clmRefNumber);
			if (cd != null) {
				tcDetails = cd.getTcDetails();

				if (tcDetails != null) {
					for (int i = 0; i < tcDetails.size(); i++) {
						HashMap map = (HashMap) tcDetails.elementAt(i);
						if (map != null) {
							String pan = (String) map.get("CGPAN");
							java.util.Date dsbrsDate = (java.util.Date) map.get("LASTDSBRSMNTDT");
							HashMap hashmap = null;
							for (int m = 0; m < allcgpandetails.size(); m++) {
								hashmap = (HashMap) allcgpandetails.elementAt(m);
								Log.log(4, "CPDAO", "getCGPANDetailsForBorrowerId()",
										"priting hasmap in allcgpandetails vector :" + hashmap);
								if (hashmap != null) {
									if (hashmap.containsKey("CGPAN")) {
										String cgpn = (String) hashmap.get("CGPAN");

										if ((cgpn != null) && (!cgpn.equals(""))) {
											if (cgpn.equals(pan)) {
												hashmap = (HashMap) allcgpandetails.remove(m);
												hashmap.put("LASTDSBRSMNTDT", dsbrsDate);
												allcgpandetails.add(m, hashmap);
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}

		/* REPAYMENT CHECKING */
		type = 1;
		HashMap hashmap = null;
		ArrayList repayments = null;
		ArrayList repayAmounts = null;
		Repayment repayment = null;
		RepaymentAmount repayamntAmnt = null;
		for (int m = 0; m < allcgpandetails.size(); m++) {
			hashmap = (HashMap) allcgpandetails.elementAt(m);
			if (hashmap != null) {
				if (hashmap.containsKey("CGPAN")) {
					String cgpn = (String) hashmap.get("CGPAN");
					if ((cgpn != null) && (!cgpn.equals(""))) {
						repaymentDetails = getCPRepaymentDetails(cgpn, type);
						for (int i = 0; i < repaymentDetails.size(); i++) {
							pi = (PeriodicInfo) repaymentDetails.get(i);
							if (pi != null) {
								repayments = pi.getRepaymentDetails();
								if ((repayments == null) || (repayments.size() != 0)) {
									double totalRepaidAmnt = 0.0D;
									for (int j = 0; j < repayments.size(); j++) {
										repayment = (Repayment) repayments.get(j);
										if (repayment != null) {
											repayAmounts = repayment.getRepaymentAmounts();
											if ((repayAmounts == null) || (repayAmounts.size() != 0)) {
												String lastPan = null;

												for (int k = 0; k < repayAmounts.size(); k++) {
													repayamntAmnt = (RepaymentAmount) repayAmounts.get(k);
													String pan = repayamntAmnt.getCgpan();

													if (pan != null) {
														double repaidAmnt = repayamntAmnt.getRepaymentAmount();

														if (k == 0) {
															lastPan = pan;
															totalRepaidAmnt = repaidAmnt + totalRepaidAmnt;
														} else if (pan.equals(lastPan)) {
															lastPan = pan;
															totalRepaidAmnt = repaidAmnt + totalRepaidAmnt;
															continue;
														}

														hashmap = (HashMap) allcgpandetails.remove(m);
														hashmap.put("AMNT_REPAID", new Double(totalRepaidAmnt));
														allcgpandetails.add(m, hashmap);
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		Log.log(4, "CPDAO", "getCGPANDetailsForBorrowerId()", "Exited!");
		for (int i = 0; i < allcgpandetails.size(); i++) {
			HashMap m = (HashMap) allcgpandetails.elementAt(i);
			if (m != null) {
				Log.log(4, "CPDAO", "getCGPANDetailsForBorrowerId()", "------> Printing the hashmap from CPDAO:" + m);
			}
		}
		return allcgpandetails;
	}

	public Vector getAllRecoveryModes() throws DatabaseException {
		Log.log(4, "CPDAO", "getAllRecoveryModes()", "Entered!");
		CallableStatement callableStmt = null;
		Connection conn = null;
		ResultSet resultset = null;
		Vector recoveryModes = new Vector();
		String recoverymode = null;

		int status = -1;
		String errorCode = null;

		try {
			conn = DBConnection.getConnection();
			callableStmt = conn.prepareCall("{? = call packGetAllRecoveryModes.funcGetAllRecoveryModes(?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt.registerOutParameter(2, Constants.CURSOR);
			callableStmt.registerOutParameter(3, java.sql.Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(3);

			if (status == 1) {
				Log.log(2, "CPDAO", "getAllRecoveryModes()", "SP returns a 1. Error code is :" + errorCode);

				callableStmt.close();
				throw new DatabaseException(errorCode);
			}
			if (status == 0) {
				resultset = (ResultSet) callableStmt.getObject(2);

				while (resultset.next()) {
					recoverymode = resultset.getString(1);
					if (!recoveryModes.contains(recoverymode)) {
						recoveryModes.addElement(recoverymode);
					}
				}

				resultset.close();
				resultset = null;

				callableStmt.close();
			}
		} catch (SQLException sqlexception) {
			Log.log(2, "CPDAO", "getAllRecoveryModes()", "Error retrieving Recovery Modes from the database");
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(4, "CPDAO", "getAllRecoveryModes()", "Exited!");

		return recoveryModes;
	}

	public void saveClaimApplication(ClaimApplication claimApplication, HashMap map, boolean flag)
			throws DatabaseException {

		Log.log(4, "CPDAO", "saveClaimApplication()", "Entered!");
		String claimRefNumber = claimApplication.getClaimRefNumber();

		String borrowerId = claimApplication.getBorrowerId();

		String memberId = claimApplication.getMemberId();

		GMProcessor gmprocessor = new GMProcessor();

		LegalProceedingsDetail legalProceedingsDetail = claimApplication.getLegalProceedingsDetails();

		NPADetails npaDtls = gmprocessor.getNPADetails(borrowerId);
		if (claimApplication.getFirstInstallment()) {
			// updateNPADetails(npaDtls);
			updateNPADetails(npaDtls);
			updateLegalProceedingDetails(legalProceedingsDetail);
			if (map != null) {
				String borowrId = (String) map.get("BORROWERID");
				String itpanOfChiefPromoter = (String) map.get("Clm_ITPAN_of_Chief_Promoter");
				saveITPANDetail(borowrId, itpanOfChiefPromoter);
			}
		}

		memberId = memberId.trim();

		// System.out.println("memberId"+memberId);
		String bankId = memberId.substring(0, Types.INTEGER);
		// System.out.println("bankId"+bankId);

		String zoneId = memberId.substring(4, 8);

		// System.out.println("zoneId"+zoneId);
		String branchId = memberId.substring(8, Types.VARCHAR);

		// System.out.println("branchId"+branchId);
		MemberInfo memberInfo = getMemberInfoDetails(bankId, zoneId, branchId);
		String bankName = memberInfo.getMemberBankName();
		// System.out.println("bankName"+bankName);
		String participatingBank = claimApplication.getParticipatingBank();
		// System.out.println("participatingBank"+participatingBank);

		String whethereWillFulDfaulter = claimApplication.getWhetherBorrowerIsWilfulDefaulter();

		// System.out.println("whethereWillFulDfaulter"+whethereWillFulDfaulter);
		java.util.Date dtOfConclusionOfRecProc = claimApplication.getDtOfConclusionOfRecoveryProc();

		// System.out.println("dtOfConclusionOfRecProc"+dtOfConclusionOfRecProc);
		String whetherAccntWrittenOffBooks = claimApplication.getWhetherAccntWrittenOffFromBooksOfMLI();

		// System.out.println("whetherAccntWrittenOffBooks"+whetherAccntWrittenOffBooks);
		java.util.Date dtOfWrittenOffBooks = claimApplication.getDtOnWhichAccntWrittenOff();

		// System.out.println("dtOfWrittenOffBooks"+dtOfWrittenOffBooks);

		java.util.Date filingdate = legalProceedingsDetail.getFilingDate();

		// System.out.println("filingdate"+filingdate);
		Log.log(4, "CPDAO", "saveClaimApplication()", "Filing Date is :" + filingdate);
		double amountClaimed = legalProceedingsDetail.getAmountClaimed();

		// System.out.println("amountClaimed"+amountClaimed);
		Log.log(4, "CPDAO", "saveClaimApplication()", "Amount Claimed is :" + amountClaimed);
		String currentStatusRemarks = legalProceedingsDetail.getCurrentStatusRemarks();

		// System.out.println("currentStatusRemarks"+currentStatusRemarks);
		Log.log(4, "CPDAO", "saveClaimApplication()", "Current Status Remarks is :" + currentStatusRemarks);
		String isRecoveryProceedingsComplete = legalProceedingsDetail.getIsRecoveryProceedingsConcluded();

		// System.out.println("isRecoveryProceedingsComplete"+isRecoveryProceedingsComplete);
		Log.log(4, "CPDAO", "saveClaimApplication()",
				"Recovery Proceedings Complete is :" + isRecoveryProceedingsComplete);

		java.util.Date claimSubmittedDate = claimApplication.getClaimSubmittedDate();
		// System.out.println("claimSubmittedDate"+claimSubmittedDate);

		java.util.Date dateOfReleaseOfWC = claimApplication.getDateOfReleaseOfWC();
		// System.out.println("claimRefNumber"+claimRefNumber);

		java.util.Date dateOfIssueOfRecallNotice = claimApplication.getDateOfIssueOfRecallNotice();
		// System.out.println("dateOfIssueOfRecallNotice"+dateOfIssueOfRecallNotice);

		String nameOfOfficial = claimApplication.getNameOfOfficial();

		// System.out.println("nameOfOfficial"+nameOfOfficial);
		String designationOfOfficial = claimApplication.getDesignationOfOfficial();

		// System.out.println("designationOfOfficial"+designationOfOfficial);
		String place = claimApplication.getPlace();

		// System.out.println("place"+place);
		String createdModifiedBy = claimApplication.getCreatedModifiedy();

		// System.out.println("createdModifiedBy"+createdModifiedBy);
		String view = claimApplication.getView();//
		// added rajuk
		String isActivityEligibleVal = claimApplication.getIsActivityEligibleVal();
		String isActivityEligibleValflag = claimApplication.getIsActivityEligibleValflag();
		String whetherCibilval = claimApplication.getWhetherCibilval();
		String whetherCibilvalflag = claimApplication.getWhetherCibilvalflag();
		String rateChargeVal = claimApplication.getRateChargeVal();
		String rateChargeValflag = claimApplication.getRateChargeValflag();
		String thirdpartyGuaranteeVal = claimApplication.getThirdpartyGuaranteeVal();
		String thirdpartyGuaranteeValflag = claimApplication.getThirdpartyGuaranteeValflag();
		String dateofNPAval = claimApplication.getDateofNPAval();
		String dateofNPAvalflag = claimApplication.getDateofNPAvalflag();
		String outstandingAmountVal = claimApplication.getOutstandingAmountVal();
		String outstandingAmountValflag = claimApplication.getOutstandingAmountValflag();
		String seriousDeficieniesVal = claimApplication.getSeriousDeficieniesVal();
		String seriousDeficieniesValflag = claimApplication.getSeriousDeficieniesValflag();
		String majorDeficienciesObservedVal = claimApplication.getMajorDeficienciesObservedVal();
		String majorDeficienciesObservedValflag = claimApplication.getMajorDeficienciesObservedValflag();
		String deficienciesObservedVal = claimApplication.getDeficienciesObservedVal();
		String deficienciesObservedValflag = claimApplication.getDeficienciesObservedValflag();
		String internalRatingVal = claimApplication.getInternalRatingVal();
		String internalRatingValflag = claimApplication.getInternalRatingValflag();
		String alltheRecoveriesVal = claimApplication.getAlltheRecoveriesVal();
		String alltheRecoveriesValflag = claimApplication.getAlltheRecoveriesValflag();
		// ended by rajuk
		String installmentFlag = null;
		if (claimApplication.getFirstInstallment()) {
			installmentFlag = "F";
		} else if (claimApplication.getSecondInstallment()) {
			installmentFlag = "S";
		}
		// System.out.println("installmentFlag" + installmentFlag);
		String cgpan = null;
		java.util.Date disbursementDate = null;
		double principalAmount = 0.0D;
		double interestAmnt = 0.0D;
		double npaAmount = 0.0D;
		double legalAmount = 0.0D;
		double claimAmount = 0.0D;

		CallableStatement callableStmt = null;
		Connection conn = null;
		boolean hasExceptionOccured = false;
		int status = -1;
		String errorCode = null;
		try {
			conn = DBConnection.getConnection(false);
			if (claimApplication.getIsActivityEligibleValflag() != null
					&& !claimApplication.getIsActivityEligibleValflag().equals("")
					&& claimApplication.getThirdpartyGuaranteeValflag() != null
					&& claimApplication.getMajorDeficienciesObservedValflag() != null)

			{

				// added by rajuk

				// callableStmt =
				// conn.prepareCall("{?=call
				// funcInsertClaimCheckList(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				callableStmt = conn.prepareCall(
						"{?=call funcInsertClaimCheckList(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
				callableStmt.setString(2, claimRefNumber);
				callableStmt.setString(3, borrowerId);
				callableStmt.setString(4, bankId);
				callableStmt.setString(5, zoneId);
				callableStmt.setString(6, branchId);

				// if(claimApplication.getIsActivityEligibleVal()=="NA")

				System.out.println(claimApplication.getIsActivityEligibleVal());

				System.out.println(claimApplication.getIsActivityEligibleValflag());

				System.out.println(claimApplication.getWhetherCibilval());

				System.out.println(claimApplication.getRateChargeValflag());

				callableStmt.setString(7, claimApplication.getIsActivityEligibleVal());

				if (claimApplication.getIsActivityEligibleValflag().equals("NA")) {
					// callableStmt.setNull(8,null);

					callableStmt.setNull(8, java.sql.Types.VARCHAR);

				} else {
					callableStmt.setString(8, claimApplication.getIsActivityEligibleValflag());
				}

				// callableStmt.setString(8,
				// claimApplication.getIsActivityEligibleValflag());

				callableStmt.setString(9, claimApplication.getWhetherCibilval());

				if (claimApplication.getWhetherCibilvalflag().equals("NA")) {
					// callableStmt.setNull(8,null);

					callableStmt.setNull(10, java.sql.Types.VARCHAR);

				} else {
					callableStmt.setString(10, claimApplication.getWhetherCibilvalflag());
				}

				// callableStmt.setString(10,
				// claimApplication.getWhetherCibilvalflag());

				callableStmt.setString(11, claimApplication.getRateChargeVal());

				if (claimApplication.getRateChargeValflag().equals("NA")) {
					// callableStmt.setNull(8,null);

					callableStmt.setNull(12, java.sql.Types.VARCHAR);

				} else {
					callableStmt.setString(12, claimApplication.getRateChargeValflag());
				}

				// callableStmt.setString(12,
				// claimApplication.getRateChargeValflag());

				callableStmt.setString(13, claimApplication.getThirdpartyGuaranteeVal());

				if (claimApplication.getThirdpartyGuaranteeValflag().equals("NA")) {
					// callableStmt.setNull(8,null);

					callableStmt.setNull(14, java.sql.Types.VARCHAR);

				} else {
					callableStmt.setString(14, claimApplication.getThirdpartyGuaranteeValflag());
				}

				// callableStmt.setString(14,
				// claimApplication.getThirdpartyGuaranteeValflag());

				callableStmt.setString(15, claimApplication.getDateofNPAval());

				if (claimApplication.getDateofNPAvalflag().equals("NA")) {
					// callableStmt.setNull(8,null);

					callableStmt.setNull(16, java.sql.Types.VARCHAR);

				} else {
					callableStmt.setString(16, claimApplication.getDateofNPAvalflag());
				}
				// callableStmt.setString(16,
				// claimApplication.getDateofNPAvalflag());

				callableStmt.setString(17, claimApplication.getOutstandingAmountVal());

				if (claimApplication.getOutstandingAmountValflag().equals("NA")) {
					// callableStmt.setNull(8,null);

					callableStmt.setNull(18, java.sql.Types.VARCHAR);

				} else {
					callableStmt.setString(18, claimApplication.getOutstandingAmountValflag());
				}

				// callableStmt.setString(18,
				// claimApplication.getOutstandingAmountValflag());

				callableStmt.setString(19, claimApplication.getSeriousDeficieniesVal());

				if (claimApplication.getSeriousDeficieniesValflag().equals("NA")) {
					// callableStmt.setNull(8,null);

					callableStmt.setNull(20, java.sql.Types.VARCHAR);

				} else {
					callableStmt.setString(20, claimApplication.getSeriousDeficieniesValflag());
				}

				// callableStmt.setString(20,
				// claimApplication.getSeriousDeficieniesValflag());

				callableStmt.setString(21, claimApplication.getMajorDeficienciesObservedVal());

				if (claimApplication.getMajorDeficienciesObservedValflag().equals("NA")) {
					// callableStmt.setNull(8,null);

					callableStmt.setNull(22, java.sql.Types.VARCHAR);

				} else {
					callableStmt.setString(22, claimApplication.getMajorDeficienciesObservedValflag());
				}
				// callableStmt.setString(22,
				// claimApplication.getMajorDeficienciesObservedValflag());

				callableStmt.setString(23, claimApplication.getDeficienciesObservedVal());

				if (claimApplication.getDeficienciesObservedValflag().equals("NA")) {
					// callableStmt.setNull(8,null);

					callableStmt.setNull(24, java.sql.Types.VARCHAR);

				} else {
					callableStmt.setString(24, claimApplication.getDeficienciesObservedValflag());
				}
				// callableStmt.setString(24,
				// claimApplication.getDeficienciesObservedValflag());

				callableStmt.setString(25, claimApplication.getInternalRatingVal());

				if (claimApplication.getInternalRatingValflag().equals("NA")) {
					// callableStmt.setNull(8,null);

					callableStmt.setNull(26, java.sql.Types.VARCHAR);

				} else {
					callableStmt.setString(26, claimApplication.getInternalRatingValflag());
				}
				// callableStmt.setString(26,
				// claimApplication.getInternalRatingValflag());

				callableStmt.setString(27, claimApplication.getAlltheRecoveriesVal());

				if (claimApplication.getAlltheRecoveriesValflag().equals("NA")) {
					// callableStmt.setNull(8,null);

					callableStmt.setNull(28, java.sql.Types.VARCHAR);

				} else {
					callableStmt.setString(28, claimApplication.getAlltheRecoveriesValflag());
				}
				// callableStmt.setString(28,
				// claimApplication.getAlltheRecoveriesValflag());

				callableStmt.registerOutParameter(29, java.sql.Types.VARCHAR);

				callableStmt.execute();
				status = callableStmt.getInt(1);
				// errorCode = callableStmt.getString(44);
				errorCode = callableStmt.getString(29);

				if (status == Constants.FUNCTION_FAILURE) {

					Log.log(Log.ERROR, "CPDAO", "saveClaimApplication()",
							"SP returns a 1. Error code is :" + errorCode);
					callableStmt.close();
					try {
						conn.rollback();
					} catch (SQLException sqlex) {
						throw new DatabaseException(sqlex.getMessage());
					}
					throw new DatabaseException(errorCode);
				}
				conn.commit();
			}

			// ended by rajuk

			// callableStmt =
			// conn.prepareCall("{?=call
			// funcInsertClaimDetails(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			// callableStmt =
			// conn.prepareCall("{?=call
			// funcInsertClaimDetailsNew(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			callableStmt = conn.prepareCall(
					"{?=call funcInsertClaimDetailsNew(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt.setString(2, claimRefNumber);
			callableStmt.setString(3, borrowerId);
			callableStmt.setString(4, bankId);
			callableStmt.setString(5, zoneId);
			callableStmt.setString(6, branchId);
			callableStmt.setString(7, participatingBank);
			if (dateOfIssueOfRecallNotice != null) {
				callableStmt.setDate(8, new java.sql.Date(dateOfIssueOfRecallNotice.getTime()));
			} else {
				callableStmt.setNull(8, java.sql.Types.DATE);
			}
			if (filingdate != null) {
				callableStmt.setDate(9, new java.sql.Date(filingdate.getTime()));
			} else {
				callableStmt.setNull(9, java.sql.Types.DATE);
			}
			if (dateOfReleaseOfWC != null) {
				callableStmt.setDate(10, new java.sql.Date(dateOfReleaseOfWC.getTime()));
			} else {
				callableStmt.setNull(10, java.sql.Types.DATE);
			}
			callableStmt.setString(11, nameOfOfficial);
			callableStmt.setString(12, designationOfOfficial);
			callableStmt.setString(13, bankName);
			callableStmt.setString(14, place);
			if (claimSubmittedDate != null) {
				callableStmt.setDate(15, new java.sql.Date(claimSubmittedDate.getTime()));
			} else {
				callableStmt.setNull(15, java.sql.Types.DATE);
			}

			callableStmt.setString(16, installmentFlag);
			callableStmt.setString(17, createdModifiedBy);

			java.util.Date subsidyDate = (java.util.Date) claimApplication.getSubsidyDate();
			if (subsidyDate != null) {
				callableStmt.setDate(18, new java.sql.Date(subsidyDate.getTime()));
			} else {
				callableStmt.setNull(18, java.sql.Types.DATE);
			}
			callableStmt.setDouble(19, claimApplication.getSubsidyAmt());
			callableStmt.setString(20, claimApplication.getIfsCode());
			callableStmt.setString(21, claimApplication.getNeftCode());
			callableStmt.setString(22, claimApplication.getRtgsBankName());
			callableStmt.setString(23, claimApplication.getRtgsBranchName());
			callableStmt.setString(24, claimApplication.getRtgsBankNumber());
			callableStmt.setString(25, claimApplication.getMicroCategory());

			callableStmt.setString(26, claimApplication.getWilful());
			callableStmt.setString(27, claimApplication.getFraudFlag());
			callableStmt.setString(28, claimApplication.getEnquiryFlag());
			callableStmt.setString(29, claimApplication.getMliInvolvementFlag());
			callableStmt.setString(30, claimApplication.getReasonForRecall());
			callableStmt.setString(31, claimApplication.getReasonForFilingSuit());

			java.util.Date possessionDt = claimApplication.getAssetPossessionDt();
			if (possessionDt != null) {
				callableStmt.setDate(32, new java.sql.Date(possessionDt.getTime()));
			} else {
				callableStmt.setNull(32, java.sql.Types.DATE);
			}
			callableStmt.setString(33, claimApplication.getInclusionOfReceipt());
			callableStmt.setString(34, claimApplication.getConfirmRecoveryFlag());
			callableStmt.setString(35, claimApplication.getSubsidyFlag());
			callableStmt.setString(36, claimApplication.getIsSubsidyRcvdAfterNpa());
			callableStmt.setString(37, claimApplication.getIsSubsidyAdjustedOnDues());
			callableStmt.setString(38, claimApplication.getMliCommentOnFinPosition());
			callableStmt.setString(39, claimApplication.getDetailsOfFinAssistance());
			callableStmt.setString(40, claimApplication.getCreditSupport());
			callableStmt.setString(41, claimApplication.getBankFacilityDetail());
			callableStmt.setString(42, claimApplication.getPlaceUnderWatchList());
			callableStmt.setString(43, claimApplication.getRemarksOnNpa());

			callableStmt.setString(44, claimApplication.getDealingOfficerName());

			// callableStmt.registerOutParameter(44,java.sql.Types.VARCHAR);
			callableStmt.registerOutParameter(45, java.sql.Types.VARCHAR);
			/* Added for GST changes */
			callableStmt.setString(46, claimApplication.getGstNo());
			callableStmt.setString(47, claimApplication.getGstStateCode());

			callableStmt.execute();
			status = callableStmt.getInt(1);
			// errorCode = callableStmt.getString(44);
			errorCode = callableStmt.getString(45);

			// System.out.println("errorCode"+errorCode);
			if (status == Constants.FUNCTION_FAILURE) {

				Log.log(Log.ERROR, "CPDAO", "saveClaimApplication()", "SP returns a 1. Error code is :" + errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			// closing the callable statement
			callableStmt.close();
			callableStmt = null;

			Log.log(4, "CPDAO", "saveClaimApplication()", "Done with saving Generic Info of Claim Application");

			Log.log(4, "CPDAO", "saveClaimApplication()", "Saving Term Loan Dtls of Claim Application......");

			Vector tcDetails = claimApplication.getTermCapitalDtls();
			double osAsOnSecondClmLodgemnt = 0.0D;
			String tcClaimFlag = "";
			double totalDisbAmnt = 0.0D;
			if (tcDetails != null) {
				for (int i = 0; i < tcDetails.size(); i++) {
					TermLoanCapitalLoanDetail tcLoanDetail = (TermLoanCapitalLoanDetail) tcDetails.elementAt(i);
					if (tcLoanDetail != null) {
						cgpan = tcLoanDetail.getCgpan();
						disbursementDate = tcLoanDetail.getLastDisbursementDate();
						principalAmount = tcLoanDetail.getPrincipalRepayment();
						interestAmnt = tcLoanDetail.getInterestAndOtherCharges();
						npaAmount = tcLoanDetail.getOutstandingAsOnDateOfNPA();
						legalAmount = tcLoanDetail.getOutstandingStatedInCivilSuit();
						claimAmount = tcLoanDetail.getOutstandingAsOnDateOfLodgement();
						osAsOnSecondClmLodgemnt = tcLoanDetail.getOsAsOnDateOfLodgementOfClmForSecInstllmnt();
						tcClaimFlag = tcLoanDetail.getTcClaimFlag();
						totalDisbAmnt = tcLoanDetail.getTotaDisbAmnt();
						// callableStmt =
						// conn.prepareCall("{?=call funcInsertClaimTLDetails(?,?,?,?,?,?,?,?,?,?)}");
						callableStmt = conn.prepareCall("{?=call funcInsertClaimTLDetails(?,?,?,?,?,?,?,?,?,?,?,?)}");
						callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
						callableStmt.setString(2, claimRefNumber);
						callableStmt.setString(3, cgpan);
						if (disbursementDate != null) {
							callableStmt.setDate(4, new java.sql.Date(disbursementDate.getTime()));
						} else {
							callableStmt.setNull(4, java.sql.Types.DATE);
						}
						callableStmt.setDouble(5, principalAmount);
						callableStmt.setDouble(6, interestAmnt);
						callableStmt.setDouble(7, npaAmount);
						callableStmt.setDouble(8, legalAmount);
						callableStmt.setDouble(9, claimAmount);
						callableStmt.setDouble(10, osAsOnSecondClmLodgemnt);
						callableStmt.registerOutParameter(11, java.sql.Types.VARCHAR);
						callableStmt.setString(11, tcClaimFlag);
						callableStmt.setDouble(12, totalDisbAmnt);
						callableStmt.registerOutParameter(13, java.sql.Types.VARCHAR);

						callableStmt.execute();
						status = callableStmt.getInt(1);
						// errorCode = callableStmt.getString(11);
						errorCode = callableStmt.getString(13);
						if (status == 1) {
							Log.log(2, "CPDAO", "saveClaimApplication()",
									"SP returns a 1. Error code is :" + errorCode);
							callableStmt.close();
							try {
								conn.rollback();
							} catch (SQLException sqlex) {
								throw new DatabaseException(sqlex.getMessage());
							}
							throw new DatabaseException(errorCode);
						}
						callableStmt.close();
					}
				}
			}
			Log.log(4, "CPDAO", "saveClaimApplication()", "Done with saving Term Loan Details of Claim Application");

			Log.log(4, "CPDAO", "saveClaimApplication()", "Saving Working Capital Dtls of Claim Application......");
			ArrayList wcDetails = claimApplication.getWorkingCapitalDtls();
			double osWCAsOnSecWCClmt = 0.0D;
			String wcClaimFlag = "";
			if (wcDetails != null) {
				for (int i = 0; i < wcDetails.size(); i++) {
					WorkingCapitalDetail wcDetail = (WorkingCapitalDetail) wcDetails.get(i);
					cgpan = wcDetail.getCgpan();
					npaAmount = wcDetail.getOutstandingAsOnDateOfNPA();
					legalAmount = wcDetail.getOutstandingStatedInCivilSuit();
					claimAmount = wcDetail.getOutstandingAsOnDateOfLodgement();
					osWCAsOnSecWCClmt = wcDetail.getOsAsOnDateOfLodgementOfClmForSecInstllmnt();
					wcClaimFlag = wcDetail.getWcClaimFlag();
					callableStmt = conn.prepareCall("{? = call funcInsertClaimWCDetails(?,?,?,?,?,?,?)}");
					callableStmt = conn.prepareCall("{? = call funcInsertClaimWCDetails(?,?,?,?,?,?,?,?)}");
					callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
					callableStmt.setString(2, claimRefNumber);
					callableStmt.setString(3, cgpan);
					callableStmt.setDouble(4, npaAmount);
					callableStmt.setDouble(5, legalAmount);
					callableStmt.setDouble(6, claimAmount);
					callableStmt.setDouble(7, osWCAsOnSecWCClmt);
					callableStmt.setString(8, wcClaimFlag);
					// callableStmt.registerOutParameter(8,java.sql.Types.VARCHAR);
					callableStmt.registerOutParameter(9, java.sql.Types.VARCHAR);
					callableStmt.execute();
					status = callableStmt.getInt(1);
					// errorCode = callableStmt.getString(8);
					errorCode = callableStmt.getString(9);
					if (status == 1) {
						Log.log(2, "CPDAO", "saveClaimApplication()", "SP returns a 1. Error code is :" + errorCode);
						callableStmt.close();
						try {
							conn.rollback();
						} catch (SQLException sqlex) {
							throw new DatabaseException(sqlex.getMessage());
						}
						throw new DatabaseException(errorCode);
					}

					callableStmt.close();
				}
			}
			Log.log(4, "CPDAO", "saveClaimApplication()",
					"Done with saving Working Capital Details of Claim Application");

			Log.log(4, "CPDAO", "saveClaimApplication()", "Saving Recovery Details of Claim Application");

			Vector vecRecoveryDetails = claimApplication.getRecoveryDetails();
			String recoveryMode = null;
			double tcPrincipal = 0.0D;
			double tcInterestAndOtherCharges = 0.0D;
			double wcAmount = 0.0D;
			double wcOtherCharges = 0.0D;

			if (vecRecoveryDetails != null) {
				for (int i = 0; i < vecRecoveryDetails.size(); i++) {
					RecoveryDetails recoveryDetail = (RecoveryDetails) vecRecoveryDetails.elementAt(i);
					if (recoveryDetail != null) {
						cgpan = recoveryDetail.getCgpan();
						recoveryMode = recoveryDetail.getModeOfRecovery();
						tcPrincipal = recoveryDetail.getTcPrincipal();
						tcInterestAndOtherCharges = recoveryDetail.getTcInterestAndOtherCharges();
						wcAmount = recoveryDetail.getWcAmount();
						wcOtherCharges = recoveryDetail.getWcOtherCharges();
						if ((tcPrincipal != 0.0D) || (tcInterestAndOtherCharges != 0.0D) || (wcAmount != 0.0D)
								|| (wcOtherCharges != 0.0D)) {
							callableStmt = conn.prepareCall("{?=call funcInsertClaimRecoveryDetails(?,?,?,?,?,?,?,?)}");
							callableStmt.registerOutParameter(1, Types.INTEGER);
							callableStmt.setString(2, claimRefNumber);
							callableStmt.setString(3, cgpan);
							callableStmt.setString(4, recoveryMode);
							callableStmt.setDouble(5, tcPrincipal);
							callableStmt.setDouble(6, tcInterestAndOtherCharges);
							callableStmt.setDouble(7, wcAmount);
							callableStmt.setDouble(8, wcOtherCharges);
							callableStmt.registerOutParameter(9, java.sql.Types.VARCHAR);

							callableStmt.execute();
							status = callableStmt.getInt(1);
							errorCode = callableStmt.getString(9);
							if (status == 1) {
								Log.log(2, "CPDAO", "saveClaimApplication()",
										"SP returns a 1. Error code is :" + errorCode);
								callableStmt.close();
								try {
									conn.rollback();
								} catch (SQLException sqlex) {
									throw new DatabaseException(sqlex.getMessage());
								}
								throw new DatabaseException(errorCode);
							}

							callableStmt.close();
						}
					}
				}
			}
			Log.log(4, "CPDAO", "saveClaimApplication()", "Done with saving Recovery Details of Claim Application");

			Log.log(4, "CPDAO", "saveClaimApplication()",
					"Saving Security and Personal Guarantee Dtls of Claim Application for as on Sanction......");
			SecurityAndPersonalGuaranteeDtls secAndPerGuaranteeDtl = claimApplication
					.getSecurityAndPersonalGuaranteeDtls();

			DtlsAsOnDateOfNPA dtlsAsOnNPA = secAndPerGuaranteeDtl.getDetailsAsOnDateOfNPA();
			DtlsAsOnDateOfSanction dtlsAsOnSanction = secAndPerGuaranteeDtl.getDetailsAsOnDateOfSanction();
			DtlsAsOnLogdementOfClaim dtlsAsOnLodgeOfClaim = secAndPerGuaranteeDtl
					.getDetailsAsOnDateOfLodgementOfClaim();
			DtlsAsOnLogdementOfSecondClaim dtlsAsOnLodgemntOfSecClm = secAndPerGuaranteeDtl
					.getDetailsAsOnDateOfLodgementOfSecondClaim();

			double networthAsOnSanction = dtlsAsOnSanction.getNetworthOfGuarantors();
			double amntRealizedThruInvocationPerGuar = 0.0D;
			double amntRealizedThruSecurity = 0.0D;
			String reasonForReduction = null;
			String reasonForReductionAsOnSanction = dtlsAsOnSanction.getReasonsForReduction();
			String claimSecurityIdAsOnSanction = null;

			callableStmt = conn.prepareCall("{?=call funcInsertClaimSecurityDetail(?,?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt.setString(2, claimRefNumber);
			callableStmt.setString(3, ClaimConstants.CLM_SAPGD_AS_ON_SANCTION_CODE);
			callableStmt.setDouble(4, networthAsOnSanction);
			callableStmt.setString(5, reasonForReductionAsOnSanction);
			callableStmt.setDouble(6, amntRealizedThruInvocationPerGuar);
			callableStmt.registerOutParameter(7, java.sql.Types.VARCHAR);
			callableStmt.registerOutParameter(8, java.sql.Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(8);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing As on Dt of Sanction Dtl. Error code is :" + errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			if (status == 0) {
				claimSecurityIdAsOnSanction = callableStmt.getString(7);

				callableStmt.close();
			}

			// Calling the next SP to save security and personal guarantee
			// particulars
			// For Land
			callableStmt = conn.prepareCall("{?=call funcInsertClaimPerGuarDetail(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			if ((claimSecurityIdAsOnSanction != null) && (!(claimSecurityIdAsOnSanction.equals("")))) {
				callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnSanction));
			}
			callableStmt.setString(3, ClaimConstants.CLM_SAPGD_PARTICULAR_LAND);
			double vallandsanction = dtlsAsOnSanction.getValueOfLand();
			callableStmt.setDouble(4, vallandsanction);
			callableStmt.setDouble(5, amntRealizedThruSecurity);
			callableStmt.setString(6, reasonForReduction);
			callableStmt.registerOutParameter(7, java.sql.Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing Land as on Dt of Sanction. Error code is :" + errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			Log.log(4, "CPDAO", "saveClaimApplication()", "Successfully saved - Land for As on Dt of Sanction");

			callableStmt = conn.prepareCall("{?=call funcInsertClaimPerGuarDetail(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			if ((claimSecurityIdAsOnSanction != null) && (!claimSecurityIdAsOnSanction.equals(""))) {
				callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnSanction));
			}
			callableStmt.setString(3, "MACHINE");
			double valmachinesanction = dtlsAsOnSanction.getValueOfMachine();
			callableStmt.setDouble(4, valmachinesanction);
			callableStmt.setDouble(5, amntRealizedThruSecurity);
			callableStmt.setString(6, reasonForReduction);
			callableStmt.registerOutParameter(7, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing Machine as on Dt of Sanction. Error code is :" + errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			Log.log(4, "CPDAO", "saveClaimApplication()", "Successfully saved - Machine for As on Dt of Sanction");

			// For Building
			callableStmt = conn.prepareCall("{?=call funcInsertClaimPerGuarDetail(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			if ((claimSecurityIdAsOnSanction != null) && (!(claimSecurityIdAsOnSanction.equals("")))) {
				callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnSanction));
			}
			callableStmt.setString(3, ClaimConstants.CLM_SAPGD_PARTICULAR_BLDG);
			double valbldgesanction = dtlsAsOnSanction.getValueOfBuilding();
			callableStmt.setDouble(4, valbldgesanction);
			callableStmt.setDouble(5, amntRealizedThruSecurity);
			callableStmt.setString(6, reasonForReduction);
			callableStmt.registerOutParameter(7, java.sql.Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing Building as on Dt of Sanction. Error code is :" + errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			Log.log(4, "CPDAO", "saveClaimApplication()", "Successfully saved - Building for As on Dt of Sanction");

			// For Other Fixed/Movable Assets
			callableStmt = conn.prepareCall("{?=call funcInsertClaimPerGuarDetail(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			if ((claimSecurityIdAsOnSanction != null) && (!(claimSecurityIdAsOnSanction.equals("")))) {
				callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnSanction));
			}
			callableStmt.setString(3, ClaimConstants.CLM_SAPGD_PARTICULAR_OTHER_FIXED_MOV_ASSETS);
			double valfixedmovableassetssanction = dtlsAsOnSanction.getValueOfOtherFixedMovableAssets();
			callableStmt.setDouble(4, valfixedmovableassetssanction);
			callableStmt.setDouble(5, amntRealizedThruSecurity);
			callableStmt.setString(6, reasonForReduction);
			callableStmt.registerOutParameter(7, java.sql.Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing Other Fixed/Movable Assets as on Dt of Sanction. Error code is :"
								+ errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			Log.log(4, "CPDAO", "saveClaimApplication()",
					"Successfully saved - Fixed and Movable Assets for As on Dt of Sanction");

			// For Current Assets
			callableStmt = conn.prepareCall("{?=call funcInsertClaimPerGuarDetail(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			if ((claimSecurityIdAsOnSanction != null) && (!(claimSecurityIdAsOnSanction.equals("")))) {
				callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnSanction));
			}
			callableStmt.setString(3, ClaimConstants.CLM_SAPGD_PARTICULAR_CUR_ASSETS);
			double valcurrassetssanction = dtlsAsOnSanction.getValueOfCurrentAssets();
			callableStmt.setDouble(4, valcurrassetssanction);
			callableStmt.setDouble(5, amntRealizedThruSecurity);
			callableStmt.setString(6, reasonForReduction);
			callableStmt.registerOutParameter(7, java.sql.Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing Current Assets as on Dt of Sanction. Error code is :" + errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			Log.log(4, "CPDAO", "saveClaimApplication()",
					"Successfully saved - Current Assets for As on Dt of Sanction");

			// For other assets
			callableStmt = conn.prepareCall("{?=call funcInsertClaimPerGuarDetail(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			if ((claimSecurityIdAsOnSanction != null) && (!(claimSecurityIdAsOnSanction.equals("")))) {
				callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnSanction));
			}
			callableStmt.setString(3, ClaimConstants.CLM_SAPGD_PARTICULAR_OTHERS);
			double valotherssanction = dtlsAsOnSanction.getValueOfOthers();
			callableStmt.setDouble(4, valotherssanction);
			callableStmt.setDouble(5, amntRealizedThruSecurity);
			callableStmt.setString(6, reasonForReduction);
			callableStmt.registerOutParameter(7, java.sql.Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing Other Assets as on Dt of Sanction. Error code is :" + errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			Log.log(4, "CPDAO", "saveClaimApplication()", "Successfully saved - Other Assets for As on Dt of Sanction");
			Log.log(4, "CPDAO", "saveClaimApplication()",
					"Done with Security and Personal Guarantee Dtls of Claim Application for as on Sanction");

			Log.log(4, "CPDAO", "saveClaimApplication()",
					"Saving Security and Personal Guarantee Dtls of Claim Application for as on date of NPA");
			double networthAsOnNPA = dtlsAsOnNPA.getNetworthOfGuarantors();
			String reasonForReductionAsOnNPA = dtlsAsOnNPA.getReasonsForReduction();
			String claimSecurityIdAsOnNPA = null;

			callableStmt = conn.prepareCall("{?=call funcInsertClaimSecurityDetail(?,?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt.setString(2, claimRefNumber);
			callableStmt.setString(3, ClaimConstants.CLM_SAPGD_AS_ON_NPA_CODE);
			callableStmt.setDouble(4, networthAsOnNPA);
			callableStmt.setString(5, reasonForReductionAsOnNPA);
			callableStmt.setDouble(6, amntRealizedThruInvocationPerGuar);
			callableStmt.registerOutParameter(7, java.sql.Types.VARCHAR);
			callableStmt.registerOutParameter(8, java.sql.Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(8);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing NPA Dtls. Error code is :" + errorCode);

				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			if (status == 0) {
				claimSecurityIdAsOnNPA = callableStmt.getString(7);

				callableStmt.close();
			}

			// Calling the next SP to save security and personal guarantee
			// particulars
			// For Land
			callableStmt = conn.prepareCall("{?=call funcInsertClaimPerGuarDetail(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			if ((claimSecurityIdAsOnNPA != null) && (!(claimSecurityIdAsOnNPA.equals("")))) {
				callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnNPA));
			}
			callableStmt.setString(3, ClaimConstants.CLM_SAPGD_PARTICULAR_LAND);
			double vallandnpa = dtlsAsOnNPA.getValueOfLand();
			callableStmt.setDouble(4, vallandnpa);
			callableStmt.setDouble(5, amntRealizedThruSecurity);
			callableStmt.setString(6, reasonForReduction);
			callableStmt.registerOutParameter(7, java.sql.Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing Land as on NPA. Error code is :" + errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			Log.log(4, "CPDAO", "saveClaimApplication()", "Successfully saved - Land for As on Dt of NPA");

			callableStmt = conn.prepareCall("{?=call funcInsertClaimPerGuarDetail(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			if ((claimSecurityIdAsOnNPA != null) && (!claimSecurityIdAsOnNPA.equals(""))) {
				callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnNPA));
			}
			callableStmt.setString(3, "MACHINE");
			double valmachinenpa = dtlsAsOnNPA.getValueOfMachine();
			callableStmt.setDouble(4, valmachinenpa);
			callableStmt.setDouble(5, amntRealizedThruSecurity);
			callableStmt.setString(6, reasonForReduction);
			callableStmt.registerOutParameter(7, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing Machine as on NPA. Error code is :" + errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			Log.log(4, "CPDAO", "saveClaimApplication()", "Successfully saved - Machine for As on Dt of NPA");

			callableStmt = conn.prepareCall("{?=call funcInsertClaimPerGuarDetail(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			if ((claimSecurityIdAsOnNPA != null) && (!claimSecurityIdAsOnNPA.equals(""))) {
				callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnNPA));
			}
			callableStmt.setString(3, "BUILDING");
			double valbldgnpa = dtlsAsOnNPA.getValueOfBuilding();
			callableStmt.setDouble(4, valbldgnpa);
			callableStmt.setDouble(5, amntRealizedThruSecurity);
			callableStmt.setString(6, reasonForReduction);
			callableStmt.registerOutParameter(7, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing Building as on NPA. Error code is :" + errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			Log.log(4, "CPDAO", "saveClaimApplication()", "Successfully saved - Building for As on Dt of NPA");

			callableStmt = conn.prepareCall("{?=call funcInsertClaimPerGuarDetail(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			if ((claimSecurityIdAsOnNPA != null) && (!claimSecurityIdAsOnNPA.equals(""))) {
				callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnNPA));
			}
			callableStmt.setString(3, "OTHER FIXED MOVABLE ASSETS");
			double valfixedmovassetsnpa = dtlsAsOnNPA.getValueOfOtherFixedMovableAssets();
			callableStmt.setDouble(4, valfixedmovassetsnpa);
			callableStmt.setDouble(5, amntRealizedThruSecurity);
			callableStmt.setString(6, reasonForReduction);
			callableStmt.registerOutParameter(7, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing Fixed/Movable Assets as on NPA. Error code is :" + errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			Log.log(4, "CPDAO", "saveClaimApplication()",
					"Successfully saved - Fixed and Movable Assets for As on Dt of NPA");

			callableStmt = conn.prepareCall("{?=call funcInsertClaimPerGuarDetail(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			if ((claimSecurityIdAsOnNPA != null) && (!claimSecurityIdAsOnNPA.equals(""))) {
				callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnNPA));
			}
			callableStmt.setString(3, "CURRENT ASSETS");
			double valcurrassetsnpa = dtlsAsOnNPA.getValueOfCurrentAssets();
			callableStmt.setDouble(4, valcurrassetsnpa);
			callableStmt.setDouble(5, amntRealizedThruSecurity);
			callableStmt.setString(6, reasonForReduction);
			callableStmt.registerOutParameter(7, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing Current Assets as on NPA. Error code is :" + errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			Log.log(4, "CPDAO", "saveClaimApplication()", "Successfully saved - Current Assets for As on Dt of NPA");

			callableStmt = conn.prepareCall("{?=call funcInsertClaimPerGuarDetail(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			if ((claimSecurityIdAsOnNPA != null) && (!claimSecurityIdAsOnNPA.equals(""))) {
				callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnNPA));
			}
			callableStmt.setString(3, "OTHERS");
			double valothersnpa = dtlsAsOnNPA.getValueOfOthers();
			callableStmt.setDouble(4, valothersnpa);
			callableStmt.setDouble(5, amntRealizedThruSecurity);
			callableStmt.setString(6, reasonForReduction);
			callableStmt.registerOutParameter(7, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing Others as on NPA. Error code is :" + errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			Log.log(4, "CPDAO", "saveClaimApplication()", "Successfully saved - Other Assets for As on Dt of NPA");
			Log.log(4, "CPDAO", "saveClaimApplication()",
					"Done with saving Security and Personal Guarantee Dtls of Claim Application for as on dt of npa");

			Log.log(4, "CPDAO", "saveClaimApplication()",
					"Saving Security and Personal Guarantee Details of Claim Application for as on date of lodgement of claim");
			double networthAsOnLodgement = dtlsAsOnLodgeOfClaim.getNetworthOfGuarantors();
			String reasonForReductionAsOnLodgeOfClaim = dtlsAsOnLodgeOfClaim.getReasonsForReduction();
			String claimSecurityIdAsOnLodgeOfClaim = null;

			callableStmt = conn.prepareCall("{?=call funcInsertClaimSecurityDetail(?,?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			callableStmt.setString(2, claimRefNumber);
			callableStmt.setString(3, "CLM");
			callableStmt.setDouble(4, networthAsOnLodgement);
			callableStmt.setString(5, reasonForReductionAsOnLodgeOfClaim);
			callableStmt.setDouble(6, amntRealizedThruInvocationPerGuar);
			callableStmt.registerOutParameter(7, Types.VARCHAR);
			callableStmt.registerOutParameter(8, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(8);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing As on Dt of Lodgemnt of Claim Dtl. Error code is :" + errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			if (status == 0) {
				claimSecurityIdAsOnLodgeOfClaim = callableStmt.getString(7);

				callableStmt.close();
			}

			callableStmt = conn.prepareCall("{?=call funcInsertClaimPerGuarDetail(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			if ((claimSecurityIdAsOnLodgeOfClaim != null) && (!claimSecurityIdAsOnLodgeOfClaim.equals(""))) {
				callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnLodgeOfClaim));
			}
			callableStmt.setString(3, "LAND");
			double vallandlodgeclm = dtlsAsOnLodgeOfClaim.getValueOfLand();
			callableStmt.setDouble(4, vallandlodgeclm);
			callableStmt.setDouble(5, amntRealizedThruSecurity);
			callableStmt.setString(6, reasonForReduction);
			callableStmt.registerOutParameter(7, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing Land As on Dt of Lodgemnt of Claim Dtl. Error code is :"
								+ errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			Log.log(4, "CPDAO", "saveClaimApplication()",
					"Successfully saved - Land for As on Dt of Lodgement of First Claim");

			callableStmt = conn.prepareCall("{?=call funcInsertClaimPerGuarDetail(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			if ((claimSecurityIdAsOnLodgeOfClaim != null) && (!claimSecurityIdAsOnLodgeOfClaim.equals(""))) {
				callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnLodgeOfClaim));
			}
			callableStmt.setString(3, "MACHINE");
			double valmclodgeclm = dtlsAsOnLodgeOfClaim.getValueOfMachine();
			callableStmt.setDouble(4, valmclodgeclm);
			callableStmt.setDouble(5, amntRealizedThruSecurity);
			callableStmt.setString(6, reasonForReduction);
			callableStmt.registerOutParameter(7, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing Machine As on Dt of Lodgemnt of Claim Dtl. Error code is :"
								+ errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			Log.log(4, "CPDAO", "saveClaimApplication()",
					"Successfully saved - Machine for As on Dt of Lodgement of First Claim");

			callableStmt = conn.prepareCall("{?=call funcInsertClaimPerGuarDetail(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			if ((claimSecurityIdAsOnLodgeOfClaim != null) && (!claimSecurityIdAsOnLodgeOfClaim.equals(""))) {
				callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnLodgeOfClaim));
			}
			callableStmt.setString(3, "BUILDING");
			double valbldglodgeclm = dtlsAsOnLodgeOfClaim.getValueOfBuilding();
			callableStmt.setDouble(4, valbldglodgeclm);
			callableStmt.setDouble(5, amntRealizedThruSecurity);
			callableStmt.setString(6, reasonForReduction);
			callableStmt.registerOutParameter(7, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing Building As on Dt of Lodgemnt of Claim Dtl. Error code is :"
								+ errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			Log.log(4, "CPDAO", "saveClaimApplication()",
					"Successfully saved - Building for As on Dt of Lodgement of First Claim");

			callableStmt = conn.prepareCall("{?=call funcInsertClaimPerGuarDetail(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			if ((claimSecurityIdAsOnLodgeOfClaim != null) && (!claimSecurityIdAsOnLodgeOfClaim.equals(""))) {
				callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnLodgeOfClaim));
			}
			callableStmt.setString(3, "OTHER FIXED MOVABLE ASSETS");
			double valfixedmovassetslodgeclm = dtlsAsOnLodgeOfClaim.getValueOfOtherFixedMovableAssets();
			callableStmt.setDouble(4, valfixedmovassetslodgeclm);
			callableStmt.setDouble(5, amntRealizedThruSecurity);
			callableStmt.setString(6, reasonForReduction);
			callableStmt.registerOutParameter(7, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing Fixed Movable Assets As on Dt of Lodgemnt of Claim Dtl. Error code is :"
								+ errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			Log.log(4, "CPDAO", "saveClaimApplication()",
					"Successfully saved - Fixed Assets for As on Dt of Lodgement of First Claim");

			callableStmt = conn.prepareCall("{?=call funcInsertClaimPerGuarDetail(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			if ((claimSecurityIdAsOnLodgeOfClaim != null) && (!claimSecurityIdAsOnLodgeOfClaim.equals(""))) {
				callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnLodgeOfClaim));
			}
			callableStmt.setString(3, "CURRENT ASSETS");
			double valcurrassetslodgeclm = dtlsAsOnLodgeOfClaim.getValueOfCurrentAssets();
			callableStmt.setDouble(4, valcurrassetslodgeclm);
			callableStmt.setDouble(5, amntRealizedThruSecurity);
			callableStmt.setString(6, reasonForReduction);
			callableStmt.registerOutParameter(7, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing Current Assets As on Dt of Lodgemnt of Claim Dtl. Error code is :"
								+ errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			Log.log(4, "CPDAO", "saveClaimApplication()",
					"Successfully saved - Current Assets for As on Dt of Lodgement of First Claim");

			callableStmt = conn.prepareCall("{?=call funcInsertClaimPerGuarDetail(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			if ((claimSecurityIdAsOnLodgeOfClaim != null) && (!claimSecurityIdAsOnLodgeOfClaim.equals(""))) {
				callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnLodgeOfClaim));
			}
			callableStmt.setString(3, "OTHERS");
			double valotherslodgeclm = dtlsAsOnLodgeOfClaim.getValueOfOthers();
			callableStmt.setDouble(4, valotherslodgeclm);
			callableStmt.setDouble(5, amntRealizedThruSecurity);
			callableStmt.setString(6, reasonForReduction);
			callableStmt.registerOutParameter(7, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing Others As on Dt of Lodgemnt of Claim Dtl. Error code is :"
								+ errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			Log.log(4, "CPDAO", "saveClaimApplication()",
					"Successfully saved - Other Assets for As on Dt of Lodgement of First Claim");
			Log.log(4, "CPDAO", "saveClaimApplication()",
					"Done with saving Security and Personal Guarantee Details of Claim Application for as on dt of lodgement of claim");

			Log.log(4, "CPDAO", "saveClaimApplication()",
					"Saving Security and Personal Guarantee Details of Claim Application for as on dt of lodgement of second claim.....");
			if (claimApplication.getSecondInstallment()) {
				double networthAsOnLodgmntOfSecClm = dtlsAsOnLodgemntOfSecClm.getNetworthOfGuarantors();
				amntRealizedThruInvocationPerGuar = dtlsAsOnLodgemntOfSecClm.getAmtRealisedPersonalGuarantee();

				String reasonForReductionAsOnLodgeOfSecClaim = "";
				String claimSecurityIdAsOnLodgeOfSecClaim = null;

				callableStmt = conn.prepareCall("{?=call funcInsertClaimSecurityDetail(?,?,?,?,?,?,?)}");
				callableStmt.registerOutParameter(1, Types.INTEGER);
				callableStmt.setString(2, claimRefNumber);
				callableStmt.setString(3, "SCLM");
				callableStmt.setDouble(4, networthAsOnLodgmntOfSecClm);
				callableStmt.setString(5, reasonForReductionAsOnLodgeOfSecClaim);
				callableStmt.setDouble(6, amntRealizedThruInvocationPerGuar);
				callableStmt.registerOutParameter(7, Types.VARCHAR);
				callableStmt.registerOutParameter(8, Types.VARCHAR);

				callableStmt.execute();
				status = callableStmt.getInt(1);
				errorCode = callableStmt.getString(8);
				if (status == 1) {
					Log.log(2, "CPDAO", "saveClaimApplication()",
							"SP returns a 1 in storing As on Dt of Lodgemnt of Second Claim Dtl. Error code is :"
									+ errorCode);
					callableStmt.close();
					try {
						conn.rollback();
					} catch (SQLException sqlex) {
						throw new DatabaseException(sqlex.getMessage());
					}
					throw new DatabaseException(errorCode);
				}
				if (status == 0) {
					claimSecurityIdAsOnLodgeOfSecClaim = callableStmt.getString(7);

					callableStmt.close();
				}

				amntRealizedThruSecurity = dtlsAsOnLodgemntOfSecClm.getAmtRealisedLand();
				reasonForReduction = dtlsAsOnLodgemntOfSecClm.getReasonsForReductionLand();
				callableStmt = conn.prepareCall("{?=call funcInsertClaimPerGuarDetail(?,?,?,?,?,?)}");
				callableStmt.registerOutParameter(1, Types.INTEGER);
				if ((claimSecurityIdAsOnLodgeOfSecClaim != null) && (!claimSecurityIdAsOnLodgeOfSecClaim.equals(""))) {
					callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnLodgeOfSecClaim));
				}
				callableStmt.setString(3, "LAND");
				double vallandlodgeOfSecclm = dtlsAsOnLodgemntOfSecClm.getValueOfLand();
				callableStmt.setDouble(4, vallandlodgeOfSecclm);
				callableStmt.setDouble(5, amntRealizedThruSecurity);
				callableStmt.setString(6, reasonForReduction);
				callableStmt.registerOutParameter(7, Types.VARCHAR);

				callableStmt.execute();
				status = callableStmt.getInt(1);
				errorCode = callableStmt.getString(7);
				if (status == 1) {
					Log.log(2, "CPDAO", "saveClaimApplication()",
							"SP returns a 1 in storing Land As on Dt of Lodgemnt of Second Claim Dtl. Error code is :"
									+ errorCode);
					callableStmt.close();
					try {
						conn.rollback();
					} catch (SQLException sqlex) {
						throw new DatabaseException(sqlex.getMessage());
					}
					throw new DatabaseException(errorCode);
				}
				callableStmt.close();
				Log.log(4, "CPDAO", "saveClaimApplication()",
						"Successfully saved - Land for As on Dt of Lodgement of Second Claim");

				amntRealizedThruSecurity = dtlsAsOnLodgemntOfSecClm.getAmtRealisedMachine();
				reasonForReduction = dtlsAsOnLodgemntOfSecClm.getReasonsForReductionMachine();
				callableStmt = conn.prepareCall("{?=call funcInsertClaimPerGuarDetail(?,?,?,?,?,?)}");
				callableStmt.registerOutParameter(1, Types.INTEGER);
				if ((claimSecurityIdAsOnLodgeOfSecClaim != null) && (!claimSecurityIdAsOnLodgeOfSecClaim.equals(""))) {
					callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnLodgeOfSecClaim));
				}
				callableStmt.setString(3, "MACHINE");
				double valmclodgeOfSecclm = dtlsAsOnLodgemntOfSecClm.getValueOfMachine();
				callableStmt.setDouble(4, valmclodgeOfSecclm);
				callableStmt.setDouble(5, amntRealizedThruSecurity);
				callableStmt.setString(6, reasonForReduction);
				callableStmt.registerOutParameter(7, Types.VARCHAR);

				callableStmt.execute();
				status = callableStmt.getInt(1);
				errorCode = callableStmt.getString(7);
				if (status == 1) {
					Log.log(2, "CPDAO", "saveClaimApplication()",
							"SP returns a 1 in storing Machine As on Dt of Lodgemnt of Second Claim Dtl. Error code is :"
									+ errorCode);
					callableStmt.close();
					try {
						conn.rollback();
					} catch (SQLException sqlex) {
						throw new DatabaseException(sqlex.getMessage());
					}
					throw new DatabaseException(errorCode);
				}
				callableStmt.close();
				Log.log(4, "CPDAO", "saveClaimApplication()",
						"Successfully saved - Machine for As on Dt of Lodgement of Second Claim");

				amntRealizedThruSecurity = dtlsAsOnLodgemntOfSecClm.getAmtRealisedBuilding();
				reasonForReduction = dtlsAsOnLodgemntOfSecClm.getReasonsForReductionBuilding();
				callableStmt = conn.prepareCall("{?=call funcInsertClaimPerGuarDetail(?,?,?,?,?,?)}");
				callableStmt.registerOutParameter(1, Types.INTEGER);
				if ((claimSecurityIdAsOnLodgeOfSecClaim != null) && (!claimSecurityIdAsOnLodgeOfSecClaim.equals(""))) {
					callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnLodgeOfSecClaim));
				}
				callableStmt.setString(3, "BUILDING");
				double valbldglodgeOfSecclm = dtlsAsOnLodgemntOfSecClm.getValueOfBuilding();
				callableStmt.setDouble(4, valbldglodgeOfSecclm);
				callableStmt.setDouble(5, amntRealizedThruSecurity);
				callableStmt.setString(6, reasonForReduction);
				callableStmt.registerOutParameter(7, Types.VARCHAR);

				callableStmt.execute();
				status = callableStmt.getInt(1);
				errorCode = callableStmt.getString(7);
				if (status == 1) {
					Log.log(2, "CPDAO", "saveClaimApplication()",
							"SP returns a 1 in storing Building As on Dt of Lodgemnt of Second Claim Dtl. Error code is :"
									+ errorCode);
					callableStmt.close();
					try {
						conn.rollback();
					} catch (SQLException sqlex) {
						throw new DatabaseException(sqlex.getMessage());
					}
					throw new DatabaseException(errorCode);
				}
				callableStmt.close();
				Log.log(4, "CPDAO", "saveClaimApplication()",
						"Successfully saved - Building for As on Dt of Lodgement of Second Claim");

				amntRealizedThruSecurity = dtlsAsOnLodgemntOfSecClm.getAmtRealisedFixed();
				reasonForReduction = dtlsAsOnLodgemntOfSecClm.getReasonsForReductionFixed();
				callableStmt = conn.prepareCall("{?=call funcInsertClaimPerGuarDetail(?,?,?,?,?,?)}");
				callableStmt.registerOutParameter(1, Types.INTEGER);
				if ((claimSecurityIdAsOnLodgeOfSecClaim != null) && (!claimSecurityIdAsOnLodgeOfSecClaim.equals(""))) {
					callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnLodgeOfSecClaim));
				}
				callableStmt.setString(3, "OTHER FIXED MOVABLE ASSETS");
				double valfixedmovassetslodgeOfSecclm = dtlsAsOnLodgemntOfSecClm.getValueOfOtherFixedMovableAssets();
				callableStmt.setDouble(4, valfixedmovassetslodgeOfSecclm);
				callableStmt.setDouble(5, amntRealizedThruSecurity);
				callableStmt.setString(6, reasonForReduction);
				callableStmt.registerOutParameter(7, Types.VARCHAR);

				callableStmt.execute();
				status = callableStmt.getInt(1);
				errorCode = callableStmt.getString(7);
				if (status == 1) {
					Log.log(2, "CPDAO", "saveClaimApplication()",
							"SP returns a 1 in storing Fixed/Movable Assets As on Dt of Lodgemnt of Second Claim Dtl. Error code is :"
									+ errorCode);
					callableStmt.close();
					try {
						conn.rollback();
					} catch (SQLException sqlex) {
						throw new DatabaseException(sqlex.getMessage());
					}
					throw new DatabaseException(errorCode);
				}
				callableStmt.close();
				Log.log(4, "CPDAO", "saveClaimApplication()",
						"Successfully saved - Fixed Assets for As on Dt of Lodgement of Second Claim");

				amntRealizedThruSecurity = dtlsAsOnLodgemntOfSecClm.getAmtRealisedCurrent();
				reasonForReduction = dtlsAsOnLodgemntOfSecClm.getReasonsForReductionCurrent();
				callableStmt = conn.prepareCall("{?=call funcInsertClaimPerGuarDetail(?,?,?,?,?,?)}");
				callableStmt.registerOutParameter(1, Types.INTEGER);
				if ((claimSecurityIdAsOnLodgeOfSecClaim != null) && (!claimSecurityIdAsOnLodgeOfSecClaim.equals(""))) {
					callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnLodgeOfSecClaim));
				}
				callableStmt.setString(3, "CURRENT ASSETS");
				double valcurrassetslodgeOfSecclm = dtlsAsOnLodgemntOfSecClm.getValueOfCurrentAssets();
				callableStmt.setDouble(4, valcurrassetslodgeOfSecclm);
				callableStmt.setDouble(5, amntRealizedThruSecurity);
				callableStmt.setString(6, reasonForReduction);
				callableStmt.registerOutParameter(7, Types.VARCHAR);

				callableStmt.execute();
				status = callableStmt.getInt(1);
				errorCode = callableStmt.getString(7);
				if (status == 1) {
					Log.log(2, "CPDAO", "saveClaimApplication()",
							"SP returns a 1 in storing Current Assets As on Dt of Lodgemnt of Second Claim Dtl. Error code is :"
									+ errorCode);
					callableStmt.close();
					try {
						conn.rollback();
					} catch (SQLException sqlex) {
						throw new DatabaseException(sqlex.getMessage());
					}
					throw new DatabaseException(errorCode);
				}
				callableStmt.close();
				Log.log(4, "CPDAO", "saveClaimApplication()",
						"Successfully saved - Current Assets for As on Dt of Lodgement of Second Claim");

				amntRealizedThruSecurity = dtlsAsOnLodgemntOfSecClm.getAmtRealisedOthers();
				reasonForReduction = dtlsAsOnLodgemntOfSecClm.getReasonsForReductionOthers();
				callableStmt = conn.prepareCall("{?=call funcInsertClaimPerGuarDetail(?,?,?,?,?,?)}");
				callableStmt.registerOutParameter(1, Types.INTEGER);
				if ((claimSecurityIdAsOnLodgeOfSecClaim != null) && (!claimSecurityIdAsOnLodgeOfSecClaim.equals(""))) {
					callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnLodgeOfSecClaim));
				}
				callableStmt.setString(3, "OTHERS");
				double valotherslodgeOfSecclm = dtlsAsOnLodgemntOfSecClm.getValueOfOthers();
				callableStmt.setDouble(4, valotherslodgeOfSecclm);
				callableStmt.setDouble(5, amntRealizedThruSecurity);
				callableStmt.setString(6, reasonForReduction);
				callableStmt.registerOutParameter(7, Types.VARCHAR);

				callableStmt.execute();
				status = callableStmt.getInt(1);
				errorCode = callableStmt.getString(7);
				if (status == 1) {
					Log.log(2, "CPDAO", "saveClaimApplication()",
							"SP returns a 1 in storing Others As on Dt of Lodgemnt of Second Claim Dtl. Error code is :"
									+ errorCode);
					callableStmt.close();
					try {
						conn.rollback();
					} catch (SQLException sqlex) {
						throw new DatabaseException(sqlex.getMessage());
					}
					throw new DatabaseException(errorCode);
				}
				callableStmt.close();
				Log.log(4, "CPDAO", "saveClaimApplication()",
						"Successfully saved - Other Assets for As on Dt of Lodgement of Second Claim");
			}
			Log.log(4, "CPDAO", "saveClaimApplication()",
					"Done with saving Security and Personal Guarantee Details of Claim Application for as on dt of lodgement of second claim");

			Log.log(4, "CPDAO", "saveClaimApplication()", "Saving Claim Summary Details of Claim Application");
			ArrayList claimsummarydtls = claimApplication.getClaimSummaryDtls();
			double appliedamnt = 0.0D;
			ClaimSummaryDtls dtls = null;
			if (claimsummarydtls != null) {
				for (int i = 0; i < claimsummarydtls.size(); i++) {
					dtls = (ClaimSummaryDtls) claimsummarydtls.get(i);
					cgpan = dtls.getCgpan();
					appliedamnt = dtls.getAmount();

					callableStmt = conn.prepareCall("{? = call funcInsertClaimAmount(?,?,?,?)}");
					callableStmt.registerOutParameter(1, Types.INTEGER);
					callableStmt.setString(2, claimRefNumber);
					callableStmt.setString(3, cgpan);
					callableStmt.setDouble(4, appliedamnt);
					callableStmt.registerOutParameter(5, Types.VARCHAR);

					callableStmt.execute();
					status = callableStmt.getInt(1);
					errorCode = callableStmt.getString(5);
					if (status == 1) {
						Log.log(2, "CPDAO", "saveClaimApplication()",
								"SP returns a 1 in storing Claim Summary Details. Error code is :" + errorCode);
						callableStmt.close();
						try {
							conn.rollback();
						} catch (SQLException sqlex) {
							throw new DatabaseException(sqlex.getMessage());
						}
						throw new DatabaseException(errorCode);
					}
					callableStmt.close();
				}
			}

			if (claimApplication.getSecondInstallment()) {
				if (npaDtls != null) {
					npaDtls.setWillfulDefaulter(whethereWillFulDfaulter);
					npaDtls.setDtOfConclusionOfRecProc(dtOfConclusionOfRecProc);
					npaDtls.setWhetherWrittenOff(whetherAccntWrittenOffBooks);
					npaDtls.setDtOnWhichAccntWrittenOff(dtOfWrittenOffBooks);

					updateNPADetails(npaDtls);

					updateLegalProceedingDetails(legalProceedingsDetail);
				}
			}
			//comented by AbhishekSikarwar
			//insertRecallAndLegalAttachments(claimApplication, claimRefNumber, flag);

			Log.log(4, "CPDAO", "saveClaimApplication()",
					"Done with saving Claim Summary Details of Claim Application");
			// conn.commit();

		} catch (DatabaseException sqlexception) {
			hasExceptionOccured = true;
			Log.log(4, "CPDAO", "saveClaimApplication()", "Error in saving the claim application.");
			try {
				callableStmt.close();
				conn.rollback();
			} catch (SQLException sqlex) {
				throw new DatabaseException(sqlex.getMessage());
			}
			throw new DatabaseException(sqlexception.getMessage());
		} catch (SQLException sqlexception) {
			hasExceptionOccured = true;
			Log.log(4, "CPDAO", "saveClaimApplication()", "Error in saving the claim application.");
			try {
				callableStmt.close();
				conn.rollback();
			} catch (SQLException sqlex) {
				throw new DatabaseException(sqlex.getMessage());
			}
			throw new DatabaseException(sqlexception.getMessage());
		}

		finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(4, "CPDAO", "saveClaimApplication()", "Exited!");
	}

	public ArrayList getAllClaimRefNums(String bankId, String zoneId, String branchId) throws DatabaseException {
		Log.log(4, "CPDAO", "getAllClaimRefNums()", "Entered!");
		String claimRefNum = null;
		String cgclan = null;

		CallableStatement callableStmt = null;
		ResultSet rs = null;
		Connection conn = null;
		Hashtable claimRefNumDetail = new Hashtable();
		ArrayList claimRefNumbers = new ArrayList();

		int status = -1;
		String errorCode = null;
		try {
			conn = DBConnection.getConnection();
			callableStmt = conn.prepareCall("{?=call packGetClmRefNoForMember.funcGetClmRefNoForMember(?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			callableStmt.setString(2, bankId);
			callableStmt.setString(3, zoneId);
			callableStmt.setString(4, branchId);
			callableStmt.registerOutParameter(5, Constants.CURSOR);
			callableStmt.registerOutParameter(6, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(6);
			if (status == 1) {
				Log.log(2, "CPDAO", "getAllClaimRefNums()", "SP returns a 1.Error code is :" + errorCode);
				callableStmt.close();
				throw new DatabaseException(errorCode);
			}
			if (status == 0) {
				rs = (ResultSet) callableStmt.getObject(5);
				while (rs.next()) {
					claimRefNum = rs.getString(1);
					cgclan = rs.getString(2);
					claimRefNumDetail.put("ClaimRefNumber", claimRefNum);
					claimRefNumDetail.put("cgclan", cgclan);
					claimRefNumbers.add(claimRefNumDetail);
				}

				rs.close();
				rs = null;
			}

			callableStmt.close();
		} catch (SQLException sqlexception) {
			Log.log(2, "CPDAO", "getAllClaimRefNums()",
					"Error retrieving all Claim Reference Numbers from the database");
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(4, "CPDAO", "getAllClaimRefNums()", "Exited!");

		return claimRefNumbers;
	}

	public Vector getOTSRequestDetails(String borrowerId) throws DatabaseException {
		Log.log(4, "CPDAO", "getOTSRequestDetails()", "Entered!");
		Hashtable cgpanloandetail = null;
		String cgpan = null;
		String tcSanctionedAmnt = null;
		String wcFBSanctionedAmnt = null;
		String wcNFBSanctionedAmnt = null;
		String approvedAmnt = null;
		String enhancedApprovedAmnt = null;
		ResultSet rs = null;
		Vector cgpanloandetails = new Vector();
		String reapproveAmount = null;
		String appRefNumber = null;
		ApplicationDAO appDAO = new ApplicationDAO();
		Application application = null;

		CallableStatement callableStmt = null;
		Connection conn = null;

		int status = -1;
		String errorCode = null;
		try {
			conn = DBConnection.getConnection();
			callableStmt = conn.prepareCall("{? = call packGetCGPANLoanDtlForBid.funcGetCGPANLoanDtlForBid(?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			callableStmt.setString(2, borrowerId);
			callableStmt.registerOutParameter(3, Constants.CURSOR);
			callableStmt.registerOutParameter(4, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(4);
			if (status == 1) {
				Log.log(2, "CPDAO", "getOTSRequestDetails()", "SP returns a 1.Error code is :" + errorCode);
				callableStmt.close();
				throw new DatabaseException(errorCode);
			}
			if (status == 0) {
				rs = (ResultSet) callableStmt.getObject(3);
				while (rs.next()) {
					cgpanloandetail = new Hashtable();
					cgpan = rs.getString(1);
					if (cgpan != null) {
						approvedAmnt = Double.toString(rs.getDouble(2));
						enhancedApprovedAmnt = Double.toString(rs.getDouble(3));
						tcSanctionedAmnt = Double.toString(rs.getDouble(4));
						wcFBSanctionedAmnt = Double.toString(rs.getDouble(5));
						wcNFBSanctionedAmnt = Double.toString(rs.getDouble(6));
						appRefNumber = getAppRefNumber(cgpan);
						if (appRefNumber != null) {
							application = appDAO.getAppForAppRef(null, appRefNumber);
						}
						if (application != null) {
							reapproveAmount = Double.toString(application.getReapprovedAmount());

							cgpanloandetail.put("CGPAN", cgpan);
							if (application.getReapprovedAmount() == 0.0D) {
								cgpanloandetail.put("ApprovedAmount", approvedAmnt);
							}
							cgpanloandetail.put("EnhancedApprovedAmount", enhancedApprovedAmnt);
							cgpanloandetail.put("TCSanctionedAmnt", tcSanctionedAmnt);
							cgpanloandetail.put("WCFundBasedSanctionedAmnt", wcFBSanctionedAmnt);
							cgpanloandetail.put("WCNFBSanctionedAmnt", wcNFBSanctionedAmnt);
							if (application.getReapprovedAmount() > 0.0D) {
								cgpanloandetail.put("ApprovedAmount", reapproveAmount);
							}

							cgpanloandetails.addElement(cgpanloandetail);
						}
					}
				}
				rs.close();
				rs = null;
			}

			callableStmt.close();
		} catch (SQLException sqlexception) {
			Log.log(2, "CPDAO", "getOTSRequestDetails()",
					"Error retrieving OTS Details for a Borrower from the database");

			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(4, "CPDAO", "getOTSRequestDetails()", "Exited!");

		return cgpanloandetails;
	}

	public void saveOTSDetail(OTSRequestDetail otsDetail, String userId) throws DatabaseException {
		Log.log(4, "CPDAO", "saveOTSDetail()", "Entered!");
		CallableStatement callableStmt = null;
		Connection conn = null;
		String borrowerId = otsDetail.getCgbid();
		String reasonForOTS = otsDetail.getReasonForOTS();
		String willfulDefaulter = otsDetail.getWillfulDefaulter();

		int status = -1;
		String errorCode = null;
		try {
			conn = DBConnection.getConnection();
			callableStmt = conn.prepareCall("{? = call funcInsertOTSRequest(?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			callableStmt.setString(2, borrowerId);
			callableStmt.setString(3, reasonForOTS);
			callableStmt.setString(4, willfulDefaulter);
			callableStmt.setString(5, userId);
			callableStmt.registerOutParameter(6, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(6);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveOTSDetail()",
						"SP returns a 1 in saving OTS Request Details.Error code is :" + errorCode);
				callableStmt.close();
				throw new DatabaseException(errorCode);
			}

			callableStmt.close();

			String cgpan = null;
			String proposedAmntPaid = null;
			String amountSacrificed = null;
			String osAmntAsOnDate = null;
			Vector details = otsDetail.getLoanDetails();

			for (int i = 0; i < details.size(); i++) {
				Hashtable ht = (Hashtable) details.elementAt(i);
				if (ht != null) {
					if (ht.containsKey("CGPAN")) {
						cgpan = (String) ht.get("CGPAN");
					}
					if (ht.containsKey("ProposedAmntPaid")) {
						proposedAmntPaid = (String) ht.get("ProposedAmntPaid");
					}
					if (ht.containsKey("ProposedAmntSacrificed")) {
						amountSacrificed = (String) ht.get("ProposedAmntSacrificed");
					}
					if (ht.containsKey("OutstandingAmntAsOnDate")) {
						osAmntAsOnDate = (String) ht.get("OutstandingAmntAsOnDate");
					}

					callableStmt = conn.prepareCall("{? = call funcInsertOTSAmount(?,?,?,?,?,?)}");
					callableStmt.registerOutParameter(1, Types.INTEGER);
					callableStmt.setString(2, borrowerId);
					callableStmt.setString(3, cgpan);
					if ((proposedAmntPaid != null) && (!proposedAmntPaid.equals(""))) {
						callableStmt.setDouble(4, Double.parseDouble(proposedAmntPaid));
					}
					if ((amountSacrificed != null) && (!amountSacrificed.equals(""))) {
						callableStmt.setDouble(5, Double.parseDouble(amountSacrificed));
					}
					if ((osAmntAsOnDate != null) && (!osAmntAsOnDate.equals(""))) {
						callableStmt.setDouble(6, Double.parseDouble(osAmntAsOnDate));
					}
					callableStmt.registerOutParameter(7, Types.VARCHAR);

					callableStmt.execute();
					status = callableStmt.getInt(1);
					errorCode = callableStmt.getString(7);
					if (status == 1) {
						Log.log(2, "CPDAO", "saveOTSDetail()",
								"SP returns a 1 in saving CGPAN wise OTS Request Details.Error code is :" + errorCode);
						throw new DatabaseException("OTS Details already exist for the Borrower");
					}

					callableStmt.close();
				}
			}
		} catch (SQLException sqlexception) {
			Log.log(2, "CPDAO", "saveOTSDetail()", "Error saving CGPAN-wise OTS Details into the database");
			try {
				conn.rollback();
			} catch (SQLException sqlex) {
				Log.log(2, "CPDAO", "saveOTSDetail()", "Error rolling back the transaction :" + sqlex.getMessage());
			}
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(4, "CPDAO", "saveOTSDetail()", "Exited!");
	}

	public Vector getToBeApprovedOTSRequests() throws DatabaseException {
		Log.log(4, "CPDAO", "getToBeApprovedOTSRequests()", "Entered!");
		String memberId = null;
		String bankId = null;
		String zoneId = null;
		String branchId = null;
		String borrowerId = null;
		java.util.Date otsRequestDate = null;

		OTSApprovalDetail otsApprovalDetail = null;
		Vector otsRequestDetails = new Vector();

		CallableStatement callableStmt = null;
		Connection conn = null;
		ResultSet resultset = null;

		int status = -1;
		String errorCode = null;
		try {
			conn = DBConnection.getConnection();
			callableStmt = conn.prepareCall("{? = call packGetPendingOTSRequests.funcGetPendingOTSRequests(?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			callableStmt.registerOutParameter(2, Constants.CURSOR);
			callableStmt.registerOutParameter(3, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(3);
			if (status == 1) {
				Log.log(2, "CPDAO", "getToBeApprovedOTSRequests()",
						"SP returns a 1 in saving CGPAN wise OTS Request Details.Error code is :" + errorCode);
				callableStmt.close();
				throw new DatabaseException(errorCode);
			}
			if (status == 0) {
				resultset = (ResultSet) callableStmt.getObject(2);

				while (resultset.next()) {
					otsApprovalDetail = new OTSApprovalDetail();
					borrowerId = resultset.getString(1);
					otsRequestDate = resultset.getDate(2);
					bankId = resultset.getString(3);
					zoneId = resultset.getString(4);
					branchId = resultset.getString(5);
					memberId = bankId + zoneId + branchId;

					otsApprovalDetail.setCgbid(borrowerId);
					otsApprovalDetail.setMliId(memberId);
					otsApprovalDetail.setOtsRequestDate(otsRequestDate);

					boolean duplicateobjectexists = false;
					for (int i = 0; i < otsRequestDetails.size(); i++) {
						OTSApprovalDetail temp = (OTSApprovalDetail) otsRequestDetails.elementAt(i);
						if (temp != null) {
							String tempmliid = temp.getMliId();
							String tempbid = temp.getCgbid();
							java.util.Date tempOtsReqDate = temp.getOtsRequestDate();
							if ((tempmliid.equals(memberId)) && (tempbid.equals(borrowerId))
									&& (tempOtsReqDate.equals(otsRequestDate))) {
								duplicateobjectexists = true;
								break;
							}
						}
					}
					if (!duplicateobjectexists) {
						otsRequestDetails.addElement(otsApprovalDetail);
					}
				}

				resultset.close();
				resultset = null;
			}

			callableStmt.close();
		} catch (SQLException sqlexception) {
			Log.log(2, "CPDAO", "getToBeApprovedOTSRequests()",
					"Error retrieving to be approved OTS Requests from the database");
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(4, "CPDAO", "getToBeApprovedOTSRequests()", "Exited!");

		return otsRequestDetails;
	}

	public void saveOTSProcessingResults(Vector otsApprovalDetails, String userId) throws DatabaseException {
		Log.log(4, "CPDAO", "saveOTSProcessingResults()", "Entered!");
		CallableStatement callableStmt = null;
		Connection conn = null;
		OTSApprovalDetail otsApprovalDtl = null;

		int status = -1;
		String errorCode = null;
		boolean isThereException = false;
		try {
			conn = DBConnection.getConnection();

			for (int i = 0; i < otsApprovalDetails.size(); i++) {
				otsApprovalDtl = (OTSApprovalDetail) otsApprovalDetails.elementAt(i);
				if (otsApprovalDtl != null) {
					String borrowerId = otsApprovalDtl.getCgbid();
					String decision = otsApprovalDtl.getDecision();
					String remarks = otsApprovalDtl.getRemarks();

					callableStmt = conn.prepareCall("{? = call funcUpdateOTSRequest(?,?,?,?,?)}");
					callableStmt.registerOutParameter(1, Types.INTEGER);
					callableStmt.setString(2, borrowerId);
					callableStmt.setString(3, decision);
					callableStmt.setString(4, remarks);
					callableStmt.setString(5, userId);
					callableStmt.registerOutParameter(6, Types.VARCHAR);

					callableStmt.execute();
					status = callableStmt.getInt(1);
					errorCode = callableStmt.getString(6);
					if (status == 1) {
						Log.log(2, "CPDAO", "saveOTSProcessingResults()",
								"SP returns a 1 in saving OTS Processing Results.Error code is :" + errorCode);
						callableStmt.close();
						throw new DatabaseException(errorCode);
					}

					callableStmt.close();
				}
			}
		} catch (SQLException sqlexception) {
			isThereException = true;
			Log.log(2, "CPDAO", "saveOTSProcessingResults()", "Error saving OTS Processing Results into the database");
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(4, "CPDAO", "saveOTSProcessingResults()", "Exited!");
	}

	public Vector getClaimApprovalDetails(String loggedUsr) throws DatabaseException {
		Log.log(4, "CPDAO", "getClaimApprovalDetails()", "Entered!");
		Vector claimDetails = new Vector();
		CallableStatement callableStmt = null;
		Connection conn = null;
		ResultSet resultset = null;
		ClaimDetail claimdetail = null;
		String flag = "F";
		String cgclan = null;
		String bid = null;
		String memberId = null;
		String claimRefNumber = null;
		double claimApprovedAmnt = 0.0D;
		double applicationApprovedAmnt = 0.0D;
		double tcApprovedAmt = 0.0D;
		double wcApprovedAmt = 0.0D;
		double tcOutstanding = 0.0D;
		double wcOutstanding = 0.0D;
		double tcrecovery = 0.0D;
		double wcrecovery = 0.0D;
		double tcEligibleAmt = 0.0D;
		double wcEligibleAmt = 0.0D;
		double tcDeduction = 0.0D;
		double wcDeduction = 0.0D;
		double tcFirstInstallment = 0.0D;
		double wcFirstInstallment = 0.0D;
		java.util.Date clmApprvdDate = null;
		java.util.Date npaEffectiveDate = null;
		double outstandingAmntAsOnNPA = 0.0D;
		double appliedClaimAmnt = 0.0D;
		String clmStatus = null;
		String comments = null;
		String forwardedToUser = null;
		String unitName = null;
		int status = -1;
		String errorCode = null;
		try {
			conn = DBConnection.getConnection();
			callableStmt = conn.prepareCall("{? = call packGetClmAppForUser.funcGetClmAppForUser(?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			callableStmt.setString(2, loggedUsr);
			callableStmt.registerOutParameter(3, Constants.CURSOR);
			callableStmt.registerOutParameter(4, Types.VARCHAR);
			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(4);
			if (status == 1) {
				Log.log(2, "CPDAO", "getClaimApprovalDetails()",
						"SP returns a 1 in retrieving Claim Processing Results.Error code is :" + errorCode);
				callableStmt.close();
				throw new DatabaseException(errorCode);
			}
			if (status == 0) {
				resultset = (ResultSet) callableStmt.getObject(3);

				while (resultset.next()) {
					memberId = resultset.getString(1);

					claimRefNumber = resultset.getString(2);

					unitName = resultset.getString(3);
					bid = resultset.getString(4);
					tcApprovedAmt = resultset.getDouble(5);
					wcApprovedAmt = resultset.getDouble(6);
					tcOutstanding = resultset.getDouble(7);
					wcOutstanding = resultset.getDouble(8);
					tcrecovery = resultset.getDouble(9);
					wcrecovery = resultset.getDouble(10);
					tcEligibleAmt = resultset.getDouble(11);
					wcEligibleAmt = resultset.getDouble(12);
					tcDeduction = resultset.getDouble(13);
					wcDeduction = resultset.getDouble(14);
					tcFirstInstallment = resultset.getDouble(15);
					wcFirstInstallment = resultset.getDouble(16);
					comments = resultset.getString(17);
					if (flag.equals("F")) {
						claimdetail = new ClaimDetail();
						claimdetail.setMliId(memberId);
						claimdetail.setClaimRefNum(claimRefNumber);
						claimdetail.setSsiUnitName(unitName);
						claimdetail.setBorrowerId(bid);
						claimdetail.setTcApprovedAmt(tcApprovedAmt);
						claimdetail.setWcApprovedAmt(wcApprovedAmt);
						claimdetail.setTcOutstanding(tcOutstanding);
						claimdetail.setWcOutstanding(wcOutstanding);
						claimdetail.setTcrecovery(tcrecovery);
						claimdetail.setWcrecovery(wcrecovery);
						claimdetail.setTcClaimEligibleAmt(tcEligibleAmt);
						claimdetail.setWcClaimEligibleAmt(wcEligibleAmt);
						claimdetail.setAsfDeductableforTC(tcDeduction);
						claimdetail.setAsfDeductableforWC(wcDeduction);
						claimdetail.setTcFirstInstallment(tcFirstInstallment);
						claimdetail.setWcFirstInstallment(wcFirstInstallment);
						claimdetail.setComments(comments);
					}
					if (claimdetail != null) {
						claimDetails.addElement(claimdetail);
					}
				}
				resultset.close();
				resultset = null;
			}

			callableStmt.close();
		} catch (SQLException sqlexception) {
			Log.log(2, "CPDAO", "getClaimApprovalDetails()",
					"Error retrieving Details for Processing Claims from the database");
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(4, "CPDAO", "getClaimApprovalDetails()", "Exited!");

		return claimDetails;
	}

	public Vector getClaimProcessingDetails(String flag) throws DatabaseException {
		Log.log(4, "CPDAO", "getClaimProcessingDetails()", "Entered!");
		Vector claimDetails = new Vector();
		CallableStatement callableStmt = null;
		Connection conn = null;
		ResultSet resultset = null;
		ClaimDetail claimdetail = null;
		ClaimDetail cd = null;

		String cgclan = null;
		String bid = null;
		String memberId = null;
		String bankId = null;
		String zoneId = null;
		String branchId = null;
		String claimRefNumber = null;
		double claimApprovedAmnt = 0.0D;
		double applicationApprovedAmnt = 0.0D;
		java.util.Date clmApprvdDate = null;
		java.util.Date npaEffectiveDate = null;
		double outstandingAmntAsOnNPA = 0.0D;
		double appliedClaimAmnt = 0.0D;
		String clmStatus = null;
		String comments = null;
		String forwardedToUser = null;
		String unitName = null;

		int status = -1;
		String errorCode = null;
		try {
			conn = DBConnection.getConnection();
			callableStmt = conn
					.prepareCall("{? = call packGetClaimstoAuthorizeDtl.funcGetClaimstoAuthorizeDtl(?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			callableStmt.setString(2, flag);
			callableStmt.registerOutParameter(3, Constants.CURSOR);
			callableStmt.registerOutParameter(4, Constants.CURSOR);
			callableStmt.registerOutParameter(5, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(5);
			if (status == 1) {
				Log.log(2, "CPDAO", "getClaimProcessingDetails()",
						"SP returns a 1 in retrieving Claim Processing Results.Error code is :" + errorCode);
				callableStmt.close();
				throw new DatabaseException(errorCode);
			}
			if (status == 0) {
				resultset = (ResultSet) callableStmt.getObject(3);

				while (resultset.next()) {
					cgclan = resultset.getString(1);
					bid = resultset.getString(2);
					bankId = resultset.getString(3);
					zoneId = resultset.getString(4);
					branchId = resultset.getString(5);
					memberId = bankId + zoneId + branchId;

					claimRefNumber = resultset.getString(6);

					clmStatus = resultset.getString(7);

					claimApprovedAmnt = resultset.getDouble(8);

					clmApprvdDate = resultset.getDate(9);

					comments = resultset.getString(10);

					forwardedToUser = resultset.getString(11);
					npaEffectiveDate = resultset.getDate(12);

					outstandingAmntAsOnNPA = resultset.getDouble(13);

					appliedClaimAmnt = resultset.getDouble(14);

					applicationApprovedAmnt = resultset.getDouble(15);

					unitName = resultset.getString(16);

					if (flag.equals("F")) {
						claimdetail = new ClaimDetail();
						claimdetail.setCGCLAN(cgclan);

						claimdetail.setBorrowerId(bid);
						claimdetail.setMliId(memberId);
						claimdetail.setClaimRefNum(claimRefNumber);

						claimdetail.setClmStatus(clmStatus);
						claimdetail.setApprovedClaimAmount(claimApprovedAmnt);
						claimdetail.setApplicationApprovedAmount(applicationApprovedAmnt);
						claimdetail.setClmApprvdDt(clmApprvdDate);
						claimdetail.setNpaDate(npaEffectiveDate);
						claimdetail.setOutstandingAmntAsOnNPADate(outstandingAmntAsOnNPA);
						claimdetail.setAppliedClaimAmt(appliedClaimAmnt);
						claimdetail.setComments(comments);
						claimdetail.setForwaredToUser(forwardedToUser);
						claimdetail.setSsiUnitName(unitName);
					} else if (flag.equals("S")) {
						HashMap details = getFirstClmDtlForBid(bankId, zoneId, branchId, bid);
						if (details != null) {
							if (details.containsKey("cgclan")) {
								String cgcln = (String) details.get("cgclan");

								claimdetail = new ClaimDetail();
								claimdetail.setCGCLAN(cgcln);
								claimdetail.setBorrowerId(bid);
								claimdetail.setMliId(memberId);
								claimdetail.setClaimRefNum(claimRefNumber);
								claimdetail.setClmStatus(clmStatus);
								claimdetail.setApprovedClaimAmount(claimApprovedAmnt);
								claimdetail.setApplicationApprovedAmount(applicationApprovedAmnt);
								claimdetail.setClmApprvdDt(clmApprvdDate);
								claimdetail.setNpaDate(npaEffectiveDate);
								claimdetail.setOutstandingAmntAsOnNPADate(outstandingAmntAsOnNPA);
								claimdetail.setAppliedClaimAmt(appliedClaimAmnt);
								claimdetail.setComments(comments);
								claimdetail.setForwaredToUser(forwardedToUser);
								claimdetail.setSsiUnitName(unitName);
							}
						}

					}

					if (claimdetail != null) {
						claimDetails.addElement(claimdetail);
					}

				}

				resultset.close();
				resultset = null;

				resultset = (ResultSet) callableStmt.getObject(4);
				int count = 0;
				while (resultset.next()) {
					cgclan = resultset.getString(1);
					bid = resultset.getString(2);
					bankId = resultset.getString(3);
					zoneId = resultset.getString(4);
					branchId = resultset.getString(5);
					memberId = bankId + zoneId + branchId;
					claimRefNumber = resultset.getString(6);
					clmStatus = resultset.getString(7);

					claimApprovedAmnt = resultset.getDouble(8);
					clmApprvdDate = resultset.getDate(9);
					comments = resultset.getString(10);
					forwardedToUser = resultset.getString(11);
					npaEffectiveDate = resultset.getDate(12);
					outstandingAmntAsOnNPA = resultset.getDouble(13);
					appliedClaimAmnt = resultset.getDouble(14);
					applicationApprovedAmnt = resultset.getDouble(15);
					unitName = resultset.getString(16);
					if (flag.equals("F")) {
						claimdetail = new ClaimDetail();
						claimdetail.setCGCLAN(cgclan);
						claimdetail.setBorrowerId(bid);
						claimdetail.setMliId(memberId);
						claimdetail.setClaimRefNum(claimRefNumber);
						claimdetail.setClmStatus(clmStatus);
						claimdetail.setApprovedClaimAmount(claimApprovedAmnt);
						claimdetail.setApplicationApprovedAmount(applicationApprovedAmnt);
						claimdetail.setClmApprvdDt(clmApprvdDate);
						claimdetail.setNpaDate(npaEffectiveDate);
						claimdetail.setOutstandingAmntAsOnNPADate(outstandingAmntAsOnNPA);
						claimdetail.setAppliedClaimAmt(appliedClaimAmnt);
						claimdetail.setComments(comments);
						claimdetail.setForwaredToUser(forwardedToUser);
						claimdetail.setSsiUnitName(unitName);
					} else if (flag.equals("S")) {
						HashMap details = getFirstClmDtlForBid(bankId, zoneId, branchId, bid);
						if (details != null) {
							if (details.containsKey("cgclan")) {
								String cgcln = (String) details.get("cgclan");

								claimdetail = new ClaimDetail();
								claimdetail.setCGCLAN(cgcln);
								claimdetail.setBorrowerId(bid);
								claimdetail.setMliId(memberId);
								claimdetail.setClaimRefNum(claimRefNumber);
								claimdetail.setClmStatus(clmStatus);
								claimdetail.setApprovedClaimAmount(claimApprovedAmnt);
								claimdetail.setApplicationApprovedAmount(applicationApprovedAmnt);
								claimdetail.setClmApprvdDt(clmApprvdDate);
								claimdetail.setNpaDate(npaEffectiveDate);
								claimdetail.setOutstandingAmntAsOnNPADate(outstandingAmntAsOnNPA);
								claimdetail.setAppliedClaimAmt(appliedClaimAmnt);
								claimdetail.setComments(comments);
								claimdetail.setForwaredToUser(forwardedToUser);
								claimdetail.setSsiUnitName(unitName);
							}

						}

					}

					if (claimDetails.size() == 0) {
						claimDetails.addElement(claimdetail);
					}

					String clmRefNum = null;

					for (int j = 0; j < claimDetails.size(); j++) {
						cd = (ClaimDetail) claimDetails.elementAt(j);
						clmRefNum = cd.getClaimRefNum();

						if (clmRefNum != null) {
							if (!clmRefNum.equals(claimRefNumber)) {
								if (!claimDetails.contains(claimdetail)) {
									claimDetails.addElement(claimdetail);
								}
							}
							if (clmRefNum.equals(claimRefNumber)) {
								cd = (ClaimDetail) claimDetails.remove(j);
								claimDetails.add(j, claimdetail);
							}
						}
					}
					count++;
				}
				resultset.close();
				resultset = null;
			}

			callableStmt.close();
		} catch (SQLException sqlexception) {
			Log.log(2, "CPDAO", "getClaimProcessingDetails()",
					"Error retrieving Details for Processing Claims from the database");
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(4, "CPDAO", "getClaimProcessingDetails()", "Exited!");

		return claimDetails;
	}

	public Vector getClaimProcessingDetailsMod(String flag, java.sql.Date fromDate, java.sql.Date toDate)
			throws DatabaseException {
		Log.log(4, "CPDAO", "getClaimProcessingDetailsMod()", "Entered!");
		Vector claimDetails = new Vector();
		CallableStatement callableStmt = null;
		Connection conn = null;
		ResultSet resultset = null;
		ClaimDetail claimdetail = null;
		ClaimDetail cd = null;

		String cgclan = null;
		String bid = null;
		String memberId = null;
		String bankId = null;
		String zoneId = null;
		String branchId = null;
		String claimRefNumber = null;
		double claimApprovedAmnt = 0.0D;
		double applicationApprovedAmnt = 0.0D;
		java.util.Date clmApprvdDate = null;
		java.util.Date npaEffectiveDate = null;
		double outstandingAmntAsOnNPA = 0.0D;
		double appliedClaimAmnt = 0.0D;
		String clmStatus = null;
		String comments = null;
		String forwardedToUser = null;
		String unitName = null;

		int status = -1;
		String errorCode = null;
		try {
			conn = DBConnection.getConnection();
			callableStmt = conn.prepareCall(
					"{? = call PackgetclaimstoauthorizedtlNew.funcGetClaimstoAuthorizeDtlNew(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			callableStmt.setString(2, flag);
			callableStmt.setDate(3, fromDate);
			callableStmt.setDate(4, toDate);
			callableStmt.registerOutParameter(5, Constants.CURSOR);
			callableStmt.registerOutParameter(6, Constants.CURSOR);
			callableStmt.registerOutParameter(7, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);
			if (status == 1) {
				Log.log(2, "CPDAO", "getClaimProcessingDetails()",
						"SP returns a 1 in retrieving Claim Processing Results.Error code is :" + errorCode);
				callableStmt.close();
				throw new DatabaseException(errorCode);
			}
			if (status == 0) {
				resultset = (ResultSet) callableStmt.getObject(5);

				while (resultset.next()) {
					cgclan = resultset.getString(1);
					bid = resultset.getString(2);
					bankId = resultset.getString(3);
					zoneId = resultset.getString(4);
					branchId = resultset.getString(5);
					memberId = bankId + zoneId + branchId;

					claimRefNumber = resultset.getString(6);

					clmStatus = resultset.getString(7);

					claimApprovedAmnt = resultset.getDouble(8);

					clmApprvdDate = resultset.getDate(9);

					comments = resultset.getString(10);

					forwardedToUser = resultset.getString(11);
					npaEffectiveDate = resultset.getDate(12);

					outstandingAmntAsOnNPA = resultset.getDouble(13);

					appliedClaimAmnt = resultset.getDouble(14);

					applicationApprovedAmnt = resultset.getDouble(15);

					unitName = resultset.getString(16);

					if (flag.equals("F")) {
						claimdetail = new ClaimDetail();
						claimdetail.setCGCLAN(cgclan);

						claimdetail.setBorrowerId(bid);
						claimdetail.setMliId(memberId);
						claimdetail.setClaimRefNum(claimRefNumber);

						claimdetail.setClmStatus(clmStatus);
						claimdetail.setApprovedClaimAmount(claimApprovedAmnt);
						claimdetail.setApplicationApprovedAmount(applicationApprovedAmnt);
						claimdetail.setClmApprvdDt(clmApprvdDate);
						claimdetail.setNpaDate(npaEffectiveDate);
						claimdetail.setOutstandingAmntAsOnNPADate(outstandingAmntAsOnNPA);
						claimdetail.setAppliedClaimAmt(appliedClaimAmnt);
						claimdetail.setComments(comments);
						claimdetail.setForwaredToUser(forwardedToUser);
						claimdetail.setSsiUnitName(unitName);
					} else if (flag.equals("S")) {
						HashMap details = getFirstClmDtlForBid(bankId, zoneId, branchId, bid);
						if (details != null) {
							if (details.containsKey("cgclan")) {
								String cgcln = (String) details.get("cgclan");

								claimdetail = new ClaimDetail();
								claimdetail.setCGCLAN(cgcln);
								claimdetail.setBorrowerId(bid);
								claimdetail.setMliId(memberId);
								claimdetail.setClaimRefNum(claimRefNumber);
								claimdetail.setClmStatus(clmStatus);
								claimdetail.setApprovedClaimAmount(claimApprovedAmnt);
								claimdetail.setApplicationApprovedAmount(applicationApprovedAmnt);
								claimdetail.setClmApprvdDt(clmApprvdDate);
								claimdetail.setNpaDate(npaEffectiveDate);
								claimdetail.setOutstandingAmntAsOnNPADate(outstandingAmntAsOnNPA);
								claimdetail.setAppliedClaimAmt(appliedClaimAmnt);
								claimdetail.setComments(comments);
								claimdetail.setForwaredToUser(forwardedToUser);
								claimdetail.setSsiUnitName(unitName);
							}
						}

					}

					if (claimdetail != null) {
						claimDetails.addElement(claimdetail);
					}

				}

				resultset.close();
				resultset = null;

				resultset = (ResultSet) callableStmt.getObject(6);
				int count = 0;
				while (resultset.next()) {
					cgclan = resultset.getString(1);
					bid = resultset.getString(2);
					bankId = resultset.getString(3);
					zoneId = resultset.getString(4);
					branchId = resultset.getString(5);
					memberId = bankId + zoneId + branchId;
					claimRefNumber = resultset.getString(6);
					clmStatus = resultset.getString(7);

					claimApprovedAmnt = resultset.getDouble(8);
					clmApprvdDate = resultset.getDate(9);
					comments = resultset.getString(10);
					forwardedToUser = resultset.getString(11);
					npaEffectiveDate = resultset.getDate(12);
					outstandingAmntAsOnNPA = resultset.getDouble(13);
					appliedClaimAmnt = resultset.getDouble(14);
					applicationApprovedAmnt = resultset.getDouble(15);
					unitName = resultset.getString(16);
					if (flag.equals("F")) {
						claimdetail = new ClaimDetail();
						claimdetail.setCGCLAN(cgclan);
						claimdetail.setBorrowerId(bid);
						claimdetail.setMliId(memberId);
						claimdetail.setClaimRefNum(claimRefNumber);
						claimdetail.setClmStatus(clmStatus);
						claimdetail.setApprovedClaimAmount(claimApprovedAmnt);
						claimdetail.setApplicationApprovedAmount(applicationApprovedAmnt);
						claimdetail.setClmApprvdDt(clmApprvdDate);
						claimdetail.setNpaDate(npaEffectiveDate);
						claimdetail.setOutstandingAmntAsOnNPADate(outstandingAmntAsOnNPA);
						claimdetail.setAppliedClaimAmt(appliedClaimAmnt);
						claimdetail.setComments(comments);
						claimdetail.setForwaredToUser(forwardedToUser);
						claimdetail.setSsiUnitName(unitName);
					} else if (flag.equals("S")) {
						HashMap details = getFirstClmDtlForBid(bankId, zoneId, branchId, bid);
						if (details != null) {
							if (details.containsKey("cgclan")) {
								String cgcln = (String) details.get("cgclan");

								claimdetail = new ClaimDetail();
								claimdetail.setCGCLAN(cgcln);
								claimdetail.setBorrowerId(bid);
								claimdetail.setMliId(memberId);
								claimdetail.setClaimRefNum(claimRefNumber);
								claimdetail.setClmStatus(clmStatus);
								claimdetail.setApprovedClaimAmount(claimApprovedAmnt);
								claimdetail.setApplicationApprovedAmount(applicationApprovedAmnt);
								claimdetail.setClmApprvdDt(clmApprvdDate);
								claimdetail.setNpaDate(npaEffectiveDate);
								claimdetail.setOutstandingAmntAsOnNPADate(outstandingAmntAsOnNPA);
								claimdetail.setAppliedClaimAmt(appliedClaimAmnt);
								claimdetail.setComments(comments);
								claimdetail.setForwaredToUser(forwardedToUser);
								claimdetail.setSsiUnitName(unitName);
							}

						}

					}

					if (claimDetails.size() == 0) {
						claimDetails.addElement(claimdetail);
					}

					String clmRefNum = null;

					for (int j = 0; j < claimDetails.size(); j++) {
						cd = (ClaimDetail) claimDetails.elementAt(j);
						clmRefNum = cd.getClaimRefNum();

						if (clmRefNum != null) {
							if (!clmRefNum.equals(claimRefNumber)) {
								if (!claimDetails.contains(claimdetail)) {
									claimDetails.addElement(claimdetail);
								}
							}
							if (clmRefNum.equals(claimRefNumber)) {
								cd = (ClaimDetail) claimDetails.remove(j);
								claimDetails.add(j, claimdetail);
							}
						}
					}
					count++;
				}
				resultset.close();
				resultset = null;
			}

			callableStmt.close();
		} catch (SQLException sqlexception) {
			Log.log(2, "CPDAO", "getClaimProcessingDetails()",
					"Error retrieving Details for Processing Claims from the database");
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(4, "CPDAO", "getClaimProcessingDetails()", "Exited!");

		return claimDetails;
	}

	public String generateClaimRefNumber(String bid) throws DatabaseException {
		Log.log(4, "CPDAO", "generateClaimRefNumber()", "Entered!");
		String claimRefNumber = null;
		CallableStatement callableStmt = null;
		Connection conn = null;

		int status = -1;
		String errorCode = null;
		try {
			conn = DBConnection.getConnection();
			callableStmt = conn.prepareCall("{? = call funcGenClmRefNum(?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			callableStmt.setString(2, bid);
			callableStmt.registerOutParameter(3, Types.VARCHAR);
			callableStmt.registerOutParameter(4, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(4);
			if (status == 1) {
				Log.log(2, "CPDAO", "generateClaimRefNumber()",
						"SP returns a 1 in generating Claim Ref Number.Error code is :" + errorCode);
				callableStmt.close();
				throw new DatabaseException(errorCode);
			}
			if (status == 0) {
				claimRefNumber = callableStmt.getString(3);
			}

			callableStmt.close();
		} catch (SQLException sqlexception) {
			Log.log(2, "CPDAO", "generateClaimRefNumber()", "Error generating Claim Reference Number");
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(4, "CPDAO", "generateClaimRefNumber()", "Exited!");

		return claimRefNumber;
	}

	public String generateCGCLAN() throws DatabaseException {
		Log.log(4, "CPDAO", "generateCGCLAN()", "Entered!");
		String cgclan = null;

		CallableStatement callableStmt = null;
		Connection conn = null;

		int status = -1;
		String errorCode = null;
		try {
			conn = DBConnection.getConnection();
			callableStmt = conn.prepareCall("{? = call funcGenCGCLAN(?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			callableStmt.registerOutParameter(2, Types.VARCHAR);
			callableStmt.registerOutParameter(3, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(3);
			if (status == 1) {
				Log.log(2, "CPDAO", "generateCGCLAN()",
						"SP returns a 1 in generating CGCLAN.Error code is :" + errorCode);
				callableStmt.close();
				throw new DatabaseException(errorCode);
			}
			if (status == 0) {
				cgclan = callableStmt.getString(2);
			}

			callableStmt.close();
		} catch (SQLException sqlexception) {
			Log.log(2, "CPDAO", "generateCGCLAN()", "Error generating CGCLAN");
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(4, "CPDAO", "generateCGCLAN()", "Exited!");

		return cgclan;
	}

	public HashMap saveClaimProcessingResults(Vector claimdetails) throws DatabaseException {
		Log.log(4, "CPDAO", "saveClaimProcessingResults()", "Entered!");
		CallableStatement callableStmt = null;
		Connection conn = null;
		HashMap details = new HashMap();

		int status = -1;
		String errorCode = null;
		ClaimDetail claimdetail = null;
		try {
			conn = DBConnection.getConnection();

			for (int i = 0; i < claimdetails.size(); i++) {
				String cgclan = null;

				claimdetail = (ClaimDetail) claimdetails.elementAt(i);

				if (claimdetail != null) {
					if (claimdetail.getWhichInstallemnt() == null) {
						continue;
					}
				} else {
					continue;
				}
				double claimApprovedAmnt = claimdetail.getApprovedClaimAmount();
				String decision = claimdetail.getDecision();
				String remarks = claimdetail.getComments();

				if (decision.equals("AP")) {
					if (claimdetail.getWhichInstallemnt().equals("F")) {
						cgclan = generateCGCLAN();

						generateSFDANforClaimSettledCases(claimdetail.getClaimRefNum(), claimdetail.getMliId(),
								claimdetail.getCreatedModifiedBy());
					} else if (claimdetail.getWhichInstallemnt().equals("S")) {
						cgclan = claimdetail.getCGCLAN();
					}
				} else if (decision.equals("TC")) {
					cgclan = null;
				} else if (decision.equals("RE")) {
					cgclan = null;
				} else if (decision.equals("FW")) {
					Mailer mailer = new Mailer();
					String subject = "Claim Application Forwarded by";
					String messageBody = "Claim Application for Member Id :" + claimdetail.getMliId()
							+ " and Claim Ref Number :" + claimdetail.getClaimRefNum();
					String forwardedToUser = claimdetail.getForwaredToUser();
					String processedBy = claimdetail.getCreatedModifiedBy();
					Administrator admin = new Administrator();
					User userinfo = null;
					try {
						userinfo = admin.getUserInfo(forwardedToUser);
					} catch (Exception ex) {
						Log.log(4, "CPDAO", "saveClaimProcessingResults", "Error fetching the details for the User");
					}
					String emailId = userinfo.getEmailId();
					ArrayList emailIds = new ArrayList();
					emailIds.add(emailId);

					Message message = new Message(emailIds, null, null, subject, messageBody);
					message.setFrom(processedBy);
					ArrayList idsToBeSent = new ArrayList();
					idsToBeSent.add(forwardedToUser);
					Message emailMsg = new Message(idsToBeSent, null, null, subject, messageBody);
					Log.log(4, "CPDAO", "saveClaimProcessingResults", "processedBy :" + processedBy);
					emailMsg.setFrom(processedBy);
					admin.sendMail(emailMsg);
					try {
						mailer.sendEmail(message);
					} catch (MailerException mailerException) {
						Log.log(4, "CPDAO", "saveClaimProcessingResults", "Email could not be sent.");
					}
				}
				String claimRefNumber = claimdetail.getClaimRefNum();

				String forwardedToUser = claimdetail.getForwaredToUser();

				String createdModifiedBy = claimdetail.getCreatedModifiedBy();

				callableStmt = conn.prepareCall("{? = call funcApproveClaims(?,?,?,?,?,?,?,?)}");
				callableStmt.registerOutParameter(1, Types.INTEGER);
				callableStmt.setString(2, claimRefNumber);
				if (cgclan == null) {
					callableStmt.setNull(3, Types.VARCHAR);
				} else {
					callableStmt.setString(3, cgclan);
				}
				callableStmt.setDouble(4, claimApprovedAmnt);
				callableStmt.setString(5, decision);
				callableStmt.setString(6, remarks);

				if ((forwardedToUser == null) || ((forwardedToUser != null) && (forwardedToUser.equals("")))) {
					callableStmt.setNull(7, Types.VARCHAR);
				} else {
					callableStmt.setString(7, forwardedToUser);
				}
				callableStmt.setString(8, createdModifiedBy);
				callableStmt.registerOutParameter(9, Types.VARCHAR);

				callableStmt.execute();
				status = callableStmt.getInt(1);
				errorCode = callableStmt.getString(9);
				if (status == 1) {
					Log.log(2, "CPDAO", "saveClaimProcessingResults()",
							"SP returns a 1 in saving Claim Processing Results.Error code is :" + errorCode);
					callableStmt.close();
					try {
						conn.rollback();
					} catch (SQLException sqlex) {
						Log.log(2, "CPDAO", "saveClaimProcessingResults", sqlex.getMessage());
					}
					throw new DatabaseException(errorCode);
				}
				details.put(claimRefNumber, cgclan);
			}
			conn.commit();

			callableStmt.close();
		} catch (SQLException sqlexception) {
			try {
				conn.rollback();
			} catch (SQLException sqlex) {
				Log.log(2, "CPDAO", "saveClaimProcessingResults", sqlex.getMessage());
			}
			Log.log(2, "CPDAO", "saveClaimProcessingResults()",
					"Error saving Claims Processing Results into the database.");
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(4, "CPDAO", "saveClaimProcessingResults()", "Exited!");

		return details;
	}

	public Vector getSettlementDetails(String bankId, String zoneId, String branchId, String flag, boolean anotherFlag)
			throws DatabaseException {
		Log.log(4, "CPDAO", "getSettlementDetails()", "Entered!");
		String cgclan = null;
		double claimApprovedAmount = 0.0D;
		double outstandingAmntAsOnNPA = 0.0D;
		double recoveredAmnt = 0.0D;

		double firstTierSettlementAmnt = 0.0D;
		java.util.Date firstSettlmntDt = null;

		String borrowerId = null;
		String lastBorrowerId = null;
		double totalRecoveredAmnt = 0.0D;
		java.util.Date clmApprvdDate = null;

		CallableStatement callableStmt = null;
		Connection conn = null;
		int status = -1;
		String errorCode = null;
		ResultSet resultset = null;
		SettlementDetail settlementdetail = null;
		Vector settlementdetails = new Vector();
		try {
			conn = DBConnection.getConnection();
			callableStmt = conn
					.prepareCall("{? = call packGetClaimSettlementDtl.funcGetClaimSettlementDtl(?,?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			callableStmt.setString(2, bankId);
			callableStmt.setString(3, zoneId);
			callableStmt.setString(4, branchId);
			callableStmt.setString(5, flag);
			callableStmt.registerOutParameter(6, Constants.CURSOR);
			callableStmt.registerOutParameter(7, Constants.CURSOR);
			callableStmt.registerOutParameter(8, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(8);

			if (status == 1) {
				Log.log(2, "CPDAO", "getSettlementDetails()",
						"SP returns a 1 in getting Settlement Details.Error code is :" + errorCode);
				callableStmt.close();
				throw new DatabaseException(errorCode);
			}
			if (status == 0) {
				if (flag.equals("F")) {
					resultset = (ResultSet) callableStmt.getObject(6);
					while (resultset.next()) {
						borrowerId = resultset.getString(1);
						settlementdetail = new SettlementDetail();
						cgclan = resultset.getString(2);
						claimApprovedAmount = resultset.getDouble(3);
						clmApprvdDate = resultset.getDate(4);
						outstandingAmntAsOnNPA = resultset.getDouble(5);

						recoveredAmnt = resultset.getDouble(6);

						settlementdetail.setCgbid(borrowerId);
						settlementdetail.setCgclan(cgclan);
						settlementdetail.setApprovedClaimAmt(claimApprovedAmount);
						settlementdetail.setClmApprvdDate(clmApprvdDate);
						settlementdetail.setOsAmtAsonNPA(outstandingAmntAsOnNPA);
						settlementdetail.setRecoveryAmt(recoveredAmnt);
						settlementdetail.setTypeOfSettlement("F");

						settlementdetails.add(settlementdetail);
					}

					resultset.close();
					resultset = null;

					resultset = (ResultSet) callableStmt.getObject(7);
					while (resultset.next()) {
						borrowerId = resultset.getString(1);
						settlementdetail = new SettlementDetail();
						cgclan = resultset.getString(2);
						claimApprovedAmount = resultset.getDouble(3);
						clmApprvdDate = resultset.getDate(4);
						outstandingAmntAsOnNPA = resultset.getDouble(5);
						recoveredAmnt = resultset.getDouble(6);

						settlementdetail.setCgbid(borrowerId);
						settlementdetail.setCgclan(cgclan);
						settlementdetail.setApprovedClaimAmt(claimApprovedAmount);
						settlementdetail.setClmApprvdDate(clmApprvdDate);
						settlementdetail.setOsAmtAsonNPA(outstandingAmntAsOnNPA);
						settlementdetail.setRecoveryAmt(recoveredAmnt);
						settlementdetail.setTypeOfSettlement("F");

						if (settlementdetails.size() == 0) {
							settlementdetails.addElement(settlementdetail);
						} else {
							boolean toAdd = false;
							for (int i = 0; i < settlementdetails.size(); i++) {
								SettlementDetail sd = (SettlementDetail) settlementdetails.elementAt(i);
								if (sd != null) {
									String bd = sd.getCgbid();
									if (bd.equals(borrowerId)) {
										sd = (SettlementDetail) settlementdetails.remove(i);

										toAdd = true;
									}
								}
							}
							if (toAdd) {
								settlementdetails.addElement(settlementdetail);
								toAdd = false;
							} else {
								settlementdetails.addElement(settlementdetail);
							}
						}
					}
					resultset.close();
					resultset = null;
				} else if (flag.equals("S")) {
					resultset = (ResultSet) callableStmt.getObject(6);
					while (resultset.next()) {
						borrowerId = resultset.getString(1);
						cgclan = resultset.getString(2);
						claimApprovedAmount = resultset.getDouble(3);
						clmApprvdDate = resultset.getDate(4);
						outstandingAmntAsOnNPA = resultset.getDouble(5);
						recoveredAmnt = resultset.getDouble(6);

						firstTierSettlementAmnt = resultset.getDouble(7);
						firstSettlmntDt = resultset.getDate(8);
						lastBorrowerId = borrowerId;

						settlementdetail = new SettlementDetail();
						settlementdetail.setCgbid(borrowerId);
						settlementdetail.setCgclan(cgclan);
						settlementdetail.setApprovedClaimAmt(claimApprovedAmount);
						settlementdetail.setClmApprvdDate(clmApprvdDate);
						settlementdetail.setOsAmtAsonNPA(outstandingAmntAsOnNPA);
						settlementdetail.setRecoveryAmt(recoveredAmnt);
						settlementdetail.setTierOneSettlement(firstTierSettlementAmnt);
						settlementdetail.setTierOneSettlementDt(firstSettlmntDt);
						settlementdetail.setTypeOfSettlement("S");

						settlementdetails.add(settlementdetail);
					}

					resultset.close();
					resultset = null;

					resultset = (ResultSet) callableStmt.getObject(7);
					while (resultset.next()) {
						borrowerId = resultset.getString(1);
						cgclan = resultset.getString(2);
						claimApprovedAmount = resultset.getDouble(3);
						clmApprvdDate = resultset.getDate(4);
						outstandingAmntAsOnNPA = resultset.getDouble(5);
						recoveredAmnt = resultset.getDouble(6);
						if (borrowerId.equals(lastBorrowerId)) {
							totalRecoveredAmnt += recoveredAmnt;
						}
						firstTierSettlementAmnt = resultset.getDouble(7);
						firstSettlmntDt = resultset.getDate(8);
						lastBorrowerId = borrowerId;

						settlementdetail = new SettlementDetail();
						settlementdetail.setCgbid(borrowerId);
						settlementdetail.setCgclan(cgclan);
						settlementdetail.setApprovedClaimAmt(claimApprovedAmount);
						settlementdetail.setClmApprvdDate(clmApprvdDate);
						settlementdetail.setOsAmtAsonNPA(outstandingAmntAsOnNPA);
						settlementdetail.setRecoveryAmt(recoveredAmnt);
						settlementdetail.setTierOneSettlement(firstTierSettlementAmnt);
						settlementdetail.setTierOneSettlementDt(firstSettlmntDt);
						settlementdetail.setTypeOfSettlement("S");

						if (settlementdetails.size() == 0) {
							settlementdetails.addElement(settlementdetail);
						} else {
							boolean toBeAdd = false;
							for (int i = 0; i < settlementdetails.size(); i++) {
								SettlementDetail sd = (SettlementDetail) settlementdetails.elementAt(i);
								if (sd != null) {
									String bd = sd.getCgbid();
									if (bd.equals(borrowerId)) {
										sd = (SettlementDetail) settlementdetails.remove(i);

										toBeAdd = true;
									}
								}
							}

							if (toBeAdd) {
								settlementdetails.addElement(settlementdetail);
								toBeAdd = false;
							} else {
								settlementdetails.addElement(settlementdetail);
							}
						}

					}

					resultset.close();
					resultset = null;
				}
			}

			callableStmt.close();
		} catch (SQLException sqlexception) {
			Log.log(2, "CPDAO", "getSettlementDetails()", "Error retrieving Settlement Details from the database.");

			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(4, "CPDAO", "getSettlementDetails()", "Exited!");

		return settlementdetails;
	}

	public void insertClaimProcessDetails(String clmRefNum, String userRemarks, double tcServiceFee,
			double wcServiceFee, double tcClaimEligibleAmt, double wcClaimEligibleAmt, double tcFirstInstallment,
			double wcFirstInstallment, double totalTCOSAmountAsOnNPA, double totalWCOSAmountAsOnNPA, double tcrecovery,
			double wcrecovery, java.util.Date dateofReceipt) throws DatabaseException {
		String methodName = "insertClaimProcessDetails";
		Connection connection = null;
		CallableStatement insertClaimProcessDtls = null;
		Log.log(4, "CPDAO", methodName, "Entered");
		int updateStatus = 0;
		boolean newConn = false;
		try {
			if (connection == null) {
				connection = DBConnection.getConnection();
				newConn = true;
			}
			insertClaimProcessDtls = connection
					.prepareCall("{?= call FuncInsClaimSetDetail(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			insertClaimProcessDtls.registerOutParameter(1, Types.INTEGER);
			insertClaimProcessDtls.setString(2, clmRefNum);

			insertClaimProcessDtls.setString(3, userRemarks);

			insertClaimProcessDtls.setDouble(4, tcServiceFee);

			insertClaimProcessDtls.setDouble(5, wcServiceFee);

			insertClaimProcessDtls.setDouble(6, tcClaimEligibleAmt);

			insertClaimProcessDtls.setDouble(7, wcClaimEligibleAmt);

			insertClaimProcessDtls.setDouble(8, tcFirstInstallment);

			insertClaimProcessDtls.setDouble(9, wcFirstInstallment);

			insertClaimProcessDtls.setDouble(10, totalTCOSAmountAsOnNPA);
			insertClaimProcessDtls.setDouble(11, tcrecovery);
			insertClaimProcessDtls.setDouble(12, totalWCOSAmountAsOnNPA);
			insertClaimProcessDtls.setDouble(13, wcrecovery);
			insertClaimProcessDtls.setDate(14, new java.sql.Date(dateofReceipt.getTime()));

			insertClaimProcessDtls.registerOutParameter(15, Types.VARCHAR);
			insertClaimProcessDtls.executeQuery();

			updateStatus = insertClaimProcessDtls.getInt(1);
			String error = insertClaimProcessDtls.getString(15);

			Log.log(5, "CPDAO", methodName, "updateStatus,error " + updateStatus + "," + error);

			if (updateStatus == 1) {
				insertClaimProcessDtls.close();
				insertClaimProcessDtls = null;

				Log.log(2, "CPDAO", methodName, error);

				throw new DatabaseException(error);
			}
			insertClaimProcessDtls.close();
			insertClaimProcessDtls = null;
		} catch (SQLException exception) {
			Log.log(2, "CPDAO", methodName, exception.getMessage());
			Log.logException(exception);
			throw new DatabaseException(exception.getMessage());
		} finally {
			if (newConn) {
				DBConnection.freeConnection(connection);
			}
		}

		Log.log(4, "CPDAO", methodName, "Exited");
	}

	public void generateSFDANforClaimSettledCases(String clmRefNum, String mliId, String userId)
			throws DatabaseException {
		String methodName = "generateSFDANforClaimSettledCases";
		Connection connection = null;
		CallableStatement generateSFDANforClaimSettledCase = null;
		Log.log(4, "CPDAO", methodName, "Entered");
		int updateStatus = 0;
		boolean newConn = false;
		try {
			if (connection == null) {
				connection = DBConnection.getConnection();
				newConn = true;
			}

			generateSFDANforClaimSettledCase = connection.prepareCall("{?= call funcGenSFForClaimApp(?,?,?)}");
			generateSFDANforClaimSettledCase.registerOutParameter(1, Types.INTEGER);
			generateSFDANforClaimSettledCase.setString(2, clmRefNum);

			generateSFDANforClaimSettledCase.setString(3, userId);
			generateSFDANforClaimSettledCase.registerOutParameter(4, Types.VARCHAR);
			generateSFDANforClaimSettledCase.executeQuery();

			updateStatus = generateSFDANforClaimSettledCase.getInt(1);

			String error = generateSFDANforClaimSettledCase.getString(4);

			Log.log(5, "CPDAO", methodName, "updateStatus,error " + updateStatus + "," + error);

			if (updateStatus == 1) {
				// System.out.println("Error:" + error);
				generateSFDANforClaimSettledCase.close();
				generateSFDANforClaimSettledCase = null;

				Log.log(2, "CPDAO", methodName, error);

				throw new DatabaseException(error);
			}
			generateSFDANforClaimSettledCase.close();
			generateSFDANforClaimSettledCase = null;
		} catch (SQLException exception) {
			Log.log(2, "CPDAO", methodName, exception.getMessage());
			Log.logException(exception);
			throw new DatabaseException(exception.getMessage());
		} finally {
			if (newConn) {
				DBConnection.freeConnection(connection);
			}
		}

		Log.log(4, "CPDAO", methodName, "Exited");
	}

	public ClaimDetail getDetailsForClaimRefNumber(String claimRefNumber) throws DatabaseException {
		Log.log(4, "CPDAO", "getDetailsForClaimRefNumber()", "Entered!");

		String mliname = null;
		String memberId = null;
		String ssiunitname = null;
		String borrowerId = null;
		java.util.Date dateAccntClassifiedAsNPA = null;
		java.util.Date dateOfReportingOfNPAtoCGTSI = null;
		String reasonsForAccntNPA = null;
		double osAmountAsOnNPA = 0.0D;
		java.util.Date dateOfIssueOfRecallNotice = null;
		double totalTCOSAmount = 0.0D;
		double totalWCOSAmount = 0.0D;
		double totalOSAmntAsOnNPA = 0.0D;

		java.util.Date appApprvdDate = null;
		java.util.Date guaranteeStartDate = null;
		double tcSanctionedAmnt = 0.0D;
		double wcFBSanctionedAmnt = 0.0D;
		double wcNFBSanctionedAmnt = 0.0D;
		double applicationApprvdAmnt = 0.0D;
		double enhancedApprvdAmount = 0.0D;
		String applicationType = null;
		double claimAppliedAmount = 0.0D;
		double totalClaimAppliedAmount = 0.0D;

		CallableStatement callableStmt = null;
		Connection conn = null;
		ResultSet resultset = null;
		ClaimDetail claimdetail = new ClaimDetail();
		int status = -1;
		String errorCode = null;
		String legalForum = null;
		String legalForumName = null;
		String legalSuitNumber = null;
		String legalLocation = null;
		java.util.Date legalFilingDate = null;
		try {
			conn = DBConnection.getConnection();
			callableStmt = conn.prepareCall("{? = call packGetClaimDetails.funcGetClaimDtl(?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			callableStmt.setString(2, claimRefNumber);
			callableStmt.registerOutParameter(3, Constants.CURSOR);
			callableStmt.registerOutParameter(4, Constants.CURSOR);
			callableStmt.registerOutParameter(5, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(5);

			if (status == 1) {
				Log.log(2, "CPDAO", "getDetailsForClaimRefNumber()",
						"SP returns a 1 in getting details for a Claim Ref Number.Error code is :" + errorCode);
				callableStmt.close();
				throw new DatabaseException(errorCode);
			}
			if (status == 0) {
				Vector cgpandtls = new Vector();
				HashMap details = null;
				resultset = (ResultSet) callableStmt.getObject(3);

				boolean detailsread = false;

				while (resultset.next()) {
					mliname = resultset.getString(1);
					memberId = resultset.getString(2);
					borrowerId = resultset.getString(3);
					dateOfIssueOfRecallNotice = resultset.getDate(4);
					ssiunitname = resultset.getString(5);
					dateAccntClassifiedAsNPA = resultset.getDate(6);
					dateOfReportingOfNPAtoCGTSI = resultset.getDate(7);
					reasonsForAccntNPA = resultset.getString(8);
					osAmountAsOnNPA = resultset.getDouble(9);
					String cgpan = resultset.getString(10);
					ReportDAO rpDao = new ReportDAO();
					RpDAO rcDao = new RpDAO();
					double serviceFee = rcDao.calculateServiceFeeforCGPAN(cgpan);
					ArrayList danDetails = rpDao.getCgpanHistoryReportDetails(cgpan);
					claimdetail.setDanSummaryReportDetails(cgpan, danDetails);
					applicationApprvdAmnt = resultset.getDouble(11);

					tcSanctionedAmnt = resultset.getDouble(12);
					wcFBSanctionedAmnt = resultset.getDouble(13);
					wcNFBSanctionedAmnt = resultset.getDouble(14);
					applicationType = resultset.getString(15);
					guaranteeStartDate = resultset.getDate(16);
					claimAppliedAmount = resultset.getDouble(17);
					legalSuitNumber = resultset.getString(18);
					legalForum = resultset.getString(19);
					legalForumName = resultset.getString(20);
					legalLocation = resultset.getString(21);
					legalFilingDate = resultset.getDate(22);
					totalClaimAppliedAmount += claimAppliedAmount;

					details = new HashMap();
					details.put("CGPAN", cgpan);
					details.put("ApprovedAmount", new Double(applicationApprvdAmnt));

					details.put("TCSanctionedAmnt", new Double(tcSanctionedAmnt));
					details.put("WCFundBasedSanctionedAmnt", new Double(wcFBSanctionedAmnt));
					details.put("WCNFBSanctionedAmnt", new Double(wcNFBSanctionedAmnt));
					details.put("LoanType", applicationType);
					details.put("GUARANTEESTARTDT", guaranteeStartDate);
					details.put("TOTALSERVICEFEE", new Double(serviceFee));
					if (!cgpandtls.contains(details)) {
						cgpandtls.addElement(details);
					}

					if (!detailsread) {
						claimdetail.setMliName(mliname);
						claimdetail.setMliId(memberId);
						claimdetail.setBorrowerId(borrowerId);
						claimdetail.setDateOfIssueOfRecallNotice(dateOfIssueOfRecallNotice);
						claimdetail.setSsiUnitName(ssiunitname);
						claimdetail.setNpaDate(dateAccntClassifiedAsNPA);
						claimdetail.setDtOfNPAReportedToCGTSI(dateOfReportingOfNPAtoCGTSI);
						claimdetail.setReasonForTurningNPA(reasonsForAccntNPA);
						claimdetail.setOutstandingAmntAsOnNPADate(osAmountAsOnNPA);
						claimdetail.setLegalSuitNumber(legalSuitNumber);
						claimdetail.setLegalForum(legalForum);
						claimdetail.setLegalForumName(legalForumName);
						claimdetail.setLegalLocation(legalLocation);
						claimdetail.setLegalFilingDate(legalFilingDate);

						detailsread = true;
					}
				}
				claimdetail.setAppliedClaimAmt(totalClaimAppliedAmount);
				claimdetail.setCgpanDetails(cgpandtls);

				resultset.close();
				resultset = null;

				resultset = (ResultSet) callableStmt.getObject(4);

				detailsread = false;

				while (resultset.next()) {
					mliname = resultset.getString(1);

					memberId = resultset.getString(2);

					borrowerId = resultset.getString(3);

					dateOfIssueOfRecallNotice = resultset.getDate(4);

					ssiunitname = resultset.getString(5);
					dateAccntClassifiedAsNPA = resultset.getDate(6);
					dateOfReportingOfNPAtoCGTSI = resultset.getDate(7);
					reasonsForAccntNPA = resultset.getString(8);
					osAmountAsOnNPA = resultset.getDouble(9);

					String cgpan = resultset.getString(10);
					ReportDAO rpDao = new ReportDAO();
					RpDAO rcDao = new RpDAO();
					double serviceFee = rcDao.calculateServiceFeeforCGPAN(cgpan);

					ArrayList danDetails = rpDao.getCgpanHistoryReportDetails(cgpan);

					claimdetail.setDanSummaryReportDetails(cgpan, danDetails);

					applicationApprvdAmnt = resultset.getDouble(11);

					tcSanctionedAmnt = resultset.getDouble(12);
					wcFBSanctionedAmnt = resultset.getDouble(13);
					wcNFBSanctionedAmnt = resultset.getDouble(14);
					applicationType = resultset.getString(15);
					guaranteeStartDate = resultset.getDate(16);
					claimAppliedAmount = resultset.getDouble(17);
					legalSuitNumber = resultset.getString(18);
					legalForum = resultset.getString(19);
					legalForumName = resultset.getString(20);
					legalLocation = resultset.getString(21);
					legalFilingDate = resultset.getDate(22);
					totalClaimAppliedAmount += claimAppliedAmount;

					details = new HashMap();
					details.put("CGPAN", cgpan);
					details.put("ApprovedAmount", new Double(applicationApprvdAmnt));

					details.put("TCSanctionedAmnt", new Double(tcSanctionedAmnt));
					details.put("WCFundBasedSanctionedAmnt", new Double(wcFBSanctionedAmnt));
					details.put("WCNFBSanctionedAmnt", new Double(wcNFBSanctionedAmnt));
					details.put("LoanType", applicationType);
					details.put("GUARANTEESTARTDT", guaranteeStartDate);
					details.put("TOTALSERVICEFEE", new Double(serviceFee));
					if (!cgpandtls.contains(details)) {
						cgpandtls.addElement(details);
					}

					if (!detailsread) {
						claimdetail.setMliName(mliname);
						claimdetail.setMliId(memberId);
						claimdetail.setBorrowerId(borrowerId);
						claimdetail.setDateOfIssueOfRecallNotice(dateOfIssueOfRecallNotice);
						claimdetail.setSsiUnitName(ssiunitname);
						if (dateAccntClassifiedAsNPA != null) {
							claimdetail.setNpaDate(dateAccntClassifiedAsNPA);
							claimdetail.setDtOfNPAReportedToCGTSI(dateOfReportingOfNPAtoCGTSI);
							claimdetail.setReasonForTurningNPA(reasonsForAccntNPA);
							claimdetail.setOutstandingAmntAsOnNPADate(osAmountAsOnNPA);
							claimdetail.setLegalSuitNumber(legalSuitNumber);
							claimdetail.setLegalForum(legalForum);
							claimdetail.setLegalForumName(legalForumName);
							claimdetail.setLegalLocation(legalLocation);
							claimdetail.setLegalFilingDate(legalFilingDate);
						}

						detailsread = true;
					}
				}
				claimdetail.setAppliedClaimAmt(totalClaimAppliedAmount);
				claimdetail.setCgpanDetails(cgpandtls);

				resultset.close();
				resultset = null;

				callableStmt.close();
			}

			Vector cgpandtls = claimdetail.getCgpanDetails();
			Vector updatedCgpanDetails = new Vector();

			for (int i = 0; i < cgpandtls.size(); i++) {
				HashMap hashmap = (HashMap) cgpandtls.elementAt(i);
				if (hashmap != null) {
					String cgpan = (String) hashmap.get("CGPAN");
					double disbursementAmnt = 0.0D;
					java.util.Date disbursementDate = null;
					String finalDisbursementFlag = null;
					double totalDisbursement = 0.0D;
					java.util.Date lastDisbursementDt = null;

					if (cgpan != null) {
						callableStmt = conn
								.prepareCall("{? = call packGetPIDBRDtlsCGPAN.funcDBRDetailsForCGPAN(?,?,?)}");
						callableStmt.registerOutParameter(1, Types.INTEGER);
						callableStmt.setString(2, cgpan);
						callableStmt.registerOutParameter(3, Constants.CURSOR);
						callableStmt.registerOutParameter(4, Types.VARCHAR);

						callableStmt.execute();
						status = callableStmt.getInt(1);
						errorCode = callableStmt.getString(4);

						if (status == 1) {
							Log.log(2, "CPDAO", "getDetailsForClaimRefNumber()",
									"SP returns a 1 in getting Disbursement details for a CGPAN.Error code is :"
											+ errorCode);
							callableStmt.close();
							throw new DatabaseException(errorCode);
						}
						if (status == 0) {
							resultset = (ResultSet) callableStmt.getObject(3);
							int counter = 0;

							while (resultset.next()) {
								disbursementAmnt = resultset.getDouble(2);
								disbursementDate = resultset.getDate(3);
								finalDisbursementFlag = resultset.getString(4);

								totalDisbursement += disbursementAmnt;

								if (counter == 0) {
									if (disbursementDate != null) {
										lastDisbursementDt = disbursementDate;
									} else {
										break; // label1813;
									}
								} else if (counter > 0) {
									if (disbursementDate != null) {
										if (disbursementDate.compareTo(lastDisbursementDt) > 0) {
											lastDisbursementDt = disbursementDate;
										}
									} else {
										break; // label1813;
									}
								}
								counter++;
							}
							resultset.close();
							resultset = null;

							hashmap.put("TotalDisbursementAmnt", new Double(totalDisbursement));
							hashmap.put("LASTDSBRSMNTDT", lastDisbursementDt);
							if (!updatedCgpanDetails.contains(hashmap)) {
								updatedCgpanDetails.addElement(hashmap);
							}
						}
					} else
						;
				}

			}

			claimdetail.setCgpanDetails(updatedCgpanDetails);

			cgpandtls = null;
			updatedCgpanDetails = null;

			cgpandtls = claimdetail.getCgpanDetails();
			updatedCgpanDetails = new Vector();

			String previousCGPAN = null;
			java.util.Date lastDtOfServiceFeePaid = null;
			double lastServiceFeePaid = 0.0D;
			Vector sfeedtls = new Vector();
			HashMap sfeedtl = null;

			callableStmt = conn.prepareCall("{? = call packGetSFeeDetail.funcGetServiceFeeDetail(?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			callableStmt.setString(2, claimRefNumber);
			callableStmt.registerOutParameter(3, Constants.CURSOR);
			callableStmt.registerOutParameter(4, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(4);

			if (status == 1) {
				Log.log(2, "CPDAO", "getDetailsForClaimRefNumber()",
						"SP returns a 1 in getting Service Fee details for a CGPAN.Error code is :" + errorCode);
				callableStmt.close();
				throw new DatabaseException(errorCode);
			}
			if (status == 0) {
				resultset = (ResultSet) callableStmt.getObject(3);
				int counter = 0;

				while (resultset.next()) {
					String cgpan = resultset.getString(1);
					double amountRaised = resultset.getDouble(2);
					java.util.Date dtOfServiceFeePaid = resultset.getDate(3);

					if (counter == 0) {
						if (cgpan != null) {
							previousCGPAN = cgpan;
							lastDtOfServiceFeePaid = dtOfServiceFeePaid;
							lastServiceFeePaid = amountRaised;
						} else {
							break; // label2308;
						}
					} else if (counter > 0) {
						if (cgpan != null) {
							if (cgpan.equals(previousCGPAN)) {
								if (dtOfServiceFeePaid != null) {
									if (lastDtOfServiceFeePaid != null) {
										if (dtOfServiceFeePaid.compareTo(lastDtOfServiceFeePaid) > 0) {
											lastDtOfServiceFeePaid = dtOfServiceFeePaid;
											lastServiceFeePaid = amountRaised;
										}
									}
								}
							} else if (!cgpan.equals(previousCGPAN)) {
								sfeedtl = new HashMap();
								sfeedtl.put("CGPAN", previousCGPAN);
								sfeedtl.put("ServiceFee", new Double(lastServiceFeePaid));
								sfeedtl.put("ServiceFeeDate", lastDtOfServiceFeePaid);
								if (!sfeedtls.contains(sfeedtl)) {
									sfeedtls.addElement(sfeedtl);
								}

								previousCGPAN = cgpan;
								lastDtOfServiceFeePaid = dtOfServiceFeePaid;
								lastServiceFeePaid = amountRaised;
							}

						} else {
							break; // label2308;
						}
					}
					counter++;
				}
				resultset.close();
				resultset = null;

				if (counter > 0) {
					sfeedtl = new HashMap();
					sfeedtl.put("CGPAN", previousCGPAN);
					sfeedtl.put("ServiceFee", new Double(lastServiceFeePaid));
					sfeedtl.put("ServiceFeeDate", lastDtOfServiceFeePaid);
					if (!sfeedtls.contains(sfeedtl)) {
						sfeedtls.addElement(sfeedtl);
					}

					for (int i = 0; i < cgpandtls.size(); i++) {
						HashMap tempi = (HashMap) cgpandtls.elementAt(i);
						if (tempi != null) {
							String cgpani = (String) tempi.get("CGPAN");
							for (int j = 0; j < sfeedtls.size(); j++) {
								HashMap tempj = (HashMap) sfeedtls.elementAt(j);
								if (tempj != null) {
									String cgpanj = (String) tempj.get("CGPAN");

									if (cgpanj.equals(cgpani)) {
										double amountRaised = ((Double) tempj.get("ServiceFee")).doubleValue();
										lastDtOfServiceFeePaid = (java.util.Date) tempj.get("ServiceFeeDate");
										tempi.put("ServiceFee", new Double(amountRaised));
										tempi.put("ServiceFeeDate", lastDtOfServiceFeePaid);
									}
								}
							}
							if (!updatedCgpanDetails.contains(tempi)) {
								updatedCgpanDetails.addElement(tempi);
							}
						}
					}
					claimdetail.setCgpanDetails(updatedCgpanDetails);
				}

			}

			double tcOSAmtForCGPAN = 0.0D;

			String cgpn = null;
			double principalAmnt = 0.0D;
			double interestOtherCharges = 0.0D;
			double osAsOnNPADate = 0.0D;
			double osAsInCivilSuit = 0.0D;
			double osAsInFirstClmLodgement = 0.0D;
			callableStmt = conn.prepareCall("{? = call packGetTCDetails.funcGetTermCreditDtls(?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			callableStmt.setString(2, claimRefNumber);
			callableStmt.registerOutParameter(3, Constants.CURSOR);
			callableStmt.registerOutParameter(4, Constants.CURSOR);
			callableStmt.registerOutParameter(5, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(5);

			if (status == 1) {
				Log.log(2, "CPDAO", "getDetailsForClaimRefNumber()",
						"SP returns a 1 in getting Term Credit details for a Claim Ref Number.Error code is :"
								+ errorCode);
				callableStmt.close();
				throw new DatabaseException(errorCode);
			}
			if (status == 0) {
				resultset = (ResultSet) callableStmt.getObject(3);

				while (resultset.next()) {
					String cgpan = resultset.getString(1);
					tcOSAmtForCGPAN = resultset.getDouble(2);
					totalTCOSAmount += tcOSAmtForCGPAN;
				}
				resultset.close();
				resultset = null;

				java.util.Date dsbrsDate = null;
				resultset = (ResultSet) callableStmt.getObject(4);
				Vector tcOSDetails = new Vector();
				HashMap mp = null;
				while (resultset.next()) {
					mp = new HashMap();
					cgpn = resultset.getString(1);
					dsbrsDate = resultset.getDate(2);
					principalAmnt = resultset.getDouble(3);
					interestOtherCharges = resultset.getDouble(4);
					osAsOnNPADate = resultset.getDouble(5);
					osAsInCivilSuit = resultset.getDouble(6);
					osAsInFirstClmLodgement = resultset.getDouble(7);

					mp.put("CGPAN", cgpn);
					mp.put("LASTDSBRSMNTDT", dsbrsDate);
					mp.put("TCPRINCIPAL", new Double(principalAmnt));
					mp.put("TCINTEREST", new Double(interestOtherCharges));
					mp.put("OS as on NPA", new Double(osAsOnNPADate));
					mp.put("OS as in Civil Suit", new Double(osAsInCivilSuit));
					mp.put("OS as in Clm Logdmnt", new Double(osAsInFirstClmLodgement));

					if (!tcOSDetails.contains(mp)) {
						tcOSDetails.addElement(mp);
					}
				}
				resultset.close();
				resultset = null;

				claimdetail.setTcDetails(tcOSDetails);
			}
			claimdetail.setTotalTCOSAmountAsOnNPA(totalTCOSAmount);

			cgpandtls = claimdetail.getCgpanDetails();
			Vector wcDetails = new Vector();
			Vector wcOSDetails = new Vector();
			updatedCgpanDetails = new Vector();
			double wcOSAmtForCGPAN = 0.0D;
			callableStmt = conn.prepareCall("{? = call packGetWCDetails.funcGetWorkingCapitalDtl(?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			callableStmt.setString(2, claimRefNumber);
			callableStmt.registerOutParameter(3, Constants.CURSOR);
			callableStmt.registerOutParameter(4, Constants.CURSOR);
			callableStmt.registerOutParameter(5, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(5);

			if (status == 1) {
				Log.log(2, "CPDAO", "getDetailsForClaimRefNumber()",
						"SP returns a 1 in getting Working Capital details for a Claim Ref Number.Error code is :"
								+ errorCode);
				callableStmt.close();
				throw new DatabaseException(errorCode);
			}
			if (status == 0) {
				resultset = (ResultSet) callableStmt.getObject(3);

				while (resultset.next()) {
					String cgpan = resultset.getString(1);
					java.util.Date dateOfReleaseOfWC = resultset.getDate(2);
					wcOSAmtForCGPAN = resultset.getDouble(3);
					totalWCOSAmount += wcOSAmtForCGPAN;

					HashMap wcHashMap = new HashMap();
					wcHashMap.put("CGPAN", cgpan);
					wcHashMap.put("DTOFRELEASEOFWC", dateOfReleaseOfWC);

					if (!wcDetails.contains(wcHashMap)) {
						wcDetails.addElement(wcHashMap);
					}
				}
				resultset.close();
				resultset = null;

				resultset = (ResultSet) callableStmt.getObject(4);
				HashMap mp = null;
				while (resultset.next()) {
					mp = new HashMap();
					cgpn = resultset.getString(1);
					osAsOnNPADate = resultset.getDouble(2);
					osAsInCivilSuit = resultset.getDouble(3);
					osAsInFirstClmLodgement = resultset.getDouble(4);

					mp.put("CGPAN", cgpn);
					mp.put("OS as on NPA", new Double(osAsOnNPADate));
					mp.put("OS as in Civil Suit", new Double(osAsInCivilSuit));
					mp.put("OS as in Clm Logdmnt", new Double(osAsInFirstClmLodgement));

					if (!wcOSDetails.contains(mp)) {
						wcOSDetails.addElement(mp);
					}
				}
				resultset.close();
				resultset = null;
				claimdetail.setWcDetails(wcOSDetails);

				for (int i = 0; i < cgpandtls.size(); i++) {
					HashMap cgpandtlsHashMap = (HashMap) cgpandtls.elementAt(i);
					if (cgpandtlsHashMap != null) {
						String cgpanCgpandtls = (String) cgpandtlsHashMap.get("CGPAN");
						for (int j = 0; j < wcDetails.size(); j++) {
							HashMap wcDetailsHashMap = (HashMap) wcDetails.elementAt(j);
							if (wcDetailsHashMap != null) {
								String cgpanWcDetails = (String) wcDetailsHashMap.get("CGPAN");
								java.util.Date wcReleaseDate = (java.util.Date) wcDetailsHashMap.get("DTOFRELEASEOFWC");
								if (cgpanWcDetails.equals(cgpanCgpandtls)) {
									cgpandtlsHashMap.put("DTOFRELEASEOFWC", wcReleaseDate);
								}
							}
						}
						if (!updatedCgpanDetails.contains(cgpandtlsHashMap)) {
							updatedCgpanDetails.addElement(cgpandtlsHashMap);
						}
					}
				}
				claimdetail.setCgpanDetails(updatedCgpanDetails);
			}
			claimdetail.setTotalWCOSAmountAsOnNPA(totalWCOSAmount);
		} catch (Exception sqlexception) {
			Log.log(2, "CPDAO", "getDetailsForClaimRefNumber()",
					"Error retrieving Details for Claim Ref Number from the database.");
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
			Log.log(4, "CPDAO", "getDetailsForClaimRefNumber()", "Exited!");
		}

		return claimdetail;
	}

	public Vector getAllClaimsFiled() throws DatabaseException {
		Log.log(4, "CPDAO", "getAllClaimsFiled()", "Entered!");
		CallableStatement callableStmt = null;
		Connection conn = null;
		ResultSet resultset = null;
		Vector allclaims = new Vector();

		int status = -1;
		String errorCode = null;

		String bid = null;
		String claimrefnumber = null;
		String memberId = null;
		HashMap claims = null;
		try {
			conn = DBConnection.getConnection();
			callableStmt = conn.prepareCall("{? = call packGetAllClaims.funcGetAllClaims(?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			callableStmt.registerOutParameter(2, Constants.CURSOR);
			callableStmt.registerOutParameter(3, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(3);

			if (status == 1) {
				Log.log(2, "CPDAO", "getAllClaimsFiled()",
						"SP returns a 1 in getting all claims filed.Error code is :" + errorCode);
				callableStmt.close();
				throw new DatabaseException(errorCode);
			}
			if (status == 0) {
				resultset = (ResultSet) callableStmt.getObject(2);
				while (resultset.next()) {
					claimrefnumber = resultset.getString(1);
					bid = resultset.getString(2);
					memberId = resultset.getString(3);

					claims = new HashMap();
					claims.put("ClaimRefNumber", claimrefnumber);
					claims.put("BORROWERID", bid);
					claims.put("MEMBERID", memberId);

					if (!allclaims.contains(claims)) {
						allclaims.addElement(claims);
					}
				}

				resultset.close();
				resultset = null;
			}

			callableStmt.close();
		} catch (SQLException sqlexception) {
			Log.log(2, "CPDAO", "getAllClaimsFiled()", "Error retrieving all Claims Filed so far from the database.");
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(4, "CPDAO", "getAllClaimsFiled()", "Exited!");

		return allclaims;
	}

	public Vector getLockInDetails(String borrowerid) throws DatabaseException {
		Log.log(4, "CPDAO", "getLockInDetails()", "Entered!");
		CallableStatement callableStmt = null;
		Connection conn = null;
		ResultSet resultset = null;
		Vector lockindtlsvector = new Vector();
		int status = -1;
		String errorCode = null;
		try {
			conn = DBConnection.getConnection();
			callableStmt = conn.prepareCall("{? = call packGetDtlsforLock.funcGetDtlsforLock(?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			callableStmt.setString(2, borrowerid);
			callableStmt.registerOutParameter(3, Constants.CURSOR);
			callableStmt.registerOutParameter(4, Constants.CURSOR);
			callableStmt.registerOutParameter(5, Constants.CURSOR);
			callableStmt.registerOutParameter(6, Types.VARCHAR);
			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(6);
			if (status == 1) {
				Log.log(2, "CPDAO", "getLockInDetails()",
						"SP returns a 1 in getting Lock-in details for a borrower.Error code is :" + errorCode);
				callableStmt.close();
				throw new DatabaseException(errorCode);
			}
			if (status == 0) {
				resultset = (ResultSet) callableStmt.getObject(3);
				String cgpan = null;
				Date guaranteeStartDt = null;
				Date lastDisbursemntDt = null;
				String finalDisbursementFlag = null;
				HashMap details = null;
				do {
					if (!resultset.next())
						break;
					details = new HashMap();
					cgpan = resultset.getString(1);
					java.sql.Date guaranteeStartDtSqlDt = resultset.getDate(2);
					java.sql.Date lastDisbursemntDtSqlDt = resultset.getDate(3);
					finalDisbursementFlag = resultset.getString(4);
					if (cgpan != null && guaranteeStartDtSqlDt != null) {
						guaranteeStartDt = new Date(guaranteeStartDtSqlDt.getTime());
						if (lastDisbursemntDtSqlDt != null) {
							lastDisbursemntDt = new Date(lastDisbursemntDtSqlDt.getTime());
							details.put("CGPAN", cgpan);
							details.put("GUARANTEESTARTDT", guaranteeStartDt);
							details.put("LASTDSBRSMNTDT", lastDisbursemntDt);
							lockindtlsvector.addElement(details);
							details = null;
							// System.out.println("guaranteeStartDt:"+new
							// SimpleDateFormat("dd/MM/yyyy").format(guaranteeStartDt)+
							// " lastDisbursemntDt:"+new
							// SimpleDateFormat("dd/MM/yyyy").format(lastDisbursemntDt));
						}
					}
				} while (true);
				resultset.close();
				resultset = null;
				resultset = (ResultSet) callableStmt.getObject(4);
				do {
					if (!resultset.next())
						break;
					details = new HashMap();
					cgpan = resultset.getString(1);
					java.sql.Date guaranteeStartDtSqlDt = resultset.getDate(2);
					if (cgpan != null) {
						details.put("CGPAN", cgpan);
						if (guaranteeStartDtSqlDt == null)
							continue;
						guaranteeStartDt = new Date(guaranteeStartDtSqlDt.getTime());
						details.put("GUARANTEESTARTDT", guaranteeStartDt);
					}
					lockindtlsvector.addElement(details);
					details = null;
				} while (true);
				resultset.close();
				resultset = null;
				resultset = (ResultSet) callableStmt.getObject(5);
				String ccCgpan = null;
				Date ccGuaranteeStartDt = null;
				Date ccLastDisbursemntDt = null;
				String ccFinalDisbursementFlag = null;
				HashMap ccDetails = null;
				do {
					if (!resultset.next())
						break;
					ccDetails = new HashMap();
					ccCgpan = resultset.getString(1);
					java.sql.Date ccGuaranteeStartDtSqlDt = resultset.getDate(2);
					java.sql.Date ccLastDisbursemntDtSqlDt = resultset.getDate(3);
					ccFinalDisbursementFlag = resultset.getString(4);
					if (ccCgpan != null) {
						boolean jumpOut = false;
						for (int k = 0; k < lockindtlsvector.size(); k++) {
							HashMap map = (HashMap) lockindtlsvector.elementAt(k);
							if (map == null)
								continue;
							String thispan = (String) map.get("CGPAN");
							if (thispan == null || !thispan.equals(ccCgpan))
								continue;
							Date tempDate = (Date) map.get("LASTDSBRSMNTDT");
							map = (HashMap) lockindtlsvector.remove(k);
							map = null;
							if (tempDate == null)
								continue;
							map = new HashMap();
							map.put("CGPAN", thispan);
							map.put("GUARANTEESTARTDT", ccGuaranteeStartDtSqlDt);
							if (tempDate.compareTo(ccLastDisbursemntDtSqlDt) > 0)
								map.put("LASTDSBRSMNTDT", tempDate);
							if (tempDate.compareTo(ccLastDisbursemntDtSqlDt) < 0)
								map.put("LASTDSBRSMNTDT", ccLastDisbursemntDtSqlDt);
							if (tempDate.compareTo(ccLastDisbursemntDtSqlDt) == 0)
								map.put("LASTDSBRSMNTDT", ccLastDisbursemntDtSqlDt);
							lockindtlsvector.addElement(map);
							jumpOut = true;
							break;
						}

						if (!jumpOut && ccGuaranteeStartDtSqlDt != null) {
							ccGuaranteeStartDt = new Date(ccGuaranteeStartDtSqlDt.getTime());
							if (ccLastDisbursemntDtSqlDt != null) {
								ccLastDisbursemntDt = new Date(ccLastDisbursemntDtSqlDt.getTime());
								ccDetails.put("CGPAN", ccCgpan);
								ccDetails.put("GUARANTEESTARTDT", ccGuaranteeStartDt);
								ccDetails.put("LASTDSBRSMNTDT", ccLastDisbursemntDt);
								lockindtlsvector.addElement(ccDetails);
								ccDetails = null;
							}
						}
					}
				} while (true);
				resultset.close();
				resultset = null;
			}
			callableStmt.close();
		} catch (SQLException sqlexception) {
			Log.log(2, "CPDAO", "getLockInDetails()",
					"Error retrieving Lock-in details for a borrower from the database.");
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(4, "CPDAO", "getLockInDetails()", "Exited!");
		for (int i = 0; i < lockindtlsvector.size(); i++) {
			HashMap h = (HashMap) lockindtlsvector.elementAt(i);
			Log.log(4, "CPDAO", "getLockInDetails()", "Printing Hashmap from CPDAO :" + h);
		}

		return lockindtlsvector;
	}

	public void saveSettlementDetails(Vector details, Vector voucherdtls, String createdBy, ChequeDetails chequeDetails,
			String contextPath) throws MessageException, DatabaseException {
		Log.log(4, "CPDAO", "saveSettlementDetails()", "Entered!");
		CallableStatement callableStmt = null;
		Connection conn = null;
		RpProcessor rpprocessor = new RpProcessor();
		RpDAO rpDAO = new RpDAO();

		IFDAO ifDAO = new IFDAO();

		String errorCode = null;
		int status = -1;

		SettlementDetail settlmntDtl = null;
		String cgclan = null;
		String finalSettlemntFlag = null;
		double tierOneSettlement = 0.0D;
		java.util.Date tierOneSettlmntDt = null;
		double tierTwoSettlement = 0.0D;
		java.util.Date tierTwoSettlmntDt = null;
		try {
			conn = DBConnection.getConnection(false);

			for (int i = 0; i < details.size(); i++) {
				settlmntDtl = (SettlementDetail) details.elementAt(i);
				if (settlmntDtl != null) {
					String whichInstallment = settlmntDtl.getWhichInstallment();

					if (whichInstallment.equals("F")) {
						cgclan = settlmntDtl.getCgclan();

						finalSettlemntFlag = settlmntDtl.getFinalSettlementFlag();

						tierOneSettlement = settlmntDtl.getTierOneSettlement();

						tierOneSettlmntDt = settlmntDtl.getTierOneSettlementDt();

						tierTwoSettlement = settlmntDtl.getTierTwoSettlement();

						tierTwoSettlmntDt = settlmntDtl.getTierTwoSettlementDt();

						callableStmt = conn.prepareCall("{? = call funcInsertSettDtl(?,?,?,?,?,?,?,?)}");
						callableStmt.registerOutParameter(1, Types.INTEGER);
						callableStmt.setString(2, cgclan);
						callableStmt.setDouble(3, tierOneSettlement);
						if (tierOneSettlmntDt != null) {
							callableStmt.setDate(4, new java.sql.Date(tierOneSettlmntDt.getTime()));
						} else {
							callableStmt.setNull(4, 91);
						}

						callableStmt.setDouble(5, tierTwoSettlement);
						if (tierTwoSettlmntDt != null) {
							callableStmt.setDate(6, new java.sql.Date(tierTwoSettlmntDt.getTime()));
						} else {
							callableStmt.setNull(6, 91);
						}

						callableStmt.setString(7, finalSettlemntFlag);
						callableStmt.setString(8, createdBy);
						callableStmt.registerOutParameter(9, Types.VARCHAR);

						callableStmt.execute();
						status = callableStmt.getInt(1);
						errorCode = callableStmt.getString(9);
						if (status == 1) {
							Log.log(2, "CPDAO", "saveSettlementDetails()",
									"SP returns a 1 in saving Settlement details.Error code is :" + errorCode);

							callableStmt.close();
							try {
								conn.rollback();
							} catch (SQLException localSQLException1) {
							}

							throw new DatabaseException(errorCode);
						}
					} else if (whichInstallment.equals("S")) {
						cgclan = settlmntDtl.getCgclan();

						finalSettlemntFlag = settlmntDtl.getFinalSettlementFlag();

						tierTwoSettlement = settlmntDtl.getTierTwoSettlement();

						tierTwoSettlmntDt = settlmntDtl.getTierTwoSettlementDt();

						callableStmt = conn.prepareCall("{? = call funcUpdateSettDtl(?,?,?,?,?,?)}");
						callableStmt.registerOutParameter(1, Types.INTEGER);
						callableStmt.setString(2, cgclan);
						callableStmt.setDouble(3, tierTwoSettlement);
						if (tierTwoSettlmntDt != null) {
							callableStmt.setDate(4, new java.sql.Date(tierTwoSettlmntDt.getTime()));
						} else {
							callableStmt.setNull(4, 91);
						}
						callableStmt.setString(5, finalSettlemntFlag);
						callableStmt.setString(6, createdBy);
						callableStmt.registerOutParameter(7, Types.VARCHAR);

						callableStmt.execute();
						status = callableStmt.getInt(1);
						errorCode = callableStmt.getString(7);
						if (status == 1) {
							Log.log(2, "CPDAO", "saveSettlementDetails()",
									"SP returns a 1 in saving Settlement details.Error code is :" + errorCode);

							callableStmt.close();
							try {
								conn.rollback();
							} catch (SQLException sqlex) {
								Log.log(2, "CPDAO", "saveSettlementDetails()",
										"Error rolling back transaction. Error code is :" + sqlex.getMessage());
							}
							throw new DatabaseException(errorCode);
						}
					}
				}
			}
			for (int i = 0; i < voucherdtls.size(); i++) {
				VoucherDetail vd = (VoucherDetail) voucherdtls.elementAt(i);
				if (vd != null) {
					rpDAO.insertVoucherDetails(vd, createdBy, conn);
				}
			}
			if (chequeDetails != null) {
				ifDAO.chequeDetailsInsertSuccess(chequeDetails, conn, contextPath, createdBy);
			}

			conn.commit();
			callableStmt.close();
		} catch (SQLException sqlexception) {
			Log.log(2, "CPDAO", "saveSettlementDetails()", "Error saving Settlement Details into the database.");
			try {
				conn.rollback();
			} catch (SQLException sqlex) {
				Log.log(2, "CPDAO", "saveSettlementDetails()", "Error rolling back the SQL Changes.");
			}
			throw new DatabaseException(sqlexception.getMessage());
		} catch (DatabaseException dbexception) {
			Log.log(2, "CPDAO", "saveSettlementDetails()", "Error saving Settlement Details into the database.");
			try {
				conn.rollback();
			} catch (SQLException sqlex) {
				Log.log(2, "CPDAO", "saveSettlementDetails()", "Error rolling back the SQL Changes.");
			}
			throw dbexception;
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(4, "CPDAO", "saveSettlementDetails()", "Exited!");
	}

	public double getCumulativeClaims(Application application) throws DatabaseException {
		Log.log(4, "CPDAO", "getCumulativeClaims()", "Entered!");
		Connection connection = null;
		CallableStatement getCumulativeClaimsStmt = null;
		ResultSet resultSet = null;

		int status = -1;
		double cumulativeClaims = 0.0D;
		try {
			String exception = "";

			connection = DBConnection.getConnection();
			getCumulativeClaimsStmt = connection.prepareCall("{?=call funcGetCumulativeClaim(?,?,?,?,?,?)}");

			getCumulativeClaimsStmt.registerOutParameter(1, Types.INTEGER);
			getCumulativeClaimsStmt.setString(2, application.getBankId());
			getCumulativeClaimsStmt.setString(3, application.getZoneId());
			getCumulativeClaimsStmt.setString(4, application.getBranchId());
			getCumulativeClaimsStmt.registerOutParameter(5, 8);
			getCumulativeClaimsStmt.registerOutParameter(6, Types.VARCHAR);
			getCumulativeClaimsStmt.execute();

			cumulativeClaims = getCumulativeClaimsStmt.getDouble(5);
			status = getCumulativeClaimsStmt.getInt(1);
			exception = getCumulativeClaimsStmt.getString(6);
			if (status == 1) {
				Log.log(2, "CPDAO", "getCumulativeClaims()",
						"SP returns a 1 in getting Cumulative Claims details.Error code is :" + exception);
				getCumulativeClaimsStmt.close();
				throw new DatabaseException(exception);
			}
			getCumulativeClaimsStmt.close();
		} catch (Exception exception) {
			Log.log(2, "CPDAO", "getCumulativeClaims()",
					"Error retrieving Cumulative Claim Details from the database.");
			throw new DatabaseException(exception.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(4, "CPDAO", "getCumulativeClaims()", "Exited!");

		return cumulativeClaims;
	}

	public HashMap getClmRefAndFlagDtls(String bankId, String zoneId, String branchId, String borrowerId)
			throws DatabaseException {
		Log.log(4, "CPDAO", "getClmRefAndFlagDtls()", "Entered!");
		CallableStatement callableStmt = null;
		Connection conn = null;
		ResultSet rs = null;
		HashMap results = new HashMap();

		String clmRefNumber = null;
		String clmStatus = null;
		String cgclan = null;
		String installmentFlag = null;

		int status = -1;
		String errorCode = null;
		try {
			conn = DBConnection.getConnection();
			callableStmt = conn
					.prepareCall("{? = call packGetClmRefAndFlagDtl.funcGetGetClmRefAndFlagDtl(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			callableStmt.setString(2, bankId);
			callableStmt.setString(3, zoneId);
			callableStmt.setString(4, branchId);
			callableStmt.setString(5, borrowerId);
			callableStmt.registerOutParameter(6, Constants.CURSOR);
			callableStmt.registerOutParameter(7, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);

			if (status == 1) {
				Log.log(2, "CPDAO", "getClmRefAndFlagDtls()",
						"SP returns a 1 in getting Claim Ref Number and Installment Flag.Error code is :" + errorCode);
				callableStmt.close();
				throw new DatabaseException(errorCode);
			}
			if (status == 0) {
				rs = (ResultSet) callableStmt.getObject(6);
				String formattedFirstValue = "";
				String formattedSecondValue = "";
				boolean isFirstSettlementDtRead = false;
				boolean isSecondSettlementDtRead = false;

				while (rs.next()) {
					clmRefNumber = rs.getString(2);
					clmStatus = rs.getString(3);
					cgclan = rs.getString(4);

					installmentFlag = rs.getString(5);
					String firstSettlementDate = rs.getString(6);

					if (firstSettlementDate == null) {
						firstSettlementDate = "0";
					}
					String secondSettlementDate = rs.getString(7);
					if (secondSettlementDate == null) {
						secondSettlementDate = "0";
					}
					String whetherFinalInstallment = rs.getString(8);

					String DandUStatus = rs.getString(9);// RAJU

					System.out.println("DandUStatus" + DandUStatus);

					if (installmentFlag.equals("F")) {
						if (cgclan != null) {
							if (!firstSettlementDate.equals("0")) {
								formattedFirstValue = installmentFlag + "#" + cgclan + "#" + firstSettlementDate;
								isFirstSettlementDtRead = true;
							} else if (!isFirstSettlementDtRead) {
								firstSettlementDate = "-";
								formattedFirstValue = installmentFlag + "#" + cgclan + "#" + firstSettlementDate;
							}

						}

//RAJU	
						else if ((cgclan == null)
								&& ((clmStatus.equals("NE")) || (clmStatus.equals("FW")) || (clmStatus.equals("HO")))
								&& ((DandUStatus == null))) {
							formattedFirstValue = "PMA";
							System.out.println("pend at checker side");
						}

						else if ((cgclan == null)
								&& ((clmStatus.equals("NE")) || (clmStatus.equals("FW")) || (clmStatus.equals("HO")))
								&& ((DandUStatus != null))) {
							formattedFirstValue = "PCG";
							System.out.println("pend at cgtmse side");
						}

						else if ((cgclan == null) && (clmStatus.equals("MR"))) {
							formattedFirstValue = "CRE";
							System.out.println("returned by checker");
						}

						/*
						 * else if ((cgclan == null) && ((clmStatus.equals("NE")) ||
						 * (clmStatus.equals("FW")) || (clmStatus.equals("HO")) && (DandUStatus!=null)
						 * && (DandUStatus.equals("Y")) )) { formattedFirstValue = "KK";
						 * System.out.println("pend at cgtmse side"); }
						 * 
						 * 
						 * 
						 * 
						 * else if ((cgclan == null) && (clmStatus.equals("NE") )) { formattedFirstValue
						 * = "kk2"; System.out.println("pending at checker side"); }
						 */

						// RAJU

						// else if ((cgclan == null)
						// && ((clmStatus.equals("NE"))
						// || (clmStatus.equals("FW")) || (clmStatus
						// .equals("HO")))) {
						// formattedFirstValue = null;
						// }

						else if ((cgclan == null) && (clmStatus.equals("RE"))) {
							formattedFirstValue = "$";
						}
						if ((whetherFinalInstallment != null) && (whetherFinalInstallment.equals("Y"))) {
							formattedFirstValue = "^";
						}
					} else if (installmentFlag.equals("S")) {
						if (cgclan != null) {
							if (!secondSettlementDate.equals("0")) {
								formattedSecondValue = installmentFlag + "#" + cgclan + "#" + secondSettlementDate;
								isSecondSettlementDtRead = true;
							} else if (!isSecondSettlementDtRead) {
								secondSettlementDate = "-";
								formattedSecondValue = installmentFlag + "#" + cgclan + "#" + secondSettlementDate;
							}

						} else if ((cgclan == null)
								&& ((clmStatus.equals("NE")) || (clmStatus.equals("FW")) || (clmStatus.equals("HO")))) {
							formattedSecondValue = null;
						} else if ((cgclan == null) && (clmStatus.equals("RE"))) {
							formattedSecondValue = "$";
						}
					}
					results.put("F", formattedFirstValue);
					results.put("S", formattedSecondValue);
				}
				rs.close();
				rs = null;
			}

			callableStmt.close();
		} catch (SQLException sqlexception) {
			Log.log(2, "CPDAO", "getClmRefAndFlagDtls()",
					"Error retrieving Claim Ref Number and the corresponding flag from the database.");
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(4, "CPDAO", "getClmRefAndFlagDtls()", "Exited!");

		return results;
	}

	public Vector getSettlementAdviceDetail(String memberId, String flag) throws DatabaseException {
		Log.log(4, "CPDAO", "getSettlementAdviceDetail()", "Entered!");
		CallableStatement callableStmt = null;
		Connection conn = null;
		ResultSet rs = null;
		HashMap settlmntAdviceDtl = null;
		Vector settlementAdviceDtls = new Vector();

		String bankId = null;
		String zoneId = null;
		String branchId = null;
		String cgclan = null;
		double firstSettlmntTierAmnt = 0.0D;
		java.util.Date firstSettlmntTierDt = null;
		double secondSettlmntTierAmnt = 0.0D;
		java.util.Date secondSettlmntTierDt = null;

		int status = -1;
		String errorCode = null;
		try {
			conn = DBConnection.getConnection();
			callableStmt = conn.prepareCall("{? = call packGetSettlementDtl.funcGetSettlementDtl(?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			callableStmt.setString(2, memberId);
			callableStmt.setString(3, flag);
			callableStmt.registerOutParameter(4, Constants.CURSOR);
			callableStmt.registerOutParameter(5, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(5);

			if (status == 1) {
				Log.log(2, "CPDAO", "getSettlementAdviceDetail()",
						"SP returns a 1 in getting Settlement Advice Details.Error code is :" + errorCode);
				callableStmt.close();
				throw new DatabaseException(errorCode);
			}
			if (status == 0) {
				rs = (ResultSet) callableStmt.getObject(4);
				while (rs.next()) {
					bankId = rs.getString(1);
					zoneId = rs.getString(2);
					branchId = rs.getString(3);
					cgclan = rs.getString(4);
					firstSettlmntTierAmnt = rs.getDouble(6);
					firstSettlmntTierDt = rs.getDate(7);
					secondSettlmntTierAmnt = rs.getDouble(8);
					secondSettlmntTierDt = rs.getDate(9);

					settlmntAdviceDtl = new HashMap();

					settlmntAdviceDtl.put("MEMBERID", memberId);
					settlmntAdviceDtl.put("cgclan", cgclan);
					settlmntAdviceDtl.put("FirstSettlmntAmnt", new Double(firstSettlmntTierAmnt));
					settlmntAdviceDtl.put("FirstSettlmntDt", firstSettlmntTierDt);
					settlmntAdviceDtl.put("SecondSettlmntAmnt", new Double(secondSettlmntTierAmnt));
					settlmntAdviceDtl.put("SecondSettlmntDt", secondSettlmntTierDt);

					settlementAdviceDtls.addElement(settlmntAdviceDtl);
				}

				rs.close();
				rs = null;
			}

			callableStmt.close();
		} catch (SQLException sqlexception) {
			Log.log(2, "CPDAO", "getSettlementAdviceDetail()",
					"Error retrieving Settlement Advice Details from the database.");
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(4, "CPDAO", "getSettlementAdviceDetail()", "Exited!");

		return settlementAdviceDtls;
	}

	public void saveSettlementAdviceDetail(Vector settlemntAdviceDtls, String userid) throws DatabaseException {
		Log.log(4, "CPDAO", "saveSettlementAdviceDetail()", "Entered!");
		CallableStatement callableStmt = null;
		Connection conn = null;

		int status = -1;
		String errorCode = null;

		String cgclan = null;
		String cgcsa = null;
		String voucherid = null;
		String flag = null;
		try {
			conn = DBConnection.getConnection();
			for (int i = 0; i < settlemntAdviceDtls.size(); i++) {
				HashMap dtl = (HashMap) settlemntAdviceDtls.elementAt(i);
				if (dtl != null) {
					cgclan = (String) dtl.get("cgclan");

					cgcsa = generateCGCSANumber();

					voucherid = (String) dtl.get("VOUCHERID");

					flag = (String) dtl.get("INSTALLMENTFLAG");

					callableStmt = conn.prepareCall("{? = call funcInsertSettAdivceDtl(?,?,?,?,?,?)}");
					callableStmt.registerOutParameter(1, Types.INTEGER);
					callableStmt.setString(2, cgclan);
					callableStmt.setString(3, cgcsa);
					callableStmt.setString(4, voucherid);
					callableStmt.setString(5, flag);
					callableStmt.setString(6, userid);
					callableStmt.registerOutParameter(7, Types.VARCHAR);

					callableStmt.execute();
					status = callableStmt.getInt(1);
					errorCode = callableStmt.getString(7);
					if (status == 1) {
						Log.log(2, "CPDAO", "saveSettlementAdviceDetail()",
								"SP returns a 1 in saving Settlement Advice Details.Error code is :" + errorCode);

						callableStmt.close();
						try {
							conn.rollback();
						} catch (SQLException sqlex) {
							Log.log(2, "CPDAO", "saveSettlementAdviceDetail()", "Error :" + sqlex.getMessage());
						}
						throw new DatabaseException(errorCode);
					}
				}
			}
		} catch (SQLException sqlexception) {
			Log.log(2, "CPDAO", "saveSettlementAdviceDetail()",
					"Error saving Settlement Advice Details into the database.");
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(4, "CPDAO", "saveSettlementAdviceDetail()", "Exited!");
	}

	public String generateCGCSANumber() throws DatabaseException {
		Log.log(4, "CPDAO", "generateCGCSANumber()", "Entered!");
		CallableStatement callableStmt = null;
		Connection conn = null;
		String cgcsanumber = null;

		int status = -1;
		String errorCode = null;
		try {
			conn = DBConnection.getConnection();
			callableStmt = conn.prepareCall("{? = call funcGenCGCSA(?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			callableStmt.registerOutParameter(2, Types.VARCHAR);
			callableStmt.registerOutParameter(3, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(3);
			if (status == 1) {
				Log.log(2, "CPDAO", "generateCGCSANumber()",
						"SP returns a 1 in generating CGCSA Number.Error code is :" + errorCode);
				callableStmt.close();
				throw new DatabaseException(errorCode);
			}
			if (status == 0) {
				cgcsanumber = callableStmt.getString(2);
			}

			callableStmt.close();
		} catch (SQLException sqlexception) {
			Log.log(2, "CPDAO", "generateCGCSANumber()", "Error generating CGCSA Number.");
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(4, "CPDAO", "generateCGCSANumber()", "Exited!");

		return cgcsanumber;
	}

	public void updateSettlementDetail(HashMap settlementDtls, String flag, String userid) throws DatabaseException {
		Log.log(4, "CPDAO", "updateSettlementDetail()", "Entered!");
		CallableStatement callableStmt = null;
		Connection conn = null;

		int status = -1;
		String errorCode = null;
		String cgclan = null;
		double settlementAmnt = 0.0D;
		java.util.Date settlementDt = null;

		if (settlementDtls.containsKey("cgclan")) {
			cgclan = (String) settlementDtls.get("cgclan");
		}
		if (settlementDtls.containsKey("SecondSettlmntAmnt")) {
			settlementAmnt = ((Double) settlementDtls.get("SecondSettlmntAmnt")).doubleValue();
		}
		if (settlementDtls.containsKey("SecondSettlmntDt")) {
			settlementDt = (java.util.Date) settlementDtls.get("SecondSettlmntDt");
		}

		try {
			conn = DBConnection.getConnection();
			callableStmt = conn.prepareCall("{? = call funcUpdateSettDtl(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			callableStmt.setString(2, cgclan);
			callableStmt.setDouble(3, settlementAmnt);
			if (settlementDt != null) {
				callableStmt.setDate(4, new java.sql.Date(settlementDt.getTime()));
			} else {
				callableStmt.setNull(4, 91);
			}
			callableStmt.setString(5, flag);
			callableStmt.setString(6, userid);
			callableStmt.registerOutParameter(7, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);
			if (status == 1) {
				Log.log(2, "CPDAO", "updateSettlementDetail()",
						"SP returns a 1 in updating Settlement Details.Error code is :" + errorCode);
				callableStmt.close();
				throw new DatabaseException(errorCode);
			}

			callableStmt.close();
		} catch (SQLException sqlexception) {
			Log.log(2, "CPDAO", "updateSettlementDetail()", "Error updating Settlement Details.");
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(4, "CPDAO", "updateSettlementDetail()", "Exited!");
	}

	public void updateLegalProceedingDetails(LegalProceedingsDetail legaldtls) throws DatabaseException {
		Log.log(4, "CPDAO", "updateLegalProceedingDetails()", "Entered!");
		CallableStatement callableStmt = null;
		Connection conn = null;

		int status = -1;
		String errorCode = null;

		String borrowerId = legaldtls.getBorrowerId();
		String forumThruWhichInitiated = legaldtls.getForumRecoveryProceedingsInitiated();
		String suitCaseNumber = legaldtls.getSuitCaseRegNumber();
		// System.out.println("casesuitregnumber:"+suitCaseNumber);
		java.util.Date filingDt = legaldtls.getFilingDate();
		String forumName = legaldtls.getNameOfForum();
		// System.out.println("forumThruWhichInitiated:"+forumThruWhichInitiated+"
		// forumName:"+forumName);
		String location = legaldtls.getLocation();
		double amntClaimed = legaldtls.getAmountClaimed();
		String currentStatus = legaldtls.getCurrentStatusRemarks();
		String recoveryFlag = legaldtls.getIsRecoveryProceedingsConcluded();

		String commHeadedByOfficerOrAboveauth = legaldtls.getCommHeadedByOfficerOrAbove();
		try {
			conn = DBConnection.getConnection();
			callableStmt = conn
					.prepareCall("{? = call packGetInsUpdLegalDetail.funcUpdateLegalDetail(?,?,?,?,?,?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			callableStmt.setString(2, borrowerId);

			if (forumThruWhichInitiated.equals("")) {
				callableStmt.setString(3, "waiver as per circular 62");
			} else {

				callableStmt.setString(3, forumThruWhichInitiated);
			}
			callableStmt.setString(4, suitCaseNumber);

			if (location.equals("")) {
				callableStmt.setString(7, "circular 62");
			} else {

				callableStmt.setString(7, location);
			}

			if (filingDt != null) {
				callableStmt.setDate(5, new java.sql.Date(filingDt.getTime()));
			} else {
				callableStmt.setNull(5, 91);
			}

			callableStmt.setString(6, forumName);
			// callableStmt.setString(7, location);
			callableStmt.setDouble(8, amntClaimed);
			callableStmt.setString(9, currentStatus);
			callableStmt.setString(10, recoveryFlag);

			if (commHeadedByOfficerOrAboveauth != null) {
				if (commHeadedByOfficerOrAboveauth.equals("N")) {
					callableStmt.setString(11, "N");
				} else {
					callableStmt.setString(11, "Y");
				}

			} else {
				callableStmt.setString(11, "NA");
			}

			// callableStmt.setString(11, commHeadedByOfficerOrAboveauth);
			callableStmt.registerOutParameter(12, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(12);
			if (status == 1) {
				Log.log(2, "CPDAO", "updateLegalProceedingDetails()",
						"SP returns a 1 in updating Legal Proceedings Details.Error code is :" + errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}

			callableStmt.close();
		} catch (SQLException sqlexception) {
			Log.log(2, "CPDAO", "updateLegalProceedingDetails()", "Error updating Legal Proceeding Details.");
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(4, "CPDAO", "updateLegalProceedingDetails()", "Exited!");
	}

	public HashMap getFirstClmDtlForBid(String bankId, String zoneId, String branchId, String borrowerId)
			throws DatabaseException {
		Log.log(4, "CPDAO", "getFirstClmDtlForBid()", "Entered!");
		CallableStatement callableStmt = null;
		Connection conn = null;

		int status = -1;
		String errorCode = null;

		String clmRefNumber = null;
		String cgclan = null;
		java.util.Date tierOneSettlmntDt = null;
		double firstClmApprvdAmt = 0.0D;
		java.util.Date firstClmApprvdDt = null;
		java.util.Date clmRecallNoticeDt = null;

		HashMap clmDtl = new HashMap();
		try {
			conn = DBConnection.getConnection();
			callableStmt = conn
					.prepareCall("{? = call packGetFirstClmDtlForBid.funcGetFirstClmDtlForBid(?,?,?,?,?,?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			callableStmt.setString(2, bankId);
			callableStmt.setString(3, zoneId);
			callableStmt.setString(4, branchId);
			callableStmt.setString(5, borrowerId);
			callableStmt.registerOutParameter(6, Types.VARCHAR);
			callableStmt.registerOutParameter(7, Types.VARCHAR);
			callableStmt.registerOutParameter(8, 91);
			callableStmt.registerOutParameter(9, 8);
			callableStmt.registerOutParameter(10, 91);
			callableStmt.registerOutParameter(11, 91);
			callableStmt.registerOutParameter(12, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(12);
			if (status == 1) {
				Log.log(2, "CPDAO", "getFirstClmDtlForBid()",
						"SP returns a 1 in getting Claim Details for First Installment.Error code is :" + errorCode);
				callableStmt.close();
				throw new DatabaseException(errorCode);
			}
			if (status == 0) {
				clmRefNumber = callableStmt.getString(6);
				cgclan = callableStmt.getString(7);
				tierOneSettlmntDt = callableStmt.getDate(8);
				firstClmApprvdAmt = callableStmt.getDouble(9);
				firstClmApprvdDt = callableStmt.getDate(10);
				clmRecallNoticeDt = callableStmt.getDate(11);

				clmDtl.put("ClaimRefNumber", clmRefNumber);
				clmDtl.put("cgclan", cgclan);
				clmDtl.put("FirstSettlmntDt", tierOneSettlmntDt);
				clmDtl.put("CLMAPPRVDAMT", new Double(firstClmApprvdAmt));
				clmDtl.put("CLMAPPRVDDT", firstClmApprvdDt);
				clmDtl.put("RECALL-", clmRecallNoticeDt);
			}

			callableStmt.close();
		} catch (SQLException sqlexception) {
			Log.log(2, "CPDAO", "getFirstClmDtlForBid()", "Error retrieving First Clm Dtls for the Borrower.");
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(4, "CPDAO", "getFirstClmDtlForBid()", "Exited!");

		return clmDtl;
	}

	public boolean isClaimSettlementMade(String borrowerId) {
		return true;
	}

	public Vector getClaimAppliedAmounts(String borrowerid, String flag) throws DatabaseException {
		Log.log(4, "CPDAO", "getClaimAppliedAmounts", "Entered");
		CallableStatement callableStmt = null;
		Connection conn = null;
		Vector clmappliedamnts = new Vector();
		int status = -1;
		String cgpan = null;
		double clmAppliedAmnt = 0.0D;
		String errorCode = null;
		ResultSet rs = null;
		HashMap dtl = null;
		try {
			conn = DBConnection.getConnection();
			callableStmt = conn.prepareCall("{? = call packGetClmAppliedAmnts.funcGetClmAppliedAmnts(?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			callableStmt.setString(2, borrowerid);
			callableStmt.setString(3, flag);
			callableStmt.registerOutParameter(4, Constants.CURSOR);
			callableStmt.registerOutParameter(5, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(5);
			if (status == 1) {
				Log.log(2, "CPDAO", "getClaimAppliedAmounts()",
						"SP returns a 1 in getting Claim Applied Amounts. Error code is :" + errorCode);
				callableStmt.close();
				throw new DatabaseException(errorCode);
			}
			if (status == 0) {
				rs = (ResultSet) callableStmt.getObject(4);
				while (rs.next()) {
					dtl = new HashMap();
					cgpan = rs.getString(1);
					clmAppliedAmnt = rs.getDouble(2);
					dtl.put("CGPAN", cgpan);
					dtl.put("ClaimAppliedAmnt", new Double(clmAppliedAmnt));
					clmappliedamnts.addElement(dtl);
				}
				rs.close();
				rs = null;
			}
			callableStmt.close();
		} catch (SQLException sqlexception) {
			Log.log(2, "CPDAO", "getClaimAppliedAmounts()",
					"Error retrieving claim applied amounts. Error is :" + sqlexception.getMessage());
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(4, "CPDAO", "getClaimAppliedAmounts", "Exited");

		return clmappliedamnts;
	}

	public Vector getOTSReferenceDetailsForBorrower(String borrowerId) throws DatabaseException {
		Log.log(4, "CPDAO", "getOTSReferenceDetailsForBorrower()", "Entered");
		Vector otsreqdetails = new Vector();
		Connection conn = null;
		CallableStatement callableStmt = null;
		ResultSet rs = null;
		HashMap otsreqdetail = null;

		String cgpan = null;
		double approvedAmnt = 0.0D;
		double enhancedApprvdAmnt = 0.0D;
		double tcSantionedAmnt = 0.0D;
		double wcFBLimit = 0.0D;
		double wcNFBLimit = 0.0D;
		String bid = null;
		String otsreason = null;
		String willfulDefaulter = null;
		java.util.Date otsReqDt = null;
		double borrowerProposedAmnt = 0.0D;
		double proposedScrificedAmnt = 0.0D;
		double outstandingAmnt = 0.0D;

		int status = -1;
		String errorCode = null;
		try {
			conn = DBConnection.getConnection();
			callableStmt = conn.prepareCall("{?=call packGetOTSReqDetails.funcGetOTSRequestDetails(?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			callableStmt.setString(2, borrowerId);
			callableStmt.registerOutParameter(3, Constants.CURSOR);
			callableStmt.registerOutParameter(4, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(4);

			if (status == 1) {
				Log.log(2, "CPDAO", "getOTSReferenceDetailsForBorrower()",
						"SP returns a 1 in retrieving OTS Request Details. Error code is :" + errorCode);
				callableStmt.close();
				throw new DatabaseException(errorCode);
			}
			if (status == 0) {
				rs = (ResultSet) callableStmt.getObject(3);
				while (rs.next()) {
					cgpan = rs.getString(1);
					approvedAmnt = rs.getDouble(2);
					enhancedApprvdAmnt = rs.getDouble(3);
					tcSantionedAmnt = rs.getDouble(4);
					wcFBLimit = rs.getDouble(5);
					wcNFBLimit = rs.getDouble(6);
					bid = rs.getString(7);
					otsreason = rs.getString(8);
					willfulDefaulter = rs.getString(9);
					otsReqDt = rs.getDate(10);
					borrowerProposedAmnt = rs.getDouble(11);
					proposedScrificedAmnt = rs.getDouble(12);
					outstandingAmnt = rs.getDouble(13);

					otsreqdetail = new HashMap();
					otsreqdetail.put("CGPAN", cgpan);
					otsreqdetail.put("ApprovedAmount", new Double(approvedAmnt));
					otsreqdetail.put("EnhancedApprovedAmount", new Double(enhancedApprvdAmnt));
					otsreqdetail.put("TCSanctionedAmnt", new Double(tcSantionedAmnt));
					otsreqdetail.put("WCFundBasedSanctionedAmnt", new Double(wcFBLimit));
					otsreqdetail.put("WCNFBSanctionedAmnt", new Double(wcNFBLimit));
					otsreqdetail.put("BORROWERID", bid);
					otsreqdetail.put("ReasonForOTS", otsreason);
					otsreqdetail.put("WillfulDefaulter", willfulDefaulter);
					otsreqdetail.put("OTSRequestDate", otsReqDt);
					otsreqdetail.put("TotalBorrowerProposedAmnt", new Double(borrowerProposedAmnt));
					otsreqdetail.put("TotalProposedSacrificeAmt", new Double(proposedScrificedAmnt));
					otsreqdetail.put("TotalOSAmnt", new Double(outstandingAmnt));

					otsreqdetails.addElement(otsreqdetail);
				}
				rs.close();
				rs = null;
			}

			callableStmt.close();
		} catch (SQLException sqlexception) {
			Log.log(4, "CPDAO", "getOTSReferenceDetailsForBorrower()",
					"There is an error. Error is :" + sqlexception.getMessage());
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(4, "CPDAO", "getOTSReferenceDetailsForBorrower()", "Exited");

		return otsreqdetails;
	}

	public HashMap getClaimSettlementDetailForBorrower(String borrowerId) throws DatabaseException {
		Log.log(4, "CPDAO", "getClaimSettlementDetailForBorrower()", "Entered");
		CallableStatement callableStmt = null;
		Connection conn = null;
		ResultSet rs = null;
		HashMap settlementDetails = new HashMap();

		int status = -1;
		String errorCode = null;

		double firstSettlementAmnt = 0.0D;
		java.util.Date firstSettlementDt = null;
		double secSettlementAmnt = 0.0D;
		java.util.Date secSettlementDt = null;
		try {
			conn = DBConnection.getConnection();
			callableStmt = conn
					.prepareCall("{? = call packGetClaimSettlementForBid.funcGetClaimSettlementForBid(?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			callableStmt.setString(2, borrowerId);
			callableStmt.registerOutParameter(3, Constants.CURSOR);
			callableStmt.registerOutParameter(4, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(4);

			if (status == 1) {
				Log.log(2, "CPDAO", "getClaimSettlementDetailForBorrower()",
						"SP returns a 1 in retrieving Claim Settlement Details. Error code is :" + errorCode);
				callableStmt.close();
				throw new DatabaseException(errorCode);
			}
			if (status == 0) {
				rs = (ResultSet) callableStmt.getObject(3);
				while (rs.next()) {
					firstSettlementAmnt = rs.getDouble(1);
					firstSettlementDt = rs.getDate(2);
					secSettlementAmnt = rs.getDouble(3);
					secSettlementDt = rs.getDate(4);
				}
				settlementDetails.put("FirstSettlmntAmnt", new Double(firstSettlementAmnt));
				settlementDetails.put("FirstSettlmntDt", firstSettlementDt);
				settlementDetails.put("SecondSettlmntAmnt", new Double(secSettlementAmnt));
				settlementDetails.put("SecondSettlmntDt", secSettlementDt);

				rs.close();
				rs = null;
			}

			callableStmt.close();
		} catch (SQLException sqlexception) {
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(4, "CPDAO", "getClaimSettlementDetailForBorrower()", "Exited");

		return settlementDetails;
	}

	public Vector getServiceFeeDetails(String claimRefNumber) throws DatabaseException {
		Log.log(4, "CPDAO", "getServiceFeeDetails", "Entered");
		CallableStatement callableStmt = null;
		Connection conn = null;
		ResultSet rs = null;

		int status = -1;
		String errorCode = null;

		Vector serviceFeeDetails = new Vector();
		HashMap serviceFeeDtl = null;
		String cgpan = null;
		double amountRaised = 0.0D;
		java.util.Date appropriationDate = null;
		try {
			conn = DBConnection.getConnection();
			callableStmt = conn.prepareCall("{? = packGetServiceFeeDetail.funcGetServiceFeeDetail(?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			callableStmt.setString(2, claimRefNumber);
			callableStmt.registerOutParameter(3, Constants.CURSOR);
			callableStmt.registerOutParameter(4, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(4);

			if (status == 1) {
				Log.log(2, "CPDAO", "getServiceFeeDetails",
						"SP returns a 1 for funcGetServiceFeeDetail SP. Error is :" + errorCode);
				callableStmt.close();
				throw new DatabaseException(errorCode);
			}

			rs = (ResultSet) callableStmt.getObject(3);

			while (rs.next()) {
				cgpan = rs.getString(1);
				amountRaised = rs.getDouble(2);
				appropriationDate = rs.getDate(3);

				serviceFeeDtl = new HashMap();
				serviceFeeDtl.put("CGPAN", cgpan);
				serviceFeeDtl.put("ServiceFee", new Double(amountRaised));
				serviceFeeDtl.put("ServiceFeeDate", appropriationDate);

				serviceFeeDetails.addElement(serviceFeeDtl);
			}
			rs.close();
			rs = null;
		} catch (SQLException sqlexception) {
			Log.log(2, "CPDAO", "getServiceFeeDetails",
					"Exception in getServiceFeeDetails method. Exception is :" + sqlexception.getMessage());
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(2, "CPDAO", "getServiceFeeDetails", "Exited");

		return serviceFeeDetails;
	}

	public Vector getWorkingCapitalDetails(String claimRefNumber) throws DatabaseException {
		Log.log(4, "CPDAO", "getWorkingCapitalDetails", "Entered");
		CallableStatement callableStmt = null;
		Connection conn = null;
		ResultSet rs = null;

		int status = -1;
		String errorCode = null;

		Vector workingCapitalDetails = new Vector();
		HashMap workingCapitalDtl = null;
		String cgpan = null;
		double sanctionedAmnt = 0.0D;
		double outstandingAmntAsOnNPA = 0.0D;
		try {
			conn = DBConnection.getConnection();
			callableStmt = conn.prepareCall("{? = packGetWCDetails.funcGetWorkingCapitalDtl(?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			callableStmt.setString(2, claimRefNumber);
			callableStmt.registerOutParameter(3, Constants.CURSOR);
			callableStmt.registerOutParameter(4, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(4);

			if (status == 1) {
				Log.log(2, "CPDAO", "getWorkingCapitalDetails",
						"SP returns a 1 for funcGetWorkingCapitalDtl SP. Error is :" + errorCode);
				callableStmt.close();
				throw new DatabaseException(errorCode);
			}

			rs = (ResultSet) callableStmt.getObject(3);

			while (rs.next()) {
				cgpan = rs.getString(1);
				sanctionedAmnt = rs.getDouble(2);
				outstandingAmntAsOnNPA = rs.getDouble(3);

				workingCapitalDtl = new HashMap();
				workingCapitalDtl.put("CGPAN", cgpan);
				workingCapitalDtl.put("WCFundBasedSanctionedAmnt", new Double(sanctionedAmnt));
				workingCapitalDtl.put("OSAmntAsOnNPADate", new Double(outstandingAmntAsOnNPA));

				workingCapitalDetails.addElement(workingCapitalDtl);
			}
			rs.close();
			rs = null;
		} catch (SQLException sqlexception) {
			Log.log(2, "CPDAO", "getWorkingCapitalDetails()",
					"Exception in getWorkingCapitalDetails method. Exception is :" + sqlexception.getMessage());
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(4, "CPDAO", "getWorkingCapitalDetails()", "Exited");

		return workingCapitalDetails;
	}

	public Vector getTermCreditDetails(String claimRefNumber) throws DatabaseException {
		Log.log(4, "CPDAO", "getTermCreditDetails", "Entered");
		CallableStatement callableStmt = null;
		Connection conn = null;
		ResultSet rs = null;

		int status = -1;
		String errorCode = null;

		Vector termCreditDetails = new Vector();
		HashMap termCreditDtl = null;
		String cgpan = null;
		double outstandingAmntAsOnNPA = 0.0D;
		try {
			conn = DBConnection.getConnection();
			callableStmt = conn.prepareCall("{? = packGetTCDetails.funcGetTermCreditDtls(?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			callableStmt.setString(2, claimRefNumber);
			callableStmt.registerOutParameter(3, Constants.CURSOR);
			callableStmt.registerOutParameter(4, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(4);

			if (status == 1) {
				Log.log(2, "CPDAO", "getTermCreditDetails",
						"SP returns a 1 for funcGetTermCreditDtls SP. Error is :" + errorCode);
				callableStmt.close();
				throw new DatabaseException(errorCode);
			}

			rs = (ResultSet) callableStmt.getObject(3);

			while (rs.next()) {
				cgpan = rs.getString(1);
				outstandingAmntAsOnNPA = rs.getDouble(2);

				termCreditDtl = new HashMap();
				termCreditDtl.put("CGPAN", cgpan);
				termCreditDtl.put("OSAmntAsOnNPADate", new Double(outstandingAmntAsOnNPA));

				termCreditDetails.addElement(termCreditDtl);
			}
			rs.close();
			rs = null;
		} catch (SQLException sqlexception) {
			Log.log(2, "CPDAO", "getTermCreditDetails",
					"Exception in getTermCreditDetails method. Exception is :" + sqlexception.getMessage());
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(4, "CPDAO", "getTermCreditDetails", "Exited");

		return termCreditDetails;
	}

	public Vector getAllVoucherIds() throws DatabaseException {
		Log.log(4, "CPDAO", "getAllVoucherIds()", "Entered!");
		CallableStatement callableStmt = null;
		Connection conn = null;
		ResultSet resultset = null;
		Vector allVouchers = new Vector();

		int status = -1;
		String errorCode = null;

		int voucherId = 0;
		try {
			conn = DBConnection.getConnection();
			callableStmt = conn.prepareCall("{? = call packGetAllVoucherIds.funcGetAllVoucherIds(?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			callableStmt.registerOutParameter(2, Constants.CURSOR);
			callableStmt.registerOutParameter(3, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(3);

			if (status == 1) {
				Log.log(2, "CPDAO", "getAllVoucherIds()",
						"SP returns a 1 in getting all claims filed.Error code is :" + errorCode);
				callableStmt.close();
				throw new DatabaseException(errorCode);
			}
			if (status == 0) {
				resultset = (ResultSet) callableStmt.getObject(2);
				while (resultset.next()) {
					voucherId = resultset.getInt(1);
					if (!allVouchers.contains(new Integer(voucherId))) {
						allVouchers.addElement(new Integer(voucherId));
					}
				}

				resultset.close();
				resultset = null;
			}

			callableStmt.close();
		} catch (SQLException sqlexception) {
			Log.log(2, "CPDAO", "getAllVoucherIds()", "Error retrieving all Claims Filed so far from the database.");
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(4, "CPDAO", "getAllVoucherIds()", "Exited!");

		return allVouchers;
	}

	private void updateNPADetails(NPADetails details) throws DatabaseException {
		Log.log(2, "CPDAO", "updateNPADetails()", "Entered.");
		CallableStatement callableStmt = null;
		Connection conn = null;

		int status = -1;
		String errorCode = null;

		String borrowerId = details.getCgbid();

		java.util.Date npaEffectiveDt = details.getNpaDate();

		String cgtsiReportingFlag = details.getWhetherNPAReported();

		String cgtsiReportingMode = details.getCgtsiReportingMode();
		java.util.Date cgtsiReportingDate = details.getReportingDate();
		String reference = details.getReference();
		double osAmnt = details.getOsAmtOnNPA();
		String reasonForNPA = details.getNpaReason();
		String mliRemarks = details.getEffortsTaken();
		String recoveryInitiated = details.getIsRecoveryInitiated();
		int noOfActions = details.getNoOfActions();
		java.util.Date actionCompletionDt = details.getEffortsConclusionDate();
		String mliRemarksOnFinPosition = details.getMliCommentOnFinPosition();
		String dtlsOnFinAssistance = details.getDetailsOfFinAssistance();
		String enhanceSupport = details.getCreditSupport();
		String bankFacilityDtl = details.getBankFacilityDetail();
		String willfulDefaulter = details.getWillfulDefaulter();

		String watchListFlag = details.getPlaceUnderWatchList();
		String monitoringDtls = null;
		String remarks = details.getRemarksOnNpa();
		java.util.Date recConcludingDt = details.getDtOfConclusionOfRecProc();
		String whetherWrittenOff = details.getWhetherWrittenOff();
		java.util.Date writtenOffDate = details.getDtOnWhichAccntWrittenOff();
		try {
			conn = DBConnection.getConnection();
			callableStmt = conn
					.prepareCall("{? = call funcUpdateNPADtl(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			callableStmt.setString(2, borrowerId);
			if (npaEffectiveDt != null) {
				callableStmt.setDate(3, new java.sql.Date(npaEffectiveDt.getTime()));
			} else {
				callableStmt.setNull(3, 91);
			}
			callableStmt.setString(4, cgtsiReportingFlag);
			if (cgtsiReportingMode == null) {
				callableStmt.setNull(5, Types.VARCHAR);
			} else {
				callableStmt.setString(5, cgtsiReportingMode);
			}
			if (cgtsiReportingDate != null) {
				callableStmt.setDate(6, new java.sql.Date(cgtsiReportingDate.getTime()));
			} else {
				callableStmt.setNull(6, 91);
			}
			callableStmt.setString(7, reference);
			callableStmt.setDouble(8, osAmnt);
			callableStmt.setString(9, reasonForNPA);
			callableStmt.setString(10, mliRemarks);
			callableStmt.setString(11, recoveryInitiated);
			callableStmt.setInt(12, noOfActions);
			if (actionCompletionDt != null) {
				callableStmt.setDate(13, new java.sql.Date(actionCompletionDt.getTime()));
			} else {
				callableStmt.setNull(13, 91);
			}
			callableStmt.setString(14, mliRemarksOnFinPosition);
			callableStmt.setString(15, dtlsOnFinAssistance);
			callableStmt.setString(16, enhanceSupport);
			callableStmt.setString(17, bankFacilityDtl);
			callableStmt.setString(18, willfulDefaulter);
			callableStmt.setString(19, watchListFlag);
			callableStmt.setString(20, monitoringDtls);
			callableStmt.setString(21, remarks);
			if (recConcludingDt != null) {
				callableStmt.setDate(22, new java.sql.Date(recConcludingDt.getTime()));
			} else {
				callableStmt.setNull(22, 91);
			}
			callableStmt.setString(23, whetherWrittenOff);
			if (writtenOffDate != null) {
				callableStmt.setDate(24, new java.sql.Date(writtenOffDate.getTime()));
			} else {
				callableStmt.setNull(24, 91);
			}
			callableStmt.registerOutParameter(25, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(25);
			if (status == 1) {
				Log.log(2, "CPDAO", "updateNPADetails()",
						"SP returns a 1 in updating NPA Details.Error code is :" + errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}

			callableStmt.close();
		} catch (SQLException sqlexception) {
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(2, "CPDAO", "updateNPADetails()", "Exited.");
	}

	public HashMap getPrimarySecurityAndNetworthOfGuarantors(String borrowerId, String memberId)
			throws DatabaseException {
		Log.log(2, "CPDAO", "getPrimarySecurityAndNetworthOfGuarantors()", "Entered.");

		Vector cgpans = getCGPANDetailsForBorrowerId(borrowerId, memberId);
		HashMap cgpanDtl = null;
		String cgpan = null;
		String appRefNumber = null;

		double totalNetWorth = 0.0D;
		double totalValOfLand = 0.0D;
		double totalValOfMachine = 0.0D;
		double totalValOfBuilding = 0.0D;
		double totalValOfOFMA = 0.0D;
		double totalValOfCurrAssets = 0.0D;
		double totalValOfOthers = 0.0D;

		CallableStatement callableStmt = null;
		Connection conn = null;
		HashMap completeDtls = new HashMap();
		if (cgpans == null) {
			return null;
		}
		try {
			conn = DBConnection.getConnection();
			for (int j = 0; j < cgpans.size(); j++) {
				cgpanDtl = (HashMap) cgpans.elementAt(j);
				if (cgpanDtl != null) {
					cgpan = (String) cgpanDtl.get("CGPAN");
					if (cgpan != null) {
						appRefNumber = getAppRefNumber(cgpan);
						if (appRefNumber != null) {
							callableStmt = conn
									.prepareCall("{?=call packGetPersonalGuarantee.funcGetPerGuarforAppRef(?,?,?)}");

							callableStmt.setString(2, appRefNumber);

							callableStmt.registerOutParameter(1, Types.INTEGER);
							callableStmt.registerOutParameter(4, Types.VARCHAR);

							callableStmt.registerOutParameter(3, Constants.CURSOR);

							callableStmt.executeQuery();

							int status = callableStmt.getInt(1);

							Log.log(5, "CPDAO", "getPrimarySecurityAndNetworthOfGuarantors", "Status :" + status);

							if (status == 1) {
								String error = callableStmt.getString(4);

								callableStmt.close();
								callableStmt = null;

								conn.rollback();

								Log.log(2, "ApplicationDAO", "getPrimarySecurityAndNetworthOfGuarantors",
										"Error Message:" + error);

								throw new DatabaseException(error);
							}

							ResultSet guarantorsResults = (ResultSet) callableStmt.getObject(3);
							int i = 0;
							while (guarantorsResults.next()) {
								if (i == 0) {
									totalNetWorth += guarantorsResults.getDouble(2);
								}
								if (i == 1) {
									totalNetWorth += guarantorsResults.getDouble(2);
								}
								if (i == 2) {
									totalNetWorth += guarantorsResults.getDouble(2);
								}
								if (i == 3) {
									totalNetWorth += guarantorsResults.getDouble(2);
								}
								i++;
							}

							guarantorsResults.close();
							guarantorsResults = null;

							callableStmt.close();
							callableStmt = null;
						}
					}
				}
			}
			completeDtls.put("networth", new Double(totalNetWorth));

			for (int i = 0; i < cgpans.size(); i++) {
				cgpanDtl = (HashMap) cgpans.elementAt(i);
				if (cgpanDtl != null) {
					cgpan = (String) cgpanDtl.get("CGPAN");
					if (cgpan != null) {
						appRefNumber = getAppRefNumber(cgpan);
						if (appRefNumber != null) {
							callableStmt = conn
									.prepareCall("{?=call packGetPrimarySecurity.funcGetPriSecforAppRef(?,?,?)}");

							callableStmt.setString(2, appRefNumber);

							callableStmt.registerOutParameter(1, Types.INTEGER);
							callableStmt.registerOutParameter(4, Types.VARCHAR);

							callableStmt.registerOutParameter(3, Constants.CURSOR);

							callableStmt.executeQuery();
							int status = callableStmt.getInt(1);

							if (status == 1) {
								String error = callableStmt.getString(4);

								callableStmt.close();
								callableStmt = null;

								conn.rollback();

								throw new DatabaseException(error);
							}
							ResultSet psResults = (ResultSet) callableStmt.getObject(3);
							while (psResults.next()) {
								if (psResults.getString(1).equals("Land")) {
									totalValOfLand += psResults.getDouble(3);
								}
								if (psResults.getString(1).equals("Building")) {
									totalValOfBuilding += psResults.getDouble(3);
								}
								if (psResults.getString(1).equals("Machinery")) {
									totalValOfMachine += psResults.getDouble(3);
								}
								if (psResults.getString(1).equals("Fixed Assets")) {
									totalValOfOFMA += psResults.getDouble(3);
								}
								if (psResults.getString(1).equals("Current Assets")) {
									totalValOfCurrAssets += psResults.getDouble(3);
								}
								if (psResults.getString(1).equals("Others")) {
									totalValOfOthers += psResults.getDouble(3);
								}

							}

							psResults.close();
							psResults = null;
							callableStmt.close();
							callableStmt = null;
						}
					}
				}
			}

		} catch (SQLException sqlex) {
			Log.log(2, "CPDAO", "getPrimarySecurityAndNetworthOfGuarantors()", "Error :" + sqlex.getMessage());
			throw new DatabaseException(sqlex.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}

		completeDtls.put("LAND", new Double(totalValOfLand));
		completeDtls.put("BUILDING", new Double(totalValOfBuilding));
		completeDtls.put("MACHINE", new Double(totalValOfMachine));
		completeDtls.put("OTHER FIXED MOVABLE ASSETS", new Double(totalValOfOFMA));
		completeDtls.put("CURRENT ASSETS", new Double(totalValOfCurrAssets));
		completeDtls.put("OTHERS", new Double(totalValOfOthers));

		Log.log(2, "CPDAO", "getPrimarySecurityAndNetworthOfGuarantors()", "Exited");

		return completeDtls;
	}

	public String getAppRefNumber(String cgpan) throws DatabaseException {
		Log.log(2, "CPDAO", "getAppRefNumber()", "Entered");
		CallableStatement callableStmt = null;
		Connection conn = null;
		int status = -1;
		String errorCode = null;
		String appRefNumber = null;
		try {
			conn = DBConnection.getConnection();
			if (conn == null) {
				Log.log(2, "CPDAO", "getAppRefNumber()", "Conection is null");
			}
			callableStmt = conn.prepareCall("{? = call funcGetAppRefNoforCGPAN(?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			callableStmt.registerOutParameter(2, Types.VARCHAR);
			callableStmt.setNull(3, Types.VARCHAR);
			callableStmt.setString(4, cgpan);
			callableStmt.registerOutParameter(5, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(5);
			if (status == 1) {
				Log.log(2, "CPDAO", "getAppRefNumber()",
						"SP returns a 1 in getting all claims filed.Error code is :" + errorCode);
				callableStmt.close();
			}

			appRefNumber = callableStmt.getString(2);
		} catch (SQLException sqlex) {
			Log.log(2, "CPDAO", "getAppRefNumber()", "Error :" + sqlex.getMessage());

			throw new DatabaseException(sqlex.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(2, "CPDAO", "getAppRefNumber()", "Exited");

		return appRefNumber;
	}

	public HashMap getRepaymentDetails(String cgpan) throws DatabaseException {
		Log.log(4, "CPDAO", "getRepaymentDetails", "Entered");

		CallableStatement callableStmt = null;
		Connection conn = DBConnection.getConnection();
		ResultSet rs = null;
		HashMap map = new HashMap();
		double totalAmntRepaid = 0.0D;

		double repayAmnt = 0.0D;
		String repayId = null;
		java.util.Date repayDt = null;

		int status = -1;
		String errorCode = null;
		try {
			callableStmt = conn.prepareCall("{? = call packGetRepaymentDtls.funcGetRepaymentDtl(?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			callableStmt.setString(2, cgpan);
			callableStmt.registerOutParameter(3, Constants.CURSOR);
			callableStmt.registerOutParameter(4, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(4);
			if (status == 1) {
				Log.log(2, "CPDAO", "getRepaymentDetails", "SP returns a 1. Code is :" + errorCode);
				callableStmt.close();
				throw new DatabaseException(errorCode);
			}
			rs = (ResultSet) callableStmt.getObject(3);
			while (rs.next()) {
				repayId = rs.getString(1);
				repayAmnt = rs.getDouble(2);
				repayDt = rs.getDate(3);
				totalAmntRepaid += repayAmnt;
			}
			rs.close();
			rs = null;

			map.put("CGPAN", cgpan);
			map.put("AMNT_REPAID", new Double(totalAmntRepaid));
		} catch (SQLException sqlex) {
			throw new DatabaseException(sqlex.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(4, "CPDAO", "getRepaymentDetails", "Exited");

		return map;
	}

	public HashMap getClaimLimitDtls(String designation) throws DatabaseException {
		Log.log(4, "CPDAO", "getClaimLimitDtls", "Entered");
		CallableStatement callableStmt = null;
		Connection conn = DBConnection.getConnection();
		ResultSet rs = null;
		HashMap map = new HashMap();
		double maxClmApprvAmnt = 0.0D;

		java.util.Date fromDate = null;
		java.util.Date toDate = null;

		int status = -1;
		String errorCode = null;
		try {
			callableStmt = conn.prepareCall("{? = call packGetClmLimitDtls.funcGetClmLimitDtls(?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			callableStmt.setString(2, designation);
			callableStmt.registerOutParameter(3, Constants.CURSOR);
			callableStmt.registerOutParameter(4, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(4);
			if (status == 1) {
				Log.log(2, "CPDAO", "getClaimLimitDtls", "SP returns a 1. Code is :" + errorCode);
				callableStmt.close();
				throw new DatabaseException(errorCode);
			}
			rs = (ResultSet) callableStmt.getObject(3);
			while (rs.next()) {
				maxClmApprvAmnt = rs.getDouble(1);
				fromDate = rs.getDate(2);
				toDate = rs.getDate(3);
			}
			rs.close();
			rs = null;

			map.put("Max Claim Approved Amount", new Double(maxClmApprvAmnt));
			map.put("Claim Valid From Date", fromDate);
			map.put("Claim Valid To Date", toDate);
		} catch (SQLException sqlex) {
			throw new DatabaseException(sqlex.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(4, "CPDAO", "getClaimLimitDtls", "Printing the HashMap :" + map);
		Log.log(4, "CPDAO", "getClaimLimitDtls", "Exited");

		return map;
	}

	public Vector getFirstClmPerGaurSecDtls(String clmRefNumber) throws DatabaseException {
		Log.log(4, "CPDAO", "getFirstClmPerGaurSecDtls", "Entered");
		CallableStatement callableStmt = null;
		Connection conn = DBConnection.getConnection();
		ResultSet rs = null;
		HashMap sanctionMap = new HashMap();
		HashMap npaMap = new HashMap();
		HashMap clmLodgementMap = new HashMap();
		Vector dtls = new Vector();
		double guarantorNetWorth = 0.0D;

		String securityId = null;
		String particular = null;
		double value = 0.0D;
		double amntThruSecurity = 0.0D;
		String reasonForReduction = null;
		String particularFlag = null;

		int status = -1;
		String errorCode = null;
		try {
			callableStmt = conn.prepareCall("{? = call packGetClmSecurityDetails.funcGetClmSecurityDetails(?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			callableStmt.setString(2, clmRefNumber);
			callableStmt.registerOutParameter(3, Constants.CURSOR);
			callableStmt.registerOutParameter(4, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(4);
			if (status == 1) {
				Log.log(2, "CPDAO", "getFirstClmPerGaurSecDtls", "SP returns a 1. Code is :" + errorCode);
				callableStmt.close();
				throw new DatabaseException(errorCode);
			}
			rs = (ResultSet) callableStmt.getObject(3);
			int count = 0;
			while (rs.next()) {
				securityId = rs.getString(1);
				particularFlag = rs.getString(2);
				guarantorNetWorth = rs.getDouble(3);
				if (securityId != null) {
					particular = rs.getString(4);
					value = rs.getDouble(5);
					amntThruSecurity = rs.getDouble(6);
					reasonForReduction = rs.getString(7);
					if ((particularFlag != null) && (particularFlag.equals("SAN"))) {
						sanctionMap.put("SecurityId", securityId);
						if ((particular != null) && (particular.equals("LAND"))) {
							sanctionMap.put("LAND", new Double(value));
						}
						if ((particular != null) && (particular.equals("BUILDING"))) {
							sanctionMap.put("BUILDING", new Double(value));
						}
						if ((particular != null) && (particular.equals("MACHINE"))) {
							sanctionMap.put("MACHINE", new Double(value));
						}
						if ((particular != null) && (particular.equals("OTHER FIXED MOVABLE ASSETS"))) {
							sanctionMap.put("OTHER FIXED MOVABLE ASSETS", new Double(value));
						}
						if ((particular != null) && (particular.equals("CURRENT ASSETS"))) {
							sanctionMap.put("CURRENT ASSETS", new Double(value));
						}
						if ((particular != null) && (particular.equals("OTHERS"))) {
							sanctionMap.put("OTHERS", new Double(value));
						}
						sanctionMap.put("AmntRealizedThruSecurity", new Double(amntThruSecurity));
						sanctionMap.put("reasonReduction", reasonForReduction);
						sanctionMap.put("networth", new Double(guarantorNetWorth));
						sanctionMap.put("SecurityParticularFlag", "SAN");
					}
					if ((particularFlag != null) && (particularFlag.equals("NPA"))) {
						npaMap.put("SecurityId", securityId);
						if ((particular != null) && (particular.equals("LAND"))) {
							npaMap.put("LAND", new Double(value));
						}
						if ((particular != null) && (particular.equals("BUILDING"))) {
							npaMap.put("BUILDING", new Double(value));
						}
						if ((particular != null) && (particular.equals("MACHINE"))) {
							npaMap.put("MACHINE", new Double(value));
						}
						if ((particular != null) && (particular.equals("OTHER FIXED MOVABLE ASSETS"))) {
							npaMap.put("OTHER FIXED MOVABLE ASSETS", new Double(value));
						}
						if ((particular != null) && (particular.equals("CURRENT ASSETS"))) {
							npaMap.put("CURRENT ASSETS", new Double(value));
						}
						if ((particular != null) && (particular.equals("OTHERS"))) {
							npaMap.put("OTHERS", new Double(value));
						}
						npaMap.put("AmntRealizedThruSecurity", new Double(amntThruSecurity));
						npaMap.put("reasonReduction", reasonForReduction);
						npaMap.put("networth", new Double(guarantorNetWorth));
						npaMap.put("SecurityParticularFlag", "NPA");
					}
					if ((particularFlag != null) && (particularFlag.equals("CLM"))) {
						clmLodgementMap.put("SecurityId", securityId);
						if ((particular != null) && (particular.equals("LAND"))) {
							clmLodgementMap.put("LAND", new Double(value));
						}
						if ((particular != null) && (particular.equals("BUILDING"))) {
							clmLodgementMap.put("BUILDING", new Double(value));
						}
						if ((particular != null) && (particular.equals("MACHINE"))) {
							clmLodgementMap.put("MACHINE", new Double(value));
						}
						if ((particular != null) && (particular.equals("OTHER FIXED MOVABLE ASSETS"))) {
							clmLodgementMap.put("OTHER FIXED MOVABLE ASSETS", new Double(value));
						}
						if ((particular != null) && (particular.equals("CURRENT ASSETS"))) {
							clmLodgementMap.put("CURRENT ASSETS", new Double(value));
						}
						if ((particular != null) && (particular.equals("OTHERS"))) {
							clmLodgementMap.put("OTHERS", new Double(value));
						}
						clmLodgementMap.put("AmntRealizedThruSecurity", new Double(amntThruSecurity));
						clmLodgementMap.put("reasonReduction", reasonForReduction);
						clmLodgementMap.put("networth", new Double(guarantorNetWorth));
						clmLodgementMap.put("SecurityParticularFlag", "CLM");
					}
					count++;
				}
			}
			rs.close();
			rs = null;

			dtls.addElement(sanctionMap);
			dtls.addElement(npaMap);
			dtls.addElement(clmLodgementMap);
		} catch (SQLException sqlex) {
			throw new DatabaseException(sqlex.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(4, "CPDAO", "getClaimLimitDtls", "Size of the PerGaurDetails Vector :" + dtls.size());
		Log.log(4, "CPDAO", "getClaimLimitDtls", "Exited");

		return dtls;
	}

	public Vector getCGPANSForBid(String bid) throws DatabaseException {
		Log.log(4, "CPDAO", "getCGPANSForBid", "Entered");
		CallableStatement callableStmt = null;
		Connection conn = DBConnection.getConnection();
		ResultSet rs = null;
		Vector cgpans = new Vector();

		int status = -1;
		String errorCode = null;
		try {
			callableStmt = conn.prepareCall("{? = call packGetCGPANforBIDBName.funcGetCGPANforBID(?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			callableStmt.setString(2, bid);
			callableStmt.registerOutParameter(3, Constants.CURSOR);
			callableStmt.registerOutParameter(4, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(4);
			if (status == 1) {
				Log.log(2, "CPDAO", "getCGPANSForBid", "SP returns a 1. Code is :" + errorCode);
				callableStmt.close();
				throw new DatabaseException(errorCode);
			}
			rs = (ResultSet) callableStmt.getObject(3);
			String cgpan = null;
			while (rs.next()) {
				cgpan = rs.getString(1);
				if (cgpan != null) {
					if (!cgpans.contains(cgpan)) {
						cgpans.addElement(cgpan);
					}
				}
			}
			rs.close();
			rs = null;

			callableStmt.close();
			callableStmt = null;
		} catch (SQLException sqlex) {
			throw new DatabaseException(sqlex.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(4, "CPDAO", "getCGPANSForBid", "Exited");

		return cgpans;
	}

	public void saveITPANDetail(String borrowerId, String itpan) throws DatabaseException {
		Log.log(4, "CPDAO", "saveITPANDetail()", "Entered!");
		CallableStatement callableStmt = null;
		Connection conn = null;

		int status = -1;
		String errorCode = null;

		String voucherid = null;
		String flag = null;
		try {
			conn = DBConnection.getConnection();

			callableStmt = conn.prepareCall("{? = call funcInsertITPANDtl(?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			callableStmt.setString(2, borrowerId);
			callableStmt.setString(3, itpan);
			callableStmt.registerOutParameter(4, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(4);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveITPANDetail()",
						"SP returns a 1 in saving ITPAN Details. Error code is :" + errorCode);

				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					Log.log(2, "CPDAO", "saveITPANDetail()", "Error :" + sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			callableStmt = null;
		} catch (SQLException sqlexception) {
			Log.log(2, "CPDAO", "saveITPANDetail()", "Error saving ITPAN Details into the database.");
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(4, "CPDAO", "saveITPANDetail()", "Exited!");
	}

	/*public void updateRecallAndLegalAttachments(ClaimApplication claimApplication, String claimRefNumber,
			boolean internetUser) throws DatabaseException {

		Log.log(4, "CPDAO", "insertRecallAndLegalAttachments()", "Entered");
		// System.out.println("insertRecallAndLegalAttachments called");
		Connection connection = DBConnection.getConnection(false);
		try {

			String tableName = "CLAIM_FILES";

			if (internetUser) {
				tableName = "CLAIM_FILES_TEMP";
			}
			Statement statement = connection.createStatement();

			if ((claimApplication.getLegalDetailsFileName() != null)) {

				PreparedStatement statement1 = connection.prepareStatement("update " + tableName
						+ " set CFT_LEGAL_FILE_NAME= ? , CFT_LEGAL_PROCEED_FILE =? where CLM_REF_NO= ?");
				statement1.setString(1, claimApplication.getLegalDetailsFileName());
				statement1.setBytes(2, claimApplication.getLegalDetailsFileData());
				statement1.setString(3, claimRefNumber);
				statement1.executeUpdate();

				
				 * statement.executeUpdate("UPDATE "+tableName+" set STAFF_AC_FILE_NAME= '"
				 * +claimApplication.getStaffDetailsFileName()+
				 * "' , STAFF_AC_FILE='' where CLM_REF_NO = '"+clmRefNumber+"'");
				 

				// System.out.println("getLegalDetailsFileName===is=="+claimApplication.getLegalDetailsFileName());

				statement = connection.createStatement();
				ResultSet resutls = statement.executeQuery("select CFT_LEGAL_PROCEED_FILE from " + tableName
						+ " where CLM_REF_NO= '" + claimRefNumber + "'" + " for update ");

				// System.out.println("select CFT_LEGAL_PROCEED_FILE from "+ tableName + " where
				// CLM_REF_NO= '"+ claimRefNumber + "'" + " for update ");

				// System.out.println("staff data
				// file==="+claimApplication.getLegalDetailsFileName());

				while (resutls.next()) {
					BLOB blob = ((OracleResultSet) resutls).getBLOB(1);
					OutputStream outputStream = blob.getBinaryOutputStream();
					outputStream.write(claimApplication.getLegalDetailsFileData());
					outputStream.close();

				}

				resutls.close();
				statement.close();
			} else if ((claimApplication.getLegalDetailsFileName() != null)) {

				PreparedStatement statement1 = connection.prepareStatement("update " + tableName
						+ " set CFT_LEGAL_FILE_NAME= ? , CFT_LEGAL_PROCEED_FILE =? where CLM_REF_NO= ?");
				statement1.setString(1, claimApplication.getLegalDetailsFileName());
				statement1.setBytes(2, claimApplication.getLegalDetailsFileData());
				statement1.setString(3, claimRefNumber);
				statement1.executeUpdate();

				statement = connection.createStatement();
				ResultSet resutls = statement.executeQuery("select CFT_LEGAL_PROCEED_FILE from " + tableName
						+ " where CLM_REF_NO= '" + claimRefNumber + "'" + " for update ");

				while (resutls.next()) {
					BLOB blob = ((OracleResultSet) resutls).getBLOB(1);
					OutputStream outputStream = blob.getBinaryOutputStream();
					outputStream.write(claimApplication.getLegalDetailsFileData());
					outputStream.close();
				}

				resutls.close();
				statement.close();
			} else if ((claimApplication.getLegalDetailsFileName() == null)) {

				PreparedStatement statement1 = connection.prepareStatement("update " + tableName
						+ " set CFT_LEGAL_FILE_NAME= ? , CFT_LEGAL_PROCEED_FILE =? where CLM_REF_NO= ?");
				statement1.setString(1, claimApplication.getLegalDetailsFileName());
				statement1.setBytes(2, claimApplication.getLegalDetailsFileData());
				statement1.setString(3, claimRefNumber);
				statement1.executeUpdate();

				statement = connection.createStatement();
				ResultSet resutls = statement.executeQuery("select CFT_LEGAL_PROCEED_FILE from " + tableName
						+ " where CLM_REF_NO= '" + claimRefNumber + "'" + " for update ");

				// System.out.println("line no===8649=="+"select CFT_LEGAL_PROCEED_FILE from " +
				// tableName + " where CLM_REF_NO= '"+ claimRefNumber + "'" + " for update ");

				while (resutls.next()) {
					BLOB blob = ((OracleResultSet) resutls).getBLOB(1);
					OutputStream outputStream = blob.getBinaryOutputStream();
					outputStream.write(claimApplication.getLegalDetailsFileData());
					outputStream.close();

				}

				resutls.close();
				statement.close();
			} else {
				Log.log(5, "CPDAO", "insertStaffAttachment()",
						"No Attachments are available! We should not come here at all...");
			}
			if (!internetUser) {
				CallableStatement callable = connection.prepareCall("{?=call funcInsClmfiles(?,?)}");

				callable.registerOutParameter(1, Types.INTEGER);
				callable.registerOutParameter(3, Types.VARCHAR);
				callable.setString(2, claimRefNumber);
				callable.execute();
				int errorCode = callable.getInt(1);
				String error = callable.getString(3);

				if (errorCode == 1) {
					connection.rollback();
					callable.close();
					callable = null;
					Log.log(2, "CPDAO", "insertRecallAndLegalAttachments()", "Error " + error);

					throw new DatabaseException("Unable to Store Attachments ");
				}

				callable.close();
				callable = null;
			}

			connection.commit();

		} catch (SQLException e) {
			Log.log(2, "CPDAO", "insertRecallAndLegalAttachments()", "Error " + e.getMessage());
			System.out.println("insertRecallAndLegalAttachments error =" + e.getMessage());
			Log.logException(e);
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} catch (IOException e) {
			Log.log(2, "CPDAO", "insertRecallAndLegalAttachments()", "Error " + e.getMessage());
			System.out.println("insertRecallAndLegalAttachments error IOException =" + e.getMessage());
			Log.logException(e);
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(4, "CPDAO", "insertRecallAndLegalAttachments()", "Exited");
	}
*/
//end rajuk

	//Commented by AbhishekSikarwar
/*	public void insertRecallAndLegalAttachments(ClaimApplication claimApplication, String claimRefNumber,
			boolean internetUser) throws DatabaseException {

		Log.log(4, "CPDAO", "insertRecallAndLegalAttachments()", "Entered");

		Connection connection = DBConnection.getConnection(false);
		try {
			Log.log(5, "CPDAO", "insertRecallAndLegalAttachments()", "claimRefNumber " + claimRefNumber);
			String tableName = "CLAIM_FILES";

			if (internetUser) {
				tableName = "CLAIM_FILES_TEMP";
			}
			Statement statement = connection.createStatement();

			Log.log(5, "CPDAO", "insertRecallAndLegalAttachments()", "Legal Detail and Recall Detail File Names are  "
					+ claimApplication.getLegalDetailsFileName() + "," + claimApplication.getRecalNoticeFileName());

			if ((claimApplication.getLegalDetailsFileName() != null)
					&& (claimApplication.getRecalNoticeFileName() == null)) {

				Log.log(5, "CPDAO", "insertRecallAndLegalAttachments()", "Both Attachments are available ");

				statement.execute(
						"INSERT INTO " + tableName + " (CLM_REF_NO," + "CFT_RECALL_NOTICE_FILE,CFT_RECALL_FILE_NAME,"
								+ "CFT_LEGAL_PROCEED_FILE,CFT_LEGAL_FILE_NAME) VALUES ('" + claimRefNumber + "',"
								+ "empty_blob(),'" + claimApplication.getRecalNoticeFileName() + "'" + ",empty_blob(),'"
								+ claimApplication.getLegalDetailsFileName() + "')");

				statement = connection.createStatement();
				ResultSet resutls = statement.executeQuery("select CFT_RECALL_NOTICE_FILE,CFT_LEGAL_PROCEED_FILE from "
						+ tableName + " where CLM_REF_NO= '" + claimRefNumber + "'" + " for update ");

				while (resutls.next()) {
					BLOB blob = ((OracleResultSet) resutls).getBLOB(1);
					OutputStream outputStream = blob.getBinaryOutputStream();
					// outputStream.write(claimApplication.getRecallNoticeFileData());
					outputStream.close();

					blob = ((OracleResultSet) resutls).getBLOB(2);
					outputStream = blob.getBinaryOutputStream();
					outputStream.write(claimApplication.getLegalDetailsFileData());
					outputStream.close();
				}

				resutls.close();
				statement.close();
			} else if ((claimApplication.getLegalDetailsFileName() != null)
					&& (claimApplication.getRecalNoticeFileName() != null)) {

				Log.log(5, "CPDAO", "insertRecallAndLegalAttachments()",
						"Only Legal Details attachments are available  ");

				statement.execute("INSERT INTO " + tableName + " (CLM_REF_NO,"
						+ "CFT_RECALL_NOTICE_FILE,CFT_RECALL_FILE_NAME," + ") VALUES ('" + claimRefNumber + "',"
						+ "empty_blob(),'" + claimApplication.getRecalNoticeFileName() + "'" + ")");

				statement = connection.createStatement();
				ResultSet resutls = statement.executeQuery("select CFT_LEGAL_PROCEED_FILE from " + tableName
						+ " where CLM_REF_NO= '" + claimRefNumber + "'" + " for update ");

				while (resutls.next()) {
					BLOB blob = ((OracleResultSet) resutls).getBLOB(1);
					OutputStream outputStream = blob.getBinaryOutputStream();
					outputStream.write(claimApplication.getLegalDetailsFileData());
					outputStream.close();
				}

				resutls.close();
				statement.close();
			} else if ((claimApplication.getLegalDetailsFileName() == null)
					&& (claimApplication.getRecalNoticeFileName() != null)) {
				Log.log(5, "CPDAO", "insertRecallAndLegalAttachments()",
						"Only Recall Details attachments are available  ");

				statement.execute("INSERT INTO " + tableName + " (CLM_REF_NO,"
						+ "CFT_LEGAL_PROCEED_FILE,CFT_LEGAL_FILE_NAME) VALUES ('" + claimRefNumber + "',"
						+ ",empty_blob(),'" + claimApplication.getLegalDetailsFileName() + "')");

				statement = connection.createStatement();
				ResultSet resutls = statement.executeQuery("select CFT_RECALL_NOTICE_FILE from " + tableName
						+ " where CLM_REF_NO= '" + claimRefNumber + "'" + " for update ");

				while (resutls.next()) {
					BLOB blob = ((OracleResultSet) resutls).getBLOB(1);
					OutputStream outputStream = blob.getBinaryOutputStream();
					outputStream.write(claimApplication.getRecallNoticeFileData());
					outputStream.close();
				}

				resutls.close();
				statement.close();
			} else {
				Log.log(5, "CPDAO", "insertRecallAndLegalAttachments()",
						"No Attachments are available! We should not come here at all...");
			}
			if (!internetUser) {
				CallableStatement callable = connection.prepareCall("{?=call funcInsClmfiles(?,?)}");

				callable.registerOutParameter(1, Types.INTEGER);
				callable.registerOutParameter(3, Types.VARCHAR);
				callable.setString(2, claimRefNumber);
				callable.execute();
				int errorCode = callable.getInt(1);
				String error = callable.getString(3);

				Log.log(5, "CPDAO", "insertRecallAndLegalAttachments()",
						"errorCode and error are " + errorCode + "," + error);

				if (errorCode == 1) {
					connection.rollback();
					callable.close();
					callable = null;
					Log.log(2, "CPDAO", "insertRecallAndLegalAttachments()", "Error " + error);

					throw new DatabaseException("Unable to Store Attachments ");
				}

				callable.close();
				callable = null;
			}

			connection.commit();

		} catch (SQLException e) {
			Log.log(2, "CPDAO", "insertRecallAndLegalAttachments()", "Error " + e.getMessage());
			Log.logException(e);
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} catch (IOException e) {
			Log.log(2, "CPDAO", "insertRecallAndLegalAttachments()", "Error " + e.getMessage());
			Log.logException(e);
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(4, "CPDAO", "insertRecallAndLegalAttachments()", "Exited");
	}*/

	public HashMap getWorkingCapital(String cgpan) throws DatabaseException {
		Log.log(4, "CPDAODAO", "getWorkingCapital", "Entered.");
		HashMap wcDetails = new HashMap();
		Connection connection = DBConnection.getConnection(false);
		int wcTenure = -1;
		java.util.Date wcGuarStartDt = null;
		try {
			CallableStatement wcObj = connection.prepareCall("{?=call funcGetWCTenureforCGPAN(?,?,?,?)}");

			wcObj.setString(2, cgpan);

			wcObj.registerOutParameter(1, Types.INTEGER);
			wcObj.registerOutParameter(5, Types.VARCHAR);
			wcObj.registerOutParameter(4, Types.INTEGER);
			wcObj.registerOutParameter(3, 91);

			wcObj.executeQuery();
			int wcObjValue = wcObj.getInt(1);
			String errorCode = wcObj.getString(5);

			if (wcObjValue == 1) {
				Log.log(2, "CPDAO", "getWorkingCapital()",
						"SP returns a 1 in fetching WC Details. Error code is :" + errorCode);

				wcObj.close();
				wcObj = null;
				throw new DatabaseException(errorCode);
			}
			wcTenure = wcObj.getInt(4);
			wcGuarStartDt = wcObj.getDate(3);
			wcDetails.put("WC_TENURE", new Integer(wcTenure));
			wcDetails.put("GUARANTEESTARTDT", wcGuarStartDt);
			wcObj.close();
			wcObj = null;
		} catch (SQLException sqlexception) {
			Log.log(2, "CPDAO", "getWorkingCapital()", "Error in fetching WC Details");
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(4, "CPDAO", "getWorkingCapital", "Exited");

		return wcDetails;
	}

	public ArrayList getAllCgpansForClmRefNum(String clmRefNum) throws DatabaseException {
		Log.log(4, "CPDAO", "getAllCgpansForClmRefNum", "entered");
		Connection connection = DBConnection.getConnection();

		ArrayList cgpanList = new ArrayList();
		try {
			CallableStatement allCgpans = connection
					.prepareCall("{?=call packGetAllCgpansForClmRefNum.funcGetAllCgpansForClmRef(?,?,?)}");

			allCgpans.registerOutParameter(1, Types.INTEGER);
			allCgpans.setString(2, clmRefNum);
			allCgpans.registerOutParameter(3, Constants.CURSOR);
			allCgpans.registerOutParameter(4, Types.VARCHAR);

			allCgpans.execute();

			int functionReturnValue = allCgpans.getInt(1);

			if (functionReturnValue == 1) {
				String error = allCgpans.getString(3);

				allCgpans.close();
				allCgpans = null;

				throw new DatabaseException(error);
			}

			ResultSet allCgpansResult = (ResultSet) allCgpans.getObject(3);

			while (allCgpansResult.next()) {
				String cgpan = allCgpansResult.getString(1);

				cgpanList.add(cgpan);
			}
			allCgpansResult.close();
			allCgpansResult = null;
			allCgpans.close();
			allCgpans = null;
		} catch (SQLException sqlException) {
			Log.log(4, "CPDAO", "getAllCgpansForClmRefNum", sqlException.getMessage());
			Log.logException(sqlException);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(4, "CPDAO", "getAllCgpansForClmRefNum", ignore.getMessage());
			}
			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(4, "CPDAO", "getAllCgpansForClmRefNum", "Exited");

		return cgpanList;
	}

	private ArrayList getDisbursementDetails(String id, int type) throws DatabaseException {
		Log.log(4, "CPDAO", "getDisbursementDetails", "Entered");

		ArrayList periodicInfos = new ArrayList();

		Connection connection = DBConnection.getConnection();

		CallableStatement getDisbursementDetailStmt = null;

		ResultSet resultSet = null;
		try {
			String exception = "";

			String functionName = null;

			if (type == 0) {
				functionName = "{?=call packGetCPDtlsforDBR.funcGetCPDtlsForBid(?,?,?)}";
			}

			getDisbursementDetailStmt = connection.prepareCall(functionName);
			getDisbursementDetailStmt.registerOutParameter(1, Types.INTEGER);
			getDisbursementDetailStmt.setString(2, id);
			getDisbursementDetailStmt.registerOutParameter(3, Constants.CURSOR);
			getDisbursementDetailStmt.registerOutParameter(4, Types.VARCHAR);

			getDisbursementDetailStmt.executeQuery();

			exception = getDisbursementDetailStmt.getString(4);

			int error = getDisbursementDetailStmt.getInt(1);

			if (error == 1) {
				getDisbursementDetailStmt.close();
				getDisbursementDetailStmt = null;
				Log.log(5, "CPDAO", "getDisbursementDetails", "Exception :" + exception);
				connection.rollback();
				throw new DatabaseException(exception);
			}
			resultSet = (ResultSet) getDisbursementDetailStmt.getObject(3);

			PeriodicInfo periodicInfo = new PeriodicInfo();
			Disbursement disbursement = null;
			ArrayList listOfDisbursement = new ArrayList();
			boolean firstTime = true;

			while (resultSet.next()) {
				disbursement = new Disbursement();

				if (firstTime) {
					periodicInfo.setBorrowerId(resultSet.getString(1));
					periodicInfo.setBorrowerName(resultSet.getString(4));
					firstTime = false;
				}

				disbursement.setCgpan(resultSet.getString(2));

				disbursement.setScheme(resultSet.getString(3));

				disbursement.setSanctionedAmount(resultSet.getDouble(5));

				listOfDisbursement.add(disbursement);
			}

			resultSet.close();
			resultSet = null;

			getDisbursementDetailStmt.close();
			getDisbursementDetailStmt = null;

			String cgpan = null;

			int disbSize = listOfDisbursement.size();
			functionName = "{?=call packGetCPPIDBRDtlsCGPAN.funcCPDBRDetailsForCGPAN(?,?,?)}";
			getDisbursementDetailStmt = connection.prepareCall(functionName);
			for (int i = 0; i < disbSize; i++) {
				ArrayList listOfDisbursementAmount = new ArrayList();
				disbursement = (Disbursement) listOfDisbursement.get(i);
				cgpan = disbursement.getCgpan();
				if (cgpan != null) {
					Log.log(5, "CPDAO", "getDisbursementDetails", "Cgpan" + cgpan);
					getDisbursementDetailStmt.registerOutParameter(1, Types.INTEGER);
					getDisbursementDetailStmt.setString(2, cgpan);

					getDisbursementDetailStmt.registerOutParameter(3, Constants.CURSOR);
					getDisbursementDetailStmt.registerOutParameter(4, Types.VARCHAR);

					getDisbursementDetailStmt.execute();

					exception = getDisbursementDetailStmt.getString(4);

					error = getDisbursementDetailStmt.getInt(1);
					if (error == 1) {
						getDisbursementDetailStmt.close();
						getDisbursementDetailStmt = null;
						Log.log(2, "CPDAO", "getDisbursementDetails", "Exception" + exception);
						connection.rollback();
						throw new DatabaseException(exception);
					}
					resultSet = (ResultSet) getDisbursementDetailStmt.getObject(3);
					DisbursementAmount disbursementAmount = null;
					while (resultSet.next()) {
						disbursementAmount = new DisbursementAmount();

						disbursementAmount.setCgpan(cgpan);

						disbursementAmount.setDisbursementId(resultSet.getString(1));
						Log.log(5, "CPDAO", "getDisbursementDetails",
								"disb Id" + disbursementAmount.getDisbursementId());

						disbursementAmount.setDisbursementAmount(resultSet.getDouble(2));
						Log.log(5, "CPDAO", "getDisbursementDetails",
								"disb Amt" + disbursementAmount.getDisbursementAmount());

						disbursementAmount.setDisbursementDate(DateHelper.sqlToUtilDate(resultSet.getDate(3)));
						Log.log(5, "CPDAO", "getDisbursementDetails",
								"disb date" + disbursementAmount.getDisbursementDate());

						disbursementAmount.setFinalDisbursement(resultSet.getString(4));
						Log.log(5, "CPDAO", "getDisbursementDetails",
								"Fin disb " + disbursementAmount.getFinalDisbursement());

						listOfDisbursementAmount.add(disbursementAmount);
					}

					disbursement.setDisbursementAmounts(listOfDisbursementAmount);
					resultSet.close();
					resultSet = null;
				}
			}

			getDisbursementDetailStmt.close();
			getDisbursementDetailStmt = null;

			periodicInfo.setDisbursementDetails(listOfDisbursement);
			periodicInfos.add(periodicInfo);

			connection.commit();
		} catch (Exception exception) {
			Log.logException(exception);
			try {
				connection.rollback();
			} catch (SQLException localSQLException) {
			}
			throw new DatabaseException(exception.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(4, "CPDAO", "get disbursementDetails", "Exited");

		return periodicInfos;
	}

	private ArrayList getCPRepaymentDetails(String id, int type) throws DatabaseException {
		Log.log(4, "CPDAO", "get Repayment Details", "Entered");

		ArrayList periodicInfos = new ArrayList();

		Connection connection = DBConnection.getConnection();
		CallableStatement getRepaymentDetailStmt = null;
		ResultSet resultSet = null;
		try {
			String exception = "";

			String functionName = "{?=call packGetDtlsforRepayment.funcGetDtlsforCGPAN(?,?,?)}";

			getRepaymentDetailStmt = connection.prepareCall(functionName);
			getRepaymentDetailStmt.registerOutParameter(1, Types.INTEGER);
			getRepaymentDetailStmt.setString(2, id);
			getRepaymentDetailStmt.registerOutParameter(3, Constants.CURSOR);
			getRepaymentDetailStmt.registerOutParameter(4, Types.VARCHAR);

			getRepaymentDetailStmt.executeQuery();

			exception = getRepaymentDetailStmt.getString(4);
			Log.log(5, "CPDAO", "repayment detail", "exception " + exception);
			int error = getRepaymentDetailStmt.getInt(1);
			Log.log(5, "CPDAO", "repayment detail", "errorCode " + error);
			if (error == 1) {
				getRepaymentDetailStmt.close();
				getRepaymentDetailStmt = null;
				connection.rollback();
				Log.log(2, "CPDAO", "getRepaymentdetail", "error in SP " + exception);
				throw new DatabaseException(exception);
			}
			Log.log(5, "CPDAO", "getRepaymentdetail", "Before ResultSet assign");
			resultSet = (ResultSet) getRepaymentDetailStmt.getObject(3);
			Log.log(5, "CPDAO", "getRepaymentdetail", "resultSet assigned");

			PeriodicInfo periodicInfo = new PeriodicInfo();
			Repayment repayment = null;

			ArrayList listOfRepayment = new ArrayList();

			boolean firstTime = true;
			String tcCgpan = null;
			int len = 0;
			String applType = null;

			while (resultSet.next()) {
				Log.log(5, "CPDAO", "getRepaymentdetail", "Inside ResultSet");
				repayment = new Repayment();
				tcCgpan = resultSet.getString(2);
				len = tcCgpan.length();
				applType = tcCgpan.substring(len - 2, len - 1);
				if (applType.equalsIgnoreCase("T")) {
					if (firstTime) {
						periodicInfo.setBorrowerId(resultSet.getString(1));
						Log.log(5, "getRepaymentDetails for Borrower", "Borrower ID",
								" : " + periodicInfo.getBorrowerId());

						periodicInfo.setBorrowerName(resultSet.getString(3));
						Log.log(5, "getRepaymentDetailsfor Borrower:", "Borrower Name",
								" : " + periodicInfo.getBorrowerName());
						firstTime = false;
					}

					repayment.setCgpan(tcCgpan);
					Log.log(5, "getRepaymentDetailsfor Borrower:", "CGPAN ", ": " + repayment.getCgpan());
					repayment.setScheme(resultSet.getString(4));
					Log.log(5, "getRepaymentDetailsfor Borrower:", "Scheme", " : " + repayment.getScheme());

					listOfRepayment.add(repayment);
				}
			}

			Log.log(5, "getRepaymentDetails for Borrower:", "size of RepaymentObj", " : " + listOfRepayment.size());

			resultSet.close();
			resultSet = null;

			getRepaymentDetailStmt = null;

			functionName = "{?=call packGetRepaymentDtls.funcGetRepaymentDtl(?,?,?)}";
			getRepaymentDetailStmt = connection.prepareCall(functionName);

			String cgpan = "";
			int size = listOfRepayment.size();
			for (int i = 0; i < size; i++) {
				ArrayList listOfRepaymentAmount = new ArrayList();
				repayment = (Repayment) listOfRepayment.get(i);
				cgpan = repayment.getCgpan();
				Log.log(5, "getRepaymentDetails for cgpan:", "cgpan", " : " + i + " " + cgpan);
				getRepaymentDetailStmt.registerOutParameter(1, Types.INTEGER);
				getRepaymentDetailStmt.setString(2, cgpan);

				getRepaymentDetailStmt.registerOutParameter(3, Constants.CURSOR);
				getRepaymentDetailStmt.registerOutParameter(4, Types.VARCHAR);

				getRepaymentDetailStmt.execute();

				exception = getRepaymentDetailStmt.getString(4);

				error = getRepaymentDetailStmt.getInt(1);
				if (error == 1) {
					getRepaymentDetailStmt.close();
					getRepaymentDetailStmt = null;
					Log.log(2, "getRepaymentDetails for cgpan:", "Exception ", exception);
					connection.rollback();
					throw new DatabaseException(exception);
				}
				resultSet = (ResultSet) getRepaymentDetailStmt.getObject(3);
				RepaymentAmount repaymentAmount = null;
				while (resultSet.next()) {
					repaymentAmount = new RepaymentAmount();

					repaymentAmount.setCgpan(cgpan);
					Log.log(5, "CPDAO", "RepaymentAmount", "Cgpan " + cgpan);

					repaymentAmount.setRepayId(resultSet.getString(1));
					Log.log(5, "CPDAO", "RepaymentAmount", "RepaymentId " + repaymentAmount.getRepayId());

					repaymentAmount.setRepaymentAmount(resultSet.getDouble(2));
					Log.log(5, "rep Amt: ", "rpAmount", "--" + repaymentAmount.getRepaymentAmount());

					repaymentAmount.setRepaymentDate(resultSet.getDate(3));
					Log.log(5, "rep date:", "date", " " + repaymentAmount.getRepaymentDate());

					listOfRepaymentAmount.add(repaymentAmount);
					Log.log(5, "************", "***********", "****************");
				}
				repayment.setRepaymentAmounts(listOfRepaymentAmount);
				resultSet.close();
				resultSet = null;
			}
			periodicInfo.setRepaymentDetails(listOfRepayment);

			periodicInfos.add(periodicInfo);

			getRepaymentDetailStmt.close();
			getRepaymentDetailStmt = null;

			connection.commit();
		} catch (Exception exception) {
			Log.logException(exception);
			try {
				connection.rollback();
			} catch (SQLException localSQLException) {
			}
			throw new DatabaseException(exception.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(4, "CPDAO", "get Repayment Details", "Exited");

		return periodicInfos;
	}

	public String getClaimRefNo(String cgpan) throws DatabaseException {
		Log.log(Log.INFO, "CPDAO", "getClaimRefNo", "Entered");

		String claimRefNo = null;
		PreparedStatement pStmt = null;
		ArrayList aList = new ArrayList();
		ResultSet rsSet = null;
		Connection connection = DBConnection.getConnection();
		try {
			String query = "SELECT UNIQUE CLM_REF_NO FROM CLAIM_APPLICATION_AMOUNT_TEMP WHERE CGPAN = ?";
			pStmt = connection.prepareStatement(query);
			pStmt.setString(1, cgpan);
			rsSet = pStmt.executeQuery();
			while (rsSet.next()) {
				claimRefNo = rsSet.getString(1);
			}
			rsSet.close();
			pStmt.close();

		} catch (Exception exception) {
			Log.logException(exception);
			throw new DatabaseException(exception.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}
		return claimRefNo;
	}

	public void updateClaimApplicationStatus(String claimRefNo, String cgpan, String userId) throws DatabaseException {

		Connection connection = null;
		CallableStatement stmt = null;

		try {
			connection = DBConnection.getConnection();

			stmt = connection.prepareCall("{?=call Funcchangeclaimstatus(?,?,?,?)}");
			stmt.registerOutParameter(1, java.sql.Types.INTEGER);
			if (!(claimRefNo == null || claimRefNo.equals(""))) {
				stmt.setString(2, claimRefNo);
			} else {
				stmt.setNull(2, Types.VARCHAR);
			}

			if (!(cgpan == null || cgpan.equals(""))) {
				stmt.setString(3, cgpan);
			} else {
				stmt.setNull(3, Types.VARCHAR);
			}

			stmt.setString(4, userId);
			stmt.registerOutParameter(5, java.sql.Types.VARCHAR);
			stmt.execute();

			int status = stmt.getInt(1);
			// System.out.println("status:"+status);
			if (status == Constants.FUNCTION_FAILURE) {
				String err = stmt.getString(5);
				// System.out.println("error:" + err);
				stmt.close();
				stmt = null;
				throw new DatabaseException(err);
			}
			stmt.close();
			stmt = null;

			Log.log(Log.INFO, "CPDAO", "updateClaimApplicationStatus", "funcCancAllocation executed successfully");
		} catch (SQLException exp) {
			// System.out.println("sql exception " +exp.getMessage());
			Log.log(Log.INFO, "CPDAO", "updateClaimApplicationStatus", "sql exception " + exp.getMessage());
			throw new DatabaseException(exp.getMessage());
		}
	}

	public Vector getClaimApprovalDetails(String loggedUsr, String bankName) throws DatabaseException {
		Log.log(Log.INFO, "CPDAO", "getClaimApprovalDetails()", "Entered!");
		Vector claimDetails = new Vector();
		CallableStatement callableStmt = null;
		Connection conn = null;
		ResultSet resultset = null;
		ClaimDetail claimdetail = null;
		String flag = ClaimConstants.FIRST_INSTALLMENT;
		String cgclan = null;
		String bid = null;
		String memberId = null;
		String claimRefNumber = null;
		double claimApprovedAmnt = 0.0;
		double applicationApprovedAmnt = 0.0;
		double tcApprovedAmt = 0.0;
		double wcApprovedAmt = 0.0;
		double tcOutstanding = 0.0;
		double wcOutstanding = 0.0;
		double tcrecovery = 0.0;
		double wcrecovery = 0.0;
		double tcEligibleAmt = 0.0;
		double wcEligibleAmt = 0.0;
		double tcDeduction = 0.0;
		double wcDeduction = 0.0;
		double tcFirstInstallment = 0.0;
		double wcFirstInstallment = 0.0;
		java.util.Date clmApprvdDate = null;
		java.util.Date npaEffectiveDate = null;
		double outstandingAmntAsOnNPA = 0.0;
		double appliedClaimAmnt = 0.0;
		String clmStatus = null;
		String comments = null;
		String forwardedToUser = null;
		String unitName = null;
		int status = -1;
		String errorCode = null;

		try {
			conn = DBConnection.getConnection();
			callableStmt = conn.prepareCall("{? = call packGetClmAppForUser.funcGetClmAppForUser(?,?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt.setString(2, loggedUsr);
			callableStmt.setString(3, bankName);
			callableStmt.registerOutParameter(4, Constants.CURSOR);
			callableStmt.registerOutParameter(5, java.sql.Types.VARCHAR);
			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(5);
			if (status == Constants.FUNCTION_FAILURE) {
				Log.log(Log.ERROR, "CPDAO", "getClaimApprovalDetails()",
						"SP returns a 1 in retrieving Claim Processing Results.Error code is :" + errorCode);
				callableStmt.close();
				throw new DatabaseException(errorCode);
			} else if (status == Constants.FUNCTION_SUCCESS) {
				// extracting the resultset from the callable statement
				resultset = (ResultSet) callableStmt.getObject(4);

				while (resultset.next()) {
					// Extracting the values from the resultset
					memberId = resultset.getString(1);
					// System.out.println("Member Id:"+memberId);
					claimRefNumber = resultset.getString(2);
					// System.out.println("claimRefNumber:"+claimRefNumber);
					unitName = resultset.getString(3);
					bid = resultset.getString(4);
					tcApprovedAmt = resultset.getDouble(5);
					wcApprovedAmt = resultset.getDouble(6);
					tcOutstanding = resultset.getDouble(7);
					wcOutstanding = resultset.getDouble(8);
					tcrecovery = resultset.getDouble(9);
					wcrecovery = resultset.getDouble(10);
					tcEligibleAmt = resultset.getDouble(11);
					wcEligibleAmt = resultset.getDouble(12);
					tcDeduction = resultset.getDouble(13);
					wcDeduction = resultset.getDouble(14);
					tcFirstInstallment = resultset.getDouble(15);
					wcFirstInstallment = resultset.getDouble(16);
					comments = resultset.getString(17);
					if (flag.equals(ClaimConstants.FIRST_INSTALLMENT)) {
						// System.out.println("In CPDAO.java Flag is equals to Claim First
						// Installment");
						// Setting the values in ClaimDetail object
						claimdetail = new ClaimDetail();
						claimdetail.setMliId(memberId);
						claimdetail.setClaimRefNum(claimRefNumber);
						claimdetail.setSsiUnitName(unitName);
						claimdetail.setBorrowerId(bid);
						claimdetail.setTcApprovedAmt(tcApprovedAmt);
						claimdetail.setWcApprovedAmt(wcApprovedAmt);
						claimdetail.setTcOutstanding(tcOutstanding);
						claimdetail.setWcOutstanding(wcOutstanding);
						claimdetail.setTcrecovery(tcrecovery);
						claimdetail.setWcrecovery(wcrecovery);
						claimdetail.setTcClaimEligibleAmt(tcEligibleAmt);
						claimdetail.setWcClaimEligibleAmt(wcEligibleAmt);
						claimdetail.setAsfDeductableforTC(tcDeduction);
						claimdetail.setAsfDeductableforWC(wcDeduction);
						claimdetail.setTcFirstInstallment(tcFirstInstallment);
						claimdetail.setWcFirstInstallment(wcFirstInstallment);
						claimdetail.setComments(comments);
					}
					if (claimdetail != null) {
						// adding the claimdetail object to the vector
						claimDetails.addElement(claimdetail);
					}
				}
				resultset.close();
				resultset = null;
			}
			// closing the callable statement
			callableStmt.close();
			callableStmt = null;
		} catch (SQLException sqlexception) {
			Log.log(Log.ERROR, "CPDAO", "getClaimApprovalDetails()",
					"Error retrieving Details for Processing Claims from the database");
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(Log.INFO, "CPDAO", "getClaimApprovalDetails()", "Exited!");
		return claimDetails;
	}

	public int insertClaimTCProcessingDetails(String claimRefNo, String q1, String q2, String q3, String q4, String q5,
			String q6, String q7, String q8, String q9, String q10, String q11, String q12, String q13, String q14,
			String userId, java.util.Date systemDate, String ltrRefNo, String ltrDate) throws DatabaseException {
		Connection connection = null;
		int tempcount = 0;
		java.sql.Date sqlltrDate;

		CallableStatement insertClaimProcessDetails = null;

		boolean status = false;

		sqlltrDate = null;
		Date endDt = null;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			java.util.Date dt = formatter.parse(ltrDate);
			sqlltrDate = new java.sql.Date(dt.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// java.util.Date utilDate;
		// java.sql.Date sqlDate;

		try {
			connection = DBConnection.getConnection();
			insertClaimProcessDetails = connection
					.prepareCall("{?=call funcclmqueryinsert(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			insertClaimProcessDetails.registerOutParameter(1, Types.INTEGER);
			insertClaimProcessDetails.setString(2, claimRefNo);
			insertClaimProcessDetails.setString(3, q1);
			insertClaimProcessDetails.setString(4, q2);
			insertClaimProcessDetails.setString(5, q3);
			insertClaimProcessDetails.setString(6, q4);
			insertClaimProcessDetails.setString(7, q5);
			insertClaimProcessDetails.setString(8, q6);
			insertClaimProcessDetails.setString(9, q7);
			insertClaimProcessDetails.setString(10, q8);
			insertClaimProcessDetails.setString(11, q9);
			insertClaimProcessDetails.setString(12, q10);
			insertClaimProcessDetails.setString(13, q11);
			insertClaimProcessDetails.setString(14, q12);
			insertClaimProcessDetails.setString(15, q13);
			insertClaimProcessDetails.setString(16, q14);
			insertClaimProcessDetails.setString(17, userId);
			insertClaimProcessDetails.setDate(18, new java.sql.Date(systemDate.getTime()));
			insertClaimProcessDetails.setString(19, ltrRefNo);
			insertClaimProcessDetails.setDate(20, sqlltrDate);
			insertClaimProcessDetails.registerOutParameter(21, Types.VARCHAR);

			insertClaimProcessDetails.executeQuery();
			int returnValue = insertClaimProcessDetails.getInt(1);
			// System.out.println("Claim Ref No:"+claimRefNo+" Return Value:"+returnValue);
			if (returnValue == Constants.FUNCTION_FAILURE) {

				String error = insertClaimProcessDetails.getString(21);
				// System.out.println("Error:" + error);
				status = false;
				tempcount = 0;
				insertClaimProcessDetails.close();
				insertClaimProcessDetails = null;

				Log.log(Log.ERROR, "CPDAO", "insertClaimTCProcessingDetails", error);

				connection.rollback();

				throw new DatabaseException(insertClaimProcessDetails.getString(4));

			} else if (returnValue == Constants.FUNCTION_SUCCESS) {
				status = true;
				tempcount = 1;
				insertClaimProcessDetails.close();
				insertClaimProcessDetails = null;
			}
			// System.out.println("Temp Count: "+tempcount);
			connection.commit();
		} catch (Exception exception) {
			throw new DatabaseException(exception.getMessage());
		} finally {
			// Free the connection here.
			DBConnection.freeConnection(connection);
		}

		return tempcount;

	}

	public String insertClaimProcessDetails1(String clmRefNum, String userRemarks, ArrayList mainArray,
			java.util.Date dateofReceipt, String microFlag, String falgforCasesafet, ClaimDetail clmdtl, String nerfalg,
			String womenOprator) throws DatabaseException {
		String methodName = "insertClaimProcessDetails1";

		// System.out.println("Inside the method
		// insertClaimProcessDetails1...!##################################### ");
		Connection connection = null;
		CallableStatement insertClaimProcessDtls = null;
		Log.log(Log.INFO, "CPDAO", methodName, "Entered");
		int updateStatus = 0;
		boolean newConn = false;

		// ,nerfalg,womenOprator

		// jai code for CGPAN wise data

		String[] hidcgpan = (String[]) mainArray.get(0);
		String[] hidgaurIssue = (String[]) mainArray.get(1);
		String[] outstandingAsOnNPA = (String[]) mainArray.get(2);
		String[] recoverafterNPA = (String[]) mainArray.get(3);
		String[] netOutsandingAmt = (String[]) mainArray.get(4);
		String[] hidclaimbymliamt = (String[]) mainArray.get(5);
		String[] claimEligibleAmt = (String[]) mainArray.get(6);
		String[] firstInstallAmt = (String[]) mainArray.get(7);
		String[] dedecutByMliIfAny = (String[]) mainArray.get(8);
		String[] paybleAmt = (String[]) mainArray.get(9);

		double tcIssued1 = 0.0;
		double wcIssued1 = 0.0;
		double totalTCOSAmountAsOnNPA1 = 0.0;
		double totalWCOSAmountAsOnNPA1 = 0.0;
		double tcrecovery1 = 0.0;
		double wcrecovery1 = 0.0;
		double tcClaimEligibleAmt1 = 0.0;
		double wcClaimEligibleAmt1 = 0.0;

		// new Variables
		double totalIssuedAmt = 0.0;
		double tcClaimEligibleAmt = 0.0;
		double wcClaimEligibleAmt = 0.0;
		double tcFirstInstallment = 0.0;
		double wcFirstInstallment = 0.0;
		double totalTCFirstInstallment = 0.0;
		double totalWCFirstInstallment = 0.0;
		double totalDeductedByMli = 0.0;
		String payAmntNow = "";
		// retriveing the Total Inssue amount .

		for (int i = 0; i < hidgaurIssue.length; i++) {
			totalIssuedAmt = totalIssuedAmt + Double.parseDouble(hidgaurIssue[i]);
		}
		// System.out.println("The Total issued Amount is :--->"+totalIssuedAmt);
		// / System.out.println("The Micro Falg Value is :--->"+microFlag);

		// retriveing the Total Inssue amount .

		try { // try before main loop

			connection = DBConnection.getConnection();

			Statement str = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			connection.setAutoCommit(false);

			java.sql.Date dateReciipt = new java.sql.Date(dateofReceipt.getTime());
			// String converttoDBform =DateHelper.stringToDBDate(dateofReceipt);
			// System.out.println("The SQL FORMAT DATE IS :--->"+dateReciipt);

			// new java.sql.Date(dateofReceipt.getTime() dateofReceipt

			// Date date = Calendar.getInstance().getTime();
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			String todysDate = formatter.format(dateofReceipt);
			// System.out.println("Today : " + todysDate);
			String claimReciDate = DateHelper.stringToDBDate(todysDate);
			// System.out.println("The Current Date is :--->"+claimReciDate);

			String remQury = "Update claim_detail_temp@cginter set clm_remarks = clm_remarks ||'" + userRemarks
					+ "',CLM_DT_OF_RECEIPT='" + claimReciDate + "' where clm_ref_no = '" + clmRefNum + "'";
			// str.executeQuery(Qury1);
			// int resletVal=str.executeUpdate(remQury);
			int remarkUpdat = str.executeUpdate(remQury);
			// str.execute(remQury);
			// System.out.println("The Result Value for remark upadate is
			// :--->"+remarkUpdat);

			Log.log(Log.DEBUG, "CPDAO", methodName, "updateStatus,error " + updateStatus + ",");

			// System.out.println("The length of Array is :-->"+hidcgpan.length);
			for (int i = 0; i < hidcgpan.length; i++) {
				String cgpan = hidcgpan[i];
				// System.out.println("The CGPAN is:---->"+cgpan);

				double guissu = Double.parseDouble(hidgaurIssue[i]);
				// System.out.println("The guissu is:---->"+guissu);

				double ountstand = Double.parseDouble(outstandingAsOnNPA[i]);
				// System.out.println("The ountstand is:---->"+ountstand);

				double recov = Double.parseDouble(recoverafterNPA[i]);
				// System.out.println("The recov is:---->"+recov);

				double netout = Double.parseDouble(netOutsandingAmt[i]);
				// System.out.println("The netout is:---->"+netout);

				double claimbymliamt = Double.parseDouble(hidclaimbymliamt[i]);
				// System.out.println("The claimByMLI is:---->"+claimbymliamt);

				double claimeligibleamt = Double.parseDouble(claimEligibleAmt[i]);
				// System.out.println("The ClaimEligible is:---->"+claimeligibleamt);

				double firstInstall = Double.parseDouble(firstInstallAmt[i]);
				// System.out.println("The firstInstall is:---->"+firstInstall);

				double dedecutByMli = Double.parseDouble(dedecutByMliIfAny[i]);
				// System.out.println("The dedecutByMli is:---->"+dedecutByMli);

				totalDeductedByMli = totalDeductedByMli + dedecutByMli;

				double payAmt = Double.parseDouble(paybleAmt[i]);
				// System.out.println("The payAmt is:---->"+payAmt);

				String loanType = cgpan.substring(cgpan.length() - 2, cgpan.length());
				// System.out.println("The Loan Type IS :--->"+loanType);
				// System.out.println("The New CGPAN IS :-------------------->"+cgpan);

				// /jai code for elegibel claim amount
				// code for term loan over
				cgpan = cgpan.trim();

				if (loanType.equals("TC") || loanType.equals("CC")) { // term
																		// loan
																		// type
																		// start
					if ((totalIssuedAmt <= 500000) && (microFlag.equals("Y"))) {
						// jai code
						if (falgforCasesafet.equals("Y")) {
							tcClaimEligibleAmt = Math.round((Math.min(guissu, ountstand) - (recov)) * 0.85);
						} else if (falgforCasesafet.equals("N")) {
							// jai code
							tcClaimEligibleAmt = Math.round((Math.min(guissu, ountstand) - (recov)) * 0.80);
						}
					}
					/*
					 * else if(((totalIssuedAmt)<=500000)&&(microFlag.equals("N"))) {
					 * 
					 * if ( falgforCasesafet.equals("Y") && (nerfalg.equals("Y") ||
					 * womenOprator.equals("F")) ){ tcClaimEligibleAmt
					 * =Math.round((Math.min(guissu,ountstand)-(recov))*0.80); } else{
					 * tcClaimEligibleAmt =Math.round((Math.min(guissu,ountstand)-(recov))*0.75); }
					 * }
					 */

					else if (totalIssuedAmt <= 5000000 && (womenOprator.equals("F") || nerfalg.equals("Y"))) {

						tcClaimEligibleAmt = Math.round((Math.min(guissu, ountstand) - (recov)) * 0.80);

					} else if (totalIssuedAmt <= 500000 && (microFlag == "N")) {

						tcClaimEligibleAmt = Math.round((Math.min(guissu, ountstand) - (recov)) * 0.75);
					} else {
						tcClaimEligibleAmt = Math.round((Math.min(guissu, ountstand) - (recov)) * 0.75);
					}

					tcFirstInstallment = Math.round(tcClaimEligibleAmt * 0.75);
					totalTCFirstInstallment = totalTCFirstInstallment + tcFirstInstallment;
					// System.out.println("First Installment is jagdish :--->"+tcFirstInstallment);

					// System.out.println("Inside inner resultSet For TC...!");
					// String newQury=
					// "Update claim_tc_detail_temp@cginter set ctd_tc_asf_deductable
					// ='"+dedecutByMli+"',CTD_TC_CLM_ELIG_AMT
					// ='"+tcClaimEligibleAmt+"',CTD_TC_FIRST_INST_PAY_AMT
					// ='"+tcFirstInstallment+"',CTD_NPA_OUTSTANDING_AMT_REVISE
					// ='"+ountstand+"',CTD_NPA_RECOVERED_REVISE ='"+recov+"' where
					// CGPAN='"+cgpan+"'";
					// System.out.println("The Quetrt tC=========>"+newQury);
					// int uapdValu=str.executeUpdate(newQury);
					// System.out.println("After Update to claim_tc_detail_temp@cginter value is
					// :---->"+uapdValu);

					String Qury1 = "select count(*) from claim_tc_detail_temp@cginter where clm_ref_no ='" + clmRefNum
							+ "'";

					ResultSet rsQury1 = str.executeQuery(Qury1);
					if (rsQury1.next()) {
						try {
							// System.out.println("Inside inner resultSet For TC...!");

							String newQury = "update claim_tc_detail_temp set ctd_tc_asf_deductable ='" + dedecutByMli
									+ "',CTD_TC_CLM_ELIG_AMT ='" + tcClaimEligibleAmt + "',CTD_TC_FIRST_INST_PAY_AMT ='"
									+ tcFirstInstallment + "',CTD_NPA_OUTSTANDING_AMT_REVISE ='" + ountstand
									+ "',CTD_NPA_RECOVERED_REVISE ='" + recov + "' where  clm_ref_no ='" + clmRefNum
									+ "'  AND CGPAN='" + cgpan + "'";
							// String newQury=
							// "update claim_tc_detail_temp set ctd_tc_asf_deductable
							// ="+dedecutByMli+",CTD_TC_CLM_ELIG_AMT
							// ="+tcClaimEligibleAmt+",CTD_TC_FIRST_INST_PAY_AMT
							// ="+tcFirstInstallment+",CTD_NPA_OUTSTANDING_AMT_REVISE
							// ="+ountstand+",CTD_NPA_RECOVERED_REVISE ="+recov+" where clm_ref_no
							// ='"+clmRefNum+"' and cgpan='"+cgpan+"'";
							// System.out.println("The Quetrt tC=========>"+newQury);
							// int uapdValu=str.executeUpdate(newQury);
							int uapdValu = str.executeUpdate(newQury);
							// System.out.println("After Update to claim_tc_detail_temp@cginter value is
							// :---->"+uapdValu);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				} // term loan type over
				else { // working capital loan type start

					if (((totalIssuedAmt) <= 500000) && (microFlag.equals("Y"))) {
						// jai code
						if (falgforCasesafet.equals("Y")) {
							wcClaimEligibleAmt = Math.round((Math.min(guissu, ountstand) - (recov)) * 0.85);

						} else if (falgforCasesafet.equals("N")) {
							// jai code
							wcClaimEligibleAmt = Math.round((Math.min(guissu, ountstand) - (recov)) * 0.80);
						}
					}
					/*
					 * else if(((totalIssuedAmt)<=500000)&&(microFlag.equals("N"))) { //new code if
					 * ( falgforCasesafet.equals("Y") && (nerfalg.equals("Y") ||
					 * womenOprator.equals("F")) ){
					 * 
					 * wcClaimEligibleAmt =Math.round((Math.min(guissu,ountstand)-(recov))*0.80); }
					 * else{ wcClaimEligibleAmt
					 * =Math.round((Math.min(guissu,ountstand)-(recov))*0.75); } }
					 */

					else if (totalIssuedAmt <= 5000000 && (womenOprator == "F" || nerfalg == "Y")) {
						wcClaimEligibleAmt = Math.round((Math.min(guissu, ountstand) - (recov)) * 0.80);
					} else if (totalIssuedAmt <= 500000 && (microFlag == "N")) {
						wcClaimEligibleAmt = Math.round((Math.min(guissu, ountstand) - (recov)) * 0.75);
					} else {
						wcClaimEligibleAmt = Math.round((Math.min(guissu, ountstand) - (recov)) * 0.75);

					}
					wcFirstInstallment = Math.round(wcClaimEligibleAmt * 0.75);

					totalWCFirstInstallment = totalTCFirstInstallment + wcFirstInstallment;
					// System.out.println("First Installment for Working Capital By Jagdish
					// :--->:"+wcFirstInstallment);
					// System.out.println("wcClaimEligibleAmt By Jagdish
					// :--->:"+wcClaimEligibleAmt);

					// String
					// wcUpdateQuery="update claim_wc_detail_temp@cginter set CWD_WC_ASF_DEDUCTABLE
					// = '"+dedecutByMli+"',CWD_WC_CLM_ELIG_AMT =
					// '"+wcClaimEligibleAmt+"',CWD_WC_FIRST_INST_PAY_AMT =
					// '"+wcFirstInstallment+"',CWD_NPA_OUTSTANDING_AMT_REVISE =
					// '"+ountstand+"',CWD_NPA_RECOVERED_REVISE = '"+recov+"' where
					// CGPAN='"+cgpan+"'"
					// ;
					// System.out.println("The Quetrt WC=========>"+wcUpdateQuery);
					// int uapdValuforWC=str.executeUpdate(wcUpdateQuery);
					// System.out.println("After Update to update claim_wc_detail_temp@cginter value
					// is :---->"+uapdValuforWC);

					//
					String wcSelqury = "select count(*)  from claim_wc_detail_temp@cginter where clm_ref_no = '"
							+ clmRefNum + "'";
					ResultSet rswcSelqury = str.executeQuery(wcSelqury);
					if (rswcSelqury.next()) {
						String wcUpdateQuery = "update claim_wc_detail_temp set CWD_WC_ASF_DEDUCTABLE = '"
								+ dedecutByMli + "',CWD_WC_CLM_ELIG_AMT = '" + wcClaimEligibleAmt
								+ "',CWD_WC_FIRST_INST_PAY_AMT = '" + wcFirstInstallment
								+ "',CWD_NPA_OUTSTANDING_AMT_REVISE = '" + ountstand + "',CWD_NPA_RECOVERED_REVISE = '"
								+ recov + "' where clm_ref_no = '" + clmRefNum + "' AND CGPAN='" + cgpan + "'";
						// String
						// wcUpdateQuery="update claim_wc_detail_temp@cginter set CWD_WC_ASF_DEDUCTABLE
						// = "+dedecutByMli+",CWD_WC_CLM_ELIG_AMT =
						// "+wcClaimEligibleAmt+",CWD_WC_FIRST_INST_PAY_AMT =
						// "+wcFirstInstallment+",CWD_NPA_OUTSTANDING_AMT_REVISE =
						// "+ountstand+",CWD_NPA_RECOVERED_REVISE = "+recov+" where clm_ref_no =
						// '"+clmRefNum+"' AND CGPAN='"+cgpan+"'"
						// ;
						// System.out.println("The Quetrt WC=========>"+wcUpdateQuery);
						int uapdValuforWC = str.executeUpdate(wcUpdateQuery);

						// System.out.println("After Update to update claim_wc_detail_temp@cginter value
						// is :---->"+uapdValuforWC);
					}

				} // //working capital loan type over

				cgpan = "";
			} // for close

			// totalTCFirstInstallment+totalWCFirstInstallment-totalDeductedByMli
			connection.commit();

			payAmntNow = Double.toString(totalTCFirstInstallment + totalWCFirstInstallment - totalDeductedByMli);
			clmdtl.setTotalAmtPayNow(payAmntNow);

			// payAmntNow =
			// Double.toString(tcFirstInstallment+wcFirstInstallment-tcServiceFee-wcServiceFee);
			// clmdtl.setTotalAmtPayNow(payAmntNow);

		} catch (Exception e) // try after main loop over
		{
			Log.log(Log.ERROR, "CPDAO", methodName, e.getMessage());
			Log.logException(e);
			e.printStackTrace();
			throw new DatabaseException(e.getMessage());

			// e.printStackTrace();
		}

		// jai code for CGPAN wise data over

		finally {
			if (newConn) {
				DBConnection.freeConnection(connection);
			}
		}

		Log.log(Log.INFO, "CPDAO", methodName, "Exited");
		return payAmntNow;
	}

	public HashMap getPrimarySecurityAndNetworthOfGuarantors(String npaId) throws DatabaseException {
		Connection conn = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		HashMap sancmap = new HashMap();
		HashMap npamap = new HashMap();
		HashMap map = new HashMap();

		conn = DBConnection.getConnection();

		try {
			stmt = conn.prepareCall("{?=call packgetprimarysecurity.funcGetPriSecforAppRefasonNPA(?,?,?)}");
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.registerOutParameter(3, Constants.CURSOR);
			stmt.registerOutParameter(4, Types.VARCHAR);
			stmt.setString(2, npaId);
			stmt.executeQuery();

			int status = stmt.getInt(1);

			if (status == Constants.FUNCTION_FAILURE) {

				String error = stmt.getString(4);

				stmt.close();
				stmt = null;

				conn.rollback();

				throw new DatabaseException(error);
			} else {
				rs = (ResultSet) stmt.getObject(3);
				while (rs.next()) {
					// logic to populate map values
					String flag = (String) rs.getString(1);
					Double networth = (Double) rs.getDouble(2);
					String reason = (String) rs.getString(3);

					if ("SAN".equals(flag)) {
						// add to sanc map
						sancmap.put("networth", networth);
						sancmap.put("reasonReduction", reason);
					}
					if ("NPA".equals(flag)) {
						// add to npa map
						npamap.put("networth", networth);
						npamap.put("reasonReduction", reason);
					}
				}
			}
			rs.close();
			rs = null;
			stmt.close();
			stmt = null;

			stmt = conn.prepareCall("{?=call packgetprimarysecurity.funcGetPriSecPartforArnAsOnNPA(?,?,?)}");
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.registerOutParameter(3, Constants.CURSOR);
			stmt.registerOutParameter(4, Types.VARCHAR);
			stmt.setString(2, npaId);

			stmt.executeQuery();

			status = stmt.getInt(1);
			if (status == Constants.FUNCTION_FAILURE) {

				String error = stmt.getString(4);

				stmt.close();
				stmt = null;

				conn.rollback();

				throw new DatabaseException(error);
			} else {
				rs = (ResultSet) stmt.getObject(3);

				while (rs.next()) {
					// logic to populate map values
					if ("SAN".equals((String) rs.getString(2))) {
						if ("LAND".equals((String) rs.getString(1))) {
							// add to sanc map
							sancmap.put("LAND", (Double) rs.getDouble(3));

						}
						if ("BUILDING".equals((String) rs.getString(1))) {
							// add to sanc map
							sancmap.put("BUILDING", (Double) rs.getDouble(3));

						}
						if ("MACHINE".equals((String) rs.getString(1))) {
							// add to sanc map
							sancmap.put("MACHINE", (Double) rs.getDouble(3));

						}
						if ("OTHER FIXED MOVABLE ASSETS".equals((String) rs.getString(1))) {
							// add to sanc map
							sancmap.put("OTHER FIXED MOVABLE ASSETS", (Double) rs.getDouble(3));

						}
						if ("CUR_ASSETS".equals((String) rs.getString(1))) {
							// add to sanc map
							sancmap.put("CURRENT ASSETS", (Double) rs.getDouble(3));

						}
						if ("OTHERS".equals((String) rs.getString(1))) {
							// add to sanc map
							sancmap.put("OTHERS", (Double) rs.getDouble(3));

						}
					}

					if ("NPA".equals((String) rs.getString(2))) {
						if ("LAND".equals((String) rs.getString(1))) {

							npamap.put("LAND", (Double) rs.getDouble(3));

						}
						if ("BUILDING".equals((String) rs.getString(1))) {

							npamap.put("BUILDING", (Double) rs.getDouble(3));

						}
						if ("MACHINE".equals((String) rs.getString(1))) {
							npamap.put("MACHINE", (Double) rs.getDouble(3));
						}
						if ("OTHER FIXED MOVABLE ASSETS".equals((String) rs.getString(1))) {
							npamap.put("OTHER FIXED MOVABLE ASSETS", (Double) rs.getDouble(3));
						}
						if ("CUR_ASSETS".equals((String) rs.getString(1))) {
							npamap.put("CURRENT ASSETS", (Double) rs.getDouble(3));
						}
						if ("OTHERS".equals((String) rs.getString(1))) {
							npamap.put("OTHERS", (Double) rs.getDouble(3));
						}
					}
				}
			}
			rs.close();
			rs = null;
			stmt.close();
			stmt = null;
		} catch (SQLException e) {
			throw new DatabaseException();
		} finally {
			if (conn != null) {
				DBConnection.freeConnection(conn);
			}
		}
		map.put("SAN", sancmap);
		map.put("NPA", npamap);
		return map;
	}

	public Map getCgpanFlagsForBid(String borrowerId) throws DatabaseException {
		CallableStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		Map map = new HashMap();
		Vector tccgpans = new Vector();
		Vector wccgpans = new Vector();
		conn = DBConnection.getConnection(true);

		try {
			stmt = conn.prepareCall("{?=call packgetcgpdetailforclaim.funcGetCGPFlags(?,?,?)}");
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.setString(2, borrowerId);
			stmt.registerOutParameter(3, Constants.CURSOR);
			stmt.registerOutParameter(4, Types.VARCHAR);
			stmt.execute();

			int errorCode = stmt.getInt(1);
			String exception = stmt.getString(4);
			if (errorCode == Constants.FUNCTION_FAILURE) {
				stmt.close();
				stmt = null;
				throw new DatabaseException(exception);
			} else {
				rs = (ResultSet) stmt.getObject(3);
				while (rs.next()) {
					Map m = new HashMap();
					String cgpan = (String) rs.getString(1);
					m.put("CGPAN", cgpan);
					m.put("MICROFLAG", rs.getString(2));
					m.put("SCHEME", rs.getString(3));
					m.put("GENDER", rs.getString(4));
					m.put("SANCFLAG", rs.getString(5));
					m.put("NERFLAG", rs.getString(6));
					m.put("LASTDSBRSMNTDT", rs.getDate(7));
					m.put("TCPRINREPAMT", rs.getDouble(8));
					m.put("TCINTREPAMT", rs.getDouble(9));
					m.put("TCPRINNPAOSAMT", rs.getDouble(10));
					m.put("WCPRINNPAOSAMT", rs.getDouble(11));
					m.put("TOTALDISBAMNT", rs.getDouble(16));
					m.put("TCINTNPAOSAMT", rs.getDouble(17));
					m.put("WCINTNPAOSAMT", rs.getDouble(18));
					m.put("ApprovedAmount", rs.getDouble(19));
					m.put("AppSanctionDate", rs.getDate(13));
					// System.out.println("tc os prin:"+rs.getDouble(10)+" tc os
					// int"+rs.getDouble(17));
					// System.out.println("wc os prin:"+rs.getDouble(11)+" wc os
					// int"+rs.getDouble(18));
					if ("TC".equals(cgpan.substring(cgpan.length() - 2))) {
						tccgpans.add(m);
					} else {
						wccgpans.add(m);
					}
				}
				// System.out.println("tccgpans size:"+tccgpans.size());
				// System.out.println("wccgpans size:"+wccgpans.size());
				map.put("tccgpans", tccgpans);
				map.put("wccgpans", wccgpans);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				rs = null;
				stmt.close();
				stmt = null;
				DBConnection.freeConnection(conn);
			} catch (SQLException e) {

			}
		}
		return map;
	}

	public void updateClaimApplication(ClaimApplication claimApplication, HashMap map, boolean flag)
			throws DatabaseException {
		Log.log(4, "CPDAO", "saveClaimApplication()", "Entered!");
		GMProcessor gmprocessor = new GMProcessor();
		String claimRefNumber = claimApplication.getClaimRefNumber();
		// System.out.println("claimRefNumber"+claimRefNumber);
		String borrowerId = claimApplication.getBorrowerId();
		String memberId = claimApplication.getMemberId();
		memberId = memberId.trim();
		// System.out.println("memberId"+memberId);
		String bankId = memberId.substring(0, Types.INTEGER);
		String zoneId = memberId.substring(4, 8);
		String branchId = memberId.substring(8, Types.VARCHAR);

		MemberInfo memberInfo = getMemberInfoDetails(bankId, zoneId, branchId);

		String bankName = memberInfo.getMemberBankName();
		// System.out.println("bankName"+bankName);
		String participatingBank = claimApplication.getParticipatingBank();
		// System.out.println("participatingBank"+participatingBank);
		String whethereWillFulDfaulter = claimApplication.getWhetherBorrowerIsWilfulDefaulter();
		java.util.Date dtOfConclusionOfRecProc = claimApplication.getDtOfConclusionOfRecoveryProc();
		String whetherAccntWrittenOffBooks = claimApplication.getWhetherAccntWrittenOffFromBooksOfMLI();
		java.util.Date dtOfWrittenOffBooks = claimApplication.getDtOnWhichAccntWrittenOff();

		LegalProceedingsDetail legalProceedingsDetail = claimApplication.getLegalProceedingsDetails();

		java.util.Date filingdate = legalProceedingsDetail.getFilingDate();
		Log.log(4, "CPDAO", "saveClaimApplication()", "Filing Date is :" + filingdate);
		double amountClaimed = legalProceedingsDetail.getAmountClaimed();
		Log.log(4, "CPDAO", "saveClaimApplication()", "Amount Claimed is :" + amountClaimed);
		String currentStatusRemarks = legalProceedingsDetail.getCurrentStatusRemarks();
		Log.log(4, "CPDAO", "saveClaimApplication()", "Current Status Remarks is :" + currentStatusRemarks);

		String isRecoveryProceedingsComplete = legalProceedingsDetail.getIsRecoveryProceedingsConcluded();

		// System.out.println("isRecoveryProceedingsComplete"+isRecoveryProceedingsComplete);
		Log.log(4, "CPDAO", "saveClaimApplication()",
				"Recovery Proceedings Complete is :" + isRecoveryProceedingsComplete);
		java.util.Date claimSubmittedDate = claimApplication.getClaimSubmittedDate();
		java.util.Date dateOfReleaseOfWC = claimApplication.getDateOfReleaseOfWC();
		java.util.Date dateOfIssueOfRecallNotice = claimApplication.getDateOfIssueOfRecallNotice();
		String nameOfOfficial = claimApplication.getNameOfOfficial();
		String designationOfOfficial = claimApplication.getDesignationOfOfficial();
		String place = claimApplication.getPlace();
		String createdModifiedBy = claimApplication.getCreatedModifiedy();
		String installmentFlag = null;
		if (claimApplication.getFirstInstallment()) {
			installmentFlag = "F";
		} else if (claimApplication.getSecondInstallment()) {
			installmentFlag = "S";
		}
		// System.out.println("installmentFlag"+installmentFlag);

		String cgpan = null;
		java.util.Date disbursementDate = null;
		double principalAmount = 0.0D;
		double interestAmnt = 0.0D;
		double npaAmount = 0.0D;
		double legalAmount = 0.0D;
		double claimAmount = 0.0D;

		CallableStatement callableStmt = null;
		Connection conn = null;
		boolean hasExceptionOccured = false;
		int status = -1;
		String errorCode = null;
		try {
			conn = DBConnection.getConnection(false);

			/*
			 * callableStmt = conn.prepareCall("{?=call funcInsClaimTrail(?,?)}");
			 * callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			 * callableStmt.setString(2, claimRefNumber);
			 * callableStmt.registerOutParameter(3, java.sql.Types.VARCHAR);
			 * callableStmt.execute(); int trailstatus = callableStmt.getInt(1);
			 * if(trailstatus == 0){ callableStmt.close(); callableStmt = null; }else{ throw
			 * new DatabaseException("Unable to update claim trails."); }
			 */
			System.out.println("=====9914=====");

			callableStmt = conn.prepareCall(
					"{?=call funcUpdateClaimDetails(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt.setString(2, claimRefNumber);
			callableStmt.setString(3, borrowerId);
			callableStmt.setString(4, bankId);
			callableStmt.setString(5, zoneId);
			callableStmt.setString(6, branchId);
			callableStmt.setString(7, participatingBank);
			if (dateOfIssueOfRecallNotice != null) {
				callableStmt.setDate(8, new java.sql.Date(dateOfIssueOfRecallNotice.getTime()));
			} else {
				callableStmt.setNull(8, java.sql.Types.DATE);
			}
			if (filingdate != null) {
				callableStmt.setDate(9, new java.sql.Date(filingdate.getTime()));
			} else {
				callableStmt.setNull(9, java.sql.Types.DATE);
			}
			if (dateOfReleaseOfWC != null) {
				callableStmt.setDate(10, new java.sql.Date(dateOfReleaseOfWC.getTime()));
			} else {
				callableStmt.setNull(10, java.sql.Types.DATE);
			}
			callableStmt.setString(11, nameOfOfficial);
			callableStmt.setString(12, designationOfOfficial);
			callableStmt.setString(13, bankName);
			callableStmt.setString(14, place);
			if (claimSubmittedDate != null) {
				callableStmt.setDate(15, new java.sql.Date(claimSubmittedDate.getTime()));
			} else {
				callableStmt.setNull(15, java.sql.Types.DATE);
			}

			callableStmt.setString(16, installmentFlag);
			callableStmt.setString(17, createdModifiedBy);

			java.util.Date subsidyDate = (java.util.Date) claimApplication.getSubsidyDate();
			if (subsidyDate != null) {
				callableStmt.setDate(18, new java.sql.Date(subsidyDate.getTime()));
			} else {
				callableStmt.setNull(18, java.sql.Types.DATE);
			}
			callableStmt.setDouble(19, claimApplication.getSubsidyAmt());
			callableStmt.setString(20, claimApplication.getIfsCode());
			callableStmt.setString(21, claimApplication.getNeftCode());
			callableStmt.setString(22, claimApplication.getRtgsBankName());
			callableStmt.setString(23, claimApplication.getRtgsBranchName());
			callableStmt.setString(24, claimApplication.getRtgsBankNumber());
			callableStmt.setString(25, claimApplication.getMicroCategory());

			callableStmt.setString(26, claimApplication.getWilful());
			callableStmt.setString(27, claimApplication.getFraudFlag());
			callableStmt.setString(28, claimApplication.getEnquiryFlag());
			callableStmt.setString(29, claimApplication.getMliInvolvementFlag());
			callableStmt.setString(30, claimApplication.getReasonForRecall());
			callableStmt.setString(31, claimApplication.getReasonForFilingSuit());

			java.util.Date possessionDt = claimApplication.getAssetPossessionDt();
			if (possessionDt != null) {
				callableStmt.setDate(32, new java.sql.Date(possessionDt.getTime()));
			} else {
				callableStmt.setNull(32, java.sql.Types.DATE);
			}
			callableStmt.setString(33, claimApplication.getInclusionOfReceipt());
			callableStmt.setString(34, claimApplication.getConfirmRecoveryFlag());
			callableStmt.setString(35, claimApplication.getSubsidyFlag());
			callableStmt.setString(36, claimApplication.getIsSubsidyRcvdAfterNpa());
			callableStmt.setString(37, claimApplication.getIsSubsidyAdjustedOnDues());
			callableStmt.setString(38, claimApplication.getMliCommentOnFinPosition());
			callableStmt.setString(39, claimApplication.getDetailsOfFinAssistance());
			callableStmt.setString(40, claimApplication.getCreditSupport());
			callableStmt.setString(41, claimApplication.getBankFacilityDetail());
			callableStmt.setString(42, claimApplication.getPlaceUnderWatchList());
			callableStmt.setString(43, claimApplication.getRemarksOnNpa());

			callableStmt.setString(44, claimApplication.getDealingOfficerName());
			callableStmt.setString(46, "23AAACU0564G2ZO");
			callableStmt.setString(47, "MP");

			// callableStmt.registerOutParameter(44,java.sql.Types.VARCHAR);
			callableStmt.registerOutParameter(45, java.sql.Types.VARCHAR);
			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(45);

			if (status == Constants.FUNCTION_FAILURE) {

				Log.log(Log.ERROR, "CPDAO", "saveClaimApplication()", "SP returns a 1. Error code is :" + errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			// closing the callable statement
			callableStmt.close();
			callableStmt = null;

			Log.log(4, "CPDAO", "saveClaimApplication()", "Done with saving Generic Info of Claim Application");

			Log.log(4, "CPDAO", "saveClaimApplication()", "Saving Term Loan Dtls of Claim Application......");

			Vector tcDetails = claimApplication.getTermCapitalDtls();
			double osAsOnSecondClmLodgemnt = 0.0D;
			String tcClaimFlag = "";
			double totalDisbAmnt = 0.0D;

			if (tcDetails != null) {
				for (int i = 0; i < tcDetails.size(); i++) {
					TermLoanCapitalLoanDetail tcLoanDetail = (TermLoanCapitalLoanDetail) tcDetails.elementAt(i);
					if (tcLoanDetail != null) {
						cgpan = tcLoanDetail.getCgpan();
						disbursementDate = tcLoanDetail.getLastDisbursementDate();
						principalAmount = tcLoanDetail.getPrincipalRepayment();
						interestAmnt = tcLoanDetail.getInterestAndOtherCharges();
						npaAmount = tcLoanDetail.getOutstandingAsOnDateOfNPA();
						legalAmount = tcLoanDetail.getOutstandingStatedInCivilSuit();
						claimAmount = tcLoanDetail.getOutstandingAsOnDateOfLodgement();
						osAsOnSecondClmLodgemnt = tcLoanDetail.getOsAsOnDateOfLodgementOfClmForSecInstllmnt();
						tcClaimFlag = tcLoanDetail.getTcClaimFlag();
						totalDisbAmnt = tcLoanDetail.getTotaDisbAmnt();

						callableStmt = conn.prepareCall("{?=call funcUpdateClaimTLDetails(?,?,?,?,?,?,?,?,?,?,?,?)}");

						callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
						callableStmt.setString(2, claimRefNumber);
						callableStmt.setString(3, cgpan);
						if (disbursementDate != null) {
							callableStmt.setDate(4, new java.sql.Date(disbursementDate.getTime()));
						} else {
							callableStmt.setNull(4, java.sql.Types.DATE);
						}
						callableStmt.setDouble(5, principalAmount);
						callableStmt.setDouble(6, interestAmnt);
						callableStmt.setDouble(7, npaAmount);
						callableStmt.setDouble(8, legalAmount);
						callableStmt.setDouble(9, claimAmount);
						callableStmt.setDouble(10, osAsOnSecondClmLodgemnt);
						// callableStmt.registerOutParameter(11,java.sql.Types.VARCHAR);
						callableStmt.setString(11, tcClaimFlag);
						callableStmt.setDouble(12, totalDisbAmnt);
						callableStmt.registerOutParameter(13, java.sql.Types.VARCHAR);

						callableStmt.execute();
						status = callableStmt.getInt(1);
						// errorCode = callableStmt.getString(11);
						errorCode = callableStmt.getString(13);
						if (status == 1) {
							Log.log(2, "CPDAO", "saveClaimApplication()",
									"SP returns a 1. Error code is :" + errorCode);
							callableStmt.close();
							try {
								conn.rollback();
							} catch (SQLException sqlex) {
								throw new DatabaseException(sqlex.getMessage());
							}
							throw new DatabaseException(errorCode);
						}
						callableStmt.close();
					}
				}
			}
			Log.log(4, "CPDAO", "saveClaimApplication()", "Done with saving Term Loan Details of Claim Application");

			Log.log(4, "CPDAO", "saveClaimApplication()", "Saving Working Capital Dtls of Claim Application......");
			ArrayList wcDetails = claimApplication.getWorkingCapitalDtls();
			double osWCAsOnSecWCClmt = 0.0D;
			String wcClaimFlag = "";

			if (wcDetails != null) {
				for (int i = 0; i < wcDetails.size(); i++) {
					WorkingCapitalDetail wcDetail = (WorkingCapitalDetail) wcDetails.get(i);
					cgpan = wcDetail.getCgpan();
					npaAmount = wcDetail.getOutstandingAsOnDateOfNPA();
					legalAmount = wcDetail.getOutstandingStatedInCivilSuit();
					claimAmount = wcDetail.getOutstandingAsOnDateOfLodgement();
					osWCAsOnSecWCClmt = wcDetail.getOsAsOnDateOfLodgementOfClmForSecInstllmnt();
					wcClaimFlag = wcDetail.getWcClaimFlag();

					callableStmt = conn.prepareCall("{? = call funcUpdateClaimWCDetails(?,?,?,?,?,?,?,?)}");

					callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
					callableStmt.setString(2, claimRefNumber);
					callableStmt.setString(3, cgpan);
					callableStmt.setDouble(4, npaAmount);
					callableStmt.setDouble(5, legalAmount);
					callableStmt.setDouble(6, claimAmount);
					callableStmt.setDouble(7, osWCAsOnSecWCClmt);
					// callableStmt.registerOutParameter(8,java.sql.Types.VARCHAR);
					callableStmt.setString(8, wcClaimFlag);
					callableStmt.registerOutParameter(9, java.sql.Types.VARCHAR);
					callableStmt.execute();

					status = callableStmt.getInt(1);
					// errorCode = callableStmt.getString(8);
					errorCode = callableStmt.getString(9);
					if (status == 1) {
						Log.log(2, "CPDAO", "saveClaimApplication()", "SP returns a 1. Error code is :" + errorCode);
						callableStmt.close();
						try {
							conn.rollback();
						} catch (SQLException sqlex) {
							throw new DatabaseException(sqlex.getMessage());
						}
						throw new DatabaseException(errorCode);
					}

					callableStmt.close();
				}
			}
			Log.log(4, "CPDAO", "saveClaimApplication()",
					"Done with saving Working Capital Details of Claim Application");

			Log.log(4, "CPDAO", "saveClaimApplication()", "Saving Recovery Details of Claim Application");

			Vector vecRecoveryDetails = claimApplication.getRecoveryDetails();
			String recoveryMode = null;
			double tcPrincipal = 0.0D;
			double tcInterestAndOtherCharges = 0.0D;
			double wcAmount = 0.0D;
			double wcOtherCharges = 0.0D;

			if (vecRecoveryDetails != null) {
				for (int i = 0; i < vecRecoveryDetails.size(); i++) {
					RecoveryDetails recoveryDetail = (RecoveryDetails) vecRecoveryDetails.elementAt(i);
					if (recoveryDetail != null) {
						cgpan = recoveryDetail.getCgpan();
						recoveryMode = recoveryDetail.getModeOfRecovery();
						tcPrincipal = recoveryDetail.getTcPrincipal();
						tcInterestAndOtherCharges = recoveryDetail.getTcInterestAndOtherCharges();
						wcAmount = recoveryDetail.getWcAmount();
						wcOtherCharges = recoveryDetail.getWcOtherCharges();
						// if ((tcPrincipal != 0.0D) ||
						// (tcInterestAndOtherCharges != 0.0D) ||
						// (wcAmount != 0.0D) || (wcOtherCharges != 0.0D)) {
						callableStmt = conn.prepareCall("{?=call funcUpdateClaimRecoveryDetails(?,?,?,?,?,?,?,?)}");
						callableStmt.registerOutParameter(1, Types.INTEGER);
						callableStmt.setString(2, claimRefNumber);
						callableStmt.setString(3, cgpan);
						callableStmt.setString(4, recoveryMode);
						callableStmt.setDouble(5, tcPrincipal);
						callableStmt.setDouble(6, tcInterestAndOtherCharges);
						callableStmt.setDouble(7, wcAmount);
						callableStmt.setDouble(8, wcOtherCharges);
						callableStmt.registerOutParameter(9, java.sql.Types.VARCHAR);

						callableStmt.execute();
						status = callableStmt.getInt(1);
						errorCode = callableStmt.getString(9);
						if (status == 1) {
							Log.log(2, "CPDAO", "saveClaimApplication()",
									"SP returns a 1. Error code is :" + errorCode);
							callableStmt.close();
							try {
								conn.rollback();
							} catch (SQLException sqlex) {
								throw new DatabaseException(sqlex.getMessage());
							}
							throw new DatabaseException(errorCode);
						}

						callableStmt.close();
						// }
					}
				}
			}
			Log.log(4, "CPDAO", "saveClaimApplication()", "Done with saving Recovery Details of Claim Application");

			Log.log(4, "CPDAO", "saveClaimApplication()",
					"Saving Security and Personal Guarantee Dtls of Claim Application for as on Sanction......");
			SecurityAndPersonalGuaranteeDtls secAndPerGuaranteeDtl = claimApplication
					.getSecurityAndPersonalGuaranteeDtls();

			DtlsAsOnDateOfNPA dtlsAsOnNPA = secAndPerGuaranteeDtl.getDetailsAsOnDateOfNPA();
			DtlsAsOnDateOfSanction dtlsAsOnSanction = secAndPerGuaranteeDtl.getDetailsAsOnDateOfSanction();
			DtlsAsOnLogdementOfClaim dtlsAsOnLodgeOfClaim = secAndPerGuaranteeDtl
					.getDetailsAsOnDateOfLodgementOfClaim();
			DtlsAsOnLogdementOfSecondClaim dtlsAsOnLodgemntOfSecClm = secAndPerGuaranteeDtl
					.getDetailsAsOnDateOfLodgementOfSecondClaim();

			double networthAsOnSanction = dtlsAsOnSanction.getNetworthOfGuarantors();
			double amntRealizedThruInvocationPerGuar = 0.0D;
			double amntRealizedThruSecurity = 0.0D;
			String reasonForReduction = null;
			String reasonForReductionAsOnSanction = dtlsAsOnSanction.getReasonsForReduction();
			String claimSecurityIdAsOnSanction = null;

			callableStmt = conn.prepareCall("{?=call funcUpdateClaimSecurityDetail(?,?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt.setString(2, claimRefNumber);
			callableStmt.setString(3, ClaimConstants.CLM_SAPGD_AS_ON_SANCTION_CODE);
			callableStmt.setDouble(4, networthAsOnSanction);
			callableStmt.setString(5, reasonForReductionAsOnSanction);
			callableStmt.setDouble(6, amntRealizedThruInvocationPerGuar);
			callableStmt.registerOutParameter(7, java.sql.Types.VARCHAR);
			callableStmt.registerOutParameter(8, java.sql.Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(8);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing As on Dt of Sanction Dtl. Error code is :" + errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			if (status == 0) {
				claimSecurityIdAsOnSanction = callableStmt.getString(7);

				callableStmt.close();
			}

			// Calling the next SP to save security and personal guarantee
			// particulars
			// For Land
			callableStmt = conn.prepareCall("{?=call funcUpdateClaimPerGuarDetail(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			if ((claimSecurityIdAsOnSanction != null) && (!(claimSecurityIdAsOnSanction.equals("")))) {
				callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnSanction));
			}
			callableStmt.setString(3, ClaimConstants.CLM_SAPGD_PARTICULAR_LAND);
			double vallandsanction = dtlsAsOnSanction.getValueOfLand();
			callableStmt.setDouble(4, vallandsanction);
			callableStmt.setDouble(5, amntRealizedThruSecurity);
			callableStmt.setString(6, reasonForReduction);
			callableStmt.registerOutParameter(7, java.sql.Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing Land as on Dt of Sanction. Error code is :" + errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			Log.log(4, "CPDAO", "saveClaimApplication()", "Successfully saved - Land for As on Dt of Sanction");

			callableStmt = conn.prepareCall("{?=call funcUpdateClaimPerGuarDetail(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			if ((claimSecurityIdAsOnSanction != null) && (!claimSecurityIdAsOnSanction.equals(""))) {
				callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnSanction));
			}
			callableStmt.setString(3, "MACHINE");
			double valmachinesanction = dtlsAsOnSanction.getValueOfMachine();
			callableStmt.setDouble(4, valmachinesanction);
			callableStmt.setDouble(5, amntRealizedThruSecurity);
			callableStmt.setString(6, reasonForReduction);
			callableStmt.registerOutParameter(7, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing Machine as on Dt of Sanction. Error code is :" + errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			Log.log(4, "CPDAO", "saveClaimApplication()", "Successfully saved - Machine for As on Dt of Sanction");

			// For Building
			callableStmt = conn.prepareCall("{?=call funcUpdateClaimPerGuarDetail(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			if ((claimSecurityIdAsOnSanction != null) && (!(claimSecurityIdAsOnSanction.equals("")))) {
				callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnSanction));
			}
			callableStmt.setString(3, ClaimConstants.CLM_SAPGD_PARTICULAR_BLDG);
			double valbldgesanction = dtlsAsOnSanction.getValueOfBuilding();
			callableStmt.setDouble(4, valbldgesanction);
			callableStmt.setDouble(5, amntRealizedThruSecurity);
			callableStmt.setString(6, reasonForReduction);
			callableStmt.registerOutParameter(7, java.sql.Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing Building as on Dt of Sanction. Error code is :" + errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			Log.log(4, "CPDAO", "saveClaimApplication()", "Successfully saved - Building for As on Dt of Sanction");

			// For Other Fixed/Movable Assets
			callableStmt = conn.prepareCall("{?=call funcUpdateClaimPerGuarDetail(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			if ((claimSecurityIdAsOnSanction != null) && (!(claimSecurityIdAsOnSanction.equals("")))) {
				callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnSanction));
			}
			callableStmt.setString(3, ClaimConstants.CLM_SAPGD_PARTICULAR_OTHER_FIXED_MOV_ASSETS);
			double valfixedmovableassetssanction = dtlsAsOnSanction.getValueOfOtherFixedMovableAssets();
			callableStmt.setDouble(4, valfixedmovableassetssanction);
			callableStmt.setDouble(5, amntRealizedThruSecurity);
			callableStmt.setString(6, reasonForReduction);
			callableStmt.registerOutParameter(7, java.sql.Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing Other Fixed/Movable Assets as on Dt of Sanction. Error code is :"
								+ errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			Log.log(4, "CPDAO", "saveClaimApplication()",
					"Successfully saved - Fixed and Movable Assets for As on Dt of Sanction");

			// For Current Assets
			callableStmt = conn.prepareCall("{?=call funcUpdateClaimPerGuarDetail(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			if ((claimSecurityIdAsOnSanction != null) && (!(claimSecurityIdAsOnSanction.equals("")))) {
				callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnSanction));
			}
			callableStmt.setString(3, ClaimConstants.CLM_SAPGD_PARTICULAR_CUR_ASSETS);
			double valcurrassetssanction = dtlsAsOnSanction.getValueOfCurrentAssets();
			callableStmt.setDouble(4, valcurrassetssanction);
			callableStmt.setDouble(5, amntRealizedThruSecurity);
			callableStmt.setString(6, reasonForReduction);
			callableStmt.registerOutParameter(7, java.sql.Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing Current Assets as on Dt of Sanction. Error code is :" + errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			Log.log(4, "CPDAO", "saveClaimApplication()",
					"Successfully saved - Current Assets for As on Dt of Sanction");

			// For other assets
			callableStmt = conn.prepareCall("{?=call funcUpdateClaimPerGuarDetail(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			if ((claimSecurityIdAsOnSanction != null) && (!(claimSecurityIdAsOnSanction.equals("")))) {
				callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnSanction));
			}
			callableStmt.setString(3, ClaimConstants.CLM_SAPGD_PARTICULAR_OTHERS);
			double valotherssanction = dtlsAsOnSanction.getValueOfOthers();
			callableStmt.setDouble(4, valotherssanction);
			callableStmt.setDouble(5, amntRealizedThruSecurity);
			callableStmt.setString(6, reasonForReduction);
			callableStmt.registerOutParameter(7, java.sql.Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing Other Assets as on Dt of Sanction. Error code is :" + errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			Log.log(4, "CPDAO", "saveClaimApplication()", "Successfully saved - Other Assets for As on Dt of Sanction");
			Log.log(4, "CPDAO", "saveClaimApplication()",
					"Done with Security and Personal Guarantee Dtls of Claim Application for as on Sanction");

			Log.log(4, "CPDAO", "saveClaimApplication()",
					"Saving Security and Personal Guarantee Dtls of Claim Application for as on date of NPA");
			double networthAsOnNPA = dtlsAsOnNPA.getNetworthOfGuarantors();
			String reasonForReductionAsOnNPA = dtlsAsOnNPA.getReasonsForReduction();
			String claimSecurityIdAsOnNPA = null;

			callableStmt = conn.prepareCall("{?=call funcUpdateClaimSecurityDetail(?,?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt.setString(2, claimRefNumber);
			callableStmt.setString(3, ClaimConstants.CLM_SAPGD_AS_ON_NPA_CODE);
			callableStmt.setDouble(4, networthAsOnNPA);
			callableStmt.setString(5, reasonForReductionAsOnNPA);
			callableStmt.setDouble(6, amntRealizedThruInvocationPerGuar);
			callableStmt.registerOutParameter(7, java.sql.Types.VARCHAR);
			callableStmt.registerOutParameter(8, java.sql.Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(8);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing NPA Dtls. Error code is :" + errorCode);

				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			if (status == 0) {
				claimSecurityIdAsOnNPA = callableStmt.getString(7);

				callableStmt.close();
			}

			// Calling the next SP to save security and personal guarantee
			// particulars
			// For Land
			callableStmt = conn.prepareCall("{?=call funcUpdateClaimPerGuarDetail(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			if ((claimSecurityIdAsOnNPA != null) && (!(claimSecurityIdAsOnNPA.equals("")))) {
				callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnNPA));
			}
			callableStmt.setString(3, ClaimConstants.CLM_SAPGD_PARTICULAR_LAND);
			double vallandnpa = dtlsAsOnNPA.getValueOfLand();
			callableStmt.setDouble(4, vallandnpa);
			callableStmt.setDouble(5, amntRealizedThruSecurity);
			callableStmt.setString(6, reasonForReduction);
			callableStmt.registerOutParameter(7, java.sql.Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing Land as on NPA. Error code is :" + errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			Log.log(4, "CPDAO", "saveClaimApplication()", "Successfully saved - Land for As on Dt of NPA");

			callableStmt = conn.prepareCall("{?=call funcUpdateClaimPerGuarDetail(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			if ((claimSecurityIdAsOnNPA != null) && (!claimSecurityIdAsOnNPA.equals(""))) {
				callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnNPA));
			}
			callableStmt.setString(3, "MACHINE");
			double valmachinenpa = dtlsAsOnNPA.getValueOfMachine();
			callableStmt.setDouble(4, valmachinenpa);
			callableStmt.setDouble(5, amntRealizedThruSecurity);
			callableStmt.setString(6, reasonForReduction);
			callableStmt.registerOutParameter(7, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing Machine as on NPA. Error code is :" + errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			Log.log(4, "CPDAO", "saveClaimApplication()", "Successfully saved - Machine for As on Dt of NPA");

			callableStmt = conn.prepareCall("{?=call funcUpdateClaimPerGuarDetail(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			if ((claimSecurityIdAsOnNPA != null) && (!claimSecurityIdAsOnNPA.equals(""))) {
				callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnNPA));
			}
			callableStmt.setString(3, "BUILDING");
			double valbldgnpa = dtlsAsOnNPA.getValueOfBuilding();
			callableStmt.setDouble(4, valbldgnpa);
			callableStmt.setDouble(5, amntRealizedThruSecurity);
			callableStmt.setString(6, reasonForReduction);
			callableStmt.registerOutParameter(7, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing Building as on NPA. Error code is :" + errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			Log.log(4, "CPDAO", "saveClaimApplication()", "Successfully saved - Building for As on Dt of NPA");

			callableStmt = conn.prepareCall("{?=call funcUpdateClaimPerGuarDetail(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			if ((claimSecurityIdAsOnNPA != null) && (!claimSecurityIdAsOnNPA.equals(""))) {
				callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnNPA));
			}
			callableStmt.setString(3, "OTHER FIXED MOVABLE ASSETS");
			double valfixedmovassetsnpa = dtlsAsOnNPA.getValueOfOtherFixedMovableAssets();
			callableStmt.setDouble(4, valfixedmovassetsnpa);
			callableStmt.setDouble(5, amntRealizedThruSecurity);
			callableStmt.setString(6, reasonForReduction);
			callableStmt.registerOutParameter(7, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing Fixed/Movable Assets as on NPA. Error code is :" + errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			Log.log(4, "CPDAO", "saveClaimApplication()",
					"Successfully saved - Fixed and Movable Assets for As on Dt of NPA");

			callableStmt = conn.prepareCall("{?=call funcUpdateClaimPerGuarDetail(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			if ((claimSecurityIdAsOnNPA != null) && (!claimSecurityIdAsOnNPA.equals(""))) {
				callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnNPA));
			}
			callableStmt.setString(3, "CURRENT ASSETS");
			double valcurrassetsnpa = dtlsAsOnNPA.getValueOfCurrentAssets();
			callableStmt.setDouble(4, valcurrassetsnpa);
			callableStmt.setDouble(5, amntRealizedThruSecurity);
			callableStmt.setString(6, reasonForReduction);
			callableStmt.registerOutParameter(7, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing Current Assets as on NPA. Error code is :" + errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			Log.log(4, "CPDAO", "saveClaimApplication()", "Successfully saved - Current Assets for As on Dt of NPA");

			callableStmt = conn.prepareCall("{?=call funcUpdateClaimPerGuarDetail(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			if ((claimSecurityIdAsOnNPA != null) && (!claimSecurityIdAsOnNPA.equals(""))) {
				callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnNPA));
			}
			callableStmt.setString(3, "OTHERS");
			double valothersnpa = dtlsAsOnNPA.getValueOfOthers();
			callableStmt.setDouble(4, valothersnpa);
			callableStmt.setDouble(5, amntRealizedThruSecurity);
			callableStmt.setString(6, reasonForReduction);
			callableStmt.registerOutParameter(7, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing Others as on NPA. Error code is :" + errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			Log.log(4, "CPDAO", "saveClaimApplication()", "Successfully saved - Other Assets for As on Dt of NPA");
			Log.log(4, "CPDAO", "saveClaimApplication()",
					"Done with saving Security and Personal Guarantee Dtls of Claim Application for as on dt of npa");

			Log.log(4, "CPDAO", "saveClaimApplication()",
					"Saving Security and Personal Guarantee Details of Claim Application for as on date of lodgement of claim");
			double networthAsOnLodgement = dtlsAsOnLodgeOfClaim.getNetworthOfGuarantors();
			String reasonForReductionAsOnLodgeOfClaim = dtlsAsOnLodgeOfClaim.getReasonsForReduction();
			String claimSecurityIdAsOnLodgeOfClaim = null;

			callableStmt = conn.prepareCall("{?=call funcUpdateClaimSecurityDetail(?,?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			callableStmt.setString(2, claimRefNumber);
			callableStmt.setString(3, "CLM");
			callableStmt.setDouble(4, networthAsOnLodgement);
			callableStmt.setString(5, reasonForReductionAsOnLodgeOfClaim);
			callableStmt.setDouble(6, amntRealizedThruInvocationPerGuar);
			callableStmt.registerOutParameter(7, Types.VARCHAR);
			callableStmt.registerOutParameter(8, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(8);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing As on Dt of Lodgemnt of Claim Dtl. Error code is :" + errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			if (status == 0) {
				claimSecurityIdAsOnLodgeOfClaim = callableStmt.getString(7);

				callableStmt.close();
			}

			callableStmt = conn.prepareCall("{?=call funcUpdateClaimPerGuarDetail(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			if ((claimSecurityIdAsOnLodgeOfClaim != null) && (!claimSecurityIdAsOnLodgeOfClaim.equals(""))) {
				callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnLodgeOfClaim));
			}
			callableStmt.setString(3, "LAND");
			double vallandlodgeclm = dtlsAsOnLodgeOfClaim.getValueOfLand();
			callableStmt.setDouble(4, vallandlodgeclm);
			callableStmt.setDouble(5, amntRealizedThruSecurity);
			callableStmt.setString(6, reasonForReduction);
			callableStmt.registerOutParameter(7, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing Land As on Dt of Lodgemnt of Claim Dtl. Error code is :"
								+ errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			Log.log(4, "CPDAO", "saveClaimApplication()",
					"Successfully saved - Land for As on Dt of Lodgement of First Claim");

			callableStmt = conn.prepareCall("{?=call funcUpdateClaimPerGuarDetail(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			if ((claimSecurityIdAsOnLodgeOfClaim != null) && (!claimSecurityIdAsOnLodgeOfClaim.equals(""))) {
				callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnLodgeOfClaim));
			}
			callableStmt.setString(3, "MACHINE");
			double valmclodgeclm = dtlsAsOnLodgeOfClaim.getValueOfMachine();
			callableStmt.setDouble(4, valmclodgeclm);
			callableStmt.setDouble(5, amntRealizedThruSecurity);
			callableStmt.setString(6, reasonForReduction);
			callableStmt.registerOutParameter(7, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing Machine As on Dt of Lodgemnt of Claim Dtl. Error code is :"
								+ errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			Log.log(4, "CPDAO", "saveClaimApplication()",
					"Successfully saved - Machine for As on Dt of Lodgement of First Claim");

			callableStmt = conn.prepareCall("{?=call funcUpdateClaimPerGuarDetail(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			if ((claimSecurityIdAsOnLodgeOfClaim != null) && (!claimSecurityIdAsOnLodgeOfClaim.equals(""))) {
				callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnLodgeOfClaim));
			}
			callableStmt.setString(3, "BUILDING");
			double valbldglodgeclm = dtlsAsOnLodgeOfClaim.getValueOfBuilding();
			callableStmt.setDouble(4, valbldglodgeclm);
			callableStmt.setDouble(5, amntRealizedThruSecurity);
			callableStmt.setString(6, reasonForReduction);
			callableStmt.registerOutParameter(7, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing Building As on Dt of Lodgemnt of Claim Dtl. Error code is :"
								+ errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			Log.log(4, "CPDAO", "saveClaimApplication()",
					"Successfully saved - Building for As on Dt of Lodgement of First Claim");

			callableStmt = conn.prepareCall("{?=call funcUpdateClaimPerGuarDetail(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			if ((claimSecurityIdAsOnLodgeOfClaim != null) && (!claimSecurityIdAsOnLodgeOfClaim.equals(""))) {
				callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnLodgeOfClaim));
			}
			callableStmt.setString(3, "OTHER FIXED MOVABLE ASSETS");
			double valfixedmovassetslodgeclm = dtlsAsOnLodgeOfClaim.getValueOfOtherFixedMovableAssets();
			callableStmt.setDouble(4, valfixedmovassetslodgeclm);
			callableStmt.setDouble(5, amntRealizedThruSecurity);
			callableStmt.setString(6, reasonForReduction);
			callableStmt.registerOutParameter(7, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing Fixed Movable Assets As on Dt of Lodgemnt of Claim Dtl. Error code is :"
								+ errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			Log.log(4, "CPDAO", "saveClaimApplication()",
					"Successfully saved - Fixed Assets for As on Dt of Lodgement of First Claim");

			callableStmt = conn.prepareCall("{?=call funcUpdateClaimPerGuarDetail(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			if ((claimSecurityIdAsOnLodgeOfClaim != null) && (!claimSecurityIdAsOnLodgeOfClaim.equals(""))) {
				callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnLodgeOfClaim));
			}
			callableStmt.setString(3, "CURRENT ASSETS");
			double valcurrassetslodgeclm = dtlsAsOnLodgeOfClaim.getValueOfCurrentAssets();
			callableStmt.setDouble(4, valcurrassetslodgeclm);
			callableStmt.setDouble(5, amntRealizedThruSecurity);
			callableStmt.setString(6, reasonForReduction);
			callableStmt.registerOutParameter(7, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing Current Assets As on Dt of Lodgemnt of Claim Dtl. Error code is :"
								+ errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			Log.log(4, "CPDAO", "saveClaimApplication()",
					"Successfully saved - Current Assets for As on Dt of Lodgement of First Claim");

			callableStmt = conn.prepareCall("{?=call funcUpdateClaimPerGuarDetail(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			if ((claimSecurityIdAsOnLodgeOfClaim != null) && (!claimSecurityIdAsOnLodgeOfClaim.equals(""))) {
				callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnLodgeOfClaim));
			}
			callableStmt.setString(3, "OTHERS");
			double valotherslodgeclm = dtlsAsOnLodgeOfClaim.getValueOfOthers();
			callableStmt.setDouble(4, valotherslodgeclm);
			callableStmt.setDouble(5, amntRealizedThruSecurity);
			callableStmt.setString(6, reasonForReduction);
			callableStmt.registerOutParameter(7, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing Others As on Dt of Lodgemnt of Claim Dtl. Error code is :"
								+ errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			Log.log(4, "CPDAO", "saveClaimApplication()",
					"Successfully saved - Other Assets for As on Dt of Lodgement of First Claim");
			Log.log(4, "CPDAO", "saveClaimApplication()",
					"Done with saving Security and Personal Guarantee Details of Claim Application for as on dt of lodgement of claim");

			Log.log(4, "CPDAO", "saveClaimApplication()",
					"Saving Security and Personal Guarantee Details of Claim Application for as on dt of lodgement of second claim.....");
			if (claimApplication.getSecondInstallment()) {
				double networthAsOnLodgmntOfSecClm = dtlsAsOnLodgemntOfSecClm.getNetworthOfGuarantors();
				amntRealizedThruInvocationPerGuar = dtlsAsOnLodgemntOfSecClm.getAmtRealisedPersonalGuarantee();

				String reasonForReductionAsOnLodgeOfSecClaim = "";
				String claimSecurityIdAsOnLodgeOfSecClaim = null;

				callableStmt = conn.prepareCall("{?=call funcUpdateClaimSecurityDetail(?,?,?,?,?,?,?)}");
				callableStmt.registerOutParameter(1, Types.INTEGER);
				callableStmt.setString(2, claimRefNumber);
				callableStmt.setString(3, "SCLM");
				callableStmt.setDouble(4, networthAsOnLodgmntOfSecClm);
				callableStmt.setString(5, reasonForReductionAsOnLodgeOfSecClaim);
				callableStmt.setDouble(6, amntRealizedThruInvocationPerGuar);
				callableStmt.registerOutParameter(7, Types.VARCHAR);
				callableStmt.registerOutParameter(8, Types.VARCHAR);

				callableStmt.execute();
				status = callableStmt.getInt(1);
				errorCode = callableStmt.getString(8);
				if (status == 1) {
					Log.log(2, "CPDAO", "saveClaimApplication()",
							"SP returns a 1 in storing As on Dt of Lodgemnt of Second Claim Dtl. Error code is :"
									+ errorCode);
					callableStmt.close();
					try {
						conn.rollback();
					} catch (SQLException sqlex) {
						throw new DatabaseException(sqlex.getMessage());
					}
					throw new DatabaseException(errorCode);
				}
				if (status == 0) {
					claimSecurityIdAsOnLodgeOfSecClaim = callableStmt.getString(7);

					callableStmt.close();
				}

				amntRealizedThruSecurity = dtlsAsOnLodgemntOfSecClm.getAmtRealisedLand();
				reasonForReduction = dtlsAsOnLodgemntOfSecClm.getReasonsForReductionLand();
				callableStmt = conn.prepareCall("{?=call funcUpdateClaimPerGuarDetail(?,?,?,?,?,?)}");
				callableStmt.registerOutParameter(1, Types.INTEGER);
				if ((claimSecurityIdAsOnLodgeOfSecClaim != null) && (!claimSecurityIdAsOnLodgeOfSecClaim.equals(""))) {
					callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnLodgeOfSecClaim));
				}
				callableStmt.setString(3, "LAND");
				double vallandlodgeOfSecclm = dtlsAsOnLodgemntOfSecClm.getValueOfLand();
				callableStmt.setDouble(4, vallandlodgeOfSecclm);
				callableStmt.setDouble(5, amntRealizedThruSecurity);
				callableStmt.setString(6, reasonForReduction);
				callableStmt.registerOutParameter(7, Types.VARCHAR);

				callableStmt.execute();
				status = callableStmt.getInt(1);
				errorCode = callableStmt.getString(7);
				if (status == 1) {
					Log.log(2, "CPDAO", "saveClaimApplication()",
							"SP returns a 1 in storing Land As on Dt of Lodgemnt of Second Claim Dtl. Error code is :"
									+ errorCode);
					callableStmt.close();
					try {
						conn.rollback();
					} catch (SQLException sqlex) {
						throw new DatabaseException(sqlex.getMessage());
					}
					throw new DatabaseException(errorCode);
				}
				callableStmt.close();
				Log.log(4, "CPDAO", "saveClaimApplication()",
						"Successfully saved - Land for As on Dt of Lodgement of Second Claim");

				amntRealizedThruSecurity = dtlsAsOnLodgemntOfSecClm.getAmtRealisedMachine();
				reasonForReduction = dtlsAsOnLodgemntOfSecClm.getReasonsForReductionMachine();
				callableStmt = conn.prepareCall("{?=call funcUpdateClaimPerGuarDetail(?,?,?,?,?,?)}");
				callableStmt.registerOutParameter(1, Types.INTEGER);
				if ((claimSecurityIdAsOnLodgeOfSecClaim != null) && (!claimSecurityIdAsOnLodgeOfSecClaim.equals(""))) {
					callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnLodgeOfSecClaim));
				}
				callableStmt.setString(3, "MACHINE");
				double valmclodgeOfSecclm = dtlsAsOnLodgemntOfSecClm.getValueOfMachine();
				callableStmt.setDouble(4, valmclodgeOfSecclm);
				callableStmt.setDouble(5, amntRealizedThruSecurity);
				callableStmt.setString(6, reasonForReduction);
				callableStmt.registerOutParameter(7, Types.VARCHAR);

				callableStmt.execute();
				status = callableStmt.getInt(1);
				errorCode = callableStmt.getString(7);
				if (status == 1) {
					Log.log(2, "CPDAO", "saveClaimApplication()",
							"SP returns a 1 in storing Machine As on Dt of Lodgemnt of Second Claim Dtl. Error code is :"
									+ errorCode);
					callableStmt.close();
					try {
						conn.rollback();
					} catch (SQLException sqlex) {
						throw new DatabaseException(sqlex.getMessage());
					}
					throw new DatabaseException(errorCode);
				}
				callableStmt.close();
				Log.log(4, "CPDAO", "saveClaimApplication()",
						"Successfully saved - Machine for As on Dt of Lodgement of Second Claim");

				amntRealizedThruSecurity = dtlsAsOnLodgemntOfSecClm.getAmtRealisedBuilding();
				reasonForReduction = dtlsAsOnLodgemntOfSecClm.getReasonsForReductionBuilding();
				callableStmt = conn.prepareCall("{?=call funcUpdateClaimPerGuarDetail(?,?,?,?,?,?)}");
				callableStmt.registerOutParameter(1, Types.INTEGER);
				if ((claimSecurityIdAsOnLodgeOfSecClaim != null) && (!claimSecurityIdAsOnLodgeOfSecClaim.equals(""))) {
					callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnLodgeOfSecClaim));
				}
				callableStmt.setString(3, "BUILDING");
				double valbldglodgeOfSecclm = dtlsAsOnLodgemntOfSecClm.getValueOfBuilding();
				callableStmt.setDouble(4, valbldglodgeOfSecclm);
				callableStmt.setDouble(5, amntRealizedThruSecurity);
				callableStmt.setString(6, reasonForReduction);
				callableStmt.registerOutParameter(7, Types.VARCHAR);

				callableStmt.execute();
				status = callableStmt.getInt(1);
				errorCode = callableStmt.getString(7);
				if (status == 1) {
					Log.log(2, "CPDAO", "saveClaimApplication()",
							"SP returns a 1 in storing Building As on Dt of Lodgemnt of Second Claim Dtl. Error code is :"
									+ errorCode);
					callableStmt.close();
					try {
						conn.rollback();
					} catch (SQLException sqlex) {
						throw new DatabaseException(sqlex.getMessage());
					}
					throw new DatabaseException(errorCode);
				}
				callableStmt.close();
				Log.log(4, "CPDAO", "saveClaimApplication()",
						"Successfully saved - Building for As on Dt of Lodgement of Second Claim");

				amntRealizedThruSecurity = dtlsAsOnLodgemntOfSecClm.getAmtRealisedFixed();
				reasonForReduction = dtlsAsOnLodgemntOfSecClm.getReasonsForReductionFixed();
				callableStmt = conn.prepareCall("{?=call funcUpdateClaimPerGuarDetail(?,?,?,?,?,?)}");
				callableStmt.registerOutParameter(1, Types.INTEGER);
				if ((claimSecurityIdAsOnLodgeOfSecClaim != null) && (!claimSecurityIdAsOnLodgeOfSecClaim.equals(""))) {
					callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnLodgeOfSecClaim));
				}
				callableStmt.setString(3, "OTHER FIXED MOVABLE ASSETS");
				double valfixedmovassetslodgeOfSecclm = dtlsAsOnLodgemntOfSecClm.getValueOfOtherFixedMovableAssets();
				callableStmt.setDouble(4, valfixedmovassetslodgeOfSecclm);
				callableStmt.setDouble(5, amntRealizedThruSecurity);
				callableStmt.setString(6, reasonForReduction);
				callableStmt.registerOutParameter(7, Types.VARCHAR);

				callableStmt.execute();
				status = callableStmt.getInt(1);
				errorCode = callableStmt.getString(7);
				if (status == 1) {
					Log.log(2, "CPDAO", "saveClaimApplication()",
							"SP returns a 1 in storing Fixed/Movable Assets As on Dt of Lodgemnt of Second Claim Dtl. Error code is :"
									+ errorCode);
					callableStmt.close();
					try {
						conn.rollback();
					} catch (SQLException sqlex) {
						throw new DatabaseException(sqlex.getMessage());
					}
					throw new DatabaseException(errorCode);
				}
				callableStmt.close();
				Log.log(4, "CPDAO", "saveClaimApplication()",
						"Successfully saved - Fixed Assets for As on Dt of Lodgement of Second Claim");

				amntRealizedThruSecurity = dtlsAsOnLodgemntOfSecClm.getAmtRealisedCurrent();
				reasonForReduction = dtlsAsOnLodgemntOfSecClm.getReasonsForReductionCurrent();
				callableStmt = conn.prepareCall("{?=call funcUpdateClaimPerGuarDetail(?,?,?,?,?,?)}");
				callableStmt.registerOutParameter(1, Types.INTEGER);
				if ((claimSecurityIdAsOnLodgeOfSecClaim != null) && (!claimSecurityIdAsOnLodgeOfSecClaim.equals(""))) {
					callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnLodgeOfSecClaim));
				}
				callableStmt.setString(3, "CURRENT ASSETS");
				double valcurrassetslodgeOfSecclm = dtlsAsOnLodgemntOfSecClm.getValueOfCurrentAssets();
				callableStmt.setDouble(4, valcurrassetslodgeOfSecclm);
				callableStmt.setDouble(5, amntRealizedThruSecurity);
				callableStmt.setString(6, reasonForReduction);
				callableStmt.registerOutParameter(7, Types.VARCHAR);

				callableStmt.execute();
				status = callableStmt.getInt(1);
				errorCode = callableStmt.getString(7);
				if (status == 1) {
					Log.log(2, "CPDAO", "saveClaimApplication()",
							"SP returns a 1 in storing Current Assets As on Dt of Lodgemnt of Second Claim Dtl. Error code is :"
									+ errorCode);
					callableStmt.close();
					try {
						conn.rollback();
					} catch (SQLException sqlex) {
						throw new DatabaseException(sqlex.getMessage());
					}
					throw new DatabaseException(errorCode);
				}
				callableStmt.close();
				Log.log(4, "CPDAO", "saveClaimApplication()",
						"Successfully saved - Current Assets for As on Dt of Lodgement of Second Claim");

				amntRealizedThruSecurity = dtlsAsOnLodgemntOfSecClm.getAmtRealisedOthers();
				reasonForReduction = dtlsAsOnLodgemntOfSecClm.getReasonsForReductionOthers();
				callableStmt = conn.prepareCall("{?=call funcUpdateClaimPerGuarDetail(?,?,?,?,?,?)}");
				callableStmt.registerOutParameter(1, Types.INTEGER);
				if ((claimSecurityIdAsOnLodgeOfSecClaim != null) && (!claimSecurityIdAsOnLodgeOfSecClaim.equals(""))) {
					callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnLodgeOfSecClaim));
				}
				callableStmt.setString(3, "OTHERS");
				double valotherslodgeOfSecclm = dtlsAsOnLodgemntOfSecClm.getValueOfOthers();
				callableStmt.setDouble(4, valotherslodgeOfSecclm);
				callableStmt.setDouble(5, amntRealizedThruSecurity);
				callableStmt.setString(6, reasonForReduction);
				callableStmt.registerOutParameter(7, Types.VARCHAR);

				callableStmt.execute();
				status = callableStmt.getInt(1);
				errorCode = callableStmt.getString(7);
				if (status == 1) {
					Log.log(2, "CPDAO", "saveClaimApplication()",
							"SP returns a 1 in storing Others As on Dt of Lodgemnt of Second Claim Dtl. Error code is :"
									+ errorCode);
					callableStmt.close();
					try {
						conn.rollback();
					} catch (SQLException sqlex) {
						throw new DatabaseException(sqlex.getMessage());
					}
					throw new DatabaseException(errorCode);
				}
				callableStmt.close();
				Log.log(4, "CPDAO", "saveClaimApplication()",
						"Successfully saved - Other Assets for As on Dt of Lodgement of Second Claim");
			}
			Log.log(4, "CPDAO", "saveClaimApplication()",
					"Done with saving Security and Personal Guarantee Details of Claim Application for as on dt of lodgement of second claim");

			Log.log(4, "CPDAO", "saveClaimApplication()", "Saving Claim Summary Details of Claim Application");
			ArrayList claimsummarydtls = claimApplication.getClaimSummaryDtls();
			double appliedamnt = 0.0D;
			ClaimSummaryDtls dtls = null;
			if (claimsummarydtls != null) {
				for (int i = 0; i < claimsummarydtls.size(); i++) {
					dtls = (ClaimSummaryDtls) claimsummarydtls.get(i);
					cgpan = dtls.getCgpan();
					appliedamnt = dtls.getAmount();

					callableStmt = conn.prepareCall("{? = call funcUpdateClaimAmount(?,?,?,?)}");
					callableStmt.registerOutParameter(1, Types.INTEGER);
					callableStmt.setString(2, claimRefNumber);
					callableStmt.setString(3, cgpan);
					callableStmt.setDouble(4, appliedamnt);
					callableStmt.registerOutParameter(5, Types.VARCHAR);

					callableStmt.execute();
					status = callableStmt.getInt(1);
					errorCode = callableStmt.getString(5);
					if (status == 1) {
						Log.log(2, "CPDAO", "saveClaimApplication()",
								"SP returns a 1 in storing Claim Summary Details. Error code is :" + errorCode);
						callableStmt.close();
						try {
							conn.rollback();
						} catch (SQLException sqlex) {
							throw new DatabaseException(sqlex.getMessage());
						}
						throw new DatabaseException(errorCode);
					}
					callableStmt.close();
				}
			}

			NPADetails npaDtls = gmprocessor.getNPADetails(borrowerId);
			if (claimApplication.getFirstInstallment()) {
				updateNPADetails(npaDtls);
				updateLegalProceedingDetails(legalProceedingsDetail);
				if (map != null) {
					String borowrId = (String) map.get("BORROWERID");
					String itpanOfChiefPromoter = (String) map.get("Clm_ITPAN_of_Chief_Promoter");
					saveITPANDetail(borowrId, itpanOfChiefPromoter);
				}
			}
			if (claimApplication.getSecondInstallment()) {
				if (npaDtls != null) {
					npaDtls.setWillfulDefaulter(whethereWillFulDfaulter);
					npaDtls.setDtOfConclusionOfRecProc(dtOfConclusionOfRecProc);
					npaDtls.setWhetherWrittenOff(whetherAccntWrittenOffBooks);
					npaDtls.setDtOnWhichAccntWrittenOff(dtOfWrittenOffBooks);

					updateNPADetails(npaDtls);

					updateLegalProceedingDetails(legalProceedingsDetail);
				}
			}
			
			//commented by AbhishekSikarwar
			//updateRecallAndLegalAttachments(claimApplication, claimRefNumber, flag);

			Log.log(4, "CPDAO", "saveClaimApplication()",
					"Done with saving Claim Summary Details of Claim Application");
			// conn.commit();

		} catch (DatabaseException sqlexception) {
			hasExceptionOccured = true;
			Log.log(4, "CPDAO", "saveClaimApplication()", "Error in saving the claim application.");
			try {
				callableStmt.close();
				conn.rollback();
			} catch (SQLException sqlex) {
				throw new DatabaseException(sqlex.getMessage());
			}
			throw new DatabaseException(sqlexception.getMessage());
		} catch (SQLException sqlexception) {
			hasExceptionOccured = true;
			Log.log(4, "CPDAO", "saveClaimApplication()", "Error in saving the claim application.");
			try {
				callableStmt.close();
				conn.rollback();
			} catch (SQLException sqlex) {
				throw new DatabaseException(sqlex.getMessage());
			}
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(4, "CPDAO", "saveClaimApplication()", "Exited!");
	}

	public Vector getCGPANDetailsForBidClaim(String borrowerId, String memberId) throws DatabaseException {
		Log.log(4, "CPDAO", "getCGPANDetailsForBorrowerId()", "Entered!");
		ResultSet rs = null;
		HashMap cgpandetails = null;
		Vector allcgpandetails = new Vector();
		ApplicationDAO appDAO = new ApplicationDAO();
		Application application = null;
		CallableStatement callableStmt = null;
		Connection conn = null;
		int status = -1;
		String errorCode = null;

		try {
			conn = DBConnection.getConnection();
			callableStmt = conn.prepareCall("{? = call packGetCGPANForBorrower.funcGetCGPANForBorrower(?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt.setString(2, borrowerId);
			callableStmt.registerOutParameter(3, Constants.CURSOR);
			callableStmt.registerOutParameter(4, java.sql.Types.VARCHAR);
			// System.out.println("before call
			// packGetCGPANForBorrower.funcGetCGPANForBidClaim:"+
			// new java.util.Date());
			callableStmt.execute();
			// System.out.println("after call
			// packGetCGPANForBorrower.funcGetCGPANForBidClaim:"+
			// new java.util.Date());
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(4);

			if (status == Constants.FUNCTION_FAILURE) {
				Log.log(Log.ERROR, "CPDAO", "getCGPANDetailsForBorrowerId()",
						"SP returns a 1. Error code is :" + errorCode);

				callableStmt.close();
				throw new DatabaseException(errorCode);
			}
			if (status == 0) {
				rs = (ResultSet) callableStmt.getObject(3);
				while (rs.next()) {
					String cgpan = null;
					double approvedAmount = 0.0D;
					double enhancedApprovedAmount = 0.0D;
					double reapproveAmount = 0.0D;
					String appRefNum = null;
					String loantype = null;
					java.util.Date guarStartDt = null;
					String applicationStatus = null;

					cgpan = rs.getString(1);
					if (cgpan != null) {
						appRefNum = getAppRefNumber(cgpan);
						application = appDAO.getAppForAppRef(null, appRefNum);
						reapproveAmount = application.getReapprovedAmount();
						approvedAmount = rs.getDouble(2);
						enhancedApprovedAmount = rs.getDouble(3);
						loantype = rs.getString(4);
						guarStartDt = rs.getDate(5);
						applicationStatus = rs.getString(6);

						if (cgpan != null) {
							cgpandetails = new HashMap();
							cgpandetails.put("CGPAN", cgpan);
							if (reapproveAmount == 0.0D) {
								cgpandetails.put("ApprovedAmount", new Double(approvedAmount));
							}
							if (reapproveAmount > 0.0D) {
								cgpandetails.put("ApprovedAmount", new Double(reapproveAmount));
							}
							cgpandetails.put("EnhancedApprovedAmount", new Double(enhancedApprovedAmount));
							cgpandetails.put("LoanType", loantype);
							cgpandetails.put("GUARANTEESTARTDT", guarStartDt);
							cgpandetails.put("APPLICATION_STATUS", applicationStatus);
							allcgpandetails.add(cgpandetails);
						}
					}
				}

				rs.close();
				rs = null;

				callableStmt.close();
				callableStmt = null;
			}
		} catch (SQLException sqlexception) {
			Log.log(2, "CPDAO", "getCGPANDetailsForBorrowerId()", "Error retrieving CGPAN Details for the Borrower!");
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			// DBConnection.freeConnection(conn);
		}
		String clmRefNumber = null;
		Statement stmt = null;
		String query = "select clm_ref_no from claim_detail where mem_bnk_id||mem_zne_id||mem_brn_id='" + memberId
				+ "' and bid='" + borrowerId + "'";
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			if (rs.next()) {
				clmRefNumber = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			rs = null;
			try {
				stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			stmt = null;
			DBConnection.freeConnection(conn);
		}

		/* DISBURSEMENT CHECKING */
		int type = 0;
		ArrayList disbursementDetails = null;
		ArrayList repaymentDetails = null;
		ArrayList disbDtls = null;
		ArrayList nestedDtls = null;
		PeriodicInfo pi = null;
		if (clmRefNumber == null) {
			disbursementDetails = getDisbursementDetails(borrowerId, type);
			if (disbursementDetails != null) {
				if (disbursementDetails.size() > 0) {
					Log.log(4, "CPDAO", "getCGPANDetailsForBorrowerId()",
							"Size of disbursementDetails Dtls :" + disbursementDetails.size());
					for (int i = 0; i < disbursementDetails.size(); i++) {
						pi = (PeriodicInfo) disbursementDetails.get(i);
						if (pi != null) {
							disbDtls = pi.getDisbursementDetails();
							if (disbDtls != null) {
								Log.log(4, "CPDAO", "getCGPANDetailsForBorrowerId()",
										"Size of disbDtls Dtls :" + disbDtls.size());
								if (disbDtls.size() > 0) {
									for (int j = 0; j < disbDtls.size(); j++) {
										Disbursement dsbrsmnt = (Disbursement) disbDtls.get(j);
										if (dsbrsmnt != null) {
											nestedDtls = dsbrsmnt.getDisbursementAmounts();
											if ((nestedDtls == null) || (nestedDtls.size() != 0)) {
												String lastcgpan = null;
												java.util.Date lastDsbrsmntDt = null;
												java.util.Date presentDsbrsmntDt = null;
												Log.log(4, "CPDAO", "getCGPANDetailsForBorrowerId()",
														"Size of Nested Dtls :" + nestedDtls.size());
												for (int k = 0; k < nestedDtls.size(); k++) {
													DisbursementAmount dbamnt = (DisbursementAmount) nestedDtls.get(k);
													if (dbamnt != null) {
														String cgpan = dbamnt.getCgpan();
														Log.log(4, "CPDAO", "getCGPANDetailsForBorrowerId()",
																"cgpan :" + cgpan);

														presentDsbrsmntDt = dbamnt.getDisbursementDate();
														Log.log(4, "CPDAO", "getCGPANDetailsForBorrowerId()",
																"presentDsbrsmntDt :" + presentDsbrsmntDt);
														if (k == 0) {
															lastcgpan = cgpan;
															lastDsbrsmntDt = presentDsbrsmntDt;
														}
														if (k > 0) {
															if (cgpan.equals(lastcgpan)) {
																if (presentDsbrsmntDt != null) {
																	if (presentDsbrsmntDt
																			.compareTo(lastDsbrsmntDt) > 0) {
																		lastDsbrsmntDt = presentDsbrsmntDt;
																	}
																	if (presentDsbrsmntDt
																			.compareTo(lastDsbrsmntDt) == 0) {
																		lastDsbrsmntDt = presentDsbrsmntDt;
																	}

																}

															} else {
																continue;
															}

														}

														HashMap hashmap = null;
														Log.log(4, "CPDAO", "getCGPANDetailsForBorrowerId()",
																"allcgpandetails size :" + allcgpandetails.size());
														for (int m = 0; m < allcgpandetails.size(); m++) {
															hashmap = (HashMap) allcgpandetails.elementAt(m);
															Log.log(4, "CPDAO", "getCGPANDetailsForBorrowerId()",
																	"priting hasmap in allcgpandetails vector :"
																			+ hashmap);
															if (hashmap != null) {
																if (hashmap.containsKey("CGPAN")) {
																	String cgpn = (String) hashmap.get("CGPAN");

																	if ((cgpn != null) && (!cgpn.equals(""))) {
																		if (cgpn.equals(lastcgpan)) {
																			hashmap = (HashMap) allcgpandetails
																					.remove(m);
																			hashmap.put("LASTDSBRSMNTDT",
																					lastDsbrsmntDt);
																			allcgpandetails.add(m, hashmap);
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
			disbDtls = null;
			nestedDtls = null;
		}
		/* CLAIM DETAIL CHECKING */
		if (clmRefNumber != null) {
			Vector tcDetails = null;
			ClaimDetail cd = getDetailsForClaimRefNumber(clmRefNumber);
			if (cd != null) {
				tcDetails = cd.getTcDetails();

				if (tcDetails != null) {
					for (int i = 0; i < tcDetails.size(); i++) {
						HashMap map = (HashMap) tcDetails.elementAt(i);
						if (map != null) {
							String pan = (String) map.get("CGPAN");
							java.util.Date dsbrsDate = (java.util.Date) map.get("LASTDSBRSMNTDT");
							HashMap hashmap = null;
							for (int m = 0; m < allcgpandetails.size(); m++) {
								hashmap = (HashMap) allcgpandetails.elementAt(m);
								Log.log(4, "CPDAO", "getCGPANDetailsForBorrowerId()",
										"priting hasmap in allcgpandetails vector :" + hashmap);
								if (hashmap != null) {
									if (hashmap.containsKey("CGPAN")) {
										String cgpn = (String) hashmap.get("CGPAN");

										if ((cgpn != null) && (!cgpn.equals(""))) {
											if (cgpn.equals(pan)) {
												hashmap = (HashMap) allcgpandetails.remove(m);
												hashmap.put("LASTDSBRSMNTDT", dsbrsDate);
												allcgpandetails.add(m, hashmap);
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}

		type = 1;
		HashMap hashmap = null;
		ArrayList repayments = null;
		ArrayList repayAmounts = null;
		Repayment repayment = null;
		RepaymentAmount repayamntAmnt = null;
		for (int m = 0; m < allcgpandetails.size(); m++) {
			hashmap = (HashMap) allcgpandetails.elementAt(m);
			if (hashmap != null) {
				if (hashmap.containsKey("CGPAN")) {
					String cgpn = (String) hashmap.get("CGPAN");
					if ((cgpn != null) && (!cgpn.equals(""))) {
						repaymentDetails = getCPRepaymentDetails(cgpn, type);
						for (int i = 0; i < repaymentDetails.size(); i++) {
							pi = (PeriodicInfo) repaymentDetails.get(i);
							if (pi != null) {
								repayments = pi.getRepaymentDetails();
								if ((repayments == null) || (repayments.size() != 0)) {
									double totalRepaidAmnt = 0.0D;
									for (int j = 0; j < repayments.size(); j++) {
										repayment = (Repayment) repayments.get(j);
										if (repayment != null) {
											repayAmounts = repayment.getRepaymentAmounts();
											if ((repayAmounts == null) || (repayAmounts.size() != 0)) {
												String lastPan = null;

												for (int k = 0; k < repayAmounts.size(); k++) {
													repayamntAmnt = (RepaymentAmount) repayAmounts.get(k);
													String pan = repayamntAmnt.getCgpan();

													if (pan != null) {
														double repaidAmnt = repayamntAmnt.getRepaymentAmount();

														if (k == 0) {
															lastPan = pan;
															totalRepaidAmnt = repaidAmnt + totalRepaidAmnt;
														} else if (pan.equals(lastPan)) {
															lastPan = pan;
															totalRepaidAmnt = repaidAmnt + totalRepaidAmnt;
															continue;
														}

														hashmap = (HashMap) allcgpandetails.remove(m);
														hashmap.put("AMNT_REPAID", new Double(totalRepaidAmnt));
														allcgpandetails.add(m, hashmap);
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}

		Log.log(4, "CPDAO", "getCGPANDetailsForBorrowerId()", "Exited!");
		for (int i = 0; i < allcgpandetails.size(); i++) {
			HashMap m = (HashMap) allcgpandetails.elementAt(i);
			if (m != null) {
				Log.log(4, "CPDAO", "getCGPANDetailsForBorrowerId()", "------> Printing the hashmap from CPDAO:" + m);
			}
		}

		return allcgpandetails;
	}

	public Vector getCGPANsForClaim(String borrowerId, String memberId) throws DatabaseException {
		// System.out.println("----GMDAO----getCGPANDetailsNPA-------");
		Log.log(Log.INFO, "CPDAO", "getCGPANDetailsForBorrowerId()", "Entered!");
		Vector allcgpandetails = new Vector();
		String query = null;

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Map cgpanMap = null;

		try {
			conn = DBConnection.getConnection();

			query = " select c2.cgpan,DECODE(APP_REAPPROVE_AMOUNT,NULL,APP_APPROVED_AMOUNT,APP_REAPPROVE_AMOUNT) app_approved_amount,c.clm_status,app_status,app_loan_type "
					+ " from claim_detail_temp c,claim_tc_detail_temp c2,application_detail a where c.clm_ref_no=c2.clm_ref_no "
					+ " and app_status in('AP','EX') and a.cgpan=c2.cgpan and bid='" + borrowerId + "'" + " union all "
					+ " select c2.cgpan,DECODE(APP_REAPPROVE_AMOUNT,NULL,APP_APPROVED_AMOUNT,APP_REAPPROVE_AMOUNT) app_approved_amount,c.clm_status,app_status,app_loan_type "
					+ " from claim_detail_temp c,claim_wc_detail_temp c2,application_detail a where c.clm_ref_no=c2.clm_ref_no "
					+ " and app_status in('AP','EX') and a.cgpan=c2.cgpan and bid='" + borrowerId + "'";

			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				cgpanMap = new HashMap();
				String cgpan = rs.getString("cgpan");
				double approvedAmount = rs.getDouble("app_approved_amount");
				String appstatus = rs.getString("app_status");
				String clmstatus = rs.getString("clm_status");
				String loanType = rs.getString("app_loan_type");

				cgpanMap.put(ClaimConstants.CLM_CGPAN, cgpan);
				cgpanMap.put(ClaimConstants.CLM_APPLICATION_APPRVD_AMNT, new Double(approvedAmount));
				cgpanMap.put("LoanType", loanType);
				// if("EX".equals(appstatus) && "TC".equals(clmstatus)){
				// allcgpandetails.add(cgpanMap);
				// }else{
				// continue;
				// }
				allcgpandetails.add(cgpanMap);
			}
		} catch (SQLException sqlexception) {
			Log.log(Log.ERROR, "CPDAO", "getCGPANDetailsForBorrowerId()",
					"Error retrieving CGPAN Details for the Borrower!");
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}
		return allcgpandetails;
	}

	//commented by AbhishekSikarwar
/*	public void uploadClaimFiles(String clmRefeneceNumber, String bid, String userID, ArrayList claimFiles,
			ClaimFiles file) throws FileNotFoundException, IOException, DatabaseException {

		FormFile[] diligenceFiles = file.getDiligenceReportFiles();
		FormFile[] postInspectionFiles = file.getPostInspectionReportFiles();
		FormFile[] postNpaFiles = file.getPostNpaReportFiles();
		FormFile[] idproofFiles = file.getIdProofFiles();
		FormFile[] otherFiles = file.getOtherFiles();

		String npa_file_name = "NPA_FILE_" + bid + "_" + file.getNpaReportFile().getFileName().trim();

		String diligence_file_name = "";
		if (diligenceFiles[0] != null)
			if (!(diligenceFiles[0].getFileName().equals("")))
				diligence_file_name = "DILIGENCE_" + bid + "_" + diligenceFiles[0].getFileName().trim();
		String diligence_file_name1 = "";
		if (diligenceFiles[1] != null)
			if (!(diligenceFiles[1].getFileName().equals("")))
				diligence_file_name1 = "DILIGENCE1_" + bid + "_" + diligenceFiles[1].getFileName().trim();
		String diligence_file_name2 = "";
		if (diligenceFiles[2] != null)
			if (!(diligenceFiles[2].getFileName().equals("")))
				diligence_file_name2 = "DILIGENCE2_" + bid + "_" + diligenceFiles[2].getFileName().trim();

		String post_insp_file_name = "";
		if (postInspectionFiles[0] != null)
			if (!(postInspectionFiles[0].getFileName().equals("")))
				post_insp_file_name = "POST_INSPEC_" + bid + "_" + postInspectionFiles[0].getFileName().trim();
		String post_insp_file_name1 = "";
		if (postInspectionFiles[1] != null)
			if (!(postInspectionFiles[1].getFileName().equals("")))
				post_insp_file_name1 = "POST_INSPEC1_" + bid + "_" + postInspectionFiles[1].getFileName().trim();
		String post_insp_file_name2 = "";
		if (postInspectionFiles[2] != null)
			if (!(postInspectionFiles[2].getFileName().equals("")))
				post_insp_file_name2 = "POST_INSPEC2_" + bid + "_" + postInspectionFiles[2].getFileName().trim();

		String post_npa_file_name = "";
		if (postNpaFiles[0] != null)
			if (!(postNpaFiles[0].getFileName().equals("")))
				post_npa_file_name = "POST_NPA_" + bid + "_" + postNpaFiles[0].getFileName().trim();
		String post_npa_file_name1 = "";
		if (postNpaFiles[1] != null)
			if (!(postNpaFiles[1].getFileName().equals("")))
				post_npa_file_name1 = "POST_NPA1_" + bid + "_" + postNpaFiles[1].getFileName().trim();
		String post_npa_file_name2 = "";
		if (postNpaFiles[2] != null)
			if (!(postNpaFiles[2].getFileName().equals("")))
				post_npa_file_name2 = "POST_NPA2_" + bid + "_" + postNpaFiles[2].getFileName().trim();

		String id_file_name = "";
		if (idproofFiles[0] != null)
			if (!(idproofFiles[0].getFileName().equals("")))
				id_file_name = "IDPROOF_" + bid + "_" + idproofFiles[0].getFileName().trim();
		String id_file_name1 = "";
		if (idproofFiles[1] != null)
			if (!(idproofFiles[1].getFileName().equals("")))
				id_file_name1 = "IDPROOF1_" + bid + "_" + idproofFiles[1].getFileName().trim();
		String id_file_name2 = "";
		if (idproofFiles[2] != null)
			if (!(idproofFiles[2].getFileName().equals("")))
				id_file_name2 = "IDPROOF2_" + bid + "_" + idproofFiles[2].getFileName().trim();

		String other_file_name = "";
		if (otherFiles[0] != null)
			if (!(otherFiles[0].getFileName().equals("")))
				other_file_name = "OTHER_" + bid + "_" + otherFiles[0].getFileName().trim();
		String other_file_name1 = "";
		if (otherFiles[1] != null)
			if (!(otherFiles[1].getFileName().equals("")))
				other_file_name1 = "OTHER1_" + bid + "_" + otherFiles[1].getFileName().trim();
		String other_file_name2 = "";
		if (otherFiles[2] != null)
			if (!(otherFiles[2].getFileName().equals("")))
				other_file_name2 = "OTHER2_" + bid + "_" + otherFiles[2].getFileName().trim();

		String suit_file_name = "SUIT_" + bid + "_" + file.getSuitReportFileFile().getFileName().trim();

		String verdict_file_name = "";
		if (file.getFinalVerdictFile() != null)
			if (!(file.getFinalVerdictFile().getFileName().equals("")))
				verdict_file_name = "VERDICT_" + bid + "_" + file.getFinalVerdictFile().getFileName().trim();

		String staff_report_name = "";
		if (file.getStaffReportFile() != null)
			if (!(file.getStaffReportFile().getFileName().equals(""))) {
				staff_report_name = "STAFF_" + bid + "_" + file.getStaffReportFile().getFileName().trim();
			}

		String internal_rating_file_name = "";
		if (file.getInternalRatingFile() != null) {
			if (!(file.getInternalRatingFile().getFileName().equals(""))) {
				internal_rating_file_name = "IR_" + bid + "_" + file.getInternalRatingFile().getFileName().trim();
			}
		}

		 call procedure 
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		connection = DBConnection.getConnection(false);
		 procudure to insert general fields and file names 
		try {
			stmt = connection.createStatement();
			String q1 = " SELECT COUNT(*) FROM CLAIM_FILES_NEW_TEMP WHERE CLM_REF_NO='" + clmRefeneceNumber + "'";

			String q2 = "";

			String q3 = "";

			String q4 = " SELECT NPAREPORT_FILE,DILIGENCEREPORT_FILE,DILIGENCEREPORT_FILE1,DILIGENCEREPORT_FILE2,POSTINSPECTIONREPORT_FILE,POSTINSPECTIONREPORT_FILE1,POSTINSPECTIONREPORT_FILE2"
					+ ",POSTNPAREPORT_FILE,POSTNPAREPORT_FILE1,POSTNPAREPORT_FILE2,SUITFILE_FILE,FINALVERDICTFILE_FILE "
					+ ",IDPROOFFILE_FILE,IDPROOFFILE_FILE1,IDPROOFFILE_FILE2,OTHERFILE_FILE,OTHERFILE_FILE1,OTHERFILE_FILE2,STAFFREPORT_FILE,INTERNAL_RATING_FILE "
					+ " FROM CLAIM_FILES_NEW_TEMP " + " WHERE CLM_REF_NO='" + clmRefeneceNumber + "' FOR UPDATE ";

			String q5 = " SELECT NPAREPORT_FILE,DILIGENCEREPORT_FILE,DILIGENCEREPORT_FILE1,DILIGENCEREPORT_FILE2,POSTINSPECTIONREPORT_FILE,POSTINSPECTIONREPORT_FILE1,POSTINSPECTIONREPORT_FILE2"
					+ ",POSTNPAREPORT_FILE,POSTNPAREPORT_FILE1,POSTNPAREPORT_FILE2,SUITFILE_FILE "
					+ ",IDPROOFFILE_FILE,IDPROOFFILE_FILE1,IDPROOFFILE_FILE2,OTHERFILE_FILE,OTHERFILE_FILE1,OTHERFILE_FILE2,STAFFREPORT_FILE,INTERNAL_RATING_FILE "
					+ " FROM CLAIM_FILES_NEW_TEMP " + " WHERE CLM_REF_NO='" + clmRefeneceNumber + "' FOR UPDATE ";

			rs = stmt.executeQuery(q1);
			int count = 0;
			if (rs.next()) {
				count = rs.getInt(1);
			}
			rs = null;
			if (count == 0) {
				q2 = " INSERT INTO CLAIM_FILES_NEW_TEMP VALUES('" + clmRefeneceNumber + "','" + npa_file_name
						+ "',empty_blob(),'" + diligence_file_name + "',empty_blob(),'" + diligence_file_name1
						+ "',empty_blob(),'" + diligence_file_name2 + "',empty_blob(),'" + post_insp_file_name
						+ "',empty_blob(),'" + post_insp_file_name1 + "',empty_blob(),'" + post_insp_file_name2
						+ "',empty_blob(),'" + post_npa_file_name + "',empty_blob(),'" + post_npa_file_name1
						+ "',empty_blob(),'" + post_npa_file_name2 + "',empty_blob(),'" + file.getInsuranceFileFlag()
						+ "','" + file.getInsuranceReason() + "','" + suit_file_name + "',empty_blob(),'"
						+ verdict_file_name + "',empty_blob(),'" + id_file_name + "',empty_blob(),'" + id_file_name1
						+ "',empty_blob(),'" + id_file_name2 + "',empty_blob(),'" + other_file_name + "',empty_blob(),'"
						+ other_file_name1 + "',empty_blob(),'" + other_file_name2 + "',empty_blob(),'"
						+ staff_report_name + "',empty_blob(),'" + file.getBankRateType() + "','"
						+ file.getSecurityRemarks() + "','" + file.getRecoveryEffortsTaken() + "','" + file.getRating()
						+ "','" + file.getBranchAddress() + "','" + file.getInvestmentGradeFlag() + "','I'," + "'"
						+ userID + "',sysdate,'" + userID + "',sysdate," + file.getPlr() + "," + file.getRate() + ",'"
						+ internal_rating_file_name + "',empty_blob())";

				stmt.execute(q2);
			} else {
				q3 = " UPDATE CLAIM_FILES_NEW_TEMP SET NPAREPORT_NAME='" + npa_file_name + "',DILIGENCEREPORT_NAME='"
						+ diligence_file_name + "',DILIGENCEREPORT_NAME1='" + diligence_file_name1
						+ "',DILIGENCEREPORT_NAME2='" + diligence_file_name2 + "',POSTINSPECTIONREPORT_NAME='"
						+ post_insp_file_name + "',POSTINSPECTIONREPORT_NAME1='" + post_insp_file_name1
						+ "',POSTINSPECTIONREPORT_NAME2='" + post_insp_file_name2 + "',POSTNPAREPORT_NAME='"
						+ post_npa_file_name + "',POSTNPAREPORT_NAME1='" + post_npa_file_name1
						+ "',POSTNPAREPORT_NAME2='" + post_npa_file_name2 + "',INSURANCE_FLAG='"
						+ file.getInsuranceFileFlag() + "',INSURANCE_REASON='" + file.getInsuranceReason()
						+ "',SUITFILE_NAME='" + suit_file_name + "',FINALVERDICTFILE_NAME='" + verdict_file_name
						+ "',IDPROOFFILE_NAME='" + id_file_name + "',IDPROOFFILE_NAME1='" + id_file_name1
						+ "',IDPROOFFILE_NAME2='" + id_file_name2 + "',OTHERFILE_NAME='" + other_file_name
						+ "',OTHERFILE_NAME1='" + other_file_name1 + "',OTHERFILE_NAME2='" + other_file_name2
						+ "',STAFFREPORT_NAME='" + staff_report_name + "',BANKRATE_TYPE='" + file.getBankRateType()
						+ "',SECURITYREMARKS='" + file.getSecurityRemarks() + "',RECOVERYEFFORTSTAKEN='"
						+ file.getRecoveryEffortsTaken() + "',RATING='" + file.getRating() + "',BRANCHADDRESS='"
						+ file.getBranchAddress() + "',INVESTMENTGRADE='" + file.getInvestmentGradeFlag()
						+ "',OPERATION_TYPE='U',MODIFIED_BY='" + userID + "',MODIFIED_DT=sysdate, PLR=" + file.getPlr()
						+ ",RATE=" + file.getRate() + ",INTERNAL_RATING_FILE_NAME='" + internal_rating_file_name + "' "
						+ " WHERE CLM_REF_NO='" + clmRefeneceNumber + "'";

				stmt.execute(q3);
			}

			stmt = connection.createStatement();
			if (verdict_file_name != null) {
				rs = stmt.executeQuery(q4);
				while (rs.next()) {
					BLOB blob = ((OracleResultSet) rs).getBLOB(1);
					OutputStream os = blob.getBinaryOutputStream();
					os.write(file.getNpaReportFile().getFileData());
					os.close();
					blob = ((OracleResultSet) rs).getBLOB(2);
					os = blob.getBinaryOutputStream();
					os.write(file.getDiligenceReportFiles()[0].getFileData());
					os.close();
					if (file.getDiligenceReportFiles()[1] != null) {
						blob = ((OracleResultSet) rs).getBLOB(3);
						os = blob.getBinaryOutputStream();
						os.write(file.getDiligenceReportFiles()[1].getFileData());
						os.close();
					}
					if (file.getDiligenceReportFiles()[2] != null) {
						blob = ((OracleResultSet) rs).getBLOB(4);
						os = blob.getBinaryOutputStream();
						os.write(file.getDiligenceReportFiles()[2].getFileData());
						os.close();
					}

					if (file.getPostInspectionReportFiles()[0] != null) {
						blob = ((OracleResultSet) rs).getBLOB(5);
						os = blob.getBinaryOutputStream();
						os.write(file.getPostInspectionReportFiles()[0].getFileData());
						os.close();
					}
					if (file.getPostInspectionReportFiles()[1] != null) {
						blob = ((OracleResultSet) rs).getBLOB(6);
						os = blob.getBinaryOutputStream();
						os.write(file.getPostInspectionReportFiles()[1].getFileData());
						os.close();
					}
					if (file.getPostInspectionReportFiles()[2] != null) {
						blob = ((OracleResultSet) rs).getBLOB(7);
						os = blob.getBinaryOutputStream();
						os.write(file.getPostInspectionReportFiles()[2].getFileData());
						os.close();
					}

					if (file.getPostNpaReportFiles()[0] != null) {
						blob = ((OracleResultSet) rs).getBLOB(8);
						os = blob.getBinaryOutputStream();
						os.write(file.getPostNpaReportFiles()[0].getFileData());
						os.close();
					}
					if (file.getPostNpaReportFiles()[1] != null) {
						blob = ((OracleResultSet) rs).getBLOB(9);
						os = blob.getBinaryOutputStream();
						os.write(file.getPostNpaReportFiles()[1].getFileData());
						os.close();
					}
					if (file.getPostNpaReportFiles()[2] != null) {
						blob = ((OracleResultSet) rs).getBLOB(10);
						os = blob.getBinaryOutputStream();
						os.write(file.getPostNpaReportFiles()[2].getFileData());
						os.close();
					}

					blob = ((OracleResultSet) rs).getBLOB(11);
					os = blob.getBinaryOutputStream();
					os.write(file.getSuitReportFileFile().getFileData());
					os.close();
					blob = ((OracleResultSet) rs).getBLOB(12);
					os = blob.getBinaryOutputStream();
					os.write(file.getFinalVerdictFile().getFileData());
					os.close();

					if (file.getIdProofFiles()[0] != null) {
						blob = ((OracleResultSet) rs).getBLOB(13);
						os = blob.getBinaryOutputStream();
						os.write(file.getIdProofFiles()[0].getFileData());
						os.close();
					}
					if (file.getIdProofFiles()[1] != null) {
						blob = ((OracleResultSet) rs).getBLOB(14);
						os = blob.getBinaryOutputStream();
						os.write(file.getIdProofFiles()[1].getFileData());
						os.close();
					}
					if (file.getIdProofFiles()[2] != null) {
						blob = ((OracleResultSet) rs).getBLOB(15);
						os = blob.getBinaryOutputStream();
						os.write(file.getIdProofFiles()[2].getFileData());
						os.close();
					}

					if (file.getOtherFiles()[0] != null) {
						blob = ((OracleResultSet) rs).getBLOB(16);
						os = blob.getBinaryOutputStream();
						os.write(file.getOtherFiles()[0].getFileData());
						os.close();
					}
					if (file.getOtherFiles()[1] != null) {
						blob = ((OracleResultSet) rs).getBLOB(17);
						os = blob.getBinaryOutputStream();
						os.write(file.getOtherFiles()[1].getFileData());
						os.close();
					}
					if (file.getOtherFiles()[2] != null) {
						blob = ((OracleResultSet) rs).getBLOB(18);
						os = blob.getBinaryOutputStream();
						os.write(file.getOtherFiles()[2].getFileData());
						os.close();
					}
					if (file.getStaffReportFile() != null) {
						blob = ((OracleResultSet) rs).getBLOB(19);
						os = blob.getBinaryOutputStream();
						os.write(file.getStaffReportFile().getFileData());
						os.close();
					}
					if (file.getInternalRatingFile() != null) {
						blob = ((OracleResultSet) rs).getBLOB(20);
						os = blob.getBinaryOutputStream();
						os.write(file.getInternalRatingFile().getFileData());
						os.close();
					}
				}
				rs.close();
				stmt.close();
			} else {
				rs = stmt.executeQuery(q5);
				while (rs.next()) {
					BLOB blob = ((OracleResultSet) rs).getBLOB(1);
					OutputStream os = blob.getBinaryOutputStream();
					os.write(file.getNpaReportFile().getFileData());
					os.close();
					blob = ((OracleResultSet) rs).getBLOB(2);
					os = blob.getBinaryOutputStream();
					os.write(file.getDiligenceReportFiles()[0].getFileData());
					os.close();
					if (file.getDiligenceReportFiles()[1] != null) {
						blob = ((OracleResultSet) rs).getBLOB(3);
						os = blob.getBinaryOutputStream();
						os.write(file.getDiligenceReportFiles()[1].getFileData());
						os.close();
					}
					if (file.getDiligenceReportFiles()[2] != null) {
						blob = ((OracleResultSet) rs).getBLOB(4);
						os = blob.getBinaryOutputStream();
						os.write(file.getDiligenceReportFiles()[2].getFileData());
						os.close();
					}

					if (file.getPostInspectionReportFiles()[0] != null) {
						blob = ((OracleResultSet) rs).getBLOB(5);
						os = blob.getBinaryOutputStream();
						os.write(file.getPostInspectionReportFiles()[0].getFileData());
						os.close();
					}
					if (file.getPostInspectionReportFiles()[1] != null) {
						blob = ((OracleResultSet) rs).getBLOB(6);
						os = blob.getBinaryOutputStream();
						os.write(file.getPostInspectionReportFiles()[1].getFileData());
						os.close();
					}
					if (file.getPostInspectionReportFiles()[2] != null) {
						blob = ((OracleResultSet) rs).getBLOB(7);
						os = blob.getBinaryOutputStream();
						os.write(file.getPostInspectionReportFiles()[2].getFileData());
						os.close();
					}

					if (file.getPostNpaReportFiles()[0] != null) {
						blob = ((OracleResultSet) rs).getBLOB(8);
						os = blob.getBinaryOutputStream();
						os.write(file.getPostNpaReportFiles()[0].getFileData());
						os.close();
					}
					if (file.getPostNpaReportFiles()[1] != null) {
						blob = ((OracleResultSet) rs).getBLOB(9);
						os = blob.getBinaryOutputStream();
						os.write(file.getPostNpaReportFiles()[1].getFileData());
						os.close();
					}
					if (file.getPostNpaReportFiles()[2] != null) {
						blob = ((OracleResultSet) rs).getBLOB(10);
						os = blob.getBinaryOutputStream();
						os.write(file.getPostNpaReportFiles()[2].getFileData());
						os.close();
					}

					blob = ((OracleResultSet) rs).getBLOB(11);
					os = blob.getBinaryOutputStream();
					os.write(file.getSuitReportFileFile().getFileData());
					os.close();

					if (file.getIdProofFiles()[0] != null) {
						blob = ((OracleResultSet) rs).getBLOB(12);
						os = blob.getBinaryOutputStream();
						os.write(file.getIdProofFiles()[0].getFileData());
						os.close();
					}
					if (file.getIdProofFiles()[1] != null) {
						blob = ((OracleResultSet) rs).getBLOB(13);
						os = blob.getBinaryOutputStream();
						os.write(file.getIdProofFiles()[1].getFileData());
						os.close();
					}
					if (file.getIdProofFiles()[2] != null) {
						blob = ((OracleResultSet) rs).getBLOB(14);
						os = blob.getBinaryOutputStream();
						os.write(file.getIdProofFiles()[2].getFileData());
						os.close();
					}

					if (file.getOtherFiles()[0] != null) {
						blob = ((OracleResultSet) rs).getBLOB(15);
						os = blob.getBinaryOutputStream();
						os.write(file.getOtherFiles()[0].getFileData());
						os.close();
					}
					if (file.getOtherFiles()[1] != null) {
						blob = ((OracleResultSet) rs).getBLOB(16);
						os = blob.getBinaryOutputStream();
						os.write(file.getOtherFiles()[1].getFileData());
						os.close();
					}
					if (file.getOtherFiles()[2] != null) {
						blob = ((OracleResultSet) rs).getBLOB(17);
						os = blob.getBinaryOutputStream();
						os.write(file.getOtherFiles()[2].getFileData());
						os.close();
					}
					if (file.getStaffReportFile() != null) {
						blob = ((OracleResultSet) rs).getBLOB(18);
						os = blob.getBinaryOutputStream();
						os.write(file.getStaffReportFile().getFileData());
						os.close();
					}
					if (file.getInternalRatingFile() != null) {
						blob = ((OracleResultSet) rs).getBLOB(19);
						os = blob.getBinaryOutputStream();
						os.write(file.getInternalRatingFile().getFileData());
						os.close();
					}
				}
				rs.close();
				stmt.close();
			}

			for (int i = 0; i < claimFiles.size(); i++) {
				// ClaimFiles claimFile = (ClaimFiles)itr.next();
				ClaimFiles claimFile = (ClaimFiles) claimFiles.get(i);
				String cgpan = claimFile.getCgpan();
				FormFile[] statementFile = claimFile.getStatementFile();
				FormFile[] appraisalFile = claimFile.getAppraisalReportFile();
				FormFile[] sanctionFile = claimFile.getSanctionLetterFile();
				FormFile[] complianceFile = claimFile.getComplianceFile();
				FormFile[] preinspectionFile = claimFile.getPreInspectionFile();
				FormFile[] insuranceCopyFile = claimFile.getInsuranceCopyFile();
				double pRepayAmt = claimFile.getPrincipalRepayBeforeNpaAmts();
				double iRepayAmt = claimFile.getInterestRepayBeforeNpaAmts();
				double pRecoveryAmt = claimFile.getPrincipalRecoveryAfterNpaAmts();
				double iRecoveryAmt = claimFile.getInterestRecoveryAfterNpaAmts();
				double interest = claimFile.getInterestRate();

				String statement_file_name = "";
				String statement_file_name1 = "";
				String statement_file_name2 = "";
				String appraisal_file_name = "";
				String appraisal_file_name1 = "";
				String appraisal_file_name2 = "";
				String sanction_letter_name = "";
				String sanction_letter_name1 = "";
				String sanction_letter_name2 = "";
				String compliance_file_name = "";
				String compliance_file_name1 = "";
				String compliance_file_name2 = "";
				String preinspection_file_name = "";
				String preinspection_file_name1 = "";
				String preinspection_file_name2 = "";
				String insurancecopy_name = "";
				String insurancecopy_name1 = "";
				String insurancecopy_name2 = "";

				statement_file_name = "STMT_" + i + "_" + bid + "_" + statementFile[0].getFileName();
				if (statementFile[1] != null)
					if (!(statementFile[1].getFileName().equals("")))
						statement_file_name1 = "STMT1_" + i + "_" + bid + "_" + statementFile[1].getFileName();
				if (statementFile[2] != null)
					if (!(statementFile[2].getFileName().equals("")))
						statement_file_name2 = "STMT2_" + i + "_" + bid + "_" + statementFile[2].getFileName();

				if (appraisalFile[0] != null)
					if (!(appraisalFile[0].getFileName().equals("")))
						appraisal_file_name = "APPSL_" + i + "_" + bid + "_" + appraisalFile[0].getFileName();
				if (appraisalFile[1] != null)
					if (!(appraisalFile[1].getFileName().equals("")))
						appraisal_file_name1 = "APPSL1_" + i + "_" + bid + "_" + appraisalFile[1].getFileName();
				if (appraisalFile[2] != null)
					if (!(appraisalFile[2].getFileName().equals("")))
						appraisal_file_name2 = "APPSL2_" + i + "_" + bid + "_" + appraisalFile[2].getFileName();

				if (sanctionFile[0] != null)
					if (!(sanctionFile[0].getFileName().equals("")))
						sanction_letter_name = "SANC_" + i + "_" + bid + "_" + sanctionFile[0].getFileName();
				if (sanctionFile[1] != null)
					if (!(sanctionFile[1].getFileName().equals("")))
						sanction_letter_name1 = "SANC1_" + i + "_" + bid + "_" + sanctionFile[1].getFileName();
				if (sanctionFile[2] != null)
					if (!(sanctionFile[2].getFileName().equals("")))
						sanction_letter_name2 = "SANC2_" + i + "_" + bid + "_" + sanctionFile[2].getFileName();

				if (complianceFile[0] != null)
					if (!(complianceFile[0].getFileName().equals("")))
						compliance_file_name = "COMPL_" + i + "_" + bid + "_" + complianceFile[0].getFileName();
				if (complianceFile[1] != null)
					if (!(complianceFile[1].getFileName().equals("")))
						compliance_file_name1 = "COMPL1_" + i + "_" + bid + "_" + complianceFile[1].getFileName();
				if (complianceFile[2] != null)
					if (!(complianceFile[2].getFileName().equals("")))
						compliance_file_name2 = "COMPL2_" + i + "_" + bid + "_" + complianceFile[2].getFileName();

				if (preinspectionFile[0] != null)
					if (!(preinspectionFile[0].getFileName().equals("")))
						preinspection_file_name = "PRE_INSPEC_" + i + "_" + bid + "_"
								+ preinspectionFile[0].getFileName();
				if (preinspectionFile[1] != null)
					if (!(preinspectionFile[1].getFileName().equals("")))
						preinspection_file_name1 = "PRE_INSPEC1_" + i + "_" + bid + "_"
								+ preinspectionFile[1].getFileName();
				if (preinspectionFile[2] != null)
					if (!(preinspectionFile[2].getFileName().equals("")))
						preinspection_file_name2 = "PRE_INSPEC2_" + i + "_" + bid + "_"
								+ preinspectionFile[2].getFileName();

				if (file.getInsuranceFileFlag().equals("Y")) {
					if (insuranceCopyFile[0] != null)
						if (!(insuranceCopyFile[0].getFileName().equals("")))
							insurancecopy_name = "INSURANCE_" + i + "_" + bid + "_"
									+ insuranceCopyFile[0].getFileName();
					if (insuranceCopyFile[1] != null)
						if (!(insuranceCopyFile[1].getFileName().equals("")))
							insurancecopy_name1 = "INSURANCE1_" + i + "_" + bid + "_"
									+ insuranceCopyFile[1].getFileName();
					if (insuranceCopyFile[2] != null)
						if (!(insuranceCopyFile[2].getFileName().equals("")))
							insurancecopy_name2 = "INSURANCE2_" + i + "_" + bid + "_"
									+ insuranceCopyFile[2].getFileName();
				}

				stmt = connection.createStatement();

				if (count == 0) {
					if (cgpan.endsWith("TC")) {
						stmt.execute("INSERT INTO CLAIM_TC_FILES_NEW_TEMP VALUES('" + clmRefeneceNumber + "','" + cgpan
								+ "','" + statement_file_name + "',empty_blob(),'" + statement_file_name1
								+ "',empty_blob(),'" + statement_file_name2 + "',empty_blob(),'"
								+ claimFile.getIsSameAppraisalFile() + "','" + appraisal_file_name + "',empty_blob(),'"
								+ appraisal_file_name1 + "',empty_blob(),'" + appraisal_file_name2 + "',empty_blob(),'"
								+ claimFile.getIsSameSanctionFile() + "','" + sanction_letter_name + "',empty_blob(),'"
								+ sanction_letter_name1 + "',empty_blob(),'" + sanction_letter_name2
								+ "',empty_blob(),'" + claimFile.getIsSameComplianceFile() + "','"
								+ compliance_file_name + "',empty_blob(),'" + compliance_file_name1 + "',empty_blob(),'"
								+ compliance_file_name2 + "',empty_blob(),'" + claimFile.getIsSamePreInspectionFile()
								+ "','" + preinspection_file_name + "',empty_blob(),'" + preinspection_file_name1
								+ "',empty_blob(),'" + preinspection_file_name2 + "',empty_blob(),'"
								+ claimFile.getIsSameInsuranceFile() + "','" + insurancecopy_name + "',empty_blob(),'"
								+ insurancecopy_name1 + "',empty_blob(),'" + insurancecopy_name2 + "',empty_blob(),"
								+ pRepayAmt + "," + iRepayAmt + "," + pRecoveryAmt + "," + iRecoveryAmt + "," + interest
								+ ")");
					} else {
						stmt.execute("INSERT INTO CLAIM_WC_FILES_NEW_TEMP VALUES('" + clmRefeneceNumber + "','" + cgpan
								+ "','" + statement_file_name + "',empty_blob(),'" + statement_file_name1
								+ "',empty_blob(),'" + statement_file_name2 + "',empty_blob(),'"
								+ claimFile.getIsSameAppraisalFile() + "','" + appraisal_file_name + "',empty_blob(),'"
								+ appraisal_file_name1 + "',empty_blob(),'" + appraisal_file_name2 + "',empty_blob(),'"
								+ claimFile.getIsSameSanctionFile() + "','" + sanction_letter_name + "',empty_blob(),'"
								+ sanction_letter_name1 + "',empty_blob(),'" + sanction_letter_name2
								+ "',empty_blob(),'" + claimFile.getIsSameComplianceFile() + "','"
								+ compliance_file_name + "',empty_blob(),'" + compliance_file_name1 + "',empty_blob(),'"
								+ compliance_file_name2 + "',empty_blob(),'" + claimFile.getIsSamePreInspectionFile()
								+ "','" + preinspection_file_name + "',empty_blob(),'" + preinspection_file_name1
								+ "',empty_blob(),'" + preinspection_file_name2 + "',empty_blob(),'"
								+ claimFile.getIsSameInsuranceFile() + "','" + insurancecopy_name + "',empty_blob(),'"
								+ insurancecopy_name1 + "',empty_blob(),'" + insurancecopy_name2 + "',empty_blob(),"
								+ pRecoveryAmt + "," + iRecoveryAmt + "," + interest + ")");
					}
				} else {
					if (cgpan.endsWith("TC")) {
						stmt.execute(" UPDATE CLAIM_TC_FILES_NEW_TEMP SET STATEMENT_NAME='" + statement_file_name
								+ "',APPRAISALREPORT_NAME='" + appraisal_file_name + "',APPRAISALREPORT_NAME1='"
								+ appraisal_file_name1 + "',APPRAISALREPORT_NAME2='" + appraisal_file_name2
								+ "',SANCTIONLETTER_NAME='" + sanction_letter_name + "',SANCTIONLETTER_NAME1='"
								+ sanction_letter_name1 + "',SANCTIONLETTER_NAME2='" + sanction_letter_name2
								+ "',COMPLIANCEREPORT_NAME='" + compliance_file_name + "',COMPLIANCEREPORT_NAME1='"
								+ compliance_file_name1 + "',COMPLIANCEREPORT_NAME2='" + compliance_file_name2
								+ "',PREINSPECTIONREPORT_NAME='" + preinspection_file_name
								+ "',PREINSPECTIONREPORT_NAME1='" + preinspection_file_name1
								+ "',PREINSPECTIONREPORT_NAME2='" + preinspection_file_name2 + "',INSURANCECOPY_NAME='"
								+ insurancecopy_name + "',INSURANCECOPY_NAME1='" + insurancecopy_name1
								+ "',INSURANCECOPY_NAME2='" + insurancecopy_name2 + "',PRINCIPALREPAYBEFORENPA_AMT="
								+ pRepayAmt + ",INTERESTREPAYBEFORENPA_AMT=" + iRepayAmt + ",PRINCIPALRECOAFTERNPA_AMT="
								+ pRecoveryAmt + ",INTERESTRECOAFTERNPA_AMT=" + iRecoveryAmt + ",INTERESTRATE="
								+ interest + " WHERE CLM_REF_NO='" + clmRefeneceNumber + "' AND CGPAN='" + cgpan + "'");
					} else {
						stmt.execute(" UPDATE CLAIM_WC_FILES_NEW_TEMP SET STATEMENT_NAME='" + statement_file_name
								+ "',APPRAISALREPORT_NAME='" + appraisal_file_name + "',APPRAISALREPORT_NAME1='"
								+ appraisal_file_name1 + "',APPRAISALREPORT_NAME2='" + appraisal_file_name2
								+ "',SANCTIONLETTER_NAME='" + sanction_letter_name + "',SANCTIONLETTER_NAME1='"
								+ sanction_letter_name1 + "',SANCTIONLETTER_NAME2='" + sanction_letter_name2
								+ "',COMPLIANCEREPORT_NAME='" + compliance_file_name + "',COMPLIANCEREPORT_NAME1='"
								+ compliance_file_name1 + "',COMPLIANCEREPORT_NAME2='" + compliance_file_name2
								+ "',PREINSPECTIONREPORT_NAME='" + preinspection_file_name
								+ "',PREINSPECTIONREPORT_NAME1='" + preinspection_file_name1
								+ "',PREINSPECTIONREPORT_NAME2='" + preinspection_file_name2 + "',INSURANCECOPY_NAME='"
								+ insurancecopy_name + "',INSURANCECOPY_NAME1='" + insurancecopy_name1
								+ "',INSURANCECOPY_NAME2='" + insurancecopy_name2 + "',PRINCIPALRECOAFTERNPA_AMT="
								+ pRecoveryAmt + ",INTERESTRECOAFTERNPA_AMT=" + iRecoveryAmt + ",INTERESTRATE="
								+ interest + " WHERE CLM_REF_NO='" + clmRefeneceNumber + "' AND CGPAN='" + cgpan + "'");
					}
				}

				stmt = connection.createStatement();
				if ("Y".equals(file.getInsuranceFileFlag())) {
					if (cgpan.endsWith("TC")) {
						rs = stmt.executeQuery(
								" SELECT STATEMENT_FILE,STATEMENT_FILE1,STATEMENT_FILE2,APPRAISALREPORT_FILE,APPRAISALREPORT_FILE1,APPRAISALREPORT_FILE2,"
										+ "SANCTIONLETTER_FILE,SANCTIONLETTER_FILE1,SANCTIONLETTER_FILE2,COMPLIANCEREPORT_FILE,COMPLIANCEREPORT_FILE1,COMPLIANCEREPORT_FILE2,"
										+ "PREINSPECTIONREPORT_FILE,PREINSPECTIONREPORT_FILE1,PREINSPECTIONREPORT_FILE2,INSURANCECOPY_FILE,INSURANCECOPY_FILE1,INSURANCECOPY_FILE2"
										+ " from claim_tc_files_new_temp  WHERE CLM_REF_NO='" + clmRefeneceNumber
										+ "' AND CGPAN='" + cgpan + "' for update");
					} else {
						rs = stmt.executeQuery(
								" SELECT STATEMENT_FILE,STATEMENT_FILE1,STATEMENT_FILE2,APPRAISALREPORT_FILE,APPRAISALREPORT_FILE1,APPRAISALREPORT_FILE2,"
										+ "SANCTIONLETTER_FILE,SANCTIONLETTER_FILE1,SANCTIONLETTER_FILE2,COMPLIANCEREPORT_FILE,COMPLIANCEREPORT_FILE1,COMPLIANCEREPORT_FILE2,"
										+ "PREINSPECTIONREPORT_FILE,PREINSPECTIONREPORT_FILE1,PREINSPECTIONREPORT_FILE2,INSURANCECOPY_FILE,INSURANCECOPY_FILE1,INSURANCECOPY_FILE2"
										+ " from claim_wc_files_new_temp  WHERE CLM_REF_NO='" + clmRefeneceNumber
										+ "' AND CGPAN='" + cgpan + "' for update");
					}
					while (rs.next()) {
						BLOB blob = ((OracleResultSet) rs).getBLOB(1);
						OutputStream os = blob.getBinaryOutputStream();
						byte[] data = null;
						if (statementFile[0] != null) {
							data = statementFile[0].getFileData();
							os.write(data);
						}
						os.close();
						blob = ((OracleResultSet) rs).getBLOB(2);
						os = blob.getBinaryOutputStream();
						if (statementFile[1] != null) {
							data = statementFile[1].getFileData();
							os.write(data);
						}
						os.close();
						blob = ((OracleResultSet) rs).getBLOB(3);
						os = blob.getBinaryOutputStream();
						if (statementFile[2] != null) {
							data = statementFile[2].getFileData();
							os.write(data);
						}
						os.close();

						blob = ((OracleResultSet) rs).getBLOB(4);
						os = blob.getBinaryOutputStream();
						if (appraisalFile[0] != null) {
							data = appraisalFile[0].getFileData();
							os.write(data);
						}
						os.close();
						blob = ((OracleResultSet) rs).getBLOB(5);
						os = blob.getBinaryOutputStream();
						if (appraisalFile[1] != null) {
							data = appraisalFile[1].getFileData();
							os.write(data);
						}
						os.close();
						blob = ((OracleResultSet) rs).getBLOB(6);
						os = blob.getBinaryOutputStream();
						if (appraisalFile[2] != null) {
							data = appraisalFile[2].getFileData();
							os.write(data);
						}
						os.close();
						blob = ((OracleResultSet) rs).getBLOB(7);
						os = blob.getBinaryOutputStream();
						if (sanctionFile[0] != null) {
							data = sanctionFile[0].getFileData();
							os.write(data);
						}
						os.close();
						blob = ((OracleResultSet) rs).getBLOB(8);
						os = blob.getBinaryOutputStream();
						if (sanctionFile[1] != null) {
							data = sanctionFile[1].getFileData();
							os.write(data);
						}
						os.close();
						blob = ((OracleResultSet) rs).getBLOB(9);
						os = blob.getBinaryOutputStream();
						if (sanctionFile[2] != null) {
							data = sanctionFile[2].getFileData();
							os.write(data);
						}
						os.close();
						blob = ((OracleResultSet) rs).getBLOB(10);
						os = blob.getBinaryOutputStream();
						if (complianceFile[0] != null) {
							data = complianceFile[0].getFileData();
							os.write(data);
						}
						os.close();
						blob = ((OracleResultSet) rs).getBLOB(11);
						os = blob.getBinaryOutputStream();
						if (complianceFile[1] != null) {
							data = complianceFile[1].getFileData();
							os.write(data);
						}
						os.close();
						blob = ((OracleResultSet) rs).getBLOB(12);
						os = blob.getBinaryOutputStream();
						if (complianceFile[2] != null) {
							data = complianceFile[2].getFileData();
							os.write(data);
						}
						os.close();
						blob = ((OracleResultSet) rs).getBLOB(13);
						os = blob.getBinaryOutputStream();
						if (preinspectionFile[0] != null) {
							data = preinspectionFile[0].getFileData();
							os.write(data);
						}
						os.close();
						blob = ((OracleResultSet) rs).getBLOB(14);
						os = blob.getBinaryOutputStream();
						if (preinspectionFile[1] != null) {
							data = preinspectionFile[1].getFileData();
							os.write(data);
						}
						os.close();
						blob = ((OracleResultSet) rs).getBLOB(15);
						os = blob.getBinaryOutputStream();
						if (preinspectionFile[2] != null) {
							data = preinspectionFile[2].getFileData();
							os.write(data);
						}
						os.close();
						blob = ((OracleResultSet) rs).getBLOB(16);
						os = blob.getBinaryOutputStream();
						if (insuranceCopyFile[0] != null) {
							data = insuranceCopyFile[0].getFileData();
							os.write(data);
						}
						os.close();
						blob = ((OracleResultSet) rs).getBLOB(17);
						os = blob.getBinaryOutputStream();
						if (insuranceCopyFile[1] != null) {
							data = insuranceCopyFile[1].getFileData();
							os.write(data);
						}
						os.close();
						blob = ((OracleResultSet) rs).getBLOB(18);
						os = blob.getBinaryOutputStream();
						if (insuranceCopyFile[2] != null) {
							data = insuranceCopyFile[2].getFileData();
							os.write(data);
						}
						os.close();

					}
				} else {
					if (cgpan.endsWith("TC")) {
						rs = stmt.executeQuery(
								" SELECT STATEMENT_FILE,STATEMENT_FILE1,STATEMENT_FILE2,APPRAISALREPORT_FILE,APPRAISALREPORT_FILE1,APPRAISALREPORT_FILE2,"
										+ "SANCTIONLETTER_FILE,SANCTIONLETTER_FILE1,SANCTIONLETTER_FILE2,COMPLIANCEREPORT_FILE,COMPLIANCEREPORT_FILE1,COMPLIANCEREPORT_FILE2,"
										+ "PREINSPECTIONREPORT_FILE,PREINSPECTIONREPORT_FILE1,PREINSPECTIONREPORT_FILE2 from claim_tc_files_new_temp "
										+ " WHERE CLM_REF_NO='" + clmRefeneceNumber + "' AND CGPAN='" + cgpan + "'");
					} else {
						rs = stmt.executeQuery(
								" SELECT STATEMENT_FILE,STATEMENT_FILE1,STATEMENT_FILE2,APPRAISALREPORT_FILE,APPRAISALREPORT_FILE1,APPRAISALREPORT_FILE2,"
										+ "SANCTIONLETTER_FILE,SANCTIONLETTER_FILE1,SANCTIONLETTER_FILE2,COMPLIANCEREPORT_FILE,COMPLIANCEREPORT_FILE1,COMPLIANCEREPORT_FILE2,"
										+ "PREINSPECTIONREPORT_FILE,PREINSPECTIONREPORT_FILE1,PREINSPECTIONREPORT_FILE2 from claim_wc_files_new_temp "
										+ " WHERE CLM_REF_NO='" + clmRefeneceNumber + "' AND CGPAN='" + cgpan + "'");
					}
					while (rs.next()) {
						BLOB blob = ((OracleResultSet) rs).getBLOB(1);
						OutputStream os = blob.getBinaryOutputStream();
						byte[] data = null;
						if (statementFile[0] != null) {
							data = statementFile[0].getFileData();
							os.write(data);
						}
						os.close();
						blob = ((OracleResultSet) rs).getBLOB(2);
						os = blob.getBinaryOutputStream();
						if (statementFile[1] != null) {
							data = statementFile[1].getFileData();
							os.write(data);
						}
						os.close();
						blob = ((OracleResultSet) rs).getBLOB(3);
						os = blob.getBinaryOutputStream();
						if (statementFile[2] != null) {
							data = statementFile[2].getFileData();
							os.write(data);
						}
						os.close();

						blob = ((OracleResultSet) rs).getBLOB(4);
						os = blob.getBinaryOutputStream();
						if (appraisalFile[0] != null) {
							data = appraisalFile[0].getFileData();
							os.write(data);
						}
						os.close();
						blob = ((OracleResultSet) rs).getBLOB(5);
						os = blob.getBinaryOutputStream();
						if (appraisalFile[1] != null) {
							data = appraisalFile[1].getFileData();
							os.write(data);
						}
						os.close();
						blob = ((OracleResultSet) rs).getBLOB(6);
						os = blob.getBinaryOutputStream();
						if (appraisalFile[2] != null) {
							data = appraisalFile[2].getFileData();
							os.write(data);
						}
						os.close();
						blob = ((OracleResultSet) rs).getBLOB(7);
						os = blob.getBinaryOutputStream();
						if (sanctionFile[0] != null) {
							data = sanctionFile[0].getFileData();
							os.write(data);
						}
						os.close();
						blob = ((OracleResultSet) rs).getBLOB(8);
						os = blob.getBinaryOutputStream();
						if (sanctionFile[1] != null) {
							data = sanctionFile[1].getFileData();
							os.write(data);
						}
						os.close();
						blob = ((OracleResultSet) rs).getBLOB(9);
						os = blob.getBinaryOutputStream();
						if (sanctionFile[2] != null) {
							data = sanctionFile[2].getFileData();
							os.write(data);
						}
						os.close();
						blob = ((OracleResultSet) rs).getBLOB(10);
						os = blob.getBinaryOutputStream();
						if (complianceFile[0] != null) {
							data = complianceFile[0].getFileData();
							os.write(data);
						}
						os.close();
						blob = ((OracleResultSet) rs).getBLOB(11);
						os = blob.getBinaryOutputStream();
						if (complianceFile[1] != null) {
							data = complianceFile[1].getFileData();
							os.write(data);
						}
						os.close();
						blob = ((OracleResultSet) rs).getBLOB(12);
						os = blob.getBinaryOutputStream();
						if (complianceFile[2] != null) {
							data = complianceFile[2].getFileData();
							os.write(data);
						}
						os.close();
						blob = ((OracleResultSet) rs).getBLOB(13);
						os = blob.getBinaryOutputStream();
						if (preinspectionFile[0] != null) {
							data = preinspectionFile[0].getFileData();
							os.write(data);
						}
						os.close();
						blob = ((OracleResultSet) rs).getBLOB(14);
						os = blob.getBinaryOutputStream();
						if (preinspectionFile[1] != null) {
							data = preinspectionFile[1].getFileData();
							os.write(data);
						}
						os.close();
						blob = ((OracleResultSet) rs).getBLOB(15);
						os = blob.getBinaryOutputStream();
						if (preinspectionFile[2] != null) {
							data = preinspectionFile[2].getFileData();
							os.write(data);
						}
						os.close();

					}
				}
				rs.close();
				stmt.close();

			}

			stmt = connection.createStatement();
			stmt.execute("UPDATE CLAIM_DETAIL_TEMP SET CLM_STATUS='AC' WHERE CLM_REF_NO='" + clmRefeneceNumber + "'");
			stmt.close();

			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				throw new DatabaseException();
			}
			e.printStackTrace();
			throw new DatabaseException();
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			throw new DatabaseException();
		} finally {
			DBConnection.freeConnection(connection);
		}

	}*/

	public Map getAppSanctionDate(String borrwerId, String memberId) throws DatabaseException {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		Map map = new HashMap();
		conn = DBConnection.getConnection(true);
		boolean is_has_2013 = false;
		try {

			/*
			 * 
			 * String sql="SELECT A.APP_SANCTION_DT FROM application_detail a, ssi_detail s
			 * WHERE LTRIM(RTRIM(UPPER(s.bid))) = LTRIM(RTRIM(UPPER(borrwerId))) AND
			 * LTRIM(RTRIM(UPPER(a.app_status))) NOT IN ('CL','XE','LC','RE') AND
			 * LTRIM(RTRIM(UPPER(a.app_created_modified_by))) <>
			 * LTRIM(RTRIM(UPPER('demouser'))) AND
			 * LTRIM(RTRIM(UPPER(s.ssi_created_modified_by))) <>
			 * LTRIM(RTRIM(UPPER('demouser'))) AND s.ssi_reference_number =
			 * a.ssi_reference_number
			 * 
			 */
			ArrayList dates = new ArrayList();

			/*
			 * String
			 * sql="SELECT 	a.cgpan,A.APP_SANCTION_DT	FROM   	application_detail a, 	ssi_detail s  where   "
			 * + "	LTRIM(RTRIM(UPPER(s.bid))) = LTRIM(RTRIM(UPPER(?)))" +
			 * "	LTRIM(RTRIM(UPPER(a.mem_bnk_id||a.mem_zne_id||a.mem_brn_id))) = LTRIM(RTRIM(UPPER(?)))"
			 * + " AND (LTRIM(RTRIM(UPPER(a.app_status)))) NOT IN ('CL','XE','LC','RE')  " +
			 * " AND  LTRIM(RTRIM(UPPER(a.app_created_modified_by))) <> LTRIM(RTRIM(UPPER('demouser')))"
			 * +
			 * "	AND  LTRIM(RTRIM(UPPER(s.ssi_created_modified_by))) <> LTRIM(RTRIM(UPPER('demouser'))) "
			 * + "AND  s.ssi_reference_number= a.ssi_reference_number ";
			 */

			String sql = "SELECT  a.cgpan,A.APP_SANCTION_DT    FROM  application_detail a,  ssi_detail s  where "
					+ " LTRIM(RTRIM(UPPER(s.bid))) = LTRIM(RTRIM(UPPER(?))) and  "
					+ " LTRIM(RTRIM(UPPER(a.mem_bnk_id||a.mem_zne_id||a.mem_brn_id))) = LTRIM(RTRIM(UPPER(?))) "
					+ " AND (LTRIM(RTRIM(UPPER(a.app_status)))) NOT IN ('CL','XE','LC','RE')    " +
					// " AND LTRIM(RTRIM(UPPER(a.app_created_modified_by))) <>
					// LTRIM(RTRIM(UPPER('demouser'))) " +
					// " AND LTRIM(RTRIM(UPPER(s.ssi_created_modified_by))) <>
					// LTRIM(RTRIM(UPPER('demouser'))) " +
					" AND s.ssi_reference_number  = a.ssi_reference_number";

			stmt = conn.prepareStatement(sql);

			stmt.setString(1, borrwerId);
			stmt.setString(2, memberId);

			// System.out.println("test2");
			rs = stmt.executeQuery();
			// System.out.println("test1");

			while (rs.next()) {
				String cgpan = (String) rs.getString(1);
				Date sancDate = (Date) rs.getDate(2);
				map.put(cgpan, sancDate);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				rs = null;
				stmt.close();
				stmt = null;
				DBConnection.freeConnection(conn);
			} catch (SQLException e) {
			}
		}
		return map;
	}

	// rajuk
	// rajukonkati
	public void updateClaimApplicationforResub(ClaimApplication claimApplication, HashMap map, boolean flag)
			throws DatabaseException {

		Log.log(4, "CPDAO", "saveClaimApplication()", "Entered!");
		GMProcessor gmprocessor = new GMProcessor();
		String claimRefNumber = claimApplication.getClaimRefNumber();

		String borrowerId = claimApplication.getBorrowerId();
		String memberId = claimApplication.getMemberId();
		memberId = memberId.trim();
		// System.out.println("memberId"+memberId);
		String bankId = memberId.substring(0, Types.INTEGER);
		String zoneId = memberId.substring(4, 8);
		String branchId = memberId.substring(8, Types.VARCHAR);

		MemberInfo memberInfo = getMemberInfoDetails(bankId, zoneId, branchId);

		String bankName = memberInfo.getMemberBankName();
		// System.out.println("bankName"+bankName);
		String participatingBank = claimApplication.getParticipatingBank();
		// System.out.println("participatingBank"+participatingBank);
		String whethereWillFulDfaulter = claimApplication.getWhetherBorrowerIsWilfulDefaulter();
		java.util.Date dtOfConclusionOfRecProc = claimApplication.getDtOfConclusionOfRecoveryProc();
		String whetherAccntWrittenOffBooks = claimApplication.getWhetherAccntWrittenOffFromBooksOfMLI();
		java.util.Date dtOfWrittenOffBooks = claimApplication.getDtOnWhichAccntWrittenOff();

		LegalProceedingsDetail legalProceedingsDetail = claimApplication.getLegalProceedingsDetails();

		java.util.Date filingdate = legalProceedingsDetail.getFilingDate();
		Log.log(4, "CPDAO", "saveClaimApplication()", "Filing Date is :" + filingdate);
		double amountClaimed = legalProceedingsDetail.getAmountClaimed();
		Log.log(4, "CPDAO", "saveClaimApplication()", "Amount Claimed is :" + amountClaimed);
		String currentStatusRemarks = legalProceedingsDetail.getCurrentStatusRemarks();
		Log.log(4, "CPDAO", "saveClaimApplication()", "Current Status Remarks is :" + currentStatusRemarks);

		String isRecoveryProceedingsComplete = legalProceedingsDetail.getIsRecoveryProceedingsConcluded();

		// System.out.println("isRecoveryProceedingsComplete"+isRecoveryProceedingsComplete);
		Log.log(4, "CPDAO", "saveClaimApplication()",
				"Recovery Proceedings Complete is :" + isRecoveryProceedingsComplete);
		java.util.Date claimSubmittedDate = claimApplication.getClaimSubmittedDate();
		java.util.Date dateOfReleaseOfWC = claimApplication.getDateOfReleaseOfWC();
		java.util.Date dateOfIssueOfRecallNotice = claimApplication.getDateOfIssueOfRecallNotice();
		String nameOfOfficial = claimApplication.getNameOfOfficial();

		System.out.println("iseligact" + nameOfOfficial);

		String designationOfOfficial = claimApplication.getDesignationOfOfficial();
		String place = claimApplication.getPlace();
		// added by rajuk
		String iseligact = claimApplication.getIseligact();
		System.out.println("iseligact" + iseligact);
		String iseligactcomm = claimApplication.getIseligactcomm();
		System.out.println("iseligactcomm" + iseligactcomm);

		String whetcibildone = claimApplication.getWhetcibildone();
		System.out.println("whetcibildone" + whetcibildone);

		String whetcibildonecomm = claimApplication.getWhetcibildonecomm();
		System.out.println("whetcibildonecomm" + isRecoveryProceedingsComplete);

		// ended by rajuk

		String createdModifiedBy = claimApplication.getCreatedModifiedy();
		String installmentFlag = null;
		if (claimApplication.getFirstInstallment()) {
			installmentFlag = "F";
		} else if (claimApplication.getSecondInstallment()) {
			installmentFlag = "S";
		}
		// System.out.println("installmentFlag"+installmentFlag);

		String cgpan = null;
		java.util.Date disbursementDate = null;
		double principalAmount = 0.0D;
		double interestAmnt = 0.0D;
		double npaAmount = 0.0D;
		double legalAmount = 0.0D;
		double claimAmount = 0.0D;

		CallableStatement callableStmt = null;
		Connection conn = null;
		boolean hasExceptionOccured = false;
		int status = -1;
		String errorCode = null;
		try {
			conn = DBConnection.getConnection(false);
			Statement stmt = conn.createStatement();
			ResultSet rs;
			int ischeklist = 0;
			int noOfClaims = 0;
			String query = "";
			// String statuses ="";

			query = "select count(*) from claim_check_list where clm_ref_no='" + claimRefNumber + "'";

			rs = stmt.executeQuery(query);

			System.out.println(query);

			while (rs.next()) {

				ischeklist = rs.getInt(1);
			}

			if (ischeklist > 0)

			{

				String isrataspercgs = claimApplication.getIsrataspercgs();
				String isrataspercgscomm = claimApplication.getIsrataspercgscomm();
				String isthirdcollattaken = claimApplication.getIsthirdcollattaken();
				String isthirdcollattakencomm = claimApplication.getIsthirdcollattakencomm();
				String isnpadtasperguid = claimApplication.getIsnpadtasperguid();
				String isnpadtasperguidcomm = claimApplication.getIsnpadtasperguidcomm();
				String isclmoswrtnpadt = claimApplication.getIsclmoswrtnpadt();
				String isclmoswrtnpadtcomm = claimApplication.getIsclmoswrtnpadtcomm();
				String whetseriousdeficinvol = claimApplication.getWhetseriousdeficinvol();
				String whetseriousdeficinvolcomm = claimApplication.getWhetseriousdeficinvolcomm();
				String whetmajordeficinvolvd = claimApplication.getWhetmajordeficinvolvd();
				String whetmajordeficinvolvdcomm = claimApplication.getWhetmajordeficinvolvdcomm();
				String whetdeficinvolbystaff = claimApplication.getWhetdeficinvolbystaff();
				String whetdeficinvolbystaffcomm = claimApplication.getWhetdeficinvolbystaffcomm();
				String isinternratinvestgrad = claimApplication.getIsinternratinvestgrad();
				String isinternratinvestgradcomm = claimApplication.getIsinternratinvestgradcomm();
				String isallrecinclmform = claimApplication.getIsallrecinclmform();
				String isallrecinclmformcomm = claimApplication.getIsallrecinclmformcomm();
				conn = DBConnection.getConnection(false);

				// added by rajuk

				callableStmt = conn.prepareCall(
						"{?=call funcInsertClaimCheckList(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
				callableStmt.setString(2, claimRefNumber);
				callableStmt.setString(3, borrowerId);
				callableStmt.setString(4, bankId);
				callableStmt.setString(5, zoneId);
				callableStmt.setString(6, branchId);

				callableStmt.setString(7, claimApplication.getIseligact());

				if (claimApplication.getIseligactcomm().equals("NA")) {
					callableStmt.setNull(8, java.sql.Types.VARCHAR);

				} else {

					callableStmt.setString(8, claimApplication.getIseligactcomm());

				}

				callableStmt.setString(9, claimApplication.getWhetcibildone());

				if (claimApplication.getWhetcibildonecomm().equals("NA")) {
					callableStmt.setNull(10, java.sql.Types.VARCHAR);

				} else {

					callableStmt.setString(10, claimApplication.getWhetcibildonecomm());

				}

				callableStmt.setString(11, claimApplication.getIsrataspercgs());
				if (claimApplication.getIsrataspercgscomm().equals("NA")) {
					callableStmt.setNull(12, java.sql.Types.VARCHAR);

				} else {

					callableStmt.setString(12, claimApplication.getIsrataspercgscomm());

				}

				callableStmt.setString(13, claimApplication.getIsthirdcollattaken());

				if (claimApplication.getIsthirdcollattakencomm().equals("NA")) {
					callableStmt.setNull(14, java.sql.Types.VARCHAR);

				} else {

					callableStmt.setString(14, claimApplication.getIsthirdcollattakencomm());

				}

				callableStmt.setString(15, claimApplication.getIsnpadtasperguid());

				if (claimApplication.getIsnpadtasperguidcomm().equals("NA")) {
					callableStmt.setNull(16, java.sql.Types.VARCHAR);

				} else {

					callableStmt.setString(16, claimApplication.getIsnpadtasperguidcomm());

				}

				callableStmt.setString(17, claimApplication.getIsclmoswrtnpadt());
				if (claimApplication.getIsclmoswrtnpadtcomm().equals("NA")) {
					callableStmt.setNull(18, java.sql.Types.VARCHAR);

				} else {

					callableStmt.setString(18, claimApplication.getIsclmoswrtnpadtcomm());

				}

				callableStmt.setString(19, claimApplication.getWhetseriousdeficinvol());
				if (claimApplication.getWhetseriousdeficinvolcomm().equals("NA")) {
					callableStmt.setNull(20, java.sql.Types.VARCHAR);

				} else {

					callableStmt.setString(20, claimApplication.getWhetseriousdeficinvolcomm());

				}

				callableStmt.setString(21, claimApplication.getWhetmajordeficinvolvd());
				if (claimApplication.getWhetmajordeficinvolvdcomm().equals("NA")) {
					callableStmt.setNull(22, java.sql.Types.VARCHAR);

				} else {

					callableStmt.setString(22, claimApplication.getWhetmajordeficinvolvdcomm());

				}

				callableStmt.setString(23, claimApplication.getWhetdeficinvolbystaff());
				if (claimApplication.getWhetdeficinvolbystaffcomm().equals("NA")) {
					callableStmt.setNull(24, java.sql.Types.VARCHAR);

				} else {

					callableStmt.setString(24, claimApplication.getWhetdeficinvolbystaffcomm());

				}

				callableStmt.setString(25, claimApplication.getIsinternratinvestgrad());
				if (claimApplication.getIsinternratinvestgradcomm().equals("NA")) {
					callableStmt.setNull(26, java.sql.Types.VARCHAR);

				} else {

					callableStmt.setString(26, claimApplication.getIsinternratinvestgradcomm());

				}

				callableStmt.setString(27, claimApplication.getIsallrecinclmform());
				if (claimApplication.getIsallrecinclmformcomm().equals("NA")) {
					callableStmt.setNull(28, java.sql.Types.VARCHAR);

				} else {

					callableStmt.setString(28, claimApplication.getIsallrecinclmformcomm());

				}

				callableStmt.registerOutParameter(29, java.sql.Types.VARCHAR);

				callableStmt.execute();
				status = callableStmt.getInt(1);

				errorCode = callableStmt.getString(29);

				if (status == Constants.FUNCTION_FAILURE) {

					Log.log(Log.ERROR, "CPDAO", "saveClaimApplication()",
							"SP returns a 1. Error code is :" + errorCode);
					callableStmt.close();
					try {
						conn.rollback();
					} catch (SQLException sqlex) {
						throw new DatabaseException(sqlex.getMessage());
					}
					throw new DatabaseException(errorCode);
				}
			}
			// conn.commit();

			// ended by rajuk

			/*
			 * callableStmt = conn.prepareCall("{?=call funcInsClaimTrail(?,?)}");
			 * callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			 * callableStmt.setString(2, claimRefNumber);
			 * callableStmt.registerOutParameter(3, java.sql.Types.VARCHAR);
			 * callableStmt.execute(); int trailstatus = callableStmt.getInt(1);
			 * if(trailstatus == 0){ callableStmt.close(); callableStmt = null; }else{ throw
			 * new DatabaseException("Unable to update claim trails."); }
			 */
			System.out.println("=====13180=====");

			callableStmt = conn.prepareCall(
					"{?=call funcUpdateClaimDetails(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt.setString(2, claimRefNumber);
			callableStmt.setString(3, borrowerId);
			callableStmt.setString(4, bankId);
			callableStmt.setString(5, zoneId);
			callableStmt.setString(6, branchId);
			callableStmt.setString(7, participatingBank);
			if (dateOfIssueOfRecallNotice != null) {
				callableStmt.setDate(8, new java.sql.Date(dateOfIssueOfRecallNotice.getTime()));
			} else {
				callableStmt.setNull(8, java.sql.Types.DATE);
			}
			if (filingdate != null) {
				callableStmt.setDate(9, new java.sql.Date(filingdate.getTime()));
			} else {
				callableStmt.setNull(9, java.sql.Types.DATE);
			}
			if (dateOfReleaseOfWC != null) {
				callableStmt.setDate(10, new java.sql.Date(dateOfReleaseOfWC.getTime()));
			} else {
				callableStmt.setNull(10, java.sql.Types.DATE);
			}
			callableStmt.setString(11, nameOfOfficial);
			callableStmt.setString(12, designationOfOfficial);
			callableStmt.setString(13, bankName);
			callableStmt.setString(14, place);
			if (claimSubmittedDate != null) {
				callableStmt.setDate(15, new java.sql.Date(claimSubmittedDate.getTime()));
			} else {
				callableStmt.setNull(15, java.sql.Types.DATE);
			}

			callableStmt.setString(16, installmentFlag);
			callableStmt.setString(17, createdModifiedBy);

			java.util.Date subsidyDate = (java.util.Date) claimApplication.getSubsidyDate();
			if (subsidyDate != null) {
				callableStmt.setDate(18, new java.sql.Date(subsidyDate.getTime()));
			} else {
				callableStmt.setNull(18, java.sql.Types.DATE);
			}
			callableStmt.setDouble(19, claimApplication.getSubsidyAmt());
			callableStmt.setString(20, claimApplication.getIfsCode());
			callableStmt.setString(21, claimApplication.getNeftCode());
			callableStmt.setString(22, claimApplication.getRtgsBankName());
			callableStmt.setString(23, claimApplication.getRtgsBranchName());
			callableStmt.setString(24, claimApplication.getRtgsBankNumber());
			callableStmt.setString(25, claimApplication.getMicroCategory());

			callableStmt.setString(26, claimApplication.getWilful());
			callableStmt.setString(27, claimApplication.getFraudFlag());
			callableStmt.setString(28, claimApplication.getEnquiryFlag());
			callableStmt.setString(29, claimApplication.getMliInvolvementFlag());
			callableStmt.setString(30, claimApplication.getReasonForRecall());
			callableStmt.setString(31, claimApplication.getReasonForFilingSuit());

			java.util.Date possessionDt = claimApplication.getAssetPossessionDt();
			if (possessionDt != null) {
				callableStmt.setDate(32, new java.sql.Date(possessionDt.getTime()));
			} else {
				callableStmt.setNull(32, java.sql.Types.DATE);
			}
			callableStmt.setString(33, claimApplication.getInclusionOfReceipt());
			callableStmt.setString(34, claimApplication.getConfirmRecoveryFlag());
			callableStmt.setString(35, claimApplication.getSubsidyFlag());
			callableStmt.setString(36, claimApplication.getIsSubsidyRcvdAfterNpa());
			callableStmt.setString(37, claimApplication.getIsSubsidyAdjustedOnDues());
			callableStmt.setString(38, claimApplication.getMliCommentOnFinPosition());
			callableStmt.setString(39, claimApplication.getDetailsOfFinAssistance());
			callableStmt.setString(40, claimApplication.getCreditSupport());
			callableStmt.setString(41, claimApplication.getBankFacilityDetail());
			callableStmt.setString(42, claimApplication.getPlaceUnderWatchList());
			callableStmt.setString(43, claimApplication.getRemarksOnNpa());

			callableStmt.setString(44, claimApplication.getDealingOfficerName());

			// callableStmt.registerOutParameter(44,java.sql.Types.VARCHAR);
			callableStmt.registerOutParameter(45, java.sql.Types.VARCHAR);
			// Added for GST Changes
			callableStmt.setString(46, claimApplication.getGstNo());
			callableStmt.setString(47, claimApplication.getGstStateCode());

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(45);

			if (status == Constants.FUNCTION_FAILURE) {

				Log.log(Log.ERROR, "CPDAO", "saveClaimApplication()", "SP returns a 1. Error code is :" + errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			// closing the callable statement
			callableStmt.close();
			callableStmt = null;

			Log.log(4, "CPDAO", "saveClaimApplication()", "Done with saving Generic Info of Claim Application");

			Log.log(4, "CPDAO", "saveClaimApplication()", "Saving Term Loan Dtls of Claim Application......");

			Vector tcDetails = claimApplication.getTermCapitalDtls();
			double osAsOnSecondClmLodgemnt = 0.0D;
			String tcClaimFlag = "";
			double totalDisbAmnt = 0.0D;

			if (tcDetails != null) {
				for (int i = 0; i < tcDetails.size(); i++) {
					TermLoanCapitalLoanDetail tcLoanDetail = (TermLoanCapitalLoanDetail) tcDetails.elementAt(i);
					if (tcLoanDetail != null) {
						cgpan = tcLoanDetail.getCgpan();
						disbursementDate = tcLoanDetail.getLastDisbursementDate();
						principalAmount = tcLoanDetail.getPrincipalRepayment();
						interestAmnt = tcLoanDetail.getInterestAndOtherCharges();
						npaAmount = tcLoanDetail.getOutstandingAsOnDateOfNPA();
						legalAmount = tcLoanDetail.getOutstandingStatedInCivilSuit();
						claimAmount = tcLoanDetail.getOutstandingAsOnDateOfLodgement();
						osAsOnSecondClmLodgemnt = tcLoanDetail.getOsAsOnDateOfLodgementOfClmForSecInstllmnt();
						tcClaimFlag = tcLoanDetail.getTcClaimFlag();
						totalDisbAmnt = tcLoanDetail.getTotaDisbAmnt();

						callableStmt = conn.prepareCall("{?=call funcUpdateClaimTLDetails(?,?,?,?,?,?,?,?,?,?,?,?)}");

						callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
						callableStmt.setString(2, claimRefNumber);
						callableStmt.setString(3, cgpan);
						if (disbursementDate != null) {
							callableStmt.setDate(4, new java.sql.Date(disbursementDate.getTime()));
						} else {
							callableStmt.setNull(4, java.sql.Types.DATE);
						}
						callableStmt.setDouble(5, principalAmount);
						callableStmt.setDouble(6, interestAmnt);
						callableStmt.setDouble(7, npaAmount);
						callableStmt.setDouble(8, legalAmount);
						callableStmt.setDouble(9, claimAmount);
						callableStmt.setDouble(10, osAsOnSecondClmLodgemnt);
						// callableStmt.registerOutParameter(11,java.sql.Types.VARCHAR);
						callableStmt.setString(11, tcClaimFlag);
						callableStmt.setDouble(12, totalDisbAmnt);
						callableStmt.registerOutParameter(13, java.sql.Types.VARCHAR);

						callableStmt.execute();
						status = callableStmt.getInt(1);
						// errorCode = callableStmt.getString(11);
						errorCode = callableStmt.getString(13);
						if (status == 1) {
							Log.log(2, "CPDAO", "saveClaimApplication()",
									"SP returns a 1. Error code is :" + errorCode);
							callableStmt.close();
							try {
								conn.rollback();
							} catch (SQLException sqlex) {
								throw new DatabaseException(sqlex.getMessage());
							}
							throw new DatabaseException(errorCode);
						}
						callableStmt.close();
					}
				}
			}
			Log.log(4, "CPDAO", "saveClaimApplication()", "Done with saving Term Loan Details of Claim Application");

			Log.log(4, "CPDAO", "saveClaimApplication()", "Saving Working Capital Dtls of Claim Application......");
			ArrayList wcDetails = claimApplication.getWorkingCapitalDtls();
			double osWCAsOnSecWCClmt = 0.0D;
			String wcClaimFlag = "";

			if (wcDetails != null) {
				for (int i = 0; i < wcDetails.size(); i++) {
					WorkingCapitalDetail wcDetail = (WorkingCapitalDetail) wcDetails.get(i);
					cgpan = wcDetail.getCgpan();
					npaAmount = wcDetail.getOutstandingAsOnDateOfNPA();
					legalAmount = wcDetail.getOutstandingStatedInCivilSuit();
					claimAmount = wcDetail.getOutstandingAsOnDateOfLodgement();
					osWCAsOnSecWCClmt = wcDetail.getOsAsOnDateOfLodgementOfClmForSecInstllmnt();
					wcClaimFlag = wcDetail.getWcClaimFlag();

					callableStmt = conn.prepareCall("{? = call funcUpdateClaimWCDetails(?,?,?,?,?,?,?,?)}");

					callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
					callableStmt.setString(2, claimRefNumber);
					callableStmt.setString(3, cgpan);
					callableStmt.setDouble(4, npaAmount);
					callableStmt.setDouble(5, legalAmount);
					callableStmt.setDouble(6, claimAmount);
					callableStmt.setDouble(7, osWCAsOnSecWCClmt);
					// callableStmt.registerOutParameter(8,java.sql.Types.VARCHAR);
					callableStmt.setString(8, wcClaimFlag);
					callableStmt.registerOutParameter(9, java.sql.Types.VARCHAR);
					callableStmt.execute();

					status = callableStmt.getInt(1);
					// errorCode = callableStmt.getString(8);
					errorCode = callableStmt.getString(9);
					if (status == 1) {
						Log.log(2, "CPDAO", "saveClaimApplication()", "SP returns a 1. Error code is :" + errorCode);
						callableStmt.close();
						try {
							conn.rollback();
						} catch (SQLException sqlex) {
							throw new DatabaseException(sqlex.getMessage());
						}
						throw new DatabaseException(errorCode);
					}

					callableStmt.close();
				}
			}
			Log.log(4, "CPDAO", "saveClaimApplication()",
					"Done with saving Working Capital Details of Claim Application");

			Log.log(4, "CPDAO", "saveClaimApplication()", "Saving Recovery Details of Claim Application");

			Vector vecRecoveryDetails = claimApplication.getRecoveryDetails();
			String recoveryMode = null;
			double tcPrincipal = 0.0D;
			double tcInterestAndOtherCharges = 0.0D;
			double wcAmount = 0.0D;
			double wcOtherCharges = 0.0D;

			if (vecRecoveryDetails != null) {
				for (int i = 0; i < vecRecoveryDetails.size(); i++) {
					RecoveryDetails recoveryDetail = (RecoveryDetails) vecRecoveryDetails.elementAt(i);
					if (recoveryDetail != null) {
						cgpan = recoveryDetail.getCgpan();
						recoveryMode = recoveryDetail.getModeOfRecovery();
						tcPrincipal = recoveryDetail.getTcPrincipal();
						tcInterestAndOtherCharges = recoveryDetail.getTcInterestAndOtherCharges();
						wcAmount = recoveryDetail.getWcAmount();
						wcOtherCharges = recoveryDetail.getWcOtherCharges();
						// if ((tcPrincipal != 0.0D) ||
						// (tcInterestAndOtherCharges != 0.0D) ||
						// (wcAmount != 0.0D) || (wcOtherCharges != 0.0D)) {
						callableStmt = conn.prepareCall("{?=call funcUpdateClaimRecoveryDetails(?,?,?,?,?,?,?,?)}");
						callableStmt.registerOutParameter(1, Types.INTEGER);
						callableStmt.setString(2, claimRefNumber);
						callableStmt.setString(3, cgpan);
						callableStmt.setString(4, recoveryMode);
						callableStmt.setDouble(5, tcPrincipal);
						callableStmt.setDouble(6, tcInterestAndOtherCharges);
						callableStmt.setDouble(7, wcAmount);
						callableStmt.setDouble(8, wcOtherCharges);
						callableStmt.registerOutParameter(9, java.sql.Types.VARCHAR);

						callableStmt.execute();
						status = callableStmt.getInt(1);
						errorCode = callableStmt.getString(9);
						if (status == 1) {
							Log.log(2, "CPDAO", "saveClaimApplication()",
									"SP returns a 1. Error code is :" + errorCode);
							callableStmt.close();
							try {
								conn.rollback();
							} catch (SQLException sqlex) {
								throw new DatabaseException(sqlex.getMessage());
							}
							throw new DatabaseException(errorCode);
						}

						callableStmt.close();
						// }
					}
				}
			}
			Log.log(4, "CPDAO", "saveClaimApplication()", "Done with saving Recovery Details of Claim Application");

			Log.log(4, "CPDAO", "saveClaimApplication()",
					"Saving Security and Personal Guarantee Dtls of Claim Application for as on Sanction......");
			SecurityAndPersonalGuaranteeDtls secAndPerGuaranteeDtl = claimApplication
					.getSecurityAndPersonalGuaranteeDtls();

			DtlsAsOnDateOfNPA dtlsAsOnNPA = secAndPerGuaranteeDtl.getDetailsAsOnDateOfNPA();
			DtlsAsOnDateOfSanction dtlsAsOnSanction = secAndPerGuaranteeDtl.getDetailsAsOnDateOfSanction();
			DtlsAsOnLogdementOfClaim dtlsAsOnLodgeOfClaim = secAndPerGuaranteeDtl
					.getDetailsAsOnDateOfLodgementOfClaim();
			DtlsAsOnLogdementOfSecondClaim dtlsAsOnLodgemntOfSecClm = secAndPerGuaranteeDtl
					.getDetailsAsOnDateOfLodgementOfSecondClaim();

			double networthAsOnSanction = dtlsAsOnSanction.getNetworthOfGuarantors();
			double amntRealizedThruInvocationPerGuar = 0.0D;
			double amntRealizedThruSecurity = 0.0D;
			String reasonForReduction = null;
			String reasonForReductionAsOnSanction = dtlsAsOnSanction.getReasonsForReduction();
			String claimSecurityIdAsOnSanction = null;

			callableStmt = conn.prepareCall("{?=call funcUpdateClaimSecurityDetail(?,?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt.setString(2, claimRefNumber);
			callableStmt.setString(3, ClaimConstants.CLM_SAPGD_AS_ON_SANCTION_CODE);
			callableStmt.setDouble(4, networthAsOnSanction);
			callableStmt.setString(5, reasonForReductionAsOnSanction);
			callableStmt.setDouble(6, amntRealizedThruInvocationPerGuar);
			callableStmt.registerOutParameter(7, java.sql.Types.VARCHAR);
			callableStmt.registerOutParameter(8, java.sql.Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(8);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing As on Dt of Sanction Dtl. Error code is :" + errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			if (status == 0) {
				claimSecurityIdAsOnSanction = callableStmt.getString(7);

				callableStmt.close();
			}

			// Calling the next SP to save security and personal guarantee
			// particulars
			// For Land
			callableStmt = conn.prepareCall("{?=call funcUpdateClaimPerGuarDetail(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			if ((claimSecurityIdAsOnSanction != null) && (!(claimSecurityIdAsOnSanction.equals("")))) {
				callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnSanction));
			}
			callableStmt.setString(3, ClaimConstants.CLM_SAPGD_PARTICULAR_LAND);
			double vallandsanction = dtlsAsOnSanction.getValueOfLand();
			callableStmt.setDouble(4, vallandsanction);
			callableStmt.setDouble(5, amntRealizedThruSecurity);
			callableStmt.setString(6, reasonForReduction);
			callableStmt.registerOutParameter(7, java.sql.Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing Land as on Dt of Sanction. Error code is :" + errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			Log.log(4, "CPDAO", "saveClaimApplication()", "Successfully saved - Land for As on Dt of Sanction");

			callableStmt = conn.prepareCall("{?=call funcUpdateClaimPerGuarDetail(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			if ((claimSecurityIdAsOnSanction != null) && (!claimSecurityIdAsOnSanction.equals(""))) {
				callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnSanction));
			}
			callableStmt.setString(3, "MACHINE");
			double valmachinesanction = dtlsAsOnSanction.getValueOfMachine();
			callableStmt.setDouble(4, valmachinesanction);
			callableStmt.setDouble(5, amntRealizedThruSecurity);
			callableStmt.setString(6, reasonForReduction);
			callableStmt.registerOutParameter(7, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing Machine as on Dt of Sanction. Error code is :" + errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			Log.log(4, "CPDAO", "saveClaimApplication()", "Successfully saved - Machine for As on Dt of Sanction");

			// For Building
			callableStmt = conn.prepareCall("{?=call funcUpdateClaimPerGuarDetail(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			if ((claimSecurityIdAsOnSanction != null) && (!(claimSecurityIdAsOnSanction.equals("")))) {
				callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnSanction));
			}
			callableStmt.setString(3, ClaimConstants.CLM_SAPGD_PARTICULAR_BLDG);
			double valbldgesanction = dtlsAsOnSanction.getValueOfBuilding();
			callableStmt.setDouble(4, valbldgesanction);
			callableStmt.setDouble(5, amntRealizedThruSecurity);
			callableStmt.setString(6, reasonForReduction);
			callableStmt.registerOutParameter(7, java.sql.Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing Building as on Dt of Sanction. Error code is :" + errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			Log.log(4, "CPDAO", "saveClaimApplication()", "Successfully saved - Building for As on Dt of Sanction");

			// For Other Fixed/Movable Assets
			callableStmt = conn.prepareCall("{?=call funcUpdateClaimPerGuarDetail(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			if ((claimSecurityIdAsOnSanction != null) && (!(claimSecurityIdAsOnSanction.equals("")))) {
				callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnSanction));
			}
			callableStmt.setString(3, ClaimConstants.CLM_SAPGD_PARTICULAR_OTHER_FIXED_MOV_ASSETS);
			double valfixedmovableassetssanction = dtlsAsOnSanction.getValueOfOtherFixedMovableAssets();
			callableStmt.setDouble(4, valfixedmovableassetssanction);
			callableStmt.setDouble(5, amntRealizedThruSecurity);
			callableStmt.setString(6, reasonForReduction);
			callableStmt.registerOutParameter(7, java.sql.Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing Other Fixed/Movable Assets as on Dt of Sanction. Error code is :"
								+ errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			Log.log(4, "CPDAO", "saveClaimApplication()",
					"Successfully saved - Fixed and Movable Assets for As on Dt of Sanction");

			// For Current Assets
			callableStmt = conn.prepareCall("{?=call funcUpdateClaimPerGuarDetail(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			if ((claimSecurityIdAsOnSanction != null) && (!(claimSecurityIdAsOnSanction.equals("")))) {
				callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnSanction));
			}
			callableStmt.setString(3, ClaimConstants.CLM_SAPGD_PARTICULAR_CUR_ASSETS);
			double valcurrassetssanction = dtlsAsOnSanction.getValueOfCurrentAssets();
			callableStmt.setDouble(4, valcurrassetssanction);
			callableStmt.setDouble(5, amntRealizedThruSecurity);
			callableStmt.setString(6, reasonForReduction);
			callableStmt.registerOutParameter(7, java.sql.Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing Current Assets as on Dt of Sanction. Error code is :" + errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			Log.log(4, "CPDAO", "saveClaimApplication()",
					"Successfully saved - Current Assets for As on Dt of Sanction");

			// For other assets
			callableStmt = conn.prepareCall("{?=call funcUpdateClaimPerGuarDetail(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			if ((claimSecurityIdAsOnSanction != null) && (!(claimSecurityIdAsOnSanction.equals("")))) {
				callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnSanction));
			}
			callableStmt.setString(3, ClaimConstants.CLM_SAPGD_PARTICULAR_OTHERS);
			double valotherssanction = dtlsAsOnSanction.getValueOfOthers();
			callableStmt.setDouble(4, valotherssanction);
			callableStmt.setDouble(5, amntRealizedThruSecurity);
			callableStmt.setString(6, reasonForReduction);
			callableStmt.registerOutParameter(7, java.sql.Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing Other Assets as on Dt of Sanction. Error code is :" + errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			Log.log(4, "CPDAO", "saveClaimApplication()", "Successfully saved - Other Assets for As on Dt of Sanction");
			Log.log(4, "CPDAO", "saveClaimApplication()",
					"Done with Security and Personal Guarantee Dtls of Claim Application for as on Sanction");

			Log.log(4, "CPDAO", "saveClaimApplication()",
					"Saving Security and Personal Guarantee Dtls of Claim Application for as on date of NPA");
			double networthAsOnNPA = dtlsAsOnNPA.getNetworthOfGuarantors();
			String reasonForReductionAsOnNPA = dtlsAsOnNPA.getReasonsForReduction();
			String claimSecurityIdAsOnNPA = null;

			callableStmt = conn.prepareCall("{?=call funcUpdateClaimSecurityDetail(?,?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt.setString(2, claimRefNumber);
			callableStmt.setString(3, ClaimConstants.CLM_SAPGD_AS_ON_NPA_CODE);
			callableStmt.setDouble(4, networthAsOnNPA);
			callableStmt.setString(5, reasonForReductionAsOnNPA);
			callableStmt.setDouble(6, amntRealizedThruInvocationPerGuar);
			callableStmt.registerOutParameter(7, java.sql.Types.VARCHAR);
			callableStmt.registerOutParameter(8, java.sql.Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(8);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing NPA Dtls. Error code is :" + errorCode);

				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			if (status == 0) {
				claimSecurityIdAsOnNPA = callableStmt.getString(7);

				callableStmt.close();
			}

			// Calling the next SP to save security and personal guarantee
			// particulars
			// For Land
			callableStmt = conn.prepareCall("{?=call funcUpdateClaimPerGuarDetail(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			if ((claimSecurityIdAsOnNPA != null) && (!(claimSecurityIdAsOnNPA.equals("")))) {
				callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnNPA));
			}
			callableStmt.setString(3, ClaimConstants.CLM_SAPGD_PARTICULAR_LAND);
			double vallandnpa = dtlsAsOnNPA.getValueOfLand();
			callableStmt.setDouble(4, vallandnpa);
			callableStmt.setDouble(5, amntRealizedThruSecurity);
			callableStmt.setString(6, reasonForReduction);
			callableStmt.registerOutParameter(7, java.sql.Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing Land as on NPA. Error code is :" + errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			Log.log(4, "CPDAO", "saveClaimApplication()", "Successfully saved - Land for As on Dt of NPA");

			callableStmt = conn.prepareCall("{?=call funcUpdateClaimPerGuarDetail(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			if ((claimSecurityIdAsOnNPA != null) && (!claimSecurityIdAsOnNPA.equals(""))) {
				callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnNPA));
			}
			callableStmt.setString(3, "MACHINE");
			double valmachinenpa = dtlsAsOnNPA.getValueOfMachine();
			callableStmt.setDouble(4, valmachinenpa);
			callableStmt.setDouble(5, amntRealizedThruSecurity);
			callableStmt.setString(6, reasonForReduction);
			callableStmt.registerOutParameter(7, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing Machine as on NPA. Error code is :" + errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			Log.log(4, "CPDAO", "saveClaimApplication()", "Successfully saved - Machine for As on Dt of NPA");

			callableStmt = conn.prepareCall("{?=call funcUpdateClaimPerGuarDetail(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			if ((claimSecurityIdAsOnNPA != null) && (!claimSecurityIdAsOnNPA.equals(""))) {
				callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnNPA));
			}
			callableStmt.setString(3, "BUILDING");
			double valbldgnpa = dtlsAsOnNPA.getValueOfBuilding();
			callableStmt.setDouble(4, valbldgnpa);
			callableStmt.setDouble(5, amntRealizedThruSecurity);
			callableStmt.setString(6, reasonForReduction);
			callableStmt.registerOutParameter(7, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing Building as on NPA. Error code is :" + errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			Log.log(4, "CPDAO", "saveClaimApplication()", "Successfully saved - Building for As on Dt of NPA");

			callableStmt = conn.prepareCall("{?=call funcUpdateClaimPerGuarDetail(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			if ((claimSecurityIdAsOnNPA != null) && (!claimSecurityIdAsOnNPA.equals(""))) {
				callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnNPA));
			}
			callableStmt.setString(3, "OTHER FIXED MOVABLE ASSETS");
			double valfixedmovassetsnpa = dtlsAsOnNPA.getValueOfOtherFixedMovableAssets();
			callableStmt.setDouble(4, valfixedmovassetsnpa);
			callableStmt.setDouble(5, amntRealizedThruSecurity);
			callableStmt.setString(6, reasonForReduction);
			callableStmt.registerOutParameter(7, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing Fixed/Movable Assets as on NPA. Error code is :" + errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			Log.log(4, "CPDAO", "saveClaimApplication()",
					"Successfully saved - Fixed and Movable Assets for As on Dt of NPA");

			callableStmt = conn.prepareCall("{?=call funcUpdateClaimPerGuarDetail(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			if ((claimSecurityIdAsOnNPA != null) && (!claimSecurityIdAsOnNPA.equals(""))) {
				callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnNPA));
			}
			callableStmt.setString(3, "CURRENT ASSETS");
			double valcurrassetsnpa = dtlsAsOnNPA.getValueOfCurrentAssets();
			callableStmt.setDouble(4, valcurrassetsnpa);
			callableStmt.setDouble(5, amntRealizedThruSecurity);
			callableStmt.setString(6, reasonForReduction);
			callableStmt.registerOutParameter(7, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing Current Assets as on NPA. Error code is :" + errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			Log.log(4, "CPDAO", "saveClaimApplication()", "Successfully saved - Current Assets for As on Dt of NPA");

			callableStmt = conn.prepareCall("{?=call funcUpdateClaimPerGuarDetail(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			if ((claimSecurityIdAsOnNPA != null) && (!claimSecurityIdAsOnNPA.equals(""))) {
				callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnNPA));
			}
			callableStmt.setString(3, "OTHERS");
			double valothersnpa = dtlsAsOnNPA.getValueOfOthers();
			callableStmt.setDouble(4, valothersnpa);
			callableStmt.setDouble(5, amntRealizedThruSecurity);
			callableStmt.setString(6, reasonForReduction);
			callableStmt.registerOutParameter(7, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing Others as on NPA. Error code is :" + errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			Log.log(4, "CPDAO", "saveClaimApplication()", "Successfully saved - Other Assets for As on Dt of NPA");
			Log.log(4, "CPDAO", "saveClaimApplication()",
					"Done with saving Security and Personal Guarantee Dtls of Claim Application for as on dt of npa");

			Log.log(4, "CPDAO", "saveClaimApplication()",
					"Saving Security and Personal Guarantee Details of Claim Application for as on date of lodgement of claim");
			double networthAsOnLodgement = dtlsAsOnLodgeOfClaim.getNetworthOfGuarantors();
			String reasonForReductionAsOnLodgeOfClaim = dtlsAsOnLodgeOfClaim.getReasonsForReduction();
			String claimSecurityIdAsOnLodgeOfClaim = null;

			callableStmt = conn.prepareCall("{?=call funcUpdateClaimSecurityDetail(?,?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			callableStmt.setString(2, claimRefNumber);
			callableStmt.setString(3, "CLM");
			callableStmt.setDouble(4, networthAsOnLodgement);
			callableStmt.setString(5, reasonForReductionAsOnLodgeOfClaim);
			callableStmt.setDouble(6, amntRealizedThruInvocationPerGuar);
			callableStmt.registerOutParameter(7, Types.VARCHAR);
			callableStmt.registerOutParameter(8, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(8);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing As on Dt of Lodgemnt of Claim Dtl. Error code is :" + errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			if (status == 0) {
				claimSecurityIdAsOnLodgeOfClaim = callableStmt.getString(7);

				callableStmt.close();
			}

			callableStmt = conn.prepareCall("{?=call funcUpdateClaimPerGuarDetail(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			if ((claimSecurityIdAsOnLodgeOfClaim != null) && (!claimSecurityIdAsOnLodgeOfClaim.equals(""))) {
				callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnLodgeOfClaim));
			}
			callableStmt.setString(3, "LAND");
			double vallandlodgeclm = dtlsAsOnLodgeOfClaim.getValueOfLand();
			callableStmt.setDouble(4, vallandlodgeclm);
			callableStmt.setDouble(5, amntRealizedThruSecurity);
			callableStmt.setString(6, reasonForReduction);
			callableStmt.registerOutParameter(7, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing Land As on Dt of Lodgemnt of Claim Dtl. Error code is :"
								+ errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			Log.log(4, "CPDAO", "saveClaimApplication()",
					"Successfully saved - Land for As on Dt of Lodgement of First Claim");

			callableStmt = conn.prepareCall("{?=call funcUpdateClaimPerGuarDetail(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			if ((claimSecurityIdAsOnLodgeOfClaim != null) && (!claimSecurityIdAsOnLodgeOfClaim.equals(""))) {
				callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnLodgeOfClaim));
			}
			callableStmt.setString(3, "MACHINE");
			double valmclodgeclm = dtlsAsOnLodgeOfClaim.getValueOfMachine();
			callableStmt.setDouble(4, valmclodgeclm);
			callableStmt.setDouble(5, amntRealizedThruSecurity);
			callableStmt.setString(6, reasonForReduction);
			callableStmt.registerOutParameter(7, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing Machine As on Dt of Lodgemnt of Claim Dtl. Error code is :"
								+ errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			Log.log(4, "CPDAO", "saveClaimApplication()",
					"Successfully saved - Machine for As on Dt of Lodgement of First Claim");

			callableStmt = conn.prepareCall("{?=call funcUpdateClaimPerGuarDetail(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			if ((claimSecurityIdAsOnLodgeOfClaim != null) && (!claimSecurityIdAsOnLodgeOfClaim.equals(""))) {
				callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnLodgeOfClaim));
			}
			callableStmt.setString(3, "BUILDING");
			double valbldglodgeclm = dtlsAsOnLodgeOfClaim.getValueOfBuilding();
			callableStmt.setDouble(4, valbldglodgeclm);
			callableStmt.setDouble(5, amntRealizedThruSecurity);
			callableStmt.setString(6, reasonForReduction);
			callableStmt.registerOutParameter(7, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing Building As on Dt of Lodgemnt of Claim Dtl. Error code is :"
								+ errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			Log.log(4, "CPDAO", "saveClaimApplication()",
					"Successfully saved - Building for As on Dt of Lodgement of First Claim");

			callableStmt = conn.prepareCall("{?=call funcUpdateClaimPerGuarDetail(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			if ((claimSecurityIdAsOnLodgeOfClaim != null) && (!claimSecurityIdAsOnLodgeOfClaim.equals(""))) {
				callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnLodgeOfClaim));
			}
			callableStmt.setString(3, "OTHER FIXED MOVABLE ASSETS");
			double valfixedmovassetslodgeclm = dtlsAsOnLodgeOfClaim.getValueOfOtherFixedMovableAssets();
			callableStmt.setDouble(4, valfixedmovassetslodgeclm);
			callableStmt.setDouble(5, amntRealizedThruSecurity);
			callableStmt.setString(6, reasonForReduction);
			callableStmt.registerOutParameter(7, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing Fixed Movable Assets As on Dt of Lodgemnt of Claim Dtl. Error code is :"
								+ errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			Log.log(4, "CPDAO", "saveClaimApplication()",
					"Successfully saved - Fixed Assets for As on Dt of Lodgement of First Claim");

			callableStmt = conn.prepareCall("{?=call funcUpdateClaimPerGuarDetail(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			if ((claimSecurityIdAsOnLodgeOfClaim != null) && (!claimSecurityIdAsOnLodgeOfClaim.equals(""))) {
				callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnLodgeOfClaim));
			}
			callableStmt.setString(3, "CURRENT ASSETS");
			double valcurrassetslodgeclm = dtlsAsOnLodgeOfClaim.getValueOfCurrentAssets();
			callableStmt.setDouble(4, valcurrassetslodgeclm);
			callableStmt.setDouble(5, amntRealizedThruSecurity);
			callableStmt.setString(6, reasonForReduction);
			callableStmt.registerOutParameter(7, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing Current Assets As on Dt of Lodgemnt of Claim Dtl. Error code is :"
								+ errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			Log.log(4, "CPDAO", "saveClaimApplication()",
					"Successfully saved - Current Assets for As on Dt of Lodgement of First Claim");

			callableStmt = conn.prepareCall("{?=call funcUpdateClaimPerGuarDetail(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			if ((claimSecurityIdAsOnLodgeOfClaim != null) && (!claimSecurityIdAsOnLodgeOfClaim.equals(""))) {
				callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnLodgeOfClaim));
			}
			callableStmt.setString(3, "OTHERS");
			double valotherslodgeclm = dtlsAsOnLodgeOfClaim.getValueOfOthers();
			callableStmt.setDouble(4, valotherslodgeclm);
			callableStmt.setDouble(5, amntRealizedThruSecurity);
			callableStmt.setString(6, reasonForReduction);
			callableStmt.registerOutParameter(7, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);
			if (status == 1) {
				Log.log(2, "CPDAO", "saveClaimApplication()",
						"SP returns a 1 in storing Others As on Dt of Lodgemnt of Claim Dtl. Error code is :"
								+ errorCode);
				callableStmt.close();
				try {
					conn.rollback();
				} catch (SQLException sqlex) {
					throw new DatabaseException(sqlex.getMessage());
				}
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			Log.log(4, "CPDAO", "saveClaimApplication()",
					"Successfully saved - Other Assets for As on Dt of Lodgement of First Claim");
			Log.log(4, "CPDAO", "saveClaimApplication()",
					"Done with saving Security and Personal Guarantee Details of Claim Application for as on dt of lodgement of claim");

			Log.log(4, "CPDAO", "saveClaimApplication()",
					"Saving Security and Personal Guarantee Details of Claim Application for as on dt of lodgement of second claim.....");
			if (claimApplication.getSecondInstallment()) {
				double networthAsOnLodgmntOfSecClm = dtlsAsOnLodgemntOfSecClm.getNetworthOfGuarantors();
				amntRealizedThruInvocationPerGuar = dtlsAsOnLodgemntOfSecClm.getAmtRealisedPersonalGuarantee();

				String reasonForReductionAsOnLodgeOfSecClaim = "";
				String claimSecurityIdAsOnLodgeOfSecClaim = null;

				callableStmt = conn.prepareCall("{?=call funcUpdateClaimSecurityDetail(?,?,?,?,?,?,?)}");
				callableStmt.registerOutParameter(1, Types.INTEGER);
				callableStmt.setString(2, claimRefNumber);
				callableStmt.setString(3, "SCLM");
				callableStmt.setDouble(4, networthAsOnLodgmntOfSecClm);
				callableStmt.setString(5, reasonForReductionAsOnLodgeOfSecClaim);
				callableStmt.setDouble(6, amntRealizedThruInvocationPerGuar);
				callableStmt.registerOutParameter(7, Types.VARCHAR);
				callableStmt.registerOutParameter(8, Types.VARCHAR);

				callableStmt.execute();
				status = callableStmt.getInt(1);
				errorCode = callableStmt.getString(8);
				if (status == 1) {
					Log.log(2, "CPDAO", "saveClaimApplication()",
							"SP returns a 1 in storing As on Dt of Lodgemnt of Second Claim Dtl. Error code is :"
									+ errorCode);
					callableStmt.close();
					try {
						conn.rollback();
					} catch (SQLException sqlex) {
						throw new DatabaseException(sqlex.getMessage());
					}
					throw new DatabaseException(errorCode);
				}
				if (status == 0) {
					claimSecurityIdAsOnLodgeOfSecClaim = callableStmt.getString(7);

					callableStmt.close();
				}

				amntRealizedThruSecurity = dtlsAsOnLodgemntOfSecClm.getAmtRealisedLand();
				reasonForReduction = dtlsAsOnLodgemntOfSecClm.getReasonsForReductionLand();
				callableStmt = conn.prepareCall("{?=call funcUpdateClaimPerGuarDetail(?,?,?,?,?,?)}");
				callableStmt.registerOutParameter(1, Types.INTEGER);
				if ((claimSecurityIdAsOnLodgeOfSecClaim != null) && (!claimSecurityIdAsOnLodgeOfSecClaim.equals(""))) {
					callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnLodgeOfSecClaim));
				}
				callableStmt.setString(3, "LAND");
				double vallandlodgeOfSecclm = dtlsAsOnLodgemntOfSecClm.getValueOfLand();
				callableStmt.setDouble(4, vallandlodgeOfSecclm);
				callableStmt.setDouble(5, amntRealizedThruSecurity);
				callableStmt.setString(6, reasonForReduction);
				callableStmt.registerOutParameter(7, Types.VARCHAR);

				callableStmt.execute();
				status = callableStmt.getInt(1);
				errorCode = callableStmt.getString(7);
				if (status == 1) {
					Log.log(2, "CPDAO", "saveClaimApplication()",
							"SP returns a 1 in storing Land As on Dt of Lodgemnt of Second Claim Dtl. Error code is :"
									+ errorCode);
					callableStmt.close();
					try {
						conn.rollback();
					} catch (SQLException sqlex) {
						throw new DatabaseException(sqlex.getMessage());
					}
					throw new DatabaseException(errorCode);
				}
				callableStmt.close();
				Log.log(4, "CPDAO", "saveClaimApplication()",
						"Successfully saved - Land for As on Dt of Lodgement of Second Claim");

				amntRealizedThruSecurity = dtlsAsOnLodgemntOfSecClm.getAmtRealisedMachine();
				reasonForReduction = dtlsAsOnLodgemntOfSecClm.getReasonsForReductionMachine();
				callableStmt = conn.prepareCall("{?=call funcUpdateClaimPerGuarDetail(?,?,?,?,?,?)}");
				callableStmt.registerOutParameter(1, Types.INTEGER);
				if ((claimSecurityIdAsOnLodgeOfSecClaim != null) && (!claimSecurityIdAsOnLodgeOfSecClaim.equals(""))) {
					callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnLodgeOfSecClaim));
				}
				callableStmt.setString(3, "MACHINE");
				double valmclodgeOfSecclm = dtlsAsOnLodgemntOfSecClm.getValueOfMachine();
				callableStmt.setDouble(4, valmclodgeOfSecclm);
				callableStmt.setDouble(5, amntRealizedThruSecurity);
				callableStmt.setString(6, reasonForReduction);
				callableStmt.registerOutParameter(7, Types.VARCHAR);

				callableStmt.execute();
				status = callableStmt.getInt(1);
				errorCode = callableStmt.getString(7);
				if (status == 1) {
					Log.log(2, "CPDAO", "saveClaimApplication()",
							"SP returns a 1 in storing Machine As on Dt of Lodgemnt of Second Claim Dtl. Error code is :"
									+ errorCode);
					callableStmt.close();
					try {
						conn.rollback();
					} catch (SQLException sqlex) {
						throw new DatabaseException(sqlex.getMessage());
					}
					throw new DatabaseException(errorCode);
				}
				callableStmt.close();
				Log.log(4, "CPDAO", "saveClaimApplication()",
						"Successfully saved - Machine for As on Dt of Lodgement of Second Claim");

				amntRealizedThruSecurity = dtlsAsOnLodgemntOfSecClm.getAmtRealisedBuilding();
				reasonForReduction = dtlsAsOnLodgemntOfSecClm.getReasonsForReductionBuilding();
				callableStmt = conn.prepareCall("{?=call funcUpdateClaimPerGuarDetail(?,?,?,?,?,?)}");
				callableStmt.registerOutParameter(1, Types.INTEGER);
				if ((claimSecurityIdAsOnLodgeOfSecClaim != null) && (!claimSecurityIdAsOnLodgeOfSecClaim.equals(""))) {
					callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnLodgeOfSecClaim));
				}
				callableStmt.setString(3, "BUILDING");
				double valbldglodgeOfSecclm = dtlsAsOnLodgemntOfSecClm.getValueOfBuilding();
				callableStmt.setDouble(4, valbldglodgeOfSecclm);
				callableStmt.setDouble(5, amntRealizedThruSecurity);
				callableStmt.setString(6, reasonForReduction);
				callableStmt.registerOutParameter(7, Types.VARCHAR);

				callableStmt.execute();
				status = callableStmt.getInt(1);
				errorCode = callableStmt.getString(7);
				if (status == 1) {
					Log.log(2, "CPDAO", "saveClaimApplication()",
							"SP returns a 1 in storing Building As on Dt of Lodgemnt of Second Claim Dtl. Error code is :"
									+ errorCode);
					callableStmt.close();
					try {
						conn.rollback();
					} catch (SQLException sqlex) {
						throw new DatabaseException(sqlex.getMessage());
					}
					throw new DatabaseException(errorCode);
				}
				callableStmt.close();
				Log.log(4, "CPDAO", "saveClaimApplication()",
						"Successfully saved - Building for As on Dt of Lodgement of Second Claim");

				amntRealizedThruSecurity = dtlsAsOnLodgemntOfSecClm.getAmtRealisedFixed();
				reasonForReduction = dtlsAsOnLodgemntOfSecClm.getReasonsForReductionFixed();
				callableStmt = conn.prepareCall("{?=call funcUpdateClaimPerGuarDetail(?,?,?,?,?,?)}");
				callableStmt.registerOutParameter(1, Types.INTEGER);
				if ((claimSecurityIdAsOnLodgeOfSecClaim != null) && (!claimSecurityIdAsOnLodgeOfSecClaim.equals(""))) {
					callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnLodgeOfSecClaim));
				}
				callableStmt.setString(3, "OTHER FIXED MOVABLE ASSETS");
				double valfixedmovassetslodgeOfSecclm = dtlsAsOnLodgemntOfSecClm.getValueOfOtherFixedMovableAssets();
				callableStmt.setDouble(4, valfixedmovassetslodgeOfSecclm);
				callableStmt.setDouble(5, amntRealizedThruSecurity);
				callableStmt.setString(6, reasonForReduction);
				callableStmt.registerOutParameter(7, Types.VARCHAR);

				callableStmt.execute();
				status = callableStmt.getInt(1);
				errorCode = callableStmt.getString(7);
				if (status == 1) {
					Log.log(2, "CPDAO", "saveClaimApplication()",
							"SP returns a 1 in storing Fixed/Movable Assets As on Dt of Lodgemnt of Second Claim Dtl. Error code is :"
									+ errorCode);
					callableStmt.close();
					try {
						conn.rollback();
					} catch (SQLException sqlex) {
						throw new DatabaseException(sqlex.getMessage());
					}
					throw new DatabaseException(errorCode);
				}
				callableStmt.close();
				Log.log(4, "CPDAO", "saveClaimApplication()",
						"Successfully saved - Fixed Assets for As on Dt of Lodgement of Second Claim");

				amntRealizedThruSecurity = dtlsAsOnLodgemntOfSecClm.getAmtRealisedCurrent();
				reasonForReduction = dtlsAsOnLodgemntOfSecClm.getReasonsForReductionCurrent();
				callableStmt = conn.prepareCall("{?=call funcUpdateClaimPerGuarDetail(?,?,?,?,?,?)}");
				callableStmt.registerOutParameter(1, Types.INTEGER);
				if ((claimSecurityIdAsOnLodgeOfSecClaim != null) && (!claimSecurityIdAsOnLodgeOfSecClaim.equals(""))) {
					callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnLodgeOfSecClaim));
				}
				callableStmt.setString(3, "CURRENT ASSETS");
				double valcurrassetslodgeOfSecclm = dtlsAsOnLodgemntOfSecClm.getValueOfCurrentAssets();
				callableStmt.setDouble(4, valcurrassetslodgeOfSecclm);
				callableStmt.setDouble(5, amntRealizedThruSecurity);
				callableStmt.setString(6, reasonForReduction);
				callableStmt.registerOutParameter(7, Types.VARCHAR);

				callableStmt.execute();
				status = callableStmt.getInt(1);
				errorCode = callableStmt.getString(7);
				if (status == 1) {
					Log.log(2, "CPDAO", "saveClaimApplication()",
							"SP returns a 1 in storing Current Assets As on Dt of Lodgemnt of Second Claim Dtl. Error code is :"
									+ errorCode);
					callableStmt.close();
					try {
						conn.rollback();
					} catch (SQLException sqlex) {
						throw new DatabaseException(sqlex.getMessage());
					}
					throw new DatabaseException(errorCode);
				}
				callableStmt.close();
				Log.log(4, "CPDAO", "saveClaimApplication()",
						"Successfully saved - Current Assets for As on Dt of Lodgement of Second Claim");

				amntRealizedThruSecurity = dtlsAsOnLodgemntOfSecClm.getAmtRealisedOthers();
				reasonForReduction = dtlsAsOnLodgemntOfSecClm.getReasonsForReductionOthers();
				callableStmt = conn.prepareCall("{?=call funcUpdateClaimPerGuarDetail(?,?,?,?,?,?)}");
				callableStmt.registerOutParameter(1, Types.INTEGER);
				if ((claimSecurityIdAsOnLodgeOfSecClaim != null) && (!claimSecurityIdAsOnLodgeOfSecClaim.equals(""))) {
					callableStmt.setDouble(2, Double.parseDouble(claimSecurityIdAsOnLodgeOfSecClaim));
				}
				callableStmt.setString(3, "OTHERS");
				double valotherslodgeOfSecclm = dtlsAsOnLodgemntOfSecClm.getValueOfOthers();
				callableStmt.setDouble(4, valotherslodgeOfSecclm);
				callableStmt.setDouble(5, amntRealizedThruSecurity);
				callableStmt.setString(6, reasonForReduction);
				callableStmt.registerOutParameter(7, Types.VARCHAR);

				callableStmt.execute();
				status = callableStmt.getInt(1);
				errorCode = callableStmt.getString(7);
				if (status == 1) {
					Log.log(2, "CPDAO", "saveClaimApplication()",
							"SP returns a 1 in storing Others As on Dt of Lodgemnt of Second Claim Dtl. Error code is :"
									+ errorCode);
					callableStmt.close();
					try {
						conn.rollback();
					} catch (SQLException sqlex) {
						throw new DatabaseException(sqlex.getMessage());
					}
					throw new DatabaseException(errorCode);
				}
				callableStmt.close();
				Log.log(4, "CPDAO", "saveClaimApplication()",
						"Successfully saved - Other Assets for As on Dt of Lodgement of Second Claim");
			}
			Log.log(4, "CPDAO", "saveClaimApplication()",
					"Done with saving Security and Personal Guarantee Details of Claim Application for as on dt of lodgement of second claim");

			Log.log(4, "CPDAO", "saveClaimApplication()", "Saving Claim Summary Details of Claim Application");
			ArrayList claimsummarydtls = claimApplication.getClaimSummaryDtls();
			double appliedamnt = 0.0D;
			ClaimSummaryDtls dtls = null;
			if (claimsummarydtls != null) {
				for (int i = 0; i < claimsummarydtls.size(); i++) {
					dtls = (ClaimSummaryDtls) claimsummarydtls.get(i);
					cgpan = dtls.getCgpan();
					appliedamnt = dtls.getAmount();

					callableStmt = conn.prepareCall("{? = call funcUpdateClaimAmount(?,?,?,?)}");
					callableStmt.registerOutParameter(1, Types.INTEGER);
					callableStmt.setString(2, claimRefNumber);
					callableStmt.setString(3, cgpan);
					callableStmt.setDouble(4, appliedamnt);
					callableStmt.registerOutParameter(5, Types.VARCHAR);

					callableStmt.execute();
					status = callableStmt.getInt(1);
					errorCode = callableStmt.getString(5);
					if (status == 1) {
						Log.log(2, "CPDAO", "saveClaimApplication()",
								"SP returns a 1 in storing Claim Summary Details. Error code is :" + errorCode);
						callableStmt.close();
						try {
							conn.rollback();
						} catch (SQLException sqlex) {
							throw new DatabaseException(sqlex.getMessage());
						}
						throw new DatabaseException(errorCode);
					}
					callableStmt.close();
				}
			}

			NPADetails npaDtls = gmprocessor.getNPADetails(borrowerId);
			if (claimApplication.getFirstInstallment()) {
				updateNPADetails(npaDtls);
				updateLegalProceedingDetails(legalProceedingsDetail);
				if (map != null) {
					String borowrId = (String) map.get("BORROWERID");
					String itpanOfChiefPromoter = (String) map.get("Clm_ITPAN_of_Chief_Promoter");
					saveITPANDetail(borowrId, itpanOfChiefPromoter);
				}
			}
			if (claimApplication.getSecondInstallment()) {
				if (npaDtls != null) {
					npaDtls.setWillfulDefaulter(whethereWillFulDfaulter);
					npaDtls.setDtOfConclusionOfRecProc(dtOfConclusionOfRecProc);
					npaDtls.setWhetherWrittenOff(whetherAccntWrittenOffBooks);
					npaDtls.setDtOnWhichAccntWrittenOff(dtOfWrittenOffBooks);

					updateNPADetails(npaDtls);

					updateLegalProceedingDetails(legalProceedingsDetail);
				}
			}
			//commented by AbhishekSikarwar
			//updateRecallAndLegalAttachments(claimApplication, claimRefNumber, flag);

			Log.log(4, "CPDAO", "saveClaimApplication()",
					"Done with saving Claim Summary Details of Claim Application");
			// conn.commit();

		} catch (DatabaseException sqlexception) {
			hasExceptionOccured = true;
			Log.log(4, "CPDAO", "saveClaimApplication()", "Error in saving the claim application.");
			try {
				callableStmt.close();
				conn.rollback();
			} catch (SQLException sqlex) {
				throw new DatabaseException(sqlex.getMessage());
			}
			throw new DatabaseException(sqlexception.getMessage());
		} catch (SQLException sqlexception) {
			hasExceptionOccured = true;
			Log.log(4, "CPDAO", "saveClaimApplication()", "Error in saving the claim application.");
			try {
				callableStmt.close();
				conn.rollback();
			} catch (SQLException sqlex) {
				throw new DatabaseException(sqlex.getMessage());
			}
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(4, "CPDAO", "saveClaimApplication()", "Exited!");
	}
	// rajuk

	public Vector getSancDetails(String borrowerid) throws DatabaseException {
		Log.log(Log.INFO, "CPDAO", "getLockInDetails()", "Entered!");
		CallableStatement callableStmt = null;
		Connection conn = null;
		ResultSet resultset = null;
		Vector sancVect = new Vector();

		int status = -1;
		String errorCode = null;

		try {
			conn = DBConnection.getConnection();
			callableStmt = conn.prepareCall("{? = call packGetDtlsforLock.funcGetSanctDetails(?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt.setString(2, borrowerid);
			callableStmt.registerOutParameter(3, Constants.CURSOR);
			callableStmt.registerOutParameter(4, java.sql.Types.VARCHAR);
			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(4);

			if (status == Constants.FUNCTION_FAILURE) {
				Log.log(Log.ERROR, "CPDAO", "getLockInDetails()",
						"SP returns a 1 in getting Lock-in details for a borrower.Error code is :" + errorCode);
				callableStmt.close();
				throw new DatabaseException(errorCode);
			} else if (status == Constants.FUNCTION_SUCCESS) {
				// Reading the cursor containing details from the main db
				resultset = (ResultSet) callableStmt.getObject(3);

				String cgpan = null;
				java.util.Date sancDate = null;
				java.util.Date conditionDt = null;
				// HashMap sancMap=new HashMap();

				double approvdAmount = 0.0;
				String finalDisbursementFlag = null;
				HashMap sancMap = null;

				// Reading the resultset
				while (resultset.next()) {
					sancMap = new HashMap();
					cgpan = resultset.getString(1);
					sancDate = resultset.getDate(2);
					approvdAmount = resultset.getDouble(3);
					conditionDt = resultset.getDate(4);
					sancMap.put("cgpan", cgpan);
					sancMap.put("sancDt", sancDate);
					sancMap.put("apprvdAmt", approvdAmount);
					sancMap.put("condnDate", conditionDt);
					if (!sancVect.contains(sancMap)) {
						sancVect.addElement(sancMap);
					}

					// if(!(sancVect.contains(sancMap)))
					// {
					// sancVect.addElement(sancMap);}
					// finalDisbursementFlag = resultset.getString(4);
				}
			}

		} catch (SQLException sqlexception) {
			Log.log(Log.ERROR, "CPDAO", "getAllClaimsFiled()",
					"Error retrieving all Claims Filed so far from the database.");
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(Log.INFO, "CPDAO", "getAllClaimsFiled()", "Exited!");
		return sancVect;
	}

	/* Added for GST */
	public ArrayList<MLIInfo> getGSTStateList(String bankId) {
		Connection connection = DBConnection.getConnection();

		ArrayList<MLIInfo> statesList = new ArrayList<MLIInfo>();
		if (bankId != null || !bankId.equals("")) {
			Log.log(Log.INFO, "RegistationDAO", "getStateList", "Entered");
			/*
			 * MLIInfo mliinfo1=new MLIInfo(); mliinfo1.setGstNo("");
			 * mliinfo1.setState("Please Select State of Bank Id"); statesList.add(0,
			 * mliinfo1);
			 */
			PreparedStatement pStmt = null;
			ResultSet rsSet = null;
			try {
				String query = "select  gst.STE_CODE as state_code, case when HEAD_OFFICE ='Y' then sm.STE_NAME||' (HEAD OFFICE)' else sm.STE_NAME end as state_name,gst.GST_NO as gstNo  from MEMBER_BANK_STATE_GST gst, state_master sm where gst.ste_code = sm.ste_code AND GST.MEM_BNK_ID=?";
				pStmt = connection.prepareStatement(query);
				pStmt.setString(1, bankId);
				rsSet = pStmt.executeQuery();
				while (rsSet.next()) {
					MLIInfo mliinfo = new MLIInfo();

					mliinfo.setStateCode(rsSet.getString(1));
					mliinfo.setStateName(rsSet.getString(2));
					mliinfo.setGstNo(rsSet.getString(3));

					statesList.add(mliinfo);
				}
				rsSet.close();
				pStmt.close();
			} catch (Exception exception) {
				Log.logException(exception);
				try {
					throw new DatabaseException(exception.getMessage());
				} catch (DatabaseException e) {
					e.printStackTrace();
				}
			} finally {
				DBConnection.freeConnection(connection);
			}
		}
		return statesList;

	}

}
