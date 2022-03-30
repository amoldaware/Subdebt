package com.cgtsi.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.cgtsi.actionform.AppropriationActionForm;
import com.cgtsi.actionform.RPActionForm;
import com.cgtsi.admin.User;
import com.cgtsi.common.DatabaseException;
import com.cgtsi.common.Log;
import com.cgtsi.receiptspayments.RpDAO;
import com.cgtsi.receiptspayments.RpProcessor;
import com.cgtsi.registration.MLIInfo;
import com.cgtsi.registration.Registration;
import com.cgtsi.util.DBConnection;

public class AppropriationAction extends BaseAction {
	
	public ActionForward getApproPriationDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
				
		AppropriationActionForm appropriationActionForm = (AppropriationActionForm) form;
		
		User user = getUserInformation(request);
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String fromDate=null;
		String toDate=null;
	    String createdBy = user.getUserId();

		String memberId = bankId.concat(zoneId).concat(branchId);
		System.out.println("memberId==1234" + memberId);
		//String memberId = "";
		 	Date dtFromDate=appropriationActionForm.getDtFromDate();
		 	if(dtFromDate.toString().length() > 0){
		 		try {
		 		    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		 		    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		 		    System.out.println(sdf2.format(sdf.parse(dtFromDate.toString())));
		 		    fromDate = sdf2.format(sdf.parse(dtFromDate.toString()));
		 		   System.out.println("fromdate:"+fromDate);
		 		} catch (Exception e) {
		 		    e.printStackTrace();
		 		}
		 	}
			Date dtToDate  =appropriationActionForm.getDtToDate();
			if(dtToDate.toString().length() > 0){
				try {
		 		    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		 		    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		 		    System.out.println(sdf2.format(sdf.parse(dtFromDate.toString())));
		 		    toDate = sdf2.format(sdf.parse(dtToDate.toString()));
		 		    System.out.println("toDate:"+toDate);
		 		} catch (Exception e) {
		 		    e.printStackTrace();
		 		}
		 	}
			
			String mliNames = appropriationActionForm.getMliNames();
			if(mliNames.length() == 0){
				mliNames = null;
		 	}
			
			System.out.println("mliNames :"+mliNames );
			
