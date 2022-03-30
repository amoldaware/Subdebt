
package com.cgtsi.inwardoutward;

   

/*************************************************************
   *
   * Name of the class : IOProcessor
   *
   * This class contains methods that control all the functionalities of Inward and 
   * Outward module.
   *                                                                                                                                                                     
   * @author : Radjesh Kumaar
   * @version:
   * @since:
   **************************************************************/

import java.util.ArrayList;
import com.cgtsi.common.DatabaseException;
import com.cgtsi.common.Log;


public class IOProcessor
{
	private IOHelper ioHelper = null;
	private IODAO ioDAO = null;


   public IOProcessor()
   {
   		ioHelper = new IOHelper();
   		ioDAO = new IODAO();
   }

   /**
	* This method is invoked when the user wants to register a new inward. It takes
	* an object of type InOut as an argument and passes this object to the addInward
	* of DbClass which updates the database of the new Inward details.
	* @param objInward - This argument gives the information about the Inward detaild
	* entered by the user.
	* @return boolean
	*
	*/
   public String addInwardDetail(Inward inward) throws DatabaseException
   {
	   Log.log(Log.INFO,"IOProcessor","addInwardDetail","Entered");
	   String ht = null;
	  	if(inward != null )
   		{
   			try
	   		{
				String outwardId = inward.getMappedOutwardID();
				
				//Append a comma to the Outward ID that is being passed 
				if((outwardId == null) || (outwardId.equals("")))
				{
					ht = ioDAO.addInward(inward);
					ioDAO = null;
				}
				else
				{
					//String  formattedOutwardId = outwardId + ",";
					inward.setMappedOutwardID(outwardId);
					ht = ioDAO.addInward(inward);
					ioDAO = null;
				}

	   		}
			catch(Exception exception) 
			{
				 throw new DatabaseException(exception.getMessage());
			}
	   	}// end of if block
	Log.log(Log.INFO,"IOProcessor","addInwardDetail","Exited");
	return ht;
}

   /**
	* This method is invoked when the user wants to view a summary report of the
	* inward status. This method then passes the request to the
	* showInwardStatusSummary of the DbClass. 
	* @param sInwardID
	* @return java.util.Collections 
	*
	*/
   public java.util.ArrayList showInwardStatusSummary() throws DatabaseException
   {
 		Log.log(Log.INFO,"IOProcessor","showInwardStatusSummary","Entered");
		ArrayList arrayFinalInward = new ArrayList();
		try
		{
			 //All the inward ids are retrieved from the database
			 ArrayList inwardIds = getAllInwardIds();
			 
			 //Get the size of Inward Ids Array
			 int inwardSize  = inwardIds.size();
			 
			 for(int i=0;i<inwardSize;i++)
				 {
				 	String inwId=(String)inwardIds.get(i);
					if(inwId==null || inwId.equals(""))
					 {
						continue;
					 }
					 
					 InwardSummary inwardSummary=new  InwardSummary();
					 inwardSummary.setInwardId(inwId);
					 
					 Inward inwardDetail = getInwardDetail(inwId);
					 					
					 if (inwardDetail != null)
					 { 
						inwardSummary.setDocumentType(inwardDetail.getDocumentType());
						inwardSummary.setProcessedBy(inwardDetail.getProcessedBy());

						// This ArrayList will contain all the outward ids for the inward id.
						ArrayList outwardsforInwardId = (ArrayList)getAllOutwardsForAnInwardId(inwId);
						inwardSummary.setMappedOutwardID(outwardsforInwardId);
								
						//The ArrayList has all the information for an Inward Id.
						// The first element is the Inward Id
						// The second element is the hashmap containing the inward details
						// The third elemtent is the ArrayList containing the outward ids for the inward id.
						//This ArrayList combines all the three and is used for displaying them
		   
						arrayFinalInward.add(inwardSummary);
						inwardSummary = null;
					}
					inwardDetail = null;
				 }// end of the for loop
			}
			catch(DatabaseException databaseException)
			{
				throw new DatabaseException(databaseException.getMessage());
		    }
		    
			Log.log(Log.INFO,"IOProcessor","showInwardStatusSummary","Exited");
	 		return arrayFinalInward;
		}


   /**
	* This method is invoked on every 1st april when the Inward Id has to be reset.
	* @return Boolean
	*
	*/
   public boolean resetInwardId()
   {
	return false;
   }

