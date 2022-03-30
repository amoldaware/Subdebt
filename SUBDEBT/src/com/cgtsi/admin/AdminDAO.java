//Source file: C:\\CGTSI\\Vss\\com\\cgtsi\\admin\\AdminDAO.java

package com.cgtsi.admin;

import com.cgtsi.actionform.AdministrationActionForm;
import com.cgtsi.application.LogClass;
import com.cgtsi.common.Constants;
import com.cgtsi.common.DatabaseException;
import com.cgtsi.common.MessageException;
import com.cgtsi.common.Log;
import com.cgtsi.common.NoDataException;
import com.cgtsi.common.NoUserFoundException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.sql.Connection;
import java.sql.CallableStatement;

import com.cgtsi.registration.MLIInfo;
import com.cgtsi.util.CustomisedDate;
import com.cgtsi.util.DBConnection;
import com.cgtsi.util.DateHelper;

import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.Types;
import java.sql.SQLException;
import java.sql.ResultSet;

import javax.mail.Session;
import javax.mail.Transport;

/**
 * @author RP14480
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
/**
 * @author RP14480
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
/**
 * @author RP14480
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
/**
 * This class responsible for accessing data base.Database related operations
 * would be encapsulated in this class
 */
public class AdminDAO {

	/**
	 * @roseuid 39B875C502B9
	 */
	public AdminDAO() {

	}

	/**
	 * To update the industry master details into the data base
	 * 
	 * @param industryMaster
	 * @throws DatabaseException
	 * @roseuid 3972AB8B00A3
	 */
	public void updateIndustryMaster(ParameterMaster industryMaster) throws DatabaseException {
		if (1 == 2) {
			throw new DatabaseException();
		}
	}

