//Source file: C:\\CGTSI\\Vss\\com\\cgtsi\\common\\Login.java

package com.cgtsi.common;
import com.cgtsi.admin.User;
import com.cgtsi.admin.PasswordManager;
import com.cgtsi.admin.Administrator;
import com.cgtsi.admin.ParameterMaster;
import com.cgtsi.registration.MLIInfo;
import com.cgtsi.registration.Registration;
import com.cgtsi.admin.InvalidDataException;

import java.util.Date;


/**
 * This class responsible for authenticating the users.
 * With the help of Admin module  roles and privileges are checked and respective
 * screen is displayed to the user who logs in for the first time.
 */
public class Login
{

   /**
    * @roseuid 39B875CD02BB
    */
   private Login()
   {
   }

   /**
    * This is a static method.Called by the system whenever a user tries to log into
    * the application.
    * This method throws NoUserFoundException if the supplied user id is unavailable
    * in the database.
    * Also,it throws InvalidPasswordException if the password supplied by user is
    * incorrect.
    * If the user is deactivated, this method throws InactiveUserException.
    * If the number of consecutive attempts to enter the system without success, this
    * method throws UserLockedException.
    * @param userId
    * @param password
    * @return int 0 means successfull, 1- Unsuccessfull, 2- Change password
    * @throws NoUserFoundException
    * @throws InvalidPasswordException
    * @throws InactiveUserException
    * @throws UserLockedException
    * @roseuid 39715D290169
    */
   public static User login(String memberId,String userId, String password) throws  NoUserFoundException, InvalidPasswordException, InactiveUserException, UserLockedException,DatabaseException
   {
		Log.log(Log.INFO,"Login","login","Entered");

		User userObj=null;

	   	if ((userId!=null && userId!="" )&&(password!=null && password!=""))
	   	{

			Log.log(Log.DEBUG,"Login","login","User id is "+userId);

			  String userPassword=null;
		  	boolean userPwdValid=false;

		  	boolean userActive=false;
		  	boolean firstLogin=false;
		  	boolean passwordReset=false;
		  	boolean pwdChangeExpiry=false;
		  	int unsuccessfulAttempts=0;
		  	int daysSinceLastPwdChange=0;
		  	int pwdExpiryDays=15;

		  	Date lastPassChangeDate=null;
		  	Date addedDate=new Date();

		   	PasswordManager passwordMgr=new PasswordManager();
		   	CommonDAO commonDAO=new CommonDAO();

		   	ParameterMaster parameterMaster=null;

			Administrator administrator=new Administrator();

			parameterMaster=administrator.getParameter();

			Log.log(Log.DEBUG,"Login","login","Before getting user info");

		   	userObj=administrator.getUserInfo(memberId,userId);			//checks if the user exists

			Log.log(Log.DEBUG,"Login","login","After getting user info");

		   	if (userObj==null)
		   	{
			  // throw new NoUserFoundException("No User Found");
		   		throw new NoUserFoundException("Invalid Credential");
		   	}

		   	// Validating the User Login
		   	administrator.checkValidUserLogin(userObj.getUserId());

			String userMemberId=userObj.getBankId()+userObj.getZoneId()+userObj.getBranchId();

			if (!userMemberId.equals(memberId))
			{
			   throw new NoUserFoundException("User does not belong to the member");
			}


		   Log.log(Log.DEBUG,"Login","login","Verify password "+userId);

		   unsuccessfulAttempts=userObj.getUnsuccessfulAttempts();		//gets the number of unsuccessful attempts
		   if (unsuccessfulAttempts>=3)
		   {
				Log.log(Log.WARNING,"Login","login","More than 3 failed attempts ");
				throw new UserLockedException("User Locked. Please try again after 15 mins.");
		   }
		   if (!userObj.isUserActive())
		   {
				Log.log(Log.WARNING,"Login","login","User is inactive ");

				throw new InactiveUserException("Inactive User");
		   }

		   userPassword=userObj.getPassword();	
                   
                   System.out.println("systempassword......**..."+userPassword);
	   	    System.out.println("password......**..."+password);
                   //checks if password is valid
     //  System.out.println("System Password:"+userPassword);
     //    System.out.println("User Password:"+password);
		   userPwdValid=passwordMgr.checkPassword(userPassword,password);

		   if (userPwdValid==false)
		   {
				Log.log(Log.WARNING,"Login","login","Invalid password ");
				commonDAO.updateUnsuccessfullAttempts(userObj.getUserId());

				//throw new InvalidPasswordException("Invalid Password");
				throw new NoUserFoundException("Invalid Credential");
		   }

		   //userActive=userObj.isUserActive();							//checks if user is active

		   Registration registration=new Registration();

		   MLIInfo mliInfo=registration.getMemberDetails(userObj.getBankId(),userObj.getZoneId(),userObj.getBranchId());

		   if(mliInfo.getShortName()!=null)
		   {
		   		userObj.setShortName(mliInfo.getShortName());
		   }
		   else if(mliInfo.getBankName()!=null)
		   {
				userObj.setBankName(mliInfo.getBankName());
		   }

		   if(mliInfo.getZoneName()!=null)
		   {
				userObj.setZoneName(mliInfo.getZoneName());
		   }

		   if(mliInfo.getBranchName()!=null)
		   {
				userObj.setBranchName(mliInfo.getBranchName());
		   }



		   firstLogin=userObj.isFirstLogin();
		   passwordReset=userObj.isPasswordReset();

		   Log.log(Log.DEBUG,"Login","login","firstLogin,passwordReset "+firstLogin+" "+passwordReset);

		   if(!firstLogin)
		   {
		   		//For first log in password set date would be null.
				lastPassChangeDate=userObj.getPassChangeDate();
				pwdExpiryDays=parameterMaster.getPasswordExpiryDays();	//get the expiry days for the master table

				Log.log(Log.DEBUG,"Login","login","last pwd change date, pwd expiry days"+lastPassChangeDate+" "+pwdExpiryDays);

				if(lastPassChangeDate==null)
				{
					pwdChangeExpiry=true;
				}
				else if (pwdExpiryDays != -1)
				{
					addedDate=passwordMgr.pwdExpiryDate(pwdExpiryDays,lastPassChangeDate);

					Log.log(Log.DEBUG,"Login","login","addedDate "+addedDate);

					pwdChangeExpiry=passwordMgr.isPasswordExpired(addedDate);

				}

				Log.log(Log.DEBUG,"Login","login","pwdChangeExpiry "+pwdChangeExpiry);
		   }

		   if (firstLogin||passwordReset||pwdChangeExpiry)
		   {
				Log.log(Log.WARNING,"Login","login","Password is expired");
				userObj.setPasswordExpired(true);
		   }

		   commonDAO.updateLastLoginDate(userObj.getUserId());

	   	}
		Log.log(Log.INFO,"Login","login","Exited");
  
		return userObj;
   }
}
