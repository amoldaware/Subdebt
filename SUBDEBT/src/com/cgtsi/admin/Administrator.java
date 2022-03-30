//Source file: C:\\CGTSI\\Vss\\com\\cgtsi\\admin\\Administrator.java

package com.cgtsi.admin;

import java.util.ArrayList;
import com.cgtsi.common.DatabaseException;
import com.cgtsi.common.Log;
import com.cgtsi.common.MailerException;
import com.cgtsi.common.MessageException;
import com.cgtsi.common.NoDataException;
import com.cgtsi.common.NoUserFoundException;
import com.cgtsi.common.Mailer;
import com.cgtsi.common.Constants;
import com.cgtsi.registration.MLIInfo;
import com.cgtsi.registration.Registration;
import com.cgtsi.claim.ClaimsProcessor;
import com.cgtsi.reports.ReportDAO;
import java.sql.SQLException;
import java.util.Calendar;



/**
 * Administrator is responsbile for controlling the user's workflow.
 * By assigning roles and privileges he controls the user's access rights.
 */
public class Administrator extends UserManager
{
	 AdminHelper adminHelper;
	 AdminDAO adminDAO;


   /**
    * @roseuid 39B878970339
    */
   public Administrator()
   {
	   adminDAO=new AdminDAO();
	   adminHelper=new AdminHelper();

   }

   /**
    * This method is responsible for creating any user.including CGTSI user.
    * For creating bank user also, this method is called. Using the bank,zone and
    * branch ids CGTSI and bank users are differentiated.
    * @param user
    * @return int
    * @roseuid 3972AB8B0079
    */
   public String createUser(String creatingUser,User user,boolean sendMail)throws DatabaseException,MailerException,NoUserFoundException
	{
		 String userId=null;

		 if (creatingUser!=null&&!creatingUser.equals("")&&user!=null){

			 //Check if the member is deactivated.
			Registration registration=new Registration();
			String bankId=user.getBankId();
			String zoneId=user.getZoneId();
			String branchId=user.getBranchId();
			//registration.getMemberDetails(bankId,zoneId,branchId);
			String memberId=bankId+zoneId+branchId;
      
			 //Get the bank id of the user to be added.
			 bankId  =user.getBankId();
			 Log.log(Log.DEBUG,"Administrator","createUser","bankId "+bankId);

			 //Get the encrypted password decrypt it and set encrypted password in user object.
			 PasswordManager passwordManager=new PasswordManager();
			 String encryptedPassword= adminHelper.generatePassword();
			 String password=passwordManager.decryptPassword(encryptedPassword);
			 user.setPassword(encryptedPassword);

			 //The created user is entered into the database.
			 userId=adminDAO.addUser(creatingUser,user);
			 Log.log(Log.DEBUG,"Administrator","createUser","userId "+userId);

			//Get the name of the user.
			 String firstName=user.getFirstName();
			 String middleName=user.getMiddleName();
			 String lastName=user.getLastName();
			 String name=firstName+middleName+lastName;
			 String userEmail=user.getEmailId();

			 ArrayList emailToAddresses=new ArrayList();

			 //Add the emailId of the user created into the To Address list.
			 emailToAddresses.add(userEmail);

			 ArrayList mailToAddresses=new ArrayList();
			 Mailer mailer=new Mailer();

			//The subject and message body to be sent in the mail.
			  String subject="User id and password";
			/*
			 * String messageBody="User Id and Password for  "+name+ "\nUser Id    "+userId+
			 * "\nPassword   " + password;
			 */
			  
			  String messageBody="Dear Member "+name+
						"\n\n Welcome to www[dot]eclgs[dot]com,   "
						+ "\n Your account has been successfully activated."+
						"\nWe recommend you to login in to your account & have your credentials updated."+
						//"Member Id , User Id and Password for  "+name+
				 "\n\n Your Member Id :  "+memberId+
				 "\n User Id :   "+userId+ 
				 "\n Password :  " + password+
				 "\n\n You will be able to perform application lodgement for guarantee approval using Application Processing Option."+
				 "\nPlease include it in your safe senders list."+
				 "\nFeel free to contact us if you have any issues during your visit to eclgs site. "+
				 "\n We wish you a great success."+
				 "\n\nWarm Regards,"+
				 "\nTeam CGTMSE \n\n"
				// "\nhttps://www.eclgs.com"+
				 //"\nhttps://www.cgtmse.in "
				 ;


			//Get the Email id of the logged in user(sender)
			  User fromUser=getUserInfo(creatingUser);
			  
			/* String fromEmailId=fromUser.getEmailId(); */
			  String fromEmailId="diksha.zore@pathinfotech.com";
			  //System.out.println("fromEmailId=="+fromEmailId);
			  Log.log(Log.DEBUG,"Administrator","createUser","fromEmailId "+fromEmailId);

			 //Bank Id will be null for CGTSI User.
			 if (bankId==Constants.CGTSI_USER_BANK_ID){

				 //If sendmail is true perform the operations inside the loop.
				 if(sendMail){

					 //Get the user object and then the email ID for the Admin user.
					 User adminUser=getUserInfo(AdminConstants.ADMIN_USER_ID);
					 // 01String adminEmailId=adminUser.getEmailId();
					 //System.out.println("adminEmailId==");
					 //03Log.log(Log.DEBUG,"Administrator","createUser","adminEmailId "+adminEmailId);

					 //02emailToAddresses.add(adminEmailId);//Email To addresses

					 //Email is sent to the Admin user notifying the userId and Password.
					 Message emailMessage=new Message(emailToAddresses,null,null,subject,messageBody);

					 //Get the Email id of the logged in user(sender) and set it in From of message object.
					 emailMessage.setFrom(fromEmailId);

    				//Mail is sent to the Admin user notifying the userId and Password.

					mailToAddresses.add(AdminConstants.ADMIN_USER_ID);//Mail To addresses

					Message mailMessage=new Message(mailToAddresses,null,null,subject,messageBody);
					mailMessage.setFrom(creatingUser);
					//Mail is sent.
					sendMail(mailMessage);

					try{
						 //Email is sent.
						 mailer.sendEmail(emailMessage);
					}
					catch(MailerException mailerException ){

						throw new MailerException("Email could not be sent.UserId is:"+userId);
					}
				 }

			 }
				 else{

					 //Assign OO Role to the MLI user.
					 ArrayList privileges=getPrivilegesForRole(AdminConstants.MLI_USER_ROLE);
					 ArrayList noRoles=new ArrayList();
					 noRoles.add(AdminConstants.MLI_USER_ROLE);
					 adminDAO.assignRolesAndPrivileges(noRoles,privileges,userId,creatingUser);

					 //Clear the memory.
					 noRoles.clear();
					 privileges.clear();

					 noRoles=null;
					 privileges=null;

					 if (sendMail){
					   //Get the member Id of the user
					   zoneId=Constants.CGTSI_USER_BRANCH_ID;
					   branchId=Constants.CGTSI_USER_ZONE_ID;
					   memberId=bankId+zoneId+branchId;

					   //Get users for that memberId.
					   ArrayList userIds=getUsers(memberId);
					   int size=userIds.size();

					   //Check which user is the NO of HO.
					   for(int i=0;i<size;i++){
						   String noUserId=(String)userIds.get(i);
						   Log.log(Log.DEBUG,"Administrator","createUser","noUserId "+noUserId);

						   //Get the roles for the user.
						   ArrayList roles=getRoles(noUserId);
						   int roleSize=roles.size();

						   //Check if the roles of the user icludes "NO" role.
						   for(int j=0;j<roleSize;j++){
							   if (roles.get(j).equals(AdminConstants.NO_ROLE)){

								  //Get the email id of the NO of HO user
								  User NOUser=new User();
								  NOUser=getUserInfo(noUserId);
								  String noEmailId=NOUser.getEmailId();
								  Log.log(Log.DEBUG,"Administrator","createUser","noEmailId "+noEmailId);

								  emailToAddresses.add(noEmailId);//Email To addresses.

								  //Email is sent to the user notifying the userId and Password.
								  Message emailMessage=new Message(emailToAddresses,null,null,subject,messageBody);


								  //Get the Email id of the logged in user(sender) and set it in From of message object.
								  emailMessage.setFrom(fromEmailId);

								 //Mail is sent to the NO of HO user notifying the userId and Password.
								  mailToAddresses.add(noUserId);//Mail To Addresses.
								  Message mailMessage=new Message(mailToAddresses,null,null,subject,messageBody);
								  mailMessage.setFrom(creatingUser);
								  //Mail is sent.
								  sendMail(mailMessage);

								try{
									  mailer.sendEmail(emailMessage);
								 }
								catch(MailerException mailerException ){

									throw new MailerException("Email could not be sent.UserId is:"+userId);
								}
							   }
						   }
					   }

					 }
				 }

			 }
		 return userId;
	  }


