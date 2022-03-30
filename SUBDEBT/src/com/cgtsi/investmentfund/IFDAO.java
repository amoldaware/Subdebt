package com.cgtsi.investmentfund;

/*************************************************************
   *
   * Name of the class: IFDAO
   * This is the main class which interacts with the database. All the database
   * calls are routed via this class.
   *
   * @author : Nithyalakshmi P
   * @version:
   * @since:
   **************************************************************/

import com.cgtsi.admin.Parameters;
import com.cgtsi.admin.User;
import com.cgtsi.application.Application;
import com.cgtsi.application.TermLoan;
import com.cgtsi.application.WorkingCapital;
import com.cgtsi.claim.ClaimConstants;
import com.cgtsi.claim.SettlementDetail;
import com.cgtsi.common.DatabaseException;
import com.cgtsi.common.MessageException;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.Vector;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.Set;
import java.util.Iterator;

import com.cgtsi.receiptspayments.RpConstants;
import com.cgtsi.receiptspayments.RpDAO;
import com.cgtsi.receiptspayments.RpProcessor;
import com.cgtsi.receiptspayments.Voucher;
import com.cgtsi.receiptspayments.VoucherDetail;
import com.cgtsi.util.DBConnection;
import com.cgtsi.util.DateHelp;
import com.cgtsi.common.Constants;
import com.cgtsi.util.DateHelper;
import com.cgtsi.common.Log;
import com.cgtsi.guaranteemaintenance.NPADetails;

public class IFDAO {
	public static final String TERM_ANNUAL = "ANNUAL";
	public static final String TERM_SHORT = "SHORTTERM";
	public static final String BUY_REQUEST = "B";
	public static final String SELL_REQUEST = "S";

	public static final String BANK_NAME="IDBI";

	private final String MONTH="M";
	private final String YEAR="Y";

	private final int MONTH_VALUE=30;

	private final int YEAR_VALUE=365;

	private final int MONTHLY_COMPOUNDING=12;
	private final int QUARTERLY_COMPOUNDING=4;
	private final int HALF_YEARLY_COMPOUNDING=2;
	private final int YEARLY_COMPOUNDING=1;

	public IFDAO() {
	}

	public ArrayList chequeDetailsModify(java.sql.Date startDate,java.sql.Date endDate)
								 throws DatabaseException
	{
		Log.log(Log.INFO,"IFDAO","chequeDetailsModify","Entered");
		ArrayList chequeArray = new ArrayList();
		ResultSet chequeResult;
		Connection connection = DBConnection.getConnection();
		CallableStatement chequeStmt = null;
		ChequeDetails chequeDetails  = null;

		try
		{
			chequeStmt = connection.prepareCall("{? = call packGetAllChequeInserts.funcGetAllChequeInserts(?,?,?,?)}");
			chequeStmt.setDate(2,startDate);
			chequeStmt.setDate(3,endDate);
			chequeStmt.registerOutParameter(1, Types.INTEGER);
			chequeStmt.registerOutParameter(4,Constants.CURSOR);
			chequeStmt.registerOutParameter(5, Types.VARCHAR);

			chequeStmt.executeQuery();

			int status = chequeStmt.getInt(1);

			if(status == Constants.FUNCTION_FAILURE)
			{
				String errorCode = chequeStmt.getString(5);
				Log.log(Log.ERROR,"IFDAO","chequeDetailsModify","SP returns a 1." +
										" Error code is :" + errorCode);
				chequeStmt.close();
				chequeStmt = null;
			}
			else if(status == Constants.FUNCTION_SUCCESS)
			{
				chequeResult = (ResultSet)chequeStmt.getObject(4);
				while(chequeResult.next())
				{
					chequeDetails  = new ChequeDetails();
					chequeDetails.setBankName(chequeResult.getString(1));
					//System.out.println("1:"+chequeResult.getString(1));
					chequeDetails.setChequeNumber(chequeResult.getString(2));
					//System.out.println("2:"+chequeResult.getString(2));
					chequeDetails.setChequeAmount(chequeResult.getDouble(3));
					//System.out.println("3:"+chequeResult.getDouble(3));
					chequeDetails.setChequeDate(chequeResult.getDate(4));
					//System.out.println("4:"+chequeResult.getDate(4));
					chequeDetails.setChequeIssuedTo(chequeResult.getString(5));
					//System.out.println("5:"+chequeResult.getString(5));
					chequeDetails.setChequeId(chequeResult.getString(6));
					//System.out.println("6:"+chequeResult.getString(6));
					chequeDetails.setExpiryDate(chequeResult.getInt(8));
					//System.out.println("8:"+chequeResult.getInt(8));
					chequeDetails.setStatus(chequeResult.getString(9));
					//System.out.println("9:"+chequeResult.getString(9));
					chequeArray.add(chequeDetails);
				}
				chequeStmt.close();
				chequeStmt = null;
			}
		}
		catch(Exception exception)
		{
			//exception.printStackTrace();
			throw new DatabaseException(exception.getMessage());
		}
		finally
		{
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO,"IFDAO","chequeDetailsModify","Exited");
		return chequeArray;
	}

	public String getCeiling(String agencyName, String investee,String instrument)
									  throws DatabaseException 
	{
		Log.log(Log.INFO,"IFDAO","showRatingAgencyWithRatings","Entered");
		String ceiling = null;
		ResultSet danRaisedResult;
		Connection connection = DBConnection.getConnection();
		CallableStatement danRaisedStmt = null;

		try
		{
			danRaisedStmt = connection.prepareCall("{?=call funcGetRatingforInvAg(?,?,?,?,?)}");				
			danRaisedStmt.registerOutParameter(1,Types.INTEGER);
			danRaisedStmt.setString(2,investee);
			danRaisedStmt.setString(3,agencyName);
			danRaisedStmt.setString(4,instrument);
			danRaisedStmt.registerOutParameter(5,java.sql.Types.VARCHAR);
			danRaisedStmt.registerOutParameter(6,java.sql.Types.VARCHAR);
	
			danRaisedStmt.executeQuery();

			int status = danRaisedStmt.getInt(1);  
			//System.out.println("status:"+status);
			String errorCode=danRaisedStmt.getString(6);
			//System.out.println("errorCode:"+errorCode);

			if(status == Constants.FUNCTION_FAILURE)
			{
				Log.log(Log.ERROR,"IFDAO","showRatingAgencyWithRatings","SP returns a 1." +
										" Error code is :" + errorCode);
				danRaisedStmt.close();
				danRaisedStmt = null;
				throw new DatabaseException(errorCode); 
			}
			else if(status == Constants.FUNCTION_NO_DATA)
			{
				ceiling="";
			}
			else if(status == Constants.FUNCTION_SUCCESS) 
			{
				//The value returned by the Stored Procedure is stored as a ResultSet
				ceiling = danRaisedStmt.getString(5);                       
			}
				danRaisedStmt.close();
				danRaisedStmt = null;
			
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
		Log.log(Log.INFO,"IFDAO","showRatingAgencyWithRatings","Exited");
		return ceiling;
	}	
	

	public void updateAllowableRatingsForAgency (ArrayList ratingDetailsArray)
					  throws DatabaseException
	{
		Log.log(Log.INFO,"IFDAO","updateAllowableRatingsForAgency","Entered");
		ResultSet danRaisedResult;
		Connection connection = DBConnection.getConnection();
		CallableStatement danRaisedStmt = null;
		int ratingDetailsArraySize = ratingDetailsArray.size();
		
		try
		{
			danRaisedStmt = connection.prepareCall("{? = call packAllowableRat.funcUpdAllowRat(?,?,?,?)}");				
			danRaisedStmt.registerOutParameter(1,Types.INTEGER);
			danRaisedStmt.registerOutParameter(5,java.sql.Types.VARCHAR);
			
			
			for(int i=0; i<ratingDetailsArraySize; i++) 
			{
				RatingDetails ratingDetails =(RatingDetails) ratingDetailsArray.get(i);
				
				danRaisedStmt.setString(2,ratingDetails.getRating());
				danRaisedStmt.setString(3,ratingDetails.getRatingAgency());
				danRaisedStmt.setString(4,ratingDetails.getStatus());
				
				danRaisedStmt.executeQuery();

				int status = danRaisedStmt.getInt(1);  
				//System.out.println("status:"+status); 
				String errorCode=danRaisedStmt.getString(5);
				//System.out.println("errorCode:"+errorCode); 

				if(status == Constants.FUNCTION_FAILURE)
				{
					Log.log(Log.ERROR,"IFDAO","updateAllowableRatingsForAgency","SP returns a 1." +
											" Error code is :" + errorCode);
					danRaisedStmt.close();
					danRaisedStmt = null;
					throw new DatabaseException(errorCode); 
				}
				
			}
			danRaisedStmt.close();
			danRaisedStmt = null;

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
		Log.log(Log.INFO,"IFDAO","updateAllowableRatingsForAgency","Exited");
	}		

	public ArrayList showRatingAgencyWithRatings()  throws DatabaseException 
	{
		Log.log(Log.INFO,"IFDAO","showRatingAgencyWithRatings","Entered");
		ArrayList danRaisedArray = new ArrayList();
		ResultSet danRaisedResult;
		Connection connection = DBConnection.getConnection();
		CallableStatement danRaisedStmt = null;
		//System.out.println("in dao");

		try
		{
			danRaisedStmt = connection.prepareCall("{? = call packAllowableRat.funcGetUnqAgency(?,?)}");				
			danRaisedStmt.registerOutParameter(1,Types.INTEGER);
			danRaisedStmt.registerOutParameter(2,Constants.CURSOR);
			danRaisedStmt.registerOutParameter(3,java.sql.Types.VARCHAR);
	
			danRaisedStmt.executeQuery();

			int status = danRaisedStmt.getInt(1);  
			String errorCode=danRaisedStmt.getString(3);

			if(status == Constants.FUNCTION_FAILURE)
			{
				Log.log(Log.ERROR,"IFDAO","showRatingAgencyWithRatings","SP returns a 1." +
										" Error code is :" + errorCode);
				danRaisedStmt.close();
				danRaisedStmt = null;
				throw new DatabaseException(errorCode); 
			}
			else if(status == Constants.FUNCTION_SUCCESS) 
			{
				//The value returned by the Stored Procedure is stored as a ResultSet
				danRaisedResult = (ResultSet)danRaisedStmt.getObject(2);
				
				while(danRaisedResult.next())  
				{
					danRaisedArray.add(danRaisedResult.getString(2));  

				}
				danRaisedResult.close();
				danRaisedResult = null;
				danRaisedStmt.close();
				danRaisedStmt = null;
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
		Log.log(Log.INFO,"IFDAO","showRatingAgencyWithRatings","Exited");
		return danRaisedArray;
	}

	public void insertAllowableRatingsForAgency (ArrayList ratingDetailsArray)
					  throws DatabaseException
	{
		Log.log(Log.INFO,"IFDAO","insertAllowableRatingsForAgency","Entered");
		ResultSet danRaisedResult;
		Connection connection = DBConnection.getConnection();
		CallableStatement danRaisedStmt = null;
		int ratingDetailsArraySize = ratingDetailsArray.size();
		
		try
		{
			danRaisedStmt = connection.prepareCall("{? = call packAllowableRat.funcInsAllowRat(?,?,?,?)}");				
			danRaisedStmt.registerOutParameter(1,Types.INTEGER);
			danRaisedStmt.registerOutParameter(5,java.sql.Types.VARCHAR);
			
			
			for(int i=0; i<ratingDetailsArraySize; i++) 
			{
				RatingDetails ratingDetails =(RatingDetails) ratingDetailsArray.get(i);
				danRaisedStmt.setString(2,ratingDetails.getRating());
				//System.out.println(ratingDetails.getRating());
				danRaisedStmt.setString(3,ratingDetails.getRatingAgency());
				//System.out.println(ratingDetails.getRatingAgency());
				danRaisedStmt.setString(4,ratingDetails.getStatus());
				//System.out.println(ratingDetails.getStatus());
				
				danRaisedStmt.executeQuery();

				int status = danRaisedStmt.getInt(1);  
				//System.out.println("status:"+status); 
				String errorCode=danRaisedStmt.getString(5);
				//System.out.println("errorCode:"+errorCode); 

				if(status == Constants.FUNCTION_FAILURE)
				{
					Log.log(Log.ERROR,"IFDAO","insertAllowableRatingsForAgency","SP returns a 1." +
											" Error code is :" + errorCode);
					danRaisedStmt.close();
					danRaisedStmt = null;
					throw new DatabaseException(errorCode); 
				}
				
			}
			danRaisedStmt.close();
			danRaisedStmt = null;

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
		Log.log(Log.INFO,"IFDAO","insertAllowableRatingsForAgency","Exited");
	} 
	
	public ArrayList getRatingsForAgency(String agencyName)  throws DatabaseException
	{
		Log.log(Log.INFO,"IFDAO","getRatingsForAgency","Entered");
		ArrayList danRaisedArray = new ArrayList();
		ResultSet danRaisedResult;
		Connection connection = DBConnection.getConnection();
		CallableStatement danRaisedStmt = null;
		//System.out.println("in dao");

		try
		{
			danRaisedStmt = connection.prepareCall("{? = call packAllowableRat.funcGetAllowRat(?,?,?)}");				
			danRaisedStmt.registerOutParameter(1,Types.INTEGER);
			danRaisedStmt.setString(2,agencyName);
			danRaisedStmt.registerOutParameter(3,Constants.CURSOR);
			danRaisedStmt.registerOutParameter(4,java.sql.Types.VARCHAR);
	
			danRaisedStmt.executeQuery();

			int status = danRaisedStmt.getInt(1);  
			String errorCode=danRaisedStmt.getString(4);

			if(status == Constants.FUNCTION_FAILURE)
			{
				Log.log(Log.ERROR,"IFDAO","getRatingsForAgency","SP returns a 1." +
										" Error code is :" + errorCode);
				danRaisedStmt.close();
				danRaisedStmt = null;
				throw new DatabaseException(errorCode); 
			}
			else if(status == Constants.FUNCTION_SUCCESS) 
			{
				//The value returned by the Stored Procedure is stored as a ResultSet
				danRaisedResult = (ResultSet)danRaisedStmt.getObject(3);
				
				while(danRaisedResult.next())  
				{
					danRaisedArray.add(danRaisedResult.getString(1));  

				}
				danRaisedResult.close();
				danRaisedResult = null;
				danRaisedStmt.close();
				danRaisedStmt = null;
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
		Log.log(Log.INFO,"IFDAO","getRatingsForAgency","Exited");
		return danRaisedArray;
	}

	public void updateRatingAgency(RatingDetails ratingDetails)  throws DatabaseException
	{
		Log.log(Log.INFO,"IFDAO","updateRatingAgency","Entered");
		ResultSet danRaisedResult;
		Connection connection = DBConnection.getConnection();
		CallableStatement danRaisedStmt = null;
		//System.out.println("in dao");

		try
		{
			danRaisedStmt = connection.prepareCall("{? = call packRatingAgency.funcUpdRatingAgency(?,?,?,?,?)}");				
			danRaisedStmt.registerOutParameter(1,Types.INTEGER);

			danRaisedStmt.setString(2,ratingDetails.getAgency());
			danRaisedStmt.setString(3,ratingDetails.getModAgencyName());
			danRaisedStmt.setString(4,ratingDetails.getModAgencyDesc());
			danRaisedStmt.setString(5,ratingDetails.getUser());
			danRaisedStmt.registerOutParameter(6,java.sql.Types.VARCHAR);
	
			danRaisedStmt.executeQuery();

			int status = danRaisedStmt.getInt(1);  
			//System.out.println("status:"+status); 
			String errorCode=danRaisedStmt.getString(6);
			//System.out.println("errorCode:"+errorCode); 

			if(status == Constants.FUNCTION_FAILURE)
			{
				Log.log(Log.ERROR,"IFDAO","updateRatingAgency","SP returns a 1." +
										" Error code is :" + errorCode);
				danRaisedStmt.close();
				danRaisedStmt = null;
				throw new DatabaseException(errorCode); 
			}

				
			danRaisedStmt.close();
			danRaisedStmt = null;

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
		Log.log(Log.INFO,"IFDAO","updateRatingAgency","Exited");
	}
	
	public void insertRatingAgency(RatingDetails ratingDetails)  throws DatabaseException
	{
		Log.log(Log.INFO,"IFDAO","insertRatingAgency","Entered");
		ResultSet danRaisedResult;
		Connection connection = DBConnection.getConnection();
		CallableStatement danRaisedStmt = null;
		
		try
		{
			danRaisedStmt = connection.prepareCall("{? = call packRatingAgency.funcInsRatingAgency(?,?,?,?)}");				
			danRaisedStmt.registerOutParameter(1,Types.INTEGER);
			danRaisedStmt.setString(2,ratingDetails.getNewAgency());
			danRaisedStmt.setString(3,ratingDetails.getModAgencyDesc());
			danRaisedStmt.setString(4,ratingDetails.getUser());
			danRaisedStmt.registerOutParameter(5,java.sql.Types.VARCHAR);
	
			danRaisedStmt.executeQuery();

			int status = danRaisedStmt.getInt(1);  
			//System.out.println("status:"+status); 
			String errorCode=danRaisedStmt.getString(5);
			//System.out.println("errorCode:"+errorCode); 

			if(status == Constants.FUNCTION_FAILURE)
			{
				Log.log(Log.ERROR,"IFDAO","insertRatingAgency","SP returns a 1." +
										" Error code is :" + errorCode);
				danRaisedStmt.close();
				danRaisedStmt = null;
				throw new DatabaseException(errorCode); 
			}

				
			danRaisedStmt.close();
			danRaisedStmt = null;

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
		Log.log(Log.INFO,"IFDAO","insertRatingAgency","Exited");
	}		
	
	
  
  /**
   * 
   * @param depositDate
   * @param bankName
   * @param depositAmt
   * @param compoundingFrequency
   * @param years
   * @param months
   * @param days
   * @param rateOfInterest
   * @param maturityDate
   * @param maturityAmount
   * @param loggedUserId
   * @throws com.cgtsi.common.DatabaseException
   */
	
//Diksha 31/05/2017
/*  public void insertInvestmentDetails(String depositDate,String bankName,double depositAmt,
                            String compoundingFrequency,int years,int months,int days,
                            double rateOfInterest,Date maturityDate,String maturityAmount,
                            String loggedUserId) 
                        throws DatabaseException
	{
		Log.log(Log.INFO,"IFDAO","insertInvestmentDetails","Entered");
		ResultSet danRaisedResult;
		Connection connection = DBConnection.getConnection();
		CallableStatement investmentDetailsStmt = null;
		
		try
		{
			investmentDetailsStmt = connection.prepareCall("{? = call funcInsertInvestDetails(?,?,?,?,?,?,?,?,?,?,?,?)}");				
			investmentDetailsStmt.registerOutParameter(1,Types.INTEGER);
			investmentDetailsStmt.setDate(2,java.sql.Date.valueOf(DateHelper.stringToSQLdate(depositDate)));
			investmentDetailsStmt.setString(3,bankName);
			investmentDetailsStmt.setDouble(4,depositAmt);
      investmentDetailsStmt.setString(5,compoundingFrequency);
      investmentDetailsStmt.setInt(6,years);
      investmentDetailsStmt.setInt(7,months);
      investmentDetailsStmt.setInt(8,days);
      investmentDetailsStmt.setDouble(9,rateOfInterest);
      investmentDetailsStmt.setDate(10,new java.sql.Date(maturityDate.getTime()));
      investmentDetailsStmt.setDouble(11,Double.parseDouble(maturityAmount));
      investmentDetailsStmt.setString(12,loggedUserId);      
			investmentDetailsStmt.registerOutParameter(13,java.sql.Types.VARCHAR);
	
			investmentDetailsStmt.executeQuery();

			int status = investmentDetailsStmt.getInt(1);  
			System.out.println("status:"+status); 
			String errorCode=investmentDetailsStmt.getString(13);
			// System.out.println("errorCode:"+errorCode); 

			if(status == Constants.FUNCTION_FAILURE)
			{
				Log.log(Log.ERROR,"IFDAO","insertInvestmentDetails","SP returns a 1." +
										" Error code is :" + errorCode);
        System.out.println("errorCode:"+errorCode); 
				investmentDetailsStmt.close();
				investmentDetailsStmt = null;
				throw new DatabaseException(errorCode); 
			}

				
			investmentDetailsStmt.close();
			investmentDetailsStmt = null;

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
		Log.log(Log.INFO,"IFDAO","insertInvestmentDetails","Exited");
	}		
	
*/ //31/05/2017 end	
  /**
   * 
   * 
   * @param investmentId
   * @param depositDate
   * @param bankName
   * @param depositAmt
   * @param compoundingFrequency
   * @param years
   * @param months
   * @param days
   * @param rateOfInterest
   * @param maturityDt
   * @param maturityAmount
   * @param loggedUserId
   * @throws com.cgtsi.common.DatabaseException
   */
//Diksha 31/05/2017
/*  public void afterUpdateInvestmentDetails(String investmentId,String depositDate,String bankName,
                       double depositAmt,String compoundingFrequency,int years,int months,int days,
                       double rateOfInterest,String maturityDt,double maturityAmount,
                       String loggedUserId)
                        throws DatabaseException
	{
		Log.log(Log.INFO,"IFDAO","afterUpdateInvestmentDetails","Entered");
		ResultSet danRaisedResult;
		Connection connection = DBConnection.getConnection();
		CallableStatement updateInvestmentDtlStmt = null;
		
		try
		{
			updateInvestmentDtlStmt = connection.prepareCall("{? = call funcupdateinvestdetails(?,?,?,?,?,?,?,?,?,?,?,?,?)}");				
			updateInvestmentDtlStmt.registerOutParameter(1,Types.INTEGER);
      updateInvestmentDtlStmt.setString(2,investmentId);
			updateInvestmentDtlStmt.setDate(3,java.sql.Date.valueOf(DateHelper.stringToSQLdate(depositDate)));
			updateInvestmentDtlStmt.setString(4,bankName);
			updateInvestmentDtlStmt.setDouble(5,depositAmt);
      updateInvestmentDtlStmt.setString(6,compoundingFrequency);
      updateInvestmentDtlStmt.setInt(7,years);
      updateInvestmentDtlStmt.setInt(8,months);
      updateInvestmentDtlStmt.setInt(9,days);
      updateInvestmentDtlStmt.setDouble(10,rateOfInterest);
      updateInvestmentDtlStmt.setDate(11,java.sql.Date.valueOf(DateHelper.stringToSQLdate(maturityDt)));
      updateInvestmentDtlStmt.setDouble(12,maturityAmount);
      updateInvestmentDtlStmt.setString(13,loggedUserId);      
			updateInvestmentDtlStmt.registerOutParameter(14,java.sql.Types.VARCHAR);
	
			updateInvestmentDtlStmt.executeQuery();

			int status = updateInvestmentDtlStmt.getInt(1);  
			// System.out.println("status:"+status); 
			String errorCode=updateInvestmentDtlStmt.getString(14);
			// System.out.println("errorCode:"+errorCode); 

			if(status == Constants.FUNCTION_FAILURE)
			{
				Log.log(Log.ERROR,"IFDAO","afterUpdateInvestmentDetails","SP returns a 1." +
										" Error code is :" + errorCode);
        System.out.println("errorCode:"+errorCode); 
				updateInvestmentDtlStmt.close();
				updateInvestmentDtlStmt = null;
				throw new DatabaseException(errorCode); 
			}

				
			updateInvestmentDtlStmt.close();
			updateInvestmentDtlStmt = null;

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
		Log.log(Log.INFO,"IFDAO","afterUpdateInvestmentDetails","Exited");
	}		
*/ //31/05/2017 end 
 
 
  
  
  
  
  
	public ArrayList showRatingAgency()  throws DatabaseException
	{
		Log.log(Log.INFO,"IFDAO","showRatingAgency","Entered");
		ArrayList danRaisedArray = new ArrayList();
		ResultSet danRaisedResult;
		Connection connection = DBConnection.getConnection();
		CallableStatement danRaisedStmt = null;
		//System.out.println("in dao");

		try
		{
			danRaisedStmt = connection.prepareCall("{? = call packRatingAgency.funcGetAllRating(?,?)}");				
			danRaisedStmt.registerOutParameter(1,Types.INTEGER);
			danRaisedStmt.registerOutParameter(2,Constants.CURSOR);
			danRaisedStmt.registerOutParameter(3,java.sql.Types.VARCHAR);
	
			danRaisedStmt.executeQuery();

			int status = danRaisedStmt.getInt(1);  
			String errorCode=danRaisedStmt.getString(3);

			if(status == Constants.FUNCTION_FAILURE)
			{
				Log.log(Log.ERROR,"IFDAO","showRatingAgency","SP returns a 1." +
										" Error code is :" + errorCode);
				danRaisedStmt.close();
				danRaisedStmt = null;
				throw new DatabaseException(errorCode); 
			}
			else if(status == Constants.FUNCTION_SUCCESS) 
			{
				//The value returned by the Stored Procedure is stored as a ResultSet
				danRaisedResult = (ResultSet)danRaisedStmt.getObject(2);
				
				while(danRaisedResult.next())  
				{
					danRaisedArray.add(danRaisedResult.getString(2));  

				}
				danRaisedResult.close();
				danRaisedResult = null;
				danRaisedStmt.close();
				danRaisedStmt = null;
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
		Log.log(Log.INFO,"IFDAO","showRatingAgency","Exited");
		return danRaisedArray;
	}	
	
	
	public RatingDetails showRatingAgencyDetails(String agencyName)  throws DatabaseException
	{
		Log.log(Log.INFO,"IFDAO","showRatingAgencyDetails","Entered");
		RatingDetails ratingDetails = null;
		ResultSet danRaisedResult;
		Connection connection = DBConnection.getConnection();
		CallableStatement danRaisedStmt = null;
		//System.out.println("in dao");

		try
		{
			danRaisedStmt = connection.prepareCall("{? = call packRatingAgency.funcGetRatingDtl(?,?,?,?)}");				
			danRaisedStmt.registerOutParameter(1,Types.INTEGER);
			danRaisedStmt.registerOutParameter(3,java.sql.Types.VARCHAR);
			danRaisedStmt.registerOutParameter(4,java.sql.Types.VARCHAR);
			danRaisedStmt.registerOutParameter(5,java.sql.Types.VARCHAR);
			danRaisedStmt.setString(2,agencyName);
	
			danRaisedStmt.executeQuery();

			int status = danRaisedStmt.getInt(1);  
			//System.out.println("status:"+status); 
			String errorCode=danRaisedStmt.getString(5);
			//System.out.println("errorCode:"+errorCode); 

			if(status == Constants.FUNCTION_FAILURE)
			{
				Log.log(Log.ERROR,"IFDAO","showRatingAgencyDetails","SP returns a 1." +
										" Error code is :" + errorCode);
				danRaisedStmt.close();
				danRaisedStmt = null;
				throw new DatabaseException(errorCode); 
			}
			else if(status == Constants.FUNCTION_SUCCESS) 
			{
				//The value returned by the Stored Procedure is stored as a ResultSet
					ratingDetails = new RatingDetails();
					ratingDetails.setRatingAgency(danRaisedStmt.getString(3));
					ratingDetails.setRatingDescription(danRaisedStmt.getString(4));
			}
				
			danRaisedStmt.close();
			danRaisedStmt = null;

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
		Log.log(Log.INFO,"IFDAO","showRatingAgency","Exited");
		return ratingDetails;
	}	
	

	public void bankStatementUploadResult(StatementDetail statementDetail) 
										 throws DatabaseException,MessageException
	{
		Log.log(Log.INFO,"IFDAO","bankStatementUploadResult","Entered");
		Connection connection = DBConnection.getConnection(false);
		String bank = statementDetail.getBankName();
		String newBank = bank.trim();
		//System.out.println("newBank:"+newBank);
		String acNumber = statementDetail.getAccountNumber();
		String newAcNumber = acNumber.trim();		
		//System.out.println("newAcNumber:"+newAcNumber);
		
		try {
			Log.log(Log.DEBUG,"IFDAO","bankStatementUploadResult","Bank Name " + statementDetail.getBankName());
			Log.log(Log.DEBUG,"IFDAO","bankStatementUploadResult","getAccountNumber " + statementDetail.getAccountNumber());
			Log.log(Log.DEBUG,"IFDAO","bankStatementUploadResult","getOpeningBalance " + statementDetail.getOpeningBalance());
			Log.log(Log.DEBUG,"IFDAO","bankStatementUploadResult","getClosingBalance " + statementDetail.getClosingBalance());
			Log.log(Log.DEBUG,"IFDAO","bankStatementUploadResult","getCreditPendingForTheDay "
					+ statementDetail.getCreditPendingForTheDay());
			Log.log(Log.DEBUG,"IFDAO","bankStatementUploadResult",	"getTransactionFromTo "
					+ statementDetail.getTransactionFromTo());
			Log.log(Log.DEBUG,"IFDAO","bankStatementUploadResult","getTransactionNature "
					+ statementDetail.getTransactionNature());
			Log.log(Log.DEBUG,"IFDAO","bankStatementUploadResult","getTransactionDate " 
					+ statementDetail.getTransactionDate());
			Log.log(Log.DEBUG,"IFDAO","bankStatementUploadResult","getTransactionAmount "
					+ statementDetail.getTransactionAmount());
			Log.log(Log.DEBUG,"IFDAO","bankStatementUploadResult","getRemarks " + statementDetail.getRemarks());
			Log.log(Log.DEBUG,"IFDAO","bankStatementUploadResult","Userid "
					 + statementDetail.getUserId());
			Log.log(Log.DEBUG,"IFDAO","bankStatementUploadResult","getStatementDate "
					 + statementDetail.getStatementDate());

			CallableStatement callable = connection.prepareCall(
										"{?= call funcUploadBST(?,?,?,?,?,?,?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);
			callable.setString(2, statementDetail.getBankName());
			callable.setString(3, statementDetail.getAccountNumber());
			java.util.Date utilDate = statementDetail.getStatementDate();
			java.sql.Date  sqlDate = new java.sql.Date (utilDate.getTime());
			callable.setDate(4, sqlDate);
			callable.setDouble(5, statementDetail.getOpeningBalance());
			callable.setDouble(6, statementDetail.getClosingBalance());
			callable.setString(7,statementDetail.getUserId());
			callable.registerOutParameter(8, Types.INTEGER);
			callable.registerOutParameter(9, Types.VARCHAR);

			callable.execute();

			int errorCode = callable.getInt(1);
			//System.out.println("errorCode:"+errorCode);
			String error = callable.getString(9);
			//System.out.println("error:"+error);
			
			Log.log(Log.DEBUG,"IFDAO","bankStatementUploadResult","errorCode and error "
					 + errorCode + "," + error);

			if (errorCode == Constants.FUNCTION_FAILURE)
			{
				Log.log(Log.ERROR, "IFDAO", "bankStatementUploadResult", error);
				callable.close();
				callable = null;
				connection.rollback();
				throw new DatabaseException(error);
			}

			int statementId=callable.getInt(8);
			//System.out.println("statementId:"+statementId);
			
			Log.log(Log.DEBUG, "IFDAO", "bankStatementUploadResult", "Statement id is "+statementId);


			callable=connection.prepareCall("{?= call funcUploadBTD(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			callable.registerOutParameter(1,Types.INTEGER);
			callable.registerOutParameter(14,Types.VARCHAR);

			SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");

			for (int i=0;i<statementDetail.getTransactionDetail().size();i++)
			{
				
				TransactionDetail transactionDetail=
					(TransactionDetail)statementDetail.getTransactionDetail().get(i);

				Log.log(Log.DEBUG, "IFDAO", "bankStatementUploadResult", "From/To,Nature,Date,Amount "+
				transactionDetail.getTransactionFromTo()+","+ 
				transactionDetail.getTransactionNature()+","+
				transactionDetail.getTransactionDate()+","+
				transactionDetail.getTransactionAmount());

				callable.setInt(2,statementId);
				//System.out.println("statementId:"+statementId);
				
				callable.setDouble(3,transactionDetail.getTransactionAmount());

				if(transactionDetail.getChequeNumber() != null)
				{
					callable.setString(4,transactionDetail.getChequeNumber());
					//System.out.println("ChequeNumber:"+transactionDetail.getChequeNumber());
				}
				else
				{
					callable.setNull(4,java.sql.Types.VARCHAR);
					//System.out.println("ChequeNumber: Null");
				}

				callable.setString(5,transactionDetail.getTransactionFromTo());
				//System.out.println("TransactionFromTo:"+transactionDetail.getTransactionFromTo());
				if(transactionDetail.getTransactionNature()!=null && transactionDetail.getTransactionNature().equals("CR"))
				{
					if(transactionDetail.getChequeNumber()==null || transactionDetail.getChequeNumber().equals(""))
					{
						throw new MessageException("Cheque number is required for Credit Transaction");
					}
				}
				callable.setString(6,transactionDetail.getTransactionNature());
				
				//System.out.println("TransactionNature:"+transactionDetail.getTransactionNature());
				java.util.Date trdDate=dateFormat.parse(transactionDetail.getTransactionDate(),new ParsePosition(0));
				callable.setDate(7,new java.sql.Date(trdDate.getTime()));
				//System.out.println("TransactionDate:"+new java.sql.Date(trdDate.getTime()));
				if(transactionDetail.getValueDate()!=null && !transactionDetail.getValueDate().toString().equals(""))
				{
					java.util.Date valDate=dateFormat.parse(transactionDetail.getValueDate(),new ParsePosition(0));
					//System.out.println("valDate:"+valDate);				
					if((valDate != null) && (!valDate.toString().equals(""))) 
					{
						callable.setDate(8,new java.sql.Date(valDate.getTime()));
						//System.out.println("valDate:"+new java.sql.Date(valDate.getTime()));
					}
				}
				else
				{
					callable.setNull(8,java.sql.Types.DATE);
					//System.out.println("valDate: Null");
				}				
				callable.setDouble(9,transactionDetail.getWithdrawals());
				//System.out.println("Withdrawals:"+transactionDetail.getWithdrawals());
				callable.setDouble(10,transactionDetail.getDeposits());
				//System.out.println("Deposits:"+transactionDetail.getDeposits());
				callable.setString(11,transactionDetail.getTransactionId());
				//System.out.println("TransactionId:"+transactionDetail.getTransactionId());
				callable.setString(12,newBank);
				//System.out.println("newBank:"+newBank);
				callable.setString(13,newAcNumber);
				//System.out.println("newAcNumber:"+newAcNumber);				
				callable.execute();

				errorCode=callable.getInt(1);
				//System.out.println("errorCode:"+errorCode);
				error=callable.getString(14);
				//System.out.println("error:"+error);
				
				Log.log(Log.DEBUG,"IFDAO","bankStatementUploadResult",
					"errorCode and error " + errorCode + "," + error);

				if(errorCode==Constants.FUNCTION_FAILURE) 
				{
					callable.close();
					callable = null;

					connection.rollback();
					Log.log(Log.ERROR, "IFDAO", "bankStatementUploadResult", error);
					throw new DatabaseException(error);
				}   

				java.sql.Date startDate = null;
				java.util.Date todaysDate = new java.util.Date();
				java.sql.Date todaysSQLDate = new java.sql.Date(todaysDate.getTime());
				boolean chequeIssuedDetailToBeUpdated = false;
				ArrayList transactions = statementDetail.getTransactionDetail();

				ArrayList chequeDetails = chequeDetailsUpdate(startDate,todaysSQLDate);
				Log.log(Log.DEBUG,"IFAction","bankStatementUploadResult","For Loop: transactions.size() :" + transactions.size());
				Log.log(Log.DEBUG,"IFAction","bankStatementUploadResult","For Loop: chequeDetails.size() " + chequeDetails.size());

				for(int j=0; j<chequeDetails.size(); j++)
				{
					ChequeDetails cd = (ChequeDetails)chequeDetails.get(j);
					if(cd == null)
					{
						continue;
					}
					String chequeNumber = cd.getChequeNumber();
					if(transactionDetail.getChequeNumber() == null)
					{
						continue;
					}
					if(((transactionDetail.getChequeNumber()) != null) && (transactionDetail.getChequeNumber().equals("")))
					{
						continue;
					}
					if(chequeNumber.equals(transactionDetail.getChequeNumber()))
					{
						// Updating cheque_issued_detail table
						ChequeDetails dtls = chequeDetailsUpdatePage(chequeNumber);
						String chequeStatus = dtls.getStatus();
						if(chequeStatus.equals("P"))
						{
							throw new MessageException("Cheque has already been presented");
						}
						String chequeId = dtls.getChequeId();
						Log.log(Log.DEBUG,"IFAction","bankStatementUploadResult","For Loop: chequeNumber " + chequeNumber);
						java.util.Date transDate=dateFormat.parse(transactionDetail.getTransactionDate(),new ParsePosition(0));
						//System.out.println("transDate:"+transDate);
						java.sql.Date sqlTransDate = new java.sql.Date(transDate.getTime())	;
						//System.out.println("sqlTransDate:"+sqlTransDate);					
						Log.log(Log.DEBUG,"IFAction","updateStatementDetails","For Loop: chequeNumber " + chequeNumber);
						updateChequeStatus(statementDetail.getBankName(),
								statementDetail.getAccountNumber(),chequeNumber,sqlTransDate,connection);
					}
				}
			}

			callable.close();
			callable = null;
			connection.commit();


		} catch (SQLException e) {
			Log.log(Log.ERROR, "IFDAO", "bankStatementUploadResult", e.getMessage());
			Log.logException(e);
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new DatabaseException("Unable to add statement details");
		}
		catch (MessageException e) {
				Log.log(Log.ERROR, "IFDAO", "bankStatementUploadResult", e.getMessage());
				Log.logException(e);
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				throw e;
			}
		catch (DatabaseException e) {
				Log.log(Log.ERROR, "IFDAO", "bankStatementUploadResult", e.getMessage());
				Log.logException(e);
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				throw e;
			}

		finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(Log.INFO,"IFDAO","bankStatementUploadResult","Exited");
	}




	public ArrayList accruedInterestIncomeReport(java.sql.Date endDate)
										 throws DatabaseException
	{
		Log.log(Log.INFO,"ReportDAO","accruedInterestIncomeReport","Entered");
		ArrayList danRaisedArray = new ArrayList();
		ResultSet danRaisedResult;
		Connection connection = DBConnection.getConnection();
		CallableStatement danRaisedStmt = null;
		//System.out.println("in dao");

		try
		{
			danRaisedStmt = connection.prepareCall("{? = call packAccuredRep.funcGetAccuredRep(?,?,?)}");				
			danRaisedStmt.registerOutParameter(1,Types.INTEGER);
			danRaisedStmt.setDate(2,endDate);
			//System.out.println("endDate:"+endDate);
			danRaisedStmt.registerOutParameter(3,Constants.CURSOR);
			danRaisedStmt.registerOutParameter(4,java.sql.Types.VARCHAR);
	
			danRaisedStmt.executeQuery();

			int status = danRaisedStmt.getInt(1);  
			//System.out.println("status:"+status);
			String errorCode=danRaisedStmt.getString(4);
			//System.out.println("errorCode:"+errorCode);
/*
			if(status == Constants.FUNCTION_FAILURE)
			{
				Log.log(Log.ERROR,"ReportDAO","accruedInterestIncomeReport","SP returns a 1." +
										" Error code is :" + errorCode);
				danRaisedStmt.close();
				danRaisedStmt = null;
			}
			else if(status == Constants.FUNCTION_SUCCESS) 
			{
*/				//The value returned by the Stored Procedure is stored as a ResultSet
				danRaisedResult = (ResultSet)danRaisedStmt.getObject(3);
				
				while(danRaisedResult.next())  
				{
					AccruedInterestIncome accruedInterestIncome = new AccruedInterestIncome();
					accruedInterestIncome.setInvestee(danRaisedResult.getString(1)); 
					accruedInterestIncome.setDepositDate(danRaisedResult.getDate(2));
					accruedInterestIncome.setDepositAmount(danRaisedResult.getDouble(3));
					accruedInterestIncome.setMaturityDate(danRaisedResult.getDate(4));
					accruedInterestIncome.setFromDate(danRaisedResult.getDate(5));
					accruedInterestIncome.setToDate(danRaisedResult.getDate(6));
					accruedInterestIncome.setInterest(danRaisedResult.getDouble(7));
					accruedInterestIncome.setDays(Integer.parseInt(danRaisedResult.getString(8)));
					accruedInterestIncome.setAnnualInterest(danRaisedResult.getDouble(9));
					accruedInterestIncome.setInterestEarned(danRaisedResult.getDouble(10));
					accruedInterestIncome.setAmountFirst(danRaisedResult.getDouble(11));
					accruedInterestIncome.setAmountSecond(danRaisedResult.getDouble(12));
					accruedInterestIncome.setRoi(danRaisedResult.getDouble(13));
					
					danRaisedArray.add(accruedInterestIncome);  

				}
				danRaisedResult.close();
				danRaisedResult = null;
				danRaisedStmt.close();
				danRaisedStmt = null;

			}
//		}

		catch(Exception exception)
		{
			Log.logException(exception);
			throw new DatabaseException(exception.getMessage());
		}
		finally
		{
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO,"ReportDAO","accruedInterestIncomeReport","Exited");
		return danRaisedArray;
	}


	public ArrayList investeeWiseReportDetails(String investee,java.sql.Date startDate,
								 java.sql.Date endDate) throws DatabaseException
	{
		Log.log(Log.INFO,"IFDAO","investeeWiseReportDetails","Entered"); 
		PreparedStatement danRaisedStmt = null;
		ArrayList danRaisedArray = new ArrayList();
		ResultSet danRaisedResult;
		Connection connection = DBConnection.getConnection();
		HashMap fdReport = new HashMap();

		if(startDate != null)
		{
			try
			{
				String query = "select i.BSD_ID,inv.INV_NAME, m.MAT_TYPE, i.IDT_COST_OF_PURCHASE," +
					" i.IDT_MATURITY_AMOUNT from investee inv, maturity_master m," +
					" investment_detail i where i.MAT_ID = m.MAT_ID and " +
					" i.INV_ID = inv.INV_ID and trunc(i.IDT_MATURITY_DT) between ? and ?" +
					" and inv.INV_NAME = ? group by i.BSD_ID,inv.INV_NAME, m.MAT_TYPE," +
					" i.IDT_COST_OF_PURCHASE,i.IDT_MATURITY_AMOUNT" ;
				danRaisedStmt = connection.prepareStatement(query);
				danRaisedStmt.setDate(2,endDate);
				danRaisedStmt.setDate(1,startDate);
				danRaisedStmt.setString(3,investee);
				danRaisedResult = danRaisedStmt.executeQuery();

				while(danRaisedResult.next())
				{
					//Instantiate a FDReport value object
					String investeeName = danRaisedResult.getString(2);
					FDReport gFee = (FDReport)fdReport.get(investeeName);

					if(gFee == null)
					{
						gFee  = new FDReport();
						gFee.setInvestee(investee);
//						System.out.println(investee);
						gFee.setInvestmentNumber(danRaisedResult.getString(1));
						gFee.setMaturityType(danRaisedResult.getString(3));
//						System.out.println(danRaisedResult.getString(2));
						gFee.setPrincipalAmount(danRaisedResult.getDouble(4));
//						System.out.println(danRaisedResult.getDouble(3));
						gFee.setMaturityAmount(danRaisedResult.getDouble(5));
//						System.out.println(danRaisedResult.getDouble(4));
						fdReport.put(investee,gFee);
					}

					else if(gFee != null)
					{
						gFee  = new FDReport();
						gFee.setInvestmentNumber(danRaisedResult.getString(1));
						gFee.setMaturityType(danRaisedResult.getString(3));
//						System.out.println(danRaisedResult.getString(2));
						gFee.setPrincipalAmount(danRaisedResult.getDouble(4));
//						System.out.println(danRaisedResult.getDouble(3));
						gFee.setMaturityAmount(danRaisedResult.getDouble(5));
//						System.out.println(danRaisedResult.getDouble(4));

					}
					danRaisedArray.add(gFee);
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
		}
		else if(startDate == null)
		{
			try
			{
				String query = "select i.BSD_ID,inv.INV_NAME, m.MAT_TYPE, i.IDT_COST_OF_PURCHASE," +
					" i.IDT_MATURITY_AMOUNT from investee inv, maturity_master m," +
					" investment_detail i where i.MAT_ID = m.MAT_ID and " +
					" i.INV_ID = inv.INV_ID and trunc(i.IDT_MATURITY_DT) <= ?" +
					" and inv.INV_NAME = ? group by i.BSD_ID,inv.INV_NAME, m.MAT_TYPE," +
					" i.IDT_COST_OF_PURCHASE,i.IDT_MATURITY_AMOUNT"  ;

				danRaisedStmt = connection.prepareStatement(query);
				danRaisedStmt.setDate(1,endDate);
				danRaisedStmt.setString(2,investee);
				danRaisedResult = danRaisedStmt.executeQuery();

				while(danRaisedResult.next())
				{
					//Instantiate a FDReport value object
					String investeeName = danRaisedResult.getString(2);
					FDReport gFee = (FDReport)fdReport.get(investeeName);

					if(gFee == null)
					{
						gFee  = new FDReport();
						gFee.setInvestee(investee);
//						System.out.println(investee);
						gFee.setInvestmentNumber(danRaisedResult.getString(1));
						gFee.setMaturityType(danRaisedResult.getString(3));
//						System.out.println(danRaisedResult.getString(2));
						gFee.setPrincipalAmount(danRaisedResult.getDouble(4));
//						System.out.println(danRaisedResult.getDouble(3));
						gFee.setMaturityAmount(danRaisedResult.getDouble(5));
//						System.out.println(danRaisedResult.getDouble(4));
						fdReport.put(investee,gFee);
					}

					else if(gFee != null)
					{
						gFee  = new FDReport();
						gFee.setInvestmentNumber(danRaisedResult.getString(1));
						gFee.setMaturityType(danRaisedResult.getString(3));
//						System.out.println(danRaisedResult.getString(2));
						gFee.setPrincipalAmount(danRaisedResult.getDouble(4));
//						System.out.println(danRaisedResult.getDouble(3));
						gFee.setMaturityAmount(danRaisedResult.getDouble(5));
//						System.out.println(danRaisedResult.getDouble(4));

					}
					danRaisedArray.add(gFee);
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
		}
		Log.log(Log.INFO,"IFDAO","investeeWiseReportDetails","Exited");
		return  danRaisedArray;
	}
	
	public ArrayList investeeWiseReportDetailsSummary(String number,java.sql.Date startDate,
								 java.sql.Date endDate) throws DatabaseException
	{
		Log.log(Log.INFO,"IFDAO","investeeWiseReportDetailsSummary","Entered");
		PreparedStatement danRaisedStmt = null;
		ArrayList danRaisedArray = new ArrayList();
		ResultSet danRaisedResult;
		Connection connection = DBConnection.getConnection();

		if(startDate != null)
		{
			try
			{
				String query = "select inv.INV_NAME,ins.INS_TYPE, m.MAT_TYPE,r.RAT_RATING," +
					" i.IDT_COST_OF_PURCHASE, i.IDT_COMPOUNDING_FREQUENCY," +
					" i.IDT_INTEREST_RATE, i.IDT_TENURE_TYPE, i.IDT_PURCHASE_DT," +
					" i.IDT_MATURITY_AMOUNT, i.IDT_MATURITY_DT from investment_detail i," +
					" investee inv, rating_master r, instrument_master ins," +
					" maturity_master m where i.INS_ID = ins.INS_ID and " +
					" i.INV_ID = inv.INV_ID and i.MAT_ID = m.MAT_ID and i.RAT_ID = r.RAT_ID" +
					" and i.BSD_ID = ? and trunc(i.IDT_PURCHASE_DT) between ? and ?";
				danRaisedStmt = connection.prepareStatement(query);
				danRaisedStmt.setString(1,number);
				danRaisedStmt.setDate(3,endDate);
				danRaisedStmt.setDate(2,startDate);
				danRaisedResult = danRaisedStmt.executeQuery();

				while(danRaisedResult.next())
				{
					//Instantiate a FDReport value object
					FDReport gFee = new FDReport();
					gFee.setInvestee(danRaisedResult.getString(1));
					gFee.setInstrumentType(danRaisedResult.getString(2));
					gFee.setMaturityType(danRaisedResult.getString(3));
					gFee.setRating(danRaisedResult.getString(4));
					gFee.setPrincipalAmount(danRaisedResult.getDouble(5));
					gFee.setFrequency(danRaisedResult.getInt(6));
					gFee.setInterest(danRaisedResult.getInt(7));
					gFee.setTenureType(danRaisedResult.getString(8));
					gFee.setDepositDate(danRaisedResult.getDate(9));
					gFee.setMaturityAmount(danRaisedResult.getDouble(10));
					gFee.setMaturityDate(danRaisedResult.getDate(11));

					danRaisedArray.add(gFee);
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
		}
		else if(startDate == null)
		{
			try
			{
				String query = "select inv.INV_NAME,ins.INS_TYPE, m.MAT_TYPE,r.RAT_RATING," +
					" i.IDT_COST_OF_PURCHASE, i.IDT_COMPOUNDING_FREQUENCY," +
					" i.IDT_INTEREST_RATE, i.IDT_TENURE_TYPE, i.IDT_PURCHASE_DT," +
					" i.IDT_MATURITY_AMOUNT, i.IDT_MATURITY_DT from investment_detail i," +
					" investee inv, rating_master r, instrument_master ins," +
					" maturity_master m where i.INS_ID = ins.INS_ID and " +
					" i.INV_ID = inv.INV_ID and i.MAT_ID = m.MAT_ID and i.RAT_ID = r.RAT_ID" +
					" and i.BSD_ID = ? and trunc(i.IDT_PURCHASE_DT) <= ?";
				danRaisedStmt = connection.prepareStatement(query);
				danRaisedStmt.setString(1,number);
				danRaisedStmt.setDate(2,endDate);
				danRaisedResult = danRaisedStmt.executeQuery();

				while(danRaisedResult.next())
				{
					//Instantiate a FDReport value object
					FDReport gFee = new FDReport();
					gFee.setInvestee(danRaisedResult.getString(1));
					gFee.setInstrumentType(danRaisedResult.getString(2));
					gFee.setMaturityType(danRaisedResult.getString(3));
					gFee.setRating(danRaisedResult.getString(4));
					gFee.setPrincipalAmount(danRaisedResult.getDouble(5));
					gFee.setFrequency(danRaisedResult.getInt(6));
					gFee.setInterest(danRaisedResult.getInt(7));
					gFee.setTenureType(danRaisedResult.getString(8));
					gFee.setDepositDate(danRaisedResult.getDate(9));
					gFee.setMaturityAmount(danRaisedResult.getDouble(10));
					gFee.setMaturityDate(danRaisedResult.getDate(11));

					danRaisedArray.add(gFee);
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
		}
		Log.log(Log.INFO,"IFDAO","investeeWiseReportDetailsSummary","Exited");
		return  danRaisedArray;
	}



	public ChequeDetails chequeDetailsForPayment(String chequeNumber)throws DatabaseException
	{
		Log.log(Log.INFO,"IFDAO","chequeDetailsForPayment","Entered");
		ChequeDetails chequeDetails = new ChequeDetails();
		ResultSet chequeResult;
		Connection connection = DBConnection.getConnection();
		CallableStatement chequeStmt = null;

			try
			{
				chequeStmt = connection.prepareCall("{? = call packGetChequeDetailsForPayment.funcGetChequeDetailsForPayment(?,?,?)}");
				chequeStmt.setString(2,chequeNumber);
				chequeStmt.registerOutParameter(1, Types.INTEGER);
				chequeStmt.registerOutParameter(3,Constants.CURSOR);
				chequeStmt.registerOutParameter(4, Types.VARCHAR);

				chequeStmt.executeQuery();

				int status = chequeStmt.getInt(1);

				if(status == Constants.FUNCTION_FAILURE)
				{
					String errorCode = chequeStmt.getString(4);
					Log.log(Log.ERROR,"IFDAO","chequeDetailsUpdatePage","SP returns a 1." +
											" Error code is :" + errorCode);
					chequeStmt.close();
					chequeStmt = null;
				}
				else if(status == Constants.FUNCTION_SUCCESS)
				{
					chequeResult = (ResultSet)chequeStmt.getObject(3);
					while(chequeResult.next())
					{
						chequeDetails  = new ChequeDetails();
						chequeDetails.setChequeNumber(chequeResult.getString(1));
						chequeDetails.setChequeAmount(chequeResult.getDouble(2));
						chequeDetails.setChequeDate(chequeResult.getDate(3));
						chequeDetails.setChequeIssuedTo(chequeResult.getString(4));
						chequeDetails.setBankName(chequeResult.getString(5));
						chequeDetails.setChequeId(chequeResult.getString(6));
						chequeDetails.setChequeRemarks(chequeResult.getString(7));

					}
					chequeStmt.close();
					chequeStmt = null;
				}

			}
			catch(Exception exception)
			{
				exception.printStackTrace();
				throw new DatabaseException(exception.getMessage());
			}
			finally
			{
				DBConnection.freeConnection(connection);
			}
		Log.log(Log.INFO,"IFDAO","chequeDetailsForPayment","Exited");
		return chequeDetails;
	}

	public ArrayList investmentMaturityReport(java.sql.Date startDate,java.sql.Date date1,
	 java.sql.Date date2,java.sql.Date date3,java.sql.Date date4,java.sql.Date date5,
	 java.sql.Date date6,java.sql.Date date7,java.sql.Date date8,java.sql.Date date9,
	 java.sql.Date date10,java.sql.Date date11,java.sql.Date date12,java.sql.Date date13,
	 java.sql.Date date14) 	throws DatabaseException

	{
		Log.log(Log.INFO,"IFDAO","investmentMaturityReport","Entered");
		PreparedStatement danRaisedStmt = null;
		ArrayList investmentArray = new ArrayList();
		ResultSet danRaisedResult;
		Connection connection = DBConnection.getConnection();
		HashMap investmentMap = new HashMap();

		try
		{
			String query = "SELECT im.INS_DESCRIPTION," +
						   " ROUND(SUM(ID.IDT_MATURITY_AMOUNT)/100000,4), 1 " +
						   " FROM INVESTMENT_DETAIL ID,instrument_master im " +
						   " WHERE trunc(ID.IDT_MATURITY_DT) BETWEEN ? and ? AND " +
						   " im.INS_ID = id.INS_ID AND ID.IDT_MATURITY_AMOUNT <> 0 " +
						   " GROUP BY im.INS_DESCRIPTION " +
						   " UNION ALL "+
						   " SELECT im.INS_DESCRIPTION," +
						   " ROUND(SUM(ID.IDT_MATURITY_AMOUNT)/100000,4), 2 " +
						   " FROM INVESTMENT_DETAIL ID,instrument_master im " +
						   " WHERE trunc(ID.IDT_MATURITY_DT) BETWEEN ? and ? AND " +
						   " im.INS_ID = id.INS_ID AND ID.IDT_MATURITY_AMOUNT <> 0 " +
						   " GROUP BY im.INS_DESCRIPTION " +
						   " UNION ALL "+
							" SELECT im.INS_DESCRIPTION," +
							" ROUND(SUM(ID.IDT_MATURITY_AMOUNT)/100000,4), 3 " +
							" FROM INVESTMENT_DETAIL ID,instrument_master im " +
							" WHERE trunc(ID.IDT_MATURITY_DT) BETWEEN ? and ? AND " +
							" im.INS_ID = id.INS_ID AND ID.IDT_MATURITY_AMOUNT <> 0 " +
							" GROUP BY im.INS_DESCRIPTION " +
							" UNION ALL "+
							" SELECT im.INS_DESCRIPTION," +
							" ROUND(SUM(ID.IDT_MATURITY_AMOUNT)/100000,4), 4 " +
							" FROM INVESTMENT_DETAIL ID,instrument_master im " +
							" WHERE trunc(ID.IDT_MATURITY_DT) BETWEEN ? and ? AND " +
							" im.INS_ID = id.INS_ID AND ID.IDT_MATURITY_AMOUNT <> 0 " +
							" GROUP BY im.INS_DESCRIPTION " +
							" UNION ALL "+
							" SELECT im.INS_DESCRIPTION," +
							" ROUND(SUM(ID.IDT_MATURITY_AMOUNT)/100000,4), 5 " +
							" FROM INVESTMENT_DETAIL ID,instrument_master im " +
							" WHERE trunc(ID.IDT_MATURITY_DT) BETWEEN ? and ? AND " +
							" im.INS_ID = id.INS_ID AND ID.IDT_MATURITY_AMOUNT <> 0 " +
							" GROUP BY im.INS_DESCRIPTION " +
							" UNION ALL "+
							" SELECT im.INS_DESCRIPTION," +
							" ROUND(SUM(ID.IDT_MATURITY_AMOUNT)/100000,4), 6 " +
							" FROM INVESTMENT_DETAIL ID,instrument_master im " +
							" WHERE trunc(ID.IDT_MATURITY_DT) BETWEEN ? and ? AND " +
							" im.INS_ID = id.INS_ID AND ID.IDT_MATURITY_AMOUNT <> 0 " +
							" GROUP BY im.INS_DESCRIPTION " +
							" UNION ALL "+
							" SELECT im.INS_DESCRIPTION," +
							" ROUND(SUM(ID.IDT_MATURITY_AMOUNT)/100000,4), 7 " +
							" FROM INVESTMENT_DETAIL ID,instrument_master im " +
							" WHERE trunc(ID.IDT_MATURITY_DT) BETWEEN ? and ? AND " +
							" im.INS_ID = id.INS_ID AND ID.IDT_MATURITY_AMOUNT <> 0 " +
							" GROUP BY im.INS_DESCRIPTION " +
							" UNION ALL "+
							" SELECT im.INS_DESCRIPTION," +
							" ROUND(SUM(ID.IDT_MATURITY_AMOUNT)/100000,4), 8 " +
							" FROM INVESTMENT_DETAIL ID,instrument_master im " +
							" WHERE trunc(ID.IDT_MATURITY_DT) >= ? AND " +
							" im.INS_ID = id.INS_ID AND ID.IDT_MATURITY_AMOUNT <> 0 " +
							" GROUP BY im.INS_DESCRIPTION " ;

			danRaisedStmt = connection.prepareStatement(query);
			danRaisedStmt.setDate(1,startDate);
			danRaisedStmt.setDate(2,date1);
			danRaisedStmt.setDate(3,date2);
			danRaisedStmt.setDate(4,date3);
			danRaisedStmt.setDate(5,date4);
			danRaisedStmt.setDate(6,date5);
			danRaisedStmt.setDate(7,date6);
			danRaisedStmt.setDate(8,date7);
			danRaisedStmt.setDate(9,date8);
			danRaisedStmt.setDate(10,date9);
			danRaisedStmt.setDate(11,date10);
			danRaisedStmt.setDate(12,date11);
			danRaisedStmt.setDate(13,date12);
			danRaisedStmt.setDate(14,date13);
			danRaisedStmt.setDate(15,date14);

			danRaisedResult = danRaisedStmt.executeQuery();

			while(danRaisedResult.next())
			{
				//Instantiate a Gfee value object
				String investmentType = danRaisedResult.getString(1);
				InvestmentDetails investmentDetails =(InvestmentDetails)investmentMap.get(investmentType);
				int index = danRaisedResult.getInt(3);

				if(investmentDetails == null)
				{
					investmentDetails = new InvestmentDetails();

					if(index == 1)
					{
						investmentDetails.setRange1(danRaisedResult.getDouble(2));
					}
					else if(index == 2)
					{
						investmentDetails.setRange2(danRaisedResult.getDouble(2));
					}
					else if(index == 3)
					{
						investmentDetails.setRange3(danRaisedResult.getDouble(2));
					}
					else if(index == 4)
					{
						investmentDetails.setRange4(danRaisedResult.getDouble(2));
					}
					else if(index == 5)
					{
						investmentDetails.setRange5(danRaisedResult.getDouble(2));
					}
					else if(index == 6)
					{
						investmentDetails.setRange6(danRaisedResult.getDouble(2));
					}
					else if(index == 7)
					{
						investmentDetails.setRange7(danRaisedResult.getDouble(2));
					}
					else if(index == 8)
					{
						investmentDetails.setRange8(danRaisedResult.getDouble(2));
					}
					investmentMap.put(investmentType,investmentDetails);
				}

				else
				{
					if(index == 1)
					{
						investmentDetails.setRange1(investmentDetails.getRange1()+danRaisedResult.getDouble(2));
					}
					else if(index == 2)
					{
						investmentDetails.setRange2(investmentDetails.getRange2()+danRaisedResult.getDouble(2));
					}
					else if(index == 3)
					{
						investmentDetails.setRange3(investmentDetails.getRange3()+danRaisedResult.getDouble(2));
					}
					else if(index == 4)
					{
						investmentDetails.setRange4(investmentDetails.getRange4()+danRaisedResult.getDouble(2));
					}
					else if(index == 5)
					{
						investmentDetails.setRange5(investmentDetails.getRange5()+danRaisedResult.getDouble(2));
					}
					else if(index == 6)
					{
						investmentDetails.setRange6(investmentDetails.getRange6()+danRaisedResult.getDouble(2));
					}
					else if(index == 7)
					{
						investmentDetails.setRange7(investmentDetails.getRange7()+danRaisedResult.getDouble(2));
					}
					else if(index == 8)
					{
						investmentDetails.setRange8(investmentDetails.getRange8()+danRaisedResult.getDouble(2));
					}
				}
			}

			Set investmentReportSet = investmentMap.keySet();
			Iterator investmentReportIterator = investmentReportSet.iterator();

			while(investmentReportIterator.hasNext())
			{
				InvestmentDetails investmentDetails = new InvestmentDetails();
				String investmentKey = (String)investmentReportIterator.next();
				investmentDetails = (InvestmentDetails)investmentMap.get(investmentKey);
				investmentDetails.setInstrumentName(investmentKey);
				investmentArray.add(investmentDetails);
			}

			danRaisedResult.close();
			danRaisedResult = null;
			danRaisedStmt.close();
			danRaisedStmt = null;
		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		finally
		{
			DBConnection.freeConnection(connection);
		}
	Log.log(Log.INFO,"IFDAO","investmentMaturityReport","Exited");
	return  investmentArray;
	}


	public ArrayList investmentMaturityReportDetails(java.sql.Date startDate,java.sql.Date endDate,
									String type) throws DatabaseException
	{
		Log.log(Log.INFO,"IFDAO","investmentMaturityReportDetails","Entered");
		PreparedStatement danRaisedStmt = null;
		ArrayList danRaisedArray = new ArrayList();
		ResultSet danRaisedResult;
		Connection connection = DBConnection.getConnection();

		try
		{
			String query = "select id.IDT_PURCHASE_DT, id.BSD_ID, id.IDT_RECEIPT_NUMBER, " +
				" id.IDT_MATURITY_DT, id.IDT_MATURITY_AMOUNT, id.IDT_INTEREST_RATE," +
				" id.IDT_COST_OF_PURCHASE from investment_detail id, instrument_master im " +
				" where im.INS_ID = id.INS_ID and im.INS_DESCRIPTION = ? and " +
				" trunc(id.IDT_MATURITY_DT) between ? AND ? ";

			danRaisedStmt = connection.prepareStatement(query);
			danRaisedStmt.setString(1,type);
			danRaisedStmt.setDate(2,startDate);
			danRaisedStmt.setDate(3,endDate);
			danRaisedResult = danRaisedStmt.executeQuery();

			while(danRaisedResult.next())
			{
				//Instantiate a Gfee value object
				InvestmentDetails investmentDetails = new InvestmentDetails();
				investmentDetails.setInvestmentDate(danRaisedResult.getDate(1));
				investmentDetails.setInvestmentNumber(danRaisedResult.getString(2));
				investmentDetails.setReceiptNumber(danRaisedResult.getString(3));
				investmentDetails.setMaturityDate(danRaisedResult.getDate(4));
				investmentDetails.setMaturityAmount(danRaisedResult.getDouble(5));
				investmentDetails.setInterestRate(danRaisedResult.getInt(6));
				investmentDetails.setInvestmentAmount(danRaisedResult.getDouble(7));

				danRaisedArray.add(investmentDetails);

			}
			danRaisedResult.close();
			danRaisedResult = null;
			danRaisedStmt.close();
			danRaisedStmt = null;
		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		finally
		{
			DBConnection.freeConnection(connection);
		}
	Log.log(Log.INFO,"IFDAO","investmentMaturityReportDetails","Exited");
	return  danRaisedArray;
	}

	public ArrayList investmentMaturityReportDetailsForEndDate(java.sql.Date endDate,
									String type) throws DatabaseException
	{
		Log.log(Log.INFO,"IFDAO","investmentMaturityReportDetailsForEndDate","Entered");
		PreparedStatement danRaisedStmt = null;
		ArrayList danRaisedArray = new ArrayList();
		ResultSet danRaisedResult;
		Connection connection = DBConnection.getConnection();

		try
		{
			String query = "select id.IDT_PURCHASE_DT, id.BSD_ID, id.IDT_RECEIPT_NUMBER, " +
				" id.IDT_MATURITY_DT, id.IDT_MATURITY_AMOUNT, id.IDT_INTEREST_RATE," +
				" id.IDT_COST_OF_PURCHASE from investment_detail id, instrument_master im " +
				" where im.INS_ID = id.INS_ID and im.INS_DESCRIPTION = ? and " +
				" trunc(id.IDT_MATURITY_DT) >= ? ";

			danRaisedStmt = connection.prepareStatement(query);
			danRaisedStmt.setString(1,type);
			danRaisedStmt.setDate(2,endDate);
			danRaisedResult = danRaisedStmt.executeQuery();

			while(danRaisedResult.next())
			{
				//Instantiate a Gfee value object
				InvestmentDetails investmentDetails = new InvestmentDetails();
				investmentDetails.setInvestmentDate(danRaisedResult.getDate(1));
				investmentDetails.setInvestmentNumber(danRaisedResult.getString(2));
				investmentDetails.setReceiptNumber(danRaisedResult.getString(3));
				investmentDetails.setMaturityDate(danRaisedResult.getDate(4));
				investmentDetails.setMaturityAmount(danRaisedResult.getDouble(5));
				investmentDetails.setInterestRate(danRaisedResult.getInt(6));
				investmentDetails.setInvestmentAmount(danRaisedResult.getDouble(7));

				danRaisedArray.add(investmentDetails);

			}
			danRaisedResult.close();
			danRaisedResult = null;
			danRaisedStmt.close();
			danRaisedStmt = null;
		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		finally
		{
			DBConnection.freeConnection(connection);
		}
	Log.log(Log.INFO,"IFDAO","investmentMaturityReportDetailsForEndDate","Exited");
	return  danRaisedArray;
	}

	public ArrayList investmentReport(java.sql.Date startDate,java.sql.Date endDate)
					throws DatabaseException
	{
		Log.log(Log.INFO,"ReportDAO","investmentReport","Entered");
		PreparedStatement danRaisedStmt = null;
		ArrayList danRaisedArray = new ArrayList();
		ResultSet danRaisedResult;
		Connection connection = DBConnection.getConnection();

		if(startDate != null)
		{
			try
			{
				String query = "select im.INS_DESCRIPTION, count(id.INS_ID), " +
					" sum(id.IDT_COST_OF_PURCHASE) from investment_detail id, " +
					" instrument_master im where im.INS_ID = id.INS_ID and " +
					" trunc(id.IDT_CREATED_MODIFIED_DT) between ? and ? group by " +
					" im.INS_DESCRIPTION";
				danRaisedStmt = connection.prepareStatement(query);
				danRaisedStmt.setDate(1,startDate);
				danRaisedStmt.setDate(2,endDate);
				danRaisedResult = danRaisedStmt.executeQuery();

				while(danRaisedResult.next())
				{
					//Instantiate a Gfee value object
					InvestmentDetails investmentDetails = new InvestmentDetails();
					investmentDetails.setInstrumentName(danRaisedResult.getString(1));
					investmentDetails.setInstruments(danRaisedResult.getInt(2));
					investmentDetails.setInvestmentAmount(danRaisedResult.getDouble(3));

					danRaisedArray.add(investmentDetails);

				}
				danRaisedResult.close();
				danRaisedResult = null;
				danRaisedStmt.close();
				danRaisedStmt = null;
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
		}

		else if(startDate == null)
		{
			try
			{
				String query = "select im.INS_DESCRIPTION, count(id.INS_ID), " +
					" sum(id.IDT_COST_OF_PURCHASE) from investment_detail id, " +
					" instrument_master im where im.INS_ID = id.INS_ID and " +
					" trunc(id.IDT_CREATED_MODIFIED_DT) <= ? group by " +
					" im.INS_DESCRIPTION";
				danRaisedStmt = connection.prepareStatement(query);
				danRaisedStmt.setDate(1,endDate);
				danRaisedResult = danRaisedStmt.executeQuery();

				while(danRaisedResult.next())
				{
					//Instantiate a Gfee value object
					InvestmentDetails investmentDetails = new InvestmentDetails();
					investmentDetails.setInstrumentName(danRaisedResult.getString(1));
					investmentDetails.setInstruments(danRaisedResult.getInt(2));
					investmentDetails.setInvestmentAmount(danRaisedResult.getDouble(3));

					danRaisedArray.add(investmentDetails);

				}
				danRaisedResult.close();
				danRaisedResult = null;
				danRaisedStmt.close();
				danRaisedStmt = null;
			}
			catch(Exception exception)
			{
				throw new DatabaseException(exception.getMessage());
			}
			finally
			{
				DBConnection.freeConnection(connection);
			}
		}
		Log.log(Log.INFO,"ReportDAO","investmentReport","Exited");
		return  danRaisedArray;
	}


	public ArrayList investmentReportDetails(java.sql.Date startDate,java.sql.Date endDate,
									String type) throws DatabaseException
	{
		Log.log(Log.INFO,"ReportDAO","investmentReportDetails","Entered");
		PreparedStatement danRaisedStmt = null;
		ArrayList danRaisedArray = new ArrayList();
		ResultSet danRaisedResult;
		Connection connection = DBConnection.getConnection();

		if(startDate != null)
		{
			try
			{
				String query = "select id.IDT_PURCHASE_DT, id.BSD_ID, " +
					" id.IDT_RECEIPT_NUMBER, id.IDT_MATURITY_DT, id.IDT_MATURITY_AMOUNT, " +
					" id.IDT_INTEREST_RATE from investment_detail id, " +
					" instrument_master im where im.INS_ID = id.INS_ID and " +
					" im.INS_DESCRIPTION = ? and trunc(id.IDT_CREATED_MODIFIED_DT) between " +
					"? and  ?";
				danRaisedStmt = connection.prepareStatement(query);
				danRaisedStmt.setString(1,type);
				danRaisedStmt.setDate(2,startDate);
				danRaisedStmt.setDate(3,endDate);
				danRaisedResult = danRaisedStmt.executeQuery();

				while(danRaisedResult.next())
				{
					//Instantiate a Gfee value object
					InvestmentDetails investmentDetails = new InvestmentDetails();
					investmentDetails.setInvestmentDate(danRaisedResult.getDate(1));
					investmentDetails.setInvestmentNumber(danRaisedResult.getString(2));
					investmentDetails.setReceiptNumber(danRaisedResult.getString(3));
					investmentDetails.setMaturityDate(danRaisedResult.getDate(4));
					investmentDetails.setMaturityAmount(danRaisedResult.getDouble(5));
					investmentDetails.setInterestRate(danRaisedResult.getInt(6));

					danRaisedArray.add(investmentDetails);

				}
				danRaisedResult.close();
				danRaisedResult = null;
				danRaisedStmt.close();
				danRaisedStmt = null;
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
		}

		else if(startDate == null)
		{
			try
			{
				String query = "select id.IDT_PURCHASE_DT, id.BSD_ID, " +
					" id.IDT_RECEIPT_NUMBER, id.IDT_MATURITY_DT, id.IDT_MATURITY_AMOUNT, " +
					" id.IDT_INTEREST_RATE from investment_detail id, " +
					" instrument_master im where im.INS_ID = id.INS_ID and " +
					" im.INS_DESCRIPTION = ? and trunc(id.IDT_CREATED_MODIFIED_DT) <= ? ";

				danRaisedStmt = connection.prepareStatement(query);
				danRaisedStmt.setString(1,type);
				danRaisedStmt.setDate(2,endDate);
				danRaisedResult = danRaisedStmt.executeQuery();

				while(danRaisedResult.next())
				{
					//Instantiate a Gfee value object
					InvestmentDetails investmentDetails = new InvestmentDetails();
					investmentDetails.setInvestmentDate(danRaisedResult.getDate(1));
					investmentDetails.setInvestmentNumber(danRaisedResult.getString(2));
					investmentDetails.setReceiptNumber(danRaisedResult.getString(3));
					investmentDetails.setMaturityDate(danRaisedResult.getDate(4));
					investmentDetails.setMaturityAmount(danRaisedResult.getDouble(5));
					investmentDetails.setInterestRate(danRaisedResult.getInt(6));

					danRaisedArray.add(investmentDetails);

				}
				danRaisedResult.close();
				danRaisedResult = null;
				danRaisedStmt.close();
				danRaisedStmt = null;
			}
			catch(Exception exception)
			{
				throw new DatabaseException(exception.getMessage());
			}
			finally
			{
				DBConnection.freeConnection(connection);
			}
		}
		Log.log(Log.INFO,"ReportDAO","investmentReportDetails","Exited");
		return  danRaisedArray;
	}


	public ArrayList investmentReportDetailsForBonds(String type) throws DatabaseException
	{
		Log.log(Log.INFO,"ReportDAO","investmentReportDetailsForBonds","Entered");
		PreparedStatement danRaisedStmt = null;
		ArrayList danRaisedArray = new ArrayList();
		ResultSet danRaisedResult;
		Connection connection = DBConnection.getConnection();


			try
			{
				String query = "select  inv.INV_NAME, m.MAT_TYPE,r.RAT_RATING,i.IDT_NAME," +
					" i.IDT_FACE_VALUE, i.IDT_NUMBER_OF_UNITS_BOUGHT,i.IDT_FOLIO_NUMBER," +
					" i.IDT_COST_OF_PURCHASE, i.IDT_TENURE,i.IDT_TENURE_TYPE, " +
					" i.IDT_CALL_PUT_OPTION, i.IDT_CALL_PUT_DURATION" +
					" ,i.IDT_PURCHASE_DT, i.IDT_RECEIPT_NUMBER,i.IDT_MATURITY_DT," +
					" i.IDT_MATURITY_AMOUNT, i.IDT_INTEREST_RATE from investment_detail i," +
					" maturity_master m, rating_master r, investee inv where " +
					" i.BSD_ID = ? and i.RAT_ID = r.RAT_ID and i.INV_ID = inv.INV_ID and " +
					" i.MAT_ID = m.MAT_ID ";

				danRaisedStmt = connection.prepareStatement(query);
				danRaisedStmt.setString(1,type);
				danRaisedResult = danRaisedStmt.executeQuery();

				while(danRaisedResult.next())
				{
					//Instantiate a Gfee value object
					InvestmentDetails investmentDetails = new InvestmentDetails();
					investmentDetails.setInvestee(danRaisedResult.getString(1));
					investmentDetails.setMaturityType(danRaisedResult.getString(2));
					investmentDetails.setRatingType(danRaisedResult.getString(3));
					investmentDetails.setInvestmentName(danRaisedResult.getString(4));
					investmentDetails.setFaceValue(danRaisedResult.getDouble(5));
					investmentDetails.setNumberOfUnits(danRaisedResult.getInt(6));
					investmentDetails.setFolioNumber(danRaisedResult.getString(7));
					investmentDetails.setInvestmentAmount(danRaisedResult.getDouble(8));
					investmentDetails.setTenure(danRaisedResult.getInt(9));
					investmentDetails.setTenureType(danRaisedResult.getString(10));
					investmentDetails.setCallPutOption(danRaisedResult.getString(11));
					investmentDetails.setCallPutDuration(danRaisedResult.getString(12));
					investmentDetails.setInvestmentDate(danRaisedResult.getDate(13));
					investmentDetails.setReceiptNumber(danRaisedResult.getString(14));
					investmentDetails.setMaturityDate(danRaisedResult.getDate(15));
					investmentDetails.setMaturityAmount(danRaisedResult.getDouble(16));
					investmentDetails.setInterestRate(danRaisedResult.getInt(17));

					danRaisedArray.add(investmentDetails);

				}
				danRaisedResult.close();
				danRaisedResult = null;
				danRaisedStmt.close();
				danRaisedStmt = null;
			}
			catch(Exception exception)
			{
				throw new DatabaseException(exception.getMessage());
			}
			finally
			{
				DBConnection.freeConnection(connection);
			}
		Log.log(Log.INFO,"ReportDAO","investmentReportDetailsForBonds","Exited");
		return  danRaisedArray;
	}

	public ArrayList investmentReportDetailsForCommercialpapers(String type) throws DatabaseException
	{
		Log.log(Log.INFO,"ReportDAO","investmentReportDetailsForCommercialpapers","Entered");
		PreparedStatement danRaisedStmt = null;
		ArrayList danRaisedArray = new ArrayList();
		ResultSet danRaisedResult;
		Connection connection = DBConnection.getConnection();


			try
			{
				String query = "select  inv.INV_NAME, m.MAT_TYPE,r.RAT_RATING,i.IDT_NAME," +
					" i.IDT_FACE_VALUE, i.IDT_NUMBER_OF_UNITS_BOUGHT,i.IDT_FOLIO_NUMBER," +
					" i.IDT_COST_OF_PURCHASE, i.IDT_TENURE,i.IDT_TENURE_TYPE, " +
					" i.IDT_CALL_PUT_OPTION, i.IDT_CALL_PUT_DURATION " +
					" ,i.IDT_PURCHASE_DT, i.IDT_RECEIPT_NUMBER,i.IDT_MATURITY_DT, " +
					"i.IDT_MATURITY_AMOUNT, i.IDT_INTEREST_RATE from investment_detail i," +
					" maturity_master m, rating_master r, investee inv where " +
					" i.BSD_ID = ? and i.RAT_ID = r.RAT_ID and i.INV_ID = inv.INV_ID and " +
					" i.MAT_ID = m.MAT_ID ";

				danRaisedStmt = connection.prepareStatement(query);
				danRaisedStmt.setString(1,type);
				danRaisedResult = danRaisedStmt.executeQuery();

				while(danRaisedResult.next())
				{
					//Instantiate a Gfee value object
					InvestmentDetails investmentDetails = new InvestmentDetails();
					investmentDetails.setInvestee(danRaisedResult.getString(1));
					investmentDetails.setMaturityType(danRaisedResult.getString(2));
					investmentDetails.setRatingType(danRaisedResult.getString(3));
					investmentDetails.setInvestmentName(danRaisedResult.getString(4));
					investmentDetails.setFaceValue(danRaisedResult.getDouble(5));
					investmentDetails.setNumberOfUnits(danRaisedResult.getInt(6));
					investmentDetails.setFolioNumber(danRaisedResult.getString(7));
					investmentDetails.setInvestmentAmount(danRaisedResult.getDouble(8));
					investmentDetails.setTenure(danRaisedResult.getInt(9));
					investmentDetails.setTenureType(danRaisedResult.getString(10));
					investmentDetails.setCallPutOption(danRaisedResult.getString(11));
					investmentDetails.setCallPutDuration(danRaisedResult.getString(12));
					investmentDetails.setInvestmentDate(danRaisedResult.getDate(13));
					investmentDetails.setReceiptNumber(danRaisedResult.getString(14));
					investmentDetails.setMaturityDate(danRaisedResult.getDate(15));
					investmentDetails.setMaturityAmount(danRaisedResult.getDouble(16));
					investmentDetails.setInterestRate(danRaisedResult.getInt(17));

					danRaisedArray.add(investmentDetails);

				}
				danRaisedResult.close();
				danRaisedResult = null;
				danRaisedStmt.close();
				danRaisedStmt = null;
			}
			catch(Exception exception)
			{
				throw new DatabaseException(exception.getMessage());
			}
			finally
			{
				DBConnection.freeConnection(connection);
			}
		Log.log(Log.INFO,"ReportDAO","investmentReportDetailsForCommercialpapers","Exited");
		return  danRaisedArray;
	}

	public ArrayList investmentReportDetailsForDebentures(String type) throws DatabaseException
	{
		Log.log(Log.INFO,"ReportDAO","investmentReportDetailsForDebentures","Entered");
		PreparedStatement danRaisedStmt = null;
		ArrayList danRaisedArray = new ArrayList();
		ResultSet danRaisedResult;
		Connection connection = DBConnection.getConnection();


			try
			{
				String query = "select  inv.INV_NAME, m.MAT_TYPE,r.RAT_RATING,i.IDT_NAME," +
					" i.IDT_FACE_VALUE, i.IDT_NUMBER_OF_UNITS_BOUGHT,i.IDT_FOLIO_NUMBER," +
					" i.IDT_COST_OF_PURCHASE, i.IDT_TENURE,i.IDT_TENURE_TYPE, " +
					" i.IDT_CALL_PUT_OPTION, i.IDT_CALL_PUT_DURATION" +
					" ,i.IDT_PURCHASE_DT, i.IDT_RECEIPT_NUMBER,i.IDT_MATURITY_DT, " +
					" i.IDT_MATURITY_AMOUNT, i.IDT_INTEREST_RATE   from investment_detail i," +
					" maturity_master m, rating_master r, investee inv where " +
					" i.BSD_ID = ? and i.RAT_ID = r.RAT_ID and i.INV_ID = inv.INV_ID and " +
					" i.MAT_ID = m.MAT_ID ";

				danRaisedStmt = connection.prepareStatement(query);
				danRaisedStmt.setString(1,type);
				danRaisedResult = danRaisedStmt.executeQuery();

				while(danRaisedResult.next())
				{
					//Instantiate a Gfee value object
					InvestmentDetails investmentDetails = new InvestmentDetails();
					investmentDetails.setInvestee(danRaisedResult.getString(1));
					investmentDetails.setMaturityType(danRaisedResult.getString(2));
					investmentDetails.setRatingType(danRaisedResult.getString(3));
					investmentDetails.setInvestmentName(danRaisedResult.getString(4));
					investmentDetails.setFaceValue(danRaisedResult.getDouble(5));
					investmentDetails.setNumberOfUnits(danRaisedResult.getInt(6));
					investmentDetails.setFolioNumber(danRaisedResult.getString(7));
					investmentDetails.setInvestmentAmount(danRaisedResult.getDouble(8));
					investmentDetails.setTenure(danRaisedResult.getInt(9));
					investmentDetails.setTenureType(danRaisedResult.getString(10));
					investmentDetails.setCallPutOption(danRaisedResult.getString(11));
					investmentDetails.setCallPutDuration(danRaisedResult.getString(12));
					investmentDetails.setInvestmentDate(danRaisedResult.getDate(13));
					investmentDetails.setReceiptNumber(danRaisedResult.getString(14));
					investmentDetails.setMaturityDate(danRaisedResult.getDate(15));
					investmentDetails.setMaturityAmount(danRaisedResult.getDouble(16));
					investmentDetails.setInterestRate(danRaisedResult.getInt(17));

					danRaisedArray.add(investmentDetails);

				}
				danRaisedResult.close();
				danRaisedResult = null;
				danRaisedStmt.close();
				danRaisedStmt = null;
			}
			catch(Exception exception)
			{
				throw new DatabaseException(exception.getMessage());
			}
			finally
			{
				DBConnection.freeConnection(connection);
			}
		Log.log(Log.INFO,"ReportDAO","investmentReportDetailsForDebentures","Exited");
		return  danRaisedArray;
	}

	public ArrayList investmentReportDetailsForGSecurities(String type) throws DatabaseException
	{
		Log.log(Log.INFO,"ReportDAO","investmentReportDetailsForGSecurities","Entered");
		PreparedStatement danRaisedStmt = null;
		ArrayList danRaisedArray = new ArrayList();
		ResultSet danRaisedResult;
		Connection connection = DBConnection.getConnection();


			try
			{
				String query = "select  inv.INV_NAME, m.MAT_TYPE,r.RAT_RATING,i.IDT_NAME," +
					" i.IDT_FACE_VALUE, i.IDT_NUMBER_OF_UNITS_BOUGHT,i.IDT_FOLIO_NUMBER," +
					" i.IDT_COST_OF_PURCHASE, i.IDT_TENURE,i.IDT_TENURE_TYPE, " +
					" i.IDT_CALL_PUT_OPTION, i.IDT_CALL_PUT_DURATION " +
					" ,i.IDT_PURCHASE_DT, i.IDT_RECEIPT_NUMBER,i.IDT_MATURITY_DT, " +
					" i.IDT_MATURITY_AMOUNT, i.IDT_INTEREST_RATE  from investment_detail i," +
					" maturity_master m, rating_master r, investee inv where " +
					" i.BSD_ID = ? and i.RAT_ID = r.RAT_ID and i.INV_ID = inv.INV_ID and " +
					" i.MAT_ID = m.MAT_ID ";

				danRaisedStmt = connection.prepareStatement(query);
				danRaisedStmt.setString(1,type);
				danRaisedResult = danRaisedStmt.executeQuery();

				while(danRaisedResult.next())
				{
					//Instantiate a Gfee value object
					InvestmentDetails investmentDetails = new InvestmentDetails();
					investmentDetails.setInvestee(danRaisedResult.getString(1));
					investmentDetails.setMaturityType(danRaisedResult.getString(2));
					investmentDetails.setRatingType(danRaisedResult.getString(3));
					investmentDetails.setInvestmentName(danRaisedResult.getString(4));
					investmentDetails.setFaceValue(danRaisedResult.getDouble(5));
					investmentDetails.setNumberOfUnits(danRaisedResult.getInt(6));
					investmentDetails.setFolioNumber(danRaisedResult.getString(7));
					investmentDetails.setInvestmentAmount(danRaisedResult.getDouble(8));
					investmentDetails.setTenure(danRaisedResult.getInt(9));
					investmentDetails.setTenureType(danRaisedResult.getString(10));
					investmentDetails.setCallPutOption(danRaisedResult.getString(11));
					investmentDetails.setCallPutDuration(danRaisedResult.getString(12));
					investmentDetails.setInvestmentDate(danRaisedResult.getDate(13));
					investmentDetails.setReceiptNumber(danRaisedResult.getString(14));
					investmentDetails.setMaturityDate(danRaisedResult.getDate(15));
					investmentDetails.setMaturityAmount(danRaisedResult.getDouble(16));
					investmentDetails.setInterestRate(danRaisedResult.getInt(17));

					danRaisedArray.add(investmentDetails);

				}
				danRaisedResult.close();
				danRaisedResult = null;
				danRaisedStmt.close();
				danRaisedStmt = null;
			}
			catch(Exception exception)
			{
				throw new DatabaseException(exception.getMessage());
			}
			finally
			{
				DBConnection.freeConnection(connection);
			}
		Log.log(Log.INFO,"ReportDAO","investmentReportDetailsForGSecurities","Exited");
		return  danRaisedArray;
	}


	public ArrayList investmentReportDetailsForMutualFund(String type) throws DatabaseException
	{
		Log.log(Log.INFO,"ReportDAO","investmentReportDetailsForMutualFund","Entered");
		PreparedStatement danRaisedStmt = null;
		ArrayList danRaisedArray = new ArrayList();
		ResultSet danRaisedResult;
		Connection connection = DBConnection.getConnection();


			try
			{
				String query = "select  inv.INV_NAME, m.MAT_TYPE, r.RAT_RATING,i.IDT_NAME," +
					" i.IDT_NUMBER_OF_UNITS_BOUGHT, i.IDT_COST_OF_PURCHASE, " +
					" i.IDT_PAR_VALUE, i.IDT_PURCHASE_DT_NAV, i.IDT_CURRENT_DT_NAV,  " +
					" i.IDT_ISIN_NUMBER, i.IDT_OPEN_CLOSE, i.IDT_SELLING_DT,s.ITS_TYPE, " +
					" i.IDT_ENTRY_LOAD, i.IDT_EXIT_LOAD,  i.IDT_MARK_TO_MARKET " +
					" ,i.IDT_PURCHASE_DT, i.IDT_RECEIPT_NUMBER,i.IDT_MATURITY_DT, " +
					" i.IDT_MATURITY_AMOUNT, i.IDT_INTEREST_RATE  from " +
					" investment_detail i, maturity_master m, rating_master r, " +
					" investee inv, instrument_scheme s where i.BSD_ID = ? and " +
					" i.RAT_ID = r.RAT_ID and i.INV_ID = inv.INV_ID and  " +
					" i.MAT_ID = m.MAT_ID and i.ITS_ID = s.ITS_ID";

				danRaisedStmt = connection.prepareStatement(query);
				danRaisedStmt.setString(1,type);
				danRaisedResult = danRaisedStmt.executeQuery();

				while(danRaisedResult.next())
				{
					//Instantiate a Gfee value object
					InvestmentDetails investmentDetails = new InvestmentDetails();
					investmentDetails.setInvestee(danRaisedResult.getString(1));
					investmentDetails.setMaturityType(danRaisedResult.getString(2));
					investmentDetails.setRatingType(danRaisedResult.getString(3));
					investmentDetails.setInvestmentName(danRaisedResult.getString(4));
					investmentDetails.setNumberOfUnits(danRaisedResult.getInt(5));
					investmentDetails.setInvestmentAmount(danRaisedResult.getDouble(6));
					investmentDetails.setFaceValue(danRaisedResult.getDouble(7));
					investmentDetails.setInvestedDateNav(danRaisedResult.getDouble(8));
					investmentDetails.setCurrentDateNav(danRaisedResult.getDouble(9));
					investmentDetails.setIsinNumber(danRaisedResult.getString(10));
					investmentDetails.setOpenClose(danRaisedResult.getString(11));
					investmentDetails.setSellingDate(danRaisedResult.getDate(12));
					investmentDetails.setInstrumentName(danRaisedResult.getString(13));
					investmentDetails.setEntryLoad(danRaisedResult.getDouble(14));
					investmentDetails.setExitLoad(danRaisedResult.getDouble(15));
					investmentDetails.setMarkToMarket(danRaisedResult.getString(16));
					investmentDetails.setInvestmentDate(danRaisedResult.getDate(17));
					investmentDetails.setReceiptNumber(danRaisedResult.getString(18));
					investmentDetails.setMaturityDate(danRaisedResult.getDate(19));
					investmentDetails.setMaturityAmount(danRaisedResult.getDouble(20));
					investmentDetails.setInterestRate(danRaisedResult.getInt(21));

					danRaisedArray.add(investmentDetails);

				}
				danRaisedResult.close();
				danRaisedResult = null;
				danRaisedStmt.close();
				danRaisedStmt = null;
			}
			catch(Exception exception)
			{
				throw new DatabaseException(exception.getMessage());
			}
			finally
			{
				DBConnection.freeConnection(connection);
			}
		Log.log(Log.INFO,"ReportDAO","investmentReportDetailsForMutualFund","Exited");
		return  danRaisedArray;
	}

	public ArrayList investmentReportDetailsForFixedDeposit(String type) throws DatabaseException
	{
		Log.log(Log.INFO,"ReportDAO","investmentReportDetailsForFixedDeposit","Entered");
		PreparedStatement danRaisedStmt = null;
		ArrayList danRaisedArray = new ArrayList();
		ResultSet danRaisedResult;
		Connection connection = DBConnection.getConnection();


			try
			{
				String query = "select  inv.INV_NAME, m.MAT_TYPE,r.RAT_RATING," +
					" i.IDT_COST_OF_PURCHASE, i.IDT_COMPOUNDING_FREQUENCY, " +
					" i.IDT_TENURE,i.IDT_TENURE_TYPE " +
					" ,i.IDT_PURCHASE_DT, i.IDT_RECEIPT_NUMBER,i.IDT_MATURITY_DT, " +
					" i.IDT_MATURITY_AMOUNT, i.IDT_INTEREST_RATE  from investment_detail i, " +
					" maturity_master m, rating_master r, investee inv where " +
					" i.BSD_ID = ? and i.MAT_ID = m.MAT_ID and i.RAT_ID = r.RAT_ID " +
					" and i.INV_ID = inv.INV_ID ";

				danRaisedStmt = connection.prepareStatement(query);
				danRaisedStmt.setString(1,type);
				danRaisedResult = danRaisedStmt.executeQuery();

				while(danRaisedResult.next())
				{
					//Instantiate a Gfee value object
					InvestmentDetails investmentDetails = new InvestmentDetails();
					investmentDetails.setInvestee(danRaisedResult.getString(1));
					investmentDetails.setMaturityType(danRaisedResult.getString(2));
					investmentDetails.setRatingType(danRaisedResult.getString(3));
					investmentDetails.setInvestmentAmount(danRaisedResult.getDouble(4));
					investmentDetails.setFrequency(danRaisedResult.getInt(5));
					investmentDetails.setTenure(danRaisedResult.getInt(6));
					investmentDetails.setTenureType(danRaisedResult.getString(7));
					investmentDetails.setInvestmentDate(danRaisedResult.getDate(8));
					investmentDetails.setReceiptNumber(danRaisedResult.getString(9));
					investmentDetails.setMaturityDate(danRaisedResult.getDate(10));
					investmentDetails.setMaturityAmount(danRaisedResult.getDouble(11));
					investmentDetails.setInterestRate(danRaisedResult.getInt(12));

					danRaisedArray.add(investmentDetails);
				}
				danRaisedResult.close();
				danRaisedResult = null;
				danRaisedStmt.close();
				danRaisedStmt = null;
			}
			catch(Exception exception)
			{
				throw new DatabaseException(exception.getMessage());
			}
			finally
			{
				DBConnection.freeConnection(connection);
			}
		Log.log(Log.INFO,"ReportDAO","investmentReportDetailsForFixedDeposit","Exited");
		return  danRaisedArray;
	}



	public void hvcInsertSuccess(ChequeDetails chequeDetails)
								 throws DatabaseException
	{
		Log.log(Log.INFO,"IFDAO","hvcInsertSuccess","Entered");
		Connection connection = DBConnection.getConnection();
		CallableStatement chequeStmt = null;

		try
		{
			chequeStmt = connection.prepareCall("{? = call funcAddHvc(?,?,?,?,?,?,?,?,?)}");
			chequeStmt.registerOutParameter(1, Types.INTEGER);
			chequeStmt.registerOutParameter(10, Types.VARCHAR);
			chequeStmt.setString(2,chequeDetails.getUserId());
			chequeStmt.setDouble(3,chequeDetails.getChequeAmount());
			//System.out.println(chequeDetails.getChequeAmount());
			java.util.Date utilDate = chequeDetails.getChequeDate();
			java.sql.Date sqlDate = new java.sql.Date (utilDate.getTime());
			chequeStmt.setDate(4,sqlDate);
			//System.out.println(sqlDate);
			chequeStmt.setString(5,chequeDetails.getBankName());
			//System.out.println(chequeDetails.getBankName());
			chequeStmt.setString(6,chequeDetails.getBranchName());
			//System.out.println(chequeDetails.getBankName());
			chequeStmt.setString(7,chequeDetails.getToBankName());
			//System.out.println(chequeDetails.getChequeNumber());
			chequeStmt.setString(8,chequeDetails.getToBranchName());
			//System.out.println(chequeDetails.getChequeNumber());
			chequeStmt.setString(9,chequeDetails.getChequeRemarks());
			//System.out.println(chequeDetails.getChequeRemarks());

			chequeStmt.executeQuery();

			int status = chequeStmt.getInt(1);

			if(status == Constants.FUNCTION_FAILURE)
			{
				String errorCode = chequeStmt.getString(10);
				Log.log(Log.ERROR,"IFDAO","chequeDetailsUpdate","SP returns a 1." +
										" Error code is :" + errorCode);
				chequeStmt.close();
				chequeStmt = null;
			}
			else if(status == Constants.FUNCTION_SUCCESS)
			{
				chequeStmt.close();
				chequeStmt = null;
			}
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
			throw new DatabaseException(exception.getMessage());
		}
		finally
		{
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO,"IFDAO","hvcInsertSuccess","Exited");
	}



	public ArrayList hvcUpdate(java.sql.Date startDate,java.sql.Date endDate)
								 throws DatabaseException
	{
		Log.log(Log.INFO,"IFDAO","hvcUpdate","Entered");
		ArrayList chequeArray = new ArrayList();
		ResultSet chequeResult;
		Connection connection = DBConnection.getConnection();
		CallableStatement chequeStmt = null;
		ChequeDetails chequeDetails  = null;

		try
		{
			chequeStmt = connection.prepareCall("{? = call packGetAllHvcNumbers.funcGetAllHvcNumbers(?,?,?,?)}");
			chequeStmt.setDate(2,startDate);
			chequeStmt.setDate(3,endDate);
			chequeStmt.registerOutParameter(1, Types.INTEGER);
			chequeStmt.registerOutParameter(4,Constants.CURSOR);
			chequeStmt.registerOutParameter(5, Types.VARCHAR);

			chequeStmt.executeQuery();

			int status = chequeStmt.getInt(1);



			if(status == Constants.FUNCTION_FAILURE)
			{
				String errorCode = chequeStmt.getString(5);
				//String errorCode = chequeStmt.getString(5);
				Log.log(Log.ERROR,"IFDAO","hvcUpdate","SP returns a 1." +
										" Error code is :" + errorCode);
				chequeStmt.close();
				chequeStmt = null;
			}
			else if(status == Constants.FUNCTION_SUCCESS)
			{
				chequeResult = (ResultSet)chequeStmt.getObject(4);
				while(chequeResult.next())
				{
					chequeDetails  = new ChequeDetails();
					chequeDetails.setChequeNumber(chequeResult.getString(1));
					chequeDetails.setChequeAmount(chequeResult.getDouble(2));
					chequeDetails.setChequeDate(chequeResult.getDate(5));
					chequeDetails.setBankName(chequeResult.getString(7));
					chequeDetails.setToBankName(chequeResult.getString(6));
					chequeDetails.setExpiryDate(chequeResult.getInt(8));
					System.out.println(chequeResult.getInt(8));
					chequeArray.add(chequeDetails);
				}
				chequeStmt.close();
				chequeStmt = null;
			}
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
			throw new DatabaseException(exception.getMessage());
		}
		finally
		{
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO,"IFDAO","hvcUpdate","Exited");
		return chequeArray;
	}


	public ChequeDetails hvcUpdatePage(String chequeNumber)throws DatabaseException
	{
		Log.log(Log.INFO,"IFDAO","hvcUpdatePage","Entered");
		ChequeDetails chequeDetails = new ChequeDetails();
		ResultSet chequeResult;
		Connection connection = DBConnection.getConnection();
		CallableStatement chequeStmt = null;

			try
			{
				chequeStmt = connection.prepareCall("{? = call funcGetHvcDetails(?,?,?,?,?,?,?,?,?)}");
				chequeStmt.setString(2,chequeNumber);
				chequeStmt.registerOutParameter(1, Types.INTEGER);
				chequeStmt.registerOutParameter(3,Types.VARCHAR);
				chequeStmt.registerOutParameter(4, Types.DATE);
				chequeStmt.registerOutParameter(5, Types.VARCHAR);
				chequeStmt.registerOutParameter(6, Types.VARCHAR);
				chequeStmt.registerOutParameter(7, Types.VARCHAR);
				chequeStmt.registerOutParameter(8, Types.VARCHAR);
				chequeStmt.registerOutParameter(9, Types.VARCHAR);
				chequeStmt.registerOutParameter(10,Types.VARCHAR);
				chequeStmt.execute();
				int status = chequeStmt.getInt(1);


				if(status == Constants.FUNCTION_FAILURE)
				{
					String errorCode = chequeStmt.getString(10);
					Log.log(Log.ERROR,"IFDAO","hvcUpdatePage","SP returns a 1." +
											" Error code is :" + errorCode);
					chequeStmt.close();
					chequeStmt = null;
				}
				else if(status == Constants.FUNCTION_SUCCESS)
				{

					chequeDetails  = new ChequeDetails();
					chequeDetails.setChequeAmount(chequeStmt.getDouble(3));
					chequeDetails.setChequeDate(chequeStmt.getDate(4));
					chequeDetails.setBankName(chequeStmt.getString(5));
					chequeDetails.setToBankName(chequeStmt.getString(6));
					chequeDetails.setChequeRemarks(chequeStmt.getString(9));

					chequeStmt.close();
					chequeStmt = null;
				}

			}
			catch(Exception exception)
			{
				exception.printStackTrace();
				throw new DatabaseException(exception.getMessage());
			}
			finally
			{
				DBConnection.freeConnection(connection);
			}
		Log.log(Log.INFO,"IFDAO","hvcUpdatePage","Exited");
		return chequeDetails;
	}


	public void hvcUpdateSuccess(ChequeDetails chequeDetails, String chequeNumber)
								 throws DatabaseException
	{
		Log.log(Log.INFO,"IFDAO","hvcUpdateSuccess","Entered");
		Connection connection = DBConnection.getConnection();
		CallableStatement chequeStmt = null;

		try
		{
			chequeStmt = connection.prepareCall("{? = call funcUpdateHvcDetails(?,?,?,?,?,?,?,?,?,?)}");
			chequeStmt.registerOutParameter(1, Types.INTEGER);
			chequeStmt.registerOutParameter(11, Types.VARCHAR);
			chequeStmt.setString(2,chequeNumber);
			chequeStmt.setDouble(4,chequeDetails.getChequeAmount());
			java.util.Date utilDate = chequeDetails.getChequeDate();
			java.sql.Date sqlDate = new java.sql.Date (utilDate.getTime());
			chequeStmt.setDate(5,sqlDate);
			chequeStmt.setString(6,chequeDetails.getBankName());
			chequeStmt.setString(7,chequeDetails.getBranchName());
			chequeStmt.setString(8,chequeDetails.getToBankName());
			chequeStmt.setString(9,chequeDetails.getToBranchName());
			chequeStmt.setString(10,chequeDetails.getChequeRemarks());
			chequeStmt.setString(3,chequeDetails.getUserId());

			chequeStmt.executeQuery();

			int status = chequeStmt.getInt(1);

			if(status == Constants.FUNCTION_FAILURE)
			{
				String errorCode = chequeStmt.getString(11);
				Log.log(Log.ERROR,"IFDAO","hvcUpdateSuccess","SP returns a 1." +
										" Error code is :" + errorCode);
				chequeStmt.close();
				chequeStmt = null;
			}
			else if(status == Constants.FUNCTION_SUCCESS)
			{
				chequeStmt.close();
				chequeStmt = null;
			}
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
			throw new DatabaseException(exception.getMessage());
		}
		finally
		{
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO,"IFDAO","hvcUpdateSuccess","Exited");
	}





	/*
	* Cheque Details
	*
	*/


	public void chequeDetailsInsertSuccess(ChequeDetails chequeDetails,Connection connection,
							String contextPath, String user) throws DatabaseException,MessageException
	{
		Log.log(Log.INFO,"IFDAO","chequeDetailsInsertSuccess","Entered");
		boolean newconn=false;
		RpDAO rpDAO=new RpDAO();

		if(connection == null)
		{
			 connection = DBConnection.getConnection(false);
			 newconn = true;
		}
		CallableStatement chequeStmt = null;

		try
		{
			chequeStmt = connection.prepareCall("{? = call funcAddChequeDetails(?,?,?,?,?,?,?,?,?,?,?)}");
			chequeStmt.registerOutParameter(1, Types.INTEGER);
			chequeStmt.registerOutParameter(12, Types.VARCHAR);
			chequeStmt.setString(2,chequeDetails.getUserId());
			chequeStmt.setDouble(3,chequeDetails.getChequeAmount());
			java.util.Date utilDate1 = chequeDetails.getChequeDate();
			java.sql.Date sqlDate1 = new java.sql.Date (utilDate1.getTime());
			chequeStmt.setDate(4,sqlDate1);
			chequeStmt.setString(5,chequeDetails.getChequeNumber());
			chequeStmt.setString(6,chequeDetails.getChequeIssuedTo());
			chequeStmt.setString(7,chequeDetails.getBankName());
			chequeStmt.setString(8,chequeDetails.getBranchName());
			chequeStmt.setString(9,chequeDetails.getChequeRemarks());
			chequeStmt.setString(10,chequeDetails.getPayId());
			chequeStmt.setString(11,"I");

			chequeStmt.executeQuery();
			int status = chequeStmt.getInt(1);


			if(status == Constants.FUNCTION_FAILURE)
			{
				String errorCode = chequeStmt.getString(12);
				Log.log(Log.ERROR,"IFDAO","chequeDetailsInsertSuccess","SP returns a 1." +
										" Error code is :" + errorCode);
				connection.rollback();
				chequeStmt.close();
				chequeStmt = null;
				
				throw new DatabaseException(errorCode);
			}
			else if(status == Constants.FUNCTION_SUCCESS)
			{
				chequeStmt.close();
				chequeStmt = null;

				String bankName = chequeDetails.getBankName();
				String number = chequeDetails.getChequeNumber();
				double amount = chequeDetails.getChequeAmount();
				String newAmount = String.valueOf(amount);
				String issuedTo = chequeDetails.getChequeIssuedTo();
				java.util.Date date= chequeDetails.getChequeDate();
				String userId = chequeDetails.getUserId();
				String newDate = date.toString();


				Properties accCodes=new Properties();
				File tempFile = new File(contextPath+"\\WEB-INF\\classes",RpConstants.AC_CODE_FILE_NAME);
				Log.log(Log.DEBUG,"IFDAO","chequeDetailsInsertSuccess","file opened ");
				File accCodeFile = new File(tempFile.getAbsolutePath());
				try
				{
					FileInputStream fin = new FileInputStream(accCodeFile);
					accCodes.load(fin);
				}
				catch(FileNotFoundException fe)
				{
					throw new MessageException("Could not load Account Codes.");
				}
				catch(IOException ie)
				{
					throw new MessageException("Could not load Account Codes.");
				}
				Log.log(Log.DEBUG,"IFDAO","chequeDetailsInsertSuccess","props loaded ");

				RpProcessor rpProcessor = new RpProcessor();
				VoucherDetail voucherDetail = new VoucherDetail();
				ArrayList vouchers = new ArrayList();
				voucherDetail.setBankGLCode(accCodes.getProperty(RpConstants.BANK_AC));
				voucherDetail.setBankGLName(bankName);
				voucherDetail.setDeptCode(RpConstants.RP_CGTSI);
				voucherDetail.setAmount(0-amount);
				voucherDetail.setVoucherType(RpConstants.PAYMENT_VOUCHER);
				voucherDetail.setNarration("IssuedTo-"+issuedTo);

				Voucher voucher = new Voucher();
				voucher.setAcCode(accCodes.getProperty(RpConstants.PAYMENT_AC));
				voucher.setPaidTo(issuedTo);
				voucher.setDebitOrCredit("D");
				voucher.setInstrumentDate(newDate);
				voucher.setInstrumentNo(number);
				voucher.setInstrumentType("Cheque");
				voucher.setAmountInRs(newAmount);
				vouchers.add(voucher);
				voucherDetail.setVouchers(vouchers);

				rpDAO.insertVoucherDetails(voucherDetail,userId, connection);

			}
			if(newconn)
			{
				connection.commit();
			}
		}
		catch(SQLException e)
		{
			Log.log(Log.ERROR,"IFDAO","insertChequeDetails",e.getMessage());
			Log.logException(e);
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			throw new DatabaseException(e.getMessage());
		}
		finally
		{
			if(newconn)
			{
				DBConnection.freeConnection(connection);
			}

		}
		Log.log(Log.INFO,"IFDAO","chequeDetailsInsertSuccess","Exited");
	}


	public ArrayList chequeDetailsUpdate(java.sql.Date startDate,java.sql.Date endDate)
								 throws DatabaseException
	{
		Log.log(Log.INFO,"IFDAO","chequeDetailsUpdate","Entered");
		ArrayList chequeArray = new ArrayList();
		ResultSet chequeResult;
		Connection connection = DBConnection.getConnection();
		CallableStatement chequeStmt = null;
		ChequeDetails chequeDetails  = null;

		try
		{
			chequeStmt = connection.prepareCall("{? = call packGetAllChequeNumbers.funcGetAllChequeNumbers(?,?,?,?)}");
			chequeStmt.setDate(2,startDate);
			chequeStmt.setDate(3,endDate);
			chequeStmt.registerOutParameter(1, Types.INTEGER);
			chequeStmt.registerOutParameter(4,Constants.CURSOR);
			chequeStmt.registerOutParameter(5, Types.VARCHAR);

			chequeStmt.executeQuery();

			int status = chequeStmt.getInt(1);

			if(status == Constants.FUNCTION_FAILURE)
			{
				String errorCode = chequeStmt.getString(5);
				Log.log(Log.ERROR,"IFDAO","chequeDetailsUpdate","SP returns a 1." +
										" Error code is :" + errorCode);
				chequeStmt.close();
				chequeStmt = null;
			}
			else if(status == Constants.FUNCTION_SUCCESS)
			{
				chequeResult = (ResultSet)chequeStmt.getObject(4);
				while(chequeResult.next())
				{
					chequeDetails  = new ChequeDetails();
					chequeDetails.setBankName(chequeResult.getString(1));
					//System.out.println("1:"+chequeResult.getString(1));
					chequeDetails.setChequeNumber(chequeResult.getString(2));
					//System.out.println("2:"+chequeResult.getString(2));
					chequeDetails.setChequeAmount(chequeResult.getDouble(3));
					//System.out.println("3:"+chequeResult.getDouble(3));
					chequeDetails.setChequeDate(chequeResult.getDate(4));
					//System.out.println("4:"+chequeResult.getDate(4));
					chequeDetails.setChequeIssuedTo(chequeResult.getString(5));
					//System.out.println("5:"+chequeResult.getString(5));
					chequeDetails.setChequeId(chequeResult.getString(6));
					//System.out.println("6:"+chequeResult.getString(6));
					chequeDetails.setExpiryDate(chequeResult.getInt(8));
					//System.out.println("8:"+chequeResult.getInt(8));
					chequeDetails.setStatus(chequeResult.getString(9));
					//System.out.println("9:"+chequeResult.getString(9));
					chequeArray.add(chequeDetails);
				}
				chequeStmt.close();
				chequeStmt = null;
			}
		}
		catch(Exception exception)
		{
			//exception.printStackTrace();
			throw new DatabaseException(exception.getMessage());
		}
		finally
		{
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO,"IFDAO","chequeDetailsUpdate","Exited");
		return chequeArray;
	}


	public ChequeDetails chequeDetailsUpdatePage(String chequeNumber)throws DatabaseException
	{
		Log.log(Log.INFO,"IFDAO","chequeDetailsUpdatePage","Entered");
		ChequeDetails chequeDetails = new ChequeDetails();
		ResultSet chequeResult;
		Connection connection = DBConnection.getConnection();
		CallableStatement chequeStmt = null;

			try
			{
				chequeStmt = connection.prepareCall("{? = call packGetChequeDetails.funcGetChequeDetails(?,?,?)}");
				chequeStmt.setString(2,chequeNumber);
				chequeStmt.registerOutParameter(1, Types.INTEGER);
				chequeStmt.registerOutParameter(3,Constants.CURSOR);
				chequeStmt.registerOutParameter(4, Types.VARCHAR);

				chequeStmt.executeQuery();

				int status = chequeStmt.getInt(1);


				if(status == Constants.FUNCTION_FAILURE)
				{
					String errorCode = chequeStmt.getString(4);
					Log.log(Log.ERROR,"IFDAO","chequeDetailsUpdatePage","SP returns a 1." +
											" Error code is :" + errorCode);
					chequeStmt.close();
					chequeStmt = null;
				}
				else if(status == Constants.FUNCTION_SUCCESS)
				{
					chequeResult = (ResultSet)chequeStmt.getObject(3);
					while(chequeResult.next())
					{
						chequeDetails  = new ChequeDetails();
						chequeDetails.setChequeNumber(chequeResult.getString(1));
						chequeDetails.setChequeAmount(chequeResult.getDouble(2));
						chequeDetails.setChequeDate(chequeResult.getDate(3));
						chequeDetails.setChequeIssuedTo(chequeResult.getString(4));
						chequeDetails.setBankName(chequeResult.getString(5));
						chequeDetails.setChequeId(chequeResult.getString(6));
						chequeDetails.setChequeRemarks(chequeResult.getString(7));
						chequeDetails.setStatus(chequeResult.getString(8));

					}
					chequeStmt.close();
					chequeStmt = null;
				}

			}
			catch(Exception exception)
			{
				exception.printStackTrace();
				throw new DatabaseException(exception.getMessage());
			}
			finally
			{
				DBConnection.freeConnection(connection);
			}
		Log.log(Log.INFO,"IFDAO","chequeDetailsUpdatePage","Exited");
		return chequeDetails;
	}


	public void chequeDetailsUpdateSuccess(ChequeDetails chequeDetails, String chequeNumber)
								 throws DatabaseException
	{
		Log.log(Log.INFO,"IFDAO","chequeDetailsUpdateSuccess","Entered");
		Connection connection = DBConnection.getConnection();
		CallableStatement chequeStmt = null;

		try
		{
			chequeStmt = connection.prepareCall("{? = call funcUpdateChequeDetails(?,?,?,?,?,?,?,?,?,?)}");
			chequeStmt.registerOutParameter(1, Types.INTEGER);
			chequeStmt.registerOutParameter(11, Types.VARCHAR);
			chequeStmt.setDouble(2,chequeDetails.getChequeAmount());
			java.util.Date utilDate = chequeDetails.getChequeDate();
			java.sql.Date sqlDate = new java.sql.Date (utilDate.getTime());
			chequeStmt.setDate(3,sqlDate);
			chequeStmt.setString(4,chequeDetails.getChequeNumber());
			chequeStmt.setString(5,chequeDetails.getChequeIssuedTo());
			chequeStmt.setString(6,chequeDetails.getBankName());
			chequeStmt.setString(7,chequeDetails.getBranchName());
			chequeStmt.setString(8,chequeDetails.getUserId());
			chequeStmt.setString(9,chequeNumber);
			chequeStmt.setString(10,chequeDetails.getChequeRemarks());

			chequeStmt.executeQuery();

			int status = chequeStmt.getInt(1);

			if(status == Constants.FUNCTION_FAILURE)
			{
				String errorCode = chequeStmt.getString(11);
				Log.log(Log.ERROR,"IFDAO","chequeDetailsUpdateSuccess","SP returns a 1." +
										" Error code is :" + errorCode);
				chequeStmt.close();
				chequeStmt = null;
			}
			else if(status == Constants.FUNCTION_SUCCESS)
			{
				chequeStmt.close();
				chequeStmt = null;
			}
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
			throw new DatabaseException(exception.getMessage());
		}
		finally
		{
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO,"IFDAO","chequeDetailsUpdateSuccess","Exited");
	}

	/**
	 * BudgetDetails object to the corresponding method in
	 * IFDAO class for the values in the object to be stored in the database.
	 *
	 * How does this method know if the BudgetDetails object is for annual or short term
	 * budget? - Thru the  flag.
	 * Similarly thru the annualOrShortTerm flag encapsulated in the BudgetDetails, the
	 * method knows whether it is annual or short term budget.
	 * The values can be retrieved from the object through the getter methods.
	 * @param budgetDetails
	 * @return boolean
	 */
	public void saveBudgetDetail(BudgetDetails budgetDetails)
		throws DatabaseException {
		Log.log(Log.INFO, "IFDAO", "saveBudgetDetail", "Entered");

		Connection connection = null;
		CallableStatement saveBudgetDetailsStmt = null;
		ArrayList budgetHeadDetails = null;
		ArrayList budgetSubHeadDetails = null;
		BudgetHeadDetails budgetHeadDetail = null;
		BudgetSubHeadDetails budgetSubHeadDetail = null;
		String inflowOrOutflow = null;
		String term = null;
		String budgetHeadTitle = null;
		String budgetSubHeadTitle = null;
		double budgetAmount = 0.0;
		String annualToDate = null;
		String annualFromDate = null;

		String month = null;
		String year = null;
		String modifiedByUser = null;
		java.util.Date modifiedOnDate = null;
		int status = -1;
		String errorCode = null;

		try {
			connection = DBConnection.getConnection(false);

			term = budgetDetails.getAnnualOrShortTerm().toUpperCase();
			annualToDate = budgetDetails.getAnnualToDate();
			annualFromDate = budgetDetails.getAnnualFromDate();
			month = budgetDetails.getMonth();
			year = budgetDetails.getYear();
			modifiedByUser = budgetDetails.getModifiedBy();
			budgetHeadDetails = budgetDetails.getBudgetHeadDetails();

			Log.log(
				Log.DEBUG,
				"IFDAO",
				"saveBudgetDetail",
				"term, ... "
					+ term
					+ " , "
					+ annualFromDate
					+ " , "
					+ annualToDate
					+ " , "
					+ " ,"
					+ month
					+ " ,"
					+ year
					+ ", "
					+ modifiedByUser
					+ " , "
					+ budgetHeadDetails
					+ ", ");

			for (int i = 0; i < budgetHeadDetails.size(); i++) {
				Log.log(
					Log.DEBUG,
					"IFDAO",
					"saveBudgetDetail",
					"For loop.. " + i);

				budgetHeadDetail = (BudgetHeadDetails) budgetHeadDetails.get(i);
				budgetSubHeadDetails =
					budgetHeadDetail.getBudgetSubHeadDetails();
				budgetHeadTitle = budgetHeadDetail.getBudgetHead();

				if (budgetHeadTitle != null)
					budgetHeadTitle = budgetHeadTitle.trim();

				budgetAmount = budgetHeadDetail.getBudgetAmount();

				Log.log(
					Log.DEBUG,
					"IFDAO",
					"saveBudgetDetail",
					"budgetHeadTitle ,budgetAmount  "
						+ budgetHeadTitle
						+ " , "
						+ budgetAmount);

				Log.log(
					Log.DEBUG,
					"IFDAO",
					"saveBudgetDetail",
					"term ,budgetSubHeadDetails  "
						+ term
						+ " , "
						+ budgetSubHeadDetails);

				if (term.equals(TERM_ANNUAL)
					&& (budgetSubHeadDetails == null)) {
					Log.log(Log.DEBUG, "IFDAO", "saveBudgetDetail", "If  ");
					saveBudgetDetailsStmt =
						connection.prepareCall(
							"{? = call funcInsertAnnualBudgetDetail(?,?,?,?,?,?,?,?)}");
					saveBudgetDetailsStmt.registerOutParameter(
						1,
						java.sql.Types.INTEGER);
					saveBudgetDetailsStmt.setString(2, budgetHeadTitle);
					//Budget Head
					saveBudgetDetailsStmt.setString(
						3,
						budgetDetails.getInflowOrOutflow());
					//Budget Sub Head
					saveBudgetDetailsStmt.setString(4, null); //Budget Head
					saveBudgetDetailsStmt.setDouble(5, budgetAmount);
					//Budget Amount
					saveBudgetDetailsStmt.setDate(
						6,
						java.sql.Date.valueOf(
							DateHelper.stringToSQLdate(annualFromDate)));
					//Annual To Date
					saveBudgetDetailsStmt.setDate(
						7,
						java.sql.Date.valueOf(
							DateHelper.stringToSQLdate(annualToDate)));
					//Annual From Date
					saveBudgetDetailsStmt.setString(8, modifiedByUser);
					saveBudgetDetailsStmt.registerOutParameter(
						9,
						java.sql.Types.VARCHAR);

					saveBudgetDetailsStmt.execute();
					status = saveBudgetDetailsStmt.getInt(1);
					errorCode = saveBudgetDetailsStmt.getString(9);

					Log.log(
						Log.DEBUG,
						"IFDAO",
						"saveBudgetDetail",
						"ErrorCode and Error " + status + "," + errorCode);

					if (status == Constants.FUNCTION_FAILURE) {
						Log.log(
							Log.ERROR,
							"IFDAO",
							"saveBudgetDetail",
							errorCode);
						saveBudgetDetailsStmt.close();
						saveBudgetDetailsStmt = null;
						connection.rollback();
						throw new DatabaseException(errorCode);
					}

					saveBudgetDetailsStmt.close();
					saveBudgetDetailsStmt = null;
				} else if (
					term.equals(TERM_ANNUAL)
						&& (budgetSubHeadDetails != null)) {
					Log.log(
						Log.DEBUG,
						"IFDAO",
						"saveBudgetDetail",
						"Else If  ");

					for (int j = 0; j < budgetSubHeadDetails.size(); j++) {
						Log.log(
							Log.DEBUG,
							"IFDAO",
							"saveBudgetDetail",
							"For " + j);

						budgetSubHeadDetail =
							(BudgetSubHeadDetails) budgetSubHeadDetails.get(j);
						budgetSubHeadTitle =
							budgetSubHeadDetail.getSubHeadTitle();
						budgetSubHeadTitle = budgetSubHeadTitle.trim();
						budgetAmount = budgetSubHeadDetail.getBudgetAmount();

						Log.log(
							Log.DEBUG,
							"IFDAO",
							"saveBudgetDetail",
							"Head, Sub-Head title and amount "
								+ budgetHeadTitle
								+ ", "
								+ budgetSubHeadTitle
								+ ","
								+ budgetAmount);

						saveBudgetDetailsStmt =
							connection.prepareCall(
								"{? = call funcInsertAnnualBudgetDetail(?,?,?,?,?,?,?,?)}");
						saveBudgetDetailsStmt.registerOutParameter(
							1,
							java.sql.Types.INTEGER);
						saveBudgetDetailsStmt.setString(2, budgetHeadTitle);
						//Budget Head
						saveBudgetDetailsStmt.setString(
							3,
							budgetDetails.getInflowOrOutflow());
						//Budget Head Type
						saveBudgetDetailsStmt.setString(4, budgetSubHeadTitle);
						//Budget Sub Head
						saveBudgetDetailsStmt.setDouble(5, budgetAmount);
						//Budget Amount
						saveBudgetDetailsStmt.setDate(
							6,
							java.sql.Date.valueOf(
								DateHelper.stringToSQLdate(annualFromDate)));
						//Annual To Date
						saveBudgetDetailsStmt.setDate(
							7,
							java.sql.Date.valueOf(
								DateHelper.stringToSQLdate(annualToDate)));
						//Annual From Date
						saveBudgetDetailsStmt.setString(8, modifiedByUser);
						saveBudgetDetailsStmt.registerOutParameter(
							9,
							java.sql.Types.VARCHAR);

						saveBudgetDetailsStmt.execute();
						status = saveBudgetDetailsStmt.getInt(1);
						errorCode = saveBudgetDetailsStmt.getString(9);
						Log.log(
							Log.DEBUG,
							"IFDAO",
							"saveBudgetDetail",
							"ErrorCode and Error " + status + "," + errorCode);

						if (status == Constants.FUNCTION_FAILURE) {
							saveBudgetDetailsStmt.close();
							saveBudgetDetailsStmt = null;

							connection.rollback();

							Log.log(
								Log.ERROR,
								"IFDAO",
								"saveBudgetDetail",
								errorCode);
							throw new DatabaseException(errorCode);
						}
						saveBudgetDetailsStmt.close();
						saveBudgetDetailsStmt = null;
						Log.log(
							Log.DEBUG,
							"IFDAO",
							"saveBudgetDetail",
							"After execution");
					} // end of for
				} else if (
					term.equals(TERM_SHORT)
						&& (budgetSubHeadDetails == null)) {
					Log.log(
						Log.DEBUG,
						"IFDAO",
						"saveBudgetDetail",
						"Second Else If  ");

					Log.log(
						Log.DEBUG,
						"IFDAO",
						"saveBudgetDetail",
						"Flag " + budgetDetails.getInflowOrOutflow());

					saveBudgetDetailsStmt =
						connection.prepareCall(
							"{? = call funcInsertShortBudgetDetail(?,?,?,?,?,?,?,?)}");
					saveBudgetDetailsStmt.registerOutParameter(
						1,
						java.sql.Types.INTEGER);
					saveBudgetDetailsStmt.setString(2, budgetHeadTitle);
					//Budget Head
					saveBudgetDetailsStmt.setString(
						3,
						budgetDetails.getInflowOrOutflow());
					//Flag
					saveBudgetDetailsStmt.setString(4, null); //Budget Sub Head
					saveBudgetDetailsStmt.setDouble(5, budgetAmount);
					//Budget Amount
					saveBudgetDetailsStmt.setString(6, month);
					saveBudgetDetailsStmt.setString(7, year);
					saveBudgetDetailsStmt.setString(8, modifiedByUser);
					saveBudgetDetailsStmt.registerOutParameter(
						9,
						java.sql.Types.VARCHAR);

					saveBudgetDetailsStmt.execute();
					status = saveBudgetDetailsStmt.getInt(1);
					errorCode = saveBudgetDetailsStmt.getString(9);

					Log.log(
						Log.DEBUG,
						"IFDAO",
						"saveBudgetDetail",
						"ErrorCode and Error " + status + "," + errorCode);

					if (status == Constants.FUNCTION_FAILURE) {
						saveBudgetDetailsStmt.close();
						saveBudgetDetailsStmt = null;

						connection.rollback();

						Log.log(
							Log.ERROR,
							"IFDAO",
							"saveBudgetDetail",
							errorCode);
						throw new DatabaseException(errorCode);
					}
					saveBudgetDetailsStmt.close();
					saveBudgetDetailsStmt = null;
				} else if (
					term.equals(TERM_SHORT)
						&& (budgetSubHeadDetails != null)) {
					Log.log(
						Log.DEBUG,
						"IFDAO",
						"saveBudgetDetail",
						"Third Else If  ");
					for (int j = 0; j < budgetSubHeadDetails.size(); j++) {
						budgetSubHeadDetail =
							(BudgetSubHeadDetails) budgetSubHeadDetails.get(j);
						budgetSubHeadTitle =
							budgetSubHeadDetail.getSubHeadTitle();
						budgetAmount = budgetSubHeadDetail.getBudgetAmount();

						Log.log(
							Log.DEBUG,
							"IFDAO",
							"saveBudgetDetail",
							"Sub-Head Title, amount,flag "
								+ budgetSubHeadTitle
								+ ","
								+ budgetAmount
								+ ", "
								+ budgetDetails.getInflowOrOutflow());

						// Calling the stored procedure to store short term budget
						saveBudgetDetailsStmt =
							connection.prepareCall(
								"{? = call funcInsertShortBudgetDetail(?,?,?,?,?,?,?,?)}");
						saveBudgetDetailsStmt.registerOutParameter(
							1,
							java.sql.Types.INTEGER);
						saveBudgetDetailsStmt.setString(2, budgetHeadTitle);
						// Budget Head
						saveBudgetDetailsStmt.setString(
							3,
							budgetDetails.getInflowOrOutflow());
						// Flag

						saveBudgetDetailsStmt.setString(4, budgetSubHeadTitle);
						// Budget Sub Head
						saveBudgetDetailsStmt.setDouble(5, budgetAmount);
						//Budget Amount
						saveBudgetDetailsStmt.setString(6, month);
						saveBudgetDetailsStmt.setString(7, year);
						saveBudgetDetailsStmt.setString(8, modifiedByUser);
						saveBudgetDetailsStmt.registerOutParameter(
							9,
							java.sql.Types.VARCHAR);

						saveBudgetDetailsStmt.execute();
						status = saveBudgetDetailsStmt.getInt(1);
						errorCode = saveBudgetDetailsStmt.getString(9);

						Log.log(
							Log.DEBUG,
							"IFDAO",
							"saveBudgetDetail",
							"ErrorCode and Error " + status + "," + errorCode);

						if (status == Constants.FUNCTION_FAILURE) {
							saveBudgetDetailsStmt.close();
							saveBudgetDetailsStmt = null;

							connection.rollback();

							Log.log(
								Log.ERROR,
								"IFDAO",
								"saveBudgetDetail",
								errorCode);
							throw new DatabaseException(errorCode);
						}
						saveBudgetDetailsStmt.close();
						saveBudgetDetailsStmt = null;
					} // end of for
				} // end of else
			} // end of for block

			/*
			Log.log(Log.DEBUG,"IFDAO","saveBudgetDetail","Error code and error "+status+" "+errorCode);
			if(status==Constants.FUNCTION_FAILURE){
				saveBudgetDetailsStmt.close();
				saveBudgetDetailsStmt=null;

				connection.rollback();

				Log.log(Log.ERROR,"IFDAO","saveBudgetDetail",errorCode);
				throw new DatabaseException(errorCode);
			} */

			connection.commit();
		} catch (SQLException e) {
			Log.log(Log.ERROR, "IFDAO", "saveBudgetDetail", e.getMessage());
			Log.logException(e);

			try {
				connection.rollback();
			} catch (SQLException e1) {
			}
			throw new DatabaseException("Unable to save BudgetDetails");
		} finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO, "IFDAO", "saveBudgetDetail", "Exited");
	}

	/**
	 * This method saves the ceiling settings into the database.
	 *
	 * The return type to the method is boolean. If there is any exception, this
	 * method may return a false.
	 * @param ceilingDetails
	 * @return boolean
	 */
	/*   public void saveCeilingOnInvestment(HashMap ceilingDetails) throws DatabaseException
	   {
		   CeilingPerInvesteeDetail perInvestee = null;
		   CeilingPerMaturitiesDetail perMaturity = null;
		   CeilingPerInstrumentDetail perInstrument = null;
		   String createdBy = null;

		   // Extracting user details
		   if(ceilingDetails.containsKey("Updated By"))
		   {
				createdBy = (String)ceilingDetails.get("Updated By");
		   }

		   // extracting ceiling as per investee object from the hashmap
		   if(ceilingDetails.containsKey("Per Investee"))
		   {
			   perInvestee = (CeilingPerInvesteeDetail)ceilingDetails.get("Per Investee");
			   saveCeilingPerInvesteeDetail(perInvestee, createdBy);
		   }

		   // extracting ceiling as per maturity object from the hashmap
		   if(ceilingDetails.containsKey("Per Maturity"))
		   {
			   perMaturity = (CeilingPerMaturitiesDetail)ceilingDetails.get("Per Maturity");
			   saveCeilingPerMaturitiesDetail(perMaturity, createdBy);
		   }

		   // extracting ceiling as per instrument object from the hashmap
		   if(ceilingDetails.containsKey("Per Instrument"))
		   {
			   perInstrument = (CeilingPerInstrumentDetail)ceilingDetails.get("Per Instrument");
			   saveCeilingPerInstrumentDetail(perInstrument, createdBy);
		   }

		   // extracting ceiling as per investee worth
		   if(ceilingDetails.containsKey("Per Investee NetWorth"))
		   {

		   }

	   }*/

	/**
		* This method saves the ceiling settings per investee entitle and group.
		*
		* This method returns a boolean. If there is any exception, the method may return
		* a false.
		* @param ceilingPerInvesteeDetails
		* @return boolean
		*/
	/*      public void saveCeilingPerInvesteeDetail(CeilingPerInvesteeDetail ceilingPerInvesteeDetails, String createdBy) throws DatabaseException
		  {
			   // Ceiling details
			   String investeeName = ceilingPerInvesteeDetails.getInvesteeName();
			   String maturityType = null;
			   String instrumentType = null;
			   String investeeWorth = null;
			   String ceilingBasedOnInvesteeStartDate = ceilingPerInvesteeDetails.getCeilingStartDate();
			   String ceilingBasedOnInvesteeEndDate = ceilingPerInvesteeDetails.getCeilingEndDate();
			   String ceilingBasedOnInvesteeLimit = ceilingPerInvesteeDetails.getCeilingAmount();
			   // String createdBy = createdBy;

			   int status = -1;
			   String errorCode = null;

			   try
			   {

					   CallableStatement callableStmt = null;
					   Connection connection = null;
					   callableStmt = connection.prepareCall("{? = call funcInsertCeiling(?,?,?,?,?,?,?,?,?)}");
					   callableStmt.registerOutParameter(1,java.sql.Types.INTEGER);
					   callableStmt.setString(2,investeeName);
					   callableStmt.setString(3,null);
					   callableStmt.setString(4,null);
					   callableStmt.setString(5,null);
					   callableStmt.setDouble(6,Double.parseDouble(ceilingBasedOnInvesteeLimit));
					   callableStmt.setDate(7,java.sql.Date.valueOf(DateHelper.stringToSQLdate(ceilingBasedOnInvesteeStartDate)));
					   callableStmt.setDate(8,java.sql.Date.valueOf(DateHelper.stringToSQLdate(ceilingBasedOnInvesteeEndDate)));
					   callableStmt.setString(9,createdBy);
					   callableStmt.registerOutParameter(10,java.sql.Types.VARCHAR);

					   callableStmt.execute();
					   status =  callableStmt.getInt(1);
					   // //System.out.println("Status of Stored Procedure execution is :" + status );
					   errorCode = callableStmt.getString(10);
					   // //System.out.println("Error Code from Stored Procedure execution is :" + errorCode );
				 }
				 catch(Exception exception)
				 {
					 throw new DatabaseException(exception.getMessage());
				 }
		  }*/

	/**
	 * This method saves the ceiling settings on Maturities basis.
	 *
	 * This method returns a boolean. If there is any exception, the method may return
	 * a false.
	 * @param ceilingPerMaturitiesDetail
	 * @return boolean
	 */
	/*      public void saveCeilingPerMaturitiesDetail(CeilingPerMaturitiesDetail ceilingPerMaturitiesDetail, String createdBy) throws DatabaseException
		  {
				// Ceiling details
				String investeeName = null;
				String maturityType = ceilingPerMaturitiesDetail.getMaturityType();
				String instrumentType = null;
				String investeeWorth = null;
				String ceilingBasedOnMaturityStartDate = ceilingPerMaturitiesDetail.getCeilingStartDate();
				String ceilingBasedOnMaturityEndDate = ceilingPerMaturitiesDetail.getCeilingEndDate();
				String ceilingBasedOnMaturityLimit = ceilingPerMaturitiesDetail.getCeilingAmount();
				// String createdBy = createdBy;
				int status = -1;
				String errorCode = null;

				try
				{

						Connection connection = getDbConnection();
						CallableStatement callableStmt = null;
						callableStmt = connection.prepareCall("{? = call funcInsertCeiling(?,?,?,?,?,?,?,?,?)}");
						callableStmt.registerOutParameter(1,java.sql.Types.INTEGER);
						callableStmt.setString(2,investeeName);
						callableStmt.setString(3,maturityType);
						callableStmt.setString(4,instrumentType);
						callableStmt.setString(5,investeeWorth);
						callableStmt.setDouble(6,Double.parseDouble(ceilingBasedOnMaturityLimit));
						callableStmt.setDate(7,java.sql.Date.valueOf(DateHelper.stringToSQLdate(ceilingBasedOnMaturityStartDate)));
						callableStmt.setDate(8,java.sql.Date.valueOf(DateHelper.stringToSQLdate(ceilingBasedOnMaturityEndDate)));
						callableStmt.setString(9,createdBy);
						callableStmt.registerOutParameter(10,java.sql.Types.VARCHAR);

						callableStmt.execute();
						status =  callableStmt.getInt(1);
						// //System.out.println("Status of Stored Procedure execution is :" + status );
						errorCode = callableStmt.getString(10);
						// //System.out.println("Error Code from Stored Procedure execution is :" + errorCode );
				}
				catch(Exception exception)
				{
					throw new DatabaseException(exception.getMessage());
				}

		  }*/

	/**
	 * This method saves the ceiling settings on Instrument basis.
	 *
	 *
	 * The return type to the method is boolean. If there is any  exception, this
	 * method may return a false.
	 * @param ceilingPerInstrumentDetails
	 * @return boolean
	 */
	/*      public void saveCeilingPerInstrumentDetail(CeilingPerInstrumentDetail ceilingPerInstrumentDetails, String createdBy) throws DatabaseException
		  {
				// Ceiling details
				String investeeName = null;
				String maturityType = null;
				String instrumentType = ceilingPerInstrumentDetails.getInstrumentType();
				String investeeWorth = null;
				String ceilingBasedOnInstrumentStartDate = ceilingPerInstrumentDetails.getCeilingStartDate();
				String ceilingBasedOnInstrumentEndDate = ceilingPerInstrumentDetails.getCeilingEndDate();
				String ceilingBasedOnInstrumentLimit = ceilingPerInstrumentDetails.getCeilingLimit();
				// String createdBy = createdBy;
				int status = -1;
				String errorCode = null;

				try
				{

						Connection connection = getDbConnection();
						CallableStatement callableStmt = connection.prepareCall("{? = call funcInsertCeiling(?,?,?,?,?,?,?,?,?)}");
						callableStmt.registerOutParameter(1,java.sql.Types.INTEGER);
						callableStmt.setString(2,investeeName);
						callableStmt.setString(3,maturityType);
						callableStmt.setString(4,instrumentType);
						callableStmt.setString(5,investeeWorth);
						callableStmt.setDouble(6,Double.parseDouble(ceilingBasedOnInstrumentLimit));
						callableStmt.setDate(7,java.sql.Date.valueOf(DateHelper.stringToSQLdate(ceilingBasedOnInstrumentStartDate)));
						callableStmt.setDate(8,java.sql.Date.valueOf(DateHelper.stringToSQLdate(ceilingBasedOnInstrumentEndDate)));
						callableStmt.setString(9,createdBy);
						callableStmt.registerOutParameter(10,java.sql.Types.VARCHAR);

						callableStmt.execute();
						status =  callableStmt.getInt(1);
						// //System.out.println("Status of Stored Procedure execution is :" + status );
						errorCode = callableStmt.getString(10);
						// //System.out.println("Error Code from Stored Procedure execution is :" + errorCode );
				}
				catch(Exception exception)
				{
					throw new DatabaseException(exception.getMessage());
				}
		  }*/

	/**
	 * This method is used to add a new Holiday entity into the Holiday Master table.
	 *
	 * The return type for this method can be a boolean true or false. If there is an
	 * exception, the method may return a false.
	 * @param holidayDetails
	 * @return boolean
	 */
	public void insertHolidayMaster(Hashtable holidayDetails)
		throws DatabaseException {
		String holidayDate = null;
		String holidayDescription = null;
		CallableStatement callablestmt = null;
		Connection conn = null;
		java.sql.Date sqlDate = null;
		String errorCode = null;
		int status = -1;
		String updatedBy = null;

		if (holidayDetails.containsKey("New Holiday Date")) {
			holidayDate = (String) holidayDetails.get("New Holiday Date");
			sqlDate =
				java.sql.Date.valueOf(DateHelper.stringToSQLdate(holidayDate));
		}

		if (holidayDetails.containsKey("Holiday Description")) {
			holidayDescription =
				((String) holidayDetails.get("Holiday Description")).trim();
		}

		if (holidayDetails.containsKey("Updated By")) {
			updatedBy = (String) holidayDetails.get("Updated By");
		}
		try {
			conn = DBConnection.getConnection();
			callablestmt =
				conn.prepareCall(
					"{? = call packInsUpdHoliday.funcInsertHoliday(?,?,?,?)}");
			callablestmt.registerOutParameter(1, java.sql.Types.INTEGER);
			callablestmt.setDate(2, sqlDate);
			callablestmt.setString(3, holidayDescription);
			callablestmt.setString(4, updatedBy);
			callablestmt.registerOutParameter(5, java.sql.Types.VARCHAR);
			callablestmt.executeQuery();

			status = callablestmt.getInt(1);
			errorCode = callablestmt.getString(5);
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"insertHolidayMaster",
				"Error code and error " + status + " " + errorCode);
			if (status == Constants.FUNCTION_FAILURE) {
				callablestmt.close();
				callablestmt = null;
				Log.log(Log.ERROR, "IFDAO", "insertHolidayMaster", errorCode);
				throw new DatabaseException(errorCode);
			}
			callablestmt.close();
			callablestmt = null;
		} catch (SQLException e) {
			Log.log(Log.ERROR, "IFDAO", "insertHolidayMaster", e.getMessage());
			Log.logException(e);
			throw new DatabaseException("Unable to insert HolidayMaster");
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(Log.INFO, "IFDAO", "insertHolidayMaster", "Exited");
	}

	/**
	 * This method is used to add a new Investee entity into the Investee master table.
	 *
	 * The return type for this method can be a boolean true or false. If there is an
	 * exception, the method may return a false.
	 * @param investeeDetails
	 * @return boolean
	 */
	public void updateInvesteeMaster(
		InvesteeDetail investeeDetails,
		String userId)
		throws DatabaseException {
		Log.log(Log.INFO, "IFDAO", "updateInvesteeMaster", "Entered");

		Connection connection = null;
		CallableStatement statement = null;
		int status = -1;
		String errorCode = null;
		// Sending the values to the database
		try {
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"updateInvesteeMaster",
				"Investee " + investeeDetails.getInvestee());
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"updateInvesteeMaster",
				"Investee Group " + investeeDetails.getInvesteeGroup());
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"updateInvesteeMaster",
				"Tangible assets "
					+ investeeDetails.getInvesteeTangibleAssets());
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"updateInvesteeMaster",
				"net worth " + investeeDetails.getInvesteeNetWorth());

			connection = DBConnection.getConnection();
			statement =
				connection.prepareCall(
					"{? = call funcInsertInvestee(?,?,?,?,?,?,?)}");
			statement.registerOutParameter(1, java.sql.Types.INTEGER);
			if (investeeDetails.getInvestee()!=null && !investeeDetails.getInvestee().equals(""))
			{
				statement.setString(2, investeeDetails.getInvestee());
			}
			else
			{
				statement.setString(2, investeeDetails.getNewInvestee());
			}
			statement.setString(3, investeeDetails.getModInvestee());
			statement.setString(4, investeeDetails.getInvesteeGroup());
			statement.setDouble(5, investeeDetails.getInvesteeTangibleAssets());
			statement.setDouble(6, investeeDetails.getInvesteeNetWorth());
			statement.setString(7, userId);
			statement.registerOutParameter(8, java.sql.Types.VARCHAR);

			statement.execute();

			status = statement.getInt(1);
			errorCode = statement.getString(8);

			Log.log(
				Log.DEBUG,
				"IFDAO",
				"updateInvesteeMaster",
				"Error code and error " + status + " " + errorCode);

			if (status == Constants.FUNCTION_FAILURE) {
				statement.close();
				statement = null;
				Log.log(Log.ERROR, "IFDAO", "updateInvesteeMaster", errorCode);
				throw new DatabaseException(errorCode);
			}
			statement.close();
			statement = null;
		} catch (SQLException e) {
			Log.log(Log.ERROR, "IFDAO", "updateInvesteeMaster", e.getMessage());
			Log.logException(e);
			throw new DatabaseException("Unable to update InvesteeMaster");
		} finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO, "IFDAO", "updateInvesteeMaster", "Exited");
	}

	/**
	 * This method adds a new Maturity type in to Maturity master table.
	 *
	 * The return type for this method can be a boolean true or false. If there is an
	 * exception, the method may return a false.
	 * @param maturityDetails
	 * @return boolean
	 */
	public void updateMaturityMaster(MaturityDetail matDetail, String userId)
		throws DatabaseException {
		String maturityType = null;
		String newMaturityType = null;
		String modMaturityType = null;
		String maturityDescription = null;
		Connection connection = null;
		CallableStatement statement = null;
		int status = -1;
		String errorCode = null;

		try {
			connection = DBConnection.getConnection(false);
			statement =
				connection.prepareCall(
					"{? = call funcInsertMaturity(?,?,?,?,?)}");
			statement.registerOutParameter(1, java.sql.Types.INTEGER);
			maturityType=matDetail.getMaturityType();
			newMaturityType=matDetail.getNewMaturityType();
			modMaturityType=matDetail.getModMaturityType();
			maturityDescription=matDetail.getMaturityDescription();
			if (matDetail.getMaturityType()!=null && !matDetail.getMaturityType().equals(""))
			{
				statement.setString(2, maturityType);
				statement.setString(3, modMaturityType);
				statement.setString(4, maturityDescription);
			}
			else
			{
				statement.setString(2, newMaturityType);
				statement.setString(3, null);
				statement.setString(4, maturityDescription);
			}
			statement.setString(5, userId);
			statement.registerOutParameter(6, java.sql.Types.VARCHAR);

			statement.execute();

			status = statement.getInt(1);
			errorCode = statement.getString(6);
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"updateMaturityMaster",
				"Error code and error " + status + " " + errorCode);
			if (status == Constants.FUNCTION_FAILURE) {
				connection.rollback();
				statement.close();
				statement = null;
				Log.log(Log.ERROR, "IFDAO", "updateMaturityMaster", errorCode);
				throw new DatabaseException(errorCode);
			}
			statement.close();
			statement = null;
			connection.commit();
		} catch (SQLException e) {
			Log.log(Log.ERROR, "IFDAO", "updateMaturityMaster", e.getMessage());
			Log.logException(e);
			throw new DatabaseException("Unable to update MaturityMaster");
		} finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO, "IFDAO", "updateMaturityMaster", "Exited");
	}

	/**
	* This method adds a new Budget Head into the Budget Head master table.
	*
	* The return type for this method can be a boolean true or false. If there is an
	* exception, the method may return a false.
	* @param budgetHeadDetails
	* @return boolean
	*/
	public void updateBudgetHeadsMaster(BudgetHead budgetHead, String userId)
		throws DatabaseException {
		Connection connection = null;
		CallableStatement callableStmt = null;
		int status = -1;
		String errorCode = null;

		try {
			connection = DBConnection.getConnection(false);

			if (budgetHead
				.getBudgetHeadType()
				.equals(InvestmentFundConstants.BOTH)) {
				/*/
				 * If the budget head type is both, insert both as inflow and outflow.
				 * This budget head should be unique for a inflow/outflow combination.
				 */
				callableStmt =
					connection.prepareCall(
						"{? = call funcInsertBudgetHead(?,?,?,?,?)}");
				callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
				if (budgetHead.getBudgetHead()!=null && !budgetHead.getBudgetHead().equals(""))
				{
					callableStmt.setString(2, budgetHead.getBudgetHead());
				}
				else
				{
					callableStmt.setString(2, budgetHead.getNewBudgetHead());
				}
				callableStmt.setString(3, budgetHead.getModBudgetHead());
				callableStmt.setString(4, InvestmentFundConstants.INFLOW);
				callableStmt.setString(5, userId);
				callableStmt.registerOutParameter(6, java.sql.Types.VARCHAR);

				callableStmt.execute();
				status = callableStmt.getInt(1);
				errorCode = callableStmt.getString(6);
				Log.log(
					Log.DEBUG,
					"IFDAO",
					"updateBudgetHeadsMaster",
					"Error code and error " + status + " " + errorCode);
				if (status == Constants.FUNCTION_FAILURE) {
					callableStmt.close();
					callableStmt = null;
					connection.rollback();
					Log.log(
						Log.ERROR,
						"IFDAO",
						"updateBudgetHeadsMaster",
						errorCode);
					throw new DatabaseException(errorCode);
				}

				callableStmt.close();
				callableStmt = null;

				budgetHead.setBudgetHeadType(InvestmentFundConstants.OUTFLOW);
			}
			callableStmt =
				connection.prepareCall(
					"{? = call funcInsertBudgetHead(?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			if (budgetHead.getBudgetHead()!=null && !budgetHead.getBudgetHead().equals(""))
			{
				callableStmt.setString(2, budgetHead.getBudgetHead());
			}
			else
			{
				callableStmt.setString(2, budgetHead.getNewBudgetHead());
			}
			callableStmt.setString(3, budgetHead.getModBudgetHead());
			callableStmt.setString(4, budgetHead.getBudgetHeadType());
			callableStmt.setString(5, userId);
			callableStmt.registerOutParameter(6, java.sql.Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(6);
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"updateBudgetHeadsMaster",
				"Error code and error " + status + " " + errorCode);
			if (status == Constants.FUNCTION_FAILURE) {
				callableStmt.close();
				callableStmt = null;
				connection.rollback();
				Log.log(
					Log.ERROR,
					"IFDAO",
					"updateBudgetHeadsMaster",
					errorCode);
				throw new DatabaseException(errorCode);
			}

			callableStmt.close();
			callableStmt = null;

			connection.commit();
		} catch (SQLException e) {
			Log.log(
				Log.ERROR,
				"IFDAO",
				"updateBudgetHeadsMaster",
				e.getMessage());
			Log.logException(e);
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new DatabaseException("Unable to update BudgetHeadsMaster");
		} finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO, "IFDAO", "updateBudgetHeadsMaster", "Exited");
	}

	/**
	* This method adds a new Budget Sub Head into the Budget Sub Head master table.
	*
	* The return type for this method can be a boolean true or false. If there is an
	* exception, the method may return a false.
	* @param budgetSubHeadDetails
	* @return boolean
	*/
	public void updateBudgetSubHeadMaster(
		BudgetSubHead budgetSubHeadDetails,
		String userId)
		throws DatabaseException {
		String budgetHeadTitle = null;
		String budgetSubHeadTitle = null;

		Connection connection = null;
		CallableStatement callableStmt = null;
		int status = -1;
		String errorCode = null;

		budgetHeadTitle = budgetSubHeadDetails.getBudgeHead();
		//((String)budgetSubHeadDetails.get("Budget Head Type"));
		budgetSubHeadTitle = budgetSubHeadDetails.getBudgetSubHeadTitle();
		//((String)budgetSubHeadDetails.get("Budget Sub Head Title"));

		try {
			connection = DBConnection.getConnection();
			callableStmt =
				connection.prepareCall(
					"{? = call funcInsertBudgetSubHead(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt.setString(2, budgetHeadTitle);
			callableStmt.setString(3, budgetSubHeadDetails.getBudgetHeadType());
			if (budgetSubHeadDetails.getBudgetSubHeadTitle()!=null && !budgetSubHeadDetails.getBudgetSubHeadTitle().equals(""))
			{
				callableStmt.setString(4, budgetSubHeadTitle);
			}
			else
			{
				callableStmt.setString(4, budgetSubHeadDetails.getNewBudgetSubHeadTitle());
			}
			callableStmt.setString(5, budgetSubHeadDetails.getModBudgetSubHeadTitle());

			callableStmt.setString(6, userId);
			callableStmt.registerOutParameter(7, java.sql.Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"updateBudgetSubHeadMaster",
				"Error code and error " + status + " " + errorCode);
			if (status == Constants.FUNCTION_FAILURE) {
				callableStmt.close();
				callableStmt = null;
				Log.log(
					Log.ERROR,
					"IFDAO",
					"updateBudgetSubHeadMaster",
					errorCode);
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			callableStmt = null;
		} catch (SQLException e) {
			Log.log(
				Log.ERROR,
				"IFDAO",
				"updateBudgetSubHeadMaster",
				e.getMessage());
			Log.logException(e);
			throw new DatabaseException("Unable to update BudgetSubHeadMaster");
		} finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO, "IFDAO", "updateBudgetSubHeadMaster", "Exited");
	}

	/**
	* This method adds a new Instrument in to the Instrument master table.
	*
	* The return type for this method can be a boolean true or false. If there is an
	* exception, the method may return a false.
	* @param instrumentDetails
	* @return boolean
	*/
	public void updateInstrumentMaster(
		InstrumentDetail instrumentDetail,
		String userId)
		throws DatabaseException {
		/*
		String instrumentType = null;
		String instrumentDescription = null;
		String instrumentPeriod = null;
		String instrumentRating = null;
		String updatedBy = null;
		*/
		Connection connection = null;
		CallableStatement callableStmt = null;
		int status = -1;
		String errorCode = null;
		/*
				if(instrumentDetails.containsKey("Instrument Type"))
				{
					instrumentType = ((String)instrumentDetails.get("Instrument Type"));
				}
				if(instrumentDetails.containsKey("Instrument Description"))
				{
					instrumentDescription = ((String)instrumentDetails.get("Instrument Description"));
				}
				if(instrumentDetails.containsKey("Instrument Period"))
				{
					instrumentPeriod = ((String)instrumentDetails.get("Instrument Period"));
				}
				if(instrumentDetails.containsKey("Instrument Rating"))
				{
					instrumentRating = ((String)instrumentDetails.get("Instrument Rating"));
				}
				if(instrumentDetails.containsKey("Updated By"))
				{
					updatedBy = ((String)instrumentDetails.get("Updated By"));
				}*/
		try {
			connection = DBConnection.getConnection();
			callableStmt =
				connection.prepareCall(
					"{? = call packGetInsInstrType.funcInsertInstrType(?,?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			if (instrumentDetail.getInstrumentName()!=null && !instrumentDetail.getInstrumentName().equals(""))
			{
				callableStmt.setString(2, instrumentDetail.getInstrumentName());
			}
			else
			{
				callableStmt.setString(2, instrumentDetail.getNewInstrumentName());
			}
			callableStmt.setString(3, instrumentDetail.getModInstrumentName());
			callableStmt.setString(4, instrumentDetail.getInstrumentDescription());
			callableStmt.setString(5, null);
			callableStmt.setString(6, instrumentDetail.getInstrumentType());
			callableStmt.setString(7, userId);
			callableStmt.registerOutParameter(8, java.sql.Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(8);

			Log.log(
				Log.DEBUG,
				"IFDAO",
				"updateInstrumentMaster",
				"Error code and error " + status + " " + errorCode);
			if (status == Constants.FUNCTION_FAILURE) {
				callableStmt.close();
				callableStmt = null;
				Log.log(
					Log.ERROR,
					"IFDAO",
					"updateInstrumentMaster",
					errorCode);
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			callableStmt = null;
		} catch (SQLException e) {
			Log.log(
				Log.ERROR,
				"IFDAO",
				"updateInstrumentMaster",
				e.getMessage());
			Log.logException(e);
			throw new DatabaseException("Unable to update InstrumentMaster");
		} finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO, "IFDAO", "updateInstrumentMaster", "Exited");
	}

	/**
	* This method adds a new Instrument feature into the Instrument Feature master
	* table.
	*
	* The return type for this method can be a boolean true or false. If there is an
	* exception, the method may return a false.
	* @param instrumentFeatureDetails
	* @return boolean
	*/
	public void updateInstrumentFeature(
		InstrumentFeature instrumentFeatureDetails,
		String userId)
		throws DatabaseException {
		//String instrumentType = null;
		//String instrumentFeatureDescription = null;
		//String updatedBy = null;
		Connection connection = null;
		CallableStatement callableStmt = null;
		int status = -1;
		String errorCode = null;

		/*
		if(instrumentFeatureDetails.containsKey("Instrument Type"))
		{
			instrumentType = ((String)instrumentFeatureDetails.get("Instrument Type"));
		}
		if(instrumentFeatureDetails.containsKey("Instrument Feature Description"))
		{
			instrumentFeatureDescription = (String)instrumentFeatureDetails.get("Instrument Feature Description");
		}
		if(instrumentFeatureDetails.containsKey("Updated By"))
		{
			updatedBy = (String)instrumentFeatureDetails.get("Updated By");
		}
		*/
		try {
			connection = DBConnection.getConnection();
			callableStmt =
				connection.prepareCall(
					"{? = call funcInsInstrumentFeature(?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			if (instrumentFeatureDetails.getInstrumentFeatures()!=null && !instrumentFeatureDetails.getInstrumentFeatures().equals(""))
			{
				callableStmt.setString(2, instrumentFeatureDetails.getInstrumentFeatures());
			}
			else
			{
				callableStmt.setString(2, instrumentFeatureDetails.getNewInstrumentFeatures());
			}
			callableStmt.setString(3, instrumentFeatureDetails.getModInstrumentFeatures());
			callableStmt.setString(4, instrumentFeatureDetails.getInstrumentFeatureDescription());
			callableStmt.setString(5, userId);
			callableStmt.registerOutParameter(6, java.sql.Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(6);
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"updateInstrumentFeature",
				"Error code and error " + status + " " + errorCode);
			if (status == Constants.FUNCTION_FAILURE) {
				callableStmt.close();
				callableStmt = null;
				Log.log(
					Log.ERROR,
					"IFDAO",
					"updateInstrumentFeature",
					errorCode);
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			callableStmt = null;
		} catch (SQLException e) {
			Log.log(
				Log.ERROR,
				"IFDAO",
				"updateInstrumentFeature",
				e.getMessage());
			Log.logException(e);
			throw new DatabaseException("Unable to update InstrumentFeature");
		} finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO, "IFDAO", "updateInstrumentFeature", "Exited");
	}

	/**
	* This method adds a new Instrument Scheme in to the Instrument_Scheme master
	* table.
	*
	* The return type for this method can be a boolean true or false. If there is an
	* exception, the method may return a false.
	* @param instrumentSchemeDetails
	* @return boolean
	*/
	public void updateInstrumentScheme(Hashtable instrumentSchemeDetails)
		throws DatabaseException {
		String instrumentType = null;
		String instrumentSchemeType = null;
		String instrumentSchemeDescription = null;
		String newInstSchemeType = null;
		String modInstSchemeType = null;
		String updatedBy = null;
		Connection connection = null;
		CallableStatement callableStmt = null;
		int status = -1;
		String errorCode = null;

		if (instrumentSchemeDetails.containsKey("Instrument")) {
			instrumentType =
				((String) instrumentSchemeDetails.get("Instrument"));
		}
		if (instrumentSchemeDetails.containsKey("Instrument Scheme Type")) {
			instrumentSchemeType =
				((String) instrumentSchemeDetails
					.get("Instrument Scheme Type"));
		}
		if (instrumentSchemeDetails
			.containsKey("Instrument Scheme Description")) {
			instrumentSchemeDescription =
				((String) instrumentSchemeDetails
					.get("Instrument Scheme Description"));
		}
		if (instrumentSchemeDetails.containsKey("Updated By")) {
			updatedBy = ((String) instrumentSchemeDetails.get("Updated By"));
		}
		if (instrumentSchemeDetails.containsKey("New Instrument Scheme Type")) {
			newInstSchemeType = ((String) instrumentSchemeDetails.get("New Instrument Scheme Type"));
		}
		if (instrumentSchemeDetails.containsKey("Mod Instrument Scheme Type")) {
			modInstSchemeType = ((String) instrumentSchemeDetails.get("Mod Instrument Scheme Type"));
		}
		try {
			connection = DBConnection.getConnection();
			callableStmt =
				connection.prepareCall(
					"{? = call funcInsInstrumentScheme(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt.setString(2, instrumentType);
			if (instrumentSchemeType!=null && !instrumentSchemeType.equals(""))
			{
				callableStmt.setString(3, instrumentSchemeType);
			}
			else
			{
				callableStmt.setString(3, newInstSchemeType);
			}
			callableStmt.setString(4, modInstSchemeType);
			callableStmt.setString(5, instrumentSchemeDescription);
			callableStmt.setString(6, updatedBy);
			callableStmt.registerOutParameter(7, java.sql.Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"updateInstrumentScheme",
				"Error code and error " + status + " " + errorCode);
			if (status == Constants.FUNCTION_FAILURE) {
				callableStmt.close();
				callableStmt = null;
				Log.log(
					Log.ERROR,
					"IFDAO",
					"updateInstrumentScheme",
					errorCode);
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			callableStmt = null;
		} catch (SQLException e) {
			Log.log(
				Log.ERROR,
				"IFDAO",
				"updateInstrumentScheme",
				e.getMessage());
			Log.logException(e);
			throw new DatabaseException("Unable to update InstrumentScheme");
		} finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO, "IFDAO", "updateInstrumentScheme", "Exited");
	}

	/**
	* This method adds a new Rating entity into the Rating_master master table.
	*
	* The return type for this method can be a boolean true or false. If there is an
	* exception, the method may return a false.
	* @param ratingDetails
	* @return boolean
	*/

	public void updateRatingMaster(Hashtable ratingDetails)
		throws DatabaseException {
		String rating = null;
		String ratingDescription = null;
		String ratingGivenBy = null;
		String updatedBy = null;
		String newRating=null;
		String modRating=null;
		CallableStatement callableStmt = null;
		Connection connection = null;
		int status = -1;
		String errorCode = null;

		if (ratingDetails.containsKey("Rating")) {
			rating = ((String) ratingDetails.get("Rating")).trim();
		}
		if (ratingDetails.containsKey("Rating Description")) {
			ratingDescription =
				((String) ratingDetails.get("Rating Description")).trim();
		}
/*		if (ratingDetails.containsKey("Rating Given By")) {
			ratingGivenBy =
				((String) ratingDetails.get("Rating Given By")).trim();
		}
*/		if (ratingDetails.containsKey("Updated By")) {
			updatedBy = ((String) ratingDetails.get("Updated By")).trim();
		}
		if (ratingDetails.containsKey("New Rating")) {
			newRating = ((String) ratingDetails.get("New Rating")).trim();
		}
		if (ratingDetails.containsKey("Mod Rating")) {
			modRating = ((String) ratingDetails.get("Mod Rating")).trim();
		}
		try {
			connection = DBConnection.getConnection();
			callableStmt =
				connection.prepareCall(
					"{? = call funcInsertRating(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			if (rating!=null && !rating.equals(""))
			{
				callableStmt.setString(2, rating);
			}
			else
			{
				callableStmt.setString(2, newRating);
			}
			callableStmt.setString(3, modRating);
			callableStmt.setString(4, ratingDescription);
			callableStmt.setString(5, ratingGivenBy);
			callableStmt.setString(6, updatedBy);
			callableStmt.registerOutParameter(7, java.sql.Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"updateRatingMaster",
				"Error code and error " + status + " " + errorCode);
			if (status == Constants.FUNCTION_FAILURE) {
				callableStmt.close();
				callableStmt = null;
				Log.log(Log.ERROR, "IFDAO", "updateRatingMaster", errorCode);
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			callableStmt = null;
		} catch (SQLException e) {
			Log.log(Log.ERROR, "IFDAO", "updateRatingMaster", e.getMessage());
			Log.logException(e);
			throw new DatabaseException("Unable to update RatingMaster");
		} finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO, "IFDAO", "updateRatingMaster", "Exited");
	}

	/**
	* This method adds a new Corpus entity into the corpus_master master table.
	*
	* The return type for this method can be a boolean true or false. If there is an
	* exception, the method may return a false.
	* @param corpusDetails
	* @return boolean
	*/
	public void updateCorpusMaster(Hashtable corpusDetails)
		throws DatabaseException {
		String corpusContributor = null;
		double corpusContribution = 0.0;
		String corpusContrib = null;
		java.util.Date corpusDate = null;
		String modifiedBy = null;
		CallableStatement callableStmt = null;
		Connection connection = null;
		java.sql.Date sqlDate = null;
		int status = -1;
		String errorCode = null;
		String corpusId="";

		if (corpusDetails.containsKey("Corpus Contributor")) {
			corpusContributor =
				((String) corpusDetails.get("Corpus Contributor")).trim();
		}
		if (corpusDetails.containsKey("Corpus Contribution")) {
			corpusContribution = ((Double)corpusDetails.get("Corpus Contribution")).doubleValue();
		}
		if (corpusDetails.containsKey("Corpus Date")) {
			corpusDate = (Date) corpusDetails.get("Corpus Date");
			sqlDate =
				new java.sql.Date(corpusDate.getTime());
		}
		if (corpusDetails.containsKey("Updated By")) {
			modifiedBy = (String) corpusDetails.get("Updated By");
		}
		if (corpusDetails.containsKey("Corpus Id")) {
			corpusId = (String) corpusDetails.get("Corpus Id");
		}
		try {
			connection = DBConnection.getConnection();
			callableStmt =
				connection.prepareCall(
					"{? = call funcInsertCorpus(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt.setString(2, corpusId);
			callableStmt.setString(3, corpusContributor);
			callableStmt.setDouble(4, corpusContribution);
			callableStmt.setDate(5, sqlDate);
			callableStmt.setString(6, modifiedBy);
			callableStmt.registerOutParameter(7, java.sql.Types.VARCHAR);

			// Executing the Stored Procedure
			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"updateCorpusMaster",
				"Error code and error " + status + " " + errorCode);
			if (status == Constants.FUNCTION_FAILURE) {
				callableStmt.close();
				callableStmt = null;
				Log.log(Log.ERROR, "IFDAO", "updateCorpusMaster", errorCode);
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			callableStmt = null;
		} catch (SQLException e) {
			Log.log(Log.ERROR, "IFDAO", "updateCorpusMaster", e.getMessage());
			Log.logException(e);
			throw new DatabaseException("Unable to update CorpusMaster");
		} finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO, "IFDAO", "updateCorpusMaster", "Exited");
	}

	/**
	* This method persists the values in the BuySellDetail object in the database.
	*/
	public String saveBuyOrSellDetails(BuySellDetail buySellDetails, String contextPath)
		throws DatabaseException, MessageException {
		String investeeName = null;
		String instrumentName = null;
		String numberOfUnits = null;
		String worthOfUnits = null;
		String investmentReferenceNumber = null;
		String buyOrSellFlag = buySellDetails.getIsBuyOrSellRequest();
		String requestedBy = buySellDetails.getModifiedBy();
		Connection connection = DBConnection.getConnection();
		CallableStatement callableStmt = null;
		int status = -1;
		String errorCode = null;

		String buySellId = "";
		try {
			connection = DBConnection.getConnection(false);
			if((buyOrSellFlag.trim()).equals(BUY_REQUEST))
			{
			investeeName = buySellDetails.getInvesteeName();
			instrumentName = buySellDetails.getInstrumentName();
			numberOfUnits = buySellDetails.getNoOfUnits();
			worthOfUnits = buySellDetails.getWorthOfUnits();

			callableStmt =
				connection.prepareCall(
					"{? = call funcInsertBuySell(?,?,?,?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt.setString(2, investeeName);
			callableStmt.setString(3, instrumentName);
			callableStmt.setString(4, buyOrSellFlag);
			callableStmt.setInt(5, Integer.parseInt(numberOfUnits));
			callableStmt.setDouble(6, Double.parseDouble(worthOfUnits));
			callableStmt.setString(7, requestedBy);
			//status flag is required or not to be finalized.
			callableStmt.setString(8, "F");
			callableStmt.registerOutParameter(9, java.sql.Types.VARCHAR);
			callableStmt.registerOutParameter(10, java.sql.Types.VARCHAR);

			// Executing the Stored Procedure
			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(10);
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"saveBuyOrSellDetails",
				"Error code and error " + status + " " + errorCode);
			if (status == Constants.FUNCTION_FAILURE) {
				callableStmt.close();
				callableStmt = null;
				Log.log(Log.ERROR, "IFDAO", "saveBuyOrSellDetails", errorCode);
				throw new DatabaseException(errorCode);
			}
			buySellId = callableStmt.getString(9);

			callableStmt.close();
			callableStmt = null;
			}
			else if((buyOrSellFlag.trim()).equals(SELL_REQUEST))
			{

				investeeName = buySellDetails.getInvesteeName();
				instrumentName = buySellDetails.getInstrumentName();
				numberOfUnits = buySellDetails.getNoOfUnits();
				worthOfUnits = buySellDetails.getWorthOfUnits();

				callableStmt =connection.prepareCall("{? = call funcGetInvestmentDetail(?,?,?,?,?,?,?,?,?,?,?)}");
				callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
				Log.log(Log.DEBUG,"IFDAO","saveBuyOrSellDetails","ref no " + buySellDetails.getInvestmentRefNumber());
				String invRefNo = buySellDetails.getInvestmentRefNumber();
				invRefNo = invRefNo.substring(0, invRefNo.indexOf('('));
				Log.log(Log.DEBUG,"IFDAO","saveBuyOrSellDetails","ref no " + invRefNo);
				callableStmt.setString(2, invRefNo);
				callableStmt.registerOutParameter(3, java.sql.Types.INTEGER);
				callableStmt.registerOutParameter(4, java.sql.Types.INTEGER);
				callableStmt.registerOutParameter(5, java.sql.Types.VARCHAR);
				callableStmt.registerOutParameter(6, java.sql.Types.DOUBLE);
				callableStmt.registerOutParameter(7, java.sql.Types.DOUBLE);
				callableStmt.registerOutParameter(8, java.sql.Types.INTEGER);
				callableStmt.registerOutParameter(9, java.sql.Types.INTEGER);
				callableStmt.registerOutParameter(10, java.sql.Types.DOUBLE);
				callableStmt.registerOutParameter(11, java.sql.Types.DATE);
				callableStmt.registerOutParameter(12, java.sql.Types.VARCHAR);

				callableStmt.execute();
				status = callableStmt.getInt(1);
				errorCode = callableStmt.getString(12);
				Log.log(
					Log.DEBUG,
					"IFDAO",
					"saveBuyOrSellDetails",
					"Error code and error from get " + status + " " + errorCode);
				if (status == Constants.FUNCTION_FAILURE) {
					callableStmt.close();
					callableStmt = null;
					Log.log(Log.ERROR, "IFDAO", "saveBuyOrSellDetails", errorCode);
					throw new DatabaseException(errorCode);
				}

				int unitsBought = callableStmt.getInt(3);
				int tenure = callableStmt.getInt(4);
				String tenureType = callableStmt.getString(5);
				double intRate = callableStmt.getDouble(6);
				double cost = callableStmt.getDouble(7);
				int compFreq = callableStmt.getInt(8);
				int unitsSold = callableStmt.getInt(9);
				double matAmt = callableStmt.getDouble(10);
				Date matDate = callableStmt.getDate(11);

				callableStmt.close();
				callableStmt = null;

				int unitsAvailable = unitsBought-unitsSold;
				int currUnitsSold = Integer.parseInt(numberOfUnits);
				if (currUnitsSold>unitsAvailable)
				{
					throw new MessageException("Number of Units Sold is more than Units available.");
				}
				int balDays=0;

				if (unitsAvailable-currUnitsSold>0 &&
					!instrumentName.equalsIgnoreCase("FIXED DEPOSIT"))
				{
					cost = (cost*(unitsAvailable-currUnitsSold))/unitsAvailable;

					if (!instrumentName.equalsIgnoreCase("MUTUAL FUNDS"))
					{
						if (tenureType.equalsIgnoreCase("D"))
						{
							balDays=tenure-(tenure/365);
							tenure=tenure/365;
						}
						else if (tenureType.equalsIgnoreCase("M"))
						{
							balDays=tenure-(tenure/12);
							tenure=tenure/12;
						}

						if (compFreq==4)
						{
							intRate=intRate/4;
							tenure=tenure*4;
						}

						double amount = cost * (Math.pow((1+(intRate/100)), Double.parseDouble(tenure+"")));
						double intAmt = (amount * (intRate/100) * balDays)/365;

						matAmt=amount + intAmt;

					}
				}
				else
				{
					matDate = new java.util.Date();
					matAmt = Double.parseDouble(worthOfUnits);
				}

				callableStmt =
					connection.prepareCall(
						"{? = call funcInsertBuySell(?,?,?,?,?,?,?,?,?)}");
				callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
				callableStmt.setString(2, investeeName);
				callableStmt.setString(3, instrumentName);
				callableStmt.setString(4, buyOrSellFlag);
				callableStmt.setInt(5, Integer.parseInt(numberOfUnits));
				callableStmt.setDouble(6, Double.parseDouble(worthOfUnits));
				callableStmt.setString(7, requestedBy);
				//status flag is required or not to be finalized.
				callableStmt.setString(8, "F");
				callableStmt.registerOutParameter(9, java.sql.Types.VARCHAR);
				callableStmt.registerOutParameter(10, java.sql.Types.VARCHAR);

				// Executing the Stored Procedure
				callableStmt.execute();
				status = callableStmt.getInt(1);
				errorCode = callableStmt.getString(10);
				Log.log(
					Log.DEBUG,
					"IFDAO",
					"saveBuyOrSellDetails",
					"Error code and error " + status + " " + errorCode);
				if (status == Constants.FUNCTION_FAILURE) {
					callableStmt.close();
					callableStmt = null;
					Log.log(Log.ERROR, "IFDAO", "saveBuyOrSellDetails", errorCode);
					throw new DatabaseException(errorCode);
				}
				buySellId = callableStmt.getString(9);

				callableStmt.close();
				callableStmt = null;

				callableStmt =connection.prepareCall("{? = call funcUpdateInvestmentDetail(?,?,?,?,?,?,?)}");
				callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
				callableStmt.setString(2, invRefNo);
				callableStmt.setDouble(3, cost);
				callableStmt.setInt(4, unitsSold+currUnitsSold);
				callableStmt.setDouble(5, matAmt);
				if (matDate==null)
				{
					callableStmt.setNull(6, java.sql.Types.DATE);
				}
				else
				{
					callableStmt.setDate(6, new java.sql.Date(matDate.getTime()));
				}
				callableStmt.setString(7, requestedBy);
				callableStmt.registerOutParameter(8, java.sql.Types.VARCHAR);

				callableStmt.execute();
				status = callableStmt.getInt(1);
				errorCode = callableStmt.getString(8);
				Log.log(
					Log.DEBUG,
					"IFDAO",
					"saveBuyOrSellDetails",
					"Error code and error from update " + status + " " + errorCode);
				if (status == Constants.FUNCTION_FAILURE) {
					callableStmt.close();
					callableStmt = null;
					Log.log(Log.ERROR, "IFDAO", "saveBuyOrSellDetails", errorCode);
					throw new DatabaseException(errorCode);
				}

				callableStmt =connection.prepareCall("{? = call funcInsertSellDetail(?,?,?,?,?,?)}");
				callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
				callableStmt.setString(2, invRefNo);
				callableStmt.setString(3, buySellId);
				callableStmt.setDouble(4, Double.parseDouble(worthOfUnits));
				callableStmt.setDate(5, new java.sql.Date((new java.util.Date()).getTime()));
				callableStmt.setString(6, requestedBy);
				callableStmt.registerOutParameter(7, java.sql.Types.VARCHAR);
				callableStmt.execute();
				status = callableStmt.getInt(1);
				errorCode = callableStmt.getString(7);
				Log.log(
					Log.DEBUG,
					"IFDAO",
					"saveBuyOrSellDetails",
					"Error code and error from save sell detail " + status + " " + errorCode);
				if (status == Constants.FUNCTION_FAILURE) {
					callableStmt.close();
					callableStmt = null;
					Log.log(Log.ERROR, "IFDAO", "saveBuyOrSellDetails", errorCode);
					throw new DatabaseException(errorCode);
				}
				callableStmt.close();
				callableStmt = null;
			}

			Properties accCodes=new Properties();
			Log.log(Log.DEBUG,"RPAction","getPaymentsMade","path " + contextPath);
			File tempFile = new File(contextPath+"\\WEB-INF\\classes",RpConstants.AC_CODE_FILE_NAME);
			Log.log(Log.DEBUG,"RPAction","getPaymentsMade","file opened ");
			File accCodeFile = new File(tempFile.getAbsolutePath());
			try
			{
				FileInputStream fin = new FileInputStream(accCodeFile);
				accCodes.load(fin);
			}
			catch(FileNotFoundException fe)
			{
				throw new MessageException("Could not load Account Codes.");
			}
			catch(IOException ie)
			{
				throw new MessageException("Could not load Account Codes.");
			}
			Log.log(Log.DEBUG,"RPAction","getPaymentsMade","props loaded ");

			 ArrayList vouchers = new ArrayList();
		   VoucherDetail voucherDetail = new VoucherDetail();
/*		   if (buyOrSellFlag.equalsIgnoreCase(BUY_REQUEST))
		   {
			voucherDetail.setBankGLCode(accCodes.getProperty(RpConstants.BANK_AC));
			voucherDetail.setBankGLName("");
			voucherDetail.setDeptCode(RpConstants.RP_CGTSI);
			voucherDetail.setAmount(0-Double.parseDouble(worthOfUnits));
			voucherDetail.setVoucherType(RpConstants.PAYMENT_VOUCHER);

			Voucher voucher = new Voucher();
			voucher.setAcCode(accCodes.getProperty(RpConstants.INVESTMENT_AC));
			voucher.setPaidTo("CGTSI");
			voucher.setDebitOrCredit("D");
			voucher.setAmountInRs(""+worthOfUnits);
			vouchers.add(voucher);
			voucher=null;
			voucherDetail.setNarration("Investment Ref No. - "+ buySellId);
			voucherDetail.setVouchers(vouchers);
		   }
		   else 
		   */
		   if (buyOrSellFlag.equalsIgnoreCase(SELL_REQUEST))
		   {
			voucherDetail.setBankGLCode(accCodes.getProperty(RpConstants.BANK_AC));
			voucherDetail.setBankGLName("");
			voucherDetail.setDeptCode(RpConstants.RP_CGTSI);
			voucherDetail.setAmount(Double.parseDouble(worthOfUnits));
			voucherDetail.setVoucherType(RpConstants.RECEIPT_VOUCHER);

			Voucher voucher = new Voucher();
			voucher.setAcCode(accCodes.getProperty(RpConstants.INVESTMENT_AC));
			voucher.setPaidTo("CGTSI");
			voucher.setDebitOrCredit("C");
			voucher.setAmountInRs("-"+worthOfUnits);
			vouchers.add(voucher);
			voucher=null;
			voucherDetail.setNarration("Investment Ref No. - "+ buySellId);
			voucherDetail.setVouchers(vouchers);
			
			RpDAO rpDAO = new RpDAO();

			rpDAO.insertVoucherDetails(voucherDetail, requestedBy, connection);
			vouchers.clear();
			
		   }

			connection.commit();

		} catch (SQLException e) {
			Log.log(Log.ERROR, "IFDAO", "saveBuyOrSellDetails", e.getMessage());
			Log.logException(e);
			try
			{
				connection.rollback();
			}
			catch(SQLException sqle){
				throw new DatabaseException("Unable to rollback BuyOrSellDetails");
				}
			throw new DatabaseException("Unable to save BuyOrSellDetails");
		}
		catch (DatabaseException dbe)
		{
			try
			{
				connection.rollback();
			}
			catch(SQLException sqle){
				throw new DatabaseException("Unable to rollback BuyOrSellDetails");
				}
			throw dbe;
		}
		 finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO, "IFDAO", "saveBuyOrSellDetails", "Exited");

		return buySellId;
	}

	/**
	* This method persists the values in the Fund Transfer Detail in the database.
	*/
	public void saveFundTransferDetail(FundTransferDetail fundTransferDetails)
		throws DatabaseException {

	}

	/**
	* This method persists the values in the TDSDetail object in the database.
	*/
	public void saveTDSDetail(TDSDetail tdsDetails) throws DatabaseException {
		String receiptNumber = null;
		double tdsAmount = 0;
		Date tdsReminderDate = null;
		Date tdsDate = null;
		String certificateFlag = null;
		String modifiedBy = null;
		CallableStatement callableStmt = null;
		Connection connection = null;
		java.sql.Date sqlDate = null;
		int status = -1;
		String errorCode = null;

		if (tdsDetails != null) {
			receiptNumber = tdsDetails.getInvestmentRefNumber().substring(0, tdsDetails.getInvestmentRefNumber().indexOf('('));
			tdsAmount = tdsDetails.getTDSAmount();
			tdsReminderDate = tdsDetails.getReminderDate();
			certificateFlag = tdsDetails.getTDSCertificateReceivedORNot();
			tdsDate = tdsDetails.getTDSDeductedDate();
			modifiedBy = tdsDetails.getModifiedBy();
		}
		try {
			connection = DBConnection.getConnection();
			callableStmt =
				connection.prepareCall(
					"{? = call funcInsertTDS(?,?,?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt.setString(2, tdsDetails.getTdsID());
			callableStmt.setString(3, receiptNumber);
			callableStmt.setDouble(4, tdsAmount);
			if (tdsReminderDate!=null && !tdsReminderDate.toString().equals(""))
			{
				callableStmt.setDate(
					5,
					new java.sql.Date(tdsReminderDate.getTime()));
			}
			else
			{
				callableStmt.setNull(
					5,
					java.sql.Types.DATE);
			}
			callableStmt.setString(6, certificateFlag);
			callableStmt.setDate(7, new java.sql.Date(tdsDate.getTime()));
			callableStmt.setString(8, modifiedBy);
			callableStmt.registerOutParameter(9, java.sql.Types.VARCHAR);

			// Executing the Stored Procedure
			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(9);
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"saveTDSDetail",
				"Error code and error " + status + " " + errorCode);
			if (status == Constants.FUNCTION_FAILURE) {
				callableStmt.close();
				callableStmt = null;
				Log.log(Log.ERROR, "IFDAO", "saveTDSDetail", errorCode);
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			callableStmt = null;
		} catch (SQLException e) {
			Log.log(Log.ERROR, "IFDAO", "saveTDSDetail", e.getMessage());
			Log.logException(e);
			throw new DatabaseException("Unable to saveTDSDetail");
		} finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO, "IFDAO", "saveTDSDetail", "Exited");
	}

	/**
	* The parameter to the method will be a vector of Forecasting Details. This method will
	* extract each forecasting detail from the vector and save it to the database.
	*/
	public void saveForecastingDetail(ForecastDetails forecastDetails)
		throws DatabaseException {
		Connection connection = null;
		CallableStatement saveForecastDetailsStmt = null;
		ArrayList forecastHeadDetails = null;
		ArrayList forecastSubHeadDetails = null;
		ForecastHeadDetails forecastHeadDetail = null;
		ForecastSubHeadDetails forecastSubHeadDetail = null;
		String inflowOrOutflow = null;
		String forecastHeadTitle = null;
		String forecastSubHeadTitle = null;
		double forecastAmount = 0.0;
		String endDate = null;
		String startDate = null;
		String modifiedByUser = null;

		java.util.Date modifiedOnDate = null;
		int status = -1;
		String errorCode = null;

		try {
			connection = DBConnection.getConnection();
			endDate = forecastDetails.getEndDate();
			startDate = forecastDetails.getStartDate();
			modifiedByUser = forecastDetails.getModifiedBy();
			forecastHeadDetails = forecastDetails.getForecastHeadDetails();
			for (int i = 0; i < forecastHeadDetails.size(); i++) {
				forecastHeadDetail =
					(ForecastHeadDetails) forecastHeadDetails.get(i);
				forecastSubHeadDetails =
					forecastHeadDetail.getForecastSubHeadDetails();
				forecastHeadTitle = forecastHeadDetail.getForecastHead();
				if (forecastHeadTitle != null)
					forecastHeadTitle = forecastHeadTitle.trim();
				forecastAmount = forecastHeadDetail.getForecastAmount();
				if (forecastSubHeadDetails == null) {
					saveForecastDetailsStmt =
						connection.prepareCall(
							"{? = call funcInsertForecastDetail(?,?,?,?,?,?,?)}");
					saveForecastDetailsStmt.registerOutParameter(
						1,
						java.sql.Types.INTEGER);
					saveForecastDetailsStmt.setString(2, forecastHeadTitle);
					saveForecastDetailsStmt.setString(3, null);
					saveForecastDetailsStmt.setDouble(4, forecastAmount);
					saveForecastDetailsStmt.setDate(
						5,
						java.sql.Date.valueOf(
							DateHelper.stringToSQLdate(startDate)));
					saveForecastDetailsStmt.setDate(
						6,
						java.sql.Date.valueOf(
							DateHelper.stringToSQLdate(endDate)));
					saveForecastDetailsStmt.setString(7, modifiedByUser);
					saveForecastDetailsStmt.registerOutParameter(
						8,
						java.sql.Types.VARCHAR);

					saveForecastDetailsStmt.execute();
					status = saveForecastDetailsStmt.getInt(1);
					errorCode = saveForecastDetailsStmt.getString(8);
				} else {
					for (int j = 0; j < forecastSubHeadDetails.size(); j++) {
						forecastSubHeadDetail =
							(
								ForecastSubHeadDetails) forecastSubHeadDetails
									.get(
								i);
						forecastSubHeadTitle =
							forecastSubHeadDetail.getSubHeadTitle();
						forecastSubHeadTitle = forecastSubHeadTitle.trim();
						forecastAmount =
							forecastSubHeadDetail.getForecastAmount();

						saveForecastDetailsStmt =
							connection.prepareCall(
								"{? = call funcInsertForecastDetail(?,?,?,?,?,?,?)}");
						saveForecastDetailsStmt.registerOutParameter(
							1,
							java.sql.Types.INTEGER);
						saveForecastDetailsStmt.setString(2, forecastHeadTitle);
						saveForecastDetailsStmt.setString(
							3,
							forecastSubHeadTitle);
						saveForecastDetailsStmt.setDouble(4, forecastAmount);
						saveForecastDetailsStmt.setDate(
							5,
							java.sql.Date.valueOf(
								DateHelper.stringToSQLdate(startDate)));
						saveForecastDetailsStmt.setDate(
							6,
							java.sql.Date.valueOf(
								DateHelper.stringToSQLdate(endDate)));
						saveForecastDetailsStmt.setString(7, modifiedByUser);
						saveForecastDetailsStmt.registerOutParameter(
							8,
							java.sql.Types.VARCHAR);

						saveForecastDetailsStmt.execute();
						status = saveForecastDetailsStmt.getInt(1);
						errorCode = saveForecastDetailsStmt.getString(8);
						Log.log(
							Log.DEBUG,
							"IFDAO",
							"saveForecastingDetail",
							"Error code and error " + status + " " + errorCode);
						if (status == Constants.FUNCTION_FAILURE) {
							saveForecastDetailsStmt.close();
							saveForecastDetailsStmt = null;
							Log.log(
								Log.ERROR,
								"IFDAO",
								"saveForecastingDetail",
								errorCode);
							throw new DatabaseException(errorCode);
						}
						saveForecastDetailsStmt.close();
						saveForecastDetailsStmt = null;
					} // end of for
				}
			} // end of for block
		} catch (SQLException e) {
			Log.log(
				Log.ERROR,
				"IFDAO",
				"saveForecastingDetail",
				e.getMessage());
			Log.logException(e);
			throw new DatabaseException("Unable to save ForecastingDetail");
		} finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO, "IFDAO", "saveForecastingDetail", "Exited");
	}

	/**
	* This method persists the values in the ProjectExpectedClaimDetail object in the database.
	*/
	public void saveProjectExpectedClaimDetail(ProjectExpectedClaimDetail
				projectedExpectedClaimDetails,String userId)
		throws DatabaseException
	{

		Log.log(Log.INFO, "IFDAO", "saveProjectExpectedClaimDetail", "Entered");


		Connection connection = DBConnection.getConnection();

		try
		{
			CallableStatement callable =
					connection.prepareCall(
					"{?= call funcInsertClmProj(?,?,?,?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);

			if(projectedExpectedClaimDetails.getStartDate()!=null)
			{
				callable.setDate(2,new java.sql.Date(projectedExpectedClaimDetails.getStartDate().getTime()));
			}
			else
			{
				callable.setDate(2,null);
			}
			callable.setDate(3,new java.sql.Date(projectedExpectedClaimDetails.getEndDate().getTime()));

			callable.setDouble(4, projectedExpectedClaimDetails.getProjectedClaimAmount());

			callable.setString(5,userId);

			callable.registerOutParameter(6, Types.VARCHAR);

			callable.execute();

			int errorCode = callable.getInt(1);
			String error = callable.getString(6);

			Log.log(Log.DEBUG,"IFDAO","saveProjectExpectedClaimDetail",
				"errorCode and error " + errorCode + "," + error);

			if (errorCode == Constants.FUNCTION_FAILURE) {
				Log.log(Log.ERROR, "IFDAO", "saveProjectExpectedClaimDetail", error);
				callable.close();
				callable = null;

				throw new DatabaseException(error);
			}

			callable.close();
			callable = null;
		}
		catch (SQLException e)
		{
			Log.log(Log.ERROR, "IFDAO", "saveProjectExpectedClaimDetail", e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Unable to get save expected claim projections.");
		}
		finally
		{
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO, "IFDAO", "saveProjectExpectedClaimDetail", "Exited");
	}


	/**
	* The parameter to this method is an object of FDDetail.
	* This method stores the values from the input value object to the
	* INVESTMENT_DETAIL table in the database.
	*
	* @param FDDetail - Value Object for FD Details
	* @throws DatabaseException
	*/
	public void saveInvestmentDetail(FDDetail fdDetail)
		throws DatabaseException {
		Connection connection = null;
		CallableStatement saveInvStmt = null;
		int saveInvStatus = 0;
		String saveInvErr = "";
		java.sql.Date maturityDate;
		double maturityAmount = 0.0;

		try {
			connection = DBConnection.getConnection(); //34
			saveInvStmt =
				connection.prepareCall(
					"{?=call funcInsertInvestmentDetail(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			saveInvStmt.setString(2, fdDetail.getInvesteeName());
			//Investee Name
			saveInvStmt.setString(3, fdDetail.getInstrumentName());
			//Instrument name
			saveInvStmt.setString(4, fdDetail.getMaturityName()); //Maturity Name
			saveInvStmt.setString(5, null); //Instrument Scheme
			saveInvStmt.setString(6, "Fixed Deposit"); //Investment Name
			saveInvStmt.setDate(7,
				new java.sql.Date(fdDetail.getDateOfDeposit().getTime()));
			saveInvStmt.setInt(8, fdDetail.getTenure()); //Tenure
			saveInvStmt.setString(9, fdDetail.getTenureType()); //Tenure Type
			saveInvStmt.setDouble(10, fdDetail.getInterestRate());
			//Interest Rate
			saveInvStmt.setNull(11, java.sql.Types.DOUBLE);
			//Face Value **** To be chaged to null value.
			saveInvStmt.setDouble(12, fdDetail.getPrincipalAmount());
			//Cost of Purchase
			saveInvStmt.setInt(13, fdDetail.getCompoundingFrequency());
			//Compunding Frequency
			saveInvStmt.setString(14, fdDetail.getReceiptNumber());
			//Receipt Number
			saveInvStmt.setNull(15, java.sql.Types.VARCHAR); //Folio Number
			saveInvStmt.setInt(16, 1); //Units Bought is considered 1 for fixed deposits
			saveInvStmt.setNull(17, java.sql.Types.VARCHAR); //Call Put Option
			saveInvStmt.setNull(18, java.sql.Types.INTEGER);
			//Call Put Duration
			saveInvStmt.setDouble(19, fdDetail.getMaturityAmount());
			//Maturity Amount
			saveInvStmt.setDate(
				20,
				new java.sql.Date(fdDetail.getMaturityDate().getTime()));
			//Maturity Date

			saveInvStmt.setNull(21, java.sql.Types.DOUBLE); //Premium Discount
			saveInvStmt.setNull(22, java.sql.Types.INTEGER); //Units Sold
			saveInvStmt.setNull(23, java.sql.Types.DATE); //Selling Date
			saveInvStmt.setNull(24, java.sql.Types.VARCHAR); //Mark To Market
			saveInvStmt.setNull(25, java.sql.Types.DOUBLE); //Entry Load
			saveInvStmt.setNull(26, java.sql.Types.DOUBLE); //Exit Load
			saveInvStmt.setNull(27, java.sql.Types.VARCHAR); //Open Close
			saveInvStmt.setNull(28, java.sql.Types.VARCHAR); //ISIN Number
			saveInvStmt.setNull(29, java.sql.Types.DOUBLE); //Par Value
			saveInvStmt.setNull(30, java.sql.Types.DOUBLE); //Purchase Date NAV
			saveInvStmt.setNull(31, java.sql.Types.DOUBLE); //Current Date NAV
			saveInvStmt.setNull(32, java.sql.Types.VARCHAR); //refence no
			saveInvStmt.setString(33, fdDetail.getModifiedBy()); //User ID
			saveInvStmt.setNull(34, java.sql.Types.VARCHAR);
			//pInInstrumentFeature
			saveInvStmt.setString(35, fdDetail.getInvestmentReferenceNumber());
			//pInBuySellId
			saveInvStmt.setString(36,fdDetail.getRating());
			saveInvStmt.setString(37,fdDetail.getInstrumentCategory());
			saveInvStmt.setNull(38,java.sql.Types.DOUBLE);
			saveInvStmt.setString(39,fdDetail.getAgency());
			saveInvStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			saveInvStmt.registerOutParameter(40, java.sql.Types.VARCHAR);
			//Error Description
			saveInvStmt.execute();

			int status = saveInvStmt.getInt(1);
			String errorCode = saveInvStmt.getString(40);
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"saveInvestmentDetail",
				"Error code and error " + status + " " + errorCode);
			if (status == Constants.FUNCTION_FAILURE) {
				saveInvStmt.close();
				saveInvStmt = null;
				Log.log(Log.ERROR, "IFDAO", "saveInvestmentDetail", errorCode);
				throw new DatabaseException(errorCode);
			}
			saveInvStmt.close();
			saveInvStmt = null;
		} catch (SQLException e) {
			Log.log(Log.ERROR, "IFDAO", "saveInvestmentDetail", e.getMessage());
			Log.logException(e);
			throw new DatabaseException("Unable to save InvestmentDetail");
		} finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO, "IFDAO", "saveInvestmentDetail", "Exited");
	}

	/**
	* The parameter to this method is an object of CommercialPaperDetail.
	* This method stores the values from the input value object to the
	* INVESTMENT_DETAIL table in the database.
	*
	* @param CommercialPaperDetail - Value Object for Commercial Paper Details
	* @throws DatabaseException
	*/
	public void saveInvestmentDetail(CommercialPaperDetail commercialPaperDetail)
		throws DatabaseException {
		Connection connection = null;
		CallableStatement saveInvStmt = null;
		int saveInvStatus = 0;
		String saveInvErr = "";
		java.sql.Date maturityDate;
		double maturityAmount = 0.0;

		try {
			connection = DBConnection.getConnection();
			saveInvStmt =
				connection.prepareCall(
					"{?=call funcInsertInvestmentDetail(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			saveInvStmt.setString(2, commercialPaperDetail.getInvesteeName());
			//Investee Name
			saveInvStmt.setString(3, commercialPaperDetail.getInstrumentName());
			//Instrument name
			saveInvStmt.setString(4, commercialPaperDetail.getMaturityName());
			//Maturity Name
			saveInvStmt.setString(5, null); //Instrument Scheme
			saveInvStmt.setString(6, commercialPaperDetail.getNameOfCompany());
			//Investment Name
			saveInvStmt.setDate(
				7,
				new java.sql.Date(
					commercialPaperDetail.getDateOfInvestment().getTime()));
			saveInvStmt.setInt(8, commercialPaperDetail.getTenure()); //Tenure
			saveInvStmt.setString(9, commercialPaperDetail.getTenureType());
			//Tenure Type
			saveInvStmt.setDouble(10, commercialPaperDetail.getCouponRate());
			//Interest Rate
			saveInvStmt.setDouble(11, commercialPaperDetail.getFaceValue());
			//Face Value
			saveInvStmt.setDouble(
				12,
				commercialPaperDetail.getCostOfPurchase());
			//Cost of Purchase
			saveInvStmt.setNull(13, java.sql.Types.INTEGER);
			//Compunding Frequency **** to be changed to null
			saveInvStmt.setString(
				14,
				commercialPaperDetail.getCommercialPaperNumber());
			//Receipt Number
			saveInvStmt.setNull(15, java.sql.Types.VARCHAR); //Folio Number
			saveInvStmt.setInt(
				16,
				commercialPaperDetail.getNoOfCommercialPapers());
			//Units Bought
			saveInvStmt.setString(
				17,
				commercialPaperDetail.getCallOrPutOption());
			//Call Put Option
			saveInvStmt.setInt(
				18,
				commercialPaperDetail.getCallOrPutDuration());
			//Call Put Duration
			saveInvStmt.setDouble(
				19,
				commercialPaperDetail.getMaturityAmount());
			//Maturity Amount
			saveInvStmt.setDate(
				20,
				new java.sql.Date(
					commercialPaperDetail.getMaturityDate().getTime()));
			//Maturity Date

			saveInvStmt.setDouble(21, commercialPaperDetail.getCostOfPurchase()-commercialPaperDetail.getFaceValue()); //Premium Discount
			saveInvStmt.setNull(22, java.sql.Types.INTEGER); //Units Sold
			saveInvStmt.setNull(23, java.sql.Types.DATE); //Selling Date
			saveInvStmt.setNull(24, java.sql.Types.VARCHAR); //Mark To Market
			saveInvStmt.setNull(25, java.sql.Types.DOUBLE); //Entry Load
			saveInvStmt.setNull(26, java.sql.Types.DOUBLE); //Exit Load
			saveInvStmt.setNull(27, java.sql.Types.VARCHAR); //Open Close
			saveInvStmt.setNull(28, java.sql.Types.VARCHAR); //ISIN Number
			saveInvStmt.setNull(29, java.sql.Types.DOUBLE); //Par Value
			saveInvStmt.setNull(30, java.sql.Types.DOUBLE); //Purchase Date NAV
			saveInvStmt.setNull(31, java.sql.Types.DOUBLE); //Current Date NAV
			saveInvStmt.setNull(32, java.sql.Types.VARCHAR); //refence no
			saveInvStmt.setString(33, commercialPaperDetail.getModifiedBy());
			//User ID
			saveInvStmt.setNull(34, java.sql.Types.VARCHAR);
			//pInInstrumentFeature
			saveInvStmt.setString(
				35,
				commercialPaperDetail.getInvestmentReferenceNumber());
			saveInvStmt.setString(36,commercialPaperDetail.getRating());
			saveInvStmt.setString(37,commercialPaperDetail.getInstrumentCategory());
			saveInvStmt.setDouble(38,commercialPaperDetail.getYtmValue());
			saveInvStmt.setString(39,commercialPaperDetail.getAgency());
			//pInBuySellId

			// 0 - successfully inserted. 1 - error in inserting
			saveInvStmt.registerOutParameter(1, java.sql.Types.INTEGER); 
			saveInvStmt.registerOutParameter(40, java.sql.Types.VARCHAR); 

			//Error Description
			saveInvStmt.execute();

			int status = saveInvStmt.getInt(1);
			String errorCode = saveInvStmt.getString(40);
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"saveInvestmentDetail",
				"Error code and error " + status + " " + errorCode);
			if (status == Constants.FUNCTION_FAILURE) {
				saveInvStmt.close();
				saveInvStmt = null;
				Log.log(Log.ERROR, "IFDAO", "saveInvestmentDetail", errorCode);
				throw new DatabaseException(errorCode);
			}
			saveInvStmt.close();
			saveInvStmt = null;
		} catch (SQLException e) {
			Log.log(Log.ERROR, "IFDAO", "saveInvestmentDetail", e.getMessage());
			Log.logException(e);
			throw new DatabaseException("Unable to save InvestmentDetail");
		} finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO, "IFDAO", "saveInvestmentDetail", "Exited");
	}

	/**
	* The parameter to this method is an object of MutualFundDetail.
	* This method stores the values from the input value object to the
	* INVESTMENT_DETAIL table in the database.
	*
	* @param MutualFundDetail - Value Object for Mutual Fund Details
	* @throws DatabaseException
	*/
	public void saveInvestmentDetail(MutualFundDetail mutualFundDetail)
		throws DatabaseException {
		Connection connection = null;
		CallableStatement saveInvStmt = null;
		int saveInvStatus = 0;
		String saveInvErr = "";
		java.sql.Date maturityDate;
		double maturityAmount = 0.0;

		try {
			connection = DBConnection.getConnection();
			saveInvStmt =
				connection.prepareCall(
					"{?=call funcInsertInvestmentDetail(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			saveInvStmt.setString(2, mutualFundDetail.getInvesteeName()); 
			//Investee Name
			saveInvStmt.setString(3, mutualFundDetail.getInstrumentName());
			//Instrument name
			saveInvStmt.setString(4, mutualFundDetail.getMaturityName());
			//Maturity Name
			saveInvStmt.setString(5, mutualFundDetail.getSchemeNature());
			//Instrument Scheme
			saveInvStmt.setString(6, mutualFundDetail.getMutualFundName());
			//Investment Name
			saveInvStmt.setDate(
				7,
				new java.sql.Date(
					mutualFundDetail.getDateOfPurchase().getTime()));
			//Date of Purchase.
			saveInvStmt.setNull(8, java.sql.Types.INTEGER); //Tenure ** to be made null
			saveInvStmt.setString(9, null); //Tenure Type
			saveInvStmt.setNull(10, java.sql.Types.INTEGER); //Interest Rate ** to be made null
			saveInvStmt.setNull(11, java.sql.Types.INTEGER); //Face Value ** to be made null
			saveInvStmt.setDouble(12, mutualFundDetail.getCostOfPurchase());
			//Cost of Purchase
			saveInvStmt.setNull(13, java.sql.Types.INTEGER);
			//Compunding Frequency **** to be changed to null
			saveInvStmt.setString(14, mutualFundDetail.getMutualFundId());
			//Receipt Number
			saveInvStmt.setNull(15, java.sql.Types.VARCHAR); //Folio Number
			saveInvStmt.setInt(16, mutualFundDetail.getNoOfUnits());
			//Units Bought
			saveInvStmt.setNull(17, java.sql.Types.VARCHAR); //Call Put Option
			saveInvStmt.setNull(18, java.sql.Types.INTEGER);
			//Call Put Duration
			saveInvStmt.setInt(19, mutualFundDetail.getMaturityAmount());
			//Maturity Amount
			saveInvStmt.setDate(20, null); //Maturity Date
			saveInvStmt.setNull(21, java.sql.Types.DOUBLE); //Premium Discount
			saveInvStmt.setNull(22, java.sql.Types.INTEGER); //Units Sold
			saveInvStmt.setDate(
				23,
				new java.sql.Date(
					mutualFundDetail.getDateOfSelling().getTime()));
			//Selling Date
			saveInvStmt.setString(24, mutualFundDetail.getMarkToMarket());
			//Mark To Market
			saveInvStmt.setDouble(25, mutualFundDetail.getEntryLoad());
			//Entry Load
			saveInvStmt.setDouble(26, mutualFundDetail.getExitLoad());
			//Exit Load
			saveInvStmt.setString(27, mutualFundDetail.getOpenOrClose());
			//Open Close
			saveInvStmt.setString(28, mutualFundDetail.getIsinNumber());
			//ISIN Number
			saveInvStmt.setDouble(29, mutualFundDetail.getParValue());
			//Par Value
			saveInvStmt.setDouble(
				30,
				mutualFundDetail.getNavAsOnDateOfPurchase());
			//Purchase Date NAV
			saveInvStmt.setDouble(31, mutualFundDetail.getNavAsOnDate());
			//Current Date NAV
			saveInvStmt.setString(32, mutualFundDetail.getReferenceNumber()); //refence no
			saveInvStmt.setString(33, mutualFundDetail.getModifiedBy());
			//User ID
			saveInvStmt.setNull(34, java.sql.Types.VARCHAR);
			//pInInstrumentFeature
			saveInvStmt.setString(
				35,
				mutualFundDetail.getInvestmentReferenceNumber());
			//pInBuySellId
			saveInvStmt.setString(
				36,
				mutualFundDetail.getRating());

			saveInvStmt.setString(37,mutualFundDetail.getInstrumentCategory());
			saveInvStmt.setNull(38,java.sql.Types.DOUBLE);
			saveInvStmt.setString(39,mutualFundDetail.getAgency());				

			saveInvStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			saveInvStmt.registerOutParameter(40, java.sql.Types.VARCHAR);
			//Error Description
			saveInvStmt.execute();

			int status = saveInvStmt.getInt(1);
			String errorCode = saveInvStmt.getString(40);
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"saveInvestmentDetail_MutualFundDetail",
				"Error code and error " + status + " " + errorCode);
			if (status == Constants.FUNCTION_FAILURE) {
				saveInvStmt.close();
				saveInvStmt = null;
				Log.log(
					Log.ERROR,
					"IFDAO",
					"saveInvestmentDetail_MutualFundDetail",
					errorCode);
				throw new DatabaseException(errorCode);
			}
			saveInvStmt.close();
			saveInvStmt = null;
		} catch (SQLException e) {
			Log.log(Log.ERROR, "IFDAO", "saveInvestmentDetail", e.getMessage());
			Log.logException(e);
			throw new DatabaseException("Unable to save MutualFundDetail");
		} finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(
			Log.INFO,
			"IFDAO",
			"saveInvestmentDetail_MutualFundDetail",
			"Exited");
	}

	/**
	* The parameter to this method is an object of GovySecurityDetail.
	* This method stores the values from the input value object to the
	* INVESTMENT_DETAIL table in the database.
	*
	* @param GovtSecurityDetail - Value Object for Government Security Details
	* @throws DatabaseException
	*/
	public void saveInvestmentDetail(GovtSecurityDetail govtSecurityDetail)
		throws DatabaseException {
		Connection connection = null;
		CallableStatement saveInvStmt = null;
		int saveInvStatus = 0;
		String saveInvErr = "";
		java.sql.Date maturityDate;
		double maturityAmount = 0.0;

		try {
			connection = DBConnection.getConnection();
			saveInvStmt =
				connection.prepareCall(
					"{?=call funcInsertInvestmentDetail(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			saveInvStmt.setString(2, govtSecurityDetail.getInvesteeName());
			//Investee Name
			saveInvStmt.setString(3, govtSecurityDetail.getInstrumentName());
			//Instrument name
			saveInvStmt.setString(4, govtSecurityDetail.getMaturityName());
			//Maturity Name
			saveInvStmt.setNull(5, java.sql.Types.VARCHAR); //Instrument Scheme
			saveInvStmt.setString(6, govtSecurityDetail.getSeriesName());
			//Investment Name
			saveInvStmt.setDate(
				7,
				new java.sql.Date(
					govtSecurityDetail.getDateOfInvestment().getTime()));
			saveInvStmt.setInt(8, govtSecurityDetail.getTenure()); //Tenure
			saveInvStmt.setString(9, govtSecurityDetail.getTenureType());
			//Tenure Type
			saveInvStmt.setDouble(10, govtSecurityDetail.getCouponRate());
			//Interest Rate
			saveInvStmt.setDouble(11, govtSecurityDetail.getFaceValue());
			//Face Value
			saveInvStmt.setDouble(12, govtSecurityDetail.getCostOfPurchase());
			//Cost of Purchase
			saveInvStmt.setNull(13, java.sql.Types.INTEGER);
			//Compunding Frequency **** to be changed to null
			saveInvStmt.setString(
				14,
				govtSecurityDetail.getCertificateNumber());
			//Receipt Number
			saveInvStmt.setString(15, govtSecurityDetail.getFolioNumber());
			//Folio Number
			saveInvStmt.setInt(16, govtSecurityDetail.getNumberOfSecurities());
			//Units Bought
			saveInvStmt.setString(17, govtSecurityDetail.getCallOrPutOption());
			//Call Put Option
			saveInvStmt.setInt(18, govtSecurityDetail.getCallOrPutDuration());
			//Call Put Duration
			saveInvStmt.setDouble(19, govtSecurityDetail.getMaturityAmount());
			//Maturity Amount
			saveInvStmt.setDate(
				20,
				new java.sql.Date(
					govtSecurityDetail.getMaturityDate().getTime()));
			//Maturity Date
			saveInvStmt.setDouble(21, govtSecurityDetail.getCostOfPurchase()-govtSecurityDetail.getFaceValue()); //Premium Discount
			saveInvStmt.setNull(22, java.sql.Types.INTEGER); //Units Sold
			saveInvStmt.setNull(23, java.sql.Types.DATE); //Selling Date
			saveInvStmt.setString(24, null); //Mark To Market
			saveInvStmt.setNull(25, java.sql.Types.DOUBLE); //Entry Load
			saveInvStmt.setNull(26, java.sql.Types.DOUBLE); //Exit Load
			saveInvStmt.setString(27, null); //Open Close
			saveInvStmt.setString(28, null); //ISIN Number
			saveInvStmt.setNull(29, java.sql.Types.DOUBLE); //Par Value
			saveInvStmt.setNull(30, java.sql.Types.DOUBLE); //Purchase Date NAV
			saveInvStmt.setNull(31, java.sql.Types.DOUBLE); //Current Date NAV
			saveInvStmt.setNull(32, java.sql.Types.VARCHAR); //refence no
			saveInvStmt.setString(33, govtSecurityDetail.getModifiedBy());
			//User ID
			saveInvStmt.setNull(34, java.sql.Types.VARCHAR);
			//pInInstrumentFeature

			saveInvStmt.setString(
				35,
				govtSecurityDetail.getInvestmentReferenceNumber());
			//pInBuySellId
			saveInvStmt.setString(36,govtSecurityDetail.getRating());
			
			saveInvStmt.setString(37,govtSecurityDetail.getInstrumentCategory());
			saveInvStmt.setDouble(38,govtSecurityDetail.getYtmValue());
			saveInvStmt.setString(39,govtSecurityDetail.getAgency());

			// 0 - successfully inserted. 1 - error in inserting
			saveInvStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			saveInvStmt.registerOutParameter(40, java.sql.Types.VARCHAR);
			//Error Description
			saveInvStmt.execute();

			int status = saveInvStmt.getInt(1);
			String errorCode = saveInvStmt.getString(40);
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"saveInvestmentDetail_GovtSecurityDetail",
				"Error code and error " + status + " " + errorCode);
			if (status == Constants.FUNCTION_FAILURE) {
				saveInvStmt.close();
				saveInvStmt = null;
				Log.log(
					Log.ERROR,
					"IFDAO",
					"saveInvestmentDetail_GovtSecurityDetail",
					errorCode);
				throw new DatabaseException(errorCode);
			}
			saveInvStmt.close();
			saveInvStmt = null;
		} catch (SQLException e) {
			Log.log(Log.ERROR, "IFDAO", "saveInvestmentDetail", e.getMessage());
			Log.logException(e);
			throw new DatabaseException("Unable to save GovtSecurityDetail ");
		} finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(
			Log.INFO,
			"IFDAO",
			"saveInvestmentDetail_GovtSecurityDetail ",
			"Exited");
	}

	/**
	* The parameter to this method is an object of DebentureDetail.
	* This method stores the values from the input value object to the
	* INVESTMENT_DETAIL table in the database.
	*
	* @param DebentureDetail - Value Object for Debenture Details
	* @throws DatabaseException
	*/
	public void saveInvestmentDetail(DebentureDetail debentureDetail)
		throws DatabaseException {
		Connection connection = null;
		CallableStatement saveInvStmt = null;
		int saveInvStatus = 0;
		String saveInvErr = "";
		java.sql.Date maturityDate;
		double maturityAmount = 0.0;

		try {
			connection = DBConnection.getConnection();
			saveInvStmt =
				connection.prepareCall(
					"{?=call funcInsertInvestmentDetail(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			saveInvStmt.setString(2, debentureDetail.getInvesteeName());
			//Investee Name
			saveInvStmt.setString(3, debentureDetail.getInstrumentName());
			//Instrument name
			saveInvStmt.setString(4, debentureDetail.getMaturityName());
			//Maturity Name
			saveInvStmt.setNull(5, java.sql.Types.VARCHAR); //Instrument Scheme
			saveInvStmt.setString(6, debentureDetail.getDebentureName());
			//Investment Name
			saveInvStmt.setDate(
				7,
				new java.sql.Date(
					debentureDetail.getDateOfInvestment().getTime()));
			saveInvStmt.setInt(8, debentureDetail.getTenure()); //Tenure
			saveInvStmt.setString(9, debentureDetail.getTenureType());
			//Tenure Type
			saveInvStmt.setDouble(10, debentureDetail.getCouponRate());
			//Interest Rate
			saveInvStmt.setDouble(11, debentureDetail.getFaceValue());
			//Face Value
			saveInvStmt.setDouble(12, debentureDetail.getCostOfPurchase());
			//Cost of Purchase
			saveInvStmt.setNull(13, java.sql.Types.INTEGER);
			//Compunding Frequency **** to be changed to null
			saveInvStmt.setString(14, debentureDetail.getCertificateNumber());
			//Receipt Number
			saveInvStmt.setString(15, debentureDetail.getFolioNumber());
			//Folio Number
			saveInvStmt.setInt(16, debentureDetail.getNumberOfSecurities());
			//Units Bought
			saveInvStmt.setString(17, debentureDetail.getCallOrPutOption());
			//Call Put Option

			saveInvStmt.setDouble(19, debentureDetail.getMaturityAmount());
			//Maturity Amount
			saveInvStmt.setDate(
				20,
				new java.sql.Date(debentureDetail.getMaturityDate().getTime()));
			//Maturity Date

			saveInvStmt.setInt(18, debentureDetail.getCallOrPutDuration());
			//Call Put Duration
			saveInvStmt.setDouble(21, debentureDetail.getCostOfPurchase()-debentureDetail.getFaceValue()); //Premium Discount
			saveInvStmt.setNull(22, java.sql.Types.INTEGER); //Units Sold
			saveInvStmt.setNull(23, java.sql.Types.DATE); //Selling Date
			saveInvStmt.setString(24, null); //Mark To Market
			saveInvStmt.setNull(25, java.sql.Types.DOUBLE); //Entry Load
			saveInvStmt.setNull(26, java.sql.Types.DOUBLE); //Exit Load
			saveInvStmt.setString(27, null); //Open Close
			saveInvStmt.setString(28, null); //ISIN Number
			saveInvStmt.setNull(29, java.sql.Types.DOUBLE); //Par Value
			saveInvStmt.setNull(30, java.sql.Types.DOUBLE); //Purchase Date NAV
			saveInvStmt.setNull(31, java.sql.Types.DOUBLE); //Current Date NAV
			saveInvStmt.setNull(32, java.sql.Types.VARCHAR); //refence no
			saveInvStmt.setString(33, debentureDetail.getModifiedBy());
			//User ID
			saveInvStmt.setString(34, debentureDetail.getDebentureFeature());
			//pInInstrumentFeature
			//saveInvStmt.setNull(34,java.sql.Types.VARCHAR);						//pInInstrumentFeature
			saveInvStmt.setString(
				35,
				debentureDetail.getInvestmentReferenceNumber());
			//pInBuySellId
			saveInvStmt.setString(36,debentureDetail.getRating());
			saveInvStmt.setString(37,debentureDetail.getInstrumentCategory());
			saveInvStmt.setDouble(38,debentureDetail.getYtmValue());
			saveInvStmt.setString(39,debentureDetail.getAgency());
			
			saveInvStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			saveInvStmt.registerOutParameter(40, java.sql.Types.VARCHAR);
			//Error Description
			saveInvStmt.execute();

			int status = saveInvStmt.getInt(1);
			String errorCode = saveInvStmt.getString(40);
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"saveInvestmentDetail_DebentureDetail",
				"Error code and error " + status + " " + errorCode);
			if (status == Constants.FUNCTION_FAILURE) {
				saveInvStmt.close();
				saveInvStmt = null;
				Log.log(
					Log.ERROR,
					"IFDAO",
					"saveInvestmentDetail_DebentureDetail",
					errorCode);
				throw new DatabaseException(errorCode);
			}
			saveInvStmt.close();
			saveInvStmt = null;

		} catch (SQLException e) {
			Log.log(Log.ERROR, "IFDAO", "saveInvestmentDetail", e.getMessage());
			Log.logException(e);
			throw new DatabaseException("Unable to save DebentureDetail");
		} finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(
			Log.INFO,
			"IFDAO",
			"saveInvestmentDetail_DebentureDetail",
			"Exited");
	}

	/**
	* The parameter to this method is an object of BondsDetail.
	* This method stores the values from the input value object to the
	* INVESTMENT_DETAIL table in the database.
	*
	* @param BondsDetail - Value Object for Bond Details
	* @throws DatabaseException
	*/
	public void saveInvestmentDetail(BondsDetail bondsDetail)
		throws DatabaseException {
		Connection connection = null;
		CallableStatement saveInvStmt = null;
		int saveInvStatus = 0;
		String saveInvErr = "";
		java.sql.Date maturityDate;
		double maturityAmount = 0.0;

		try {
			connection = DBConnection.getConnection();
			saveInvStmt =
				connection.prepareCall(
					"{?=call funcInsertInvestmentDetail(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			saveInvStmt.setString(2, bondsDetail.getInvesteeName());
			//Investee Name 
			saveInvStmt.setString(3, bondsDetail.getInstrumentName());
			//Instrument name
			saveInvStmt.setString(4, bondsDetail.getMaturityName());
			//Maturity Name
			saveInvStmt.setNull(5, java.sql.Types.VARCHAR); //Instrument Scheme
			saveInvStmt.setString(6, bondsDetail.getBondName());
			//Investment Name
			saveInvStmt.setDate(
				7,
				new java.sql.Date(bondsDetail.getDateOfInvestment().getTime()));
			saveInvStmt.setInt(8, bondsDetail.getTenure()); //Tenure
			saveInvStmt.setString(9, bondsDetail.getTenureType());
			//Tenure Type
			saveInvStmt.setDouble(10, bondsDetail.getCouponRate());
			//Interest Rate
			saveInvStmt.setDouble(11, bondsDetail.getFaceValue()); //Face Value
			saveInvStmt.setDouble(12, bondsDetail.getCostOfPurchase());
			//Cost of Purchase
			saveInvStmt.setNull(13, java.sql.Types.INTEGER);
			//Compunding Frequency **** to be changed to null
			saveInvStmt.setString(14, bondsDetail.getCertificateNumber());
			//Receipt Number
			saveInvStmt.setString(15, bondsDetail.getFolioNumber());
			//Folio Number
			saveInvStmt.setInt(16, bondsDetail.getNumberOfSecurities());
			//Units Bought
			saveInvStmt.setString(17, bondsDetail.getCallOrPutOption());
			//Call Put Option
			saveInvStmt.setInt(18, bondsDetail.getCallOrPutDuration());
			//Call Put Duration
			saveInvStmt.setDouble(19, bondsDetail.getMaturityAmount());
			//Maturity Amount
			saveInvStmt.setDate(
				20,
				new java.sql.Date(bondsDetail.getMaturityDate().getTime()));
			//Maturity Date

			saveInvStmt.setDouble(21, bondsDetail.getCostOfPurchase()-bondsDetail.getFaceValue()); //Premium Discount
			saveInvStmt.setNull(22, java.sql.Types.INTEGER); //Units Sold
			saveInvStmt.setNull(23, java.sql.Types.DATE); //Selling Date
			saveInvStmt.setString(24, null); //Mark To Market
			saveInvStmt.setNull(25, java.sql.Types.DOUBLE); //Entry Load
			saveInvStmt.setNull(26, java.sql.Types.DOUBLE); //Exit Load
			saveInvStmt.setString(27, null); //Open Close
			saveInvStmt.setString(28, null); //ISIN Number
			saveInvStmt.setNull(29, java.sql.Types.DOUBLE); //Par Value
			saveInvStmt.setNull(30, java.sql.Types.DOUBLE); //Purchase Date NAV
			saveInvStmt.setNull(31, java.sql.Types.DOUBLE); //Current Date NAV
			saveInvStmt.setNull(32, java.sql.Types.VARCHAR); //refence no
			saveInvStmt.setString(33, bondsDetail.getModifiedBy()); //User ID
			saveInvStmt.setNull(34, java.sql.Types.VARCHAR);
			//pInInstrumentFeature
			saveInvStmt.setString(
				35,
				bondsDetail.getInvestmentReferenceNumber());
			//pInBuySellId
			saveInvStmt.setString(36,bondsDetail.getRating());
			
			saveInvStmt.setString(37,bondsDetail.getInstrumentCategory());
			saveInvStmt.setDouble(38,bondsDetail.getYtmValue());
			saveInvStmt.setString(39,bondsDetail.getAgency());

			saveInvStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			saveInvStmt.registerOutParameter(40, java.sql.Types.VARCHAR);
			//Error Description
			saveInvStmt.execute();

			int status = saveInvStmt.getInt(1);
			String errorCode = saveInvStmt.getString(40);
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"saveInvestmentDetail_BondsDetail",
				"Error code and error " + status + " " + errorCode);
			if (status == Constants.FUNCTION_FAILURE) {
				saveInvStmt.close();
				saveInvStmt = null;
				Log.log(
					Log.ERROR,
					"IFDAO",
					"saveInvestmentDetail_BondsDetail",
					errorCode);
				throw new DatabaseException(errorCode);
			}
			saveInvStmt.close();
			saveInvStmt = null;
		} catch (SQLException e) {
			Log.log(Log.ERROR, "IFDAO", "saveInvestmentDetail", e.getMessage());
			Log.logException(e);
			throw new DatabaseException("Unable to save BondsDetail");
		} finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(
			Log.INFO,
			"IFDAO",
			"saveInvestmentDetail_BondsDetail",
			"Exited");
	}

	/**
	*This method persists the values in InvestmentFulfillmentDetail object in the database.
	*/
	public void saveInvestmentFulfillmentDetail(InvestmentFulfillmentDetail investmentFulfillmentDetails, String updateFlag,ChequeDetails chequeDetails,String contextPath,String userId)
		throws DatabaseException,MessageException {

			Log.log(Log.INFO,"IFDAO","saveInvestmentFulfillmentDetail","Entered");

		Connection connection = null;
		CallableStatement saveInvStmt = null;
		int saveInvStatus = 0;
		String saveInvErr = "";
		java.sql.Date maturityDate;
		double maturityAmount = 0.0;

		try {
			connection = DBConnection.getConnection();

			Log.log(Log.INFO,"IFDAO","saveInvestmentFulfillmentDetail","flag " + updateFlag);

			if (updateFlag==null ||updateFlag.equals("") || updateFlag.equals("0"))
			{
				saveInvStmt =
					connection.prepareCall(
						"{?=call funcInvestmentFulfillmentDtl(?,?,?,?,?,?,?,?,?,?,?,?,?)}");

				saveInvStmt.registerOutParameter(1, java.sql.Types.INTEGER);
				saveInvStmt.setString(
					2,
					investmentFulfillmentDetails.getReceiptNumber());
				//pInReceiptNumber
				saveInvStmt.setString(
					3,
					investmentFulfillmentDetails.getInstrumentName());
				//pInInstrumentName
				saveInvStmt.setString(
					4,
					investmentFulfillmentDetails.getInflowOutFlowFlag());
				//pInInflowOutFlowFlag
				saveInvStmt.setString(
					5,
					investmentFulfillmentDetails.getInstrumentType());
				//pInInstrumentType
				saveInvStmt.setString(
					6,
					investmentFulfillmentDetails.getInstrumentNumber());
				//pInInstrumentNumber
				saveInvStmt.setDate(
					7,
					new java.sql.Date(
						investmentFulfillmentDetails
							.getInstrumentDate()
							.getTime()));
				//pInInstrumentDate
				saveInvStmt.setDouble(
					8,
					investmentFulfillmentDetails.getInstrumentAmount());
				//pInInstrumentAmount
				saveInvStmt.setString(
					9,
					investmentFulfillmentDetails.getDrawnBank());
				//pInDrawnBank
				saveInvStmt.setString(
					10,
					investmentFulfillmentDetails.getDrawnBranch());
				//pInDrawnBranch
				saveInvStmt.setString(
					11,
					investmentFulfillmentDetails.getPayableAt());
				//pInPayableAt
				saveInvStmt.setString(
					12,
					investmentFulfillmentDetails.getModifiedBy());
				//pInUserId
				saveInvStmt.setString(
					13,
					investmentFulfillmentDetails.getInvestmentRefNumber());
				//pInUserId
				saveInvStmt.registerOutParameter(14, java.sql.Types.VARCHAR);
				//Error Description
			}
			else
			{
				saveInvStmt =
					connection.prepareCall(
						"{?=call funcUpdInvFulfillmentDtl(?,?,?,?,?,?,?,?,?,?,?)}");

				saveInvStmt.registerOutParameter(1, java.sql.Types.INTEGER);
				saveInvStmt.setString(
					2,
					investmentFulfillmentDetails.getInstrumentType());
				//pInInstrumentType
				saveInvStmt.setString(
					3,
					investmentFulfillmentDetails.getInstrumentNumber());
				//pInInstrumentNumber
				saveInvStmt.setDate(
					4,
					new java.sql.Date(
						investmentFulfillmentDetails
							.getInstrumentDate()
							.getTime()));
				//pInInstrumentDate
				saveInvStmt.setDouble(
					5,
					investmentFulfillmentDetails.getInstrumentAmount());
				//pInInstrumentAmount
				saveInvStmt.setString(
					6,
					investmentFulfillmentDetails.getDrawnBank());
				//pInDrawnBank
				saveInvStmt.setString(
					7,
					investmentFulfillmentDetails.getDrawnBranch());
				//pInDrawnBranch
				saveInvStmt.setString(
					8,
					investmentFulfillmentDetails.getPayableAt());
				//pInPayableAt
				saveInvStmt.setString(
					9,
					investmentFulfillmentDetails.getModifiedBy());
				//pInUserId
				saveInvStmt.setString(
					10,
					investmentFulfillmentDetails.getInvestmentRefNumber());
				//pInUserId
				saveInvStmt.setInt(
									11,
									investmentFulfillmentDetails.getId());
								//pInUserId
				saveInvStmt.registerOutParameter(12, java.sql.Types.VARCHAR);
				//Error Description
			}
			saveInvStmt.execute();

			int status = saveInvStmt.getInt(1);
			String errorCode = "";
			if (updateFlag==null || updateFlag.equals("") || updateFlag.equals("0"))
			{
				errorCode = saveInvStmt.getString(14);
			}
			else
			{
				errorCode = saveInvStmt.getString(12);
			}
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"saveInvestmentFulfillmentDetail",
				"Error code and error " + status + " " + errorCode);
			if (status == Constants.FUNCTION_FAILURE) {
				saveInvStmt.close();
				saveInvStmt = null;
				Log.log(
					Log.ERROR,
					"IFDAO",
					"saveInvestmentFulfillmentDetail",
					errorCode);
				throw new DatabaseException(errorCode);
			}
			saveInvStmt.close();
			saveInvStmt = null;
			
			RpDAO rpDAO = new RpDAO();
			if(chequeDetails!=null)
			{
				rpDAO.chequeDetailsInsertSuccess(chequeDetails,connection,userId);
			}
			
			/**
			 * generating vouchers
			 */
			
			RpProcessor rpProcessor = new RpProcessor();
		
			ArrayList vouchers = new ArrayList();
		
			String narration ="";
		
			Properties accCodes=new Properties();
			Log.log(Log.DEBUG,"IFProcessor","insertVoucherInvDetails","path " + contextPath);
			File tempFile = new File(contextPath+"\\WEB-INF\\classes", RpConstants.AC_CODE_FILE_NAME);
			Log.log(Log.DEBUG,"IFProcessor","insertVoucherInvDetails","file opened "+tempFile.getAbsolutePath());
			File accCodeFile = new File(tempFile.getAbsolutePath());
			try
			{
				FileInputStream fin = new FileInputStream(accCodeFile);
				accCodes.load(fin);
			}
			catch(FileNotFoundException fe)
			{
				throw new MessageException("Could not load Account Codes.");
			}
			catch(IOException ie)
			{
				throw new MessageException("Could not load Account Codes.");
			}		
		
			VoucherDetail voucherDetail = new VoucherDetail();
			vouchers.clear();
			voucherDetail.setBankGLCode(accCodes.getProperty(RpConstants.INVESTMENT_AC));
			voucherDetail.setBankGLName(investmentFulfillmentDetails.getDrawnBank());
			voucherDetail.setDeptCode(RpConstants.RP_CGTSI);
			voucherDetail.setAmount(investmentFulfillmentDetails.getInstrumentAmount());
			voucherDetail.setVoucherType(RpConstants.PAYMENT_VOUCHER);
		
			Voucher voucher = new Voucher();
			voucher.setAcCode(accCodes.getProperty(RpConstants.BANK_AC));
			voucher.setPaidTo("CGTSI");
			voucher.setDebitOrCredit("C");
			voucher.setInstrumentDate(investmentFulfillmentDetails.getInstrumentDate().toString());
			voucher.setInstrumentNo(investmentFulfillmentDetails.getInstrumentNumber());
			voucher.setInstrumentType(investmentFulfillmentDetails.getInstrumentType());
			voucher.setAmountInRs((0-investmentFulfillmentDetails.getInstrumentAmount())+"");
				
			vouchers.add(voucher);
		
			if(investmentFulfillmentDetails.getInvestmentRefNumber()!=null && !investmentFulfillmentDetails.getInvestmentRefNumber().equals(""))
			{
				narration = narration + "Investment Reference Number: " + investmentFulfillmentDetails.getInvestmentRefNumber();
			}
			narration = narration + " Investee Name : " + investmentFulfillmentDetails.getInvesteeName();
		
			voucherDetail.setVouchers(vouchers);
			voucherDetail.setNarration(narration);	
		
			insertVoucherDetailsForInv(voucherDetail,userId,connection);	
			
			/**
			 * 
			 */
			
			connection.commit();

		} catch (SQLException e) {
			Log.log(
				Log.ERROR,
				"IFDAO",
				"saveInvestmentFulfillmentDetail",
				e.getMessage());
			Log.logException(e);
			throw new DatabaseException("Unable to save InvestmentFulfillmentDetail");
		} finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO, "IFDAO", "saveInvestmentFulfillmentDetail", "Exited");
	}
	
	public String insertVoucherDetailsForInv(VoucherDetail voucherDetail,String userId, Connection connection)  throws DatabaseException,MessageException
	{

		 Log.log(Log.INFO,"RpDAO","insertVoucherDetails","Entered");

		 boolean newConn = false;

		 if (connection==null)
		 {
			 connection=DBConnection.getConnection(false);
			 newConn=true;
		 }


		 String voucherId="";

		 try
		 {

			 CallableStatement callable=connection.prepareCall("{?=call funcPaymentVoucher(?,?,?,?,?,?,?,?,?,?,?,?)}");

			 callable.registerOutParameter(1,Types.INTEGER);

			 callable.setString(2,null);
			 callable.setString(3,voucherDetail.getVoucherType());
			 callable.setString(4,voucherDetail.getBankGLCode());
			 callable.setString(5,voucherDetail.getBankGLName());
			 callable.setString(6,voucherDetail.getDeptCode());
			 callable.setDouble(7,voucherDetail.getAmount());
			 callable.setString(8,voucherDetail.getNarration());

			 callable.setString(9,voucherDetail.getManager());
			 callable.setString(10,voucherDetail.getAsstManager());
			 callable.setString(11,userId);
			 callable.registerOutParameter(12,Types.VARCHAR);
			 callable.registerOutParameter(13,Types.VARCHAR);

			 callable.execute();

			 int errorCode=callable.getInt(1);

			 String error=callable.getString(13);

			 Log.log(Log.DEBUG,"RpDAO","insertVoucherDetails","errorCode,error "+errorCode+","+error);

			 if(errorCode==Constants.FUNCTION_FAILURE)
			 {
				 Log.log(Log.ERROR,"RpDAO","insertVoucherDetails",error);

				 connection.rollback();

				 callable.close();
				 callable=null;

				 throw new DatabaseException(error);
			 }
			 voucherId=callable.getString(12);

			 Log.log(Log.DEBUG,"RpDAO","insertVoucherDetails","voucherId "+voucherId);


			 callable.close();
			 callable=null;

			 callable=connection.prepareCall("{?=call funcVoucherTransactionDtl(?,?,?,?,?,?,?,?,?,?,?)}");

			 callable.registerOutParameter(1,Types.INTEGER);

			 callable.setString(2,voucherId);

			 callable.registerOutParameter(12,Types.VARCHAR);

			 ArrayList vouchers=voucherDetail.getVouchers();

			 SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");

			 for(int i=0;i<vouchers.size();i++)
			 {
				 Voucher voucher=(Voucher)vouchers.get(i);

				 callable.setString(3,voucher.getAcCode());
				 Log.log(Log.DEBUG,"RpDAO","insertVoucherDetails","ac cd "+voucher.getAcCode());
				 callable.setString(4,voucher.getPaidTo());
 /*				if (voucher.getDebitOrCredit().equalsIgnoreCase("D"))
				 {
					 callable.setString(5,voucher.getAmountInRs());
				 }
				 else if (voucher.getDebitOrCredit().equalsIgnoreCase("C"))
				 {
					 callable.setString(5,"-"+voucher.getAmountInRs());
				 }*/
				 callable.setString(5,voucher.getAmountInRs());
				 Log.log(Log.DEBUG,"RpDAO","insertVoucherDetails","Amt "+voucher.getAmountInRs());
				 callable.setString(6,voucher.getDebitOrCredit());
				 callable.setString(7,voucher.getAdvNo());
				 Date parsedDate=null;
				 if (voucher.getAdvDate()!=null)
				 {
					 parsedDate=dateFormat.parse(voucher.getAdvDate(),new ParsePosition(0));

					 Log.log(Log.DEBUG,"RpDAO","insertVoucherDetails","Adv. Date "+parsedDate);
				 }

				 if(parsedDate==null)
				 {
					 callable.setDate(8,null);
				 }
				 else
				 {
					 callable.setDate(8,new java.sql.Date(parsedDate.getTime()));
				 }

				 callable.setString(9,voucher.getInstrumentType());
				 callable.setString(10,voucher.getInstrumentNo());

				 if (voucher.getInstrumentDate()!=null)
				 {
					 parsedDate=dateFormat.parse(voucher.getInstrumentDate(),new ParsePosition(0));
				 }

				 Log.log(Log.DEBUG,"RpDAO","insertVoucherDetails","Instrument Date "+parsedDate);

				 if(parsedDate==null)
				 {
					 callable.setDate(11,null);
				 }
				 else
				 {
					 callable.setDate(11,new java.sql.Date(parsedDate.getTime()));
				 }

				 callable.execute();

				 errorCode=callable.getInt(1);

				 error=callable.getString(12);

				 Log.log(Log.DEBUG,"RpDAO","insertVoucherDetails","errorCode,error "+errorCode+","+error);

				 if(errorCode==Constants.FUNCTION_FAILURE)
				 {
					 Log.log(Log.ERROR,"RpDAO","insertVoucherDetails",error);

					 connection.rollback();

					 callable.close();
					 callable=null;

					 throw new DatabaseException(error);
				 }
				

			 }
			 callable.close();
			 callable=null;

			 Log.log(Log.DEBUG,"RpDAO","insertVoucherDetails","Inserting BAS information ");
			 callable=connection.prepareCall("{?= call funcInsertBASDtl(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			 callable.registerOutParameter(1,Types.INTEGER);

			 callable.setDate(2, new java.sql.Date(new java.util.Date().getTime()));

			 callable.setString(3,voucherDetail.getVoucherType());

			 Log.log(Log.DEBUG,"RpDAO","insertVoucherDetails","voucherDetail.getVoucherType()  "+voucherDetail.getVoucherType());

			 callable.setString(4,voucherId);

			 Log.log(Log.DEBUG,"RpDAO","insertVoucherDetails","voucherId  "+voucherId);

			 callable.setString(5,null);
			 callable.setString(6,voucherDetail.getDeptCode());

			 Log.log(Log.DEBUG,"RpDAO","insertVoucherDetails","voucherDetail.getDeptCode()  "+voucherDetail.getDeptCode());

			 callable.setString(7,voucherDetail.getBankGLCode());

			 Log.log(Log.DEBUG,"RpDAO","insertVoucherDetails","voucherDetail.getBankGLCode()  "+voucherDetail.getBankGLCode());

			 callable.setDate(8,null);
			 callable.setString(9,null);
			 callable.setString(10,null);
			 callable.setDouble(11,voucherDetail.getAmount());

			 callable.setString(12,userId);

			 Log.log(Log.DEBUG,"RpDAO","insertVoucherDetails","userId  "+userId);

			 callable.setDate(13,null);
			 callable.setString(14,null);

			 callable.setString(15,voucherDetail.getNarration());

			 Log.log(Log.DEBUG,"RpDAO","insertVoucherDetails","voucherDetail.getNarration()  "+voucherDetail.getNarration());

			 callable.setDate(16,null);

			 callable.registerOutParameter(17,Types.VARCHAR);

			 callable.execute();

			 errorCode=callable.getInt(1);

			 error=callable.getString(17);

			 Log.log(Log.DEBUG,"RpDAO","insertVoucherDetails","errorCode,error "+errorCode+","+error);

			 if(errorCode==Constants.FUNCTION_FAILURE)
			 {
				 Log.log(Log.ERROR,"RpDAO","insertVoucherDetails",error);

				 connection.rollback();

				 callable.close();
				 callable=null;

				 throw new DatabaseException(error);
			 }
			 callable.close();
			 callable=null;

			 Log.log(Log.DEBUG,"RpDAO","insertVoucherDetails","Inserting BAS information ");
			 callable=connection.prepareCall("{?= call funcInsertBASDtl(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			 callable.registerOutParameter(1,Types.INTEGER);

			 callable.setDate(2, new java.sql.Date(new java.util.Date().getTime()));

			 callable.setString(3,voucherDetail.getVoucherType());

			 Log.log(Log.DEBUG,"RpDAO","insertVoucherDetails","voucherDetail.getVoucherType()  "+voucherDetail.getVoucherType());

			 callable.setString(4,voucherId);

			 Log.log(Log.DEBUG,"RpDAO","insertVoucherDetails","voucherId  "+voucherId);

			 callable.setString(5,null);
			 callable.setString(6,voucherDetail.getDeptCode());

			 Log.log(Log.DEBUG,"RpDAO","insertVoucherDetails","voucherDetail.getDeptCode()  "+voucherDetail.getDeptCode());

			 callable.setString(12,userId);

			 Log.log(Log.DEBUG,"RpDAO","insertVoucherDetails","userId  "+userId);

			 callable.setString(15,voucherDetail.getNarration());

			 Log.log(Log.DEBUG,"RpDAO","insertVoucherDetails","voucherDetail.getNarration()  "+voucherDetail.getNarration());

			 callable.registerOutParameter(17,Types.VARCHAR);

			 //Insert into BAS related table.
			 Date parsedDate=null;

			 for(int i=0;i<vouchers.size();i++)
			 {
				 Voucher voucher=(Voucher)vouchers.get(i);

				 callable.setString(7,voucher.getAcCode());

				 Log.log(Log.DEBUG,"RpDAO","insertVoucherDetails","voucher.getAcCode()  "+voucher.getAcCode());


				 if (voucher.getInstrumentDate()!=null)
				 {
					 parsedDate=dateFormat.parse(voucher.getInstrumentDate(),new ParsePosition(0));
				 }

				 Log.log(Log.DEBUG,"RpDAO","insertVoucherDetails","Instrument Date "+parsedDate);

				 if(parsedDate==null)
				 {
					 callable.setDate(8,null);
				 }
				 else
				 {
					 callable.setDate(8,new java.sql.Date(parsedDate.getTime()));
				 }

				 callable.setString(9,voucher.getInstrumentType());

				 Log.log(Log.DEBUG,"RpDAO","insertVoucherDetails","voucherDetail.getInstrumentType()  "+voucher.getInstrumentType());

				 callable.setString(10,voucher.getInstrumentNo());
				 Log.log(Log.DEBUG,"RpDAO","insertVoucherDetails","voucherDetail.getInstrumentNo()  "+voucher.getInstrumentNo());

				 callable.setString(11,voucher.getAmountInRs());

				 Log.log(Log.DEBUG,"RpDAO","insertVoucherDetails","voucherDetail.getAmountInRs()  "+voucher.getAmountInRs());

				 parsedDate=null;
				 if (voucher.getAdvDate()!=null)
				 {
					 parsedDate=dateFormat.parse(voucher.getAdvDate(),new ParsePosition(0));

					 Log.log(Log.DEBUG,"RpDAO","insertVoucherDetails","Adv. Date "+parsedDate);
				 }
				 if(parsedDate==null)
				 {
					 callable.setDate(13,null);
				 }
				 else
				 {
					 callable.setDate(13,new java.sql.Date(parsedDate.getTime()));
				 }

				 Log.log(Log.DEBUG,"RpDAO","insertVoucherDetails","voucherDetail.getAdvNo()  "+voucher.getAdvNo());

				 callable.setString(14,voucher.getAdvNo());

				 Log.log(Log.DEBUG,"RpDAO","insertVoucherDetails","voucherDetail.getPaidTo()  "+voucher.getPaidTo());

				 callable.setString(16,voucher.getPaidTo());

				 callable.execute();

				 errorCode=callable.getInt(1);

				 error=callable.getString(17);

				 Log.log(Log.DEBUG,"RpDAO","insertVoucherDetails","errorCode,error "+errorCode+","+error);

				 if(errorCode==Constants.FUNCTION_FAILURE)
				 {
					 Log.log(Log.ERROR,"RpDAO","insertVoucherDetails",error);

					 connection.rollback();

					 callable.close();
					 callable=null;

					 throw new DatabaseException(error);
				 }
			 }

			 callable.close();
			 callable=null;

			 if (newConn)
			 {
				 connection.commit();
			 }
		 }
		 catch(SQLException e)
		 {
			 Log.log(Log.ERROR,"RpDAO","insertVoucherDetails",e.getMessage());
			 Log.logException(e);
			 try {
				 connection.rollback();
			 } catch (SQLException e1) {
				 e1.printStackTrace();
			 }

			 throw new DatabaseException("Unable to insert Voucher Details");
		 }
		 finally
		 {
			 if (newConn)
			 {
				 DBConnection.freeConnection(connection);
			 }
		 }

		 Log.log(Log.INFO,"RpDAO","insertVoucherDetails","Exited");

		 return voucherId;

	}
	

	/**
	* This method is used for projecting expected claims. The return type for this
	* method is a HashMap
	*/
	/*   public HashMap getPlanningDetails(String date) throws DatabaseException
	{
	return null;
	}*/

	/**
	* This method persists the values in InvestmentPlanningDetail in the database.
	*/
	public void saveInvestmentPlanningDetail(InvestmentPlanningDetail investmentPlanningDetails)
		throws DatabaseException {
		String investeeName = null;
		String instrumentName = null;
		String numberOfUnits = null;
		String worthOfUnits = null;
		String investmentReferenceNumber = null;
		String buyOrSellFlag =
			investmentPlanningDetails.getIsBuyOrSellRequest();
		String requestedBy = investmentPlanningDetails.getModifiedBy();
		Connection connection = DBConnection.getConnection();
		CallableStatement callableStmt = null;
		int status = -1;
		String errorCode = null;

		try {
			connection = DBConnection.getConnection();
			if ((buyOrSellFlag.trim()).equals(BUY_REQUEST)) {
				investeeName = investmentPlanningDetails.getInvesteeName();
				instrumentName = investmentPlanningDetails.getInstrumentName();
				numberOfUnits = investmentPlanningDetails.getNoOfUnits();

				callableStmt =
					connection.prepareCall(
						"{? = call funcInsertBuySell(?,?,?,?,?,?,?,?)}");
				callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
				callableStmt.setString(2, investeeName);
				callableStmt.setString(3, instrumentName);
				callableStmt.setNull(4, java.sql.Types.VARCHAR);
				callableStmt.setString(5, buyOrSellFlag);
				callableStmt.setInt(6, Integer.parseInt(numberOfUnits));
				callableStmt.setNull(7, java.sql.Types.DOUBLE);
				//Current Date NAV
				callableStmt.setString(8, requestedBy);
				callableStmt.registerOutParameter(9, java.sql.Types.VARCHAR);
			} else if ((buyOrSellFlag.trim()).equals(SELL_REQUEST)) {
				investmentReferenceNumber = null;
				//investmentPlanningDetails.getInvestmentRefNumber();
				callableStmt =
					connection.prepareCall(
						"{? = call funcInsertBuySell(?,?,?,?,?,?,?,?)}");
				callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
				callableStmt.setString(2, investeeName);
				callableStmt.setString(3, instrumentName);
				callableStmt.setNull(4, java.sql.Types.VARCHAR);
				callableStmt.setString(5, buyOrSellFlag);
				callableStmt.setInt(6, Integer.parseInt(numberOfUnits));
				callableStmt.setNull(7, java.sql.Types.DOUBLE);
				callableStmt.setString(8, requestedBy);
				callableStmt.registerOutParameter(9, java.sql.Types.VARCHAR);
			}
			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(9);
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"saveInvestmentPlanningDetail",
				"Error code and error " + status + " " + errorCode);
			if (status == Constants.FUNCTION_FAILURE) {
				callableStmt.close();
				callableStmt = null;
				Log.log(
					Log.ERROR,
					"IFDAO",
					"saveInvestmentPlanningDetail",
					errorCode);
				throw new DatabaseException(errorCode);
			}
			callableStmt.close();
			callableStmt = null;

		} catch (SQLException e) {
			Log.log(
				Log.ERROR,
				"IFDAO",
				"saveInvestmentPlanningDetail",
				e.getMessage());
			Log.logException(e);
			throw new DatabaseException("Unable to save InvestmentPlanningDetail");
		} finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO, "IFDAO", "saveInvestmentPlanningDetail", "Exited");
	}

	/**
	* This method saves the inflow-outflow details in the database.
	*/
	public void saveActualInflowOutflowDetails(ActualInflowOutflowDetails actualIODetails)
		throws DatabaseException {
		Connection connection = null;
		CallableStatement saveActualIODetailsStmt = null;
		ArrayList actualIOHeadDetails = null;
		ArrayList actualIOSubHeadDetails = null;
		ActualIOHeadDetail actualIOHeadDetail = null;
		ActualIOSubHeadDetail actualIOSubHeadDetail = null;
		String inflowOrOutflow = null;
		String term = null;
		String budgetHeadTitle = null;
		String budgetSubHeadTitle = null;
		double budgetAmount = 0.0;
		String dateOfFlow = null;
		String month = null;
		String year = null;
		String modifiedByUser = null;
		java.util.Date modifiedOnDate = null;
		int status = -1;
		String errorCode = null;
		String inwardOrOutward = actualIODetails.getIsInflowOrOutflow();

		Log.log(
			Log.DEBUG,
			"IFDAO",
			"saveActualInflowOutflowDetails",
			"inwardOrOutward " + inwardOrOutward);
		try {
			connection = DBConnection.getConnection(false);
			term = actualIODetails.getIsAnnualOrShortTerm().toUpperCase();
			dateOfFlow = actualIODetails.getDateOfFlow();
			modifiedByUser = actualIODetails.getModifiedBy();
			actualIOHeadDetails = actualIODetails.getActualIOHeadDetails();

			Log.log(
				Log.DEBUG,
				"IFDAO",
				"saveActualInflowOutflowDetails",
				"term, date of flow,"
					+ "actual head details "
					+ term
					+ ", "
					+ dateOfFlow
					+ ", "
					+ actualIOHeadDetails);

			for (int i = 0; i < actualIOHeadDetails.size(); i++) {
				actualIOHeadDetail =
					(ActualIOHeadDetail) actualIOHeadDetails.get(i);
				actualIOSubHeadDetails =
					actualIOHeadDetail.getActualIOSubHeadDetails();
				budgetHeadTitle = actualIOHeadDetail.getBudgetHead();

				if (budgetHeadTitle != null) {
					budgetHeadTitle = budgetHeadTitle.trim();
				}

				budgetAmount = actualIOHeadDetail.getBudgetAmount();

				Log.log(
					Log.DEBUG,
					"IFDAO",
					"saveActualInflowOutflowDetails",
					"Head, amount sub head details ,"
						+ ""
						+ budgetHeadTitle
						+ ", "
						+ budgetAmount
						+ ", "
						+ actualIOSubHeadDetails);

				if (term.equals(TERM_ANNUAL)
					&& (actualIOSubHeadDetails == null)) {
					Log.log(
						Log.DEBUG,
						"IFDAO",
						"saveActualInflowOutflowDetails",
						"Annual.. No sub heads ");

					saveActualIODetailsStmt =
						connection.prepareCall(
							"{? = call funcInsertIOflowDetail(?,?,?,?,?,?,?,?)}");

					saveActualIODetailsStmt.registerOutParameter(
						1,
						java.sql.Types.INTEGER);
					saveActualIODetailsStmt.setString(2, budgetHeadTitle);
					//Budget Head
					saveActualIODetailsStmt.setString(3, inwardOrOutward);
					//Flag
					saveActualIODetailsStmt.setString(4, null);
					//Budget Sub Head
					saveActualIODetailsStmt.setDouble(5, budgetAmount);
					//Budget Amount
					saveActualIODetailsStmt.setDate(
						6,
						java.sql.Date.valueOf(
							DateHelper.stringToSQLdate(dateOfFlow)));
					//dateOfFlow
					saveActualIODetailsStmt.setString(7, inwardOrOutward);
					saveActualIODetailsStmt.setString(8, modifiedByUser);
					saveActualIODetailsStmt.registerOutParameter(
						9,
						java.sql.Types.VARCHAR);

					saveActualIODetailsStmt.execute();
					status = saveActualIODetailsStmt.getInt(1);
					errorCode = saveActualIODetailsStmt.getString(9);

					if (status == Constants.FUNCTION_FAILURE) {
						saveActualIODetailsStmt.close();
						saveActualIODetailsStmt = null;

						connection.rollback();

						Log.log(
							Log.ERROR,
							"IFDAO",
							"saveActualInflowOutflowDetails",
							errorCode);
						throw new DatabaseException(errorCode);
					}
					saveActualIODetailsStmt.close();
					saveActualIODetailsStmt = null;

					Log.log(
						Log.DEBUG,
						"IFDAO",
						"saveActualInflowOutflowDetails",
						"ErrorCode and Error " + status + ", " + errorCode);
				} else if (
					term.equals(TERM_ANNUAL)
						&& (actualIOSubHeadDetails != null)) {
					Log.log(
						Log.DEBUG,
						"IFDAO",
						"saveActualInflowOutflowDetails",
						"Annual..sub heads ");

					for (int j = 0; j < actualIOSubHeadDetails.size(); j++) {
						Log.log(
							Log.DEBUG,
							"IFDAO",
							"saveActualInflowOutflowDetails",
							"j " + j);

						actualIOSubHeadDetail =
							(ActualIOSubHeadDetail) actualIOSubHeadDetails.get(
								j);
						budgetSubHeadTitle =
							actualIOSubHeadDetail.getSubHeadTitle();
						budgetSubHeadTitle = budgetSubHeadTitle.trim();
						budgetAmount = actualIOSubHeadDetail.getBudgetAmount();

						Log.log(
							Log.DEBUG,
							"IFDAO",
							"saveActualInflowOutflowDetails",
							"budget sub head, amount "
								+ budgetSubHeadTitle
								+ ", "
								+ budgetAmount);

						saveActualIODetailsStmt =
							connection.prepareCall(
								"{? = call funcInsertIOflowDetail(?,?,?,?,?,?,?,?)}");

						saveActualIODetailsStmt.registerOutParameter(
							1,
							java.sql.Types.INTEGER);
						saveActualIODetailsStmt.setString(2, budgetHeadTitle);
						//Budget Head
						saveActualIODetailsStmt.setString(3, inwardOrOutward);
						saveActualIODetailsStmt.setString(
							4,
							budgetSubHeadTitle);
						//Budget Sub Head
						saveActualIODetailsStmt.setDouble(5, budgetAmount);
						//Budget Amount
						saveActualIODetailsStmt.setDate(
							6,
							java.sql.Date.valueOf(
								DateHelper.stringToSQLdate(dateOfFlow)));
						//dateOfFlow
						saveActualIODetailsStmt.setString(7, inwardOrOutward);
						saveActualIODetailsStmt.setString(8, modifiedByUser);
						saveActualIODetailsStmt.registerOutParameter(
							9,
							java.sql.Types.VARCHAR);

						saveActualIODetailsStmt.execute();
						status = saveActualIODetailsStmt.getInt(1);
						errorCode = saveActualIODetailsStmt.getString(9);
						if (status == Constants.FUNCTION_FAILURE) {
							saveActualIODetailsStmt.close();
							saveActualIODetailsStmt = null;

							connection.rollback();

							Log.log(
								Log.ERROR,
								"IFDAO",
								"saveActualInflowOutflowDetails",
								errorCode);
							throw new DatabaseException(errorCode);
						}
						saveActualIODetailsStmt.close();
						saveActualIODetailsStmt = null;

						Log.log(
							Log.DEBUG,
							"IFDAO",
							"saveActualInflowOutflowDetails",
							"ErrorCode and Error " + status + ", " + errorCode);
					} // end of for
				} else if (
					term.equals(TERM_SHORT)
						&& (actualIOSubHeadDetails == null)) {
					Log.log(
						Log.DEBUG,
						"IFDAO",
						"saveActualInflowOutflowDetails",
						"Short Term.. No sub heads ");

					saveActualIODetailsStmt =
						connection.prepareCall(
							"{? = call funcInsertIOflowDetail(?,?,?,?,?,?,?,?)}");

					saveActualIODetailsStmt.registerOutParameter(
						1,
						java.sql.Types.INTEGER);
					saveActualIODetailsStmt.setString(2, budgetHeadTitle);
					//Budget Head
					saveActualIODetailsStmt.setString(3, inwardOrOutward);
					saveActualIODetailsStmt.setString(4, null);
					//Budget Sub Head
					saveActualIODetailsStmt.setDouble(5, budgetAmount);
					//Budget Amount
					saveActualIODetailsStmt.setDate(
						6,
						java.sql.Date.valueOf(
							DateHelper.stringToSQLdate(dateOfFlow)));
					saveActualIODetailsStmt.setString(7, inwardOrOutward);
					saveActualIODetailsStmt.setString(8, modifiedByUser);
					saveActualIODetailsStmt.registerOutParameter(
						9,
						java.sql.Types.VARCHAR);

					saveActualIODetailsStmt.execute();
					status = saveActualIODetailsStmt.getInt(1);
					errorCode = saveActualIODetailsStmt.getString(9);
					if (status == Constants.FUNCTION_FAILURE) {
						saveActualIODetailsStmt.close();
						saveActualIODetailsStmt = null;

						connection.rollback();

						Log.log(
							Log.ERROR,
							"IFDAO",
							"saveActualInflowOutflowDetails",
							errorCode);
						throw new DatabaseException(errorCode);
					}
					saveActualIODetailsStmt.close();
					saveActualIODetailsStmt = null;

					Log.log(
						Log.DEBUG,
						"IFDAO",
						"saveActualInflowOutflowDetails",
						"ErrorCode and Error " + status + ", " + errorCode);
				} else if (
					term.equals(TERM_SHORT)
						&& (actualIOSubHeadDetails != null)) {
					Log.log(
						Log.DEBUG,
						"IFDAO",
						"saveActualInflowOutflowDetails",
						"Short Term.. sub heads ");

					for (int j = 0; j < actualIOSubHeadDetails.size(); j++) {
						actualIOSubHeadDetail =
							(ActualIOSubHeadDetail) actualIOSubHeadDetails.get(
								i);
						budgetSubHeadTitle =
							actualIOSubHeadDetail.getSubHeadTitle();
						budgetAmount = actualIOSubHeadDetail.getBudgetAmount();

						saveActualIODetailsStmt =
							connection.prepareCall(
								"{? = call funcInsertIOflowDetail(?,?,?,?,?,?,?,?)}");

						saveActualIODetailsStmt.registerOutParameter(
							1,
							java.sql.Types.INTEGER);
						saveActualIODetailsStmt.setString(2, budgetHeadTitle);
						// Budget Head
						saveActualIODetailsStmt.setString(3, inwardOrOutward);
						saveActualIODetailsStmt.setString(
							4,
							budgetSubHeadTitle);
						// Budget Sub Head
						saveActualIODetailsStmt.setDouble(5, budgetAmount);
						//Budget Amount
						saveActualIODetailsStmt.setDate(
							6,
							java.sql.Date.valueOf(
								DateHelper.stringToSQLdate(dateOfFlow)));
						saveActualIODetailsStmt.setString(7, inwardOrOutward);
						saveActualIODetailsStmt.setString(8, modifiedByUser);
						saveActualIODetailsStmt.registerOutParameter(
							9,
							java.sql.Types.VARCHAR);

						saveActualIODetailsStmt.execute();
						status = saveActualIODetailsStmt.getInt(1);
						errorCode = saveActualIODetailsStmt.getString(9);

						Log.log(
							Log.DEBUG,
							"IFDAO",
							"saveActualInflowOutflowDetails",
							"Error code and error " + status + " " + errorCode);

						if (status == Constants.FUNCTION_FAILURE) {
							saveActualIODetailsStmt.close();
							saveActualIODetailsStmt = null;

							connection.rollback();

							Log.log(
								Log.ERROR,
								"IFDAO",
								"saveActualInflowOutflowDetails",
								errorCode);
							throw new DatabaseException(errorCode);
						}
						saveActualIODetailsStmt.close();
						saveActualIODetailsStmt = null;

					} // end of for
				} // end of else
			} // end of for block

			connection.commit();
		} catch (SQLException e) {
			Log.log(
				Log.ERROR,
				"IFDAO",
				"saveActualInflowOutflowDetails",
				e.getMessage());
			Log.logException(e);

			try {
				connection.rollback();
			} catch (SQLException e1) {
			}
			throw new DatabaseException("Unable to save ActualInflowOutflowDetails");
		} finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO, "IFDAO", "saveActualInflowOutflowDetails", "Exited");
	}

	/**
	* This method returns the parameters for calculating Exposure for
	* investment in a particular investee.
	*/
	/*   public HashMap calculateExposure(String investee) throws DatabaseException
	{
	return null;
	}*/

	/**
	* This method gets all the Budget Heads for the given budget category (Inflow or Outflow) from the Budget_HEAD master table.
	*
	* The return type is a vector.
	*/
	public Vector getBudgetHeadTitles(String ioFlag) throws DatabaseException {
		Log.log(Log.INFO, "IFDAO", "getBudgetHeadTitles", "Entered");

		Connection conn = null;
		CallableStatement callableStmt = null;
		Vector vec = new Vector();
		ResultSet resultset = null;
		int status = -1;
		String errorCode = null;

		try {
			Log.log(Log.INFO, "IFDAO", "getBudgetHeadTitles", "flag " + ioFlag);

			conn = DBConnection.getConnection();
			callableStmt =
				conn.prepareCall(
					"{?=call packGetAllBudgetHeads.funcGetAllBudgetHeads(?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt.setString(2, ioFlag);
			callableStmt.registerOutParameter(3, Constants.CURSOR);
			callableStmt.registerOutParameter(4, java.sql.Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(4);
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"getBudgetHeadTitles",
				"Error code and error " + status + " " + errorCode);
			if (status == Constants.FUNCTION_FAILURE) {
				callableStmt.close();
				callableStmt = null;
				Log.log(Log.ERROR, "IFDAO", "getBudgetHeadTitles", errorCode);
				throw new DatabaseException(errorCode);
			}
			resultset = (ResultSet) callableStmt.getObject(3);
			while (resultset.next()) {
				String budgetHead = resultset.getString(1);
				Log.log(
					Log.INFO,
					"IFDAO",
					"getBudgetHeadTitles",
					"budgetHead " + budgetHead);
				vec.addElement(budgetHead);
			}
			resultset.close();
			resultset = null;
			callableStmt.close();
			callableStmt = null;
		} catch (SQLException e) {
			Log.log(Log.ERROR, "IFDAO", "getBudgetHeadTitles", e.getMessage());
			Log.logException(e);
			throw new DatabaseException("Unable to get BudgetHeadTitles");
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(Log.INFO, "IFDAO", "getBudgetHeadTitles", "Exited");

		return vec;
	}

	/**
	 * @param budgetHead	The budget head for which Budget sub heads are required.
	 * @param flag		The flag indicates whether the head belong to inflow or outflow.
	 * @return			Vector of Sub Heads.
	 * @throws DatabaseException
	 * This method gets all the Budget Sub Heads from the Budget_Sub_HEAD master
	 * table. The return type is a vector object.
	*/
	public Vector getBudgetSubHeadTitles(String budgetHead, String flag)
		throws DatabaseException {
		Connection connection = null;
		CallableStatement getBudgetSubHeadStmt = null;
		Vector budgetSubHeads = new Vector();
		ResultSet rsBudgetSubHeads = null;
		int getBudgetSubHeadsStatus = 0;
		String getBudgetSubHeadsErr = "";

		try {
			connection = DBConnection.getConnection();
			getBudgetSubHeadStmt =
				connection.prepareCall(
					"{?=call packGetAllBudgetSubHeads.funcGetAllBudgetSubHeads(?,?,?,?)}");
			getBudgetSubHeadStmt.registerOutParameter(
				1,
				java.sql.Types.INTEGER);
			getBudgetSubHeadStmt.setString(2, budgetHead);
			getBudgetSubHeadStmt.setString(3, flag);
			getBudgetSubHeadStmt.registerOutParameter(4, Constants.CURSOR);
			getBudgetSubHeadStmt.registerOutParameter(
				5,
				java.sql.Types.VARCHAR);
			getBudgetSubHeadStmt.execute();
			getBudgetSubHeadsStatus = getBudgetSubHeadStmt.getInt(1);

			/*
			If the status is 0, the stored procedure is executed successfully, fetch the resultset
			and populate the vector with the budget sub heads.
			Else get the error message and throw an database exception.
			*/
			if (getBudgetSubHeadsStatus == 0) {
				rsBudgetSubHeads =
					(ResultSet) getBudgetSubHeadStmt.getObject(4);
				while (rsBudgetSubHeads.next()) {
					budgetSubHeads.addElement(rsBudgetSubHeads.getString(1));
				}
				rsBudgetSubHeads.close();
				rsBudgetSubHeads = null;
			} else {
				getBudgetSubHeadsErr = getBudgetSubHeadStmt.getString(5);
				Log.log(
					Log.DEBUG,
					"IFDAO",
					"getBudgetSubHeadTitles",
					"Error code and error "
						+ getBudgetSubHeadsStatus
						+ " "
						+ getBudgetSubHeadsErr);
				getBudgetSubHeadStmt.close();
				getBudgetSubHeadStmt = null;
				Log.log(
					Log.ERROR,
					"IFDAO",
					"getBudgetSubHeadTitles",
					getBudgetSubHeadsErr);
				throw new DatabaseException(getBudgetSubHeadsErr);
			}
			getBudgetSubHeadStmt.close();
			getBudgetSubHeadStmt = null;
		} catch (SQLException e) {
			Log.log(
				Log.ERROR,
				"IFDAO",
				"getBudgetSubHeadTitles",
				e.getMessage());
			Log.logException(e);
			throw new DatabaseException("Unable to get BudgetSubHeadTitles");
		} finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO, "IFDAO", "getBudgetSubHeadTitles", "Exited");
		return budgetSubHeads;
	}

	/**
	* This method will get all the instrument features from the instrument_feature
	* master table. The return type is a Vector.
	*/
	/*   public Vector getInstrumentFeatures() throws DatabaseException
	{
	Connection connection=null;
	CallableStatement getInstFeatureStmt=null;
	Vector instFeatures=new Vector();
	ResultSet rsInstFeatures=null;
	int getInstFeatureStatus=0;
	String getInstFeatureErr="";

	try
	{
	connection=getDbConnection();
	getInstFeatureStmt=connection.prepareCall("{?=call packGetAllInstrumentFeature.funcGetAllInstrumentFeature(?,?)}");

	getInstFeatureStmt.registerOutParameter(1,java.sql.Types.INTEGER);
	getInstFeatureStmt.registerOutParameter(2,Constants.CURSOR);
	getInstFeatureStmt.registerOutParameter(3,java.sql.Types.VARCHAR);

	getInstFeatureStmt.execute();
	getInstFeatureStatus=getInstFeatureStmt.getInt(1);

	/*
	If the status is 0, the stored procedure is executed successfully, fetch the resultset
	and populate the vector with the instrument features.
	Else get the error message and throw an database exception.
	*/
	/*			if (getInstFeatureStatus==0)
	{
	rsInstFeatures=(ResultSet) getInstFeatureStmt.getObject(2);
	while (rsInstFeatures.next())
	{
	instFeatures.addElement(rsInstFeatures.getString(1));
	}
	}
	else
	{
	getInstFeatureErr=getInstFeatureStmt.getString(3);
	throw new DatabaseException(getInstFeatureErr);
	}
	}
	catch (Exception exception)
	{
	throw new DatabaseException(exception.getMessage());
	}

	return instFeatures;
	}*/

	/**
	* This method returns all the Instruments in the instrument_master table.
	* The return type is a vector.
	*/
	/*   public Vector getInstruments() throws DatabaseException
	{
	Connection connection=null;
	CallableStatement getInstStmt=null;
	Vector instruments=new Vector();
	ResultSet rsInstruments=null;
	int getInstStatus=0;
	String getInstErr="";

	try
	{
	connection=getDbConnection();
	getInstStmt=connection.prepareCall("{?=call packInsTyp.funcGetInsType(?,?)}");

	getInstStmt.registerOutParameter(1,java.sql.Types.INTEGER);
	getInstStmt.registerOutParameter(2,Constants.CURSOR);
	getInstStmt.registerOutParameter(3,java.sql.Types.VARCHAR);

	getInstStmt.execute();
	getInstStatus=getInstStmt.getInt(1);

	/*
	If the status is 0, the stored procedure is executed successfully, fetch the resultset
	and populate the vector with the instruments.
	Else get the error message and throw an database exception.
	*/
	/*			if (getInstStatus==0)
	{
	rsInstruments=(ResultSet) getInstStmt.getObject(2);
	while (rsInstruments.next())
	{
	instruments.addElement(rsInstruments.getString(1));
	}
	}
	else
	{
	getInstErr=getInstStmt.getString(3);
	throw new DatabaseException(getInstErr);
	}
	}
	catch (Exception exception)
	{
	throw new DatabaseException(exception.getMessage());
	}

	return instruments;
	}*/

	/**
	* This method will return all the Investee Names from the investee master table.
	* The return type is a Vector.
	*/
	/*   public Vector getInvestees() throws DatabaseException
	{
	Connection connection=null;
	CallableStatement getInvesteesStmt=null;
	Vector investees=new Vector();
	ResultSet rsInvestees=null;
	int getInvesteesStatus=0;
	String getInvesteesErr="";

	try
	{
	connection=getDbConnection();
	getInvesteesStmt=connection.prepareCall("{?=call packGetAllInvestees.funcGetAllInvestees(?,?)}");
	getInvesteesStmt.registerOutParameter(1,java.sql.Types.INTEGER);
	getInvesteesStmt.registerOutParameter(2,Constants.CURSOR);
	getInvesteesStmt.registerOutParameter(3,java.sql.Types.VARCHAR);

	getInvesteesStmt.execute();
	getInvesteesStatus=getInvesteesStmt.getInt(1);

	/*
	If the status is 0, the stored procedure is executed successfully, fetch the resultset
	and populate the vector with the investees.
	Else get the error message and throw an database exception.
	*/
	/*			if (getInvesteesStatus==0)
	{
	rsInvestees=(ResultSet) getInvesteesStmt.getObject(2);
	while (rsInvestees.next())
	{
	investees.addElement(rsInvestees.getString(1));
	}
	}
	else
	{
	getInvesteesErr=getInvesteesStmt.getString(3);
	throw new DatabaseException(getInvesteesErr);
	}
	}
	catch (Exception exception)
	{
	throw new DatabaseException(exception.getMessage());
	}

	return investees;
	}*/

	/**
	* @param sInvestmentDetailId
	* @return com.cgtsi.investmentfund.Instrument
	*/
	/*   public HashMap getInvestmentDetail(String sInvestmentDetailId) throws DatabaseException
	{
	return null;
	}*/

	/**
	* This method returns all the maturity info from the maturity_master table. The return
	* type is a Vector.
	*/
	/*   public Vector getMaturities() throws DatabaseException
	{
	Connection conn = null;
	CallableStatement callableStmt = null;
	Vector vec = new Vector();
	ResultSet resultset = null;
	int status = -1;
	String errorCode = null;
	try
	{
	conn = getDbConnection();
	callableStmt = conn.prepareCall("{?=call packGetAllMaturities.funcGetAllMaturities(?,?)}");
	callableStmt.registerOutParameter(1,java.sql.Types.INTEGER);
	callableStmt.registerOutParameter(2,Constants.CURSOR);
	callableStmt.registerOutParameter(3,java.sql.Types.VARCHAR);

	// Executing the Stored Procedure
	callableStmt.execute();
	status = callableStmt.getInt(1);
	// //System.out.println("Status of Stored Procedure execution is :" + status );
	errorCode = callableStmt.getString(3);
	// //System.out.println("Error Code from Stored Procedure execution is :" + errorCode );
	resultset = (ResultSet)callableStmt.getObject(2);

	errorCode = callableStmt.getString(3);

	while(resultset.next())
	{
	String mat_type = resultset.getString(1);
	//System.out.println("The maturity type is :" + mat_type);
	vec.addElement(mat_type);
	}
	}
	catch(Exception e)
	{
	throw new DatabaseException(e.getMessage());
	}

	return vec;
	}*/

	/**
	* This method gets all the bank accounts from the bank_account_master table. The
	* return type for this method is a Vector.
	*/
	   public ArrayList getBankAccounts() throws DatabaseException
	{
	Connection conn = null;
	CallableStatement callableStmt = null;
	ArrayList bankDetails = new ArrayList();
	ResultSet resultset = null;
	int status = -1;
	String errorCode = null;
	
	BankAccountDetail bankAccountDetail = new BankAccountDetail();

		try
		{	
			conn = DBConnection.getConnection();
			callableStmt = conn.prepareCall("{?=call packGetAllAccountBanks.funcGetAllAccountBanks(?,?)}");
			callableStmt.registerOutParameter(1,java.sql.Types.INTEGER);
			callableStmt.registerOutParameter(2,Constants.CURSOR);
			callableStmt.registerOutParameter(3,java.sql.Types.VARCHAR);
		
			// Executing the Stored Procedure
			callableStmt.execute();
			status = callableStmt.getInt(1);
			
			errorCode = callableStmt.getString(3);
			
			if(status==Constants.FUNCTION_FAILURE)
			{				
	
				callableStmt.close();
				callableStmt=null;
	
			   conn.rollback();
	
			   Log.log(Log.ERROR,"IFDAO","getBankAccounts","getBankAccounts Exception" + errorCode);
			   throw new DatabaseException(errorCode);
				
				
			}
			else{
				resultset = (ResultSet)callableStmt.getObject(2);
		
					while(resultset.next())
					{
						bankAccountDetail = new BankAccountDetail();
						bankAccountDetail.setBankName(resultset.getString(1));
						Log.log(Log.INFO,"IFDAO","getBankAccounts","resultset.getString(1) :" + resultset.getString(1));
						bankAccountDetail.setBankBranchName(resultset.getString(2));
						Log.log(Log.INFO,"IFDAO","getBankAccounts","resultset.getString(2) :" + resultset.getString(2));
						bankAccountDetail.setAccountNumber(resultset.getString(3));
						bankAccountDetail.setAccountType(resultset.getString(4));
					
						bankDetails.add(bankAccountDetail);
					}
				
			}
		
		}
		catch(Exception e)
		{
		throw new DatabaseException(e.getMessage());
		}finally {
			DBConnection.freeConnection(conn);
		}
		

	return bankDetails;
	}

	/**
	* This method creates a database connection that can be used to perform database operations.
	* It throws a DatabaseException if there is any error in establishing a connection
	*
	* @throws DatabaseException
	* @return java.sql.Connection
	*/
	/*   private Connection getDbConnection() throws DatabaseException
	{
	String driver="oracle.jdbc.driver.OracleDriver";
	String dbURL="jdbc:oracle:thin:@cprbbfs479:1521:satyam";
	Connection connection;

	try
	{
	Class.forName(driver);
	connection=DriverManager.getConnection(dbURL,"cgtsiadmin","cgtsiadmin");
	}
	catch (Exception exception)
	{
	DatabaseException databaseException=new DatabaseException(exception.getMessage());
	throw databaseException;
	}

	return connection;
	}*/

	/*	public static void main(String args[])
	{
	IFDAO ifDAO=new IFDAO();
	Vector investees=new Vector();
	Vector instruments = new Vector();
	Vector instFeatures=new Vector();
	Vector budgetSubHeads=new Vector();
	FDDetail fdDetail=new FDDetail();
	CommercialPaperDetail commercialPaperDetail=new CommercialPaperDetail();
	MutualFundDetail mutualFundDetail=new MutualFundDetail();
	GovtSecurityDetail govtSecurityDetail=new GovtSecurityDetail();
	BondsDetail bondsDetail=new BondsDetail();
	DebentureDetail debentureDetail=new DebentureDetail();

	try
	{
	investees=ifDAO.getInvestees();
	//System.out.println("Investees:" + investees);

	instruments=ifDAO.getInstruments();
	//System.out.println("Instruments: " + instruments);

	instFeatures=ifDAO.getInstrumentFeatures();
	//System.out.println("Instrument Features: " + instFeatures);

	budgetSubHeads=ifDAO.getBudgetSubHeadTitles("Corpus Contribution");
	//System.out.println("Budget Sub Head Titles: " + budgetSubHeads);

	fdDetail.setInvesteeName("ICICI");
	fdDetail.setInstrumentName("FD");
	fdDetail.setInvestmentName("ICICI");
	fdDetail.setPrincipalAmount(12345.00);
	fdDetail.setCompoundingFrequency(2);
	fdDetail.setInterestRate(12.5);
	fdDetail.setTenure(5);
	fdDetail.setTenureType("Y");
	fdDetail.setFdReceiptNumber("FD123456");
	fdDetail.setDateOfDeposit("14/10/2003");
	fdDetail.setMaturityName("ShortTerm");

	ifDAO.saveInvestmentDetail(fdDetail);

	commercialPaperDetail.setInvesteeName("ICICI");
	commercialPaperDetail.setInstrumentName("Commercial Paper");
	commercialPaperDetail.setInvestmentName("ICICI");
	commercialPaperDetail.setCommercialPaperNumber("CP-SATYAM-00001");
	commercialPaperDetail.setNameOfCompany("SATYAM");
	commercialPaperDetail.setFaceValue(123456.00);
	commercialPaperDetail.setNoOfCommercialPapers(10);
	commercialPaperDetail.setCostOfPurchase(10000.00);
	commercialPaperDetail.setCouponRate(10.0);
	commercialPaperDetail.setTenure(2);
	commercialPaperDetail.setDateOfInvestment("14/10/2003");
	commercialPaperDetail.setTenureType("M");
	commercialPaperDetail.setMaturityName("ShortTerm");
	commercialPaperDetail.setCallOrPutOption("C");
	commercialPaperDetail.setCallPutDuration(1);

	ifDAO.saveInvestmentDetail(commercialPaperDetail);

	mutualFundDetail.setInvesteeName("ICICI");
	mutualFundDetail.setInstrumentName("Mutual Funds");
	mutualFundDetail.setInvestmentName("ICICI Prudential");
	mutualFundDetail.setMutualFundId("MFICICI00034");
	mutualFundDetail.setParValue(123456.00);
	mutualFundDetail.setCostOfPurchase(9876.00);
	mutualFundDetail.setDateOfPurchase("12/11/2001");
	mutualFundDetail.setNavAsOnDateOfPurchase(123.00);
	mutualFundDetail.setNavAsOnDate(345.00);
	mutualFundDetail.setNumberOfUnits(10);
	mutualFundDetail.setIsinNumber("ISIN001");
	mutualFundDetail.setMutualFundName("Prudential Mutual Fund");
	mutualFundDetail.setOpenOrClose("O");
	mutualFundDetail.setSchemeNature("Growth");
	mutualFundDetail.setExitLoad(1200.00);
	mutualFundDetail.setEntryLoad(1500.00);
	mutualFundDetail.setMarkToMarket("abcde");
	mutualFundDetail.setDateOfSelling("12/12/2002");
	mutualFundDetail.setMaturityName("ShortTerm");

	ifDAO.saveInvestmentDetail(mutualFundDetail);

	govtSecurityDetail.setInvesteeName("ICICI");
	govtSecurityDetail.setInstrumentName("Government Securities");
	govtSecurityDetail.setInvestmentName("Tamil Nadu Govt");
	govtSecurityDetail.setSeriesName("SecuritySeries");
	govtSecurityDetail.setFaceValue(123456.00);
	govtSecurityDetail.setNumberOfSecurities(20);
	govtSecurityDetail.setFolioNumber("FolioNumber");
	govtSecurityDetail.setCertificateNumber("CertificateNumber");
	govtSecurityDetail.setCostOfPurchase(12300.00);
	govtSecurityDetail.setCouponRate(12.5);
	govtSecurityDetail.setTenure(5);
	govtSecurityDetail.setTenureType("Y");
	govtSecurityDetail.setDateOfInvestment("23/12/2000");
	govtSecurityDetail.setCallOrPutOption("C");
	govtSecurityDetail.setCallOrPutDuration(3);
	govtSecurityDetail.setMaturityName("ShortTerm");

	ifDAO.saveInvestmentDetail(govtSecurityDetail);

	bondsDetail.setInvesteeName("ICICI");
	bondsDetail.setInstrumentName("Bond");
	bondsDetail.setInvestmentName("Tamil Nadu Govt");
	bondsDetail.setBondName("Bond Name");
	bondsDetail.setFaceValue(123456.00);
	bondsDetail.setNoOfSecurities(25);
	bondsDetail.setFolioNumber("BondFolioNumber");
	bondsDetail.setCertificateNumber("CertificateNumber");
	bondsDetail.setCostOfPurchase(12350.00);
	bondsDetail.setCouponRate(13);
	bondsDetail.setTenure(2);
	bondsDetail.setTenureType("M");
	bondsDetail.setMaturityName("ShortTerm");
	bondsDetail.setDateOfInvestment("14/10/2003");
	bondsDetail.setCallOrPutOption("P");
	bondsDetail.setCallOrPutDuration(5);

	ifDAO.saveInvestmentDetail(bondsDetail);

	debentureDetail.setInvesteeName("ICICI");
	debentureDetail.setInstrumentName("Debentures");
	debentureDetail.setInvestmentName("Tamil Nadu Govt");
	debentureDetail.setDebentureName("Debenture Name");
	debentureDetail.setFaceValue(123456789.00);
	debentureDetail.setNumberOfSecurities(25);
	debentureDetail.setFolioNumber("DebentureFolioNumber");
	debentureDetail.setCertificateNumber("CertificateNumber");
	debentureDetail.setCostOfPurchase(1234000.0);
	debentureDetail.setCouponRate(15.5);
	debentureDetail.setTenure(5);
	debentureDetail.setTenureType("M");
	debentureDetail.setMaturityName("ShortTerm");
	debentureDetail.setDateOfInvestment("15/10/2003");
	debentureDetail.setCallOrPutOption("C");
	debentureDetail.setCallOrPutDuration(5);

	ifDAO.saveInvestmentDetail(debentureDetail);
	}
	catch (DatabaseException dbException)
	{
	//System.out.println("Main "+ dbException.getMessage());
	}
	}*/

	/**
	* This method gets all the Investee Names  from the investee master table.
	*
	* The return type is a vector.
	*/

	public ArrayList getAllInvesteeNames() throws DatabaseException {
		Connection conn = null;
		CallableStatement callableStmt = null;
		ArrayList investeeNames = new ArrayList();
		ResultSet resultset = null;
		int status = -1;
		String errorCode = null;

		try {
			conn = DBConnection.getConnection();
			callableStmt =
				conn.prepareCall(
					"{?=call packGetAllInvestees.funcGetAllInvestees(?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt.registerOutParameter(2, Constants.CURSOR);
			callableStmt.registerOutParameter(3, java.sql.Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(3);
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"getAllInvesteeNames",
				"Error code and error " + status + " " + errorCode);
			if (status == Constants.FUNCTION_FAILURE) {
				callableStmt.close();
				callableStmt = null;
				Log.log(Log.ERROR, "IFDAO", "getAllActiveUsers", errorCode);
				throw new DatabaseException(errorCode);
			}
			resultset = (ResultSet) callableStmt.getObject(2);
			while (resultset.next()) {
				investeeNames.add(resultset.getString(1));
			}
			resultset.close();
			resultset = null;
			callableStmt.close();
			callableStmt = null;
		} catch (SQLException e) {
			Log.log(Log.ERROR, "IFDAO", "getAllInvesteeNames", e.getMessage());
			Log.logException(e);
			throw new DatabaseException("Unable to get all Investees");
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(Log.INFO, "IFDAO", "getAllInvesteeNames", "Exited");
		return investeeNames;
	}

	public ArrayList getAllInvesteeNamesForGroup(String groupName)
		throws DatabaseException {
		Connection conn = null;
		CallableStatement callableStmt = null;
		ArrayList investeeNames = new ArrayList();
		ResultSet resultset = null;
		int status = -1;
		String errorCode = null;

		try {
			conn = DBConnection.getConnection();
			callableStmt =
				conn.prepareCall(
					"{?=call packGetAllInvestees.funcGetInvforGroup(?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt.setString(2, groupName);
			callableStmt.registerOutParameter(3, Constants.CURSOR);
			callableStmt.registerOutParameter(4, java.sql.Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(4);
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"getAllInvesteeNamesForGroup",
				"Error code and error " + status + " " + errorCode);
			if (status == Constants.FUNCTION_FAILURE) {
				callableStmt.close();
				callableStmt = null;
				Log.log(
					Log.ERROR,
					"IFDAO",
					"getAllInvesteeNamesForGroup",
					errorCode);
				throw new DatabaseException(errorCode);
			}
			resultset = (ResultSet) callableStmt.getObject(3);
			while (resultset.next()) {
				investeeNames.add(resultset.getString(1));
			}
			resultset.close();
			resultset = null;
			callableStmt.close();
			callableStmt = null;
		} catch (SQLException e) {
			Log.log(
				Log.ERROR,
				"IFDAO",
				"getAllInvesteeNamesForGroup",
				e.getMessage());
			Log.logException(e);
			throw new DatabaseException("Unable to get all Investees");
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(Log.INFO, "IFDAO", "getAllInvesteeNamesForGroup", "Exited");
		return investeeNames;
	}
	/**
	* This method gets all the Ratings from the Rating master table.
	*
	* The return type is a ArrayList.
	*/

	public ArrayList getAllRatings() throws DatabaseException {
		Connection conn = null;
		CallableStatement callableStmt = null;
		ArrayList ratings = new ArrayList();
		ResultSet resultset = null;
		int status = -1;
		String errorCode = null;

		try {
			conn = DBConnection.getConnection();
			callableStmt =
				conn.prepareCall(
					"{?=call packGetAllRatings.funcGetAllRatings(?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt.registerOutParameter(2, Constants.CURSOR);
			callableStmt.registerOutParameter(3, java.sql.Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(3);
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"getAllRatings",
				"Error code and error " + status + " " + errorCode);
			if (status == Constants.FUNCTION_FAILURE) {
				callableStmt.close();
				callableStmt = null;
				Log.log(Log.ERROR, "IFDAO", "getAllRatings", errorCode);
				throw new DatabaseException(errorCode);
			}
			resultset = (ResultSet) callableStmt.getObject(2);
			while (resultset.next()) {
				ratings.add(resultset.getString(1));
			}
			resultset.close();
			resultset = null;
			callableStmt.close();
			callableStmt = null;
		} catch (SQLException e) {
			Log.log(Log.ERROR, "IFDAO", "getAllRatings", e.getMessage());
			Log.logException(e);
			throw new DatabaseException("Unable to get all Ratings");
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(Log.INFO, "IFDAO", "getAllRatings", "Exited");
		return ratings;
	}

	/**
	* This method gets all the InstrumentTypes  from the Instrument master table.
	*
	* The return type is a ArrayList .
	*/
	public ArrayList getInstrumentTypes(String flag) throws DatabaseException {
		Connection conn = null;
		CallableStatement callableStmt = null;
		ArrayList instrumentTypes = new ArrayList();
		ResultSet resultset = null;
		int status = -1;
		String errorCode = null;

		try {
			conn = DBConnection.getConnection();
			callableStmt =
				conn.prepareCall(
					"{?=call packGetInsInstrType.funcGetInstrType(?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt.setString(2, flag);
			callableStmt.registerOutParameter(3, Constants.CURSOR);
			callableStmt.registerOutParameter(4, java.sql.Types.VARCHAR);
			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(4);
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"getInstrumentTypes",
				"Error code and error " + status + " " + errorCode);

			if (status == Constants.FUNCTION_FAILURE) {
				callableStmt.close();
				callableStmt = null;
				Log.log(Log.ERROR, "IFDAO", "getInstrumentTypes", errorCode);
				throw new DatabaseException(errorCode);
			}
			resultset = (ResultSet) callableStmt.getObject(3);
			while (resultset.next()) {
				instrumentTypes.add(resultset.getString(1));
			}
			resultset.close();
			resultset = null;
			callableStmt.close();
			callableStmt = null;
		} catch (SQLException e) {
			Log.log(Log.ERROR, "IFDAO", "getInstrumentTypes", e.getMessage());
			Log.logException(e);
			throw new DatabaseException("Unable to get InstrumentTypes");
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(Log.INFO, "IFDAO", "getInstrumentTypes", "Exited");
		return instrumentTypes;
	}

	/**
	* This method gets all the BudgetHeadTypes  from the budgethead master table.
	*
	* The return type is a ArrayList .
	*/
	public ArrayList getBudgetHeadTypes(String headType)
		throws DatabaseException {
		Connection conn = null;
		CallableStatement callableStmt = null;
		ArrayList budgetHeadTypes = new ArrayList();
		ResultSet resultset = null;
		int status = -1;
		String errorCode = null;

		try {
			conn = DBConnection.getConnection();
			callableStmt =
				conn.prepareCall(
					"{?=call packGetAllBudgetHeads.funcGetAllBudgetHeads(?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt.setString(2, headType);
			//IO flag to be removed to get all the heads
			callableStmt.registerOutParameter(3, Constants.CURSOR);
			callableStmt.registerOutParameter(4, java.sql.Types.VARCHAR);
			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(4);
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"getBudgetHeadTypes",
				"Error code and error " + status + " " + errorCode);
			if (status == Constants.FUNCTION_FAILURE) {
				callableStmt.close();
				callableStmt = null;
				Log.log(Log.ERROR, "IFDAO", "getBudgetHeadTypes", errorCode);
				throw new DatabaseException(errorCode);
			}
			resultset = (ResultSet) callableStmt.getObject(3);
			while (resultset.next()) {
				budgetHeadTypes.add(resultset.getString(1));
			}
			resultset.close();
			resultset = null;
			callableStmt.close();
			callableStmt = null;
		} catch (SQLException e) {
			Log.log(Log.ERROR, "IFDAO", "getBudgetHeadTypes", e.getMessage());
			Log.logException(e);
			throw new DatabaseException("Unable to get InstrumentTypes");
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(Log.INFO, "IFDAO", "getBudgetHeadTypes", "Exited");
		return budgetHeadTypes;
	}

	/**
	* This method gets all the Instrument Scheme nature  from the Instrumentscheme table.
	*
	* The return type is a ArrayList .
	*/
	public ArrayList getInstrumentSchemeTypes(String instName) throws DatabaseException {
		Connection conn = null;
		CallableStatement callableStmt = null;
		ArrayList instrumentSchemeTypes = new ArrayList();
		ResultSet resultset = null;
		int status = -1;
		String errorCode = null;

		try {
			conn = DBConnection.getConnection();
			callableStmt =
				conn.prepareCall(
					"{?=call packGetInstrumentsWithScheme.funcGetInstrumentsWithScheme(?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt.setString(2, instName);
			callableStmt.registerOutParameter(3, Constants.CURSOR);
			callableStmt.registerOutParameter(4, java.sql.Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(4);
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"getInstrumentSchemeTypes",
				"Error code and error " + status + " " + errorCode);

			if (status == Constants.FUNCTION_FAILURE) {
				callableStmt.close();
				callableStmt = null;
				Log.log(
					Log.ERROR,
					"IFDAO",
					"getInstrumentSchemeTypes",
					errorCode);
				throw new DatabaseException(errorCode);
			}
			resultset = (ResultSet) callableStmt.getObject(3);
			while (resultset.next()) {
				instrumentSchemeTypes.add(resultset.getString(2));
			}
			resultset.close();
			resultset = null;
			callableStmt.close();
			callableStmt = null;
		} catch (SQLException e) {
			Log.log(
				Log.ERROR,
				"IFDAO",
				"getInstrumentSchemeTypes",
				e.getMessage());
			Log.logException(e);
			throw new DatabaseException("Unable to get InstrumentSchemeTypes");
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(Log.INFO, "IFDAO", "getInstrumentSchemeTypes", "Exited");
		return instrumentSchemeTypes;
	}

	/**
	* This method gets all the Instrument Features from the InstrumentFeature table.
	*
	* The return type is a ArrayList .
	*/
	public ArrayList getInstrumentFeatures() throws DatabaseException {
		Connection conn = null;
		CallableStatement callableStmt = null;
		ArrayList instrumentFeatures = new ArrayList();
		ResultSet resultset = null;
		int status = -1;
		String errorCode = null;

		try {
			conn = DBConnection.getConnection();
			callableStmt =
				conn.prepareCall(
					"{?=call packGetAllInstrumentFeature.funcGetAllInstrumentFeature(?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt.registerOutParameter(2, Constants.CURSOR);
			callableStmt.registerOutParameter(3, java.sql.Types.VARCHAR);
			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(3);
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"getInstrumentFeatures",
				"Error code and error " + status + " " + errorCode);

			if (status == Constants.FUNCTION_FAILURE) {
				callableStmt.close();
				callableStmt = null;
				Log.log(Log.ERROR, "IFDAO", "getInstrumentFeatures", errorCode);
				throw new DatabaseException(errorCode);
			}
			resultset = (ResultSet) callableStmt.getObject(2);
			while (resultset.next()) {
				String insFeature=(String)resultset.getString(1);
				instrumentFeatures.add(insFeature);
				Log.log(Log.DEBUG,"IFDAO","getInstrumentFeatures","ins feature " + insFeature);
			}
			resultset.close();
			resultset = null;
			callableStmt.close();
			callableStmt = null;
		} catch (SQLException e) {
			Log.log(
				Log.ERROR,
				"IFDAO",
				"getInstrumentFeatures",
				e.getMessage());
			Log.logException(e);
			throw new DatabaseException("Unable to get instrumentFeatures");
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(Log.INFO, "IFDAO", "getInstrumentFeatures", "Exited");
		return instrumentFeatures;
	}

	/**
	* This method gets all the receipt numbers/investments from the investment_detail table.
	*
	* The return type is a ArrayList .
	*/
	public ArrayList getReceiptNumbers() throws DatabaseException {
		Connection conn = null;
		CallableStatement callableStmt = null;
		ArrayList receiptNos = new ArrayList();
		ResultSet resultset = null;
		int status = -1;
		String errorCode = null;

		try {
			conn = DBConnection.getConnection();
			callableStmt =
				conn.prepareCall(
					"{?=call packGetAllReceiptNumbers.funcGetAllReceiptNumbers(?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt.registerOutParameter(2, Constants.CURSOR);
			callableStmt.registerOutParameter(3, java.sql.Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(3);
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"getReceiptNumbers",
				"Error code and error " + status + " " + errorCode);

			if (status == Constants.FUNCTION_FAILURE) {
				callableStmt.close();
				callableStmt = null;
				Log.log(Log.ERROR, "IFDAO", "getReceiptNumbers", errorCode);
				throw new DatabaseException(errorCode);
			}
			resultset = (ResultSet) callableStmt.getObject(2);
			while (resultset.next()) {
				receiptNos.add(resultset.getString(1));
			}
			resultset.close();
			resultset = null;
			callableStmt.close();
			callableStmt = null;
		} catch (SQLException e) {
			Log.log(Log.ERROR, "IFDAO", "getReceiptNumbers", e.getMessage());
			Log.logException(e);
			throw new DatabaseException("Unable to get receiptNos");
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(Log.INFO, "IFDAO", "getReceiptNumbers", "Exited");
		return receiptNos;
	}

	/**
	* This method gets available balance for the given account number from the bank_statement_detail table.
	*
	* The return type is a ArrayList .
	*/
	public String getAvailableBalance(String acctNo) throws DatabaseException {
		Connection conn = null;
		CallableStatement callableStmt = null;
		String availableBalance = new String();
		ResultSet resultset = null;
		int status = -1;
		String errorCode = null;

		try {
			conn = DBConnection.getConnection();
			callableStmt =
				conn.prepareCall("{?=call funcGetAvblBalance(?,?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt.setString(2, acctNo);
			callableStmt.registerOutParameter(3, java.sql.Types.DOUBLE);
			callableStmt.registerOutParameter(4, java.sql.Types.DOUBLE);
			callableStmt.registerOutParameter(5, java.sql.Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(5);
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"getAvailableBalance",
				"Error code and error " + status + " " + errorCode);
			if (status == Constants.FUNCTION_FAILURE) {
				callableStmt.close();
				callableStmt = null;
				Log.log(Log.ERROR, "IFDAO", "getAvailableBalance", errorCode);
				throw new DatabaseException(errorCode);
			}
			availableBalance = callableStmt.getDouble(3) + "";
			callableStmt.close();
			callableStmt = null;
		} catch (SQLException e) {
			Log.log(Log.ERROR, "IFDAO", "getAvailableBalance", e.getMessage());
			Log.logException(e);
			throw new DatabaseException("Unable to get availableBalance");
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(Log.INFO, "IFDAO", "getAvailableBalance", "Exited");
		return availableBalance;
	}

	/**
	* This method gets monthlyExpense to  the given account number from the bank_statement_detail table.
	*
	* The return type is a ArrayList .
	*/
	public String getMonthlyExpense(
		String acctNo,
		String frmDate,
		String toDate)
		throws DatabaseException {
		Connection conn = null;
		CallableStatement callableStmt = null;
		String monthlyExpense = new String();
		ResultSet resultset = null;
		int status = -1;
		String errorCode = null;

		try {
			conn = DBConnection.getConnection();
			callableStmt =
				conn.prepareCall(
					"{?=call packGetMonthlyBalance.funcGetMonthlyBalance(?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt.setString(2, acctNo);
			callableStmt.setDate(
				3,
				java.sql.Date.valueOf(DateHelper.stringToSQLdate(frmDate)));
			callableStmt.setDate(
				4,
				java.sql.Date.valueOf(DateHelper.stringToSQLdate(toDate)));
			callableStmt.registerOutParameter(5, Constants.CURSOR);
			callableStmt.registerOutParameter(6, java.sql.Types.VARCHAR);

			// Executing the Stored Procedure
			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(6);
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"getMonthlyExpense",
				"Error code and error " + status + " " + errorCode);
			if (status == Constants.FUNCTION_FAILURE) {
				callableStmt.close();
				callableStmt = null;
				Log.log(Log.ERROR, "IFDAO", "getMonthlyExpense", errorCode);
				throw new DatabaseException(errorCode);
			}
			resultset = (ResultSet) callableStmt.getObject(5);
			while (resultset.next()) {
				monthlyExpense = resultset.getString(3);
			}
			resultset.close();
			resultset = null;
			callableStmt.close();
			callableStmt = null;
		} catch (SQLException e) {
			Log.log(Log.ERROR, "IFDAO", "getMonthlyExpense", e.getMessage());
			Log.logException(e);
			throw new DatabaseException("Unable to get monthlyExpense");
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(Log.INFO, "IFDAO", "getMonthlyExpense", "Exited");
		return monthlyExpense;
	}

	/**
	* This method gets available balance for the given account number from the bank_statement_detail table.
	*
	* The return type is a ArrayList .
	*/

	public String getTodayExpense(String acctNo) throws DatabaseException {
		Connection conn = null;
		CallableStatement callableStmt = null;
		String todayExpense = new String();
		ResultSet resultset = null;
		int status = -1;
		String errorCode = null;

		try {
			conn = DBConnection.getConnection();
			callableStmt =
				conn.prepareCall(
					"{?=call packGetTodayExpense.funcGetTodayExpense(?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt.setString(2, acctNo);
			callableStmt.registerOutParameter(3, Constants.CURSOR);
			callableStmt.registerOutParameter(4, java.sql.Types.VARCHAR);

			// Executing the Stored Procedure
			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(4);
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"getTodayExpense",
				"Error code and error " + status + " " + errorCode);
			if (status == Constants.FUNCTION_FAILURE) {
				callableStmt.close();
				callableStmt = null;
				Log.log(Log.ERROR, "IFDAO", "getTodayExpense", errorCode);
				throw new DatabaseException(errorCode);
			}
			resultset = (ResultSet) callableStmt.getObject(3);
			while (resultset.next()) {
				todayExpense = resultset.getString(3);
			}
			resultset.close();
			resultset = null;
			callableStmt.close();
			callableStmt = null;
		} catch (SQLException e) {
			Log.log(Log.ERROR, "IFDAO", "getTodayExpense", e.getMessage());
			Log.logException(e);
			throw new DatabaseException("Unable to get TodayExpense");
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(Log.INFO, "IFDAO", "getTodayExpense", "Exited");
		return todayExpense;
	}

	public void saveInvesteeGroup(InvesteeGrpDetail invGrp, String userId)
		throws DatabaseException {
		Log.log(Log.INFO, "IFDAO", "saveInvesteeGroup", "Entered");

		Connection connection = DBConnection.getConnection();

		try {
			CallableStatement callable =
				connection.prepareCall("{?= call funcInsertInvGroup (?,?,?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);
			Log.log(Log.INFO,"IFDAO","saveInvesteeGroup","investee grp " + invGrp.getSIGRName());
			Log.log(Log.INFO,"IFDAO","saveInvesteeGroup","new investee grp " + invGrp.getSNewIGRName());
			Log.log(Log.INFO,"IFDAO","saveInvesteeGroup","mod investee grp " + invGrp.getSModIGRName());
			if (invGrp.getSIGRName()!=null && !invGrp.getSIGRName().equals(""))
			{
				Log.log(Log.INFO, "IFDAO", "saveInvesteeGroup", "modify");
				callable.setString(2, invGrp.getSIGRName());
				callable.setString(3, invGrp.getSModIGRName());
			}
			else
			{
				Log.log(Log.INFO, "IFDAO", "saveInvesteeGroup", "new");
				callable.setString(2, invGrp.getSNewIGRName());
				callable.setString(3, null);
			}
			callable.setString(4, userId);
			callable.registerOutParameter(5, Types.VARCHAR);

			callable.execute();

			int errorCode = callable.getInt(1);
			String error = callable.getString(5);

			Log.log(
				Log.DEBUG,
				"IFDAO",
				"saveInvesteeGroup",
				"Error code and Error " + errorCode + " , " + error);

			if (errorCode == Constants.FUNCTION_FAILURE) {
				Log.log(Log.ERROR, "IFDAO", "saveInvesteeGroup", error);
				callable.close();
				callable = null;
				throw new DatabaseException(error);
			}
			callable.close();
			callable = null;

		} catch (SQLException e) {
			Log.log(Log.ERROR, "IFDAO", "saveInvesteeGroup", e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Unable to add Investee Group ");
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(Log.INFO, "IFDAO", "saveInvesteeGroup", "Exited");
	}

	public ArrayList getInvesteeGroups() throws DatabaseException {
		Log.log(Log.INFO, "IFDAO", "getInvesteeGroups", "Entered");

		Connection connection = DBConnection.getConnection();
		ArrayList investeeGroups = new ArrayList();

		try {
			CallableStatement callable =
				connection.prepareCall(
					"{?= call packGetAllInvGroup.FUNCGETALLINVGROUP(?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);
			callable.registerOutParameter(2, Constants.CURSOR);
			callable.registerOutParameter(3, Types.VARCHAR);

			callable.execute();

			int errorCode = callable.getInt(1);
			String error = callable.getString(3);

			Log.log(
				Log.DEBUG,
				"IFDAO",
				"getInvesteeGroups",
				"Error code and Error " + errorCode + " , " + error);

			if (errorCode == Constants.FUNCTION_FAILURE) {
				Log.log(Log.ERROR, "IFDAO", "getInvesteeGroups", error);
				callable.close();
				callable = null;
				throw new DatabaseException(error);
			}

			ResultSet groups = (ResultSet) callable.getObject(2);

			while (groups.next()) {
				String group = groups.getString(1);

				Log.log(
					Log.DEBUG,
					"IFDAO",
					"getInvesteeGroups",
					"group " + group);

				investeeGroups.add(group);
			}
			groups.close();
			groups=null;
			callable.close();
			callable = null;
		} catch (SQLException e) {
			Log.log(Log.ERROR, "IFDAO", "getInvesteeGroups", e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Unable to get Investee Groups ");
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(Log.INFO, "IFDAO", "getInvesteeGroups", "Exited");
		return investeeGroups;
	}
	public HashMap getAnnualHeadDetails(
		Date annualFromDate,
		Date annualToDate,
		String flag)
		throws DatabaseException {
		HashMap inflowDetails = new HashMap();
		DecimalFormat decimalFormat = new DecimalFormat("##########.##");

		Log.log(Log.INFO, "IFDAO", "getAnnualHeadDetails", "Entered");

		Connection connection = DBConnection.getConnection();
		try {
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"getAnnualHeadDetails",
				"Annual From,To and Flag "
					+ annualFromDate
					+ ","
					+ annualToDate
					+ ","
					+ flag);

			CallableStatement callable =
				connection.prepareCall(
					"{?=call PACKGETANNUALDETAILS.FUNCGETANNHEADDTL(?,?,?,?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);

			callable.setDate(2, new java.sql.Date(annualFromDate.getTime()));

			callable.setDate(3, new java.sql.Date(annualToDate.getTime()));

			callable.setString(4, flag);
			callable.registerOutParameter(5, Constants.CURSOR);

			callable.registerOutParameter(6, Types.VARCHAR);
			callable.execute();

			int errorCode = callable.getInt(1);
			String error = callable.getString(6);

			Log.log(
				Log.DEBUG,
				"IFDAO",
				"getAnnualHeadDetails",
				"ErrorCode and Error " + errorCode + "," + error);

			if (errorCode == Constants.FUNCTION_FAILURE) {
				callable.close();
				callable = null;

				Log.log(Log.ERROR, "IFDAO", "getAnnualHeadDetails", error);

				throw new DatabaseException(error);
			}
			ResultSet results = (ResultSet) callable.getObject(5);

			while (results.next()) {
				String head = results.getString(1);
				double amount = results.getDouble(2);

				// inflowDetails.put(head, String.valueOf(amount));
				inflowDetails.put(head,decimalFormat.format(amount));

				Log.log(
					Log.DEBUG,
					"IFDAO",
					"getAnnualHeadDetails",
					"head, amount " + head + "," + amount);
			}
			results.close();
			results=null;


			callable.close();
			callable = null;
		} catch (SQLException e) {
			Log.log(Log.ERROR, "IFDAO", "getAnnualHeadDetails", e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Unable to get Head details.");
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(Log.INFO, "IFDAO", "getAnnualHeadDetails", "Exited");

		return inflowDetails;
	}

	public HashMap getAnnualSubHeadDetails(
		Date annualFromDate,
		Date annualToDate,
		String head,
		String flag)
		throws DatabaseException {
		Log.log(Log.INFO, "IFDAO", "getAnnualSubHeadDetails", "Entered");

		HashMap subHeadDetails = new HashMap();

		Connection connection = DBConnection.getConnection();

		try {
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"getAnnualSubHeadDetails",
				"From,To,head,flag "
					+ annualFromDate
					+ " , "
					+ annualToDate
					+ " ,"
					+ head
					+ ", "
					+ flag);
			CallableStatement callable =
				connection.prepareCall(
					"{?=call PACKGETANNUALDETAILS.FUNCGETANNSUBHEADDTL(?,?,?,?,?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);

			callable.setString(2, head);
			callable.setDate(3, new java.sql.Date(annualFromDate.getTime()));
			callable.setDate(4, new java.sql.Date(annualToDate.getTime()));
			callable.setString(5, flag);
			callable.registerOutParameter(6, Constants.CURSOR);

			callable.registerOutParameter(7, Types.VARCHAR);

			callable.execute();

			int errorCode = callable.getInt(1);
			String error = callable.getString(7);

			Log.log(
				Log.DEBUG,
				"IFDAO",
				"getAnnualSubHeadDetails",
				"ErrorCode and Error " + errorCode + ", " + error);

			if (errorCode == Constants.FUNCTION_FAILURE) {
				Log.log(Log.ERROR, "IFDAO", "getAnnualSubHeadDetails", error);

				callable.close();
				callable = null;

				throw new DatabaseException(error);
			}
			ResultSet results = (ResultSet) callable.getObject(6);

			while (results.next()) {
				String subHead = results.getString(2);
				String amount = results.getString(3);

				Log.log(
					Log.DEBUG,
					"IFDAO",
					"getAnnualSubHeadDetails",
					"subHead and amount " + subHead + ", " + amount);

				subHeadDetails.put(head + "_" + subHead, amount);
			}
			results.close();
			results=null;
			callable.close();
			callable = null;
		} catch (SQLException e) {
			Log.log(
				Log.ERROR,
				"IFDAO",
				"getAnnualSubHeadDetails",
				e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Unable to get Sub-Head details");
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(Log.INFO, "IFDAO", "getAnnualSubHeadDetails", "Exited");
		return subHeadDetails;
	}
	public HashMap getShortHeadDetails(String year, String month, String flag)
		throws DatabaseException {
		HashMap inflowDetails = new HashMap();

		Log.log(Log.INFO, "IFDAO", "getShortHeadDetails", "Entered");

		Connection connection = DBConnection.getConnection();
		try {
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"getShortHeadDetails",
				"Year,Month and Flag " + year + "," + month + "," + flag);

			CallableStatement callable =
				connection.prepareCall(
					"{?=call PACKGETSHORTTERMDETAILS.FUNCGETSHTHEADDTL(?,?,?,?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);

			callable.setString(2, month);
			callable.setString(3, year);
			callable.setString(4, flag);

			callable.registerOutParameter(5, Constants.CURSOR);

			callable.registerOutParameter(6, Types.VARCHAR);
			callable.execute();

			int errorCode = callable.getInt(1);
			String error = callable.getString(6);

			Log.log(
				Log.DEBUG,
				"IFDAO",
				"getShortHeadDetails",
				"ErrorCode and Error " + errorCode + "," + error);

			if (errorCode == Constants.FUNCTION_FAILURE) {
				callable.close();
				callable = null;

				Log.log(Log.ERROR, "IFDAO", "getShortHeadDetails", error);

				throw new DatabaseException(error);
			}
			ResultSet results = (ResultSet) callable.getObject(5);

			while (results.next()) {
				String head = results.getString(1);
				double amount = results.getDouble(2);

				inflowDetails.put(head, String.valueOf(amount));

				Log.log(
					Log.DEBUG,
					"IFDAO",
					"getShortHeadDetails",
					"head, amount " + head + "," + amount);
			}
			results.close();
			results=null;

			callable.close();
			callable = null;
		} catch (SQLException e) {
			Log.log(Log.ERROR, "IFDAO", "getShortHeadDetails", e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Unable to get Short Head Details.");
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(Log.INFO, "IFDAO", "getShortHeadDetails", "Exited");

		return inflowDetails;
	}
	public HashMap getShortSubHeadDetails(
		String year,
		String month,
		String head,
		String flag)
		throws DatabaseException {
		Log.log(Log.INFO, "IFDAO", "getShortSubHeadDetails", "Entered");

		HashMap subHeadDetails = new HashMap();

		Connection connection = DBConnection.getConnection();

		try {
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"getShortSubHeadDetails",
				"year,month,head,flag "
					+ year
					+ " , "
					+ month
					+ " ,"
					+ head
					+ ", "
					+ flag);

			CallableStatement callable =
				connection.prepareCall(
					"{?=call PACKGETSHORTTERMDETAILS.FUNCGETSHTSUBHEADDTL(?,?,?,?,?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);

			callable.setString(2, head);
			callable.setString(3, month);
			callable.setString(4, year);
			callable.setString(5, flag);
			callable.registerOutParameter(6, Constants.CURSOR);

			callable.registerOutParameter(7, Types.VARCHAR);

			callable.execute();

			int errorCode = callable.getInt(1);
			String error = callable.getString(7);

			Log.log(
				Log.DEBUG,
				"IFDAO",
				"getShortSubHeadDetails",
				"ErrorCode and Error " + errorCode + ", " + error);

			if (errorCode == Constants.FUNCTION_FAILURE) {
				Log.log(Log.ERROR, "IFDAO", "getShortSubHeadDetails", error);

				callable.close();
				callable = null;

				throw new DatabaseException(error);
			}
			ResultSet results = (ResultSet) callable.getObject(6);

			while (results.next()) {
				String subHead = results.getString(2);
				String amount = results.getString(3);

				Log.log(
					Log.DEBUG,
					"IFDAO",
					"getShortSubHeadDetails",
					"subHead and amount " + subHead + ", " + amount);

				subHeadDetails.put(head + "_" + subHead, amount);
			}
			results.close();
			results=null;
			callable.close();
			callable = null;
		} catch (SQLException e) {
			Log.log(
				Log.ERROR,
				"IFDAO",
				"getShortSubHeadDetails",
				e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Unable to get Short Sub-Head details");
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(Log.INFO, "IFDAO", "getShortSubHeadDetails", "Exited");
		return subHeadDetails;
	}
	public HashMap getInflowOutflowHeadDetails(Date dateOfFlow, String flag)
		throws DatabaseException {
		HashMap inflowDetails = new HashMap();

		Log.log(Log.INFO, "IFDAO", "getInflowOutflowHeadDetails", "Entered");

		Connection connection = DBConnection.getConnection();
		try {
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"getInflowOutflowHeadDetails",
				"date of Flow and Flag " + dateOfFlow + "," + flag);

			CallableStatement callable =
				connection.prepareCall(
					"{?=call PACKGETIODETAIL.FUNCGETHEADIODTL(?,?,?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);

			callable.setDate(2, new java.sql.Date(dateOfFlow.getTime()));
			callable.setString(3, flag);
			callable.registerOutParameter(4, Constants.CURSOR);
			callable.registerOutParameter(5, Types.VARCHAR);

			callable.execute();

			int errorCode = callable.getInt(1);
			String error = callable.getString(5);

			Log.log(
				Log.DEBUG,
				"IFDAO",
				"getInflowOutflowHeadDetails",
				"ErrorCode and Error " + errorCode + "," + error);

			if (errorCode == Constants.FUNCTION_FAILURE) {
				callable.close();
				callable = null;

				Log.log(Log.ERROR, "IFDAO", "getShortHeadDetails", error);

				throw new DatabaseException(error);
			}
			ResultSet results = (ResultSet) callable.getObject(4);

			while (results.next()) {
				String head = results.getString(1);
				double amount = results.getDouble(2);

				inflowDetails.put(head, String.valueOf(amount));

				Log.log(
					Log.DEBUG,
					"IFDAO",
					"getInflowOutflowHeadDetails",
					"head, amount " + head + "," + amount);
			}
			results.close();
			results=null;

			callable.close();
			callable = null;
		} catch (SQLException e) {
			Log.log(
				Log.ERROR,
				"IFDAO",
				"getInflowOutflowHeadDetails",
				e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Unable to get Inflow/Outflow Head Details.");
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(Log.INFO, "IFDAO", "getInflowOutflowHeadDetails", "Exited");

		return inflowDetails;
	}
	public HashMap getInflowOutflowSubHeadDetails(
		String head,
		Date dateOfFlow,
		String flag)
		throws DatabaseException {
		Log.log(Log.INFO, "IFDAO", "getInflowOutflowSubHeadDetails", "Entered");

		HashMap subHeadDetails = new HashMap();

		Connection connection = DBConnection.getConnection();

		try {
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"getInflowOutflowSubHeadDetails",
				"date of flow,head,flag "
					+ dateOfFlow
					+ " ,"
					+ head
					+ ", "
					+ flag);

			CallableStatement callable =
				connection.prepareCall(
					"{?=call PACKGETIODETAIL.FUNCGETSUBHEADIODTL(?,?,?,?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);
			callable.setDate(2, new java.sql.Date(dateOfFlow.getTime()));
			callable.setString(3, head);
			callable.setString(4, flag);
			callable.registerOutParameter(5, Constants.CURSOR);

			callable.registerOutParameter(6, Types.VARCHAR);

			callable.execute();

			int errorCode = callable.getInt(1);
			String error = callable.getString(6);

			Log.log(
				Log.DEBUG,
				"IFDAO",
				"getInflowOutflowSubHeadDetails",
				"ErrorCode and Error " + errorCode + ", " + error);

			if (errorCode == Constants.FUNCTION_FAILURE) {
				Log.log(
					Log.ERROR,
					"IFDAO",
					"getInflowOutflowSubHeadDetails",
					error);

				callable.close();
				callable = null;

				throw new DatabaseException(error);
			}
			ResultSet results = (ResultSet) callable.getObject(5);

			while (results.next()) {
				String subHead = results.getString(2);
				String amount = results.getString(3);

				Log.log(
					Log.DEBUG,
					"IFDAO",
					"getInflowOutflowSubHeadDetails",
					"subHead and amount " + subHead + ", " + amount);

				subHeadDetails.put(head + "_" + subHead, amount);
			}
			results.close();
			results=null;
			callable.close();
			callable = null;
		} catch (SQLException e) {
			Log.log(
				Log.ERROR,
				"IFDAO",
				"getInflowOutflowSubHeadDetails",
				e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Unable to get Inflow/Outflow SubHead Details");
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(Log.INFO, "IFDAO", "getInflowOutflowSubHeadDetails", "Exited");
		return subHeadDetails;
	}

	public ArrayList getInstrumentSchemes() throws DatabaseException {
		Log.log(Log.INFO, "IFDAO", "getInstrumentSchemes", "Entered");

		ArrayList schemes = new ArrayList();

		Connection connection = DBConnection.getConnection();

		try {
			CallableStatement callable =
				connection.prepareCall(
					"{?= call PACKGETALLINSSCHEME.FUNCGETALLINSSCHEME(?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);
			callable.registerOutParameter(2, Constants.CURSOR);
			callable.registerOutParameter(3, Types.VARCHAR);

			callable.execute();

			int errorCode = callable.getInt(1);
			String error = callable.getString(3);

			Log.log(
				Log.DEBUG,
				"IFDAO",
				"getInstrumentSchemes",
				"errorCode and error " + errorCode + "," + error);

			if (errorCode == Constants.FUNCTION_FAILURE) {
				Log.log(Log.ERROR, "IFDAO", "getInstrumentSchemes", error);
				callable.close();
				callable = null;

				throw new DatabaseException(error);
			}

			ResultSet results = (ResultSet) callable.getObject(2);

			while (results.next()) {
				schemes.add(results.getString(1));
			}

			results.close();
			results = null;

			callable.close();
			callable = null;

		} catch (SQLException e) {
			Log.log(Log.ERROR, "IFDAO", "getInstrumentSchemes", e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Unable to get Instrument Schemes");
		} finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO, "IFDAO", "getInstrumentSchemes", "Exited");

		return schemes;
	}

	public ArrayList getInvestmentRefNumbers(String inst) throws DatabaseException {
		Log.log(Log.INFO, "IFDAO", "getInvestmentRefNumbers", "Entered");

		ArrayList refNumbers = new ArrayList();

		Connection connection = DBConnection.getConnection();

		try {
			CallableStatement callable =
				connection.prepareCall(
					"{?= call PACKGETBUYSELL.FUNCGETBUYSELL(?,?,?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);
			callable.setString(2, InvestmentFundConstants.BUY_REQUEST);
			callable.setString(3, inst);
			callable.registerOutParameter(4, Constants.CURSOR);
			callable.registerOutParameter(5, Types.VARCHAR);

			callable.execute();

			int errorCode = callable.getInt(1);
			String error = callable.getString(5);

			Log.log(
				Log.DEBUG,
				"IFDAO",
				"getInvestmentRefNumbers",
				"errorCode and error " + errorCode + "," + error);

			if (errorCode == Constants.FUNCTION_FAILURE) {
				Log.log(Log.ERROR, "IFDAO", "getInvestmentRefNumbers", error);
				callable.close();
				callable = null;

				throw new DatabaseException(error);
			}

			ResultSet results = (ResultSet) callable.getObject(4);

			while (results.next()) {
				refNumbers.add(results.getString(1));
			}

			results.close();
			results = null;

			callable.close();
			callable = null;

		} catch (SQLException e) {
			Log.log(
				Log.ERROR,
				"IFDAO",
				"getInvestmentRefNumbers",
				e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Unable to get Investment reference numbers ");
		} finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO, "IFDAO", "getInvestmentRefNumbers", "Exited");

		return refNumbers;
	}

	public void addBankAccountDetail(
		BankAccountDetail accountDetail,
		String userId)
		throws DatabaseException {
		Log.log(Log.INFO, "IFDAO", "addBankAccountDetail", "Entered");

		Connection connection = DBConnection.getConnection();

		try {
			CallableStatement callable =
				connection.prepareCall(
					"{?= call FUNCINSERTBANKACCOUNTDETAILS(?,?,?,?,?,?,?,?,?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);
			callable.setString(2, accountDetail.getBankName());
			callable.setString(3, accountDetail.getBankBranchName());
			callable.setString(4, accountDetail.getAccountNumber());
			callable.setString(5, accountDetail.getModBankName());
			callable.setString(6, accountDetail.getModBankBranchName());
			callable.setString(7, accountDetail.getModAccountNumber());
			callable.setString(8, accountDetail.getAccountType());
			callable.setDouble(9, accountDetail.getMinBalance());
			callable.setString(10, userId);
			callable.registerOutParameter(11, Types.VARCHAR);

			callable.execute();

			int errorCode = callable.getInt(1);
			String error = callable.getString(11);

			Log.log(
				Log.DEBUG,
				"IFDAO",
				"addBankAccountDetail",
				"errorCode and error " + errorCode + "," + error);

			if (errorCode == Constants.FUNCTION_FAILURE) {
				Log.log(Log.ERROR, "IFDAO", "addBankAccountDetail", error);
				callable.close();
				callable = null;

				throw new DatabaseException("Unable to add Bank Account details");
			}
			callable.close();
			callable = null;
		} catch (SQLException e) {
			Log.log(Log.ERROR, "IFDAO", "addBankAccountDetail", e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Unable to add Bank Account details");
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(Log.INFO, "IFDAO", "addBankAccountDetail", "Exited");
	}

	public void addStatementDetail(
		StatementDetail statementDetail,
		String userId)
		throws DatabaseException,MessageException {
		Log.log(Log.INFO, "IFDAO", "addStatementDetail", "Entered");

		Connection connection = DBConnection.getConnection(false);
		String bank = statementDetail.getBankName();
		String newBank = bank.trim();
		//System.out.println("bank:"+bank);
		String branch = statementDetail.getBankBranchName();
		String newBranch = branch.trim();
		//System.out.println("branch:"+branch);
		String acNumber = statementDetail.getAccountNumber();
		String newAcNumber = acNumber.trim();
		//System.out.println("acNumber:"+acNumber);

		try {
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"addStatementDetail",
				"Bank Name " + statementDetail.getBankName());
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"addStatementDetail",
				"getBankBranchName " + statementDetail.getBankBranchName());
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"addStatementDetail",
				"getAccountNumber " + statementDetail.getAccountNumber());
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"addStatementDetail",
				"getOpeningBalance " + statementDetail.getOpeningBalance());
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"addStatementDetail",
				"getClosingBalance " + statementDetail.getClosingBalance());
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"addStatementDetail",
				"getCreditPendingForTheDay "
					+ statementDetail.getCreditPendingForTheDay());
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"addStatementDetail",
				"getTransactionFromTo "
					+ statementDetail.getTransactionFromTo());
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"addStatementDetail",
				"getTransactionNature "
					+ statementDetail.getTransactionNature());
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"addStatementDetail",
				"getTransactionDate " + statementDetail.getTransactionDate());
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"addStatementDetail",
				"getTransactionAmount "
					+ statementDetail.getTransactionAmount());
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"addStatementDetail",
				"getRemarks " + statementDetail.getRemarks());
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"addStatementDetail",
				"Userid " + userId);
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"addStatementDetail",
				"getStatementDate " + statementDetail.getStatementDate());

			CallableStatement callable =
				connection.prepareCall(
					"{?= call FUNCINSERTBANKSTATEMENTDTL(?,?,?,?,?,?,?,?,?,?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);
			//bank = statementDetail.getBankName();
			callable.setString(2, statementDetail.getBankName());
			callable.setString(3, statementDetail.getBankBranchName());
			callable.setString(4, statementDetail.getAccountNumber());
			callable.setDouble(5, statementDetail.getOpeningBalance());

			callable.setDouble(6, statementDetail.getClosingBalance());
			callable.setDouble(7, statementDetail.getCreditPendingForTheDay());
			callable.setString(8, statementDetail.getRemarks());
			java.util.Date utilDate = statementDetail.getStatementDate();
			java.sql.Date  sqlDate = new java.sql.Date (utilDate.getTime());
			callable.setDate(9, sqlDate);
			callable.setString(10, userId);
			callable.registerOutParameter(11, Types.INTEGER);
			callable.registerOutParameter(12, Types.VARCHAR);

			callable.execute();

			int errorCode = callable.getInt(1);
			//System.out.println("errorCode:"+errorCode);
			String error = callable.getString(12);
			//System.out.println("error:"+error);

			Log.log(
				Log.DEBUG,
				"IFDAO",
				"addStatementDetail",
				"errorCode and error " + errorCode + "," + error);

			if (errorCode == Constants.FUNCTION_FAILURE) {
				Log.log(Log.ERROR, "IFDAO", "addStatementDetail", error);
				callable.close();
				callable = null;
				connection.rollback();
				throw new DatabaseException(error);
			}


			int statementId=callable.getInt(11);
			//System.out.println("statementId:"+statementId);

			Log.log(Log.DEBUG, "IFDAO", "addStatementDetail", "Statement id is "+statementId);


			callable=connection.prepareCall("{?= call funcInsertBankTransactionDtl(?,?,?,?,?,?,?,?,?,?,?)}");
			callable.registerOutParameter(1,Types.INTEGER);
			callable.registerOutParameter(12,Types.VARCHAR);

			SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");

			for (int i=0;i<statementDetail.getTransactionDetail().size();i++)
			{
				TransactionDetail transactionDetail=
					(TransactionDetail)statementDetail.getTransactionDetail().get(i);

				Log.log(Log.DEBUG, "IFDAO", "addStatementDetail", "From/To,Nature,Date,Amount "+
				transactionDetail.getTransactionFromTo()+","+
				transactionDetail.getTransactionNature()+","+
				transactionDetail.getTransactionDate()+","+
				transactionDetail.getTransactionAmount());

				callable.setInt(2,statementId);
				//System.out.println("statementId:"+statementId);
				callable.setDouble(3,transactionDetail.getTransactionAmount());
				//System.out.println("statementId:"+transactionDetail.getTransactionAmount());
				if(transactionDetail.getChequeNumber() != null)
				{
					callable.setString(4,transactionDetail.getChequeNumber());
				}
				else
				{
					callable.setNull(4,java.sql.Types.VARCHAR);
				}
				//System.out.println("statementId:"+transactionDetail.getChequeNumber());
				callable.setString(5,transactionDetail.getTransactionFromTo());
				//System.out.println("statementId:"+transactionDetail.getTransactionFromTo());
				callable.setString(6,transactionDetail.getTransactionNature());
				//System.out.println("statementId:"+transactionDetail.getTransactionNature());
				java.util.Date trdDate=dateFormat.parse(transactionDetail.getTransactionDate(),new ParsePosition(0));
				callable.setDate(7,new java.sql.Date(trdDate.getTime()));
				//System.out.println("statementId:"+new java.sql.Date(trdDate.getTime()));
				java.util.Date valDate=dateFormat.parse(transactionDetail.getValueDate(),new ParsePosition(0));
				if(valDate != null)
				{
					callable.setDate(8,new java.sql.Date(valDate.getTime()));
				}
				else
				{
					callable.setNull(8,java.sql.Types.DATE);
				}
				//System.out.println("statementId:"+new java.sql.Date(valDate.getTime()));

				callable.setString(9,newBank);
				//System.out.println("newBank:"+newBank);
				callable.setString(10,newBranch);
				//System.out.println("newBranch:"+newBranch);
				callable.setString(11,newAcNumber);
				//System.out.println("newAcNumber:"+newAcNumber);
				
				callable.execute();

				errorCode=callable.getInt(1);
				//System.out.println("errorCode:"+errorCode);
				error=callable.getString(12);
				//System.out.println("error:"+error);
				Log.log(Log.DEBUG,"IFDAO","addStatementDetail",
					"errorCode and error " + errorCode + "," + error);

				if(errorCode==Constants.FUNCTION_FAILURE)
				{
					callable.close();
					callable = null;

					connection.rollback();
					Log.log(Log.ERROR, "IFDAO", "addStatementDetail", error);
					throw new DatabaseException(error);
				}

				java.sql.Date startDate = null;
				java.util.Date todaysDate = new java.util.Date();
				java.sql.Date todaysSQLDate = new java.sql.Date(todaysDate.getTime());
				boolean chequeIssuedDetailToBeUpdated = false;
				ArrayList transactions = statementDetail.getTransactionDetail();

				ArrayList chequeDetails = chequeDetailsUpdate(startDate,todaysSQLDate);
				Log.log(Log.DEBUG,"IFAction","addStatementDetail","For Loop: transactions.size() :" + transactions.size());
				Log.log(Log.DEBUG,"IFAction","addStatementDetail","For Loop: chequeDetails.size() " + chequeDetails.size());

				for(int j=0; j<chequeDetails.size(); j++)
				{
					ChequeDetails cd = (ChequeDetails)chequeDetails.get(j);
					if(cd == null)
					{
						continue;
					}
					String chequeNumber = cd.getChequeNumber();
					if(transactionDetail.getChequeNumber() == null)
					{
						continue;
					}
					if(((transactionDetail.getChequeNumber()) != null) && (transactionDetail.getChequeNumber().equals("")))
					{
						continue;
					}
					if(chequeNumber.equals(transactionDetail.getChequeNumber()))
					{
						// Updating cheque_issued_detail table
						ChequeDetails dtls = chequeDetailsUpdatePage(chequeNumber);
						String chequeStatus = dtls.getStatus();
						if(chequeStatus.equals("P"))
						{
							throw new MessageException("Cheque has already been presented");
						}
						String chequeId = dtls.getChequeId();
						Log.log(Log.DEBUG,"IFAction","addStatementDetail","For Loop: chequeNumber " + chequeNumber);
						java.util.Date transDate=dateFormat.parse(transactionDetail.getTransactionDate(),new ParsePosition(0));
						//System.out.println("transDate:"+transDate);
						java.sql.Date sqlTransDate = new java.sql.Date(transDate.getTime())	;
						//System.out.println("sqlTransDate:"+sqlTransDate);					
						Log.log(Log.DEBUG,"IFAction","addStatementDetail","For Loop: chequeNumber " + chequeNumber);
						updateChequeStatus(statementDetail.getBankName(),
								statementDetail.getAccountNumber(),chequeNumber,sqlTransDate,connection);
					}
				}
			}

			callable.close();
			callable = null;
			connection.commit();


		} catch (SQLException e) {
			Log.log(Log.ERROR, "IFDAO", "addStatementDetail", e.getMessage());
			Log.logException(e);
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new DatabaseException("Unable to add statement details");
		}
		catch (MessageException e) {
				Log.log(Log.ERROR, "IFDAO", "addStatementDetail", e.getMessage());
				Log.logException(e);
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				throw e;
			}
		catch (DatabaseException e) {
				Log.log(Log.ERROR, "IFDAO", "addStatementDetail", e.getMessage());
				Log.logException(e);
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				throw e;
			}

		finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO, "IFDAO", "addStatementDetail", "Exited");
	}


	public ArrayList getAllBanksWithAccountNumbers() throws DatabaseException {
		Log.log(Log.INFO, "IFDAO", "getAllBanksWithAccountNumbers", "Entered");

		ArrayList bankDetails = new ArrayList();

		Connection connection = DBConnection.getConnection();

		try {
			CallableStatement callable =
				connection.prepareCall(
					"{?= call PACKGETALLBANKDETAILS.FUNCGETALLBANKDETAILS(?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);
			callable.registerOutParameter(2, Constants.CURSOR);
			callable.registerOutParameter(3, Types.VARCHAR);

			callable.execute();

			int errorCode = callable.getInt(1);
			String error = callable.getString(3);

			Log.log(
				Log.DEBUG,
				"IFDAO",
				"getAllBanksWithAccountNumbers",
				"errorCode and error " + errorCode + "," + error);

			if (errorCode == Constants.FUNCTION_FAILURE) {
				Log.log(
					Log.ERROR,
					"IFDAO",
					"getAllBanksWithAccountNumbers",
					error);
				callable.close();
				callable = null;

				throw new DatabaseException(error);
			}

			ResultSet results = (ResultSet) callable.getObject(2);

			while (results.next()) {
				BankAccountDetail bankAccountDetail = new BankAccountDetail();

				bankAccountDetail.setBankName(results.getString(1));
				bankAccountDetail.setBankBranchName(results.getString(2));
				bankAccountDetail.setAccountNumber(results.getString(3));
				bankAccountDetail.setAccountType(results.getString(4));

				bankDetails.add(bankAccountDetail);
			}

			results.close();
			results = null;

			callable.close();
			callable = null;

		} catch (SQLException e) {
			Log.log(
				Log.ERROR,
				"IFDAO",
				"getAllBanksWithAccountNumbers",
				e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Unable to get Bank Account Details ");
		} finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO, "IFDAO", "getAllBanksWithAccountNumbers", "Exited");

		return bankDetails;
	}

	public ArrayList getAllMaturities() throws DatabaseException {
		Log.log(Log.INFO, "IFDAO", "getAllMaturities", "Entered");

		ArrayList maturityDetails = new ArrayList();

		Connection connection = DBConnection.getConnection();

		try {
			CallableStatement callable =
				connection.prepareCall(
					"{?= call PACKGETALLMATURITIES.FUNCGETALLMATURITIES(?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);
			callable.registerOutParameter(2, Constants.CURSOR);
			callable.registerOutParameter(3, Types.VARCHAR);

			callable.execute();

			int errorCode = callable.getInt(1);
			String error = callable.getString(3);

			Log.log(
				Log.DEBUG,
				"IFDAO",
				"getAllMaturities",
				"errorCode and error " + errorCode + "," + error);

			if (errorCode == Constants.FUNCTION_FAILURE) {
				Log.log(Log.ERROR, "IFDAO", "getAllMaturities", error);
				callable.close();
				callable = null;

				throw new DatabaseException(error);
			}

			ResultSet results = (ResultSet) callable.getObject(2);

			while (results.next()) {
				maturityDetails.add(results.getString(1));
			}

			results.close();
			results = null;

			callable.close();
			callable = null;

		} catch (SQLException e) {
			Log.log(Log.ERROR, "IFDAO", "getAllMaturities", e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Unable to get Maturity Details ");
		} finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO, "IFDAO", "getAllMaturities", "Exited");

		return maturityDetails;
	}

	public void addMaturityWiseCeiling(
		MaturityWiseCeiling maturityCeiling,
		String userId)
		throws DatabaseException {
		Log.log(Log.INFO, "IFDAO", "addMaturityWiseCeiling", "Entered");

		Connection connection = DBConnection.getConnection();

		try {
			CallableStatement callable =
				connection.prepareCall(
					"{?= call funcInsCeilingMat(?,?,?,?,?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);
			callable.setString(2, maturityCeiling.getMaturityType());
			callable.setDouble(3, maturityCeiling.getCeilingLimit());

			java.sql.Date startDate = null;

			if (maturityCeiling.getCeilingStartDate() != null) {
				startDate =
					new java.sql.Date(
						maturityCeiling.getCeilingStartDate().getTime());
			}

			callable.setDate(4, startDate);
			if (maturityCeiling.getCeilingEndDate() != null && !maturityCeiling.getCeilingEndDate().toString().equals("")) {
			callable.setDate(
				5,
				new java.sql.Date(
					maturityCeiling.getCeilingEndDate().getTime()));
			}
			else
			{
				callable.setNull(5, java.sql.Types.DATE);
			}

			callable.setString(6, userId);
			callable.registerOutParameter(7, Types.VARCHAR);

			callable.execute();

			int errorCode = callable.getInt(1);
			String error = callable.getString(7);

			Log.log(
				Log.DEBUG,
				"IFDAO",
				"addMaturityWiseCeiling",
				"errorCode and error " + errorCode + "," + error);

			if (errorCode == Constants.FUNCTION_FAILURE) {
				Log.log(Log.ERROR, "IFDAO", "addMaturityWiseCeiling", error);
				callable.close();
				callable = null;

				throw new DatabaseException(error);
			}
			callable.close();
			callable = null;
		} catch (SQLException e) {
			Log.log(
				Log.ERROR,
				"IFDAO",
				"addMaturityWiseCeiling",
				e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Unable to store Maturity wise ceiling.");
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(Log.INFO, "IFDAO", "addMaturityWiseCeiling", "Exited");
	}

	public void addInstrumentWiseCeiling(
		InstrumentWiseCeiling instrumentCeiling,
		String userId)
		throws DatabaseException {
		Log.log(Log.INFO, "IFDAO", "addInstrumentWiseCeiling", "Entered");

		Connection connection = DBConnection.getConnection();

		try {
			Log.log(Log.INFO, "IFDAO", "addInstrumentWiseCeiling", "name,limit," +
				"start,end dates are "+instrumentCeiling.getInstrumentName()+","+
				instrumentCeiling.getCeilingLimit()+","+instrumentCeiling.getCeilingStartDate()+","+
				instrumentCeiling.getCeilingEndDate());

			CallableStatement callable =
				connection.prepareCall(
					"{?= call FUNCINSCEILINGINS(?,?,?,?,?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);
			callable.setString(2, instrumentCeiling.getInstrumentName());
			callable.setDouble(3, instrumentCeiling.getCeilingLimit());

			java.sql.Date startDate = null;

			if (instrumentCeiling.getCeilingStartDate() != null) {
				startDate =
					new java.sql.Date(
						instrumentCeiling.getCeilingStartDate().getTime());
			}

			callable.setDate(4, startDate);
			if (instrumentCeiling.getCeilingEndDate()!=null && !instrumentCeiling.getCeilingEndDate().toString().equals(""))
			{
				callable.setDate(
					5,
					new java.sql.Date(
						instrumentCeiling.getCeilingEndDate().getTime()));
			}
			else
			{
				callable.setNull(5, java.sql.Types.DATE);
			}

			callable.setString(6, userId);
			callable.registerOutParameter(7, Types.VARCHAR);

			callable.execute();

			int errorCode = callable.getInt(1);
			String error = callable.getString(7);

			Log.log(
				Log.DEBUG,
				"IFDAO",
				"addInstrumentWiseCeiling",
				"errorCode and error " + errorCode + "," + error);

			if (errorCode == Constants.FUNCTION_FAILURE) {
				Log.log(Log.ERROR, "IFDAO", "addInstrumentWiseCeiling", error);
				callable.close();
				callable = null;

				throw new DatabaseException(error);
			}
			callable.close();
			callable = null;
		} catch (SQLException e) {
			Log.log(
				Log.ERROR,
				"IFDAO",
				"addInstrumentWiseCeiling",
				e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Unable to store Instrument wise ceiling.");
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(Log.INFO, "IFDAO", "addInstrumentWiseCeiling", "Exited");
	}

	public void setInvesteeGroupWiseCeiling(
		InvesteeGroupWiseCeiling investeeGroupCeiling,
		String userId)
		throws DatabaseException {
		Log.log(Log.INFO, "IFDAO", "setInvesteeGroupWiseCeiling", "Entered");

		Connection connection = DBConnection.getConnection();

		try {
			CallableStatement callable =
				connection.prepareCall(
					"{?= call FUNCINSCEILINGIGR(?,?,?,?,?,?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);
			callable.setString(2, investeeGroupCeiling.getInvesteeGroup());
			callable.setDouble(3, investeeGroupCeiling.getCeilingLimit());
			callable.setDouble(4, investeeGroupCeiling.getCeilingAmount());

			java.sql.Date startDate = null;

			if (investeeGroupCeiling.getCeilingStartDate() != null) {
				startDate =
					new java.sql.Date(
						investeeGroupCeiling.getCeilingStartDate().getTime());
			}

			callable.setDate(5, startDate);
			if (investeeGroupCeiling.getCeilingEndDate()!=null && !investeeGroupCeiling.getCeilingEndDate().toString().equals(""))
			{
			callable.setDate(
				6,
				new java.sql.Date(
					investeeGroupCeiling.getCeilingEndDate().getTime()));
			}
			else
			{
				callable.setNull(6, java.sql.Types.DATE);
			}

			callable.setString(7, userId);
			callable.registerOutParameter(8, Types.VARCHAR);

			callable.execute();

			int errorCode = callable.getInt(1);
			String error = callable.getString(8);

			Log.log(
				Log.DEBUG,
				"IFDAO",
				"setInvesteeGroupWiseCeiling",
				"errorCode and error " + errorCode + "," + error);

			if (errorCode == Constants.FUNCTION_FAILURE) {
				Log.log(
					Log.ERROR,
					"IFDAO",
					"setInvesteeGroupWiseCeiling",
					error);
				callable.close();
				callable = null;

				throw new DatabaseException(error);
			}
			callable.close();
			callable = null;
		} catch (SQLException e) {
			Log.log(
				Log.ERROR,
				"IFDAO",
				"setInvesteeGroupWiseCeiling",
				e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Unable to store Investee Group wise ceiling.");
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(Log.INFO, "IFDAO", "setInvesteeGroupWiseCeiling", "Exited");
	}
	public void setInvesteeWiseCeiling(
		InvesteeWiseCeiling investeeCeiling,
		String userId)
		throws DatabaseException {
		Log.log(Log.INFO, "IFDAO", "setInvesteeWiseCeiling", "Entered");

		Connection connection = DBConnection.getConnection();

		try {
			CallableStatement callable =
				connection.prepareCall(
					"{?= call FUNCINSCEILINGINV(?,?,?,?,?,?,?,?,?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);
			callable.setString(2, investeeCeiling.getInvesteeGroup());
			callable.setString(3, investeeCeiling.getInvesteeName());
			callable.setDouble(4, investeeCeiling.getNetworth());
			callable.setDouble(5, investeeCeiling.getTangibleAssets());
			callable.setDouble(6, investeeCeiling.getCeilingLimit());

			java.sql.Date startDate = null;

			if (investeeCeiling.getCeilingStartDate() != null) {
				startDate =
					new java.sql.Date(
						investeeCeiling.getCeilingStartDate().getTime());
			}

			callable.setDate(7, startDate);
			if (investeeCeiling.getCeilingEndDate()!=null && !investeeCeiling.getCeilingEndDate().toString().equals(""))
			{
				callable.setDate(
					8,
					new java.sql.Date(
						investeeCeiling.getCeilingEndDate().getTime()));
			}
			else
			{
				callable.setNull(8, java.sql.Types.DATE);
			}

			callable.setString(9, userId);
			callable.setDouble(10,investeeCeiling.getCeilingAmount());
			callable.registerOutParameter(11, Types.VARCHAR);

			callable.execute();

			int errorCode = callable.getInt(1);
			String error = callable.getString(11);

			Log.log(
				Log.DEBUG,
				"IFDAO",
				"setInvesteeWiseCeiling",
				"errorCode and error " + errorCode + "," + error);

			if (errorCode == Constants.FUNCTION_FAILURE) {
				Log.log(Log.ERROR, "IFDAO", "setInvesteeWiseCeiling", error);
				callable.close();
				callable = null;

				throw new DatabaseException(error);
			}
			callable.close();
			callable = null;
		} catch (SQLException e) {
			Log.log(
				Log.ERROR,
				"IFDAO",
				"setInvesteeWiseCeiling",
				e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Unable to store Investee wise ceiling.");
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(Log.INFO, "IFDAO", "setInvesteeWiseCeiling", "Exited");
	}

	public void setRatingWiseCeiling(
		RatingWiseCeiling ratingCeiling,
		String userId)
		throws DatabaseException {
		Log.log(Log.INFO, "IFDAO", "setRatingWiseCeiling", "Entered");

		Connection connection = DBConnection.getConnection();

		try {
			CallableStatement callable =
				connection.prepareCall(
					"{?= call FUNCINSCEILINGRAT(?,?,?,?,?,?,?,?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);
			callable.setString(2, ratingCeiling.getInvesteeGroup());
			callable.setString(3, ratingCeiling.getInvesteeName());
			callable.setString(4, ratingCeiling.getInstrumentName());
			callable.setString(5, ratingCeiling.getRating());
			callable.setString(6, ratingCeiling.getRatingAgency());

			java.sql.Date startDate = null;

			if (ratingCeiling.getCeilingStartDate() != null) {
				startDate =
					new java.sql.Date(
						ratingCeiling.getCeilingStartDate().getTime());
			}

			callable.setDate(7, startDate);
			if (ratingCeiling.getCeilingEndDate()!=null && !ratingCeiling.getCeilingEndDate().toString().equals(""))
			{
				callable.setDate(
					8,
					new java.sql.Date(ratingCeiling.getCeilingEndDate().getTime()));
			}
			else
			{
				callable.setNull(8, java.sql.Types.DATE);
			}

			callable.setString(9, userId);
			callable.registerOutParameter(10, Types.VARCHAR);

			callable.execute();

			int errorCode = callable.getInt(1);
			String error = callable.getString(10);

			Log.log(
				Log.DEBUG,
				"IFDAO",
				"setRatingWiseCeiling",
				"errorCode and error " + errorCode + "," + error);

			if (errorCode == Constants.FUNCTION_FAILURE) {
				Log.log(Log.ERROR, "IFDAO", "setRatingWiseCeiling", error);
				callable.close();
				callable = null;

				throw new DatabaseException(error);
			}
			callable.close();
			callable = null;
		} catch (SQLException e) {
			Log.log(Log.ERROR, "IFDAO", "setRatingWiseCeiling", e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Unable to store Rating wise ceiling.");
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(Log.INFO, "IFDAO", "setRatingWiseCeiling", "Exited");
	}

	public PlanInvestment getPlanInvestmentDetails() throws DatabaseException {
		Log.log(Log.INFO, "IFDAO", "getPlanInvestmentDetails", "Entered");

		PlanInvestment planInvestment = new PlanInvestment();

		Connection connection = DBConnection.getConnection();

		try {
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"getPlanInvestmentDetails",
				" Getting available balance ");

			Calendar yesterday = Calendar.getInstance();
//			yesterday.add(Calendar.DATE, -1);

			Log.log(
				Log.DEBUG,
				"IFDAO",
				"getPlanInvestmentDetails",
				"yester day " + new java.sql.Date(yesterday.getTimeInMillis()));

			CallableStatement callable =
				connection.prepareCall("{?= call FUNCGETAVBLBALANCE(?,?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);
			callable.setDate(2, new java.sql.Date(yesterday.getTimeInMillis()));
			callable.registerOutParameter(3, Types.DECIMAL);
			callable.registerOutParameter(4, Types.VARCHAR);

			callable.execute();

			int errorCode = callable.getInt(1);
			String error = callable.getString(4);

			Log.log(
				Log.DEBUG,
				"IFDAO",
				"getPlanInvestmentDetails",
				" errorCode and error " + errorCode + "," + error);

			if (errorCode == Constants.FUNCTION_FAILURE) {
				Log.log(Log.ERROR, "IFDAO", "getPlanInvestmentDetails", error);
				callable.close();
				callable = null;

				throw new DatabaseException(error);
			}
			double availableBalance = callable.getDouble(3);

			planInvestment.setAvailableBalance(availableBalance);

			callable.close();
			callable = null;

			Log.log(
				Log.DEBUG,
				"IFDAO",
				"getPlanInvestmentDetails",
				" Getting monthly expenses ");

			callable =
				connection.prepareCall(
					"{?= call funcGetXpensesMonth(?,?,?,?)}");

			Calendar today = Calendar.getInstance();

			int year = today.get(Calendar.YEAR);
			String month = DateHelp.getMonth(today.get(Calendar.MONTH));

			Log.log(
				Log.DEBUG,
				"IFDAO",
				"getPlanInvestmentDetails",
				" year, month " + year + "," + month);

			callable.registerOutParameter(1, Types.INTEGER);
			callable.setString(2, month);
			callable.setInt(3, year);
			callable.registerOutParameter(4, Types.DECIMAL);
			callable.registerOutParameter(5, Types.VARCHAR);

			callable.execute();

			errorCode = callable.getInt(1);
			error = callable.getString(5);

			Log.log(
				Log.DEBUG,
				"IFDAO",
				"getPlanInvestmentDetails",
				" errorCode and error " + errorCode + "," + error);

			if (errorCode == Constants.FUNCTION_FAILURE) {
				Log.log(Log.ERROR, "IFDAO", "getPlanInvestmentDetails", error);
				callable.close();
				callable = null;

				throw new DatabaseException(error);
			}
			double monthExpenses = callable.getDouble(4);

			Log.log(
				Log.DEBUG,
				"IFDAO",
				"getPlanInvestmentDetails",
				" monthExpenses " + monthExpenses);

			callable.close();
			callable = null;

			planInvestment.setMonthExpenses(monthExpenses);

			Log.log(
				Log.DEBUG,
				"IFDAO",
				"getPlanInvestmentDetails",
				" Getting day expenses "
					+ new java.sql.Date(today.getTimeInMillis()));

			callable =
				connection.prepareCall("{?= call funcGetXpensesDay(?,?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);
			callable.setDate(2, new java.sql.Date(today.getTimeInMillis()));
			callable.registerOutParameter(3, Types.DECIMAL);
			callable.registerOutParameter(4, Types.VARCHAR);

			callable.execute();

			errorCode = callable.getInt(1);
			error = callable.getString(4);

			Log.log(Log.DEBUG,				"IFDAO",
				"getPlanInvestmentDetails",
				" errorCode and error " + errorCode + "," + error);

			if (errorCode == Constants.FUNCTION_FAILURE) {
				Log.log(Log.ERROR, "IFDAO", "getPlanInvestmentDetails", error);
				callable.close();
				callable = null;

				throw new DatabaseException(error);
			}
			double dayExpenses = callable.getDouble(3);

			Log.log(
				Log.DEBUG,
				"IFDAO",
				"getPlanInvestmentDetails",
				" dayExpenses " + dayExpenses);

			callable.close();
			callable = null;

			planInvestment.setDayExpenses(dayExpenses);
		} catch (SQLException e) {
			Log.log(
				Log.ERROR,
				"IFDAO",
				"getPlanInvestmentDetails",
				e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Unable to get Plan Investment Details ");
		} finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO, "IFDAO", "getPlanInvestmentDetails", "Exited");

		return planInvestment;
	}
	public ExposureDetails getExposure(String investeeGroup,String investeeName) throws DatabaseException
	{

		Log.log(Log.INFO, "IFDAO", "getExposure", "Entered");

		ExposureDetails exposureDetail=new ExposureDetails();

		exposureDetail.setInvesteeName(investeeName);
		exposureDetail.setInvesteeGroup(investeeGroup);

		Connection connection = DBConnection.getConnection();

		try {

			CallableStatement callable =
				connection.prepareCall("{?= call FUNCGETASSETSNETWORTH(?,?,?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);
			callable.setString(2,investeeName );
			callable.registerOutParameter(3, Types.DECIMAL);
			callable.registerOutParameter(4, Types.DECIMAL);
			callable.registerOutParameter(5, Types.VARCHAR);

			callable.execute();

			int errorCode = callable.getInt(1);
			String error = callable.getString(5);

			Log.log(Log.DEBUG,"IFDAO","getExposure",
				" errorCode and error " + errorCode + "," + error);

			if (errorCode == Constants.FUNCTION_FAILURE) {
				Log.log(Log.ERROR, "IFDAO", "getExposure", error);
				callable.close();
				callable = null;

				throw new DatabaseException(error);
			}
			exposureDetail.setInvesteeTangibleAssets(callable.getDouble(3));
			exposureDetail.setInvesteeNetWorth(callable.getDouble(4));

			callable.close();
			callable=null;

			callable =
				connection.prepareCall("{?= call PACKGETINVESTEEDTL.FUNCGETINVDETAIL(?,?,?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);
			callable.setString(2,investeeGroup);
			callable.setString(3,investeeName );
			callable.registerOutParameter(4, Constants.CURSOR);
			callable.registerOutParameter(5, Types.VARCHAR);

			callable.execute();

			errorCode = callable.getInt(1);
			error = callable.getString(5);

			Log.log(Log.DEBUG,"IFDAO","getExposure",
				" errorCode and error " + errorCode + "," + error);

			if (errorCode == Constants.FUNCTION_FAILURE) {
				Log.log(Log.ERROR, "IFDAO", "getExposure", error);
				callable.close();
				callable = null;

				throw new DatabaseException(error);
			}

			ResultSet results=(ResultSet)callable.getObject(4);

			double netWorth=0;
			double tangibleAssets=0;
			double ceilingLimitPer = 0;
			double ceilingLimitAmount = 0;

			while(results.next())
			{
				netWorth=results.getDouble(1);
				tangibleAssets=results.getDouble(2);
				ceilingLimitPer = results.getDouble(3);
				ceilingLimitAmount= results.getDouble(4);
			}
			Log.log(Log.DEBUG,"IFDAO","getExposure",
				" netWorth and tangibleAssets " + netWorth + "," + tangibleAssets);
			results.close();
			results=null;

			callable.close();
			callable=null;

			exposureDetail.setNetworth(netWorth);
			exposureDetail.setTangibleAssets(tangibleAssets);
			exposureDetail.setCeilingLimit(ceilingLimitPer);
			exposureDetail.setInvCeilingAmt(ceilingLimitAmount);

		}
		catch(SQLException e)
		{
			Log.log(Log.ERROR,"IFDAO","getExposure",
				e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Unable to get Exposure details ");
		}
		finally
		{
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO, "IFDAO", "getExposure", "Exited");
		return exposureDetail;
	}


	public double getCorpusAmount() throws DatabaseException
	{
		double corpusAmount=0;

		Log.log(Log.INFO, "IFDAO", "getCorpusAmount", "Entered");
		Connection connection = DBConnection.getConnection();

		try
		{
			CallableStatement callable =
					connection.prepareCall(
					"{?= call funcGetCorpusAmt(?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);
			callable.registerOutParameter(2, Types.DECIMAL);
			callable.registerOutParameter(3, Types.VARCHAR);

			callable.execute();

			int errorCode = callable.getInt(1);
			String error = callable.getString(3);

			Log.log(Log.DEBUG,"IFDAO","getCorpusAmount",
				"errorCode and error " + errorCode + "," + error);

			if (errorCode == Constants.FUNCTION_FAILURE) {
				Log.log(Log.ERROR, "IFDAO", "getCorpusAmount", error);
				callable.close();
				callable = null;

				throw new DatabaseException(error);
			}
			corpusAmount=callable.getDouble(2);

			Log.log(Log.DEBUG,"IFDAO","getCorpusAmount","corpus amount " +corpusAmount);

			callable.close();
			callable = null;
		}
		catch (SQLException e)
		{
			Log.log(Log.ERROR, "IFDAO", "getCorpusAmount", e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Unable to get corpus amount.");
		}
		finally
		{
			DBConnection.freeConnection(connection);
		}

		Log.log(Log.INFO, "IFDAO", "getCorpusAmount", "Exited");
		return corpusAmount;
	}

	public ArrayList getCeilingDetails(ExposureDetails exposureDetails) throws DatabaseException
	{
		//CeilingDetails ceilingDetails=new CeilingDetails();
		ArrayList ceilingDetails=new ArrayList();
		Log.log(Log.INFO, "IFDAO", "getCeilingDetails", "Entered");
		Connection connection = DBConnection.getConnection();

		try
		{
			CallableStatement callable =
					connection.prepareCall(
					"{?= call PACKGETCEILINGDTL.FUNCGETCEILINGDTL(?,?,?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);
			callable.setString(2,exposureDetails.getInvesteeGroup());
			callable.setString(3,exposureDetails.getInvesteeName());
			callable.registerOutParameter(4, Constants.CURSOR);
			callable.registerOutParameter(5, Types.VARCHAR);

			callable.execute();

			int errorCode = callable.getInt(1);
			String error = callable.getString(5);

			Log.log(Log.DEBUG,"IFDAO","getCeilingDetails",
				"errorCode and error " + errorCode + "," + error);

			if (errorCode == Constants.FUNCTION_FAILURE) {
				Log.log(Log.ERROR, "IFDAO", "getCeilingDetails", error);
				callable.close();
				callable = null;

				throw new DatabaseException(error);
			}
			ResultSet results=(ResultSet)callable.getObject(4);

			String flag=null;

			//Map ceilingMap=new HashMap();



			while(results.next())
			{

				CeilingDetails ceiling=new CeilingDetails();

				ceiling.setFlag(results.getInt(1));

				ceiling.setCeilingName(results.getString(2));
				ceiling.setCeilingLimit(results.getDouble(3));
				ceiling.setCeilingNetWorth(results.getDouble(4));
				ceiling.setCelingTangibleAssets(results.getDouble(5));

				ceiling.setCeilingInPercentage(results.getDouble(6));

				ceilingDetails.add(ceiling);
				Log.log(Log.DEBUG,"IFDAO","getCeilingDetails","flag is " +ceiling.getFlag());
				Log.log(Log.DEBUG,"IFDAO","getCeilingDetails","ceilign name " +ceiling.getCeilingName());
				Log.log(Log.DEBUG,"IFDAO","getCeilingDetails","setCeilingLimit name " +ceiling.getCeilingLimit());
				Log.log(Log.DEBUG,"IFDAO","getCeilingDetails","setCeilingNetWorth name " +ceiling.getCeilingNetWorth());
				Log.log(Log.DEBUG,"IFDAO","getCeilingDetails","setCelingTangibleAssets name " +ceiling.getCelingTangibleAssets());
				Log.log(Log.DEBUG,"IFDAO","getCeilingDetails","setCeilingInPercentage name " +ceiling.getCeilingInPercentage());
			}
			results.close();
			results=null;

			callable.close();
			callable = null;
		}
		catch (SQLException e)
		{
			Log.log(Log.ERROR, "IFDAO", "getCeilingDetails", e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Unable to get Ceiling details.");
		}
		finally
		{
			DBConnection.freeConnection(connection);
		}

		Log.log(Log.INFO, "IFDAO", "getCeilingDetails", "Exited");

		return ceilingDetails;
	}
	public double getInvestedAmountInInvesteeGroup(String groupName, Date date) throws DatabaseException
	{
		double corpusAmount=0;

		Log.log(Log.INFO, "IFDAO", "getInvestedAmountInInvesteeGroup", "Entered");
		Connection connection = DBConnection.getConnection();

		try
		{
			CallableStatement callable =
					connection.prepareCall(
					"{?= call funcGetInvestedAmtIGR(?,?,?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);
			callable.setString(2,groupName);
			callable.setDate(3, new java.sql.Date(date.getTime()));
			callable.registerOutParameter(4, Types.DECIMAL);
			callable.registerOutParameter(5, Types.VARCHAR);

			callable.execute();

			int errorCode = callable.getInt(1);
			String error = callable.getString(5);

			Log.log(Log.DEBUG,"IFDAO","getInvestedAmountInInvesteeGroup",
				"errorCode and error " + errorCode + "," + error);

			if (errorCode == Constants.FUNCTION_FAILURE) {
				Log.log(Log.ERROR, "IFDAO", "getInvestedAmountInInvesteeGroup", error);
				callable.close();
				callable = null;

				throw new DatabaseException(error);
			}
			corpusAmount=callable.getDouble(4);

			Log.log(Log.DEBUG,"IFDAO","getInvestedAmountInInvesteeGroup","corpus amount " +corpusAmount);

			callable.close();
			callable = null;
		}
		catch (SQLException e)
		{
			Log.log(Log.ERROR, "IFDAO", "getInvestedAmountInInvesteeGroup", e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Unable to get Invested amount for Investee group.");
		}
		finally
		{
			DBConnection.freeConnection(connection);
		}

		Log.log(Log.INFO, "IFDAO", "getInvestedAmountInInvesteeGroup", "Exited");
		return corpusAmount;
	}

	public double getInvestedAmountInInvestee(String investeeGroup,String investeeName, Date date)
				 throws DatabaseException
	{
		double investeeAmount = 0;

		Log.log(Log.INFO, "IFDAO", "getInvestedAmountInInvestee", "Entered");
		Connection connection = DBConnection.getConnection();

		try
		{
			CallableStatement callable =
					connection.prepareCall(
					"{?= call PACKGETINVESTEDAMTINV.FUNCGETINVESTEDAMTINV(?,?,?,?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);
			callable.setString(2,investeeGroup);
			callable.setString(3,investeeName);
			callable.setDate(4, new java.sql.Date(date.getTime()));
			callable.registerOutParameter(5, Constants.CURSOR);
			callable.registerOutParameter(6, Types.VARCHAR);

			callable.execute();

			int errorCode = callable.getInt(1);
			String error = callable.getString(6);

			Log.log(Log.DEBUG,"IFDAO","getInvestedAmountInInvestee",
				"errorCode and error " + errorCode + "," + error);

			if (errorCode == Constants.FUNCTION_FAILURE) {
				Log.log(Log.ERROR, "IFDAO", "getInvestedAmountInInvestee", error);
				callable.close();
				callable = null;

				throw new DatabaseException(error);
			}

			ResultSet results=(ResultSet)callable.getObject(5);



			while(results.next())
			{
				investeeAmount = results.getDouble(3);
			}
			results.close();
			results=null;

			//Log.log(Log.DEBUG,"IFDAO","getInvestedAmountInInvestee","corpus amount " +corpusAmount);

			callable.close();
			callable = null;
		}
		catch (SQLException e)
		{
			Log.log(Log.ERROR, "IFDAO", "getInvestedAmountInInvestee", e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Unable to get Investment amount for investee.");
		}
		finally
		{
			DBConnection.freeConnection(connection);
		}

		Log.log(Log.INFO, "IFDAO", "getInvestedAmountInInvestee", "Exited");
		return investeeAmount;
	}
	public double getInvestedInInstrument(String instrumentName, Date date)
					 throws DatabaseException
		{
			double corpusAmount=0;

			Log.log(Log.INFO, "IFDAO", "getInvestedInInvestment", "Entered");
			Connection connection = DBConnection.getConnection();

			try
			{
				CallableStatement callable =
						connection.prepareCall(
						"{?= call PACKGETINVESTEDAMTINS.FUNCGETINVAMTINS(?,?,?)}");

				callable.registerOutParameter(1, Types.INTEGER);
				callable.setDate(2, new java.sql.Date(date.getTime()));
				callable.registerOutParameter(3, Constants.CURSOR);
				callable.registerOutParameter(4, Types.VARCHAR);

				callable.execute();

				int errorCode = callable.getInt(1);
				String error = callable.getString(4);

				Log.log(Log.DEBUG,"IFDAO","getInvestedInInvestment",
					"errorCode and error " + errorCode + "," + error);

				if (errorCode == Constants.FUNCTION_FAILURE) {
					Log.log(Log.ERROR, "IFDAO", "getInvestedInInvestment", error);
					callable.close();
					callable = null;

					throw new DatabaseException(error);
				}

				ResultSet results=(ResultSet)callable.getObject(3);

				while(results.next())
				{
					double amount=results.getDouble(1);
					String instrument=results.getString(2);

					if(instrument.equals(instrumentName))
					{
						corpusAmount=amount;
					}
				}

				results.close();
				results=null;

				Log.log(Log.DEBUG,"IFDAO","getInvestedInInvestment","corpus amount " +corpusAmount);

				callable.close();
				callable = null;
			}
			catch (SQLException e)
			{
				Log.log(Log.ERROR, "IFDAO", "getInvestedInInvestment", e.getMessage());
				Log.logException(e);

				throw new DatabaseException("Unable to get Investment amount for Instrument.");
			}
			finally
			{
				DBConnection.freeConnection(connection);
			}

			Log.log(Log.INFO, "IFDAO", "getInvestedInInvestment", "Exited");
			return corpusAmount;
	}
	public double getInvestedInMaturity(String maturityType, Date date)
					 throws DatabaseException
		{
			double corpusAmount=0;

			Log.log(Log.INFO, "IFDAO", "getInvestedInMaturity", "Entered");
			Connection connection = DBConnection.getConnection();

			try
			{
				CallableStatement callable =
						connection.prepareCall(
						"{?= call PACKGETINVESTEDAMTMAT.FUNCGETINVESTEDAMTMAT(?,?,?,?)}");

				callable.registerOutParameter(1, Types.INTEGER);
				callable.setDate(2, new java.sql.Date(date.getTime()));
				callable.setString(3,maturityType);
				callable.registerOutParameter(4, Constants.CURSOR);
				callable.registerOutParameter(5, Types.VARCHAR);

				callable.execute();

				int errorCode = callable.getInt(1);
				String error = callable.getString(5);

				Log.log(Log.DEBUG,"IFDAO","getInvestedInMaturity",
					"errorCode and error " + errorCode + "," + error);

				if (errorCode == Constants.FUNCTION_FAILURE) {
					Log.log(Log.ERROR, "IFDAO", "getInvestedInMaturity", error);
					callable.close();
					callable = null;

					throw new DatabaseException(error);
				}

				ResultSet results=(ResultSet)callable.getObject(4);

				while(results.next())
				{
					double amount=results.getDouble(1);
					String maturity=results.getString(2);

					if(maturity.equals(maturityType))
					{
						corpusAmount=amount;
					}
				}
				results.close();
				results=null;


				Log.log(Log.DEBUG,"IFDAO","getInvestedInMaturity","corpus amount " +corpusAmount);

				callable.close();
				callable = null;
			}
			catch (SQLException e)
			{
				Log.log(Log.ERROR, "IFDAO", "getInvestedInMaturity", e.getMessage());
				Log.logException(e);

				throw new DatabaseException("Unable to get Investment amount for Maturity.");
			}
			finally
			{
				DBConnection.freeConnection(connection);
			}

			Log.log(Log.INFO, "IFDAO", "getInvestedInMaturity", "Exited");
			return corpusAmount;
	}

	public Map getGuaranteeIssuedApplications() throws DatabaseException
	{
		Map guaranteeIssuedApplications=new HashMap();
		Log.log(Log.INFO, "IFDAO", "getGuaranteeIssuedApplications", "Entered");

		Connection connection = DBConnection.getConnection();

		try
		{
			CallableStatement callable =
					connection.prepareCall(
					"{?= call packGetGuarDBRDt.funcGetGuarDBRDt(?,?)}");


			callable.registerOutParameter(1, Types.INTEGER);
			callable.registerOutParameter(2, Constants.CURSOR);
			callable.registerOutParameter(3, Types.VARCHAR);

			callable.execute();

			int errorCode = callable.getInt(1);
			String error = callable.getString(3);

			Log.log(Log.DEBUG,"IFDAO","getGuaranteeIssuedApplications",
				"errorCode and error " + errorCode + "," + error);

			if (errorCode == Constants.FUNCTION_FAILURE) {
				Log.log(Log.ERROR, "IFDAO", "getGuaranteeIssuedApplications", error);
				callable.close();
				callable = null;

				throw new DatabaseException(error);
			}

			ResultSet results=(ResultSet)callable.getObject(2);

			while(results.next())
			{
				String cgpan=results.getString(1);
				Date guaranteeIssueDate=results.getDate(2);
				double guaranteeIssuedAmount=results.getDouble(3);
				Date disubursementDate=results.getDate(4);

				Log.log(Log.DEBUG,"IFDAO","getGuaranteeIssuedApplications","cgpan ,guaranteeIssueDate," +
					"disubursementDate" +cgpan+","+guaranteeIssueDate+","+disubursementDate);

				if(guaranteeIssueDate==null)
				{
					continue;
				}
				GuaranteeIssuedDetail guaranteeDetails=new GuaranteeIssuedDetail();
				guaranteeDetails.setGuaranteeIssuedAmount(guaranteeIssuedAmount);


				if(disubursementDate==null ||
				disubursementDate.compareTo(guaranteeIssueDate)<0)
				{
					guaranteeDetails.setGuaranteeIssuedDate(guaranteeIssueDate);
				}
				else
				{
					guaranteeDetails.setGuaranteeIssuedDate(disubursementDate);
				}

				guaranteeIssuedApplications.put(cgpan,guaranteeDetails);
			}
			/*
			GuaranteeIssuedDetail guaranteeDetails=new GuaranteeIssuedDetail();

			guaranteeDetails.setGuaranteeIssuedDate(new Date(15,1,2001));
			guaranteeDetails.setGuaranteeIssuedAmount(100000);

			guaranteeIssuedApplications.put("CG200400002TC",guaranteeDetails);

			guaranteeDetails=new GuaranteeIssuedDetail();

			guaranteeDetails.setGuaranteeIssuedDate(new Date(15,1,2000));
			guaranteeDetails.setGuaranteeIssuedAmount(300000);

			guaranteeIssuedApplications.put("CG200400004TC",guaranteeDetails);

			*/
			results.close();
			results=null;
			callable.close();
			callable = null;
		}
		catch (SQLException e)
		{
			Log.log(Log.ERROR, "IFDAO", "getGuaranteeIssuedApplications", e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Unable to get Gurantee issued applications.");
		}
		finally
		{
			DBConnection.freeConnection(connection);
		}

		Log.log(Log.INFO, "IFDAO", "getGuaranteeIssuedApplications", "Exited");
		return guaranteeIssuedApplications;
	}

	public Map getApplicationTenures() throws DatabaseException
	{
		Map tenures=new HashMap();
		Log.log(Log.INFO, "IFDAO", "getApplicationTenures", "Entered");

		Connection connection = DBConnection.getConnection();

		try
		{
			CallableStatement callable =
					connection.prepareCall(
					"{?= call packGetTCTenure.funcGetTCTenure(?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);
			callable.registerOutParameter(2, Constants.CURSOR);
			callable.registerOutParameter(3, Types.VARCHAR);

			callable.execute();

			int errorCode = callable.getInt(1);
			String error = callable.getString(3);

			Log.log(Log.DEBUG,"IFDAO","getApplicationTenures",
				"errorCode and error " + errorCode + "," + error);

			if (errorCode == Constants.FUNCTION_FAILURE) {
				Log.log(Log.ERROR, "IFDAO", "getApplicationTenures", error);
				callable.close();
				callable = null;

				throw new DatabaseException(error);
			}

			ResultSet results=(ResultSet)callable.getObject(2);

			///*
			while(results.next())
			{
				String cgpan=results.getString(1);
				int month=results.getInt(2);

				tenures.put(cgpan,new Integer(month));
			}
			//*/

			/*tenures.put("CG200400002TC",new Integer(36));

			tenures.put("CG200400004TC",new Integer(36));*/
			results.close();
			results=null;

			callable.close();
			callable = null;
		}
		catch (SQLException e)
		{
			Log.log(Log.ERROR, "IFDAO", "getApplicationTenures", e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Unable to get TC application tenures.");
		}
		finally
		{
			DBConnection.freeConnection(connection);
		}

		Log.log(Log.INFO, "IFDAO", "getApplicationTenures", "Exited");
		return tenures;
	}

	public ArrayList getNPACgpans() throws DatabaseException
	{
		ArrayList cgpans=new ArrayList();
		Log.log(Log.INFO, "IFDAO", "getNPACgpans", "Entered");

		Connection connection = DBConnection.getConnection();

		try
		{
			CallableStatement callable =
					connection.prepareCall(
					"{?= call packGetNPACgpans.funcGetNPACgpans(?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);
			callable.registerOutParameter(2, Constants.CURSOR);
			callable.registerOutParameter(3, Types.VARCHAR);

			callable.execute();

			int errorCode = callable.getInt(1);
			String error = callable.getString(3);

			Log.log(Log.DEBUG,"IFDAO","getNPACgpans",
				"errorCode and error " + errorCode + "," + error);

			if (errorCode == Constants.FUNCTION_FAILURE) {
				Log.log(Log.ERROR, "IFDAO", "getNPACgpans", error);
				callable.close();
				callable = null;

				throw new DatabaseException(error);
			}

			ResultSet results=(ResultSet)callable.getObject(2);


			while(results.next())
			{
				String cgpan=results.getString(1);
				cgpans.add(cgpan);
			}

			/*
			cgpans.add("CG200400019WC");
			cgpans.add("CG200400001TC");
			cgpans.add("CG200400017WC");
			cgpans.add("CG200400003WC");
			*/
			results.close();
			results=null;

			callable.close();
			callable = null;
		}
		catch (SQLException e)
		{
			Log.log(Log.ERROR, "IFDAO", "getNPACgpans", e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Unable to get NPA cgpans.");
		}
		finally
		{
			DBConnection.freeConnection(connection);
		}

		Log.log(Log.INFO, "IFDAO", "getNPACgpans", "Exited");
		return cgpans;
	}
	public Map getOutstandingDetails() throws DatabaseException
	{
		Map outstandingDetails=new HashMap();
		Log.log(Log.INFO, "IFDAO", "getOutstandingDetails", "Entered");

		Connection connection = DBConnection.getConnection();

		try
		{
			CallableStatement callable =
					connection.prepareCall(
					"{?= call packGetOutStanding.funcGetOutStanding(?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);
			callable.registerOutParameter(2, Constants.CURSOR);
			callable.registerOutParameter(3, Types.VARCHAR);

			callable.execute();

			int errorCode = callable.getInt(1);
			String error = callable.getString(3);

			Log.log(Log.DEBUG,"IFDAO","getOutstandingDetails",
				"errorCode and error " + errorCode + "," + error);

			if (errorCode == Constants.FUNCTION_FAILURE) {
				Log.log(Log.ERROR, "IFDAO", "getOutstandingDetails", error);
				callable.close();
				callable = null;

				throw new DatabaseException(error);
			}

			ResultSet results=(ResultSet)callable.getObject(2);


			while(results.next())
			{
				String cgpan=results.getString(1);
				double outstanding=results.getDouble(3);
				outstandingDetails.put(cgpan,new Double(outstanding));
			}
			/*
			outstandingDetails.put("CG200400001TC",new Double(2000));
			outstandingDetails.put("CG200400002TC",new Double(100000));
			outstandingDetails.put("CG200400004TC",new Double(1655));
			outstandingDetails.put("CG200400003WC",new Double(500000));


			outstandingDetails.put("CG200400006WC",new Double(2000));
			outstandingDetails.put("CG200400007WC",new Double(4566));
			outstandingDetails.put("CG200400017WC",new Double(45000));
			*/



			results.close();
			results=null;

			callable.close();
			callable = null;
		}
		catch (SQLException e)
		{
			Log.log(Log.ERROR, "IFDAO", "getOutstandingDetails", e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Unable to get Outstanding details.");
		}
		finally
		{
			DBConnection.freeConnection(connection);
		}

		Log.log(Log.INFO, "IFDAO", "getOutstandingDetails", "Exited");
		return outstandingDetails;
	}


	public ArrayList statementReport(java.sql.Date endDate) throws DatabaseException
	{
		Log.log(Log.INFO,"IFDAO","statementReport","Entered");
		PreparedStatement danRaisedStmt = null;
		ArrayList danRaisedArray = new ArrayList();
		ResultSet danRaisedResult;
		Connection connection = DBConnection.getConnection();

		try
		{
			String query = "select distinct(ba.BAM_BANK_NAME) from" +
				" bank_statement_detail bs , bank_account_master ba, " +
				"bank_transaction_detail btd where bs.BAM_ID " +
				" = ba.BAM_ID and btd.bst_id=bs.bst_id and trunc(bs.BST_STATEMENT_DATE) = ?";
			danRaisedStmt = connection.prepareStatement(query);
			danRaisedStmt.setDate(1,endDate);
			danRaisedResult = danRaisedStmt.executeQuery();

			while(danRaisedResult.next())
			{
				//Instantiate a Gfee value object
				BankStatement gFee = new BankStatement();
				gFee.setBank(danRaisedResult.getString(1));

				danRaisedArray.add(gFee);
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
		Log.log(Log.INFO,"IFDAO","statementReport","Exited");
		return  danRaisedArray;
	}




	public ArrayList statementReportDetails(String cgpan, java.sql.Date endDate) throws DatabaseException
	{
		Log.log(Log.INFO,"IFDAO","statementReportDetails","Entered");
		PreparedStatement danRaisedStmt = null;
		ArrayList danRaisedArray = new ArrayList();
		ResultSet danRaisedResult;
		Connection connection = DBConnection.getConnection();

		try
		{
			String query = "select bs.BST_ID,bs.BAM_ID, bs.BST_ACCOUNT_NUMBER, " +
				" bs.BST_OPENING_BALANCE, bs.BST_CLOSING_BALANCE, " +
				" bs.BST_CREDIT_PENDING_FOR_DAY,btd.BTd_TRANSACTION_FROM_TO," +
				" btd.BTd_TRANSACTION_NATURE, btd.BTd_TRANSACTION_DT,btd.BTd_AMOUNT," +
				" bs.BST_REMARKS from  bank_statement_detail bs, bank_account_master ba," +
				" bank_transaction_detail btd where bs.BAM_ID = ba.BAM_ID and  " +
				" btd.bst_id=bs.bst_id and trunc(bs.BST_STATEMENT_DATE) = ? and" +
				" ba.BAM_BANK_NAME = ?";
			danRaisedStmt = connection.prepareStatement(query);
			danRaisedStmt.setString(2,cgpan);
			danRaisedStmt.setDate(1,endDate);
			danRaisedResult = danRaisedStmt.executeQuery();

			while(danRaisedResult.next())
			{
				//Instantiate a BankStatement value object
				BankStatement gFee = new BankStatement();
				gFee.setBstId(danRaisedResult.getString(1));
				gFee.setBamId(danRaisedResult.getString(2));
				gFee.setAccountNumber(danRaisedResult.getString(3));
				gFee.setOpeningBalance(danRaisedResult.getDouble(4));
				gFee.setClosingBalance(danRaisedResult.getDouble(5));
				gFee.setCreditPending(danRaisedResult.getDouble(6));
				gFee.setTransactionFrom(danRaisedResult.getString(7));
				gFee.setTransactionNature(danRaisedResult.getString(8));
				gFee.setTransactionDate(danRaisedResult.getDate(9));
				gFee.setBstAmount(danRaisedResult.getDouble(10));
				gFee.setRemarks(danRaisedResult.getString(11));

				danRaisedArray.add(gFee);
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
		Log.log(Log.INFO,"IFDAO","statementReportDetails","Exited");
		return  danRaisedArray;
	}



	public ArrayList getFdReport(java.sql.Date startDate,java.sql.Date endDate)
													throws DatabaseException
	{
		Log.log(Log.INFO,"IFDAO","getFdReport","Entered");
		PreparedStatement danRaisedStmt = null;
		ArrayList danRaisedArray = new ArrayList();
		ResultSet danRaisedResult;
		Connection connection = DBConnection.getConnection();

		if(startDate != null)
		{
			try
			{
				String query = "select i.IDT_RECEIPT_NUMBER from investment_detail i" +
					" where trunc(i.IDT_PURCHASE_DT) between ? and ? "
					+ " and i.INS_ID = 1 ";
				danRaisedStmt = connection.prepareStatement(query);
				danRaisedStmt.setDate(1,startDate);
				danRaisedStmt.setDate(2,endDate);
				danRaisedResult = danRaisedStmt.executeQuery();

				while(danRaisedResult.next())
				{
					//Instantiate a Gfee value object
					BankStatement gFee = new BankStatement();
					gFee.setBank(danRaisedResult.getString(1));

					danRaisedArray.add(gFee);
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
		}

		else if(startDate == null)
		{
			try
			{
				String query = "select i.IDT_RECEIPT_NUMBER from investment_detail i" +
					" where trunc(i.IDT_PURCHASE_DT) <= ? ";
				danRaisedStmt = connection.prepareStatement(query);
				danRaisedStmt.setDate(1,endDate);
				danRaisedResult = danRaisedStmt.executeQuery();

				while(danRaisedResult.next())
				{
					//Instantiate a Gfee value object
					BankStatement gFee = new BankStatement();
					gFee.setBank(danRaisedResult.getString(1));

					danRaisedArray.add(gFee);
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
		}

		Log.log(Log.INFO,"IFDAO","getFdReport","Exited");
		return  danRaisedArray;
	}




	public ArrayList fdReceiptDetails(String number,java.sql.Date startDate,
								 java.sql.Date endDate) throws DatabaseException
	{
		Log.log(Log.INFO,"IFDAO","fdReceiptDetails","Entered");
		PreparedStatement danRaisedStmt = null;
		ArrayList danRaisedArray = new ArrayList();
		ResultSet danRaisedResult;
		Connection connection = DBConnection.getConnection();

		if(startDate != null)
		{
			try
			{
				String query = "select inv.INV_NAME,ins.INS_TYPE, m.MAT_TYPE,r.RAT_RATING," +
					" i.IDT_COST_OF_PURCHASE, i.IDT_COMPOUNDING_FREQUENCY," +
					" i.IDT_INTEREST_RATE, i.IDT_TENURE_TYPE, i.IDT_PURCHASE_DT," +
					" i.IDT_MATURITY_AMOUNT, i.IDT_MATURITY_DT from investment_detail i," +
					" investee inv, rating_master r, instrument_master ins," +
					" maturity_master m where i.INS_ID = ins.INS_ID and " +
					" i.INV_ID = inv.INV_ID and i.MAT_ID = m.MAT_ID and i.RAT_ID = r.RAT_ID" +
					" and i.IDT_RECEIPT_NUMBER = ? and trunc(i.IDT_PURCHASE_DT) between ? and ?" +
					" and i.INS_ID = 1";
				danRaisedStmt = connection.prepareStatement(query);
				danRaisedStmt.setString(1,number);
				danRaisedStmt.setDate(3,endDate);
				danRaisedStmt.setDate(2,startDate);
				danRaisedResult = danRaisedStmt.executeQuery();

				while(danRaisedResult.next())
				{
					//Instantiate a FDReport value object
					FDReport gFee = new FDReport();
					gFee.setInvestee(danRaisedResult.getString(1));
					gFee.setInstrumentType(danRaisedResult.getString(2));
					gFee.setMaturityType(danRaisedResult.getString(3));
					gFee.setRating(danRaisedResult.getString(4));
					gFee.setPrincipalAmount(danRaisedResult.getDouble(5));
					gFee.setFrequency(danRaisedResult.getInt(6));
					gFee.setInterest(danRaisedResult.getInt(7));
					gFee.setTenureType(danRaisedResult.getString(8));
					gFee.setDepositDate(danRaisedResult.getDate(9));
					gFee.setMaturityAmount(danRaisedResult.getDouble(10));
					gFee.setMaturityDate(danRaisedResult.getDate(11));

					danRaisedArray.add(gFee);
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
		}
		else if(startDate == null)
		{
			try
			{
				String query = "select inv.INV_NAME,ins.INS_TYPE, m.MAT_TYPE,r.RAT_RATING," +
					" i.IDT_COST_OF_PURCHASE, i.IDT_COMPOUNDING_FREQUENCY," +
					" i.IDT_INTEREST_RATE, i.IDT_TENURE_TYPE, i.IDT_PURCHASE_DT," +
					" i.IDT_MATURITY_AMOUNT, i.IDT_MATURITY_DT from investment_detail i," +
					" investee inv, rating_master r, instrument_master ins," +
					" maturity_master m where i.INS_ID = ins.INS_ID and " +
					" i.INV_ID = inv.INV_ID and i.MAT_ID = m.MAT_ID and i.RAT_ID = r.RAT_ID" +
					" and i.IDT_RECEIPT_NUMBER = ? and trunc(i.IDT_PURCHASE_DT) <= ?";
				danRaisedStmt = connection.prepareStatement(query);
				danRaisedStmt.setString(1,number);
				danRaisedStmt.setDate(2,endDate);
				danRaisedResult = danRaisedStmt.executeQuery();

				while(danRaisedResult.next())
				{
					//Instantiate a FDReport value object
					FDReport gFee = new FDReport();
					gFee.setInvestee(danRaisedResult.getString(1));
					gFee.setInstrumentType(danRaisedResult.getString(2));
					gFee.setMaturityType(danRaisedResult.getString(3));
					gFee.setRating(danRaisedResult.getString(4));
					gFee.setPrincipalAmount(danRaisedResult.getDouble(5));
					gFee.setFrequency(danRaisedResult.getInt(6));
					gFee.setInterest(danRaisedResult.getInt(7));
					gFee.setTenureType(danRaisedResult.getString(8));
					gFee.setDepositDate(danRaisedResult.getDate(9));
					gFee.setMaturityAmount(danRaisedResult.getDouble(10));
					gFee.setMaturityDate(danRaisedResult.getDate(11));

					danRaisedArray.add(gFee);
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
		}
		Log.log(Log.INFO,"IFDAO","fdReceiptDetails","Exited");
		return  danRaisedArray;
	}

	//To fix bug 02092004-11

	public ArrayList getFdReportForDeposit(java.sql.Date startDate,
								 java.sql.Date endDate) throws DatabaseException
	{
		Log.log(Log.INFO,"IFDAO","getFdReportForDeposit","Entered");
		PreparedStatement danRaisedStmt = null;
		ArrayList danRaisedArray = new ArrayList();
		ResultSet danRaisedResult;
		Connection connection = DBConnection.getConnection();
		HashMap fdReport = new HashMap();

		if(startDate != null)
		{
			try
			{
				String query = "select inv.INV_NAME, m.MAT_TYPE, sum(i.IDT_COST_OF_PURCHASE)," +
					"sum(i.IDT_MATURITY_AMOUNT) from investee inv, maturity_master m," +
					" investment_detail i where i.MAT_ID = m.MAT_ID and" +
					" i.INV_ID = inv.INV_ID and trunc(i.IDT_PURCHASE_DT) between ? and ?" +
					" group by inv.INV_NAME, m.MAT_TYPE";
				danRaisedStmt = connection.prepareStatement(query);
				danRaisedStmt.setDate(2,endDate);
				danRaisedStmt.setDate(1,startDate);
				danRaisedResult = danRaisedStmt.executeQuery();

				while(danRaisedResult.next())

				{
					//Instantiate a FDReport value object
					String investee = danRaisedResult.getString(1);
					FDReport gFee = (FDReport)fdReport.get(investee);

					if(gFee == null)
					{
						gFee  = new FDReport();
						gFee.setInvestee(investee);
//						System.out.println(investee);
						gFee.setMaturityType(danRaisedResult.getString(2));
//						System.out.println(danRaisedResult.getString(2));
						gFee.setPrincipalAmount(danRaisedResult.getDouble(3));
//						System.out.println(danRaisedResult.getDouble(3));
						gFee.setMaturityAmount(danRaisedResult.getDouble(4));
//						System.out.println(danRaisedResult.getDouble(4));
						fdReport.put(investee,gFee);
					}

					else if(gFee != null)
					{
						gFee  = new FDReport();
						gFee.setMaturityType(danRaisedResult.getString(2));
//						System.out.println(danRaisedResult.getString(2));
						gFee.setPrincipalAmount(danRaisedResult.getDouble(3));
//						System.out.println(danRaisedResult.getDouble(3));
						gFee.setMaturityAmount(danRaisedResult.getDouble(4));
//						System.out.println(danRaisedResult.getDouble(4));

					}
					danRaisedArray.add(gFee);
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
		}
		else if(startDate == null)
		{
			try
			{
				String query = "select inv.INV_NAME, m.MAT_TYPE, sum(i.IDT_COST_OF_PURCHASE)," +
					"sum(i.IDT_MATURITY_AMOUNT) from investee inv, maturity_master m," +
					" investment_detail i where i.MAT_ID = m.MAT_ID and" +
					" i.INV_ID = inv.INV_ID and trunc(i.IDT_PURCHASE_DT) <= ?" +
					" group by inv.INV_NAME, m.MAT_TYPE";
				danRaisedStmt = connection.prepareStatement(query);
				danRaisedStmt.setDate(1,endDate);
				danRaisedResult = danRaisedStmt.executeQuery();

				while(danRaisedResult.next())
				{
					//Instantiate a FDReport value object
					String investee = danRaisedResult.getString(1);
					FDReport gFee = (FDReport)fdReport.get(investee);

					if(gFee == null)
					{
						gFee  = new FDReport();
						gFee.setInvestee(investee);
//						System.out.println(investee);
						gFee.setMaturityType(danRaisedResult.getString(2));
//						System.out.println(danRaisedResult.getString(2));
						gFee.setPrincipalAmount(danRaisedResult.getDouble(3));
//						System.out.println(danRaisedResult.getDouble(3));
						gFee.setMaturityAmount(danRaisedResult.getDouble(4));
//						System.out.println(danRaisedResult.getDouble(4));
						fdReport.put(investee,gFee);
					}

					else if(gFee != null)
					{
						gFee  = new FDReport();
						gFee.setMaturityType(danRaisedResult.getString(2));
//						System.out.println(danRaisedResult.getString(2));
						gFee.setPrincipalAmount(danRaisedResult.getDouble(3));
//						System.out.println(danRaisedResult.getDouble(3));
						gFee.setMaturityAmount(danRaisedResult.getDouble(4));
//						System.out.println(danRaisedResult.getDouble(4));

					}
					danRaisedArray.add(gFee);
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
		}
		Log.log(Log.INFO,"IFDAO","getFdReportForDeposit","Exited");
		return  danRaisedArray;
	}



	public ArrayList fdDetailsForDeposit(String investee,java.sql.Date startDate,
								 java.sql.Date endDate) throws DatabaseException
	{
		Log.log(Log.INFO,"IFDAO","fdDetailsForDeposit","Entered");
		PreparedStatement danRaisedStmt = null;
		ArrayList danRaisedArray = new ArrayList();
		ResultSet danRaisedResult;
		Connection connection = DBConnection.getConnection();
		HashMap fdReport = new HashMap();

		if(startDate != null)
		{
			try
			{
				String query = "select inv.INV_NAME, m.MAT_TYPE, i.IDT_COST_OF_PURCHASE," +
					"i.IDT_MATURITY_AMOUNT from investee inv, maturity_master m, " +
					"investment_detail i where i.MAT_ID = m.MAT_ID and i.INV_ID = inv.INV_ID" +
					" and trunc(i.IDT_PURCHASE_DT) between ? and ? and inv.INV_NAME = ?" +
					" group by inv.INV_NAME, m.MAT_TYPE, i.IDT_COST_OF_PURCHASE," +
					"i.IDT_MATURITY_AMOUNT";
				danRaisedStmt = connection.prepareStatement(query);
				danRaisedStmt.setDate(2,endDate);
				danRaisedStmt.setDate(1,startDate);
				danRaisedStmt.setString(3,investee);
				danRaisedResult = danRaisedStmt.executeQuery();

				while(danRaisedResult.next())

				{
					//Instantiate a FDReport value object
					String investeeName = danRaisedResult.getString(1);
					FDReport gFee = (FDReport)fdReport.get(investeeName);

					if(gFee == null)
					{
						gFee  = new FDReport();
						gFee.setInvestee(investee);
//						System.out.println(investee);
						gFee.setMaturityType(danRaisedResult.getString(2));
//						System.out.println(danRaisedResult.getString(2));
						gFee.setPrincipalAmount(danRaisedResult.getDouble(3));
//						System.out.println(danRaisedResult.getDouble(3));
						gFee.setMaturityAmount(danRaisedResult.getDouble(4));
//						System.out.println(danRaisedResult.getDouble(4));
						fdReport.put(investee,gFee);
					}

					else if(gFee != null)
					{
						gFee  = new FDReport();
						gFee.setMaturityType(danRaisedResult.getString(2));
//						System.out.println(danRaisedResult.getString(2));
						gFee.setPrincipalAmount(danRaisedResult.getDouble(3));
//						System.out.println(danRaisedResult.getDouble(3));
						gFee.setMaturityAmount(danRaisedResult.getDouble(4));
//						System.out.println(danRaisedResult.getDouble(4));

					}
					danRaisedArray.add(gFee);
				}
				danRaisedResult.close();
				danRaisedResult=null;
				danRaisedStmt.close();
				danRaisedStmt=null;
/*
				while(danRaisedResult.next())
				{
					//Instantiate a FDReport value object
					FDReport gFee = new FDReport();
					gFee.setInvestee(danRaisedResult.getString(1));
					gFee.setMaturityType(danRaisedResult.getString(2));
					gFee.setPrincipalAmount(danRaisedResult.getDouble(3));
					gFee.setMaturityAmount(danRaisedResult.getDouble(4));

					danRaisedArray.add(gFee);
				}
*/			}
			catch(Exception exception)
			{
				throw new DatabaseException(exception.getMessage());
			}
			finally
			{
				DBConnection.freeConnection(connection);
			}
		}
		else if(startDate == null)
		{
			try
			{
				String query = "select inv.INV_NAME, m.MAT_TYPE, i.IDT_COST_OF_PURCHASE," +
					"i.IDT_MATURITY_AMOUNT from investee inv, maturity_master m, " +
					"investment_detail i where i.MAT_ID = m.MAT_ID and i.INV_ID = inv.INV_ID" +
					" and trunc(i.IDT_PURCHASE_DT) <= ? and inv.INV_NAME = ?" +
					" group by inv.INV_NAME, m.MAT_TYPE, i.IDT_COST_OF_PURCHASE," +
					"i.IDT_MATURITY_AMOUNT";
				danRaisedStmt = connection.prepareStatement(query);
				danRaisedStmt.setDate(1,endDate);
				danRaisedStmt.setString(2,investee);
				danRaisedResult = danRaisedStmt.executeQuery();

				while(danRaisedResult.next())
				{
					//Instantiate a FDReport value object
					String investeeName = danRaisedResult.getString(1);
					FDReport gFee = (FDReport)fdReport.get(investeeName);

					if(gFee == null)
					{
						gFee  = new FDReport();
						gFee.setInvestee(investee);
//						System.out.println(investee);
						gFee.setMaturityType(danRaisedResult.getString(2));
//						System.out.println(danRaisedResult.getString(2));
						gFee.setPrincipalAmount(danRaisedResult.getDouble(3));
//						System.out.println(danRaisedResult.getDouble(3));
						gFee.setMaturityAmount(danRaisedResult.getDouble(4));
//						System.out.println(danRaisedResult.getDouble(4));
						fdReport.put(investee,gFee);
					}

					else if(gFee != null)
					{
						gFee  = new FDReport();
						gFee.setMaturityType(danRaisedResult.getString(2));
//						System.out.println(danRaisedResult.getString(2));
						gFee.setPrincipalAmount(danRaisedResult.getDouble(3));
//						System.out.println(danRaisedResult.getDouble(3));
						gFee.setMaturityAmount(danRaisedResult.getDouble(4));
//						System.out.println(danRaisedResult.getDouble(4));

					}
					danRaisedArray.add(gFee);
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
		}
		Log.log(Log.INFO,"IFDAO","fdDetailsForDeposit","Exited");
		return  danRaisedArray;
	}


	public ArrayList getFdReportForMaturity(java.sql.Date startDate,
								 java.sql.Date endDate) throws DatabaseException
	{
		Log.log(Log.INFO,"IFDAO","getFdReportForMaturity","Entered");
		PreparedStatement danRaisedStmt = null;
		ArrayList danRaisedArray = new ArrayList();
		ResultSet danRaisedResult;
		HashMap fdReport = new HashMap();
		Connection connection = DBConnection.getConnection();



		if(startDate != null)
		{
			try
			{
				String query = "select inv.INV_NAME, m.MAT_TYPE, sum(i.IDT_COST_OF_PURCHASE)," +
					"sum(i.IDT_MATURITY_AMOUNT) from investee inv, maturity_master m," +
					" investment_detail i where i.MAT_ID = m.MAT_ID and i.INV_ID = inv.INV_ID " +
					"and trunc(i.IDT_MATURITY_DT) between ? and ? group by inv.INV_NAME, m.MAT_TYPE";
//				System.out.println(query);
				danRaisedStmt = connection.prepareStatement(query);
				danRaisedStmt.setDate(2,endDate);
				danRaisedStmt.setDate(1,startDate);
				danRaisedResult = danRaisedStmt.executeQuery();

				while(danRaisedResult.next())

				{
					//Instantiate a FDReport value object
					String investee = danRaisedResult.getString(1);
					FDReport gFee = (FDReport)fdReport.get(investee);

					if(gFee == null)
					{
						gFee  = new FDReport();
						gFee.setInvestee(investee);
//						System.out.println(investee);
						gFee.setMaturityType(danRaisedResult.getString(2));
//						System.out.println(danRaisedResult.getString(2));
						gFee.setPrincipalAmount(danRaisedResult.getDouble(3));
//						System.out.println(danRaisedResult.getDouble(3));
						gFee.setMaturityAmount(danRaisedResult.getDouble(4));
//						System.out.println(danRaisedResult.getDouble(4));
						fdReport.put(investee,gFee);
					}

					else if(gFee != null)
					{
						gFee  = new FDReport();
						gFee.setMaturityType(danRaisedResult.getString(2));
//						System.out.println(danRaisedResult.getString(2));
						gFee.setPrincipalAmount(danRaisedResult.getDouble(3));
//						System.out.println(danRaisedResult.getDouble(3));
						gFee.setMaturityAmount(danRaisedResult.getDouble(4));
//						System.out.println(danRaisedResult.getDouble(4));

					}
					danRaisedArray.add(gFee);
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
		}
		else if(startDate == null)
		{
			try
			{
				String query = "select inv.INV_NAME, m.MAT_TYPE, sum(i.IDT_COST_OF_PURCHASE)," +
					"sum(i.IDT_MATURITY_AMOUNT) from investee inv, maturity_master m," +
					" investment_detail i where i.MAT_ID = m.MAT_ID and i.INV_ID = inv.INV_ID " +
					"and trunc(i.IDT_MATURITY_DT) <= ? group by inv.INV_NAME, m.MAT_TYPE";
				danRaisedStmt = connection.prepareStatement(query);
				danRaisedStmt.setDate(1,endDate);
				danRaisedResult = danRaisedStmt.executeQuery();
				boolean firstTime = true;

				while(danRaisedResult.next())

				{
					//Instantiate a FDReport value object
					String investee = danRaisedResult.getString(1);
					FDReport gFee = (FDReport)fdReport.get(investee);

					if(gFee == null)
					{
						gFee  = new FDReport();
						gFee.setInvestee(investee);
//						System.out.println(investee);
						gFee.setMaturityType(danRaisedResult.getString(2));
//						System.out.println(danRaisedResult.getString(2));
						gFee.setPrincipalAmount(danRaisedResult.getDouble(3));
//						System.out.println(danRaisedResult.getDouble(3));
						gFee.setMaturityAmount(danRaisedResult.getDouble(4));
//						System.out.println(danRaisedResult.getDouble(4));
						fdReport.put(investee,gFee);
					}

					else if(gFee != null)
					{
						gFee  = new FDReport();
						gFee.setMaturityType(danRaisedResult.getString(2));
//						System.out.println(danRaisedResult.getString(2));
						gFee.setPrincipalAmount(danRaisedResult.getDouble(3));
//						System.out.println(danRaisedResult.getDouble(3));
						gFee.setMaturityAmount(danRaisedResult.getDouble(4));
//						System.out.println(danRaisedResult.getDouble(4));

					}
					danRaisedArray.add(gFee);
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
		}

		Log.log(Log.INFO,"IFDAO","getFdReportForMaturity","Exited");
		return  danRaisedArray;
	}


	public ArrayList fdDetailsForMaturity(String investee,java.sql.Date startDate,
								 java.sql.Date endDate) throws DatabaseException
	{
		Log.log(Log.INFO,"IFDAO","fdDetailsForMaturity","Entered");
		PreparedStatement danRaisedStmt = null;
		ArrayList danRaisedArray = new ArrayList();
		ResultSet danRaisedResult;
		Connection connection = DBConnection.getConnection();
		HashMap fdReport = new HashMap();

		if(startDate != null)
		{
			try
			{
				String query = "select inv.INV_NAME, m.MAT_TYPE, i.IDT_COST_OF_PURCHASE," +
					" i.IDT_MATURITY_AMOUNT from investee inv, maturity_master m," +
					" investment_detail i where i.MAT_ID = m.MAT_ID and " +
					" i.INV_ID = inv.INV_ID and trunc(i.IDT_MATURITY_DT) between ? and ?" +
					" and inv.INV_NAME = ? group by inv.INV_NAME, m.MAT_TYPE," +
					" i.IDT_COST_OF_PURCHASE,i.IDT_MATURITY_AMOUNT" ;
				danRaisedStmt = connection.prepareStatement(query);
				danRaisedStmt.setDate(2,endDate);
				danRaisedStmt.setDate(1,startDate);
				danRaisedStmt.setString(3,investee);
				danRaisedResult = danRaisedStmt.executeQuery();

				while(danRaisedResult.next())
				{
					//Instantiate a FDReport value object
					String investeeName = danRaisedResult.getString(1);
					FDReport gFee = (FDReport)fdReport.get(investeeName);

					if(gFee == null)
					{
						gFee  = new FDReport();
						gFee.setInvestee(investee);
//						System.out.println(investee);
						gFee.setMaturityType(danRaisedResult.getString(2));
//						System.out.println(danRaisedResult.getString(2));
						gFee.setPrincipalAmount(danRaisedResult.getDouble(3));
//						System.out.println(danRaisedResult.getDouble(3));
						gFee.setMaturityAmount(danRaisedResult.getDouble(4));
//						System.out.println(danRaisedResult.getDouble(4));
						fdReport.put(investee,gFee);
					}

					else if(gFee != null)
					{
						gFee  = new FDReport();
						gFee.setMaturityType(danRaisedResult.getString(2));
//						System.out.println(danRaisedResult.getString(2));
						gFee.setPrincipalAmount(danRaisedResult.getDouble(3));
//						System.out.println(danRaisedResult.getDouble(3));
						gFee.setMaturityAmount(danRaisedResult.getDouble(4));
//						System.out.println(danRaisedResult.getDouble(4));

					}
					danRaisedArray.add(gFee);
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
		}
		else if(startDate == null)
		{
			try
			{
				String query = "select inv.INV_NAME, m.MAT_TYPE, i.IDT_COST_OF_PURCHASE," +
					" i.IDT_MATURITY_AMOUNT from investee inv, maturity_master m," +
					" investment_detail i where i.MAT_ID = m.MAT_ID and " +
					" i.INV_ID = inv.INV_ID and trunc(i.IDT_MATURITY_DT) <= ?" +
					" and inv.INV_NAME = ? group by inv.INV_NAME, m.MAT_TYPE," +
					" i.IDT_COST_OF_PURCHASE,i.IDT_MATURITY_AMOUNT"  ;

				danRaisedStmt = connection.prepareStatement(query);
				danRaisedStmt.setDate(1,endDate);
				danRaisedStmt.setString(2,investee);
				danRaisedResult = danRaisedStmt.executeQuery();

				while(danRaisedResult.next())
				{
					//Instantiate a FDReport value object
					String investeeName = danRaisedResult.getString(1);
					FDReport gFee = (FDReport)fdReport.get(investeeName);

					if(gFee == null)
					{
						gFee  = new FDReport();
						gFee.setInvestee(investee);
//						System.out.println(investee);
						gFee.setMaturityType(danRaisedResult.getString(2));
//						System.out.println(danRaisedResult.getString(2));
						gFee.setPrincipalAmount(danRaisedResult.getDouble(3));
//						System.out.println(danRaisedResult.getDouble(3));
						gFee.setMaturityAmount(danRaisedResult.getDouble(4));
//						System.out.println(danRaisedResult.getDouble(4));
						fdReport.put(investee,gFee);
					}

					else if(gFee != null)
					{
						gFee  = new FDReport();
						gFee.setMaturityType(danRaisedResult.getString(2));
//						System.out.println(danRaisedResult.getString(2));
						gFee.setPrincipalAmount(danRaisedResult.getDouble(3));
//						System.out.println(danRaisedResult.getDouble(3));
						gFee.setMaturityAmount(danRaisedResult.getDouble(4));
//						System.out.println(danRaisedResult.getDouble(4));

					}
					danRaisedArray.add(gFee);
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
		}
		Log.log(Log.INFO,"IFDAO","fdDetailsForMaturity","Exited");
		return  danRaisedArray;
	}

	// Fix Completed

	public InvesteeDetail getInvesteeDetails(String invGrp, String invName) throws DatabaseException {
		Log.log(Log.INFO, "IFDAO", "getInvesteeDetails", "Entered");

		Connection conn = null;
		CallableStatement callableStmt = null;
		int status = -1;
		String errorCode = null;
		InvesteeDetail invDetail = new InvesteeDetail();
		try {
			Log.log(Log.INFO, "IFDAO", "getInvesteeDetails", "investee group " + invGrp);
			Log.log(Log.INFO, "IFDAO", "getInvesteeDetails", "investee name " + invName);

			conn = DBConnection.getConnection();
			callableStmt =
				conn.prepareCall(
					"{?=call funcGetInvesteeDetail(?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt.setString(2, invGrp);
			callableStmt.setString(3, invName);
			callableStmt.registerOutParameter(4, java.sql.Types.DOUBLE);
			callableStmt.registerOutParameter(5, java.sql.Types.DOUBLE);
			callableStmt.registerOutParameter(6, java.sql.Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(6);
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"getInvesteeDetails",
				"Error code and error " + status + " " + errorCode);
			if (status == Constants.FUNCTION_FAILURE) {
				callableStmt.close();
				callableStmt = null;
				Log.log(Log.ERROR, "IFDAO", "getInvesteeDetails", errorCode);
				throw new DatabaseException(errorCode);
			}
			double invNetWorth = callableStmt.getDouble(4);
			double invTanAssets = callableStmt.getDouble(5);

			callableStmt.close();
			callableStmt = null;

			invDetail.setInvestee(invName);
			invDetail.setInvesteeGroup(invGrp);
			invDetail.setInvesteeNetWorth(invNetWorth);
			invDetail.setInvesteeTangibleAssets(invTanAssets);

		} catch (SQLException e) {
			Log.log(Log.ERROR, "IFDAO", "getInvesteeDetails", e.getMessage());
			Log.logException(e);
			throw new DatabaseException("Unable to get InvesteeDetails");
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(Log.INFO, "IFDAO", "getInvesteeDetails", "Exited");

		return invDetail;
	}

	public MaturityDetail getMaturityDetails(String matType) throws DatabaseException {
		Log.log(Log.INFO, "IFDAO", "getMaturityDetails", "Entered");

		Connection conn = null;
		CallableStatement callableStmt = null;
		int status = -1;
		String errorCode = null;
		MaturityDetail matDetail = new MaturityDetail();
		try {
			Log.log(Log.INFO, "IFDAO", "getMaturityDetails", "maturity type " + matType);

			conn = DBConnection.getConnection();
			callableStmt =
				conn.prepareCall(
					"{?=call funcGetMaturityDetail(?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt.setString(2, matType);
			callableStmt.registerOutParameter(3, java.sql.Types.VARCHAR);
			callableStmt.registerOutParameter(4, java.sql.Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(4);
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"getInvesteeDetails",
				"Error code and error " + status + " " + errorCode);
			if (status == Constants.FUNCTION_FAILURE) {
				callableStmt.close();
				callableStmt = null;
				Log.log(Log.ERROR, "IFDAO", "getMaturityDetails", errorCode);
				throw new DatabaseException(errorCode);
			}
			String matDesc = callableStmt.getString(3);

			callableStmt.close();
			callableStmt = null;

			matDetail.setMaturityType(matType);
			matDetail.setMaturityDescription(matDesc);

		} catch (SQLException e) {
			Log.log(Log.ERROR, "IFDAO", "getMaturityDetails", e.getMessage());
			Log.logException(e);
			throw new DatabaseException("Unable to get MaturityDetails");
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(Log.INFO, "IFDAO", "getMaturityDetails", "Exited");

		return matDetail;
	}

	public InstrumentDetail getInstrumentDetail(String instName) throws DatabaseException {
		Log.log(Log.INFO, "IFDAO", "getInstrumentDetail", "Entered");

		Connection conn = null;
		CallableStatement callableStmt = null;
		int status = -1;
		String errorCode = null;
		InstrumentDetail instDetail = new InstrumentDetail();
		try {
			Log.log(Log.INFO, "IFDAO", "getInstrumentDetail", "instrument name " + instName);

			conn = DBConnection.getConnection();
			callableStmt =
				conn.prepareCall(
					"{?=call funcGetInstrumentDetail(?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt.setString(2, instName);
			callableStmt.registerOutParameter(3, java.sql.Types.VARCHAR);
			callableStmt.registerOutParameter(4, java.sql.Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(4);
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"getInstrumentDetail",
				"Error code and error " + status + " " + errorCode);
			if (status == Constants.FUNCTION_FAILURE) {
				callableStmt.close();
				callableStmt = null;
				Log.log(Log.ERROR, "IFDAO", "getInstrumentDetail", errorCode);
				throw new DatabaseException(errorCode);
			}
			String instDesc = callableStmt.getString(3);

			instDetail.setInstrumentName(instName);
			instDetail.setInstrumentDescription(instDesc);

			callableStmt.close();
			callableStmt = null;

		} catch (SQLException e) {
			Log.log(Log.ERROR, "IFDAO", "getInstrumentDetail", e.getMessage());
			Log.logException(e);
			throw new DatabaseException("Unable to get Instrument Details");
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(Log.INFO, "IFDAO", "getInstrumentDetail", "Exited");

		return instDetail;
	}

	public InstrumentFeature getInstFeaturesDetails(String instFeature) throws DatabaseException {
		Log.log(Log.INFO, "IFDAO", "getInstFeaturesDetails", "Entered");

		Connection conn = null;
		CallableStatement callableStmt = null;
		int status = -1;
		String errorCode = null;
		InstrumentFeature instrumentFeature = new InstrumentFeature();
		try {
			Log.log(Log.INFO, "IFDAO", "getInstFeaturesDetails", "instrument feature " + instFeature);

			conn = DBConnection.getConnection();
			callableStmt =
				conn.prepareCall(
					"{?=call funcGetInstFeatureDetail(?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt.setString(2, instFeature);
			callableStmt.registerOutParameter(3, java.sql.Types.VARCHAR);
			callableStmt.registerOutParameter(4, java.sql.Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(4);
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"getInstFeaturesDetails",
				"Error code and error " + status + " " + errorCode);
			if (status == Constants.FUNCTION_FAILURE) {
				callableStmt.close();
				callableStmt = null;
				Log.log(Log.ERROR, "IFDAO", "getInstFeaturesDetails", errorCode);
				throw new DatabaseException(errorCode);
			}
			String instDesc = callableStmt.getString(3);

			callableStmt.close();
			callableStmt = null;

			instrumentFeature.setInstrumentFeatures(instFeature);
			instrumentFeature.setModInstrumentFeatures(instFeature);
			instrumentFeature.setInstrumentFeatureDescription(instDesc);

		} catch (SQLException e) {
			Log.log(Log.ERROR, "IFDAO", "getInstFeaturesDetails", e.getMessage());
			Log.logException(e);
			throw new DatabaseException("Unable to get Instrument Feature Details");
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(Log.INFO, "IFDAO", "getInstFeaturesDetails", "Exited");

		return instrumentFeature;
	}

	public String getInstSchemeDetails(String instScheme) throws DatabaseException {
			Log.log(Log.INFO, "IFDAO", "getInstFeaturesDetails", "Entered");

			Connection conn = null;
			CallableStatement callableStmt = null;
			int status = -1;
			String errorCode = null;
			String instDesc = "";
			try {
				Log.log(Log.INFO, "IFDAO", "getInstSchemeDetails", "instrument scheme " + instScheme);

				conn = DBConnection.getConnection();
				callableStmt =
					conn.prepareCall(
						"{?=call funcGetInstSchemeDetail(?,?,?)}");
				callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
				callableStmt.setString(2, instScheme);
				callableStmt.registerOutParameter(3, java.sql.Types.VARCHAR);
				callableStmt.registerOutParameter(4, java.sql.Types.VARCHAR);

				callableStmt.execute();
				status = callableStmt.getInt(1);
				errorCode = callableStmt.getString(4);
				Log.log(
					Log.DEBUG,
					"IFDAO",
					"getInstSchemeDetails",
					"Error code and error " + status + " " + errorCode);
				if (status == Constants.FUNCTION_FAILURE) {
					callableStmt.close();
					callableStmt = null;
					Log.log(Log.ERROR, "IFDAO", "getInstSchemeDetails", errorCode);
					throw new DatabaseException(errorCode);
				}
				instDesc = callableStmt.getString(3);

				callableStmt.close();
				callableStmt = null;

			} catch (SQLException e) {
				Log.log(Log.ERROR, "IFDAO", "getInstSchemeDetails", e.getMessage());
				Log.logException(e);
				throw new DatabaseException("Unable to get Instrument Scheme Details");
			} finally {
				DBConnection.freeConnection(conn);
			}
			Log.log(Log.INFO, "IFDAO", "getInstSchemeDetails", "Exited");

			return instDesc;
		}

		public Hashtable getRatingDetails(String rating) throws DatabaseException {
				Log.log(Log.INFO, "IFDAO", "getRatingDetails", "Entered");

				Connection conn = null;
				CallableStatement callableStmt = null;
				int status = -1;
				String errorCode = null;
				Hashtable ratingDetails = new Hashtable();
				try {
					Log.log(Log.INFO, "IFDAO", "getRatingDetails", "rating " + rating);

					conn = DBConnection.getConnection();
					callableStmt =
						conn.prepareCall(
							"{?=call funcGetRatingDetail(?,?,?,?)}");
					callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
					callableStmt.setString(2, rating);
					callableStmt.registerOutParameter(3, java.sql.Types.VARCHAR);
					callableStmt.registerOutParameter(4, java.sql.Types.VARCHAR);
					callableStmt.registerOutParameter(5, java.sql.Types.VARCHAR);

					callableStmt.execute();
					status = callableStmt.getInt(1);
					errorCode = callableStmt.getString(5);
					Log.log(
						Log.DEBUG,
						"IFDAO",
						"getInstSchemeDetails",
						"Error code and error " + status + " " + errorCode);
					if (status == Constants.FUNCTION_FAILURE) {
						callableStmt.close();
						callableStmt = null;
						Log.log(Log.ERROR, "IFDAO", "getRatingDetails", errorCode);
						throw new DatabaseException(errorCode);
					}
					ratingDetails.put("Description", callableStmt.getString(3));
					//ratingDetails.put("Given By", callableStmt.getString(4));

					callableStmt.close();
					callableStmt = null;

				} catch (SQLException e) {
					Log.log(Log.ERROR, "IFDAO", "getRatingDetails", e.getMessage());
					Log.logException(e);
					throw new DatabaseException("Unable to get Rating Details");
				} finally {
					DBConnection.freeConnection(conn);
				}
				Log.log(Log.INFO, "IFDAO", "getRatingDetails", "Exited");

				return ratingDetails;
			}

		public ArrayList getCorpusList(Date fromDate, Date toDate) throws DatabaseException {
				Log.log(Log.INFO, "IFDAO", "getCorpusList", "Entered");

				Connection conn = null;
				CallableStatement callableStmt = null;
				int status = -1;
				String errorCode = null;
				ArrayList corpusDetails = new ArrayList();
				try {
					Log.log(Log.INFO, "IFDAO", "getCorpusList", "from date " + fromDate);
					Log.log(Log.INFO, "IFDAO", "getCorpusList", "to date " + toDate);

					conn = DBConnection.getConnection();
					callableStmt =
						conn.prepareCall(
							"{?=call packGetCorpusList.funcGetCorpusList(?,?,?,?)}");
					callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
					callableStmt.setDate(2, new java.sql.Date(fromDate.getTime()));
					if (toDate==null || toDate.toString().equals(""))
					{
						System.out.println("null");
						callableStmt.setDate(3, new java.sql.Date((new java.util.Date()).getTime()));
					}
					else
					{
						System.out.println("not null");
						callableStmt.setDate(3, new java.sql.Date(toDate.getTime()));
					}
					callableStmt.registerOutParameter(4, Constants.CURSOR);
					callableStmt.registerOutParameter(5, java.sql.Types.VARCHAR);

					callableStmt.execute();
					status = callableStmt.getInt(1);
					errorCode = callableStmt.getString(5);
					Log.log(
						Log.DEBUG,
						"IFDAO",
						"getInstSchemeDetails",
						"Error code and error " + status + " " + errorCode);
					if (status == Constants.FUNCTION_FAILURE) {
						callableStmt.close();
						callableStmt = null;
						Log.log(Log.ERROR, "IFDAO", "getCorpusList", errorCode);
						throw new DatabaseException(errorCode);
					}
					ResultSet result = (ResultSet)callableStmt.getObject(4);
					CorpusDetail corpusDetail = new CorpusDetail();
					while (result.next())
					{
						System.out.println("adding");
						corpusDetail=new CorpusDetail();
						corpusDetail.setCorpusId(result.getString(1));
						corpusDetail.setCorpusContributor(result.getString(2));
						corpusDetail.setCorpusAmount(result.getDouble(3));
						corpusDetail.setCorpusDate(result.getDate(4));

						System.out.println(corpusDetail.getCorpusId());
						System.out.println(corpusDetail.getCorpusContributor());
						System.out.println(corpusDetail.getCorpusAmount());
						System.out.println(corpusDetail.getCorpusDate());

						corpusDetails.add(corpusDetail);
						corpusDetail=null;
					}
					result.close();
					result=null;

					callableStmt.close();
					callableStmt = null;

				} catch (SQLException e) {
					Log.log(Log.ERROR, "IFDAO", "getCorpusList", e.getMessage());
					Log.logException(e);
					throw new DatabaseException("Unable to get corpus list");
				} finally {
					DBConnection.freeConnection(conn);
				}
				Log.log(Log.INFO, "IFDAO", "getCorpusList", "Exited");

				return corpusDetails;
			}

		public CorpusDetail getCorpusDetails(String corpusId) throws DatabaseException {
				Log.log(Log.INFO, "IFDAO", "getCorpusDetails", "Entered");

				Connection conn = null;
				CallableStatement callableStmt = null;
				int status = -1;
				String errorCode = null;
				CorpusDetail corpusDetail = new CorpusDetail();
				try {
					Log.log(Log.INFO, "IFDAO", "getCorpusDetails", "corpus id " + corpusId);

					conn = DBConnection.getConnection();
					callableStmt =
						conn.prepareCall(
							"{?=call funcGetCorpusDetails(?,?,?,?,?)}");
					callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
					callableStmt.setString(2, corpusId);
					callableStmt.registerOutParameter(3, java.sql.Types.VARCHAR);
					callableStmt.registerOutParameter(4, java.sql.Types.DOUBLE);
					callableStmt.registerOutParameter(5, java.sql.Types.DATE);
					callableStmt.registerOutParameter(6, java.sql.Types.VARCHAR);

					callableStmt.execute();
					status = callableStmt.getInt(1);
					errorCode = callableStmt.getString(6);
					Log.log(
						Log.DEBUG,
						"IFDAO",
						"getCorpusDetails",
						"Error code and error " + status + " " + errorCode);
					if (status == Constants.FUNCTION_FAILURE) {
						callableStmt.close();
						callableStmt = null;
						Log.log(Log.ERROR, "IFDAO", "getCorpusDetails", errorCode);
						throw new DatabaseException(errorCode);
					}

					corpusDetail.setCorpusContributor(callableStmt.getString(3));
					corpusDetail.setCorpusAmount(callableStmt.getDouble(4));
					corpusDetail.setCorpusDate(DateHelper.sqlToUtilDate( callableStmt.getDate(5)));
					corpusDetail.setCorpusId(corpusId);

					callableStmt.close();
					callableStmt = null;

				} catch (SQLException e) {
					Log.log(Log.ERROR, "IFDAO", "getCorpusDetails", e.getMessage());
					Log.logException(e);
					throw new DatabaseException("Unable to get corpus details");
				} finally {
					DBConnection.freeConnection(conn);
				}
				Log.log(Log.INFO, "IFDAO", "getCorpusDetails", "Exited");

				return corpusDetail;
			}

		public ArrayList getHolidayDates() throws DatabaseException {
				Log.log(Log.INFO, "IFDAO", "getHolidayDates", "Entered");

				Connection conn = null;
				CallableStatement callableStmt = null;
				int status = -1;
				String errorCode = null;
				ArrayList holidays = new ArrayList();
				try {
					conn = DBConnection.getConnection();
					callableStmt =
						conn.prepareCall(
							"{?=call packGetHolidayList.funcGetHolidayList(?,?)}");
					callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
					callableStmt.registerOutParameter(2, Constants.CURSOR);
					callableStmt.registerOutParameter(3, java.sql.Types.VARCHAR);

					callableStmt.execute();
					status = callableStmt.getInt(1);
					errorCode = callableStmt.getString(3);
					Log.log(
						Log.DEBUG,
						"IFDAO",
						"getHolidayDates",
						"Error code and error " + status + " " + errorCode);
					if (status == Constants.FUNCTION_FAILURE) {
						callableStmt.close();
						callableStmt = null;
						Log.log(Log.ERROR, "IFDAO", "getHolidayDates", errorCode);
						throw new DatabaseException(errorCode);
					}
					ResultSet result = (ResultSet)callableStmt.getObject(2);
					while (result.next())
					{
						Date date=result.getDate(1);
						holidays.add((new SimpleDateFormat("dd/MM/yyyy")).format(date));
					}
					result.close();
					result=null;

					callableStmt.close();
					callableStmt = null;

				} catch (SQLException e) {
					Log.log(Log.ERROR, "IFDAO", "getHolidayDates", e.getMessage());
					Log.logException(e);
					throw new DatabaseException("Unable to get holiday list");
				} finally {
					DBConnection.freeConnection(conn);
				}
				Log.log(Log.INFO, "IFDAO", "getHolidayDates", "Exited");

				return holidays;
			}

		public String getHolidayDetail(Date holDate) throws DatabaseException {
				Log.log(Log.INFO, "IFDAO", "getHolidayDetail", "Entered");

				Connection conn = null;
				CallableStatement callableStmt = null;
				int status = -1;
				String errorCode = null;
				String holidayDesc = "";
				try {
					conn = DBConnection.getConnection();
					callableStmt =
						conn.prepareCall(
							"{?=call funcGetHolidayDetail(?,?,?)}");
					callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
					callableStmt.setDate(2, new java.sql.Date(holDate.getTime()));
					callableStmt.registerOutParameter(3, java.sql.Types.VARCHAR);
					callableStmt.registerOutParameter(4, java.sql.Types.VARCHAR);

					callableStmt.execute();
					status = callableStmt.getInt(1);
					errorCode = callableStmt.getString(4);
					Log.log(
						Log.DEBUG,
						"IFDAO",
						"getHolidayDates",
						"Error code and error " + status + " " + errorCode);
					if (status == Constants.FUNCTION_FAILURE) {
						callableStmt.close();
						callableStmt = null;
						Log.log(Log.ERROR, "IFDAO", "getHolidayDetail", errorCode);
						throw new DatabaseException(errorCode);
					}
					holidayDesc=callableStmt.getString(3);

					callableStmt.close();
					callableStmt = null;

				} catch (SQLException e) {
					Log.log(Log.ERROR, "IFDAO", "getHolidayDetail", e.getMessage());
					Log.logException(e);
					throw new DatabaseException("Unable to get holiday detail");
				} finally {
					DBConnection.freeConnection(conn);
				}
				Log.log(Log.INFO, "IFDAO", "getHolidayDetail", "Exited");

				return holidayDesc;
			}

		/**
		 * This method is used to add a new Holiday entity into the Holiday Master table.
		 *
		 * The return type for this method can be a boolean true or false. If there is an
		 * exception, the method may return a false.
		 * @param holidayDetails
		 * @return boolean
		 */
		public void updateHolidayMaster(Hashtable holidayDetails)
			throws DatabaseException {
			String holidayDate = null;
			String modHolidayDate = null;
			String holidayDescription = null;
			CallableStatement callablestmt = null;
			Connection conn = null;
			java.sql.Date sqlDate = null;
			java.sql.Date modDate = null;
			String errorCode = null;
			int status = -1;
			String updatedBy = null;

			if (holidayDetails.containsKey("Holiday Date")) {
				holidayDate = (String) holidayDetails.get("Holiday Date");
				sqlDate =
					java.sql.Date.valueOf(DateHelper.stringToSQLdate(holidayDate));
			}

			if (holidayDetails.containsKey("Mod Holiday Date")) {
				modHolidayDate = (String) holidayDetails.get("Mod Holiday Date");
				modDate =
					java.sql.Date.valueOf(DateHelper.stringToSQLdate(modHolidayDate));
			}

			if (holidayDetails.containsKey("Holiday Description")) {
				holidayDescription =
					((String) holidayDetails.get("Holiday Description")).trim();
			}

			if (holidayDetails.containsKey("Updated By")) {
				updatedBy = (String) holidayDetails.get("Updated By");
			}
			try {
				conn = DBConnection.getConnection();
				callablestmt =
					conn.prepareCall(
						"{? = call packInsUpdHoliday.funcUpdateHoliday(?,?,?,?,?)}");
				callablestmt.registerOutParameter(1, java.sql.Types.INTEGER);
				callablestmt.setDate(2, sqlDate);
				callablestmt.setDate(3, modDate);
				callablestmt.setString(4, holidayDescription);
				callablestmt.setString(5, updatedBy);
				callablestmt.registerOutParameter(6, java.sql.Types.VARCHAR);
				callablestmt.executeQuery();

				status = callablestmt.getInt(1);
				errorCode = callablestmt.getString(6);
				Log.log(
					Log.DEBUG,
					"IFDAO",
					"updateHolidayMaster",
					"Error code and error " + status + " " + errorCode);
				if (status == Constants.FUNCTION_FAILURE) {
					callablestmt.close();
					callablestmt = null;
					Log.log(Log.ERROR, "IFDAO", "updateHolidayMaster", errorCode);
					throw new DatabaseException(errorCode);
				}

				callablestmt.close();
				callablestmt = null;
			} catch (SQLException e) {
				Log.log(Log.ERROR, "IFDAO", "updateHolidayMaster", e.getMessage());
				Log.logException(e);
				throw new DatabaseException("Unable to update HolidayMaster");
			} finally {
				DBConnection.freeConnection(conn);
			}
			Log.log(Log.INFO, "IFDAO", "updateHolidayMaster", "Exited");
		}

	public void saveEnterPaymentDetails(PaymentDetails paydetails,ChequeDetails chequeDetails,
								String contextPath) throws DatabaseException,MessageException
	{

		Log.log(Log.INFO,"IFDAO","saveEnterPaymentDetails","Entered");
		CallableStatement callablestmt = null;
		Connection conn = null;
		int status = -1;
		String errorCode = null;

		String payId;


		try
		{

			conn = DBConnection.getConnection(false);
			callablestmt =
				conn.prepareCall(
					"{? = call funcInsertPayDetails(?,?,?,?,?,?,?)}");
			callablestmt.registerOutParameter(1, java.sql.Types.INTEGER);
			callablestmt.registerOutParameter(2, java.sql.Types.VARCHAR);
			callablestmt.setString(3, paydetails.getPaymentsTo());
			callablestmt.setDouble(4, paydetails.getAmount());
			callablestmt.setString(5, paydetails.getRemarks());
			java.util.Date utilDate1 = paydetails.getPaymentDate();
			java.sql.Date sqlDate1 = new java.sql.Date (utilDate1.getTime());
			callablestmt.setDate(6, sqlDate1);
			callablestmt.setString(7, paydetails.getUserId());
			callablestmt.registerOutParameter(8, java.sql.Types.VARCHAR);
			callablestmt.executeQuery();
			status = callablestmt.getInt(1);
			errorCode = callablestmt.getString(8);
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"saveEnterPaymentDetails",
				"Error code and error " + status + " " + errorCode);
			if (status == Constants.FUNCTION_FAILURE) {

				callablestmt.close();
				callablestmt = null;
				Log.log(Log.ERROR, "IFDAO", "saveEnterPaymentDetails", errorCode);
				throw new DatabaseException(errorCode);
			}
			else{

				payId= callablestmt.getString(2);

			}

			if(chequeDetails != null)
			{
				String user = chequeDetails.getUserId();
				String remarks = paydetails.getRemarks();
				chequeDetails.setChequeRemarks(remarks);   
				chequeDetails.setPayId(payId);
				chequeDetailsInsertSuccess(chequeDetails,conn,contextPath,user);

			}
			conn.commit();

		} catch (SQLException e) {
			Log.log(Log.ERROR, "IFDAO", "saveEnterPaymentDetails", e.getMessage());
			Log.logException(e);
			throw new DatabaseException("Unable to Insert PaymentDetails");
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(Log.INFO, "IFDAO", "saveEnterPaymentDetails", "Exited");


	}

	public void saveModifyPaymentDetails(PaymentDetails paydetails) throws DatabaseException
	{
		Log.log(Log.INFO, "IFDAO", "Payment details are modified", "Modified");
		CallableStatement callablestmt = null;
		Connection conn = null;
		int status = -1;
		String errorcode = null;

		try
		{
			conn = DBConnection.getConnection();
			callablestmt = conn.prepareCall("{? = call funcUpdatePayDetails(?,?,?,?,?,?,?)}");
			callablestmt.registerOutParameter(1, java.sql.Types.INTEGER);
		Log.log(Log.INFO, "IFDAO", "pay id ", paydetails.getPayId());
			callablestmt.setString(2, paydetails.getPayId());
		Log.log(Log.INFO, "IFDAO", "pay to ", paydetails.getPaymentsTo());
			callablestmt.setString(3, paydetails.getPaymentsTo());
		Log.log(Log.INFO, "IFDAO", "pay amount ", paydetails.getAmount()+"");
			callablestmt.setDouble(4, paydetails.getAmount());
		Log.log(Log.INFO, "IFDAO", "pay remarks ", paydetails.getRemarks());
			callablestmt.setString(5, paydetails.getRemarks());
		Log.log(Log.INFO, "IFDAO", "pay date ", paydetails.getPaymentDate().toString());
			java.util.Date utilDate1 = paydetails.getPaymentDate();
			java.sql.Date sqlDate1 = new java.sql.Date (utilDate1.getTime());
			callablestmt.setDate(6, sqlDate1);
			callablestmt.setString(7, paydetails.getUserId());
			callablestmt.registerOutParameter(8, java.sql.Types.VARCHAR);
			callablestmt.executeQuery();

			status = callablestmt.getInt(1);
			errorcode = callablestmt.getString(8);
			Log.log(Log.DEBUG, "IFDAO", "FuncUpdatePaymentDetials", "Error code and error" +status +" " +errorcode);

			if(status == Constants.FUNCTION_FAILURE)
			{
				callablestmt.close();
				callablestmt = null;
				Log.log(Log.ERROR, "IFDAO", "FuncUpdatePaymentDetails", errorcode);
				throw new DatabaseException(errorcode);
			}

			callablestmt.close();
			callablestmt = null;
		} catch(SQLException  e)
		{
			Log.log(Log.ERROR, "IFDAO", "FuncUpdatePaymentDetails", e.getMessage());
			Log.logException(e);
			throw new DatabaseException("Unable update payment details");
		} finally
			{
				DBConnection.freeConnection(conn);
			}
			Log.log(Log.INFO, "IFDAO", "FuncUpdatePaymentDetails", "Exited");

	}


	public ArrayList showListOfPaymentDetails(PaymentDetails paydetails) throws DatabaseException
	{
		Log.log(Log.INFO, "IFDAO", "ListOfPaymentDetails are displayed", "Displayed");
		CallableStatement callablestmt = null;
		Connection conn = null;
		ArrayList payments = new ArrayList();
		ResultSet resultset;
		int status = -1;
		String errorcode = null;
		try
		{
			conn = DBConnection.getConnection();
			callablestmt = conn.prepareCall("{? = call packGetPaymentDetail.funcGetPaymentDetail1(?,?,?,?)}");
			callablestmt.registerOutParameter(1, java.sql.Types.INTEGER);
			if (paydetails.getFromDate()!=null && !paydetails.getFromDate().toString().equals(""))
			{
				callablestmt.setDate(2, new java.sql.Date(paydetails.getFromDate().getTime()));
			}
			else
			{
				callablestmt.setNull(2, java.sql.Types.DATE);
			}

			callablestmt.setDate(3, new java.sql.Date(paydetails.getToDate().getTime()));
			callablestmt.registerOutParameter(4, Constants.CURSOR);
			callablestmt.registerOutParameter(5, java.sql.Types.VARCHAR);

			resultset = callablestmt.executeQuery();

			status = callablestmt.getInt(1);
			errorcode = callablestmt.getString(5);
			Log.log(Log.DEBUG, "IFDAO", "packGetPaymentDetial", "Error code and error" +status +" " +errorcode);

			if(status == Constants.FUNCTION_FAILURE)
			{
				callablestmt.close();
				callablestmt = null;
				Log.log(Log.ERROR, "IFDAO", "packGetPaymentDetail", errorcode);
				throw new DatabaseException(errorcode);
			}

			resultset = (ResultSet) callablestmt.getObject(4);

			while(resultset.next())
			{
				paydetails = new PaymentDetails();
				paydetails.setPayId(resultset.getString(1));
				paydetails.setPaymentsTo(resultset.getString(2));
				paydetails.setAmount(resultset.getDouble(3));
				paydetails.setPaymentDate(resultset.getDate(4));
				payments.add(paydetails);
			}
				resultset.close();
				resultset = null;
			callablestmt.close();
			callablestmt = null;

		}
		catch(Exception e)
		{
			Log.log(Log.ERROR, "IFDAO", "packGetPaymentDetails", e.getMessage());
			Log.logException(e);
			throw new DatabaseException("Unable to display payment details");
		}
		finally
		{
			DBConnection.freeConnection(conn);
		}
		Log.log(Log.INFO, "IFDAO", "showListOfPaymentDetails", "Exited");
		return payments;
	}


	public PaymentDetails getPaymentDetails(String payId) throws DatabaseException
	{
		Log.log(Log.INFO, "IFDAO", "getPaymentDetails are updated", "updated");
		CallableStatement callablestmt = null;
		PaymentDetails paydetails = new PaymentDetails();
		Connection conn = null;
		int status = -1;
		String errorcode = null;

		try
		{
			conn = DBConnection.getConnection();
			callablestmt= conn.prepareCall("{? = call funcGetPayDetail(?,?,?,?,?,?)}");
			callablestmt.registerOutParameter(1, java.sql.Types.INTEGER);
			callablestmt.setString(2, payId);
			callablestmt.registerOutParameter(3, java.sql.Types.VARCHAR);
			callablestmt.registerOutParameter(4, java.sql.Types.DOUBLE);
			callablestmt.registerOutParameter(5, java.sql.Types.DATE);
			callablestmt.registerOutParameter(6, java.sql.Types.VARCHAR);
			callablestmt.registerOutParameter(7, java.sql.Types.VARCHAR);

			callablestmt.executeQuery();

			status = callablestmt.getInt(1);
			errorcode = callablestmt.getString(7);
			Log.log(Log.DEBUG, "IFDAO", "FuncGetPaymentDetial", "Error code and error" +status +" " +errorcode);


			if(status == Constants.FUNCTION_FAILURE)
			{
				callablestmt.close();
				callablestmt = null;
				Log.log(Log.ERROR, "IFDAO", "funcGetPaymentDetail", errorcode);
				throw new DatabaseException(errorcode);
			}

			else
			{
				//DateHelper.sqlToUtilDate()
				paydetails.setPaymentsTo(callablestmt.getString(3));
				paydetails.setAmount(callablestmt.getDouble(4));
				paydetails.setPaymentDate(callablestmt.getDate(5));
				paydetails.setRemarks(callablestmt.getString(6));
				paydetails.setPayId(payId);
			}
			callablestmt.close();
			callablestmt = null;
		}
		catch(SQLException e)
		{
			Log.log(Log.ERROR, "IFDAO", "funcGetPaymentDetails", e.getMessage());
			Log.logException(e);
			throw new DatabaseException("Unable to get payment details");
		}
		finally
		{
			DBConnection.freeConnection(conn);
		}
		Log.log(Log.INFO, "IFDAO", "getPaymentDetails", "Exited");
		return paydetails;
	}





	public Map showInvstMaturingDetails(Date date) throws DatabaseException
	{
			Log.log(Log.INFO, "IFDAO", "showInvstMaturingDetails", "entered");

			CallableStatement pStmt = null;
			Connection conn = null;
			ResultSet rsInOut = null;

			InvestmentMaturityDetails imDetail = new InvestmentMaturityDetails();
			Map reportDetails = new TreeMap();
			DecimalFormat df = new DecimalFormat("###############.##");

			try
			{
				conn = DBConnection.getConnection();

				pStmt=conn.prepareCall("{? = call packPlanInvestment.funcGetPlanInv(?,?,?,?)}");
				pStmt.registerOutParameter(1, java.sql.Types.INTEGER);
				pStmt.setDate(2, new java.sql.Date(date.getTime()));
				pStmt.registerOutParameter(3, Constants.CURSOR);
				pStmt.registerOutParameter(4, Constants.CURSOR);
				pStmt.registerOutParameter(5, java.sql.Types.VARCHAR);
				
				pStmt.executeQuery();
				
				int status = pStmt.getInt(1);
				String error = pStmt.getString(5);
				
				Log.log(Log.DEBUG, "IFDAO", "showInvstMaturingDetails", "Error code and error" +status +", " +error);
				
				if (status==Constants.FUNCTION_FAILURE)
				{
					pStmt.close();
					pStmt=null;
					throw new DatabaseException(error);
				}
				
				boolean dataAvailable=false;
				
				rsInOut = (ResultSet) pStmt.getObject(3);
				int counter=0;

				// takes data already entered buy the user at some prior time.
				while (rsInOut.next())
				{
					imDetail = new InvestmentMaturityDetails();
					imDetail.setPliId(rsInOut.getInt(1));
					imDetail.setInvName(rsInOut.getString(2));
					imDetail.setMaturityAmt(df.format(rsInOut.getDouble(3)));
					imDetail.setInvFlag(rsInOut.getString(4));
					imDetail.setOtherDesc(rsInOut.getString(6));
					imDetail.setPurchaseDate(rsInOut.getDate(9));
					imDetail.setBuySellId(rsInOut.getString(10));
					reportDetails.put("key-"+counter, imDetail);
					counter++;
					dataAvailable=true;
				}

				rsInOut.close();
				rsInOut=null;

				if (!dataAvailable)
				{
					// user is visiting this report for the given date the first time. 
					// so details of maturing investments are taken from investment_detail.
					rsInOut = (ResultSet) pStmt.getObject(4);
					counter=0;
					while (rsInOut.next())
					{
						imDetail = new InvestmentMaturityDetails();
						imDetail.setInvName(rsInOut.getString(1));
						imDetail.setPurchaseDate(rsInOut.getDate(2));
						imDetail.setMaturityAmt(df.format(rsInOut.getDouble(3)));
						imDetail.setBuySellId(rsInOut.getString(4));
						reportDetails.put("key-"+counter, imDetail);
						counter++;
					}
					rsInOut.close();
					rsInOut=null;
				}
				
				// to make the extra row for the user to enter other maturity details if not already exist
				boolean otherAvail = false;
				for (int i=0; i<counter; i++)
				{
					String key = "key-"+i;
					imDetail = (InvestmentMaturityDetails) reportDetails.get(key);
					if (imDetail.getInvName()==null || imDetail.getInvName().equals(""))
					{
						otherAvail=true;
						break;
					}
				}
				if (!otherAvail)
				{
					imDetail = new InvestmentMaturityDetails();
					imDetail.setOtherDesc("");
					reportDetails.put("key-"+counter, imDetail);
				}
				
				pStmt.close();
				pStmt=null;

			}
			catch(SQLException e)
			{
				Log.log(Log.ERROR, "IFDAO", "showInvstMaturingDetails", e.getMessage());
				Log.logException(e);
				throw new DatabaseException("Unable to get inflow outflow details");
			}
			finally
			{
				DBConnection.freeConnection(conn);
			}
			Log.log(Log.INFO, "IFDAO", "showInvstMaturingDetails", "Exited");
			return reportDetails;
		}

		public double getBankAccountDetails(String bankName, String branchName, String accNumber) throws DatabaseException {
				Log.log(Log.INFO, "IFDAO", "getBankAccountDetails", "Entered");

				Connection conn = null;
				CallableStatement callableStmt = null;
				int status = -1;
				String errorCode = null;
				double minBal = 0;
				try {
					conn = DBConnection.getConnection();
					callableStmt =
						conn.prepareCall(
							"{?=call funcGetBankAccountDetail(?,?,?,?,?)}");
					callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
					callableStmt.setString(2, bankName);
					callableStmt.setString(3, branchName);
					callableStmt.setString(4, accNumber);
					callableStmt.registerOutParameter(5, java.sql.Types.DOUBLE);
					callableStmt.registerOutParameter(6, java.sql.Types.VARCHAR);

					callableStmt.execute();
					status = callableStmt.getInt(1);
					errorCode = callableStmt.getString(6);
					Log.log(
						Log.DEBUG,
						"IFDAO",
						"getHolidayDates",
						"Error code and error " + status + " " + errorCode);
					if (status == Constants.FUNCTION_FAILURE) {
						callableStmt.close();
						callableStmt = null;
						Log.log(Log.ERROR, "IFDAO", "getHolidayDetail", errorCode);
						throw new DatabaseException(errorCode);
					}
					minBal=callableStmt.getDouble(5);
					callableStmt.close();
					callableStmt = null;

				} catch (SQLException e) {
					Log.log(Log.ERROR, "IFDAO", "getHolidayDetail", e.getMessage());
					Log.logException(e);
					throw new DatabaseException("Unable to get holiday detail");
				} finally {
					DBConnection.freeConnection(conn);
				}
				Log.log(Log.INFO, "IFDAO", "getHolidayDetail", "Exited");

				return minBal;
			}

		public ArrayList getInvDetails(String invRefNo) throws DatabaseException
		{
			ArrayList details=new ArrayList();
			Log.log(Log.INFO, "IFDAO", "getInvDetails", "Entered");

			Connection connection = DBConnection.getConnection();

			try
			{
				CallableStatement callable =
						connection.prepareCall(
						"{?= call funcGetInvDetail(?,?,?,?,?)}");

				callable.registerOutParameter(1, Types.INTEGER);
				callable.setString(2, invRefNo);
				callable.registerOutParameter(3, Types.VARCHAR);
				callable.registerOutParameter(4, Types.DOUBLE);
				callable.registerOutParameter(5, Types.INTEGER);
				callable.registerOutParameter(6, Types.VARCHAR);

				callable.execute();

				int errorCode = callable.getInt(1);
				String error = callable.getString(6);

				Log.log(Log.DEBUG,"IFDAO","getInvDetails",
					"errorCode and error " + errorCode + "," + error);

				if (errorCode == Constants.FUNCTION_FAILURE) {
					Log.log(Log.ERROR, "IFDAO", "getInvDetails", error);
					callable.close();
					callable = null;

					throw new DatabaseException(error);
				}

				String invName = callable.getString(3);
				double amt = callable.getDouble(4);
				int units = callable.getInt(5);

				details.add(invName);
				details.add(new Double(amt));
				details.add(new Integer(units));

				callable.close();
				callable = null;

/*				callable =connection.prepareCall("{?= call funcGetRatingForInst(?,?,?,?)}");

				callable.registerOutParameter(1, Types.INTEGER);
				callable.setString(2, invRefNo);
				callable.setString(3, invName);
				callable.registerOutParameter(4, Types.VARCHAR);
				callable.registerOutParameter(5, Types.VARCHAR);

				callable.execute();

				errorCode = callable.getInt(1);
				error = callable.getString(5);

				Log.log(Log.DEBUG,"IFDAO","getInvDetails",
					"errorCode and error " + errorCode + "," + error);

				if (errorCode == Constants.FUNCTION_FAILURE) {
					Log.log(Log.ERROR, "IFDAO", "getInvDetails", error);
					callable.close();
					callable = null;

					throw new DatabaseException(error);
				}

				details.add(callable.getString(4));

				callable.close();
				callable = null;
*/
			}
			catch (SQLException e)
			{
				Log.log(Log.ERROR, "IFDAO", "getInvDetails", e.getMessage());
				Log.logException(e);

				throw new DatabaseException("Unable to get Inv details.");
			}
			finally
			{
				DBConnection.freeConnection(connection);
			}

			Log.log(Log.INFO, "IFDAO", "getInvDetails", "Exited");
			return details;
		}

	public ArrayList getInvRefNosForFullfilment(String inst, String inv) throws DatabaseException {
		Log.log(Log.INFO, "IFDAO", "getInvRefNosForFullfilment", "Entered");

		ArrayList refNumbers = new ArrayList();

		Connection connection = DBConnection.getConnection();

		try {
			CallableStatement callable =
				connection.prepareCall(
					"{?= call packGetInvRefNosForFullfilment.funcGetInvRefNosForFullfilment(?,?,?,?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);
			callable.setString(2, InvestmentFundConstants.BUY_REQUEST);
			callable.setString(3, inst);
			if (inv!=null)
			{
				callable.setString(4, inv);
			}
			else
			{
				callable.setString(4, null);
			}
			callable.registerOutParameter(5, Constants.CURSOR);
			callable.registerOutParameter(6, Types.VARCHAR);

			callable.execute();

			int errorCode = callable.getInt(1);
			String error = callable.getString(6);

			Log.log(
				Log.DEBUG,
				"IFDAO",
				"getInvestmentRefNumbers",
				"errorCode and error " + errorCode + "," + error);

			if (errorCode == Constants.FUNCTION_FAILURE) {
				Log.log(Log.ERROR, "IFDAO", "getInvRefNosForFullfilment", error);
				callable.close();
				callable = null;

				throw new DatabaseException(error);
			}

			ResultSet results = (ResultSet) callable.getObject(5);

			while (results.next()) {
				refNumbers.add(results.getString(1));
			}

			results.close();
			results = null;

			callable.close();
			callable = null;

		} catch (SQLException e) {
			Log.log(
				Log.ERROR,
				"IFDAO",
				"getInvRefNosForFullfilment",
				e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Unable to get Investment reference numbers ");
		} finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO, "IFDAO", "getInvRefNosForFullfilment", "Exited");

		return refNumbers;
	}

	public ArrayList getAllHolidays() throws DatabaseException {
		Log.log(Log.INFO, "IFDAO", "getAllHolidays", "Entered");

		ArrayList holidays = new ArrayList();

		Connection connection = DBConnection.getConnection();

		try {
			CallableStatement callable =
				connection.prepareCall(
					"{?= call packGetAllHolidays.funcGetAllHolidays(?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);
			callable.registerOutParameter(2, Constants.CURSOR);
			callable.registerOutParameter(3, Types.VARCHAR);

			callable.execute();

			int errorCode = callable.getInt(1);
			String error = callable.getString(3);

			Log.log(
				Log.DEBUG,
				"IFDAO",
				"getAllHolidays",
				"errorCode and error " + errorCode + "," + error);

			if (errorCode == Constants.FUNCTION_FAILURE) {
				Log.log(Log.ERROR, "IFDAO", "getAllHolidays", error);
				callable.close();
				callable = null;

				throw new DatabaseException(error);
			}

			ResultSet results = (ResultSet) callable.getObject(2);

			while (results.next()) {
				holidays.add(DateHelper.sqlToUtilDate(results.getDate(1)));
			}

			results.close();
			results = null;

			callable.close();
			callable = null;

		} catch (SQLException e) {
			Log.log(
				Log.ERROR,
				"IFDAO",
				"getAllHolidays",
				e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Unable to get Holidays ");
		} finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO, "IFDAO", "getAllHolidays", "Exited");

		return holidays;
	}

	public ArrayList getInvRefNosForTDS(String inst, String inv) throws DatabaseException {
		Log.log(Log.INFO, "IFDAO", "getInvRefNosForTDS", "Entered");

		ArrayList refNumbers = new ArrayList();

		Connection connection = DBConnection.getConnection();

		try {
			CallableStatement callable =
				connection.prepareCall(
					"{?= call packGetInvRefNosForTDS.funcGetInvRefNosForTDS(?,?,?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);
			callable.setString(2, inst);
			if (inv!=null)
			{
				callable.setString(3, inv);
			}
			else
			{
				callable.setString(3, null);
			}
			callable.registerOutParameter(4, Constants.CURSOR);
			callable.registerOutParameter(5, Types.VARCHAR);

			callable.execute();

			int errorCode = callable.getInt(1);
			String error = callable.getString(5);

			Log.log(
				Log.DEBUG,
				"IFDAO",
				"getInvRefNosForTDS",
				"errorCode and error " + errorCode + "," + error);

			if (errorCode == Constants.FUNCTION_FAILURE) {
				Log.log(Log.ERROR, "IFDAO", "getInvRefNosForTDS", error);
				callable.close();
				callable = null;

				throw new DatabaseException(error);
			}

			ResultSet results = (ResultSet) callable.getObject(4);

			while (results.next()) {
				String refNo = results.getString(1);
				String recNo = results.getString(2);
				refNumbers.add(recNo + "(" + refNo + ")");
			}

			results.close();
			results = null;

			callable.close();
			callable = null;

		} catch (SQLException e) {
			Log.log(
				Log.ERROR,
				"IFDAO",
				"getInvRefNosForTDS",
				e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Unable to get Investment reference numbers ");
		} finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO, "IFDAO", "getInvRefNosForTDS", "Exited");

		return refNumbers;
	}

	public double getPrincipalAmntInInvesteeGrpAndName(Date currentDate, Date date) throws DatabaseException
	{
		double principalAmount=0;

		Log.log(Log.INFO, "IFDAO", "getPrincipalAmntInInvesteeGrpAndName", "Entered");
		Connection connection = DBConnection.getConnection();

		try
		{
			CallableStatement callable =
					connection.prepareCall(
					"{?= call funcGetInvstdAmtIGRndInvestee(?,?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);
			callable.setDate(2,new java.sql.Date(date.getTime()));
			//callable.setString(3,investeeName);
			//callable.setDate(4, new java.sql.Date(date.getTime()));
			callable.registerOutParameter(3, Types.DOUBLE);
			callable.registerOutParameter(4, Types.VARCHAR);

			callable.execute();

			int errorCode = callable.getInt(1);
			String error = callable.getString(4);

			Log.log(Log.DEBUG,"IFDAO","getPrincipalAmntInInvesteeGrpAndName",
				"errorCode and error " + errorCode + "," + error);

			if (errorCode == Constants.FUNCTION_FAILURE) {
				Log.log(Log.ERROR, "IFDAO", "getPrincipalAmntInInvesteeGrpAndName", error);
				callable.close();
				callable = null;

				throw new DatabaseException(error);
			}
			principalAmount=callable.getDouble(3);

			Log.log(Log.DEBUG,"IFDAO","getPrincipalAmntInInvesteeGrpAndName","principal amount " +principalAmount);

			callable.close();
			callable = null;
		}
		catch (SQLException e)
		{
			Log.log(Log.ERROR, "IFDAO", "getPrincipalAmntInInvesteeGrpAndName", e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Unable to get Principal Amount for the Investee Group and Investee Name.");
		}
		finally
		{
			DBConnection.freeConnection(connection);
		}

		Log.log(Log.INFO, "IFDAO", "getPrincipalAmntInInvesteeGrpAndName", "Exited");
		return principalAmount;
	}

	public double getMaturityAmntInInvesteeGrpAndName(Date date, Date proposedDate) throws DatabaseException
	{
		double maturityAmount=0;

		Log.log(Log.INFO, "IFDAO", "getMaturityAmntInInvesteeGrpAndName", "Entered");
		Connection connection = DBConnection.getConnection();

		try
		{
			CallableStatement callable =
					connection.prepareCall(
					"{?= call funcGetMatAmntForInvGrndName(?,?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);
			callable.setDate(2,new java.sql.Date(proposedDate.getTime()));
			//callable.setString(3,investeeName);
			//callable.setDate(4, new java.sql.Date(proposedDate.getTime()));
			callable.registerOutParameter(3, Types.DOUBLE);
			callable.registerOutParameter(4, Types.VARCHAR);

			callable.execute();

			int errorCode = callable.getInt(1);
			String error = callable.getString(4);

			Log.log(Log.DEBUG,"IFDAO","getMaturityAmntInInvesteeGrpAndName",
				"errorCode and error " + errorCode + "," + error);

			if (errorCode == Constants.FUNCTION_FAILURE) {

				Log.log(Log.ERROR, "IFDAO", "getMaturityAmntInInvesteeGrpAndName", error);
				callable.close();
				callable = null;

				throw new DatabaseException(error);
			}
			maturityAmount=callable.getDouble(3);

			Log.log(Log.DEBUG,"IFDAO","getMaturityAmntInInvesteeGrpAndName","maturity amount " +maturityAmount);

			callable.close();
			callable = null;
		}
		catch (SQLException e)
		{
			Log.log(Log.ERROR, "IFDAO", "getMaturityAmntInInvesteeGrpAndName", e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Unable to get Maturity Amount for the Investee Group and Investee Name.");
		}
		finally
		{
			DBConnection.freeConnection(connection);
		}

		Log.log(Log.INFO, "IFDAO", "getMaturityAmntInInvesteeGrpAndName", "Exited");
		return maturityAmount;
	}

	public void updateChequeStatus(String bankName, String accountNumber,
				 String chequeNumber, java.sql.Date sqlTransDate,Connection connection) throws DatabaseException
	{
		Log.log(Log.INFO,"IFDAO","updateChequeStatus","Entered");
		Log.log(Log.INFO,"IFDAO","updateChequeStatus","bankName :" + bankName);
		//Log.log(Log.INFO,"IFDAO","updateChequeStatus","branchName :" + branchName);
		Log.log(Log.INFO,"IFDAO","updateChequeStatus","accountNumber :" + accountNumber);
		Log.log(Log.INFO,"IFDAO","updateChequeStatus","chequeNumber :" + chequeNumber);

		//Connection connection = DBConnection.getConnection(false);
		CallableStatement chequeStmt = null;

		try
		{
			chequeStmt = connection.prepareCall("{? = call funcUpdateChequeStatus(?,?,?,?,?)}");
			chequeStmt.registerOutParameter(1, Types.INTEGER);
			chequeStmt.setString(2,bankName);
			//chequeStmt.setString(3,branchName);
			chequeStmt.setString(3,accountNumber);
			chequeStmt.setString(4,chequeNumber);
			chequeStmt.setDate(5,sqlTransDate);
			chequeStmt.registerOutParameter(6, Types.VARCHAR);
			
			chequeStmt.executeQuery();

			int status = chequeStmt.getInt(1);
			//System.out.println("status:"+status);

			if(status == Constants.FUNCTION_FAILURE)
			{
				String errorCode = chequeStmt.getString(6);
				//System.out.println("errorCode:"+errorCode);
				Log.log(Log.ERROR,"IFDAO","updateChequeStatus","SP returns a 1." +
										" Error code is :" + errorCode);
					connection.rollback();
				chequeStmt.close();
				chequeStmt = null;
			}
/*			else if(status == Constants.FUNCTION_SUCCESS)
			{
				try
				{
					connection.commit();
				}
				catch(SQLException sqlex)
				{
					throw new DatabaseException("Error  commiting Transaction");
				}
				chequeStmt.close();
				chequeStmt = null;
			}
*/
		chequeStmt.close();
		chequeStmt = null;

		}
		catch(SQLException exception)
		{
			// exception.printStackTrace();
			throw new DatabaseException(exception.getMessage());
		}
/*		finally
		{
			DBConnection.freeConnection(connection);
		}
*/		Log.log(Log.INFO,"IFDAO","updateChequeStatus","Exited");
	}

	public MaturityWiseCeiling getMaturityWiseCeiling(String maturityType)throws DatabaseException
	{
		Log.log(Log.INFO,"IFDAO","getMaturityWiseCeiling","Entered");
		MaturityWiseCeiling matWiseCeiling = null;
		ResultSet matResult;
		Connection connection = DBConnection.getConnection();
		CallableStatement callableStmt = null;

			try
			{
				callableStmt = connection.prepareCall("{? = call packGetCeilings.funcGetMaturityWiseCeiling(?,?,?)}");
				callableStmt.setString(2,maturityType);
				callableStmt.registerOutParameter(1, Types.INTEGER);
				callableStmt.registerOutParameter(3,Constants.CURSOR);
				callableStmt.registerOutParameter(4, Types.VARCHAR);

				callableStmt.executeQuery();

				int status = callableStmt.getInt(1);


				if(status == Constants.FUNCTION_FAILURE)
				{
					String errorCode = callableStmt.getString(4);
					Log.log(Log.ERROR,"IFDAO","getMaturityWiseCeiling","SP returns a 1." +
											" Error code is :" + errorCode);
					callableStmt.close();
					callableStmt = null;
				}
				else if(status == Constants.FUNCTION_SUCCESS)
				{
					matResult = (ResultSet)callableStmt.getObject(3);
					while(matResult.next())
					{
						matWiseCeiling = new MaturityWiseCeiling();
						matWiseCeiling.setMaturityType(maturityType);
						matWiseCeiling.setCeilingStartDate(matResult.getDate(1));
						matWiseCeiling.setCeilingEndDate(matResult.getDate(2));
						matWiseCeiling.setCeilingLimit(matResult.getDouble(3));
					}
					callableStmt.close();
					callableStmt = null;
				}

			}
			catch(SQLException exception)
			{
				// exception.printStackTrace();
				throw new DatabaseException(exception.getMessage());
			}
			finally
			{
				DBConnection.freeConnection(connection);
			}
		Log.log(Log.INFO,"IFDAO","getMaturityWiseCeiling","Exited");
		return matWiseCeiling;
	}

	public InstrumentCategoryWiseCeiling getInstrumentWiseCeiling(String instrumentType)throws DatabaseException
	{
		Log.log(Log.INFO,"IFDAO","getInstrumentWiseCeiling","Entered");
		InstrumentCategoryWiseCeiling instrWiseCeiling = null;
		ResultSet instrResult;
		Connection connection = DBConnection.getConnection();
		CallableStatement callableStmt = null;

			try
			{
				callableStmt = connection.prepareCall("{? = call packGetCeilings.funcGetInstrumentWiseCeiling(?,?,?)}");
				callableStmt.setString(2,instrumentType);
				callableStmt.registerOutParameter(1, Types.INTEGER);
				callableStmt.registerOutParameter(3,Constants.CURSOR);
				callableStmt.registerOutParameter(4, Types.VARCHAR);

				callableStmt.executeQuery();

				int status = callableStmt.getInt(1);


				if(status == Constants.FUNCTION_FAILURE)
				{
					String errorCode = callableStmt.getString(4);
					Log.log(Log.ERROR,"IFDAO","getInstrumentWiseCeiling","SP returns a 1." +
											" Error code is :" + errorCode);
					callableStmt.close();
					callableStmt = null;
				}
				else if(status == Constants.FUNCTION_SUCCESS)
				{
					instrResult = (ResultSet)callableStmt.getObject(3);
					while(instrResult.next())
					{
						instrWiseCeiling=new InstrumentCategoryWiseCeiling();
						instrWiseCeiling.setInstrumentName(instrumentType);
						instrWiseCeiling.setCeilingStartDate(instrResult.getDate(1));
						instrWiseCeiling.setCeilingEndDate(instrResult.getDate(2));
						instrWiseCeiling.setCeilingLimit(instrResult.getDouble(3));
					}
					callableStmt.close();
					callableStmt = null;
				}

			}
			catch(SQLException exception)
			{
				// exception.printStackTrace();
				throw new DatabaseException(exception.getMessage());
			}
			finally
			{
				DBConnection.freeConnection(connection);
			}
		Log.log(Log.INFO,"IFDAO","getInstrumentWiseCeiling","Exited");
		return instrWiseCeiling;
	}

	public InvesteeGroupWiseCeiling getIGroupWiseCeiling(String investeeGroupName)throws DatabaseException
	{
		Log.log(Log.INFO,"IFDAO","getInstrumentWiseCeiling","Entered");
		InvesteeGroupWiseCeiling investeeGrWiseCeiling = null;
		ResultSet invGrResult;
		Connection connection = DBConnection.getConnection();
		CallableStatement callableStmt = null;

			try
			{
				callableStmt = connection.prepareCall("{? = call packGetCeilings.funcGetIGroupWiseCeiling(?,?,?)}");
				callableStmt.setString(2,investeeGroupName);
				callableStmt.registerOutParameter(1, Types.INTEGER);
				callableStmt.registerOutParameter(3,Constants.CURSOR);
				callableStmt.registerOutParameter(4, Types.VARCHAR);

				callableStmt.executeQuery();

				int status = callableStmt.getInt(1);


				if(status == Constants.FUNCTION_FAILURE)
				{
					String errorCode = callableStmt.getString(4);
					Log.log(Log.ERROR,"IFDAO","getIGroupWiseCeiling","SP returns a 1." +
											" Error code is :" + errorCode);
					callableStmt.close();
					callableStmt = null;
				}
				else if(status == Constants.FUNCTION_SUCCESS)
				{
					invGrResult = (ResultSet)callableStmt.getObject(3);
					while(invGrResult.next())
					{
						investeeGrWiseCeiling = new InvesteeGroupWiseCeiling();
						investeeGrWiseCeiling.setInvesteeGroup(investeeGroupName);
						investeeGrWiseCeiling.setCeilingStartDate(invGrResult.getDate(1));
						investeeGrWiseCeiling.setCeilingEndDate(invGrResult.getDate(2));
						investeeGrWiseCeiling.setCeilingLimit(invGrResult.getDouble(3));
						investeeGrWiseCeiling.setCeilingAmount(invGrResult.getDouble(4));
					}
					callableStmt.close();
					callableStmt = null;
				}

			}
			catch(SQLException exception)
			{
				// exception.printStackTrace();
				throw new DatabaseException(exception.getMessage());
			}
			finally
			{
				DBConnection.freeConnection(connection);
			}
		Log.log(Log.INFO,"IFDAO","getIGroupWiseCeiling","Exited");
		return investeeGrWiseCeiling;
	}

	public InvesteeWiseCeiling getInvesteeWiseCeiling(String investeeGroupName, String investeeName)throws DatabaseException
	{
		Log.log(Log.INFO,"IFDAO","getInvesteeWiseCeiling","Entered");
		InvesteeWiseCeiling investeeWiseCeiling = null;
		ResultSet invResult;
		Connection connection = DBConnection.getConnection();
		CallableStatement callableStmt = null;

			try
			{
				callableStmt = connection.prepareCall("{? = call packGetCeilings.funcGetInvesteeWiseCeiling(?,?,?,?)}");
				callableStmt.setString(2,investeeGroupName);
				callableStmt.setString(3,investeeName);
				callableStmt.registerOutParameter(1, Types.INTEGER);
				callableStmt.registerOutParameter(4,Constants.CURSOR);
				callableStmt.registerOutParameter(5, Types.VARCHAR);

				callableStmt.executeQuery();

				int status = callableStmt.getInt(1);


				if(status == Constants.FUNCTION_FAILURE)
				{
					String errorCode = callableStmt.getString(5);
					Log.log(Log.ERROR,"IFDAO","getInvesteeWiseCeiling","SP returns a 1." +
											" Error code is :" + errorCode);
					callableStmt.close();
					callableStmt = null;
				}
				else if(status == Constants.FUNCTION_SUCCESS)
				{
					invResult = (ResultSet)callableStmt.getObject(4);
					while(invResult.next())
					{
						investeeWiseCeiling = new InvesteeWiseCeiling();
						investeeWiseCeiling.setInvesteeGroup(investeeGroupName);
						investeeWiseCeiling.setInvesteeName(investeeName);
						investeeWiseCeiling.setCeilingStartDate(invResult.getDate(2));
						investeeWiseCeiling.setCeilingEndDate(invResult.getDate(3));
						investeeWiseCeiling.setCeilingLimit(invResult.getDouble(4));
						investeeWiseCeiling.setNetworth(invResult.getDouble(5));
						investeeWiseCeiling.setTangibleAssets(invResult.getDouble(6));
						investeeWiseCeiling.setCeilingAmount(invResult.getDouble(7));
					}
					callableStmt.close();
					callableStmt = null;
				}

			}
			catch(SQLException exception)
			{
				// exception.printStackTrace();
				throw new DatabaseException(exception.getMessage());
			}
			finally
			{
				DBConnection.freeConnection(connection);
			}
		Log.log(Log.INFO,"IFDAO","getInvesteeWiseCeiling","Exited");
		return investeeWiseCeiling;
	}

	public RatingWiseCeiling getRatingWiseCeiling(String investeeGroupName, String investeeName, String insType)throws DatabaseException
	{
		Log.log(Log.INFO,"IFDAO","getRatingWiseCeiling","Entered");
		RatingWiseCeiling ratingWiseCeiling = null;
		ResultSet ratingResult;
		Connection connection = DBConnection.getConnection();
		CallableStatement callableStmt = null;

			try
			{
				callableStmt = connection.prepareCall("{? = call packGetCeilings.funcGetRatingWiseCeiling(?,?,?,?,?)}");
				callableStmt.setString(2,investeeGroupName);
				callableStmt.setString(3,investeeName);
				callableStmt.setString(4,insType);
//				callableStmt.setString(5,ratingName);
				callableStmt.registerOutParameter(1, Types.INTEGER);
				callableStmt.registerOutParameter(5,Constants.CURSOR);
				callableStmt.registerOutParameter(6, Types.VARCHAR);

				callableStmt.executeQuery();

				int status = callableStmt.getInt(1);


				if(status == Constants.FUNCTION_FAILURE)
				{
					String errorCode = callableStmt.getString(6);
					Log.log(Log.ERROR,"IFDAO","getRatingWiseCeiling","SP returns a 1." +
											" Error code is :" + errorCode);
					callableStmt.close();
					callableStmt = null;
				}
				else if(status == Constants.FUNCTION_SUCCESS)
				{
					ratingResult = (ResultSet)callableStmt.getObject(5);
					while(ratingResult.next())
					{
						ratingWiseCeiling = new RatingWiseCeiling();
						ratingWiseCeiling.setInvesteeGroup(investeeGroupName);
						ratingWiseCeiling.setInvesteeName(investeeName);
						ratingWiseCeiling.setInstrumentName(insType);
						ratingWiseCeiling.setCeilingStartDate(ratingResult.getDate(2));
						ratingWiseCeiling.setCeilingEndDate(ratingResult.getDate(3));
						ratingWiseCeiling.setRatingAgency(ratingResult.getString(4));
						ratingWiseCeiling.setRating(ratingResult.getString(5));
					}
					callableStmt.close();
					callableStmt = null;
				}

			}
			catch(SQLException exception)
			{
				// exception.printStackTrace();
				throw new DatabaseException(exception.getMessage());
			}
			finally
			{
				DBConnection.freeConnection(connection);
			}
		Log.log(Log.INFO,"IFDAO","getRatingWiseCeiling","Exited");
		return ratingWiseCeiling;
	}

	/**
	 * gets the receipt numbers of all investments of type FD. used for validation of unique receipts numbers.
	 * @return
	 * @throws DatabaseException
	 */
	public ArrayList getFDReceiptNumbers() throws DatabaseException {
		Connection conn = null;
		CallableStatement callableStmt = null;
		ArrayList receiptNos = new ArrayList();
		ResultSet resultset = null;
		int status = -1;
		String errorCode = null;

		try {
			conn = DBConnection.getConnection();
			callableStmt =
				conn.prepareCall(
					"{?=call packGetAllFDReceiptNumbers.funcGetAllFDReceiptNumbers(?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt.registerOutParameter(2, Constants.CURSOR);
			callableStmt.registerOutParameter(3, java.sql.Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(3);
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"getReceiptNumbers",
				"Error code and error " + status + " " + errorCode);

			if (status == Constants.FUNCTION_FAILURE) {
				callableStmt.close();
				callableStmt = null;
				Log.log(Log.ERROR, "IFDAO", "getReceiptNumbers", errorCode);
				throw new DatabaseException(errorCode);
			}
			resultset = (ResultSet) callableStmt.getObject(2);
			while (resultset.next()) {
				receiptNos.add(resultset.getString(1));
			}
			resultset.close();
			resultset = null;
			callableStmt.close();
			callableStmt = null;
		} catch (SQLException e) {
			Log.log(Log.ERROR, "IFDAO", "getReceiptNumbers", e.getMessage());
			Log.logException(e);
			throw new DatabaseException("Unable to get receiptNos");
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(Log.INFO, "IFDAO", "getReceiptNumbers", "Exited");
		return receiptNos;
	}

	public InvestmentFulfillmentDetail getInvFullfilmentDetails(InvestmentFulfillmentDetail invFullfillment)throws DatabaseException
	{
		Log.log(Log.INFO, "IFDAO", "getInvFullfilmentDetails", "Entered");
		Connection conn = null;
		CallableStatement callableStmt = null;
		ResultSet resultset = null;
		int status = -1;
		String errorCode = null;
		boolean value=false;

		try {
			conn = DBConnection.getConnection();
			callableStmt =
				conn.prepareCall(
					"{?=call packGetInvFullfillmentDetails.funcGetInvFullfillmentDetails(?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			Log.log(Log.INFO, "IFDAO", "getInvFullfilmentDetails", "ref no " + invFullfillment.getInvestmentRefNumber());
			callableStmt.setString(2, invFullfillment.getInvestmentRefNumber());
			callableStmt.registerOutParameter(3, Constants.CURSOR);
			callableStmt.registerOutParameter(4, java.sql.Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(4);
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"getInvFullfilmentDetails",
				"Error code and error " + status + " " + errorCode);
			if (status == Constants.FUNCTION_FAILURE) {
							callableStmt.close();
							callableStmt = null;
							Log.log(Log.ERROR, "IFDAO", "getInvFullfilmentDetails", errorCode);
							throw new DatabaseException(errorCode);
						}
			resultset = (ResultSet) callableStmt.getObject(3);
			while (resultset.next()) {
				value=true;
				invFullfillment.setInvestmentRefNumber(invFullfillment.getInvestmentRefNumber());
				invFullfillment.setInstrumentType(resultset.getString(1));
				invFullfillment.setInstrumentNumber(resultset.getString(2));
				invFullfillment.setDrawnBank(resultset.getString(3));
				invFullfillment.setDrawnBranch(resultset.getString(4));
				invFullfillment.setInstrumentAmount(resultset.getDouble(5));
				invFullfillment.setInstrumentDate(resultset.getDate(6));
				invFullfillment.setPayableAt(resultset.getString(7));
				invFullfillment.setId(resultset.getInt(8));
			}
			resultset.close();
			resultset = null;
			callableStmt.close();
			callableStmt = null;

			if (!value)
			{
				invFullfillment=null;
			}

		} catch (SQLException e) {
			Log.log(Log.ERROR, "IFDAO", "getInvFullfilmentDetails", e.getMessage());
			Log.logException(e);
			throw new DatabaseException("Unable to get Investment Fullfilment Details");
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(Log.INFO, "IFDAO", "getInvFullfilmentDetails", "Exited");
		return invFullfillment;
	}

	public ArrayList getInvRefNosForBuySell(String inst, String inv) throws DatabaseException {
			Log.log(Log.INFO, "IFDAO", "getInvRefNosForBuySell", "Entered");

			ArrayList refNumbers = new ArrayList();

			Connection connection = DBConnection.getConnection();

			try {
				CallableStatement callable =
					connection.prepareCall(
						"{?= call packGetInvRefNosForBuySell.funcGetInvRefNosForBuySell(?,?,?,?)}");

				callable.registerOutParameter(1, Types.INTEGER);
				callable.setString(2, inst);
				if (inv!=null)
				{
					callable.setString(3, inv);
				}
				else
				{
					callable.setString(3, null);
				}
				callable.registerOutParameter(4, Constants.CURSOR);
				callable.registerOutParameter(5, Types.VARCHAR);

				callable.execute();

				int errorCode = callable.getInt(1);
				String error = callable.getString(5);

				Log.log(
					Log.DEBUG,
					"IFDAO",
					"getInvRefNosForBuySell",
					"errorCode and error " + errorCode + "," + error);

				if (errorCode == Constants.FUNCTION_FAILURE) {
					Log.log(Log.ERROR, "IFDAO", "getInvRefNosForBuySell", error);
					callable.close();
					callable = null;

					throw new DatabaseException(error);
				}

				ResultSet results = (ResultSet) callable.getObject(4);

				while (results.next()) {
					String refNo = results.getString(1);
					String recNo = results.getString(2);
					refNumbers.add(recNo + "(" + refNo + ")");
				}

				results.close();
				results = null;

				callable.close();
				callable = null;

			} catch (SQLException e) {
				Log.log(
					Log.ERROR,
					"IFDAO",
					"getInvRefNosForBuySell",
					e.getMessage());
				Log.logException(e);

				throw new DatabaseException("Unable to get Investment reference numbers ");
			} finally {
				DBConnection.freeConnection(connection);
			}
			Log.log(Log.INFO, "IFDAO", "getInvRefNosForBuySell", "Exited");

			return refNumbers;
		}

		public Hashtable getROI(Date referenceDate) throws DatabaseException
		{
			Log.log(Log.INFO, "IFDAO", "getROI", "Entered");

			Hashtable rois = new Hashtable();

			Connection connection = DBConnection.getConnection();

			try {

			String query="SELECT IM.INS_TYPE,ID.IDT_COST_OF_PURCHASE, " +
			" ID.IDT_INTEREST_RATE,ID.IDT_TENURE, ID.IDT_TENURE_TYPE,"+
			" ID.IDT_COMPOUNDING_FREQUENCY,NVL(ID.IDT_NUMBER_OF_UNITS_BOUGHT-ID.IDT_NUMBER_OF_UNITS_SOLD,ID.IDT_NUMBER_OF_UNITS_BOUGHT),"+
			" ID.IDT_CURRENT_DT_NAV-ID.IDT_PURCHASE_DT_NAV NAVDIFF,?-ID.IDT_PURCHASE_DT,"+
			" ID.IDT_COST_OF_PURCHASE-((NVL(ID.IDT_NUMBER_OF_UNITS_BOUGHT-ID.IDT_NUMBER_OF_UNITS_SOLD,0))*ID.IDT_PURCHASE_DT_NAV) "+
			" FROM INVESTMENT_DETAIL ID, INSTRUMENT_MASTER IM WHERE "+
			" (ID.IDT_MATURITY_DT IS NULL OR ID.IDT_MATURITY_DT>? "+
			" OR (ID.IDT_NUMBER_OF_UNITS_BOUGHT-ID.IDT_NUMBER_OF_UNITS_SOLD)>0)"+
			" AND ID.IDT_PURCHASE_DT<? AND ID.INS_ID=IM.INS_ID";

			PreparedStatement prepraredQuery=connection.prepareStatement(query);

			prepraredQuery.setDate(1,new java.sql.Date(referenceDate.getTime()));
			prepraredQuery.setDate(2,new java.sql.Date(referenceDate.getTime()));
			prepraredQuery.setDate(3,new java.sql.Date(referenceDate.getTime()));


			ResultSet results=prepraredQuery.executeQuery();
			int tenure=0;
			String tenureType=null;
			int liveUnits=0;
			int noOfYears=0;
			double navDiff=0;
			int cmpdFreq=0;
			double tempRateOfInterest=0;
			double powerValue=0;

			while(results.next())
			{
				ROIInfo roiInfo=new ROIInfo();

				roiInfo.setInstrumentName(results.getString(1));
				roiInfo.setPrincipalAmount(results.getDouble(2));
				//roiInfo.setInvestedDate(results.getDate(3));
				//roiInfo.setMaturityDate(results.getDate(4));
				roiInfo.setRateOfInterest(results.getDouble(3));
				tenure=results.getInt(4);
				tenureType=results.getString(5);
				cmpdFreq=results.getInt(6);
				liveUnits=results.getInt(7);
				navDiff=results.getDouble(8);
				//R1 = [(1 + R11/(N * 100))^ (T11/K) -1] / (T11/12)

				Log.log(Log.DEBUG,"IFDAO","getROI","cmpdFreq "+cmpdFreq);

				if(cmpdFreq!=0 && !tenureType.equalsIgnoreCase("D"))
				{
					if(tenureType.equalsIgnoreCase(YEAR))
					{
						powerValue=tenure*12;
					}
					else if(tenureType.equalsIgnoreCase(MONTH))
					{
						powerValue=tenure;
					}
					Log.log(Log.DEBUG,"IFDAO","getROI","powerValue "+powerValue);

					if(cmpdFreq==MONTHLY_COMPOUNDING)
					{
						Log.log(Log.DEBUG,"IFDAO","getROI","Monthly Compounding ");
						tempRateOfInterest=((1+roiInfo.getRateOfInterest()/(MONTHLY_COMPOUNDING*100)));
						tempRateOfInterest=Math.pow(tempRateOfInterest,(powerValue-1));
					}
					else if(cmpdFreq==QUARTERLY_COMPOUNDING)
					{
						Log.log(Log.DEBUG,"IFDAO","getROI","Quarterly Compounding ");
						tempRateOfInterest=((1+roiInfo.getRateOfInterest()/(QUARTERLY_COMPOUNDING*100)));
						Log.log(Log.DEBUG,"IFDAO","getROI","tempRateOfInterest "+tempRateOfInterest);
						Log.log(Log.DEBUG,"IFDAO","getROI","power"+((QUARTERLY_COMPOUNDING/3)-1));
						tempRateOfInterest=Math.pow(tempRateOfInterest,(QUARTERLY_COMPOUNDING/3)-1);
					}
					else if(cmpdFreq==HALF_YEARLY_COMPOUNDING)
					{
						Log.log(Log.DEBUG,"IFDAO","getROI","Half Yearly Compounding ");
						tempRateOfInterest=((1+roiInfo.getRateOfInterest()/(HALF_YEARLY_COMPOUNDING*100)));
						tempRateOfInterest=Math.pow(tempRateOfInterest,(powerValue/6)-1);
					}
					else
					{
						Log.log(Log.DEBUG,"IFDAO","getROI","Annual Compounding ");
						tempRateOfInterest=((1+roiInfo.getRateOfInterest()/(YEARLY_COMPOUNDING*100)));
						tempRateOfInterest=Math.pow(tempRateOfInterest,(powerValue/12)-1);
					}
					Log.log(Log.DEBUG,"IFDAO","getROI","tempRateOfInterest before"+tempRateOfInterest);
					tempRateOfInterest=tempRateOfInterest/(powerValue/12);

					Log.log(Log.DEBUG,"IFDAO","getROI","tempRateOfInterest "+tempRateOfInterest);

					roiInfo.setRateOfInterest(tempRateOfInterest);
				}

				Log.log(Log.DEBUG,"IFDAO","getROI","Tenure, Tenure Type and liveUnits "+tenure+","+tenureType+","+liveUnits);

				if(tenureType!=null)
				{
					if(tenureType.equalsIgnoreCase(MONTH))
					{
						tenure*=MONTH_VALUE;
					}
					if(tenureType.equalsIgnoreCase(YEAR))
					{
						tenure*=YEAR_VALUE;
					}
				}
				if(navDiff!=0)
				{
					roiInfo.setNoOfUnits(liveUnits);
					roiInfo.setNavDifference(navDiff);
					noOfYears=results.getInt(9);
					roiInfo.setPrincipalAmount(results.getDouble(10));

					Log.log(Log.DEBUG,"IFDAO","getROI","No of Years, Cost of purchage " +noOfYears+", "+roiInfo.getPrincipalAmount());

					if(noOfYears==0)
					{
						roiInfo.setNoOfYears(1);
						Log.log(Log.DEBUG,"IFDAO","getROI","Inside IF " +roiInfo.getNoOfYears());
					}
					else
					{
						roiInfo.setNoOfYears((int)Math.ceil((double)noOfYears/365));
						Log.log(Log.DEBUG,"IFDAO","getROI","Inside ELSE " +roiInfo.getNoOfYears()+", "+(Math.ceil(noOfYears/365)));
					}

					Log.log(Log.DEBUG,"IFDAO","getROI","No Of units, NAV Diff, No of Years" +
						" "+roiInfo.getNoOfUnits()+"," +
						""+roiInfo.getNavDifference()+","+roiInfo.getNoOfYears());
				}
				roiInfo.setNoOfDays(tenure);
				ArrayList instrumentGroups=(ArrayList)rois.get(roiInfo.getInstrumentName());

				if(instrumentGroups==null)
				{
					instrumentGroups=new ArrayList();
					rois.put(roiInfo.getInstrumentName(),instrumentGroups);
				}

				instrumentGroups.add(roiInfo);

				Log.log(Log.DEBUG,"IFDAO","getROI",
					"instrument name, principal amount, purchase date,maturity date, interest rate, no of days " +
					"" +roiInfo.getInstrumentName()+","+roiInfo.getPrincipalAmount()+"," +
						//""+roiInfo.getInvestedDate()+","+roiInfo.getMaturityDate()+"," +
							""+roiInfo.getRateOfInterest()+", "+roiInfo.getNoOfDays());
			}

			results.close();
			results=null;

			prepraredQuery.close();
			prepraredQuery=null;

		}
		catch (SQLException e)
		{
			Log.log(Log.ERROR,"IFDAO","getROI",e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Unable to get Investment Details ");
		}
		finally
		{
			DBConnection.freeConnection(connection);
		}
			Log.log(Log.INFO, "IFDAO", "getROI", "Exited");

		return rois;
	}


	public ExposureDetails getPositionDetails(Date currentDate, Date proposedDate) throws DatabaseException {
			Log.log(Log.INFO, "IFDAO", "getPositionDetails", "Entered");
			
			ExposureDetails exposureDetails = new ExposureDetails();
			double liveInvAmount = getPrincipalAmntInInvesteeGrpAndName(currentDate,proposedDate);
			double maturedAmount = getMaturityAmntInInvesteeGrpAndName(currentDate,proposedDate);
			
			double currentInvAmount = 0;
			
			Connection connection = DBConnection.getConnection();
			PreparedStatement positionStmt = null;
			
			ResultSet positionResult;
			
			try{
			
				String query = "select SUM(a.IDT_COST_OF_PURCHASE) " + 
								"FROM 	INVESTMENT_DETAIL a,investee b,investee_group c " + 
								"WHERE 	b.IGR_ID=c.IGR_ID " +
								"AND 	a.INV_ID=b.INV_ID " + 
								"AND a.IDT_PURCHASE_DT = trunc(sysdate)";
								
				positionStmt = connection.prepareStatement(query);
				positionResult = positionStmt.executeQuery();
				
				while(positionResult.next())
				{
					currentInvAmount = positionResult.getDouble(1);
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
			
			exposureDetails.setLiveInvtAmount(liveInvAmount);
			exposureDetails.setMaturedAmount(maturedAmount);
			exposureDetails.setInvestedAmount(currentInvAmount);
			
			
			return exposureDetails;
	}
	
	
	public ArrayList getInstrumentCategories() throws DatabaseException {
		Log.log(Log.INFO, "IFDAO", "getInstrumentCategories", "Entered");

		Connection connection = DBConnection.getConnection();
		ArrayList instCategories = new ArrayList();

		try {
			CallableStatement callable =
				connection.prepareCall(
					"{?= call packGetAllInstCategory.funcGetAllInstCategory(?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);
			callable.registerOutParameter(2, Constants.CURSOR);
			callable.registerOutParameter(3, Types.VARCHAR);

			callable.execute();

			int errorCode = callable.getInt(1);
			String error = callable.getString(3);

			Log.log(
				Log.DEBUG,
				"IFDAO",
				"getInstrumentCategories",
				"Error code and Error " + errorCode + " , " + error);

			if (errorCode == Constants.FUNCTION_FAILURE) {
				Log.log(Log.ERROR, "IFDAO", "getInstrumentCategories", error);
				callable.close();
				callable = null;
				throw new DatabaseException(error);
			}

			ResultSet groups = (ResultSet) callable.getObject(2);

			while (groups.next()) {
				String category = groups.getString(1);

				Log.log(
					Log.DEBUG,
					"IFDAO",
					"getInstrumentCategories",
					"group " + category);

				instCategories.add(category);
			}
			groups.close();
			groups=null;
			callable.close();
			callable = null;
		} catch (SQLException e) {
			Log.log(Log.ERROR, "IFDAO", "getInstrumentCategories", e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Unable to get Investee Groups ");
		} finally {
			DBConnection.freeConnection(connection);
		}

		Log.log(Log.INFO, "IFDAO", "getInstrumentCategories", "Exited");
		return instCategories;
	}
	
	public void saveInstrumentCategory(InstrumentCategory instCategory, String userId)
		throws DatabaseException {
		Log.log(Log.INFO, "IFDAO", "saveInstrumentCategory", "Entered");

		Connection connection = DBConnection.getConnection();

		try {
			connection = DBConnection.getConnection(false);
			CallableStatement statement =
				connection.prepareCall(
					"{? = call funcInsertInstCat(?,?,?,?,?)}");
			statement.registerOutParameter(1, java.sql.Types.INTEGER);
			String insCatName=instCategory.getIctName();
			String newInstCatName=instCategory.getIctNewName();
			String modInstCatName=instCategory.getIctModName();
			String instCatDesc=instCategory.getIctDesc();
			if (insCatName!=null && !insCatName.equals(""))
			{
				statement.setString(2, insCatName);
				statement.setString(3, modInstCatName);
				statement.setString(4, instCatDesc);
			}
			else
			{
				statement.setString(2, newInstCatName);
				statement.setString(3, null);
				statement.setString(4, instCatDesc);
			}
			statement.setString(5, userId);
			statement.registerOutParameter(6, java.sql.Types.VARCHAR);

			statement.execute();

			int status = statement.getInt(1);
			String errorCode = statement.getString(6);
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"saveInstrumentCategory",
				"Error code and error " + status + " " + errorCode);
			if (status == Constants.FUNCTION_FAILURE) {
				connection.rollback();
				statement.close();
				statement = null;
				Log.log(Log.ERROR, "IFDAO", "saveInstrumentCategory", errorCode);
				throw new DatabaseException(errorCode);
			}
			statement.close();
			statement = null;
			connection.commit();
		} catch (SQLException e) {
			Log.log(Log.ERROR, "IFDAO", "saveInstrumentCategory", e.getMessage());
			Log.logException(e);
			throw new DatabaseException("Unable to update InstrumentCategory");
		} finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO, "IFDAO", "saveInstrumentCategory", "Exited");
	}


	public InstrumentCategory getInstCategoryDetails(String instCatName) throws DatabaseException {
		Log.log(Log.INFO, "IFDAO", "getMaturityDetails", "Entered");

		Connection conn = null;
		CallableStatement callableStmt = null;
		int status = -1;
		String errorCode = null;
		InstrumentCategory instCatDetail = new InstrumentCategory();
		try {
			

			conn = DBConnection.getConnection();
			callableStmt =
				conn.prepareCall(
					"{?=call funcGetInstCategoryDtl(?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt.setString(2, instCatName);
			callableStmt.registerOutParameter(3, java.sql.Types.VARCHAR);
			callableStmt.registerOutParameter(4, java.sql.Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(4);
			Log.log(
				Log.DEBUG,
				"IFDAO",
				"getInstCategoryDetails",
				"Error code and error " + status + " " + errorCode);
			if (status == Constants.FUNCTION_FAILURE) {
				callableStmt.close();
				callableStmt = null;
				Log.log(Log.ERROR, "IFDAO", "getInstCategoryDetails", errorCode);
				throw new DatabaseException(errorCode);
			}
			String matDesc = callableStmt.getString(3);

			callableStmt.close();
			callableStmt = null;

			instCatDetail.setIctName(instCatName);
			instCatDetail.setIctDesc(matDesc);

		} catch (SQLException e) {
			Log.log(Log.ERROR, "IFDAO", "getMaturityDetails", e.getMessage());
			Log.logException(e);
			throw new DatabaseException("Unable to get MaturityDetails");
		} finally {
			DBConnection.freeConnection(conn);
		}
		Log.log(Log.INFO, "IFDAO", "getMaturityDetails", "Exited");

		return instCatDetail;
	}

	public void addInstrumentCatWiseCeiling(InstrumentCategoryWiseCeiling instCat,String userId)throws DatabaseException
	{
		Connection connection = DBConnection.getConnection();

		try {
			CallableStatement callable =
				connection.prepareCall(
					"{?= call funcInsCeilingInsCat(?,?,?,?,?,?)}");

			callable.registerOutParameter(1, Types.INTEGER);
			callable.setString(2, instCat.getInstrumentName());
			callable.setDouble(3, instCat.getCeilingLimit());

			java.sql.Date startDate = null;

			if (instCat.getCeilingStartDate() != null) {
				startDate =
					new java.sql.Date(
				instCat.getCeilingStartDate().getTime());
			}

			callable.setDate(4, startDate);
			if (instCat.getCeilingEndDate()!=null && !instCat.getCeilingEndDate().toString().equals(""))
			{
				callable.setDate(
					5,
					new java.sql.Date(
				instCat.getCeilingEndDate().getTime()));
			}
			else
			{
				callable.setNull(5, java.sql.Types.DATE);
			}

			callable.setString(6, userId);
			callable.registerOutParameter(7, Types.VARCHAR);

			callable.execute();

			int errorCode = callable.getInt(1);
			String error = callable.getString(7);

			Log.log(
				Log.DEBUG,
				"IFDAO",
				"addInstrumentCatWiseCeiling",
				"errorCode and error " + errorCode + "," + error);

			if (errorCode == Constants.FUNCTION_FAILURE) {
				Log.log(Log.ERROR, "IFDAO", "addInstrumentWiseCeiling", error);
				callable.close();
				callable = null;

				throw new DatabaseException(error);
			}
			callable.close();
			callable = null;
		} catch (SQLException e) {
			Log.log(
				Log.ERROR,
				"IFDAO",
				"addInstrumentWiseCeiling",
				e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Unable to store Instrument Category wise ceiling.");
		} finally {
			DBConnection.freeConnection(connection);
		}
		
	}
	
	
	public double getInvestedInInstCat(String instCatName, Date date)
					 throws DatabaseException
		{
			double instCatInvAmount=0;

			Log.log(Log.INFO, "IFDAO", "getInvestedInInstCat", "Entered");
			Connection connection = DBConnection.getConnection();

			try
			{
				CallableStatement callable =
						connection.prepareCall(
						"{?= call funcGetInvestedAmtICT(?,?,?,?)}");

				callable.registerOutParameter(1, Types.INTEGER);
				callable.setString(2,instCatName);
				callable.setDate(3, new java.sql.Date(date.getTime()));				
				callable.registerOutParameter(4, Types.DOUBLE);
				callable.registerOutParameter(5, Types.VARCHAR);

				callable.execute();

				int errorCode = callable.getInt(1);
				String error = callable.getString(5);

				Log.log(Log.DEBUG,"IFDAO","getInvestedInInstCat",
					"errorCode and error " + errorCode + "," + error);

				if (errorCode == Constants.FUNCTION_FAILURE) {
					Log.log(Log.ERROR, "IFDAO", "getInvestedInInstCat", error);
					callable.close();
					callable = null;

					throw new DatabaseException(error);
				}

				instCatInvAmount = callable.getDouble(4);
				Log.log(Log.DEBUG,"IFDAO","getInvestedInInstCat","corpus amount " +instCatInvAmount);

				callable.close();
				callable = null;
			}
			catch (SQLException e)
			{
				Log.log(Log.ERROR, "IFDAO", "getInvestedInInstCat", e.getMessage());
				Log.logException(e);

				throw new DatabaseException("Unable to get Investment amount for Instrument Category.");
			}
			finally
			{
				DBConnection.freeConnection(connection);
			}

			Log.log(Log.INFO, "IFDAO", "getInvestedInInstCat", "Exited");
			return instCatInvAmount;
	}
	
	public ArrayList getTDSList(Date fromDate, Date toDate) throws DatabaseException
	{
		Log.log(Log.INFO, "IFDAO", "getTDSList", "Entered");

		Connection connection = DBConnection.getConnection();
		ArrayList tdsList=new ArrayList();
		TDSDetail tdsDetails;

		try {
			CallableStatement callable =
				connection.prepareCall(
					"{?= call packGetTDSList.funcGetTDSList(?,?,?,?)}");
					
			callable.registerOutParameter(1, java.sql.Types.INTEGER);
			callable.setDate(2, new java.sql.Date(fromDate.getTime()));
			callable.setDate(3, new java.sql.Date(toDate.getTime()));
			callable.registerOutParameter(4, Constants.CURSOR);
			callable.registerOutParameter(5, java.sql.Types.VARCHAR);
			callable.execute();
			int status = callable.getInt(1);
			Log.log(Log.INFO, "IFDAO", "getTDSList", "status " + status);
			if (status==Constants.FUNCTION_FAILURE)
			{
				String error=callable.getString(5);
				Log.log(Log.INFO, "IFDAO", "getTDSList", "error " + error);

				callable.close();
				callable=null;
				throw new DatabaseException(error);
			}
			ResultSet tds = (ResultSet)callable.getObject(4);
			while (tds.next())
			{
				tdsDetails = new TDSDetail();
				tdsDetails.setTdsID(tds.getString(1));
				tdsDetails.setInvesteeName(tds.getString(2));
				tdsDetails.setInstrumentName(tds.getString(3));
				String reptNo = tds.getString(5);
				String invRefNo = tds.getString(4);
				tdsDetails.setInvestmentRefNumber(reptNo+"("+invRefNo+")");
				tdsDetails.setTdsAmount(tds.getDouble(6));
				tdsDetails.setTdsDeductedDate(tds.getDate(7));
				tdsDetails.setIsTDSCertificateReceived(tds.getString(8));
				tdsList.add(tdsDetails);
			}
			tds.close();
			tds=null;
			callable.close();
			callable=null;
		}
		catch (SQLException e)
		{
			Log.log(Log.ERROR,"IFDAO","getTDSList",e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Unable to get TDS List ");
		}
		finally
		{
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO, "IFDAO", "getTDSList", "Exited");
		return tdsList;

	}

	public TDSDetail getTDSDetails(String tdsId) throws DatabaseException {
			Log.log(Log.INFO, "IFDAO", "getTDSDetails", "Entered");

			Connection conn = null;
			CallableStatement callableStmt = null;
			int status = -1;
			String errorCode = null;
			TDSDetail tdsDetail = new TDSDetail();
			try {
				Log.log(Log.INFO, "IFDAO", "getTDSDetails", "tds id " + tdsId);

				conn = DBConnection.getConnection();
				callableStmt =
					conn.prepareCall(
						"{?=call funcGetTDSDetails(?,?,?,?,?,?,?,?,?)}");
				callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
				callableStmt.setString(2, tdsId);
				callableStmt.registerOutParameter(3, java.sql.Types.VARCHAR);
				callableStmt.registerOutParameter(4, java.sql.Types.VARCHAR);
				callableStmt.registerOutParameter(5, java.sql.Types.VARCHAR);
				callableStmt.registerOutParameter(6, java.sql.Types.VARCHAR);
				callableStmt.registerOutParameter(7, java.sql.Types.DOUBLE);
				callableStmt.registerOutParameter(8, java.sql.Types.DATE);
				callableStmt.registerOutParameter(9, java.sql.Types.VARCHAR);
				callableStmt.registerOutParameter(10, java.sql.Types.VARCHAR);

				callableStmt.execute();
				status = callableStmt.getInt(1);
				errorCode = callableStmt.getString(10);
				Log.log(
					Log.DEBUG,
					"IFDAO",
					"getTDSDetails",
					"Error code and error " + status + " " + errorCode);
				if (status == Constants.FUNCTION_FAILURE) {
					callableStmt.close();
					callableStmt = null;
					Log.log(Log.ERROR, "IFDAO", "getTDSDetails", errorCode);
					throw new DatabaseException(errorCode);
				}

				tdsDetail.setInvesteeName(callableStmt.getString(3));
				tdsDetail.setInstrumentName(callableStmt.getString(4));
				tdsDetail.setInvestmentRefNumber(callableStmt.getString(6)+"("+callableStmt.getString(5)+")");
				tdsDetail.setTdsAmount(callableStmt.getDouble(7));
				tdsDetail.setTdsDeductedDate(DateHelper.sqlToUtilDate( callableStmt.getDate(8)));
				tdsDetail.setIsTDSCertificateReceived(callableStmt.getString(9));
				tdsDetail.setTdsID(tdsId);

				callableStmt.close();
				callableStmt = null;

			} catch (SQLException e) {
				Log.log(Log.ERROR, "IFDAO", "getTDSDetails", e.getMessage());
				Log.logException(e);
				throw new DatabaseException("Unable to get tds details");
			} finally {
				DBConnection.freeConnection(conn);
			}
			Log.log(Log.INFO, "IFDAO", "getTDSDetails", "Exited");

			return tdsDetail;
		}
		
		public void insertMiscReceipts(Date date, Map receipts, String userId) throws DatabaseException
		{
			Log.log(Log.INFO, "IFDAO", "insertMiscReceipts", "Entered");

			Connection conn = null;
			CallableStatement callableStmt = null;
			int status = -1;
			String errorCode = null;
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			
			try {			
				conn = DBConnection.getConnection(false);
				callableStmt = conn.prepareCall("{?=call packMiscReport.funInsMiscReport(?,?,?,?,?,?,?,?,?,?)}");
	
				MiscReceipts miscReceipts = new MiscReceipts();
				
				Set receiptsSet = receipts.keySet();
				Iterator receiptsIterator = receiptsSet.iterator();
				
				while (receiptsIterator.hasNext())
				{
					String key = (String)receiptsIterator.next();
					miscReceipts = (MiscReceipts)receipts.get(key);
					callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
					callableStmt.setDate(2, new java.sql.Date(date.getTime()));
					if (miscReceipts.getId()==0)
					{
						callableStmt.setNull(3, java.sql.Types.INTEGER);
					}
					else
					{
						callableStmt.setInt(3, miscReceipts.getId());
					}
					callableStmt.setString(4, miscReceipts.getSourceOfFund());
					callableStmt.setDate(5, new java.sql.Date((dateFormat.parse(miscReceipts.getInstrumentDate(), new ParsePosition(0))).getTime()));
					callableStmt.setInt(6, Integer.parseInt(miscReceipts.getInstrumentNo()));
					callableStmt.setDouble(7, Double.parseDouble(miscReceipts.getAmount()));
					callableStmt.setString(8, miscReceipts.getIsConsideredForInv());
					callableStmt.setDate(9, new java.sql.Date((dateFormat.parse(miscReceipts.getDateOfReceipt(), new ParsePosition(0))).getTime()));
					callableStmt.setString(10, userId);
					callableStmt.registerOutParameter(11, java.sql.Types.VARCHAR);
					
					callableStmt.execute();
					status = callableStmt.getInt(1);
					errorCode = callableStmt.getString(11);
					Log.log(
						Log.DEBUG,
						"IFDAO",
						"insertMiscReceipts",
						"Error code and error " + status + " " + errorCode);
					if (status == Constants.FUNCTION_FAILURE) {
						conn.rollback();
						callableStmt.close();
						callableStmt = null;
						Log.log(Log.ERROR, "IFDAO", "insertMiscReceipts", errorCode);
						throw new DatabaseException(errorCode);
					}
					
					miscReceipts=null;
				}
				conn.commit();
			} catch (SQLException e) {
				
				Log.log(Log.ERROR, "IFDAO", "insertMiscReceipts", e.getMessage());
				Log.logException(e);
				throw new DatabaseException("Unable to insert receipts");
			}
			catch (DatabaseException db)
			{
				try
				{
					conn.rollback();
				}
				catch(SQLException sql)
				{
					Log.log(Log.ERROR, "IFDAO", "insertMiscReceipts", "error rolling back");
				}
				throw db;
			} finally {
				DBConnection.freeConnection(conn);
			}
			
			Log.log(Log.INFO, "IFDAO", "insertMiscReceipts", "Exited");

		}

		public Map getMiscReceiptsForDate(Date date) throws DatabaseException {
				Log.log(Log.INFO, "IFDAO", "getMiscReceiptsForDate", "Entered");

				Connection conn = null;
				CallableStatement callableStmt = null;
				int status = -1;
				String errorCode = null;
				Map receipts = new TreeMap();
				MiscReceipts miscReceipts = new MiscReceipts();
				ResultSet rsReceipts;
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				DecimalFormat df= new DecimalFormat("######################.##");
				
				try {
					Log.log(Log.INFO, "IFDAO", "getMiscReceiptsForDate", "date " + date);

					conn = DBConnection.getConnection();
					callableStmt =
						conn.prepareCall(
							"{?=call packMiscReport.funcGetMiscReport(?,?,?)}");
					callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
					callableStmt.setDate(2, new java.sql.Date(date.getTime()));
					callableStmt.registerOutParameter(3, Constants.CURSOR);
					callableStmt.registerOutParameter(4, java.sql.Types.VARCHAR);

					callableStmt.execute();
					status = callableStmt.getInt(1);
					errorCode = callableStmt.getString(4);
					Log.log(
						Log.DEBUG,
						"IFDAO",
						"getMiscReceiptsForDate",
						"Error code and error " + status + " " + errorCode);
					if (status == Constants.FUNCTION_FAILURE) {
						callableStmt.close();
						callableStmt = null;
						Log.log(Log.ERROR, "IFDAO", "getMiscReceiptsForDate", errorCode);
						throw new DatabaseException(errorCode);
					}

					int counter=0;


					rsReceipts = (ResultSet) callableStmt.getObject(3);
					while (rsReceipts.next())
					{
						miscReceipts = new MiscReceipts();
						miscReceipts.setId(rsReceipts.getInt(1));
						miscReceipts.setSourceOfFund(rsReceipts.getString(3));
						miscReceipts.setInstrumentDate(dateFormat.format(rsReceipts.getDate(4)));
						miscReceipts.setInstrumentNo(rsReceipts.getInt(5)+"");
						miscReceipts.setAmount(df.format(rsReceipts.getDouble(6)));
						miscReceipts.setIsConsideredForInv(rsReceipts.getString(7));
						miscReceipts.setDateOfReceipt(dateFormat.format(rsReceipts.getDate(8)));
						
						receipts.put("key-"+counter, miscReceipts);
						Log.log(Log.DEBUG, "IFDAO", "getMiscReceiptsForDate", "added  " + "key-"+counter);
						counter++;
					}
					callableStmt.close();
					callableStmt = null;

				} catch (SQLException e) {
					Log.log(Log.ERROR, "IFDAO", "getMiscReceiptsForDate", e.getMessage());
					Log.logException(e);
					throw new DatabaseException("Unable to get Miscellaneous Receipts");
				} finally {
					DBConnection.freeConnection(conn);
				}
				Log.log(Log.INFO, "IFDAO", "getMiscReceiptsForDate", "Exited");

				return receipts;
			}


		public ArrayList applicationsForClaimProjection()throws DatabaseException
		{
			ArrayList appsForProjection = new ArrayList();
			
			ArrayList tcApprovedList = new ArrayList();
			ArrayList tcExpiredList = new ArrayList();
			ArrayList wcApprovedList = new ArrayList();
			ArrayList wcExpiredList = new ArrayList();
			
			Connection conn = null;
			CallableStatement callableStmt = null;
			
			try {
				Log.log(Log.INFO, "IFDAO", "applicationsForClaimProjection","entered");

				conn = DBConnection.getConnection();
				callableStmt =
					conn.prepareCall(
						"{?=call packGetAppsForClaimProjection.funcGetTCApprovedForProjection(?,?)}");
						
				callableStmt.registerOutParameter(1,Types.INTEGER);
				callableStmt.registerOutParameter(2,Constants.CURSOR);
				callableStmt.registerOutParameter(3,Types.VARCHAR);
 
				callableStmt.execute();

				int functionReturnValue=callableStmt.getInt(1);
				   if(functionReturnValue==Constants.FUNCTION_FAILURE){
	
					   String error = callableStmt.getString(3);
	
					callableStmt.close();
					callableStmt=null;
	
					   conn.rollback();
	
					   throw new DatabaseException(error);
				   }else{
				   	
					ResultSet tcApprovedResults=(ResultSet)callableStmt.getObject(2);
					while(tcApprovedResults.next())
					{
						Application application = new Application();
						TermLoan termLoan = new TermLoan();
				   		
						application.setCgpan(tcApprovedResults.getString(1));
						//Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","tc approved cgpan :" + tcApprovedResults.getString(1));
						application.setGuaranteeStartDate(new java.util.Date(tcApprovedResults.getDate(2).getTime()));
						termLoan.setTenure(tcApprovedResults.getInt(3));
						termLoan.setFirstDisbursementDate(new java.util.Date(tcApprovedResults.getDate(4).getTime()));
						application.setApprovedAmount(tcApprovedResults.getDouble(5));
				   		
						application.setTermLoan(termLoan);
				   		
						tcApprovedList.add(application);
					}
				   		
				   }
				   


				callableStmt =
					conn.prepareCall(
						"{?=call packGetAppsForClaimProjection.funcGetTCExpiredForProjection(?,?)}");
						
				callableStmt.registerOutParameter(1,Types.INTEGER);
				callableStmt.registerOutParameter(2,Constants.CURSOR);
				callableStmt.registerOutParameter(3,Types.VARCHAR);
 
				callableStmt.execute();

				functionReturnValue=callableStmt.getInt(1);
				   if(functionReturnValue==Constants.FUNCTION_FAILURE){
	
					   String error = callableStmt.getString(3);
	
					callableStmt.close();
					callableStmt=null;
	
					   conn.rollback();
	
					   throw new DatabaseException(error);
				   }else{
				   	
					ResultSet tcExpiredResults=(ResultSet)callableStmt.getObject(2);
					while(tcExpiredResults.next())
					{
						Application application = new Application();
						TermLoan termLoan = new TermLoan();
						
						application.setCgpan(tcExpiredResults.getString(1));
						application.setGuaranteeStartDate(new java.util.Date(tcExpiredResults.getDate(2).getTime()));
						termLoan.setTenure(tcExpiredResults.getInt(3));
						termLoan.setFirstDisbursementDate(new java.util.Date(tcExpiredResults.getDate(4).getTime()));
						application.setApprovedAmount(tcExpiredResults.getDouble(5));
				   		
						application.setTermLoan(termLoan);
				   		
						tcExpiredList.add(application);
					}
				   		
				   }


				callableStmt =
					conn.prepareCall(
						"{?=call packGetAppsForClaimProjection.funcGetWCApprovedForProjection(?,?)}");
						
				callableStmt.registerOutParameter(1,Types.INTEGER);
				callableStmt.registerOutParameter(2,Constants.CURSOR);
				callableStmt.registerOutParameter(3,Types.VARCHAR);
 
				callableStmt.execute();

				functionReturnValue=callableStmt.getInt(1);
				   if(functionReturnValue==Constants.FUNCTION_FAILURE){
	
					   String error = callableStmt.getString(3);
	
					callableStmt.close();
					callableStmt=null;
	
					   conn.rollback();
	
					   throw new DatabaseException(error);
				   }else{
				   	
					ResultSet wcApprovedResults=(ResultSet)callableStmt.getObject(2);
					while(wcApprovedResults.next())
					{
						Application application = new Application();
						WorkingCapital wc = new WorkingCapital();
						
						application.setCgpan(wcApprovedResults.getString(1));
						application.setGuaranteeStartDate(new java.util.Date(wcApprovedResults.getDate(2).getTime()));
						wc.setWcTenure(wcApprovedResults.getInt(3));
						application.setApprovedAmount(wcApprovedResults.getDouble(4));

						application.setWc(wc);
				   		
						wcApprovedList.add(application);
					}
				   	
				   		
				   }

				callableStmt =
					conn.prepareCall(
						"{?=call packGetAppsForClaimProjection.funcGetWCExpiredForProjection(?,?)}");
						
				callableStmt.registerOutParameter(1,Types.INTEGER);
				callableStmt.registerOutParameter(2,Constants.CURSOR);
				callableStmt.registerOutParameter(3,Types.VARCHAR);
 
				callableStmt.execute();

				functionReturnValue=callableStmt.getInt(1);
				   if(functionReturnValue==Constants.FUNCTION_FAILURE){
	
					   String error = callableStmt.getString(3);
	
					callableStmt.close();
					callableStmt=null;
	
					   conn.rollback();
	
					   throw new DatabaseException(error);
				   }else{
				   	
					ResultSet wcExpiredResults=(ResultSet)callableStmt.getObject(2);
					while(wcExpiredResults.next())
					{
						Application application = new Application();
						WorkingCapital wc = new WorkingCapital();
						
						application.setCgpan(wcExpiredResults.getString(1));
						application.setGuaranteeStartDate(new java.util.Date(wcExpiredResults.getDate(2).getTime()));
						wc.setWcTenure(wcExpiredResults.getInt(3));
						application.setApprovedAmount(wcExpiredResults.getDouble(4));

						application.setWc(wc);
				   		
						wcExpiredList.add(application);
					}
				   		
				   }
				   
				appsForProjection.add(tcApprovedList);
				appsForProjection.add(tcExpiredList);
				appsForProjection.add(wcApprovedList);
				appsForProjection.add(wcExpiredList);

			conn.commit();

			   }
				catch(SQLException sqlException) {
			
					try
					{
						conn.rollback();
					}
					catch (SQLException ignore){
						Log.log(Log.INFO,"ApplicationDAO","checkDuplicate","Exception :" + ignore.getMessage());
					}
			

				throw new DatabaseException(sqlException.getMessage());

			   }finally{
					DBConnection.freeConnection(conn);
				}
			
					
			
			return appsForProjection;
		}
		
		/**
		 * retrieve the list of applications for NPA Accounts
		 * @return
		 * @throws DatabaseException
		 */
		
		public ArrayList applicationsForClaimProjNPA()throws DatabaseException
		{
			ArrayList appsForProjection = new ArrayList();
			
			ArrayList tcApprovedList = new ArrayList();
			
			/*
			ArrayList tcExpiredList = new ArrayList();
			ArrayList wcApprovedList = new ArrayList();
			ArrayList wcExpiredList = new ArrayList();
			*/
			
			Connection conn = null;
			CallableStatement callableStmt = null;
			
			try {
				Log.log(Log.INFO, "IFDAO", "applicationsForClaimProjNonNPA","entered");

				conn = DBConnection.getConnection();
				callableStmt =
					conn.prepareCall(
						"{?=call packGetAppsForClaimProjNPA.funcGetTCApprovedForProjection(?,?)}");
						
				callableStmt.registerOutParameter(1,Types.INTEGER);
				callableStmt.registerOutParameter(2,Constants.CURSOR);
				callableStmt.registerOutParameter(3,Types.VARCHAR);
 
				callableStmt.execute();

				int functionReturnValue=callableStmt.getInt(1);
				   if(functionReturnValue==Constants.FUNCTION_FAILURE){
	
					   String error = callableStmt.getString(3);
	
					callableStmt.close();
					callableStmt=null;
	
					   conn.rollback();
	
					   throw new DatabaseException(error);
				   }else{
				   	
						ResultSet tcApprovedResults=(ResultSet)callableStmt.getObject(2);
						while(tcApprovedResults.next())
						{						
							NPADetails npaDetails = new NPADetails();
							
							double approvedAmount =  tcApprovedResults.getDouble(1);
							double outAmtAsOnNPA = tcApprovedResults.getDouble(2);
							
							double minAmount = Math.min(approvedAmount,outAmtAsOnNPA);
							
							npaDetails.setOsAmtOnNPA(minAmount);
							npaDetails.setNpaDate(tcApprovedResults.getDate(3));
							npaDetails.setCgbid(tcApprovedResults.getString(4));
							appsForProjection.add(npaDetails);
						}
				   		
				   }

/*				callableStmt =
					conn.prepareCall(
						"{?=call packGetAppsForClaimProjNonNPA.funcGetTCExpiredForProjection(?,?)}");
						
				callableStmt.registerOutParameter(1,Types.INTEGER);
				callableStmt.registerOutParameter(2,Constants.CURSOR);
				callableStmt.registerOutParameter(3,Types.VARCHAR);
 
				callableStmt.execute();

				functionReturnValue=callableStmt.getInt(1);
				   if(functionReturnValue==Constants.FUNCTION_FAILURE){
	
					   String error = callableStmt.getString(3);
	
					callableStmt.close();
					callableStmt=null;
	
					   conn.rollback();
	
					   throw new DatabaseException(error);
				   }else{
				   	
					ResultSet tcExpiredResults=(ResultSet)callableStmt.getObject(2);
					while(tcExpiredResults.next())
					{
						Application application = new Application();
						TermLoan termLoan = new TermLoan();
						
						application.setCgpan(tcExpiredResults.getString(1));
						application.setGuaranteeStartDate(new java.util.Date(tcExpiredResults.getDate(2).getTime()));
						termLoan.setTenure(tcExpiredResults.getInt(3));
						termLoan.setFirstDisbursementDate(new java.util.Date(tcExpiredResults.getDate(4).getTime()));
						application.setApprovedAmount(tcExpiredResults.getDouble(5));
				   		
						application.setTermLoan(termLoan);
				   		
						tcExpiredList.add(application);
					}
				   		
				   }


				callableStmt =
					conn.prepareCall(
						"{?=call packGetAppsForClaimProjNonNPA.funcGetWCApprovedForProjection(?,?)}");
						
				callableStmt.registerOutParameter(1,Types.INTEGER);
				callableStmt.registerOutParameter(2,Constants.CURSOR);
				callableStmt.registerOutParameter(3,Types.VARCHAR);
 
				callableStmt.execute();

				functionReturnValue=callableStmt.getInt(1);
				   if(functionReturnValue==Constants.FUNCTION_FAILURE){
	
					   String error = callableStmt.getString(3);
	
					callableStmt.close();
					callableStmt=null;
	
					   conn.rollback();
	
					   throw new DatabaseException(error);
				   }else{
				   	
					ResultSet wcApprovedResults=(ResultSet)callableStmt.getObject(2);
					while(wcApprovedResults.next())
					{
						Application application = new Application();
						WorkingCapital wc = new WorkingCapital();
						
						application.setCgpan(wcApprovedResults.getString(1));
						application.setGuaranteeStartDate(new java.util.Date(wcApprovedResults.getDate(2).getTime()));
						wc.setWcTenure(wcApprovedResults.getInt(3));
						application.setApprovedAmount(wcApprovedResults.getDouble(4));

						application.setWc(wc);
				   		
						wcApprovedList.add(application);
					}
				   	
				   		
				   }

				callableStmt =
					conn.prepareCall(
						"{?=call packGetAppsForClaimProjNonNPA.funcGetWCExpiredForProjection(?,?)}");
						
				callableStmt.registerOutParameter(1,Types.INTEGER);
				callableStmt.registerOutParameter(2,Constants.CURSOR);
				callableStmt.registerOutParameter(3,Types.VARCHAR);
 
				callableStmt.execute();

				functionReturnValue=callableStmt.getInt(1);
				   if(functionReturnValue==Constants.FUNCTION_FAILURE){
	
					   String error = callableStmt.getString(3);
	
					callableStmt.close();
					callableStmt=null;
	
					   conn.rollback();
	
					   throw new DatabaseException(error);
				   }else{
				   	
					ResultSet wcExpiredResults=(ResultSet)callableStmt.getObject(2);
					while(wcExpiredResults.next())
					{
						Application application = new Application();
						WorkingCapital wc = new WorkingCapital();
						
						application.setCgpan(wcExpiredResults.getString(1));
						application.setGuaranteeStartDate(new java.util.Date(wcExpiredResults.getDate(2).getTime()));
						wc.setWcTenure(wcExpiredResults.getInt(3));
						application.setApprovedAmount(wcExpiredResults.getDouble(4));

						application.setWc(wc);
				   		
						wcExpiredList.add(application);
					}
				   		
				   }
				   */
				
				/*
				appsForProjection.add(tcExpiredList);
				appsForProjection.add(wcApprovedList);
				appsForProjection.add(wcExpiredList);
				*/

			conn.commit();

			   }
				catch(SQLException sqlException) {
			
					try
					{
						conn.rollback();
					}
					catch (SQLException ignore){
						Log.log(Log.INFO,"ApplicationDAO","checkDuplicate","Exception :" + ignore.getMessage());
					}
			

				throw new DatabaseException(sqlException.getMessage());

			   }finally{
					DBConnection.freeConnection(conn);
				}
			
					
			
			return appsForProjection;
		}

		public Map getFundTransfersForDate(Date date) throws DatabaseException {
				Log.log(Log.INFO, "IFDAO", "getFundTransfersForDate", "Entered");

				Connection conn = null;
				CallableStatement callableStmt = null;
				int status = -1;
				String errorCode = null;
				Map fundTransfers = new TreeMap();
				Map banks = new TreeMap();
				FundTransferDetail fTDetail = new FundTransferDetail();
				ResultSet rsDetails;
				ResultSet rsBanks;
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				DecimalFormat df= new DecimalFormat("######################.##");
				
				try {
					Log.log(Log.INFO, "IFDAO", "getFundTransfersForDate", "date " + date);

					conn = DBConnection.getConnection();
					callableStmt =
						conn.prepareCall(
							"{?=call packFundTransfer.funcGetFTR(?,?,?,?)}");
					callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
					callableStmt.setDate(2, new java.sql.Date(date.getTime()));
					callableStmt.registerOutParameter(3, Constants.CURSOR);
					callableStmt.registerOutParameter(4, Constants.CURSOR);
					callableStmt.registerOutParameter(5, java.sql.Types.VARCHAR);

					callableStmt.execute();
					status = callableStmt.getInt(1);
					errorCode = callableStmt.getString(5);
					Log.log(
						Log.DEBUG,
						"IFDAO",
						"getFundTransfersForDate",
						"Error code and error " + status + " " + errorCode);
					if (status == Constants.FUNCTION_FAILURE) {
						callableStmt.close();
						callableStmt = null;
						Log.log(Log.ERROR, "IFDAO", "getFundTransfersForDate", errorCode);
						throw new DatabaseException(errorCode);
					}

					int counter=0;


					rsDetails = (ResultSet) callableStmt.getObject(3);
					while (rsDetails.next())
					{
						fTDetail = new FundTransferDetail();
						fTDetail.setId(rsDetails.getInt(1));
						fTDetail.setBankId(rsDetails.getInt(2));
						fTDetail.setBankName(rsDetails.getString(3)+", "+rsDetails.getString(4));
						fTDetail.setAccNumber(rsDetails.getString(5));
						fTDetail.setClosingBalanceDate(dateFormat.format(rsDetails.getDate(6)));
						double stmtBal=rsDetails.getDouble(7);
						double unClBal=rsDetails.getDouble(8);
						fTDetail.setBalanceAsPerStmt(df.format(stmtBal));
						fTDetail.setUnclearedBalance(df.format(unClBal));
						fTDetail.setBalanceUtil(df.format(stmtBal-unClBal));
						double minBal=rsDetails.getDouble(9);
						fTDetail.setMinBalance(df.format(minBal));
						double amtCa=rsDetails.getDouble(10);
						fTDetail.setAmtCANotReflected(df.format(amtCa));
						fTDetail.setAmtForIDBI(df.format(stmtBal-unClBal-minBal-amtCa));
						fTDetail.setAvailForInvst(rsDetails.getString(11));
						fTDetail.setRemarks(rsDetails.getString(12));
						
						fundTransfers.put("key-"+counter, fTDetail);
						Log.log(Log.DEBUG, "IFDAO", "getFundTransfersForDate", "added  " + "key-"+counter);
						counter++;
					}

					rsBanks = (ResultSet) callableStmt.getObject(4);
					while (rsBanks.next())
					{
						fTDetail = new FundTransferDetail();
						fTDetail.setId(0);
						fTDetail.setBankId(rsBanks.getInt(1));
						fTDetail.setBankName(rsBanks.getString(2)+", "+rsBanks.getString(3));
						fTDetail.setAccNumber(rsBanks.getString(4));
						fTDetail.setMinBalance(df.format(rsBanks.getDouble(5)));
						
						fundTransfers.put("key-"+counter, fTDetail);
						Log.log(Log.DEBUG, "IFDAO", "getFundTransfersForDate", "added bank " + fTDetail.getBankId());
						counter++;
					}
					
					Log.log(Log.ERROR, "IFDAO", "getFundTransfersForDate", "size " + fundTransfers.size());
					
					rsDetails.close();
					rsDetails=null;
					rsBanks.close();
					rsBanks=null;
					callableStmt.close();
					callableStmt = null;

				} catch (SQLException e) {
					Log.log(Log.ERROR, "IFDAO", "getFundTransfersForDate", e.getMessage());
					Log.logException(e);
					throw new DatabaseException("Unable to get Fund Transfers");
				} finally {
					DBConnection.freeConnection(conn);
				}
				Log.log(Log.INFO, "IFDAO", "getFundTransfersForDate", "Exited");

				return fundTransfers;
			}

		public void updateFundTransfers(Date date, Map fundTransfers, String userId) throws DatabaseException
		{
			Log.log(Log.INFO, "IFDAO", "updateFundTransfers", "Entered");

			Connection conn = null;
			CallableStatement callableStmt = null;
			int status = -1;
			String errorCode = null;
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			
			try {			
				conn = DBConnection.getConnection(false);
				callableStmt = conn.prepareCall("{?=call packFundTransfer.funcInsFTR(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
	
				FundTransferDetail ftDetail = new FundTransferDetail();
				
				Set ftSet = fundTransfers.keySet();
				Iterator ftIterator = ftSet.iterator();
				
				while (ftIterator.hasNext())
				{
					String key = (String)ftIterator.next();
					ftDetail = (FundTransferDetail)fundTransfers.get(key);
					callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
					Log.log(Log.DEBUG, "IFDAO", "updateFundTransfers", "id " + ftDetail.getId());
					if (ftDetail.getId()==0)
					{
						callableStmt.setNull(2, java.sql.Types.INTEGER);
					}
					else
					{
						callableStmt.setInt(2, ftDetail.getId());
					}
					String bankName= ftDetail.getBankName().trim();
					Log.log(Log.DEBUG, "IFDAO", "updateFundTransfers", "full bank name " + bankName);
					int index = bankName.indexOf(", ");
					String branchName=bankName.substring(index+2, bankName.length());
					bankName=bankName.substring(0,index);
					Log.log(Log.DEBUG, "IFDAO", "updateFundTransfers", "bank name " + bankName);
					Log.log(Log.DEBUG, "IFDAO", "updateFundTransfers", "branch name " + branchName);
					callableStmt.setString(3, bankName);
					callableStmt.setString(4, branchName);
					Log.log(Log.DEBUG, "IFDAO", "updateFundTransfers", "bank acc no " + ftDetail.getAccNumber());
					callableStmt.setString(5, ftDetail.getAccNumber());
					callableStmt.setDate(6, new java.sql.Date(date.getTime()));
					callableStmt.setDate(7, new java.sql.Date((dateFormat.parse(ftDetail.getClosingBalanceDate(), new ParsePosition(0))).getTime()));
					callableStmt.setDouble(8, Double.parseDouble(ftDetail.getBalanceAsPerStmt()));
					callableStmt.setDouble(9, Double.parseDouble(ftDetail.getUnclearedBalance()));
					callableStmt.setDouble(10, Double.parseDouble(ftDetail.getAmtCANotReflected()));
					callableStmt.setString(11, ftDetail.getAvailForInvst());
					callableStmt.setString(12, ftDetail.getRemarks());
					callableStmt.setString(13, userId);
					callableStmt.registerOutParameter(14, java.sql.Types.VARCHAR);
					
					callableStmt.execute();
					status = callableStmt.getInt(1);
					errorCode = callableStmt.getString(14);
					Log.log(
						Log.DEBUG,
						"IFDAO",
						"updateFundTransfers",
						"Error code and error " + status + " " + errorCode);
					if (status == Constants.FUNCTION_FAILURE) {
						conn.rollback();
						callableStmt.close();
						callableStmt = null;
						Log.log(Log.ERROR, "IFDAO", "updateFundTransfers", errorCode);
						throw new DatabaseException(errorCode);
					}
					else if (status==2)
					{
						conn.rollback();
						callableStmt.close();
						callableStmt = null;
						Log.log(Log.ERROR, "IFDAO", "updateFundTransfers", "closing balance date already available.");
						throw new DatabaseException("Closing Balance Date details for " + bankName + ", " + branchName + " is already available.");
					}
					
					ftDetail=null;
				}
				conn.commit();
			} catch (SQLException e) {
				
				Log.log(Log.ERROR, "IFDAO", "updateFundTransfers", e.getMessage());
				Log.logException(e);
				throw new DatabaseException("Unable to insert fund transfers");
			}
			catch (DatabaseException db)
			{
				try
				{
					conn.rollback();
				}
				catch(SQLException sql)
				{
					Log.log(Log.ERROR, "IFDAO", "updateFundTransfers", "error rolling back");
				}
				throw db;
			} finally {
				DBConnection.freeConnection(conn);
			}
			
			Log.log(Log.INFO, "IFDAO", "updateFundTransfers", "Exited");

		}

		public BankReconcilation getBankReconDetails(Date date) throws DatabaseException {
				Log.log(Log.INFO, "IFDAO", "getBankReconDetails", "Entered");

				Connection conn = null;
				CallableStatement callableStmt = null;
				int status = -1;
				String errorCode = null;
				BankReconcilation bankReconcilation = new BankReconcilation();
				ArrayList chequesArr = new ArrayList();
				ChequeDetails chequeDetails = new ChequeDetails();
				ResultSet rsDetails;
				ResultSet rsCheques;
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				DecimalFormat df= new DecimalFormat("######################.##");
				
				try {
					Log.log(Log.INFO, "IFDAO", "getBankReconDetails", "date " + date);

					conn = DBConnection.getConnection();
					callableStmt =
						conn.prepareCall(
							"{?=call packBankRecon.funcGetBankRecon(?,?,?,?,?,?)}");
					callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
					callableStmt.setDate(2, new java.sql.Date(date.getTime()));
					callableStmt.registerOutParameter(3, Constants.CURSOR);
					callableStmt.registerOutParameter(4, Constants.CURSOR);
					callableStmt.registerOutParameter(5, java.sql.Types.DOUBLE);
					callableStmt.registerOutParameter(6, java.sql.Types.DOUBLE);
					callableStmt.registerOutParameter(7, java.sql.Types.VARCHAR);

					callableStmt.execute();
					status = callableStmt.getInt(1);
					errorCode = callableStmt.getString(7);
					Log.log(
						Log.DEBUG,
						"IFDAO",
						"getBankReconDetails",
						"Error code and error " + status + " " + errorCode);
					if (status == Constants.FUNCTION_FAILURE) {
						callableStmt.close();
						callableStmt = null;
						Log.log(Log.ERROR, "IFDAO", "getBankReconDetails", errorCode);
						throw new DatabaseException(errorCode);
					}

					rsDetails = (ResultSet) callableStmt.getObject(3);
					while (rsDetails.next())
					{
						Log.log(
								Log.DEBUG,
								"IFDAO",
								"getBankReconDetails",
								"inside bank recon rs ");
						bankReconcilation = new BankReconcilation();
						bankReconcilation.setReconId(rsDetails.getInt(1));
						bankReconcilation.setCgtsiBalance(rsDetails.getDouble(2));
						bankReconcilation.setReconDate(rsDetails.getDate(3));
						bankReconcilation.setChequeIssuedAmount(rsDetails.getDouble(4));
						bankReconcilation.setDirectCredit(rsDetails.getDouble(5));
						bankReconcilation.setDirectDebit(rsDetails.getDouble(6));
						bankReconcilation.setClosingBalanceIDBI(rsDetails.getDouble(7));
					}
					
					rsCheques = (ResultSet) callableStmt.getObject(4);
					while (rsCheques.next())
					{
						Log.log(
								Log.DEBUG,
								"IFDAO",
								"getBankReconDetails",
								"inside cheques rs ");
						chequeDetails = new ChequeDetails();
						chequeDetails.setChequeDate(rsCheques.getDate(1));
						chequeDetails.setChequeNumber(rsCheques.getInt(2)+"");
						chequeDetails.setPresentedDate(rsCheques.getDate(3));
						chequeDetails.setChequeAmount(rsCheques.getDouble(4));
						chequeDetails.setChequeRemarks(rsCheques.getString(5));
						
						chequesArr.add(chequeDetails);
					}
					
					bankReconcilation.setChequeDetails(chequesArr);
					Log.log(
							Log.DEBUG,
							"IFDAO",
							"getBankReconDetails",
							"bank recon id "+ bankReconcilation.getReconId());					
					
					if (bankReconcilation.getReconId()==0)
					{
						bankReconcilation.setClosingBalanceIDBI(callableStmt.getDouble(5));
						bankReconcilation.setChequeIssuedAmount(callableStmt.getDouble(6));
						Log.log(
								Log.DEBUG,
								"IFDAO",
								"getBankReconDetails",
								"closing bal "+ bankReconcilation.getClosingBalanceIDBI());
						Log.log(
								Log.DEBUG,
								"IFDAO",
								"getBankReconDetails",
								"chq amount "+ bankReconcilation.getChequeIssuedAmount());
					}

					rsDetails.close();
					rsDetails=null;
					callableStmt.close();
					callableStmt = null;

				} catch (SQLException e) {
					Log.log(Log.ERROR, "IFDAO", "getBankReconDetails", e.getMessage());
					Log.logException(e);
					throw new DatabaseException("Unable to get Bank Reconcilation details");
				} finally {
					DBConnection.freeConnection(conn);
				}
				Log.log(Log.INFO, "IFDAO", "getBankReconDetails", "Exited");

				return bankReconcilation;
			}
			
			
		public ArrayList getMaturedVoucherDetails(Date fromDate, Date toDate)throws DatabaseException
		{
			Connection conn = null;
			CallableStatement callableStmt = null;
			int status;
			String errorCode="";
			InvestmentDetails invDetail = new InvestmentDetails(); 
			
			ArrayList investmentDetails = new ArrayList();
			try {			
				conn = DBConnection.getConnection(false);
				callableStmt = conn.prepareCall("{?=call packInvestmentVoucher.funcGetMaturedInv(?,?,?,?)}");
				
				Log.log(Log.INFO, "IFDAO", "getMaturedVoucherDetails", "new java.sql.Date(toDate.getTime() :" + new java.sql.Date(toDate.getTime()));
				Log.log(Log.INFO, "IFDAO", "getMaturedVoucherDetails", "new java.sql.Date(fromDate.getTime() :" + new java.sql.Date(fromDate.getTime()));
				
				callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
				callableStmt.setDate(2,new java.sql.Date(fromDate.getTime()));
				callableStmt.setDate(3,new java.sql.Date(toDate.getTime()));
				
				callableStmt.registerOutParameter(4, Constants.CURSOR);
				callableStmt.registerOutParameter(5, java.sql.Types.VARCHAR);
				
				callableStmt.execute();
				status = callableStmt.getInt(1);
				errorCode = callableStmt.getString(5);
				Log.log(
					Log.DEBUG,
					"IFDAO",
					"getMaturedVoucherDetails",
					"Error code and error " + status + " " + errorCode);
				if (status == Constants.FUNCTION_FAILURE) {
					conn.rollback();
					callableStmt.close();
					callableStmt = null;
					Log.log(Log.ERROR, "IFDAO", "updateFundTransfers", errorCode);
					throw new DatabaseException(errorCode);
				}
				else{
					
					ResultSet invdetails = (ResultSet)callableStmt.getObject(4);
					while(invdetails.next())
					{
						Log.log(Log.INFO, "IFDAO", "getFundTransfersForDate", "entering the result set");
						invDetail = new InvestmentDetails();
						invDetail.setInstruments(invdetails.getInt(1));
						invDetail.setInvestmentNumber(invdetails.getString(3));
						invDetail.setInvestmentAmount(invdetails.getDouble(15));
						invDetail.setMaturityAmount(invdetails.getDouble(23));
						invDetail.setInvestedDateNav(invdetails.getDouble(42));
						invDetail.setInvestee(invdetails.getString(43));	
						
						investmentDetails.add(invDetail);					
						
					}
					invdetails.close();
					invdetails=null;					
				}
				callableStmt.close();
				callableStmt = null;
				
				conn.commit();
			
			}catch(SQLException sqlException){		
				
				Log.logException(sqlException);
		
				try
				{
					conn.rollback();
				}
				catch (SQLException ignore) {
					Log.log(Log.INFO,"IFDAO","getMaturedVoucherDetails",ignore.getMessage());			
				}
		
				throw new DatabaseException(sqlException.getMessage());
		
			}
			finally
			{
//				Free the connection here.
				DBConnection.freeConnection(conn);
			}
			return investmentDetails;
			

		}
		
		public void updateVoucherId(String voucherId,int invNumber)throws DatabaseException
		{
			
			Log.log(Log.INFO,"IFDAO","updateVoucherId","entered");
			
			Connection conn = null;
			CallableStatement callableStmt = null;
			int status;
			String errorCode="";
			
			try {			
				conn = DBConnection.getConnection(false);
				callableStmt = conn.prepareCall("{?=call packInvestmentVoucher.funcUpdVouDetail(?,?,?)}");
				
				callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
				
				callableStmt.setString(2,voucherId);
				callableStmt.setInt(3,invNumber);
				callableStmt.registerOutParameter(4, java.sql.Types.VARCHAR);
				
				callableStmt.execute();
				status = callableStmt.getInt(1);
				errorCode = callableStmt.getString(4);
				Log.log(
					Log.DEBUG,
					"IFDAO",
					"updateVoucherId",
					"Error code and error " + status + " " + errorCode);
				if (status == Constants.FUNCTION_FAILURE) {
					conn.rollback();
					callableStmt.close();
					callableStmt = null;
					Log.log(Log.ERROR, "IFDAO", "updateVoucherId", errorCode);
					throw new DatabaseException(errorCode);
				}
				callableStmt.close();
				callableStmt = null;
				
				conn.commit();
			
			
			} catch (SQLException e) {
				
				Log.log(Log.ERROR, "IFDAO", "updateVoucherId", e.getMessage());
				Log.logException(e);
				throw new DatabaseException("Unable to insert voucher details");
			}
			catch (DatabaseException db)
			{
				try
				{
					conn.rollback();
				}
				catch(SQLException sql)
				{
					Log.log(Log.ERROR, "IFDAO", "updateVoucherId", "error rolling back");
				}
				throw db;
			} finally {
				DBConnection.freeConnection(conn);
			}
			
			Log.log(Log.INFO,"IFDAO","updateVoucherId","exited");
		}

		public void updateBankRecon(Date date, BankReconcilation bankReconcilation, String userId) throws DatabaseException
		{
			Log.log(Log.INFO, "IFDAO", "updateBankRecon", "Entered");

			Connection conn = null;
			CallableStatement callableStmt = null;
			int status = -1;
			String errorCode = null;
			
			try {			
				conn = DBConnection.getConnection();
				callableStmt = conn.prepareCall("{?=call packBankRecon.funcInsUpdBankRecon(?,?,?,?,?,?,?,?)}");
	
				callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
				callableStmt.setDouble(2, bankReconcilation.getCgtsiBalance());
				callableStmt.setDate(3, new java.sql.Date(date.getTime()));
				callableStmt.setDouble(4, bankReconcilation.getChequeIssuedAmount());
				callableStmt.setDouble(5, bankReconcilation.getDirectCredit());
				callableStmt.setDouble(6, bankReconcilation.getDirectDebit());
				callableStmt.setDouble(7, bankReconcilation.getClosingBalanceIDBI());
				callableStmt.setString(8, userId);
				callableStmt.registerOutParameter(9, java.sql.Types.VARCHAR);
				
				callableStmt.execute();
				status = callableStmt.getInt(1);
				errorCode = callableStmt.getString(9);
				Log.log(
					Log.DEBUG,
					"IFDAO",
					"updateBankRecon",
					"Error code and error " + status + " " + errorCode);
				if (status == Constants.FUNCTION_FAILURE) {
					conn.rollback();
					callableStmt.close();
					callableStmt = null;
					Log.log(Log.ERROR, "IFDAO", "updateBankRecon", errorCode);
					throw new DatabaseException(errorCode);
				}
				callableStmt.close();
				callableStmt = null;
				
				
			} catch (SQLException e) {
				
				Log.log(Log.ERROR, "IFDAO", "updateBankRecon", e.getMessage());
				Log.logException(e);
				throw new DatabaseException("Unable to insert bank reconciliation details");
			}finally {
				DBConnection.freeConnection(conn);
			}
			
			Log.log(Log.INFO, "IFDAO", "updateBankRecon", "Exited");

		}		
		
		/**
		 * inserting cheque details into the database
		 */
		
		public void insertChqLeavesDetails(ChequeLeavesDetails chqLeavesdetails,User user)throws DatabaseException
		{
			Connection conn = null;
			CallableStatement callableStmt = null;
			int status = -1;
			String errorCode = null;
			
			Date sysDate = new java.util.Date();
			
			try {			
				conn = DBConnection.getConnection();
				callableStmt = conn.prepareCall("{?=call packChqLeavesMaster.funcInsChqMaster(?,?,?,?,?,?,?,?,?)}");
				
				Log.log(Log.INFO, "IFDAO", "insertChqLeavesDetails", "chqLeavesdetails.getChqBankName() :" + chqLeavesdetails.getChqBankName());
				Log.log(Log.INFO, "IFDAO", "insertChqLeavesDetails", "chqLeavesdetails.getChqBranchName() :" + chqLeavesdetails.getChqBranchName());
				Log.log(Log.INFO, "IFDAO", "insertChqLeavesDetails", "chqLeavesdetails.getBnkAccountNumber() :" + chqLeavesdetails.getBnkAccountNumber());
				 
				callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
				callableStmt.setString(2, chqLeavesdetails.getChqBankName());
				callableStmt.setString(3, chqLeavesdetails.getChqBranchName());
				callableStmt.setString(4,chqLeavesdetails.getBnkAccountNumber());
				callableStmt.setInt(5, chqLeavesdetails.getChqStartNo());
				callableStmt.setInt(6, chqLeavesdetails.getChqEndingNo());
				callableStmt.setInt(7, (chqLeavesdetails.getChqEndingNo() - chqLeavesdetails.getChqStartNo()) + 1);
				callableStmt.setDate(8, new java.sql.Date(sysDate.getTime()));
				callableStmt.setString(9, user.getUserId());
				callableStmt.registerOutParameter(10, java.sql.Types.VARCHAR);
				
				callableStmt.execute();
				status = callableStmt.getInt(1);
				errorCode = callableStmt.getString(10);
				Log.log(
					Log.DEBUG,
					"IFDAO",
					"insertChqLeavesDetails",
					"Error code and error " + status + " " + errorCode);
				if (status == Constants.FUNCTION_FAILURE) {
					conn.rollback();
					callableStmt.close();
					callableStmt = null;
					Log.log(Log.ERROR, "IFDAO", "insertChqLeavesDetails", errorCode);
					throw new DatabaseException(errorCode);
				}
				callableStmt.close();
				callableStmt = null;
				
				conn.commit();
				
			} catch (SQLException e) {
				
				Log.log(Log.ERROR, "IFDAO", "insertChqLeavesDetails", e.getMessage());
				Log.logException(e);
				throw new DatabaseException("Unable to insert Cheque Leaves Details");
			}finally {
				DBConnection.freeConnection(conn);
			}
			
			Log.log(Log.INFO, "IFDAO", "insertChqLeavesDetails", "Exited");
				
			
		}

		public ArrayList getBankChqLeavesDetails(String bankName,String branchName,String bnkAcctNo)throws DatabaseException
		{
			Connection conn = null;
			CallableStatement callableStmt = null;
			int status = -1;
			String errorCode = null;
			
			Date sysDate = new java.util.Date();
			ChequeLeavesDetails chequeLeavesDetails = new ChequeLeavesDetails();
			
			ArrayList bnkChqDetails = new ArrayList();
			
			try {			
				conn = DBConnection.getConnection();
				callableStmt = conn.prepareCall("{?=call packChqLeavesMaster.funcGetChqMaster(?,?,?,?,?)}");
				
				callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
				callableStmt.setString(2, bankName);
				callableStmt.setString(3, branchName);
				callableStmt.setString(4, bnkAcctNo);
				callableStmt.registerOutParameter(5, Constants.CURSOR);
				callableStmt.registerOutParameter(6, java.sql.Types.VARCHAR);
				
				callableStmt.execute();
				status = callableStmt.getInt(1);
				errorCode = callableStmt.getString(6);
				Log.log(
					Log.DEBUG,
					"IFDAO",
					"getBankChqLeavesDetails",
					"Error code and error " + status + " " + errorCode);
				if (status == Constants.FUNCTION_FAILURE) {
					conn.rollback();
					callableStmt.close();
					callableStmt = null;
					Log.log(Log.ERROR, "IFDAO", "getBankChqLeavesDetails", errorCode);
					throw new DatabaseException(errorCode);
				}
				else{
					
					ResultSet bankChqDetails = (ResultSet)callableStmt.getObject(5);
					while(bankChqDetails.next())
					{
						chequeLeavesDetails = new ChequeLeavesDetails();
						chequeLeavesDetails.setChqId(bankChqDetails.getInt(1));
						chequeLeavesDetails.setChqBankId(bankChqDetails.getInt(2));
						chequeLeavesDetails.setChqStartNo(bankChqDetails.getInt(3));
						chequeLeavesDetails.setChqEndingNo(bankChqDetails.getInt(4));
						
						bnkChqDetails.add(chequeLeavesDetails);
					}
					bankChqDetails.close();
					bankChqDetails=null;
				}
				callableStmt.close();
				callableStmt = null;
				
				conn.commit();
				
			} catch (SQLException e) {
				
				Log.log(Log.ERROR, "IFDAO", "getBankChqLeavesDetails", e.getMessage());
				Log.logException(e);
				throw new DatabaseException("Unable to retrieve Cheque Leaves Details");
			}finally {
				DBConnection.freeConnection(conn);
			}
			
			Log.log(Log.INFO, "IFDAO", "insertChqLeavesDetails", "Exited");
				
			return bnkChqDetails;			
		}
		
		
		/**
		 * inserting cheque details into the database
		 */
		
		public void updateChqLeavesDetails(ChequeLeavesDetails chqLeavesdetails,User user)throws DatabaseException
		{
			Connection conn = null;
			CallableStatement callableStmt = null;
			int status = -1;
			String errorCode = null;
			
			Date sysDate = new java.util.Date();
			
			try {			
				conn = DBConnection.getConnection();
				callableStmt = conn.prepareCall("{?=call packChqLeavesMaster.funcUpdChqMaster(?,?,?,?,?,?,?,?)}");
				
				callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
				callableStmt.setInt(2, chqLeavesdetails.getChqId());
				callableStmt.setInt(3, chqLeavesdetails.getChqBankId());
				callableStmt.setInt(4, chqLeavesdetails.getChqStartNo());
				callableStmt.setInt(5, chqLeavesdetails.getChqEndingNo());
				callableStmt.setInt(6, (chqLeavesdetails.getChqEndingNo() - chqLeavesdetails.getChqStartNo()) + 1);
				callableStmt.setDate(7, new java.sql.Date(sysDate.getTime()));
				callableStmt.setString(8, user.getUserId());
				callableStmt.registerOutParameter(9, java.sql.Types.VARCHAR);
				
				callableStmt.execute();
				status = callableStmt.getInt(1);
				errorCode = callableStmt.getString(9);
				Log.log(
					Log.DEBUG,
					"IFDAO",
					"updateChqLeavesDetails",
					"Error code and error " + status + " " + errorCode);
				if (status == Constants.FUNCTION_FAILURE) {
					conn.rollback();
					callableStmt.close();
					callableStmt = null;
					Log.log(Log.ERROR, "IFDAO", "updateChqLeavesDetails", errorCode);
					throw new DatabaseException(errorCode);
				}
				callableStmt.close();
				callableStmt = null;
				
				conn.commit();
				
			} catch (SQLException e) {
				
				Log.log(Log.ERROR, "IFDAO", "updateChqLeavesDetails", e.getMessage());
				Log.logException(e);
				throw new DatabaseException("Unable to update Cheque Leaves Details");
			}finally {
				DBConnection.freeConnection(conn);
			}
			
			Log.log(Log.INFO, "IFDAO", "updateChqLeavesDetails", "Exited");
				
			
		}
		
		
		public ArrayList getUnUtilizedChqLeaves(String bankName,String branchName,String bnkAcctNo)throws DatabaseException
		{
			Connection conn = null;
			CallableStatement callableStmt = null;
			int status = -1;
			String errorCode = null;
			
			Date sysDate = new java.util.Date();
			ChequeDetails chequeDetails = new ChequeDetails();
			
			ArrayList chqDetails = new ArrayList();
			
			try {			
				conn = DBConnection.getConnection(false);
				
				callableStmt = conn.prepareCall("{?=call packChqLeavesMaster.funcGetUnUtilized(?,?,?,?,?)}");
				
				callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
				callableStmt.setString(2, bankName);
				callableStmt.setString(3, branchName);
				callableStmt.setString(4, bnkAcctNo);
				callableStmt.registerOutParameter(5, Constants.CURSOR);
				callableStmt.registerOutParameter(6, java.sql.Types.VARCHAR);
				
				callableStmt.execute();
				status = callableStmt.getInt(1);
				errorCode = callableStmt.getString(6);
				Log.log(
					Log.DEBUG,
					"IFDAO",
					"getUnUtilizedChqLeaves",
					"Error code and error " + status + " " + errorCode);
				if (status == Constants.FUNCTION_FAILURE) {
					
					callableStmt.close();
					callableStmt = null;
					conn.rollback();
					Log.log(Log.ERROR, "IFDAO", "getUnUtilizedChqLeaves", errorCode);
					throw new DatabaseException(errorCode);
				}
				else{
					
					ResultSet chqDetailsResult = (ResultSet)callableStmt.getObject(5);
					while(chqDetailsResult.next())
					{
						chequeDetails = new ChequeDetails();
						chequeDetails.setChequeId(chqDetailsResult.getString(1));
						chequeDetails.setChqNumber(chqDetailsResult.getInt(2));	
						
						Log.log(Log.INFO, "IFDAO", "getUnUtilizedChqLeaves", "chqDetailsResult.getInt(2) :" + chqDetailsResult.getInt(2));					
						
						chqDetails.add(chequeDetails);
					}
					chqDetailsResult.close();
					chqDetailsResult = null;
				}
				callableStmt.close();
				callableStmt = null;
				
				
				CallableStatement callableStmt1 = conn.prepareCall("{?=call packChqLeavesMaster.funcUnUtilizedChq(?,?,?,?,?)}");
				
				callableStmt1.registerOutParameter(1, java.sql.Types.INTEGER);
				
				Log.log(Log.INFO, "IFDAO", "getUnUtilizedChqLeaves", "bankName :" + bankName);
				Log.log(Log.INFO, "IFDAO", "getUnUtilizedChqLeaves", "branchName:" +branchName);
				Log.log(Log.INFO, "IFDAO", "getUnUtilizedChqLeaves", "bnkAcctNo:" + bnkAcctNo);
				
				callableStmt1.setString(2, bankName);
				callableStmt1.setString(3, branchName);
				callableStmt1.setString(4, bnkAcctNo);
				callableStmt1.registerOutParameter(5, Constants.CURSOR);
				callableStmt1.registerOutParameter(6, java.sql.Types.VARCHAR);
				
				callableStmt1.execute();
				status = callableStmt1.getInt(1);
				errorCode = callableStmt1.getString(6);
				
				
				Log.log(
					Log.INFO,
					"IFDAO",
					"getUnUtilizedChqLeaves 1",
					"Error code and error " + status + " " + errorCode);
				if (status == Constants.FUNCTION_FAILURE) {
					
					callableStmt1.close();
					callableStmt1 = null;
					conn.rollback();
					Log.log(Log.ERROR, "IFDAO", "getUnUtilizedChqLeaves", errorCode);
					throw new DatabaseException(errorCode);
				}
				else{
					
					ResultSet chqDetailsResultSet = (ResultSet)callableStmt1.getObject(5);
					while(chqDetailsResultSet.next())
					{
						Log.log(Log.INFO, "IFDAO", "getUnUtilizedChqLeaves", "chqDetailsResultSet.getInt(2) :" );
						chequeDetails = new ChequeDetails();
						chequeDetails.setChequeId(chqDetailsResultSet.getString(1));
						chequeDetails.setChqNumber(chqDetailsResultSet.getInt(2));	
						
						Log.log(Log.INFO, "IFDAO", "getUnUtilizedChqLeaves", "chqDetailsResultSet.getInt(2) :" + chqDetailsResultSet.getInt(2));					
						
						chqDetails.add(chequeDetails);
					}
					chqDetailsResultSet.close();
					chqDetailsResultSet = null;
					
				}
				
				callableStmt1.close();
				callableStmt1 = null;
				
				conn.commit();
			} catch (SQLException e) {
				
				Log.log(Log.ERROR, "IFDAO", "getBankChqLeavesDetails", e.getMessage());
				Log.logException(e);
				throw new DatabaseException(e.getMessage());
			}finally {
				DBConnection.freeConnection(conn);
			}
			
			Log.log(Log.INFO, "IFDAO", "insertChqLeavesDetails", "Exited");
				
			return chqDetails;			
		}


		public void saveCancelledChqDetails(ChequeDetails chequeDetails,User user)throws DatabaseException
		{
			Log.log(Log.INFO, "IFDAO", "saveCancelledChqDetails", "Entered");
			Connection conn = null;
			CallableStatement callableStmt = null;
			int status = -1;
			String errorCode = null;
			
			Date sysDate = new java.util.Date();
			
			try {			
				conn = DBConnection.getConnection();
				callableStmt = conn.prepareCall("{?=call packChqLeavesMaster.funcUpdChqDtl(?,?,?,?,?,?,?,?,?,?,?,?)}");
				
				Log.log(Log.INFO, "IFDAO", "saveCancelledChqDetails", "cheque numbe" + chequeDetails.getChqNumber());
				
				callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
				callableStmt.setString(2, user.getUserId());
				callableStmt.setDouble(3, 0);
				callableStmt.setDate(4, new java.sql.Date(new java.util.Date().getTime()));
				callableStmt.setInt(5, chequeDetails.getChqNumber());
				callableStmt.setString(6, "");
				callableStmt.setString(7, chequeDetails.getBankName());
				Log.log(Log.INFO, "IFDAO", "saveCancelledChqDetails", "bank name" + chequeDetails.getBankName());
				callableStmt.setString(8, chequeDetails.getBranchName());
				Log.log(Log.INFO, "IFDAO", "saveCancelledChqDetails", "branch name" + chequeDetails.getBranchName());
				callableStmt.setString(9, "Cheque No :" + chequeDetails.getChqNumber() + "has been cancelled");
				callableStmt.setString(10, null);
				callableStmt.setString(11, "C");
				callableStmt.setInt(12, chequeDetails.getChqNumber());
				callableStmt.registerOutParameter(13, java.sql.Types.VARCHAR);
				
				callableStmt.execute();
				status = callableStmt.getInt(1);
				errorCode = callableStmt.getString(13);
				Log.log(
					Log.DEBUG,
					"IFDAO",
					"saveCancelledChqDetails",
					"Error code and error " + status + " " + errorCode);
				if (status == Constants.FUNCTION_FAILURE) {
					conn.rollback();
					callableStmt.close();
					callableStmt = null;
					Log.log(Log.ERROR, "IFDAO", "saveCancelledChqDetails", errorCode);
					throw new DatabaseException(errorCode);
				}
				
				callableStmt.close();
				callableStmt = null;
				
				conn.commit();
				
			} catch (SQLException e) {
				
				Log.log(Log.ERROR, "IFDAO", "saveCancelledChqDetails", e.getMessage());
				Log.logException(e);
				throw new DatabaseException("Unable to cancel Cheque Leaves Details");
			}finally {
				DBConnection.freeConnection(conn);
			}
			
			Log.log(Log.INFO, "IFDAO", "saveCancelledChqDetails", "Exited");
				
			
		}
		
		
		
		public SettlementDetail getClmDtlsForProjection(String borrowerId) throws DatabaseException
		{
			Log.log(Log.INFO,"CPDAO","getClmDtlsForProjection()","Entered!");
			CallableStatement callableStmt = null;
			Connection conn = null;
			ResultSet rs = null;
			
			SettlementDetail settlementDetails = null; 

			String clmRefNumber = null;
			String clmStatus = null;
			String cgclan = null;
			String installmentFlag = null;
			String whetherFinalInstallment;
			String firstSettlementDate;
			String secondSettlementDate;
			
			String clmFirstSettlDate;
			String clmSecondStlDate;

			int status = -1;
			String errorCode = null;
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

			try
			{
			   conn = DBConnection.getConnection();
			   callableStmt = conn.prepareCall("{? = call packGetClmDtlsForProjection.funcGetClmDtlsForProjection(?,?,?)}");
			   callableStmt.registerOutParameter(1,java.sql.Types.INTEGER);
			   callableStmt.setString(2,borrowerId);
			   callableStmt.registerOutParameter(3,Constants.CURSOR);
			   callableStmt.registerOutParameter(4,java.sql.Types.VARCHAR);

			   // executing the callable statement
			   callableStmt.execute();
			   status =  callableStmt.getInt(1);
			   errorCode = callableStmt.getString(4);

			   if(status == Constants.FUNCTION_FAILURE)
			   {
					  Log.log(Log.ERROR,"IFDAO","getClmDtlsForProjection()","SP returns a 1 in getting Claim Ref Number and Installment Flag.Error code is :" + errorCode);
					  callableStmt.close();
					  throw new DatabaseException(errorCode);
			   }
			   else if(status == Constants.FUNCTION_SUCCESS)
			   {
				   // Retrieving the resultset
				   rs = (ResultSet)callableStmt.getObject(3);
				   
				settlementDetails  = new SettlementDetail();
				settlementDetails.setTierOneSettlementDt(null); 

				   // retrieving the values from the resulset
				   while(rs.next())
				   {
					Log.log(Log.INFO,"IFDAO","getClmDtlsForProjection()","entering into result set:" );
						 clmRefNumber = rs.getString(2);
						 clmStatus = rs.getString(3);
						 cgclan = rs.getString(4);

						 installmentFlag = rs.getString(5);
						 firstSettlementDate = rs.getString(6);
						 
						secondSettlementDate = rs.getString(7);
					
						Log.log(Log.INFO,"IFDAO","getClmDtlsForProjection()","firstSettlementDate :" + firstSettlementDate);
					Log.log(Log.INFO,"IFDAO","getClmDtlsForProjection()","installmentFlag :" + installmentFlag);
						 whetherFinalInstallment = rs.getString(8);

						 if(installmentFlag.equals(ClaimConstants.FIRST_INSTALLMENT))
						 {
							Log.log(Log.INFO,"IFDAO","getClmDtlsForProjection()","tier 1 :" + settlementDetails.getTierOneSettlementDt());
							Log.log(Log.INFO,"IFDAO","getClmDtlsForProjection()","tier 1 from rs :" + firstSettlementDate);
							if(settlementDetails.getTierOneSettlementDt()==null && (firstSettlementDate==null || firstSettlementDate.equals("0")))
							{
								Log.log(Log.INFO,"IFDAO","getClmDtlsForProjection()","tier 1 null:");
								settlementDetails.setTierOneSettlementDt(null);
							}
							else if (!(firstSettlementDate==null || firstSettlementDate.equals("0"))){
						 		
								Log.log(Log.INFO,"IFDAO","getClmDtlsForProjection()","tier 1 not null:");
								Date firstSettlDate = sdf.parse(firstSettlementDate,new ParsePosition(0)); 
								settlementDetails.setTierOneSettlementDt(firstSettlDate);
								
								Log.log(Log.INFO,"IFDAO","getClmDtlsForProjection()","tier 1 after setting:" + settlementDetails.getTierOneSettlementDt());
							}
								
						 }						
						else if(installmentFlag.equals(ClaimConstants.SECOND_INSTALLMENT))
						{
							if(settlementDetails.getTierTwoSettlementDt()==null && (secondSettlementDate==null || secondSettlementDate.equals("0")))
							{
								Log.log(Log.INFO,"IFDAO","getClmDtlsForProjection()","tier 1 null:");
								settlementDetails.setTierTwoSettlementDt(null);
							}
							else if (!(secondSettlementDate==null || secondSettlementDate.equals("0"))){
						 		
								Log.log(Log.INFO,"IFDAO","getClmDtlsForProjection()","tier 1 not null:");
								Date secondSettlDate = sdf.parse(secondSettlementDate,new ParsePosition(0)); 
								settlementDetails.setTierTwoSettlementDt(secondSettlDate);
								
								Log.log(Log.INFO,"IFDAO","getClmDtlsForProjection()","tier 1 after setting:" + settlementDetails.getTierOneSettlementDt());
							}
							
						}
						
						settlementDetails.setFinalSettlementFlag(whetherFinalInstallment);
						
					System.out.println(settlementDetails.getTierOneSettlementDt());
				   }
				   rs.close();
				   rs = null;
			   }
			   // Closing the Callable Statement
			   callableStmt.close();
			}
			catch(SQLException sqlexception)
			{
				Log.log(Log.ERROR,"IFDAO","getClmDtlsForProjection()","Error retrieving Claim Ref Number and the corresponding flag from the database.");
				throw new DatabaseException(sqlexception.getMessage());
			}
			finally{
				DBConnection.freeConnection(conn);
			}
			Log.log(Log.INFO,"IFDAO","getClmDtlsForProjection()","Exited!");
			return settlementDetails;
		}
		
		public Map showInflowOutflowReport(Date date, Map invMatDetails, String userId) throws DatabaseException
		{
				Log.log(Log.INFO, "IFDAO", "showInflowOutflowReport", "entered");

				CallableStatement pStmt = null;
				Connection conn = null;
				ResultSet rsInOut = null;

				InvestmentMaturityDetails imDetail = new InvestmentMaturityDetails();
				InflowOutflowReport ioReport= new InflowOutflowReport();
				Map reportDetails = new TreeMap();
				Map mainDetails = new TreeMap();
				ArrayList fundTransfers = new ArrayList();
				DecimalFormat df = new DecimalFormat("###############.##");
				ArrayList provisionDetails = new ArrayList();
				ArrayList maturityDetails = new ArrayList();
				double matAmtTotal = 0;
				
				Set imSet = invMatDetails.keySet();
				Iterator imIterator = imSet.iterator();
				while (imIterator.hasNext())
				{
					imDetail = (InvestmentMaturityDetails)invMatDetails.get(imIterator.next());
					if (imDetail.getInvFlag().equalsIgnoreCase("Y"))
					{
						maturityDetails.add(imDetail);
						if (!imDetail.getMaturityAmt().trim().equals(""))
						{
							matAmtTotal += Double.parseDouble(imDetail.getMaturityAmt());
						}
					}
				}

				try
				{
					conn = DBConnection.getConnection(false);

					Log.log(Log.DEBUG, "IFDAO", "showInflowOutflowReport", "saving maturity details");
					
					Set invMatSet = invMatDetails.keySet();
					Iterator invMatIterator = invMatSet.iterator();
					
					while (invMatIterator.hasNext())
					{
						imDetail = new InvestmentMaturityDetails();
						String key = (String)invMatIterator.next();
						Log.log(Log.DEBUG, "IFDAO", "showInflowOutflowReport", "saving for key " + key);
						imDetail = (InvestmentMaturityDetails) invMatDetails.get(key);
						if (imDetail.getPliId()==0)
						{
							pStmt=conn.prepareCall("{? = call packPlanInvestment.funcInsPLI(?,?,?,?,?,?,?)}");
							pStmt.registerOutParameter(1, java.sql.Types.INTEGER);
							pStmt.setString(2, imDetail.getBuySellId());
							if (imDetail.getMaturityAmt().trim().equals(""))
							{
								pStmt.setDouble(3, 0);
							}
							else
							{
								pStmt.setDouble(3, Double.parseDouble(imDetail.getMaturityAmt()));
							}
							pStmt.setString(4, imDetail.getInvFlag());
							pStmt.setDate(5, new java.sql.Date(date.getTime()));
							pStmt.setString(6, imDetail.getOtherDesc());
							pStmt.setString(7, userId);
							pStmt.registerOutParameter(8, java.sql.Types.VARCHAR);
					
							pStmt.executeQuery();
					
							int status = pStmt.getInt(1);
							String error = pStmt.getString(8);
					
							Log.log(Log.DEBUG, "IFDAO", "showInflowOutflowReport", "Error code and error" +status +", " +error);
					
							if (status==Constants.FUNCTION_FAILURE)
							{
								conn.rollback();
								pStmt.close();
								pStmt=null;
								throw new DatabaseException(error);
							}
							pStmt.close();
							pStmt=null;
						}
						else
						{
							pStmt=conn.prepareCall("{? = call packPlanInvestment.funcUpdPLI(?,?,?,?,?,?,?)}");
							pStmt.registerOutParameter(1, java.sql.Types.INTEGER);
							pStmt.setInt(2, imDetail.getPliId());
							if (imDetail.getMaturityAmt().trim().equals(""))
							{
								pStmt.setDouble(3, 0);
							}
							else
							{
								pStmt.setDouble(3, Double.parseDouble(imDetail.getMaturityAmt()));
							}
							pStmt.setString(4, imDetail.getInvFlag());
							pStmt.setDate(5, new java.sql.Date(date.getTime()));
							pStmt.setString(6, imDetail.getOtherDesc());
							pStmt.setString(7, userId);
							pStmt.registerOutParameter(8, java.sql.Types.VARCHAR);
					
							pStmt.executeQuery();
					
							int status = pStmt.getInt(1);
							String error = pStmt.getString(8);
					
							Log.log(Log.DEBUG, "IFDAO", "showInflowOutflowReport", "Error code and error" +status +", " +error);
					
							if (status==Constants.FUNCTION_FAILURE)
							{
								conn.rollback();
								pStmt.close();
								pStmt=null;
								throw new DatabaseException(error);
							}
							pStmt.close();
							pStmt=null;
						}
					}
					conn.commit();
					
					int counter=0;
					
					pStmt=conn.prepareCall("{? = call packPLIIDBI.funcIDBIReport(?,?,?,?,?,?,?,?,?,?)}");
					pStmt.registerOutParameter(1, java.sql.Types.INTEGER);
					pStmt.setDate(2, new java.sql.Date(date.getTime()));
					pStmt.registerOutParameter(3, java.sql.Types.VARCHAR);
					pStmt.registerOutParameter(4, java.sql.Types.DOUBLE);
					pStmt.registerOutParameter(5, java.sql.Types.DOUBLE);
					pStmt.registerOutParameter(6, java.sql.Types.DOUBLE);
					pStmt.registerOutParameter(7, java.sql.Types.DOUBLE);
					pStmt.registerOutParameter(8, java.sql.Types.DOUBLE);
					pStmt.registerOutParameter(9, java.sql.Types.DOUBLE);
					pStmt.registerOutParameter(10, java.sql.Types.DOUBLE);
					pStmt.registerOutParameter(11, java.sql.Types.VARCHAR);
					
					pStmt.executeQuery();
					
					int status = pStmt.getInt(1);
					String error = pStmt.getString(11);
					
					Log.log(Log.DEBUG, "IFDAO", "showInflowOutflowReport", "Error code and error" +status +", " +error);
					
					if (status==Constants.FUNCTION_FAILURE)
					{
						pStmt.close();
						pStmt=null;
						throw new DatabaseException(error);
					}
					
					ioReport= new InflowOutflowReport();
					ioReport.setBankName(pStmt.getString(3));
					double stmtBal=pStmt.getDouble(4);
					double availBal=pStmt.getDouble(5);
					double ftInflow=pStmt.getDouble(6);
					double miscReceiptsInflow=pStmt.getDouble(7);
					double chqAmt=pStmt.getDouble(8);
					double minBal=pStmt.getDouble(9);
					double provisionAmt=pStmt.getDouble(10);
					
					ioReport.setStmtBalance(df.format(stmtBal));
					ioReport.setAvailBalance(df.format(availBal));
					ioReport.setMaturityInflow(df.format(matAmtTotal));			//maturity
					ioReport.setFundTransferInflow(df.format(ftInflow));
					ioReport.setMiscReceiptsInflow(df.format(miscReceiptsInflow));
					ioReport.setChqissuedAmt(df.format(chqAmt));
					ioReport.setMinBalance(df.format(minBal));
					ioReport.setProvisionFundsAmt(df.format(provisionAmt));
					
					double netBal=availBal + matAmtTotal + ftInflow + miscReceiptsInflow;
					
					ioReport.setNetBalance(df.format(netBal));
					ioReport.setSurplusShort(df.format(netBal - (chqAmt + minBal + provisionAmt)));
					
					mainDetails.put("key-"+counter, ioReport);
					
					pStmt.close();
					pStmt=null;
					
					pStmt=conn.prepareCall("{? = call packPLIIDBI.funcOtherBankReport(?,?,?)}");
					pStmt.registerOutParameter(1, java.sql.Types.INTEGER);
					Log.log(Log.DEBUG, "IFDAO", "showInflowOutflowReport", "date " +new java.sql.Date(date.getTime()));
					pStmt.setDate(2, new java.sql.Date(date.getTime()));
					pStmt.registerOutParameter(3, Constants.CURSOR);
					pStmt.registerOutParameter(4, java.sql.Types.VARCHAR);
					
					pStmt.executeQuery();
					
					status = pStmt.getInt(1);
					error = pStmt.getString(4);
					
					Log.log(Log.DEBUG, "IFDAO", "showInflowOutflowReport", "Error code and error" +status +", " +error);
					
					if (status==Constants.FUNCTION_FAILURE)
					{
						pStmt.close();
						pStmt=null;
						throw new DatabaseException(error);
					}
					
					rsInOut = (ResultSet) pStmt.getObject(3);
					counter++;
					
					while (rsInOut.next())
					{
						ioReport = new InflowOutflowReport();
						ioReport.setStmtBalance(df.format(rsInOut.getDouble(1)));
						ioReport.setBankName(rsInOut.getString(2));
						ioReport.setCaNotReflectedAmt(df.format(rsInOut.getDouble(3)));
						ioReport.setMinBalance(df.format(rsInOut.getDouble(4)));
						
						mainDetails.put("key-"+counter, ioReport);
						counter++;
					}

					rsInOut.close();
					rsInOut=null;
					pStmt.close();
					pStmt=null;
					
					pStmt=conn.prepareCall("{? = call packPLIIDBI.funcGetPLIRemark(?,?,?,?,?,?)}");
					for (int i=0; i<counter;i++)
					{
						ioReport = (InflowOutflowReport)mainDetails.get("key-"+i);
						String bankName = ioReport.getBankName();
						String branchName = bankName.substring(bankName.indexOf(',')+1, bankName.indexOf('('));
						String accNo = bankName.substring(bankName.indexOf('(')+1, bankName.indexOf(')'));
						bankName = bankName.substring(0,bankName.indexOf(','));
						Log.log(Log.DEBUG, "IFDAO", "showInflowOutflowReport", "bank name " + bankName);
						Log.log(Log.DEBUG, "IFDAO", "showInflowOutflowReport", "branch name " + branchName);
						Log.log(Log.DEBUG, "IFDAO", "showInflowOutflowReport", "account number " + accNo);
						pStmt.registerOutParameter(1, java.sql.Types.INTEGER);
						pStmt.setString(2, bankName);
						pStmt.setString(3, branchName);
						pStmt.setString(4, accNo);
						Log.log(Log.DEBUG, "IFDAO", "showInflowOutflowReport", "date " +new java.sql.Date(date.getTime()));
						pStmt.setDate(5, new java.sql.Date(date.getTime()));
						pStmt.registerOutParameter(6, Constants.CURSOR);
						pStmt.registerOutParameter(7, java.sql.Types.VARCHAR);
					
						pStmt.executeQuery();
					
						status = pStmt.getInt(1);
						error = pStmt.getString(7);
					
						Log.log(Log.DEBUG, "IFDAO", "showInflowOutflowReport", "Error code and error" +status +", " +error);
					
						if (status==Constants.FUNCTION_FAILURE)
						{
							conn.rollback();
							pStmt.close();
							pStmt=null;
							throw new DatabaseException(error);
						}
						
						rsInOut = (ResultSet) pStmt.getObject(6);
						while (rsInOut.next())
						{
							ioReport.setId(rsInOut.getInt(1));
							ioReport.setRemarks(rsInOut.getString(2));
						}
						mainDetails.put("key-"+i, ioReport);
					}

					pStmt.close();
					pStmt=null;
					
					reportDetails.put("DT", mainDetails);
					reportDetails.put("MA", maturityDetails);
					
					pStmt=conn.prepareCall("{? = call packIndividualRep.funcFTRReport(?,?,?)}");
					pStmt.registerOutParameter(1, java.sql.Types.INTEGER);
					Log.log(Log.DEBUG, "IFDAO", "showInflowOutflowReport", "date " +new java.sql.Date(date.getTime()));
					pStmt.setDate(2, new java.sql.Date(date.getTime()));
					pStmt.registerOutParameter(3, Constants.CURSOR);
					pStmt.registerOutParameter(4, java.sql.Types.VARCHAR);
					
					pStmt.executeQuery();
					
					status = pStmt.getInt(1);
					error = pStmt.getString(4);
					
					Log.log(Log.DEBUG, "IFDAO", "showInflowOutflowReport", "Error code and error" +status +", " +error);
					
					if (status==Constants.FUNCTION_FAILURE)
					{
						pStmt.close();
						pStmt=null;
						throw new DatabaseException(error);
					}
					
					rsInOut = (ResultSet) pStmt.getObject(3);
					
					while (rsInOut.next())
					{
						FundTransferDetail ftDetail = new FundTransferDetail();
						ftDetail.setAmtForIDBI(df.format(rsInOut.getDouble(1)));
						ftDetail.setBankName(rsInOut.getString(2));
					
						fundTransfers.add(ftDetail);
					}
					
					reportDetails.put("FT", fundTransfers);

					rsInOut.close();
					rsInOut=null;
					pStmt.close();
					pStmt=null;
					
					pStmt=conn.prepareCall("{? = call packIndividualRep.funcProvisionRep(?,?,?)}");
					pStmt.registerOutParameter(1, java.sql.Types.INTEGER);
					Log.log(Log.DEBUG, "IFDAO", "showInflowOutflowReport", "date " +new java.sql.Date(date.getTime()));
					pStmt.setDate(2, new java.sql.Date(date.getTime()));
					pStmt.registerOutParameter(3, Constants.CURSOR);
					pStmt.registerOutParameter(4, java.sql.Types.VARCHAR);
					
					pStmt.executeQuery();
					
					status = pStmt.getInt(1);
					error = pStmt.getString(4);
					
					Log.log(Log.DEBUG, "IFDAO", "showInflowOutflowReport", "Error code and error" +status +", " +error);
					
					if (status==Constants.FUNCTION_FAILURE)
					{
						pStmt.close();
						pStmt=null;
						throw new DatabaseException(error);
					}
					
					rsInOut = (ResultSet) pStmt.getObject(3);
					
					while (rsInOut.next())
					{
						ActualIOHeadDetail ioDetail = new ActualIOHeadDetail();
						ioDetail.setBudgetAmount(rsInOut.getDouble(1));
						ioDetail.setId(rsInOut.getInt(2));
						ioDetail.setBudgetHead(rsInOut.getString(3));
						if (ioDetail.getBudgetHead()==null || ioDetail.getBudgetHead().equals(""))
						{
							ioDetail.setBudgetHead(rsInOut.getString(4));
						}
						ioDetail.setRemarks(rsInOut.getString(5));
					
						provisionDetails.add(ioDetail);
					}
					
					reportDetails.put("PR", provisionDetails);

					rsInOut.close();
					rsInOut=null;
					pStmt.close();
					pStmt=null;
					
					conn.commit();
				}
				catch(SQLException e)
				{
					Log.log(Log.ERROR, "IFDAO", "showInflowOutflowReport", e.getMessage());
					Log.logException(e);
					try {
					conn.rollback();
					}catch(SQLException sql){}
					throw new DatabaseException("Unable to get inflow outflow details");
				}
				finally
				{
					DBConnection.freeConnection(conn);
				}
				Log.log(Log.INFO, "IFDAO", "showInflowOutflowReport", "Exited");
				return reportDetails;
			}
			
		public void saveInflowOutflowReport(Date date, Map invMainDetails, Map invProvisionDetails, String userId) throws DatabaseException
		{
				Log.log(Log.INFO, "IFDAO", "saveInflowOutflowReport", "entered");

				CallableStatement pStmt = null;
				Connection conn = null;
				ResultSet rsInOut = null;

				InflowOutflowReport ioReport= new InflowOutflowReport();
				ActualIOHeadDetail ioDetail = new ActualIOHeadDetail();

				try
				{
					conn = DBConnection.getConnection(false);

					Log.log(Log.DEBUG, "IFDAO", "saveInflowOutflowReport", "saving main remarks");
					
					Set invMainSet = invMainDetails.keySet();
					Iterator invMainIterator = invMainSet.iterator();
					
					while (invMainIterator.hasNext())
					{
						String key = (String)invMainIterator.next();
						Log.log(Log.DEBUG, "IFDAO", "saveInflowOutflowReport", "saving for key " + key);
						ioReport = (InflowOutflowReport) invMainDetails.get(key);
						if (ioReport.getId()==0)
						{
							pStmt=conn.prepareCall("{? = call packPLIIDBI.funcInsPLIRemark(?,?,?,?,?,?)}");
							pStmt.registerOutParameter(1, java.sql.Types.INTEGER);
							String bankName = ioReport.getBankName();
							String branchName = bankName.substring(bankName.indexOf(',')+1, bankName.indexOf('('));
							String accNo = bankName.substring(bankName.indexOf('(')+1, bankName.indexOf(')'));
							bankName = bankName.substring(0, bankName.indexOf(','));
							Log.log(Log.DEBUG, "IFDAO", "saveInflowOutflowReport", "bankName " + bankName);
							Log.log(Log.DEBUG, "IFDAO", "saveInflowOutflowReport", "branchName " + branchName);
							Log.log(Log.DEBUG, "IFDAO", "saveInflowOutflowReport", "accNo " + accNo);
							pStmt.setString(2, bankName);
							pStmt.setString(3, branchName);
							pStmt.setString(4, accNo);
							pStmt.setDate(5, new java.sql.Date(date.getTime()));
							pStmt.setString(6, ioReport.getRemarks());
							pStmt.registerOutParameter(7, java.sql.Types.VARCHAR);
					
							pStmt.executeQuery();
					
							int status = pStmt.getInt(1);
							String error = pStmt.getString(7);
					
							Log.log(Log.DEBUG, "IFDAO", "saveInflowOutflowReport", "Error code and error" +status +", " +error);
					
							if (status==Constants.FUNCTION_FAILURE)
							{
								conn.rollback();
								pStmt.close();
								pStmt=null;
								throw new DatabaseException(error);
							}
							pStmt.close();
							pStmt=null;
						}
						else
						{
							Log.log(Log.DEBUG, "IFDAO", "saveInflowOutflowReport", "saving for id " + ioReport.getId());
							pStmt=conn.prepareCall("{? = call packPLIIDBI.funcUpdPLIRemark(?,?,?,?)}");
							pStmt.registerOutParameter(1, java.sql.Types.INTEGER);
							pStmt.setInt(2, ioReport.getId());
							pStmt.setDate(3, new java.sql.Date(date.getTime()));
							pStmt.setString(4, ioReport.getRemarks());
							pStmt.registerOutParameter(5, java.sql.Types.VARCHAR);
					
							pStmt.executeQuery();
					
							int status = pStmt.getInt(1);
							String error = pStmt.getString(5);
					
							Log.log(Log.DEBUG, "IFDAO", "saveInflowOutflowReport", "Error code and error" +status +", " +error);
					
							if (status==Constants.FUNCTION_FAILURE)
							{
								conn.rollback();
								pStmt.close();
								pStmt=null;
								throw new DatabaseException(error);
							}
							pStmt.close();
							pStmt=null;
						}
					}

					Log.log(Log.DEBUG, "IFDAO", "saveInflowOutflowReport", "saving provision remark ");
					Set invProvisionSet = invProvisionDetails.keySet();
					Iterator invProvisionIterator = invProvisionSet.iterator();
					
					pStmt=conn.prepareCall("{? = call packIndividualRep.funcUpdIODRemark(?,?,?,?)}");

					while (invProvisionIterator.hasNext())
					{
						String key = (String)invProvisionIterator.next();
						Log.log(Log.DEBUG, "IFDAO", "saveInflowOutflowReport", "key " + key);
						String idStr = key.substring(key.indexOf('-')+1);
						int id=Integer.parseInt(idStr);

						Log.log(Log.DEBUG, "IFDAO", "saveInflowOutflowReport", "saving provision for id " + id);
						
						String remarks = (String)invProvisionDetails.get(key);
						pStmt.registerOutParameter(1, java.sql.Types.INTEGER);
						pStmt.setInt(2, id);
						pStmt.setString(3, remarks);
						pStmt.setString(4, userId);
						pStmt.registerOutParameter(5, java.sql.Types.VARCHAR);
					
						pStmt.executeQuery();
					
						int status = pStmt.getInt(1);
						String error = pStmt.getString(5);
					
						Log.log(Log.DEBUG, "IFDAO", "saveInflowOutflowReport", "Error code and error" +status +", " +error);
					
						if (status==Constants.FUNCTION_FAILURE)
						{
							conn.rollback();
							pStmt.close();
							pStmt=null;
							throw new DatabaseException(error);
						}
					}
					pStmt.close();
					pStmt=null;

					conn.commit();
				}
				catch(SQLException e)
				{
					Log.log(Log.ERROR, "IFDAO", "saveInflowOutflowReport", e.getMessage());
					Log.logException(e);
					try {
					conn.rollback();
					}catch(SQLException sql){}
					throw new DatabaseException("Unable to save plan investment details");
				}
				finally
				{
					DBConnection.freeConnection(conn);
				}
				Log.log(Log.INFO, "IFDAO", "saveInflowOutflowReport", "Exited");
			}
			
		public String getBranchForBank(String bankName)throws DatabaseException
		{
			
			Log.log(Log.INFO, "IFDAO", "getBranchForBank", "Entered");
			
			String branchName = "";
			
			Connection conn = null;

			try
			{
				conn = DBConnection.getConnection(false);
				CallableStatement callableStmt = conn.prepareCall("{?=call funcGetBranchForBank(?,?,?)}");
				
				callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
				callableStmt.setString(2,bankName);				
				callableStmt.registerOutParameter(3,java.sql.Types.VARCHAR);
				callableStmt.registerOutParameter(4,java.sql.Types.VARCHAR);
				
				callableStmt.execute();
				
				int errorCode=callableStmt.getInt(1);

				String error=callableStmt.getString(4);

				Log.log(Log.DEBUG,"IFDAO","getBranchForBank","errorCode,error "+errorCode+","+error);

				if(errorCode==Constants.FUNCTION_FAILURE)
				{
					Log.log(Log.ERROR,"IFDAO","getBranchForBank",error);

					conn.rollback();

					callableStmt.close();
					callableStmt=null;

					throw new DatabaseException(error);
				}
				else{
					
					branchName = callableStmt.getString(3);
				}
			}
			catch(SQLException e)
			{
				Log.log(Log.ERROR, "IFDAO","getBranchForBank", e.getMessage());
				Log.logException(e);
				try {
				conn.rollback();
				}catch(SQLException sql){}
				throw new DatabaseException(e.getMessage());
			}
			
			finally
			{
				DBConnection.freeConnection(conn);
			}
			Log.log(Log.INFO, "IFDAO", "getBranchForBank", "Exited");

			return branchName;
		}
		
		public ArrayList getFdReportForDepositDate(java.sql.Date startDate,
									 java.sql.Date endDate) throws DatabaseException
		{
			Log.log(Log.INFO,"IFDAO","getFdReportForDeposit","Entered");
			PreparedStatement danRaisedStmt = null;
			ArrayList danRaisedArray = new ArrayList();
			ResultSet danRaisedResult;
			Connection connection = DBConnection.getConnection();
			HashMap fdReport = new HashMap();

			if(startDate != null)
			{
				try
				{
					String query = "select inv.INV_NAME, m.MAT_TYPE, sum(i.IDT_COST_OF_PURCHASE)," +
						"sum(i.IDT_MATURITY_AMOUNT) from investee inv, maturity_master m," +
						" investment_detail i where i.MAT_ID = m.MAT_ID and" +
						" i.INV_ID = inv.INV_ID and trunc(i.IDT_PURCHASE_DT) between ? and ?" +
						" and i.INS_ID = 1" + 
						" group by inv.INV_NAME, m.MAT_TYPE";
					danRaisedStmt = connection.prepareStatement(query);
					danRaisedStmt.setDate(2,endDate);
					danRaisedStmt.setDate(1,startDate);
					danRaisedResult = danRaisedStmt.executeQuery();

					while(danRaisedResult.next())

					{
						//Instantiate a FDReport value object
						String investee = danRaisedResult.getString(1);
						FDReport gFee = (FDReport)fdReport.get(investee);

						if(gFee == null)
						{
							gFee  = new FDReport();
							gFee.setInvestee(investee);
//							System.out.println(investee);
							gFee.setMaturityType(danRaisedResult.getString(2));
//							System.out.println(danRaisedResult.getString(2));
							gFee.setPrincipalAmount(danRaisedResult.getDouble(3));
//							System.out.println(danRaisedResult.getDouble(3));
							gFee.setMaturityAmount(danRaisedResult.getDouble(4));
//							System.out.println(danRaisedResult.getDouble(4));
							fdReport.put(investee,gFee);
						}

						else if(gFee != null)
						{
							gFee  = new FDReport();
							gFee.setMaturityType(danRaisedResult.getString(2));
//							System.out.println(danRaisedResult.getString(2));
							gFee.setPrincipalAmount(danRaisedResult.getDouble(3));
//							System.out.println(danRaisedResult.getDouble(3));
							gFee.setMaturityAmount(danRaisedResult.getDouble(4));
//							System.out.println(danRaisedResult.getDouble(4));

						}
						danRaisedArray.add(gFee);
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
			}
			else if(startDate == null)
			{
				try
				{
					String query = "select inv.INV_NAME, m.MAT_TYPE, sum(i.IDT_COST_OF_PURCHASE)," +
						"sum(i.IDT_MATURITY_AMOUNT) from investee inv, maturity_master m," +
						" investment_detail i where i.MAT_ID = m.MAT_ID and" +
						" i.INV_ID = inv.INV_ID and trunc(i.IDT_PURCHASE_DT) <= ?" +
						" and i.INS_ID =1" + 
						" group by inv.INV_NAME, m.MAT_TYPE";
					danRaisedStmt = connection.prepareStatement(query);
					danRaisedStmt.setDate(1,endDate);
					danRaisedResult = danRaisedStmt.executeQuery();

					while(danRaisedResult.next())
					{
						//Instantiate a FDReport value object
						String investee = danRaisedResult.getString(1);
						FDReport gFee = (FDReport)fdReport.get(investee);

						if(gFee == null)
						{
							gFee  = new FDReport();
							gFee.setInvestee(investee);
//							System.out.println(investee);
							gFee.setMaturityType(danRaisedResult.getString(2));
//							System.out.println(danRaisedResult.getString(2));
							gFee.setPrincipalAmount(danRaisedResult.getDouble(3));
//							System.out.println(danRaisedResult.getDouble(3));
							gFee.setMaturityAmount(danRaisedResult.getDouble(4));
//							System.out.println(danRaisedResult.getDouble(4));
							fdReport.put(investee,gFee);
						}

						else if(gFee != null)
						{
							gFee  = new FDReport();
							gFee.setMaturityType(danRaisedResult.getString(2));
//							System.out.println(danRaisedResult.getString(2));
							gFee.setPrincipalAmount(danRaisedResult.getDouble(3));
//							System.out.println(danRaisedResult.getDouble(3));
							gFee.setMaturityAmount(danRaisedResult.getDouble(4));
//							System.out.println(danRaisedResult.getDouble(4));

						}
						danRaisedArray.add(gFee);
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
			}
			Log.log(Log.INFO,"IFDAO","getFdReportForDeposit","Exited");
			return  danRaisedArray;
		}
		
		public ArrayList getFdReportForMaturityDate(java.sql.Date startDate,
									 java.sql.Date endDate) throws DatabaseException
		{
			Log.log(Log.INFO,"IFDAO","getFdReportForMaturity","Entered");
			PreparedStatement danRaisedStmt = null;
			ArrayList danRaisedArray = new ArrayList();
			ResultSet danRaisedResult;
			HashMap fdReport = new HashMap();
			Connection connection = DBConnection.getConnection();



			if(startDate != null)
			{
				try
				{
					String query = "select inv.INV_NAME, m.MAT_TYPE, sum(i.IDT_COST_OF_PURCHASE)," +
						"sum(i.IDT_MATURITY_AMOUNT) from investee inv, maturity_master m," +
						" investment_detail i where i.MAT_ID = m.MAT_ID and i.INV_ID = inv.INV_ID " +
						"and trunc(i.IDT_MATURITY_DT) between ? and ? and i.INS_ID = 1 group by inv.INV_NAME, m.MAT_TYPE";
//					System.out.println(query);
					danRaisedStmt = connection.prepareStatement(query);
					danRaisedStmt.setDate(2,endDate);
					danRaisedStmt.setDate(1,startDate);
					danRaisedResult = danRaisedStmt.executeQuery();

					while(danRaisedResult.next())

					{
						//Instantiate a FDReport value object
						String investee = danRaisedResult.getString(1);
						FDReport gFee = (FDReport)fdReport.get(investee);

						if(gFee == null)
						{
							gFee  = new FDReport();
							gFee.setInvestee(investee);
//							System.out.println(investee);
							gFee.setMaturityType(danRaisedResult.getString(2));
//							System.out.println(danRaisedResult.getString(2));
							gFee.setPrincipalAmount(danRaisedResult.getDouble(3));
//							System.out.println(danRaisedResult.getDouble(3));
							gFee.setMaturityAmount(danRaisedResult.getDouble(4));
//							System.out.println(danRaisedResult.getDouble(4));
							fdReport.put(investee,gFee);
						}

						else if(gFee != null)
						{
							gFee  = new FDReport();
							gFee.setMaturityType(danRaisedResult.getString(2));
//							System.out.println(danRaisedResult.getString(2));
							gFee.setPrincipalAmount(danRaisedResult.getDouble(3));
//							System.out.println(danRaisedResult.getDouble(3));
							gFee.setMaturityAmount(danRaisedResult.getDouble(4));
//							System.out.println(danRaisedResult.getDouble(4));

						}
						danRaisedArray.add(gFee);
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
			}
			else if(startDate == null)
			{
				try
				{
					String query = "select inv.INV_NAME, m.MAT_TYPE, sum(i.IDT_COST_OF_PURCHASE)," +
						"sum(i.IDT_MATURITY_AMOUNT) from investee inv, maturity_master m," +
						" investment_detail i where i.MAT_ID = m.MAT_ID and i.INV_ID = inv.INV_ID " +
						"and trunc(i.IDT_MATURITY_DT) <= ? and i.INS_ID = 1 group by inv.INV_NAME, m.MAT_TYPE";
					danRaisedStmt = connection.prepareStatement(query);
					danRaisedStmt.setDate(1,endDate);
					danRaisedResult = danRaisedStmt.executeQuery();
					boolean firstTime = true;

					while(danRaisedResult.next())

					{
						//Instantiate a FDReport value object
						String investee = danRaisedResult.getString(1);
						FDReport gFee = (FDReport)fdReport.get(investee);

						if(gFee == null)
						{
							gFee  = new FDReport();
							gFee.setInvestee(investee);
//							System.out.println(investee);
							gFee.setMaturityType(danRaisedResult.getString(2));
//							System.out.println(danRaisedResult.getString(2));
							gFee.setPrincipalAmount(danRaisedResult.getDouble(3));
//							System.out.println(danRaisedResult.getDouble(3));
							gFee.setMaturityAmount(danRaisedResult.getDouble(4));
//							System.out.println(danRaisedResult.getDouble(4));
							fdReport.put(investee,gFee);
						}

						else if(gFee != null)
						{
							gFee  = new FDReport();
							gFee.setMaturityType(danRaisedResult.getString(2));
//							System.out.println(danRaisedResult.getString(2));
							gFee.setPrincipalAmount(danRaisedResult.getDouble(3));
//							System.out.println(danRaisedResult.getDouble(3));
							gFee.setMaturityAmount(danRaisedResult.getDouble(4));
//							System.out.println(danRaisedResult.getDouble(4));

						}
						danRaisedArray.add(gFee);
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
			}

			Log.log(Log.INFO,"IFDAO","getFdReportForMaturity","Exited");
			return  danRaisedArray;
		}
   
   /**
   * 
   * @param investmentId
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
//Diksha 31/05/2017 
/* public InvestmentDetails getInvestmentDetails(java.lang.String investmentId)throws DatabaseException

   {
		Log.log(Log.INFO,"IFDAO","getInwardDetail","Entered");
   		CallableStatement investementDetailStmt;
  		Connection connection=DBConnection.getConnection();
   		InvestmentDetails investmentDetail =null;
   		
 		 try
   		{
   		investementDetailStmt = connection.prepareCall("{?=call funcGetInvestmentDetailsNew(?,?,?,?,?,?,?,?,?,?,?,?)}");
			investementDetailStmt.registerOutParameter(1,java.sql.Types.INTEGER);			
			investementDetailStmt.setString(2,investmentId);	
      investementDetailStmt.registerOutParameter(3,java.sql.Types.DATE);
			investementDetailStmt.registerOutParameter(4,java.sql.Types.VARCHAR);
      investementDetailStmt.registerOutParameter(5,Types.DOUBLE);
      investementDetailStmt.registerOutParameter(6,java.sql.Types.VARCHAR);
      investementDetailStmt.registerOutParameter(7,Types.INTEGER);
      investementDetailStmt.registerOutParameter(8,Types.INTEGER);
      investementDetailStmt.registerOutParameter(9,Types.INTEGER);
      investementDetailStmt.registerOutParameter(10,Types.DOUBLE);
      investementDetailStmt.registerOutParameter(11,java.sql.Types.DATE);
      investementDetailStmt.registerOutParameter(12,Types.DOUBLE);     
			investementDetailStmt.registerOutParameter(13,java.sql.Types.VARCHAR);
						
			investementDetailStmt.execute(); 
			int status = investementDetailStmt.getInt(1);
			String error = investementDetailStmt.getString(13);
			
			if(status == Constants.FUNCTION_FAILURE)
			{
				Log.log(Log.ERROR,"IFDAO","getInvestmentDetails","SP returns a 1." +
					" Error code is :" + error);
				investementDetailStmt.close();
				investementDetailStmt = null;
			}
			else if(status == Constants.FUNCTION_SUCCESS)
			{
				//Create a investment object and set the values of different paramter in it
				investmentDetail=new InvestmentDetails();
        investmentDetail.setDepositDate(investementDetailStmt.getDate(3));
				investmentDetail.setBankName(investementDetailStmt.getString(4));
				investmentDetail.setDepositAmt(investementDetailStmt.getDouble(5));
        investmentDetail.setCompoundingFrequency(investementDetailStmt.getString(6));
        investmentDetail.setYears(investementDetailStmt.getInt(7));
        investmentDetail.setMonths(investementDetailStmt.getInt(8));
        investmentDetail.setDays(investementDetailStmt.getInt(9));
        investmentDetail.setRateOfInterest(investementDetailStmt.getDouble(10));
        investmentDetail.setMaturityDate(investementDetailStmt.getDate(11));
        investmentDetail.setMaturityAmount(investementDetailStmt.getDouble(12));
        
					
				investementDetailStmt.close(); 
				investementDetailStmt = null;
				}
	 		 } 
			 catch (Exception exception)
			 {
				throw new DatabaseException(exception.getMessage());
			 }
			 finally{
				DBConnection.freeConnection(connection);
			}
		  Log.log(Log.INFO,"IODAO","getInvestmentDetails","Exited");
	   	 return  investmentDetail;
	   }
 */ //31/05/2017 end    
    
    
		
		public ChequeDetails chequeDetailsUpdatePageReport(String chequeNumber)throws DatabaseException
		{
			Log.log(Log.INFO,"IFDAO","chequeDetailsUpdatePageReport","Entered");
			ChequeDetails chequeDetails = new ChequeDetails();
			ResultSet chequeResult;
			Connection connection = DBConnection.getConnection();
			CallableStatement chequeStmt = null;

				try
				{
					chequeStmt = connection.prepareCall("{? = call packGetChequeDetailsForUpdate.funcGetChequeDetails(?,?,?)}");
					chequeStmt.setString(2,chequeNumber);
					chequeStmt.registerOutParameter(1, Types.INTEGER);
					chequeStmt.registerOutParameter(3,Constants.CURSOR);
					chequeStmt.registerOutParameter(4, Types.VARCHAR);

					chequeStmt.executeQuery();

					int status = chequeStmt.getInt(1);


					if(status == Constants.FUNCTION_FAILURE)
					{
						String errorCode = chequeStmt.getString(4);
						Log.log(Log.ERROR,"IFDAO","chequeDetailsUpdatePageReport","SP returns a 1." +
												" Error code is :" + errorCode);
						chequeStmt.close();
						chequeStmt = null;
					}
					else if(status == Constants.FUNCTION_SUCCESS)
					{
						chequeResult = (ResultSet)chequeStmt.getObject(3);
						while(chequeResult.next())
						{
							chequeDetails  = new ChequeDetails();
							chequeDetails.setChequeNumber(chequeResult.getString(1));
							chequeDetails.setChequeAmount(chequeResult.getDouble(2));
							chequeDetails.setChequeDate(chequeResult.getDate(3));
							chequeDetails.setChequeIssuedTo(chequeResult.getString(4));
							chequeDetails.setBankName(chequeResult.getString(5));
							chequeDetails.setChequeId(chequeResult.getString(6));
							chequeDetails.setChequeRemarks(chequeResult.getString(7));
							chequeDetails.setStatus(chequeResult.getString(8));

						}
						chequeStmt.close();
						chequeStmt = null;
					}

				}
				catch(Exception exception)
				{
					exception.printStackTrace();
					throw new DatabaseException(exception.getMessage());
				}
				finally
				{
					DBConnection.freeConnection(connection);
				}
			Log.log(Log.INFO,"IFDAO","chequeDetailsUpdatePageReport","Exited");
			return chequeDetails;
		}		

}
