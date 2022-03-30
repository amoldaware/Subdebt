//Source file: C:\\CGTSI\\Vss\\com\\cgtsi\\application\\Application.java

package com.cgtsi.application;
import java.util.Date;
import java.io.Serializable;

/**
 * This class represents the detials of the Special Message.
 */

 public class SpecialMessage implements Serializable
 {
	 private String msgTitle;
	 private String messageDesc;
	 private Date validityFromDate;
	 private Date validityToDate;

	 public SpecialMessage()
	 {
	 }

	/**
	* Access method for the msgTitle property.
	* 
	* @return   the current value of the msgTitle property
	*/
   public String getMsgTitle() 
   {
	  return msgTitle;
   }
   
   /**
	* Sets the value of the msgTitle property.
	* 
	* @param aMsgTitle the new value of the msgTitle property
	*/
   public void setMsgTitle(String aMsgTitle) 
   {
	  msgTitle= aMsgTitle;
   }

   /**
	* Access method for the messageDesc property.
	* 
	* @return   the current value of the messageDesc property
	*/
   public String getMessageDesc() 
   {
	  return messageDesc;
   }
   
   /**
	* Sets the value of the messageDesc property.
	* 
	* @param aMessageDesc the new value of the messageDesc property
	*/
   public void setMessageDesc(String aMessageDesc) 
   {
	  messageDesc= aMessageDesc;
   }

   /**
	* Access method for the validityFromDate property.
	* 
	* @return   the current value of the validityFromDate property
	*/
   public Date getValidityFromDate() 
   {
	  return validityFromDate;
   }
   
   /**
	* Sets the value of the validityFromDate property.
	* 
	* @param aValidityFromDate the new value of the validityFromDate property
	*/
   public void setValidityFromDate(Date aValidityFromDate) 
   {
	  validityFromDate= aValidityFromDate;
   }

   /**
	* Access method for the validityToDate property.
	* 
	* @return   the current value of the validityToDate property
	*/
   public Date getValidityToDate() 
   {
	  return validityToDate;
   }
   
   /**
	* Sets the value of the validityToDate property.
	* 
	* @param aValidityToDate the new value of the validityToDate property
	*/
   public void setValidityToDate(Date aValidityToDate) 
   {
	  validityToDate= aValidityToDate;
   }
 };