     /**
    * This method would be used to update the master tables such as Alert
    * Master,Exception Master, Parameter Master etc.
    * @param master
    * @roseuid 3972AB8B0088
    */
   public void updateMaster(Master master,String userId) throws DatabaseException,DuplicateException
   {
		//calls the update master method depending on the object passed

	   if (master!=null){
		    master.updateMaster(userId);
	   }
   }

   /**
    * Whenever a need to broadcast a message. This messages are broadcasted for a
    * member (ZO/RO or BO). User of the member is identified and these broadcasted
    * messages are displayed to them when the next log in.
    * Only last broadcasted message is displayed to the user.
    * @param message
    * @roseuid 3972AB8B008A
    */
   public void broadcastMessage(BroadCastMessage message,String createdBy)throws DatabaseException,NoUserFoundException
   {
   	   		if (message!=null&&createdBy!=null){

				ArrayList userIds=new ArrayList();
				ArrayList allUsers=new ArrayList();

 		   		if (message.getAllHos()==true){
					//Retrieve the userIds for all the HOs.
					userIds=getAllHoUsers();
					Log.log(Log.DEBUG,"Administrator","broadcastMessage","userIds"+userIds);
					message.setUserIds(userIds);
				    adminDAO.broadcastMessage(message,createdBy);
				 }

 		  		 else if (message.getMemberOfBank()==true){
						//Get the userIds for the bank selected.
						String bankName=message.getBankName();
						int first=bankName.indexOf("(");
						int last=bankName.indexOf(")");
						String memberId=bankName.substring(first+1,last);
						Log.log(Log.DEBUG,"Administrator","broadcastMessage","memberId "+memberId);
						userIds=getUsers(memberId);
						Log.log(Log.DEBUG,"Administrator","broadcastMessage","userIds "+userIds);
						message.setUserIds(userIds);
						adminDAO.broadcastMessage(message,createdBy);
	 		    }

	 		    else if (message.getMembersOfZoOrRO()==true){

	 		    		//Get the userIds for the zones selected.

						String[] zones=message.getZoneRegions();

						int length=zones.length;

						for(int i=0;i<length;i++)
						{
							String zone=zones[i];

							int first=zone.indexOf("(");

							int last=zone.indexOf(")");

							String memberId=zone.substring(first+1,last);

							Log.log(Log.DEBUG,"Administrator","broadcastMessage","memberId "+memberId);

							userIds=getUsers(memberId);

							Log.log(Log.DEBUG,"Administrator","broadcastMessage","userIds "+userIds);

							allUsers.addAll(userIds);

						}

						message.setUserIds(allUsers);

						adminDAO.broadcastMessage(message,createdBy);
			   }

	 		  else if (message.getMembersOfBranch()==true){

	 					//Get the userIds for the branches selected.

					   String []branches=message.getBranchNames();

					   int length=branches.length;

					   for(int i=0;i<length;i++){

						   String branch=branches[i];

						   int first=branch.indexOf("(");

						   int last=branch.indexOf(")");

						   String memberId=branch.substring(first+1,last);

						   Log.log(Log.DEBUG,"Administrator","broadcastMessage","memberId "+memberId);

						   userIds=getUsers(memberId);

						   Log.log(Log.DEBUG,"Administrator","broadcastMessage","userIds "+userIds);

						   allUsers.addAll(userIds);
					   }
					   message.setUserIds(allUsers);

					   adminDAO.broadcastMessage(message,createdBy);
 		  	  }

	 		  else if (message.getNoOfBank()==true){

	 		  			//Get the userIds for the NO of the bank chosen.

						ArrayList roles=new ArrayList();

						ArrayList users=new ArrayList();

						String bankName=message.getBankName();

						int first=bankName.indexOf("(");

						int last=bankName.indexOf(")");

						String memberId=bankName.substring(first+1,last);

						userIds=getUsers(memberId);

						int size=userIds.size();

						for(int i=0;i<size;i++){

							String userId=(String)userIds.get(i);

							//Get the roles for the user.

							roles=getRoles(userId);

							int roleSize=roles.size();

							//Check if the roles of the user icludes "NO" role.

							for(int j=0;j<roleSize;j++){

								if (roles.get(j).equals(AdminConstants.NO_ROLE)){

									users.add(userId);

									message.setUserIds(users);

									adminDAO.broadcastMessage(message,createdBy);
								}
				       		}
	 		 		    }
	 		   }

	 		  else if(message.getNoOfZonesRegions()==true){

					  //Get the userIds for the NO of the zones chosen.

						  ArrayList roles=new ArrayList();

						  ArrayList users=new ArrayList();

						  String[] zoneNames=message.getZoneRegions();

						  int size=zoneNames.length;

						  for(int i=0;i<size;i++){

						  	  String zoneName=zoneNames[i];

							  Log.log(Log.DEBUG,"Administrator","broadcastMessage","zoneName "+zoneName);

							  int first=zoneName.indexOf("(");

							  int last=zoneName.indexOf(")");

							  String memberId=zoneName.substring(first+1,last);

							  userIds=getUsers(memberId);

							  int userSize=userIds.size();

							  for(int j=0;j<userSize;j++){

								  String userId=(String)userIds.get(j);

								  //Get the roles for the user.

								  roles=getRoles(userId);

								  int roleSize=roles.size();

								 //Check if the roles of the user includes "MEMBER NO" role.

								  for(int k=0;k<roleSize;k++){

									  if (((String)roles.get(k)).equalsIgnoreCase(AdminConstants.MEMBER_NO_ROLE)){

									  	  users.add(userId);
									  }
								  }
							  }
						  allUsers.addAll(users);
			    		  }
						  message.setUserIds(allUsers);

						  adminDAO.broadcastMessage(message,createdBy);
	 		  }
			  else if(message.getNoOfBranches()==true){
					  //Get the userIds for the NO of the branch chosen.
					  ArrayList roles=new ArrayList();
					  ArrayList users=new ArrayList();

				      String[] branchNames=message.getBranchNames();
					  int size=branchNames.length;
					  for(int i=0;i<size;i++){
							String branchName=branchNames[i];
							int first=branchName.indexOf("(");
							int last=branchName.indexOf(")");
							String memberId=branchName.substring(first+1,last);
							userIds=getUsers(memberId);
							int userSize=userIds.size();
							for(int j=0;j<userSize;j++){
								String userId=(String)userIds.get(j);
								//Get the roles for the user.
								roles=getRoles(userId);
								int roleSize=roles.size();
							   //Check if the roles of the user includes "MEMBER NO" role.
								for(int k=0;k<roleSize;k++){
									if (((String)roles.get(k)).equalsIgnoreCase(AdminConstants.MEMBER_NO_ROLE)){
										users.add(userId);
									}
         						 }
						      }
						allUsers.addAll(users);
					     }
						message.setUserIds(allUsers);
						adminDAO.broadcastMessage(message,createdBy);
				}
			else if (message.isAllMembers()==true){
					Administrator administrator=new Administrator();
					ArrayList activeUsers=administrator.getAllActiveUser();
					int size=activeUsers.size();
					ArrayList users=new ArrayList();
					for(int i=0;i<size;i++){
						User user=(User)activeUsers.get(i);
						if(user == null)
						{
							continue;
						}
						/*String bankId=user.getBankId();
						if(bankId.equals(Constants.CGTSI_USER_BANK_ID)){*/
							String userId=user.getUserId();
							users.add(userId);
						/*}*/
					}
					message.setUserIds(users);
					adminDAO.broadcastMessage(message,createdBy);
				 }
  		 }
  }

