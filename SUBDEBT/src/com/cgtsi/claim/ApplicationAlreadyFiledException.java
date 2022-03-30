package com.cgtsi.claim;


/**
 * If the application for claim first and second installment(s) is already filed,
 * this exception may be thrown.
 */
public class ApplicationAlreadyFiledException extends Exception
{

   public ApplicationAlreadyFiledException()
   {
    	super();
   }
   public ApplicationAlreadyFiledException(String message)
   {
   		super(message);
   }
}
