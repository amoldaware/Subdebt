//Source file: C:\\CGTSI\\Vss\\com\\cgtsi\\admin\\ExceptionMaster.java

package com.cgtsi.admin;
import com.cgtsi.common.DatabaseException;


/**
 * This value object encapsulates the exception master details.
 */
public class ExceptionMaster extends Master 
{
   private String exceptionTitle;
   private String exceptionDesc;
   private String newExceptionTitle;//21 NOV 2003,rp14480.
   private String exceptionType;//21 NOV 2003,rp14480.
   private String exceptionMessage;
   
   AdminDAO adminDAO;
   
   /**
    * @roseuid 39B875C70117
    */
   public ExceptionMaster() 
   {
   		adminDAO=new AdminDAO();
    
   }
   
   /**
    * Access method for the exceptionTitle property.
    * 
    * @return   the current value of the exceptionTitle property
    */
   public String getExceptionTitle() 
   {
      return exceptionTitle;
   }
   
   /**
    * Sets the value of the exceptionTitle property.
    * 
    * @param aExceptionTitle the new value of the exceptionTitle property
    */
   public void setExceptionTitle(String aExceptionTitle) 
   {
      exceptionTitle = aExceptionTitle;
   }
   
   /**
    * Access method for the exceptionDesc property.
    * 
    * @return   the current value of the exceptionDesc property
    */
   public String getExceptionDesc() 
   {
      return exceptionDesc;
   }
   
   /**
    * Sets the value of the exceptionDesc property.
    * 
    * @param aExceptionDesc the new value of the exceptionDesc property
    */
   public void setExceptionDesc(String aExceptionDesc) 
   {
      exceptionDesc = aExceptionDesc;
   }
   
   /**
   * Updates the exception message details by calling the DBClass in AdminDAO
   */

   public void updateMaster() throws DatabaseException
	{		
		adminDAO.updateExceptionMaster(this);
	}
/**
 * @return
 */
public String getNewExceptionTitle() {
	return newExceptionTitle;
}

/**
 * @param string
 */
public void setNewExceptionTitle(String string) {
	newExceptionTitle = string;
}

/**
 * @return
 */
public String getExceptionType() {
	return exceptionType;
}

/**
 * @param string
 */
public void setExceptionType(String string) {
	exceptionType = string;
}

/**
 * @return
 */
public String getExceptionMessage() {
	return exceptionMessage;
}

/**
 * @param string
 */
public void setExceptionMessage(String string) {
	exceptionMessage = string;
}

}
