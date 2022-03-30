package com.cgtsi.action;

import com.cgtsi.actionform.GMActionForm;
import com.cgtsi.admin.User;
import com.cgtsi.application.ApplicationConstants;
import com.cgtsi.application.ApplicationDAO;
import com.cgtsi.application.ApplicationProcessor;
import com.cgtsi.claim.ClaimsProcessor;
import com.cgtsi.common.Constants;
import com.cgtsi.common.DatabaseException;
import com.cgtsi.common.Log;
import com.cgtsi.common.MessageException;
import com.cgtsi.common.NoDataException;
import com.cgtsi.guaranteemaintenance.ClosureDetail;
import com.cgtsi.guaranteemaintenance.GMDAO;
import com.cgtsi.guaranteemaintenance.GMProcessor;
import com.cgtsi.receiptspayments.DANSummary;
import com.cgtsi.receiptspayments.DemandAdvice;
import com.cgtsi.receiptspayments.RpDAO;
import com.cgtsi.registration.NoMemberFoundException;
import com.cgtsi.reports.ApplicationReport;
import com.cgtsi.reports.ReportManager;
import com.cgtsi.util.DBConnection;

import java.io.File;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.Types;

import java.text.SimpleDateFormat;

import java.util.ArrayList;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


// Referenced classes of package com.cgtsi.action:
//            BaseAction


public class GMActionNew extends BaseAction
{
    public GMActionNew() {
    }
    
    
    
