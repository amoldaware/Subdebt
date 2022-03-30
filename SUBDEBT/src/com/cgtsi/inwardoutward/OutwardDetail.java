//Source file: F:\\CGTSI\\Design\\com\\cgtsi\\inwardoutward\\Outward.java

package com.cgtsi.inwardoutward; 


/**
 * This class encapsulates the various details of a Outward Communication. All 
 * Outward communications have an Id associated with them. The Outward 
 * communication may be associated with an Inward Id.
 */
public class OutwardDetail
{
   private java.lang.String destinationType;
   private java.lang.String destinationName;
   private java.lang.String documentType; 
   private java.lang.String modeOfDelivery;
   private java.lang.String destinationRef;
   private java.lang.String documentSentDate;
   private java.lang.String language;
   private java.lang.String subject;
   private java.lang.String remarks;
    private java.lang.String outwardId;
   
   public OutwardDetail() 
   {
    
   }
   
   /**
    * Access method for the destinationType property.
    * 
    * @return   the current value of the destinationType property
    */
   public java.lang.String getDestinationType() 
   {
      return destinationType;    
   }
   
   /**
    * Sets the value of the destinationType property.
    * 
    * @param aDestinationType the new value of the destinationType property
    */
   public void setDestinationType(java.lang.String aDestinationType) 
   {
      destinationType = aDestinationType;
   }
   
   /**
    * Access method for the destinationName property.
    * 
    * @return   the current value of the destinationName property
    */
   public java.lang.String getDestinationName() 
   {
      return destinationName;    
   }
   
   /**
    * Sets the value of the destinationName property.
    * 
    * @param aDestinationName the new value of the destinationName property
    */
   public void setDestinationName(java.lang.String aDestinationName) 
   {
      destinationName = aDestinationName;
   }
   
   /**
    * Access method for the documentType property.
    * 
    * @return   the current value of the documentType property
    */
   public java.lang.String getDocumentType() 
   {
      return documentType;    
   }
   
   /**
    * Sets the value of the documentType property.
    * 
    * @param aDocumentType the new value of the documentType property
    */
   public void setDocumentType(java.lang.String aDocumentType) 
   {
      documentType = aDocumentType;
   }
   
   /**
    * Access method for the modeOfDelivery property.
    * 
    * @return   the current value of the modeOfDelivery property
    */
   public java.lang.String getModeOfDelivery() 
   {
      return modeOfDelivery;    
   }
   
   /**
    * Sets the value of the modeOfDelivery property.
    * 
    * @param aModeOfDelivery the new value of the modeOfDelivery property
    */
   public void setModeOfDelivery(java.lang.String aModeOfDelivery) 
   {
      modeOfDelivery = aModeOfDelivery;  
   }
   
   public java.lang.String getdestinationRef() 
	 {
		return destinationRef;    
	 }
   
	 /**
	  * Sets the value of the referenceId property.
	  * 
	  * @param aReferenceId the new value of the referenceId property
	  */
	 public void setdestinationRef(java.lang.String adestinationRef) 
	 {
		destinationRef= adestinationRef;
	 }
   
   
   /**
    * Access method for the documentSentDate property.
    * 
    * @return   the current value of the documentSentDate property
    */
   public java.lang.String getDocumentSentDate() 
   {
      return documentSentDate;    
   }
   
   /**
    * Sets the value of the documentSentDate property.
    * 
    * @param aDocumentSentDate the new value of the documentSentDate property
    */
   public void setDocumentSentDate(java.lang.String aDocumentSentDate)  
   {
      documentSentDate = aDocumentSentDate;
   }
   
   /**
    * Access method for the language property.
    * 
    * @return   the current value of the language property
    */
   public java.lang.String getLanguage() 
   {
      return language;    
   }
   
   /**
    * Sets the value of the language property.
    * 
    * @param aLanguage the new value of the language property
    */
   public void setLanguage(java.lang.String aLanguage) 
   {
      language = aLanguage;
   }
   
   /**
    * Access method for the subject property.
    * 
    * @return   the current value of the subject property
    */
   public java.lang.String getSubject() 
   {
      return subject;    
   }
   
   /**
    * Sets the value of the subject property.
    * 
    * @param aSubject the new value of the subject property
    */
   public void setSubject(java.lang.String aSubject) 
   {
      subject = aSubject;
   }
   
   /**
    * Access method for the remarks property.
    * 
    * @return   the current value of the remarks property
    */
   public java.lang.String getRemarks() 
   {
      return remarks;    
   }
   
   /**
    * Sets the value of the remarks property.
    * 
    * @param aRemarks the new value of the remarks property
    */
   public void setRemarks(java.lang.String aRemarks) 
   {
      remarks = aRemarks;
   }
   
   
   /**
    * Access method for the outwardId property.
    * 
    * @return   the current value of the outwardId property
    */
   public java.lang.String getOutwardId() 
   {
      return outwardId;    
   }
   
   /**
    * Sets the value of the outwardId property.
    * 
    * @param aOutwardId the new value of the outwardId property
    */
   public void setOutwardId(java.lang.String aOutwardId) 
   {
      outwardId = aOutwardId;
   }
   
}
