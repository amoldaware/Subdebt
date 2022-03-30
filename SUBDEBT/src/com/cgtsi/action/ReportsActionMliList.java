package com.cgtsi.action;

import com.cgtsi.admin.User;
import com.cgtsi.common.DatabaseException;
import com.cgtsi.common.Log;
import com.cgtsi.common.MessageException;
import com.cgtsi.common.NoDataException;
import com.cgtsi.reports.CumlativeDailyReportBean;
import com.cgtsi.reports.ReportManager;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

public class ReportsActionMliList extends BaseAction
{
  private ReportManager reportManager = null;

  public ReportsActionMliList()
  {
    this.reportManager = new ReportManager();
  }

  public ActionForward listOfMLIPath1(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "ReportsAction", "listOfMLIPath", "Entered");
    ArrayList mli = new ArrayList();
    DynaActionForm dynaForm = (DynaActionForm)form;

    mli = this.reportManager.getMliList();
    dynaForm.set("mli", mli);
    if ((mli == null) || (mli.size() == 0))
    {
      throw new NoDataException("No Data is available for the values entered, Please Enter Any Other Value ");
    }

    mli = null;
    Log.log(4, "ReportsAction", "listOfMLIPath", "Exited");

    return mapping.findForward("success1");
  }
  
  public ActionForward hoUserCumlativeReportSubmit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
		    throws DatabaseException,Exception
		  {
		    Log.log(4, "ReportsAction", "hoUserCumlativeReportSubmit", "Entered");		    
		    DynaActionForm dynaForm = (DynaActionForm)form;	
		    HttpSession session = request.getSession(false);		    
		    String bankId ="";
		    String zoneId = "";
			String branchId = "";
			String mliId = "";
			User user = getUserInformation(request);
			String userId = user.getUserId();
			System.out.println("userId>>>>>hoUserCumlativeReportSubmit()>>>>>>"+userId);
			bankId = user.getBankId();
			zoneId = user.getZoneId();
			branchId = user.getBranchId();
			String memberId=bankId+zoneId+branchId;
			System.out.println("Member id===65==="+memberId);
			session.setAttribute("memberId", memberId);
			
			
			
			
			
			CumlativeDailyReportBean cdReport = new CumlativeDailyReportBean();
			//cdReport.setHoReportBankName(((String) dynaForm.get("hoReportBankName")).toString());
			
			cdReport.setHoReportBankName(((String) dynaForm.get("hoReportBankName")).toString());
			cdReport.setHoReportEligBorrower(((Double) dynaForm.get("hoReportEligBorrower")).doubleValue());
			cdReport.setHoReportTotalOs(((Double) dynaForm.get("hoReportTotalOs")).doubleValue());
			cdReport.setHoReport20EligOs(((Double) dynaForm.get("hoReport20EligOs")).doubleValue());
			cdReport.setHoReportNoBorrowerOffer(((Double) dynaForm.get("hoReportNoBorrowerOffer")).doubleValue());
			cdReport.setHoReportNoBorrowerOpt(((Double) dynaForm.get("hoReportNoBorrowerOpt")).doubleValue());
			cdReport.setHoReportSanctionNo(((Double) dynaForm.get("hoReportSanctionNo")).doubleValue());
			cdReport.setHoReportSanctionAmt(((Double) dynaForm.get("hoReportSanctionAmt")).doubleValue());
			cdReport.setHoReportDisburseNo(((Double) dynaForm.get("hoReportDisburseNo")).doubleValue());
			cdReport.setHoReportDisburseAmt(((Double) dynaForm.get("hoReportDisburseAmt")).doubleValue());
			 int returnStatus = this.reportManager.submitCumlativeReport(cdReport,user);		   
		     if(returnStatus>0){
		    	  request.setAttribute("message", "Record submited successfully.");
		     }else{ 
		    	  request.setAttribute("message", "Unable to process the record.");
		      }
		    Log.log(4, "ReportsAction", "hoUserCumlativeReportSubmit", "Exited");
		    return mapping.findForward("success");
		  }
		  
}