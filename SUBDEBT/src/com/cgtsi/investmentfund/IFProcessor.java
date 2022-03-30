package com.cgtsi.investmentfund;

/*************************************************************
   *
   * Name of the class: IFProcessor
   * This is main manager level class of this module.
   *
   * @author : Nithyalakshmi P
   * @version:
   * @since:
   **************************************************************/

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;

import com.cgtsi.admin.AdminDAO;
import com.cgtsi.admin.Administrator;
import com.cgtsi.admin.ParameterMaster;
import com.cgtsi.admin.User;
import com.cgtsi.application.Application;
import com.cgtsi.claim.CPDAO;
import com.cgtsi.claim.ClaimConstants;
import com.cgtsi.claim.ClaimsProcessor;
import com.cgtsi.claim.SettlementDetail;
import com.cgtsi.common.DatabaseException;
import com.cgtsi.common.Log;
import com.cgtsi.common.MessageException;
import com.cgtsi.guaranteemaintenance.NPADetails;
import com.cgtsi.receiptspayments.RpConstants;
import com.cgtsi.receiptspayments.RpProcessor;
import com.cgtsi.receiptspayments.Voucher;
import com.cgtsi.receiptspayments.VoucherDetail;
import com.cgtsi.util.DateHelper;

public class IFProcessor
{
   private IFDAO ifDAO;

   public IFProcessor()
   {
	   ifDAO=new IFDAO();
   }



   public ArrayList chequeDetailsModify(java.sql.Date startDate,java.sql.Date endDate)
								throws DatabaseException
   {
	   Log.log(Log.INFO,"IFProcessor","chequeDetailsModify","Entered");
	   ArrayList chequeArray = null;
	   try
	   {
		chequeArray = ifDAO.chequeDetailsModify(startDate,endDate);
	   }
	   catch(Exception exception)
	   {
		   throw new DatabaseException(exception.getMessage());
	   }
	   Log.log(Log.INFO,"IFProcessor","chequeDetailsModify","Exited");
	   return chequeArray;
   }

   public String getCeiling(String agencyName, String investee, String instrument)
										 throws DatabaseException
   {
	   Log.log(Log.INFO,"IFProcessor","getCeiling","Entered");
	   String ceiling = null;
	   try
	   {
		ceiling = ifDAO.getCeiling(agencyName,investee,instrument); 
	   } 
	   catch(Exception exception)
	   {
		   throw new DatabaseException(exception.getMessage());
	   }
	   Log.log(Log.INFO,"IFProcessor","getCeiling","Exited");
	   return ceiling;
   }    


   public void updateAllowableRatingsForAgency(ArrayList ratingDetailsArray)
										 throws DatabaseException
   {
	   Log.log(Log.INFO,"IFProcessor","updateAllowableRatingsForAgency","Entered");
	   try
	   {
			ifDAO.updateAllowableRatingsForAgency(ratingDetailsArray);
	   } 
	   catch(Exception exception)
	   {
		   throw new DatabaseException(exception.getMessage());
	   }
	   Log.log(Log.INFO,"IFProcessor","updateAllowableRatingsForAgency","Exited");
   }    
   
   
   public ArrayList  showRatingAgencyWithRatings() throws DatabaseException
   {
	   Log.log(Log.INFO,"IFProcessor","showRatingAgencyWithRatings","Entered");
		ArrayList bankDetailsArray = new ArrayList();
	   try
	   {
			bankDetailsArray = ifDAO.showRatingAgencyWithRatings();
	   }
	   catch(Exception exception)
	   {
		   throw new DatabaseException(exception.getMessage());
	   }
	   Log.log(Log.INFO,"IFProcessor","showRatingAgencyWithRatings","Exited");
	   return bankDetailsArray;
   }   


   public void insertAllowableRatingsForAgency(ArrayList ratingDetailsArray)
										 throws DatabaseException
   {
	   Log.log(Log.INFO,"IFProcessor","insertAllowableRatingsForAgency","Entered");
	   try
	   {
			ifDAO.insertAllowableRatingsForAgency(ratingDetailsArray);
	   } 
	   catch(Exception exception)
	   {
		   throw new DatabaseException(exception.getMessage());
	   }
	   Log.log(Log.INFO,"IFProcessor","insertAllowableRatingsForAgency","Exited");
   }   

   
   public ArrayList  getRatingsForAgency(String agencyName) throws DatabaseException
   {
	   Log.log(Log.INFO,"IFProcessor","getRatingsForAgency","Entered");
		ArrayList bankDetailsArray = new ArrayList();
	   try
	   {
			bankDetailsArray = ifDAO.getRatingsForAgency(agencyName);
	   }
	   catch(Exception exception)
	   {
		   throw new DatabaseException(exception.getMessage());
	   }
	   Log.log(Log.INFO,"IFProcessor","getRatingsForAgency","Exited");
	   return bankDetailsArray;
   }   


   public void updateRatingAgency(RatingDetails ratingDetails)	throws DatabaseException
   {
	   Log.log(Log.INFO,"IFProcessor","updateRatingAgency","Entered");
	   try
	   {
			ifDAO.updateRatingAgency(ratingDetails);
	   }
	   catch(Exception exception)
	   {
		   throw new DatabaseException(exception.getMessage());
	   }
	   Log.log(Log.INFO,"IFProcessor","updateRatingAgency","Exited");
   }   
   
