//Source file: C:\\CGTSI\\Vss\\com\\cgtsi\\common\\InvalidPasswordException.java

package com.cgtsi.common;


/**
 * If the password supplied by the user is incorrect, this exception would be 
 * thrown.
 */
public class InvalidPasswordException extends Exception
{
   
   /**
    * @roseuid 39B875CE008B
    */
   public InvalidPasswordException() 
   {
   	super();
    
   }
   public InvalidPasswordException(String message)
   {
   	super(message);
   }
}
