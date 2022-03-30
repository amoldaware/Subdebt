//Source file: C:\\CGTSI\\Vss\\com\\cgtsi\\common\\AuditDetails.java

package com.cgtsi.common;


public class AuditDetails 
{
   private String auditComments;
   private String auditedBy;
   private int auditedDate;
   private int auditId;
   
   private String message=null;
   
   /**
    * @roseuid 39B875CE014A
    */
   public AuditDetails() 
   {
    
   }
   
   /**
    * Access method for the auditComments property.
    * 
    * @return   the current value of the auditComments property
    */
   public String getAuditComments() 
   {
      return auditComments;
   }
   
   /**
    * Sets the value of the auditComments property.
    * 
    * @param aauditComments the new value of the auditComments property
    */
   public void setAuditComments(String aAuditComments) 
   {
      auditComments = aAuditComments;
   }
   
   /**
    * Access method for the auditedBy property.
    * 
    * @return   the current value of the auditedBy property
    */
   public String getAuditedBy() 
   {
      return auditedBy;
   }
   
   /**
    * Sets the value of the auditedBy property.
    * 
    * @param aAuditedBy the new value of the auditedBy property
    */
   public void setAuditedBy(String aAuditedBy) 
   {
      auditedBy = aAuditedBy;
   }
   
   /**
    * Access method for the auditedDate property.
    * 
    * @return   the current value of the auditedDate property
    */
   public int getAuditedDate() 
   {
      return auditedDate;
   }
   
   /**
    * Sets the value of the auditedDate property.
    * 
    * @param aAuditedDate the new value of the auditedDate property
    */
   public void setAuditedDate(int aAuditedDate) 
   {
      auditedDate = aAuditedDate;
   }
   
   /**
   * Access method for the auditId property.
   * 
   * @return   the current value of the auditId property
   */
	public int getAuditId() {
		
		return auditId;
	}
	
	/**
	 * Sets the value of the auditId property.
	 * 
	 * @param aAuditId the new value of the auditId property
	 */
	public void setAuditId(int aAuditId) {
		
		auditId = aAuditId;
	}

/**
 * @return
 */
public String getMessage() {
	return message;
}

/**
 * @param string
 */
public void setMessage(String string) {
	message = string;
}

}
