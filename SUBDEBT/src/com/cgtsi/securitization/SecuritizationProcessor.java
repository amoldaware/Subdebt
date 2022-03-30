/*************************************************************
   *
   * Name of the class: SecuritizationProcessor
   * This class is repsonsible for handling securitization sub-system related 
   * requests. 
   *  
   *
   * @author : Veldurai T
   * @version:  
   * @since: Nov 21, 2003
   **************************************************************/


package com.cgtsi.securitization;

import java.util.ArrayList;

import com.cgtsi.common.DatabaseException;
import com.cgtsi.common.Log;
import com.cgtsi.admin.DuplicateException;
import com.cgtsi.admin.User;
import com.cgtsi.application.ApplicationConstants;

/**
 * This class is repsonsible for handling securitization sub-system related 
 * requests.
 */
public class SecuritizationProcessor 
{
	SecuritizationDAO securitizationDAO=new SecuritizationDAO();   
   /**
    * @roseuid 3A164701023D
    */
   public SecuritizationProcessor() 
   {
    
   }
   
   /**
    * This method is used to get all the homogenous loans from database based on the 
    * given select criteria. Only non-default, non-NPA accounts, accounts not closed, 
    * not cancelled need to be considered. This method returns an ArrayList of 
    * HomogenousLoan objects.
    * @param selectCriteria
    * @return ArrayList
    * @throws DatabaseException
    * @roseuid 39FBE68F000D
    */
   public ArrayList getHomogenousLoans(SelectCriteria selectCriteria) throws DatabaseException 
   {
    	//return securitizationDAO.getHomogenousLoans(selectCriteria);
	
    	if(selectCriteria.getLoanType().equalsIgnoreCase(ApplicationConstants.BOTH_APPLICATION))
    	{
    		selectCriteria.setLoanType(ApplicationConstants.TC_APPLICATION);
			ArrayList tcLoans=securitizationDAO.getHomogenousLoans(selectCriteria);
			
			selectCriteria.setLoanType(ApplicationConstants.WC_APPLICATION);
			ArrayList wcLoans=securitizationDAO.getHomogenousLoans(selectCriteria);
			
			selectCriteria.setLoanType(ApplicationConstants.BOTH_APPLICATION);
			
			ArrayList consolidatedLoans=new ArrayList();
			
			consolidatedLoans.add(tcLoans);
			consolidatedLoans.add(wcLoans);
			
			return consolidatedLoans;
    	}
    	else
    	{
			return securitizationDAO.getHomogenousLoans(selectCriteria);
    	}
    	
   }
   
   /**
    * This method is used to capture the loan pool details. The loans selected for 
    * the trench are marked so that they will not participate in another trench 
    * creation. This method throws DuplicateException if the pool name already 
    * exists.After creating a pool, a unique number called pool number is generated.
    * @param loanPool
    * @param selectCriteria
    * @param createdBy
    * @throws DatabaseException
    * @throws DuplicateException
    * @roseuid 39FBF32902AA
    */
   public int createLoanPool(LoanPool loanPool, SelectCriteria selectCriteria, User createdBy) throws DatabaseException, DuplicateException 
   {
		Log.log(Log.INFO,"SecuritizationProcessor","createLoanPool","Entered");
	   		
		int poolId=securitizationDAO.createLoanPool(loanPool,selectCriteria,createdBy);
		
		
		Log.log(Log.INFO,"SecuritizationProcessor","createLoanPool","Exited");
		
		return poolId;
    	
   }
   
   public ArrayList getLoanPoolNames() throws DatabaseException
   {
		Log.log(Log.INFO,"SecuritizationProcessor","getLoanPoolNames","Entered");
		
		ArrayList poolNames=securitizationDAO.getLoanPoolNames();
		Log.log(Log.INFO,"SecuritizationProcessor","getLoanPoolNames","Exited");
		
		return poolNames;
   }
   
   /**
    * This method is used to add the investor details for the just created loan pool.
 	* @param poolId 	The created pool id.
 	* @param investorName	The investor Name
 	* @param amount			Amount invested
 	* @throws DatabaseException	throws DatabaseException if any error occured 
 	* 							while inserting data into database.
 	*/
	public void addInvestor(int poolId,Investor investor) throws DatabaseException
   	{
		Log.log(Log.INFO,"SecuritizationProcessor","addInvestor","Entered");
		
		securitizationDAO.addInvesterDetails(poolId,investor);
		
		Log.log(Log.INFO,"SecuritizationProcessor","addInvestor","Exited");
   	}
	/**
	 * This method is used to get the homogenous loan pool details for the selected state.
	 * @param sector 			The selected Sector
	 * @param state				The selected state
	 * @param selectCriteria	The select criteria
	 * @throws DatabaseException	throws DatabaseException if any error occured 
	 * 							while selecting data from the  database.
	 */
   	
	public StateWise getStateWiseLoans(String sector,String state,SelectCriteria selectCriteria)
	 throws DatabaseException
	{
		Log.log(Log.INFO,"SecuritizationProcessor","getStateWiseLoans","Entered");
		
		StateWise stateWise=securitizationDAO.getStateWiseLoans(sector,state,selectCriteria);
		
		Log.log(Log.INFO,"SecuritizationProcessor","getStateWiseLoans","Exited");
		
		return stateWise;		
	}
}
