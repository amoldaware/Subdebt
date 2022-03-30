package com.cgtsi.risk;

/*************************************************************
   *
   * Name of the class: RIDAO
   * This class encapsulates all the database queries for this module.
   *
   * @author : Nithyalakshmi P
   * @version:  
   * @since: 
   **************************************************************/

import com.cgtsi.common.DatabaseException;
import com.cgtsi.common.MessageException;

import java.util.ArrayList;
import java.util.Date;
import java.sql.Connection;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import com.cgtsi.util.DBConnection;
import com.cgtsi.common.Constants;
import com.cgtsi.util.DateHelper;
import com.cgtsi.common.Log;
import com.cgtsi.mcgs.ParticipatingBankLimit;

import com.cgtsi.application.Application;

public class RIDAO 
{
   
   public RIDAO() 
   {
   }


   public ArrayList defaultRateReport(java.sql.Date settlementDate) throws DatabaseException
   {
	   Log.log(Log.INFO,"IFDAO","defaultRateReport","Entered"); 
	   PreparedStatement danRaisedStmt = null;
	   ArrayList danRaisedArray = new ArrayList();
	   ResultSet danRaisedResult;
	   Connection connection = DBConnection.getConnection();
	   String  id = "0000";

	   try
	   {
		   String query = "select sum(setd.set_tier1 + nvl(setd.set_tier2,0)), " + 
		   	" bankid, appamt,bankname from ( select  distinct cd.cgclan clan, " +
		   	" cd.mem_bnk_id bankid from claim_detail cd where   " +
		   	" cd.mem_bnk_id IN (select mem_bnk_id from member_info m " +
		   	" where m.mem_zne_id = ? and m.mem_brn_id = ? and " +
		   	" m.mem_bnk_id <> ?) and cd.cgclan is not null), " +
		   	" (select sum(decode(app.app_reapprove_amount, null, app.app_approved_amount, app.app_reapprove_amount))" +
		   	"  appamt, app.mem_bnk_id memid," +
		   	" mem.mem_bank_name bankname from   application_detail app, " +
		   	" member_info mem where  trunc(app.app_guar_start_date_time) <= ? and" +
		   	"   app.app_guar_start_date_time IS NOT NULL and app.mem_bnk_id =" +
		   	"  mem.mem_bnk_id and  mem.mem_zne_id = ? and  mem.mem_brn_id = ? " +
		   	"  group by app.mem_bnk_id, mem.mem_bank_name),settlement_detail setd " +
		   	" where setd.cgclan = clan and   bankid = memid and " +
		   	" trunc(setd.set_tier1_dt) <= ?" +
		   	"  group by bankid, appamt,bankname ";
		   danRaisedStmt = connection.prepareStatement(query);
			danRaisedStmt.setString(1,id);
			danRaisedStmt.setString(2,id);
			danRaisedStmt.setString(3,id);
			danRaisedStmt.setString(5,id);
			danRaisedStmt.setString(6,id);
		    danRaisedStmt.setDate(4,settlementDate);
			danRaisedStmt.setDate(7,settlementDate);
		    danRaisedResult = danRaisedStmt.executeQuery();

		   while(danRaisedResult.next())
		   {
			   //Instantiate a Gfee value object
			DefaultRate defaultRate = new DefaultRate();
			defaultRate.setMliId(danRaisedResult.getString(2));
			defaultRate.setMliName(danRaisedResult.getString(4));
			
			double claimAmount = danRaisedResult.getDouble(1);
			defaultRate.setCumulativeClaimSettlement(claimAmount);
			
			double guaranteeAmount = danRaisedResult.getDouble(3);
			defaultRate.setGuaranteeCoverIssuedAmount(guaranteeAmount);
			 
			double rate = claimAmount/guaranteeAmount;
			defaultRate.setDefaultRate(rate);
			
			danRaisedArray.add(defaultRate); 
		   }
		   danRaisedResult.close();
		   danRaisedResult=null;
		   danRaisedStmt.close();
		   danRaisedStmt=null;
	   }
	   catch(Exception exception)
	   { 
		   throw new DatabaseException(exception.getMessage());
	   }
	   finally
	   {
		   DBConnection.freeConnection(connection);
	   }
	   Log.log(Log.INFO,"IFDAO","defaultRateReport","Exited");
	   return  danRaisedArray;
   }
   
