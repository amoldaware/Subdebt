package com.cgtsi.application;

import com.cgtsi.admin.Administrator;
import com.cgtsi.admin.ParameterMaster;
import com.cgtsi.admin.User;

import com.cgtsi.claim.ClaimsProcessor;
import com.cgtsi.common.DatabaseException;
import com.cgtsi.common.Log;
import com.cgtsi.common.MessageException;
import com.cgtsi.mcgs.MCGFDetails;
import com.cgtsi.mcgs.MCGSProcessor;
import com.cgtsi.registration.MLIInfo;
import com.cgtsi.registration.Registration;
import com.cgtsi.risk.GlobalLimits;
import com.cgtsi.risk.RiskManagementProcessor;
import com.cgtsi.risk.SubSchemeValues;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.util.DateUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.struts.action.ActionError;  // 22/05/2017
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;

public class ApplicationProcessor
{
  ApplicationDAO appDAO;
  Administrator admin;             // 22/05/2017
  String recordArray[]=null;
  ExcelModuleMethods objExcelModuleMethod=null;
  ArrayList statesList = null;
   String bankIds = "";
	ArrayList industryNatureList = null;
	ArrayList constitutions=new ArrayList();
	double maxValue = 0.0D;       // 22/05/2017
	ParameterMaster paramMaster;  // 22/05/2017
	 
  public ApplicationProcessor()
  {
	  objExcelModuleMethod= new ExcelModuleMethods();
    this.appDAO = new ApplicationDAO();
    // 22/05/2017
    this.admin = new Administrator();
    
    try {
    	paramMaster = admin.getParameter();
    } catch (DatabaseException e) {
    	// TODO Auto-generated catch block
//    	e.printStackTrace();
    }
    // end
        maxValue = paramMaster.getMaxApprovedAmt();
  }
  
//Milind 24-02-2016 start
 
//Milind 24-02-2016 end
  public Application submitNewApplication(Application application, String createdBy)
    throws DatabaseException, InvalidApplicationException, MessageException
  {
    Log.log(4, "ApplicationProcessor", "submitNewApplication", "Entered");
    application = this.appDAO.submitApplication(application, createdBy);      //********************************* DKR *****************

    Log.log(4, "ApplicationProcessor", "submitNewApplication", "Exited");

    return application;
  }
  
  public Application submitMudraApplication(Application application, String createdBy)
		    throws DatabaseException, InvalidApplicationException, MessageException
		  {
		    Log.log(4, "ApplicationProcessor", "submitNewApplication", "Entered");
		    application = this.appDAO.submitMudraApplication(application, createdBy);      //********************************* DKR *****************

		    Log.log(4, "ApplicationProcessor", "submitNewApplication", "Exited");

		    return application;
		  }

  public Application submitNewRSFApplication(Application application, String createdBy)
    throws DatabaseException, InvalidApplicationException, MessageException
  {
    Log.log(4, "ApplicationProcessor", "submitNewRSFApplication", "Entered");
    application = this.appDAO.submitRSFApplication(application, createdBy);

    Log.log(4, "ApplicationProcessor", "submitNewRSFApplication", "Exited");

    return application;
  }

  public Application submitNewRSF2Application(Application application, String createdBy)
    throws DatabaseException, InvalidApplicationException, MessageException
  {
  //  System.out.println("applicationProcessor:  line 95");

    Log.log(4, "ApplicationProcessor", "submitNewRSF2Application", "Entered");
    application = this.appDAO.submitRSF2Application(application, createdBy);

    Log.log(4, "ApplicationProcessor", "submitNewRSF2Application", "Exited");

    return application;
  }

  public BorrowerDetails fetchBorrowerDetails(String cgbid, String cgpan)
    throws DatabaseException, NoApplicationFoundException
  {
    Log.log(4, "ApplicationProcessor", "fetchBorrowerDetails", "Entered");

    Log.log(4, "ApplicationProcessor", "fetchBorrowerDetails", "CGBID to fetch SSI Details :" + cgbid);

    Log.log(4, "ApplicationProcessor", "fetchBorrowerDetails", "CGPAN to fetch SSI Details :" + cgpan);

    BorrowerDetails borrowerDetails = this.appDAO.fetchBorrowerDtls(cgbid, cgpan);
    if (borrowerDetails == null) {
      throw new NoApplicationFoundException("No Application Found");
    }

    Log.log(4, "ApplicationProcessor", "fetchBorrowerDetails", "Exited");

    return borrowerDetails;
  }

  public BorrowerDetails fetchBorrowerDetailsNew(String cgbid, String cgpan, String loanType)
    throws DatabaseException, NoApplicationFoundException
  {
    Log.log(4, "ApplicationProcessor", "fetchBorrowerDetailsNew", "Entered");

    Log.log(4, "ApplicationProcessor", "fetchBorrowerDetailsNew", "CGBID to fetch SSI Details :" + cgbid);

    Log.log(4, "ApplicationProcessor", "fetchBorrowerDetailsNew", "CGPAN to fetch SSI Details :" + cgpan);

    BorrowerDetails borrowerDetails = this.appDAO.fetchBorrowerDtlsNew(cgbid, cgpan, loanType);
    if (borrowerDetails == null) {
      throw new NoApplicationFoundException("No Application Found");
    }

    Log.log(4, "ApplicationProcessor", "fetchBorrowerDetailsNew", "Exited");

    return borrowerDetails;
  }

  public void rejectApplication(String cgpan, String remarks, String userId)
    throws DatabaseException
  {
    Log.log(4, "ApplicationProcessor", "rejectApplication", "Entered");

    ApplicationDAO appDAO = new ApplicationDAO();

    appDAO.rejectApplication(cgpan, remarks, userId);
    Log.log(4, "ApplicationProcessor", "rejectApplication", "Exited");
  }

  public ArrayList checkDuplicatePath(String mliFlag, String bankName)
    throws DatabaseException
  {
    Log.log(4, "ApplicationProcessor", "checkDuplicatePath", "Entered");

    ArrayList duplicateCriteriaObj = new ArrayList();
    ArrayList tcDuplicateCriteriaObj = new ArrayList();
    ArrayList wcDuplicateCriteriaObj = new ArrayList();
    ApplicationDAO appDAO = new ApplicationDAO();

    HashMap approvedPendingApplications = appDAO.checkDuplicatePath(bankName);

    HashMap tcApprovedApplications = (HashMap)approvedPendingApplications.get("tcApproved");
    HashMap wcApprovedApplications = (HashMap)approvedPendingApplications.get("wcApproved");
    HashMap tcPendingApplications = (HashMap)approvedPendingApplications.get("tcPending");
    HashMap wcPendingApplications = (HashMap)approvedPendingApplications.get("wcPending");
    Set tcApprovedAppsKeys = tcApprovedApplications.keySet();
    Set wcApprovedAppsKeys = wcApprovedApplications.keySet();
    Set tcPendingAppsKeys = tcPendingApplications.keySet();
    Set wcPendingAppsKeys = wcPendingApplications.keySet();
    Iterator tcApprovedAppsIterator = tcApprovedAppsKeys.iterator();
    Iterator wcApprovedAppsIterator = wcApprovedAppsKeys.iterator();
    Iterator tcPendingAppsIterator = tcPendingAppsKeys.iterator();
    Iterator wcPendingAppsIterator = wcPendingAppsKeys.iterator();

        if(mliFlag.equals("W"))
                {
                    Log.log(5, "ApplicationProcessor", "checkDuplicatePath", "Entering if flag is W");
                    String mliID = null;
                    ArrayList approvedApps = null;
                    for(ArrayList pendingApps = null; tcPendingAppsIterator.hasNext(); pendingApps = null)
                    {
                        ArrayList tcDuplicateCriteriaObjTemp = new ArrayList();
                        Log.log(5, "ApplicationProcessor", "checkDuplicatePath", "Entering pending interator..");
                        mliID = (String)tcPendingAppsIterator.next();
                        approvedApps = (ArrayList)tcApprovedApplications.get(mliID);
                        if(approvedApps == null)
                        {
                            approvedApps = new ArrayList();
                            pendingApps = (ArrayList)tcPendingApplications.get(mliID);
                            tcDuplicateCriteriaObjTemp = checkDuplicateApplications(approvedApps, pendingApps);
                        } else
                        {
                            pendingApps = (ArrayList)tcPendingApplications.get(mliID);
                            tcDuplicateCriteriaObjTemp = checkDuplicateApplications(approvedApps, pendingApps);
                        }
                        for(int i = 0; i < tcDuplicateCriteriaObjTemp.size(); i++)
                        {
                            DuplicateApplication tcDupApplication = (DuplicateApplication)tcDuplicateCriteriaObjTemp.get(i);
                            tcDuplicateCriteriaObj.add(tcDupApplication);
                        }

                        mliID = null;
                        approvedApps = null;
                    }

                    while(wcPendingAppsIterator.hasNext()) 
                    {
                        Log.log(5, "ApplicationProcessor", "checkDuplicatePath", "Entering wc pending interator..");
                        ArrayList wcDuplicateCriteriaObjTemp = new ArrayList();
                        mliID = (String)wcPendingAppsIterator.next();
                        approvedApps = (ArrayList)wcApprovedApplications.get(mliID);
                        ArrayList pendingApps;
                        if(approvedApps == null)
                        {
                            approvedApps = new ArrayList();
                            pendingApps = (ArrayList)wcPendingApplications.get(mliID);
                            wcDuplicateCriteriaObjTemp = checkDuplicateApplications(approvedApps, pendingApps);
                        } else
                        {
                            pendingApps = (ArrayList)wcPendingApplications.get(mliID);
                            wcDuplicateCriteriaObjTemp = checkDuplicateApplications(approvedApps, pendingApps);
                        }
                        for(int j = 0; j < wcDuplicateCriteriaObjTemp.size(); j++)
                        {
                            DuplicateApplication wcDupApplication = (DuplicateApplication)wcDuplicateCriteriaObjTemp.get(j);
                            wcDuplicateCriteriaObj.add(wcDupApplication);
                        }

                        mliID = null;
                        approvedApps = null;
                        pendingApps = null;
                    }
                    duplicateCriteriaObj.add(tcDuplicateCriteriaObj);
                    duplicateCriteriaObj.add(wcDuplicateCriteriaObj);
                } else
                if(mliFlag.equals("A"))
                {
                    Log.log(4, "ApplicationProcessor", "checkDuplicatePath", "Entering if flag is A");
                    ArrayList tcDuplicateCriteriaObjtemp = new ArrayList();
                    ArrayList pendingApps = new ArrayList();
                    while(tcPendingAppsIterator.hasNext()) 
                    {
                        String key = (String)tcPendingAppsIterator.next();
                        ArrayList tempPendingApps = (ArrayList)tcPendingApplications.get(key);
                        for(int i = 0; i < tempPendingApps.size(); i++)
                        {
                            Application tempPending = (Application)tempPendingApps.get(i);
                            pendingApps.add(tempPending);
                        }

                    }
                    ArrayList approvedApps = new ArrayList();
                    tcApprovedAppsIterator = tcApprovedAppsKeys.iterator();
                    if(tcApprovedApplications.isEmpty())
                    {
                        approvedApps = new ArrayList();
                    } else
                    {
                        approvedApps = new ArrayList();
                        for(; tcApprovedAppsIterator.hasNext(); Log.log(5, "ApplicationProcessor", "checkDuplicate", (new StringBuilder()).append("Size :").append(tcDuplicateCriteriaObj.size()).toString()))
                        {
                            String key = (String)tcApprovedAppsIterator.next();
                            ArrayList approvedAppsTemp = (ArrayList)tcApprovedApplications.get(key);
                            for(int j = 0; j < approvedAppsTemp.size(); j++)
                            {
                                Application tcApplicationTemp = (Application)approvedAppsTemp.get(j);
                                approvedApps.add(tcApplicationTemp);
                            }

                        }

                    }
                    tcDuplicateCriteriaObjtemp = checkDuplicateApplications(approvedApps, pendingApps);
                    for(int m = 0; m < tcDuplicateCriteriaObjtemp.size(); m++)
                    {
                        DuplicateApplication tcDupApplication = (DuplicateApplication)tcDuplicateCriteriaObjtemp.get(m);
                        tcDuplicateCriteriaObj.add(tcDupApplication);
                    }

                    while(wcPendingAppsIterator.hasNext()) 
                    {
                        ArrayList wcDuplicateCriteriaObjtemp = new ArrayList();
                        String key = (String)wcPendingAppsIterator.next();
                        Log.log(5, "ApplicationProcessor", "checkDuplicatePath", (new StringBuilder()).append("key 1:").append(key).toString());
                        Log.log(5, "ApplicationProcessor", "checkDuplicatePath", "Entering wc pending interator..");
                        pendingApps = (ArrayList)wcPendingApplications.get(key);
                        Log.log(5, "ApplicationProcessor", "checkDuplicatePath", (new StringBuilder()).append("pendingApps size :").append(pendingApps.size()).toString());
                        wcApprovedAppsIterator = wcApprovedAppsKeys.iterator();
                        if(wcApprovedApplications.isEmpty())
                        {
                            Log.log(5, "ApplicationProcessor", "checkDuplicatePath", "approved empty..");
                            approvedApps = new ArrayList();
                            wcDuplicateCriteriaObjtemp = checkDuplicateApplications(approvedApps, pendingApps);
                        } else
                        {
                            approvedApps = new ArrayList();
                            Log.log(5, "ApplicationProcessor", "checkDuplicatePath", "approved not empty..");
                            while(wcApprovedAppsIterator.hasNext()) 
                            {
                                key = (String)wcApprovedAppsIterator.next();
                                Log.log(5, "ApplicationProcessor", "checkDuplicatePath", (new StringBuilder()).append("key :").append(key).toString());
                                Log.log(5, "ApplicationProcessor", "checkDuplicatePath", "wcApprovedAppsIterator..");
                                ArrayList approvedAppsTemp = (ArrayList)wcApprovedApplications.get(key);
                                for(int i = 0; i < approvedAppsTemp.size(); i++)
                                {
                                    Application wcApplicationtemp = (Application)approvedAppsTemp.get(i);
                                    approvedApps.add(wcApplicationtemp);
                                }

                            }
                            wcDuplicateCriteriaObjtemp = checkDuplicateApplications(approvedApps, pendingApps);
                        }
                        for(int k = 0; k < wcDuplicateCriteriaObjtemp.size(); k++)
                        {
                            DuplicateApplication dupApplication = (DuplicateApplication)wcDuplicateCriteriaObjtemp.get(k);
                            wcDuplicateCriteriaObj.add(dupApplication);
                        }

                    }
                    duplicateCriteriaObj.add(tcDuplicateCriteriaObj);
                    duplicateCriteriaObj.add(wcDuplicateCriteriaObj);
                }
                appDAO = null;
                approvedPendingApplications = null;
                tcApprovedApplications = null;
                wcApprovedApplications = null;
                tcPendingApplications = null;
                wcPendingApplications = null;
                tcApprovedAppsKeys = null;
                wcApprovedAppsKeys = null;
                tcPendingAppsKeys = null;
                wcPendingAppsKeys = null;
                tcApprovedAppsIterator = null;
                wcApprovedAppsIterator = null;
                tcPendingAppsIterator = null;
                wcPendingAppsIterator = null;
                Log.log(4, "ApplicationProcessor", "checkDuplicate", "Exited");
                return duplicateCriteriaObj;
  }

  public ArrayList checkDuplicate(String mliFlag) throws DatabaseException
  {
    Log.log(4, "ApplicationProcessor", "checkDuplicate", "Entered");

        ArrayList duplicateCriteriaObj = new ArrayList();
                ArrayList tcDuplicateCriteriaObj = new ArrayList();
                ArrayList wcDuplicateCriteriaObj = new ArrayList();
                ApplicationDAO appDAO = new ApplicationDAO();
                HashMap approvedPendingApplications = appDAO.checkDuplicate();
                HashMap tcApprovedApplications = (HashMap)approvedPendingApplications.get("tcApproved");
                HashMap wcApprovedApplications = (HashMap)approvedPendingApplications.get("wcApproved");
                HashMap tcPendingApplications = (HashMap)approvedPendingApplications.get("tcPending");
                HashMap wcPendingApplications = (HashMap)approvedPendingApplications.get("wcPending");
                Set tcApprovedAppsKeys = tcApprovedApplications.keySet();
                Set wcApprovedAppsKeys = wcApprovedApplications.keySet();
                Set tcPendingAppsKeys = tcPendingApplications.keySet();
                Set wcPendingAppsKeys = wcPendingApplications.keySet();
                Iterator tcApprovedAppsIterator = tcApprovedAppsKeys.iterator();
                Iterator wcApprovedAppsIterator = wcApprovedAppsKeys.iterator();
                Iterator tcPendingAppsIterator = tcPendingAppsKeys.iterator();
                Iterator wcPendingAppsIterator = wcPendingAppsKeys.iterator();

    if (mliFlag.equals("W"))
    {
      Log.log(5, "ApplicationProcessor", "checkDuplicate", "Entering if flag is W");

      String mliID = null;
      ArrayList approvedApps = null;
      ArrayList pendingApps = null;

      while (tcPendingAppsIterator.hasNext())
      {
        ArrayList tcDuplicateCriteriaObjTemp = new ArrayList();
        Log.log(5, "ApplicationProcessor", "checkDuplicate", "Entering pending interator..");

        mliID = (String)tcPendingAppsIterator.next();
        approvedApps = (ArrayList)tcApprovedApplications.get(mliID);

        if (approvedApps == null)
        {
          approvedApps = new ArrayList();
          pendingApps = (ArrayList)tcPendingApplications.get(mliID);
          tcDuplicateCriteriaObjTemp = checkDuplicateApplications(approvedApps, pendingApps);
        }
        else
        {
          pendingApps = (ArrayList)tcPendingApplications.get(mliID);
          tcDuplicateCriteriaObjTemp = checkDuplicateApplications(approvedApps, pendingApps);
        }

        for (int i = 0; i < tcDuplicateCriteriaObjTemp.size(); i++)
        {
          DuplicateApplication tcDupApplication = (DuplicateApplication)tcDuplicateCriteriaObjTemp.get(i);
          tcDuplicateCriteriaObj.add(tcDupApplication);
        }

        mliID = null;
        approvedApps = null;
        pendingApps = null;
      }

      while (wcPendingAppsIterator.hasNext())
      {
        Log.log(5, "ApplicationProcessor", "checkDuplicate", "Entering wc pending interator..");

        ArrayList wcDuplicateCriteriaObjTemp = new ArrayList();

        mliID = (String)wcPendingAppsIterator.next();
        approvedApps = (ArrayList)wcApprovedApplications.get(mliID);

        if (approvedApps == null)
        {
          approvedApps = new ArrayList();
          pendingApps = (ArrayList)wcPendingApplications.get(mliID);
          wcDuplicateCriteriaObjTemp = checkDuplicateApplications(approvedApps, pendingApps);
        }
        else
        {
          pendingApps = (ArrayList)wcPendingApplications.get(mliID);
          wcDuplicateCriteriaObjTemp = checkDuplicateApplications(approvedApps, pendingApps);
        }

        for (int j = 0; j < wcDuplicateCriteriaObjTemp.size(); j++)
        {
          DuplicateApplication wcDupApplication = (DuplicateApplication)wcDuplicateCriteriaObjTemp.get(j);
          wcDuplicateCriteriaObj.add(wcDupApplication);
        }

        mliID = null;
        approvedApps = null;
        pendingApps = null;
      }

      duplicateCriteriaObj.add(tcDuplicateCriteriaObj);
      duplicateCriteriaObj.add(wcDuplicateCriteriaObj);
    }
    else if (mliFlag.equals("A"))
    {
      Log.log(4, "ApplicationProcessor", "checkDuplicate", "Entering if flag is A");

      ArrayList tcDuplicateCriteriaObjtemp = new ArrayList();
      ArrayList pendingApps = new ArrayList();
      ArrayList tempPendingApps;
   //   int i;
        while(tcPendingAppsIterator.hasNext()) 
                   {
                       String key = (String)tcPendingAppsIterator.next();
                       tempPendingApps = (ArrayList)tcPendingApplications.get(key);
                       for(int i = 0; i < tempPendingApps.size(); i++)
                       {
                           Application tempPending = (Application)tempPendingApps.get(i);
                           pendingApps.add(tempPending);
                       }

                   }

      ArrayList approvedApps = new ArrayList();
      tcApprovedAppsIterator = tcApprovedAppsKeys.iterator();
      if (tcApprovedApplications.isEmpty())
      {
        approvedApps = new ArrayList();
      }
      else
      {
        approvedApps = new ArrayList();

        while (tcApprovedAppsIterator.hasNext())
        {
          String key = (String)tcApprovedAppsIterator.next();
          ArrayList approvedAppsTemp = (ArrayList)tcApprovedApplications.get(key);
          for (int j = 0; j < approvedAppsTemp.size(); j++)
          {
            Application tcApplicationTemp = (Application)approvedAppsTemp.get(j);
            approvedApps.add(tcApplicationTemp);
          }

          Log.log(5, "ApplicationProcessor", "checkDuplicate", "Size :" + tcDuplicateCriteriaObj.size());
        }
      }
      tcDuplicateCriteriaObjtemp = checkDuplicateApplications(approvedApps, pendingApps);

      for (int m = 0; m < tcDuplicateCriteriaObjtemp.size(); m++)
      {
        DuplicateApplication tcDupApplication = (DuplicateApplication)tcDuplicateCriteriaObjtemp.get(m);
        tcDuplicateCriteriaObj.add(tcDupApplication);
      }
      ArrayList wcDuplicateCriteriaObjtemp;
        while(wcPendingAppsIterator.hasNext()) 
                   {
                       wcDuplicateCriteriaObjtemp = new ArrayList();
                       String key = (String)wcPendingAppsIterator.next();
                       Log.log(5, "ApplicationProcessor", "checkDuplicate", (new StringBuilder()).append("key 1:").append(key).toString());
                       Log.log(5, "ApplicationProcessor", "checkDuplicate", "Entering wc pending interator..");
                       pendingApps = (ArrayList)wcPendingApplications.get(key);
                       Log.log(5, "ApplicationProcessor", "checkDuplicate", (new StringBuilder()).append("pendingApps size :").append(pendingApps.size()).toString());
                       wcApprovedAppsIterator = wcApprovedAppsKeys.iterator();
                       if(wcApprovedApplications.isEmpty())
                       {
                           Log.log(5, "ApplicationProcessor", "checkDuplicate", "approved empty..");
                           approvedApps = new ArrayList();
                           wcDuplicateCriteriaObjtemp = checkDuplicateApplications(approvedApps, pendingApps);
                       } else
                       {
                           approvedApps = new ArrayList();
                           Log.log(5, "ApplicationProcessor", "checkDuplicate", "approved not empty..");
                           while(wcApprovedAppsIterator.hasNext()) 
                           {
                               key = (String)wcApprovedAppsIterator.next();
                               Log.log(5, "ApplicationProcessor", "checkDuplicate", (new StringBuilder()).append("key :").append(key).toString());
                               Log.log(5, "ApplicationProcessor", "checkDuplicate", "wcApprovedAppsIterator..");
                               ArrayList approvedAppsTemp = (ArrayList)wcApprovedApplications.get(key);
                               for(int i = 0; i < approvedAppsTemp.size(); i++)
                               {
                                   Application wcApplicationtemp = (Application)approvedAppsTemp.get(i);
                                   approvedApps.add(wcApplicationtemp);
                               }

                           }
                           wcDuplicateCriteriaObjtemp = checkDuplicateApplications(approvedApps, pendingApps);
                       }
                       for(int k = 0; k < wcDuplicateCriteriaObjtemp.size(); k++)
                       {
                           DuplicateApplication dupApplication = (DuplicateApplication)wcDuplicateCriteriaObjtemp.get(k);
                           wcDuplicateCriteriaObj.add(dupApplication);
                       }

                   }
      duplicateCriteriaObj.add(tcDuplicateCriteriaObj);
      duplicateCriteriaObj.add(wcDuplicateCriteriaObj);
    }

    appDAO = null;
    approvedPendingApplications = null;

    tcApprovedApplications = null;
    wcApprovedApplications = null;

    tcPendingApplications = null;
    wcPendingApplications = null;

    tcApprovedAppsKeys = null;
    wcApprovedAppsKeys = null;

    tcPendingAppsKeys = null;
    wcPendingAppsKeys = null;

    tcApprovedAppsIterator = null;
    wcApprovedAppsIterator = null;

    tcPendingAppsIterator = null;
    wcPendingAppsIterator = null;

    Log.log(4, "ApplicationProcessor", "checkDuplicate", "Exited");

    return duplicateCriteriaObj;
  }

  private ArrayList checkDuplicateApplications(ArrayList approvedApps, ArrayList pendingApps)
    throws DatabaseException
  {
    Log.log(4, "ApplicationProcessor", "checkDuplicateApplications", "Entered ");
    DuplicateApplication duplicateApplication = new DuplicateApplication();
    DuplicateCriteria duplicateCriteria = new DuplicateCriteria();
    DuplicateCondition duplicateCondition = new DuplicateCondition();
    ArrayList duplicateApp = new ArrayList();
    int approvedAppSize = approvedApps.size();
    int pendingAppSize = pendingApps.size();
    ArrayList duplicateConditionList = null;
    boolean duplicateApps = false;

    ApplicationDAO applicationDAO = new ApplicationDAO();

    for (int i = 0; i < pendingAppSize; i++)
    {
      duplicateApps = false;

      duplicateApplication = new DuplicateApplication();
      duplicateCriteria = new DuplicateCriteria();

      duplicateConditionList = new ArrayList();
      duplicateApplication.setDuplicateCondition(duplicateConditionList);
      duplicateApplication.setDuplicateCriteria(duplicateCriteria);
      Application pendingApp = (Application)pendingApps.get(i);

      if (Log.isDebugEnabled())
      {
        Log.log(5, "ApplicationProcessor", "checkDuplicateApplications", "before checking with Approved Applications");
        Log.log(5, "ApplicationProcessor", "checkDuplicateApplications", "pending " + i + " app ref no " + pendingApp.getAppRefNo());
      }

      for (int j = 0; j < approvedAppSize; j++)
      {
        duplicateApps = false;
        boolean regNo = false;
        boolean unitName = false;
        boolean firstName = false;
        boolean middleName = false;
        boolean lastName = false;
        boolean address = false;

        boolean itpan = false;
        boolean loanType1 = false;

        Application approvedApp = (Application)approvedApps.get(j);
        if (Log.isDebugEnabled())
        {
          Log.log(5, "ApplicationProcessor", "checkDuplicateApplications", "approved " + j + " app ref no " + approvedApp.getAppRefNo());
        }
        String aState = approvedApp.getBorrowerDetails().getSsiDetails().getState();
        String pState = pendingApp.getBorrowerDetails().getSsiDetails().getState();
        String aDistrict = approvedApp.getBorrowerDetails().getSsiDetails().getDistrict();
        String pDistrict = pendingApp.getBorrowerDetails().getSsiDetails().getDistrict();
        String aLoanType = approvedApp.getLoanType();
        String pLoanType = pendingApp.getLoanType();

        if ((aState.equalsIgnoreCase(pState)) && (aDistrict.equalsIgnoreCase(pDistrict)))
        {
          String aSsiRegNo = approvedApp.getBorrowerDetails().getSsiDetails().getRegNo();
          String pSsiRegNo = pendingApp.getBorrowerDetails().getSsiDetails().getRegNo();
          if ((aSsiRegNo != null) && (!aSsiRegNo.equals("")) && (pSsiRegNo != null) && (!pSsiRegNo.equals("")) && (!aSsiRegNo.equals("0")) && (!pSsiRegNo.equals("0")))
          {
            if (aSsiRegNo.equalsIgnoreCase(pSsiRegNo))
            {
              duplicateConditionList = duplicateApplication.getDuplicateCondition();
              duplicateCondition = new DuplicateCondition();
              duplicateCondition.setPrevLoanType(aLoanType);
              duplicateCondition.setexistLoanType(pLoanType);
              duplicateCondition.setExistingValue(aSsiRegNo);
              duplicateCondition.setNewValue(pSsiRegNo);

              duplicateCondition.setConditionName("SSI Reg No");
              duplicateConditionList.add(duplicateCondition);
              duplicateApplication.setDuplicateCondition(duplicateConditionList);
              duplicateCriteria = duplicateApplication.getDuplicateCriteria();
              duplicateCriteria.setSsiRegNo(true);
              duplicateApplication.setDuplicateCriteria(duplicateCriteria);

              regNo = true;
            }
            else
            {
              regNo = false;
            }
          }
          else
          {
            regNo = true;
          }
          String aSsiUnitName = approvedApp.getBorrowerDetails().getSsiDetails().getSsiName();
          String pSsiUnitName = pendingApp.getBorrowerDetails().getSsiDetails().getSsiName();
          String approvedSsiUnitName = aSsiUnitName.replaceAll(" ", "");
          String pendingSsiUnitName = pSsiUnitName.replaceAll(" ", "");
          if (approvedSsiUnitName.equalsIgnoreCase(pendingSsiUnitName))
          {
            duplicateConditionList = duplicateApplication.getDuplicateCondition();
            duplicateCondition = new DuplicateCondition();
            duplicateCondition.setPrevLoanType(aLoanType);
            duplicateCondition.setexistLoanType(pLoanType);
            duplicateCondition.setExistingValue(aSsiUnitName);
            duplicateCondition.setNewValue(pSsiUnitName);

            duplicateCondition.setConditionName("Unit Name");
            duplicateConditionList.add(duplicateCondition);
            duplicateApplication.setDuplicateCondition(duplicateConditionList);
            duplicateCriteria = duplicateApplication.getDuplicateCriteria();
            duplicateCriteria.setUnitName(true);
            duplicateApplication.setDuplicateCriteria(duplicateCriteria);

            unitName = true;
          }
          if (Log.isDebugEnabled())
          {
            Log.log(5, "ApplicationProcessor", "checkDuplicateApplications", "Approved First Name :" + approvedApp.getBorrowerDetails().getSsiDetails().getCpFirstName());
          }
          String aFirstName = approvedApp.getBorrowerDetails().getSsiDetails().getCpFirstName();
          String pFirstName = pendingApp.getBorrowerDetails().getSsiDetails().getCpFirstName();
          if (Log.isDebugEnabled())
          {
            Log.log(5, "ApplicationProcessor", "checkDuplicateApplications", "PEnding First Name :" + pFirstName);
          }
          String approvedFirstName = aFirstName.replaceAll(" ", "");
          String pendingFirstName = pFirstName.replaceAll(" ", "");
          if (approvedFirstName.equalsIgnoreCase(pendingFirstName))
          {
            duplicateConditionList = duplicateApplication.getDuplicateCondition();
            duplicateCondition = new DuplicateCondition();
            duplicateCondition.setConditionName("Promoter First Name");
            duplicateCondition.setExistingValue(aFirstName);
            duplicateCondition.setNewValue(pFirstName);

            duplicateConditionList.add(duplicateCondition);
            duplicateApplication.setDuplicateCondition(duplicateConditionList);
            duplicateCriteria = duplicateApplication.getDuplicateCriteria();
            duplicateCriteria.setPFirstName(true);
            duplicateApplication.setDuplicateCriteria(duplicateCriteria);

            firstName = true;
          }
          else
          {
            firstName = false;
          }
          String aMiddleName = approvedApp.getBorrowerDetails().getSsiDetails().getCpMiddleName();
          if (Log.isDebugEnabled())
          {
            Log.log(5, "ApplicationProcessor", "checkDuplicateApplications", "Approved Middle Name :" + aMiddleName);
          }
          String pMiddleName = pendingApp.getBorrowerDetails().getSsiDetails().getCpMiddleName();
          if (Log.isDebugEnabled())
          {
            Log.log(5, "ApplicationProcessor", "checkDuplicateApplications", "Approved Middle name :" + aMiddleName + " for app ref no" + approvedApp.getCgpan());
            Log.log(5, "ApplicationProcessor", "checkDuplicateApplications", "Pending Middle name:" + pMiddleName + " for cgpan" + pendingApp.getAppRefNo());
          }
          if ((aMiddleName != null) && (!aMiddleName.equals("")) && (pMiddleName != null) && (!pMiddleName.equals("")))
          {
            String approvedMiddleName = aMiddleName.replaceAll(" ", "");
            String pendingMiddleName = pMiddleName.replaceAll(" ", "");
            if (approvedMiddleName.equalsIgnoreCase(pendingMiddleName))
            {
              if (Log.isDebugEnabled())
              {
                Log.log(5, "ApplicationProcessor", "checkDuplicateApplications", "Approved Middle name :" + approvedMiddleName + " for app ref no" + approvedApp.getCgpan());
                Log.log(5, "ApplicationProcessor", "checkDuplicateApplications", "Pending Middle name:" + pendingMiddleName + " for cgpan" + pendingApp.getAppRefNo());
              }
              duplicateConditionList = duplicateApplication.getDuplicateCondition();
              duplicateCondition = new DuplicateCondition();
              duplicateCondition.setConditionName("Promoter Middle Name");
              duplicateCondition.setExistingValue(aMiddleName);
              duplicateCondition.setNewValue(pMiddleName);

              duplicateConditionList.add(duplicateCondition);
              duplicateApplication.setDuplicateCondition(duplicateConditionList);
              duplicateCriteria = duplicateApplication.getDuplicateCriteria();
              duplicateCriteria.setPMiddleName(true);
              duplicateApplication.setDuplicateCriteria(duplicateCriteria);

              middleName = true;
            }
            else
            {
              middleName = false;
            }
          }
          else
          {
            middleName = true;
          }
          String aLastName = approvedApp.getBorrowerDetails().getSsiDetails().getCpLastName();

          String pLastName = pendingApp.getBorrowerDetails().getSsiDetails().getCpLastName();
          String approvedLastName = aLastName.replaceAll(" ", "");
          String pendingLastName = pLastName.replaceAll(" ", "");
          if (approvedLastName.equalsIgnoreCase(pendingLastName))
          {
            if (Log.isDebugEnabled())
            {
              Log.log(5, "ApplicationProcessor", "checkDuplicateApplications", "Approved Last name :" + approvedLastName + "for app ref no" + approvedApp.getCgpan());
              Log.log(5, "ApplicationProcessor", "checkDuplicateApplications", "Pending last name:" + pendingLastName + "for cgpan" + pendingApp.getAppRefNo());
            }
            duplicateConditionList = duplicateApplication.getDuplicateCondition();
            duplicateCondition = new DuplicateCondition();

            duplicateCondition.setConditionName("Promoter Last Name");
            duplicateCondition.setExistingValue(aLastName);
            duplicateCondition.setNewValue(pLastName);

            duplicateConditionList.add(duplicateCondition);
            duplicateApplication.setDuplicateCondition(duplicateConditionList);
            duplicateCriteria = duplicateApplication.getDuplicateCriteria();
            duplicateCriteria.setPLastName(true);
            duplicateApplication.setDuplicateCriteria(duplicateCriteria);

            lastName = true;
          }
          else
          {
            lastName = false;
          }
          String aAddress = approvedApp.getBorrowerDetails().getSsiDetails().getAddress();
          if (Log.isDebugEnabled())
          {
            Log.log(5, "ApplicationProcessor", "checkDuplicateApplications", "Approved Address :" + aAddress);
          }
          String pAddress = pendingApp.getBorrowerDetails().getSsiDetails().getAddress();
          if (Log.isDebugEnabled())
          {
            Log.log(5, "ApplicationProcessor", "checkDuplicateApplications", "Approved Address :" + pAddress);
          }
          String approvedAddress = aAddress.replaceAll(" ", "");
          String pendingAddress = pAddress.replaceAll(" ", "");
          if (approvedAddress.equalsIgnoreCase(pendingAddress))
          {
            duplicateConditionList = duplicateApplication.getDuplicateCondition();
            duplicateCondition = new DuplicateCondition();
            duplicateCondition.setConditionName("SSI Address");
            duplicateCondition.setExistingValue(aAddress);
            duplicateCondition.setNewValue(pAddress);

            duplicateConditionList.add(duplicateCondition);
            duplicateApplication.setDuplicateCondition(duplicateConditionList);
            duplicateCriteria = duplicateApplication.getDuplicateCriteria();
            duplicateCriteria.setAddress(true);
            duplicateApplication.setDuplicateCriteria(duplicateCriteria);

            address = true;
          }
          else
          {
            address = false;
          }
          String aItpan = approvedApp.getBorrowerDetails().getSsiDetails().getCpITPAN();
          String pItpan = pendingApp.getBorrowerDetails().getSsiDetails().getCpITPAN();
          if ((aItpan != null) && (!aItpan.equals("")) && (pItpan != null) && (!pItpan.equals("")))
          {
            char[] array1 = aItpan.toCharArray();
            char[] array2 = pItpan.toCharArray();
            if ((array1.length == 10) && (array2.length == 10) && (Character.isLetter(array1[0])) && (Character.isLetter(array1[1])) && (Character.isLetter(array1[2])) && (Character.isLetter(array1[3])) && (Character.isLetter(array1[4])) && (Character.isLetter(array2[0])) && (Character.isLetter(array2[1])) && (Character.isLetter(array2[2])) && (Character.isLetter(array2[3])) && (Character.isLetter(array2[4])) && (Character.isDigit(array1[5])) && (Character.isDigit(array1[6])) && (Character.isDigit(array1[7])) && (Character.isDigit(array1[8])) && (Character.isDigit(array2[5])) && (Character.isDigit(array2[6])) && (Character.isDigit(array2[7])) && (Character.isDigit(array2[8])) && (Character.isLetter(array1[9])) && (Character.isLetter(array2[9])))
            {
              if (aItpan.equalsIgnoreCase(pItpan))
              {
                duplicateConditionList = duplicateApplication.getDuplicateCondition();
                duplicateCondition = new DuplicateCondition();
                duplicateCondition.setConditionName("ITPAN Of Chief Promoter");
                duplicateCondition.setExistingValue(aItpan);
                duplicateCondition.setNewValue(pItpan);

                duplicateConditionList.add(duplicateCondition);
                duplicateApplication.setDuplicateCondition(duplicateConditionList);
                duplicateCriteria = duplicateApplication.getDuplicateCriteria();
                duplicateCriteria.setItPAN(true);
                duplicateApplication.setDuplicateCriteria(duplicateCriteria);

                itpan = true;
              }
              else
              {
                itpan = false;
              }

            }
            else if (aItpan.equalsIgnoreCase(pItpan))
            {
              duplicateConditionList = duplicateApplication.getDuplicateCondition();
              duplicateCondition = new DuplicateCondition();
              duplicateCondition.setConditionName("ITPAN Of Chief Promoter");
              duplicateCondition.setExistingValue(aItpan);
              duplicateCondition.setNewValue(pItpan);

              duplicateConditionList.add(duplicateCondition);
              duplicateApplication.setDuplicateCondition(duplicateConditionList);
              duplicateCriteria = duplicateApplication.getDuplicateCriteria();
              duplicateCriteria.setItPAN(true);
              duplicateApplication.setDuplicateCriteria(duplicateCriteria);

              itpan = true;
            }

          }
          else
          {
            itpan = true;
          }

        }

        int temp = 0;
        if (regNo) temp += 1;
        if (itpan) temp += 1;

        if (firstName) temp += 1;

        if (lastName) temp += 1;

        if ((unitName) && (address))
        {
          temp += 3;
        }
        else
        {
          if (unitName) temp += 2;
          if (address) temp += 2;

        }

        if (temp >= 4)
        {
          Log.log(5, "ApplicationProcessor", "checkDuplicateApplications", "pending all matching : " + pendingApp.getAppRefNo());
          duplicateApps = true;
        }
        else
        {
          Log.log(5, "ApplicationProcessor", "checkDuplicateApplications", "pending all not matching : " + pendingApp.getAppRefNo());

          duplicateApps = false;
        }

        if (duplicateApps)
        {
          duplicateApplication.setOldCgpan(approvedApp.getCgpan());

          duplicateApplication.setNewAppRefNo(pendingApp.getAppRefNo());

          duplicateApplication.setPrevLoanType(approvedApp.getLoanType());

          duplicateApplication.setExistLoanType(pendingApp.getLoanType());
          duplicateApplication.setBorrowerId(approvedApp.getBorrowerDetails().getSsiDetails().getCgbid());
          duplicateApp.add(duplicateApplication);
          Log.log(5, "ApplicationProcessor", "checkDuplicateApplications", "pending " + i + " duplicate with approved " + j + " cgpan " + approvedApp.getCgpan());
          Log.log(5, "ApplicationProcessor", "checkDuplicateApplications", "duplicateAppList size" + duplicateConditionList.size());
          break;
        }

        duplicateConditionList.clear();
      }

      if (!duplicateApps)
      {
        duplicateApplication = new DuplicateApplication();
        duplicateCriteria = new DuplicateCriteria();

        duplicateConditionList = new ArrayList();
        duplicateApplication.setDuplicateCondition(duplicateConditionList);
        duplicateApplication.setDuplicateCriteria(duplicateCriteria);
        for (int k = 0; k < pendingAppSize; k++)
        {
          boolean regNo = false;
          boolean unitName = false;
          boolean firstName = false;
          boolean middleName = false;
          boolean lastName = false;
          boolean address = false;

          boolean itpan = false;

          if (k != i)
          {
            Application remPendingApp = (Application)pendingApps.get(k);
            if (Log.isDebugEnabled())
            {
              Log.log(5, "ApplicationProcessor", "checkDuplicateApplications", "pending " + i + " checking with pending " + k + " app ref no " + remPendingApp.getAppRefNo());
            }
            String rState = remPendingApp.getBorrowerDetails().getSsiDetails().getState();
            String pState = pendingApp.getBorrowerDetails().getSsiDetails().getState();
            String rDistrict = remPendingApp.getBorrowerDetails().getSsiDetails().getDistrict();
            String pDistrict = pendingApp.getBorrowerDetails().getSsiDetails().getDistrict();
            if ((rState.equalsIgnoreCase(pState)) && (rDistrict.equalsIgnoreCase(pDistrict)))
            {
              if (Log.isDebugEnabled())
              {
                Log.log(5, "ApplicationProcessor", "checkDuplicateApplications", "state and district are same for :" + remPendingApp.getAppRefNo() + "and " + pendingApp.getAppRefNo());
              }
              String rSsiRegNo = remPendingApp.getBorrowerDetails().getSsiDetails().getRegNo();
              String pSsiRegNo = pendingApp.getBorrowerDetails().getSsiDetails().getRegNo();
              if ((rSsiRegNo != null) && (!rSsiRegNo.equals("")) && (pSsiRegNo != null) && (!pSsiRegNo.equals("")) && (!rSsiRegNo.equals("0")) && (!pSsiRegNo.equals("0")))
              {
                if (rSsiRegNo.equalsIgnoreCase(pSsiRegNo))
                {
                  if (Log.isDebugEnabled())
                  {
                    Log.log(5, "ApplicationProcessor", "checkDuplicateApplications", "ssi reg no are same for :" + remPendingApp.getAppRefNo() + "and " + pendingApp.getAppRefNo());
                  }
                  duplicateConditionList = duplicateApplication.getDuplicateCondition();
                  duplicateCondition = new DuplicateCondition();
                  duplicateCondition.setExistingValue(rSsiRegNo);
                  duplicateCondition.setNewValue(pSsiRegNo);
                  duplicateCondition.setConditionName("SSI Reg No");
                  duplicateConditionList.add(duplicateCondition);
                  duplicateApplication.setDuplicateCondition(duplicateConditionList);
                  duplicateCriteria = duplicateApplication.getDuplicateCriteria();
                  duplicateCriteria.setSsiRegNo(true);
                  duplicateApplication.setDuplicateCriteria(duplicateCriteria);

                  regNo = true;
                }
                else {
                  regNo = false;
                }
              }
              else {
                regNo = true;
              }
              String rSsiUnitName = remPendingApp.getBorrowerDetails().getSsiDetails().getSsiName();
              String pSsiUnitName = pendingApp.getBorrowerDetails().getSsiDetails().getSsiName();
              String remPendingSsiUnitName = rSsiUnitName.replaceAll(" ", "");
              String pendingSsiUnitName = pSsiUnitName.replaceAll(" ", "");
              if (remPendingSsiUnitName.equalsIgnoreCase(pendingSsiUnitName))
              {
                if (Log.isDebugEnabled())
                {
                  Log.log(5, "ApplicationProcessor", "checkDuplicateApplications", "ssi unti name are same for :" + remPendingApp.getAppRefNo() + "and " + pendingApp.getAppRefNo());
                }
                duplicateConditionList = duplicateApplication.getDuplicateCondition();
                duplicateCondition = new DuplicateCondition();
                duplicateCondition.setExistingValue(rSsiUnitName);
                duplicateCondition.setNewValue(pSsiUnitName);
                duplicateCondition.setConditionName("Unit Name");
                duplicateConditionList.add(duplicateCondition);
                duplicateApplication.setDuplicateCondition(duplicateConditionList);
                duplicateCriteria = duplicateApplication.getDuplicateCriteria();
                duplicateCriteria.setUnitName(true);
                duplicateApplication.setDuplicateCriteria(duplicateCriteria);

                unitName = true;
              }
              else {
                unitName = false;
              }
              if (Log.isDebugEnabled())
              {
                Log.log(5, "ApplicationProcessor", "checkDuplicateApplications", "Rem Pending First Name :" + remPendingApp.getBorrowerDetails().getSsiDetails().getCpFirstName());
              }
              String rFirstName = remPendingApp.getBorrowerDetails().getSsiDetails().getCpFirstName();
              String pFirstName = pendingApp.getBorrowerDetails().getSsiDetails().getCpFirstName();
              if (Log.isDebugEnabled())
              {
                Log.log(5, "ApplicationProcessor", "checkDuplicateApplications", "PEnding First Name :" + pFirstName);
              }
              String remPendingFirstName = rFirstName.replaceAll(" ", "");
              String pendingFirstName = pFirstName.replaceAll(" ", "");
              if (remPendingFirstName.equalsIgnoreCase(pendingFirstName))
              {
                if (Log.isDebugEnabled())
                {
                  Log.log(5, "ApplicationProcessor", "checkDuplicateApplications", "promoter name are same for :" + remPendingApp.getAppRefNo() + "and " + pendingApp.getAppRefNo());
                }
                duplicateConditionList = duplicateApplication.getDuplicateCondition();
                duplicateCondition = new DuplicateCondition();
                duplicateCondition.setConditionName("Promoter First Name");
                duplicateCondition.setExistingValue(rFirstName);
                duplicateCondition.setNewValue(pFirstName);
                duplicateConditionList.add(duplicateCondition);
                duplicateApplication.setDuplicateCondition(duplicateConditionList);
                duplicateCriteria = duplicateApplication.getDuplicateCriteria();
                duplicateCriteria.setPFirstName(true);
                duplicateApplication.setDuplicateCriteria(duplicateCriteria);

                firstName = true;
              }
              else
              {
                firstName = false;
              }
              String rMiddleName = remPendingApp.getBorrowerDetails().getSsiDetails().getCpMiddleName();
              if (Log.isDebugEnabled())
              {
                Log.log(5, "ApplicationProcessor", "checkDuplicateApplications", "Rem Pending Middle Name :" + rMiddleName);
              }
              String pMiddleName = pendingApp.getBorrowerDetails().getSsiDetails().getCpMiddleName();
              if ((rMiddleName != null) && (pMiddleName != null))
              {
                String remPendingMiddleName = rMiddleName.replaceAll(" ", "");
                String pendingMiddleName = pMiddleName.replaceAll(" ", "");
                if (remPendingMiddleName.equalsIgnoreCase(pendingMiddleName))
                {
                  if (Log.isDebugEnabled())
                  {
                    Log.log(5, "ApplicationProcessor", "checkDuplicateApplications", "promoter middle name are same for :" + remPendingApp.getAppRefNo() + "and " + pendingApp.getAppRefNo());
                  }
                  duplicateConditionList = duplicateApplication.getDuplicateCondition();
                  duplicateCondition = new DuplicateCondition();
                  duplicateCondition.setConditionName("Promoter Middle Name");
                  duplicateCondition.setExistingValue(rMiddleName);
                  duplicateCondition.setNewValue(pMiddleName);
                  duplicateConditionList.add(duplicateCondition);
                  duplicateApplication.setDuplicateCondition(duplicateConditionList);
                  duplicateCriteria = duplicateApplication.getDuplicateCriteria();
                  duplicateCriteria.setPMiddleName(true);
                  duplicateApplication.setDuplicateCriteria(duplicateCriteria);

                  middleName = true;
                }
                else {
                  middleName = false;
                }
              }
              else {
                middleName = true;
              }
              String rLastName = remPendingApp.getBorrowerDetails().getSsiDetails().getCpLastName();
              String pLastName = pendingApp.getBorrowerDetails().getSsiDetails().getCpLastName();
              String remPendingLastName = rLastName.replaceAll(" ", "");
              String pendingLastName = pLastName.replaceAll(" ", "");
              if (remPendingLastName.equalsIgnoreCase(pendingLastName))
              {
                if (Log.isDebugEnabled())
                {
                  Log.log(5, "ApplicationProcessor", "checkDuplicateApplications", "promoter last name are same for :" + remPendingApp.getAppRefNo() + "and " + pendingApp.getAppRefNo());
                }
                duplicateConditionList = duplicateApplication.getDuplicateCondition();
                duplicateCondition = new DuplicateCondition();
                duplicateCondition.setConditionName("Promoter Last Name");
                duplicateCondition.setExistingValue(rLastName);
                duplicateCondition.setNewValue(pLastName);
                duplicateConditionList.add(duplicateCondition);
                duplicateApplication.setDuplicateCondition(duplicateConditionList);
                duplicateCriteria = duplicateApplication.getDuplicateCriteria();
                duplicateCriteria.setPLastName(true);
                duplicateApplication.setDuplicateCriteria(duplicateCriteria);

                lastName = true;
              }
              else {
                lastName = false;
              }
              String rAddress = remPendingApp.getBorrowerDetails().getSsiDetails().getAddress();
              if (Log.isDebugEnabled())
              {
                Log.log(5, "ApplicationProcessor", "checkDuplicateApplications", "Rem Pending  Address :" + rAddress);
              }
              String pAddress = pendingApp.getBorrowerDetails().getSsiDetails().getAddress();
              if (Log.isDebugEnabled())
              {
                Log.log(5, "ApplicationProcessor", "checkDuplicateApplications", "Pending Address :" + pAddress);
              }
              String remPendingAddress = rAddress.replaceAll(" ", "");
              String pendingAddress = pAddress.replaceAll(" ", "");
              if (remPendingAddress.equalsIgnoreCase(pendingAddress))
              {
                if (Log.isDebugEnabled())
                {
                  Log.log(5, "ApplicationProcessor", "checkDuplicateApplications", "promoter address are same for :" + remPendingApp.getAppRefNo() + "and " + pendingApp.getAppRefNo());
                }
                duplicateConditionList = duplicateApplication.getDuplicateCondition();
                duplicateCondition = new DuplicateCondition();
                duplicateCondition.setConditionName("SSI Address");
                duplicateCondition.setExistingValue(rAddress);
                duplicateCondition.setNewValue(pAddress);
                duplicateConditionList.add(duplicateCondition);
                duplicateApplication.setDuplicateCondition(duplicateConditionList);
                duplicateCriteria = duplicateApplication.getDuplicateCriteria();
                duplicateCriteria.setAddress(true);
                duplicateApplication.setDuplicateCriteria(duplicateCriteria);

                address = true;
              }
              else {
                address = false;
              }
              String aItpan = remPendingApp.getBorrowerDetails().getSsiDetails().getCpITPAN();
              String pItpan = pendingApp.getBorrowerDetails().getSsiDetails().getCpITPAN();

              if ((aItpan != null) && (!aItpan.equals("")) && (pItpan != null) && (!pItpan.equals("")))
              {
                if (Log.isDebugEnabled())
                {
                  Log.log(5, "ApplicationProcessor", "checkDuplicateApplications", "Approved itpan :" + aItpan);
                  Log.log(5, "ApplicationProcessor", "checkDuplicateApplications", "remaining Pending itpan :" + pItpan);
                }
                char[] array1 = aItpan.toCharArray();
                char[] array2 = pItpan.toCharArray();

                if ((array1.length == 10) && (array2.length == 10) && (Character.isLetter(array1[0])) && (Character.isLetter(array1[1])) && (Character.isLetter(array1[2])) && (Character.isLetter(array1[3])) && (Character.isLetter(array1[4])) && (Character.isLetter(array2[0])) && (Character.isLetter(array2[1])) && (Character.isLetter(array2[2])) && (Character.isLetter(array2[3])) && (Character.isLetter(array2[4])) && (Character.isDigit(array1[5])) && (Character.isDigit(array1[6])) && (Character.isDigit(array1[7])) && (Character.isDigit(array1[8])) && (Character.isDigit(array2[5])) && (Character.isDigit(array2[6])) && (Character.isDigit(array2[7])) && (Character.isDigit(array2[8])) && (Character.isLetter(array1[9])) && (Character.isLetter(array2[9])))
                {
                  if (Log.isDebugEnabled())
                  {
                    Log.log(5, "ApplicationProcessor", "checkDuplicateApplications", "valid format are same for :" + remPendingApp.getAppRefNo() + "and " + pendingApp.getAppRefNo());
                  }

                  if (aItpan.equalsIgnoreCase(pItpan))
                  {
                    duplicateConditionList = duplicateApplication.getDuplicateCondition();
                    duplicateCondition = new DuplicateCondition();
                    duplicateCondition.setConditionName("ITPAN Of Chief Promoter");
                    duplicateCondition.setExistingValue(aItpan);
                    duplicateCondition.setNewValue(pItpan);
                    duplicateConditionList.add(duplicateCondition);
                    duplicateApplication.setDuplicateCondition(duplicateConditionList);
                    duplicateCriteria = duplicateApplication.getDuplicateCriteria();
                    duplicateCriteria.setItPAN(true);
                    duplicateApplication.setDuplicateCriteria(duplicateCriteria);
                    itpan = true;
                  }
                  else {
                    itpan = false;
                  }
                }
                else {
                  if (Log.isDebugEnabled())
                  {
                    Log.log(5, "ApplicationProcessor", "checkDuplicateApplications", " not valid format are same for :" + remPendingApp.getAppRefNo() + "and " + pendingApp.getAppRefNo());
                  }
                  if (aItpan.equalsIgnoreCase(pItpan))
                  {
                    duplicateConditionList = duplicateApplication.getDuplicateCondition();
                    duplicateCondition = new DuplicateCondition();
                    duplicateCondition.setConditionName("ITPAN Of Chief Promoter");
                    duplicateCondition.setExistingValue(aItpan);
                    duplicateCondition.setNewValue(pItpan);
                    duplicateConditionList.add(duplicateCondition);
                    duplicateApplication.setDuplicateCondition(duplicateConditionList);
                    duplicateCriteria = duplicateApplication.getDuplicateCriteria();
                    duplicateCriteria.setItPAN(true);
                    duplicateApplication.setDuplicateCriteria(duplicateCriteria);
                    itpan = true;
                  }
                }

              }
              else
              {
                itpan = true;
              }

            }

            Log.log(5, "ApplicationProcessor", "checkDuplicateApplications", "state and district are not same for :" + remPendingApp.getAppRefNo() + "and " + pendingApp.getAppRefNo());
            int temp1 = 0;
            if (regNo) temp1 += 1;
            if (itpan) temp1 += 1;

            if (firstName) temp1 += 1;

            if (lastName) temp1 += 1;

            if ((unitName) && (address))
            {
              temp1 += 3;
            }
            else
            {
              if (unitName) temp1 += 2;
              if (address) temp1 += 2;

            }

            if (temp1 >= 4)
            {
              Log.log(5, "ApplicationProcessor", "checkDuplicateApplications", "pending  checking with pending  app ref no " + remPendingApp.getAppRefNo());
              duplicateApps = true;
            }
            else {
              Log.log(5, "ApplicationProcessor", "checkDuplicateApplications", "pending  checking with pending  app ref no not all matching" + remPendingApp.getAppRefNo());
              duplicateApps = false;
            }

            if (duplicateApps)
            {
              duplicateApplication.setOldCgpan(remPendingApp.getAppRefNo());
              duplicateApplication.setNewAppRefNo(pendingApp.getAppRefNo());
              duplicateApplication.setPrevLoanType(remPendingApp.getLoanType());

              duplicateApplication.setExistLoanType(pendingApp.getLoanType());

              Log.log(5, "ApplicationProcessor", "checkDuplicateApplications", "pending " + i + " duplicate with pending " + k + " appref no " + remPendingApp.getAppRefNo());
              duplicateApp.add(duplicateApplication);

              break;
            }

            duplicateConditionList.clear();
          }

        }

      }

    }

    Log.log(4, "ApplicationProcessor", "checkDuplicateApplications", "Exited ");

    return duplicateApp;
  }

  public void submitWcEnhancement(Application app, String createdBy)
    throws InvalidApplicationException, DatabaseException, MessageException
  {
    Log.log(4, "ApplicationProcessor", "submitWcEnhancement", "Entered");

    this.appDAO.storeWcEnhancement(app, createdBy);

    app = null;
    createdBy = null;

    Log.log(4, "ApplicationProcessor", "submitWcEnhancement", "Exited");
  }

  public String submitWcRenewal(Application app, String createdBy)
    throws InvalidApplicationException, DatabaseException, MessageException
  {
    Log.log(4, "ApplicationProcessor", "submitWcRenewal", "Entered");

    String appRefNo = this.appDAO.storeWcRenewal(app, createdBy);

    app = null;
    createdBy = null;

    Log.log(4, "ApplicationProcessor", "submitWcRenewal", "Exited");

    return appRefNo;
  }

  public ArrayList uploadApplication(ArrayList applications, String userId, String bankId, String zoneId, String branchId)
    throws InvalidApplicationException, DatabaseException, NoApplicationFoundException, MessageException
  {
    Log.log(4, "ApplicationProcessor", "uploadApplication", "Entered");
    ArrayList errorMessages = new ArrayList();
    if ((applications != null) && (applications.size() != 0))
    {
      Log.log(4, "ApplicationProcessor", "uploadApplication", "Application not null");

      boolean applicationValidVal = false;
      boolean errorMessageVal = false;
      int count = 0;
      ApplicationDAO applicationDAO = new ApplicationDAO();
      String errorMessage = "";

      int applicationSize = applications.size();
      for (int i = 0; i < applicationSize; i++)
      {
        Log.log(4, "ApplicationProcessor", "uploadApplication", "Each Application Loop");
        Application application = (Application)applications.get(i);

        errorMessageVal = false;

        if (application.getIsVerified())
        {
          SSIDetails ssiDetails = application.getBorrowerDetails().getSsiDetails();
          BorrowerDetails borrowerDetails = application.getBorrowerDetails();
          borrowerDetails.setSsiDetails(ssiDetails);
          MCGFDetails mcgfDetails = application.getMCGFDetails();
          application.setBorrowerDetails(borrowerDetails);
          application.setMCGFDetails(mcgfDetails);

          Log.log(4, "ApplicationProcessor", "uploadApplication", "Borrower Previously Covered :" + application.getBorrowerDetails().getPreviouslyCovered());

          if (application.getBorrowerDetails().getPreviouslyCovered().equals("Y"))
          {
            if ((application.getCgpanReference() != null) && (!application.getCgpanReference().equals("")))
            {
              String cgbid = "";
              String cgpan = application.getCgpanReference().toUpperCase();

              Log.log(4, "ApplicationProcessor", "uploadApplication", "cgpan:" + cgpan);
              ArrayList cgpans = this.appDAO.getAllCgpans();
              if (!cgpans.contains(cgpan))
              {
                errorMessageVal = true;
                if ((application.getMliRefNo() != null) && (!application.getMliRefNo().equals("")))
                {
                  errorMessage = "The CGPAN : " + cgpan + " is an invalid CGPAN " + "(" + "Bank Reference No. :" + application.getMliRefNo() + ")";
                }
                else {
                  errorMessage = "The CGPAN : " + cgpan + " is an invalid CGPAN ";
                }

                errorMessages.add(errorMessage);
              }
              else
              {
                ClaimsProcessor claimProcessor = new ClaimsProcessor();

                if ((cgpan != null) && (!cgpan.equals("")))
                {
                  cgbid = claimProcessor.getBorowwerForCGPAN(cgpan);

                  int claimCount = this.appDAO.getClaimCount(cgbid);
                  if (claimCount > 0)
                  {
                    errorMessageVal = true;
                    errorMessage = "Application cannot be filed by this borrower " + cgbid + " since Claim Application has been submitted";
                    errorMessages.add(errorMessage);
                  }

                }

              }

              try
              {
                this.appDAO.getAppForCgpan(bankId + zoneId + branchId, cgpan);
              }
              catch (DatabaseException databaseException) {
                errorMessageVal = true;
                errorMessage = "The CGPAN :" + cgpan + " does not belong the Member " + bankId + zoneId + branchId;
                errorMessages.add(errorMessage);
              }

              if (application.getAdditionalTC())
              {
                Application tcApplication = new Application();
                try
                {
                  tcApplication = this.appDAO.getAppForCgpan(null, cgpan);

                  if (!tcApplication.getLoanType().equals("TC"))
                  {
                    errorMessageVal = true;
                    errorMessage = "The CGPAN : " + cgpan + " cannot be applied for Additional Term Loan " + "(" + "Bank Reference No. :" + tcApplication.getMliRefNo() + ")";
                    errorMessages.add(errorMessage);
                  }
                  if ((tcApplication.getLoanType().equals("TC")) && (!tcApplication.getStatus().equals("EX")) && (!tcApplication.getStatus().equals("AP")))
                  {
                    errorMessageVal = true;
                    errorMessage = "The CGPAN : " + cgpan + " is not expired / approved " + "(" + "Bank Reference No. :" + tcApplication.getMliRefNo() + ")";
                    errorMessages.add(errorMessage);
                  }

                }
                catch (DatabaseException databaseException)
                {
                  errorMessageVal = true;
                  errorMessage = "The CGPAN :" + cgpan + " is not valid for Additional Term Loan ";
                  errorMessages.add(errorMessage);
                }
              }

              if (application.getWcRenewal())
              {
                Application wcRenewApplication = new Application();
                try
                {
                  wcRenewApplication = this.appDAO.getAppForCgpan(null, application.getCgpanReference());
                  if (!wcRenewApplication.getLoanType().equals("WC"))
                  {
                    errorMessageVal = true;
                    errorMessage = "The CGPAN : " + cgpan + " cannot be applied for Renewal of Cover " + "(" + "Bank Reference No. :" + wcRenewApplication.getMliRefNo() + ")";
                    errorMessages.add(errorMessage);
                  }

                  String renewcgpan = this.appDAO.checkRenewCgpan(application.getCgpanReference());
                  if (renewcgpan.equals("0"))
                  {
                    if (!wcRenewApplication.getStatus().equals("EX"))
                    {
                      errorMessageVal = true;
                      errorMessage = "The CGPAN : " + cgpan + " is not expired " + "(" + "Bank Reference No. :" + wcRenewApplication.getMliRefNo() + ")";
                      errorMessages.add(errorMessage);
                    }

                  }
                  else
                  {
                    Application tempWcRenewApplication = this.appDAO.getAppForCgpan(null, renewcgpan);
                    if (!tempWcRenewApplication.getStatus().equals("EX"))
                    {
                      errorMessageVal = true;
                      errorMessage = "The CGPAN : " + renewcgpan + " is not expired " + "(" + "Bank Reference No. :" + tempWcRenewApplication.getMliRefNo() + ")";
                      errorMessages.add(errorMessage);
                    }

                    tempWcRenewApplication = null;
                  }

                  if ((!renewcgpan.equals("0")) && (renewcgpan.substring(11, 13).equals("R9")))
                  {
                    errorMessageVal = true;
                    errorMessage = "This CGPAN :" + cgpan + " cannot be renewed further " + "(" + "Bank Reference No. :" + wcRenewApplication.getMliRefNo() + ")";
                    errorMessages.add(errorMessage);
                  }

                }
                catch (DatabaseException databaseException)
                {
                  errorMessageVal = true;
                  errorMessage = "The CGPAN :" + cgpan + " is not valid for Working Capital Renewal ";
                  errorMessages.add(errorMessage);
                }

                wcRenewApplication = null;
              }

              if (application.getWcEnhancement())
              {
                Application wcEnhanceApplication = new Application();
                try
                {
                  wcEnhanceApplication = this.appDAO.getAppForCgpan(null, cgpan);
                  if (!wcEnhanceApplication.getLoanType().equals("WC"))
                  {
                    errorMessageVal = true;
                    errorMessage = "The CGPAN : " + cgpan + " cannot be applied for WC Enhancement " + "(" + "Bank Reference No. :" + wcEnhanceApplication.getMliRefNo() + ")";
                    errorMessages.add(errorMessage);
                  }
                  if ((wcEnhanceApplication.getLoanType().equals("WC")) && (!wcEnhanceApplication.getStatus().equals("AP")))
                  {
                    errorMessageVal = true;
                    errorMessage = "The CGPAN : " + cgpan + " is not an Approved Application " + "(" + "Bank Reference No. :" + wcEnhanceApplication.getMliRefNo() + ")";
                    errorMessages.add(errorMessage);
                  }

                }
                catch (DatabaseException databaseException)
                {
                  errorMessageVal = true;
                  errorMessage = "The CGPAN :" + cgpan + " is not valid for Working Capital Enhancement ";
                  errorMessages.add(errorMessage);
                }

                Application enhancementApp = new Application();
                try
                {
                  enhancementApp = this.appDAO.getApplication(null, application.getCgpanReference(), "");
                  Log.log(4, "ApplicationProcessor", "uploadApplication", "application Wc:" + application.getWc().getEnhancedFundBased());
                  Log.log(4, "ApplicationProcessor", "uploadApplication", "wcEnhanceApplication Wc:" + enhancementApp.getProjectOutlayDetails().getWcFundBasedSanctioned());
                  if (application.getWc().getEnhancedFundBased() < enhancementApp.getProjectOutlayDetails().getWcFundBasedSanctioned())
                  {
                    errorMessageVal = true;
                    errorMessage = "For Enhancement of CGPAN : " + cgpan + " The Enhanced Fund Based Amount should be greater than the current fund based sanctioned Amount " + "(" + "Bank Reference No. :" + enhancementApp.getMliRefNo() + ")";
                    errorMessages.add(errorMessage);
                  }

                  if (application.getWc().getEnhancedNonFundBased() < enhancementApp.getProjectOutlayDetails().getWcNonFundBasedSanctioned())
                  {
                    errorMessageVal = true;
                    errorMessage = "For Enhancement of CGPAN : " + cgpan + " the Enhanced Non Fund Based amount should be greater than the current Non fund based sanctioned Amount " + "(" + "Bank Reference No. :" + enhancementApp.getMliRefNo() + ")";
                    errorMessages.add(errorMessage);
                  }

                  if (application.getWc().getEnhancedFundBased() + application.getWc().getEnhancedNonFundBased() < enhancementApp.getProjectOutlayDetails().getWcFundBasedSanctioned() + enhancementApp.getProjectOutlayDetails().getWcNonFundBasedSanctioned())
                  {
                    errorMessageVal = true;
                    errorMessage = "For Enhancement of CGPAN : " + cgpan + " the total enhanced amount should be greater than the current sanctioned Amount " + "(" + "Bank Reference No. :" + enhancementApp.getMliRefNo() + ")";
                    errorMessages.add(errorMessage);
                  }

                }
                catch (DatabaseException databaseException)
                {
                  errorMessageVal = true;
                  errorMessage = "The CGPAN :" + cgpan + " is not valid for Working Capital Enhancement " + "(" + "Bank Reference No. :" + enhancementApp.getMliRefNo() + ")";
                  errorMessages.add(errorMessage);
                }

                enhancementApp = null;
                wcEnhanceApplication = null;
              }

            }
            else if ((application.getBorrowerDetails().getSsiDetails().getCgbid() != null) && (!application.getBorrowerDetails().getSsiDetails().getCgbid().equals("")))
            {
              String cgpan = "";
              String cgbid = application.getBorrowerDetails().getSsiDetails().getCgbid();

              ArrayList bidList = this.appDAO.getAllBids();
              if (!bidList.contains(cgbid))
              {
                errorMessageVal = true;
                errorMessage = "The CGBID :" + cgbid + " does not exist";
                errorMessages.add(errorMessage);
              }

              ClaimsProcessor cpProcessor = new ClaimsProcessor();
              ArrayList borrowerIds = cpProcessor.getAllBorrowerIDs(bankId + zoneId + branchId);
              if (!borrowerIds.contains(cgbid))
              {
                errorMessageVal = true;
                errorMessage = "The CGBID :" + cgbid + " does not belong to the Member " + bankId + zoneId + branchId;
                errorMessages.add(errorMessage);
              }
              else if ((cgbid != null) && (!cgbid.equals("")))
              {
                int claimCount = this.appDAO.getClaimCount(cgbid);
                if (claimCount > 0)
                {
                  errorMessageVal = true;
                  errorMessage = "Application cannot be filed by this borrower " + cgbid + "since Claim Application has been submitted";
                  errorMessages.add(errorMessage);
                }

              }

            }

          }

          MLIInfo mliInfo = new MLIInfo();
          Registration registration = new Registration();
          Log.log(4, "ApplicationProcessor", "uploadApplication", "bank Id:" + bankId);
          Log.log(4, "ApplicationProcessor", "uploadApplication", "zone Id:" + zoneId);
          Log.log(4, "ApplicationProcessor", "uploadApplication", "branchId:" + branchId);
          mliInfo = registration.getMemberDetails(bankId, zoneId, branchId);
          String mcgfFlag = mliInfo.getSupportMCGF();
          Log.log(4, "ApplicationProcessor", "uploadApplication", "MCGF Flag:" + mcgfFlag);

          if ((mcgfFlag.equals("Y")) && (application.getMCGFDetails() == null))
          {
            errorMessageVal = true;
            errorMessage = "For the MCGF User" + bankId + zoneId + branchId + " ,MCGF Details are not available.Hence this application cannot be submitted";
            errorMessages.add(errorMessage);
          }

          if (application.getMCGFDetails() != null)
          {
            if ((application.getMCGFDetails().getParticipatingBank() != null) && (!application.getMCGFDetails().getParticipatingBank().equals("")) && (mcgfFlag.equals("N")))
            {
              errorMessageVal = true;
              errorMessage = "For an Non - MCGF User ,MCGF Details should not be available.Hence this application cannot be submitted";
              errorMessages.add(errorMessage);
            }

            if ((mcgfFlag.equals("Y")) && (!application.getMCGFDetails().getMcgfId().equals(bankId + zoneId + branchId)))
            {
              errorMessageVal = true;
              Log.log(4, "ApplicationProcessor", "uploadApplication", "MCGF ID:" + application.getMCGFDetails().getMcgfId());
              errorMessage = "For an MCGF User ,MCGF ID should be equal to the logged In user ";
              errorMessages.add(errorMessage);
            }

            MCGSProcessor mcgsProcessor = new MCGSProcessor();
            if (application.getMCGFDetails().getMcgfId().equals(bankId + zoneId + branchId))
            {
              ArrayList participatingBanks = mcgsProcessor.getAllParticipatingBanks(bankId + zoneId + branchId);
              if ((participatingBanks == null) || (participatingBanks.size() == 0))
              {
                errorMessageVal = true;
                errorMessage = "For the MCGF ID " + bankId + zoneId + branchId + " no participating banks are available.Hence application cannot be submitted";
                errorMessages.add(errorMessage);
              }
            }

          }

          if (!errorMessageVal)
          {
            application.setBankId(bankId);
            application.setZoneId(zoneId);
            application.setBranchId(branchId);

            if (application.getAdditionalTC())
            {
              borrowerDetails = applicationDAO.fetchBorrowerDtls("", application.getCgpanReference());

              Log.log(5, "ApplicationProcessor", "uploadApplication", "covered here " + application.getBorrowerDetails().getPreviouslyCovered());

              if (borrowerDetails != null)
              {
                int ssiRefNo = borrowerDetails.getSsiDetails().getBorrowerRefNo();
                SSIDetails ssiDtl = new SSIDetails();
                ssiDtl.setBorrowerRefNo(ssiRefNo);
                BorrowerDetails borrowerDtl = new BorrowerDetails();
                borrowerDtl.setSsiDetails(ssiDtl);
                application.setBorrowerDetails(borrowerDtl);
              }

              application.setMliID(application.getBankId() + application.getZoneId() + application.getBranchId());

              Log.log(4, "ApplicationProcessor", "uploadApplication", "Additional Term Loan");
              this.appDAO.submitAddlTermCredit(application, userId);

              errorMessage = "Application for CGPAN " + application.getCgpanReference() + " has been submitted successfully for Additional Term Loan";
              errorMessages.add(errorMessage);
            }
            else if (application.getWcEnhancement())
            {
              Application enhanceApplication = this.appDAO.getAppForCgpan(null, application.getCgpanReference());
              Application enhancementApp = this.appDAO.getApplication(null, enhanceApplication.getCgpan(), "");
              String status = enhanceApplication.getStatus();
              application.setStatus(status);
              application.setAppRefNo(enhanceApplication.getAppRefNo());
              application.setMliBranchName(enhanceApplication.getMliBranchName());
              application.setMliRefNo(enhanceApplication.getMliRefNo());
              WorkingCapital enhanceWc = application.getWc();
              enhanceWc.setFundBasedLimitSanctioned(enhanceWc.getEnhancedFundBased());
              enhanceWc.setNonFundBasedLimitSanctioned(enhanceWc.getEnhancedNonFundBased());
              enhanceWc.setLimitFundBasedInterest(enhanceWc.getEnhancedFBInterest());
              enhanceWc.setLimitNonFundBasedCommission(enhanceWc.getEnhancedNFBComission());
              enhanceWc.setCreditFundBased(enhanceWc.getEnhancedFundBased());
              enhanceWc.setCreditNonFundBased(enhanceWc.getEnhancedNonFundBased());

              if ((enhancementApp.getProjectOutlayDetails().getWcFundBasedSanctioned() == 0.0D) && (enhanceWc.getEnhancedFundBased() != 0.0D))
              {
                enhanceWc.setLimitFundBasedSanctionedDate(enhanceWc.getEnhancementDate());
              }
              else if ((enhancementApp.getProjectOutlayDetails().getWcFundBasedSanctioned() != 0.0D) && (enhanceWc.getEnhancedFundBased() != 0.0D))
              {
                enhanceWc.setLimitFundBasedSanctionedDate(enhanceWc.getEnhancementDate());
              }
              if ((enhancementApp.getProjectOutlayDetails().getWcNonFundBasedSanctioned() == 0.0D) && (enhanceWc.getEnhancedNonFundBased() != 0.0D))
              {
                enhanceWc.setLimitNonFundBasedSanctionedDate(enhanceWc.getEnhancementDate());
              }
              else if ((enhancementApp.getProjectOutlayDetails().getWcNonFundBasedSanctioned() != 0.0D) && (enhanceWc.getEnhancedNonFundBased() != 0.0D))
              {
                enhanceWc.setLimitNonFundBasedSanctionedDate(enhanceWc.getEnhancementDate());
              }

              enhanceWc.setEnhancedFundBased(enhanceWc.getEnhancedFundBased() - enhancementApp.getProjectOutlayDetails().getWcFundBasedSanctioned());
              enhanceWc.setEnhancedNonFundBased(enhanceWc.getEnhancedNonFundBased() - enhancementApp.getProjectOutlayDetails().getWcNonFundBasedSanctioned());

              Log.log(4, "ApplicationProcessor", "uploadApplication", "enhanced fund based :" + enhanceWc.getEnhancedNonFundBased());
              Log.log(4, "ApplicationProcessor", "uploadApplication", "credit to be guarantee :" + enhanceWc.getCreditNonFundBased());
              Log.log(4, "ApplicationProcessor", "uploadApplication", "enhanced fund based :" + enhanceWc.getEnhancedFundBased());
              Log.log(4, "ApplicationProcessor", "uploadApplication", "enhanced fund based :" + enhanceWc.getCreditFundBased());

              application.setWc(enhanceWc);

              borrowerDetails = applicationDAO.fetchBorrowerDtls("", application.getCgpanReference());
              int ssiRefNo = borrowerDetails.getSsiDetails().getBorrowerRefNo();
              SSIDetails ssiDtl = new SSIDetails();
              ssiDtl.setBorrowerRefNo(ssiRefNo);
              BorrowerDetails borrowerDtl = new BorrowerDetails();
              borrowerDtl.setSsiDetails(ssiDtl);
              borrowerDtl.setPreviouslyCovered("Y");
              application.setBorrowerDetails(borrowerDtl);

              application.setMliID(application.getBankId() + application.getZoneId() + application.getBranchId());
              try
              {
                this.appDAO.storeWcEnhancement(application, userId);
              }
              catch (DatabaseException exception)
              {
                if (exception.getMessage().equalsIgnoreCase("Credit to be Guaranteed Amount should be within the eligible amount available for Guarantee"))
                {
                  errorMessage = "Credit to be Guaranteed Amount should be within the eligible amount available for Guarantee";
                  errorMessages.add(errorMessage);
                }

                if (exception.getMessage().equalsIgnoreCase("Borrower has crossed his exposure limit.Hence ineligible to submit a new application"))
                {
                  errorMessage = "Borrower has crossed his exposure limit.Hence ineligible to submit a new application";
                  errorMessages.add(errorMessage);
                }

              }

              errorMessage = "Application for CGPAN " + application.getCgpanReference() + " has been submitted successfully for Working Capital Enhancement";
              errorMessages.add(errorMessage);
            }
            else if (application.getWcRenewal())
            {
              borrowerDetails = applicationDAO.fetchBorrowerDtls("", application.getCgpanReference());

              Log.log(5, "ApplicationProcessor", "uploadApplication", "cgpan reference" + application.getCgpanReference());
              Log.log(5, "ApplicationProcessor", "uploadApplication", "covered here " + application.getBorrowerDetails().getPreviouslyCovered());

              if (borrowerDetails != null)
              {
                int ssiRefNo = borrowerDetails.getSsiDetails().getBorrowerRefNo();
                SSIDetails ssiDtl = new SSIDetails();
                ssiDtl.setBorrowerRefNo(ssiRefNo);
                BorrowerDetails borrowerDtl = new BorrowerDetails();
                borrowerDtl.setSsiDetails(ssiDtl);
                application.setBorrowerDetails(borrowerDtl);
              }

              WorkingCapital tempWc = application.getWc();
              tempWc.setFundBasedLimitSanctioned(tempWc.getRenewalFundBased());
              tempWc.setNonFundBasedLimitSanctioned(tempWc.getRenewalNonFundBased());
              tempWc.setLimitFundBasedInterest(tempWc.getRenewalFBInterest());
              tempWc.setLimitNonFundBasedCommission(tempWc.getRenewalNFBComission());
              tempWc.setLimitFundBasedSanctionedDate(tempWc.getRenewalDate());

              tempWc.setCreditFundBased(tempWc.getRenewalFundBased());
              tempWc.setCreditNonFundBased(tempWc.getRenewalNonFundBased());

              if ((tempWc.getRenewalFundBased() != 0.0D) && (tempWc.getRenewalNonFundBased() == 0.0D))
              {
                tempWc.setLimitFundBasedSanctionedDate(tempWc.getRenewalDate());
              }
              else if ((tempWc.getRenewalFundBased() == 0.0D) && (tempWc.getRenewalNonFundBased() != 0.0D))
              {
                tempWc.setLimitNonFundBasedSanctionedDate(tempWc.getRenewalDate());
              }
              else if ((tempWc.getRenewalFundBased() != 0.0D) && (tempWc.getRenewalNonFundBased() != 0.0D))
              {
                tempWc.setLimitNonFundBasedSanctionedDate(tempWc.getRenewalDate());
                tempWc.setLimitNonFundBasedSanctionedDate(tempWc.getRenewalDate());
              }

              application.setWc(tempWc);

              String renewcgpan = this.appDAO.checkRenewCgpan(application.getCgpanReference());
              Log.log(5, "ApplicationProcessor", "uploadApplication", "renewcgpan" + renewcgpan);

              if (renewcgpan.equals("0"))
              {
                application.setCgpanReference(application.getCgpanReference());
              }
              else {
                application.setCgpanReference(renewcgpan);
              }

              application.setMliID(application.getBankId() + application.getZoneId() + application.getBranchId());
              try
              {
                this.appDAO.storeWcRenewal(application, userId);
              }
              catch (DatabaseException exception)
              {
                if (exception.getMessage().equalsIgnoreCase("Credit to be Guaranteed Amount should be within the eligible amount available for Guarantee"))
                {
                  errorMessage = "Credit to be Guaranteed Amount should be within the eligible amount available for Guarantee";
                  errorMessages.add(errorMessage);
                }

                if (exception.getMessage().equalsIgnoreCase("Borrower has crossed his exposure limit.Hence ineligible to submit a new application"))
                {
                  errorMessage = "Borrower has crossed his exposure limit.Hence ineligible to submit a new application";
                  errorMessages.add(errorMessage);
                }

              }

              errorMessage = "Application for CGPAN " + application.getCgpanReference() + " has been submitted successfully for Working Capital Renewal";
              errorMessages.add(errorMessage);
            }
            else
            {
              Log.log(4, "ApplicationProcessor", "uploadApplication", "New Application");
              Log.log(4, "ApplicationProcessor", "uploadApplication", "borrower vcovered value" + application.getBorrowerDetails().getPreviouslyCovered());
              if (application.getBorrowerDetails().getPreviouslyCovered().equals("Y"))
              {
                String cgpanValue = application.getCgpanReference();

                if ((cgpanValue != null) && (!cgpanValue.equals("")))
                {
                  application.setCgpan(cgpanValue);
                }
              }
              application.setMliID(application.getBankId() + application.getZoneId() + application.getBranchId());
              try
              {
                this.appDAO.submitApplication(application, userId);
              }
              catch (DatabaseException exception)
              {
                if (exception.getMessage().equalsIgnoreCase("Credit to be Guaranteed Amount should be within the eligible amount available for Guarantee"))
                {
                  errorMessage = "Credit to be Guaranteed Amount should be within the eligible amount available for Guarantee";
                  errorMessages.add(errorMessage);
                }

                if (exception.getMessage().equalsIgnoreCase("Borrower has crossed his exposure limit.Hence ineligible to submit a new application"))
                {
                  errorMessage = "Borrower has crossed his exposure limit.Hence ineligible to submit a new application";
                  errorMessages.add(errorMessage);
                }

              }

              count++;
            }

          }

        }
        else
        {
          errorMessage = "This application has not been verified by the NO.Hence cannot be uploaded ";
          errorMessages.add(errorMessage);
        }

      }

      if (count != 0)
      {
        errorMessage = count + " new Application(s) have been successfully submitted";
        errorMessages.add(errorMessage);
      }

    }
    else
    {
      String errorMessage = "There are no applications for Upload";
      errorMessages.add(errorMessage);
    }
    Log.log(4, "ApplicationProcessor", "uploadApplication", "Exited");

    return errorMessages;
  }

  public Application getApplication(String mliID, String cgpan, String appRefNo)
    throws NoApplicationFoundException, DatabaseException
  {
    Log.log(4, "ApplicationProcessor", "getApplication", "Entered");

    Application application = null;

    Log.log(5, "ApplicationProcessor", "getApplication", "Value of CGPAN:" + cgpan);

    Log.log(5, "ApplicationProcessor", "getApplication", "Value of App Ref No:" + appRefNo);

    if ((!cgpan.equals("")) || (!appRefNo.equals("")))
    {
      application = this.appDAO.getApplication(mliID, cgpan, appRefNo);

      if (application == null) {
        throw new NoApplicationFoundException("No Application Found");
      }

    }

    Log.log(5, "ApplicationProcessor", "getApplication", "Value of Application :" + application);

    Log.log(4, "ApplicationProcessor", "getApplication", "Exited");

    return application;
  }

  public Application getPartApplication(String mliID, String cgpan, String appRefNo)
    throws NoApplicationFoundException, DatabaseException
  {
    Log.log(4, "ApplicationProcessor", "getPartApplication", "Entered");

    Application application = null;

    Log.log(5, "ApplicationProcessor", "getPartApplication", "Value of CGPAN:" + cgpan);

    Log.log(5, "ApplicationProcessor", "getPartApplication", "Value of App Ref No:" + appRefNo);

    if ((!cgpan.equals("")) || (!appRefNo.equals("")))
    {
      application = this.appDAO.getPartApplication(mliID, cgpan, appRefNo);

      if (application == null) {
        throw new NoApplicationFoundException("No Application Found");
      }

    }

    Log.log(5, "ApplicationProcessor", "getPartApplication", "Value of Application :" + application);

    Log.log(4, "ApplicationProcessor", "getPartApplication", "Exited");

    return application;
  }

  public Application getPartApplicationPath(String mliID, String cgpan, String appRefNo)
    throws NoApplicationFoundException, DatabaseException
  {
    Application application = null;

    Log.log(5, "ApplicationProcessor", "getPartApplication", "Value of CGPAN:" + cgpan);

    Log.log(5, "ApplicationProcessor", "getPartApplication", "Value of App Ref No:" + appRefNo);

    if ((!cgpan.equals("")) || (!appRefNo.equals("")))
    {
      application = this.appDAO.getPartApplicationPath(mliID, cgpan, appRefNo);

      if (application == null) {
        throw new NoApplicationFoundException("No Application Found");
      }

    }

    return application;
  }

  public ArrayList getPendingApps()
    throws DatabaseException, NoApplicationFoundException
  {
    Log.log(4, "ApplicationProcessor", "getPendingApps", "Entered");

    ArrayList eligibilityApps = (ArrayList)this.appDAO.getPendingApps();

    Log.log(4, "ApplicationProcessor", "getPendingApps", "Exited");

    return eligibilityApps;
  }

  public EligibleApplication getAppsForEligibilityCheck(String appRefNo)
    throws DatabaseException, NoApplicationFoundException
  {
    EligibleApplication eligibleApplication = this.appDAO.getAppsForEligibilityCheck(appRefNo);

    return eligibleApplication;
  }

  public void updateApplicationsStatus(Application application, String createdBy)
    throws DatabaseException
  {
    Log.log(4, "ApplicationProcessor", "updateApplicationsStatus", "Entered");

    this.appDAO.updateApplicationStatus(application, createdBy);

    Log.log(4, "ApplicationProcessor", "updateApplicationsStatus", "Exited");
    
    
  }

  public void updateRejectedApplicationsStatus(Application application, String createdBy)
    throws DatabaseException
  {
    Log.log(4, "ApplicationProcessor", "updateRejectedApplicationsStatus", "Entered");

    this.appDAO.updateRejectedApplicationStatus(application, createdBy);

    Log.log(4, "ApplicationProcessor", "updateRejectedApplicationsStatus", "Exited");
  }

  public void updateApplication(Application application, String createdBy)
    throws DatabaseException
  {
    Log.log(4, "ApplicationProcessor", "updateApplication", "Entered");
    if (application != null)
    {
      Log.log(4, "ApplicationProcessor", "updateApplication", "Before updating in the processor class....");

      this.appDAO.updateApplication(application, createdBy);
      Log.log(4, "ApplicationProcessor", "updateApplication", "After updating in the processor class....");

      Log.log(4, "ApplicationProcessor", "updateApplication", "Exited");

      application = null;
    }
    else
    {
        return;
    }
  }

  public ArrayList getApplicationsForReapproval(String userId)
    throws DatabaseException, NoApplicationFoundException
  {
    Log.log(4, "ApplicationProcessor", "getApplicationsForReapproval", "Entered");

    ArrayList ineligibleReapproveApps = new ArrayList();
    ArrayList eligibleReapproveApps = new ArrayList();
    ArrayList reapprovalAppsList = new ArrayList();
    ArrayList appRefNos = new ArrayList();

    Application application = null;

    ArrayList reapprovalApplications = this.appDAO.getApplicationsForReapproval(userId);
    ArrayList tcReapproveApps = (ArrayList)reapprovalApplications.get(0);
    ArrayList wcReapproveApps = (ArrayList)reapprovalApplications.get(1);

    int tcSize = tcReapproveApps.size();
    int wcSize = wcReapproveApps.size();
    double reapprovedAmt;
    for (int j = 0; j < tcSize; j++)
    {
      Application tcApplication = (Application)tcReapproveApps.get(j);

      Application tempApplication = this.appDAO.getApplication(null, tcApplication.getCgpan(), "");
      reapprovedAmt = calApprovedAmount(tempApplication);
      tcApplication.setReapprovedAmount(reapprovedAmt);

      Log.log(4, "ApplicationProcessor", "getApplicationsForReapproval", "tc status:" + tcApplication.getStatus());
      Log.log(4, "ApplicationProcessor", "getApplicationsForReapproval", "tc App Ref No :" + tcApplication);
      TermLoan tempTermLoan = tcApplication.getTermLoan();
      if (tempApplication.getLoanType().equals("CC"))
      {
        tempTermLoan.setCreditGuaranteed(tempApplication.getTermLoan().getCreditGuaranteed() + tempApplication.getWc().getCreditFundBased() + tempApplication.getWc().getCreditNonFundBased());
      }
      else
      {
        tempTermLoan.setCreditGuaranteed(tempApplication.getTermLoan().getCreditGuaranteed());
      }

      tcApplication.setTermLoan(tempTermLoan);
      tcApplication.setReapprovalRemarks(tempApplication.getReapprovalRemarks());
      appRefNos.add(tcApplication);

      tempApplication = null;
    }

    for (int k = 0; k < wcSize; k++)
    {
      Application wcApplication = (Application)wcReapproveApps.get(k);
      Application tempApplication = this.appDAO.getApplication(null, wcApplication.getCgpan(), "");
      reapprovedAmt = calApprovedAmount(tempApplication);
      wcApplication.setReapprovedAmount(reapprovedAmt);

      TermLoan tempTc = wcApplication.getTermLoan();
      tempTc.setCreditGuaranteed(tempApplication.getWc().getCreditFundBased() + tempApplication.getWc().getCreditNonFundBased());
      wcApplication.setReapprovalRemarks(tempApplication.getReapprovalRemarks());
      wcApplication.setTermLoan(tempTc);

      Log.log(4, "ApplicationProcessor", "getApplicationsForReapproval", "wc status:" + wcApplication.getStatus());
      Log.log(4, "ApplicationProcessor", "getApplicationsForReapproval", "wc App Ref No :" + wcApplication);
      appRefNos.add(wcApplication);
    }

    int appRefNosList = appRefNos.size();

    for (int i = 0; i < appRefNosList; i++)
    {
      Application reapproveApp = (Application)appRefNos.get(i);
      String reapproveAppRefNo = reapproveApp.getAppRefNo();
      EligibleApplication eligibleApplication = this.appDAO.getAppsForEligibilityCheck(reapproveAppRefNo);

      if (!eligibleApplication.getFailedCondition().equals(""))
      {
        eligibleApplication.setAppRefNo(reapproveAppRefNo);
        eligibleApplication.setBorrowerRefNo(reapproveApp.getBorrowerDetails().getSsiDetails().getBorrowerRefNo());
        eligibleApplication.setCgpan(reapproveApp.getCgpan());
        eligibleApplication.setSubmissiondate(reapproveApp.getSubmittedDate().toString());
        eligibleApplication.setEligibleApprovedAmount(reapproveApp.getApprovedAmount());
        eligibleApplication.setEligibleCreditAmount(reapproveApp.getReapprovedAmount());
        eligibleApplication.setEligibleCreditGuaranteed(reapproveApp.getTermLoan().getCreditGuaranteed());
        eligibleApplication.setStatus(reapproveApp.getStatus());
        eligibleApplication.setEligibleRemarks(reapproveApp.getReapprovalRemarks());
        ineligibleReapproveApps.add(eligibleApplication);
      }
      else
      {
        eligibleReapproveApps.add(reapproveApp);
      }
    }

    reapprovalAppsList.add(ineligibleReapproveApps);
    reapprovalAppsList.add(eligibleReapproveApps);

    Log.log(4, "ApplicationProcessor", "getApplicationsForReapproval", "Exited");

    return reapprovalAppsList;
  }

  public String submitAddlTermCredit(Application application, String createdBy)
    throws DatabaseException, InvalidApplicationException
  {
    Log.log(4, "ApplicationProcessor", "submitAddlTermCredit", "Entered");

    String appRefNo = this.appDAO.submitAddlTermCredit(application, createdBy);   //***************************

    Log.log(4, "ApplicationProcessor", "submitAddlTermCredit", "Exited");

    return appRefNo;
  }

  public ArrayList getCgpans(String mliId, String borrowerId, int type, String borrowerName)
    throws DatabaseException, NoApplicationFoundException
  {
    Log.log(4, "ApplicationProcessor", "getCgpans", "Entered");

    Log.log(5, "ApplicationProcessor", "getCgpans", "Value of MLI ID" + mliId);

    ArrayList cgpansList = null;
    cgpansList = this.appDAO.getCgpans(mliId, borrowerId, type, borrowerName);

    Log.log(4, "ApplicationProcessor", "getCgpans", "Exited");

    return cgpansList;
  }

  public ArrayList getAppRefNos(String mliId, String borrowerId, String borrowerName)
    throws DatabaseException, NoApplicationFoundException
  {
    Log.log(4, "ApplicationProcessor", "getAppRefNos", "Entered");

    ArrayList cgpanAppRefNoList = this.appDAO.getAppRefNos(mliId, borrowerId, borrowerName);

    Log.log(4, "ApplicationProcessor", "getAppRefNos", "AppRefNosList:" + cgpanAppRefNoList);

    return cgpanAppRefNoList;
  }

  public ArrayList getApplicationsForApprovalPath(String userId, String bankName)
    throws DatabaseException, NoApplicationFoundException
  {
    ArrayList applicationsList = new ArrayList();
    Log.log(4, "ApplicationProcessor", "getApplicationsForApprovalPath", "Entered. Memory : " + Runtime.getRuntime().freeMemory());

    HashMap duplicateApplications = new HashMap();
    HashMap ineligibleApplications = new HashMap();

    Application application = new Application();
    EligibleApplication eligibleApplication = new EligibleApplication();

    ArrayList eligibleAppsList = new ArrayList();

    ArrayList eligibleNonDupApps = new ArrayList();

    ArrayList eligibleNonDupRsfApps = new ArrayList();

    ArrayList eligibleDupApps = new ArrayList();
    ArrayList ineligibleNonDupApps = new ArrayList();
    ArrayList ineligibleApps = new ArrayList();
    ArrayList dupApps = new ArrayList();
    ArrayList ineligibleDupApps = new ArrayList();
    String mliId = null;
    String cgpan = "";

    String mliFlag = "A";

    Log.log(5, "ApplicationProcessor", "getApplicationsForApproval", "Before callin to get duplicate Applications Memory Size :" + Runtime.getRuntime().freeMemory());

    ArrayList duplicateApp = new ArrayList();

    ArrayList duplicateApps = checkDuplicatePath(mliFlag, bankName);

    ArrayList tcDuplicateApp = (ArrayList)duplicateApps.get(0);
    ArrayList wcDuplicateApp = (ArrayList)duplicateApps.get(1);
    for (int i = 0; i < tcDuplicateApp.size(); i++)
    {
      DuplicateApplication duplicateApplication = (DuplicateApplication)tcDuplicateApp.get(i);
      duplicateApp.add(duplicateApplication);
    }
    for (int j = 0; j < wcDuplicateApp.size(); j++)
    {
      DuplicateApplication duplicateApplication = (DuplicateApplication)wcDuplicateApp.get(j);
      duplicateApp.add(duplicateApplication);
    }
    Log.log(5, "ApplicationProcessor", "getApplicationsForApproval", "After callin to get duplicate Applications Memory Size :" + Runtime.getRuntime().freeMemory());
    ArrayList dupAppRefNoList = new ArrayList();
    int duplicateAppSize = duplicateApp.size();

    DuplicateApplication dupApplication = null;
    for (int a = 0; a < duplicateAppSize; a++)
    {
      dupApplication = (DuplicateApplication)duplicateApp.get(a);
      String dupRefNo = dupApplication.getNewAppRefNo();

      duplicateApplications.put(dupRefNo, dupApplication);
      dupAppRefNoList.add(dupRefNo);
      dupRefNo = null;
    }
    dupAppRefNoList.trimToSize();
    dupApplication = null;
    Log.log(5, "ApplicationProcessor", "getApplicationsForApproval", "duplicate app ref nos size :" + dupAppRefNoList.size());

    Log.log(5, "ApplicationProcessor", "getApplicationsForApproval", "Before callin to get view applications for approval Memory Size :" + Runtime.getRuntime().freeMemory());

    ArrayList pendingAppList = this.appDAO.viewApplicationsForApprovalPath(userId, bankName);

    Log.log(5, "ApplicationProcessor", "getApplicationsForApproval", "Pending size:" + pendingAppList.size());
    Log.log(5, "ApplicationProcessor", "getApplicationsForApproval", "After callin to get view applications for approval Memory Size :" + Runtime.getRuntime().freeMemory());

    ArrayList tcPendingAppList = (ArrayList)pendingAppList.get(0);
    ArrayList wcPendingAppList = (ArrayList)pendingAppList.get(1);

    int tcPendingAppListSize = tcPendingAppList.size();

    Log.log(5, "ApplicationProcessor", "getApplicationsForApproval", "tc Pending size:" + tcPendingAppListSize);
    int wcPendingAppListSize = wcPendingAppList.size();

    Log.log(5, "ApplicationProcessor", "getApplicationsForApproval", "wc Pending size:" + wcPendingAppListSize);

    Integer intCount = (Integer)pendingAppList.get(2);

    pendingAppList.clear();
    pendingAppList = null;

    ArrayList appRefNoList = new ArrayList();
    Application tcApplication = null;
    String tcAppRefNo = null;
    for (int x = 0; x < tcPendingAppListSize; x++)
    {
      tcApplication = (Application)tcPendingAppList.get(x);
      tcAppRefNo = tcApplication.getAppRefNo();
      appRefNoList.add(tcAppRefNo);
    }

    tcPendingAppList.clear();
    tcPendingAppList = null;
    tcApplication = null;
    tcAppRefNo = null;
    Application wcApplication = null;
    String wcAppRefNo = null;
    for (int y = 0; y < wcPendingAppListSize; y++)
    {
      wcApplication = new Application();
      wcApplication = (Application)wcPendingAppList.get(y);
      wcAppRefNo = wcApplication.getAppRefNo();
      appRefNoList.add(wcAppRefNo);
    }

    appRefNoList.trimToSize();
    wcPendingAppList.clear();
    wcPendingAppList = null;
    wcApplication = null;
    wcAppRefNo = null;
    int appRefNoListSize = appRefNoList.size();

    Log.log(5, "ApplicationProcessor", "getApplicationsForApproval", "pending app ref nos size :" + appRefNoListSize);
    for (int i = 0; i < appRefNoListSize; i++)
    {
      String appRefNo = (String)appRefNoList.get(i);

      Log.log(5, "ApplicationProcessor", "getApplicationsForApproval", "Before callin eligiblity check :" + appRefNo);
      Log.log(5, "ApplicationProcessor", "getApplicationsForApproval", "Before callin to get eligiblity Check Memory Size :" + Runtime.getRuntime().freeMemory());

      eligibleApplication = getAppsForEligibilityCheck(appRefNo);

      Log.log(5, "ApplicationProcessor", "getApplicationsForApproval", "After callin to get eligiblity Check Memory Size :" + Runtime.getRuntime().freeMemory());

      if (!eligibleApplication.getFailedCondition().equals(""))
      {
        eligibleApplication.setAppRefNo(appRefNo);
        eligibleAppsList.add(eligibleApplication);

        application = null;
      }

      appRefNo = null;
      eligibleApplication = null;
    }

    Log.log(5, "ApplicationProcessor", "getApplicationsForApproval", "eligible apps size :" + eligibleAppsList.size());
    ArrayList eligibleAppRefNoList = new ArrayList();

    int eligibleAppsListSize = eligibleAppsList.size();

    EligibleApplication eligibleApp = null;
    for (int b = 0; b < eligibleAppsListSize; b++)
    {
      eligibleApp = (EligibleApplication)eligibleAppsList.get(b);
      String eligibleRefNo = eligibleApp.getAppRefNo();
      ineligibleApplications.put(eligibleRefNo, eligibleApp);
      eligibleAppRefNoList.add(eligibleRefNo);
      eligibleApp = null;
      eligibleRefNo = null;
    }
    eligibleAppRefNoList.trimToSize();

    BorrowerDetails borrowerDetails = new BorrowerDetails();
    SSIDetails ssiDetails = new SSIDetails();
    borrowerDetails.setSsiDetails(ssiDetails);
    String strLoanType = null;
    String handicraftFlag = null;
    String handicraftReimb = null;

    TermLoan termLoan = null;
    WorkingCapital workingCapital = null;
    for (int i = 0; i < appRefNoListSize; i++)
    {
      double creditAmount = 0.0D;
      String appRefNo = (String)appRefNoList.get(i);

      application = new Application();

      application.setBorrowerDetails(borrowerDetails);

      Log.log(5, "ApplicationProcessor", "getApplicationsForApproval", "Before callin to get Application Memory = :" + Runtime.getRuntime().freeMemory());

      application = getPartApplicationPath(mliId, cgpan, appRefNo);

      String memberId = application.getMliID();
      String bankId = memberId.substring(0, 4);
      String zoneId = memberId.substring(4, 8);
      String branchId = memberId.substring(8, 12);
      Registration registration = new Registration();
      MLIInfo mliInfo = registration.getMemberDetails(bankId, zoneId, branchId);

      application.setZoneName(mliInfo.getZoneName());

      strLoanType = application.getLoanType();
      handicraftFlag = application.getHandiCrafts();
      handicraftReimb = application.getDcHandicrafts();

      termLoan = this.appDAO.getTermLoan(appRefNo, strLoanType);
      application.setTermLoan(termLoan);
      termLoan = null;

      workingCapital = this.appDAO.getWorkingCapital(appRefNo, strLoanType);

      application.setWc(workingCapital);
      workingCapital = null;

      Log.log(5, "ApplicationProcessor", "getApplicationsForApproval", "After callin to get Application Memory = :" + Runtime.getRuntime().freeMemory());

      Log.log(5, "ApplicationProcessingAction", "showAppsForApproval", "Before checkin:" + appRefNo);

      double dblApprovedAmt = 0.0D;

      if ((!dupAppRefNoList.contains(appRefNo)) && (!eligibleAppRefNoList.contains(appRefNo)))
      {
        Log.log(5, "ApplicationProcessingAction", "showAppsForApproval", "Eligible Non Duplicate Ref No :" + appRefNo);
        Application clearApplication = new Application();
        BorrowerDetails clearBorrowerDetails = new BorrowerDetails();

        clearBorrowerDetails.setSsiDetails(new SSIDetails());
        clearApplication.setBorrowerDetails(clearBorrowerDetails);
        clearBorrowerDetails = null;

        Date submittedDate = application.getSubmittedDate();
        Log.log(5, "ApplicationProcessingAction", "showAppsForApproval", "submitted Date:" + submittedDate);
        int ssiRefNo = application.getBorrowerDetails().getSsiDetails().getBorrowerRefNo();
        Log.log(5, "ApplicationProcessingAction", "showAppsForApproval", "ssi ref no:" + ssiRefNo);

        clearApplication.setAppRefNo(appRefNo);
    //    System.out.println("In getApplicationsForApprovalPath sanctionedDate "+submittedDate);
        clearApplication.setSubmittedDate(submittedDate);

        clearApplication.setTermLoan(application.getTermLoan());

        clearApplication.setWc(application.getWc());

        clearApplication.setLoanType(strLoanType);

        clearApplication.setHandiCrafts(handicraftFlag);
        clearApplication.setDcHandicrafts(handicraftReimb);

        dblApprovedAmt = calApprovedAmount(application);

        clearApplication.setApprovedAmount(dblApprovedAmt);
        clearApplication.setStatus(application.getStatus());

        clearApplication.setRemarks(application.getRemarks());
        clearApplication.setZoneName(application.getZoneName());

        clearApplication.getBorrowerDetails().getSsiDetails().setConstitution(application.getBorrowerDetails().getSsiDetails().getConstitution());
        clearApplication.getBorrowerDetails().getSsiDetails().setSsiName(application.getBorrowerDetails().getSsiDetails().getSsiName());
        clearApplication.getBorrowerDetails().getSsiDetails().setActivityType(application.getBorrowerDetails().getSsiDetails().getActivityType());
        clearApplication.getBorrowerDetails().getSsiDetails().setSancDate_new(application.getBorrowerDetails().getSsiDetails().getSancDate_new());
        clearApplication.getBorrowerDetails().getSsiDetails().setIndustryNature(application.getBorrowerDetails().getSsiDetails().getIndustryNature());

        clearApplication.getBorrowerDetails().getSsiDetails().setBorrowerRefNo(ssiRefNo);
        if (!application.getScheme().equals("RSF"))
        {
          eligibleNonDupApps.add(clearApplication);
        }
        else {
          eligibleNonDupRsfApps.add(clearApplication);
        }

        submittedDate = null;

        clearApplication = null;
      }
      else if ((dupAppRefNoList.contains(appRefNo)) && (!eligibleAppRefNoList.contains(appRefNo)))
      {
        Log.log(5, "ApplicationProcessingAction", "showAppsForApproval", "Eligible Duplicate Ref No :" + appRefNo);
        creditAmount = 0.0D;
        if (strLoanType.equals("TC"))
        {
          creditAmount = application.getTermLoan().getCreditGuaranteed();
        } else if (strLoanType.equals("WC"))
        {
          creditAmount = application.getWc().getCreditFundBased() + application.getWc().getCreditNonFundBased();
        } else if (strLoanType.equals("CC"))
        {
          double tcCreditAmount = application.getTermLoan().getCreditGuaranteed();
          double wcCreditAmount = application.getWc().getCreditFundBased() + application.getWc().getCreditNonFundBased();
          creditAmount = tcCreditAmount + wcCreditAmount;
        }

        String industrynature = application.getBorrowerDetails().getSsiDetails().getIndustryNature();

        String activityType = application.getBorrowerDetails().getSsiDetails().getActivityType();

        DuplicateApplication eligibleDupApp = new DuplicateApplication();

        eligibleDupApp = (DuplicateApplication)duplicateApplications.get(appRefNo);

        eligibleDupApp.setDupCreditAmount(creditAmount);

        dblApprovedAmt = calApprovedAmount(application);

        eligibleDupApp.setDupApprovedAmount(dblApprovedAmt);

        eligibleDupApp.setStatus(application.getStatus());
        eligibleDupApp.setNature(industrynature);
        eligibleDupApp.setZoneName(application.getZoneName());
        eligibleDupApp.setLoanType(strLoanType);
        eligibleDupApp.setHandicraftFlag(handicraftFlag);
        eligibleDupApp.setDcHandicraftReimb(handicraftReimb);

        eligibleDupApp.setDuplicateRemarks(application.getRemarks());
        eligibleDupApp.setActivityType(activityType);

        eligibleDupApps.add(eligibleDupApp);

        eligibleDupApp = null;
      }
      else if ((!dupAppRefNoList.contains(appRefNo)) && (eligibleAppRefNoList.contains(appRefNo)))
      {
        Log.log(5, "ApplicationProcessingAction", "showAppsForApproval", "InEligible Non Duplicate Ref No :" + appRefNo);
        creditAmount = 0.0D;
        if (strLoanType.equals("TC"))
        {
          creditAmount = application.getTermLoan().getCreditGuaranteed();
        } else if (strLoanType.equals("WC"))
        {
          creditAmount = application.getWc().getCreditFundBased() + application.getWc().getCreditNonFundBased();
        } else if (strLoanType.equals("CC"))
        {
          double tcCreditAmount = application.getTermLoan().getCreditGuaranteed();
          double wcCreditAmount = application.getWc().getCreditFundBased() + application.getWc().getCreditNonFundBased();
          creditAmount = tcCreditAmount + wcCreditAmount;
        }

        EligibleApplication inEligibleApplication = new EligibleApplication();

        inEligibleApplication = (EligibleApplication)ineligibleApplications.get(appRefNo);

        inEligibleApplication.setEligibleCreditAmount(creditAmount);

        inEligibleApplication.setSubmissiondate(application.getSubmittedDate().toString());
        dblApprovedAmt = calApprovedAmount(application);
        inEligibleApplication.setEligibleApprovedAmount(dblApprovedAmt);

        inEligibleApplication.setStatus(application.getStatus());

        inEligibleApplication.setEligibleRemarks(application.getRemarks());

        ineligibleNonDupApps.add(inEligibleApplication);
        inEligibleApplication = null;
      }
      else if ((dupAppRefNoList.contains(appRefNo)) && (eligibleAppRefNoList.contains(appRefNo)))
      {
        Log.log(5, "ApplicationProcessingAction", "showAppsForApproval", "InEligible Duplicate Ref No :" + appRefNo);
        creditAmount = 0.0D;
        if (strLoanType.equals("TC"))
        {
          creditAmount = application.getTermLoan().getCreditGuaranteed();
        } else if (strLoanType.equals("WC"))
        {
          creditAmount = application.getWc().getCreditFundBased() + application.getWc().getCreditNonFundBased();
        } else if (strLoanType.equals("CC"))
        {
          double tcCreditAmount = application.getTermLoan().getCreditGuaranteed();
          double wcCreditAmount = application.getWc().getCreditFundBased() + application.getWc().getCreditNonFundBased();
          creditAmount = tcCreditAmount + wcCreditAmount;
        }
        DuplicateApplication dupApp = new DuplicateApplication();
        dupApp = (DuplicateApplication)duplicateApplications.get(appRefNo);
        dupApp.setDupCreditAmount(creditAmount);
        dupApp.setStatus(application.getStatus());
        dupApp.setDuplicateRemarks(application.getRemarks());

        EligibleApplication inEligibleApp = new EligibleApplication();
        inEligibleApp = (EligibleApplication)ineligibleApplications.get(appRefNo);

        dblApprovedAmt = calApprovedAmount(application);
        dupApp.setDupApprovedAmount(dblApprovedAmt);

        inEligibleApp.setSubmissiondate(application.getSubmittedDate().toString());
        inEligibleApp.setStatus(application.getStatus());

        inEligibleApp.setEligibleRemarks(application.getRemarks());

        dupApps.add(dupApp);
        ineligibleApps.add(inEligibleApp);
        dupApp = null;
        inEligibleApp = null;
      }
      strLoanType = null;
      appRefNo = null;
      application = null;
    }
    ssiDetails = null;
    borrowerDetails = null;
    eligibleAppRefNoList = null;
    ineligibleDupApps.add(dupApps);
    ineligibleDupApps.add(ineligibleApps);

    applicationsList.add(eligibleNonDupApps);
    applicationsList.add(eligibleDupApps);
    applicationsList.add(ineligibleNonDupApps);
    applicationsList.add(ineligibleDupApps);
    applicationsList.add(intCount);
    applicationsList.add(eligibleNonDupRsfApps);
    cgpan = null;
    intCount = null;
    Log.log(4, "ApplicationProcessor", "getApplicationsForApproval", "Exited. Memory : " + Runtime.getRuntime().freeMemory());

    duplicateApplications = null;
    ineligibleApplications = null;

    application = null;
    eligibleApplication = null;

    eligibleAppsList = null;

    eligibleNonDupApps = null;
    eligibleDupApps = null;
    ineligibleNonDupApps = null;
    ineligibleApps = null;
    dupApps = null;
    ineligibleDupApps = null;

    return applicationsList;
  }

  public ArrayList getApplicationsForApproval(String userId)
    throws DatabaseException, NoApplicationFoundException
  {
    ArrayList applicationsList = new ArrayList();

    Log.log(4, "ApplicationProcessor", "getApplicationsForApproval", "Entered. Memory : " + Runtime.getRuntime().freeMemory());

    HashMap duplicateApplications = new HashMap();
    HashMap ineligibleApplications = new HashMap();

    Application application = new Application();
    EligibleApplication eligibleApplication = new EligibleApplication();

    ArrayList eligibleAppsList = new ArrayList();

    ArrayList eligibleNonDupApps = new ArrayList();
    ArrayList eligibleDupApps = new ArrayList();
    ArrayList ineligibleNonDupApps = new ArrayList();
    ArrayList ineligibleApps = new ArrayList();
    ArrayList dupApps = new ArrayList();
    ArrayList ineligibleDupApps = new ArrayList();

    String mliId = null;
    String cgpan = "";

    String mliFlag = "A";

    Log.log(5, "ApplicationProcessor", "getApplicationsForApproval", "Before callin to get duplicate Applications Memory Size :" + Runtime.getRuntime().freeMemory());

    ArrayList duplicateApp = new ArrayList();

    ArrayList duplicateApps = checkDuplicate(mliFlag);

    ArrayList tcDuplicateApp = (ArrayList)duplicateApps.get(0);
    ArrayList wcDuplicateApp = (ArrayList)duplicateApps.get(1);

    for (int i = 0; i < tcDuplicateApp.size(); i++)
    {
      DuplicateApplication duplicateApplication = (DuplicateApplication)tcDuplicateApp.get(i);
   //   System.out.println("duplicateApplication.newAppRefNo" + duplicateApplication.getNewAppRefNo());

      duplicateApp.add(duplicateApplication);
    }
    for (int j = 0; j < wcDuplicateApp.size(); j++)
    {
      DuplicateApplication duplicateApplication = (DuplicateApplication)wcDuplicateApp.get(j);
      duplicateApp.add(duplicateApplication);
    }

    Log.log(5, "ApplicationProcessor", "getApplicationsForApproval", "After callin to get duplicate Applications Memory Size :" + Runtime.getRuntime().freeMemory());

    ArrayList dupAppRefNoList = new ArrayList();
    int duplicateAppSize = duplicateApp.size();

    DuplicateApplication dupApplication = null;
    for (int a = 0; a < duplicateAppSize; a++)
    {
      dupApplication = (DuplicateApplication)duplicateApp.get(a);
      String dupRefNo = dupApplication.getNewAppRefNo();
      duplicateApplications.put(dupRefNo, dupApplication);
      dupAppRefNoList.add(dupRefNo);

      dupRefNo = null;
    }
    dupAppRefNoList.trimToSize();

    dupApplication = null;
    Log.log(5, "ApplicationProcessor", "getApplicationsForApproval", "duplicate app ref nos size :" + dupAppRefNoList.size());

    Log.log(5, "ApplicationProcessor", "getApplicationsForApproval", "Before callin to get view applications for approval Memory Size :" + Runtime.getRuntime().freeMemory());

    ArrayList pendingAppList = this.appDAO.viewApplicationsForApproval(userId);

    Log.log(5, "ApplicationProcessor", "getApplicationsForApproval", "Pending size:" + pendingAppList.size());

    Log.log(5, "ApplicationProcessor", "getApplicationsForApproval", "After callin to get view applications for approval Memory Size :" + Runtime.getRuntime().freeMemory());

    ArrayList tcPendingAppList = (ArrayList)pendingAppList.get(0);
    ArrayList wcPendingAppList = (ArrayList)pendingAppList.get(1);
    int tcPendingAppListSize = tcPendingAppList.size();
    Log.log(5, "ApplicationProcessor", "getApplicationsForApproval", "tc Pending size:" + tcPendingAppListSize);
    int wcPendingAppListSize = wcPendingAppList.size();
    Log.log(5, "ApplicationProcessor", "getApplicationsForApproval", "wc Pending size:" + wcPendingAppListSize);

    Integer intCount = (Integer)pendingAppList.get(2);

    pendingAppList.clear();
    pendingAppList = null;

    ArrayList appRefNoList = new ArrayList();
    Application tcApplication = null;
    String tcAppRefNo = null;
    for (int x = 0; x < tcPendingAppListSize; x++)
    {
      tcApplication = (Application)tcPendingAppList.get(x);
      tcAppRefNo = tcApplication.getAppRefNo();
   //   System.out.println("TC ARN ADDED |" + tcAppRefNo);
      appRefNoList.add(tcAppRefNo);
    }

    tcPendingAppList.clear();
    tcPendingAppList = null;
    tcApplication = null;
    tcAppRefNo = null;
    Application wcApplication = null;
    String wcAppRefNo = null;
    for (int y = 0; y < wcPendingAppListSize; y++)
    {
      wcApplication = new Application();
      wcApplication = (Application)wcPendingAppList.get(y);
      wcAppRefNo = wcApplication.getAppRefNo();
    //  System.out.println("WC ARN ADDED |" + wcAppRefNo);
      appRefNoList.add(wcAppRefNo);
    }

    appRefNoList.trimToSize();
    wcPendingAppList.clear();
    wcPendingAppList = null;
    wcApplication = null;
    wcAppRefNo = null;
    int appRefNoListSize = appRefNoList.size();

    Log.log(5, "ApplicationProcessor", "getApplicationsForApproval", "pending app ref nos size :" + appRefNoListSize);
    for (int i = 0; i < appRefNoListSize; i++)
    {
      String appRefNo = (String)appRefNoList.get(i);
    //  System.out.println("appRefNo-test:" + appRefNo);
      Log.log(5, "ApplicationProcessor", "getApplicationsForApproval", "Before callin eligiblity check :" + appRefNo);
      Log.log(5, "ApplicationProcessor", "getApplicationsForApproval", "Before callin to get eligiblity Check Memory Size :" + Runtime.getRuntime().freeMemory());

      eligibleApplication = getAppsForEligibilityCheck(appRefNo);

      Log.log(5, "ApplicationProcessor", "getApplicationsForApproval", "After callin to get eligiblity Check Memory Size :" + Runtime.getRuntime().freeMemory());

      if (!eligibleApplication.getFailedCondition().equals(""))
      {
        eligibleApplication.setAppRefNo(appRefNo);

        eligibleAppsList.add(eligibleApplication);

        application = null;
      }

      appRefNo = null;
      eligibleApplication = null;
    }

    Log.log(5, "ApplicationProcessor", "getApplicationsForApproval", "eligible apps size :" + eligibleAppsList.size());
    ArrayList eligibleAppRefNoList = new ArrayList();

    int eligibleAppsListSize = eligibleAppsList.size();

    EligibleApplication eligibleApp = null;
    for (int b = 0; b < eligibleAppsListSize; b++)
    {
      eligibleApp = (EligibleApplication)eligibleAppsList.get(b);
      String eligibleRefNo = eligibleApp.getAppRefNo();
      ineligibleApplications.put(eligibleRefNo, eligibleApp);
      eligibleAppRefNoList.add(eligibleRefNo);
      eligibleApp = null;
      eligibleRefNo = null;
    }
    eligibleAppRefNoList.trimToSize();

    BorrowerDetails borrowerDetails = new BorrowerDetails();
    SSIDetails ssiDetails = new SSIDetails();
    borrowerDetails.setSsiDetails(ssiDetails);
    String strLoanType = null;
    TermLoan termLoan = null;
    String memId = null;
    WorkingCapital workingCapital = null;
    for (int i = 0; i < appRefNoListSize; i++)
    {
      double creditAmount = 0.0D;
      String appRefNo = (String)appRefNoList.get(i);

      application = new Application();
      Application applicationTemp = new Application();

      application.setBorrowerDetails(borrowerDetails);

      Log.log(5, "ApplicationProcessor", "getApplicationsForApproval", "Before callin to get Application Memory = :" + Runtime.getRuntime().freeMemory());

      application = getPartApplication(mliId, cgpan, appRefNo);
      strLoanType = application.getLoanType();
      memId = this.appDAO.getMemberIdforAppRef(appRefNo);
      termLoan = this.appDAO.getTermLoan(appRefNo, strLoanType);
      application.setTermLoan(termLoan);
      termLoan = null;
      workingCapital = this.appDAO.getWorkingCapital(appRefNo, strLoanType);

      application.setWc(workingCapital);
      workingCapital = null;

      Log.log(5, "ApplicationProcessor", "getApplicationsForApproval", "After callin to get Application Memory = :" + Runtime.getRuntime().freeMemory());

      Log.log(5, "ApplicationProcessingAction", "showAppsForApproval", "Before checkin:" + appRefNo);

      double dblApprovedAmt = 0.0D;

      if ((!dupAppRefNoList.contains(appRefNo)) && (!eligibleAppRefNoList.contains(appRefNo)))
      {
        Log.log(5, "ApplicationProcessingAction", "showAppsForApproval", "Eligible Non Duplicate Ref No :" + appRefNo);
        Application clearApplication = new Application();
        BorrowerDetails clearBorrowerDetails = new BorrowerDetails();

        clearBorrowerDetails.setSsiDetails(new SSIDetails());
        clearApplication.setBorrowerDetails(clearBorrowerDetails);
        clearBorrowerDetails = null;

        Date submittedDate = application.getSubmittedDate();
        Log.log(5, "ApplicationProcessingAction", "showAppsForApproval", "submitted Date:" + submittedDate);
        int ssiRefNo = application.getBorrowerDetails().getSsiDetails().getBorrowerRefNo();
        Log.log(5, "ApplicationProcessingAction", "showAppsForApproval", "ssi ref no:" + ssiRefNo);
        clearApplication.setMliID(memId);
        clearApplication.setAppRefNo(appRefNo);
        clearApplication.setSubmittedDate(submittedDate);
        clearApplication.setTermLoan(application.getTermLoan());
        clearApplication.setWc(application.getWc());
        clearApplication.setLoanType(strLoanType);
        dblApprovedAmt = calApprovedAmount(application);
        clearApplication.setApprovedAmount(dblApprovedAmt);
        clearApplication.setStatus(application.getStatus());
        clearApplication.setRemarks(application.getRemarks());

        Date sanctionedDate = null;
        if (strLoanType.equals("TC"))
        {
          sanctionedDate = application.getTermLoan().getAmountSanctionedDate();
        } else if (strLoanType.equals("WC")) {
          if (application.getWc().getLimitFundBasedSanctionedDate() != null)
            sanctionedDate = application.getWc().getLimitFundBasedSanctionedDate();
          else
            sanctionedDate = application.getWc().getLimitNonFundBasedSanctionedDate();
        }
        else if (strLoanType.equals("CC")) {
          sanctionedDate = application.getTermLoan().getAmountSanctionedDate();
        }
        clearApplication.setSanctionedDate(sanctionedDate);

        clearApplication.getBorrowerDetails().getSsiDetails().setBorrowerRefNo(ssiRefNo);
        clearApplication.getBorrowerDetails().getSsiDetails().setActivityType(application.getBorrowerDetails().getSsiDetails().getActivityType());

        eligibleNonDupApps.add(clearApplication);
        submittedDate = null;

        clearApplication = null;
      }
      else if ((dupAppRefNoList.contains(appRefNo)) && (!eligibleAppRefNoList.contains(appRefNo)))
      {
        Log.log(5, "ApplicationProcessingAction", "showAppsForApproval", "Eligible Duplicate Ref No :" + appRefNo);
        creditAmount = 0.0D;
        Date sanctionedDate = null;

        if (strLoanType.equals("TC"))
        {
          creditAmount = application.getTermLoan().getCreditGuaranteed();
          sanctionedDate = application.getTermLoan().getAmountSanctionedDate();
        }
        else if (strLoanType.equals("WC"))
        {
          creditAmount = application.getWc().getCreditFundBased() + application.getWc().getCreditNonFundBased();

          if (application.getWc().getLimitFundBasedSanctionedDate() != null)
            sanctionedDate = application.getWc().getLimitFundBasedSanctionedDate();
          else
            sanctionedDate = application.getWc().getLimitNonFundBasedSanctionedDate();
        }
        else if (strLoanType.equals("CC"))
        {
          double tcCreditAmount = application.getTermLoan().getCreditGuaranteed();
          double wcCreditAmount = application.getWc().getCreditFundBased() + application.getWc().getCreditNonFundBased();
          creditAmount = tcCreditAmount + wcCreditAmount;
          sanctionedDate = application.getTermLoan().getAmountSanctionedDate();
        }

        DuplicateApplication eligibleDupApp = new DuplicateApplication();
        eligibleDupApp = (DuplicateApplication)duplicateApplications.get(appRefNo);
        eligibleDupApp.setDupCreditAmount(creditAmount);
        dblApprovedAmt = calApprovedAmount(application);
        eligibleDupApp.setDupApprovedAmount(dblApprovedAmt);
        eligibleDupApp.setStatus(application.getStatus());

        eligibleDupApp.setSanctionedDate(sanctionedDate);
        eligibleDupApp.setDuplicateRemarks(application.getRemarks());
        eligibleDupApps.add(eligibleDupApp);

        eligibleDupApp = null;
      }
      else if ((!dupAppRefNoList.contains(appRefNo)) && (eligibleAppRefNoList.contains(appRefNo)))
      {
        Log.log(5, "ApplicationProcessingAction", "showAppsForApproval", "InEligible Non Duplicate Ref No :" + appRefNo);
        creditAmount = 0.0D;
        if (strLoanType.equals("TC"))
        {
          creditAmount = application.getTermLoan().getCreditGuaranteed();
        } else if (strLoanType.equals("WC"))
        {
          creditAmount = application.getWc().getCreditFundBased() + application.getWc().getCreditNonFundBased();
        } else if (strLoanType.equals("CC"))
        {
          double tcCreditAmount = application.getTermLoan().getCreditGuaranteed();
          double wcCreditAmount = application.getWc().getCreditFundBased() + application.getWc().getCreditNonFundBased();
          creditAmount = tcCreditAmount + wcCreditAmount;
        }

        EligibleApplication inEligibleApplication = new EligibleApplication();
        inEligibleApplication = (EligibleApplication)ineligibleApplications.get(appRefNo);
        inEligibleApplication.setEligibleCreditAmount(creditAmount);
        inEligibleApplication.setSubmissiondate(application.getSubmittedDate().toString());
        dblApprovedAmt = calApprovedAmount(application);
        inEligibleApplication.setEligibleApprovedAmount(dblApprovedAmt);
        inEligibleApplication.setStatus(application.getStatus());
        inEligibleApplication.setEligibleRemarks(application.getRemarks());
        ineligibleNonDupApps.add(inEligibleApplication);
        inEligibleApplication = null;
      }
      else if ((dupAppRefNoList.contains(appRefNo)) && (eligibleAppRefNoList.contains(appRefNo)))
      {
        Log.log(5, "ApplicationProcessingAction", "showAppsForApproval", "InEligible Duplicate Ref No :" + appRefNo);
        creditAmount = 0.0D;
        if (strLoanType.equals("TC"))
        {
          creditAmount = application.getTermLoan().getCreditGuaranteed();
        } else if (strLoanType.equals("WC"))
        {
          creditAmount = application.getWc().getCreditFundBased() + application.getWc().getCreditNonFundBased();
        } else if (strLoanType.equals("CC"))
        {
          double tcCreditAmount = application.getTermLoan().getCreditGuaranteed();
          double wcCreditAmount = application.getWc().getCreditFundBased() + application.getWc().getCreditNonFundBased();
          creditAmount = tcCreditAmount + wcCreditAmount;
        }
        DuplicateApplication dupApp = new DuplicateApplication();
        dupApp = (DuplicateApplication)duplicateApplications.get(appRefNo);
        dupApp.setDupCreditAmount(creditAmount);
        dupApp.setStatus(application.getStatus());
        dupApp.setDuplicateRemarks(application.getRemarks());

        EligibleApplication inEligibleApp = new EligibleApplication();
        inEligibleApp = (EligibleApplication)ineligibleApplications.get(appRefNo);
        dblApprovedAmt = calApprovedAmount(application);
        dupApp.setDupApprovedAmount(dblApprovedAmt);
        inEligibleApp.setSubmissiondate(application.getSubmittedDate().toString());
        inEligibleApp.setStatus(application.getStatus());
        inEligibleApp.setEligibleRemarks(application.getRemarks());

        dupApps.add(dupApp);
        ineligibleApps.add(inEligibleApp);
        dupApp = null;
        inEligibleApp = null;
      }
      strLoanType = null;
      appRefNo = null;
      application = null;
    }
    ssiDetails = null;
    borrowerDetails = null;
    eligibleAppRefNoList = null;
    ineligibleDupApps.add(dupApps);
    ineligibleDupApps.add(ineligibleApps);

    applicationsList.add(eligibleNonDupApps);
    applicationsList.add(eligibleDupApps);
    applicationsList.add(ineligibleNonDupApps);
    applicationsList.add(ineligibleDupApps);
    applicationsList.add(intCount);
    cgpan = null;
    intCount = null;
    Log.log(4, "ApplicationProcessor", "getApplicationsForApproval", "Exited. Memory : " + Runtime.getRuntime().freeMemory());

    duplicateApplications = null;
    ineligibleApplications = null;

    application = null;
    eligibleApplication = null;

    eligibleAppsList = null;

    eligibleNonDupApps = null;
    eligibleDupApps = null;
    ineligibleNonDupApps = null;
    ineligibleApps = null;
    dupApps = null;
    ineligibleDupApps = null;

    return applicationsList;
  }

  public ArrayList getMessageTitleContent()
    throws DatabaseException
  {
    Log.log(4, "ApplicationProcessor", "getMessageTitleContent", "Entered");

    ArrayList messageTitles = this.appDAO.getMessageTitleContent();

    Log.log(4, "ApplicationProcessor", "getMessageTitleContent", "Exited");

    return messageTitles;
  }

  public SpecialMessage getMessageDesc(String title)
    throws DatabaseException
  {
    Log.log(4, "ApplicationProcessor", "getMessageDesc", "Entered");

    SpecialMessage specialMessage = this.appDAO.getMessageDesc(title);

    title = null;

    Log.log(4, "ApplicationProcessor", "getMessageDesc", "Exited");

    return specialMessage;
  }

  public void addSpecialMessage(SpecialMessage specialMessage)
    throws DatabaseException
  {
    Log.log(4, "ApplicationProcessor", "addSpecialMessage", "Entered");

    this.appDAO.addSpecialMessage(specialMessage);

    specialMessage = null;

    Log.log(4, "ApplicationProcessor", "addSpecialMessage", "Exited");
  }

  public void updateSpecialMessage(SpecialMessage specialMessage)
    throws DatabaseException
  {
    Log.log(4, "ApplicationProcessor", "updateSpecialMessage", "Entered");

    this.appDAO.updateSpecialMessage(specialMessage);

    specialMessage = null;

    Log.log(4, "ApplicationProcessor", "updateSpecialMessage", "Exited");
  }

  public String generateCgpan(Application application)
    throws DatabaseException
  {
    Log.log(4, "ApplicationProcessor", "generateCgpan", "Entered");

    String cgpan = this.appDAO.generateCgpan(application);

    application = null;

    Log.log(4, "ApplicationProcessor", "generateCgpan", "Exited");

    return cgpan;
  }

  public void updateGeneralStatus(Application application, String userId)
    throws DatabaseException
  {
    Log.log(4, "ApplicationProcessor", "updateGeneralStatus", "Entered");

    this.appDAO.updateGeneralStatus(application, userId);

    application = null;
    userId = null;

    Log.log(4, "ApplicationProcessor", "updateGeneralStatus", "Exited");
  }

  public BorrowerDetails viewBorrowerDetails(int ssiRefNo)
    throws DatabaseException
  {
    Log.log(4, "ApplicationProcessor", "viewBorrowerDetails", "Entered");

    BorrowerDetails borrowerDetails = this.appDAO.viewBorrowerDetails(ssiRefNo);

    Log.log(4, "ApplicationProcessor", "viewBorrowerDetails", "Exited");

    return borrowerDetails;
  }

  public double getCorpusAmount()
    throws DatabaseException
  {
    Log.log(4, "ApplicationProcessor", "updateCgbid", "Entered");

    double corpusAmount = this.appDAO.getCorpusAmount();

    Log.log(4, "ApplicationProcessor", "updateCgbid", "Exited");

    return corpusAmount;
  }

  public void checkCgpanPool(String appRefNo)
    throws DatabaseException
  {
    Log.log(4, "ApplicationProcessor", "checkCgpanPool", "Entered");

    this.appDAO.checkCgpanPool(appRefNo);

    appRefNo = null;

    Log.log(4, "ApplicationProcessor", "checkCgpanPool", "Exited");
  }

  public Application getAppForCgpan(String mliId, String cgpan)
    throws DatabaseException
  {
    Log.log(4, "ApplicationProcessor", "checkCgpanPool", "Entered");

    Application application = this.appDAO.getAppForCgpan(mliId, cgpan);
   // System.out.println("cgpan is " + cgpan);
    Log.log(4, "ApplicationProcessor", "checkCgpanPool", "Exited");

    return application;
  }

  public void updateReapprovalStatus(Application application, String userId)
    throws DatabaseException
  {
    Log.log(4, "ApplicationProcessor", "updateReapprovalStatus", "Entered");

    this.appDAO.updateReapprovalStatus(application, userId);

    application = null;
    userId = null;

    Log.log(4, "ApplicationProcessor", "updateReapprovalStatus", "Exited");
  }

  public String generateRenewCgpan(String renewalCgpan)
    throws DatabaseException
  {
    Log.log(4, "ApplicationProcessor", "generateRenewCgpan", "Entered");

    String renewCgpan = this.appDAO.generateRenewCgpan(renewalCgpan);

    Log.log(4, "ApplicationProcessor", "generateRenewCgpan", "Exited");

    return renewCgpan;
  }

  public String checkRenewCgpan(String cgpan)
    throws DatabaseException
  {
    Log.log(4, "ApplicationProcessor", "checkRenewCgpan", "Entered");

    String stringVal = this.appDAO.checkRenewCgpan(cgpan);

    Log.log(4, "ApplicationProcessor", "checkRenewCgpan", "Exited");

    return stringVal;
  }

  private double calApprovedAmount(Application application)
  {
    TermLoan termLoan = new TermLoan();
    WorkingCapital workingCapital = new WorkingCapital();
    SubSchemeValues subSchemeValues = new SubSchemeValues();

    RiskManagementProcessor rpProcessor = new RiskManagementProcessor();

    String mliId = null;
    String cgpan = "";

    double approvedAmount = 0.0D;
    double calApprovedAmt = 0.0D;
    try
    {
      String loanType = application.getLoanType();
      String subScheme = application.getSubSchemeName();
      double maxApprovedAmt = 0.0D;
      double maxGCoverAmt = 0.0D;
      double maxRsfApprovedAmt = 0.0D;
      if (!subScheme.equals("GLOBAL"))
      {
        double creditAmount = 0.0D;

        if (loanType.equals("TC"))
        {
          creditAmount = application.getTermLoan().getCreditGuaranteed();
        }
        else if (loanType.equals("WC"))
        {
          double fundBasedCredit = application.getWc().getCreditFundBased();
          double nonFundBasedCredit = application.getWc().getCreditNonFundBased();
          creditAmount = fundBasedCredit + nonFundBasedCredit;
        } else if (loanType.equals("CC"))
        {
          double tcCreditAmount = application.getTermLoan().getCreditGuaranteed();

          double fundBasedCredit = application.getWc().getCreditFundBased();
          double nonFundBasedCredit = application.getWc().getCreditNonFundBased();
          double wcCreditAmount = fundBasedCredit + nonFundBasedCredit;

          creditAmount = tcCreditAmount + wcCreditAmount;
        }

        subSchemeValues = rpProcessor.getSubSchemeValues(subScheme);
        if (subSchemeValues != null)
        {
          double maxBorrowerExposure = subSchemeValues.getMaxBorrowerExposureAmount();

          double maxExposureAmt = maxBorrowerExposure;

          if (maxExposureAmt < creditAmount)
          {
            approvedAmount = maxExposureAmt;
          }
          else {
            approvedAmount = creditAmount;
          }

        }
        else
        {
          approvedAmount = creditAmount;
        }

        Log.log(4, "ApplicationProcessor", "calApprovedAmount", "approvedAmount :" + approvedAmount + "for app ref" + application.getAppRefNo());

        GlobalLimits globalLimits = rpProcessor.getGlobalLimits(application.getScheme(), application.getSubSchemeName());

        double globalAmount = globalLimits.getUpperLimit();

        Log.log(4, "ApplicationProcessor", "calApprovedAmount", "globalAmount:" + globalAmount);

        double totalApprovedAmt = getTotalApprovedAmt(application);

        Log.log(4, "ApplicationProcessor", "calApprovedAmount", "globalAmount:" + totalApprovedAmt);

        if (globalAmount != 0.0D)
        {
          if ((totalApprovedAmt > globalAmount) || (totalApprovedAmt == globalAmount))
          {
            calApprovedAmt = approvedAmount;
          }
          else if (globalAmount > totalApprovedAmt)
          {
            double diffAmount = globalAmount - totalApprovedAmt;

            Log.log(4, "ApplicationProcessor", "calApprovedAmount", "diffAmount:" + diffAmount);

            if (diffAmount > approvedAmount)
            {
              calApprovedAmt = approvedAmount;
            }
            else if (diffAmount < approvedAmount)
            {
              calApprovedAmt = approvedAmount - diffAmount;
            }
          }

        }
        else
        {
          calApprovedAmt = approvedAmount;
        }

        double mcgsAmount = 0.0D;

        if (application.getScheme().equals("MCGS"))
        {
          double partBankAmount = getPartBankAmount(application.getMliID().substring(0, 4), application.getMliID().substring(4, 8), application.getMliID().substring(8, 12));
          double mcgsApprovedAmt = getMcgsApprovedAmount(application.getMliID().substring(0, 4), application.getMliID().substring(4, 8), application.getMliID().substring(8, 12));
          Log.log(4, "ApplicationProcessor", "calApprovedAmount", "partBankAmount:" + partBankAmount);
          Log.log(4, "ApplicationProcessor", "calApprovedAmount", "mcgsApprovedAmt:" + mcgsApprovedAmt);
          if ((partBankAmount != 0.0D) && (mcgsApprovedAmt != 0.0D))
          {
            if ((mcgsApprovedAmt > partBankAmount) || (mcgsApprovedAmt == partBankAmount))
            {
              mcgsAmount = calApprovedAmt;
            }
            else if (partBankAmount > mcgsApprovedAmt)
            {
              double diffMcgsAmount = partBankAmount - mcgsApprovedAmt;
              Log.log(4, "ApplicationProcessor", "calApprovedAmount", "diffMcgsAmount:" + diffMcgsAmount);
              if (calApprovedAmt > diffMcgsAmount)
              {
                mcgsAmount = calApprovedAmt - diffMcgsAmount;
              }
              else if (calApprovedAmt < diffMcgsAmount)
              {
                mcgsAmount = calApprovedAmt;
              }
            }

            calApprovedAmt = mcgsAmount;
          }
        }

        Log.log(4, "ApplicationProcessor", "calApprovedAmount", "calApprovedAmt:" + calApprovedAmt);
      }
      else if (subScheme.equals("GLOBAL"))
      {
        double creditAmount = 0.0D;

        if (loanType.equals("TC"))
        {
          creditAmount = application.getTermLoan().getCreditGuaranteed();
        }
        else if (loanType.equals("WC"))
        {
          double fundBasedCredit = application.getWc().getCreditFundBased();

          double nonFundBasedCredit = application.getWc().getCreditNonFundBased();

          creditAmount = fundBasedCredit + nonFundBasedCredit;
        }
        else if (loanType.equals("CC"))
        {
          double tcCreditAmount = application.getTermLoan().getCreditGuaranteed();

          double fundBasedCredit = application.getWc().getCreditFundBased();
          double nonFundBasedCredit = application.getWc().getCreditNonFundBased();
          double wcCreditAmount = fundBasedCredit + nonFundBasedCredit;

          creditAmount = tcCreditAmount + wcCreditAmount;
        }
        Administrator admin = new Administrator();
        ParameterMaster paramMaster = admin.getParameter();
        maxApprovedAmt = paramMaster.getMaxApprovedAmt();
        maxRsfApprovedAmt = paramMaster.getMaxRsfApprovedAmt();

        if ((application.getScheme().equals("RSF")) && (creditAmount <= maxRsfApprovedAmt)) {
          calApprovedAmt = creditAmount;
        }
        else if (maxApprovedAmt == -1.0D)
        {
          approvedAmount = creditAmount;
        }
        else if (creditAmount > maxApprovedAmt)
        {
          calApprovedAmt = maxApprovedAmt;
        }
        else
        {
          calApprovedAmt = creditAmount;
        }

        double mcgsAmount = 0.0D;

        if (application.getScheme().equals("MCGS"))
        {
          double partBankAmount = getPartBankAmount(application.getMliID().substring(0, 4), application.getMliID().substring(4, 8), application.getMliID().substring(8, 12));
          double mcgsApprovedAmt = getMcgsApprovedAmount(application.getMliID().substring(0, 4), application.getMliID().substring(4, 8), application.getMliID().substring(8, 12));
          Log.log(4, "ApplicationProcessor", "calApprovedAmount", "partBankAmount:" + partBankAmount);
          Log.log(4, "ApplicationProcessor", "calApprovedAmount", "mcgsApprovedAmt:" + mcgsApprovedAmt);
          if ((partBankAmount != 0.0D) && (mcgsApprovedAmt != 0.0D))
          {
            if ((mcgsApprovedAmt > partBankAmount) || (mcgsApprovedAmt == partBankAmount))
            {
              mcgsAmount = calApprovedAmt;
            }
            else if (partBankAmount > mcgsApprovedAmt)
            {
              double diffMcgsAmount = partBankAmount - mcgsApprovedAmt;
              Log.log(4, "ApplicationProcessor", "calApprovedAmount", "diffMcgsAmount:" + diffMcgsAmount);
              if (calApprovedAmt > diffMcgsAmount)
              {
                mcgsAmount = calApprovedAmt - diffMcgsAmount;
              }
              else if (calApprovedAmt < diffMcgsAmount)
              {
                mcgsAmount = calApprovedAmt;
              }
            }

            calApprovedAmt = mcgsAmount;
          }
        }

        Log.log(4, "ApplicationProcessor", "calApprovedAmount", "calApprovedAmt:" + calApprovedAmt);
      }

      loanType = null;
      subScheme = null;
      Log.log(4, "ApplicationProcessor", "calApprovedAmount", "calApprovedAmt 1:" + calApprovedAmt);
    }
    catch (Exception e)
    {
      Log.log(2, "ApplicationProcessor", "calApprovedAmount", e.getMessage());
      Log.logException(e);
    }

    termLoan = null;
    workingCapital = null;
    rpProcessor = null;

    return calApprovedAmt;
  }

  public ArrayList getSsiRefNosForMcgf(String memberId)
    throws DatabaseException
  {
    Log.log(4, "ApplicationProcessor", "getSsiRefNosForMcgf", "Entered");

    ArrayList ssiRefNosList = this.appDAO.getSsiRefNosForMcgf(memberId);

    Log.log(4, "ApplicationProcess1or", "getSsiRefNosForMcgf", "Exited");

    return ssiRefNosList;
  }

  public ArrayList getDtlForBIDMem(String borrowerId, String bankId, String zoneId, String branchId)
    throws DatabaseException, NoApplicationFoundException
  {
    Log.log(4, "ApplicationProcessor", "getDtlForBIDMem", "Entered");

    ArrayList cgpanAppRefNos = this.appDAO.getDtlForBIDMem(borrowerId, bankId, zoneId, branchId);

    Log.log(4, "ApplicationProcessor", "getDtlForBIDMem", "Exited");

    return cgpanAppRefNos;
  }

  public void updatePendingRejectedStatus(Application application, String userId)
    throws DatabaseException
  {
    this.appDAO.updatePendingRejectedStatus(application, userId);
  }

  public Application getOldApplication(String mliID, String cgpan, String appRefNo)
    throws NoApplicationFoundException, DatabaseException
  {
    Log.log(4, "ApplicationProcessor", "getApplication", "Entered");

    Application application = null;

    Log.log(5, "ApplicationProcessor", "getApplication", "Value of CGPAN:" + cgpan);

    Log.log(5, "ApplicationProcessor", "getApplication", "Value of App Ref No:" + appRefNo);

    if ((!cgpan.equals("")) || (!appRefNo.equals("")))
    {
      application = this.appDAO.getOldApplication(mliID, cgpan, appRefNo);

      if (application == null) {
        throw new NoApplicationFoundException("No Application Found");
      }

    }

    Log.log(5, "ApplicationProcessor", "getApplication", "Value of Application :" + application);

    Log.log(4, "ApplicationProcessor", "getApplication", "Exited");

    return application;
  }

  public void updateEnhanceAppStatus(Application application, String userId)
    throws DatabaseException
  {
    this.appDAO.updateEnhanceAppStatus(application, userId);
  }

  public void updateRejectStatus(Application application, String userId)
    throws DatabaseException
  {
    this.appDAO.updateRejectStatus(application, userId);
  }

  public double getTotalApprovedAmt(Application application) throws DatabaseException
  {
    double totalApprovedAmt = this.appDAO.getTotalApprovedAmt(application);

    return totalApprovedAmt;
  }

  public int getClaimCount(String bid) throws DatabaseException
  {
    int claimCount = this.appDAO.getClaimCount(bid);

    return claimCount;
  }
  //DUPITPAN
  public int getDupItpanCount(String ssiITPan,String cpITPAN) throws DatabaseException
  {
    int dupItpanCnt = this.appDAO.getDupItpanCount(ssiITPan,cpITPAN);

    return dupItpanCnt;
  }

  public double getCorpusContAmt(int ssiRefNumber) throws DatabaseException
  {
    double corpusContAmt = this.appDAO.getCorpusContAmt(ssiRefNumber);

    return corpusContAmt;
  }

  public double getPartBankAmount(String bnkId, String zoneId, String branchId) throws DatabaseException
  {
    double partBankAmount = this.appDAO.getPartBankAmount(bnkId, zoneId, branchId);

    return partBankAmount;
  }

  public double getMcgsApprovedAmount(String bnkId, String zoneId, String branchId) throws DatabaseException
  {
    double mcgsApprovedAmt = this.appDAO.getMcgsApprovedAmount(bnkId, zoneId, branchId);

    return mcgsApprovedAmt;
  }

  public ArrayList getCountForTCConv() throws DatabaseException
  {
    ArrayList countTCApp = this.appDAO.getCountForTCConv();

    return countTCApp;
  }

  public ArrayList getCountForWCConv() throws DatabaseException
  {
    ArrayList countWCApp = this.appDAO.getCountForWCConv();

    return countWCApp;
  }

  public void updateTCConv(Application application, int appSSIRef) throws DatabaseException
  {
    this.appDAO.updateTCConv(application, appSSIRef);
  }

  public void updateWCConv(Application application, int appSSIRef)
    throws DatabaseException
  {
    this.appDAO.updateWCConv(application, appSSIRef);
  }

  public ArrayList getCountForDanGen(String appRefNo)
    throws DatabaseException
  {
    ArrayList countAmount = this.appDAO.getCountForDanGen(appRefNo);

    return countAmount;
  }

  public void generateDanForEnhance(Application application, User user)
    throws DatabaseException
  {
    this.appDAO.generateDanForEnhance(application, user);
  }

  public double getBalanceApprovedAmt(Application application)
    throws DatabaseException
  {
    return this.appDAO.getBalanceApprovedAmt(application);
  }

  public void updateAppCgpanReference(Application application) throws DatabaseException
  {
    this.appDAO.updateAppCgpanReference(application);
  }
  
 //DKR insertXLSFileData XLS UPLOAD TC
	public HashMap insertXLSFileData(FormFile file, User user)
			throws SQLException, IOException, FileNotFoundException, Exception {

		String senDate = "2018-12-01";		
		boolean datFlag4UI1 = false; 
		int financialRecordFlag=0;
		HashMap apptypes = null;
		HashMap dupApp = new HashMap();
		ArrayList validapps = new ArrayList();		
		ArrayList invalidapps = new ArrayList();
		FileInputStream fis = null;
		DataInputStream dis = null;
		InputStream is = null;
		ArrayList bankAppRefNos = new ArrayList();
		ClaimsProcessor cpProcessor = new ClaimsProcessor();
		Registration registration = new Registration();
		ApplicationDAO applicaitonDAO = new ApplicationDAO();
		// DKR Added GSTNO
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");	
		Date date2 = sdf.parse(senDate); 
		
		String bankId = user.getBankId();
		
		double exposureLimit = applicaitonDAO
				.getExposureLimit(user.getBankId());
		double maxExposureLimit = applicaitonDAO.getMaxExposureLimit(user
				.getBankId());

		if ((exposureLimit > 0.0D)
				&& (exposureLimit / 10000000.0D >= maxExposureLimit)) {
			throw new DatabaseException(
					"The Maximum Limit of Guarantee Approved Amount should be less than "
							+ maxExposureLimit);
		}
		String classificationofMLI = applicaitonDAO.getClassificationMLI(user
				.getBankId());
		// double handloomMaxValue = 200000.0D;
		int inthandloomMaxValue = 200000;
		// double maxValue = 10000000.0D;
		int intmaxValue = 10000000;
		// double rrbValue = 5000000.0D;
		int intrrbValue = 5000000;
		String memberId = user.getBankId() + user.getZoneId()
				+ user.getBranchId();
		bankAppRefNos = applicaitonDAO.getBankAppRefNumbers(memberId);

		try {
			is = (InputStream) file.getInputStream();
			HSSFWorkbook book;
			book = new HSSFWorkbook(is);
			HSSFSheet sheet = book.getSheetAt(0);
		//	System.out.println(sheet.getSheetName()+"\t sheet row"+sheet.getLastRowNum());

			Iterator rowItr = sheet.iterator();
			int i = 0;
			int j = 0;
			int counterForRowLimit = 0;
			while (rowItr.hasNext()) {
				// if(counterForRowLimit < 50 )
				// {
				HSSFRow row = (HSSFRow) rowItr.next();
				// HSSFCell celVal [] = new HSSFCell[row.getLastCellNum()];
		//		HSSFCell celVal[] = new HSSFCell[128];
				HSSFCell celVal[] = new HSSFCell[168];                  //156 colt
				// System.out.println(celVal.length);
				// Iterator cellItr = row.cellIterator();
				// if (cellItr.hasNext())
				for (int k = 0; k < 168; k++) {
					HSSFCell cellV = row.getCell(k) != null ? row.getCell(k): null;
					celVal[k] = cellV != null ? cellV : null;					
					
				}// end for loop
					// System.out.println("cell val: "+Arrays.asList(celVal));
					// System.out.println("celVal string format"+celVal.toString());
				List CrunchifyList = Arrays.asList(celVal);
				int counter = 0;
				for (int i1 = 0; i1 < CrunchifyList.size(); i1++) {
					if (CrunchifyList.get(i1) != null) {
						String a = CrunchifyList.get(i1).toString().trim();
						if (a.equals("")) {
							System.out.println(a+""+ +i1);
							counter++;
						}
					}
				}

				if (counter < 168 && counterForRowLimit < 50) {      
					//if (counter < 156 && counterForRowLimit < 500) {    //Change COUNTER 50
					HSSFCell LOAN_TYPE = celVal[0];
					HSSFCell BankAppRefNum = celVal[1];					
					HSSFCell BranchName = celVal[2];
					HSSFCell BranchCode = celVal[3];
					HSSFCell AlreadyCoveredInCgtmse = celVal[4];
					HSSFCell Constitution = celVal[5];
					HSSFCell SsiTitle = celVal[6];
					HSSFCell SsiName = celVal[7];
					HSSFCell SsiAddress = celVal[8];
					HSSFCell SsiState = celVal[9];
					HSSFCell SsiDistrict = celVal[10];
					HSSFCell SsiCity = celVal[11];
					HSSFCell PinCode = celVal[12];
					HSSFCell ItpanFirm = celVal[13];
					HSSFCell SsiRegnNo = celVal[14];
					HSSFCell IndustryNature = celVal[15];
					HSSFCell IndustrySector = celVal[16];
					HSSFCell TypeOfActivity = celVal[17];
					HSSFCell NoOfEmpl = celVal[18];
					HSSFCell ProjectedSales = celVal[19];
					HSSFCell ProjectedExports = celVal[20];
					HSSFCell PromTitle = celVal[21];
					HSSFCell PromFirstName = celVal[22];
					HSSFCell PromMiddleName = celVal[23];    //e
					HSSFCell PromLastName = celVal[24];                   //e
					HSSFCell PromParentTitle = celVal[25];
					HSSFCell PromParentFirstName = celVal[26];       //e
					HSSFCell PromParentMiddleName = celVal[27];           //e
					HSSFCell PromParentLastName = celVal[28];                 //e
					HSSFCell PromGender = celVal[29];
					HSSFCell PromItpan = celVal[30];
					HSSFCell IsMinorityCommunity = celVal[31];
					HSSFCell PromDob = celVal[32];
					HSSFCell LegalType = celVal[33];
					HSSFCell LegalIdNumber = celVal[34];
					HSSFCell PmrFirstName = celVal[35];
					HSSFCell PmrFirstDob = celVal[36];
					HSSFCell PmrFirstItpan = celVal[37];
					HSSFCell PmrSecondName = celVal[38];
					HSSFCell PmrSecondDob = celVal[39];
					HSSFCell PmrSecondItpan = celVal[40];
					HSSFCell PmrThirdName = celVal[41];
					HSSFCell PmrThirdDob = celVal[42];
					HSSFCell PmrThirdItpan = celVal[43];
					HSSFCell IsNewUnit = celVal[44];
					HSSFCell IsWomenOperated = celVal[45];
					HSSFCell IsMse = celVal[46];
					HSSFCell MicroEnterprise = celVal[47];
					HSSFCell CollateralSecurityTaken = celVal[48];
					HSSFCell ThirdPartySecurityTaken = celVal[49];
					HSSFCell IsDcHandcraft = celVal[50];
					HSSFCell IsDcReimbursement = celVal[51];
					HSSFCell AccIcard = celVal[52];
					HSSFCell AccIcardIssueDt = celVal[53];
					HSSFCell IsHandicraft = celVal[54];
					HSSFCell JointFinanceFlag = celVal[55];
					HSSFCell JointCgpan = celVal[56];
					HSSFCell DcHandloomsFlag = celVal[57];
					HSSFCell DcWeaverCreditSchemeFlag = celVal[58];
					HSSFCell DcHanloomsCheck = celVal[59];
					HSSFCell HandloomSchemeName = celVal[60];
					HSSFCell InternalRating = celVal[61];
					HSSFCell InternalRatingProposal = celVal[62];
					HSSFCell InvestmentGrade = celVal[63];
					HSSFCell SubsidyName = celVal[64];
					HSSFCell SubsidyOther = celVal[65];
					HSSFCell TlSanctionedAmount = celVal[66];
					HSSFCell TlPromoterContribution = celVal[67];
					HSSFCell TlEquitySupport = celVal[68];
					HSSFCell TlOthers = celVal[69];
					HSSFCell TlSanctionDt = celVal[70];
					HSSFCell TlCreditToGuarantee = celVal[71];
					HSSFCell TlAmtDisbursed = celVal[72];
					HSSFCell TlFirstDisbursementDt = celVal[73];
					HSSFCell TlLastDisbursementDt = celVal[74];
					HSSFCell TlBasrRateType = celVal[75];
					HSSFCell TlBaseRate = celVal[76];
					HSSFCell TlInterestType = celVal[77];
					HSSFCell TlInterestRate = celVal[78];
					HSSFCell TlTenure = celVal[79];
					HSSFCell TlPrincipalMoratarium = celVal[80];
					HSSFCell TlInterestMoratarium = celVal[81];
					HSSFCell Periodicity = celVal[82];
					HSSFCell NoOfInstallments = celVal[83];
					HSSFCell TlFirstInstalmentDueDt = celVal[84];
					HSSFCell TlOutstandingAmount = celVal[85];
					HSSFCell TlOutstandingDt = celVal[86];
					HSSFCell WcFbLimitSanctioned = celVal[87];
					HSSFCell WcFbCreditToGuarantee = celVal[88];
					HSSFCell WcNfbLimitSanctioned = celVal[89];
					HSSFCell WcNfbCreditToGuarantee = celVal[90];
					HSSFCell WcTlUnderMargin = celVal[91];
					HSSFCell WcPromoterContribution = celVal[92];
					HSSFCell WcEquitySupport = celVal[93];
					HSSFCell WcOthers = celVal[94];
					HSSFCell WcPlrType = celVal[95];
					HSSFCell WcPlr = celVal[96];
					HSSFCell WcInterestType = celVal[97];
					HSSFCell WcFbInterest = celVal[98];
					HSSFCell WcFbLimitSanctionedDt = celVal[99];
					HSSFCell WcNfbCommission = celVal[100];
					HSSFCell WcNfbLimitSanctionedDt = celVal[101];
					HSSFCell FbOutstandingAmount = celVal[102];
					HSSFCell FbOutstandingDt = celVal[103];
					HSSFCell NfbOutstandingAmt = celVal[104];
					HSSFCell NfbOutstandingDt = celVal[105];
					HSSFCell WcDisbursementAmt = celVal[106];
					HSSFCell WcFirstDisbursementDt = celVal[107];
					HSSFCell WcLastDisbursementDt = celVal[108];
					HSSFCell LoanTerminationDt = celVal[109];
					HSSFCell SpreadOverPlr = celVal[110];
					HSSFCell RepaymentEqual = celVal[111];
					HSSFCell TangibleNetworth = celVal[112];
					HSSFCell FixedAssetCoverageRatio = celVal[113];
					HSSFCell CurrentRatio = celVal[114];
					HSSFCell MinimumDscr = celVal[115];
					HSSFCell AverageDscr = celVal[116];
					HSSFCell IsPrimarySecurity = celVal[117];
					HSSFCell Remarks = celVal[118];
					HSSFCell ConditionsAccepted = celVal[119];
					HSSFCell udyogAdharNo = celVal[120];
					HSSFCell bankAccNo = celVal[121];			// DKR	GST Added   first
					HSSFCell stateCode = celVal[122];					
					HSSFCell hybridSecFlag = celVal[123];
					HSSFCell movCollactSecAmt = celVal[124];
					HSSFCell imMovCollactSecAmt = celVal[125];
					HSSFCell chiefPromtMob = celVal[126];
					HSSFCell exposureID = celVal[127];
					
					HSSFCell promDirDefaltFlg =  celVal[128];
					 HSSFCell credBureKeyPromScor = celVal[129];
					 HSSFCell credBurePromScor2 =  celVal[130];
					 HSSFCell credBurePromScor3 =  celVal[131];
					 HSSFCell credBurePromScor4 = celVal[132];
					 HSSFCell credBurePromScor5 = celVal[133];
					 HSSFCell credBureName1 =  celVal[134];
					 HSSFCell credBureName2 =  celVal[135];
					 HSSFCell credBureName3 =  celVal[136];
					 HSSFCell credBureName4 = 	 celVal[137];
					 HSSFCell credBureName5 =	  celVal[138];
					 HSSFCell cibilFirmMsmeRank =    celVal[139];
					 HSSFCell expCommerScor =       celVal[140];
					 HSSFCell promBorrNetWorth =     celVal[141];
					 HSSFCell promContribution =     celVal[142];
					 HSSFCell promGAssoNPA1YrFlg = celVal[143];
					 HSSFCell promBussExpYr =         celVal[144];
					 HSSFCell salesRevenue =          celVal[145];
					 HSSFCell taxPBIT =  celVal[146];
					 HSSFCell interestPayment =       celVal[147];
					 HSSFCell taxCurrentProvisionAmt =celVal[148]; 
					HSSFCell totCurrentAssets =    celVal[149];
					HSSFCell totCurrentLiability = celVal[150];
					HSSFCell totTermLiability =    celVal[151];
					HSSFCell exuityCapital =       celVal[152];
					HSSFCell preferenceCapital =   celVal[153];
					HSSFCell reservesSurplus =     celVal[154];
					HSSFCell repaymentDueNyrAmt =  celVal[155];						
					  HSSFCell existGreenFldUnitType= celVal[156];             				
					  HSSFCell opratIncome= celVal[157];            			
					  HSSFCell profAftTax= celVal[158];                        
					  HSSFCell networth= celVal[159];  
					  HSSFCell debitEqtRatioUnt= celVal[160];
					  HSSFCell debitSrvCoverageRatioTl= celVal[161];
					  HSSFCell currentRatioWc= celVal[162];  			
					  HSSFCell debitEqtRatio= celVal[163];		
					  HSSFCell debitSrvCoverageRatio= celVal[164];		
					  HSSFCell currentRatios= celVal[165];	   					
					  HSSFCell creditBureauChiefPromScor= celVal[166];			
					  HSSFCell totalAssets= celVal[167];
					
					  /*if (!counterForRowLimit.hasNext()) 
					  { 
					   throw new MessageException(
					      "Last coloumn of the sheet got modified. Please copy content from the file and paste on new excel file."
					    );
					  } HSSFCell cell120 = (HSSFCell) cellItr.next();*/
					 // CONDITIONS_ACCEPTED
					 
					/*// Added by DKR
					 switch (cellV.getCellType()) {
		                case HSSFCell.CELL_TYPE_STRING:
		                    System.out.println(cellV.getRichStringCellValue().getString());
		                    break;
		                case HSSFCell.CELL_TYPE_NUMERIC:
		                    if (HSSFDateUtil.isCellDateFormatted(cellV)) {
		                        System.out.println(cellV.getDateCellValue());
		                    } else {
		                        System.out.println(cellV.getNumericCellValue());
		                    }
		                    break;
		                case HSSFCell.CELL_TYPE_BOOLEAN:
		                    System.out.println(cellV.getBooleanCellValue());
		                    break;
		                case HSSFCell.CELL_TYPE_FORMULA:
		                    System.out.println(cellV.getCellFormula());
		                    break;
		                case HSSFCell.CELL_TYPE_BLANK:
		                    System.out.println("case::: "+cellV.toString());
		                    break;
		                default:
		                    System.out.println();
		            }
                    // Addd
					 * Validation work goes here. Filter invalid apps. 1. Value
					 * checking 2. Mandatory fields checking 3. Business rule
					 * checking
					 */

					if (i > 0) {
						String app_loan_type = "";
						int errorFieldCount = 0;
						String scheme_id = "";
						String ITPAN_FIRM="";
						String SSI_REGN_NO="";
						// TL Fields
						String tl_interest_type = "F";// floating
						String tl_plr_type = "";
						double app_tl_sanction_amt = 0.0;
						int intApp_tl_sanction_amt = 0;
						double app_tl_credit_amt = 0.0;
						int intApp_tl_credit_amt = 0;
						int intFbOutstandingAmount = 0;
						double tl_os_Amt = 0.0;
						double tl_plr = 0.0;
						double tl_interest = 0.0;
						int IntTlOutstandingAmount = 0;
						double tl_prom_cont = 0.0;
						double tl_eq_support = 0.0;
						double tl_other = 0.0;
						Date app_tl_sanction_dt = null;
						Date PmrFirstDobDt = null;
						Date PmrSecondDobDt = null;
						Date appTlFirstDisbursementDt = null;
						Date appTlLastDisbursementDt = null;
						int intnFbOutstandingAmount = 0;
						Date tl_os_dt = null;
						Date tl_first_inst_du_dt = null;
						int intTlAmtDisbursed = 0;
						// WC Fields
						String wc_plr_type = "";
						String wc_interest_type = "F";// floating
						double app_wc_fb_sanction_amt = 0.0;
						int intApp_wc_fb_sanction_amt = 0;
						double app_wc_nfb_sanction_amt = 0.0;
						int intApp_wc_nfb_sanction_amt = 0;
						double app_wc_fb_credit_amt = 0.0;
						int intApp_wc_fb_credit_amt = 0;
						double app_wc_nfb_credit_amt = 0.0;
						int intApp_wc_nfb_credit_amt = 0;
						double wc_fb_os_amt = 0.0;
						double wc_nfb_os_amt = 0.0;
						double wc_interest = 0.0;
						double wc_commission = 0.0;
						double wc_plr = 0.0;
						double sprade_over_plr = 0.0;
						double wc_prom_cont = 0.0;
						int intwc_prom_cont = 0;
						double wc_eq_support = 0.0;
						int intwc_eq_support = 0;
						double intFixedAssetCoverageRatio = 0.00;
						double intCurrentRatio = 0.00;
						double wc_other = 0.0;
						int intwc_other = 0;
						double credittoguaranteeamount = 0.0;
						int intcredittoguaranteeamount = 0;
						Date app_wc_fb_sanction_dt = null;
						Date app_wc_nfb_sanction_dt = null;
						Date app_wc_fb_os_dt = null;
						Date wc_fb_os_dt = null;
						Date wc_nfb_os_dt = null;
						Date DateWcFirstDisbursement = null;
						Date DateWcLastDisbursement = null;

						// double prevCoveredAmount = 0.0;
						int intprevCoveredAmount = 0;
						String pincode = "";
						ArrayList constitutions = new ArrayList();
						constitutions.add("PROPRIETARY/INDIVIDUAL");
						constitutions.add("PARTNERSHIP/LIMITED LIABILITY PARTNERSHIP");
						constitutions.add("PRIVATE");
						constitutions.add("PUBLIC");
						constitutions.add("HUF");
						constitutions.add("TRUST");
						constitutions.add("SOCIETY/CO OP");
						ArrayList industryNatureList = null;
						ArrayList industrySectors = null;
						ArrayList statesList = null;
						ArrayList districtList = null;
						boolean isValidIndustry = false;
						boolean isValidSector = false;
						boolean isValidState = false;
						boolean isValidDistrict = false;
						boolean isValidCity = false;
						String industryNature = "";
						String sector = "";
						String state = "";
						String district = "";
						String city = "";
						String dcHandicraft = "";
						String dcHandicraftStatus = "";
						String dcHandlooms = "";
						Date icardDt = null;
						Date app_expiry_dt = null;
						Date prom_dob = null;
						String jointFinanceFlag = "";
						String jointCGPAN = "";
						int periodicity = 0;
						int installmentNos = 0;
						int employeeNos = 0;
						int tenure = 0;
						int principal_mora = 0;
						int intWcDisbursementAmt = 0;
						String cgbid = "";
						ArrayList errors = new ArrayList();
						double total_sanction_amt = 0.0;
						Administrator admin = new Administrator();
						ArrayList socialList = admin.getAllSocialCategories();
					//	ArrayList socialList = admin.getAllSocialCategories();
						String udyogAdharNos = "";			
						String bankAcNo="";
						String stateCodes ="";						
						String gstNo = "";
						String expoIdds="";
						double expoAmt=0.0;
						String hybridSec_Flag = "";
						double movCollactSec_Amt = 0.0;
						double imMovCollactSec_Amt = 0.0;
						Long chiefPromt_Mob = 0L;
						
						 String d_promDirDefaltFlg="";       
						 Integer d_credBureKeyPromScor=0;       
						 int d_credBurePromScor2=0;         
						 int d_credBurePromScor3=0;         
						 int d_credBurePromScor4=0;         
						 int d_credBurePromScor5=0;         
						 String d_credBureName1 = "";          
						 String d_credBureName2= "";  	   
						 String d_credBureName3= "";		   
						 String d_credBureName4= ""; 		   
						 String d_credBureName5= "";		   
						 int d_cibilFirmMsmeRank=0;         
						 int d_expCommerScor=0;             
						 float d_promBorrNetWorth=0.0f;        
						 int d_promContribution=0;          
						 String d_promGAssoNPA1YrFlg = "";     
						 int d_promBussExpYr=0;             
						 float d_salesRevenue=0.0f;             
						 float d_taxPBIT=0.0f;                  
						 float d_interestPayment=0.0f;          
						 float d_taxCurrentProvisionAmt=0.0f;   
						 float d_totCurrentAssets=0.0f;         
						 float d_totCurrentLiability=0.0f;      
						 float d_totTermLiability=0.0f;         
						 float d_exuityCapital=0.0f;            
						 float d_preferenceCapital=0.0f;        
						 float d_reservesSurplus=0.0f;          
						 float d_repaymentDueNyrAmt=0.0f;
						  
						   String d_existGreenFldUnitType="";               				
						   float d_opratIncome=0.0f;               			
						   float d_profAftTax=0.0f;                           
						   float d_networth=0.0f;                  			
						   float d_debitEqtRatioUnt=0.0f;
						   float d_debitSrvCoverageRatioTl=0.0f;
						   float d_currentRatioWc=0.0f;	
						   float d_debitEqtRatio=0.0f;	
						   float d_debitSrvCoverageRatio=0.0f;		
						   float d_currentRatios=0.0f;   					
						   int d_creditBureauChiefPromScor=0;			
						   float d_totalAssets=0.0f;	
						 
						 
							 
						/********** START VALIDATIONS ********/
						// validateNumericField() checks numeric values like
						// amount
						// and date
						// validateTextField() checks values like string or
						// character
						// validateFlags() checks flag Y or N or blank
						boolean isValidType = false;
						errors.add((i + 1) + ".");
						// isValidType = validateNumericField(cell1);// scheme
						// id
						/*
						 * if (isValidType) { int id = new
						 * Double(cell1.getNumericCellValue()) .intValue();
						 * scheme_id = String.valueOf(id); } else {
						 * errorFieldCount++; errors.add("(" + errorFieldCount +
						 * ")Invalid scheme Id"); }
						 */

						// start LOAN_TYPE = celVal[0];

						if (celVal[0] != null) {
							isValidType = validateTextFieldMandatory(celVal[0]);// loan
																				// type
							if (isValidType) {
								if (celVal[0].getStringCellValue()
										.equalsIgnoreCase("TC")
										|| celVal[0].getStringCellValue().equalsIgnoreCase("WC")) {
									app_loan_type = celVal[0]
											.getStringCellValue();
								} else {
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ")Invalid Loan Type");
								}
							} else {
								if (celVal[0].toString().trim().length() == 0) {

									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ")Loan Type is Required");
								} else {
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ")Loan Type is Invalid");
								}

							}
						} else {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")Loan Type is Required");
						}
						// end LOAN_TYPE = celVal[0];

						// start BankAppRefNum = celVal[1];

						String strBankRefNo = "";
						if (celVal[1] != null) {
							strBankRefNo = celVal[1].toString();
							System.out.println("strBankRefNo "+strBankRefNo);
							
							if(strBankRefNo.startsWith("\""))
							{
								strBankRefNo=strBankRefNo.replace("\"", "");
							}
							System.out.println("strBankRefNo== "+strBankRefNo);
							if (validateNumericField(celVal[1])) {
								long LocalIntTlSanctionedAmount = (long) celVal[1]
										.getNumericCellValue();
								strBankRefNo = Long
										.toString(LocalIntTlSanctionedAmount);
							}

							//System.out
								//	.println(strBankRefNo.length()
									//		+ " celVal[1] TlSanctionedAmount LocalIntTlSanctionedAmount "
									//		+ strBankRefNo);
							
							if (strBankRefNo.length() > 16
									|| strBankRefNo.length() < 10) {
								// isValidType = false;
								errorFieldCount++;
								errors.add("("
										+ errorFieldCount
										+ ")Bank Reference Number should not less than 10 char and greater than 16 char");
							} else {
								if (celVal[1].toString().trim().length() != 0) {
									if (bankAppRefNos.contains(strBankRefNo)) {
										errorFieldCount++;
										errors.add("("
												+ errorFieldCount
												+ ")Duplicate Bank Application Reference Number : "
												+ strBankRefNo);
									} else {
										bankAppRefNos.add(strBankRefNo);
									}
								} else {
									errorFieldCount++;
									errors.add("("
											+ errorFieldCount
											+ ")Bank Reference Number is Required");
								}
							}

						} else {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")Bank Reference Number is Required");
						}
						// end BankAppRefNum = celVal[1];

						if (celVal[2] != null) // branchName
						{
							isValidType = validateTextFieldMandatory(celVal[2]);// branchName

							if (isValidType) {

								if (celVal[2].getCellType() != Cell.CELL_TYPE_BLANK) // branchName
								{
									isValidType = validateTextField(celVal[2]);
									if (!isValidType) {
										errorFieldCount++;
										errors.add("(" + errorFieldCount
												+ ")Invalid Branch Name");
									}
								}
							} else {
								if (celVal[2].toString().trim().length() == 0) {
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ")Branch Name is Required");
								} else {
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ")Branch Name is Invalid");
								}
							}

						} else {

							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")Branch Name is Invalid");

						}

						if (celVal[3] != null) // Branch Code
						{
							String strBranchCode = celVal[3].toString();
						System.out.println("celVal[3] strBranchCode "
									+ strBranchCode);
							System.out.println(celVal[3].toString().trim()+ "celVal[3].toString().trim().length() "	+ celVal[3].toString().trim().length());
							if (celVal[3].toString().trim().length() > 10) {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")Branch Code is Invalid");
							} else {
								if (celVal[3].toString().trim().length() != 0) {
									Pattern p = Pattern.compile("[^A-Za-z0-9]");
									Matcher m = p.matcher(celVal[3].toString()
											.trim());
									boolean b = m.matches();

									if (b == true) {
										System.out.println("There is a sp. character in my string");
										errorFieldCount++;
										errors.add("("+ errorFieldCount	+ ")Branch Code should not contains special character");
									} else {
										System.out.println("There is no sp. char.");
									}
								}
							}
						}

						String flagValue = ""; // AlreadyCoveredInCgtmse

						if (celVal[4] != null) {
							if (celVal[4].toString().trim().length() > 1) {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")Already Covered Flag is Invalid");
							} else if (celVal[4].toString().trim().length() == 0) {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")Already Covered Flag is Required");
							} else if (celVal[4].toString().trim().length() == 1) {
								if (celVal[4].toString().equalsIgnoreCase("Y")
										|| celVal[4].toString()
												.equalsIgnoreCase("N")) {
									flagValue = celVal[4].getStringCellValue();

								} else {
									errorFieldCount++;
									errors.add("("
											+ errorFieldCount
											+ ")Already Covered Flag is Invalid");
								}

							}

						} else {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")Already Covered Flag is Invalid");
						}

						if (celVal[5] != null) {
							isValidType = validateTextField(celVal[5]); // Constitution
							if (isValidType) {
								if (!constitutions.contains(celVal[5]
										.getStringCellValue().toUpperCase())) {
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ")Invalid Constitution");
								}
							} else {
								if (celVal[5].toString().trim().length() == 0) {
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ")Constitution is Required");
								} else {
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ")Constitution is Invalid");
								}

							}
						} else {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")Constitution is Required");
						}

						if (celVal[6] != null) {
							isValidType = validateTextField(celVal[6]); // SsiTitle
							if (isValidType) {
								String ut = celVal[6].getStringCellValue();
								System.out.println("SsiTitle length "+ut.length());
								
								if (!(ut.equalsIgnoreCase("M/S")
										|| ut.equalsIgnoreCase("Shri")
										|| ut.equalsIgnoreCase("Smt") || ut
										.equalsIgnoreCase("Ku") ||  ut
										.equalsIgnoreCase("MR"))) {
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ")Invalid Unit Type [SSI TITLE]");
								}
							} else {
								if (celVal[6].toString().trim().length() == 0) {
									errorFieldCount++;
									errors.add("("
											+ errorFieldCount
											+ ")Unit Type[SSI TITLE] is Required");
								} else {
									errorFieldCount++;
									errors.add("("
											+ errorFieldCount
											+ ")Unit Type[SSI TITLE] is Invalid");
								}

							}
						} else {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")Unit Type[SSI TITLE] is Invalid");
						}

						if (celVal[7] != null) {
							isValidType = validateTextField(celVal[7]); // SsiName
							if (!isValidType) {
								if (celVal[7].toString().trim().length() == 0) {
									errorFieldCount++;
									errors.add("("
											+ errorFieldCount
											+ ")Borrower Name [SSI NAME] is Required");
								} else {
									errorFieldCount++;
									errors.add("("
											+ errorFieldCount
											+ ")Borrower Name [SSI NAME] is Invalid");
								}

							}
						} else {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")Borrower Name [SSI NAME] is Invalid");
						}

						if (celVal[8] != null) {
							isValidType = validateTextField(celVal[8]); // SsiAddress
							if (!isValidType) {
								if (celVal[8].toString().trim().length() == 0) {
									errorFieldCount++;
									errors.add("("
											+ errorFieldCount
											+ ")Unit Address [SSI ADDRESS] is Required");
								} else {
									errorFieldCount++;
									errors.add("("
											+ errorFieldCount
											+ ")Unit Address [SSI ADDRESS] is Invalid");
								}

							}
						} else {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")Unit Address [SSI ADDRESS] is Invalid");
						}

						if (celVal[9] != null) {
							isValidType = validateTextField(celVal[9]); // SsiState
							if (isValidType) {
								statesList = (ArrayList) admin.getAllStates();
								/*Diksha 22/05/2017
								 * if(statesList.size()==0)
							      {
							    	  LogClass.StepWritter("Null stateslist in ==Line4160== in insertXLSFileData method in 'ApplicationProcesser'");
							      } end 22/05/2017*/
								state = celVal[9].getStringCellValue()
										.toUpperCase();
								/*Diksha 22/05/2017
								 * if(statesList.size()==0)
								 
					    	      {
					    	    	  LogClass.StepWritter("Null stateslist in ==Line4166== in insertCSVFileData method in 'ApplicationProcessor'statesList==="+statesList);
					    	      }end 22/05/2017*/
								if (statesList.contains(state)) {
									isValidState = true;
								} else {
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ")Invalid Ssi State name");
								}
							} else {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")Ssi State name is Required");
							}
						} else {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")Ssi State name is Required");
						}

						if (celVal[10] != null) {
							isValidType = validateTextField(celVal[10]); // SsiDistrict
							if (isValidType && isValidState) {
								districtList = admin.getAllDistricts(state);
								district = celVal[10].getStringCellValue()
										.toUpperCase();
								if (districtList.contains(district)) {
									isValidDistrict = true;
								} else {
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ")Invalid Ssi District name");
								}
							} else {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")Ssi District name is Required");
							}
						} else {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")Ssi District name is Invalid");
						}

						if (celVal[11] != null) {
							isValidType = validateTextField(celVal[11]); // SsiCity
							if (isValidType) {
								city = celVal[11].getStringCellValue();
							} else {
								if (celVal[11].toString().trim().length() == 0) {
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ")City name is Required");
								} else {
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ")City name is Invalid");
								}

							}
						} else {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")City name is Required");
						}

						
						if(celVal[12]!=null)
						{
						isValidType = validateNumericField(celVal[12]); // PinCode
						if (isValidType) {
							double pc = celVal[12].getNumericCellValue();
							int pin = new Double(pc).intValue();
							pincode = String.valueOf(pin);
						//	System.out.println(pc);
						//	System.out.println(pin);
						//	System.out.println(pincode);
							if (pincode.length() != 6) {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")Invalid Pin Code");
							}
						} else {
						//	System.out.println("pincode else");
							if (celVal[12] != null) {
								if (celVal[12].toString().trim().length() > 0) {
							//		System.out.println("pincode invalid");
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ")Pin Code is Invalid");
								}
								if (celVal[12].toString().trim().length() == 0) {
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ")Pin Code is Required");
								//	System.out.println("pincode Required");
								}
							}

						}
						}
						else
						{
							//System.out.println("pincode invalid2");
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")Pin Code is Required");
						}
						if(celVal[13]==null)
						{
							System.out.println(celVal[13]+" ITPAN_FIRM ");
						}
						
						if (celVal[13] != null ) // ITPAN_FIRM
						{
							if(celVal[13].toString().trim().length()!=0)
							{
								isValidType = validateTextField(celVal[13]); 
								if (isValidType) {
									isValidType = validateITPAN(celVal[13]
											.getStringCellValue());
									if (!isValidType) {
										errorFieldCount++;
										errors.add("(" + errorFieldCount
												+ ")Invalid ITPAN_FIRM");
									}
									else
									{
										ITPAN_FIRM=celVal[13].toString();
									}
								} else {
										errorFieldCount++;
										errors.add("(" + errorFieldCount
												+ ")ITPAN_FIRM is Invalid");
								}
							}
						}
						//System.out.println("celVal[14].getStringCellValue().......1.............."+celVal[14].getStringCellValue());
						if (celVal[14] != null) {          // SSI_REGN_NO
							if (celVal[14].toString().trim().length() > 25) 
							{
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")SSI_REGN_NO is Invalid");
							}
							else
							{ 								 
								
								SSI_REGN_NO = celVal[14].toString().trim();
							//	long ssiReg = (long)celVal[14].getNumericCellValue();

								//System.out.println(ssiReg+"celVal[14].getNumericCellValue()......66..............."+celVal[14].getNumericCellValue());
								//System.out.println("celVal[14].getStringCellValue()......2..............."+celVal[14].getStringCellValue());
							//	long ssiRegNo = Long.parseLong(celVal[14].getStringCellValue().toString());							
								//SSI_REGN_NO = String.valueOf(ssiReg);// String.valueOf(ssiRegNo);									
							}										 
						}						

						if(celVal[15] != null) // IndustryNature
						{
							isValidType = validateTextField(celVal[15]);
							System.out.println("Industry Nature : "+isValidType);
							if (isValidType) {
								industryNatureList = admin.getAllIndustryNature();								
								for(int v=0; v<industryNatureList.size(); v++)
								{
									industryNatureList.set(v, industryNatureList.get(v).toString().toLowerCase());
								}
								industryNature = celVal[15].getStringCellValue().toLowerCase();
								if (industryNatureList.contains(industryNature)) {
									isValidIndustry = true;
								} else {
																	errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ")Invalid Industry Nature");
								}
							} else {
								//System.out.println("2");
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")Industry Nature is Invalid");
							}
						}
						else
						{
							//	System.out.println("3");
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")Industry Nature is Required");							
						}

						isValidType = validateTextField(celVal[16]); // IndustrySector
						if (isValidType && isValidIndustry) {
							industrySectors = admin
									.getIndustrySectors(industryNature);
							
							for(int k=0; k < industrySectors.size(); k++) {
								industrySectors.set(k, industrySectors.get(k).toString().toLowerCase());
								}
							sector = celVal[16].getStringCellValue().toLowerCase();
							if (industrySectors.contains(sector)) {
								isValidSector = true;
							} else {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")Invalid Industry Sector");
							}
						} else {

							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")Industry Sector is Required");
						}

						if (celVal[17] != null) // TypeOfActivity
						{
							System.out.println(celVal[17].toString() +".......celVal[17] ");
							if (validateTextField(celVal[17])) {
								if (celVal[17].toString().trim().length() > 50) {
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ")Activity Type is Invalid");
								} else if (celVal[17].toString().trim()
										.length() == 0) {
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ")Activity Type is Required");
								}
							} else {
								if (celVal[17].toString().trim().length() == 0) {
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ")Activity Type is Required");
								} else {
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ")Activity Type is Invalid");
								}
							}
						} else {

							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")Activity Type is Required");
						}

						isValidType = validateNumericField(celVal[18]); // NoOfEmpl
						if (isValidType) {
							double empno = celVal[18].getNumericCellValue();
							employeeNos = new Double(empno).intValue();
							if (employeeNos < 1 || employeeNos > 500) {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")Invalid Number of Employees");
							}
						} else {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")Employees number is Required");
						}

						long LocalLongProjectedSales = 0l; // ProjectedSales
						
					System.out.println("ProjectedSales " + isValidType);
						if (celVal[19]!=null) {
							
							isValidType = validateNumericField(celVal[19]);
						//	System.out.println(celVal[19]
						//			+ "ProjectedSales is true "
						//			+ celVal[19].toString().trim().length());
                        System.out.println("celVal[19].getNumericCellValue()>>>>>>>>>>>>>>>>>>8888888>>>>>>>>>>"+celVal[19].getNumericCellValue());
						LocalLongProjectedSales = (long) celVal[19].getNumericCellValue();
						System.out.println("ProjectedSales LocalLongProjectedSales "+ LocalLongProjectedSales);

							if (Long.toString(LocalLongProjectedSales).length() > 13) {
							//	System.out.println("ProjectedSales is  > 15");
								errorFieldCount++;
								errors.add("(" + errorFieldCount+ ")Projected Sales is Invalid");
							} else if (celVal[19].toString().trim().length() == 0) {
								System.out.println("ProjectedSales is  < 15");
								errorFieldCount++;
								errors.add("(" + errorFieldCount+ ")Projected Sales is Required");
							}
						} else {
							if (celVal[19].toString().trim().length() == 0) {
								errorFieldCount++;
								errors.add("(" + errorFieldCount+ ")Projected Sales is Required");
							} else {
								errorFieldCount++;
								errors.add("(" + errorFieldCount+ ")Projected Sales is Invalid");
							}

						}

						long LocalLongPROJECTED_EXPORTS = 0; // PROJECTED_EXPORTS																	
						System.out.println("PROJECTED_EXPORTS " + isValidType);
						if(celVal[20] != null)
						{
							isValidType = validateNumericField(celVal[20]);
							if (isValidType) {
	
								LocalLongPROJECTED_EXPORTS = (long) celVal[20]
										.getNumericCellValue();
								if (Long.toString(LocalLongPROJECTED_EXPORTS)
										.length() > 13) {
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ")PROJECTED EXPORTS is Invalid");
								}
	
							} else {
								if (celVal[20].toString().trim().length() != 0) {
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ")PROJECTED EXPORTS is Invalid");
								}
	
							}
						}						

						isValidType = validateTextField(celVal[21]); // PromTitle
						System.out.println(celVal[17].toString() +".......celVal[17] ");
						if (!isValidType) {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")Promoter title is Required");
						} else {
							String ValPromTitle = celVal[21].toString().trim();
							System.out.println("celVal[21] "+celVal[21]);
							if (ValPromTitle.equalsIgnoreCase(("MR"))
									|| ValPromTitle.equalsIgnoreCase(("MS"))
									|| ValPromTitle.equalsIgnoreCase(("MRS"))  || ValPromTitle.equalsIgnoreCase(("SMT")) || ValPromTitle.equalsIgnoreCase(("KU"))) {

							} else {
								if(celVal[21].toString().trim().length()!=0)
								{
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")Promoter title is Invalid");
								}
							}
						}

						isValidType = validateTextField(celVal[22]); // PromFirstName
						if (!isValidType) {
							if (celVal[22].toString().trim().length() == 0) {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")Promoter first name is Required");
							} else {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")Promoter first name is Invalid");
							}
						} else {
							if (celVal[22].toString().trim().length() > 20) {
								errorFieldCount++;
								errors.add("("
										+ errorFieldCount
										+ ")Promoter first name should not greater than 20 character");
							}
						}
						
						if(celVal[23] != null)
						{
							System.out.println(celVal[23].toString() +".......celVal[23] ");
							isValidType = validateTextField(celVal[23]); // PromMiddleName
							if (!isValidType) {
								if (celVal[23].toString().trim().length() == 0) {
	
								} else {
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ")Promoter Middle name is Invalid");
								}
							} else {
								if (celVal[23].toString().trim().length() > 20) {
									errorFieldCount++;
									errors.add("("
											+ errorFieldCount
											+ ")Promoter Middle name should not greater than 20 character");
								}
							}
						}
						
						isValidType = validateTextField(celVal[24]); // PromLastName
						if (!isValidType) {
							if (celVal[24].toString().trim().length() == 0) {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")Promoter last name is Required");
							} else {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")Promoter last name is Invalid");
							}
						} else {
							if (celVal[24].toString().trim().length() > 20) {
								errorFieldCount++;
								errors.add("("
										+ errorFieldCount
										+ ")Promoter last name should not greater than 20 character");
							}
						}

						// isValidType = validateTextField(celVal[24]);
						// //PromLastName
						// if (!isValidType)
						// {
						// errorFieldCount++;
						// errors.add("(" + errorFieldCount+
						// ")Promoter last name is Required");
						// }

						isValidType = validateTextField(celVal[25]); // Prom
																		// parent
																		// title
						if (!isValidType) {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")Promoter Parent title is Required");
						} else {
							String ValPromTitle = celVal[25].toString().trim();
							if (ValPromTitle.equalsIgnoreCase(("MR"))
									|| ValPromTitle.equalsIgnoreCase(("MS"))
									|| ValPromTitle.equalsIgnoreCase(("MRS"))) {

							} else {

								if(celVal[25].toString().trim().length()!=0)
								{
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")Promoter Parent title is Invalid");
								}
							}
						}

						if (celVal[26] != null) {
							isValidType = validateTextField(celVal[26]); // PromParentFirstName
							if (!isValidType) {
								if (celVal[26].toString().trim().length() == 0) {
									errorFieldCount++;
									errors.add("("
											+ errorFieldCount
											+ ")Promoter's Parent first name is Required");
								} else {
									errorFieldCount++;
									errors.add("("
											+ errorFieldCount
											+ ")Promoter's Parent first name is Invalid");
								}

							} else {
								if (celVal[26].toString().trim().length() > 20) {
									errorFieldCount++;
									errors.add("("
											+ errorFieldCount
											+ ")Promoter's Parent first name should not greater than 20 character");
								}
							}
						} else {
							errorFieldCount++;
							errors.add("("
									+ errorFieldCount
									+ ")Promoter's Parent first name is Required");
						}

						if (celVal[27] != null) {
							isValidType = validateTextField(celVal[27]); // PromParentMiddleName
							if (!isValidType) {
								if (celVal[27].toString().trim().length() != 0) {
									errorFieldCount++;
									errors.add("("
											+ errorFieldCount
											+ ")Promoter's Parent middle name is Invalid");
								}

							} else {
								if (celVal[27].toString().trim().length() > 20) {
									errorFieldCount++;
									errors.add("("
											+ errorFieldCount
											+ ")Promoter's Parent middle name should not greater than 20 character");
								}
							}
						}

						// isValidType = validateTextField(celVal[28]);
						// //PromParentLastName
						// if (!isValidType)
						// {
						// errorFieldCount++;
						// errors.add("("+ errorFieldCount+
						// ")Promoter's Parent last name is Required");
						// }

						if (celVal[28] != null) {
							isValidType = validateTextField(celVal[28]); // PromParentLastName
							if (!isValidType) {
								if (celVal[28].toString().trim().length() == 0) {
									errorFieldCount++;
									errors.add("("
											+ errorFieldCount
											+ ")Promoter's Parent last name is Required");
								} else {
									errorFieldCount++;
									errors.add("("
											+ errorFieldCount
											+ ")Promoter's Parent last name is Invalid");
								}

							} else {
								if (celVal[28].toString().trim().length() > 20) {
									errorFieldCount++;
									errors.add("("
											+ errorFieldCount
											+ ")Promoter's Parent last name should not greater than 20 character");
								}
							}
						} else {
							errorFieldCount++;
							errors.add("("
									+ errorFieldCount
									+ ")Promoter's Parent last name is Required");
						}

						isValidType = validateTextField(celVal[29]); // PromGender
						if (isValidType) {
							String gender = celVal[29].getStringCellValue();
							if (!("M".equalsIgnoreCase(gender) || "F"
									.equalsIgnoreCase(gender))) {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")Invalid value of promoter gender");
							}
						} else {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")Promoter's gender is Required");
						}//						
						
						if (celVal[66] != null && celVal[87]!=null) // PromItpan
						{

							if (validateNumericField(celVal[30])) {
								double a = 0;
								if (app_loan_type.equalsIgnoreCase("TC")) {
									a = celVal[66].getNumericCellValue();
								} else if (app_loan_type.equalsIgnoreCase("WC")) {
									a = celVal[87].getNumericCellValue();
								}
								if (a > 1000000) {
									if (celVal[30].toString().trim().length() != 0) {
										if (validateITPAN(celVal[30].toString())) {

										} else {
											errorFieldCount++;
											errors.add("(" + errorFieldCount
													+ ")ITPAN_FIRM is Invalid");
										}
									} else {
										errorFieldCount++;
										errors.add("(" + errorFieldCount
												+ ")ITPAN_FIRM is Required");
									}
								}
							}
						}
						if (celVal[31] != null) // IS_MINORITY_COMMUNITY
						{
							if (celVal[31].toString().trim().length() > 0) {
								if (!chkYesNoDecision(celVal[31])) {
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ")MINORITY COMMUNITY is Invalid");
								}
							}
						}

						if (celVal[32] != null) // PromDob
						{ 
							System.out.println("PromDob= "+celVal[32]);
							prom_dob = formatDateCell(celVal[32]);
							System.out.println("PromDob== "+prom_dob);
							
							if (prom_dob!=null) 
							{					
									if (!validateFromCurrentDate(prom_dob)) 
									{
										System.out.println("PromDob is greater");
										if (celVal[32].toString().trim().length() != 0) 
										{
											errorFieldCount++;
											errors.add("("
													+ errorFieldCount
													+ ")Promotor Date of birth should not greater than current date ");
										}
									}
									else
									{
										System.out.println("Promoter Dob is smaller");
									}
								
							}
							else
							{
								if (celVal[32].toString().trim().length() != 0) {
									System.out.println("Promoter Dob is invalid");
									errorFieldCount++;
									errors.add("("
											+ errorFieldCount
											+ ") Promotor Date is Invalid ");
								}
							}
							
						}

						if (celVal[33] != null) {
							isValidType = validateTextField(celVal[33]); // Promlegaltype
							if (!isValidType) {
								if (celVal[33].toString().trim().length() != 0) {
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ")Legal Type is Invalid");
								}

							} else {
								if (celVal[33].toString().trim().length() > 50) {
									errorFieldCount++;
									errors.add("("
											+ errorFieldCount
											+ ")Legal Type should not greater than 50 character");
								}
							}
						}

						if (celVal[34] != null) {
							if (celVal[34].toString().trim().length() > 20
									&& celVal[34].toString().trim().length() != 0) {
								errorFieldCount++;
								errors.add("("
										+ errorFieldCount
										+ ")Legal ID Number should not greater than 50 character");
							}
						}
						if (celVal[35] != null) // PmrFirstName celVal[35]
						{
							isValidType = validateTextField(celVal[35]);
							if (!isValidType) {
								if (celVal[35].toString().trim().length() != 0) {
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ") PRM First name is Invalid");
								}

							} else {
								if (celVal[35].toString().trim().length() > 50) {
									errorFieldCount++;
									errors.add("("
											+ errorFieldCount
											+ ")PRM First name should not greater than 50 character");
								}
							}
						}

						if (celVal[36] != null)// PmrFirstDob celVal[36]
						{
							String PmrFirstDobLocal=celVal[36].toString();
							if(celVal[36].toString().startsWith("\""))
							{
								PmrFirstDobLocal=celVal[36].toString().replace("\"", "");
								
							}
							System.out.println(celVal[36]+"PmrFirstDob "+celVal[36].toString().trim().length());
							if (PmrFirstDobLocal.trim().length() > 0) {
								PmrFirstDobDt = formatDateCell(celVal[36]);
							if (PmrFirstDobDt == null) {
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ")PRM First date is Invalid");
								}
							}

						}

						if (celVal[37] != null) // PMR_FIRST_ITPAN
						{
							if (celVal[37].toString().trim().length() > 10) {
								errorFieldCount++;
								errors.add("("
										+ errorFieldCount
										+ ")PMR FIRST ITPAN Length should not be gretter than 10");
							} else if (celVal[37].toString().trim().length() == 10) {
								if (validateITPAN(celVal[37].toString())) {
								} else {
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ")PMR FIRST ITPAN is Invalid");
								}
							}
						}

						if (celVal[38] != null) // PmrSecondName celVal[38]
						{
							isValidType = validateTextField(celVal[38]);
							if (!isValidType) {
								if (celVal[38].toString().trim().length() != 0) {
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ") PRM Second name is Invalid");
								}

							} else {
								if (celVal[38].toString().trim().length() > 50) {
									errorFieldCount++;
									errors.add("("
											+ errorFieldCount
											+ ")PRM Second name should not greater than 50 character");
								}
							}
						}

						if (celVal[39] != null)// PmrSecondDob celVal[39]
						{
							String PmrSecondDobLocal=celVal[39].toString();
							if(PmrSecondDobLocal.startsWith("\""))
							{
								PmrSecondDobLocal=celVal[39].toString().replace("\"", "");
								
							}
							if (PmrSecondDobLocal.trim().length() > 0) {
									PmrSecondDobDt = formatDateCell(celVal[39]);
								
								if (PmrSecondDobDt == null) {
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ")PRM Second Dob date is Invalid");
								}
							}

						}

						if (celVal[40] != null) // PMR_SECOND_ITPAN
						{
                              System.out.println(celVal[40] + "PMR_SECOND_ITPAN "+ celVal[40].toString().trim().length());
							if (celVal[40].toString().trim().length() > 10) {
								errorFieldCount++;
								errors.add("("
										+ errorFieldCount
										+ ")PMR SECOND ITPAN Length should not be gretter than 10");
							} else if (celVal[40].toString().trim().length() == 10) {

								if (validateITPAN(celVal[40].toString())) {

								} else {
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ")PMR SECOND ITPAN is Invalid");
								}
							}

						}

						if (celVal[41] != null) // PmrThirdName celVal[41]
						{
							isValidType = validateTextField(celVal[41]);
							if (!isValidType) {
								if (celVal[41].toString().trim().length() != 0) {
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ") PRM Third name is Invalid");
								}

							} else {
								if (celVal[41].toString().trim().length() > 50) {
									errorFieldCount++;
									errors.add("("
											+ errorFieldCount
											+ ")PRM Third name should not greater than 50 character");
								}
							}
						}

						Date PmrThirdDobDt = null;
						if (celVal[42] != null)// PmrThirdDob celVal[42]
						{
							String PmrThirdDobLocal=celVal[42].toString();
							if(PmrThirdDobLocal.startsWith("\""))
							{
								PmrThirdDobLocal=celVal[42].toString().replace("\"", "");
								
							}
							if (PmrThirdDobLocal.trim().length() > 0) {
									PmrThirdDobDt = formatDateCell(celVal[42]);
								
								if (PmrThirdDobDt == null) {
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ")PRM Third date is Invalid");
								}
							}

						}

						if (celVal[43] != null) // PMR_THIRD_ITPAN
						{

							if (!validateITPAN(celVal[43].toString().trim())
									&& celVal[43].toString().trim().length() > 0) {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")PMR THIRD ITPAN is Invalid");
							}
						}

						if (celVal[44] != null) // IsNewUnit celVal[44]
						{
							if (celVal[44].toString().trim().length() > 0) {
								if (!chkYesNoDecision(celVal[44])) {
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ")Is New Unit Flag is Invalide");
								}
							} else if (celVal[44].toString().trim().length() == 0) {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")Is New Unit Flag is Required");
							}

						} else {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")Is New Unit Flag is Required");
						}

						if (celVal[45] != null) // IsWomenOperated celVal[45]
						{
							if (celVal[45].toString().trim().length() > 0) {
								if (!chkYesNoDecision(celVal[45])) {
									errorFieldCount++;
									errors.add("("
											+ errorFieldCount
											+ ")Women operated Flag is Invalide");
								}
							} else if (celVal[45].toString().trim().length() == 0) {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")Women operated Flag is Required");
							}

						} else {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")Women operated is Required");
						}

						if (celVal[46] != null) // IsMse celVal[46]
						{
							if (celVal[46].toString().trim().length() > 0) {
								if (!chkYesNoDecision(celVal[46])) {
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ")MSE Flag is Invalide");
								}
							} else if (celVal[46].toString().trim().length() == 0) {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")MSE Flag is Required");
							}
						} else {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")MSE is Required");
						}

						if (celVal[47] != null) // MicroEnterprise celVal[47]
						{
							if (celVal[47].toString().trim().length() > 0) {
								if (!chkYesNoDecision(celVal[47])) {
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ")Micro Unit Flag is Invalide");
								}
							} else if (celVal[47].toString().trim().length() == 0) {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")Micro Unit Flag is Required");
							}
						} else {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")Micro Unit is Required");
						}

						if (celVal[48] != null) // CollateralSecurityTaken
												// celVal[48]
						{
							if (celVal[48].toString().trim().length() > 0) {
								if (!chkYesNoDecision(celVal[48])) {
									errorFieldCount++;
									errors.add("("
											+ errorFieldCount
											+ ")Colleteral security Flag is Invalide");
								}
							} else if (celVal[48].toString().trim().length() == 0) {
								errorFieldCount++;
								errors.add("("
										+ errorFieldCount
										+ ")Colleteral security Flag is Required");
							}
						} else {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")Colleteral security is Required");
						}

						if (celVal[49] != null) // ThirdPartySecurityTaken
												// celVal[49]
						{
							if (celVal[49].toString().trim().length() > 0) {
								if (!chkYesNoDecision(celVal[49])) {
									errorFieldCount++;
									errors.add("("
											+ errorFieldCount
											+ ")Third party guarantee Flag is Invalide");
								}
							} else if (celVal[49].toString().trim().length() == 0) {
								errorFieldCount++;
								errors.add("("
										+ errorFieldCount
										+ ")Third party guarantee Flag is Required");
							}
						} else {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")Third party guarantee flag is Required");
						}

						if (celVal[50] != null) // IsDcHandcraft celVal[50]
						{
							if (celVal[50].toString().trim().length() > 0) {
								if (!chkYesNoDecision(celVal[50])) {
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ")Handcraft Flag is Invalide");
								}
							} else if (celVal[50].toString().trim().length() == 0) {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")Handcraft Flag is Required");
							}
						} else {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")Handcraft flag is Required");
						}

						if (celVal[51] != null) // IsDcReimbursement celVal[51]
						{
							if (celVal[51].toString().trim().length() > 0) {
								if (!chkYesNoDecision(celVal[51])) {
									errorFieldCount++;
									errors.add("("
											+ errorFieldCount
											+ ")IS DC Reimbursement Flag is Invalide");
								}
							} else if (celVal[51].toString().trim().length() == 0) {
								errorFieldCount++;
								errors.add("("
										+ errorFieldCount
										+ ")IS DC Reimbursement Flag is Required");
							}
						} else {
							errorFieldCount++;
							errors.add("("
									+ errorFieldCount
									+ ")IS DC Reimbursement flag is Required");
						}

						if (celVal[52] != null) // AccIcard celVal[52]						
						{
							if (celVal[52].toString().trim().length() > 15) {								
								errorFieldCount++;
								errors.add("("
										+ errorFieldCount
										+ ")AAC ICard should not greater than 15 character");
							}

						}

						if (celVal[53] != null) // AccIcardIssueDt celVal[53]
						{							
							if (
									 celVal[50].toString().trim()
											.equalsIgnoreCase("Y")
									&& celVal[51].toString().trim()
											.equalsIgnoreCase("Y")) {
								icardDt = formatDateCell(celVal[53]);
							} 
							
							String AccIcardIssueDtLocal=celVal[53].toString();
							if(AccIcardIssueDtLocal.startsWith("\""))
							{
								AccIcardIssueDtLocal=celVal[53].toString().replace("\"", "");
								
							}
							
							if (icardDt==null
									&& AccIcardIssueDtLocal.trim().length() != 0) {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")Invalid ICard Date");
							}
						}

						if (celVal[54] != null) // IsHandicraft celVal[54]
						{
							if (celVal[54].toString().trim().length() > 0) {
								if (!chkYesNoDecision(celVal[54])) {
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ")Handicapped flag is Invalide");
								}
							} else if (celVal[54].toString().trim().length() == 0) {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")Handicapped flag is Required");
							}
						} else {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")Handicapped flag is Required");
						}
						if (celVal[55] != null) // JointFinanceFlag celVal[55]
						{
							if (celVal[55].toString().trim().length() > 0) {
								if (!chkYesNoDecision(celVal[55])) {
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ")Joint finance flag is Invalide");
								} else {
									jointFinanceFlag = celVal[55].toString()
											.trim();
								}
							} else if (celVal[55].toString().trim().length() == 0) {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")Joint finance flag is Required");
							}
						} else {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")Joint finance flag is Required");
						}

						if (celVal[55] != null && celVal[56] != null) // JointCgpan
																		// celVal[56]
						{
							if (celVal[55].toString().trim()
									.equalsIgnoreCase("Y")) {

								if (celVal[56].toString().trim().length() == 1) {
									if (!chkYesNoDecision(celVal[56])) {
										errorFieldCount++;
										errors.add("(" + errorFieldCount
												+ ")Invalid Joint CGPAN");
									} else {
										jointCGPAN = celVal[56].toString()
												.trim();
									}
								} else {
									if (celVal[56].toString().trim().length() > 0) {
										errorFieldCount++;
										errors.add("(" + errorFieldCount
												+ ")Invalid Joint CGPAN");
									}

								}

							}
						}
						if (celVal[57] != null) // DcHandloomsFlag celVal[57]
						{
							if (celVal[57].toString().trim().length() > 0) {
								if (!chkYesNoDecision(celVal[57])) {
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ")DC Handloom flag is Invalide");
								}
							} else if (celVal[57].toString().trim().length() == 0) {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")DC Handloom flag is Required");
							}
						} else {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")DC Handloom flag is Required");
						}

						if (celVal[58] != null) // DcWeaverCreditSchemeFlag
												// celVal[58]
						{
							if (celVal[58].toString().trim().length() > 0) {
								if (!chkYesNoDecision(celVal[58])) {
									errorFieldCount++;
									errors.add("("
											+ errorFieldCount
											+ ")DC WEAVER CREDIT SCHEME flag is Invalide");
								}
							} else if (celVal[58].toString().trim().length() == 0) {
								errorFieldCount++;
								errors.add("("
										+ errorFieldCount
										+ ")DC WEAVER CREDIT SCHEME flag is Required");
							}
						} else {
							errorFieldCount++;
							errors.add("("
									+ errorFieldCount
									+ ")DC WEAVER CREDIT SCHEME flag is Required");
						}

						if (celVal[59] != null) // DcHanloomsCheck celVal[59]
						{
							if (celVal[59].toString().trim().length() > 0) {
								if (!chkYesNoDecision(celVal[59])) {
									errorFieldCount++;
									errors.add("("
											+ errorFieldCount
											+ ")DcHanloomsCheck flag is Invalid");
								}
							}
						}
						if (celVal[60] != null && celVal[57] != null)// HandloomSchemeName
																	// celVal[60]
						{
							if (celVal[57].toString().trim()
									.equalsIgnoreCase("Y")) {
								if (celVal[60].toString().trim().length() == 0) {
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ")HandloomSchemeName is Required");
								} else if (celVal[60].toString().trim()
										.length() > 0) {

									if (celVal[60].toString().trim()
											.equalsIgnoreCase("IHDS")
											|| celVal[60].toString().trim()
													.equalsIgnoreCase("CHCDS")) {

									} else {
										errorFieldCount++;
										errors.add("("
												+ errorFieldCount
												+ ")HandloomSchemeName should IHDS or CHCDS");
									}

								}

							}

							// if(celVal[60].toString().trim().length() > 5)
							// {
							// errorFieldCount++;
							// errors.add("(" +
							// errorFieldCount+")HandloomSchemeName should not greater than 5 character");
							// }

						}
						if (celVal[61] != null)// InternalRating celVal[61]
						{
							if (celVal[61].toString().trim().length() <= 7) {
								// isValidType = validateTextField(celVal[61]);
								// if (!isValidType)
								// {
								// errorFieldCount++;
								// errors.add("(" + errorFieldCount+
								// ")Internal rating is Required");
								// }
							} else {
								if (celVal[61].toString().trim().length() > 7) {
									errorFieldCount++;
									errors.add("("
											+ errorFieldCount
											+ ")Internal rating should not greater than 7 character");
								} else {
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ")Internal rating is Invalid");
								}
							}
						} else {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")Internal rating is Required");
						}

						if (celVal[62] != null) // InternalRatingProposal
												// celVal[62]
						{
							if (celVal[62].toString().trim().length() > 0) {
								// flagValue = validateFlags(celVal[62]);
								if (validateFlags(celVal[62]) == null) {
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ")Invalid Internal Rating proposal flag");
								}
							}
						}

						if (celVal[63] != null) // InvestmentGrade celVal[63]
						{
							if (celVal[63].toString().trim().length() > 0) {
								// flagValue = validateFlags(celVal[63]);
								if (validateFlags(celVal[63]) == null) {
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ")Invalid investment grade flag");
								}
							}
						}

						if (celVal[64] != null)// SubsidyName = celVal[64];
						{
							if (celVal[64].toString().trim().length() > 10) {
								errorFieldCount++;
								errors.add("("
										+ errorFieldCount
										+ ")Subsidy Name Size Should Not be Greater Than 10");
							}
						}
						if (celVal[65] != null)// SubsidyOther = celVal[65];
						{
							if (celVal[65].toString().trim().length() > 10) {
								errorFieldCount++;
								errors.add("("
										+ errorFieldCount
										+ ")Subsidy Other Size Should Not be Greater Than 10");
							}
						}

						/* term credit and working capital */
						int TL_INTEREST_MORATARIUM = 0;
						if ("TC".equalsIgnoreCase(app_loan_type)) // For TC
						{
							if (celVal[66] != null) // TlSanctionedAmount
													// celVal[66]
							{
								System.out.println("TlSanctionedAmount "+ celVal[66].toString());
								if (celVal[66].toString().trim().length() != 0) {
									isValidType = validateNumericField(celVal[66]);

									if (isValidType) {

										int LocalIntTlSanctionedAmount = (int) celVal[66]
												.getNumericCellValue();
									//	System.out
										//		.println(" celVal[66] TlSanctionedAmount LocalIntTlSanctionedAmount "
											//			+ LocalIntTlSanctionedAmount);
										String strCellValue = String
												.valueOf(LocalIntTlSanctionedAmount);
										intApp_tl_sanction_amt = Integer
												.parseInt(strCellValue);
										if (intApp_tl_sanction_amt <= 10000000) {

											total_sanction_amt = total_sanction_amt
													+ intApp_tl_sanction_amt;
										} else {
											errorFieldCount++;
											errors.add("("
													+ errorFieldCount
													+ ")TL Sanctioned Amount should not be greater than 1 cr");
										}
										// System.out.println("TlSanctionedAmount 1"+celVal[66].getNumericCellValue());

									} else {
										errorFieldCount++;
										errors.add("("
												+ errorFieldCount
												+ ")Invalid TL Sanctioned Amount");
									}
								} else {
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ")TL Sanctioned Amount Required");
								}

							} else {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")TL Sanctioned Amount Required");
							}

							if (celVal[67] != null) // TlPromoterContribution
													// celVal[67]
							{
								System.out.println("TlSanctionedAmount 67 "	+ celVal[67].toString());
								if (celVal[67].toString().trim().length() != 0) {
									isValidType = validateNumericField(celVal[67]);
									if (isValidType) {

										int LocalIntTlPromoterContribution = (int) celVal[67]
												.getNumericCellValue();

										//
										if (LocalIntTlPromoterContribution <= 10000000) {

											tl_prom_cont = celVal[67]
													.getNumericCellValue();
										} else {
											errorFieldCount++;
											errors.add("("
													+ errorFieldCount
													+ ")TL Promoter Contribution Amount should not be greater than 1 cr");
										}
										// System.out.println("TlSanctionedAmount 1"+celVal[66].getNumericCellValue());

									} else {
										errorFieldCount++;
										errors.add("("
												+ errorFieldCount
												+ ")Invalid TL Promoter Contribution Amount");
									}
								}

							}

							if (celVal[68] != null) // TlEquitySupport
													// celVal[68]
							{

								if (celVal[68].toString().trim().length() != 0) {

									isValidType = validateNumericField(celVal[68]);

									if (isValidType) {

										tl_eq_support = celVal[68]
												.getNumericCellValue();
										if (tl_eq_support <= 10000000) {
											tl_eq_support = celVal[68]
													.getNumericCellValue();
										} else {
											errorFieldCount++;
											errors.add("("
													+ errorFieldCount
													+ ")TL Promoter Equity support should not be greater than 1 cr");
										}

									} else {
										errorFieldCount++;
										errors.add("("
												+ errorFieldCount
												+ ")Invalid TL Promoter Equity support");
									}

								}

							}

							if (celVal[69] != null) // TlOthers celVal[69]
							{

								if (celVal[69].toString().trim().length() != 0) {

									isValidType = validateNumericField(celVal[69]);

									if (isValidType) {

										tl_other = celVal[69]
												.getNumericCellValue();
										if (tl_other <= 10000000) {
											tl_other = celVal[69]
													.getNumericCellValue();
										} else {
											errorFieldCount++;
											errors.add("("
													+ errorFieldCount
													+ ")TL Other should not be greater than 1 cr");
										}

									} else {
										errorFieldCount++;
										errors.add("(" + errorFieldCount
												+ ")Invalid TL Other");
									}
								}
							}

							if (celVal[70] != null)// TlSanctionDt celVal[70]                        ERROR    CHANGE FOR EXCEL
							{
								if (celVal[70].toString().trim().length() > 0) {
									 app_tl_sanction_dt = formatDateCell(celVal[70]);
										System.out.println(date2+"<<<<<<<<date2----------------------app_tl_sanction_dt>>>>>>>>>> celVal[70] >>>>>>>>>>>>>>>>>>>>"+app_tl_sanction_dt);										
										// (java.util.Date) dynaForm.get("amountSanctionedDate");
										
									//	amountSanctionedDateVal.compareTo(enterdDateVal) >= 0
										   if (app_tl_sanction_dt.compareTo(date2) > 0){
											    datFlag4UI1 = true;		
											    System.out.println(datFlag4UI1 +"<<<<<<<<datFlag4UI1--------true--------------app_tl_sanction_dt>>>>>>>>>> celVal[70] >>>>>>>>>>>>>>>>>>>>"+app_tl_sanction_dt);	
									        } else if (app_tl_sanction_dt.compareTo(date2) < 0) {
									        	datFlag4UI1 = false;
									        	 System.out.println(datFlag4UI1 +"<<<<<<<<datFlag4UI1------datFlag4UI1----------------app_tl_sanction_dt>>>>>>>>>> celVal[70] >>>>>>>>>>>>>>>>>>>>"+app_tl_sanction_dt);	
									        } else if (app_tl_sanction_dt.compareTo(date2) == 0) {
									        		datFlag4UI1 = true;	  
									        		 System.out.println(datFlag4UI1 +"<<<<<<<<datFlag4UI1-----true-----------------app_tl_sanction_dt>>>>>>>>>> celVal[70] >>>>>>>>>>>>>>>>>>>>"+app_tl_sanction_dt);	
									        }
										   /*if (!validateFromLastQuarterDate(app_tl_sanction_dt)) {    ERROR    CHANGE FOR EXCEL CHANGABLE
												errorFieldCount++;
												errors.add("("
														+ errorFieldCount
														+ ")TL Sanction date should not be less than previous qurter");
											}*/
										/*if (!validateFromLastQuarterDate(app_tl_sanction_dt)) {    ERROR    CHANGE FOR EXCEL CHANGABLE
											errorFieldCount++;
											errors.add("("
													+ errorFieldCount
													+ ")TL Sanction date should not be less than previous qurter");
										}*/		
										   
									if (app_tl_sanction_dt == null) {
										errorFieldCount++;
										errors.add("("
												+ errorFieldCount
												+ ")TL Sanction date is Invalid");
									}
								} else if (celVal[70].toString().trim()
										.length() == 0) {
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ")TL Sanction date is Required");
								}
							} else {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")TL Sanction date is Required");
							}

							//System.out.println("credittoguaranteeamount celVal[71]"
							//				+ celVal[71]);
							if (celVal[71] != null) // TlCreditToGuarantee
								// celVal[71]
		{

			if (celVal[71].toString().trim().length() != 0) {
				isValidType = validateNumericField(celVal[71]);
				//System.out
					//	.println("credittoguaranteeamount isValidType"
						//		+ isValidType);
				int LocalIntTlCreditToGuarantee = (int) celVal[71]
						.getNumericCellValue();
				String strCellValue = String
						.valueOf(LocalIntTlCreditToGuarantee);
				intApp_tl_credit_amt = Integer
						.parseInt(strCellValue);
 				String fbIdFlag=celVal[127].getStringCellValue();
				System.out.println("FBID..........>>>>>>>>>>" +fbIdFlag);
			
				if (isValidType) {
					if(fbIdFlag.equalsIgnoreCase("Y"))
				
						{                    //  need to remove DKR
						HashMap<String, Double> mapExpo = this.appDAO.getExposuredetailsForFileUpload(bankId);
						 Iterator it = mapExpo.entrySet().iterator();
						    while (it.hasNext()) {
						        Map.Entry pair = (Map.Entry)it.next();
						         expoIdds = (String) pair.getKey(); 
						         expoAmt = (Double) pair.getValue();
						        it.remove(); 
						    }

					    if (intApp_tl_credit_amt <= expoAmt) {										
				
						} else {
							errorFieldCount++;
							errors.add("("
									+ errorFieldCount
									+ ")TL Credit to Guarantee amount should not be greater than  Guarantee Limit amount. Please increase the exposure Guarantee Limit amount");
				         	}							
						}
					else {	
					

					 app_tl_credit_amt =  celVal[71].getNumericCellValue();
				

					if (intApp_tl_credit_amt <= 25000000) {
						// credittoguaranteeamount =
						// credittoguaranteeamount+
						// intApp_tl_credit_amt;
						intcredittoguaranteeamount = intcredittoguaranteeamount
								+ intApp_tl_credit_amt;
				//		System.out
							//	.println("credittoguaranteeamount "
						//				+ intcredittoguaranteeamount);
					} else {
						errorFieldCount++;
						errors.add("("
								+ errorFieldCount
								+ ")TL Credit To Guarantee should not be greater than 2 cr");
					}
					}
				} else {
					errorFieldCount++;
					errors.add("("
							+ errorFieldCount
							+ ")Invalid TL Credit To Guarantee");
				}
			} else {
				errorFieldCount++;
				errors.add("("
						+ errorFieldCount
						+ ")TL Credit To Guarantee Required");
			}

		} else {
			errorFieldCount++;
			errors.add("(" + errorFieldCount
					+ ")TL Credit To Guarantee Required");
		}

							if (celVal[72] != null) // TlAmtDisbursed celVal[72]
							{

								if (celVal[72].toString().trim().length() != 0) {
									isValidType = validateNumericField(celVal[72]);
									if (isValidType) {
										int LocalIntTlAmtDisbursed = (int) celVal[72]
												.getNumericCellValue();
										String strCellValue = String
												.valueOf(LocalIntTlAmtDisbursed);
										intTlAmtDisbursed = Integer
												.parseInt(strCellValue);
										if (intTlAmtDisbursed <= 10000000) {

										} else {
											errorFieldCount++;
											errors.add("("
													+ errorFieldCount
													+ ")TL Amount Disbursed should not be greater than 1 cr");
										}

									} else {
										errorFieldCount++;
										errors.add("("
												+ errorFieldCount
												+ ")Invalid TL Amount Disbursed");
									}
								}

							}

							//

							if (celVal[73] != null)// TlFirstDisbursementDt =
													// celVal[73];
							{
								
								String TlFirstDisbursementDtLocal=celVal[73].toString();
								if(TlFirstDisbursementDtLocal.startsWith("\""))
								{
									TlFirstDisbursementDtLocal=celVal[73].toString().replace("\"", "");
									
								}
								if (TlFirstDisbursementDtLocal.trim().length() > 0) {
									
										appTlFirstDisbursementDt = formatDateCell(celVal[73]);
										if (appTlFirstDisbursementDt == null) {
											errorFieldCount++;
											errors.add("("
													+ errorFieldCount
													+ ")TL FirstDisbursement date is Invalid");
										}
									
								}

							}

							if (celVal[74] != null)// TlLastDisbursementDt =
													// celVal[74];
							{
								String TlLastDisbursementDtLocal=celVal[74].toString();
								if(TlLastDisbursementDtLocal.startsWith("\""))
								{
									TlLastDisbursementDtLocal=celVal[74].toString().replace("\"", "");
									
								}
								if (TlLastDisbursementDtLocal.trim().length() > 0) {
									
										appTlLastDisbursementDt = formatDateCell(celVal[74]);

										if (appTlLastDisbursementDt == null) {

											errorFieldCount++;
											errors.add("("
													+ errorFieldCount
													+ ")TL LastDisbursement date is Invalid");
										}
								

								}

							}

							if (celVal[75] != null)// TlBasrRateType celVal[75]
							{
								if (celVal[75].toString().trim().length() < 50) {
									isValidType = validateTextField(celVal[75]); // TlBasrRateType
																					// celVal[75]
									if (isValidType
											&& !celVal[75].toString().trim()
													.equalsIgnoreCase("null")) {
										tl_plr_type = celVal[75]
												.getStringCellValue();
								//		System.out
									//			.println("tl_plr_type celVal[75]"
									//					+ tl_plr_type);
									} else {
										if (celVal[75].toString().trim()
												.length() == 0) {
											errorFieldCount++;
											errors.add("("
													+ errorFieldCount
													+ ")TL Base Rate type is Required");
										} else {
											errorFieldCount++;
											errors.add("("
													+ errorFieldCount
													+ ")TL Base Rate type is Invalid");
										}
									}
								} else {
									errorFieldCount++;
									errors.add("("
											+ errorFieldCount
											+ ")TL Base Rate type should not be greater than 50 char");
								}
							} else {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")TL Base Rate type is Required");
							}

							if (celVal[76] != null) // TlBaseRate celVal[76]
							{
								// isValidType =
								// validateNumericField(celVal[76]);
								// System.out.println("TlBaseRate"+TlBaseRate);
								// if (isValidType)
								// {
								// int count3=0;
								// System.out.println(celVal[76].toString().trim().length()+"=TlBaseRate is valid="+celVal[76].toString().trim());
								// if(celVal[76].toString().trim().endsWith(".0"))
								// {
								// count3=7;
								// }
								// else
								// {
								// count3=5;
								// }
								//
								// if(celVal[76].toString().trim().length() <=
								// count3)
								// {
								// tl_plr = celVal[76].getNumericCellValue();
								//
								// }
								// else
								// {
								// errorFieldCount++;
								// errors.add("(" + errorFieldCount+
								// ")TL Base Rate is Invalid");
								// }
								//
								// }
								// else
								// {
								// if(celVal[76].toString().trim().length()==0)
								// {
								// errorFieldCount++;
								// errors.add("(" + errorFieldCount+
								// ")TL Base Rate is Required");
								// }
								// else
								// {
								// errorFieldCount++;
								// errors.add("(" + errorFieldCount+
								// ")TL Base Rate is Invalid");
								// }
								//
								// }

								if (celVal[76].toString().trim().length() != 0) {
									if (validateNumericField(celVal[76])) {
										if (Double.parseDouble(celVal[76]
												.toString().trim()) <= 99.99
												&& celVal[76].toString().trim()
														.length() != 0) {
											tl_plr = celVal[76]
													.getNumericCellValue();
											//System.out
											//		.println("FixedAssetCoverageRatio is less than 99.99 "
											//				+ tl_plr);
										} else {
											errorFieldCount++;
											errors.add("("
													+ errorFieldCount
													+ ")TL Base Rate is Invalid");

										}
									} else {

										errorFieldCount++;
										errors.add("(" + errorFieldCount
												+ ")TL Base Rate is Invalid");
									}
								} else {
									if (celVal[76].toString().trim().length() == 0) {
										errorFieldCount++;
										errors.add("(" + errorFieldCount
												+ ")TL Base Rate is Required");
									} else {
										errorFieldCount++;
										errors.add("(" + errorFieldCount
												+ ")TL Base Rate is Invalid");
									}
								}

							} else {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")TL Base Rate is Required");
							}

							if (celVal[77] != null) // TlInterestType celVal[77]
							{
								if (celVal[77].toString().trim().length() == 1) {
									isValidType = validateTextField(celVal[77]);
									if (isValidType) {
										if (celVal[77].toString().trim()
												.equalsIgnoreCase("T")
												|| celVal[77].toString().trim()
														.equalsIgnoreCase("F")) {
											tl_interest_type = celVal[77]
													.getStringCellValue();
										} else {
											errorFieldCount++;
											errors.add("("
													+ errorFieldCount
													+ ")TL Interest type should be T or F");
										}

									} else {

										errorFieldCount++;
										errors.add("("
												+ errorFieldCount
												+ ")TL Interest type is Invalid");
									}
								} else {
									if (celVal[77].toString().trim().length() == 0) {
										errorFieldCount++;
										errors.add("("
												+ errorFieldCount
												+ ")TL Interest type is Required");
									} else {
										errorFieldCount++;
										errors.add("("
												+ errorFieldCount
												+ ")TL Interest type is Invalid");
									}

								}
							}

							if (celVal[78] != null) // Tl Intrest Rate
													// celVal[78]
							{
								// isValidType =
								// validateNumericField(celVal[78]);
								//
								// if (isValidType)
								// {
								// int count3=0;
								//
								// if(celVal[78].toString().trim().endsWith(".0"))
								// {
								// count3=7;
								// }
								// else
								// {
								// count3=5;
								// }
								//
								// if(celVal[78].toString().trim().length() <=
								// count3)
								// {
								// tl_interest =
								// celVal[78].getNumericCellValue();
								// System.out.println(" tl_interest celVal[78]"+tl_interest);
								//
								// }
								// else
								// {
								// errorFieldCount++;
								// errors.add("(" + errorFieldCount+
								// ")TL INTEREST Rate is Invalid");
								// }
								//
								// }
								// else
								// {
								// if(celVal[78].toString().trim().length()==0)
								// {
								// errorFieldCount++;
								// errors.add("(" + errorFieldCount+
								// ")TL INTEREST Rate is Required");
								// }
								// else
								// {
								// errorFieldCount++;
								// errors.add("(" + errorFieldCount+
								// ")TL INTEREST Rate is Invalid");
								// }
								//
								// }

								if (celVal[78].toString().trim().length() != 0) {
									if (validateNumericField(celVal[78])) {
										if (Double.parseDouble(celVal[78]
												.toString().trim()) <= 99.99
												&& celVal[78].toString().trim()
														.length() != 0) {
											tl_interest = celVal[78]
													.getNumericCellValue();
											//System.out
												//	.println("FixedAssetCoverageRatio is less than 99.99 "
												//			+ tl_plr);
										} else {
											errorFieldCount++;
											errors.add("("
													+ errorFieldCount
													+ ")TL INTEREST Rate is Invalid");

										}
									} else {

										errorFieldCount++;
										errors.add("("
												+ errorFieldCount
												+ ")TL INTEREST Rate is Invalid");
									}
								} else {
									if (celVal[78].toString().trim().length() == 0) {
										errorFieldCount++;
										errors.add("("
												+ errorFieldCount
												+ ")TL INTEREST Rate is Required");
									} else {
										errorFieldCount++;
										errors.add("("
												+ errorFieldCount
												+ ")TL INTEREST Rate is Invalid");
									}
								}
							} else {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")TL INTEREST Rate is Required");
							}

							if (celVal[79] != null) // TlTenure celVal[79]
							{
								if (celVal[79].toString().trim().length() == 0) {
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ")Tenure is Required");
								} else if (!validateNumericField(celVal[79])) {

									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ")Tenure is Invalid");
								} else if (validateNumericField(celVal[79])) {
									int tlTenure = (int) celVal[79]
											.getNumericCellValue();
								//	System.out.println("tlTenure" + tlTenure);

									if (tlTenure >= 12 && tlTenure <= 120) {
										tenure = tlTenure;
									} else {
										errorFieldCount++;
										errors.add("("
												+ errorFieldCount
												+ ")Tenure should be greater than 12 and less than 120");
									}
								}
							} else {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")Tenure is Required");
							}

							if (celVal[80] != null) // TL_PRINCIPAL_MORATARIUM
													// celVal[80]
							{

								if (celVal[80].toString().trim().length() != 0) {
									if (!validateNumericField(celVal[80])) {
										errorFieldCount++;
										errors.add("("
												+ errorFieldCount
												+ ")TL PRINCIPAL MORATARIUM is Invalid");
									} else {
										int intPrincipal_mora = (int) celVal[80]
												.getNumericCellValue();
									//	System.out
										//		.println("TL_PRINCIPAL_MORATARIUM "
											//			+ intPrincipal_mora);
										if (intPrincipal_mora > 30) {
											errorFieldCount++;
											errors.add("("
													+ errorFieldCount
													+ ")TL PRINCIPAL MORATARIUM should not be greater than 30");
										} else {
											principal_mora = intPrincipal_mora;
										}
									}
								} else {
									errorFieldCount++;
									errors.add("("
											+ errorFieldCount
											+ ")TL PRINCIPAL MORATARIUM is Required");
								}

							} else {
								errorFieldCount++;
								errors.add("("
										+ errorFieldCount
										+ ")TL_PRINCIPAL_MORATARIUM is Required");
							}

							if (celVal[81] != null) // TL_INTEREST_MORATARIUM
													// celVal[81]
							{

								if (celVal[81].toString().trim().length() != 0) {
									if (!validateNumericField(celVal[81])) {
										errorFieldCount++;
										errors.add("("
												+ errorFieldCount
												+ ")TL INTEREST_MORATARIUM is Invalid");
									} else {
										int a = (int) celVal[81]
												.getNumericCellValue();

										if (a > 30) {
											errorFieldCount++;
											errors.add("("
													+ errorFieldCount
													+ ")TL INTEREST_MORATARIUM should not be greater than 30");
										} else {
											TL_INTEREST_MORATARIUM = (int) celVal[81]
													.getNumericCellValue();
										}
									}
								}

							}

							if (celVal[82] != null) {
								isValidType = validateTextField(celVal[82]); // Periodicity
								if (isValidType) {
									String period = celVal[82]
											.getStringCellValue();
									if (period.equalsIgnoreCase("Monthly")
											|| period.equalsIgnoreCase("m"))
										periodicity = 1;
									else if (period
											.equalsIgnoreCase("Quarterly")
											|| period.equalsIgnoreCase("q"))
										periodicity = 2;
									else if (period
											.equalsIgnoreCase("Half Yearly")
											|| period.equalsIgnoreCase("h"))
										periodicity = 3;
									else if ((period.equalsIgnoreCase("Yearly") || period
											.equalsIgnoreCase("y"))
											|| ((period
													.equalsIgnoreCase("Anually") || period
													.equalsIgnoreCase("a"))))
										periodicity = 4;
									else if (period.equalsIgnoreCase("Weekly")
											|| period.equalsIgnoreCase("w"))
										periodicity = 5;
									else {
										errorFieldCount++;
										errors.add("(" + errorFieldCount
												+ ")Periodicity is Invalid");
									}
								} else {
									if (celVal[82].toString().trim().length() != 0) {
										errorFieldCount++;
										errors.add("(" + errorFieldCount
												+ ")Periodicity is Invalid");
									}
								}
							}

							if (celVal[83] != null) // NO_OF_INSTALLMENTS
													// celVal[83]
							{

								if (celVal[83].toString().trim().length() != 0) {
									if (!validateNumericField(celVal[83])) {
										errorFieldCount++;
										errors.add("("
												+ errorFieldCount
												+ ") NO_OF_INSTALLMENTS is Invalid");
									} else {
										int a = (int) celVal[83]
												.getNumericCellValue();

										if (a > 999) {
											errorFieldCount++;
											errors.add("("
													+ errorFieldCount
													+ ") NO_OF_INSTALLMENTS Invalid");
										} else {
											installmentNos = (int) celVal[83]
													.getNumericCellValue();
										}
									}
								} else {
									errorFieldCount++;
									errors.add("("
											+ errorFieldCount
											+ ") NO_OF_INSTALLMENTS is Required");
								}

							} else {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")NO_OF_INSTALLMENTS is Required");
							}

							// Start TL_FIRST_INSTALMENT_DUE_DT = celVal[84];
							if (celVal[84] != null) {
								if (celVal[84].toString().trim().length() > 0) {
									
										Date TL_FIRST_INSTALMENT_DUE_DT = formatDateCell(celVal[84]);
										if (TL_FIRST_INSTALMENT_DUE_DT == null) {
											errorFieldCount++;
											errors.add("("
													+ errorFieldCount
													+ ")TL FIRST_INSTALMENT_DUE date is Invalid");
										}
									
								}
							}
							// end TL_FIRST_INSTALMENT_DUE_DT = celVal[84];

							// start TlOutstandingAmount celVal[85]
							if (celVal[85] != null) {

								if (celVal[85].toString().trim().length() != 0) {
									isValidType = validateNumericField(celVal[85]);
									if (isValidType) {
										int LocalIntTlOutstandingAmount = (int) celVal[85]
												.getNumericCellValue();
										String strCellValue = String
												.valueOf(LocalIntTlOutstandingAmount);
										IntTlOutstandingAmount = Integer
												.parseInt(strCellValue);
										if (!(celVal[85].getNumericCellValue() <= 10000000)) {
											errorFieldCount++;
											errors.add("("
													+ errorFieldCount
													+ ")TL Outstanding Amount should not be greater than 1 cr");
										}
									} else {
										errorFieldCount++;
										errors.add("("
												+ errorFieldCount
												+ ")Invalid TL Outstanding Amount");
									}
								}

							}
							// end TlOutstandingAmount celVal[85]

							if (celVal[86] != null)// TlOutstandingDt =
													// celVal[86];
							{
								if (celVal[86].toString().trim().length() > 0) {
									Date appTlOutstandingDt = formatDateCell(celVal[86]);
										//System.out.println("appTlOutstandingDt : "
										//				+ appTlOutstandingDt);
										if (appTlOutstandingDt == null) {

											errorFieldCount++;
											errors.add("("
													+ errorFieldCount
													+ ")TL Outstanding date is Invalid");
										}
									
								}
							}

						} // TC End here
						isValidType = validateTL(app_loan_type,
								intApp_tl_sanction_amt, intApp_tl_credit_amt,
								app_tl_sanction_dt, tl_interest, tl_os_Amt,
								tl_os_dt, tenure, principal_mora, tl_plr_type,
								tl_plr);

						/*if (!isValidType) {                                      Need to remove
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")Invalid Term credit details.");
						}*/

						
						if ("WC".equalsIgnoreCase(app_loan_type)) {

							if (celVal[87] != null) // WcFbLimitSanctioned
													// celVal[87]
							{
								if (celVal[87].toString().trim().length() != 0) {
									isValidType = validateNumericField(celVal[87]);
									if (isValidType) {
										int LocalIntTlCreditToGuarantee = (int) celVal[87]
												.getNumericCellValue();
										String strCellValue = String
												.valueOf(LocalIntTlCreditToGuarantee);
										intApp_wc_fb_sanction_amt = Integer
												.parseInt(strCellValue);

										if (intApp_wc_fb_sanction_amt <= 10000000) {
											total_sanction_amt = total_sanction_amt
													+ intApp_wc_fb_sanction_amt;
										} else {
											errorFieldCount++;
											errors.add("("
													+ errorFieldCount
													+ ") WC Sanctioned Amount should not be greater than 1 cr");
										}

									} else {
										errorFieldCount++;
										errors.add("("
												+ errorFieldCount
												+ ")Invalid WC FB Sanctioned Amount");
									}
								} else {
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ") WC Sanctioned Amount Required");
								}

							} else {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ") WC Sanctioned Amount Required");
							}

							if (celVal[88] != null) // WcFbCreditToGuarantee
													// celVal[88]
							{

								if (celVal[88].toString().trim().length() != 0) {
									isValidType = validateNumericField(celVal[88]);
									if (isValidType) {

										int LocalIntTlCreditToGuarantee = (int) celVal[88]
												.getNumericCellValue();
										String strCellValue = String
												.valueOf(LocalIntTlCreditToGuarantee);
										intApp_wc_fb_credit_amt = Integer
												.parseInt(strCellValue);

										if (intApp_wc_fb_credit_amt <= 10000000) {

											intcredittoguaranteeamount = intcredittoguaranteeamount
													+ intApp_wc_fb_credit_amt;
										} else {
											errorFieldCount++;
											errors.add("("
													+ errorFieldCount
													+ ") Wc Fb CreditTo Guarantee should not be greater than 1 cr");
										}

									} else {
										errorFieldCount++;
										errors.add("("
												+ errorFieldCount
												+ ")Invalid Wc Fb CreditTo Guarantee ");
									}
								} else {
									errorFieldCount++;
									errors.add("("
											+ errorFieldCount
											+ ") Wc Fb CreditTo Guarantee  Required");
								}

							} else {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")Wc Fb CreditTo Guarantee Required");
							}

							// start WcNfbLimitSanctioned celVal[89]
						//	System.out.println("celVal[89]" + celVal[89]);
							if (celVal[89] != null)// WcNfbLimitSanctioned
													// celVal[89]
							{

								if (celVal[89].toString().trim().length() != 0) {

									isValidType = validateNumericField(celVal[89]);

									if (isValidType) {
										int LocalIntapp_wc_nfb_sanction_amt = (int) celVal[89]
												.getNumericCellValue();

										String strCellValue = String
												.valueOf(LocalIntapp_wc_nfb_sanction_amt);

										intApp_wc_nfb_sanction_amt = Integer
												.parseInt(strCellValue);

										if (intApp_wc_nfb_sanction_amt <= 10000000) {
											total_sanction_amt = total_sanction_amt
													+ intApp_wc_nfb_sanction_amt;
										} else {
											errorFieldCount++;
											errors.add("("
													+ errorFieldCount
													+ ")WC NFB Limit Sanctioned should not be greater than 1 cr");
										}
									} else {
										errorFieldCount++;
										errors.add("("
												+ errorFieldCount
												+ ")WC NFB Limit Sanctioned Invalid");
									}
								} else {
									errorFieldCount++;
									errors.add("("
											+ errorFieldCount
											+ ")WC NFB Limit Sanctioned Required");
								}
							} else {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")WC NFB Limit Sanctioned Required");
							}
							// end WcNfbLimitSanctioned celVal[89]

							// start WcNfbCreditToGuarantee celVal[90]
							if (celVal[90] != null)// WcNfbCreditToGuarantee
													// celVal[90]
							{
								if (celVal[90].toString().trim().length() != 0) {
									isValidType = validateNumericField(celVal[90]);
									if (isValidType) {
										int LocalIntapp_wc_nfb_credit_amt = (int) celVal[90]
												.getNumericCellValue();
										String strCellValue = String
												.valueOf(LocalIntapp_wc_nfb_credit_amt);
										intApp_wc_nfb_credit_amt = Integer
												.parseInt(strCellValue);
										if (intApp_wc_nfb_credit_amt <= 10000000) {
											intcredittoguaranteeamount = intcredittoguaranteeamount
													+ intApp_wc_nfb_credit_amt;
										} else {
											errorFieldCount++;
											errors.add("("
													+ errorFieldCount
													+ ") WC NFB Credit To Guarantee should not be greater than 1 cr");
										}
									} else {
										errorFieldCount++;
										errors.add("("
												+ errorFieldCount
												+ ") WC NFB CreditTo Guarantee Invalid");
									}
								} else {
									errorFieldCount++;
									errors.add("("
											+ errorFieldCount
											+ ") WC NFB CreditTo Guarantee  Required");
								}

							} else {
								errorFieldCount++;
								errors.add("("
										+ errorFieldCount
										+ ")WC NFB CreditTo Guarantee  Required");
							}
							// end WcNfbCreditToGuarantee celVal[90]

							// start WcTlUnderMargin celVal[91]
							if (celVal[91] != null) {
								if (celVal[91].toString().trim().length() == 1) {
									isValidType = validateTextField(celVal[91]);
									if (isValidType) {
										if (celVal[91].toString().trim()
												.equalsIgnoreCase("Y")
												|| celVal[91].toString().trim()
														.equalsIgnoreCase("N")) {
										} else {
											errorFieldCount++;
											errors.add("("
													+ errorFieldCount
													+ ")WC_TL Under Margin Should be Y or N");
										}
									} else {
										errorFieldCount++;
										errors.add("("
												+ errorFieldCount
												+ ")WC_TL Under Margin is Invalid");
									}

								} else {
									if (celVal[91].toString().trim().length() == 0) {
										errorFieldCount++;
										errors.add("("
												+ errorFieldCount
												+ ")WC_TL Under Margin is Required");
									} else {
										errorFieldCount++;
										errors.add("("
												+ errorFieldCount
												+ ")WC_TL Under Margin is Invalid");
									}
								}

							} else {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")WC_TL Under Margin is Required");
							}
							// end WcTlUnderMargin celVal[91]

							// start WcPromoterContribution celVal[92]
							if (celVal[92] != null) {
								if (celVal[92].toString().trim().length() != 0) {
									isValidType = validateNumericField(celVal[92]);
									if (isValidType) {
										int LocalIntWcPromoterContribution = (int) celVal[92]
												.getNumericCellValue();
										String strCellValue = String
												.valueOf(LocalIntWcPromoterContribution);
										intwc_prom_cont = Integer
												.parseInt(strCellValue);
										if (!(intwc_prom_cont <= 10000000)) {
											errorFieldCount++;
											errors.add("("
													+ errorFieldCount
													+ ") WC Promoter Contribution should not be greater than 1 cr");
										}
									} else {
										errorFieldCount++;
										errors.add("("
												+ errorFieldCount
												+ ")Invalid WC Promoter Contribution");
									}
								}
							}
							// end WcPromoterContribution celVal[92]

							// start WcEquitySupport celVal[93]

							if (celVal[93] != null) {
								if (celVal[93].toString().trim().length() != 0) {
									isValidType = validateNumericField(celVal[93]);
									if (isValidType) {
										int LocalIntWcEquitySupport = (int) celVal[93]
												.getNumericCellValue();
										String strCellValue = String
												.valueOf(LocalIntWcEquitySupport);
										intwc_eq_support = Integer
												.parseInt(strCellValue);
										if (!(intwc_eq_support <= 10000000)) {
											errorFieldCount++;
											errors.add("("
													+ errorFieldCount
													+ ") WC Equity Support should not be greater than 1 cr");
										}
									} else {
										errorFieldCount++;
										errors.add("(" + errorFieldCount
												+ ")Invalid WC Equity Support");
									}
								}
							}
							// end WcEquitySupport celVal[93]

							// start WcOthers celVal[94]
							if (celVal[94] != null) {
								if (celVal[94].toString().trim().length() != 0) {
									isValidType = validateNumericField(celVal[94]);
									if (isValidType) {
										int LocalIntWcOthers = (int) celVal[94]
												.getNumericCellValue();
										String strCellValue = String
												.valueOf(LocalIntWcOthers);
										intwc_other = Integer
												.parseInt(strCellValue);
										if (!(intwc_other <= 10000000)) {
											errorFieldCount++;
											errors.add("("
													+ errorFieldCount
													+ ") WC Other should not be greater than 1 cr");
										}
									} else {
										errorFieldCount++;
										errors.add("(" + errorFieldCount
												+ ")Invalid WC Other");
									}
								}
							}
							// end WcOthers celVal[94]

							if (celVal[95] != null)// WcPlrType celVal [95]
							{
							//	System.out.println(" celVal[95] " + celVal[95]);
								if (celVal[95].toString().trim().length() < 50) {
									isValidType = validateTextField(celVal[95]);
								//	System.out
									//		.println(" celVal[95] isValidType"
										//			+ isValidType);
									if (isValidType
											&& !celVal[95].toString().trim()
													.equalsIgnoreCase("null")) {
										wc_plr_type = celVal[95]
												.getStringCellValue();
									} else {
									//	System.out
										//		.println(" celVal[95] isValidType else"
											//			+ celVal[95].toString()
											//					.trim()
											//					.length());
										if (celVal[95].toString().trim()
												.length() == 0) {
										//	System.out
											//		.println(" celVal[95] isValidType if length o"
											//				+ isValidType);
											errorFieldCount++;
											errors.add("("
													+ errorFieldCount
													+ ")WC Base Rate type is Required");
										} else {
										//	System.out
											//		.println(" celVal[95] isValidType else length o"
											//				+ isValidType);
											errorFieldCount++;
											errors.add("("
													+ errorFieldCount
													+ ")WC Base Rate type is Invalid");
										}
									}
								} else {
									errorFieldCount++;
									errors.add("("
											+ errorFieldCount
											+ ")WC Base Rate type should not be greater than 50 char");
								}
							} else {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")WC Base Rate type is Required");
							}

							if (celVal[96] != null) // //WcPlr celVal[96]
							{
								isValidType = validateNumericField(celVal[96]);

								if (isValidType) {

									if (Double.parseDouble(celVal[96]
											.toString().trim()) <= 99.99
											&& celVal[96].toString().trim()
													.length() != 0) {
										wc_plr = celVal[96]
												.getNumericCellValue();

									} else {
										errorFieldCount++;
										errors.add("("
												+ errorFieldCount
												+ ")WC Base Rate[WC_PLR] is Invalid");
									}

								} else {
									if (celVal[96].toString().trim().length() != 0) {
										errorFieldCount++;
										errors.add("("
												+ errorFieldCount
												+ ")WC Base Rate[WC_PLR] is Invalid");
									}

								}

							} else {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")WC Base Rate[WC_PLR] is Required");
							}

							if (celVal[97] != null) // WC InterestType
													// celVal[97]
							{
								if (celVal[97].toString().trim().length() == 1) {
									isValidType = validateTextField(celVal[97]);
									if (isValidType) {
										if (celVal[97].toString().trim()
												.equalsIgnoreCase("T")
												|| celVal[97].toString().trim()
														.equalsIgnoreCase("F")) {
											wc_interest_type = celVal[97]
													.getStringCellValue();
										} else {
											errorFieldCount++;
											errors.add("("
													+ errorFieldCount
													+ ")WC Interest type should be T or F");
										}

									} else {

										errorFieldCount++;
										errors.add("("
												+ errorFieldCount
												+ ")WC Interest type is Invalid");
									}
								} else {
									if (celVal[97].toString().trim().length() == 0) {
										errorFieldCount++;
										errors.add("("
												+ errorFieldCount
												+ ")WC Interest type is Required");
									} else {
										errorFieldCount++;
										errors.add("("
												+ errorFieldCount
												+ ")WC Interest type is Invalid");
									}

								}
							}

							if (celVal[98] != null) // WcFbInterest celVal[98]
							{

								isValidType = validateNumericField(celVal[98]);

								if (isValidType) {

									if (Double.parseDouble(celVal[98]
											.toString().trim()) <= 99.99
											&& celVal[98].toString().trim()
													.length() != 0) {
										wc_interest = celVal[98]
												.getNumericCellValue();
									//	System.out
										//		.println("FixedAssetCoverageRatio is less than 99.99 ");
									} else {
										errorFieldCount++;
										errors.add("("
												+ errorFieldCount
												+ ")WC FB Interest Rate is Invalid");
									}

								} else {
									if (celVal[98].toString().trim().length() != 0) {
										errorFieldCount++;
										errors.add("("
												+ errorFieldCount
												+ ")WC FB Interest Rate is Invalid");
									}

								}
							}

							if (celVal[99] != null)// WcFbLimitSanctionedDt
													// celVal[99];
							{
								if (celVal[99].toString().trim().length() > 0) {
									
										app_wc_fb_sanction_dt = formatDateCell(celVal[99]);

										if (app_wc_fb_sanction_dt == null) {

											errorFieldCount++;
											errors.add("("
													+ errorFieldCount
													+ ")WC FB Sanction date is Invalid");
										}
									
								}
							} else {

								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")WC FB Sanction date is required");

							}

							if (celVal[100] != null) // WcNfbCommission
														// celVal[100]
							{

								isValidType = validateNumericField(celVal[100]);

								if (isValidType) {

									if (Double.parseDouble(celVal[100]
											.toString().trim()) <= 99.99
											&& celVal[100].toString().trim()
													.length() != 0) {
										wc_commission = celVal[100]
												.getNumericCellValue();
									//	System.out
											//	.println("FixedAssetCoverageRatio is less than 99.99 ");
									} else {
										errorFieldCount++;
										errors.add("("
												+ errorFieldCount
												+ ")WC NFB commission limit exceed");
									}

								} else {
									if (celVal[100].toString().trim().length() != 0) {
										errorFieldCount++;
										errors.add("("
												+ errorFieldCount
												+ ")WC NFB commission limit exceed");
									}

								}

							}

							// start //WcNfbLimitSanctionedDt celVal[101]
							if (celVal[101] != null)// WcNfbLimitSanctionedDt
							{
								if (celVal[101].toString().trim().length() > 0) {
									
										app_wc_nfb_sanction_dt = formatDateCell(celVal[101]);

										if (app_wc_nfb_sanction_dt == null) {

											errorFieldCount++;
											errors.add("("
													+ errorFieldCount
													+ ")WC NFB Sanction date is Invalid");
										}
									
								}
							} else {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")WC NFB Sanction date is Invalid");
							}
							// end WcNfbLimitSanctionedDt celVal[101]

							if (celVal[102] != null) // FbOutstandingAmount
														// celVal[102]
							{
								if (celVal[102].toString().trim().length() != 0) {
									isValidType = validateNumericField(celVal[102]);
									if (isValidType) {
										int LocalIntWcOthers = (int) celVal[102]
												.getNumericCellValue();
										String strCellValue = String
												.valueOf(LocalIntWcOthers);
										intFbOutstandingAmount = Integer
												.parseInt(strCellValue);
										if (!(intFbOutstandingAmount <= 10000000)) {
											errorFieldCount++;
											errors.add("("
													+ errorFieldCount
													+ ") WC FB Outstanding Amount should not be greater than 1 cr");
										}
									} else {
										errorFieldCount++;
										errors.add("("
												+ errorFieldCount
												+ ")Invalid WC FB Outstanding Amount");
									}
								}
							}

							if (celVal[103] != null)// FbOutstandingDt
													// celVal[103];
							{

								if (celVal[103].toString().trim().length() > 0) {
									
										app_wc_fb_os_dt = formatDateCell(celVal[103]);

										if (app_wc_fb_os_dt == null) {

											errorFieldCount++;
											errors.add("("
													+ errorFieldCount
													+ ")FB OUTSTANDING date is Invalid");
										}
									
								}
							}

							if (celVal[104] != null) // NfbOutstandingAmt
														// celVal[104]
							{
								if (celVal[104].toString().trim().length() != 0) {
									isValidType = validateNumericField(celVal[104]);
									if (isValidType) {
										int LocalIntWcOthers = (int) celVal[104]
												.getNumericCellValue();
										String strCellValue = String
												.valueOf(LocalIntWcOthers);
										intnFbOutstandingAmount = Integer
												.parseInt(strCellValue);
										if (!(intnFbOutstandingAmount <= 10000000)) {
											errorFieldCount++;
											errors.add("("
													+ errorFieldCount
													+ ") WC NFB OUTSTANDING Amount should not be greater than 1 cr");
										}
									} else {
										errorFieldCount++;
										errors.add("("
												+ errorFieldCount
												+ ")Invalid WC NFB OUTSTANDING Amount");
									}
								}
							}

							if (celVal[105] != null)// NfbOutstandingDt
													// celVal[105];
							{

								if (celVal[105].toString().trim().length() > 0) {
									
										wc_nfb_os_dt = formatDateCell(celVal[105]);

										if (wc_nfb_os_dt == null) {

											errorFieldCount++;
											errors.add("("
													+ errorFieldCount
													+ ")NFB OUTSTANDING date is Invalid");
										}
									
								}
							}

							if (celVal[106] != null) // WcDisbursementAmt
														// celVal[106]
							{
								if (celVal[106].toString().trim().length() != 0) {
									isValidType = validateNumericField(celVal[106]);
									if (isValidType) {
										int LocalIntWcOthers = (int) celVal[106]
												.getNumericCellValue();
										String strCellValue = String
												.valueOf(LocalIntWcOthers);
										intWcDisbursementAmt = Integer
												.parseInt(strCellValue);
										if (!(intWcDisbursementAmt <= 10000000)) {
											errorFieldCount++;
											errors.add("("
													+ errorFieldCount
													+ ") WC DISBURSEMENT Amount should not be greater than 1 cr");
										}
									} else {
										errorFieldCount++;
										errors.add("("
												+ errorFieldCount
												+ ")Invalid WC DISBURSEMENT Amount");
									}
								}
							}
							// start WcFirstDisbursementDt = celVal[107];

							if (celVal[107] != null)// WcFirstDisbursementDt
													// celVal[107];
							{

								if (celVal[107].toString().trim().length() > 0) {
									
										DateWcFirstDisbursement = formatDateCell(celVal[107]);

										if (DateWcFirstDisbursement == null) {

											errorFieldCount++;
											errors.add("("
													+ errorFieldCount
													+ ")WC FIRST DISBURSEMENT Date is Invalid");
										}
								
								}
							}
					    	if (celVal[108] != null)// WcLastDisbursementDt
													// celVal[108];
							{
								if (celVal[108].toString().trim().length() > 0) {
									
										DateWcLastDisbursement = formatDateCell(celVal[108]);

										if (DateWcLastDisbursement == null) {

											errorFieldCount++;
											errors.add("("
													+ errorFieldCount
													+ ")WC LAST DISBURSEMENT Date is Invalid");
										}
									
								}
							}

							if (celVal[109] != null) // LoanTerminationDt
														// celVal[109]
							{
							
									app_expiry_dt = formatDateCell(celVal[109]);
									if (validateFromCurrentDate(app_expiry_dt)) {
										errorFieldCount++;
										errors.add("("
												+ errorFieldCount
												+ ")Loan termination date should be greater than current date");
									}
									
								 if(app_expiry_dt==null) {
									if (celVal[109].toString().trim().length() == 0) {
										errorFieldCount++;
										errors.add("("
												+ errorFieldCount
												+ ")Loan termination date is Required");
									} else {
										errorFieldCount++;
										errors.add("("
												+ errorFieldCount
												+ ")Loan termination date is Invalid");
									}
								}
							} else {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")Loan termination date is Required");
							}
							// end WcLastDisbursementDt = celVal[108];

						} // loan type WC required

						isValidType = validateWC(app_loan_type,
								intApp_wc_fb_sanction_amt,
								intApp_wc_nfb_sanction_amt,
								intApp_wc_fb_credit_amt,
								intApp_wc_nfb_credit_amt,
								app_wc_fb_sanction_dt, app_wc_nfb_sanction_dt,
								wc_interest, wc_commission, wc_fb_os_amt,
								wc_nfb_os_amt, wc_fb_os_dt, wc_nfb_os_dt,
								wc_plr_type, wc_plr);
						if (!isValidType) {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")Invalid Working Capital details");
						}

						if (celVal[110] != null) // //SpreadOverPlr =
													// celVal[110];
						{

							isValidType = validateNumericField(celVal[110]);

							if (isValidType) {

								if (Double.parseDouble(celVal[110].toString()
										.trim()) <= 99.99
										&& celVal[110].toString().trim()
												.length() != 0) {
									sprade_over_plr = celVal[110].getNumericCellValue();
									System.out.println("FixedAssetCoverageRatio is less than "+sprade_over_plr);
								} else {
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ")SPREAD OVER PLR is Invalid");
								}

							} else {
								if (celVal[110].toString().trim().length() != 0) {
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ")SPREAD OVER PLR is Invalid");
								}

							}
						}

						// start RepaymentEqual = celVal[111];
						String isRepaymentEqual = "";
						if (celVal[111] != null) {
							if (celVal[111].toString().trim().length() == 1) {
								isValidType = validateTextField(celVal[111]);
								if (isValidType) {
									if (celVal[111].toString().trim()
											.equalsIgnoreCase("Y")
											|| celVal[111].toString().trim()
													.equalsIgnoreCase("N")) {
										isRepaymentEqual = celVal[111]
												.getStringCellValue();
									} else {
										errorFieldCount++;
										errors.add("("
												+ errorFieldCount
												+ ")Repayment Equal Should be Y or N");
									}
								} else {
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ")Repayment Equal is Invalid");

								}
							} else {
								if (celVal[111].toString().trim().length() != 0) {
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ")Repayment Equal is Invalid");
								}
							}
						}
						// end RepaymentEqual = celVal[111];

						// start TangibleNetworth = celVal[112];
						if (celVal[112] != null) {
							if (celVal[112].toString().trim().length() != 0) {
								isValidType = validateNumericField(celVal[112]);
								if (isValidType) {
									int LocalIntTangibleNetworth = (int) celVal[112]
											.getNumericCellValue();
									String strCellValue = String
											.valueOf(LocalIntTangibleNetworth);
									int intTangibleNetworth = Integer
											.parseInt(strCellValue);
									if (!(intTangibleNetworth <= 10000000)) {
										errorFieldCount++;
										errors.add("("
												+ errorFieldCount
												+ ") Tangible Networth Amount should not be greater than 1 cr");
									}
								} else {
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ")Invalid Tangible Networth");
								}
							}
						}
						// end TangibleNetworth = celVal[112];

						// start FixedAssetCoverageRatio = celVal[113];
						if (celVal[113] != null) {
							isValidType = validateNumericField(celVal[113]);
						//	System.out.println("FixedAssetCoverageRatio "
						//			+ celVal[113]);
							//System.out
								//	.println("FixedAssetCoverageRatio isValidType"
									//		+ isValidType);
							if (isValidType) {

								if (Double.parseDouble(celVal[113].toString()
										.trim()) <= 99.99
										&& celVal[113].toString().trim()
												.length() != 0) {
									intFixedAssetCoverageRatio = celVal[113]
											.getNumericCellValue();
									//System.out
										//	.println("FixedAssetCoverageRatio is less than 99.99 ");
								} else {
									errorFieldCount++;
									errors.add("("
											+ errorFieldCount
											+ ")Fixed Asset Coverage Ratio is Invalid");
								}

							} else {
								if (celVal[113].toString().trim().length() != 0) {
									errorFieldCount++;
									errors.add("("
											+ errorFieldCount
											+ ")Fixed Asset Coverage Ratio is Invalid");
								}

							}
						}
						// end FixedAssetCoverageRatio = celVal[113];

						// start CurrentRatio = celVal[114];
				//		System.out.println("CurrentRatio " + celVal[114]);
						if (celVal[114] != null) {
							isValidType = validateNumericField(celVal[114]);
						//	System.out.println("CurrentRatio isValidType"
								//	+ isValidType);
							if (isValidType) {
								if (Double.parseDouble(celVal[114].toString()
										.trim()) <= 99.99
										&& celVal[114].toString().trim()
												.length() != 0) {
									intCurrentRatio = celVal[114]
											.getNumericCellValue();
									//System.out
									//		.println("FixedAssetCoverageRatio is less than 99.99 ");
								} else {
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ")Current Ratio is Invalid");
								}
							} else {
								if (celVal[114].toString().trim().length() != 0) {
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ")Current Ratio is Invalid");
								}
							}

						}
						// end CurrentRatio = celVal[114];

						// start MinimumDscr = celVal[115];
						int intMinimumDscr = 0;
						if (celVal[115] != null  ) {
							
							
							isValidType = validateNumericField(celVal[115]);
							if (isValidType) {
								int LocalIntMinimumDscr = (int) celVal[115]
										.getNumericCellValue();
								String strCellValue = String
										.valueOf(LocalIntMinimumDscr);
								intMinimumDscr = Integer.parseInt(strCellValue);
								if (!(intMinimumDscr <= 10000000)) {
									errorFieldCount++;
									errors.add("("
											+ errorFieldCount
											+ ") Minimum DSCR Amount should not be greater than 1 cr");
								}
							} else {
								
								if(celVal[115].toString().length()!=0)
								{
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")Invalid Minimum DSCR");
								}
							}
						}
						// end MinimumDscr = celVal[115];

						// start AverageDscr = celVal[116];
						int intAverageDscr = 0;
						if (celVal[116] != null) {
							isValidType = validateNumericField(celVal[116]);
							if (isValidType) {
								int LocalIntAverageDscr = (int) celVal[116]
										.getNumericCellValue();
								String strCellValue = String
										.valueOf(LocalIntAverageDscr);
								intAverageDscr = Integer.parseInt(strCellValue);
								if (!(intAverageDscr <= 10000000)) {
									errorFieldCount++;
									errors.add("("
											+ errorFieldCount
											+ ") Average DSCR Amount should not be greater than 1 cr");
								}
							} else {

								if (celVal[116].toString().trim().length() != 0) {
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ")Invalid Average DSCR");
								}

							}
						}
						// end AverageDscr = celVal[116];

						// start IsPrimarySecurity = celVal[117];
						String is_pri_sec = "";
						if (celVal[117] != null) {
							if (celVal[117].toString().trim().length() == 1) {
								isValidType = validateTextField(celVal[117]);
								if (isValidType) {
									if (celVal[117].toString().trim()
											.equalsIgnoreCase("Y")) {
										is_pri_sec = celVal[117]
												.getStringCellValue();
									} else {
										errorFieldCount++;
										errors.add("("
												+ errorFieldCount
												+ ")Is Primary Security Should be 'Y' ");
									}
								} else {
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ")Is Primary Security is Invalid");
								}
							} else {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")Is Primary Security is Invalid");
							}
						} else {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")Is Primary Security is Required");
						}
						// end IsPrimarySecurity = celVal[117];

						// start Remarks = celVal[118]
						String remarks = "";
						if (celVal[118] != null) {
							if (celVal[118].toString().trim().length() <= 4000) {
								System.out.println("remarks2"+celVal[118].getRichStringCellValue());
								remarks = celVal[118].getStringCellValue();
								System.out.println("remarks1d" + remarks);

							} else {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")Remarks Data is Exceed !");
							}

						}
						// end Remarks = celVal[118]

						// start ConditionsAccepted = celVal[119]
						String conditionExepted = "";
						if (celVal[119] != null) {
							if (celVal[119].toString().trim().length() == 1) {
								isValidType = validateTextField(celVal[119]);
								if (isValidType) {
									if (celVal[119].toString().trim()
											.equalsIgnoreCase("Y")
											|| celVal[119].toString().trim()
													.equalsIgnoreCase("N")) {
										conditionExepted = celVal[119]
												.getStringCellValue();
									} else {
										errorFieldCount++;
										errors.add("("
												+ errorFieldCount
												+ ")Conditions Accepted Flag is Should be 'Y' or 'N' ");
									}
								} else {
									errorFieldCount++;
									errors.add("("
											+ errorFieldCount
											+ ")Conditions Accepted Flag is Invalid");
								}
							} else {
								errorFieldCount++;
								errors.add("("
										+ errorFieldCount
										+ ")Conditions Accepted Flag is Invalid");
							}
						} else {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")Conditions Accepted Flag is Required");
						}
						
			//  Added by DKR for GST No	11072017
						//  Added by DKR for GST No	11072017
						if (celVal[120] != null) {
							 udyogAdharNos = celVal[120].toString();
							if (celVal[120].toString().trim().length() > 11) {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")Invalid Udyog Aadhar No.");
							} else {
								if (!udyogAdharNos.trim().equals("")) {
									Pattern pattern = Pattern.compile("[a-zA-Z]{2}[a-zA-Z0-9]{2}[abdeABDE](?!0{7})[a-zA-Z0-9]{7}");
									Matcher matcher = pattern.matcher(udyogAdharNos);
									if (matcher.matches()) {
									} else {										
										errorFieldCount++;
										errors.add("(" + errorFieldCount
												+ ")Udyog Adhar Number is invalid. Please enter first 2 digits Alphabets and 5th digits must belong to ['A','B','D','E'] and last 7 digits are numbers(all &ne; '0') respectively.");
									}
								}								
							}
						}
						                      
						if (celVal[121] != null) {
							isValidType = validateNumericField(celVal[121]); // Bank A/c No
							if (isValidType) {
								long bankAccountN = (long)celVal[121].getNumericCellValue();
								bankAcNo = String.valueOf(bankAccountN);								
								if (bankAcNo.length()>10) {
									isValidState = true;
								} else {
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ")Invalid Loan A/c Number");
								}
							} else {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")Loan A/c No. is Required.");
							}
						} else {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")Loan A/c No. is Required.");
						}
												
						//-------------------------------------
						  											
					 if (celVal[122] != null) {
						            // System.out.println("ApplicationProcessor...........1..................."+bankIds+"----------------"+celVal[122]);
									isValidType = validateTextField(celVal[122]); // STATE_CODE
									if (isValidType) {																
										stateCodes = celVal[122].getStringCellValue();								
										if (stateCodes.length()>=2) {
											 isValidState = true;
											System.out.println("ApplicationProcessor............2.................."+bankIds);
											 gstNo=registration.getGstNo(stateCodes,bankIds);
											 if((gstNo!=null || !gstNo.equals(""))){	
												 //System.out.println("ApplicationProcessor.....3...(gstNo!=null || !gstNo.equals()) && !bankIds.equals(0000))............."+gstNo);											  
												 isValidState = true;
											/*}else if(bankIds.equals("0000") && gstNo==null || gstNo.equals("") ){
											  gstNo = "N/A";                                                                // GST SET VAL
											 // System.out.println("ApplicationProcessor.....4....gstNo....................."+gstNo);
											  isValidState = true;*/
											}else{
											errorFieldCount++;
											errors.add("(" + errorFieldCount
													+ ")Invalid STATE_CODE, not mapped with GSTIN No.");
											}
										} else {
											errorFieldCount++;
											errors.add("(" + errorFieldCount
													+ ")Invalid STATE_CODE, not mapped with GSTIN No.");
										}
									} else {
										errorFieldCount++;
										errors.add("(" + errorFieldCount
												+ ")STATE_CODE is required for GSTIN No.");
									}
								} else {
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ")STATE_CODE is required for GSTIN No.");
								}
					 
//====================================================================================================================================================
						 // HYBRIDSECURITY_FLAG
							if (celVal[123] != null) {
								if (celVal[123].toString().trim().length() == 1) {
									isValidType = validateTextField(celVal[123]);
									if (isValidType) {
										if (celVal[123].toString().trim().equalsIgnoreCase("Y")|| celVal[123].toString().trim().equalsIgnoreCase("N")) {
											hybridSec_Flag = celVal[123].getStringCellValue();
											 System.out.println("hybridSec_Flag.....4....hybridSec_Flag....................."+hybridSec_Flag);
										} else {
											errorFieldCount++;
											errors.add("("+ errorFieldCount + ")Hybrid Security Flag is Should be 'Y' or 'N' ");
										}
									} else {
										errorFieldCount++;
										errors.add("("	+ errorFieldCount+ ")Hybrid Security Flag is Invalid");
									}
								} else {
									errorFieldCount++;
									errors.add("("
											+ errorFieldCount
											+ ")Hybrid Security Flag is Invalid");
								}
							} else {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")Hybrid Security Flag is Required");
							}
					 
					 if (celVal[124] != null && hybridSec_Flag.trim().equalsIgnoreCase("Y")) {
							isValidType = validateNumericField(celVal[124]); // MOVCOLLATERATLSECURITY_AMT

							if (isValidType) {
								movCollactSec_Amt = (double)celVal[124].getNumericCellValue();		
								System.out.println("movCollactSec_Amt.....4....movCollactSec_Amt....................."+movCollactSec_Amt);
								if (movCollactSec_Amt>=0.0) {
									isValidState = true;
								} else {
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ")Invalid movable collactral security amount");
								}
							} else {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")Movable collactral security amount is required.");
							}
						 } else if (celVal[124] == null && hybridSec_Flag.trim().equalsIgnoreCase("Y")) {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")Movable collactral security amount is required.");
						 }
					 
					 
					 if (celVal[125] != null && hybridSec_Flag.trim().equalsIgnoreCase("Y")) {
							isValidType = validateNumericField(celVal[125]); // IMMOVCOLLATERATLSECURITY_AMT
							if (isValidType) {								
								imMovCollactSec_Amt  = (double)celVal[125].getNumericCellValue();			
								System.out.println("imMovCollactSec_Amt.....4....imMovCollactSec_Amt....................."+imMovCollactSec_Amt);
								if (imMovCollactSec_Amt>=0.0) {
									isValidState = true;
								} else {
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ")Invalid immovable collactral security Amount");
								}
							} else {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")Immovable collactral security amount is Required.");
							}
						} else if (celVal[125] == null && hybridSec_Flag.trim().equalsIgnoreCase("Y")) {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")Immovable collactral security amount is Required.");
						}
					 
					 
					 
					 if (celVal[126] != null) {
							isValidType = validateNumericField(celVal[126]); // CHIEF_PROMOTER_MOBILE
							if (isValidType) {
								chiefPromt_Mob  = (long)celVal[126].getNumericCellValue();
								System.out.println( "chiefPromt_Mob.....4....chiefPromt_Mob....................."+chiefPromt_Mob);
								if (chiefPromt_Mob > 0) {			
									// Pattern pattern = Pattern.compile("^[0-9]{10}$");
								   //   Matcher matcher = pattern.matcher(String.valueOf(chiefPromt_Mob));
								      
								     // if (matcher.matches()) {
								    	 // isValidState = true;
								    	  //System.out.println("Phone Number Valid"+chiefPromt_Mob);
								     // }
								     /* else
								      {
								    	  errorFieldCount++;
											errors.add("(" + errorFieldCount
													+ ")Chief promoter's mobile number is invalid");
								      }
									*/
									isValidState = true;
								} else {
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ")Chief promoter's mobile number is invalid");
								}
							} else {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")Chief promoter's mobile number is required.");
							}
						} else {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")Chief promoter's mobile number is required.");
						}
					
					 //==================expoId
						if (celVal[127] != null) {
									            // System.out.println("ApplicationProcessor...........1..................."+bankIds+"----------------"+celVal[122]);
												isValidType = validateTextField(celVal[127]); // EXPO_ID_FOREN
												if (isValidType) {																
												String	expoIdd = celVal[127].getStringCellValue();								
													if (expoIdd.equalsIgnoreCase("Y") || expoIdd.equalsIgnoreCase("N")) {
														 isValidState = true;
														System.out.println(".....................expoIdd............2.................."+expoIdd);									 
														}else{
														errorFieldCount++;
														errors.add("(" + errorFieldCount
																+ ")Invalid EXPO_ID_FOREN.Either 'Y' or 'N'");
														}
													} else {
														errorFieldCount++;
														errors.add("(" + errorFieldCount
																+ ")Invalid EXPO_ID_FOREN.Either 'Y' or 'N'");
								}//*******************************************************************************************************************************
						 }
						//*********************************************************************** DKR 45 Financial and GREEN/Existing *****************************************
												
						if (celVal[128] != null && (intApp_tl_credit_amt >= 10000000  &&  intApp_tl_credit_amt <= 25000000) && datFlag4UI1==true) {
							isValidType = validateTextField(celVal[128]);
								if (isValidType) {
									if (celVal[128].toString().trim().equalsIgnoreCase("Y") || celVal[128].toString().trim().equalsIgnoreCase("N")) {
										d_promDirDefaltFlg = celVal[128].getStringCellValue();
							  	} else {
										errorFieldCount++;
										errors.add("("+ errorFieldCount + ")Promoter/Director in CRTILC/CIBIL/RBI list of defalulters flag must be 'Y'  or 'N' ");
									}
								} else {
									errorFieldCount++;
									errors.add("("	+ errorFieldCount+ ")Promoter/Director in CRTILC/CIBIL/RBI list of defalulters flag is Invalid");
								}
							
						 } else if (celVal[128] == null && (intApp_tl_credit_amt >= 10000000  &&  intApp_tl_credit_amt <= 20000000) && datFlag4UI1==true) {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")Promoter/Director in CRTILC/CIBIL/RBI list of defalulters flag is Required.");
						 }else {
							d_promDirDefaltFlg="";
						 }
						
						// d_credBureKeyPromScor 1						
							if (celVal[129]!=null && (intApp_tl_credit_amt >= 10000000  &&  intApp_tl_credit_amt <= 20000000) && datFlag4UI1==true) {
								isValidType = validateNumericField(celVal[129]);
							 if (isValidType){								
								 d_credBureKeyPromScor = (int)celVal[129].getNumericCellValue();
								   if(d_credBureKeyPromScor >=300 && d_credBureKeyPromScor <= 900) {
									   isValidState = true;											   
									}else {
										errorFieldCount++;
											errors.add("("
												+ errorFieldCount
												+ "))Invalid Credit Bureau score of Promoter value");
									}
								} else 	if (celVal[129]==null && (intApp_tl_credit_amt >= 10000000  &&  intApp_tl_credit_amt <= 20000000) && datFlag4UI1==true) {
									errorFieldCount++;
									errors.add("("
											+ errorFieldCount
											+ ")Credit Bureau score of Promoter is required.");									
								}else {
									d_credBureKeyPromScor = 0;									
								}
							}				
			  
			  
			   if (celVal[130] != null && (intApp_tl_credit_amt >= 10000000  && intApp_tl_credit_amt <= 20000000) && datFlag4UI1==true) {
					isValidType = validateNumericField(celVal[130]); // d_credBurePromScor2
					if (isValidType) {						
						d_credBurePromScor2=(int)celVal[130].getNumericCellValue();
				     	 if(d_credBurePromScor2 >=300 && d_credBurePromScor2 <= 900) {													
							isValidState = true;
						} else {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")Invalid Credit Bureau score of Promoter2");
						}
					} else {
						errorFieldCount++;
						errors.add("(" + errorFieldCount
								+ ")Credit Bureau score of Promoter2 is required.");
					}
				} else {
					d_credBurePromScor2 = 0;
				}
			   
			   if (celVal[131] != null && (intApp_tl_credit_amt >= 10000000 &&  intApp_tl_credit_amt <= 20000000) && datFlag4UI1==true) {
					isValidType = validateNumericField(celVal[131]); // d_credBurePromScor3
					if (isValidType) {						
					     d_credBurePromScor3 = (int)celVal[131].getNumericCellValue();								
				      if(d_credBurePromScor3 >=300 && d_credBurePromScor3 <= 900) {
						  isValidState = true;
						} else {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")Invalid Credit Bureau score of Promoter3");
						}
					} else {
						errorFieldCount++;
						errors.add("(" + errorFieldCount
								+ ")Credit Bureau score of Promoter3 is required.");
					}
			    } else {
					d_credBurePromScor3=0;					
				}
			   
			   if (celVal[132] != null  && (intApp_tl_credit_amt >= 10000000 && intApp_tl_credit_amt <= 20000000) && datFlag4UI1==true) {
				   
					isValidType = validateNumericField(celVal[132]); // d_credBurePromScor4
					if (isValidType) {						
					     d_credBurePromScor4 = (int)celVal[132].getNumericCellValue();									
				      if(d_credBurePromScor4 >=300 && d_credBurePromScor4 <= 900) {													
							isValidState = true;
						} else {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")Invalid Credit Bureau score of Promoter4");
						}
					} else {
						errorFieldCount++;
						errors.add("(" + errorFieldCount
								+ ")Credit Bureau score of Promoter4 is required.");
					}
				} else {
					d_credBurePromScor4=0;					
				}
			   if (celVal[133] != null  && (intApp_tl_credit_amt >= 10000000 &&  intApp_tl_credit_amt <= 20000000) && datFlag4UI1==true) {
					isValidType = validateNumericField(celVal[133]); // d_credBurePromScor5
					if (isValidType) {
						d_credBurePromScor5 = (int)celVal[133].getNumericCellValue();								
				      if(d_credBurePromScor5 >=300 && d_credBurePromScor5 <= 900) {													
							isValidState = true;
						} else {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")Invalid Credit Bureau score of Promoter5 ");
						}
					} else {
						errorFieldCount++;
						errors.add("(" + errorFieldCount
								+ ")Credit Bureau score of Promoter5 is required.");
					}
				} else {
					d_credBurePromScor5=0;
					/*errorFieldCount++;
					errors.add("(" + errorFieldCount
							+ ")Credit Bureau score of Promoter5 is required.");*/
				}
			   
			 //d_credBureName1  
			   if (celVal[134] != null   &&  (intApp_tl_credit_amt >= 10000000  && intApp_tl_credit_amt <= 20000000) && datFlag4UI1==true) {
						isValidType = validateTextField(celVal[134]);
						if (isValidType) {
							d_credBureName1 = celVal[134].getStringCellValue();					  	
						} else {
							errorFieldCount++;
							errors.add("("	+ errorFieldCount+ ")Credit bureau name1 is Required.");
						}
					
				} else if (celVal[134] == null   &&  (intApp_tl_credit_amt >= 10000000  && intApp_tl_credit_amt <= 20000000) && datFlag4UI1==true) {
					errorFieldCount++;
					errors.add("(" + errorFieldCount
							+ ")Credit bureau name1 is Required.");
				  }	else{				
					d_credBureName1="";					
				}
			   
      //d_credBureName2  
			   if (celVal[135] != null   &&  (intApp_tl_credit_amt >= 10000000  &&  intApp_tl_credit_amt <= 20000000) && datFlag4UI1==true) {
						isValidType = validateTextField(celVal[135]);
						if (isValidType) {
							d_credBureName2 = celVal[135].getStringCellValue();					  	
						} else {
							errorFieldCount++;
							errors.add("("	+ errorFieldCount+ ")Credit bureau name2 is invalid.");
						}					
				} else {
					d_credBureName2="";					
				}
			  
			   //d_credBureName3  										   
			   if (celVal[136] != null   &&  (intApp_tl_credit_amt >= 10000000  &&  intApp_tl_credit_amt <= 20000000) && datFlag4UI1==true) {
						isValidType = validateTextField(celVal[136]);
						if (isValidType) {
							d_credBureName3 = celVal[136].getStringCellValue();					  	
						} else {
							errorFieldCount++;
							errors.add("("	+ errorFieldCount+ ")Credit bureau name3 is invalid.");
						}
				
				} else {
					d_credBureName3="";					
				}
			  
			 //d_credBureName4 										   
			   if (celVal[137] != null && (intApp_tl_credit_amt >= 10000000  &&  intApp_tl_credit_amt <= 20000000) && datFlag4UI1==true) {
				 isValidType = validateTextField(celVal[137]);
					if (isValidType) {
						d_credBureName4 = celVal[137].getStringCellValue();					  	
					} else {
						errorFieldCount++;
						errors.add("("	+ errorFieldCount+ ")Credit bureau name4 is invalid.");
					}
			
			} else {
				d_credBureName4="";
			}
			   //d_credBureName5 										   
			   if (celVal[138] != null && (intApp_tl_credit_amt >= 10000000  &&  intApp_tl_credit_amt <= 20000000) && datFlag4UI1==true) {
						isValidType = validateTextField(celVal[138]);
							if (isValidType) {
								d_credBureName5 = celVal[138].getStringCellValue();					  	
							} else {
								errorFieldCount++;
								errors.add("("	+ errorFieldCount+ ")Credit bureau name5 is invalid.");
							}
						
					} else {
						d_credBureName5="";
					}
			 //  d_cibilFirmMsmeRank  --------------------------					   
					   
					   if (celVal[139] != null  && (intApp_tl_credit_amt >= 10000000  &&  intApp_tl_credit_amt <= 20000000) && datFlag4UI1==true) // d_credBureKeyPromScor								
						{
							//if (celVal[139].toString().trim().length() != 0) {
								isValidType = validateNumericField(celVal[139]);
								if (isValidType) {
						     	 d_cibilFirmMsmeRank = (int)celVal[139].getNumericCellValue();
								   if(d_cibilFirmMsmeRank >= 0 && d_cibilFirmMsmeRank < 11) {
									   isValidState = true;											   
									}else {
										errorFieldCount++;
											errors.add("("
												+ errorFieldCount
												+ "))Invalid CIBIL MSME Rank of the firm (Between 0-11)");
									}
								} else {
									errorFieldCount++;
									errors.add("("
											+ errorFieldCount
											+ ")CIBIL MSME Rank of the firm is required (Between 0-11)");
								}
							//}
						}else if (celVal[139] == null  && (intApp_tl_credit_amt >= 10000000  && intApp_tl_credit_amt <= 20000000) && datFlag4UI1==true) {						
							errorFieldCount++;
							errors.add("("
									+ errorFieldCount
									+ ")CIBIL MSME Rank of the firm is required.");
							
				     	}else {
							d_cibilFirmMsmeRank=0;
						}
					   
			   
			//d_expCommerScor 					  					   
			   if (celVal[140] != null  && (intApp_tl_credit_amt >= 10000000  && intApp_tl_credit_amt <= 20000000) && datFlag4UI1==true) // d_expCommerScor								
				{
					if (celVal[140].toString().trim().length() != 0) {
						isValidType = validateNumericField(celVal[140]);
						if (isValidType) {
				     	 d_expCommerScor = (int)celVal[140].getNumericCellValue();
						   if(d_expCommerScor >=300 || d_expCommerScor <= 900) {
							   isValidState = true;											   
							}else {
								errorFieldCount++;
									errors.add("("
										+ errorFieldCount
										+ "))Invalid  EXPERIENCE COMMERCIAL SCORE.(Between 300-900)");
							}
						} else {
							errorFieldCount++;
							errors.add("("
									+ errorFieldCount
									+ ")  EXPERIENCE COMMERCIAL SCORE(Between 300-900) is required.");
						}
					}else  if (celVal[140] == null  && (intApp_tl_credit_amt >= 10000000  && intApp_tl_credit_amt <= 20000000) && datFlag4UI1==true) // d_expCommerScor								
					{
						errorFieldCount++;
					       errors.add("("
							+ errorFieldCount
							+ ") EXPERIENCE COMMERCIAL SCORE(Between 300-900) is required.");			
				    }else {
						d_expCommerScor=0;
					}
				}
		// d_promBorrNetWorth	
			   if (celVal[141] != null && (intApp_tl_credit_amt >= 10000000  && intApp_tl_credit_amt <= 20000000) && datFlag4UI1==true) {
					isValidType = validateNumericField(celVal[141]); // d_promBorrNetWorth
					if (isValidType) {								
						d_promBorrNetWorth  = (float)celVal[141].getNumericCellValue();			
						if (d_promBorrNetWorth>=0.0) {
							isValidState = true;
						} else {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")Invalid PROM_BORROWER_NETWORTH Amount");
						}
					} else {
						errorFieldCount++;
						errors.add("(" + errorFieldCount
								+ ")Immovable PROM_BORROWER_NETWORTH amount is Required.");
					}
				} else if (celVal[141] == null && (intApp_tl_credit_amt >= 10000000  &&  intApp_tl_credit_amt <= 20000000) && datFlag4UI1==true) {
					errorFieldCount++;
					errors.add("(" + errorFieldCount
							+ ")Immovable PROM_BORROWER_NETWORTH amount is Required.");
				
				} else {
					d_promBorrNetWorth=0;
					/*errorFieldCount++;
					errors.add("(" + errorFieldCount
							+ ")Immovable PROM_BORROWER_NETWORTH amount is Required.");*/
				}
			   
			 //d_promContribution 					  
			  // ===============================================
					   if (celVal[142] != null && (intApp_tl_credit_amt >= 10000000  &&  intApp_tl_credit_amt <= 20000000) && datFlag4UI1==true) {
							isValidType = validateNumericField(celVal[142]); // d_promContribution
							if (isValidType) {
							     d_promContribution = (int)celVal[142].getNumericCellValue();							
								if (d_promContribution >= 0 && d_promContribution <= 100 ) {														
									isValidState = true;
								} else {
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ")Invalid PROM_CONTRIBUTION, must be between (0-100).");
								}
							} else {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")PROM_CONTRIBUTION(%) is required, must be between (0-100).");
							}
						}  else if (celVal[142] == null && (intApp_tl_credit_amt >= 10000000  &&  intApp_tl_credit_amt <= 20000000) && datFlag4UI1==true) {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")PROM_CONTRIBUTION(%) is required, must be between (0-100).");
						
						} else {
							d_promContribution=0;
							/*errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")PROM_CONTRIBUTION(%) is required.");*/
						}	   
			//=============================================
				  //d_promGAssoNPA1YrFlg						   
				  if (celVal[143] != null  && (intApp_tl_credit_amt >= 10000000  &&  intApp_tl_credit_amt <= 20000000) && datFlag4UI1==true) {
							isValidType = validateTextField(celVal[143]);
							if (isValidType) {
								if (celVal[143].toString().trim().equalsIgnoreCase("Y")|| celVal[143].toString().trim().equalsIgnoreCase("N")) {
									d_promGAssoNPA1YrFlg = celVal[123].getStringCellValue();
						  	} else {
									errorFieldCount++;
									errors.add("("+ errorFieldCount + ")Group/Associate entities of promoter's have been into NPA Category in past 1 year flag must be 'Y' or 'N' ");
								}
							} else {
								errorFieldCount++;
								errors.add("("	+ errorFieldCount+ ")Group/Associate entities of promoter's have been into NPA Category in past 1 year flag is Invalid");
							}
						
					} else if (celVal[143] == null && (intApp_tl_credit_amt >= 10000000  && intApp_tl_credit_amt <= 20000000) && datFlag4UI1==true) {
						errorFieldCount++;
						errors.add("(" + errorFieldCount
								+ ")Group/Associate entities of promoter's have been into NPA Category in past 1 year flag is Invalid");
					
					} else {
						d_promGAssoNPA1YrFlg="";
						
					}
					
			   
				 //d_promBussExpYr  
				   if (celVal[144] != null && (intApp_tl_credit_amt >= 10000000  &&  intApp_tl_credit_amt <= 20000000) && datFlag4UI1==true) {
						isValidType = validateNumericField(celVal[144]); // d_promBussExpYr
						if (isValidType) {
							d_promBussExpYr = (int)celVal[144].getNumericCellValue();								
							if (d_promBussExpYr >= 0 && d_promBussExpYr <= 100 ) {	
								isValidState = true;
							} else {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")Invalid Promoter's experience in current/related business(in year) between 0 to 100 only");
							}
						} else {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")Promoter's experience in current/related business(in year) is required.");
						}
					}  else if (celVal[144] == null && (intApp_tl_credit_amt >= 10000000 && intApp_tl_credit_amt <= 20000000) && datFlag4UI1==true) {
						errorFieldCount++;
						errors.add("(" + errorFieldCount
								+ ")Promoter's experience in current/related business(in year) is required.");					
					} else {
						d_promBussExpYr=0;						
					}
				 
				// d_salesRevenue	
				   if (celVal[145] != null &&  (intApp_tl_credit_amt >= 10000000  && intApp_tl_credit_amt <= 20000000) && datFlag4UI1==true) {
						isValidType = validateNumericField(celVal[145]); // d_salesRevenue
						if (isValidType) {								
							d_salesRevenue  = (float)celVal[145].getNumericCellValue();			
							if (d_salesRevenue>=0.0) {
								isValidState = true;
							} else {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")Invalid Sales/Revenue amount");
							}
						} else {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")Sales/Revenue  amount is Required.");
						}
					}  else if (celVal[145] == null && (intApp_tl_credit_amt >= 10000000 &&  intApp_tl_credit_amt <= 20000000) && datFlag4UI1==true) {
						errorFieldCount++;
						errors.add("(" + errorFieldCount
								+ ")Sales/Revenue amount is Required.");			
					} else {
						d_salesRevenue=0.0f;						
					}
					
				// d_taxPBIT	
				   if (celVal[146] != null  && (intApp_tl_credit_amt >= 10000000  &&  intApp_tl_credit_amt <= 20000000) && datFlag4UI1==true) {
						isValidType = validateNumericField(celVal[146]); // d_taxPBIT
						if (isValidType) {								
							d_taxPBIT  = (float)celVal[146].getNumericCellValue();			
							if (d_taxPBIT>=0.0) {
								isValidState = true;
							} else {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")Invalid Tax(PBDIT) amount");
							}
						} else {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")Tax(PBDIT) amount is Required.");
						}
					}  else if (celVal[146] == null && (intApp_tl_credit_amt >= 10000000  &&  intApp_tl_credit_amt <= 20000000) && datFlag4UI1==true) {
						errorFieldCount++;
						errors.add("(" + errorFieldCount
								+ ")Tax(PBDIT) amount is Required.");			
					}else {
						d_taxPBIT=0.0f;						
					}
				   
				// d_interestPayment	
				   if (celVal[147] != null  && (intApp_tl_credit_amt >= 10000000  &&  intApp_tl_credit_amt <= 20000000) && datFlag4UI1==true) {
						isValidType = validateNumericField(celVal[147]); // d_interestPayment
						if (isValidType) {								
							d_interestPayment  = (float)celVal[147].getNumericCellValue();			
							if (d_interestPayment>=0.0) {
								isValidState = true;
							} else {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")Invalid Interest payment amount");
							}
						} else {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ") Interest payment amount is Required.");
						}
					} else if (celVal[147] == null && (intApp_tl_credit_amt >= 10000000  &&  intApp_tl_credit_amt <= 20000000) && datFlag4UI1==true) {
						errorFieldCount++;
						errors.add("(" + errorFieldCount
								+ ")Interest payment amount is Required.");			
					} else {
						d_interestPayment=0.0f;
					}
				   
				// d_taxCurrentProvisionAmt	
				   if (celVal[148] != null  && (intApp_tl_credit_amt >= 10000000  &&  intApp_tl_credit_amt <= 20000000) && datFlag4UI1==true) {
						isValidType = validateNumericField(celVal[148]); // d_taxCurrentProvisionAmt
						if (isValidType) {								
							d_taxCurrentProvisionAmt  = (float)celVal[148].getNumericCellValue();			
							if (d_taxCurrentProvisionAmt>=0.0) {
								isValidState = true;
							} else {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")Invalid Tax(Current+provision) amount");
							}
						} else {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ") Tax(Current+provision) amount is Required.");
						}
					} else if (celVal[148] == null && (intApp_tl_credit_amt >= 10000000  &&  intApp_tl_credit_amt <= 20000000) && datFlag4UI1==true) {
						errorFieldCount++;
						errors.add("(" + errorFieldCount
								+ ")Tax(Current+provision) amount is Required.");			
					} else {
						d_taxCurrentProvisionAmt=0.0f;
					}
				   
				// d_totCurrentAssets	
				   if (celVal[149] != null  && (intApp_tl_credit_amt >= 10000000 &&  intApp_tl_credit_amt <= 20000000) && datFlag4UI1==true) {
						isValidType = validateNumericField(celVal[149]); // d_totCurrentAssets
						if (isValidType) {								
							d_totCurrentAssets  = (float)celVal[149].getNumericCellValue();			
							if (d_totCurrentAssets>=0.0f) {
								isValidState = true;
							} else {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")Invalid Total current assets amount");
							}
						} else {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")Total current assets amount is Required.");
						}
					}  else if (celVal[149] == null && (intApp_tl_credit_amt >= 10000000 &&  intApp_tl_credit_amt <= 20000000) && datFlag4UI1==true) {
						errorFieldCount++;
						errors.add("(" + errorFieldCount
								+ "))Total current assets amount is Required.");			
					} else {
						d_totCurrentAssets=0.0f;
					}
				   
				// d_totCurrentLiability	
				   if (celVal[150] != null  && (intApp_tl_credit_amt >= 10000000  &&  intApp_tl_credit_amt <= 20000000) && datFlag4UI1==true) {
						isValidType = validateNumericField(celVal[150]); // d_totCurrentLiability
						if (isValidType) {								
							d_totCurrentLiability  = (float)celVal[150].getNumericCellValue();			
							if (d_totCurrentLiability>=0.0f) {
								isValidState = true;
							} else {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")Invalid Total current liabilities amount");
							}
						} else {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")Total current liabilities amount is Required.");
						}
					}  else if (celVal[150] == null && (intApp_tl_credit_amt >= 10000000  && intApp_tl_credit_amt <= 20000000) && datFlag4UI1==true) {
						errorFieldCount++;
						errors.add("(" + errorFieldCount
								+ "))Total current liabilities amount is Required.");			
					} else {
						d_totCurrentLiability=0.0f;
					}
				   
				// d_totTermLiability	
				   if (celVal[151] != null  && (intApp_tl_credit_amt >= 10000000  &&  intApp_tl_credit_amt <= 20000000) && datFlag4UI1==true) {
						isValidType = validateNumericField(celVal[151]); // d_totTermLiability
						if (isValidType) {								
							d_totTermLiability  = (float)celVal[151].getNumericCellValue();			
							if (d_totTermLiability>=0.0f) {
								isValidState = true;
							} else {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")Invalid Total term liabilities amount");
							}
						} else {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")Total term liabilities amount is Required.");
						}
					} else if (celVal[151] == null && (intApp_tl_credit_amt >= 10000000 && intApp_tl_credit_amt <= 20000000) && datFlag4UI1==true) {
						errorFieldCount++;
						errors.add("(" + errorFieldCount
								+ "))Total term liabilities amount is Required.");			
					}  else {
						d_totCurrentLiability=0.0f;
					}
				   
				// d_exuityCapital	
				   if (celVal[152] != null  && (intApp_tl_credit_amt >= 10000000 && intApp_tl_credit_amt <= 20000000) && datFlag4UI1==true) {
						isValidType = validateNumericField(celVal[152]); // d_exuityCapital
						if (isValidType) {								
							d_exuityCapital  = (float)celVal[152].getNumericCellValue();			
							if (d_exuityCapital>=0.0f) {
								isValidState = true;
							} else {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")Invalid Equity Capital amount");
							}
						} else {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")Equity Capital  amount is Required.");
						}
					}else if (celVal[152] == null && (intApp_tl_credit_amt >= 10000000  && intApp_tl_credit_amt <= 20000000) && datFlag4UI1==true) {
						errorFieldCount++;
						errors.add("(" + errorFieldCount
								+ "))Equity Capital  amount is Required.");			
					} else {
						d_exuityCapital=0.0f;
					}
				   
				// d_preferenceCapital	
				   if (celVal[153] != null  && (intApp_tl_credit_amt >= 10000000  &&  intApp_tl_credit_amt <= 20000000) && datFlag4UI1==true) {
						isValidType = validateNumericField(celVal[153]); // d_preferenceCapital
						if (isValidType) {								
							d_preferenceCapital  = (float)celVal[153].getNumericCellValue();			
							if (d_preferenceCapital>=0.0f) {
								isValidState = true;
							} else {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")Invalid Preference Capital amount");
							}
						} else {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")Preference Capital  amount is Required.");
						}
					}else if (celVal[153] == null && (intApp_tl_credit_amt >= 10000000  &&  intApp_tl_credit_amt <= 20000000) && datFlag4UI1==true) {
						errorFieldCount++;
						errors.add("(" + errorFieldCount
								+ "))Preference Capital  amount is Required.");			
					}  else {
						d_preferenceCapital=0.0f;
					}
				   
					// d_reservesSurplus	
				   if (celVal[154] != null  && (intApp_tl_credit_amt >= 10000000  && intApp_tl_credit_amt <= 20000000) && datFlag4UI1==true) {
						isValidType = validateNumericField(celVal[154]); // d_reservesSurplus
						if (isValidType) {								
							d_reservesSurplus  = (float)celVal[154].getNumericCellValue();			
							if (d_reservesSurplus>=0.0f) {
								isValidState = true;
							} else {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")Invalid Reserves and surplus amount");
							}
						} else {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ "Reserves and surplus  amount is Required.");
						}
					}else if (celVal[154] == null && (intApp_tl_credit_amt >= 10000000  && intApp_tl_credit_amt <= 20000000) && datFlag4UI1==true) {
						errorFieldCount++;
						errors.add("(" + errorFieldCount
								+ "))Reserves and surplus  amount is Required.");			
					}  else {
						d_reservesSurplus=0;
					}
				  //d_repaymentDueNyrAmt 
				   if (celVal[155] != null  && (intApp_tl_credit_amt >= 10000000  && intApp_tl_credit_amt <= 20000000) && datFlag4UI1==true) {
						isValidType = validateNumericField(celVal[155]); // d_repaymentDueNyrAmt
						if (isValidType) {								
							d_repaymentDueNyrAmt  = (float)celVal[155].getNumericCellValue();			
							if (d_repaymentDueNyrAmt>=0.0f) {
								isValidState = true;
							} else {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")Invalid Repayments due within a year's amount");
							}
						} else {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ "Repayments due within a year's amount is Required.");
						}
					} else if (celVal[155] == null && (intApp_tl_credit_amt >= 10000000  &&  intApp_tl_credit_amt <= 20000000) && datFlag4UI1==true) {
						errorFieldCount++;
						errors.add("(" + errorFieldCount
								+ "))Repayments due within a year's amount is Required.");			
					}  else {
						d_repaymentDueNyrAmt=0.0f;
						/*errorFieldCount++;
						errors.add("(" + errorFieldCount
								+ ")Repayments due within a year's amount is Required.");*/
					}
			   // ********************************** DKR **************************************************Colt Green 12
				  
				   //d_existGreenFldUnitType  										   
				   if (celVal[156] != null && (intApp_tl_credit_amt > 1000000  &&  intApp_tl_credit_amt <= 9999999) && datFlag4UI1==true){
						isValidType = validateTextField(celVal[156]); // d_existGreenFldUnitType
						if (isValidType) {
							String dd_existGreenFldUnitType=celVal[156].getStringCellValue(); 
							if (dd_existGreenFldUnitType.trim().equalsIgnoreCase("EXISTING") || dd_existGreenFldUnitType.trim().equalsIgnoreCase("GREENFIELD")) {
								d_existGreenFldUnitType=dd_existGreenFldUnitType;
					  	} else {
								errorFieldCount++;
								errors.add("("+ errorFieldCount + ")Exist or Green Field Unit Type must be  eigther 'EXISTING' or 'GREENFIELD'");
							}
						} else {
							errorFieldCount++;
							errors.add("("	+ errorFieldCount+ ")Exist or Green Field Unit Type must be  eigther 'EXISTING' or 'GREENFIELD'");
						}						
					}  else if (celVal[156] == null  && (intApp_tl_credit_amt > 1000000  &&  intApp_tl_credit_amt <= 9999999) && datFlag4UI1==true) {
						errorFieldCount++;
						errors.add("(" + errorFieldCount+ "))EXISTING or GREENFIELD Unit Type is Required");		
					}else {
						d_existGreenFldUnitType="";
					}
				   
				   //opratIncome 
				   if (celVal[157] != null && (intApp_tl_credit_amt > 1000000  &&  intApp_tl_credit_amt <= 9999999) && datFlag4UI1==true) {
						System.out.println("#########################"+celVal[157].getNumericCellValue());
					   isValidType = validateNumericField(celVal[157]); 
						
						if (isValidType) {								
							d_opratIncome  = (float)celVal[157].getNumericCellValue();	
							System.out.println(isValidType+"#########################"+d_opratIncome);
							if (d_opratIncome >= 0.0f) {
								
							} else {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")Invalid Operating Income amount");
							}
						} else {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")Operating Income amount is Required.");
						}
					}  else if ((celVal[157] == null) && (intApp_tl_credit_amt > 1000000  &&  intApp_tl_credit_amt <= 9999999) && datFlag4UI1==true) {
						errorFieldCount++;
						errors.add("(" + errorFieldCount
								+ "))Operating Income amount is Required.");		
					}else {
						d_opratIncome=0.0f;
					}
				   
				   
				   //profAftTax 
				   if (celVal[158] != null && (intApp_tl_credit_amt > 1000000  &&  intApp_tl_credit_amt <= 9999999) && datFlag4UI1==true) {
						isValidType = validateNumericField(celVal[158]); 
						if (isValidType) {								
							d_profAftTax  = (float)celVal[158].getNumericCellValue();			
							if (d_profAftTax>=0.0f) {
								isValidState = true;
							} else {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")Invalid profit After Tax  amount");
							}
						} else {
							errorFieldCount++;
							errors.add("(" +errorFieldCount+ ") Profit After Tax  amount is required.");
						}
					} else if ((celVal[158] == null ) && (intApp_tl_credit_amt > 1000000  &&  intApp_tl_credit_amt <= 9999999) && datFlag4UI1==true) {
						errorFieldCount++;
						errors.add("(" + errorFieldCount+ ")Profit After Tax  amount is required.");		
					}else {
						d_profAftTax=0.0f;
					}
				   
				   // networth
				   if (celVal[159] != null && (intApp_tl_credit_amt > 1000000  && intApp_tl_credit_amt <= 9999999) && datFlag4UI1==true) {
						isValidType = validateNumericField(celVal[159]); 
						if (isValidType) {								
							d_networth  = (float)celVal[159].getNumericCellValue();			
							if (d_profAftTax>=0.0) {
								isValidState = true;
							} else {
								errorFieldCount++;
								errors.add("(" + errorFieldCount+ ")Invalid Networth  amount");
							}
						} else {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ "Networth amount is Required.");
						}
					} else if ((celVal[159] == null || celVal[159].getNumericCellValue()==0.0) && (intApp_tl_credit_amt > 1000000  && intApp_tl_credit_amt <= 9999999) && datFlag4UI1==true) {
						errorFieldCount++;
						errors.add("(" + errorFieldCount
								+ "))Networth amount is Required..");		
					}else {
						d_networth=0.0f;
					}
				   
				   //debitEqtRatioUnt
				   if (celVal[160] != null  && (intApp_tl_credit_amt > 1000000  &&  intApp_tl_credit_amt <= 5000000) && datFlag4UI1==true) {
						isValidType = validateNumericField(celVal[160]); // 
						if (isValidType) {
							 d_debitEqtRatioUnt = (float)celVal[160].getNumericCellValue();
						    if(d_debitEqtRatioUnt > 0) {													
								isValidState = true;
							} else {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")Invalid Debit Equity Ratio Unit");
							}
						} else {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")Debit Equity Ratio Unit is required.");
						}
					}  else if ((celVal[160] == null || celVal[160].getNumericCellValue()==0.0) && (intApp_tl_credit_amt > 1000000  &&  intApp_tl_credit_amt <= 5000000) && datFlag4UI1==true) {
						errorFieldCount++;
						errors.add("(" + errorFieldCount
								+ ")Debit Equity Ratio Unit is required.");	
					}else {
						d_debitEqtRatioUnt=0;
					}
				   
				 //debitSrvCoverageRatioTl
				   if (celVal[161] != null  && (intApp_tl_credit_amt > 1000000  &&  intApp_tl_credit_amt <= 5000000) && datFlag4UI1==true) {
						isValidType = validateNumericField(celVal[161]); // 
						if (isValidType) {
							 d_debitSrvCoverageRatioTl = (float)celVal[161].getNumericCellValue();
						    if(d_debitSrvCoverageRatioTl > 0.0f) {													
								isValidState = true;
							} else {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")Invalid Debit Service Coverage Ratio TL Unit");
							}
						} else {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")Debit Service Coverage Ratio TL is required.");
						}
					}  else if ((celVal[161] == null || celVal[161].getNumericCellValue()==0.0) && (intApp_tl_credit_amt > 1000000 && intApp_tl_credit_amt <= 5000000) && datFlag4UI1==true) {
						errorFieldCount++;
						errors.add("(" + errorFieldCount
								+ ")Debit Service Coverage Ratio TL is required.");	
					} else {
						d_debitSrvCoverageRatioTl=0.0f;
					}
				   //currentRatioWc
				   if (celVal[162] != null && (intApp_tl_credit_amt > 1000000  && intApp_tl_credit_amt <= 5000000) && datFlag4UI1==true) {
						isValidType = validateNumericField(celVal[162]); // 
						if (isValidType) {
							 d_currentRatioWc = (float)celVal[162].getNumericCellValue();
						    if(d_currentRatioWc > 0.0f) {													
								isValidState = true;
							} else {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")Invalid Current Ratio for Wc");
							}
						}/* else {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")Current Ratio for Wc is required.");
						}*/
					} else {
						d_currentRatioWc=0.0f;
					}
					
				   //debitEqtRatio
				   if (celVal[163] != null && (intApp_tl_credit_amt > 5000000  && intApp_tl_credit_amt <= 9999999) && datFlag4UI1==true) {
						isValidType = validateNumericField(celVal[163]); // 
						if (isValidType) {
							 d_debitEqtRatio = (float)celVal[163].getNumericCellValue();
						    if(d_debitEqtRatio > 0.0f) {													
								isValidState = true;
							} else {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")Invalid Debit Equity Ratio");
							}
						} else {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")Debit Equity Ratio is required.");
						}
					}  else if ((celVal[163] == null || celVal[163].getNumericCellValue()==0.0) && (intApp_tl_credit_amt >= 5000000  && intApp_tl_credit_amt <= 9999999) && datFlag4UI1==true) {
						errorFieldCount++;
						errors.add("(" + errorFieldCount
								+ ")Debit Equity Ratio is required.");	
					} else {
						d_debitEqtRatio=0.0f;						
					}
   
				   //debitSrvCoverageRatio
				   if (celVal[164] != null && (intApp_tl_credit_amt >= 5000000  &&  intApp_tl_credit_amt <= 9999999) && datFlag4UI1==true) {
						isValidType = validateNumericField(celVal[164]); // 
						if (isValidType) {
							 d_debitSrvCoverageRatio = (float)celVal[164].getNumericCellValue();
						    if(d_debitSrvCoverageRatio > 0.0f) {													
								isValidState = true;
							} else {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")Invalid Debit Service Coverage Ratio");
							}
						} else {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")Debit Service Coverage Ratio is required.");
						}
					}  else if ((celVal[164] == null || celVal[164].getNumericCellValue()==0.0) && (intApp_tl_credit_amt >= 5000000  && intApp_tl_credit_amt <= 9999999) && datFlag4UI1==true) {
						errorFieldCount++;
						errors.add("(" + errorFieldCount
								+ ")Debit Service Coverage Ratio is required.");	
					}  else {
						d_debitSrvCoverageRatio=0.0f;
					}
				  
				   // currentRatios
				   if (celVal[165] != null && (intApp_tl_credit_amt >= 5000000  && intApp_tl_credit_amt <= 9999999) && datFlag4UI1==true) {
						isValidType = validateNumericField(celVal[165]); // 
						if (isValidType) {
							 d_currentRatios = (float)celVal[165].getNumericCellValue();
						    if(d_currentRatios > 0.0f) {													
								isValidState = true;
							} else {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")Invalid Current Ratio");
							}
						} else {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")Current Ratio is required.");
						}
					}  else if ((celVal[165] == null || celVal[165].getNumericCellValue()==0.0) && (intApp_tl_credit_amt >= 5000000 && intApp_tl_credit_amt <= 9999999) && datFlag4UI1==true) {
						errorFieldCount++;
						errors.add("(" + errorFieldCount
								+ ")Current Ratio is required.");	
					} else {
						d_currentRatios=0.0f;
					}
				   // creditBureauChiefPromScor
				   if (celVal[166] != null && (intApp_tl_credit_amt >= 5000000 && intApp_tl_credit_amt <= 9999999)  &&  datFlag4UI1==true) {
						isValidType = validateNumericField(celVal[166]); 
						if (isValidType) {
							 d_creditBureauChiefPromScor = (int)celVal[166].getNumericCellValue();
						    if(d_creditBureauChiefPromScor > 0) {													
								isValidState = true;
							} else {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")Invalid Credit Bureo chief Promoter Score");
							}
						} else {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")Credit Bureo chief Promoter Score is required.");
						}
					}else if ((celVal[166] == null || celVal[166].getNumericCellValue()==0) && (intApp_tl_credit_amt >= 5000000  && intApp_tl_credit_amt <= 9999999) && datFlag4UI1==true) {
						errorFieldCount++;
						errors.add("(" + errorFieldCount
								+ ")Credit Bureo chief Promoter Score is required.");	
					}  else {
						d_creditBureauChiefPromScor=0;
					}	   
				   
				   // totalAssets
				   if (celVal[167] != null && (intApp_tl_credit_amt >= 5000000 &&  intApp_tl_credit_amt <= 9999999) && datFlag4UI1==true) {
						isValidType = validateNumericField(celVal[167]); 
						if (isValidType) {								
							d_totalAssets  = (float)celVal[167].getNumericCellValue();			
							if (d_totalAssets>=0.0f) {
								isValidState = true;
							} else {
								errorFieldCount++;
								errors.add("(" + errorFieldCount
										+ ")Invalid Total Assets  amount");
							}
						} else {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")Total Assets  amount is Required.");
						}
					}else if (celVal[167] == null && (intApp_tl_credit_amt >= 5000000 && intApp_tl_credit_amt <= 9999999) && datFlag4UI1==true) {
						errorFieldCount++;
						errors.add("(" + errorFieldCount
								+ ")Total Assets  amount is Required.");	
					}   else {
						d_totalAssets=0.0f;
					}
				   
				   
				//**********************************************************************END***********************************************************
					 if ((dcHandlooms != null) && (cgbid.equals(""))) {
							if ((!sector.equals(""))
									&& (dcHandlooms.equals("Y"))
									&& (!sector
											.equalsIgnoreCase("HANDLOOM WEAVING"))) {
								errorFieldCount++;
								errors.add("("
										+ errorFieldCount
										+ ")Reimbursement of GF/ASF under DC(HL) is eligible only if Industry Sector/Activity of the borrower is 'handloom weaving'. Please refer our Circular No: 61/2012-13 dated 12/06/2012 for details.");
							}
						}

						if ("RRB".equals(classificationofMLI)
								&& (intcredittoguaranteeamount > intrrbValue)) {
							errorFieldCount++;
							errors.add("("
									+ errorFieldCount
									+ ")Maximum 'credit to be guaranteed' amount per eligible borrower for RRBs is caped at Rs.50 lakh. Please refer our <b> Circular No. 50 / 2008  09 </b> dated January 07, 2009");
						}

						if ((dcHandlooms != null) && !dcHandlooms.equals("")) {
							if ((intcredittoguaranteeamount > inthandloomMaxValue)
									&& (dcHandlooms.equals("Y"))) {
								errorFieldCount++;
								errors.add("("
										+ errorFieldCount
										+ ")Credit to be Guaranteed Amount should be within the eligible amount available for Guarantee upto  Rs. 200000 as per ceiling fixed by Office of DC Handlooms");
							}
						}

						if (intcredittoguaranteeamount > intmaxValue) {
							errorFieldCount++;
							errors.add("("
									+ errorFieldCount
									+ ")Credit to be Guaranteed Amount should be within the eligible amount available for Guarantee upto "+ 10000000);
						}

						if ((intprevCoveredAmount + intcredittoguaranteeamount) > 10000000) {
							errorFieldCount++;
							errors.add("("
									+ errorFieldCount
									+ ")Guarantee of Rs. "
									+ intprevCoveredAmount
									+ " is already available for the Borrower. Total Credit to be Guaranteed Amount should be within the eligible amount available for Guarantee upto"
									+ 10000000);
						}

						if (((intprevCoveredAmount + intcredittoguaranteeamount) > 200000)
								&& (dcHandlooms.equals("Y"))) {
							errorFieldCount++;
							errors.add("("
									+ errorFieldCount
									+ ")Guarantee of Rs. "
									+ intprevCoveredAmount
									+ " is already available for the Borrower. Total Credit to be Guaranteed Amount should be within the eligible amount available for Guarantee upto  Rs. 200000 as per ceiling fixed by Office of DC Handlooms");
						}
				//		System.out.println("total errors:" + errorFieldCount);

						Application app = new Application();
						BorrowerDetails bd = new BorrowerDetails();
						SSIDetails sd = new SSIDetails();
						PrimarySecurityDetails ps = new PrimarySecurityDetails();
						TermLoan tl = new TermLoan();
						WorkingCapital wc = new WorkingCapital();
						ProjectOutlayDetails pd = new ProjectOutlayDetails();
						Securitization sec = new Securitization();
						if (errorFieldCount > 0) {
							invalidapps.add(errors);
						}
						
						if (errorFieldCount == 0) {
							bankAppRefNos.add(strBankRefNo);
							// bankAppRefNos.add(strBankRefNo);

							// app.setMliID();
							app.setBankId(user.getBankId());
							app.setZoneId(user.getZoneId());
							app.setBranchId(user.getBranchId());
							// app.setSchemeId(scheme_id);
							app.setLoanType(app_loan_type);
							// app.setCompositeLoan("N");
							// app.setMliRefNo(celVal[1].getStringCellValue());
							app.setMliRefNo(strBankRefNo);
							app.setMliBranchName(celVal[2].getStringCellValue());
							if (celVal[3].getCellType() == Cell.CELL_TYPE_STRING) {									
							String branchCodes = trimTrailingZeros(celVal[3].getStringCellValue());								
								app.setMliBranchCode(branchCodes);
							} else if (celVal[3].getCellType() == Cell.CELL_TYPE_NUMERIC) {								
								app.setMliBranchCode(String.valueOf((int)celVal[3].getNumericCellValue()));
							}

							bd.setPreviouslyCovered(flagValue);// app.getBorrowerDetails().setPreviouslyCovered("Y");						
							sd.setConstitution(celVal[5].getStringCellValue());// app.getBorrowerDetails().getSsiDetails().setConstitution("TESTCONST");
							sd.setSsiType(celVal[6].getStringCellValue());// app.getBorrowerDetails().getSsiDetails().setSsiType("TEST");//
							sd.setSsiName(celVal[7].getStringCellValue());// app.getBorrowerDetails().getSsiDetails().setSsiName("TESTUNIT");
							sd.setAddress(celVal[8].getStringCellValue());// app.getBorrowerDetails().getSsiDetails().setAddress("UNIT@MUMBAI");
							sd.setState(state);// app.getBorrowerDetails().getSsiDetails().setState("MH");
							sd.setDistrict(district);// app.getBorrowerDetails().getSsiDetails().setDistrict("MUMBAI");
							sd.setCity(city);// app.getBorrowerDetails().getSsiDetails().setCity("MUMBAI");
							sd.setPincode(pincode);// app.getBorrowerDetails().getSsiDetails().setPincode("400051");
							sd.setSsiITPan(ITPAN_FIRM);// app.getBorrowerDetails().getSsiDetails().setSsiITPan("MH101MH");//							
							//long ssiReg = Long.parseLong("sd.setRegNo(SSI_REGN_NO);
							//if (celVal[3].getCellType() == Cell.CELL_TYPE_STRING) {	
							//String ssiRegN = String.valueOf(SSI_REGN_NO).split("\\.")[0];
							sd.setRegNo(trimTrailingZeros(SSI_REGN_NO));		
							System.out.println(sd.getRegNo()+"<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<DRKR");
							sd.setIndustryNature(industryNature);// app.getBorrowerDetails().getSsiDetails().setIndustryNature("IT SERVICES");
							sd.setIndustrySector(celVal[16].getStringCellValue());// app.getBorrowerDetails().getSsiDetails().setIndustrySector("BPO");
							sd.setActivityType(celVal[17].getStringCellValue());// app.getBorrowerDetails().getSsiDetails().setActivityType("TESTING");
							sd.setEmployeeNos(employeeNos);// app.getBorrowerDetails().getSsiDetails().setEmployeeNos(10);
							sd.setProjectedSalesTurnover(LocalLongProjectedSales);// app.getBorrowerDetails().getSsiDetails().setProjectedSalesTurnover(1000);
							sd.setProjectedExports(LocalLongPROJECTED_EXPORTS);// app.getBorrowerDetails().getSsiDetails().setProjectedExports(1000);
							sd.setCpTitle(celVal[21].getStringCellValue());// app.getBorrowerDetails().getSsiDetails().setCpTitle("MR");
							sd.setCpFirstName(celVal[22].getStringCellValue());// app.getBorrowerDetails().getSsiDetails().setCpFirstName("TEST");
							sd.setCpMiddleName(celVal[23].getStringCellValue());// app.getBorrowerDetails().getSsiDetails().setCpMiddleName("FILE");
							sd.setCpLastName(celVal[24].getStringCellValue());// app.getBorrowerDetails().getSsiDetails().setCpLastName("UPLOAD");
							sd.setCpParTitle(celVal[25].getStringCellValue());// app.getBorrowerDetails().getSsiDetails().setCpParTitle("MR");
							sd.setCpParFirstName(celVal[26].getStringCellValue());// app.getBorrowerDetails().getSsiDetails().setCpParFirstName("FILE");
							sd.setCpParMiddleName(celVal[27].getStringCellValue());// app.getBorrowerDetails().getSsiDetails().setCpParMiddleName("UPLOAD");
							sd.setCpParLastName(celVal[28].getStringCellValue());// app.getBorrowerDetails().getSsiDetails().setCpParLastName("TEST");
							sd.setCpGender(celVal[29].getStringCellValue());// app.getBorrowerDetails().getSsiDetails().setCpGender("M");
							sd.setCpITPAN(celVal[30].getStringCellValue());// app.getBorrowerDetails().getSsiDetails().setCpITPAN("TEST101");
							sd.setReligion(celVal[31].getStringCellValue());// app.getBorrowerDetails().getSsiDetails().setReligion("N");//DOUBT
							sd.setCpDOB(prom_dob); 							
							sd.setCpLegalID(celVal[33].getStringCellValue());// app.getBorrowerDetails().getSsiDetails().setCpLegalID("L1");
							sd.setCpLegalIdValue(celVal[34].toString()); // app.getBorrowerDetails().getSsiDetails().setCpLegalIdValue("11");
							sd.setFirstName(celVal[35].toString());// app.getBorrowerDetails().getSsiDetails().setFirstName("F1");
							sd.setFirstDOB(PmrFirstDobDt);// app.getBorrowerDetails().getSsiDetails().setFirstDOB(new
															// // Date());
							sd.setFirstItpan(celVal[37].toString());// app.getBorrowerDetails().getSsiDetails().setFirstItpan("I1");
							sd.setSecondName(celVal[38].toString());// app.getBorrowerDetails().getSsiDetails().setSecondName("F2");
							sd.setSecondDOB(PmrSecondDobDt);// app.getBorrowerDetails().getSsiDetails().setSecondDOB(new
															// // Date());
							sd.setSecondItpan(celVal[40].toString());// app.getBorrowerDetails().getSsiDetails().setSecondItpan("I2");
							sd.setThirdName(celVal[41].toString());// app.getBorrowerDetails().getSsiDetails().setThirdName("F3");
							sd.setThirdDOB(PmrThirdDobDt);// app.getBorrowerDetails().getSsiDetails().setThirdDOB(new
															// // Date());
							sd.setThirdItpan(celVal[43].toString());// app.getBorrowerDetails().getSsiDetails().setThirdItpan("I3");
							app.setPrevSSI(celVal[44].getStringCellValue());// app.setPrevSSI("N");
							sd.setWomenOperated(celVal[45].getStringCellValue());// app.getBorrowerDetails().getSsiDetails().setWomenOperated("N");
							sd.setMSE(celVal[46].getStringCellValue());// app.getBorrowerDetails().getSsiDetails().setMSE("N");
							sd.setEnterprise(celVal[47].getStringCellValue());// app.getBorrowerDetails().getSsiDetails().setEnterprise("N");
							pd.setCollateralSecurityTaken(celVal[48]
									.getStringCellValue());// app.getProjectOutlayDetails().setCollateralSecurityTaken("N");
							pd.setThirdPartyGuaranteeTaken(celVal[49]
									.getStringCellValue());// app.getProjectOutlayDetails().setThirdPartyGuaranteeTaken("N");
							app.setDcHandicrafts(dcHandicraft);// app.setDcHandicrafts("N");
							app.setDcHandicraftsStatus(dcHandicraftStatus);// app.setDcHandicraftsStatus("N");
							app.setIcardNo(celVal[52].getStringCellValue());// app.setIcardNo("I01");
							app.setIcardIssueDate(icardDt);// app.setIcardIssueDate(new
							sd.setPhysicallyHandicapped(celVal[54]
									.getStringCellValue());// app.getBorrowerDetails().getSsiDetails().setPhysicallyHandicapped("N");
							app.setJointFinance(jointFinanceFlag);// app.setJointFinance("N");
							app.setJointcgpan(jointCGPAN);// app.setJointcgpan("");
							app.setDcHandlooms(celVal[57].getStringCellValue());// app.setDcHandlooms("N");
							app.setWeaverCreditScheme(celVal[58]
									.getStringCellValue());// app.setWeaverCreditScheme("N");
							app.setHandloomchk(celVal[59].getStringCellValue());// app.setHandloomchk("N");
							app.setHandloomSchName(celVal[60].getStringCellValue());
							app.setInternalRating(celVal[61].getStringCellValue());// app.setInternalRating("R");
							app.setInternalratingProposal(celVal[62].getStringCellValue());// app.setInternalratingProposal("IP");
							app.setInvestmentGrade(celVal[63].getStringCellValue());// app.setInvestmentGrade("IG");
							app.setSubsidyType(celVal[64].toString());
							app.setSubsidyOther(celVal[65].toString());							
							/* MEANS OF FINANCE */
							tl.setAmountSanctioned(intApp_tl_sanction_amt);// app.getTermLoan().setAmountSanctioned(1000);
							pd.setTcPromoterContribution(tl_prom_cont);// app.getProjectOutlayDetails().setTcPromoterContribution(10);
							pd.setTcSubsidyOrEquity(tl_eq_support);// app.getProjectOutlayDetails().setTcSubsidyOrEquity(10);
							pd.setTcOthers(tl_other);// app.getProjectOutlayDetails().setTcOthers(10);
							/* TERM CREDIT DETAILS */
							tl.setAmountSanctionedDate(app_tl_sanction_dt);// app.getTermLoan().setAmountSanctionedDate(new Date());
							tl.setCreditGuaranteed(intApp_tl_credit_amt);// app.getTermLoan().setCreditGuaranteed(1000);
							tl.setAmtDisbursed(intTlAmtDisbursed);// app.getTermLoan().setAmtDisbursed(0);
							tl.setFirstDisbursementDate(formatDateCell(celVal[73]));// app.getTermLoan().setFirstDisbursementDate(new Date());
							tl.setFinalDisbursementDate(formatDateCell(celVal[74]));// app.getTermLoan().setFinalDisbursementDate(new Date());
							tl.setTypeOfPLR(tl_plr_type);// app.getTermLoan().setTypeOfPLR("PL");
							tl.setPlr(tl_plr);// app.getTermLoan().setPlr(10);							
							tl.setInterestType(tl_interest_type);// app.getTermLoan().setInterestType("F");
							tl.setInterestRate(tl_interest);// app.getTermLoan().setInterestRate(10);
							tl.setTenure(tenure);// app.getTermLoan().setTenure(60);
							tl.setRepaymentMoratorium(principal_mora);// app.getTermLoan().setRepaymentMoratorium(0);
							tl.setInterestMoratorium(TL_INTEREST_MORATARIUM);// app.getTermLoan().setInterestMoratorium(0);
							tl.setPeriodicity(periodicity);// app.getTermLoan().setPeriodicity(0);
							tl.setNoOfInstallments(installmentNos);// app.getTermLoan().setNoOfInstallments(0);
							tl.setFirstInstallmentDueDate(formatDateCell(celVal[84]));// app.getTermLoan().setFirstInstallmentDueDate(null);
							tl.setPplOS(IntTlOutstandingAmount);// app.getTermLoan().setPplOS(0);
							tl.setPplOsAsOnDate(formatDateCell(celVal[86]));// app.getTermLoan().setPplOsAsOnDate(null);
							// WC
							wc.setFundBasedLimitSanctioned(intApp_wc_fb_sanction_amt);// app.getWc().setFundBasedLimitSanctioned(0);
							wc.setCreditFundBased(intApp_wc_fb_credit_amt);// app.getWc().setCreditFundBased(0);
							wc.setNonFundBasedLimitSanctioned(intApp_wc_nfb_sanction_amt);// app.getWc().setNonFundBasedLimitSanctioned(0);
							wc.setCreditNonFundBased(intApp_wc_nfb_credit_amt);// app.getWc().setCreditNonFundBased(0);
							wc.setIsTLMarginMoney(celVal[91].getStringCellValue());// app.getWc().setIsTLMarginMoney("N");
							pd.setWcPromoterContribution(intwc_prom_cont);// app.getProjectOutlayDetails().setWcPromoterContribution(10);
							pd.setWcSubsidyOrEquity(intwc_eq_support);// app.getProjectOutlayDetails().setWcSubsidyOrEquity(10);
							pd.setWcOthers(intwc_other);// app.getProjectOutlayDetails().setWcOthers(10);
							double project_cost = intApp_tl_sanction_amt
									+ tl_prom_cont + tl_eq_support + tl_other;

							double wc_assets = intApp_wc_fb_sanction_amt
									+ app_wc_nfb_sanction_amt + wc_prom_cont
									+ wc_eq_support + wc_other;

							pd.setProjectCost(project_cost);// app.getProjectOutlayDetails().setProjectCost(10);
							pd.setWcAssessed(wc_assets);// app.getProjectOutlayDetails().setWcAssessed(10);
							pd.setProjectOutlay((project_cost + wc_assets));// app.getProjectOutlayDetails().setProjectOutlay(100);
							/* WORKING CAPITAL DETAILS */
							wc.setWcTypeOfPLR(wc_plr_type);// app.getWc().setWcTypeOfPLR("PL");
							wc.setWcPlr(wc_plr);// app.getWc().setWcPlr(0);
							wc.setWcInterestType(wc_interest_type);// app.getWc().setWcInterestType("F");
							wc.setWcInterestRate(wc_interest);// app.getWc().setWcInterestRate(10);
							wc.setLimitFundBasedSanctionedDate(app_wc_fb_sanction_dt);// app.getWc().setLimitFundBasedSanctionedDate(new Date());
							wc.setLimitNonFundBasedCommission(wc_commission);// app.getWc().setLimitNonFundBasedCommission(0);
							wc.setLimitNonFundBasedSanctionedDate(app_wc_nfb_sanction_dt);// app.getWc().setLimitNonFundBasedSanctionedDate(new Date());
							wc.setOsFundBasedPpl(intFbOutstandingAmount);// app.getWc().setOsFundBasedPpl(0);
							wc.setOsFundBasedAsOnDate(app_wc_fb_os_dt);// app.getWc().setOsFundBasedAsOnDate(new Date());
							wc.setOsNonFundBasedPpl(intnFbOutstandingAmount);// app.getWc().setOsNonFundBasedPpl(0);
							wc.setOsNonFundBasedAsOnDate(wc_nfb_os_dt);// app.getWc().setOsNonFundBasedAsOnDate(new Date());
							wc.setWcDisbAmt(intWcDisbursementAmt);
							wc.setWcFirstDisbDt(DateWcFirstDisbursement);
							wc.setWcFinalDisbDt(DateWcLastDisbursement);
							app.setAppExpiryDate(app_expiry_dt);// app.setAppExpiryDate(new Date());

							/* SECURITISATION */
							sec.setSpreadOverPLR(sprade_over_plr);// app.getSecuritization().setSpreadOverPLR(0);
							sec.setPplRepaymentInEqual(isRepaymentEqual);// app.getSecuritization().setPplRepaymentInEqual(null);
							sec.setTangibleNetWorth(celVal[112]
									.getNumericCellValue());// app.getSecuritization().setTangibleNetWorth(0);

							if (celVal[113] != null) {
								if (celVal[113].toString().trim().length() == 0) {
									sec.setFixedACR(intFixedAssetCoverageRatio);// app.getSecuritization().setFixedACR(0);
								} else {
									sec.setFixedACR(celVal[113]
											.getNumericCellValue());// app.getSecuritization().setFixedACR(0);
								}
							} else {
								sec.setFixedACR(intFixedAssetCoverageRatio);// app.getSecuritization().setFixedACR(0);
							}

							if (celVal[114] != null) {
								if (celVal[114].toString().trim().length() == 0) {
									sec.setCurrentRatio(intCurrentRatio);// app.getSecuritization().setCurrentRatio(0);
								} else {
									sec.setCurrentRatio(celVal[114]
											.getNumericCellValue());// app.getSecuritization().setCurrentRatio(0);
								}
							} else {
								sec.setCurrentRatio(intCurrentRatio);// app.getSecuritization().setCurrentRatio(0);
							}

							if (celVal[115] != null) {
								if (celVal[115].toString().trim().length() == 0) {
									sec.setMinimumDSCR(intMinimumDscr);// app.getSecuritization().setMinimumDSCR(0);
								} else {
									sec.setMinimumDSCR(celVal[115]
											.getNumericCellValue());// app.getSecuritization().setMinimumDSCR(0);
								}
							} else {
								sec.setMinimumDSCR(intMinimumDscr);// app.getSecuritization().setMinimumDSCR(0);
							}

							if (celVal[116] != null) {
								if (celVal[116].toString().trim().length() == 0) {
									sec.setAvgDSCR(intAverageDscr);// app.getSecuritization().setAvgDSCR(0);
								} else {
									sec.setAvgDSCR(celVal[116]
											.getNumericCellValue());// app.getSecuritization().setAvgDSCR(0);
								}
							} else {
								sec.setAvgDSCR(intAverageDscr);// app.getSecuritization().setAvgDSCR(0);
							}

							pd.setIsPrimarySecurity(celVal[117]
									.getStringCellValue());
							app.setRemarks(remarks);
							sd.setConditionAccepted(conditionExepted);// app.getBorrowerDetails().getSsiDetails().setConditionAccepted("");//NOT
							
							// IN FORMS
							bd.setSsiDetails(sd);
							app.setBorrowerDetails(bd);
							app.setSecuritization(sec);
							pd.setPrimarySecurityDetails(ps);
							app.setProjectOutlayDetails(pd);
							app.setTermLoan(tl);
							app.setWc(wc);
							app.setUserId(user.getUserId());
							if(!udyogAdharNos.equals(null)){
							app.setUdyogAdharNo(udyogAdharNos);
							}else{
								app.setUdyogAdharNo("N/A");
							}			
							app.setBankAcNo(bankAcNo);
							System.out.println("-----------appPro---------->>>> "+app.getBankAcNo());
							app.setStateCode(stateCodes);								
							if((!app.getStateCode().equals("") || !app.getStateCode().equals(null)) && !user.getBankId().equals("0000")){
							gstNo = registration.getGstNo(stateCodes,user.getBankId());							
							app.setGstNo(gstNo.toString());
							System.out.println(gstNo+"....GSTNO............chiefPromt_Mob....."+chiefPromt_Mob);
							}/*else{
								app.setGstNo("N/A");								
							}*/	
							// adding collactral amt
							 System.out.println("hybridSec_Flag..........."+hybridSec_Flag);
						     	app.setHybridSecurity(hybridSec_Flag);						     
								app.setMovCollateratlSecurityAmt(movCollactSec_Amt);
								app.setImmovCollateratlSecurityAmt(imMovCollactSec_Amt);
							    app.setTotalMIcollatSecAmt(app.getMovCollateratlSecurityAmt() + app.getImmovCollateratlSecurityAmt());
							    
								if(hybridSec_Flag.equalsIgnoreCase("Y")) {
									tl.setCreditGuaranteed(tl.getAmountSanctioned() - app.getTotalMIcollatSecAmt());    // intApp_tl_sanction_amt -- intApp_tl_credit_amt
																		
								//	app.setGuaranteeAmount(tl.getAmountSanctioned() - app.getTotalMIcollatSecAmt());
								//	app.setGuaranteeFee(tl.getAmountSanctioned() - app.getTotalMIcollatSecAmt());
					              System.out.println("hybridSec_Flag.equalsIgnoreCase('Y').... setCreditGuaranteed........."+ tl.getCreditGuaranteed());
								}							    
						        System.out.println(app.getTotalMIcollatSecAmt()+"app.getMovCollateratlSecurityAmt()+app.getImmovCollateratlSecurityAmt()............."+app.getMovCollateratlSecurityAmt()+app.getImmovCollateratlSecurityAmt());
							    app.setProMobileNo(chiefPromt_Mob);
							    System.out.println("chiefPromt_Mob..........."+chiefPromt_Mob);
							    app.setExposureFbId(expoIdds);	 
							 if(expoIdds.equalsIgnoreCase("Y")){
								    app.setExposureFbId(expoIdds);
								 } else {// if(expoIdds.equalsIgnoreCase("N")){
									 app.setExposureFbId("0");
							 }							 
							 System.out.println("ExposureFbId..........."+expoIdds);
						  //******************  45 ********************************					 
							    app.setPromDirDefaltFlg(d_promDirDefaltFlg);
								app.setCredBureKeyPromScor(d_credBureKeyPromScor);
								app.setCredBurePromScor2(d_credBurePromScor2);
								app.setCredBurePromScor3(d_credBurePromScor3);
								app.setCredBurePromScor4(d_credBurePromScor4);
								app.setCredBurePromScor5(d_credBurePromScor5);
								app.setCredBureName1(d_credBureName1);
								app.setCredBureName2(d_credBureName2);
								app.setCredBureName3(d_credBureName3);
								app.setCredBureName4(d_credBureName4);
								app.setCredBureName5(d_credBureName5);
								app.setCibilFirmMsmeRank(d_cibilFirmMsmeRank);
								app.setExpCommerScor(d_expCommerScor);
								app.setPromBorrNetWorth(d_promBorrNetWorth);
								app.setPromContribution(d_promContribution);
								app.setPromGAssoNPA1YrFlg(d_promGAssoNPA1YrFlg);
								app.setPromBussExpYr(d_promBussExpYr);
								app.setSalesRevenue(d_salesRevenue);
								app.setTaxPBIT(d_taxPBIT);
								app.setInterestPayment(d_interestPayment);
								app.setTaxCurrentProvisionAmt(d_taxCurrentProvisionAmt);
								app.setTotCurrentAssets(d_totCurrentAssets);
								app.setTotCurrentLiability(d_totCurrentLiability);
								app.setTotTermLiability(d_totTermLiability);
								app.setExuityCapital(d_exuityCapital);
								app.setPreferenceCapital(d_preferenceCapital);
								app.setReservesSurplus(d_reservesSurplus);
								app.setRepaymentDueNyrAmt(d_repaymentDueNyrAmt);
							    	app.setExistGreenFldUnitType(d_existGreenFldUnitType);
									app.setOpratIncome(d_opratIncome);
									app.setProfAftTax(d_profAftTax); 
									app.setNetworth(d_networth);
									app.setDebitEqtRatioUnt(d_debitEqtRatioUnt); 
									app.setDebitSrvCoverageRatioTl(d_debitSrvCoverageRatioTl);
									app.setCurrentRatioWc(d_currentRatioWc); 
									app.setDebitEqtRatio(d_debitEqtRatio); 
									app.setDebitSrvCoverageRatio(d_debitSrvCoverageRatio);
									app.setCurrentRatios(d_currentRatios);
									app.setCreditBureauChiefPromScor(d_creditBureauChiefPromScor);
									app.setTotalAssets(d_totalAssets); 								
							 // ********************** END *******************************************
							validapps.add(app);							
						} // end if(errorFieldCount==0)
					} // end if(i > 0)
					i++;
					// } // end for loop
				}
				counterForRowLimit++;
				// } // end of counterForRowLimit if
			} // end while loop

			// for testing error
			if (invalidapps.size() > 0) {				
				if (validapps.size() > 0) {		
					apptypes = appDAO.uploadAppsIntoInterface(validapps);                 //DKR insertXLSFileData
				}
              /*	dupApp.put("DUPAPPS", invalidapps);        // 2 by dkr
			    return dupApp; */                              
			} else if (invalidapps.size() == 0) {
				if (validapps.size() > 0) {							
		    		apptypes = appDAO.uploadAppsIntoInterface(validapps);                                    // DKR EXL UPLOAD insertXLSFileData
					System.out.println(apptypes.get("CLEARAPPS").toString());
				 System.out.println("dupApp.put(DUPAPPS, invalidapps)......14............."+invalidapps.size());
				}
				/*dupApp.put("DUPAPPS", invalidapps);
				return dupApp;*/
			}			
		//	 apptypes = appDAO.uploadAppsIntoInterfaceV(validapps);			
			//apptypes = new HashMap();			
			if ((invalidapps.size() > 0) && validapps.size() == 0) {
				System.out.println("INVALIDAPPS :2........16.....ap......." + invalidapps.size());
				apptypes = new HashMap();
				apptypes.put("INVALIDAPPS", invalidapps);				
			}
			if (invalidapps.size() > 0) {
				System.out.println("INVALIDAPPS :1.......17.....ap........." + invalidapps);
				apptypes.put("INVALIDAPPS", invalidapps);				
			}
		} catch (FileNotFoundException e) {
			// e.printStackTrace();
		} catch (IOException e) {
			// e.printStackTrace();
			throw new MessageException("Unable to Read Excel File.");
		} catch (Exception e) {  	
			System.out.println("INVALIDAPPS :1.......18....ex....." + invalidapps+"----------"+e.getMessage());
			e.printStackTrace();
			Date d = new Date();
			Log.log(4, "ApplicationProcessor", "insertXLSFileData",
					e.getMessage());

			throw new MessageException(
					e.getMessage()+ "Please Verify the record and upload again. Errors are:"+invalidapps);
		} 
      	finally {
			if (is != null) {
				is.close();
			}
		}
		return apptypes;		
	}

	/*
	 * UploadApp Validation Methods
	 **/
	
	public boolean validateTextFieldMandatory(HSSFCell cell) {
		boolean isValid = false;
		if (cell != null) {

			if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
				if (cell.getStringCellValue().length() > 0)
					isValid = true;
			}
		} else {
			isValid = false;
		}

		return isValid;
	}	

	public boolean validateNumericField(HSSFCell cell) {

		boolean isValid = false;
		
			if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
			isValid = true;
		
		return isValid;
	}
	
	public boolean validateTextField(HSSFCell cell) {
		boolean isValid = false;
		try{
    		if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
    			 System.out.println(cell.getStringCellValue()+"........1.....validateTextField...........cell.getStringCellValue()"+cell.getStringCellValue());
			if (cell.getStringCellValue().length() > 0){
				 System.out.println(cell.getStringCellValue()+".......2......validateTextField...........cell.getStringCellValue() length() > 0"+cell.getStringCellValue());
				isValid = true;
			}else{
					return true;
				}
		}
		}catch(Exception ex){
			//System.out.println(cell.getColumnIndex()+".....................cell.getColumnIndex");
			ex.printStackTrace();
		}
		return isValid;
	}
	
	public String validateFlags(HSSFCell cell) {
		String flag = null;
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_BLANK:
			flag = "B";
			break;
		case Cell.CELL_TYPE_STRING:
			if ("Y".equalsIgnoreCase(cell.getStringCellValue())
					|| "N".equalsIgnoreCase(cell.getStringCellValue())) {
				flag = cell.getStringCellValue().toUpperCase();
			}
		}
		return flag;
	}
	
	public boolean validateITPAN(String itpan) {
		boolean isValid = false;
		String regex = "[A-Z]{5}[0-9]{4}[A-Z]{1}";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(itpan);
		if (matcher.matches())
			isValid = true;

		return isValid;
	}
	
//	public Date formatDateCell(HSSFCell cell) {
////		Date date = null;
////		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
////		if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
////			System.out.println("In formatDateCell numbar"+cell.getDateCellValue());
////			String str = sdf.format(cell.getDateCellValue());
////			try {
////				date = sdf.parse(str);
////			} catch (ParseException e) {
////				date = null;
////			}
////		} else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
////			System.out.println("In formatDateCell string"+cell.getRichStringCellValue());
////			//String str = sdf.format(cell.getRichStringCellValue());
////			SimpleDateFormat sdfString = new SimpleDateFormat("MM/dd/yyyy");
////			try {
////				date = sdfString.parse(cell.getRichStringCellValue().toString());
////				System.out.println("In formatDateCellNew string parsed"+date);
////			} catch (ParseException e) {
////				System.out.println("In formatDateCellNew string exception");
////				date = null;
////			}
////		}
////		return date;
//		
//		Date date = null;
//		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//		if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
//			String str = sdf.format((cell.getDateCellValue()));
//			try {
//				date = sdf.parse(str);
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
//		}
//		return date;
//	}
	
	
	public Date formatDateCellNew(HSSFCell cell)
	{
		Date date=null;
		String dateSapmple=cell.toString();
		Map months= new HashMap();
		months.put( "Jan","1");						
		months.put("Feb","2");						
		months.put("Mar","3");						
		months.put("Apr","4");						
		months.put("May","5");						
		months.put("Jun","6");						
		months.put("Jul","7");						
		months.put("Aug","8");						
		months.put("Sep","9");						
		months.put("Oct","10");						
		months.put("Nov","11");						
		months.put("Dec","12");
		try
		{
			
			if(dateSapmple!=null && dateSapmple.length() > 7)
			{
					if(dateSapmple.startsWith("\""))
					{
						dateSapmple=dateSapmple.replace("\"", "");
					}
					
				
					if(dateSapmple.contains("-") && dateSapmple.split("-").length ==3)
					{
						String splitedStr[] =dateSapmple.split("-");				
						if(months.containsKey(splitedStr[1]))
						{
							dateSapmple=dateSapmple.replaceAll(splitedStr[1], months.get(splitedStr[1]).toString());
							String[] dateStr=dateSapmple.split("-");
							dateSapmple=dateStr[0].concat("/").concat(dateStr[1].concat("/").concat(dateStr[2]));
						
						}
						
					}
				
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					try 
					{
						//sdf.setLenient(false);
						date = sdf.parse(dateSapmple);			
					} catch (ParseException e) 
					{
						date=null;
					}
							

			}
		}
		catch(Exception e)
		{
			
		}
		return date;
	}
	
	
	public Date formatDateCell(HSSFCell cell)
	{
		Date date=null;
		String dateSapmple=cell.toString();
		Map months= new HashMap();
		months.put( "Jan","1");						
		months.put("Feb","2");						
		months.put("Mar","3");						
		months.put("Apr","4");						
		months.put("May","5");						
		months.put("Jun","6");						
		months.put("Jul","7");						
		months.put("Aug","8");						
		months.put("Sep","9");						
		months.put("Oct","10");						
		months.put("Nov","11");						
		months.put("Dec","12");
		try
		{
			
			if(dateSapmple!=null && dateSapmple.length() > 7)
			{
					if(dateSapmple.startsWith("\""))
					{
						dateSapmple=dateSapmple.replace("\"", "");
					}
					
				
					if(dateSapmple.contains("-") && dateSapmple.split("-").length ==3)
					{
						String splitedStr[] =dateSapmple.split("-");				
						if(months.containsKey(splitedStr[1]))
						{
							dateSapmple=dateSapmple.replaceAll(splitedStr[1], months.get(splitedStr[1]).toString());
							String[] dateStr=dateSapmple.split("-");
							dateSapmple=dateStr[0].concat("/").concat(dateStr[1].concat("/").concat(dateStr[2]));
						
						}
						
					}
				
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					try 
					{
						sdf.setLenient(false);
						date = sdf.parse(dateSapmple);			
					} catch (ParseException e) 
					{
						date=null;
					}
							

			}
		}
		catch(Exception e)
		{
			
		}
		return date;
	}
	
	public boolean chkYesNoDecision(HSSFCell cell)
	{
		boolean isValid = false;
		String value=cell.toString().trim();
		if(value.equalsIgnoreCase("N") || value.equalsIgnoreCase("Y"))
		{
			isValid = true;
		}
		return isValid;
	}
	
	public boolean validateFromCurrentDate(Date date) {
		boolean isBefore = false;
		
		if(date!=null)
		{
				Date currentDt = new Date();
		//System.out.println("currentDt="+currentDt);
		//System.out.println("date="+date);
				if (date.compareTo(currentDt) <= 0) {
					isBefore = true;
				}
		}
		return isBefore;
	}
	
	public boolean validateFromLastQuarterDate(Date date) {
		boolean isValid = false;
		
		if(date!=null)
		{
			Calendar today_cal = Calendar.getInstance();
			Calendar input_cal = Calendar.getInstance();
			Date today = new Date();
			today_cal.setTime(today);
			int current_month = today_cal.get(Calendar.MONTH);
			input_cal.setTime(date);
			int input_month = input_cal.get(Calendar.MONTH);
	
			if (((current_month >= 0 && current_month <= 2) && ((input_month >= 0 && input_month <= 2) || (input_month >= 9 && input_month <= 11)))) {
				isValid = true;
			}
			if (((current_month >= 3 && current_month <= 5) && ((input_month >= 3 && input_month <= 5) || (input_month >= 0 && input_month <= 2)))) {
				isValid = true;
			}
			if (((current_month >= 9 && current_month <= 11) && ((input_month >= 9 && input_month <= 11) || (input_month >= 6 && input_month <= 8)))) {
				isValid = true;
			}
		}
		return isValid;
	}
	
	public boolean validateTL(String app_loan_type, int intApp_tl_sanction_amt,
			int intApp_tl_credit_amt, Date app_tl_sanction_dt, double interest,
			double os_amt, Date os_dt, int tenure, int principal_mora,
			String plr_type, double plr) {
		int count = 0;
		boolean isValid = true;
		if ("TC".equalsIgnoreCase(app_loan_type)) {
		//	System.out.println("1st if app_tl_sanction_amt"
			//		+ intApp_tl_sanction_amt + " app_tl_credit_amt"
			//		+ intApp_tl_credit_amt);
			if (intApp_tl_sanction_amt < 999 || intApp_tl_credit_amt < 999) {
			//	System.out.println("1st-A if app_tl_sanction_amt"
				//		+ intApp_tl_sanction_amt + " app_tl_credit_amt"
				//		+ intApp_tl_credit_amt);
				count++;
			}
		//	System.out.println("2nd if tenure" + tenure);
			if (tenure < 12 || tenure > 120) {
			//	System.out.println("2nd A if tenure" + tenure);
				count++;
			}
			//System.out.println("3rd if plr_type" + plr_type);
			if (plr_type == null || plr < 0) {
				//System.out.println("3rd A if plr_type" + plr_type);
				count++;
			}
			//System.out.println("4th if app_tl_sanction_amt "
			//		+ intApp_tl_sanction_amt + " app_tl_credit_amt"
			//		+ intApp_tl_credit_amt);
			if ((intApp_tl_sanction_amt < intApp_tl_credit_amt)) {
			//	System.out.println("4th A if app_tl_sanction_amt "
				//		+ intApp_tl_sanction_amt + " app_tl_credit_amt"
				//		+ intApp_tl_credit_amt);
				count++;
			}
			//System.out.println("5th if app_tl_sanction_dt "
			//		+ app_tl_sanction_dt);

			if (app_tl_sanction_dt != null) {
				//System.out
				//		.println("5th 1 if validateFromCurrentDate(app_tl_sanction_dt) "
					//			+ validateFromCurrentDate(app_tl_sanction_dt));
				if (!validateFromCurrentDate(app_tl_sanction_dt)) {

				//	System.out.println("5th A if app_tl_sanction_dt "
				//			+ app_tl_sanction_dt);
					count++;
				} else {
				//	System.out.println("5th if app_tl_sanction_dt"
				//			+ app_tl_sanction_dt);
				}
				// commected this code by vinod 01-july-2015 for testing
				/*
				 * if (!validateFromLastQuarterDate(app_tl_sanction_dt)) {
				 * count++; }
				 */
			}
		//	System.out.println("6th if principal_mora" + principal_mora);
			if (principal_mora < 0 || principal_mora > 30) {
			//	System.out.println("6th A if principal_mora" + principal_mora);
				count++;
			}
		}
		//System.out.println("7th if app_tl_sanction_amt"
		//		+ intApp_tl_sanction_amt);
		if (intApp_tl_sanction_amt > 0 && intApp_tl_sanction_amt < 999) {
		//	System.out.println("7th A if app_tl_sanction_amt"
			//		+ intApp_tl_sanction_amt);
			count++;
		}

		if (count > 0) {
			isValid = false;
		}

		return isValid;
	}
	
	public boolean validateWC(String app_loan_type,
			int intApp_wc_fb_sanction_amt, int app_wc_nfb_sanction_amt,
			int intApp_wc_fb_credit_amt, int app_wc_nfb_credit_amt,
			Date app_wc_fb_sanction_dt, Date app_wc_nfb_sanction_dt,
			double wc_fb_interest, double wc_nfb_commission,
			double wc_fb_os_amt, double wc_nfb_os_amt, Date wc_fb_os_dt,
			Date wc_nfb_os_dt, String plr_type, double plr) {
		boolean isValid = true;
		int count = 0;
		Date currentDt = new Date();
	//	System.out.println(intApp_wc_fb_sanction_amt
		//		+ "=intApp_wc_fb_sanction_amt=" + intApp_wc_fb_credit_amt
		//	+ "=intApp_wc_fb_credit_amt");
		//System.out.println(app_wc_nfb_sanction_amt
		//		+ "=app_wc_nfb_sanction_amt=" + app_wc_nfb_credit_amt
		//		+ "=app_wc_nfb_credit_amt");
	//	System.out.println(wc_fb_interest + "=wc_fb_interest="
	//			+ app_wc_fb_sanction_dt + "=app_wc_fb_sanction_dt");
	//	System.out.println(app_wc_nfb_sanction_dt + "=app_wc_nfb_sanction_dt="
		//		+ wc_nfb_commission + "wc_nfb_commission");
		if ("WC".equalsIgnoreCase(app_loan_type)) {
			// commected by vinod 22-sep-15 for testing
			if (plr_type == null || plr < 0)
				count++;
			if (intApp_wc_fb_sanction_amt < 999
					|| intApp_wc_fb_credit_amt < 999) {
				count++;
			}
			if (app_wc_nfb_sanction_amt > 0
					&& (app_wc_nfb_credit_amt < 0 || app_wc_nfb_sanction_amt < app_wc_nfb_credit_amt))
				count++;
			if (intApp_wc_fb_sanction_amt > 0
					&& (wc_fb_interest < 0 || app_wc_fb_sanction_dt == null))
				count++;
			if (app_wc_nfb_sanction_amt > 0
					&& (wc_nfb_commission < 0 || app_wc_nfb_sanction_dt == null))
				count++;
			if (intApp_wc_fb_sanction_amt < intApp_wc_fb_credit_amt) {
				count++;
			}
			if (app_wc_nfb_sanction_amt < app_wc_nfb_credit_amt) {
				count++;
			}
			// commected by vinod 07-july-15 for testing
		//	System.out.println("app_wc_fb_sanction_dt" + app_wc_fb_sanction_dt);
			if (app_wc_fb_sanction_dt != null && app_wc_nfb_sanction_dt != null) {
				if (intApp_wc_fb_sanction_amt > 0
						&& (!validateFromCurrentDate(app_wc_fb_sanction_dt) || !validateFromLastQuarterDate(app_wc_nfb_sanction_dt))) {
				//	System.out
				//			.println("validateWC validateFromCurrentDate(app_wc_fb_sanction_dt) ="
					///				+ validateFromCurrentDate(app_wc_fb_sanction_dt)
						//			+ " validateFromLastQuarterDate(app_wc_nfb_sanction_dt)="
						//			+ validateFromLastQuarterDate(app_wc_nfb_sanction_dt));
					count++;
				}
				if ((app_wc_nfb_sanction_amt > 0 && !validateFromCurrentDate(app_wc_nfb_sanction_dt))) {
				//	System.out.println("validateWC2");
					count++;
				}
				if ((app_wc_nfb_sanction_amt > 0 && !validateFromLastQuarterDate(app_wc_nfb_sanction_dt))) {
				//	System.out.println("validateWC3");
					count++;
				}
			}
		}

		if (intApp_wc_fb_sanction_amt > 0 && intApp_wc_fb_sanction_amt < 999) {
			count++;
		}

		if (count > 0) {
			isValid = false;
		}

		return isValid;
	}
	
	public String chkForConstantFieldValue(String value , String fieldName, boolean flag , int indexNo , String mandatoryFor)
	{		
		String message="";
	
		if((value!=null && objExcelModuleMethod.chkStringEmptyOrNot(value)) && flag==true)
		{	
			if(mandatoryFor.equalsIgnoreCase("BOTH"))
			{				
				message=fieldName+ " is Require ";
				if(fieldName.equalsIgnoreCase("HANDLOOM SCHEME NAME") && recordArray[57].equalsIgnoreCase("Y"))
				{
					message=fieldName+ " is Require ";
				}
				else if(fieldName.equalsIgnoreCase("HANDLOOM SCHEME NAME") && !recordArray[57].equalsIgnoreCase("Y"))
				{					
					message="";
				}
			}
			else if(mandatoryFor.equalsIgnoreCase("TC"))
			{
				
				if(!(fieldName.equalsIgnoreCase("WC_TL_UNDER_MARGIN") || fieldName.equalsIgnoreCase("WC_INTEREST_TYPE") ))
				{
					message=fieldName+ " is Require ";
				}
			}
			else
			{
				
				if(!(fieldName.equalsIgnoreCase("TL_INTEREST_TYPE")))
				{
					message=fieldName+ " is Require ";
				}
			}
		}
		else
		{
			value=value.trim();
			switch(indexNo)
			{
				case 0:	
					if(!objExcelModuleMethod.chkStringEmptyOrNot(value))
					{		
						if(!(value.trim().equalsIgnoreCase("TC") || value.trim().equalsIgnoreCase("WC")))
						{							
							message="Invalid Loan Type";
						}						
					}
					else
					{
						message="Loan Type Required";
						 
					}
					
					break;
				case 4:	
					if(!(value.equalsIgnoreCase("y") || value.equalsIgnoreCase("n")) && !objExcelModuleMethod.chkStringEmptyOrNot(value))
					{
						message="Invalid "+fieldName;						
					}					
					break;
				case 6:
					if(!(value.equalsIgnoreCase("M/S") || value.equalsIgnoreCase("Shri") || value.equalsIgnoreCase("Smt") || value.equalsIgnoreCase("Ku") || value.equalsIgnoreCase("MS") || value.equalsIgnoreCase("MR") || value.equalsIgnoreCase("MRS")))
					{
						if(!objExcelModuleMethod.chkStringEmptyOrNot(value))
						{
							message="Invalid "+fieldName;						
						}
					}					
					break;
				case 12:
					if(value!=null  && (value.trim().length() ==6))
					{
						 if(!objExcelModuleMethod.chkNumericField(value))
						 {
							 message="Invalid Pin Code";
						 }
					}
					else if(!objExcelModuleMethod.chkStringEmptyOrNot(value))
					{
						message="Invalid Pin Code";
					}					 
					break;
				case 29:
					if(!(value.equalsIgnoreCase("M") || value.equalsIgnoreCase("F")))
					{
						if(!objExcelModuleMethod.chkStringEmptyOrNot(value))
						{
							message="Invalid "+fieldName;						
						}
					}					
					break;
				case 60:
					if(!(value.equalsIgnoreCase("IHDS") || value.equalsIgnoreCase("CHCDS")))
					{	
						if(!objExcelModuleMethod.chkStringEmptyOrNot(value))
						{
							message="Invalid "+fieldName;						
						}
					}					
					break;
				case 77:
					if(!(value.equalsIgnoreCase("T") || value.equalsIgnoreCase("F")))
					{
						if(!objExcelModuleMethod.chkStringEmptyOrNot(value))
						{
							message="Invalid "+fieldName;						
						}
					}					
					break;
				case 82:
					if(!(value.equalsIgnoreCase("W") || value.equalsIgnoreCase("M") || value.equalsIgnoreCase("Q") || value.equalsIgnoreCase("H") || value.equalsIgnoreCase("Y")))
					{
						if(!objExcelModuleMethod.chkStringEmptyOrNot(value))
						{
							message="Invalid "+fieldName;						
						}
					}					
					break;
				case 117:
					if(!(value.equalsIgnoreCase("y")))
					{
						if(!objExcelModuleMethod.chkStringEmptyOrNot(value))
						{						
								message="Invalid "+fieldName+" ,"+fieldName+" value should be Y only.";		
																		
						}
					}					
					break;
			}
		}
		return message;
	}
	// CVS
	public HashMap insertCSVFileData(FormFile file, User user ,String path)
	throws SQLException, IOException, FileNotFoundException, Exception {

        ArrayList invalidRecord = new ArrayList();
        HashMap FinalRecordResult = new HashMap();
        HashMap FinalRecordResultForJSP = new HashMap();
        ArrayList allBankAppRefNos = new ArrayList();
        HashMap arrForInternalRating = new HashMap();
        ApplicationDAO applicaitonDAO = new ApplicationDAO();
        if(constitutions.size() == 0)
        {
            constitutions.add("PROPRIETARY/INDIVIDUAL");
            constitutions.add("PARTNERSHIP/LIMITED LIABILITY PARTNERSHIP");
            constitutions.add("PRIVATE");
            constitutions.add("PUBLIC");
            constitutions.add("HUF");
            constitutions.add("TRUST");
            constitutions.add("SOCIETY/CO OP");
        }
        ArrayList industrySectors = null;
        Administrator admin = new Administrator();
        if(statesList == null)
            statesList = admin.getAllStates();
        if(industryNatureList == null)
            industryNatureList = admin.getAllIndustryNature();
        ArrayList districtList = null;
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try
        {
            inputStream = file.getInputStream();
            outputStream = new FileOutputStream(new File((new StringBuilder(String.valueOf(path))).append("//Uploaded Files//").append(user.getUserId()).append(".xls").toString()));
            int read = 0;
            byte bytes[] = new byte[1024];
            while((read = inputStream.read(bytes)) != -1) 
                outputStream.write(bytes, 0, read);
            File f2 = new File((new StringBuilder(String.valueOf(path))).append("//Uploaded Files//").append(user.getUserId()).append(".xls").toString());
            Workbook wb = WorkbookFactory.create(f2);
            DataFormatter objDefaultFormat = new DataFormatter();
            FormulaEvaluator objFormulaEvaluator = new HSSFFormulaEvaluator((HSSFWorkbook)wb);
            Sheet sheet = wb.getSheetAt(0);
            Iterator objIterator = sheet.rowIterator();
            int counter = 0;
            String memberId = (new StringBuilder(String.valueOf(user.getBankId()))).append(user.getZoneId()).append(user.getBranchId()).toString();
            allBankAppRefNos = applicaitonDAO.getBankAppRefNumbers(memberId);
            String classificationofMLI = applicaitonDAO.getClassificationMLI(user.getBankId());
            int intrrbValue = 0x4c4b40;
            int inthandloomMaxValue = 0x30d40;
            int intprevCoveredAmount = 0;
            for(; objIterator.hasNext(); FinalRecordResultForJSP.put("FinalResult", FinalRecordResult))
            {
                Row row = (Row)objIterator.next();
                if(counter > 0)
                {
                    String loanType = "";
                    String dcHandlooms = "";
                    String strIndustrySector = "";
                    int intcredittoguaranteeamount = 0;
                    int intFBCredittoguaranteeamount = 0;
                    int intNFBCredittoguaranteeamount = 0;
                    double intrestRate = 0.0D;
                    double baseRate = 0.0D;
                    double wcBaseRate = 0.0D;
                    double wcInterestRate = 0.0D;
                    Date SanctionedDt = null;
                    Date fbSanctionedDt = null;
                    Date nfbSanctionedDt = null;
                    boolean isLoanTypeValid = false;
                    boolean isTlSanctionAmountValid = false;
                    boolean isCreditToGauAmountValid = false;
                    boolean isTLSanctionDtValid = false;
                    boolean isTLTenureValid = false;
                    boolean isTLPrincipleMoratiumValid = false;
                    boolean isTLPlrTypeValid = false;
                    boolean isTLPlrValid = false;
                    boolean isWcFbLimitSanctionedAmtValid = false;
                    boolean isWcNFbLimitSanctionedAmtValid = false;
                    boolean isWcFbCredittoGaurnteeAmtValid = false;
                    boolean isWcNFbCredittoGaurnteeAmtValid = false;
                    boolean isWcFbSanctionedDateValid = false;
                    boolean isWcNFbSanctionedDateValid = false;
                    boolean isWcFbIntrestValid = false;
                    boolean isWcNFbCommisionValid = false;
                    boolean isWcPlrTypeValid = false;
                    boolean isWcPlrValid = false;
                    
                    recordArray = new String[123];
                    for(int i = 0; i <= 122; i++)
                    {
                        Cell cellValue = row.getCell(i);
                        objFormulaEvaluator.evaluate(cellValue);
                        String cellValueStr = objDefaultFormat.formatCellValue(cellValue, objFormulaEvaluator);
                        if(i == 0)
                        {
                            loanType = cellValueStr;
                            String validationMsg = chkForConstantFieldValue(cellValueStr, "Loan Type", true, 0, loanType);
                            recordArray[i] = cellValueStr;
                            if(!validationMsg.equalsIgnoreCase(""))
                                invalidRecord.add(validationMsg);
                            else
                                isLoanTypeValid = true;
                        }
                        if(i == 1)
                        {
                            recordArray[i] = cellValueStr;
                            String validationMsg = objExcelModuleMethod.VerifyAppRefNumber(cellValueStr);
                            if(!validationMsg.equalsIgnoreCase(""))
                                invalidRecord.add(validationMsg);
                            else
                            if(allBankAppRefNos.contains(recordArray[1]))
                                invalidRecord.add((new StringBuilder("Duplicate Bank Application Reference Number :")).append(recordArray[1]).toString());
                            else
                                allBankAppRefNos.add(recordArray[1]);
                        }
                        if(i == 2)
                        {
                            recordArray[i] = cellValueStr;
                            String validationMsg = objExcelModuleMethod.VerifyBranchName(cellValueStr);
                            if(!validationMsg.equalsIgnoreCase(""))
                                invalidRecord.add(validationMsg);
                        }
                        if(i == 3)
                        {
                            recordArray[i] = cellValueStr;
                            String validationMsg = objExcelModuleMethod.VerifyBranchCode(cellValueStr);
                            if(!validationMsg.equalsIgnoreCase(""))
                                invalidRecord.add(validationMsg);
                        }
                        if(i == 4)
                        {
                            recordArray[i] = cellValueStr;
                            String validationMsg = chkForConstantFieldValue(cellValueStr, "ALREADY COVERED IN CGTMSE", true, 4, "BOTH");
                            if(!validationMsg.equalsIgnoreCase(""))
                                invalidRecord.add(validationMsg);
                        }
                        if(i == 5)
                        {
                            recordArray[i] = cellValueStr;
                            String validationMsg = "";
                            if(cellValueStr != null && objExcelModuleMethod.chkLength(cellValueStr, 50))
                            {
                                if(!constitutions.contains(cellValueStr.trim().toUpperCase()))
                                    validationMsg = "Invalid Constitution";
                            } else
                            {
                                validationMsg = "Invalid Constitution";
                                if(objExcelModuleMethod.chkStringEmptyOrNot(cellValueStr))
                                    validationMsg = "Constitution is required";
                            }
                            if(!validationMsg.equalsIgnoreCase(""))
                                invalidRecord.add(validationMsg);
                        }
                        if(i == 6)
                        {
                            recordArray[i] = cellValueStr;
                            String validationMsg = chkForConstantFieldValue(cellValueStr, "SSI TITLE", true, 6, "BOTH");
                            if(!validationMsg.equalsIgnoreCase(""))
                                invalidRecord.add(validationMsg);
                        }
                        if(i == 7)
                        {
                            recordArray[i] = cellValueStr;
                            String validationMsg = objExcelModuleMethod.chkNameValidation(cellValueStr, "SSI NAME", true, 7, "BOTH");
                            if(!validationMsg.equalsIgnoreCase(""))
                                invalidRecord.add(validationMsg);
                        }
                        if(i == 8)
                        {
                            recordArray[i] = cellValueStr;
                            String validationMsg = objExcelModuleMethod.chkNameValidation(cellValueStr, "SSI ADDRESS", true, 8, "BOTH");
                            if(!validationMsg.equalsIgnoreCase(""))
                                invalidRecord.add(validationMsg);
                        }
                        if(i == 9)
                        {
                            recordArray[i] = cellValueStr;
                            String validationMsg = "";
                            if(recordArray[i] != null && !objExcelModuleMethod.chkStringEmptyOrNot(recordArray[i]))
                            {
                                recordArray[i] = cellValueStr.trim().toUpperCase();
                                if(statesList.size() == 0)
                                    LogClass.StepWritter((new StringBuilder("Null stateslist in ==Line8627== in insertCSVFileData method in 'ApplicationProcessor'statesList===")).append(statesList).toString());
                                if(!statesList.contains(recordArray[i]))
                                    validationMsg = "SSI STATE is Invalid";
                                else
                                    validationMsg = "SSI STATE is Required";
                                if(!validationMsg.equalsIgnoreCase(""))
                                {
                                    invalidRecord.add(validationMsg);
                                } else
                                {
                                    districtList = admin.getAllDistricts(recordArray[i]);
                                    if(districtList == null)
                                        invalidRecord.add("Incorrect SSI STATE NAME , kindly refer Master Data excel file.");
                                }
                            }
                            if(i == 10)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = "";
                                if(recordArray[i] != null && !objExcelModuleMethod.chkStringEmptyOrNot(recordArray[i]))
                                {
                                    recordArray[i] = cellValueStr.trim().toUpperCase();
                                    if(!districtList.contains(recordArray[i]))
                                        validationMsg = "SSI DISTRICT is Invalid";
                                } else
                                {
                                    validationMsg = "SSI DISTRICT is Required";
                                }
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 11)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.chkNameValidation(cellValueStr, "SSI CITY", true, 7, "BOTH");
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 12)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = chkForConstantFieldValue(cellValueStr, "PINCODE", true, 12, "BOTH");
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 13)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.panCardValidation(cellValueStr, "ITPAN", true);
                                if(!validationMsg.equalsIgnoreCase("") && !validationMsg.contains("Required"))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 14)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.chkNameValidation(cellValueStr, "SSI REG NO.", false, 14, "BOTH");
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 15)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = "";
                                if(cellValueStr != null)
                                {
                                    cellValueStr = cellValueStr.trim().toUpperCase();
                                    if(industryNatureList != null)
                                    {
                                        for(int ii = 0; ii < industryNatureList.size(); ii++)
                                            industryNatureList.set(ii, industryNatureList.get(ii).toString().toUpperCase());

                                    }
                                    if(!industryNatureList.contains(cellValueStr))
                                    {
                                        validationMsg = "Invalid INDUSTRY_NATURE";
                                    } else
                                    {
                                        industrySectors = admin.getIndustrySectors(cellValueStr);
                                        if(industrySectors != null)
                                        {
                                            for(int ii = 0; ii < industrySectors.size(); ii++)
                                                industrySectors.set(ii, industrySectors.get(ii).toString().toUpperCase());

                                        }
                                    }
                                } else
                                if(objExcelModuleMethod.chkStringEmptyOrNot(cellValueStr))
                                    validationMsg = "INDUSTRY_NATURE is required";
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 16)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = "";
                                if(cellValueStr != null)
                                {
                                    cellValueStr = cellValueStr.trim().toLowerCase();
                                    if(industrySectors != null && !industrySectors.contains(cellValueStr.toUpperCase()))
                                        validationMsg = "Invalid INDUSTRY_SECTOR";
                                } else
                                {
                                    validationMsg = "Invalid INDUSTRY_SECTOR";
                                    if(objExcelModuleMethod.chkStringEmptyOrNot(cellValueStr))
                                        validationMsg = "INDUSTRY_SECTOR is required";
                                }
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                                else
                                    strIndustrySector = cellValueStr.trim().toUpperCase();
                            }
                            if(i == 17)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.chkNameValidation(cellValueStr, "TYPE_OF_ACTIVITY", true, 122, "BOTH");
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 18)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.chkFixNumberValidation(cellValueStr, "NO_OF_EMPL", true, 18, "BOTH");
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 19)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.chkProjectSalesAndExport(cellValueStr, "PROJECTED_SALES", true, 19, "BOTH");
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 20)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.chkProjectSalesAndExport(cellValueStr, "PROJECTED_EXPORTS", false, 19, "BOTH");
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 21)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = chkForConstantFieldValue(cellValueStr, "PROM_TITLE", true, 6, "BOTH");
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 22)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.chkNameValidation(cellValueStr, "PROM_FIRST_NAME", true, 22, "BOTH");
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 23)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.chkNameValidation(cellValueStr, "PROM_MIDDLE_NAME", false, 22, "BOTH");
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 24)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.chkNameValidation(cellValueStr, "PROM_LAST_NAME", true, 22, "BOTH");
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 25)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = chkForConstantFieldValue(cellValueStr, "PROM_TITLE", false, 6, "BOTH");
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 26)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.chkNameValidation(cellValueStr, "PROM_PARENT_FIRST_NAME", false, 17, "BOTH");
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 27)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.chkNameValidation(cellValueStr, "PROM_PARENT_MIDDLE_NAME", false, 17, "BOTH");
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 28)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.chkNameValidation(cellValueStr, "PROM_PARENT_LAST_NAME", false, 17, "BOTH");
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 29)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = chkForConstantFieldValue(cellValueStr, "PROM_GENDER", true, 29, "BOTH");
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 30)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.panCardValidation(cellValueStr, "PROM_ITPAN", true);
                                if(!validationMsg.equalsIgnoreCase("") && !validationMsg.contains("Required"))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 31)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = chkForConstantFieldValue(cellValueStr, "IS_MINORITY", false, 4, "BOTH");
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 32)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.dateValidation(cellValueStr, "PROM_DOB", false, "BOTH");
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 33)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.chkNameValidation(cellValueStr, "LEGAL_TYPE", false, 17, "BOTH");
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 34)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.chkNameValidation(cellValueStr, "LEGAL_ID", false, 22, "BOTH");
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 35)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.chkNameValidation(cellValueStr, "PMR_FIRST_NAME", false, 17, "BOTH");
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 36)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.dateValidation(cellValueStr, "PMR_FIRST_DOB", false, "BOTH");
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 37)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.panCardValidation(cellValueStr, "PMR_FIRST_ITPAN", false);
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 38)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.chkNameValidation(cellValueStr, "PMR_SEC_NAME", false, 17, "BOTH");
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 39)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.dateValidation(cellValueStr, "PMR_SEC_DOB", false, "BOTH");
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 40)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.panCardValidation(cellValueStr, "PMR_SEC_ITPAN", false);
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 41)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.chkNameValidation(cellValueStr, "PMR_THIRD_NAME", false, 17, "BOTH");
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 42)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.dateValidation(cellValueStr, "PMR_THIRD_DOB", false, "BOTH");
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 43)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.panCardValidation(cellValueStr, "PMR_THIRD_ITPAN", false);
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 44)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = chkForConstantFieldValue(cellValueStr, "IS NEW UNIT", true, 4, "BOTH");
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 45)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = chkForConstantFieldValue(cellValueStr, "IS_WOMEN_OPERATED", true, 4, "BOTH");
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 46)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = chkForConstantFieldValue(cellValueStr, "IS_MSE(ACT 2006)", true, 4, "BOTH");
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 47)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = chkForConstantFieldValue(cellValueStr, "MICRO ENTERPRISE(5 LAKH)", true, 4, "BOTH");
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 48)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = chkForConstantFieldValue(cellValueStr, "COLLATERAL_SECURITY_TAKEN", true, 4, "BOTH");
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 49)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = chkForConstantFieldValue(cellValueStr, "THIRD_PARTY_SECURITY_TAKEN", true, 4, "BOTH");
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 50)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = chkForConstantFieldValue(cellValueStr, "IS DC HANDCRAFT", true, 4, "BOTH");
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 51)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = chkForConstantFieldValue(cellValueStr, "IS DC REIMBURSEMENT", true, 4, "BOTH");
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 52)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.chkNameValidation(cellValueStr, "ACC_ICARD", false, 53, "BOTH");
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 53)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.dateValidation(cellValueStr, "ACC_ICARD_ISSUE_DATE", false, "BOTH");
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 54)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = chkForConstantFieldValue(cellValueStr, "IS HANDICRAFT", true, 4, "BOTH");
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 55)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = chkForConstantFieldValue(cellValueStr, "JOINT_FINANCE_FLAG", true, 4, "BOTH");
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 56)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = chkForConstantFieldValue(cellValueStr, "JOINT_CGPAN", false, 4, "BOTH");
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 57)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = chkForConstantFieldValue(cellValueStr, "DC_HANDLOOMS_FLAG", true, 4, "BOTH");
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                                else
                                    dcHandlooms = cellValueStr;
                            }
                            if(i == 58)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = chkForConstantFieldValue(cellValueStr, "DC_WEAVER_CREDIT_SCHEME_FLAG", true, 4, "BOTH");
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 59)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = chkForConstantFieldValue(cellValueStr, "DC_HANLOOMS_CHECK", false, 4, "BOTH");
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 60)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = chkForConstantFieldValue(cellValueStr, "HANDLOOM SCHEME NAME", true, 60, "BOTH");
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 61)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = chkForConstantFieldValue(cellValueStr, "INTERNAL_RATING", true, 4, "BOTH");
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                                else
                                    arrForInternalRating.put("INTERNAL_RATING", cellValueStr);
                            }
                            if(i == 62)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = chkForConstantFieldValue(cellValueStr, "INTERNAL_RATING_PROPOSAL", false, 4, "BOTH");
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 63)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = chkForConstantFieldValue(cellValueStr, "INVESTMENT_GRADE", false, 4, "BOTH");
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 64)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.chkNameValidation(cellValueStr, "SUBSIDY_NAME", false, 64, "BOTH");
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 65)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.chkNameValidation(cellValueStr, "SUBSIDY_OTHER", false, 64, "BOTH");
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 66)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.chkFixNumberValidation(cellValueStr, "TL_SANCTIONED AMOUNT", true, 66, loanType);
                                if(!validationMsg.equalsIgnoreCase(""))
                                {
                                    invalidRecord.add(validationMsg);
                                } else
                                {
                                    isTlSanctionAmountValid = true;
                                    if(loanType.equalsIgnoreCase("TC") && objExcelModuleMethod.ConvertStrToInt(cellValueStr) > 0xf4240)
                                    {
                                        if(objExcelModuleMethod.chkStringEmptyOrNot(recordArray[13]))
                                            invalidRecord.add("ITPAN FIRM is required");
                                        if(objExcelModuleMethod.chkStringEmptyOrNot(recordArray[30]))
                                            invalidRecord.add("ITPAN FIRM is required");
                                    }
                                }
                            }
                            if(i == 67)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.chkFixNumberValidation(cellValueStr, "TL PROMOTER CONTRIBUTION", false, 66, loanType);
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 68)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.chkFixNumberValidation(cellValueStr, "TL EQUITY SUPPORT", false, 66, loanType);
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 69)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.chkFixNumberValidation(cellValueStr, "TL OTHERS", false, 66, loanType);
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 70)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.dateValidation(cellValueStr, "TL_SANCTION_DATE", true, loanType);
                                if(!validationMsg.equalsIgnoreCase(""))
                                {
                                    invalidRecord.add(validationMsg);
                                } else
                                {
                                    isTLSanctionDtValid = true;
                                    if(!"".equals(cellValueStr.trim()))
                                        SanctionedDt = (new SimpleDateFormat("dd/MM/yyyy")).parse(cellValueStr);
                                }
                            }
                            if(i == 71)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.chkFixNumberValidation(cellValueStr, "TL_CREDIT_TO_GUARANTEE", true, 66, loanType);
                                if(!validationMsg.equalsIgnoreCase(""))
                                {
                                    invalidRecord.add(validationMsg);
                                } else
                                {
                                    intcredittoguaranteeamount = objExcelModuleMethod.ConvertStrToInt(cellValueStr);
                                    isCreditToGauAmountValid = true;
                                }
                            }
                            if(i == 72)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.chkFixNumberValidation(cellValueStr, "TL_AMT_DISBURSED", false, 66, loanType);
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 73)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.dateValidation(cellValueStr, "TL_FIRST_DISBURSEMENT_DT", false, loanType);
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 74)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.dateValidation(cellValueStr, "TL_LAST_DISBURSEMENT_DT", false, loanType);
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 75)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.chkNameValidation(cellValueStr, "TL_BASR_RATE_TYPE", true, 17, loanType);
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                                else
                                    isTLPlrTypeValid = true;
                            }
                            if(i == 76)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.chkPersentNumberValidation(cellValueStr, "TL_BASR_RATE", true, loanType);
                                if(!validationMsg.equalsIgnoreCase(""))
                                {
                                    invalidRecord.add(validationMsg);
                                } else
                                {
                                    recordArray[i] = objExcelModuleMethod.CutPersentValue(cellValueStr);
                                    isTLPlrValid = true;
                                    baseRate = Double.parseDouble(objExcelModuleMethod.CutPersentValue(cellValueStr));
                                }
                            }
                            if(i == 77)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = chkForConstantFieldValue(cellValueStr, "TL_INTEREST_TYPE", true, 77, loanType);
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 78)
                            {
                                recordArray[i] = cellValueStr;
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.chkPersentNumberValidation(cellValueStr, "TL_INTEREST_RATE", true, loanType);
                                if(!validationMsg.equalsIgnoreCase(""))
                                {
                                    invalidRecord.add(validationMsg);
                                } else
                                {
                                    recordArray[i] = objExcelModuleMethod.CutPersentValue(cellValueStr);
                                    if(!"".equals(cellValueStr.trim()))
                                        intrestRate = Double.parseDouble(objExcelModuleMethod.CutPersentValue(cellValueStr));
                                }
                            }
                            if(i == 79)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.chkFixNumberValidation(cellValueStr, "TL_TENURE", true, 79, loanType);
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                                else
                                    isTLTenureValid = true;
                            }
                            if(i == 80)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.chkFixNumberValidation(cellValueStr, "TL_PRINCIPAL_MORATARIUM", true, 80, loanType);
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                                else
                                    isTLPrincipleMoratiumValid = true;
                            }
                            if(i == 81)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.chkFixNumberValidation(cellValueStr, "TL_INTEREST_MORATARIUM", false, 80, loanType);
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 82)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = chkForConstantFieldValue(cellValueStr, "PERIODICITY", false, 82, loanType);
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                                else
                                if(cellValueStr.equalsIgnoreCase("m"))
                                    recordArray[i] = "1";
                                else
                                if(cellValueStr.equalsIgnoreCase("q"))
                                    recordArray[i] = "2";
                                else
                                if(cellValueStr.equalsIgnoreCase("h"))
                                    recordArray[i] = "3";
                                else
                                if(cellValueStr.equalsIgnoreCase("y"))
                                    recordArray[i] = "4";
                                else
                                if(cellValueStr.equalsIgnoreCase("w"))
                                    recordArray[i] = "5";
                            }
                            if(i == 83)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.chkFixNumberValidation(cellValueStr, "NO_OF_INSTALLMENTS", true, 83, loanType);
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 84)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.dateValidation(cellValueStr, "TL_FIRST_INSTALMENT_DUE_DT", false, loanType);
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 85)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.chkFixNumberValidation(cellValueStr, "TL_OUTSTANDING AMOUNT", false, 66, loanType);
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 86)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.dateValidation(cellValueStr, "TL_OUTSTANDING_DT", false, loanType);
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 87)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.chkFixNumberValidation(cellValueStr, "WC_FB_LIMIT_SANCTIONED", true, 66, loanType);
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                                else
                                    isWcFbLimitSanctionedAmtValid = true;
                            }
                            if(i == 88)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.chkFixNumberValidation(cellValueStr, "WC_FB_CREDIT_TO_GUARANTEE", true, 66, loanType);
                                if(!validationMsg.equalsIgnoreCase(""))
                                {
                                    invalidRecord.add(validationMsg);
                                } else
                                {
                                    intFBCredittoguaranteeamount = objExcelModuleMethod.ConvertStrToInt(cellValueStr);
                                    isWcFbCredittoGaurnteeAmtValid = true;
                                }
                            }
                            if(i == 89)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.chkFixNumberValidation(cellValueStr, "WC_NFB_LIMIT_SANCTIONED", true, 66, loanType);
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                                else
                                    isWcNFbLimitSanctionedAmtValid = true;
                            }
                            if(i == 90)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.chkFixNumberValidation(cellValueStr, "WC_NFB_CREDIT_TO_GUARANTEE", true, 66, loanType);
                                if(!validationMsg.equalsIgnoreCase(""))
                                {
                                    invalidRecord.add(validationMsg);
                                } else
                                {
                                    intNFBCredittoguaranteeamount = objExcelModuleMethod.ConvertStrToInt(cellValueStr);
                                    isWcNFbCredittoGaurnteeAmtValid = true;
                                }
                            }
                            if(i == 91)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = chkForConstantFieldValue(cellValueStr, "WC_TL_UNDER_MARGIN", false, 4, loanType);
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 92)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.chkFixNumberValidation(cellValueStr, "WC PROMOTER CONTRIBUTION", false, 66, loanType);
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 93)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.chkFixNumberValidation(cellValueStr, "WC EQUITY SUPPORT", false, 66, loanType);
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 94)
                            {
                                validationMsg = objExcelModuleMethod.chkFixNumberValidation(cellValueStr, "WC OTHERS", false, 66, loanType);
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 95)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.chkNameValidation(cellValueStr, "WC_PLR_TYPE", true, 17, loanType);
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                                else
                                    isWcPlrTypeValid = true;
                            }
                            if(i == 96)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.chkPersentNumberValidation(cellValueStr, "WC_PLR", true, loanType);
                                if(!validationMsg.equalsIgnoreCase(""))
                                {
                                    invalidRecord.add(validationMsg);
                                } else
                                {
                                    isWcPlrValid = true;
                                    recordArray[i] = objExcelModuleMethod.CutPersentValue(cellValueStr);
                                    if(!"".equals(cellValueStr.trim()))
                                        wcBaseRate = Double.parseDouble(objExcelModuleMethod.CutPersentValue(cellValueStr));
                                }
                            }
                            if(i == 97)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = chkForConstantFieldValue(cellValueStr, "WC_INTEREST_TYPE", true, 77, loanType);
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 98)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.chkPersentNumberValidation(cellValueStr, "WC_FB_INTEREST", false, loanType);
                                if(!validationMsg.equalsIgnoreCase(""))
                                {
                                    invalidRecord.add(validationMsg);
                                } else
                                {
                                    isWcFbIntrestValid = true;
                                    recordArray[i] = objExcelModuleMethod.CutPersentValue(cellValueStr);
                                    if(!"".equals(cellValueStr.trim()))
                                        wcInterestRate = Double.parseDouble(objExcelModuleMethod.CutPersentValue(cellValueStr));
                                }
                            }
                            if(i == 99)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.dateValidation(cellValueStr, "WC_FB_LIMIT_SANCTIONED_DT", false, loanType);
                                if(!validationMsg.equalsIgnoreCase(""))
                                {
                                    invalidRecord.add(validationMsg);
                                } else
                                {
                                    isWcFbSanctionedDateValid = true;
                                    if(!"".equals(cellValueStr.trim()))
                                        fbSanctionedDt = (new SimpleDateFormat("dd/MM/yyyy")).parse(cellValueStr);
                                }
                            }
                            if(i == 100)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.chkPersentNumberValidation(cellValueStr, "WC_NFB_COMMISSION", false, loanType);
                                if(!validationMsg.equalsIgnoreCase(""))
                                {
                                    invalidRecord.add(validationMsg);
                                } else
                                {
                                    isWcNFbCommisionValid = true;
                                    recordArray[i] = objExcelModuleMethod.CutPersentValue(cellValueStr);
                                }
                            }
                            if(i == 101)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.dateValidation(cellValueStr, "WC_NFB_LIMIT_SANCTIONED_DT", false, loanType);
                                if(!validationMsg.equalsIgnoreCase(""))
                                {
                                    invalidRecord.add(validationMsg);
                                } else
                                {
                                    isWcNFbSanctionedDateValid = true;
                                    if(!"".equals(cellValueStr.trim()))
                                        nfbSanctionedDt = (new SimpleDateFormat("dd/MM/yyyy")).parse(cellValueStr);
                                }
                            }
                            if(i == 102)
                            {
                                recordArray[i] = cellValueStr;
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.chkFixNumberValidation(cellValueStr, "FB_OUTSTANDING AMOUNT ", false, 66, loanType);
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 103)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.dateValidation(cellValueStr, "FB_OUTSTANDING_DT", false, loanType);
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 104)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.chkFixNumberValidation(cellValueStr, "NFB_OUTSTANDING_AMT", false, 66, loanType);
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 105)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.dateValidation(cellValueStr, "NFB_OUTSTANDING_DT", false, loanType);
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 106)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.chkFixNumberValidation(cellValueStr, "WC_DISBURSEMENT_AMT", false, 66, loanType);
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 107)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.dateValidation(cellValueStr, "WC_FIRST_DISBURSEMENT_DT", false, loanType);
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 108)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.dateValidation(cellValueStr, "WC_LAST_DISBURSEMENT_DT", false, loanType);
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 109)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.dateValidation(cellValueStr, "LOAN TERMINATION DATE", true, loanType);
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 110)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.chkPersentNumberValidation(cellValueStr, "SPREAD_OVER_PLR", false, loanType);
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                                else
                                    recordArray[i] = objExcelModuleMethod.CutPersentValue(cellValueStr);
                            }
                            if(i == 111)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = chkForConstantFieldValue(cellValueStr, "REPAYMENT_EQUAL", false, 4, loanType);
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 112)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.chkFixNumberValidation(cellValueStr, "TANGIBLE_NETWORTH", false, 66, loanType);
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 113)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.chkPersentNumberValidation(cellValueStr, "FIXED_ASSET_COVERAGE_RATIO", false, loanType);
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                                else
                                    recordArray[i] = objExcelModuleMethod.CutPersentValue(cellValueStr);
                            }
                            if(i == 114)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.chkPersentNumberValidation(cellValueStr, "CURRENT_RATIO", false, loanType);
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                                else
                                    recordArray[i] = objExcelModuleMethod.CutPersentValue(cellValueStr);
                            }
                            if(i == 115)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.chkFixNumberValidation(cellValueStr, "MINIMUM_DSCR", false, 66, loanType);
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 116)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.chkFixNumberValidation(cellValueStr, "AVERAGE_DSCR", false, 66, loanType);
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 117)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = chkForConstantFieldValue(cellValueStr, "IS_PRIMARY_SECURITY", true, 117, loanType);
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 118)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.chkNameValidation(cellValueStr, "REMARKS", false, 118, loanType);
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 119)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = chkForConstantFieldValue(cellValueStr, "CONDITIONS_ACCEPTED", true, 117, loanType);
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 120)
                            {
                            	     recordArray[i] = cellValueStr;
                                       validationMsg = "";
                                       if(recordArray[i] != null)
                                       {
                                           recordArray[i] = cellValueStr.trim();                                           
                               				Pattern pattern = Pattern
                               						.compile("[a-zA-Z]{2}[a-zA-Z0-9]{2}[abdeABDE](?!0{7})[a-zA-Z0-9]{7}");                               				
                               				Matcher matcher = pattern.matcher(recordArray[i]);
                               				if (matcher.matches()) {

                               				} else {
                               			      validationMsg = "Udyog Adhar Number is invalid. Please enter first 2 digits Alphabets and 5th digits must belong to ['A','B','D','E'] and last 7 digits are numbers(all &ne; '0') respectively.";
                                    	    }                                        	   
                                              // validationMsg = "Udyog Adhar Number is invalid. Please enter first 2 digits Alphabets and 5th digits must belong to ['A','B','D','E'] and last 7 digits are numbers(all &ne; '0') respectively.";
                                       } 
                                       if(!validationMsg.equalsIgnoreCase(""))
                                           invalidRecord.add(validationMsg);
                                  
                            	
                            /*    recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.chkNameValidation(cellValueStr, "UDYOG_ADHAR_NO", false, 120, loanType);
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);*/
                            }
                            
                            if(i == 121)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.chkNameValidation(cellValueStr, "BANK_ACC_NO", false, 121, loanType);
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                            if(i == 122)
                            {
                                recordArray[i] = cellValueStr;
                                validationMsg = objExcelModuleMethod.chkNameValidation(cellValueStr, "STATE_CODE", false, 122, loanType);
                                if(!validationMsg.equalsIgnoreCase(""))
                                    invalidRecord.add(validationMsg);
                            }
                        }
                        if(dcHandlooms != null && !strIndustrySector.equals("") && dcHandlooms.equals("Y") && !strIndustrySector.equalsIgnoreCase("HANDLOOM WEAVING"))
                            invalidRecord.add("Reimbursement of GF/ASF under DC(HL) is eligible only if Industry Sector/Activity of the borrower is 'handloom weaving'. Please refer our Circular No: 61/2012-13 dated 12/06/2012 for details.");
                        long gaurenteeAmount = 0L;
                        if(loanType.equalsIgnoreCase("TC"))
                        {
                            gaurenteeAmount = intcredittoguaranteeamount;
                            if(gaurenteeAmount > 0x1312d00L)
                            {
                                invalidRecord.add("Credit to be Guaranteed Amount can not be more than 2 Cr.");
                            } else
                            {
                                if(gaurenteeAmount > 0x989680L && !objExcelModuleMethod.validatedateSanctionDate(SanctionedDt))
                                    invalidRecord.add(" Sanction date should be after or equal to 01-Jan-2017 when credit to guarantee amount is above 1 Cr.");
                                if(objExcelModuleMethod.validatedateSanctionDate(SanctionedDt))
                                {
                                    if(intrestRate > 14D)
                                        invalidRecord.add("Interest rate[including guarantee fee] can not be more than 14%  , when Sanction Date is after 01-JAN-2017.");
                                } else
                                if(intrestRate > baseRate + 4D)
                                    invalidRecord.add("Difference between Base rate and interest rate  can not be more than 4%(e.g. (base rate + 4)%<= interest rate%).");
                            }
                        } else
                        if(loanType.equalsIgnoreCase("WC"))
                        {
                            gaurenteeAmount = intFBCredittoguaranteeamount + intNFBCredittoguaranteeamount;
                            if(gaurenteeAmount > 0x1312d00L)
                            {
                                invalidRecord.add("Credit to be Guaranteed Amount can not be more than 2 Cr.");
                            } else
                            {
                                if(gaurenteeAmount > 0x989680L && (!objExcelModuleMethod.validatedateSanctionDate(fbSanctionedDt) || !objExcelModuleMethod.validatedateSanctionDate(nfbSanctionedDt)))
                                    invalidRecord.add(" Sanction date [FB / NFB ] should be after or equal to 01-Jan-2017 when credit to guarantee amount [FB+NFB] is above 1 Cr.");
                                if(objExcelModuleMethod.validatedateSanctionDate(fbSanctionedDt) || objExcelModuleMethod.validatedateSanctionDate(nfbSanctionedDt))
                                {
                                    if(wcInterestRate > 14D)
                                        invalidRecord.add("Interest rate[including guarantee fee] can not be more than 14%  , when Sanction Date is after 01-JAN-2017.");
                                } else
                                if(wcInterestRate > wcBaseRate + 4D)
                                    invalidRecord.add("Difference between Base rate and interest rate  can not be more than 4%(e.g. (base rate + 4)%<= interest rate%).");
                            }
                        }
                        boolean checkSchemFlag = false;
                        if("FB".equals(classificationofMLI) && checkSchemFlag == true && gaurenteeAmount > (long)intrrbValue && ("0096".equals(user.getBankId()) || "0096".equals(user.getBankId()))&& ("0111".equals(user.getBankId()) || "0111".equals(user.getBankId())))
                            invalidRecord.add("Maximum 'credit to be guaranteed' amount per eligible borrower for FBs is caped at  Rs.50 lakh. Please refer our <b> Circular No. 50 / 2008 \357\277\275 09 </b> dated December 11, 2017");
                       if("RRB".equals(classificationofMLI) && gaurenteeAmount > (long)intrrbValue)
                            invalidRecord.add("Maximum 'credit to be guaranteed' amount per eligible borrower for RRBs is caped at Rs.50 lakh. Please refer our <b> Circular No. 50 / 2008 \357\277\275 09 </b> dated January 07, 2009");
                        if("FI".equals(classificationofMLI) && gaurenteeAmount > 0x989680L && (!"0036".equals(user.getBankId()) || !"0139".equals(user.getBankId())))
                            invalidRecord.add("Maximum 'credit to be guaranteed' amount per eligible borrower for FIs is caped at Rs 1 Cr. Please refer our <b> Circular No. 50 / 2008 \357\277\275 09 </b> dated January 07, 2009");
                        if("FI".equals(classificationofMLI) && (double)gaurenteeAmount > maxValue && ("0036".equals(user.getBankId())||"0139".equals(user.getBankId())))
                            invalidRecord.add("Maximum 'credit to be guaranteed' amount per eligible borrower for FIs [SIDBI] is caped at Rs 2 Cr. Please refer our <b> Circular No. 50 / 2008 \357\277\275 09 </b> dated January 07, 2009");
                        if(dcHandlooms != null && !dcHandlooms.equals("") && intcredittoguaranteeamount > inthandloomMaxValue && dcHandlooms.equals("Y"))
                            invalidRecord.add("Credit to be Guaranteed Amount should be within the eligible amount available for Guarantee upto  Rs. 200000 as per ceiling fixed by Office of DC Handlooms");
                        if(intprevCoveredAmount + intcredittoguaranteeamount > 0x30d40 && dcHandlooms.equals("Y"))
                            invalidRecord.add((new StringBuilder("Guarantee of Rs. ")).append(intprevCoveredAmount).append(" is already available for the Borrower. Total Credit to be Guaranteed Amount should be within the eligible amount available for Guarantee upto  Rs. 200000 as per ceiling fixed by Office of DC Handlooms").toString());
                        if(isLoanTypeValid && isTlSanctionAmountValid && isCreditToGauAmountValid && isTLSanctionDtValid && isTLTenureValid && isTLPrincipleMoratiumValid && isTLPlrTypeValid && isTLPlrValid && loanType.equalsIgnoreCase("TC"))
                        {
                            String message = objExcelModuleMethod.validateTC(loanType, objExcelModuleMethod.ConvertStrToInt(recordArray[66]), objExcelModuleMethod.ConvertStrToInt(recordArray[71]), recordArray[70], objExcelModuleMethod.ConvertStrToInt(recordArray[79]), objExcelModuleMethod.ConvertStrToInt(recordArray[80]), recordArray[75], objExcelModuleMethod.ConvertStrToDouble(recordArray[76]), memberId);
                            if(!message.equals(""))
                                invalidRecord.add(message);
                        }
                        if(isLoanTypeValid && isWcFbLimitSanctionedAmtValid && isWcNFbLimitSanctionedAmtValid && isWcFbCredittoGaurnteeAmtValid && isWcNFbCredittoGaurnteeAmtValid && isWcFbSanctionedDateValid && isWcNFbSanctionedDateValid && isWcFbIntrestValid && isWcNFbCommisionValid && isWcPlrTypeValid && isWcPlrValid && loanType.equalsIgnoreCase("WC"))
                        {
                            String message = objExcelModuleMethod.validateWC(loanType, objExcelModuleMethod.ConvertStrToInt(recordArray[87]), objExcelModuleMethod.ConvertStrToInt(recordArray[89]), objExcelModuleMethod.ConvertStrToInt(recordArray[88]), objExcelModuleMethod.ConvertStrToInt(recordArray[89]), recordArray[99], recordArray[101], objExcelModuleMethod.ConvertStrToDouble(recordArray[98]), objExcelModuleMethod.ConvertStrToDouble(recordArray[100]), recordArray[95], objExcelModuleMethod.ConvertStrToDouble(recordArray[96]), memberId);
                            if(!message.equals(""))
                                invalidRecord.add(message);
                        }
                        if(loanType.equalsIgnoreCase("tc") && isCreditToGauAmountValid && invalidRecord.size() == 0)
                        {
                            arrForInternalRating.put("TL_CREDIT_TO_GUARANTEE", Integer.valueOf(intcredittoguaranteeamount));
                            String validationMsg = objExcelModuleMethod.validateInternalRating(loanType, arrForInternalRating);
                            if(!validationMsg.equalsIgnoreCase(""))
                                invalidRecord.add(validationMsg);
                        }
                        if(loanType.equalsIgnoreCase("wc") && isWcFbCredittoGaurnteeAmtValid && isWcNFbCredittoGaurnteeAmtValid && invalidRecord.size() == 0)
                        {
                            arrForInternalRating.put("WC_FB_CREDIT_TO_GUARANTEE", Integer.valueOf(intFBCredittoguaranteeamount));
                            arrForInternalRating.put("WC_NFB_CREDIT_TO_GUARANTEE", Integer.valueOf(intNFBCredittoguaranteeamount));
                            String validationMsg = objExcelModuleMethod.validateInternalRating(loanType, arrForInternalRating);
                            if(!validationMsg.equalsIgnoreCase(""))
                                invalidRecord.add(validationMsg);
                        }
                        if(invalidRecord.size() > 0)
                        {
                            String TotalValidation = "";
                            for(Iterator ii = invalidRecord.iterator(); ii.hasNext();)
                                TotalValidation = TotalValidation.concat((new StringBuilder(", ")).append((String)ii.next()).toString());

                            TotalValidation.length();
                            FinalRecordResult.put(Integer.valueOf(counter), (new StringBuilder(String.valueOf(counter))).append(" record is invalid and reason is ").append(TotalValidation).toString());
                        }
                        if(invalidRecord.size() == 0)
                        {
                            appDAO.uploadAppsIntoInterface(objExcelModuleMethod.assignExcelDataToObjects(recordArray, user));
                            arrForInternalRating.clear();
                            FinalRecordResult.put(Integer.valueOf(counter), (new StringBuilder(String.valueOf(counter))).append(" record uploaded successfully.").toString());
                        }
                        invalidRecord.clear();
                    }

                    counter++;
                }
            }

        }
        catch(Exception e)
        {
            LogClass.writeExceptionOnFile(e);
        }
        return FinalRecordResultForJSP;
}
	
	 public int checkDublicateRecordAppPro(Application application,TermLoan termLoan,SSIDetails ssiDetails, String createdBy,String mliId,long longTermCreditSanctioned,long longwcFundBasedSanctioned,long longwcNonFundBasedSanctioned)
	  throws DatabaseException
	{	  
		  int result=this.appDAO.checkDublicateRecordAppDAO(application,termLoan,ssiDetails,createdBy,mliId,longTermCreditSanctioned,longwcFundBasedSanctioned,longwcNonFundBasedSanctioned);
		  return result;
	}

	public String sendBankId(String bankIds) {
		// TODO Auto-generated method stub
		System.out.println("Set bankIds in Application Processor.............."+bankIds);
		return bankIds;
	}
	private static String trimTrailingZeros(String number) {
	    if(!number.contains(".")) {
	    	System.out.println(number+"...........................Remove Sp CHAR");
	        return number;
	    }
	    return number.replaceAll("\\.?0*$", "");
	}

	public double getExposuredetails(String bankId, HttpServletRequest request) {
		   Log.log(4, "ApplicationProcessor", "exposuredetails", "Entered");
		    double expodetail = this.appDAO.getExposuredetails(bankId, request);

		    Log.log(4, "ApplicationProcessor", "exposuredetails", "Exited");

		    return expodetail;
	}

	/*public boolean getSensionDatebyCGBID(String cgpan) throws DatabaseException{ //String dCgpan, String string, String selectMember) {
		// TODO Auto-generated method stub
		boolean appSenFlag = this.appDAO.getSensionDatebyCGBID(cgpan);
		return appSenFlag;
	}
*/
	public Date getSensionDatebyCGBID(String cgpan) throws DatabaseException{ //String dCgpan, String string, String selectMember) {
		// TODO Auto-generated method stub
		Date appSenFlag = this.appDAO.getSensionDatebyCGBID(cgpan);
		return appSenFlag;
	}
	
	
	public HashMap insertXLSFileDataNew(FormFile file, User user)
	throws SQLException, IOException, FileNotFoundException, Exception{
		

		HashMap apptypes = null;
		HashMap dupApp = new HashMap();
		ArrayList validapps = new ArrayList();		
		ArrayList invalidapps = new ArrayList();
		FileInputStream fis = null;
		DataInputStream dis = null;
		InputStream is = null;
		ArrayList bankAppRefNos = new ArrayList();
		ClaimsProcessor cpProcessor = new ClaimsProcessor();
		Registration registration = new Registration();
		ApplicationDAO applicaitonDAO = new ApplicationDAO();
		// DKR Added GSTNO
		//String bankId = user.getBankId();		
		String bankid = (user.getBankId()).trim();
		String zoneid = (user.getZoneId()).trim();
		String branchid = (user.getBranchId()).trim();
		String memberId = bankid + zoneid + branchid;
        String member=zoneid + branchid;

		try {
			is = (InputStream) file.getInputStream();
			HSSFWorkbook book;
			book = new HSSFWorkbook(is);
			HSSFSheet sheet = book.getSheetAt(0);
		//	System.out.println(sheet.getSheetName()+"\t sheet row"+sheet.getLastRowNum());

			Iterator rowItr = sheet.iterator();
			int i = 0;
			int j = 0;
			int counterForRowLimit = 0;
			while (rowItr.hasNext()) {
				// if(counterForRowLimit < 50 )
				// {
				HSSFRow row = (HSSFRow) rowItr.next();
				// HSSFCell celVal [] = new HSSFCell[row.getLastCellNum()];
				HSSFCell celVal[] = new HSSFCell[3];
				// System.out.println(celVal.length);
				// Iterator cellItr = row.cellIterator();
				// if (cellItr.hasNext())
				for (int k = 0; k < 3; k++) {
					HSSFCell cellV = row.getCell(k) != null ? row.getCell(k): null;
					celVal[k] = cellV != null ? cellV : null;

				}// end for loop
					// System.out.println("cell val: "+Arrays.asList(celVal));
					// System.out.println("celVal string format"+celVal.toString());
				List CrunchifyList = Arrays.asList(celVal);
				int counter = 0;
				for (int i1 = 0; i1 < CrunchifyList.size(); i1++) {
					if (CrunchifyList.get(i1) != null) {
						String a = CrunchifyList.get(i1).toString().trim();
						if (a.equals("")) {
							System.out.println(a+""+ +i1);
							counter++;
						}
					}
				}

				if (counter < 3 && counterForRowLimit < 500) {                      //Change COUNTER 50

					HSSFCell Cgpan = celVal[0];
					HSSFCell OutstandingAmount = celVal[1];					
					HSSFCell OutstandingDate = celVal[2];
					

					if (i > 0) {
						String d_cgpan = "";
						int errorFieldCount = 0;
						double d_outstandingAmount = 0.0d;						
						Date d_outstandingDate = null;						
						ArrayList errors = new ArrayList();
				
						boolean isValidType = false;
						boolean isCgpanDuplicate = false;
						errors.add((i + 1) + ".");	
						
						
						if (celVal[0] != null) {
							isValidType = validateTextFieldMandatory(celVal[0]);// cgpan
							String cgpan=celVal[0].getStringCellValue();
							System.out.println("cgpan==="+cgpan);
							if(!member.equals("00000000")){
						try
				              {
				                this.appDAO.getAppForCgpan(bankid + zoneid + branchid, cgpan);
				              }
				              catch (DatabaseException databaseException) {
				            	  errorFieldCount++;
									errors.add("(" + errorFieldCount+ ") CGPAN DOESNOT BELONGS TO THIS MEMBERID.");
				              }
							}
							try
				              {
				               
				                this.appDAO.validateCgpandate(cgpan);
				              }
				              catch (DatabaseException databaseException) {
				            	  errorFieldCount++;
				            	  errors.add("(" + errorFieldCount+ ") SANCTION DATE IS LESS THAN 01-APR-2018.");
									//errors.add("validate===."+cgpan);
				              }
				              
				          	try
				              {
				                this.appDAO.validateCgpanFieldNotDuplicate(cgpan);
				              }
				              catch (DatabaseException databaseException) {
				            	  errorFieldCount++;
				            	  errors.add("(" + errorFieldCount+ ") OUTSTANDING AMOUNT ALREADY UPDATED.");
				              }
							//isCgpanDuplicate = validateCgpanFieldNotDuplicate(celVal[0]);// cgpan
							if (isValidType) {
								if(isCgpanDuplicate)
								{}
								//System.out.println(celVal[0].toString()+"GET CGPAN:---------------->"+d_cgpan);
								d_cgpan=celVal[0].getStringCellValue();
								if(d_cgpan.length()>6 && d_cgpan.substring(0,2).equalsIgnoreCase("CG")) {
									//System.out.println("GET CGPAN:---------------->"+d_cgpan);
							     } else {
								
									errors.add("(" + errorFieldCount+ ")Invalid CGPAN.");
								}
							} else {
								if (celVal[0].toString().trim().length() == 0) {

									errorFieldCount++;
									errors.add("(" + errorFieldCount+ ")CGPAN is Required");
								} else {
									errorFieldCount++;
									errors.add("(" + errorFieldCount
											+ ")CGPAN is Invalid");
								}
							}
						} else {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")CGPAN is Required");
						}	

						/*if (celVal[0] != null) // PmrFirstName celVal[35]
						{
							isValidType = validateTextFieldMandatory(celVal[0]);// cgpan
							if (!isValidType) {
								if (celVal[0].toString().trim().length() != 0) {
									errorFieldCount++;
									errors.add("(" + errorFieldCount+ ")  CGPAN is Invalid");
								}

							} else {
								if (celVal[0].toString().trim().length() > 20) {
									errorFieldCount++;
									errors.add("("
											+ errorFieldCount
											+ ")CGPAN is Invalid 20 character");
								}
							}
						} else {
							errorFieldCount++;
							errors.add("(" + errorFieldCount
									+ ")CGPAN is Required");
						}*/
						
						if (celVal[1] != null) 
												
						{
							//System.out.println("raju or sanju   ddddddd");
							if (celVal[1].toString().trim().length() != 0) {
				//System.out.println("raju or sanju   1");
								isValidType = validateNumericField(celVal[1]);
								//System.out.println("raju or sanju   11");
								if (isValidType) {
									//System.out.println("raju or sanju   111");
									d_outstandingAmount = celVal[1].getNumericCellValue();
				
								} else {
									errorFieldCount++;
									errors.add("("
											+ errorFieldCount
											+ ")Invalid AMT");
								}
				
							}
				
						}
						
						if (celVal[2] != null)// PmrFirstDob celVal[36]
						{
							String outstandingDate2=celVal[2].toString();
						/*	if(celVal[2].toString().startsWith("\""))
							{
								outstandingDate2=celVal[2].toString().replace("\"", "");
								
							}*/
				
							if (outstandingDate2.trim().length() > 0) {
								
								d_outstandingDate = formatDateCellNew(celVal[2]);
								System.out.println("d_outstandingDate=="+d_outstandingDate);
								
								if (d_outstandingDate == null) {
									errorFieldCount++;
									errors.add("(" + errorFieldCount + ") date is Invalid");
								}
							}

						}				
				
						Application app = new Application();
						
						if (errorFieldCount > 0) {
							invalidapps.add(errors);
							//System.out.println(errorFieldCount+" errorFieldCount====errors "+errors);
						}
						
						if (errorFieldCount == 0) {					
							
							app.setCgpan(d_cgpan);//celVal[0].getStringCellValue());
							app.setOutstandingAmount(d_outstandingAmount);//celVal[1].getNumericCellValue());
							app.setOutstandingDate(d_outstandingDate);//celVal[2].getDateCellValue());
							app.setUserId(user.getUserId());
							app.setMliID(memberId);
							validapps.add(app);							
						} // end if(errorFieldCount==0)
					} // end if(i > 0)
					i++;
					// } // end for loop
				}
				counterForRowLimit++;
				// } // end of counterForRowLimit if
			} // end while loop

			// for testing error
			if (invalidapps.size() > 0) {				
				if (validapps.size() > 0) {					
					apptypes = appDAO.uploadOutstandingIntoInterface(validapps);                 //
 
				}
              	//dupApp.put("DUPAPPS", invalidapps);
				// return dupApp;
			} else if (invalidapps.size() == 0) {
				if (validapps.size() > 0) {					
					apptypes = appDAO.uploadOutstandingIntoInterface(validapps);                                    // DKR EXL UPLOAD insertXLSFileData
					//System.out.println(apptypes.get("CLEARAPPS").toString());

				// System.out.println("dupApp.put(DUPAPPS, invalidapps)......14............."+invalidapps.size());
				}
				//dupApp.put("DUPAPPS", invalidapps);
				//return dupApp;
			}			
			/*
			 * if (validapps.size() == 0) { throw new
			 * MessageException("A Problem occured when uploading file."); }
			 */
		//	 apptypes = appDAO.uploadAppsIntoInterfaceV(validapps);
			
			//apptypes = new HashMap();
			
			if ((invalidapps.size() > 0) && validapps.size() == 0) {
				//System.out.println("INVALIDAPPS :2........16.....ap......." + invalidapps.size());
				apptypes = new HashMap();
				apptypes.put("INVALIDAPPS", invalidapps);
				
			}

			if (invalidapps.size() > 0) {
				//System.out.println("INVALIDAPPS :1.......17.....ap........." + invalidapps);
				apptypes.put("INVALIDAPPS", invalidapps);
				
			}
		

		} catch (FileNotFoundException e) {
			// e.printStackTrace();
		} catch (IOException e) {
			// e.printStackTrace();
			throw new MessageException("Unable to Read Excel File.");
		} catch (Exception e) {  	
			//System.out.println("INVALIDAPPS :1.......18....ex....." + invalidapps+"----------"+e.getMessage());
			e.printStackTrace();
			Date d = new Date();
			Log.log(4, "ApplicationProcessor", "insertXLSFileData",
					e.getMessage());

			throw new MessageException(
					e.getMessage()
							+ "Exception occured on "
							+ d.toGMTString()
							+ " Please verify your excel sheet with required record in row's .");
		} 
      	finally {
			if (is != null) {
				is.close();
			}
		}
	
						
						
		return apptypes;
	
		
		
	}
	
	
}

	/*// Updated by DKR
	 public int validateCgpanOrBid(String cgpanOrBidVal, String inputType) throws DatabaseException,NoApplicationFoundException
	 {		System.out.println("validateCgpanOrBid  APROCESSOR...................."+cgpanOrBidVal);
		 return new ApplicationDAO().validateCgpanOrBidDAO(cgpanOrBidVal,inputType);
	 }*/
