//Source file: C:\\CGTSI\\Vss\\com\\cgtsi\\admin\\PasswordManager.java

package com.cgtsi.admin;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import com.cgtsi.common.Mailer;
import com.cgtsi.common.DatabaseException;
import com.cgtsi.common.NoUserFoundException;
import com.cgtsi.common.InvalidPasswordException;
import com.cgtsi.common.MailerException;
import com.cgtsi.common.Log;
import com.cgtsi.common.Constants;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.SecretKey;

import sun.misc.BASE64Encoder;
import sun.misc.BASE64Decoder;





/**
 * Password manager class holds the responsibility of managing the password
 * related issues. whether it be password change,password reset and others.
 */
public class PasswordManager
{
   	Administrator admin;
    AdminDAO adminDAO;

    //Let this string be the key. make it transient.
    private final transient String key="AC8DB6529C2B12AFCB24A83B09AE8109CGTSI";

   /**
    * @roseuid 39B875C50115
    */
   public PasswordManager()
   {
	   	admin=new Administrator();
	    adminDAO=new AdminDAO();
   }

   /**
    * Whenver a user wants to change his password. This method is used to change the
    * password in the database.
    *
    * @param oldPassword
    * @param newPassword
    * @param userId
    * @roseuid 3972AB8B00CC
    */
    public void changePassword(String oldPassword, String newPassword, User user,String emailId)
   				throws InvalidPasswordException,DatabaseException,NoUserFoundException,Exception
   {
		   String password=null;
		   boolean chkPasswordVal=false;
		   boolean updatePwdVal=true;

		   if(user!=null){
			   password=user.getPassword();	//gets the original password of the user

//			checks whether entered password is same as the original password
			   chkPasswordVal=checkPassword(password,oldPassword);
			   if (!chkPasswordVal)
			   {
				   throw new InvalidPasswordException("Invalid Password");
			   }

//			updates the new password into the database
 updatePwdVal=adminDAO.updatePassword(encryptPassword(newPassword),user.getUserId(),emailId);
		   }
		   else
		   {
		   		throw new NoUserFoundException("No User Found");
		   }
   }

