//Source file: D:\\com\\cgtsi\\knowledge\\SearchCriteria.java

package com.cgtsi.knowledge;

import java.util.Date;
/**
 * This class contains the search parameters. The SearchCriteria object is passed 
 * to the searchForDocument() method in KnowledgeManager class. The 
 * searchForDocument(), with the help of KnowledgeDAO class queries the database 
 * and returns the result.
 * 
 * The SearchCriteria object is formed in InwardAndOutwardAction (part of the 
 * Struts Frameworks) class. The values in this class are copied from 
 * KnowledgeMgmtActionForm (part of the Struts Frameworks).
 */
public class SearchCriteria 
{
   private String fileNumber;
   private String subject;
   private Date dateOfTheDocument;
   private String fileTitle;
   private String category;
   private String remarks;
   
   /**
    * @roseuid 387EC9E10227
    */
   public SearchCriteria() 
   {
    
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
    * Access method for the dateOfDocument property.
    * 
    * @return   the current value of the dateOfDocument property
    */
   public Date getDateOfTheDocument() 
   {
      return dateOfTheDocument;
   }
   
   /**
    * Sets the value of the dateOfDocument property.
    * 
    * @param aDateOfDocument the new value of the dateOfDocument property
    */
   public void setDateOfTheDocument(Date adateOfTheDocument) 
   {
	dateOfTheDocument = adateOfTheDocument;
   }
   
   /**
    * Access method for the fileTitle property.
    * 
    * @return   the current value of the fileTitle property
    */
   public String getFileTitle() 
   {
      return fileTitle;
   }
   
   /**
    * Sets the value of the fileTitle property.
    * 
    * @param aFileTitle the new value of the fileTitle property
    */
   public void setFileTitle(String aFileTitle) 
   {
      fileTitle = aFileTitle;
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
}