   /**
	* This method invokes the stored procedure for updating the global limit settings in the database.
	* It returns a boolean true if the update is successfull.
	* It returns a boolean false if :
	*	an error was encountered while executing the stored procedure.
	*	if the globalLimits object passed to this method is null.
	* It throws a DatabaseException in case of:
	*	any Exception during the establishment of the connection or
	*	execution of the prepared call statement.
	*
	* @throws DatabaseException
	* @param globalLimits
	*/
   public void updateLimitsForGlobalSettings(GlobalLimits globalLimits, String userId) throws DatabaseException
   {
	   Log.log(Log.INFO,"RIDAO","updateLimitsForGlobalSettings","Entered");
	   Connection connection;
	   CallableStatement globalSettingStmt;
	   int amtFlag=0;																//indicates fund based or non fund based or both
	   int updateStatus=0;															//indicates whether the stored procedure was exceuted successfully
	   java.sql.Date sqlDate;
	   java.util.Date utilDate;

	   if (globalLimits != null)
	   {
		   connection=DBConnection.getConnection();
		   try
		   {
			   globalSettingStmt=connection.prepareCall("{?=call funcInsertGlobalLimit(?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			   globalSettingStmt.setString(2,globalLimits.getScheme());					//Scheme Name
			   Log.log(Log.DEBUG,"RIDAO","updateLimitsForGlobalSettings","2 -- " + globalLimits.getScheme());
			   globalSettingStmt.setString(3,globalLimits.getSubScheme());				//Sub Scheme Name
			   Log.log(Log.DEBUG,"RIDAO","updateLimitsForGlobalSettings","3 -- " + globalLimits.getSubScheme());
			   amtFlag=globalLimits.getIsFundsBasedOrNonFundsBasedOrBoth();
			   if (amtFlag==Constants.FUND_BASED)										//Fund Based
			   {
				   Log.log(Log.DEBUG,"RIDAO","updateLimitsForGlobalSettings","Setting fund based limit");
				   globalSettingStmt.setDouble(4,globalLimits.getUpperLimit());			//Amount
				   Log.log(Log.DEBUG,"RIDAO","updateLimitsForGlobalSettings","4 -- " + globalLimits.getUpperLimit());
				   utilDate=globalLimits.getValidFromDate();
				   sqlDate=new java.sql.Date(utilDate.getTime());
				   globalSettingStmt.setDate(5,sqlDate);		//Limit Valid From
				   Log.log(Log.DEBUG,"RIDAO","updateLimitsForGlobalSettings","5 -- " + sqlDate);
				   utilDate=globalLimits.getValidToDate();
				   if (utilDate.toString().equals(""))
				   {
					globalSettingStmt.setNull(6,java.sql.Types.DATE);		//Limit Valid To Date
				   }
				   else
				   {
					sqlDate=new java.sql.Date(utilDate.getTime());
					globalSettingStmt.setDate(6,sqlDate);		//Limit Valid To Date
					Log.log(Log.DEBUG,"RIDAO","updateLimitsForGlobalSettings","6 -- " + sqlDate);
				   }
				   globalSettingStmt.setNull(7,java.sql.Types.DOUBLE);
				   Log.log(Log.DEBUG,"RIDAO","updateLimitsForGlobalSettings","7 -- null");
				   globalSettingStmt.setNull(8,java.sql.Types.DATE);
				   Log.log(Log.DEBUG,"RIDAO","updateLimitsForGlobalSettings","8 -- null");
				   globalSettingStmt.setNull(9,java.sql.Types.DATE);
				   Log.log(Log.DEBUG,"RIDAO","updateLimitsForGlobalSettings","9 -- null");
				   globalSettingStmt.setDouble(10,globalLimits.getUpperLimit());			//Total Amount
				   Log.log(Log.DEBUG,"RIDAO","updateLimitsForGlobalSettings","10 -- " + globalLimits.getUpperLimit());
				   utilDate=globalLimits.getValidFromDate();
				   sqlDate=new java.sql.Date(utilDate.getTime());
				   globalSettingStmt.setDate(11,sqlDate);		//Limit Valid From
				   Log.log(Log.DEBUG,"RIDAO","updateLimitsForGlobalSettings","11 -- " + sqlDate);
				   utilDate=globalLimits.getValidToDate();
					if (utilDate.toString().equals(""))
					{
					 globalSettingStmt.setNull(12,java.sql.Types.DATE);		//Limit Valid To Date
					}
					else
					{				   
						sqlDate=new java.sql.Date(utilDate.getTime());
						globalSettingStmt.setDate(12,sqlDate);		//Limit Valid To Date
						Log.log(Log.DEBUG,"RIDAO","updateLimitsForGlobalSettings","12 -- " + sqlDate);
					}
			   }
			   else if (amtFlag==Constants.NON_FUND_BASED)								//Non Fund Based
			   {
				   Log.log(Log.DEBUG,"RIDAO","updateLimitsForGlobalSettings","setting non fund based limits");
				   globalSettingStmt.setNull(4,java.sql.Types.DOUBLE);
				   Log.log(Log.DEBUG,"RIDAO","updateLimitsForGlobalSettings","4 -- null");
				   globalSettingStmt.setNull(5,java.sql.Types.DATE);
				   Log.log(Log.DEBUG,"RIDAO","updateLimitsForGlobalSettings","5 -- null");
				   globalSettingStmt.setNull(6,java.sql.Types.DATE);
				   Log.log(Log.DEBUG,"RIDAO","updateLimitsForGlobalSettings","6 -- null");
				   globalSettingStmt.setDouble(7,globalLimits.getUpperLimit());			//Amount
				   Log.log(Log.DEBUG,"RIDAO","updateLimitsForGlobalSettings","7 -- " + globalLimits.getUpperLimit());
				   utilDate=globalLimits.getValidToDate();
				   sqlDate=new java.sql.Date(utilDate.getTime());
				   globalSettingStmt.setDate(8,sqlDate);		//Limit Valid From
				   Log.log(Log.DEBUG,"RIDAO","updateLimitsForGlobalSettings","8 -- " + sqlDate);
				   utilDate=globalLimits.getValidToDate();
					if (utilDate.toString().equals(""))
					{
					 globalSettingStmt.setNull(9,java.sql.Types.DATE);		//Limit Valid To Date
					}
					else
					{
					   sqlDate=new java.sql.Date(utilDate.getTime());
					   globalSettingStmt.setDate(9,sqlDate);		//Limit Valid To Date
					   Log.log(Log.DEBUG,"RIDAO","updateLimitsForGlobalSettings","9 -- " + sqlDate);
					}
				   globalSettingStmt.setDouble(10,globalLimits.getUpperLimit());			//Total Amount
				   Log.log(Log.DEBUG,"RIDAO","updateLimitsForGlobalSettings","10 -- " + globalLimits.getUpperLimit());
				   utilDate=globalLimits.getValidFromDate();
				   sqlDate=new java.sql.Date(utilDate.getTime());
				   globalSettingStmt.setDate(11,sqlDate);		//Limit Valid From
				   Log.log(Log.DEBUG,"RIDAO","updateLimitsForGlobalSettings","11 -- " + sqlDate);
				   utilDate=globalLimits.getValidToDate();
				if (utilDate.toString().equals(""))
				{
				 globalSettingStmt.setNull(12,java.sql.Types.DATE);		//Limit Valid To Date
				}
				else
				{
				   sqlDate=new java.sql.Date(utilDate.getTime());
				   globalSettingStmt.setDate(12,sqlDate);		//Limit Valid To Date
				   Log.log(Log.DEBUG,"RIDAO","updateLimitsForGlobalSettings","12 -- " + sqlDate);
				}				   
			   }
			   else if (amtFlag==Constants.BOTH_FUND_BASED_NON_FUND_BASED)					//Both
			   {
				   Log.log(Log.DEBUG,"RIDAO","updateLimitsForGlobalSettings","setting both limits");
				   globalSettingStmt.setNull(4,java.sql.Types.DOUBLE);
				   Log.log(Log.DEBUG,"RIDAO","updateLimitsForGlobalSettings","4 -- null");
				   globalSettingStmt.setNull(5,java.sql.Types.DATE);
				   Log.log(Log.DEBUG,"RIDAO","updateLimitsForGlobalSettings","5 -- null");
				   globalSettingStmt.setNull(6,java.sql.Types.DATE);
				   Log.log(Log.DEBUG,"RIDAO","updateLimitsForGlobalSettings","6 -- null");
				   globalSettingStmt.setNull(7,java.sql.Types.DOUBLE);
				   Log.log(Log.DEBUG,"RIDAO","updateLimitsForGlobalSettings","7 -- null");
				   globalSettingStmt.setNull(8,java.sql.Types.DATE);
				   Log.log(Log.DEBUG,"RIDAO","updateLimitsForGlobalSettings","8 -- null");
				   globalSettingStmt.setNull(9,java.sql.Types.DATE);
				   Log.log(Log.DEBUG,"RIDAO","updateLimitsForGlobalSettings","9 -- null");
				   globalSettingStmt.setDouble(10,globalLimits.getUpperLimit());			//Total Amount
				   Log.log(Log.DEBUG,"RIDAO","updateLimitsForGlobalSettings","10 -- " + globalLimits.getUpperLimit());
				   utilDate=globalLimits.getValidFromDate();
				   sqlDate=new java.sql.Date(utilDate.getTime());
				   globalSettingStmt.setDate(11,sqlDate);		//Limit Valid From
				   Log.log(Log.DEBUG,"RIDAO","updateLimitsForGlobalSettings","11 -- " + sqlDate);
				   utilDate=globalLimits.getValidToDate();
				if (utilDate.toString().equals(""))
				{
				 globalSettingStmt.setNull(12,java.sql.Types.DATE);		//Limit Valid To Date
				}
				else
				{
				   sqlDate=new java.sql.Date(utilDate.getTime());
				   globalSettingStmt.setDate(12,sqlDate);		//Limit Valid To Date
				   Log.log(Log.DEBUG,"RIDAO","updateLimitsForGlobalSettings","12 -- " + sqlDate);
				}
			   }
			   globalSettingStmt.setString(13, userId);
			   Log.log(Log.DEBUG,"RIDAO","updateLimitsForGlobalSettings","13 -- " + userId);

			   globalSettingStmt.registerOutParameter(1,java.sql.Types.INTEGER);
			   globalSettingStmt.registerOutParameter(14,java.sql.Types.VARCHAR);
			   Log.log(Log.DEBUG,"RIDAO","updateLimitsForGlobalSettings","b4 execute" );
			   globalSettingStmt.executeQuery();
			   Log.log(Log.DEBUG,"RIDAO","updateLimitsForGlobalSettings","after execute" );
			   updateStatus=Integer.parseInt(globalSettingStmt.getObject(1).toString());
				Log.log(Log.DEBUG,"RIDAO","updateLimitsForGlobalSettings","Value returned from sp -- " + updateStatus);
			   if (updateStatus==Constants.FUNCTION_FAILURE)
			   {
				   String err = globalSettingStmt.getObject(14).toString();
					Log.log(Log.ERROR,"RIDAO","updateLimitsForGlobalSettings","exception from sp -- " + err);
					globalSettingStmt.close();
					globalSettingStmt=null;
				   throw new DatabaseException(err);
			   }
			   globalSettingStmt.close();
				globalSettingStmt=null;
		   }
		   catch (SQLException sqlException)
			{
				Log.log(Log.ERROR,"RIDAO","updateLimitsForGlobalSettings",sqlException.getMessage());
				Log.logException(sqlException);
				throw new DatabaseException(sqlException.getMessage());
			}
		   finally
		   {
			   DBConnection.freeConnection(connection);
		   }
	   }
	   globalLimits=null;
	   Log.log(Log.INFO,"RIDAO","updateLimitsForGlobalSettings","Exited");
   }
   
   /**
	* This method invokes the stored procedure for updating the user limits in the database.
	* It returns a boolean true if the update is successfull.
	* It returns a boolean false if :
	*	an error was encountered while executing the stored procedure.
	*	if the userLimits object passed to this method is null.
	* It throws a DatabaseException in case of:
	*	any Exception during the establishment of the connection or
	*	execution of the prepared call statement.
	*
	* @throws DatabaseException
	* @param userLimits
	*/
   public void updateUserLimits(UserLimits userLimits, String byUserId) throws Exception
   {
	   Log.log(Log.INFO,"RIDAO","updateUserLimits","Entered");
	   Connection connection;
	   CallableStatement userLimitStmt;
	   int updateStatus=0;														//indicates whether the stored procedure was exceuted successfully
	   java.sql.Date sqlDate;
	   java.util.Date utilDate;

	   if (userLimits != null)
	   {
			   connection=DBConnection.getConnection();
		   try
		   {
			   sqlDate=new java.sql.Date(0);
			   utilDate=new java.util.Date();
			   userLimitStmt=connection.prepareCall("{?=call funcInsertUserLimit(?,?,?,?,?,?,?,?,?)}");

				String desig=userLimits.getDesignation();
			   userLimitStmt.setString(2,desig);							//Designation
				Log.log(Log.DEBUG,"RIDAO","updateUserLimits","2 -- " + desig);
				//Application Approval Amount
			   userLimitStmt.setDouble(3,userLimits.getUpperApplicationApprovalLimit());
				Log.log(Log.DEBUG,"RIDAO","updateUserLimits","4 -- " + userLimits.getUpperApplicationApprovalLimit());
			   //Application Limit Valid From Date
			   if (userLimits.getUpperApplicationApprovalLimit() != 0.0)
			   {
				   utilDate=userLimits.getApplicationLimitValidFrom();
				   sqlDate=new java.sql.Date(utilDate.getTime());
				   userLimitStmt.setDate(4, sqlDate);
					Log.log(Log.DEBUG,"RIDAO","updateUserLimits","5 -- " + sqlDate);

				   //Application Limit Valid To Date
				   utilDate=userLimits.getApplicationLimitValidTo();
				if (utilDate.toString().equals(""))
				{
					userLimitStmt.setNull(5,java.sql.Types.DATE);		//Limit Valid To Date
				}
				else
				{
				   sqlDate=new java.sql.Date(utilDate.getTime());
				   userLimitStmt.setDate(5, sqlDate);
					Log.log(Log.DEBUG,"RIDAO","updateUserLimits","6 -- " + sqlDate);
				}
			   }
			   else
			   {
				   userLimitStmt.setNull(4, java.sql.Types.DATE);
				   userLimitStmt.setNull(5, java.sql.Types.DATE);
					Log.log(Log.DEBUG,"RIDAO","updateUserLimits","setting null for app approval valid from and to");
			   }

			   userLimitStmt.setDouble(6,userLimits.getUpperClaimsApprovalLimit());			//Claim Approval Amount
				Log.log(Log.DEBUG,"RIDAO","updateUserLimits","7 -- " + userLimits.getUpperClaimsApprovalLimit());
			   //Claim Limit Valid From Date
			   if (userLimits.getUpperClaimsApprovalLimit() != 0.0)
			   {
				   utilDate= userLimits.getClaimsLimitValidFrom();
				   sqlDate=new java.sql.Date(utilDate.getTime());
				   userLimitStmt.setDate(7, sqlDate);
					Log.log(Log.DEBUG,"RIDAO","updateUserLimits","8 -- " + sqlDate);

				   //Claim Limit Valid To Date
				   utilDate= userLimits.getClaimsLimitValidTo();
				if (utilDate.toString().equals(""))
				{
					userLimitStmt.setNull(8,java.sql.Types.DATE);		//Limit Valid To Date
				}
				else
				{
				   sqlDate=new java.sql.Date(utilDate.getTime());
				   userLimitStmt.setDate(8, sqlDate);
					Log.log(Log.DEBUG,"RIDAO","updateUserLimits","9 -- " + sqlDate);
				}
			   }
			   else
			   {
				   userLimitStmt.setNull(7, java.sql.Types.DATE);
				   userLimitStmt.setNull(8, java.sql.Types.DATE);
				   Log.log(Log.DEBUG,"RIDAO","updateUserLimits","setting null for claim approval valid from and to");
			   }

			   userLimitStmt.setString(9, byUserId);										//Limit Set by
				Log.log(Log.DEBUG,"RIDAO","updateUserLimits","10 -- " + byUserId);

			   userLimitStmt.registerOutParameter(1,java.sql.Types.INTEGER);
			   userLimitStmt.registerOutParameter(10,java.sql.Types.VARCHAR);
			   userLimitStmt.executeQuery();
			   updateStatus=Integer.parseInt(userLimitStmt.getObject(1).toString());
			   Log.log(Log.DEBUG,"RIDAO","updateUserLimits","value returned from sp -- " + updateStatus);
			   if (updateStatus==Constants.FUNCTION_FAILURE)
			   {
				   String err = userLimitStmt.getString(10);
				   Log.log(Log.DEBUG,"RIDAO","updateUserLimits","exception from sp -- " + err);
				   userLimitStmt.close();
				   userLimitStmt=null;
				   throw new DatabaseException(err);
			   }
			   userLimitStmt.close();
			   userLimitStmt=null;
		   }
		   catch (SQLException sqlException)
			{
			   Log.log(Log.ERROR, "RIDAO", "updateUserLimits", sqlException.getMessage());
				Log.logException(sqlException);
			   throw new DatabaseException(sqlException.getMessage());
			}
		   finally
		   {
			   DBConnection.freeConnection(connection);
		   }
	   }
	   userLimits=null;
	   Log.log(Log.INFO,"RIDAO","updateUserLimits","Exited");
   }



   public void setSubSchemeDetails(SubSchemeParameters subSchemeParameters, SubSchemeValues subSchemeValues, String userId) throws DatabaseException
	{
	   Log.log(Log.INFO,"RIDAO","setSubSchemeDetails","Entered");
	   Connection connection;
	   CallableStatement subSchemeStmt;
	   CallableStatement subSchemeDetailsStmt;
	   int status=-1;
	   java.sql.Date sqlDate;
	   java.util.Date utilDate;

	   connection=DBConnection.getConnection(false);
	   try
	   {
			subSchemeStmt=connection.prepareCall("{?=call funcInsertSubScheme(?,?,?,?,?,?)}");
			
			subSchemeStmt.setString(2,subSchemeParameters.getSubScheme());				//Sub Scheme Name
			Log.log(Log.DEBUG,"RIDAO","setSubSchemeDetails","2 -- " + subSchemeParameters.getSubScheme());

			utilDate = subSchemeParameters.getValidFromDate();
			sqlDate = new java.sql.Date(utilDate.getTime());
			subSchemeStmt.setDate(3,sqlDate);				//Sub Scheme Valid From Date
			Log.log(Log.DEBUG,"RIDAO","setSubSchemeDetails","3 -- " + sqlDate);

			utilDate = subSchemeParameters.getValidToDate();
			Log.log(Log.DEBUG,"RIDAO","setSubSchemeDetails","util valid to date " + utilDate.toString());
			if (utilDate.toString().equals(""))
			{
				subSchemeStmt.setNull(4, java.sql.Types.DATE);				//Sub Scheme Valid To Date
			}
			else
		   {
			sqlDate = new java.sql.Date(utilDate.getTime());
			Log.log(Log.DEBUG,"RIDAO","setSubSchemeDetails","sql valid to date " + sqlDate);
			subSchemeStmt.setDate(4,sqlDate);				//Sub Scheme Valid To Date
		   }
			Log.log(Log.DEBUG,"RIDAO","setSubSchemeDetails","4 -- " + sqlDate);

			subSchemeStmt.setString(5,userId);				// created by user id.

			subSchemeStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			subSchemeStmt.registerOutParameter(6, java.sql.Types.VARCHAR);		//sub scheme id
			subSchemeStmt.registerOutParameter(7, java.sql.Types.VARCHAR);

			subSchemeStmt.execute();
			status=subSchemeStmt.getInt(1);
		   Log.log(Log.DEBUG,"RIDAO","setSubSchemeDetails","value from sp -- " + status);
			if (status==Constants.FUNCTION_FAILURE)
			{
				String err = subSchemeStmt.getString(7);
				subSchemeStmt.close();
				subSchemeStmt = null;
				Log.log(Log.ERROR,"RIDAO","setSubSchemeDetails","exception inserting sub scheme -- " + err);
				throw new DatabaseException(err);
			}

			String subSchemeId = subSchemeStmt.getString(6);

			subSchemeStmt.close();
			Log.log(Log.ERROR,"RIDAO","setSubSchemeDetails","inserting sub scheme state");
			subSchemeStmt = connection.prepareCall("{?=call funcInsertSubSchemeState(?,?,?)}");

			subSchemeStmt.setString(2, subSchemeId);			//sub scheme id
			subSchemeStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			subSchemeStmt.registerOutParameter(4, java.sql.Types.VARCHAR);

			String[] states = subSchemeParameters.getState();
			int statesSize = states.length;
			int i = 0;
			for (i=0;i<statesSize;i++)
			{
				subSchemeStmt.setString(3, states[i]);
				subSchemeStmt.execute();
				status = subSchemeStmt.getInt(1);
				Log.log(Log.ERROR,"RIDAO","setSubSchemeDetails","sub scheme state returned -- " + status);
				if (status==Constants.FUNCTION_FAILURE)
				{
					String err = subSchemeStmt.getString(4);
					subSchemeStmt.close();
					subSchemeStmt = null;
					Log.log(Log.ERROR,"RIDAO","setSubSchemeDetails","exception inserting sub scheme state -- " + err);
					connection.rollback();
					throw new DatabaseException(err);
				}
			}

			subSchemeStmt.close();
			Log.log(Log.ERROR,"RIDAO","setSubSchemeDetails","inserting sub scheme industry");
			subSchemeStmt = connection.prepareCall("{?= call funcInsertSubSchemeIndustry(?,?,?)}");

			subSchemeStmt.setString(2, subSchemeId);			//sub scheme id
			subSchemeStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			subSchemeStmt.registerOutParameter(4, java.sql.Types.VARCHAR);

			String[] industry = subSchemeParameters.getIndustry();
			int indSize = industry.length;
			for (i=0;i<indSize;i++)
			{
				subSchemeStmt.setString(3, industry[i]);
				subSchemeStmt.execute();
				status = subSchemeStmt.getInt(1);
				Log.log(Log.ERROR,"RIDAO","setSubSchemeDetails","sub scheme industry returned -- " + status);
				if (status==Constants.FUNCTION_FAILURE)
				{
					String err = subSchemeStmt.getString(4);
					subSchemeStmt.close();
					subSchemeStmt = null;
					Log.log(Log.ERROR,"RIDAO","setSubSchemeDetails","exception inserting sub scheme industry -- " + err);
					connection.rollback();
					throw new DatabaseException(err);
				}
			}

			subSchemeStmt.close();
			Log.log(Log.ERROR,"RIDAO","setSubSchemeDetails","inserting sub scheme gender");
			subSchemeStmt = connection.prepareCall("{?= call funcInsertSubSchemeGender(?,?,?)}");

			subSchemeStmt.setString(2, subSchemeId);			//sub scheme id
			subSchemeStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			subSchemeStmt.registerOutParameter(4, java.sql.Types.VARCHAR);

			String[] gender = subSchemeParameters.getGender();
			int genderSize = gender.length;
			for (i=0;i<genderSize;i++)
			{
				subSchemeStmt.setString(3, gender[i]);
				subSchemeStmt.execute();
				status = subSchemeStmt.getInt(1);
				Log.log(Log.ERROR,"RIDAO","setSubSchemeDetails","sub scheme gender returned -- " + status);
				if (status==Constants.FUNCTION_FAILURE)
				{
					String err = subSchemeStmt.getString(4);
					subSchemeStmt.close();
					subSchemeStmt = null;
					Log.log(Log.ERROR,"RIDAO","setSubSchemeDetails","exception inserting sub scheme gender -- " + err);
					connection.rollback();
					throw new DatabaseException(err);
				}
			}

			subSchemeStmt.close();
			Log.log(Log.ERROR,"RIDAO","setSubSchemeDetails","inserting sub scheme member");
			subSchemeStmt = connection.prepareCall("{?= call funcInsertSubSchemeMember(?,?,?,?,?)}");

			subSchemeStmt.setString(2, subSchemeId);			//sub scheme id
			subSchemeStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			subSchemeStmt.registerOutParameter(6, java.sql.Types.VARCHAR);

			String[] mli = subSchemeParameters.getMli();
			int mliSize = mli.length;
			for (i=0;i<mliSize;i++)
			{
				String id = mli[i];
				int index=id.indexOf("(");
				String mliBnkId=id.substring(index+1,index+5);
				String mliZneId=id.substring(index+5,index+9);
				String mliBrnId=id.substring(index+9,index+13);
				subSchemeStmt.setString(3, mliBnkId);
				subSchemeStmt.setString(4, mliZneId);
				subSchemeStmt.setString(5, mliBrnId);
				subSchemeStmt.execute();
				status = subSchemeStmt.getInt(1);
				Log.log(Log.ERROR,"RIDAO","setSubSchemeDetails","sub scheme member returned -- " + status);
				if (status==Constants.FUNCTION_FAILURE)
				{
					String err = subSchemeStmt.getString(6);
					subSchemeStmt.close();
					subSchemeStmt = null;
					Log.log(Log.ERROR,"RIDAO","setSubSchemeDetails","exception inserting sub scheme mli -- " + err);
					connection.rollback();
					throw new DatabaseException(err);
				}
			}

			subSchemeStmt.close();
			subSchemeStmt = connection.prepareCall("{?= call funcInsertSubSchemeSocialCat(?,?,?)}");

			subSchemeStmt.setString(2, subSchemeId);			//sub scheme id
			subSchemeStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			subSchemeStmt.registerOutParameter(4, java.sql.Types.VARCHAR);

			String[] socialCat = subSchemeParameters.getSocialCategory();
			int socialCatSize = socialCat.length;
			for (i=0;i<socialCatSize;i++)
			{
				subSchemeStmt.setString(3, socialCat[i]);
				subSchemeStmt.execute();
				status = subSchemeStmt.getInt(1);
				Log.log(Log.ERROR,"RIDAO","setSubSchemeDetails","sub scheme social category returned -- " + status);
				if (status==Constants.FUNCTION_FAILURE)
				{
					String err = subSchemeStmt.getString(4);
					subSchemeStmt.close();
					subSchemeStmt = null;
					Log.log(Log.ERROR,"RIDAO","setSubSchemeDetails","exception inserting sub scheme social category -- " + err);
					connection.rollback();
					throw new DatabaseException(err);
				}
			}

			subSchemeStmt.close();
			subSchemeStmt = null;

		   Log.log(Log.DEBUG,"RIDAO","setSubSchemeDetails","inserted sub scheme parameters");
			subSchemeDetailsStmt=connection.prepareCall("{?= call funcInsertSubSchemeValue (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			subSchemeDetailsStmt.setString(2, subSchemeId);		//Sub Scheme Name
			Log.log(Log.DEBUG,"RIDAO","setSubSchemeDetails","2 -- " + subSchemeParameters.getSubScheme());
			subSchemeDetailsStmt.setInt(3, subSchemeValues.getAppFilingTimeLimit());	//Application Filing Time Limit
			Log.log(Log.DEBUG,"RIDAO","setSubSchemeDetails","3 -- " + subSchemeValues.getAppFilingTimeLimit());
			utilDate=subSchemeValues.getAppFilingTimeLimitValidFrom();
			sqlDate=new java.sql.Date(utilDate.getTime());
			subSchemeDetailsStmt.setDate(4, sqlDate);								//Application Filing Time Limit Valid From
			Log.log(Log.DEBUG,"RIDAO","setSubSchemeDetails","4 -- " + sqlDate);
			utilDate=subSchemeValues.getAppFilingTimeLimitValidTo();
			if (utilDate.toString().equals(""))
			{
				subSchemeDetailsStmt.setNull(5,java.sql.Types.DATE);
			}
			else
			{
				sqlDate=new java.sql.Date(utilDate.getTime());
				subSchemeDetailsStmt.setDate(5, sqlDate);								//Application Filing Time Limit Valid To
				Log.log(Log.DEBUG,"RIDAO","setSubSchemeDetails","5 -- " + sqlDate);
			}
/*			subSchemeDetailsStmt.setDouble(6, subSchemeValues.getMaxGCoverExposure());	//Maximum Guarantee Cover Exposure
			Log.log(Log.DEBUG,"RIDAO","setSubSchemeDetails","6 -- " + subSchemeValues.getMaxGCoverExposure());
			utilDate=subSchemeValues.getMaxGCoverExposureValidFrom();
			sqlDate=new java.sql.Date(utilDate.getTime());
			subSchemeDetailsStmt.setDate(7, sqlDate);								//Maximum Guarantee Cover Exposure Valid From
			Log.log(Log.DEBUG,"RIDAO","setSubSchemeDetails","7 -- " + sqlDate);
			utilDate=subSchemeValues.getMaxGCoverExposureValidTo();
			if (utilDate.toString().equals(""))
			{
				subSchemeDetailsStmt.setNull(8,java.sql.Types.DATE);
			}
			else
			{
				sqlDate=new java.sql.Date(utilDate.getTime());
				subSchemeDetailsStmt.setDate(8, sqlDate);								//Maximum Guarantee Cover Exposure Valid To
				Log.log(Log.DEBUG,"RIDAO","setSubSchemeDetails","8 -- " + sqlDate);
			}
			subSchemeDetailsStmt.setDouble(9, subSchemeValues.getMinSanctionedAmount());	//Minimum Sanctioned Amount
			Log.log(Log.DEBUG,"RIDAO","setSubSchemeDetails","9 -- " + subSchemeValues.getMinSanctionedAmount());
			utilDate=subSchemeValues.getMinSanctionedAmtValidFrom();
			sqlDate=new java.sql.Date(utilDate.getTime());
			subSchemeDetailsStmt.setDate(10, sqlDate);								//Minimum Sanctioned Amount Valid From
			Log.log(Log.DEBUG,"RIDAO","setSubSchemeDetails","10 -- " + sqlDate);
			utilDate=subSchemeValues.getMinSanctionedAmtValidTo();
			if (utilDate.toString().equals(""))
			{
				subSchemeDetailsStmt.setNull(11, java.sql.Types.DATE);
			}
			else
			{
				sqlDate=new java.sql.Date(utilDate.getTime());
				subSchemeDetailsStmt.setDate(11, sqlDate);								//Minimum Sanctioned Amount Valid To
				Log.log(Log.DEBUG,"RIDAO","setSubSchemeDetails","11 -- " + sqlDate);
			}
			subSchemeDetailsStmt.setDouble(12, subSchemeValues.getMaxSanctionedAmount());	//Maximum Sanctioned Amount
			Log.log(Log.DEBUG,"RIDAO","setSubSchemeDetails","12 -- " + subSchemeValues.getMaxSanctionedAmount());
			utilDate=subSchemeValues.getMaxSanctionedAmtValidFrom();
			sqlDate=new java.sql.Date(utilDate.getTime());
			subSchemeDetailsStmt.setDate(13, sqlDate);								//Maximum Sanctioned Amount Valid From
			Log.log(Log.DEBUG,"RIDAO","setSubSchemeDetails","13 -- " + sqlDate);
			utilDate=subSchemeValues.getMaxSanctionedAmtValidTo();
			if (utilDate.toString().equals(""))
			{
				subSchemeDetailsStmt.setNull(14, java.sql.Types.DATE);
			}
			else
			{			
				sqlDate=new java.sql.Date(utilDate.getTime());
				subSchemeDetailsStmt.setDate(14, sqlDate);								//Maximum Sanctioned Amount Valid To
				Log.log(Log.DEBUG,"RIDAO","setSubSchemeDetails","14 -- " + sqlDate);
			}*/
			subSchemeDetailsStmt.setNull(6, java.sql.Types.DOUBLE);	//Maximum Guarantee Cover Exposure
			subSchemeDetailsStmt.setNull(7, java.sql.Types.DATE);								//Maximum Guarantee Cover Exposure Valid From
			subSchemeDetailsStmt.setNull(8,java.sql.Types.DATE);
			subSchemeDetailsStmt.setNull(9, java.sql.Types.DOUBLE);	//Minimum Sanctioned Amount
			subSchemeDetailsStmt.setNull(10,java.sql.Types.DATE);								//Minimum Sanctioned Amount Valid From
			subSchemeDetailsStmt.setNull(11, java.sql.Types.DATE);
			subSchemeDetailsStmt.setNull(12, java.sql.Types.DOUBLE);	//Maximum Sanctioned Amount
			subSchemeDetailsStmt.setNull(13, java.sql.Types.DATE);
			subSchemeDetailsStmt.setNull(14, java.sql.Types.DATE);
			subSchemeDetailsStmt.setDouble(15, subSchemeValues.getMaxBorrowerExposureAmount());	//Maximum Borrower Exposure Amount
			Log.log(Log.DEBUG,"RIDAO","setSubSchemeDetails","15 -- " + subSchemeValues.getMaxBorrowerExposureAmount());
			utilDate=subSchemeValues.getMaxBorrowerExpAmtValidFrom();
			sqlDate=new java.sql.Date(utilDate.getTime());
			subSchemeDetailsStmt.setDate(16, sqlDate);								//Maximum Borrower Exposure Amount Valid From
			Log.log(Log.DEBUG,"RIDAO","setSubSchemeDetails","16 -- " + sqlDate);
			utilDate=subSchemeValues.getMaxBorrowerExpAmtValidTo();
			if (utilDate.toString().equals(""))
			{
				subSchemeDetailsStmt.setNull(17, java.sql.Types.DATE);
			}
			else
			{			
				sqlDate=new java.sql.Date(utilDate.getTime());
				subSchemeDetailsStmt.setDate(17, sqlDate);								//Maximum Borrower Exposure Amount Valid To
				Log.log(Log.DEBUG,"RIDAO","setSubSchemeDetails","17 -- " + sqlDate);
			}
/*			subSchemeDetailsStmt.setDouble(18, subSchemeValues.getGuaranteeCoverExtent());	//Gurantee Cover Extent
			Log.log(Log.DEBUG,"RIDAO","setSubSchemeDetails","18 -- " + subSchemeValues.getGuaranteeCoverExtent());
			utilDate=subSchemeValues.getGuaranteeCoverExtentValidFrom();
			sqlDate=new java.sql.Date(utilDate.getTime());
			subSchemeDetailsStmt.setDate(19, sqlDate);								//Guarantee Cover Extent Valid From
			Log.log(Log.DEBUG,"RIDAO","setSubSchemeDetails","19 -- " + sqlDate);
			utilDate=subSchemeValues.getGuaranteeCoverExtentValidTo();
			if (utilDate.toString().equals(""))
			{
				subSchemeDetailsStmt.setNull(20, java.sql.Types.DATE);
			}
			else
			{			
				sqlDate=new java.sql.Date(utilDate.getTime());
				subSchemeDetailsStmt.setDate(20, sqlDate);								//Guarantee Cover Extent Valid To
				Log.log(Log.DEBUG,"RIDAO","setSubSchemeDetails","20 -- " + sqlDate);
			}*/
			subSchemeDetailsStmt.setNull(18, java.sql.Types.DOUBLE);
			subSchemeDetailsStmt.setNull(19, java.sql.Types.DATE);
			subSchemeDetailsStmt.setNull(20, java.sql.Types.DATE);
			subSchemeDetailsStmt.setDouble(21, subSchemeValues.getServiceFeeCardRate());	//Service Fee Card Rate
			Log.log(Log.DEBUG,"RIDAO","setSubSchemeDetails","21 -- " + subSchemeValues.getServiceFeeCardRate());
			utilDate=subSchemeValues.getServiceFeeCardRateValidFrom();
			sqlDate=new java.sql.Date(utilDate.getTime());
			subSchemeDetailsStmt.setDate(22, sqlDate);								//Service Fee Card Rate Valid From
			Log.log(Log.DEBUG,"RIDAO","setSubSchemeDetails","22 -- " + sqlDate);
			utilDate=subSchemeValues.getServiceFeeCardRateValidTo();
			if (utilDate.toString().equals(""))
			{
				subSchemeDetailsStmt.setNull(23, java.sql.Types.DATE);
			}
			else
			{			
				sqlDate=new java.sql.Date(utilDate.getTime());
				subSchemeDetailsStmt.setDate(23, sqlDate);								//Service Fee Card Rate Valid To
				Log.log(Log.DEBUG,"RIDAO","setSubSchemeDetails","23 -- " + sqlDate);
			}
/*			subSchemeDetailsStmt.setDouble(24, subSchemeValues.getGuaranteeFeeCardRate());	//Guarantee Fee Card Rate
			Log.log(Log.DEBUG,"RIDAO","setSubSchemeDetails","24 -- " + subSchemeValues.getGuaranteeFeeCardRate());
			utilDate=subSchemeValues.getGuaranteeFeeCardRateValidFrom();
			sqlDate=new java.sql.Date(utilDate.getTime());
			subSchemeDetailsStmt.setDate(25, sqlDate);								//Guarantee Fee Card Rate Valid From
			Log.log(Log.DEBUG,"RIDAO","setSubSchemeDetails","25 -- " + sqlDate);
			utilDate=subSchemeValues.getGuaranteeFeeCardRateValidTo();
			if (utilDate.toString().equals(""))
			{
				subSchemeDetailsStmt.setNull(26, java.sql.Types.DATE);
			}
			else
			{			
				sqlDate=new java.sql.Date(utilDate.getTime());
				subSchemeDetailsStmt.setDate(26, sqlDate);								//Guarantee Fee Card Rate Valid To
				Log.log(Log.DEBUG,"RIDAO","setSubSchemeDetails","26 -- " + sqlDate);
			}*/

			subSchemeDetailsStmt.setNull(24, java.sql.Types.DOUBLE);	//Guarantee Fee Card Rate
			subSchemeDetailsStmt.setNull(25, java.sql.Types.DATE);								//Guarantee Fee Card Rate Valid From
			subSchemeDetailsStmt.setNull(26, java.sql.Types.DATE);
			
			if (subSchemeValues.getDefaultRateApplicable().equals("Y"))
			{
				subSchemeDetailsStmt.setDouble(27, subSchemeValues.getDefaultRate());	//Default Rate
				Log.log(Log.DEBUG,"RIDAO","setSubSchemeDetails","27 -- " + subSchemeValues.getDefaultRate());
				utilDate=subSchemeValues.getDefRateValidFrom();
				sqlDate=new java.sql.Date(utilDate.getTime());
				subSchemeDetailsStmt.setDate(28, sqlDate);								//Default Rate Valid From
				Log.log(Log.DEBUG,"RIDAO","setSubSchemeDetails","28 -- " + sqlDate);
				utilDate=subSchemeValues.getDefRateValidTo();
				if (utilDate.toString().equals(""))
				{
					subSchemeDetailsStmt.setNull(29, java.sql.Types.DATE);
				}
				else
				{				
					sqlDate=new java.sql.Date(utilDate.getTime());
					subSchemeDetailsStmt.setDate(29, sqlDate);								//Default Rate Valid To
					Log.log(Log.DEBUG,"RIDAO","setSubSchemeDetails","29 -- " + sqlDate);
				}				
			}
			else
			{
				subSchemeDetailsStmt.setNull(27, java.sql.Types.DOUBLE);	//Default Rate
				Log.log(Log.DEBUG,"RIDAO","setSubSchemeDetails","27 -- null");
				subSchemeDetailsStmt.setNull(28, java.sql.Types.DATE);								//Default Rate Valid From
				Log.log(Log.DEBUG,"RIDAO","setSubSchemeDetails","28 -- null");
				subSchemeDetailsStmt.setNull(29, java.sql.Types.DATE);								//Default Rate Valid To
				Log.log(Log.DEBUG,"RIDAO","setSubSchemeDetails","29 -- null");
			}
			subSchemeDetailsStmt.setString(30, subSchemeValues.getDefaultRateApplicable());	//Default Rate Applicable
			Log.log(Log.DEBUG,"RIDAO","setSubSchemeDetails","30 -- " + subSchemeValues.getDefaultRateApplicable());
			utilDate=subSchemeValues.getDefRateApplicableValidFrom();
			sqlDate=new java.sql.Date(utilDate.getTime());
			subSchemeDetailsStmt.setDate(31, sqlDate);								//Default Rate Applicable Valid From
			Log.log(Log.DEBUG,"RIDAO","setSubSchemeDetails","31 -- " + sqlDate);
			utilDate=subSchemeValues.getDefRateApplicableValidTo();
			if (utilDate.toString().equals(""))
			{
				subSchemeDetailsStmt.setNull(32, java.sql.Types.DATE);
			}
			else
			{			
				sqlDate=new java.sql.Date(utilDate.getTime());
				subSchemeDetailsStmt.setDate(32, sqlDate);								//Default Rate Applicable Valid To
				Log.log(Log.DEBUG,"RIDAO","setSubSchemeDetails","32 -- " + sqlDate);
			}
			subSchemeDetailsStmt.setDouble(33, subSchemeValues.getModerationFactor());	//Moderation Factor
			Log.log(Log.DEBUG,"RIDAO","setSubSchemeDetails","33 -- " + subSchemeValues.getModerationFactor());
			utilDate=subSchemeValues.getModerationFactorValidFrom();
			sqlDate=new java.sql.Date(utilDate.getTime());
			subSchemeDetailsStmt.setDate(34, sqlDate);								//Moderation Factor Valid From
			Log.log(Log.DEBUG,"RIDAO","setSubSchemeDetails","34 -- " + sqlDate);
			utilDate=subSchemeValues.getModerationFactorValidTo();
			if (utilDate.toString().equals(""))
			{
				subSchemeDetailsStmt.setNull(35, java.sql.Types.DATE);
			}
			else
			{			
				sqlDate=new java.sql.Date(utilDate.getTime());
				subSchemeDetailsStmt.setDate(35, sqlDate);								//Moderation Factor Valid To
				Log.log(Log.DEBUG,"RIDAO","setSubSchemeDetails","35 -- " + sqlDate);
			}

			subSchemeDetailsStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			subSchemeDetailsStmt.registerOutParameter(36, java.sql.Types.VARCHAR);

			subSchemeDetailsStmt.execute();
			status=subSchemeDetailsStmt.getInt(1);
			Log.log(Log.DEBUG,"RIDAO","setSubSchemeDetails","value from insert sub scheme details sp -- " + status);
			if (status==Constants.FUNCTION_FAILURE)
			{
				String err = subSchemeDetailsStmt.getString(36);
				Log.log(Log.ERROR,"RIDAO","setSubSchemeDetails","exception from sp -- " + err);		
				connection.rollback();				//roll back the sub scheme parameter insert.
				subSchemeDetailsStmt.close();
				subSchemeDetailsStmt=null;
				throw new DatabaseException(err);
			}
			connection.commit();				//commit both sub scheme parameter and subscheme values.
			subSchemeDetailsStmt.close();
			subSchemeDetailsStmt=null;
	   }
	   catch (SQLException sqlException)
		{
			Log.log(Log.ERROR,"RIDAO","setSubSchemeDetails",sqlException.getMessage());
			Log.logException(sqlException);
		   throw new DatabaseException(sqlException.getMessage());
		}
	   finally
	   {
		   DBConnection.freeConnection(connection);
	   }
	   subSchemeParameters=null;
	   subSchemeValues=null;
	   Log.log(Log.INFO,"RIDAO","setSubSchemeDetails","Exited");
	}

   /**
	* This method retireves all the Schemes from the Scheme Master table.
	*
	* @return ArrayList
	*/
   public ArrayList getValuesFromSchemeMaster() throws DatabaseException
   {
		Log.log(Log.INFO,"RIDAO","getValuesFromSchemeMaster","Entered");

	   ArrayList schemes=new ArrayList();
	   Connection connection;
	   CallableStatement getSchemesStmt;
	   int getSchemesStatus=0;
	   ResultSet rsSchemes=null;
	   String getSchemesErr="";

			connection=DBConnection.getConnection();
	   try
	   {
			getSchemesStmt=connection.prepareCall("{?=call packGetAllSchemes.funcGetSchemes(?,?)}");

			getSchemesStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			getSchemesStmt.registerOutParameter(2, Constants.CURSOR);
			getSchemesStmt.registerOutParameter(3, java.sql.Types.VARCHAR);
			getSchemesStmt.execute();

			getSchemesStatus=getSchemesStmt.getInt(1);
			Log.log(Log.DEBUG,"RIDAO","getValuesFromSchemeMaster","value returned from sp -- " + getSchemesStatus);
			if (getSchemesStatus==0)
			{
				rsSchemes=(ResultSet) getSchemesStmt.getObject(2);
				while (rsSchemes.next())
				{
					schemes.add(rsSchemes.getString(1));
				}
				Log.log(Log.DEBUG,"RIDAO","getValuesFromSchemeMaster","schemes list -- " + schemes);
				rsSchemes.close();
				rsSchemes=null;
				getSchemesStmt.close();
				getSchemesStmt=null;
			}
			else
		   {
				getSchemesErr=getSchemesStmt.getString(3);
				Log.log(Log.DEBUG,"RIDAO","getValuesFromSchemeMaster","error from sp -- " + getSchemesErr);
				getSchemesStmt.close();
				getSchemesStmt=null;
				throw new DatabaseException(getSchemesErr);
		   }
	   }
	   catch (SQLException sqlException)
	   {
			Log.log(Log.ERROR,"RIDAO","getValuesFromSchemeMaster",sqlException.getMessage());
			Log.logException(sqlException);
		   throw new DatabaseException(sqlException.getMessage());
	   }
	   finally
	   {
		   DBConnection.freeConnection(connection);
	   }
		Log.log(Log.INFO,"RIDAO","getValuesFromSchemeMaster","Exited");
	   return schemes;
   }
   
   /**
	* This method gets the sub scheme name for the given sub scheme parameters
	* @return String
	*
	public String getSubSchemeName(SubSchemeParameters subSchemeParameters) throws DatabaseException
	{
		Log.log(Log.INFO,"RIDAO","getSubSchemeName","Entered");
		Connection connection;
		CallableStatement sshNameStmt;
		int status=-1;
		String subSchemeName="";

			connection=DBConnection.getConnection();
		try
		{
			sshNameStmt=connection.prepareCall("{?=call funcGetSubSchemeName(?,?,?,?,?,?,?,?,?)}");

			if (!(subSchemeParameters.getState()).equals(""))
			{
				sshNameStmt.setString(3, subSchemeParameters.getState());			//State Name
				Log.log(Log.DEBUG,"RIDAO","getSubSchemeName","3 -- " + subSchemeParameters.getState());
			}
			else
			{
				sshNameStmt.setString(3, "0");			//State Name
				Log.log(Log.DEBUG,"RIDAO","getSubSchemeName","3 -- 0");
			}

			String mliName=subSchemeParameters.getMli();
			String mliShortName="0";
			String mliBnkId="0";
			String mliBrnId="0";
			String mliZneId="0";
			if (!mliName.equals(""))
			{
				int index=mliName.indexOf("(");
				mliShortName=mliName.substring(0,index-1);
				mliBnkId=mliName.substring(index+1,index+5);
				mliZneId=mliName.substring(index+5,index+9);
				mliBrnId=mliName.substring(index+9,index+13);
			}
			sshNameStmt.setString(4, mliBnkId);									//Member bank id
			Log.log(Log.DEBUG,"RIDAO","getSubSchemeName","4 -- " + mliBnkId);
			sshNameStmt.setString(5, mliZneId);									//Member zone id
			Log.log(Log.DEBUG,"RIDAO","getSubSchemeName","5 -- " + mliZneId);
			sshNameStmt.setString(6, mliBrnId);									//Member branch id
			Log.log(Log.DEBUG,"RIDAO","getSubSchemeName","6 -- " + mliBrnId);

			if (!(subSchemeParameters.getIndustry()).equals(""))
			{
				sshNameStmt.setString(7, subSchemeParameters.getIndustry());		//Industry Name
				Log.log(Log.DEBUG,"RIDAO","getSubSchemeName","7 -- " + subSchemeParameters.getIndustry());
			}
			else
			{
				sshNameStmt.setString(7, "0");										//Industry Name
				Log.log(Log.DEBUG,"RIDAO","getSubSchemeName","7 -- 0");
			}

			if (!(subSchemeParameters.getGender()).equals(""))
			{
				sshNameStmt.setString(8, subSchemeParameters.getGender());			//Gender
				Log.log(Log.DEBUG,"RIDAO","getSubSchemeName","8 -- " + subSchemeParameters.getGender());
			}
			else
			{
				sshNameStmt.setString(8, "0");			//Gender
				Log.log(Log.DEBUG,"RIDAO","getSubSchemeName","8 -- 0");
			}

			if (!(subSchemeParameters.getSocialCategory()).equals(""))
			{
				sshNameStmt.setString(9, subSchemeParameters.getSocialCategory());	//Social Category
				Log.log(Log.DEBUG,"RIDAO","getSubSchemeName","8 -- " + subSchemeParameters.getSocialCategory());
			}
			else
			{
				sshNameStmt.setString(9, "0");	//Social Category
				Log.log(Log.DEBUG,"RIDAO","getSubSchemeName","8 -- 0");
			}


			sshNameStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			sshNameStmt.registerOutParameter(2, java.sql.Types.VARCHAR);		//Sub Scheme Name
			sshNameStmt.registerOutParameter(10, java.sql.Types.VARCHAR);		//Error Description

			sshNameStmt.execute();
			status=sshNameStmt.getInt(1);

			Log.log(Log.DEBUG,"RIDAO","getSubSchemeName","Value returned from sp -- " + status);
			if (status==Constants.FUNCTION_FAILURE)
			{
				String err=sshNameStmt.getString(10);
				Log.log(Log.DEBUG,"RIDAO","getSubSchemeName","exception from sp -- " + err);
				sshNameStmt.close();
				throw new DatabaseException(err);
			}
			subSchemeName=sshNameStmt.getString(2);
			sshNameStmt.close();
		}
		catch (SQLException sqlException)
		{
			Log.log(Log.ERROR,"RIDAO","getSubSchemeName",sqlException.getMessage());
			Log.logException(sqlException);
			 throw new DatabaseException(sqlException.getMessage());
		}
	   finally
	   {
		   DBConnection.freeConnection(connection);
	   }

		Log.log(Log.INFO,"RIDAO","getSubSchemeName","Exited");
		return subSchemeName;
	}

	/**
	 * This method calculates the exposure details for the given parameters.
	 * The exposure summary object passed contains the parameters for which the exposure details
	 * are retrieved.
	 * The no of applications and the total amount for each MLI for facility - 
	 * Term Loan and Working Capital is retrieved.
	 * @returns ExposureSummary
	 */
	 public ExposureSummary getExposureDetails(ExposureSummary exposureSummary) throws DatabaseException
	 {
		Log.log(Log.INFO,"RIDAO","getExposureDetails","Entered");

		 Connection connection;
		 PreparedStatement exposureStmt;
		 ResultSet exposureDetails;
		 int status=-1;

		 ExposureDetails expDetails=new ExposureDetails();
		 ArrayList tcDetails=new ArrayList();
		 ArrayList wcDetails=new ArrayList();

		 connection=DBConnection.getConnection();
		 try
		 {
			 String scheme = exposureSummary.getScheme();

			 String[] states = exposureSummary.getState();
			 String[] mlis = exposureSummary.getMli();
			 String[] ind = exposureSummary.getIndustry();
			 String[] gen = exposureSummary.getGender();
			 String[] sc = exposureSummary.getSocialCategory();

			 StringBuffer query=new StringBuffer(2000);
			 StringBuffer tempBuffer=new StringBuffer(100);
			 int size = states.length;
			 for (int i=0;i<size;i++)
			 {
				 if (size-1==i)
				 {
					 tempBuffer.append(" ?");
				 }
				 else
				 {
					 tempBuffer.append(" ?,");
				 }
			 }
			 String statesQuery=tempBuffer.toString();
			 Log.log(Log.INFO,"RIDAO","getExposureDetails","state query " + statesQuery);

			 tempBuffer.setLength(0);
			 size = ind.length;
			 for (int i=0;i<size;i++)
			 {
				 if (size-1==i)
				 {
					 tempBuffer.append(" ?");
				 }
				 else
				 {
					 tempBuffer.append(" ?,");
				 }
			 }
			 String indQuery = tempBuffer.toString();
			 Log.log(Log.INFO,"RIDAO","getExposureDetails","industry query " + indQuery);

			 tempBuffer.setLength(0);
			 size = gen.length;
			 for (int i=0;i<size;i++)
			 {
				 if (size-1==i)
				 {
					 tempBuffer.append(" ?");
				 }
				 else
				 {
					 tempBuffer.append(" ?,");
				 }
			 }
			 String genderQuery = tempBuffer.toString();
			 Log.log(Log.INFO,"RIDAO","getExposureDetails","gender query " + genderQuery);

			 tempBuffer.setLength(0);
			 size = sc.length;
			 for (int i=0;i<size;i++)
			 {
				 if (size-1==i)
				 {
					 tempBuffer.append(" ?");
				 }
				 else
				 {
					 tempBuffer.append(" ?,");
				 }
			 }
			 String scQuery = tempBuffer.toString();
			 Log.log(Log.INFO,"RIDAO","getExposureDetails","social category query " + scQuery);

			 tempBuffer.setLength(0);
			 size = mlis.length;
			 for (int i=0;i<size;i++)
			 {
				 if (size-1==i)
				 {
					 tempBuffer.append(" ?");
				 }
				 else
				 {
					 tempBuffer.append(" ?,");
				 }
			 }
			 String mliQuery = tempBuffer.toString();
			 tempBuffer.setLength(0);
			 tempBuffer=null;

			 Log.log(Log.INFO,"RIDAO","getExposureDetails","mli query " + mliQuery);

			 query.append("select app.MEM_BNK_ID||app.MEM_ZNE_ID||app.MEM_BRN_ID, ");
			 query.append("app.APP_LOAN_TYPE, sum(nvl(app.APP_REAPPROVE_AMOUNT, app.APP_APPROVED_AMOUNT)), count(app.CGPAN), 1 ");
			 query.append("from application_detail app, scheme_master scm, ssi_detail ssi, ");
			 query.append("promoter_detail prd ");
			 //query.append("where app.APP_GUAR_START_DATE_TIME is null ");
			 query.append("where app.SCM_ID = scm.SCM_ID ");
			 query.append("and LTRIM(RTRIM(UPPER(scm.SCM_NAME))) = LTRIM(RTRIM(UPPER(?))) ");
			 query.append("and app.SSI_REFERENCE_NUMBER = ssi.SSI_REFERENCE_NUMBER ");
			 query.append("and ssi.SSI_REFERENCE_NUMBER = prd.SSI_REFERENCE_NUMBER ");
			 if (! states[0].equalsIgnoreCase("ALL"))
			 {
				query.append("and ssi.SSI_STATE_NAME IN (");
				query.append(statesQuery);
				query.append(") ");
			 }
			 if (! ind[0].equalsIgnoreCase("ALL"))
			 {
				query.append("and ssi.SSI_INDUSTRY_NATURE IN (");
				query.append(indQuery);
				query.append(") ");
			 }
			 if (!gen[0].equalsIgnoreCase("A"))
			 {
				query.append("and prd.PMR_CHIEF_GENDER IN (");
				query.append(genderQuery);
				query.append(") ");
			 }
			 if (! sc[0].equalsIgnoreCase("ALL"))
			 {
				query.append("and prd.PMR_CHIEF_SOCIAL_CAT IN (");
				query.append(scQuery);
				query.append(") ");
			 }
			 if (! mlis[0].equalsIgnoreCase("ALL"))
			 {
				query.append("and LTRIM(RTRIM(app.MEM_BNK_ID)) IN (");
				query.append(mliQuery);
				query.append(") ");
			 }
			 query.append("group by app.APP_LOAN_TYPE, app.MEM_BNK_ID||app.MEM_ZNE_ID||app.MEM_BRN_ID");

			 String strQuery = query.toString();

			exposureStmt = connection.prepareStatement(strQuery);
			exposureStmt.setString(1, scheme);
			Log.log(Log.INFO,"RIDAO","getExposureDetails","index , value  -- 1 " + scheme);
			int index=2;
			if (! states[0].equalsIgnoreCase("ALL"))
			{
			size = states.length;
			for (int i=0;i<size;i++)
			{
				exposureStmt.setString(index, states[i]);
				Log.log(Log.INFO,"RIDAO","getExposureDetails","index , value  -- " + index + states[i]);
				index++;
			}
			}
			if (! ind[0].equalsIgnoreCase("ALL"))
			{			
			size = ind.length;
			for (int i=0;i<size;i++)
			{
				exposureStmt.setString(index, ind[i]);
				Log.log(Log.INFO,"RIDAO","getExposureDetails","index , value  -- " + index + ind[i]);
				index++;
			}
			}
			if (! gen[0].equalsIgnoreCase("A"))
			{			
			size = gen.length;
			for (int i=0;i<size;i++)
			{
				exposureStmt.setString(index, gen[i]);
				Log.log(Log.INFO,"RIDAO","getExposureDetails","index , value  -- " + index + gen[i]);
				index++;
			}
			}
			if (! sc[0].equalsIgnoreCase("ALL"))
			{			
			size = sc.length;
			for (int i=0;i<size;i++)
			{
				exposureStmt.setString(index, sc[i]);
				Log.log(Log.INFO,"RIDAO","getExposureDetails","index , value  -- " + index + sc[i]);
				index++;
			}
			}
			if (! mlis[0].equalsIgnoreCase("ALL"))
			{			
			size = mlis.length;
			for (int i=0;i<size;i++)
			{
				String tempMli = mlis[i].substring( mlis[i].indexOf("(")+1, mlis[i].length()-1 );
				tempMli = tempMli.substring(0, 4);
				exposureStmt.setString(index, tempMli);
				Log.log(Log.INFO,"RIDAO","getExposureDetails","index , value  -- " + index + mlis[i].substring( mlis[i].indexOf("(")+1, mlis[i].length()-1 ));
				index++;
			}
			}

			exposureDetails = exposureStmt.executeQuery();

			// approved details
			while (exposureDetails.next())
			{
				expDetails = new ExposureDetails();

				String mem = exposureDetails.getString(1);
				String loanType = exposureDetails.getString(2);
				double amount = exposureDetails.getDouble(3);
				int count = exposureDetails.getInt(4);

				if (count != 0 && amount != 0.0)
				{
					expDetails.setMemberId(mem);
					expDetails.setAppAmount(amount);
					expDetails.setAppCount(count);
					if (loanType.equals("TC") || loanType.equals("CC"))
					{
						Log.log(Log.DEBUG,"RIDAO","getExposureSummary","tc approved amount " + expDetails.getAppAmount());
						Log.log(Log.DEBUG,"RIDAO","getExposureSummary","tc approved count " + expDetails.getAppCount());
						tcDetails.add(expDetails);
					}
					else if (loanType.equals("WC"))
					{
						Log.log(Log.DEBUG,"RIDAO","getExposureSummary","wc approved amount " + expDetails.getAppAmount());
						Log.log(Log.DEBUG,"RIDAO","getExposureSummary","wc approved count " + expDetails.getAppCount());
						wcDetails.add(expDetails);
					}
				}

				expDetails=null;
			}

			exposureSummary.setTcDetails(tcDetails);
			exposureSummary.setWcDetails(wcDetails);

//			tcDetails.clear();
			tcDetails=null;
//			wcDetails.clear();
			wcDetails=null;

			exposureDetails.close();
			exposureStmt.close();
			query.setLength(0);

			 query.append("select app.MEM_BNK_ID||app.MEM_ZNE_ID||app.MEM_BRN_ID, ");
			 query.append("app.APP_LOAN_TYPE, sum(nvl(app.APP_REAPPROVE_AMOUNT,app.APP_APPROVED_AMOUNT)), count(app.CGPAN), 1 ");
			 query.append("from application_detail app, scheme_master scm, ssi_detail ssi, ");
			 query.append("promoter_detail prd ");
			 query.append("where app.APP_GUAR_START_DATE_TIME is not null ");
			 query.append("and app.SCM_ID = scm.SCM_ID ");
			 query.append("and LTRIM(RTRIM(UPPER(scm.SCM_NAME))) = LTRIM(RTRIM(UPPER(?))) ");
			 query.append("and app.SSI_REFERENCE_NUMBER = ssi.SSI_REFERENCE_NUMBER ");
			 query.append("and ssi.SSI_REFERENCE_NUMBER = prd.SSI_REFERENCE_NUMBER ");
			 if (! states[0].equalsIgnoreCase("ALL"))
			 {
				query.append("and ssi.SSI_STATE_NAME IN (");
				query.append(statesQuery);
				query.append(") ");
			 }
			 if (! ind[0].equalsIgnoreCase("ALL"))
			 {
				query.append("and ssi.SSI_INDUSTRY_NATURE IN (");
				query.append(indQuery);
				query.append(") ");
			 }
			 if (!gen[0].equalsIgnoreCase("A"))
			 {
				query.append("and prd.PMR_CHIEF_GENDER IN (");
				query.append(genderQuery);
				query.append(") ");
			 }
			 if (! sc[0].equalsIgnoreCase("ALL"))
			 {
				query.append("and prd.PMR_CHIEF_SOCIAL_CAT IN (");
				query.append(scQuery);
				query.append(") ");
			 }
			 if (! mlis[0].equalsIgnoreCase("ALL"))
			 {
				query.append("and LTRIM(RTRIM(app.MEM_BNK_ID)) IN (");
				query.append(mliQuery);
				query.append(") ");
			 }
			 query.append("group by app.APP_LOAN_TYPE, app.MEM_BNK_ID||app.MEM_ZNE_ID||app.MEM_BRN_ID");

			 strQuery = query.toString();

			 query.setLength(0);
			 query=null;

			exposureStmt = connection.prepareStatement(strQuery);
			exposureStmt.setString(1, scheme);
			Log.log(Log.INFO,"RIDAO","getExposureDetails","index , value  -- 1 " + scheme);
			index=2;
			if (! states[0].equalsIgnoreCase("ALL"))
			{
			size = states.length;
			for (int i=0;i<size;i++)
			{
				exposureStmt.setString(index, states[i]);
				Log.log(Log.INFO,"RIDAO","getExposureDetails","index , value  -- " + index + states[i]);
				index++;
			}
			}
			if (! ind[0].equalsIgnoreCase("ALL"))
			{			
			size = ind.length;
			for (int i=0;i<size;i++)
			{
				exposureStmt.setString(index, ind[i]);
				Log.log(Log.INFO,"RIDAO","getExposureDetails","index , value  -- " + index + ind[i]);
				index++;
			}
			}
			if (! gen[0].equalsIgnoreCase("A"))
			{			
			size = gen.length;
			for (int i=0;i<size;i++)
			{
				exposureStmt.setString(index, gen[i]);
				Log.log(Log.INFO,"RIDAO","getExposureDetails","index , value  -- " + index + gen[i]);
				index++;
			}
			}
			if (! sc[0].equalsIgnoreCase("ALL"))
			{			
			size = sc.length;
			for (int i=0;i<size;i++)
			{
				exposureStmt.setString(index, sc[i]);
				Log.log(Log.INFO,"RIDAO","getExposureDetails","index , value  -- " + index + sc[i]);
				index++;
			}
			}
			if (! mlis[0].equalsIgnoreCase("ALL"))
			{			
			size = mlis.length;
			for (int i=0;i<size;i++)
			{
				String tempMli = mlis[i].substring( mlis[i].indexOf("(")+1, mlis[i].length()-1 );
				tempMli = tempMli.substring(0, 4);
				exposureStmt.setString(index, tempMli);
				Log.log(Log.INFO,"RIDAO","getExposureDetails","index , value  -- " + index + mlis[i].substring( mlis[i].indexOf("(")+1, mlis[i].length()-1 ));
				index++;
			}
			}

			exposureDetails = exposureStmt.executeQuery();

			ArrayList tcDetailsArr = new ArrayList();
			tcDetailsArr = exposureSummary.getTcDetails();
			ArrayList wcDetailsArr = new ArrayList();
			wcDetailsArr = exposureSummary.getWcDetails();

			while (exposureDetails.next())
			{
				String mem = exposureDetails.getString(1);
				String loanType = exposureDetails.getString(2);
				double amount = exposureDetails.getDouble(3);
				int count = exposureDetails.getInt(4);

				if (loanType.equals("TC") || loanType.equals("CC"))
				{
					int tcSize = 0;
					int tcIndex=0;
					ExposureDetails tcExpDetails = new ExposureDetails();
					tcSize = tcDetailsArr.size();
					for (tcIndex=0;tcIndex<tcSize;tcIndex++)
					{
						tcExpDetails = (ExposureDetails) tcDetailsArr.get(tcIndex);
						if (tcExpDetails.getMemberId().equals(mem))
						{
							tcExpDetails.setIssuedAmount(amount);
							tcExpDetails.setIssuedCount(count);
							tcDetailsArr.set(tcIndex, tcExpDetails);
							Log.log(Log.DEBUG,"RIDAO","getExposureSummary","tc approved amount " + tcExpDetails.getAppAmount());
							Log.log(Log.DEBUG,"RIDAO","getExposureSummary","tc approved count " + tcExpDetails.getAppCount());
						}
					}
					tcExpDetails=null;
				}
				else if (loanType.equals("WC"))
				{
					int wcSize = 0;
					int wcIndex=0;
					ExposureDetails wcExpDetails = new ExposureDetails();
					wcSize = wcDetailsArr.size();
					for (wcIndex=0;wcIndex<wcSize;wcIndex++)
					{
						wcExpDetails = (ExposureDetails) wcDetailsArr.get(wcIndex);
						if (wcExpDetails.getMemberId().equals(mem))
						{
							wcExpDetails.setIssuedAmount(amount);
							wcExpDetails.setIssuedCount(count);
							wcDetailsArr.set(wcIndex, wcExpDetails);
							Log.log(Log.DEBUG,"RIDAO","getExposureSummary","wc approved amount " + wcExpDetails.getAppAmount());
							Log.log(Log.DEBUG,"RIDAO","getExposureSummary","wc approved count " + wcExpDetails.getAppCount());
						}
					}
					wcExpDetails=null;
				}
			}

			exposureDetails.close();
			exposureDetails=null;
			exposureStmt.close();
			exposureStmt=null;

			exposureSummary.setTcDetails(tcDetailsArr);
			exposureSummary.setWcDetails(wcDetailsArr);

//			tcDetailsArr.clear();
			tcDetailsArr=null;
//			wcDetailsArr.clear();
			wcDetailsArr=null;
		 }
		 catch (SQLException sqlException)
		{
			Log.log(Log.ERROR,"RIDAO","getExposureDetails",sqlException.getMessage());
			Log.logException(sqlException);
			 throw new DatabaseException(sqlException.getMessage());
		}
	   finally
	   {
		   DBConnection.freeConnection(connection);
	   }

		Log.log(Log.INFO,"RIDAO","getExposureDetails","Exited");
		return exposureSummary;
	}

	/**
	 * This method retrieves the list of sub scheme names from the database.
	 * @return ArrayList
	 * @throws DatabaseException
	 */
	 public ArrayList getAllSubSchemeNames() throws DatabaseException
	{
		Log.log(Log.INFO,"RIDAO","getAllSubSchemeNames","Entered");
		 Connection connection;
		 CallableStatement subSchemeStmt;
		 ResultSet subSchemes;
		 int status=-1;
		 ArrayList subSchemeNames=new ArrayList();

			connection=DBConnection.getConnection();
		 try
		 {
			subSchemeStmt=connection.prepareCall("{?=call packGetAllSubSchemeName.funcGetAllSubSchemeName(?,?)}");

			subSchemeStmt.registerOutParameter(1, java.sql.Types.INTEGER);		//status
			subSchemeStmt.registerOutParameter(2, Constants.CURSOR);			//sub schemes as a resultset
			subSchemeStmt.registerOutParameter(3, java.sql.Types.VARCHAR);		//error desctription

			subSchemeStmt.execute();
			status=subSchemeStmt.getInt(1);
			Log.log(Log.DEBUG,"RIDAO","getAllSubSchemeNames","Value returned from sp -- " + status);
			if (status==Constants.FUNCTION_FAILURE)
			{
				String err=subSchemeStmt.getString(3);
				Log.log(Log.DEBUG,"RIDAO","getAllSubSchemeNames","exception from sp -- " + err);
				subSchemeStmt.close();
				subSchemeStmt=null;
				throw new DatabaseException(err);
			}

			subSchemes=(ResultSet) subSchemeStmt.getObject(2);
			while (subSchemes.next())
			{
				subSchemeNames.add(subSchemes.getString(1));
			}
			Log.log(Log.DEBUG,"RIDAO","getAllSubSchemeNames","sub schemes list -- " + subSchemeNames);
			subSchemes.close();
			subSchemes=null;
			subSchemeStmt.close();
			subSchemeStmt=null;
		 }
		 catch (SQLException sqlException)
		{
			Log.log(Log.ERROR,"RIDAO","getAllSubSchemeNames",sqlException.getMessage());
			Log.logException(sqlException);
			throw new DatabaseException(sqlException.getMessage());
		}
	   finally
	   {
		   DBConnection.freeConnection(connection);
	   }

		Log.log(Log.INFO,"RIDAO","getAllSubSchemeNames","Exited");
		return subSchemeNames;
	}


	/**
	 * This method retrieves the sub scheme parameter details for the given sub scheme name.
	 * @return SubSchemeParameters
	 * @throws DatabaseException
	 */
	 public SubSchemeParameters getSubSchemeDetails(String subSchemeName) throws DatabaseException
	{
		Log.log(Log.INFO,"RIDAO","getSubSchemeDetails","Entered");

		 Connection connection;
		 CallableStatement subSchemeStmt;
		 ResultSet rsStates;
		 ResultSet rsMlis;
		 ResultSet rsIndustry;
		 ResultSet rsGender;
		 ResultSet rsSocialCat;

		 ArrayList statesList = new ArrayList();
		 ArrayList mlisList = new ArrayList();
		 ArrayList industryList = new ArrayList();
		 ArrayList genderList = new ArrayList();
		 ArrayList socialCatList = new ArrayList();
		 
		 int size = 0;
		 int i = 0;

		 String[] states;
		 String[] mlis;
		 String[] industry;
		 String[] gender;
		 String[] socialCat;

		 int status=-1;

			connection=DBConnection.getConnection();
		 try
		 {
			subSchemeStmt=connection.prepareCall("{?=call packgetSubSchemeDetails.funcGetSubSchemeDetails(?,?,?,?,?,?,?)}");

			subSchemeStmt.setString(2, subSchemeName);
			Log.log(Log.DEBUG,"RIDAO","getSubSchemeDetails","2 -- " + subSchemeName);
			subSchemeStmt.registerOutParameter(1, java.sql.Types.INTEGER);		//status
			subSchemeStmt.registerOutParameter(3, Constants.CURSOR);		//state
			subSchemeStmt.registerOutParameter(4, Constants.CURSOR);		//mli
			subSchemeStmt.registerOutParameter(5, Constants.CURSOR);		//industry
			subSchemeStmt.registerOutParameter(6, Constants.CURSOR);		//social category
			subSchemeStmt.registerOutParameter(7, Constants.CURSOR);		//gender
			subSchemeStmt.registerOutParameter(8, java.sql.Types.VARCHAR);		//error

			subSchemeStmt.execute();
			status=subSchemeStmt.getInt(1);
			Log.log(Log.DEBUG,"RIDAO","getSubSchemeDetails","Value returned from sp -- " + status);
			if (status==Constants.FUNCTION_FAILURE)
			{
				String err=subSchemeStmt.getString(8);
				Log.log(Log.DEBUG,"RIDAO","getSubSchemeDetails","exception from sp -- " + err);
				subSchemeStmt.close();
				subSchemeStmt=null;
				throw new DatabaseException(err);
			}

			rsStates = (ResultSet) subSchemeStmt.getObject(3);
			while (rsStates.next())
			{
				statesList.add(rsStates.getString(1));
			}
			rsStates.close();
			rsStates=null;
			size = statesList.size();
			states = new String[size];
			for (i=0;i<size;i++)
			{
				states[i] = (String) statesList.get(i);
			}

			rsMlis = (ResultSet) subSchemeStmt.getObject(4);
			while (rsMlis.next())
			{
				mlisList.add(rsMlis.getString(1));
			}
			rsMlis.close();
			rsMlis=null;
			size = mlisList.size();
			mlis = new String[size];
			for (i=0;i<size;i++)
			{
				mlis[i] = (String) mlisList.get(i);
			}

			rsIndustry = (ResultSet) subSchemeStmt.getObject(5);
			while (rsIndustry.next())
			{
				industryList.add(rsIndustry.getString(1));
			}
			rsIndustry.close();
			rsIndustry=null;
			size = industryList.size();
			industry = new String[size];
			for (i=0;i<size;i++)
			{
				industry[i] = (String) industryList.get(i);
			}

			rsSocialCat = (ResultSet) subSchemeStmt.getObject(6);
			while (rsSocialCat.next())
			{
				socialCatList.add(rsSocialCat.getString(1));
			}
			rsSocialCat.close();
			rsSocialCat=null;
			size = socialCatList.size();
			socialCat = new String[size];
			for (i=0;i<size;i++)
			{
				socialCat[i] = (String) socialCatList.get(i);
			}

			rsGender = (ResultSet) subSchemeStmt.getObject(7);
			while (rsGender.next())
			{
				genderList.add(rsGender.getString(1));
			}
			rsGender.close();
			rsGender=null;
			size = genderList.size();
			gender = new String[size];
			for (i=0;i<size;i++)
			{
				gender[i] = (String) genderList.get(i);
			}

			SubSchemeParameters subSchemeParameters=new SubSchemeParameters();

			subSchemeParameters.setSubScheme(subSchemeName);
			subSchemeParameters.setState(states);
			subSchemeParameters.setMli(mlis);
			subSchemeParameters.setIndustry(industry);
			subSchemeParameters.setGender(gender);
			subSchemeParameters.setSocialCategory(socialCat);

			subSchemeStmt.close();
			subSchemeStmt=null;

			Log.log(Log.INFO,"RIDAO","getSubSchemeDetails","Exited");
			return subSchemeParameters;

		 }
		 catch (SQLException sqlException)
		{
			Log.log(Log.ERROR,"RIDAO","getSubSchemeDetails",sqlException.getMessage());
			Log.logException(sqlException);
			 throw new DatabaseException(sqlException.getMessage());
		}
	   finally
	   {
		   DBConnection.freeConnection(connection);
	   }
	}

	/**
	 * This method retrieves the sub scheme value details for the given sub scheme name.
	 * @return SubSchemeValues
	 * @throws DatabaseException
	 */
	 public SubSchemeValues getSubSchemeValues(String subSchemeName) throws DatabaseException
	{
		Log.log(Log.INFO,"RIDAO","getSubSchemeValues","Entered");

		 Connection connection;
		 CallableStatement subSchemeValuesStmt;
		 int status=-1;

		connection=DBConnection.getConnection();

		 try
		 {
			subSchemeValuesStmt=connection.prepareCall("{?=call funcGetSubSchemeValue(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			subSchemeValuesStmt.setString(2, subSchemeName);
			Log.log(Log.DEBUG,"RIDAO","getSubSchemeValues","2 -- " + subSchemeName);

			subSchemeValuesStmt.registerOutParameter(1, java.sql.Types.INTEGER);		//status
			subSchemeValuesStmt.registerOutParameter(3, java.sql.Types.INTEGER);		//application filing time limit
			subSchemeValuesStmt.registerOutParameter(4, java.sql.Types.DATE);			//application filing time limit valid from
			subSchemeValuesStmt.registerOutParameter(5, java.sql.Types.DATE);			//application filing time limit valid to
			subSchemeValuesStmt.registerOutParameter(6, java.sql.Types.DOUBLE);		//maximum guarantee cover exposure
			subSchemeValuesStmt.registerOutParameter(7, java.sql.Types.DATE);			//maximum guarantee cover exposure valid from
			subSchemeValuesStmt.registerOutParameter(8, java.sql.Types.DATE);			//maximum guarantee cover exposure valid to
			subSchemeValuesStmt.registerOutParameter(9, java.sql.Types.DOUBLE);		//minimum sanctioned amount
			subSchemeValuesStmt.registerOutParameter(10, java.sql.Types.DATE);		//minimum sanctioned amount valid from
			subSchemeValuesStmt.registerOutParameter(11, java.sql.Types.DATE);		//minimum sanctioned amount valid to
			subSchemeValuesStmt.registerOutParameter(12, java.sql.Types.DOUBLE);		//maximum sanctioned amount
			subSchemeValuesStmt.registerOutParameter(13, java.sql.Types.DATE);		//maximum sanctioned amount valid from
			subSchemeValuesStmt.registerOutParameter(14, java.sql.Types.DATE);		//maximum sanctioned amount valid to
			subSchemeValuesStmt.registerOutParameter(15, java.sql.Types.DOUBLE);		//maximum borrower exposure
			subSchemeValuesStmt.registerOutParameter(16, java.sql.Types.DATE);		//maximum borrower exposure valid from
			subSchemeValuesStmt.registerOutParameter(17, java.sql.Types.DATE);		//maximum borrower exposure valid to
			subSchemeValuesStmt.registerOutParameter(18, java.sql.Types.DOUBLE);		//guarantee cover extent
			subSchemeValuesStmt.registerOutParameter(19, java.sql.Types.DATE);		//guarantee cover extent valid from
			subSchemeValuesStmt.registerOutParameter(20, java.sql.Types.DATE);		//guarantee cover extent valid to
			subSchemeValuesStmt.registerOutParameter(21, java.sql.Types.DOUBLE);		//service fee card rate
			subSchemeValuesStmt.registerOutParameter(22, java.sql.Types.DATE);		//service fee card rate valid from
			subSchemeValuesStmt.registerOutParameter(23, java.sql.Types.DATE);		//service fee card rate valid to
			subSchemeValuesStmt.registerOutParameter(24, java.sql.Types.DOUBLE);		//guarantee fee card rate
			subSchemeValuesStmt.registerOutParameter(25, java.sql.Types.DATE);		//guarantee fee card rate valid from
			subSchemeValuesStmt.registerOutParameter(26, java.sql.Types.DATE);		//guarantee fee card rate valid to
			subSchemeValuesStmt.registerOutParameter(27, java.sql.Types.DOUBLE);		//default rate
			subSchemeValuesStmt.registerOutParameter(28, java.sql.Types.DATE);		//default rate valid from
			subSchemeValuesStmt.registerOutParameter(29, java.sql.Types.DATE);		//default rate valid to
			subSchemeValuesStmt.registerOutParameter(30, java.sql.Types.VARCHAR);		//default rate applicable
			subSchemeValuesStmt.registerOutParameter(31, java.sql.Types.DATE);		//default rate applicable valid from
			subSchemeValuesStmt.registerOutParameter(32, java.sql.Types.DATE);		//default rate applicable valid to
			subSchemeValuesStmt.registerOutParameter(33, java.sql.Types.DOUBLE);		//moderation factor
			subSchemeValuesStmt.registerOutParameter(34, java.sql.Types.DATE);		//moderation factor valid from
			subSchemeValuesStmt.registerOutParameter(35, java.sql.Types.DATE);		//moderation factor valid to

			subSchemeValuesStmt.registerOutParameter(36, java.sql.Types.VARCHAR);		//error

			subSchemeValuesStmt.execute();
			status=subSchemeValuesStmt.getInt(1);
			Log.log(Log.DEBUG,"RIDAO","getSubSchemeValues","Value returned from sp -- " + status);
			if (status==Constants.FUNCTION_FAILURE)
			{
				String err=subSchemeValuesStmt.getString(36);
				Log.log(Log.DEBUG,"RIDAO","getSubSchemeValues","exception from sp -- " + err);
				subSchemeValuesStmt.close();
				subSchemeValuesStmt=null;
				throw new DatabaseException(err);
			}else if(status==Constants.FUNCTION_NO_DATA){
				subSchemeValuesStmt.close();
				subSchemeValuesStmt=null;
				return null;
				
			}
			

			SubSchemeValues subSchemeValues=new SubSchemeValues();

			subSchemeValues.setAppFilingTimeLimit(subSchemeValuesStmt.getInt(3));
			subSchemeValues.setAppFilingTimeLimitValidFrom(DateHelper.sqlToUtilDate(subSchemeValuesStmt.getDate(4)));
			 if (subSchemeValuesStmt.getDate(5) != null)
			 {
				subSchemeValues.setAppFilingTimeLimitValidTo(DateHelper.sqlToUtilDate(subSchemeValuesStmt.getDate(5)));
			 }
/*			subSchemeValues.setMaxGCoverExposure(subSchemeValuesStmt.getDouble(6));
			subSchemeValues.setMaxGCoverExposureValidFrom(DateHelper.sqlToUtilDate(subSchemeValuesStmt.getDate(7)));
			if (subSchemeValuesStmt.getDate(8) != null)
			{			
				subSchemeValues.setMaxGCoverExposureValidTo(DateHelper.sqlToUtilDate(subSchemeValuesStmt.getDate(8)));
			}
			subSchemeValues.setMinSanctionedAmount(subSchemeValuesStmt.getDouble(9));
			subSchemeValues.setMinSanctionedAmtValidFrom(DateHelper.sqlToUtilDate(subSchemeValuesStmt.getDate(10)));
			if (subSchemeValuesStmt.getDate(11) != null)
			{
				subSchemeValues.setMinSanctionedAmtValidTo(DateHelper.sqlToUtilDate(subSchemeValuesStmt.getDate(11)));
			}
			subSchemeValues.setMaxSanctionedAmount(subSchemeValuesStmt.getDouble(12));
			subSchemeValues.setMaxSanctionedAmtValidFrom(DateHelper.sqlToUtilDate(subSchemeValuesStmt.getDate(13)));
			if (subSchemeValuesStmt.getDate(14) != null)
			{			
				subSchemeValues.setMaxSanctionedAmtValidTo(DateHelper.sqlToUtilDate(subSchemeValuesStmt.getDate(14)));
			}*/
			subSchemeValues.setMaxBorrowerExposureAmount(subSchemeValuesStmt.getDouble(15));
			subSchemeValues.setMaxBorrowerExpAmtValidFrom(DateHelper.sqlToUtilDate(subSchemeValuesStmt.getDate(16)));
			if (subSchemeValuesStmt.getDate(17) != null)
			{			
				subSchemeValues.setMaxBorrowerExpAmtValidTo(DateHelper.sqlToUtilDate(subSchemeValuesStmt.getDate(17)));
			}
/*			subSchemeValues.setGuaranteeCoverExtent(subSchemeValuesStmt.getDouble(18));
			subSchemeValues.setGuaranteeCoverExtentValidFrom(DateHelper.sqlToUtilDate(subSchemeValuesStmt.getDate(19)));
			if (subSchemeValuesStmt.getDate(20) != null)
			{			
				subSchemeValues.setGuaranteeCoverExtentValidTo(DateHelper.sqlToUtilDate(subSchemeValuesStmt.getDate(20)));
			}*/
			subSchemeValues.setServiceFeeCardRate(subSchemeValuesStmt.getDouble(21));
			subSchemeValues.setServiceFeeCardRateValidFrom(DateHelper.sqlToUtilDate(subSchemeValuesStmt.getDate(22)));
			if (subSchemeValuesStmt.getDate(23) != null)
			{			
				subSchemeValues.setServiceFeeCardRateValidTo(DateHelper.sqlToUtilDate(subSchemeValuesStmt.getDate(23)));
			}
			subSchemeValues.setGuaranteeFeeCardRate(subSchemeValuesStmt.getDouble(24));
			subSchemeValues.setGuaranteeFeeCardRateValidFrom(DateHelper.sqlToUtilDate(subSchemeValuesStmt.getDate(25)));
			if (subSchemeValuesStmt.getDate(26) != null)
			{			
				subSchemeValues.setGuaranteeFeeCardRateValidTo(DateHelper.sqlToUtilDate(subSchemeValuesStmt.getDate(26)));
			}
			subSchemeValues.setDefaultRate(subSchemeValuesStmt.getDouble(27));
			subSchemeValues.setDefRateValidFrom(DateHelper.sqlToUtilDate(subSchemeValuesStmt.getDate(28)));
			if (subSchemeValuesStmt.getDate(29) != null)
			{			
				subSchemeValues.setDefRateValidTo(DateHelper.sqlToUtilDate(subSchemeValuesStmt.getDate(29)));
			}
			subSchemeValues.setDefaultRateApplicable(subSchemeValuesStmt.getString(30).trim());
			subSchemeValues.setDefRateApplicableValidFrom(DateHelper.sqlToUtilDate(subSchemeValuesStmt.getDate(31)));
			if (subSchemeValuesStmt.getDate(32) != null)
			{			
				subSchemeValues.setDefRateApplicableValidTo(DateHelper.sqlToUtilDate(subSchemeValuesStmt.getDate(32)));
			}
			subSchemeValues.setModerationFactor(subSchemeValuesStmt.getDouble(33));
			subSchemeValues.setModerationFactorValidFrom(DateHelper.sqlToUtilDate(subSchemeValuesStmt.getDate(34)));
			if (subSchemeValuesStmt.getDate(35) != null)
			{			
				subSchemeValues.setModerationFactorValidTo(DateHelper.sqlToUtilDate(subSchemeValuesStmt.getDate(35)));
			}

			subSchemeValuesStmt.close();
			subSchemeValuesStmt=null;

			Log.log(Log.INFO,"RIDAO","getSubSchemeValues","Exited");
			return subSchemeValues;
		 }
		 catch (SQLException sqlException)
		{
			Log.log(Log.ERROR,"RIDAO","getSubSchemeValues",sqlException.getMessage());
			Log.logException(sqlException);
			 throw new DatabaseException(sqlException.getMessage());
		}
		finally
		{
			DBConnection.freeConnection(connection);
		}
	}

	/**
	 * This method retrieves the sub scheme value details for the given sub scheme id.
	 * @return SubSchemeValues
	 * @throws DatabaseException
	 */
	 public SubSchemeValues getSubSchemeValuesForId(String subSchemeId) throws DatabaseException
	{
		Log.log(Log.INFO,"RIDAO","getSubSchemeValuesForId","Entered");

		 Connection connection;
		 CallableStatement subSchemeValuesStmt;
		 int status=-1;

		connection=DBConnection.getConnection();

		 try
		 {
			subSchemeValuesStmt=connection.prepareCall("{?=call funcGetSubSchemeValueForId(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			subSchemeValuesStmt.setString(2, subSchemeId);
			Log.log(Log.DEBUG,"RIDAO","getSubSchemeValuesForId","2 -- " + subSchemeId);

			subSchemeValuesStmt.registerOutParameter(1, java.sql.Types.INTEGER);		//status
			subSchemeValuesStmt.registerOutParameter(3, java.sql.Types.INTEGER);		//application filing time limit
			subSchemeValuesStmt.registerOutParameter(4, java.sql.Types.DATE);			//application filing time limit valid from
			subSchemeValuesStmt.registerOutParameter(5, java.sql.Types.DATE);			//application filing time limit valid to
			subSchemeValuesStmt.registerOutParameter(6, java.sql.Types.DOUBLE);		//maximum guarantee cover exposure
			subSchemeValuesStmt.registerOutParameter(7, java.sql.Types.DATE);			//maximum guarantee cover exposure valid from
			subSchemeValuesStmt.registerOutParameter(8, java.sql.Types.DATE);			//maximum guarantee cover exposure valid to
			subSchemeValuesStmt.registerOutParameter(9, java.sql.Types.DOUBLE);		//minimum sanctioned amount
			subSchemeValuesStmt.registerOutParameter(10, java.sql.Types.DATE);		//minimum sanctioned amount valid from
			subSchemeValuesStmt.registerOutParameter(11, java.sql.Types.DATE);		//minimum sanctioned amount valid to
			subSchemeValuesStmt.registerOutParameter(12, java.sql.Types.DOUBLE);		//maximum sanctioned amount
			subSchemeValuesStmt.registerOutParameter(13, java.sql.Types.DATE);		//maximum sanctioned amount valid from
			subSchemeValuesStmt.registerOutParameter(14, java.sql.Types.DATE);		//maximum sanctioned amount valid to
			subSchemeValuesStmt.registerOutParameter(15, java.sql.Types.DOUBLE);		//maximum borrower exposure
			subSchemeValuesStmt.registerOutParameter(16, java.sql.Types.DATE);		//maximum borrower exposure valid from
			subSchemeValuesStmt.registerOutParameter(17, java.sql.Types.DATE);		//maximum borrower exposure valid to
			subSchemeValuesStmt.registerOutParameter(18, java.sql.Types.DOUBLE);		//guarantee cover extent
			subSchemeValuesStmt.registerOutParameter(19, java.sql.Types.DATE);		//guarantee cover extent valid from
			subSchemeValuesStmt.registerOutParameter(20, java.sql.Types.DATE);		//guarantee cover extent valid to
			subSchemeValuesStmt.registerOutParameter(21, java.sql.Types.DOUBLE);		//service fee card rate
			subSchemeValuesStmt.registerOutParameter(22, java.sql.Types.DATE);		//service fee card rate valid from
			subSchemeValuesStmt.registerOutParameter(23, java.sql.Types.DATE);		//service fee card rate valid to
			subSchemeValuesStmt.registerOutParameter(24, java.sql.Types.DOUBLE);		//guarantee fee card rate
			subSchemeValuesStmt.registerOutParameter(25, java.sql.Types.DATE);		//guarantee fee card rate valid from
			subSchemeValuesStmt.registerOutParameter(26, java.sql.Types.DATE);		//guarantee fee card rate valid to
			subSchemeValuesStmt.registerOutParameter(27, java.sql.Types.DOUBLE);		//default rate
			subSchemeValuesStmt.registerOutParameter(28, java.sql.Types.DATE);		//default rate valid from
			subSchemeValuesStmt.registerOutParameter(29, java.sql.Types.DATE);		//default rate valid to
			subSchemeValuesStmt.registerOutParameter(30, java.sql.Types.VARCHAR);		//default rate applicable
			subSchemeValuesStmt.registerOutParameter(31, java.sql.Types.DATE);		//default rate applicable valid from
			subSchemeValuesStmt.registerOutParameter(32, java.sql.Types.DATE);		//default rate applicable valid to
			subSchemeValuesStmt.registerOutParameter(33, java.sql.Types.DOUBLE);		//moderation factor
			subSchemeValuesStmt.registerOutParameter(34, java.sql.Types.DATE);		//moderation factor valid from
			subSchemeValuesStmt.registerOutParameter(35, java.sql.Types.DATE);		//moderation factor valid to

			subSchemeValuesStmt.registerOutParameter(36, java.sql.Types.VARCHAR);		//error

			subSchemeValuesStmt.execute();
			status=subSchemeValuesStmt.getInt(1);
			Log.log(Log.DEBUG,"RIDAO","getSubSchemeValues","Value returned from sp -- " + status);
			if (status==Constants.FUNCTION_FAILURE)
			{
				String err=subSchemeValuesStmt.getString(36);
				Log.log(Log.DEBUG,"RIDAO","getSubSchemeValues","exception from sp -- " + err);
				subSchemeValuesStmt.close();
				subSchemeValuesStmt=null;
				throw new DatabaseException(err);
			}

			SubSchemeValues subSchemeValues=new SubSchemeValues();

			subSchemeValues.setAppFilingTimeLimit(subSchemeValuesStmt.getInt(3));
			subSchemeValues.setAppFilingTimeLimitValidFrom(DateHelper.sqlToUtilDate(subSchemeValuesStmt.getDate(4)));
			 if (subSchemeValuesStmt.getDate(5) != null)
			 {
				subSchemeValues.setAppFilingTimeLimitValidTo(DateHelper.sqlToUtilDate(subSchemeValuesStmt.getDate(5)));
			 }
			subSchemeValues.setMaxGCoverExposure(subSchemeValuesStmt.getDouble(6));
			subSchemeValues.setMaxGCoverExposureValidFrom(DateHelper.sqlToUtilDate(subSchemeValuesStmt.getDate(7)));
			if (subSchemeValuesStmt.getDate(8) != null)
			{			
				subSchemeValues.setMaxGCoverExposureValidTo(DateHelper.sqlToUtilDate(subSchemeValuesStmt.getDate(8)));
			}
			subSchemeValues.setMinSanctionedAmount(subSchemeValuesStmt.getDouble(9));
			subSchemeValues.setMinSanctionedAmtValidFrom(DateHelper.sqlToUtilDate(subSchemeValuesStmt.getDate(10)));
			if (subSchemeValuesStmt.getDate(11) != null)
			{
				subSchemeValues.setMinSanctionedAmtValidTo(DateHelper.sqlToUtilDate(subSchemeValuesStmt.getDate(11)));
			}
			subSchemeValues.setMaxSanctionedAmount(subSchemeValuesStmt.getDouble(12));
			subSchemeValues.setMaxSanctionedAmtValidFrom(DateHelper.sqlToUtilDate(subSchemeValuesStmt.getDate(13)));
			if (subSchemeValuesStmt.getDate(14) != null)
			{			
				subSchemeValues.setMaxSanctionedAmtValidTo(DateHelper.sqlToUtilDate(subSchemeValuesStmt.getDate(14)));
			}
			subSchemeValues.setMaxBorrowerExposureAmount(subSchemeValuesStmt.getDouble(15));
			subSchemeValues.setMaxBorrowerExpAmtValidFrom(DateHelper.sqlToUtilDate(subSchemeValuesStmt.getDate(16)));
			if (subSchemeValuesStmt.getDate(17) != null)
			{			
				subSchemeValues.setMaxBorrowerExpAmtValidTo(DateHelper.sqlToUtilDate(subSchemeValuesStmt.getDate(17)));
			}
			subSchemeValues.setGuaranteeCoverExtent(subSchemeValuesStmt.getDouble(18));
			subSchemeValues.setGuaranteeCoverExtentValidFrom(DateHelper.sqlToUtilDate(subSchemeValuesStmt.getDate(19)));
			if (subSchemeValuesStmt.getDate(20) != null)
			{			
				subSchemeValues.setGuaranteeCoverExtentValidTo(DateHelper.sqlToUtilDate(subSchemeValuesStmt.getDate(20)));
			}
			subSchemeValues.setServiceFeeCardRate(subSchemeValuesStmt.getDouble(21));
			subSchemeValues.setServiceFeeCardRateValidFrom(DateHelper.sqlToUtilDate(subSchemeValuesStmt.getDate(22)));
			if (subSchemeValuesStmt.getDate(23) != null)
			{			
				subSchemeValues.setServiceFeeCardRateValidTo(DateHelper.sqlToUtilDate(subSchemeValuesStmt.getDate(23)));
			}
			subSchemeValues.setGuaranteeFeeCardRate(subSchemeValuesStmt.getDouble(24));
			subSchemeValues.setGuaranteeFeeCardRateValidFrom(DateHelper.sqlToUtilDate(subSchemeValuesStmt.getDate(25)));
			if (subSchemeValuesStmt.getDate(26) != null)
			{			
				subSchemeValues.setGuaranteeFeeCardRateValidTo(DateHelper.sqlToUtilDate(subSchemeValuesStmt.getDate(26)));
			}
			subSchemeValues.setDefaultRate(subSchemeValuesStmt.getDouble(27));
			subSchemeValues.setDefRateValidFrom(DateHelper.sqlToUtilDate(subSchemeValuesStmt.getDate(28)));
			if (subSchemeValuesStmt.getDate(29) != null)
			{			
				subSchemeValues.setDefRateValidTo(DateHelper.sqlToUtilDate(subSchemeValuesStmt.getDate(29)));
			}
			subSchemeValues.setDefaultRateApplicable(subSchemeValuesStmt.getString(30).trim());
			subSchemeValues.setDefRateApplicableValidFrom(DateHelper.sqlToUtilDate(subSchemeValuesStmt.getDate(31)));
			if (subSchemeValuesStmt.getDate(32) != null)
			{			
				subSchemeValues.setDefRateApplicableValidTo(DateHelper.sqlToUtilDate(subSchemeValuesStmt.getDate(32)));
			}
			subSchemeValues.setModerationFactor(subSchemeValuesStmt.getDouble(33));
			subSchemeValues.setModerationFactorValidFrom(DateHelper.sqlToUtilDate(subSchemeValuesStmt.getDate(34)));
			if (subSchemeValuesStmt.getDate(35) != null)
			{			
				subSchemeValues.setModerationFactorValidTo(DateHelper.sqlToUtilDate(subSchemeValuesStmt.getDate(35)));
			}

			subSchemeValuesStmt.close();
			subSchemeValuesStmt=null;

			Log.log(Log.INFO,"RIDAO","getSubSchemeValues","Exited");
			return subSchemeValues;
		 }
		 catch (SQLException sqlException)
		{
			Log.log(Log.ERROR,"RIDAO","getSubSchemeValues",sqlException.getMessage());
			Log.logException(sqlException);
			 throw new DatabaseException(sqlException.getMessage());
		}
		finally
		{
			DBConnection.freeConnection(connection);
		}
	}

	/**
	 * This method updates the database with the sub scheme parameters and values 
	 * for the given sub scheme name.
	 * 
	 * @throws DatabaseException
	 *
	 public void updateSubSchemeDetails(SubSchemeParameters subSchemeParameters, SubSchemeValues subSchemeValues) throws DatabaseException
	{
			Log.log(Log.INFO,"RIDAO","updateSubSchemeDetails","Entered");

		 Connection connection;
		 CallableStatement updateSubSchemeStmt;
		 CallableStatement subSchemeDetailsStmt;
		 int status=-1;
		 java.sql.Date sqlDate;
		 java.util.Date utilDate;
			connection=DBConnection.getConnection(false);
		 try
		 {
			updateSubSchemeStmt=connection.prepareCall("{?=call funcUpdateSubScheme(?,?,?,?,?,?,?,?,?)}");

			updateSubSchemeStmt.setString(2,subSchemeParameters.getSubScheme());				//Sub Scheme Name
			Log.log(Log.DEBUG,"RIDAO","updateSubSchemeDetails","2 -- " + subSchemeParameters.getSubScheme());
			if (!(subSchemeParameters.getState()).equals(""))
			{
				updateSubSchemeStmt.setString(3,subSchemeParameters.getState());					//State
				Log.log(Log.DEBUG,"RIDAO","updateSubSchemeDetails","3 -- " + subSchemeParameters.getState());
			}
			else
			 {
				updateSubSchemeStmt.setNull(3, java.sql.Types.VARCHAR);
				Log.log(Log.DEBUG,"RIDAO","updateSubSchemeDetails","3 -- null");
			 }

			String mliName=subSchemeParameters.getMli();
			if (!mliName.equals(""))
			{
				int index=mliName.indexOf("(");
				String mliBnkId=mliName.substring(index+1,index+5);
				String mliZneId=mliName.substring(index+5,index+9);
				String mliBrnId=mliName.substring(index+9,index+13);
				updateSubSchemeStmt.setString(4,mliBnkId);									//MLI bank id
				Log.log(Log.DEBUG,"RIDAO","updateSubSchemeDetails","4 -- " + mliBnkId);
				updateSubSchemeStmt.setString(5,mliZneId);									//MLI bank id
				Log.log(Log.DEBUG,"RIDAO","updateSubSchemeDetails","5 -- " + mliZneId);
				updateSubSchemeStmt.setString(6,mliBrnId);									//MLI bank id
				Log.log(Log.DEBUG,"RIDAO","updateSubSchemeDetails","6 -- " + mliBrnId);
			}
			else
			 {
				updateSubSchemeStmt.setNull(4, java.sql.Types.VARCHAR);
				Log.log(Log.DEBUG,"RIDAO","updateSubSchemeDetails","4 -- null");
				updateSubSchemeStmt.setNull(5, java.sql.Types.VARCHAR);
				Log.log(Log.DEBUG,"RIDAO","updateSubSchemeDetails","5 -- null");
				updateSubSchemeStmt.setNull(6, java.sql.Types.VARCHAR);
				Log.log(Log.DEBUG,"RIDAO","updateSubSchemeDetails","6 -- null");
			 }
			if (!(subSchemeParameters.getIndustry()).equals(""))
			{
				updateSubSchemeStmt.setString(7,subSchemeParameters.getIndustry());				//Industry
				Log.log(Log.DEBUG,"RIDAO","updateSubSchemeDetails","7 -- " + subSchemeParameters.getIndustry());
			}
			else
			 {
				updateSubSchemeStmt.setNull(7, java.sql.Types.VARCHAR);
				Log.log(Log.DEBUG,"RIDAO","updateSubSchemeDetails","7 -- null");
			 }

			if (!(subSchemeParameters.getGender()).equals(""))
			{
				updateSubSchemeStmt.setString(8,subSchemeParameters.getGender());					//Gender
				Log.log(Log.DEBUG,"RIDAO","updateSubSchemeDetails","8 -- " + subSchemeParameters.getGender());
			}
			else
			 {
				updateSubSchemeStmt.setNull(8, java.sql.Types.VARCHAR);
				Log.log(Log.DEBUG,"RIDAO","updateSubSchemeDetails","8 -- null");
			 }

			if (!(subSchemeParameters.getSocialCategory()).equals(""))
			{
				updateSubSchemeStmt.setString(9,subSchemeParameters.getSocialCategory());			//Social Category
				Log.log(Log.DEBUG,"RIDAO","updateSubSchemeDetails","8 -- " + subSchemeParameters.getSocialCategory());
			}
			else
			 {
				updateSubSchemeStmt.setNull(9, java.sql.Types.VARCHAR);
				Log.log(Log.DEBUG,"RIDAO","updateSubSchemeDetails","9 -- null");
			 }

			updateSubSchemeStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			updateSubSchemeStmt.registerOutParameter(10, java.sql.Types.VARCHAR);

			updateSubSchemeStmt.execute();
			status=updateSubSchemeStmt.getInt(1);
			Log.log(Log.DEBUG,"RIDAO","updateSubSchemeDetails","Value returned from sp -- " + status);
			if (status==Constants.FUNCTION_FAILURE)
			{
				String err=updateSubSchemeStmt.getString(10);
				Log.log(Log.DEBUG,"RIDAO","updateUserLimits","exception from sp -- " + err);
				throw new DatabaseException(err);
			}

			Log.log(Log.INFO,"RIDAO","updateSubSchemeDetails","Sub Scheme Paramters Updated");

			subSchemeDetailsStmt=connection.prepareCall("{?= call funcUpdateSubSchemeValue (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			subSchemeDetailsStmt.setString(2, subSchemeParameters.getSubScheme());		//Sub Scheme Name
			Log.log(Log.DEBUG,"RIDAO","updateSubSchemeDetails","2 -- " + subSchemeParameters.getSubScheme());
			subSchemeDetailsStmt.setInt(3, subSchemeValues.getAppFilingTimeLimit());	//Application Filing Time Limit
			Log.log(Log.DEBUG,"RIDAO","updateSubSchemeDetails","3 -- " + subSchemeValues.getAppFilingTimeLimit());
			utilDate=subSchemeValues.getAppFilingTimeLimitValidFrom();
			sqlDate=new java.sql.Date(utilDate.getTime());
			subSchemeDetailsStmt.setDate(4, sqlDate);								//Application Filing Time Limit Valid From
			Log.log(Log.DEBUG,"RIDAO","updateSubSchemeDetails","4 -- " + sqlDate);
			utilDate=subSchemeValues.getAppFilingTimeLimitValidTo();
			sqlDate=new java.sql.Date(utilDate.getTime());
			subSchemeDetailsStmt.setDate(5, sqlDate);								//Application Filing Time Limit Valid To
			Log.log(Log.DEBUG,"RIDAO","updateSubSchemeDetails","5 -- " + sqlDate);
			subSchemeDetailsStmt.setDouble(6, subSchemeValues.getMaxGCoverExposure());	//Maximum Guarantee Cover Exposure
			Log.log(Log.DEBUG,"RIDAO","updateSubSchemeDetails","6 -- " + subSchemeValues.getMaxGCoverExposure());
			utilDate=subSchemeValues.getMaxGCoverExposureValidFrom();
			sqlDate=new java.sql.Date(utilDate.getTime());
			subSchemeDetailsStmt.setDate(7, sqlDate);								//Maximum Guarantee Cover Exposure Valid From
			Log.log(Log.DEBUG,"RIDAO","updateSubSchemeDetails","7 -- " + sqlDate);
			utilDate=subSchemeValues.getMaxGCoverExposureValidTo();
			sqlDate=new java.sql.Date(utilDate.getTime());
			subSchemeDetailsStmt.setDate(8, sqlDate);								//Maximum Guarantee Cover Exposure Valid To
			Log.log(Log.DEBUG,"RIDAO","updateSubSchemeDetails","8 -- " + sqlDate);
			subSchemeDetailsStmt.setDouble(9, subSchemeValues.getMinSanctionedAmount());	//Minimum Sanctioned Amount
			Log.log(Log.DEBUG,"RIDAO","updateSubSchemeDetails","8 -- " + subSchemeValues.getMinSanctionedAmount());
			utilDate=subSchemeValues.getMinSanctionedAmtValidFrom();
			sqlDate=new java.sql.Date(utilDate.getTime());
			subSchemeDetailsStmt.setDate(10, sqlDate);								//Minimum Sanctioned Amount Valid From
			Log.log(Log.DEBUG,"RIDAO","updateSubSchemeDetails","10 -- " + sqlDate);
			utilDate=subSchemeValues.getMinSanctionedAmtValidTo();
			sqlDate=new java.sql.Date(utilDate.getTime());
			subSchemeDetailsStmt.setDate(11, sqlDate);								//Minimum Sanctioned Amount Valid To
			Log.log(Log.DEBUG,"RIDAO","updateSubSchemeDetails","11 -- " + sqlDate);
			subSchemeDetailsStmt.setDouble(12, subSchemeValues.getMaxSanctionedAmount());	//Maximum Sanctioned Amount
			Log.log(Log.DEBUG,"RIDAO","updateSubSchemeDetails","12 -- " + subSchemeValues.getMaxSanctionedAmount());
			utilDate=subSchemeValues.getMaxSanctionedAmtValidFrom();
			sqlDate=new java.sql.Date(utilDate.getTime());
			subSchemeDetailsStmt.setDate(13, sqlDate);								//Maximum Sanctioned Amount Valid From
			Log.log(Log.DEBUG,"RIDAO","updateSubSchemeDetails","13 -- " + sqlDate);
			utilDate=subSchemeValues.getMaxSanctionedAmtValidTo();
			sqlDate=new java.sql.Date(utilDate.getTime());
			subSchemeDetailsStmt.setDate(14, sqlDate);								//Maximum Sanctioned Amount Valid To
			Log.log(Log.DEBUG,"RIDAO","updateSubSchemeDetails","14 -- " + sqlDate);
			subSchemeDetailsStmt.setDouble(15, subSchemeValues.getMaxBorrowerExposureAmount());	//Maximum Borrower Exposure Amount
			Log.log(Log.DEBUG,"RIDAO","updateSubSchemeDetails","15 -- " + subSchemeValues.getMaxBorrowerExposureAmount());
			utilDate=subSchemeValues.getMaxBorrowerExpAmtValidFrom();
			sqlDate=new java.sql.Date(utilDate.getTime());
			subSchemeDetailsStmt.setDate(16, sqlDate);								//Maximum Borrower Exposure Amount Valid From
			Log.log(Log.DEBUG,"RIDAO","updateSubSchemeDetails","16 -- " + sqlDate);
			utilDate=subSchemeValues.getMaxBorrowerExpAmtValidTo();
			sqlDate=new java.sql.Date(utilDate.getTime());
			subSchemeDetailsStmt.setDate(17, sqlDate);								//Maximum Borrower Exposure Amount Valid To
			Log.log(Log.DEBUG,"RIDAO","updateSubSchemeDetails","17 -- " + sqlDate);
			subSchemeDetailsStmt.setDouble(18, subSchemeValues.getGuaranteeCoverExtent());	//Gurantee Cover Extent
			Log.log(Log.DEBUG,"RIDAO","updateSubSchemeDetails","18 -- " + subSchemeValues.getGuaranteeCoverExtent());
			utilDate=subSchemeValues.getGuaranteeCoverExtentValidFrom();
			sqlDate=new java.sql.Date(utilDate.getTime());
			subSchemeDetailsStmt.setDate(19, sqlDate);								//Guarantee Cover Extent Valid From
			Log.log(Log.DEBUG,"RIDAO","updateSubSchemeDetails","19 -- " + sqlDate);
			utilDate=subSchemeValues.getGuaranteeCoverExtentValidTo();
			sqlDate=new java.sql.Date(utilDate.getTime());
			subSchemeDetailsStmt.setDate(20, sqlDate);								//Guarantee Cover Extent Valid To
			Log.log(Log.DEBUG,"RIDAO","updateSubSchemeDetails","20 -- " + sqlDate);
			subSchemeDetailsStmt.setDouble(21, subSchemeValues.getServiceFeeCardRate());	//Service Fee Card Rate
			Log.log(Log.DEBUG,"RIDAO","updateSubSchemeDetails","21 -- " + subSchemeValues.getServiceFeeCardRate());
			utilDate=subSchemeValues.getServiceFeeCardRateValidFrom();
			sqlDate=new java.sql.Date(utilDate.getTime());
			subSchemeDetailsStmt.setDate(22, sqlDate);								//Service Fee Card Rate Valid From
			Log.log(Log.DEBUG,"RIDAO","updateSubSchemeDetails","22 -- " + sqlDate);
			utilDate=subSchemeValues.getServiceFeeCardRateValidTo();
			sqlDate=new java.sql.Date(utilDate.getTime());
			subSchemeDetailsStmt.setDate(23, sqlDate);								//Service Fee Card Rate Valid To
			Log.log(Log.DEBUG,"RIDAO","updateSubSchemeDetails","23 -- " + sqlDate);
			subSchemeDetailsStmt.setDouble(24, subSchemeValues.getGuaranteeFeeCardRate());	//Guarantee Fee Card Rate
			Log.log(Log.DEBUG,"RIDAO","updateSubSchemeDetails","24 -- " + subSchemeValues.getGuaranteeFeeCardRate());
			utilDate=subSchemeValues.getGuaranteeFeeCardRateValidFrom();
			sqlDate=new java.sql.Date(utilDate.getTime());
			subSchemeDetailsStmt.setDate(25, sqlDate);								//Guarantee Fee Card Rate Valid From
			Log.log(Log.DEBUG,"RIDAO","updateSubSchemeDetails","25 -- " + sqlDate);
			utilDate=subSchemeValues.getGuaranteeFeeCardRateValidTo();
			sqlDate=new java.sql.Date(utilDate.getTime());
			subSchemeDetailsStmt.setDate(26, sqlDate);								//Guarantee Fee Card Rate Valid To
			Log.log(Log.DEBUG,"RIDAO","updateSubSchemeDetails","26 -- " + sqlDate);
			subSchemeDetailsStmt.setDouble(27, subSchemeValues.getDefaultRate());	//Default Rate
			Log.log(Log.DEBUG,"RIDAO","updateSubSchemeDetails","27 -- " + subSchemeValues.getDefaultRate());
			utilDate=subSchemeValues.getDefRateValidFrom();
			sqlDate=new java.sql.Date(utilDate.getTime());
			subSchemeDetailsStmt.setDate(28, sqlDate);								//Default Rate Valid From
			Log.log(Log.DEBUG,"RIDAO","updateSubSchemeDetails","28 -- " + sqlDate);
			utilDate=subSchemeValues.getDefRateValidTo();
			sqlDate=new java.sql.Date(utilDate.getTime());
			subSchemeDetailsStmt.setDate(29, sqlDate);								//Default Rate Valid To
			Log.log(Log.DEBUG,"RIDAO","updateSubSchemeDetails","29 -- " + sqlDate);
			subSchemeDetailsStmt.setString(30, subSchemeValues.getDefaultRateApplicable());	//Default Rate Applicable
			Log.log(Log.DEBUG,"RIDAO","updateSubSchemeDetails","30 -- " + subSchemeValues.getDefaultRateApplicable());
			utilDate=subSchemeValues.getDefRateApplicableValidFrom();
			sqlDate=new java.sql.Date(utilDate.getTime());
			subSchemeDetailsStmt.setDate(31, sqlDate);								//Default Rate Applicable Valid From
			Log.log(Log.DEBUG,"RIDAO","updateSubSchemeDetails","31 -- " + sqlDate);
			utilDate=subSchemeValues.getDefRateApplicableValidTo();
			sqlDate=new java.sql.Date(utilDate.getTime());
			subSchemeDetailsStmt.setDate(32, sqlDate);								//Default Rate Applicable Valid To
			Log.log(Log.DEBUG,"RIDAO","updateSubSchemeDetails","32 -- " + sqlDate);
			subSchemeDetailsStmt.setDouble(33, subSchemeValues.getModerationFactor());	//Moderation Factor
			Log.log(Log.DEBUG,"RIDAO","updateSubSchemeDetails","33 -- " + subSchemeValues.getModerationFactor());
			utilDate=subSchemeValues.getModerationFactorValidFrom();
			sqlDate=new java.sql.Date(utilDate.getTime());
			subSchemeDetailsStmt.setDate(34, sqlDate);								//Moderation Factor Valid From
			Log.log(Log.DEBUG,"RIDAO","updateSubSchemeDetails","42 -- " + sqlDate);
			utilDate=subSchemeValues.getModerationFactorValidTo();
			sqlDate=new java.sql.Date(utilDate.getTime());
			subSchemeDetailsStmt.setDate(35, sqlDate);								//Moderation Factor Valid To
			Log.log(Log.DEBUG,"RIDAO","updateSubSchemeDetails","35 -- " + sqlDate);

			subSchemeDetailsStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			subSchemeDetailsStmt.registerOutParameter(36, java.sql.Types.VARCHAR);

			subSchemeDetailsStmt.execute();
			status=subSchemeDetailsStmt.getInt(1);
			Log.log(Log.DEBUG,"RIDAO","updateSubSchemeDetails","value returned from sp -- " + status);
			if (status==Constants.FUNCTION_FAILURE)
			{
				String err=subSchemeDetailsStmt.getString(36);
				Log.log(Log.DEBUG,"RIDAO","updateSubSchemeDetails","error from sp -- " + err);
				subSchemeDetailsStmt.close();
				connection.rollback();
				throw new DatabaseException(err);
			}
			connection.commit();
			subSchemeDetailsStmt.close();

			Log.log(Log.INFO,"RIDAO","updateSubSchemeDetails","Sub Scheme Values Updated");
		 }
		 catch (SQLException sqlException)
		 {
			Log.log(Log.ERROR,"RIDAO","updateSubSchemeDetails",sqlException.getMessage());
			Log.logException(sqlException);
			 throw new DatabaseException(sqlException.getMessage());
		 }
		finally
		{
			DBConnection.freeConnection(connection);
		}

		Log.log(Log.INFO,"RIDAO","updateSubSchemeDetails","Exited");
	}

	/**
	* This method invokes the stored procedure for updating the participating bank limits in the database.
	* It returns a boolean true if the update is successfull.
	* It returns a boolean false if :
	*	an error was encountered while executing the stored procedure.
	*	if the userLimits object passed to this method is null.
	* It throws a DatabaseException in case of:
	*	any Exception during the establishment of the connection or
	*	execution of the prepared call statement.
	*
	* @throws DatabaseException
	* @param participatingBankLimits
	*/
   public void updateParticipatingBankLimits(ParticipatingBankLimit participatingBankLimits, String userId) throws DatabaseException
   {
	   Log.log(Log.INFO,"RIDAO","updateParticipatingBankLimits","Entered");
	   CallableStatement limitStmt;
	   java.util.Date utilDate;
	   Connection connection = DBConnection.getConnection();
	   try
	   {
		   utilDate=new java.util.Date();
		   limitStmt = connection.prepareCall("{?=call funcInsertPartBankLimit(?,?,?,?,?,?,?,?,?)}");

		   String bankName = participatingBankLimits.getBankName();
		   int index = bankName.indexOf(",");
		   limitStmt.setString(2, bankName.substring(0, index));
		   limitStmt.setDouble(3, participatingBankLimits.getAmount());
		   utilDate=participatingBankLimits.getValidFrom();
		   limitStmt.setDate(4, java.sql.Date.valueOf(DateHelper.stringToSQLdate(utilDate.toString())));
		   utilDate=participatingBankLimits.getValidTo();
			if (utilDate.toString().equals(""))
			{
				limitStmt.setNull(5,java.sql.Types.DATE);		//Limit Valid To Date
			}
			else
			{		   
				limitStmt.setDate(5, java.sql.Date.valueOf(DateHelper.stringToSQLdate(utilDate.toString())));
			}
		   limitStmt.setString(6, userId);
		   String memberId = participatingBankLimits.getMemberId();
		   memberId=memberId.substring(1, memberId.indexOf(")"));
		   String bnkId = memberId.substring(0,4);
		   String zneId = memberId.substring(4,8);
		   String brnId = memberId.substring(8,12);
		   limitStmt.setString(7, bnkId);
		   limitStmt.setString(8, zneId);
		   limitStmt.setString(9, brnId);

		   limitStmt.registerOutParameter(1, java.sql.Types.INTEGER);
		   limitStmt.registerOutParameter(10, java.sql.Types.VARCHAR);

		   limitStmt.execute();

		   int errCode= limitStmt.getInt(1);
		   String errDesc = limitStmt.getString(10);

		   Log.log(Log.DEBUG,"RIDAO","updateParticipatingBankLimits","error and error Code "+errDesc+" "+errCode);

		   if (errCode==Constants.FUNCTION_FAILURE)
		   {
				Log.log(Log.ERROR,"RIDAO","updateParticipatingBankLimits",errDesc);
				
				limitStmt.close();
				limitStmt=null;
				
				throw new DatabaseException(errDesc);
		   }
	   }
	   catch (SQLException sqlException)
	   {
			Log.log(Log.ERROR,"RIDAO","updateParticipatingBankLimits", sqlException.getMessage());
			
			Log.logException(sqlException);
			
			throw new DatabaseException("Unable to save Participating Bank Limits");
		}
		finally
		{
			DBConnection.freeConnection(connection);
		}

	   Log.log(Log.INFO,"RIDAO","updateParticipatingBankLimits","Exited");
   }

   /**
	* This method returns the sub scheme name for the applicaiton passed.
	* First all the currently valid sub schemes are retrieved from the database.
	* For each of the valid sub scheme, the sub scheme parameters are retrieved from the database
	* and compared with the application parameter values.
	* If the application parameter values matches with the sub scheme parameter values,
	*	the sub scheme name is returned.
	* Else
	*	GLOBAL is returned.
	*@param application
	*@returns String
	*	returns the subscheme name or GLOBAL
	*/
   public String getSubScheme(Application application) throws DatabaseException
   {
	   Log.log(Log.INFO,"RIDAO","getSubScheme","Entered");
	   CallableStatement stmt;
	   Connection connection = DBConnection.getConnection();
	   ResultSet rsSubSchemes;
	   ResultSet rsStates;
	   ResultSet rsMli;
	   ResultSet rsIndustry;
	   ResultSet rsGender;
	   ResultSet rsSocialCat;
	   ArrayList subSchemeId = new ArrayList();
	   ArrayList subSchemeName = new ArrayList();
	   ArrayList states = new ArrayList();
	   ArrayList mli = new ArrayList();
	   ArrayList industry = new ArrayList();
	   ArrayList gender = new ArrayList();
	   ArrayList socialCat = new ArrayList();
	   String outSubSchemeName = "";
	   try
	   {
			String appState = application.getBorrowerDetails().getSsiDetails().getState();
			String appIndustry = application.getBorrowerDetails().getSsiDetails().getIndustryNature();
			String appGender = application.getBorrowerDetails().getSsiDetails().getCpGender();
			String appMli = application.getMliID().substring(0,4)+"00000000";
			String appSocialCat = application.getBorrowerDetails().getSsiDetails().getSocialCategory();

			Log.log(Log.INFO,"RIDAO","getSubScheme","applicaiton state -- " + appState);
			Log.log(Log.INFO,"RIDAO","getSubScheme","application industry -- " + appIndustry);
			Log.log(Log.INFO,"RIDAO","getSubScheme","application gender -- " + appGender);
			Log.log(Log.INFO,"RIDAO","getSubScheme","applicaiton mli -- " + appMli);
			Log.log(Log.INFO,"RIDAO","getSubScheme","application social category -- " + appSocialCat);
			
			stmt = connection.prepareCall("{call PKGGETVLDSUBSCHEMEFUNCGETVALIDSUBSCHEMES(?,?)}");

			stmt.registerOutParameter(1, java.sql.Types.INTEGER);
//			stmt.registerOutParameter(2, Constants.CURSOR);
			stmt.registerOutParameter(2, java.sql.Types.VARCHAR);

			stmt.execute();

		   int errCode= stmt.getInt(1);
		   String errDesc = stmt.getString(2);

		   Log.log(Log.DEBUG,"RIDAO","getSubScheme","error and error Code "+errDesc+" "+errCode);

		   if (errCode==Constants.FUNCTION_FAILURE)
		   {
				Log.log(Log.ERROR,"RIDAO","getSubScheme",errDesc);
				
				stmt.close();
				stmt=null;
			
				throw new DatabaseException(errDesc);
		   }
		   rsSubSchemes = stmt.executeQuery();
		   while (rsSubSchemes.next())
		   {
			   subSchemeId.add(rsSubSchemes.getString(1));
			   subSchemeName.add(rsSubSchemes.getString(2));
		   }
		   rsSubSchemes.close();
		   rsSubSchemes=null;
		   stmt.close();
		   stmt=  null;

		   Log.log(Log.INFO,"RIDAO","getSubScheme","valid sub schemes retrieved");

		   int size = subSchemeId.size();
		   int i = 0;
		   boolean subSchemeFound = false;
		   while (! subSchemeFound && i<size)
		   {
			   ArrayList values = getSubSchemeParameters((String) subSchemeId.get(i));
			   states = (ArrayList)values.get(0);
			   industry = (ArrayList)values.get(2);
			   mli = (ArrayList)values.get(1);
			   gender = (ArrayList)values.get(3);
			   socialCat = (ArrayList)values.get(4);

			   Log.log(Log.INFO,"RIDAO","getSubScheme","states for subscheme " + subSchemeId.get(i) + "--" + states);
			   Log.log(Log.INFO,"RIDAO","getSubScheme","industry for subscheme " + subSchemeId.get(i) + "--" + industry);
			   Log.log(Log.INFO,"RIDAO","getSubScheme","mli for subscheme " + subSchemeId.get(i) + "--" + mli);
			   Log.log(Log.INFO,"RIDAO","getSubScheme","gender for subscheme " + subSchemeId.get(i) + "--" + gender);
			   Log.log(Log.INFO,"RIDAO","getSubScheme","social category for subscheme " + subSchemeId.get(i) + "--" + socialCat);

			   if ((states.contains(appState))
				   && (mli.contains(appMli))
				   && (industry.contains(appIndustry))
				   && (gender.contains(appGender))
				   && (socialCat.contains(appSocialCat)))
			   {
				   outSubSchemeName = (String) subSchemeName.get(i);
				   subSchemeFound = true;
				   Log.log(Log.INFO,"RIDAO","getSubScheme","sub scheme found for application -- " + outSubSchemeName);
			   }

			   i++;
		   }
		   if (! subSchemeFound)
		   {
			   outSubSchemeName = Constants.GLOBAL_SUB_SCHEME;
			   Log.log(Log.INFO,"RIDAO","getSubScheme","no sub scheme found. so global is passed");
		   }
	   }
	   catch (SQLException sqlException)
	   {
			Log.log(Log.ERROR,"RIDAO","getSubScheme", sqlException.getMessage());
			
			Log.logException(sqlException);
			
			throw new DatabaseException("Unable to get sub scheme details for application");
		}
		finally
		{
			DBConnection.freeConnection(connection);
		}

	   Log.log(Log.INFO,"RIDAO","getSubScheme","Exited");
	   return outSubSchemeName;
   }

	/**
	 * This method generates the exposure report for the given parameters.
	 * The ParameterCombination object passed contains the parameters for which the exposure report 
	 * is generated.
	 * The Exposure Report is generated for the hierarchy state - member - borrower - cgpan.
	 * The sanctioned amount, outstanding amount for each of the application is retrieved.
	 * Also the no of claim applications and the total claim amount for each borrower is calculated.
	 * @returns ExposureReportDetail
	 */
	 public ExposureReportDetail generateExposureReport(ParameterCombination parameterCombination) throws DatabaseException
	{
		   Log.log(Log.INFO,"RIDAO","generateExposureDetails","Entered");

			Connection connection;
			PreparedStatement exposureStmt;
			PreparedStatement claimStmt;
			ResultSet exposureDetails;
			ResultSet bidClaimDetails;
			int status=-1;

			ExposureReportDetail exposureReportDetail = new ExposureReportDetail();
			ExposureDetails expDetails=new ExposureDetails();
			ArrayList tcDetails=new ArrayList(10000);
			ArrayList wcDetails=new ArrayList();

			ArrayList tcBids=new ArrayList(10000);
			ArrayList wcBids=new ArrayList();
		 
			connection=DBConnection.getConnection();
			try
			{
				String scheme = parameterCombination.getScheme();
				String[] states = parameterCombination.getState();
				String[] mlis = parameterCombination.getMli();
				String[] ind = parameterCombination.getIndustry();
				String[] gen = parameterCombination.getGender();
				String[] sc = parameterCombination.getSocialCategory();
				java.util.Date gIssued = parameterCombination.getGuaIssuedAsOn();
				double sancAmtFrom = parameterCombination.getSancAmtRangeFrom();
				double sancAmtTo = parameterCombination.getSancAmtRangeTo();
				String facility = parameterCombination.getFacilityType();

				StringBuffer tcQuery=new StringBuffer(2000);
				StringBuffer wcQuery=new StringBuffer(2000);
				StringBuffer bidQuery=new StringBuffer(2000);
				StringBuffer tempBuffer=new StringBuffer(100);
				int size = states.length;
				for (int i=0;i<size;i++)
				{
					if (size-1==i)
					{
						tempBuffer.append(" ?");
					}
					else
					{
						tempBuffer.append(" ?,");
					}
				}
				String statesQuery=tempBuffer.toString();
			 
				if(Log.isDebugEnabled())
				Log.log(Log.DEBUG,"RIDAO","generateExposureDetails","state query " + statesQuery);

				tempBuffer.setLength(0);
				size = ind.length;
				for (int i=0;i<size;i++)
				{
					if (size-1==i)
					{
						tempBuffer.append(" ?");
					}
					else
					{
						tempBuffer.append(" ?,");
					}
				}
				String indQuery = tempBuffer.toString();
			   if(Log.isDebugEnabled())
				Log.log(Log.DEBUG,"RIDAO","generateExposureDetails","industry query " + indQuery);

				tempBuffer.setLength(0);
				size = gen.length;
				for (int i=0;i<size;i++)
				{
					if (size-1==i)
					{
						tempBuffer.append(" ?");
					}
					else
					{
						tempBuffer.append(" ?,");
					}
				}
				String genderQuery = tempBuffer.toString();
				if(Log.isDebugEnabled())	
				Log.log(Log.DEBUG,"RIDAO","generateExposureDetails","gender query " + genderQuery);

				tempBuffer.setLength(0);
				size = sc.length;
				for (int i=0;i<size;i++)
				{
					if (size-1==i)
					{
						tempBuffer.append(" ?");
					}
					else
					{
						tempBuffer.append(" ?,");
					}
				}
				String scQuery = tempBuffer.toString();
			   if(Log.isDebugEnabled())
				Log.log(Log.DEBUG,"RIDAO","generateExposureDetails","social category query " + scQuery);

				tempBuffer.setLength(0);
				size = mlis.length;
				for (int i=0;i<size;i++)
				{
					if (size-1==i)
					{
						tempBuffer.append(" ?");
					}
					else
					{
						tempBuffer.append(" ?,");
					}
				}
				String mliQuery = tempBuffer.toString();
				tempBuffer.setLength(0);

			   if(Log.isDebugEnabled())
				Log.log(Log.DEBUG,"RIDAO","generateExposureDetails","mli query " + mliQuery);

				tcQuery.append("SELECT ssi.SSI_STATE_NAME, ");
				tcQuery.append("app.MEM_BNK_ID||app.MEM_ZNE_ID||app.MEM_BRN_ID, ssi.BID, app.CGPAN, ");
				tcQuery.append("tc.TRM_AMOUNT_SANCTIONED, ");
				tcQuery.append("tco.TCO_OUTSTANDING_AMOUNT,max(tco.TCO_OUTSTANDING_ON_DT) ");
				tcQuery.append("FROM application_detail app, ssi_detail ssi,term_loan_detail tc, ");
				tcQuery.append("tc_outstanding_detail tco, scheme_master sch ,promoter_detail pd ");
				tcQuery.append("WHERE " );
				tcQuery.append(" app.APP_GUAR_START_DATE_TIME <= ?  ");
				tcQuery.append("AND  app.APP_STATUS = ? ");
				tcQuery.append("AND app.APP_LOAN_TYPE IN (?, ?) ");
				tcQuery.append("AND app.SCM_ID = sch.SCM_ID ");
				tcQuery.append("AND sch.SCM_NAME=? ");
				tcQuery.append("AND app.SSI_REFERENCE_NUMBER=ssi.SSI_REFERENCE_NUMBER ");
				tcQuery.append("AND tc.APP_REF_NO=app.APP_REF_NO ");
				tcQuery.append("AND (tc.TRM_AMOUNT_SANCTIONED ");
				tcQuery.append("BETWEEN ? AND ?) ");
				tcQuery.append("AND app.APP_REF_NO=tco.APP_REF_NO ");
				/*tcQuery.append("AND tco.TCO_OUTSTANDING_ON_DT = (select max(tco.TCO_OUTSTANDING_ON_DT) ");
				tcQuery.append("FROM tc_outstanding_detail tco ");
				tcQuery.append("WHERE tco.APP_REF_NO = app.APP_REF_NO) "); */
			 
				if (! states[0].equalsIgnoreCase("ALL"))
				{
				   tcQuery.append("AND ssi.SSI_STATE_NAME IN (");
				   tcQuery.append(statesQuery);
				   tcQuery.append(") ");
				}
				if (! ind[0].equalsIgnoreCase("ALL"))
				{
				   tcQuery.append("AND ssi.SSI_INDUSTRY_NATURE IN (");
				   tcQuery.append(indQuery);
				   tcQuery.append(") ");
				}
				if (! mlis[0].equalsIgnoreCase("ALL"))
				{
				   tcQuery.append("AND LTRIM(RTRIM(app.MEM_BNK_ID)) IN (");
				   tcQuery.append(mliQuery);
				   tcQuery.append(") ");
				}
				if (! gen[0].equalsIgnoreCase("A"))
				{
				   tcQuery.append("AND pd.PMR_CHIEF_GENDER IN (");
				   tcQuery.append(genderQuery);
				   tcQuery.append(") ");
				}
				if (! sc[0].equalsIgnoreCase("ALL"))
				{
				   tcQuery.append("AND pd.PMR_CHIEF_SOCIAL_CAT IN (");
				   tcQuery.append(scQuery);
				   tcQuery.append(") ");
				}
		 
			 
				tcQuery.append("GROUP BY ssi.SSI_STATE_NAME, ");
				tcQuery.append("app.MEM_BNK_ID||app.MEM_ZNE_ID||app.MEM_BRN_ID, ssi.BID, ");
				tcQuery.append("app.CGPAN, tc.TRM_AMOUNT_SANCTIONED, ");
				tcQuery.append("tco.TCO_OUTSTANDING_AMOUNT");
			 

				String strTcQuery = tcQuery.toString();
			   //if(Log.isDebugEnabled())
			
				Log.log(Log.DEBUG,"RIDAO","generateExposureDetails","tc query -- " + strTcQuery);

				wcQuery.append("SELECT ssi.SSI_STATE_NAME, ");
				wcQuery.append("app.MEM_BNK_ID||app.MEM_ZNE_ID||app.MEM_BRN_ID, ssi.BID, app.CGPAN, ");
				wcQuery.append("wc.WCP_FB_LIMIT_SANCTIONED, ");
				wcQuery.append("wco.WCO_FB_PRINCIPAL_OUTSTAND_AMT+wco.WCO_FB_INTEREST_OUTSTAND_AMT, ");
				wcQuery.append("max(wco.WCO_FB_OUTSTAND_ON_DT) ");
				wcQuery.append("FROM application_detail app, ssi_detail ssi, working_capital_detail wc, ");
				wcQuery.append("wc_outstanding_detail wco, scheme_master sch, promoter_detail pd ");
				wcQuery.append("WHERE wc.APP_REF_NO=app.APP_REF_NO ");
				wcQuery.append("AND app.SSI_REFERENCE_NUMBER=ssi.SSI_REFERENCE_NUMBER ");
				wcQuery.append("AND wc.APP_REF_NO=wco.APP_REF_NO ");
				wcQuery.append("AND app.APP_REF_NO=wco.APP_REF_NO ");
				wcQuery.append("AND app.APP_GUAR_START_DATE_TIME <= ? ");
				wcQuery.append("AND app.APP_STATUS = ? ");
				wcQuery.append("AND app.SCM_ID = sch.SCM_ID ");
				wcQuery.append("AND LTRIM(RTRIM(UPPER(sch.SCM_NAME)))=LTRIM(RTRIM(UPPER(?))) ");
				wcQuery.append("AND ssi.SSI_REFERENCE_NUMBER = pd.SSI_REFERENCE_NUMBER ");
			   if (! states[0].equalsIgnoreCase("ALL"))
			   {
				  wcQuery.append("AND ssi.SSI_STATE_NAME IN (");
				  wcQuery.append(statesQuery);
				  wcQuery.append(") ");
			   }
			   if (! ind[0].equalsIgnoreCase("ALL"))
			   {
				  wcQuery.append("AND ssi.SSI_INDUSTRY_NATURE IN (");
				  wcQuery.append(indQuery);
				  wcQuery.append(") ");
			   }
			   if (! mlis[0].equalsIgnoreCase("ALL"))
			   {
				  wcQuery.append("AND LTRIM(RTRIM(app.MEM_BNK_ID)) IN (");
				  wcQuery.append(mliQuery);
				  wcQuery.append(") ");
			   }
			   if (! gen[0].equalsIgnoreCase("A"))
			   {
				  wcQuery.append("AND pd.PMR_CHIEF_GENDER IN (");
				  wcQuery.append(genderQuery);
				  wcQuery.append(") ");
			   }
			   if (! sc[0].equalsIgnoreCase("ALL"))
			   {
				  wcQuery.append("AND pd.PMR_CHIEF_SOCIAL_CAT IN (");
				  wcQuery.append(scQuery);
				  wcQuery.append(") ");
			   }
				wcQuery.append("AND (wc.WCP_FB_LIMIT_SANCTIONED) ");
				wcQuery.append("BETWEEN ? AND ? ");
				wcQuery.append("AND app.APP_LOAN_TYPE IN (?) ");
				wcQuery.append("GROUP BY ssi.SSI_STATE_NAME, ");
				wcQuery.append("app.MEM_BNK_ID||app.MEM_ZNE_ID||app.MEM_BRN_ID, ssi.BID, ");
				wcQuery.append("app.CGPAN, wc.WCP_FB_LIMIT_SANCTIONED, ");
				wcQuery.append("wco.WCO_FB_PRINCIPAL_OUTSTAND_AMT+wco.WCO_FB_INTEREST_OUTSTAND_AMT");

				String strWcQuery = wcQuery.toString();
			   if(Log.isDebugEnabled())
				Log.log(Log.DEBUG,"RIDAO","generateExposureDetails","wc query -- " + strWcQuery);

				if (facility.equals("TC") || facility.equals("BO"))
				{
					exposureStmt = connection.prepareStatement(strTcQuery);

					exposureStmt.setDate(1, new java.sql.Date(gIssued.getTime()));
					exposureStmt.setString(2, "AP");
					exposureStmt.setString(3, "TC");
					exposureStmt.setString(4, "CC");
					exposureStmt.setString(5, scheme);
					exposureStmt.setDouble(6, sancAmtFrom);
					exposureStmt.setDouble(7, sancAmtTo);
				 
				
				
					int index=8;
					if (! states[0].equalsIgnoreCase("ALL"))
					{
					   size = states.length;
					   for (int i=0;i<size;i++)
					   {
						  exposureStmt.setString(index, states[i]);
						  if(Log.isDebugEnabled())
						  Log.log(Log.DEBUG,"RIDAO","generateExposureDetails","index , value  -- " + index + states[i]);
						  index++;
					   }
					}
				   if (! ind[0].equalsIgnoreCase("ALL"))
				   {				 
					size = ind.length;
					for (int i=0;i<size;i++)
					{
					   exposureStmt.setString(index, ind[i]);
					   if(Log.isDebugEnabled())
					   Log.log(Log.DEBUG,"RIDAO","generateExposureDetails","index , value  -- " + index + ind[i]);
					   index++;
					}
				   }
				   if (! mlis[0].equalsIgnoreCase("ALL"))
				   {				
					size = mlis.length;
					for (int i=0;i<size;i++)
					{
					   String tempMli = mlis[i].substring( mlis[i].indexOf("(")+1, mlis[i].length()-1 );
					   tempMli = tempMli.substring(0, 4);
					   exposureStmt.setString(index, tempMli);
					   if(Log.isDebugEnabled())
					   Log.log(Log.DEBUG,"RIDAO","generateExposureDetails","index , value  -- " + index + mlis[i].substring( mlis[i].indexOf("(")+1, mlis[i].length()-1 ));
					   index++;
					}
				   }
				   if (! gen[0].equalsIgnoreCase("A"))
				   {				
					size = gen.length;
					for (int i=0;i<size;i++)
					{
					   exposureStmt.setString(index, gen[i]);
					
					   if(Log.isDebugEnabled())
					   Log.log(Log.DEBUG,"RIDAO","generateExposureDetails","index , value  -- " + index + gen[i]);
					   index++;
					}
				   }
				   if (! sc[0].equalsIgnoreCase("ALL"))
				   {				
					size = sc.length;
					for (int i=0;i<size;i++)
					{
					   exposureStmt.setString(index, sc[i]);
					   if(Log.isDebugEnabled())
					   Log.log(Log.DEBUG,"RIDAO","generateExposureDetails","index , value  -- " + index + sc[i]);
					   index++;
					}
				   }
				 
				   if(Log.isDebugEnabled())
					Log.log(Log.DEBUG,"RIDAO","generateExposureDetails","index , value  -- " + index + sancAmtFrom);
					index++;

				 
				   if(Log.isDebugEnabled())
					Log.log(Log.DEBUG,"RIDAO","generateExposureDetails","index , value  -- " + index + sancAmtTo);
					index++;

				 
				   if(Log.isDebugEnabled())
					Log.log(Log.DEBUG,"RIDAO","generateExposureDetails","index , value  -- " + index + " TC");
					index++;

				 
				   if(Log.isDebugEnabled())
					Log.log(Log.DEBUG,"RIDAO","generateExposureDetails","index , value  -- " + index + " CC");
					index++;
				
				   Log.log(Log.DEBUG,"RIDAO","generateExposureDetails","Before executing TC query");
				
				   exposureDetails = exposureStmt.executeQuery();
				   exposureDetails.setFetchSize(10000);
				
				   Log.log(Log.DEBUG,"RIDAO","generateExposureDetails","After executing TC query");
				 
				   if(Log.isDebugEnabled())
				   Log.log(Log.DEBUG,"RIDAO","generateExposureDetails","tc exposure query executed ");
				
				   ArrayList exposureDetailsTemp=null;
				   int counter=0;
				
					while (exposureDetails.next())
					{
					   counter++;
						expDetails = new ExposureDetails();

						expDetails.setState(exposureDetails.getString(1));
					
					   if(Log.isDebugEnabled())
					   Log.log(Log.DEBUG,"RIDAO","generateExposureDetails","tc state " + expDetails.getState());
					
						expDetails.setMemberId(exposureDetails.getString(2));
					
					   if(Log.isDebugEnabled())
					   Log.log(Log.DEBUG,"RIDAO","generateExposureDetails","tc member id " + expDetails.getMemberId());
					
						expDetails.setBid(exposureDetails.getString(3));
					
					   if(Log.isDebugEnabled())
					   Log.log(Log.DEBUG,"RIDAO","generateExposureDetails","tc bid " + expDetails.getBid());
					
						expDetails.setCgpan(exposureDetails.getString(4));
					
					   if(Log.isDebugEnabled())
					   Log.log(Log.DEBUG,"RIDAO","generateExposureDetails","tc cgpan " + expDetails.getCgpan());
					
						expDetails.setSancAmt(exposureDetails.getDouble(5));
					
					   if(Log.isDebugEnabled())
					   Log.log(Log.DEBUG,"RIDAO","generateExposureDetails","tc sanc amt " + expDetails.getSancAmt());
					
						expDetails.setOsAmt(exposureDetails.getDouble(6));
					
					   if(Log.isDebugEnabled())
					   Log.log(Log.DEBUG,"RIDAO","generateExposureDetails","tc os amt " + expDetails.getOsAmt());

						if (! tcBids.contains(expDetails.getBid()))
						{
							tcBids.add(expDetails.getBid());
						}

						tcDetails.add(expDetails);
					}
				   Log.log(Log.DEBUG,"RIDAO","generateExposureDetails","After Iterating ");
				
					exposureReportDetail.setTcExposureDetails(tcDetails);

					exposureDetails.close();
					exposureDetails=null;
					exposureStmt.close();
					exposureStmt=null;

					size = tcBids.size();
				   if(Log.isDebugEnabled())
				   Log.log(Log.DEBUG,"RIDAO","generateExposureDetails","size " + size);
				
//				   System.out.println("BId size " + size);
				
//				   System.out.println("TC size " + tcDetails.size());
				
//				   System.out.println("count " + counter);
				
					if (size>0)
					{
					   bidQuery.append("SELECT cd.BID, COUNT(cd.CGCLAN), SUM(cd.CLM_APPROVED_AMT) ");
					   bidQuery.append("FROM claim_detail cd, claim_application_amount ca, application_detail app ");
					   bidQuery.append("WHERE cd.CLM_REF_NO = ca.CLM_REF_NO ");
					   bidQuery.append("AND ca.CGPAN = app.CGPAN ");
					   bidQuery.append("AND app.APP_LOAN_TYPE IN (?, ?) ");
					   String bidsQuery = "";
					   boolean additional=false;
					   int count=0;
						for (int i=0;i<size;i++)
						{
							if (size-1==i)
							{
								tempBuffer.append(" ?");
							}
							else
							{
								tempBuffer.append(" ?,");
							}
							count++;
							if (count==999 && ! additional)
							{
							   bidsQuery = tempBuffer.toString();
							   tempBuffer.setLength(0);
							   bidQuery.append("AND (cd.BID IN (");
							
							   if(Log.isDebugEnabled())
							   Log.log(Log.DEBUG,"RIDAO","generateExposureDetails","length " + i + " " + bidsQuery.length());
							   bidQuery.append(bidsQuery.substring(0, bidsQuery.length()-1));
							   bidQuery.append(")");
							   additional=true;
							   count=0;
							}
							else if (count==999 && additional)
							{
							   bidsQuery = tempBuffer.toString();
							   tempBuffer.setLength(0);
							   bidQuery.append(" OR cd.BID IN (");
							
							   if(Log.isDebugEnabled())
							   Log.log(Log.DEBUG,"RIDAO","generateExposureDetails","length " + i + " " + bidsQuery.length());
														
							   bidQuery.append(bidsQuery.substring(0, bidsQuery.length()-1));
							   bidQuery.append(")");
							   additional=true;
							   count=0;
							}
						}

					   if (!tempBuffer.toString().equals("") && additional)
					   {
						   bidsQuery = tempBuffer.toString();
						   bidQuery.append(" OR cd.BID IN (");
						   bidQuery.append(bidsQuery.substring(0, bidsQuery.length()));
						   bidQuery.append("))");
						   bidQuery.append(" GROUP BY cd.BID");
					   }
					   else if (!tempBuffer.toString().equals("") && ! additional)
					   {
						   bidsQuery = tempBuffer.toString();
						   bidQuery.append(" AND cd.BID IN (");
						   bidQuery.append(bidsQuery.substring(0, bidsQuery.length()));
						   bidQuery.append(")");
						   bidQuery.append(" GROUP BY cd.BID");						
					   }
					   else
					   {
						   bidQuery.append(") GROUP BY cd.BID");
					   }					
						tempBuffer.setLength(0);

						String strBidQuery=bidQuery.toString();
					
					   if(Log.isDebugEnabled())
					   Log.log(Log.DEBUG,"RIDAO","generateExposureDetails","tc bid query  -- " + strBidQuery);

						claimStmt=connection.prepareStatement(strBidQuery);
						claimStmt.setString(1, "TC");
						claimStmt.setString(2, "CC");
						index=3;
						for (int i=0;i<size;i++)
						{
						   claimStmt.setString(index, (String) tcBids.get(i));
						
						   if(Log.isDebugEnabled())
						   Log.log(Log.DEBUG,"RIDAO","generateExposureDetails","tc bid index , value  -- " + index + (String) tcBids.get(i));
						   index++;
						}

					   Log.log(Log.DEBUG,"RIDAO","generateExposureDetails","before executing bid query ");
					
						bidClaimDetails=claimStmt.executeQuery();
					 
					   Log.log(Log.DEBUG,"RIDAO","generateExposureDetails","After executing bid query ");
					
						tcDetails=exposureReportDetail.getTcExposureDetails();
						int tcDetailsSize=tcDetails.size();
						while (bidClaimDetails.next())
						{
							String tempBid=bidClaimDetails.getString(1);
							int tempCount=bidClaimDetails.getInt(2);
							double tempAmt=bidClaimDetails.getDouble(3);

							ExposureDetails tempExpDetails = new ExposureDetails();
							for (int i=0;i<tcDetailsSize;i++)
							{
								tempExpDetails = (ExposureDetails) tcDetails.get(i);
								if (tempExpDetails.getBid().equals(tempBid))
								{
									tempExpDetails.setNoOfClaims(tempCount);
									tempExpDetails.setTotalClaim(tempAmt);
									tcDetails.set(i, tempExpDetails);
									break;
								}
							}
						}
					 
					   Log.log(Log.DEBUG,"RIDAO","generateExposureDetails","After iterating bids results ");
					
						exposureReportDetail.setTcExposureDetails(tcDetails);
					}
				}
			   exposureReportDetail.setTcExposureDetails(tcDetails);

				if (facility.equals("WC") || facility.equals("BO"))
				{
					exposureStmt = connection.prepareStatement(strWcQuery);

					exposureStmt.setDate(1, new java.sql.Date(gIssued.getTime()));
					exposureStmt.setString(2, "AP");
					exposureStmt.setString(3, scheme);
					int index=4;
				   if (! states[0].equalsIgnoreCase("ALL"))
				   {
					  size = states.length;
					  for (int i=0;i<size;i++)
					  {
						 exposureStmt.setString(index, states[i]);
					   if(Log.isDebugEnabled())
						 Log.log(Log.DEBUG,"RIDAO","generateExposureDetails","index , value  -- " + index + states[i]);
						 index++;
					  }
				   }
				  if (! ind[0].equalsIgnoreCase("ALL"))
				  {				 
				   size = ind.length;
				   for (int i=0;i<size;i++)
				   {
					  exposureStmt.setString(index, ind[i]);
					  if(Log.isDebugEnabled())
					  Log.log(Log.DEBUG,"RIDAO","generateExposureDetails","index , value  -- " + index + ind[i]);
					  index++;
				   }
				  }
				  if (! mlis[0].equalsIgnoreCase("ALL"))
				  {				
				   size = mlis.length;
				   for (int i=0;i<size;i++)
				   {
					  String tempMli = mlis[i].substring( mlis[i].indexOf("(")+1, mlis[i].length()-1 );
					  tempMli = tempMli.substring(0, 4);
					  exposureStmt.setString(index, tempMli);
					  if(Log.isDebugEnabled())
					  Log.log(Log.DEBUG,"RIDAO","generateExposureDetails","index , value  -- " + index + mlis[i].substring( mlis[i].indexOf("(")+1, mlis[i].length()-1 ));
					  index++;
				   }
				  }
				  if (! gen[0].equalsIgnoreCase("A"))
				  {				
				   size = gen.length;
				   for (int i=0;i<size;i++)
				   {
					  exposureStmt.setString(index, gen[i]);
					  if(Log.isDebugEnabled())
					  Log.log(Log.DEBUG,"RIDAO","generateExposureDetails","index , value  -- " + index + gen[i]);
					  index++;
				   }
				  }
				  if (! sc[0].equalsIgnoreCase("ALL"))
				  {				
				   size = sc.length;
				   for (int i=0;i<size;i++)
				   {
					  exposureStmt.setString(index, sc[i]);
					  if(Log.isDebugEnabled())
					  Log.log(Log.DEBUG,"RIDAO","generateExposureDetails","index , value  -- " + index + sc[i]);
					  index++;
				   }
				  }

					exposureStmt.setDouble(index, sancAmtFrom);
				   if(Log.isDebugEnabled())
					Log.log(Log.DEBUG,"RIDAO","generateExposureDetails","index , value  -- " + index + sancAmtFrom);
					index++;

					exposureStmt.setDouble(index, sancAmtTo);
				   if(Log.isDebugEnabled())
					Log.log(Log.DEBUG,"RIDAO","generateExposureDetails","index , value  -- " + index + sancAmtTo);
					index++;

					exposureStmt.setString(index, "WC");
				   if(Log.isDebugEnabled())
					Log.log(Log.DEBUG,"RIDAO","generateExposureDetails","index , value  -- " + index + " WC");
					index++;

					exposureDetails = exposureStmt.executeQuery();

					while (exposureDetails.next())
					{
						expDetails = new ExposureDetails();

						expDetails.setState(exposureDetails.getString(1));
						expDetails.setMemberId(exposureDetails.getString(2));
						expDetails.setBid(exposureDetails.getString(3));
						expDetails.setCgpan(exposureDetails.getString(4));
						expDetails.setSancAmt(exposureDetails.getDouble(5));
						expDetails.setOsAmt(exposureDetails.getDouble(6));

						if (! wcBids.contains(expDetails.getBid()))
						{
							wcBids.add(expDetails.getBid());
						}

						wcDetails.add(expDetails);
					}
					exposureReportDetail.setWcExposureDetails(wcDetails);

					exposureDetails.close();
					exposureDetails=null;
					exposureStmt.close();
					exposureStmt=null;

					size = wcBids.size();
					if (size>0)
					{
						bidQuery.setLength(0);
						bidQuery.append("SELECT cd.BID, COUNT(cd.CGCLAN), SUM(cd.CLM_APPROVED_AMT) ");
						bidQuery.append("FROM claim_detail cd, claim_application_amount ca, application_detail app ");
						bidQuery.append("WHERE cd.CLM_REF_NO = ca.CLM_REF_NO ");
						bidQuery.append("AND ca.CGPAN = app.CGPAN ");
						bidQuery.append("AND app.APP_LOAN_TYPE IN (?) ");

					   String bidsQuery = "";
					   boolean additional=false;
					   int count=0;
						for (int i=0;i<size;i++)
						{
							if (size-1==i)
							{
								tempBuffer.append(" ?");
							}
							else
							{
								tempBuffer.append(" ?,");
							}
							count++;
							if (count==999 && ! additional)
							{
							   bidsQuery = tempBuffer.toString();
							   tempBuffer.setLength(0);
							   bidQuery.append("AND (cd.BID IN (");
							   if(Log.isDebugEnabled())
							   Log.log(Log.DEBUG,"RIDAO","generateExposureDetails","length " + i + " " + bidsQuery.length());
							   bidQuery.append(bidsQuery.substring(0, bidsQuery.length()-1));
							   bidQuery.append(")");
							   additional=true;
							   count=0;
							}
							else if (count==999 && additional)
							{
							   bidsQuery = tempBuffer.toString();
							   tempBuffer.setLength(0);
							   bidQuery.append(" OR cd.BID IN (");
							   if(Log.isDebugEnabled())
							   Log.log(Log.DEBUG,"RIDAO","generateExposureDetails","length " + i + " " + bidsQuery.length());							
							   bidQuery.append(bidsQuery.substring(0, bidsQuery.length()-1));
							   bidQuery.append(")");
							   additional=true;
							   count=0;
							}
						}

					   if (!tempBuffer.toString().equals("") && additional)
					   {
						   bidsQuery = tempBuffer.toString();
						   bidQuery.append(" OR cd.BID IN (");
						   bidQuery.append(bidsQuery.substring(0, bidsQuery.length()));
						   bidQuery.append("))");
						   bidQuery.append(" GROUP BY cd.BID");
					   }
					   else if (!tempBuffer.toString().equals("") && ! additional)
					   {
						   bidsQuery = tempBuffer.toString();
						   bidQuery.append(" AND cd.BID IN (");
						   bidQuery.append(bidsQuery.substring(0, bidsQuery.length()));
						   bidQuery.append(")");
						   bidQuery.append(" GROUP BY cd.BID");						
					   }
					   else
					   {
						   bidQuery.append(") GROUP BY cd.BID");
					   }
					
						tempBuffer.setLength(0);

						String strBidQuery=bidQuery.toString();
					   if(Log.isDebugEnabled())
						Log.log(Log.DEBUG,"RIDAO","generateExposureDetails","wc bid query " + strBidQuery);

						claimStmt=connection.prepareStatement(strBidQuery);
						claimStmt.setString(1, "WC");
						index=2;
						for (int i=0;i<size;i++)
						{
						   claimStmt.setString(index, (String) wcBids.get(i));
						   if(Log.isDebugEnabled())
						   Log.log(Log.DEBUG,"RIDAO","generateExposureDetails","tc bid index , value  -- " + index + (String) wcBids.get(i));
						   index++;
						}

						bidClaimDetails=claimStmt.executeQuery();

						wcDetails=exposureReportDetail.getWcExposureDetails();
						int wcDetailsSize=wcDetails.size();
						while (bidClaimDetails.next())
						{
							String tempBid=bidClaimDetails.getString(1);
							int tempCount=bidClaimDetails.getInt(2);
							double tempAmt=bidClaimDetails.getDouble(3);

							ExposureDetails tempExpDetails = new ExposureDetails();
							for (int i=0;i<wcDetailsSize;i++)
							{
								tempExpDetails = (ExposureDetails) wcDetails.get(i);
								if (tempExpDetails.getBid().equals(tempBid))
								{
									tempExpDetails.setNoOfClaims(tempCount);
									tempExpDetails.setTotalClaim(tempAmt);
									wcDetails.set(i, tempExpDetails);
									break;
								}
							}
						}
						exposureReportDetail.setWcExposureDetails(wcDetails);
					}
				}
			   exposureReportDetail.setWcExposureDetails(wcDetails);

			}
			catch (SQLException sqlException)
			{
			   Log.log(Log.ERROR,"RIDAO","generateExposureDetails",sqlException.getMessage());
			   Log.logException(sqlException);
			   throw new DatabaseException(sqlException.getMessage());
			}
			finally
			{
			   DBConnection.freeConnection(connection);
			}
		 
		   Log.log(Log.INFO,"RIDAO","generateExposureDetails","Exited");
		
			return exposureReportDetail;
		}

	/**
	 * This method generates the sub scheme report valid between the given from and to dates.
	 * @returns ArrayList
	 */
	 public ArrayList getSubSchemes(Date fromDate, Date toDate) throws DatabaseException, MessageException
	 {
		Connection connection;
		PreparedStatement pStmt;
		CallableStatement cStmt;
		ArrayList returnSubSchemes = new ArrayList();
		ResultSet subSchemes;
	 	
		connection = DBConnection.getConnection();
		try
		{
			java.sql.Date sqlFromDate = new java.sql.Date(fromDate.getTime());
			java.sql.Date sqlToDate=null;
			if (toDate!=null && !toDate.toString().equals(""))
			{
				sqlToDate = new java.sql.Date(toDate.getTime());
			}
			
			Log.log(Log.ERROR,"RIDAO","getSubSchemes","from date  " + fromDate);
			Log.log(Log.ERROR,"RIDAO","getSubSchemes","to date  " + toDate);
			Log.log(Log.ERROR,"RIDAO","getSubSchemes","sql from date  " + sqlFromDate);
			Log.log(Log.ERROR,"RIDAO","getSubSchemes","sql to date  " + sqlToDate);
			
			String query ="";
			if (toDate==null || toDate.toString().equals(""))
			{
				Log.log(Log.ERROR,"RIDAO","getSubSchemes","to date null");
				query = "select ssh_name, ssh_id, ssh_valid_from_dt, ssh_valid_to_dt " +
					"from sub_scheme " +
					"where (ssh_valid_to_dt is null and ssh_valid_from_dt >= ?) or " +
					"(ssh_valid_to_dt is not null and (? between ssh_valid_from_dt and ssh_valid_to_dt))";
					   
				pStmt = connection.prepareStatement(query);
				pStmt.setDate(1, sqlFromDate);
				pStmt.setDate(2, sqlFromDate);
			}
			else
			{
				Log.log(Log.ERROR,"RIDAO","getSubSchemes","to date not null");
				query = "select ssh_name, ssh_id, ssh_valid_from_dt, ssh_valid_to_dt " +
					"from sub_scheme " +
					"where (ssh_valid_to_dt is null and ssh_valid_from_dt >= ? and ssh_valid_from_dt <= ?) or " +
					"(ssh_valid_to_dt is not null and (? between ssh_valid_from_dt and ssh_valid_to_dt " +
					"and ? between ssh_valid_from_dt and ssh_valid_to_dt))";
				pStmt = connection.prepareStatement(query);
				pStmt.setDate(1, sqlFromDate);
				pStmt.setDate(2, sqlToDate);
				pStmt.setDate(3, sqlFromDate);
				pStmt.setDate(4, sqlToDate);
			}
			subSchemes = pStmt.executeQuery();
			
			SubSchemeParameters subSchemeParameters;
			
			while (subSchemes.next())
			{
				subSchemeParameters = new SubSchemeParameters();
				subSchemeParameters.setSubScheme(subSchemes.getString(1));
				Log.log(Log.ERROR,"RIDAO","getSubSchemes","sub scheme name  " + subSchemeParameters.getSubScheme());
				subSchemeParameters.setSubSchemeId(subSchemes.getString(2));
				Log.log(Log.ERROR,"RIDAO","getSubSchemes","sub scheme id  " + subSchemeParameters.getSubSchemeId());
				subSchemeParameters.setValidFromDate(DateHelper.sqlToUtilDate(subSchemes.getDate(3)));
				Log.log(Log.ERROR,"RIDAO","getSubSchemes","sub scheme valid from date  " + subSchemeParameters.getValidFromDate());
				java.sql.Date sqlDate = subSchemes.getDate(4);
				Log.log(Log.ERROR,"RIDAO","getSubSchemes","sub scheme valid to date  " + subSchemeParameters.getValidToDate());
				if (sqlDate!=null)
				{
					subSchemeParameters.setValidToDate(DateHelper.sqlToUtilDate(sqlDate));
				}
				
				returnSubSchemes.add(subSchemeParameters);
				subSchemeParameters=null;
			}
			subSchemes.close();
			subSchemes=null;
			pStmt=null;
			
			Log.log(Log.ERROR,"RIDAO","getSubSchemes","size  " + returnSubSchemes.size());
			
			if (returnSubSchemes.size()==0)
			{
				throw new MessageException("No Sub Schemes Found");
			}
			
			for (int i=0;i<returnSubSchemes.size();i++)
			{
				subSchemeParameters = (SubSchemeParameters) returnSubSchemes.get(i);
				Log.log(Log.ERROR,"RIDAO","getSubSchemes","getting parameters for sub scheme id  " + subSchemeParameters.getSubSchemeId());
				ArrayList values = getSubSchemeParameters(subSchemeParameters.getSubSchemeId());
				ArrayList states = (ArrayList)values.get(0);
				String[] arrStates =  new String[states.size()];
				
				ArrayList mli = (ArrayList)values.get(1);
				String[] arrMli =  new String[mli.size()];
				
				ArrayList industry = (ArrayList)values.get(2);
				String[] arrIndustry =  new String[industry.size()];
				
				ArrayList gender = (ArrayList)values.get(3);
				String[] arrGender =  new String[gender.size()];
				
				ArrayList socialCat = (ArrayList)values.get(4);
				String[] arrSocialCat =  new String[socialCat.size()];
				
				for (int j=0;j<states.size();j++)
				{
					arrStates[j] = (String)states.get(j);
				}
				for (int j=0;j<mli.size();j++)
				{
					arrMli[j] = (String)mli.get(j);
				}
				for (int j=0;j<industry.size();j++)
				{
					arrIndustry[j] = (String)industry.get(j);
				}
				for (int j=0;j<gender.size();j++)
				{
					arrGender[j] = (String)gender.get(j);
				}
				for (int j=0;j<socialCat.size();j++)
				{
					arrSocialCat[j] = (String)socialCat.get(j);
				}
				subSchemeParameters.setState(arrStates);
				Log.log(Log.ERROR,"RIDAO","getSubSchemes","states for " + subSchemeParameters.getSubSchemeId() + " - " + arrStates.toString());
				subSchemeParameters.setIndustry(arrIndustry);
				Log.log(Log.ERROR,"RIDAO","getSubSchemes","industries for " + subSchemeParameters.getSubSchemeId() + " - " + arrIndustry.toString());
				subSchemeParameters.setMli(arrMli);
				Log.log(Log.ERROR,"RIDAO","getSubSchemes","mlis for " + subSchemeParameters.getSubSchemeId() + " - " + arrMli.toString());
				subSchemeParameters.setSocialCategory(arrSocialCat);
				Log.log(Log.ERROR,"RIDAO","getSubSchemes","social cats for " + subSchemeParameters.getSubSchemeId() + " - " + arrSocialCat.toString());
				subSchemeParameters.setGender(arrGender);
				Log.log(Log.ERROR,"RIDAO","getSubSchemes","gender for " + subSchemeParameters.getSubSchemeId() + " - " + arrGender.toString());
				
				returnSubSchemes.set(i, subSchemeParameters);
/*				subSchemeParameters=null;
				arrStates=null;
				arrIndustry=null;
				arrMli=null;
				arrSocialCat=null;
				arrGender=null;
				states=null;
				industry=null;
				mli=null;
				socialCat=null;
				gender=null;
				values=null;*/
			}
		}
		catch(SQLException exp)
		{
			throw new DatabaseException(exp.getMessage());
		}
		finally
		{
			DBConnection.freeConnection(connection);
		}
		return returnSubSchemes;
	 }

	public ArrayList getSubSchemeParameters(String subSchemeId) throws DatabaseException
	{
		Connection connection;
		CallableStatement stmt;
		ArrayList states = new ArrayList();
		ArrayList mli = new ArrayList();
		ArrayList gender = new ArrayList();
		ArrayList industry = new ArrayList();
		ArrayList socialCat = new ArrayList();
		ArrayList returnList = new ArrayList();
		
		ResultSet rsStates;
		ResultSet rsMli;
		ResultSet rsGender;
		ResultSet rsIndustry;
		ResultSet rsSocialCat;
		
		connection = DBConnection.getConnection();
		try
		{
			stmt = connection.prepareCall("{?=call packGetSubSchemeParameters.funcGetSubSchemeParameters(?,?,?,?,?,?,?)}");

			stmt.setString(2, subSchemeId);

			stmt.registerOutParameter(1, java.sql.Types.INTEGER);
			stmt.registerOutParameter(3, Constants.CURSOR);
			stmt.registerOutParameter(4, Constants.CURSOR);
			stmt.registerOutParameter(5, Constants.CURSOR);
			stmt.registerOutParameter(6, Constants.CURSOR);
			stmt.registerOutParameter(7, Constants.CURSOR);
			stmt.registerOutParameter(8, java.sql.Types.VARCHAR);

			stmt.execute();
			int status = stmt.getInt(1);

			Log.log(Log.DEBUG,"RIDAO","getSubScheme","value returned from funcGetSubSchemeParameters " + status);

			if (status == Constants.FUNCTION_FAILURE)
			{
				String errDesc = stmt.getString(8);

				Log.log(Log.DEBUG,"RIDAO","getSubScheme","exception in getting sub scheme parameters " + errDesc);

				stmt.close();
				stmt = null;

				throw new DatabaseException(errDesc);
			}

			rsStates = (ResultSet) stmt.getObject(3);
			rsMli = (ResultSet) stmt.getObject(4);
			rsIndustry = (ResultSet) stmt.getObject(5);
			rsGender = (ResultSet) stmt.getObject(6);
			rsSocialCat = (ResultSet) stmt.getObject(7);

			while (rsStates.next())
			{
				states.add(rsStates.getString(1));
			}
			while (rsMli.next())
			{
				mli.add(rsMli.getString(1));
			}
			while (rsIndustry.next())
			{
				industry.add(rsIndustry.getString(1));
			}
			while (rsGender.next())
			{
				gender.add(rsGender.getString(1));
			}
			while (rsSocialCat.next())
			{
				socialCat.add(rsSocialCat.getString(1));
			}
			rsStates.close();
			rsStates=null;
			rsMli.close();
			rsMli=null;
			rsIndustry.close();
			rsIndustry=null;
			rsGender.close();
			rsGender=null;
			rsSocialCat.close();
			rsSocialCat=null;
			stmt.close();
			stmt = null;
			
			returnList.add(states);
			returnList.add(mli);
			returnList.add(industry);
			returnList.add(gender);
			returnList.add(socialCat);
		}
		catch(SQLException exp)
		{
			throw new DatabaseException(exp.getMessage());
		}
		finally
		{
			DBConnection.freeConnection(connection);
		}
		return returnList;
	}
	
	/**
	 * this method retrieves the global limits for the scheme and subscheme passed
	 */
	
	public GlobalLimits getGlobalLimits(String scheme,String subScheme)throws DatabaseException
	{
		Log.log(Log.INFO,"RIDAO","getGlobalLimits","Entered");
		
		Connection connection = DBConnection.getConnection();
		GlobalLimits globalLimits = new GlobalLimits();	
		
		try{	
			
			
			CallableStatement globalLimitStmt=connection.prepareCall("{?=call funcGetGlobalLimits(?,?,?,?,?,?)}");
			
			globalLimitStmt.setString(2,scheme);
			globalLimitStmt.setString(3,subScheme);
			globalLimitStmt.registerOutParameter(4,java.sql.Types.DOUBLE);				
			globalLimitStmt.registerOutParameter(5,java.sql.Types.DATE);
			globalLimitStmt.registerOutParameter(6,java.sql.Types.DATE);
			
			globalLimitStmt.registerOutParameter(1,java.sql.Types.INTEGER);
			globalLimitStmt.registerOutParameter(7,java.sql.Types.VARCHAR);
			
			globalLimitStmt.execute();
			
			int status=globalLimitStmt.getInt(1);
			
			if (status==Constants.FUNCTION_FAILURE)
			{
				String err=globalLimitStmt.getString(7);
				Log.log(Log.DEBUG,"RIDAO","getGlobalLimits","exception from sp -- " + err);
				globalLimitStmt.close();
				globalLimitStmt=null;
				throw new DatabaseException(err);
			}
			else{
				
				globalLimits.setUpperLimit(globalLimitStmt.getDouble(4));
				if(globalLimitStmt.getDate(5)!=null)
				{
					globalLimits.setValidFromDate(DateHelper.sqlToUtilDate(globalLimitStmt.getDate(5)));
				}
				Log.log(Log.INFO,"RIDAO","getGlobalLimits","globalLimits.setValidFromDate :" + DateHelper.sqlToUtilDate(globalLimitStmt.getDate(5)));
				
				if(globalLimitStmt.getDate(6)!=null)
				{
					globalLimits.setValidToDate(DateHelper.sqlToUtilDate(globalLimitStmt.getDate(6)));
				}
				Log.log(Log.INFO,"RIDAO","getGlobalLimits","globalLimits.setValidToDate :" + DateHelper.sqlToUtilDate(globalLimitStmt.getDate(6)));
				
			}
			
		}catch (SQLException sqlException)
		{
			Log.log(Log.ERROR,"RIDAO","getGlobalLimits",sqlException.getMessage());
			Log.logException(sqlException);
			 throw new DatabaseException(sqlException.getMessage());
		}
		finally
		{
			DBConnection.freeConnection(connection);
		}
		
		return globalLimits;
		
	}


	/**
	 * this method retrieves the global limits for the scheme and subscheme passed
	 */
	
	public UserLimits getUserLimits(String desigName)throws DatabaseException
	{
		Log.log(Log.INFO,"RIDAO","getGlobalLimits","Entered");
		
		Connection connection = DBConnection.getConnection();
		UserLimits userLimits= new UserLimits();	
		
		try{	
			
			
			CallableStatement userLimitStmt=connection.prepareCall("{?=call packGetClmLimitDtls.funcGetClmLimitDtls(?,?,?)}");
			
			userLimitStmt.setString(2,desigName);
			
			userLimitStmt.registerOutParameter(3,Constants.CURSOR);				
			
			userLimitStmt.registerOutParameter(1,java.sql.Types.INTEGER);
			userLimitStmt.registerOutParameter(4,java.sql.Types.VARCHAR);
			
			userLimitStmt.execute();
			
			int status=userLimitStmt.getInt(1);
			
			if (status==Constants.FUNCTION_FAILURE)
			{
				String err=userLimitStmt.getString(7);
				Log.log(Log.DEBUG,"RIDAO","getGlobalLimits","exception from sp -- " + err);
				userLimitStmt.close();
				userLimitStmt=null;
				throw new DatabaseException(err);
			}
			else{
				
				ResultSet results = (ResultSet)userLimitStmt.getObject(3);
				while(results.next())
				{
					userLimits.setUpperClaimsApprovalLimit(results.getDouble(1));
					userLimits.setClaimsLimitValidFrom(DateHelper.sqlToUtilDate(results.getDate(2)));
					userLimits.setClaimsLimitValidTo(DateHelper.sqlToUtilDate(results.getDate(3)));
					userLimits.setUpperApplicationApprovalLimit(results.getDouble(4));
					userLimits.setApplicationLimitValidFrom(DateHelper.sqlToUtilDate(results.getDate(5)));
					userLimits.setApplicationLimitValidTo(DateHelper.sqlToUtilDate(results.getDate(6)));
				}
				
			}
			
		}catch (SQLException sqlException)
		{
			Log.log(Log.ERROR,"RIDAO","getGlobalLimits",sqlException.getMessage());
			Log.logException(sqlException);
			 throw new DatabaseException(sqlException.getMessage());
		}
		finally
		{
			DBConnection.freeConnection(connection);
		}
		
		return userLimits;
		
	}

	public ParticipatingBankLimit getParticipatingBanksLimits(String memberId, String bankName)throws DatabaseException
	{
		Log.log(Log.INFO,"RIDAO","getParticipatingBanksLimits","Entered");
		
		Connection connection = DBConnection.getConnection();
		ParticipatingBankLimit participatingBankLimit= new ParticipatingBankLimit();	
		
		try{	
			
			
			CallableStatement partLimitStmt=connection.prepareCall("{?=call funcGetPartBankLimits(?,?,?,?,?,?,?,?)}");

			memberId = memberId.substring(1, memberId.indexOf(')'));			
			String bnkId = memberId.substring(0,4);
			String zneId = memberId.substring(4,8);
			String brnId = memberId.substring(8,12);
			
			partLimitStmt.registerOutParameter(1,java.sql.Types.INTEGER);
			
			partLimitStmt.setString(2,bnkId);
			partLimitStmt.setString(3,zneId);
			partLimitStmt.setString(4,brnId);
			partLimitStmt.setString(5,bankName.substring(0, bankName.indexOf(',')));
			
			partLimitStmt.registerOutParameter(6,java.sql.Types.DOUBLE);
			partLimitStmt.registerOutParameter(7,java.sql.Types.DATE);
			partLimitStmt.registerOutParameter(8,java.sql.Types.DATE);
			partLimitStmt.registerOutParameter(9,java.sql.Types.VARCHAR);
			
			partLimitStmt.execute();
			
			int status=partLimitStmt.getInt(1);
			
			if (status==Constants.FUNCTION_FAILURE)
			{
				String err=partLimitStmt.getString(9);
				Log.log(Log.DEBUG,"RIDAO","getParticipatingBanksLimits","exception from sp -- " + err);
				partLimitStmt.close();
				partLimitStmt=null;
				throw new DatabaseException(err);
			}
			else{
				participatingBankLimit.setAmount(partLimitStmt.getDouble(6));
				participatingBankLimit.setValidFrom(DateHelper.sqlToUtilDate(partLimitStmt.getDate(7)));
				participatingBankLimit.setValidTo(DateHelper.sqlToUtilDate(partLimitStmt.getDate(8)));
			}
			partLimitStmt.close();
			partLimitStmt=null;			
		}catch (SQLException sqlException)
		{
			Log.log(Log.ERROR,"RIDAO","getParticipatingBanksLimits",sqlException.getMessage());
			Log.logException(sqlException);
			 throw new DatabaseException(sqlException.getMessage());
		}
		finally
		{
			DBConnection.freeConnection(connection);
		}
		
		return participatingBankLimit;
		
	}
	 
}
