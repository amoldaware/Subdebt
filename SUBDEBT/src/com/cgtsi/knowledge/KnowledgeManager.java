package com.cgtsi.knowledge;

/*************************************************************
   *
   * Name of the class: KnowledgeManager
   * This is the main manager level class of this module. This class has methods to 
   * upload document, store documents and to search for documents.
   *
   * @author : Nithyalakshmi P
   * @version:  
   * @since: 
   **************************************************************/

import com.cgtsi.common.FileUploader;
import com.cgtsi.common.DatabaseException;
import java.util.HashMap;
import java.io.File;

public class KnowledgeManager 
{
	/**
    * This attribute will be used to access the methods provided by the KnowledgeDAO class for database interactions.
    */
	private KnowledgeDAO knowledgeDAO;
   
   /**
    * This attribute will be used to generate the Document Reference Number.
    */
   private KMHelper kmHelper;
   
   /**
    * This attribute will be used to upload the file on to a specific location on the 
    * server.
    */
   private FileUploader fileUploader;
   
   public KnowledgeManager() 
   {
	   knowledgeDAO=new KnowledgeDAO();
   }
   
   /**
    * Access method for the kmHelper property.
    * 
    * @return   the current value of the kmHelper property
    */
   public KMHelper getKmHelper() 
   {
      return kmHelper;
   }
   
   /**
    * Sets the value of the kmHelper property.
    * 
    * @param aKmHelper the new value of the kmHelper property
    */
   public void setKmHelper(KMHelper aKmHelper) 
   {
      kmHelper = aKmHelper;
   }
   
   /**
    * Access method for the fileUploader property.
    * 
    * @return   the current value of the fileUploader property
    */
   public FileUploader getFileUploader() 
   {
      return fileUploader;
   }
   
   /**
    * Sets the value of the fileUploader property.
    * 
    * @param aFileUploader the new value of the fileUploader property
    */
   public void setFileUploader(FileUploader aFileUploader) 
   {
      fileUploader = aFileUploader;
   }
   
   /**
    * This method is invoked by the InwardAndOutwardAction class to store the 
    * Document details in the database.
    * 
    * This method actually delegates the call to the corresponding method of 
    * KnowledgeDAO class. Refer to the 'storeDocument()' method of KnowledgeDAO for a 
    * full description.
    * @param document
    * @return boolean
    */
   public void storeDocument(Document document) throws DatabaseException
   {   	
   		try
	   	{
			knowledgeDAO.storeDocument(document);
	   	}
	 	catch(DatabaseException databaseException)
		{
			throw new DatabaseException(databaseException.getMessage());
		}
   }
   
   /**
    * This method is invoked by InwardAndOutwardAction class to search the database 
    * for documents satisfying the given parameters.
    * 
    * This method delegates the call to the corresponding method in KnowledgeDAO 
    * class. Refer to the 'searchForDocument()' method in KnowledgeDAO class for 
    * description.
    * @param searchCriteria
    * @return Collections 
    */
   
   public HashMap searchForDocument(SearchCriteria searchCriteria) throws DatabaseException,Exception
   {
   		try
   		{
			HashMap results=new HashMap();
			results=knowledgeDAO.searchForDocument(searchCriteria);
			return results;
   		}
		catch(DatabaseException databaseException)
		{
			throw new DatabaseException(databaseException.getMessage());
		}	
   		
		
   }
   
   /**
    * This method is invoked by InwardAndOutwardAction class to search the database 
    * for documents satisfying the given parameters.
    * 
    * This method delegates the call to the corresponding method in KnowledgeDAO 
    * class. Refer to the 'searchForDocument()' method in KnowledgeDAO class for 
    * description.
    * @param searchCriteria
    * @return Collections
    */
	public boolean UploadFiles(File file, int type)
	{
		return false;
	}

	public Document getDocumentDetails(String sDocRefNo) throws DatabaseException
	{
		try
		{
			return knowledgeDAO.getDocumentDetails(sDocRefNo);
		}
		catch(DatabaseException databaseException)
		{
			throw new DatabaseException(databaseException.getMessage());
		}
	}
	

	

}
