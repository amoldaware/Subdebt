//Source file: C:\\CGTSI\\Vss\\com\\cgtsi\\admin\\Message.java

package com.cgtsi.admin;
import java.util.Date;
import java.util.ArrayList;



/**
 * This class deal with message related data.This class is used to send emails, 
 * mails across users.
 */
public class Message 
{
   private String message = null;
   String from = null;
   private ArrayList to = null;
   private ArrayList cc = null;
   private ArrayList bcc = null;
   private Date date = null;
   private String status;
   private String subject;
   
   private int messageId=0;
   
   private String bankId;
   private String zoneId;
   private String branchId;
   
   /**
    * @roseuid 39B875C60062
    */
   public Message() 
   {
    /*this.message = message;
    this.to = to;
    this.cc = cc;
    this.bcc = bcc;
    this.subject=subject;*/
   }
   
   /**
    * This is message constructor. The required information are fed through the 
    * constructor.
    * @param to
    * @param cc
    * @param bcc
    * @param subject
    * @param message
    * @roseuid 3981115B00C3
    */
   public Message(ArrayList to, ArrayList cc, ArrayList bcc, String subject, String message) 
   {		
   	//assigning the passed arraylist to the arraylist in the class
		setTo(to);	
		setCc(cc);
		setBcc(bcc);  
	//assigning the Strings to the variable declared in teh class
		setSubject(subject); 
		setMessage(message);		
		
   }
   
   /**
    * Access method for the message property.
    * 
    * @return   the current value of the message property
    */
   public String getMessage() 
   {
      return message;
   }
   
   /**
    * Sets the value of the message property.
    * 
    * @param aMessage the new value of the message property
    */
   public void setMessage(String aMessage) 
   {
      message = aMessage;
   }
   
   /**
    * Access method for the from property.
    * 
    * @return   the current value of the from property
    */
   public String getFrom() 
   {
      return from;
   }
   
   /**
    * Sets the value of the from property.
    * 
    * @param aFrom the new value of the from property
    */
   public void setFrom(String aFrom) 
   {
      from = aFrom;
   }
   
   /**
    * Access method for the to property.
    * 
    * @return   the current value of the to property
    */
   public ArrayList getTo() 
   {
      return to;
   }
   
   /**
    * Sets the value of the to property.
    * 
    * @param aTo the new value of the to property
    */
   public void setTo(ArrayList aTo) 
   {
      to = aTo;
   }
   
   /**
    * Access method for the cc property.
    * 
    * @return   the current value of the cc property
    */
   public ArrayList getCc() 
   {
      return cc;
   }
   
   /**
    * Sets the value of the cc property.
    * 
    * @param aCc the new value of the cc property
    */
   public void setCc(ArrayList aCc) 
   {
      cc = aCc;
   }
   
   /**
    * Access method for the bcc property.
    * 
    * @return   the current value of the bcc property
    */
   public ArrayList getBcc() 
   {
      return bcc;
   }
   
   /**
    * Sets the value of the bcc property.
    * 
    * @param aBcc the new value of the bcc property
    */
   public void setBcc(ArrayList aBcc) 
   {
      bcc = aBcc;
   }
   
   /**
    * Access method for the date property.
    * 
    * @return   the current value of the date property
    */
   public Date getDate() 
   {
      return date;
   }
   
   /**
    * Sets the value of the date property.
    * 
    * @param aDate the new value of the date property
    */
   public void setDate(Date aDate) 
   {
      date = aDate;
   }
   
   /**
    * Access method for the status property.
    * 
    * @return   the current value of the status property
    */
   public String getStatus() 
   {
      return status;
   }
   
   /**
    * Sets the value of the status property.
    * 
    * @param aStatus the new value of the status property
    */
   public void setStatus(String aStatus) 
   {
      status = aStatus;
   }
   
   /**
    * Access method for the subject property.
    * 
    * @return   the current value of the subject property
    */
   public String getSubject() 
   {
      return subject;
   }
   
   /**
    * Sets the value of the subject property.
    * 
    * @param aSubject the new value of the subject property
    */
   public void setSubject(String aSubject) 
   {
      subject = aSubject;
   }
/**
 * @return
 */
public int getMessageId() {
	return messageId;
}

/**
 * @param i
 */
public void setMessageId(int i) {
	messageId = i;
}

/**
 * @return
 */
public String getBankId() {
	return bankId;
}

/**
 * @return
 */
public String getBranchId() {
	return branchId;
}

/**
 * @return
 */
public String getZoneId() {
	return zoneId;
}

/**
 * @param string
 */
public void setBankId(String string) {
	bankId = string;
}

/**
 * @param string
 */
public void setBranchId(String string) {
	branchId = string;
}

/**
 * @param string
 */
public void setZoneId(String string) {
	zoneId = string;
}

}
