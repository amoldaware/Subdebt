package com.cgtsi.action;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;

import com.cgtsi.admin.AdminConstants;
import com.cgtsi.admin.AdminDAO;
import com.cgtsi.admin.AdminHelper;
import com.cgtsi.admin.Message;
import com.cgtsi.admin.PasswordManager;
import com.cgtsi.admin.User;
import com.cgtsi.admin.UserManager;
import com.cgtsi.common.Constants;
import com.cgtsi.common.DatabaseException;
import com.cgtsi.common.Log;
import com.cgtsi.common.Mailer;
import com.cgtsi.common.MailerException;
import com.cgtsi.common.NoUserFoundException;
import com.cgtsi.registration.Registration;
import com.cgtsi.util.DBConnection;
import com.sun.mail.handlers.message_rfc822;

public class MLIUser extends UserManager {
	AdminHelper adminHelper;
	AdminDAO adminDAO;

	public MLIUser() {
		adminHelper = new AdminHelper();
		adminDAO = new AdminDAO();
	}

	public String createUser(String creatingUser, User user, boolean sendMail)throws Exception 
	{
		String userId = null;
		String bankId = "", zoneId = "", branchId = "", memberId = "";
		String encryptedPassword = "", password = "";
		String firstName = "" , middleName = "", lastName = "", name ="", userEmail = "",message = "";
		try 
		{
			if (creatingUser != null && !creatingUser.equals("") && user != null) 
			{
				Registration registration = new Registration();
				bankId = user.getBankId();
				zoneId = user.getZoneId();
				branchId = user.getBranchId();
				memberId = bankId + zoneId + branchId;

				bankId = user.getBankId();
				Log.log(Log.DEBUG, "Administrator", "createUser", "bankId " + bankId);

				PasswordManager passwordManager = new PasswordManager();
				encryptedPassword = adminHelper.generatePassword();
				password = passwordManager.decryptPassword(encryptedPassword);
				user.setPassword(encryptedPassword);

				HashMap<String,String> addUserData = new HashMap<>();
				addUserData = addUser(creatingUser, user);// userId is created in the database..
				if(addUserData != null)
				{
					for(String key : addUserData.keySet())
					{
						userId = key; 
						message = addUserData.get(key);
					}
				}
				Log.log(Log.DEBUG, "MLIUser", "createUser", "userId " + userId);

				
				firstName = user.getFirstName();
				middleName = user.getMiddleName();
				lastName = user.getLastName();
				name = firstName + middleName + lastName;
				userEmail = user.getEmailId();

				ArrayList emailToAddresses = new ArrayList();
				emailToAddresses.add(userEmail);

				System.out.println("Email is :: [" + userEmail + "]\t emailToAddresses :: [" + emailToAddresses + "]");

				ArrayList mailToAddresses = new ArrayList();
				Mailer mailer = new Mailer();
				String subject = "User id and password";
				String messageBody = "Dear Member " + name + "\n\n Welcome to www[dot]eclgs[dot]com,   "
						+ "\n Your account has been successfully activated."
						+ "\nWe recommend you to login in to your account & have your credentials updated." +
						// "Member Id , User Id and Password for "+name+
						"\n\n Your Member Id :  " + memberId + "\n User Id :   " + userId + "\n Password :  " + password
						+ "\n\n You will be able to perform application lodgement for guarantee approval using Application Processing Option."
						+ "\nPlease include it in your safe senders list."
						+ "\nFeel free to contact us if you have any issues during your visit to eclgs site. "
						+ "\n We wish you a great success." + "\n\nWarm Regards," + "\nTeam CGTMSE \n\n";

				
				User fromUser = getUserInfo(creatingUser);// Get the Email id of the logged in user(sender)

				String fromEmailId = "diksha.zore@pathinfotech.com";
				Log.log(Log.DEBUG, "MLIUser", "createUser", "fromEmailId " + fromEmailId);

				if (bankId == Constants.CGTSI_USER_BANK_ID) 
				{
					if (sendMail) 
					{
						User adminUser = getUserInfo(AdminConstants.ADMIN_USER_ID);
						Message emailMessage = new Message(emailToAddresses, null, null, subject, messageBody);
						emailMessage.setFrom(fromEmailId);
						mailToAddresses.add(AdminConstants.ADMIN_USER_ID);
						Message mailMessage = new Message(mailToAddresses, null, null, subject, messageBody);
						mailMessage.setFrom(creatingUser);
						/*sendMail(mailMessage);
						try {
							mailer.sendEmail(emailMessage);
						} catch (MailerException mailerException) {
							System.out.println("Email could not be sent.UserId is:" + mailerException.getMessage());
							throw new MailerException("Email could not be sent.UserId is:" + userId);
						}*/
					}
				} 
				else 
				{
					ArrayList privileges = getPrivilegesForRole(AdminConstants.MLI_USER_ROLE);// Assign OO Role to the MLI user.
					ArrayList noRoles = new ArrayList();
					noRoles.add(AdminConstants.MLI_USER_ROLE);
					adminDAO.assignRolesAndPrivileges(noRoles, privileges, userId, creatingUser);
					
					if(noRoles != null || privileges != null){
						noRoles.clear();// Clear the memory.
						privileges.clear();

						noRoles = null;
						privileges = null;
					}
					if (sendMail) 
					{
						zoneId = Constants.CGTSI_USER_BRANCH_ID;// Get the member Id of the user
						branchId = Constants.CGTSI_USER_ZONE_ID;
						memberId = bankId + zoneId + branchId;

						ArrayList userIds = getUsers(memberId);// Get users for that memberId.
						int size = userIds.size();

						for (int i = 0; i < size; i++) // Check which user is the NO of HO.
						{
							String noUserId = (String) userIds.get(i);
							Log.log(Log.DEBUG, "Administrator", "createUser", "noUserId " + noUserId);

							ArrayList roles = getRoles(noUserId);// Get the roles for the user.
							int roleSize = roles.size();

							for (int j = 0; j < roleSize; j++) // Check if the roles of the user icludes "NO" role.
							{
								if (roles.get(j).equals(AdminConstants.NO_ROLE)) 
								{
									User NOUser = new User();// Get the email id of the NO of HO user
									NOUser = getUserInfo(noUserId);
									String noEmailId = NOUser.getEmailId();
									Log.log(Log.DEBUG, "Administrator", "createUser", "noEmailId " + noEmailId);

									emailToAddresses.add(noEmailId);					
									// Email To addresses.	
									// Email is sent to the user notifying the
									// userId and Password.
									Message emailMessage = new Message(emailToAddresses, null, null, subject,messageBody);

									// Get the Email id of the logged in
									// user(sender) and set it in From of
									// message object.
									emailMessage.setFrom(fromEmailId);
									// Mail is sent to the NO of HO user
									// notifying the userId and Password.
									mailToAddresses.add(noUserId);// Mail To
																	// Addresses.
									Message mailMessage = new Message(mailToAddresses, null, null, subject,
											messageBody);
									mailMessage.setFrom(creatingUser);
									// Mail is sent.
									/*sendMail(mailMessage);

									try {
										mailer.sendEmail(emailMessage);
									} catch (MailerException mailerException) {
										throw new MailerException("Email could not be sent.UserId is:" + userId);
									}*/
								}
							}
						}

					}
				}
			}
		} catch (DatabaseException e) {
			e.printStackTrace();
			throw new DatabaseException(e.getMessage());
		}
		return userId;
	}

