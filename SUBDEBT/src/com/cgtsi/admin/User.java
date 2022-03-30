//Source file: C:\\CGTSI\\Vss\\com\\cgtsi\\admin\\User.java

package com.cgtsi.admin;
import java.util.Date;
import java.util.ArrayList;



/**
 * This class is responsible for hoding the user information.
 */
public class User
{

   /**
    * This variable holds the user id. 
    */
   private String mobileNo;
   private String userId = null;
   private String check_Flag;

   /**
    * This variable holds the bank id field
    */
   private String bankId = null;

   public String getMobileNo() {
	return mobileNo;
}
public void setMobileNo(String mobileNo) {
	this.mobileNo = mobileNo;
}

/**
    * This variable holds the zonal id
    */
   private String zoneId = null;

   /**
    * This variable holds the branch id
    */
   private String branchId = null;

   /**
    * The role a user plays
    */
   private String roleId = null;

   /**
    * The name assigned to the user to log in
    */
   private String firstName=null;
   private String middleName=null;
   private String lastName=null;
   private String userName = null;
   private String password = null;
   private String designation = null;
   private String emailId = null;

   private String superUser = null;
   private Hint hint = null;
   private int status = 0;
   private Date passwordChangedDate = null;
   private boolean firstLogin = true;
   private int unsuccessfulAttempts = 0;
   private Date lastLogin = null;
   private ArrayList privileges = null;
   private Role roles = null;

   /**
    * To identify the password has been reset or not
    */
   private boolean passwordReset;

   private boolean passwordExpired=false;
   
   private String bankName="";
   private String zoneName="";
   private String branchName="";
   
   private String shortName="";
   //private String flag="N";   // Diksha 
   /**
    * @roseuid 39B875C60242
    */
   public User()
   {

   }
/**
   * 
   * @return 
   */
//Diksha 
/*public String getFlag(){
return flag;
}
/**
   * 
   * @param aFlag
   */
//Diksha 
  /*public void setFlag(String aFlag){
 flag = aFlag;
}*/
   /**
    * Access method for the userId property.
    *
    * @return   the current value of the userId property
    */
   public String getUserId()
   {
      return userId;
   }
   

   public String getCheck_Flag() {
	return check_Flag;
}
public void setCheck_Flag(String check_Flag) {
	this.check_Flag = check_Flag;
}
/**
    * Sets the value of the userId property.
    *
    * @param aUserId the new value of the userId property
    */
   public void setUserId(String aUserId)
   {
      userId = aUserId;
   }

   /**
    * Access method for the bankId property.
    *
    * @return   the current value of the bankId property
    */
   public String getBankId()
   {
      return bankId;
   }

   /**
    * Sets the value of the bankId property.
    *
    * @param aBankId the new value of the bankId property
    */
   public void setBankId(String aBankId)
   {
      bankId = aBankId;
   }

   /**
    * Access method for the zoneId property.
    *
    * @return   the current value of the zoneId property
    */
   public String getZoneId()
   {
      return zoneId;
   }

   /**
    * Sets the value of the zoneId property.
    *
    * @param aZoneId the new value of the zoneId property
    */
   public void setZoneId(String aZoneId)
   {
      zoneId = aZoneId;
   }

   /**
    * Access method for the branchId property.
    *
    * @return   the current value of the branchId property
    */
   public String getBranchId()
   {
      return branchId;
   }

   /**
    * Sets the value of the branchId property.
    *
    * @param aBranchId the new value of the branchId property
    */
   public void setBranchId(String aBranchId)
   {
      branchId = aBranchId;
   }

   /**
    * Access method for the roleId property.
    *
    * @return   the current value of the roleId property
    */
   public String getRoleId()
   {
      return roleId;
   }

   /**
    * Sets the value of the roleId property.
    *
    * @param aRoleId the new value of the roleId property
    */
   public void setRoleId(String aRoleId)
   {
      roleId = aRoleId;
   }

   /**
    * Access method for the userName property.
    *
    * @return   the current value of the userName property
    */
   public String getUserName()
   {
      return userName;
   }

   /**
    * Sets the value of the userName property.
    *
    * @param aUserName the new value of the userName property
    */
   public void setUserName(String aUserName)
   {
      userName = aUserName;
   }

   /**
    * Access method for the password property.
    *
    * @return   the current value of the password property
    */
   public String getPassword()
   {
      return password;
   }

   /**
    * Sets the value of the password property.
    *
    * @param aPassword the new value of the password property
    */
   public void setPassword(String aPassword)
   {
      password = aPassword;
   }

   /**
    * Access method for the designation property.
    *
    * @return   the current value of the designation property
    */
   public String getDesignation()
   {
      return designation;
   }

