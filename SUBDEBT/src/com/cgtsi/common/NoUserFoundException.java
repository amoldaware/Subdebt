//Source file: C:\\CGTSI\\Vss\\com\\cgtsi\\common\\NoUserFoundException.java

package com.cgtsi.common;


/**
 * This exception would be thrown if the user id submited is not found in the data 
 * base.
 */
public class NoUserFoundException extends Exception
{
   
   /**
    * @roseuid 39B875CE0059
    */
   public NoUserFoundException() 
   {
    	super();
   }
   
   public NoUserFoundException(String message)
   {
   	super(message);
   }
}
