package com.cgtsi.knowledge;

/*************************************************************
   *
   * Name of the class: KnowledgeDAO
   * This is the main database layer class in Knowledge Management module. This 
   * class executes queries on the database.
   *
   * @author : Nithyalakshmi P
   * @version:  
   * @since: 
   **************************************************************/
 
import java.util.Date;
import java.util.HashMap;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import com.cgtsi.common.DatabaseException;
import com.cgtsi.common.Constants;
//import com.cgtsi.util.DateHelper;
import com.cgtsi.util.DBConnection;



public class KnowledgeDAO 
{
   
   public KnowledgeDAO() 
   {
    
   }
   
   /**
    * This method stores the values contained in Document class in the database.
    * 
    * The return type for the method is a String type. The String represents the 
	* Knowledge ID generated for the document uploaded.
	*
	* This method throws a Database Exception in case there is any error in the
	* sql statements or if any error occurs during the execution of the stored 
	* procedure.
    * 
    * The values from the Document object are extracted using the getter methods and 
    * passed on to the SQL query to be stored in the database.
    * @param document
    * @return boolean
    */
   public String storeDocument(Document document) throws DatabaseException
   {
	   Connection connection=DBConnection.getConnection();
	   CallableStatement storeDocStmt;
	   java.sql.Date sqlDate;
	   java.util.Date utilDate;
	   int Status;
	   String knwId=null;
	   String errCode=null;
	   //String  dateOfDocument =null;

	   try
	   {
			storeDocStmt=connection.prepareCall("{?=call funcInsertDocumentDetails(?,?,?,?,?,?,?,?,?,?,?,?)}");

			//To register the out parameter returned by the SP
			storeDocStmt.registerOutParameter(1,java.sql.Types.INTEGER);

			//Set the value for Document Description Property
			storeDocStmt.setString(2,document.getDocumentDescription());
			

			//Set the value for File Path Property
			storeDocStmt.setString(3,document.getDocumentPath());
			

			//Set the value for FileNumber Property
			storeDocStmt.setString(4,document.getFileNumber());	
			

			//Set the value for FileTitle Property
			storeDocStmt.setString(5,document.getDocumentTitle());
			

			//Set the value for Subject Property
			storeDocStmt.setString(6,document.getSubject());
			

			//Set the value for Category Property	
			storeDocStmt.setString(7,document.getCategory());//Document Category
			

			//Set the value for ReferenceId Property
			storeDocStmt.setString(8,document.getInResponseToID());//Reference Id
			

			//Set the value for Document Date Property
			utilDate = document.getDateOfDocument();
			sqlDate = new java.sql.Date (utilDate.getTime());
			storeDocStmt.setDate(9,sqlDate);	
			

			//Set the value for Remarks Property
			storeDocStmt.setString(10,document.getRemarks());//Remarks
			

			//Set the value for User Property
			String UserId = document.getUser();		
			storeDocStmt.setString(11,UserId);
			
	
			//Knowledge Id that is generated
			storeDocStmt.registerOutParameter(12,java.sql.Types.VARCHAR);
			
			//Error code if any
			storeDocStmt.registerOutParameter(13,java.sql.Types.VARCHAR);
						
			//Execute the Stored Procedure
			storeDocStmt.execute();
			Status = storeDocStmt.getInt(1);
						
			knwId=storeDocStmt.getString(12);
			
			errCode=storeDocStmt.getString(13);
			
	   }
	   catch (Exception exception)
	   {
			throw new DatabaseException(exception.getMessage());
	   }
	   finally{
			DBConnection.freeConnection(connection);
	   }

    return knwId;
   }
   
