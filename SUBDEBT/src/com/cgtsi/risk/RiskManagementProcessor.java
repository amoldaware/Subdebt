package com.cgtsi.risk;
import com.cgtsi.common.DatabaseException;
import com.cgtsi.common.MessageException;
import java.util.ArrayList;
import java.util.Date;

import com.cgtsi.common.Log;

import com.cgtsi.mcgs.ParticipatingBankLimit;

import com.cgtsi.application.Application;


/*************************************************************
   *
   * Name of the class: RiskManagementProcessor
   * This is main manager level class of this module. This class has methods to set 
   * global limits, user limits, to calculate risk exposure and to generate risk 
   * exposure report.
   * 
   * @author : Nithyalakshmi P.
   * @version:  
   * @since: 
   **************************************************************/

public class RiskManagementProcessor 
{
//   public RIHelper theRIHelper;
   public RIDAO riDAO;
   
   public RiskManagementProcessor() 
   {
	   riDAO=new RIDAO();
   }


   public ArrayList defaultRateReport(java.sql.Date settlementDate) throws DatabaseException
   {
   		ArrayList defaultArray = new ArrayList();
	   if (settlementDate != null) 
	   {
		defaultArray = riDAO.defaultRateReport(settlementDate);  
	   }
	   return defaultArray;
   }
   
   /**
    * This method stores the values, stored in Global Limits object. This method 
    * passes the global limits object to RIDAO class to be stored in the database..
    * @param objGlobalLimits
    */
   public void updateLimitsForGlobalSettings(GlobalLimits globalLimits, String userId) throws DatabaseException
   {
	   if (globalLimits != null)
	   {
			riDAO.updateLimitsForGlobalSettings(globalLimits, userId);
	   }
   }

   /**
    * This method stores the sub scheme parameters and values. This method 
    * passes the SubSchemeParamters and SubSchemeValues objects to RIDAO class to be stored in the database..
    * @param subSchemeParameters, subSchemeValues
	* @throws DatabaseException
	*/
   public void setSubSchemeDetails(SubSchemeParameters subSchemeParameters, SubSchemeValues subSchemeValues, String userId) throws DatabaseException
   {
	   if ((subSchemeParameters != null) && (subSchemeValues != null))
	   {
			riDAO.setSubSchemeDetails(subSchemeParameters, subSchemeValues, userId);
	   }
   }

	/**
    * This method updates the sub scheme parameters and values. This method 
    * passes the SubSchemeParamters and SubSchemeValues objects to RIDAO class to be stored in the database.
    * @param subSchemeParameters, subSchemeValues
	* @throws DatabaseException
    *
   public void updateSubSchemeDetails(SubSchemeParameters subSchemeParameters, SubSchemeValues subSchemeValues) throws DatabaseException
   {
	   if ((subSchemeParameters != null) && (subSchemeValues != null))
	   {
			riDAO.updateSubSchemeDetails(subSchemeParameters, subSchemeValues);
	   }
   }

   
   /**
    * This method stores the limits for each CGTSI user. The user limits object is 
    * passed on to RIDAO class which actually stores the values in the database.
    * @param objUserLimits
    * @return boolean
    */
   public void updateUserLimits(UserLimits userLimits, String userId) throws Exception
   {
	   if (userLimits != null)
	   {
			riDAO.updateUserLimits(userLimits, userId);
	   }
   }
   
   /**
    * This method is for calculating the risk exposure. The user enters some 
    * parameters for which he wants risk exposure calculated. The parameters are 
    * stored in a collections object and passed on to this method. This method passes 
    * on the parameters to RIDAO class and retrieves the values from the database. 
    * The risk exposure is then calculated and displayed to the user.
    * @param objParameters
    * @return Collections
    */
   public ExposureSummary calculateExposure(ExposureSummary exposureSummary) throws DatabaseException
   {
		Log.log(Log.INFO,"RiskMangementProcessor","calculateExposure","Entered");
		exposureSummary=riDAO.getExposureDetails(exposureSummary);

		Log.log(Log.INFO,"RiskMangementProcessor","calculateExposure","Exited");
		return exposureSummary;
   }
   