   /**
    * Whenever logging is required for auditing
    * @return int
    * @roseuid 3972AB8B008C
    */
  /* private int logDetails()
   {
    return 0;
   }*/

   /**
    * Wheneve a need raised to add a new role
    *
    * @param role
    * @roseuid 3972AB8B008D
    */
   public void addRole(Role role, User user) throws DuplicateException,DatabaseException
   {
		if(role!=null){
   		   adminDAO.addRole(role, user);
		}
   }


   /**
    * Whenever the admin wants to modify a role. This method modifies the role and
    * its associated privileges.
    * @param role
    * @roseuid 3972AB8B008F
    */
   public void modifyRole(Role role, User user)throws DatabaseException
   {
		if(role!=null){
		   adminDAO.modifyRole(role, user);
		}
   }

   /**
    * Whenever the user's roles and privileges are modified or assigned
    * @param roles
    * @param privileges
    * @param userID
    * @roseuid 3972AB8B0091
    */
   public void assignRolesAndPrivileges(ArrayList roles,
   					ArrayList privileges, String userID,String creatingUser)throws DatabaseException
   {
   		if((roles!=null)&&(privileges!=null)&&(userID!=null)){
			adminDAO.assignRolesAndPrivileges(roles,privileges,userID,creatingUser);
   		}
   }
   public ArrayList getRoles(String userId) throws DatabaseException
   {
   		return adminDAO.getRoles(userId);
   }

   public ArrayList getPrivileges(String userId) throws DatabaseException
   {
		return adminDAO.getPrivileges(userId);
   }

   public void modifyRolesAndPrivileges(ArrayList roles,
   					ArrayList privileges, String userID,String creatingUser)throws DatabaseException
	  {
		   if((roles!=null)&&(privileges!=null)&&(userID!=null))
		   {
			   adminDAO.modifyRolesAndPrivileges(roles,privileges,userID,creatingUser);
		   }
	  }

   /**
    * Admin user can send mails to all the users. All other users can send mails to
    * administrator only.
    * @param message
    * @roseuid 3972AB8B0094
    */
   public void sendMail(Message message) throws DatabaseException
   {
   		if(message!=null){
			adminDAO.sendMail(message);
   		}
   }
   /**
   * 
   * added by sukumar@path for providing password change mail
   * @param userId
   * @param password
   * @throws com.cgtsi.common.DatabaseException
   */
    public String sendPasswordMail(String userId,String password,String loginUserId) throws DatabaseException
   {
      String emailId = null;
   		if(userId!=null){
			emailId = adminDAO.sendPasswordMail(userId,password,loginUserId);
   		}
      
      return emailId;
   }
   

   /**
    * This method is used to get all the roles available in the database.
    * @return ArrayList
    * @roseuid 3977D85D0177
    */
   public ArrayList getAllRoles()throws DatabaseException
   {
  		ArrayList roles=adminDAO.getAllRoles();
   		return roles;
   }

   /**
    * This method is used to view the master details for a selected master.
    * @param masterId
    * @return com.cgtsi.admin.Master
    * @roseuid 39780F69011B
    */
   public Master viewMasterDetails(int masterId)
   {
    	return null;
   }

   /**
    * This method is used to get all the deactivated users. This method is invoked
    * when Admin user tries to activate a deactivated user.
    * @param memberID
    * @return ArrayList
    * @roseuid 3981282F00F9
    */
   public ArrayList getDeactivatedUsers(String memberID) throws NoUserFoundException,DatabaseException
   {
		ArrayList deactivatedUsers=null;
		if (memberID!=null){
			deactivatedUsers=adminDAO.getAllDeactivatedUsers(memberID);
		}
		return deactivatedUsers;
   }

   /**
    * This method is used to retrieve all the privileges available in the database.
    * @return ArrayList
    * @roseuid 39812E840136
    */
   public ArrayList getAllPrivileges()throws DatabaseException
   {
		ArrayList allPrivileges=adminDAO.getAllPrivileges();
		return allPrivileges;
   }

   /**
    * This method is used to retrieve the user information for a given user id.
    * @param userID
    * @return User
    * @roseuid 398150F80047
    */
   public User getUserInfo(String userID)throws DatabaseException,NoUserFoundException
   {
   		User user=null;

   		//Gets the user information based on the userID passed
	   	if (userID!=null){
	 	    user=adminDAO.getUserInfo(userID);
	  	 }
	  	 return user;
   }

   /**
    * Gets all the users belong to a member.If the member belong to ZO or RO or HO
    * all the users belong to its affiliated member also retrieved.
    * @param memberID - Members could be HO/ZO/RO/BO
    * @return ArrayList
    * @roseuid 399E328701E6
    */
   public ArrayList getAllUsers(String memberID) throws NoUserFoundException,DatabaseException
   {
   		ArrayList activeBankUsers=null;
		if (memberID!=null){
			activeBankUsers=adminDAO.getActiveBankUsers(memberID);
			//If no users exists for the member throw an exception.
   			if (activeBankUsers==null){
   				throw new NoUserFoundException();
   			}
   		}
   		return activeBankUsers;
   }
 /**
  * This method gets only those active users under the particular HO/Zone/Region based on the memberId passed.
  * Ramesh rp14480
  * 29 NOV 2003
  */
	 public ArrayList getUsers(String memberID) throws NoUserFoundException,DatabaseException
		 {
			  ArrayList userIds=null;
			  if (memberID!=null){
				  userIds=adminDAO.getUsers(memberID);
				  //If no users exists for the member throw an exception.
				  if (userIds==null){
					  throw new NoUserFoundException();
				  }
			  }
			  return userIds;
	 }
 /**
 * This method gets the all the active users from all the HOs.
 * Ramesh rp14480
 * 29 NOV 2003
 */
	 public ArrayList getAllHoUsers() throws NoUserFoundException,DatabaseException
			 {
				  ArrayList userIds=adminDAO.getAllHoUsers();
					  //If no users exists for the member throw an exception.
					  if (userIds==null){
						  throw new NoUserFoundException();
					  }
				  return userIds;
	 }

