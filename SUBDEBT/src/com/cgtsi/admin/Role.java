//Source file: C:\\CGTSI\\Vss\\com\\cgtsi\\admin\\Role.java

package com.cgtsi.admin;
import java.util.ArrayList;


/**
 * This class holds the role related information to be assigned to a user while
 * logs in.This class in turn uses the Privilege class to know the details about
 * the privileges associated with the Role
 */
public class Role extends Master
{
   public String roleName = null;
   public String roleDescription = null;

   /**
    * collection of privileges
    */
   public ArrayList privileges = null;

   /**
    * @roseuid 39B875C600EE
    */
   public Role()
   {

   }

   /**
    * Access method for the roleName property.
    *
    * @return   the current value of the roleName property
    */
   public String getRoleName()
   {
      return roleName;
   }

   /**
    * Sets the value of the roleName property.
    *
    * @param aRoleName the new value of the roleName property
    */
   public void setRoleName(String aRoleName)
   {
      roleName = aRoleName;
   }

   /**
    * Access method for the roleDesc property.
    *
    * @return   the current value of the roleDesc property
    */
   public String getRoleDescription()
   {
      return roleDescription;
   }

   /**
    * Sets the value of the roleDesc property.
    *
    * @param aRoleDesc the new value of the roleDesc property
    */
   public void setRoleDescription(String aRoleDesc)
   {
		roleDescription = aRoleDesc;
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
}
