package com.cgtsi.knowledge;

import java.util.Date;

public class Document 
{
   private Date dateOfDocument;
   private String subject;
   private String remarks;
   private String inResponseToID;
   private String category;
   private String documentTitle;
   private String fileNumber;
   private String documentPath;
   private String documentRefNumber;
   private String documentDescription;	// added 06/10/2003 - Nithya
   private String user;   //added 10/11/2003 Radjesh 
   
   public Document() 
   {
    
   }
   
   /**
    * Access method for the dateOfDocument property.
    * 
    * @return   the current value of the dateOfDocument property
    */
   public Date getDateOfDocument() 
   {
      return dateOfDocument;
   }
   
   /**
    * Sets the value of the dateOfDocument property.
    * 
    * @param aDateOfDocument the new value of the dateOfDocument property
    */
   public void setDateOfDocument(Date aDateOfDocument) 
   {
      dateOfDocument = aDateOfDocument;
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
    * Access method for the remarks property.
    * 
    * @return   the current value of the remarks property
    */
   public String getRemarks() 
   {
      return remarks;
   }
   
   /**
    * Sets the value of the remarks property.
    * 
    * @param aRemarks the new value of the remarks property
    */
   public void setRemarks(String aRemarks) 
   {
      remarks = aRemarks;
   }
   
   /**
    * Access method for the inResponseToID property.
    * 
    * @return   the current value of the inResponseToID property
    */
   public String getInResponseToID() 
   {
      return inResponseToID;
   }
   
   /**
    * Sets the value of the inResponseToID property.
    * 
    * @param aInResponseToID the new value of the inResponseToID property
    */
   public void setInResponseToID(String aInResponseToID) 
   {
      inResponseToID = aInResponseToID;
   }
   
   /**
    * Access method for the category property.
    * 
    * @return   the current value of the category property
    */
   public String getCategory() 
   {
      return category;
   }
   
   /**
    * Sets the value of the category property.
    * 
    * @param aCategory the new value of the category property
    */
   public void setCategory(String aCategory) 
   {
      category = aCategory;
   }
   
   /**
    * Access method for the documentTitle property.
    * 
    * @return   the current value of the documentTitle property
    */
   public String getDocumentTitle() 
   {
      return documentTitle;
   }
   
   /**
    * Sets the value of the documentTitle property.
    * 
    * @param aDocumentTitle the new value of the documentTitle property
    */
   public void setDocumentTitle(String aDocumentTitle) 
   {
      documentTitle = aDocumentTitle;
   }
   
   /**
    * Access method for the fileNumber property.
    * 
    * @return   the current value of the fileNumber property
    */
   public String getFileNumber() 
   {
      return fileNumber;
   }
   
   /**
    * Sets the value of the fileNumber property.
    * 
    * @param aFileNumber the new value of the fileNumber property
    */
   public void setFileNumber(String aFileNumber) 
   {
      fileNumber = aFileNumber;
   }
   
   /**
    * Access method for the documentPath property.
    * 
    * @return   the current value of the documentPath property
    */
   public String getDocumentPath() 
   {
      return documentPath;
   }
   
   /**
    * Sets the value of the documentPath property.
    * 
    * @param aDocumentPath the new value of the documentPath property
    */
   public void setDocumentPath(String aDocumentPath) 
   {
      documentPath = aDocumentPath;
   }
   
   /**
    * Access method for the documentRefNumber property.
    * 
    * @return   the current value of the documentRefNumber property
    */
   public String getDocumentRefNumber() 
   {
      return documentRefNumber;
   }
   
   /**
    * Sets the value of the documentRefNumber property.
    * 
    * @param aDocumentRefNumber the new value of the documentRefNumber property
    */
   public void setDocumentRefNumber(String aDocumentRefNumber) 
   {
      documentRefNumber = aDocumentRefNumber;
   }

	/**
    * Access method for the documentDescription property.
    * 
    * @return   the current value of the documentDescription property
    */
   public String getDocumentDescription() 
   {
      return documentDescription;
   }
   
   /**
    * Sets the value of the documentDescription property.
    * 
    * @param aDocumentDescription the new value of the documentDescription property
    */
   public void setDocumentDescription(String aDocumentDescription) 
   {
      documentDescription = aDocumentDescription;
   }

	/**
	 * Access method for the User property.
	 * 
	 * @return
	 */
	public String getUser() {
		return user;
	}
	
	/**
	 * Sets the value of the documentDescription property.
	 * 
	 * @param string
	 */
	public void setUser(String string) {
		user = string;
	}

}