   /**
    * This method is invoked after the Admin enters the NO of HO user details.
    * @param userNO
    * @roseuid 399E68C702B8
    */
   public String createNOUser(String createdBy,User userNO,boolean sendEmail)throws DatabaseException,NoUserFoundException,MailerException
   {
   	   String userId=null;
	   if (userNO!=null)
	   {
		  //Add the NO User into database.
		  AdminHelper adminHelper=new AdminHelper();
		  String password=adminHelper.generatePassword();
		  userNO.setPassword(password);

		  userId=adminDAO.addUser(createdBy,userNO);	//NO user is added.
		  String bankId=userNO.getBankId();
		  String zoneId=userNO.getZoneId();
		  String branchId=userNO.getBranchId();
		  String memberId=bankId+zoneId+branchId;

		  //Assign the default NO Role to the user.
		  ArrayList privileges=getPrivilegesForRole(AdminConstants.NO_ROLE);
		  ArrayList noRoles=new ArrayList();
		  noRoles.add(AdminConstants.NO_ROLE);
		  adminDAO.assignRolesAndPrivileges(noRoles,privileges,userId,createdBy);

		  //Clear the memory.
		  noRoles.clear();
		  privileges.clear();

		  noRoles=null;
		  privileges=null;

		  try{

		  if(sendEmail){

			Mailer mailer=new Mailer();

			ArrayList emailToAddresses=new ArrayList();

			String noEmail=userNO.getEmailId();

			Log.log(Log.DEBUG,"Administrator","createNOUser","noEmail"+noEmail);

			emailToAddresses.add(noEmail);//Email To addresses

			//Get the Email id of the logged in user(sender)

			User fromUser=getUserInfo(createdBy);

			String fromEmailId=fromUser.getEmailId();

			Log.log(Log.DEBUG,"Administrator","createNOUser","fromEmailId "+fromEmailId);

			//Get the encrypted password decrypt it.

			PasswordManager passwordManager=new PasswordManager();

			String decryptedPassword=passwordManager.decryptPassword(password);

			//The subject and message body to be sent in the mail.

			String subject="User id and password";

			String name=userNO.getFirstName();

			String messageBody="Dear Member"+name+
					"\nWelcome to https://www.eclgs.com. Your account has been successfully activated."+
					"\nWe recommend you to login in to your account & have your credentials updated."+
					//"Member Id , User Id and Password for  "+name+
			 "\n Your Member Id :  "+memberId+
			 "\n User Id :   "+userId+ 
			 "\nPassword :  " + decryptedPassword+
			 "\n\nYou will be able to perform application lodgement for guarantee approval using “Application Processing” Option."+
			 "\nPlease include it in your safe senders list."+
			 "\nFeel free to contact us if you have any issues during your visit to https://app.eclsg.com. "+
			 "\n We wish you a great success."+
			 "\nWarm Regards,"+
			 "\nTeam ECLGS / CGTMSE"+
			 "\nhttps://www.eclgs.com"+
			 "\nhttps://www.cgtmse.in "
			 ;

			//Email is sent to the Admin user notifying the userId and Password.
			Message emailMessage=new Message(emailToAddresses,null,null,subject,messageBody);

			//Get the Email id of the logged in user(sender) and set it in From of message object.
			emailMessage.setFrom(fromEmailId);

			//Email is sent.
			mailer.sendEmail(emailMessage);
		  }
	   }
		  catch(MailerException mailerException){
		  	throw new MailerException("Email could not be sent.UserId is:"+userId);
		  }
	   }
		return userId;

   }
   /**
    * This method deactivates the given member.
    *
    * @param bankId
    * @param zoneID
    * @param branchId
    * @roseuid 399F5C8C01D8
    */
   public void deactivateMember(String bankId, String zoneId, String branchId,String userId,String reason)throws DatabaseException
   {
	    ClaimsProcessor processor = new ClaimsProcessor();
		if ((bankId!=null)&&(zoneId!=null)&&(branchId!=null)){
            boolean canBeDeactivated = processor.checkIfMemCanBeDeactivated(bankId,zoneId,branchId);
            if(canBeDeactivated)
            {
				adminDAO.deactivateMember(bankId,zoneId,branchId,userId,reason);
			}
			else
			{
				throw new DatabaseException("There are pending Claim Applications/Claim Settlements for the Member. The Member cannot be deactivated.");
			}
		}
   }

   /**
    * Admin user can reactivate a deactivated member. This method is used to
    * accopalish  that task.
    * @param bankId
    * @param zoneId
    * @param branchId
    * @roseuid 399F5D3801C2
    */
   public void reactivateMember(String bankId, String zoneId, String branchId,String userId,String reason)throws DatabaseException
   {
		if ((bankId!=null)&&(zoneId!=null)&&(branchId!=null)){
			adminDAO.reactivateMember(bankId,zoneId,branchId,userId,reason);
		}
   }

   /**
    * This method is used to get all the designations available in the database.
    * @return ArrayList
    * @roseuid 39AE1A1F01E2
    */
   public ArrayList getAllDesignations()throws DatabaseException
   {
	   ArrayList designations=adminDAO.getAllDesignations();
   	   return designations;
   }

   /**
    * This method returns all the states available.
    * @return ArrayList
    * @roseuid 39AF708E0392
    */
   public ArrayList getAllStates()throws DatabaseException
   {
		ArrayList allStates=adminDAO.getAllStates();
		return allStates;
   }
   
   //The below line of code added by Parmanand Start
   public ArrayList getAllMliTypes()throws DatabaseException
   {
		ArrayList allMliTypes=adminDAO.getAllMliTypes();
		return allMliTypes;
   }
 //The below line of code added by Parmanand End
   
   
   /**
   * 
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
 public ArrayList getMemberList(String bankId)throws DatabaseException
   {
		ArrayList allMembers=adminDAO.getMemberList(bankId);
		return allMembers;
   }
   /**
    * This method returns all the regions available.
    * @return ArrayList
    * @roseuid 39AF708E0392
    */
   public ArrayList getAllRegions()throws DatabaseException
   {
		 ArrayList allRegions=adminDAO.getAllRegions();
		 return allRegions;
   }

   /**
    * This method returns all the alerts available in the database.
    * @return ArrayList
    * @roseuid 39AF73B602A4
    */
   public ArrayList getAllAlerts()throws DatabaseException
   {
	  	ArrayList allAlerts=adminDAO.getAllAlerts();
    	return allAlerts;
   }

   /**
    * This method is used to get all the exception messages.
    * @return ArrayList
    * @roseuid 39AF758600F2
    */
   public ArrayList getAllExceptionMessages()throws DatabaseException
   {
	   	ArrayList allExceptionMessages=adminDAO.getAllExceptionMessages();
    	return allExceptionMessages;
   }

   /**
    * This method is used to get the parameter master. All the parameter details are
    * encapsulated in this parameter object.
    * @return com.cgtsi.admin.ParameterMaster
    * @roseuid 39AF78850380
    */
   public ParameterMaster getParameter()throws DatabaseException
   {
		ParameterMaster parameterMaster=adminDAO.getParameter();
    	return parameterMaster;
   }

   /**
    * This method returns all the districts available for the given state.
    * @param state
    * @return ArrayList
    * @roseuid 39AF8D7801FB
    */
   public ArrayList getAllDistricts(String state)throws DatabaseException
   {
		ArrayList allDistricts=null;
		if (state!=null){
			allDistricts=adminDAO.getAllDistricts(state);
		}
		return allDistricts;
   }
   /**
   * 
   * @param cityName
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
    public ArrayList getCityNames(String cityName)throws DatabaseException
   {
		ArrayList allCities=null;
    
		if (cityName!=null){
    //System.out.println("City Name:"+cityName);
			allCities=adminDAO.getAllCitiesNew(cityName);
		}
		return allCities;
   }

   public ArrayList getAllZones()throws DatabaseException
	  {
		   ArrayList allZones=null;
		   allZones=adminDAO.getAllZones();
		   return allZones;
	  }

   /**
    * This method is invoked after defining the origanisation structure. NO of HO
    * user information is passed along with member id generated for the created
    * member.
    * @param noOfHOUserinfo - NO of HO's user information
    * @param memberId - The created member's id
    * @roseuid 39AFACF7012D
    */
   public void createMemberUsers(User noOfHoUserinfo, String memberId)
   						throws DatabaseException,MailerException,NoUserFoundException
   {
	   if ((noOfHoUserinfo!=null)&&(memberId!=null)){
			ArrayList toAddresses=new ArrayList();
			ArrayList activeBankUsers=adminDAO.getActiveBankUsers(memberId);
	   		if (activeBankUsers==null){
					//throw new NoUserFoundException("No user found ");
	   			throw new NoUserFoundException("Invalid Credential");
			}
			StringBuffer messageBody=new StringBuffer();
			String subject="User id and password";
			String emailId=noOfHoUserinfo.getEmailId();
			toAddresses.add(emailId);

			//Generate userId and Password for the Bankusers.
  			int size=activeBankUsers.size();
			for(int i=0;i<=size;i++){
				User MLIUser=(User)activeBankUsers.get(i);
				String userId  = MLIUser.getUserName();
				String password= MLIUser.getPassword();
				messageBody.append("UserID: ").append(userId).
											   append(" password:").append(password);
			}
        	Message message=new Message(toAddresses,null,null,
        											subject,messageBody.toString());
			Mailer mailer=new Mailer();
			mailer.sendEmail(message);
	   }
       	   return;
   }
   /**
    * This method returns an ArrayList of alerts available for the given user id.
    * @param userId
    * @return java.util.ArrayList
    * @roseuid 39B0826B03DE
    */
   public ArrayList getAlerts(String userId)throws DatabaseException
   {
	   ArrayList alerts=null;
	   if (userId!=null){
			alerts=adminDAO.getAlerts(userId);
	   }
	  return alerts;
   }