   /**
    * This method searches the database for the documents for the given parameters. 
    * 
    * The parameter passed to the method is an object of SearchCriteria class. The 
    * values are to be retrieved from the SearchCriteria object using the getter 
    * methods and passed to the SQL query.
	*
	* This method returns the Document Reference Number and the subject detail of the document.
    * @param searchCriteria
    * @return Collections
	* @throws DatabaseException
    */
   public HashMap searchForDocument(SearchCriteria searchCriteria) throws Exception
   {
		HashMap results=new HashMap();
	    Connection connection=DBConnection.getConnection();
	    CallableStatement searchDocStmt;
	    ResultSet searchResults = null;
	  	java.util.Date utilDate;
		java.sql.Date sqlDate;
	    int searchFlag;
	    String fileType=null;

	    try
	    {
			searchDocStmt=connection.prepareCall("{?=call packSearchDocuments.funcSearchDocument(?,?,?,?,?,?,?,?,?,?,?,?,?)}");		
		   
			
		   //Register the Out parameter that is returned by the Stored Procedure 
		   searchDocStmt.registerOutParameter(1,java.sql.Types.INTEGER);
		   

			//
			String fileNumber =  searchCriteria.getFileNumber();
	        if((fileNumber == null) || (fileNumber.equals("")))
			{
				//Set the value for FileNumber Property as "0" if user does not enter any
				//values
				//searchDocStmt.setNull(2,java.sql.Types.NULL); 
	            searchDocStmt.setString(2,null);
				
		    }
			else
			{
				//Set the value for FileNumber Property 
			    searchDocStmt.setString(2,fileNumber);
			   
			}
		   
		   		   
			String fileTitle = searchCriteria.getFileTitle();
           if((fileTitle == null) || (fileTitle.equals("")))
		   {
				//Set the value for FileTitle Property as "0" if user does not enter any
				//values
				//System.out.println("File Title: Null1");
				//searchDocStmt.setNull(3,java.sql.Types.NULL);
				searchDocStmt.setString(3,null);
			 	
		   }
		   else
		   {
				//Set the value for FileTitle Property
				searchDocStmt.setString(3,fileTitle);//File Title
				
		   }		


		   String subject = searchCriteria.getSubject();	
	       if((subject == null) || (subject.equals("")))
		   {
			  //Set the value for Subject Property as "0" if user does not enter any
			  //values
		      //System.out.println("File Title: Null1");
			  searchDocStmt.setString(4,null);
			  //searchDocStmt.setString(4,"0");
			  
		   }
		   else 
	       {
	       	  //Set the value for Subject Property	
			  searchDocStmt.setString(4,subject);//Subject
			  
		   }
		  
			
		   String category = searchCriteria.getCategory();	
           if((category == null) || (category.equals("")))
		   {
		   		//Set the value for Category Property as "0" if user does not enter any
		   		//values
				//searchDocStmt.setNull(5,java.sql.Types.NULL);
				searchDocStmt.setString(5,null);
				
		   }
		   else
		   {
		   	   //Set the value for Category Property	
			   searchDocStmt.setString(5,category);	//Category
			   
		   }
		  

		   utilDate=searchCriteria.getDateOfTheDocument();
		   String date = utilDate.toString();
		   if((date == null) || (date.equals("")))
		   {
		   	   //Set the value for DateOfTheDocument Property as "01-jan-4000" if user does
		   	   // not enter any values
			  	//searchDocStmt.setNull(6,java.sql.Types.NULL);  
			   searchDocStmt.setString(6,null);	//Date of Document
			  
		   }
		   else
		   {
		   	  //Set the value for DateOfTheDocument Property 
		      sqlDate = new java.sql.Date (utilDate.getTime());
			  searchDocStmt.setDate(6,sqlDate);	
			  		
		   }


			String remarks = searchCriteria.getRemarks();
			if((remarks == null) || (remarks.equals("")))
		   {
		   		//Set the value for Remarks Property as "0" if user does not enter any
		   		//values
				//searchDocStmt.setNull(7,java.sql.Types.NULL); 
				searchDocStmt.setString(7,null);
				
		   }
		   else
		   {
		   	  //Set the value for Remarks Property
		   	  searchDocStmt.setString(7,remarks);//Remarks
			  
		   }
		    		
			//System.out.println("Before Query is Executed"); 		
			searchDocStmt.registerOutParameter(8, Constants.CURSOR);
			searchDocStmt.registerOutParameter(9, Constants.CURSOR);
			searchDocStmt.registerOutParameter(10, Constants.CURSOR);
			searchDocStmt.registerOutParameter(11, Constants.CURSOR);
			searchDocStmt.registerOutParameter(12, Constants.CURSOR);
			searchDocStmt.registerOutParameter(13, Constants.CURSOR);
		    searchDocStmt.registerOutParameter(14, java.sql.Types.VARCHAR);//Error Description
			
		    
			searchDocStmt.execute();
			searchFlag=searchDocStmt.getInt(1);
			
		   	   //Move the values returned to a ResultSet 
				searchResults=(ResultSet)searchDocStmt.getObject(8);
			   	while (searchResults.next())
			   {
			   	
			   	   //Add the values returned to a HashMap
			   	   //System.out.println(searchResults.getString(1)+","+searchResults.getString(2));
				   results.put(searchResults.getString(1),searchResults.getString(2));
			   }
			   

					 
			searchResults=(ResultSet)searchDocStmt.getObject(9);
			while (searchResults.next())
		   {
			   	
			   //Add the values returned to a HashMap
			   //System.out.println(searchResults.getString(1)+","+searchResults.getString(2));
			   results.put(searchResults.getString(1),searchResults.getString(2));
		   }
		   searchResults.close();
			
			searchResults=(ResultSet)searchDocStmt.getObject(10);
			while (searchResults.next())
		   {
			   	
			   //Add the values returned to a HashMap
			   //System.out.println(searchResults.getString(1)+","+searchResults.getString(2));
			   results.put(searchResults.getString(1),searchResults.getString(2));
		   }
		   searchResults.close();
		   
			searchResults=(ResultSet)searchDocStmt.getObject(11);
			while (searchResults.next())
		   {
			   	
			   //Add the values returned to a HashMap
			   //System.out.println(searchResults.getString(1)+","+searchResults.getString(2));
			   results.put(searchResults.getString(1),searchResults.getString(2));
		   }
		   searchResults.close();
		   
			searchResults=(ResultSet)searchDocStmt.getObject(12);
			while (searchResults.next())
		   {
			   	
			   //Add the values returned to a HashMap
			   //System.out.println(searchResults.getString(1)+","+searchResults.getString(2));
			   results.put(searchResults.getString(1),searchResults.getString(2));
		   }
		   searchResults.close();
		   
			searchResults=(ResultSet)searchDocStmt.getObject(13);
			while (searchResults.next())
		   {
			   	
			   //Add the values returned to a HashMap
			   //System.out.println(searchResults.getString(1)+","+searchResults.getString(2));
			   results.put(searchResults.getString(1),searchResults.getString(2));
		   }
		   searchResults.close();

	   }
	   catch (Exception exception)
	   {
		   throw new DatabaseException(exception.getMessage());
	   }
	   finally
	   {
			DBConnection.freeConnection(connection);
	   }
	   return results;
   }

