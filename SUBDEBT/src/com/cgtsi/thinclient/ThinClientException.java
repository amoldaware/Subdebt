package com.cgtsi.thinclient;


/**
 * This exception is thrown whenever there is an error operating in Thin Client
 */
public class ThinClientException extends Exception
{
   public ThinClientException() 
   {
    super();
   }
   public ThinClientException(String message) 
	 {
    	super(message);
	 }
}
