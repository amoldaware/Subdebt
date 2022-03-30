package com.cgtsi.claim;


/**
 * If export of the details fails, this exception is thrown.
 */
public class LockInPeriodNotCompletedException extends Exception
{

   public LockInPeriodNotCompletedException()
   {
    	super();
   }
   public LockInPeriodNotCompletedException(String message)
   {
   		super(message);
   }
}