   /**
    * This method is used get the message available for the given user id.
    * @param userId - The user id for which messages are to be retrieved.
    * @return com.cgtsi.admin.Message
    * @roseuid 39B082B303D7
    */
   public String getMessage(String userId)throws DatabaseException
   {
	   String message=null;
	   //message=null;
	   if (userId!=null){
			message=adminDAO.getMessage(userId);
			//message.setMessage(returnMessage);
	   }
	   return message;
   }
   /**
		  * This method is used to reactivate a deactivated  User.
		  * @param userID
		  * @roseuid 3972AB8B0135
		  */
	public void reactivateUser(String userID,String reason,String createdBy)throws DatabaseException
	{
		if(userID!=null){
		adminDAO.reactivateUser(userID,reason,createdBy);
		}
    }

		 /**
		  * Any user can be deactivated through this method.
		  * @param userID
		  * @roseuid 3972AB8B0137
		  */
	public void deactivateUser(String userID,String reason,String createdBy)throws DatabaseException
	{
		if(userID!=null){
		 adminDAO.deactivateUser(userID,reason,createdBy);
		 }
	}

		 /**
		  * After modifying the user data, this method is called to update the changes.
		  * @param user
		  * @roseuid 3972AB8B0138
		  */
	public void modifyUser(User user,String createdBy)throws DatabaseException
	{
	   if(user!=null){
	      adminDAO.modifyUser(user,createdBy);
	   }
	}
	public ArrayList getPrivilegesForRole(String roleName) throws DatabaseException
	{
		if(roleName!=null && !roleName.equals(""))
		{
			return adminDAO.getPrivilegesForRole(roleName);
		}

		return null;
	}
	public ArrayList getAllActiveUser() throws DatabaseException
	{
		return adminDAO.getAllActiveUsers();
	}

	public ArrayList getAllAdminMails(String userId) throws DatabaseException
	{
		return adminDAO.getAllAdminMails(userId);
	}

	public Message getMailForId(String mailId) throws DatabaseException,NoDataException
	{
		return adminDAO.getMailForId(mailId);
	}

	public void deleteAdminMails(String[] selectedMailIds) throws DatabaseException
	{
		adminDAO.deleteAdminMails(selectedMailIds);
	}

//This method is used to return the designation description on passing the designation.
//Used while updating Designation master.
//19 NOV 2003
//Ramesh rp14480
	public String getDesigDescr(String designation)throws DatabaseException
	   {
			String desigDescr=null;
			if (designation!=null){
				desigDescr=adminDAO.getDesigDescr(designation);
			}
			return desigDescr;
	   }

/*	This method is used to return the banks that have registered the PLR details.
*	Used while updating PLR master.
*	19 NOV 2003
*	Ramesh rp14480
*/
	  public ArrayList getPLRBanks()throws DatabaseException
		 {

		   return adminDAO.getPLRBanks();

		 }

/*	This method is used to return the plr details of a bank.
*	Used while updating PLR master.
*	20 NOV 2003
*	Ramesh rp14480
*/
	  public ArrayList getPlrDetails(String bank)throws DatabaseException
		 {
		   return  adminDAO.getPlrDetails(bank);

		 }

	/*	This method is used to return the alert titles from the database.
	*	Used while updating alert master.
	*	20 NOV 2003
	*	Ramesh rp14480
	*/
		  public ArrayList getAlertTitles()throws DatabaseException
			 {

			   return adminDAO.getAlertTitles();

			 }
	/*	This method is used to return the message associated with the alert title.
		*	Used while updating alert master.
		*	20 NOV 2003
		*	Ramesh rp14480
		*/
			  public String getAlertMessage(String alertTitle)throws DatabaseException
				 {

				   return adminDAO.getAlertMessage(alertTitle);

				 }
	/*	This method is used to return the exception titles from the database.
		*	Used while updating exception master.
		*	20 NOV 2003
		*	Ramesh rp14480
		*/
			  public ArrayList getExceptionTitles()throws DatabaseException
				 {

				   return adminDAO.getExceptionTitles();

				 }
	/*	This method is used to return the message associated with the alert title.
			*	Used while updating alert master.
			*	21 NOV 2003
			*	Ramesh rp14480
			*/
	  public ExceptionMaster getExceptionMessage(String exceptionTitle)throws DatabaseException
		 {

		   return adminDAO.getExceptionMessage(exceptionTitle);

		 }

	public ArrayList getAllSocialCategories() throws DatabaseException
	{
		return adminDAO.getAllSocialCategories();
	}
  public ArrayList getAllRsfParticipatingBanks() throws DatabaseException
  {
  return adminDAO.getAllRsfParticipatingBanks();
  }
	public ArrayList getAllIndustryNature() throws DatabaseException
	{
		return adminDAO.getAllIndustryNature();
	}

 public ArrayList getIndustrySectors() throws DatabaseException
	{
		return adminDAO.getAllIndustrySectors();
	}

	public ArrayList getIndustrySectors(String industryNature) throws DatabaseException
	{
		return adminDAO.getIndustrySectors(industryNature);
	}

	public ArrayList getAllIndustrySectors() throws DatabaseException
	{
		return adminDAO.getAllIndustrySectors();
	}
	public ArrayList getUsersWithoutRolesAndPrivileges(String memberId) throws DatabaseException
	{
		return adminDAO.getUsersWithoutRolesAndPrivileges(memberId);
	}





	/**
		* To return the user information details pertaining to the user id,memberId
		*   passed.
		* If user details are not found, this method would throw NoUserFoundException.
		* @param userId
		* @return User
		* @throws DatabaseException
		* rp14480 on 6th April 2004
		*/
	   public User getUserInfo(String memberId,String userId) throws DatabaseException
	   {
		User user=null;

				//Gets the user information based on the userID,memberId passed
				if (userId!=null&&memberId!=null){
					user=adminDAO.getUserInfo(memberId,userId);
				 }
				 return user;
	   }

	   public void batchProcess() throws DatabaseException, MessageException
		{

		   AdminDAO adminDAO = new AdminDAO();

		   adminDAO.batchProcess();

		}

	//New method added for Data Archival in front end
	public void archiveData() throws DatabaseException, MessageException, SQLException
	 {

		AdminDAO adminDAO = new AdminDAO();

		adminDAO.archiveData();

	 }

	 public int getUsersCount(String bankId, String zoneId,String branchId, String flag) throws DatabaseException
	 {
		 return adminDAO.getUsersCount(bankId,zoneId,branchId,flag);
	 }