   /**
    * Sets the value of the designation property.
    *
    * @param aDesignation the new value of the designation property
    */
   public void setDesignation(String aDesignation)
   {
      designation = aDesignation;
   }

   /**
    * Access method for the emailId property.
    *
    * @return   the current value of the emailId property
    */
   public String getEmailId()
   {
      return emailId;
   }

   /**
    * Sets the value of the emailId property.
    *
    * @param aemailId the new value of the emailId property
    */
   public void setEmailId(String aemailId)
   {
      emailId = aemailId;
   }

   /**
    * Access method for the superUser property.
    *
    * @return   the current value of the superUser property
    */
   public String getSuperUser()
   {
      return superUser;
   }

   /**
    * Sets the value of the superUser property.
    *
    * @param aSuperUser the new value of the superUser property
    */
   public void setSuperUser(String aSuperUser)
   {
      superUser = aSuperUser;
   }

   /**
    * Access method for the hint property.
    *
    * @return   the current value of the hint property
    */
   public Hint getHint()
   {
      return hint;
   }

   /**
    * Sets the value of the hint property.
    *
    * @param aHint the new value of the hint property
    */
   public void setHint(Hint aHint)
   {
      hint = aHint;
   }

   /**
    * Access method for the status property.
    *
    * @return   the current value of the status property
    */
   public int getStatus()
   {
      return status;
   }

   /**
    * Sets the value of the status property.
    *
    * @param aStatus the new value of the status property
    */
   public void setStatus(int aStatus)
   {
      status = aStatus;
   }

   /**
    * Access method for the passwordChangedDate property.
    *
    * @return   the current value of the passwordChangedDate property
    */
   public Date getPasswordChangedDate()
   {
      return passwordChangedDate;
   }

   /**
    * Sets the value of the passwordChangedDate property.
    *
    * @param aPasswordChangedDate the new value of the passwordChangedDate property
    */
   public void setPasswordChangedDate(Date aPasswordChangedDate)
   {
      passwordChangedDate = aPasswordChangedDate;
   }

   /**
    * Access method for the firstLogin property.
    *
    * @return   the current value of the firstLogin property
    */
   public boolean getFirstLogin()
   {
      return firstLogin;
   }

   /**
    * Sets the value of the firstLogin property.
    *
    * @param aFirstLogin the new value of the firstLogin property
    */
   public void setFirstLogin(boolean aFirstLogin)
   {
      firstLogin = aFirstLogin;
   }

   /**
    * Access method for the unsuccessfulAttempts property.
    *
    * @return   the current value of the unsuccessfulAttempts property
    */
   public int getUnsuccessfulAttempts()
   {
      return unsuccessfulAttempts;
   }

   /**
    * Sets the value of the unsuccessfulAttempts property.
    *
    * @param aUnsuccessfulAttempts the new value of the unsuccessfulAttempts property
    */
   public void setUnsuccessfulAttempts(int aUnsuccessfulAttempts)
   {
      unsuccessfulAttempts = aUnsuccessfulAttempts;
   }

   /**
    * Access method for the lastLogin property.
    *
    * @return   the current value of the lastLogin property
    */
   public Date getLastLogin()
   {
      return lastLogin;
   }

   /**
    * Sets the value of the lastLogin property.
    *
    * @param aLastLogin the new value of the lastLogin property
    */
   public void setLastLogin(Date aLastLogin)
   {
      lastLogin = aLastLogin;
   }

   /**
    * Access method for the privileges property.
    *
    * @return   the current value of the privileges property
    */
   public ArrayList getPrivileges()
   {
      return privileges;
   }

   /**
    * Sets the value of the privileges property.
    *
    * @param aPrivileges the new value of the privileges property
    */
   public void setPrivileges(ArrayList aPrivileges)
   {
      privileges = aPrivileges;
   }

   /**
    * Access method for the roles property.
    *
    * @return   the current value of the roles property
    */
   public Role getRoles()
   {
      return roles;
   }

   /**
    * Sets the value of the roles property.
    *
    * @param aRoles the new value of the roles property
    */
   public void setRoles(Role aRoles)
   {
      roles = aRoles;
   }

   /**
    * Access method for the passwordReset property.
    *
    * @return   the current value of the passwordReset property
    */
   public boolean getPasswordReset()
   {
      return passwordReset;
   }

   /**
    * Sets the value of the passwordReset property.
    *
    * @param aPasswordReset the new value of the passwordReset property
    */
   public void setPasswordReset(boolean aPasswordReset)
   {
      passwordReset = aPasswordReset;
   }