   /**
    * This method retrieves all the scheme names.
	* This method invokes the getValuesFromSchemeMaster of the DAO class.
	*
    * @return ArrayList
    */
   public ArrayList getValuesFromSchemeMaster() throws DatabaseException
   {
	   ArrayList schemes;
	   schemes=riDAO.getValuesFromSchemeMaster();

    return schemes;
   }
   
   /**
    * This method returns the list of sub scheme names.
	* @return ArrayList
	* @throws DatabaseException
	*/
	public ArrayList getAllSubSchemeNames() throws DatabaseException
	{
		ArrayList subSchemeNames=riDAO.getAllSubSchemeNames();
		return subSchemeNames;
	}

	/**
    * This method returns the sub scheme parameter values for the given sub scheme name.
	* @return SubSchemeParameters
	*/
	public SubSchemeParameters getSubSchemeDetails(String subSchemeName) throws DatabaseException
	{
		SubSchemeParameters subSchemeParameters=riDAO.getSubSchemeDetails(subSchemeName);
		return subSchemeParameters;
	}

	/**
    * This method returns the sub scheme values for the given sub scheme name.
	* @return SubSchemeValues
	*/
	public SubSchemeValues getSubSchemeValues(String subSchemeName) throws DatabaseException
	{
		SubSchemeValues subSchemeValues=riDAO.getSubSchemeValues(subSchemeName);
		return subSchemeValues;
	}
	
	/**
	* This method returns the sub scheme values for the given sub scheme id.
	* @return SubSchemeValues
	*/
	public SubSchemeValues getSubSchemeValuesForId(String subSchemeId) throws DatabaseException
	{
		SubSchemeValues subSchemeValues=riDAO.getSubSchemeValuesForId(subSchemeId);
		return subSchemeValues;
	}

   /**
    * This method stores the limits for banks part of the MCGF. The participating bank limits object is 
    * passed on to RIDAO class which actually stores the values in the database.
    * @param participatingBankLimits
    * @return void
    */
   public void updateParticipatingBankLimits(ParticipatingBankLimit participatingBankLimits, String userId) throws DatabaseException
   {
	   if (participatingBankLimits != null)
	   {
	   		riDAO.updateParticipatingBankLimits(participatingBankLimits, userId);
	   }
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
	   return riDAO.getSubScheme(application);
   }

   /**
    * This method generates the exposure report for the parameter combination.
    * @param parameterCombination
    * @return ExposureReportDetail
    */
   public ExposureReportDetail generateExposureReport(ParameterCombination parameterCombination) throws DatabaseException
   {
	   ExposureReportDetail exposureReportDetail;
   	   exposureReportDetail = riDAO.generateExposureReport(parameterCombination);
	   return exposureReportDetail;
   }

   /**
	* This method generates the sub scheme report valid between the given from and to dates.
	* @returns ArrayList
	*/
	public ArrayList getSubSchemes(Date fromDate, Date toDate) throws DatabaseException, MessageException
	{
		ArrayList returnList = riDAO.getSubSchemes(fromDate, toDate);
		return returnList;
	}
	
	/**
	 * This method returns the global limit values
	 * @returns ArrayList
	 */
	 public GlobalLimits getGlobalLimits(String scheme,String subScheme) throws DatabaseException
	 {
		GlobalLimits globalLimits = riDAO.getGlobalLimits(scheme, subScheme);
		 return globalLimits;
	 }

	/**
	 * This method returns the user limit values
	 * @returns ArrayList
	 */
	 public UserLimits getUserLimits(String dsgName) throws DatabaseException
	 {
		UserLimits userLimits = riDAO.getUserLimits(dsgName);
		 return userLimits;
	 }

	public ParticipatingBankLimit getParticipatingBanksLimits(String memberId, String bankName) throws DatabaseException
	{
		ParticipatingBankLimit participatingBankLimit = riDAO.getParticipatingBanksLimits(memberId, bankName);
		return participatingBankLimit;
	}
	
}
