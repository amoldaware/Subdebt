/*
 * Created on Feb 10, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.cgtsi.admin;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
//import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;

//import com.cgtsi.common.DatabaseException;
import com.cgtsi.claim.SettlementDetail;
import com.cgtsi.common.Log;
import com.cgtsi.common.DatabaseException;
import com.cgtsi.receiptspayments.DemandAdvice;
import com.cgtsi.receiptspayments.RpConstants;
import com.cgtsi.receiptspayments.RpDAO;
import com.cgtsi.receiptspayments.RpProcessor;
import com.cgtsi.util.DBConnection;
import com.cgtsi.common.Constants;

/**
 * @author RT14509
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ScheduledProcessDAO {
	
	
	public ScheduledProcessDAO(){
		
	}
	
	
	public  void adminMailInfo() throws DatabaseException
	{
		Log.log(Log.INFO,"ScheduledProcessDAO","adminMailInfo","Entered");
		CallableStatement updateStatement;
		Connection connection=DBConnection.getConnection(); 
		try
		{
			updateStatement = connection.prepareCall("{? = call funcDTAdminMailInfo(?)}");
			updateStatement.registerOutParameter(1,Types.INTEGER);
			updateStatement.registerOutParameter(2,Types.VARCHAR);
			updateStatement.execute();
			
			int status = updateStatement.getInt(1);
			
			if(status == Constants.FUNCTION_FAILURE)
			{
				 //Error Code if any 
				String error = updateStatement.getString(2); 
				
				Log.log(Log.ERROR,"ScheduledProcessDAO","adminMailInfo","SP returns a 1." +
					" Error code is :"+error);
				updateStatement.close();
			}
			else if(status == Constants.FUNCTION_SUCCESS)
			{
				updateStatement.close();
				updateStatement = null;
			}
		}
		catch(Exception exception)
		{
			Log.log(Log.CRITICAL,"ScheduledProcessDAO","adminMailInfo","Exception:"+exception.getMessage());
		}
		finally
		{
				DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO,"ScheduledProcessDAO","adminMailInfo","Exited");
	}


	public  void tcOutstanding() throws DatabaseException
	{
		Log.log(Log.INFO,"ScheduledProcessDAO","tcOutstanding","Entered");
		CallableStatement updateStatement;
		Connection connection=DBConnection.getConnection(); 
		try
		{
			updateStatement = connection.prepareCall("{? = call funcDTTCDetail(?)}");
			updateStatement.registerOutParameter(1,Types.INTEGER);
			updateStatement.registerOutParameter(2,Types.VARCHAR);
			updateStatement.execute();
			
			int status = updateStatement.getInt(1);
			
			if(status == Constants.FUNCTION_FAILURE)
			{
				 //Error Code if any 
				String error = updateStatement.getString(2); 
				
				Log.log(Log.ERROR,"ScheduledProcessDAO","adminMailInfo","SP returns a 1." +
					" Error code is :"+error);
				updateStatement.close();
			}
			else if(status == Constants.FUNCTION_SUCCESS)
			{
				updateStatement.close();
				updateStatement = null;
			}
		}
		catch(Exception exception)
		{
			Log.log(Log.CRITICAL,"ScheduledProcessDAO","adminMailInfo","Exception:"+exception.getMessage());
		}
		finally
		{
				DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO,"ScheduledProcessDAO","tcOutstanding","Exited");
	}

	
	public  void wcOutstanding() throws DatabaseException
	{
		Log.log(Log.INFO,"ScheduledProcessDAO","wcOutstanding","Entered");
		CallableStatement updateStatement;
		Connection connection=DBConnection.getConnection(); 
		try
		{
			updateStatement = connection.prepareCall("{? = call funcDTWCDetail(?)}");
			updateStatement.registerOutParameter(1,Types.INTEGER);
			updateStatement.registerOutParameter(2,Types.VARCHAR);
			updateStatement.execute();
			
			int status = updateStatement.getInt(1);
			
			if(status == Constants.FUNCTION_FAILURE)
			{
				 //Error Code if any 
				String error = updateStatement.getString(2); 
				
				Log.log(Log.ERROR,"ScheduledProcessDAO","adminMailInfo","SP returns a 1." +
					" Error code is :"+error);
				updateStatement.close();
			}
			else if(status == Constants.FUNCTION_SUCCESS)
			{
				updateStatement.close();
				updateStatement = null;
			}
		}
		catch(Exception exception)
		{
			Log.log(Log.CRITICAL,"ScheduledProcessDAO","adminMailInfo","Exception:"+exception.getMessage());
		}
		finally
		{
				DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO,"ScheduledProcessDAO","wcOutstanding","Exited");
	}

	
	public  void dbrDetail() throws DatabaseException
	{
		Log.log(Log.INFO,"ScheduledProcessDAO","dbrDetail","Entered");
		CallableStatement updateStatement;
		Connection connection=DBConnection.getConnection(); 
		try
		{
			updateStatement = connection.prepareCall("{? = call funcDTDBRDetail(?)}");
			updateStatement.registerOutParameter(1,Types.INTEGER);
			updateStatement.registerOutParameter(2,Types.VARCHAR);
			updateStatement.execute();
			
			int status = updateStatement.getInt(1);
			
			
			if(status == Constants.FUNCTION_FAILURE)
			{
				 //Error Code if any 
				String error = updateStatement.getString(2); 
				
				Log.log(Log.ERROR,"ScheduledProcessDAO","dbrDetail","SP returns a 1." +
					" Error code is :" + error);
				updateStatement.close();
			}
			else if(status == Constants.FUNCTION_SUCCESS)
			{
				updateStatement.close();
				updateStatement = null;
			}
		}
		catch(Exception exception)
		{
			Log.log(Log.CRITICAL,"ScheduledProcessDAO","dbrDetail","Exception:"+exception.getMessage());
		}
		finally{
				DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO,"ScheduledProcessDAO","dbrDetail","Exited");
	}
	
	
	public  void donorDetail() throws DatabaseException
	{
		Log.log(Log.INFO,"ScheduledProcessDAO","donorDetail","Entered");
		CallableStatement updateStatement;
		Connection connection=DBConnection.getConnection(); 
		try
		{
			updateStatement = connection.prepareCall("{? = call funcDTDonorDtl(?)}");
			updateStatement.registerOutParameter(1,Types.INTEGER);
			updateStatement.registerOutParameter(2,Types.VARCHAR);
			updateStatement.execute();
			
			int status = updateStatement.getInt(1);
			
			
			if(status == Constants.FUNCTION_FAILURE)
			{
				 //Error Code if any 
				String error = updateStatement.getString(2); 
				
				Log.log(Log.ERROR,"ScheduledProcessDAO","donorDetail","SP returns a 1." +
					" Error code is :" + error);
				updateStatement.close();
			}
			else if(status == Constants.FUNCTION_SUCCESS)
			{
				updateStatement.close();
				updateStatement = null;
			}
		}
		catch(Exception exception)
		{
			Log.log(Log.CRITICAL,"ScheduledProcessDAO","donorDetail","Exception:"+exception.getMessage());
		}
		finally{
				DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO,"ScheduledProcessDAO","donorDetail","Exited");
	}
	
	public  void legalDetail() throws DatabaseException
	{
		Log.log(Log.INFO,"ScheduledProcessDAO","legalDetail","Entered");
		CallableStatement updateStatement;
		Connection connection=DBConnection.getConnection(); 
		try
		{
			updateStatement = connection.prepareCall("{? = call funcDTLegalDtl(?)}");
			updateStatement.registerOutParameter(1,Types.INTEGER);
			updateStatement.registerOutParameter(2,Types.VARCHAR);
			updateStatement.execute();
			
			int status = updateStatement.getInt(1);
			
			
			if(status == Constants.FUNCTION_FAILURE)
			{
				 //Error Code if any 
				String error = updateStatement.getString(2); 
				
				Log.log(Log.ERROR,"ScheduledProcessDAO","legalDetail","SP returns a 1." +
					" Error code is :" + error);
				updateStatement.close();
			}
			else if(status == Constants.FUNCTION_SUCCESS)
			{
				updateStatement.close();
				updateStatement = null;
			}
		}
		catch(Exception exception)
		{
			Log.log(Log.CRITICAL,"ScheduledProcessDAO","legalDetail","Exception:"+exception.getMessage());
		}
		finally{
				DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO,"ScheduledProcessDAO","legalDetail","Exited");
	}
	
	public  void memberInfo() throws DatabaseException
	{
		Log.log(Log.INFO,"ScheduledProcessDAO","memberInfo","Entered");
		CallableStatement updateStatement;
		Connection connection=DBConnection.getConnection(); 
		try
		{
			updateStatement = connection.prepareCall("{? = call funcDTMemberInfo(?)}");
			updateStatement.registerOutParameter(1,Types.INTEGER);
			updateStatement.registerOutParameter(2,Types.VARCHAR);
			updateStatement.execute();
			
			int status = updateStatement.getInt(1);
			
			
			if(status == Constants.FUNCTION_FAILURE)
			{
				 //Error Code if any 
				String error = updateStatement.getString(2); 
				
				Log.log(Log.ERROR,"ScheduledProcessDAO","memberInfo","SP returns a 1." +
					" Error code is :" + error);
				updateStatement.close();
			}
			else if(status == Constants.FUNCTION_SUCCESS)
			{
				updateStatement.close();
				updateStatement = null;
			}
		}
		catch(Exception exception)
		{
			Log.log(Log.CRITICAL,"ScheduledProcessDAO","memberInfo","Exception:"+exception.getMessage());
		}
		finally{
				DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO,"ScheduledProcessDAO","memberInfo","Exited");
	}
	
	public  void npaDetail() throws DatabaseException
	{
		Log.log(Log.INFO,"ScheduledProcessDAO","npaDetail","Entered");
		CallableStatement updateStatement;
		Connection connection=DBConnection.getConnection(); 
		try
		{
			updateStatement = connection.prepareCall("{? = call funcDTNpaDetail(?)}");
			updateStatement.registerOutParameter(1,Types.INTEGER);
			updateStatement.registerOutParameter(2,Types.VARCHAR);
			updateStatement.execute();
			
			int status = updateStatement.getInt(1);
			
			
			if(status == Constants.FUNCTION_FAILURE)
			{
				 //Error Code if any 
				String error = updateStatement.getString(2); 
				
				Log.log(Log.ERROR,"ScheduledProcessDAO","npaDetail","SP returns a 1." +
					" Error code is :" + error);
				updateStatement.close();
			}
			else if(status == Constants.FUNCTION_SUCCESS)
			{
				updateStatement.close();
				updateStatement = null;
			}
		}
		catch(Exception exception)
		{
			Log.log(Log.CRITICAL,"ScheduledProcessDAO","npaDetail","Exception:"+exception.getMessage());
		}
		finally{
				DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO,"ScheduledProcessDAO","npaDetail","Exited");
	}
	
	public  void plr() throws DatabaseException
	{
		Log.log(Log.INFO,"ScheduledProcessDAO","plr","Entered");
		CallableStatement updateStatement;
		Connection connection=DBConnection.getConnection(); 
		try
		{
			updateStatement = connection.prepareCall("{? = call funcDTPLR(?)}");
			updateStatement.registerOutParameter(1,Types.INTEGER);
			updateStatement.registerOutParameter(2,Types.VARCHAR);
			updateStatement.execute();
			
			int status = updateStatement.getInt(1);
			
			if(status == Constants.FUNCTION_FAILURE)
			{
				 //Error Code if any 
				String error = updateStatement.getString(2); 
				
				Log.log(Log.ERROR,"ScheduledProcessDAO","plr","SP returns a 1." +
					" Error code is :" + error);
				updateStatement.close();
			}
			else if(status == Constants.FUNCTION_SUCCESS)
			{
				updateStatement.close();
				updateStatement = null;
			}
		}
		catch(Exception exception)
		{
			Log.log(Log.CRITICAL,"ScheduledProcessDAO","plr","Exception:"+exception.getMessage());
		}
		finally{
				DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO,"ScheduledProcessDAO","plr","Exited");
	}
	
	public  void recoveryDetail(User user) throws DatabaseException
	{
		Log.log(Log.INFO,"ScheduledProcessDAO","recoveryDetail","Entered");
		CallableStatement updateStatement;
		Connection connection=DBConnection.getConnection(); 
		
		String bankId="";
		String cldanNo="";
		RpDAO rpDAO = new RpDAO();
		RpProcessor rpProcessor = new RpProcessor(); 
		DemandAdvice demandAdvice = new DemandAdvice();
		try
		{
			/**
			 * Generate CLDAN for the recovery details entered
			 */
			
			ArrayList recoveryDetails = getRecoveryDtlsForCLDAN();
			if(recoveryDetails!=null && recoveryDetails.size()>0)
			{				
								
				for(int i=0; i< recoveryDetails.size(); i++)
				{
					SettlementDetail settlementDetail = (SettlementDetail)recoveryDetails.get(i);
					
					double recoveredAmount = settlementDetail.getRecoveryAmt();
					
					String danType = RpConstants.DAN_TYPE_CLDAN;

					if(settlementDetail.getMliId()!=null)
					{
						bankId=(settlementDetail.getMliId()).substring(0,4);
					}

					cldanNo=rpDAO.getDANId(danType, bankId, connection);
					Log.log(Log.DEBUG, "ScheduledProcessDAO","recoveryDetail","cldan: " + cldanNo);
					
					ArrayList cgpanList=rpDAO.getCgansForBID(settlementDetail.getCgbid());
					int cgpanSize=cgpanList.size();
					
					Log.log(Log.DEBUG, "ScheduledProcessDAO","recoveryDetail","cgpanSize: " + cgpanSize);
					
					double distAmount=recoveredAmount;					

					demandAdvice.setDanNo(cldanNo);
					demandAdvice.setDanType(danType);
					demandAdvice.setDanGeneratedDate(new java.util.Date());

					demandAdvice.setBankId((settlementDetail.getMliId()).substring(0,4));
					demandAdvice.setZoneId((settlementDetail.getMliId()).substring(4,8));
					demandAdvice.setBranchId((settlementDetail.getMliId()).substring(8,12));
					//java.util.Date cldanDueDate=getPANDueDate(demandAdvice,null);

					Calendar calendar=Calendar.getInstance();
					java.util.Date generatedDate=demandAdvice.getDanGeneratedDate();
					calendar.setTime(generatedDate);
					calendar.add(Calendar.DATE,30);

					java.util.Date cldanDueDate=calendar.getTime();
					demandAdvice.setDanDueDate(cldanDueDate);
					demandAdvice.setUserId(user.getUserId());
					rpDAO.insertDANDetails(demandAdvice, connection);

					for(int j=0;j<cgpanSize;j++)
					{
						Log.log(Log.DEBUG, "ScheduledProcessDAO","recoveryDetail","cldan: " + cldanNo +
						"for CGPAN " + cgpanList.get(j));

						demandAdvice=new DemandAdvice();

						String cgpan=(String)cgpanList.get(j);
						demandAdvice.setCgpan(cgpan);
						demandAdvice.setDanNo(cldanNo);
						demandAdvice.setDanType(danType);


						//demandAdvice.setBankId(bankId);

						//demandAdvice.setZoneId(zoneId);

						//demandAdvice.setBranchId(branchId);

						//demandAdvice.setUserId(user.getUserId());

						Log.log(Log.DEBUG, "ScheduledProcessDAO","recoveryDetail","distAmount: " + distAmount);
						if(j==0)
						{
							demandAdvice.setAmountRaised(Math.round(distAmount));
						}
						else{
							demandAdvice.setAmountRaised(0);
						}
						
						demandAdvice.setPenalty(0);
						demandAdvice.setDanGeneratedDate(new java.util.Date());

						cldanDueDate=rpProcessor.getPANDueDate(demandAdvice,null);
						demandAdvice.setDanDueDate(cldanDueDate);
						rpDAO.insertPANDetailsForDAN(demandAdvice, connection);
						//rpDAO.insertDANDetails(demandAdvice);

					}
				}

			}
			
			updateStatement = connection.prepareCall("{? = call funcDTRecDtl(?)}");
			updateStatement.registerOutParameter(1,Types.INTEGER);
			updateStatement.registerOutParameter(2,Types.VARCHAR);
			updateStatement.execute();
			
			int status = updateStatement.getInt(1);
			
			
			if(status == Constants.FUNCTION_FAILURE)
			{
				 //Error Code if any 
				String error = updateStatement.getString(2); 
				
				Log.log(Log.ERROR,"ScheduledProcessDAO","recoveryDetail","SP returns a 1." +
					" Error code is :" + error);
				updateStatement.close();
			}
			else if(status == Constants.FUNCTION_SUCCESS)
			{
				updateStatement.close();
				updateStatement = null;
			}
		}
		catch(Exception exception)
		{
			Log.log(Log.CRITICAL,"ScheduledProcessDAO","recoveryDetail","Exception:"+exception.getMessage());
		}
		finally{
				DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO,"ScheduledProcessDAO","recoveryDetail","Exited");
	}
	
	public  void recoveryActionDetail() throws DatabaseException
	{
		Log.log(Log.INFO,"ScheduledProcessDAO","recoveryActionDetail","Entered");
		CallableStatement updateStatement;
		Connection connection=DBConnection.getConnection(); 
		try
		{
			updateStatement = connection.prepareCall("{? = call funcDTRecAxnDtl(?)}");
			updateStatement.registerOutParameter(1,Types.INTEGER);
			updateStatement.registerOutParameter(2,Types.VARCHAR);
			updateStatement.execute();
			
			int status = updateStatement.getInt(1);
			System.out.println("status:"+status);
			
			if(status == Constants.FUNCTION_FAILURE)
			{
				 //Error Code if any 
				String error = updateStatement.getString(2); 
				System.out.println("error:"+error);
				Log.log(Log.ERROR,"ScheduledProcessDAO","recoveryActionDetail","SP returns a 1." +
					" Error code is :" + error);
				updateStatement.close();
			}
			else if(status == Constants.FUNCTION_SUCCESS)
			{
				updateStatement.close();
				updateStatement = null;
			}
		}
		catch(Exception exception)
		{
			Log.log(Log.CRITICAL,"ScheduledProcessDAO","recoveryActionDetail","Exception:"+exception.getMessage());
		}
		finally{
				DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO,"ScheduledProcessDAO","recoveryActionDetail","Exited");
	}
	
	public  void repaymentDetail() throws DatabaseException
	{
		Log.log(Log.INFO,"ScheduledProcessDAO","repaymentDetail","Entered");
		CallableStatement updateStatement;
		Connection connection=DBConnection.getConnection(); 
		try
		{
			updateStatement = connection.prepareCall("{? = call funcDTRepayDtl(?)}");
			updateStatement.registerOutParameter(1,Types.INTEGER);
			updateStatement.registerOutParameter(2,Types.VARCHAR);
			updateStatement.execute();
			
			int status = updateStatement.getInt(1);
			System.out.println("status:"+status);
			
			if(status == Constants.FUNCTION_FAILURE)
			{
				 //Error Code if any 
				String error = updateStatement.getString(2); 
				System.out.println("error:"+error);
				Log.log(Log.ERROR,"ScheduledProcessDAO","repaymentDetail","SP returns a 1." +
					" Error code is :" + error);
				updateStatement.close();
			}
			else if(status == Constants.FUNCTION_SUCCESS)
			{
				updateStatement.close();
				updateStatement = null;
			}
		}
		catch(Exception exception)
		{
			Log.log(Log.CRITICAL,"ScheduledProcessDAO","repaymentDetail","Exception:"+exception.getMessage());
		}
		finally{
				DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO,"ScheduledProcessDAO","repaymentDetail","Exited");
	}
	
	public  void repaymentSchemeDetail() throws DatabaseException
	{
		Log.log(Log.INFO,"ScheduledProcessDAO","repaymentSchemeDetail","Entered");
		CallableStatement updateStatement;
		Connection connection=DBConnection.getConnection(); 
		try
		{
			updateStatement = connection.prepareCall("{? = call funcDTRepaySch(?)}");
			updateStatement.registerOutParameter(1,Types.INTEGER);
			updateStatement.registerOutParameter(2,Types.VARCHAR);
			updateStatement.execute();
			
			int status = updateStatement.getInt(1);
			System.out.println("status:"+status);
			
			if(status == Constants.FUNCTION_FAILURE)
			{
				 //Error Code if any 
				String error = updateStatement.getString(2); 
				System.out.println("error:"+error);
				Log.log(Log.ERROR,"ScheduledProcessDAO","repaymentSchemeDetail","SP returns a 1." +
					" Error code is :" + error);
				updateStatement.close();
			}
			else if(status == Constants.FUNCTION_SUCCESS)
			{
				updateStatement.close();
				updateStatement = null;
			}
		}
		catch(Exception exception)
		{
			Log.log(Log.CRITICAL,"ScheduledProcessDAO","repaymentSchemeDetail","Exception:"+exception.getMessage());
		}
		finally{
				DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO,"ScheduledProcessDAO","repaymentSchemeDetail","Exited");
	}
	
	public  void userInfo() throws DatabaseException
	{
		
		Log.log(Log.INFO,"ScheduledProcessDAO","userInfo","Entered");
		CallableStatement updateStatement;
		Connection connection=DBConnection.getConnection(); 
		try
		{
			updateStatement = connection.prepareCall("{? = call funcDTUserInfo(?)}");
			updateStatement.registerOutParameter(1,Types.INTEGER);
			updateStatement.registerOutParameter(2,Types.VARCHAR);
			updateStatement.execute();
			
			int status = updateStatement.getInt(1);
			System.out.println("status:"+status);
			
			if(status == Constants.FUNCTION_FAILURE)
			{
				 //Error Code if any 
				String error = updateStatement.getString(2); 
				System.out.println("error:"+error);
				Log.log(Log.ERROR,"ScheduledProcessDAO","addInward","SP returns a 1." +
					" Error code is :" + error);
				updateStatement.close();
			}
			else if(status == Constants.FUNCTION_SUCCESS)
			{
				updateStatement.close();
				updateStatement = null;
			}
		}
		catch(Exception exception)
		{
			Log.log(Log.CRITICAL,"ScheduledProcessDAO","userInfo","Exception:"+exception.getMessage());
		}
		finally{
				DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO,"ScheduledProcessDAO","userInfo","Exited");
	}
	
	
	public  void userActiveDeactiveLog() throws DatabaseException
	{
		Log.log(Log.INFO,"ScheduledProcessDAO","userActiveDeactiveLog","Entered");
		CallableStatement updateStatement;
		Connection connection=DBConnection.getConnection(); 
		try
		{
			updateStatement = connection.prepareCall("{? = call funcDTUsrActLog(?)}");
			updateStatement.registerOutParameter(1,Types.INTEGER);
			updateStatement.registerOutParameter(2,Types.VARCHAR);
			updateStatement.execute();
			
			int status = updateStatement.getInt(1);
			System.out.println("status:"+status);
			
			if(status == Constants.FUNCTION_FAILURE)
			{
				 //Error Code if any 
				String error = updateStatement.getString(2); 
				System.out.println("error:"+error);
				Log.log(Log.ERROR,"ScheduledProcessDAO","userActiveDeactiveLog","SP returns a 1." +
					" Error code is :" + error);
				updateStatement.close();
			}
			else if(status == Constants.FUNCTION_SUCCESS)
			{
				updateStatement.close();
				updateStatement = null;
			}
		}
		catch(Exception exception)
		{
			Log.log(Log.CRITICAL,"ScheduledProcessDAO","userActiveDeactiveLog","Exception:"+exception.getMessage());
		}
		finally{
				DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO,"ScheduledProcessDAO","userActiveDeactiveLog","Exited");
	}
	
	public  void procRemDemoUser() throws DatabaseException
	{
		Log.log(Log.INFO,"ScheduledProcessDAO","procRemDemoUser","Entered");
		CallableStatement updateStatement;
		Connection connection=DBConnection.getConnection(); 
		try
		{
			updateStatement = connection.prepareCall("{call procRemDemoUser}");
			updateStatement.execute();
		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		finally{
				DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO,"ScheduledProcessDAO","procRemDemoUser","Exited");
	}
	
	public  void outstandingDetail() throws DatabaseException
	{
		Log.log(Log.INFO,"ScheduledProcessDAO","outstandingDetail","Entered");
		CallableStatement updateStatement;
		Connection connection=DBConnection.getConnection(); 
		try
		{
			updateStatement = connection.prepareCall("{? = call funcDTOTSDetail(?)}");
			updateStatement.registerOutParameter(1,Types.INTEGER);
			updateStatement.registerOutParameter(2,Types.VARCHAR);
			updateStatement.execute();
			
			int status = updateStatement.getInt(1);
			System.out.println("--------------------------");
			System.out.println("status:"+status);
			
			if(status == Constants.FUNCTION_FAILURE)
			{
				 //Error Code if any 
				String error = updateStatement.getString(2); 
				System.out.println("error:"+error);
				Log.log(Log.ERROR,"ScheduledProcessDAO","outstandingDetail","SP returns a 1." +
					" Error code is :"+error);
				updateStatement.close();
			}
			else if(status == Constants.FUNCTION_SUCCESS)
			{
				updateStatement.close();
				updateStatement = null;
			}
		}
		catch(Exception exception)
		{
			Log.log(Log.CRITICAL,"ScheduledProcessDAO","outstandingDetail","Exception:"+exception.getMessage());
		}
		finally
		{
				DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO,"ScheduledProcessDAO","outstandingDetail","Exited");
	}
	

	public void updateAppExpiry() throws DatabaseException
	{
		
		Log.log(Log.INFO,"ScheduledProcessDAO","updateAppExpiry","Exited");
		
		CallableStatement expiryStmt;
		Connection connection = DBConnection.getConnection();

		try
		{
			expiryStmt = connection.prepareCall("{? = call funcUpdateAppExpiry(?,?)}");
			expiryStmt.setString(2, AdminConstants.ADMIN_USER_ID);						// User Id
			expiryStmt.registerOutParameter(1,Types.INTEGER);
			expiryStmt.registerOutParameter(3,Types.VARCHAR);
			expiryStmt.execute();
		 
			int status = expiryStmt.getInt(1);
			String error = expiryStmt.getString(3); 
			if (status == Constants.FUNCTION_FAILURE)
			{
				Log.log(Log.CRITICAL,"ScheduledProcess","updateAppExpiry","Error from funcUpdateAppExpiry " + error);
			}
			else
			{
				Log.log(Log.CRITICAL,"ScheduledProcess","updateAppExpiry","Updation of Application Expiry Status Successfull");
			}
		} 
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		finally{
				DBConnection.freeConnection(connection);
		}
		
		Log.log(Log.INFO,"ScheduledProcessDAO","updateAppExpiry","Exited");
	}


	public  void userRoles() throws DatabaseException
	{
		Log.log(Log.INFO,"ScheduledProcessDAO","userRoles","Entered");
		CallableStatement updateStatement;
		Connection connection=DBConnection.getConnection(); 
		try
		{
			updateStatement = connection.prepareCall("{? = call funcDTUserRoles(?)}");
			updateStatement.registerOutParameter(1,Types.INTEGER);
			updateStatement.registerOutParameter(2,Types.VARCHAR);
			updateStatement.execute();
			
			int status = updateStatement.getInt(1);
			System.out.println("status:"+status);
			
			if(status == Constants.FUNCTION_FAILURE)
			{
				 //Error Code if any 
				String error = updateStatement.getString(2); 
				System.out.println("error:"+error);
				Log.log(Log.ERROR,"ScheduledProcessDAO","userRoles","SP returns a 1." +
					" Error code is :"+error);
				updateStatement.close();
			}
			else if(status == Constants.FUNCTION_SUCCESS)
			{
				updateStatement.close();
				updateStatement = null;
			}
		}
		catch(Exception exception)
		{
			Log.log(Log.CRITICAL,"ScheduledProcessDAO","userRoles","Exception:"+exception.getMessage());
		}
		finally
		{
				DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO,"ScheduledProcessDAO","userRoles","Exited");
	}
	
	
	
	public  void userPrivileges() throws DatabaseException
	{
		Log.log(Log.INFO,"ScheduledProcessDAO","userPrivileges","Entered");
		CallableStatement updateStatement;
		Connection connection=DBConnection.getConnection(); 
		try
		{
			updateStatement = connection.prepareCall("{? = call funcDTUserPrivileges(?)}");
			updateStatement.registerOutParameter(1,Types.INTEGER);
			updateStatement.registerOutParameter(2,Types.VARCHAR);
			updateStatement.execute();
			
			int status = updateStatement.getInt(1);
			System.out.println("status:"+status);
			
			if(status == Constants.FUNCTION_FAILURE)
			{
				 //Error Code if any 
				String error = updateStatement.getString(2); 
				System.out.println("error:"+error);
				Log.log(Log.ERROR,"ScheduledProcessDAO","userPrivileges","SP returns a 1." +
					" Error code is :"+error);
				updateStatement.close();
			}
			else if(status == Constants.FUNCTION_SUCCESS)
			{
				updateStatement.close();
				updateStatement = null;
			}
		}
		catch(Exception exception)
		{
			Log.log(Log.CRITICAL,"ScheduledProcessDAO","userPrivileges","Exception:"+exception.getMessage());
		}
		finally
		{
				DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO,"ScheduledProcessDAO","userPrivileges","Exited");
	}
	
	public  ArrayList getRecoveryDtlsForCLDAN() throws DatabaseException
	{
		Log.log(Log.INFO,"ScheduledProcessDAO","getRecoveryDtlsForCLDAN","Entered");
		ArrayList settlementDetails = new ArrayList();

		CallableStatement updateStatement;
		Connection connection=DBConnection.getConnection(); 
		try
		{
			updateStatement = connection.prepareCall("{? = call packGetDtlsForCLDANRec.funcGetDtlsForCLDANRec(?,?)}");
			updateStatement.registerOutParameter(1,Types.INTEGER);
			updateStatement.registerOutParameter(2,Constants.CURSOR);
			updateStatement.registerOutParameter(3,Types.VARCHAR);
			updateStatement.execute();
			
			int status = updateStatement.getInt(1);			
			
			if(status == Constants.FUNCTION_FAILURE)
			{
				 //Error Code if any 
				String error = updateStatement.getString(3); 
				
				Log.log(Log.ERROR,"ScheduledProcessDAO","getRecoveryDtlsForCLDAN","SP returns a 1." +
					" Error code is :"+error);
				updateStatement.close();
			}
			else if(status == Constants.FUNCTION_SUCCESS)
			{
				ResultSet recoveryResults = (ResultSet)updateStatement.getObject(2);
				while(recoveryResults.next())
				{
					SettlementDetail settlementDetail = new SettlementDetail();
					settlementDetail.setCgbid(recoveryResults.getString(2));
					if(recoveryResults.getDouble(3) > 0)
					{
						settlementDetail.setRecoveryAmt(recoveryResults.getDouble(3));
						Log.log(Log.INFO,"ScheduledProcessDAO","getRecoveryDtlsForCLDAN","recoveryResults.getString(4) :" + recoveryResults.getString(4));
						Log.log(Log.INFO,"ScheduledProcessDAO","getRecoveryDtlsForCLDAN","recoveryResults.getString(5) :" + recoveryResults.getString(5));
						Log.log(Log.INFO,"ScheduledProcessDAO","getRecoveryDtlsForCLDAN","recoveryResults.getString(6) :" + recoveryResults.getString(6));
						settlementDetail.setMliId(recoveryResults.getString(4)+ recoveryResults.getString(5)+ recoveryResults.getString(6));
						Log.log(Log.INFO,"ScheduledProcessDAO","getRecoveryDtlsForCLDAN","settlementDetail.getMliId() :" + settlementDetail.getMliId());
						settlementDetails.add(settlementDetail);					
												
					}
					
					
				}
			}
		}
		catch(Exception exception)
		{
			Log.log(Log.CRITICAL,"ScheduledProcessDAO","getRecoveryDtlsForCLDAN","Exception:"+exception.getMessage());
		}
		return settlementDetails;
		
	}
	
}