   /**
    * This method will check whether the passed privilege is held by the user. If
    * yes, it returns true.Else, returns false.
    * @param privilegeId
    * @return boolean
    * @roseuid 3972C7C60091
    */
   public boolean checkPrivilege(int privilegeId)
   {
		boolean privilegeStatus=false;
	   	if (privilegeId!=0){
		   int noOfPrivileges=0;
		   int i=0;
		   noOfPrivileges=this.privileges.size();

		   //Each of the privilege ID passed is compared with the ID of the user
		   //and the status is returned.
		   for (i=0;i<noOfPrivileges ;i++ )
			   {
				   if (privilegeId==Integer.parseInt(this.privileges.get(i).toString())){
					   privilegeStatus=true;
					   return privilegeStatus;
				   } else {
				   	privilegeStatus=false;
				   }
			   }
	   	}
		return privilegeStatus;
   }

   /**
    * returns the email id of the user
    *
    * @return String
    * @roseuid 3972C7D6010C
    */
  /* public String getEmailId()
   {
    	return null;
   }*/

   /**
    * return whether the user logs in for the first time.
    * @return boolean
    * @roseuid 398131F20369
    */
   public boolean isFirstLogin()
   {
    	return firstLogin;
   }

   /**
    * returns the last password changed date
    *
    * @return Date
    * @roseuid 3981325C02AD
    */
   public Date getPassChangeDate()
   {
    	return passwordChangedDate;
   }

   /**
    * returns the hint question of the user
    * @return String
    * @roseuid 39814318020D
    */
   public String getHintQuestion()
   {
    	return null;
   }

   /**
    * returns the hint answer of the user
    * @return String
    * @roseuid 3981441C029D
    */
   public String getHintAnswer()
   {
   		return null;
   }

   /**
    * @return boolean
    * @roseuid 3983C8A80189
    */
   public boolean isUserActive()
   {
	   boolean userStatus=false;
		//System.out.println("In user info"+getStatus());
	   if(getStatus()==AdminConstants.ACTIVE_USER_STATUS){
		   userStatus=true;
	   }
	  	return userStatus;
   }

   /**
    * @return boolean
    * @roseuid 3983CBAD023F
    */
   public boolean isPasswordReset()
   {
    	return firstLogin;
   }
/**
 * @return
 */
public String getFirstName() {
	return firstName;
}

/**
 * @param string
 */
public void setFirstName(String string) {
	firstName = string;
}

/**
 * @return
 */
public String getLastName() {
	return lastName;
}

/**
 * @return
 */
public String getMiddleName() {
	return middleName;
}

/**
 * @param string
 */
public void setLastName(String string) {
	lastName = string;
}

/**
 * @param string
 */
public void setMiddleName(String string) {
	middleName = string;
}

/**
 * @return
 */
public boolean isPasswordExpired() {
	return passwordExpired;
}

/**
 * @param b
 */
public void setPasswordExpired(boolean b) {
	passwordExpired = b;
}

/**
 * @return
 */
public String getBankName() {
	return bankName;
}

/**
 * @return
 */
public String getBranchName() {
	return branchName;
}

/**
 * @return
 */
public String getZoneName() {
	return zoneName;
}

/**
 * @param string
 */
public void setBankName(String string) {
	bankName = string;
}

/**
 * @param string
 */
public void setBranchName(String string) {
	branchName = string;
}

/**
 * @param string
 */
public void setZoneName(String string) {
	zoneName = string;
}

/**
 * @return
 */
public String getShortName() {
	return shortName;
}

/**
 * @param string
 */
public void setShortName(String string) {
	shortName = string;
}

//Added by sarita [21/05/2021] START
private String user_role;
private String employee_id;
private String userType;
private int srlId;
private String mliId;
private String cgtms_status;
private String approval_remark;
private String isAdmin;



public String getCgtms_status() {
	return cgtms_status;
}
public void setCgtms_status(String cgtms_status) {
	this.cgtms_status = cgtms_status;
}
public String getApproval_remark() {
	return approval_remark;
}
public void setApproval_remark(String approval_remark) {
	this.approval_remark = approval_remark;
}
public String getIsAdmin() {
	return isAdmin;
}
public void setIsAdmin(String isAdmin) {
	this.isAdmin = isAdmin;
}
public int getSrlId() {
	return srlId;
}
public void setSrlId(int srlId) {
	this.srlId = srlId;
}
public String getMliId() {
	return mliId;
}
public void setMliId(String mliId) {
	this.mliId = mliId;
}
public String getUserType() {
	return userType;
}
public void setUserType(String userType) {
	this.userType = userType;
}
public String getUser_role() {
	return user_role;
}
public void setUser_role(String user_role) {
	this.user_role = user_role;
}
public String getEmployee_id() {
	return employee_id;
}
public void setEmployee_id(String employee_id) {
	this.employee_id = employee_id;
}
//Added by sarita [21/05/2021] END
}