   /**
   * This method returns the document details for the given Document Reference Number.
   *
   * @param sDocRefno
   * @return Document
   * @throws DatabaseException
   */
 public  Document getDocumentDetails(String sDocRefNo) throws DatabaseException
	{
	   Connection connection=DBConnection.getConnection();
	   CallableStatement getDocDetails;
	   int docDetailsFlag=0;
	   Document documentInfo=null;
	   java.sql.Date sqlDate;

	   try
	   {
	   		getDocDetails=connection.prepareCall("{?=call funcGetDocumentDetailsForDocId(?,?,?,?,?,?,?,?,?,?)}");

			//Out Parameter - indicate whether the stored procedure was executed successfully
			getDocDetails.registerOutParameter(1,java.sql.Types.INTEGER);
			 
			//Set the value of Knowledge ID
			getDocDetails.setString(2, sDocRefNo);
			
			//Register the out Paramter returned by the SP
			getDocDetails.registerOutParameter(3,java.sql.Types.VARCHAR);
			
			//Register the out Paramter returned by the SP
			getDocDetails.registerOutParameter(4,java.sql.Types.VARCHAR);
			
			//Register the out Paramter returned by the SP	
			getDocDetails.registerOutParameter(5,java.sql.Types.VARCHAR);
			
			//Register the out Paramter returned by the SP	
			getDocDetails.registerOutParameter(6,java.sql.Types.VARCHAR);
			
			//Register the out Paramter returned by the SP	
			getDocDetails.registerOutParameter(7,java.sql.Types.VARCHAR);
			
			//Register the out Paramter returned by the SP	
			getDocDetails.registerOutParameter(8,java.sql.Types.VARCHAR);
			
			//Register the out Paramter returned by the SP	
			getDocDetails.registerOutParameter(9,java.sql.Types.DATE);
			
			//Register the out Paramter returned by the SP			
			getDocDetails.registerOutParameter(10,java.sql.Types.VARCHAR);
			
			//Register the out Paramter returned by the SP		
			getDocDetails.registerOutParameter(11,java.sql.Types.VARCHAR);
			
			getDocDetails.execute();
			int returned = getDocDetails.getInt(1);
			

			if(returned==0)
		   {
			   documentInfo = new Document();

			   //Set the value of DocumentDescription in the document object 
			   documentInfo.setDocumentDescription(getDocDetails.getString(3));
			   
				//Set the value of FileNumber in the document object
			   documentInfo.setFileNumber(getDocDetails.getString(4));
			   
				//Set the value of DocumentTitle in the document object
			   documentInfo.setDocumentTitle(getDocDetails.getString(5));
			   
				//Set the value of Subject in the document object
			   documentInfo.setSubject(getDocDetails.getString(6));
			   
				//Set the value of Category in the document object
			   documentInfo.setCategory(getDocDetails.getString(7));
			   
				//Set the value of ReferenceId in the document object
			   documentInfo.setInResponseToID(getDocDetails.getString(8));
			   
				//Set the value of Remarks in the document object
			   documentInfo.setRemarks(getDocDetails.getString(10));

				//Set the value of DateOfDocument in the document object
		    	documentInfo.setDateOfDocument(getDocDetails.getDate(9));
			
			  
			  /* System.out.println("");
			   System.out.println("");	
			   System.out.println("File Name:"+getDocDetails.getString(3)); 
			   System.out.println("File Number:"+getDocDetails.getString(4));
			   System.out.println("File Title:"+getDocDetails.getString(5));
			   System.out.println("Subject:"+getDocDetails.getString(6));
			   System.out.println("Category:"+getDocDetails.getString(7));
			   System.out.println("Reference Id:"+getDocDetails.getString(8));
			   System.out.println("Date:"+getDocDetails.getString(9));
			   System.out.println("Remarks:"+getDocDetails.getString(10));
			   System.out.println("Error:"+getDocDetails.getString(11));
			   */
   			}
	   }
	   catch (Exception exception)
	   {
		   throw new DatabaseException(exception.getMessage());
	   }
	   finally
	   {
			DBConnection.freeConnection(connection);
	   }
	    return documentInfo;
	}

}
