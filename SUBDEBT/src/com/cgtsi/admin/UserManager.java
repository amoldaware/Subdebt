//Source file: C:\\CGTSI\\Vss\\com\\cgtsi\\admin\\UserManager.java

package com.cgtsi.admin;

import com.cgtsi.common.DatabaseException;


/**
 * This is a interface.which would be implemented by other admin privileged users 
 * to manage users
 */
public class UserManager 
{
   
   /**
    * @roseuid 39B875C6035B
    */
   public UserManager() 
   {
    
   }
   
   /**
    * This method is used to reactivate a deactivated  User.
    * @param userID
    * @roseuid 3972AB8B0135
    */
   public void reactivateUser(String userID)throws DatabaseException 
   {
    
   }
   
   /**
    * Any user can be deactivated through this method.
    * @param userID
    * @roseuid 3972AB8B0137
    */
   public void deactivateUser(String userID) throws DatabaseException
   {
    
   }
   
   /**
    * After modifying the user data, this method is called to update the changes.
    * @param user
    * @roseuid 3972AB8B0138
    */
   public void modifyUser(User user) throws DatabaseException
   {
    
   }
}
