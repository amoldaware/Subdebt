//Source file: C:\\CGTSI\\Vss\\com\\cgtsi\\common\\CommonDAO.java

package com.cgtsi.common;

import com.cgtsi.admin.User;
import java.util.ArrayList;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.sql.SQLException;
import com.cgtsi.util.DBConnection;

/**
 * This is an Data base access object which abstracts the commom module database
 * access.This object's primary responsibility is ,all the database calls are
 * intercepted by this call and converted into SQL scripts.
 */
public class CommonDAO {

	/**
	 * @roseuid 39B875CD035B
	 */
	public CommonDAO() {

	}

	/**
	 * This method is used to update the audit remarks entered by the Auditor.
	 * 
	 * @param userID
	 * @param remarks
	 * @throws DatabaseException
	 * @roseuid 3971A9860367
	 */
	public void enterAuditDetails(String CGPAN, String userID, String remarks) throws DatabaseException, SQLException {
		Connection connection = DBConnection.getConnection();
		try {
			CallableStatement auditDtls = connection.prepareCall("{?=call funcInsertAuditDetails(?,?,?,?)}");
			auditDtls.registerOutParameter(1, Types.INTEGER);
			auditDtls.registerOutParameter(5, Types.VARCHAR);

			auditDtls.setString(2, remarks);
			auditDtls.setString(3, CGPAN);
			auditDtls.setString(4, userID);

			auditDtls.execute();

			int functionReturn = auditDtls.getInt(1);

			String error = auditDtls.getString(5);

			Log.log(Log.ERROR, "CommonDAO", "enterAuditDetails",
					"Error code and errors are: " + functionReturn + " " + error);

			if (functionReturn == Constants.FUNCTION_FAILURE) {

				auditDtls.close();

				auditDtls = null;

				throw new DatabaseException(error);
			}
			auditDtls.close();
			auditDtls = null;

		} catch (SQLException e) {

			Log.log(Log.ERROR, "commonDAO", "enterAuditDetails", e.getMessage());

			Log.logException(e);

			throw new DatabaseException("Unable to enter Audit Details ");
		} finally {
			DBConnection.freeConnection(connection);
		}

	}

	/**
	 * This method is used to get all the audited details to the purview of the
	 * CGTSI user.
	 * 
	 * @return ArrayList
	 * @throws DatabaseException
	 * @roseuid 3971B1CA01C2
	 */
	public AuditDetails viewAuditDetails(AuditDetails auditDetails) throws DatabaseException {
		AuditDetails returnReviewAuditDtls = new AuditDetails();

		Connection connection = DBConnection.getConnection();
		try {
			CallableStatement viewAuditDtls = connection
					.prepareCall("{?=call packGetUpdateAuditDetails.funcGetAuditDetails(?,?,?,?)}");
			viewAuditDtls.registerOutParameter(1, Types.INTEGER);
			viewAuditDtls.registerOutParameter(5, Types.VARCHAR);
			viewAuditDtls.registerOutParameter(3, Types.INTEGER);// Audit Id
			viewAuditDtls.registerOutParameter(4, Types.VARCHAR);// Audit Remarks

			viewAuditDtls.setInt(2, auditDetails.getAuditId());

			viewAuditDtls.execute();

			int functionReturn = viewAuditDtls.getInt(1);

			String error = viewAuditDtls.getString(5);

			Log.log(Log.ERROR, "CommonDAO", "viewAuditDetails",
					"Error code and errors are: " + functionReturn + " " + error);

			if (functionReturn == Constants.FUNCTION_FAILURE) {

				viewAuditDtls.close();

				viewAuditDtls = null;

				throw new DatabaseException(error);
			} else {
				returnReviewAuditDtls.setMessage(viewAuditDtls.getString(4));
				returnReviewAuditDtls.setAuditId(viewAuditDtls.getInt(3));
			}

			viewAuditDtls.close();
			viewAuditDtls = null;

		} catch (SQLException e) {
			Log.log(Log.ERROR, "commonDAO", "viewAuditDetails", e.getMessage());

			Log.logException(e);

			throw new DatabaseException("Unable to retrieve Audit Details ");
		} finally {
			DBConnection.freeConnection(connection);
		}
		return returnReviewAuditDtls;
	}

