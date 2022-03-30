//Source file: F:\\CGTSI\\Design\\com\\cgtsi\\inwardoutward\\Inward.java

package com.cgtsi.inwardoutward; 


/**
 * This class encapsulates the various details of an Inward Communication. All
 * Inward communications have an Id associated with them. The Inward communication
 * may be associated with an Outward id.
 */
public class Inward 
{
   private String sourceName;
   private String sourceType;
   private String sourceId;
   private String sourceRef;
   private java.lang.String documentType;
   private java.lang.String modeOfReceipt;
   private java.util.Date dateOfDocument; 
   private java.lang.String language;
   private java.lang.String subject;
   private java.lang.String remarks;
   private java.lang.String mappedOutwardID;
   private java.lang.String processedBy;
   private java.lang.String fileTitle;
   private java.lang.String fileNumber;
   private java.lang.String filePath;
   private java.lang.String inwardId;
   // To fix bug 07092004-18
  private java.util.Date dateOfDoc;
  //Fix is completed.
   

   public Inward()
   {

   }

   /**
    * Access method for the sourceName property.
    *
    * @return   the current value of the sourceName property
    */
   public String getSourceName()
   {
      return sourceName;
   }

   /**
    * Sets the value of the sourceName property.
    *
    * @param aSourceName the new value of the sourceName property
    */
   public void setSourceName(String aSourceName)
   {
      sourceName = aSourceName;
   }

   /**
    * Access method for the sourceType property.
    *
    * @return   the current value of the sourceType property
    */
   public String getSourceType()
   {
      return sourceType;
   }

   /**
    * Sets the value of the sourceType property.
    *
    * @param aSourceType the new value of the sourceType property
    */
   public void setSourceType(String aSourceType)
   {
      sourceType = aSourceType;
   }

   /**
    * Access method for the sourceId property.
    *
    * @return   the current value of the sourceId property
    */
   public String getSourceId()
   {
      return sourceId;
   }

   /**
    * Sets the value of the sourceId property.
    *
    * @param aSourceId the new value of the sourceId property
    */
   public void setSourceId(String aSourceId)
   {
      sourceId = aSourceId;
   }

   /**
    * Access method for the sourceRef property.
    *
    * @return   the current value of the sourceRef property
    */
   public String getSourceRef()
   {
      return sourceRef;
   }

   /**
    * Sets the value of the sourceRef property.
    *
    * @param aSourceRef the new value of the sourceRef property
    */
   public void setSourceRef(String aSourceRef)
   {
      sourceRef = aSourceRef;
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
    * Access method for the modeOfReceipt property.
    *
    * @return   the current value of the modeOfReceipt property
    */
   public java.lang.String getModeOfReceipt()
   {
      return modeOfReceipt;
   }

   /**
    * Sets the value of the modeOfReceipt property.
    *
    * @param aModeOfReceipt the new value of the modeOfReceipt property
    */
   public void setModeOfReceipt(java.lang.String aModeOfReceipt)
   {
      modeOfReceipt = aModeOfReceipt;
   }

   /**
    * Access method for the dateOfDocument property.
    *
    * @return   the current value of the dateOfDocument property
    */
   public java.util.Date getDateOfDocument()
   {
      return dateOfDocument;
   }

   /**
    * Sets the value of the dateOfDocument property.
    *
    * @param dateOfDocument the new value of the dateOfDocument property
    */
   public void setDateOfDocument(java.util.Date adateOfDocument)
   {
      dateOfDocument = adateOfDocument;
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
    * Access method for the mappedOutwardID property.
    *
    * @return   the current value of the mappedOutwardID property
    */
   public java.lang.String getMappedOutwardID()
   {
      return mappedOutwardID;
   }

   /**
    * Sets the value of the mappedOutwardID property.
    *
    * @param aMappedOutwardID the new value of the mappedOutwardID property
    */
   public void setMappedOutwardID(java.lang.String aMappedOutwardID)
   {
      mappedOutwardID = aMappedOutwardID;
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
      return filePath;
   }

   /**
    * Sets the value of the filePath property.
    *
    * @param aFilePath the new value of the filePath property
    */
   public void setFilePath(java.lang.String aFilePath)
   {
      filePath = aFilePath;
   }

   /**
    * Access method for the inwardId property.
    *
    * @return   the current value of the inwardId property
    */
   public java.lang.String getInwardId()
   {
      return inwardId;
   }

   /**
    * Sets the value of the inwardId property.
    *
    * @param aInwardId the new value of the inwardId property
    */
   public void setInwardId(java.lang.String aInwardId)
   {
      inwardId = aInwardId;
   }

   /**
    * @param sID
    */
   public void setInwardID(java.lang.String sID)
   {

   }

   /**
    * @return java.lang.String
    */
   public java.lang.String getInwardID()
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
 * @param date
 */
public void setDateOfDoc(java.util.Date date) {
	dateOfDoc = date;
}

}