    /*
     * The fillowing method added to get all the approved cgpans.
     * The method being added to validate CGPAN in Enter Audit Details
     */
   	public ArrayList getAllCgpans() throws DatabaseException
	{
		ReportDAO reportdao = new ReportDAO();
		ArrayList cgpans = reportdao.getAllCgpans();
		return cgpans;
	}

	 public void checkValidUserLogin(String userId) throws DatabaseException
	 {
		 adminDAO.checkValidUserLogin(userId);
	 }

	public ArrayList getAllStateCodes()throws DatabaseException
	{
		 ArrayList allStates=adminDAO.getAllStateCodes();
		 return allStates;
	}

	public String getStateName(String stateCode)throws DatabaseException
	{
		 return adminDAO.getStateName(stateCode);
	}

	public void modifyStateMaster(StateMaster stateMaster) throws DatabaseException
	{
		adminDAO.modifyStateMaster(stateMaster);
	}

	public void modifyDistrictmaster(DistrictMaster distMaster, String modDistrict) throws DatabaseException
	{
		adminDAO.modifyDistrictmaster(distMaster, modDistrict);
	}

	public void modifyPLRMaster(PLRMaster plrObj, String userid) throws DatabaseException, InvalidDataException
	{
		    // From the JSP
		    //ArrayList sortedArrayListFromJSP = sortPLRDetails(plrDetails);
			AdminDAO adminDAO=new AdminDAO();
			adminDAO.updatePLRMaster(plrObj,userid);
			/*
			PLRMaster plrObj = null;
			for(int i=0; i<sortedArrayListFromJSP.size(); i++)
			{
				  boolean sameObject = false;
				  plrObj = (PLRMaster)sortedArrayListFromJSP.get(i);
				  if(plrObj == null)
				  {
					  continue;
				  }
				  plrObj.setBankId(bankId);
				  // From the DB
				  ArrayList plrMasters= getPlrDetails(bankId);
				  ArrayList sortedArrayListFromDB = sortPLRDetails(plrMasters);
				  for(int j=0; j<sortedArrayListFromDB.size(); j++)
				  {
					  PLRMaster plrObject = (PLRMaster)sortedArrayListFromDB.get(i);
					  if(plrObject == null)
					  {
						  continue;
					  }
					  if(!plrObject.equals(plrObj))
					  {
                          java.util.Date startDateParam = plrObj.getStartDate();
                          java.util.Date endDateParam = plrObj.getEndDate();
                          java.util.Date startDateDB = plrObject.getStartDate();
                          java.util.Date endDateDB = plrObject.getEndDate();
                          if(((startDateParam != null) && (startDateDB != null) && (startDateParam.equals(startDateDB)))
                          && ((endDateParam != null) && (endDateDB != null) && (endDateParam.equals(endDateDB))))
                          {
							  adminDAO.updatePLRMaster(plrObj,userid);
						  }
						  else
						  {
							  boolean flag = validatePLRDetails(plrObj,plrMasters,userid);
							  if(flag)
							  {
							  	   adminDAO.updatePLRMaster(plrObj,userid);
							  }
						  }
					  }
					  else
					  {
						  continue;
					  }
				  }
			}
			*/
	}


