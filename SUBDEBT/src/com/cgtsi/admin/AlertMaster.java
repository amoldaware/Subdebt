//Source file: C:\\CGTSI\\Vss\\com\\cgtsi\\admin\\AlertMaster.java

package com.cgtsi.admin;
import com.cgtsi.common.DatabaseException;

/**
 * This value object encapsulates the alert master details.
 */
public class AlertMaster extends Master 
{
   private String alertTitle;
   private String alertMessage;
   private String newAlert; //added by Ramesh rp14480.
   private String createdBy;//added by Ramesh rp14480.
   
   AdminDAO adminDAO;
   
   /**
    * @roseuid 39B875C700D1
    */
   public AlertMaster() 
   {
   		adminDAO=new AdminDAO();
    
   }
   
   /**
    * Access method for the alertTitle property.
    * 
    * @return   the current value of the alertTitle property
    */
   public String getAlertTitle() 
   {
      return alertTitle;
   }
   
   /**
    * Sets the value of the alertTitle property.
    * 
    * @param aAlertTitle the new value of the alertTitle property
    */
   public void setAlertTitle(String aAlertTitle) 
   {
      alertTitle = aAlertTitle;
   }
   
   /**
    * Access method for the alertMessage property.
    * 
    * @return   the current value of the alertMessage property
    */
   public String getAlertMessage() 
   {
      return alertMessage;
   }
   
   /**
    * Sets the value of the alertMessage property.
    * 
    * @param aAlertMessage the new value of the alertMessage property
    */
   public void setAlertMessage(String aAlertMessage) 
   {
      alertMessage = aAlertMessage;
   }
	/**
	* Updates the alert message by calling the DBClass in AdminDAO
	*/
    public void updateMaster() throws DatabaseException
	{		
		adminDAO.updateAlertMaster(this);
	}
/**
 * @return
 */
public String getCreatedBy() {
	return createdBy;
}

/**
 * @param string
 */
public void setCreatedBy(String string) {
	createdBy = string;
}

/**
 * @return
 */
public String getNewAlert() {
	return newAlert;
}

/**
 * @param string
 */
public void setNewAlert(String string) {
	newAlert = string;
}

}
