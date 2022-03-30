package com.cgtsi.claim;


/**
 * If export of the details fails, this exception is thrown.
 */
public class ExportFailedException extends Exception
{
   
   public ExportFailedException() 
   {
    	super();
   }
   public ExportFailedException(String message)
   {
   		super(message);
   }
}
