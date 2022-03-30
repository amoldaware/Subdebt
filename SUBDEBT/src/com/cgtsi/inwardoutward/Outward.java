//Source file: F:\\CGTSI\\Design\\com\\cgtsi\\inwardoutward\\Outward.java

package com.cgtsi.inwardoutward;  


/**
 * This class encapsulates the various details of a Outward Communication. All 
 * Outward communications have an Id associated with them. The Outward 
 * communication may be associated with an Inward Id.
 */
public class Outward 
{
   private java.lang.String destinationType;
   private java.lang.String destinationName;
   private java.lang.String documentType1; 
   private java.lang.String modeOfDelivery;
   private java.lang.String referenceId;
   private java.lang.String destinationRef;
   
   /**
    * if the outward document is mapped to an inward document, then this attribute 
    * signifies that inward reference.
    */
   private java.lang.String mappedInward;
   private java.util.Date documentSentDate;
   private java.lang.String language;
   private java.lang.String subject;
   private java.lang.String remarks;
   private java.lang.String processedBy;
   private java.lang.String fileTitle;
   private java.lang.String fileNumber;
   private java.lang.String filePath1;
   private java.lang.String outwardId;
	// To fix bug 07092004-18
   private java.util.Date dateOfDoc;
   //Fix is completed.
   
   public Outward() 
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
      return documentType1;    
   }
   
   /**
    * Sets the value of the documentType property.
    * 
    * @param aDocumentType the new value of the documentType property
    */
   public void setDocumentType(java.lang.String aDocumentType) 
   {
      documentType1 = aDocumentType;
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
   
   /**
    * Access method for the referenceId property.
    * 
    * @return   the current value of the referenceId property
    */
   public java.lang.String getReferenceId() 
   {
      return referenceId;    
   }
   
   /**
    * Sets the value of the referenceId property.
    * 
    * @param aReferenceId the new value of the referenceId property
    */
   public void setReferenceId(java.lang.String aReferenceId) 
   {
      referenceId = aReferenceId;
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
    * Access method for the mappedInward property.
    * 
    * @return   the current value of the mappedInward property
    */
   public java.lang.String getMappedInward() 
   {
      return mappedInward;    
   } 
   
   /**
    * Sets the value of the mappedInward property.
    * 
    * @param aMappedInward the new value of the mappedInward property
    */
   public void setMappedInward(java.lang.String aMappedInward) 
   {
      mappedInward = aMappedInward;
   }
   
   /**
    * Access method for the documentSentDate property.
    * 
    * @return   the current value of the documentSentDate property
    */
   public java.util.Date getDocumentSentDate() 
   {
      return documentSentDate;    
   }
   
   /**
    * Sets the value of the documentSentDate property.
    * 
    * @param aDocumentSentDate the new value of the documentSentDate property
    */
   public void setDocumentSentDate(java.util.Date aDocumentSentDate)  
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
      language= aLanguage;
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
    * Access method for the processedBy property.
    * 
    * @return   the current value of the processedBy property
    */
   public java.lang.String getProcessedBy() 
   {
      return processedBy;    
   }
   
   /**
    * Sets the value of the processedBy property.
    * 
    * @param aProcessedBy the new value of the processedBy property
    */
   public void setProcessedBy(java.lang.String aProcessedBy) 
   {
      processedBy = aProcessedBy;
   }
   
   /**
    * Access method for the fileTitle property.
    * 
    * @return   the current value of the fileTitle property
    */
   public java.lang.String getFileTitle() 
   {
      return fileTitle;    
   }
   
   /**
    * Sets the value of the fileTitle property.
    * 
    * @param aFileTitle the new value of the fileTitle property
    */
   public void setFileTitle(java.lang.String aFileTitle) 
   {
      fileTitle = aFileTitle;
   }
   
   /**
    * Access method for the fileNumber property.
    * 
    * @return   the current value of the fileNumber property
    */
   public java.lang.String getFileNumber() 
   {
      return fileNumber;    
   }
   
   /**
    * Sets the value of the fileNumber property.
    * 
    * @param aFileNumber the new value of the fileNumber property
    */
   public void setFileNumber(java.lang.String aFileNumber) 
   {
      fileNumber = aFileNumber;
   }
   
   /**
    * Access method for the filePath property.
    * 
    * @return   the current value of the filePath property
    */
   public java.lang.String getFilePath() 
   {
      return filePath1;    
   }
   
   /**
    * Sets the value of the filePath property.
    * 
    * @param aFilePath the new value of the filePath property
    */
   public void setFilePath(java.lang.String aFilePath) 
   {
      filePath1 = aFilePath;
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
   
   /**
    * @param sID
    */
   public void setOutwardID(java.lang.String sID) 
   {
    
   }
   
   /**
    * @return java.lang.String
    */
   public java.lang.String getOutwardID() 
   {
    return null;
   }
/**
 * @return
 */
public java.util.Date getDateOfDoc() {
	return dateOfDoc;
}

/**
 * @return
 */
public java.lang.String getDestinationRef() {
	return destinationRef;
}

/**
 * @return
 */
public java.lang.String getDocumentType1() {
	return documentType1;
}

/**
 * @return
 */
public java.lang.String getFilePath1() {
	return filePath1;
}

/**
 * @param date
 */
public void setDateOfDoc(java.util.Date date) {
	dateOfDoc = date;
}

/**
 * @param string
 */
public void setDestinationRef(java.lang.String string) {
	destinationRef = string;
}

/**
 * @param string
 */
public void setDocumentType1(java.lang.String string) {
	documentType1 = string;
}

/**
 * @param string
 */
public void setFilePath1(java.lang.String string) {
	filePath1 = string;
}

}