	/**
	 * Whenever a user to be added to the data base The user information are stored
	 * into database
	 *
	 * @param user
	 * @return int
	 * @throws DatabaseException
	 * @roseuid 3972AB8B00A5
	 */
	public String addUser(String createdBy, User user) throws DatabaseException {
		Connection connection = DBConnection.getConnection(false);

		String userId = null;

		try {
			CallableStatement callable = connection
					.prepareCall("{call funcInsertUserDetail(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			// values are retrieved from the user object and set in the DataBase.

			callable.setString(2, user.getFirstName());

			callable.setString(3, user.getMiddleName());

			callable.setString(4, user.getLastName());

			callable.setString(5, user.getDesignation());

			callable.setString(6, user.getBankId());

			callable.setString(7, user.getZoneId());

			callable.setString(8, user.getBranchId());

			callable.setString(9, user.getEmailId());

			callable.setString(10, user.getPassword());

			callable.setString(11, createdBy);

			callable.setString(12, "ACTIVE_USER_LIMIT");

			// The error status,userId and error message is returned from the database.

			callable.registerOutParameter(1, Types.INTEGER);

			callable.registerOutParameter(13, Types.VARCHAR);// userID

			callable.registerOutParameter(14, Types.VARCHAR);// error code
			callable.setString(15, user.getMobileNo());

			callable.execute();

			userId = callable.getString(13);

			int functionReturn = callable.getInt(1);

			String error = callable.getString(14);

			if (functionReturn == Constants.FUNCTION_FAILURE) {

				connection.rollback();

				callable.close();
				callable = null;

				Log.log(Log.ERROR, "AdminDAO", "add User", error);

				throw new DatabaseException(error);
			}
			callable.close();
			callable = null;
			connection.commit();

		} catch (SQLException e) {

			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "add User", ignore.getMessage());
			}

			Log.log(Log.ERROR, "AdminDAO", "add User", e.getMessage());

			Log.logException(e);

			throw new DatabaseException("Unable to add user");

		} finally {

			DBConnection.freeConnection(connection);
		}
		return userId;
	}

	/**
	 * Whenever a user to be activated this method is invoked to update the user
	 * status in the database.
	 * 
	 * @param userID
	 * @throws DatabaseException
	 * @roseuid 3972AB8B00A7
	 */
	public void reactivateUser(String userID, String message, String reactivatedBy) throws DatabaseException {

		Connection connection = DBConnection.getConnection(false);

		try {
			CallableStatement callable = connection.prepareCall("{call funcUpdateUserStatus(?,?,?,?,?,?)}");

			callable.setString(2, userID);

			callable.setString(3, AdminConstants.ACTIVE_FLAG);

			callable.setString(4, message);

			callable.setString(5, reactivatedBy);

			callable.registerOutParameter(1, Types.INTEGER);

			callable.registerOutParameter(6, Types.VARCHAR);

			callable.execute();

			int functionReturn = callable.getInt(1);

			String error = callable.getString(6);

			if (functionReturn == Constants.FUNCTION_FAILURE) {

				connection.rollback();

				callable.close();

				callable = null;

				Log.log(Log.ERROR, "AdminDAO", "reactivate User", error);

				throw new DatabaseException(error);
			}

			callable.close();

			callable = null;

			connection.commit();

		} catch (SQLException e) {

			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "reactivate User", ignore.getMessage());
			}

			Log.log(Log.ERROR, "AdminDAO", "reactivate User", e.getMessage());

			Log.logException(e);

			throw new DatabaseException("Unable to reactivate user");

		} finally {

			DBConnection.freeConnection(connection);
		}
	}

	/**
	 * Whenever a user to be deactivated, this method is used to update the status
	 * of the user in the database.
	 * 
	 * @param userID
	 * @throws DatabaseException
	 * @roseuid 3972AB8B00A9
	 */
	public void deactivateUser(String userID, String message, String deactivatedBy) throws DatabaseException {
		Connection connection = DBConnection.getConnection(false);

		try {
			CallableStatement callable = connection.prepareCall("{call funcUpdateUserStatus(?,?,?,?,?,?)}");

			callable.setString(2, userID);

			callable.setString(3, AdminConstants.INACTIVE_FLAG);

			callable.setString(4, message);

			callable.setString(5, deactivatedBy);

			callable.registerOutParameter(1, Types.INTEGER);

			callable.registerOutParameter(6, Types.VARCHAR);

			callable.execute();

			int functionReturn = callable.getInt(1);

			String error = callable.getString(6);

			if (functionReturn == Constants.FUNCTION_FAILURE) {

				connection.rollback();

				callable.close();

				callable = null;

				throw new DatabaseException(error);

			}

			callable.close();

			callable = null;

			connection.commit();

		} catch (SQLException e) {

			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "deactivateUser", ignore.getMessage());
			}

			Log.log(Log.ERROR, "AdminDAO", "deactivateUser", e.getMessage());

			Log.logException(e);

			throw new DatabaseException("Unable to deactivate user");

		} finally {

			DBConnection.freeConnection(connection);

		}

	}

	/**
	 * This method would be called whenever there is change in password. Either by
	 * optionally selecting change password option or by forcibly asked to change
	 * the password.
	 * 
	 * @param password
	 * @param userId
	 * @return boolean
	 * @throws DatabaseException
	 * @roseuid 3972AB8B00AB
	 */
	public boolean updatePassword(String password, String userId, String emailId) throws DatabaseException {
		Connection connection = null;
		try {
			Log.log(Log.INFO, "AdminDAO", "updatePassword", "Entered");
			connection = DBConnection.getConnection(false);

			Log.log(Log.DEBUG, "AdminDAO", "updatePassword", "user id and passwords are " + userId + " " + password);

			CallableStatement callable = connection.prepareCall("{call FUNCCHANGEPWD(?,?,?,?,?)}");
			callable.registerOutParameter(1, Types.INTEGER);
			callable.registerOutParameter(5, Types.VARCHAR);

			callable.setString(2, userId);
			callable.setString(3, password);
			callable.setString(4, emailId);

			boolean updated = callable.execute();

			int result = callable.getInt(1);

			if (result == Constants.FUNCTION_FAILURE) {
				connection.rollback();

				callable.close();

				callable = null;

				String error = callable.getString(5);

				Log.log(Log.ERROR, "AdminDAO", "updatePassword", error);

				throw new DatabaseException(error);
			}
			callable.close();
			callable = null;
			connection.commit();
		} catch (SQLException e) {

			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "updatePassword", ignore.getMessage());
			}
			Log.log(Log.ERROR, "AdminDAO", "updatePassword", e.getMessage());
			Log.logException(e);
			throw new DatabaseException("Unable to update password");
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(Log.INFO, "AdminDAO", "updatePassword", "Exited");
		return true;
	}

	/**
	 * When a user wants to change his hint question or answer
	 * 
	 * @param hint
	 * @param answer
	 * @param userId
	 * @return boolean
	 * @throws DatabaseException
	 * @roseuid 3972AB8B00AD
	 */
	public boolean updateHint(String hint, String answer, String userId) throws DatabaseException {

		Connection connection = DBConnection.getConnection(false);

		try {
			Log.log(Log.INFO, "AdminDAO", "updateHint", "Entered");

			CallableStatement callable = connection.prepareCall("{call funcUpdateHintQA(?,?,?,?,?)}");

			// the new hint question and answer for the user Id is passed to the database.

			callable.setString(2, hint);

			callable.setString(3, answer);

			callable.setString(4, userId);

			// The error status and error message are retrieved from the database.

			callable.registerOutParameter(1, Types.INTEGER);

			callable.registerOutParameter(5, Types.VARCHAR);

			callable.execute();

			// If error status is 1 throw database exception.

			int functionReturn = callable.getInt(1);

			String error = callable.getString(5);

			if (functionReturn == Constants.FUNCTION_FAILURE) {

				connection.rollback();

				callable.close();

				callable = null;

				throw new DatabaseException(error);

			}
			callable.close();
			callable = null;
			connection.commit();

		} catch (SQLException e) {

			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "updateHint", ignore.getMessage());
			}

			Log.log(Log.ERROR, "AdminDAO", "updateHint", e.getMessage());

			Log.logException(e);

			throw new DatabaseException("Unable to update hint");

		} finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO, "AdminDAO", "updateHint", "Exited");
		return true;

	}

	/**
	 * To return the user information details pertaining to the user id passed.If
	 * user details are not found, this method would throw NoUserFoundException.
	 * 
	 * @param userId
	 * @return User
	 * @throws DatabaseException
	 * @roseuid 3972AD080245
	 */
	public User getUserInfo(String memberId, String userId) throws DatabaseException {
		Connection connection = DBConnection.getConnection(false);

		User user = null;
		String bankId = memberId.substring(0, 4);
		String zoneId = memberId.substring(4, 8);
		String branchId = memberId.substring(8, 12);

		try {
			Log.log(Log.INFO, "AdminDAO", "getUserInfo", "Entered");

			CallableStatement callable = connection
					.prepareCall("{call funcGetUserDetail(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			callable.setString(2, userId);

			callable.registerOutParameter(1, Types.INTEGER); // error status
			callable.registerOutParameter(3, Types.VARCHAR); // firstname
			callable.registerOutParameter(4, Types.VARCHAR); // middle name
			callable.registerOutParameter(5, Types.VARCHAR); // last name
			callable.registerOutParameter(6, Types.VARCHAR); // bank id
			callable.registerOutParameter(7, Types.VARCHAR); // zone id
			callable.registerOutParameter(8, Types.VARCHAR); // branch id
			callable.registerOutParameter(9, Types.VARCHAR); // designation
			callable.registerOutParameter(10, Types.VARCHAR); // emailid
			callable.registerOutParameter(11, Types.VARCHAR); // password
			callable.registerOutParameter(12, Types.VARCHAR); // Hint question
			callable.registerOutParameter(13, Types.VARCHAR); // Hint answer
			callable.registerOutParameter(14, Types.VARCHAR); // status
			callable.registerOutParameter(15, Types.DATE); // Password changed date
			callable.registerOutParameter(16, Types.VARCHAR); // first log in
			callable.registerOutParameter(17, Types.INTEGER); // Unsuccessfull attempts
			callable.registerOutParameter(18, Types.DATE); // Last logged in date

			callable.registerOutParameter(19, Types.VARCHAR); // error message

			callable.execute();

			int functionReturn = callable.getInt(1);

			String error = callable.getString(19);

			if (functionReturn == Constants.FUNCTION_FAILURE) {
				// try to get the user info if the entered user id is old.
				Log.log(Log.DEBUG, "AdminDAO", "getUserInfo",
						"Supplied user id not found in new user id. try in old user id.");

				/***************
				 * Start of function get old user details
				 **********************************/

				callable = connection
						.prepareCall("{call funcGetOldUserDetail(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

				callable.setString(2, userId);
				callable.setString(3, bankId);
				callable.setString(4, zoneId);
				callable.setString(5, branchId);

				callable.registerOutParameter(1, Types.INTEGER); // error status
				callable.registerOutParameter(6, Types.VARCHAR); // firstname
				callable.registerOutParameter(7, Types.VARCHAR); // middle name
				callable.registerOutParameter(8, Types.VARCHAR); // last name
				callable.registerOutParameter(9, Types.VARCHAR); // bank id
				callable.registerOutParameter(10, Types.VARCHAR); // zone id
				callable.registerOutParameter(11, Types.VARCHAR); // branch id
				callable.registerOutParameter(12, Types.VARCHAR); // designation
				callable.registerOutParameter(13, Types.VARCHAR); // emailid
				callable.registerOutParameter(14, Types.VARCHAR); // password
				callable.registerOutParameter(15, Types.VARCHAR); // Hint question
				callable.registerOutParameter(16, Types.VARCHAR); // Hint answer
				callable.registerOutParameter(17, Types.VARCHAR); // status
				callable.registerOutParameter(18, Types.DATE); // Password changed date
				callable.registerOutParameter(19, Types.VARCHAR); // first log in
				callable.registerOutParameter(20, Types.INTEGER); // Unsuccessfull attempts
				callable.registerOutParameter(21, Types.DATE); // Last logged in date
				callable.registerOutParameter(22, Types.VARCHAR); // new user id

				callable.registerOutParameter(23, Types.VARCHAR); // error message

				callable.execute();

				functionReturn = callable.getInt(1);

				error = callable.getString(23);

				if (functionReturn == Constants.FUNCTION_FAILURE) {
					connection.rollback();
					callable.close();
					callable = null;
					/*
					 * The following line is commented to fix FinalUATBugs-client bugs-1
					 */
					// throw new DatabaseException("Unable to get old user detail");
					return null;
					/*
					 * Fix Completed.
					 */
				} else {
					Log.log(Log.DEBUG, "AdminDAO", "getUserInfo", "Function executed without error");
					user = new User();
					// the values are retrieved from the database and
					// set into the user object.

					user.setFirstName(callable.getString(6));
					user.setMiddleName(callable.getString(7));
					user.setLastName(callable.getString(8));
					user.setBankId(callable.getString(9));
					user.setZoneId(callable.getString(10));
					user.setBranchId(callable.getString(11));
					user.setDesignation(callable.getString(12));
					user.setEmailId(callable.getString(13));

					user.setPassword(callable.getString(14));
					Hint hint = new Hint();
					hint.setHintQuestion(callable.getString(15));
					hint.setHintAnswer(callable.getString(16));

					user.setHint(hint);
					String userStatus = callable.getString(17);
					user.setUserId(callable.getString(22));

					if (userStatus.trim().equals(AdminConstants.ACTIVE_FLAG)) {

						Log.log(Log.DEBUG, "AdminDAO", "getUserInfo", "Active user");
						user.setStatus(AdminConstants.ACTIVE_USER_STATUS);
					} else {

						Log.log(Log.DEBUG, "AdminDAO", "getUserInfo", "InActive user");
						user.setStatus(AdminConstants.INACTIVE_USER_STATUS);
					}

					user.setPasswordChangedDate(callable.getDate(18));
					String firstLogin = callable.getString(19);

					if (firstLogin.trim().equals(Constants.YES)) {
						Log.log(Log.DEBUG, "AdminDAO", "getUserInfo", "first log in");
						user.setFirstLogin(true);
					} else {
						Log.log(Log.DEBUG, "AdminDAO", "getUserInfo", "not first log in");
						user.setFirstLogin(false);
					}

					user.setUnsuccessfulAttempts(callable.getInt(20));

					user.setLastLogin(callable.getDate(21));

				}

			}
			/********************************
			 * End of function get old user details
			 ******************/
			else {
				Log.log(Log.DEBUG, "AdminDAO", "getUserInfo", "Function executed without error");
				user = new User();
				// the values are retrieved from the database and
				// set into the user object.
				user.setUserId(userId);
				user.setFirstName(callable.getString(3));
				user.setMiddleName(callable.getString(4));
				user.setLastName(callable.getString(5));
				user.setBankId(callable.getString(6));
				user.setZoneId(callable.getString(7));
				user.setBranchId(callable.getString(8));
				user.setDesignation(callable.getString(9));
				user.setEmailId(callable.getString(10));

				user.setPassword(callable.getString(11));
				Hint hint = new Hint();
				hint.setHintQuestion(callable.getString(12));
				hint.setHintAnswer(callable.getString(13));

				user.setHint(hint);
				String userStatus = callable.getString(14);

				user.setPasswordChangedDate(callable.getDate(15));
				String firstLogin = callable.getString(16);

				if (userStatus.trim().equals(AdminConstants.ACTIVE_FLAG)) {

					Log.log(Log.DEBUG, "AdminDAO", "getUserInfo", "Active user");
					user.setStatus(AdminConstants.ACTIVE_USER_STATUS);
				} else {

					Log.log(Log.DEBUG, "AdminDAO", "getUserInfo", "InActive user");
					user.setStatus(AdminConstants.INACTIVE_USER_STATUS);
				}

				if (firstLogin.trim().equals(Constants.YES)) {
					Log.log(Log.DEBUG, "AdminDAO", "getUserInfo", "first log in");
					user.setFirstLogin(true);
				} else {
					Log.log(Log.DEBUG, "AdminDAO", "getUserInfo", "not first log in");
					user.setFirstLogin(false);
				}

				user.setUnsuccessfulAttempts(callable.getInt(17));

				user.setLastLogin(callable.getDate(18));

			}

			connection.commit();
			callable.close();
			callable = null;
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "getUserInfo", ignore.getMessage());
			}

			Log.log(Log.ERROR, "AdminDAO", "getUserInfo", e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Error while getting user details.");
		}

		finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(Log.INFO, "AdminDAO", "getUserInfo", "Exited");
		return user;
	}

	/**
	 * This method returns an ArrayList of all the privileges available in the
	 * database.
	 * 
	 * @return ArrayList
	 * @throws DatabaseException
	 * @roseuid 3972C5530374
	 */
	public ArrayList getAllPrivileges() throws DatabaseException {
		Log.log(Log.INFO, "AdminDAO", "getAllPrivileges", "Entered");
		Connection connection = DBConnection.getConnection(false);
		ArrayList privileges = new ArrayList();

		try {
			CallableStatement callable = connection
					.prepareCall("{?=call packGetAllPrivileges.funcGetAllPrivileges(?,?)}");

			callable.registerOutParameter(1, Types.INTEGER); // error status
			// The list of privileges is stored in the CURSOR.
			callable.registerOutParameter(2, Constants.CURSOR);
			callable.registerOutParameter(3, Types.VARCHAR); // error message

			callable.execute();

			int errorCode = callable.getInt(1);
			String error = callable.getString(3);

			Log.log(Log.DEBUG, "AdminDAO", "getAllPrivileges", "Error code and error are " + errorCode + " " + error);

			// If error status is 1 throw database exception.
			if (errorCode == Constants.FUNCTION_FAILURE) {
				connection.rollback();
				callable.close();
				callable = null;
				throw new DatabaseException("Unable to get Privileges");
			} else {
				// the privileges are retrieved into a result set object "result".
				ResultSet result = (ResultSet) callable.getObject(2);
				while (result.next()) { // The privileges are added into an arraylist.
					String privilege = result.getString(1);
					// Get the privilege description from the constants file.
					privileges.add(Privileges.getPrivilege(privilege));
				}

				result.close();
				result = null;
			}
			callable.close();
			callable = null;
			connection.commit();

		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "getAllPrivileges", ignore.getMessage());
			}

			Log.log(Log.ERROR, "AdminDAO", "getAllPrivileges", e.getMessage());
			Log.logException(e);
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(Log.INFO, "AdminDAO", "getAllPrivileges", "Exited");
		return privileges;
	}

	/* Added by sukumar@path for getting RSF Participating Banks */
	/**
	 * this function return all RSF participating bank codes
	 * 
	 * @return
	 * @throws com.cgtsi.common.DatabaseException
	 */
	public ArrayList getAllRsfParticipatingBanks() throws DatabaseException {
		Log.log(Log.INFO, "AdminDAO", "getAllRsfParticipatingBanks", "Entered");
		Connection connection = DBConnection.getConnection(false);
		ArrayList rsfBanks = new ArrayList();

		try {
			CallableStatement callable = connection.prepareCall("{?=call packGetAllRsfBanks.funcGetAllRsfBanks(?,?)}");

			callable.registerOutParameter(1, Types.INTEGER); // error status

			callable.registerOutParameter(2, Constants.CURSOR);
			callable.registerOutParameter(3, Types.VARCHAR); // error message

			callable.execute();

			int errorCode = callable.getInt(1);
			String error = callable.getString(3);

			Log.log(Log.DEBUG, "AdminDAO", "getAllRsfParticipatingBanks",
					"Error code and error are " + errorCode + " " + error);

			// If error status is 1 throw database exception.
			if (errorCode == Constants.FUNCTION_FAILURE) {
				connection.rollback();
				callable.close();
				callable = null;
				throw new DatabaseException("Unable to get RSF Participating Banks");
			} else {

				ResultSet result = (ResultSet) callable.getObject(2);
				while (result.next()) {
					String bankId = result.getString(1);

					rsfBanks.add(bankId);
				}

				result.close();
				result = null;
			}
			callable.close();
			callable = null;
			connection.commit();

		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "getAllRsfParticipatingBanks", ignore.getMessage());
			}

			Log.log(Log.ERROR, "AdminDAO", "getAllRsfParticipatingBanks", e.getMessage());
			Log.logException(e);
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(Log.INFO, "AdminDAO", "getAllRsfParticipatingBanks", "Exited");
		return rsfBanks;
	}

	public ArrayList getAllRsf2ParticipatingBanks() throws DatabaseException {
		Connection connection;
		ArrayList rsfBanks;
		//System.out.println("test-admindao");
		Log.log(4, "AdminDAO", "getAllRsfParticipatingBanks", "Entered");
		connection = DBConnection.getConnection(false);
		rsfBanks = new ArrayList();
		try {
			//System.out.println("rsf2 test");
			CallableStatement callable = connection.prepareCall("{?=call packGetAllRsfBanks.funcGetAllRsf2Banks(?,?)}");
			callable.registerOutParameter(1, 4);
			callable.registerOutParameter(2, -10);
			callable.registerOutParameter(3, 12);
			callable.execute();
			int errorCode = callable.getInt(1);
			String error = callable.getString(3);
			Log.log(5, "AdminDAO", "getAllRsfParticipatingBanks", (new StringBuilder())
					.append("Error code and error are ").append(errorCode).append(" ").append(error).toString());
			if (errorCode == 1) {
				connection.rollback();
				callable.close();
				callable = null;
				throw new DatabaseException("Unable to get RSF Participating Banks");
			}
			ResultSet result;
			String bankId;
			for (result = (ResultSet) callable.getObject(2); result.next(); rsfBanks.add(bankId)) {
				bankId = result.getString(1);
				//System.out.println((new StringBuilder()).append("bankId-----").append(bankId).toString());
			}

			//System.out.println((new StringBuilder()).append("rsfBanks size ").append(rsfBanks.size()).toString());
			result.close();
			result = null;
			callable.close();
			callable = null;
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(2, "AdminDAO", "getAllRsfParticipatingBanks", ignore.getMessage());
			}
			Log.log(2, "AdminDAO", "getAllRsfParticipatingBanks", e.getMessage());
			Log.logException(e);
		} finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(4, "AdminDAO", "getAllRsfParticipatingBanks", "Exited");
		return rsfBanks;
	}

	/**
	 * returns an ArrayList of active bank users come under a member. If the member
	 * is ZO or RO or HO, all the users under the reporting members also included.
	 * 
	 * @param memberId - Member id
	 * @return ArrayList
	 * @throws NoUserFoundException
	 * @throws DatabaseException
	 * @roseuid 397422890173
	 */
	public ArrayList getActiveBankUsers(String memberId) throws DatabaseException {
		Connection connection = DBConnection.getConnection(false);

		ArrayList bankUsers = new ArrayList();

		try {
			CallableStatement callable = connection
					.prepareCall("{?=call packGetAllUsersForMember.funcGetAllUsers(?,?,?)}");

			callable.setString(2, memberId);

			callable.registerOutParameter(1, Types.INTEGER); // error status

			callable.registerOutParameter(3, Constants.CURSOR);

			callable.registerOutParameter(4, Types.VARCHAR); // error message

			callable.execute();

			int functionReturn = callable.getInt(1);

			String error = callable.getString(4);

			if (functionReturn == Constants.FUNCTION_FAILURE) {

				connection.rollback();

				callable.close();

				callable = null;

				throw new DatabaseException(error);

			}

			else {
				ResultSet result = (ResultSet) callable.getObject(3);

				while (result.next()) {

					User bankUser = new User();
					bankUser.setUserId(result.getString(1));
					bankUser.setFirstName(result.getString(2));
					bankUser.setMiddleName(result.getString(3));
					bankUser.setLastName(result.getString(4));
					bankUser.setDesignation(result.getString(5));
					bankUser.setBankId(result.getString(6));
					bankUser.setZoneId(result.getString(7));
					bankUser.setBranchId(result.getString(8));
					bankUser.setEmailId(result.getString(9));
					bankUser.setPassword(result.getString(10));
					// bankUser.setHintQuestion(result.getString(11));
					// bankUser.setHintAnswer(result.getString(12));
					// bankUser.setStatus(result.getString(13));
					bankUser.setFirstName(result.getString(14));
					bankUser.setFirstName(result.getString(15));
					bankUser.setFirstName(result.getString(16));

					// The bankUser object is added to the ArrayList.
					bankUsers.add(bankUser);
				}

				result.close();

				result = null;
			}

			connection.commit();

			callable.close();

			callable = null;

		} catch (SQLException e) {

			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "getActiveBankUsers", ignore.getMessage());
			}

			Log.log(Log.ERROR, "AdminDAO", "getActiveBankUsers", e.getMessage());

			Log.logException(e);

			throw new DatabaseException("Unable to get active bank users");

		} finally {

			DBConnection.freeConnection(connection);
		}

		return bankUsers;
	}

	/**
	 * The users for the member id passed are retrieved. Ramesh rp14480 29 NOV 2003
	 */
	public ArrayList getUsers(String memberId) throws DatabaseException {
		Log.log(Log.INFO, "AdminDAO", "getUsers", "Entered");

		Connection connection = DBConnection.getConnection(false);

		ArrayList userIdList = new ArrayList();

		try {

			CallableStatement callable = connection.prepareCall("{call PKGGETUSERMEMFUNCGETUSERFORMEMBER(?,?,?)}");
	
			// Set the member id in the database.
			callable.setString(2, memberId);

			// The error status and error message are retrieved from the database.

			callable.registerOutParameter(1, Types.INTEGER);

			callable.registerOutParameter(3, Types.VARCHAR);

			callable.execute();

			// If error status is 1 throw database exception.

			int functionReturn = callable.getInt(1);

			String error = callable.getString(3);

			if (functionReturn == Constants.FUNCTION_FAILURE) {

				connection.rollback();

				callable.close();

				callable = null;

				throw new DatabaseException(error);
			}

			else {

				ResultSet result = callable.executeQuery();

				while (result.next()) {

					String userId = result.getString(1);
					//System.out.println("USER_ID==" + userId);

					userIdList.add(userId);

				}
				//System.out.println("USER_ID==" + userIdList);
				Log.log(Log.DEBUG, "AdminDAO", "getUsers", "userIdList" + userIdList);

				result.close();

				result = null;
			}

			callable.close();

			callable = null;

			connection.commit();

		} catch (SQLException e) {

			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "getUsers", ignore.getMessage());
			}

			Log.log(Log.ERROR, "AdminDAO", "getUsers", e.getMessage());

			Log.logException(e);

			throw new DatabaseException("unable to get users");

		} finally {

			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO, "AdminDAO", "getUsers", "Exited");

		return userIdList;
	}

	/**
	 * The active users from all the Hos are retrieved. Ramesh rp14480 29 NOV 2003
	 */
	public ArrayList getAllHoUsers() throws DatabaseException {
		Connection connection = DBConnection.getConnection(false);

		ArrayList userIdList = new ArrayList();

		try {

			CallableStatement callable = connection.prepareCall("{call FUNCGETUSERSFORHO(?,?)}");

			// The error status and error message are retrieved from the database.

			callable.registerOutParameter(1, Types.INTEGER);

			callable.registerOutParameter(2, Types.VARCHAR);

			// Retrieves the userIDs.

			callable.execute();

			// If error status is 1 throw database exception.

			int functionReturn = callable.getInt(1);

			String error = callable.getString(2);

			if (functionReturn == Constants.FUNCTION_FAILURE) {

				connection.rollback();

				callable.close();

				callable = null;

				throw new DatabaseException(error);
			}

			else {

				ResultSet result = callable.executeQuery();

				while (result.next()) {

					String userId = result.getString(1);
					Log.log(Log.DEBUG, "AdminDAO", "getAllHoUsers", "userId" + userId);

					userIdList.add(userId);
				}

				result.close();
				result = null;
			}

			callable.close();

			callable = null;

			connection.commit();

		} catch (SQLException e) {

			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "getAllHoUsers", ignore.getMessage());
			}

			Log.log(Log.ERROR, "AdminDAO", "getAllHoUsers", e.getMessage());

			Log.logException(e);

			throw new DatabaseException("Unable to get all HO users");

		} finally {

			DBConnection.freeConnection(connection);
		}

		return userIdList;
	}

	/**
	 * The modified user details are updated into database
	 * 
	 * @param user
	 * @return boolean
	 * @throws DatabaseException
	 * @roseuid 3977D49B02A0
	 */
	public boolean modifyUser(User user,String createdBy) throws DatabaseException
    {
                 Connection connection=DBConnection.getConnection(false);

                 try{
                         CallableStatement callable=connection.prepareCall
                                                                 ("{call funcUpdateUserDetail(?,?,?,?,?,?,?,?,?)}");

                         //the new details of the user is passed to the database.

                         callable.setString(2,user.getUserId());

                         callable.setString(3,user.getFirstName());

                         callable.setString(4,user.getMiddleName());

                         callable.setString(5,user.getLastName());

                         callable.setString(6,user.getDesignation());

                         callable.setString(7,user.getEmailId());

                         callable.setString(8,createdBy);

                         //The error status and error message are retrieved from the database.

                         callable.registerOutParameter(1,Types.INTEGER);

                         callable.registerOutParameter(9,Types.VARCHAR);

                         callable.execute();

                         //If error status is 1 throw database exception.

                         int functionReturn=callable.getInt(1);

                         String error=callable.getString(9);

                         if(functionReturn==Constants.FUNCTION_FAILURE){

                                 connection.rollback();

                                 callable.close();

                                 callable=null;

                                 throw new DatabaseException(error);
                         }
                 callable.close();
                 callable=null;


         }catch (SQLException e) {

                                 try {
                                   connection.rollback();
                                 }
                                 catch (SQLException ignore) {
                                         Log.log(Log.ERROR,"AdminDAO","modifyUser",ignore.getMessage());
                                 }

                                 Log.log(Log.ERROR,"AdminDAO","modifyUser",e.getMessage());

                                 Log.logException(e);

                                 throw new DatabaseException("Unable to modify user");

              }finally{

                                 DBConnection.freeConnection(connection);
                   }

            return false;
    }

	/**
	 * Whenver a new role details are added, this method is used to update into
	 * database
	 *
	 * @param role
	 * @return boolean
	 * @throws DuplicateException
	 * @throws DatabaseException
	 * @roseuid 3977D6BB0339
	 */
	public boolean addRole(Role role, User user) throws DuplicateException, DatabaseException {
		Log.log(Log.INFO, "AdminDAO", "addRole", "Entered");

		Connection connection = DBConnection.getConnection(false);
		try {

			// Insert the role first.

			CallableStatement callable = connection.prepareCall("{?=call PACKGETALLROLES.FUNCINSERTROLES(?,?,?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);
			callable.setString(2, role.getRoleName());
			callable.setString(3, role.getRoleDescription());
			callable.setString(4, user.getUserId());
			callable.registerOutParameter(5, Types.VARCHAR);

			callable.execute();
			// If error status is 1 throw database exception.
			int errorStatus = callable.getInt(1);
			String error = callable.getString(5);

			Log.log(Log.DEBUG, "AdminDAO", "addRole", "Status After adding role " + errorStatus + " " + error);

			callable.close();
			callable = null;

			if (errorStatus == Constants.FUNCTION_FAILURE) {
				if (callable != null) {
					callable.close();
				}
				connection.rollback();
				throw new DatabaseException("Unable to add Role");
			}
			ArrayList privileges = role.getPrivileges();

			Log.log(Log.DEBUG, "AdminDAO", "addRole", "Privileges Are " + privileges);

			// Insert the privileges into database.
			String privilege = null;

			for (int i = 0; i < privileges.size(); i++) {
				// callable=connection.prepareCall
				// ("{?=call funcAddRolePrivilege(?,?,?,?)}");
				callable = connection.prepareCall("{?=call FUNCUPDATEROLEPRIVILEGE(?,?,?,?,?)}");
				privilege = (String) privileges.get(i);

				Log.log(Log.DEBUG, "AdminDAO", "addRole", "privilege " + privilege + " @ " + i);

				callable.registerOutParameter(1, Types.INTEGER);
				callable.setString(2, role.getRoleName());
				callable.setString(3, privilege);
				callable.setString(4, AdminConstants.ACTIVE_FLAG);
				callable.setString(5, user.getUserId());
				callable.registerOutParameter(6, Types.VARCHAR);

				callable.execute();

				errorStatus = callable.getInt(1);
				error = callable.getString(6);

				if (errorStatus == Constants.FUNCTION_FAILURE) {
					callable.close();
					callable = null;
					Log.log(Log.DEBUG, "AdminDAO", "addRole",
							"Status After adding privilege " + errorStatus + " " + error);
					// Role back.
					connection.rollback();
					throw new DatabaseException("Unable to add Privileges to Role");
				}
				callable.close();
				callable = null;
			}
			// If everything successfull, commit the transaction.
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "addRole", ignore.getMessage());
			}
			Log.log(Log.ERROR, "AdminDAO", "addRole", e.getMessage());
			Log.logException(e);
			throw new DatabaseException(e.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO, "AdminDAO", "addRole", "Exited");
		return false;
	}

	/**
	 * This method returns an ArrayList of all the roles available
	 * 
	 * @return ArrayList
	 * @throws DatabaseException
	 * @roseuid 3977D8D30389
	 */
	public ArrayList getAllRoles() throws DatabaseException {
		Log.log(Log.INFO, "AdminDAO", "getAllRoles", "Entered");
		Connection connection = DBConnection.getConnection(false);
		ArrayList rolesList = new ArrayList();
		try {
			CallableStatement callable = connection.prepareCall("{call PACKGETALLROLESFUNCGETALLROLES (?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);

			callable.registerOutParameter(2, Types.VARCHAR);

			callable.execute();

			int errorCode = callable.getInt(1);
			String error = callable.getString(2);

			Log.log(Log.DEBUG, "AdminDAO", "getAllRoles", "Error code and error " + errorCode + " " + error);

			if (errorCode == Constants.FUNCTION_FAILURE) {
				connection.rollback();

				callable.close();
				callable = null;

				Log.log(Log.ERROR, "AdminDAO", "getAllRoles", error);
				throw new DatabaseException(error);
			} else {
				ResultSet result = callable.executeQuery();

				while (result.next()) {
					Role role = new Role();
					String roleName = result.getString(1);
					String roleDescription = result.getString(2);
					role.setRoleName(roleName);
					role.setRoleDescription(roleDescription);
					rolesList.add(role);
				}

				result.close();
				result = null;
			}

			connection.commit();
			callable.close();
			callable = null;
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "getAllRoles", ignore.getMessage());
			}

			Log.log(Log.ERROR, "AdminDAO", "getAllRoles", e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Unable to get Roles");
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(Log.INFO, "AdminDAO", "getAllRoles", "Exited");
		return rolesList;
	}

	public ArrayList getPrivilegesForRole(String roleName) throws DatabaseException {
		Log.log(Log.INFO, "AdminDAO", "getPrivilegesForRole", "Entered");

		Connection connection = DBConnection.getConnection(false);
		ArrayList privileges = new ArrayList();

		try {
			CallableStatement callable = connection.prepareCall("{call PKGALLGESFUNCGETALLPRIVILEGES(?,?,?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);

			callable.setString(2, roleName);

			callable.registerOutParameter(3, Types.VARCHAR);

			callable.execute();

			int errorCode = callable.getInt(1);
			String error = callable.getString(4);

			Log.log(Log.DEBUG, "AdminDAO", "getPrivilegesForRole", " error code and error: " + errorCode + " " + error);

			if (errorCode == Constants.FUNCTION_FAILURE) {

				connection.rollback();
				Log.log(Log.ERROR, "AdminDAO", "getPrivilegesForRole", error);
				throw new DatabaseException(error);
			}

			ResultSet result = callable.executeQuery();

			while (result.next()) {
				String privilege = result.getString(1);

				if (privilege != null) {
					privileges.add(privilege);
				}
			}
			result.close();
			result = null;
			callable.close();
			callable = null;
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "getPrivilegesForRole", ignore.getMessage());
			}

			Log.log(Log.ERROR, "AdminDAO", "getPrivilegesForRole", e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Unable to get Privileges for a role");
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(Log.INFO, "AdminDAO", "getPrivilegesForRole", "Exited");
		return privileges;
	}

	/**
	 * This method updates the modified role details into database.
	 * 
	 * @param role
	 * @return boolean
	 * @throws DatabaseException
	 * @roseuid 3977EE0A00BD
	 */
	public void modifyRole(Role role, User user) throws DatabaseException {
		Log.log(Log.INFO, "AdminDAO", "modifyRole", "Entered");

		Connection connection = DBConnection.getConnection(false);

		try {
			// First update the role description.
			CallableStatement callable = connection.prepareCall("{?=call packGetAllRoles.funcUpdateRoles(?,?,?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);

			callable.setString(2, role.getRoleName());
			callable.setString(3, role.getRoleDescription());
			callable.setString(4, user.getUserId());

			callable.registerOutParameter(5, Types.VARCHAR);

			callable.execute();

			int errorCode = callable.getInt(1);
			String error = callable.getString(5);

			Log.log(Log.DEBUG, "AdminDAO", "modifyRole", " error code and error: " + errorCode + " " + error);

			if (errorCode == Constants.FUNCTION_FAILURE) {
				connection.rollback();

				callable.close();
				callable = null;

				Log.log(Log.ERROR, "AdminDAO", "modifyRole", error);
				connection.rollback();

				throw new DatabaseException(error);
			}

			callable.close();
			callable = null;

			// get the privileges for the role from database.
			ArrayList databasePrivileges = getPrivilegesForRole(role.getRoleName());

			// get the privileges added/modified from the passed in role object.
			ArrayList screenPrivileges = role.getPrivileges();
			boolean isAvl = false;
			int counter = 0;
			ArrayList availablePrivileges = new ArrayList();

			for (int i = 0; i < screenPrivileges.size(); i++) {
				isAvl = false;

				String screenPrivilege = (String) screenPrivileges.get(i);
				Log.log(Log.DEBUG, "AdminDAO", "modifyRole", " screenPrivilege " + screenPrivilege);

				for (int j = 0; j < databasePrivileges.size(); j++) {
					String databasePrivilege = (String) databasePrivileges.get(j);

					Log.log(Log.DEBUG, "AdminDAO", "modifyRole", " databasePrivilege " + databasePrivilege);

					if (screenPrivilege.equals(databasePrivilege)) {
						// database and screen privileges are same.
						// means user did not modify this privilege.
						isAvl = true;

						// increment the counter.
						counter++;
						Log.log(Log.DEBUG, "AdminDAO", "modifyRole", "available in screen and database ");
						availablePrivileges.add(databasePrivilege);

						break;
					}
				}
				if (!isAvl) {
					Log.log(Log.DEBUG, "AdminDAO", "modifyRole", " new privilege ");
					// Privilege is not available in the database.
					// Add the new privilege
					// callable=connection.prepareCall("{?=call funcAddRolePrivilege(?,?,?,?)}");
					callable = connection.prepareCall("{?=call FUNCUPDATEROLEPRIVILEGE(?,?,?,?,?)}");

					callable.registerOutParameter(1, Types.INTEGER);
					callable.setString(2, role.getRoleName());
					callable.setString(3, screenPrivilege);
					callable.setString(4, AdminConstants.ACTIVE_FLAG);
					callable.setString(5, user.getUserId());
					callable.registerOutParameter(6, Types.VARCHAR);

					// callable.addBatch();
					callable.execute();

					errorCode = callable.getInt(1);
					error = callable.getString(6);

					if (errorCode == Constants.FUNCTION_FAILURE) {
						callable.close();
						callable = null;

						connection.rollback();
						Log.log(Log.ERROR, "AdminDAO", "modifyRole", error);
						throw new DatabaseException(error);
					}

					callable.close();
					callable = null;
					Log.log(Log.DEBUG, "AdminDAO", "modifyRole", " new privilege for user ");

					callable = connection.prepareCall("{?=call FUNCADDPRIV(?,?,?,?,?)}");

					callable.registerOutParameter(1, Types.INTEGER);
					callable.setString(2, role.getRoleName());
					callable.setString(3, screenPrivilege);
					callable.setString(4, AdminConstants.ACTIVE_FLAG);
					callable.setString(5, user.getUserId());
					callable.registerOutParameter(6, Types.VARCHAR);

					// callable.addBatch();
					callable.execute();

					errorCode = callable.getInt(1);
					error = callable.getString(6);

					if (errorCode == Constants.FUNCTION_FAILURE) {
						callable.close();
						callable = null;

						connection.rollback();
						Log.log(Log.ERROR, "AdminDAO", "modifyRole", error);
						throw new DatabaseException(error);
					}

					callable.close();
					callable = null;
				}
			}
			Log.log(Log.DEBUG, "AdminDAO", "modifyRole",
					"counter and database privileges size " + counter + " " + databasePrivileges.size());
			if (counter != databasePrivileges.size()) {
				Log.log(Log.DEBUG, "AdminDAO", "modifyRole", " processing removed privileges ");
				// In the sceeen, few privileges are removed.
				// Update those privileges also.

				for (int i = 0; i < databasePrivileges.size(); i++) {
					isAvl = false;
					String databasePrivilege = (String) databasePrivileges.get(i);

					Log.log(Log.DEBUG, "AdminDAO", "modifyRole", "databasePrivilege " + databasePrivilege);

					for (int j = 0; j < availablePrivileges.size(); j++) {
						String availablePrivilege = (String) availablePrivileges.get(j);

						Log.log(Log.DEBUG, "AdminDAO", "modifyRole", "availablePrivilege " + availablePrivilege);

						if (databasePrivilege.equals(availablePrivilege)) {
							// Privilege is available. No need to update the status.
							isAvl = true;
							break;
						}
					}
					if (!isAvl) {
						// Privilege has been removed. so, update the status.
						Log.log(Log.DEBUG, "AdminDAO", "modifyRole", " privilege removed");
						callable = connection.prepareCall("{?=call FUNCUPDATEROLEPRIVILEGE(?,?,?,?,?)}");

						callable.registerOutParameter(1, Types.INTEGER);
						callable.setString(2, role.getRoleName());
						callable.setString(3, databasePrivilege);
						callable.setString(4, AdminConstants.INACTIVE_FLAG);
						callable.setString(5, user.getUserId());
						callable.registerOutParameter(6, Types.VARCHAR);

						// callable.addBatch();

						callable.execute();

						errorCode = callable.getInt(1);
						error = callable.getString(6);

						if (errorCode == Constants.FUNCTION_FAILURE) {
							callable.close();
							callable = null;

							connection.rollback();
							Log.log(Log.ERROR, "AdminDAO", "modifyRole", error);
							throw new DatabaseException(error);
						}

						callable.close();
						callable = null;
						Log.log(Log.DEBUG, "AdminDAO", "modifyRole", " privilege removed for user");
						callable = connection.prepareCall("{?=call FUNCREMPRIV(?,?,?,?,?)}");

						callable.registerOutParameter(1, Types.INTEGER);
						callable.setString(2, role.getRoleName());
						callable.setString(3, databasePrivilege);
						callable.setString(4, AdminConstants.INACTIVE_FLAG);
						callable.setString(5, user.getUserId());
						callable.registerOutParameter(6, Types.VARCHAR);

						// callable.addBatch();

						callable.execute();

						errorCode = callable.getInt(1);
						error = callable.getString(6);

						if (errorCode == Constants.FUNCTION_FAILURE) {
							callable.close();
							callable = null;

							connection.rollback();
							Log.log(Log.ERROR, "AdminDAO", "modifyRole", error);
							throw new DatabaseException(error);
						}

						callable.close();
						callable = null;
					}
				}
			}

			if (callable != null) {
				callable.close();
			}
			callable = null;
			// If everything is successful, commit the transaction.
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "modifyRole", ignore.getMessage());
			}
			Log.log(Log.ERROR, "AdminDAO", "modifyRole", e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Unable to get Privileges for a role");
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(Log.INFO, "AdminDAO", "modifyRole", "Exited");
	}

	/**
	 * Assign the roles and privileges for the given user id
	 * 
	 * @param role
	 * @param privilege
	 * @param userID
	 * @throws DatabaseException
	 * @roseuid 3977F1370383
	 */
	public void assignRolesAndPrivileges(ArrayList roles, ArrayList privileges, String userId, String creatingUser)
			throws DatabaseException {
		Log.log(Log.INFO, "AdminDAO", "assignRolesAndPrivileges", "Entered");

		Connection connection = DBConnection.getConnection(false);

		try {

			int errorCode = 0;
			String error = null;

			// Insert the roles for the user.
			for (int i = 0; i < roles.size(); i++) {
				String role = (String) roles.get(i);

				Log.log(Log.INFO, "AdminDAO", "assignRolesAndPrivileges", "role " + role);

				// CallableStatement callable=connection.prepareCall("{?=call
				// FUNCUPDATEUSERROLE(?,?,?,?,?)}");
				CallableStatement callable = connection.prepareCall("{call FUNCUPDATEUSERROLE(?,?,?,?,?,?)}");

				callable.registerOutParameter(1, Types.INTEGER);

				callable.setString(2, userId);
				callable.setString(3, role);
				callable.setString(4, AdminConstants.ACTIVE_FLAG);
				callable.setString(5, creatingUser);
				callable.registerOutParameter(6, Types.VARCHAR);

				callable.execute();

				errorCode = callable.getInt(1);
				error = callable.getString(6);

				callable.close();
				callable = null;

				Log.log(Log.INFO, "AdminDAO", "assignRolesAndPrivileges(FUNCUPDATEUSERROLE)",
						"error code and error are  " + errorCode + " " + error);

				if (errorCode == Constants.FUNCTION_FAILURE) {
					connection.rollback();

					Log.log(Log.ERROR, "AdminDAO", "assignRolesAndPrivileges", error);

					throw new DatabaseException(error);
				}
			}

			// Insert the privileges for the user.

			for (int i = 0; i < privileges.size(); i++) {

				String privilege = (String) privileges.get(i);

				Log.log(Log.INFO, "AdminDAO", "assignRolesAndPrivileges", "privilege " + privilege);

				// CallableStatement callable=connection.prepareCall("{?=call
				// FUNCUPDATEUSERPRIVILEGE(?,?,?,?,?)}");
				CallableStatement callable = connection.prepareCall("{call FUNCUPDATEUSERPRIVILEGE(?,?,?,?,?,?)}");

				callable.registerOutParameter(1, Types.INTEGER);

				callable.setString(2, userId);
				callable.setString(3, privilege);
				callable.setString(4, AdminConstants.ACTIVE_FLAG);
				callable.setString(5, creatingUser);
				callable.registerOutParameter(6, Types.VARCHAR);

				callable.execute();

				errorCode = callable.getInt(1);
				error = callable.getString(6);

				Log.log(Log.INFO, "AdminDAO", "assignRolesAndPrivileges(FUNCUPDATEUSERPRIVILEGE)",
						"error code and error are  " + errorCode + " " + error);

				callable.close();
				callable = null;

				if (errorCode == Constants.FUNCTION_FAILURE) {
					connection.rollback();
					Log.log(Log.ERROR, "AdminDAO", "assignRolesAndPrivileges", error);

					throw new DatabaseException(error);
				}
			}
			// connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "assignRolesAndPrivileges", ignore.getMessage());
			}
			Log.log(Log.ERROR, "AdminDAO", "assignRolesAndPrivileges", e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Unable to Assign Roles And Privileges");
		} finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO, "AdminDAO", "assignRolesAndPrivileges", "Exited");
	}

	public void modifyRolesAndPrivileges(ArrayList roles, ArrayList privileges, String userID, String creatingUser)
			throws DatabaseException {
		Log.log(Log.INFO, "AdminDAO", "modifyRolesAndPrivileges", "Entered");

		for (int i = 0; i < privileges.size(); i++) {
			Log.log(Log.INFO, "AdminDAO", "modifyRolesAndPrivileges",
					"Privilege fro the Screen :" + (String) privileges.get(i));
		}

		Connection connection = DBConnection.getConnection(false);

		try {
			CallableStatement callable = connection.prepareCall("{call PKGFUNCGETALLROLESFORUSER(?,?,?)}");

			// get all the roles for the user.
			callable.registerOutParameter(1, Types.INTEGER);
			callable.setString(2, userID);

			callable.registerOutParameter(3, Types.VARCHAR);

			callable.execute();

			int errorCode = callable.getInt(1);
			String error = callable.getString(3);

			Log.log(Log.DEBUG, "AdminDAO", "modifyRolesAndPrivileges",
					"error code and error are  " + errorCode + " " + error);

			if (errorCode == Constants.FUNCTION_FAILURE) {
				callable.close();
				callable = null;
				Log.log(Log.ERROR, "AdminDAO", "modifyRolesAndPrivileges", error);
				throw new DatabaseException(error);
			}

			Log.log(Log.DEBUG, "AdminDAO", "modifyRolesAndPrivileges", "After getting user roles");

			ArrayList databaseRoles = new ArrayList();
			ResultSet rolesResult = callable.executeQuery();

//                      This variable to hold the newly added roles
			ArrayList newRoles = new ArrayList();

			// This variable to hold the removed roles
			ArrayList removedRoles = new ArrayList();
			boolean avl = false;

			while (rolesResult.next()) {
				String role = rolesResult.getString(1);
				avl = roles.contains(role);
				databaseRoles.add(role);

				if (!avl) {
					Log.log(Log.DEBUG, "AdminDAO", "modifyRolesAndPrivileges", "remove role " + role);
					removedRoles.add(role);
				}
			}
			rolesResult.close();
			rolesResult = null;

			for (int i = 0; i < roles.size(); i++) {
				String role = (String) roles.get(i);
				if (!databaseRoles.contains(role)) {
					Log.log(Log.DEBUG, "AdminDAO", "modifyRolesAndPrivileges", "new role " + role);
					newRoles.add(role);
				}
			}

			for (int i = 0; i < removedRoles.size(); i++) {
				callable = connection.prepareCall("{call FUNCUPDATEUSERROLE(?,?,?,?,?,?)}");

				String role = (String) removedRoles.get(i);

				callable.registerOutParameter(1, Types.INTEGER);
				callable.setString(2, userID);
				callable.setString(3, role);
				callable.setString(4, AdminConstants.INACTIVE_FLAG);
				callable.setString(5, creatingUser);

				callable.registerOutParameter(6, Types.VARCHAR);

				callable.execute();

				errorCode = callable.getInt(1);
				error = callable.getString(6);

				Log.log(Log.DEBUG, "AdminDAO", "modifyRolesAndPrivileges",
						"error code and error are  " + errorCode + " " + error);

				if (errorCode == Constants.FUNCTION_FAILURE) {
					callable.close();
					callable = null;
					connection.rollback();

					throw new DatabaseException(error);
				}
				callable.close();
				callable = null;
			}

			for (int i = 0; i < newRoles.size(); i++) {
				String newRole = (String) newRoles.get(i);

				Log.log(Log.DEBUG, "AdminDAO", "modifyRolesAndPrivileges", "role " + newRole);

				callable = connection.prepareCall("{call FUNCUPDATEUSERROLE(?,?,?,?,?,?)}");

				callable.registerOutParameter(1, Types.INTEGER);

				callable.setString(2, userID);
				callable.setString(3, newRole);
				callable.setString(4, AdminConstants.ACTIVE_FLAG);

				callable.setString(5, creatingUser);
				callable.registerOutParameter(6, Types.VARCHAR);
				callable.execute();

				errorCode = callable.getInt(1);
				error = callable.getString(6);

				callable.close();
				callable = null;

				Log.log(Log.DEBUG, "AdminDAO", "modifyRolesAndPrivileges",
						"error code and error are  " + errorCode + " " + error);

				if (errorCode == Constants.FUNCTION_FAILURE) {
					connection.rollback();

					Log.log(Log.ERROR, "AdminDAO", "modifyRolesAndPrivileges", error);

					throw new DatabaseException(error);
				}
			}

			Log.log(Log.DEBUG, "AdminDAO", "modifyRolesAndPrivileges", "Role are updated...starting Privileges.");
			// Do the same thing for privileges also.

			callable = connection.prepareCall("{call PKGfuncGetAllPrivilegesForUser(?,?,?)}");

			// get all the roles for the user.
			callable.registerOutParameter(1, Types.INTEGER);
			callable.setString(2, userID);

			callable.registerOutParameter(3, Types.VARCHAR);

			callable.execute();

			errorCode = callable.getInt(1);
			error = callable.getString(3);

			Log.log(Log.DEBUG, "AdminDAO", "modifyRolesAndPrivileges",
					"error code and error are  " + errorCode + " " + error);

			if (errorCode == Constants.FUNCTION_FAILURE) {
				callable.close();
				callable = null;
				Log.log(Log.ERROR, "AdminDAO", "modifyRolesAndPrivileges", error);
				throw new DatabaseException(error);
			}

			Log.log(Log.DEBUG, "AdminDAO", "modifyRolesAndPrivileges", "After getting user privileges");

			databaseRoles.clear();
			newRoles.clear();
			removedRoles.clear();

			ArrayList databasePrivileges = new ArrayList();
			ResultSet privilegesResult = callable.executeQuery();

			// This variable to hold the newly added roles
			ArrayList newPrivileges = new ArrayList();

			// This variable to hold the removed roles
			ArrayList removedPrivileges = new ArrayList();
			avl = false;

			while (privilegesResult.next()) {
				String privilege = privilegesResult.getString(1);
				Log.log(Log.DEBUG, "AdminDAO", "modifyRolesAndPrivileges", "Privilege from DB " + privilege);
				// avl=privileges.contains(privilege);
				Log.log(Log.DEBUG, "AdminDAO", "modifyRolesAndPrivileges", "Privilege present flag " + avl);
				databasePrivileges.add(privilege);

				/*
				 * if(!avl) {
				 * Log.log(Log.DEBUG,"AdminDAO","modifyRolesAndPrivileges","removed privilege "
				 * +privilege); removedPrivileges.add(privilege); }
				 */
			}
			privilegesResult.close();
			privilegesResult = null;

			/*
			 * for(int i=0; i<privileges.size(); i++) { String
			 * privilege=(String)privileges.get(i);
			 * if(!databasePrivileges.contains(privilege)) {
			 * Log.log(Log.DEBUG,"AdminDAO","modifyRolesAndPrivileges","removed privilege "
			 * +privilege); removedPrivileges.add(privilege); } }
			 */

			if ((privileges != null) && (databasePrivileges != null)) {
				// Some privileges are to be revoked
				for (int i = 0; i < databasePrivileges.size(); i++) {
					String privilege = (String) databasePrivileges.get(i);
					if (privilege == null) {
						continue;
					}
					if (!privileges.contains(privilege)) {
						removedPrivileges.add(privilege);
					}
				}
			}

			for (int i = 0; i < privileges.size(); i++) {
				String privilege = (String) privileges.get(i);
				if (!databasePrivileges.contains(privilege)) {
					Log.log(Log.DEBUG, "AdminDAO", "modifyRolesAndPrivileges", "new privilege " + privilege);
					newPrivileges.add(privilege);
				}
			}

			for (int i = 0; i < removedPrivileges.size(); i++) {
				String privilege = (String) removedPrivileges.get(i);
				Log.log(Log.DEBUG, "AdminDAO", "modifyRolesAndPrivileges", "role " + privilege);
				callable = connection.prepareCall("{call FUNCUPDATEUSERPRIVILEGE(?,?,?,?,?,?)}");

				callable.registerOutParameter(1, Types.INTEGER);
				callable.setString(2, userID);
				callable.setString(3, privilege);
				callable.setString(4, AdminConstants.INACTIVE_FLAG);
				callable.setString(5, creatingUser);
				callable.registerOutParameter(6, Types.VARCHAR);

				callable.execute();

				errorCode = callable.getInt(1);
				error = callable.getString(6);

				Log.log(Log.DEBUG, "AdminDAO", "modifyRolesAndPrivileges",
						"error code and error are  " + errorCode + " " + error);

				if (errorCode == Constants.FUNCTION_FAILURE) {
					callable.close();
					callable = null;
					connection.rollback();

					throw new DatabaseException(error);
				}
				callable.close();
				callable = null;
			}

			for (int i = 0; i < newPrivileges.size(); i++) {
				String newPrivilege = (String) newPrivileges.get(i);

				Log.log(Log.DEBUG, "AdminDAO", "modifyRolesAndPrivileges", "role " + newPrivilege);

				callable = connection.prepareCall("{call FUNCUPDATEUSERPRIVILEGE(?,?,?,?,?,?)}");

				callable.registerOutParameter(1, Types.INTEGER);

				callable.setString(2, userID);
				callable.setString(3, newPrivilege);
				callable.setString(4, AdminConstants.ACTIVE_FLAG);
				callable.setString(5, creatingUser);
				callable.registerOutParameter(6, Types.VARCHAR);

				callable.execute();

				errorCode = callable.getInt(1);
				error = callable.getString(6);

				callable.close();
				callable = null;

				Log.log(Log.DEBUG, "AdminDAO", "modifyRolesAndPrivileges",
						"error code and error are  " + errorCode + " " + error);

				if (errorCode == Constants.FUNCTION_FAILURE) {
					connection.rollback();

					Log.log(Log.ERROR, "AdminDAO", "modifyRolesAndPrivileges", error);

					throw new DatabaseException(error);
				}
			}

			databasePrivileges.clear();
			databasePrivileges = null;

			newPrivileges.clear();
			newPrivileges = null;

			removedPrivileges.clear();
			removedPrivileges = null;

			connection.commit();
		} catch (SQLException e) {

			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "modifyRolesAndPrivileges", ignore.getMessage());
			}
			Log.log(Log.ERROR, "AdminDAO", "modifyRolesAndPrivileges", e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Unable to Update user Role");
		} finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO, "AdminDAO", "modifyRolesAndPrivileges", "Exited");
	}

	/**
	 * This method is used to resets the password for the given user in the
	 * database.
	 * 
	 * @param userId
	 * @param password
	 * @return String
	 * @throws DatabaseException
	 * @roseuid 39780C77022F
	 */
	public void resetPassword(String userId, String password, String loginUserId) throws DatabaseException {
		Connection connection = DBConnection.getConnection(false);

		try {
			CallableStatement callable = connection.prepareCall("{call funcResetPwd(?,?,?,?,?)}");

			// the default password for the user Id is passed to the database.
			callable.setString(2, userId);
			callable.setString(3, password);
			callable.setString(4, loginUserId);

			// The error status and error message are retrieved from the database.
			callable.registerOutParameter(1, Types.INTEGER);
			callable.registerOutParameter(5, Types.VARCHAR);

			callable.execute();

			// If error status is 1 throw database exception.

			int functionReturn = callable.getInt(1);

			String error = callable.getString(5);

			if (functionReturn == Constants.FUNCTION_FAILURE) {

				connection.rollback();

				callable.close();

				callable = null;

				throw new DatabaseException(error);
			}
			callable.close();
			callable = null;
			connection.commit();

		} catch (SQLException e) {

			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "Reset Password", ignore.getMessage());
			}

			Log.log(Log.ERROR, "AdminDAO", "Reset Password", e.getMessage());

			Log.logException(e);

			throw new DatabaseException("Unable to get reset password");
		} finally {
			DBConnection.freeConnection(connection);
		}

	}

	/**
	 * @param broadcastMessage
	 * @throws DatabaseException
	 * @roseuid 3979770F026D
	 */
	public void broadcastMessage(BroadCastMessage broadcastMessage, String createdBy) throws DatabaseException {

		Connection connection = DBConnection.getConnection(false);
		Log.log(Log.DEBUG, "AdminDAO", "broadcastMessage", "message " + broadcastMessage.getMessage());

		java.util.Date date = new java.util.Date();
		java.sql.Date currentDate = new java.sql.Date(date.getTime());

		try {

			CallableStatement callableFirst = connection.prepareCall("{call FUNCINSERTMESSAGE(?,?,?,?,?)}");

			callableFirst.setString(2, broadcastMessage.getMessage());
			callableFirst.setString(3, createdBy);

			callableFirst.registerOutParameter(1, Types.INTEGER);
			callableFirst.registerOutParameter(4, Types.INTEGER);
			callableFirst.registerOutParameter(5, Types.VARCHAR);

			callableFirst.execute();

			int messageId = callableFirst.getInt(4);

			int functionReturn1 = callableFirst.getInt(1);

			String error1 = callableFirst.getString(5);

			Log.log(Log.DEBUG, "AdminDAO", "broadcastMessage(funcInsertMessage)",
					"error code and error are  " + functionReturn1 + " " + error1);
			Log.log(Log.DEBUG, "AdminDAO", "broadcastMessage", "messageId" + messageId);

			if (functionReturn1 == Constants.FUNCTION_FAILURE) {
				connection.rollback();
				callableFirst.close();
				callableFirst = null;
				throw new DatabaseException(error1);
			}

			callableFirst.close();
			callableFirst = null;

			ArrayList userIds = broadcastMessage.getUserIds();
			int size = userIds.size();

			for (int i = 0; i < size; i++) {
				String userId = (String) userIds.get(i);
				Log.log(Log.DEBUG, "AdminDAO", "broadcastMessage", "userId " + userId);
				CallableStatement callable = connection.prepareCall("{call funcInsertBroadCastMessage(?,?,?,?,?)}");

				// Details of broadcast messsage is passed to the database.
				callable.setString(2, userId); // userId
				callable.setInt(3, messageId); // message content
				callable.setDate(4, currentDate);// message date

				// The error status and error message are retrieved from the database.
				callable.registerOutParameter(1, Types.INTEGER);
				callable.registerOutParameter(5, Types.VARCHAR);

				callable.execute();

				// If error status is 1 throw database exception.

				int functionReturn2 = callable.getInt(1);

				String error2 = callable.getString(5);

				Log.log(Log.DEBUG, "AdminDAO", "broadcastMessage(funcInsertBroadCastMessage)",
						"error code and error are  " + functionReturn2 + " " + error2);

				if (functionReturn2 == Constants.FUNCTION_FAILURE) {

					connection.rollback();

					callable.close();

					callable = null;

					throw new DatabaseException(error2);
				}
				callable.close();
				callable = null;
			}

		} catch (SQLException e) {

			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "Broadcast message", ignore.getMessage());
			}
			Log.log(Log.ERROR, "AdminDAO", "Broadcast message", e.getMessage());

			Log.logException(e);

			throw new DatabaseException("Unable to broadcast message");

		} finally {
			DBConnection.freeConnection(connection);
		}
	}

	/**
	 * This method returns the User object for the passed in member id and the user
	 * id.
	 * 
	 * @param userId
	 * @param mliID
	 * @return User
	 * @throws DatabaseException
	 * @roseuid 398125AA02F0
	 */
	public User getUserInfo(String userId) throws DatabaseException, NoUserFoundException {
		Log.log(Log.INFO, "AdminDAO", "getUserInfo", "userId :" + userId);

		Connection connection = DBConnection.getConnection(false);

		User user = null;

		try {
			Log.log(Log.INFO, "AdminDAO", "getUserInfo", "Entered");

			CallableStatement callable = connection
					.prepareCall("{call FUNCGETUSERDETAIL(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			callable.setString(2, userId);

			callable.registerOutParameter(1, Types.INTEGER); // error status
			callable.registerOutParameter(3, Types.VARCHAR); // firstname
			callable.registerOutParameter(4, Types.VARCHAR); // middle name
			callable.registerOutParameter(5, Types.VARCHAR); // last name
			callable.registerOutParameter(6, Types.VARCHAR); // bank id
			callable.registerOutParameter(7, Types.VARCHAR); // zone id
			callable.registerOutParameter(8, Types.VARCHAR); // branch id
			callable.registerOutParameter(9, Types.VARCHAR); // designation
			callable.registerOutParameter(10, Types.VARCHAR); // emailid
			callable.registerOutParameter(11, Types.VARCHAR); // password
			callable.registerOutParameter(12, Types.VARCHAR); // Hint question
			callable.registerOutParameter(13, Types.VARCHAR); // Hint answer
			callable.registerOutParameter(14, Types.VARCHAR); // status
			callable.registerOutParameter(15, Types.DATE); // Password changed date
			callable.registerOutParameter(16, Types.VARCHAR); // first log in
			callable.registerOutParameter(17, Types.INTEGER); // Unsuccessfull attempts
			callable.registerOutParameter(18, Types.DATE); // Last logged in date

			callable.registerOutParameter(19, Types.VARCHAR); // error message

			callable.execute();

			int functionReturn = callable.getInt(1);

			String error = callable.getString(19);

			// If error status is 1 throw database exception.
			if (functionReturn == Constants.FUNCTION_FAILURE) {
				connection.rollback();

				Log.log(Log.ERROR, "AdminDAO", "getUserInfo", "Function executed with error");

				throw new DatabaseException(error);

			} else {
				Log.log(Log.DEBUG, "AdminDAO", "getUserInfo", "Function executed without error");
				user = new User();
				// the values are retrieved from the database and
				// set into the user object.
				user.setUserId(userId);
				user.setFirstName(callable.getString(3));
				user.setMiddleName(callable.getString(4));
				user.setLastName(callable.getString(5));
				user.setBankId(callable.getString(6));
				user.setZoneId(callable.getString(7));
				user.setBranchId(callable.getString(8));
				user.setDesignation(callable.getString(9));
				user.setEmailId(callable.getString(10));

				user.setPassword(callable.getString(11));
				Hint hint = new Hint();
				hint.setHintQuestion(callable.getString(12));
				hint.setHintAnswer(callable.getString(13));

				user.setHint(hint);
				String userStatus = callable.getString(14);

				if (userStatus.trim().equals(AdminConstants.ACTIVE_FLAG)) {

					Log.log(Log.DEBUG, "AdminDAO", "getUserInfo", "Active user");
					user.setStatus(AdminConstants.ACTIVE_USER_STATUS);
				} else {

					Log.log(Log.DEBUG, "AdminDAO", "getUserInfo", "InActive user");
					user.setStatus(AdminConstants.INACTIVE_USER_STATUS);
				}

				user.setPasswordChangedDate(callable.getDate(15));
				String firstLogin = callable.getString(16);

				if (firstLogin.trim().equals(Constants.YES)) {
					Log.log(Log.DEBUG, "AdminDAO", "getUserInfo", "first log in");
					user.setFirstLogin(true);
				} else {
					Log.log(Log.DEBUG, "AdminDAO", "getUserInfo", "not first log in");
					user.setFirstLogin(false);
				}

				user.setUnsuccessfulAttempts(callable.getInt(17));

				user.setLastLogin(callable.getDate(18));

			}

			connection.commit();
			callable.close();
			callable = null;
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "getUserInfo", ignore.getMessage());
			}

			Log.log(Log.ERROR, "AdminDAO", "getUserInfo", e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Error while getting user details.");
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(Log.INFO, "AdminDAO", "getUserInfo", "Exited");
		return user;
	}

	/**
	 * This method returns all the deactivated users for the given member id.
	 * 
	 * @param memberID
	 * @return ArrayList
	 * @throws DatabaseException
	 * @roseuid 398128520384
	 */
	public ArrayList getAllDeactivatedUsers(String memberID) throws DatabaseException {

		Connection connection = DBConnection.getConnection(false);

		ArrayList inactiveUsers = new ArrayList();

		try {
			CallableStatement callable = connection
					.prepareCall("{call PKGGETUSERMEMFUNCGETINACTUSERSFORMEMBER(?,?,?)}");

			callable.registerOutParameter(1, Types.INTEGER); // error status

			callable.setString(2, memberID);

			callable.registerOutParameter(3, Types.VARCHAR); // error message

			callable.execute();

			int functionReturn = callable.getInt(1);

			String error = callable.getString(3);

			Log.log(Log.DEBUG, "AdminDAO", "getAllDeactivatedUsers",
					"Error Code and error " + functionReturn + " " + error);

			if (functionReturn == Constants.FUNCTION_FAILURE) {

				connection.rollback();

				callable.close();

				callable = null;

				throw new DatabaseException(error);
			}

			else {
				ResultSet result = callable.executeQuery();

				while (result.next()) {
					String inactiveUser = result.getString(1);
					inactiveUsers.add(inactiveUser);
				}
				result.close();
				result = null;
			}

			callable.close();
			callable = null;

			connection.commit();

		} catch (SQLException e) {

			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "getAllDeactivatedUsers", ignore.getMessage());
			}

			Log.log(Log.ERROR, "AdminDAO", "getAllDeactivatedUsers", e.getMessage());

			Log.logException(e);

			throw new DatabaseException("Unable to get all deactivated users");

		} finally {

			DBConnection.freeConnection(connection);
		}

		return inactiveUsers;

	}

	/**
	 * This method is used to send mails between Admin user and the member/CGTSI
	 * users.
	 * 
	 * @param message
	 * @throws DatabaseException
	 * @roseuid 399E5D4900FF
	 */
	public void sendMail(Message message) throws DatabaseException {
		Log.log(Log.INFO, "AdminDAO", "sendMail", "Entered");
		Connection connection = DBConnection.getConnection(false);

		try {
			CallableStatement callable = connection.prepareCall("{call FUNCINSERTADMINMAILINFO(?,?,?,?,?,?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);
			callable.setString(2, message.getFrom());

			//System.out.println("From " + message.getFrom());
			ArrayList to = message.getTo();

			for (int i = 0; i < to.size(); i++) {
				String toAddress = (String) to.get(i);

				callable.setString(3, toAddress);
				//System.out.println("toAddress " + toAddress);
				callable.setString(4, message.getMessage());

				//System.out.println("message " + message.getMessage());

				callable.setString(5, message.getSubject());

				//System.out.println("subject " + message.getSubject());

				callable.setString(6, Constants.NO);

				//System.out.println("read " + Constants.NO);

				callable.registerOutParameter(7, Types.VARCHAR);

				callable.execute();

				int errorCode = callable.getInt(1);
				String error = callable.getString(7);

				Log.log(Log.DEBUG, "AdminDAO", "sendMail", "Error Code and error " + errorCode + " " + error);

				if (errorCode == Constants.FUNCTION_FAILURE) {
					connection.rollback();

					callable.close();
					callable = null;

					Log.log(Log.ERROR, "AdminDAO", "sendMail", error);

					throw new DatabaseException(error);
				}
			}

			callable.close();
			callable = null;
			connection.commit();

		} catch (SQLException e) {

			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "sendMail", ignore.getMessage());
			}
			Log.log(Log.ERROR, "AdminDAO", "sendMail", e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Unable to send mail");
		} finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO, "AdminDAO", "sendMail", "Exited");

	}

	/**
	 * 
	 * @param userId
	 * @param password
	 * @throws com.cgtsi.common.DatabaseException
	 */
	public String sendPasswordMail(String userId, String password, String loggedUserId) throws DatabaseException {
		Log.log(Log.INFO, "AdminDAO", "sendPasswordMail", "Entered");
		Connection connection = DBConnection.getConnection(false);
		String emailId = null;
		try {
			CallableStatement callable = connection.prepareCall("{call Sendpwdresetmsg(?,?,?,?,?)}");

			// callable.registerOutParameter(1,Types.INTEGER);
			callable.setString(1, userId);
			callable.setString(2, password);
			callable.setString(3, loggedUserId);
			callable.registerOutParameter(4, Types.VARCHAR);

			callable.registerOutParameter(5, Types.VARCHAR);

			callable.execute();

			// int errorCode=callable.getInt(1);
			String error = callable.getString(5);
			emailId = callable.getString(4);

			Log.log(Log.DEBUG, "AdminDAO", "sendMail", "Error Code and error " + error);
			//System.out.println("error:" + error);

			/*
			 * if(errorCode==Constants.FUNCTION_FAILURE) { connection.rollback();
			 * 
			 * callable.close(); callable=null;
			 * 
			 * Log.log(Log.ERROR,"AdminDAO","sendPasswordMail",error);
			 * 
			 * throw new DatabaseException(error); }
			 */

			callable.close();
			callable = null;
			connection.commit();

		} catch (SQLException e) {

			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "sendPasswordMail", ignore.getMessage());
			}
			Log.log(Log.ERROR, "AdminDAO", "sendPasswordMail", e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Unable to send mail");
		} finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO, "AdminDAO", "sendPasswordMail", "Exited");
		return emailId;
	}

	/**
	 * This method is used to get all the states.
	 * 
	 * @return ArrayList
	 * @throws DatabaseException
	 * @roseuid 399E65A60356
	 */
	public ArrayList getAllStates() throws DatabaseException {
		Connection connection = DBConnection.getConnection();

		ArrayList statesList = new ArrayList();

		try {

			CallableStatement callable = connection.prepareCall("{call PKGZSDRFUNCGETALLSTATES(?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);

			callable.registerOutParameter(2, Types.VARCHAR);

			callable.execute();

			int functionReturn = callable.getInt(1);

			String error = callable.getString(2);

			if (functionReturn == Constants.FUNCTION_FAILURE) {

				connection.rollback();

				callable.close();

				callable = null;

				throw new DatabaseException(error);

			} else {
				ResultSet result = callable.executeQuery();

				while (result.next()) {
					String state = result.getString(2);
					statesList.add(state);
					if (statesList.size() == 0) {
						LogClass.StepWritter(
								"Null stateslist in getAllStates method in 'AdminDAO LINE number==2872=='");
					}
				}
				result.close();
				result = null;
			}

			callable.close();
			callable = null;

		} catch (SQLException e) {

			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "getAllStates", ignore.getMessage());
			}

			Log.log(Log.ERROR, "AdminDAO", "getAllStates", e.getMessage());

			Log.logException(e);

			throw new DatabaseException("Unable to get all states");

		} finally {

			DBConnection.freeConnection(connection);
		}
		if (statesList.size() == 0) {
			LogClass.StepWritter("Null stateslist in getAllStates method in 'AdminDAO LINE number==2903=='");
		}
		return statesList;
	}

	// The below line of code added by parmanand start
	public ArrayList getAllMliTypes() throws DatabaseException {
		Connection con = DBConnection.getConnection();
		ArrayList mliTypesList = new ArrayList();
		try {
			Statement stmt = null;
			String query = "SELECT MLI_TYPE FROM ECLGS_MLI_TYPE ORDER BY MLI_TYPE";
			try {
				stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				while (rs.next()) {
					mliTypesList.add(rs.getString("MLI_TYPE"));
					//System.out.println("mliTypesList size doa---show mli detail ~~~~~~-----" + mliTypesList.size());
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (stmt != null) {
					stmt.close();
				}
			}
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "getAllMLITypes", ignore.getMessage());
			}

			Log.log(Log.ERROR, "AdminDAO", "getAllMLITypes", e.getMessage());
			//System.out.println("ERROR1==" + e.getMessage());

			Log.logException(e);
			//System.out.println("ERROR1==" + e);

			throw new DatabaseException("Unable to get all All Mli Types");

		} finally {
			DBConnection.freeConnection(con);
		}
		if (mliTypesList.size() == 0) {
			LogClass.StepWritter("Null stateslist in getMliTypes method in 'AdminDAO LINE number==2904=='");
		}
		return mliTypesList;
	}

	// The below line of code added by parmanand End

	public ArrayList getMemberList(String bankId) throws DatabaseException {
		Connection connection = DBConnection.getConnection();

		ArrayList<String> memberList = new ArrayList();

		try {

			CallableStatement callable = connection.prepareCall("{call PKGGETALLMEMIDSFUNCGETALLMEMBERIDS(?,?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);

			callable.setString(2, bankId);

//                         callable.registerOutParameter(3,Constants.CURSOR);

			callable.registerOutParameter(3, Types.VARCHAR);

			callable.execute();

			int functionReturn = callable.getInt(1);

			String error = callable.getString(3);

			if (functionReturn == Constants.FUNCTION_FAILURE) {

				connection.rollback();

				callable.close();

				callable = null;

				throw new DatabaseException(error);

			} else {
				ResultSet result = callable.executeQuery();

				while (result.next()) {
					String member = result.getString(1);
					memberList.add(member);
				}
				result.close();
				result = null;
			}

			callable.close();
			callable = null;

		} catch (SQLException e) {

			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "getMemberList", ignore.getMessage());
			}

			Log.log(Log.ERROR, "AdminDAO", "getMemberList", e.getMessage());

			Log.logException(e);

			throw new DatabaseException("Unable to get all Members");

		} finally {

			DBConnection.freeConnection(connection);
		}

		return memberList;
	}

	/**
	 * This method is used to get all the regions.
	 * 
	 * @return ArrayList
	 * @throws DatabaseException
	 * @roseuid 399E65A60356
	 */
	public ArrayList getAllRegions() throws DatabaseException {
		Connection connection = DBConnection.getConnection();

		ArrayList regionList = new ArrayList();

		try {

			CallableStatement callable = connection
					.prepareCall("{?=call packGetAllZneRgnStateDist.funcGetAllRegions (?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);

			callable.registerOutParameter(2, Constants.CURSOR);

			callable.registerOutParameter(3, Types.VARCHAR);

			callable.execute();

			int functionReturn = callable.getInt(1);

			String error = callable.getString(3);

			if (functionReturn == Constants.FUNCTION_FAILURE) {

				connection.rollback();

				callable.close();

				callable = null;

				throw new DatabaseException(error);

			} else {
				ResultSet result = (ResultSet) callable.getObject(2);

				while (result.next()) {

					String region = result.getString(1);

					regionList.add(region);
				}

				result.close();
				result = null;
			}

			callable.close();
			callable = null;

		} catch (SQLException e) {

			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "getAllRegions", ignore.getMessage());
			}

			Log.log(Log.ERROR, "AdminDAO", "getAllRegions", e.getMessage());

			Log.logException(e);

			throw new DatabaseException("Unable to get all regions");

		} finally {
			DBConnection.freeConnection(connection);
		}

		return regionList;
	}

	/**
	 * This method is used to get all the districts for the given state.
	 * 
	 * @param state
	 * @return ArrayList
	 * @throws DatabaseException
	 * @roseuid 399E65BF0352
	 */
	public ArrayList getAllDistricts(String state) throws DatabaseException {
		Connection connection = DBConnection.getConnection(false);

		ArrayList districtList = new ArrayList();

		try {
			CallableStatement callable = connection.prepareCall("{call PKGZSDRFUNCGETALLDISTRICTFORSTATE(?,?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);

			callable.setString(2, state);
			callable.registerOutParameter(3, Types.VARCHAR);

			callable.execute();

			int functionReturn = callable.getInt(1);

			String error = callable.getString(3);

			if (functionReturn == Constants.FUNCTION_FAILURE) {

				connection.rollback();

				callable.close();

				callable = null;

				throw new DatabaseException(error);

			} else {
				ResultSet result = callable.executeQuery();

				while (result.next()) {

					String district = result.getString(1);
					// //System.out.println("district:"+district);
					districtList.add(district);
				}

				result.close();
				result = null;
			}
		} catch (SQLException e) {

			LogClass.StepWritter("Exception in getAllDistricts" + e.getMessage());
			LogClass.writeExceptionOnFile(e);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "getAllDistricts", ignore.getMessage());
			}

			Log.log(Log.ERROR, "AdminDAO", "getAllDistricts", e.getMessage());

			Log.logException(e);

			throw new DatabaseException("Unable to get all districts");

		} finally {

			DBConnection.freeConnection(connection);
		}

		return districtList;

	}

	/**
	 * 
	 * @param cityName
	 * @return
	 * @throws com.cgtsi.common.DatabaseException
	 */
	public ArrayList getAllCities(String cityName) throws DatabaseException {
		//System.out.println(" getAllCities Entered:");
		Connection connection = DBConnection.getConnection(false);
		ArrayList cityList = new ArrayList();

		try {
			CallableStatement callable = connection
					.prepareCall("{?=call packGetAllZneRgnStateDist.funcGetAllCityListforCity (?,?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);
			callable.setString(2, cityName);
			//System.out.println("City Name:" + cityName);
			callable.registerOutParameter(3, Constants.CURSOR);
			callable.registerOutParameter(4, Types.VARCHAR);
			callable.execute();
			int functionReturn = callable.getInt(1);
			String error = callable.getString(4);

			if (functionReturn == Constants.FUNCTION_FAILURE) {
				connection.rollback();
				callable.close();
				callable = null;
				throw new DatabaseException(error);
			} else {
				ResultSet cityNames = (ResultSet) callable.getObject(3);
				while (cityNames.next()) {

					String city = cityNames.getString(1);
					//System.out.println("City:" + city);
					cityList.add(city);
				}
				cityNames.close();
				cityNames = null;
			}
		} catch (SQLException e) {

			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "getAllCities", ignore.getMessage());
			}

			Log.log(Log.ERROR, "AdminDAO", "getAllCities", e.getMessage());

			Log.logException(e);

			throw new DatabaseException("Unable to get all districts");

		} finally {

			DBConnection.freeConnection(connection);
		}
		//System.out.println("cityList size :" + cityList.size());

		return cityList;

	}

	/**
	 * 
	 * @param cityName
	 * @return
	 * @throws com.cgtsi.common.DatabaseException
	 */
	public ArrayList getAllCitiesNew(String cityName) throws DatabaseException {
		//System.out.println(" getAllCities Entered:");

		Connection connection = DBConnection.getConnection(false);
		PreparedStatement pStmt = null;
		ResultSet rsSet;
		ArrayList cityList = new ArrayList();
		try {

			String query = "SELECT UNIQUE SSI_CITY FROM SSI_DETAIL WHERE UPPER(SSI_CITY) LIKE UPPER(?)";
			pStmt = connection.prepareStatement(query);
			pStmt.setString(1, cityName + "%");
			rsSet = pStmt.executeQuery();
			while (rsSet.next()) {
				String city = rsSet.getString(1);
				cityList.add(city);
			}
			rsSet.close();
			pStmt.close();

		} catch (SQLException e) {

			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "getAllCities", ignore.getMessage());
			}

			Log.log(Log.ERROR, "AdminDAO", "getAllCities", e.getMessage());

			Log.logException(e);

			throw new DatabaseException("Unable to get all Cities");

		} finally {

			DBConnection.freeConnection(connection);
		}
		//System.out.println("cityList size :" + cityList.size());

		return cityList;

	}

	public ArrayList getAllZones() throws DatabaseException {
		Connection connection = DBConnection.getConnection(false);
		ArrayList zoneList = new ArrayList();
		try {

			// Get all the zones from the database.
			CallableStatement callable = connection
					.prepareCall("{?=call packGetAllZneRgnStateDist.funcGetAllZones (?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);

			callable.registerOutParameter(2, Constants.CURSOR);

			callable.registerOutParameter(3, Types.VARCHAR);

			callable.execute();

			// If error status is 1 throw database exception.

			int functionReturn = callable.getInt(1);

			String error = callable.getString(3);

			if (functionReturn == Constants.FUNCTION_FAILURE) {

				connection.rollback();

				callable.close();

				callable = null;

				throw new DatabaseException(error);

			} else {
				ResultSet result = (ResultSet) callable.getObject(2);

				while (result.next()) {
					String zone = result.getString(1);
					zoneList.add(zone);
				}
				result.close();
				result = null;
			}

			callable.close();
			callable = null;

		} catch (SQLException e) {

			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "getAllZones", ignore.getMessage());
			}

			Log.log(Log.ERROR, "AdminDAO", "getAllZones", e.getMessage());

			Log.logException(e);

			throw new DatabaseException("Unable to get all zones");

		} finally {

			DBConnection.freeConnection(connection);
		}

		return zoneList;

	}

	/**
	 * This method is used to get all the designations
	 * 
	 * @return ArrayList
	 * @throws DatabaseException
	 * @roseuid 399E6A5000D2
	 */
	public ArrayList getAllDesignations() throws DatabaseException
    {
                 Connection connection=DBConnection.getConnection(false);

                  ArrayList designations=new ArrayList();

                  try{
                                 CallableStatement callable=connection.prepareCall
                                                 ("{call PKGALLGESFUNCGETALLDESIGNATIONS (?,?)}");

                                 callable.registerOutParameter(1,Types.INTEGER);

//                                 callable.registerOutParameter(2,Constants.CURSOR);

                                 callable.registerOutParameter(2,Types.VARCHAR);

                                 callable.execute();

                                 int functionReturn=callable.getInt(1);

                                 String error=callable.getString(2);

                                 if(functionReturn==Constants.FUNCTION_FAILURE){

                                         connection.rollback();

                                         callable.close();

                                         callable=null;

                                         throw new DatabaseException(error);

                                 }
                                 else{
                                                 ResultSet result=callable.executeQuery();

                                                 while(result.next()){

                                                           String designation=result.getString(1);

                                                           designations.add(designation);
                                                  }

                                                  result.close();
                                                  result=null;
                                   }
                         callable.close();
                         callable=null;
//                         connection.commit();

                          }catch (SQLException e) {

                                         try {
                                           connection.rollback();
                                         }
                                         catch (SQLException ignore) {
                                                 Log.log(Log.ERROR,"AdminDAO","getAllDesignations",ignore.getMessage());
                                         }

                                         Log.log(Log.ERROR,"AdminDAO","getAllDesignations",e.getMessage());

                                         Log.logException(e);

                                         throw new DatabaseException("Unable to get all designations");


                   }finally{

                                 DBConnection.freeConnection(connection);
                     }

                 return designations;
            }

	/**
	 * This method is used to deactivate a member. All the users under this member
	 * is deactivated.
	 * 
	 * @param bankID
	 * @param zoneId
	 * @param branchID
	 * @throws DatabaseException
	 * @roseuid 399F5CC200BE
	 */
	public void deactivateMember(String bankId, String zoneId, String branchId, String userId, String reason)
			throws DatabaseException {
		Connection connection = DBConnection.getConnection();

		try {
			CallableStatement callable = connection.prepareCall("{?=call funcDeActivateMember(?,?,?,?,?,?)}");

			callable.setString(2, bankId);
			callable.setString(3, zoneId);
			callable.setString(4, branchId);
			callable.setString(5, reason);
			callable.setString(6, userId);

			// The error status and error message are retrieved from the database.
			callable.registerOutParameter(1, Types.INTEGER);
			callable.registerOutParameter(7, Types.VARCHAR);

			callable.execute();

			// If error status is 1 throw database exception.

			int functionReturn = callable.getInt(1);

			String error = callable.getString(7);

			if (functionReturn == Constants.FUNCTION_FAILURE) {

				connection.rollback();

				callable.close();

				callable = null;

				throw new DatabaseException(error);

			}
			callable.close();

			callable = null;

			connection.commit();

		} catch (SQLException e) {

			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "deactivateMember", ignore.getMessage());
			}

			Log.log(Log.ERROR, "AdminDAO", "deactivateMember", e.getMessage());

			Log.logException(e);

			throw new DatabaseException("Unable to deactivate member");

		} finally {
			DBConnection.freeConnection(connection);
		}

	}

	/**
	 * This method is used to reactivate a member. All the users under this member
	 * is reactivated.
	 * 
	 * @param bankId
	 * @param zoneId
	 * @param branchId
	 * @throws DatabaseException
	 * @roseuid 399F5D6802A7
	 */
	public void reactivateMember(String bankId, String zoneId, String branchId, String userId, String reason)
			throws DatabaseException {
		Connection connection = DBConnection.getConnection(false);

		try {
			CallableStatement callable = connection.prepareCall("{?=call funcReActivateMember(?,?,?,?,?,?)}");

			callable.setString(2, bankId);
			callable.setString(3, zoneId);
			callable.setString(4, branchId);
			callable.setString(5, reason);
			callable.setString(6, userId);

			// The error status and error message are retrieved from the database.
			callable.registerOutParameter(1, Types.INTEGER);
			callable.registerOutParameter(7, Types.VARCHAR);

			callable.execute();

			// If error status is 1 throw database exception.
			int functionReturn = callable.getInt(1);

			String error = callable.getString(7);

			if (functionReturn == Constants.FUNCTION_FAILURE) {

				connection.rollback();

				callable.close();

				callable = null;

				throw new DatabaseException(error);

			}
			callable.close();
			callable = null;

			connection.commit();

		} catch (SQLException e) {

			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "reactivateMember", ignore.getMessage());
			}

			Log.log(Log.ERROR, "AdminDAO", "reactivateMember", e.getMessage());

			Log.logException(e);

			throw new DatabaseException("Unable to reactivate member");

		} finally {
			DBConnection.freeConnection(connection);
		}
	}

	/**
	 * This method is used to get all the message available for the given user id.
	 * 
	 * @param userId
	 * @return String
	 * @throws DatabaseException
	 * @roseuid 399F60C600B2
	 */
	public String getMessage(String userId) throws DatabaseException {
		Log.log(Log.INFO, "AdminDAO", "getMessage", "Entered");
		Connection connection = DBConnection.getConnection(false);
		String message = null;

		try {

			CallableStatement callable = connection.prepareCall("{call FUNCGETMSGFORUSER(?,?,?,?)}");
			// The user Id for whom the message is to be retrieved is passed to the
			// database.
			callable.setString(2, userId);

			// The error status,message for user and error message are retrieved from the
			// database.
			callable.registerOutParameter(1, Types.INTEGER);
			callable.registerOutParameter(3, Types.VARCHAR);
			callable.registerOutParameter(4, Types.VARCHAR);

			callable.execute();
			// If error status is 1 throw database exception.

			String error = callable.getString(4);
			int errorCode = callable.getInt(1);

			Log.log(Log.DEBUG, "AdminDAO", "getMessage", "Error code and error " + errorCode + " " + error);

			if (errorCode == Constants.FUNCTION_FAILURE) {

				connection.rollback();
				callable.close();
				callable = null;

				Log.log(Log.ERROR, "AdminDAO", "getMessage", error);
				throw new DatabaseException(error);
			}
			// the message for the user id is passed to the String "message".
			message = callable.getString(3);

			Log.log(Log.DEBUG, "AdminDAO", "getMessage", "message is " + message);

		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "getMessage", ignore.getMessage());
			}
			Log.log(Log.ERROR, "AdminDAO", "getMessage", e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Unable to get Message for user ");
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(Log.INFO, "AdminDAO", "getMessage", "Exited");
		return message;

	}

	/**
	 * This method is update the states master with the available state information.
	 * 
	 * @param stateMaster
	 * @throws DatabaseException
	 * @roseuid 39AF6E0A036E
	 */
	public void updateStateMaster(StateMaster stateMaster, String createdBy) throws DatabaseException {
		Connection connection = DBConnection.getConnection(false);

		try {

			CallableStatement callable = connection.prepareCall("{?=call funcInsertState(?,?,?,?)}");

			// The state name for the region is passed along with the userId creating it.

			callable.setString(2, stateMaster.getStateName());

			callable.setString(3, stateMaster.getStateCode());

			callable.setString(4, createdBy);

			// The error status and error message are sent from the database.
			callable.registerOutParameter(1, Types.INTEGER);
			callable.registerOutParameter(5, Types.VARCHAR);

			callable.execute();

			// If error status is 1 throw database exception.

			int functionReturn = callable.getInt(1);

			String error = callable.getString(5);

			if (functionReturn == Constants.FUNCTION_FAILURE) {

				connection.rollback();

				callable.close();

				callable = null;

				throw new DatabaseException(error);

			}

			callable.close();
			callable = null;

			connection.commit();

		} catch (SQLException e) {

			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "updateStateMaster", ignore.getMessage());
			}

			Log.log(Log.ERROR, "AdminDAO", "updateStateMaster", e.getMessage());

			Log.logException(e);

			throw new DatabaseException("Unable to update state master");

		} finally {
			DBConnection.freeConnection(connection);
		}
	}

	/**
	 * This method is used to get all the alerts available in the database.
	 * 
	 * @return ArrayList
	 * @throws DatabaseException
	 * @roseuid 39AF73C40177
	 */
	public ArrayList getAllAlerts() throws DatabaseException {
		Connection connection = DBConnection.getConnection(false);

		ArrayList alertsList = new ArrayList();

		try {
			CallableStatement callable = connection
					.prepareCall("{?=call packGetInsertUpdateAlerts.funcGetAllAlerts(?)}");

			callable.registerOutParameter(1, Types.INTEGER);
			callable.registerOutParameter(2, Constants.CURSOR);

			callable.execute();

			int functionReturn = callable.getInt(1);

			if (functionReturn == Constants.FUNCTION_FAILURE) {

				connection.rollback();

				callable.close();

				callable = null;

				throw new DatabaseException("error retrieving alerts");

			} else {
				ResultSet result = (ResultSet) callable.getObject(2);

				while (result.next()) {

					String alert = result.getString(1);

					alertsList.add(alert);
				}

				result.close();
				result = null;
			}

			callable.close();
			callable = null;
			connection.commit();

		} catch (SQLException e) {

			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "getAllAlerts", ignore.getMessage());
			}

			Log.log(Log.ERROR, "AdminDAO", "getAllAlerts", e.getMessage());

			Log.logException(e);

			throw new DatabaseException("Unable to retrieve alerts");

		} finally {

			DBConnection.freeConnection(connection);
		}

		return alertsList;
	}

	/**
	 * This method is used update the district information for a given state.
	 * 
	 * @param districtMaster
	 * @throws DatabaseException
	 * @roseuid 39AF749B0307
	 */
	public void updateDistrictMaster(DistrictMaster districtMaster, String createdBy) throws DatabaseException {
		Connection connection = DBConnection.getConnection(false);

		try {

			CallableStatement callable = connection.prepareCall("{?=call funcInsertDistrict(?,?,?,?)}");

			// The district name for the state is passed along with the userId creating it.

			callable.setString(2, districtMaster.getStateName());
			callable.setString(3, districtMaster.getDistrictName());
			callable.setString(4, createdBy);

			// The error status and error message are sent from the database.
			callable.registerOutParameter(1, Types.INTEGER);
			callable.registerOutParameter(5, Types.VARCHAR);

			callable.execute();

			// If error status is 1 throw database exception.

			int functionReturn = callable.getInt(1);

			String error = callable.getString(5);

			if (functionReturn == Constants.FUNCTION_FAILURE) {

				connection.rollback();

				callable.close();

				callable = null;

				throw new DatabaseException(error);

			}

			callable.close();
			callable = null;

			connection.commit();

		} catch (SQLException e) {

			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "updateDistrictMaster", ignore.getMessage());
			}

			Log.log(Log.ERROR, "AdminDAO", "updateDistrictMaster", e.getMessage());

			Log.logException(e);

			throw new DatabaseException("Unable to update district master");

		} finally {
			DBConnection.freeConnection(connection);
		}

	}

	/**
	 * This method is used to update the alerts information in the database.
	 * 
	 * @param alertMaster
	 * @throws DatabaseException
	 * @roseuid 39AF74AA0290
	 */
	public void updateAlertMaster(AlertMaster alertMaster) throws DatabaseException {
		Connection connection = DBConnection.getConnection(false);

		try {
			if ((alertMaster.getNewAlert() != null) && !(alertMaster.getNewAlert().equals(""))) {
				CallableStatement callable = connection
						.prepareCall("{?=call packGetInsertUpdateAlerts.funcInsertAlert(?,?,?,?)}");
				// The alert title,messages and creating user are passed to the database.

				callable.setString(2, alertMaster.getCreatedBy());
				callable.setString(3, alertMaster.getNewAlert());
				callable.setString(4, alertMaster.getAlertMessage());

				// The error status and error message are sent from the database.
				callable.registerOutParameter(1, Types.INTEGER);
				callable.registerOutParameter(5, Types.VARCHAR);

				callable.execute();

				// If error status is 1 throw database exception.

				int functionReturn = callable.getInt(1);

				String error = callable.getString(5);

				if (functionReturn == Constants.FUNCTION_FAILURE) {

					connection.rollback();

					callable.close();

					callable = null;

					throw new DatabaseException(error);
				}

			} else {
				CallableStatement callable = connection
						.prepareCall("{?=call packGetInsertUpdateAlerts.funcUpdateAlert (?,?,?,?)}");
				// The Alert message is updated for the alert title.

				callable.setString(2, alertMaster.getCreatedBy());
				callable.setString(3, alertMaster.getAlertTitle());
				callable.setString(4, alertMaster.getAlertMessage());

				// The error status and error message are sent from the database.
				callable.registerOutParameter(1, Types.INTEGER);
				callable.registerOutParameter(5, Types.VARCHAR);

				callable.execute();

				// If error status is 1 throw database exception.

				int functionReturn = callable.getInt(1);

				String error = callable.getString(5);

				if (functionReturn == Constants.FUNCTION_FAILURE) {

					connection.rollback();

					callable.close();

					callable = null;

					throw new DatabaseException(error);
				}

			}
		} catch (SQLException e) {

			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "updateAlertMaster", ignore.getMessage());
			}

			Log.log(Log.ERROR, "AdminDAO", "updateAlertMaster", e.getMessage());

			Log.logException(e);

			throw new DatabaseException("Unable to update alert master");

		} finally {

			DBConnection.freeConnection(connection);
		}
	}

	/**
	 * This method is used to get all the exception messages available in the
	 * database.
	 * 
	 * @return ArrayList
	 * @throws DatabaseException
	 * @roseuid 39AF759603A8
	 */
	public ArrayList getAllExceptionMessages() throws DatabaseException {
		Connection connection = DBConnection.getConnection(false);

		ArrayList exceptionList = new ArrayList();

		try {
			CallableStatement callable = connection
					.prepareCall("{?=call packGetInsertUpdateErrors.funcGetAllErrors(?)}");

			callable.registerOutParameter(1, Types.INTEGER);
			callable.registerOutParameter(2, Constants.CURSOR);

			callable.execute();

			int functionReturn = callable.getInt(1);

			if (functionReturn == Constants.FUNCTION_FAILURE) {

				connection.rollback();

				callable.close();

				callable = null;

				throw new DatabaseException();

			} else {
				ResultSet result = (ResultSet) callable.getObject(2);

				while (result.next()) {

					String exception = result.getString(1);

					exceptionList.add(exception);
				}
				result.close();
				result = null;
			}

			callable.close();
			callable = null;
			connection.commit();

		} catch (SQLException e) {

			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "getAllExceptionMessages", ignore.getMessage());
			}

			Log.log(Log.ERROR, "AdminDAO", "getAllExceptionMessages", e.getMessage());

			Log.logException(e);

			throw new DatabaseException("Unable to get all exception messages");

		} finally {
			DBConnection.freeConnection(connection);
		}

		return exceptionList;
	}

	/**
	 * This method is used to update the exception messages into database.
	 * 
	 * @param exceptionMsgMaster
	 * @roseuid 39AF75A003C1
	 */
	public void updateExceptionMaster(ExceptionMaster exceptionMaster) throws DatabaseException {
		Connection connection = DBConnection.getConnection(false);
		try {
			if ((exceptionMaster.getNewExceptionTitle() != null)
					&& !(exceptionMaster.getNewExceptionTitle().equals(""))) {
				CallableStatement callable = connection
						.prepareCall("{?=call packGetInsertUpdateErrors.funcInsertError(?,?,?,?)}");
				// The error title,messages and type are passed into the database.

				callable.setString(2, exceptionMaster.getExceptionType());
				callable.setString(3, exceptionMaster.getNewExceptionTitle());
				callable.setString(4, exceptionMaster.getExceptionMessage());

				// The error status and error message are sent from the database.
				callable.registerOutParameter(1, Types.INTEGER);
				callable.registerOutParameter(5, Types.VARCHAR);

				callable.execute();

				// If error status is 1 throw database exception.

				int functionReturn = callable.getInt(1);

				String error = callable.getString(5);

				if (functionReturn == Constants.FUNCTION_FAILURE) {

					connection.rollback();

					callable.close();

					callable = null;

					throw new DatabaseException(error);
				}
			} else {
				CallableStatement callable = connection
						.prepareCall("{?=call packGetInsertUpdateErrors.funcUpdateError(?,?,?,?)}");
				// The state name for the region is passed along with the userId creating it.

				// The error title,messages and type are updated into the database.

				callable.setString(2, exceptionMaster.getExceptionType());
				callable.setString(3, exceptionMaster.getExceptionTitle());
				callable.setString(4, exceptionMaster.getExceptionMessage());

				// The error status and error message are sent from the database.
				callable.registerOutParameter(1, Types.INTEGER);
				callable.registerOutParameter(5, Types.VARCHAR);

				callable.execute();

				// If error status is 1 throw database exception.

				int functionReturn = callable.getInt(1);

				String error = callable.getString(5);

				if (functionReturn == Constants.FUNCTION_FAILURE) {

					connection.rollback();

					callable.close();

					callable = null;

					throw new DatabaseException(error);
				}
				callable.close();

				callable = null;
			}

		} catch (SQLException e) {

			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "updateExceptionMaster", ignore.getMessage());
			}

			Log.log(Log.ERROR, "AdminDAO", "updateExceptionMaster", e.getMessage());

			Log.logException(e);

			throw new DatabaseException("Unable to update exception master");
		} finally {

			DBConnection.freeConnection(connection);
		}

	}

	/**
	 * This method is used to update the designation into the database.
	 * 
	 * @param desigMaster
	 * @throws DatabaseException
	 * @roseuid 39AF77C903D0
	 */
	public void updateDesignationMaster(DesignationMaster desigMaster) throws DatabaseException {

		Connection connection = DBConnection.getConnection(false);
		try {
			if ((desigMaster.getNewDesigName() != null) && !(desigMaster.getNewDesigName().equals(""))) {
				CallableStatement callable = connection
						.prepareCall("{?=call packGetAllDesignations.funcInsertDesignation(?,?,?)}");
				Log.log(Log.DEBUG, "updateDesignationMaster", "funcInsertDesignation", "funcInsertDesignation");

				callable.setString(2, desigMaster.getNewDesigName());
				callable.setString(3, desigMaster.getDesigDesc());

				// The error status and error message are sent from the database.
				callable.registerOutParameter(1, Types.INTEGER);
				callable.registerOutParameter(4, Types.VARCHAR);

				callable.execute();

				// If error status is 1 throw database exception.

				int functionReturn = callable.getInt(1);

				String error = callable.getString(4);

				if (functionReturn == Constants.FUNCTION_FAILURE) {

					connection.rollback();
					callable.close();
					callable = null;
					throw new DatabaseException(error);
				}
			} else {
				CallableStatement callable = connection
						.prepareCall("{?=call packGetAllDesignations.funcUpdateDesignation(?,?,?)}");

				Log.log(Log.DEBUG, "updateDesignationMaster", "funcUpdateDesignation", "funcUpdateDesignation");
				callable.setString(2, desigMaster.getDesigName());
				callable.setString(3, desigMaster.getDesigDesc());

				// The error status and error message are sent from the database.
				callable.registerOutParameter(1, Types.INTEGER);
				callable.registerOutParameter(4, Types.VARCHAR);

				callable.execute();

				// If error status is 1 throw database exception.

				int functionReturn = callable.getInt(1);

				String error = callable.getString(4);

				if (functionReturn == Constants.FUNCTION_FAILURE) {

					connection.rollback();

					callable.close();

					callable = null;

					throw new DatabaseException(error);
				}
			}

		} catch (SQLException e) {

			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "updateDesignationMaster", ignore.getMessage());
			}

			Log.log(Log.ERROR, "AdminDAO", "updateDesignationMaster", e.getMessage());

			Log.logException(e);

			throw new DatabaseException("Unable to update designation master");

		} finally {

			DBConnection.freeConnection(connection);
		}
	}

	/**
	 * This method is used to get all the parameter values into database.
	 * 
	 * @return com.cgtsi.admin.ParameterMaster
	 * @throws DatabaseException
	 * @roseuid 39AF78AC0368
	 */
	public ParameterMaster getParameter() throws DatabaseException {
		Log.log(Log.INFO, "AdminDAO", "getParameter", "Entered");

		Connection connection = DBConnection.getConnection(false);

		ParameterMaster paramMaster = new ParameterMaster();

		try {
			CallableStatement callable = connection.prepareCall("{call PKGPARADETLFUNCGETALLPARAMETER(?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);

			/* callable.registerOutParameter(2,Constants.CURSOR); */
			callable.registerOutParameter(2, Types.VARCHAR);

			callable.execute();

			int errorCode = callable.getInt(1);
			String error = callable.getString(2);

			Log.log(Log.DEBUG, "AdminDAO", "getParameter", "Error Code and Error are " + errorCode + " " + error);

			if (errorCode == Constants.FUNCTION_FAILURE) {

				connection.rollback();

				callable.close();
				callable = null;

				Log.log(Log.ERROR, "AdminDAO", "getParameter", error);
				throw new DatabaseException(error);
			}
			ResultSet params = callable.executeQuery();

			/* ResultSet params=(ResultSet)callable.getObject(2); */

			HashMap paramHash = new HashMap();

			while (params.next()) {
				String paramName = params.getString(1);

				String paramValue = params.getString(2);

				paramHash.put(paramName, paramValue);
			}
			params.close();
			params = null;

			callable.close();
			callable = null;

			String userLimit = (String) paramHash.get(Parameters.ACTIVE_USER_LIMIT);

			if (userLimit != null) {
				paramMaster.setActiveUserLimit(Integer.parseInt(userLimit));
			}

			String passwordExpiry = (String) paramHash.get(Parameters.PASSWORD_EXPIRY);

			if (passwordExpiry != null) {
				paramMaster.setPasswordExpiryDays(Integer.parseInt(passwordExpiry));
			}
			String passwordDisplay = (String) paramHash.get(Parameters.PASSWORD_DISPLAY_PERIOD);

			if (passwordDisplay != null) {
				paramMaster.setPasswordDisplayPeriod(Integer.parseInt(passwordDisplay));
			}

			String wcTenor = (String) paramHash.get(Parameters.WORKING_CAPITAL_TENOR);

			if (wcTenor != null) {
				paramMaster.setWcTenorInYrs(Integer.parseInt(wcTenor));
			}
			String maxIntRate = (String) paramHash.get(Parameters.MAXIMUM_INTEREST_RATE);

			if (maxIntRate != null) {
				paramMaster.setMaxIntRateApplied(Double.parseDouble(maxIntRate));
			}
			String maxApprovedAmt = (String) paramHash.get(Parameters.MAXIMUM_APPROVED_AMOUNT);

			if (maxApprovedAmt != null) {
				paramMaster.setMaxApprovedAmt(Double.parseDouble(maxApprovedAmt));
			}
			String firstInstallPerc = (String) paramHash.get(Parameters.FIRST_INSTALLMENT_PERCENTAGE);

			if (firstInstallPerc != null) {
				paramMaster.setFirstInstallClaim(Double.parseDouble(firstInstallPerc));
			}
			String lockInPeriod = (String) paramHash.get(Parameters.LOCK_IN_PERIOD);

			if (lockInPeriod != null) {
				paramMaster.setLockInPeriod(Integer.parseInt(lockInPeriod));
			}
			String minITPAN = (String) paramHash.get(Parameters.MIN_AMOUNT_FOR_MANDATORY_ITPAN);

			if (minITPAN != null) {
				paramMaster.setMinAmtForMandatoryITPAN(Double.parseDouble(minITPAN));
			}
			String claimSettlementNoPenalty = (String) paramHash.get(Parameters.CLAIM_SETTLEMENT_WITHOUT_PENALTY);

			if (claimSettlementNoPenalty != null) {
				paramMaster.setClaimSettlementWithoutPenalty(Integer.parseInt(claimSettlementNoPenalty));
			}

			String serviceFeeRate = (String) paramHash.get(Parameters.SERVICE_FEE_RATE);

			if (serviceFeeRate != null) {
				paramMaster.setServiceFeeRate(Double.parseDouble(serviceFeeRate));
			}
			String serviceFeeNoPenalty = (String) paramHash.get(Parameters.SERVICE_FEE_WITHOUT_PENALTY);

			if (serviceFeeNoPenalty != null) {
				paramMaster.setServiceFeeWithoutPenalty(Integer.parseInt(serviceFeeNoPenalty));
			}
			String penaltyRate = (String) paramHash.get(Parameters.SERVICE_FEE_PENALTY_RATE);

			if (penaltyRate != null) {
				paramMaster.setServiceFeePenaltyRate(Double.parseDouble(penaltyRate));
			}
			String noCGPANs = (String) paramHash.get(Parameters.NO_CGPANS_PER_DAN);

			if (noCGPANs != null) {
				paramMaster.setNoOfCgpansPerDAN(Integer.parseInt(noCGPANs));
			}
			String cgNoPenalty = (String) paramHash.get(Parameters.GF_WITHOUT_PENALTY);

			if (cgNoPenalty != null) {
				paramMaster.setGuaranteeFeeWithoutPenalty(Integer.parseInt(cgNoPenalty));
			}
			String shortLimit = (String) paramHash.get(Parameters.SHORT_PAYMENT_LIMIT);

			if (shortLimit != null) {
				paramMaster.setShortLimit(Double.parseDouble(shortLimit));
			}

			String excessLimit = (String) paramHash.get(Parameters.EXCESS_PAYMENT_LIMIT);

			if (excessLimit != null) {
				paramMaster.setExcessLimit(Double.parseDouble(excessLimit));
			}
			String waiveLimit = (String) paramHash.get(Parameters.WAIVE_LIMIT);

			if (waiveLimit != null) {
				paramMaster.setWaiveLimit(Double.parseDouble(waiveLimit));
			}
			String gfFirstAlert = (String) paramHash.get(Parameters.GF_FIRST_ALERT_DAYS);

			if (gfFirstAlert != null) {
				paramMaster.setGuaranteeFeeFirstAlert(Integer.parseInt(gfFirstAlert));
			}
			String gfAlertFreq = (String) paramHash.get(Parameters.GF_ALERT_FREQUENCY);

			if (gfAlertFreq != null) {
				paramMaster.setGuaranteeFeeAlertFrequency(Integer.parseInt(gfAlertFreq));
			}
			String calculationDay = (String) paramHash.get(Parameters.SERVICE_FEE_CALCULATION_DAY);

			if (calculationDay != null) {
				paramMaster.setServiceFeeCalculationDay(Integer.parseInt(calculationDay));
			}
			String calculationMonth = (String) paramHash.get(Parameters.SERVICE_FEE_CALCULATION_MONTH);

			if (calculationMonth != null) {
				paramMaster.setServiceFeeCalculationMonth(calculationMonth);
			}
			String serviceFeeFreq = (String) paramHash.get(Parameters.SERVICE_FEE_ALERT_FREQUENCY);

			if (serviceFeeFreq != null) {
				paramMaster.setServiceFeeAlertFreq(Integer.parseInt(serviceFeeFreq));
			}

			String periodicFreq = (String) paramHash.get(Parameters.PERIODIC_FREQUENCY);

			if (periodicFreq != null) {
				paramMaster.setPeriodicInfoFrequency(periodicFreq);
			}
			String peridicInfoAlertFreq = (String) paramHash.get(Parameters.PERIODIC_ALERT_FREQUENCY);

			if (peridicInfoAlertFreq != null) {
				paramMaster.setPeriodicInfoAlertFreq(Integer.parseInt(peridicInfoAlertFreq));
			}
			String bankRate = (String) paramHash.get(Parameters.BANK_RATE);

			if (bankRate != null) {
				paramMaster.setBankRate(Double.parseDouble(bankRate));
			}

			String gfPenaltyRate = (String) paramHash.get(Parameters.GF_PENALTY_RATE);

			if (gfPenaltyRate != null) {
				paramMaster.setGuaranteeFeePenaltyRate(Double.parseDouble(gfPenaltyRate));
			}
			String claimPenaltyRate = (String) paramHash.get(Parameters.CLAIM_PENALTY_RATE);

			if (claimPenaltyRate != null) {
				paramMaster.setClaimPenaltyRate(Double.parseDouble(claimPenaltyRate));
			}

			String penaltyCalculationType = (String) paramHash.get(Parameters.PENALTY_CALCULATION_TYPE);

			if (penaltyCalculationType != null) {
				paramMaster.setPenaltyCalculationType(penaltyCalculationType);
			}

			String mcgfServiceFee = (String) paramHash.get(Parameters.MCGF_SERVICE_FEE);

			if (mcgfServiceFee != null) {
				paramMaster.setMcgfServiceFee(mcgfServiceFee);
			}
			String thirdPartyGuarantee = (String) paramHash.get(Parameters.THIRD_PARTY_GUARANTEE_ALLOWED);

			if (thirdPartyGuarantee != null) {
				paramMaster.setThirdPartyGuarantee(thirdPartyGuarantee);
			}

			String collateralTaken = (String) paramHash.get(Parameters.COLLATERAL_SECURITY_ALLOWED);

			if (collateralTaken != null) {
				paramMaster.setCollateralTaken(collateralTaken);
			}
			// Added guarantee fee parameter on 08-06-2004.
			String guaranteeFee = (String) paramHash.get(Parameters.GUARANTEE_FEE);

			if (guaranteeFee != null) {
				paramMaster.setGuaranteeFee(Double.parseDouble(guaranteeFee));
			}
			// Added check expiry and cheque expiry warning parameters on 10-06-2004.
			String chequeExpiry = (String) paramHash.get(Parameters.CHEQUE_EXPIRY_IN_MONTHS);

			if (chequeExpiry != null) {
				paramMaster.setChequeExpiryInMonths(Integer.parseInt(chequeExpiry));
			}

			String chequeExpiryWarning = (String) paramHash.get(Parameters.CHEQUE_EXPIRY_WARNING_IN_DAYS);

			if (chequeExpiryWarning != null) {
				paramMaster.setChequeExpiryWarningInDays(Integer.parseInt(chequeExpiryWarning));
			}

			String mcgfGuaranteeFeePercentage = (String) paramHash.get(Parameters.MCGF_GUARANTEE_FEE_PERCENTAGE);
			if (mcgfGuaranteeFeePercentage != null) {
				paramMaster.setMcgfGuaranteeFeePercentage(Double.parseDouble(mcgfGuaranteeFeePercentage));
			}

			String applicationFilingTimeLimit = (String) paramHash.get(Parameters.APPLICATION_FILING_TIME_LIMIT);
			if (applicationFilingTimeLimit != null) {
				paramMaster.setApplicationFilingTimeLimit((Integer.parseInt(applicationFilingTimeLimit)));
			}

			String minimumSanctionedAmount = (String) paramHash.get(Parameters.MINIMUM_SANCTIONED_AMOUNT);
			if (minimumSanctionedAmount != null) {
				paramMaster.setMinimumSanctionedAmount(Double.parseDouble(minimumSanctionedAmount));
			}

			String isDefaultRateApplicable = (String) paramHash.get(Parameters.IS_DEFAULT_RATE_APPLICABLE);
			if (isDefaultRateApplicable != null) {
				paramMaster.setIsDefaultRateApplicable(isDefaultRateApplicable);
			}

			String defaultRate = (String) paramHash.get(Parameters.DEFAULT_RATE);
			if (defaultRate != null) {
				paramMaster.setDefaultRate(Double.parseDouble(defaultRate));
			}

			String moderationFactor = (String) paramHash.get(Parameters.MODERATION_FACTOR);
			if (moderationFactor != null) {
				paramMaster.setModerationFactor(Double.parseDouble(moderationFactor));
			}

			String cgtsiLiability = (String) paramHash.get(Parameters.CGTSI_LIABILITY);
			if (cgtsiLiability != null) {
				paramMaster.setCgtsiLiability(Double.parseDouble(cgtsiLiability));
			}

			String highValClearanceAmnt = (String) paramHash.get(Parameters.HIGH_VALUE_CLEARENCE_AMOUNT);
			if (highValClearanceAmnt != null) {
				paramMaster.setHighValClearanceAmnt(Double.parseDouble(highValClearanceAmnt));
			}

			String periodTenureExpiryLodgementClaims = (String) paramHash
					.get(Parameters.PERIOD_TENURE_EXPIRY_LODGEMENT_CLAIMS);
			if (periodTenureExpiryLodgementClaims != null) {
				paramMaster.setPeriodTenureExpiryLodgementClaims((Integer.parseInt(periodTenureExpiryLodgementClaims)));
			}
			String maxRsfApprovedAmt = (String) paramHash.get(Parameters.MAXIMUM_RSF_APPROVED_AMOUNT);

			if (maxRsfApprovedAmt != null) {
				paramMaster.setMaxRsfApprovedAmt(Double.parseDouble(maxRsfApprovedAmt));
			}
			String maxRsf2ApprovedAmt = (String) paramHash.get("MAXIMUM_RSF2_APPROVED_AMOUNT");

			if (maxRsf2ApprovedAmt != null) {
				paramMaster.setMaxRsf2ApprovedAmt(Double.parseDouble(maxRsf2ApprovedAmt));
			}
		} catch (SQLException e) {
			LogClass.StepWritter("Exception in getParameter " + e.getMessage());
			LogClass.writeExceptionOnFile(e);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "getParameter", ignore.getMessage());
			}

			Log.log(Log.ERROR, "AdminDAO", "getParameter", e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Unable to fetch Parameters.");
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(Log.INFO, "AdminDAO", "getParameter", "Exited");

		return paramMaster;
	}

	/**
	 * This method is used go update the ParameterMaster with the parameter values.
	 * 
	 * @param parameterMaster
	 * @throws DatabaseException
	 * @roseuid 39AF78C8008D
	 */
	public void updateParameter(ParameterMaster modifiedParamMaster, String userId) throws DatabaseException {
		Log.log(Log.INFO, "AdminDAO", "updateParameter", "Entered");

		ParameterMaster databaseParams = getParameter();

		// Check the for updated fields.

		if (modifiedParamMaster.getActiveUserLimit() != databaseParams.getActiveUserLimit()) {
			updateParam(userId, Parameters.ACTIVE_USER_LIMIT, String.valueOf(modifiedParamMaster.getActiveUserLimit()));
		}

		if (modifiedParamMaster.getBankRate() != databaseParams.getBankRate()) {
			updateParam(userId, Parameters.BANK_RATE, String.valueOf(modifiedParamMaster.getBankRate()));
		}

		if (modifiedParamMaster.getClaimPenaltyRate() != databaseParams.getClaimPenaltyRate()) {
			updateParam(userId, Parameters.CLAIM_PENALTY_RATE,
					String.valueOf(modifiedParamMaster.getClaimPenaltyRate()));
		}

		if (modifiedParamMaster.getClaimSettlementWithoutPenalty() != databaseParams
				.getClaimSettlementWithoutPenalty()) {
			updateParam(userId, Parameters.CLAIM_SETTLEMENT_WITHOUT_PENALTY,
					String.valueOf(modifiedParamMaster.getClaimSettlementWithoutPenalty()));
		}

		if (modifiedParamMaster.getExcessLimit() != databaseParams.getExcessLimit()) {
			updateParam(userId, Parameters.EXCESS_PAYMENT_LIMIT, String.valueOf(modifiedParamMaster.getExcessLimit()));
		}

		if (modifiedParamMaster.getFirstInstallClaim() != databaseParams.getFirstInstallClaim()) {
			updateParam(userId, Parameters.FIRST_INSTALLMENT_PERCENTAGE,
					String.valueOf(modifiedParamMaster.getFirstInstallClaim()));
		}
		if (modifiedParamMaster.getGuaranteeFeeAlertFrequency() != databaseParams.getGuaranteeFeeAlertFrequency()) {
			updateParam(userId, Parameters.GF_ALERT_FREQUENCY,
					String.valueOf(modifiedParamMaster.getGuaranteeFeeAlertFrequency()));
		}

		if (modifiedParamMaster.getGuaranteeFeeFirstAlert() != databaseParams.getGuaranteeFeeFirstAlert()) {
			updateParam(userId, Parameters.GF_FIRST_ALERT_DAYS,
					String.valueOf(modifiedParamMaster.getGuaranteeFeeFirstAlert()));
		}

		if (modifiedParamMaster.getGuaranteeFeePenaltyRate() != databaseParams.getGuaranteeFeePenaltyRate()) {
			updateParam(userId, Parameters.GF_PENALTY_RATE,
					String.valueOf(modifiedParamMaster.getGuaranteeFeePenaltyRate()));
		}

		if (modifiedParamMaster.getGuaranteeFeeWithoutPenalty() != databaseParams.getGuaranteeFeeWithoutPenalty()) {
			updateParam(userId, Parameters.GF_WITHOUT_PENALTY,
					String.valueOf(modifiedParamMaster.getGuaranteeFeeWithoutPenalty()));
		}

		if (modifiedParamMaster.getLockInPeriod() != databaseParams.getLockInPeriod()) {
			updateParam(userId, Parameters.LOCK_IN_PERIOD, String.valueOf(modifiedParamMaster.getLockInPeriod()));
		}

		if (modifiedParamMaster.getMaxApprovedAmt() != databaseParams.getMaxApprovedAmt()) {
			updateParam(userId, Parameters.MAXIMUM_APPROVED_AMOUNT,
					String.valueOf(modifiedParamMaster.getMaxApprovedAmt()));
		}

		if (modifiedParamMaster.getMaxIntRateApplied() != databaseParams.getMaxIntRateApplied()) {
			updateParam(userId, Parameters.MAXIMUM_INTEREST_RATE,
					String.valueOf(modifiedParamMaster.getMaxIntRateApplied()));
		}

		if (modifiedParamMaster.getMinAmtForMandatoryITPAN() != databaseParams.getMinAmtForMandatoryITPAN()) {
			updateParam(userId, Parameters.MIN_AMOUNT_FOR_MANDATORY_ITPAN,
					String.valueOf(modifiedParamMaster.getMinAmtForMandatoryITPAN()));
		}

		if (modifiedParamMaster.getNoOfCgpansPerDAN() != databaseParams.getNoOfCgpansPerDAN()) {
			updateParam(userId, Parameters.NO_CGPANS_PER_DAN,
					String.valueOf(modifiedParamMaster.getNoOfCgpansPerDAN()));
		}

		if (modifiedParamMaster.getPasswordDisplayPeriod() != databaseParams.getPasswordDisplayPeriod()) {
			updateParam(userId, Parameters.PASSWORD_DISPLAY_PERIOD,
					String.valueOf(modifiedParamMaster.getPasswordDisplayPeriod()));
		}

		if (modifiedParamMaster.getPasswordExpiryDays() != databaseParams.getPasswordExpiryDays()) {
			updateParam(userId, Parameters.PASSWORD_EXPIRY,
					String.valueOf(modifiedParamMaster.getPasswordExpiryDays()));
		}

		if (!modifiedParamMaster.getPenaltyCalculationType().equals(databaseParams.getPenaltyCalculationType())) {
			updateParam(userId, Parameters.PENALTY_CALCULATION_TYPE,
					String.valueOf(modifiedParamMaster.getPenaltyCalculationType()));
		}

		if (modifiedParamMaster.getPeriodicInfoAlertFreq() != databaseParams.getPeriodicInfoAlertFreq()) {
			updateParam(userId, Parameters.PERIODIC_ALERT_FREQUENCY,
					String.valueOf(modifiedParamMaster.getPeriodicInfoAlertFreq()));
		}

		if (!modifiedParamMaster.getPeriodicInfoFrequency().equals(databaseParams.getPeriodicInfoFrequency())) {
			updateParam(userId, Parameters.PERIODIC_FREQUENCY,
					String.valueOf(modifiedParamMaster.getPeriodicInfoFrequency()));
		}

		if (modifiedParamMaster.getServiceFeeAlertFreq() != databaseParams.getServiceFeeAlertFreq()) {
			updateParam(userId, Parameters.SERVICE_FEE_ALERT_FREQUENCY,
					String.valueOf(modifiedParamMaster.getServiceFeeAlertFreq()));
		}

		if (modifiedParamMaster.getServiceFeeCalculationDay() != databaseParams.getServiceFeeCalculationDay()) {
			updateParam(userId, Parameters.SERVICE_FEE_CALCULATION_DAY,
					String.valueOf(modifiedParamMaster.getServiceFeeCalculationDay()));
		}

		if (!modifiedParamMaster.getServiceFeeCalculationMonth()
				.equals(databaseParams.getServiceFeeCalculationMonth())) {
			updateParam(userId, Parameters.SERVICE_FEE_CALCULATION_MONTH,
					String.valueOf(modifiedParamMaster.getServiceFeeCalculationMonth()));
		}

		if (modifiedParamMaster.getServiceFeePenaltyRate() != databaseParams.getServiceFeePenaltyRate()) {
			updateParam(userId, Parameters.SERVICE_FEE_PENALTY_RATE,
					String.valueOf(modifiedParamMaster.getServiceFeePenaltyRate()));
		}

		if (modifiedParamMaster.getServiceFeeRate() != databaseParams.getServiceFeeRate()) {
			updateParam(userId, Parameters.SERVICE_FEE_RATE, String.valueOf(modifiedParamMaster.getServiceFeeRate()));
		}

		if (modifiedParamMaster.getServiceFeeWithoutPenalty() != databaseParams.getServiceFeeWithoutPenalty()) {
			updateParam(userId, Parameters.SERVICE_FEE_WITHOUT_PENALTY,
					String.valueOf(modifiedParamMaster.getServiceFeeWithoutPenalty()));
		}

		if (modifiedParamMaster.getShortLimit() != databaseParams.getShortLimit()) {
			updateParam(userId, Parameters.SHORT_PAYMENT_LIMIT, String.valueOf(modifiedParamMaster.getShortLimit()));
		}

		if (modifiedParamMaster.getWaiveLimit() != databaseParams.getWaiveLimit()) {
			updateParam(userId, Parameters.WAIVE_LIMIT, String.valueOf(modifiedParamMaster.getWaiveLimit()));
		}

		if (modifiedParamMaster.getWcTenorInYrs() != databaseParams.getWcTenorInYrs()) {
			updateParam(userId, Parameters.WORKING_CAPITAL_TENOR,
					String.valueOf(modifiedParamMaster.getWcTenorInYrs()));
		}

		if (!modifiedParamMaster.getMcgfServiceFee().equals(databaseParams.getMcgfServiceFee())) {
			updateParam(userId, Parameters.MCGF_SERVICE_FEE, modifiedParamMaster.getMcgfServiceFee());
		}
		if (!modifiedParamMaster.getThirdPartyGuarantee().equals(databaseParams.getThirdPartyGuarantee())) {
			updateParam(userId, Parameters.THIRD_PARTY_GUARANTEE_ALLOWED, modifiedParamMaster.getThirdPartyGuarantee());
		}

		if (!modifiedParamMaster.getCollateralTaken().equals(databaseParams.getCollateralTaken())) {
			updateParam(userId, Parameters.COLLATERAL_SECURITY_ALLOWED, modifiedParamMaster.getCollateralTaken());
		}
		// Added guarantee fee parameter on 08-06-2004.
		if (modifiedParamMaster.getGuaranteeFee() != (databaseParams.getGuaranteeFee())) {
			updateParam(userId, Parameters.GUARANTEE_FEE, String.valueOf(modifiedParamMaster.getGuaranteeFee()));
		}

		// Added check expiry and cheque expiry warning parameters on 10-06-2004.

		if (modifiedParamMaster.getChequeExpiryInMonths() != (databaseParams.getChequeExpiryInMonths())) {
			updateParam(userId, Parameters.CHEQUE_EXPIRY_IN_MONTHS,
					String.valueOf(modifiedParamMaster.getChequeExpiryInMonths()));
		}

		if (modifiedParamMaster.getChequeExpiryWarningInDays() != (databaseParams.getChequeExpiryWarningInDays())) {
			updateParam(userId, Parameters.CHEQUE_EXPIRY_WARNING_IN_DAYS,
					String.valueOf(modifiedParamMaster.getChequeExpiryWarningInDays()));
		}

		if (modifiedParamMaster.getMcgfGuaranteeFeePercentage() != (databaseParams.getMcgfGuaranteeFeePercentage())) {
			updateParam(userId, Parameters.MCGF_GUARANTEE_FEE_PERCENTAGE,
					String.valueOf(modifiedParamMaster.getMcgfGuaranteeFeePercentage()));
		}

		if (modifiedParamMaster.getApplicationFilingTimeLimit() != (databaseParams.getApplicationFilingTimeLimit())) {
			updateParam(userId, Parameters.APPLICATION_FILING_TIME_LIMIT,
					String.valueOf(modifiedParamMaster.getApplicationFilingTimeLimit()));
		}

		if (modifiedParamMaster.getMinimumSanctionedAmount() != (databaseParams.getMinimumSanctionedAmount())) {
			updateParam(userId, Parameters.MINIMUM_SANCTIONED_AMOUNT,
					String.valueOf(modifiedParamMaster.getMinimumSanctionedAmount()));
		}

		if (modifiedParamMaster.getIsDefaultRateApplicable() != (databaseParams.getIsDefaultRateApplicable())) {
			updateParam(userId, Parameters.IS_DEFAULT_RATE_APPLICABLE,
					String.valueOf(modifiedParamMaster.getIsDefaultRateApplicable()));
		}

		if (modifiedParamMaster.getDefaultRate() != (databaseParams.getDefaultRate())) {
			updateParam(userId, Parameters.DEFAULT_RATE, String.valueOf(modifiedParamMaster.getDefaultRate()));
		}

		if (modifiedParamMaster.getModerationFactor() != (databaseParams.getModerationFactor())) {
			updateParam(userId, Parameters.MODERATION_FACTOR,
					String.valueOf(modifiedParamMaster.getModerationFactor()));
		}

		if (modifiedParamMaster.getCgtsiLiability() != (databaseParams.getCgtsiLiability())) {
			updateParam(userId, Parameters.CGTSI_LIABILITY, String.valueOf(modifiedParamMaster.getCgtsiLiability()));
		}

		if (modifiedParamMaster.getHighValClearanceAmnt() != (databaseParams.getHighValClearanceAmnt())) {
			updateParam(userId, Parameters.HIGH_VALUE_CLEARENCE_AMOUNT,
					String.valueOf(modifiedParamMaster.getHighValClearanceAmnt()));
		}

		if (modifiedParamMaster
				.getPeriodTenureExpiryLodgementClaims() != (databaseParams.getPeriodTenureExpiryLodgementClaims())) {
			updateParam(userId, Parameters.PERIOD_TENURE_EXPIRY_LODGEMENT_CLAIMS,
					String.valueOf(modifiedParamMaster.getPeriodTenureExpiryLodgementClaims()));
		}

		Log.log(Log.INFO, "AdminDAO", "updateParameter", "Exited");
	}

	private void updateParam(String userId, String paramName, String paramValue) throws DatabaseException {
		Log.log(Log.INFO, "AdminDAO", "updateParam", "Entered");

		Connection connection = DBConnection.getConnection(false);

		try {
			Log.log(Log.DEBUG, "AdminDAO", "updateParam", "Param Name and Value " + paramName + " " + paramValue);

			CallableStatement callable = connection.prepareCall("{call PKGPARADETLfuncUpdateParameter(?,?,?,?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);

			callable.setString(2, paramName);
			callable.setString(3, paramValue);
			callable.setString(4, userId);

			callable.registerOutParameter(5, Types.VARCHAR);

			callable.execute();

			int errorCode = callable.getInt(1);
			String error = callable.getString(5);

			Log.log(Log.DEBUG, "AdminDAO", "updateParam", "Error Code and Error " + errorCode + " " + error);

			if (errorCode == Constants.FUNCTION_FAILURE) {
				connection.rollback();

				callable.close();
				callable = null;

				Log.log(Log.ERROR, "AdminDAO", "updateParam", error);

				throw new DatabaseException(error);
			}

			callable.close();
			callable = null;

			connection.commit();

		} catch (SQLException e) {

			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "updateParam", ignore.getMessage());
			}
			Log.log(Log.ERROR, "AdminDAO", "updateParam", e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Unable update parameter");
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(Log.INFO, "AdminDAO", "updateParam", "Exited");
	}

	/**
	 * This method is used to get all the alerts for the given user id.
	 * 
	 * @param userId
	 * @return ArrayList
	 * @throws DatabaseException
	 * @roseuid 39B0828B03C6
	 */
	public ArrayList getAlerts(String userId) throws DatabaseException {
		Log.log(Log.INFO, "AdminDAO", "getAlerts", "Entered");

		Connection connection = DBConnection.getConnection(false);
		ArrayList alertsList = new ArrayList();
		try {
			CallableStatement callable = connection.prepareCall("{call PKGALERTSFUNCGETALLALERTSFORUSER(?,?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);
			callable.setString(2, userId);
			callable.registerOutParameter(3, Types.VARCHAR);

			callable.execute();

			int errorCode = callable.getInt(1);
			String error = callable.getString(3);

			Log.log(Log.DEBUG, "AdminDAO", "getAlerts", "error code and error " + errorCode + " " + error);

			if (errorCode == Constants.FUNCTION_FAILURE) {

				connection.rollback();

				Log.log(Log.ERROR, "AdminDAO", "getAlerts", error);

				callable.close();
				callable = null;

				throw new DatabaseException(error);
			} else {
				ResultSet result = callable.executeQuery();

				while (result.next()) {
					String alert = result.getString(1);

					Log.log(Log.DEBUG, "AdminDAO", "getAlerts", "alert " + alert);

					alertsList.add(alert);
				}

				result.close();
				result = null;
			}

			callable.close();
			callable = null;
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "getAlerts", ignore.getMessage());
			}

			Log.log(Log.ERROR, "AdminDAO", "getAlerts", e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Unable to get Alerts for user");
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(Log.INFO, "AdminDAO", "getAlerts", "Exited");
		return alertsList;
	}

	/**
	 * This method is used to update the zones into the database.
	 * 
	 * @param desigMaster
	 * @throws DatabaseException
	 * @roseuid 39AF77C903D0
	 */
	public void updateZoneMaster(ZoneMaster zoneMaster, String createdBy) throws DatabaseException {
		Connection connection = DBConnection.getConnection(false);

		try {

			CallableStatement callable = connection.prepareCall("{?=call funcInsertZone(?,?,?,?)}");
			// The zone name and description with the creating userId is passed to the
			// database.
			callable.setString(2, zoneMaster.getZoneName());
			callable.setString(3, zoneMaster.getZoneDesc());
			callable.setString(4, createdBy);

			// The error status and error message are sent from the database.
			callable.registerOutParameter(1, Types.INTEGER);
			callable.registerOutParameter(5, Types.VARCHAR);

			callable.execute();

			// If error status is 1 throw database exception.

			int functionReturn = callable.getInt(1);

			String error = callable.getString(5);

			if (functionReturn == Constants.FUNCTION_FAILURE) {

				connection.rollback();

				callable.close();

				callable = null;

				throw new DatabaseException(error);
			}
		} catch (SQLException e) {

			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "updateZoneMaster", ignore.getMessage());
			}

			Log.log(Log.ERROR, "AdminDAO", "updateZoneMaster", e.getMessage());

			Log.logException(e);

			throw new DatabaseException("Unable to update zone master");
		} finally {

			DBConnection.freeConnection(connection);
		}

	}

	/**
	 * This method is used to update the regions into the database.
	 * 
	 * @param desigMaster
	 * @throws DatabaseException
	 * @roseuid 39AF77C903D0
	 */
	public void updateRegionMaster(RegionMaster regionMaster, String createdBy) throws DatabaseException {
		Connection connection = DBConnection.getConnection(false);

		try {

			CallableStatement callable = connection.prepareCall("{?=call funcInsertRegion(?,?,?,?,?)}");

			// The region name for the state is passed along with the userId creating it.
			callable.setString(2, regionMaster.getZoneName());
			callable.setString(3, regionMaster.getRegionName());
			callable.setString(4, regionMaster.getRegionDesc());
			callable.setString(5, createdBy);

			// The error status and error message are sent from the database.
			callable.registerOutParameter(1, Types.INTEGER);
			callable.registerOutParameter(6, Types.VARCHAR);

			callable.execute();

			// If error status is 1 throw database exception.

			int functionReturn = callable.getInt(1);

			String error = callable.getString(6);

			if (functionReturn == Constants.FUNCTION_FAILURE) {

				connection.rollback();

				callable.close();

				callable = null;

				throw new DatabaseException(error);
			}
			callable.close();
			callable = null;
			connection.commit();

		} catch (SQLException e) {

			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "updateRegionMaster", ignore.getMessage());
			}

			Log.log(Log.ERROR, "AdminDAO", "updateRegionMaster", e.getMessage());

			Log.logException(e);

			throw new DatabaseException("Unable to update region master");

		} finally {
			DBConnection.freeConnection(connection);
		}

	}

	public ArrayList getRoles(String userId) throws DatabaseException {
		Log.log(Log.INFO, "AdminDAO", "getRoles", "Entered");
		ArrayList roles = new ArrayList();

		Connection connection = DBConnection.getConnection(false);

		try {
			CallableStatement callable = connection.prepareCall("{call PKGFUNCGETALLROLESFORUSER(?,?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);
			callable.setString(2, userId);

			callable.registerOutParameter(3, Types.VARCHAR);

			callable.execute();
			ResultSet roleResults = callable.executeQuery();

			int errorCode = 0;
			String error = null;

			errorCode = callable.getInt(1);

			if (errorCode == Constants.FUNCTION_FAILURE) {
				connection.rollback();

				callable.close();
				callable = null;

				Log.log(Log.ERROR, "AdminDAO", "getRoles", error);

				throw new DatabaseException(error);
			}
			while (roleResults.next()) {
				String role = roleResults.getString(1);
				Log.log(Log.DEBUG, "AdminDAO", "getRoles", "role " + role);
				roles.add(role);

			}

			roleResults.close();
			roleResults = null;

			callable.close();
			callable = null;
			connection.commit();

		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "getRoles", ignore.getMessage());
			}

			Log.log(Log.ERROR, "AdminDAO", "getRoles", e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Unable to get roles for user.");
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(Log.INFO, "AdminDAO", "getRoles", "Exited");
		return roles;
	}

	public ArrayList getPrivileges(String userId) throws DatabaseException {
		ArrayList privileges = new ArrayList();
		Log.log(Log.INFO, "AdminDAO", "getPrivileges", "Entered");

		Connection connection = DBConnection.getConnection(false);

		try {
			CallableStatement callable = connection
					.prepareCall("{call PKGGETALLPRIVUSRFUNCGETALLPRIVILEGESFORUSER(?,?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);
			callable.setString(2, userId);

			callable.registerOutParameter(3, Types.VARCHAR);

			callable.execute();

			int errorCode = callable.getInt(1);
			String error = callable.getString(3);

			if (errorCode == Constants.FUNCTION_FAILURE) {

				connection.rollback();
				callable.close();
				callable = null;
				throw new DatabaseException(error);
			}

			ResultSet privilegeResults = callable.executeQuery();

			while (privilegeResults.next()) {
				String privilege = privilegeResults.getString(1);
				Log.log(Log.DEBUG, "AdminDAO", "getRoles", "privilege" + privilege);
				privileges.add(privilege);
			}
			privilegeResults.close();
			privilegeResults = null;

			callable.close();
			callable = null;
			connection.commit();
		} catch (SQLException sql) {

			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.INFO, "AdminDAO", "getPrivileges", ignore.getMessage());
			}

			Log.log(Log.INFO, "AdminDAO", "getPrivileges", sql.getMessage());
			Log.logException(sql);

			throw new DatabaseException("Unable to get Privileges for user ");
		} finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO, "AdminDAO", "getPrivileges", "Exited");
		return privileges;
	}

	public ArrayList getAllActiveUsers() throws DatabaseException {
		Log.log(Log.INFO, "AdminDAO", "getAllActiveUsers", "Entered");

		ArrayList users = new ArrayList();
		Connection connection = DBConnection.getConnection(false);

		try {
			CallableStatement callable = connection
					.prepareCall("{?=call PACKGETALLACTIVEUSERS.FUNCGETALLACTIVEUSERS(?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);
			callable.registerOutParameter(2, Constants.CURSOR);
			callable.registerOutParameter(3, Types.VARCHAR);

			callable.execute();

			int errorCode = callable.getInt(1);
			String error = callable.getString(3);

			Log.log(Log.DEBUG, "AdminDAO", "getAllActiveUsers", "Error code and error " + errorCode + " " + error);

			if (errorCode == Constants.FUNCTION_FAILURE) {

				connection.rollback();

				callable.close();
				callable = null;

				Log.log(Log.ERROR, "AdminDAO", "getAllActiveUsers", error);
				throw new DatabaseException(error);
			}

			ResultSet usersList = (ResultSet) callable.getObject(2);

			while (usersList.next()) {
				String userId = usersList.getString(1);

				Log.log(Log.DEBUG, "AdminDAO", "getAllActiveUsers", "userId " + userId);

				User user = new User();

				user.setUserId(userId);
				user.setFirstName(usersList.getString(2));
				user.setMiddleName(usersList.getString(3));
				user.setLastName(usersList.getString(4));
				user.setBankId(usersList.getString(5));
				user.setZoneId(usersList.getString(6));
				user.setBranchId(usersList.getString(7));

				users.add(user);
			}

			usersList.close();
			usersList = null;
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "getAllActiveUsers", ignore.getMessage());
			}

			Log.log(Log.ERROR, "AdminDAO", "getAllActiveUsers", e.getMessage());
			Log.logException(e);
			throw new DatabaseException("Unable to get all active users");
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(Log.INFO, "AdminDAO", "getAllActiveUsers", "Exited");

		return users;
	}

	public ArrayList getAllAdminMails(String userId) throws DatabaseException {

		Log.log(Log.INFO, "AdminDAO", "getAllAdminMails", "Entered==5458");

		ArrayList mails = new ArrayList();

		Connection connection = DBConnection.getConnection(false);
		try {
			CallableStatement callable = connection
					.prepareCall("{?=call packGetDelAdminMail.FUNCGETMSGSFORUSER(?,?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);
			callable.setString(2, userId);
			callable.registerOutParameter(3, Constants.CURSOR);
			callable.registerOutParameter(4, Types.VARCHAR);

			callable.execute();

			int errorCode = callable.getInt(1);
			String error = callable.getString(4);

			Log.log(Log.DEBUG, "AdminDAO", "getAllAdminMails==5479==",
					"error code and errors " + errorCode + " " + error);

			if (errorCode == Constants.FUNCTION_FAILURE) {
				connection.rollback();

				callable.close();
				callable = null;

				Log.log(Log.ERROR, "AdminDAO", "getAllAdminMails", error);

				throw new DatabaseException(error);
			}

			ResultSet mailResults = (ResultSet) callable.getObject(3);

			while (mailResults.next()) {
				Message message = new Message();

				int messageId = mailResults.getInt(1);
				String from = mailResults.getString(2);
				String subject = mailResults.getString(3);
				String messageInfo = mailResults.getString(4);
				Date messageDate = mailResults.getDate(5);

				if (messageDate != null) {

					messageDate = new java.util.Date(messageDate.getTime());

					CustomisedDate customDate = new CustomisedDate();
					customDate.setDate(messageDate);
					messageDate = customDate;

				}
				String status = mailResults.getString(6);

				if (status != null && !status.trim().equals("")) {
					if (status.trim().equals(Constants.NO)) {
						status = AdminConstants.UNREAD;
					} else {
						status = AdminConstants.READ;
					}
				}
				String bankId = mailResults.getString(7);
				String zoneId = mailResults.getString(8);
				String branchId = mailResults.getString(9);

				Log.log(Log.DEBUG, "AdminDAO", "getAllAdminMails", "messageId " + messageId);

				Log.log(Log.DEBUG, "AdminDAO", "getAllAdminMails", "from " + from);
				Log.log(Log.DEBUG, "AdminDAO", "getAllAdminMails", "subject " + subject);
				Log.log(Log.DEBUG, "AdminDAO", "getAllAdminMails", "messageInfo " + messageInfo);
				Log.log(Log.DEBUG, "AdminDAO", "getAllAdminMails", "messageDate " + messageDate);
				Log.log(Log.DEBUG, "AdminDAO", "getAllAdminMails", "status " + status);
				Log.log(Log.DEBUG, "AdminDAO", "getAllAdminMails", "bankId " + bankId);
				Log.log(Log.DEBUG, "AdminDAO", "getAllAdminMails", "zoneId " + zoneId);
				Log.log(Log.DEBUG, "AdminDAO", "getAllAdminMails", "branchId " + branchId);

				message.setMessageId(messageId);
				message.setFrom(from);
				message.setSubject(subject);
				message.setMessage(messageInfo);
				message.setDate(messageDate);

				message.setStatus(status);
				message.setBankId(bankId.trim());
				message.setZoneId(zoneId.trim());
				message.setBranchId(branchId.trim());

				mails.add(message);
			}

			mailResults.close();
			mailResults = null;
		} catch (SQLException e) {
			LogClass.StepWritter("Exception in getAllAdminMails" + e.getMessage());
			LogClass.writeExceptionOnFile(e);
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "getAllAdminMails", ignore.getMessage());
			}

			Log.log(Log.ERROR, "AdminDAO", "getAllAdminMails", e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Unable to get Mail information ");
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(Log.INFO, "AdminDAO", "getAllAdminMails", "Exited");

		return mails;
	}

	/**
	 * This method retrieves the message for the selected mail id.
	 * 
	 * @param mailId The mail id selected for viewing.
	 * @return Message Retusn the message object.
	 * @throws DatabaseException If any database error occurs this exception is
	 *                           thrown.
	 * @throws NoDataException   If the no message is available for the selected id,
	 *                           this exception is thrown.
	 */

	public Message getMailForId(String mailId) throws DatabaseException, NoDataException {

		Log.log(Log.INFO, "AdminDAO", "getMailForId", "Entered");

		if (mailId == null || mailId.trim().equals("")) {
			throw new NoDataException("Id is invalid");
		}

		int id = Integer.parseInt(mailId);

		Message message = null;

		Connection connection = DBConnection.getConnection();

		try {
			CallableStatement callable = connection
					.prepareCall("{?=call PACKGETDELADMINMAIL.FUNCGETMSGFORID(?,?,?,?,?,?,?,?,?,?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);
			callable.setInt(2, id);

			callable.registerOutParameter(3, Types.VARCHAR);
			callable.registerOutParameter(4, Types.VARCHAR);
			callable.registerOutParameter(5, Types.VARCHAR);
			callable.registerOutParameter(6, Types.VARCHAR);
			callable.registerOutParameter(7, Types.DATE);
			callable.registerOutParameter(8, Types.VARCHAR);
			callable.registerOutParameter(9, Types.VARCHAR);
			callable.registerOutParameter(10, Types.VARCHAR);
			callable.registerOutParameter(11, Types.VARCHAR);
			callable.registerOutParameter(12, Types.VARCHAR);

			callable.execute();

			int errorCode = callable.getInt(1);

			String error = callable.getString(12);

			Log.log(Log.DEBUG, "AdminDAO", "getMailForId", "Error Code and error " + errorCode + " " + error);

			if (errorCode == Constants.FUNCTION_FAILURE) {
				callable.close();
				callable = null;
				Log.log(Log.ERROR, "AdminDAO", "getMailForId", error);

				throw new DatabaseException(error);
			}

			message = new Message();
			String from = callable.getString(4);
			String mailInfo = callable.getString(5);
			String subject = callable.getString(6);

			String bankId = callable.getString(9);
			String zoneId = callable.getString(10);
			String branchId = callable.getString(11);

			Log.log(Log.DEBUG, "AdminDAO", "from ", from);
			Log.log(Log.DEBUG, "AdminDAO", "mailInfo", mailInfo);
			Log.log(Log.DEBUG, "AdminDAO", "bank Id ", bankId);
			Log.log(Log.DEBUG, "AdminDAO", "zone Id ", zoneId);
			Log.log(Log.DEBUG, "AdminDAO", "branch Id ", branchId);

			message.setFrom(from);
			message.setSubject(subject);
			message.setMessage(mailInfo);
			message.setBankId(bankId);
			message.setZoneId(zoneId);
			message.setBranchId(branchId);

			callable.close();
			callable = null;
			// Set the message flag as read.

			callable = connection.prepareCall("{?=call PACKGETDELADMINMAIL.FUNCUPDATEREADFLAG(?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);

			callable.setInt(2, id);
			callable.registerOutParameter(3, Types.VARCHAR);

			callable.execute();

			errorCode = callable.getInt(1);
			error = callable.getString(3);

			Log.log(Log.DEBUG, "AdminDAO", "getMailForId", "Error Code and error " + errorCode + " " + error);

			if (errorCode == Constants.FUNCTION_FAILURE) {
				Log.log(Log.ERROR, "AdminDAO", "getMailForId", error);

				callable.close();
				callable = null;
				throw new DatabaseException("Unable to update read flag");

			}
			callable.close();
			callable = null;
		} catch (SQLException e) {
			LogClass.StepWritter("Exception in getMailForId" + e.getMessage());
			LogClass.writeExceptionOnFile(e);
			Log.log(Log.ERROR, "AdminDAO", "getMailForId", e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Unable get mail information");
		} finally {
			DBConnection.freeConnection(connection);
		}

		if (message == null) {
			throw new NoDataException("No Mail information available for the selected id");
		}

		Log.log(Log.INFO, "AdminDAO", "getMailForId", "Exited");
		return message;
	}

	/**
	 * This method is used to delete all the selected mails
	 * 
	 * @param selectedMails String array of ids which are selected for deletion.
	 * @throws DatabaseException If any database error occurs this exception is
	 *                           thrown.
	 */

	public void deleteAdminMails(String[] selectedMails) throws DatabaseException {
		Log.log(Log.INFO, "AdminDAO", "deleteAdminMails", "Entered");

		Connection connection = DBConnection.getConnection(false);

		try {

			for (int i = 0; i < selectedMails.length; i++) {
				Log.log(Log.DEBUG, "AdminDAO", "deleteAdminMails", "ID is  " + selectedMails[i]);

				CallableStatement callable = connection.prepareCall("{?=call PACKGETDELADMINMAIL.FUNCDELETEMSG(?,?)}");

				callable.registerOutParameter(1, Types.INTEGER);

				callable.setString(2, selectedMails[i]);

				callable.registerOutParameter(3, Types.VARCHAR);

				callable.execute();

				int errorCode = callable.getInt(1);
				String error = callable.getString(3);

				Log.log(Log.DEBUG, "AdminDAO", "deleteAdminMails", "Error Code and error " + errorCode + " " + error);

				if (errorCode == Constants.FUNCTION_FAILURE) {

					Log.log(Log.ERROR, "AdminDAO", "deleteAdminMails", error);
					callable.close();
					callable = null;

					connection.rollback();

					throw new DatabaseException(error);
				}
				callable.close();
				callable = null;
			}

			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "deleteAdminMails", ignore.getMessage());
			}
			Log.log(Log.ERROR, "AdminDAO", "deleteAdminMails", e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Unable to delete the selected mails");

		} finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO, "AdminDAO", "deleteAdminMails", "Exited");

	}

	// Method for inserting PLR details.
	// 19 NOV 2003
	// Ramesh rp14480

	public void insertPLRMaster(PLRMaster plrMaster, String createdBy) throws DatabaseException {
		Connection connection = DBConnection.getConnection(false);
		try {
			/*
			 * if((plrMaster.getNewBankName()!=null)&&!(plrMaster.getNewBankName().equals(""
			 * ))){
			 * 
			 * CallableStatement callable=connection.prepareCall
			 * ("{?=call packGetInsUpdPLRDetails.funcInsertPLRdetail (?,?,?,?,?,?,?,?,?,?,?,?,?)}"
			 * );
			 * 
			 * //The PLR details is passed to the database.
			 * 
			 * java.sql.Date sqlStartDate=java.sql.Date.
			 * valueOf(DateHelper.stringToSQLdate(plrMaster.getStartDate().toString()));
			 * 
			 * java.sql.Date sqlEndDate=java.sql.Date.
			 * valueOf(DateHelper.stringToSQLdate(plrMaster.getEndDate().toString()));
			 * 
			 * callable.setString(2,plrMaster.getNewBankName());
			 * callable.setDate(3,sqlStartDate); callable.setDate(4,sqlEndDate);
			 * callable.setDouble(5,plrMaster.getShortTermPLR());
			 * callable.setDouble(6,plrMaster.getMediumTermPLR());
			 * callable.setDouble(7,plrMaster.getLongTermPLR());
			 * callable.setInt(8,plrMaster.getShortTermPeriod());
			 * callable.setInt(9,plrMaster.getMediumTermPeriod());
			 * callable.setInt(10,plrMaster.getLongTermPeriod());
			 * callable.setDouble(11,plrMaster.getBPLR());
			 * callable.setString(12,plrMaster.getPLR()); callable.setString(13,createdBy);
			 * 
			 * 
			 * //The error status and error message are sent from the database.
			 * callable.registerOutParameter(1,Types.INTEGER);
			 * callable.registerOutParameter(14,Types.VARCHAR);
			 * 
			 * callable.execute();
			 * 
			 * //If error status is 1 throw database exception. int
			 * functionReturn=callable.getInt(1);
			 * 
			 * String error=callable.getString(14);
			 * 
			 * Log.log(Log.DEBUG,"AdminDAO",
			 * "insertPLRMaster","Error Code and error for inserting "+functionReturn+" "
			 * +error);
			 * 
			 * if(functionReturn==Constants.FUNCTION_FAILURE){
			 * 
			 * connection.rollback();
			 * 
			 * callable.close();
			 * 
			 * callable=null;
			 * 
			 * throw new DatabaseException(error); } }
			 * 
			 * else{
			 */

			Log.log(Log.DEBUG, "AdminDAO", "insertPLRMaster",
					"*****plrMaster.getBankName() :" + plrMaster.getBankName());
			CallableStatement callable = connection
					.prepareCall("{?=call packGetInsUpdPLRDetails.funcInsertPLRdetail(?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			// The PLR details is passed to the database.

			java.util.Date startDateParam = plrMaster.getStartDate();

			java.sql.Date sqlStartDate = new java.sql.Date(startDateParam.getTime());

			Log.log(Log.DEBUG, "AdminDAO", "insertPLRMaster", "*****plrMaster.getEndDate() :" + plrMaster.getEndDate());

			java.sql.Date sqlEndDate = null;

			if ((plrMaster.getEndDate() != null) && (!(plrMaster.getEndDate().toString()).equals(""))) {
				sqlEndDate = new java.sql.Date(plrMaster.getEndDate().getTime());
			}

			callable.setString(2, plrMaster.getBankId());
			callable.setDate(3, sqlStartDate);
			if (sqlEndDate != null) {
				callable.setDate(4, sqlEndDate);
			} else {
				callable.setNull(4, java.sql.Types.DATE);
			}
			callable.setDouble(5, plrMaster.getShortTermPLR());
			callable.setDouble(6, plrMaster.getMediumTermPLR());
			callable.setDouble(7, plrMaster.getLongTermPLR());
			callable.setDouble(8, plrMaster.getShortTermPeriod());
			callable.setDouble(9, plrMaster.getMediumTermPeriod());
			callable.setDouble(10, plrMaster.getLongTermPeriod());
			callable.setDouble(11, plrMaster.getBPLR());
			callable.setString(12, plrMaster.getPLR());
			callable.setString(13, createdBy);

			// The error status and error message are sent from the database.
			callable.registerOutParameter(1, Types.INTEGER);
			callable.registerOutParameter(14, Types.VARCHAR);

			callable.execute();

			// If error status is 1 throw database exception..

			int functionReturn = callable.getInt(1);

			String error = callable.getString(14);

			Log.log(Log.DEBUG, "AdminDAO", "insertPLRMaster",
					"Error Code and error for updating  " + functionReturn + " " + error);

			if (functionReturn == Constants.FUNCTION_FAILURE) {

				connection.rollback();

				callable.close();

				callable = null;

				throw new DatabaseException(error);
			}

			callable.close();

			callable = null;
			// }
		} catch (SQLException e) {

			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "insertPLRMaster", ignore.getMessage());
			}

			Log.log(Log.ERROR, "AdminDAO", "insertPLRMaster", e.getMessage());

			Log.logException(e);

			throw new DatabaseException("Unable to update PLR master");

		} finally {

			DBConnection.freeConnection(connection);
		}

	}

	/*
	 * This method is used to return the designation description on passing the
	 * designation. Used while updating Designation master. 19 NOV 2003 Ramesh
	 * rp14480
	 */
	public String getDesigDescr(String designation) throws DatabaseException {
		Connection connection = DBConnection.getConnection(false);
		String description = null;

		try {

			CallableStatement callable = connection
					.prepareCall("{?=call packGetAllDesignations.funcGetDescForDsgName(?,?,?)}");

			callable.setString(2, designation);

			// The error status and error message are sent from the database.
			callable.registerOutParameter(1, Types.INTEGER);
			callable.registerOutParameter(3, Types.VARCHAR);
			callable.registerOutParameter(4, Types.VARCHAR);

			callable.execute();

			// Get the description for the designation passed.
			description = callable.getString(3);

			// If error status is 1 throw database exception.

			int functionReturn = callable.getInt(1);

			String error = callable.getString(4);

			if (functionReturn == Constants.FUNCTION_FAILURE) {

				connection.rollback();

				callable.close();

				callable = null;

				throw new DatabaseException(error);
			}
		} catch (SQLException e) {

			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "getDesigDescr", ignore.getMessage());
			}

			Log.log(Log.ERROR, "AdminDAO", "getDesigDescr", e.getMessage());

			Log.logException(e);

			throw new DatabaseException();

		} finally {
			DBConnection.freeConnection(connection);
		}

		return description;
	}

	// This method is used to return the banks that have registered the PLR details.
	// Used while updating PLR master.
	// 19 NOV 2003
	// Ramesh rp14480
	public ArrayList getPLRBanks() throws DatabaseException {
		Connection connection = DBConnection.getConnection(false);
		ArrayList banks = new ArrayList();
		try {
			CallableStatement callable = connection
					.prepareCall("{?=call packGetInsUpdPLRDetails.funcGetBankDetails (?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);
			callable.registerOutParameter(2, Constants.CURSOR);
			callable.registerOutParameter(3, Types.VARCHAR);

			callable.execute();

			// If error status is 1 throw database exception.

			int functionReturn = callable.getInt(1);

			String error = callable.getString(3);

			if (functionReturn == Constants.FUNCTION_FAILURE) {

				connection.rollback();

				callable.close();

				callable = null;

				throw new DatabaseException(error);
			} else {

				ResultSet result = (ResultSet) callable.getObject(2);

				while (result.next()) {

					String bank = result.getString(1);

					Log.log(Log.DEBUG, "AdminDAO", "getPLRBanks", "bank" + bank);

					banks.add(bank);
				}

				result.close();
				result = null;
				callable.close();
				callable = null;
			}
		} catch (SQLException e) {

			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "getPLRBanks", ignore.getMessage());
			}

			Log.log(Log.ERROR, "AdminDAO", "getPLRBanks", e.getMessage());

			Log.logException(e);

			throw new DatabaseException("Unable to get banks that have PLR details");

		} finally {

			DBConnection.freeConnection(connection);
		}

		return banks;
	}

	public ArrayList getPlrDetails(String bank) throws DatabaseException {
		Log.log(Log.INFO, "AdminDAO", "getPlrDetails", "Entered with  Bank Id :" + bank);
		Connection connection = DBConnection.getConnection(false);
		ArrayList plrDetailsArray = new ArrayList();

		PLRMaster plrMaster = null;

		try {
			CallableStatement callable = connection
					.prepareCall("{?=call packGetInsUpdPLRDetails.funcGetPLRDetails (?,?,?)}");

			callable.setString(2, bank);
			callable.registerOutParameter(1, Types.INTEGER);
			callable.registerOutParameter(3, Constants.CURSOR);
			callable.registerOutParameter(4, Types.VARCHAR);

			callable.execute();

			// If error status is 1 throw database exception.

			int functionReturn = callable.getInt(1);

			String error = callable.getString(4);

			Log.log(Log.DEBUG, "AdminDAO", "getPlrDetails", "functionReturn, error " + functionReturn + "," + error);

			if (functionReturn == Constants.FUNCTION_FAILURE) {

				connection.rollback();

				callable.close();

				callable = null;
				Log.log(Log.ERROR, "AdminDAO", "getPlrDetails", "error " + error);
				throw new DatabaseException(error);
			}

			else {
				ResultSet result = (ResultSet) callable.getObject(3);

				while (result.next()) {
					plrMaster = new PLRMaster();
					plrMaster.setBankId(bank);
					plrMaster.setPlrId(result.getInt(1));
					// CustomisedDate customDate=new CustomisedDate();
					// customDate.setDate(DateHelper.sqlToUtilDate(result.getDate(2)));
					plrMaster.setStartDate(DateHelper.sqlToUtilDate(result.getDate(2)));
					// CustomisedDate customDate1=new CustomisedDate();
					// customDate1.setDate(DateHelper.sqlToUtilDate(result.getDate(3)));
					plrMaster.setEndDate(DateHelper.sqlToUtilDate(result.getDate(3)));
					plrMaster.setBPLR(result.getDouble(4));
					plrMaster.setPLR(result.getString(5));
					plrMaster.setShortTermPLR(result.getDouble(6));
					plrMaster.setMediumTermPLR(result.getDouble(7));
					plrMaster.setLongTermPLR(result.getDouble(8));
					plrMaster.setShortTermPeriod(result.getInt(9));
					plrMaster.setMediumTermPeriod(result.getInt(10));
					plrMaster.setLongTermPeriod(result.getInt(11));

					/*
					 * // Adding the PLRMaster object to the ArrayList
					 * if(!plrDetailsArray.contains(plrMaster)) { plrDetailsArray.add(plrMaster); }
					 */
					plrDetailsArray.add(plrMaster);
				}
				result.close();
				result = null;

				callable.close();

				callable = null;
			}
		} catch (SQLException e) {

			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "getPlrDetails", ignore.getMessage());
			}

			Log.log(Log.ERROR, "AdminDAO", "getPlrDetails", e.getMessage());

			Log.logException(e);

			throw new DatabaseException("Unable to return the PLR details of a bank");
		} finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO, "AdminDAO", "getPlrDetails", " Exited");
		return plrDetailsArray;
	}
	// This method is used to get all the alerts from the database.
	// Used while updating Alert master.
	// 19 NOV 2003
	// Ramesh rp14480

	public ArrayList getAlertTitles() throws DatabaseException {

		Connection connection = DBConnection.getConnection(false);

		ArrayList alertTitles = new ArrayList();
		try {
			CallableStatement callable = connection
					.prepareCall("{?=call packGetInsertUpdateAlerts.funcGetAllAlerts(?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);
			callable.registerOutParameter(2, Constants.CURSOR);
			callable.registerOutParameter(3, Types.VARCHAR);

			callable.execute();

			// If error status is 1 throw database exception.

			int functionReturn = callable.getInt(1);

			String error = callable.getString(3);

			if (functionReturn == Constants.FUNCTION_FAILURE) {

				connection.rollback();

				callable.close();

				callable = null;

				throw new DatabaseException(error);
			}

			else {

				ResultSet result = (ResultSet) callable.getObject(2);

				while (result.next()) {

					String alert = result.getString(1);

					Log.log(Log.DEBUG, "AdminDAO", "getAlertTitles", "alert " + alert);

					alertTitles.add(alert);
				}

				result.close();
				result = null;
				callable.close();
				callable = null;
			}
		} catch (SQLException e) {

			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "getAlertTitles", ignore.getMessage());
			}

			Log.log(Log.ERROR, "AdminDAO", "getAlertTitles", e.getMessage());

			Log.logException(e);

			throw new DatabaseException("Unable to get the alert details");

		} finally {
			DBConnection.freeConnection(connection);
		}

		return alertTitles;
	}

	/*
	 * This method is used to return the message associated with a title. Used while
	 * updating Alert master. 20 NOV 2003 Ramesh rp14480
	 */
	public String getAlertMessage(String alertTitle) throws DatabaseException {

		Connection connection = DBConnection.getConnection(false);
		String alertMessage = null;

		try {

			CallableStatement callable = connection
					.prepareCall("{?=call packGetInsertUpdateAlerts.funcGetAlertMsgForTitle(?,?,?)}");

			callable.setString(2, alertTitle);

			callable.registerOutParameter(1, Types.INTEGER);
			callable.registerOutParameter(3, Types.VARCHAR);// alert Message
			callable.registerOutParameter(4, Types.VARCHAR);

			callable.execute();

			// Get the alert message for the Alert title passed.
			alertMessage = callable.getString(3);

			// If error status is 1 throw database exception.

			int functionReturn = callable.getInt(1);

			String error = callable.getString(4);

			if (functionReturn == Constants.FUNCTION_FAILURE) {

				connection.rollback();

				callable.close();

				callable = null;

				throw new DatabaseException(error);
			}
			callable.close();
			callable = null;

		} catch (SQLException e) {

			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "getAlertMessage", ignore.getMessage());
			}

			Log.log(Log.ERROR, "AdminDAO", "getAlertMessage", e.getMessage());

			Log.logException(e);

			throw new DatabaseException("Unable to get the alert messages");
		} finally {

			DBConnection.freeConnection(connection);
		}

		return alertMessage;
	}

	// This method is used to get all the Exceptions from the database.
	// Used while updating Exceptions master.
	// 19 NOV 2003
	// Ramesh rp14480

	public ArrayList getExceptionTitles() throws DatabaseException {

		Connection connection = DBConnection.getConnection(false);

		ArrayList exceptionTitles = new ArrayList();
		try {
			CallableStatement callable = connection
					.prepareCall("{?=call packGetInsertUpdateErrors.funcGetAllErrors (?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);
			callable.registerOutParameter(2, Constants.CURSOR);
			callable.registerOutParameter(3, Types.VARCHAR);

			callable.execute();

			// If error status is 1 throw database exception.

			int functionReturn = callable.getInt(1);

			String error = callable.getString(3);

			if (functionReturn == Constants.FUNCTION_FAILURE) {

				connection.rollback();

				callable.close();

				callable = null;

				throw new DatabaseException(error);
			}

			else {
				ResultSet result = (ResultSet) callable.getObject(2);

				while (result.next()) {

					String exception = result.getString(2);

					Log.log(Log.DEBUG, "AdminDAO", "getExceptionTitles", "exception" + exception);

					exceptionTitles.add(exception);
				}
				result.close();
				result = null;
			}

			callable.close();
			callable = null;
			connection.commit();

		} catch (SQLException e) {

			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "getExceptionTitles", ignore.getMessage());
			}

			Log.log(Log.ERROR, "AdminDAO", "getExceptionTitles", e.getMessage());

			Log.logException(e);

			throw new DatabaseException("Unable to get the Exception titles");

		} finally {

			DBConnection.freeConnection(connection);
		}

		return exceptionTitles;
	}

	/*
	 * This method is used to return the message associated with an exception title.
	 * Used while updating Exception master. 21 NOV 2003 Ramesh rp14480
	 */
	public ExceptionMaster getExceptionMessage(String exceptionTitle) throws DatabaseException {
		Connection connection = DBConnection.getConnection(false);
		ExceptionMaster exceptionMaster = new ExceptionMaster();
		try {

			CallableStatement callable = connection
					.prepareCall("{?=call packGetInsertUpdateErrors.funcGetErrorMsgForTitle(?,?,?,?)}");

			callable.setString(2, exceptionTitle);

			callable.registerOutParameter(1, Types.INTEGER);
			callable.registerOutParameter(3, Types.VARCHAR);// exception type
			callable.registerOutParameter(4, Types.VARCHAR);// exception Message
			callable.registerOutParameter(5, Types.VARCHAR);

			callable.execute();

			// Get the alert message for the Alert title passed.
			exceptionMaster.setExceptionType(callable.getString(3));
			exceptionMaster.setExceptionMessage(callable.getString(4));

			// If error status is 1 throw database exception.

			int functionReturn = callable.getInt(1);

			String error = callable.getString(5);

			if (functionReturn == Constants.FUNCTION_FAILURE) {

				connection.rollback();

				callable.close();

				callable = null;

				throw new DatabaseException(error);
			}
			callable.close();
			callable = null;
			connection.commit();

		} catch (SQLException e) {

			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "getExceptionMessage", ignore.getMessage());
			}
			Log.log(Log.ERROR, "AdminDAO", "getExceptionMessage", e.getMessage());

			Log.logException(e);

			throw new DatabaseException("Unable to get the exception message");

		} finally {
			DBConnection.freeConnection(connection);
		}

		return exceptionMaster;
	}

	/**
	 * This method retrives all the social categories from the database.
	 * 
	 * @return ArrayList
	 * @throws DatabaseException
	 */
	/**
	 * This method retrives all the social categories from the database.
	 * 
	 * @return ArrayList
	 * @throws DatabaseException
	 */
	public ArrayList getAllSocialCategories() throws DatabaseException {
		Log.log(Log.INFO, "AdminDAO", "getAllSocialCategories", "Entered");

		CallableStatement socialCatStmt;
		ResultSet rsSocialCategories;

		ArrayList socialCategories = new ArrayList();

		Connection connection = null;
		if (connection == null) {
			connection = DBConnection.getConnection(false);
		}
		try {
			socialCatStmt = connection.prepareCall("{call PKGGETALLSCATEGORYFUNCGETSOCIALCATEGORIES(?,?)}");

			socialCatStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			socialCatStmt.registerOutParameter(2, java.sql.Types.VARCHAR); // error description

			socialCatStmt.execute();
			int errorCode = socialCatStmt.getInt(1);
			String error = socialCatStmt.getString(2);

			Log.log(Log.DEBUG, "AdminDAO", "getAllSocialCategories", "error,errorCode " + error + " " + errorCode);

			if (errorCode == Constants.FUNCTION_FAILURE) {
				connection.rollback();

				socialCatStmt.close();
				socialCatStmt = null;

				Log.log(Log.ERROR, "AdminDAO", "getAllSocialCategories", "error" + error);

				throw new DatabaseException(error);
			}
			rsSocialCategories = socialCatStmt.executeQuery();
			while (rsSocialCategories.next()) {
				String socialCategory = rsSocialCategories.getString(1);

				Log.log(Log.DEBUG, "AdminDAO", "getAllSocialCategories", "socialCategory " + socialCategory);

				socialCategories.add(socialCategory);
			}

			rsSocialCategories.close();
			rsSocialCategories = null;

			socialCatStmt.close();
			socialCatStmt = null;

		} catch (SQLException sqlException) {
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "getAllSocialCategories", ignore.getMessage());
			}

			Log.log(Log.ERROR, "AdminDAO", "getAllSocialCategories", sqlException.getMessage());
			Log.logException(sqlException);

			throw new DatabaseException(sqlException.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(Log.INFO, "AdminDAO", "getAllSocialCategories", "Exited");

		return socialCategories;
	}

	// Diksha SMTP

	public AdministrationActionForm getSmtpDetails() throws DatabaseException {
		AdministrationActionForm adForm = new AdministrationActionForm();
		ArrayList smtpDetails = new ArrayList();

		Connection connection = DBConnection.getConnection(false);

		try {
			CallableStatement callable = connection.prepareCall("{call funcGetSMTPDetails(?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);
		
			callable.registerOutParameter(2, Types.VARCHAR);

			callable.execute();

			int errorCode = callable.getInt(1);
			String error = callable.getString(2);

			if (errorCode == Constants.FUNCTION_FAILURE) {
				connection.rollback();

				callable.close();
				callable = null;
				Log.log(Log.ERROR, "AdminDAO", "getAllIndustryNature", error);

				throw new DatabaseException(error);
			}

			ResultSet rs =callable.executeQuery() ;
			// //System.out.println("Exception in getAllIndustryNature before while
			// IndustryNature");
			while (rs.next()) {
				/*
				 * String smtpHost=rs.getString(1); //System.out.println("smtpHost=="+smtpHost);
				 * String userId=rs.getString(2); //System.out.println("userId=="+userId);
				 * 
				 * String password=rs.getString(3); //System.out.println("password=="+password);
				 * String port=rs.getString(4); //System.out.println("port=="+port);
				 */

				adForm.setSmtpHost(rs.getString(1));
				adForm.setSmtpUserId(rs.getString(2));
				adForm.setSmtpPassword(rs.getString(3));
				adForm.setSmtpPort(rs.getString(4));

				// //System.out.println("nature="+nature);
				// Log.log(Log.DEBUG,"AdminDAO","getAllIndustryNature",smtpHost);
				// //System.out.println("Exception in getAllIndustryNature method before
				// toUpperCase"+nature);
				// smtpDetails.add(adForm);
				// //System.out.println("Exception in getAllIndustryNature method after
				// toUpperCase"+nature);
			}
			// //System.out.println("Exception in getAllIndustryNature after while
			// IndustryNature");

			rs.close();
			rs = null;

			callable.close();
			callable = null;
			connection.commit();

		} catch (SQLException e) {
			//System.out.println("Exception  in getAllIndustryNature method" + e.getMessage());
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "getAllIndustryNature", ignore.getMessage());
			}

			Log.log(Log.ERROR, "AdminDAO", "getAllIndustryNature", e.getMessage());

			Log.logException(e);

			throw new DatabaseException("Unable to get Industry Natures.");
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(Log.INFO, "AdminDAO", "getAllIndustryNature", "Exited");
		return adForm;

	}

	/**
	 * This method fetches all the industry natures available in the database.
	 * 
	 * @return ArrayList an ArrayList of industry natures.
	 * @throws DatabaseException If any error occured while executing database
	 *                           queries this exception is thrown.
	 */
	public ArrayList getAllIndustryNature() throws DatabaseException {

		Log.log(Log.INFO, "AdminDAO", "getAllIndustryNature", "Entered");

		ArrayList industryNatures = new ArrayList();

		Connection connection = DBConnection.getConnection(false);

		try {
			CallableStatement callable = connection.prepareCall("{call PKGGETINSECOTORFUNCGETINDNATURE(?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);
			callable.registerOutParameter(2, Types.VARCHAR);

			callable.execute();

			int errorCode = callable.getInt(1);
			String error = callable.getString(2);

			if (errorCode == Constants.FUNCTION_FAILURE) {
				connection.rollback();

				callable.close();
				callable = null;
				Log.log(Log.ERROR, "AdminDAO", "getAllIndustryNature", error);

				throw new DatabaseException(error);
			}

			ResultSet natures = callable.executeQuery();
			// //System.out.println("Exception in getAllIndustryNature before while
			// IndustryNature");
			while (natures.next()) {
				String nature = natures.getString(1);
				// //System.out.println("nature="+nature);
				Log.log(Log.DEBUG, "AdminDAO", "getAllIndustryNature", nature);
				// //System.out.println("Exception in getAllIndustryNature method before
				// toUpperCase"+nature);
				industryNatures.add(nature.toUpperCase());
				// //System.out.println("Exception in getAllIndustryNature method after
				// toUpperCase"+nature);
			}
			// //System.out.println("Exception in getAllIndustryNature after while
			// IndustryNature");

			natures.close();
			natures = null;

			callable.close();
			callable = null;
			connection.commit();

		} catch (SQLException e) {
			//System.out.println("Exception  in getAllIndustryNature method" + e.getMessage());
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "getAllIndustryNature", ignore.getMessage());
			}

			Log.log(Log.ERROR, "AdminDAO", "getAllIndustryNature", e.getMessage());

			Log.logException(e);

			throw new DatabaseException("Unable to get Industry Natures.");
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(Log.INFO, "AdminDAO", "getAllIndustryNature", "Exited");
		return industryNatures;
	}

	/**
	 * This method fetches the industry sectors mapped to an industry nature.
	 * 
	 * @param industryNature The industry nature for which industry sectors are to
	 *                       be retrieved.
	 * @return ArrayList ArrayList of industry sectors.
	 * @throws DatabaseException If any error occured during database access.
	 *
	 */
	public ArrayList getIndustrySectors(String industryNature) throws DatabaseException {

		LogClass.StepWritter("getIndustrySectors " + industryNature);

		ArrayList industrySectors = new ArrayList();

		Connection connection = DBConnection.getConnection(false);
		if (connection != null) {
			LogClass.StepWritter("A connection[connection] in getIndustrySectors" + connection);
		}
		try {
			CallableStatement callable = connection.prepareCall("{call PKGGETINSECOTORFUNCGETINDNATSECTOR(?,?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);
			callable.setString(2, industryNature);
			callable.registerOutParameter(3, Types.VARCHAR);

			callable.execute();

			int errorCode = callable.getInt(1);
			String error = callable.getString(3);
			//System.out.println(error + " error in getIndustrySectors" + errorCode);
			LogClass.StepWritter("error in getIndustrySectors" + errorCode);
			if (errorCode == Constants.FUNCTION_FAILURE) {
				connection.rollback();

				callable.close();
				callable = null;
				Log.log(Log.ERROR, "AdminDAO", "getIndustrySectors", error);

				throw new DatabaseException(error);
			}

			ResultSet sectors = callable.executeQuery();
			if (sectors != null) {
				LogClass.StepWritter("sectors[ResultSet] in getIndustrySectors" + sectors);
			}
			while (sectors.next()) {
				String sector = sectors.getString(1);

				industrySectors.add(sector);
			}
			LogClass.StepWritter("A sectors[ResultSet] in getIndustrySectors" + sectors);
			sectors.close();
			sectors = null;
			LogClass.StepWritter("A callable[CallableStatement] in getIndustrySectors" + callable);
			callable.close();
			callable = null;
			LogClass.StepWritter("B connection[connection] in getIndustrySectors" + connection);
			connection.commit();
			//System.out.println("Sector Value all stmt executed");
		} catch (SQLException e) {
			LogClass.StepWritter("Exception in getIndustrySectors " + e.getMessage());
			LogClass.writeExceptionOnFile(e);

			try {
				LogClass.StepWritter("C connection[connection] in getIndustrySectors" + connection);
				connection.rollback();
			} catch (SQLException ignore) {

			}
			throw new DatabaseException("Unable to get Industry Natures.");
		} finally {
			DBConnection.freeConnection(connection);
		}

		return industrySectors;
	}

	public ArrayList getAllIndustrySectors() throws DatabaseException {
		Log.log(Log.INFO, "AdminDAO", "getAllIndustrySectors", "Entered");

		ArrayList industrySectors = new ArrayList();

		Connection connection = DBConnection.getConnection(false);

		try {
			CallableStatement callable = connection
					.prepareCall("{?=call PACKGETINDNATURESECTOR.FUNCGETALLINDSECTOR(?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);
			callable.registerOutParameter(2, Constants.CURSOR);
			callable.registerOutParameter(3, Types.VARCHAR);

			callable.execute();

			int errorCode = callable.getInt(1);
			String error = callable.getString(3);

			if (errorCode == Constants.FUNCTION_FAILURE) {
				connection.rollback();

				callable.close();
				callable = null;

				Log.log(Log.ERROR, "AdminDAO", "getAllIndustrySectors", error);

				throw new DatabaseException(error);
			}

			ResultSet sectors = (ResultSet) callable.getObject(2);

			while (sectors.next()) {
				String sector = sectors.getString(1);

				Log.log(Log.DEBUG, "AdminDAO", "getAllIndustrySectors", "sector is " + sector);

				industrySectors.add(sector);
			}

			sectors.close();
			sectors = null;

			callable.close();
			callable = null;
			connection.commit();
		} catch (SQLException e) {

			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "getAllIndustrySectors", ignore.getMessage());
			}

			Log.log(Log.ERROR, "AdminDAO", "getAllIndustrySectors", e.getMessage());

			Log.logException(e);

			throw new DatabaseException("Unable to get All Industry Sectors");
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(Log.INFO, "AdminDAO", "getAllIndustrySectors", "Exited");

		return industrySectors;
	}

	public ArrayList getUsersWithoutRolesAndPrivileges(String memberId) throws DatabaseException {
		ArrayList users = new ArrayList();
		Log.log(Log.INFO, "AdminDAO", "getUsersWithoutRolesAndPrivileges", "Entered");
		Connection connection = DBConnection.getConnection(false);

		try {
			Log.log(Log.DEBUG, "AdminDAO", "getUsersWithoutRolesAndPrivileges", "member id " + memberId);
			CallableStatement callable = connection.prepareCall("{call PKGFUNCGETUSERSHAVENORP(?,?,?) }");

			callable.registerOutParameter(1, Types.INTEGER);
			callable.setString(2, memberId);
			// callable.registerOutParameter(3,Constants.CURSOR);
			callable.registerOutParameter(3, Types.VARCHAR);

			callable.execute();

			int errorCode = callable.getInt(1);
			String error = callable.getString(3);

			Log.log(Log.DEBUG, "AdminDAO", "getUsersWithoutRolesAndPrivileges",
					"Error Code and Errors are " + errorCode + ", " + error);

			if (errorCode == Constants.FUNCTION_FAILURE) {
				connection.rollback();

				callable.close();
				callable = null;

				Log.log(Log.ERROR, "AdminDAO", "getUsersWithoutRolesAndPrivileges", error);

				throw new DatabaseException(error);
			}

			ResultSet usersList = callable.executeQuery();

			while (usersList.next()) {
				String userId = usersList.getString(1);

				Log.log(Log.DEBUG, "AdminDAO", "getUsersWithoutRolesAndPrivileges", "user id " + userId);

				users.add(userId);
			}

			usersList.close();
			usersList = null;

			callable.close();
			callable = null;
			// connection.commit();

		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "getUsersWithoutRolesAndPrivileges", ignore.getMessage());
			}

			Log.log(Log.ERROR, "AdminDAO", "getUsersWithoutRolesAndPrivileges", e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Unable to get user for the selected member");
		} finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO, "AdminDAO", "getUsersWithoutRolesAndPrivileges", "Exited");

		return users;
	}

	public void batchProcess() throws DatabaseException, MessageException {
		Log.log(Log.INFO, "AdminDAO", "batchProcess", "Entered");

		Connection connection = DBConnection.getConnection();

		CallableStatement callableStmt;

		try {
			callableStmt = connection.prepareCall("{ call PROCBATCHCODE(?)}");
			callableStmt.registerOutParameter(1, Types.VARCHAR);
			callableStmt.execute();

			String error = callableStmt.getString(1);

			Log.log(Log.CRITICAL, "AdminDAO", "batchProcess", "Batch Process Completed... Information is " + error);

			if (error != null && !error.trim().equals("")) {
				Log.log(Log.CRITICAL, "AdminDAO", "batchProcess", "Batch Process Failed. The Error is " + error);

				throw new MessageException("Batch Process Failed. The Error is " + error);
			}
		} catch (SQLException e) {
			Log.log(Log.ERROR, "AdminDAO", "batchProcess", e.getMessage());

			Log.logException(e);

			throw new DatabaseException("Error occured in the batchProcess");
		} finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO, "AdminDAO", "batchProcess", "Exited");
	}

	// New method added for Data Archival in front end
	public void archiveData() throws DatabaseException, MessageException, SQLException {
		Log.log(Log.INFO, "AdminDAO", "archiveData", "Entered");

		// Connection connection = DriverManager.getConnection(
		// "jdbc:oracle:thin:@cpr-pcs686:1521:ARCHIVAL", "CGTSIARCHIVAL",
		// "CGTSIARCHIVAL");

		Connection connection = DBConnection.getConnection();

		CallableStatement callableStmt;

		try {
			callableStmt = connection.prepareCall("{call packArchiveLogTbl.procArchAuditLog@ARCHLINK}");
			callableStmt.execute();
			Log.log(Log.INFO, "AdminDAO", "archiveData", "packArchiveLogTbl.procArchAuditLog executed ");

			callableStmt = connection.prepareCall("{call packArchiveLogTbl.procArchHistoryDetail@ARCHLINK}");
			callableStmt.execute();
			Log.log(Log.INFO, "AdminDAO", "archiveData", "packArchiveLogTbl.procArchHistoryDetail executed ");

			callableStmt = connection.prepareCall("{call packArchiveLogTbl.procArchErrorTransfer@ARCHLINK}");
			callableStmt.execute();
			Log.log(Log.INFO, "AdminDAO", "archiveData", "packArchiveLogTbl.procArchErrorTransfer executed ");

			callableStmt = connection.prepareCall("{call packArchiveLogTbl.procArchBatchLog@ARCHLINK}");
			callableStmt.execute();
			Log.log(Log.INFO, "AdminDAO", "archiveData", "packArchiveLogTbl.procArchBatchLog executed ");

			callableStmt = connection.prepareCall("{call packArchiveMem.procArchiveMem@ARCHLINK}");
			callableStmt.execute();
			Log.log(Log.INFO, "AdminDAO", "archiveData", "packArchiveMem.procArchiveMem executed ");

			callableStmt = connection.prepareCall("{call packArchiveUsers.procActiveUsers@ARCHLINK}");
			callableStmt.execute();
			Log.log(Log.INFO, "AdminDAO", "archiveData", "packArchiveUsers.procActiveUsers executed ");

			callableStmt = connection.prepareCall("{call packArchiveUsers.procInActiveUsers@ARCHLINK}");
			callableStmt.execute();
			Log.log(Log.INFO, "AdminDAO", "archiveData", "packArchiveUsers.procInActiveUsers executed ");

			callableStmt = connection.prepareCall("{call packArchiveApp.procArchiveApp@ARCHLINK}");
			callableStmt.execute();
			Log.log(Log.INFO, "AdminDAO", "archiveData", "packArchiveApp.procArchiveApp executed ");

			callableStmt = connection.prepareCall("{call procArchiveAdminMail@ARCHLINK}");
			callableStmt.execute();
			Log.log(Log.INFO, "AdminDAO", "archiveData", "procArchiveAdminMail executed ");

			callableStmt = connection.prepareCall("{call packArchiveVoucher.procArchVoucher@ARCHLINK}");
			callableStmt.execute();
			Log.log(Log.INFO, "AdminDAO", "archiveData", "packArchiveVoucher.procArchVoucher executed ");

		} catch (SQLException e) {
			Log.log(Log.ERROR, "AdminDAO", "archiveData", e.getMessage());

			Log.logException(e);

			throw new DatabaseException("Error occured in Data Archival");
		} finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO, "AdminDAO", "archiveData", "Exited");
	}

	public int getUsersCount(String bankId, String zoneId, String branchId, String flag) throws DatabaseException {
		Log.log(Log.INFO, "AdminDAO", "getUsersCount()", "Entered!");
		int count = 0;

		CallableStatement callableStmt = null;
		Connection conn = null;

		int status = -1;
		String errorCode = null;

		try {
			conn = DBConnection.getConnection();
			callableStmt = conn.prepareCall("{call funcUsersCount(?,?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt.setString(2, bankId);
			callableStmt.setString(3, zoneId);
			callableStmt.setString(4, branchId);
			callableStmt.setString(5, flag);
			callableStmt.registerOutParameter(6, java.sql.Types.INTEGER);
			callableStmt.registerOutParameter(7, java.sql.Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);
			if (status == Constants.FUNCTION_FAILURE) {
				Log.log(Log.ERROR, "AdminDAO", "getUsersCount()",
						"SP returns a 1 in retrieving the Users Count.Error code is :" + errorCode);
				callableStmt.close();
				throw new DatabaseException(errorCode);
			} else if (status == Constants.FUNCTION_SUCCESS) {
				count = callableStmt.getInt(6);
			}
			// closing the callable statement
			callableStmt.close();
			callableStmt = null;
		} catch (SQLException sqlexception) {
			Log.log(Log.ERROR, "AdminDAO", "getUsersCount()", "Error retrieving Users Count");
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(Log.INFO, "AdminDAO", "getUsersCount()", "Exited!");
		return count;
	}

	public void checkValidUserLogin(String userId) throws DatabaseException {
		Log.log(Log.INFO, "AdminDAO", "checkValidUserLogin()", "Entered!");

		CallableStatement callableStmt = null;
		Connection conn = null;

		int status = -1;
		String errorCode = null;

		try {
			conn = DBConnection.getConnection();
			callableStmt = conn.prepareCall("{call funcCheckUserLogin(?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt.setString(2, userId);
			callableStmt.registerOutParameter(3, java.sql.Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(3);
			if (status == Constants.FUNCTION_FAILURE) {
				Log.log(Log.ERROR, "AdminDAO", "checkValidUserLogin()",
						"SP returns a 1 in checking User Login. Error code is :" + errorCode);
				callableStmt.close();
				throw new DatabaseException(errorCode);
			}
			// closing the callable statement
			callableStmt.close();
			callableStmt = null;
		} catch (SQLException sqlexception) {
			Log.log(Log.ERROR, "AdminDAO", "checkValidUserLogin()", "Error checking User Login");
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(Log.INFO, "AdminDAO", "checkValidUserLogin()", "Exited!");
	}

	public ArrayList getAllStateCodes() throws DatabaseException {
		Connection connection = DBConnection.getConnection();

		ArrayList stateCodes = new ArrayList();

		try {

			CallableStatement callable = connection
					.prepareCall("{?=call packGetAllZneRgnStateDist.funcGetAllStates(?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);

			callable.registerOutParameter(2, Constants.CURSOR);

			callable.registerOutParameter(3, Types.VARCHAR);

			callable.execute();

			int functionReturn = callable.getInt(1);

			String error = callable.getString(3);

			if (functionReturn == Constants.FUNCTION_FAILURE) {

				connection.rollback();

				callable.close();

				callable = null;

				throw new DatabaseException(error);

			} else {
				ResultSet result = (ResultSet) callable.getObject(2);

				while (result.next()) {
					String stateCode = result.getString(1);
					stateCodes.add(stateCode);
				}
				result.close();
				result = null;
			}

			callable.close();
			callable = null;

		} catch (SQLException e) {

			try {
				connection.rollback();
			} catch (SQLException ignore) {
			}

			Log.log(Log.ERROR, "AdminDAO", "getAllStateCodes", e.getMessage());

			Log.logException(e);

			throw new DatabaseException("Unable to get all state codes");

		} finally {

			DBConnection.freeConnection(connection);
		}

		return stateCodes;
	}

	public String getStateName(String stateCode) throws DatabaseException {
		Connection connection = DBConnection.getConnection();

		String stateName = "";

		try {

			CallableStatement callable = connection.prepareCall("{?=call funcGetStateName(?,?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);

			callable.setString(2, stateCode);

			callable.registerOutParameter(3, Types.VARCHAR);

			callable.registerOutParameter(4, Types.VARCHAR);

			callable.execute();

			int functionReturn = callable.getInt(1);

			String error = callable.getString(4);

			if (functionReturn == Constants.FUNCTION_FAILURE) {

				connection.rollback();

				callable.close();

				callable = null;

				throw new DatabaseException(error);

			} else {
				stateName = callable.getString(3);
			}

			callable.close();
			callable = null;

		} catch (SQLException e) {

			try {
				connection.rollback();
			} catch (SQLException ignore) {
			}

			Log.log(Log.ERROR, "AdminDAO", "getAllStateCodes", e.getMessage());

			Log.logException(e);

			throw new DatabaseException("Unable to get all state codes");

		} finally {

			DBConnection.freeConnection(connection);
		}

		return stateName;
	}

	public void modifyStateMaster(StateMaster stateMaster) throws DatabaseException {
		Log.log(Log.INFO, "AdminDAO", "modifyStateMaster", "Entered");
		Connection connection = DBConnection.getConnection(false);

		try {

			CallableStatement callable = connection.prepareCall("{?=call funcUpdateState(?,?,?,?)}");

			// The state name for the region is passed along with the userId creating it.

			callable.setString(2, stateMaster.getStateCode());

			callable.setString(3, stateMaster.getStateName());

			callable.setString(4, stateMaster.getCreatedBy());

			// The error status and error message are sent from the database.
			callable.registerOutParameter(1, Types.INTEGER);
			callable.registerOutParameter(5, Types.VARCHAR);

			callable.execute();

			// If error status is 1 throw database exception.

			int functionReturn = callable.getInt(1);

			String error = callable.getString(5);

			if (functionReturn == Constants.FUNCTION_FAILURE) {

				connection.rollback();

				callable.close();

				callable = null;

				throw new DatabaseException(error);

			}

			callable.close();
			callable = null;

			connection.commit();

		} catch (SQLException e) {

			try {
				connection.rollback();
			} catch (SQLException ignore) {
			}

			Log.log(Log.ERROR, "AdminDAO", "updateStateMaster", e.getMessage());

			Log.logException(e);

			throw new DatabaseException("Unable to update state master");

		} finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO, "AdminDAO", "modifyStateMaster", "Exited");
	}

	public void modifyDistrictmaster(DistrictMaster distMaster, String modDistrict) throws DatabaseException {
		Log.log(Log.INFO, "AdminDAO", "modifyDistrictmaster", "Entered");
		Connection connection = DBConnection.getConnection(false);

		try {

			CallableStatement callable = connection.prepareCall("{?=call funcUpdateDist(?,?,?,?,?)}");

			callable.setString(2, distMaster.getStateName());
			callable.setString(3, distMaster.getDistrictName());

			callable.setString(4, modDistrict);

			callable.setString(5, distMaster.getCreatedBy());

			// The error status and error message are sent from the database.
			callable.registerOutParameter(1, Types.INTEGER);
			callable.registerOutParameter(6, Types.VARCHAR);

			callable.execute();

			// If error status is 1 throw database exception.

			int functionReturn = callable.getInt(1);

			String error = callable.getString(6);

			if (functionReturn == Constants.FUNCTION_FAILURE) {

				connection.rollback();

				callable.close();

				callable = null;

				throw new DatabaseException(error);

			}

			callable.close();
			callable = null;

			connection.commit();

		} catch (SQLException e) {

			try {
				connection.rollback();
			} catch (SQLException ignore) {
			}

			Log.log(Log.ERROR, "AdminDAO", "modifyDistrictmaster", e.getMessage());

			Log.logException(e);

			throw new DatabaseException("Unable to update district master");

		} finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO, "AdminDAO", "modifyDistrictmaster", "Exited");
	}

	public void updatePLRMaster(PLRMaster plrMaster, String createdBy) throws DatabaseException {
		Connection connection = DBConnection.getConnection(false);
		try {
			Log.log(Log.DEBUG, "AdminDAO", "updatePLRMaster",
					"*****plrMaster.getBankName() :" + plrMaster.getBankName());
			CallableStatement callable = connection
					.prepareCall("{?=call packGetInsUpdPLRDetails.funcUpdatePLRdetail(?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			// The PLR details is passed to the database.

			java.util.Date startDateParam = plrMaster.getStartDate();

			java.sql.Date sqlStartDate = new java.sql.Date(startDateParam.getTime());

			Log.log(Log.DEBUG, "AdminDAO", "updatePLRMaster", "*****plrMaster.getEndDate() :" + plrMaster.getEndDate());

			java.sql.Date sqlEndDate = null;

			if ((plrMaster.getEndDate() != null) && (!(plrMaster.getEndDate().toString()).equals(""))) {
				sqlEndDate = new java.sql.Date(plrMaster.getEndDate().getTime());
			}

			callable.setInt(2, plrMaster.getPlrId());
			callable.setDate(3, sqlStartDate);
			if (sqlEndDate != null) {
				callable.setDate(4, sqlEndDate);
			} else {
				callable.setNull(4, java.sql.Types.DATE);
			}
			callable.setDouble(5, plrMaster.getShortTermPLR());
			callable.setDouble(6, plrMaster.getMediumTermPLR());
			callable.setDouble(7, plrMaster.getLongTermPLR());
			callable.setDouble(8, plrMaster.getShortTermPeriod());
			callable.setDouble(9, plrMaster.getMediumTermPeriod());
			callable.setDouble(10, plrMaster.getLongTermPeriod());
			callable.setDouble(11, plrMaster.getBPLR());
			callable.setString(12, plrMaster.getPLR());
			callable.setString(13, createdBy);

			// The error status and error message are sent from the database.
			callable.registerOutParameter(1, Types.INTEGER);
			callable.registerOutParameter(14, Types.VARCHAR);

			callable.execute();

			// If error status is 1 throw database exception..

			int functionReturn = callable.getInt(1);

			String error = callable.getString(14);

			Log.log(Log.DEBUG, "AdminDAO", "updatePLRMaster",
					"Error Code and error for updating  " + functionReturn + " " + error);

			if (functionReturn == Constants.FUNCTION_FAILURE) {

				connection.rollback();

				callable.close();

				callable = null;

				throw new DatabaseException(error);
			}
			callable.close();

			callable = null;

		} catch (SQLException e) {

			try {
				connection.rollback();
			} catch (SQLException ignore) {
			}

			Log.log(Log.ERROR, "AdminDAO", "updatePLRMaster", e.getMessage());

			Log.logException(e);

			throw new DatabaseException("Unable to update PLR master");

		} finally {

			DBConnection.freeConnection(connection);
		}

	}

	public void sendMailtoMli(String department, ArrayList emaiIds, String complaintid) throws DatabaseException {

		try {

			Connection connection = DBConnection.getConnection(false);
			Statement stmt = connection.createStatement();
			ResultSet rs;
			String departEmailId = "";

			String departName = "";
			int noOfClaims = 0;
			String query = "";
			String status = "";

			String subject = "";

			String mailBody = "";

			query = "select dep_email_id  from CGTMSE_DEPARTMENTS where DEPARTMENT='" + department + "'";

			rs = stmt.executeQuery(query);

			// //System.out.println(query);

			while (rs.next()) {

				departEmailId = rs.getString(1);

			}

			emaiIds.add(departEmailId);

			rs.close();
			rs = null;
			stmt.close();
			stmt = null;

			for (int i = 0; i <= emaiIds.size(); i++) {

				String toMail = (String) emaiIds.get(i);

				if (i == 0) {
					subject = "Your query with CGTMSE, ID:" + complaintid;

					mailBody = "Your query with CGTMSE has been registered and id for the same is: " + complaintid
							+ ". We will revert you soon over phone/mail. \n Regards, \n Team CGTMSE,Mumbai ";
					// mailBody = "Dear User This is Your Query/Complaint to CGTMSE.Your complaint
					// id is "+complaintid+".We will revert you by Telecall/Mail soon. \n Regards,
					// \n CGTMSE, \n Mumbai. " ;
				} else {
					subject = "Query ID:" + complaintid + "received.";
					mailBody = "A query with ID:" + complaintid
							+ ". has been received for you to attend/ respond. Please attend the same";

				}
				String filePath = "";

				String host = "192.168.10.118";

				boolean sessionDebug = false;
				Properties props = System.getProperties();
				props.put("mail.host", host);
				props.put("mail.transport.protocol", "smtp");
				props.put("mail.smtp.host", host);
				props.put("mail.from", "administrator@cgtmse.in");

				Session session1 = null;
				session1 = Session.getDefaultInstance(props, null);
				session1.setDebug(sessionDebug);

				javax.mail.internet.MimeMessage msg = new javax.mail.internet.MimeMessage(session1);
				msg.setFrom(new javax.mail.internet.InternetAddress("administrator@cgtmse.in"));
				javax.mail.internet.InternetAddress[] Toaddress = { new javax.mail.internet.InternetAddress(toMail) };
				msg.setRecipients(javax.mail.Message.RecipientType.TO, Toaddress);
				msg.setSubject(subject);
				msg.setSentDate(new java.util.Date());

				if (!filePath.equals("")) {

				} else {
					msg.setText(mailBody);
				}

				Transport.send(msg);

				//System.out.println("after send  mail  to" + toMail);
				// );

			}

		} catch (Exception e) {

		}

	}

	public String insertQryDetails(MLIInfo mliInfo) throws DatabaseException {
		String compId = "";
		Connection connection = DBConnection.getConnection(false);

		try {
			CallableStatement callable = connection
					.prepareCall("{?=call PACKQUERYTRANSACTIONS.funcInsertQueryDetails(?,?,?,?,?,?,?,?,?,?)}");

			callable.setString(2, mliInfo.getBankId());

			callable.setString(3, mliInfo.getZoneId());

			callable.setString(4, mliInfo.getBranchId());

			callable.setString(5, mliInfo.getPhone());

			callable.setString(6, mliInfo.getEmailId());

			callable.setString(7, mliInfo.getDepartment());

			callable.setString(8, mliInfo.getQueryDesc());

			callable.setString(9, mliInfo.getContPerson());

			callable.registerOutParameter(10, Types.VARCHAR);

			// callable.setString(5,reactivatedBy);

			callable.registerOutParameter(1, Types.INTEGER);

			callable.registerOutParameter(11, Types.VARCHAR);

			callable.execute();

			int functionReturn = callable.getInt(1);

			compId = callable.getString(10);

			String error = callable.getString(11);

			if (functionReturn == Constants.FUNCTION_FAILURE) {

				connection.rollback();

				callable.close();

				callable = null;

				Log.log(Log.ERROR, "AdminDAO", "reactivate User", error);

				throw new DatabaseException(error);
			}

			callable.close();

			callable = null;

			connection.commit();

		} catch (SQLException e) {

			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "AdminDAO", "insertQryDetails", ignore.getMessage());
			}

			Log.log(Log.ERROR, "AdminDAO", "insertQryDetails", e.getMessage());

			Log.logException(e);

			// throw new DatabaseException("Unable to reactivate user");

		} finally {

			DBConnection.freeConnection(connection);
		}

		return compId;
	}

//Diksha 29/05/2017
	public double fetchOldGauranteeAmount(String serachType, String searchOn) throws DatabaseException {
		// //System.out.println("fetchOldGauranteeAmount called");
		double amount = 0;
		String query = "";
		ResultSet rs = null;
		Statement st = null;
		Connection connection = null;
		try {
			connection = DBConnection.getConnection(false);
			if (serachType.equals("cgbid")) // cgpan
			// if(searchOn.startsWith("CG")) // cgpan
			{

				query = "select sum(APP_APPROVED_AMOUNT) from application_detail where SSI_REFERENCE_NUMBER in (select SSI_REFERENCE_NUMBER from ssi_detail where bid='"
						+ searchOn + "')";
			}
			if (serachType.equalsIgnoreCase("cgpan")) //
			// else
			{
				query = "select sum(APP_APPROVED_AMOUNT) from application_detail where SSI_REFERENCE_NUMBER in (select SSI_REFERENCE_NUMBER from application_detail where cgpan='"
						+ searchOn + "' )";

			}
			st = connection.createStatement();
			// //System.out.println("fetchOldGauranteeAmount query11"+query);
			rs = st.executeQuery(query);
			if (rs.next()) {
				amount = rs.getDouble(1);
				// //System.out.println("fetchOldGauranteeAmount amount"+amount);
			}
		} catch (Exception e) {
			// //System.out.println("exception in fetchOldGauranteeAmount"+e.getMessage());
		} finally {
			try {
				rs.close();
				st.close();
				DBConnection.freeConnection(connection);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				// //System.out.println("exception while colsing connection"+e.getMessage());
			}

		}
		return amount;
	}

	public double fetchOldGauranteeAmountForEnahncementCases(String serachType, String searchOn)
			throws DatabaseException {
		//System.out.println("fetchOldGauranteeAmount called");
		double amount = 0;
		String query = "";
		ResultSet rs = null;
		Statement st = null;
		Connection connection = null;
		try {
			connection = DBConnection.getConnection(false);
			if (serachType.equals("cgbid")) // cgpan
			// if(searchOn.startsWith("CG")) // cgpan
			{

				query = "select sum(APP_APPROVED_AMOUNT) from application_detail where SSI_REFERENCE_NUMBER in (select SSI_REFERENCE_NUMBER from ssi_detail where bid='"
						+ searchOn + "')";
			}
			if (serachType.equalsIgnoreCase("cgpan")) //
			// else
			{
				String splitedCgpan = searchOn.substring(0, searchOn.length() - 2);
				query = " select (select sum(APP_APPROVED_AMOUNT) from application_detail where SSI_REFERENCE_NUMBER in (select SSI_REFERENCE_NUMBER from application_detail where cgpan='"
						+ searchOn + "'))-(select sum(APP_APPROVED_AMOUNT) from application_detail where cgpan like '%"
						+ splitedCgpan + "%' and cgpan !='" + splitedCgpan + "'||'TC' ) amount from dual  ";

				query = " SELECT   (SELECT SUM (APP_APPROVED_AMOUNT) " + "  FROM application_detail "
						+ "   WHERE app_status not in ('RE') and SSI_REFERENCE_NUMBER IN (SELECT SSI_REFERENCE_NUMBER "
						+ "        FROM application_detail " + "       WHERE cgpan = '" + searchOn + "')) "
						+ "   - (SELECT SUM (APP_APPROVED_AMOUNT) " + "  FROM application_detail "
						+ "  WHERE  app_status not in ('RE') and    cgpan LIKE '%" + splitedCgpan + "%'  "
						+ "      AND cgpan !='" + splitedCgpan + "'||'TC' ) " + "   amount " + " FROM DUAL ";

			}
			st = connection.createStatement();
			//System.out.println("fetchOldGauranteeAmountForEnahncementCases query" + query);
			rs = st.executeQuery(query);
			if (rs.next()) {
				amount = rs.getDouble(1);
				// //System.out.println("fetchOldGauranteeAmount amount"+amount);
			}
		} catch (Exception e) {
			// //System.out.println("exception in fetchOldGauranteeAmount"+e.getMessage());
		} finally {
			try {
				rs.close();
				st.close();
				DBConnection.freeConnection(connection);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				// //System.out.println("exception while colsing connection"+e.getMessage());
			}

		}
		return amount;
	}

	public double fetchOldOLDBaseRate(String searchOn) throws DatabaseException {
		// //System.out.println("fetchOldGauranteeAmount called");
		double amount = 0;
		String query = "";
		ResultSet rs = null;
		Statement st = null;
		Connection connection = null;
		try {
			connection = DBConnection.getConnection(false);
			if (searchOn.startsWith("CG")) // cgpan
			// if(searchOn.startsWith("CG")) // cgpan
			{
				query = "select WCP_PLR from working_capital_detail where cgpan='" + searchOn + "'";

			}

			else {
				query = "select WCP_PLR from working_capital_detail where cgpan = (select distinct a.cgpan from application_detail a , ssi_detail s where s.SSI_REFERENCE_NUMBER=a.SSI_REFERENCE_NUMBER and s.bid='"
						+ searchOn + "')";

			}
			st = connection.createStatement();
			// //System.out.println("fetchOldGauranteeAmount query22"+query);
			rs = st.executeQuery(query);
			if (rs.next()) {
				amount = rs.getDouble(1);
				// //System.out.println("fetchOldGauranteeAmount amount"+amount);
			}
		} catch (Exception e) {
			// //System.out.println("exception in fetchOldGauranteeAmount"+e.getMessage());
		} finally {
			try {
				rs.close();
				st.close();
				DBConnection.freeConnection(connection);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				// //System.out.println("exception while colsing connection"+e.getMessage());
			}

		}
		return amount;
	}

//29/05/2017 end..     
	/* Created by kailash for GST */
	public String getState(String mliBranchCode) {

		Log.log(Log.INFO, "AdminDao", "getState", "Entered");

		PreparedStatement pStmt = null;
		String stateName = null;
		ResultSet rsSet = null;
		Connection connection = DBConnection.getConnection();
		try {
			String query = "SELECT MEM_STATE_NAME FROM MLI_GST_TEMP1 where MEM_BRN_CODE=?";
			pStmt = connection.prepareStatement(query);
			pStmt.setString(1, mliBranchCode);
			rsSet = pStmt.executeQuery();
			while (rsSet.next()) {
				stateName = rsSet.getString(1);
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
		return stateName;

	}
	//Added By Deepak
		public ArrayList checkPreviousPasswordString(String password,
				String oldPassword, String userId) {

			Log.log(Log.INFO, "AdminDao", "checkPreviousPasswordString", "Entered");

			PreparedStatement pStmt = null;
			String stateName=null;
			ResultSet rsSet = null;
			Connection connection=null;
			if(connection==null){
			connection = DBConnection.getConnection();
			}
			ArrayList pwdList = new ArrayList();
			//System.out.println(password+"----password-----oldpwd---"+oldPassword+"----userId---"+userId);
			try {
				String query = "select * from(select  USR_ID,USR_PASSWORD,USR_CREATED_MODIFIED_DT from user_info_hist" +
						" WHERE  USR_ID=? order by TO_DATE(USR_CREATED_MODIFIED_DT, 'DD-MM-YYYY') DESC )where ROWNUM <= 3 ";
				pStmt = connection.prepareStatement(query);
				pStmt.setString(1, userId);
				rsSet = pStmt.executeQuery();
				//System.out.println("Query>>"+query);
				while (rsSet.next()) {				
					pwdList.add(rsSet.getString("USR_PASSWORD"));				
					//System.out.println(rsSet.getString("USR_PASSWORD")+"==========admindao list>>>>>>>>>>>>>>>>>>>>>>>>.....USR_CREATED_MODIFIED_DT "+rsSet.getDate("USR_CREATED_MODIFIED_DT").toString());
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
			return pwdList;
		}
}