	/**
	 * This method is used to get the audit details on press of the previous button
	 * on the screen. 24 NOV 2003
	 * 
	 * @throws DatabaseException added by Ramesh rp14480
	 */
	public AuditDetails viewRevAuditDetails(AuditDetails auditDetails) throws DatabaseException {
		AuditDetails returnReviewAuditDtls = new AuditDetails();

		Connection connection = DBConnection.getConnection();
		try {
			CallableStatement viewAuditDtls = connection
					.prepareCall("{?=call packGetUpdateAuditDetails.funcGetRevAuditDetails(?,?,?,?)}");
			viewAuditDtls.registerOutParameter(1, Types.INTEGER);
			viewAuditDtls.registerOutParameter(5, Types.VARCHAR);
			viewAuditDtls.registerOutParameter(3, Types.INTEGER);// Audit Id
			viewAuditDtls.registerOutParameter(4, Types.VARCHAR);// Audit Remarks

			viewAuditDtls.setInt(2, auditDetails.getAuditId());

			viewAuditDtls.execute();

			int functionReturn = viewAuditDtls.getInt(1);

			String error = viewAuditDtls.getString(5);

			Log.log(Log.ERROR, "CommonDAO", "viewRevAuditDetails",
					"Error code and errors are: " + functionReturn + " " + error);

			if (functionReturn == Constants.FUNCTION_FAILURE) {

				viewAuditDtls.close();

				viewAuditDtls = null;

				throw new DatabaseException(error);
			} else {
				returnReviewAuditDtls.setMessage(viewAuditDtls.getString(4));
				returnReviewAuditDtls.setAuditId(viewAuditDtls.getInt(3));
			}

			viewAuditDtls.close();
			viewAuditDtls = null;

		} catch (SQLException e) {
			Log.log(Log.ERROR, "commonDAO", "viewRev(Reviewed)AuditDetails", e.getMessage());

			Log.logException(e);

			throw new DatabaseException("Unable to retrieve Audit Details ");
		} finally {
			DBConnection.freeConnection(connection);
		}
		return returnReviewAuditDtls;
	}

	/**
	 * This method is used to get the auditor comments for the CGPAN entered. user.
	 * 
	 * @return ArrayList
	 * @throws DatabaseException
	 * @roseuid 3971B1CA01C2
	 */
	public AuditDetails getAuditForCgpan(String CGPAN) throws DatabaseException {

		AuditDetails returnAuditDtls = new AuditDetails();

		Connection connection = DBConnection.getConnection();
		try {
			CallableStatement viewAuditDtls = connection
					.prepareCall("{?=call packGetUpdateAuditDetails.funcGetAuditDtlCGPAN(?,?,?,?)}");
			viewAuditDtls.registerOutParameter(1, Types.INTEGER);

			viewAuditDtls.registerOutParameter(3, Types.INTEGER);// Audit Id

			viewAuditDtls.registerOutParameter(4, Types.VARCHAR);// Audit remarks

			viewAuditDtls.registerOutParameter(5, Types.VARCHAR);

			viewAuditDtls.setString(2, CGPAN);

			viewAuditDtls.execute();

			int functionReturn = viewAuditDtls.getInt(1);

			String error = viewAuditDtls.getString(5);

			Log.log(Log.ERROR, "CommonDAO", "getAuditForCgpan",
					"Error code and errors are: " + functionReturn + " " + error);

			if (functionReturn == Constants.FUNCTION_FAILURE) {

				viewAuditDtls.close();

				viewAuditDtls = null;

				throw new DatabaseException(error);
			} else {
				returnAuditDtls.setAuditId(viewAuditDtls.getInt(3));
				returnAuditDtls.setMessage(viewAuditDtls.getString(4));
			}

			viewAuditDtls.close();
			viewAuditDtls = null;

		} catch (SQLException e) {
			Log.log(Log.ERROR, "commonDAO", "getAuditForCgpan", e.getMessage());

			Log.logException(e);

			throw new DatabaseException("Unable to get Audit Details for the CGPAN ");
		} finally {
			DBConnection.freeConnection(connection);
		}
		return returnAuditDtls;
	}

