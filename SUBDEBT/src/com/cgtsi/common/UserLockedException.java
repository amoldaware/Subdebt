//Source file: C:\\CGTSI\\Vss\\com\\cgtsi\\common\\UserLockedException.java

package com.cgtsi.common;


/**
 * If the user is locked after three unsuccessfull attempts, this exception is 
 * thrown.
 */
public class UserLockedException extends Exception
{
   
   /**
    * @roseuid 39B875CE010D
    */
   public UserLockedException() 
   {
    	super();
   }
   
   public UserLockedException(String message)
   {
   		super(message);
   }
}
