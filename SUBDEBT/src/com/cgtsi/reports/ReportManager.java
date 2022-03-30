 /*
 * Created on Nov 22, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.cgtsi.reports;

import com.cgtsi.admin.User;

/**
 * @author RT14509
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

import com.cgtsi.claim.CPDAO;
import com.cgtsi.common.DatabaseException;
import com.cgtsi.common.Log;
import com.cgtsi.common.Constants;
import com.cgtsi.claim.ClaimConstants;
//import java.sql.Date;
import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;
import com.cgtsi.claim.ClaimApplication;

public class ReportManager
{
	private ReportDAO reportDao = null;
	private CPDAO cpDao;

	public ReportManager()
	{
		reportDao = new ReportDAO();		
		cpDao = null; 		// 
	}

	public ArrayList otsReport(java.sql.Date startDate,
                             java.sql.Date endDate
                             )throws DatabaseException{
                                   
          Log.log(Log.INFO,"ReportsManager","otsReport","Entered");
          ArrayList danRaisedArray = null;
          try
          {
            danRaisedArray = reportDao.otsReport(startDate,endDate);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","otsReport","Exited");
          return danRaisedArray;
	}

	public ArrayList otsReportDetails(java.sql.Date startDate,
                                    java.sql.Date endDate,
                                    String flag
                                    )throws DatabaseException{
           
            Log.log(Log.INFO,"ReportsManager","otsReportDetails","Entered");
            ArrayList danRaisedArray = new ArrayList();
            try
            {
              danRaisedArray = reportDao.otsReportDetails(startDate,endDate,flag);
            }
            catch(Exception exception)
            {
              throw new DatabaseException(exception.getMessage());
            }
            Log.log(Log.INFO,"ReportsManager","otsReportDetails","Exited");
            return danRaisedArray;
		}


	public ArrayList guaranteeCoverMli(java.sql.Date startDate,
                                     java.sql.Date endDate
                                     )throws DatabaseException{

          Log.log(Log.INFO,"ReportsManager","guaranteeCoverMli","Entered");
          ArrayList danRaisedArray = null;
          try
          {
            danRaisedArray = reportDao.guaranteeCoverMli(startDate,endDate);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","guaranteeCoverMli","Exited");
          return danRaisedArray;
	}

	public ArrayList guaranteeCoverSsiMli(java.sql.Date startDate,
                                        java.sql.Date endDate
                                        )throws DatabaseException{
                                              
          Log.log(Log.INFO,"ReportsManager","guaranteeCoverSsiMli","Entered");
          ArrayList danRaisedArray = null;
          try
          {
            danRaisedArray = reportDao.guaranteeCoverSsiMli(startDate,endDate);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","guaranteeCoverSsiMli","Exited");
          return danRaisedArray;
	}



//Fix 070904 - 09,10
	public ArrayList getAllCgpans() throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","shortReport","Entered");
		ArrayList danRaisedArray = null;
		try
		{
			danRaisedArray = reportDao.getAllCgpans();
		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","shortReport","Exited");
		return danRaisedArray;
	}
//Fix Completed

	public ArrayList shortReport(java.sql.Date startDate,
                               java.sql.Date endDate
                               )throws DatabaseException{
                                     
          Log.log(Log.INFO,"ReportsManager","shortReport","Entered");
          ArrayList danRaisedArray = null;
          try
          {
            danRaisedArray = reportDao.shortReport(startDate,endDate);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","shortReport","Exited");
          return danRaisedArray;
	}

public ArrayList dayReport(java.sql.Date endDate)throws DatabaseException{
                                     
          Log.log(Log.INFO,"ReportsManager","dayReport","Entered");
          ArrayList daRaisedArray = null;
          try
          {
            daRaisedArray = reportDao.dayReport(endDate);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","dayReport","Exited");
          return daRaisedArray;
	}

	public ArrayList shortReportDetails(java.sql.Date startDate,java.sql.Date endDate,
										String flag)  throws DatabaseException
		{
			Log.log(Log.INFO,"ReportsManager","shortReportDetails","Entered");
			ArrayList danRaisedArray = new ArrayList();
			try
			{
				danRaisedArray = reportDao.shortReportDetails(startDate,endDate,flag);
			}
			catch(Exception exception)
			{
				throw new DatabaseException(exception.getMessage());
			}
			Log.log(Log.INFO,"ReportsManager","shortReportDetails","Exited");
			return danRaisedArray;
		}


	public ArrayList excessReport(java.sql.Date startDate,
                                java.sql.Date endDate
                                ) throws DatabaseException{
                                      
          Log.log(Log.INFO,"ReportsManager","excessReport","Entered");
          ArrayList danRaisedArray = null;
          try
          {
            danRaisedArray = reportDao.excessReport(startDate,endDate);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","excessReport","Exited");
          return danRaisedArray;
	}


	public ArrayList excessReportDetails(java.sql.Date startDate,java.sql.Date endDate,
										String flag)  throws DatabaseException
		{
			Log.log(Log.INFO,"ReportsManager","excessReportDetails","Entered");
			ArrayList danRaisedArray = new ArrayList();
			try
			{
				danRaisedArray = reportDao.excessReportDetails(startDate,endDate,flag);
			}
			catch(Exception exception)
			{
				throw new DatabaseException(exception.getMessage());
			}
			Log.log(Log.INFO,"ReportsManager","excessReportDetails","Exited");
			return danRaisedArray;
		}

	public ArrayList securitizationReport(java.sql.Date startDate,
                                        java.sql.Date endDate
                                        ) throws DatabaseException{
                                              
          Log.log(Log.INFO,"ReportsManager","securitizationReport","Entered");
          ArrayList danRaisedArray = null;
          try
          {
            danRaisedArray = reportDao.securitizationReport(startDate,endDate);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","securitizationReport","Exited");
          return danRaisedArray;
	}

/**
   * 
   * @param startDate
   * @param endDate
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
public ArrayList inwardReport(java.sql.Date startDate,
                                        java.sql.Date endDate
                                        ) throws DatabaseException{
                                              
          Log.log(Log.INFO,"ReportsManager","inwardReport","Entered");
          ArrayList inwardList = null;
          try
          {
            inwardList = reportDao.inwardReportDetails(startDate,endDate);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","inwardReport","Exited");
          return inwardList;
	}
  
  
  /**
   * 
   * @param startDate
   * @param endDate
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
  public ArrayList workshopReport(java.sql.Date startDate,
                                        java.sql.Date endDate
                                        ) throws DatabaseException{
                                              
          Log.log(Log.INFO,"ReportsManager","workshopReport","Entered");
          ArrayList inwardList = null;
          try
          {
            inwardList = reportDao.workshopReportDetails(startDate,endDate);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","workshopReport","Exited");
          return inwardList;
	}
  
  public ArrayList mliworkshopReport(java.sql.Date startDate,
                                        java.sql.Date endDate
                                        ) throws DatabaseException{
                                              
          Log.log(Log.INFO,"ReportsManager","mliworkshopReport","Entered");
          ArrayList mliworkshopReports = null;
          try
          {
            mliworkshopReports = reportDao.mliworkshopReportDetails(startDate,endDate);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","mliworkshopReport","Exited");
          return mliworkshopReports;
	}
  
  
  
  public ArrayList stateworkshopReportDetails(java.sql.Date startDate,
                                        java.sql.Date endDate
                                        ) throws DatabaseException{
                                              
          Log.log(Log.INFO,"ReportsManager","stateworkshopReportDetails","Entered");
          ArrayList stateworkshopReports = null;
          try
          {
            stateworkshopReports = reportDao.stateworkshopReportDetails(startDate,endDate);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","stateworkshopReportDetails","Exited");
          return stateworkshopReports;
	}
  
  /**
   * 
   * @param startDate
   * @param endDate
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
   public ArrayList asf2011notallocatedSummaryDtl(java.sql.Date startDate,
                                        java.sql.Date endDate
                                        ) throws DatabaseException{
                                              
          Log.log(Log.INFO,"ReportsManager","asf2011notallocatedSummaryDtl","Entered");
          ArrayList asf2011notallocatedSummaryDtls = null;
          try
          {
            asf2011notallocatedSummaryDtls = reportDao.asf2011notallocatedSummary(startDate,endDate);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","asf2011notallocatedSummaryDtl","Exited");
          return asf2011notallocatedSummaryDtls;
	}
  
  
  
  
  
   public ArrayList agencyworkshopReportDetails(java.sql.Date startDate,
                                        java.sql.Date endDate
                                        ) throws DatabaseException{
                                              
          Log.log(Log.INFO,"ReportsManager","agencyworkshopReportDetails","Entered");
          ArrayList stateworkshopReports = null;
          try
          {
            stateworkshopReports = reportDao.agencyworkshopReportDetails(startDate,endDate);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","agencyworkshopReportDetails","Exited");
          return stateworkshopReports;
	}
  
   public ArrayList schemeworkshopReportDetails(java.sql.Date startDate,
                                        java.sql.Date endDate
                                        ) throws DatabaseException{
                                              
          Log.log(Log.INFO,"ReportsManager","schemeworkshopReportDetails","Entered");
          ArrayList schemeWorkshopDetails = null;
          try
          {
            schemeWorkshopDetails = reportDao.schemeworkshopReportDetails(startDate,endDate);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","schemeworkshopReportDetails","Exited");
          return schemeWorkshopDetails;
	}
  
  
  
  /**
   * 
   * @param startDate
   * @param endDate
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
  public ArrayList investmentReport(java.sql.Date startDate,
                                        java.sql.Date endDate
                                        ) throws DatabaseException{
                                              
          Log.log(Log.INFO,"ReportsManager","investmentReport","Entered");
          ArrayList investmentList = null;
          try
          {
            investmentList = reportDao.investmentReportDetails(startDate,endDate);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","investmentReport","Exited");
          return investmentList;
	}
  
  /**
   * 
   * @param startDate
   * @param endDate
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
  public ArrayList npaReport(java.sql.Date startDate,
                                        java.sql.Date endDate,String id
                                        ) throws DatabaseException{
                                              
          Log.log(Log.INFO,"ReportsManager","npaReport","Entered");
          ArrayList npaReport = null;
          try
          {
            npaReport = reportDao.NPAReportDetails(startDate,endDate,id);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","npaReport","Exited");
          return npaReport;
	}
  
  
  /**
   * 
   * @param startDate
   * @param endDate
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
   public ArrayList declarationReceivedCases(java.sql.Date startDate,
                                        java.sql.Date endDate
                                        ) throws DatabaseException{
                                              
          Log.log(Log.INFO,"ReportsManager","declarationReceivedCases","Entered");
          ArrayList declarationReceivedCases = null;
          try
          {
            declarationReceivedCases = reportDao.declarationReceivedCases(startDate,endDate);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","declarationReceivedCases","Exited");
          return declarationReceivedCases;
	}
  
  
  /**
   * 
   * @param startDate
   * @param endDate
   * @param id
   * @param ssi
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
  public ArrayList declarationReceivedCasesNew(java.sql.Date startDate,
                                        java.sql.Date endDate,String id,String ssi
                                        ) throws DatabaseException{
                                              
          Log.log(Log.INFO,"ReportsManager","declarationReceivedCasesNew","Entered");
          ArrayList declarationReceivedCases = null;
          try
          {
            declarationReceivedCases = reportDao.declarationReceivedCasesNew(startDate,endDate,id,ssi);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","declarationReceivedCasesNew","Exited");
          return declarationReceivedCases;
	}
  /**
   * 
   * @param startDate
   * @param endDate
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
   public ArrayList ddDepositReport(java.sql.Date startDate,
                                        java.sql.Date endDate
                                        ) throws DatabaseException{
                                              
          Log.log(Log.INFO,"ReportsManager","ddDepositReport","Entered");
          ArrayList ddDepositReport = null;
          try
          {
            ddDepositReport = reportDao.ddDepositReportDetails(startDate,endDate);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","ddDepositReport","Exited");
          return ddDepositReport;
	}
  
 /**
   * 
   * @param startDate
   * @param endDate
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
 public ArrayList dcHandicraftReport(java.sql.Date startDate,
                                        java.sql.Date endDate,String id
                                        ) throws DatabaseException{
                                              
          Log.log(Log.INFO,"ReportsManager","dcHandicraftReport","Entered");
          ArrayList dcHandicraftReport = null;
          try
          {
            dcHandicraftReport = reportDao.dcHandiCraftReportDetails(startDate,endDate,id);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","dcHandicraftReport","Exited");
          return dcHandicraftReport;
	}
    public ArrayList dcHandloomReport(java.sql.Date startDate,
                                           java.sql.Date endDate,String id
                                           ) throws DatabaseException{
                                                 
             Log.log(Log.INFO,"ReportsManager","dcHandicraftReport","Entered");
             ArrayList dcHandloomReport = null;
             try
             {
              dcHandloomReport = reportDao.dcHandloomReportDetails(startDate,endDate,id);
             }
             catch(Exception exception)
             {
               throw new DatabaseException(exception.getMessage());
             }
             Log.log(Log.INFO,"ReportsManager","dcHandicraftReport","Exited");
             return dcHandloomReport;
           }
public ArrayList AfterNotAppropriatedDetailsfromInward(java.sql.Date startDate,
                                        java.sql.Date endDate
                                        ) throws DatabaseException{
                                              
          Log.log(Log.INFO,"ReportsManager","AfterNotAppropriatedDetailsfromInward","Entered");
          ArrayList notAppropriatedCasesList = null;
          try
          {
            notAppropriatedCasesList = reportDao.AfterNotAppropriatedDetailsfromInward(startDate,endDate);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","AfterNotAppropriatedDetailsfromInward","Exited");
          return notAppropriatedCasesList;
	}



	public ArrayList securitizationReportDetails(java.sql.Date startDate,java.sql.Date endDate,
										String flag)  throws DatabaseException
		{
			Log.log(Log.INFO,"ReportsManager","securitizationReportDetails","Entered");
			ArrayList danRaisedArray = new ArrayList();
			try
			{
				danRaisedArray = reportDao.securitizationReportDetails(startDate,endDate,flag);
			}
			catch(Exception exception)
			{
				throw new DatabaseException(exception.getMessage());
			}
			Log.log(Log.INFO,"ReportsManager","securitizationReportDetails","Exited");
			return danRaisedArray;
		}


	public ApplicationReport securitizationReportDetailsForCgpan(String application)
												 throws DatabaseException
		{
			Log.log(Log.INFO,"ReportsManager","securitizationReportDetailsForCgpan","Entered");
			ApplicationReport statusArray = new ApplicationReport();
			try
			{
				statusArray = reportDao.securitizationReportDetailsForCgpan(application);
			}
			catch(Exception exception)
			{
				throw new DatabaseException(exception.getMessage());
			}
			Log.log(Log.INFO,"ReportsManager","securitizationReportDetailsForCgpan","Exited");
			return statusArray;
		}


	public ArrayList applicationWiseReport(java.sql.Date startDate,
                                         java.sql.Date endDate,
                                         String application
                                         )throws DatabaseException{
			Log.log(Log.INFO,"ReportsManager","applicationWiseReport","Entered");
			ArrayList statusArray = null;
			try
			{
				statusArray = reportDao.applicationWiseReport(startDate,endDate,application);
			}
			catch(Exception exception)
			{
				throw new DatabaseException(exception.getMessage());
			}
			Log.log(Log.INFO,"ReportsManager","applicationWiseReport","Exited");
			return statusArray;
		}

	public ApplicationReport applicationWiseReportDetails(java.sql.Date startDate,java.sql.Date endDate,
									String application)	 throws DatabaseException
		{
			Log.log(Log.INFO,"ReportsManager","applicationWiseReportDetails","Entered");
			ApplicationReport statusArray = new ApplicationReport();
			try
			{
				statusArray = reportDao.applicationWiseReportDetails(startDate,endDate,application);
			}
			catch(Exception exception)
			{
				throw new DatabaseException(exception.getMessage());
			}
			Log.log(Log.INFO,"ReportsManager","applicationWiseReportDetails","Exited");
			return statusArray;
		}


	public ArrayList applicationRecievedReportDetails(java.sql.Date startDate,
                                                    java.sql.Date endDate
                                                    )throws DatabaseException{
          Log.log(Log.INFO,"ReportsManager","applicationRecievedReportDetails","Entered");
          ArrayList danRaisedArray = null;
          try
          {
            danRaisedArray = reportDao.applicationRecievedReportDetails(startDate,endDate);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","applicationRecievedReportDetails","Exited");
          return danRaisedArray;
	}



	public ArrayList getDanRaisedMlis(java.sql.Date startDate,
                                    java.sql.Date endDate
                                    )throws DatabaseException{
        
          Log.log(Log.INFO,"ReportsManager","getDanRaisedMlis","Entered");
          ArrayList danRaisedArray = null;
          try
          {
            danRaisedArray = reportDao.danRaisedMlis(startDate,endDate);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","getDanRaisedMlis","Exited");
          return danRaisedArray;
	}


	public ArrayList getStatusWiseReportDetails(java.sql.Date startDate,java.sql.Date endDate,
									String application)	 throws DatabaseException
		{
			Log.log(Log.INFO,"ReportsManager","getStatusWiseReportDetails","Entered");
			ArrayList statusArray = null;
			try
			{
				//System.out.println("dao invoked");
				statusArray = reportDao.StatusWiseReportDetails(startDate,endDate,application);
			}
			catch(Exception exception)
			{
				throw new DatabaseException(exception.getMessage());
			}
			Log.log(Log.INFO,"ReportsManager","getStatusWiseReportDetails","Exited");
			return statusArray;
		}

	public ArrayList getStatusDetailsForBranch(java.sql.Date startDate,java.sql.Date endDate,
							String memberId,String application)	 throws DatabaseException
		{
			Log.log(Log.INFO,"ReportsManager","getStatusDetailsForBranch","Entered");
			ArrayList statusArray = null;
			try
			{
				statusArray = reportDao.StatusDetailsForBranch(startDate,endDate,memberId,application);
        System.out.println("Status Array Size:"+statusArray.size());
			}
			catch(Exception exception)
			{
				throw new DatabaseException(exception.getMessage());
			}
			Log.log(Log.INFO,"ReportsManager","getStatusDetailsForBranch","Exited");
			return statusArray;
		}

	public ArrayList getStatusDetailsForZone(java.sql.Date startDate,java.sql.Date endDate,
							String bankId,String zoneId,String application)	 throws DatabaseException
		{
			Log.log(Log.INFO,"ReportsManager","getStatusDetailsForZone","Entered");
			ArrayList statusArray = null;
			try
			{
				statusArray = reportDao.StatusDetailsForZone(startDate,endDate,bankId,zoneId,application);
			}
			catch(Exception exception)
			{
				throw new DatabaseException(exception.getMessage());
			}
			Log.log(Log.INFO,"ReportsManager","getStatusDetailsForZone","Exited");
			return statusArray;
		}


	public ArrayList getStatusDetailsForBank(java.sql.Date startDate,java.sql.Date endDate,
						String memberId,String application)	 throws DatabaseException
		{
			Log.log(Log.INFO,"ReportsManager","getStatusDetailsForBank","Entered");
			ArrayList statusArray = null;
			try
			{
				statusArray = reportDao.StatusDetailsForBank(startDate,endDate,memberId,application);
			}
			catch(Exception exception)
			{
				throw new DatabaseException(exception.getMessage());
			}
			Log.log(Log.INFO,"ReportsManager","getStatusDetailsForBank","Exited");
			return statusArray;
		}

	public ArrayList getStatusWiseReportDetails1(java.sql.Date startDate,java.sql.Date endDate,
									String application)	 throws DatabaseException
		{
			Log.log(Log.INFO,"ReportsManager","getStatusWiseReportDetails1","Entered");
			ArrayList statusArray = null;
			try
			{
		//		System.out.println("dao invoked");
				statusArray = reportDao.StatusWiseReportDetails1(startDate,endDate,application);
			}
			catch(Exception exception)
			{
				throw new DatabaseException(exception.getMessage());
			}
			Log.log(Log.INFO,"ReportsManager","getStatusWiseReportDetails1","Exited");
			return statusArray;
		}

	public ArrayList getStatusDetailsForBranch1(java.sql.Date startDate,java.sql.Date endDate,
							String memberId,String application)	 throws DatabaseException
		{
			Log.log(Log.INFO,"ReportsManager","getStatusDetailsForBranch1","Entered");
			ArrayList statusArray = null;
			try
			{
				statusArray = reportDao.StatusDetailsForBranch1(startDate,endDate,memberId,application);
			}
			catch(Exception exception)
			{
				throw new DatabaseException(exception.getMessage());
			}
			Log.log(Log.INFO,"ReportsManager","getStatusDetailsForBranch1","Exited");
			return statusArray;
		}

	public ArrayList getStatusDetailsForZone1(java.sql.Date startDate,java.sql.Date endDate,
							String bankId,String zoneId,String application)	 throws DatabaseException
		{
			Log.log(Log.INFO,"ReportsManager","getStatusDetailsForZone1","Entered");
			ArrayList statusArray = null;
			try
			{
				statusArray = reportDao.StatusDetailsForZone1(startDate,endDate,bankId,zoneId,application);
			}
			catch(Exception exception)
			{
				throw new DatabaseException(exception.getMessage());
			}
			Log.log(Log.INFO,"ReportsManager","getStatusDetailsForZone1","Exited");
			return statusArray;
		}


	public ArrayList getStatusDetailsForBank1(java.sql.Date startDate,java.sql.Date endDate,
						String memberId,String application)	 throws DatabaseException
		{
			Log.log(Log.INFO,"ReportsManager","getStatusDetailsForBank1","Entered");
			ArrayList statusArray = null;
			try
			{
				statusArray = reportDao.StatusDetailsForBank1(startDate,endDate,memberId,application);
			}
			catch(Exception exception)
			{
				throw new DatabaseException(exception.getMessage());
			}
			Log.log(Log.INFO,"ReportsManager","getStatusDetailsForBank1","Exited");
			return statusArray;
		}
    
    /**
   * 
   * @param startDate
   * @param endDate
   * @param memberId
   * @param application
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
    	public ArrayList StatusDetailsForBranchMod(java.sql.Date startDate,java.sql.Date endDate,
						String memberId,String application)	 throws DatabaseException
		{
			Log.log(Log.INFO,"ReportsManager","getStatusDetailsForBank1","Entered");
			ArrayList statusArray = null;
			try
			{
				statusArray = reportDao.StatusDetailsForBranchMod(startDate,endDate,memberId,application);
			}
			catch(Exception exception)
			{
				throw new DatabaseException(exception.getMessage());
			}
			Log.log(Log.INFO,"ReportsManager","getStatusDetailsForBank1","Exited");
			return statusArray;
		}


	public ApplicationReport getApplicationStatusWiseReportDetails(java.sql.Date startDate,java.sql.Date endDate,
									String application)	 throws DatabaseException
		{
			Log.log(Log.INFO,"ReportsManager","getApplicationStatusWiseReportDetails","Entered");
			ApplicationReport statusArray = new ApplicationReport();
			try
			{
				statusArray = reportDao.ApplicationStatusWiseReportDetails(startDate,endDate,application);
			}
			catch(Exception exception)
			{
				throw new DatabaseException(exception.getMessage());
			}
			Log.log(Log.INFO,"ReportsManager","getApplicationStatusWiseReportDetails","Exited");
			return statusArray;
		}


	public ApplicationReport getApplicationStatusWiseReportDetails1(java.sql.Date startDate,java.sql.Date endDate,
									String application)	 throws DatabaseException
		{
			Log.log(Log.INFO,"ReportsManager","getApplicationStatusWiseReportDetails1","Entered");
			ApplicationReport statusArray = new ApplicationReport();
			try
			{
				statusArray = reportDao.ApplicationStatusWiseReportDetails1(startDate,endDate,application);
			}
			catch(Exception exception)
			{
				throw new DatabaseException(exception.getMessage());
			}
			Log.log(Log.INFO,"ReportsManager","getApplicationStatusWiseReportDetails1","Exited");
			return statusArray;
		}



	public ArrayList getDanRaisedReport(java.sql.Date startDate,java.sql.Date endDate,
										String flag)  throws DatabaseException
		{
			Log.log(Log.INFO,"ReportsManager","getDanRaisedReport","Entered");
			ArrayList danRaisedArray = new ArrayList();
			try
			{
				danRaisedArray = reportDao.DanRaisedReport(startDate,endDate,flag);
			}
			catch(Exception exception)
			{
				throw new DatabaseException(exception.getMessage());
			}
			Log.log(Log.INFO,"ReportsManager","getDanRaisedReport","Exited");
			return danRaisedArray;
		}

	public ArrayList getMliList() throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getMliList","Entered");
		ArrayList mliArray = new ArrayList();
		try
		{
			mliArray = reportDao.mliList();
		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getMliList","Exited");
		return mliArray;
	}
  
  
// added by ritesh path on 20Nov2006 to show the summary report
	public ArrayList getAppListMliWise() throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getAppListMliWise","Entered");
		ArrayList mliArray = new ArrayList();
		try
		{
			mliArray = reportDao.AppListMliWise();
		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getAppListMliWise","Exited");
		return mliArray;
	}
  
  
  /**
   * 
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
  public ArrayList getClaimAppListMliWise() throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getClaimAppListMliWise","Entered");
		ArrayList mliArray = new ArrayList();
		try
		{
			mliArray = reportDao.ClmListMliWise();
		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getClaimAppListMliWise","Exited");
		return mliArray;
	}
  
  

	public ArrayList getMliListForBranch(String memberId) throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getMliListForBranch","Entered");
		ArrayList mliArray = new ArrayList();
		try
		{
			mliArray = reportDao.getMliListForBranch(memberId);
		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getMliListForBranch","Exited");
		return mliArray;
	}

	public ArrayList getMliListForZone(String bankId,
                                     String zoneId
                                     )throws DatabaseException	{
                                           
          Log.log(Log.INFO,"ReportsManager","getMliListForZone","Entered");
          ArrayList mliArray = new ArrayList();
          try
          {
            mliArray = reportDao.getMliListForZone(bankId,zoneId);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","getMliListForZone","Exited");
          return mliArray;
	}

	public ArrayList getMliListForBank(String memberId) throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getMliListForBank","Entered");
		ArrayList mliArray = new ArrayList();
		try
		{
			mliArray = reportDao.getMliListForBank(memberId);
		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getMliListForBank","Exited");
		return mliArray;
	}


	public ArrayList getMliListReport(String bank) throws Exception
	{
		Log.log(Log.INFO,"ReportsManager","getMliListReport","Entered");
		ArrayList mliArray = new ArrayList();
		try
		{
			mliArray = reportDao.mliListReport(bank);
		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getMliListReport","Exited");
		return mliArray;
	}
  
  /**
   * 
   * @param memberId
   * @return 
   * @throws java.lang.Exception
   */
  public String getMLIAddressforMemberId(String memberId) throws Exception
	{
		Log.log(Log.INFO,"ReportsManager","getMLIAddressforMemberId","Entered");
		String mliAddress = null;
		try
		{
			mliAddress = reportDao.getMLIAddressforMemberId(memberId);
		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getMliListReport","Exited");
		return mliAddress;
	}
  
  


	public ArrayList getMliPendingReport() throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getMliPendingReport","Entered");
		ArrayList mliArray = new ArrayList();
		try
		{
			mliArray = reportDao.mliPendingReport();
		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getMliPendingReport","Exited");
		return mliArray;
	}


	public ArrayList  getDanReportForBranch(java.sql.Date startDate,java.sql.Date endDate,String Id,String ssi)
				 throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getDanReportForBranch","Entered");
		ArrayList danArray = new ArrayList();
		try
		{
			//System.out.println("branch 2");
			danArray = reportDao.getDanReportForBranch(startDate,endDate,Id,ssi);

		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getDanReportForBranch","Exited");
		return danArray;
	}
 
  
  public ArrayList  getGFDanReportForBranch(java.sql.Date startDate,java.sql.Date endDate,String Id,String ssi)
				 throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getGFDanReportForBranch","Entered");
		ArrayList danArray = new ArrayList();
		try
		{
			//System.out.println("branch 2");
			danArray = reportDao.getGFDanReportForBranch(startDate,endDate,Id,ssi);

		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getGFDanReportForBranch","Exited");
		return danArray;
	}
  /**
   * 
   * @param startDate
   * @param endDate
   * @param Id
   * @param ssi
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
  	public ArrayList  getASFDanReportForBranch(java.sql.Date startDate,java.sql.Date endDate,String Id,String ssi)
				 throws DatabaseException
	{
  		System.out.println("getASFDanReportForBranch");
		Log.log(Log.INFO,"ReportsManager","getASFDanReportForBranch","Entered");
		ArrayList danArray = new ArrayList();
		try
		{
			//System.out.println("branch 2");
			danArray = reportDao.getASFDanReportForBranch(startDate,endDate,Id,ssi);

		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getASFDanReportForBranch","Exited");
		return danArray;
	}

	public ArrayList  getDanReport(java.sql.Date startDate,java.sql.Date endDate,String id,String ssi)
				 throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getDanReport","Entered");
		ArrayList danArray = new ArrayList();
		try
		{
			//System.out.println("dao invoked");
			danArray = reportDao.danReport(startDate,endDate,id,ssi);

		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getDanReport","Exited");
		return danArray;
	}
  
  
  public ArrayList  getGFDanReport(java.sql.Date startDate,java.sql.Date endDate,String id,String ssi)
				 throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getGFDanReport","Entered");
		ArrayList danArray = new ArrayList();
		try
		{
			//System.out.println("dao invoked");
			danArray = reportDao.gfdanReport(startDate,endDate,id,ssi);

		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getGFDanReport","Exited");
		return danArray;
	}
 
  /**
   * 
   * added by sukumar@path for DAN Report for ASF
   * @param startDate
   * @param endDate
   * @param id
   * @param ssi
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
  public ArrayList  getASFDanReport(java.sql.Date startDate,java.sql.Date endDate,String id,String ssi)
				 throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getASFDanReport","Entered");
		ArrayList danArray = new ArrayList();
		try
		{
			//System.out.println("dao invoked");
			danArray = reportDao.asfdanReport(startDate,endDate,id,ssi);

		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getASFDanReport","Exited");
		return danArray;
	}

	public ArrayList  getDanReportForZone(java.sql.Date startDate,java.sql.Date endDate,
							String bankId,String ssi,String zoneId)  throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getDanReportForZone","Entered");
		ArrayList danArray = new ArrayList();
		try
		{
			danArray = reportDao.getDanReportForZone(startDate,endDate,bankId,ssi,zoneId);

		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getDanReportForZone","Exited");
		return danArray;
	}
  /**
   * 
   * @param startDate
   * @param endDate
   * @param bankId
   * @param ssi
   * @param zoneId
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
  public ArrayList  getGFDanReportForZone(java.sql.Date startDate,java.sql.Date endDate,
							String bankId,String ssi,String zoneId)  throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getGFDanReportForZone","Entered");
		ArrayList danArray = new ArrayList();
		try
		{
			danArray = reportDao.getGFDanReportForZone(startDate,endDate,bankId,ssi,zoneId);

		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getGFDanReportForZone","Exited");
		return danArray;
	}
 
  /**
   * 
   * @param startDate
   * @param endDate
   * @param bankId
   * @param ssi
   * @param zoneId
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
  public ArrayList  getASFDanReportForZone(java.sql.Date startDate,java.sql.Date endDate,
							String bankId,String ssi,String zoneId)  throws DatabaseException
	{
	  System.out.println("getASFDanReportForZone");
		Log.log(Log.INFO,"ReportsManager","getASFDanReportForZone","Entered");
		ArrayList danArray = new ArrayList();
		try
		{
			danArray = reportDao.getASFDanReportForZone(startDate,endDate,bankId,ssi,zoneId);

		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getASFDanReportForZone","Exited");
		return danArray;
	}

	public ArrayList  getDanReportForBank(java.sql.Date startDate,java.sql.Date endDate,String Id,String ssi)
				 throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getDanReportForBank","Entered");
		ArrayList danArray = new ArrayList();
		try
		{
			//System.out.println("bank2");
			danArray = reportDao.getDanReportForBank(startDate,endDate,Id,ssi);

		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getDanReportForBank","Exited");
		return danArray;
	}
  
  	public ArrayList  getGFDanReportForBank(java.sql.Date startDate,java.sql.Date endDate,String Id,String ssi)
				 throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getGFDanReportForBank","Entered");
		ArrayList danArray = new ArrayList();
		try
		{
			//System.out.println("bank2");
			danArray = reportDao.getGFDanReportForBank(startDate,endDate,Id,ssi);

		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getGFDanReportForBank","Exited");
		return danArray;
	}
  
  /**
   * 
   * @param startDate
   * @param endDate
   * @param Id
   * @param ssi
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
  
  public ArrayList  getASFDanReportForBank(java.sql.Date startDate,java.sql.Date endDate,String Id,String ssi)
				 throws DatabaseException
	{
	  System.out.println("getASFDanReportForBank");
		Log.log(Log.INFO,"ReportsManager","getASFDanReportForBank","Entered");
		ArrayList danArray = new ArrayList();
		try
		{
			//System.out.println("bank2");
			danArray = reportDao.getASFDanReportForBank(startDate,endDate,Id,ssi);

		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getASFDanReportForBank","Exited");
		return danArray;
	}

		public ArrayList  getDanReportDetails(String id) throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getDanReportDetails","Entered");
		ArrayList danArray = new ArrayList();
		try
		{
			//System.out.println("dao invoked");
			danArray = reportDao.danReportDetails(id);

		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getDanReportDetails","Exited");
		return danArray;
	}

	public ArrayList  getDanReportDetailsForSsi(String id,String ssiName) throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getDanReportDetails","Entered");
		ArrayList danArray = new ArrayList();
		try
		{
			//System.out.println("dao invoked");
			danArray = reportDao.danReportDetailsForSsi(id,ssiName);

		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getDanReportDetails","Exited");
		return danArray;
	}


/**
   * 
   * added by sukumar @path for getting ASF allocated Payment Report for branch
   * @param startDate
   * @param endDate
   * @param Id
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
public ArrayList  getAllocatedPaymentReportForBranch(java.sql.Date startDate,java.sql.Date endDate,String Id)
				 throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getAllocatedPaymentReportForBranch","Entered");
		ArrayList paymentArray = new ArrayList();
		try
		{
			paymentArray = reportDao.getAllocatedPaymentReportForBranch(startDate,endDate,Id);

		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getAllocatedPaymentReportForBranch","Exited");
		return paymentArray;
	}

/* ---------------------  */

/**
   * 
   * added by sukumar@path for getting GF allocated report for branch
   * @param startDate
   * @param endDate
   * @param Id
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
public ArrayList  getGFAllocatedPaymentReportForBranch(java.sql.Date startDate,java.sql.Date endDate,String Id)
				 throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getGFAllocatedPaymentReportForBranch","Entered");
		ArrayList paymentArray = new ArrayList();
		try
		{
			paymentArray = reportDao.getGFAllocatedPaymentReportForBranch(startDate,endDate,Id);

		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getGFAllocatedPaymentReportForBranch","Exited");
		return paymentArray;
	}

	public ArrayList  getPaymentReportForBranch(java.sql.Date startDate,java.sql.Date endDate,String Id)
				 throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getPaymentReportForBranch","Entered");
		ArrayList paymentArray = new ArrayList();
		try
		{
			paymentArray = reportDao.getPaymentReportForBranch(startDate,endDate,Id);

		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getPaymentReportForBranch","Exited");
		return paymentArray;
	}
  
  
  	public ArrayList  getDCHPaymentReportForBranch(java.sql.Date startDate,java.sql.Date endDate,String Id)
				 throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getDCHPaymentReportForBranch","Entered");
		ArrayList paymentArray = new ArrayList();
		try
		{
			paymentArray = reportDao.getDCHPaymentReportForBranch(startDate,endDate,Id);

		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getDCHPaymentReportForBranch","Exited");
		return paymentArray;
	}
  
  /**
   * 
   * @param startDate
   * @param endDate
   * @param Id
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
  public ArrayList  getASFPaymentReportForBranchNew(java.sql.Date startDate,java.sql.Date endDate,String Id)
				 throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getASFPaymentReportForBranchNew","Entered");
		ArrayList paymentArray = new ArrayList();
		try
		{
			paymentArray = reportDao.getASFPaymentReportForBranchNew(startDate,endDate,Id);

		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getASFPaymentReportForBranchNew","Exited");
		return paymentArray;
	}
  
  /**
   * 
   * @param startDate
   * @param endDate
   * @param Id
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
  public ArrayList  getdailyPaymentReportForBranch(java.sql.Date startDate,java.sql.Date endDate,String Id)
				 throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getdailyPaymentReportForBranch","Entered");
		ArrayList paymentArray = new ArrayList();
		try
		{
			paymentArray = reportDao.getdailyPaymentReportForBranch(startDate,endDate,Id);

		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getdailyPaymentReportForBranch","Exited");
		return paymentArray;
	}
  
   public ArrayList  getdailyDCHPaymentReportForBranch(java.sql.Date startDate,java.sql.Date endDate,String Id)
				 throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getdailyDCHPaymentReportForBranch","Entered");
		ArrayList paymentArray = new ArrayList();
		try
		{
			paymentArray = reportDao.getdailyDCHPaymentReportForBranch(startDate,endDate,Id);

		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getdailyDCHPaymentReportForBranch","Exited");
		return paymentArray;
	}
  
  /**
   * 
   * @param startDate
   * @param endDate
   * @param Id
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
  public ArrayList  getASFdailyPaymentReportForBranch(java.sql.Date startDate,java.sql.Date endDate,String Id)
				 throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getASFdailyPaymentReportForBranch","Entered");
		ArrayList paymentArray = new ArrayList();
		try
		{
			paymentArray = reportDao.getasfdailyPaymentReportForBranch(startDate,endDate,Id);

		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getASFdailyPaymentReportForBranch","Exited");
		return paymentArray;
	}
  
/* added by sukumar@path 28-04-2008 */
public ArrayList  getASFPaymentReportForBranch(java.sql.Date startDate,java.sql.Date endDate,String Id)
				 throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getASFPaymentReportForBranch","Entered");
		ArrayList paymentArray = new ArrayList();
		try
		{
			paymentArray = reportDao.getASFPaymentReportForBranch(startDate,endDate,Id);

		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getASFPaymentReportForBranch","Exited");
		return paymentArray;
	}

/* ------------------------------   */

  /**
   * 
   * added by sukumar@path for ASF allocated payment report for zone
   * @param startDate
   * @param endDate
   * @param bankId
   * @param zoneId
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
  public ArrayList  getAllocatePaymentReportForZone(java.sql.Date startDate,java.sql.Date endDate,
									String bankId, String zoneId) throws DatabaseException

	{
		Log.log(Log.INFO,"ReportsManager","getAllocatePaymentReportForZone","Entered");
		ArrayList paymentArray = new ArrayList();
		try
		{
			paymentArray = reportDao.getAllocatedPaymentReportForZone(startDate,endDate,bankId,zoneId);

		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getAllocatePaymentReportForZone","Exited");
		return paymentArray;
	}
  /* --------------------- */
  
/**
   * 
   * added by sukumar@path for getting GF allocated payment report for zone
   * @param startDate
   * @param endDate
   * @param bankId
   * @param zoneId
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
  public ArrayList  getGFAllocatePaymentReportForZone(java.sql.Date startDate,java.sql.Date endDate,
									String bankId, String zoneId) throws DatabaseException

	{
		Log.log(Log.INFO,"ReportsManager","getGFAllocatePaymentReportForZone","Entered");
		ArrayList paymentArray = new ArrayList();
		try
		{
			paymentArray = reportDao.getGFAllocatedPaymentReportForZone(startDate,endDate,bankId,zoneId);

		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getGFAllocatePaymentReportForZone","Exited");
		return paymentArray;
	}
  public ArrayList  getPaymentReportForZone(java.sql.Date startDate,java.sql.Date endDate,
									String bankId, String zoneId) throws DatabaseException

	{
		Log.log(Log.INFO,"ReportsManager","getPaymentReportForZone","Entered");
		ArrayList paymentArray = new ArrayList();
		try
		{
			paymentArray = reportDao.getPaymentReportForZone(startDate,endDate,bankId,zoneId);

		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getPaymentReportForZone","Exited");
		return paymentArray;
	}
 
 public ArrayList  getDCHPaymentReportForZone(java.sql.Date startDate,java.sql.Date endDate,
									String bankId, String zoneId) throws DatabaseException

	{
		Log.log(Log.INFO,"ReportsManager","getDCHPaymentReportForZone","Entered");
		ArrayList paymentArray = new ArrayList();
		try
		{
			paymentArray = reportDao.getDCHPaymentReportForZone(startDate,endDate,bankId,zoneId);

		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getDCHPaymentReportForZone","Exited");
		return paymentArray;
	}
 
   public ArrayList  getASFPaymentReportForZoneNew(java.sql.Date startDate,java.sql.Date endDate,
									String bankId, String zoneId) throws DatabaseException

	{
		Log.log(Log.INFO,"ReportsManager","getASFPaymentReportForZoneNew","Entered");
		ArrayList paymentArray = new ArrayList();
		try
		{
			paymentArray = reportDao.getASFPaymentReportForZoneNew(startDate,endDate,bankId,zoneId);

		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getASFPaymentReportForZoneNew","Exited");
		return paymentArray;
	}

 
 /**
   * 
   * @param startDate
   * @param endDate
   * @param bankId
   * @param zoneId
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
 public ArrayList  getdailyPaymentReportForZone(java.sql.Date startDate,java.sql.Date endDate,
									String bankId, String zoneId) throws DatabaseException

	{
		Log.log(Log.INFO,"ReportsManager","getdailyPaymentReportForZone","Entered");
		ArrayList paymentArray = new ArrayList();
		try
		{
			paymentArray = reportDao.getdailyPaymentReportForZone(startDate,endDate,bankId,zoneId);

		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getdailyPaymentReportForZone","Exited");
		return paymentArray;
	}
 
 /**
   * 
   * @param startDate
   * @param endDate
   * @param bankId
   * @param zoneId
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
 public ArrayList  getdailyDCHPaymentReportForZone(java.sql.Date startDate,java.sql.Date endDate,
									String bankId, String zoneId) throws DatabaseException

	{
		Log.log(Log.INFO,"ReportsManager","getdailyDCHPaymentReportForZone","Entered");
		ArrayList paymentArray = new ArrayList();
		try
		{
			paymentArray = reportDao.getdailyDCHPaymentReportForZone(startDate,endDate,bankId,zoneId);

		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getdailyDCHPaymentReportForZone","Exited");
		return paymentArray;
	}
 /**
   * 
   * @param startDate
   * @param endDate
   * @param bankId
   * @param zoneId
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
 public ArrayList  getASFdailyPaymentReportForZone(java.sql.Date startDate,java.sql.Date endDate,
									String bankId, String zoneId) throws DatabaseException

	{
		Log.log(Log.INFO,"ReportsManager","getASFdailyPaymentReportForZone","Entered");
		ArrayList paymentArray = new ArrayList();
		try
		{
			paymentArray = reportDao.getasfdailyPaymentReportForZone(startDate,endDate,bankId,zoneId);

		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getASFdailyPaymentReportForZone","Exited");
		return paymentArray;
	}
 
 
  /**
   * added by sukumar@path on 28-04-2008
   * @param startDate
   * @param endDate
   * @param bankId
   * @param zoneId
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
   public ArrayList  getASFPaymentReportForZone(java.sql.Date startDate,java.sql.Date endDate,
									String bankId, String zoneId) throws DatabaseException

	{
		Log.log(Log.INFO,"ReportsManager","getASFPaymentReportForZone","Entered");
		ArrayList paymentArray = new ArrayList();
		try
		{
			paymentArray = reportDao.getASFPaymentReportForZone(startDate,endDate,bankId,zoneId);

		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getASFPaymentReportForZone","Exited");
		return paymentArray;
	}
  /* ----------------------------------- */

/**
   * added by sukumar @path 14-04-2008
   * @param startDate
   * @param endDate
   * @param Id
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
public ArrayList  getAllocatePaymentReportForBank(java.sql.Date startDate,java.sql.Date endDate,String Id)
				 throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getAllocatePaymentReportForBank","Entered");
		ArrayList paymentArray = new ArrayList();
		try
		{
			paymentArray = reportDao.getAllocatePaymentReportForBank(startDate,endDate,Id);

		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getAllocatePaymentReportForBank","Exited");
		return paymentArray;
	}

/* --------------------------------- */

/**
   * 
   * added by sukumar@path for getting GF allocated payment report for bank
   * @param startDate
   * @param endDate
   * @param Id
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
public ArrayList  getGFAllocatePaymentReportForBank(java.sql.Date startDate,java.sql.Date endDate,String Id)
				 throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getGFAllocatePaymentReportForBank","Entered");
		ArrayList paymentArray = new ArrayList();
		try
		{
			paymentArray = reportDao.getGFAllocatePaymentReportForBank(startDate,endDate,Id);

		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getGFAllocatePaymentReportForBank","Exited");
		return paymentArray;
	}
	public ArrayList  getPaymentReportForBank(java.sql.Date startDate,java.sql.Date endDate,String Id)
				 throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getPaymentReportForBank","Entered");
		ArrayList paymentArray = new ArrayList();
		try
		{
			paymentArray = reportDao.getPaymentReportForBank(startDate,endDate,Id);

		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getPaymentReportForBank","Exited");
		return paymentArray;
	}


public ArrayList  getDCHPaymentReportForBank(java.sql.Date startDate,java.sql.Date endDate,String Id)
				 throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getDCHPaymentReportForBank","Entered");
		ArrayList paymentArray = new ArrayList();
		try
		{
			paymentArray = reportDao.getDCHPaymentReportForBank(startDate,endDate,Id);

		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getDCHPaymentReportForBank","Exited");
		return paymentArray;
	}

public ArrayList  getASFPaymentReportForBankNew(java.sql.Date startDate,java.sql.Date endDate,String Id)
				 throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getASFPaymentReportForBankNew","Entered");
		ArrayList paymentArray = new ArrayList();
		try
		{
			paymentArray = reportDao.getASFPaymentReportForBankNew(startDate,endDate,Id);

		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getASFPaymentReportForBankNew","Exited");
		return paymentArray;
	}


public ArrayList  getdailyPaymentReportForBank(java.sql.Date startDate,java.sql.Date endDate,String Id)
				 throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getdailyPaymentReportForBank","Entered");
		ArrayList paymentArray = new ArrayList();
		try
		{
			paymentArray = reportDao.getdailyPaymentReportForBank(startDate,endDate,Id);

		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getdailyPaymentReportForBank","Exited");
		return paymentArray;
	}

/**
   * 
   * @param startDate
   * @param endDate
   * @param Id
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
public ArrayList  getdailyDCHPaymentReportForBank(java.sql.Date startDate,java.sql.Date endDate,String Id)
				 throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getdailyDCHPaymentReportForBank","Entered");
		ArrayList paymentArray = new ArrayList();
		try
		{
			paymentArray = reportDao.getdailyDCHPaymentReportForBank(startDate,endDate,Id);

		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getdailyDCHPaymentReportForBank","Exited");
		return paymentArray;
	}

/**
   * 
   * @param startDate
   * @param endDate
   * @param Id
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
public ArrayList  getASFdailyPaymentReportForBank(java.sql.Date startDate,java.sql.Date endDate,String Id)
				 throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getASFdailyPaymentReportForBank","Entered");
		ArrayList paymentArray = new ArrayList();
		try
		{
			paymentArray = reportDao.getASFdailyPaymentReportForBank(startDate,endDate,Id);

		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getASFdailyPaymentReportForBank","Exited");
		return paymentArray;
	}


/**
   * added by sukumar@path on 28-04-2008 for asf payment report
   * @param startDate
   * @param endDate
   * @param Id
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
public ArrayList  getASFPaymentReportForBank(java.sql.Date startDate,java.sql.Date endDate,String Id)
				 throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getASFPaymentReportForBank","Entered");
		ArrayList paymentArray = new ArrayList();
		try
		{
			paymentArray = reportDao.getASFPaymentReportForBank(startDate,endDate,Id);

		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getASFPaymentReportForBank","Exited");
		return paymentArray;
	}

/* --------------------------------------------------------   */
	public ArrayList  getPaymentReport(java.sql.Date startDate,
                                     java.sql.Date endDate,
                                     String Id
                                     )throws DatabaseException{
                                           
          Log.log(Log.INFO,"ReportsManager","getPaymentReport","Entered");
          ArrayList paymentArray = new ArrayList();
          try
          {
            paymentArray = reportDao.paymentReport(startDate,endDate,Id);
      
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","getPaymentReport","Exited");
      		return paymentArray;
	}


public ArrayList  getDCHPaymentReport(java.sql.Date startDate,
                                     java.sql.Date endDate,
                                     String Id
                                     )throws DatabaseException{
                                           
          Log.log(Log.INFO,"ReportsManager","getDCHPaymentReport","Entered");
          ArrayList paymentArray = new ArrayList();
          try
          {
            paymentArray = reportDao.dchpaymentReport(startDate,endDate,Id);
      
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","getDCHPaymentReport","Exited");
      		return paymentArray;
	}
/**
   * 
   * @param startDate
   * @param endDate
   * @param Id
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
	public ArrayList  getASFPaymentReportNew(java.sql.Date startDate,
                                     java.sql.Date endDate,
                                     String Id
                                     )throws DatabaseException{
                                           
          Log.log(Log.INFO,"ReportsManager","getASFPaymentReport","Entered");
          ArrayList paymentArray = new ArrayList();
          try
          {
            paymentArray = reportDao.asfpaymentReportNew(startDate,endDate,Id);
      
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","getASFPaymentReport","Exited");
      		return paymentArray;
	}


/**
   * 
   * @param startDate
   * @param endDate
   * @param Id
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
	public ArrayList  getdailyPaymentReport(java.sql.Date startDate,
                                     java.sql.Date endDate,
                                     String Id
                                     )throws DatabaseException{
                                           
          Log.log(Log.INFO,"ReportsManager","getdailyPaymentReport","Entered");
          ArrayList paymentArray = new ArrayList();
          try
          {
            paymentArray = reportDao.getdailyPaymentReport(startDate,endDate,Id);
      
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","getdailyPaymentReport","Exited");
      		return paymentArray;
	}
  
  /**
   * 
   * @param startDate
   * @param endDate
   * @param Id
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
  public ArrayList  getdailydchPaymentReport(java.sql.Date startDate,
                                     java.sql.Date endDate,
                                     String Id
                                     )throws DatabaseException{
                                           
          Log.log(Log.INFO,"ReportsManager","getdailyPaymentReport","Entered");
          ArrayList paymentArray = new ArrayList();
          try
          {
            paymentArray = reportDao.getdailydchPaymentReport(startDate,endDate,Id);
      
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","getdailydchPaymentReport","Exited");
      		return paymentArray;
	}
  
  /**
   * 
   * @param startDate
   * @param endDate
   * @param Id
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
  public ArrayList  getasfdailyPaymentReport(java.sql.Date startDate,
                                     java.sql.Date endDate,
                                     String Id
                                     )throws DatabaseException{
                                           
          Log.log(Log.INFO,"ReportsManager","getdailyPaymentReport","Entered");
          ArrayList paymentArray = new ArrayList();
          try
          {
            paymentArray = reportDao.getasfdailyPaymentReport(startDate,endDate,Id);
      
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","getdailyPaymentReport","Exited");
      		return paymentArray;
	}
/**
   * 
   * added by sukumar@path 28-04-2008
   * @param startDate
   * @param endDate
   * @param Id
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
public ArrayList  getASFPaymentReport(java.sql.Date startDate,
                                     java.sql.Date endDate,
                                     String Id
                                     )throws DatabaseException{
                                           
          Log.log(Log.INFO,"ReportsManager","getASFPaymentReport","Entered");
          ArrayList paymentArray = new ArrayList();
          try
          {
            paymentArray = reportDao.asfpaymentReport(startDate,endDate,Id);
      
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","getASFPaymentReport","Exited");
      		return paymentArray;
	}

/* -------------------------------- */

/**
   * 
   * added by sukumar@path 14-04-2008
   * @param startDate
   * @param endDate
   * @param Id
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
public ArrayList  getAllocatedPaymentReport(java.sql.Date startDate,
                                     java.sql.Date endDate,
                                     String Id
                                     )throws DatabaseException{
                                           
          Log.log(Log.INFO,"ReportsManager","getAllocatedPaymentReport","Entered");
          ArrayList paymentArray = new ArrayList();
          try
          {
            paymentArray = reportDao.allocatedpaymentReport(startDate,endDate,Id);
      
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","getAllocatedPaymentReport","Exited");
      		return paymentArray;
	}


/*  -----------------------------   */

public ArrayList  getGFAllocatedPaymentReport(java.sql.Date startDate,
                                     java.sql.Date endDate,
                                     String Id
                                     )throws DatabaseException{
                                           
          Log.log(Log.INFO,"ReportsManager","getGFAllocatedPaymentReport","Entered");
          ArrayList paymentArray = new ArrayList();
          try
          {
            paymentArray = reportDao.gfallocatedpaymentReport(startDate,endDate,Id);
      
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","getGFAllocatedPaymentReport","Exited");
      		return paymentArray;
	}

	public ArrayList  getDisbursementReport(java.sql.Date startDate,
                                          java.sql.Date endDate ,
                                          String Id
                                          )throws DatabaseException	{
                                                
          Log.log(Log.INFO,"ReportsManager","getDisbursementReport","Entered");
          ArrayList disbursementArray = new ArrayList();
          try
          {
            disbursementArray = reportDao.disbursementReport(startDate,endDate,Id);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","getDisbursementReport","Exited");
          return disbursementArray;
	}
	//DKR
public ArrayList  getClaimRecoveryReport(java.util.Date startDate,java.util.Date endDate,String Id)throws DatabaseException	{	                
	Log.log(Log.INFO,"ReportsManager","getDisbursementReport","Entered");
	
	 ArrayList claimRecoveryArray = new ArrayList();
	try
	{
		claimRecoveryArray = reportDao.claimRecoveryReport(startDate,endDate,Id);
	}
	catch(Exception exception)
	{
	   throw new DatabaseException(exception.getMessage());
	}	
	return claimRecoveryArray;
}
	
	


	public ArrayList  getDisbursementReportForBranch(java.sql.Date startDate,
                                                   java.sql.Date endDate ,
                                                   String Id
                                                   )throws DatabaseException{
                                                         
          Log.log(Log.INFO,"ReportsManager","getDisbursementReportForBranch","Entered");
          ArrayList disbursementArray = new ArrayList();
          try
          {
            disbursementArray = reportDao.getDisbursementReportForBranch(startDate,endDate,Id);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","getDisbursementReportForBranch","Exited");
          return disbursementArray;
	}



	public ArrayList  getDisbursementReportForZone(java.sql.Date startDate,
                                                 java.sql.Date endDate ,
                                                 String bankId,
                                                 String zoneId
                                                 ) throws DatabaseException	{
                                                       
          Log.log(Log.INFO,"ReportsManager","getDisbursementReportForZone","Entered");
          ArrayList disbursementArray = new ArrayList();
          try
          {
            disbursementArray = reportDao.getDisbursementReportForZone(startDate,endDate,bankId,zoneId);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","getDisbursementReportForZone","Exited");
          return disbursementArray;
	}



	public ArrayList  getDisbursementReportForBank(java.sql.Date startDate,
                                                 java.sql.Date endDate ,
                                                 String Id
                                                 ) throws DatabaseException	{
                                                       
          Log.log(Log.INFO,"ReportsManager","getDisbursementReportForBank","Entered");
          ArrayList disbursementArray = new ArrayList();
          try
          {
             disbursementArray = reportDao.getDisbursementReportForBank(startDate,endDate,Id);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","getDisbursementReportForBank","Exited");
          return disbursementArray;
	}

//7
	public ArrayList  getStateWiseReportDetails(java.sql.Date startDate,
                                              java.sql.Date endDate,
                                              String Id
                                              )throws DatabaseException{
                                                    
          Log.log(Log.INFO,"ReportsManager","getStateWiseReportDetails","Entered");
          ArrayList statesArray = new ArrayList();
          try
          {
            statesArray = reportDao.stateWiseReportDetails(startDate,endDate,Id);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","getStateWiseReportDetails","Exited");
          return statesArray;
	}

/**
   * 
   * @param startDate
   * @param endDate
   * @param Id
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
public ArrayList  getStateWiseReportDetailsNew(java.sql.Date startDate,
                                              java.sql.Date endDate,
                                              String Id
                                              )throws DatabaseException{
                                                    
          Log.log(Log.INFO,"ReportsManager","getStateWiseReportDetailsNew","Entered");
          ArrayList statesArray = new ArrayList();
          try
          {
            statesArray = reportDao.stateWiseReportDetailsNew(startDate,endDate,Id);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","getStateWiseReportDetailsNew","Exited");
          return statesArray;
	}
//8
	public ArrayList  getStateDetails(String state, 
                                    String flag,
                                    java.sql.Date startDate,
                                    java.sql.Date endDate
                                    ) throws DatabaseException{
                                          
          Log.log(Log.INFO,"ReportsManager","getStateDetails","Entered");
          ArrayList stateDetailsArray = new ArrayList();
          try
          {
            stateDetailsArray = reportDao.stateDetails(state,flag,startDate,endDate);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","getStateDetails","Exited");
          return stateDetailsArray;
	}
/**
   * 
   * @param state
   * @param flag
   * @param startDate
   * @param endDate
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
public ArrayList  getStateDetailsNew(String state, 
                                    String flag,
                                    java.sql.Date startDate,
                                    java.sql.Date endDate
                                    ) throws DatabaseException{
                                          
          Log.log(Log.INFO,"ReportsManager","getStateDetailsNew","Entered");
          ArrayList stateDetailsArray = new ArrayList();
          try
          {
            stateDetailsArray = reportDao.stateDetails(state,flag,startDate,endDate);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","getStateDetailsNew","Exited");
          return stateDetailsArray;
	}



//9
	public ArrayList  getStateDistrictDetails(String district, 
                                            String flag, 
                                            java.sql.Date startDate,
                                            java.sql.Date endDate
                                            ) throws DatabaseException{
                                                  
          Log.log(Log.INFO,"ReportsManager","getStateDistrictDetails","Entered");
          ArrayList districtDetailsArray = new ArrayList();
          try
          {
            districtDetailsArray = reportDao.stateDistrictDetails(district,flag,
                                    startDate,endDate);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","getStateDistrictDetails","Exited");
          return districtDetailsArray;
	}


public ArrayList  getStateDistrictDetailsNew(String district, 
                                            java.sql.Date startDate,
                                            java.sql.Date endDate
                                            ) throws DatabaseException{
                                                  
          Log.log(Log.INFO,"ReportsManager","getStateDistrictDetailsNew","Entered");
          ArrayList districtDetailsArray = new ArrayList();
          try
          {
            districtDetailsArray = reportDao.mliWiseNEDistrictReportDetailsNew(startDate,endDate,district);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","getStateDistrictDetailsNew","Exited");
          return districtDetailsArray;
	}

//10
	public ArrayList  getMliApplicationDetails(java.sql.Date startDate,
                                             java.sql.Date endDate,
                                             String guarantee
                                             )throws DatabaseException{
                                                   
          Log.log(Log.INFO,"ReportsManager","getMliApplicationDetails","Entered");
          ArrayList mliApplicationArray = new ArrayList();
          try
          {
            mliApplicationArray = reportDao.mliApplicationDetails(startDate,endDate,guarantee);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","getMliApplicationDetails","Exited");
          return mliApplicationArray;
	}
  
  /**
   * 
   * @param startDate
   * @param endDate
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
  public ArrayList  getMliClaimApplicationDetailsNew(java.sql.Date startDate,
                                             java.sql.Date endDate
                                             )throws DatabaseException{
                                                   
          Log.log(Log.INFO,"ReportsManager","getMliApplicationDetails","Entered");
          ArrayList mliApplicationArray = new ArrayList();
          try
          {
            mliApplicationArray = reportDao.mliClaimApplicationDetailsNew(startDate,endDate);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","getMliApplicationDetails","Exited");
          return mliApplicationArray;
	}
  
  /**
   * 
   * @param startDate
   * @param endDate
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
   public ArrayList  getStateClaimApplicationDetails(java.sql.Date startDate,
                                             java.sql.Date endDate
                                             )throws DatabaseException{
                                                   
          Log.log(Log.INFO,"ReportsManager","getMliApplicationDetails","Entered");
          ArrayList mliApplicationArray = new ArrayList();
          try
          {
            mliApplicationArray = reportDao.StateClaimApplicationDetails(startDate,endDate);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","getMliApplicationDetails","Exited");
          return mliApplicationArray;
	}
  
  /**
   * 
   * @param startDate
   * @param endDate
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
   public ArrayList  getSectorClaimApplicationDetails(java.sql.Date startDate,
                                             java.sql.Date endDate
                                             )throws DatabaseException{
                                                   
          Log.log(Log.INFO,"ReportsManager","getSectorClaimApplicationDetails","Entered");
          ArrayList mliApplicationArray = new ArrayList();
          try
          {
            mliApplicationArray = reportDao.SectorClaimApplicationDetails(startDate,endDate);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","getSectorClaimApplicationDetails","Exited");
          return mliApplicationArray;
	}
/**
   * 
   * @param startDate
   * @param endDate
   * @param guarantee
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
public ArrayList  getMliApplicationDetailsForRsf(java.sql.Date startDate,
                                             java.sql.Date endDate,
                                             String guarantee
                                             )throws DatabaseException{
                                                   
          Log.log(Log.INFO,"ReportsManager","getMliApplicationDetailsForRsf","Entered");
          ArrayList mliApplicationArray = new ArrayList();
          try
          {
            mliApplicationArray = reportDao.mliApplicationDetailsForRsf(startDate,endDate,guarantee);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","getMliApplicationDetailsForRsf","Exited");
          return mliApplicationArray;
	}
  /**
   * 
   * @param startDate
   * @param endDate
   * @param guarantee
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
public ArrayList  getMliApplicationDetailsForRsfPend(java.sql.Date startDate,
                                             java.sql.Date endDate,
                                             String guarantee
                                             )throws DatabaseException{
                                                   
          Log.log(Log.INFO,"ReportsManager","getMliApplicationDetailsForRsfPend","Entered");
          ArrayList mliApplicationArray = new ArrayList();
          try
          {
            mliApplicationArray = reportDao.mliApplicationDetailsForRsfPend(startDate,endDate,guarantee);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","getMliApplicationDetailsForRsfPend","Exited");
          return mliApplicationArray;
	}

public ArrayList  getmliWiseNEReportDetailsNew(java.sql.Date startDate,
                                             java.sql.Date endDate
                                             )throws DatabaseException{
                                                   
          Log.log(Log.INFO,"ReportsManager","getmliWiseNEReportDetailsNew","Entered");
          ArrayList mliApplicationArray = new ArrayList();
          try
          {
            mliApplicationArray = reportDao.getmliWiseNEReportDetailsNew(startDate,endDate);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","getmliWiseNEReportDetailsNew","Exited");
          return mliApplicationArray;
	}
  
/**
   * 
   * @param startDate
   * @param endDate
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
  public ArrayList  getmliWiseNEStateReportDetailsNew(java.sql.Date startDate,
                                             java.sql.Date endDate
                                             )throws DatabaseException{
                                                   
          Log.log(Log.INFO,"ReportsManager","getmliWiseNEStateReportDetailsNew","Entered");
          ArrayList mliApplicationArray = new ArrayList();
          try
          {
            mliApplicationArray = reportDao.getmliWiseNEStateReportDetailsNew(startDate,endDate);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","getmliWiseNEStateReportDetailsNew","Exited");
          return mliApplicationArray;
	}
  /**
   * 
   * @param startDate
   * @param endDate
   * @param state
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
   public ArrayList  mliWiseNEDistrictReportDetailsNew1(java.sql.Date startDate,
                                             java.sql.Date endDate,String state
                                             )throws DatabaseException{
                                                   
          Log.log(Log.INFO,"ReportsManager","mliWiseNEDistrictReportDetailsNew","Entered");
          ArrayList mliApplicationArray = new ArrayList();
          try
          {
            mliApplicationArray = reportDao.mliWiseNEDistrictReportDetailsNew(startDate,endDate,state);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","mliWiseNEDistrictReportDetailsNew","Exited");
          return mliApplicationArray;
	}

//	11
	public ArrayList  getBankApplicationDetails(java.sql.Date startDate,java.sql.Date endDate,
							String id, String memberId) throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getBankApplicationDetails","Entered");
		ArrayList bankDetailsArray = new ArrayList();
		try
		{
			bankDetailsArray = reportDao.BankApplicationDetails(startDate,endDate,id,memberId);
		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getBankApplicationDetails","Exited");
		return bankDetailsArray;
	}

/**
   * 
   * @param startDate
   * @param endDate
     * @param memberId
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
public ArrayList  getBankClaimApplicationDetailsNew(java.sql.Date startDate,java.sql.Date endDate,
							 String memberId) throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getBankClaimApplicationDetails","Entered");
		ArrayList bankDetailsArray = new ArrayList();
		try
		{
			bankDetailsArray = reportDao.BankClaimApplicationDetailsNew(startDate,endDate,memberId);
		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getBankClaimApplicationDetails","Exited");
		return bankDetailsArray;
	}


public ArrayList  getStateBankClaimApplicationDetails(java.sql.Date startDate,java.sql.Date endDate,
							 String memberId) throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getBankClaimApplicationDetails","Entered");
		ArrayList bankDetailsArray = new ArrayList();
		try
		{
			bankDetailsArray = reportDao.StateBankClaimApplicationDetails(startDate,endDate,memberId);
		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getBankClaimApplicationDetails","Exited");
		return bankDetailsArray;
	}
  
  /**
   * 
   * @param startDate
   * @param endDate
   * @param memberId
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
  public ArrayList  getSectorBankClaimApplicationDetails(java.sql.Date startDate,java.sql.Date endDate,
							 String memberId) throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getSectorBankClaimApplicationDetails","Entered");
		ArrayList bankDetailsArray = new ArrayList();
		try
		{
			bankDetailsArray = reportDao.SectorBankClaimApplicationDetails(startDate,endDate,memberId);
		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getSectorBankClaimApplicationDetails","Exited");
		return bankDetailsArray;
	}

/**
   * 
   * @param startDate
   * @param endDate
   * @param id
   * @param memberId
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
	public ArrayList  getStateBankApplicationDetails(java.sql.Date startDate,java.sql.Date endDate,
							String id, String memberId) throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getStateBankApplicationDetails","Entered");
		ArrayList bankDetailsArray = new ArrayList();
		try
		{
			bankDetailsArray = reportDao.StateBankApplicationDetails(startDate,endDate,id,memberId);
		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getStateBankApplicationDetails","Exited");
		return bankDetailsArray;
	}
	public ArrayList  getZoneApplicationDetails(java.sql.Date startDate,
                                              java.sql.Date endDate,
                                              String id, 
                                              String memberId
                                              )throws DatabaseException{
                                                    
          Log.log(Log.INFO,"ReportsManager","getZoneApplicationDetails","Entered");
          ArrayList bankDetailsArray = new ArrayList();
          try
          {
            bankDetailsArray = reportDao.ZoneApplicationDetails(startDate,endDate,id,memberId);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","getZoneApplicationDetails","Exited");
          return bankDetailsArray;
	}

/**
   * 
   * @param startDate
   * @param endDate
   * @param memberId
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
public ArrayList  getZoneClaimApplicationDetailsNew(java.sql.Date startDate,
                                              java.sql.Date endDate,
                                               String memberId
                                              )throws DatabaseException{
                                                    
          Log.log(Log.INFO,"ReportsManager","getZoneClaimApplicationDetails","Entered");
          ArrayList bankDetailsArray = new ArrayList();
          try
          {
            bankDetailsArray = reportDao.ZoneClaimApplicationDetailsNew(startDate,endDate,memberId);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","getZoneClaimApplicationDetails","Exited");
          return bankDetailsArray;
	}

/**
   * 
   * @param startDate
   * @param endDate
   * @param memberId
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
public ArrayList  getStateZoneClaimApplicationDetails(java.sql.Date startDate,
                                              java.sql.Date endDate,
                                               String memberId
                                              )throws DatabaseException{
                                                    
          Log.log(Log.INFO,"ReportsManager","getStateZoneClaimApplicationDetails","Entered");
          ArrayList bankDetailsArray = new ArrayList();
          try
          {
            bankDetailsArray = reportDao.StateZoneClaimApplicationDetails(startDate,endDate,memberId);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","getStateZoneClaimApplicationDetails","Exited");
          return bankDetailsArray;
	}
  
  /**
   * 
   * @param startDate
   * @param endDate
   * @param memberId
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
  public ArrayList  getSectorZoneClaimApplicationDetails(java.sql.Date startDate,
                                              java.sql.Date endDate,
                                               String memberId
                                              )throws DatabaseException{
                                                    
          Log.log(Log.INFO,"ReportsManager","getSectorZoneClaimApplicationDetails","Entered");
          ArrayList bankDetailsArray = new ArrayList();
          try
          {
            bankDetailsArray = reportDao.SectorZoneClaimApplicationDetails(startDate,endDate,memberId);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","getSectorZoneClaimApplicationDetails","Exited");
          return bankDetailsArray;
	}

public ArrayList  getStateZoneApplicationDetails(java.sql.Date startDate,
                                              java.sql.Date endDate,
                                              String id, 
                                              String memberId
                                              )throws DatabaseException{
                                                    
          Log.log(Log.INFO,"ReportsManager","getStateZoneApplicationDetails","Entered");
          ArrayList bankDetailsArray = new ArrayList();
          try
          {
            bankDetailsArray = reportDao.StateZoneApplicationDetails(startDate,endDate,id,memberId);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","getStateZoneApplicationDetails","Exited");
          return bankDetailsArray;
	}

	public ArrayList  getBranchApplicationDetails(java.sql.Date startDate,
                                                java.sql.Date endDate,
                                                String id, 
                                                String memberId
                                                ) throws DatabaseException{
                                                      
          Log.log(Log.INFO,"ReportsManager","getBranchApplicationDetails","Entered");
          ArrayList bankDetailsArray = new ArrayList();
          try
          {
            bankDetailsArray = reportDao.BranchApplicationDetails(startDate,endDate,id,memberId);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","getBranchApplicationDetails","Exited");
          return bankDetailsArray;
	}
  
  /*  added by sukumar on 08-01-2010 */
  /**
   * 
   * @param startDate
   * @param endDate
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
  public ArrayList getMliClaimApplicationDetails(java.sql.Date startDate, java.sql.Date endDate)
        throws DatabaseException
    {
        Log.log(4, "ReportsManager", "getMliApplicationDetails", "Entered");
        ArrayList mliApplicationArray = new ArrayList();
        try
        {
            mliApplicationArray = reportDao.mliClaimApplicationDetails(startDate, endDate);
        }
        catch(Exception exception)
        {
            throw new DatabaseException(exception.getMessage());
        }
        Log.log(4, "ReportsManager", "getMliApplicationDetails", "Exited");
        return mliApplicationArray;
    }
    
    /**
   * 
   * @param endDate
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
    public ArrayList mliWiseClaimSummaryReportDetails(java.sql.Date endDate,String memberId)
        throws DatabaseException
    {
        Log.log(4, "ReportsManager", "mliWiseClaimSummaryReportDetails", "Entered");
        ArrayList mliApplicationArray = new ArrayList();
        try
        {
            mliApplicationArray = reportDao.mliWiseClaimSummaryReportDetails(endDate,memberId);
        }
        catch(Exception exception)
        {
            throw new DatabaseException(exception.getMessage());
        }
        Log.log(4, "ReportsManager", "mliWiseClaimSummaryReportDetails", "Exited");
        return mliApplicationArray;
    }
    
    /**
   * 
   * @param endDate
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
     public ArrayList mliWiseClaimPendingReportDetails(java.sql.Date endDate)
        throws DatabaseException
    {
        Log.log(4, "ReportsManager", "mliWiseClaimPendingReportDetails", "Entered");
        ArrayList mliApplicationArray = new ArrayList();
        try
        {
            mliApplicationArray = reportDao.mliWiseClaimPendingReportDetails(endDate);
        }
        catch(Exception exception)
        {
            throw new DatabaseException(exception.getMessage());
        }
        Log.log(4, "ReportsManager", "mliWiseClaimPendingReportDetails", "Exited");
        return mliApplicationArray;
    }
/**
   * 
   * @param startDate
   * @param endDate
   * @param memberId
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
public ArrayList getBankClaimApplicationDetails(java.sql.Date startDate, java.sql.Date endDate, String memberId)
        throws DatabaseException
    {
        Log.log(4, "ReportsManager", "getBankClaimApplicationDetails", "Entered");
        ArrayList bankDetailsArray = new ArrayList();
        try
        {
            bankDetailsArray = reportDao.BankClaimApplicationDetails(startDate, endDate, memberId);
        }
        catch(Exception exception)
        {
            throw new DatabaseException(exception.getMessage());
        }
        Log.log(4, "ReportsManager", "getBankClaimApplicationDetails", "Exited");
        return bankDetailsArray;
    }
/**
   * 
   * @param startDate
   * @param endDate
   * @param memberId
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
public ArrayList getZoneClaimApplicationDetails(java.sql.Date startDate, java.sql.Date endDate, String memberId)
        throws DatabaseException
    {
        Log.log(4, "ReportsManager", "getZoneClaimApplicationDetails", "Entered");
        ArrayList bankDetailsArray = new ArrayList();
        try
        {
            bankDetailsArray = reportDao.ZoneClaimApplicationDetails(startDate, endDate, memberId);
        }
        catch(Exception exception)
        {
            throw new DatabaseException(exception.getMessage());
        }
        Log.log(4, "ReportsManager", "getZoneClaimApplicationDetails", "Exited");
        return bankDetailsArray;
    }

/**
   * 
   * @param startDate
   * @param endDate
   * @param memberId
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
public ArrayList getBranchClaimApplicationDetails(java.sql.Date startDate, java.sql.Date endDate, String memberId)
        throws DatabaseException
    {
        Log.log(4, "ReportsManager", "getBranchClaimApplicationDetails", "Entered");
        ArrayList bankDetailsArray = new ArrayList();
        try
        {
            bankDetailsArray = reportDao.BranchClaimApplicationDetails(startDate, endDate, memberId);
        }
        catch(Exception exception)
        {
            throw new DatabaseException(exception.getMessage());
        }
        Log.log(4, "ReportsManager", "getBranchClaimApplicationDetails", "Exited");
        return bankDetailsArray;
    }
  
  /**
   * 
   * @param startDate
   * @param endDate
   * @param id
   * @param memberId
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
  public ArrayList  getBranchClaimApplicationDetailsNew(java.sql.Date startDate,
                                                java.sql.Date endDate,
                                                String memberId
                                                ) throws DatabaseException{
                                                      
          Log.log(Log.INFO,"ReportsManager","getBranchClaimApplicationDetails","Entered");
          ArrayList bankDetailsArray = new ArrayList();
          try
          {
            bankDetailsArray = reportDao.BranchClaimApplicationDetailsNew(startDate,endDate,memberId);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","getBranchClaimApplicationDetails","Exited");
          return bankDetailsArray;
	}
  
  /**
   * 
   * @param startDate
   * @param endDate
   * @param memberId
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
  public ArrayList  getStateBranchClaimApplicationDetails(java.sql.Date startDate,
                                                java.sql.Date endDate,
                                                String memberId
                                                ) throws DatabaseException{
                                                      
          Log.log(Log.INFO,"ReportsManager","getStateBranchClaimApplicationDetails","Entered");
          ArrayList bankDetailsArray = new ArrayList();
          try
          {
            bankDetailsArray = reportDao.StateBranchClaimApplicationDetails(startDate,endDate,memberId);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","getStateBranchClaimApplicationDetails","Exited");
          return bankDetailsArray;
	}
  
  public ArrayList  getSectorBranchClaimApplicationDetails(java.sql.Date startDate,
                                                java.sql.Date endDate,
                                                String memberId
                                                ) throws DatabaseException{
                                                      
          Log.log(Log.INFO,"ReportsManager","getSectorBranchClaimApplicationDetails","Entered");
          ArrayList bankDetailsArray = new ArrayList();
          try
          {
            bankDetailsArray = reportDao.SectorBranchClaimApplicationDetails(startDate,endDate,memberId);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","getSectorBranchClaimApplicationDetails","Exited");
          return bankDetailsArray;
	}
/**
   * 
   * @param startDate
   * @param endDate
   * @param id
   * @param memberId
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
	public ArrayList  getStateBranchApplicationDetails(java.sql.Date startDate,
                                                java.sql.Date endDate,
                                                String id, 
                                                String memberId
                                                ) throws DatabaseException{
                                                      
          Log.log(Log.INFO,"ReportsManager","getStateBranchApplicationDetails","Entered");
          ArrayList bankDetailsArray = new ArrayList();
          try
          {
            bankDetailsArray = reportDao.StateBranchApplicationDetails(startDate,endDate,id,memberId);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","getStateBranchApplicationDetails","Exited");
          return bankDetailsArray;
	}

	public ArrayList  ZoneDetailsForBranch(java.sql.Date startDate,java.sql.Date endDate,
						String id, String memberId) throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","ZoneDetailsForBranch","Entered");
		ArrayList bankDetailsArray = new ArrayList();
		try
		{
			bankDetailsArray = reportDao.ZoneDetailsForBranch(startDate,endDate,id,memberId);
		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","ZoneDetailsForBranch","Exited");
		return bankDetailsArray;
	}

	public ArrayList  ZoneDetailsForZone(java.sql.Date startDate,java.sql.Date endDate,
						String id, String memberId) throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","ZoneDetailsForZone","Entered");
		ArrayList bankDetailsArray = new ArrayList();
		try
		{
			bankDetailsArray = reportDao.ZoneDetailsForZone(startDate,endDate,id,memberId);
		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","ZoneDetailsForZone","Exited");
		return bankDetailsArray;
	}


	public ArrayList  ZoneDetailsForBank(java.sql.Date startDate,java.sql.Date endDate,
						String id, String memberId) throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","ZoneDetailsForBank","Entered");
		ArrayList bankDetailsArray = new ArrayList();
		try
		{
			bankDetailsArray = reportDao.ZoneDetailsForBank(startDate,endDate,id,memberId);
		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","ZoneDetailsForBank","Exited");
		return bankDetailsArray;
	}


	public ArrayList  sectorDetailsForBranch(java.sql.Date startDate,java.sql.Date endDate,
						String id, String memberId) throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","sectorDetailsForBranch","Entered");
		ArrayList bankDetailsArray = new ArrayList();
		try
		{
			bankDetailsArray = reportDao.sectorDetailsForBranch(startDate,endDate,id,memberId);
		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","sectorDetailsForBranch","Exited");
		return bankDetailsArray;
	}

	public ArrayList  sectorDetailsForZone(java.sql.Date startDate,java.sql.Date endDate,
						String id, String memberId) throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","sectorDetailsForZone","Entered");
		ArrayList bankDetailsArray = new ArrayList();
		try
		{
			bankDetailsArray = reportDao.sectorDetailsForZone(startDate,endDate,id,memberId);
		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","sectorDetailsForZone","Exited");
		return bankDetailsArray;
	}

	public ArrayList  sectorDetailsForBank(java.sql.Date startDate,java.sql.Date endDate,
						String id, String memberId) throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","sectorDetailsForBank","Entered");
		ArrayList bankDetailsArray = new ArrayList();
		try
		{
			bankDetailsArray = reportDao.sectorDetailsForBank(startDate,endDate,id,memberId);
		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","sectorDetailsForBank","Exited");
		return bankDetailsArray;
	}

	public ArrayList  stateDetailsForBranch(java.sql.Date startDate,java.sql.Date endDate,
						String id, String memberId) throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","stateDetailsForBranch","Entered");
		ArrayList bankDetailsArray = new ArrayList();
		try
		{
			bankDetailsArray = reportDao.stateDetailsForBranch(startDate,endDate,id,memberId);
		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","stateDetailsForBranch","Exited");
		return bankDetailsArray;
	}

	public ArrayList  stateDetailsForZone(java.sql.Date startDate,java.sql.Date endDate,
						String id, String memberId) throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","stateDetailsForZone","Entered");
		ArrayList bankDetailsArray = new ArrayList();
		try
		{
			bankDetailsArray = reportDao.stateDetailsForZone(startDate,endDate,id,memberId);
		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","stateDetailsForZone","Exited");
		return bankDetailsArray;
	}

	public ArrayList  stateDetailsForBank(java.sql.Date startDate,java.sql.Date endDate,
						String id, String memberId) throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","stateDetailsForBank","Entered");
		ArrayList bankDetailsArray = new ArrayList();
		try
		{
			bankDetailsArray = reportDao.stateDetailsForBank(startDate,endDate,id,memberId);
		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","stateDetailsForBank","Exited");
		return bankDetailsArray;
	}

	public ArrayList  districtDetailsForBranch(java.sql.Date startDate,java.sql.Date endDate,
						String id, String memberId, String state) throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","districtDetailsForBranch","Entered");
		ArrayList bankDetailsArray = new ArrayList();
		try
		{
			bankDetailsArray = reportDao.districtDetailsForBranch(startDate,endDate,id,memberId,state);
		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","districtDetailsForBranch","Exited");
		return bankDetailsArray;
	}

	public ArrayList  districtDetailsForZone(java.sql.Date startDate,java.sql.Date endDate,
						String id, String memberId, String state) throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","districtDetailsForZone","Entered");
		ArrayList bankDetailsArray = new ArrayList();
		try
		{
			bankDetailsArray = reportDao.districtDetailsForZone(startDate,endDate,id,memberId,state);
		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","districtDetailsForZone","Exited");
		return bankDetailsArray;
	}

	public ArrayList  districtDetailsForBank(java.sql.Date startDate,java.sql.Date endDate,
						String id, String memberId,String state) throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","districtDetailsForBank","Entered");
		ArrayList bankDetailsArray = new ArrayList();
		try
		{
			bankDetailsArray = reportDao.districtDetailsForBank(startDate,endDate,id,memberId,state);
		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","districtDetailsForBank","Exited");
		return bankDetailsArray;
	}

   public ArrayList  getZoneDetails(String zone, String id, java.sql.Date startDate,
   									java.sql.Date endDate) throws DatabaseException
   {
	Log.log(Log.INFO,"ReportsManager","getZoneDetails","Entered");
	   ArrayList bankDetailsArray = new ArrayList();
	   try
	   {
		   bankDetailsArray = reportDao.zoneDetails(zone,id,startDate,endDate);
	   }
	   catch(Exception exception)
	   {
		   throw new DatabaseException(exception.getMessage());
	   }
	Log.log(Log.INFO,"ReportsManager","getZoneDetails","Exited");
	   return bankDetailsArray;
   }


/**
   * 
   * @param zone
   * @param id
   * @param startDate
   * @param endDate
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
public ArrayList  getZoneDetailsNew(String zone, String id, java.sql.Date startDate,
   									java.sql.Date endDate) throws DatabaseException
   {
	Log.log(Log.INFO,"ReportsManager","getZoneDetailsNew","Entered");
	   ArrayList bankDetailsArray = new ArrayList();
	   try
	   {
		   bankDetailsArray = reportDao.zoneDetailsNew(zone,id,startDate,endDate);
	   }
	   catch(Exception exception)
	   {
		   throw new DatabaseException(exception.getMessage());
	   }
	Log.log(Log.INFO,"ReportsManager","getZoneDetailsNew","Exited");
	   return bankDetailsArray;
   }



 public ArrayList  getRSFDetails(String zone, String id, java.sql.Date startDate,
   									java.sql.Date endDate) throws DatabaseException
   {
	Log.log(Log.INFO,"ReportsManager","getRSFDetails","Entered");
	   ArrayList bankDetailsArray = new ArrayList();
	   try
	   {
		   bankDetailsArray = reportDao.getRSFDetails(zone,id,startDate,endDate);
	   }
	   catch(Exception exception)
	   {
		   throw new DatabaseException(exception.getMessage());
	   }
	Log.log(Log.INFO,"ReportsManager","getRSFDetails","Exited");
	   return bankDetailsArray;
   }

/**
   * 
   * @param zone
   * @param id
   * @param startDate
   * @param endDate
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
 public ArrayList  getGFAllocatedReportDetails(String payId) throws DatabaseException
   {
	Log.log(Log.INFO,"ReportsManager","getGFAllocatedReportDetails","Entered");
	   ArrayList rpDetailsArray = new ArrayList();
	   try
	   {
		   rpDetailsArray = reportDao.getGFAllocatedReportDetails(payId);
	   }
	   catch(Exception exception)
	   {
		   throw new DatabaseException(exception.getMessage());
	   }
	Log.log(Log.INFO,"ReportsManager","getGFAllocatedReportDetails","Exited");
	   return rpDetailsArray;
   }
   
   
   /**
   * 
   * @param payInstrumentNo
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
   public ArrayList  getPayInstrumentDetails(String payInstrumentNo,String memberId) throws DatabaseException
   {
	Log.log(Log.INFO,"ReportsManager","getPayInstrumentDetails","Entered");
	   ArrayList rpDetailsArray = new ArrayList();
	   try
	   {
		   rpDetailsArray = reportDao.getPayInstrumentDetails(payInstrumentNo,memberId);
	   }
	   catch(Exception exception)
	   {
		   throw new DatabaseException(exception.getMessage());
	   }
	Log.log(Log.INFO,"ReportsManager","getPayInstrumentDetails","Exited");
	   return rpDetailsArray;
   }

/**
   * 
   * @param payId
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
public ArrayList  getASFAllocatedReportDetails(String payId) throws DatabaseException
   {
	Log.log(Log.INFO,"ReportsManager","getASFAllocatedReportDetails","Entered");
	   ArrayList rpDetailsArray = new ArrayList();
	   try
	   {
		   rpDetailsArray = reportDao.getASFAllocatedReportDetails(payId);
	   }
	   catch(Exception exception)
	   {
		   throw new DatabaseException(exception.getMessage());
	   }
	Log.log(Log.INFO,"ReportsManager","getASFAllocatedReportDetails","Exited");
	   return rpDetailsArray;
   }

 public ArrayList  getbranchDetails(String bank,String zone, String id, java.sql.Date startDate,
   									java.sql.Date endDate) throws DatabaseException
   {
	Log.log(Log.INFO,"ReportsManager","getbranchDetails","Entered");
	   ArrayList bankDetailsArray = new ArrayList();
	   try
	   {
		   bankDetailsArray = reportDao.branchDetails(bank,zone,id,startDate,endDate);
	   }
	   catch(Exception exception)
	   {
		   throw new DatabaseException(exception.getMessage());
	   }
	Log.log(Log.INFO,"ReportsManager","getbranchDetails","Exited");
	   return bankDetailsArray;
   }

	public ArrayList  getSectorDetails(String sector,
                                     String id, 
                                     java.sql.Date startDate,
                                     java.sql.Date endDate
                                     )throws DatabaseException{
                                           
          Log.log(Log.INFO,"ReportsManager","getSectorDetails","Entered");
          ArrayList sectorDetailsArray = new ArrayList();
        
          
          try
          {
 sectorDetailsArray = reportDao.sectorDetails(sector,id,startDate,endDate);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","getSectorDetails","Exited");
          return sectorDetailsArray;
	}


/**
   * 
   * @param sector
   * @param id
   * @param startDate
   * @param endDate
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
public ArrayList  getSectorDetailsNew(String sector,
                                     String id, 
                                     java.sql.Date startDate,
                                     java.sql.Date endDate
                                     )throws DatabaseException{
                                           
          Log.log(Log.INFO,"ReportsManager","getSectorDetailsNew","Entered");
          ArrayList sectorDetailsArray = new ArrayList();
          try
          {
      
            sectorDetailsArray = reportDao.sectorDetailsNew(sector,id,startDate,endDate);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","getSectorDetailsNew","Exited");
          return sectorDetailsArray;
	}

	public ArrayList  getStatesWiseReport(String state, 
                                        String id, 
                                        java.sql.Date startDate,
                                        java.sql.Date endDate
                                        ) throws DatabaseException{
                                              
          Log.log(Log.INFO,"ReportsManager","getStatesWiseReport","Entered");
          ArrayList stateApplicationArray = new ArrayList();
          try
          {
            stateApplicationArray = reportDao.stateReport(state,id,startDate,endDate);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","getStatesWiseReport","Exited");
          return stateApplicationArray;
	}

/**
   * 
   * @param state
   * @param id
   * @param startDate
   * @param endDate
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
public ArrayList  getStatesWiseReportNew(String state, 
                                        String id, 
                                        java.sql.Date startDate,
                                        java.sql.Date endDate
                                        ) throws DatabaseException{
                                              
          Log.log(Log.INFO,"ReportsManager","getStatesWiseReportNew","Entered");
          ArrayList stateApplicationArray = new ArrayList();
          try
          {
            stateApplicationArray = reportDao.stateReportNew(state,id,startDate,endDate);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","getStatesWiseReportNew","Exited");
          return stateApplicationArray;
	}


public ArrayList  getStatesWorkshopWiseReportNew(String state,                                         
                                        java.sql.Date startDate,
                                        java.sql.Date endDate
                                        ) throws DatabaseException{
                                              
          Log.log(Log.INFO,"ReportsManager","getStatesWorkshopWiseReportNew","Entered");
          ArrayList stateApplicationArray = new ArrayList();
          try
          {
            stateApplicationArray = reportDao.stateworkshopReportDetailsNew(state,startDate,endDate);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","getStatesWorkshopWiseReportNew","Exited");
          return stateApplicationArray;
	}



public ArrayList  schemeworkshopReportDetailsNew(String state,                                         
                                        java.sql.Date startDate,
                                        java.sql.Date endDate
                                        ) throws DatabaseException{
                                              
          Log.log(Log.INFO,"ReportsManager","getAgencyWorkshopWiseReportNew","Entered");
          ArrayList stateApplicationArray = new ArrayList();
          try
          {
            stateApplicationArray = reportDao.schemeworkshopReportDetailsNew(state,startDate,endDate);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","getAgencyWorkshopWiseReportNew","Exited");
          return stateApplicationArray;
	}

public ArrayList  districtworkshopReportDtls(String state,                                         
                                        java.sql.Date startDate,
                                        java.sql.Date endDate
                                        ) throws DatabaseException{
                                              
          Log.log(Log.INFO,"ReportsManager","districtworkshopReportDtls","Entered");
          ArrayList stateApplicationArray = new ArrayList();
          try
          {
            stateApplicationArray = reportDao.districtworkshopReportNew(state,startDate,endDate);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","districtworkshopReportDtls","Exited");
          return stateApplicationArray;
	}

public ArrayList  statemliworkshopReportDtls(String state,                                         
                                        java.sql.Date startDate,
                                        java.sql.Date endDate
                                        ) throws DatabaseException{
                                              
          Log.log(Log.INFO,"ReportsManager","statemliworkshopReportDtls","Entered");
          ArrayList stateApplicationArray = new ArrayList();
          try
          {
            stateApplicationArray = reportDao.statemliworkshopReportDtls(state,startDate,endDate);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","statemliworkshopReportDtls","Exited");
          return stateApplicationArray;
	}

/**
   * 
   * @param state
   * @param startDate
   * @param endDate
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
public ArrayList  propagationmliworkshopReportDtls(String state,                                         
                                        java.sql.Date startDate,
                                        java.sql.Date endDate
                                        ) throws DatabaseException{
                                              
          Log.log(Log.INFO,"ReportsManager","propagationmliworkshopReportDtls","Entered");
          ArrayList stateApplicationArray = new ArrayList();
          try
          {
            stateApplicationArray = reportDao.propagationmliworkshopReportDtls(state,startDate,endDate);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","propagationmliworkshopReportDtls","Exited");
          return stateApplicationArray;
	}


public ArrayList  agencymliworkshopReportDtls(String state,                                         
                                        java.sql.Date startDate,
                                        java.sql.Date endDate
                                        ) throws DatabaseException{
                                              
          Log.log(Log.INFO,"ReportsManager","agencymliworkshopReportDtls","Entered");
          ArrayList stateApplicationArray = new ArrayList();
          try
          {
            stateApplicationArray = reportDao.agencymliworkshopReportNewDtls(state,startDate,endDate);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","agencymliworkshopReportDtls","Exited");
          return stateApplicationArray;
	}



/**
   * 
   * @param state
   * @param startDate
   * @param endDate
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
public ArrayList  propagationstateworkshopReportDtls(String state,                                         
                                        java.sql.Date startDate,
                                        java.sql.Date endDate
                                        ) throws DatabaseException{
                                              
          Log.log(Log.INFO,"ReportsManager","propagationstateworkshopReportDtls","Entered");
          ArrayList stateApplicationArray = new ArrayList();
          try
          {
            stateApplicationArray = reportDao.propagationstateworkshopReportDtls(state,startDate,endDate);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","propagationstateworkshopReportDtls","Exited");
          return stateApplicationArray;
	}

/**
   * 
   * @param state
   * @param startDate
   * @param endDate
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
public ArrayList  agencystateworkshopReportDtls(String state,                                         
                                        java.sql.Date startDate,
                                        java.sql.Date endDate
                                        ) throws DatabaseException{
                                              
          Log.log(Log.INFO,"ReportsManager","agencystateworkshopReportDtls","Entered");
          ArrayList stateApplicationArray = new ArrayList();
          try
          {
            stateApplicationArray = reportDao.agencystateworkshopReportNewDtls(state,startDate,endDate);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","agencystateworkshopReportDtls","Exited");
          return stateApplicationArray;
	}

/**
   * 
   * @param state
   * @param startDate
   * @param endDate
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
public ArrayList  unitwiseasf2011notallocatedSummaryDtls(String state,                                         
                                        java.sql.Date startDate,
                                        java.sql.Date endDate
                                        ) throws DatabaseException{
                                              
          Log.log(Log.INFO,"ReportsManager","unitwiseasf2011notallocatedSummaryDtls","Entered");
          ArrayList stateApplicationArray = new ArrayList();
          try
          {
            stateApplicationArray = reportDao.unitwiseasf2011notallocatedSummaryDtls(state,startDate,endDate);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","unitwiseasf2011notallocatedSummaryDtls","Exited");
          return stateApplicationArray;
	}






public ArrayList  agencypropagationworkshopReportDtls(String state,                                         
                                        java.sql.Date startDate,
                                        java.sql.Date endDate
                                        ) throws DatabaseException{
                                              
          Log.log(Log.INFO,"ReportsManager","agencypropagationworkshopReportDtls","Entered");
          ArrayList stateApplicationArray = new ArrayList();
          try
          {
            stateApplicationArray = reportDao.agencypropagationworkshopReportDtls(state,startDate,endDate);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","agencypropagationworkshopReportDtls","Exited");
          return stateApplicationArray;
	}




public ArrayList  propagationagencyworkshopReportDtls(String state,                                         
                                        java.sql.Date startDate,
                                        java.sql.Date endDate
                                        ) throws DatabaseException{
                                              
          Log.log(Log.INFO,"ReportsManager","propagationagencyworkshopReportDtls","Entered");
          ArrayList stateApplicationArray = new ArrayList();
          try
          {
            stateApplicationArray = reportDao.propagationagencyworkshopReportDtls(state,startDate,endDate);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","propagationagencyworkshopReportDtls","Exited");
          return stateApplicationArray;
	}


/**
   * 
   * @param state
   * @param startDate
   * @param endDate
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
public ArrayList  stateagencyworkshopReportDtls(String state,                                         
                                        java.sql.Date startDate,
                                        java.sql.Date endDate
                                        ) throws DatabaseException{
                                              
          Log.log(Log.INFO,"ReportsManager","stateagencyworkshopReportDtls","Entered");
          ArrayList stateApplicationArray = new ArrayList();
          try
          {
            stateApplicationArray = reportDao.stateagencyworkshopReportDtls(state,startDate,endDate);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","stateagencyworkshopReportDtls","Exited");
          return stateApplicationArray;
	}


public ArrayList  stateprogaramworkshopReportDtls(String state,                                         
                                        java.sql.Date startDate,
                                        java.sql.Date endDate
                                        ) throws DatabaseException{
                                              
          Log.log(Log.INFO,"ReportsManager","stateprogaramworkshopReportDtls","Entered");
          ArrayList stateApplicationArray = new ArrayList();
          try
          {
            stateApplicationArray = reportDao.stateprogaramworkshopReportDtls(state,startDate,endDate);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","stateprogaramworkshopReportDtls","Exited");
          return stateApplicationArray;
	}

	public ArrayList  getProposalSizeReport(java.sql.Date startDate,java.sql.Date endDate,String guarantee)
				 throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getProposalSizeReport","Entered");
		ArrayList proposalSizeArray = new ArrayList();
		try
		{
			 proposalSizeArray = reportDao.proposalSizeWise(startDate,endDate,guarantee);
		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getProposalSizeReport","Exited");
		return proposalSizeArray;
	}



	public ArrayList  getProposalSectorReport(java.sql.Date startDate,java.sql.Date endDate,String guarantee)
				 throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getProposalSectorReport","Entered");
		ArrayList proposalSectorArray = new ArrayList();
		try
		{
			proposalSectorArray = reportDao.proposalSectorWise(startDate,endDate,guarantee);
		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getProposalSectorReport","Exited");
		return proposalSectorArray;
	}

/*
	public ArrayList  getStateApplicationDetails(java.sql.Date startDate,java.sql.Date endDate,String guarantee)
				 throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getStateApplicationDetails","Entered");
		ArrayList stateApplicationArray = new ArrayList();
		try
		{
			// stateApplicationArray = reportDao.stateApplicationDetails(startDate,endDate,guarantee);
		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getStateApplicationDetails","Exited");
		return stateApplicationArray;
	}
*/

	public ArrayList  getMliDistrictDetails(String district, String flag, String bank,
	 		java.sql.Date startDate,java.sql.Date endDate) throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getMliDistrictDetails","Entered");
		ArrayList districtDetailsArray = new ArrayList();
		try
		{
			districtDetailsArray = reportDao.mliDistrictDetails(district,flag,bank,
															startDate,endDate);
		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getMliDistrictDetails","Exited");
		return districtDetailsArray;
	}



	public ArrayList getMonthlyProgressReport(java.sql.Date startDate,
                                            java.sql.Date endDate
                                            )throws DatabaseException{
                                                  
          Log.log(Log.INFO,"ReportsManager","getMonthlyProgressReport","Entered");
          ArrayList progressArray = new ArrayList();
          try
          {
            progressArray = reportDao.monthlyProgressReport(startDate,endDate);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","getMonthlyProgressReport","Exited");
          return progressArray;
	}

public ArrayList getMonthlyProgressReportNew(java.sql.Date startDate,
                                            java.sql.Date endDate
                                            )throws DatabaseException{
                                                  
          Log.log(Log.INFO,"ReportsManager","getMonthlyProgressReportNew","Entered");
          ArrayList progressArray = new ArrayList();
          try
          {
            progressArray = reportDao.getMonthlyProgressReportNew(startDate,endDate);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","getMonthlyProgressReportNew","Exited");
          return progressArray;
	}

/* added by shyam 01-04-2008 */
  /**
   * 
   * @param startDate
   * @param endDate
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
public ArrayList getminorityProgressReport(java.sql.Date startDate,
                                            java.sql.Date endDate
                                            )throws DatabaseException{
                                                  
          Log.log(Log.INFO,"ReportsManager","getminorityProgressReport","Entered");
          ArrayList progressArray = new ArrayList();
          try
          {
            progressArray = reportDao.minorityProgressReport(startDate,endDate);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","getminorityProgressReport","Exited");
          return progressArray;
	}

/*                           */
/**
   * 
   * added by sukumar@path on 12-05-2008
   * @param startDate
   * @param endDate
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */

public ArrayList getturnoverProgressReport(java.sql.Date startDate,
                                            java.sql.Date endDate
                                            )throws DatabaseException{
                                                  
          Log.log(Log.INFO,"ReportsManager","getturnoverProgressReport","Entered");
          ArrayList progressArray = new ArrayList();
          try
          {
            progressArray = reportDao.turnoverProgressReport(startDate,endDate);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","getturnoverProgressReport","Exited");
          return progressArray;
	}

/*                           */
/* added by sukumar 03-04-2008 */
public ArrayList getminorityStateProgressReport(java.sql.Date startDate,
                                            java.sql.Date endDate
                                            )throws DatabaseException{
                                                  
          Log.log(Log.INFO,"ReportsManager","getminorityStateProgressReport","Entered");
          ArrayList progressArray = new ArrayList();
          try
          {
            progressArray = reportDao.minorityStateProgressReport(startDate,endDate);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","getminorityStateProgressReport","Exited");
          return progressArray;
	}

                         
/**
   * 
   * @param startDate
   * @param endDate
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
public ArrayList getapplicationApprovedReport(java.sql.Date startDate,
                                            java.sql.Date endDate
                                            )throws DatabaseException{
                                                  
          Log.log(Log.INFO,"ReportsManager","getapplicationApprovedReport","Entered");
          ArrayList progressArray = new ArrayList();
          try
          {
            progressArray = reportDao.getapplicationApprovedReport(startDate,endDate);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","getapplicationApprovedReport","Exited");
          return progressArray;
	}

/* added by sukumar 08-04-2008 */
public ArrayList getcategorywiseguaranteeissuedprogressReport(java.sql.Date startDate,
                                            java.sql.Date endDate
                                            )throws DatabaseException{
                                                  
          Log.log(Log.INFO,"ReportsManager","getcategorywiseguaranteeissuedprogressReport","Entered");
          ArrayList progressArray = new ArrayList();
          try
          {
            progressArray = reportDao.categorywiseguaranteeissuedprogressReport(startDate,endDate);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","getcategorywiseguaranteeissuedprogressReport","Exited");
          return progressArray;
	}

/*                           */

	public ArrayList getGuaranteeCover(java.sql.Date startDate,java.sql.Date endDate, String bank)
					 throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getGuaranteeCover","Entered");
		ArrayList guaranteeCoverArray = new ArrayList();
		try
		{
			guaranteeCoverArray = reportDao.guaranteeCover(startDate,endDate,bank);

		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getGuaranteeCover","Exited");
		return guaranteeCoverArray;
	}


	public ArrayList getGuaranteeCoverSsi(java.sql.Date startDate,java.sql.Date endDate, String bank)
					 throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getGuaranteeCoverSsi","Entered");
		ArrayList guaranteeCoverArray = new ArrayList();
		try
		{
			guaranteeCoverArray = reportDao.guaranteeCoverSsi(startDate,endDate,bank);

		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getGuaranteeCoverSsi","Exited");
		return guaranteeCoverArray;
	}


	public ApplicationReport getApplicationReport(String cgpan,String ssi)
					 throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getApplicationReport","Entered");
		ApplicationReport appReport = null;
		try
		{
			appReport = reportDao.applicationReport(cgpan,ssi);
		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getApplicationReport","Exited");
		return appReport;
	}
/*
	public ApplicationReport getApplicationReportForBid(String bid,String cgpan,String ssi)
					 throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getApplicationReportForBid","Entered");
		ApplicationReport appReport = null;
		try
		{
			appReport = reportDao.getApplicationReportForBid(bid,cgpan,ssi);
		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getApplicationReportForBid","Exited");
		return appReport;
	}
*/

	public ApplicationReport getApplicationReportForMember(String cgpan,String ssi,
		 					String memberId)  throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getApplicationReportForMember","Entered");
		ApplicationReport appReport = new ApplicationReport();
		// ApplicationReport appReport = null;
		try
		{
			appReport = reportDao.applicationReportForMember(cgpan,ssi,memberId);
		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getApplicationReportForMember","Exited");
		return appReport;
	}

	public ApplicationReport applicationReportForBranch(String cgpan,
                                                      String ssi,
                                                      String memberId
                                                      )throws DatabaseException	{
                                                            
          Log.log(Log.INFO,"ReportsManager","applicationReportForBranch","Entered");
          ApplicationReport appReport = new ApplicationReport();
          try
          {
            appReport = reportDao.applicationReportForBranch(cgpan,ssi,memberId);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","applicationReportForBranch","Exited");
          return appReport;
	}


//	Fix Bug 02 - 07092004
	public ApplicationReport applicationReportForZone(String cgpan,String ssi,
							String bankId,String zoneId)  throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","applicationReportForZone","Entered");
		ApplicationReport appReport = new ApplicationReport();
		// ApplicationReport appReport = null;
		try
		{
			appReport = reportDao.applicationReportForZone(cgpan,ssi,bankId,zoneId);
		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","applicationReportForZone","Exited");
		return appReport;
	}// Fix Completed

	public ApplicationReport getApplicationReportForCgpan(String cgpan)
					 throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getApplicationReportForCgpan","Entered");
		ApplicationReport appReport = new ApplicationReport();
		try
		{
			appReport = reportDao.applicationReportForCgpan(cgpan);

		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getApplicationReportForCgpan","Exited");
		return appReport;
	}

/*
	public ArrayList  getDaRaised(java.sql.Date date)
						 throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getDaRaised","Entered");
		ArrayList daRaisedArray = new ArrayList();
		try
		{
			// daRaisedArray = reportDao.daRaised(date);
		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getDaRaised","Exited");
		return daRaisedArray;
	}

*/

	public ArrayList  getDanDetails(java.sql.Date startDate,java.sql.Date endDate,
					 				 String bank,String cgdan) throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getDanDetails","Entered");
		ArrayList danDetailsArray = new ArrayList();
		try
		{
			 danDetailsArray = reportDao.danDetails(startDate,endDate,bank,cgdan);
		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getDanDetails","Exited");
		return danDetailsArray;
	}


	public ArrayList  getDanDetailsGf(java.sql.Date startDate,java.sql.Date endDate,
									 String bank,String cgdan) throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getDanDetailsGf","Entered");
		ArrayList danDetailsArray = new ArrayList();
		try
		{
			 danDetailsArray = reportDao.danDetailsGf(startDate,endDate,bank,cgdan);
		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getDanDetailsGf","Exited");
		return danDetailsArray;
	}


	public ArrayList  getGfOutstandingMli(java.sql.Date startDate,
                                        java.sql.Date endDate
                                        )throws DatabaseException {
                                              
          Log.log(Log.INFO,"ReportsManager","getGfOutstandingMli","Entered");
          ArrayList gfOutstandingArray = new ArrayList();
          try
          {
            gfOutstandingArray = reportDao.gfOutstandingMlis(startDate,endDate);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","getGfOutstandingMli","Exited");
          return gfOutstandingArray;
	}


	public ArrayList  getGfOutstanding(java.sql.Date startDate,java.sql.Date endDate,
										String bank)
				 throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getGfOutstanding","Entered");
		ArrayList getGfOutstanding = new ArrayList();
		try
		{
			//System.out.println("bank:"+bank);
			getGfOutstanding = reportDao.gfOutstandingReport(startDate,endDate,bank);
		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getGfOutstanding","Exited");
		return getGfOutstanding;
	}



	public ArrayList  getCgpan(String ssiName) throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getCgpan","Entered");
		ArrayList getCgpan = new ArrayList();
		try
		{
			getCgpan = reportDao.getCgpan(ssiName);
		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getCgpan","Exited");
		return getCgpan;
	}

	public ArrayList  getCgpanForMember(String ssiName, String memberId) throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getCgpanForMember","Entered");
		ArrayList getCgpan = new ArrayList();
		try
		{
			getCgpan = reportDao.getCgpanForMember(ssiName,memberId);
		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getCgpanForMember","Exited");
		return getCgpan;
	}

	public ArrayList  getCgpanForBranch(String ssiName, String memberId) throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getCgpanForBranch","Entered");
		ArrayList getCgpan = new ArrayList();
		try
		{
			getCgpan = reportDao.getCgpanForBranch(ssiName,memberId);
		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getCgpanForBranch","Exited");
		return getCgpan;
	}


//	Fix Bug 02 - 07092004

	public ArrayList  getCgpanForZone(String ssiName, String bankId,String zoneId) throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getCgpanForZone","Entered");
		ArrayList getCgpan = new ArrayList();
		try
		{
			getCgpan = reportDao.getCgpanForZone(ssiName,bankId,zoneId);
		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getCgpanForZone","Exited");
		return getCgpan;
	}
//	Fix Completed


	public ArrayList  getSanctionedApplicationReport(java.sql.Date startDate,
                                                   java.sql.Date endDate,
                                                   String flag
                                                   )throws DatabaseException{
        
          Log.log(Log.INFO,"ReportsManager","getSanctionedApplicationReport","Entered");
          ArrayList sanctionedArray = new ArrayList();
          try
          {
            sanctionedArray = reportDao.sanctionedApplicationReport(startDate,endDate,flag);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","getSanctionedApplicationReport","Exited");
          return sanctionedArray;
	}

	public ArrayList  getSanctionedApplicationReportForBranch(java.sql.Date startDate,
                                                            java.sql.Date endDate,
                                                            String memberId, 
                                                            String flag
                                                            ) throws DatabaseException{
          Log.log(Log.INFO,"ReportsManager","getSanctionedApplicationReportForBranch","Entered");
          ArrayList sanctionedArray = new ArrayList();
          try
          {
            sanctionedArray = reportDao.getSanctionedApplicationReportForBranch(startDate,endDate,memberId,flag);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          Log.log(Log.INFO,"ReportsManager","getSanctionedApplicationReportForBranch","Exited");
          return sanctionedArray;
	}

	public ArrayList  getSanctionedApplicationReportForZone(java.sql.Date startDate,
				java.sql.Date endDate,String memberId, String flag) throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getSanctionedApplicationReportForZone","Entered");
		ArrayList sanctionedArray = new ArrayList();
		try
		{
			sanctionedArray = reportDao.getSanctionedApplicationReportForZone(startDate,endDate,memberId,flag);
		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getSanctionedApplicationReportForZone","Exited");
		return sanctionedArray;
	}

	public ArrayList  getSanctionedApplicationReportForBank(java.sql.Date startDate,
				java.sql.Date endDate,String memberId, String flag) throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getSanctionedApplicationReportForBank","Entered");
		ArrayList sanctionedArray = new ArrayList();
		try
		{
			sanctionedArray = reportDao.getSanctionedApplicationReportForBank(startDate,endDate,memberId,flag);
		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getSanctionedApplicationReportForBank","Exited");
		return sanctionedArray;
	}


	public ArrayList  getBranchApplicationDetailsForState(java.sql.Date startDate,
                                                        java.sql.Date endDate,
						String id, String memberId) throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getBranchApplicationDetailsForState","Entered");
		ArrayList bankDetailsArray = new ArrayList();
		try
		{
			bankDetailsArray = reportDao.BranchApplicationDetailsForState(startDate,endDate,id,memberId);
		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getBranchApplicationDetailsForState","Exited");
		return bankDetailsArray;
	}



	public ArrayList  getZoneApplicationDetailsForState(java.sql.Date startDate,java.sql.Date endDate,
						String id, String memberId) throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getZoneApplicationDetailsForState","Entered");
		ArrayList bankDetailsArray = new ArrayList();
		try
		{
			bankDetailsArray = reportDao.ZoneApplicationDetailsForState(startDate,endDate,id,memberId);
		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getZoneApplicationDetailsForState","Exited");
		return bankDetailsArray;
	}

	public ArrayList  getBankApplicationDetailsForState(java.sql.Date startDate,java.sql.Date endDate,
						String id, String memberId) throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getBankApplicationDetailsForState","Entered");
		ArrayList bankDetailsArray = new ArrayList();
		try
		{
			bankDetailsArray = reportDao.BankApplicationDetailsForState(startDate,endDate,id,memberId);
		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getBankApplicationDetailsForState","Exited");
		return bankDetailsArray;
	}


	public ArrayList  getBranchApplicationDetailsForDistrict(java.sql.Date startDate,java.sql.Date endDate,
						String id, String memberId) throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getBranchApplicationDetailsForDistrict","Entered");
		ArrayList bankDetailsArray = new ArrayList();
		try
		{
			bankDetailsArray = reportDao.BranchApplicationDetailsForDistrict(startDate,endDate,id,memberId);
		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getBranchApplicationDetailsForDistrict","Exited");
		return bankDetailsArray;
	}



	public ArrayList  getZoneApplicationDetailsForDistrict(java.sql.Date startDate,java.sql.Date endDate,
						String id, String memberId) throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getZoneApplicationDetailsForDistrict","Entered");
		ArrayList bankDetailsArray = new ArrayList();
		try
		{
			bankDetailsArray = reportDao.ZoneApplicationDetailsForDistrict(startDate,endDate,id,memberId);
		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getZoneApplicationDetailsForDistrict","Exited");
		return bankDetailsArray;
	}

	public ArrayList  getBankApplicationDetailsForDistrict(java.sql.Date startDate,java.sql.Date endDate,
						String id, String memberId) throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getBankApplicationDetailsForDistrict","Entered");
		ArrayList bankDetailsArray = new ArrayList();
		try
		{
			bankDetailsArray = reportDao.BankApplicationDetailsForDistrict(startDate,endDate,id,memberId);
		}
		catch(Exception exception)
		{
			throw new DatabaseException(exception.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getBankApplicationDetailsForDistrict","Exited");
		return bankDetailsArray;
	}


	/*
	* This method returns a vector of HashMap. Each HashMap contains the Bank Name, Member Id
	* and the Claim Reference Number.
	*/
	public Vector getListOfClaimRefNumbers(java.sql.Date fromDate, 
                                         java.sql.Date toDate, 
                                         String clmApplicationStatusFlag, 
                                         String memberId
                                         ) throws DatabaseException	{
                                               
          String bankId   = memberId.substring(0,4);
          String zoneId   = memberId.substring(4,8);
          String branchId = memberId.substring(8,12);
      
          if(bankId.equals(Constants.CGTSI_USER_BANK_ID))
          {
             return reportDao.getListOfClaimRefNumbers(fromDate,toDate,clmApplicationStatusFlag);
          }
          if((zoneId.equals("0000")) && (branchId.equals("0000")))
          {
             return reportDao.getListOfClaimRefNumbers(fromDate,toDate,clmApplicationStatusFlag, bankId);
          }
          if(branchId.equals("0000"))
          {
             return reportDao.getListOfClaimRefNumbers(fromDate,toDate,clmApplicationStatusFlag, bankId, zoneId);
          }
          else
          {
             return reportDao.getListOfClaimRefNumbers(fromDate,toDate,clmApplicationStatusFlag, bankId, zoneId, branchId);
          }
	}

/**
   * 
   * @param fromDate
   * @param toDate
   * @param clmApplicationStatusFlag
   * @param memberId
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
public Vector getListOfClaimRefNumbersNew(java.sql.Date fromDate, 
                                         java.sql.Date toDate, 
                                         String clmApplicationStatusFlag, 
                                         String memberId
                                         ) throws DatabaseException	{
                                               
          String bankId   = memberId.substring(0,4);
          String zoneId   = memberId.substring(4,8);
          String branchId = memberId.substring(8,12);
      
          if(bankId.equals(Constants.CGTSI_USER_BANK_ID))
          {
             return reportDao.getListOfClaimRefNumbersNew(fromDate,toDate,clmApplicationStatusFlag);
          }
          if((zoneId.equals("0000")) && (branchId.equals("0000")))
          {
             return reportDao.getListOfClaimRefNumbers(fromDate,toDate,clmApplicationStatusFlag, bankId);
          }
          if(branchId.equals("0000"))
          {
             return reportDao.getListOfClaimRefNumbers(fromDate,toDate,clmApplicationStatusFlag, bankId, zoneId);
          }
          else
          {
             return reportDao.getListOfClaimRefNumbers(fromDate,toDate,clmApplicationStatusFlag, bankId, zoneId, branchId);
          }
}

   /*
    * This method returns a ClaimApplication object for the given Claim Reference
    * Number.
    */
	public ClaimApplication displayClmRefNumberDtl(String claimRefNumber, String status, String memberId) throws DatabaseException
	{
		if(status != null)
		{
			if((status.equals(ClaimConstants.CLM_APPROVAL_STATUS)) ||
			  (status.equals(ClaimConstants.CLM_REJECT_STATUS)))
			  {
		            return reportDao.displayClmRefNumberDtl(claimRefNumber, memberId);
			  }
			//
			/*if((status.equals(ClaimConstants.CLM_PENDING_STATUS)) ||
			  (status.equals(ClaimConstants.CLM_FORWARD_STATUS)) ||
                        (status.equals(ClaimConstants.CLM_TEMPORARY_CLOSE)) ||
                        (status.equals(ClaimConstants.CLM_TEMPORARY_REJECT)) ||
			  (status.equals(ClaimConstants.CLM_HOLD_STATUS)) || (status.equals("RT") || 
                          status.equals(ClaimConstants.CLM_WITHDRAWN) || status.equals("RR")))*/ //end
			if(status.equals("NE") || status.equals("FW") || status.equals("TC") || status.equals("TR") || status.equals("HO") || status.equals("RT") || status.equals("WD") || status.equals("RR") || status.equals("MR"))
			  {
                            return reportDao.displayClmRefNumberDtl(claimRefNumber, status, memberId);
			  }
		}
        return null;
	}
		// 02/06/2017 
	public ClaimApplication displayClmRefNumberDtls(String claimRefNumber, String status, String memberId)
    throws DatabaseException
    {
		if(status != null)
		{
			if(status.equals("AP") || status.equals("RE"))
				return reportDao.displayClmRefNumberDtl(claimRefNumber, memberId);
			if(status.equals("NE") || status.equals("FW") || status.equals("TC") || status.equals("TR") || status.equals("HO") || "RR".equals(status) || "RT".equals(status) || "RU".equals(status) || "AC".equals(status) || "SR".equals(status) || "WD".equals(status))
				return reportDao.displayClmRefNumberDtl(claimRefNumber, status, memberId);
		}
		return null;
    }
	//end

   /*
    * This method returns Vector of Settlement Details of Claim First and Second Installments
    */
    public Vector getSettlementDetails(java.sql.Date sqlFromDate, java.sql.Date toDate, String memberId,String flag) throws DatabaseException
    {
		return reportDao.getSettlementDetails(sqlFromDate,toDate,memberId,flag);
	}

	public ArrayList getQueryReport(QueryBuilderFields queryFields) throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getQueryReport","Entered");
		String query = null;
		ArrayList arrResult = null;
		query = getPartQuery(queryFields);
		String[] values = getWhereClause(queryFields);
		String orderBy = getOrderByClause(queryFields);
		String junkQuery = values[0];

		if(orderBy != null)
		{
			junkQuery = junkQuery.concat(orderBy);
		}
		Log.log(Log.INFO,"ReportsManager","getQueryReport","where + orderby : "+junkQuery);
		String join = getJoin(query.concat(junkQuery));
		String temp = null;
		if(!join.equalsIgnoreCase(""))
		{
			if(values[0].indexOf("where") != -1)
			{
				temp = values[0].substring(6);
				if(orderBy != null)
				{
					values[0] = "where "+join+ " ("+temp+")" +orderBy;
				}
				else
				{
					values[0] = "where "+join+ " ("+temp+")";
				}
			}
			else
			{
				join = join.substring(0,join.length()-5);
				if(orderBy != null)
				{
					values[0] = "where "+join + " " + orderBy;
				}
				else
				{
					values[0] = "where "+join;
				}
			}
		}
		Log.log(Log.INFO,"ReportsManager","getQueryReport","final where : "+values[0]);
		String fromClause = getFromClause(query.concat(values[0]));
		join = null;
		temp = null;
		query = (query.concat(" ").concat(fromClause).concat(" ").concat(values[0])).trim();
		Log.log(Log.INFO,"ReportsManager","getQueryReport","query : "+query);
		values[0] = query;
		arrResult = reportDao.getQueryReport(values);
		query = null;
		values = null;
		orderBy = null;
		fromClause = null;
		Log.log(Log.INFO,"ReportsManager","getQueryReport","Exited");
		return arrResult;

	}

	private String getJoin(String junkQuery)
	{
		Log.log(Log.INFO,"ReportsManager","getJoin","entered");
		StringBuffer partWhere = new StringBuffer("");
		if(junkQuery.indexOf("application_detail") != -1)
		{
			if(junkQuery.indexOf("ssi_detail") != -1)
			{
				partWhere = partWhere.append("application_detail.ssi_reference_number = ssi_detail.ssi_reference_number and ");
			}
			if(junkQuery.indexOf("term_loan_detail") != -1)
			{
				partWhere = partWhere.append("application_detail.app_ref_no = term_loan_detail.app_ref_no(+) and ");
			}
			if(junkQuery.indexOf("working_capital_detail") != -1)
			{
				partWhere = partWhere.append("application_detail.app_ref_no = working_capital_detail.app_ref_no(+) and ");
			}
			if(junkQuery.indexOf("promoter_detail") != -1)
			{
				partWhere = partWhere.append("application_detail.ssi_reference_number = promoter_detail.ssi_reference_number and ");
			}
		}
		else
		{
			if(junkQuery.indexOf("ssi_detail") != -1)
			{
				if(junkQuery.indexOf("term_loan_detail") != -1)
				{
					partWhere = partWhere.append("application_detail.ssi_reference_number = ssi_detail.ssi_reference_number and application_detail.app_ref_no = term_loan_detail.app_ref_no(+) and ");
				}
				if(junkQuery.indexOf("working_capital_detail") != -1)
				{
					partWhere = partWhere.append("application_detail.ssi_reference_number = ssi_detail.ssi_reference_number and application_detail.app_ref_no = working_capital_detail.app_ref_no(+) and ");
				}
				if(junkQuery.indexOf("promoter_detail") != -1)
				{
					partWhere = partWhere.append("ssi_detail.ssi_reference_number = promoter_detail.ssi_reference_number and ");
				}
			}
			else
			{
				if(junkQuery.indexOf("term_loan_detail") != -1)
				{
					if(junkQuery.indexOf("working_capital_detail") != -1)
					{
						partWhere = partWhere.append("term_loan_detail.app_ref_no = working_capital_detail.app_ref_no(+) and ");
					}
					if(junkQuery.indexOf("promoter_detail") != -1)
					{
						partWhere = partWhere.append("application_detail.app_ref_no = term_loan_detail.app_ref_no(+) and promoter_detail.ssi_reference_number = application_detail.ssi_reference_number and ");
					}
				}
				else
				{
					if(junkQuery.indexOf("working_capital_detail") != -1)
					{
						if(junkQuery.indexOf("promoter_detail") != -1)
						{
							partWhere = partWhere.append("application_detail.app_ref_no = working_capital_detail.app_ref_no(+) and promoter_detail.ssi_reference_number = application_detail.ssi_reference_number and ");
						}
					}
				}
			}
		}
		Log.log(Log.INFO,"ReportsManager","getJoin","join : "+partWhere);
		Log.log(Log.INFO,"ReportsManager","getJoin","exited");
		return partWhere.toString();
	}

	private String getFromClause(String junkQuery)
	{
		Log.log(Log.INFO,"ReportsManager","getFromClause","entered");
		StringBuffer fromClause = new StringBuffer("from ");
		//StringBuffer partWhere = new StringBuffer("");
		if(junkQuery.indexOf("application_detail") != -1)
		{
			fromClause = fromClause.append("application_detail, ");
		}
		if(junkQuery.indexOf("ssi_detail") != -1)
		{
			fromClause = fromClause.append("ssi_detail, ");
		}
		if(junkQuery.indexOf("term_loan_detail") != -1)
		{
			fromClause = fromClause.append("term_loan_detail, ");
		}
		if(junkQuery.indexOf("working_capital_detail") != -1)
		{
			fromClause = fromClause.append("working_capital_detail, ");
		}
		if(junkQuery.indexOf("promoter_detail") != -1)
		{
			fromClause = fromClause.append("promoter_detail, ");
		}
		String from = fromClause.substring(0, fromClause.length()-2);
		Log.log(Log.INFO,"ReportsManager","getFromClause","from clause : "+from);
		fromClause = null;
		Log.log(Log.INFO,"ReportsManager","getFromClause","exited");
		return from;
	}

	private String getOrderByClause(QueryBuilderFields queryFields)
	{
		Log.log(Log.INFO,"ReportsManager","getOrderByClause","Entered");
		StringBuffer orderByClause = new StringBuffer("order by ");
		String orderBy = null;
		if(queryFields.isAppSubmittedChkBox())
		{
			orderByClause = orderByClause.append("application_detail.app_submitted_dt, ");
		}
		if(queryFields.isGuarFeePaidChkBox())
		{
			orderByClause = orderByClause.append("application_detail.app_guar_start_date_time, ");
		}
		if(queryFields.isTcSanctionedChkBox())
		{
			orderByClause = orderByClause.append("term_loan_detail.trm_amount_sanctioned, ");
		}
		if(queryFields.isWcSanctionedChkBox())
		{
			orderByClause = orderByClause.append("working_capital_detail.wcp_fb_limit_sanctioned, ");
		}
		orderBy = orderByClause.toString();
		if(orderBy.equalsIgnoreCase("order by "))
		{
			orderBy = null;
		}
		else
		{
			orderBy = orderBy.substring(0, orderBy.length()-2);
		}
		orderByClause = null;
		Log.log(Log.INFO,"ReportsManager","getOrderByClause","order by : "+orderBy);
		Log.log(Log.INFO,"ReportsManager","getOrderByClause","Exited");
		return orderBy;
	}

	private String[] getWhereClause(QueryBuilderFields queryFields)
	{
		Log.log(Log.INFO,"ReportsManager","getWhereClause","Entered");
		StringBuffer whereClause = new StringBuffer("where ");
		String[] values = new String[7];
		String where = null;
		int counter = 1;

		if((queryFields.getCgpan() != null) && !queryFields.getCgpan().equalsIgnoreCase(""))
		{
			whereClause = whereClause.append("upper(application_detail.cgpan) ").
			append(queryFields.getCgpanCombo()).append("upper(?) ").
			append(queryFields.getCgpanBoolean());
			whereClause = whereClause.append(" ");
			values[counter] = "string|"+queryFields.getCgpan().toUpperCase();
			counter++;

		}
		if((queryFields.getAppSubmitted() != null) && !queryFields.getAppSubmitted().equalsIgnoreCase(""))
		{
			whereClause = whereClause.append("trunc(application_detail.app_submitted_dt) ").
			append(queryFields.getAppSubmittedCombo()).append(" ? ").
			append(queryFields.getAppSubmittedBoolean());
			whereClause = whereClause.append(" ");
			values[counter] = "date|"+queryFields.getAppSubmitted();
			counter++;

		}
		if((queryFields.getGuarFeePaid() != null) && !queryFields.getGuarFeePaid().equalsIgnoreCase(""))
		{
			whereClause = whereClause.append("trunc(application_detail.app_guar_start_date_time) ").
			append(queryFields.getGuarFeePaidCombo()).append(" ? ").
			append(queryFields.getGuarFeePaidBoolean());
			whereClause = whereClause.append(" ");
			values[counter] = "date|"+queryFields.getGuarFeePaid();
			counter++;
		}
		if((queryFields.getSsiName() != null) && !queryFields.getSsiName().equalsIgnoreCase(""))
		{
			whereClause = whereClause.append("upper(ssi_detail.ssi_unit_name) ").
			append(queryFields.getSsiNameCombo()).append(" upper(?) ").
			append(queryFields.getSsiNameBoolean());
			whereClause = whereClause.append(" ");
			values[counter] = "string|"+queryFields.getSsiName().toUpperCase();
			if(queryFields.getSsiNameCombo().equalsIgnoreCase("LIKE"))
			{
				values[counter] = "string|"+"%"+queryFields.getSsiName().toUpperCase()+"%";
			}
			counter++;
		}
		if((queryFields.getTcSanctioned() != null) && !queryFields.getTcSanctioned().equalsIgnoreCase(""))
		{
			whereClause = whereClause.append("term_loan_detail.trm_amount_sanctioned ").
			append(queryFields.getTcSanctionedCombo()).append(" ? ").
			append(queryFields.getTcSanctionedBoolean());
			whereClause = whereClause.append(" ");
			values[counter] = "number|"+queryFields.getTcSanctioned();
			counter++;
		}
		if((queryFields.getWcSanctioned() != null) && !queryFields.getWcSanctioned().equalsIgnoreCase(""))
		{
			whereClause = whereClause.append("working_capital_detail.wcp_fb_limit_sanctioned ").
			append(queryFields.getWcSanctionedCombo()).append(" ? ").
			append(queryFields.getWcSanctionedBoolean());
			whereClause = whereClause.append(" ");
			values[counter] = "number|"+queryFields.getWcSanctioned();
			counter++;
		}
		where = whereClause.toString();
		if(where.equalsIgnoreCase("where "))
		{
			where = "";
		}
		else
		{
			if(where.endsWith("and "))
			{
				where = where.substring(0, where.length()-5);
			}
			else if(where.endsWith("or "))
			{
				where = where.substring(0, where.length()-4);
			}
		}
		values[0] = where;
		whereClause = null;
		Log.log(Log.INFO,"ReportsManager","getWhereClause","where clause : "+where);
		where = null;
		Log.log(Log.INFO,"ReportsManager","getWhereClause","Exited");
		return values;
	}

	private String getPartQuery(QueryBuilderFields queryFields)
	{
		Log.log(Log.INFO,"ReportsManager","getPartQuery","Entered");
		StringBuffer partQuery = new StringBuffer("select ");
		/* getting the fields that user want to see as output */


		if(queryFields.isApplnRefnoSelChkBox())
		{
			partQuery = partQuery.append("application_detail.APP_REF_NO, ");
		}
		if(queryFields.isCgpanSelChkBox())
		{
			partQuery = partQuery.append("application_detail.CGPAN, ");
		}
		if(queryFields.isBankRefNoSelChkBox())
		{
			partQuery = partQuery.append("application_detail.app_bank_app_ref_no, ");
		}
		if(queryFields.isAppSubmittedSelChkBox())
		{
			partQuery = partQuery.append("application_detail.app_submitted_dt, ");
		}
		if(queryFields.isTcPLRSelChkBox())
		{
			partQuery = partQuery.append("term_loan_detail.trm_plr, ");
		}
		if(queryFields.isWcPLRSelChkBox())
		{
			partQuery = partQuery.append("working_capital_detail.wcp_plr, ");
		}
		if(queryFields.isChiefPromoterSelChkBox())
		{
			partQuery = partQuery.append("promoter_detail.pmr_chief_title, ");
			partQuery = partQuery.append("promoter_detail.pmr_chief_first_name, ");
			partQuery = partQuery.append("promoter_detail.pmr_chief_middle_name, ");
			partQuery = partQuery.append("promoter_detail.pmr_chief_last_name, ");
		}
		if(queryFields.isItPANSelChkBox())
		{
			partQuery = partQuery.append("ssi_detail.ssi_it_pan, ");
		}
		if(queryFields.isSsiDetailsSelChkBox())
		{
			partQuery = partQuery.append("ssi_detail.ssi_unit_name, ");
			partQuery = partQuery.append("ssi_detail.ssi_address, ");
			partQuery = partQuery.append("ssi_detail.ssi_city, ");
			partQuery = partQuery.append("ssi_detail.ssi_state_name, ");
			partQuery = partQuery.append("ssi_detail.ssi_district_name, ");
			partQuery = partQuery.append("ssi_detail.ssi_pincode, ");
			partQuery = partQuery.append("ssi_detail.ssi_constitution, ");
		}
		if(queryFields.isTcSanctionedSelChkBox())
		{
			partQuery = partQuery.append("term_loan_detail.trm_amount_sanctioned, ");
		}
		if(queryFields.isTcInterestRateSelChkBox())
		{
			partQuery = partQuery.append("term_loan_detail.trm_interest_rate, ");
		}
		if(queryFields.isTcTenureSelChkBox())
		{
			partQuery = partQuery.append("term_loan_detail.trm_tenure, ");
		}
		if(queryFields.isWcSanctionedSelChkBox())
		{
			partQuery = partQuery.append("working_capital_detail.wcp_fb_limit_sanctioned, ");
		}
		if(queryFields.isProjectOutlaySelChkBox())
		{
			partQuery = partQuery.append("application_detail.app_project_outlay, ");
		}
		if(queryFields.isApprovedDateSelChkBox())
		{
			partQuery = partQuery.append("application_detail.app_approved_date_time, ");
		}
		if(queryFields.isApprovedAmtSelChkBox())
		{
			partQuery = partQuery.append("application_detail.app_approved_amount, ");
		}
		if(queryFields.isGuarFeeSelChkBox())
		{
			partQuery = partQuery.append("application_detail.app_guarantee_fee, ");
		}
		if(queryFields.isGuarFeeStartDateSelChkBox())
		{
			partQuery = partQuery.append("application_detail.app_guar_start_date_time, ");
		}
		String subQuery = partQuery.substring(0,partQuery.length()-2);
		Log.log(Log.INFO,"ReportsManager","getPartQuery","sub query : " + subQuery);
		partQuery = null;
		Log.log(Log.INFO,"ReportsManager","getPartQuery","Exited");
		return subQuery;
	}

	public Vector displayMemberSettlementDtls(java.sql.Date fromDate, 
                                            java.sql.Date toDate
                                            ) throws DatabaseException{
                                            
          return reportDao.displayMemberSettlementDtls(fromDate, toDate);
	}

	public ArrayList getDefaulterReport(DefaulterInputFields defFields) throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","getDefaulterReport","Entered");
		ArrayList defarray = new ArrayList();
		try
		{
			defarray = reportDao.defaulterReport(defFields);
		}
		catch(Exception e)
		{
			throw new DatabaseException(e.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","getDefaulterReport","Exited");
		return defarray;
	}
	public Map getClaimAttachments(String claimRefNumber,String status,
		boolean internetUser) throws DatabaseException
	{
		return reportDao.getClaimAttachments(claimRefNumber,status,internetUser);
	}
	
	/*
	 * public int getHistoryCount(String memberId) throws DatabaseException { int
	 * historyCount = this.reportDao.getHistoryCount(memberId);
	 * 
	 * return historyCount; }
	 */
  
	public int submitCumlativeReport(CumlativeDailyReportBean cdReport,User user) throws DatabaseException
	{
		Log.log(Log.INFO,"ReportsManager","submitCumlativeReport","Entered");
		int retString=0;
		try
		{
			retString = reportDao.submitCumlativeReport(cdReport,user);
		}
		catch(Exception e)
		{
			throw new DatabaseException(e.getMessage());
		}
		Log.log(Log.INFO,"ReportsManager","submitCumlativeReport","Exited");
		return retString;
	}
	// end

}