	/**
	 * This method is used to update the number of unsuccessfull attempts for a user
	 * id in the database
	 * 
	 * @param userid
	 * @return boolean
	 * @throws DatabaseException
	 * @roseuid 3972C249000E
	 */
	public void updateUnsuccessfullAttempts(String userId) throws DatabaseException {
		Log.log(Log.INFO, "CommonDAO", "updateUnsuccessfullAttempts", "Entered");
		Connection connection = DBConnection.getConnection();

		try {
			CallableStatement callable = connection.prepareCall("{call FUNCUPDATEUNSUCCESSLOGINS(?,?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);
			callable.setString(2, userId);
			callable.registerOutParameter(3, Types.VARCHAR);
			callable.executeUpdate();

			int errorCode = callable.getInt(1);
			String error = callable.getString(3);

			Log.log(Log.DEBUG, "CommonDAO", "updateUnsuccessfullAttempts",
					"errorCode,error are " + errorCode + " " + error);

			if (errorCode == Constants.FUNCTION_FAILURE) {
				Log.log(Log.ERROR, "CommonDAO", "updateUnsuccessfullAttempts",
						"Unable to Update Unsuccessfull Attempts");

				throw new DatabaseException("Unable to update Unsuccefull Attempts");
			}
		} catch (SQLException e) {
			Log.log(Log.ERROR, "CommonDAO", "updateUnsuccessfullAttempts", e.getMessage());
			Log.logException(e);

			throw new DatabaseException(e.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO, "CommonDAO", "updateUnsuccessfullAttempts", "Exited");
	}

	/**
	 * 
	 * added this method for uploading the online applications
	 * 
	 * @param serverPath
	 * @param fileName
	 * @throws com.cgtsi.common.DatabaseException
	 */
	public void uploadFileApplications(String serverPath, String fileName) throws DatabaseException {
		Log.log(Log.INFO, "CommonDAO", "uploadFileApplications", "Entered");
		System.out.println("CommonDAO uploadFileApplications Entered");
		Connection connection = DBConnection.getConnection();

		try {
			CallableStatement callable = connection.prepareCall("{?=call FuncInterfaceUpload(?,?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);
			callable.setString(2, serverPath);
			callable.setString(3, fileName);
			callable.registerOutParameter(4, Types.VARCHAR);
			callable.executeUpdate();
			System.out.println("Server Path:" + serverPath);
			System.out.println("File Name:" + fileName);
			int errorCode = callable.getInt(1);
			String error = callable.getString(4);

			Log.log(Log.DEBUG, "CommonDAO", "uploadFileApplications", "errorCode,error are " + errorCode + " " + error);

			if (errorCode == Constants.FUNCTION_FAILURE) {

				Log.log(Log.ERROR, "CommonDAO", "uploadFileApplications", "Unable to Upload Applications");

				throw new DatabaseException("Unable to Upload the file application");
			}
		} catch (SQLException e) {

			Log.log(Log.ERROR, "CommonDAO", "uploadFileApplications", e.getMessage());
			Log.logException(e);
			throw new DatabaseException(e.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO, "CommonDAO", "uploadFileApplications", "Exited");

	}

	/**
	 * This method is used to update the CGTSI user comments for the auditor's
	 * remarks.
	 * 
	 * @param userID
	 * @param reviewCmts -
	 *
	 * @throws DatabaseException
	 * @roseuid 3972E59802E7
	 */
	public void updateAuditReview(String CGPAN, int auditId, String userID, String reviewCmts)
			throws DatabaseException {
		Log.log(Log.ERROR, "commonDAO", "updateAuditReview", "Audit Id :" + auditId);
		Log.log(Log.ERROR, "commonDAO", "updateAuditReview", "userID :" + userID);
		Log.log(Log.ERROR, "commonDAO", "updateAuditReview", "reviewCmts :" + reviewCmts);
		Connection connection = DBConnection.getConnection();
		try {
			if (CGPAN.equals("") || (CGPAN == null)) {

				CallableStatement updateAuditDtls = connection
						.prepareCall("{?=call packGetUpdateAuditDetails.funcUpdateAuditDetails(?,?,?,?)}");
				updateAuditDtls.registerOutParameter(1, Types.INTEGER);
				updateAuditDtls.registerOutParameter(5, Types.VARCHAR);

				updateAuditDtls.setInt(2, auditId);
				updateAuditDtls.setString(3, userID);
				updateAuditDtls.setString(4, reviewCmts);

				updateAuditDtls.execute();

				int functionReturn = updateAuditDtls.getInt(1);

				String error = updateAuditDtls.getString(5);

				Log.log(Log.ERROR, "CommonDAO", "updateAuditReview",
						"Error code and errors are: " + functionReturn + " " + error);

				if (functionReturn == Constants.FUNCTION_FAILURE) {

					updateAuditDtls.close();

					updateAuditDtls = null;

					throw new DatabaseException(error);
				}

				updateAuditDtls.close();
				updateAuditDtls = null;
			} else {
				CallableStatement updateAuditDtls = connection
						.prepareCall("{?=call packGetUpdateAuditDetails.funcUpdAuditDtlCGPAN(?,?,?,?)}");
				updateAuditDtls.registerOutParameter(1, Types.INTEGER);
				updateAuditDtls.registerOutParameter(5, Types.VARCHAR);

				updateAuditDtls.setString(2, CGPAN);
				updateAuditDtls.setString(3, userID);
				updateAuditDtls.setString(4, reviewCmts);

				updateAuditDtls.execute();

				int functionReturn = updateAuditDtls.getInt(1);

				String error = updateAuditDtls.getString(5);

				Log.log(Log.ERROR, "CommonDAO", "updateAuditReview",
						"Error code and errors are: " + functionReturn + " " + error);

				if (functionReturn == Constants.FUNCTION_FAILURE) {

					updateAuditDtls.close();

					updateAuditDtls = null;

					throw new DatabaseException("CGPAN could not be found in the System. Details could not be saved.");
				}

				updateAuditDtls.close();
				updateAuditDtls = null;
			}
		} catch (SQLException e) {
			Log.log(Log.ERROR, "commonDAO", "updateAuditReview", e.getMessage());

			Log.logException(e);

			throw new DatabaseException("Unable to to update the CGTSI user comments for the auditor's remarks");
		} finally {
			DBConnection.freeConnection(connection);
		}

	}

	/**
	 * This method is used to get all the users who are not paid their guarantee fee
	 * eventhough CGDAN is generated.
	 * 
	 * @return ArrayList
	 * @throws DatabaseException
	 * @roseuid 3973E4D803A5
	 */
	public ArrayList getAllGFDefaultList() throws DatabaseException {
		ArrayList defaulters = new ArrayList();
		User user1 = new User();
		defaulters.add(user1);
		return defaulters;
	}

	/**
	 * This method is used to insert alert messages for a given reason. Alert
	 * messages can be given for guarantee fee, periodic info, service fee etc.
	 * 
	 * @param userId
	 * @param message
	 * @param messageType - Type of message. 0-Guarantee Fee 1-Service Fee Alert
	 *                    2-Periodic info
	 * @return boolean
	 * @throws DatabaseException
	 * @roseuid 397405A100FA
	 */
	public boolean insertMessageForUser(String userId, String message, int messageType) throws DatabaseException {
		return false;
	}

	/**
	 * This method returns the users who are not entered the outstanding details .
	 * 
	 * @return ArrayList
	 * @throws DatabaseException
	 * @roseuid 3974070600C2
	 */
	public ArrayList getAllOsDefaulters() throws DatabaseException {
		ArrayList defaulters = new ArrayList();
		User user2 = new User();
		defaulters.add(user2);
		return defaulters;
	}

	/**
	 * This method is used to get all the users who are not entered their periodic
	 * info details.
	 * 
	 * @return ArrayList
	 * @throws DatabaseException
	 * @roseuid 39741265012C
	 */
	public ArrayList getPeriodicInfoDefaulters() throws DatabaseException {
		ArrayList defaulters = new ArrayList();
		User user2 = new User();
		defaulters.add(user2);
		return defaulters;
	}

	/**
	 * This method is used to get all the industries available in the database.
	 * 
	 * @return ArrayList
	 * @throws DatabaseException
	 * @roseuid 398296E801FE
	 */
	public ArrayList getAllIndustries() throws DatabaseException {
		return null;
	}

	/**
	 * This method is used to get all the zones available from the ZONE master.
	 * 
	 * @return ArrayList
	 * @throws DatabaseException
	 * @roseuid 39A651C4039A
	 */
	public ArrayList getAllZones() throws DatabaseException {
		return null;
	}

	/**
	 * This method is used to get all the regions available in the Region master.
	 * 
	 * @return ArrayList
	 * @throws DatabaseException
	 * @roseuid 39A651DC027C
	 */
	public ArrayList getRegions() throws DatabaseException {
		return null;
	}

	public void updateLastLoginDate(String userId) throws DatabaseException {
		Log.log(Log.INFO, "CommonDAO", "updateLastLoginDate", "Entered");
		Connection connection = DBConnection.getConnection();
		try {
			CallableStatement callable = connection.prepareCall("{call FUNCUPDATELASTLOGINDATE(?,?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);

			callable.setString(2, userId);

			callable.registerOutParameter(3, Types.VARCHAR);
			callable.executeUpdate();

			int errorCode = callable.getInt(1);
			String error = callable.getString(3);

			Log.log(Log.ERROR, "CommonDAO", "updateLastLoginDate",
					"Error code and errors are: " + errorCode + " " + error);

			if (errorCode == Constants.FUNCTION_FAILURE) {
				throw new DatabaseException(error);
			}
		} catch (SQLException e) {
			Log.log(Log.ERROR, "CommonDAO", "updateLastLoginDate", e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Failed to update Last Login Date");
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(Log.INFO, "CommonDAO", "updateLastLoginDate", "Exited");
	}

	// Developed by Bhuwneshwar.Singh@Path in DANreportDetails

	String string;
	String st1[] = { "", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", };
	String st2[] = { "Hundred", "Thousand", "Lakh", "Crore" };
	String st3[] = { "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen",
			"Ninteen", };
	String st4[] = { "Twenty", "Thirty", "Fourty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninty" };

	public String inWordFormat(double numbe) {

		int number = (int) numbe;
		int n = 1;
		int word;
		string = "";
		while (number != 0) {
			switch (n) {
			case 1:
				word = number % 100;
				pass(word);
				if (number > 100 && number % 100 != 0) {
					show("and ");
				}
				number /= 100;
				break;

			case 2:
				word = number % 10;
				if (word != 0) {
					show(" ");
					show(st2[0]);
					show(" ");
					pass(word);
				}
				number /= 10;
				break;

			case 3:
				word = number % 100;
				if (word != 0) {
					show(" ");
					show(st2[1]);
					show(" ");
					pass(word);
				}
				number /= 100;
				break;

			case 4:
				word = number % 100;
				if (word != 0) {
					show(" ");
					show(st2[2]);
					show(" ");
					pass(word);
				}
				number /= 100;
				break;

			case 5:
				word = number % 100;
				if (word != 0) {
					show(" ");
					show(st2[3]);
					show(" ");
					pass(word);
				}
				number /= 100;
				break;

			}
			n++;
		}
		return string;
	}

	public void pass(int number) {
		int word, q;
		if (number < 10) {
			show(st1[number]);
		}
		if (number > 9 && number < 20) {
			show(st3[number - 10]);
		}
		if (number > 19) {
			word = number % 10;
			if (word == 0) {
				q = number / 10;
				show(st4[q - 2]);
			} else {
				q = number / 10;
				show(st1[word]);
				show(" ");
				show(st4[q - 2]);
			}
		}
	}

	public void show(String s) {
		String st;
		st = string;
		string = s;
		string += st;
	}

}