	    RpProcessor rpProcessor = new RpProcessor(); 
	    ArrayList displayData =  rpProcessor.displayDetails(fromDate,toDate,mliNames);
	    Registration registration = new Registration();
		 String mli ="";
		    ArrayList mliInfos = registration.getAllMLIs();
		    ArrayList mlis = new ArrayList();
		    int mliInfoSize = mliInfos.size();
		    int i = 0;
		    for (i = 0; i < mliInfoSize; i++)
		    {
		      MLIInfo mliInfo = new MLIInfo();
		      mliInfo = (MLIInfo)mliInfos.get(i);
		      //mli = mliInfo.getShortName() + "(" + mliInfo.getBankId() + mliInfo.getZoneId() + mliInfo.getBranchId() + ")";
		      mli = mliInfo.getBankName();
		      mlis.add(mli);
		   }
		    appropriationActionForm.setAllMliNames(mlis);
	    System.out.println("displayData="+ displayData.size());
	  //  appropriationActionForm.setPaymentRcvdDate(displayData);
	    request.setAttribute("displayData", displayData);
	    return mapping.findForward("displayDetails");
				
		
	}
	
	public ActionForward getApproPriationDetailsPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
				
		AppropriationActionForm appropriationActionForm = (AppropriationActionForm) form;
		
		 Registration registration = new Registration();
		 String mli ="";
		    ArrayList mliInfos = registration.getAllMLIs();
		    ArrayList mlis = new ArrayList();
		    int mliInfoSize = mliInfos.size();
		    int i = 0;
		    for (i = 0; i < mliInfoSize; i++)
		    {
		      MLIInfo mliInfo = new MLIInfo();
		      mliInfo = (MLIInfo)mliInfos.get(i);
		      //mli = mliInfo.getShortName() + "(" + mliInfo.getBankId() + mliInfo.getZoneId() + mliInfo.getBranchId() + ")";
		      mli = mliInfo.getBankName();
		      mlis.add(mli);
		    appropriationActionForm.setAllMliNames(mlis);
		    }
		    
		return mapping.findForward("displayDetails");
	}
	
	public ActionForward saveApproPriationDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
				
		AppropriationActionForm appropriationActionForm = (AppropriationActionForm) form;
		RpDAO rpDAO = new RpDAO();
		User user = getUserInformation(request);
		String loggedInUser = user.getUserId();
		System.out.println("username:::"+loggedInUser);
		 Registration registration = new Registration();
		 String mli ="";
		    ArrayList mliInfos = registration.getAllMLIs();
		    ArrayList mlis = new ArrayList();
		    int mliInfoSize = mliInfos.size();
		    int i = 0;
		    for (i = 0; i < mliInfoSize; i++)
		    {
		      MLIInfo mliInfo = new MLIInfo();
		      mliInfo = (MLIInfo)mliInfos.get(i);
		      //mli = mliInfo.getShortName() + "(" + mliInfo.getBankId() + mliInfo.getZoneId() + mliInfo.getBranchId() + ")";
		      mli = mliInfo.getBankName();
		      mlis.add(mli);
		    appropriationActionForm.setAllMliNames(mlis);
		    }
		    String [] date = request.getParameterValues("paymentRcvdDate");
		   String[] status=request.getParameterValues("status"); 
		   String [] rpid = request.getParameterValues("rpID");
		   String statusval = null;
		   int countFlag = 0;
		   int length = status.length-1;
		   int l = date.length-1;
		   int lo = rpid.length-1;
		   System.out.println("len:"+lo);
		  // System.out.println("length:"+l);
		   for(int j=0;j<=length;j++){
			   if(status[j].equalsIgnoreCase("Approve") || status[j].equalsIgnoreCase("Reject") ){
				   if(status[j].contentEquals("Approve")){
					   statusval="AP";
					   countFlag++;
					   request.setAttribute("message","Appropriation saved successfuly.Invoice Generated."); 
				   }
				   else if(status[j].contentEquals("Reject")){
					   statusval="RP";
					   request.setAttribute("message","Appropriation rejected successfuly."); 
				   }
				   //System.out.println("gere:::"+j+"::"+rpid[j]);
				   rpDAO.updateAppropriationDetails(statusval,date[j],rpid[j],loggedInUser);
				   rpDAO.updateCgPanDetails(statusval,date[j],rpid[j],loggedInUser);
			   }
		   }
		   if(countFlag>0){
			   rpDAO.updateInvoiceDetails(loggedInUser);
			   }
		   
		   
		   
		   //rpDAO.updateAppropriationDetails();
		    
		return mapping.findForward("success");
	}
	public ActionForward displayCgpanDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		AppropriationActionForm actionFormobj = (AppropriationActionForm) form;
		PreparedStatement allocatePaymentfinalStmt;
		ResultSet allocatePaymentFinalResult;
		Connection connection = DBConnection.getConnection();
		ArrayList rpArray = new ArrayList();
		
		
		try {
			String rpID = request.getParameter("val");
			System.out.println("value:" +rpID);
			String query = "select a.CGPAN,DAN_ID,Round(DCI_GFEE_AMT,0) as DCI_GFEE_AMT,SSI_UNIT_NAME from subdebt_application_detail a inner join dan_cgpan_info b on a.CGPAN=b.CGPAN where Payment_ID='"
					+ rpID + "'";

			allocatePaymentfinalStmt = connection.prepareStatement(query);
			allocatePaymentFinalResult = allocatePaymentfinalStmt.executeQuery();
		
			while (allocatePaymentFinalResult.next()) {

				AppropriationActionForm actionForm = new AppropriationActionForm();

				actionForm.setCgpan(allocatePaymentFinalResult.getString(1));

				actionForm.setDanid(allocatePaymentFinalResult.getString(2));
				actionForm.setGuaranteeFee(allocatePaymentFinalResult.getDouble(3));
				actionForm.setUnitNameRP(allocatePaymentFinalResult.getString(4));
				rpArray.add(actionForm);

			}
			actionFormobj.setPopupData(rpArray);
	}
		catch (Exception exception) {
			Log.logException(exception);
			throw new DatabaseException(exception.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}
		return mapping.findForward("popup");

}
}