   public void insertRatingAgency(RatingDetails ratingDetails)	throws DatabaseException
   {
	   Log.log(Log.INFO,"IFProcessor","insertRatingAgency","Entered");
	   try
	   {
			ifDAO.insertRatingAgency(ratingDetails);
	   }
	   catch(Exception exception)
	   {
		   throw new DatabaseException(exception.getMessage());
	   }
	   Log.log(Log.INFO,"IFProcessor","insertRatingAgency","Exited");
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
  /* public void insertInvestmentDetails(String depositDate,String bankName,double depositAmt,String compoundingFrequency,int years,int months,int days,double rateOfInterest,Date maturityDate,String maturityAmount,String loggedUserId)	throws DatabaseException
   {
	   Log.log(Log.INFO,"IFProcessor","insertInvestmentDetails","Entered");
	   try
	   {
			ifDAO.insertInvestmentDetails(depositDate,bankName,depositAmt,compoundingFrequency,years,months,days,rateOfInterest,maturityDate,maturityAmount,loggedUserId);
	   }
	   catch(Exception exception)
	   {
		   throw new DatabaseException(exception.getMessage());
	   }
	   Log.log(Log.INFO,"IFProcessor","insertInvestmentDetails","Exited");
   }    
*/ //31/05/2017 end
/**
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
/*public void afterUpdateInvestmentDetails(String investmentId,String depositDate,String bankName,double depositAmt,String compoundingFrequency,int years,int months,int days,double rateOfInterest,String maturityDt,double maturityAmount,String loggedUserId)	throws DatabaseException
   {
	   Log.log(Log.INFO,"IFProcessor","afterUpdateInvestmentDetails","Entered");
	   try
	   {
			ifDAO.afterUpdateInvestmentDetails(investmentId,depositDate,bankName,depositAmt,compoundingFrequency,years,months,days,rateOfInterest,maturityDt,maturityAmount,loggedUserId);
	   }
	   catch(Exception exception)
	   {
		   throw new DatabaseException(exception.getMessage());
	   }
	   Log.log(Log.INFO,"IFProcessor","afterUpdateInvestmentDetails","Exited");
   }    
*/
//31/05/2017 end



   public ArrayList  showRatingAgency() throws DatabaseException
   {
	   Log.log(Log.INFO,"IFProcessor","showRatingAgency","Entered");
		ArrayList bankDetailsArray = new ArrayList();
	   try
	   {
			bankDetailsArray = ifDAO.showRatingAgency();
	   }
	   catch(Exception exception)
	   {
		   throw new DatabaseException(exception.getMessage());
	   }
	   Log.log(Log.INFO,"IFProcessor","showRatingAgency","Exited");
	   return bankDetailsArray;
   }
   
   public RatingDetails  showRatingAgencyDetails(String agencyName) throws DatabaseException
	{
		Log.log(Log.INFO,"IFProcessor","showRatingAgencyDetails","Entered");
		RatingDetails ratDtl = new RatingDetails();
		try
		{
			ratDtl = ifDAO.showRatingAgencyDetails(agencyName);
		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"IFProcessor","showRatingAgencyDetails","Exited");
		return ratDtl;
	}   


   public void bankStatementUploadResult(StatementDetail statementDetail) 
									throws DatabaseException
   {
	   Log.log(Log.INFO,"IFProcessor","bankStatementUploadResult","Entered");
	   try
	   {
			ifDAO.bankStatementUploadResult(statementDetail);
	   }
	   catch(Exception exception)
	   {
		   throw new DatabaseException(exception.getMessage());
	   }
	   Log.log(Log.INFO,"IFProcessor","bankStatementUploadResult","Exited");
   }

   public ArrayList  accruedInterestIncomeReport(java.sql.Date endDate) 
									throws DatabaseException
   {
	   Log.log(Log.INFO,"IFProcessor","accruedInterestIncomeReport","Entered");
		ArrayList bankDetailsArray = new ArrayList();
	   try
	   {
			bankDetailsArray = ifDAO.accruedInterestIncomeReport(endDate);
	   }
	   catch(Exception exception)
	   {
		   throw new DatabaseException(exception.getMessage());
	   }
	   Log.log(Log.INFO,"IFProcessor","accruedInterestIncomeReport","Exited");
	   return bankDetailsArray;
   }


   public ArrayList  investeeWiseReportDetails(String investee,java.sql.Date startDate,
						 java.sql.Date endDate) throws DatabaseException
   {
	   Log.log(Log.INFO,"IFProcessor","investeeWiseReportDetails","Entered");
		ArrayList bankDetailsArray = new ArrayList();
	   try
	   {
			bankDetailsArray = ifDAO.investeeWiseReportDetails(investee,startDate, endDate);
	   }
	   catch(Exception exception)
	   {
		   throw new DatabaseException(exception.getMessage());
	   }
	   Log.log(Log.INFO,"IFProcessor","investeeWiseReportDetails","Exited");
	   return bankDetailsArray;
   }
   
   public ArrayList  investeeWiseReportDetailsSummary(String investee,java.sql.Date startDate,
						 java.sql.Date endDate) throws DatabaseException
   {
	   Log.log(Log.INFO,"IFProcessor","investeeWiseReportDetails","Entered");
		ArrayList bankDetailsArray = new ArrayList();
	   try
	   {
			bankDetailsArray = ifDAO.investeeWiseReportDetailsSummary(investee,startDate, endDate);
	   }
	   catch(Exception exception)
	   {
		   throw new DatabaseException(exception.getMessage());
	   }
	   Log.log(Log.INFO,"IFProcessor","investeeWiseReportDetails","Exited");
	   return bankDetailsArray;
   }

   public ChequeDetails chequeDetailsForPayment(String chequeNumber)throws DatabaseException
   {
	   Log.log(Log.INFO,"IFProcessor","chequeDetailsForPayment","Entered");
		ChequeDetails chequeArray = null;
	   try
	   {
		chequeArray = ifDAO.chequeDetailsForPayment(chequeNumber);
	   }
	   catch(Exception exception)
	   {
		   throw new DatabaseException(exception.getMessage());
	   }
	   Log.log(Log.INFO,"IFProcessor","chequeDetailsForPayment","Exited");
	   return chequeArray;
   }

   public ArrayList investmentMaturityReport(java.sql.Date startDate,java.sql.Date date1,
	java.sql.Date date2,java.sql.Date date3,java.sql.Date date4,java.sql.Date date5,
	java.sql.Date date6,java.sql.Date date7,java.sql.Date date8,java.sql.Date date9,
	java.sql.Date date10,java.sql.Date date11,java.sql.Date date12,java.sql.Date date13,
	java.sql.Date date14) 	throws DatabaseException
   {
	   Log.log(Log.INFO,"IFProcessor","investmentMaturityReport","Entered");
	   ArrayList investmentDetails = null;
	   try
	   {
			investmentDetails = ifDAO.investmentMaturityReport(startDate,date1,date2,date3,
			date4,date5,date6,date7,date8,date9,date10,date11,date12,date13,date14);
	   }
	   catch(Exception exception)
	   {
		   throw new DatabaseException(exception.getMessage());
	   }
	   Log.log(Log.INFO,"IFProcessor","investmentMaturityReport","Exited");
	   return investmentDetails;
   }

   public ArrayList investmentMaturityReportDetails(java.sql.Date startDate,java.sql.Date endDate,
						String type) throws DatabaseException
   {
	   Log.log(Log.INFO,"IFProcessor","investmentMaturityReportDetails","Entered");
	   ArrayList investmentDetails = null;
	   try
	   {
		investmentDetails = ifDAO.investmentMaturityReportDetails(startDate,endDate,type);
	   }
	   catch(Exception exception)
	   {
		   throw new DatabaseException(exception.getMessage());
	   }
	   Log.log(Log.INFO,"IFProcessor","investmentMaturityReportDetails","Exited");
	   return investmentDetails;
   }

   public ArrayList investmentMaturityReportDetailsForEndDate(java.sql.Date endDate,
						String type) throws DatabaseException
   {
	   Log.log(Log.INFO,"IFProcessor","investmentMaturityReportDetailsForEndDate","Entered");
	   ArrayList investmentDetails = null;
	   try
	   {
		investmentDetails = ifDAO.investmentMaturityReportDetailsForEndDate(endDate,type);
	   }
	   catch(Exception exception)
	   {
		   throw new DatabaseException(exception.getMessage());
	   }
	   Log.log(Log.INFO,"IFProcessor","investmentMaturityReportDetailsForEndDate","Exited");
	   return investmentDetails;
   }

   public ArrayList investmentReport(java.sql.Date startDate,java.sql.Date endDate)
								throws DatabaseException
   {
	   Log.log(Log.INFO,"IFProcessor","investmentReport","Entered");
	   ArrayList investmentDetails = null;
	   try
	   {
		investmentDetails = ifDAO.investmentReport(startDate,endDate);
	   }
	   catch(Exception exception)
	   {
		   throw new DatabaseException(exception.getMessage());
	   }
	   Log.log(Log.INFO,"IFProcessor","investmentReport","Exited");
	   return investmentDetails;
   }

   public ArrayList investmentReportDetails(java.sql.Date startDate,java.sql.Date endDate,
									String type) throws DatabaseException
   {
	   Log.log(Log.INFO,"IFProcessor","investmentReportDetails","Entered");
	   ArrayList investmentDetails = null;
	   try
	   {
		investmentDetails = ifDAO.investmentReportDetails(startDate,endDate,type);
	   }
	   catch(Exception exception)
	   {
		   throw new DatabaseException(exception.getMessage());
	   }
	   Log.log(Log.INFO,"IFProcessor","investmentReportDetails","Exited");
	   return investmentDetails;
   }



   public ArrayList investmentReportDetailsForFixedDeposit(String type) throws DatabaseException
   {
	   Log.log(Log.INFO,"IFProcessor","investmentReportDetailsForFixedDeposit","Entered");
	   ArrayList investmentDetails = null;
	   try
	   {
		investmentDetails = ifDAO.investmentReportDetailsForFixedDeposit(type);
	   }
	   catch(Exception exception)
	   {
		   throw new DatabaseException(exception.getMessage());
	   }
	   Log.log(Log.INFO,"IFProcessor","investmentReportDetailsForFixedDeposit","Exited");
	   return investmentDetails;
   }

   public ArrayList investmentReportDetailsForMutualFund(String type) throws DatabaseException
   {
	   Log.log(Log.INFO,"IFProcessor","investmentReportDetailsForMutualFund","Entered");
	   ArrayList investmentDetails = null;
	   try
	   {
		investmentDetails = ifDAO.investmentReportDetailsForMutualFund(type);
	   }
	   catch(Exception exception)
	   {
		   throw new DatabaseException(exception.getMessage());
	   }
	   Log.log(Log.INFO,"IFProcessor","investmentReportDetailsForMutualFund","Exited");
	   return investmentDetails;
   }

   public ArrayList investmentReportDetailsForBonds(String type) throws DatabaseException
   {
	   Log.log(Log.INFO,"IFProcessor","investmentReportDetailsForBonds","Entered");
	   ArrayList investmentDetails = null;
	   try
	   {
		investmentDetails = ifDAO.investmentReportDetailsForBonds(type);
	   }
	   catch(Exception exception)
	   {
		   throw new DatabaseException(exception.getMessage());
	   }
	   Log.log(Log.INFO,"IFProcessor","investmentReportDetailsForBonds","Exited");
	   return investmentDetails;
   }

   public ArrayList investmentReportDetailsForCommercialpapers(String type) throws DatabaseException
   {
	   Log.log(Log.INFO,"IFProcessor","investmentReportDetailsForCommercialpapers","Entered");
	   ArrayList investmentDetails = null;
	   try
	   {
		investmentDetails = ifDAO.investmentReportDetailsForCommercialpapers(type);
	   }
	   catch(Exception exception)
	   {
		   throw new DatabaseException(exception.getMessage());
	   }
	   Log.log(Log.INFO,"IFProcessor","investmentReportDetailsForCommercialpapers","Exited");
	   return investmentDetails;
   }

   public ArrayList investmentReportDetailsForDebentures(String type) throws DatabaseException
   {
	   Log.log(Log.INFO,"IFProcessor","investmentReportDetailsForDebentures","Entered");
	   ArrayList investmentDetails = null;
	   try
	   {
		investmentDetails = ifDAO.investmentReportDetailsForDebentures(type);
	   }
	   catch(Exception exception)
	   {
		   throw new DatabaseException(exception.getMessage());
	   }
	   Log.log(Log.INFO,"IFProcessor","investmentReportDetailsForDebentures","Exited");
	   return investmentDetails;
   }

   public ArrayList investmentReportDetailsForGSecurities(String type) throws DatabaseException
   {
	   Log.log(Log.INFO,"IFProcessor","investmentReportDetailsForGSecurities","Entered");
	   ArrayList investmentDetails = null;
	   try
	   {
		investmentDetails = ifDAO.investmentReportDetailsForGSecurities(type);
	   }
	   catch(Exception exception)
	   {
		   throw new DatabaseException(exception.getMessage());
	   }
	   Log.log(Log.INFO,"IFProcessor","investmentReportDetailsForGSecurities","Exited");
	   return investmentDetails;
   }


	public void hvcInsertSuccess(ChequeDetails chequeDetails)
					  throws DatabaseException
	 {
		 Log.log(Log.INFO,"IFProcessor","hvcInsertSuccess","Entered");
		 ArrayList chequeArray = null;
		 try
		 {
		  ifDAO.hvcInsertSuccess(chequeDetails);
		 }
		 catch(Exception exception)
		 {
			 throw new DatabaseException(exception.getMessage());
		 }
		 Log.log(Log.INFO,"IFProcessor","hvcInsertSuccess","Exited");
	 }


	public ArrayList hvcUpdate(java.sql.Date startDate,java.sql.Date endDate)
								 throws DatabaseException
	{
		Log.log(Log.INFO,"IFProcessor","hvcUpdate","Entered");
		ArrayList chequeArray = null;
		try
		{
		 chequeArray = ifDAO.hvcUpdate(startDate,endDate);
		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"IFProcessor","hvcUpdate","Exited");
		return chequeArray;
	}


	public ChequeDetails hvcUpdatePage(String chequeNumber)throws DatabaseException
	{
		Log.log(Log.INFO,"IFProcessor","hvcUpdatePage","Entered");
		 ChequeDetails chequeArray = null;
		try
		{
			chequeArray = ifDAO.hvcUpdatePage(chequeNumber);
		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"IFProcessor","hvcUpdatePage","Exited");

		return chequeArray;
	}


	public void hvcUpdateSuccess(ChequeDetails chequeDetails, String chequeNumber)
					  throws DatabaseException
	 {
		 Log.log(Log.INFO,"IFProcessor","hvcUpdateSuccess","Entered");
		 ArrayList chequeArray = null;
		 try
		 {
		  ifDAO.hvcUpdateSuccess(chequeDetails,chequeNumber);
		 }
		 catch(Exception exception)
		 {
			 throw new DatabaseException(exception.getMessage());
		 }
		 Log.log(Log.INFO,"IFProcessor","hvcUpdateSuccess","Exited");
	 }


   /*
	* Cheque Details
	*
	*/

   public void chequeDetailsInsertSuccess(ChequeDetails chequeDetails, String contextPath,
						String user) throws DatabaseException,MessageException
   {
	   Log.log(Log.INFO,"IFProcessor","chequeDetailsInsertSuccess","Entered");
	   ArrayList chequeArray = null;

		ifDAO.chequeDetailsInsertSuccess(chequeDetails, null,contextPath,user);
	   Log.log(Log.INFO,"IFProcessor","chequeDetailsInsertSuccess","Exited");
   }


   public void chequeDetailsUpdateSuccess(ChequeDetails chequeDetails, String chequeNumber)
					 throws DatabaseException
	{
		Log.log(Log.INFO,"IFProcessor","chequeDetailsUpdateSuccess","Entered");
		ArrayList chequeArray = null;
		try
		{
		 ifDAO.chequeDetailsUpdateSuccess(chequeDetails,chequeNumber);
		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"IFProcessor","chequeDetailsUpdateSuccess","Exited");
	}




   public ArrayList chequeDetailsUpdate(java.sql.Date startDate,java.sql.Date endDate)
								throws DatabaseException
   {
	   Log.log(Log.INFO,"IFProcessor","chequeDetailsUpdate","Entered");
	   ArrayList chequeArray = null;
	   try
	   {
		chequeArray = ifDAO.chequeDetailsUpdate(startDate,endDate);
	   }
	   catch(Exception exception)
	   {
		   throw new DatabaseException(exception.getMessage());
	   }
	   Log.log(Log.INFO,"IFProcessor","chequeDetailsUpdate","Exited");
	   return chequeArray;
   }


   public ChequeDetails chequeDetailsUpdatePage(String chequeNumber)throws DatabaseException
   {
	   Log.log(Log.INFO,"IFProcessor","chequeDetailsUpdatePage","Entered");
		ChequeDetails chequeArray = null;
	   try
	   {
		chequeArray = ifDAO.chequeDetailsUpdatePage(chequeNumber);
	   }
	   catch(Exception exception)
	   {
		   throw new DatabaseException(exception.getMessage());
	   }
	   Log.log(Log.INFO,"IFProcessor","chequeDetailsUpdatePage","Exited");
	   return chequeArray;
   }

   /**
	* The method passes on the BudgetDetails object to the corresponding method in
	* IFDAO class for the values in the object to be stored in the database.
	*/
   public void saveBudgetDetails(BudgetDetails budgetDetails) throws DatabaseException
   {
	   if (budgetDetails != null)
	   {
		   ifDAO.saveBudgetDetail(budgetDetails);
	   }
	   else
	   {
		   throw new DatabaseException("No Budget Details!");
	   }
   }

   /**
	* This method saves the various ceiling settings in to the database. This method
	* internally calls saveCeilingPerInvesteeDetail(),
	* saveCeilingPerMaturitiesDetail() and saveCeilingPerInstrumentDetail().
	*
	* The parameter to the method is a collection object containing the
	* CeilingPerInvesteeDetail object, CeilingPerMaturitiesDetail object and
	* CeilingPerInstrumentDetail object.
	*/
/*   public void saveCeilingOnInvestment(HashMap ceilingDetails) throws DatabaseException
   {
		if(ceilingDetails != null)
		{
			ifDAO.saveCeilingOnInvestment(ceilingDetails);
		}
		else
		{
			throw new DatabaseException("No Ceiling Details!");
		}
   }*/

   /**
	* This method will return all the Investee Names from the investee master table.
	*/
/*   public Vector getInvestees() throws DatabaseException
   {
		return ifDAO.getInvestees();
   }*/

   /**
	* This method returns all the Instruments in the instrument_master table as a
	* collections object.
	*/
/*   public Vector getInstruments() throws DatabaseException
   {
		return ifDAO.getInstruments();
   }*/

   /**
	* This method returns all the maturity info from the maturity_master table as a
	* collections object.
	*/
/*   public Vector getMaturities() throws DatabaseException
   {
		return ifDAO.getMaturities();
   }*/

   /**
	* This method will get all the instrument features from the instrument_feature
	* master table. The return type will be a java.util.collections object or an
	* array.
	*/
   /*public Vector getInstrumentFeatures() throws DatabaseException
   {
		return ifDAO.getInstrumentFeatures();
   }*/

   /**
	* This method is used to add a new Holiday entity into the Holiday Master table.
	*/
   public void updateHolidayMaster(Hashtable holidayDetails) throws DatabaseException
   {
	   if(holidayDetails != null)
	   {
			 ifDAO.updateHolidayMaster(holidayDetails);
	   }
	   else
	   {
		   throw new DatabaseException("No Holiday Details!");
	   }
   }

   /**
	* This method is used to add a new Investee entity into the Investee master table.
	*/
   public void updateInvesteeMaster(InvesteeDetail investeeDetails,String userId) throws DatabaseException
   {
	   if(investeeDetails != null)
	   {
		   ifDAO.updateInvesteeMaster(investeeDetails,userId);
	   }
	   else
	   {
		   throw new DatabaseException("No Investee Details!");
	   }
   }

   /**
	* This method adds a new Maturity type in to Maturity master table.
	*/
   public void updateMaturityMaster(MaturityDetail matDetail, String userId) throws DatabaseException
   {
	if(matDetail != null)
		{
			 ifDAO.updateMaturityMaster(matDetail, userId);
		}
		else
		{
			throw new DatabaseException("No Maturity Details!");
		}

   }

   /**
	* This method adds a new Budget Head into the Budget Head master table.
	*/
   public void updateBudgetHeadsMaster(BudgetHead budgetHead,String userId) throws DatabaseException{
	   if(budgetHead != null)
	   {
			ifDAO.updateBudgetHeadsMaster(budgetHead,userId);
	   }
	   else{
		   throw new DatabaseException("No Budget Head Details!");
	   }
   }

   /**
	* This method adds a new Budget Sub Head into the Budget Sub Head master table.
	*/
   public void updateBudgetSubHeadMaster(BudgetSubHead budgetSubHeadDetails,String userId) throws DatabaseException
   {
	   if(budgetSubHeadDetails != null)
	   {
			ifDAO.updateBudgetSubHeadMaster(budgetSubHeadDetails,userId);
	   }
	   else
	   {
		   throw new DatabaseException("No Budget Sub Head Details!");
	   }
   }

   /**
	* This method adds a new Instrument in to the Instrument master table.
	*/
   public void updateInstrumentMaster(InstrumentDetail instrumentDetails,String userId) throws DatabaseException
   {
	   if(instrumentDetails != null)
	   {
		   ifDAO.updateInstrumentMaster(instrumentDetails,userId);
	   }
	   else
	   {
			throw new DatabaseException("No Instrument Details!");
	   }
   }

   /**
	* This method adds a new Instrument feature into the Instrument Feature master
	* table.
	*/
   public void updateInstrumentFeature(InstrumentFeature instrumentFeatureDetails,String userId) throws DatabaseException
   {
		if(instrumentFeatureDetails != null)
		{
			ifDAO.updateInstrumentFeature(instrumentFeatureDetails,userId);
		}
		else
		{
			throw new DatabaseException("No Instrument Features!");
		}
   }

   /**
	* This method adds a new Instrument Scheme in to the Instrument_Scheme master
	* table.
	*/
   public void updateInstrumentScheme(Hashtable instrumentSchemeDetails) throws DatabaseException
   {
		if(instrumentSchemeDetails != null)
		{
			ifDAO.updateInstrumentScheme(instrumentSchemeDetails);
		}
		else
		{
			throw new DatabaseException("No Instrument Scheme Details!");
		}
   }

   /**
	* This method adds a new Rating entity into the Rating_master master table.
	*/
   public void updateRatingMaster(Hashtable ratingDetails) throws DatabaseException
   {
	   if(ratingDetails != null)
	   {
		   ifDAO.updateRatingMaster(ratingDetails);
	   }
	   else
	   {
		   throw new DatabaseException("No Rating Details!");
	   }
   }

   /**
	* This method adds a new Corpus entity into the corpus_master master table.
	*/

   public void updateCorpusMaster(Hashtable corpusDetails) throws DatabaseException
   {
	   if(corpusDetails != null)
	   {
		   ifDAO.updateCorpusMaster(corpusDetails);
	   }
	   else
	   {
		   throw new DatabaseException("No Corpus Details!");
	   }
   }

   /**
	* This method passes on the object to the corresponding method in IFDAO class
	* for the values in the object to be persisted in the database.
	*/
   public String saveBuyOrSellDetails(BuySellDetail buySellDetail, String contextPath) throws DatabaseException, MessageException
   {
	   if(buySellDetail != null)
	   {
		   return ifDAO.saveBuyOrSellDetails(buySellDetail, contextPath);
	   }
	   else
	   {
		   throw new DatabaseException("No Buy Sell Details!");
	   }
   }

   /**
	* This method passes on the object to the corresponding method in IFDAO class
	* for the values in the object to be persisted in the database.
	*/
   public void saveFundTransferDetail(FundTransferDetail fundTransferDetail) throws DatabaseException
   {
	   if(fundTransferDetail != null)
	   {
		   ifDAO.saveFundTransferDetail(fundTransferDetail);
	   }
	   else
	   {
		   throw new DatabaseException("No Fund Transfer Details");
	   }
   }

   /**
	* This method passes on the object to the corresponding method in IFDAO class for the
	* values in the object to be persisted in the database.
	*/
   public void saveTDSDetail(TDSDetail tdsDetail) throws DatabaseException
   {
	   if(tdsDetail != null)
	   {
		   ifDAO.saveTDSDetail(tdsDetail);
	   }
	   else
	   {
		   throw new DatabaseException("No TDS Details!");
	   }
   }

   /**
	* This method gets all the Budget Heads from the Budget_HEAD master table.
	*/
   public Vector getBudgetHeadTitles(String ioFlag) throws DatabaseException
   {
	   return ifDAO.getBudgetHeadTitles(ioFlag);
   }

   /**
	* This method gets all the Budget Sub Heads for the given Budget head title
	* from the Budget_Sub_HEAD master table.
	*/
   public Vector getBudgetSubHeadTitles(String budgetHead,String flag) throws DatabaseException
   {
	   return ifDAO.getBudgetSubHeadTitles(budgetHead,flag);
   }

   /**
	* This method passes on the object to the corresponding method in IFDAO class
	* for the values in the object to be persisted in the database.
	*/

   public void saveForecastingDetail(ForecastDetails forecastingDetail) throws DatabaseException
   {
	   if(forecastingDetail != null)
	   {
		   ifDAO.saveForecastingDetail(forecastingDetail);
	   }
	   else
	   {
		   throw new DatabaseException("No Forecasting Details!");
	   }
   }

   /**
	* This method passes on the object to the corresponding method in IFDAO class for the
	* values in the object to be persisted in the database.
	*/
   public void saveProjectExpectedClaimDetail(ProjectExpectedClaimDetail
						projectedExpectedClaimDetail,String userId) throws DatabaseException
   {
	   if(projectedExpectedClaimDetail != null)
	   {
		   ifDAO.saveProjectExpectedClaimDetail(projectedExpectedClaimDetail,userId);
	   }
	   else
	   {
		   throw new DatabaseException("No Details for Project Expected Claims!");
	   }
   }

   /**
	* The parameter to this method is an object of BankStmtDetail.  This method
	* passes on the object to the corresponding method in IFDAO class for the values
	* in the object to be persisted in the database.
	*/
/*   public void saveBankStmtDetail(InvestmentPlanningDetail bankStmtDetail) throws DatabaseException
   {
	   if(bankStmtDetail != null)
	   {
		   ifDAO.saveBankStmtDetail(bankStmtDetail);
	   }
	   else
	   {
		   throw new DatabaseException("No Bank Statement Details!");
	   }
   }*/

   /**
	* The parameter to this method is an object of FDDetail.
	* This method passes on the object to the corresponding method in IFDAO class
	* for the values in the object to be persisted in the database.
	*
	* @param FDDetail - Value Object for FD Details
	* @throws DatabaseException
	*/
   public void saveInvestmentDetail(FDDetail fdDetail) throws DatabaseException
   {
	   if (fdDetail != null)
	   {
		   ifDAO.saveInvestmentDetail(fdDetail);
	   }
	   else
	   {
		   throw new DatabaseException("Null FD Details");
	   }
   }

   /**
	* The parameter to this method is an object of CommercialPaperDetail.
	* This method passes on the object to the corresponding method in IFDAO class
	* for the values in the object to be persisted in the database.
	*
	* @param CommercialPaperDetail - Value Object for Commercial Paper Details
	* @throws DatabaseException
	*/
   public void saveInvestmentDetail(CommercialPaperDetail commercialPaperDetail) throws DatabaseException
   {
	   if (commercialPaperDetail != null)
	   {
		   ifDAO.saveInvestmentDetail(commercialPaperDetail);
	   }
	   else
	   {
		   throw new DatabaseException("Null Commercial Paper Details");
	   }
   }

   /**
	* The parameter to this method is an object of MutualFundDetail.
	* This method passes on the object to the corresponding method in IFDAO class
	* for the values in the object to be persisted in the database.
	*
	* @param MutualFundDetail - Value Object for Mutual Fund Details
	* @throws DatabaseException
	*/
   public void saveInvestmentDetail(MutualFundDetail mutualFundDetail) throws DatabaseException
   {
	   if (mutualFundDetail != null)
	   {
		   ifDAO.saveInvestmentDetail(mutualFundDetail);
	   }
	   else
	   {
		   throw new DatabaseException("Null Mutual Fund Details");
	   }
   }

   /**
	* The parameter to this method is an object of GovySecurityDetail.
	* This method passes on the object to the corresponding method in IFDAO class
	* for the values in the object to be persisted in the database.
	*
	* @param GovtSecurityDetail - Value Object for Government Security Details
	* @throws DatabaseException
	*/
   public void saveInvestmentDetail(GovtSecurityDetail govtSecurityDetail) throws DatabaseException
   {
	   if (govtSecurityDetail != null)
	   {
		   ifDAO.saveInvestmentDetail(govtSecurityDetail);
	   }
	   else
	   {
		   throw new DatabaseException("Null Governemnt Security Details");
	   }
   }

   /**
	* The parameter to this method is an object of DebentureDetail.
	* This method passes on the object to the corresponding method in IFDAO class
	* for the values in the object to be persisted in the database.
	*
	* @param DebentureDetail - Value Object for Debenture Details
	* @throws DatabaseException
	*/
   public void saveInvestmentDetail(DebentureDetail debentureDetail) throws DatabaseException
   {
	   if (debentureDetail != null)
	   {
		   ifDAO.saveInvestmentDetail(debentureDetail);
	   }
	   else
	   {
		   throw new DatabaseException("Null Debenture Details");
	   }
   }

   /**
	* The parameter to this method is an object of BondsDetail.
	* This method passes on the object to the corresponding method in IFDAO class
	* for the values in the object to be persisted in the database.
	*
	* @param BondsDetail - Value Object for Bond Details
	* @throws DatabaseException
	*/
   public void saveInvestmentDetail(BondsDetail bondsDetail) throws DatabaseException
   {
	   if (bondsDetail != null)
	   {
		   ifDAO.saveInvestmentDetail(bondsDetail);
	   }
	   else
	   {
		   throw new DatabaseException("Null Bond Details");
	   }
   }

   /**
	* This method passes on the object to the corresponding method in IFDAO class for the
	* values in the object to be persisted in the database.
	*/
   public void saveInvestmentFulfillmentDetail(InvestmentFulfillmentDetail investmentFulfillmentDetail, String updateFlag,ChequeDetails chequeDetails,String contextPath,String loggedUserId) throws DatabaseException,MessageException
   {
	   if(investmentFulfillmentDetail != null)
	   {
		   ifDAO.saveInvestmentFulfillmentDetail(investmentFulfillmentDetail, updateFlag,chequeDetails,contextPath,loggedUserId);
	   }
	   else
	   {
		   throw new DatabaseException("Investment Fulfillment Details!");
	   }
   }

   /**
	* This method passes on the object to the corresponding method in IFDAO class for the
	* values in the object to be persisted in the database.
	*/
   public void saveInvestmentPlanningDetail(InvestmentPlanningDetail investmentPlanningDetail) throws DatabaseException
   {
	   if(investmentPlanningDetail != null)
	   {
		   ifDAO.saveInvestmentPlanningDetail(investmentPlanningDetail);
	   }
	   else
	   {
		   throw new DatabaseException("No Investment Planning Details!");
	   }
   }

   /**
	* This method is used for projecting expected claims. The return type for this
	* method is a HashMap object.
	*/
/*   public HashMap getPlanningDetails(String date) throws DatabaseException
   {
	   if(date != null)
	   {
		   return ifDAO.getPlanningDetails(date);
	   }
	   else
	   {

		   throw new DatabaseException("No Plan Details!");
	   }
   }*/

   /**
	* This method passes on the object to the corresponding method in IFDAO class for the
	* values in the object to be persisted in the database.
	*/
   public void saveActualInflowOutflowDetails(ActualInflowOutflowDetails actualIODetail) throws DatabaseException
   {
	   if(actualIODetail != null)
	   {
		   ifDAO.saveActualInflowOutflowDetails(actualIODetail);
	   }
	   else
	   {
		   throw new DatabaseException("No Inflow Outflow Details!");
	   }

   }

   /**
	* This method is used for calculating the exposure based on the input parameters.
	* This method passes on the parameters to the corresponding method in IFDAO
	* class.
	*/
/*   public HashMap calculateExposure(java.lang.String investee) throws DatabaseException
   {
	   if(investee != null)
	   {
		   return ifDAO.calculateExposure(investee);
	   }
	   else
	   {
		   throw new DatabaseException("No Investee Parameter");
	   }
   }*/

   /**
	* This method gets all the bank accounts from the bank_account_master table. The
	* return type for this method is a Vector.
	*/
   public ArrayList getBankAccounts() throws DatabaseException
   {
			return ifDAO.getBankAccounts();
   }

   //ramvel 28thNov begin

   /**
	* This method gets all the Investee names from the Investee master table.
	*/
   public ArrayList getAllInvesteeNames() throws DatabaseException
   {
	   return ifDAO.getAllInvesteeNames();
   }
   public ArrayList getAllInvesteeNamesForGroup(String groupName) throws DatabaseException
   {
		return ifDAO.getAllInvesteeNamesForGroup(groupName);
   }
   /**
	* This method gets all the instrument types from the instrument master table.
	*/
   public ArrayList getInstrumentTypes(String flag) throws DatabaseException
   {
	   return ifDAO.getInstrumentTypes(flag);
   }

   /**
	* This method gets all the ratings from the Rating master table.
	*/
   public ArrayList getAllRatings() throws DatabaseException
   {
	   return ifDAO.getAllRatings();
   }

   /**
	* This method gets all the budget heads from the budgethead master table.
	*/
   public ArrayList getBudgetHeadTypes(String headType) throws DatabaseException
   {
	   return ifDAO.getBudgetHeadTypes(headType);
   }

	/**
	* This method gets all the InstrumentSchemeTypes from the InstrumentScheme table.
	*/
   public ArrayList getInstrumentSchemeTypes(String instName) throws DatabaseException
   {
	   return ifDAO.getInstrumentSchemeTypes(instName);
   }

   /**
	* This method gets all the InstrumentSchemeTypes from the InstrumentScheme table.
	*/
   public ArrayList getInstrumentFeatures() throws DatabaseException
   {
	   return ifDAO.getInstrumentFeatures();
   }

   /**
	* This method gets all the InstrumentSchemeTypes from the InstrumentScheme table.
	*/
   public ArrayList getReceiptNumbers() throws DatabaseException
   {
	   return ifDAO.getReceiptNumbers();
   }

   /**
	* This method gets availableBalance from the bank_statement_detail table.
	*/
   public String getAvailableBalance(String acctNo) throws DatabaseException
   {
	   return ifDAO.getAvailableBalance(acctNo);
   }

   /**
	* This method gets availableBalance from the bank_statement_detail table.
	*/
   public String getMonthlyExpense(String acctNo,String frmDate,String toDate) throws DatabaseException
   {
	   return ifDAO.getMonthlyExpense(acctNo,frmDate,toDate);
   }

   /**
	* This method gets availableBalance from the bank_statement_detail table.
	*/
   public String getTodayExpense(String acctNo) throws DatabaseException
   {
	   return ifDAO.getTodayExpense(acctNo);
   }

   public void saveInvesteeGroup(InvesteeGrpDetail invGrp, String userId) throws DatabaseException
   {
		ifDAO.saveInvesteeGroup(invGrp,userId);
   }
   public ArrayList getInvesteeGroups() throws DatabaseException
   {
		return ifDAO.getInvesteeGroups();
   }

   public HashMap getAnnualHeadDetails(Date annualFromDate,
							Date annualToDate,String flag) throws DatabaseException
   {
		return ifDAO.getAnnualHeadDetails(annualFromDate,annualToDate,flag);
   }

   public HashMap getAnnualSubHeadDetails(Date annualFromDate,
				Date annualToDate,String head,String flag) throws DatabaseException
   {
		return ifDAO.getAnnualSubHeadDetails(annualFromDate,annualToDate,head,flag);
   }

   public HashMap getShortHeadDetails(String year,String month, String flag)
								throws DatabaseException
   {
		return ifDAO.getShortHeadDetails(year,month,flag);
   }

   public HashMap getShortSubHeadDetails(String year,String month,String head,String flag)
								throws DatabaseException
   {
		return ifDAO.getShortSubHeadDetails(year,month,head,flag);
   }

   public HashMap getInflowOutflowHeadDetails(Date dateOfFlow,String flag)
								throws DatabaseException
   {
		return ifDAO.getInflowOutflowHeadDetails(dateOfFlow,flag);
   }

   public HashMap getInflowOutflowSubHeadDetails(String head,Date dateOfFlow,String flag)
								throws DatabaseException
   {
		return ifDAO.getInflowOutflowSubHeadDetails(head,dateOfFlow,flag);
   }

   public ArrayList getInstrumentSchemes() throws DatabaseException
   {
		return ifDAO.getInstrumentSchemes();
   }
   public ArrayList getInvestmentRefNumbers(String inst) throws DatabaseException
   {
		return ifDAO.getInvestmentRefNumbers(inst);
   }
   //ramvel 28thNov End

   public void addBankAccountDetail(BankAccountDetail accountDetail,String userId)
										 throws DatabaseException
   {
		ifDAO.addBankAccountDetail(accountDetail,userId);
   }

   public void addStatementDetail(StatementDetail statementDetail,String userId)
										throws DatabaseException, MessageException
   {
		ifDAO.addStatementDetail(statementDetail,userId);
   }
   public ArrayList getAllBanksWithAccountNumbers() throws DatabaseException
   {
		return ifDAO.getAllBanksWithAccountNumbers();
   }

   public ArrayList getAllMaturities() throws DatabaseException
   {
		return ifDAO.getAllMaturities();
   }
   public void addMaturityWiseCeiling(MaturityWiseCeiling maturityCeiling,String userId) throws DatabaseException
   {
		ifDAO.addMaturityWiseCeiling(maturityCeiling,userId);
   }
   public void addInstrumentWiseCeiling(InstrumentWiseCeiling instrumentCeiling,String userId) throws DatabaseException
   {
		ifDAO.addInstrumentWiseCeiling(instrumentCeiling,userId);
   }
   public void addInvesteeGroupWiseCeiling(InvesteeGroupWiseCeiling investeeGroupCeiling,String userId) throws DatabaseException
   {
		ifDAO.setInvesteeGroupWiseCeiling(investeeGroupCeiling,userId);
   }

   public void addInvesteeWiseCeiling(InvesteeWiseCeiling investeeGroupCeiling,String userId) throws DatabaseException
   {
		ifDAO.setInvesteeWiseCeiling(investeeGroupCeiling,userId);
   }
   public void setRatingWiseCeiling(RatingWiseCeiling ratingCeiling,String userId) throws DatabaseException
   {
		 ifDAO.setRatingWiseCeiling(ratingCeiling,userId);
   }
   public PlanInvestment getPlanInvestmentDetails() throws DatabaseException
   {
		return ifDAO.getPlanInvestmentDetails();
   }

/*   public ExposureDetails getExposure(Date sysDate, Date proposedDate)
	throws DatabaseException,InvestmentLimitExceededException
   {
		Log.log(Log.INFO,"IFProcessor","getExposure","Entered");

		//ExposureDetails exposureDetails=ifDAO.getExposure(investeeGroup,investeeName);
		// since the value entered by the user for the investee n stored in the db are in crores,
		// while retrieving n calculating the values, v have to convert the value to crores.
		//exposureDetails.setInvesteeNetWorth(exposureDetails.getInvesteeNetWorth()*10000000);
		//exposureDetails.setInvesteeTangibleAssets(exposureDetails.getInvesteeTangibleAssets()*10000000);

/*		Log.log(Log.INFO,"IFProcessor","getExposure","proposedAmntToBeInvested :" + proposedAmntToBeInvested);

		double dProposedAmntToBeInvested = 0;

		if((proposedAmntToBeInvested != null) && (proposedAmntToBeInvested.equals("")))
		{
			 dProposedAmntToBeInvested = 0;
		}
		else
		{
			dProposedAmntToBeInvested = (Double.valueOf(proposedAmntToBeInvested)).doubleValue();
		}

		double totalPrincipalAmount = ifDAO.getPrincipalAmntInInvesteeGrpAndName(sysDate, proposedDate);

	Log.log(Log.INFO,"IFProcessor","getExposure","totalPrincipalAmount :" + totalPrincipalAmount);

		double totalMaturedAmnt = ifDAO.getMaturityAmntInInvesteeGrpAndName(sysDate, proposedDate);

	Log.log(Log.INFO,"IFProcessor","getExposure","totalMaturedAmnt :" + totalMaturedAmnt);

		double totalAmntForExposure = dProposedAmntToBeInvested + totalPrincipalAmount + totalMaturedAmnt;

	Log.log(Log.INFO,"IFProcessor","getExposure","totalAmntForExposure :" + totalAmntForExposure);

		exposureDetails.setTotalAmntForCalculateExposure(totalAmntForExposure);

		exposureDetails=calculateExposure(exposureDetails, proposedDate);

		for(int i=0;i<exposureDetails.getExposureDetails().size();i++)
		{
			Log.log(Log.INFO,"IFProcessor","getExposure","ceiling amount "+((ExposureDetail)exposureDetails.getExposureDetails().get(i)).getCeilingAmount());
		}

		Log.log(Log.INFO,"IFProcessor","getExposure","Entered");

		return exposureDetails;
   }
*/

	public ArrayList getExposure(Date sysDate, Date proposedDate,double corpusAmount)
		throws DatabaseException,InvestmentLimitExceededException
		{
			ArrayList investeeWiseExpReport = new ArrayList();
			ExposureDetails exposureDetails=new ExposureDetails();
			
			//investee Group Wise
			ArrayList investeeGroups = ifDAO.getInvesteeGroups();
			
			for(int i=0; i<investeeGroups.size(); i++)
			{
				String invGroup = (String)investeeGroups.get(i);
				
				Log.log(Log.INFO,"IFProcessor","getExposure","invGroup :" + invGroup);
				ArrayList invNames = ifDAO.getAllInvesteeNamesForGroup(invGroup);
				//investee Wise
				for(int j=0; j<invNames.size(); j++)
				{
					ExposureDetails exposureDetailsTemp=new ExposureDetails();
					String investeeName = (String)invNames.get(j);
					
					Log.log(Log.INFO,"IFProcessor","getExposure","investeeName :" + investeeName);
					
					double investeeAmount = ifDAO.getInvestedAmountInInvestee(invGroup,investeeName,proposedDate);
					
					Log.log(Log.INFO,"IFProcessor","getExposure","got details for:" + investeeName);
					ExposureDetails expDetails = ifDAO.getExposure(invGroup,investeeName);
					
					double invNetWorth = expDetails.getInvesteeNetWorth() * 10000000;
					double invTangibleAsset = expDetails.getInvesteeTangibleAssets() * 10000000;
					
					double ceilingNetWorth = expDetails.getNetworth();
					double ceilingTangibleAsset = expDetails.getTangibleAssets();
					
					double eligibleNetWorth = (ceilingNetWorth/100)*invNetWorth;//4a
					double eligibleTangibleAsset = (ceilingTangibleAsset/100)*invTangibleAsset;//4b
					
					exposureDetailsTemp.setInvesteeName(investeeName);
					exposureDetailsTemp.setInvestedAmount(investeeAmount);
					exposureDetailsTemp.setInvesteeNetWorth(eligibleNetWorth);
					exposureDetailsTemp.setInvesteeTangibleAssets(eligibleTangibleAsset);
					
					double totalCorpusAmount = ifDAO.getCorpusAmount();
					double expCorpusAmount = (expDetails.getCeilingLimit()/100)* (totalCorpusAmount + corpusAmount);
					exposureDetailsTemp.setCeilingLimit(expCorpusAmount);//5
					
					exposureDetailsTemp.setInvCeilingAmt(expDetails.getInvCeilingAmt());//6
					
					double eligibleAmount = Math.min(expDetails.getInvCeilingAmt(),Math.min(expCorpusAmount,Math.min(eligibleNetWorth,eligibleTangibleAsset)));
					
					exposureDetailsTemp.setInvesteeEligibleAmt(eligibleAmount);
					if(eligibleAmount - investeeAmount>=0)
					{
						exposureDetailsTemp.setGapAvailableAmount(eligibleAmount - investeeAmount);
					}
					else{
						exposureDetailsTemp.setGapAvailableAmount(0);
					}
					
					if(expDetails.getCeilingLimit()!=0)
					{
						investeeWiseExpReport.add(exposureDetailsTemp);
					}
					
					
				}
			}
			
			return investeeWiseExpReport;
		}
		
		public ArrayList getInvesteeGrpWiseDetails(Date sysDate, Date proposedDate,double corpusAmount)throws DatabaseException
		{
			
			ExposureDetails exposureDetails=new ExposureDetails();
			ArrayList investeeGrpWiseExpReport = new ArrayList();
			
			ArrayList investeeGroups = ifDAO.getInvesteeGroups();
			for(int i=0; i<investeeGroups.size(); i++)
			{
				ExposureDetails exposureDetailsTemp=new ExposureDetails();
				
				String investeeGrpName = (String)investeeGroups.get(i);
				double investedAmtForGrp = ifDAO.getInvestedAmountInInvesteeGroup(investeeGrpName,proposedDate);
				
				InvesteeGroupWiseCeiling invGrpWiseCeiling = ifDAO.getIGroupWiseCeiling(investeeGrpName);
				if(invGrpWiseCeiling!=null)
				{
					double totalCorpusAmount = ifDAO.getCorpusAmount();
					if(invGrpWiseCeiling.getCeilingLimit()!=0)
					{
						double expCorpusAmount = (invGrpWiseCeiling.getCeilingLimit()/100)* (totalCorpusAmount + corpusAmount);
				
						exposureDetailsTemp.setInvesteeGroup(investeeGrpName);
						exposureDetailsTemp.setInvestedAmount(investedAmtForGrp);
						exposureDetailsTemp.setCeilingLimit(expCorpusAmount);
						exposureDetailsTemp.setInvCeilingAmt(invGrpWiseCeiling.getCeilingAmount());
				
						double eligibleAmount = Math.min(expCorpusAmount,invGrpWiseCeiling.getCeilingAmount());
						exposureDetailsTemp.setInvesteeEligibleAmt(eligibleAmount);
				
						if(eligibleAmount - investedAmtForGrp>=0)
						{
							exposureDetailsTemp.setGapAvailableAmount(eligibleAmount - investedAmtForGrp);
						}
						else{
							exposureDetailsTemp.setGapAvailableAmount(0);
						}
						investeeGrpWiseExpReport.add(exposureDetailsTemp);
					
					}
					
				}
				
			}
			
			return investeeGrpWiseExpReport;
		}
		
	public ArrayList getMaturityWiseDetails(Date sysDate, Date proposedDate,double surplusAmount)throws DatabaseException
	{
		ExposureDetails exposureDetails=new ExposureDetails();
		ArrayList maturityWiseExpReport = new ArrayList();
		
		ArrayList allMaturities = ifDAO.getAllMaturities();
		for(int i=0; i<allMaturities.size(); i++)
		{
			ExposureDetails exposureDetailsTemp=new ExposureDetails();
			
			String maturityType = (String)allMaturities.get(i);
			
			exposureDetailsTemp.setMaturityName(maturityType);
			
			double amountInvested = ifDAO.getInvestedInMaturity(maturityType,proposedDate);
			
			exposureDetailsTemp.setInvestedAmount(amountInvested);
			
			MaturityWiseCeiling matWiseCeiling = ifDAO.getMaturityWiseCeiling(maturityType);
			if(matWiseCeiling!=null)
			{
				double ceilingLimit = matWiseCeiling.getCeilingLimit();
			
				if(ceilingLimit!=0)
				{
					if(surplusAmount==0)
					{
						exposureDetailsTemp.setGapAvailableAmount(-1);
					}

					double partSurplusFunds = (ceilingLimit / 100)* surplusAmount;
			
					exposureDetailsTemp.setInvCeilingAmt(partSurplusFunds);
			
					if(partSurplusFunds!=0 && partSurplusFunds - amountInvested >=0)
					{
							exposureDetailsTemp.setGapAvailableAmount(partSurplusFunds - amountInvested);
					}
					else{
				
						exposureDetailsTemp.setGapAvailableAmount(-1);
					}
					maturityWiseExpReport.add(exposureDetailsTemp);
				
				}
			}
		}
		
		return maturityWiseExpReport;
		
	}		
		
	public ArrayList getInstCategoryWiseDetails(Date sysDate, Date proposedDate,double surplusAmount)throws DatabaseException
	{
		ExposureDetails exposureDetails=new ExposureDetails();
		ArrayList instCatWiseExpReport = new ArrayList();
		
		ArrayList instCatList = (ArrayList)ifDAO.getInstrumentCategories();
		for(int i=0; i<instCatList.size(); i++)
		{
			String instCatName = (String)instCatList.get(i);
			
			double invAmount = ifDAO.getInvestedInInstCat(instCatName,proposedDate);
				ExposureDetails exposureDetailsTemp=new ExposureDetails();
				exposureDetailsTemp.setInstCatName(instCatName);
				exposureDetailsTemp.setInvestedAmount(invAmount);
				
				InstrumentCategoryWiseCeiling instCatWiseCeiling = ifDAO.getInstrumentWiseCeiling(instCatName);
				if(instCatWiseCeiling!=null)
				{
					double ceilingLimit = instCatWiseCeiling.getCeilingLimit();
				
					if(ceilingLimit!=0)
					{
						if(surplusAmount==0)
						{
							exposureDetailsTemp.setGapAvailableAmount(-1);
						}
						
						double partSurplusFunds = (ceilingLimit / 100)* surplusAmount;
						exposureDetailsTemp.setInvCeilingAmt(partSurplusFunds);
				
						if(partSurplusFunds!=0 && partSurplusFunds - invAmount >=0)
						{
								exposureDetailsTemp.setGapAvailableAmount(partSurplusFunds - invAmount);
						}
						else{
				
							exposureDetailsTemp.setGapAvailableAmount(-1);
						}
						instCatWiseExpReport.add(exposureDetailsTemp);				
					
					}
					
				}
			
		}
		
		return instCatWiseExpReport;

	}
		
/*		public ArrayList getInvesteeGrpWiseDetails(Date sysDate, Date proposedDate)throws DatabaseException
		{
			ArrayList investeeGroups = ifDAO.getInvesteeGroups();
		}
*/	
/*   private ExposureDetails calculateExposure(ExposureDetails exposureDetails, Date proposedDate)
				 throws DatabaseException,InvestmentLimitExceededException
   {
		Log.log(Log.INFO,"IFProcessor","calculateExposure","Entered");

		double totalAmntForExposure = exposureDetails.getTotalAmntForCalculateExposure();

		// double corpusAmount=ifDAO.getCorpusAmount();

		Log.log(Log.DEBUG,"IFProcessor","calculateExposure","totalAmntForExposure "+totalAmntForExposure);

		ArrayList ceilingDetails=ifDAO.getCeilingDetails(exposureDetails);

		double investedAmountInInvesteeGroup=
				ifDAO.getInvestedAmountInInvesteeGroup(exposureDetails.getInvesteeGroup(), proposedDate);

		Log.log(Log.DEBUG,"IFProcessor","calculateExposure","investedAmountInInvesteeGroup "+investedAmountInInvesteeGroup);

		exposureDetails.setInvGrpInvestedAmt(investedAmountInInvesteeGroup);

		ArrayList exposes=
			ifDAO.getInvestedAmountInInvestee(exposureDetails.getInvesteeGroup(),exposureDetails.getInvesteeName(), proposedDate);

		exposureDetails.setExposureDetails(exposes);

		Log.log(Log.DEBUG,"IFProcessor","calculateExposure","Size ... "+exposes.size());
		///*
		double investedAmountInInvestee=0;

		for(int i=0;i<exposes.size();i++)
		{
			investedAmountInInvestee+=((ExposureDetail)exposes.get(i)).getAmountInvested();
		}

		Log.log(Log.DEBUG,"IFProcessor","calculateExposure","investedAmountInInvestee "+investedAmountInInvestee);

		exposureDetails.setInvInvestedAmt(investedAmountInInvestee);
		//*
		double investmentLimit=0;
		//ArrayList exposures=exposureDetails.getExposureDetails();
		Log.log(Log.DEBUG,"IFProcessor","calculateExposure","Size ...234 "+exposes.size());

		double investeeEligibleAmt=0;
		double invGrpEligibleAmt=0;
		Hashtable instEligibleAmts=new Hashtable();
		Hashtable matEligibleAmts=new Hashtable();
		Hashtable instInvestedAmts=new Hashtable();
		Hashtable matInvestedAmts=new Hashtable();

		for(int j=0;j<ceilingDetails.size();j++)
		{
			CeilingDetails ceilingDetail=(CeilingDetails)ceilingDetails.get(j);

			Log.log(Log.DEBUG,"IFProcessor","getCeilingDetails","flag is " +ceilingDetail.getFlag());
			Log.log(Log.DEBUG,"IFProcessor","getCeilingDetails","ceilign name " +ceilingDetail.getCeilingName());
			Log.log(Log.DEBUG,"IFProcessor","getCeilingDetails","setCeilingLimit name " +ceilingDetail.getCeilingLimit());
			Log.log(Log.DEBUG,"IFProcessor","getCeilingDetails","setCeilingNetWorth name " +ceilingDetail.getCeilingNetWorth());
			Log.log(Log.DEBUG,"IFProcessor","getCeilingDetails","setCelingTangibleAssets name " +ceilingDetail.getCelingTangibleAssets());
			Log.log(Log.DEBUG,"IFProcessor","getCeilingDetails","setCeilingInPercentage name " +ceilingDetail.getCeilingInPercentage());

			switch(ceilingDetail.getFlag())
			{
				case InvestmentFundConstants.INVESTEE_CEILING:
				{
					Log.log(Log.DEBUG,"IFProcessor","calculateExposure","Investee Ceiling");

					if (ceilingDetail.getCeilingName().equalsIgnoreCase(exposureDetails.getInvesteeName()))
					{
						double netWorth=exposureDetails.getInvesteeNetWorth();
						double tangibleAssets=exposureDetails.getInvesteeTangibleAssets();

						double ceilingNetWorth=ceilingDetail.getCeilingNetWorth();

						double networthExposureAmount=ceilingNetWorth*netWorth/100;

						Log.log(Log.DEBUG,"IFProcessor","calculateExposure","netWorth,tangibleAssets,ceilingNetWorth " +
							"networthExposureAmount "+netWorth+","+
						tangibleAssets+","+ceilingNetWorth+","+networthExposureAmount);
						Log.log(Log.DEBUG,"IFProcessor","calculateExposure","investmentLimit "+investmentLimit);

						double tangibleExposureAmount=
							ceilingDetail.getCelingTangibleAssets()*tangibleAssets/100;

						Log.log(Log.DEBUG,"IFProcessor","calculateExposure","tangibleExposureAmount "+tangibleExposureAmount);
						Log.log(Log.DEBUG,"IFProcessor","calculateExposure","investmentLimit++++++++++ "+investmentLimit+" +++++++++++++");
						Log.log(Log.DEBUG,"IFProcessor","calculateExposure","Switch : INVESTEE_CEILING");

						double invExpAmt = ceilingDetail.getCeilingInPercentage()*totalAmntForExposure/100;

						investeeEligibleAmt=Math.min(networthExposureAmount, Math.min(tangibleExposureAmount, invExpAmt));
						exposureDetails.setInvesteeEligibleAmt(investeeEligibleAmt);
					}
					Log.log(Log.DEBUG,"IFProcessor","calculateExposure","investmentLimit++++++++++ "+investeeEligibleAmt+" +++++++++++++");
					Log.log(Log.DEBUG,"IFProcessor","calculateExposure","Switch : INVESTEE_CEILING");

					break;
				}
				case InvestmentFundConstants.INVESTEE_GROUP_CEILING:
				{
					Log.log(Log.DEBUG,"IFProcessor","calculateExposure","Investee Group Ceiling");
					if (ceilingDetail.getCeilingName().equalsIgnoreCase(exposureDetails.getInvesteeGroup()))
					{
						double percentage=ceilingDetail.getCeilingInPercentage();

						Log.log(Log.DEBUG,"IFProcessor","calculateExposure","percentage "+percentage);
						double invGrpExpAmt = totalAmntForExposure*percentage/100;

						double ceilingAmount=ceilingDetail.getCeilingLimit();

						invGrpEligibleAmt=Math.min(invGrpExpAmt, ceilingAmount);
						exposureDetails.setInvGroupEligibleAmt(invGrpEligibleAmt);
						exposureDetails.setInvCeilingAmt(ceilingAmount);
					}
					Log.log(Log.DEBUG,"IFProcessor","calculateExposure","investmentLimit++++++++++ "+invGrpEligibleAmt+" +++++++++++++");
					Log.log(Log.DEBUG,"IFProcessor","calculateExposure","Switch : INVESTEE_GROUP_CEILING");

					break;
				}
				case InvestmentFundConstants.INSTRUMENT_CEILING:
				{
					Log.log(Log.DEBUG,"IFProcessor","calculateExposure","Instrument ceiling ");

						String name = ceilingDetail.getCeilingName();
						double percentage=ceilingDetail.getCeilingInPercentage();

						Log.log(Log.DEBUG,"IFProcessor","calculateExposure","percentage "+percentage);

						double instExpAmt = totalAmntForExposure*percentage/100;
						instEligibleAmts.put(name, new Double(instExpAmt));

					Log.log(Log.DEBUG,"IFProcessor","calculateExposure","name++++++++++ "+name+" +++++++++++++");
					Log.log(Log.DEBUG,"IFProcessor","calculateExposure","Limit++++++++++ "+instExpAmt+" +++++++++++++");
					Log.log(Log.DEBUG,"IFProcessor","calculateExposure","Switch : INSTRUMENT_CEILING");

					break;
				}

				case InvestmentFundConstants.MATURITY_CEILING:
				{
					Log.log(Log.DEBUG,"IFProcessor","calculateExposure","maturity ceiling ");

					String name = ceilingDetail.getCeilingName();
					double percentage=ceilingDetail.getCeilingInPercentage();

					Log.log(Log.DEBUG,"IFProcessor","calculateExposure","percentage "+percentage);

					double matExpAmt = totalAmntForExposure*percentage/100;
					matEligibleAmts.put(name, new Double(matExpAmt));

					Log.log(Log.DEBUG,"IFProcessor","calculateExposure","name++++++++++ "+name+" +++++++++++++");
					Log.log(Log.DEBUG,"IFProcessor","calculateExposure","Limit++++++++++ "+matExpAmt+" +++++++++++++");
					Log.log(Log.DEBUG,"IFProcessor","calculateExposure","Switch : MATURITY_CEILING");

					break;
				}
			}

			ceilingDetail=null;
		}

		exposureDetails.setInstEligibleAmts(instEligibleAmts);
		exposureDetails.setMatEligibleAmts(matEligibleAmts);
		exposureDetails.setInvGroupEligibleAmt(invGrpEligibleAmt);
		exposureDetails.setInvesteeEligibleAmt(investeeEligibleAmt);

		double tempMinAmt = 0;
		if (invGrpEligibleAmt!=0 && investeeEligibleAmt!=0)
		{
			tempMinAmt = Math.min(invGrpEligibleAmt, investeeEligibleAmt);
		}
		else
		{
			tempMinAmt = Math.max(invGrpEligibleAmt, investeeEligibleAmt);
		}


		for(int i=0;i<exposes.size();i++)
		{
			Log.log(Log.DEBUG,"IFProcessor","calculateExposure","i ... "+i+","+exposes.size());

			investmentLimit=0;
			ExposureDetail exposure=(ExposureDetail)exposes.get(i);

			Log.log(Log.DEBUG,"IFProcessor","calculateExposure","+++ "+exposure.getCeilingAmount()+""+exposure);

			String maturity=exposure.getMaturityName();
			String instrumentName=exposure.getInstrumentName();
			double investedIninstrument=ifDAO.getInvestedInInstrument(instrumentName, proposedDate);
			double investedInMaturity=ifDAO.getInvestedInMaturity(maturity, proposedDate);

			Log.log(Log.DEBUG,"IFProcessor","calculateExposure","maturity,instrument,amount invested" +
				"in instrument, amount invested in maturity "+maturity+","+
			instrumentName+","+investedIninstrument+","+investedInMaturity);

			double tempAmt = 0;
			if (instInvestedAmts.get(instrumentName)!=null)
			{
				tempAmt = ((Double)instInvestedAmts.get(instrumentName)).doubleValue();
			}
			instInvestedAmts.put(instrumentName, new Double(investedIninstrument+tempAmt));
			tempAmt=0;
			if (matInvestedAmts.get(maturity)!=null)
			{
				tempAmt = ((Double)matInvestedAmts.get(maturity)).doubleValue();
			}
			matInvestedAmts.put(maturity, new Double(investedInMaturity+tempAmt));

			double tempInst = tempMinAmt;
			if (instEligibleAmts.get(instrumentName)!=null)
			{
				tempInst = ((Double)instEligibleAmts.get(instrumentName)).doubleValue();
			}
			double tempMat = tempMinAmt;
			if (instEligibleAmts.get(instrumentName)!=null)
			{
				tempMat = ((Double)matEligibleAmts.get(maturity)).doubleValue();
			}

			exposure.setCeilingAmount(Math.min(tempInst, Math.min(tempMat, tempMinAmt)));
			exposes.set(i, exposure);

			Log.log(Log.DEBUG,"IFProcessor","calculateExposure","+++ "+exposure.getCeilingAmount()+""+exposure);
		}
		for(int i=0;i<exposes.size();i++)
		{
			ExposureDetail exposure=(ExposureDetail)exposes.get(i);
			Log.log(Log.DEBUG,"IFProcessor","calculateExposure","AAA "+exposure.getCeilingAmount()+"");
		}
		exposureDetails.setExposureDetails(exposes);
		exposureDetails.setInstInvestedAmts(instInvestedAmts);
		exposureDetails.setMatInvestedAmts(matInvestedAmts);


		Log.log(Log.INFO,"IFProcessor","calculateExposure","Exited");
		return exposureDetails;
   }
*/
   public ArrayList getClaimProjection(Date startDate,Date endDate) throws DatabaseException
   {
		ArrayList projections=new ArrayList();

		Log.log(Log.INFO,"IFProcessor","getClaimProjection","Entered");

		//1.get all guarantee issued applications.
		//2. check whether they are live or not.
		//3. consolidate the live applications.
		//4. get the NPA accounts.
		//5. get the outstanding amounts.
		//5. classify the accounts (>2 Lakhs and <2 Lakhs.
		//6. Apply the predefined % to arrive the claim projections.

		ParameterMaster params=new Administrator().getParameter();


		//get the application issued dates.
		Map guaranteeIssuedApplications=ifDAO.getGuaranteeIssuedApplications();

		//get the application tenures.
		Map tenures=ifDAO.getApplicationTenures();

		Map outstandingDetails=ifDAO.getOutstandingDetails();

		//CGPANs array list to hold the live and the lock in period expiry
		//falls between the start and end dates.
		ArrayList cgpans=new ArrayList();

		Set appsSet=guaranteeIssuedApplications.keySet();
		Iterator iterator=appsSet.iterator();

		while(iterator.hasNext())
		{
			String cgpan=(String)iterator.next();

			GuaranteeIssuedDetail guaranteeIssuedDetails=(GuaranteeIssuedDetail)guaranteeIssuedApplications.get(cgpan);

			Date date=guaranteeIssuedDetails.getGuaranteeIssuedDate();

			Log.log(Log.DEBUG,"IFProcessor","getClaimProjection","cgpan,date "+cgpan+","+date);

			Calendar temp=Calendar.getInstance();

			temp.setTime(date);
			Date currentDate=new Date();
			//Find out they are live or not.
			if(tenures.containsKey(cgpan))
			{
				Log.log(Log.DEBUG,"IFProcessor","getClaimProjection","TC Application");

				int tenure=((Integer)tenures.get(cgpan)).intValue();

				Log.log(Log.DEBUG,"IFProcessor","getClaimProjection","tenure "+tenure);

				temp.add(Calendar.MONTH,tenure);

				Date tempDate=temp.getTime();

				Log.log(Log.DEBUG,"IFProcessor","getClaimProjection","tenure "+tempDate);

				if(tempDate.compareTo(currentDate)<0)
				{
					continue;
				}
			}
			else
			{
				Log.log(Log.DEBUG,"IFProcessor","getClaimProjection","WC Application");
				//WC Applications.
				temp.add(Calendar.MONTH,InvestmentFundConstants.WC_TENURE);
				Date tempDate=temp.getTime();

				Log.log(Log.DEBUG,"IFProcessor","getClaimProjection","tenure "+tempDate);

				if(tempDate.compareTo(currentDate)<0)
				{
					continue;
				}
			}

			Calendar cal=Calendar.getInstance();
			cal.setTime(date);

			cal.add(Calendar.MONTH,params.getLockInPeriod());

			Date expiryDate=cal.getTime();
			Log.log(Log.DEBUG,"IFProcessor","getClaimProjection","expiry date "+expiryDate);
			//If start date is null, lock in period expiry should be within the end date.
			if(startDate==null)
			{
				Log.log(Log.DEBUG,"IFProcessor","getClaimProjection","start date is null ");

				if(expiryDate.compareTo(endDate)<=0)
				{
					Log.log(Log.DEBUG,"IFProcessor","getClaimProjection","Added CGPAN "+cgpan);
					cgpans.add(cgpan);
				}
			}
			else
			{
				Log.log(Log.DEBUG,"IFProcessor","getClaimProjection","start date is not null ");

				if(expiryDate.compareTo(startDate)>=0 && expiryDate.compareTo(endDate)<=0)
				{
					Log.log(Log.DEBUG,"IFProcessor","getClaimProjection","Added CGPAN "+cgpan);
					cgpans.add(cgpan);
				}
			}
		}

		//get the NPA accounts.
		ArrayList npaCgpans=ifDAO.getNPACgpans();

		//browse through the NPA applications.
		double amountAvl=0;

		for(int i=0;i<npaCgpans.size();i++)
		{
			String cgpan=(String)npaCgpans.get(i);

			Log.log(Log.DEBUG,"IFProcessor","getClaimProjection","NPA CGPAN "+cgpan);

			if(!cgpans.contains(cgpan))
			{
				continue;
			}

			GuaranteeIssuedDetail issuedDetails=(GuaranteeIssuedDetail)guaranteeIssuedApplications.get(cgpan);
			if(outstandingDetails.get(cgpan)==null)
			{
				continue;
			}
			double outstandingAmount=((Double)outstandingDetails.get(cgpan)).doubleValue();

			if(outstandingAmount==0)
			{
				continue;
			}
			amountAvl=0.75*0.75*outstandingAmount;

			ProjectExpectedClaimDetail projection=new ProjectExpectedClaimDetail();

			projection.setCgpan(cgpan);

			projection.setOutstandingAmount(Math.round(outstandingAmount));
			projection.setProjectedClaimAmount(Math.round(amountAvl));

			projections.add(projection);

			guaranteeIssuedApplications.remove(cgpan);
		}

		Set nonNPAKeySet=guaranteeIssuedApplications.keySet();

		Iterator nonNPAiterator=nonNPAKeySet.iterator();

		//while(nonNPAiterator.hasNext())
		for(int i=0;i<cgpans.size();i++)
		{
			String cgpan=(String)cgpans.get(i);

			Log.log(Log.DEBUG,"IFProcessor","getClaimProjection","Non-NPA CGPAN "+cgpan);

			GuaranteeIssuedDetail issuedDetails=(GuaranteeIssuedDetail)guaranteeIssuedApplications.get(cgpan);

			if(issuedDetails==null)
			{
				continue;
			}
			Log.log(Log.DEBUG,"IFProcessor","getClaimProjection","Avl.");

			double issuedAmount=issuedDetails.getGuaranteeIssuedAmount();

			if(outstandingDetails.get(cgpan)==null)
			{
				continue;
			}

			double outstandingAmount=((Double)outstandingDetails.get(cgpan)).doubleValue();

			if(outstandingAmount==0)
			{
				continue;
			}
			if(issuedAmount<=InvestmentFundConstants.CLAIM_PROJECTION_GROUP1)
			{
				amountAvl=0.50*0.75*outstandingAmount;
			}
			else
			{
				amountAvl=0.30*0.75*outstandingAmount;
			}

			ProjectExpectedClaimDetail projection=new ProjectExpectedClaimDetail();

			projection.setCgpan(cgpan);
			projection.setOutstandingAmount(Math.round(outstandingAmount));
			projection.setProjectedClaimAmount(Math.round(amountAvl));

			projections.add(projection);
		}

		Log.log(Log.INFO,"IFProcessor","getClaimProjection","Exited");

		return projections;

   }


	   public ArrayList  statementReport(java.sql.Date endDate) throws DatabaseException
   {
	   Log.log(Log.INFO,"IFProcessor","statementReport","Entered");
	   ArrayList bankDetailsArray = new ArrayList();
	   try
	   {
		   bankDetailsArray = ifDAO.statementReport(endDate);
	   }
	   catch(Exception exception)
	   {
		   throw new DatabaseException(exception.getMessage());
	   }
	   Log.log(Log.INFO,"IFProcessor","statementReport","Exited");
	   return bankDetailsArray;
   }

   public ArrayList  statementReportDetails(String cgpan, java.sql.Date endDate) throws DatabaseException
   {
	   Log.log(Log.INFO,"IFProcessor","statementReportDetails","Entered");
		ArrayList bankDetailsArray = new ArrayList();
	   try
	   {
			bankDetailsArray = ifDAO.statementReportDetails(cgpan, endDate);
	   }
	   catch(Exception exception)
	   {
		   throw new DatabaseException(exception.getMessage());
	   }
	   Log.log(Log.INFO,"IFProcessor","statementReportDetails","Exited");
	   return bankDetailsArray;
   }


   public ArrayList  getFdReport(java.sql.Date startDate,
						 java.sql.Date endDate) throws DatabaseException
   {
	   Log.log(Log.INFO,"IFProcessor","getFdReport","Entered");
		ArrayList bankDetailsArray = new ArrayList();
	   try
	   {
			bankDetailsArray = ifDAO.getFdReport(startDate, endDate);
	   }
	   catch(Exception exception)
	   {
		   throw new DatabaseException(exception.getMessage());
	   }
	   Log.log(Log.INFO,"IFProcessor","getFdReport","Exited");
	   return bankDetailsArray;
   }

   public ArrayList  fdReceiptDetails(String number,java.sql.Date startDate,
						 java.sql.Date endDate) throws DatabaseException
   {
		Log.log(Log.INFO,"IFProcessor","fdReceiptDetails","Entered");
		ArrayList bankDetailsArray = new ArrayList();
	   try
	   {
			bankDetailsArray = ifDAO.fdReceiptDetails(number,startDate, endDate);
	   }
	   catch(Exception exception)
	   {
		   throw new DatabaseException(exception.getMessage());
	   }
	   Log.log(Log.INFO,"IFProcessor","fdReceiptDetails","Exited");
	   return bankDetailsArray;
   }


   public ArrayList  getFdReportForDeposit(java.sql.Date startDate,
						 java.sql.Date endDate) throws DatabaseException
   {
	   Log.log(Log.INFO,"IFProcessor","getFdReportForDeposit","Entered");
		ArrayList bankDetailsArray = new ArrayList();
	   try
	   {
			bankDetailsArray = ifDAO.getFdReportForDeposit(startDate, endDate);
	   }
	   catch(Exception exception)
	   {
		   throw new DatabaseException(exception.getMessage());
	   }
	   Log.log(Log.INFO,"IFProcessor","getFdReportForDeposit","Exited");
	   return bankDetailsArray;
   }


   public ArrayList  fdDetailsForDeposit(String investee,java.sql.Date startDate,
						 java.sql.Date endDate) throws DatabaseException
   {
	   Log.log(Log.INFO,"IFProcessor","fdDetailsForDeposit","Entered");
		ArrayList bankDetailsArray = new ArrayList();
	   try
	   {
			bankDetailsArray = ifDAO.fdDetailsForDeposit(investee,startDate, endDate);
	   }
	   catch(Exception exception)
	   {
		   throw new DatabaseException(exception.getMessage());
	   }
	   Log.log(Log.INFO,"IFProcessor","fdDetailsForDeposit","Exited");
	   return bankDetailsArray;
   }



   public ArrayList  getFdReportForMaturity(java.sql.Date startDate,
						 java.sql.Date endDate) throws DatabaseException
   {
	   Log.log(Log.INFO,"IFProcessor","getFdReportForMaturity","Entered");
		ArrayList bankDetailsArray = new ArrayList();
	   try
	   {
			bankDetailsArray = ifDAO.getFdReportForMaturity(startDate, endDate);
	   }
	   catch(Exception exception)
	   {
		   throw new DatabaseException(exception.getMessage());
	   }
	   Log.log(Log.INFO,"IFProcessor","getFdReportForMaturity","Exited");
	   return bankDetailsArray;
   }

   public ArrayList  fdDetailsForMaturity(String investee,java.sql.Date startDate,
						 java.sql.Date endDate) throws DatabaseException
   {
	   Log.log(Log.INFO,"IFProcessor","fdDetailsForMaturity","Entered");
		ArrayList bankDetailsArray = new ArrayList();
	   try
	   {
			bankDetailsArray = ifDAO.fdDetailsForMaturity(investee,startDate, endDate);
	   }
	   catch(Exception exception)
	   {
		   throw new DatabaseException(exception.getMessage());
	   }
	   Log.log(Log.INFO,"IFProcessor","fdDetailsForMaturity","Exited");
	   return bankDetailsArray;
   }

   public InvesteeDetail getInvesteeDetails(String invGrp, String invName) throws DatabaseException
   {
	   return ifDAO.getInvesteeDetails(invGrp, invName);
   }

   public MaturityDetail getMaturityDetails(String matType) throws DatabaseException
   {
	   return ifDAO.getMaturityDetails(matType);
   }

   public InstrumentDetail getInstrumentDetail(String instName) throws DatabaseException
   {
	   return ifDAO.getInstrumentDetail(instName);
   }

   public InstrumentFeature getInstFeaturesDetails(String instFeature) throws DatabaseException
   {
	   return ifDAO.getInstFeaturesDetails(instFeature);
   }

   public String getInstSchemeDetails(String instScheme) throws DatabaseException
   {
	   return ifDAO.getInstSchemeDetails(instScheme);
   }

   public Hashtable getRatingDetails(String rating) throws DatabaseException
   {
	   return ifDAO.getRatingDetails(rating);
   }

   public ArrayList getCorpusList(Date fromDate, Date toDate) throws DatabaseException
   {
	   return ifDAO.getCorpusList(fromDate, toDate);
   }

   public CorpusDetail getCorpusDetails(String corpusId) throws DatabaseException
   {
	   return ifDAO.getCorpusDetails(corpusId);
   }

   public ArrayList getHolidayDates() throws DatabaseException
   {
	   return ifDAO.getHolidayDates();
   }

   public String getHolidayDetail(Date holDate) throws DatabaseException
   {
	   return ifDAO.getHolidayDetail(holDate);
   }

   public void insertHolidayMaster(Hashtable holidayDetails) throws DatabaseException
   {
	   if(holidayDetails != null)
	   {
			 ifDAO.insertHolidayMaster(holidayDetails);
	   }
	   else
	   {
		   throw new DatabaseException("No Holiday Details!");
	   }
   }

   public void saveEnterPaymentDetails(PaymentDetails paydetails,ChequeDetails chequeDetails,
									String contextPath) throws DatabaseException,MessageException
   {

		 ifDAO.saveEnterPaymentDetails(paydetails,chequeDetails,contextPath);
   }

   public void saveModifyPaymentDetails(PaymentDetails paydetails) throws DatabaseException
	{
		ifDAO.saveModifyPaymentDetails(paydetails);
	}



   public ArrayList showListOfPaymentDetails(PaymentDetails paydetails) throws DatabaseException
	{
		Log.log(Log.INFO, "IFProcessor", "showListOfPaymentDetails", "Displayed");

		ArrayList payDetailsArray = new ArrayList();

		try
		{
			payDetailsArray = ifDAO.showListOfPaymentDetails(paydetails);
		}
		catch(Exception e)
		{
			throw new DatabaseException(e.getMessage());
		}
		Log.log(Log.INFO, "IFProcessor", "showListOfPaymentDetails", "Exited");

		return payDetailsArray;
	}

	public PaymentDetails getPaymentDetails(String payId) throws DatabaseException
	{

		Log.log(Log.INFO, "IFProcessor", "getPaymentDetails", "updated");

		PaymentDetails paydetails = new PaymentDetails();
		try
		{
		paydetails = ifDAO.getPaymentDetails(payId);
		}
		catch(Exception e)
		{
			throw new DatabaseException(e.getMessage());
		}
		Log.log(Log.INFO, "IFProcessor", "getPaymentDetails", "Exited");

		return paydetails;
	}

	public Map showInvstMaturingDetails(Date date) throws DatabaseException
	{
		return ifDAO.showInvstMaturingDetails(date);
	}

	public double getBankAccountDetails(String bankName, String branchName, String accNumber) throws DatabaseException
	{
		return ifDAO.getBankAccountDetails(bankName, branchName, accNumber);
	}

	public ArrayList getInvDetails(String invRefNo) throws DatabaseException
	{
		 return ifDAO.getInvDetails(invRefNo);
	}

   public ArrayList getInvRefNosForFullfilment(String inst, String inv) throws DatabaseException
   {
		return ifDAO.getInvRefNosForFullfilment(inst, inv);
   }
   public ArrayList getAllHolidays() throws DatabaseException
   {
		return ifDAO.getAllHolidays();
   }

   public ArrayList getInvRefNosForTDS(String inst, String inv) throws DatabaseException
   {
		return ifDAO.getInvRefNosForTDS(inst, inv);
   }

   public MaturityWiseCeiling getMaturityWiseCeiling(String maturityType)throws DatabaseException
   {
		return ifDAO.getMaturityWiseCeiling(maturityType);
   }

   public InstrumentCategoryWiseCeiling getInstrumentWiseCeiling(String instrumentType)throws DatabaseException
   {
	   return ifDAO.getInstrumentWiseCeiling(instrumentType);
   }

   public InvesteeGroupWiseCeiling getIGroupWiseCeiling(String investeeGroupName)throws DatabaseException
   {
	   return ifDAO.getIGroupWiseCeiling(investeeGroupName);
   }

   public InvesteeWiseCeiling getInvesteeWiseCeiling(String investeeGroupName, String investeeName)throws DatabaseException
   {
	   return ifDAO.getInvesteeWiseCeiling(investeeGroupName,investeeName);
   }

   public RatingWiseCeiling getRatingWiseCeiling(String investeeGroupName, String investeeName, String insType)throws DatabaseException
   {
	   return ifDAO.getRatingWiseCeiling(investeeGroupName,investeeName,insType);
   }

   public ArrayList getFDReceiptNumbers()throws DatabaseException
   {
	   return ifDAO.getFDReceiptNumbers();
   }

   public InvestmentFulfillmentDetail getInvFullfilmentDetails(InvestmentFulfillmentDetail invFullfillment)throws DatabaseException
   {
	   return ifDAO.getInvFullfilmentDetails(invFullfillment);
   }

   public ArrayList getInvRefNosForBuySell(String inst, String inv) throws DatabaseException
	  {
		   return ifDAO.getInvRefNosForBuySell(inst, inv);
	  }

	public ArrayList getROI(Date referenceDate) throws DatabaseException
	{
		Log.log(Log.INFO,"IFProcessor","getROI","Entered");

		Hashtable rois=ifDAO.getROI(referenceDate);
		ArrayList returnableROIs=new ArrayList();
		Set keySet=rois.keySet();
		Iterator iterator=keySet.iterator();
		int totalDenominator=0;
		double totalNumerator=0;
		double	rateOfInterest=0;
		double 	mfNumerator=0;
		double	mfDenominator=0;
		DecimalFormat decimalFormat=new DecimalFormat("###.##");


		while(iterator.hasNext())
		{
			totalNumerator=0;
			rateOfInterest=0;
			totalDenominator=0;
			mfNumerator=0;
			mfDenominator=0;

			Object key=iterator.next();

			Log.log(Log.DEBUG,"IFProcessor","getROI","key "+key);

			ArrayList instruments=(ArrayList)rois.get(key);

			for(int i=0;i<instruments.size();i++)
			{
				ROIInfo roiInfo=(ROIInfo)instruments.get(i);


				if(roiInfo.getNoOfUnits()>0)
				{
					//MF
					mfNumerator+= roiInfo.getNoOfUnits()*roiInfo.getNavDifference();
					mfDenominator+=roiInfo.getPrincipalAmount()/roiInfo.getNoOfYears();

					Log.log(Log.DEBUG,"IFProcessor","getROI","mfNumerator,mfDenominator "+mfNumerator+", "+mfDenominator);
				}
				else
				{
					totalNumerator+=roiInfo.getPrincipalAmount()*roiInfo.getRateOfInterest()*
					roiInfo.getNoOfDays();

					totalDenominator+=roiInfo.getPrincipalAmount()*roiInfo.getNoOfDays();

					Log.log(Log.DEBUG,"IFProcessor","getROI","totalNumerator,totalDenominator "+totalNumerator+", "+totalDenominator);
				}


			}
			ROIInfo finalROIInfo=new ROIInfo();
			finalROIInfo.setInstrumentName((String)key);
			if(mfNumerator!=0)
			{
				//Mutual Fund
				rateOfInterest=mfNumerator/mfDenominator;
			}
			else
			{
				rateOfInterest=totalNumerator/totalDenominator;
			}

			finalROIInfo.setRateOfInterest(Double.parseDouble(decimalFormat.format(rateOfInterest)));

			Log.log(Log.DEBUG,"IFProcessor","getROI","rateOfInterest "+rateOfInterest);


			returnableROIs.add(finalROIInfo);
		}

		Log.log(Log.INFO,"IFProcessor","getROI","Exited");

		return returnableROIs;
	}
	
	public ExposureDetails getPositionDetails(Date sysDate, Date proposedDate) throws DatabaseException
	   {
			return ifDAO.getPositionDetails(sysDate, proposedDate);
	   }
	
	public ArrayList getInstrumentCategories() throws DatabaseException
	   {
			return ifDAO.getInstrumentCategories();
	   }

	public void saveInstrumentCategory(InstrumentCategory instCategory, String userId) throws DatabaseException
	   {
			ifDAO.saveInstrumentCategory(instCategory,userId);
	   }

	public InstrumentCategory getInstCategoryDetails(String instCatName) throws DatabaseException
	   {
		InstrumentCategory instCategory = ifDAO.getInstCategoryDetails(instCatName);
		return instCategory;
	   }
	
	public void addInstrumentCatWiseCeiling(InstrumentCategoryWiseCeiling instCat,String userId)throws DatabaseException
	{
		ifDAO.addInstrumentCatWiseCeiling(instCat,userId);
	}

	public ArrayList getTDSList(Date fromDate, Date toDate) throws DatabaseException
   {
	   return ifDAO.getTDSList(fromDate, toDate);
   }
   public TDSDetail getTDSDetails(String tdsId) throws DatabaseException
	  {
		  return ifDAO.getTDSDetails(tdsId);
	  }
	  
	public void insertMiscReceipts(Date date, Map receipts, String userId) throws DatabaseException
	{
		ifDAO.insertMiscReceipts(date, receipts, userId);
	}

	public Map getMiscReceiptsForDate(Date date) throws DatabaseException
   {
	   return ifDAO.getMiscReceiptsForDate(date);
   }
   
   public ArrayList applicationsForClaimProjection(Date filterDate)throws DatabaseException
   {
		ArrayList appsForProjection = ifDAO.applicationsForClaimProjection();
   		
		ArrayList tcApprovedAppsList = new ArrayList();
		ArrayList tcExpiredAppsList = new ArrayList();
		ArrayList wcApprovedAppsList = new ArrayList();
		ArrayList wcExpiredAppsList = new ArrayList();
		
		ArrayList applicationsList = new ArrayList();
   		
		Calendar calendar=Calendar.getInstance();
		
		AdminDAO adminDAO = new AdminDAO();
		ClaimsProcessor cpProcessor = new ClaimsProcessor(); 
		
		ParameterMaster param = adminDAO.getParameter();
		int claimLodgePeriod = param.getPeriodTenureExpiryLodgementClaims();  
		
		int lockinperiodmonths = param.getLockInPeriod();
		
		Date sysDate = new java.util.Date();
   		
			/**
			 * TC Approved Applications
			 */
			ArrayList tcApprovedApps = (ArrayList)appsForProjection.get(0);
			if(tcApprovedApps!=null && tcApprovedApps.size()!=0)
			{
				//Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","tc approved size :" + tcApprovedApps.size());
				for(int j=0; j<tcApprovedApps.size(); j++)
				{
					Application application = (Application)tcApprovedApps.get(j);
   				
					java.util.Date lckdtl_gstartdate = null;
					java.util.Date lckdtl_dtlastdsbrsmnt = null;
					java.util.Date lckdtl_lockin_start_date = null;
					java.util.Date tempdate = null;
					java.util.Date lckdtl_lockin_end_date = null;
				
					lckdtl_gstartdate = application.getGuaranteeStartDate();
					lckdtl_dtlastdsbrsmnt = application.getTermLoan().getFirstDisbursementDate();
   				
   				
					// checking if the approved application has expired
					int tenure = application.getTermLoan().getTenure();
   				
					calendar.setTime(application.getGuaranteeStartDate());
					calendar.add(Calendar.MONTH,tenure);
				
					Date tenureEndDate = calendar.getTime();
					if(tenureEndDate.before(sysDate))		// then the application has expired				
					{
						//Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","application has expired:");
						/**
						 * Check if the period of lodgment of claim after expiry has past over the parameter
						 */
						calendar.setTime(tenureEndDate);
						calendar.add(Calendar.MONTH,claimLodgePeriod);
					
						Date expiryEndDate = calendar.getTime();
						/**
						 * check if the validity including the period of lodgment
						 * is after the generation date
						 */
						if(expiryEndDate.after(sysDate))
						{
							/**
							 * Check if the lock in period falls before the 
							 */
							if((lckdtl_gstartdate != null) && (lckdtl_dtlastdsbrsmnt != null))
							{
								 if((lckdtl_gstartdate.compareTo(lckdtl_dtlastdsbrsmnt)) < 0)
								 {
									   tempdate = lckdtl_dtlastdsbrsmnt;
								 }
								 if((lckdtl_gstartdate.compareTo(lckdtl_dtlastdsbrsmnt)) > 0)
								 {
									   tempdate = lckdtl_gstartdate;
								 }
								 if((lckdtl_gstartdate.compareTo(lckdtl_dtlastdsbrsmnt)) == 0)
								 {
									   tempdate = lckdtl_gstartdate;
								 }
							 }
							 else if((lckdtl_gstartdate != null) && (lckdtl_dtlastdsbrsmnt == null))
							 {
								 tempdate = lckdtl_gstartdate;
							 }
							lckdtl_lockin_end_date = cpProcessor.getDate(tempdate,lockinperiodmonths);
							if(lckdtl_lockin_end_date.before(filterDate))
							{
								Calendar instance1 = Calendar.getInstance();
								Calendar instance2 = Calendar.getInstance();
							
								instance1.setTime(lckdtl_lockin_end_date);
								instance2.setTime(filterDate);
								long monthDiff = DateHelper.getMonthDifference(instance1,instance2);
								if(monthDiff > 1)
								{
									instance1.add(Calendar.MONTH,1);
									application.setApprovedDate(instance1.getTime());
									application.setSubmittedDate(tempdate);
									application.setGuaranteeStartDate(expiryEndDate);
									
									tcExpiredAppsList.add(application);
								}
							
							}
						
						}
					}
					else{
					
						/**
						 * Jus check if the lock in period end date is before the filter date
						 */
						//Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","application has not expired:");
					
						if((lckdtl_gstartdate != null) && (lckdtl_dtlastdsbrsmnt != null))
						{
							 if((lckdtl_gstartdate.compareTo(lckdtl_dtlastdsbrsmnt)) < 0)
							 {
								   tempdate = lckdtl_dtlastdsbrsmnt;
							 }
							 if((lckdtl_gstartdate.compareTo(lckdtl_dtlastdsbrsmnt)) > 0)
							 {
								   tempdate = lckdtl_gstartdate;
							 }
							 if((lckdtl_gstartdate.compareTo(lckdtl_dtlastdsbrsmnt)) == 0)
							 {
								   tempdate = lckdtl_gstartdate;
							 }
						 }
						 else if((lckdtl_gstartdate != null) && (lckdtl_dtlastdsbrsmnt == null))
						 {
							 tempdate = lckdtl_gstartdate;
						 }
						lckdtl_lockin_end_date = cpProcessor.getDate(tempdate,lockinperiodmonths);
						
						//Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","lckdtl_lockin_end_date :" + lckdtl_lockin_end_date);
						if(lckdtl_lockin_end_date.compareTo(filterDate) < 0)
						{
							//Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","lckdtl_lockin_end_date.compareTo(filterDate)");							
							Calendar instance1 = Calendar.getInstance();
							Calendar instance2 = Calendar.getInstance();
							
							instance1.setTime(lckdtl_lockin_end_date);
							instance2.setTime(filterDate);
							long monthDiff = DateHelper.getMonthDifference(instance1,instance2);
							if(monthDiff > 1)
							{
								//Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","instance1.get(Calendar.MONTH) < instance2.get(Calendar.MONTH)");
								instance1.add(Calendar.MONTH,1);
								application.setApprovedDate(instance1.getTime());
								application.setSubmittedDate(tempdate);

								tcApprovedAppsList.add(application);
							}
							
						}
					
					}
   				
				}	// end of TC Approved Applications
   				
			}
   			
			/**
			 * TC Expired Applications 
			 */
			ArrayList tcExpiredApps = (ArrayList)appsForProjection.get(1);
			if(tcExpiredApps!=null && tcExpiredApps.size()!=0)
			{
				for(int k=0; k<tcExpiredApps.size(); k++)
				{
					Application application = (Application)tcExpiredApps.get(k);
   				
					java.util.Date lckdtl_gstartdate = null;
					java.util.Date lckdtl_dtlastdsbrsmnt = null;
					java.util.Date lckdtl_lockin_start_date = null;
					java.util.Date tempdate = null;
					java.util.Date lckdtl_lockin_end_date = null;
   				
					lckdtl_gstartdate = application.getGuaranteeStartDate();
					lckdtl_dtlastdsbrsmnt = application.getTermLoan().getFirstDisbursementDate();
   				
					int tenure = application.getTermLoan().getTenure();
   				
					calendar.setTime(application.getGuaranteeStartDate());
					calendar.add(Calendar.MONTH,tenure);
				
					Date tenureEndDate = calendar.getTime();
   				
   				
					calendar.setTime(tenureEndDate);
					calendar.add(Calendar.MONTH,claimLodgePeriod);
					
					Date expiryEndDate = calendar.getTime();
					/**
					 * check if the validity including the period of lodgment is after the generation date
					 */
					if(expiryEndDate.after(sysDate))
					{
						/**
						 * Check if the lock in period falls before the 
						 */
						if((lckdtl_gstartdate != null) && (lckdtl_dtlastdsbrsmnt != null))
						{
							 if((lckdtl_gstartdate.compareTo(lckdtl_dtlastdsbrsmnt)) < 0)
							 {
								   tempdate = lckdtl_dtlastdsbrsmnt;
							 }
							 if((lckdtl_gstartdate.compareTo(lckdtl_dtlastdsbrsmnt)) > 0)
							 {
								   tempdate = lckdtl_gstartdate;
							 }
							 if((lckdtl_gstartdate.compareTo(lckdtl_dtlastdsbrsmnt)) == 0)
							 {
								   tempdate = lckdtl_gstartdate;
							 }
						 }
						 else if((lckdtl_gstartdate != null) && (lckdtl_dtlastdsbrsmnt == null))
						 {
							 tempdate = lckdtl_gstartdate;
						 }
						lckdtl_lockin_end_date = cpProcessor.getDate(tempdate,lockinperiodmonths);
						/**
						 * check if the L.I.P end date is before the filter date
						 */
						if(lckdtl_lockin_end_date.compareTo(filterDate) < 0)
						{
							Calendar instance1 = Calendar.getInstance();
							Calendar instance2 = Calendar.getInstance();
							
							instance1.setTime(lckdtl_lockin_end_date);
							instance2.setTime(filterDate);
							long monthDiff = DateHelper.getMonthDifference(instance1,instance2);
							if(monthDiff > 1)

							{
								instance1.add(Calendar.MONTH,1);
								application.setApprovedDate(instance1.getTime());
								application.setSubmittedDate(tempdate);
								application.setGuaranteeStartDate(expiryEndDate);

								tcExpiredAppsList.add(application);
							}
						}
					}
				}	//end of TC Expired Applications
				
			}
   			
			ArrayList wcApprovedApps = (ArrayList)appsForProjection.get(2);
			if(wcApprovedApps!=null && wcApprovedApps.size()!=0)
			{
				for(int a=0; a<wcApprovedApps.size(); a++)
				{
					Application application = (Application)wcApprovedApps.get(a);
				
					java.util.Date lckdtl_gstartdate = null;

					java.util.Date lckdtl_lockin_start_date = null;
					java.util.Date tempdate = null;
					java.util.Date lckdtl_lockin_end_date = null;
				
					lckdtl_gstartdate = application.getGuaranteeStartDate();				
				
					int tenure = application.getWc().getWcTenure();
   				
					calendar.setTime(application.getGuaranteeStartDate());
					calendar.add(Calendar.MONTH,tenure);
				
					Date tenureEndDate = calendar.getTime();
					if(tenureEndDate.before(sysDate))		// then the application has expired				
					{
						/**
						 * Check if the period of lodgment of claim after expiry has past over the parameter
						 */
						calendar.setTime(tenureEndDate);
						calendar.add(Calendar.MONTH,claimLodgePeriod);
					
						Date expiryEndDate = calendar.getTime();
						if(expiryEndDate.after(sysDate))
						{
							/**
							 * Check if the lock in period falls before the 
							 */
							tempdate = lckdtl_gstartdate;
						
							lckdtl_lockin_end_date = cpProcessor.getDate(tempdate,lockinperiodmonths);
						
							if(lckdtl_lockin_end_date.compareTo(filterDate) < 0)
							{
								Calendar instance1 = Calendar.getInstance();
								Calendar instance2 = Calendar.getInstance();
							
								instance1.setTime(lckdtl_lockin_end_date);
								instance2.setTime(filterDate);
								long monthDiff = DateHelper.getMonthDifference(instance1,instance2);
								if(monthDiff > 1)

								{
									instance1.add(Calendar.MONTH,1);
									application.setApprovedDate(instance1.getTime());
									application.setSubmittedDate(tempdate);
									application.setGuaranteeStartDate(expiryEndDate);
									
									wcExpiredAppsList.add(application);
								}
							
							}
						
						}
					}
					else{
					
						/**
						 * Jus check if the lock in period end date is before the filter date
						 */
					
						tempdate = lckdtl_gstartdate;
						
						lckdtl_lockin_end_date = cpProcessor.getDate(tempdate,lockinperiodmonths);
						
						if(lckdtl_lockin_end_date.compareTo(filterDate) < 0)
						{
							Calendar instance1 = Calendar.getInstance();
							Calendar instance2 = Calendar.getInstance();
							
							instance1.setTime(lckdtl_lockin_end_date);
							instance2.setTime(filterDate);
							long monthDiff = DateHelper.getMonthDifference(instance1,instance2);
							if(monthDiff > 1)

							{
								instance1.add(Calendar.MONTH,1);
								application.setApprovedDate(instance1.getTime());
								application.setSubmittedDate(tempdate);
								
								wcApprovedAppsList.add(application);
							}
							
						}
					
					}
				}	//end of WC Approved Applications
				
			}
			
			ArrayList wcExpiredApps = (ArrayList)appsForProjection.get(3);
			if(wcExpiredApps!=null && wcExpiredApps.size()!=0)
			{
				for(int b=0; b<wcExpiredApps.size(); b++)
				{
					Application application = (Application)wcExpiredApps.get(b);
				
					java.util.Date lckdtl_gstartdate = null;

					java.util.Date lckdtl_lockin_start_date = null;
					java.util.Date tempdate = null;
					java.util.Date lckdtl_lockin_end_date = null;
				
					lckdtl_gstartdate = application.getGuaranteeStartDate();				
				
					int tenure = application.getWc().getWcTenure();
   				
					calendar.setTime(application.getGuaranteeStartDate());
					calendar.add(Calendar.MONTH,tenure);
				
					Date tenureEndDate = calendar.getTime();
					if(tenureEndDate.before(sysDate))		// then the application has expired				
					{
						/**
						 * Check if the period of lodgment of claim after expiry has past over the parameter
						 */
						calendar.setTime(tenureEndDate);
						calendar.add(Calendar.MONTH,claimLodgePeriod);
					
						Date expiryEndDate = calendar.getTime();
						if(expiryEndDate.after(sysDate))
						{
							/**
							 * Check if the lock in period falls before the 
							 */
							tempdate = lckdtl_gstartdate;
						
							lckdtl_lockin_end_date = cpProcessor.getDate(tempdate,lockinperiodmonths);
						
							if(lckdtl_lockin_end_date.compareTo(filterDate) < 0)
							{
								Calendar instance1 = Calendar.getInstance();
								Calendar instance2 = Calendar.getInstance();
							
								instance1.setTime(lckdtl_lockin_end_date);
								instance2.setTime(filterDate);
								long monthDiff = DateHelper.getMonthDifference(instance1,instance2);
								if(monthDiff > 1)

								{
									instance1.add(Calendar.MONTH,1);
									application.setApprovedDate(instance1.getTime());
									application.setSubmittedDate(tempdate);
									application.setGuaranteeStartDate(expiryEndDate);

									wcExpiredAppsList.add(application);
								}
							
							}
						
						}
					}
					else{
					
						/**
						 * Jus check if the lock in period end date is before the filter date
						 */
					
						tempdate = lckdtl_gstartdate;
						
						lckdtl_lockin_end_date = cpProcessor.getDate(tempdate,lockinperiodmonths);
						
						if(lckdtl_lockin_end_date.compareTo(filterDate) < 0)
						{
							Calendar instance1 = Calendar.getInstance();
							Calendar instance2 = Calendar.getInstance();
							
							instance1.setTime(lckdtl_lockin_end_date);
							instance2.setTime(filterDate);
							long monthDiff = DateHelper.getMonthDifference(instance1,instance2);
							if(monthDiff > 1)

							{
								instance1.add(Calendar.MONTH,1);
								application.setApprovedDate(instance1.getTime());
								application.setSubmittedDate(tempdate);

								wcApprovedAppsList.add(application);
							}
						}
					}
				}	//end of WC expired Applications			
				
			}
			
	//Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","tcApprovedAppsList :" + tcApprovedAppsList.size());
		applicationsList.add(tcApprovedAppsList);
		applicationsList.add(tcExpiredAppsList);

		applicationsList.add(wcApprovedAppsList);
		applicationsList.add(wcExpiredAppsList);
   		
	return applicationsList;	
   }
   
   public ArrayList getApplicationsForProjection(Date filterDate)throws DatabaseException
   {
		ArrayList applicationsListForClaim = applicationsForClaimProjection(filterDate);
		
		AdminDAO adminDAO = new AdminDAO();
		
		ParameterMaster param = adminDAO.getParameter();
		int lockinperiodmonths = param.getLockInPeriod();
		double lockinperiodmonthsDouble = new Integer(lockinperiodmonths).doubleValue();
		
		Date sysDate = new java.util.Date();
		
		TreeMap dateMap = new TreeMap();
		ArrayList amounts = new ArrayList();
		ArrayList totalList = new ArrayList();
		
		ArrayList mnthYears = new ArrayList();
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
	/**
	 * For TC Approved Applications
	 */
	ArrayList tcApprovedAppsList = (ArrayList)applicationsListForClaim.get(0);
		
	Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","tc approved final list size :" + tcApprovedAppsList.size());
	for(int i=0; i<tcApprovedAppsList.size(); i++)
	{
		sysDate = new java.util.Date();
			
		Application application = (Application)tcApprovedAppsList.get(i);
		double approvedAmt = application.getApprovedAmount();
			
		// get the tenure
		int tenure = application.getTermLoan().getTenure();
		double doubleTenure = (new Integer(tenure)).doubleValue();
			
			
		double distAmount = 0;
			
		if(approvedAmt <= 200000)
		{
			distAmount = (.8) * approvedAmt;
		}
		else{
			distAmount = (.2) * approvedAmt;
		}

		double distPerMonth = distAmount / doubleTenure;
			
		Date lockInPeriodStartDate = application.getSubmittedDate();
			
		/**
		 * Find the difference of months b/w Lock in Period Start Date and Generation date			 * 
		 */

		Calendar date1 = Calendar.getInstance();
		Calendar date2 = Calendar.getInstance();
		date1.setTime(lockInPeriodStartDate);
		date2.setTime(sysDate);
		long monthDiff = DateHelper.getMonthDifference(date1,date2);
		if(monthDiff!=0)
		{
			monthDiff = monthDiff-1;
		}
			
			
		double monthDiffDouble = new Long(monthDiff).doubleValue(); 
			
		int mnthDiff = new Long(monthDiff).intValue();
			
		/**
		 * Find the difference of months b/w Lock in Period Start Date and filter date
		 */
			
		date1.setTime(lockInPeriodStartDate);
		date2.setTime(filterDate);
		long filterMonthDiff = DateHelper.getMonthDifference(date1,date2);
		if(filterMonthDiff!=0)
		{
			filterMonthDiff = filterMonthDiff-1;
		}
			

		int filterMnthDiff = new Long(filterMonthDiff).intValue();
			
		double intitalDistAmt = distPerMonth * lockinperiodmonthsDouble;
			
		double initialAmount = 0;
			
		double totalAmount = 0;
			
		Calendar currentDate = Calendar.getInstance();
		currentDate.setTime(sysDate);
		
			                                                                                                                                  
		/**
		 * The lock in period end date ends before the generation date
		 */
		if(mnthDiff >lockinperiodmonths)
		{
			//Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","monthDiff >lockinperiodmonths");
			/**
			 * if the month difference is less than 30
			 */
				if(monthDiff < 30)
				{						
					/**
					 * if the filter date difference is less than 30
					 */
					if(filterMonthDiff <= 30)
					{
						//Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","filterMonthDiff <= 30");
						currentDate = Calendar.getInstance();
						currentDate.setTime(sysDate);
							
						for(int j=mnthDiff; j<=filterMnthDiff; j++)
						{
							totalAmount = 0;
								
							if(j==mnthDiff)
							{
								initialAmount = (lockinperiodmonthsDouble * distPerMonth ) + ((monthDiffDouble - lockinperiodmonthsDouble) * distPerMonth);
							}
							else{
									
								initialAmount = initialAmount + distPerMonth;
							}
							int month = currentDate.get(Calendar.MONTH);
							String monthString = getMonth(month);
							int year = currentDate.get(Calendar.YEAR);
									
							String monthYear = monthString + "-" + year;
								
							if(dateMap.containsKey(monthYear))
							{
								totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
								
							}
								
							totalAmount = totalAmount + initialAmount;

							if(!mnthYears.contains(monthYear))
							{
								mnthYears.add(monthYear);
							}
							dateMap.put(monthYear,new Double(totalAmount));
							
							currentDate.add(Calendar.MONTH,1);							
								
								
						}
							
					}
					else if (filterMonthDiff > 30)
					{							
						currentDate = Calendar.getInstance();
						currentDate.setTime(sysDate);
							
						for(int k=mnthDiff; k<=30; k++)
						{
							totalAmount = 0;							
								if(k==mnthDiff)
								{
									initialAmount = (lockinperiodmonthsDouble * distPerMonth ) + ((monthDiffDouble - lockinperiodmonthsDouble) * distPerMonth);
								}
								else{
									initialAmount = initialAmount + distPerMonth;
								}
									
									
							int month = currentDate.get(Calendar.MONTH);
							String monthString = getMonth(month);
							int year = currentDate.get(Calendar.YEAR);
									
							String monthYear = monthString + "-" + year;
								
							if(dateMap.containsKey(monthYear))
							{
								totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();

							}
								
							totalAmount = totalAmount + initialAmount;


							if(!mnthYears.contains(monthYear))
							{
								mnthYears.add(monthYear);
							}
								
							dateMap.put(monthYear,new Double(totalAmount));
							
							currentDate.add(Calendar.MONTH,1);							
									
						}
						for(int l=31; l<=filterMonthDiff; l++)
						{
							totalAmount = 0;
							initialAmount = distPerMonth;
								
							int month = currentDate.get(Calendar.MONTH);
							String monthString = getMonth(month);
							int year = currentDate.get(Calendar.YEAR);
									
							String monthYear = monthString + "-" + year;
								
							if(dateMap.containsKey(monthYear))
							{
								totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
								
							}
								
							totalAmount = totalAmount + initialAmount;

							if(!mnthYears.contains(monthYear))
							{
								mnthYears.add(monthYear);
							}
								
							dateMap.put(monthYear,new Double(totalAmount));
							
							currentDate.add(Calendar.MONTH,1);							
								
						}
														
					}
				}
				else if(monthDiff > 30)
				{
					if(filterMonthDiff > 30)
					{
						for(int a=mnthDiff; a<=filterMonthDiff; a++)
						{		
							totalAmount = 0;
														
							initialAmount = distPerMonth;
								
							int month = currentDate.get(Calendar.MONTH);
							String monthString = getMonth(month);
							int year = currentDate.get(Calendar.YEAR);
									
							String monthYear = monthString + "-" + year;
								
							if(dateMap.containsKey(monthYear))
							{
								totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
								
							}
								
							totalAmount = totalAmount + initialAmount;

							if(!mnthYears.contains(monthYear))
							{
								mnthYears.add(monthYear);
							}
								
							dateMap.put(monthYear,new Double(totalAmount));
							
							currentDate.add(Calendar.MONTH,1);							
								
						}
					}
				}
		}
		/**
		 * the lock in period ends after the sysdate
		 */
		else if(monthDiff < lockinperiodmonths)
		{				
			if(filterMonthDiff <= 30)
			{
				for(int c=mnthDiff; c<=lockinperiodmonths; c++ )
				{
					totalAmount = 0;
						
					initialAmount = 0;		
						
					int month = currentDate.get(Calendar.MONTH);
					String monthString = getMonth(month);
					int year = currentDate.get(Calendar.YEAR);
									
					String monthYear = monthString + "-" + year;
								
					if(dateMap.containsKey(monthYear))
					{
						totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
								
					}
								
					totalAmount = totalAmount + initialAmount;

					if(!mnthYears.contains(monthYear))
					{
						mnthYears.add(monthYear);
					}
						
					dateMap.put(monthYear,new Double(totalAmount));
							
					currentDate.add(Calendar.MONTH,1);							

				}			
				for(int e=lockinperiodmonths + 1; e<=filterMonthDiff; e++)
				{
					totalAmount = 0;
						
					if(e==lockinperiodmonths + 1)
					{
						initialAmount = (lockinperiodmonthsDouble * distPerMonth ) +  distPerMonth;
					}
					else{
						initialAmount = initialAmount + distPerMonth;
					}
					int month = currentDate.get(Calendar.MONTH);
					String monthString = getMonth(month);
					int year = currentDate.get(Calendar.YEAR);
									
					String monthYear = monthString + "-" + year;
								
					if(dateMap.containsKey(monthYear))
					{
						totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
					}
								
					totalAmount = totalAmount + initialAmount;

					if(!mnthYears.contains(monthYear))
					{
						mnthYears.add(monthYear);
					}
						
					dateMap.put(monthYear,new Double(totalAmount));
							
					currentDate.add(Calendar.MONTH,1);							
						
				}		
			}
			else if(filterMonthDiff > 30)
			{					
				if(lockinperiodmonths <= 30)
				{				
					for(int k=mnthDiff; k<=lockinperiodmonths; k++)
					{
						totalAmount = 0;
							
						initialAmount = 0;
							
						int month = currentDate.get(Calendar.MONTH);
						String monthString = getMonth(month);
						int year = currentDate.get(Calendar.YEAR);
									
						String monthYear = monthString + "-" + year;
								
						if(dateMap.containsKey(monthYear))
						{
							totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
						}
								
						totalAmount = totalAmount + initialAmount;

						if(!mnthYears.contains(monthYear))
						{
							mnthYears.add(monthYear);
						}
							
						dateMap.put(monthYear,new Double(totalAmount));
							
						currentDate.add(Calendar.MONTH,1);							
							
					}
					for(int k=lockinperiodmonths +1; k<=30; k++)
					{
						totalAmount = 0;
							
						if(k==lockinperiodmonths + 1)
						{
							initialAmount = (lockinperiodmonthsDouble * distPerMonth ) + distPerMonth;
						}
						else{
							initialAmount = initialAmount + distPerMonth;
						}
						int month = currentDate.get(Calendar.MONTH);
						String monthString = getMonth(month);
						int year = currentDate.get(Calendar.YEAR);
									
						String monthYear = monthString + "-" + year;
								
						if(dateMap.containsKey(monthYear))
						{
							totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();

						}
								
						totalAmount = totalAmount + initialAmount;

						if(!mnthYears.contains(monthYear))
						{
							mnthYears.add(monthYear);
						}
							
						dateMap.put(monthYear,new Double(totalAmount));
							
						currentDate.add(Calendar.MONTH,1);							
							
					}
					for(int k=31; k<=filterMonthDiff; k++)
					{
						totalAmount = 0;
							
						initialAmount = distPerMonth;
							
						int month = currentDate.get(Calendar.MONTH);
						String monthString = getMonth(month);
						int year = currentDate.get(Calendar.YEAR);
									
						String monthYear = monthString + "-" + year;
								

						if(dateMap.containsKey(monthYear))
						{
							totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();

								
						}
								
						totalAmount = totalAmount + initialAmount;

						if(!mnthYears.contains(monthYear))
						{
							mnthYears.add(monthYear);
						}
							
						dateMap.put(monthYear,new Double(totalAmount));
							
						currentDate.add(Calendar.MONTH,1);							
							
					}
				}
				else if(lockinperiodmonths > 30)
				{
					for(int k=mnthDiff; k<=lockinperiodmonths; k++)
					{
						totalAmount = 0;
							
						initialAmount=0;
							
						int month = currentDate.get(Calendar.MONTH);
						String monthString = getMonth(month);
						int year = currentDate.get(Calendar.YEAR);
									
						String monthYear = monthString + "-" + year;
								
						if(dateMap.containsKey(monthYear))
						{
							totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
								
						}
								
						totalAmount = totalAmount + initialAmount;

						if(!mnthYears.contains(monthYear))
						{
							mnthYears.add(monthYear);
						}
							
						dateMap.put(monthYear,new Double(totalAmount));
							
						currentDate.add(Calendar.MONTH,1);							
							
					}
					for(int k=lockinperiodmonths+1; k<=filterMonthDiff; k++)
					{
						totalAmount = 0;
							
						initialAmount= distPerMonth;
							
						int month = currentDate.get(Calendar.MONTH);
						String monthString = getMonth(month);
						int year = currentDate.get(Calendar.YEAR);
									
						String monthYear = monthString + "-" + year;
								
						if(dateMap.containsKey(monthYear))
						{
							totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
								
						}
								
						totalAmount = totalAmount + initialAmount;


						if(!mnthYears.contains(monthYear))
						{
							mnthYears.add(monthYear);
						}
							
						dateMap.put(monthYear,new Double(totalAmount));
							
						currentDate.add(Calendar.MONTH,1);							
							
					}
						
				}
			}
		}
			
	}
	
		
	/**
	 * For WC Approved Applications
	 */
	ArrayList wcApprovedAppsList = (ArrayList)applicationsListForClaim.get(2);
			
	for(int i=0; i<wcApprovedAppsList.size(); i++)
	{
		sysDate = new java.util.Date();
				
		Application application = (Application)wcApprovedAppsList.get(i);
		double approvedAmt = application.getApprovedAmount();
				
		// get the tenure
		int tenure = application.getWc().getWcTenure();
		double doubleTenure = (new Integer(tenure)).doubleValue();
				
		Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","doubleTenure :" + doubleTenure);
						
		double distAmount = 0;
				
		if(approvedAmt <= 200000)
		{
			distAmount = (.8) * approvedAmt;
		}
		else{
			distAmount = (.2) * approvedAmt;
		}
	
		double distPerMonth = distAmount / doubleTenure;
		
		Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","distPerMonth :" + distPerMonth);
				
		Date lockInPeriodStartDate = application.getSubmittedDate();
				
		/**
		 * Find the difference of months b/w Lock in Period Start Date and Generation date			 * 
		 */
	
		Calendar date1 = Calendar.getInstance();
		Calendar date2 = Calendar.getInstance();
		date1.setTime(lockInPeriodStartDate);
		date2.setTime(sysDate);
		long monthDiff = DateHelper.getMonthDifference(date1,date2);
		if(monthDiff!=0)
		{
			monthDiff = monthDiff-1;
		}
				
		double monthDiffDouble = new Long(monthDiff).doubleValue(); 
				
		int mnthDiff = new Long(monthDiff).intValue();
				
		/**
		 * Find the difference of months b/w Lock in Period Start Date and filter date
		 */
				
		date1.setTime(lockInPeriodStartDate);
		date2.setTime(filterDate);
		long filterMonthDiff = DateHelper.getMonthDifference(date1,date2);
		if(filterMonthDiff!=0)
		{
			filterMonthDiff = filterMonthDiff-1;
		}
				
	
		int filterMnthDiff = new Long(filterMonthDiff).intValue();
				
		double intitalDistAmt = distPerMonth * lockinperiodmonthsDouble;
				
		double initialAmount = 0;
			
		double totalAmount = 0;
				
		Calendar currentDate = Calendar.getInstance();
		currentDate.setTime(sysDate);
			
		Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","cgpan:" + application.getCgpan());	                                                                                                                                  
		/**
		 * The lock in period end date ends before the generation date
		 */
		if(mnthDiff >lockinperiodmonths)
		{			
			/**
			 * if the month difference is less than 30
			 */
				if(monthDiff < 30)
				{					
					/**
					 * if the filter date difference is less than 30
					 */
					if(filterMonthDiff <= 30)
					{				
						currentDate = Calendar.getInstance();
						currentDate.setTime(sysDate);
								
						for(int j=mnthDiff; j<=filterMnthDiff; j++)
						{			
							totalAmount = 0;
									
							if(j==mnthDiff)
							{
								initialAmount = (lockinperiodmonthsDouble * distPerMonth ) + ((monthDiffDouble - lockinperiodmonthsDouble) * distPerMonth);
							}
							else{
										
								initialAmount = initialAmount + distPerMonth;
							}
								
							int month = currentDate.get(Calendar.MONTH);
							String monthString = getMonth(month);
							int year = currentDate.get(Calendar.YEAR);
										
							String monthYear = monthString + "-" + year;
									
								
							if(dateMap.containsKey(monthYear))
							{
								totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();								
									
							}
									
							totalAmount = totalAmount + initialAmount;
	
							if(!mnthYears.contains(monthYear))
							{
								mnthYears.add(monthYear);
							}
								
							dateMap.put(monthYear,new Double(totalAmount));
							
							if(totalAmount>=0)
							{
								
							}
							else{
								
								Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","totalAmount :" + totalAmount);
								
								System.out.println("cgpan :" + application.getCgpan());
								
							}
							
							
								
							currentDate.add(Calendar.MONTH,1);							
									
									
						}
								
					}
					else if (filterMonthDiff > 30)
					{
						currentDate = Calendar.getInstance();
						currentDate.setTime(sysDate);
								
						for(int k=mnthDiff; k<=30; k++)
						{
							totalAmount = 0;
								
								if(k==mnthDiff)
								{
									initialAmount = (lockinperiodmonthsDouble * distPerMonth ) + ((monthDiffDouble - lockinperiodmonthsDouble) * distPerMonth);
								}
								else{
									initialAmount = initialAmount + distPerMonth;
								}
										
							int month = currentDate.get(Calendar.MONTH);
							String monthString = getMonth(month);
							int year = currentDate.get(Calendar.YEAR);
										
							String monthYear = monthString + "-" + year;
									
							if(dateMap.containsKey(monthYear))
							{
								totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
							}
									
							totalAmount = totalAmount + initialAmount;
	
							if(!mnthYears.contains(monthYear))
							{
								mnthYears.add(monthYear);
							}
								
							dateMap.put(monthYear,new Double(totalAmount));
							
							Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","totalAmount1 :" + totalAmount);
								
							currentDate.add(Calendar.MONTH,1);							
										
						}
						for(int l=31; l<=filterMonthDiff; l++)
						{
							totalAmount = 0;
								
							initialAmount = distPerMonth;
									
							int month = currentDate.get(Calendar.MONTH);
							String monthString = getMonth(month);
							int year = currentDate.get(Calendar.YEAR);
										
							String monthYear = monthString + "-" + year;
									
							if(dateMap.containsKey(monthYear))
							{
								totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();								
									
							}
									
							totalAmount = totalAmount + initialAmount;
								
							if(!mnthYears.contains(monthYear))
							{
								mnthYears.add(monthYear);
							}
								
							dateMap.put(monthYear,new Double(totalAmount));
							
							Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","totalAmount 2:" + totalAmount);
								
							currentDate.add(Calendar.MONTH,1);							
									
						}
															
					}
				}
				else if(monthDiff > 30)
				{
	
					if(filterMonthDiff > 30)
					{
	
						for(int a=mnthDiff; a<=filterMonthDiff; a++)
						{			
							totalAmount = 0;
													
							initialAmount = distPerMonth;
									
							int month = currentDate.get(Calendar.MONTH);
							String monthString = getMonth(month);
							int year = currentDate.get(Calendar.YEAR);
										
							String monthYear = monthString + "-" + year;
									
							if(dateMap.containsKey(monthYear))
							{
								totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
							}
									
							totalAmount = totalAmount + initialAmount;
	
							if(!mnthYears.contains(monthYear))
							{
								mnthYears.add(monthYear);
							}
								
							dateMap.put(monthYear,new Double(totalAmount));
							
							Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","totalAmount 3:" + totalAmount);
								
							currentDate.add(Calendar.MONTH,1);							
									
						}
					}
				}
		}
		/**
		 * the lock in period ends after the sysdate
		 */
		else if(monthDiff < lockinperiodmonths)
		{			
			if(filterMonthDiff <= 30)
			{
				for(int c=mnthDiff; c<=lockinperiodmonths; c++ )
				{
					totalAmount = 0;
						
					initialAmount = 0;		
							
					int month = currentDate.get(Calendar.MONTH);
					String monthString = getMonth(month);
					int year = currentDate.get(Calendar.YEAR);
										
					String monthYear = monthString + "-" + year;
									
					if(dateMap.containsKey(monthYear))
					{
						totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
					}
									
					totalAmount = totalAmount + initialAmount;
	
					if(!mnthYears.contains(monthYear))
					{
						mnthYears.add(monthYear);
					}
						
					dateMap.put(monthYear,new Double(totalAmount));
					
					Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","totalAmount 4:" + totalAmount);
								
					currentDate.add(Calendar.MONTH,1);							
	
				}			
				for(int e=lockinperiodmonths + 1; e<=filterMonthDiff; e++)
				{
					totalAmount = 0;
						
					if(e==lockinperiodmonths + 1)
					{
						initialAmount = (lockinperiodmonthsDouble * distPerMonth )+  distPerMonth;
					}
					else{
						initialAmount = initialAmount + distPerMonth;
					}
					int month = currentDate.get(Calendar.MONTH);
					String monthString = getMonth(month);
					int year = currentDate.get(Calendar.YEAR);
										
					String monthYear = monthString + "-" + year;
									
					if(dateMap.containsKey(monthYear))
					{
						totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
									
					}
									
					totalAmount = totalAmount + initialAmount;
	
					if(!mnthYears.contains(monthYear))
					{
						mnthYears.add(monthYear);
					}
						
					dateMap.put(monthYear,new Double(totalAmount));
					
					Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","totalAmount 5:" + totalAmount);
								
					currentDate.add(Calendar.MONTH,1);							
							
				}		
			}
			else if(filterMonthDiff > 30)
			{				
				if(lockinperiodmonths <= 30)
				{
					for(int k=mnthDiff; k<=lockinperiodmonths; k++)
					{
						totalAmount = 0;
							
						initialAmount = 0;
								
						int month = currentDate.get(Calendar.MONTH);
						String monthString = getMonth(month);
						int year = currentDate.get(Calendar.YEAR);
										
						String monthYear = monthString + "-" + year;
									
						//Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","initialAmount :" + initialAmount);
						if(dateMap.containsKey(monthYear))
						{
							totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
									
						}
									
						totalAmount = totalAmount + initialAmount;
	
						if(!mnthYears.contains(monthYear))
						{
							mnthYears.add(monthYear);
						}
							
						dateMap.put(monthYear,new Double(totalAmount));

						Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","totalAmount 6:" + totalAmount);
														
						currentDate.add(Calendar.MONTH,1);							
								
					}
					for(int k=lockinperiodmonths +1; k<=30; k++)
					{
						totalAmount = 0;
						
						Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","lockinperiodmonthsDoublet 7:" + lockinperiodmonthsDouble);
						Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","distPerMonth 7:" + distPerMonth);
						
							
						if(k==lockinperiodmonths + 1)
						{
							initialAmount = (lockinperiodmonthsDouble * distPerMonth ) + distPerMonth;
						}
						else{
							initialAmount = initialAmount + distPerMonth;
						}
						Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","initialAmount 7:" + initialAmount);
						int month = currentDate.get(Calendar.MONTH);
						String monthString = getMonth(month);
						int year = currentDate.get(Calendar.YEAR);
										
						String monthYear = monthString + "-" + year;
									
						//Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","initialAmount :" + initialAmount);
						if(dateMap.containsKey(monthYear))
						{
							totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
									
						}
									
						totalAmount = totalAmount + initialAmount;
	
						if(!mnthYears.contains(monthYear))
						{
							mnthYears.add(monthYear);
						}
							
						dateMap.put(monthYear,new Double(totalAmount));
						
						Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","totalAmount 7:" + totalAmount);
								
						currentDate.add(Calendar.MONTH,1);							
								
					}
					for(int k=31; k<=filterMonthDiff; k++)
					{
						totalAmount = 0;
							
						initialAmount = distPerMonth;
								
						int month = currentDate.get(Calendar.MONTH);
						String monthString = getMonth(month);
						int year = currentDate.get(Calendar.YEAR);
										
						String monthYear = monthString + "-" + year;
									
						//Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","initialAmount :" + initialAmount);
						if(dateMap.containsKey(monthYear))
						{
							totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
									
						}
									
						totalAmount = totalAmount + initialAmount;
	
						if(!mnthYears.contains(monthYear))
						{
							mnthYears.add(monthYear);
						}
							
						dateMap.put(monthYear,new Double(totalAmount));
						
						Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","totalAmount 8:" + totalAmount);
								
						currentDate.add(Calendar.MONTH,1);							
								
					}
				}
				else if(lockinperiodmonths > 30)
				{
					for(int k=mnthDiff; k<=lockinperiodmonths; k++)
					{
						totalAmount = 0;
							
						initialAmount=0;
								
						int month = currentDate.get(Calendar.MONTH);
						String monthString = getMonth(month);
						int year = currentDate.get(Calendar.YEAR);
										
						String monthYear = monthString + "-" + year;
									
						if(dateMap.containsKey(monthYear))
						{
							totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
						}
									
						totalAmount = totalAmount + initialAmount;
	
						if(!mnthYears.contains(monthYear))
						{
							mnthYears.add(monthYear);
						}
							
						dateMap.put(monthYear,new Double(totalAmount));
						
						Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","totalAmount9 :" + totalAmount);
								
						currentDate.add(Calendar.MONTH,1);							
								
					}
					for(int k=lockinperiodmonths+1; k<=filterMonthDiff; k++)
					{
						totalAmount = 0;
							
						initialAmount= distPerMonth;
								
						int month = currentDate.get(Calendar.MONTH);
						String monthString = getMonth(month);
						int year = currentDate.get(Calendar.YEAR);
										
						String monthYear = monthString + "-" + year;
									
						//Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","initialAmount :" + initialAmount);
						if(dateMap.containsKey(monthYear))
						{
							totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
						}
									
						totalAmount = totalAmount + initialAmount;
	
						if(!mnthYears.contains(monthYear))
						{
							mnthYears.add(monthYear);
						}
							
						dateMap.put(monthYear,new Double(totalAmount));
						
						Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","totalAmount 10:" + totalAmount);
								
						currentDate.add(Calendar.MONTH,1);							
								
					}
							
				}
			}
		}
				
	}
		
	
	/**
	 * TC Expired Applications
	 */
		
	ArrayList tcExpiredAppsList = (ArrayList)applicationsListForClaim.get(1);
	for(int i=0; i<tcExpiredAppsList.size(); i++)
	{
		sysDate = new java.util.Date();
			
		Application application = (Application)tcExpiredAppsList.get(i);
			
		double approvedAmt = application.getApprovedAmount();
				
		// get the tenure
		int tenure = application.getTermLoan().getTenure();
		double doubleTenure = (new Integer(tenure)).doubleValue();
				
				
		double distAmount = 0;
				
		if(approvedAmt <= 200000)
		{
			distAmount = (.8) * approvedAmt;
		}
		else{
			distAmount = (.2) * approvedAmt;
		}
	
		double distPerMonth = distAmount / doubleTenure;
			
		Date lockInPeriodStartDate = application.getSubmittedDate();
			
		/**
		 * Find the difference of months b/w Lock in Period Start Date and Generation date			 * 
		 */
	
	
		Calendar date1 = Calendar.getInstance();
		Calendar date2 = Calendar.getInstance();
		date1.setTime(lockInPeriodStartDate);
		date2.setTime(sysDate);
		long monthDiff = DateHelper.getMonthDifference(date1,date2);
		if(monthDiff!=0)
		{
			monthDiff = monthDiff-1;
		}
				
		double monthDiffDouble = new Long(monthDiff).doubleValue(); 
				
		int mnthDiff = new Long(monthDiff).intValue();
			
		/**
		 * Find the difference of months b/w Lock in Period Start Date and filter date
		 */
				
		date1.setTime(lockInPeriodStartDate);
		date2.setTime(filterDate);
		long filterMonthDiff = DateHelper.getMonthDifference(date1,date2);
		if(filterMonthDiff!=0)
		{
			filterMonthDiff = filterMonthDiff-1;
		}
				
	
		int filterMnthDiff = new Long(filterMonthDiff).intValue();
			
			
		double intitalDistAmt = distPerMonth * lockinperiodmonthsDouble;
				
		double initialAmount = 0;
				
		double totalAmount = 0;
				
		Calendar currentDate = Calendar.getInstance();
		currentDate.setTime(sysDate);
			
		/**
		 * expiry date including the claim lodgment period
		 */
		Date expiryDate = application.getGuaranteeStartDate(); 
		Calendar expiry = Calendar.getInstance();
		expiry.setTime(expiryDate);
		long expiryMonthDiff = DateHelper.getMonthDifference(date1,expiry);
		if(expiryMonthDiff!=0)
		{
			expiryMonthDiff = expiryMonthDiff-1;
		}
			
		int expiryMnthDiff = new Long(expiryMonthDiff).intValue();
			
		/**
		 * the lock in period ends before the generation date  
		 */
		if((mnthDiff >lockinperiodmonths))
		{
			/**
			 * the validity date is after the filter date
			 */
			if(expiryDate.after(filterDate))
			{
				/**
				 * if the month difference is less than 30
				 */
					if(monthDiff < 30)
					{						
						/**
						 * if the filter date difference is less than 30
						 */
						if(filterMonthDiff <= 30)
						{
							//Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","filterMonthDiff <= 30");
							currentDate = Calendar.getInstance();
							currentDate.setTime(sysDate);
								
							for(int j=mnthDiff; j<=filterMnthDiff; j++)
							{
								totalAmount = 0;
									
								if(j==mnthDiff)
								{
									initialAmount = (lockinperiodmonthsDouble * distPerMonth ) + ((monthDiffDouble - lockinperiodmonthsDouble) * distPerMonth);
								}
								else{
										
									initialAmount = initialAmount + distPerMonth;
								}
								int month = currentDate.get(Calendar.MONTH);
								String monthString = getMonth(month);
								int year = currentDate.get(Calendar.YEAR);
										
								String monthYear = monthString + "-" + year;
									
								if(dateMap.containsKey(monthYear))
								{
									totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
									
								}
									
								totalAmount = totalAmount + initialAmount;
	
								if(!mnthYears.contains(monthYear))
								{
									mnthYears.add(monthYear);
								}
								dateMap.put(monthYear,new Double(totalAmount));
								
								currentDate.add(Calendar.MONTH,1);							
									
									
							}
								
						}
						else if (filterMonthDiff > 30)
						{							
							currentDate = Calendar.getInstance();
							currentDate.setTime(sysDate);
								
							for(int k=mnthDiff; k<=30; k++)
							{
								totalAmount = 0;							
									if(k==mnthDiff)
									{
										initialAmount = (lockinperiodmonthsDouble * distPerMonth ) + ((monthDiffDouble - lockinperiodmonthsDouble) * distPerMonth);
									}
									else{
										initialAmount = initialAmount + distPerMonth;
									}
										
										
								int month = currentDate.get(Calendar.MONTH);
								String monthString = getMonth(month);
								int year = currentDate.get(Calendar.YEAR);
										
								String monthYear = monthString + "-" + year;
									
								if(dateMap.containsKey(monthYear))
								{
									totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
	
								}
									
								totalAmount = totalAmount + initialAmount;
	
	
								if(!mnthYears.contains(monthYear))
								{
									mnthYears.add(monthYear);
								}
									
								dateMap.put(monthYear,new Double(totalAmount));
								
								currentDate.add(Calendar.MONTH,1);							
										
							}
							for(int l=31; l<=filterMonthDiff; l++)
							{
								totalAmount = 0;
								initialAmount = distPerMonth;
									
								int month = currentDate.get(Calendar.MONTH);
								String monthString = getMonth(month);
								int year = currentDate.get(Calendar.YEAR);
										
								String monthYear = monthString + "-" + year;
									
								if(dateMap.containsKey(monthYear))
								{
									totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
									
								}
									
								totalAmount = totalAmount + initialAmount;
	
								if(!mnthYears.contains(monthYear))
								{
									mnthYears.add(monthYear);
								}
									
								dateMap.put(monthYear,new Double(totalAmount));
								
								currentDate.add(Calendar.MONTH,1);							
									
							}
															
						}
					}
					else if(monthDiff > 30)
					{
						if(filterMonthDiff > 30)
						{
							for(int a=mnthDiff; a<=filterMonthDiff; a++)
							{		
								totalAmount = 0;
															
								initialAmount = distPerMonth;
									
								int month = currentDate.get(Calendar.MONTH);
								String monthString = getMonth(month);
								int year = currentDate.get(Calendar.YEAR);
										
								String monthYear = monthString + "-" + year;
									
								if(dateMap.containsKey(monthYear))
								{
									totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
									
								}
									
								totalAmount = totalAmount + initialAmount;
	
								if(!mnthYears.contains(monthYear))
								{
									mnthYears.add(monthYear);
								}
									
								dateMap.put(monthYear,new Double(totalAmount));
								
								currentDate.add(Calendar.MONTH,1);							
									
							}
						}
					}
			}
			/**
			 * validity ends before the filter date and L.I.P end date is before the generation date
			 */
			else if(expiryDate.before(filterDate)){
					
				if(mnthDiff<30)
				{
					if(expiryMnthDiff<=30)
					{
						currentDate = Calendar.getInstance();
						currentDate.setTime(sysDate);
									
						for(int j=mnthDiff; j<=expiryMnthDiff; j++)
						{
							totalAmount = 0;
										
							if(j==mnthDiff)
							{
								initialAmount = (lockinperiodmonthsDouble * distPerMonth ) + ((monthDiffDouble - lockinperiodmonthsDouble) * distPerMonth);
							}
							else{
											
								initialAmount = initialAmount + distPerMonth;
							}
							int month = currentDate.get(Calendar.MONTH);
							String monthString = getMonth(month);
							int year = currentDate.get(Calendar.YEAR);
											
							String monthYear = monthString + "-" + year;
										
							if(dateMap.containsKey(monthYear))
							{
								totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
										
							}
										
							totalAmount = totalAmount + initialAmount;
	
							if(!mnthYears.contains(monthYear))
							{
								mnthYears.add(monthYear);
							}
							dateMap.put(monthYear,new Double(totalAmount));
									
							currentDate.add(Calendar.MONTH,1);							
						}
							
						for(int j=expiryMnthDiff;j<=filterMnthDiff; j++)
						{
							totalAmount = 0;
							initialAmount=0;
								
							int month = currentDate.get(Calendar.MONTH);
							String monthString = getMonth(month);
							int year = currentDate.get(Calendar.YEAR);
											
							String monthYear = monthString + "-" + year;
										
							if(dateMap.containsKey(monthYear))
							{
								totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
										
							}
										
							totalAmount = totalAmount + initialAmount;
	
							if(!mnthYears.contains(monthYear))
							{
								mnthYears.add(monthYear);
							}
							dateMap.put(monthYear,new Double(totalAmount));
									
							currentDate.add(Calendar.MONTH,1);						
						}
					}
					else if(expiryMnthDiff > 30)
					{
						currentDate = Calendar.getInstance();
						currentDate.setTime(sysDate);
								
						for(int k=mnthDiff; k<=30; k++)
						{
							totalAmount = 0;							
								if(k==mnthDiff)
								{
									initialAmount = (lockinperiodmonthsDouble * distPerMonth ) + ((monthDiffDouble - lockinperiodmonthsDouble) * distPerMonth);
								}
								else{
									initialAmount = initialAmount + distPerMonth;
								}
										
										
							int month = currentDate.get(Calendar.MONTH);
							String monthString = getMonth(month);
							int year = currentDate.get(Calendar.YEAR);
										
							String monthYear = monthString + "-" + year;
									
							if(dateMap.containsKey(monthYear))
							{
								totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
	
							}
									
							totalAmount = totalAmount + initialAmount;
	
	
							if(!mnthYears.contains(monthYear))
							{
								mnthYears.add(monthYear);
							}
									
							dateMap.put(monthYear,new Double(totalAmount));
								
							currentDate.add(Calendar.MONTH,1);							
										
						}
						for(int l=31; l<=expiryMonthDiff; l++)
						{
							totalAmount = 0;
							initialAmount = distPerMonth;
									
							int month = currentDate.get(Calendar.MONTH);
							String monthString = getMonth(month);
							int year = currentDate.get(Calendar.YEAR);
										
							String monthYear = monthString + "-" + year;
									
							if(dateMap.containsKey(monthYear))
							{
								totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
									
							}
									
							totalAmount = totalAmount + initialAmount;
	
							if(!mnthYears.contains(monthYear))
							{
								mnthYears.add(monthYear);
							}
									
							dateMap.put(monthYear,new Double(totalAmount));
								
							currentDate.add(Calendar.MONTH,1);							
									
						}
						for(int m=(expiryMnthDiff + 1); m<=filterMonthDiff; m++)
						{
							totalAmount = 0;
							initialAmount = distPerMonth;
									
							int month = currentDate.get(Calendar.MONTH);
							String monthString = getMonth(month);
							int year = currentDate.get(Calendar.YEAR);
										
							String monthYear = monthString + "-" + year;
									
							if(dateMap.containsKey(monthYear))
							{
								totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
									
							}
									
							totalAmount = totalAmount + initialAmount;
	
							if(!mnthYears.contains(monthYear))
							{
								mnthYears.add(monthYear);
							}
									
							dateMap.put(monthYear,new Double(totalAmount));
								
							currentDate.add(Calendar.MONTH,1);							
									
						}
							
					}
				}
				else if(mnthDiff > 30)
				{
					if(expiryMnthDiff > 30)
					{
						for(int a=mnthDiff; a<=expiryMnthDiff; a++)
						{		
							totalAmount = 0;
															
							initialAmount = distPerMonth;
									
							int month = currentDate.get(Calendar.MONTH);
							String monthString = getMonth(month);
							int year = currentDate.get(Calendar.YEAR);
										
							String monthYear = monthString + "-" + year;
									
							if(dateMap.containsKey(monthYear))
							{
								totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
									
							}
									
							totalAmount = totalAmount + initialAmount;
	
							if(!mnthYears.contains(monthYear))
							{
								mnthYears.add(monthYear);
							}
									
							dateMap.put(monthYear,new Double(totalAmount));
								
							currentDate.add(Calendar.MONTH,1);							
									
						}
							
						for(int a=(expiryMnthDiff + 1); a<=filterMnthDiff; a++)
						{		
							totalAmount = 0;
															
							initialAmount = 0;
									
							int month = currentDate.get(Calendar.MONTH);
							String monthString = getMonth(month);
							int year = currentDate.get(Calendar.YEAR);
										
							String monthYear = monthString + "-" + year;
									
							if(dateMap.containsKey(monthYear))
							{
								totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
									
							}
									
							totalAmount = totalAmount + initialAmount;
	
							if(!mnthYears.contains(monthYear))
							{
								mnthYears.add(monthYear);
							}
									
							dateMap.put(monthYear,new Double(totalAmount));
								
							currentDate.add(Calendar.MONTH,1);							
									
						}
							
					}
				}
					
			}
		}
		/**
		 * L.I.P. end date ends after the sysdate
		 */
		else if(monthDiff < lockinperiodmonths)
		{			
			if(expiryDate.after(filterDate))
			{
				if(filterMonthDiff <= 30)
				{
					for(int c=mnthDiff; c<=lockinperiodmonths; c++ )
					{
						totalAmount = 0;
							
						initialAmount = 0;		
							
						int month = currentDate.get(Calendar.MONTH);
						String monthString = getMonth(month);
						int year = currentDate.get(Calendar.YEAR);
										
						String monthYear = monthString + "-" + year;
									
						if(dateMap.containsKey(monthYear))
						{
							totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
									
						}
									
						totalAmount = totalAmount + initialAmount;
	
						if(!mnthYears.contains(monthYear))
						{
							mnthYears.add(monthYear);
						}
							
						dateMap.put(monthYear,new Double(totalAmount));
								
						currentDate.add(Calendar.MONTH,1);							
	
					}			
					for(int e=lockinperiodmonths + 1; e<=filterMonthDiff; e++)
					{
						totalAmount = 0;
							
						if(e==lockinperiodmonths + 1)
						{
							initialAmount = (lockinperiodmonthsDouble * distPerMonth ) + distPerMonth;
						}
						else{
							initialAmount = initialAmount + distPerMonth;
						}
						int month = currentDate.get(Calendar.MONTH);
						String monthString = getMonth(month);
						int year = currentDate.get(Calendar.YEAR);
										
						String monthYear = monthString + "-" + year;
									
						if(dateMap.containsKey(monthYear))
						{
							totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
						}
									
						totalAmount = totalAmount + initialAmount;
	
						if(!mnthYears.contains(monthYear))
						{
							mnthYears.add(monthYear);
						}
							
						dateMap.put(monthYear,new Double(totalAmount));
								
						currentDate.add(Calendar.MONTH,1);							
							
					}		
				}
				else if(filterMonthDiff > 30)
				{					
					if(lockinperiodmonths <= 30)
					{				
						for(int k=mnthDiff; k<=lockinperiodmonths; k++)
						{
							totalAmount = 0;
								
							initialAmount = 0;
								
							int month = currentDate.get(Calendar.MONTH);
							String monthString = getMonth(month);
							int year = currentDate.get(Calendar.YEAR);
										
							String monthYear = monthString + "-" + year;
									
							if(dateMap.containsKey(monthYear))
							{
								totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
							}
									
							totalAmount = totalAmount + initialAmount;
	
							if(!mnthYears.contains(monthYear))
							{
								mnthYears.add(monthYear);
							}
								
							dateMap.put(monthYear,new Double(totalAmount));
								
							currentDate.add(Calendar.MONTH,1);							
								
						}
						for(int k=lockinperiodmonths +1; k<=30; k++)
						{
							totalAmount = 0;
								
							if(k==lockinperiodmonths + 1)
							{
								initialAmount = (lockinperiodmonthsDouble * distPerMonth ) + distPerMonth;
							}
							else{
								initialAmount = initialAmount + distPerMonth;
							}
							int month = currentDate.get(Calendar.MONTH);
							String monthString = getMonth(month);
							int year = currentDate.get(Calendar.YEAR);
										
							String monthYear = monthString + "-" + year;
									
							if(dateMap.containsKey(monthYear))
							{
								totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
	
							}
									
							totalAmount = totalAmount + initialAmount;
	
							if(!mnthYears.contains(monthYear))
							{
								mnthYears.add(monthYear);
							}
								
							dateMap.put(monthYear,new Double(totalAmount));
								
							currentDate.add(Calendar.MONTH,1);							
								
						}
						for(int k=31; k<=filterMonthDiff; k++)
						{
							totalAmount = 0;
								
							initialAmount = distPerMonth;
								
							int month = currentDate.get(Calendar.MONTH);
							String monthString = getMonth(month);
							int year = currentDate.get(Calendar.YEAR);
										
							String monthYear = monthString + "-" + year;
									
	
							if(dateMap.containsKey(monthYear))
							{
								totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
	
									
							}
									
							totalAmount = totalAmount + initialAmount;
	
							if(!mnthYears.contains(monthYear))
							{
								mnthYears.add(monthYear);
							}
								
							dateMap.put(monthYear,new Double(totalAmount));
								
							currentDate.add(Calendar.MONTH,1);							
								
						}
					}
					else if(lockinperiodmonths > 30)
					{
						for(int k=mnthDiff; k<=lockinperiodmonths; k++)
						{
							totalAmount = 0;
								
							initialAmount=0;
								
							int month = currentDate.get(Calendar.MONTH);
							String monthString = getMonth(month);
							int year = currentDate.get(Calendar.YEAR);
										
							String monthYear = monthString + "-" + year;
									
							if(dateMap.containsKey(monthYear))
							{
								totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
									
							}
									
							totalAmount = totalAmount + initialAmount;
	
							if(!mnthYears.contains(monthYear))
							{
								mnthYears.add(monthYear);
							}
								
							dateMap.put(monthYear,new Double(totalAmount));
								
							currentDate.add(Calendar.MONTH,1);							
								
						}
						for(int k=lockinperiodmonths+1; k<=filterMonthDiff; k++)
						{
							totalAmount = 0;
								
							initialAmount= distPerMonth;
								
							int month = currentDate.get(Calendar.MONTH);
							String monthString = getMonth(month);
							int year = currentDate.get(Calendar.YEAR);
										
							String monthYear = monthString + "-" + year;
									
							if(dateMap.containsKey(monthYear))
							{
								totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
									
							}
									
							totalAmount = totalAmount + initialAmount;
	
	
							if(!mnthYears.contains(monthYear))
							{
								mnthYears.add(monthYear);
							}
								
							dateMap.put(monthYear,new Double(totalAmount));
								
							currentDate.add(Calendar.MONTH,1);							
								
						}
							
					}
				}
			}
			else if(expiryDate.before(filterDate))
			{
				if(expiryDate.after(application.getApprovedDate()))
				{
					if(expiryMonthDiff <= 30)
					{
						for(int c=mnthDiff; c<=lockinperiodmonths; c++ )
						{
							totalAmount = 0;
							
							initialAmount = 0;		
							
							int month = currentDate.get(Calendar.MONTH);
							String monthString = getMonth(month);
							int year = currentDate.get(Calendar.YEAR);
										
							String monthYear = monthString + "-" + year;
									
							if(dateMap.containsKey(monthYear))
							{
								totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
									
							}
									
							totalAmount = totalAmount + initialAmount;
	
							if(!mnthYears.contains(monthYear))
							{
								mnthYears.add(monthYear);
							}
							
							dateMap.put(monthYear,new Double(totalAmount));
								
							currentDate.add(Calendar.MONTH,1);							
	
						}			
						for(int e=lockinperiodmonths + 1; e<=expiryMonthDiff; e++)
						{
							totalAmount = 0;
							
							if(e==lockinperiodmonths + 1)
							{
								initialAmount = (lockinperiodmonthsDouble * distPerMonth ) + distPerMonth;
							}
							else{
								initialAmount = initialAmount + distPerMonth;
							}
							int month = currentDate.get(Calendar.MONTH);
							String monthString = getMonth(month);
							int year = currentDate.get(Calendar.YEAR);
										
							String monthYear = monthString + "-" + year;
									
							if(dateMap.containsKey(monthYear))
							{
								totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
							}
									
							totalAmount = totalAmount + initialAmount;
	
							if(!mnthYears.contains(monthYear))
							{
								mnthYears.add(monthYear);
							}
							
							dateMap.put(monthYear,new Double(totalAmount));
								
							currentDate.add(Calendar.MONTH,1);							
							
						}
						for(int f=(expiryMnthDiff+1); f<=filterMonthDiff; f++)
						{
							totalAmount = 0;
							
							initialAmount = 0;
							
							int month = currentDate.get(Calendar.MONTH);
							String monthString = getMonth(month);
							int year = currentDate.get(Calendar.YEAR);
										
							String monthYear = monthString + "-" + year;
									
							if(dateMap.containsKey(monthYear))
							{
								totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
							}
									
							totalAmount = totalAmount + initialAmount;
	
							if(!mnthYears.contains(monthYear))
							{
								mnthYears.add(monthYear);
							}
							
							dateMap.put(monthYear,new Double(totalAmount));
								
							currentDate.add(Calendar.MONTH,1);							
							
						}		
					}
					else if(filterMonthDiff > 30)
					{					
						if(lockinperiodmonths <= 30)
						{				
							for(int k=mnthDiff; k<=lockinperiodmonths; k++)
							{
								totalAmount = 0;
								
								initialAmount = 0;
								
								int month = currentDate.get(Calendar.MONTH);
								String monthString = getMonth(month);
								int year = currentDate.get(Calendar.YEAR);
										
								String monthYear = monthString + "-" + year;
									
								if(dateMap.containsKey(monthYear))
								{
									totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
								}
									
								totalAmount = totalAmount + initialAmount;
	
								if(!mnthYears.contains(monthYear))
								{
									mnthYears.add(monthYear);
								}
								
								dateMap.put(monthYear,new Double(totalAmount));
								
								currentDate.add(Calendar.MONTH,1);							
								
							}
							for(int k=lockinperiodmonths +1; k<=30; k++)
							{
								totalAmount = 0;
								
								if(k==lockinperiodmonths + 1)
								{
									initialAmount = (lockinperiodmonthsDouble * distPerMonth ) + distPerMonth;
								}
								else{
									initialAmount = initialAmount + distPerMonth;
								}
								int month = currentDate.get(Calendar.MONTH);
								String monthString = getMonth(month);
								int year = currentDate.get(Calendar.YEAR);
										
								String monthYear = monthString + "-" + year;
									
								if(dateMap.containsKey(monthYear))
								{
									totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
	
								}
									
								totalAmount = totalAmount + initialAmount;
	
								if(!mnthYears.contains(monthYear))
								{
									mnthYears.add(monthYear);
								}
								
								dateMap.put(monthYear,new Double(totalAmount));
								
								currentDate.add(Calendar.MONTH,1);							
								
							}
							for(int k=31; k<=filterMonthDiff; k++)
							{
								totalAmount = 0;
								
								initialAmount = distPerMonth;
								
								int month = currentDate.get(Calendar.MONTH);
								String monthString = getMonth(month);
								int year = currentDate.get(Calendar.YEAR);
										
								String monthYear = monthString + "-" + year;
									
	
								if(dateMap.containsKey(monthYear))
								{
									totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
	
									
								}
									
								totalAmount = totalAmount + initialAmount;
	
								if(!mnthYears.contains(monthYear))
								{
									mnthYears.add(monthYear);
								}
								
								dateMap.put(monthYear,new Double(totalAmount));
								
								currentDate.add(Calendar.MONTH,1);							
								
							}
						}
						else if(lockinperiodmonths > 30)
						{
							for(int k=mnthDiff; k<=lockinperiodmonths; k++)
							{
								totalAmount = 0;
								
								initialAmount=0;
								
								int month = currentDate.get(Calendar.MONTH);
								String monthString = getMonth(month);
								int year = currentDate.get(Calendar.YEAR);
										
								String monthYear = monthString + "-" + year;
									
								if(dateMap.containsKey(monthYear))
								{
									totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
									
								}
									
								totalAmount = totalAmount + initialAmount;
	
								if(!mnthYears.contains(monthYear))
								{
									mnthYears.add(monthYear);
								}
								
								dateMap.put(monthYear,new Double(totalAmount));
								
								currentDate.add(Calendar.MONTH,1);							
								
							}
							for(int k=lockinperiodmonths+1; k<=filterMonthDiff; k++)
							{
								totalAmount = 0;
								
								initialAmount= distPerMonth;
								
								int month = currentDate.get(Calendar.MONTH);
								String monthString = getMonth(month);
								int year = currentDate.get(Calendar.YEAR);
										
								String monthYear = monthString + "-" + year;
									
								if(dateMap.containsKey(monthYear))
								{
									totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
									
								}
									
								totalAmount = totalAmount + initialAmount;
	
	
								if(!mnthYears.contains(monthYear))
								{
									mnthYears.add(monthYear);
								}
								
								dateMap.put(monthYear,new Double(totalAmount));
								
								currentDate.add(Calendar.MONTH,1);							
								
							}
							
						}
					}
					
				}
			}
		}
	}
		

		

	/**
	 * WC Expired Applications
	 */
	
	ArrayList wcExpiredAppsList = (ArrayList)applicationsListForClaim.get(3);
	//Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","wcExpiredAppsList :" + wcExpiredAppsList.size());
	for(int i=0; i<wcExpiredAppsList.size(); i++)
	{
		sysDate = new java.util.Date();
		
		Application application = (Application)wcExpiredAppsList.get(i);
		
		Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","wcExpiredAppsList cgpan:" + application.getCgpan());
		
		double approvedAmt = application.getApprovedAmount();
			
		// get the tenure
		int tenure = application.getWc().getWcTenure();
		double doubleTenure = (new Integer(tenure)).doubleValue();
			
			
		double distAmount = 0;
			
		if(approvedAmt <= 200000)
		{
			distAmount = (.8) * approvedAmt;
		}
		else{
			distAmount = (.2) * approvedAmt;
		}

		double distPerMonth = distAmount / doubleTenure;
		
		//Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","distPerMonth:" + distPerMonth + "for cgpan " + application.getCgpan());
		
		Date lockInPeriodStartDate = application.getSubmittedDate();
		
		/**
		 * Find the difference of months b/w Lock in Period Start Date and Generation date			 * 
		 */

		Calendar date1 = Calendar.getInstance();
		Calendar date2 = Calendar.getInstance();
		
		date1.setTime(lockInPeriodStartDate);
		
		//date1.add(Calendar.MONTH,1);
		date2.setTime(sysDate);
		Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","lockInPeriodStartDate:" + lockInPeriodStartDate + "for cgpan " + application.getCgpan());
		//Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","sysDate:" + sysDate + "for cgpan " + application.getCgpan());
		long monthDiff = DateHelper.getMonthDifference(date1,date2);
		if(monthDiff!=0)
		{
			monthDiff = monthDiff-1;
		}
			
		double monthDiffDouble = new Long(monthDiff).doubleValue(); 
			
		int mnthDiff = new Long(monthDiff).intValue();
		
	Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","mnthDiff:" + mnthDiff + "for cgpan " + application.getCgpan());
		
		/**
		 * Find the difference of months b/w Lock in Period Start Date and filter date
		 */
		//Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","lockInPeriodStartDate:" + lockInPeriodStartDate + "for cgpan " + application.getCgpan());
			
		date1.setTime(lockInPeriodStartDate);
		date2.setTime(filterDate);
		long filterMonthDiff = DateHelper.getMonthDifference(date1,date2);
			
		if(filterMonthDiff!=0)
		{
			filterMonthDiff = filterMonthDiff-1;
		}
			

		int filterMnthDiff = new Long(filterMonthDiff).intValue();
		
	Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","filterMnthDiff:" + filterMnthDiff + "for cgpan " + application.getCgpan());
		
		
		double intitalDistAmt = distPerMonth * lockinperiodmonthsDouble;
		
		//Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","intitalDistAmt:" + intitalDistAmt + "for cgpan " + application.getCgpan());
			
		double initialAmount = 0;
			
		double totalAmount = 0;
			
		Calendar currentDate = Calendar.getInstance();
		currentDate.setTime(sysDate);
		
		/**
		 * expiry date including the claim lodgment period
		 */
				
		Date expiryDate = application.getGuaranteeStartDate();
		
		Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","expiryDate:" + expiryDate + "for cgpan " + application.getCgpan()); 
		Calendar expiry = Calendar.getInstance();
		expiry.setTime(expiryDate);
		long expiryMonthDiff = DateHelper.getMonthDifference(date1,expiry);
		
		if(expiryMonthDiff!=0)
		{
			expiryMonthDiff = expiryMonthDiff-1;
		}
		
		int expiryMnthDiff = new Long(expiryMonthDiff).intValue();
		
		Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","expiryMnthDiff:" + expiryMnthDiff + "for cgpan " + application.getCgpan());
		
		/**
		 * the lock in period ends before the generation date  
		 */
		if((mnthDiff >lockinperiodmonths))
		{
			Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","mnthDiff >lockinperiodmonths " +  "for cgpan " + application.getCgpan());
			/**
			 * the validity date is after the filter date
			 */
			if(expiryDate.after(filterDate))			
			{
				Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","expiryDate.after(filterDate) " +  "for cgpan " + application.getCgpan());
				/**
				 * if the month difference is less than 30
				 */
					if(monthDiff <= 30)
					{						
						/**
						 * if the filter date difference is less than 30
						 */
						if(filterMonthDiff <= 30)
						{
							Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","filterMonthDiff <= 30");
							currentDate = Calendar.getInstance();
							currentDate.setTime(sysDate);
							
							for(int j=mnthDiff; j<=filterMnthDiff; j++)
							{
								totalAmount = 0;
								
								if(j==mnthDiff)
								{
									initialAmount = (lockinperiodmonthsDouble * distPerMonth ) + ((monthDiffDouble - lockinperiodmonthsDouble) * distPerMonth);
								}
								else{
									
									initialAmount = initialAmount + distPerMonth;
								}
								int month = currentDate.get(Calendar.MONTH);
								String monthString = getMonth(month);
								int year = currentDate.get(Calendar.YEAR);
									
								String monthYear = monthString + "-" + year;
								
								if(dateMap.containsKey(monthYear))
								{
									totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
								
								}
								
								totalAmount = totalAmount + initialAmount;

								if(!mnthYears.contains(monthYear))
								{
									mnthYears.add(monthYear);
								}
								dateMap.put(monthYear,new Double(totalAmount));
							
								currentDate.add(Calendar.MONTH,1);							
								
								
							}
							
						}
						else if (filterMonthDiff > 30)
						{						
							Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","filterMonthDiff > 30");	
							currentDate = Calendar.getInstance();
							currentDate.setTime(sysDate);
							
							for(int k=mnthDiff; k<=30; k++)
							{
								//Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","k :" + k + "for cgpan"+ application.getCgpan());
								totalAmount = 0;							
									if(k==mnthDiff)
									{
										initialAmount = (lockinperiodmonthsDouble * distPerMonth ) + ((monthDiffDouble - lockinperiodmonthsDouble) * distPerMonth);
										//Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","initialAmount :" + initialAmount + "for cgpan"+ application.getCgpan());
									}
									else{
										initialAmount = initialAmount + distPerMonth;
									}
									
								
								int month = currentDate.get(Calendar.MONTH);
								String monthString = getMonth(month);
								int year = currentDate.get(Calendar.YEAR);
									
								String monthYear = monthString + "-" + year;
								
								//Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","monthYear :" + monthYear + "for cgpan" + application.getCgpan());
								
								if(dateMap.containsKey(monthYear))
								{
									totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();

								}
								
								totalAmount = totalAmount + initialAmount;
								
								//Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","totalAmount :" + totalAmount + "for cgpan" + application.getCgpan());


								if(!mnthYears.contains(monthYear))
								{
									mnthYears.add(monthYear);
								}
								
								dateMap.put(monthYear,new Double(totalAmount));
							
								currentDate.add(Calendar.MONTH,1);							
									
							}
							for(int l=31; l<=filterMonthDiff; l++)
							{
								totalAmount = 0;
								initialAmount = distPerMonth;
								
								int month = currentDate.get(Calendar.MONTH);
								String monthString = getMonth(month);
								int year = currentDate.get(Calendar.YEAR);
									
								String monthYear = monthString + "-" + year;
								
								//Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","monthYear :" + monthYear + "for cgpan" + application.getCgpan());
								
								if(dateMap.containsKey(monthYear))
								{
									totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
								
								}
								
								totalAmount = totalAmount + initialAmount;
								
								//Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","totalAmount :" + totalAmount + "for cgpan" + application.getCgpan());

								if(!mnthYears.contains(monthYear))
								{
									mnthYears.add(monthYear);
								}
								
								dateMap.put(monthYear,new Double(totalAmount));
							
								currentDate.add(Calendar.MONTH,1);							
								
							}
														
						}
					}
					else if(monthDiff > 30)
					{
						if(filterMonthDiff > 30)
						{
							Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","filterMonthDiff > 30 and  monthDiff > 30" +  "for cgpan " + application.getCgpan());
							for(int a=mnthDiff; a<=filterMonthDiff; a++)
							{		
								totalAmount = 0;
														
								initialAmount = distPerMonth;
								
								int month = currentDate.get(Calendar.MONTH);
								String monthString = getMonth(month);
								int year = currentDate.get(Calendar.YEAR);
									
								String monthYear = monthString + "-" + year;
								
								//Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","monthYear" +monthYear +  "for cgpan " + application.getCgpan());
								
								if(dateMap.containsKey(monthYear))
								{
									totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
								
								}
								
								totalAmount = totalAmount + initialAmount;
								//Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","totalAmount" +totalAmount +  "for cgpan " + application.getCgpan());

								if(!mnthYears.contains(monthYear))
								{
									mnthYears.add(monthYear);
								}
								
								dateMap.put(monthYear,new Double(totalAmount));
							
								currentDate.add(Calendar.MONTH,1);							
								
							}
						}
					}
			}
			/**
			 * validity ends before the filter date and L.I.P end date is before the generation date
			 */
			else if(expiryDate.before(filterDate)){
				
				Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","expiryDate.before(filterDate)" +  "for cgpan " + application.getCgpan());
				
				if(mnthDiff<30)
				{
					Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","mnthDiff<30" +  "for cgpan " + application.getCgpan());
					if(expiryMnthDiff<=30)
					{
						Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","expiryMnthDiff<=30" +  "for cgpan " + application.getCgpan());
						
						currentDate = Calendar.getInstance();
						currentDate.setTime(sysDate);
								
						for(int j=mnthDiff; j<=expiryMnthDiff; j++)
						{
							totalAmount = 0;
									
							if(j==mnthDiff)
							{
								initialAmount = (lockinperiodmonthsDouble * distPerMonth ) + ((monthDiffDouble - lockinperiodmonthsDouble) * distPerMonth);
							}
							else{
										
								initialAmount = initialAmount + distPerMonth;
							}
							int month = currentDate.get(Calendar.MONTH);
							String monthString = getMonth(month);
							int year = currentDate.get(Calendar.YEAR);
										
							String monthYear = monthString + "-" + year;
									
							if(dateMap.containsKey(monthYear))
							{
								totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
									
							}
									
							totalAmount = totalAmount + initialAmount;

							if(!mnthYears.contains(monthYear))
							{
								mnthYears.add(monthYear);
							}
							dateMap.put(monthYear,new Double(totalAmount));
							
							Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","monthYear" +  "for cgpan " + application.getCgpan() + monthYear);
							
							Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","totalAmount" +  "for cgpan " + application.getCgpan() + totalAmount);
							
								
							currentDate.add(Calendar.MONTH,1);							
						}
						
						for(int j=expiryMnthDiff;j<=filterMnthDiff; j++)
						{
							totalAmount = 0;
							initialAmount=0;
							
							int month = currentDate.get(Calendar.MONTH);
							String monthString = getMonth(month);
							int year = currentDate.get(Calendar.YEAR);
										
							String monthYear = monthString + "-" + year;
									
							if(dateMap.containsKey(monthYear))
							{
								totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
									
							}
									
							totalAmount = totalAmount + initialAmount;

							if(!mnthYears.contains(monthYear))
							{
								mnthYears.add(monthYear);
							}
							dateMap.put(monthYear,new Double(totalAmount));
							
							Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","monthYear" +  "for cgpan " + application.getCgpan() + monthYear);
							
							Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","totalAmount" +  "for cgpan " + application.getCgpan() + totalAmount);
							
								
							currentDate.add(Calendar.MONTH,1);						
						}
					}
					else if(expiryMnthDiff > 30)
					{
						Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","expiryMnthDiff>30" +  "for cgpan " + application.getCgpan());
						currentDate = Calendar.getInstance();
						currentDate.setTime(sysDate);
							
						for(int k=mnthDiff; k<=30; k++)
						{
							totalAmount = 0;							
								if(k==mnthDiff)
								{
									initialAmount = (lockinperiodmonthsDouble * distPerMonth ) + ((monthDiffDouble - lockinperiodmonthsDouble) * distPerMonth);
								}
								else{
									initialAmount = initialAmount + distPerMonth;
								}
									
									
							int month = currentDate.get(Calendar.MONTH);
							String monthString = getMonth(month);
							int year = currentDate.get(Calendar.YEAR);
									
							String monthYear = monthString + "-" + year;
								
							if(dateMap.containsKey(monthYear))
							{
								totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();

							}
								
							totalAmount = totalAmount + initialAmount;


							if(!mnthYears.contains(monthYear))
							{
								mnthYears.add(monthYear);
							}
								
							dateMap.put(monthYear,new Double(totalAmount));
							
							Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","monthYear" +  "for cgpan " + application.getCgpan() + monthYear);
							
							Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","totalAmount" +  "for cgpan " + application.getCgpan() + totalAmount);
							
							
							currentDate.add(Calendar.MONTH,1);							
									
						}
						for(int l=31; l<=expiryMonthDiff; l++)
						{
							totalAmount = 0;
							initialAmount = distPerMonth;
								
							int month = currentDate.get(Calendar.MONTH);
							String monthString = getMonth(month);
							int year = currentDate.get(Calendar.YEAR);
									
							String monthYear = monthString + "-" + year;
								
							if(dateMap.containsKey(monthYear))
							{
								totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
								
							}
								
							totalAmount = totalAmount + initialAmount;

							if(!mnthYears.contains(monthYear))
							{
								mnthYears.add(monthYear);
							}
								
							dateMap.put(monthYear,new Double(totalAmount));
							
							Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","monthYear" +  "for cgpan " + application.getCgpan() + monthYear);
							
							Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","totalAmount" +  "for cgpan " + application.getCgpan() + totalAmount);
							
							
							currentDate.add(Calendar.MONTH,1);							
								
						}
						for(int m=(expiryMnthDiff + 1); m<=filterMonthDiff; m++)
						{
							totalAmount = 0;
							initialAmount = distPerMonth;
								
							int month = currentDate.get(Calendar.MONTH);
							String monthString = getMonth(month);
							int year = currentDate.get(Calendar.YEAR);
									
							String monthYear = monthString + "-" + year;
								
							if(dateMap.containsKey(monthYear))
							{
								totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
								
							}
								
							totalAmount = totalAmount + initialAmount;

							if(!mnthYears.contains(monthYear))
							{
								mnthYears.add(monthYear);
							}
								
							dateMap.put(monthYear,new Double(totalAmount));
							
							Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","monthYear" +  "for cgpan " + application.getCgpan() + monthYear);
							
							Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","totalAmount" +  "for cgpan " + application.getCgpan() + totalAmount);
							
							
							currentDate.add(Calendar.MONTH,1);							
								
						}
						
					}
				}
				else if(mnthDiff > 30)
				{
					Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","mnthDiff > 30" +  "for cgpan " + application.getCgpan());
					if(expiryMnthDiff > 30)
					{
						Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","expiryMnthDiff > 30" +  "for cgpan " + application.getCgpan());
						for(int a=mnthDiff; a<=expiryMnthDiff; a++)
						{		
							totalAmount = 0;
														
							initialAmount = distPerMonth;
								
							int month = currentDate.get(Calendar.MONTH);
							String monthString = getMonth(month);
							int year = currentDate.get(Calendar.YEAR);
									
							String monthYear = monthString + "-" + year;
								
							if(dateMap.containsKey(monthYear))
							{
								totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
								
							}
								
							totalAmount = totalAmount + initialAmount;

							if(!mnthYears.contains(monthYear))
							{
								mnthYears.add(monthYear);
							}
								
							dateMap.put(monthYear,new Double(totalAmount));
							
							Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","monthYear" +  "for cgpan " + application.getCgpan() + monthYear);
							
							Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","totalAmount" +  "for cgpan " + application.getCgpan() + totalAmount);
							
							currentDate.add(Calendar.MONTH,1);							
								
						}
						
						for(int a=(expiryMnthDiff + 1); a<=filterMnthDiff; a++)
						{		
							totalAmount = 0;
														
							initialAmount = 0;
								
							int month = currentDate.get(Calendar.MONTH);
							String monthString = getMonth(month);
							int year = currentDate.get(Calendar.YEAR);
									
							String monthYear = monthString + "-" + year;
								
							if(dateMap.containsKey(monthYear))
							{
								totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
								
							}
								
							totalAmount = totalAmount + initialAmount;

							if(!mnthYears.contains(monthYear))
							{
								mnthYears.add(monthYear);
							}
								
							dateMap.put(monthYear,new Double(totalAmount));
							
							Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","monthYear" +  "for cgpan " + application.getCgpan() + monthYear);
							
							Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","totalAmount" +  "for cgpan " + application.getCgpan() + totalAmount);
							
							
							currentDate.add(Calendar.MONTH,1);							
								
						}
						
					}
				}
				
			}
		}
		/**
		 * L.I.P. end date ends after the sysdate
		 */
		else if(monthDiff < lockinperiodmonths)
		{
			Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","mnthDiff <lockinperiodmonths " +  "for cgpan " + application.getCgpan());
			//Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","expiryDate " + expiryDate +  "for cgpan " + application.getCgpan());
			//Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","filterMonthDiff " +  filterMonthDiff + "for cgpan " + application.getCgpan());	
			if(expiryDate.after(filterDate))
			{
				Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","expiryDate.before(filterDate) " +  "for cgpan " + application.getCgpan());
				
				if(filterMonthDiff <= 30)
				{
					Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","filterMonthDiff <= 30" +  "for cgpan " + application.getCgpan());
					for(int c=mnthDiff; c<=lockinperiodmonths; c++ )
					{
						totalAmount = 0;
						
						initialAmount = 0;		
						
						int month = currentDate.get(Calendar.MONTH);
						String monthString = getMonth(month);
						int year = currentDate.get(Calendar.YEAR);
									
						String monthYear = monthString + "-" + year;
								
						if(dateMap.containsKey(monthYear))
						{
							totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
								
						}
								
						totalAmount = totalAmount + initialAmount;

						if(!mnthYears.contains(monthYear))
						{
							mnthYears.add(monthYear);
						}
						
						dateMap.put(monthYear,new Double(totalAmount));
						
						Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","monthYear" +  "for cgpan " + application.getCgpan() + monthYear);
							
						Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","totalAmount" +  "for cgpan " + application.getCgpan() + totalAmount);
						
							
						currentDate.add(Calendar.MONTH,1);							

					}			
					for(int e=lockinperiodmonths + 1; e<=filterMonthDiff; e++)
					{
						totalAmount = 0;
						
						//Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","distPerMonth" + distPerMonth +  "for cgpan " + application.getCgpan());
						
						if(e==lockinperiodmonths + 1)
						{
							initialAmount = (lockinperiodmonthsDouble * distPerMonth )+ distPerMonth;
							//Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","lockinperiodmonthsDouble" + lockinperiodmonthsDouble +  "for cgpan " + application.getCgpan());
							//Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","initialAmount" + initialAmount +  "for cgpan " + application.getCgpan());
						}
						else{
							initialAmount = initialAmount + distPerMonth;
							//Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","initialAmount 1" + initialAmount +  "for cgpan " + application.getCgpan());							
						}
						
						int month = currentDate.get(Calendar.MONTH);
						String monthString = getMonth(month);
						int year = currentDate.get(Calendar.YEAR);
									
						String monthYear = monthString + "-" + year;
								
						if(dateMap.containsKey(monthYear))
						{
							totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
						}
								
						totalAmount = totalAmount + initialAmount;

						if(!mnthYears.contains(monthYear))
						{
							mnthYears.add(monthYear);
						}
						
						dateMap.put(monthYear,new Double(totalAmount));
						
						Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","monthYear" +  "for cgpan " + application.getCgpan() + monthYear);
							
						Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","totalAmount" +  "for cgpan " + application.getCgpan() + totalAmount);
						
							
						currentDate.add(Calendar.MONTH,1);							
						
					}		
				}
				else if(filterMonthDiff > 30)
				{					
					if(lockinperiodmonths <= 30)
					{				
						for(int k=mnthDiff; k<=lockinperiodmonths; k++)
						{
							totalAmount = 0;
							
							initialAmount = 0;
							
							int month = currentDate.get(Calendar.MONTH);
							String monthString = getMonth(month);
							int year = currentDate.get(Calendar.YEAR);
									
							String monthYear = monthString + "-" + year;
								
							if(dateMap.containsKey(monthYear))
							{
								totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
							}
								
							totalAmount = totalAmount + initialAmount;

							if(!mnthYears.contains(monthYear))
							{
								mnthYears.add(monthYear);
							}
							
							dateMap.put(monthYear,new Double(totalAmount));
							
							Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","monthYear" +  "for cgpan " + application.getCgpan() + monthYear);
							
							Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","totalAmount" +  "for cgpan " + application.getCgpan() + totalAmount);
							
							
							currentDate.add(Calendar.MONTH,1);							
							
						}
						for(int k=lockinperiodmonths +1; k<=30; k++)
						{
							totalAmount = 0;
							
							if(k==lockinperiodmonths + 1)
							{
								initialAmount = (lockinperiodmonthsDouble * distPerMonth ) + distPerMonth;
							}
							else{
								initialAmount = initialAmount + distPerMonth;
							}
							int month = currentDate.get(Calendar.MONTH);
							String monthString = getMonth(month);
							int year = currentDate.get(Calendar.YEAR);
									
							String monthYear = monthString + "-" + year;
								
							if(dateMap.containsKey(monthYear))
							{
								totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();

							}
								
							totalAmount = totalAmount + initialAmount;

							if(!mnthYears.contains(monthYear))
							{
								mnthYears.add(monthYear);
							}
							
							dateMap.put(monthYear,new Double(totalAmount));
							
							Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","monthYear" +  "for cgpan " + application.getCgpan() + monthYear);
							
							Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","totalAmount" +  "for cgpan " + application.getCgpan() + totalAmount);
							
							
							currentDate.add(Calendar.MONTH,1);							
							
						}
						for(int k=31; k<=filterMonthDiff; k++)
						{
							totalAmount = 0;
							
							initialAmount = distPerMonth;
							
							int month = currentDate.get(Calendar.MONTH);
							String monthString = getMonth(month);
							int year = currentDate.get(Calendar.YEAR);
									
							String monthYear = monthString + "-" + year;
								

							if(dateMap.containsKey(monthYear))
							{
								totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();

								
							}
								
							totalAmount = totalAmount + initialAmount;

							if(!mnthYears.contains(monthYear))
							{
								mnthYears.add(monthYear);
							}
							
							dateMap.put(monthYear,new Double(totalAmount));
							
							Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","monthYear" +  "for cgpan " + application.getCgpan() + monthYear);
							
							Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","totalAmount" +  "for cgpan " + application.getCgpan() + totalAmount);
							
							
							currentDate.add(Calendar.MONTH,1);							
							
						}
					}
					else if(lockinperiodmonths > 30)
					{
						for(int k=mnthDiff; k<=lockinperiodmonths; k++)
						{
							totalAmount = 0;
							
							initialAmount=0;
							
							int month = currentDate.get(Calendar.MONTH);
							String monthString = getMonth(month);
							int year = currentDate.get(Calendar.YEAR);
									
							String monthYear = monthString + "-" + year;
								
							if(dateMap.containsKey(monthYear))
							{
								totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
								
							}
								
							totalAmount = totalAmount + initialAmount;

							if(!mnthYears.contains(monthYear))
							{
								mnthYears.add(monthYear);
							}
							
							dateMap.put(monthYear,new Double(totalAmount));
							
							Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","monthYear" +  "for cgpan " + application.getCgpan() + monthYear);
							
							Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","totalAmount" +  "for cgpan " + application.getCgpan() + totalAmount);
							
							
							currentDate.add(Calendar.MONTH,1);							
							
						}
						for(int k=lockinperiodmonths+1; k<=filterMonthDiff; k++)
						{
							totalAmount = 0;
							
							initialAmount= distPerMonth;
							
							int month = currentDate.get(Calendar.MONTH);
							String monthString = getMonth(month);
							int year = currentDate.get(Calendar.YEAR);
									
							String monthYear = monthString + "-" + year;
								
							if(dateMap.containsKey(monthYear))
							{
								totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
								
							}
								
							totalAmount = totalAmount + initialAmount;


							if(!mnthYears.contains(monthYear))
							{
								mnthYears.add(monthYear);
							}
							
							dateMap.put(monthYear,new Double(totalAmount));
							
							Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","monthYear" +  "for cgpan " + application.getCgpan() + monthYear);
							
							Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","totalAmount" +  "for cgpan " + application.getCgpan() + totalAmount);
							
							
							currentDate.add(Calendar.MONTH,1);							
							
						}
						
					}
				}
			}
			else if(expiryDate.before(filterDate))
			{
				Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","expiryDate.before(filterDate) " +  "for cgpan " + application.getCgpan());
				if(expiryDate.after(application.getApprovedDate()))
				{
					if(expiryMonthDiff <= 30)
					{
						for(int c=mnthDiff; c<=lockinperiodmonths; c++ )
						{
							totalAmount = 0;
						
							initialAmount = 0;		
						
							int month = currentDate.get(Calendar.MONTH);
							String monthString = getMonth(month);
							int year = currentDate.get(Calendar.YEAR);
									
							String monthYear = monthString + "-" + year;
								
							if(dateMap.containsKey(monthYear))
							{
								totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
								
							}
								
							totalAmount = totalAmount + initialAmount;

							if(!mnthYears.contains(monthYear))
							{
								mnthYears.add(monthYear);
							}
						
							dateMap.put(monthYear,new Double(totalAmount));
						
							Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","monthYear" +  "for cgpan " + application.getCgpan() + monthYear);
							
							Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","totalAmount" +  "for cgpan " + application.getCgpan() + totalAmount);
						
							
							currentDate.add(Calendar.MONTH,1);							

						}			
						for(int e=lockinperiodmonths + 1; e<=expiryMonthDiff; e++)
						{
							totalAmount = 0;
						
							if(e==lockinperiodmonths + 1)
							{
								initialAmount = (lockinperiodmonthsDouble * distPerMonth ) + distPerMonth;
							}
							else{
								initialAmount = initialAmount + distPerMonth;
							}
							//Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","initialAmount " + initialAmount +  "for cgpan " + application.getCgpan());
							int month = currentDate.get(Calendar.MONTH);
							String monthString = getMonth(month);
							int year = currentDate.get(Calendar.YEAR);
									
							String monthYear = monthString + "-" + year;
								
							if(dateMap.containsKey(monthYear))
							{
								totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
							}
								
							totalAmount = totalAmount + initialAmount;
						
							//Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","totalAmount " + totalAmount +  "for cgpan " + application.getCgpan());						

							if(!mnthYears.contains(monthYear))
							{
								mnthYears.add(monthYear);
							}
						
							dateMap.put(monthYear,new Double(totalAmount));
						
							Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","monthYear" +  "for cgpan " + application.getCgpan() + monthYear);
							
							Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","totalAmount" +  "for cgpan " + application.getCgpan() + totalAmount);
						
							
							currentDate.add(Calendar.MONTH,1);							
						
						}
						for(int f=(expiryMnthDiff+1); f<=filterMonthDiff; f++)
						{
							totalAmount = 0;
						
							initialAmount = 0;
						
							int month = currentDate.get(Calendar.MONTH);
							String monthString = getMonth(month);
							int year = currentDate.get(Calendar.YEAR);
									
							String monthYear = monthString + "-" + year;
								
							if(dateMap.containsKey(monthYear))
							{
								totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
							}
								
							totalAmount = totalAmount + initialAmount;

							if(!mnthYears.contains(monthYear))
							{
								mnthYears.add(monthYear);
							}
						
							dateMap.put(monthYear,new Double(totalAmount));
						
							Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","monthYear" +  "for cgpan " + application.getCgpan() + monthYear);
							
							Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","totalAmount" +  "for cgpan " + application.getCgpan() + totalAmount);
						
							
							currentDate.add(Calendar.MONTH,1);							
						
						}		
					}
					else if(filterMonthDiff > 30)
					{					
						if(lockinperiodmonths <= 30)
						{				
							for(int k=mnthDiff; k<=lockinperiodmonths; k++)
							{
								totalAmount = 0;
							
								initialAmount = 0;
							
								int month = currentDate.get(Calendar.MONTH);
								String monthString = getMonth(month);
								int year = currentDate.get(Calendar.YEAR);
									
								String monthYear = monthString + "-" + year;
								
								if(dateMap.containsKey(monthYear))
								{
									totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
								}
								
								totalAmount = totalAmount + initialAmount;

								if(!mnthYears.contains(monthYear))
								{
									mnthYears.add(monthYear);
								}
							
								dateMap.put(monthYear,new Double(totalAmount));
							
								Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","monthYear" +  "for cgpan " + application.getCgpan() + monthYear);
							
								Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","totalAmount" +  "for cgpan " + application.getCgpan() + totalAmount);
							
								currentDate.add(Calendar.MONTH,1);							
							
							}
							for(int k=lockinperiodmonths +1; k<=30; k++)
							{
								totalAmount = 0;
							
								if(k==lockinperiodmonths + 1)
								{
									initialAmount = (lockinperiodmonthsDouble * distPerMonth ) +  distPerMonth;
								}
								else{
									initialAmount = initialAmount + distPerMonth;
								}
								int month = currentDate.get(Calendar.MONTH);
								String monthString = getMonth(month);
								int year = currentDate.get(Calendar.YEAR);
									
								String monthYear = monthString + "-" + year;
								
								if(dateMap.containsKey(monthYear))
								{
									totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();

								}
								
								totalAmount = totalAmount + initialAmount;

								if(!mnthYears.contains(monthYear))
								{
									mnthYears.add(monthYear);
								}
							
								dateMap.put(monthYear,new Double(totalAmount));
							
								Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","monthYear" +  "for cgpan " + application.getCgpan() + monthYear);
							
								Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","totalAmount" +  "for cgpan " + application.getCgpan() + totalAmount);
							
							
								currentDate.add(Calendar.MONTH,1);							
							
							}
							for(int k=31; k<=filterMonthDiff; k++)
							{
								totalAmount = 0;
							
								initialAmount = distPerMonth;
							
								int month = currentDate.get(Calendar.MONTH);
								String monthString = getMonth(month);
								int year = currentDate.get(Calendar.YEAR);
									
								String monthYear = monthString + "-" + year;
								

								if(dateMap.containsKey(monthYear))
								{
									totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();

								
								}
								
								totalAmount = totalAmount + initialAmount;

								if(!mnthYears.contains(monthYear))
								{
									mnthYears.add(monthYear);
								}
							
								dateMap.put(monthYear,new Double(totalAmount));
							
								Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","monthYear" +  "for cgpan " + application.getCgpan() + monthYear);
							
								Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","totalAmount" +  "for cgpan " + application.getCgpan() + totalAmount);
							
							
								currentDate.add(Calendar.MONTH,1);							
							
							}
						}
						else if(lockinperiodmonths > 30)
						{
							for(int k=mnthDiff; k<=lockinperiodmonths; k++)
							{
								totalAmount = 0;
							
								initialAmount=0;
							
								int month = currentDate.get(Calendar.MONTH);
								String monthString = getMonth(month);
								int year = currentDate.get(Calendar.YEAR);
									
								String monthYear = monthString + "-" + year;
								
								if(dateMap.containsKey(monthYear))
								{
									totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
								
								}
								
								totalAmount = totalAmount + initialAmount;

								if(!mnthYears.contains(monthYear))
								{
									mnthYears.add(monthYear);
								}
							
								dateMap.put(monthYear,new Double(totalAmount));
							
								Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","monthYear" +  "for cgpan " + application.getCgpan() + monthYear);
							
								Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","totalAmount" +  "for cgpan " + application.getCgpan() + totalAmount);
							
							
								currentDate.add(Calendar.MONTH,1);							
							
							}
							for(int k=lockinperiodmonths+1; k<=filterMonthDiff; k++)
							{
								totalAmount = 0;
							
								initialAmount= distPerMonth;
							
								int month = currentDate.get(Calendar.MONTH);
								String monthString = getMonth(month);
								int year = currentDate.get(Calendar.YEAR);
									
								String monthYear = monthString + "-" + year;
								
								if(dateMap.containsKey(monthYear))
								{
									totalAmount = ((Double)dateMap.get(monthYear)).doubleValue();
								
								}
								
								totalAmount = totalAmount + initialAmount;


								if(!mnthYears.contains(monthYear))
								{
									mnthYears.add(monthYear);
								}
							
								dateMap.put(monthYear,new Double(totalAmount));
							
								Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","monthYear" +  "for cgpan " + application.getCgpan() + monthYear);
							
								Log.log(Log.INFO,"IFProcessor","applicationsForClaimProjection","totalAmount" +  "for cgpan " + application.getCgpan() + totalAmount);
							
							
								currentDate.add(Calendar.MONTH,1);							
							
							}
						
						}
					}
					
				}
			}
		}
	}
		

	
	
	totalList.add(dateMap);
	totalList.add(mnthYears);
		
		
		return totalList;	
   }
   
   /**
	* get the applications for NPA Accounts 
	* @param calendarMonth
	* @return
	*/
   public ArrayList getApplicationsForNPAAccounts(Date filterDate)throws DatabaseException
   {
		ArrayList npaAppsList = ifDAO.applicationsForClaimProjNPA();
	   	
		Date currentDate = new java.util.Date();
	   	
		Administrator admin = new Administrator();
		ParameterMaster param = admin.getParameter();
	   	
		TreeMap dateMap = new TreeMap();
		
		ArrayList totalList = new ArrayList();
		
		
	ArrayList mnthYears = new ArrayList();
	   	
		for(int i=0; i<npaAppsList.size(); i++)
		{
			NPADetails npaDetails = (NPADetails)npaAppsList.get(i);	   	
			String  cgbid =npaDetails.getCgbid(); 
	   		
			Log.log(Log.INFO,"IFProcessor","getApplicationsForNPAAccounts","cgbid :" + cgbid);
	   		
			Date lockInPeriodEndDate = isLockinPeriodOver(cgbid,filterDate);
			
			if(lockInPeriodEndDate!=null)
			{
				
				double totalAmount = 0;
				/**
				 * calculate the first and second installmetn amounts
				 */
				double maxAmount = npaDetails.getOsAmtOnNPA();
				
				Log.log(Log.INFO,"IFProcessor","getApplicationsForNPAAccounts","maxAmount:" + maxAmount);
				double partAmount = .75 * maxAmount;
				
				double cgtsiLiability = param.getCgtsiLiability();
				
				double firstInstAmount = (cgtsiLiability /100)* partAmount;
				Log.log(Log.INFO,"IFProcessor","getApplicationsForNPAAccounts","firstInstAmount :" + firstInstAmount);
				double secondInstAmount = partAmount - firstInstAmount;
				Log.log(Log.INFO,"IFProcessor","getApplicationsForNPAAccounts","secondInstAmount :" + secondInstAmount);
				/**
				 * get the settlement details
				 */
				SettlementDetail settlementDetail = ifDAO.getClmDtlsForProjection(cgbid);
				
				Log.log(Log.INFO,"IFProcessor","getApplicationsForNPAAccounts","settlementDetail :" + settlementDetail);
				Log.log(Log.INFO,"IFProcessor","getApplicationsForNPAAccounts","settlementDetail.getTierOneSettlementDt() :" + settlementDetail.getTierOneSettlementDt());
	   		
				/**
				 * IF the settlement details are avaiable and 
				 * if the first installment is settled and 
				 * if it's not the final settlement,account for the application.
				 */
				if(settlementDetail.getTierOneSettlementDt()!=null)
				{
					if(settlementDetail.getTierOneSettlementDt()!=null && !settlementDetail.getTierOneSettlementDt().toString().equals("")
					&& (settlementDetail.getTierTwoSettlementDt()==null || settlementDetail.getTierTwoSettlementDt().toString().equals(""))
					&& settlementDetail.getFinalSettlementFlag().equals("N"))
					{
						/**
						 * get the maximum of NPA Date and L.I.P End Date
						 * 				  
						 */
						Date npaDate = npaDetails.getNpaDate();
						Date maxDate = null;
						if(npaDate.after(lockInPeriodEndDate))
						{
							maxDate = npaDate;
						}
						else if(npaDate.before(lockInPeriodEndDate)){
	   					
							maxDate = lockInPeriodEndDate;
						}
						else if(npaDate.equals(lockInPeriodEndDate))
						{
							maxDate = lockInPeriodEndDate;
						}
						//Log.log(Log.INFO,"IFProcessor","getApplicationsForNPAAccounts","maxDate:" + maxDate);
						/**
						 * get the next month of the lock in period/ NPA Date
						 */
						Calendar calendar1 = Calendar.getInstance();
						calendar1.setTime(maxDate);
					
						Calendar calendar = Calendar.getInstance();
						Date date1 = calendar1.getTime();
						calendar.setTime(date1);
						calendar.add(Calendar.MONTH,1);
	   				
						Calendar filterTime = Calendar.getInstance();
						filterTime.setTime(filterDate);
					
					
						/**
						 * check if the max date if before the generation date
						 */
						double distAmount=0;
	   				
						Calendar currentTime = Calendar.getInstance();
						currentTime.setTime(currentDate);	 
					  				
						int month = currentTime.get(Calendar.MONTH);
						String monthString = getMonth(month);
						int year = currentTime.get(Calendar.YEAR);
									
						String currentYear = monthString + "-" + year;
					
						int k=0;
					
						/**
						 * find the difference between the L.I.P End date and generation date
						 */

						int month1 = calendar1.get(Calendar.MONTH);
						int month2 = currentTime.get(Calendar.MONTH);
					
						long difference1 = DateHelper.getMonthDifference(calendar,filterTime);
					
						//Log.log(Log.INFO,"IFProcessor","getApplicationsForNPAAccounts","calendar1.get(Calendar.MONTH):" + calendar1.get(Calendar.MONTH));
						//Log.log(Log.INFO,"IFProcessor","getApplicationsForNPAAccounts","currentTime.get(Calendar.MONTH:" + currentTime.get(Calendar.MONTH));
	   				
						if(maxDate.before(currentDate))
						{
							if((calendar1.get(Calendar.MONTH)==currentTime.get(Calendar.MONTH))
							&& (calendar1.get(Calendar.YEAR)==currentTime.get(Calendar.YEAR)))
							{
								int endMonth = calendar1.get(Calendar.MONTH);
								String endMonthString = getMonth(endMonth);
								int endYear = calendar1.get(Calendar.YEAR);
									
								String endMonthYear = endMonthString + "-" + endYear;
	   						
								distAmount = 0;
							
								totalAmount = 0;
							
								if(dateMap.containsKey(endMonthYear))
								{
									totalAmount = ((Double)dateMap.get(endMonthYear)).doubleValue();
								
								}
								
								totalAmount = totalAmount + distAmount;

								if(!mnthYears.contains(endMonthYear))
								{
									mnthYears.add(endMonthYear);
								}
								dateMap.put(endMonthYear,new Double(totalAmount));
							
								k=1;
							
	   						
							}
								/**
								 * get the difference between the filter date and the max date(Max of L.I.P / NPA dates)
								 */
						
								//Log.log(Log.INFO,"IFProcessor","getApplicationsForNPAAccounts","difference1:" + difference1);
						
								for(int j=0; j<difference1; j++)
								{
								
									totalAmount = 0;
							
									Log.log(Log.INFO,"IFProcessor","getApplicationsForNPAAccounts","calendar:" + calendar.getTime());
							
									int endMonth = calendar.get(Calendar.MONTH);
									String endMonthString = getMonth(endMonth);
									int endYear = calendar.get(Calendar.YEAR);
									
									String endMonthYear = endMonthString + "-" + endYear;
								
									Log.log(Log.INFO,"IFProcessor","getApplicationsForNPAAccounts","endMonthYear:" + endMonthYear);
	   						
							
									if(j<=5)
									{
										distAmount = 0;
									}
									else if(j<=11)
									{
										distAmount=0;
									}
									else if(j<=17)
									{
										distAmount = secondInstAmount;
									}
									else if(j>17)
									{
										distAmount=0;
									}
							
									//Log.log(Log.INFO,"IFProcessor","getApplicationsForNPAAccounts","distAmount:" + distAmount);
								
									//Log.log(Log.INFO,"IFProcessor","getApplicationsForNPAAccounts","currentYear:" + currentYear);
									//Log.log(Log.INFO,"IFProcessor","getApplicationsForNPAAccounts","endMonthYear:" + endMonthYear);
							
									if(currentYear.equals(endMonthYear))
									{
										Log.log(Log.INFO,"IFProcessor","getApplicationsForNPAAccounts","endMonthYear equal to current year");
										if(dateMap.containsKey(endMonthYear))
										{
											totalAmount = ((Double)dateMap.get(endMonthYear)).doubleValue();
								
										}
								
										totalAmount = totalAmount + distAmount;

										if(!mnthYears.contains(endMonthYear))
										{
											mnthYears.add(endMonthYear);
										}
										dateMap.put(endMonthYear,new Double(totalAmount));
							
										k=1;							
									}
							
									if(k==1)
									{
										++k;
									}
									else if (k>1){
									
										if(dateMap.containsKey(endMonthYear))
										{
											totalAmount = ((Double)dateMap.get(endMonthYear)).doubleValue();
								
										}
								
										totalAmount = totalAmount + distAmount;

										if(!mnthYears.contains(endMonthYear))
										{
											mnthYears.add(endMonthYear);
										}
										dateMap.put(endMonthYear,new Double(totalAmount));
									
										++k;
								
									}								
							
									calendar.add(Calendar.MONTH,1);
									Log.log(Log.INFO,"IFProcessor","getApplicationsForNPAAccounts","calendar.gettime():" + calendar.getTime());
								}
						}
						/**
						 * the max date if between the generation date and the filter date
						 */
						else if(maxDate.after(currentDate))
						{
							//Log.log(Log.INFO,"IFProcessor","getApplicationsForNPAAccounts","difference1:" + difference1);
							long monthdiffLong = DateHelper.getMonthDifference(currentTime,calendar1);
							int monthdiff = new Long(monthdiffLong).intValue();
	   					
							//Log.log(Log.INFO,"IFProcessor","getApplicationsForNPAAccounts","monthdiff:" + monthdiff);

	/*						int currentMonth = currentTime.get(Calendar.MONTH);
							String currentMonthString = getMonth(currentMonth);
							int currentYr = currentTime.get(Calendar.YEAR);
									
							String currentMonthYear = currentMonthString + "-" + currentYr;
	*/	   					
							for(int a=0; a<monthdiff-1; a++)
							{
								int currentMonth = currentTime.get(Calendar.MONTH);
								String currentMonthString = getMonth(currentMonth);
								int currentYr = currentTime.get(Calendar.YEAR);
									
								String currentMonthYear = currentMonthString + "-" + currentYr;
	   						
							
								totalAmount = 0;
							
								distAmount = 0;
							
								Log.log(Log.INFO,"IFProcessor","getApplicationsForNPAAccounts","currentMonthYear:" + currentMonthYear);
							
								if(dateMap.containsKey(currentMonthYear))
								{
									Log.log(Log.INFO,"IFProcessor","getApplicationsForNPAAccounts","currentMonthYear contains" + currentMonthYear);
									totalAmount = ((Double)dateMap.get(currentMonthYear)).doubleValue();
								
									Log.log(Log.INFO,"IFProcessor","getApplicationsForNPAAccounts","currentMonthYear amount" + totalAmount);
								
								}
								
								totalAmount = totalAmount + distAmount;

								if(!mnthYears.contains(currentMonthYear))
								{
									mnthYears.add(currentMonthYear);
								}
								dateMap.put(currentMonthYear,new Double(totalAmount));
							
								Log.log(Log.INFO,"IFProcessor","getApplicationsForNPAAccounts","currentTime:" + currentTime.getTime());
								Log.log(Log.INFO,"IFProcessor","getApplicationsForNPAAccounts","totalAmount:" + totalAmount);
							
							
								currentTime.add(Calendar.MONTH,1);	
							
							
							}
							for(int b=monthdiff; b<=(difference1 + monthdiff); b++)
							{								
							
								int currentMonth = currentTime.get(Calendar.MONTH);
								String currentMonthString = getMonth(currentMonth);
								int currentYr = currentTime.get(Calendar.YEAR);
									
								String currentMonthYear = currentMonthString + "-" + currentYr;
							
								Log.log(Log.INFO,"IFProcessor","getApplicationsForNPAAccounts","currentMonthYear:" + currentMonthYear);
	   						
								Log.log(Log.INFO,"IFProcessor","getApplicationsForNPAAccounts","b:" + b);
								totalAmount = 0;
							
								if(b<=monthdiff+ 6)
								{
									distAmount = 0;
								}
								else if(b<=(monthdiff)+ 12)
								{
									distAmount=0;
								}
								else if(b<=(monthdiff)+ 18)
								{
									distAmount = secondInstAmount;
								}
								else if(b>(monthdiff)+ 18)
								{
									distAmount=0;
								}
								if(dateMap.containsKey(currentMonthYear))
								{
									Log.log(Log.INFO,"IFProcessor","getApplicationsForNPAAccounts","currentMonthYear1 contains" + currentMonthYear);
									totalAmount = ((Double)dateMap.get(currentMonthYear)).doubleValue();
								
									Log.log(Log.INFO,"IFProcessor","getApplicationsForNPAAccounts","currentMonthYear1 contains amount " + currentMonthYear + " " + totalAmount);
								
								}
								
								totalAmount = totalAmount + distAmount;

								if(!mnthYears.contains(currentMonthYear))
								{
									mnthYears.add(currentMonthYear);
								}
								dateMap.put(currentMonthYear,new Double(totalAmount));
							
								Log.log(Log.INFO,"IFProcessor","getApplicationsForNPAAccounts","currentTime1:" + currentTime.getTime());
								Log.log(Log.INFO,"IFProcessor","getApplicationsForNPAAccounts","totalAmount1:" + totalAmount);
							
							
								currentTime.add(Calendar.MONTH,1);
							
							}
						}   				
						
	   			
					}
				}
				/**
				 * if the settlement details are not available 
				 */
				else{
	   				
					//Log.log(Log.INFO,"IFProcessor","getApplicationsForNPAAccounts","settlement details null:");
	   				
					/**
					 * get the maximum of NPA Date and L.I.P End Date
					 * 				  
					 */
					Date npaDate = npaDetails.getNpaDate();
					Date maxDate = null;
					if(npaDate.after(lockInPeriodEndDate))
					{
						maxDate = npaDate;
					}
					else if(npaDate.before(lockInPeriodEndDate)){
	   					
						maxDate = lockInPeriodEndDate;
					}
					else if(npaDate.equals(lockInPeriodEndDate))
					{
						maxDate = lockInPeriodEndDate;
					}
					//Log.log(Log.INFO,"IFProcessor","getApplicationsForNPAAccounts","maxDate:" + maxDate);
					/**
					 * get the next month of the lock in period/ NPA Date
					 */
					Calendar calendar1 = Calendar.getInstance();
					calendar1.setTime(maxDate);
					
					Calendar calendar = Calendar.getInstance();
					Date date1 = calendar1.getTime();
					calendar.setTime(date1);
					calendar.add(Calendar.MONTH,1);
	   				
					Calendar filterTime = Calendar.getInstance();
					filterTime.setTime(filterDate);
					
					
					/**
					 * check if the max date if before the generation date
					 */
					double distAmount=0;
	   				
					Calendar currentTime = Calendar.getInstance();
					currentTime.setTime(currentDate);	 
					  				
					int month = currentTime.get(Calendar.MONTH);
					String monthString = getMonth(month);
					int year = currentTime.get(Calendar.YEAR);
									
					String currentYear = monthString + "-" + year;
					
					int k=0;
					
					/**
					 * find the difference between the L.I.P End date and generation date
					 */

					int month1 = calendar1.get(Calendar.MONTH);
					int month2 = currentTime.get(Calendar.MONTH);
					
					long difference1 = DateHelper.getMonthDifference(calendar,filterTime);
					
					//Log.log(Log.INFO,"IFProcessor","getApplicationsForNPAAccounts","calendar1.get(Calendar.MONTH):" + calendar1.get(Calendar.MONTH));
					//Log.log(Log.INFO,"IFProcessor","getApplicationsForNPAAccounts","currentTime.get(Calendar.MONTH:" + currentTime.get(Calendar.MONTH));
	   				
					if(maxDate.before(currentDate))
					{
						if((calendar1.get(Calendar.MONTH)==currentTime.get(Calendar.MONTH))
						&& (calendar1.get(Calendar.YEAR)==currentTime.get(Calendar.YEAR)))
						{
							int endMonth = calendar1.get(Calendar.MONTH);
							String endMonthString = getMonth(endMonth);
							int endYear = calendar1.get(Calendar.YEAR);
									
							String endMonthYear = endMonthString + "-" + endYear;
							
							Log.log(Log.INFO,"IFProcessor","getApplicationsForNPAAccounts","endMonthYear for equal end date and current date:" + endMonthYear);
	   						
							distAmount = 0;
							
							totalAmount = 0;
							
							if(dateMap.containsKey(endMonthYear))
							{
								totalAmount = ((Double)dateMap.get(endMonthYear)).doubleValue();
								
							}
								
							totalAmount = totalAmount + distAmount;

							if(!mnthYears.contains(endMonthYear))
							{
								Log.log(Log.INFO,"IFProcessor","getApplicationsForNPAAccounts","month year in the array list:" + endMonthYear);
								mnthYears.add(endMonthYear);
							}
							dateMap.put(endMonthYear,new Double(totalAmount));
							
							calendar1.add(Calendar.MONTH,1);
							
							Date calenderDate = calendar1.getTime();
							
							calendar.setTime(calenderDate);
							
							k=1;
							
							++k;
	   						
						}
							/**
							 * get the difference between the filter date and the max date(Max of L.I.P / NPA dates)
							 */
						
							//Log.log(Log.INFO,"IFProcessor","getApplicationsForNPAAccounts","difference1:" + difference1);
						
							for(int j=0; j<difference1; j++)
							{
								
								totalAmount = 0;
							
								Log.log(Log.INFO,"IFProcessor","getApplicationsForNPAAccounts","calendar:" + calendar.getTime());
							
								int endMonth = calendar.get(Calendar.MONTH);
								String endMonthString = getMonth(endMonth);
								int endYear = calendar.get(Calendar.YEAR);
									
								String endMonthYear = endMonthString + "-" + endYear;
								
								Log.log(Log.INFO,"IFProcessor","getApplicationsForNPAAccounts","endMonthYear:" + endMonthYear);
	   						
							
								if(j<=5)
								{
									distAmount = firstInstAmount;
								}
								else if(j<=11)
								{
									distAmount=0;
								}
								else if(j<=17)
								{
									distAmount = secondInstAmount;
								}
								else if(j>17)
								{
									distAmount=0;
								}
							
								//Log.log(Log.INFO,"IFProcessor","getApplicationsForNPAAccounts","distAmount:" + distAmount);
								
								//Log.log(Log.INFO,"IFProcessor","getApplicationsForNPAAccounts","currentYear:" + currentYear);
								//Log.log(Log.INFO,"IFProcessor","getApplicationsForNPAAccounts","endMonthYear:" + endMonthYear);
							
								if(currentYear.equals(endMonthYear))
								{
									Log.log(Log.INFO,"IFProcessor","getApplicationsForNPAAccounts","endMonthYear equal to current year");
									if(dateMap.containsKey(endMonthYear))
									{
										totalAmount = ((Double)dateMap.get(endMonthYear)).doubleValue();
								
									}
								
									totalAmount = totalAmount + distAmount;

									if(!mnthYears.contains(endMonthYear))
									{
										mnthYears.add(endMonthYear);
									}
									dateMap.put(endMonthYear,new Double(totalAmount));
							
									k=1;							
								}
							
								if(k==1)
								{
									++k;
								}
								else if (k>1){
									
									Log.log(Log.INFO,"IFProcessor","getApplicationsForNPAAccounts","k>1 for bid" + cgbid);
									
									if(dateMap.containsKey(endMonthYear))
									{
										totalAmount = ((Double)dateMap.get(endMonthYear)).doubleValue();
								
									}
								
									totalAmount = totalAmount + distAmount;

									if(!mnthYears.contains(endMonthYear))
									{
										mnthYears.add(endMonthYear);
									}
									dateMap.put(endMonthYear,new Double(totalAmount));
									
									++k;
								
								}								
							
								calendar.add(Calendar.MONTH,1);
								Log.log(Log.INFO,"IFProcessor","getApplicationsForNPAAccounts","calendar.gettime():" + calendar.getTime());
							}
	   					
					}
					/**
					 * the max date if between the generation date and the filter date
					 */
					else if(maxDate.after(currentDate))
					{
						//Log.log(Log.INFO,"IFProcessor","getApplicationsForNPAAccounts","difference1:" + difference1);
						long monthdiffLong = DateHelper.getMonthDifference(currentTime,calendar1);
						int monthdiff = new Long(monthdiffLong).intValue();
	   					
						//Log.log(Log.INFO,"IFProcessor","getApplicationsForNPAAccounts","monthdiff:" + monthdiff);

/*						int currentMonth = currentTime.get(Calendar.MONTH);
						String currentMonthString = getMonth(currentMonth);
						int currentYr = currentTime.get(Calendar.YEAR);
									
						String currentMonthYear = currentMonthString + "-" + currentYr;
*/	   					
						for(int a=0; a<monthdiff-1; a++)
						{
							int currentMonth = currentTime.get(Calendar.MONTH);
							String currentMonthString = getMonth(currentMonth);
							int currentYr = currentTime.get(Calendar.YEAR);
									
							String currentMonthYear = currentMonthString + "-" + currentYr;
	   						
							
							totalAmount = 0;
							
							distAmount = 0;
							
							Log.log(Log.INFO,"IFProcessor","getApplicationsForNPAAccounts","currentMonthYear:" + currentMonthYear);
							
							if(dateMap.containsKey(currentMonthYear))
							{
								Log.log(Log.INFO,"IFProcessor","getApplicationsForNPAAccounts","currentMonthYear contains" + currentMonthYear);
								totalAmount = ((Double)dateMap.get(currentMonthYear)).doubleValue();
								
								Log.log(Log.INFO,"IFProcessor","getApplicationsForNPAAccounts","currentMonthYear amount" + totalAmount);
								
							}
								
							totalAmount = totalAmount + distAmount;

							if(!mnthYears.contains(currentMonthYear))
							{
								mnthYears.add(currentMonthYear);
							}
							dateMap.put(currentMonthYear,new Double(totalAmount));
							
							Log.log(Log.INFO,"IFProcessor","getApplicationsForNPAAccounts","currentTime:" + currentTime.getTime());
							Log.log(Log.INFO,"IFProcessor","getApplicationsForNPAAccounts","totalAmount:" + totalAmount);
							
							
							currentTime.add(Calendar.MONTH,1);	
							
							
						}
						for(int b=monthdiff; b<=(difference1 + monthdiff); b++)
						{							
							
							int currentMonth = currentTime.get(Calendar.MONTH);
							String currentMonthString = getMonth(currentMonth);
							int currentYr = currentTime.get(Calendar.YEAR);
									
							String currentMonthYear = currentMonthString + "-" + currentYr;
							
							Log.log(Log.INFO,"IFProcessor","getApplicationsForNPAAccounts","currentMonthYear:" + currentMonthYear);
	   						
							Log.log(Log.INFO,"IFProcessor","getApplicationsForNPAAccounts","b:" + b);
							totalAmount = 0;
							
							if(b<=monthdiff+ 6)
							{
								distAmount = firstInstAmount;
							}
							else if(b<=(monthdiff)+ 12)
							{
								distAmount=0;
							}
							else if(b<=(monthdiff)+ 18)
							{
								distAmount = secondInstAmount;
							}
							else if(b>(monthdiff)+ 18)
							{
								distAmount=0;
							}
							if(dateMap.containsKey(currentMonthYear))
							{
								Log.log(Log.INFO,"IFProcessor","getApplicationsForNPAAccounts","currentMonthYear1 contains" + currentMonthYear);
								totalAmount = ((Double)dateMap.get(currentMonthYear)).doubleValue();
								
								Log.log(Log.INFO,"IFProcessor","getApplicationsForNPAAccounts","currentMonthYear1 contains amount " + currentMonthYear + " " + totalAmount);
								
							}
								
							totalAmount = totalAmount + distAmount;

							if(!mnthYears.contains(currentMonthYear))
							{
								mnthYears.add(currentMonthYear);
							}
							dateMap.put(currentMonthYear,new Double(totalAmount));
							
							Log.log(Log.INFO,"IFProcessor","getApplicationsForNPAAccounts","currentTime1:" + currentTime.getTime());
							Log.log(Log.INFO,"IFProcessor","getApplicationsForNPAAccounts","totalAmount1:" + totalAmount);
							
							
							currentTime.add(Calendar.MONTH,1);
							
						}
					}   				
				}
			}
		}

		totalList.add(dateMap);
		totalList.add(mnthYears);
		
		return totalList;
   }
   
   public Date isLockinPeriodOver(String borrowerid,Date filterDate) throws DatabaseException
   {
		CPDAO cpDAO = new CPDAO(); 
		ClaimsProcessor cpProcessor = new ClaimsProcessor(); 
   		
		Log.log(Log.INFO,"IFProcessor","isLockinPeriodOver()","Entered");
		Vector lockindetails = cpDAO.getLockInDetails(borrowerid);
		String lckdtl_cgpan = null;
		java.util.Date lckdtl_gstartdate = null;
		java.util.Date lckdtl_dtlastdsbrsmnt = null;
		java.util.Date lckdtl_lockin_start_date = null;
		java.util.Date tempdate = null;
		if(lockindetails.size() == 0)
		{
			return null;
		}

		for(int i=0; i<lockindetails.size(); i++)
		{
			HashMap temp = (HashMap)lockindetails.elementAt(i);
			//System.out.println("Printing the HashMap :" + temp);
			lckdtl_cgpan = (String)temp.get(ClaimConstants.CLM_CGPAN);
			lckdtl_gstartdate = (java.util.Date)temp.get(ClaimConstants.CLM_GUARANTEE_START_DT);
			lckdtl_dtlastdsbrsmnt = (java.util.Date)temp.get(ClaimConstants.CLM_LAST_DISBURSEMENT_DT);
		   if(lckdtl_cgpan != null)
		   {
			   if((lckdtl_gstartdate != null) && (lckdtl_dtlastdsbrsmnt != null))
			   {
					if((lckdtl_gstartdate.compareTo(lckdtl_dtlastdsbrsmnt)) < 0)
					{
						  tempdate = lckdtl_dtlastdsbrsmnt;
					}
					if((lckdtl_gstartdate.compareTo(lckdtl_dtlastdsbrsmnt)) > 0)
					{
						  tempdate = lckdtl_gstartdate;
					}
					if((lckdtl_gstartdate.compareTo(lckdtl_dtlastdsbrsmnt)) == 0)
					{
						  tempdate = lckdtl_gstartdate;
					}
				}
				else if((lckdtl_gstartdate != null) && (lckdtl_dtlastdsbrsmnt == null))
				{
					tempdate = lckdtl_gstartdate;
				}
				else
				{
					continue;
				}

				if(i == 0)
				{
					  lckdtl_lockin_start_date = tempdate;
				}
				else
				{
					if(lckdtl_lockin_start_date != null)
					{
						if((lckdtl_lockin_start_date.compareTo(tempdate)) > 0)
						{
							lckdtl_lockin_start_date = tempdate;
						}
					}
				}
		   }
		   else
		   {
			   continue;
		   }
		}

		Administrator admin = new Administrator();
		// ParameterMaster parameterMaster = new ParameterMaster();
		ParameterMaster parameterMaster = admin.getParameter();

		int lockinperiodmonths = parameterMaster.getLockInPeriod();
		if (lockinperiodmonths==-1)
		{
			return null;
		}
		else
		{
		   // System.out.println("lockinperiodmonths :" + lockinperiodmonths);
		   // System.out.println("Lock In Period Start Date to the GetDate() method :" + lckdtl_lockin_start_date);
		   java.util.Date lockinperiodenddate = cpProcessor.getDate(lckdtl_lockin_start_date,lockinperiodmonths);
		   java.util.Date currentDate=new java.util.Date();
		   
		   Log.log(Log.INFO,"IFProcessor","isLockinPeriodOver()","lockinperiodenddate :" + lockinperiodenddate);

		   if(lockinperiodenddate.before(filterDate))
		   {
/*			Calendar instance1 = Calendar.getInstance();
			Calendar instance2 = Calendar.getInstance();
							
			instance1.setTime(lockinperiodenddate);
			instance2.setTime(filterDate);
				if(instance1.get(Calendar.MONTH) < instance2.get(Calendar.MONTH))
				{
*/					return lockinperiodenddate;			
/*				}
				else{
					
					return null;
				}
*/		   	
		   }
		   else
		   {
			  Log.log(Log.INFO,"IFProcessor","isLockinPeriodOver()","Exited - 4");
			  return null;
		   }
	   }
   }
   
   
   private String getMonth(int calendarMonth)
   {
	   String month = "";
	   if(calendarMonth==0)
	   {
		   month="JAN";
	   }
	   else if(calendarMonth==1){
					
		   month="FEB";
	   }
	   else if(calendarMonth==2){
					
		   month="MAR";
	   }
	   else if(calendarMonth==3){
					
		   month="APR";
	   }
	   else if(calendarMonth==4){
					
		   month="MAY";
	   }
	   else if(calendarMonth==5){
					
		   month="JUN";
	   }
	   else if(calendarMonth==6){
					
		   month="JUL";
	   }
	   else if(calendarMonth==7){
					
		   month="AUG";
	   }
	   else if(calendarMonth==8){
					
		   month="SEP";
	   }
	   else if(calendarMonth==9){
					
		   month="OCT";
	   }
	   else if(calendarMonth==10){
					
		   month="NOV";
	   }
	   else if(calendarMonth==11){
					
		   month="DEC";
	   }

	   return month;
   }
   
   
	public ArrayList getMaturedVoucherDetails(Date fromDate, Date toDate)throws DatabaseException
	{
		ArrayList invVoucherDetails = ifDAO.getMaturedVoucherDetails(fromDate,toDate);
		
		return invVoucherDetails;
	}
	
	public void insertVoucherInvDetails(ArrayList invVoucherDetails,Date fromDate, Date toDate,String contextPath,User user)throws DatabaseException,MessageException
	{
		Log.log(Log.INFO,"IFProcessor","insertVoucherInvDetails","entered");
		
		ArrayList vouchers = new ArrayList();
		
		String narration ="";
		
		RpProcessor rpProcessor = new RpProcessor();
		
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
		
		for(int i=0; i<invVoucherDetails.size(); i++)
		{
			narration ="";
			InvestmentDetails invDetail = (InvestmentDetails)invVoucherDetails.get(i);
			double principalAmt = invDetail.getInvestmentAmount();
			double maturedAmount = invDetail.getMaturityAmount();
			double tdsAmount = invDetail.getInvestedDateNav();
			String investeeName = invDetail.getInvestee();
			String invRefNumber = invDetail.getInvestmentNumber();
			int instrumentKey = invDetail.getInstruments();
			
			VoucherDetail voucherDetail = new VoucherDetail();
			vouchers.clear();
			voucherDetail.setBankGLCode(accCodes.getProperty(RpConstants.BANK_AC));
			voucherDetail.setBankGLName("");
			voucherDetail.setDeptCode(RpConstants.RP_CGTSI);
			voucherDetail.setAmount(maturedAmount);
			voucherDetail.setVoucherType(RpConstants.RECEIPT_VOUCHER);
			
			if(tdsAmount!=0)
			{
				Voucher voucher1 = new Voucher();
				voucher1.setAcCode(accCodes.getProperty(RpConstants.INVESTMENT_TDS_AC));
				voucher1.setPaidTo("CGTSI");
				voucher1.setDebitOrCredit("D");
				voucher1.setInstrumentDate(null);
				voucher1.setInstrumentNo(null);
				voucher1.setInstrumentType(null);
				voucher1.setAmountInRs(tdsAmount+"");
				
				vouchers.add(voucher1);
			}
			
			Voucher voucher2 = new Voucher();
			voucher2.setAcCode(accCodes.getProperty(RpConstants.INVESTMENT_INTEREST_AC));
			voucher2.setPaidTo("CGTSI");
			voucher2.setDebitOrCredit("C");
			voucher2.setInstrumentDate(null);
			voucher2.setInstrumentNo(null);
			voucher2.setInstrumentType(null);
			voucher2.setAmountInRs((maturedAmount-principalAmt)+"");
			
			vouchers.add(voucher2);

			Voucher voucher3 = new Voucher();
			voucher3.setAcCode(accCodes.getProperty(RpConstants.INVESTMENT_AC));
			voucher3.setPaidTo("CGTSI");
			voucher3.setDebitOrCredit("C");
			voucher3.setInstrumentDate(null);
			voucher3.setInstrumentNo(null);
			voucher3.setInstrumentType(null);
			voucher3.setAmountInRs(principalAmt+"");
			
			vouchers.add(voucher3);
			
			narration = narration + "Investment Reference Number: " + invRefNumber ;
			narration = narration + " Investment Name: " + investeeName ;
			
			voucherDetail.setNarration(narration);
			voucherDetail.setVouchers(vouchers);

			String voucherId = rpProcessor.insertVoucherDetails(voucherDetail, user.getUserId());
			
			Log.log(Log.INFO,"IFProcessor","insertVoucherInvDetails","voucherId :" + voucherId);
			ifDAO.updateVoucherId(voucherId,instrumentKey);
			
			
		}
	}
	
	public void insertVoucherForFulfillment(InvestmentFulfillmentDetail invFulfillmentDetails,String contextPath,User user)throws DatabaseException,MessageException
	{
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
		voucherDetail.setBankGLName(invFulfillmentDetails.getDrawnBank());
		voucherDetail.setDeptCode(RpConstants.RP_CGTSI);
		voucherDetail.setAmount(invFulfillmentDetails.getInstrumentAmount());
		voucherDetail.setVoucherType(RpConstants.PAYMENT_VOUCHER);
		
		Voucher voucher = new Voucher();
		voucher.setAcCode(accCodes.getProperty(RpConstants.BANK_AC));
		voucher.setPaidTo("CGTSI");
		voucher.setDebitOrCredit("C");
		voucher.setInstrumentDate(invFulfillmentDetails.getInstrumentDate().toString());
		voucher.setInstrumentNo(invFulfillmentDetails.getInstrumentNumber());
		voucher.setInstrumentType(invFulfillmentDetails.getInstrumentType());
		voucher.setAmountInRs((invFulfillmentDetails.getInstrumentAmount())+"");
				
		vouchers.add(voucher);
		
		if(invFulfillmentDetails.getInvestmentRefNumber()!=null && !invFulfillmentDetails.getInvestmentRefNumber().equals(""))
		{
			narration = narration + "Investment Reference Number: " + invFulfillmentDetails.getInvestmentRefNumber();
		}
		narration = narration + " Investee Name : " + invFulfillmentDetails.getInvesteeName();
		
		voucherDetail.setVouchers(vouchers);
		voucherDetail.setNarration(narration);	
		
		rpProcessor.insertVoucherDetails(voucherDetail,user.getUserId());	
		
	}
   
   
   public Map getFundTransfersForDate(Date date) throws DatabaseException
   {
	return ifDAO.getFundTransfersForDate(date);
   }
 
   public void updateFundTransfers(Date date, Map fundTransfers, String userId) throws DatabaseException
   {
	   ifDAO.updateFundTransfers(date, fundTransfers, userId);
   }
   
   public BankReconcilation getBankReconDetails(Date date) throws DatabaseException
   {
	return ifDAO.getBankReconDetails(date);
   }
   
   public void updateBankRecon(Date date, BankReconcilation bankReconcilation, String userId) throws DatabaseException
   {
	   ifDAO.updateBankRecon(date, bankReconcilation, userId);
   }
   
   public void insertChqLeavesDetails(ChequeLeavesDetails chqLeavesdetails,User user) throws DatabaseException
   {
	   ifDAO.insertChqLeavesDetails(chqLeavesdetails, user);
   }
   
   public ArrayList getBankChqLeavesDetails(String bankName, String branchName,String bnkAcctNo) throws DatabaseException
   {
		return ifDAO.getBankChqLeavesDetails(bankName,branchName,bnkAcctNo);
   }
   
   public void updateChqLeavesDetails(ChequeLeavesDetails chqLeavesdetails,User user) throws DatabaseException
   {
	   ifDAO.updateChqLeavesDetails(chqLeavesdetails, user);
   }

   public ArrayList getUnUtilizedChqLeaves(String bankName, String branchName,String bnkAcctNo) throws DatabaseException
   {
		return ifDAO.getUnUtilizedChqLeaves(bankName,branchName,bnkAcctNo);
   }
   
   public void saveCancelledChqDetails(ChequeDetails chequeDetails,User user) throws DatabaseException
   {
	   ifDAO.saveCancelledChqDetails(chequeDetails, user);
   }
   
   public Map showInflowOutflowReport(Date date, Map invMatDetails, String userId) throws DatabaseException
   {
	   return ifDAO.showInflowOutflowReport(date, invMatDetails, userId);
   }
   public void saveInflowOutflowReport(Date date, Map invMainDetails, Map invProvisionDetails, String userId) throws DatabaseException
   {
	   ifDAO.saveInflowOutflowReport(date, invMainDetails, invProvisionDetails, userId);
   }

   public String getBranchForBank(String bankName) throws DatabaseException
   {
	   return ifDAO.getBranchForBank(bankName);
   }
   
   public ArrayList  getFdReportForMaturityDate(java.sql.Date startDate,
						 java.sql.Date endDate) throws DatabaseException
   {
	   Log.log(Log.INFO,"IFProcessor","getFdReportForMaturityDate","Entered");
		ArrayList bankDetailsArray = new ArrayList();
	   try
	   {
			bankDetailsArray = ifDAO.getFdReportForMaturityDate(startDate, endDate);
	   }
	   catch(Exception exception)
	   {
		   throw new DatabaseException(exception.getMessage());
	   }
	   Log.log(Log.INFO,"IFProcessor","getFdReportForMaturityDate","Exited");
	   return bankDetailsArray;
   }
   
   public ArrayList  getFdReportForDepositDate(java.sql.Date startDate,
						 java.sql.Date endDate) throws DatabaseException
   {
	   Log.log(Log.INFO,"IFProcessor","getFdReportForDeposit","Entered");
		ArrayList bankDetailsArray = new ArrayList();
	   try
	   {
			bankDetailsArray = ifDAO.getFdReportForDepositDate(startDate, endDate);
	   }
	   catch(Exception exception)
	   {
		   throw new DatabaseException(exception.getMessage());
	   }
	   Log.log(Log.INFO,"IFProcessor","getFdReportForDeposit","Exited");
	   return bankDetailsArray;
   }
   
   public ChequeDetails chequeDetailsUpdatePageReport(String chequeNumber)throws DatabaseException
   {
	   Log.log(Log.INFO,"IFProcessor","chequeDetailsUpdatePageReport","Entered");
		ChequeDetails chequeArray = null;
	   try
	   {
		chequeArray = ifDAO.chequeDetailsUpdatePageReport(chequeNumber);
	   }
	   catch(Exception exception)
	   {
		   throw new DatabaseException(exception.getMessage());
	   }
	   Log.log(Log.INFO,"IFProcessor","chequeDetailsUpdatePageReport","Exited");
	   return chequeArray;
   }

}
