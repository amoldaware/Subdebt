//Source file: D:\\com\\cgtsi\\guaranteemaintenance\\RecoveryProcedure.java

package com.cgtsi.guaranteemaintenance;

import java.util.Date;
import java.io.Serializable;

public class RecoveryProcedure implements Serializable
{ 
   private String actionType;
   private String actionDetails;
   private Date actionDate;
   private String attachmentName;
   private String npaId;
   private String radId;
   
   /**
    * @roseuid 39B9CCDA00EA
    */
   public RecoveryProcedure() 
   {
    
   }
   
   /**
    * Access method for the actionType property.
    * 
    * @return   the current value of the actionType property
    */
   public String getActionType() 
   {
      return actionType;    
   }
   
   /**
    * Sets the value of the actionType property.
    * 
    * @param aActionType the new value of the actionType property
    */
   public void setActionType(String aActionType) 
   {
      actionType = aActionType;
   }
   
   /**
    * Access method for the actionDetails property.
    * 
    * @return   the current value of the actionDetails property
    */
   public String getActionDetails() 
   {
      return actionDetails;    
   }
   
   /**
    * Sets the value of the actionDetails property.
    * 
    * @param aActionDetails the new value of the actionDetails property
    */
   public void setActionDetails(String aActionDetails) 
   {
      actionDetails = aActionDetails;
   }
   
   /**
    * Access method for the actionDate property.
    * 
    * @return   the current value of the actionDate property
    */
   public Date getActionDate() 
   {
      return actionDate;    
   }
   
   /**
    * Sets the value of the actionDate property.
    * 
    * @param aActionDate the new value of the actionDate property
    */
   public void setActionDate(Date aActionDate) 
   {
      actionDate = aActionDate;
   }
   
   /**
    * Access method for the fileName property.
    * 
    * @return   the current value of the fileName property
    */
   public String getAttachmentName() 
   {
      return attachmentName;    
   }
   
   /**
    * Sets the value of the fileName property.
    * 
    * @param aFileName the new value of the fileName property
    */
   public void setAttachmentName(String aAttachmentName) 
   {
      attachmentName = aAttachmentName;
   }

/**
 * @return
 */
public String getNpaId() {
	return npaId;
}

/**
 * @param string
 */
public void setNpaId(String string) {
	npaId = string;
}

/**
 * @return
 */
public String getRadId() {
	return radId;
}

/**
 * @param string
 */
public void setRadId(String string) {
	radId = string;
}

}