   /**
    * Whenever a user requested the admin to change reset his password.This method
    * would be called and new password would be generated.The same password would be
    * mailed to the concerned user if emai id is available.His immediate higher
    * authority would be intimated about the password change
    * @param userId
    * @return String
    * @roseuid 3972AB8B00CE
    */
 public String resetPassword(String userId,String loginUserId)throws DatabaseException,MailerException,NoUserFoundException
   {
       Log.log(4, "PasswordManager", "resetPassword()", "userId :" + userId);
              Log.log(4, "PasswordManager", "resetPassword()", "loginUserId :" + loginUserId);
              String userEmailId = null;
              if(userId != null)
              {
                  AdminHelper adminHelper = new AdminHelper();
                  PasswordManager passwordManager = new PasswordManager();
                  String defaultPassword = adminHelper.generatePassword();
                  System.out.println("defaultPassword:" + defaultPassword);
                  adminDAO.resetPassword(userId, defaultPassword, loginUserId);
                  String password = passwordManager.decryptPassword(defaultPassword);
                  System.out.println("Password:" + password);
                  Administrator admin = new Administrator();
                  User userdetails = admin.getUserInfo(userId);
                  ArrayList roles = null;
                 // ArrayList userIds = null;
                  String memberId = userdetails.getBankId() + userdetails.getZoneId() + userdetails.getBranchId();
                  String noMemberId = userdetails.getBankId() + "0000" + "0000";
                  Log.log(4, "PasswordManager", "resetPassword", "memberId :" + memberId);
                 // userIds = admin.getUsers(noMemberId);
                  //int size = userIds.size();
                 // Log.log(4, "PasswordManager", "resetPassword", "userIds.size() :" + userIds.size());
                  ArrayList superUserToAddresses = new ArrayList();
                  ArrayList idsToBeSent = new ArrayList();
                  //for(int i = 0; i < size; i++)
                  //{
                    //  String usrId = (String)userIds.get(i);
                      //roles = admin.getRoles(usrId);
                      //int roleSize = roles.size();
                      //Log.log(4, "PasswordManager", "resetPassword", "roleSize :" + roles.size());
                      //for(int j = 0; j < roleSize; j++)
                      //{
                        //  if(!roles.get(j).equals("NO"))
                          //    continue;
                          User noUserDetails = admin.getUserInfo(userId);
                          idsToBeSent.add(userId);
                         // if(noUserDetails == null)
                            //  continue;
                          String noEmailId = noUserDetails.getEmailId();
                          System.out.println("noEmailId:" + noEmailId);
                          Log.log(4, "PasswordManager", "resetPassword", "noEmailId :" + noEmailId);
                          if(noEmailId != null)
                              superUserToAddresses.add(noEmailId);
                    //  }

                 // }

                  String subject = "Password Reset for UserId :" + userId;
                  Mailer mailer = new Mailer();
                  Log.log(4, "PasswordManager", "resetPassword", "Start of Prinitng Email Ids");
                  for(int i = 0; i < superUserToAddresses.size(); i++)
                  {
                      String id = (String)idsToBeSent.get(i);
                      Log.log(4, "PasswordManager", "resetPassword", "Email Id :" + id);
                      System.out.println("Reset Password Email Id:" + id);
                  }

                  Log.log(4, "PasswordManager", "resetPassword", "End of Prinitng Email Ids");
                  Message message = new Message(superUserToAddresses, null, null, subject, "User :" + userId + " Password :" + password+"\n\n");
                  message.setFrom(loginUserId);
                  idsToBeSent.add(userId);
                  Log.log(4, "PasswordManager", "resetPassword", "Start of Prinitng User Ids");
                  for(int i = 0; i < idsToBeSent.size(); i++)
                  {
                      String id = (String)idsToBeSent.get(i);
                      Log.log(4, "PasswordManager", "resetPassword", "User Id :" + id);
                  }

                  Log.log(4, "PasswordManager", "resetPassword", "End of Prinitng User Ids");
                  Message emailMsg = new Message(idsToBeSent, null, null, subject, "User :" + userId + " Password :" + password+"\n\n");
                  emailMsg.setFrom(loginUserId);
                  admin.sendMail(emailMsg);
                  //userEmailId = admin.sendPasswordMail(userId, password, loginUserId);
                  try
                  {
                      mailer.sendEmail(message);
                  }
                  catch(MailerException mailerException)
                  {
                      Log.log(4, "PasswordManager", "resetPassword", "Email could not be sent.");
                  }
              }
              return userEmailId;
   }


   /**
    * Whenever a user wants to change his hint question
    * @param hint
    * @param answer
    * @param userId
    * @return boolean
    * @roseuid 3972AB8B00D1
    */
   public void changeHintQuestionAndAnswer(String hint, String answer, String userId)throws DatabaseException
   {

			adminDAO.updateHint(hint,answer,userId);

   }

   /**
    * @param hint
    * @param answer
    * @param userId
    * @return String
    * @roseuid 3977FB7D0317
    */
   public String answerHintQuestion(String hint, String answer, String userId)
   						 throws InvalidDataException,DatabaseException,MailerException,NoUserFoundException
   {
		if((hint!=null)&&(answer!=null)&&(userId!=null)){
			User userInfo=adminDAO.getUserInfo(userId);
			//Get the hint answer of the user.
			String hintAnswer=userInfo.getHintAnswer();
			if (!(hintAnswer.equalsIgnoreCase(answer))){
				throw new InvalidDataException();
			}
		/*If user submitted hint Answer and the answer stored
						                     in the database are same, get password.*/
		String password=userInfo.getPassword();
		String userEmailId=userInfo.getEmailId();
		//If no user exists password is displayed on screen.
		String passwordToDisplay=null;
		if (userEmailId==null){
			passwordToDisplay=decryptPassword(password);
			return passwordToDisplay;
		}
		//The password is mailed to the user.
		ArrayList toAddresses=new ArrayList();
		toAddresses.add(userEmailId);
		String subject="your password";
		Message message=new Message(toAddresses,null,null,subject,password);
		Mailer mailer=new Mailer();
		mailer.sendEmail(message);

	}
	return null;
   }

