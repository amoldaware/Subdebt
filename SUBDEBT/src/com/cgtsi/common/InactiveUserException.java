//Source file: C:\\CGTSI\\Vss\\com\\cgtsi\\common\\InactiveUserException.java

package com.cgtsi.common;


/**
 * If the deactivated user tries to enter into the system this exception would be 
 * thrown.
 */
public class InactiveUserException extends Exception
{
   
   /**
    * @roseuid 39B875CE00BD
    */
   public InactiveUserException() 
   {
    	super();
   }
   
   public InactiveUserException(String message)
   {
   	super(message);
   }
}