   /*
    * This method validates if the PLR Details entered are valid or not.
    */
	public boolean validatePLRDetails(PLRMaster objPlr, ArrayList plrDetails,String userid) throws DatabaseException,InvalidDataException
	{
		java.util.Date startDateParam = objPlr.getStartDate();
		java.util.Date endDateParam = objPlr.getEndDate();

        Log.log(Log.INFO,"Administrator","validatePLRDetails","**************************************************");
	    Log.log(Log.INFO,"Administrator","validatePLRDetails","startDateParam :" + startDateParam);
	    Log.log(Log.INFO,"Administrator","validatePLRDetails","endDateParam :" + endDateParam);
	    Log.log(Log.INFO,"Administrator","validatePLRDetails","**************************************************");

		java.util.Date startDateDb = null;
		java.util.Date endDateDb = null;

		PLRMaster plrObj = null;

		java.util.Date tempStartDt = null;

		// ArrayList sortedArrayList = new ArrayList();

		PLRMaster objPLRTemp = new PLRMaster();;

		Log.log(Log.INFO,"Administrator","validatePLRDetails","Size of plrDetails :" + plrDetails.size());

        ArrayList sortedArrayList = sortPLRDetails(plrDetails);

		Log.log(Log.INFO,"Administrator","validatePLRDetails","Size of sortedArrayList :" + sortedArrayList.size());
		Log.log(Log.INFO,"Administrator","validatePLRDetails","**************************************************");
		for(int i=0; i<sortedArrayList.size();i++)
		{
			  PLRMaster plrObjTemp = (PLRMaster)sortedArrayList.get(i);
			  if(plrObjTemp == null)
			  {
				  continue;
			  }
			  startDateDb = plrObjTemp.getStartDate();
			  endDateDb = plrObjTemp.getEndDate();

              Log.log(Log.INFO,"Administrator","validatePLRDetails","***************************");
			  Log.log(Log.INFO,"Administrator","validatePLRDetails","startDateDb :" + startDateDb);
			  Log.log(Log.INFO,"Administrator","validatePLRDetails","endDateDb :" + endDateDb);
			  Log.log(Log.INFO,"Administrator","validatePLRDetails","***************************");
		}
		Log.log(Log.INFO,"Administrator","validatePLRDetails","**************************************************");


		// The Sorted PLRDetails ArrayList is in sortedArrayList
		// java.util.Date startDateParam = objPlr.getStartDate();
		// java.util.Date endDateParam = objPlr.getEndDate();

		// java.util.Date startDateDb = null;
		// java.util.Date endDateDb = null;
		// boolean invalidData = false;
        Log.log(Log.INFO,"Administrator","validatePLRDetails","Before control 1");
        // Check No 1
		for(int i=0; i<sortedArrayList.size();i++)
		{
			  plrObj = (PLRMaster)sortedArrayList.get(i);
			  if(plrObj == null)
			  {
				  continue;
			  }
			  startDateDb = plrObj.getStartDate();
			  endDateDb = plrObj.getEndDate();
			  Log.log(Log.INFO,"Administrator","validatePLRDetails","Check No 1 startDateDb :" + startDateDb);
			  Log.log(Log.INFO,"Administrator","validatePLRDetails","Check No 1 endDateDb :" + endDateDb);

			  if((startDateParam != null) && ((endDateParam != null) && (!endDateParam.toString().equals(""))) && (startDateDb != null) && (endDateDb != null))
			  {
				  Log.log(Log.INFO,"Administrator","validatePLRDetails","Check No 1 Inside the 1st If Loop");
				  if(((startDateParam.compareTo(startDateDb)) >= 0) && ((endDateParam.compareTo(endDateDb)) <= 0))
				  {
					  Log.log(Log.INFO,"Administrator","validatePLRDetails","Check No 1 Inside the 2nd If Loop");
					  Log.log(Log.INFO,"Administrator","validatePLRDetails","Control 1");
					  throw new InvalidDataException("PLR Details already exist for the period :" + startDateParam +" and " + endDateParam);
				  }
			  }
		}

        Log.log(Log.INFO,"Administrator","validatePLRDetails","Before control 2");
		// Check No 2
		for(int i=0; i<sortedArrayList.size();i++)
		{
			int j = i+1;
			plrObj = (PLRMaster)sortedArrayList.get(i);
			startDateDb = plrObj.getStartDate();
			endDateDb = plrObj.getEndDate();
			Log.log(Log.INFO,"Administrator","validatePLRDetails","Check No 2 startDateDb :" + startDateDb);
			Log.log(Log.INFO,"Administrator","validatePLRDetails","Check No 2 endDateDb :" + endDateDb);

            if(j < sortedArrayList.size())
            {
				Log.log(Log.INFO,"Administrator","validatePLRDetails","Check No 2 Inside the 1st If Loop");
				PLRMaster plrAnotherObj = (PLRMaster)sortedArrayList.get(j);
				java.util.Date nextStartDateDb = plrAnotherObj.getStartDate();
				java.util.Date nextEndDateDb = plrAnotherObj.getEndDate();

				if((startDateParam != null) && (endDateParam != null) && (!endDateParam.toString().equals("")) && (startDateDb != null) && (endDateDb != null))
				{
					Log.log(Log.INFO,"Administrator","validatePLRDetails","Check No 2 Inside the 2nd If Loop");
					 if(((startDateParam.compareTo(endDateDb)) <= 0) && ((startDateParam.compareTo(nextStartDateDb)) >= 0))
					 {
						 Log.log(Log.INFO,"Administrator","validatePLRDetails","Check No 2 Inside the 3rd If Loop");
						 Log.log(Log.INFO,"Administrator","validatePLRDetails","Control 2");
						 throw new InvalidDataException("PLR Details already exist for the period :" + startDateParam +" and " + endDateParam);
					 }
				}
			}
		}

        Log.log(Log.INFO,"Administrator","validatePLRDetails","Before control 3");
		// Check No 3
		for(int i=0; i<sortedArrayList.size();i++)
		{
			  plrObj = (PLRMaster)sortedArrayList.get(i);
			  if(plrObj == null)
			  {
				  continue;
			  }
			  startDateDb = plrObj.getStartDate();
			  endDateDb = plrObj.getEndDate();
			  Log.log(Log.INFO,"Administrator","validatePLRDetails","Check No 3 startDateDb :" + startDateDb);
			  Log.log(Log.INFO,"Administrator","validatePLRDetails","Check No 3 endDateDb :" + endDateDb);

			  if((startDateParam != null) && (endDateParam != null) && (!endDateParam.toString().equals("")) && (startDateDb != null) && (endDateDb == null))
			  {
				  Log.log(Log.INFO,"Administrator","validatePLRDetails","Check No 3 Inside the 1st If Loop");
				  if((startDateParam.compareTo(startDateDb)) > 0)
				  {
					    Log.log(Log.INFO,"Administrator","validatePLRDetails","Check No 3 Inside the 2nd If Loop");
						Calendar calendar =Calendar.getInstance();
						calendar.setTime(startDateParam);
						int day = calendar.get(Calendar.DATE);
						int month = calendar.get(Calendar.MONTH);
						if(day > 1)
						{
							day= day - 1;
						}
						if(day == 1)
						{
							month = month -1;
                            day= day - 1;
						}
						calendar.set(Calendar.DATE,day);
						endDateDb = calendar.getTime();
						// plrObj = (PLRMaster)sortedArrayList.remove(i);
						plrObj.setEndDate(endDateDb);
						// sortedArrayList.add(i,plrObj);
						Log.log(Log.INFO,"Administrator","validatePLRDetails","startDateDb :" + plrObj.getStartDate());
						Log.log(Log.INFO,"Administrator","validatePLRDetails","endDateDb :" + plrObj.getEndDate());
						adminDAO.updatePLRMaster(plrObj,userid);
						return false;
				  }
			  }
		}

        Log.log(Log.INFO,"Administrator","validatePLRDetails","Before control 4");
        // Check No 4
		for(int i=0; i<sortedArrayList.size();i++)
		{
			  plrObj = (PLRMaster)sortedArrayList.get(i);
			  if(plrObj == null)
			  {
				  continue;
			  }
			  startDateDb = plrObj.getStartDate();
			  endDateDb = plrObj.getEndDate();
			  Log.log(Log.INFO,"Administrator","validatePLRDetails","Check No 4 startDateDb :" + startDateDb);
			  Log.log(Log.INFO,"Administrator","validatePLRDetails","Check No 4 endDateDb :" + endDateDb);

			  if((startDateParam != null) && ((endDateParam != null) && (!endDateParam.toString().equals(""))) && (startDateDb != null) && (endDateDb != null))
			  {
				  Log.log(Log.INFO,"Administrator","validatePLRDetails","Check No 4 Inside the 1st If Loop");
				  Log.log(Log.INFO,"Administrator","validatePLRDetails","startDateParam and startDateDb " + startDateParam+","+startDateDb+","+startDateParam.getClass()+","+startDateDb.getClass());
				  Log.log(Log.INFO,"Administrator","validatePLRDetails","(startDateParam.compareTo(startDateDb)) " + (startDateParam.compareTo(startDateDb)));
				  Log.log(Log.INFO,"Administrator","validatePLRDetails","(startDateParam.compareTo(endDateDb)) :" + (startDateParam.compareTo(endDateDb)));
				  Log.log(Log.INFO,"Administrator","validatePLRDetails","(endDateParam.compareTo(endDateDb)) :" + (endDateParam.compareTo(endDateDb)));
				  if(((startDateParam.compareTo(startDateDb)) >= 0) && ((startDateParam.compareTo(endDateDb)) <= 0) && ((endDateParam.compareTo(endDateDb)) > 0))
				  {
					  Log.log(Log.INFO,"Administrator","validatePLRDetails","Check No 4 Inside the 2nd If Loop");
					  Log.log(Log.INFO,"Administrator","validatePLRDetails","Control 3");
					  throw new InvalidDataException("PLR Details already exist for the period :" + startDateParam +" and " + endDateParam);
				  }
			  }
		}

        Log.log(Log.INFO,"Administrator","validatePLRDetails","Before control 5");
        // Check No 5
		for(int i=0; i<sortedArrayList.size();i++)
		{
			  plrObj = (PLRMaster)sortedArrayList.get(i);
			  if(plrObj == null)
			  {
				  continue;
			  }
			  startDateDb = plrObj.getStartDate();
			  endDateDb = plrObj.getEndDate();
			  Log.log(Log.INFO,"Administrator","validatePLRDetails","Check No 5 startDateDb :" + startDateDb);
			  Log.log(Log.INFO,"Administrator","validatePLRDetails","Check No 5 endDateDb :" + endDateDb);

			  if((startDateParam != null) && ((endDateParam == null) || (endDateParam.toString().equals(""))) && (startDateDb != null) && (endDateDb != null))
			  {
				  Log.log(Log.INFO,"Administrator","validatePLRDetails","Check No 5 Inside the 1st If Loop");
				  if(((startDateParam.compareTo(startDateDb)) >= 0) && ((startDateParam.compareTo(endDateDb)) <= 0))
				  {
					  Log.log(Log.INFO,"Administrator","validatePLRDetails","Check No 5 Inside the 2nd If Loop");
					  Log.log(Log.INFO,"Administrator","validatePLRDetails","Control 5");
					  throw new InvalidDataException("PLR Details already exist for the period.");
				  }
			  }
		}

        Log.log(Log.INFO,"Administrator","validatePLRDetails","Before control 6");
		// Check No 6
		Log.log(Log.INFO,"Administrator","validatePLRDetails","Control 6");
		Log.log(Log.INFO,"Administrator","validatePLRDetails","sortedArrayList.size() :" + sortedArrayList.size());
		for(int i=0; i<sortedArrayList.size();i++)
		{
			  plrObj = (PLRMaster)sortedArrayList.get(i);
			  if(plrObj == null)
			  {
				  continue;
			  }
			  startDateDb = plrObj.getStartDate();
			  endDateDb = plrObj.getEndDate();
			  Log.log(Log.INFO,"Administrator","validatePLRDetails","Check No 6 startDateDb :" + startDateDb);
			  Log.log(Log.INFO,"Administrator","validatePLRDetails","Check No 6 endDateDb :" + endDateDb);

			  if((startDateParam != null) && ((endDateParam != null) && (endDateParam.toString().equals(""))) && (startDateDb != null) && (endDateDb != null))
			  {
				  Log.log(Log.INFO,"Administrator","validatePLRDetails","Check No 6 Inside the 1st If Loop");
				  Log.log(Log.INFO,"Administrator","validatePLRDetails","Control 11");
				  if((startDateParam.compareTo(startDateDb)) < 0)
				  {
					  Log.log(Log.INFO,"Administrator","validatePLRDetails","Check No 6 Inside the 2nd If Loop");
					  Log.log(Log.INFO,"Administrator","validatePLRDetails","Control 12");
						Calendar calendar =Calendar.getInstance();
						calendar.setTime(startDateDb);
						int day = calendar.get(Calendar.DATE);
						int month = calendar.get(Calendar.MONTH);
						int year = calendar.get(Calendar.YEAR);
						if(day > 1)
						{
							Log.log(Log.INFO,"Administrator","validatePLRDetails","Control 13");
							day= day - 1;
						}
						if(day == 1)
						{
							Log.log(Log.INFO,"Administrator","validatePLRDetails","Control 14");
							day= day - 1;
							// month = month -1;

						}
						/*
						if((day == 1) && (month == 1))
						{
							Log.log(Log.INFO,"Administrator","validatePLRDetails","Control 15");
							month = Calendar.DECEMBER;
                            day= day - 1;
                            year = year - 1;
						}
						*/
						calendar.set(Calendar.DATE,day);
						calendar.set(Calendar.MONTH,month);
						calendar.set(Calendar.YEAR,year);
						endDateParam = calendar.getTime();
						// plrObj = (PLRMaster)sortedArrayList.remove(i);
						objPlr.setStartDate(startDateParam);
						objPlr.setEndDate(endDateParam);
						// sortedArrayList.add(i,plrObj);
						Log.log(Log.INFO,"Administrator","validatePLRDetails","startDateDb :" + objPlr.getStartDate());
						Log.log(Log.INFO,"Administrator","validatePLRDetails","endDateDb :" + objPlr.getEndDate());
						adminDAO.insertPLRMaster(objPlr,userid);
		                return false;
				  }
			  }
		}

        Log.log(Log.INFO,"Administrator","validatePLRDetails","Before control 7");
        // Check No 7
		for(int i=0; i<sortedArrayList.size();i++)
		{
			  plrObj = (PLRMaster)sortedArrayList.get(i);
			  if(plrObj == null)
			  {
				  continue;
			  }
			  startDateDb = plrObj.getStartDate();
			  endDateDb = plrObj.getEndDate();
			  Log.log(Log.INFO,"Administrator","validatePLRDetails","Check No 7 startDateDb :" + startDateDb);
			  Log.log(Log.INFO,"Administrator","validatePLRDetails","Check No 7 endDateDb :" + endDateDb);

			  if((startDateParam != null) && ((endDateParam != null) && (!endDateParam.toString().equals(""))) && (startDateDb != null) && (endDateDb != null))
			  {
				  Log.log(Log.INFO,"Administrator","validatePLRDetails","Check No 7 Inside the 1st If Loop");
				  Log.log(Log.INFO,"Administrator","validatePLRDetails","Check No 7 Inside the 1st If Loop");
				  Log.log(Log.INFO,"Administrator","validatePLRDetails","startDateParam and startDateDb " + startDateParam+","+startDateDb+","+startDateParam.getClass()+","+startDateDb.getClass());
				  Log.log(Log.INFO,"Administrator","validatePLRDetails","endDateParam and endDateDb " + endDateParam+","+endDateDb+","+endDateParam.getClass()+","+endDateDb.getClass());
				  Log.log(Log.INFO,"Administrator","validatePLRDetails","(startDateParam.compareTo(startDateDb)) " + (startDateParam.compareTo(startDateDb)));
				  Log.log(Log.INFO,"Administrator","validatePLRDetails","(endDateParam.compareTo(startDateDb)) :" + (endDateParam.compareTo(startDateDb)));
				  Log.log(Log.INFO,"Administrator","validatePLRDetails","(endDateParam.compareTo(endDateDb)) :" + (endDateParam.compareTo(endDateDb)));
				  if(((startDateParam.compareTo(startDateDb)) <= 0) && ((endDateParam.compareTo(startDateDb)) >= 0) )
				  {
					  Log.log(Log.INFO,"Administrator","validatePLRDetails","Check No 7 Inside the 2nd If Loop");
					  Log.log(Log.INFO,"Administrator","validatePLRDetails","Control 7");
					  throw new InvalidDataException("PLR Details already exist for the period.");
				  }
			  }
		}

		return true;
	}