   /**
	* This method is invoked when the user wants to register a new outward. It takes
	* an object of type InOut as an argument and passes this object to the addOutward
	* of DbClass which updates the database of the new Outward details.
	* @param outward - This argument gives the information about the Outward details
	* entered by the user.
	* @return boolean
	*
	*/
   public String  addOutwardDetail(Outward outward) throws DatabaseException
   {
		Log.log(Log.INFO,"IOProcessor","addOutward","Entered");
		String outwardId = null;
		if(outward!= null )
			{
				try
				{
					String inwardId=outward.getMappedInward();
					
					if((inwardId == null) || (inwardId.equals("")))
					{
						outwardId= ioDAO.addOutward(outward);
						ioDAO = null;
					}
					else
					{
						//Append a comma to the Inward ID that is being passed 
						//String formattedInwardId=inwardId+",";
						outward.setMappedInward(inwardId);
						outwardId= ioDAO.addOutward(outward);
						ioDAO = null;
					}

				}
				catch(DatabaseException databaseException)
				{
					throw new DatabaseException(databaseException.getMessage());
				}
			}//end of if block
			Log.log(Log.INFO,"IOProcessor","addOutward","Exited");
			return outwardId;
	    }

   /**
	* This method is invoked on every 1st april when the Outward Id has to be reset.
	* @return Boolean
	*
	*/
   public boolean resetOutwardId()
   {
	return false;
   }

   /**
	* This method is invoked when the user wants to view a summary report of the
	* outward status. This method then passes the request to the
	* showOutwardStatusSummary of the DbClass.
	* @param sInwardID
	* @return java.util.Collections
	*
	*/
   public java.util.ArrayList showOutwardStatusSummary() throws DatabaseException
   {
		Log.log(Log.INFO,"IOProcessor","addOutward","Entered");
		ArrayList arrayFinalOutward= new ArrayList(); 
		try
		{
			//All the outward ids are retrieved from the database
			ArrayList outwardIds = getAllOutwardIds();
			
			//Get the size of Inward Ids Array 
			int outwardSize  = outwardIds.size();
			
			for(int i=0;i<outwardSize;i++)
			{
				String outId=(String)outwardIds.get(i);
				
				if(outId==null || outId.equals(""))
				{
					continue;
				}
				OutwardSummary outwardSummary=new OutwardSummary();
				outwardSummary.setOutwardId(outId);

				Outward outwardDetail = getOutwardDetail(outId);
				
				if(outwardDetail != null)
				{
					outwardSummary.setDocumentType(outwardDetail.getDocumentType());
					outwardSummary.setProcessedBy(outwardDetail.getProcessedBy());
	
					//This ArrayList will contain all the inward ids for the Outward id.
					ArrayList inwardsforOutwardId = (ArrayList)getAllInwardsForAnOutwardId(outId);
					outwardSummary.setMappedInwardID(inwardsforOutwardId);
					
					
					//The ArrayList has all the information for an Outward Id.
					//The first element is the Outward Id 
					//The second element is the hashmap containing the Outward details
					//The third elemtent is the ArrayList containing the Inward ids for the Outward id.
					//This ArrayList combines all the three and is used for displaying them
					arrayFinalOutward.add(outwardSummary);
					outwardSummary = null;
					
				}
				outwardDetail = null;  
			}// end of the for loop
		}
		catch(DatabaseException databaseException)
		{
	   		throw new DatabaseException(databaseException.getMessage());
		}
	Log.log(Log.INFO,"IOProcessor","addOutward","Exited");
	return arrayFinalOutward;
	}

   /**
	* This method delegates the call to IODAO which in turn returns the collection of
	* all the outward ids from the database.
	* @return java.util.Collections
	*/
   public java.util.ArrayList getAllOutwardIds() throws DatabaseException
   {
		Log.log(Log.INFO,"IOProcessor","addOutward","Entered");
		ArrayList outwardIds = new ArrayList();
		try
			{
				outwardIds = ioDAO.getAllOutwardIds();
			}
			catch(DatabaseException databaseException)
			{
				throw new DatabaseException(databaseException.getMessage());
			}
		Log.log(Log.INFO,"IOProcessor","addOutward","Exited");
		return outwardIds;
	   }

   /**
	* This method delegates the call to IODAO which in turn returns the collection of
	* all the inward ids from the database.
	* @return java.util.Collections
	*
	*/
   
   public java.util.ArrayList getAllInwardIds() throws DatabaseException
   {
		Log.log(Log.INFO,"IOProcessor","addOutward","Entered");
		ArrayList inwardIds = new ArrayList();
	   	try
   		{
			inwardIds =  ioDAO.getAllInwardIds();
   		}
   		catch(DatabaseException databaseException)
   		{
			throw new DatabaseException(databaseException.getMessage());
   		}
		Log.log(Log.INFO,"IOProcessor","addOutward","Exited");
   		return inwardIds;
   }

   /**
	* @param id
	* @param docRefNo
	* @param type - 0-Inward
	* 1-Outward
	*
	*/
   public void openLink(String id, String docRefNo, Integer type)
   {

   }

