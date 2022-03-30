//Source file: C:\\CGTSI\\Vss\\com\\cgtsi\\common\\MailerException.java

package com.cgtsi.common;


/**
 * If any exception is thrown while sending an email, Mailer exeption is thrown.
 */
public class MailerException extends Exception
{
   
   /**
    * @roseuid 39B875CF001F
    */
   public MailerException() 
   {
    	super();
   }
   public MailerException(String message)
   {
   		super(message);
   }
}