   /**
    * This method is used to check the supplied passwords.
    * Original password is obtained from database. The supplied password is decrypted
    * and compared with the original password.
    * @param orgPassword
    * @param suppliedPassword
    * @return boolean
    * @roseuid 398134660358
    */
   public boolean checkPassword(String orgPassword, String suppliedPassword)
   {
		boolean chkPassword=false;

	   if ((orgPassword!=null)&&(suppliedPassword!=null)){
		   String encPassword=null;
		   
		   //System.out.println("encPassword:=="+encPassword);

//		encyptes the entered password
		   encPassword=encryptPassword(suppliedPassword);
		   System.out.println("encPassword:===255=="+encPassword);
		   System.out.println("orgPassword:=="+orgPassword);

//		checks whether the original password is same as the encypted password
		   if (orgPassword.equals(encPassword)) {
			   
			   System.out.println("orgPassword:===260=="+orgPassword);
			   chkPassword=true;
		   }
	   }
		return chkPassword;

   }

   /**
	   * This method adds the date when last the password changed with the the expiry days
	   * @param expiry days,
	   * @return date
	   * @roseuid 3981428E0309
	   */

   public Date pwdExpiryDate(int pwdExpiryDays,Date lastpwdChangeDate)
	   {

		   Calendar calendar=Calendar.getInstance();
		   Date addedDate=new Date();
		   calendar.setTime(lastpwdChangeDate);	//set the date when the password was last changed
                   calendar.add(Calendar.DATE,pwdExpiryDays);//add the expiry days to the date when the password was last changed
		   addedDate=calendar.getTime();
                
		   return addedDate;
	   }

  /**
	* This method compares the added Date with the current Date
	* @param added Date
	* @return boolean
	* @roseuid 3981428E0309
	*/

	public boolean isPasswordExpired(Date addedDate)
		{
			int compareVal=0;
			boolean returnValue=false;
			Date currentDate=new Date();
		   compareVal=currentDate.compareTo(addedDate);//compare the added value to the current date
		   if (compareVal>0){
			   returnValue=true;
		   } else {
			   returnValue=false;
		   }
		   return returnValue;
		}

	/**
	 * This method is used enrypt the password.
	 * @param source
	 * @return String
	 * @roseuid 3977F554008E
	 */
	public String encryptPassword(String source)
	{
		String returnVal=null;
		SecretKey secretKey=getKey();
		try
		{
			Cipher cipherObject = Cipher.getInstance("DES");
			cipherObject.init(Cipher.ENCRYPT_MODE,secretKey);
		 	byte[] encrypted = cipherObject.doFinal(source.getBytes());
		 	returnVal = (new BASE64Encoder()).encode(encrypted);
		}
		catch (Exception e)
		{
			throw new IllegalStateException("Unable to do encryption");
		}
		//System.out.println("encrypted string "+returnVal);

	  	return returnVal;
	}

	/**
	 * This method is used to decrypt the passsword.
	 * @param source
	 * @return String
	 * @roseuid 3977F56F0227
	 */
	public String decryptPassword(String source)
	{

		byte[] decrypted=null;
		try
		{
			SecretKey secretKey=getKey();
			byte[] decoded = (new BASE64Decoder()).decodeBuffer(source);

			//System.out.println("decoded " +String.valueOf(decoded));

			Cipher cipherObject = Cipher.getInstance("DES");
			cipherObject.init(Cipher.DECRYPT_MODE,secretKey);
			decrypted = cipherObject.doFinal(decoded);

		}
		catch(Exception e)
		{
			throw new IllegalStateException("Unable to decrypt...");
		}


		//System.out.println("decrypted string "+decrypted);

		return new String(decrypted);

	}

	private SecretKey getKey()
	{

		KeyGenerator keygen=null;
		try
		{
			keygen = KeyGenerator.getInstance("DES");
		}
		catch (NoSuchAlgorithmException e)
		{
			//throw new Exception("Unable to get the key.");

			throw new IllegalStateException("Unable to do encryption/decryption");
		}

		SecureRandom securerandomobject = new SecureRandom(key.getBytes());
		keygen.init(securerandomobject);
		SecretKey secretKey = keygen.generateKey();

		return secretKey;
	}

}