	private ArrayList getPrivilegesForRole(String roleName) throws DatabaseException 
	{
		try
		{
			if (roleName != null && !roleName.equals("")) 
			{
				return adminDAO.getPrivilegesForRole(roleName);
			}
		}
		catch(Exception err)
		{
			throw new DatabaseException();
		}
		return null;
	}

	private void sendMail(Message message) throws DatabaseException 
	{
		try {
			if (message != null) {
				adminDAO.sendMail(message);
			}
		} catch (Exception e) {
			throw new DatabaseException();
		}
	}

	private User getUserInfo(String userID) throws DatabaseException, NoUserFoundException {
		User user = null;
		try {
			// Gets the user information based on the userID passed
			if (userID != null) {
				user = adminDAO.getUserInfo(userID); // Need to work on it
			}
		} catch (Exception e) {
			throw new DatabaseException();
		}
		return user;
	}

	private ArrayList getRoles(String userId) throws DatabaseException {
		try {
			return adminDAO.getRoles(userId);
		} catch (Exception e) {
			throw new DatabaseException();
		}
	}

	private ArrayList getUsers(String memberID) throws NoUserFoundException, DatabaseException {
		ArrayList userIds = null;
		try {
			if (memberID != null) {
				userIds = adminDAO.getUsers(memberID);
				// If no users exists for the member throw an exception.
				if (userIds == null) {
					throw new NoUserFoundException();
				}
			}
		} catch (Exception e) {
			throw new NoUserFoundException();
		}
		return userIds;
	}

	public HashMap<String,String> addUser(String createdBy, User user) throws DatabaseException {
		
		String userId = null;
		CallableStatement callable = null;
		Connection connection = null;
		System.out.println("User Data ::::" + user.getFirstName() + "\t" + user.getLastName() + user.getBankId());
		String message = "";
		HashMap<String,String> addUserData = new HashMap<>();
		try 
		{
			if(connection == null)
			{
				connection = DBConnection.getConnection(false);
			}
			callable = connection.prepareCall("{call FUNCINSERTUSERDETAIL_New(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
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
			callable.setString(13, user.getUser_role());
			callable.setString(14, user.getEmployee_id());
			callable.registerOutParameter(1, Types.INTEGER);
			callable.registerOutParameter(15, Types.VARCHAR);// userID
			callable.registerOutParameter(16, Types.VARCHAR);// error code
			callable.setString(17, user.getMobileNo());
			callable.setString(18, user.getUserType());
			callable.setString(19, user.getUserId());
			callable.execute();
			userId = callable.getString(15);

			int functionReturn = callable.getInt(1);
			String error = callable.getString(16);
			if (functionReturn == Constants.FUNCTION_FAILURE) 
			{
				if(connection != null){connection.rollback();}
				if(callable != null){
				callable.close();
				callable = null;}
				Log.log(Log.ERROR, "MLIUser", "add User", error);
				throw new DatabaseException(error);
			}
			else if (functionReturn == Constants.FUNCTION_SUCCESS) 
			{
				message = error;
				if(callable != null){
					callable.close();
					callable = null;
				}
				connection.commit();
			}
			addUserData.put(userId, message);
			
		} catch (SQLException e) 
		{
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(Log.ERROR, "MLIUser", "add User", ignore.getMessage());
			}
			Log.log(Log.ERROR, "MLIUser", "add User", e.getMessage());
			Log.logException(e);
			throw new DatabaseException("Unable to add user");

		} finally 
		{
			try
			{
				if(callable != null){
					callable.close();
					callable = null;
				}
				if(connection != null){
				DBConnection.freeConnection(connection);
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		return addUserData;
	}	
}
