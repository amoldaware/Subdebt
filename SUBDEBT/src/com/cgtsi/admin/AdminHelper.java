//Source file: C:\\CGTSI\\Vss\\com\\cgtsi\\admin\\AdminHelper.java

package com.cgtsi.admin;
import java.util.ArrayList;

import java.util.Random;
import com.cgtsi.admin.PasswordManager;

/**
 * This is a helper class.Helping the system to do their normal functionality.
 */
public class AdminHelper 
{
   
   /**
    * @roseuid 39B875C500B0
    */
   public AdminHelper() 
   {
    
   }
   
   private static final String ALPHA_NUM = "!@#$%0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"; 

   
   
   /**
    * This method is used to generate user ids
    * @param bankId
    * @return ArrayList
    * @roseuid 3972AB8B012D
    */
   public ArrayList generateUserIds(String bankId) 
   {
    return null;
   }
   
   /**
    * This method is used to generate passwords
    * @roseuid 3972AB8B012E
    */
   public void generatePasswords() 
   {
    
   }
   
   /**
    * @param bankId
    * @roseuid 39796098034A
    */
   public void getNoofUsers(String bankId) 
   {
    
   }
   
   /**
    * Generates user id for a given user 
    * @param user
    * @return String
    * @roseuid 398112950364
    */
   public String generateUserId(User user) 
   {
	   	String userid=null;
	   	
		if (user!=null){	
		  userid="satyam";			
		} else {
		  userid="";
		}
		return userid;
   }
   
   /**
    * Generates password for a given user
    * @return String
    * @roseuid 398112C00302
    */
   public String generatePassword() 
   {
	   	// String password=AdminConstants.PASSWORD;
      PasswordManager passwordManager=new PasswordManager();			
      
      /* added following code2 by sukumar@path for testing of password */
      
       Random rand = new Random(System.currentTimeMillis());
       StringBuffer sb1 = new StringBuffer();
        for (int i = 0; i < 10; i++) {
            int pos = rand.nextInt(ALPHA_NUM.length());
            sb1.append(ALPHA_NUM.charAt(pos));
        }
     // System.out.println("Test Password 2 is :"+sb1.toString());
     String encryptedPasswordNew=new PasswordManager().encryptPassword(sb1.toString());
    // System.out.println("encryptedPassword 2:"+encryptedPasswordNew);
     //String testpassword2=passwordManager.decryptPassword(encryptedPasswordNew);
     //System.out.println("testpassword2:"+testpassword2);
      /* end password test2 here */ 
	   
	   //	String encryptedPassword=new PasswordManager().encryptPassword(password);
	   	
	   	return encryptedPasswordNew;
   }
}