   /**
	* This method returns all the details of an inward id. Return type is a
	* collection object. it can be a hashtable or a hashmap.
	* @param sInwardId
	* @return java.util.Collections 
	*
	*/
   public Inward getInwardDetail(java.lang.String inwardId) throws DatabaseException
   {
	    Log.log(Log.INFO,"IOProcessor","addOutward","Entered");
	    Inward inwardDetail = new Inward();
   	  	try
   	  	{
			inwardDetail =  ioDAO.getInwardDetail(inwardId);
   	  	}

   	  	catch(DatabaseException databaseException)
   	  	{
   	  		throw new DatabaseException(databaseException.getMessage());
   	  	}
	Log.log(Log.INFO,"IOProcessor","addOutward","Exited");
	return inwardDetail;
  
   }
//Diksha 01/06/2017  
/*   public void afterAddInwardSorceName(String sourceName,String createdBy) throws DatabaseException
   {
   Log.log(Log.INFO,"IOProcessor","afterAddInwardSorceName","Entered");
   
   ioDAO.afterAddInwardSorceName(sourceName,createdBy);
   
   Log.log(Log.INFO,"IOProcessor","afterAddInwardSorceName","Exited");
   
   }
*/ //01/06/2017 end
   /**
	* This method returns all the details of an outward id. Return type is a
	* collection object. it can be a hashtable or a hashmap.
	* @param sOutwardId
	* @return java.util.Collections
	*
	*/
   public Outward getOutwardDetail(java.lang.String outwardId) throws DatabaseException
   {
		Log.log(Log.INFO,"IOProcessor","addOutward","Entered");
		Outward outwardDetail = new Outward();
		try
		{  
			outwardDetail = ioDAO.getOutwardDetail(outwardId);
		}
		catch(DatabaseException databaseException)
		{
			throw new DatabaseException(databaseException.getMessage());
		}
	Log.log(Log.INFO,"IOProcessor","addOutward","Exited");
	return outwardDetail;
   }

   /**
	* @param sInwardId
	* @return java.util.Collection 
	*
	*/
   public java.util.ArrayList getAllOutwardsForAnInwardId(java.lang.String inwardId) throws DatabaseException
   {
	  Log.log(Log.INFO,"IOProcessor","addOutward","Entered"); 
	  ArrayList outwardIds = new ArrayList();
   	  if(inwardId != null)
   		{
   			try
   			{
				outwardIds =  ioDAO.getAllOutwardsForAnInwardId(inwardId);
   			}
			catch(DatabaseException databaseException)
			{
				throw new DatabaseException(databaseException.getMessage());
			}
   		}
		Log.log(Log.INFO,"IOProcessor","addOutward","Exited");
		return outwardIds;
   }

   /**
	* @param sOutwardId
	* @return java.util.Collections
	*
	*/
   public java.util.ArrayList getAllInwardsForAnOutwardId(java.lang.String outwardId) throws DatabaseException
   {
		Log.log(Log.INFO,"IOProcessor","addOutward","Entered");
		ArrayList inwardIds = new ArrayList();
		try
			{
				inwardIds =  ioDAO.getAllInwardsForAnOutwardId(outwardId);
			}
			catch(DatabaseException databaseException)
			{
				throw new DatabaseException(databaseException.getMessage());
			}
       	Log.log(Log.INFO,"IOProcessor","addOutward","Exited");
		return inwardIds;
   }

   /**
	* @return java.util.Collections
	*
	*/
   public java.util.ArrayList getAllDocumentTypes() throws Exception
   {
   	
		Log.log(Log.INFO,"IOProcessor","getAllDocumentTypes","Entered");
	   	ArrayList documentTypes = new ArrayList();
	   	try
	   	{
			documentTypes = ioDAO.getAllDocumentTypes();;
	   	}
		catch(DatabaseException databaseException)
		{
			throw new DatabaseException(databaseException.getMessage());
		}
			
	Log.log(Log.INFO,"IOProcessor","getAllDocumentTypes","Exited");
	return documentTypes;
	}

   /**
	* @return com.cgtsi.inwardoutward.IOHelper
	*
	*/
   public IOHelper getIOHelper()
   {
	return this.ioHelper;
   }

   /**
	* @param objIOHelper
	*
	*/
   public void setIOHelper(IOHelper objIOHelper)  
   {
     this.ioHelper = objIOHelper;
   }
   
   public java.lang.String getFile(java.lang.String id) throws Exception
   {
		Log.log(Log.INFO,"IOProcessor","getFile","Entered");
		String inwardIds = null;
		try
			{
				inwardIds =  ioDAO.getFile(id);
			}
			catch(DatabaseException databaseException)
			{
				throw new DatabaseException(databaseException.getMessage());
			}
		Log.log(Log.INFO,"IOProcessor","getFile","Exited");
		return inwardIds;
   }
 
}
