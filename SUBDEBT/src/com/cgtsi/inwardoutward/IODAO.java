package com.cgtsi.inwardoutward;
   
import com.cgtsi.actionform.IOFormBean;
import com.cgtsi.admin.User;
import com.cgtsi.util.DateHelper;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DataTruncation;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types; 
import java.text.SimpleDateFormat;
import java.util.ArrayList; 
//import java.sql.Date;
import com.cgtsi.util.DBConnection;
import com.cgtsi.common.Constants;
import com.cgtsi.common.DatabaseException;
import com.cgtsi.common.Log;
/*import com.cgtsi.util.DateHelper;
import java.util.StringTokenizer;
import java.util.Vector;
*/



/**
 * This class contains methods that interact with the database.
 */
public class IODAO
{

   /**
    * @roseuid 3878293303B6
    */
   public IODAO()
   {

   }

   /**
    * This method is invoked when the user wants to add details for a new Inward
    * Communication.
    *
    * This method takes an object of class 'Inward' as a parameter.
    *
    * The values entered by the user are captured in InwardAndOutwardActionForm
    * class. All the values from InwardAndOutwardActionForm are copied on to Inward
    * class object.
    *
    * This method returns a boolean true or false indicating whether the values are
    * successfully stored in the database or not.
    * If there is any exception, the method might throw a false.
    * @param inward
    * @return Boolean
    * @roseuid 387C168D028C
    */
   public String addInward(Inward inward) throws Exception
   {
		Log.log(Log.INFO,"IODAO","addInward","Entered");
		CallableStatement addInwardStmt;
		CallableStatement inwardIdStmt;
	    Connection connection=DBConnection.getConnection(false);	
	  	int status = -1;
		String inwardId = null;
		String knowledgeId = null;
		java.sql.Date sqlDate = null;
		java.util.Date utilDate = null;
		String errorCode=null;
				
		try
		{
			inwardIdStmt=connection.prepareCall("{?=call  funcGenInwardId(?,?)}");
			inwardIdStmt.registerOutParameter(1,java.sql.Types.INTEGER);
			inwardIdStmt.registerOutParameter(2,java.sql.Types.VARCHAR);
			inwardIdStmt.registerOutParameter(3,java.sql.Types.VARCHAR);
			
			inwardIdStmt.execute();
			int valueReturned = inwardIdStmt.getInt(1);
		    
			if(valueReturned == Constants.FUNCTION_FAILURE)
			{
				String error = inwardIdStmt.getString(3);
				Log.log(Log.ERROR,"IODAO","addInward","SP returns a 1." +
					" Error code is :" + error);
				inwardIdStmt.close();
				inwardIdStmt = null;
			}
			else if(valueReturned == Constants.FUNCTION_SUCCESS)
			{
				// Extracting the borrower id
				String id = inwardIdStmt.getString(2);
				//System.out.println("id:"+id);
				inwardIdStmt.close();
				inwardIdStmt = null;
		    
			  	if (inward != null)//Check for Boundary Condition
			 	{
			
					   // Creates a CallableStatement object for calling database stored procedures
					   addInwardStmt=connection.prepareCall("{?=call funcInsertInward(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
					   addInwardStmt.registerOutParameter(1,java.sql.Types.INTEGER);
						
						
						//Set the current value of the sourceType property
						//String inwardIdSet = inward.getInwardId();
						addInwardStmt.setString(2,id);
						
						
					   //Set the current value of the sourceType property
					   String sourceType = inward.getSourceType();
					   addInwardStmt.setString(3,sourceType);
					
					   
						//Set the current value of the sourceId property
					   String sourceId = inward.getSourceId();
					   addInwardStmt.setString(4,sourceId);
					   
				       //Set the current value of the SourceName property
					   String sourceName = inward.getSourceName();
					   addInwardStmt.setString(5,sourceName);
					   
					   //Set the current value of the SourceRef property
					   String sourceRef = inward.getSourceRef();
					   addInwardStmt.setString(6,sourceRef);
					   
					   //Set the current value of the DocumentType property
					   String documentType = inward.getDocumentType();
					   addInwardStmt.setString(7,documentType);
					   
					   //Set the current value of the ModeOfReceipt property
					   String modeOfReceipt = inward.getModeOfReceipt();
					   addInwardStmt.setString(8 ,modeOfReceipt);
					   
						//Set the current value of the dateOfDocument property
						utilDate = inward.getDateOfDocument();
						sqlDate = new java.sql.Date (utilDate.getTime());
					   addInwardStmt.setDate(9,sqlDate);
					   
					   //Set the current value of the Language property
					   String Language = inward.getLanguage();
					   addInwardStmt.setString(10,Language); 
				   
					   //Set the current value of the Subject property
					   String Subject = inward.getSubject();
					   addInwardStmt.setString(11,Subject);
					
					   
					   //Set the current value of the FilePath property
						int index1 = id.indexOf("/");
						int index2 = id.lastIndexOf("/");
						String part1 = id.substring(0,index1);
						String part2 = id.substring(++index1,index2);
						String part3 = id.substring(++index2);
						String newInwardId = part1+part2+part3;
						
						String filePath = inward.getFilePath();
						if((filePath != null) && (!(filePath.equals(""))))
						{
							int index = filePath.lastIndexOf(".");
							String name = filePath.substring(0,index);
							String type = filePath.substring(index);
							String newFilePath = name+newInwardId+type;
							addInwardStmt.setString(12,newFilePath);
					
							
						}
						
						else
						{
							addInwardStmt.setString(12,null);
					
						}

					
					    
					   //Set the current value of the MappedOutwardID property
					   String mappedOutwardID = inward.getMappedOutwardID();
					   addInwardStmt.setString(13,mappedOutwardID);
					
					
					   //Set the current value of the Remarks property
					   String Remarks  = inward.getRemarks();
					   addInwardStmt.setString(14,Remarks);
	
					   
					   //Set the current value of the ProcessedBy property
					   String UserId = inward.getProcessedBy();		
					   addInwardStmt.setString(15,UserId);
		    
					   
						//Register the OutParameter returned by the SP
					   addInwardStmt.registerOutParameter(16,java.sql.Types.VARCHAR);
					   addInwardStmt.registerOutParameter(17,java.sql.Types.VARCHAR);
					   addInwardStmt.registerOutParameter(18,java.sql.Types.VARCHAR);
					   
						//Execute the SP
					    addInwardStmt.execute();
						status = addInwardStmt.getInt(1);
					
						
						   
						if(status == Constants.FUNCTION_FAILURE)
						{
							connection.rollback();
							 //Error Code if any 
							String errorCode1 = addInwardStmt.getString(18);
							//System.out.println("errorCode1:"+errorCode1);
							Log.log(Log.ERROR,"IODAO","addInward","SP returns a 1." +
								" Error code is :" + errorCode1);
							addInwardStmt.close();
						}
						else if(status == Constants.FUNCTION_SUCCESS)
						{
							//Get the Inward ID generated		   
						   inwardId = addInwardStmt.getString(16);
						   
							//Get the knowledge ID generated
						   knowledgeId = addInwardStmt.getString(17);
						   						   
							addInwardStmt.close();
							addInwardStmt = null;
						}
					 }
			      }
			      connection.commit();
			  }
			 catch(SQLException exception)
	 	     {
	 	     	// exception.printStackTrace();
				try
				{
					  connection.rollback();
				}
				 
				catch (SQLException ignore)
			    {
				}
	 	    	throw new DatabaseException(exception.getMessage());
	 	    }
			finally
			{
				  DBConnection.freeConnection(connection);
			}
			
		Log.log(Log.INFO,"IODAO","addInward","Exited");
	    return inwardId;		
	}

/**
   * 
   * @return inwardId
   * @throws java.lang.Exception
   */
 public int getInwardId() throws Exception
   {
		Log.log(Log.INFO,"IODAO","getInwardId","Entered");
		CallableStatement addInwardStmt;
		CallableStatement inwardIdStmt;
	  Connection connection=DBConnection.getConnection(false);	
	  int inwardId = 0;
		try
		{
			inwardIdStmt=connection.prepareCall("{?=call  funcGetInwardId(?,?)}");
			inwardIdStmt.registerOutParameter(1,java.sql.Types.INTEGER);
			inwardIdStmt.registerOutParameter(2,java.sql.Types.INTEGER);
      inwardIdStmt.registerOutParameter(3,java.sql.Types.VARCHAR);
			inwardIdStmt.execute();
			int valueReturned = inwardIdStmt.getInt(1);		    
			if(valueReturned == Constants.FUNCTION_FAILURE)
			{
				String error = inwardIdStmt.getString(3);
        System.out.println("error:"+error);
				Log.log(Log.ERROR,"IODAO","getInwardId","SP returns a 1." +
					" Error code is :" + error);
				inwardIdStmt.close();
				inwardIdStmt = null;
			}else if(valueReturned == Constants.FUNCTION_SUCCESS){
       inwardId= inwardIdStmt.getInt(2);   
     //  System.out.println("Inward Id:"+inwardId);
      }			      
			  }
			 catch(SQLException exception)
	 	     {
	 	     	// exception.printStackTrace();
				try
				{
					  connection.rollback();
				}
				 
				catch (SQLException ignore)
			    {
				}
	 	    	throw new DatabaseException(exception.getMessage());
	 	    }
			finally
			{
				  DBConnection.freeConnection(connection);
			}
			
		 Log.log(Log.INFO,"IODAO","getInwardId","Exited");
	    return inwardId;		
	}



/**
   * 
   * @param sourceName
   * @param createdBy
   * @return 
   * @throws java.lang.Exception
   */
public void afterAddInwardSorceName(String sourceName,String createdBy) throws DatabaseException
   {
		Log.log(Log.INFO,"IODAO","afterAddInwardSorceName","Entered");
		CallableStatement callableStmt;
	  Connection connection=DBConnection.getConnection(false);	
	 
		try
		{
			callableStmt=connection.prepareCall("{?=call  FuncInsInwardParty(?,?,?)}");
			callableStmt.registerOutParameter(1,java.sql.Types.INTEGER);
			callableStmt.setString(2,sourceName);
      callableStmt.setString(3,createdBy);
      callableStmt.registerOutParameter(4,Types.VARCHAR);
			callableStmt.execute();
			int valueReturned = callableStmt.getInt(1);		    
			if(valueReturned == Constants.FUNCTION_FAILURE)
			{
				String error = callableStmt.getString(4);
        System.out.println("error:"+error);
				Log.log(Log.ERROR,"IODAO","afterAddInwardSorceName","SP returns a 1." +
					" Error code is :" + error);
				callableStmt.close();
				callableStmt = null;
			} else if(valueReturned == Constants.FUNCTION_SUCCESS){
        callableStmt.close();
				callableStmt = null;
      }	
      connection.commit();
			  }
			 catch(SQLException exception)
	 	     {
	 	     	// exception.printStackTrace();
				try
				{
					  connection.rollback();
				}
				 
				catch (SQLException ignore)
			    {
				}
	 	    	throw new DatabaseException(exception.getMessage());
	 	    }
			finally
			{
				  DBConnection.freeConnection(connection);
			}
			
		 Log.log(Log.INFO,"IODAO","afterAddInwardSorceName","Exited");
	  
	}




   /**
    * This method is invoked when the user wants to add details for a new Outward
    * Communication in to the database.
    *
    * This method takes an object of class 'Outward' as a parameter.
    *
    * The values entered by the user are captured in InwardAndOutwardActionForm
    * class. All the values from InwardAndOutwardActionForm are copied on to Outward
    * class object.
    *
    * This method returns a boolean true or false indicating whether the values are
    * successfully stored in the database or not.
    * If there is any exception, the method might throw a false.
    * @param outward
    * @return Boolean
    * @roseuid 387C168D028F
    */
   public String addOutward(Outward outward) throws DatabaseException
   {
		Log.log(Log.INFO,"IODAO","addOutward","Entered");
   		CallableStatement addOutwardStmt;
		CallableStatement outwardIdStmt;
		Connection connection=DBConnection.getConnection(false);	
   		java.sql.Date sqlDate = null;
		java.util.Date utilDate = null;
		String outwardId = null;
		String knowledgeId1 = null; 
		String error=null;
		int status=0;
		
		
		try
		{
			outwardIdStmt=connection.prepareCall("{?=call  funcGenOutwardId(?,?)}");
			outwardIdStmt.registerOutParameter(1,java.sql.Types.INTEGER);
			outwardIdStmt.registerOutParameter(2,java.sql.Types.VARCHAR);
			outwardIdStmt.registerOutParameter(3,java.sql.Types.VARCHAR);
				
			outwardIdStmt.execute();
							
			int valueReturned = outwardIdStmt.getInt(1);
			String errorCode = outwardIdStmt.getString(3);
			
			if(valueReturned == Constants.FUNCTION_FAILURE)
			{
				Log.log(Log.ERROR,"IODAO","addOutward","SP returns a 1. " +
					"Error code is :"+errorCode);
				outwardIdStmt.close();
				outwardIdStmt = null;
			}
			else if(valueReturned == Constants.FUNCTION_SUCCESS)
			{
				//Get the Inward ID generated	
				String id = outwardIdStmt.getString(2);
				//Error Code if any 
				outwardIdStmt.close();	
				outwardIdStmt = null;
	
	  		if(outward != null)
	   		{
				//Creates a CallableStatement object for calling database stored procedures
				addOutwardStmt = connection.prepareCall("{? = call funcInsertOutward(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			    addOutwardStmt.registerOutParameter(1,java.sql.Types.INTEGER);
				 
				addOutwardStmt.setString(2,id);
				 
				//Set the current value of the destinationType property 
				String destinationType = outward.getDestinationType(); 
				addOutwardStmt.setString(3,destinationType);
				
				//Set the current value of the referenceId property 
				String referenceId = outward.getReferenceId(); 
				addOutwardStmt.setString(4,referenceId);
				
				//Set the current value of the destinationName property 
				String destinationName = outward.getDestinationName(); 
				addOutwardStmt.setString(5,destinationName);
				
				//Set the current value of the destinationRef property 
				String destinationRef = outward.getdestinationRef();
				addOutwardStmt.setString(6,destinationRef);
				
				//Set the current value of the documentType property 	
				String documentType= outward.getDocumentType();
				addOutwardStmt.setString(7,documentType);
				
				//Set the current value of the modeOfDelivery property 
				String modeOfDelivery = outward.getModeOfDelivery();
				addOutwardStmt.setString(8,modeOfDelivery);
				
				//Set the current value of the documentSentDate property 
				utilDate = outward.getDocumentSentDate(); 
				sqlDate = new java.sql.Date (utilDate.getTime());
				addOutwardStmt.setDate(9,sqlDate);
				
				//Set the current value of the Language property 
				String Language = outward.getLanguage();
				addOutwardStmt.setString(10,Language);
				
				//Set the current value of the Subject property 	
				String Subject = outward.getSubject();
				addOutwardStmt.setString(11,Subject);
				
				//Set the current value of the filePath property 
				int index1 = id.indexOf("/");
				int index2 = id.lastIndexOf("/");
				String part1 = id.substring(0,index1);
				String part2 = id.substring(++index1,index2);
				String part3 = id.substring(++index2);
				String newOutwardId = part1+part2+part3;
				String filePath = outward.getFilePath();
				
				if((filePath != null) && (!(filePath.equals(""))))
				{
					int index = filePath.lastIndexOf(".");
					String name = filePath.substring(0,index);
					String type = filePath.substring(index);
					String newFilePath = name+newOutwardId+type;
					addOutwardStmt.setString(12,newFilePath);
					
				}
				
				else
				{
					addOutwardStmt.setString(12,null);
				}

				
						
				//Set the current value of the Remarks property 
				String mappedInward = outward.getMappedInward();
				addOutwardStmt.setString(13,mappedInward); 
				
				//Set the current value of the Remarks property 
				String Remarks = outward.getRemarks(); 
				addOutwardStmt.setString(14,Remarks); 
				
				//Set the current value of the Remarks property 
				String userId = outward.getProcessedBy();
				addOutwardStmt.setString(15,userId);
				
				//Register the OutParameter returned by the SP					
				addOutwardStmt.registerOutParameter(16,java.sql.Types.VARCHAR);
				addOutwardStmt.registerOutParameter(17,java.sql.Types.VARCHAR);
				addOutwardStmt.registerOutParameter(18,java.sql.Types.VARCHAR);
        		
				addOutwardStmt.execute();
				status=addOutwardStmt.getInt(1);
				error= addOutwardStmt.getString(18);
				
				if(status == Constants.FUNCTION_FAILURE)
				{
					connection.commit();
					Log.log(Log.ERROR,"IODAO","addOutward","SP returns a 1. " +
						"Error code is :" + error);
					addOutwardStmt.close();
					addOutwardStmt = null;
				}
				else if(status == Constants.FUNCTION_SUCCESS)
				{
				
					//Get the Outward ID generated
					outwardId = addOutwardStmt.getString(16);
					
					//Get the knowledge ID generated
					knowledgeId1 = addOutwardStmt.getString(17);
					
					//Error Code if Any
					addOutwardStmt.close();
					addOutwardStmt = null;
					}
				}
			}
		connection.commit();
		}
	   catch(SQLException exception)
	   {
		  try
		  {
				connection.rollback();
		  }
				 
		  catch (SQLException ignore)
		  {
		  }
		  throw new DatabaseException(exception.getMessage());
	  }
		finally
		{
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO,"IODAO","addOutward","Exited");
		return outwardId;
	}


public void insertWorkshopDetails(String workshopDt, String bankName,
                                  String agencyName, String targetGroup,
                                  String place, String stateName,
                                  String districtName, String city,
                                  String type, String topic,
                                  int participants, String organisation,
                                  String name, String designation,
                                  String reasons, String loggedUserId,String organisedfor,
                                  String nonMliName,String mliNames,String zone,String mliId,String governmentOrgs) 
                        throws DatabaseException
	{
		Log.log(Log.INFO,"IODAO","insertWorkshopDetails","Entered");
		ResultSet danRaisedResult;
		Connection connection = DBConnection.getConnection();
		CallableStatement workshopDetailsStmt = null;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
    int count=0;
    PreparedStatement pStmt = null;  
    
   
//     try
//            { 
//            Statement stmt=connection.createStatement();
//		String query = "INSERT INTO WORKSHOP_DETAILS (WORKSHOP_ID,WORKSHOP_DT,BANK_NAME,AGENCY_NAME,PLACE,STATE, "+
//			             "  DISTRICT,CITY,TYPE_OF_WORKSHOP,TOPIC,PARTICIPANTS,TARGET_GROUP,	ORGANISATION,OFFICER_NAME,DESIGNATION,REMARKS,CREATED_BY,CREATED_DT  ) "+
//      "  VALUES (WORKSHOP_DETAILS_SEQ.NEXTVAL,'" + dateFormat.format(java.sql.Date.valueOf(DateHelper.stringToSQLdate(workshopDt))) + "', '" + 
//                     bankName + "', '" +agencyName+ "' ,'" +place + "' ,'" + stateName + 
//                     "' ,'" + districtName + "' ,'" + city + "' ,'" + type + "' ,'" + 
//                     topic+ "' ," + participants + ",'" + targetGroup + "','" + 
//                     organisation + "' ,'" + name +"','"+designation +"','"+reasons +"','"+loggedUserId +"',SYSDATE)";
//         System.out.println("query:"+query);
//     count = stmt.executeUpdate(query); 
//     pStmt.close();
//		  if(count==Constants.FUNCTION_FAILURE){	
//			connection.rollback();	
//			throw new DatabaseException("UNABLE TO INSERT WORKSHOP DETAILS");	
//		 }
//		connection.commit();
//     }
//		  catch(Exception exception)
//            {
//              Log.logException(exception);
//              System.out.println("Exception Message:"+exception.getMessage());
//              throw new DatabaseException(exception.getMessage());
//            }
//            finally
//            {
//              DBConnection.freeConnection(connection);
//            }
    
		
		try
		{
			workshopDetailsStmt = connection.prepareCall("{? = call Funcinsertworkshopdetails(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");				
			workshopDetailsStmt.registerOutParameter(1,Types.INTEGER);
			workshopDetailsStmt.setDate(2,java.sql.Date.valueOf(DateHelper.stringToSQLdate(workshopDt)));
      //System.out.println("workshopDt:"+java.sql.Date.valueOf(DateHelper.stringToSQLdate(workshopDt)));
			workshopDetailsStmt.setString(3,bankName);
     // System.out.println("bankName:"+bankName);
      workshopDetailsStmt.setString(4,agencyName);
      //System.out.println("agencyName:"+agencyName);
      workshopDetailsStmt.setString(5,targetGroup);
      //System.out.println("targetGroup:"+targetGroup);
      workshopDetailsStmt.setString(6,place);
     // System.out.println("place:"+place);
      workshopDetailsStmt.setString(7,stateName);
      //System.out.println("stateName:"+stateName);
      workshopDetailsStmt.setString(8,districtName);
     // System.out.println("districtName:"+districtName);
      workshopDetailsStmt.setString(9,city);
      //System.out.println("city:"+city);
      workshopDetailsStmt.setString(10,type);
      //System.out.println("type:"+type);
      workshopDetailsStmt.setString(11,topic);
      //System.out.println("topic:"+topic);
      workshopDetailsStmt.setInt(12,participants);
      //System.out.println("participants:"+participants);
      workshopDetailsStmt.setString(13,organisation);
      //System.out.println("organisation:"+organisation);
      workshopDetailsStmt.setString(14,name);
      //System.out.println("name:"+name);
      workshopDetailsStmt.setString(15,designation);
      //System.out.println("designation:"+designation);
      workshopDetailsStmt.setString(16,reasons); 
      //System.out.println("reasons:"+reasons);
      workshopDetailsStmt.setString(17,loggedUserId); 	
      //System.out.println("loggedUserId:"+loggedUserId);
      workshopDetailsStmt.setString(18,organisedfor); 
      workshopDetailsStmt.setString(19,nonMliName);
      workshopDetailsStmt.setString(20,mliNames);     
      
      workshopDetailsStmt.setString(21,zone);   
      workshopDetailsStmt.setString(22,mliId);   
      workshopDetailsStmt.setString(23,governmentOrgs);   
       
      workshopDetailsStmt.registerOutParameter(24,java.sql.Types.VARCHAR);
      workshopDetailsStmt.executeQuery();
     // System.out.println("workshopDetailsStmt Executed");
			int status = workshopDetailsStmt.getInt(1);  
			//System.out.println("status:"+status); 
			String errorCode=workshopDetailsStmt.getString(24);
			//System.out.println("errorCode:"+errorCode); 

			if(status == Constants.FUNCTION_FAILURE)
			{
				Log.log(Log.ERROR,"IODAO","insertWorkshopDetails","SP returns a 1." +
										" Error code is :" + errorCode);
        System.out.println("errorCode:"+errorCode); 
				workshopDetailsStmt.close();
				workshopDetailsStmt = null;
				throw new DatabaseException(errorCode); 
			} else 
       {
       workshopDetailsStmt.close();
			  workshopDetailsStmt = null;
       }

		}
		catch(Exception exception)
		{
			Log.logException(exception);
			throw new DatabaseException(exception.getMessage());
		}
		finally
		{
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO,"IODAO","insertWorkshopDetails","Exited");
	}





   /**
    * This method returns the collection of all the Outward Ids from the database.
    *
    * The return type is a java.util.Collections object. It can be a ArrayList or an
    * ArrayList.
    * @return java.util.Collections
    * @roseuid 387C168D0292
    */
   public java.util.ArrayList getAllOutwardIds() throws DatabaseException
   {
		Log.log(Log.INFO,"IODAO","getAllOutwardIds","Entered");
   		CallableStatement outwardIdsStmt;
   		ResultSet resultSetOutwardIds = null;
   		ArrayList ArrayListOfOutwardIds = new ArrayList();
		String errorCode=null;
		Connection connection=DBConnection.getConnection();

   		try
   		{
   			//Call the Stored Procedure to get all Outward Ids from the database
   			outwardIdsStmt = connection.prepareCall("{? = call packGetAllOutwardIds.funcGetAllOutwardIds(?,?)}");

			//Registers the out parameter returned by the Stored Procedure to the JDBC type
   			outwardIdsStmt.registerOutParameter(1, Types.INTEGER);
			outwardIdsStmt.registerOutParameter(2, Constants.CURSOR);
			outwardIdsStmt.registerOutParameter(3,java.sql.Types.VARCHAR);

			//Execute the Stored Procedure
			outwardIdsStmt.execute();
			int status =  outwardIdsStmt.getInt(1);
			errorCode= outwardIdsStmt.getString(3);
			
			if(status == Constants.FUNCTION_FAILURE)
			{
				Log.log(Log.ERROR,"IODAO","getAllOutwardIds","SP returns a 1." +
					" Error code is :" + errorCode);
				outwardIdsStmt.close();
				outwardIdsStmt = null;
			}
			else if(status == Constants.FUNCTION_SUCCESS)
			{
				//The value returned by the Stored Procedure is stored as a ResultSet
	   			resultSetOutwardIds = (ResultSet)outwardIdsStmt.getObject(2);
							
				while(resultSetOutwardIds.next())
	   			{
	   				ArrayListOfOutwardIds.add(resultSetOutwardIds.getObject(1));
				}
				resultSetOutwardIds.close();
				resultSetOutwardIds = null;
				outwardIdsStmt.close();
				outwardIdsStmt = null;
	   		}
   		}
   		catch(Exception exception)
   		{
   			throw new DatabaseException(exception.getMessage());
   		}
		finally
		{
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO,"IODAO","getAllOutwardIds","Exited");
   		return ArrayListOfOutwardIds;
   }

   /**
    * This method returns a collection of all the Inward IDs from the database.
    *
    * The return type is a Collections object. It can be a ArrayList or an ArrayList or
    * a plain array.
    * @return java.util.Collections
    * @roseuid 387C168D0293
    */
   public java.util.ArrayList getAllInwardIds() throws DatabaseException
   {
		Log.log(Log.INFO,"IODAO","getAllInwardIds","Entered");
   		CallableStatement inwardIdsStmt;
   		ResultSet resultSetInwardIds = null;
		ArrayList ArrayListOfInwardIds = new ArrayList();
		String errorCode=null;
		Connection connection=DBConnection.getConnection();
		try {
				//Call the Stored Procedure to get all Inward Ids from the database
				inwardIdsStmt = connection.prepareCall("{? = call packGetAllInwardIds.funcGetAllInwardIds(?,?)}");
				
				//Registers the out parameter returned by the Stored Procedure to the JDBC type
				inwardIdsStmt.registerOutParameter(1,Types.INTEGER);
			    inwardIdsStmt.registerOutParameter(2,Constants.CURSOR);
				inwardIdsStmt.registerOutParameter(3,java.sql.Types.VARCHAR);

				//Execute the Stored Procedure
				inwardIdsStmt.execute();
				int status = inwardIdsStmt.getInt(1);
				errorCode= inwardIdsStmt.getString(3);
			
				if(status == Constants.FUNCTION_FAILURE)
				{
					Log.log(Log.ERROR,"IODAO","getAllInwardIds","SP returns a 1." +
						" Error code is :" + errorCode);
					inwardIdsStmt.close();
					inwardIdsStmt = null;
				}
				else if(status == Constants.FUNCTION_SUCCESS)
				{
					//The value returned by the Stored Procedure is stored as a ResultSet
				resultSetInwardIds = (ResultSet)inwardIdsStmt.getObject(2);
				
				
				while(resultSetInwardIds.next())
				{
					ArrayListOfInwardIds.add((String)resultSetInwardIds.getObject(1));
				}
				
				inwardIdsStmt.close();	
				inwardIdsStmt = null;
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
		Log.log(Log.INFO,"IODAO","getAllInwardIds","Exited");
   		return ArrayListOfInwardIds;
   }


   /**
    * This method returns all the details of an inward id. Return type is a
    * collection object. it can be a hashtable or a hashmap.
    * @param sInwardId
    * @return java.util.Collections
    * @roseuid 387EF2A6024C
    */
   public Inward getInwardDetail(java.lang.String sInwardId)throws DatabaseException
   {
		Log.log(Log.INFO,"IODAO","getInwardDetail","Entered");
   		CallableStatement inwardDetailStmt;
  		Connection connection=DBConnection.getConnection();
   		Inward inward =null;
   		
 		 try
   		{
   			inwardDetailStmt = connection.prepareCall("{? = call funcGetInwardDetailsForInward(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			inwardDetailStmt.registerOutParameter(1,java.sql.Types.INTEGER);
			//Set the Inward Id that is passed to the SP
			inwardDetailStmt.setString(2,sInwardId);
			//Register the Out Parameters returned by the SP
			inwardDetailStmt.registerOutParameter(3,java.sql.Types.VARCHAR);
			inwardDetailStmt.registerOutParameter(4,java.sql.Types.VARCHAR);
			inwardDetailStmt.registerOutParameter(5,java.sql.Types.VARCHAR);
			inwardDetailStmt.registerOutParameter(6,java.sql.Types.VARCHAR);
			inwardDetailStmt.registerOutParameter(7,java.sql.Types.VARCHAR);
			inwardDetailStmt.registerOutParameter(8,java.sql.Types.VARCHAR);
			inwardDetailStmt.registerOutParameter(9,java.sql.Types.DATE);
			inwardDetailStmt.registerOutParameter(10,java.sql.Types.VARCHAR);
			inwardDetailStmt.registerOutParameter(11,java.sql.Types.VARCHAR);
			inwardDetailStmt.registerOutParameter(12,java.sql.Types.VARCHAR);
			inwardDetailStmt.registerOutParameter(13,java.sql.Types.VARCHAR);
			inwardDetailStmt.registerOutParameter(14,java.sql.Types.VARCHAR);
			inwardDetailStmt.registerOutParameter(15,java.sql.Types.VARCHAR);
						
			inwardDetailStmt.execute(); 
			int status = inwardDetailStmt.getInt(1);
			String error = inwardDetailStmt.getString(15);
			
			if(status == Constants.FUNCTION_FAILURE)
			{
				Log.log(Log.ERROR,"IODAO","getInwardDetail","SP returns a 1." +
					" Error code is :" + error);
				inwardDetailStmt.close();
				inwardDetailStmt = null;
			}
			else if(status == Constants.FUNCTION_SUCCESS)
			{
				//Create a inward object and set the values of different paramter in it
				inward=new Inward();
				inward.setSourceType(inwardDetailStmt.getString(3));
				inward.setSourceId(inwardDetailStmt.getString(4));
                inward.setSourceName(inwardDetailStmt.getString(5));
				inward.setSourceRef(inwardDetailStmt.getString(6));
				inward.setDocumentType(inwardDetailStmt.getString(7));
				inward.setModeOfReceipt(inwardDetailStmt.getString(8));
				inward.setDateOfDocument(inwardDetailStmt.getDate(9));
				inward.setLanguage(inwardDetailStmt.getString(10));
				inward.setSubject(inwardDetailStmt.getString(11));
				inward.setRemarks(inwardDetailStmt.getString(12));
				inward.setInwardId(inwardDetailStmt.getString(13));
				inward.setProcessedBy(inwardDetailStmt.getString(14));

/*
				String stringDate = inwardDetailStmt.getString(9);
				if(stringDate != null)
				{	
				
					StringTokenizer tokenizer = new StringTokenizer(stringDate);
					Vector v = new Vector();
					while(tokenizer.hasMoreTokens())
					{
						v.addElement(tokenizer.nextToken());
						System.out.println("--------"+tokenizer.nextToken()+"--------");
					}
					String first = (String)(v.firstElement()); 
					System.out.println("+++++++++"+first+"++++++++++");
					inward.setDateOfDocument(first);
				}
				else
				{
*/				
				//}
					
					inwardDetailStmt.close(); 
					inwardDetailStmt = null;
				}
	 		 } 
			 catch (Exception exception)
			 {
				throw new DatabaseException(exception.getMessage());
			 }
			 finally{
				DBConnection.freeConnection(connection);
			}
		  Log.log(Log.INFO,"IODAO","getInwardDetail","Exited");
	   	 return  inward;
	   }
 
/**
    * This method returns all the details of an Outward id. Return type is a
    * collection object. it can be a hashtable or a hashmap.
    * @param sOutwardId
    * @return java.util.Collections
    * @roseuid 387EF2A6026A
    */
   public Outward getOutwardDetail(java.lang.String sOutwardId) throws DatabaseException
   {
			Log.log(Log.INFO,"IODAO","getOutwardDetail","Entered");
			CallableStatement outwardDetailStmt;
			Connection connection=DBConnection.getConnection();
			Outward outward = null; 

			try 
			{
				
				outwardDetailStmt = connection.prepareCall("{ ? = call funcGetOutwardDetForOutward(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				outwardDetailStmt.registerOutParameter(1,java.sql.Types.INTEGER);
				//Set the Outward Id that is passed to the SP
				outwardDetailStmt.setString(2,sOutwardId);
				//Register the Out Parameters returned by the SP
				outwardDetailStmt.registerOutParameter(3,java.sql.Types.VARCHAR);
				outwardDetailStmt.registerOutParameter(4,java.sql.Types.VARCHAR);
				outwardDetailStmt.registerOutParameter(5,java.sql.Types.VARCHAR);
				outwardDetailStmt.registerOutParameter(6,java.sql.Types.VARCHAR);
				outwardDetailStmt.registerOutParameter(7,java.sql.Types.VARCHAR);
				outwardDetailStmt.registerOutParameter(8,java.sql.Types.DATE);
				outwardDetailStmt.registerOutParameter(9,java.sql.Types.VARCHAR);
				outwardDetailStmt.registerOutParameter(10,java.sql.Types.VARCHAR);
				outwardDetailStmt.registerOutParameter(11,java.sql.Types.VARCHAR);
				outwardDetailStmt.registerOutParameter(12,java.sql.Types.VARCHAR);
				outwardDetailStmt.registerOutParameter(13,java.sql.Types.VARCHAR);
				outwardDetailStmt.registerOutParameter(14,java.sql.Types.VARCHAR);
								
				outwardDetailStmt.execute();
				int status = outwardDetailStmt.getInt(1);
				String error = outwardDetailStmt.getString(14);
				
				if(status == Constants.FUNCTION_FAILURE)
				{
					Log.log(Log.ERROR,"IODAO","getOutwardDetail","SP returns a 1." +
						" Error code is :" + error);
					outwardDetailStmt.close();
					outwardDetailStmt = null;
				}
				else if(status == Constants.FUNCTION_SUCCESS)
				{
					//Create a outward object and set the values of different paramters in it			
					outward = new Outward();
					outward.setDestinationType(outwardDetailStmt.getString(3));
					outward.setDestinationName(outwardDetailStmt.getString(4));
					outward.setDocumentType(outwardDetailStmt.getString(5));
					outward.setModeOfDelivery(outwardDetailStmt.getString(6));
					outward.setdestinationRef(outwardDetailStmt.getString(7));
					outward.setDocumentSentDate(outwardDetailStmt.getDate(8));
					outward.setLanguage(outwardDetailStmt.getString(9));
					outward.setSubject(outwardDetailStmt.getString(10));
					outward.setRemarks(outwardDetailStmt.getString(11));
					outward.setOutwardId(outwardDetailStmt.getString(12));
					outward.setProcessedBy(outwardDetailStmt.getString(13));
					
/*					String stringDate = outwardDetailStmt.getString(8);
					if(stringDate!= null)
					{
						StringTokenizer tokenizer = new StringTokenizer(stringDate);
						Vector v = new Vector();
						while(tokenizer.hasMoreTokens())
						{
								v.addElement(tokenizer.nextToken());
								System.out.println("--------"+tokenizer.nextToken()+"--------");
						}
						String first = (String)(v.firstElement());
						System.out.println("+++++++++"+first+"++++++++++");
						outward.setDocumentSentDate(first);
					 }
					 
					 else
					 {
*/					 	
						
	//				 }
					outwardDetailStmt.close();
					outwardDetailStmt = null;
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
		 Log.log(Log.INFO,"IODAO","getOutwardDetail","Exited");
		 return outward;
   }

   /**
    * This method retrieves all the Outward Communications in reference to which this
    * Inward Communication has been sent.
    *
    * The return type is a Collections object. It can be a Hashtable or a HashMap.
    * @param sInwardId
    * @return java.util.Collection
    * @roseuid 386EE4DD03AF
    */
   public java.util.ArrayList getAllOutwardsForAnInwardId(java.lang.String inwardId) throws DatabaseException
   {
		Log.log(Log.INFO,"IODAO","getAllOutwardsForAnInwardId","Entered");
		CallableStatement outwardIdsStmt;
		ResultSet resultSetOutwardIds = null; 
		ArrayList ArrayListOfOutwardIds = new ArrayList();
		String errorCode=null;
		Connection connection=DBConnection.getConnection();

		try
		{
			//Call the Stored Procedure to get all Outward Ids for an Inward Id from the database
			outwardIdsStmt = connection.prepareCall("{? = call  packGetOutwardsForInward.funcGetOutwardsForInward(?,?,?)}");
			//Registers the out parameter returned by the Stored Procedure to the JDBC type

			outwardIdsStmt.registerOutParameter(1, Types.INTEGER);
			outwardIdsStmt.setString(2,inwardId);
			outwardIdsStmt.registerOutParameter(3,Constants.CURSOR);
			outwardIdsStmt.registerOutParameter(4,java.sql.Types.VARCHAR);

			//Execute the Stored Procedure
			outwardIdsStmt.execute();
			int status = outwardIdsStmt.getInt(1);
			errorCode=outwardIdsStmt.getString(4);
			
			if(status == Constants.FUNCTION_FAILURE)
			{
				Log.log(Log.ERROR,"IODAO","getAllOutwardsForAnInwardId","SP returns a 1." +
										" Error code is :" + errorCode);
				outwardIdsStmt.close();
				outwardIdsStmt = null;
			}
			else if(status == Constants.FUNCTION_SUCCESS)
			{
				//The value returned by the Stored Procedure is stored as a ResultSet
				resultSetOutwardIds = (ResultSet)outwardIdsStmt.getObject(3);
		
				while(resultSetOutwardIds.next())
				{
					ArrayListOfOutwardIds.add(resultSetOutwardIds.getObject(1));
				}
				resultSetOutwardIds.close();
				resultSetOutwardIds = null;
				outwardIdsStmt.close();
				outwardIdsStmt = null;
			}
		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		finally
		{
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO,"IODAO","getAllOutwardsForAnInwardId","Exited");
		return ArrayListOfOutwardIds;
   }

   /**
    * This method retrieves all the Inward Communications in reference to which this
    * Outward Communication has been sent.
    *
    * The return type is a Collections object. It can a Hashtable or a HashMap.
    * @param sOutwardId
    * @return java.util.Collections
    * @roseuid 386EE4DD03C3
    */
   public java.util.ArrayList getAllInwardsForAnOutwardId(java.lang.String sOutwardId) throws DatabaseException
   {
		Log.log(Log.INFO,"IODAO","getAllInwardsForAnOutwardId","Entered");
		CallableStatement inwardIdsStmt;
		String errorCode=null;
		ResultSet resultSetInwardIds = null;
		ArrayList ArrayListOfInwardIds = new ArrayList(); 
		Connection connection=DBConnection.getConnection();

		try
		{
			//Call the Stored Procedure to get all Inward Ids for an Outward Id  from the database
			inwardIdsStmt = connection.prepareCall("{? = call packGetInwardsForOutward.funcGetInwardsForOutward(?,?,?)}");
			
			//Registers the out parameter returned by the Stored Procedure to the JDBC type
			inwardIdsStmt.registerOutParameter(1,Types.INTEGER);
			inwardIdsStmt.setString(2,sOutwardId);
			inwardIdsStmt.registerOutParameter(3,Constants.CURSOR);
			inwardIdsStmt.registerOutParameter(4,java.sql.Types.VARCHAR);
			
			//Execute the Stored Procedure
			inwardIdsStmt.execute();
			int status = inwardIdsStmt.getInt(1);
			errorCode=inwardIdsStmt.getString(4);
			
			if(status == Constants.FUNCTION_FAILURE)
			{
				Log.log(Log.ERROR,"IODAO","getAllInwardsForAnOutwardId","SP returns a 1. " +
					"Error code is :" + errorCode);
				inwardIdsStmt.close();
				inwardIdsStmt = null;
			}
			else if(status == Constants.FUNCTION_SUCCESS)
			{
					resultSetInwardIds = (ResultSet)inwardIdsStmt.getObject(3);
	
				while(resultSetInwardIds.next())
				{
					ArrayListOfInwardIds.add(resultSetInwardIds.getObject(1));
				}
				resultSetInwardIds.close();
				resultSetInwardIds = null;
				inwardIdsStmt.close();
				inwardIdsStmt = null;
				
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
	Log.log(Log.INFO,"IODAO","getAllInwardsForAnOutwardId","Exited");
	return ArrayListOfInwardIds;
   }

   /**
    * This method gets all the document types from the Document_Type table.
    * @return java.util.Collections
    * @roseuid 386EE588036F
    */
   public java.util.ArrayList getAllDocumentTypes() throws DatabaseException
   {
	   Log.log(Log.INFO,"IODAO","getAllDocumentTypes","Entered");
	   ArrayList documentTypes = new ArrayList();
	   Connection connection=DBConnection.getConnection();
	   CallableStatement statement = null;

	   int status = -1;
	   ResultSet docTypesResultSet = null;
	   String error = null;
	   
	   try
	   {
			statement = connection.prepareCall("{? = call packGetAllDocumentType.funcGetDocType(?,?)}");
			//Register the Out Parameters returned by the SP
			statement.registerOutParameter(1,java.sql.Types.INTEGER);
			statement.registerOutParameter(2, Constants.CURSOR);
			statement.registerOutParameter(3,java.sql.Types.VARCHAR);
	
			 //Execute the Query
			statement.execute();
			status = statement.getInt(1);
			error = statement.getString(3);
			if(status == Constants.FUNCTION_FAILURE)
			{
				Log.log(Log.ERROR,"IODAO","getAllDocumentTypes","SP returns a 1." +
					" Error code is :" + error);
				statement.close();
				statement = null;
			}
			else if(status == Constants.FUNCTION_SUCCESS)
			{
				docTypesResultSet = (ResultSet)statement.getObject(2);
						
				while(docTypesResultSet.next())
				{
					documentTypes.add(docTypesResultSet.getObject(1));
				}
				docTypesResultSet.close();
				docTypesResultSet = null;
				statement.close();
				statement = null;
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
		Log.log(Log.INFO,"IODAO","getAllDocumentTypes","Exited"); 
	    return documentTypes;
	   }
	   
	 /**
   * 
   * @param ioForm
   * @throws com.cgtsi.common.DatabaseException
   */
   public void afterUpdateInwardDetails(String inwardId,String oldInstrumentNo,String instrumentNo,
                                        String bankNames,String drawnonBank,String places,
                                        String subjects,String referenceIds,String ltrDt,
                                        String instrumentDt,int instrumentAmt,String section,String userId,
                                        String outwardId,String outwardDt,
                                        String assignedTo,String reasons,String inwardDt) throws DatabaseException
	{
		String methodName = "afterUpdateInwardDetails" ;

		Log.log(Log.INFO, "IODAO", methodName, "Entered") ;

		boolean newConn = false;
   Connection connection=DBConnection.getConnection(false);
  
		if (connection == null)
		{
			connection=DBConnection.getConnection(false);
			newConn=true;
		}
		try
		{
			CallableStatement callable=null;
   		int errorCode;
			String error;
			
  		callable=connection.prepareCall("{?=call funcUpdateInward (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			callable.registerOutParameter(1,Types.INTEGER);
			callable.setString(2,inwardId);
      if(oldInstrumentNo==null||oldInstrumentNo.equals("")){
       callable.setNull(3,Types.VARCHAR); 
      } else {
        callable.setString(3,oldInstrumentNo); 
      }
      
      callable.setString(4,bankNames); 
      callable.setString(5,drawnonBank); 
      callable.setString(6,places); 
      callable.setString(7,subjects); 
      callable.setString(8,referenceIds); 
      callable.setDate(9,java.sql.Date.valueOf(DateHelper.stringToSQLdate(ltrDt)));
			callable.setString(10,instrumentNo);
      if(instrumentDt==null||instrumentDt.equals("")){
       callable.setNull(11,Types.DATE);
      } else {
           callable.setDate(11,java.sql.Date.valueOf(DateHelper.stringToSQLdate(instrumentDt)));
      }
      callable.setInt(12,instrumentAmt);
      callable.setString(13,section);
      callable.setString(14,userId);
      if(outwardId==null || outwardId.equals("")){
    //   callable.setNull(15,java.sql.Types.INTEGER);
       callable.setNull(15,Types.VARCHAR);
      } else {
        // callable.setInt(15,Integer.parseInt(outwardId));
        callable.setString(15,outwardId);
      }
      if(outwardDt==null||outwardDt.equals("")){
        callable.setNull(16,java.sql.Types.DATE);
      } else {
          callable.setDate(16,java.sql.Date.valueOf(DateHelper.stringToSQLdate(outwardDt)));
      }
    
   //   System.out.println("userId:"+userId);
   
     if(assignedTo==null || assignedTo.equals("")){
       callable.setNull(17,Types.VARCHAR);
      } else {
        // callable.setInt(15,Integer.parseInt(outwardId));
        callable.setString(17,assignedTo);
      }
   
   if(reasons==null || reasons.equals("")){
       callable.setNull(18,Types.VARCHAR);
      } else {
        // callable.setInt(15,Integer.parseInt(outwardId));
        callable.setString(18,reasons);
      }
   
   if(inwardDt==null || inwardDt.equals("")){
       callable.setNull(19,Types.DATE);
      } else {
        // callable.setInt(15,Integer.parseInt(outwardId));
        callable.setDate(19,java.sql.Date.valueOf(DateHelper.stringToSQLdate(inwardDt)));
      }
     
			callable.registerOutParameter(20,Types.VARCHAR);

			callable.execute();

			errorCode=callable.getInt(1);

			error=callable.getString(20);

			Log.log(Log.DEBUG, "IODAO", methodName, "error code and error"+errorCode+","+error) ;
      System.out.println("error code and error"+errorCode+","+error);

			if(errorCode==Constants.FUNCTION_FAILURE)
			{
				Log.log(Log.ERROR, "IODAO", methodName, error) ;

				callable.close();
				callable=null;
				connection.rollback();

				throw new DatabaseException(error);
			}

			callable.close();
			callable=null;
			connection.commit();
		
		}
		catch(SQLException e)
		{
			Log.log(Log.ERROR, "IODAO", methodName, e.getMessage()) ;

			Log.logException(e);

			if (newConn)
			{
				try
				{
					connection.rollback();
				}
				catch (SQLException ignore)
				{
				}
			}

			throw new DatabaseException("Unable to update Inward Details.");

		}
		finally
		{
			if (newConn)
			{
				DBConnection.freeConnection(connection);
			}
		}
			Log.log(Log.INFO, "IODAO", methodName, "Exited") ;
	}

   
   
   /**
   * 
   * @param ioFormBean
   * @param userId
   * @throws com.cgtsi.common.DatabaseException
   */
   public void afterUpdateSchemePropagationDtl(IOFormBean ioFormBean,String userId) throws DatabaseException
	{
		String methodName = "afterUpdateSchemePropagationDtl" ;

		Log.log(Log.INFO, "IODAO", methodName, "Entered") ;

		boolean newConn = false;
   Connection connection=DBConnection.getConnection(false);
  
		if (connection == null)
		{
			connection=DBConnection.getConnection(false);
			newConn=true;
		}
		try
		{
			CallableStatement callable=null;
   		int errorCode;
			String error;
			
  		callable=connection.prepareCall("{?=call funcUpdateSchemePropagationDtl (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			callable.registerOutParameter(1,Types.INTEGER);
			String workshopId = ioFormBean.getWorkshopId();
      if(workshopId==null||workshopId.equals("")){
       callable.setNull(2,Types.VARCHAR); 
      } else {
        callable.setString(2,workshopId); 
      }
      String workshopDt = ioFormBean.getWorkshopDt();
      if(workshopDt==null||workshopDt.equals("")){
       callable.setNull(3,Types.DATE);
      } else {
           callable.setDate(3,java.sql.Date.valueOf(DateHelper.stringToSQLdate(workshopDt)));
      }
      callable.setString(4,ioFormBean.getType()); 
      callable.setString(5,ioFormBean.getMliName());
      callable.setString(6,ioFormBean.getAgencyName());
      callable.setString(7,ioFormBean.getOrganisedfor());
      callable.setString(8,ioFormBean.getBankNames());
      callable.setString(9,ioFormBean.getSourceName());
      callable.setString(10,ioFormBean.getTargetGroup());
      callable.setString(11,ioFormBean.getStateName());
      callable.setString(12,ioFormBean.getDistrictName());
      callable.setString(13,ioFormBean.getCity());
      callable.setString(14,ioFormBean.getTopic());
      callable.setInt(15,ioFormBean.getParticipants());
      callable.setString(16,ioFormBean.getName());
      callable.setString(17,ioFormBean.getOrganisation());
      callable.setString(18,ioFormBean.getReasons()); 
      callable.setString(19,userId); 
     
			callable.registerOutParameter(20,Types.VARCHAR);

			callable.execute();

			errorCode=callable.getInt(1);

			error=callable.getString(20);

			Log.log(Log.DEBUG, "IODAO", methodName, "error code and error"+errorCode+","+error) ;

			if(errorCode==Constants.FUNCTION_FAILURE)
			{
				Log.log(Log.ERROR, "IODAO", methodName, error) ;

				callable.close();
				callable=null;
				connection.rollback();

				throw new DatabaseException(error);
			}

			callable.close();
			callable=null;
			connection.commit();
		
		}
		catch(SQLException e)
		{
			Log.log(Log.ERROR, "IODAO", methodName, e.getMessage()) ;

			Log.logException(e);

			if (newConn)
			{
				try
				{
					connection.rollback();
				}
				catch (SQLException ignore)
				{
				}
			}

			throw new DatabaseException("Unable to update Scheme Propagation Details.");

		}
		finally
		{
			if (newConn)
			{
				DBConnection.freeConnection(connection);
			}
		}
			Log.log(Log.INFO, "IODAO", methodName, "Exited") ;
	}
   
   
   
   
   
   
     
	public String getFile(String inward) throws Exception  
	{
		 Log.log(Log.INFO,"IODAO","getFile","Entered");	
		 String fileName = null;
		 PreparedStatement inwardStmt = null;
		 ResultSet inwardResult;
		 Connection connection = DBConnection.getConnection();

		try
		{
			String query = "select a.KNW_FILE_PATH from knowledge_mgmt a " +
							"where a.KNW_ID = ?";
			inwardStmt = connection.prepareStatement(query);
			inwardStmt.setString(1,inward);
			inwardResult = inwardStmt.executeQuery();

			while(inwardResult.next())
			{
				fileName = inwardResult.getString(1);
			}
			inwardResult.close();
			inwardResult = null;			
			inwardStmt.close();
			inwardStmt = null;

		
		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		finally
		{
			DBConnection.freeConnection(connection);
		}
		 
		 Log.log(Log.INFO,"IODAO","getFile","Exited");
		 return fileName;		
	 }
	}