	private ArrayList sortPLRDetails(ArrayList plrDetails)
	{
		// java.util.Date startDateParam = objPlr.getStartDate();
		// java.util.Date endDateParam = objPlr.getEndDate();

	    // Log.log(Log.INFO,"Administrator","validatePLRDetails","startDateParam :" + startDateParam);
	    // Log.log(Log.INFO,"Administrator","validatePLRDetails","endDateParam :" + endDateParam);

		java.util.Date startDateDb = null;
		java.util.Date endDateDb = null;

		PLRMaster plrObj = null;

		java.util.Date tempStartDt = null;

		ArrayList sortedArrayList = new ArrayList();

		PLRMaster objPLRTemp = new PLRMaster();;

		// ArrayList sortedArrayList = new ArrayList();

		// Sorting the ArrayList of PLRDetails from the database
		if(plrDetails != null)
		{
			while(true)
			{
				for(int i=0; i<plrDetails.size(); i++)
				{
					  plrObj = (PLRMaster)plrDetails.get(i);
					  if(plrObj == null)
					  {
						  continue;
					  }
					  startDateDb = plrObj.getStartDate();
					  if(i==0)
					  {
						  tempStartDt = startDateDb;
						  objPLRTemp = plrObj;
					  }
					  if(i>0)
					  {
						  if((startDateDb != null) && (tempStartDt != null))
						  {
							  if((startDateDb.compareTo(tempStartDt)) <0)
							  {
								   tempStartDt = startDateDb;
								   objPLRTemp = plrObj;
							  }
							  else
							  {
								  continue;
							  }
						  }
					  }
				}
				if(!sortedArrayList.contains(objPLRTemp))
				{
					sortedArrayList.add(objPLRTemp);
				}
				for(int i=0; i<plrDetails.size();i++)
				{
					  plrObj = (PLRMaster)plrDetails.get(i);
					  if(plrObj == null)
					  {
						  continue;
					  }
					  java.util.Date tempDate = plrObj.getStartDate();
					  if((tempDate != null) && (tempDate.equals(tempStartDt)))
					  {
						  plrDetails.remove(i);
					  }
				}
				if(plrDetails.size() ==0)
				{
					break;
				}
			}
		}

		return sortedArrayList;
	}
        
     public ArrayList getAllRsf2ParticipatingBanks()
             throws DatabaseException
         {
             return adminDAO.getAllRsf2ParticipatingBanks();
         }
     
     public String insertQryDetails(MLIInfo mliInfo)
     throws DatabaseException
 {
     String compId = adminDAO.insertQryDetails(mliInfo);
     return compId;
 }
     
     public void sendMailtoMli(String department, ArrayList emaiIds, String complaintid)
     throws DatabaseException
 {
     adminDAO.sendMailtoMli(department, emaiIds, complaintid);
 }
     /* Created by kailash for GST*/

	public String getState(String mliBranchCode) {
		String stateName=null;
		if(mliBranchCode!=null){
			stateName=adminDAO.getState(mliBranchCode);
		}
		return stateName;  

	}

 }