    public ActionForward validateCgpanForTenureModification(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
	
		String message="kkkk";
PrintWriter out=response.getWriter();
out.println(message);
		try
		{
			 Connection connection = DBConnection.getConnection();
			 ResultSet rsNpaUpGradationDetails=null;
			 Statement st=connection.createStatement();
			 String query1="select count(*) from application_detail where  cgpan='CG200501736TC'  and app_loan_type='WC'";
			// if(rsNpaUpGradationDetails
		}
		catch(Exception e)
		{
			
		}
		return null;
	}
    public ActionForward tenureModificationLastPayDateCalcMethod(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {		
  	 // System.out.println("tenureModificationLastPayDateCalcMethod called first"+request.getParameter("tenure"));
  	 
        GMActionForm objGMActionForm= (GMActionForm)form;

        System.out.println("ExpiryDate"+objGMActionForm.getExpiryDate());
        System.out.println("Tenure"+objGMActionForm.getTenure());
        System.out.println("existingTenure"+request.getParameter("existingTenure"));
        
        int reviseTenure=Integer.parseInt(objGMActionForm.getTenure())-Integer.parseInt(request.getParameter("existingTenure"));
        Calendar cal = Calendar.getInstance();
        Date d= new Date(objGMActionForm.getExpiryDate());
        cal.setTime(d);
        cal.add(Calendar.MONTH, reviseTenure); //minus number would decrement the days
       Date d1= cal.getTime();
  		PrintWriter out= response.getWriter();
  		
  		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
  		SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
  	

  		System.out.println(format2.format(d1));
  	  		out.print(format2.format(d1));
  		
  	//return mapping.findForward("success1");*/
  		return null;
  }
    
    public ActionForward tenureModificationCGPanValidation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {		
  	 // System.out.println("tenureModificationLastPayDateCalcMethod called first"+request.getParameter("tenure"));
  	 String message="";
    	String cgpan = null;
    	GMActionForm gmActionForm = (GMActionForm)form;
    	User user = getUserInformation(request);
    	PrintWriter out= response.getWriter();
    	String bankId = null;
    	String zoneId = null;
    	String branchId = null;

    	String memberId = gmActionForm.getMemberIdForClosure();

    	cgpan = gmActionForm.getCgpanForClosure();

    	if ((memberId == null) || (memberId.equals(""))) {
    		System.out.println("getUnitForTenureRequest memberId inside loop"+memberId);
    		message="";
    		message="Member Id is Required";

    	}
    	
    	if ((cgpan == null) || (cgpan.equals(""))) {
    		message="";
    		message="Cgpan  is Required";
    	}
    	else
    	{
    		if(cgpan.endsWith("wc") || cgpan.endsWith("WC") || cgpan.endsWith("Wc") || cgpan.endsWith("wC"))
    		{
    			message="";
    			message="If you want to modify tenure for working capital please go  for renewal option";
    		}
    	}
    	
    	if(memberId.length()<12)
    	{
    		message="";
    		message="Member Id can not be less than 12 characters";
    	}
    	
    	if(message.equals(""))
    	{
    		GMDAO gmdao= new GMDAO();
    		message="";
    		message=gmdao.validateCgpanForTenureModificationNew(cgpan,memberId);
    	}
    	out.print(message);

  		
  		
  	  		
  		
  	//return mapping.findForward("success1");*/
  		return null;
  }
   
    
    
    public ActionForward getUnitForTenureRequest(ActionMapping mapping, 
            ActionForm form, 
            HttpServletRequest request, 
            HttpServletResponse response) throws Exception {
    	System.out.println("getUnitForTenureRequest executed ");
Log.log(Log.INFO, "GMAction", "Request for Modification of tenure", 
"Entered");
String cgpan = null;
GMActionForm gmActionForm = (GMActionForm)form;
User user = getUserInformation(request);

String bankId = null;
String zoneId = null;
String branchId = null;

String memberId = gmActionForm.getMemberIdForClosure();

cgpan = gmActionForm.getCgpanForClosure();

ApplicationReport appReport = new ApplicationReport();

System.out.println(cgpan+"getUnitForTenureRequest memberId"+memberId);
if ((memberId == null) || (memberId.equals(""))) {
	System.out.println("getUnitForTenureRequest memberId inside loop"+memberId);

throw new NoDataException("Member Id is Required");

} else if ((cgpan == null) || (cgpan.equals(""))) {

throw new NoDataException("Cgpan  is Required");
}
else if(memberId.length()<12)
{
throw new NoDataException("Member Id can not be less than 12 characters");
}
//else if ((cgpan != null) || (!cgpan.equals("")))
//{
//	if(cgpan.endsWith("WC") || cgpan.endsWith("wc") || cgpan.endsWith("Wc") || cgpan.endsWith("wC"))
//	{
//		//throw new NoDataException("You can't extend tenure of WC cases");
//	}
//
//}
else {
bankId = memberId.substring(0, 4);
zoneId = memberId.substring(4, 8);
branchId = memberId.substring(8, 12);
}
cgpan = gmActionForm.getCgpanForClosure();
gmActionForm.setApplicationReport(appReport);

Connection connection = DBConnection.getConnection(false);
CallableStatement getTenureDetail = null;
ResultSet resultSet = null;

try {

String exception = "";
String functionName = null;
functionName =  "{?=call Funcgetssidetailfortenure(?,?,?,?,?,?,?,?,?,?,?)}";
System.out.println("bankId"+bankId+"zoneId"+zoneId+"branchId"+branchId);
getTenureDetail = connection.prepareCall(functionName);
getTenureDetail.registerOutParameter(1, java.sql.Types.INTEGER);
getTenureDetail.setString(2, bankId);
getTenureDetail.setString(3, zoneId);
getTenureDetail.setString(4, branchId);
getTenureDetail.setString(5, cgpan);
getTenureDetail.registerOutParameter(6, java.sql.Types.VARCHAR);
getTenureDetail.registerOutParameter(7, java.sql.Types.VARCHAR);
getTenureDetail.registerOutParameter(8, java.sql.Types.VARCHAR);
getTenureDetail.registerOutParameter(9, java.sql.Types.VARCHAR);
getTenureDetail.registerOutParameter(10, java.sql.Types.VARCHAR);
getTenureDetail.registerOutParameter(11, java.sql.Types.DATE);
getTenureDetail.registerOutParameter(12, java.sql.Types.VARCHAR);
getTenureDetail.executeQuery();

//  Log.log(Log.DEBUG,"GMDAO","repayment detail","exception "+exception);
int error = getTenureDetail.getInt(1);
System.out.println("inside getUnitForTenureRequest error "+error);

exception = getTenureDetail.getString(12);
System.out.println("inside getUnitForTenureRequest exception "+exception);
Log.log(Log.DEBUG, "GMActionNEW", "Request for Modification of tenure", 
"errorCode " + exception);

if (error == Constants.FUNCTION_FAILURE) {
getTenureDetail.close();
getTenureDetail = null;
connection.rollback();
Log.log(Log.ERROR, "GMActionNEW", "Funcgetssidetailfortenure", 
"error in SP " + exception);

throw new DatabaseException(exception);
} else {

gmActionForm.setBankName(getTenureDetail.getString(6));
gmActionForm.setZoneName(getTenureDetail.getString(7));
gmActionForm.setBranchName(getTenureDetail.getString(8));
gmActionForm.setUnitName(getTenureDetail.getString(9));
gmActionForm.setTenure(getTenureDetail.getString(10));


SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
SimpleDateFormat format2 = new SimpleDateFormat("dd-MMM-yyyy");
Date date = format1.parse(getTenureDetail.getDate(11).toString());

System.out.println(format2.format(date)+"inside getUnitForTenureRequest setExpiryDate "+getTenureDetail.getDate(11));
gmActionForm.setExpiryDate(format2.format(date));
}


} catch (SQLException e) {
	
	System.out.println("exception in getUnitForTenureRequest "+e.getMessage());
	e.printStackTrace();

try {
connection.rollback();
} catch (SQLException ignore) {
Log.log(Log.ERROR, "GMActionNew", "reactivate User", 
ignore.getMessage());
}

Log.log(Log.ERROR, "GMActionNew", "reactivate User", 
e.getMessage());

Log.logException(e);

throw new DatabaseException("Unable to reactivate user");

} finally {

DBConnection.freeConnection(connection);
}


Log.log(Log.INFO, "GMActionNew", "Request for Modification of tenure", 
"Exited");
return mapping.findForward(Constants.SUCCESS);

}
    
    
                
    /*    public ActionForward requestModifyTenure(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {
            
            
              GMProcessor gmProcessor = new GMProcessor();

        GMActionForm gmActionForm = (GMActionForm)form;

        User user = getUserInformation(request);

        String bankId = user.getBankId();
        String zoneId = user.getZoneId();
        String branchId = user.getBranchId();
        String memberId = bankId.concat(zoneId).concat(branchId);

        gmActionForm.setBankIdForClosure(bankId);

        if (bankId.equals(Constants.CGTSI_USER_BANK_ID)) {
            memberId = "";
        }
        gmActionForm.setMemberIdForClosure(memberId);
        // gmActionForm.setClosureRemarks("");
        gmActionForm.setCgpanForClosure("");


        return mapping.findForward("requestModifyClosure");

    }

    public ActionForward getUnitForTenureRequest(ActionMapping mapping, 
                                                  ActionForm form, 
                                                  HttpServletRequest request, 
                                                  HttpServletResponse response) throws Exception {

        Log.log(Log.INFO, "GMAction", "Request for Modification of tenure", 
                "Entered");
        String cgpan = null;
        GMActionForm gmActionForm = (GMActionForm)form;
        User user = getUserInformation(request);

        String bankId = null;
        String zoneId = null;
        String branchId = null;

        String memberId = gmActionForm.getMemberIdForClosure();
        
        cgpan = gmActionForm.getCgpanForClosure();

        ApplicationReport appReport = new ApplicationReport();
        if ((memberId == null) || (memberId.equals(""))) {

            throw new NoDataException("Member Id is Required");

        } else if ((cgpan == null) || (cgpan.equals(""))) {

            throw new NoDataException("Cgpan  is Required");
        }
        else if(memberId.length()<12)
              {
                  throw new NoDataException("Member Id can not be less than 12 characters");
              }
        else {
            bankId = memberId.substring(0, 4);
            zoneId = memberId.substring(4, 8);
            branchId = memberId.substring(8, 12);
        }
        cgpan = gmActionForm.getCgpanForClosure();
        gmActionForm.setApplicationReport(appReport);

        Connection connection = DBConnection.getConnection(false);
        CallableStatement getTenureDetail = null;
        ResultSet resultSet = null;

        try {

            String exception = "";
            String functionName = null;
             functionName =  "{?=call Funcgetssidetailfortenure(?,?,?,?,?,?,?,?,?,?,?)}";

            getTenureDetail = connection.prepareCall(functionName);
            getTenureDetail.registerOutParameter(1, java.sql.Types.INTEGER);
            getTenureDetail.setString(2, bankId);
            getTenureDetail.setString(3, zoneId);
            getTenureDetail.setString(4, branchId);
            getTenureDetail.setString(5, cgpan);
            getTenureDetail.registerOutParameter(6, java.sql.Types.VARCHAR);
            getTenureDetail.registerOutParameter(7, java.sql.Types.VARCHAR);
            getTenureDetail.registerOutParameter(8, java.sql.Types.VARCHAR);
            getTenureDetail.registerOutParameter(9, java.sql.Types.VARCHAR);
            getTenureDetail.registerOutParameter(10, java.sql.Types.VARCHAR);
            getTenureDetail.registerOutParameter(11, java.sql.Types.VARCHAR);
            getTenureDetail.registerOutParameter(12, java.sql.Types.VARCHAR);
            getTenureDetail.executeQuery();

            //  Log.log(Log.DEBUG,"GMDAO","repayment detail","exception "+exception);
            int error = getTenureDetail.getInt(1);
            exception = getTenureDetail.getString(12);
            Log.log(Log.DEBUG, "GMActionNEW", "Request for Modification of tenure", 
                    "errorCode " + exception);

            if (error == Constants.FUNCTION_FAILURE) {
                getTenureDetail.close();
                getTenureDetail = null;
                connection.rollback();
                Log.log(Log.ERROR, "GMActionNEW", "Funcgetssidetailfortenure", 
                        "error in SP " + exception);

                throw new DatabaseException(exception);
            } else {

                gmActionForm.setBankName(getTenureDetail.getString(6));
                gmActionForm.setZoneName(getTenureDetail.getString(7));
                gmActionForm.setBranchName(getTenureDetail.getString(8));
                gmActionForm.setUnitName(getTenureDetail.getString(9));
                gmActionForm.setTenure(getTenureDetail.getString(10));
                gmActionForm.setExpiryDate(getTenureDetail.getString(11));
            }


        } catch (SQLException e) {

            try {
                connection.rollback();
            } catch (SQLException ignore) {
                Log.log(Log.ERROR, "GMActionNew", "reactivate User", 
                        ignore.getMessage());
            }

            Log.log(Log.ERROR, "GMActionNew", "reactivate User", 
                    e.getMessage());

            Log.logException(e);

            throw new DatabaseException("Unable to reactivate user");

        } finally {

            DBConnection.freeConnection(connection);
        }


        Log.log(Log.INFO, "GMActionNew", "Request for Modification of tenure", 
                "Exited");
        return mapping.findForward(Constants.SUCCESS);

    }
   
    public ActionForward afterTenureApproval(
                                    ActionMapping mapping,
                                    ActionForm form,
                                    HttpServletRequest request,
                                    HttpServletResponse response)
                                    throws Exception {
                                    GMActionForm gmPeriodicInfoForm =(GMActionForm)form;
            GMDAO gmDAO = new GMDAO();  
            RpDAO rpDAO=new RpDAO();
             //   ApplicationProcessor appProcessor=new ApplicationProcessor();
           ApplicationDAO applicationDAO = new ApplicationDAO();
           Connection connection = DBConnection.getConnection(false);
           User user=getUserInformation(request);
           String userId=user.getUserId();
          
           Map modifCgpan=gmPeriodicInfoForm.getClosureCgpan();
           Set modifCgpanSet=modifCgpan.keySet();
           Iterator modifCgpanIterator=modifCgpanSet.iterator();
            while (modifCgpanIterator.hasNext())
                            {
                  String key=(String)modifCgpanIterator.next();
                  String decision=(String)modifCgpan.get(key);
                 if(!(decision.equals("")))
                   {
                 
                   if(decision.equals(ApplicationConstants.APPLICATION_APPROVED_STATUS))
                   {
                      DANSummary danSummaryNew = new DANSummary();
                      danSummaryNew =getRequestedForTenureApplication(key);
                     updateApplicationStatusForTenureCases(key,danSummaryNew,user);
                   request.setAttribute("message", "<b>The Request for Modification of Tenure approved successfully.<b><br>");
                   }
               
                   }
                }   
            
               modifCgpan.clear(); 
               
               
       
          return mapping.findForward("success");
                            }         
               
            
    public DANSummary getRequestedForTenureApplication(String cgpan) throws DatabaseException
     {
                  
                  Connection connection = null ;
                  ResultSet rsDanDetails = null;
                  ResultSet rsPaidDetails = null;
                  CallableStatement getTenureDetailsStmt;
      DANSummary Summary1 = null;
                  int getSchemesStatus=0;
                  String getDanDetailsErr = "" ;
            connection=DBConnection.getConnection(false);
                  try {
                          
        getTenureDetailsStmt=connection.prepareCall("{?= call Packgettenuremoddetail.funcGetCgpanTenureDetails(?,?,?,?,?,?,?,?,?,?,?,?,?)}");

                       getTenureDetailsStmt.registerOutParameter(1, java.sql.Types.INTEGER);
                        getTenureDetailsStmt.setString(2,cgpan);
                          getTenureDetailsStmt.registerOutParameter(3, java.sql.Types.VARCHAR);
                          getTenureDetailsStmt.registerOutParameter(4, java.sql.Types.VARCHAR);
                      getTenureDetailsStmt.registerOutParameter(5, java.sql.Types.VARCHAR);
                      getTenureDetailsStmt.registerOutParameter(6, java.sql.Types.VARCHAR);
                      getTenureDetailsStmt.registerOutParameter(7, java.sql.Types.VARCHAR);
                      getTenureDetailsStmt.registerOutParameter(8, java.sql.Types.VARCHAR);
                      getTenureDetailsStmt.registerOutParameter(9, java.sql.Types.VARCHAR);
                      getTenureDetailsStmt.registerOutParameter(10, java.sql.Types.VARCHAR);
                      getTenureDetailsStmt.registerOutParameter(11, java.sql.Types.VARCHAR);
                      getTenureDetailsStmt.registerOutParameter(12, java.sql.Types.VARCHAR);
                      getTenureDetailsStmt.registerOutParameter(13, java.sql.Types.VARCHAR);
                      getTenureDetailsStmt.registerOutParameter(14, java.sql.Types.VARCHAR);
                          getTenureDetailsStmt.execute();

                          getSchemesStatus=getTenureDetailsStmt.getInt(1);
                                         if (getSchemesStatus==0) {
                              Summary1 = new DANSummary() ;
                              Summary1.setMemberId(getTenureDetailsStmt.getString(3));
                              Summary1.setCgpan(getTenureDetailsStmt.getString(4));
                              Summary1.setAppStatus(getTenureDetailsStmt.getString(5));
                              Summary1.setOriginalTenure(getTenureDetailsStmt.getInt(6));
                              Summary1.setAppExpiryDate(getTenureDetailsStmt.getString(7));
                              Summary1.setRevisedTenure(getTenureDetailsStmt.getInt(8));
                              Summary1.setTermAmountSanctionedtDate(getTenureDetailsStmt.getString(9));                        
                              Summary1.setLastDateOfRePayment(getTenureDetailsStmt.getString(10));
                              Summary1.setReason(getTenureDetailsStmt.getString(11));
                              Summary1.setRequestCreatedUserId(getTenureDetailsStmt.getString(12));                                  
                              Summary1.setRequestCreatedDate(getTenureDetailsStmt.getString(13));
                                
                                  getTenureDetailsStmt.close();
                                  getTenureDetailsStmt=null;

                          }
                          else {
                                  getDanDetailsErr=getTenureDetailsStmt.getString(12);
   
                                  getTenureDetailsStmt.close();
                                  getTenureDetailsStmt=null;

                                  connection.rollback();

                                  throw new DatabaseException(getDanDetailsErr);
                     }

                     connection.commit();

                  }
                  catch (Exception exception)
                  {
                       try
                          {
                                  connection.rollback();
                          }
                          catch (SQLException ignore){}

                     throw new DatabaseException(exception.getMessage());

                  }
                  finally
                  {
                          DBConnection.freeConnection(connection);
                  }
          return Summary1;
          }
          
    public void updateApplicationStatusForTenureCases(String cgpan,DANSummary danSummary,User user)throws DatabaseException
    {
       
            Log.log(Log.INFO,"GMActionNew","updateApplicationStatusForTenureCases","Entered");
                    Connection connection = DBConnection.getConnection(false);
                    CallableStatement updateCgpanTenureDetails = null;
                    int updateStatus=0;
            
             SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
             String formatedDate = "" ;
             String formatedDate1 = "";
             java.util.Date utilDate = new java.util.Date();
             java.sql.Date sqlDate ; 
             java.sql.Date sqlDate1 ; 
             String userId = user.getUserId(); 

                    try {
                                    updateCgpanTenureDetails = connection.prepareCall(
                                                            "{?=call Packgettenuremoddetail.funcupdtenuredetails(?,?,to_date(?,'dd/mm/yyyy'),?,to_date(?,'dd/mm/yyyy'),?,to_date(?,'dd/mm/yyyy'),?,?,?)}");
                               updateCgpanTenureDetails.registerOutParameter(1,java.sql.Types.INTEGER);
                               updateCgpanTenureDetails.setString(2,danSummary.getCgpan());
                                updateCgpanTenureDetails.setInt(3,danSummary.getOriginalTenure());
                                updateCgpanTenureDetails.setString(4,danSummary.getAppExpiryDate());
                                updateCgpanTenureDetails.setInt(5,danSummary.getRevisedTenure());
                                updateCgpanTenureDetails.setString(6,danSummary.getLastDateOfRePayment());
                                updateCgpanTenureDetails.setString(7,danSummary.getRequestCreatedUserId());
                                updateCgpanTenureDetails.setString(8,danSummary.getRequestCreatedDate());
                                updateCgpanTenureDetails.setString(9,danSummary.getReason());
                                updateCgpanTenureDetails.setString(10,user.getUserId());
                                updateCgpanTenureDetails.registerOutParameter(11, java.sql.Types.VARCHAR);
                                updateCgpanTenureDetails.executeQuery();
                                updateStatus=updateCgpanTenureDetails.getInt(1);
                            String error = updateCgpanTenureDetails.getString(11);

                                    if (updateStatus==Constants.FUNCTION_SUCCESS) {
                                updateCgpanTenureDetails.close();
                                    updateCgpanTenureDetails = null;
                                      connection.commit();
                                    Log.log(Log.DEBUG,"GMActionNew","updateApplicationStatusForTenureCases","success-SP");
                                    }
                                    else if (updateStatus==Constants.FUNCTION_FAILURE) {
                                            updateCgpanTenureDetails.close();
                                            updateCgpanTenureDetails = null;
                                            Log.log(Log.ERROR,"GMActionNew","updateApplicationStatusForTenureCases","Error : "+error);
                                            connection.rollback();
                                            throw new DatabaseException(error);
                                    }
                                    
                            }catch (Exception exception) {
                                    Log.logException(exception);
                                    try
                                    {
                                            connection.rollback();
                                    }
                                    catch (SQLException ignore){}

                                    throw new DatabaseException(exception.getMessage());
                             }                       
                             finally{
                                    DBConnection.freeConnection(connection);
                             }

                    Log.log(Log.INFO,"GMActionNew","updateApplicationDetailForTenureCases","Exited");
      
    }
    public ActionForward showTenureApproval(
                    ActionMapping mapping,
                    ActionForm form,
                    HttpServletRequest request,
                    HttpServletResponse response)
                    throws Exception{

                    Log.log(Log.INFO,"GMAction","showTenureApproval","Entered");
    User user = getUserInformation(request) ;
            String bankId = user.getBankId() ;
            String zoneId = user.getZoneId() ;
            String branchId = user.getBranchId() ;
            String memberId = "" ;
            GMActionForm gmActionForm = (GMActionForm)form;

                    String forward="";
    
    
                    GMProcessor gmProcessor = new GMProcessor();
    
                    ArrayList tenureApprovalDetail = displayRequestedForTenureApproval();
    
    ArrayList tenureApprovalDetailMod1 = (ArrayList)tenureApprovalDetail.get(0);
  
      
                    if(tenureApprovalDetail.size()==0 && tenureApprovalDetailMod1.size()==0 )
                    {
                            Log.log(Log.INFO,"GMAction","showTenureApproval","emty Tenure list");
                            request.setAttribute("message","No Tenure Details available for Approval");
                            forward="success";
                    }
    else{
   
    gmActionForm.setClosureDetailsReq(tenureApprovalDetailMod1);
    forward="tenureList";
    }
                    return mapping.findForward(forward);

            }
    public ArrayList displayRequestedForTenureApproval() throws DatabaseException
    {
        
                 DemandAdvice demandAdvice = null ;
                 Connection connection = null ;
      
                 CallableStatement getDanDetailsStmt;

                 int getSchemesStatus=0;
                 String getDanDetailsErr = "" ;

                 ArrayList danDetails=new ArrayList();
                ArrayList tenDetails2=new ArrayList();

                 connection=DBConnection.getConnection(false);
          
                 try {
                         danDetails=new ArrayList();

       getDanDetailsStmt=connection.prepareCall("{?= call Packgettenuremoddetail.funcGetTenureModDet(?,?)}");
                 
                        getDanDetailsStmt.registerOutParameter(1, java.sql.Types.INTEGER);
                        getDanDetailsStmt.registerOutParameter(2, Constants.CURSOR);
                         getDanDetailsStmt.registerOutParameter(3, java.sql.Types.VARCHAR);
                        getDanDetailsStmt.execute();
                         getSchemesStatus=getDanDetailsStmt.getInt(1);
                   
       if(getSchemesStatus==Constants.FUNCTION_FAILURE){

                                 String error = getDanDetailsStmt.getString(4);

                                 getDanDetailsStmt.close();
                                 getDanDetailsStmt=null;
                                  connection.rollback();

                                 throw new DatabaseException(error);
                         }
                         else {
       
                     
         ResultSet  rsPaidDetails=(ResultSet) getDanDetailsStmt.getObject(2);
              
                                 while (rsPaidDetails.next())
                                 {
                                   DANSummary    Summary1 = new DANSummary() ;
                              
           Summary1.setMemberId(rsPaidDetails.getString(1));
           Summary1.setCgpan(rsPaidDetails.getString(2));
           Summary1.setAppStatus(rsPaidDetails.getString(3));
           Summary1.setOriginalTenure(rsPaidDetails.getInt(4));
           Summary1.setAppExpiryDate(rsPaidDetails.getString(5));
           Summary1.setRevisedTenure(rsPaidDetails.getInt(6));
           Summary1.setTermAmountSanctionedtDate(rsPaidDetails.getString(7));                        
           Summary1.setLastDateOfRePayment(rsPaidDetails.getString(8));
           Summary1.setReason(rsPaidDetails.getString(9));
           tenDetails2.add(Summary1);
                            }
             rsPaidDetails.close();
             rsPaidDetails=null;                   
             getDanDetailsStmt.close();
             getDanDetailsStmt=null;
        
                         }
                 
        danDetails.add(0,tenDetails2);
                    connection.commit();

                 }
                 catch (Exception exception)
                 {
                    try
                         {
                                 connection.rollback();
                         }
                         catch (SQLException ignore){}

                    throw new DatabaseException(exception.getMessage());

                 }
                 finally
                 {
                         DBConnection.freeConnection(connection);
                 }
         return danDetails;
         } 
         
    public ActionForward upgradationfromnpatostandard (
                ActionMapping mapping,
                ActionForm form,
                HttpServletRequest request,
                HttpServletResponse response)
                throws Exception{

                Log.log(Log.INFO,"GMAction","upgradationfromnpatostandard","Entered");
    //  System.out.println("getIdForClosureRequestInput  Entered:");
                GMProcessor gmProcessor = new GMProcessor ();

                GMActionForm gmActionForm =(GMActionForm)form;

                User user = getUserInformation(request);

                String bankId = user.getBankId();
                String zoneId = user.getZoneId();
                String branchId = user.getBranchId();
                String memberId = bankId.concat(zoneId).concat(branchId );

                gmActionForm.setBankIdForClosure(bankId);
    
                if(bankId.equals(Constants.CGTSI_USER_BANK_ID))
                {
                        memberId = "";
                }
                gmActionForm.setMemberIdForClosure(memberId);
                gmActionForm.setClosureRemarks("");
                gmActionForm.setCgpanForClosure("");

                Log.log(Log.INFO,"GMAction","upgradationfromnpatostandard","Exited");
                return mapping.findForward(Constants.SUCCESS);

                }
                
    public ActionForward upgradationNpa (
                ActionMapping mapping,
                ActionForm form,
                HttpServletRequest request,
                HttpServletResponse response)
                throws Exception{

                Log.log(Log.INFO,"GMAction","upgradationNpa","Entered");

                GMProcessor gmProcessor = new GMProcessor ();
                GMActionForm gmActionForm =(GMActionForm)form;
    ApplicationReport appReport = new ApplicationReport();
    ReportManager reportManager = new ReportManager();
                    String bankId="";
                    String zoneId="";
                    String branchId="";
    String memberId = gmActionForm.getMemberIdForClosure();
    String cgpan = gmActionForm.getCgpanForClosure().toUpperCase();
    // System.out.println("Member Id:"+memberId+" Cgpan:"+cgpan);
     Log.log(Log.INFO, "GMDAO","submitUpgradationDetails", "Entered") ;
             if ((memberId == null) || (memberId.equals(""))) {

                 throw new NoDataException("Member Id is Required");

             } else if ((cgpan == null) || (cgpan.equals(""))) {

                 throw new NoDataException("Cgpan  is Required");
             }
             else if(memberId.length()<12)
                   {
                       throw new NoDataException("Member Id can not be less than 12 characters");
                   }
             else {
                  bankId = memberId.substring(0,4);
                  zoneId = memberId.substring(4,8);
                  branchId = memberId.substring(8,12);
             }
          
                gmActionForm.setMemberIdForClosure(memberId);
                gmActionForm.setCgpanForClosure(cgpan);
                appReport = reportManager.getApplicationReportForCgpan(cgpan);
                gmActionForm.setClosureRemarks("");
                gmActionForm.setApplicationReport(appReport);       

                Log.log(Log.INFO,"GMAction","upgradationNpa","Exited");
                return mapping.findForward(Constants.SUCCESS);

    }
                
    public ActionForward submitUpgradationDetails(
                ActionMapping mapping,
                ActionForm form,
                HttpServletRequest request,
                HttpServletResponse response)
                throws Exception{

                Log.log(Log.INFO,"GMAction","submitClosureDetails","Entered");

                GMProcessor gmProcessor = new GMProcessor();
                GMDAO gmDAO = new GMDAO();
                ApplicationProcessor appProcessor = new ApplicationProcessor();
                ClosureDetail closureDtl = new ClosureDetail();
                HashMap closureDetails = null;
    
                GMActionForm gmActionForm=(GMActionForm)form;
                HttpSession session = request.getSession(false);
    
                User user=getUserInformation(request);
    
                String userId = user.getUserId();
                String memberId = gmActionForm.getMemberIdForClosure() ;
                String cgpan = gmActionForm.getCgpanForClosure().toUpperCase();
                String npaRemarks = gmActionForm.getclosureRemarks();
                    String bankId="";
                    String zoneId="";
                    String branchId="";
                    if ((npaRemarks == null) || (npaRemarks.equals(""))) {

                        throw new NoDataException("Remarks is Required");

                    }
                    
                 Log.log(Log.INFO, "GMDAO","submitUpgradationDetails", "Entered") ;
                      bankId = memberId.substring(0,4);
                         zoneId = memberId.substring(4,8);
                         branchId = memberId.substring(8,12);
          Connection connection=DBConnection.getConnection();
            try
            {
                    CallableStatement callable=connection.prepareCall("{?=call " +
                            "Funcupgnpastand(?,?,?,?,?)}");
                     callable.registerOutParameter(1,Types.INTEGER);
                    callable.setString(2,memberId);
                   callable.setString(3,cgpan);
                   callable.setString(4,npaRemarks);
                    callable.setString(5,userId);
                    callable.registerOutParameter(6,Types.VARCHAR);
                    callable.execute();
                    int errorCode=callable.getInt(1);
                   String error=callable.getString(6);
            //   System.out.println("Error:"+error);

                    Log.log(Log.DEBUG, "GMDAO", "submitUpgradationDetails", "error code and error"+errorCode+","+error) ;

                    if(errorCode==Constants.FUNCTION_FAILURE)
                    {
                            Log.log(Log.ERROR, "GMDAO", "submitUpgradationDetails", error) ;

                            callable.close();
                            callable=null;
                            throw new DatabaseException(error);
                    }

                    callable.close();
                    callable=null;
            }
            catch(SQLException e)
            {
                    Log.log(Log.ERROR, "GMDAO", "submitUpgradationDetails", e.getMessage()) ;

                    Log.logException(e);

                    throw new DatabaseException("Unable to delete the NPA Details.");

            }
            finally
            {
                    DBConnection.freeConnection(connection);
            }
                    Log.log(Log.INFO,"GMAction","upgradationfromnpatostandard","Exited");
                    return mapping.findForward(Constants.SUCCESS);
                
                }
                 
          */   
    /*added by vinod@path 05-nov-15*/
    
    public ActionForward updateNpaDtInput(ActionMapping mapping,ActionForm form, 
			HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		//GMProcessor gmProcessor = new GMProcessor();
		Log.log(Log.INFO, "GMAction", "updateNpaDtInput", "Entered");
		GMActionForm gmActionForm = new GMActionForm();
		User user = getUserInformation(request);
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String memberId = bankId.concat(zoneId).concat(branchId);		
								
		Log.log(Log.INFO, "GMAction", "updateNpaDtInput", "Exited");
		return mapping.findForward("NpaDtInput");
	}
	
	public ActionForward updateNpaDtDetail(ActionMapping mapping,ActionForm form, 
			HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		Log.log(Log.INFO, "GMAction", "updateNpaDtDetail", "Entered");
		String cgpan = null;
		String memberId = null;
		GMActionForm gmActionForm = (GMActionForm)form;
		User user = getUserInformation(request);
		String userId = user.getUserId();
		String bankId = null;
		String zoneId = null;
		String branchId = null;
		
		memberId = gmActionForm.getMemberId();
		cgpan = gmActionForm.getCgpan();
		System.out.println("memberId : "+memberId +"\tcgpan : "+cgpan);
		if((memberId == null) || memberId.equals(""))
		{
			throw new NoDataException("Member Id is Required");
		}
		else if((cgpan == null) || cgpan.equals(""))
		{
			throw new NoDataException("CGPAN is Required");
		}
		else if(memberId.length() <12)
		{
			throw new NoDataException("Member Id can not be less than 12 characters");
		}
		else
		{
			bankId = memberId.substring(0, 4);
			zoneId = memberId.substring(4, 8);
			branchId = memberId.substring(8, 12);
		}
		cgpan = gmActionForm.getCgpan();
		Connection connection = DBConnection.getConnection(false);
		PreparedStatement prepareStmt = null;
		ResultSet resultSet = null;
		
		try
		{
			String query =" select sd.ssi_unit_name, app.cgpan, to_char(ndt.npa_effective_dt,'dd-MM-yyyy'), ndt.Npa_Reasons_Turning_Npa" +
					" from ssi_detail sd, application_detail app, npa_detail_temp ndt" +
					" where app.cgpan=? and app.ssi_reference_number = sd.ssi_reference_number and" +
					" sd.bid = ndt.bid";
			//System.out.println("query : "+query);
			prepareStmt = connection.prepareStatement(query);
			prepareStmt.setString(1, cgpan);
			resultSet = prepareStmt.executeQuery();
			while(resultSet.next())
			{
				//SimpleDateFormat sim = new SimpleDateFormat("dd/MM/yyyy");
				gmActionForm.setUnitName(resultSet.getString(1));
				gmActionForm.setCgpan(resultSet.getString(2));
				gmActionForm.setNpaUpdateDt(resultSet.getString(3));
				gmActionForm.setNpaResons(resultSet.getString(4));
			}
			prepareStmt.close();
			prepareStmt = null;
			resultSet.close();
			resultSet = null;
		}
		catch(Exception e)
		{
			Log.log(Log.ERROR,"GMAction","updateNpaDtDetail",e.getMessage());
			e.printStackTrace();
			throw new MessageException("Unable to Find Given CGPAN Number Data");
		}
		finally
		{
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO, "GMAction", "updateNpaDtDetail", "Exited");
		return mapping.findForward("NpaDtDetail");
	}
	
	public ActionForward updateNpaDtDetailSave(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		Log.log(Log.INFO, "GMActionNew","updateNpaDtDetailSave","Entered");
		GMActionForm gmActionForm = (GMActionForm)form;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		HttpSession session = request.getSession(false);
		User user = getUserInformation(request);
		String userId = user.getUserId();
		String bnk_id = user.getBankId();
		String zne_id = user.getZoneId();
		String brn_id = user.getBranchId();
		System.out.println("memberId : "+bnk_id+zne_id+brn_id);
		
		String unitName = gmActionForm.getUnitName();
		System.out.println("unitName : "+unitName);
		
		String cgpan = gmActionForm.getCgpan();
		System.out.println("cgpan : "+cgpan);
		
		String npaupdt = gmActionForm.getNpaUpdateDt();
		System.out.println("nudt : "+npaupdt);
		Date npDt = dateFormat.parse(npaupdt);		
		
		String npaResons = gmActionForm.getNpaResons();
		System.out.println("npaResons : "+npaResons);
				
		//file upload 
		
		File file = gmActionForm.getNpaFile();
		String fileName = file.getName();
		//InputStream is = null;		
		if(file != null)
		{
			System.out.println("File Name : "+file.getName());
			System.out.println("File Size : "+file.length());
		}
		
		
		
		Date correctiionDt = gmActionForm.getCorrectionDt();		
		System.out.println("correctiionDt : "+correctiionDt);
		
		String remarks = gmActionForm.getModificationOfRemarks();
		System.out.println("remarks : "+remarks);
		
		
		Connection connection = DBConnection.getConnection(false);
		PreparedStatement prepareStmt = null;
		ResultSet resultSet = null;
		
		try
		{
			String query = " INSERT INTO UPDATE_NPA_DATE_DETAIL (MEM_BNK_ID,MEM_ZNE_ID,MEM_BRN_ID,UNIT_NAME, " +
					" CGPAN,NPA_UPDATED_DT,NPA_REASONS_TURNING,NPA_UPDATED_FILE_NAME,NPA_UPDATED_FILE, " +
					" CORRECTION_NPA_DT,LASTCLMLODGEDT,REMARKS,CREATED_USER_ID,CREATED_DT)values" +
					" (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			System.out.println("insert query : "+query);
			prepareStmt = connection.prepareStatement(query);
			prepareStmt.setString(1, bnk_id);//MEM_BNK_ID
			prepareStmt.setString(2, zne_id);//MEM_ZNE_ID
			prepareStmt.setString(3, brn_id);//MEM_BRN_ID
			prepareStmt.setString(4, unitName);//UNIT_NAME
			prepareStmt.setString(5, cgpan);//CGPAN
			prepareStmt.setDate(6, new java.sql.Date(npDt.getTime()));//NPA_UPDATED_DT
			prepareStmt.setString(7, npaResons);//NPA_REASONS_TURNING
		    prepareStmt.setString(8, fileName);//NPA_UPDATED_FILE_NAME
			//prepareStmt.setBinaryStream(9, npaUploadedFile.getInputStream(), npaUploadedFile.getFileSize());//NPA_UPDATED_FILE
		    Blob blob = connection.createBlob();
		    blob.setBytes(0, new byte[100]);
			prepareStmt.setBlob(9, blob);
			prepareStmt.setDate(10, new java.sql.Date(correctiionDt.getTime()));//CORRECTION_NPA_DT
			prepareStmt.setDate(11, new java.sql.Date(correctiionDt.getTime()));//LASTCLMLODGEDT			
			prepareStmt.setString(12, remarks);//REMARKS
			prepareStmt.setString(13, userId);//CREATED_USER_ID
			Date currentDt = new Date();
			prepareStmt.setDate(14, new java.sql.Date(currentDt.getTime()));//CREATED_DT
			
			int updateCount = prepareStmt.executeUpdate();
			if(updateCount > 0)
			{
				connection.commit();
				request.setAttribute("message", "NPA Effective Date Updated for "+cgpan);
			}
			else
			{
				connection.rollback();
				request.setAttribute("message", "NPA Effective Date Not Updated");
			}
			
		}
		catch(Exception e)
		{			
			Log.log(Log.ERROR,"GMActionNew","updateNpaDtDetailSave",e.getMessage());
			e.printStackTrace();
			throw new MessageException("A Problem Occured While Inserting NPA Effective Date Details.");
		}
		finally
		{
			DBConnection.freeConnection(connection);
		}
		request.setAttribute("message", "napDt : "+npDt+"\t cgpan : "+cgpan);
		Log.log(Log.INFO, "GMActionNew","updateNpaDtDetailSave","Exited");
		return mapping.findForward(Constants.SUCCESS);
	}
                
}
