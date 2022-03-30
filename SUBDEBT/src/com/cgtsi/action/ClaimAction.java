package com.cgtsi.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.Vector;

import javax.mail.Session;
import javax.mail.Transport;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.DynaValidatorActionForm;

import com.cgtsi.actionform.ClaimActionForm;
import com.cgtsi.actionform.ClaimRecoveryForm;
import com.cgtsi.actionform.RecoveryActionForm;
import com.cgtsi.admin.Administrator;
import com.cgtsi.admin.InvalidDataException;
import com.cgtsi.admin.MenuOptions;
import com.cgtsi.admin.ParameterMaster;
import com.cgtsi.admin.User;
import com.cgtsi.application.BorrowerDetails;
import com.cgtsi.application.SSIDetails;
import com.cgtsi.application.TermLoan;
import com.cgtsi.claim.ApplicationAlreadyFiledException;
import com.cgtsi.claim.BorrowerInfo;
import com.cgtsi.claim.CPDAO;
import com.cgtsi.claim.ClaimApplication;
import com.cgtsi.claim.ClaimConstants;
import com.cgtsi.claim.ClaimDetail;
import com.cgtsi.claim.ClaimFiles;
import com.cgtsi.claim.ClaimSummaryDtls;
import com.cgtsi.claim.ClaimsProcessor;
import com.cgtsi.claim.DtlsAsOnDateOfNPA;
import com.cgtsi.claim.DtlsAsOnDateOfSanction;
import com.cgtsi.claim.DtlsAsOnLogdementOfClaim;
import com.cgtsi.claim.DtlsAsOnLogdementOfSecondClaim;
import com.cgtsi.claim.ExportFailedException;
import com.cgtsi.claim.LegalProceedingsDetail;
import com.cgtsi.claim.LockInPeriodNotCompletedException;
import com.cgtsi.claim.MemberInfo;
import com.cgtsi.claim.OTSApprovalDetail;
import com.cgtsi.claim.OTSRequestDetail;
import com.cgtsi.claim.RecoveryDetails;
import com.cgtsi.claim.SecurityAndPersonalGuaranteeDtls;
import com.cgtsi.claim.SettlementDetail;
import com.cgtsi.claim.TermLoanCapitalLoanDetail;
import com.cgtsi.claim.WorkingCapitalDetail;
import com.cgtsi.common.Constants;
import com.cgtsi.common.DatabaseException;
import com.cgtsi.common.Log;
import com.cgtsi.common.MessageException;
import com.cgtsi.common.NoDataException;
import com.cgtsi.guaranteemaintenance.GMProcessor;
import com.cgtsi.guaranteemaintenance.Recovery;
import com.cgtsi.investmentfund.BankAccountDetail;
import com.cgtsi.investmentfund.ChequeDetails;
import com.cgtsi.investmentfund.IFProcessor;
import com.cgtsi.investmentfund.InvestmentFundConstants;
import com.cgtsi.receiptspayments.PaymentDetails;
import com.cgtsi.receiptspayments.RpConstants;
import com.cgtsi.receiptspayments.RpDAO;
import com.cgtsi.receiptspayments.Voucher;
import com.cgtsi.receiptspayments.VoucherDetail;
import com.cgtsi.registration.CollectingBank;
import com.cgtsi.registration.MLIInfo;
import com.cgtsi.registration.NoMemberFoundException;
import com.cgtsi.registration.Registration;
import com.cgtsi.reports.GeneralReport;
import com.cgtsi.reports.ReportManager;
import com.cgtsi.util.DBConnection;
import com.cgtsi.util.DateHelper;
import com.cgtsi.util.PropertyLoader;

public class ClaimAction extends BaseAction
{

	public ActionForward updateRecoveryInfoMenuAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaValidatorActionForm formobj = (DynaValidatorActionForm) form;
	    Registration registration = new Registration();
		CollectingBank collectingBank = new CollectingBank();
		User user = getUserInformation(request);
		String bankid = (user.getBankId()).trim();
		String zoneid = (user.getZoneId()).trim();
		String branchid = (user.getBranchId()).trim();
		String memberId = bankid + zoneid + branchid;
		 collectingBank = 
            registration.getCollectingBank("(".concat(memberId).concat(")"));
		//System.out.println("updateRecoveryGetPaymentInfo "+collectingBank.getBranchName());
		formobj.set("bankName", collectingBank.getCollectingBankName());
		formobj.set("branchName", collectingBank.getBranchName());
		String bankName=collectingBank.getCollectingBankName();
		String branchName=collectingBank.getBranchName();
		if(collectingBank.getCollectingBankName()==null)
		{
			bankName="";
		}
		if(collectingBank.getCollectingBankName()==null)
		{
			branchName="";
		}	
		if(bankName.equals("") || branchName.equals(""))
		{
			throw new NoMemberFoundException(
			"Bank details[Bank Name/Branch Name] are not exist for this["+memberId+"] member ID");
		} 
		
		//formobj.set("bankName", "IDBI");
	   //formobj.set("branchName", "NARIMAN POINT");
		return mapping.findForward("success");
	}
	
	public ActionForward fetchRecoveryDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		////System.out.println("updateRecoveryInfoActionJsonCall executed");
		String responseStr="";
		try
		{
			CPDAO c= new CPDAO();
			responseStr=c.daoFetchRecoveryDetails(request.getParameter("clmRefNo"));
			PrintWriter pw = response.getWriter();
			pw.write(responseStr);
	
		}
		catch(Exception e)
		{
			responseStr="0";
		}
		return null;
	}
	// Updated BY DKR Recovery Save
	public ActionForward saveRecoverydata(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession dsession = request.getSession(false);
		CPDAO cpDao=new CPDAO();
		RpDAO rpDao=new RpDAO();
		User user = getUserInformation(request);
		String bankid = (user.getBankId()).trim();
		String zoneid = (user.getZoneId()).trim();
		String branchid = (user.getBranchId()).trim();
		String memberId = bankid + zoneid + branchid;
		
	//System.out.println("updateRecoveryInfoAction ");		
		  String userid = user.getUserId();
		  PaymentDetails paymentDetails = new PaymentDetails();
		 
		 DynaValidatorActionForm formobj = (DynaValidatorActionForm) form;
		 RecoveryActionForm[] lines = (RecoveryActionForm[]) formobj.get("objRecoveryActionForm");
		 paymentDetails.setModeOfPayment(formobj.get("modeOfPayment").toString());
		 paymentDetails.setCollectingBank(formobj.get("bankName").toString());
		 paymentDetails.setCollectingBankBranch(formobj.get("branchName").toString());
				
		/* if("DD".equals(formobj.get("modeOfPayment").toString()))
		 {
			 java.util.Date paymentDate =(Date) formobj.get("ddPaymentDate");
			 paymentDetails.setPaymentDate(paymentDate);
			 paymentDetails.setInstrumentNo(formobj.get("instrumentNumber").toString());
			 java.util.Date instrumentDate =(Date) formobj.get("instrumentDate");
			 paymentDetails.setInstrumentDate(instrumentDate);
			 double instrumentAmount =Double.parseDouble(formobj.get("totalInstrumentAmount").toString());
			 paymentDetails.setInstrumentAmount(instrumentAmount);
			 paymentDetails.setDrawnAtBank(formobj.get("drawnAtBank").toString());
			 paymentDetails.setDrawnAtBranch(formobj.get("drawnAtBranch").toString());
			 paymentDetails.setPayableAt(formobj.get("instrumentpaybleAt").toString());
		 }
		 else
		 {*/			
			 java.util.Date paymentDate =(Date) formobj.get("rtgsPaymentDate");
			 paymentDetails.setPaymentDate(paymentDate);
			 paymentDetails.setInstrumentNo(formobj.get("utrNumber").toString());
			 paymentDetails.setInstrumentDate(null);
			 double rtgsAmount = Double.parseDouble(formobj.get("rtgsAmount").toString());			 
			 paymentDetails.setInstrumentAmount(rtgsAmount);
			 paymentDetails.getInstrumentAmount();
			 paymentDetails.setDrawnAtBank("");
			 paymentDetails.setDrawnAtBranch("");
			 paymentDetails.setPayableAt("");
		// }
		 paymentDetails.setModeOfDelivery("");
		 paymentDetails.setCgtsiAccNumber("");
		 paymentDetails.setAllocationType1("R");                                   // FLAG
		// System.out.println("tableRowCount"+formobj.get("tableRowCount"));
		 RecoveryActionForm objRecoveryActionForm[] = new RecoveryActionForm[Integer.parseInt(formobj.get("tableRowCount").toString())];		
		 String paymentID=cpDao.createPayId(paymentDetails);               // Payment Detail 		 
		 String payIdVisualAcMsg = "";
		 if(paymentID!=null)
		 {
			//  SAVE TOTAL
			 double totalAmountToDisplay=Double.parseDouble(request.getParameter("totalAmountToDisplay"));	
			 System.out.println("totalAmountToDisplay:-------------------"+totalAmountToDisplay);
			 double totalPenalBankIntRateDisplay=Double.parseDouble(request.getParameter("totalPenalBankIntRateDisplay"));
			 double totalAmt2PaidDisplay=Double.parseDouble(request.getParameter("totalAmt2PaidDisplay"));	       
			 paymentDetails.setTotalPenalBankIntRateDisplay(totalPenalBankIntRateDisplay);
			 paymentDetails.setTotalRemettedAmt(totalAmountToDisplay);	
			 
		     String virtualAcNo = rpDao.insertRecoveryNEFTRTGSPayment(paymentID,paymentDetails,user); 		   
		     payIdVisualAcMsg  = "PayID: "+paymentID+ "/ Virtual A/C No:."+virtualAcNo;		    	
		     dsession.setAttribute("payIdVisualAcMsg", payIdVisualAcMsg);				  
		    
		  for (int i = 1; i <= Integer.parseInt(formobj.get("tableRowCount").toString()); i++) {
			  
			  double  amoutRemitted=0.0d;			  
			  Date mliRecoveryDateRecv;
			  double penalBankIntRate=0.0d;
			  double totalAmt2Paid=0.0d;		 
			  
			  try
			  { 
				   amoutRemitted=Double.parseDouble(lines[i].getAmoutRemitted());			  
				   mliRecoveryDateRecv=lines[i].getMliRecoveryDateRecv();					   
				   penalBankIntRate=lines[i].getPenalBankIntRate();	
				   totalAmt2Paid=lines[i].getTotalAmt2Paid();
			  }
			  catch(Exception e)
			  {
				   amoutRemitted=0;			  
				   mliRecoveryDateRecv=null;
				   penalBankIntRate=0;
				   totalAmt2Paid=0;
				   totalAmountToDisplay=0;
				   totalPenalBankIntRateDisplay=0;
				   totalAmt2PaidDisplay=0;
						
			  }
			  
			  if(null!=lines[i].getDecision())
			  {
			//System.out.println("getTypeOfRecovery"+lines[i].getDecision());
				  if(lines[i].getDecision().equalsIgnoreCase("on"))
				  {
					  objRecoveryActionForm[i-1]=new RecoveryActionForm();
					  objRecoveryActionForm[i-1].setClaimRefNo(lines[i].getClaimRefNo());
					  objRecoveryActionForm[i-1].setCgpan(lines[i].getCgpan());
					  objRecoveryActionForm[i-1].setUnitName(lines[i].getUnitName());
					  objRecoveryActionForm[i-1].setFirstInstallmentAmount(lines[i].getFirstInstallmentAmount());
					  objRecoveryActionForm[i-1].setPreviouseRecoveredAmount(lines[i].getPreviouseRecoveredAmount());
					  objRecoveryActionForm[i-1].setTypeOfRecovery(lines[i].getTypeOfRecovery());
					  objRecoveryActionForm[i-1].setRecoveredAmout(lines[i].getRecoveredAmout());					  
					  // Added by DKR for MLIDATE
					  objRecoveryActionForm[i-1].setAmoutRemitted(lines[i].getAmoutRemitted());
					  objRecoveryActionForm[i-1].setPenalBankIntRate(lines[i].getPenalBankIntRate());
					  objRecoveryActionForm[i-1].setMliRecoveryDateRecv(lines[i].getMliRecoveryDateRecv());
					  objRecoveryActionForm[i-1].setTotalAmt2Paid(lines[i].getTotalAmt2Paid());						
						/*
						 * System.out.println("**********DKR******lines[i].getAmoutRemitted()::"+lines[i
						 * ].getAmoutRemitted() +
						 * "lines[i].getPenalBankIntRate() ::"+lines[i].getPenalBankIntRate() +
						 * "lines[i].getMliRecoveryDateRecv()::"+lines[i].getMliRecoveryDateRecv()+
						 * "lines[i].getTotalAmt2Paid()"+lines[i].getTotalAmt2Paid());
						 */
					//RECOVRY_AFTER_BEFORE_FST_CLAIM
					  if("0".equals(lines[i].getHiddenFieldForClaimNotSettled()) && amoutRemitted > 0)
					  {
						  //System.out.println("claim after settlement"+lines[i].getClaimRefNo());
						  objRecoveryActionForm[i-1].setLegalExpenses(lines[i].getLegalExpenses());
						  objRecoveryActionForm[i-1].setAmoutRemitted(lines[i].getAmoutRemitted());						  
						  objRecoveryActionForm[i-1].setPenalBankIntRate(lines[i].getPenalBankIntRate());   /// need to move
						  objRecoveryActionForm[i-1].setMliRecoveryDateRecv(lines[i].getMliRecoveryDateRecv());
						  objRecoveryActionForm[i-1].setTotalAmt2Paid(lines[i].getTotalAmt2Paid());	
						  						  
						  System.out.println("*********DKR*******lines[i].getAmoutRemitted()::"+lines[i].getAmoutRemitted() + 
								  "lines[i].getPenalBankIntRate() ::"+lines[i].getPenalBankIntRate() +
								  "lines[i].getMliRecoveryDateRecv()::"+lines[i].getMliRecoveryDateRecv()+"lines[i].getTotalAmt2Paid()"+lines[i].getTotalAmt2Paid());
								
						  if(cpDao.saveRecoveryDetails(objRecoveryActionForm[i-1], paymentID , userid ,"AC")==1)
						  {
							  throw new NoMemberFoundException(
								"Error while Saving recovery details , kindly contact to support[support@cgtmse.in] team");
						  }
					  }
				      else if("1".equals(lines[i].getHiddenFieldForClaimNotSettled()))
					  {	
				    	  System.out.println("claim before settlement"+lines[i].getClaimRefNo());
				    	  objRecoveryActionForm[i-1].setLegalExpenses("0");
						  objRecoveryActionForm[i-1].setAmoutRemitted("0");
						  objRecoveryActionForm[i-1].setTypeOfRecovery("0");
						  if(cpDao.saveRecoveryDetails(objRecoveryActionForm[i-1], paymentID , userid ,"BC")==1)
						  {
							  throw new NoMemberFoundException(
								"Error while Saving recovery details , kindly contact to support[support@cgtmse.in] team");
						  }
					  }
					 
				  }
			  }
			
		  }	
		 }else
		 {
			 throw new NoMemberFoundException(
						"Error while generating PayID , kindly contact to support[support@cgtmse.in] team");
		 }
		return mapping.findForward("successfullySaved");
		
		/*
		CPDAO cpDao=new CPDAO();
	//System.out.println("updateRecoveryInfoAction ");
		User user = getUserInformation(request);
		String userid = user.getUserId();
		  PaymentDetails paymentDetails = new PaymentDetails();
		 
		DynaValidatorActionForm formobj = (DynaValidatorActionForm) form;
		RecoveryActionForm[] lines = (RecoveryActionForm[]) formobj.get("objRecoveryActionForm");
		 paymentDetails.setModeOfPayment(formobj.get("modeOfPayment").toString());
		 paymentDetails.setCollectingBank(formobj.get("bankName").toString());
		 paymentDetails.setCollectingBankBranch(formobj.get("branchName").toString());
		 
		 if("DD".equals(formobj.get("modeOfPayment").toString()))
		 {
			 java.util.Date paymentDate =(Date) formobj.get("ddPaymentDate");
			 paymentDetails.setPaymentDate(paymentDate);
			 paymentDetails.setInstrumentNo(formobj.get("instrumentNumber").toString());
			 java.util.Date instrumentDate =(Date) formobj.get("instrumentDate");
			 paymentDetails.setInstrumentDate(instrumentDate);
			 double instrumentAmount=Double.parseDouble(formobj.get("totalInstrumentAmount").toString());
			 paymentDetails.setInstrumentAmount(instrumentAmount);
			 paymentDetails.setDrawnAtBank(formobj.get("drawnAtBank").toString());
			 paymentDetails.setDrawnAtBranch(formobj.get("drawnAtBranch").toString());
			 paymentDetails.setPayableAt(formobj.get("instrumentpaybleAt").toString());
		 }
		 else
		 {
			 java.util.Date paymentDate =(Date) formobj.get("rtgsPaymentDate");
			 paymentDetails.setPaymentDate(paymentDate);
			 paymentDetails.setInstrumentNo(formobj.get("utrNumber").toString());
			 paymentDetails.setInstrumentDate(null);
			 double rtgsAmount =Double.parseDouble(formobj.get("rtgsAmount").toString());
			 paymentDetails.setInstrumentAmount(rtgsAmount);
			 paymentDetails.setDrawnAtBank("");
			 paymentDetails.setDrawnAtBranch("");
			 paymentDetails.setPayableAt("");
		 }
		 paymentDetails.setModeOfDelivery("");
		 paymentDetails.setCgtsiAccNumber("");
		 paymentDetails.setAllocationType1("R");
		// System.out.println("tableRowCount"+formobj.get("tableRowCount"));
		 RecoveryActionForm objRecoveryActionForm[] = new RecoveryActionForm[Integer.parseInt(formobj.get("tableRowCount").toString())];
		 
		 String paymentID=cpDao.createPayId(paymentDetails);
		 if(paymentID!=null)
		 {
		// //System.out.println("paymentID "+paymentID);
		// //System.out.println("Payment Mode:"+formobj.get("modeOfPayment"));
		//System.out.println("Name:" + formobj.get("tableRowCount"));
		// //System.out.println("length "+lines[0].getDecision().length());
		  for (int i = 1; i <= Integer.parseInt(formobj.get("tableRowCount").toString()); i++) {
			//  lines[i] = new RecoveryActionForm();
			//  System.out.println(lines[i].getCgpan()+"lines"+lines[i].getClaimRefNo());
			  double  amoutRemitted=0;
			  try
			  {
				  amoutRemitted=Double.parseDouble(lines[i].getAmoutRemitted());
			  }
			  catch(Exception e)
			  {
				  amoutRemitted=0;
			  }
			  
			  if(lines[i].getDecision()!=null)
			  {
			//System.out.println("getTypeOfRecovery"+lines[i].getDecision());
				  if(lines[i].getDecision().equalsIgnoreCase("on"))
				  {

					  objRecoveryActionForm[i-1]=new RecoveryActionForm();
					//  System.out.println("getPreviouseRecoveredAmount "+lines[i].getRecoveredAmout());
					//  System.out.println("getAmoutRemitted "+lines[i].getAmoutRemitted());
					  objRecoveryActionForm[i-1].setClaimRefNo(lines[i].getClaimRefNo());
					  objRecoveryActionForm[i-1].setCgpan(lines[i].getCgpan());
					  objRecoveryActionForm[i-1].setUnitName(lines[i].getUnitName());
					  objRecoveryActionForm[i-1].setFirstInstallmentAmount(lines[i].getFirstInstallmentAmount());
					  objRecoveryActionForm[i-1].setPreviouseRecoveredAmount(lines[i].getPreviouseRecoveredAmount());
					  objRecoveryActionForm[i-1].setTypeOfRecovery(lines[i].getTypeOfRecovery());
					  objRecoveryActionForm[i-1].setRecoveredAmout(lines[i].getRecoveredAmout());
					  
					//  System.out.println("getTypeOfRecovery"+lines[i].getHiddenFieldForClaimNotSettled());
					//RECOVRY_AFTER_BEFORE_FST_CLAIM
					  if("0".equals(lines[i].getHiddenFieldForClaimNotSettled()) && amoutRemitted > 0)
					  {
						  System.out.println("claim after settlement"+lines[i].getClaimRefNo());
						  objRecoveryActionForm[i-1].setLegalExpenses(lines[i].getLegalExpenses());
						  objRecoveryActionForm[i-1].setAmoutRemitted(lines[i].getAmoutRemitted());
						  if(cpDao.saveRecoveryDetails(objRecoveryActionForm[i-1], paymentID , userid ,"AC")==1)
						  {
							  throw new NoMemberFoundException(
								"Error while Saving recovery details , kindly contact to support[support@cgtmse.in] team");
						  }
					  }
				      else if("1".equals(lines[i].getHiddenFieldForClaimNotSettled()))
					  {	
				    	  System.out.println("claim before settlement"+lines[i].getClaimRefNo());
				    	  objRecoveryActionForm[i-1].setLegalExpenses("0");
						  objRecoveryActionForm[i-1].setAmoutRemitted("0");
						  objRecoveryActionForm[i-1].setTypeOfRecovery("0");
						  if(cpDao.saveRecoveryDetails(objRecoveryActionForm[i-1], paymentID , userid ,"BC")==1)
						  {
							  throw new NoMemberFoundException(
								"Error while Saving recovery details , kindly contact to support[support@cgtmse.in] team");
						  }
					  }
					 
				  }
			  }
			
		  }	
		 }else
		 {
			 throw new NoMemberFoundException(
						"Error while generating PayID , kindly contact to support[support@cgtmse.in] team");
		 }
		return mapping.findForward("successfullySaved");
	*/}
	
	public ActionForward setBankId(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(Log.INFO, "ClaimAction", "setBankId", "Entered");
		ClaimActionForm claimForm = (ClaimActionForm) form;
		claimForm.resetTheForm(mapping, request);
		// HttpSession session = request.getSession(false);
		Log.log(Log.INFO, "ClaimAction", "setBankId",
				"Retrieving user info from request object");
		User user = getUserInformation(request);
		String bankid = (user.getBankId()).trim();
		String zoneid = (user.getZoneId()).trim();
		String branchid = (user.getBranchId()).trim();
		String memberId = bankid + zoneid + branchid;

		if (!bankid.equals(Constants.CGTSI_USER_BANK_ID)) {
			claimForm.setMemberId(memberId);
		}
		claimForm.setBankId(bankid);
		Log.log(Log.INFO, "ClaimAction", "setBankId", "Exited");
		return mapping.findForward("getBorrowerId");
	}

	/**
	 * 
	 * @author Sukumar@path
	 * @since 05-May-2011
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws java.lang.Exception
	 */
	public ActionForward claimStatusChange(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(Log.INFO, "ClaimAction", "claimStatusChange", "Entered");

		ClaimActionForm claimForm = (ClaimActionForm) form;

		// claimForm.resetTheForm(mapping,request);

		claimForm.setClaimRefNum("");
		claimForm.setEnterCgpan("");

		return mapping.findForward("changeStatus");

	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws java.lang.Exception
	 */
	public ActionForward afterclaimStatusChange(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(Log.INFO, "ClaimAction", "afterclaimStatusChange", "Entered");

		ClaimActionForm claimForm = (ClaimActionForm) form;
		String claimRefNum = claimForm.getClaimRefNum();
		String cgpan = claimForm.getEnterCgpan();
		CPDAO cpDAO = new CPDAO();
		String claimRefNo = null;

		boolean statusFlag = false;
		User user = getUserInformation(request);
		String userId = user.getUserId();

		if (!(claimRefNum == null || claimRefNum.equals(""))
				&& !(cgpan == null || cgpan.equals(""))) {
			// //System.out.println("CGPAN and Clm Ref No :"+cgpan+claimRefNum);
			claimRefNo = cpDAO.getClaimRefNo(cgpan);
			if (!(claimRefNum.equals(claimRefNo))) {
				throw new DatabaseException(
						"CGPAN and Claim Ref no does not Match");
			}
			cpDAO.updateClaimApplicationStatus(claimRefNum, cgpan, userId);
			statusFlag = true;
		} else if (!(claimRefNum == null || claimRefNum.equals(""))) {
			// //System.out.println("claimRefNum:"+claimRefNum);

			cpDAO.updateClaimApplicationStatus(claimRefNum, cgpan, userId);
			statusFlag = true;
		} else if (!(cgpan == null || cgpan.equals(""))) {

			cpDAO.updateClaimApplicationStatus(claimRefNum, cgpan, userId);
			statusFlag = true;
		}

		if (!statusFlag) {
			throw new DatabaseException(
					"Unable Update Claim Application Status");
		}

		request.setAttribute("message",
				"Claim Application Status Changed Successfully");
		Log.log(Log.INFO, "ClaimAction", "afterclaimStatusChange", "Exited");

		return mapping.findForward("success");

	}

	public ActionForward getDeclartaionDetail(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(Log.INFO, "ClaimAction", "getDeclartionDeatils", "Entered");

		// DynaActionForm dynaForm = (DynaActionForm)form;


		Log.log(Log.INFO, "ClaimAction", "getDeclartionDeatils", "Exited");
		return mapping.findForward("success");
	}

	public ActionForward getDeclartaionDetailData(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(Log.INFO, "ClaimAction", "getDeclartaionDetailData", "Entered");

		String memberId = null;
		String enterclaimReff = null;
		String enterCgpan = null;
		ClaimActionForm dynaForm = (ClaimActionForm) form;

		try {
			memberId = dynaForm.getEnterMember().toString();

			enterclaimReff = dynaForm.getAppRefNo().toString().toUpperCase();

			enterCgpan = dynaForm.getEnterCgpan().toString().toUpperCase();

		} catch (Exception e) {

			throw new NoMemberFoundException(
					"Enter CGPAN Or Claim Reff Number.");

		}
		if (memberId.equals("")) {
			throw new NoMemberFoundException("Enter valid Member ID.");

		}
		if (!memberId.equals("")) {
			Connection connection = DBConnection.getConnection();
			String claimreff = null;
			try {

				if (enterclaimReff.equals("") && enterCgpan.equals("")) {
					throw new NoMemberFoundException(
							"Enter CGPAN Or Claim Reff Number.");
				}

				Statement str = connection.createStatement(
						ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_READ_ONLY);

				// /////////////////code for checkeing member id presnt or not
				// ///////////////////////
				String one = memberId.substring(0, 4);
				String two = memberId.substring(4, 8);
				String three = memberId.substring(8, 12);

				String MemIdavailbel = "select c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id member_id from application_detail c \n"
						+ "where  c.mem_bnk_id='"
						+ one
						+ "' and\n"
						+ "c.mem_zne_id='"
						+ two
						+ "' and\n"
						+ "c.mem_brn_id='"
						+ three + "'";
				ResultSet rsforavailmemid = str.executeQuery(MemIdavailbel);
				if (!rsforavailmemid.next()) {
					throw new NoMemberFoundException(
							" Member Id  Not Exsist : ");
				}

				// /////////////////code for checkeing member id presnt or not
				// over ///////////////////////

				if (!enterCgpan.equals("")) {

					// String
					// qury1="select clm_ref_no from application_detail a,ssi_detail s,claim_detail c\n"
					// +
					// " where a.ssi_reference_number=s.ssi_reference_number\n"
					// +
					// " and s.bid=c.bid\n" +
					// " and cgpan='CG200100748WC'";

					// String
					// qury1="select clm_ref_no from application_detail a,ssi_detail s,claim_detail_temp c\n"
					// +
					// " where a.ssi_reference_number=s.ssi_reference_number\n"
					// +
					// " and s.bid=c.bid\n" +
					// " and cgpan='"+enterCgpan+"' ";

					String cgQury = "select c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id member_id from application_detail c where cgpan='"
							+ enterCgpan + "'";
					ResultSet rsforvalid = str.executeQuery(cgQury);
					String memberid = ""; // checkin CGPAN is Correct or not for
											// memberid Enterd
					if (!rsforvalid.next()) {
						throw new NoMemberFoundException(
								" CGPAN  Not Exsist : ");
					}
					rsforvalid.beforeFirst();
					while (rsforvalid.next()) {

						memberid = rsforvalid.getString(1);
					}
					// //System.out.println("After Vlid CGPAN is Correct or not for memberid Enterd");
					if (memberid.equals(memberId)) {

						// String chck="select clm_Ref_no from claim_detail\n" +
						// "where bid in\n" +
						// "(select s.bid from ssi_detail s,application_detail a\n"
						// +
						// "where a.ssi_Reference_number = s.ssi_Reference_number\n"
						// +
						// "and a.cgpan = '"+enterCgpan+"'\n" +
						// "and  a.mem_bnk_id||a.mem_zne_id||a.mem_brn_id='"+memberId+"')";
						// //checking where Claim application Alredy Approved Or
						// not

						String chck = " select clm_Ref_no from claim_detail\n"
								+ "where bid in\n"
								+ "(select s.bid from ssi_detail s,application_detail a\n"
								+ "where a.ssi_Reference_number = s.ssi_Reference_number\n"
								+ "and a.cgpan = '"
								+ enterCgpan
								+ "'\n"
								+ "and  a.mem_bnk_id||a.mem_zne_id||a.mem_brn_id='"
								+ memberId + "')"; // checking where Claim
													// application Alredy
													// Approved Or not

						ResultSet rsChk = str.executeQuery(chck);

						if (!rsChk.next()) {
							String qury1 = "select clm_Ref_no from claim_detail_temp\n"
									+ "where bid in\n"
									+ "(select s.bid from ssi_detail s,application_detail a\n"
									+ "where a.ssi_Reference_number = s.ssi_Reference_number\n"
									+ "and a.cgpan = '"
									+ enterCgpan
									+ "'\n"
									+ "and  a.mem_bnk_id||a.mem_zne_id||a.mem_brn_id='"
									+ memberId + "')"; // checking claim
														// application Lodge or
														// not

							ResultSet rs1 = str.executeQuery(qury1);
							if (!rs1.next()) {
								throw new NoMemberFoundException(
										"Claim Application Not Lodged : ");
							}

							rs1.beforeFirst();
							while (rs1.next()) {

								claimreff = rs1.getString(1);
								// //System.out.println("the Value Retrive is :--->"+claimreff);
							}
						} // ChK close checking where Claim application Alredy
							// Approved Or not
						else {
							String ClaimReffNo = "";
							rsChk.beforeFirst();
							while (rsChk.next()) {
								ClaimReffNo = rsChk.getString(1);
							}
							throw new NoMemberFoundException(
									"Claim Application Already Approved for Claim Reff Number "
											+ ClaimReffNo);
						}
					} else if (!memberid.equals(memberId)) {
						throw new NoMemberFoundException(
								" Member Id and CGPAN are not Associated: ");
					}
				} // enterCgpan!=null if over
				String qury2 = null;
				if (!enterclaimReff.equals("")) {
					if (claimreff != null) {
						if (!(enterclaimReff.equals(claimreff))) {
							throw new NoMemberFoundException(
									"Claim Reff Number Does Not Exsist.");
						}
					}
					// //System.out.println("Before Excuting the Query!!!!!!!");

					qury2 = "select c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id member_id,C.clm_ref_no,ssi_unit_name,npa_effective_dt,D.cgpan,\n"
							+ "a.app_approved_date_time,a.app_approved_amount \n"
							+ "from claim_detail_temp c,ssi_detail s,npa_detail n,application_detail a,claim_application_amount_temp d \n"
							+ "where c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id='"
							+ memberId
							+ "' \n"
							+ "and C.clm_ref_no='"
							+ enterclaimReff
							+ "' \n"
							+ "and C.CLM_REF_NO=D.CLM_REF_NO AND s.bid=c.BID \n"
							+ "and n.bid=c.bid\n"
							+ "and a.ssi_reference_number=s.ssi_reference_number \n"
							+ "union\n"
							+ "select c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id member_id,C.clm_ref_no,ssi_unit_name,npa_effective_dt,D.cgpan,\n"
							+ "a.app_approved_date_time,a.app_approved_amount \n"
							+ "from claim_detail_temp c,ssi_detail s,npa_detail_temp n,application_detail a,CLAIM_APPLICATION_AMOUNT_TEMP D \n"
							+ "where c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id='"
							+ memberId
							+ "' \n"
							+ "and C.clm_ref_no='"
							+ enterclaimReff
							+ "' \n"
							+ "and C.CLM_REF_NO=D.CLM_REF_NO AND s.bid=c.BID \n"
							+ "and app_status not in ('RE') and n.bid=c.bid\n"
							+ "and a.ssi_reference_number=s.ssi_reference_number\n";

				} else {

					qury2 = "select c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id member_id,C.clm_ref_no,ssi_unit_name,npa_effective_dt,D.cgpan,\n"
							+ "a.app_approved_date_time,a.app_approved_amount \n"
							+ "from claim_detail_temp c,ssi_detail s,npa_detail n,application_detail a,CLAIM_APPLICATION_AMOUNT_TEMP D \n"
							+ "where c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id='"
							+ memberId
							+ "' \n"
							+ "and C.clm_ref_no='"
							+ claimreff
							+ "' \n"
							+ "and C.CLM_REF_NO=D.CLM_REF_NO AND  s.bid=c.BID \n"
							+ "and n.bid=c.bid\n"
							+ "and a.ssi_reference_number=s.ssi_reference_number \n"
							+ "union\n"
							+ "select c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id member_id,C.clm_ref_no,ssi_unit_name,npa_effective_dt,D.cgpan,\n"
							+ "a.app_approved_date_time,a.app_approved_amount \n"
							+ "from claim_detail_temp c,ssi_detail s,npa_detail_temp n,application_detail a,CLAIM_APPLICATION_AMOUNT_TEMP D \n"
							+ "where c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id='"
							+ memberId
							+ "' \n"
							+ "and C.clm_ref_no='"
							+ claimreff
							+ "' \n"
							+ "and C.CLM_REF_NO=D.CLM_REF_NO AND s.bid=c.BID \n"
							+ " and app_status not in ('RE') and n.bid=c.bid\n"
							+ "and a.ssi_reference_number=s.ssi_reference_number";

				}

				if (enterclaimReff.equals("") && claimreff.equals("")) {
					throw new NoMemberFoundException(
							"Claim Reff Number Does Not Exist ");
				}
				Connection connection1 = DBConnection.getConnection();
				Statement str1 = connection1.createStatement();
				String quryforSelect = "select * from Claim_detail_temp where clm_declaration_recvd='Y' and clm_decl_recvd_dt is Not Null";
				ResultSet rs = str1.executeQuery(quryforSelect);
				String claimreffNo = null;

				while (rs.next()) {
					claimreffNo = rs.getString(1);
					if (claimreffNo.equals(enterclaimReff)
							|| claimreffNo.equals(claimreff)) {
						throw new NoMemberFoundException(
								"Declaration Already updated for  Claim Reffrence Number  ");
					}
				}
				// /////////writeing code for claimReffe valid or
				// not///////////////
				String chkMemForClaim = "";
				if (!enterclaimReff.equals("")) {

					// if (claimreff!=null){
					// // if (!(enterclaimReff.equals(claimreff)))
					// // {
					// // throw new
					// NoMemberFoundException("Claim Reff Number Does Not Exsist.");
					// // }
					// chkMemForClaim="select sum(cnt) from (\n" +
					// "select count(clm_ref_no) cnt from claim_detail_temp where clm_ref_no='"+enterclaimReff+"' \n"
					// +
					// "union all\n" +
					// "select count(clm_ref_no) cnt from claim_detail  where clm_ref_no='"+enterclaimReff+"')";

					chkMemForClaim = "select clm_ref_no cnt from claim_detail_temp where clm_ref_no='"
							+ enterclaimReff
							+ "'   \n"
							+ "union all\n"
							+ "select clm_ref_no cnt from claim_detail  where clm_ref_no='"
							+ enterclaimReff + "'";

					ResultSet chkclaimRefavil = str1
							.executeQuery(chkMemForClaim);
					while (!chkclaimRefavil.next()) {
						throw new NoMemberFoundException(
								"Claim Reference Number Not Exist  : ");
					}
				}

				// /////////writeing code for memberId + claimReffe
				// combinationcheck

				// ////////////////////////////////////////////////code for
				// check alredy approved or not////////////////////////
				String claimappro = "select clm_ref_no from claim_detail where clm_ref_no='"
						+ enterclaimReff + "' ";
				ResultSet claimapptest = str1.executeQuery(claimappro);
				while (claimapptest.next()) {
					throw new NoMemberFoundException(
							"Claim Application Already Approved  : ");
				}

				// //////code for check alredy approved or
				// not////////////////////////

				ResultSet results = str.executeQuery(qury2);

				String memID = null;

				ArrayList claimDeclrationArr = new ArrayList();

				if (!results.next()) {
					throw new NoMemberFoundException(
							"Member Id and claim Reference Number Mismatch  : ");
				}
				// //System.out.println("After while Not eqils to next");
				results.beforeFirst();
				ClaimActionForm claimdetail2 = new ClaimActionForm();
				while (results.next()) {

					ClaimActionForm claimdetail = new ClaimActionForm();
					memID = results.getString(1);
					claimdetail2.setMemberID(memID);

					claimdetail2.setClaimRefNum(results.getString(2));

					claimdetail2.setSsiUnitName(results.getString(3));

					claimdetail2.setDtOfNPAReportedToCGTSI(results.getDate(4));
					claimdetail.setCgpanNo(results.getString(5));

					claimdetail.setAppApproveDate(results.getDate(6));

					claimdetail.setAppAmount(results.getString(7));

					claimDeclrationArr.add(claimdetail);
					claimdetail2.setClaimdeatil(claimDeclrationArr);

				}

				if (memID.equals("")) {
					throw new NoMemberFoundException(
							"Member ID Does Not Exsist : ");
				}
				Date date = new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				int month = calendar.get(Calendar.MONTH);
				int day = calendar.get(Calendar.DATE);
				month = month - 1;
				day = day + 1;
				calendar.set(Calendar.MONTH, month);
				calendar.set(Calendar.DATE, day);
				Date prevDate = calendar.getTime();
				claimdetail2.setDateOfdeclartionRecive(date);
				//

				BeanUtils.copyProperties(dynaForm, claimdetail2);

			} finally {
				DBConnection.freeConnection(connection);
			} // finally close
		}
		Log.log(Log.INFO, "ClaimAction", "getDeclarationDetailData", "Exited");
		return mapping.findForward("detailsuccess");
	}

	public ActionForward SaveDeclartaionDetailData(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Log.log(Log.INFO, "ClaimAction", "SaveDeclartaionDetailData", "Entered");

		ClaimActionForm dynaForm = (ClaimActionForm) form;
		boolean falgvalue = false;
		String Dateofdeclartionrecvied = "";
		String Clamireff = "";
		String memId = "";
		try {

			// memId=dynaForm.get("enterMember").toString();
			Clamireff = dynaForm.getClaimRefNum().toString();
			//
			// Dateofdeclartionrecvied=dynaForm.get("dateOfdeclartionRecive").toString();

			// memId=request.getParameter("enterMember");

			// Clamireff=request.getParameter("claimRefNum").toString();

			Dateofdeclartionrecvied = request.getParameter(
					"dateOfdeclartionRecive").toString();

			falgvalue = dynaForm.getBooleanProperty();
			// //System.out.println("date of mem ....!"+memId);
			// //System.out.println("date of claim ....!"+Clamireff);
			// //System.out.println("date of declration ....!"+Dateofdeclartionrecvied);
			// //System.out.println("date of flag ....!"+falgvalue);

		} catch (Exception e) {
			// //System.out.println("inside catch/.");
			e.getMessage();
		}
		// DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		if (Dateofdeclartionrecvied.equals("")) {
			throw new NoMemberFoundException("Date Is Null  : ");
		}
		String converttoDBform = DateHelper
				.stringToDBDate(Dateofdeclartionrecvied);
		if (falgvalue == true) {
			String falg = "Y";
			// //System.out.println("inside condition!!!!!!!");
			Connection connection = DBConnection.getConnection();
			try {
				Statement str = connection.createStatement();
				String qury = "update Claim_detail_temp set clm_declaration_recvd='"
						+ falg
						+ "',clm_decl_recvd_dt='"
						+ converttoDBform
						+ "' where clm_ref_no='" + Clamireff + "'";
				str.executeUpdate(qury);

				request.setAttribute("message",
						"Declaration Recived Update Sucsessfully ");
				// HttpSession session=request.getSession(false);
				// session.invalidate();
				dynaForm.setEnterCgpan("");
				dynaForm.setAppRefNo("");
				dynaForm.setEnterCgpan("");
				// Boolean b=new Boolean(false);
				dynaForm.setBooleanProperty(false);

			} // try closoe
			catch (Exception e) {
				e.printStackTrace();
			} // ctach close
			finally {

				DBConnection.freeConnection(connection);
			} // finally close
		} else {
			throw new NoMemberFoundException(
					"Please Select Check Box For Update  : ");

		}

		Log.log(Log.INFO, "ClaimAction", "SaveDeclartaionDetailData", "Exited");
		return mapping.findForward("successfullUpdate");
	}

	public ActionForward getRecoveryDetail(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(Log.INFO, "ClaimAction", "getRecoveryDetail", "Entered");

		ClaimActionForm claimfm = (ClaimActionForm) form;
		claimfm.setEnterMember("");
		claimfm.setEnterCgpan("");

		Log.log(Log.INFO, "ClaimAction", "getRecoveryDetail", "Exited");
		return mapping.findForward("recoveryDetailsuccess");
	} // method getRecoveryDetail over

	public ActionForward getRecoveryDetailData1(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(Log.INFO, "ClaimAction", "getRecoveryDetailData", "Entered");

		String enteredMemberId = null;
		String enterCgpan = null;
		ActionForm dynaForm = form;
		try {
			enteredMemberId = request.getParameter("enterMember");
			enterCgpan = request.getParameter("enterCgpan").toUpperCase();

		} catch (Exception e) {
			throw new NoMemberFoundException("Enter CGPAN and  Member Id.");
		}

		if (!enteredMemberId.equals(null)) {

			if (!enterCgpan.equals(null)) {

				Connection connection = DBConnection.getConnection();
				try {

					Statement str = connection.createStatement(
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_READ_ONLY);

					String one = enteredMemberId.substring(0, 4);
					String two = enteredMemberId.substring(4, 8);
					String three = enteredMemberId.substring(8, 12);

					// check member id Exsist or not
					String MemIdavailbel = "select c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id member_id from application_detail c \n"
							+ "where  c.mem_bnk_id='"
							+ one
							+ "' and\n"
							+ "c.mem_zne_id='"
							+ two
							+ "' and\n"
							+ "c.mem_brn_id='" + three + "'";
					ResultSet rsforavailmemid = str.executeQuery(MemIdavailbel);
					if (!rsforavailmemid.next()) {
						throw new NoMemberFoundException(
								" Member Id  Not Exsist : ");
					}
					// Over check member id Exsist or not
					// Check CGPAN Exsist Or Not

					String cgQury = "select c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id member_id from application_detail c where cgpan='"
							+ enterCgpan + "'";
					ResultSet rsforvalid = str.executeQuery(cgQury);
					String memberid = "";
					if (!rsforvalid.next()) {
						throw new NoMemberFoundException(
								" CGPAN  Not Exsist : ");
					}

					// Over Check CGPAN Exsist Or Not
					// checking Member Id and Cgpan are Associated Or not

					rsforvalid.beforeFirst();
					while (rsforvalid.next()) {
						memberid = rsforvalid.getString(1);
					}
					if (!memberid.equals(enteredMemberId)) {
						throw new NoMemberFoundException(
								" Enterd Member Id and CGPAN  Not Associated. ");
					}
					// Over checking Member Id and Cgpan are Associated Or not

					// retrive claim reference number using CGPAN and Member id
					String crefQuery = "select clm_Ref_no from claim_detail  \n"
							+ "where bid in \n"
							+ "(select s.bid from ssi_detail s,application_detail a \n"
							+ "where a.ssi_Reference_number = s.ssi_Reference_number \n"
							+ "and a.cgpan = '"
							+ enterCgpan
							+ "'\n"
							+ "and  a.mem_bnk_id||a.mem_zne_id||a.mem_brn_id='"
							+ enteredMemberId + "')";

					ResultSet rsforClaimRefNo = str.executeQuery(crefQuery);
					while (!rsforClaimRefNo.next()) {
						throw new NoMemberFoundException(
								" Claim Application Does Not Exsist for Entred CGPAN and Member Id. ");
					}
					rsforClaimRefNo.beforeFirst();
					String retrivedClmRefNo = "";
					while (rsforClaimRefNo.next()) {
						retrivedClmRefNo = rsforClaimRefNo.getString(1);
					}

					HttpSession session = request.getSession(true);
					session.setAttribute("clmReff", retrivedClmRefNo);
					session.setAttribute("cgpan", enterCgpan);

					// Over retrive claim reference number using CGPAN and
					// Member id

					/*
					 * Need to Write Code for checking the claim is setteled or
					 * not and first insatllment realaeded or not, if claim is
					 * not settled and first installment is not relaese then
					 * show apropriate msg and also check wheather claim realse
					 * second installment or not if second installment relase
					 * then there is no recovey process so show appropriate msg
					 * for this condtion also
					 */

					// here is code for chercking all

					// rsforClaimRefNo
					String quryfortestapp = "select clm_status,clm_installment_flag from claim_detail where clm_ref_no ='"
							+ retrivedClmRefNo + "'";
					ResultSet rsforchkapp = str.executeQuery(quryfortestapp);
					String clmstatus = "";
					String clminstallmentgflag = "";
					while (rsforchkapp.next()) {
						clmstatus = rsforchkapp.getString(1);

						clminstallmentgflag = rsforchkapp.getString(2);

					}
					if (!clmstatus.equals("AP")) {
						throw new NoMemberFoundException(
								" The Claim Application is Not Approved. ");
					}

					String QuryForchcond = "select * from claim_application_amount where cgpan='"
							+ enterCgpan + "'";
					ResultSet rsforchkCond = str.executeQuery(QuryForchcond);
					if (!rsforchkCond.next()) {
						throw new NoMemberFoundException(
								" The Claim Applications fisrt Installment not Release. ");
					}

					// here code done
					String QuryForRetriveInfo = "select c.CLM_MLI_NAME,c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id mem_id ,c.CLM_PLACE,c.clm_ref_no,ca.cgpan,ssi_unit_name unit,Ctd_TC_CLM_ELIG_AMT elg_amt,CTD_TC_FIRST_INST_PAY_AMT First_Inst\n"
							+ "from claim_detail c,ssi_detail s,claim_application_amount ca,CLAIM_TC_DETAIL T\n"
							+ "where ca.clm_ref_no=c.clm_ref_no\n"
							+ "and  c.clm_status='AP'\n"
							+ "and  c.clm_installment_flag='F'\n"
							+ "and  c.bid=s.bid\n"
							+ "AND t.CGPAN=ca.CGPAN\n"
							+ "and c.clm_ref_no in(select clm_ref_no from claim_application_amount where cgpan='"
							+ enterCgpan
							+ "')\n"
							+ "union\n"
							+ "select c.CLM_MLI_NAME,c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id mem_id ,c.CLM_PLACE,c.clm_ref_no,ca.cgpan,ssi_unit_name unit,Cwd_wC_CLM_ELIG_AMT elg_amt,Cwd_wC_FIRST_INST_PAY_AMT First_Inst\n"
							+ "from claim_detail c,ssi_detail s,claim_application_amount ca,CLAIM_wC_DETAIL T\n"
							+ "where ca.clm_ref_no=c.clm_ref_no\n"
							+ "and  c.clm_status='AP'\n"
							+ "and  c.clm_installment_flag='F'\n"
							+ "and  c.bid=s.bid\n"
							+ "AND t.CGPAN=ca.CGPAN\n"
							+ "and c.clm_ref_no in(select clm_ref_no from claim_application_amount where cgpan='"
							+ enterCgpan + "')";

					ResultSet rsforRetrirvedInfo = str
							.executeQuery(QuryForRetriveInfo);
					ClaimActionForm claimActionForm = new ClaimActionForm();
					if (rsforRetrirvedInfo.next()) {

						claimActionForm.setMliName(rsforRetrirvedInfo
								.getString(1));
						claimActionForm.setMliId(rsforRetrirvedInfo
								.getString(2));
						claimActionForm
								.setPlaceforClmRecovery(rsforRetrirvedInfo
										.getString(3));
						claimActionForm.setClmRefNumber(rsforRetrirvedInfo
								.getString(4));
						claimActionForm
								.setCgpanforclamRecovery(rsforRetrirvedInfo
										.getString(5));
						claimActionForm.setUnitName(rsforRetrirvedInfo
								.getString(6));
						claimActionForm.setClmEligibleAmt(rsforRetrirvedInfo
								.getDouble(7));
						claimActionForm
								.setFirstinstalmentrelease(rsforRetrirvedInfo
										.getDouble(8));

					}

					// select CCRD_RECEIPT_DATE, CCRD_RECOVERY_AMOUNT,
					// CCRD_RECOVERY_EXPENSES, CCRD_EXPENSES_DEDUCTED,
					// CCRD_NET_RECOVERY, CCRD_REC_DD_NO, CCRD_REC_DD_DT,
					// CCRD_REMARKS from CLM_CGPAN_RECOVERY_DETAIL where
					// clm_ref_no='CL05KE020049301'
					ArrayList viewArr = new ArrayList();
					// String
					// viewquery="select CCRD_RECEIPT_DATE, CCRD_RECOVERY_AMOUNT, CCRD_RECOVERY_EXPENSES, CCRD_EXPENSES_DEDUCTED, CCRD_NET_RECOVERY, CCRD_REC_DD_NO,  CCRD_REC_DD_DT, CCRD_REMARKS from  CLM_CGPAN_RECOVERY_DETAIL where clm_ref_no='CL05KE020049301'";
					String viewquery = "select * from CLM_CGPAN_RECOVERY_DETAIL where clm_ref_no='"
							+ retrivedClmRefNo + "'";
					ResultSet rsforViewData = str.executeQuery(viewquery);

					while (rsforViewData.next()) {
						ClaimActionForm claimActionForm2 = new ClaimActionForm();

						claimActionForm2.setDateOfreciept(rsforViewData
								.getDate(4));
						claimActionForm2.setAmtRecipt(rsforViewData
								.getDouble(5));
						claimActionForm2.setExpIncforRecovery(rsforViewData
								.getDouble(6));
						claimActionForm2.setExpDeducted(rsforViewData
								.getString(7));
						claimActionForm2.setNetRecovery(rsforViewData
								.getDouble(8));
						claimActionForm2.setDdNo(rsforViewData.getString(9));
						claimActionForm2.setDdDate(rsforViewData.getDate(10));
						claimActionForm2.setRemark(rsforViewData.getString(11));

						viewArr.add(claimActionForm2);
					}

					String clmRefnoId = claimActionForm.getClmRefNumber();
					// ////////////////////////new code for final recovery to
					// check final recovery done or not///////

					String selectRecovery = "SELECT CCRD_FINAL_RECOVERY  FROM CLM_CGPAN_RECOVERY_DETAIL WHERE CLM_REF_NO='"
							+ clmRefnoId + "' ";
					String finalrecovr = "";
					ResultSet rs = str.executeQuery(selectRecovery);
					while (rs.next()) {
						finalrecovr = rs.getString(1);

						if (finalrecovr.equals("Y")) {
							throw new NoMemberFoundException(
									"Final recovery Alreday Made for Claim . ");

						}

					}
					// ////////////////////////////////////////////////////////////////////////////

					claimActionForm.setViewRecArr(viewArr);

					BeanUtils.copyProperties(dynaForm, claimActionForm);
				} finally {
					DBConnection.freeConnection(connection);

				}

			} // if (!enterCgpan.equals(null))
		} // (!memberId.equals(null))

		Log.log(Log.INFO, "ClaimAction", "getRecoveryDetailData", "Exited");

		return mapping.findForward("ClaimRecoverysuccess");
	} // method getRecoveryDetailData over

	public ActionForward SaveClaimrecovryData1(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Log.log(Log.INFO, "ClaimAction", "SaveClaimrecovryData", "Entered");

		// ActionForm actionform=form;
		ClaimActionForm actionform = (ClaimActionForm) form;
		HttpSession session = request.getSession(true);
		User user = (User) session
				.getAttribute(com.cgtsi.util.SessionConstants.USER);
		String userid = user.getUserId();

		Date recDate1 = actionform.getDateOfreciept();
		String recDate = recDate1.toString();

		double amtreciept = actionform.getAmtRecipt();
		double expInForrecover = actionform.getExpIncforRecovery();
		boolean booleanExp1 = actionform.isBooleanExpInc();
		String booleanExp = "";
		if (booleanExp1 == true) {
			booleanExp = "Y";
		} else {
			booleanExp = "N";
		}
		double netrecover = actionform.getNetRecovery();
		String ddNo = actionform.getDdNo();
		Date ddDate1 = actionform.getDdDate();
		String ddDate = ddDate1.toString();
		String remark = actionform.getRemark();

		String clmreff = (String) session.getAttribute("clmReff");
		String cgPan = (String) session.getAttribute("cgpan");
		// String cgPan=request.getParameter("cgpanforclamRecovery");

		Date date = Calendar.getInstance().getTime();
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String toadsDate = formatter.format(date);

		String currentDate = DateHelper.stringToDBDate(toadsDate);

		String reciptDate = DateHelper.stringToDBDate(recDate);
		String dddbDate = DateHelper.stringToDBDate(ddDate);

		// String falgvalue="N";
		boolean falgvalue1 = false;
		String falgvalue = "";
		try {
			// falgvalue=request.getParameter("booleanFinalRecovery");
			falgvalue1 = actionform.isBooleanFinalRecovery();

			if (falgvalue1 == true) {
				falgvalue = "Y";

			} else {
				falgvalue = "N";

			}
		} catch (Exception e) {
			e.getMessage();
		}

		int rowCount = 1;
		Connection connection = DBConnection.getConnection();
		int count = 0;
		try {
			Statement str = connection.createStatement();

			String insertRecovery = "INSERT INTO CLM_CGPAN_RECOVERY_DETAIL( CCRD_SR_NO, CLM_REF_NO, CGPAN, CCRD_RECEIPT_DATE, CCRD_RECOVERY_AMOUNT, CCRD_RECOVERY_EXPENSES, CCRD_EXPENSES_DEDUCTED, CCRD_NET_RECOVERY, CCRD_REC_DD_NO,  CCRD_REC_DD_DT, CCRD_REMARKS,CCRD_CREATED_MODIFIED_BY,CCRD_CREATED_MODIFIED_DT,CCRD_FINAL_RECOVERY )\n"
					+ "VALUES (CLM_CGP_REC_SEQ.nextval,'"
					+ clmreff
					+ "', '"
					+ cgPan
					+ "', '"
					+ reciptDate
					+ "' ,'"
					+ amtreciept
					+ "' ,'"
					+ expInForrecover
					+ "' ,'"
					+ booleanExp
					+ "' ,'"
					+ netrecover
					+ "' ,'"
					+ ddNo
					+ "' ,'"
					+ dddbDate
					+ "' ,'"
					+ remark
					+ "','"
					+ userid
					+ "','"
					+ currentDate + "' ,'" + falgvalue + "')";

			str.executeUpdate(insertRecovery);

			// }//for close
		} // try closoe
		catch (Exception e) {
			e.printStackTrace();
		} // ctach close
		finally {

			DBConnection.freeConnection(connection);
		} // finally close

		Log.log(Log.INFO, "ClaimAction", "SaveClaimrecovryData", "Exited");

		return mapping.findForward("recoverysuccess");
	}

	/**
	 * 
	 * added by sukumar@path on 05-Sep-2009 for updating the claim settled
	 * details where claim settled manually
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws java.lang.Exception
	 */
	public ActionForward updateClaimSettledDetailsNew(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Log.log(Log.INFO, "ClaimAction", "updateClaimSettledDetailsNew",
				"Entered");
		// //System.out.println("updateClaimSettledDetailsNew Entered");
		ClaimActionForm claimForm = (ClaimActionForm) form;
		claimForm.resetTheForm(mapping, request);

		Log.log(Log.INFO, "ClaimAction", "updateClaimSettledDetailsNew",
				"Exited");
		return mapping.findForward("success");
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws java.lang.Exception
	 */
	public ActionForward afterUpdateClaimSettledDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Log.log(Log.INFO, "ClaimAction", "afterUpdateClaimSettledDetails",
				"Entered");
		ClaimActionForm claimForm = (ClaimActionForm) form;
		// claimForm.resetTheForm(mapping,request);
		// //System.out.println("afterUpdateClaimSettledDetails Entered");

		Log.log(Log.INFO, "ClaimAction", "afterUpdateClaimSettledDetails",
				"Exited");
		return mapping.findForward("success");
	}
// Change by DKR
	public ActionForward forwardToNextPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// ActionErrors errors = null;
		// //System.out.println("Claim Action forwardToNextPage Entered");
		HttpSession session = (HttpSession) request.getSession(false);
		 int conditnPassed = 0;
		if ((((String) session.getAttribute("mainMenu")).equals(MenuOptions
				.getMenu(MenuOptions.CP_CLAIM_FOR)))
				&& ((session.getAttribute("subMenuItem")).equals(MenuOptions
						.getMenu(MenuOptions.CP_CLAIM_FOR_FIRST_INSTALLMENT)))) {
			Log.log(Log.INFO, "ClaimAction", "forwardToNextPage", "Entered");
			ClaimActionForm claimForm = (ClaimActionForm) form;

			StringTokenizer tokenizer = null;

			ClaimsProcessor processor = new ClaimsProcessor();

			// Retrieving the memberId
			String memberId = ((claimForm.getMemberId()).toUpperCase()).trim();
			// //System.out.println("memberId:"+memberId);
			// Retrieving the borrowerId
			String borrowerId = ((claimForm.getBorrowerID()).toUpperCase())
					.trim();
			// //System.out.println("borrowerId:"+borrowerId);
			// Retrieving the CGPAN
			String inputcgpan = ((claimForm.getCgpan()).toUpperCase()).trim();
			System.out.println("inputcgpan:RAJU"+inputcgpan);
			// Validating MemberId, BorrowerId and CGPAN
			claimForm = validateMemIdBidCgpan(memberId, borrowerId, inputcgpan,
					claimForm);

	//checking cgpan status
			
			Connection connection1 = DBConnection.getConnection();
			Statement strcgpan = connection1.createStatement();
			String checkQrycheck =" select bid from ssi_detail where ssi_reference_number in (select ssi_reference_number from application_detail" +
					"  where cgpan='"+inputcgpan+"') ";
			System.out.println(checkQrycheck );
			ResultSet rscgpan = strcgpan.executeQuery(checkQrycheck);
			String bid = null;
			
			//int check1 = 0;

			while (rscgpan.next()) {
				bid = rscgpan.getString(1);
			}


			String checkQry =" select count(*) from Claim_detail_temp where bid='"+bid+"' ";
			System.out.println(checkQry );
			ResultSet rspend = strcgpan.executeQuery(checkQry);
			int check = 0;
			
			//int check1 = 0;

			while (rspend.next()) {
				check = (Integer.parseInt(rspend.getString(1)));
			}


			if(check>0)
			{
					throw new NoMemberFoundException(
							"Your claim is alrady lodged it is under processing  ");
			}
			
			String checkQry1 = "select count(*) from Claim_detail where bid='"+bid+"' ";
			System.out.println(checkQry1 );
			ResultSet rsapproved = strcgpan.executeQuery(checkQry1);
			int check1 = 0;

			while (rsapproved.next()) {
				check1 = (Integer.parseInt(rsapproved.getString(1)));
			}


			if(check1>0)
			{
					throw new NoMemberFoundException(
							"Your claim is aproved please check at claim status wise report ");
			}
			//end checking cgpan status
			
			
			// Updating the properties of claim form
			memberId = claimForm.getMemberId();
			borrowerId = ((claimForm.getBorrowerID()).toUpperCase()).trim();
			inputcgpan = ((claimForm.getCgpan()).toUpperCase()).trim();

			String bankId = memberId.substring(0, 4);
			String zoneId = memberId.substring(4, 8);
			String branchId = memberId.substring(8, 12);

			HashMap whichClmApplicationsHasUserFiled = processor
					.getClmRefAndFlagDtls(bankId, zoneId, branchId, borrowerId);

			if (whichClmApplicationsHasUserFiled != null) {

				if (whichClmApplicationsHasUserFiled.size() > 0) {

					java.util.Set valuesSet = whichClmApplicationsHasUserFiled
							.keySet();
					java.util.Iterator valuesIterator = valuesSet.iterator();
					while (valuesIterator.hasNext()) {

						String installmentFlag = null;
						String cgclan = null;
						String firstSettlementDate = null;
						String id = (String) valuesIterator.next();

						String val = (String) whichClmApplicationsHasUserFiled
								.get(id);
						// //System.out.println("val:"+val);
						if (id.equals(ClaimConstants.FIRST_INSTALLMENT)) {
							// //System.out.println("Control 4");
							
							if  (val.equals("PMA")) {
								throw new NoDataException(
										"Application for Claim First Installment has been filed and is pending at Checker Level.");
							}
							
							if  (val.equals("PCG")) {
								throw new NoDataException(
										"Application for Claim First Installment has been filed and is pending Processing by CGTSI.");
							}
								if  (val.equals("CRE")) {
								throw new NoDataException(
										"Application for Claim First Installment has been returned by the Checker.Please relodge the  claim by  using Claim Processing-->Claim For--->Resubmission of D/U option");
							}
							
							
							
							
							if (val.equals(ClaimConstants.CLM_DELIMITER4)) {
								throw new NoDataException(
										"Application for Claim First Installment has already been filed and is rejected.");
							}
							if (val.equals(ClaimConstants.CLM_DELIMITER5)) {
								throw new NoDataException(
										"Claim Application has been completely settled.");
							} else if (!val.equals("")) {
								// //System.out.println("Control 5");
								// //System.out.println("ID :" + id);
								tokenizer = new StringTokenizer(val,
										ClaimConstants.CLM_DELIMITER1);
								// boolean isStatusRead = false;
								boolean isFlagRead = false;
								boolean isCGCLANRead = false;
								boolean isSettlementDateRead = false;
								while (tokenizer.hasMoreTokens()) {
									// //System.out.println("Control 6");
									String token = (String) tokenizer
											.nextToken();
									// //System.out.println("Next TOken :" +
									// token);
									if (!isSettlementDateRead) {
										if (!isCGCLANRead) {
											if (!isFlagRead) {
												installmentFlag = token;
												isFlagRead = true;
												continue;
											}
											cgclan = token;
											isCGCLANRead = true;
											continue;
										}
										firstSettlementDate = token;
										isSettlementDateRead = true;
										continue;
									}
								}
								// //System.out.println("firstSettlementDate :" +
								// firstSettlementDate);
								if (firstSettlementDate
										.equals(ClaimConstants.CLM_NO_VALUE)) {
									throw new NoDataException(
											"Application for First Claim Installment has already been filed and approved and pending for Settlement by CGTSI");
								} else {
									throw new NoDataException(
											"Application for First Claim Installment has already been Filed, Approved and Settled by CGTSI");
								}

							}
						} else if (id.equals(ClaimConstants.SECOND_INSTALLMENT)) {
							continue;
						}

					}
				}
			}

			// Checking if the borrower has paid guarantee fee for all his loan
			// accounts
			Log.log(Log.INFO, "ClaimAction", "forwardToNextPage",
					"retriving the details to check if member has paid guarantee fee ......");
			Vector gfeeDtlsVector = processor.getLockInDetails(borrowerId);
			Vector gfeePaidCGPANS = new Vector();
			boolean gfeepaid = false;
			// //System.out.println("Size of the vector :" +
			// gfeeDtlsVector.size());
			for (int i = 0; i < gfeeDtlsVector.size(); i++) {
				HashMap gfeedtl = (HashMap) gfeeDtlsVector.elementAt(i);
				if (gfeedtl != null) {
					Log.log(Log.INFO, "ClaimAction", "forwardToNextPage",
							"Printing gfeedtl :" + gfeedtl);
					// //System.out.println("Printing the hashmap :" + gfeedtl);
					String cgpn = (String) gfeedtl
							.get(ClaimConstants.CLM_CGPAN);
					java.util.Date gfeedate = (java.util.Date) gfeedtl
							.get(ClaimConstants.CLM_GUARANTEE_START_DT);
					// //System.out.println("cgpan:"+cgpn);
					// //System.out.println("gfeedate:"+gfeedate);
					Log.log(Log.INFO, "ClaimAction", "forwardToNextPage",
							"------> Printing gfeedate :" + gfeedate);
					if (gfeedate != null) {
						gfeepaid = true;
						if (!gfeePaidCGPANS.contains(cgpn)) {
							gfeePaidCGPANS.addElement(cgpn);
						}
					} else {
						gfeepaid = false;
					}
					// Clearing the HashMap
					// gfeedtl.clear();
					gfeedtl = null;
				}
			}

			// Clearing up the Vector
			// gfeeDtlsVector.clear();
			gfeeDtlsVector = null;

			if (gfeePaidCGPANS.size() == 0) {
				throw new NoDataException(
						" Kindly update the disbursement details online before proceeding for claim lodgment. The menu is Guarantee Maintenance-->Periodic Info--> Disbursement details. Please make sure Guarantee Fee has been paid and Disbursement Details, if any, are available for the CGPAN(s) of the Borrower.");
			}

			// Check if the npa details are available
			// The Hashmap contains NPA Classified Date, NPA Reporting date and
			// Reason for turning NPA
			Log.log(Log.INFO, "ClaimAction", "forwardToNextPage",
					"retrieving details to check if npa details exist for the borrower......");
			HashMap npadetails = processor.isNPADetailsAvailable(borrowerId);
			Log.log(Log.INFO, "ClaimAction", "forwardToNextPage",
					"Printing NPA Details :" + npadetails);
			// //System.out.println("Printing HashMap :" + npadetails);

			String willfulDefaulter = null;
			boolean npaDtlsAvl = false;
			// java.util.Date npaClassifiedDate = null;

			/*
			 * java.util.Date npaclassifieddt =
			 * (java.util.Date)npadetails.get(ClaimConstants.NPA_CLASSIFIED_DT);
			 * String reasonsfornpa =
			 * (String)npadetails.get(ClaimConstants.REASONS_FOR_TURNING_NPA);
			 * if((npaclassifieddt == null) || (reasonsfornpa == null)) {
			 * Log.log(Log.INFO,"ClaimAction","forwardToNextPage",
			 * "Error : npa details do not exist for the borrower."); // return
			 * mapping.findForward("npaDetailsPage"); throw new
			 * NoDataException("NPA Details not available."); }
			 */
			if (npadetails == null) {
				session.setAttribute(ClaimConstants.CLM_MEMBER_ID, memberId);
				session.setAttribute(ClaimConstants.CLM_BORROWER_ID, borrowerId);
				// return mapping.findForward("npaDetailsPage");
				throw new NoDataException(
						"NPA Details not available.Please go to GuaranteeMaintenance-->PeriodicInfo-->NPA Details.");
			}
			if (npadetails.size() == 0) {
				session.setAttribute(ClaimConstants.CLM_MEMBER_ID, memberId);
				session.setAttribute(ClaimConstants.CLM_BORROWER_ID, borrowerId);
				// return mapping.findForward("npaDetailsPage");
				throw new NoDataException(
						"NPA Details not available.Please go to GuaranteeMaintenance-->PeriodicInfo-->NPA Details.");
			}

			// Retrieving the HashMaps containing npa details from main and temp
			// tables.

			HashMap npadtlMainTable = (HashMap) npadetails
					.get(ClaimConstants.CLM_MAIN_TABLE);
			if (npadtlMainTable != null) {
				if (npadtlMainTable.size() > 0) {
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					java.util.Date npaCreatedDate = (Date) npadtlMainTable
							.get("npaCreatedDate");
					if (npaCreatedDate == null) {
						throw new NoDataException(
								"Please Update Npa Details.Please go to GuaranteeMaintenance-->PeriodicInfo-->NPA Details.");
					}
					/* added by sukumar@path for capturing claim npa details */

					java.util.Date npaClassified = (java.util.Date) npadtlMainTable
							.get("NPAClassifiedDate");

					String npaClassifiedDateStr = sdf.format(npaClassified);
					// //System.out.println("NPA Classified:"+npaClassified);
					claimForm.setDateOnWhichAccountClassifiedNPA(npaClassified);
					if (npaClassifiedDateStr != null) {
						claimForm.setNpaDateNew(npaClassifiedDateStr);
						// //System.out.println("npaClassifiedDateStr from Main:"+npaClassifiedDateStr);
					}

					willfulDefaulter = (String) npadtlMainTable
							.get(ClaimConstants.WILLFUL_DEFAULTER);
					if (willfulDefaulter != null) {
						npaDtlsAvl = npaDtlsAvl || true;
					}
				}
			}

			HashMap npadtltemptable = (HashMap) npadetails
					.get(ClaimConstants.CLM_TEMP_TABLE);
			if (npadtltemptable != null) {
				if (npadtltemptable.size() > 0) {
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					java.util.Date npaCreatedDate = (Date) npadtltemptable
							.get("npaCreatedDate");
					if (npaCreatedDate == null) {
						throw new NoDataException(
								"Please Update Npa Details.Please go to GuaranteeMaintenance-->PeriodicInfo-->NPA Details.");
					}

					java.util.Date npaClassified = (java.util.Date) npadtltemptable
							.get("NPAClassifiedDate");

					String npaClassifiedDateStr = sdf.format(npaClassified);
					// //System.out.println("NPA Classified:"+npaClassified);
					claimForm.setDateOnWhichAccountClassifiedNPA(npaClassified);
					if (npaClassifiedDateStr != null) {
						claimForm.setNpaDateNew(npaClassifiedDateStr);
						// //System.out.println("npaClassifiedDateStr from Temp:"+npaClassifiedDateStr);
					}

					willfulDefaulter = (String) npadtltemptable
							.get(ClaimConstants.WILLFUL_DEFAULTER);
					if (willfulDefaulter != null) {
						npaDtlsAvl = npaDtlsAvl || true;
					}
				}
			}

			if (!npaDtlsAvl) {
				Log.log(Log.INFO, "ClaimAction", "forwardToNextPage",
						"Error : npa details do not exist for the borrower.");
				// return mapping.findForward("npaDetailsPage");
				throw new NoDataException(
						"NPA Details not available.GuaranteeMaintenance-->PeriodicInfo-->NPA Details.");
			}

			// Check if the Legal Proceedings details are available
			/*
			 * Comment on 10-11-2014 as it is not used till the time of claim
			 * lodgement
			 */
			/*
			 * LegalProceedingsDetail legaldetails = null; Log.log(Log.INFO,
			 * "ClaimAction", "forwardToNextPage",
			 * "retrieving the details to check if there are legal proceeding details for the borrower......."
			 * ); legaldetails =
			 * processor.isLegalProceedingsDetailAvl(borrowerId); boolean
			 * legalDtlsAvl = legaldetails .getAreLegalProceedingsDetailsAvl();
			 * if (!legalDtlsAvl) { // return
			 * mapping.findForward("npaDetailsPage"); Log.log(Log.INFO,
			 * "ClaimAction", "forwardToNextPage",
			 * "Error: Legal Details are not available for the borrower......."
			 * ); // throw new NoDataException("Legal Details not available.");
			 * }
			 */

			// Check if Disbursement details are avaiable
			Log.log(Log.INFO, "ClaimAction", "forwardToNextPage",
					"retrieving the details to check if disbursement details exist......");
			Hashtable disbursementdetails = processor
					.isDisbursementDetailsAvl(borrowerId);
			if (disbursementdetails == null) {				
				 throw new NoDataException(
				 "No Disbursement Details available.Please go to GuaranteeMaintenance-->PeriodicInfo-->Disbursement Details.");
				 
			}
			if (disbursementdetails.size() == 0) {				
				  throw new NoDataException(
				  "No Disbursement Details available.Please go to GuaranteeMaintenance-->PeriodicInfo-->Disbursement Details.");
				 
			}
			Enumeration cgpankeys = disbursementdetails.keys();
			String message = "";
			boolean isDisbursemntDtlsAvl = true;
			while (cgpankeys.hasMoreElements()) {
				String cgpan = (String) cgpankeys.nextElement();
				String gfeecgpan = null;
				for (int i = 0; i < gfeePaidCGPANS.size(); i++) {
					gfeecgpan = (String) gfeePaidCGPANS.elementAt(i);
					if ((gfeecgpan != null) && (cgpan != null)) {
						if (gfeecgpan.equals(cgpan)) {
							if ("TC".equals(cgpan.substring(cgpan.length() - 2))) {
								String status = (String) disbursementdetails
										.get(cgpan);
								if (!(status
										.equals(ClaimConstants.DISBRSMNT_YES_FLAG))) {
									isDisbursemntDtlsAvl = false;
								}
							}
						}
					}
				}
			}
			if (!isDisbursemntDtlsAvl) {
				Log.log(Log.INFO,
						"ClaimAction",
						"forwardToNextPage",
						"Error: Final Disbursement details not available for those CGPAN(s) whose Guarantee Fee has been paid.");
				
				  throw new NoDataException(
				  "Final Disbursement details not available for those CGPAN(s) whose Guarantee Fee has been paid. Please furnish Final Disbursement Details");
				 
			}

			// disbursementdetails.clear();
			// disbursementdetails = null;

			// Check if the lock-in period is complete
			Log.log(Log.INFO, "ClaimAction", "forwardToNextPage",
					"retrieving the details to check if lock-in period is over......");
			boolean islockinperiodover = processor
					.isLockinPeriodOver(borrowerId);
			 Vector sancDetails = processor.getSancDetails(borrowerId);
	            ArrayList datesList = new ArrayList();
	            Date conditionDt = null;
	            if(sancDetails.size() > 0)
	            {
	                Date sancDate = null;
	                Date tempDt = null;
	                double totapprovedAmt = 0.0D;
	                for(int i = 0; i < sancDetails.size(); i++)
	                {
	                    HashMap sancMap = (HashMap)sancDetails.elementAt(i);
	                    String cgpan = (String)sancMap.get("cgpan");
	                    Date sancDt = (Date)sancMap.get("sancDt");
	                   // //System.out.println(sancDt+"=cgpan="+cgpan);
	                    datesList.add(sancDt);
	                    double apprvdAmount = ((Double)sancMap.get("apprvdAmt")).doubleValue();
	                    totapprovedAmt += apprvdAmount;
	                    conditionDt = (Date)sancMap.get("condnDate");
	                }
//System.out.println("datesList "+datesList);
	                Collections.sort(datesList);
	                sancDate = (Date)datesList.get(0);
	                int j = sancDate.compareTo(conditionDt);
	                if(j > 0 && totapprovedAmt <= 50000D)
	                    conditnPassed = 1;
	                else
	                if(j == 0 && totapprovedAmt <= 50000D)
	                    conditnPassed = 1;
	                else
	                    conditnPassed = 2;
	            }
			/* *
			 * added by sukumar@path for capturing Guarantee Start Dt, Last
			 * disbursement date and NPA Expiry dt for lodgement The lending
			 * institution may invoke the guarantee in respect of credit
			 * facility within a maximum period of one year from date of NPA, if
			 * NPA is after lock-in period or within one year of lock-in period,
			 * if NPA is within lock-in period
			 */
			Vector lockindetails = processor.getLockInDetails(borrowerId);
			String lckdtl_cgpan = null;
			java.util.Date lckdtl_gstartdate = null;
			java.util.Date lckdtl_dtlastdsbrsmnt = null;
			java.util.Date lckdtl_lockin_start_date = null;
			java.util.Date tempdate = null;

			if (lockindetails.size() > 0) {
				for (int i = 0; i < lockindetails.size(); i++) {
					HashMap temp = (HashMap) lockindetails.elementAt(i);
					lckdtl_cgpan = (String) temp.get(ClaimConstants.CLM_CGPAN);
					lckdtl_gstartdate = (java.util.Date) temp
							.get(ClaimConstants.CLM_GUARANTEE_START_DT);
					//System.out.println("lckdtl_gstartdate "+lckdtl_gstartdate);
					lckdtl_dtlastdsbrsmnt = (java.util.Date) temp
							.get(ClaimConstants.CLM_LAST_DISBURSEMENT_DT);
					//System.out.println("lckdtl_dtlastdsbrsmnt "+lckdtl_dtlastdsbrsmnt);
					if (lckdtl_cgpan != null) {
						if ((lckdtl_gstartdate != null)
								&& (lckdtl_dtlastdsbrsmnt != null)) {
							if ((lckdtl_gstartdate
									.compareTo(lckdtl_dtlastdsbrsmnt)) < 0) {
								tempdate = lckdtl_dtlastdsbrsmnt;
							}
							if ((lckdtl_gstartdate
									.compareTo(lckdtl_dtlastdsbrsmnt)) > 0) {
								tempdate = lckdtl_gstartdate;
							}
							if ((lckdtl_gstartdate
									.compareTo(lckdtl_dtlastdsbrsmnt)) == 0) {
								tempdate = lckdtl_gstartdate;
							}
						} else if ((lckdtl_gstartdate != null)
								&& (lckdtl_dtlastdsbrsmnt == null)) {
							tempdate = lckdtl_gstartdate;
						} else {
							continue;
						}
					} else {
						continue;
					}
				}
				//System.out.println("tempdate "+tempdate);
			}
			Administrator admin = new Administrator();
			ParameterMaster parameterMaster = admin.getParameter();
			int lockinperiodmonths = parameterMaster.getLockInPeriod();
			//System.out.println("lockinperiodmonths "+lockinperiodmonths);
			java.util.Date lockinperiodenddate = processor.getDate(tempdate,
					lockinperiodmonths);
			//System.out.println("lockinperiodenddate "+lockinperiodenddate);
			Map map = processor.getAppSanctionDate(borrowerId, memberId);
           // Date fixed01012013 = DateHelper.getFixedDate("01/01/2013");
            Date npaClassfied = claimForm.getDateOnWhichAccountClassifiedNPA();
            //System.out.println("npaClassfied "+npaClassfied);
            Date expiryDateTenure = lockinperiodenddate;
            //System.out.println("expiryDateTenure "+expiryDateTenure);
          //commented by koteswar  if(lockinperiodenddate.compareTo(npaClassfied) < 0)
            //commented by koteswa    expiryDateTenure = npaClassfied;
            //koteswar  start for claim expiry date caluclation on 28 oct 2015 
                                  
            
            if ((lockinperiodenddate != null)
					&& (npaClassfied != null)) {
				if ((lockinperiodenddate
						.compareTo(npaClassfied)) < 0) {
					expiryDateTenure = npaClassfied;
				}
				if ((lockinperiodenddate
						.compareTo(npaClassfied)) > 0) {
					expiryDateTenure = lockinperiodenddate;
				}
				if ((lockinperiodenddate
						.compareTo(npaClassfied)) == 0) {
					expiryDateTenure = lockinperiodenddate;
				}
			}
            
            //koteswar  end  for claim expiry date caluclation on 28 oct 2015 
            
            Set dates = new TreeSet();
            //System.out.println("borrowerId "+borrowerId);
            Map npa_map = (new CPDAO()).getCgpanFlagsForBid(borrowerId);
            Vector tccgs = (Vector)npa_map.get("tccgpans");
            Vector wccgs = (Vector)npa_map.get("wccgpans");
            for(int i = 0; i < tccgs.size(); i++)
            {
                Map m = (HashMap)tccgs.get(i);
                Date sancDate = (Date)m.get("AppSanctionDate");
                //System.out.println("sancDate tccgs "+sancDate);
                dates.add(sancDate);
            }

            for(int i = 0; i < wccgs.size(); i++)
            {
                Map m = (HashMap)wccgs.get(i);
                Date sancDate = (Date)m.get("AppSanctionDate");
                //System.out.println("sancDate wccgs "+sancDate);
                dates.add(sancDate);
            }

            Iterator dateItr = dates.iterator();
            Date minSancDate = null;
            if(dateItr.hasNext())
                minSancDate = (Date)dateItr.next();
            //System.out.println("minSancDate  "+minSancDate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(minSancDate);
            int year = cal.get(1);
            //System.out.println("minSancDate year  "+year);
            Date claimExpiryDate = null;
            if(year >= 2013)
                claimExpiryDate = processor.getDate(expiryDateTenure, 24);
            else
                claimExpiryDate = processor.getDate(expiryDateTenure, 12);
            //System.out.println(dates);
            //System.out.println(minSancDate);
            //System.out.println(npaClassfied);
            
            //System.out.println("claimExpiryDate "+claimExpiryDate);
            //System.out.println("new Date() "+new Date());
            //System.out.println("claimExpiryDate.compareTo(new Date()) "+claimExpiryDate.compareTo(new Date()));
            if(claimExpiryDate.compareTo(new Date()) < 0)
                throw new DatabaseException("The lending institution may invoke the guarantee in respect of credit facility within a maximum period of one year ( or two year for guarantees on or after 01/01/2013) from the npa date or lock-in period whichever is later.");
            Date lockinperiodexpirydate = processor.getDate(lockinperiodenddate, 12);
			java.util.Date npaExpiryForLodgement = processor.getDate(
					npaClassfied, 12);

			 //System.out.println("lockinperiodexpirydate:"+lockinperiodexpirydate);
			 //System.out.println("npaExpiryForLodgement:"+npaExpiryForLodgement);
			// //System.out.println("NPA Classified Dt:"+claimForm.getDateOnWhichAccountClassifiedNPA());
			 //System.out.println("NPA Expiry Dt for Lodgement:"+npaExpiryForLodgement);
			 //System.out.println("lockinperiodexpirydate.compareTo(npaExpiryForLodgement):"+lockinperiodexpirydate.compareTo(npaExpiryForLodgement));
			 //System.out.println("lockinperiodexpirydate.compareTo(currentDate):"+lockinperiodexpirydate.compareTo(new java.util.Date()));
			if (!islockinperiodover) {
				Log.log(Log.INFO, "ClaimAction", "forwardToNextPage", "l......");
				throw new LockInPeriodNotCompletedException(
						"Lock In Period Not Completed for the Borrower."); // bhu comment testing 01072015
			}
			/*
			 * commented by sukumar@path for lodging claim applications where
			 * NPA Expiry date is before system date
			 */

	/*  //koteswar  start for claim expiry date caluclation on 28 oct 2015 
	 	else if ((lockinperiodexpirydate.compareTo(npaExpiryForLodgement)) < 0) {
				lockinperiodexpirydate = npaExpiryForLodgement;

				java.util.Date currentDate = new java.util.Date();

				if ((lockinperiodexpirydate.compareTo(currentDate)) < 0) {
					 throw new
					 DatabaseException("The lending institution may invoke the guarantee in respect of credit facility within a maximum period of one year from date of NPA, if NPA is after lock-in period or within one year of lock-in period, if NPA is within lock-in period ");
//					throw new DatabaseException(
//							"The lending institution may invoke the guarantee in respect of credit facility within a maximum period of one year from date of NPA or Lock-in period (which ever is later)");
				}
			} else if ((lockinperiodexpirydate.compareTo(npaExpiryForLodgement)) > 0) {

				java.util.Date currentDate = new java.util.Date();

				if ((lockinperiodexpirydate.compareTo(currentDate)) < 0) {
					 throw new
					 DatabaseException("3 The lending institution may invoke the guarantee in respect of credit facility within a maximum period of one year from date of NPA, if NPA is after lock-in period or within one year of lock-in period, if NPA is within lock-in period ");
//					throw new DatabaseException(
//							"The lending institution may invoke the guarantee in respect of credit facility within a maximum period of one year from date of NPA or Lock-in period (which ever is later)");
				}

			}  	  //koteswar  end for claim expiry date caluclation on 28 oct 2015 */
			ParameterMaster pm = (ParameterMaster) admin.getParameter();
			double minAmntForITPAN = pm.getMinAmtForMandatoryITPAN();
			// //System.out.println("minAmntForITPAN:"+minAmntForITPAN);
			// //System.out.println("borrowerId:"+borrowerId);
			Vector details = processor.getOTSRequestDetails(borrowerId);
			double amntSanctioned = 0.0;
			boolean isITPANDtlAvl = false;
			for (int i = 0; i < details.size(); i++) {
				Hashtable dtl = (Hashtable) details.elementAt(i);
				if (dtl == null) {
					continue;
				}
				Log.log(Log.INFO, "ClaimAction", "forwardToNextPage",
						"Printing the Hashmap to check ITPAN Details :" + dtl);
				String amntSanctionedStr = (String) dtl
						.get(ClaimConstants.CLM_APPLICATION_APPRVD_AMNT);
				if (amntSanctionedStr != null) {
					amntSanctioned = Double.parseDouble(amntSanctionedStr);
					Log.log(Log.INFO, "ClaimAction", "forwardToNextPage",
							"Printing the Hashmap to check ITPAN Details amntSanctioned:"
									+ amntSanctioned);

					if (amntSanctioned > minAmntForITPAN) {
						isITPANDtlAvl = checkIfITPANAvailable(memberId,
								borrowerId);
						if (!isITPANDtlAvl) {
							return mapping.findForward("itpanDtlsPage");
						}
					}
				}
			}

			// Redirecting the User to Recovery Filter page
			claimForm.setMemberId(memberId);
			claimForm.setBorrowerID(borrowerId);

			/*
			 * Clearing the Collection Objects
			 */
			// npadetails.clear();
			npadetails = null;
			// gfeePaidCGPANS.clear();
			gfeePaidCGPANS = null;
			// claimForm.setRecoveryFlag(null);
			claimForm.setRecoveryFlag(ClaimConstants.DISBRSMNT_NO_FLAG);
			session.setAttribute("conditnPassedVal", Integer.valueOf(conditnPassed));
			claimForm.resetTheFirstClaimApplication(mapping, request);
			return mapping.findForward("claimpage");
		}
		if ((((String) session.getAttribute("mainMenu")).equals(MenuOptions
				.getMenu(MenuOptions.CP_CLAIM_FOR)))
				&& ((session.getAttribute("subMenuItem")).equals(MenuOptions
						.getMenu(MenuOptions.CP_CLAIM_FOR_SECOND_INSTALLMENT)))) {
			ClaimActionForm claimForm = (ClaimActionForm) form;

			ClaimsProcessor processor = new ClaimsProcessor();

			// Retrieving the memberId
			String memberId = ((claimForm.getMemberId()).toUpperCase()).trim();

			// Retrieving the borrowerId
			String borrowerId = ((claimForm.getBorrowerID()).toUpperCase())
					.trim();

			// Retrieving the CGPAN
			String inputcgpan = ((claimForm.getCgpan()).toUpperCase()).trim();

			// Validating MemberId, BorrowerId and CGPAN
			claimForm = validateMemIdBidCgpan(memberId, borrowerId, inputcgpan,
					claimForm);

			// Updating the properties of claim form
			memberId = claimForm.getMemberId();
			borrowerId = ((claimForm.getBorrowerID()).toUpperCase()).trim();
			inputcgpan = ((claimForm.getCgpan()).toUpperCase()).trim();

			// Checking if the user has already filed claim second application
			String bankId = memberId.substring(0, 4);
			String zoneId = memberId.substring(4, 8);
			String branchId = memberId.substring(8, 12);

			HashMap whichClmApplicationsHasUserFiled = processor
					.getClmRefAndFlagDtls(bankId, zoneId, branchId, borrowerId);
			// //System.out.println("Printing the hashmap...:" +
			// whichClmApplicationsHasUserFiled);
			if (whichClmApplicationsHasUserFiled != null) {
				// //System.out.println("Control 1");
				if (whichClmApplicationsHasUserFiled.size() > 0) {
					// //System.out.println("Control 2");
					StringTokenizer tokenizer = null;
					java.util.Set valuesSet = whichClmApplicationsHasUserFiled
							.keySet();
					java.util.Iterator valuesIterator = valuesSet.iterator();
					while (valuesIterator.hasNext()) {
						// //System.out.println("Control 3");
						String installmentFlag = null;
						String cgclan = null;
						String firstSettlementDate = null;
						String secondSettlementDate = null;
						String id = (String) valuesIterator.next();

						String val = (String) whichClmApplicationsHasUserFiled
								.get(id);

						if (id.equals(ClaimConstants.FIRST_INSTALLMENT)) {
							// //System.out.println("Control 4");
							if (val == null) {
								throw new NoDataException(
										"Application for Claim First Installment is pending for Approval. Please file Application for Second Claim Installment after the Approval and Settlement of Application of Claim First Installment.");
							}
							if (val.equals(ClaimConstants.CLM_DELIMITER4)) {
								throw new NoDataException(
										"Application for Claim First Installment has already been filed and is rejected. You cannot file application for Claim Second Installment.");
							}
							if (val.equals("")) {
								throw new NoDataException(
										"Please file Application for Claim First Installment before filing for Claim Second Installment.");
							}
							if (val.equals(ClaimConstants.CLM_DELIMITER5)) {
								throw new NoDataException(
										"Claim Application has been completely settled.");
							} else {
								// //System.out.println("Control 5");
								// //System.out.println("ID :" + id);
								tokenizer = new StringTokenizer(val,
										ClaimConstants.CLM_DELIMITER1);
								boolean isFlagRead = false;
								boolean isCGCLANRead = false;
								boolean isSettlementDateRead = false;
								while (tokenizer.hasMoreTokens()) {
									// //System.out.println("Control 6");
									String token = (String) tokenizer
											.nextToken();
									// //System.out.println("Next TOken :" +
									// token);
									if (!isSettlementDateRead) {
										if (!isCGCLANRead) {
											if (!isFlagRead) {
												installmentFlag = token;
												isFlagRead = true;
												continue;
											}
											cgclan = token;
											isCGCLANRead = true;
											continue;
										}
										firstSettlementDate = token;
										isSettlementDateRead = true;
										continue;
									}
								}
								// //System.out.println("firstSettlementDate :" +
								// firstSettlementDate);
								if (firstSettlementDate
										.equals(ClaimConstants.CLM_NO_VALUE)) {
									throw new NoDataException(
											"Application for First Claim Installment has already been filed and approved and pending for Settlement by CGTSI. Please file Application for Second Claim Installment after the Settlement of First Claim Installment.");
								}
							}

						} else if (id.equals(ClaimConstants.SECOND_INSTALLMENT)) {
							// //System.out.println("Val is :" + val);
							if (val == null) {
								throw new NoDataException(
										"Application for Claim Second Installment is pending processing by CGTSI.");
							}
							if (val.equals(ClaimConstants.CLM_DELIMITER4)) {
								throw new NoDataException(
										"Application for Claim Second Installment has already been filed and is rejected.");
							} else if (!val.equals("")) {
								// //System.out.println("Control 5");
								// //System.out.println("ID :" + id);
								tokenizer = new StringTokenizer(val,
										ClaimConstants.CLM_DELIMITER1);
								boolean isFlagRead = false;
								boolean isCGCLANRead = false;
								boolean isSettlementDateRead = false;
								while (tokenizer.hasMoreTokens()) {
									// //System.out.println("Control 6");
									String token = (String) tokenizer
											.nextToken();
									// //System.out.println("Next TOken :" +
									// token);
									if (!isSettlementDateRead) {
										if (!isCGCLANRead) {
											if (!isFlagRead) {
												installmentFlag = token;
												isFlagRead = true;
												continue;
											}
											cgclan = token;
											isCGCLANRead = true;
											continue;
										}
										secondSettlementDate = token;
										isSettlementDateRead = true;
										continue;
									}
								}
								// //System.out.println("firstSettlementDate :" +
								// firstSettlementDate);
								if (secondSettlementDate
										.equals(ClaimConstants.CLM_NO_VALUE)) {
									throw new NoDataException(
											"Application for Second Claim Installment has already been filed and approved and is pending for Settlement by CGTSI.");
								} else {
									throw new NoDataException(
											"Application for Second Claim Installment has been filed, approved and settled by CGTSI.");
								}
							}
						}

					}
				} else {
					throw new NoDataException(
							"Please file Application for First Claim Installment before filing for Second Claim Installment.");
				}
			} else {
				throw new NoDataException(
						"Please file Application for First Claim Installment before filing for Second Claim Installment.");
			}
			Log.log(Log.INFO, "ClaimAction", "forwardToNextPage",
					"Printing Member Id :" + memberId);
			Log.log(Log.INFO, "ClaimAction", "forwardToNextPage",
					"Printing Borrower Id :" + borrowerId);

			// Redirecting the User to Recovery Filter page
			claimForm.setMemberId(memberId);
			claimForm.setBorrowerID(borrowerId);
			claimForm.setRecoveryFlag(null);
			Log.log(Log.INFO, "ClaimAction", "forwardToNextPage",
					"Proceeding to Recovery Filter Page ......");
			claimForm.setRecoveryFlag(ClaimConstants.DISBRSMNT_NO_FLAG);
			claimForm.resetTheSecondClaimApplication(mapping, request);
			return mapping.findForward("claimpage");
		}
		if (((String) session.getAttribute("mainMenu")).equals(MenuOptions
				.getMenu(MenuOptions.CP_OTS))) {
			ClaimActionForm claimForm = (ClaimActionForm) form;

			ClaimsProcessor processor = new ClaimsProcessor();

			// Retrieving the memberId
			String memberId = ((claimForm.getMemberId()).toUpperCase()).trim();

			// Retrieving the borrowerId
			String borrowerId = ((claimForm.getBorrowerID()).toUpperCase())
					.trim();

			// Retrieving the CGPAN
			String inputcgpan = ((claimForm.getCgpan()).toUpperCase()).trim();

			// Validating MemberId, BorrowerId and CGPAN
			claimForm = validateMemIdBidCgpan(memberId, borrowerId, inputcgpan,
					claimForm);

			// Updating the properties of claim form
			memberId = claimForm.getMemberId();
			borrowerId = ((claimForm.getBorrowerID()).toUpperCase()).trim();
			inputcgpan = ((claimForm.getCgpan()).toUpperCase()).trim();

			Vector otsdetails = processor.getOTSDetails(borrowerId);
			for (int i = 0; i < otsdetails.size(); i++) {
				HashMap map = (HashMap) otsdetails.elementAt(i);
				if (map != null) {
					String willfuldefaulter = (String) map
							.get(ClaimConstants.CLM_OTS_WILLFUL_DEFAULTER);
					// //System.out.println("Willful Defaulter :" +
					// willfuldefaulter);
					if (willfuldefaulter != null) {
						throw new ApplicationAlreadyFiledException(
								"OTS Details already exist for the Borrower");
					}
				}
			}
			Vector otsReqDtls = processor.getOTSRequestDetails(borrowerId);

			claimForm.setOtsRequestDtls(otsReqDtls);
			claimForm.setWilfullDefaulter(ClaimConstants.DISBRSMNT_NO_FLAG);
			/*
			 * Clearing up the Collection Objects
			 */
			// otsdetails.clear();
			otsdetails = null;
			// otsReqDtls.clear();
			otsReqDtls = null;
			claimForm.resetOTSRequestPage(mapping, request);
			return mapping.findForward("otsdetailspage");
		}
		if ((((String) session.getAttribute("mainMenu")).equals(MenuOptions
				.getMenu(MenuOptions.CP_EXPORT_CLAIM_FILE)))
				&& ((session.getAttribute("subMenuItem"))
						.equals(MenuOptions
								.getMenu(MenuOptions.CP_EXPORT_CLAIM_FILE_FIRST_INSTALLMNT)))) {
			ClaimActionForm claimForm = (ClaimActionForm) form;
			ClaimsProcessor processor = new ClaimsProcessor();
			StringTokenizer tokenizer = null;

			// Retrieving the memberId
			String memberId = ((claimForm.getMemberId()).toUpperCase()).trim();

			// Retrieving the borrowerId
			String borrowerId = ((claimForm.getBorrowerID()).toUpperCase())
					.trim();

			// Retrieving the CGPAN
			String inputcgpan = ((claimForm.getCgpan()).toUpperCase()).trim();

			// Validating MemberId, BorrowerId and CGPAN
			claimForm = validateMemIdBidCgpan(memberId, borrowerId, inputcgpan,
					claimForm);

			// Updating the properties of claim form
			memberId = claimForm.getMemberId();
			borrowerId = ((claimForm.getBorrowerID()).toUpperCase()).trim();
			inputcgpan = ((claimForm.getCgpan()).toUpperCase()).trim();
			String bankId = memberId.substring(0, 4);
			String zoneId = memberId.substring(4, 8);
			String branchId = memberId.substring(8, 12);

			HashMap whichClmApplicationsHasUserFiled = processor
					.getClmRefAndFlagDtls(bankId, zoneId, branchId, borrowerId);
			// //System.out.println("Printing the hashmap...:" +
			// whichClmApplicationsHasUserFiled);
			if (whichClmApplicationsHasUserFiled != null) {
				// //System.out.println("Control 1");
				if (whichClmApplicationsHasUserFiled.size() > 0) {
					// //System.out.println("Control 2");
					java.util.Set valuesSet = whichClmApplicationsHasUserFiled
							.keySet();
					java.util.Iterator valuesIterator = valuesSet.iterator();
					while (valuesIterator.hasNext()) {
						// //System.out.println("Control 3");
						String installmentFlag = null;
						String cgclan = null;
						String firstSettlementDate = null;
						String id = (String) valuesIterator.next();

						String val = (String) whichClmApplicationsHasUserFiled
								.get(id);

						if (id.equals(ClaimConstants.FIRST_INSTALLMENT)) {
							// //System.out.println("Control 4");
							if (val == null) {
								throw new NoDataException(
										"Application for Claim First Installment has been filed and is pending Processing by CGTSI.");
							}
							if (val.equals(ClaimConstants.CLM_DELIMITER4)) {
								throw new NoDataException(
										"Application for Claim First Installment has already been filed and is rejected.");
							}
							if (val.equals(ClaimConstants.CLM_DELIMITER5)) {
								throw new NoDataException(
										"Claim Application has been completely settled.");
							} else if (!val.equals("")) {
								// //System.out.println("Control 5");
								// //System.out.println("ID :" + id);
								tokenizer = new StringTokenizer(val,
										ClaimConstants.CLM_DELIMITER1);
								// boolean isStatusRead = false;
								boolean isFlagRead = false;
								boolean isCGCLANRead = false;
								boolean isSettlementDateRead = false;
								while (tokenizer.hasMoreTokens()) {
									// //System.out.println("Control 6");
									String token = (String) tokenizer
											.nextToken();
									// //System.out.println("Next TOken :" +
									// token);
									if (!isSettlementDateRead) {
										if (!isCGCLANRead) {
											if (!isFlagRead) {
												installmentFlag = token;
												isFlagRead = true;
												continue;
											}
											cgclan = token;
											isCGCLANRead = true;
											continue;
										}
										firstSettlementDate = token;
										isSettlementDateRead = true;
										continue;
									}
								}
								// //System.out.println("firstSettlementDate :" +
								// firstSettlementDate);
								if (firstSettlementDate
										.equals(ClaimConstants.CLM_NO_VALUE)) {
									throw new NoDataException(
											"Application for First Claim Installment has already been filed and approved and pending for Settlement by CGTSI");
								} else {
									throw new NoDataException(
											"Application for First Claim Installment has already been Filed, Approved and Settled by CGTSI");
								}

							}
						} else if (id.equals(ClaimConstants.SECOND_INSTALLMENT)) {
							continue;
						}

					}
				}
			}

			ClaimApplication claimapplication = new ClaimApplication();
			claimapplication.setBorrowerId(borrowerId);
			// Retrieving the member details for the given member id
			MemberInfo memberinfo = processor.getMemberInfoDetails(memberId);
			claimapplication.setMemberDetails(memberinfo);
			// Retrieving the borrower details for the given borrower id
			BorrowerInfo borrowerinfo = processor
					.getBorrowerDetails(borrowerId);
			claimapplication.setBorrowerDetails(borrowerinfo);

			// Retriving the npa details for the given borrower id
			java.util.Date npaClassifiedDate = null;
			java.util.Date npaReportingDate = null;
			String reasonfornpa = null;
			String willfulDefaulter = null;

			HashMap npadetails = processor.isNPADetailsAvailable(borrowerId);
			boolean npaDtlsAvl = false;
			if (npadetails == null) {
				throw new NoDataException(
						"NPA Details are not available. Please enter through Guarantee Maintenance.");
			}
			if (npadetails.size() == 0) {
				throw new NoDataException(
						"NPA Details are not available. Please enter through Guarantee Maintenance.");
			}

			HashMap npadtlMainTable = (HashMap) npadetails
					.get(ClaimConstants.CLM_MAIN_TABLE);
			if (npadtlMainTable != null) {
				if (npadtlMainTable.size() > 0) {
					willfulDefaulter = (String) npadtlMainTable
							.get(ClaimConstants.WILLFUL_DEFAULTER);
					if (willfulDefaulter != null) {
						npaDtlsAvl = npaDtlsAvl || true;
						npaClassifiedDate = (java.util.Date) npadtlMainTable
								.get(ClaimConstants.NPA_CLASSIFIED_DT);
						// //System.out.println("npaClassifiedDate:"+npaClassifiedDate);

						npaReportingDate = (java.util.Date) npadtlMainTable
								.get(ClaimConstants.NPA_REPORTING_DT);
						// //System.out.println("npaReportingDate:"+npaReportingDate);
						reasonfornpa = (String) npadtlMainTable
								.get(ClaimConstants.REASONS_FOR_TURNING_NPA);
						// //System.out.println("reasonfornpa:"+reasonfornpa);
						willfulDefaulter = (String) npadtlMainTable
								.get(ClaimConstants.WILLFUL_DEFAULTER);
						// //System.out.println("willfulDefaulter:"+willfulDefaulter);
					}
				}
			}

			HashMap npadtltemptable = (HashMap) npadetails
					.get(ClaimConstants.CLM_TEMP_TABLE);
			// String willfulDefaulter = null;
			// boolean npaDtlsAvl = false;
			if (npadtltemptable != null) {
				if (npadtltemptable.size() > 0) {
					willfulDefaulter = (String) npadtltemptable
							.get(ClaimConstants.WILLFUL_DEFAULTER);
					if (willfulDefaulter != null) {
						npaDtlsAvl = npaDtlsAvl || true;
						npaClassifiedDate = (java.util.Date) npadtltemptable
								.get(ClaimConstants.NPA_CLASSIFIED_DT);
						npaReportingDate = (java.util.Date) npadtltemptable
								.get(ClaimConstants.NPA_REPORTING_DT);
						reasonfornpa = (String) npadtltemptable
								.get(ClaimConstants.REASONS_FOR_TURNING_NPA);
						willfulDefaulter = (String) npadtltemptable
								.get(ClaimConstants.WILLFUL_DEFAULTER);
					}
				}
			}

			if (!npaDtlsAvl) {
				Log.log(Log.INFO, "ClaimAction", "forwardToNextPage",
						"Error : npa details do not exist for the borrower.");
				// return mapping.findForward("npaDetailsPage");
				throw new NoDataException(
						"NPA Details are not available. Please enter through Guarantee Maintenance.");
			}
			// npaClassifiedDate =
			// (java.util.Date)npadtlMainTable.get(ClaimConstants.NPA_CLASSIFIED_DT);
			// npaReportingDate =
			// (java.util.Date)npadtlMainTable.get(ClaimConstants.NPA_REPORTING_DT);
			// reasonfornpa =
			// (String)npadtlMainTable.get(ClaimConstants.REASONS_FOR_TURNING_NPA);
			// willfulDefaulter =
			// (String)npadtlMainTable.get(ClaimConstants.WILLFUL_DEFAULTER);
			/*
			 * if((willfulDefaulter == null) || (willfulDefaulter.equals(""))) {
			 * throw new
			 * NoDataException("NPA Details do not exist for the Borrower :" +
			 * borrowerId); }
			 */
			claimapplication
					.setDateOnWhichAccountClassifiedNPA(npaClassifiedDate);
			claimapplication.setDateOfReportingNpaToCgtsi(npaReportingDate);
			claimapplication.setReasonsForAccountTurningNPA(reasonfornpa);

			// Retrieving legal proceeding details
			LegalProceedingsDetail legalproceedingsdetail = processor
					.isLegalProceedingsDetailAvl(borrowerId);

			if (legalproceedingsdetail == null) {
				throw new NoDataException(
						"Legal Proceeding do not exit for the Borrower :"
								+ borrowerId);
			}
			claimapplication.setLegalProceedingsDetails(legalproceedingsdetail);

			Vector cgpanDetails = processor.getCGPANDetailsForBorrowerId(
					borrowerId, memberId);

			Vector cgpans = new Vector();
			Vector tccgpans = new Vector();
			Vector wccgpans = new Vector();
			HashMap hashmap = null;
			java.util.Date currentDate = new java.util.Date();
			Administrator admin = new Administrator();
			ParameterMaster pm = (ParameterMaster) admin.getParameter();
			int periodTenureExpiryLodgementClaims = pm
					.getPeriodTenureExpiryLodgementClaims();

			for (int i = 0; i < cgpanDetails.size(); i++) {
				hashmap = (HashMap) cgpanDetails.elementAt(i);
				if (hashmap != null) {
					if (hashmap.containsKey(ClaimConstants.CLM_CGPAN)) {
						String cgpan = (String) hashmap
								.get(ClaimConstants.CLM_CGPAN);
						String cgpanStatus = (String) hashmap
								.get(ClaimConstants.APPLICATION_STATUS);
						// //System.out.println("cgpanStatus:"+cgpanStatus);
						if ((cgpan != null) && (!(cgpan.equals("")))) {
							if ((hashmap.get(ClaimConstants.CGPAN_LOAN_TYPE))
									.equals(ClaimConstants.CGPAN_TC_LOAN_TYPE)) {
								if ((cgpanStatus != null)
										&& (cgpanStatus.equalsIgnoreCase("EX"))) {
									String applicationRefNum = processor
											.getAppRefNumber(cgpan);
									TermLoan termLoanObj = processor
											.getTermLoan(applicationRefNum,
													"TC");
									int applicationTenure = termLoanObj
											.getTenure();
									// //System.out.println("applicationTenure:"+applicationTenure);
									java.util.Date guarStartDt = (java.util.Date) termLoanObj
											.getAmountSanctionedDate();
									java.util.Date expiryDate = processor
											.getDate(guarStartDt,
													applicationTenure);
									java.util.Date tenureExpiryDate = processor
											.getDate(expiryDate,
													periodTenureExpiryLodgementClaims);
									if ((currentDate
											.compareTo(tenureExpiryDate)) > 0) {
										continue;
									}
								}

								HashMap mp = new HashMap();
								java.util.Date dsbrsDt = (java.util.Date) hashmap
										.get(ClaimConstants.CLM_LAST_DISBURSEMENT_DT);

								Double repaidAmnt = (Double) hashmap
										.get(ClaimConstants.TOTAL_AMNT_REPAID);
								mp.put(ClaimConstants.CLM_CGPAN, cgpan);
								mp.put(ClaimConstants.CLM_LAST_DISBURSEMENT_DT,
										dsbrsDt);
								mp.put(ClaimConstants.TOTAL_AMNT_REPAID,
										repaidAmnt);
								if (!tccgpans.contains(mp)) {
									tccgpans.addElement(mp);
								}
							}
							if ((hashmap.get(ClaimConstants.CGPAN_LOAN_TYPE))
									.equals(ClaimConstants.CGPAN_WC_LOAN_TYPE)) {
								if ((cgpanStatus != null)
										&& (cgpanStatus.equalsIgnoreCase("EX"))) {
									HashMap workingCapitalDtl = processor
											.getWorkingCapital(cgpan);
									Integer applicationTenureObj = (Integer) workingCapitalDtl
											.get(ClaimConstants.WC_TENURE);
									if (applicationTenureObj != null) {
										int applicationTenure = applicationTenureObj
												.intValue();
										java.util.Date guarStartDt = (java.util.Date) workingCapitalDtl
												.get(ClaimConstants.CLM_GUARANTEE_START_DT);
										java.util.Date expiryDate = processor
												.getDate(guarStartDt,
														applicationTenure);
										java.util.Date tenureExpiryDate = processor
												.getDate(expiryDate,
														periodTenureExpiryLodgementClaims);
										if ((currentDate
												.compareTo(tenureExpiryDate)) > 0) {
											continue;
										}
									}
								}
								if (!wccgpans.contains(cgpan)) {
									wccgpans.addElement(cgpan);
								}
							}
							if ((hashmap.get(ClaimConstants.CGPAN_LOAN_TYPE))
									.equals(ClaimConstants.CGPAN_CC_LOAN_TYPE)) {
								if ((cgpanStatus != null)
										&& (cgpanStatus.equalsIgnoreCase("EX"))) {
									String applicationRefNum = processor
											.getAppRefNumber(cgpan);
									TermLoan termLoanObj = processor
											.getTermLoan(applicationRefNum,
													"CC");
									int applicationTenure = termLoanObj
											.getTenure();
									java.util.Date guarStartDt = (java.util.Date) termLoanObj
											.getAmountSanctionedDate();
									java.util.Date expiryDate = processor
											.getDate(guarStartDt,
													applicationTenure);
									java.util.Date tenureExpiryDate = processor
											.getDate(expiryDate,
													periodTenureExpiryLodgementClaims);
									if ((currentDate
											.compareTo(tenureExpiryDate)) > 0) {
										continue;
									}
								}

								HashMap mp = new HashMap();
								java.util.Date dsbrsDt = (java.util.Date) hashmap
										.get(ClaimConstants.CLM_LAST_DISBURSEMENT_DT);
								Double repaidAmnt = (Double) hashmap
										.get(ClaimConstants.TOTAL_AMNT_REPAID);
								// //System.out.println("repaidAmnt:"+repaidAmnt);
								mp.put(ClaimConstants.CLM_CGPAN, cgpan);
								mp.put(ClaimConstants.CLM_LAST_DISBURSEMENT_DT,
										dsbrsDt);
								mp.put(ClaimConstants.TOTAL_AMNT_REPAID,
										repaidAmnt);
								if (!tccgpans.contains(mp)) {
									tccgpans.addElement(mp);
								}
							}
							if (!cgpans.contains(cgpan)) {
								cgpans.addElement(cgpan);
							}
						}
					}
				}
			}

			Vector tlcldetails = new Vector();
			for (int i = 0; i < tccgpans.size(); i++) {
				double repaidAmt = 0.0;
				HashMap map = (HashMap) tccgpans.elementAt(i);
				if (map == null) {
					continue;
				}
				TermLoanCapitalLoanDetail tcdetail = new TermLoanCapitalLoanDetail();
				String cgpan = (String) map.get(ClaimConstants.CLM_CGPAN);
				java.util.Date dsbrsDt = (java.util.Date) map
						.get(ClaimConstants.CLM_LAST_DISBURSEMENT_DT);
				Double repaidAmnt = (Double) hashmap
						.get(ClaimConstants.TOTAL_AMNT_REPAID);
				if (repaidAmnt != null) {
					repaidAmt = repaidAmnt.doubleValue();
					// //System.out.println("Line number 1044 repaidAmt:"+repaidAmt);
				}
				tcdetail.setCgpan(cgpan);
				tcdetail.setLastDisbursementDate(dsbrsDt);
				tcdetail.setPrincipalRepayment(repaidAmt);
				if (!tlcldetails.contains(tcdetail)) {
					tlcldetails.addElement(tcdetail);
				}
			}

			// Setting the vector of TermLoanCapitalLoanDetail objects in the
			// claim application
			claimapplication.setTermCapitalDtls(tlcldetails);

			ArrayList wcdetails = new ArrayList();
			for (int i = 0; i < wccgpans.size(); i++) {
				WorkingCapitalDetail wcdetail = new WorkingCapitalDetail();
				String cgpan = (String) wccgpans.elementAt(i);
				wcdetail.setCgpan(cgpan);
				if (!wcdetails.contains(wcdetail)) {
					wcdetails.add(wcdetail);
				}
			}
			// Setting the arraylist of WorkingCapitalDetail objects in the
			// claim application
			claimapplication.setWorkingCapitalDtls(wcdetails);

			if ((tccgpans != null) && (wccgpans != null)) {
				if ((tccgpans.size() == 0) && (wccgpans.size() == 0)) {
					throw new NoDataException(
							"For this Borrower, there are no Loan Account(s) or the Loan Account(s) have been closed.");
				}
			}

			Vector recoverydetails = new Vector();
			for (int i = 0; i < cgpans.size(); i++) {
				RecoveryDetails recdetail = new RecoveryDetails();
				String cgpan = (String) cgpans.elementAt(i);
				recdetail.setCgpan(cgpan);
				if (!recoverydetails.contains(recdetail)) {
					recoverydetails.addElement(recdetail);
				}
			}
			// Setting the vector of RecoveryDetails objects in the
			// claim application
			claimapplication.setRecoveryDetails(recoverydetails);

			// Retrieving the Claim Summary Details
			ArrayList claimsummarydetails = new ArrayList();
			for (int i = 0; i < cgpanDetails.size(); i++) {
				HashMap hashmp = (HashMap) cgpanDetails.elementAt(i);
				if (hashmp != null) {
					String cgpan = (String) hashmp
							.get(ClaimConstants.CLM_CGPAN);
					double approvedamnt = ((Double) hashmp
							.get(ClaimConstants.CGPAN_APPRVD_AMNT))
							.doubleValue();
					// //System.out.println("approvedamnt:"+approvedamnt);
					ClaimSummaryDtls clmsummarydtl = new ClaimSummaryDtls();
					clmsummarydtl.setCgpan(cgpan);
					clmsummarydtl.setLimitCoveredUnderCGFSI(String
							.valueOf(approvedamnt));
					if (!claimsummarydetails.contains(clmsummarydtl)) {
						claimsummarydetails.add(clmsummarydtl);
					}
					// hashmp.clear();
					hashmp = null;
				}
			}

			// Retrieving the primary security and guarantor details.
			HashMap details = processor
					.getPrimarySecurityAndNetworthOfGuarantors(borrowerId,
							memberId);
			SecurityAndPersonalGuaranteeDtls sapg = new SecurityAndPersonalGuaranteeDtls();
			DtlsAsOnDateOfSanction asOnSnctn = new DtlsAsOnDateOfSanction();
			double totalNetworth = 0.0;
			double totalLandVal = 0.0;
			double totalMachineVal = 0.0;
			double totalBldgVal = 0.0;
			double totalOFMAVal = 0.0;
			double totalCurrAssetsVal = 0.0;
			double totalOthersVal = 0.0;

			Double totalNetWorthDouble = (Double) details
					.get(ClaimConstants.CLM_SAPGD_NETWORTH_OF_GUARANTOR);
			if (totalNetWorthDouble != null) {
				totalNetworth = totalNetWorthDouble.doubleValue();
			}
			Double totalLandValDouble = (Double) details
					.get(ClaimConstants.CLM_SAPGD_PARTICULAR_LAND);
			if (totalLandValDouble != null) {
				totalLandVal = totalLandValDouble.doubleValue();
			}
			Double totalMCValDouble = (Double) details
					.get(ClaimConstants.CLM_SAPGD_PARTICULAR_MC);
			if (totalMCValDouble != null) {
				totalMachineVal = totalMCValDouble.doubleValue();
			}
			Double totalBldgValDouble = (Double) details
					.get(ClaimConstants.CLM_SAPGD_PARTICULAR_BLDG);
			if (totalBldgValDouble != null) {
				totalBldgVal = totalBldgValDouble.doubleValue();
			}
			Double totalOFMAValDouble = (Double) details
					.get(ClaimConstants.CLM_SAPGD_PARTICULAR_OTHER_FIXED_MOV_ASSETS);
			if (totalOFMAValDouble != null) {
				totalOFMAVal = totalOFMAValDouble.doubleValue();
			}
			Double totalCurrAssetsDouble = (Double) details
					.get(ClaimConstants.CLM_SAPGD_PARTICULAR_CUR_ASSETS);
			if (totalCurrAssetsDouble != null) {
				totalCurrAssetsVal = totalCurrAssetsDouble.doubleValue();
			}
			Double totalOthersValDouble = (Double) details
					.get(ClaimConstants.CLM_SAPGD_PARTICULAR_OTHERS);
			if (totalOthersValDouble != null) {
				totalOthersVal = totalOthersValDouble.doubleValue();
			}
			asOnSnctn.setNetworthOfGuarantors(totalNetworth);
			asOnSnctn.setValueOfLand(totalLandVal);
			asOnSnctn.setValueOfBuilding(totalBldgVal);
			asOnSnctn.setValueOfMachine(totalOFMAVal);
			asOnSnctn.setValueOfOtherFixedMovableAssets(totalOFMAVal);
			asOnSnctn.setValueOfCurrentAssets(totalCurrAssetsVal);
			asOnSnctn.setValueOfOthers(totalOthersVal);
			sapg.setDetailsAsOnDateOfSanction(asOnSnctn);
			claimapplication.setSecurityAndPersonalGuaranteeDtls(sapg);

			// Setting the ArrayList of ClaimSummaryDtls in the claim
			// application
			claimapplication.setClaimSummaryDtls(claimsummarydetails);

			// Setting the Claim Installment as for First Installment
			claimapplication.setFirstInstallment(true);

			// Retrieving the User Info
			User user = getUserInformation(request);
			String userid = user.getUserId();

			// Retrieving the present time in yyyy-mm-dd format
			String currentTime = (new java.sql.Date(System.currentTimeMillis()))
					.toString();

			// Storing the claimapplication and the borrower id in a hashtable
			Hashtable claimDetails = new Hashtable();
			claimDetails.put(borrowerId, claimapplication);
			// claimDetails.put(claimapplication.getBorrowerId(),
			// claimapplication);
			boolean exportObjectStatus = false;
			try {
				String contextPath1 = request.getSession(false)
						.getServletContext().getRealPath("");
				String contextPath = PropertyLoader
						.changeToOSpath(contextPath1);

				FileOutputStream fileOutputStream = null;
				;
				ObjectOutputStream objectOutputStream = null;
				String filename = contextPath + File.separator
						+ Constants.FILE_DOWNLOAD_DIRECTORY + File.separator
						+ userid + currentTime + ClaimConstants.CLM_TAG
						+ ClaimConstants.FIRST_INSTALLMENT
						+ ClaimConstants.CLM_EXTENSION;

				String formattedToOSPath = request.getContextPath()
						+ File.separator + Constants.FILE_DOWNLOAD_DIRECTORY
						+ File.separator + userid + currentTime
						+ ClaimConstants.CLM_TAG
						+ ClaimConstants.FIRST_INSTALLMENT
						+ ClaimConstants.CLM_EXTENSION;

				session.setAttribute("fileName", formattedToOSPath);
				File exportFlatFile = new File(filename);
				if (!exportFlatFile.exists()) {
					exportFlatFile.createNewFile();
				}
				fileOutputStream = new FileOutputStream(exportFlatFile);
				objectOutputStream = new ObjectOutputStream(fileOutputStream);
				objectOutputStream.writeObject(claimDetails);
				objectOutputStream.close();
				fileOutputStream.close();
				exportObjectStatus = true;
			} catch (IOException ioexception) {
				exportObjectStatus = false;
				Log.log(Log.ERROR, "ClaimAction", "Export Claim File",
						"Export failed because " + ioexception.getMessage());
				throw new ExportFailedException("Export Failed.");
			}

			/*
			 * Cleaning up the Collection Objects
			 */
			// wcdetails.clear();
			wcdetails = null;
			// claimsummarydetails.clear();
			claimsummarydetails = null;
			// claimDetails.clear();
			claimDetails = null;
			// npadetails.clear();
			npadetails = null;
			// hashmap.clear();
			hashmap = null;
			// cgpanDetails.clear();
			cgpanDetails = null;
			// cgpans.clear();
			cgpans = null;
			// tccgpans.clear();
			tccgpans = null;
			// wccgpans.clear();
			wccgpans = null;
			return mapping.findForward("exportClaimFile");
		}
		if ((((String) session.getAttribute("mainMenu")).equals(MenuOptions
				.getMenu(MenuOptions.CP_EXPORT_CLAIM_FILE)))
				&& ((session.getAttribute("subMenuItem"))
						.equals(MenuOptions
								.getMenu(MenuOptions.CP_EXPORT_CLAIM_FILE_SECOND_INSTALLMNT)))) {
			ClaimActionForm claimForm = (ClaimActionForm) form;
			ClaimsProcessor processor = new ClaimsProcessor();

			// Retrieving the memberId
			String memberId = ((claimForm.getMemberId()).toUpperCase()).trim();

			// Retrieving the borrowerId
			String borrowerId = ((claimForm.getBorrowerID()).toUpperCase())
					.trim();

			// Retrieving the CGPAN
			String inputcgpan = ((claimForm.getCgpan()).toUpperCase()).trim();

			// Validating MemberId, BorrowerId and CGPAN
			claimForm = validateMemIdBidCgpan(memberId, borrowerId, inputcgpan,
					claimForm);

			// Updating the properties of claim form
			memberId = claimForm.getMemberId();
			borrowerId = ((claimForm.getBorrowerID()).toUpperCase()).trim();
			inputcgpan = ((claimForm.getCgpan()).toUpperCase()).trim();

			String bankId = memberId.substring(0, 4);
			String zoneId = memberId.substring(4, 8);
			String branchId = memberId.substring(8, 12);

			HashMap whichClmApplicationsHasUserFiled = processor
					.getClmRefAndFlagDtls(bankId, zoneId, branchId, borrowerId);
			// //System.out.println("Printing the hashmap...:" +
			// whichClmApplicationsHasUserFiled);
			if (whichClmApplicationsHasUserFiled != null) {
				// //System.out.println("Control 1");
				if (whichClmApplicationsHasUserFiled.size() > 0) {
					// //System.out.println("Control 2");
					StringTokenizer tokenizer = null;
					java.util.Set valuesSet = whichClmApplicationsHasUserFiled
							.keySet();
					java.util.Iterator valuesIterator = valuesSet.iterator();
					while (valuesIterator.hasNext()) {
						// //System.out.println("Control 3");
						String installmentFlag = null;
						String cgclan = null;
						String firstSettlementDate = null;
						String secondSettlementDate = null;
						String id = (String) valuesIterator.next();

						String val = (String) whichClmApplicationsHasUserFiled
								.get(id);

						if (id.equals(ClaimConstants.FIRST_INSTALLMENT)) {
							// //System.out.println("Control 4");
							if (val == null) {
								throw new NoDataException(
										"Application for Claim First Installment is pending for Approval. Please file Application for Second Claim Installment after the Approval and Settlement of Application of Claim First Installment.");
							}
							if (val.equals(ClaimConstants.CLM_DELIMITER4)) {
								throw new NoDataException(
										"Application for Claim First Installment has already been filed and is rejected. You cannot file application for Claim Second Installment.");
							}
							if (val.equals("")) {
								throw new NoDataException(
										"Please file Application for Claim First Installment before filing for Claim Second Installment.");
							}
							if (val.equals(ClaimConstants.CLM_DELIMITER5)) {
								throw new NoDataException(
										"Claim Application has been completely settled.");
							} else {
								// //System.out.println("Control 5");
								// //System.out.println("ID :" + id);
								tokenizer = new StringTokenizer(val,
										ClaimConstants.CLM_DELIMITER1);
								boolean isFlagRead = false;
								boolean isCGCLANRead = false;
								boolean isSettlementDateRead = false;
								while (tokenizer.hasMoreTokens()) {
									// //System.out.println("Control 6");
									String token = (String) tokenizer
											.nextToken();
									// //System.out.println("Next TOken :" +
									// token);
									if (!isSettlementDateRead) {
										if (!isCGCLANRead) {
											if (!isFlagRead) {
												installmentFlag = token;
												isFlagRead = true;
												continue;
											}
											cgclan = token;
											isCGCLANRead = true;
											continue;
										}
										firstSettlementDate = token;
										isSettlementDateRead = true;
										continue;
									}
								}
								// //System.out.println("firstSettlementDate :" +
								// firstSettlementDate);
								if (firstSettlementDate
										.equals(ClaimConstants.CLM_NO_VALUE)) {
									throw new NoDataException(
											"Application for First Claim Installment has already been filed and approved and pending for Settlement by CGTSI. Please file Application for Second Claim Installment after the Settlement of First Claim Installment.");
								}
							}
						} else if (id.equals(ClaimConstants.SECOND_INSTALLMENT)) {
							// //System.out.println("Val is :" + val);
							if (val == null) {
								throw new NoDataException(
										"Application for Claim Second Installment is pending processing by CGTSI.");
							}
							if (val.equals(ClaimConstants.CLM_DELIMITER4)) {
								throw new NoDataException(
										"Application for Claim Second Installment has already been filed and is rejected.");
							} else if (!val.equals("")) {
								// //System.out.println("Control 5");
								// //System.out.println("ID :" + id);
								tokenizer = new StringTokenizer(val,
										ClaimConstants.CLM_DELIMITER1);
								boolean isFlagRead = false;
								boolean isCGCLANRead = false;
								boolean isSettlementDateRead = false;
								while (tokenizer.hasMoreTokens()) {
									// //System.out.println("Control 6");
									String token = (String) tokenizer
											.nextToken();
									// //System.out.println("Next TOken :" +
									// token);
									if (!isSettlementDateRead) {
										if (!isCGCLANRead) {
											if (!isFlagRead) {
												installmentFlag = token;
												isFlagRead = true;
												continue;
											}
											cgclan = token;
											isCGCLANRead = true;
											continue;
										}
										secondSettlementDate = token;
										isSettlementDateRead = true;
										continue;
									}
								}
								// //System.out.println("firstSettlementDate :" +
								// firstSettlementDate);
								if (secondSettlementDate
										.equals(ClaimConstants.CLM_NO_VALUE)) {
									throw new NoDataException(
											"Application for Second Claim Installment has already been filed and approved and is pending for Settlement by CGTSI.");
								} else {
									throw new NoDataException(
											"Application for Second Claim Installment has been filed, approved and settled by CGTSI.");
								}
							}
						}

					}
				} else {
					throw new NoDataException(
							"Please file Application for First Claim Installment before filing for Second Claim Installment.");
				}
			} else {
				throw new NoDataException(
						"Please file Application for First Claim Installment before filing for Second Claim Installment.");
			}

			ClaimApplication claimapplication = new ClaimApplication();

			HashMap firstClmDtl = processor.getFirstClmDtlForBid(bankId,
					zoneId, branchId, borrowerId);
			java.util.Date settDate = null;
			String claimRefNumber = null;
			String cgclan = null;
			if (firstClmDtl
					.containsKey(ClaimConstants.CLM_SETTLMNT_FIRST_SETTLMNT_DT)) {
				settDate = (java.util.Date) firstClmDtl
						.get(ClaimConstants.CLM_SETTLMNT_FIRST_SETTLMNT_DT);
				claimapplication.setClaimSettlementDate(settDate);
			}
			if (firstClmDtl.containsKey(ClaimConstants.CLM_CLAIM_REF_NUMBER)) {
				claimRefNumber = (String) firstClmDtl
						.get(ClaimConstants.CLM_CLAIM_REF_NUMBER);
				claimapplication.setClaimRefNumber(claimRefNumber);
			}
			if (firstClmDtl.containsKey(ClaimConstants.CLM_CGCLAN)) {
				cgclan = (String) firstClmDtl.get(ClaimConstants.CLM_CGCLAN);
				claimapplication.setClpan(cgclan);
			}

			java.util.Date recallNoticeDt = null;
			if (firstClmDtl.containsKey(ClaimConstants.CLM_RECALL_NOTICE)) {
				recallNoticeDt = (java.util.Date) firstClmDtl
						.get(ClaimConstants.CLM_RECALL_NOTICE);
				claimapplication.setDateOfIssueOfRecallNotice(recallNoticeDt);
			}
			claimapplication.setBorrowerId(borrowerId);
			// Retrieving the member details for the given member id
			MemberInfo memberinfo = processor.getMemberInfoDetails(memberId);
			claimapplication.setMemberDetails(memberinfo);
			// Retrieving the borrower details for the given borrower id
			BorrowerInfo borrowerinfo = processor
					.getBorrowerDetails(borrowerId);
			claimapplication.setBorrowerDetails(borrowerinfo);

			// Retriving the npa details for the given borrower id
			java.util.Date npaClassifiedDate = null;
			java.util.Date npaReportingDate = null;
			String reasonfornpa = null;
			String willfulDefaulter = null;

			HashMap npadetails = processor.isNPADetailsAvailable(borrowerId);
			boolean npaDtlsAvl = false;
			if (npadetails == null) {
				throw new NoDataException(
						"NPA Details are not available. Please enter through Guarantee Maintenance.");
			}
			if (npadetails.size() == 0) {
				throw new NoDataException(
						"NPA Details are not available. Please enter through Guarantee Maintenance.");
			}

			HashMap npadtlMainTable = (HashMap) npadetails
					.get(ClaimConstants.CLM_MAIN_TABLE);
			if (npadtlMainTable != null) {
				if (npadtlMainTable.size() > 0) {
					willfulDefaulter = (String) npadtlMainTable
							.get(ClaimConstants.WILLFUL_DEFAULTER);
					if (willfulDefaulter != null) {
						npaDtlsAvl = npaDtlsAvl || true;
						npaClassifiedDate = (java.util.Date) npadtlMainTable
								.get(ClaimConstants.NPA_CLASSIFIED_DT);
						npaReportingDate = (java.util.Date) npadtlMainTable
								.get(ClaimConstants.NPA_REPORTING_DT);
						reasonfornpa = (String) npadtlMainTable
								.get(ClaimConstants.REASONS_FOR_TURNING_NPA);
						willfulDefaulter = (String) npadtlMainTable
								.get(ClaimConstants.WILLFUL_DEFAULTER);
					}
				}
			}

			HashMap npadtltemptable = (HashMap) npadetails
					.get(ClaimConstants.CLM_TEMP_TABLE);
			// String willfulDefaulter = null;
			// boolean npaDtlsAvl = false;
			if (npadtltemptable != null) {
				if (npadtltemptable.size() > 0) {
					willfulDefaulter = (String) npadtltemptable
							.get(ClaimConstants.WILLFUL_DEFAULTER);
					if (willfulDefaulter != null) {
						npaDtlsAvl = npaDtlsAvl || true;
						npaClassifiedDate = (java.util.Date) npadtltemptable
								.get(ClaimConstants.NPA_CLASSIFIED_DT);
						npaReportingDate = (java.util.Date) npadtltemptable
								.get(ClaimConstants.NPA_REPORTING_DT);
						reasonfornpa = (String) npadtltemptable
								.get(ClaimConstants.REASONS_FOR_TURNING_NPA);
						willfulDefaulter = (String) npadtltemptable
								.get(ClaimConstants.WILLFUL_DEFAULTER);
					}
				}
			}

			if (!npaDtlsAvl) {
				Log.log(Log.INFO, "ClaimAction", "forwardToNextPage",
						"Error : npa details do not exist for the borrower.");
				// return mapping.findForward("npaDetailsPage");
				throw new NoDataException(
						"NPA Details are not available. Please enter through Guarantee Maintenance.");
			}
			// java.util.Date npaClassifiedDate =
			// (java.util.Date)npadtlMainTable.get(ClaimConstants.NPA_CLASSIFIED_DT);
			// java.util.Date npaReportingDate =
			// (java.util.Date)npadtlMainTable.get(ClaimConstants.NPA_REPORTING_DT);
			// String reasonfornpa =
			// (String)npadtlMainTable.get(ClaimConstants.REASONS_FOR_TURNING_NPA);
			// willfulDefaulter =
			// (String)npadtlMainTable.get(ClaimConstants.WILLFUL_DEFAULTER);

			claimapplication
					.setDateOnWhichAccountClassifiedNPA(npaClassifiedDate);
			claimapplication.setDateOfReportingNpaToCgtsi(npaReportingDate);
			claimapplication.setReasonsForAccountTurningNPA(reasonfornpa);

			// Retrieving legal proceeding details
			LegalProceedingsDetail legalproceedingsdetail = processor
					.isLegalProceedingsDetailAvl(borrowerId);
			if (legalproceedingsdetail == null) {
				throw new NoDataException(
						"Legal Proceeding do not exit for the Borrower :"
								+ borrowerId);
			}
			claimapplication.setLegalProceedingsDetails(legalproceedingsdetail);

			Vector cgpanDetails = processor.getCGPANDetailsForBorrowerId(
					borrowerId, memberId);

			ArrayList cgpansForClmRefNum = processor
					.getAllCgpansForClmRefNum(claimRefNumber);

			Vector cgpans = new Vector();
			Vector tccgpans = new Vector();
			Vector wccgpans = new Vector();
			HashMap hashmap = null;
			for (int i = 0; i < cgpanDetails.size(); i++) {
				hashmap = (HashMap) cgpanDetails.elementAt(i);
				if (hashmap != null) {
					if (hashmap.containsKey(ClaimConstants.CLM_CGPAN)) {
						String cgpan = (String) hashmap
								.get(ClaimConstants.CLM_CGPAN);
						if ((cgpan != null) && (!(cgpan.equals("")))) {
							if (!cgpansForClmRefNum.contains(cgpan)) {
								continue;
							}

							if ((hashmap.get(ClaimConstants.CGPAN_LOAN_TYPE))
									.equals(ClaimConstants.CGPAN_TC_LOAN_TYPE)) {
								HashMap mp = new HashMap();
								java.util.Date dsbrsDt = (java.util.Date) hashmap
										.get(ClaimConstants.CLM_LAST_DISBURSEMENT_DT);
								Double repaidAmnt = (Double) hashmap
										.get(ClaimConstants.TOTAL_AMNT_REPAID);
								mp.put(ClaimConstants.CLM_CGPAN, cgpan);
								mp.put(ClaimConstants.CLM_LAST_DISBURSEMENT_DT,
										dsbrsDt);
								mp.put(ClaimConstants.TOTAL_AMNT_REPAID,
										repaidAmnt);
								if (!tccgpans.contains(mp)) {
									tccgpans.addElement(mp);
								}
							}
							if ((hashmap.get(ClaimConstants.CGPAN_LOAN_TYPE))
									.equals(ClaimConstants.CGPAN_WC_LOAN_TYPE)) {
								if (!wccgpans.contains(cgpan)) {
									wccgpans.addElement(cgpan);
								}
							}
							if ((hashmap.get(ClaimConstants.CGPAN_LOAN_TYPE))
									.equals(ClaimConstants.CGPAN_CC_LOAN_TYPE)) {
								HashMap mp = new HashMap();
								java.util.Date dsbrsDt = (java.util.Date) hashmap
										.get(ClaimConstants.CLM_LAST_DISBURSEMENT_DT);
								Double repaidAmnt = (Double) hashmap
										.get(ClaimConstants.TOTAL_AMNT_REPAID);
								mp.put(ClaimConstants.CLM_CGPAN, cgpan);
								mp.put(ClaimConstants.CLM_LAST_DISBURSEMENT_DT,
										dsbrsDt);
								mp.put(ClaimConstants.TOTAL_AMNT_REPAID,
										repaidAmnt);
								if (!tccgpans.contains(mp)) {
									tccgpans.addElement(mp);
								}
							}
							if (!cgpans.contains(cgpan)) {
								cgpans.addElement(cgpan);
							}
						}
					}
				}
			}
			Vector tlcldetails = new Vector();
			TermLoanCapitalLoanDetail tlclDtl = null;
			String clmRefNum = (String) firstClmDtl
					.get(ClaimConstants.CLM_CLAIM_REF_NUMBER);
			ClaimDetail clmdtl = processor
					.getDetailsForClaimRefNumber(clmRefNum);
			Vector tcVec = clmdtl.getTcDetails();
			for (int i = 0; i < tccgpans.size(); i++) {
				HashMap outMap = (HashMap) tccgpans.elementAt(i);
				if (outMap == null) {
					continue;
				}
				String outCgpan = (String) outMap.get(ClaimConstants.CLM_CGPAN);
				for (int j = 0; j < tcVec.size(); j++) {
					HashMap innerMap = (HashMap) tcVec.elementAt(j);
					if (innerMap == null) {
						continue;
					}
					String innerCgpan = (String) innerMap
							.get(ClaimConstants.CLM_CGPAN);
					if (innerCgpan == null) {
						continue;
					}
					if (!innerCgpan.equals(outCgpan)) {
						continue;
					}
					double osAsOnNPADate = ((Double) innerMap
							.get(ClaimConstants.CLM_OS_AS_ON_NPA))
							.doubleValue();
					double osAsInCivilSuit = ((Double) innerMap
							.get(ClaimConstants.CLM_OS_AS_ON_CIVIL_SUIT))
							.doubleValue();
					double osAsInFirstClmLodgement = ((Double) innerMap
							.get(ClaimConstants.CLM_OS_AS_IN_CLM_LODGMNT))
							.doubleValue();

					outMap.put(ClaimConstants.CLM_OS_AS_ON_NPA, new Double(
							osAsOnNPADate));
					outMap.put(ClaimConstants.CLM_OS_AS_ON_CIVIL_SUIT,
							new Double(osAsInCivilSuit));
					outMap.put(ClaimConstants.CLM_OS_AS_IN_CLM_LODGMNT,
							new Double(osAsInFirstClmLodgement));
				}
				String cgpan = (String) outMap.get(ClaimConstants.CLM_CGPAN);
				java.util.Date dsbrsDt = (java.util.Date) outMap
						.get(ClaimConstants.CLM_LAST_DISBURSEMENT_DT);
				Double obj = (Double) outMap
						.get(ClaimConstants.TOTAL_AMNT_REPAID);
				double repaidAmnt = 0.0;
				if (obj != null) {
					repaidAmnt = obj.doubleValue();
				}
				double osAsOnNPADate = ((Double) outMap
						.get(ClaimConstants.CLM_OS_AS_ON_NPA)).doubleValue();
				double osAsInCivilSuit = ((Double) outMap
						.get(ClaimConstants.CLM_OS_AS_ON_CIVIL_SUIT))
						.doubleValue();
				double osAsInFirstClmLodgement = ((Double) outMap
						.get(ClaimConstants.CLM_OS_AS_IN_CLM_LODGMNT))
						.doubleValue();
				tlclDtl = new TermLoanCapitalLoanDetail();
				tlclDtl.setCgpan(cgpan);
				tlclDtl.setLastDisbursementDate(dsbrsDt);
				tlclDtl.setPrincipalRepayment(repaidAmnt);
				tlclDtl.setOutstandingAsOnDateOfNPA(osAsOnNPADate);
				tlclDtl.setOutstandingStatedInCivilSuit(osAsInCivilSuit);
				tlclDtl.setOutstandingAsOnDateOfLodgement(osAsInFirstClmLodgement);
				if (!tlcldetails.contains(tlclDtl)) {
					tlcldetails.addElement(tlclDtl);
				}
			}
			Vector wcVec = clmdtl.getWcDetails();
			ArrayList workingCapitalDtls = new ArrayList();
			WorkingCapitalDetail wcDetail = null;
			String outerCgpan = null;
			for (int i = 0; i < wccgpans.size(); i++) {
				outerCgpan = (String) wccgpans.elementAt(i);
				if (outerCgpan == null) {
					continue;
				}
				for (int j = 0; j < wcVec.size(); j++) {
					HashMap innerMap = (HashMap) wcVec.elementAt(j);
					if (innerMap == null) {
						continue;
					}
					String innerCgpan = (String) innerMap
							.get(ClaimConstants.CLM_CGPAN);
					if (innerCgpan == null) {
						continue;
					}
					if (!innerCgpan.equals(outerCgpan)) {
						continue;
					}
					wcDetail = new WorkingCapitalDetail();
					String wccgpan = (String) innerMap
							.get(ClaimConstants.CLM_CGPAN);
					double osAsOnNPADate = ((Double) innerMap
							.get(ClaimConstants.CLM_OS_AS_ON_NPA))
							.doubleValue();
					double osAsInCivilSuit = ((Double) innerMap
							.get(ClaimConstants.CLM_OS_AS_ON_CIVIL_SUIT))
							.doubleValue();
					double osAsInFirstClmLodgement = ((Double) innerMap
							.get(ClaimConstants.CLM_OS_AS_IN_CLM_LODGMNT))
							.doubleValue();
					wcDetail.setCgpan(wccgpan);
					wcDetail.setOutstandingAsOnDateOfNPA(osAsOnNPADate);
					wcDetail.setOutstandingStatedInCivilSuit(osAsInCivilSuit);
					wcDetail.setOutstandingAsOnDateOfLodgement(osAsInFirstClmLodgement);
					if (!workingCapitalDtls.contains(wcDetail)) {
						workingCapitalDtls.add(wcDetail);
					}
				}
			}

			/*
			 * Vector tlcldetails = new Vector(); for(int i=0;
			 * i<tccgpans.size(); i++) { double repaidAmt = 0.0; HashMap map =
			 * (HashMap)tccgpans.elementAt(i); if(map == null) { continue; }
			 * TermLoanCapitalLoanDetail tcdetail = new
			 * TermLoanCapitalLoanDetail(); String cgpan =
			 * (String)map.get(ClaimConstants.CLM_CGPAN); java.util.Date dsbrsDt
			 * =
			 * (java.util.Date)map.get(ClaimConstants.CLM_LAST_DISBURSEMENT_DT);
			 * Double repaidAmnt =
			 * (Double)hashmap.get(ClaimConstants.TOTAL_AMNT_REPAID);
			 * if(repaidAmnt != null) { repaidAmt = repaidAmnt.doubleValue(); }
			 * tcdetail.setCgpan(cgpan);
			 * tcdetail.setLastDisbursementDate(dsbrsDt);
			 * tcdetail.setPrincipalRepayment(repaidAmt);
			 * if(!tlcldetails.contains(tcdetail)) {
			 * tlcldetails.addElement(tcdetail); } }
			 */

			// Setting the vector of TermLoanCapitalLoanDetail objects in the
			// claim application
			claimapplication.setTermCapitalDtls(tlcldetails);

			/*
			 * ArrayList wcdetails = new ArrayList(); for(int i=0
			 * ;i<wccgpans.size(); i++) { WorkingCapitalDetail wcdetail = new
			 * WorkingCapitalDetail(); String cgpan =
			 * (String)wccgpans.elementAt(i); wcdetail.setCgpan(cgpan);
			 * if(!wcdetails.contains(wcdetail)) { wcdetails.add(wcdetail); } }
			 */

			// Setting the arraylist of WorkingCapitalDetail objects in the
			// claim application
			claimapplication.setWorkingCapitalDtls(workingCapitalDtls);

			if ((tccgpans != null) && (wccgpans != null)) {
				if ((tccgpans.size() == 0) && (wccgpans.size() == 0)) {
					throw new NoDataException(
							"For this Borrower, there are no Loan Account(s) or the Loan Account(s) have been closed.");
				}
			}

			Vector recoverydetails = new Vector();
			for (int i = 0; i < cgpans.size(); i++) {
				RecoveryDetails recdetail = new RecoveryDetails();
				String cgpan = (String) cgpans.elementAt(i);
				recdetail.setCgpan(cgpan);
				if (!recoverydetails.contains(recdetail)) {
					recoverydetails.addElement(recdetail);
				}
			}
			// Setting the vector of RecoveryDetails objects in the
			// claim application
			claimapplication.setRecoveryDetails(recoverydetails);

			// /////////////////////////////////////////////////////////////////////////////////
			Vector cgpnDetails = processor.getCGPANDetailsForBorrowerId(
					borrowerId, memberId);
			Vector clmAppliedAmnts = processor.getClaimAppliedAmounts(
					borrowerId, ClaimConstants.FIRST_INSTALLMENT);
			Vector updateClmDtls = new Vector();
			String thiscgpn = null;
			for (int i = 0; i < cgpnDetails.size(); i++) {
				HashMap dtl = (HashMap) cgpnDetails.elementAt(i);
				if (dtl != null) {
					thiscgpn = (String) dtl.get(ClaimConstants.CLM_CGPAN);
					if (thiscgpn != null) {
						for (int j = 0; j < clmAppliedAmnts.size(); j++) {
							HashMap clmAppliedDtl = (HashMap) clmAppliedAmnts
									.elementAt(j);
							String cgpnInAppliedAmntsVec = null;
							if (clmAppliedDtl != null) {
								cgpnInAppliedAmntsVec = (String) clmAppliedDtl
										.get(ClaimConstants.CLM_CGPAN);
								if (cgpnInAppliedAmntsVec != null) {
									if (cgpnInAppliedAmntsVec.equals(thiscgpn)) {
										double clmAppliedAmnt = 0.0;
										Double clmAppAmntObj = (Double) clmAppliedDtl
												.get(ClaimConstants.CGPAN_CLM_APPLIED_AMNT);
										if (clmAppAmntObj != null) {
											clmAppliedAmnt = clmAppAmntObj
													.doubleValue();
										} else {
											clmAppliedAmnt = 0.0;
										}

										// Setting the Claim Applied Amount
										dtl.put(ClaimConstants.CGPAN_CLM_APPLIED_AMNT,
												new Double(clmAppliedAmnt));
										if (!updateClmDtls.contains(dtl)) {
											updateClmDtls.addElement(dtl);
										}

										// Clearing up the HashMap
										// clmAppliedDtl.clear();
										clmAppliedDtl = null;
										break;
									} else {
										continue;
									}
								} else {
									continue;
								}
							} else {
								continue;
							}
						}
					} else {
						continue;
					}
				} else {
					continue;
				}
				// dtl.clear();
				dtl = null;
			}

			/*
			 * Getting Claim Settlement Details and setting in the form
			 */
			HashMap settlmntDetails = processor
					.getClaimSettlementDetailForBorrower(borrowerId);
			double firstSettlementAmnt = 0.0;
			Double firstSettlementAmntObj = (Double) settlmntDetails
					.get(ClaimConstants.CLM_SETTLMNT_FIRST_SETTLMNT_AMNT);
			if (firstSettlementAmntObj != null) {
				firstSettlementAmnt = firstSettlementAmntObj.doubleValue();
			}
			java.util.Date firstSettlementDt = (java.util.Date) settlmntDetails
					.get(ClaimConstants.CLM_SETTLMNT_FIRST_SETTLMNT_DT);

			HashMap dtl = null;
			Vector finalUpdatedDtls = new Vector();
			ArrayList clmSummryDtls = new ArrayList();
			ClaimSummaryDtls dtlSummary = null;
			for (int i = 0; i < updateClmDtls.size(); i++) {
				dtl = (HashMap) updateClmDtls.elementAt(i);
				if (dtl == null) {
					continue;
				}
				dtlSummary = new ClaimSummaryDtls();
				if (dtl != null) {
					dtl.put(ClaimConstants.CLM_SETTLMNT_FIRST_SETTLMNT_AMNT,
							new Double(firstSettlementAmnt));
					dtl.put(ClaimConstants.CLM_SETTLMNT_FIRST_SETTLMNT_DT,
							firstSettlementDt);
					if (!finalUpdatedDtls.contains(dtl)) {
						finalUpdatedDtls.addElement(dtl);
					}
				}
				String pan = (String) dtl.get(ClaimConstants.CLM_CGPAN);
				dtlSummary.setCgpan(pan);
				String loanType = (String) dtl
						.get(ClaimConstants.CGPAN_LOAN_TYPE);
				dtlSummary.setTypeOfFacility(loanType);
				double loanLimit = ((Double) dtl
						.get(ClaimConstants.CGPAN_APPRVD_AMNT)).doubleValue();
				dtlSummary.setLimitCoveredUnderCGFSI(String.valueOf(loanLimit));
				double clmAppliedAmnt = ((Double) dtl
						.get(ClaimConstants.CGPAN_CLM_APPLIED_AMNT))
						.doubleValue();
				dtlSummary.setAmount(clmAppliedAmnt);
				dtlSummary.setAmntSettledByCGTSI(firstSettlementAmntObj
						.doubleValue());
				dtlSummary
						.setDtOfSettlemntOfFirstInstallmentOfClm(firstSettlementDt);
				if (!clmSummryDtls.contains(dtlSummary)) {
					clmSummryDtls.add(dtlSummary);
				}

			}

			// /////////////////////////////////////////////////////////////////////////////////

			// Retrieving the Claim Summary Details
			ArrayList claimsummarydetails = new ArrayList();
			for (int i = 0; i < cgpanDetails.size(); i++) {
				HashMap hashmp = (HashMap) cgpanDetails.elementAt(i);
				if (hashmp != null) {
					String cgpan = (String) hashmp
							.get(ClaimConstants.CLM_CGPAN);
					double approvedamnt = ((Double) hashmp
							.get(ClaimConstants.CGPAN_APPRVD_AMNT))
							.doubleValue();
					ClaimSummaryDtls clmsummarydtl = new ClaimSummaryDtls();
					clmsummarydtl.setCgpan(cgpan);
					clmsummarydtl.setLimitCoveredUnderCGFSI(String
							.valueOf(approvedamnt));
					if (!claimsummarydetails.contains(clmsummarydtl)) {
						claimsummarydetails.add(clmsummarydtl);
					}
					// hashmp.clear();
					hashmp = null;
				}
			}

			// Retrieving the primary security and guarantor details.
			HashMap details = processor
					.getPrimarySecurityAndNetworthOfGuarantors(borrowerId,
							memberId);
			SecurityAndPersonalGuaranteeDtls sapg = new SecurityAndPersonalGuaranteeDtls();
			DtlsAsOnDateOfSanction asOnSnctn = new DtlsAsOnDateOfSanction();
			double totalNetworth = 0.0;
			double totalLandVal = 0.0;
			double totalMachineVal = 0.0;
			double totalBldgVal = 0.0;
			double totalOFMAVal = 0.0;
			double totalCurrAssetsVal = 0.0;
			double totalOthersVal = 0.0;

			Double totalNetWorthDouble = (Double) details
					.get(ClaimConstants.CLM_SAPGD_NETWORTH_OF_GUARANTOR);
			if (totalNetWorthDouble != null) {
				totalNetworth = totalNetWorthDouble.doubleValue();
			}
			Double totalLandValDouble = (Double) details
					.get(ClaimConstants.CLM_SAPGD_PARTICULAR_LAND);
			if (totalLandValDouble != null) {
				totalLandVal = totalLandValDouble.doubleValue();
			}
			Double totalMCValDouble = (Double) details
					.get(ClaimConstants.CLM_SAPGD_PARTICULAR_MC);
			if (totalMCValDouble != null) {
				totalMachineVal = totalMCValDouble.doubleValue();
			}
			Double totalBldgValDouble = (Double) details
					.get(ClaimConstants.CLM_SAPGD_PARTICULAR_BLDG);
			if (totalBldgValDouble != null) {
				totalBldgVal = totalBldgValDouble.doubleValue();
			}
			Double totalOFMAValDouble = (Double) details
					.get(ClaimConstants.CLM_SAPGD_PARTICULAR_OTHER_FIXED_MOV_ASSETS);
			if (totalOFMAValDouble != null) {
				totalOFMAVal = totalOFMAValDouble.doubleValue();
			}
			Double totalCurrAssetsDouble = (Double) details
					.get(ClaimConstants.CLM_SAPGD_PARTICULAR_CUR_ASSETS);
			if (totalCurrAssetsDouble != null) {
				totalCurrAssetsVal = totalCurrAssetsDouble.doubleValue();
			}
			Double totalOthersValDouble = (Double) details
					.get(ClaimConstants.CLM_SAPGD_PARTICULAR_OTHERS);
			if (totalOthersValDouble != null) {
				totalOthersVal = totalOthersValDouble.doubleValue();
			}
			asOnSnctn.setNetworthOfGuarantors(totalNetworth);
			asOnSnctn.setValueOfLand(totalLandVal);
			asOnSnctn.setValueOfBuilding(totalBldgVal);
			asOnSnctn.setValueOfMachine(totalOFMAVal);
			asOnSnctn.setValueOfOtherFixedMovableAssets(totalOFMAVal);
			asOnSnctn.setValueOfCurrentAssets(totalCurrAssetsVal);
			asOnSnctn.setValueOfOthers(totalOthersVal);
			sapg.setDetailsAsOnDateOfSanction(asOnSnctn);
			claimapplication.setSecurityAndPersonalGuaranteeDtls(sapg);

			String clmRefNumber = null;
			Vector clmsFiled = (Vector) processor.getAllClaimsFiled();
			for (int k = 0; k < clmsFiled.size(); k++) {
				HashMap mp = (HashMap) clmsFiled.elementAt(k);
				if (mp == null) {
					continue;
				}
				String mpMemberId = (String) mp
						.get(ClaimConstants.CLM_MEMBER_ID);
				String mpbid = (String) mp.get(ClaimConstants.CLM_BORROWER_ID);
				if (mpMemberId == null) {
					continue;
				}
				if (mpbid == null) {
					continue;
				}
				if ((mpMemberId.equals(memberId)) && (mpbid.equals(borrowerId))) {
					clmRefNumber = (String) mp
							.get(ClaimConstants.CLM_CLAIM_REF_NUMBER);
				}
			}

			HashMap totalRecDtls = processor
					.isRecoveryDetailsAvailable(clmRefNumber);
			Vector recDetails = (Vector) totalRecDtls
					.get(ClaimConstants.CLM_MAIN_TABLE);
			Vector recoveryDtls = new Vector();
			RecoveryDetails rd = null;
			for (int i = 0; i < recDetails.size(); i++) {
				HashMap recoveryDetail = (HashMap) recDetails.elementAt(i);
				if (recoveryDetail == null) {
					continue;
				}
				// //System.out.println("Printing Recovery Details :" +
				// recoveryDetail);
				String pan = (String) recoveryDetail
						.get(ClaimConstants.CLM_CGPAN);
				double tcPrincipalAmt = 0.0;
				Double tcPrincipalAmtObj = (Double) recoveryDetail
						.get(ClaimConstants.CLM_REC_TC_PRINCIPAL);
				if (tcPrincipalAmtObj != null) {
					tcPrincipalAmt = tcPrincipalAmtObj.doubleValue();
				}
				double tcInterestAmt = 0.0;
				Double tcInterestAmtObj = (Double) recoveryDetail
						.get(ClaimConstants.CLM_REC_TC_INTEREST);
				if (tcInterestAmtObj != null) {
					tcInterestAmt = tcInterestAmtObj.doubleValue();
				}
				double wcAmount = 0.0;
				Double wcAmountObj = (Double) recoveryDetail
						.get(ClaimConstants.CLM_REC_WC_AMOUNT);
				if (wcAmountObj != null) {
					wcAmount = wcAmountObj.doubleValue();
				}
				double wcOtherAmount = 0.0;
				Double wcOtherAmountObj = (Double) recoveryDetail
						.get(ClaimConstants.CLM_REC_WC_OTHER);
				if (wcOtherAmountObj != null) {
					wcOtherAmount = wcOtherAmountObj.doubleValue();
				}
				String mode = (String) recoveryDetail
						.get(ClaimConstants.CLM_REC_MODE);

				rd = new RecoveryDetails();
				rd.setCgpan(pan);
				rd.setModeOfRecovery(mode);
				rd.setTcPrincipal(tcPrincipalAmt);
				// //System.out.println("tcPrincipalAmt:"+tcPrincipalAmt);
				rd.setTcInterestAndOtherCharges(tcInterestAmt);
				// //System.out.println("tcInterestAmt:"+tcInterestAmt);
				rd.setWcAmount(wcAmount);
				rd.setWcOtherCharges(wcOtherAmount);

				if (!recoveryDtls.contains(rd)) {
					recoveryDtls.addElement(rd);
				}
			}

			if (cgpans.size() != recoveryDtls.size()) {
				for (int i = 0; i < cgpans.size(); i++) {
					RecoveryDetails recdetail = new RecoveryDetails();
					String cgpan = (String) cgpans.elementAt(i);
					recdetail.setCgpan(cgpan);
					boolean dtlsThere = false;
					for (int j = 0; j < recoveryDtls.size(); j++) {
						RecoveryDetails tempRd = (RecoveryDetails) recoveryDtls
								.elementAt(j);

						if (tempRd != null
								&& tempRd.getCgpan().equalsIgnoreCase(cgpan)) {
							dtlsThere = true;
							break;
						}
					}
					if (!dtlsThere) {
						recoveryDtls.add(recdetail);
					}
				}
			}

			/*
			 * for(int i=0;i<recoveryDtls.size();i++) {
			 * //System.out.println("***********"); rd =
			 * (RecoveryDetails)recoveryDtls.get(i);
			 * //System.out.println(" cgpan "+rd.getCgpan());
			 * //System.out.println(" recovery mode "+rd.getModeOfRecovery());
			 * //System.out.println(" tc principal "+rd.getTcPrincipal());
			 * //System.out.println(" interest and other charges "+rd.
			 * getTcInterestAndOtherCharges());
			 * //System.out.println(" WC amount "+rd.getWcAmount());
			 * //System.out.println(" WC other charges "+rd.getWcOtherCharges());
			 * 
			 * //System.out.println("***********"); }
			 */

			// Setting the ArrayList of ClaimSummaryDtls in the claim
			// application
			claimapplication.setClaimSummaryDtls(clmSummryDtls);
			// Setting the ArrayList of Recovery Dtls in the claim application
			claimapplication.setRecoveryDetails(recoveryDtls);

			// Setting the flag for Second Claim Installment in Claim
			// Application
			claimapplication.setSecondInstallment(true);

			// Retrieving the User Info
			User user = getUserInformation(request);
			String userid = user.getUserId();

			// Retrieving the present time in yyyy-mm-dd format
			String currentTime = (new java.sql.Date(System.currentTimeMillis()))
					.toString();

			// Storing the claimapplication and the borrower id in a hashtable
			Hashtable claimDetails = new Hashtable();
			claimDetails.put(borrowerId, claimapplication);
			// claimDetails.put(claimapplication.getBorrowerId(),
			// claimapplication);
			boolean exportObjectStatus = false;
			try {
				String contextPath1 = request.getSession(false)
						.getServletContext().getRealPath("");
				String contextPath = PropertyLoader
						.changeToOSpath(contextPath1);

				FileOutputStream fileOutputStream = null;
				;
				ObjectOutputStream objectOutputStream = null;
				String filename = contextPath + File.separator
						+ Constants.FILE_DOWNLOAD_DIRECTORY + File.separator
						+ userid + currentTime + ClaimConstants.CLM_TAG
						+ ClaimConstants.SECOND_INSTALLMENT
						+ ClaimConstants.CLM_EXTENSION;

				String formattedToOSPath = request.getContextPath()
						+ File.separator + Constants.FILE_DOWNLOAD_DIRECTORY
						+ File.separator + userid + currentTime
						+ ClaimConstants.CLM_TAG
						+ ClaimConstants.SECOND_INSTALLMENT
						+ ClaimConstants.CLM_EXTENSION;
				session.setAttribute("fileName", formattedToOSPath);
				File exportFlatFile = new File(filename);
				session.setAttribute("fileName", formattedToOSPath);
				if (!exportFlatFile.exists()) {
					exportFlatFile.createNewFile();
				}
				fileOutputStream = new FileOutputStream(exportFlatFile);
				objectOutputStream = new ObjectOutputStream(fileOutputStream);
				objectOutputStream.writeObject(claimDetails);
				objectOutputStream.close();
				fileOutputStream.close();
				exportObjectStatus = true;
			} catch (IOException ioexception) {
				exportObjectStatus = false;
				Log.log(Log.ERROR, "ClaimAction", "Export Claim File",
						"Export failed because " + ioexception.getMessage());
				throw new ExportFailedException("Export Failed.");
			}

			/*
			 * Cleaning up the Collection Objects
			 */
			// wcdetails.clear();
			// wcdetails = null;
			// claimsummarydetails.clear();
			claimsummarydetails = null;
			// claimDetails.clear();
			claimDetails = null;
			// npadetails.clear();
			npadetails = null;
			// hashmap.clear();
			hashmap = null;
			// tlcldetails.clear();
			tlcldetails = null;
			// recoverydetails.clear();
			recoverydetails = null;
			return mapping.findForward("exportClaimFile");

		}
		if (((String) session.getAttribute("mainMenu")).equals(MenuOptions
				.getMenu(MenuOptions.GM_PERIODIC_INFO))) {
			ClaimActionForm claimForm = (ClaimActionForm) form;
			claimForm.setRecoveryFlag(null);
			claimForm.setOtsFlag(null);
			return mapping.findForward("recoveryfilter");
		}
		return null;
	}
//dkr
	public ActionForward proceedFromRecoveryFilterPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String recoveryflag = null;
		HttpSession session = request.getSession(false);
		
		if ((((String) session.getAttribute("mainMenu")).equals(MenuOptions
				.getMenu(MenuOptions.CP_CLAIM_FOR)))
				&& (((String) session.getAttribute("subMenuItem"))
						.equals(MenuOptions
								.getMenu(MenuOptions.CP_CLAIM_FOR_FIRST_INSTALLMENT)))) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			ClaimActionForm claimForm = (ClaimActionForm) form;
			claimForm.setForumthrulegalinitiated("");
			String memberId = claimForm.getMemberId();
			String borrowerId = ((claimForm.getBorrowerID()).toUpperCase())
					.trim();
			recoveryflag = claimForm.getRecoveryFlag();
			java.util.Date npaDateNew = null;
			
			 
			if (recoveryflag.equals(ClaimConstants.DISBRSMNT_YES_FLAG)) {
				Log.log(Log.INFO, "ClaimAction",
						"proceedFromRecoveryFilterPage",
						"Redirecting to Recovery Details Page of Guarantee Maintenance");
				session.setAttribute(ClaimConstants.CLM_MEMBER_ID, memberId);
				session.setAttribute(ClaimConstants.CLM_BORROWER_ID, borrowerId);
				claimForm.setRecoveryFlag(ClaimConstants.DISBRSMNT_NO_FLAG);
				return mapping.findForward("recoverydetails");
			}

			ClaimsProcessor processor = new ClaimsProcessor();
			
			
			
			/*Changes for GST*/
			 String bankId = memberId.substring(0, 4);
 		    String zoneId = memberId.substring(4, 8);
 		    String branchId = memberId.substring(8, 12);
			 ArrayList<MLIInfo> branchStateList=processor.getGSTStateList(bankId);		 		
			 claimForm.setBranchStateList(branchStateList);
	        
			Log.log(Log.INFO, "ClaimAction", "proceedFromRecoveryFilterPage",
					"Retrieving member details for a member id......");
			MemberInfo memberDetails = processor.getMemberInfoDetails(memberId);
			Log.log(Log.INFO, "ClaimAction", "proceedFromRecoveryFilterPage",
					"Successfully retrieved member details for a member id......");
			Log.log(Log.INFO, "ClaimAction", "proceedFromRecoveryFilterPage",
					"Retrieving borrower details for a borrower id......");
			BorrowerInfo borrowerDetails = processor
					.getBorrowerDetails(borrowerId);
			Log.log(Log.INFO, "ClaimAction", "proceedFromRecoveryFilterPage",
					"Successfully retrieved details for a borrower id......");
			Log.log(Log.INFO, "ClaimAction", "proceedFromRecoveryFilterPage",
					"Retrieving NPA details for a borrower id......");
			java.util.Date npaClassifiedDate = null;
			java.util.Date npaReportingDate = null;
			String reasonfornpa = null;
			String willfulDefaulter = null;
			String subsidyFlag = null;
			String isSubsidyRcvdFlag = null;
			String isSubsidyAdjustedFlag = null;
			java.util.Date subsidyDt = null;
			double subsidyAmt = 0.0;
			HashMap npadetails = processor.isNPADetailsAvailable(borrowerId);
			if (npadetails == null) {
				throw new NoDataException("NPA Details not available.");
			}
			if (npadetails.size() == 0) {
				throw new NoDataException("NPA Details not available.");
			}

			String npaId = null;
			HashMap npadtlMainTable = (HashMap) npadetails
					.get(ClaimConstants.CLM_MAIN_TABLE);
			if (npadtlMainTable != null) {
				if (npadtlMainTable.size() > 0) {
					npaId = (String) npadtlMainTable.get("npaId");
					willfulDefaulter = (String) npadtlMainTable
							.get(ClaimConstants.WILLFUL_DEFAULTER);
					// if(willfulDefaulter != null)
					// {
					npaClassifiedDate = (java.util.Date) npadtlMainTable
							.get(ClaimConstants.NPA_CLASSIFIED_DT);
					// //System.out.println("NPA Classified From main:"+npaClassifiedDate);
					if (npaClassifiedDate != null) {
						npadtlMainTable
								.remove(ClaimConstants.NPA_CLASSIFIED_DT);
						// SimpleDateFormat sdf = new
						// SimpleDateFormat("dd/MM/yyyy");
						String npaClassifiedDateStr = sdf
								.format(npaClassifiedDate);
						npadtlMainTable.put(ClaimConstants.NPA_CLASSIFIED_DT,
								npaClassifiedDateStr);
						// //System.out.println("Test 1:"+claimForm.getNpaDateNew());
					}
					npaReportingDate = (java.util.Date) npadtlMainTable
							.get(ClaimConstants.NPA_REPORTING_DT);
					if (npaReportingDate != null) {
						npadtlMainTable.remove(ClaimConstants.NPA_REPORTING_DT);
						// SimpleDateFormat sdf = new
						// SimpleDateFormat("dd/MM/yyyy");
						String npaReportingDateStr = sdf
								.format(npaReportingDate);
						npadtlMainTable.put(ClaimConstants.NPA_REPORTING_DT,
								npaReportingDateStr);
					}
					reasonfornpa = (String) npadtlMainTable
							.get(ClaimConstants.REASONS_FOR_TURNING_NPA);
					// willfulDefaulter =
					// (String)npadtlMainTable.get(ClaimConstants.WILLFUL_DEFAULTER);
					subsidyFlag = (String) npadtlMainTable.get("SUBSIDYFLAG");
					isSubsidyRcvdFlag = (String) npadtlMainTable
							.get("SUBSIDYRCVDFLAG");
					isSubsidyAdjustedFlag = (String) npadtlMainTable
							.get("SUBSIDYADJUSTEDFLAG");
					subsidyAmt = (Double) npadtlMainTable.get("SUBSIDYAMT");
					subsidyDt = (java.util.Date) npadtlMainTable
							.get("SUBSIDYDATE");
					String subsidyDtStr = "";
					if (subsidyDt != null) {
						subsidyDtStr = sdf.format(subsidyDt);
						subsidyDt = sdf.parse(subsidyDtStr);
					}
					claimForm.setSubsidyFlag(subsidyFlag);
					claimForm.setIsSubsidyRcvdAfterNpa(isSubsidyRcvdFlag);
					claimForm.setIsSubsidyAdjustedOnDues(isSubsidyAdjustedFlag);
					claimForm.setSubsidyAmt(subsidyAmt);
					// claimForm.setSubsidyDate(subsidyDt);
					claimForm.setSubsidyDate(subsidyDtStr);
					// }
				}
			}

			HashMap npadtltemptable = (HashMap) npadetails
					.get(ClaimConstants.CLM_TEMP_TABLE);
			// //System.out.println("Printing the NPA Temp Table :" +
			// npadtltemptable);
			// String willfulDefaulter = null;
			// boolean npaDtlsAvl = false;
			if (npadtltemptable != null) {
				if (npadtltemptable.size() > 0) {
					npaId = (String) npadtltemptable.get("npaId");
					willfulDefaulter = (String) npadtltemptable
							.get(ClaimConstants.WILLFUL_DEFAULTER);
					// if(willfulDefaulter != null)
					// {
					/*
					 * Object obj =
					 * npadtltemptable.get(ClaimConstants.NPA_CLASSIFIED_DT);
					 * if(obj instanceof java.lang.String) {
					 * //System.out.println("*** String Object ***"); } if(obj
					 * instanceof java.util.Date) {
					 * //System.out.println("*** Java Date ***"); }
					 */
					npaClassifiedDate = (java.util.Date) npadtltemptable
							.get(ClaimConstants.NPA_CLASSIFIED_DT);
					// //System.out.println("npaClassifiedDate from Temp:"+npaClassifiedDate);

					if (npaClassifiedDate != null) {
						npadtltemptable
								.remove(ClaimConstants.NPA_CLASSIFIED_DT);
						// SimpleDateFormat sdf = new
						// SimpleDateFormat("dd/MM/yyyy");

						String npaClassifiedDateStr = sdf
								.format(npaClassifiedDate);

						npadtltemptable.put(ClaimConstants.NPA_CLASSIFIED_DT,
								npaClassifiedDateStr);
					}
					npaReportingDate = (java.util.Date) npadtltemptable
							.get(ClaimConstants.NPA_REPORTING_DT);
					if (npaReportingDate != null) {
						npadtltemptable.remove(ClaimConstants.NPA_REPORTING_DT);
						// SimpleDateFormat sdf = new
						// SimpleDateFormat("dd/MM/yyyy");
						String npaReportingDateStr = sdf
								.format(npaReportingDate);
						npadtltemptable.put(ClaimConstants.NPA_REPORTING_DT,
								npaReportingDateStr);
					}
					reasonfornpa = (String) npadtltemptable
							.get(ClaimConstants.REASONS_FOR_TURNING_NPA);
					// willfulDefaulter =
					// (String)npadtltemptable.get(ClaimConstants.WILLFUL_DEFAULTER);
					subsidyFlag = (String) npadtltemptable.get("SUBSIDYFLAG");
					isSubsidyRcvdFlag = (String) npadtltemptable
							.get("SUBSIDYRCVDFLAG");
					isSubsidyAdjustedFlag = (String) npadtltemptable
							.get("SUBSIDYADJUSTEDFLAG");
					subsidyAmt = (Double) npadtltemptable.get("SUBSIDYAMT");
					subsidyDt = (java.util.Date) npadtltemptable
							.get("SUBSIDYDATE");
					String subsidyDtStr = "";
					if (subsidyDt != null) {
						subsidyDtStr = sdf.format(subsidyDt);
						subsidyDt = sdf.parse(subsidyDtStr);
					}
					claimForm.setSubsidyFlag(subsidyFlag);
					claimForm.setIsSubsidyRcvdAfterNpa(isSubsidyRcvdFlag);
					claimForm.setIsSubsidyAdjustedOnDues(isSubsidyAdjustedFlag);
					claimForm.setSubsidyAmt(subsidyAmt);
					// claimForm.setSubsidyDate(subsidyDt);
					claimForm.setSubsidyDate(subsidyDtStr);
					// }
				}
			}

			/*
			 * if(!npaDtlsAvl) {
			 * Log.log(Log.INFO,"ClaimAction","forwardToNextPage"
			 * ,"Error : npa details do not exist for the borrower."); // return
			 * mapping.findForward("npaDetailsPage"); throw new
			 * NoDataException("NPA Details not available."); } java.util.Date
			 * npaClassifiedDate =
			 * (java.util.Date)npadtlMainTable.get(ClaimConstants
			 * .NPA_CLASSIFIED_DT); java.util.Date npaReportingDate =
			 * (java.util.
			 * Date)npadtlMainTable.get(ClaimConstants.NPA_REPORTING_DT); String
			 * reasonfornpa =
			 * (String)npadtlMainTable.get(ClaimConstants.REASONS_FOR_TURNING_NPA
			 * ); String willfulDefaulter =
			 * (String)npadtlMainTable.get(ClaimConstants.WILLFUL_DEFAULTER);
			 */

			// claimapplication.setDateOnWhichAccountClassifiedNPA(npaClassifiedDate);
			// claimapplication.setDateOfReportingNpaToCgtsi(npaReportingDate);
			// claimapplication.setReasonsForAccountTurningNPA(reasonfornpa);

			/*
			 * java.util.Date classifiedDt =
			 * (java.util.Date)npadetails.get(ClaimConstants.NPA_CLASSIFIED_DT);
			 * String classifiedDtStr = null; if(classifiedDt != null) {
			 * classifiedDtStr = sdf.format(classifiedDt); }
			 * npadetails.remove(ClaimConstants.NPA_CLASSIFIED_DT);
			 * java.util.Date reportDt =
			 * (java.util.Date)npadetails.get(ClaimConstants.NPA_REPORTING_DT);
			 * String reportDtStr = null; if(reportDt != null) { reportDtStr =
			 * sdf.format(reportDt); }
			 * npadetails.remove(ClaimConstants.NPA_REPORTING_DT);
			 * npadetails.put(ClaimConstants.NPA_CLASSIFIED_DT,classifiedDtStr);
			 * npadetails.put(ClaimConstants.NPA_REPORTING_DT,reportDtStr);
			 */
			Log.log(Log.INFO, "ClaimAction", "proceedFromRecoveryFilterPage",
					"Successfully retrieved npa details for a borrower id......");
			Log.log(Log.INFO, "ClaimAction", "proceedFromRecoveryFilterPage",
					"Retrieving legal details for a borrower id......");
			LegalProceedingsDetail legaldetails = processor
					.isLegalProceedingsDetailAvl(borrowerId);
			Log.log(Log.INFO, "ClaimAction", "proceedFromRecoveryFilterPage",
					"Printing Legal Details Object :" + legaldetails);
			Log.log(Log.INFO, "ClaimAction", "proceedFromRecoveryFilterPage",
					"Successfully retrieved legal details for a borrower id......");
			Log.log(Log.INFO, "ClaimAction", "proceedFromRecoveryFilterPage",
					"Retrieving the cgpan details for the borrower id......");
			// //System.out.println("before call getCGPANDetailsForBorrowerId:"+
			// new java.util.Date());
			Vector cgpnDetails = processor.getCGPANDetailsForBidClaim(
					borrowerId, memberId);
			// //System.out.println("after call getCGPANDetailsForBorrowerId:"+
			// new java.util.Date());
			 //System.out.println("cgpnDetails:"+cgpnDetails.size());
			Log.log(Log.INFO, "ClaimAction", "proceedFromRecoveryFilterPage",
					"Successfully retrieved the cgpan details for the borrower id......");
			Vector cgpans = new Vector();
			Vector tccgpans = new Vector();
			Vector wccgpans = new Vector();
			HashMap hashmap = null;
			Administrator admin = new Administrator();
			ParameterMaster pm = (ParameterMaster) admin.getParameter();
			int periodTenureExpiryLodgementClaims = pm
					.getPeriodTenureExpiryLodgementClaims();
			java.util.Date currentDate = new java.util.Date();

			// boolean isITPANAvl = false;
			// double approvedAmnt = 0.0;
			// double enhancedApprvdAmnt = 0.0;
			// double amountToBeConsidered = 0.0;
			for (int i = 0; i < cgpnDetails.size(); i++) {
				hashmap = (HashMap) cgpnDetails.elementAt(i);
				 //System.out.println("Printing HashMap :" + hashmap);
				if (hashmap != null) {
					//System.out.println("Printing HashMap ClaimConstants.CLM_CGPAN :" + ClaimConstants.CLM_CGPAN);	
					if (hashmap.containsKey(ClaimConstants.CLM_CGPAN)) {
						Log.log(Log.INFO, "ClaimAction",
								"proceedFromRecoveryFilterPage",
								"Printing cgpnDetail :" + hashmap);
						String cgpan = (String) hashmap
								.get(ClaimConstants.CLM_CGPAN);
						String cgpanStatus = (String) hashmap
								.get(ClaimConstants.APPLICATION_STATUS);
						 //System.out.println("cgpan:"+cgpan+"cgpanStatus"+cgpanStatus);
						Log.log(Log.INFO, "ClaimAction",
								"proceedFromRecoveryFilterPage",
								"Printing cgpan :" + cgpan);
					//	cgpanStatus="EX";
						if ((cgpan != null) && (!(cgpan.equals("")))) {
							if ((hashmap.get(ClaimConstants.CGPAN_LOAN_TYPE))
									.equals(ClaimConstants.CGPAN_TC_LOAN_TYPE)) {
								//System.out.println("TC 1st if loop executed");
								if ((cgpanStatus != null)
										&& (cgpanStatus.equalsIgnoreCase("EX"))) {
									//System.out.println("TC 2nd if loop executed");
									String applicationRefNum = processor
											.getAppRefNumber(cgpan);
									// //System.out.println("Line number 2381 applicationRefNum:"+applicationRefNum);
									TermLoan termLoanObj = processor
											.getTermLoan(applicationRefNum,
													"TC");
									int applicationTenure = termLoanObj
											.getTenure();
									// //System.out.println("TC CGPAN:"+cgpan);
									 //System.out.println("applicationTenure:"+applicationTenure);
									// //System.out.println("NPA Classified Dt:"+claimForm.getDateOnWhichAccountClassifiedNPA());
									java.util.Date guarStartDt = (java.util.Date) termLoanObj
											.getAmountSanctionedDate();
									java.util.Date expiryDate = processor
											.getDate(guarStartDt,
													applicationTenure);
									// //System.out.println("expiryDate:"+expiryDate);
									java.util.Date tenureExpiryDate = processor
											.getDate(expiryDate,
													periodTenureExpiryLodgementClaims);

									// added by sukumar@path on 29-Jan-2010 for
									// extending time period for lodgement of
									// expired applications
									Calendar calendar2 = Calendar.getInstance();
									calendar2.set(Calendar.MONTH,
											Calendar.MARCH);
									calendar2.set(Calendar.DATE, 31);
									calendar2.set(Calendar.YEAR, 2010);
									java.util.Date extendedtenureExpiryDate = calendar2
											.getTime();

									// compare tenure expiry date with extended
									// tenure expiry date, if tenure expiry date
									// is lower than extende tenure expiry date,
									// takes extended tenure expiry date as
									// tenure expiry date
									if ((extendedtenureExpiryDate
											.compareTo(tenureExpiryDate)) > 0) {
										tenureExpiryDate = extendedtenureExpiryDate;
									}
									if ((currentDate
											.compareTo(tenureExpiryDate)) > 0) {
										// //System.out.println("Test1");
										continue;
									}
								}
								//System.out.println("After TC 2nd if loop executed");
								HashMap mp = new HashMap();
								java.util.Date dsbrsDt = (java.util.Date) hashmap
										.get(ClaimConstants.CLM_LAST_DISBURSEMENT_DT);
								//System.out.println("After TC 2nd if loop executed 1");
								Log.log(Log.INFO, "ClaimAction",
										"proceedFromRecoveryFilterPage",
										"******* Printing dsbrsDt :" + dsbrsDt);
								
								Double appAmt = (Double) hashmap
										.get(ClaimConstants.CLM_APPLICATION_APPRVD_AMNT);
								//System.out.println("After TC 2nd if loop executed 2");
								Double repaidAmnt = (Double) hashmap
										.get(ClaimConstants.TOTAL_AMNT_REPAID);
								//System.out.println("After TC 2nd if loop executed 3");
								java.util.Date guarStartDt = (java.util.Date) hashmap
										.get(ClaimConstants.CLM_GUARANTEE_START_DT);
								//System.out.println("After TC 2nd if loop executed 4");
								Log.log(Log.INFO, "ClaimAction",
										"proceedFromRecoveryFilterPage",
										"******* Printing guarStartDt :"
												+ guarStartDt);
								//System.out.println(guarStartDt+"After TC 2nd if loop executed 5 ");
								if (guarStartDt == null) {
									continue;
								}
								//System.out.println("After TC 2nd if loop executed 6");
								mp.put(ClaimConstants.CLM_CGPAN, cgpan);
								mp.put(ClaimConstants.CLM_LAST_DISBURSEMENT_DT,
										dsbrsDt);
								mp.put(ClaimConstants.TOTAL_AMNT_REPAID,
										repaidAmnt);
								mp.put(ClaimConstants.CLM_APPLICATION_APPRVD_AMNT,
										appAmt);
								Log.log(Log.INFO, "ClaimAction",
										"proceedFromRecoveryPage",
										"Printing HashMap :" + mp);
								//System.out.println("MP "+mp);
								if (!tccgpans.contains(mp)) {
									// //System.out.println("tccgpans doen not contained cgpan");
									tccgpans.addElement(mp);
								}
							}
							if ((hashmap.get(ClaimConstants.CGPAN_LOAN_TYPE))
									.equals(ClaimConstants.CGPAN_WC_LOAN_TYPE)) {
								if ((cgpanStatus != null)
										&& (cgpanStatus.equalsIgnoreCase("EX"))) {
									HashMap workingCapitalDtl = processor
											.getWorkingCapital(cgpan);
									Integer applicationTenureObj = (Integer) workingCapitalDtl
											.get(ClaimConstants.WC_TENURE);
									if (applicationTenureObj != null) {
										int applicationTenure = applicationTenureObj
												.intValue();
										// //System.out.println("WC CGPAN:"+cgpan);
										java.util.Date guarStartDt = (java.util.Date) workingCapitalDtl
												.get(ClaimConstants.CLM_GUARANTEE_START_DT);
										java.util.Date expiryDate = processor
												.getDate(guarStartDt,
														applicationTenure);
										java.util.Date tenureExpiryDate = processor
												.getDate(expiryDate,
														periodTenureExpiryLodgementClaims);
										// //System.out.println("tenureExpiryDate:"+tenureExpiryDate);
										// added by sukumar@path on 29-Jan-2010
										// for extending time period for
										// lodgement of expired applications
										Calendar calendar2 = Calendar
												.getInstance();
										calendar2.set(Calendar.MONTH,
												Calendar.MARCH);
										calendar2.set(Calendar.DATE, 31);
										calendar2.set(Calendar.YEAR, 2010);
										java.util.Date extendedtenureExpiryDate = calendar2
												.getTime();
										// //System.out.println("tenureExpiryDate:"+tenureExpiryDate);
										// //System.out.println("extendedtenureExpiryDate:"+extendedtenureExpiryDate);
										// compare tenure expiry date with
										// extended tenure expiry date, if
										// tenure expiry date is lower than
										// extende tenure expiry date, takes
										// extended tenure expiry date as tenure
										// expiry date
										if ((extendedtenureExpiryDate
												.compareTo(tenureExpiryDate)) > 0) {
											tenureExpiryDate = extendedtenureExpiryDate;
										}

										if ((currentDate
												.compareTo(tenureExpiryDate)) > 0) {
											// //System.out.println("Test2");
											continue;
										}
									}
								}

								java.util.Date guarStartDt = (java.util.Date) hashmap
										.get(ClaimConstants.CLM_GUARANTEE_START_DT);
								Log.log(Log.INFO, "ClaimAction",
										"proceedFromRecoveryFilterPage",
										"******* Printing guarStartDt :"
												+ guarStartDt);

								if (guarStartDt == null) {
									continue;
								}
								if (!wccgpans.contains(cgpan)) {
									wccgpans.addElement(cgpan);
								}
							}
							if ((hashmap.get(ClaimConstants.CGPAN_LOAN_TYPE))
									.equals(ClaimConstants.CGPAN_CC_LOAN_TYPE)) {
								if ((cgpanStatus != null)
										&& (cgpanStatus.equalsIgnoreCase("EX"))) {
									String applicationRefNum = processor
											.getAppRefNumber(cgpan);
									TermLoan termLoanObj = processor
											.getTermLoan(applicationRefNum,
													"CC");
									int applicationTenure = termLoanObj
											.getTenure();
									java.util.Date guarStartDt = (java.util.Date) termLoanObj
											.getAmountSanctionedDate();
									java.util.Date expiryDate = processor
											.getDate(guarStartDt,
													applicationTenure);
									java.util.Date tenureExpiryDate = processor
											.getDate(expiryDate,
													periodTenureExpiryLodgementClaims);
									// added by sukumar@path on 29-Jan-2010 for
									// extending time period for lodgement of
									// expired applications
									Calendar calendar2 = Calendar.getInstance();
									calendar2.set(Calendar.MONTH,
											Calendar.MARCH);
									calendar2.set(Calendar.DATE, 31);
									calendar2.set(Calendar.YEAR, 2010);
									java.util.Date extendedtenureExpiryDate = calendar2
											.getTime();
									// //System.out.println("tenureExpiryDate:"+tenureExpiryDate);
									// //System.out.println("extendedtenureExpiryDate:"+extendedtenureExpiryDate);
									// compare tenure expiry date with extended
									// tenure expiry date, if tenure expiry date
									// is lower than extende tenure expiry date,
									// takes extended tenure expiry date as
									// tenure expiry date
									if ((extendedtenureExpiryDate
											.compareTo(tenureExpiryDate)) > 0) {
										tenureExpiryDate = extendedtenureExpiryDate;
									}
									if ((currentDate
											.compareTo(tenureExpiryDate)) > 0) {
										// //System.out.println("Test3");
										continue;
									}
								}

								HashMap mp = new HashMap();
								java.util.Date dsbrsDt = (java.util.Date) hashmap
										.get(ClaimConstants.CLM_LAST_DISBURSEMENT_DT);
								Log.log(Log.INFO, "ClaimAction",
										"proceedFromRecoveryFilterPage",
										"******* Printing dsbrsDt :" + dsbrsDt);
								// //System.out.println("dsbrsDt:"+dsbrsDt);
								Double repaidAmnt = (Double) hashmap
										.get(ClaimConstants.TOTAL_AMNT_REPAID);
								Double appAmt = (Double) hashmap
										.get(ClaimConstants.CLM_APPLICATION_APPRVD_AMNT);

								mp.put(ClaimConstants.CLM_CGPAN, cgpan);
								mp.put(ClaimConstants.CLM_LAST_DISBURSEMENT_DT,
										dsbrsDt);
								mp.put(ClaimConstants.TOTAL_AMNT_REPAID,
										repaidAmnt);
								mp.put(ClaimConstants.CLM_APPLICATION_APPRVD_AMNT,
										appAmt);
								// //System.out.println("repaidAmnt:"+repaidAmnt);
								Log.log(Log.INFO, "ClaimAction",
										"proceedFromRecoveryPage",
										"Printing HashMap :" + mp);
								java.util.Date guarStartDt = (java.util.Date) hashmap
										.get(ClaimConstants.CLM_GUARANTEE_START_DT);
								// //System.out.println("guarStartDt:"+guarStartDt);
								Log.log(Log.INFO, "ClaimAction",
										"proceedFromRecoveryFilterPage",
										"******* Printing guarStartDt :"
												+ guarStartDt);
								if (guarStartDt == null) {
									continue;
								}
								if (!tccgpans.contains(mp)) {
									tccgpans.addElement(mp);
								}
							}
							if (!cgpans.contains(cgpan)) {
								cgpans.addElement(cgpan);
							}
						}
					}
				}
			}
			// //System.out.println("size of cgpans:" + cgpans.size());
			// //System.out.println("tccgpans:"+tccgpans);
			// //System.out.println("wccgpans:"+wccgpans);

			// Retrieving the primary security and guarantor details.
			CPDAO dao = new CPDAO();
			Double totalLandValDouble = 0.0;
			Double totalNetworthDouble = 0.0;
			Double totalMachineValDouble = 0.0;
			Double totalBldgValueDouble = 0.0;
			Double totalOFMAValDouble = 0.0;
			Double totalCurrAssetsValDouble = 0.0;
			Double totalOthersValDouble = 0.0;
			double totSecAsOnSanc = 0.0;
			double totSecAsOnNpa = 0.0;

			String totalLandValueStr = null;
			String totalNetworthStr = null;
			String totalMachineValueStr = null;
			String totalBldgValueStr = null;
			String totalOFMAValueStr = null;
			String totalCurrAssetsValueStr = null;
			String totalOthersValueStr = null;
			String reasonReduction = "";
			Map securityMap = new HashMap();
			Map npaSecurityMap = new HashMap();
			// CPDAO dao = new CPDAO();
			// HashMap details =
			// processor.getPrimarySecurityAndNetworthOfGuarantors(borrowerId,memberId);
			HashMap details = dao
					.getPrimarySecurityAndNetworthOfGuarantors(npaId);
			HashMap sancmap = (HashMap) details.get("SAN");
			HashMap npamap = (HashMap) details.get("NPA");

			if (sancmap != null) {
				totalLandValDouble = (Double) sancmap.get("LAND");
				if (totalLandValDouble != null) {
					if (totalLandValDouble.doubleValue() > 0.0D) {
						totalLandValueStr = totalLandValDouble.toString();
						totSecAsOnSanc = totSecAsOnSanc
								+ totalLandValDouble.doubleValue();
					} else {
						totalLandValueStr = "";
					}
				}
				totalNetworthDouble = (Double) sancmap.get("networth");
				if (totalNetworthDouble != null) {
					if (totalNetworthDouble.doubleValue() > 0.0D) {
						totalNetworthStr = totalNetworthDouble.toString();
					} else {
						totalNetworthStr = "";
					}
				}
				totalMachineValDouble = (Double) sancmap.get("MACHINE");
				if (totalMachineValDouble != null) {
					if (totalMachineValDouble.doubleValue() > 0.0D) {
						totalMachineValueStr = totalMachineValDouble.toString();
						totSecAsOnSanc = totSecAsOnSanc
								+ totalMachineValDouble.doubleValue();
					} else {
						totalMachineValueStr = "";
					}
				}
				totalBldgValueDouble = (Double) sancmap.get("BUILDING");
				if (totalBldgValueDouble != null) {
					if (totalBldgValueDouble.doubleValue() > 0.0D) {
						totalBldgValueStr = totalBldgValueDouble.toString();
						totSecAsOnSanc = totSecAsOnSanc
								+ totalBldgValueDouble.doubleValue();
					} else {
						totalBldgValueStr = "";
					}
				}
				totalOFMAValDouble = (Double) sancmap
						.get("OTHER FIXED MOVABLE ASSETS");
				if (totalOFMAValDouble != null) {
					if (totalOFMAValDouble.doubleValue() > 0.0D) {
						totalOFMAValueStr = totalOFMAValDouble.toString();
						totSecAsOnSanc = totSecAsOnSanc
								+ totalOFMAValDouble.doubleValue();
					} else {
						totalOFMAValueStr = "";
					}
				}
				totalCurrAssetsValDouble = (Double) sancmap
						.get("CURRENT ASSETS");
				if (totalCurrAssetsValDouble != null) {
					if (totalCurrAssetsValDouble.doubleValue() > 0.0D) {
						totalCurrAssetsValueStr = totalCurrAssetsValDouble
								.toString();
						totSecAsOnSanc = totSecAsOnSanc
								+ totalCurrAssetsValDouble.doubleValue();
					} else {
						totalCurrAssetsValueStr = "";
					}
				}
				totalOthersValDouble = (Double) sancmap.get("OTHERS");
				if (totalOthersValDouble != null) {
					if (totalOthersValDouble.doubleValue() > 0.0D) {
						totalOthersValueStr = totalOthersValDouble.toString();
						totSecAsOnSanc = totSecAsOnSanc
								+ totalOthersValDouble.doubleValue();
					} else {
						totalOthersValueStr = "";
					}
				}
			}

			securityMap.put("LAND", totalLandValueStr);
			securityMap.put("networth", totalNetworthStr);
			securityMap.put("MACHINE", totalMachineValueStr);
			securityMap.put("BUILDING", totalBldgValueStr);
			securityMap.put("OTHER FIXED MOVABLE ASSETS", totalOFMAValueStr);
			securityMap.put("CURRENT ASSETS", totalCurrAssetsValueStr);
			securityMap.put("OTHERS", totalOthersValueStr);
			securityMap.put("reasonReduction", "NA");
			claimForm.setAsOnDtOfSanctionDtl(securityMap);

			if (npamap != null) {
				totalLandValDouble = (Double) npamap.get("LAND");
				if (totalLandValDouble != null) {
					if (totalLandValDouble.doubleValue() > 0.0D) {
						totalLandValueStr = totalLandValDouble.toString();
						totSecAsOnNpa = totSecAsOnNpa
								+ totalLandValDouble.doubleValue();
					} else {
						totalLandValueStr = "";
					}
				}
				totalNetworthDouble = (Double) npamap.get("networth");
				if (totalNetworthDouble != null) {
					if (totalNetworthDouble.doubleValue() > 0.0D) {
						totalNetworthStr = totalNetworthDouble.toString();
					} else {
						totalNetworthStr = "";
					}
				}
				totalMachineValDouble = (Double) npamap.get("MACHINE");
				if (totalMachineValDouble != null) {
					if (totalMachineValDouble.doubleValue() > 0.0D) {
						totalMachineValueStr = totalMachineValDouble.toString();
						totSecAsOnNpa = totSecAsOnNpa
								+ totalMachineValDouble.doubleValue();
					} else {
						totalMachineValueStr = "";
					}
				}
				totalBldgValueDouble = (Double) npamap.get("BUILDING");
				if (totalBldgValueDouble != null) {
					if (totalBldgValueDouble.doubleValue() > 0.0D) {
						totalBldgValueStr = totalBldgValueDouble.toString();
						totSecAsOnNpa = totSecAsOnNpa
								+ totalBldgValueDouble.doubleValue();
					} else {
						totalBldgValueStr = "";
					}
				}
				totalOFMAValDouble = (Double) npamap
						.get("OTHER FIXED MOVABLE ASSETS");
				if (totalOFMAValDouble != null) {
					if (totalOFMAValDouble.doubleValue() > 0.0D) {
						totalOFMAValueStr = totalOFMAValDouble.toString();
						totSecAsOnNpa = totSecAsOnNpa
								+ totalOFMAValDouble.doubleValue();
					} else {
						totalOFMAValueStr = "";
					}
				}
				totalCurrAssetsValDouble = (Double) npamap
						.get("CURRENT ASSETS");
				if (totalCurrAssetsValDouble != null) {
					if (totalCurrAssetsValDouble.doubleValue() > 0.0D) {
						totalCurrAssetsValueStr = totalCurrAssetsValDouble
								.toString();
						totSecAsOnNpa = totSecAsOnNpa
								+ totalCurrAssetsValDouble.doubleValue();
					} else {
						totalCurrAssetsValueStr = "";
					}
				}
				totalOthersValDouble = (Double) npamap.get("OTHERS");
				if (totalOthersValDouble != null) {
					if (totalOthersValDouble.doubleValue() > 0.0D) {
						totalOthersValueStr = totalOthersValDouble.toString();
						totSecAsOnNpa = totSecAsOnNpa
								+ totalOthersValDouble.doubleValue();
					} else {
						totalOthersValueStr = "";
					}
				}
				reasonReduction = (String) npamap.get("reasonReduction");
			}
			npaSecurityMap.put("LAND", totalLandValueStr);
			npaSecurityMap.put("networth", totalNetworthStr);
			npaSecurityMap.put("MACHINE", totalMachineValueStr);
			npaSecurityMap.put("BUILDING", totalBldgValueStr);
			npaSecurityMap.put("OTHER FIXED MOVABLE ASSETS", totalOFMAValueStr);
			npaSecurityMap.put("CURRENT ASSETS", totalCurrAssetsValueStr);
			npaSecurityMap.put("OTHERS", totalOthersValueStr);
			npaSecurityMap.put("reasonReduction", reasonReduction);
			claimForm.setAsOnDtOfNPA(npaSecurityMap);

			claimForm.setTotSecAsOnSanc(totSecAsOnSanc);
			claimForm.setTotSecAsOnNpa(totSecAsOnNpa);
			// Retrieving the recovery modes from the recovery_mode master table
			Log.log(Log.INFO, "ClaimAction", "proceedFromRecoveryFilterPage",
					"Retrieving all the recovery modes from the database......");
			Vector recoveryModes = processor.getAllRecoveryModes();

			// Retrieving the OTS Details
			Vector otsDtls = processor.getOTSDetails(borrowerId);
			java.util.Date otsReqDate = null;
			String otsReqDateStr = null;
			for (int k = 0; k < otsDtls.size(); k++) {
				HashMap dtl = (HashMap) otsDtls.elementAt(k);
				if (dtl == null) {
					continue;
				}
				otsReqDate = (java.util.Date) dtl
						.get(ClaimConstants.CLM_OTS_REQUEST_DATE);
				sdf = new SimpleDateFormat("dd/MM/yyyy");
				otsReqDateStr = sdf.format(otsReqDate);
			}

			// Setting the properties of Claim Form
			claimForm.setMemberDetails(memberDetails);
			claimForm.setBorrowerDetails(borrowerDetails);
			if (npadtltemptable != null) {
				if (npadtltemptable.size() > 0) {
					claimForm.setNpaDetails(npadtltemptable);
				}
			} else if (npadtlMainTable != null) {
				if (npadtlMainTable.size() > 0) {
					claimForm.setNpaDetails(npadtlMainTable);
				}
			}
			// claimForm.setNpaDetails(npadetails);
			Log.log(Log.INFO,
					"ClaimAction",
					"proceedFromRecoveryFilterPage()",
					"legaldetails.getForumRecoveryProceedingsInitiated() :"
							+ legaldetails
									.getForumRecoveryProceedingsInitiated());
			String forumNameStr = legaldetails
					.getForumRecoveryProceedingsInitiated();
			if (forumNameStr != null) {
				if ((forumNameStr.equals("Civil Court"))
						|| (forumNameStr.equals("DRT"))
						|| (forumNameStr.equals("LokAdalat"))
						|| (forumNameStr.equals("Revenue Recovery Autority"))
						|| (forumNameStr.equals("Securitisation Act "))) {
					claimForm.setForumthrulegalinitiated(legaldetails
							.getForumRecoveryProceedingsInitiated());
				} else {
					claimForm.setForumthrulegalinitiated("Others");
					claimForm.setOtherforums(legaldetails
							.getForumRecoveryProceedingsInitiated());
				}
			}
			// claimForm.setForumthrulegalinitiated(legaldetails.getForumRecoveryProceedingsInitiated());
			// claimForm.setOtherforums(legaldetails.getForumRecoveryProceedingsInitiated());
			claimForm.setCaseregnumber(legaldetails.getSuitCaseRegNumber());
			java.util.Date legaldt_utilformat = legaldetails.getFilingDate();
			if (legaldt_utilformat != null) {
				claimForm.setLegaldate(sdf.format(legaldt_utilformat));
			} else {
				claimForm.setLegaldate("");
			}
			claimForm.setNameofforum(legaldetails.getNameOfForum());
			claimForm.setLocation(legaldetails.getLocation());
			claimForm.setAmountclaimed(String.valueOf(legaldetails
					.getAmountClaimed()));
			claimForm.setCurrentstatusremarks(legaldetails
					.getCurrentStatusRemarks());
			claimForm.setProceedingsConcluded(legaldetails
					.getIsRecoveryProceedingsConcluded());
			// //System.out.println("ProceedingsConcludedFlag :" +
			// legaldetails.getIsRecoveryProceedingsConcluded());

			Vector cgs = new Vector();
			Vector tccgs = new Vector();
			Vector wccgs = new Vector();

			Map mapnpa = dao.getCgpanFlagsForBid(borrowerId);
			Vector tccgpannpa = (Vector) mapnpa.get("tccgpans");
			Vector wccgpannpa = (Vector) mapnpa.get("wccgpans");
			 //System.out.println("1 tccgpannpa:"+tccgpannpa);
			 //System.out.println("2 wccgpannpa:"+wccgpannpa);
			/* SETTING TC CGPANS INTO FORM */
			 //System.out.println("TC tccgpans.size"+tccgpans.size());
			for (int i = 0; i < tccgpans.size(); i++) {
				Map m = (HashMap) tccgpans.get(i);
				String cgNo = (String) m.get("CGPAN");
				//System.out.println("TC cgNo"+cgNo);
				//System.out.println("TC tccgpannpa.size"+tccgpannpa.size());
				for (int j = 0; j < tccgpannpa.size(); j++) {
					Map m2 = (HashMap) tccgpannpa.get(j);
					String cg = (String) m2.get("CGPAN");
					//System.out.println("TC cg"+cg);
					if (cgNo.equals(cg)) {
						m2.put(ClaimConstants.CGPAN_APPRVD_AMNT,
								m2.get(ClaimConstants.CGPAN_APPRVD_AMNT));
						m2.put("GUARANTEESTARTDT", m.get("GUARANTEESTARTDT"));
						tccgs.add(m2);
						if (!cgs.contains(cgNo)) {
							 //System.out.println("TC cgpan record addded into cgs"+cgNo);
							cgs.addElement(cgNo);
						}
						break;
					}
				}

			}
			int tcCounter = 0;
			if (tccgs != null) {
				tcCounter = tccgs.size();
			}
			claimForm.setTcCounter(tcCounter);
			claimForm.setTcCgpansVector(tccgs);
			// //System.out.println("size of tcVector:" + tcCounter);
			/* SETTING WC CGPANS INTO FORM */
			for (int i = 0; i < wccgpans.size(); i++) {
				String cgNo = (String) wccgpans.get(i);
				for (int j = 0; j < wccgpannpa.size(); j++) {
					Map m2 = (HashMap) wccgpannpa.get(j);
					String cg = (String) m2.get("CGPAN");
					if (cgNo.equals(cg)) {
						wccgs.add(m2);
						if (!cgs.contains(cgNo)) {
							//System.out.println("WC cgpan record addded into cgs"+cgNo);
							cgs.addElement(cgNo);
						}
						break;
					}
				}

			}
			int wcCounter = 0;
			if (wccgs != null) {
				wcCounter = wccgs.size();
			}
			claimForm.setWcCounter(wcCounter); // TERM LOAN CGPANS
			claimForm.setWcCgpansVector(wccgs); // WORKING CAPITAL CGPANS
			claimForm.setCgpansVector(cgs); // RECOVERY DETAIL CGPANS
			//System.out.println("1 setCgpansVector executed");
			// //System.out.println("size of wcVector:" + wcCounter);
			// //System.out.println("size of cgVector:" + cgs.size());
			// //System.out.println("cgs:"+cgs);
			// //System.out.println("tccgs:"+tccgs);
			// //System.out.println("wccgs:"+wccgs);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"dd/MM/yyyy");
			/* SETTING TERM LOAN/COMPOSITE LOAN DETAILS INTO FORM */
			for (int i = 0; i < tccgs.size(); i++) {
				int j = i + 1;
				HashMap map = (HashMap) tccgs.elementAt(i);
				if (map != null) {
					Date dsbrsDt = (Date) map.get("LASTDSBRSMNTDT");
					// Double repaidAmnt = (Double)map.get("AMNT_REPAID");
					Double repaidAmnt = (Double) map.get("TCPRINREPAMT");
					Double repaidIntAmnt = (Double) map.get("TCINTREPAMT");
					double tcprinosnpaAmnt = (Double) map.get("TCPRINNPAOSAMT");
					double tcintosnpaAmnt = (Double) map.get("TCINTNPAOSAMT");
					Double totalDisbAmnt = (Double) map.get("TOTALDISBAMNT");
					double tcosnpaAmnt = tcprinosnpaAmnt + tcintosnpaAmnt;
					// //System.out.println("total tcosnpaAmnt:"+tcosnpaAmnt);
					String repaidStr = "0.0";
					String repaidIntStr = "0.0";
					String tcosStr = "0.0";
					String dateStr = "";
					String totalDisbAmntStr = "0.0";
					if (dsbrsDt != null) {
						dateStr = simpleDateFormat.format(dsbrsDt);
					}
					if (repaidAmnt != null) {
						repaidStr = repaidAmnt.toString();
					}
					if (repaidIntAmnt != null) {
						repaidIntStr = repaidIntAmnt.toString();
					}

					tcosStr = String.valueOf(tcosnpaAmnt);

					Double approvedAmt = (Double) map
							.get(ClaimConstants.CGPAN_APPRVD_AMNT);
					String approvedAmtStr = "0.0";
					if (approvedAmt != null) {
						approvedAmtStr = approvedAmt.toString();
					}
					if (totalDisbAmnt != null) {
						totalDisbAmntStr = totalDisbAmnt.toString();
					}

					claimForm.setLastDisbursementDate((new StringBuilder())
							.append("key-").append(j).toString(), dateStr);
					claimForm.setTcprincipal(
							(new StringBuilder()).append("key-").append(j)
									.toString(), repaidStr);
					claimForm.setTcInterestCharge(
							(new StringBuilder()).append("key-").append(j)
									.toString(), repaidIntStr);
					claimForm.setTcOsAsOnDateOfNPA((new StringBuilder())
							.append("key-").append(j).toString(), tcosStr);
					claimForm.setAppApprovedAmt(
							(new StringBuilder()).append("key-").append(j)
									.toString(), approvedAmtStr);
					claimForm.setTotalDisbursementAmt((new StringBuilder())
							.append("key-").append(j).toString(),
							totalDisbAmntStr);
				}
			}
			/* SETTING WORKING CAPITAL DETAILS INTO FORM */
			for (int i = 0; i < wccgs.size(); i++) {
				int j = i + 1;
				Map map = (HashMap) wccgs.elementAt(i);
				if (map != null) {
					double wcosnpaAmnt = 0.0;
					double wcprinosnpaAmnt = (Double) map.get("WCPRINNPAOSAMT");
					double wcintosnpaAmnt = (Double) map.get("WCINTNPAOSAMT");
					wcosnpaAmnt = wcprinosnpaAmnt + wcintosnpaAmnt;
					// //System.out.println("total wcosnpaAmnt:"+wcosnpaAmnt);
					String wcosStr = "";
					// if(wcosnpaAmnt != null){
					wcosStr = String.valueOf(wcosnpaAmnt);
					// }else{
					// wcosStr = "0.0";
					// }
					claimForm.setWcOsAsOnDateOfNPA((new StringBuilder())
							.append("key-").append(j).toString(), wcosStr);
				}
			}

			/*
			 * Map newCgMap = null; Vector newCgVector = new Vector(); for(int
			 * i=0;i<cgpnDetails.size();i++){ Map m1 =
			 * (HashMap)cgpnDetails.elementAt(i); for(int j=0;j<cgs.size();j++){
			 * if(m1.get("CGPAN").equals(cgs.elementAt(j))){ newCgMap = new
			 * HashMap(); newCgMap.put("CGPAN",m1.get("CGPAN"));
			 * newCgMap.put("ApprovedAmount",m1.get("ApprovedAmount"));
			 * //newCgMap
			 * .put("EnhancedApprovedAmount",m1.get("EnhancedApprovedAmount"));
			 * //newCgMap.put("LoanType",m1.get("LoanType"));
			 * //newCgMap.put("GUARANTEESTARTDT",m1.get("GUARANTEESTARTDT"));
			 * //newCgMap
			 * .put("APPLICATION_STATUS",m1.get("APPLICATION_STATUS"));
			 * newCgVector.add(newCgMap); break; } } }
			 */

			Map newCgMap = null;
			Vector newCgVector = new Vector();

			for (int i = 0; i < cgpnDetails.size(); i++) {
				Map m1 = (HashMap) cgpnDetails.elementAt(i);
				for (int j = 0; j < tccgs.size(); j++) {
					Map m2 = (HashMap) tccgs.elementAt(j);
					if (m1.get("CGPAN").equals(m2.get("CGPAN"))) {
						newCgMap = new HashMap();
						newCgMap.put("CGPAN", m1.get("CGPAN"));
						newCgMap.put("ApprovedAmount",
								m1.get(ClaimConstants.CGPAN_APPRVD_AMNT));
						newCgMap.put("EnhancedApprovedAmount",
								m1.get("EnhancedApprovedAmount"));
						newCgMap.put("LoanType", m1.get("LoanType"));
						newCgMap.put("GUARANTEESTARTDT",
								m1.get("GUARANTEESTARTDT"));
						newCgMap.put("APPLICATION_STATUS",
								m1.get("APPLICATION_STATUS"));
						/*
						 * String microFlag = (String)m2.get("MICROFLAG");
						 * String schemeName = (String)m2.get("SCHEME"); String
						 * womanOperated = (String)m2.get("GENDER"); String
						 * sancFlag = (String)m2.get("SANCFLAG"); String nerFlag
						 * = (String)m2.get("NERFLAG"); double tcIssued =
						 * (Double)m2.get("TCISSUEDAMT"); double wcFBIssued =
						 * (Double)m2.get("WCFBISSUED"); double wcNFBIssued =
						 * (Double)m2.get("WCNFBISSUED");
						 * 
						 * newCgMap.put("MICROFLAG",microFlag);
						 * newCgMap.put("SCHEME",schemeName);
						 * newCgMap.put("GENDER",womanOperated);
						 * newCgMap.put("SANCFLAG",sancFlag);
						 * newCgMap.put("NERFLAG",nerFlag);
						 * newCgMap.put("TCISSUEDAMT",tcIssued);
						 * newCgMap.put("WCFBISSUED",wcFBIssued);
						 * newCgMap.put("WCNFBISSUED",wcNFBIssued);
						 * newCgMap.put("CLAIMELIGAMT","");
						 */
						newCgVector.add(newCgMap);
						break;
					}
				}
			}

			for (int i = 0; i < cgpnDetails.size(); i++) {
				Map m1 = (HashMap) cgpnDetails.elementAt(i);
				for (int j = 0; j < wccgs.size(); j++) {
					Map m2 = (HashMap) wccgs.elementAt(j);
					if (m1.get("CGPAN").equals(m2.get("CGPAN"))) {
						newCgMap = new HashMap();
						newCgMap.put("CGPAN", m1.get("CGPAN"));
						newCgMap.put("ApprovedAmount",
								m1.get(ClaimConstants.CGPAN_APPRVD_AMNT));
						newCgMap.put("EnhancedApprovedAmount",
								m1.get("EnhancedApprovedAmount"));
						newCgMap.put("LoanType", m1.get("LoanType"));
						newCgMap.put("GUARANTEESTARTDT",
								m1.get("GUARANTEESTARTDT"));
						newCgMap.put("APPLICATION_STATUS",
								m1.get("APPLICATION_STATUS"));
						/*
						 * String microFlag = (String)m2.get("MICROFLAG");
						 * String schemeName = (String)m2.get("SCHEME"); String
						 * womanOperated = (String)m2.get("GENDER"); String
						 * sancFlag = (String)m2.get("SANCFLAG"); String nerFlag
						 * = (String)m2.get("NERFLAG"); double tcIssued =
						 * (Double)m2.get("TCISSUEDAMT"); double wcFBIssued =
						 * (Double)m2.get("WCFBISSUED"); double wcNFBIssued =
						 * (Double)m2.get("WCNFBISSUED");
						 * 
						 * newCgMap.put("MICROFLAG",microFlag);
						 * newCgMap.put("SCHEME",schemeName);
						 * newCgMap.put("GENDER",womanOperated);
						 * newCgMap.put("SANCFLAG",sancFlag);
						 * newCgMap.put("NERFLAG",nerFlag);
						 * newCgMap.put("TCISSUEDAMT",tcIssued);
						 * newCgMap.put("WCFBISSUED",wcFBIssued);
						 * newCgMap.put("WCNFBISSUED",wcNFBIssued);
						 * newCgMap.put("CLAIMELIGAMT","");
						 */
						newCgVector.add(newCgMap);
						break;
					}
				}
			}
			// //System.out.println("newCgVector:" + newCgVector);

			cgpnDetails.removeAllElements();
			// //System.out.println("cgpnDetails after compare with cgs:"+cgpnDetails.size());
			// //System.out.println("newCgVector after compare with cgs:"+newCgVector.size());
			if (tccgs != null && wccgs != null && tccgs.size() == 0
					&& wccgs.size() == 0) {
				session.setAttribute("CurrentPage",
						"getBorrowerId.do?method=setBankId");
				throw new NoDataException(
						"There are no Loan Account(s) for this Borrower or the existing Loan Account(s) may have been closed.");
			}
			claimForm.setRecoveryModes(recoveryModes);
			claimForm.setDateOfSeekingOTS(otsReqDateStr);
			claimForm.setCgpnDetails(newCgVector);
			claimForm.setSecurityDetails(details);
			/*
			 * Making the change for removing the CGPANs for which Gurantee Fee
			 * has not been paid
			 */
			// for(int i =0; i <cgpnDetails.size(); i++)
			// {
			// hashmap = (HashMap)cgpnDetails.elementAt(i);
			// if(hashmap == null)
			// {
			// continue;
			// }
			// java.util.Date guarStartDt =
			// (java.util.Date)hashmap.get(ClaimConstants.CLM_GUARANTEE_START_DT);
			// Log.log(Log.INFO,"ClaimAction","proceedFromRecoveryFilterPage","******* Printing guarStartDt :"
			// + guarStartDt);
			// if(guarStartDt == null)
			// {
			// cgpnDetails.remove(i);
			// i--;
			// }
			// else
			// {
			// continue;
			// }
			// }
			// claimForm.setCgpnDetails(cgpnDetails);

			/*
			 * Clearing up the Collection Objects
			 */
			// npadetails.clear();
			npadetails = null;
			// hashmap.clear();
			hashmap = null;
			// cgpnDetails.clear();
			cgpnDetails = null;
			// cgpans.clear();
			cgpans = null;
			// tccgpans.clear();
			tccgpans = null;
			// wccgpans.clear();
			wccgpans = null;
			// recoveryModes.clear();
			recoveryModes = null;
			return mapping.findForward("claimdetails");
		}
		if ((((String) session.getAttribute("mainMenu")).equals(MenuOptions
				.getMenu(MenuOptions.CP_CLAIM_FOR)))
				&& ((session.getAttribute("subMenuItem")).equals(MenuOptions
						.getMenu(MenuOptions.CP_CLAIM_FOR_SECOND_INSTALLMENT)))) {
			ClaimActionForm claimForm = (ClaimActionForm) form;
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String memberId = claimForm.getMemberId();
			String bankId = memberId.substring(0, 4);
			String zoneId = memberId.substring(4, 8);
			String branchId = memberId.substring(8, 12);
			String borrowerId = ((claimForm.getBorrowerID()).toUpperCase())
					.trim();
			Log.log(Log.INFO, "ClaimAction", "proceedFromRecoveryFilterPage()",
					"Printing the Borrower Id :" + borrowerId);
			HashMap firstClmDtl = null;
			recoveryflag = claimForm.getRecoveryFlag();
			if (recoveryflag.equals(ClaimConstants.DISBRSMNT_YES_FLAG)) {
				Log.log(Log.INFO, "ClaimAction",
						"proceedFromRecoveryFilterPage",
						"Redirecting to Recovery Details Page of Guarantee Maintenance");

				session.setAttribute(ClaimConstants.CLM_MEMBER_ID, memberId);
				session.setAttribute(ClaimConstants.CLM_BORROWER_ID, borrowerId);
				claimForm.setRecoveryFlag(ClaimConstants.DISBRSMNT_NO_FLAG);
				return mapping.findForward("recoverydetails");
			}
			if (recoveryflag.equals(ClaimConstants.DISBRSMNT_NO_FLAG)) {
				ClaimsProcessor processor = new ClaimsProcessor();
				java.util.Date settDate = null;
				firstClmDtl = processor.getFirstClmDtlForBid(bankId, zoneId,
						branchId, borrowerId);
				if (firstClmDtl
						.containsKey(ClaimConstants.CLM_SETTLMNT_FIRST_SETTLMNT_DT)) {
					settDate = (java.util.Date) firstClmDtl
							.get(ClaimConstants.CLM_SETTLMNT_FIRST_SETTLMNT_DT);
					firstClmDtl
							.remove(ClaimConstants.CLM_SETTLMNT_FIRST_SETTLMNT_DT);
					if (settDate != null) {
						firstClmDtl.put(
								ClaimConstants.CLM_SETTLMNT_FIRST_SETTLMNT_DT,
								sdf.format(settDate));
					}
				}
				String clmRefNum = (String) firstClmDtl
						.get(ClaimConstants.CLM_CLAIM_REF_NUMBER);
				java.util.Date recallNoticeDt = (java.util.Date) firstClmDtl
						.get(ClaimConstants.CLM_RECALL_NOTICE);
				String recallNoticeDtStr = sdf.format(recallNoticeDt);
				claimForm.setDateOfRecallNotice(recallNoticeDtStr);
				Log.log(Log.INFO, "ClaimAction",
						"proceedFromRecoveryFilterPage",
						"Retrieving member details for a member id......");
				MemberInfo memberDetails = processor
						.getMemberInfoDetails(memberId);
				Log.log(Log.INFO, "ClaimAction",
						"proceedFromRecoveryFilterPage",
						"Successfully retrieved member details for a member id......");
				Log.log(Log.INFO, "ClaimAction",
						"proceedFromRecoveryFilterPage",
						"Retrieving borrower details for a borrower id......");
				BorrowerInfo borrowerDetails = processor
						.getBorrowerDetails(borrowerId);
				Log.log(Log.INFO, "ClaimAction",
						"proceedFromRecoveryFilterPage",
						"Successfully retrieved details for a borrower id......");
				Log.log(Log.INFO, "ClaimAction",
						"proceedFromRecoveryFilterPage",
						"Retrieving NPA details for a borrower id......");
				java.util.Date npaClassifiedDate = null;
				java.util.Date npaReportingDate = null;
				String reasonfornpa = null;
				String willfulDefaulter = null;
				ClaimDetail clmdtl = processor
						.getDetailsForClaimRefNumber(clmRefNum);
				Vector tcVec = clmdtl.getTcDetails();
				Vector wcVec = clmdtl.getWcDetails();
				HashMap npadetails = processor
						.isNPADetailsAvailable(borrowerId);
				if (npadetails == null) {
					throw new NoDataException("NPA Details not available.");
				}
				if (npadetails.size() == 0) {
					throw new NoDataException("NPA Details not available.");
				}
				HashMap npadtlMainTable = (HashMap) npadetails
						.get(ClaimConstants.CLM_MAIN_TABLE);
				if (npadtlMainTable != null) {
					if (npadtlMainTable.size() > 0) {
						willfulDefaulter = (String) npadtlMainTable
								.get(ClaimConstants.WILLFUL_DEFAULTER);
						if (willfulDefaulter != null) {
							npaClassifiedDate = (java.util.Date) npadtlMainTable
									.get(ClaimConstants.NPA_CLASSIFIED_DT);
							if (npaClassifiedDate != null) {
								npadtlMainTable
										.remove(ClaimConstants.NPA_CLASSIFIED_DT);
								// SimpleDateFormat sdf = new
								// SimpleDateFormat("dd/MM/yyyy");
								String npaClassifiedDateStr = sdf
										.format(npaClassifiedDate);
								npadtlMainTable.put(
										ClaimConstants.NPA_CLASSIFIED_DT,
										npaClassifiedDateStr);
							}
							npaReportingDate = (java.util.Date) npadtlMainTable
									.get(ClaimConstants.NPA_REPORTING_DT);
							if (npaReportingDate != null) {
								npadtlMainTable
										.remove(ClaimConstants.NPA_REPORTING_DT);
								// SimpleDateFormat sdf = new
								// SimpleDateFormat("dd/MM/yyyy");
								String npaReportingDateStr = sdf
										.format(npaReportingDate);
								npadtlMainTable.put(
										ClaimConstants.NPA_REPORTING_DT,
										npaReportingDateStr);
							}
							reasonfornpa = (String) npadtlMainTable
									.get(ClaimConstants.REASONS_FOR_TURNING_NPA);
							willfulDefaulter = (String) npadtlMainTable
									.get(ClaimConstants.WILLFUL_DEFAULTER);
						}
					}
				}

				HashMap npadtltemptable = (HashMap) npadetails
						.get(ClaimConstants.CLM_TEMP_TABLE);
				// String willfulDefaulter = null;
				// boolean npaDtlsAvl = false;
				if (npadtltemptable != null) {
					if (npadtltemptable.size() > 0) {
						willfulDefaulter = (String) npadtltemptable
								.get(ClaimConstants.WILLFUL_DEFAULTER);
						if (willfulDefaulter != null) {
							npaClassifiedDate = (java.util.Date) npadtltemptable
									.get(ClaimConstants.NPA_CLASSIFIED_DT);
							if (npaClassifiedDate != null) {
								npadtltemptable
										.remove(ClaimConstants.NPA_CLASSIFIED_DT);
								// SimpleDateFormat sdf = new
								// SimpleDateFormat("dd/MM/yyyy");
								String npaClassifiedDateStr = sdf
										.format(npaClassifiedDate);
								npadtltemptable.put(
										ClaimConstants.NPA_CLASSIFIED_DT,
										npaClassifiedDateStr);
							}
							npaReportingDate = (java.util.Date) npadtltemptable
									.get(ClaimConstants.NPA_REPORTING_DT);
							if (npaReportingDate != null) {
								npadtltemptable
										.remove(ClaimConstants.NPA_REPORTING_DT);
								// SimpleDateFormat sdf = new
								// SimpleDateFormat("dd/MM/yyyy");
								String npaReportingDateStr = sdf
										.format(npaReportingDate);
								npadtltemptable.put(
										ClaimConstants.NPA_REPORTING_DT,
										npaReportingDateStr);
							}
							reasonfornpa = (String) npadtltemptable
									.get(ClaimConstants.REASONS_FOR_TURNING_NPA);
							willfulDefaulter = (String) npadtltemptable
									.get(ClaimConstants.WILLFUL_DEFAULTER);
						}
					}
				}
				// String willfulDefaulter =
				// (String)npadetails.get(ClaimConstants.WILLFUL_DEFAULTER);
				Log.log(Log.INFO, "ClaimAction",
						"proceedFromRecoveryFilterPage",
						"Successfully retrieved npa details for a borrower id......");
				Log.log(Log.INFO, "ClaimAction",
						"proceedFromRecoveryFilterPage",
						"Retrieving legal details for a borrower id......");
				LegalProceedingsDetail legaldetails = processor
						.isLegalProceedingsDetailAvl(borrowerId);
				Log.log(Log.INFO, "ClaimAction",
						"proceedFromRecoveryFilterPage",
						"Successfully retrieved legal details for a borrower id......");
				Vector cgpnDetails = processor.getCGPANDetailsForBorrowerId(
						borrowerId, memberId);
				Log.log(Log.INFO, "ClaimAction",
						"proceedFromRecoveryFilterPage",
						"Successfully retrieved the cgpan details for the borrower id......");
				Vector cgpans = new Vector();
				Vector tccgpans = new Vector();
				Vector wccgpans = new Vector();
				Vector clmsFiled = (Vector) processor.getAllClaimsFiled();
				String clmRefNumber = null;
				for (int k = 0; k < clmsFiled.size(); k++) {
					HashMap mp = (HashMap) clmsFiled.elementAt(k);
					if (mp == null) {
						continue;
					}
					String mpMemberId = (String) mp
							.get(ClaimConstants.CLM_MEMBER_ID);
					String mpbid = (String) mp
							.get(ClaimConstants.CLM_BORROWER_ID);
					if (mpMemberId == null) {
						continue;
					}
					if (mpbid == null) {
						continue;
					}
					if ((mpMemberId.equals(memberId))
							&& (mpbid.equals(borrowerId))) {
						clmRefNumber = (String) mp
								.get(ClaimConstants.CLM_CLAIM_REF_NUMBER);
					}
				}
				HashMap totalRecDtls = processor
						.isRecoveryDetailsAvailable(clmRefNumber);
				Vector recDetails = (Vector) totalRecDtls
						.get(ClaimConstants.CLM_MAIN_TABLE);
				ArrayList cgpansForClmRefNum = processor
						.getAllCgpansForClmRefNum(clmRefNum);
				// Map recDtlsMap = new HashMap();
				HashMap hashmap = null;
				for (int i = 0; i < cgpnDetails.size(); i++) {
					hashmap = (HashMap) cgpnDetails.elementAt(i);
					Log.log(Log.INFO, "ClaimAction", "proceedFromRecoveryPage",
							"Printing HashMap :" + hashmap);
					if (hashmap != null) {
						if (hashmap.containsKey(ClaimConstants.CLM_CGPAN)) {
							String cgpan = (String) hashmap
									.get(ClaimConstants.CLM_CGPAN);
							if ((cgpan != null) && (!(cgpan.equals("")))) {
								if (!cgpansForClmRefNum.contains(cgpan)) {
									continue;
								}

								if ((hashmap
										.get(ClaimConstants.CGPAN_LOAN_TYPE))
										.equals(ClaimConstants.CGPAN_TC_LOAN_TYPE)) {
									HashMap mp = new HashMap();
									java.util.Date dsbrsDt = (java.util.Date) hashmap
											.get(ClaimConstants.CLM_LAST_DISBURSEMENT_DT);
									Log.log(Log.INFO, "ClaimAction",
											"proceedFromRecoveryFilterPage",
											"******* Printing dsbrsDt :"
													+ dsbrsDt);
									Double repaidAmnt = (Double) hashmap
											.get(ClaimConstants.TOTAL_AMNT_REPAID);
									mp.put(ClaimConstants.CLM_CGPAN, cgpan);
									mp.put(ClaimConstants.CLM_LAST_DISBURSEMENT_DT,
											dsbrsDt);
									mp.put(ClaimConstants.TOTAL_AMNT_REPAID,
											repaidAmnt);
									java.util.Date guarStartDt = (java.util.Date) hashmap
											.get(ClaimConstants.CLM_GUARANTEE_START_DT);
									if (guarStartDt == null) {
										continue;
									}
									if (!tccgpans.contains(mp)) {
										tccgpans.addElement(mp);
									}
								}
								if ((hashmap
										.get(ClaimConstants.CGPAN_LOAN_TYPE))
										.equals(ClaimConstants.CGPAN_WC_LOAN_TYPE)) {
									java.util.Date guarStartDt = (java.util.Date) hashmap
											.get(ClaimConstants.CLM_GUARANTEE_START_DT);
									if (guarStartDt == null) {
										continue;
									}
									if (!wccgpans.contains(cgpan)) {
										wccgpans.addElement(cgpan);
									}
								}
								if ((hashmap
										.get(ClaimConstants.CGPAN_LOAN_TYPE))
										.equals(ClaimConstants.CGPAN_CC_LOAN_TYPE)) {
									HashMap mp = new HashMap();
									java.util.Date dsbrsDt = (java.util.Date) hashmap
											.get(ClaimConstants.CLM_LAST_DISBURSEMENT_DT);
									Log.log(Log.INFO, "ClaimAction",
											"proceedFromRecoveryFilterPage",
											"******* Printing dsbrsDt :"
													+ dsbrsDt);
									Double repaidAmnt = (Double) hashmap
											.get(ClaimConstants.TOTAL_AMNT_REPAID);
									mp.put(ClaimConstants.CLM_CGPAN, cgpan);
									mp.put(ClaimConstants.CLM_LAST_DISBURSEMENT_DT,
											dsbrsDt);
									mp.put(ClaimConstants.TOTAL_AMNT_REPAID,
											repaidAmnt);
									java.util.Date guarStartDt = (java.util.Date) hashmap
											.get(ClaimConstants.CLM_GUARANTEE_START_DT);
									if (guarStartDt == null) {
										continue;
									}
									if (!tccgpans.contains(mp)) {
										tccgpans.addElement(mp);
									}
								}

								if (!cgpans.contains(cgpan)) {
									cgpans.addElement(cgpan);

								}
								HashMap recMap = null;
								for (int m = 0; m < recDetails.size(); m++) {
									int count = m;
									recMap = (HashMap) recDetails.elementAt(m);
									if (recMap == null) {
										continue;
									}
									String pan = (String) recMap
											.get(ClaimConstants.CLM_CGPAN);
									if (pan == null) {
										continue;
									}
									if (pan.equals(cgpan)) {
										count++;
										if ((pan.indexOf(ClaimConstants.CGPAN_TC_LOAN_TYPE)) > 0) {
											if (recMap
													.containsKey(ClaimConstants.CLM_REC_TC_PRINCIPAL)) {
												String principalAmntStr = ((Double) recMap
														.get(ClaimConstants.CLM_REC_TC_PRINCIPAL))
														.toString();
												claimForm.setCgpandetails(
														"tcprincipal$recovery#key-"
																+ count,
														principalAmntStr);
											}
											if (recMap
													.containsKey(ClaimConstants.CLM_REC_TC_INTEREST)) {
												String interestChargesStr = ((Double) recMap
														.get(ClaimConstants.CLM_REC_TC_INTEREST))
														.toString();
												claimForm.setCgpandetails(
														"tcInterestCharges$recovery#key-"
																+ count,
														interestChargesStr);
											}
										}
										if ((pan.indexOf(ClaimConstants.CGPAN_WC_LOAN_TYPE)) > 0) {
											if (recMap
													.containsKey(ClaimConstants.CLM_REC_WC_AMOUNT)) {
												String wcAmountStr = ((Double) recMap
														.get(ClaimConstants.CLM_REC_WC_AMOUNT))
														.toString();
												claimForm.setCgpandetails(
														"wcAmount$recovery#key-"
																+ count,
														wcAmountStr);
											}
											if (recMap
													.containsKey(ClaimConstants.CLM_REC_WC_OTHER)) {
												String wcOthersStr = ((Double) recMap
														.get(ClaimConstants.CLM_REC_WC_OTHER))
														.toString();
												claimForm.setCgpandetails(
														"wcOtherCharges$recovery#key-"
																+ count,
														wcOthersStr);
											}
										}
										if (recMap
												.containsKey(ClaimConstants.CLM_REC_MODE)) {
											String modeOfRec = (String) recMap
													.get(ClaimConstants.CLM_REC_MODE);
											claimForm.setCgpandetails(
													"recoveryMode$recovery#key-"
															+ count, modeOfRec);
										}
									}
								}
							}
						}
						// hashmap.clear();
						hashmap = null;
					}
				}

				Vector perGaurDtls = processor
						.getFirstClmPerGaurSecDtls(clmRefNumber);
				String particularFlag = null;
				String landValue = "";
				String networthValue = "";
				String machineValue = "";
				String bldgValue = "";
				String ofmaValue = "";
				String currAssetsValue = "";
				String othersValue = "";
				String reasonForReduction = "";
				Double doubleObj = null;

				for (int i = 0; i < perGaurDtls.size(); i++) {
					HashMap mp = (HashMap) perGaurDtls.elementAt(i);
					if (mp == null) {
						continue;
					}
					particularFlag = (String) mp
							.get(ClaimConstants.CLM_SECURITY_PARTICULAR_FLAG);
					if ((particularFlag != null)
							&& (particularFlag
									.equals(ClaimConstants.CLM_SAPGD_AS_ON_SANCTION_CODE))) {
						// Fetching Land Value
						doubleObj = (Double) mp
								.get(ClaimConstants.CLM_SAPGD_PARTICULAR_LAND);
						if ((doubleObj != null)
								&& (doubleObj.doubleValue() != 0.0)) {
							landValue = doubleObj.toString();
						}
						claimForm.setAsOnDtOfSanctionDtl(
								ClaimConstants.CLM_SAPGD_PARTICULAR_LAND,
								landValue);

						// Fetching Guarantor Networth
						doubleObj = (Double) mp
								.get(ClaimConstants.CLM_SAPGD_NETWORTH_OF_GUARANTOR);
						if ((doubleObj != null)
								&& (doubleObj.doubleValue() != 0.0)) {
							networthValue = doubleObj.toString();
						}
						claimForm.setAsOnDtOfSanctionDtl(
								ClaimConstants.CLM_SAPGD_NETWORTH_OF_GUARANTOR,
								networthValue);

						// Fetching Reason for Reduction
						reasonForReduction = (String) mp
								.get(ClaimConstants.CLM_SAPGD_REASONS_FOR_REDUCTION);
						claimForm.setAsOnDtOfSanctionDtl(
								ClaimConstants.CLM_SAPGD_REASONS_FOR_REDUCTION,
								reasonForReduction);

						// Fetching Building Value
						doubleObj = (Double) mp
								.get(ClaimConstants.CLM_SAPGD_PARTICULAR_BLDG);
						if ((doubleObj != null)
								&& (doubleObj.doubleValue() != 0.0)) {
							bldgValue = doubleObj.toString();
						}
						claimForm.setAsOnDtOfSanctionDtl(
								ClaimConstants.CLM_SAPGD_PARTICULAR_BLDG,
								bldgValue);

						// Fetching Machine Value
						doubleObj = (Double) mp
								.get(ClaimConstants.CLM_SAPGD_PARTICULAR_MC);
						if ((doubleObj != null)
								&& (doubleObj.doubleValue() != 0.0)) {
							machineValue = doubleObj.toString();
						}
						claimForm.setAsOnDtOfSanctionDtl(
								ClaimConstants.CLM_SAPGD_PARTICULAR_MC,
								machineValue);

						// Fetching OFMA Value
						doubleObj = (Double) mp
								.get(ClaimConstants.CLM_SAPGD_PARTICULAR_OTHER_FIXED_MOV_ASSETS);
						if ((doubleObj != null)
								&& (doubleObj.doubleValue() != 0.0)) {
							ofmaValue = doubleObj.toString();
						}
						claimForm
								.setAsOnDtOfSanctionDtl(
										ClaimConstants.CLM_SAPGD_PARTICULAR_OTHER_FIXED_MOV_ASSETS,
										ofmaValue);

						// Fetching Current Assets
						doubleObj = (Double) mp
								.get(ClaimConstants.CLM_SAPGD_PARTICULAR_CUR_ASSETS);
						if ((doubleObj != null)
								&& (doubleObj.doubleValue() != 0.0)) {
							currAssetsValue = doubleObj.toString();
						}
						claimForm.setAsOnDtOfSanctionDtl(
								ClaimConstants.CLM_SAPGD_PARTICULAR_CUR_ASSETS,
								currAssetsValue);

						// Fetching Others Value
						doubleObj = (Double) mp
								.get(ClaimConstants.CLM_SAPGD_PARTICULAR_OTHERS);
						if ((doubleObj != null)
								&& (doubleObj.doubleValue() != 0.0)) {
							othersValue = doubleObj.toString();
						}
						claimForm.setAsOnDtOfSanctionDtl(
								ClaimConstants.CLM_SAPGD_PARTICULAR_OTHERS,
								othersValue);
					}
					if ((particularFlag != null)
							&& (particularFlag
									.equals(ClaimConstants.CLM_SAPGD_AS_ON_NPA_CODE))) {
						// Fetching Land Value
						doubleObj = (Double) mp
								.get(ClaimConstants.CLM_SAPGD_PARTICULAR_LAND);
						if ((doubleObj != null)
								&& (doubleObj.doubleValue() != 0.0)) {
							landValue = doubleObj.toString();
						}
						claimForm.setAsOnDtOfNPA(
								ClaimConstants.CLM_SAPGD_PARTICULAR_LAND,
								landValue);

						// Fetching Guarantor Networth
						doubleObj = (Double) mp
								.get(ClaimConstants.CLM_SAPGD_NETWORTH_OF_GUARANTOR);
						if ((doubleObj != null)
								&& (doubleObj.doubleValue() != 0.0)) {
							networthValue = doubleObj.toString();
						}
						claimForm.setAsOnDtOfNPA(
								ClaimConstants.CLM_SAPGD_NETWORTH_OF_GUARANTOR,
								networthValue);

						// Fetching Reason for Reduction
						reasonForReduction = (String) mp
								.get(ClaimConstants.CLM_SAPGD_REASONS_FOR_REDUCTION);
						claimForm.setAsOnDtOfNPA(
								ClaimConstants.CLM_SAPGD_REASONS_FOR_REDUCTION,
								reasonForReduction);

						// Fetching Building Value
						doubleObj = (Double) mp
								.get(ClaimConstants.CLM_SAPGD_PARTICULAR_BLDG);
						if ((doubleObj != null)
								&& (doubleObj.doubleValue() != 0.0)) {
							bldgValue = doubleObj.toString();
						}
						claimForm.setAsOnDtOfNPA(
								ClaimConstants.CLM_SAPGD_PARTICULAR_BLDG,
								bldgValue);

						// Fetching Machine Value
						doubleObj = (Double) mp
								.get(ClaimConstants.CLM_SAPGD_PARTICULAR_MC);
						if ((doubleObj != null)
								&& (doubleObj.doubleValue() != 0.0)) {
							machineValue = doubleObj.toString();
						}
						claimForm.setAsOnDtOfNPA(
								ClaimConstants.CLM_SAPGD_PARTICULAR_MC,
								machineValue);

						// Fetching OFMA Value
						doubleObj = (Double) mp
								.get(ClaimConstants.CLM_SAPGD_PARTICULAR_OTHER_FIXED_MOV_ASSETS);
						if ((doubleObj != null)
								&& (doubleObj.doubleValue() != 0.0)) {
							ofmaValue = doubleObj.toString();
						}
						claimForm
								.setAsOnDtOfNPA(
										ClaimConstants.CLM_SAPGD_PARTICULAR_OTHER_FIXED_MOV_ASSETS,
										ofmaValue);

						// Fetching Current Assets
						doubleObj = (Double) mp
								.get(ClaimConstants.CLM_SAPGD_PARTICULAR_CUR_ASSETS);
						if ((doubleObj != null)
								&& (doubleObj.doubleValue() != 0.0)) {
							currAssetsValue = doubleObj.toString();
						}
						claimForm.setAsOnDtOfNPA(
								ClaimConstants.CLM_SAPGD_PARTICULAR_CUR_ASSETS,
								currAssetsValue);

						// Fetching Others Value
						doubleObj = (Double) mp
								.get(ClaimConstants.CLM_SAPGD_PARTICULAR_OTHERS);
						if ((doubleObj != null)
								&& (doubleObj.doubleValue() != 0.0)) {
							othersValue = doubleObj.toString();
						}
						claimForm.setAsOnDtOfNPA(
								ClaimConstants.CLM_SAPGD_PARTICULAR_OTHERS,
								othersValue);
					}
					if ((particularFlag != null)
							&& (particularFlag
									.equals(ClaimConstants.CLM_SAPGD_AS_ON_LODGE_OF_CLM))) {
						// Fetching Land Value
						doubleObj = (Double) mp
								.get(ClaimConstants.CLM_SAPGD_PARTICULAR_LAND);
						if ((doubleObj != null)
								&& (doubleObj.doubleValue() != 0.0)) {
							landValue = doubleObj.toString();
						}
						claimForm.setAsOnLodgemntOfCredit(
								ClaimConstants.CLM_SAPGD_PARTICULAR_LAND,
								landValue);

						// Fetching Guarantor Networth
						doubleObj = (Double) mp
								.get(ClaimConstants.CLM_SAPGD_NETWORTH_OF_GUARANTOR);
						if ((doubleObj != null)
								&& (doubleObj.doubleValue() != 0.0)) {
							networthValue = doubleObj.toString();
						}
						claimForm.setAsOnLodgemntOfCredit(
								ClaimConstants.CLM_SAPGD_NETWORTH_OF_GUARANTOR,
								networthValue);

						// Fetching Reason for Reduction
						reasonForReduction = (String) mp
								.get(ClaimConstants.CLM_SAPGD_REASONS_FOR_REDUCTION);
						claimForm.setAsOnLodgemntOfCredit(
								ClaimConstants.CLM_SAPGD_REASONS_FOR_REDUCTION,
								reasonForReduction);

						// Fetching Building Value
						doubleObj = (Double) mp
								.get(ClaimConstants.CLM_SAPGD_PARTICULAR_BLDG);
						if ((doubleObj != null)
								&& (doubleObj.doubleValue() != 0.0)) {
							bldgValue = doubleObj.toString();
						}
						claimForm.setAsOnLodgemntOfCredit(
								ClaimConstants.CLM_SAPGD_PARTICULAR_BLDG,
								bldgValue);

						// Fetching Machine Value
						doubleObj = (Double) mp
								.get(ClaimConstants.CLM_SAPGD_PARTICULAR_MC);
						if ((doubleObj != null)
								&& (doubleObj.doubleValue() != 0.0)) {
							machineValue = doubleObj.toString();
						}
						claimForm.setAsOnLodgemntOfCredit(
								ClaimConstants.CLM_SAPGD_PARTICULAR_MC,
								machineValue);

						// Fetching OFMA Value
						doubleObj = (Double) mp
								.get(ClaimConstants.CLM_SAPGD_PARTICULAR_OTHER_FIXED_MOV_ASSETS);
						if ((doubleObj != null)
								&& (doubleObj.doubleValue() != 0.0)) {
							ofmaValue = doubleObj.toString();
						}
						claimForm
								.setAsOnLodgemntOfCredit(
										ClaimConstants.CLM_SAPGD_PARTICULAR_OTHER_FIXED_MOV_ASSETS,
										ofmaValue);

						// Fetching Current Assets
						doubleObj = (Double) mp
								.get(ClaimConstants.CLM_SAPGD_PARTICULAR_CUR_ASSETS);
						if ((doubleObj != null)
								&& (doubleObj.doubleValue() != 0.0)) {
							currAssetsValue = doubleObj.toString();
						}
						claimForm.setAsOnLodgemntOfCredit(
								ClaimConstants.CLM_SAPGD_PARTICULAR_CUR_ASSETS,
								currAssetsValue);

						// Fetching Others Value
						doubleObj = (Double) mp
								.get(ClaimConstants.CLM_SAPGD_PARTICULAR_OTHERS);
						if ((doubleObj != null)
								&& (doubleObj.doubleValue() != 0.0)) {
							othersValue = doubleObj.toString();
						}
						claimForm.setAsOnLodgemntOfCredit(
								ClaimConstants.CLM_SAPGD_PARTICULAR_OTHERS,
								othersValue);
					}
				}

				/*
				 * // Retrieving the primary security and guarantor details.
				 * String totalLandValueStr = null; String totalNetworthStr =
				 * null; String totalMachineValueStr = null; String
				 * totalBldgValueStr = null; String totalOFMAValueStr = null;
				 * String totalCurrAssetsValueStr = null; String
				 * totalOthersValueStr = null; Map securityMap = new HashMap();
				 * HashMap details =
				 * processor.getPrimarySecurityAndNetworthOfGuarantors
				 * (borrowerId); Double totalLandValDouble =
				 * (Double)details.get(
				 * ClaimConstants.CLM_SAPGD_PARTICULAR_LAND);
				 * if(totalLandValDouble != null) {
				 * if(totalLandValDouble.doubleValue() > 0.0) {
				 * totalLandValueStr = totalLandValDouble.toString(); } else {
				 * totalLandValueStr = ""; } } Double totalNetworthDouble =
				 * (Double
				 * )details.get(ClaimConstants.CLM_SAPGD_NETWORTH_OF_GUARANTOR);
				 * if(totalNetworthDouble != null) {
				 * if(totalNetworthDouble.doubleValue() > 0.0) {
				 * totalNetworthStr = totalNetworthDouble.toString(); } else {
				 * totalNetworthStr = ""; } } Double totalMachineValDouble =
				 * (Double)details.get(ClaimConstants.CLM_SAPGD_PARTICULAR_MC);
				 * if(totalMachineValDouble != null) {
				 * if(totalMachineValDouble.doubleValue() > 0.0) {
				 * totalMachineValueStr = totalMachineValDouble.toString(); }
				 * else { totalMachineValueStr = ""; } } Double
				 * totalBldgValueDouble =
				 * (Double)details.get(ClaimConstants.CLM_SAPGD_PARTICULAR_BLDG
				 * ); if(totalBldgValueDouble != null) {
				 * if(totalBldgValueDouble.doubleValue() > 0.0) {
				 * totalBldgValueStr = totalBldgValueDouble.toString(); } else {
				 * totalBldgValueStr = ""; } } Double totalOFMAValDouble =
				 * (Double)details.get(ClaimConstants.
				 * CLM_SAPGD_PARTICULAR_OTHER_FIXED_MOV_ASSETS);
				 * if(totalOFMAValDouble != null) {
				 * if(totalOFMAValDouble.doubleValue() > 0.0) {
				 * totalOFMAValueStr = totalOFMAValDouble.toString(); } else {
				 * totalOFMAValueStr = ""; } } Double totalCurrAssetsValDouble =
				 * (
				 * Double)details.get(ClaimConstants.CLM_SAPGD_PARTICULAR_CUR_ASSETS
				 * ); if(totalCurrAssetsValDouble != null) {
				 * if(totalCurrAssetsValDouble.doubleValue() > 0.0) {
				 * totalCurrAssetsValueStr =
				 * totalCurrAssetsValDouble.toString(); } else {
				 * totalCurrAssetsValueStr = ""; } } Double totalOthersValDouble
				 * =
				 * (Double)details.get(ClaimConstants.CLM_SAPGD_PARTICULAR_OTHERS
				 * ); if(totalOthersValDouble != null) {
				 * if(totalOthersValDouble.doubleValue() > 0.0) {
				 * totalOthersValueStr = totalOthersValDouble.toString(); } else
				 * { totalOthersValueStr = ""; } }
				 * securityMap.put(ClaimConstants.CLM_SAPGD_PARTICULAR_LAND,
				 * totalLandValueStr);
				 * securityMap.put(ClaimConstants.CLM_SAPGD_NETWORTH_OF_GUARANTOR
				 * , totalNetworthStr);
				 * securityMap.put(ClaimConstants.CLM_SAPGD_PARTICULAR_MC,
				 * totalMachineValueStr);
				 * securityMap.put(ClaimConstants.CLM_SAPGD_PARTICULAR_BLDG,
				 * totalBldgValueStr); securityMap.put(ClaimConstants.
				 * CLM_SAPGD_PARTICULAR_OTHER_FIXED_MOV_ASSETS,
				 * totalOFMAValueStr);
				 * securityMap.put(ClaimConstants.CLM_SAPGD_PARTICULAR_CUR_ASSETS
				 * , totalCurrAssetsValueStr);
				 * securityMap.put(ClaimConstants.CLM_SAPGD_PARTICULAR_OTHERS,
				 * totalOthersValueStr);
				 * claimForm.setAsOnDtOfSanctionDtl(securityMap);
				 */
				/*
				 * Retrieving the Claim Applied Amounts for CGPAN(s) of the
				 * Borrower
				 */
				Vector clmAppliedAmnts = processor.getClaimAppliedAmounts(
						borrowerId, ClaimConstants.FIRST_INSTALLMENT);
				Vector updateClmDtls = new Vector();
				String thiscgpn = null;
				for (int i = 0; i < cgpnDetails.size(); i++) {
					HashMap dtl = (HashMap) cgpnDetails.elementAt(i);
					if (dtl != null) {
						thiscgpn = (String) dtl.get(ClaimConstants.CLM_CGPAN);
						if (thiscgpn != null) {
							for (int j = 0; j < clmAppliedAmnts.size(); j++) {
								HashMap clmAppliedDtl = (HashMap) clmAppliedAmnts
										.elementAt(j);
								String cgpnInAppliedAmntsVec = null;
								if (clmAppliedDtl != null) {
									cgpnInAppliedAmntsVec = (String) clmAppliedDtl
											.get(ClaimConstants.CLM_CGPAN);
									if (cgpnInAppliedAmntsVec != null) {
										if (cgpnInAppliedAmntsVec
												.equals(thiscgpn)) {
											double clmAppliedAmnt = 0.0;
											Double clmAppAmntObj = (Double) clmAppliedDtl
													.get(ClaimConstants.CGPAN_CLM_APPLIED_AMNT);
											if (clmAppAmntObj != null) {
												clmAppliedAmnt = clmAppAmntObj
														.doubleValue();
											} else {
												clmAppliedAmnt = 0.0;
											}

											// Setting the Claim Applied Amount
											dtl.put(ClaimConstants.CGPAN_CLM_APPLIED_AMNT,
													new Double(clmAppliedAmnt));
											if (!updateClmDtls.contains(dtl)) {
												updateClmDtls.addElement(dtl);
											}

											// Clearing up the HashMap
											// clmAppliedDtl.clear();
											clmAppliedDtl = null;
											break;
										} else {
											continue;
										}
									} else {
										continue;
									}
								} else {
									continue;
								}
							}
						} else {
							continue;
						}
					} else {
						continue;
					}
					// dtl.clear();
					dtl = null;
				}

				/*
				 * Getting Claim Settlement Details and setting in the form
				 */
				HashMap settlmntDetails = processor
						.getClaimSettlementDetailForBorrower(borrowerId);
				double firstSettlementAmnt = 0.0;
				Double firstSettlementAmntObj = (Double) settlmntDetails
						.get(ClaimConstants.CLM_SETTLMNT_FIRST_SETTLMNT_AMNT);
				if (firstSettlementAmntObj != null) {
					firstSettlementAmnt = firstSettlementAmntObj.doubleValue();
				}
				java.util.Date firstSettlementDt = (java.util.Date) settlmntDetails
						.get(ClaimConstants.CLM_SETTLMNT_FIRST_SETTLMNT_DT);

				HashMap dtl = null;
				Vector finalUpdatedDtls = new Vector();
				for (int i = 0; i < updateClmDtls.size(); i++) {
					dtl = (HashMap) updateClmDtls.elementAt(i);
					if (dtl != null) {
						dtl.put(ClaimConstants.CLM_SETTLMNT_FIRST_SETTLMNT_AMNT,
								new Double(firstSettlementAmnt));
						dtl.put(ClaimConstants.CLM_SETTLMNT_FIRST_SETTLMNT_DT,
								firstSettlementDt);
						if (!finalUpdatedDtls.contains(dtl)) {
							finalUpdatedDtls.addElement(dtl);
						}
						// dtl.clear();
						dtl = null;
					}
				}

				Vector recoveryModes = processor.getAllRecoveryModes();

				// Retrieving the OTS Details
				Vector otsDtls = processor.getOTSDetails(borrowerId);
				java.util.Date otsReqDate = null;
				String otsReqDateStr = null;
				for (int k = 0; k < otsDtls.size(); k++) {
					HashMap dtlMap = (HashMap) otsDtls.elementAt(k);
					if (dtlMap == null) {
						continue;
					}
					otsReqDate = (java.util.Date) dtlMap
							.get(ClaimConstants.CLM_OTS_REQUEST_DATE);
					sdf = new SimpleDateFormat("dd/MM/yyyy");
					otsReqDateStr = sdf.format(otsReqDate);
				}

				claimForm.setMemberDetails(memberDetails);
				claimForm.setBorrowerDetails(borrowerDetails);
				if (npadtltemptable != null) {
					if (npadtltemptable.size() > 0) {
						claimForm.setNpaDetails(npadtltemptable);
					}
				} else if (npadtlMainTable != null) {
					if (npadtlMainTable.size() > 0) {
						claimForm.setNpaDetails(npadtlMainTable);
					}
				}
				claimForm.setWilfullDefaulter(willfulDefaulter);
				int tcCounter = 0;
				if (tccgpans != null) {
					tcCounter = tccgpans.size();
				}
				claimForm.setTcCounter(tcCounter);
				for (int i = 0; i < tccgpans.size(); i++) {
					HashMap outMap = (HashMap) tccgpans.elementAt(i);
					if (outMap == null) {
						continue;
					}
					String outCgpan = (String) outMap
							.get(ClaimConstants.CLM_CGPAN);
					for (int j = 0; j < tcVec.size(); j++) {
						HashMap innerMap = (HashMap) tcVec.elementAt(j);
						if (innerMap == null) {
							continue;
						}
						String innerCgpan = (String) innerMap
								.get(ClaimConstants.CLM_CGPAN);
						if (innerCgpan == null) {
							continue;
						}
						if (!innerCgpan.equals(outCgpan)) {
							continue;
						}
						double principalAmnt = ((Double) innerMap
								.get(ClaimConstants.CLM_REC_TC_PRINCIPAL))
								.doubleValue();
						// //System.out.println("Line number 3636 - principalAmnt:"+principalAmnt);
						double interestOtherCharges = ((Double) innerMap
								.get(ClaimConstants.CLM_REC_TC_INTEREST))
								.doubleValue();
						// //System.out.println("interestOtherCharges:"+interestOtherCharges);
						double osAsOnNPADate = ((Double) innerMap
								.get(ClaimConstants.CLM_OS_AS_ON_NPA))
								.doubleValue();
						double osAsInCivilSuit = ((Double) innerMap
								.get(ClaimConstants.CLM_OS_AS_ON_CIVIL_SUIT))
								.doubleValue();
						double osAsInFirstClmLodgement = ((Double) innerMap
								.get(ClaimConstants.CLM_OS_AS_IN_CLM_LODGMNT))
								.doubleValue();

						outMap.put(ClaimConstants.CLM_REC_TC_PRINCIPAL,
								new Double(principalAmnt));
						outMap.put(ClaimConstants.CLM_REC_TC_INTEREST,
								new Double(interestOtherCharges));
						outMap.put(ClaimConstants.CLM_OS_AS_ON_NPA, new Double(
								osAsOnNPADate));
						outMap.put(ClaimConstants.CLM_OS_AS_ON_CIVIL_SUIT,
								new Double(osAsInCivilSuit));
						outMap.put(ClaimConstants.CLM_OS_AS_IN_CLM_LODGMNT,
								new Double(osAsInFirstClmLodgement));
					}
					Log.log(Log.INFO, "ClaimAction", "proceedFromRecoveryPage",
							"Printing OutMap :" + outMap);
				}
				claimForm.setTcCgpansVector(tccgpans);
				for (int i = 0; i < tccgpans.size(); i++) {
					int j = i + 1;
					HashMap map = (HashMap) tccgpans.elementAt(i);
					if (map == null) {
						continue;
					}
					java.util.Date dsbrsDt = (java.util.Date) map
							.get(ClaimConstants.CLM_LAST_DISBURSEMENT_DT);
					java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat(
							"dd/MM/yyyy");
					String dateStr = "";
					if (dsbrsDt != null) {
						dateStr = simpleDateFormat.format(dsbrsDt);
					}
					Double repaidAmnt = (Double) map
							.get(ClaimConstants.TOTAL_AMNT_REPAID);
					String repaidStr = "";
					if (repaidAmnt != null) {
						repaidStr = repaidAmnt.toString();
					} else {
						repaidStr = "0.0";
					}
					String principalAmntStr = ((Double) map
							.get(ClaimConstants.CLM_REC_TC_PRINCIPAL))
							.toString();
					String interestOtherChargesStr = ((Double) map
							.get(ClaimConstants.CLM_REC_TC_INTEREST))
							.toString();
					String osAsOnNPADate = ((Double) map
							.get(ClaimConstants.CLM_OS_AS_ON_NPA)).toString();
					String osAsInCivilSuit = ((Double) map
							.get(ClaimConstants.CLM_OS_AS_ON_CIVIL_SUIT))
							.toString();
					String osAsInFirstClmLodgement = ((Double) map
							.get(ClaimConstants.CLM_OS_AS_IN_CLM_LODGMNT))
							.toString();
					claimForm.setLastDisbursementDate("key-" + j, dateStr);
					claimForm.setTcprincipal("key-" + j, principalAmntStr);
					claimForm.setTcInterestCharge("key-" + j,
							interestOtherChargesStr);
					claimForm.setTcOsAsOnDateOfNPA("key-" + j, osAsOnNPADate);
					claimForm.setTcOsAsStatedInCivilSuit("key-" + j,
							osAsInCivilSuit);
					claimForm.setTcOsAsOnLodgementOfClaim("key-" + j,
							osAsInFirstClmLodgement);
				}
				int wcCounter = 0;
				if (wccgpans != null) {
					wcCounter = wccgpans.size();
				}
				claimForm.setWcCounter(wcCounter);
				Vector updateWCDtls = new Vector();
				String outerCgpan = null;
				for (int i = 0; i < wccgpans.size(); i++) {
					outerCgpan = (String) wccgpans.elementAt(i);
					if (outerCgpan == null) {
						continue;
					}
					for (int j = 0; j < wcVec.size(); j++) {
						HashMap innerMap = (HashMap) wcVec.elementAt(j);
						if (innerMap == null) {
							continue;
						}
						String innerCgpan = (String) innerMap
								.get(ClaimConstants.CLM_CGPAN);
						if (innerCgpan == null) {
							continue;
						}
						if (!innerCgpan.equals(outerCgpan)) {
							continue;
						}
						updateWCDtls.addElement(innerMap);
					}
				}

				claimForm.setWcCgpansVector(updateWCDtls);
				if ((tccgpans != null) && (wccgpans != null)) {
					if ((tccgpans.size() == 0) && (wccgpans.size() == 0)) {
						session.setAttribute("CurrentPage",
								"getBorrowerId.do?method=setBankId");
						throw new NoDataException(
								"There are no Loan Account(s) for this Borrower or the existing Loan Account(s) may have been closed.");
					}
				}
				claimForm.setCgpansVector(cgpans);
				//System.out.println("2 setCgpansVector executed");
				claimForm.setRecoveryModes(recoveryModes);
				claimForm.setCgpnDetails(finalUpdatedDtls);
				Log.log(Log.INFO, "ClaimAction",
						"proceedFromRecoveryFilterPage",
						"Setting Legal Details in the claim form!");
				Log.log(Log.INFO,
						"ClaimAction",
						"proceedFromRecoveryFilterPage()",
						"legaldetails.getForumRecoveryProceedingsInitiated() :"
								+ legaldetails
										.getForumRecoveryProceedingsInitiated());
				String forumNameStr = legaldetails
						.getForumRecoveryProceedingsInitiated();
				if (forumNameStr != null) {
					if ((forumNameStr.equals("Civil Court"))
							|| (forumNameStr.equals("DRT"))
							|| (forumNameStr.equals("LokAdalat"))
							|| (forumNameStr
									.equals("Revenue Recovery Autority"))
							|| (forumNameStr.equals("Securitisation Act "))) {
						claimForm.setForumthrulegalinitiated(legaldetails
								.getForumRecoveryProceedingsInitiated());
					} else {
						claimForm.setForumthrulegalinitiated("Others");
						claimForm.setOtherforums(legaldetails
								.getForumRecoveryProceedingsInitiated());
					}
				}

				claimForm.setCaseregnumber(legaldetails.getSuitCaseRegNumber());
				claimForm.setProceedingsConcluded(legaldetails
						.getIsRecoveryProceedingsConcluded());
				Log.log(Log.INFO, "ClaimAction",
						"proceedFromRecoveryFilterPage",
						"Setting Legal Filing Date in the claim form!");
				// SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				java.util.Date legaldt_utilformat = legaldetails
						.getFilingDate();
				if (legaldt_utilformat != null) {
					claimForm.setLegaldate(sdf.format(legaldt_utilformat));
				} else {
					claimForm.setLegaldate("");
				}
				Log.log(Log.INFO, "ClaimAction",
						"proceedFromRecoveryFilterPage",
						"Setting Name of the Forum in the claim form!");
				claimForm.setNameofforum(legaldetails.getNameOfForum());
				claimForm.setLocation(legaldetails.getLocation());
				claimForm.setAmountclaimed(String.valueOf(legaldetails
						.getAmountClaimed()));
				claimForm.setCurrentstatusremarks(legaldetails
						.getCurrentStatusRemarks());
				claimForm.setClmDtlForFirstInstllmnt(firstClmDtl);
				// claimForm.setSecurityDetails(details);
				claimForm.setDateOfSeekingOTS(otsReqDateStr);
				claimForm.setPerGaurDtls(perGaurDtls);
				claimForm
						.setWhetherAccntWasWrittenOffBooks(ClaimConstants.DISBRSMNT_NO_FLAG);
				Log.log(Log.INFO, "ClaimAction",
						"proceedFromRecoveryFilterPage",
						"Proceeding to Second Claim Installment Page...");

				/*
				 * Clearing up the Collection Objects
				 */
				// firstClmDtl.clear();
				firstClmDtl = null;
				// npadetails.clear();
				npadetails = null;
				// settlmntDetails.clear();
				settlmntDetails = null;
				// cgpnDetails.clear();
				cgpnDetails = null;
				// cgpans.clear();
				cgpans = null;
				// tccgpans.clear();
				tccgpans = null;
				// wccgpans.clear();
				wccgpans = null;
				// clmAppliedAmnts.clear();
				clmAppliedAmnts = null;
				// updateClmDtls.clear();
				updateClmDtls = null;
				// finalUpdatedDtls.clear();
				finalUpdatedDtls = null;
				// recoveryModes.clear();
				recoveryModes = null;
				return mapping.findForward("secondclaimdetails");
			}
		}
		if (((String) session.getAttribute("mainMenu")).equals(MenuOptions
				.getMenu(MenuOptions.GM_PERIODIC_INFO))) {
			GMProcessor gmProcessor = new GMProcessor();
			ClaimActionForm claimForm = (ClaimActionForm) form;
			Recovery recoveryobject = null;
			String otsFlag = claimForm.getOtsFlag();
			if (otsFlag.equals(ClaimConstants.DISBRSMNT_YES_FLAG)) {
				String memberId = (String) claimForm.getMemberId();
				String borrowerId = ((claimForm.getBorrowerID()).toUpperCase())
						.trim();
				ClaimsProcessor processor = new ClaimsProcessor();
				Vector otsReqDtls = processor.getOTSRequestDetails(borrowerId);
				recoveryobject = (Recovery) session
						.getAttribute(ClaimConstants.CLM_RECOVERY_OBJECT);
				recoveryobject
						.setIsRecoveryByOTS(ClaimConstants.DISBRSMNT_NO_FLAG);
				gmProcessor.addRecoveryDetails(recoveryobject);
				claimForm.setMemberId(memberId);
				claimForm.setBorrowerID(borrowerId);
				claimForm.setOtsRequestDtls(otsReqDtls);
				String srcSubMenu = (String) session
						.getAttribute("subMenuItem");
				// //System.out.println("SRC SUB MENU :" + srcSubMenu);
				if (srcSubMenu.equals(MenuOptions
						.getMenu(MenuOptions.CP_CLAIM_FOR_FIRST_INSTALLMENT))) {
					session.setAttribute(ClaimConstants.CLM_FIRST_OTS_FROM_REC,
							new Boolean(true));
				}
				if (srcSubMenu.equals(MenuOptions
						.getMenu(MenuOptions.CP_CLAIM_FOR_SECOND_INSTALLMENT))) {
					session.setAttribute(
							ClaimConstants.CLM_SECOND_OTS_FROM_REC,
							new Boolean(true));
				}
				// Clearing up the Vector object
				// otsReqDtls.clear();
				otsReqDtls = null;
				srcSubMenu = null;
				return mapping.findForward("otsdetailspage");
			}
			if (otsFlag.equals(ClaimConstants.DISBRSMNT_NO_FLAG)) {
				recoveryobject = (Recovery) session
						.getAttribute(ClaimConstants.CLM_RECOVERY_OBJECT);
				recoveryobject
						.setIsRecoveryByOTS(ClaimConstants.DISBRSMNT_NO_FLAG);
				// gmProcessor.addRecoveryDetails(recoveryobject);
				session.setAttribute(ClaimConstants.CLM_RECOVERY_OBJECT, null);
				request.setAttribute(
						"message",
						"Recovery Details is not being saved. Please re-submit Recovery Details with OTS Flag as No and then file Claim Details.");
				return mapping.findForward("success");
			}
		}
		return null;
	}
//already method..middle changes
	//rajuk For maker part(first claim)
	public ActionForward addFirstClaimsPageDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
	    System.out.println("<br>===552===");
	   // ClaimActionForm claimFormobject = (ClaimActionForm)form;
	    System.out.println("<br>===554===");
	    User user = getUserInformation(request);
	    String bankId = user.getBankId();
	    String zoneId = user.getZoneId();
	    String branchId = user.getBranchId();
	    String memberId = bankId.concat(zoneId).concat(branchId);
	    System.out.println("rajukonkati"+memberId);
	    ClaimActionForm cpForm = (ClaimActionForm) form;
		ClaimsProcessor processor = new ClaimsProcessor();
		String clmrefno = (String)cpForm.getClmRefNumberNew();
	    //String clmRefNumber = (String)request.getParameter("clmrefnum");
	    System.out.println("rajuk1234567"+clmrefno);
	    ArrayList claimViewArray = new ArrayList();
	    ArrayList claimCheckListView = new ArrayList();
	    Connection connection = DBConnection.getConnection();
	    ResultSet claimviewResult=null;
		PreparedStatement claimviewStmt=null;
		ResultSet claimviewResult1=null;
		PreparedStatement claimviewStmt1=null;
		ClaimActionForm claimForm = (ClaimActionForm) form;
   
		 String  isNorthEastRegion="";
		 String  isWomenorNot="";
		
		//ended by rajuk
		
		try{
			
			String Query2="select IS_ELIG_ACT,IS_ELIG_ACT_COMM,WHET_CIBIL_DONE,WHET_CIBIL_DONE_COMM,IS_RAT_AS_PER_CGS,IS_RAT_AS_PER_CGS_COMM,IS_THIRD_COLLAT_TAKEN," +
    		"IS_THIRD_COLLAT_TAKEN_COMM,IS_NPA_DT_AS_PER_GUID,IS_NPA_DT_AS_PER_GUID_COMM,IS_CLM_OS_WRT_NPA_DT,IS_CLM_OS_WRT_NPA_DT_COMM,WHET_SERIOUS_DEFIC_INVOL," +
    		"WHET_SERIOUS_DEFIC_INVOL_COMM,WHET_MAJOR_DEFIC_INVOLVD,WHET_MAJOR_DEFIC_INVOLVD_COMM," +
    		"WHET_DEFIC_INVOL_BY_STAFF,WHET_DEFIC_INVOL_BY_STAFF_COMM,IS_INTERN_RAT_INVEST_GRAD," +
    		"IS_INTERN_RAT_INVEST_GRAD_COMM,IS_ALL_REC_IN_CLM_FORM,IS_ALL_REC_IN_CLM_FORM_COMM " +
    		"from claim_check_list a ,claim_detail_temp b where a.clm_ref_no=b.clm_ref_no and a.clm_ref_no=? "+"AND CLM_STATUS IN ('MR') and a.clm_ref_no=? ";
    
           //System.out.println("rrrrrrrrr"+Query2);
      
         claimviewStmt1 = connection.prepareStatement(Query2);
    	 claimviewStmt1.setString(1, clmrefno);
    	 claimviewStmt1.setString(2, clmrefno);
         claimviewResult1 = claimviewStmt1.executeQuery();
   	     ClaimApplication claimApplication = new ClaimApplication();
         //ClaimActionForm claimFormobj = new ClaimActionForm();
	    
         while(claimviewResult1.next()){
        	 
        	 String iseligact1=claimviewResult1.getString(1);
        	        	 
        	 String iseligactcomm1=claimviewResult1.getString(2);
        	 
             String whetcibildone1=claimviewResult1.getString(3);
        	 
        	 String whetcibildonecomm1=claimviewResult1.getString(4);
                
        	 String isrataspercgs1=claimviewResult1.getString(5);
        	 
        	 String isrataspercgscomm1=claimviewResult1.getString(6);
        	
        	 String isthirdcollattaken1=claimviewResult1.getString(7);
             
        	 String isthirdcollattakencomm1=claimviewResult1.getString(8);
        	
        	 String isnpadtasperguid1=claimviewResult1.getString(9);
        	 
        	 String isnpadtasperguidcomm1=claimviewResult1.getString(10);
        	
        	 String isclmoswrtnpadt1=claimviewResult1.getString(11);
        	 
        	 String isclmoswrtnpadtcomm1=claimviewResult1.getString(12);
        	 
        	 String whetseriousdeficinvol1=claimviewResult1.getString(13);
        	 
        	 String whetseriousdeficinvolcomm1=claimviewResult1.getString(14);
        	 String whetmajordeficinvolvd1=claimviewResult1.getString(15);
        	 String whetmajordeficinvolvdcomm1=claimviewResult1.getString(16);
        	 String whetdeficinvolbystaff1=claimviewResult1.getString(17);
        	 String whetdeficinvolbystaffcomm1=claimviewResult1.getString(18);
        	 String isinternratinvestgrad1=claimviewResult1.getString(19);
        	 String isinternratinvestgradcomm1=claimviewResult1.getString(20);
        	 String isallrecinclmform1=claimviewResult1.getString(21);
        	 String isallrecinclmformcomm1=claimviewResult1.getString(22);
        	       	 
        	
         
        	 claimForm.setIseligact(iseligact1);  
        	 claimForm.setIseligactcomm(iseligactcomm1);
        	 claimForm.setWhetcibildone(whetcibildone1);
        	 claimForm.setWhetcibildonecomm(whetcibildonecomm1);
        	 claimForm.setIsrataspercgs(isrataspercgs1);
        	 claimForm.setIsrataspercgscomm(isrataspercgscomm1);
        	 claimForm.setIsthirdcollattaken(isthirdcollattaken1);
        	 claimForm.setIsthirdcollattakencomm(isthirdcollattakencomm1);
        	 claimForm.setIsnpadtasperguid(isnpadtasperguid1);
        	 claimForm.setIsnpadtasperguidcomm(isnpadtasperguidcomm1);
        	 claimForm.setIsclmoswrtnpadt(isclmoswrtnpadt1);
        	 claimForm.setIsclmoswrtnpadtcomm(isclmoswrtnpadtcomm1);
        	 claimForm.setWhetseriousdeficinvol(whetseriousdeficinvol1);
        	 claimForm.setWhetseriousdeficinvolcomm(whetseriousdeficinvolcomm1);
        	 claimForm.setWhetmajordeficinvolvd(whetmajordeficinvolvd1);
        	 claimForm.setWhetmajordeficinvolvdcomm(whetmajordeficinvolvdcomm1);
        	 claimForm.setWhetdeficinvolbystaff(whetdeficinvolbystaff1);
        	 claimForm.setWhetdeficinvolbystaffcomm(whetdeficinvolbystaffcomm1);
        	 claimForm.setIsinternratinvestgrad(isinternratinvestgrad1);
        	 claimForm.setIsinternratinvestgradcomm(isinternratinvestgradcomm1);
        	 claimForm.setIsallrecinclmform(isallrecinclmform1);
        	 claimForm.setIsallrecinclmformcomm(isallrecinclmformcomm1);
         }
        	 System.out.println(claimForm.getIseligact());
        	 System.out.println(claimForm.getIseligactcomm());
        	 System.out.println("end");
        	 
        	 //rajuk
        	 

        	// if(!(clmrefno.equals(null)))
        	        	// {
        	        	 
        	              String query1  ="";
        	    		  String nameofofficial1="";
        	    		  String designation1="";
        	    		  String place1="";
        	    		  
        	    		  String Query3="select CLM_OFFICIAL_NAME,CLM_OFFICIAL_DESIGNATION,CLM_PLACE from claim_detail_temp  where clm_ref_no= ? ";	 
        	    		 
        	    		  claimviewStmt1 = connection.prepareStatement(Query3);
        	    		  claimviewStmt1.setString(1, clmrefno);
        	    	      claimviewResult1 = claimviewStmt1.executeQuery();
        	    	      //Statement stmt = connection.createStatement();
        	    	   //   stmt.setString(1, clmrefno);
        	   		     //  ResultSet rs=null;
        	   		     //  Connection conn = null;
        	    	
        	    			 while (claimviewResult1.next()) {
        	    				 nameofofficial1= claimviewResult1.getString(1);
        	    				 
        	    				 System.out.println("nameofofficial"+nameofofficial1);
        	    				 designation1= claimviewResult1.getString(2);
        	    				 System.out.println("Desig"+designation1);
        	    				 place1= claimviewResult1.getString(3);
        	    				 System.out.println("place"+place1);	
        	    				 
        	    				 claimForm.setNameOfOffi(nameofofficial1);
        	    	        	 claimForm.setDesigna(designation1);
        	    	        	 claimForm.setPlaces(designation1);
        	    				
        	    			 }
        	    			// stmt = ;
        	    			/* rs = stmt.executeQuery(Query3);
        	    			 while(rs.next())
        	    			{
        	    			
        	    				 claimApplication.setNameOfOfficial(rs.getString("CLM_OFFICIAL_NAME"));
        	    				 
        	    				 claimApplication.setDesignationOfOfficial(rs.getString("CLM_OFFICIAL_DESIGNATION"));
        	    				 claimApplication.setPlace(rs.getString("CLM_PLACE"));
        	    			
        	    			 }
        	    		  */
        	 			
        	        	// }
        	
//rajuk
 	 
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		//ClaimApplication claimApplication = new ClaimApplication();
		String mId = claimForm.getMemberId();
		String bid = ((claimForm.getBorrowerID()).toUpperCase()).trim();

		MemberInfo info = claimForm.getMemberDetails();
		// String phone =
		// info.getTelephone();//--------------------------------------------------------phone
		// String email =
		// info.getEmail();//------------------------------------------------------------email
		String officerName = claimForm.getDealingOfficerName();
		String microFlag = claimForm.getMicroCategory();
		String wilful = claimForm.getWilfullDefaulter();
		String fraudFlag = claimForm.getIsAcctFraud();
		String enquiryFlag = claimForm.getIsEnquiryConcluded();
		String mliInvolvementFlag = claimForm.getIsMLIInvolved();
		String reasonForRecall = claimForm.getReasonForIssueRecallNotice();
		String reasonForFilingSuit = claimForm.getReasonForFilingSuit();

		String inclusionOfReceipt = claimForm.getInclusionOfReciept();
		String confirmRecoveryFlag = claimForm.getConfirmRecoveryValues();
		String subsidyFlag = claimForm.getSubsidyFlag();
		String subsidyRcvd = claimForm.getIsSubsidyRcvdAfterNpa();
		String subsidyAdjusted = claimForm.getIsSubsidyAdjustedOnDues();
		double subsidyAmt = claimForm.getSubsidyAmt();
		String subsidyDateStr = claimForm.getSubsidyDate();
		Date subsidyDate = null;
		if (subsidyDateStr != null && !subsidyDateStr.equals("")) {
			subsidyDate = sdf.parse(subsidyDateStr, new ParsePosition(0));
		}
		String comments = claimForm.getMliCommentOnFinPosition();
		String finAssistance = claimForm.getDetailsOfFinAssistance();
		String creditSupport = claimForm.getCreditSupport();
		/*Changes for GST */
		/*String gstState = claimForm.getGstState();
		String gstNo = request.getParameter("gstNo");*/
		HttpSession session1=request.getSession();		
		session1.setAttribute("gstNo", request.getParameter("gstNo"));
		session1.setAttribute("gstStateCode", claimForm.getGstState());
		
		
		String bankFacility = claimForm.getBankFacilityDetail();
		String watchListPlace = claimForm.getPlaceUnderWatchList();
		String remarks = claimForm.getRemarksOnNpa();
		String assetPossessionDtStr = claimForm.getAssetPossessionDate();
		java.util.Date assetPossessionDt = null;
		if (assetPossessionDtStr != null && !assetPossessionDtStr.equals("")) {
			assetPossessionDt = sdf.parse(assetPossessionDtStr,
					new ParsePosition(0));
		}
		String nameofforum = claimForm.getNameofforum(); // ---------------------------------------------name
															// of forum
		String dtOfRecallNoticeStr = claimForm.getDateOfRecallNotice();
		java.util.Date recallnoticedate = sdf.parse(dtOfRecallNoticeStr,
				new ParsePosition(0));
		String forumthruwhichinitiated = claimForm.getForumthrulegalinitiated();
		if (forumthruwhichinitiated != null) {
			if (forumthruwhichinitiated.equals("Others")) {
				forumthruwhichinitiated = claimForm.getOtherforums();
			}
		}
		String casesuitregnumber = claimForm.getCaseregnumber();
		// //System.out.println("casesuitregnumber:" + casesuitregnumber);
		// String nameofforum = claimForm.getNameofforum();
		String dtOfFilingStr = claimForm.getLegaldate();
		java.util.Date filingdate = sdf.parse(dtOfFilingStr, new ParsePosition(
				0));
		String location = claimForm.getLocation();
		String amntClaimed1 = claimForm.getAmountclaimed();
		String currentstatusremarks = claimForm.getCurrentstatusremarks();
		String recoveryprocconcluded = claimForm.getProceedingsConcluded();
		String dtOfReleaseOfWCStr = claimForm.getDateOfReleaseOfWC();
		java.util.Date dateofreleasewc = null;
		if ((dtOfReleaseOfWCStr != null) && (dtOfReleaseOfWCStr.equals(""))) {
			dateofreleasewc = sdf.parse(dtOfReleaseOfWCStr,
					new ParsePosition(0));
		}
		String dtofseekingofots = claimForm.getDateOfSeekingOTS();
		Map tclastDisbursementDts = claimForm.getLastDisbursementDate();
		Set tclastDisbursementDtsSet = tclastDisbursementDts.keySet();
		Iterator tclastDisbursementDtsIterator = tclastDisbursementDtsSet
				.iterator();

		/*
		 * Uploading the attachements in the claim application
		 */
		String contextPath = null;
		String nameOfFile = null;
		// Retrieving the present time in yyyy-mm-dd format
		String currentTime = (new java.sql.Date(System.currentTimeMillis()))
				.toString();
		/*
		 * Uploading the Recall Issue Notice File Attachment bhu
		 */
		FormFile file = (FormFile) claimForm.getRecallnoticefilepath();
		if (file != null) {
			contextPath = request.getSession().getServletContext()
					.getRealPath("");
			nameOfFile = ClaimConstants.CLM_RECALL_NOTICE + currentTime
					+ ClaimConstants.CLM_NO_VALUE + mId + bid;
			// Uploading the file with the new name
			Log.log(Log.DEBUG, "ClaimAction", "addFirstClaimsPageDetails()",
					"Uploading the Recall Notice attachment....");

			byte[] data = file.getFileData();
			claimApplication.setRecallNoticeFileData(data);
			claimApplication.setRecalNoticeFileName(file.getFileName());

			 uploadFile(file,contextPath,nameOfFile);

			Log.log(Log.DEBUG, "ClaimAction", "addFirstClaimsPageDetails()",
					"File Name, File data " + file.getFileName() + "," + data);

			if (data != null) {
				Log.log(Log.DEBUG, "ClaimAction",
						"addFirstClaimsPageDetails()", "File data length "
								+ data.length);
			}

		}

		/*
		 * Uploading the Legal Attachment
		 */
		FormFile legalfile = (FormFile) claimForm.getLegalAttachmentPath();
		if (legalfile != null) {
			contextPath = request.getSession().getServletContext()
					.getRealPath("");
			nameOfFile = ClaimConstants.CLM_LEGAL_ATTACHMENT + currentTime
					+ ClaimConstants.CLM_NO_VALUE + mId + bid;
			// Uploading the file with the new name
			Log.log(Log.INFO, "ClaimAction", "addFirstClaimsPageDetails()",
					"Uploading Legal Details the attachment....");

			byte[] data = legalfile.getFileData();
			claimApplication.setLegalDetailsFileData(data);
			claimApplication.setLegalDetailsFileName(legalfile.getFileName());

			uploadFile(legalfile, contextPath, nameOfFile);

			Log.log(Log.DEBUG, "ClaimAction", "addFirstClaimsPageDetails()",
					"File Name, File data " + legalfile.getFileName() + ","
							+ data);

			if (data != null) {
				Log.log(Log.DEBUG, "ClaimAction",
						"addFirstClaimsPageDetails()", "File data length "
								+ data.length);
			}
		}

		
		
		TermLoanCapitalLoanDetail tcLoanDetail = null;
		
		Vector termCapitalDtls = new Vector();
		Vector cgpans = claimForm.getCgpansVector();
		Vector keys = new Vector();
		while (tclastDisbursementDtsIterator.hasNext()) {
			String key = (String) tclastDisbursementDtsIterator.next();
			if (!keys.contains(key)) {
				keys.addElement(key);
			}
		}

		for (int i = 0; i < keys.size(); i++) {
			tcLoanDetail = new TermLoanCapitalLoanDetail();

			// Capturing the key
			String key = (String) keys.elementAt(i);
			if (key == null) {
				continue;
			}
			tcLoanDetail.setCgpan((String) claimForm.getCgpandetails(key));

			// Capturing the last disbursement date
			String date = (String) claimForm.getLastDisbursementDate(key);
			tcLoanDetail.setLastDisbursementDate(sdf.parse(date,
					new ParsePosition(0)));

			// capturing total disbursed amount
			String disbAmnt = (String) claimForm.getTotalDisbursementAmt(key);
			if ("".equals(disbAmnt) || disbAmnt == null) {
				disbAmnt = "0.0";
			}
			tcLoanDetail.setTotaDisbAmnt(Double.parseDouble(disbAmnt));

			// Capturing Principal
			String principl = (String) claimForm.getTcprincipal(key);
			if ((principl.equals("")) || (principl == null)) {
				principl = "0.0";
			}
			tcLoanDetail.setPrincipalRepayment(Double.parseDouble(principl));

			// Capturing Interest Charges
			String interestCharges = (String) claimForm
					.getTcInterestCharge(key);
			if ((interestCharges.equals("")) || (interestCharges == null)) {
				interestCharges = "0.0";
			}
			tcLoanDetail.setInterestAndOtherCharges(Double
					.parseDouble(interestCharges));

			// Capturing Outstanding as on dt of npa
			String osnpa = (String) claimForm.getTcOsAsOnDateOfNPA(key);
			if ((osnpa.equals("")) || (osnpa == null)) {
				osnpa = "0.0";
			}
			tcLoanDetail.setOutstandingAsOnDateOfNPA(Double.parseDouble(osnpa));

			// Capturing outstanding as stated in civil suit
			String oscivilsuit = (String) claimForm
					.getTcOsAsStatedInCivilSuit(key);
			if ((oscivilsuit.equals("")) || (oscivilsuit == null)) {
				oscivilsuit = "0.0";
			}
			tcLoanDetail.setOutstandingStatedInCivilSuit(Double
					.parseDouble(oscivilsuit));

			// Capturing outstanding as on date of lodgement
			String oslodgement = (String) claimForm
					.getTcOsAsOnLodgementOfClaim(key);
			if ((oslodgement.equals("")) || (oslodgement == null)) {
				oslodgement = "0.0";
			}
			tcLoanDetail.setOutstandingAsOnDateOfLodgement(Double
					.parseDouble(oslodgement));

			// capturing tc claim flag
			String tcClaimFlag = (String) claimForm.getClaimFlagsTc(key);
			tcLoanDetail.setTcClaimFlag(tcClaimFlag);

			if (!termCapitalDtls.contains(tcLoanDetail)) {
				termCapitalDtls.addElement(tcLoanDetail);
			}
		}
		tcLoanDetail = null;
		// Capturing the Working Capital Applications Details
		ArrayList workingCapitalDtls = new ArrayList();
		Map wcOsAsOnNPADtls = claimForm.getWcOsAsOnDateOfNPA();
		Set wcOsAsOnNPADtlsSet = wcOsAsOnNPADtls.keySet();
		Iterator wcOsAsOnNPADtlsIterator = wcOsAsOnNPADtlsSet.iterator();
		Vector wcKeys = new Vector();
		while (wcOsAsOnNPADtlsIterator.hasNext()) {
			String key = (String) wcOsAsOnNPADtlsIterator.next();
			if (!wcKeys.contains(key)) {
				wcKeys.addElement(key);
			}
		}
		WorkingCapitalDetail wcDetail = null;
		for (int i = 0; i < wcKeys.size(); i++) {
			wcDetail = new WorkingCapitalDetail();
			wcDetail.setCgpan((String) claimForm.getWcCgpan((String) wcKeys
					.elementAt(i)));

			// Capturing outstanding as on npa
			String osnpawc = (String) claimForm
					.getWcOsAsOnDateOfNPA((String) wcKeys.elementAt(i));
			if ((osnpawc.equals("")) || (osnpawc == null)) {
				osnpawc = "0.0";
			}
			wcDetail.setOutstandingAsOnDateOfNPA(Double.parseDouble(osnpawc));

			// Capturing outstanding as in civil suit
			String oswccivilsuit = (String) claimForm
					.getWcOsAsStatedInCivilSuit((String) wcKeys.elementAt(i));
			if ((oswccivilsuit.equals("")) || (oswccivilsuit == null)) {
				oswccivilsuit = "0.0";
			}
			wcDetail.setOutstandingStatedInCivilSuit(Double
					.parseDouble(oswccivilsuit));

			// Capturing outstanding as on dt of lodgement
			String oswclodgement = (String) claimForm
					.getWcOsAsOnLodgementOfClaim((String) wcKeys.elementAt(i));
			if ((oswclodgement.equals("")) || (oswclodgement == null)) {
				oswclodgement = "0.0";
			}
			wcDetail.setOutstandingAsOnDateOfLodgement(Double
					.parseDouble(oswclodgement));

			// capturing wc claim flag
			String wcClaimFlag = (String) claimForm
					.getClaimFlagsWc((String) wcKeys.elementAt(i));
			wcDetail.setWcClaimFlag(wcClaimFlag);

			// Adding the workingcapitaldtl object to the vector
			if (!workingCapitalDtls.contains(wcDetail)) {
				workingCapitalDtls.add(wcDetail);
			}
		}
		wcDetail = null;
		// Capturing Security and Personal Guarantee Details
		Map asOnDtOfSanctionDtls = claimForm.getAsOnDtOfSanctionDtl();
		Set asOnDtOfSanctionDtlsSet = asOnDtOfSanctionDtls.keySet();
		Iterator asOnDtOfSanctionDtlsIterator = asOnDtOfSanctionDtlsSet
				.iterator();
		DtlsAsOnDateOfSanction dtlsAsOnSanctionDt = new DtlsAsOnDateOfSanction();
		while (asOnDtOfSanctionDtlsIterator.hasNext()) {
			String key = (String) asOnDtOfSanctionDtlsIterator.next();
			if ((key.trim())
					.equals(ClaimConstants.CLM_SAPGD_REASONS_FOR_REDUCTION)) {
				dtlsAsOnSanctionDt.setReasonsForReduction((String) claimForm
						.getAsOnDtOfSanctionDtl(key));
			}
			if ((key.trim())
					.equals(ClaimConstants.CLM_SAPGD_PARTICULAR_CUR_ASSETS)) {
				String currntassetssanction = (String) claimForm
						.getAsOnDtOfSanctionDtl(key);
				if ((currntassetssanction.equals(""))
						|| (currntassetssanction == null)) {
					currntassetssanction = "0.0";
				}
				dtlsAsOnSanctionDt.setValueOfCurrentAssets(Double
						.parseDouble(currntassetssanction));
			}
			if ((key.trim()).equals(ClaimConstants.CLM_SAPGD_PARTICULAR_LAND)) {
				String landasonsanction = (String) claimForm
						.getAsOnDtOfSanctionDtl(key);
				if ((landasonsanction.equals("")) || (landasonsanction == null)) {
					landasonsanction = "0.0";
				}
				dtlsAsOnSanctionDt.setValueOfLand(Double
						.parseDouble(landasonsanction));
			}
			if ((key.trim()).equals(ClaimConstants.CLM_SAPGD_PARTICULAR_MC)) {
				String machinesanction = (String) claimForm
						.getAsOnDtOfSanctionDtl(key);
				if ((machinesanction.equals("")) || (machinesanction == null)) {
					machinesanction = "0.0";
				}
				dtlsAsOnSanctionDt.setValueOfMachine(Double
						.parseDouble(machinesanction));
			}
			if ((key.trim()).equals(ClaimConstants.CLM_SAPGD_PARTICULAR_BLDG)) {
				String bldgsanction = (String) claimForm
						.getAsOnDtOfSanctionDtl(key);
				if ((bldgsanction.equals("")) || (bldgsanction == null)) {
					bldgsanction = "0.0";
				}
				dtlsAsOnSanctionDt.setValueOfBuilding(Double
						.parseDouble(bldgsanction));
			}
			if ((key.trim()).equals(ClaimConstants.CLM_SAPGD_PARTICULAR_OTHERS)) {
				String otherssanction = (String) claimForm
						.getAsOnDtOfSanctionDtl(key);
				if ((otherssanction.equals("")) || (otherssanction == null)) {
					otherssanction = "0.0";
				}
				dtlsAsOnSanctionDt.setValueOfOthers(Double
						.parseDouble(otherssanction));
			}
			if ((key.trim())
					.equals(ClaimConstants.CLM_SAPGD_PARTICULAR_OTHER_FIXED_MOV_ASSETS)) {
				String otherassets = (String) claimForm
						.getAsOnDtOfSanctionDtl(key);
				if ((otherassets.equals("")) || (otherassets == null)) {
					otherassets = "0.0";
				}
				dtlsAsOnSanctionDt.setValueOfOtherFixedMovableAssets(Double
						.parseDouble(otherassets));
			}
			if ((key.trim())
					.equals(ClaimConstants.CLM_SAPGD_NETWORTH_OF_GUARANTOR)) {
				String networthsanction = (String) claimForm
						.getAsOnDtOfSanctionDtl(key);
				if ((networthsanction.equals("")) || (networthsanction == null)) {
					networthsanction = "0.0";
				}
				dtlsAsOnSanctionDt.setNetworthOfGuarantors(Double
						.parseDouble(networthsanction));
			}
		}
		Map asOnNPADtls = claimForm.getAsOnDtOfNPA();
		Set asOnNPADtlsSet = asOnNPADtls.keySet();
		Iterator asOnNPADtlsIterator = asOnNPADtlsSet.iterator();
		DtlsAsOnDateOfNPA dtlsAsOnNPA = new DtlsAsOnDateOfNPA();
		while (asOnNPADtlsIterator.hasNext()) {
			String key = (String) asOnNPADtlsIterator.next();
			if ((key.trim())
					.equals(ClaimConstants.CLM_SAPGD_REASONS_FOR_REDUCTION)) {
				dtlsAsOnNPA.setReasonsForReduction((String) claimForm
						.getAsOnDtOfNPA(key));
			}
			if ((key.trim())
					.equals(ClaimConstants.CLM_SAPGD_PARTICULAR_CUR_ASSETS)) {
				String currentassetsnpa = (String) claimForm
						.getAsOnDtOfNPA(key);
				if ((currentassetsnpa.equals("")) || (currentassetsnpa == null)) {
					currentassetsnpa = "0.0";
				}
				dtlsAsOnNPA.setValueOfCurrentAssets(Double
						.parseDouble(currentassetsnpa));
			}
			if ((key.trim()).equals(ClaimConstants.CLM_SAPGD_PARTICULAR_LAND)) {
				String landnpa = (String) claimForm.getAsOnDtOfNPA(key);
				if ((landnpa.equals("")) || (landnpa == null)) {
					landnpa = "0.0";
				}
				dtlsAsOnNPA.setValueOfLand(Double.parseDouble(landnpa));
			}
			if ((key.trim()).equals(ClaimConstants.CLM_SAPGD_PARTICULAR_MC)) {
				String machinenpa = (String) claimForm.getAsOnDtOfNPA(key);
				if ((machinenpa.equals("")) || (machinenpa == null)) {
					machinenpa = "0.0";
				}
				dtlsAsOnNPA.setValueOfMachine(Double.parseDouble(machinenpa));
			}
			if ((key.trim()).equals(ClaimConstants.CLM_SAPGD_PARTICULAR_BLDG)) {
				String bldgnpa = (String) claimForm.getAsOnDtOfNPA(key);
				if ((bldgnpa.equals("")) || (bldgnpa == null)) {
					bldgnpa = "0.0";
				}
				dtlsAsOnNPA.setValueOfBuilding(Double.parseDouble(bldgnpa));
			}
			if ((key.trim()).equals(ClaimConstants.CLM_SAPGD_PARTICULAR_OTHERS)) {
				String othersnpa = (String) claimForm.getAsOnDtOfNPA(key);
				if ((othersnpa.equals("")) || (othersnpa == null)) {
					othersnpa = "0.0";
				}
				dtlsAsOnNPA.setValueOfOthers(Double.parseDouble(othersnpa));
			}
			if ((key.trim())
					.equals(ClaimConstants.CLM_SAPGD_PARTICULAR_OTHER_FIXED_MOV_ASSETS)) {
				String otherassets = (String) claimForm.getAsOnDtOfNPA(key);
				if ((otherassets.equals("")) || (otherassets == null)) {
					otherassets = "0.0";
				}
				dtlsAsOnNPA.setValueOfOtherFixedMovableAssets(Double
						.parseDouble(otherassets));
			}
			if ((key.trim())
					.equals(ClaimConstants.CLM_SAPGD_NETWORTH_OF_GUARANTOR)) {
				String networthnpa = (String) claimForm.getAsOnDtOfNPA(key);
				if ((networthnpa.equals("")) || (networthnpa == null)) {
					networthnpa = "0.0";
				}
				dtlsAsOnNPA.setNetworthOfGuarantors(Double
						.parseDouble(networthnpa));
			}
		}
		Map asOnLodgemntDtls = claimForm.getAsOnLodgemntOfCredit();
		Set asOnLodgemntDtlsSet = asOnLodgemntDtls.keySet();
		Iterator asOnLodgemntDtlsIterator = asOnLodgemntDtlsSet.iterator();
		DtlsAsOnLogdementOfClaim dtlsAsOnLodgemnt = new DtlsAsOnLogdementOfClaim();
		while (asOnLodgemntDtlsIterator.hasNext()) {
			String key = (String) asOnLodgemntDtlsIterator.next();
			if ((key.trim())
					.equals(ClaimConstants.CLM_SAPGD_REASONS_FOR_REDUCTION)) {
				dtlsAsOnLodgemnt.setReasonsForReduction((String) claimForm
						.getAsOnLodgemntOfCredit(key));
			}
			if ((key.trim())
					.equals(ClaimConstants.CLM_SAPGD_PARTICULAR_CUR_ASSETS)) {
				String currentassetslodgemnt = (String) claimForm
						.getAsOnLodgemntOfCredit(key);
				if ((currentassetslodgemnt.equals(""))
						|| (currentassetslodgemnt == null)) {
					currentassetslodgemnt = "0.0";
				}
				dtlsAsOnLodgemnt.setValueOfCurrentAssets(Double
						.parseDouble(currentassetslodgemnt));
			}
			if ((key.trim()).equals(ClaimConstants.CLM_SAPGD_PARTICULAR_LAND)) {
				String landlodgemnt = (String) claimForm
						.getAsOnLodgemntOfCredit(key);
				if ((landlodgemnt.equals("")) || (landlodgemnt == null)) {
					landlodgemnt = "0.0";
				}
				dtlsAsOnLodgemnt.setValueOfLand(Double
						.parseDouble(landlodgemnt));
			}
			if ((key.trim()).equals(ClaimConstants.CLM_SAPGD_PARTICULAR_MC)) {
				String machinelodgemnt = (String) claimForm
						.getAsOnLodgemntOfCredit(key);
				if ((machinelodgemnt.equals("")) || (machinelodgemnt == null)) {
					machinelodgemnt = "0.0";
				}
				dtlsAsOnLodgemnt.setValueOfMachine(Double
						.parseDouble(machinelodgemnt));
			}
			if ((key.trim()).equals(ClaimConstants.CLM_SAPGD_PARTICULAR_BLDG)) {
				String bldglodgemnt = (String) claimForm
						.getAsOnLodgemntOfCredit(key);
				if ((bldglodgemnt.equals("")) || (bldglodgemnt == null)) {
					bldglodgemnt = "0.0";
				}
				dtlsAsOnLodgemnt.setValueOfBuilding(Double
						.parseDouble(bldglodgemnt));
			}
			if ((key.trim()).equals(ClaimConstants.CLM_SAPGD_PARTICULAR_OTHERS)) {
				String otherslodgemnt = (String) claimForm
						.getAsOnLodgemntOfCredit(key);
				if ((otherslodgemnt.equals("")) || (otherslodgemnt == null)) {
					otherslodgemnt = "0.0";
				}
				dtlsAsOnLodgemnt.setValueOfOthers(Double
						.parseDouble(otherslodgemnt));
			}
			if ((key.trim())
					.equals(ClaimConstants.CLM_SAPGD_PARTICULAR_OTHER_FIXED_MOV_ASSETS)) {
				String otherassetslodgemnt = (String) claimForm
						.getAsOnLodgemntOfCredit(key);
				if ((otherassetslodgemnt.equals(""))
						|| (otherassetslodgemnt == null)) {
					otherassetslodgemnt = "0.0";
				}
				dtlsAsOnLodgemnt.setValueOfOtherFixedMovableAssets(Double
						.parseDouble(otherassetslodgemnt));
			}
			if ((key.trim())
					.equals(ClaimConstants.CLM_SAPGD_NETWORTH_OF_GUARANTOR)) {
				String networthlodgemnt = (String) claimForm
						.getAsOnLodgemntOfCredit(key);
				if ((networthlodgemnt.equals("")) || (networthlodgemnt == null)) {
					networthlodgemnt = "0.0";
				}
				dtlsAsOnLodgemnt.setNetworthOfGuarantors(Double
						.parseDouble(networthlodgemnt));
			}
		}
		// Capturing the recovery details
		Vector recoveryDetailsVector = new Vector();
		Map recoveryDetails = claimForm.getCgpandetails();
		Set recoveryDetailsSet = recoveryDetails.keySet();
		Iterator recoveryDetailsIterator = recoveryDetailsSet.iterator();
		StringTokenizer tokenzier = null;
		RecoveryDetails recDetails = null;

		Vector cgpanKeys = new Vector();
		Vector recoveryKeys = new Vector();
		 System.out.println("<br>===555===");
		String tcPrincipal = "tcprincipal".trim();
		String tcInterestCharges = "tcInterestCharges".trim();
		String wcAmount = "wcAmount".trim();
		String wcOtherCharges = "wcOtherCharges".trim();
		String recoveryMode = "recoveryMode".trim();
		String substring = "recovery#key-".trim();
		while (recoveryDetailsIterator.hasNext()) {
			String key = (String) recoveryDetailsIterator.next();
			if (key != null) {
				if ((key.indexOf(substring)) == 0) {
					if (!(cgpanKeys.contains(key))) {
						cgpanKeys.addElement(key);
					}

				}
				if ((key.indexOf(substring)) > 0) {
					if (!(recoveryKeys.contains(key))) {
						recoveryKeys.addElement(key);
					}
				}
			}
		}
		for (int j = 0; j < cgpanKeys.size(); j++) {
			recDetails = new RecoveryDetails();
			String cgpanKey = (String) cgpanKeys.elementAt(j);
			if (cgpanKey == null) {
				continue;
			}
			recDetails.setCgpan((String) claimForm.getCgpandetails(cgpanKey));
			// String cgpan = recDetails.getCgpan();

			for (int i = 0; i < recoveryKeys.size(); i++) {
				String recoveryKey = (String) recoveryKeys.elementAt(i);
				if (recoveryKey == null) {
					continue;
				}
				if (((recoveryKey.indexOf(tcPrincipal)) == 0)
						&& ((recoveryKey.indexOf(cgpanKey)) > 0)) {
					String recoverypricipaltc = (String) claimForm
							.getCgpandetails(recoveryKey);
					if ((recoverypricipaltc.equals(""))
							|| (recoverypricipaltc == null)) {
						recoverypricipaltc = "0.0";
					}
					recDetails.setTcPrincipal(Double
							.parseDouble(recoverypricipaltc));

				}
				if (((recoveryKey.indexOf(tcInterestCharges)) == 0)
						&& ((recoveryKey.indexOf(cgpanKey)) > 0)) {
					String recinterestchargestc = (String) claimForm
							.getCgpandetails(recoveryKey);
					if ((recinterestchargestc.equals(""))
							|| (recinterestchargestc == null)) {
						recinterestchargestc = "0.0";
					}
					recDetails.setTcInterestAndOtherCharges(Double
							.parseDouble(recinterestchargestc));
				}
				if (((recoveryKey.indexOf(wcAmount)) == 0)
						&& ((recoveryKey.indexOf(cgpanKey)) > 0)) {
					String wcamount = (String) claimForm
							.getCgpandetails(recoveryKey);
					if ((wcamount.equals("")) || (wcamount == null)) {
						wcamount = "0.0";
					}
					recDetails.setWcAmount(Double.parseDouble(wcamount));
				}
				if (((recoveryKey.indexOf(wcOtherCharges)) == 0)
						&& ((recoveryKey.indexOf(cgpanKey)) > 0)) {
					String wcothercharges = (String) claimForm
							.getCgpandetails(recoveryKey);
					if ((wcothercharges.equals("")) || (wcothercharges == null)) {
						wcothercharges = "0.0";
					}
					recDetails.setWcOtherCharges(Double
							.parseDouble(wcothercharges));
				}
				if (((recoveryKey.indexOf(recoveryMode)) == 0)
						&& ((recoveryKey.indexOf(cgpanKey)) > 0)) {
					String temp = (String) claimForm
							.getCgpandetails(recoveryKey);
					// //System.out.println("Recovery Mode :" + temp);
					recDetails.setModeOfRecovery(temp);
				}

			}
			String modeOfRec = recDetails.getModeOfRecovery();
			if ((modeOfRec != null) && (!modeOfRec.equals(""))) {
				if (!recoveryDetailsVector.contains(recDetails)) {
					recoveryDetailsVector.addElement(recDetails);
				}
			}
		}

		// Displaying and capturing the claim summary details
		ArrayList claimSummaryDtlsArrayList = new ArrayList();
		ClaimSummaryDtls summaryDtls = null;
		Map claimSummaryDtls = claimForm.getClaimSummaryDetails();
		Set claimSummaryDtlsSet = claimSummaryDtls.keySet();
		Iterator claimSummaryDtlsIterator = claimSummaryDtlsSet.iterator();
		// String claimString ="cgpan".trim();
		// String amountClaimedString ="amountClaimed".trim();
		// Vector summaryKeys = new Vector();
		double guaranteeApprovedAmt=0.0;
		while (claimSummaryDtlsIterator.hasNext()) {
			summaryDtls = new ClaimSummaryDtls();
			String key = (String) claimSummaryDtlsIterator.next();
			summaryDtls.setCgpan(key);
			RpDAO rpDAO = new RpDAO();
			double approvedAmt = rpDAO.getTotalSanctionedAmount(key);
			System.out.println("Cgpan Value:"+key);
			String amntClaimed = (String) claimForm.getClaimSummaryDetails(key);
			System.out.println("5348 - approvedAmt:"+approvedAmt+" amntClaimed:"+amntClaimed);
			guaranteeApprovedAmt = rpDAO.getTotalSanctionedAmountforCgpan(key);
			System.out.println("<br>===556===");
			 int isNorthEast = rpDAO.isNorthEastRegion(key);
			 
			 if(isNorthEast==1)
			 {
				 isNorthEastRegion="yes";
			 }
			 else
			 {
				 isNorthEastRegion="no";
			 }
			 
			 
			 int isWomen = rpDAO
				.isWomenorNot(key);
			 if(isWomen==1)
			 {
				 isWomenorNot="yes";
			 }
			 else
			 {
				 isWomenorNot="no";
			 }
			 
			 //raju end
			if ((amntClaimed.equals("")) || (amntClaimed == null)) {
				amntClaimed = "0.0";
				System.out.println("<br>===559===");
			}
			if (guaranteeApprovedAmt < Double.parseDouble(amntClaimed)) {
				throw new DatabaseException(
						"Claim Applied Amount for "
								+ key
								+ " is "
								+ amntClaimed
								+ " which can not be more than Loan / Limit Covered under CGFSI : "
								+ guaranteeApprovedAmt);
			}

			System.out.println("Line number 4445 key: "+key+" approvedAmt :"+approvedAmt+" amntClaimed: "+amntClaimed);

			summaryDtls.setAmount(Double.parseDouble(amntClaimed));
			if (!claimSummaryDtlsArrayList.contains(summaryDtls)) {
				claimSummaryDtlsArrayList.add(summaryDtls);
			}
		}
		// Building the claim application object
		claimApplication.setMemberId(mId);
		claimApplication.setBorrowerId(bid);
		System.out.println("ClamRefNum:"+claimForm.getClmRefNumberNew());
		claimApplication.setClaimRefNumber(claimForm.getClmRefNumberNew());
		System.out.println("bid:"+bid);
		claimApplication.setDateOfIssueOfRecallNotice(recallnoticedate);

		// Building the legalproceedingsdetail object
		LegalProceedingsDetail lpd = new LegalProceedingsDetail();
		lpd.setBorrowerId(bid);
		lpd.setCommHeadedByOfficerOrAbove(claimForm.getCommHeadedByOfficerOrAbove());
		lpd.setForumRecoveryProceedingsInitiated(forumthruwhichinitiated);
		lpd.setSuitCaseRegNumber(casesuitregnumber);
		lpd.setNameOfForum(nameofforum);
		lpd.setFilingDate(filingdate);
		lpd.setLocation(location);
		if ((amntClaimed1 != null) && (!(amntClaimed1.equals("")))) {
			lpd.setAmountClaimed(Double.parseDouble(amntClaimed1));
		}
		lpd.setCurrentStatusRemarks(currentstatusremarks);
		lpd.setIsRecoveryProceedingsConcluded(recoveryprocconcluded);

		claimApplication.setLegalProceedingsDetails(lpd);
		claimApplication.setDateOfReleaseOfWC(dateofreleasewc);
		SecurityAndPersonalGuaranteeDtls sapg = new SecurityAndPersonalGuaranteeDtls();
		sapg.setDetailsAsOnDateOfSanction(dtlsAsOnSanctionDt);
		sapg.setDetailsAsOnDateOfNPA(dtlsAsOnNPA);
		sapg.setDetailsAsOnDateOfLodgementOfClaim(dtlsAsOnLodgemnt);
		claimApplication.setSecurityAndPersonalGuaranteeDtls(sapg);

		java.util.Date dtofseekofotsutilformat = null;
		if (dtofseekingofots != null && dtofseekingofots.equals("")) {
			dtofseekofotsutilformat = sdf.parse(dtofseekingofots,
					new ParsePosition(0));
		}
		claimApplication.setDateOfSeekingOTS(dtofseekofotsutilformat);
		claimApplication.setRecoveryDetails(recoveryDetailsVector);
		claimApplication.setTermCapitalDtls(termCapitalDtls);
		claimApplication.setWorkingCapitalDtls(workingCapitalDtls);
		claimApplication.setClaimSummaryDtls(claimSummaryDtlsArrayList);

		claimApplication.setMemberDetails(info);
		claimApplication.setUnitAssistedMSE(microFlag);
		claimApplication.setWilful(wilful);
		claimApplication.setFraudFlag(fraudFlag);
		claimApplication.setEnquiryFlag(enquiryFlag);
		claimApplication.setMliInvolvementFlag(mliInvolvementFlag);
		claimApplication.setReasonForRecall(reasonForRecall);
		claimApplication.setReasonForFilingSuit(reasonForFilingSuit);
		claimApplication.setAssetPossessionDt(assetPossessionDt);
		claimApplication.setInclusionOfReceipt(inclusionOfReceipt);
		claimApplication.setConfirmRecoveryFlag(confirmRecoveryFlag);
		claimApplication.setSubsidyFlag(subsidyFlag);
		claimApplication.setIsSubsidyRcvdAfterNpa(subsidyRcvd);
		claimApplication.setIsSubsidyAdjustedOnDues(subsidyAdjusted);
		claimApplication.setSubsidyAmt(subsidyAmt);
		claimApplication.setSubsidyDate(subsidyDate);
		claimApplication.setMliCommentOnFinPosition(comments);
		claimApplication.setDetailsOfFinAssistance(finAssistance);
		claimApplication.setCreditSupport(creditSupport);
	/*	claimApplication.setAdviceByRbi(adviceByRbi);//bhu
		claimApplication.setRbiNonWilDef(rbiNonWilDef);//bhu
		claimApplication.setRbiNonDef(rbiNonDef);//bhu
		claimApplication.setCibilNonWilDef(cibilNonWilDef);//bhu
		claimApplication.setCibilNonDef(cibilNonDef);//bhu*/
		
		
		claimApplication.setBankFacilityDetail(bankFacility);
		claimApplication.setPlaceUnderWatchList(watchListPlace);
		claimApplication.setRemarksOnNpa(remarks);
		claimApplication.setDealingOfficerName(officerName);
		claimForm.setClaimapplication(claimApplication);
        claimForm.setTotGuaranteeAmt(guaranteeApprovedAmt);
		
		claimForm.setTotGuaranteeAmt(guaranteeApprovedAmt);
		
		claimForm.setIsNorthEastRegion(isNorthEastRegion);
		
		claimForm.setIsWomenorNot(isWomenorNot);
		System.out.println("<br>===560===");

		/*
		 * Cleaning up the Collection Objects
		 */
		/*
		 * // workingCapitalDtls.clear(); workingCapitalDtls = null; //
		 * claimSummaryDtlsArrayList.clear(); claimSummaryDtlsArrayList = null;
		 * // termCapitalDtls.clear(); termCapitalDtls = null; //
		 * cgpans.clear(); cgpans = null; // keys.clear(); keys = null;
		 */
		sdf = null;
		mId = null;
		bid = null;
		dtOfRecallNoticeStr = null;
		recallnoticedate = null;
		forumthruwhichinitiated = null;
		casesuitregnumber = null;
		nameofforum = null;
		dtOfFilingStr = null;
		filingdate = null;
		location = null;
		amntClaimed1 = null;
		currentstatusremarks = null;
		recoveryprocconcluded = null;
		dtOfReleaseOfWCStr = null;
		dateofreleasewc = null;
		dtofseekingofots = null;
		tclastDisbursementDts = null;
		tclastDisbursementDtsSet = null;
		tclastDisbursementDtsIterator = null;
		contextPath = null;
		nameOfFile = null;
		currentTime = null;
		file = null;
		legalfile = null;
		tcLoanDetail = null;
		termCapitalDtls = null;
		cgpans = null;
		keys = null;
		workingCapitalDtls = null;
		wcOsAsOnNPADtls = null;
		wcOsAsOnNPADtlsSet = null;
		wcOsAsOnNPADtlsIterator = null;
		wcKeys = null;
		wcDetail = null;
		asOnDtOfSanctionDtls = null;
		asOnDtOfSanctionDtlsSet = null;
		asOnDtOfSanctionDtlsIterator = null;
		dtlsAsOnSanctionDt = null;
		asOnNPADtls = null;
		asOnNPADtlsSet = null;
		asOnNPADtlsIterator = null;
		dtlsAsOnNPA = null;
		asOnLodgemntDtls = null;
		asOnLodgemntDtlsSet = null;
		asOnLodgemntDtlsIterator = null;
		dtlsAsOnLodgemnt = null;
		recoveryDetailsVector = null;
		recoveryDetails = null;
		recoveryDetailsSet = null;
		recoveryDetailsIterator = null;
		tokenzier = null;
		recDetails = null;
		cgpanKeys = null;
		recoveryKeys = null;
		tcPrincipal = null;
		tcInterestCharges = null;
		wcAmount = null;
		wcOtherCharges = null;
		recoveryMode = null;
		substring = null;
		claimSummaryDtlsArrayList = null;
		summaryDtls = null;
		claimSummaryDtls = null;
		claimSummaryDtlsSet = null;
		claimSummaryDtlsIterator = null;
		claimForm.resetDisclaimerPage(mapping, request);
		}
		
		//rajuk
		catch(Exception e){
			// e.printStackTrace();
			throw new DatabaseException(e.getMessage());
			
		}
		finally {
	        DBConnection.freeConnection(connection);
	    }
		
		
		if(!(clmrefno==null))
		
		{
		  Statement stmt = connection.createStatement();
		  ResultSet rs ;
          int ischeklist =0;
		  int noOfClaims = 0;
		  String query  ="";
		  String status  ="";	
		 
		
		 query = "select count(*) from claim_check_list where clm_ref_no='"+clmrefno+"'";
		  rs=stmt.executeQuery(query);

		 System.out.println(query);
		 
		 while(rs.next())
		  {
		        
			 ischeklist = rs.getInt(1);
		      }
		
		 if(ischeklist>0)
		
		 {
			 System.out.println("1rajukkk");
		//return mapping.findForward("success");
		return mapping.findForward("nextclaimdetailspage");
		
		
		 
	}
	else
	{
		 System.out.println("2raaa");
		return mapping.findForward("nextclaimPagewithoutchecklist");
		
		
		 
		
	}
		}
		 System.out.println("3rjjjj");
		return mapping.findForward("nextclaimdetailspage1");
	
	}
	
	//For Checker part :
	

	public ActionForward displayDisclaimerForSecInstallment(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ClaimActionForm claimForm = (ClaimActionForm) form;
		ClaimApplication claimApplication = new ClaimApplication();
		String mId = claimForm.getMemberId();
		String bid = ((claimForm.getBorrowerID()).toUpperCase()).trim();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String whetherBorrowerIsWilfulDefaulter = claimForm
				.getWilfullDefaulter();
		// //System.out.println("Whether Willful Defaulter :" +
		// whetherBorrowerIsWilfulDefaulter);
		String dateOfIssueOfRecallNotice = claimForm.getDateOfRecallNotice();
		String forumRecoveryProceedingsInitiated = claimForm
				.getForumthrulegalinitiated();
		// //System.out.println("Forum is :" + forumRecoveryProceedingsInitiated);
		if (forumRecoveryProceedingsInitiated != null) {
			if (forumRecoveryProceedingsInitiated.equals("Others")) {
				forumRecoveryProceedingsInitiated = claimForm.getOtherforums();
				// //System.out.println("Other Forums :" +
				// forumRecoveryProceedingsInitiated);
			}
		}
		String caseregnumber = claimForm.getCaseregnumber();
		String legaldate = claimForm.getLegaldate();
		String nameOfForum = claimForm.getNameofforum();
		String location = claimForm.getLocation();
		String amountClaimed = claimForm.getAmountclaimed();
		String currentStatusRemarks = claimForm.getCurrentstatusremarks();
		String proceedingsConcluded = claimForm.getProceedingsConcluded();
		String dtOfConclusionOfRecoveryProc = claimForm
				.getDtOfConclusionOfRecoveryProc();
		// //System.out.println("Dt of Conclusion of Recovery Proceedings :" +
		// dtOfConclusionOfRecoveryProc);
		String whetherAccntWasWrittenOffBooks = claimForm
				.getWhetherAccntWasWrittenOffBooks();
		// //System.out.println("Whether Accnt Written Off Books :" +
		// whetherAccntWasWrittenOffBooks);
		String dtOnWhichAccntWrittenOff = claimForm
				.getDtOnWhichAccntWrittenOff();
		// //System.out.println("Dt on which Accnt Written Off :" +
		// dtOnWhichAccntWrittenOff);
		String dateOfReleaseOfWC = claimForm.getDateOfReleaseOfWC();
		String dateOfSeekingOTS = claimForm.getDateOfSeekingOTS();

		/*
		 * Uploading the attachements in the claim application
		 */
		String contextPath = null;
		String nameOfFile = null;
		// Retrieving the present time in yyyy-mm-dd format
		String currentTime = (new java.sql.Date(System.currentTimeMillis()))
				.toString();
		/*
		 * Uploading the Recall Issue Notice File Attachment
		 */
		FormFile file = (FormFile) claimForm.getRecallnoticefilepath();
		if (file != null) {
			contextPath = request.getSession().getServletContext()
					.getRealPath("");
			nameOfFile = ClaimConstants.CLM_RECALL_NOTICE + currentTime
					+ ClaimConstants.CLM_NO_VALUE + mId + bid;
			// Uploading the file with the new name
			Log.log(Log.DEBUG, "ClaimAction",
					"displayDisclaimerForSecInstallment()",
					"Uploading the Recall Notice attachment....");

			byte[] data = file.getFileData();
			claimApplication.setRecallNoticeFileData(data);
			claimApplication.setRecalNoticeFileName(file.getFileName());

			uploadFile(file, contextPath, nameOfFile);

			Log.log(Log.DEBUG, "ClaimAction",
					"displayDisclaimerForSecInstallment()",
					"File Name, File data " + file.getFileName() + "," + data);

			if (data != null) {
				Log.log(Log.DEBUG, "ClaimAction",
						"displayDisclaimerForSecInstallment()",
						"File data length " + data.length);
			}

		}

		/*
		 * Uploading the Legal Attachment
		 */
		FormFile legalfile = (FormFile) claimForm.getLegalAttachmentPath();
		if (legalfile != null) {
			contextPath = request.getSession().getServletContext()
					.getRealPath("");
			nameOfFile = ClaimConstants.CLM_LEGAL_ATTACHMENT + currentTime
					+ ClaimConstants.CLM_NO_VALUE + mId + bid;
			// Uploading the file with the new name
			Log.log(Log.INFO, "ClaimAction",
					"displayDisclaimerForSecInstallment()",
					"Uploading Legal Details the attachment....");

			byte[] data = legalfile.getFileData();
			claimApplication.setLegalDetailsFileData(data);
			claimApplication.setLegalDetailsFileName(legalfile.getFileName());

			uploadFile(legalfile, contextPath, nameOfFile);

			Log.log(Log.DEBUG, "ClaimAction",
					"displayDisclaimerForSecInstallment()",
					"File Name, File data " + file.getFileName() + "," + data);

			if (data != null) {
				Log.log(Log.DEBUG, "ClaimAction",
						"displayDisclaimerForSecInstallment()",
						"File data length " + data.length);
			}
		}

		// Capturing Term Loan/Capital Loan Details from the action form
		Map tclastDisbursementDts = claimForm.getLastDisbursementDate();

		Set tclastDisbursementDtsSet = tclastDisbursementDts.keySet();

		Iterator tclastDisbursementDtsIterator = tclastDisbursementDtsSet
				.iterator();

		TermLoanCapitalLoanDetail tcLoanDetail = null;
		// termCapitalDtls vector contains TermLoanCapitalLoanDetail objects
		Vector termCapitalDtls = new Vector();
		// Vector cgpans = claimForm.getCgpansVector();
		Vector keys = new Vector();
		while (tclastDisbursementDtsIterator.hasNext()) {
			String key = (String) tclastDisbursementDtsIterator.next();
			if (!keys.contains(key)) {
				keys.addElement(key);
			}
		}

		for (int i = 0; i < keys.size(); i++) {
			tcLoanDetail = new TermLoanCapitalLoanDetail();

			// Capturing the key
			String key = (String) keys.elementAt(i);
			if (key == null) {
				continue;
			}
			tcLoanDetail.setCgpan((String) claimForm.getCgpandetails(key));

			// Capturing the last disbursement date
			String date = (String) claimForm.getLastDisbursementDate(key);
			tcLoanDetail.setLastDisbursementDate(sdf.parse(date,
					new ParsePosition(0)));

			// Capturing Principal
			String principl = (String) claimForm.getTcprincipal(key);
			if ((principl.equals("")) || (principl == null)) {
				principl = "0.0";
			}
			tcLoanDetail.setPrincipalRepayment(Double.parseDouble(principl));

			// Capturing Interest Charges
			String interestCharges = (String) claimForm
					.getTcInterestCharge(key);
			if ((interestCharges.equals("")) || (interestCharges == null)) {
				interestCharges = "0.0";
			}
			tcLoanDetail.setInterestAndOtherCharges(Double
					.parseDouble(interestCharges));

			// Capturing Outstanding as on dt of npa
			String osnpa = (String) claimForm.getTcOsAsOnDateOfNPA(key);
			if ((osnpa.equals("")) || (osnpa == null)) {
				osnpa = "0.0";
			}
			tcLoanDetail.setOutstandingAsOnDateOfNPA(Double.parseDouble(osnpa));

			// Capturing outstanding as stated in civil suit
			String oscivilsuit = (String) claimForm
					.getTcOsAsStatedInCivilSuit(key);
			if ((oscivilsuit.equals("")) || (oscivilsuit == null)) {
				oscivilsuit = "0.0";
			}
			tcLoanDetail.setOutstandingStatedInCivilSuit(Double
					.parseDouble(oscivilsuit));

			// Capturing outstanding as on date of lodgement
			String oslodgement = (String) claimForm
					.getTcOsAsOnLodgementOfClaim(key);
			if ((oslodgement.equals("")) || (oslodgement == null)) {
				oslodgement = "0.0";
			}
			tcLoanDetail.setOutstandingAsOnDateOfLodgement(Double
					.parseDouble(oslodgement));

			// Capturing as on date of lodgement of second claim
			String oslodgemntSecondClm = (String) claimForm
					.getTcOsAsOnLodgementOfSecondClaim(key);
			if ((oslodgemntSecondClm.equals(""))
					|| (oslodgemntSecondClm == null)) {
				oslodgemntSecondClm = "0.0";
			}
			double osAsOnDtOfSecClmInstmnt = Double
					.parseDouble(oslodgemntSecondClm);
			// //System.out.println("OS As on Dt of Sec Clm Instlmnt :" +
			// osAsOnDtOfSecClmInstmnt);
			tcLoanDetail
					.setOsAsOnDateOfLodgementOfClmForSecInstllmnt(osAsOnDtOfSecClmInstmnt);
			if (!termCapitalDtls.contains(tcLoanDetail)) {
				termCapitalDtls.addElement(tcLoanDetail);
			}
		}

		// Capturing the Working Capital Applications Details
		ArrayList workingCapitalDtls = new ArrayList();
		Map wcOsAsOnNPADtls = claimForm.getWcOsAsOnDateOfNPA();
		Set wcOsAsOnNPADtlsSet = wcOsAsOnNPADtls.keySet();
		Iterator wcOsAsOnNPADtlsIterator = wcOsAsOnNPADtlsSet.iterator();
		Vector wcKeys = new Vector();
		while (wcOsAsOnNPADtlsIterator.hasNext()) {
			String key = (String) wcOsAsOnNPADtlsIterator.next();
			if (!wcKeys.contains(key)) {
				wcKeys.addElement(key);
			}
		}
		WorkingCapitalDetail wcDetail = null;
		for (int i = 0; i < wcKeys.size(); i++) {
			wcDetail = new WorkingCapitalDetail();
			wcDetail.setCgpan((String) claimForm.getWcCgpan((String) wcKeys
					.elementAt(i)));

			// Capturing outstanding as on npa
			String osnpawc = (String) claimForm
					.getWcOsAsOnDateOfNPA((String) wcKeys.elementAt(i));
			if ((osnpawc.equals("")) || (osnpawc == null)) {
				osnpawc = "0.0";
			}
			wcDetail.setOutstandingAsOnDateOfNPA(Double.parseDouble(osnpawc));

			// Capturing outstanding as in civil suit
			String oswccivilsuit = (String) claimForm
					.getWcOsAsStatedInCivilSuit((String) wcKeys.elementAt(i));
			if ((oswccivilsuit.equals("")) || (oswccivilsuit == null)) {
				oswccivilsuit = "0.0";
			}
			wcDetail.setOutstandingStatedInCivilSuit(Double
					.parseDouble(oswccivilsuit));

			// Capturing outstanding as on dt of lodgement
			String oswclodgement = (String) claimForm
					.getWcOsAsOnLodgementOfClaim((String) wcKeys.elementAt(i));
			if ((oswclodgement.equals("")) || (oswclodgement == null)) {
				oswclodgement = "0.0";
			}
			wcDetail.setOutstandingAsOnDateOfLodgement(Double
					.parseDouble(oswclodgement));

			String oswclodgementOfSecondClm = (String) claimForm
					.getWcOsAsOnLodgementOfSecondClaim((String) wcKeys
							.elementAt(i));
			if ((oswclodgementOfSecondClm.equals(""))
					|| (oswclodgementOfSecondClm == null)) {
				oswclodgementOfSecondClm = "0.0";
			}
			wcDetail.setOsAsOnDateOfLodgementOfClmForSecInstllmnt(Double
					.parseDouble(oswclodgementOfSecondClm));
			// Adding the workingcapitaldtl object to the vector
			if (!workingCapitalDtls.contains(wcDetail)) {
				workingCapitalDtls.add(wcDetail);
			}
		}

		// Capturing the recovery details
		Vector recoveryDetailsVector = new Vector();
		Map recoveryDetails = claimForm.getCgpandetails();
		Set recoveryDetailsSet = recoveryDetails.keySet();
		Iterator recoveryDetailsIterator = recoveryDetailsSet.iterator();
		// StringTokenizer tokenzier = null;
		RecoveryDetails recDetails = null;

		Vector cgpanKeys = new Vector();
		Vector recoveryKeys = new Vector();

		String tcPrincipal = "tcprincipal".trim();
		String tcInterestCharges = "tcInterestCharges".trim();
		String wcAmount = "wcAmount".trim();
		String wcOtherCharges = "wcOtherCharges".trim();
		String recoveryMode = "recoveryMode".trim();
		String substring = "recovery#key-".trim();
		while (recoveryDetailsIterator.hasNext()) {
			String key = (String) recoveryDetailsIterator.next();
			if (key != null) {
				if ((key.indexOf(substring)) == 0) {
					if (!(cgpanKeys.contains(key))) {
						cgpanKeys.addElement(key);
					}

				}
				if ((key.indexOf(substring)) > 0) {
					if (!(recoveryKeys.contains(key))) {
						recoveryKeys.addElement(key);
					}
				}
			}
		}
		for (int j = 0; j < cgpanKeys.size(); j++) {
			recDetails = new RecoveryDetails();
			String cgpanKey = (String) cgpanKeys.elementAt(j);
			if (cgpanKey == null) {
				continue;
			}
			recDetails.setCgpan((String) claimForm.getCgpandetails(cgpanKey));
			// String cgpan = recDetails.getCgpan();

			for (int i = 0; i < recoveryKeys.size(); i++) {
				String recoveryKey = (String) recoveryKeys.elementAt(i);
				if (recoveryKey == null) {
					continue;
				}
				if (((recoveryKey.indexOf(tcPrincipal)) == 0)
						&& ((recoveryKey.indexOf(cgpanKey)) > 0)) {
					String recoverypricipaltc = (String) claimForm
							.getCgpandetails(recoveryKey);
					if ((recoverypricipaltc.equals(""))
							|| (recoverypricipaltc == null)) {
						recoverypricipaltc = "0.0";
					}
					recDetails.setTcPrincipal(Double
							.parseDouble(recoverypricipaltc));
				}
				if (((recoveryKey.indexOf(tcInterestCharges)) == 0)
						&& ((recoveryKey.indexOf(cgpanKey)) > 0)) {
					String recinterestchargestc = (String) claimForm
							.getCgpandetails(recoveryKey);
					if ((recinterestchargestc.equals(""))
							|| (recinterestchargestc == null)) {
						recinterestchargestc = "0.0";
					}
					recDetails.setTcInterestAndOtherCharges(Double
							.parseDouble(recinterestchargestc));
				}
				if (((recoveryKey.indexOf(wcAmount)) == 0)
						&& ((recoveryKey.indexOf(cgpanKey)) > 0)) {
					String wcamount = (String) claimForm
							.getCgpandetails(recoveryKey);
					if ((wcamount.equals("")) || (wcamount == null)) {
						wcamount = "0.0";
					}
					recDetails.setWcAmount(Double.parseDouble(wcamount));
				}
				if (((recoveryKey.indexOf(wcOtherCharges)) == 0)
						&& ((recoveryKey.indexOf(cgpanKey)) > 0)) {
					String wcothercharges = (String) claimForm
							.getCgpandetails(recoveryKey);
					if ((wcothercharges.equals("")) || (wcothercharges == null)) {
						wcothercharges = "0.0";
					}
					recDetails.setWcOtherCharges(Double
							.parseDouble(wcothercharges));
				}
				if (((recoveryKey.indexOf(recoveryMode)) == 0)
						&& ((recoveryKey.indexOf(cgpanKey)) > 0)) {
					recDetails.setModeOfRecovery((String) claimForm
							.getCgpandetails(recoveryKey));
				}

			}
			String modeOfRec = recDetails.getModeOfRecovery();
			if ((modeOfRec != null) && (!modeOfRec.equals(""))) {
				if (!recoveryDetailsVector.contains(recDetails)) {
					recoveryDetailsVector.addElement(recDetails);
				}
			}
		}

		// Capturing Security and Personal Guarantee Details
		Map asOnDtOfSanctionDtls = claimForm.getAsOnDtOfSanctionDtl();
		Set asOnDtOfSanctionDtlsSet = asOnDtOfSanctionDtls.keySet();
		Iterator asOnDtOfSanctionDtlsIterator = asOnDtOfSanctionDtlsSet
				.iterator();
		DtlsAsOnDateOfSanction dtlsAsOnSanctionDt = new DtlsAsOnDateOfSanction();
		while (asOnDtOfSanctionDtlsIterator.hasNext()) {
			String key = (String) asOnDtOfSanctionDtlsIterator.next();
			if ((key.trim())
					.equals(ClaimConstants.CLM_SAPGD_REASONS_FOR_REDUCTION)) {
				dtlsAsOnSanctionDt.setReasonsForReduction((String) claimForm
						.getAsOnDtOfSanctionDtl(key));
			}
			if ((key.trim())
					.equals(ClaimConstants.CLM_SAPGD_PARTICULAR_CUR_ASSETS)) {
				String currntassetssanction = (String) claimForm
						.getAsOnDtOfSanctionDtl(key);
				if ((currntassetssanction.equals(""))
						|| (currntassetssanction == null)) {
					currntassetssanction = "0.0";
				}
				dtlsAsOnSanctionDt.setValueOfCurrentAssets(Double
						.parseDouble(currntassetssanction));
			}
			if ((key.trim()).equals(ClaimConstants.CLM_SAPGD_PARTICULAR_LAND)) {
				String landasonsanction = (String) claimForm
						.getAsOnDtOfSanctionDtl(key);
				if ((landasonsanction.equals("")) || (landasonsanction == null)) {
					landasonsanction = "0.0";
				}
				dtlsAsOnSanctionDt.setValueOfLand(Double
						.parseDouble(landasonsanction));
			}
			if ((key.trim()).equals(ClaimConstants.CLM_SAPGD_PARTICULAR_MC)) {
				String machinesanction = (String) claimForm
						.getAsOnDtOfSanctionDtl(key);
				if ((machinesanction.equals("")) || (machinesanction == null)) {
					machinesanction = "0.0";
				}
				dtlsAsOnSanctionDt.setValueOfMachine(Double
						.parseDouble(machinesanction));
			}
			if ((key.trim()).equals(ClaimConstants.CLM_SAPGD_PARTICULAR_BLDG)) {
				String bldgsanction = (String) claimForm
						.getAsOnDtOfSanctionDtl(key);
				if ((bldgsanction.equals("")) || (bldgsanction == null)) {
					bldgsanction = "0.0";
				}
				dtlsAsOnSanctionDt.setValueOfBuilding(Double
						.parseDouble(bldgsanction));
			}
			if ((key.trim()).equals(ClaimConstants.CLM_SAPGD_PARTICULAR_OTHERS)) {
				String otherssanction = (String) claimForm
						.getAsOnDtOfSanctionDtl(key);
				if ((otherssanction.equals("")) || (otherssanction == null)) {
					otherssanction = "0.0";
				}
				dtlsAsOnSanctionDt.setValueOfOthers(Double
						.parseDouble(otherssanction));
			}
			if ((key.trim())
					.equals(ClaimConstants.CLM_SAPGD_PARTICULAR_OTHER_FIXED_MOV_ASSETS)) {
				String otherassets = (String) claimForm
						.getAsOnDtOfSanctionDtl(key);
				if ((otherassets.equals("")) || (otherassets == null)) {
					otherassets = "0.0";
				}
				dtlsAsOnSanctionDt.setValueOfOtherFixedMovableAssets(Double
						.parseDouble(otherassets));
			}
			if ((key.trim())
					.equals(ClaimConstants.CLM_SAPGD_NETWORTH_OF_GUARANTOR)) {
				String networthsanction = (String) claimForm
						.getAsOnDtOfSanctionDtl(key);
				if ((networthsanction.equals("")) || (networthsanction == null)) {
					networthsanction = "0.0";
				}
				dtlsAsOnSanctionDt.setNetworthOfGuarantors(Double
						.parseDouble(networthsanction));
			}
		}
		Map asOnNPADtls = claimForm.getAsOnDtOfNPA();
		Set asOnNPADtlsSet = asOnNPADtls.keySet();
		Iterator asOnNPADtlsIterator = asOnNPADtlsSet.iterator();
		DtlsAsOnDateOfNPA dtlsAsOnNPA = new DtlsAsOnDateOfNPA();
		while (asOnNPADtlsIterator.hasNext()) {
			String key = (String) asOnNPADtlsIterator.next();
			if ((key.trim())
					.equals(ClaimConstants.CLM_SAPGD_REASONS_FOR_REDUCTION)) {
				dtlsAsOnNPA.setReasonsForReduction((String) claimForm
						.getAsOnDtOfNPA(key));
			}
			if ((key.trim())
					.equals(ClaimConstants.CLM_SAPGD_PARTICULAR_CUR_ASSETS)) {
				String currentassetsnpa = (String) claimForm
						.getAsOnDtOfNPA(key);
				if ((currentassetsnpa.equals("")) || (currentassetsnpa == null)) {
					currentassetsnpa = "0.0";
				}
				dtlsAsOnNPA.setValueOfCurrentAssets(Double
						.parseDouble(currentassetsnpa));
			}
			if ((key.trim()).equals(ClaimConstants.CLM_SAPGD_PARTICULAR_LAND)) {
				String landnpa = (String) claimForm.getAsOnDtOfNPA(key);
				if ((landnpa.equals("")) || (landnpa == null)) {
					landnpa = "0.0";
				}
				dtlsAsOnNPA.setValueOfLand(Double.parseDouble(landnpa));
			}
			if ((key.trim()).equals(ClaimConstants.CLM_SAPGD_PARTICULAR_MC)) {
				String machinenpa = (String) claimForm.getAsOnDtOfNPA(key);
				if ((machinenpa.equals("")) || (machinenpa == null)) {
					machinenpa = "0.0";
				}
				dtlsAsOnNPA.setValueOfMachine(Double.parseDouble(machinenpa));
			}
			if ((key.trim()).equals(ClaimConstants.CLM_SAPGD_PARTICULAR_BLDG)) {
				String bldgnpa = (String) claimForm.getAsOnDtOfNPA(key);
				if ((bldgnpa.equals("")) || (bldgnpa == null)) {
					bldgnpa = "0.0";
				}
				dtlsAsOnNPA.setValueOfBuilding(Double.parseDouble(bldgnpa));
			}
			if ((key.trim()).equals(ClaimConstants.CLM_SAPGD_PARTICULAR_OTHERS)) {
				String othersnpa = (String) claimForm.getAsOnDtOfNPA(key);
				if ((othersnpa.equals("")) || (othersnpa == null)) {
					othersnpa = "0.0";
				}
				dtlsAsOnNPA.setValueOfOthers(Double.parseDouble(othersnpa));
			}
			if ((key.trim())
					.equals(ClaimConstants.CLM_SAPGD_PARTICULAR_OTHER_FIXED_MOV_ASSETS)) {
				String otherassets = (String) claimForm.getAsOnDtOfNPA(key);
				if ((otherassets.equals("")) || (otherassets == null)) {
					otherassets = "0.0";
				}
				dtlsAsOnNPA.setValueOfOtherFixedMovableAssets(Double
						.parseDouble(otherassets));
			}
			if ((key.trim())
					.equals(ClaimConstants.CLM_SAPGD_NETWORTH_OF_GUARANTOR)) {
				String networthnpa = (String) claimForm.getAsOnDtOfNPA(key);
				if ((networthnpa.equals("")) || (networthnpa == null)) {
					networthnpa = "0.0";
				}
				dtlsAsOnNPA.setNetworthOfGuarantors(Double
						.parseDouble(networthnpa));
			}
		}
		Map asOnLodgemntDtls = claimForm.getAsOnLodgemntOfCredit();
		Set asOnLodgemntDtlsSet = asOnLodgemntDtls.keySet();
		Iterator asOnLodgemntDtlsIterator = asOnLodgemntDtlsSet.iterator();
		DtlsAsOnLogdementOfClaim dtlsAsOnLodgemnt = new DtlsAsOnLogdementOfClaim();
		while (asOnLodgemntDtlsIterator.hasNext()) {
			String key = (String) asOnLodgemntDtlsIterator.next();
			if ((key.trim())
					.equals(ClaimConstants.CLM_SAPGD_REASONS_FOR_REDUCTION)) {
				dtlsAsOnLodgemnt.setReasonsForReduction((String) claimForm
						.getAsOnLodgemntOfCredit(key));
			}
			if ((key.trim())
					.equals(ClaimConstants.CLM_SAPGD_PARTICULAR_CUR_ASSETS)) {
				String currentassetslodgemnt = (String) claimForm
						.getAsOnLodgemntOfCredit(key);
				if ((currentassetslodgemnt.equals(""))
						|| (currentassetslodgemnt == null)) {
					currentassetslodgemnt = "0.0";
				}
				dtlsAsOnLodgemnt.setValueOfCurrentAssets(Double
						.parseDouble(currentassetslodgemnt));
			}
			if ((key.trim()).equals(ClaimConstants.CLM_SAPGD_PARTICULAR_LAND)) {
				String landlodgemnt = (String) claimForm
						.getAsOnLodgemntOfCredit(key);
				if ((landlodgemnt.equals("")) || (landlodgemnt == null)) {
					landlodgemnt = "0.0";
				}
				dtlsAsOnLodgemnt.setValueOfLand(Double
						.parseDouble(landlodgemnt));
			}
			if ((key.trim()).equals(ClaimConstants.CLM_SAPGD_PARTICULAR_MC)) {
				String machinelodgemnt = (String) claimForm
						.getAsOnLodgemntOfCredit(key);
				if ((machinelodgemnt.equals("")) || (machinelodgemnt == null)) {
					machinelodgemnt = "0.0";
				}
				dtlsAsOnLodgemnt.setValueOfMachine(Double
						.parseDouble(machinelodgemnt));
			}
			if ((key.trim()).equals(ClaimConstants.CLM_SAPGD_PARTICULAR_BLDG)) {
				String bldglodgemnt = (String) claimForm
						.getAsOnLodgemntOfCredit(key);
				if ((bldglodgemnt.equals("")) || (bldglodgemnt == null)) {
					bldglodgemnt = "0.0";
				}
				dtlsAsOnLodgemnt.setValueOfBuilding(Double
						.parseDouble(bldglodgemnt));
			}
			if ((key.trim()).equals(ClaimConstants.CLM_SAPGD_PARTICULAR_OTHERS)) {
				String otherslodgemnt = (String) claimForm
						.getAsOnLodgemntOfCredit(key);
				if ((otherslodgemnt.equals("")) || (otherslodgemnt == null)) {
					otherslodgemnt = "0.0";
				}
				dtlsAsOnLodgemnt.setValueOfOthers(Double
						.parseDouble(otherslodgemnt));
			}
			if ((key.trim())
					.equals(ClaimConstants.CLM_SAPGD_PARTICULAR_OTHER_FIXED_MOV_ASSETS)) {
				String otherassetslodgemnt = (String) claimForm
						.getAsOnLodgemntOfCredit(key);
				if ((otherassetslodgemnt.equals(""))
						|| (otherassetslodgemnt == null)) {
					otherassetslodgemnt = "0.0";
				}
				dtlsAsOnLodgemnt.setValueOfOtherFixedMovableAssets(Double
						.parseDouble(otherassetslodgemnt));
			}
			if ((key.trim())
					.equals(ClaimConstants.CLM_SAPGD_NETWORTH_OF_GUARANTOR)) {
				String networthlodgemnt = (String) claimForm
						.getAsOnLodgemntOfCredit(key);
				if ((networthlodgemnt.equals("")) || (networthlodgemnt == null)) {
					networthlodgemnt = "0.0";
				}
				dtlsAsOnLodgemnt.setNetworthOfGuarantors(Double
						.parseDouble(networthlodgemnt));
			}
		}

		Map asOnLodgemntOfSecClmDtls = claimForm
				.getAsOnDateOfLodgemntOfSecondClm();
		Set asOnLodgemntOfSecClmDtlsSet = asOnLodgemntOfSecClmDtls.keySet();
		Iterator asOnLodgemntOfSecClmDtlsIterator = asOnLodgemntOfSecClmDtlsSet
				.iterator();
		DtlsAsOnLogdementOfSecondClaim dtlsAsOnLodgemntOfSecClm = new DtlsAsOnLogdementOfSecondClaim();
		String keyForLand = ClaimConstants.CLM_SAPGD_REASONS_FOR_REDUCTION
				+ ClaimConstants.CLM_DELIMITER1
				+ ClaimConstants.CLM_SAPGD_PARTICULAR_LAND;

		String keyForBldg = ClaimConstants.CLM_SAPGD_REASONS_FOR_REDUCTION
				+ ClaimConstants.CLM_DELIMITER1
				+ ClaimConstants.CLM_SAPGD_PARTICULAR_BLDG;

		String keyForMachine = ClaimConstants.CLM_SAPGD_REASONS_FOR_REDUCTION
				+ ClaimConstants.CLM_DELIMITER1
				+ ClaimConstants.CLM_SAPGD_PARTICULAR_MC;

		String keyForOFMA = ClaimConstants.CLM_SAPGD_REASONS_FOR_REDUCTION
				+ ClaimConstants.CLM_DELIMITER1
				+ ClaimConstants.CLM_SAPGD_PARTICULAR_OTHER_FIXED_MOV_ASSETS;

		String keyForCurrAssets = ClaimConstants.CLM_SAPGD_REASONS_FOR_REDUCTION
				+ ClaimConstants.CLM_DELIMITER1
				+ ClaimConstants.CLM_SAPGD_PARTICULAR_CUR_ASSETS;

		String keyForOthers = ClaimConstants.CLM_SAPGD_REASONS_FOR_REDUCTION
				+ ClaimConstants.CLM_DELIMITER1
				+ ClaimConstants.CLM_SAPGD_PARTICULAR_OTHERS;

		// double valueOfLand;
		// double valueOfBuilding;
		// double valueOfMachine;
		// double valueOfOtherFixedMovableAssets;
		// double valueOfCurrentAssets;
		// double valueOfOthers;
		// double networthOfGuarantors;
		// String reasonsForReductionLand;
		// String reasonsForReductionBuilding;
		// String reasonsForReductionMachine;
		// String reasonsForReductionFixed;
		// String reasonsForReductionCurrent;
		// String reasonsForReductionOthers;
		// double amtRealisedLand;
		// double amtRealisedBuilding;
		// double amtRealisedMachine;
		// double amtRealisedFixed;
		// double amtRealisedCurrent;
		// double amtRealisedOthers;
		double amtRealisedPersonalGuarantee;
		String amtRealisedPersonalGuaranteeStr = null;

		while (asOnLodgemntOfSecClmDtlsIterator.hasNext()) {
			String key = (String) asOnLodgemntOfSecClmDtlsIterator.next();
			if ((key.trim()).equals(keyForLand)) {
				dtlsAsOnLodgemntOfSecClm
						.setReasonsForReductionLand((String) claimForm
								.getAsOnDateOfLodgemntOfSecondClm(key));
			}
			if ((key.trim()).equals(keyForBldg)) {
				dtlsAsOnLodgemntOfSecClm
						.setReasonsForReductionBuilding((String) claimForm
								.getAsOnDateOfLodgemntOfSecondClm(key));
			}
			if ((key.trim()).equals(keyForMachine)) {
				dtlsAsOnLodgemntOfSecClm
						.setReasonsForReductionMachine((String) claimForm
								.getAsOnDateOfLodgemntOfSecondClm(key));
			}
			if ((key.trim()).equals(keyForOFMA)) {
				dtlsAsOnLodgemntOfSecClm
						.setReasonsForReductionFixed((String) claimForm
								.getAsOnDateOfLodgemntOfSecondClm(key));
			}
			if ((key.trim()).equals(keyForCurrAssets)) {
				dtlsAsOnLodgemntOfSecClm
						.setReasonsForReductionCurrent((String) claimForm
								.getAsOnDateOfLodgemntOfSecondClm(key));
			}
			if ((key.trim()).equals(keyForOthers)) {
				dtlsAsOnLodgemntOfSecClm
						.setReasonsForReductionOthers((String) claimForm
								.getAsOnDateOfLodgemntOfSecondClm(key));
			}
			if ((key.trim())
					.equals(ClaimConstants.CLM_SAPGD_PARTICULAR_CUR_ASSETS)) {
				String currentassetslodgemntOfSecClm = (String) claimForm
						.getAsOnDateOfLodgemntOfSecondClm(key);
				if ((currentassetslodgemntOfSecClm.equals(""))
						|| (currentassetslodgemntOfSecClm == null)) {
					currentassetslodgemntOfSecClm = "0.0";
				}
				String amntRealizedForCurrThruSecurity = (String) claimForm
						.getAmntRealizedThruDisposalOfSecurity(key);
				if (amntRealizedForCurrThruSecurity.equals("")) {
					amntRealizedForCurrThruSecurity = "0.0";
				}
				dtlsAsOnLodgemntOfSecClm.setValueOfCurrentAssets(Double
						.parseDouble(currentassetslodgemntOfSecClm));
				dtlsAsOnLodgemntOfSecClm.setAmtRealisedCurrent(Double
						.parseDouble(amntRealizedForCurrThruSecurity));
			}
			if ((key.trim()).equals(ClaimConstants.CLM_SAPGD_PARTICULAR_LAND)) {
				String landlodgemntOfSecClm = (String) claimForm
						.getAsOnDateOfLodgemntOfSecondClm(key);
				if ((landlodgemntOfSecClm.equals(""))
						|| (landlodgemntOfSecClm == null)) {
					landlodgemntOfSecClm = "0.0";
				}
				String amntRealizedForLandThruSecurity = (String) claimForm
						.getAmntRealizedThruDisposalOfSecurity(key);
				if (amntRealizedForLandThruSecurity.equals("")) {
					amntRealizedForLandThruSecurity = "0.0";
				}
				dtlsAsOnLodgemntOfSecClm.setValueOfLand(Double
						.parseDouble(landlodgemntOfSecClm));
				dtlsAsOnLodgemntOfSecClm.setAmtRealisedLand(Double
						.parseDouble(amntRealizedForLandThruSecurity));
			}
			if ((key.trim()).equals(ClaimConstants.CLM_SAPGD_PARTICULAR_MC)) {
				String machinelodgemntOfSecClm = (String) claimForm
						.getAsOnDateOfLodgemntOfSecondClm(key);
				if ((machinelodgemntOfSecClm.equals(""))
						|| (machinelodgemntOfSecClm == null)) {
					machinelodgemntOfSecClm = "0.0";
				}
				String amntRealizedForMachineThruSecurity = (String) claimForm
						.getAmntRealizedThruDisposalOfSecurity(key);
				if (amntRealizedForMachineThruSecurity.equals("")) {
					amntRealizedForMachineThruSecurity = "0.0";
				}
				dtlsAsOnLodgemntOfSecClm.setValueOfMachine(Double
						.parseDouble(machinelodgemntOfSecClm));
				dtlsAsOnLodgemntOfSecClm.setAmtRealisedMachine(Double
						.parseDouble(amntRealizedForMachineThruSecurity));
			}
			if ((key.trim()).equals(ClaimConstants.CLM_SAPGD_PARTICULAR_BLDG)) {
				String bldglodgemntSecClm = (String) claimForm
						.getAsOnDateOfLodgemntOfSecondClm(key);
				if ((bldglodgemntSecClm.equals(""))
						|| (bldglodgemntSecClm == null)) {
					bldglodgemntSecClm = "0.0";
				}
				String amntRealizedForBldgThruSecurity = (String) claimForm
						.getAmntRealizedThruDisposalOfSecurity(key);
				if (amntRealizedForBldgThruSecurity.equals("")) {
					amntRealizedForBldgThruSecurity = "0.0";
				}
				dtlsAsOnLodgemntOfSecClm.setValueOfBuilding(Double
						.parseDouble(bldglodgemntSecClm));
				dtlsAsOnLodgemntOfSecClm.setAmtRealisedBuilding(Double
						.parseDouble(amntRealizedForBldgThruSecurity));
			}
			if ((key.trim()).equals(ClaimConstants.CLM_SAPGD_PARTICULAR_OTHERS)) {
				String otherslodgemntSecClm = (String) claimForm
						.getAsOnDateOfLodgemntOfSecondClm(key);
				if ((otherslodgemntSecClm.equals(""))
						|| (otherslodgemntSecClm == null)) {
					otherslodgemntSecClm = "0.0";
				}
				String amntRealizedForOthersThruSecurity = (String) claimForm
						.getAmntRealizedThruDisposalOfSecurity(key);
				if (amntRealizedForOthersThruSecurity.equals("")) {
					amntRealizedForOthersThruSecurity = "0.0";
				}
				dtlsAsOnLodgemntOfSecClm.setValueOfOthers(Double
						.parseDouble(otherslodgemntSecClm));
				dtlsAsOnLodgemntOfSecClm.setAmtRealisedOthers(Double
						.parseDouble(amntRealizedForOthersThruSecurity));
			}
			if ((key.trim())
					.equals(ClaimConstants.CLM_SAPGD_PARTICULAR_OTHER_FIXED_MOV_ASSETS)) {
				String otherassetslodgemntSecClm = (String) claimForm
						.getAsOnDateOfLodgemntOfSecondClm(key);
				if ((otherassetslodgemntSecClm.equals(""))
						|| (otherassetslodgemntSecClm == null)) {
					otherassetslodgemntSecClm = "0.0";
				}
				String amntRealizedForOFMAThruSecurity = (String) claimForm
						.getAmntRealizedThruDisposalOfSecurity(key);
				if (amntRealizedForOFMAThruSecurity.equals("")) {
					amntRealizedForOFMAThruSecurity = "0.0";
				}
				dtlsAsOnLodgemntOfSecClm
						.setValueOfOtherFixedMovableAssets(Double
								.parseDouble(otherassetslodgemntSecClm));
				dtlsAsOnLodgemntOfSecClm.setAmtRealisedFixed(Double
						.parseDouble(amntRealizedForOFMAThruSecurity));
			}
			if ((key.trim())
					.equals(ClaimConstants.CLM_SAPGD_NETWORTH_OF_GUARANTOR)) {
				String networthlodgemntSecClm = (String) claimForm
						.getAsOnDateOfLodgemntOfSecondClm(key);
				if ((networthlodgemntSecClm.equals(""))
						|| (networthlodgemntSecClm == null)) {
					networthlodgemntSecClm = "0.0";
				}
				dtlsAsOnLodgemntOfSecClm.setNetworthOfGuarantors(Double
						.parseDouble(networthlodgemntSecClm));
			}
		}
		// Capturing Amnt realized thru personal guarantees
		amtRealisedPersonalGuaranteeStr = (String) claimForm
				.getAmntRealizedThruInvocationOfPerGuarantees();
		if (amtRealisedPersonalGuaranteeStr.equals("")) {
			amtRealisedPersonalGuaranteeStr = "0.0";
		}
		amtRealisedPersonalGuarantee = Double
				.parseDouble(amtRealisedPersonalGuaranteeStr);
		dtlsAsOnLodgemntOfSecClm
				.setAmtRealisedPersonalGuarantee(amtRealisedPersonalGuarantee);

		// Capturing Claim Summary Details
		Map clmSummaryDtls = claimForm.getClaimSummaryDetails();
		Set clmSummaryDtlsSet = clmSummaryDtls.keySet();
		Iterator clmSummaryDtlsIterator = clmSummaryDtlsSet.iterator();
		ArrayList claimSummaryDtlsArrayList = new ArrayList();
		ClaimSummaryDtls summaryDtls = null;
		StringTokenizer tokenizer = null;
		String installmentFlag = null;
		String cgpan = null;
		while (clmSummaryDtlsIterator.hasNext()) {
			String key = (String) clmSummaryDtlsIterator.next();
			tokenizer = new StringTokenizer(key,
					com.cgtsi.claim.ClaimConstants.CLM_DELIMITER1);
			boolean isFlagRead = false;
			boolean isCGPANRead = false;
			while (tokenizer.hasMoreTokens()) {
				String token = tokenizer.nextToken();
				if (!isCGPANRead) {
					if (!isFlagRead) {
						installmentFlag = token;
						isFlagRead = true;
						continue;
					}
					cgpan = token;
					isCGPANRead = true;
				}
			}
			if (installmentFlag
					.equals(com.cgtsi.claim.ClaimConstants.SECOND_INSTALLMENT)) {
				summaryDtls = new ClaimSummaryDtls();
				summaryDtls.setCgpan(cgpan);
				String amntClaimed = (String) claimForm
						.getClaimSummaryDetails(key);
				if ((amntClaimed.equals("")) || (amntClaimed == null)) {
					amntClaimed = "0.0";
				}
				summaryDtls.setAmount(Double.parseDouble(amntClaimed));
				if (!claimSummaryDtlsArrayList.contains(summaryDtls)) {
					claimSummaryDtlsArrayList.add(summaryDtls);
				}
			}
		}

		// Building the ClaimApplication object
		claimApplication.setMemberId(mId);
		claimApplication.setBorrowerId(bid);
		claimApplication.setDateOfIssueOfRecallNotice(sdf.parse(
				dateOfIssueOfRecallNotice, new ParsePosition(0)));

		// Building the legalproceedingsdetail object
		LegalProceedingsDetail lpd = new LegalProceedingsDetail();
		lpd.setBorrowerId(bid);
		lpd.setForumRecoveryProceedingsInitiated(forumRecoveryProceedingsInitiated);
		lpd.setSuitCaseRegNumber(caseregnumber);
		lpd.setNameOfForum(nameOfForum);
		lpd.setFilingDate(sdf.parse(legaldate, new ParsePosition(0)));
		lpd.setLocation(location);
		if ((amountClaimed != null) && (!(amountClaimed.equals("")))) {
			lpd.setAmountClaimed(Double.parseDouble(amountClaimed));
		}
		lpd.setCurrentStatusRemarks(currentStatusRemarks);
		lpd.setIsRecoveryProceedingsConcluded(proceedingsConcluded);
		claimApplication
				.setWhetherBorrowerIsWilfulDefaulter(whetherBorrowerIsWilfulDefaulter);
		claimApplication.setDtOfConclusionOfRecoveryProc(sdf.parse(
				dtOfConclusionOfRecoveryProc, new ParsePosition(0)));
		claimApplication
				.setWhetherAccntWrittenOffFromBooksOfMLI(whetherAccntWasWrittenOffBooks);
		claimApplication.setDtOnWhichAccntWrittenOff(sdf.parse(
				dtOnWhichAccntWrittenOff, new ParsePosition(0)));

		claimApplication.setLegalProceedingsDetails(lpd);
		if ((dateOfReleaseOfWC != null) && (!dateOfReleaseOfWC.equals(""))) {
			claimApplication.setDateOfReleaseOfWC(sdf.parse(dateOfReleaseOfWC,
					new ParsePosition(0)));
		}
		SecurityAndPersonalGuaranteeDtls sapg = new SecurityAndPersonalGuaranteeDtls();
		sapg.setDetailsAsOnDateOfSanction(dtlsAsOnSanctionDt);
		sapg.setDetailsAsOnDateOfNPA(dtlsAsOnNPA);
		sapg.setDetailsAsOnDateOfLodgementOfClaim(dtlsAsOnLodgemnt);
		sapg.setDetailsAsOnDateOfLodgementOfSecondClaim(dtlsAsOnLodgemntOfSecClm);
		claimApplication.setSecurityAndPersonalGuaranteeDtls(sapg);

		java.util.Date dtofseekofotsutilformat = sdf.parse(dateOfSeekingOTS,
				new ParsePosition(0));
		claimApplication.setDateOfSeekingOTS(dtofseekofotsutilformat);
		claimApplication.setRecoveryDetails(recoveryDetailsVector);
		claimApplication.setTermCapitalDtls(termCapitalDtls);
		claimApplication.setWorkingCapitalDtls(workingCapitalDtls);
		claimApplication.setClaimSummaryDtls(claimSummaryDtlsArrayList);

		claimForm.setClaimapplication(claimApplication);

		/*
		 * Cleaning up the Collection Objects
		 */
		// workingCapitalDtls.clear();
		workingCapitalDtls = null;
		// claimSummaryDtlsArrayList.clear();
		claimSummaryDtlsArrayList = null;
		// wcKeys.clear();
		wcKeys = null;
		// recoveryDetailsVector.clear();
		recoveryDetailsVector = null;
		// cgpanKeys.clear();
		cgpanKeys = null;
		// recoveryKeys.clear();
		recoveryKeys = null;
		claimForm.resetDisclaimerPage(mapping, request);
		return mapping.findForward("disclaimerpage");
	}

	public ActionForward saveApplicationForSecInstlmnt(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ClaimActionForm claimForm = (ClaimActionForm) form;
		ClaimApplication claimapplication = claimForm.getClaimapplication();
		ClaimsProcessor processor = new ClaimsProcessor();
		String nameofofficial = claimForm.getNameOfOfficial();
		String designation = claimForm.getDesignationOfOfficial();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		String claimsubmitteddt = claimForm.getClaimSubmittedDate();
		String place = claimForm.getPlace();
		// Get the borrower Id from the request object
		String borrowerId = ((claimForm.getBorrowerID()).toUpperCase()).trim();
		claimapplication.setNameOfOfficial(nameofofficial);
		claimapplication.setDesignationOfOfficial(designation);
		claimapplication.setClaimSubmittedDate(sdf.parse(claimsubmitteddt,
				new ParsePosition(0)));
		claimapplication.setPlace(place);
		claimapplication.setSecondInstallment(true);

		/*
		 * Added on 12/10/2004 to view the uploaded attachments.
		 */

		boolean internetUser = true;
		User userInfo = getUserInformation(request);
		// All users belong to member id 0000 0000 0000 are intranet users
		// except demo user.
		String userId = userInfo.getUserId();
		claimapplication.setCreatedModifiedy(userId);
		if ((userInfo.getBankId() + userInfo.getZoneId() + userInfo
				.getBranchId()).equals("000000000000")
				&& !userInfo.getUserId().equalsIgnoreCase("DEMOUSER")) {
			internetUser = false;
		}

		String clmRefNumber = processor.addClaimApplication(claimapplication,
				null, internetUser);

		// processor.insertRecallAndLegalAttachments(claimapplication,clmRefNumber,internetUser);

		/*
		 * Addition completed.
		 */
		claimForm.resetTheSecondClaimApplication(mapping, request);
		claimForm.setNameOfOfficial("");
		claimForm.setDesignationOfOfficial("");
		claimForm.setClaimSubmittedDate("");
		claimForm.setPlace("");
		Log.log(Log.INFO, "ClaimAction", "saveApplicationForSecInstlmnt",
				"Exited");

		request.setAttribute(
				"message",
				"Application for Second Claim Installment for "
						+ borrowerId
						+ "\n has been saved successfully. Claim Reference Number is : "
						+ clmRefNumber);
		return mapping.findForward("detailsSaved");
	}

	public ActionForward saveOTSReqDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ClaimActionForm claimForm = (ClaimActionForm) form;
		HttpSession session = (HttpSession) request.getSession(false);
		Boolean firstValue = (Boolean) session
				.getAttribute(ClaimConstants.CLM_FIRST_OTS_FROM_REC);
		Boolean secondValue = (Boolean) session
				.getAttribute(ClaimConstants.CLM_SECOND_OTS_FROM_REC);

		// claimForm.resetOTSRequestPage(mapping,request);
		ClaimsProcessor processor = new ClaimsProcessor();
		String memberId = (String) claimForm.getMemberId();
		String borrowerId = ((claimForm.getBorrowerID()).toUpperCase()).trim();
		// Capturing the details from the claim form object
		Hashtable loanDetails = null;
		String reasonForOTS = claimForm.getReasonForOTS();
		String willfulDefaulter = claimForm.getWilfullDefaulter();
		// Vector otsRequestDetails = new Vector();

		Map propsedAmntPaidByBorrower = claimForm
				.getProposedAmntPaidByBorrower();
		// Map proposedAmntSacrificed = claimForm.getProposedAmntSacrificed();
		// Map osAmntAsOnDates = claimForm.getOsAmntOnDateForOTS();

		Set propsedAmntPaidByBorrowerSet = propsedAmntPaidByBorrower.keySet();
		Iterator propsedAmntPaidByBorrowerIterator = propsedAmntPaidByBorrowerSet
				.iterator();
		String proposedAmntPaid = null;
		String amountSacrificed = null;
		String osAmntAsOnDate = null;
		Vector cgpandtls = new Vector();
		// Setting OTSRequestDetail object
		OTSRequestDetail reqDetail = new OTSRequestDetail();
		reqDetail.setMliId(memberId);
		reqDetail.setCgbid(borrowerId);
		reqDetail.setReasonForOTS(reasonForOTS);
		reqDetail.setWillfulDefaulter(willfulDefaulter);

		while (propsedAmntPaidByBorrowerIterator.hasNext()) {
			String key = (String) propsedAmntPaidByBorrowerIterator.next();
			proposedAmntPaid = (String) claimForm
					.getProposedAmntPaidByBorrower(key);
			if (proposedAmntPaid.equals("")) {
				proposedAmntPaid = "0";
			}
			// //System.out.println("proposedAmntPaid :" + proposedAmntPaid);
			amountSacrificed = (String) claimForm
					.getProposedAmntSacrificed(key);
			if (amountSacrificed.equals("")) {
				amountSacrificed = "0";
			}
			// //System.out.println("amountSacrificed :" + amountSacrificed);
			osAmntAsOnDate = (String) claimForm.getOsAmntOnDateForOTS(key);
			if (osAmntAsOnDate.equals("")) {
				osAmntAsOnDate = "0";
			}
			// //System.out.println("osAmntAsOnDate :" + osAmntAsOnDate);

			loanDetails = new Hashtable();
			loanDetails.put(ClaimConstants.CLM_CGPAN, key);
			// //System.out.println("CGPAN :" + key);
			loanDetails.put(
					ClaimConstants.CLM_OTS_PROPOSED_AMNT_TOBEPAID_BY_BORROWER,
					proposedAmntPaid);
			// //System.out.println("PROPOSED_AMNT_TOBEPAID_BY_BORROWER :" +
			// proposedAmntPaid);
			loanDetails.put(
					ClaimConstants.CLM_OTS_PROPOSED_AMNT_TOBESACRIFICED,
					amountSacrificed);
			// //System.out.println("PROPOSED_AMNT_TOBESACRIFICED :" +
			// amountSacrificed);
			loanDetails.put(ClaimConstants.CLM_OTS_OS_AMNT_AS_ON_DT,
					osAmntAsOnDate);
			// //System.out.println("OS_AMNT_AS_ON_DT :" + osAmntAsOnDate);
			if (!cgpandtls.contains(loanDetails)) {
				cgpandtls.addElement(loanDetails);
			}
		}
		reqDetail.setLoanDetails(cgpandtls);
		User user = getUserInformation(request);
		String userid = user.getUserId();
		processor.saveOTSDetail(reqDetail, userid);
		claimForm.resetOTSRequestPage(mapping, request);
		/*
		 * Cleaning up the Collection Objects
		 */
		// loanDetails.clear();
		loanDetails = null;
		// cgpandtls.clear();
		cgpandtls = null;
		boolean val = false;
		if (firstValue != null) {
			val = firstValue.booleanValue();
			// //System.out.println("Boolean Value :" + val);
			if (val) {
				session.setAttribute("mainMenu",
						MenuOptions.getMenu(MenuOptions.CP_CLAIM_FOR));
				session.setAttribute("subMenuItem", MenuOptions
						.getMenu(MenuOptions.CP_CLAIM_FOR_FIRST_INSTALLMENT));
				claimForm.setRecoveryFlag(ClaimConstants.DISBRSMNT_NO_FLAG);
				firstValue = null;
				return mapping.findForward("firstclaimdetails");
			}
		}
		if (secondValue != null) {
			val = secondValue.booleanValue();
			// //System.out.println("Boolean Value :" + val);
			if (val) {
				session.setAttribute("mainMenu",
						MenuOptions.getMenu(MenuOptions.CP_CLAIM_FOR));
				session.setAttribute("subMenuItem", MenuOptions
						.getMenu(MenuOptions.CP_CLAIM_FOR_SECOND_INSTALLMENT));
				claimForm.setRecoveryFlag(ClaimConstants.DISBRSMNT_NO_FLAG);
				secondValue = null;
				return mapping.findForward("secondclaimdetails");
			}
		}
		val = false;
		request.setAttribute("message", "OTS Details for Member :" + memberId
				+ " and Borrower :" + borrowerId
				+ "\nhave been saved successfully.");
		return mapping.findForward("success");
	}

	public ActionForward displayOTSProcessInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ClaimActionForm claimForm = (ClaimActionForm) form;
		claimForm.resetOTSProcessPage(mapping, request);
		ClaimsProcessor processor = new ClaimsProcessor();
		// OTSApprovalDetail otsApprovalDetail = null;
		// String borrowerId = null;

		Vector otsprocessdetails = processor.getToBeApprovedOTSRequests();
		if (otsprocessdetails != null) {
			if (otsprocessdetails.size() == 0) {
				request.setAttribute("message",
						"There are no OTS Request(s) to be processed.");
				return mapping.findForward("success");
			}
		}
		claimForm.setOtsprocessdetails(otsprocessdetails);

		/*
		 * Clearing up the Collection Objects
		 */
		// otsprocessdetails.clear();
		otsprocessdetails = null;
		return mapping.findForward("otsApproveDisplayPage");
	}

	public ActionForward displayOTSReferenceDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ClaimActionForm claimForm = (ClaimActionForm) form;
		String borrowerId = (String) request
				.getParameter(ClaimConstants.CLM_BORROWER_ID);
		String willfulDefaulter = null;
		String otsReason = null;
		ClaimsProcessor processor = new ClaimsProcessor();

		// OTSReferenceDetails is vector of HashMaps
		Vector otsReferenceDetails = processor
				.getOTSReferenceDetailsForBorrower(borrowerId);
		for (int i = 0; i < otsReferenceDetails.size(); i++) {
			HashMap temp = (HashMap) otsReferenceDetails.elementAt(i);
			if (temp != null) {
				willfulDefaulter = (String) temp
						.get(ClaimConstants.CLM_OTS_WILLFUL_DEFAULTER);
				otsReason = (String) temp
						.get(ClaimConstants.CLM_OTS_REASON_FOR_OTS);
				break;
			}
			// temp.clear();
			temp = null;
		}
		claimForm.setBorrowerID(borrowerId);
		claimForm.setReasonForOTS(otsReason);
		claimForm.setWilfullDefaulter(willfulDefaulter);
		claimForm.setOtsReferenceDetails(otsReferenceDetails);

		/*
		 * Clearing up the Collection Objects
		 */
		// otsReferenceDetails.clear();
		otsReferenceDetails = null;
		return mapping.findForward("otsReferenceDetails");
	}

	public ActionForward saveOTSProcessDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ClaimActionForm claimForm = (ClaimActionForm) form;
		// claimForm.resetOTSProcessPage(mapping,request);
		ClaimsProcessor processor = new ClaimsProcessor();
		Map decisions = claimForm.getDecision();
		Set decisionset = decisions.keySet();
		Iterator decisionIterator = decisionset.iterator();
		OTSApprovalDetail approvaldetail = null;
		String key = null;
		Vector processedOTSDetails = new Vector();
		String mliId = null;
		String bid = null;
		String otsrequestdate = null;
		StringTokenizer tokenizer = null;
		// Vector tokens = new Vector();
		boolean isMliRead = false;
		boolean isBidRead = false;
		boolean isOtsDateRead = false;
		// //System.out.println("Size of the Map :" + decisions.size());
		while (decisionIterator.hasNext()) {
			isMliRead = false;
			isBidRead = false;
			isOtsDateRead = false;
			key = (String) decisionIterator.next();
			tokenizer = new StringTokenizer(key, "#");
			while (tokenizer.hasMoreTokens()) {
				String thistoken = (String) tokenizer.nextToken();
				if (!isOtsDateRead) {
					if (!isBidRead) {
						if (!isMliRead) {
							mliId = thistoken;
							isMliRead = true;
							continue;
						}
						bid = thistoken;
						isBidRead = true;
						continue;
					}
					otsrequestdate = thistoken;
					isOtsDateRead = true;
					continue;
				}

			}
			String decision = (String) claimForm.getDecision(key);
			String remrks = (String) claimForm.getRemarks(key);
			if (((!decision.equals("")) && (!remrks.equals("")))
					|| ((decision.equals("AP")) && (remrks.equals("")))) {
				approvaldetail = new OTSApprovalDetail();
				approvaldetail.setMliId(mliId);
				approvaldetail.setCgbid(bid);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
				approvaldetail.setOtsRequestDate(sdf.parse(otsrequestdate,
						new ParsePosition(0)));
				approvaldetail.setDecision(decision);
				approvaldetail.setRemarks(remrks);

				// adding the OTSApprovalDetail object to the vector
				if (!processedOTSDetails.contains(approvaldetail)) {
					processedOTSDetails.addElement(approvaldetail);
				}
			}
		}
		User user = getUserInformation(request);
		String userid = user.getUserId();
		if (processedOTSDetails.size() == 0) {
			request.setAttribute("message", "There are no OTS Details to save.");
			return mapping.findForward("success");
		}

		// //System.out.println("Size of the vector :"
		// +processedOTSDetails.size());
		processor.saveOTSProcessingResults(processedOTSDetails, userid);

		/*
		 * Clearing up the Collection objects
		 */
		// processedOTSDetails.clear();
		processedOTSDetails = null;
		claimForm.resetOTSProcessPage(mapping, request);
		request.setAttribute("message",
				"OTS Processing Details have been saved.");
		return mapping.findForward("success");
	}

	public ActionForward displayClaimApproval(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(Log.INFO, "ClaimAction", "displayClaimApproval()", "Entered");

		// //System.out.println("ClaimAction displayClaimApproval() Entered");
		ClaimActionForm claimForm = (ClaimActionForm) form;
		ClaimsProcessor processor = new ClaimsProcessor();
		String flagClmRefDtl = (String) claimForm.getClmRefDtlSet();
		// java.sql.Date stDt =
		// java.sql.Date.valueOf(DateHelper.stringToSQLdate(claimForm.getDateOfTheDocument36()));
		// java.sql.Date endDt =
		// java.sql.Date.valueOf(DateHelper.stringToSQLdate(claimForm.getDateOfTheDocument37()));
		// //System.out.println("From date:"+claimForm.getDateOfTheDocument36());
		// //System.out.println("To date:"+claimForm.getDateOfTheDocument37());

		Log.log(Log.INFO, "ClaimAction", "displayClaimApproval()",
				"*******************************");
		// //System.out.println("ClaimAction displayClaimApproval() flagClmRefDtl :"+flagClmRefDtl);
		Log.log(Log.INFO, "ClaimAction", "displayClaimApproval()",
				"flagClmRefDtl :" + flagClmRefDtl);
		Log.log(Log.INFO, "ClaimAction", "displayClaimApproval()",
				"*******************************");
		ClaimDetail clmdtl = null;
		String clmRefNum = null;
		String payAmntNow = null;
		String userRemarks = null;
		String standardRemarks = null;
		double tcServiceFee = 0;
		double wcServiceFee = 0;
		double tcClaimEligibleAmt = 0;
		double wcClaimEligibleAmt = 0;
		double tcFirstInstallment = 0;
		double wcFirstInstallment = 0;
		double totalTCOSAmountAsOnNPA = 0;
		double totalWCOSAmountAsOnNPA = 0;
		double tcrecovery = 0;
		double wcrecovery = 0;
		// double totalClaimEligibleAmt = 0;
		double tcIssued = 0;
		double wcIssued = 0;
		Administrator admin = new Administrator();

		// The CGTSI active userIds is populated from the database.
		String cgtsiBankId = Constants.CGTSI_USER_BANK_ID;
		// //System.out.println("cgtsiBankId:"+cgtsiBankId);
		String cgtsiZoneId = Constants.CGTSI_USER_ZONE_ID;
		// //System.out.println("cgtsiZoneId:"+cgtsiZoneId);

		String cgtsiBrnId = Constants.CGTSI_USER_BRANCH_ID;
		// //System.out.println("cgtsiBrnId:"+cgtsiBrnId);
		ArrayList userIds = admin.getUsers(cgtsiBankId + cgtsiZoneId
				+ cgtsiBrnId);

		// Remove the logged in userId and the Admin userId.
		User loggedUser = getUserInformation(request);
		String loggedUserId = loggedUser.getUserId();
		// //System.out.println("loggedUserId:"+loggedUserId);
		userIds.remove(loggedUserId);
		userIds.remove("ADMIN");
		if (userIds.contains("DEMOUSER")) {
			userIds.remove("DEMOUSER");
		}
		if (userIds.contains("AUDITOR")) {
			userIds.remove("AUDITOR");
		}
		claimForm.setUserIds(userIds);

		// //System.out.println("Flag from Claim Ref Dtl :" +
		// claimForm.getClmRefDtlSet());
		// The vector contains objects of ClaimDetail for first claim
		double maxClmApprvdAmnt = 0.0;
		java.util.Date fromDate = null;
		java.util.Date toDate = null;
		User user = getUserInformation(request);
		String designation = user.getDesignation();
		// //System.out.println("designation:"+designation);
		String loggedUsr = user.getUserId();
		Log.log(Log.INFO, "ClaimAction", "displayClaimApproval()",
				"designation :" + designation);
		HashMap clmLimitDetails = processor.getClaimLimitDtls(designation);
		maxClmApprvdAmnt = ((Double) clmLimitDetails
				.get(ClaimConstants.CLM_MAX_APPROVAL_AMOUNT)).doubleValue();
		// //System.out.println("maxClmApprvdAmnt:"+maxClmApprvdAmnt);
		Log.log(Log.INFO, "ClaimAction", "displayClaimApproval()",
				"maxClmApprvdAmnt :" + maxClmApprvdAmnt);
		fromDate = (java.util.Date) clmLimitDetails
				.get(ClaimConstants.CLM_VALID_FROM_DT);
		// //System.out.println("fromDate :" + fromDate);
		toDate = (java.util.Date) clmLimitDetails
				.get(ClaimConstants.CLM_VALID_TO_DT);
		// //System.out.println("toDate :" + toDate);
		java.util.Date dateofReceipt = null;
		if (flagClmRefDtl != null) {
			if (flagClmRefDtl.equals(ClaimConstants.DISBRSMNT_YES_FLAG)) {
				clmdtl = claimForm.getClaimdetail();
				if (clmdtl != null) {
					clmRefNum = (String) clmdtl.getClaimRefNum();
					// //System.out.println("clmRefNum:"+clmRefNum);
					dateofReceipt = claimForm.getDateofReceipt();
					// //System.out.println("dateofReceipt:"+dateofReceipt);
					tcIssued = claimForm.getTcIssued();
					// //System.out.println("tcIssued:"+tcIssued);
					wcIssued = claimForm.getWcIssued();
					// //System.out.println("wcIssued:"+wcIssued);
					userRemarks = (String) clmdtl.getComments();
					// //System.out.println("User Remarks:"+userRemarks);
					totalTCOSAmountAsOnNPA = clmdtl.getTotalTCOSAmountAsOnNPA();
					totalWCOSAmountAsOnNPA = clmdtl.getTotalWCOSAmountAsOnNPA();
					// //System.out.println("totalTCOSAmountAsOnNPA:"+totalTCOSAmountAsOnNPA);
					// //System.out.println("totalWCOSAmountAsOnNPA:"+totalWCOSAmountAsOnNPA);
					tcrecovery = claimForm.getTcrecovery();
					wcrecovery = claimForm.getWcrecovery();
					// //System.out.println("tcrecovery:"+tcrecovery);
					// //System.out.println("wcrecovery:"+wcrecovery);
					// totalClaimEligibleAmt =
					// claimForm.getTotalClaimEligibleAmt();
					// //System.out.println("totalClaimEligibleAmt:"+totalClaimEligibleAmt);
					// tcClaimEligibleAmt = claimForm.getTcClaimEligibleAmt();

					if ((Math.min(tcIssued, totalTCOSAmountAsOnNPA) - (tcrecovery)) <= 500000) {
						tcClaimEligibleAmt = Math.round((Math.min(tcIssued,
								totalTCOSAmountAsOnNPA) - (tcrecovery)) * 0.80);
					} else {
						tcClaimEligibleAmt = Math.round((Math.min(tcIssued,
								totalTCOSAmountAsOnNPA) - (tcrecovery)) * 0.75);
					}
					// //System.out.println("tcClaimEligibleAmt:"+tcClaimEligibleAmt);
					// wcClaimEligibleAmt = claimForm.getWcClaimEligibleAmt();
					if ((Math.min(wcIssued, totalWCOSAmountAsOnNPA) - (wcrecovery)) <= 500000) {
						wcClaimEligibleAmt = Math.round((Math.min(wcIssued,
								totalWCOSAmountAsOnNPA) - (wcrecovery)) * 0.80);
					} else {
						wcClaimEligibleAmt = Math.round((Math.min(wcIssued,
								totalWCOSAmountAsOnNPA) - (wcrecovery)) * 0.75);
					}

					// tcClaimEligibleAmt
					// =Math.round((Math.min(tcIssued,totalTCOSAmountAsOnNPA)-(tcrecovery))*0.75);
					// //System.out.println("tcClaimEligibleAmt:"+tcClaimEligibleAmt);
					// wcClaimEligibleAmt = claimForm.getWcClaimEligibleAmt();
					// wcClaimEligibleAmt
					// =Math.round((Math.min(wcIssued,totalWCOSAmountAsOnNPA)-(wcrecovery))*0.75);
					// //System.out.println("wcClaimEligibleAmt:"+wcClaimEligibleAmt);
					// tcFirstInstallment = claimForm.getTcFirstInstallment();
					tcFirstInstallment = Math.round(tcClaimEligibleAmt * 0.75);
					// //System.out.println("tcFirstInstallment:"+tcFirstInstallment);
					// wcFirstInstallment = claimForm.getWcFirstInstallment();
					wcFirstInstallment = Math.round(wcClaimEligibleAmt * 0.75);
					// //System.out.println("wcFirstInstallment:"+wcFirstInstallment);
					tcServiceFee = claimForm.getAsfDeductableforTC();
					// //System.out.println("tcServiceFee:"+tcServiceFee);
					wcServiceFee = claimForm.getAsfDeductableforWC();
					// //System.out.println("wcServiceFee:"+wcServiceFee);
					// payAmntNow = (String)clmdtl.getTotalAmtPayNow();
					payAmntNow = Double.toString(tcFirstInstallment
							+ wcFirstInstallment - tcServiceFee - wcServiceFee);
					clmdtl.setTotalAmtPayNow(payAmntNow);
					// //System.out.println("payAmntNow:"+payAmntNow);

					CPDAO cpdao = new CPDAO();
					cpdao.insertClaimProcessDetails(clmRefNum, userRemarks,
							tcServiceFee, wcServiceFee, tcClaimEligibleAmt,
							wcClaimEligibleAmt, tcFirstInstallment,
							wcFirstInstallment, totalTCOSAmountAsOnNPA,
							totalWCOSAmountAsOnNPA, tcrecovery, wcrecovery,
							dateofReceipt);
					if ((payAmntNow != null) && (!payAmntNow.equals(""))) {
						if ((Double.parseDouble(payAmntNow)) < 0.0) {
							payAmntNow = "0.0";
						}
					} else {
						payAmntNow = "0.0";
					}
					/*
					 * double clmEligibleAmnt = clmdtl.getEligibleClaimAmt();
					 * if((payAmntNow != null) &&
					 * (Double.parseDouble(payAmntNow)) > clmEligibleAmnt) {
					 * throw new InvalidDataException(
					 * "Amount Payable now cannot be more than the Claim Eligibility Amount."
					 * ); }
					 */
				}
			}
			/*
			 * else { flagClmRefDtl=""; payAmntNow=null;
			 * clmdtl.setTotalAmtPayNow(null); }
			 */
		}
		String clmStatus = null;
		String comments = null;
		String forwardedToUser = null;
		String thiskey = null;
		int outOfLimit = 0;
		double clmEligibleAmnt = 0.0;
		java.util.Date currentDate = new java.util.Date();
		Vector firstinstllmntclaims = processor
				.getClaimProcessingDetails(ClaimConstants.FIRST_INSTALLMENT);
		/*
		 * for(int i=0; i<firstinstllmntclaims.size(); i++) { ClaimDetail clmDtl
		 * = (ClaimDetail)firstinstllmntclaims.elementAt(i); if(clmDtl == null)
		 * { continue; } String memId = (String)clmDtl.getMliId();
		 * Log.log(Log.INFO
		 * ,"ClaimAction","displayClaimApproval()","******************************"
		 * ); Log.log(Log.INFO,"ClaimAction","displayClaimApproval()","memId :"
		 * + memId); String claimrefnumber = (String)clmDtl.getClaimRefNum();
		 * Log
		 * .log(Log.INFO,"ClaimAction","displayClaimApproval()","claimrefnumber :"
		 * + claimrefnumber); String claimStatus =
		 * (String)clmDtl.getClmStatus();
		 * Log.log(Log.INFO,"ClaimAction","displayClaimApproval()","clmStatus :"
		 * + claimStatus); String commnts = (String)clmDtl.getComments(); String
		 * forwardedToUsr = (String)clmDtl.getForwaredToUser();
		 * Log.log(Log.INFO,
		 * "ClaimAction","displayClaimApproval()","forwardedToUser :" +
		 * forwardedToUsr);
		 * Log.log(Log.INFO,"ClaimAction","displayClaimApproval()"
		 * ,"******************************"); }
		 */
		// Vector forwardedFirstClms = new Vector();
		if (firstinstllmntclaims != null) {
			for (int i = 0; i < firstinstllmntclaims.size(); i++) {
				ClaimDetail clmDtl = (ClaimDetail) firstinstllmntclaims
						.elementAt(i);
				if (clmDtl == null) {
					continue;
				}
				Log.log(Log.INFO, "ClaimAction", "displayClaimApproval()",
						"##################");
				String memId = (String) clmDtl.getMliId();
				// //System.out.println("MemberId:"+memId);
				Log.log(Log.INFO, "ClaimAction", "displayClaimApproval()",
						"memId :" + memId);
				String claimrefnumber = (String) clmDtl.getClaimRefNum();
				// //System.out.println("claimrefnumber:"+claimrefnumber);
				Log.log(Log.INFO, "ClaimAction", "displayClaimApproval()",
						"claimrefnumber :" + claimrefnumber);
				clmStatus = (String) clmDtl.getClmStatus();
				// //System.out.println("clmStatus:"+clmStatus);
				Log.log(Log.INFO, "ClaimAction", "displayClaimApproval()",
						"clmStatus :" + clmStatus);
				comments = (String) clmDtl.getComments();
				// //System.out.println("comments:"+comments);
				forwardedToUser = (String) clmDtl.getForwaredToUser();
				// //System.out.println("forwardedToUser:"+forwardedToUser);
				Log.log(Log.INFO, "ClaimAction", "displayClaimApproval()",
						"forwardedToUser :" + forwardedToUser);
				Log.log(Log.INFO, "ClaimAction", "displayClaimApproval()",
						"###################");
				clmEligibleAmnt = clmDtl.getEligibleClaimAmt();
				// //System.out.println("Line number 5913 clmEligibleAmnt :" +
				// clmEligibleAmnt);
				// //System.out.println("maxClmApprvdAmnt :" + maxClmApprvdAmnt);

				if (((fromDate != null) && (!fromDate.equals("")))
						&& ((toDate != null) && (!toDate.equals("")))) {
					if (((fromDate.compareTo(currentDate)) <= 0)
							&& ((currentDate.compareTo(toDate)) <= 0)) {
						if (clmEligibleAmnt > maxClmApprvdAmnt) {
							// //System.out.println("The clmEligibleAmnt is greater than the maxClmApprvdAmnt");
							outOfLimit++;
							firstinstllmntclaims.remove(i);
							i--;
							continue;
						}
					}
				}
				if (((fromDate != null) && (!fromDate.equals("")))
						&& (toDate == null)) {
					if ((fromDate.compareTo(currentDate)) <= 0) {
						if (clmEligibleAmnt > maxClmApprvdAmnt) {
							outOfLimit++;
							firstinstllmntclaims.remove(i);
							i--;
							continue;
						}
					}
				}

				if (clmStatus != null) {
					if ((clmStatus.equals(ClaimConstants.CLM_FORWARD_STATUS))
							|| (clmStatus
									.equals(ClaimConstants.CLM_HOLD_STATUS))) {
						thiskey = ClaimConstants.FIRST_INSTALLMENT
								+ ClaimConstants.CLM_DELIMITER1 + memId
								+ ClaimConstants.CLM_DELIMITER1
								+ claimrefnumber;
						claimForm.setDecision(thiskey, clmStatus);
						claimForm.setRemarks(thiskey, comments);
						Log.log(Log.INFO, "ClaimAction",
								"displayClaimApproval()", "loggedUsr :"
										+ loggedUsr);
						if ((forwardedToUser != null)
								&& (!forwardedToUser
										.equalsIgnoreCase(loggedUsr))) {
							// forwardedFirstClms.addElement(clmDtl);
							firstinstllmntclaims.remove(i);
							--i;
						}
						if ((forwardedToUser != null)
								&& (forwardedToUser.equalsIgnoreCase(loggedUsr))) {
							// forwardedFirstClms.addElement(clmDtl);
							Log.log(Log.INFO, "ClaimAction",
									"displayClaimApproval()",
									"*******************");
							Log.log(Log.INFO, "ClaimAction",
									"displayClaimApproval()", "thiskey :"
											+ thiskey);
							Log.log(Log.INFO, "ClaimAction",
									"displayClaimApproval()",
									"forwardedToUser :" + forwardedToUser);
							Log.log(Log.INFO, "ClaimAction",
									"displayClaimApproval()",
									"*******************");
							claimForm.setForwardedToIds(thiskey,
									forwardedToUser);
							if (userIds.contains(forwardedToUser)) {
								claimForm.setUserIds(userIds);
							}
						}
					}
				}
			}
		}

		// The vector contains objects of ClaimDetail for second claim
		Vector secinstllmntclaims = processor
				.getClaimProcessingDetails(ClaimConstants.SECOND_INSTALLMENT);
		// Vector forwardedSecClms = new Vector();
		if (secinstllmntclaims != null) {
			for (int i = 0; i < secinstllmntclaims.size(); i++) {
				ClaimDetail clmDtl = (ClaimDetail) secinstllmntclaims
						.elementAt(i);
				if (clmDtl == null) {
					continue;
				}
				clmEligibleAmnt = clmDtl.getEligibleClaimAmt();
				if (((fromDate != null) && (!fromDate.equals("")))
						&& ((toDate != null) && (!toDate.equals("")))) {
					if (((fromDate.compareTo(currentDate)) <= 0)
							&& ((currentDate.compareTo(toDate)) <= 0)) {
						if (clmEligibleAmnt > maxClmApprvdAmnt) {
							outOfLimit++;
							secinstllmntclaims.remove(i);
							i--;
							continue;
						}
					}
				}
				if (((fromDate != null) && (!fromDate.equals("")))
						&& (toDate == null)) {
					if ((fromDate.compareTo(currentDate)) <= 0) {
						if (clmEligibleAmnt > maxClmApprvdAmnt) {
							outOfLimit++;
							secinstllmntclaims.remove(i);
							i--;
							continue;
						}
					}
				}
				String memId = (String) clmDtl.getMliId();
				String claimrefnumber = (String) clmDtl.getClaimRefNum();
				String cgclan = (String) clmDtl.getCGCLAN();
				clmStatus = (String) clmDtl.getClmStatus();
				comments = (String) clmDtl.getComments();
				forwardedToUser = (String) clmDtl.getForwaredToUser();
				Log.log(Log.INFO, "ClaimAction", "displayClaimApproval()",
						"forwardedToUser :" + forwardedToUser);
				if (clmStatus != null) {
					if ((clmStatus.equals(ClaimConstants.CLM_FORWARD_STATUS))
							|| (clmStatus
									.equals(ClaimConstants.CLM_HOLD_STATUS))) {
						thiskey = ClaimConstants.SECOND_INSTALLMENT
								+ ClaimConstants.CLM_DELIMITER1 + memId
								+ ClaimConstants.CLM_DELIMITER1
								+ claimrefnumber
								+ ClaimConstants.CLM_DELIMITER1 + cgclan;
						claimForm.setDecision(thiskey, clmStatus);
						claimForm.setRemarks(thiskey, comments);
						Log.log(Log.INFO, "ClaimAction",
								"displayClaimApproval()", "loggedUsr :"
										+ loggedUsr);
						if ((forwardedToUser != null)
								&& (!forwardedToUser
										.equalsIgnoreCase(loggedUsr))) {
							// forwardedSecClms.addElement(clmDtl);
							secinstllmntclaims.remove(i);
							--i;
						}
						if ((forwardedToUser != null)
								&& (forwardedToUser.equalsIgnoreCase(loggedUsr))) {
							// forwardedSecClms.addElement(clmDtl);
							claimForm.setForwardedToIds(thiskey,
									forwardedToUser);
						}
					}
				}
			}
		}

		if ((firstinstllmntclaims.size() == 0)
				&& (secinstllmntclaims.size() == 0)) {
			request.setAttribute("message",
					"There are no Claim Application(s) to process.");
			return mapping.findForward("success");
		}

		for (int i = 0; i < firstinstllmntclaims.size(); i++) {
			ClaimDetail cd = (ClaimDetail) firstinstllmntclaims.elementAt(i);
			String crn = cd.getClaimRefNum();
			if (crn.equals(clmRefNum)) {
				cd = (ClaimDetail) firstinstllmntclaims.remove(i);
				cd.setTotalAmtPayNow(payAmntNow);
				cd.setComments(userRemarks);
				firstinstllmntclaims.addElement(cd);
			}
		}
		for (int i = 0; i < secinstllmntclaims.size(); i++) {
			ClaimDetail cd = (ClaimDetail) secinstllmntclaims.elementAt(i);
			String crn = cd.getClaimRefNum();
			// //System.out.println("Line number6072 crn:"+crn);
			if (crn.equals(clmRefNum)) {
				cd = (ClaimDetail) secinstllmntclaims.remove(i);
				cd.setTotalAmtPayNow(payAmntNow);
				// //System.out.println("Line number 6077 payAmntNow:"+payAmntNow);
				secinstllmntclaims.addElement(cd);
			}
		}
		clmdtl = null;
		clmRefNum = null;
		payAmntNow = null;
		claimForm.setLimit(outOfLimit);
		// //System.out.println("Line number 6085 outOfLimit:"+outOfLimit);
		claimForm.setFirstInstallmentClaims(firstinstllmntclaims);
		// //System.out.println("Number of applications for first claim installments :"
		// + firstinstllmntclaims.size());
		claimForm.setFirstCounter(firstinstllmntclaims.size());
		claimForm.setSecondInstallmentClaims(secinstllmntclaims);
		// //System.out.println("Number of applications for second claim installments :"
		// + secinstllmntclaims.size());
		claimForm.setSecondCounter(secinstllmntclaims.size());
		claimForm.setClmRefDtlSet(ClaimConstants.DISBRSMNT_NO_FLAG);

		/*
		 * Clearing up the Collection Objects
		 */
		// firstinstllmntclaims.clear();
		firstinstllmntclaims = null;
		// secinstllmntclaims.clear();
		secinstllmntclaims = null;
		Log.log(Log.INFO, "ClaimAction", "displayClaimApproval()", "Exited");
		return mapping.findForward("displayClaimsApprovalPage");
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws java.lang.Exception
	 */
	public ActionForward displayClaimApprovalMod(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(Log.INFO, "ClaimAction", "displayClaimApprovalMod()", "Entered");
		// //System.out.println("ClaimAction displayClaimApprovalMod() Entered");
		ClaimActionForm claimForm = (ClaimActionForm) form;
		ClaimsProcessor processor = new ClaimsProcessor();
		String flagClmRefDtl = (String) claimForm.getClmRefDtlSet();
		java.sql.Date stDt = java.sql.Date.valueOf(DateHelper
				.stringToSQLdate(claimForm.getDateOfTheDocument36()));
		java.sql.Date endDt = java.sql.Date.valueOf(DateHelper
				.stringToSQLdate(claimForm.getDateOfTheDocument37()));
		// //System.out.println("From date:"+claimForm.getDateOfTheDocument36());
		// //System.out.println("To date:"+claimForm.getDateOfTheDocument37());

		Log.log(Log.INFO, "ClaimAction", "displayClaimApprovalMod()",
				"*******************************");
		// //System.out.println("ClaimAction displayClaimApproval() flagClmRefDtl :"+flagClmRefDtl);
		Log.log(Log.INFO, "ClaimAction", "displayClaimApprovalMod()",
				"flagClmRefDtl :" + flagClmRefDtl);
		Log.log(Log.INFO, "ClaimAction", "displayClaimApprovalMod()",
				"*******************************");
		ClaimDetail clmdtl = null;
		String clmRefNum = null;
		String payAmntNow = null;
		String userRemarks = null;
		String standardRemarks = null;
		String microFlag = null;

		String womenOwned = null;

		String nerFlag = "N";

		double tcServiceFee = 0;
		double wcServiceFee = 0;
		double tcClaimEligibleAmt = 0;
		double wcClaimEligibleAmt = 0;
		double tcFirstInstallment = 0;
		double wcFirstInstallment = 0;
		double totalTCOSAmountAsOnNPA = 0;
		double totalWCOSAmountAsOnNPA = 0;
		double tcrecovery = 0;
		double wcrecovery = 0;
		// double totalClaimEligibleAmt = 0;
		double tcIssued = 0;
		double wcIssued = 0;
		double tcClaimEligible1 = 0.0;
		double tcClaimEligible2 = 0.0;
		double wcClaimEligible1 = 0.0;
		double wcClaimEligible2 = 0.0;
		double tcClaimEligible = 0.0;
		double wcClaimEligible = 0.0;
		double tcNetOutstanding = 0.0;
		double wcNetOutstanding = 0.0;
		double r1 = 0.0;
		double r2 = 0.0;
		String schemeName = "";

		String falgforCasesafet = "N";

		Administrator admin = new Administrator();

		// The CGTSI active userIds is populated from the database.
		String cgtsiBankId = Constants.CGTSI_USER_BANK_ID;
		// //System.out.println("cgtsiBankId:"+cgtsiBankId);
		String cgtsiZoneId = Constants.CGTSI_USER_ZONE_ID;
		// //System.out.println("cgtsiZoneId:"+cgtsiZoneId);

		String cgtsiBrnId = Constants.CGTSI_USER_BRANCH_ID;
		// //System.out.println("cgtsiBrnId:"+cgtsiBrnId);
		ArrayList userIds = admin.getUsers(cgtsiBankId + cgtsiZoneId
				+ cgtsiBrnId);

		// Remove the logged in userId and the Admin userId.
		User loggedUser = getUserInformation(request);
		String loggedUserId = loggedUser.getUserId();
		// //System.out.println("loggedUserId:"+loggedUserId);
		userIds.remove(loggedUserId);
		userIds.remove("ADMIN");
		if (userIds.contains("DEMOUSER")) {
			userIds.remove("DEMOUSER");
		}
		if (userIds.contains("AUDITOR")) {
			userIds.remove("AUDITOR");
		}
		claimForm.setUserIds(userIds);
		claimForm.setUserId(loggedUserId);

		// //System.out.println("Flag from Claim Ref Dtl :" +
		// claimForm.getClmRefDtlSet());
		// The vector contains objects of ClaimDetail for first claim
		double maxClmApprvdAmnt = 0.0;
		java.util.Date fromDate = null;
		java.util.Date toDate = null;
		java.util.Date dateofReceipt = null;
		User user = getUserInformation(request);
		String designation = user.getDesignation();
		// //System.out.println("designation:"+designation);
		String loggedUsr = user.getUserId();
		Log.log(Log.INFO, "ClaimAction", "displayClaimApprovalMod()",
				"designation :" + designation);
		HashMap clmLimitDetails = processor.getClaimLimitDtls(designation);
		maxClmApprvdAmnt = ((Double) clmLimitDetails
				.get(ClaimConstants.CLM_MAX_APPROVAL_AMOUNT)).doubleValue();
		// //System.out.println("maxClmApprvdAmnt:"+maxClmApprvdAmnt);
		Log.log(Log.INFO, "ClaimAction", "displayClaimApprovalMod()",
				"maxClmApprvdAmnt :" + maxClmApprvdAmnt);
		fromDate = (java.util.Date) clmLimitDetails
				.get(ClaimConstants.CLM_VALID_FROM_DT);
		// //System.out.println("fromDate :" + fromDate);
		toDate = (java.util.Date) clmLimitDetails
				.get(ClaimConstants.CLM_VALID_TO_DT);
		// //System.out.println("toDate :" + toDate);
		if (flagClmRefDtl != null) {
			if (flagClmRefDtl.equals(ClaimConstants.DISBRSMNT_YES_FLAG)) {
				clmdtl = claimForm.getClaimdetail();
				if (clmdtl != null) {
					clmRefNum = (String) clmdtl.getClaimRefNum();
					// //System.out.println("clmRefNum:"+clmRefNum);
					dateofReceipt = claimForm.getDateofReceipt();
					// //System.out.println("dateofReceipt:"+dateofReceipt);
					tcIssued = claimForm.getTcIssued();
					// //System.out.println("tcIssued:"+tcIssued);
					wcIssued = claimForm.getWcIssued();
					// //System.out.println("wcIssued:"+wcIssued);
					userRemarks = (String) clmdtl.getComments();
					standardRemarks = (String) clmdtl.getStandardRemarks();
					userRemarks = standardRemarks.concat(userRemarks);
					// //System.out.println("User Remarks:"+userRemarks);
					totalTCOSAmountAsOnNPA = clmdtl.getTotalTCOSAmountAsOnNPA();
					totalWCOSAmountAsOnNPA = clmdtl.getTotalWCOSAmountAsOnNPA();
					tcrecovery = claimForm.getTcrecovery();
					wcrecovery = claimForm.getWcrecovery();
					microFlag = claimForm.getMicroCategory();
					falgforCasesafet = claimForm.getFalgforCasesafet();
					womenOwned = claimForm.getWomenOperated();
					nerFlag = claimForm.getNerFlag();

					schemeName = request.getParameter("schemeName");

					tcNetOutstanding = Math.min(tcIssued,
							totalTCOSAmountAsOnNPA) - (tcrecovery);
					wcNetOutstanding = Math.min(wcIssued,
							totalWCOSAmountAsOnNPA) - (wcrecovery);

					if (schemeName.equals("RSF")) {
						tcClaimEligibleAmt = Math
								.round(tcNetOutstanding * 0.50);
						wcClaimEligibleAmt = Math
								.round(wcNetOutstanding * 0.50);
					} else if (((tcIssued + wcIssued) <= 500000)
							&& (microFlag.equals("Y"))) {
						if (falgforCasesafet.equals("Y")) {

							tcClaimEligibleAmt = Math
									.round((Math.min(tcIssued,
											totalTCOSAmountAsOnNPA) - (tcrecovery)) * 0.85);
						} else if (falgforCasesafet.equals("N")) {
							// jai code
							tcClaimEligibleAmt = Math
									.round((Math.min(tcIssued,
											totalTCOSAmountAsOnNPA) - (tcrecovery)) * 0.80);
						}

						// tcClaimEligibleAmt
						// =Math.round((Math.min(tcIssued,totalTCOSAmountAsOnNPA)-(tcrecovery))*0.80);

					} else if (((tcIssued + wcIssued) <= 5000000)
							&& (womenOwned.equals("F") || nerFlag.equals("Y"))) {
						tcClaimEligibleAmt = Math.round((Math.min(tcIssued,
								totalTCOSAmountAsOnNPA) - (tcrecovery)) * 0.80);
					} else if (((tcIssued + wcIssued) <= 500000)
							&& (microFlag.equals("N"))) {
						tcClaimEligibleAmt = Math.round((Math.min(tcIssued,
								totalTCOSAmountAsOnNPA) - (tcrecovery)) * 0.75);
					} else {
						tcClaimEligibleAmt = Math.round((Math.min(tcIssued,
								totalTCOSAmountAsOnNPA) - (tcrecovery)) * 0.75);
					}
					// //System.out.println("tcClaimEligibleAmt:"+tcClaimEligibleAmt);
					// wcClaimEligibleAmt = claimForm.getWcClaimEligibleAmt();
					// if((Math.min(wcIssued,totalWCOSAmountAsOnNPA)-(wcrecovery))<=500000){

					if (schemeName.equals("RSF")) {
						wcClaimEligibleAmt = Math
								.round(wcNetOutstanding * 0.50);
					} else if (((tcIssued + wcIssued) <= 500000)
							&& (microFlag.equals("Y"))) {
						// jai code
						if (falgforCasesafet.equals("Y")) {

							wcClaimEligibleAmt = Math
									.round((Math.min(wcIssued,
											totalWCOSAmountAsOnNPA) - (wcrecovery)) * 0.85);

						} else if (falgforCasesafet.equals("N")) {
							// jai code
							wcClaimEligibleAmt = Math
									.round((Math.min(wcIssued,
											totalWCOSAmountAsOnNPA) - (wcrecovery)) * 0.80);
						}
						// wcClaimEligibleAmt
						// =Math.round((Math.min(wcIssued,totalWCOSAmountAsOnNPA)-(wcrecovery))*0.80);
					} else if (((tcIssued + wcIssued) <= 5000000)
							&& (womenOwned.equals("F") || nerFlag.equals("Y"))) {
						wcClaimEligibleAmt = Math.round((Math.min(wcIssued,
								totalWCOSAmountAsOnNPA) - (wcrecovery)) * 0.80);

					} else if (((tcIssued + wcIssued) <= 500000)
							&& (microFlag.equals("N"))) {
						wcClaimEligibleAmt = Math.round((Math.min(wcIssued,
								totalWCOSAmountAsOnNPA) - (wcrecovery)) * 0.75);

					} else if (((schemeName.equals("CGFSI"))
							&& (tcIssued + wcIssued) > 5000000 && (nerFlag
							.equals("Y")))
							|| ((schemeName.equals("CGFSI"))
									&& (womenOwned.equals("F"))
									&& (tcIssued + wcIssued) > 5000000 && (nerFlag
									.equals("N")))) {

						long l = (int) Math
								.round(((tcIssued + wcIssued - 5000000) / (tcIssued + wcIssued)) * 1000000000); // truncates
						r2 = l / 1000000000.0;
						long l2 = (int) Math
								.round(((5000000) / (tcIssued + wcIssued)) * 1000000000); // truncates
						r1 = l2 / 1000000000.0;
						tcClaimEligible1 = Math.round(tcNetOutstanding * 0.80
								* (r1));
						tcClaimEligible2 = Math.round(tcNetOutstanding * 0.50
								* (r2));
						tcClaimEligibleAmt = tcClaimEligible1
								+ tcClaimEligible2;
						wcClaimEligible1 = Math.round(wcNetOutstanding * 0.80
								* (r1));
						wcClaimEligible2 = Math.round(wcNetOutstanding * 0.50
								* (r2));
						wcClaimEligibleAmt = wcClaimEligible1
								+ wcClaimEligible2;

						// tcClaimEligible = Math.round(tcNetOutstanding *
						// rate);
						// wcClaimEligible = Math.round(wcNetOutstanding *
						// rate);
					} else if ((schemeName.equals("CGFSI"))
							&& (womenOwned.equals("M"))
							&& (tcIssued + wcIssued) > 5000000
							&& (nerFlag.equals("N"))) {

						long l = (int) Math
								.round(((tcIssued + wcIssued - 5000000) / (tcIssued + wcIssued)) * 1000000000); // truncates
						r2 = l / 1000000000.0;
						long l2 = (int) Math
								.round(((5000000) / (tcIssued + wcIssued)) * 1000000000); // truncates
						r1 = l2 / 1000000000.0;
						tcClaimEligible1 = Math.round(tcNetOutstanding * 0.75
								* (r1));
						tcClaimEligible2 = Math.round(tcNetOutstanding * 0.50
								* (r2));
						tcClaimEligibleAmt = tcClaimEligible1
								+ tcClaimEligible2;
						wcClaimEligible1 = Math.round(wcNetOutstanding * 0.50
								* (r2));
						wcClaimEligible2 = Math.round(wcNetOutstanding * 0.75
								* (r1));
						wcClaimEligibleAmt = wcClaimEligible1
								+ wcClaimEligible2;
					} else if ((tcIssued + wcIssued) > 500000) {

						tcClaimEligibleAmt = Math
								.round(tcNetOutstanding * 0.75);
						wcClaimEligibleAmt = Math
								.round(wcNetOutstanding * 0.75);

					} else {
						wcClaimEligibleAmt = Math.round((Math.min(wcIssued,
								totalWCOSAmountAsOnNPA) - (wcrecovery)) * 0.75);

					}

					if (tcClaimEligibleAmt < 0) {
						tcClaimEligibleAmt = 0;
					}

					if (wcClaimEligibleAmt < 0) {
						wcClaimEligibleAmt = 0;
					}

					// //System.out.println("wcClaimEligibleAmt:"+wcClaimEligibleAmt);
					// tcFirstInstallment = claimForm.getTcFirstInstallment();
					tcFirstInstallment = Math.round(tcClaimEligibleAmt * 0.75);
					// //System.out.println("tcFirstInstallment:"+tcFirstInstallment);
					// wcFirstInstallment = claimForm.getWcFirstInstallment();
					wcFirstInstallment = Math.round(wcClaimEligibleAmt * 0.75);
					// //System.out.println("wcFirstInstallment:"+wcFirstInstallment);
					tcServiceFee = claimForm.getAsfDeductableforTC();
					// //System.out.println("tcServiceFee:"+tcServiceFee);
					wcServiceFee = claimForm.getAsfDeductableforWC();
					// //System.out.println("wcServiceFee:"+wcServiceFee);
					// payAmntNow = (String)clmdtl.getTotalAmtPayNow();
					payAmntNow = Double.toString(tcFirstInstallment
							+ wcFirstInstallment - tcServiceFee - wcServiceFee);
					clmdtl.setTotalAmtPayNow(payAmntNow);
					// //System.out.println("payAmntNow:"+payAmntNow);

					CPDAO cpdao = new CPDAO();
					cpdao.insertClaimProcessDetails(clmRefNum, userRemarks,
							tcServiceFee, wcServiceFee, tcClaimEligibleAmt,
							wcClaimEligibleAmt, tcFirstInstallment,
							wcFirstInstallment, totalTCOSAmountAsOnNPA,
							totalWCOSAmountAsOnNPA, tcrecovery, wcrecovery,
							dateofReceipt);
					if ((payAmntNow != null) && (!payAmntNow.equals(""))) {
						if ((Double.parseDouble(payAmntNow)) < 0.0) {
							payAmntNow = "0.0";
						}
					} else {
						payAmntNow = "0.0";
					}
					/*
					 * double clmEligibleAmnt = clmdtl.getEligibleClaimAmt();
					 * if((payAmntNow != null) &&
					 * (Double.parseDouble(payAmntNow)) > clmEligibleAmnt) {
					 * throw new InvalidDataException(
					 * "Amount Payable now cannot be more than the Claim Eligibility Amount."
					 * ); }
					 */
				}
			}
			/*
			 * else { flagClmRefDtl=""; payAmntNow=null;
			 * clmdtl.setTotalAmtPayNow(null); }
			 */
		}
		String clmStatus = null;
		String comments = null;
		String forwardedToUser = null;
		String thiskey = null;
		int outOfLimit = 0;
		double clmEligibleAmnt = 0.0;
		java.util.Date currentDate = new java.util.Date();
		Vector firstinstllmntclaims = processor.getClaimProcessingDetailsMod(
				ClaimConstants.FIRST_INSTALLMENT, stDt, endDt);
		/*
		 * for(int i=0; i<firstinstllmntclaims.size(); i++) { ClaimDetail clmDtl
		 * = (ClaimDetail)firstinstllmntclaims.elementAt(i); if(clmDtl == null)
		 * { continue; } String memId = (String)clmDtl.getMliId();
		 * Log.log(Log.INFO
		 * ,"ClaimAction","displayClaimApproval()","******************************"
		 * ); Log.log(Log.INFO,"ClaimAction","displayClaimApproval()","memId :"
		 * + memId); String claimrefnumber = (String)clmDtl.getClaimRefNum();
		 * Log
		 * .log(Log.INFO,"ClaimAction","displayClaimApproval()","claimrefnumber :"
		 * + claimrefnumber); String claimStatus =
		 * (String)clmDtl.getClmStatus();
		 * Log.log(Log.INFO,"ClaimAction","displayClaimApproval()","clmStatus :"
		 * + claimStatus); String commnts = (String)clmDtl.getComments(); String
		 * forwardedToUsr = (String)clmDtl.getForwaredToUser();
		 * Log.log(Log.INFO,
		 * "ClaimAction","displayClaimApproval()","forwardedToUser :" +
		 * forwardedToUsr);
		 * Log.log(Log.INFO,"ClaimAction","displayClaimApproval()"
		 * ,"******************************"); }
		 */
		// Vector forwardedFirstClms = new Vector();
		if (firstinstllmntclaims != null) {
			for (int i = 0; i < firstinstllmntclaims.size(); i++) {
				ClaimDetail clmDtl = (ClaimDetail) firstinstllmntclaims
						.elementAt(i);
				if (clmDtl == null) {
					continue;
				}
				Log.log(Log.INFO, "ClaimAction", "displayClaimApproval()",
						"##################");
				String memId = (String) clmDtl.getMliId();
				// //System.out.println("MemberId:"+memId);
				Log.log(Log.INFO, "ClaimAction", "displayClaimApproval()",
						"memId :" + memId);
				String claimrefnumber = (String) clmDtl.getClaimRefNum();
				// //System.out.println("claimrefnumber:"+claimrefnumber);
				Log.log(Log.INFO, "ClaimAction", "displayClaimApproval()",
						"claimrefnumber :" + claimrefnumber);
				clmStatus = (String) clmDtl.getClmStatus();
				// //System.out.println("clmStatus:"+clmStatus);
				Log.log(Log.INFO, "ClaimAction", "displayClaimApproval()",
						"clmStatus :" + clmStatus);
				comments = (String) clmDtl.getComments();
				// //System.out.println("comments:"+comments);
				forwardedToUser = (String) clmDtl.getForwaredToUser();
				// //System.out.println("forwardedToUser:"+forwardedToUser);
				Log.log(Log.INFO, "ClaimAction", "displayClaimApproval()",
						"forwardedToUser :" + forwardedToUser);
				Log.log(Log.INFO, "ClaimAction", "displayClaimApproval()",
						"###################");
				clmEligibleAmnt = clmDtl.getEligibleClaimAmt();
				// //System.out.println("Line number 5913 clmEligibleAmnt :" +
				// clmEligibleAmnt);
				// //System.out.println("maxClmApprvdAmnt :" + maxClmApprvdAmnt);

				if (((fromDate != null) && (!fromDate.equals("")))
						&& ((toDate != null) && (!toDate.equals("")))) {
					if (((fromDate.compareTo(currentDate)) <= 0)
							&& ((currentDate.compareTo(toDate)) <= 0)) {
						if (clmEligibleAmnt > maxClmApprvdAmnt) {
							// //System.out.println("The clmEligibleAmnt is greater than the maxClmApprvdAmnt");
							outOfLimit++;
							firstinstllmntclaims.remove(i);
							i--;
							continue;
						}
					}
				}
				if (((fromDate != null) && (!fromDate.equals("")))
						&& (toDate == null)) {
					if ((fromDate.compareTo(currentDate)) <= 0) {
						if (clmEligibleAmnt > maxClmApprvdAmnt) {
							outOfLimit++;
							firstinstllmntclaims.remove(i);
							i--;
							continue;
						}
					}
				}

				if (clmStatus != null) {
					if ((clmStatus.equals(ClaimConstants.CLM_FORWARD_STATUS))
							|| (clmStatus
									.equals(ClaimConstants.CLM_HOLD_STATUS))) {
						thiskey = ClaimConstants.FIRST_INSTALLMENT
								+ ClaimConstants.CLM_DELIMITER1 + memId
								+ ClaimConstants.CLM_DELIMITER1
								+ claimrefnumber;
						claimForm.setDecision(thiskey, clmStatus);
						claimForm.setRemarks(thiskey, comments);
						Log.log(Log.INFO, "ClaimAction",
								"displayClaimApproval()", "loggedUsr :"
										+ loggedUsr);
						if ((forwardedToUser != null)
								&& (!forwardedToUser
										.equalsIgnoreCase(loggedUsr))) {
							// forwardedFirstClms.addElement(clmDtl);
							firstinstllmntclaims.remove(i);
							--i;
						}
						if ((forwardedToUser != null)
								&& (forwardedToUser.equalsIgnoreCase(loggedUsr))) {
							// forwardedFirstClms.addElement(clmDtl);
							Log.log(Log.INFO, "ClaimAction",
									"displayClaimApproval()",
									"*******************");
							Log.log(Log.INFO, "ClaimAction",
									"displayClaimApproval()", "thiskey :"
											+ thiskey);
							Log.log(Log.INFO, "ClaimAction",
									"displayClaimApproval()",
									"forwardedToUser :" + forwardedToUser);
							Log.log(Log.INFO, "ClaimAction",
									"displayClaimApproval()",
									"*******************");
							claimForm.setForwardedToIds(thiskey,
									forwardedToUser);
							if (userIds.contains(forwardedToUser)) {
								claimForm.setUserIds(userIds);
							}
						}
					}
				}
			}
		}

		// The vector contains objects of ClaimDetail for second claim
		Vector secinstllmntclaims = processor
				.getClaimProcessingDetails(ClaimConstants.SECOND_INSTALLMENT);
		// Vector forwardedSecClms = new Vector();
		if (secinstllmntclaims != null) {
			for (int i = 0; i < secinstllmntclaims.size(); i++) {
				ClaimDetail clmDtl = (ClaimDetail) secinstllmntclaims
						.elementAt(i);
				if (clmDtl == null) {
					continue;
				}
				clmEligibleAmnt = clmDtl.getEligibleClaimAmt();
				if (((fromDate != null) && (!fromDate.equals("")))
						&& ((toDate != null) && (!toDate.equals("")))) {
					if (((fromDate.compareTo(currentDate)) <= 0)
							&& ((currentDate.compareTo(toDate)) <= 0)) {
						if (clmEligibleAmnt > maxClmApprvdAmnt) {
							outOfLimit++;
							secinstllmntclaims.remove(i);
							i--;
							continue;
						}
					}
				}
				if (((fromDate != null) && (!fromDate.equals("")))
						&& (toDate == null)) {
					if ((fromDate.compareTo(currentDate)) <= 0) {
						if (clmEligibleAmnt > maxClmApprvdAmnt) {
							outOfLimit++;
							secinstllmntclaims.remove(i);
							i--;
							continue;
						}
					}
				}
				String memId = (String) clmDtl.getMliId();
				String claimrefnumber = (String) clmDtl.getClaimRefNum();
				String cgclan = (String) clmDtl.getCGCLAN();
				clmStatus = (String) clmDtl.getClmStatus();
				comments = (String) clmDtl.getComments();
				forwardedToUser = (String) clmDtl.getForwaredToUser();
				Log.log(Log.INFO, "ClaimAction", "displayClaimApproval()",
						"forwardedToUser :" + forwardedToUser);
				if (clmStatus != null) {
					if ((clmStatus.equals(ClaimConstants.CLM_FORWARD_STATUS))
							|| (clmStatus
									.equals(ClaimConstants.CLM_HOLD_STATUS))) {
						thiskey = ClaimConstants.SECOND_INSTALLMENT
								+ ClaimConstants.CLM_DELIMITER1 + memId
								+ ClaimConstants.CLM_DELIMITER1
								+ claimrefnumber
								+ ClaimConstants.CLM_DELIMITER1 + cgclan;
						claimForm.setDecision(thiskey, clmStatus);
						claimForm.setRemarks(thiskey, comments);
						Log.log(Log.INFO, "ClaimAction",
								"displayClaimApproval()", "loggedUsr :"
										+ loggedUsr);
						if ((forwardedToUser != null)
								&& (!forwardedToUser
										.equalsIgnoreCase(loggedUsr))) {
							// forwardedSecClms.addElement(clmDtl);
							secinstllmntclaims.remove(i);
							--i;
						}
						if ((forwardedToUser != null)
								&& (forwardedToUser.equalsIgnoreCase(loggedUsr))) {
							// forwardedSecClms.addElement(clmDtl);
							claimForm.setForwardedToIds(thiskey,
									forwardedToUser);
						}
					}
				}
			}
		}

		if ((firstinstllmntclaims.size() == 0)
				&& (secinstllmntclaims.size() == 0)) {
			request.setAttribute("message",
					"There are no Claim Application(s) to process.");
			return mapping.findForward("success");
		}

		for (int i = 0; i < firstinstllmntclaims.size(); i++) {
			ClaimDetail cd = (ClaimDetail) firstinstllmntclaims.elementAt(i);
			String crn = cd.getClaimRefNum();
			if (crn.equals(clmRefNum)) {
				cd = (ClaimDetail) firstinstllmntclaims.remove(i);
				cd.setTotalAmtPayNow(payAmntNow);
				cd.setComments(standardRemarks.concat(userRemarks));
				firstinstllmntclaims.addElement(cd);
			}
		}
		for (int i = 0; i < secinstllmntclaims.size(); i++) {
			ClaimDetail cd = (ClaimDetail) secinstllmntclaims.elementAt(i);
			String crn = cd.getClaimRefNum();
			// //System.out.println("Line number6072 crn:"+crn);
			if (crn.equals(clmRefNum)) {
				cd = (ClaimDetail) secinstllmntclaims.remove(i);
				cd.setTotalAmtPayNow(payAmntNow);
				// //System.out.println("Line number 6077 payAmntNow:"+payAmntNow);
				secinstllmntclaims.addElement(cd);
			}
		}
		clmdtl = null;
		clmRefNum = null;
		payAmntNow = null;
		claimForm.setLimit(outOfLimit);
		// //System.out.println("Line number 6085 outOfLimit:"+outOfLimit);
		claimForm.setFirstInstallmentClaims(firstinstllmntclaims);
		// //System.out.println("Number of applications for first claim installments :"
		// + firstinstllmntclaims.size());
		claimForm.setFirstCounter(firstinstllmntclaims.size());
		claimForm.setSecondInstallmentClaims(secinstllmntclaims);
		// //System.out.println("Number of applications for second claim installments :"
		// + secinstllmntclaims.size());
		claimForm.setSecondCounter(secinstllmntclaims.size());
		claimForm.setClmRefDtlSet(ClaimConstants.DISBRSMNT_NO_FLAG);

		/*
		 * Clearing up the Collection Objects
		 */
		// firstinstllmntclaims.clear();
		firstinstllmntclaims = null;
		// secinstllmntclaims.clear();
		secinstllmntclaims = null;
		Log.log(Log.INFO, "ClaimAction", "displayClaimApproval()", "Exited");
		return mapping.findForward("displayClaimsApprovalPage");
	}

	/**
	 * 
	 * added by sukumar@path on 09-09-2009 for approving claim processed
	 * applications
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws java.lang.Exception
	 */
	public ActionForward displayClaimApprovalNew(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(Log.INFO, "ClaimAction", "displayClaimApprovalNew()", "Entered");
		// //System.out.println("ClaimAction displayClaimApprovalNew() Entered");

		ClaimActionForm claimForm = (ClaimActionForm) form;
		ClaimsProcessor processor = new ClaimsProcessor();
		ClaimDetail clmdtl = null;
		String clmRefNum = null;
		String payAmntNow = null;
		Administrator admin = new Administrator();
		String cgtsiBankId = Constants.CGTSI_USER_BANK_ID;
		String cgtsiZoneId = Constants.CGTSI_USER_ZONE_ID;
		String cgtsiBrnId = Constants.CGTSI_USER_BRANCH_ID;
		ArrayList userIds = admin.getUsers(cgtsiBankId + cgtsiZoneId
				+ cgtsiBrnId);
		double maxClmApprvdAmnt = 0.0;
		java.util.Date fromDate = null;
		java.util.Date toDate = null;
		User user = getUserInformation(request);
		String designation = user.getDesignation();

		String bankName = request.getParameter("Link");
		bankName = bankName.replaceAll("PATH", "&");
		// //System.out.println("bankName:"+bankName);

		// //System.out.println("user id:"+user.getUserId()+" designation:"+designation);
		String loggedUsr = user.getUserId();
		Log.log(Log.INFO, "ClaimAction", "displayClaimApprovalNew()",
				"designation :" + designation);
		HashMap clmLimitDetails = processor.getClaimLimitDtls(designation);
		maxClmApprvdAmnt = ((Double) clmLimitDetails
				.get(ClaimConstants.CLM_MAX_APPROVAL_AMOUNT)).doubleValue();
		// //System.out.println("maxClmApprvdAmnt:"+maxClmApprvdAmnt);
		Log.log(Log.INFO, "ClaimAction", "displayClaimApprovalNew()",
				"maxClmApprvdAmnt :" + maxClmApprvdAmnt);
		fromDate = (java.util.Date) clmLimitDetails
				.get(ClaimConstants.CLM_VALID_FROM_DT);
		// //System.out.println("fromDate :" + fromDate);
		toDate = (java.util.Date) clmLimitDetails
				.get(ClaimConstants.CLM_VALID_TO_DT);
		// //System.out.println("toDate :" + toDate);
		int outOfLimit = 0;
		double clmEligibleAmnt = 0.0;
		java.util.Date currentDate = new java.util.Date();
		// Vector firstinstllmntclaims =
		// processor.getClaimApprovalDetails(ClaimConstants.FIRST_INSTALLMENT);
		Vector firstinstllmntclaims = processor.getClaimApprovalDetails(
				loggedUsr, bankName);
		if ((firstinstllmntclaims.size() == 0)) {
			request.setAttribute("message",
					"There are no Claim Application(s) to Approve.");
			return mapping.findForward("success");
		}
		claimForm.setLimit(outOfLimit);
		// //System.out.println("Line number 6308 outOfLimit:"+outOfLimit);
		claimForm.setFirstInstallmentClaims(firstinstllmntclaims);
		// //System.out.println("Number of applications for first claim installments :"
		// + firstinstllmntclaims.size());
		claimForm.setFirstCounter(firstinstllmntclaims.size());
		claimForm.setClmRefDtlSet(ClaimConstants.DISBRSMNT_NO_FLAG);
		firstinstllmntclaims = null;
		// //System.out.println("Claim Action displayClaimApprovalNew exited");
		Log.log(Log.INFO, "ClaimAction", "displayClaimApprovalNew()", "Exited");
		return mapping.findForward("displayClaimApprovalNew");
	}

	public ActionForward displayClaimRefNumberDtls(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ClaimActionForm claimForm = (ClaimActionForm) form;
		Vector firstClms = (Vector) claimForm.getFirstInstallmentClaims();

		// //System.out.println("Number of applications for first claim installments :"
		// + firstinstllmntclaims.size());
		Vector secClms = (Vector) claimForm.getSecondInstallmentClaims();
		String claimRefNumber = (String) request
				.getParameter(ClaimConstants.CLM_CLAIM_REF_NUMBER);
		// //System.out.println("displayClaimRefNumberDtls Entered:"+claimRefNumber);
		double clmEligibleAmnt = 0.0;
		if (firstClms != null) {
			for (int i = 0; i < firstClms.size(); i++) {
				ClaimDetail clmDtl = (ClaimDetail) firstClms.elementAt(i);
				if (clmDtl == null) {
					continue;
				}
				String clmRefNum = (String) clmDtl.getClaimRefNum();
				if ((clmRefNum != null) && (clmRefNum.equals(claimRefNumber))) {
					clmEligibleAmnt = (double) clmDtl.getEligibleClaimAmt();
				}
			}
		}
		if (secClms != null) {
			for (int i = 0; i < secClms.size(); i++) {
				ClaimDetail clmDtl = (ClaimDetail) secClms.elementAt(i);
				if (clmDtl == null) {
					continue;
				}
				String clmRefNum = (String) clmDtl.getClaimRefNum();
				if ((clmRefNum != null) && (clmRefNum.equals(claimRefNumber))) {
					clmEligibleAmnt = (double) clmDtl.getEligibleClaimAmt();
				}
			}
		}

		ClaimsProcessor processor = new ClaimsProcessor();
		// //System.out.println("Claim Ref Number :" + claimRefNumber);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		ClaimDetail claimdetail = processor
				.getDetailsForClaimRefNumber(claimRefNumber);
		// String han=claimdetail.getHandicraft();
		// String dcHandicraft=claimdetail.getDcHandicraft();
		// //System.out.println("hhhhhhhhh"+han);
		// //System.out.println("ddddddccc"+dc);

		claimdetail.setEligibleClaimAmt(clmEligibleAmnt);
		// //System.out.println("Line number  6238-Claim Ref Number and amt from Claim Action :"
		// + claimdetail.getClaimRefNum()+clmEligibleAmnt);
		claimdetail.setClaimRefNum(claimRefNumber);
		java.util.Date dtOfRecallNotice = claimdetail
				.getDateOfIssueOfRecallNotice();
		// //System.out.println("dtOfRecallNotice :" +
		// dtOfRecallNotice.toString());
		if (dtOfRecallNotice != null) {
			claimdetail.setDateOfIssueOfRecallNoticeStr(sdf
					.format(dtOfRecallNotice));
		}
		java.util.Date npaDate = claimdetail.getNpaDate();
		// //System.out.println("npaDate :" + npaDate.toString());
		if (npaDate != null) {
			claimdetail.setNpaDateStr(sdf.format(npaDate));
		}
		java.util.Date dtOfNPAReportedToCGTSI = claimdetail
				.getDtOfNPAReportedToCGTSI();
		// //System.out.println("dtOfNPAReportedToCGTSI :" +
		// dtOfNPAReportedToCGTSI.toString());
		if (dtOfNPAReportedToCGTSI != null) {
			claimdetail.setDtOfNPAReportedToCGTSIStr(sdf
					.format(dtOfNPAReportedToCGTSI));
		}
		double amtClaimed = claimdetail.getAppliedClaimAmt();
		clmEligibleAmnt = claimdetail.getEligibleClaimAmt();
		// String memId = claimdetail.getMliId();
		// String borrowerId = claimdetail.getBorrowerId();
		// Log.log(Log.INFO,"Validator","validateDisplayClaimApproval()","*******************************");
		// Log.log(Log.INFO,"Validator","validateDisplayClaimApproval()","firstClms.size() :"
		// + firstClms.size());
		// Log.log(Log.INFO,"Validator","validateDisplayClaimApproval()","secClms.size() :"
		// + secClms.size());
		// Log.log(Log.INFO,"Validator","validateDisplayClaimApproval()","memId :"
		// + memId);
		// Log.log(Log.INFO,"Validator","validateDisplayClaimApproval()","borrowerId :"
		// + borrowerId);
		// Log.log(Log.INFO,"Validator","validateDisplayClaimApproval()","clmEligibleAmnt :"
		// + clmEligibleAmnt);
		// Log.log(Log.INFO,"Validator","validateDisplayClaimApproval()","*******************************");
		String amntClaimed = Double.toString(amtClaimed);
		claimForm.setAmountclaimed(amntClaimed);
		claimForm.setDanSummaryReportDetails(claimdetail
				.getDanSummaryReportDetails());
		claimForm.setClaimdetail(claimdetail);
		claimForm.setTcInterestChargeForThisBorrower("");
		claimForm.setWcOtherChargesAsOnNPA("");
		claimForm.setTcPrinRecoveriesAfterNPA("");
		claimForm.setTcInterestChargesRecovAfterNPA("");
		claimForm.setWcPrincipalRecoveAfterNPA("");
		claimForm.setWcothercgrgsRecAfterNPA("");

		return mapping.findForward("displayClaimRefNumDtlsPage");
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws java.lang.Exception
	 */
	public ActionForward displayClaimRefNumberDtlsMod(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ClaimActionForm claimForm = (ClaimActionForm) form;
		Vector firstClms = (Vector) claimForm.getFirstInstallmentClaims();

		// //System.out.println("Number of applications for first claim installments :"
		// + firstinstllmntclaims.size());
		Vector secClms = (Vector) claimForm.getSecondInstallmentClaims();
		String claimRefNumber = (String) request
				.getParameter(ClaimConstants.CLM_CLAIM_REF_NUMBER);
		// //System.out.println("displayClaimRefNumberDtlsMod Entered:"+claimRefNumber);
		double clmEligibleAmnt = 0.0;

		User user = getUserInformation(request);
		String loggedUserId = user.getUserId();

		claimForm.setUserId(loggedUserId);

		// //System.out.println("Test Claim Proceedings:"+(String)request.getParameter("isClaimProceedings"));
		// //System.out.println("IsClaimProceedings:"+(String)claimForm.getIsClaimProceedings());
		String isClaimProceedings = (String) claimForm.getIsClaimProceedings();
		// //System.out.println("isClaimProceedings:"+isClaimProceedings);

		if (firstClms != null) {
			for (int i = 0; i < firstClms.size(); i++) {
				ClaimDetail clmDtl = (ClaimDetail) firstClms.elementAt(i);
				if (clmDtl == null) {
					continue;
				}
				String clmRefNum = (String) clmDtl.getClaimRefNum();
				if ((clmRefNum != null) && (clmRefNum.equals(claimRefNumber))) {
					clmEligibleAmnt = (double) clmDtl.getEligibleClaimAmt();
				}
			}
		}
		if (secClms != null) {
			for (int i = 0; i < secClms.size(); i++) {
				ClaimDetail clmDtl = (ClaimDetail) secClms.elementAt(i);
				if (clmDtl == null) {
					continue;
				}
				String clmRefNum = (String) clmDtl.getClaimRefNum();
				if ((clmRefNum != null) && (clmRefNum.equals(claimRefNumber))) {
					clmEligibleAmnt = (double) clmDtl.getEligibleClaimAmt();
				}
			}
		}

		ClaimsProcessor processor = new ClaimsProcessor();
		// //System.out.println("Claim Ref Number :" + claimRefNumber);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		ClaimDetail claimdetail = processor
				.getDetailsForClaimRefNumber(claimRefNumber);
		claimdetail.setEligibleClaimAmt(clmEligibleAmnt);
		// //System.out.println("Line number  6238-Claim Ref Number and amt from Claim Action :"
		// + claimdetail.getClaimRefNum()+clmEligibleAmnt);
		claimdetail.setClaimRefNum(claimRefNumber);
		java.util.Date dtOfRecallNotice = claimdetail
				.getDateOfIssueOfRecallNotice();
		// //System.out.println("dtOfRecallNotice :" +
		// dtOfRecallNotice.toString());
		if (dtOfRecallNotice != null) {
			claimdetail.setDateOfIssueOfRecallNoticeStr(sdf
					.format(dtOfRecallNotice));
		}
		java.util.Date npaDate = claimdetail.getNpaDate();
		// //System.out.println("npaDate :" + npaDate.toString());
		if (npaDate != null) {
			claimdetail.setNpaDateStr(sdf.format(npaDate));
		}
		java.util.Date dtOfNPAReportedToCGTSI = claimdetail
				.getDtOfNPAReportedToCGTSI();
		// //System.out.println("dtOfNPAReportedToCGTSI :" +
		// dtOfNPAReportedToCGTSI.toString());
		if (dtOfNPAReportedToCGTSI != null) {
			claimdetail.setDtOfNPAReportedToCGTSIStr(sdf
					.format(dtOfNPAReportedToCGTSI));
		}
		double amtClaimed = claimdetail.getAppliedClaimAmt();
		clmEligibleAmnt = claimdetail.getEligibleClaimAmt();
		// String memId = claimdetail.getMliId();
		// String borrowerId = claimdetail.getBorrowerId();
		// Log.log(Log.INFO,"Validator","validateDisplayClaimApproval()","*******************************");
		// Log.log(Log.INFO,"Validator","validateDisplayClaimApproval()","firstClms.size() :"
		// + firstClms.size());
		// Log.log(Log.INFO,"Validator","validateDisplayClaimApproval()","secClms.size() :"
		// + secClms.size());
		// Log.log(Log.INFO,"Validator","validateDisplayClaimApproval()","memId :"
		// + memId);
		// Log.log(Log.INFO,"Validator","validateDisplayClaimApproval()","borrowerId :"
		// + borrowerId);
		// Log.log(Log.INFO,"Validator","validateDisplayClaimApproval()","clmEligibleAmnt :"
		// + clmEligibleAmnt);
		// Log.log(Log.INFO,"Validator","validateDisplayClaimApproval()","*******************************");
		String amntClaimed = Double.toString(amtClaimed);
		claimForm.setAmountclaimed(amntClaimed);
		claimForm.setDanSummaryReportDetails(claimdetail
				.getDanSummaryReportDetails());
		claimForm.setClaimdetail(claimdetail);
		claimForm.setTcInterestChargeForThisBorrower("");
		claimForm.setWcOtherChargesAsOnNPA("");
		claimForm.setTcPrinRecoveriesAfterNPA("");
		claimForm.setTcInterestChargesRecovAfterNPA("");
		claimForm.setWcPrincipalRecoveAfterNPA("");
		claimForm.setWcothercgrgsRecAfterNPA("");
		// added by sukumar@path for reinitialised Dt of Receipt and Recovery
		// details
		claimForm.setDateofReceipt(null);
		claimForm.setTcrecovery(0);
		claimForm.setWcrecovery(0);
		claimForm.setTotalRecovery(0);

		Vector arrylist = claimdetail.getCgpanDetails();

		String falgforCasesafet = "N";
		Connection connection = DBConnection.getConnection();
		Iterator interator = arrylist.iterator();
		while (interator.hasNext()) { // loop start
			try {

				Statement str = connection.createStatement(
						ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				HashMap hashmap = (HashMap) interator.next();

				String cgpanfordate = (String) hashmap
						.get(ClaimConstants.CLM_CGPAN);

				String queryNPAOutStanfAMt1 = "SELECT TRM_AMOUNT_SANCTIONED_DT A, NULL  B FROM TERM_LOAN_DETAIL WHERE CGPAN='"
						+ cgpanfordate
						+ "' UNION ALL SELECT WCP_FB_LIMIT_SANCTIONED_DT A,WCP_NFB_LIMIT_SANCTIONED_DT B FROM WORKING_CAPITAL_DETAIL WHERE CGPAN='"
						+ cgpanfordate + "'";

				// String queryNPAOutStanfAMt1
				// ="SELECT WCP_FB_LIMIT_SANCTIONED_DT A,WCP_NFB_LIMIT_SANCTIONED_DT B FROM WORKING_CAPITAL_DETAIL WHERE CGPAN='"+cgpanfordate+"'";

				// //System.out.println("queryNPAOutStanfAMt1:"+queryNPAOutStanfAMt1);
				// //System.out.println("cgpanfordate:"+cgpanfordate);
				java.util.Date date = new java.util.Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				int month = calendar.get(Calendar.MONTH);
				int day = calendar.get(Calendar.DATE);
				int year = calendar.get(Calendar.YEAR);
				day = 02;
				month = 01;
				year = 2009;
				calendar.set(Calendar.MONTH, month);
				calendar.set(Calendar.DATE, day);
				calendar.set(Calendar.YEAR, year);
				java.util.Date caesesafter2009 = calendar.getTime();
				claimForm.setFalgforCasesafet(falgforCasesafet);

				ResultSet rs1 = str.executeQuery(queryNPAOutStanfAMt1);
				while (rs1.next()) {

					java.util.Date firstDate = null;
					firstDate = rs1.getDate(1);
					java.util.Date secondDate = null;
					secondDate = rs1.getDate(2);
					// //System.out.println("The Value Of sanctioned date is 1:--->"+rs1.getDate(1));
					// //System.out.println("The Value Of sanctioned date is 2 :--->"+rs1.getDate(2));
					try {
						if (!firstDate.equals(null)) {

							if (firstDate.after(caesesafter2009)) {
								// //System.out.println("inside if ..");
								falgforCasesafet = "Y";
								claimForm.setFalgforCasesafet(falgforCasesafet);
								break;

							}
							if (!(secondDate.equals(null))) {
								if (secondDate.after(caesesafter2009)) {
									// //System.out.println("inside if ..");

									falgforCasesafet = "Y";
									claimForm
											.setFalgforCasesafet(falgforCasesafet);
									break;

								}
							}
							claimForm.setFalgforCasesafet(falgforCasesafet);
						}
					} catch (Exception we) {
						we.getMessage();
					}
					// falgforCasesafet set this as true if all comes under
					// after 2009
				}

			} catch (Exception e) {
				e.getMessage();
				// //System.out.println("the Exception is rised.....!"+e.getMessage());
			}
			 finally {
					DBConnection.freeConnection(connection);

				}
		} // loop over

		return mapping.findForward("displayClaimRefNumDtlsPage");
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws java.lang.Exception
	 */
	public ActionForward displayClaimProcessingInput(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(Log.INFO, "ClaimAction", "displayClaimProcessingInput",
				"Entered");
		// //System.out.println("displayClaimProcessingInput entered");
		HttpSession session = request.getSession();
		ClaimActionForm claimForm = (ClaimActionForm) form;
		User user = getUserInformation(request);
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String memberId = bankId.concat(zoneId).concat(branchId);
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		calendar.setTime(date);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DATE);
		month = month - 1;
		day = day + 1;
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DATE, day);
		Date prevDate = calendar.getTime();
		String startDate = dateFormat.format(prevDate);
		String endDate = dateFormat.format(date);
		// //System.out.println("startDate:"+startDate+":endDate"+endDate);
		claimForm.setDateOfTheDocument36(startDate);
		claimForm.setDateOfTheDocument37(endDate);
		Log.log(Log.INFO, "ClaimAction", "displayClaimProcessingInput",
				"Exited");
		// //System.out.println("displayClaimProcessingInput exited");
		return mapping.findForward("success");
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws java.lang.Exception
	 */
	public ActionForward displayClaimRefNumberDtlsNew(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ClaimActionForm claimForm = (ClaimActionForm) form;
		Vector firstClms = (Vector) claimForm.getFirstInstallmentClaims();

		// //System.out.println("Number of applications for first claim installments :"
		// + firstinstllmntclaims.size());
		Vector secClms = (Vector) claimForm.getSecondInstallmentClaims();
		String claimRefNumber = (String) request
				.getParameter(ClaimConstants.CLM_CLAIM_REF_NUMBER);
		// //System.out.println("displayClaimRefNumberDtlsNew Entered:"+claimRefNumber);
		double clmEligibleAmnt = 0.0;
		if (firstClms != null) {
			for (int i = 0; i < firstClms.size(); i++) {
				ClaimDetail clmDtl = (ClaimDetail) firstClms.elementAt(i);
				if (clmDtl == null) {
					continue;
				}
				String clmRefNum = (String) clmDtl.getClaimRefNum();
				if ((clmRefNum != null) && (clmRefNum.equals(claimRefNumber))) {
					clmEligibleAmnt = (double) clmDtl.getEligibleClaimAmt();
				}
			}
		}
		if (secClms != null) {
			for (int i = 0; i < secClms.size(); i++) {
				ClaimDetail clmDtl = (ClaimDetail) secClms.elementAt(i);
				if (clmDtl == null) {
					continue;
				}
				String clmRefNum = (String) clmDtl.getClaimRefNum();
				if ((clmRefNum != null) && (clmRefNum.equals(claimRefNumber))) {
					clmEligibleAmnt = (double) clmDtl.getEligibleClaimAmt();
				}
			}
		}

		ClaimsProcessor processor = new ClaimsProcessor();
		// //System.out.println("Claim Ref Number :" + claimRefNumber);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		ClaimDetail claimdetail = processor
				.getDetailsForClaimRefNumber(claimRefNumber);
		claimdetail.setEligibleClaimAmt(clmEligibleAmnt);
		// //System.out.println("Line number  6238-Claim Ref Number and amt from Claim Action :"
		// + claimdetail.getClaimRefNum()+clmEligibleAmnt);
		claimdetail.setClaimRefNum(claimRefNumber);
		java.util.Date dtOfRecallNotice = claimdetail
				.getDateOfIssueOfRecallNotice();
		// //System.out.println("dtOfRecallNotice :" +
		// dtOfRecallNotice.toString());
		if (dtOfRecallNotice != null) {
			claimdetail.setDateOfIssueOfRecallNoticeStr(sdf
					.format(dtOfRecallNotice));
		}
		java.util.Date npaDate = claimdetail.getNpaDate();
		// //System.out.println("npaDate :" + npaDate.toString());
		if (npaDate != null) {
			claimdetail.setNpaDateStr(sdf.format(npaDate));
		}
		java.util.Date dtOfNPAReportedToCGTSI = claimdetail
				.getDtOfNPAReportedToCGTSI();
		// //System.out.println("dtOfNPAReportedToCGTSI :" +
		// dtOfNPAReportedToCGTSI.toString());
		if (dtOfNPAReportedToCGTSI != null) {
			claimdetail.setDtOfNPAReportedToCGTSIStr(sdf
					.format(dtOfNPAReportedToCGTSI));
		}
		double amtClaimed = claimdetail.getAppliedClaimAmt();
		clmEligibleAmnt = claimdetail.getEligibleClaimAmt();
		String amntClaimed = Double.toString(amtClaimed);
		claimForm.setAmountclaimed(amntClaimed);
		claimForm.setDanSummaryReportDetails(claimdetail
				.getDanSummaryReportDetails());
		claimForm.setClaimdetail(claimdetail);
		claimForm.setTcInterestChargeForThisBorrower("");
		claimForm.setWcOtherChargesAsOnNPA("");
		claimForm.setTcPrinRecoveriesAfterNPA("");
		claimForm.setTcInterestChargesRecovAfterNPA("");
		claimForm.setWcPrincipalRecoveAfterNPA("");
		claimForm.setWcothercgrgsRecAfterNPA("");

		return mapping.findForward("displayClaimRefNumberDtlsNew");
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws java.lang.Exception
	 */
	public/*
		 * public ActionForward saveClaimTCProcessDetails( ActionMapping
		 * mapping, ActionForm form, HttpServletRequest request,
		 * HttpServletResponse response) throws Exception {
		 * 
		 * Log.log(Log.INFO,"ClaimAction","saveClaimTCProcessDetails","Entered");
		 * //System.out.println("saveClaimTCProcessDetails Entered"); User user =
		 * getUserInformation(request); String userid = user.getUserId();
		 * ClaimActionForm claimForm = (ClaimActionForm)form; //
		 * claimForm.resetClaimsProcessPage(mapping,request); ClaimsProcessor
		 * processor = new ClaimsProcessor(); ClaimDetail claimdetail = null;
		 * Map approvedClaimAmounts = claimForm.getApprovedClaimAmount();
		 * Log.log(Log.INFO,"ClaimAction","saveClaimProcessDetails",
		 * "Printing Approved Claim Amnts :" + approvedClaimAmounts); // Map
		 * decisionmap = claimForm.getDecision(); //
		 * //System.out.println("Printing Decision :" + decisionmap); // Map
		 * remarksmap = claimForm.getRemarks(); //
		 * //System.out.println("Printing Remarks :" + remarksmap); Set
		 * approvedClaimAmountsSet = approvedClaimAmounts.keySet(); Iterator
		 * approvedClaimAmountIterator = approvedClaimAmountsSet.iterator();
		 * StringTokenizer tokenizer = null; String memberId = null; String
		 * claimRefNumber = null; String cgtsidecision = null; String
		 * installmentFlag = null; double approvedclaimamount = 0.0; String
		 * cgtsicomments = null; Vector claimDetails = new Vector(); HashMap
		 * claimsummaries = new HashMap(); HashMap individualDtl = null; Vector
		 * aVector = new Vector(); Vector rVector = new Vector(); Vector hVector
		 * = new Vector(); Vector fVector = new Vector();
		 * 
		 * Vector tVector = new Vector(); Vector trVector = new Vector();
		 * 
		 * String hashmapKey = null; // String cgclanF = null; String cgclanS =
		 * null; String firstChar = null; int count = 0; String forwardedToUser
		 * = null; // Retrieving the keys set
		 * while(approvedClaimAmountIterator.hasNext()) { claimdetail = new
		 * ClaimDetail(); String key =
		 * (String)approvedClaimAmountIterator.next(); String token = null;
		 * if(key.length() == 0) { continue; } firstChar = key.substring(0,1);
		 * if(firstChar.equals(ClaimConstants.FIRST_INSTALLMENT)) { tokenizer =
		 * new StringTokenizer(key,"#"); boolean isMemIdRead = false; boolean
		 * isClaimRefNumRead = false; boolean isInstallmentFlagRead = false;
		 * while(tokenizer.hasMoreTokens()) { token = tokenizer.nextToken();
		 * if(!isClaimRefNumRead) { if(!isMemIdRead) {
		 * if(!isInstallmentFlagRead) { installmentFlag = token;
		 * isInstallmentFlagRead = true; continue; } memberId = token;
		 * isMemIdRead = true; continue; } claimRefNumber = token;
		 * isClaimRefNumRead = true; continue; } } } else
		 * if(firstChar.equals(ClaimConstants.SECOND_INSTALLMENT)) { tokenizer =
		 * new StringTokenizer(key,"#"); boolean isMemIdRead = false; boolean
		 * isClaimRefNumRead = false; boolean isInstallmentFlagRead = false;
		 * boolean isCGCLANRead = false; while(tokenizer.hasMoreTokens()) {
		 * token = tokenizer.nextToken(); if(!isCGCLANRead) {
		 * if(!isClaimRefNumRead) { if(!isMemIdRead) {
		 * if(!isInstallmentFlagRead) { installmentFlag = token;
		 * isInstallmentFlagRead = true; continue; } memberId = token;
		 * isMemIdRead = true; continue; } claimRefNumber = token;
		 * isClaimRefNumRead = true; continue; } cgclanS = token; isCGCLANRead =
		 * true; ////System.out.println("CGCLAN :" + cgclanS); continue; } }
		 * 
		 * } // Log.log(Log.INFO,"ClaimAction","saveClaimProcessDetails",
		 * "cgtsidecision key:" + key); cgtsidecision =
		 * (String)claimForm.getDecision(key); //
		 * //System.out.println("cgtsidecision:"+cgtsidecision); //
		 * Log.log(Log.INFO
		 * ,"ClaimAction","saveClaimProcessDetails","cgtsidecision :" +
		 * cgtsidecision); String apprvdamnt =
		 * (String)claimForm.getApprovedClaimAmount(key); //
		 * //System.out.println("apprvdamnt:"+apprvdamnt); if((apprvdamnt != null)
		 * && (!(apprvdamnt.equals("")))) { approvedclaimamount =
		 * Double.parseDouble(apprvdamnt); } cgtsicomments =
		 * (String)claimForm.getRemarks(key); //
		 * //System.out.println("cgtsicomments:"+cgtsicomments); // Setting the
		 * claim detail object claimdetail.setMliId(memberId);
		 * claimdetail.setClaimRefNum(claimRefNumber);
		 * claimdetail.setDecision(cgtsidecision);
		 * claimdetail.setApprovedClaimAmount(approvedclaimamount);
		 * claimdetail.setComments(cgtsicomments); if(cgtsidecision != null &&
		 * (cgtsidecision.equals(ClaimConstants.CLM_APPROVAL_STATUS))) {
		 * if(installmentFlag.equals(ClaimConstants.FIRST_INSTALLMENT)) { //
		 * System
		 * .out.println("Member Id:"+memberId+" claimRefNumber:"+claimRefNumber
		 * +" cgtsidecision:"
		 * +cgtsidecision+" approvedclaimamount:"+approvedclaimamount
		 * +" cgtsicomments:"+cgtsicomments); // cgclanF =
		 * processor.generateCGCLAN(); //
		 * //System.out.println("CGCLAN generated is :" + cgclanF); //
		 * claimdetail.setCGCLAN(cgclanF);
		 * claimdetail.setWhichInstallemnt(ClaimConstants.FIRST_INSTALLMENT);
		 * claimdetail.setCreatedModifiedBy(userid); individualDtl = new
		 * HashMap(); hashmapKey = ClaimConstants.CLM_APPROVAL_STATUS +
		 * ClaimConstants.FIRST_INSTALLMENT;
		 * individualDtl.put(ClaimConstants.CLM_FLAG,hashmapKey);
		 * individualDtl.put
		 * (ClaimConstants.CLM_CLAIM_REF_NUMBER,claimRefNumber); //
		 * individualDtl.put(ClaimConstants.CLM_CGCLAN,cgclanF );
		 * if(!aVector.contains(individualDtl)) {
		 * aVector.addElement(individualDtl); } } else
		 * if(installmentFlag.equals(ClaimConstants.SECOND_INSTALLMENT)) { //
		 * cgclanS = processor.generateCGCLAN();
		 * claimdetail.setWhichInstallemnt(ClaimConstants.SECOND_INSTALLMENT);
		 * claimdetail.setCGCLAN(cgclanS);
		 * claimdetail.setCreatedModifiedBy(userid); individualDtl = new
		 * HashMap(); hashmapKey = ClaimConstants.CLM_APPROVAL_STATUS +
		 * ClaimConstants.SECOND_INSTALLMENT;
		 * individualDtl.put(ClaimConstants.CLM_FLAG,hashmapKey);
		 * individualDtl.put
		 * (ClaimConstants.CLM_CLAIM_REF_NUMBER,claimRefNumber);
		 * individualDtl.put(ClaimConstants.CLM_CGCLAN,cgclanS);
		 * if(!aVector.contains(individualDtl)) {
		 * aVector.addElement(individualDtl); } }
		 * if(!claimDetails.contains(claimdetail)) {
		 * claimDetails.addElement(claimdetail); } }
		 * 
		 * if(cgtsidecision != null &&
		 * (cgtsidecision.equals(ClaimConstants.CLM_TEMPORARY_CLOSE))) {
		 * if(installmentFlag.equals(ClaimConstants.FIRST_INSTALLMENT)) {
		 * claimdetail.setWhichInstallemnt(ClaimConstants.FIRST_INSTALLMENT);
		 * claimdetail.setCreatedModifiedBy(userid); //
		 * //System.out.println("TEMP CLOSED-Member Id:"
		 * +memberId+" claimRefNumber:"
		 * +claimRefNumber+" cgtsidecision:"+cgtsidecision
		 * +" approvedclaimamount:"
		 * +approvedclaimamount+" cgtsicomments:"+cgtsicomments); individualDtl
		 * = new HashMap(); hashmapKey = ClaimConstants.CLM_TEMPORARY_CLOSE +
		 * ClaimConstants.FIRST_INSTALLMENT;
		 * individualDtl.put(ClaimConstants.CLM_FLAG,hashmapKey);
		 * individualDtl.put
		 * (ClaimConstants.CLM_CLAIM_REF_NUMBER,claimRefNumber);
		 * if(!tVector.contains(individualDtl)) {
		 * tVector.addElement(individualDtl); } } else
		 * if(installmentFlag.equals(ClaimConstants.SECOND_INSTALLMENT)) {
		 * claimdetail.setWhichInstallemnt(ClaimConstants.SECOND_INSTALLMENT);
		 * claimdetail.setCreatedModifiedBy(userid);
		 * 
		 * individualDtl = new HashMap(); hashmapKey =
		 * ClaimConstants.CLM_TEMPORARY_CLOSE +
		 * ClaimConstants.SECOND_INSTALLMENT;
		 * individualDtl.put(ClaimConstants.CLM_FLAG,hashmapKey);
		 * individualDtl.put
		 * (ClaimConstants.CLM_CLAIM_REF_NUMBER,claimRefNumber);
		 * if(!tVector.contains(individualDtl)) {
		 * tVector.addElement(individualDtl); } }
		 * if(!claimDetails.contains(claimdetail)) {
		 * claimDetails.addElement(claimdetail); } }
		 * 
		 * if(cgtsidecision != null &&
		 * (cgtsidecision.equals(ClaimConstants.CLM_TEMPORARY_REJECT))) {
		 * if(installmentFlag.equals(ClaimConstants.FIRST_INSTALLMENT)) {
		 * claimdetail.setWhichInstallemnt(ClaimConstants.FIRST_INSTALLMENT);
		 * claimdetail.setCreatedModifiedBy(userid); //
		 * //System.out.println("TEMP REJECTED-Member Id:"
		 * +memberId+" claimRefNumber:"
		 * +claimRefNumber+" cgtsidecision:"+cgtsidecision
		 * +" approvedclaimamount:"
		 * +approvedclaimamount+" cgtsicomments:"+cgtsicomments); individualDtl
		 * = new HashMap(); hashmapKey = ClaimConstants.CLM_TEMPORARY_REJECT +
		 * ClaimConstants.FIRST_INSTALLMENT;
		 * individualDtl.put(ClaimConstants.CLM_FLAG,hashmapKey);
		 * individualDtl.put
		 * (ClaimConstants.CLM_CLAIM_REF_NUMBER,claimRefNumber);
		 * if(!trVector.contains(individualDtl)) {
		 * trVector.addElement(individualDtl); } } else
		 * if(installmentFlag.equals(ClaimConstants.SECOND_INSTALLMENT)) {
		 * claimdetail.setWhichInstallemnt(ClaimConstants.SECOND_INSTALLMENT);
		 * claimdetail.setCreatedModifiedBy(userid); individualDtl = new
		 * HashMap(); hashmapKey = ClaimConstants.CLM_TEMPORARY_REJECT +
		 * ClaimConstants.SECOND_INSTALLMENT;
		 * individualDtl.put(ClaimConstants.CLM_FLAG,hashmapKey);
		 * individualDtl.put
		 * (ClaimConstants.CLM_CLAIM_REF_NUMBER,claimRefNumber);
		 * if(!trVector.contains(individualDtl)) {
		 * trVector.addElement(individualDtl); } }
		 * if(!claimDetails.contains(claimdetail)) {
		 * claimDetails.addElement(claimdetail); } }
		 * 
		 * 
		 * if(cgtsidecision != null &&
		 * (cgtsidecision.equals(ClaimConstants.CLM_REJECT_STATUS))) {
		 * if(installmentFlag.equals(ClaimConstants.FIRST_INSTALLMENT)) {
		 * claimdetail.setWhichInstallemnt(ClaimConstants.FIRST_INSTALLMENT);
		 * claimdetail.setCreatedModifiedBy(userid);
		 * 
		 * individualDtl = new HashMap(); hashmapKey =
		 * ClaimConstants.CLM_REJECT_STATUS + ClaimConstants.FIRST_INSTALLMENT;
		 * individualDtl.put(ClaimConstants.CLM_FLAG,hashmapKey);
		 * individualDtl.put
		 * (ClaimConstants.CLM_CLAIM_REF_NUMBER,claimRefNumber);
		 * if(!rVector.contains(individualDtl)) {
		 * rVector.addElement(individualDtl); } } else
		 * if(installmentFlag.equals(ClaimConstants.SECOND_INSTALLMENT)) {
		 * claimdetail.setWhichInstallemnt(ClaimConstants.SECOND_INSTALLMENT);
		 * claimdetail.setCreatedModifiedBy(userid);
		 * 
		 * individualDtl = new HashMap(); hashmapKey =
		 * ClaimConstants.CLM_REJECT_STATUS + ClaimConstants.SECOND_INSTALLMENT;
		 * individualDtl.put(ClaimConstants.CLM_FLAG,hashmapKey);
		 * individualDtl.put
		 * (ClaimConstants.CLM_CLAIM_REF_NUMBER,claimRefNumber);
		 * if(!rVector.contains(individualDtl)) {
		 * rVector.addElement(individualDtl); } }
		 * if(!claimDetails.contains(claimdetail)) {
		 * claimDetails.addElement(claimdetail); } } if(cgtsidecision != null &&
		 * (cgtsidecision.equals(ClaimConstants.CLM_HOLD_STATUS))) {
		 * if(installmentFlag.equals(ClaimConstants.FIRST_INSTALLMENT)) {
		 * claimdetail.setWhichInstallemnt(ClaimConstants.FIRST_INSTALLMENT);
		 * claimdetail.setCreatedModifiedBy(userid);
		 * 
		 * individualDtl = new HashMap(); hashmapKey =
		 * ClaimConstants.CLM_HOLD_STATUS + ClaimConstants.FIRST_INSTALLMENT;
		 * individualDtl.put(ClaimConstants.CLM_FLAG,hashmapKey);
		 * individualDtl.put
		 * (ClaimConstants.CLM_CLAIM_REF_NUMBER,claimRefNumber);
		 * if(!hVector.contains(individualDtl)) {
		 * hVector.addElement(individualDtl); } } else
		 * if(installmentFlag.equals(ClaimConstants.SECOND_INSTALLMENT)) {
		 * claimdetail.setWhichInstallemnt(ClaimConstants.SECOND_INSTALLMENT);
		 * claimdetail.setCreatedModifiedBy(userid);
		 * 
		 * individualDtl = new HashMap(); hashmapKey =
		 * ClaimConstants.CLM_HOLD_STATUS + ClaimConstants.SECOND_INSTALLMENT;
		 * individualDtl.put(ClaimConstants.CLM_FLAG,hashmapKey);
		 * individualDtl.put
		 * (ClaimConstants.CLM_CLAIM_REF_NUMBER,claimRefNumber);
		 * if(!hVector.contains(individualDtl)) {
		 * hVector.addElement(individualDtl); } }
		 * if(!claimDetails.contains(claimdetail)) {
		 * claimDetails.addElement(claimdetail); } } if(cgtsidecision != null &&
		 * (cgtsidecision.equals(ClaimConstants.CLM_FORWARD_STATUS))) {
		 * if(installmentFlag.equals(ClaimConstants.FIRST_INSTALLMENT)) {
		 * claimdetail.setWhichInstallemnt(ClaimConstants.FIRST_INSTALLMENT);
		 * forwardedToUser = (String)claimForm.getForwardedToIds(key);
		 * Log.log(Log
		 * .INFO,"ClaimAction","saveClaimProcessDetails()","forwardedToUser :" +
		 * forwardedToUser); claimdetail.setForwaredToUser(forwardedToUser);
		 * claimdetail.setCreatedModifiedBy(userid);
		 * 
		 * individualDtl = new HashMap(); hashmapKey =
		 * ClaimConstants.CLM_FORWARD_STATUS + ClaimConstants.FIRST_INSTALLMENT;
		 * individualDtl.put(ClaimConstants.CLM_FLAG,hashmapKey);
		 * individualDtl.put
		 * (ClaimConstants.CLM_CLAIM_REF_NUMBER,claimRefNumber);
		 * if(!fVector.contains(individualDtl)) {
		 * fVector.addElement(individualDtl); } } else
		 * if(installmentFlag.equals(ClaimConstants.SECOND_INSTALLMENT)) {
		 * claimdetail.setWhichInstallemnt(ClaimConstants.SECOND_INSTALLMENT);
		 * forwardedToUser = (String)claimForm.getForwardedToIds(key); //
		 * Log.log
		 * (Log.ERROR,"ClaimAction","saveClaimProcessDetails()","forwardedToUser :"
		 * + forwardedToUser); claimdetail.setForwaredToUser(forwardedToUser);
		 * claimdetail.setCreatedModifiedBy(userid);
		 * 
		 * individualDtl = new HashMap(); hashmapKey =
		 * ClaimConstants.CLM_FORWARD_STATUS +
		 * ClaimConstants.SECOND_INSTALLMENT;
		 * individualDtl.put(ClaimConstants.CLM_FLAG,hashmapKey);
		 * individualDtl.put
		 * (ClaimConstants.CLM_CLAIM_REF_NUMBER,claimRefNumber);
		 * if(!fVector.contains(individualDtl)) {
		 * fVector.addElement(individualDtl); } }
		 * if(!claimDetails.contains(claimdetail)) {
		 * claimDetails.addElement(claimdetail); } } // Clearing up the HashMap
		 * // individualDtl.clear(); individualDtl = null; count++; } //
		 * //System.out.println("Size of AVector is :" + aVector.size()); //
		 * //System.out.println("Size of RVector is :" + rVector.size()); //
		 * //System.out.println("Size of HVector is :" + hVector.size()); //
		 * //System.out.println("Size of FVector is :" + fVector.size()); //
		 * //System.out.println("Size of TCVector is :" + tVector.size());
		 * //System.out.println("Size of TRVector is :" + trVector.size());
		 * if((aVector.size() == 0) && (rVector.size() == 0) && (hVector.size()
		 * == 0) && (fVector.size() == 0)&& (tVector.size() == 0)&&
		 * (trVector.size() == 0)) {
		 * request.setAttribute("message","There are no Details to save.");
		 * return mapping.findForward("success"); }
		 * 
		 * if(claimDetails.size() > 0) { // HashMap details =
		 * processor.saveClaimProcessingResults(claimDetails); // if((details !=
		 * null) && (details.size() > 0)) // { // Set detailsset =
		 * details.keySet(); // Iterator detailsiterator =
		 * detailsset.iterator(); // while(detailsiterator.hasNext()) // { //
		 * String key = (String)detailsiterator.next(); // String cgclan =
		 * (String)details.get(key); // for(int i=0; i<aVector.size(); i++) // {
		 * // HashMap map = (HashMap)aVector.elementAt(i); // if(map != null) //
		 * { // String flag = (String)map.get(ClaimConstants.CLM_FLAG); //
		 * String clmrefnum =
		 * (String)map.get(ClaimConstants.CLM_CLAIM_REF_NUMBER); //
		 * if((flag.equals(ClaimConstants.CLM_APPROVAL_STATUS +
		 * ClaimConstants.FIRST_INSTALLMENT)) && (key.equals(clmrefnum))) // {
		 * // map = (HashMap)aVector.remove(i); //
		 * map.put(ClaimConstants.CLM_CGCLAN,cgclan); // aVector.add(i,map); //
		 * } // } // } // } // } }
		 * 
		 * 
		 * // Setting the sizes of Approved, Rejected, Held, Forwarded and
		 * Temporary Closed Claim // Applications in the Claim Action Form
		 * claimForm.setApprvdVectorSize(aVector.size());
		 * claimForm.setRejectdVectorSize(rVector.size());
		 * claimForm.setHeldVectorSize(hVector.size());
		 * claimForm.setForwardedVectorSize(fVector.size());
		 * claimForm.setTempclosedVectorSize(tVector.size());
		 * claimForm.setTemprejectedVectorSize(trVector.size());
		 * claimsummaries.put(ClaimConstants.CLM_APPROVAL_STATUS,aVector);
		 * claimsummaries.put(ClaimConstants.CLM_REJECT_STATUS,rVector);
		 * claimsummaries.put(ClaimConstants.CLM_HOLD_STATUS,hVector);
		 * claimsummaries.put(ClaimConstants.CLM_FORWARD_STATUS,fVector);
		 * claimsummaries.put(ClaimConstants.CLM_TEMPORARY_CLOSE,tVector);
		 * claimsummaries.put(ClaimConstants.CLM_TEMPORARY_REJECT,trVector);
		 * claimForm.setClaimSummaries(claimsummaries);
		 * 
		 * 
		 * // claimsummaries.clear(); claimsummaries = null; //
		 * claimDetails.clear(); claimDetails = null; // aVector.clear();
		 * aVector = null; // rVector.clear(); rVector = null; //
		 * hVector.clear(); hVector = null; // fVector.clear(); fVector = null;
		 * 
		 * tVector = null; trVector = null;
		 * claimForm.resetClaimsProcessPage(mapping,request); return
		 * mapping.findForward("tcsummaryPage"); }
		 */
	ActionForward saveTcClaimProcessDetailsNew(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception

	{

		Log.log(Log.INFO, "ClaimAction", "saveTcClaimProcessDetailsNew",
				"Entered");
		// //System.out.println("saveTcClaimProcessDetailsNew Entered");
		ClaimActionForm claimForm = (ClaimActionForm) form;
		ClaimsProcessor processor = new ClaimsProcessor();
		CPDAO cpDAO = new CPDAO();
		// //System.out.println(claimForm.getClaimSummaries().get(ClaimConstants.CLM_TEMPORARY_CLOSE));
		// //System.out.println("TC Size:"+claimForm.getTempclosedVectorSize());
		User user = getUserInformation(request);
		String userId = user.getUserId();
		java.util.Date systemDate = new java.util.Date();

		int tempCount = 0;
		boolean insertedFlag = false;

		Map claimRefSetCases = claimForm.getClaimRefSet();
		Map q1Cases = claimForm.getQ1Flags();
		Map q2Cases = claimForm.getQ2Flags();
		Map q3Cases = claimForm.getQ3Flags();
		Map q4Cases = claimForm.getQ4Flags();
		Map q5Cases = claimForm.getQ5Flags();
		Map q6Cases = claimForm.getQ6Flags();
		Map q7Cases = claimForm.getQ7Flags();
		Map q8Cases = claimForm.getQ8Flags();
		Map q9Cases = claimForm.getQ9Flags();
		Map q10Cases = claimForm.getQ10Flags();
		Map q11Cases = claimForm.getQ11Flags();
		Map q12Cases = claimForm.getQ12Flags();
		Map q13Cases = claimForm.getQ13Flags();
		Map q14Cases = claimForm.getQ14Flags();
		Map ltrRefNoSet = claimForm.getLtrRefNoSet();
		Map ltrDtSet = claimForm.getLtrDtSet();

		Set claimRefSet = claimRefSetCases.keySet();
		Iterator claimRefSetIterator = claimRefSet.iterator();
		Set q1Set = q1Cases.keySet();
		Iterator q1Iterator = q1Set.iterator();
		while (claimRefSetIterator.hasNext()) {
			// for(int i=0; i<claimForm.getTempclosedVectorSize();i++){
			String claimRefNo = (String) claimRefSetIterator.next();
			String q1 = (String) q1Cases.get(claimRefNo);
			String q2 = (String) q2Cases.get(claimRefNo);
			String q3 = (String) q3Cases.get(claimRefNo);
			String q4 = (String) q4Cases.get(claimRefNo);
			String q5 = (String) q5Cases.get(claimRefNo);
			String q6 = (String) q6Cases.get(claimRefNo);
			String q7 = (String) q7Cases.get(claimRefNo);

			String q8 = (String) q8Cases.get(claimRefNo);
			String q9 = (String) q9Cases.get(claimRefNo);
			String q10 = (String) q10Cases.get(claimRefNo);
			String q11 = (String) q11Cases.get(claimRefNo);
			String q12 = (String) q12Cases.get(claimRefNo);
			String q13 = (String) q13Cases.get(claimRefNo);
			String q14 = (String) q14Cases.get(claimRefNo);

			String ltrRefNo = (String) ltrRefNoSet.get(claimRefNo);
			String ltrDate = (String) ltrDtSet.get(claimRefNo);
			// //System.out.println("claimRefNo:"+claimRefNo+"ltrRefNo:"+ltrRefNo+"ltrDt:"+ltrDate);

			tempCount = tempCount
					+ cpDAO.insertClaimTCProcessingDetails(claimRefNo, q1, q2,
							q3, q4, q5, q6, q7, q8, q9, q10, q11, q12, q13,
							q14, userId, systemDate, ltrRefNo, ltrDate);
			insertedFlag = true;
			// //System.out.println("Claim Ref No:"+claimRefNo+"q1:"+q1+"q2:"+q2+"q3:"+q3+"q4:"+q4+"q5:"+q5+"q6:"+q6+"q7:"+q7+"q8:"+q8+"q9:"+q9+"q10:"+q10+"q11:"+q11+"q12:"+q12+"q13:"+q13+"q14:"+q14);

		}
		if (!insertedFlag) {
			throw new MessageException("No Queries Sent.");
		}

		Vector claimDetails = new Vector();
		Vector aVector = new Vector();
		Vector tVector = new Vector();

		claimDetails = claimForm.getClaimProcessingDetails();
		if (claimDetails.size() > 0) {

			HashMap details = processor
					.saveClaimProcessingResults(claimDetails);
			if ((details != null) && (details.size() > 0)) {
				Set detailsset = details.keySet();
				Iterator detailsiterator = detailsset.iterator();
				while (detailsiterator.hasNext()) {
					String key = (String) detailsiterator.next();
					String cgclan = (String) details.get(key);
					for (int i = 0; i < aVector.size(); i++) {
						HashMap map = (HashMap) aVector.elementAt(i);
						if (map != null) {
							String flag = (String) map
									.get(ClaimConstants.CLM_FLAG);
							String clmrefnum = (String) map
									.get(ClaimConstants.CLM_CLAIM_REF_NUMBER);
							if ((flag.equals(ClaimConstants.CLM_APPROVAL_STATUS
									+ ClaimConstants.FIRST_INSTALLMENT))
									&& (key.equals(clmrefnum))) {
								map = (HashMap) aVector.remove(i);
								map.put(ClaimConstants.CLM_CGCLAN, cgclan);
								aVector.add(i, map);
							}
						}
					}
				}
			}
		}

		claimDetails = null;
		aVector = null;
		tVector = null;

		claimForm.resetTCClaimsProcessPage(mapping, request);

		return mapping.findForward("summaryPage");
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws java.lang.Exception
	 */
	public ActionForward saveClaimProcessDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(Log.INFO, "ClaimAction", "saveClaimProcessDetails", "Entered");
		// //System.out.println("saveClaimProcessDetails Entered");
		User user = getUserInformation(request);
		String userid = user.getUserId();
		ClaimActionForm claimForm = (ClaimActionForm) form;
		// claimForm.resetClaimsProcessPage(mapping,request);
		ClaimsProcessor processor = new ClaimsProcessor();
		ClaimDetail claimdetail = null;
		Map approvedClaimAmounts = claimForm.getApprovedClaimAmount();
		Log.log(Log.INFO, "ClaimAction", "saveClaimProcessDetails",
				"Printing Approved Claim Amnts :" + approvedClaimAmounts);
		// Map decisionmap = claimForm.getDecision();
		// //System.out.println("Printing Decision :" + decisionmap);
		// Map remarksmap = claimForm.getRemarks();
		// //System.out.println("Printing Remarks :" + remarksmap);
		Set approvedClaimAmountsSet = approvedClaimAmounts.keySet();
		Iterator approvedClaimAmountIterator = approvedClaimAmountsSet
				.iterator();
		StringTokenizer tokenizer = null;
		String memberId = null;
		String claimRefNumber = null;
		String cgtsidecision = null;
		String installmentFlag = null;
		double approvedclaimamount = 0.0;
		String cgtsicomments = null;
		Vector claimDetails = new Vector();
		Vector claimTcProcessingDetails = new Vector();
		HashMap claimsummaries = new HashMap();
		HashMap individualDtl = null;
		Vector aVector = new Vector();
		Vector rVector = new Vector();
		Vector hVector = new Vector();
		Vector fVector = new Vector();
		Vector tVector = new Vector();
		Vector trVector = new Vector();
		Vector wdVector = new Vector();

		String hashmapKey = null;
		// String cgclanF = null;
		String cgclanS = null;
		String firstChar = null;
		int count = 0;
		String forwardedToUser = null;
		// Retrieving the keys set
		while (approvedClaimAmountIterator.hasNext()) {
			claimdetail = new ClaimDetail();
			String key = (String) approvedClaimAmountIterator.next();
			String token = null;
			if (key.length() == 0) {
				continue;
			}
			firstChar = key.substring(0, 1);
			if (firstChar.equals(ClaimConstants.FIRST_INSTALLMENT)) {
				tokenizer = new StringTokenizer(key, "#");
				boolean isMemIdRead = false;
				boolean isClaimRefNumRead = false;
				boolean isInstallmentFlagRead = false;
				while (tokenizer.hasMoreTokens()) {
					token = tokenizer.nextToken();
					if (!isClaimRefNumRead) {
						if (!isMemIdRead) {
							if (!isInstallmentFlagRead) {
								installmentFlag = token;
								isInstallmentFlagRead = true;
								continue;
							}
							memberId = token;
							isMemIdRead = true;
							continue;
						}
						claimRefNumber = token;
						isClaimRefNumRead = true;
						continue;
					}
				}
			} else if (firstChar.equals(ClaimConstants.SECOND_INSTALLMENT)) {
				tokenizer = new StringTokenizer(key, "#");
				boolean isMemIdRead = false;
				boolean isClaimRefNumRead = false;
				boolean isInstallmentFlagRead = false;
				boolean isCGCLANRead = false;
				while (tokenizer.hasMoreTokens()) {
					token = tokenizer.nextToken();
					if (!isCGCLANRead) {
						if (!isClaimRefNumRead) {
							if (!isMemIdRead) {
								if (!isInstallmentFlagRead) {
									installmentFlag = token;
									isInstallmentFlagRead = true;
									continue;
								}
								memberId = token;
								isMemIdRead = true;
								continue;
							}
							claimRefNumber = token;
							isClaimRefNumRead = true;
							continue;
						}
						cgclanS = token;
						isCGCLANRead = true;
						// //System.out.println("CGCLAN :" + cgclanS);
						continue;
					}
				}

			}
			// Log.log(Log.INFO,"ClaimAction","saveClaimProcessDetails","cgtsidecision key:"
			// + key);
			cgtsidecision = (String) claimForm.getDecision(key);
			// //System.out.println("cgtsidecision:"+cgtsidecision);
			// Log.log(Log.INFO,"ClaimAction","saveClaimProcessDetails","cgtsidecision :"
			// + cgtsidecision);
			String apprvdamnt = (String) claimForm.getApprovedClaimAmount(key);
			// //System.out.println("apprvdamnt:"+apprvdamnt);
			if ((apprvdamnt != null) && (!(apprvdamnt.equals("")))) {
				approvedclaimamount = Double.parseDouble(apprvdamnt);
			}
			cgtsicomments = (String) claimForm.getRemarks(key);
			// //System.out.println("cgtsicomments:"+cgtsicomments);
			// Setting the claim detail object
			claimdetail.setMliId(memberId);
			claimdetail.setClaimRefNum(claimRefNumber);
			claimdetail.setDecision(cgtsidecision);
			claimdetail.setApprovedClaimAmount(approvedclaimamount);
			claimdetail.setComments(cgtsicomments);
			if (cgtsidecision != null
					&& (cgtsidecision
							.equals(ClaimConstants.CLM_APPROVAL_STATUS))) {
				if (installmentFlag.equals(ClaimConstants.FIRST_INSTALLMENT)) {
					// //System.out.println("Member Id:"+memberId+" claimRefNumber:"+claimRefNumber+" cgtsidecision:"+cgtsidecision+" approvedclaimamount:"+approvedclaimamount+" cgtsicomments:"+cgtsicomments);
					// cgclanF = processor.generateCGCLAN();
					// //System.out.println("CGCLAN generated is :" + cgclanF);
					// claimdetail.setCGCLAN(cgclanF);
					claimdetail
							.setWhichInstallemnt(ClaimConstants.FIRST_INSTALLMENT);
					claimdetail.setCreatedModifiedBy(userid);
					individualDtl = new HashMap();
					hashmapKey = ClaimConstants.CLM_APPROVAL_STATUS
							+ ClaimConstants.FIRST_INSTALLMENT;
					individualDtl.put(ClaimConstants.CLM_FLAG, hashmapKey);
					individualDtl.put(ClaimConstants.CLM_CLAIM_REF_NUMBER,
							claimRefNumber);
					// individualDtl.put(ClaimConstants.CLM_CGCLAN,cgclanF );
					if (!aVector.contains(individualDtl)) {
						aVector.addElement(individualDtl);
					}
				} else if (installmentFlag
						.equals(ClaimConstants.SECOND_INSTALLMENT)) {
					// cgclanS = processor.generateCGCLAN();
					claimdetail
							.setWhichInstallemnt(ClaimConstants.SECOND_INSTALLMENT);
					claimdetail.setCGCLAN(cgclanS);
					claimdetail.setCreatedModifiedBy(userid);
					individualDtl = new HashMap();
					hashmapKey = ClaimConstants.CLM_APPROVAL_STATUS
							+ ClaimConstants.SECOND_INSTALLMENT;
					individualDtl.put(ClaimConstants.CLM_FLAG, hashmapKey);
					individualDtl.put(ClaimConstants.CLM_CLAIM_REF_NUMBER,
							claimRefNumber);
					individualDtl.put(ClaimConstants.CLM_CGCLAN, cgclanS);
					if (!aVector.contains(individualDtl)) {
						aVector.addElement(individualDtl);
					}
				}
				if (!claimDetails.contains(claimdetail)) {
					claimDetails.addElement(claimdetail);
				}
			}
			/* added by sukumar@path for Temporary Close Claim applications */
			if (cgtsidecision != null
					&& (cgtsidecision
							.equals(ClaimConstants.CLM_TEMPORARY_CLOSE))) {
				if (installmentFlag.equals(ClaimConstants.FIRST_INSTALLMENT)) {
					claimdetail
							.setWhichInstallemnt(ClaimConstants.FIRST_INSTALLMENT);
					claimdetail.setCreatedModifiedBy(userid);
					// //System.out.println("TEMP CLOSED-Member Id:"+memberId+" claimRefNumber:"+claimRefNumber+" cgtsidecision:"+cgtsidecision+" approvedclaimamount:"+approvedclaimamount+" cgtsicomments:"+cgtsicomments);
					individualDtl = new HashMap();
					hashmapKey = ClaimConstants.CLM_TEMPORARY_CLOSE
							+ ClaimConstants.FIRST_INSTALLMENT;
					individualDtl.put(ClaimConstants.CLM_FLAG, hashmapKey);
					individualDtl.put(ClaimConstants.CLM_CLAIM_REF_NUMBER,
							claimRefNumber);
					if (!tVector.contains(individualDtl)) {
						tVector.addElement(individualDtl);
					}
				} else if (installmentFlag
						.equals(ClaimConstants.SECOND_INSTALLMENT)) {
					claimdetail
							.setWhichInstallemnt(ClaimConstants.SECOND_INSTALLMENT);
					claimdetail.setCreatedModifiedBy(userid);

					individualDtl = new HashMap();
					hashmapKey = ClaimConstants.CLM_TEMPORARY_CLOSE
							+ ClaimConstants.SECOND_INSTALLMENT;
					individualDtl.put(ClaimConstants.CLM_FLAG, hashmapKey);
					individualDtl.put(ClaimConstants.CLM_CLAIM_REF_NUMBER,
							claimRefNumber);
					if (!tVector.contains(individualDtl)) {
						tVector.addElement(individualDtl);
					}
				}
				if (!claimDetails.contains(claimdetail)) {
					claimTcProcessingDetails.addElement(claimdetail);
				}
			}

			/* added by sukumar@path for Temporary Reject Claim applications */
			if (cgtsidecision != null
					&& (cgtsidecision
							.equals(ClaimConstants.CLM_TEMPORARY_REJECT))) {
				if (installmentFlag.equals(ClaimConstants.FIRST_INSTALLMENT)) {
					claimdetail
							.setWhichInstallemnt(ClaimConstants.FIRST_INSTALLMENT);
					claimdetail.setCreatedModifiedBy(userid);
					// //System.out.println("TEMP REJECTED-Member Id:"+memberId+" claimRefNumber:"+claimRefNumber+" cgtsidecision:"+cgtsidecision+" approvedclaimamount:"+approvedclaimamount+" cgtsicomments:"+cgtsicomments);
					individualDtl = new HashMap();
					hashmapKey = ClaimConstants.CLM_TEMPORARY_REJECT
							+ ClaimConstants.FIRST_INSTALLMENT;
					individualDtl.put(ClaimConstants.CLM_FLAG, hashmapKey);
					individualDtl.put(ClaimConstants.CLM_CLAIM_REF_NUMBER,
							claimRefNumber);
					if (!trVector.contains(individualDtl)) {
						trVector.addElement(individualDtl);
					}
				} else if (installmentFlag
						.equals(ClaimConstants.SECOND_INSTALLMENT)) {
					claimdetail
							.setWhichInstallemnt(ClaimConstants.SECOND_INSTALLMENT);
					claimdetail.setCreatedModifiedBy(userid);
					individualDtl = new HashMap();
					hashmapKey = ClaimConstants.CLM_TEMPORARY_REJECT
							+ ClaimConstants.SECOND_INSTALLMENT;
					individualDtl.put(ClaimConstants.CLM_FLAG, hashmapKey);
					individualDtl.put(ClaimConstants.CLM_CLAIM_REF_NUMBER,
							claimRefNumber);
					if (!trVector.contains(individualDtl)) {
						trVector.addElement(individualDtl);
					}
				}
				if (!claimDetails.contains(claimdetail)) {
					claimDetails.addElement(claimdetail);
				}
			}

			// added by sukumar@path for marked claim withdrawn cases
			if (cgtsidecision != null
					&& (cgtsidecision.equals(ClaimConstants.CLM_WITHDRAWN))) {
				if (installmentFlag.equals(ClaimConstants.FIRST_INSTALLMENT)) {
					claimdetail
							.setWhichInstallemnt(ClaimConstants.FIRST_INSTALLMENT);
					claimdetail.setCreatedModifiedBy(userid);
					// //System.out.println("CLAIM WITHDRAWN REJECTED-Member Id:"+memberId+" claimRefNumber:"+claimRefNumber+" cgtsidecision:"+cgtsidecision+" approvedclaimamount:"+approvedclaimamount+" cgtsicomments:"+cgtsicomments);
					individualDtl = new HashMap();
					hashmapKey = ClaimConstants.CLM_WITHDRAWN
							+ ClaimConstants.FIRST_INSTALLMENT;
					individualDtl.put(ClaimConstants.CLM_FLAG, hashmapKey);
					individualDtl.put(ClaimConstants.CLM_CLAIM_REF_NUMBER,
							claimRefNumber);
					if (!wdVector.contains(individualDtl)) {
						wdVector.addElement(individualDtl);
					}
				} else if (installmentFlag
						.equals(ClaimConstants.SECOND_INSTALLMENT)) {
					claimdetail
							.setWhichInstallemnt(ClaimConstants.SECOND_INSTALLMENT);
					claimdetail.setCreatedModifiedBy(userid);
					individualDtl = new HashMap();
					hashmapKey = ClaimConstants.CLM_WITHDRAWN
							+ ClaimConstants.SECOND_INSTALLMENT;
					individualDtl.put(ClaimConstants.CLM_FLAG, hashmapKey);
					individualDtl.put(ClaimConstants.CLM_CLAIM_REF_NUMBER,
							claimRefNumber);
					if (!wdVector.contains(individualDtl)) {
						wdVector.addElement(individualDtl);
					}
				}
				if (!claimDetails.contains(claimdetail)) {
					claimDetails.addElement(claimdetail);
				}
			}

			if (cgtsidecision != null
					&& (cgtsidecision.equals(ClaimConstants.CLM_REJECT_STATUS))) {
				if (installmentFlag.equals(ClaimConstants.FIRST_INSTALLMENT)) {
					claimdetail
							.setWhichInstallemnt(ClaimConstants.FIRST_INSTALLMENT);
					claimdetail.setCreatedModifiedBy(userid);

					individualDtl = new HashMap();
					hashmapKey = ClaimConstants.CLM_REJECT_STATUS
							+ ClaimConstants.FIRST_INSTALLMENT;
					individualDtl.put(ClaimConstants.CLM_FLAG, hashmapKey);
					individualDtl.put(ClaimConstants.CLM_CLAIM_REF_NUMBER,
							claimRefNumber);
					if (!rVector.contains(individualDtl)) {
						rVector.addElement(individualDtl);
					}
				} else if (installmentFlag
						.equals(ClaimConstants.SECOND_INSTALLMENT)) {
					claimdetail
							.setWhichInstallemnt(ClaimConstants.SECOND_INSTALLMENT);
					claimdetail.setCreatedModifiedBy(userid);

					individualDtl = new HashMap();
					hashmapKey = ClaimConstants.CLM_REJECT_STATUS
							+ ClaimConstants.SECOND_INSTALLMENT;
					individualDtl.put(ClaimConstants.CLM_FLAG, hashmapKey);
					individualDtl.put(ClaimConstants.CLM_CLAIM_REF_NUMBER,
							claimRefNumber);
					if (!rVector.contains(individualDtl)) {
						rVector.addElement(individualDtl);
					}
				}
				if (!claimDetails.contains(claimdetail)) {
					claimDetails.addElement(claimdetail);
				}
			}

			if (cgtsidecision != null
					&& (cgtsidecision.equals(ClaimConstants.CLM_HOLD_STATUS))) {
				if (installmentFlag.equals(ClaimConstants.FIRST_INSTALLMENT)) {
					claimdetail
							.setWhichInstallemnt(ClaimConstants.FIRST_INSTALLMENT);
					claimdetail.setCreatedModifiedBy(userid);

					individualDtl = new HashMap();
					hashmapKey = ClaimConstants.CLM_HOLD_STATUS
							+ ClaimConstants.FIRST_INSTALLMENT;
					individualDtl.put(ClaimConstants.CLM_FLAG, hashmapKey);
					individualDtl.put(ClaimConstants.CLM_CLAIM_REF_NUMBER,
							claimRefNumber);
					if (!hVector.contains(individualDtl)) {
						hVector.addElement(individualDtl);
					}
				} else if (installmentFlag
						.equals(ClaimConstants.SECOND_INSTALLMENT)) {
					claimdetail
							.setWhichInstallemnt(ClaimConstants.SECOND_INSTALLMENT);
					claimdetail.setCreatedModifiedBy(userid);

					individualDtl = new HashMap();
					hashmapKey = ClaimConstants.CLM_HOLD_STATUS
							+ ClaimConstants.SECOND_INSTALLMENT;
					individualDtl.put(ClaimConstants.CLM_FLAG, hashmapKey);
					individualDtl.put(ClaimConstants.CLM_CLAIM_REF_NUMBER,
							claimRefNumber);
					if (!hVector.contains(individualDtl)) {
						hVector.addElement(individualDtl);
					}
				}
				if (!claimDetails.contains(claimdetail)) {
					claimDetails.addElement(claimdetail);
				}
			}
			if (cgtsidecision != null
					&& (cgtsidecision.equals(ClaimConstants.CLM_FORWARD_STATUS))) {
				if (installmentFlag.equals(ClaimConstants.FIRST_INSTALLMENT)) {
					claimdetail
							.setWhichInstallemnt(ClaimConstants.FIRST_INSTALLMENT);
					forwardedToUser = (String) claimForm.getForwardedToIds(key);
					Log.log(Log.INFO, "ClaimAction",
							"saveClaimProcessDetails()", "forwardedToUser :"
									+ forwardedToUser);
					claimdetail.setForwaredToUser(forwardedToUser);
					claimdetail.setCreatedModifiedBy(userid);

					individualDtl = new HashMap();
					hashmapKey = ClaimConstants.CLM_FORWARD_STATUS
							+ ClaimConstants.FIRST_INSTALLMENT;
					individualDtl.put(ClaimConstants.CLM_FLAG, hashmapKey);
					individualDtl.put(ClaimConstants.CLM_CLAIM_REF_NUMBER,
							claimRefNumber);
					if (!fVector.contains(individualDtl)) {
						fVector.addElement(individualDtl);
					}
				} else if (installmentFlag
						.equals(ClaimConstants.SECOND_INSTALLMENT)) {
					claimdetail
							.setWhichInstallemnt(ClaimConstants.SECOND_INSTALLMENT);
					forwardedToUser = (String) claimForm.getForwardedToIds(key);
					// Log.log(Log.ERROR,"ClaimAction","saveClaimProcessDetails()","forwardedToUser :"
					// + forwardedToUser);
					claimdetail.setForwaredToUser(forwardedToUser);
					claimdetail.setCreatedModifiedBy(userid);

					individualDtl = new HashMap();
					hashmapKey = ClaimConstants.CLM_FORWARD_STATUS
							+ ClaimConstants.SECOND_INSTALLMENT;
					individualDtl.put(ClaimConstants.CLM_FLAG, hashmapKey);
					individualDtl.put(ClaimConstants.CLM_CLAIM_REF_NUMBER,
							claimRefNumber);
					if (!fVector.contains(individualDtl)) {
						fVector.addElement(individualDtl);
					}
				}
				if (!claimDetails.contains(claimdetail)) {
					claimDetails.addElement(claimdetail);
				}
			}
			// Clearing up the HashMap
			// individualDtl.clear();
			individualDtl = null;
			count++;
		}
		// //System.out.println("Size of AVector is :" + aVector.size());
		// //System.out.println("Size of RVector is :" + rVector.size());
		// //System.out.println("Size of HVector is :" + hVector.size());
		// //System.out.println("Size of FVector is :" + fVector.size());
		// //System.out.println("Size of TCVector is :" + tVector.size());
		// //System.out.println("Size of TRVector is :" + trVector.size());
		// //System.out.println("Size of WDVector is :" + wdVector.size());
		if ((aVector.size() == 0) && (rVector.size() == 0)
				&& (hVector.size() == 0) && (fVector.size() == 0)
				&& (tVector.size() == 0) && (trVector.size() == 0)
				&& (wdVector.size() == 0)) {
			request.setAttribute("message", "There are no Details to save.");
			return mapping.findForward("success");
		}

		// commented this code for testing to capture ltr ref no and ltr date
		// where claim application marked as Temporary Closed

		claimForm.setClaimProcessingDetails(claimTcProcessingDetails);
		// //System.out.println("claimDetails.size()"+claimDetails.size());
		claimForm.setAVector(aVector);
		// claimForm.setRVector(rVector);
		// claimForm.setHVector(hVector);
		// claimForm.setFVector(fVector);
		claimForm.setTVector(tVector);
		// claimForm.setTrVector(trVector);
		// claimForm.setWdVector(wdVector);

		if (claimDetails.size() > 0) {

			HashMap details = processor
					.saveClaimProcessingResults(claimDetails);
			if ((details != null) && (details.size() > 0)) {
				Set detailsset = details.keySet();
				Iterator detailsiterator = detailsset.iterator();
				while (detailsiterator.hasNext()) {
					String key = (String) detailsiterator.next();
					String cgclan = (String) details.get(key);
					for (int i = 0; i < aVector.size(); i++) {
						HashMap map = (HashMap) aVector.elementAt(i);
						if (map != null) {
							String flag = (String) map
									.get(ClaimConstants.CLM_FLAG);
							String clmrefnum = (String) map
									.get(ClaimConstants.CLM_CLAIM_REF_NUMBER);
							if ((flag.equals(ClaimConstants.CLM_APPROVAL_STATUS
									+ ClaimConstants.FIRST_INSTALLMENT))
									&& (key.equals(clmrefnum))) {
								map = (HashMap) aVector.remove(i);
								map.put(ClaimConstants.CLM_CGCLAN, cgclan);
								aVector.add(i, map);
							}
						}
					}
				}
			}
		}

		// Setting the sizes of Approved, Rejected, Held, Forwarded and
		// Temporary Closed Claim
		// Applications in the Claim Action Form
		claimForm.setApprvdVectorSize(aVector.size());
		claimForm.setRejectdVectorSize(rVector.size());
		claimForm.setHeldVectorSize(hVector.size());
		claimForm.setForwardedVectorSize(fVector.size());
		claimForm.setTempclosedVectorSize(tVector.size());
		claimForm.setTemprejectedVectorSize(trVector.size());
		claimForm.setClaimwithdrawnVectorSize(wdVector.size());

		claimsummaries.put(ClaimConstants.CLM_APPROVAL_STATUS, aVector);
		claimsummaries.put(ClaimConstants.CLM_REJECT_STATUS, rVector);
		claimsummaries.put(ClaimConstants.CLM_HOLD_STATUS, hVector);
		claimsummaries.put(ClaimConstants.CLM_FORWARD_STATUS, fVector);
		claimsummaries.put(ClaimConstants.CLM_TEMPORARY_CLOSE, tVector);
		claimsummaries.put(ClaimConstants.CLM_TEMPORARY_REJECT, trVector);
		claimsummaries.put(ClaimConstants.CLM_WITHDRAWN, wdVector);

		claimForm.setClaimSummaries(claimsummaries);
		int test = 0;
		if (tVector.size() > 0) {
			test = 1;
		}

		/*
		 * Clearing the Collection Objects
		 */
		// claimsummaries.clear();
		claimsummaries = null;
		// claimDetails.clear();
		claimDetails = null;
		// aVector.clear();
		aVector = null;
		// rVector.clear();
		rVector = null;
		// hVector.clear();
		hVector = null;
		// fVector.clear();
		fVector = null;

		tVector = null;
		trVector = null;

		wdVector = null;

		claimForm.resetClaimsProcessPage(mapping, request);
		if (test == 0) {
			return mapping.findForward("summaryPage");
		}
		return mapping.findForward("tcsummaryPage");
		// return mapping.findForward("tcsummaryPage");
	}

	public ActionForward saveClaimApproval(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(Log.INFO, "ClaimAction", "saveClaimApproval", "Entered");
		// //System.out.println("saveClaimApproval Entered");
		Log.log(Log.INFO, "ClaimAction", "saveClaimApproval", "Entered");

		User user = getUserInformation(request);
		String userid = user.getUserId();
		ClaimActionForm claimForm = (ClaimActionForm) form;
		// claimForm.resetClaimsProcessPage(mapping,request);
		ClaimsProcessor processor = new ClaimsProcessor();
		ClaimDetail claimdetail = null;
		Map approveClaims = claimForm.getApproveClaims();
		Set approveClaimsSet = approveClaims.keySet();
		Iterator approveClaimsSetIterator = approveClaimsSet.iterator();
		// //System.out.println("approveClaims:"+approveClaims);
		Map approvedClaimAmounts = claimForm.getApprovedClaimAmount();
		Log.log(Log.INFO, "ClaimAction", "saveClaimProcessDetailsNew",
				"Printing Approved Claim Amnts :" + approvedClaimAmounts);
		// //System.out.println("approvedClaimAmounts:"+approvedClaimAmounts);
		Map decisionmap = claimForm.getDecision();
		// //System.out.println("Printing Decision :" + decisionmap);
		Map remarksmap = claimForm.getRemarks();
		// //System.out.println("Printing Remarks :" + remarksmap);
		Set approvedClaimAmountsSet = approvedClaimAmounts.keySet();
		Iterator approvedClaimAmountIterator = approvedClaimAmountsSet
				.iterator();
		StringTokenizer tokenizer = null;
		String memberId = null;
		String claimRefNumber = null;
		String cgtsidecision = null;
		String installmentFlag = null;
		double approvedclaimamount = 0.0;
		String cgtsicomments = null;
		Vector claimDetails = new Vector();
		HashMap claimsummaries = new HashMap();
		HashMap individualDtl = null;
		Vector aVector = new Vector();
		Vector rVector = new Vector();
		Vector tVector = new Vector();
		Vector hVector = new Vector();
		Vector fVector = new Vector();
		Vector nVector = new Vector();
		String hashmapKey = null;
		// String cgclanF = null;
		String cgclanS = null;
		String firstChar = null;
		int count = 0;
		String forwardedToUser = null;
		// Retrieving the keys set
		while (approvedClaimAmountIterator.hasNext()) {
			claimdetail = new ClaimDetail();
			String key = (String) approvedClaimAmountIterator.next();
			String token = null;
			if (key.length() == 0) {
				continue;
			}
			firstChar = key.substring(0, 1);
			if (firstChar.equals(ClaimConstants.FIRST_INSTALLMENT)) {
				tokenizer = new StringTokenizer(key, "#");
				boolean isMemIdRead = false;
				boolean isClaimRefNumRead = false;
				boolean isInstallmentFlagRead = false;
				while (tokenizer.hasMoreTokens()) {
					token = tokenizer.nextToken();
					if (!isClaimRefNumRead) {
						if (!isMemIdRead) {
							if (!isInstallmentFlagRead) {
								installmentFlag = token;
								isInstallmentFlagRead = true;
								continue;
							}
							memberId = token;
							isMemIdRead = true;
							continue;
						}
						claimRefNumber = token;
						isClaimRefNumRead = true;
						continue;
					}
				}
			} else if (firstChar.equals(ClaimConstants.SECOND_INSTALLMENT)) {
				tokenizer = new StringTokenizer(key, "#");
				boolean isMemIdRead = false;
				boolean isClaimRefNumRead = false;
				boolean isInstallmentFlagRead = false;
				boolean isCGCLANRead = false;
				while (tokenizer.hasMoreTokens()) {
					token = tokenizer.nextToken();
					if (!isCGCLANRead) {
						if (!isClaimRefNumRead) {
							if (!isMemIdRead) {
								if (!isInstallmentFlagRead) {
									installmentFlag = token;
									isInstallmentFlagRead = true;
									continue;
								}
								memberId = token;
								isMemIdRead = true;
								continue;
							}
							claimRefNumber = token;
							isClaimRefNumRead = true;
							continue;
						}
						cgclanS = token;
						isCGCLANRead = true;
						// //System.out.println("CGCLAN :" + cgclanS);
						continue;
					}
				}

			}
			Log.log(Log.INFO, "ClaimAction", "saveClaimProcessDetailsNew",
					"cgtsidecision key:" + key);
			cgtsidecision = (String) claimForm.getDecision(key);
			// //System.out.println("cgtsidecision:"+cgtsidecision);
			Log.log(Log.INFO, "ClaimAction", "saveClaimProcessDetailsNew",
					"cgtsidecision :" + cgtsidecision);
			String apprvdamnt = (String) claimForm.getApprovedClaimAmount(key);
			// //System.out.println("apprvdamnt:"+apprvdamnt);
			if ((apprvdamnt != null) && (!(apprvdamnt.equals("")))) {
				approvedclaimamount = Double.parseDouble(apprvdamnt);
			}
			cgtsicomments = (String) claimForm.getRemarks(key);
			// //System.out.println("cgtsicomments:"+cgtsicomments);
			// Setting the claim detail object
			claimdetail.setMliId(memberId);
			claimdetail.setClaimRefNum(claimRefNumber);
			claimdetail.setDecision(cgtsidecision);
			claimdetail.setApprovedClaimAmount(approvedclaimamount);
			claimdetail.setComments(cgtsicomments);
			if (cgtsidecision != null
					&& (cgtsidecision
							.equals(ClaimConstants.CLM_APPROVAL_STATUS))) {
				if (installmentFlag.equals(ClaimConstants.FIRST_INSTALLMENT)) {
					// //System.out.println("Member Id:"+memberId+" claimRefNumber:"+claimRefNumber+" cgtsidecision:"+cgtsidecision+" approvedclaimamount:"+approvedclaimamount+" cgtsicomments:"+cgtsicomments);
					// cgclanF = processor.generateCGCLAN();
					// //System.out.println("CGCLAN generated is :" + cgclanF);
					// claimdetail.setCGCLAN(cgclanF);
					claimdetail
							.setWhichInstallemnt(ClaimConstants.FIRST_INSTALLMENT);
					claimdetail.setCreatedModifiedBy(userid);
					individualDtl = new HashMap();
					hashmapKey = ClaimConstants.CLM_APPROVAL_STATUS
							+ ClaimConstants.FIRST_INSTALLMENT;
					individualDtl.put(ClaimConstants.CLM_FLAG, hashmapKey);
					individualDtl.put(ClaimConstants.CLM_CLAIM_REF_NUMBER,
							claimRefNumber);
					// individualDtl.put(ClaimConstants.CLM_CGCLAN,cgclanF );
					if (!aVector.contains(individualDtl)) {
						aVector.addElement(individualDtl);
					}
				} else if (installmentFlag
						.equals(ClaimConstants.SECOND_INSTALLMENT)) {
					// cgclanS = processor.generateCGCLAN();
					claimdetail
							.setWhichInstallemnt(ClaimConstants.SECOND_INSTALLMENT);
					claimdetail.setCGCLAN(cgclanS);
					claimdetail.setCreatedModifiedBy(userid);
					individualDtl = new HashMap();
					hashmapKey = ClaimConstants.CLM_APPROVAL_STATUS
							+ ClaimConstants.SECOND_INSTALLMENT;
					individualDtl.put(ClaimConstants.CLM_FLAG, hashmapKey);
					individualDtl.put(ClaimConstants.CLM_CLAIM_REF_NUMBER,
							claimRefNumber);
					individualDtl.put(ClaimConstants.CLM_CGCLAN, cgclanS);
					if (!aVector.contains(individualDtl)) {
						aVector.addElement(individualDtl);
					}
				}
				if (!claimDetails.contains(claimdetail)) {
					claimDetails.addElement(claimdetail);
				}
			}
			/* added by sukumar@path for Temporary Close Claim applications */
			if (cgtsidecision != null
					&& (cgtsidecision
							.equals(ClaimConstants.CLM_TEMPORARY_CLOSE))) {
				if (installmentFlag.equals(ClaimConstants.FIRST_INSTALLMENT)) {
					claimdetail
							.setWhichInstallemnt(ClaimConstants.FIRST_INSTALLMENT);
					claimdetail.setCreatedModifiedBy(userid);
					// //System.out.println("TEMP CLOSED-Member Id:"+memberId+" claimRefNumber:"+claimRefNumber+" cgtsidecision:"+cgtsidecision+" approvedclaimamount:"+approvedclaimamount+" cgtsicomments:"+cgtsicomments);
					individualDtl = new HashMap();
					hashmapKey = ClaimConstants.CLM_TEMPORARY_CLOSE
							+ ClaimConstants.FIRST_INSTALLMENT;
					individualDtl.put(ClaimConstants.CLM_FLAG, hashmapKey);
					individualDtl.put(ClaimConstants.CLM_CLAIM_REF_NUMBER,
							claimRefNumber);
					if (!tVector.contains(individualDtl)) {
						tVector.addElement(individualDtl);
					}
				} else if (installmentFlag
						.equals(ClaimConstants.SECOND_INSTALLMENT)) {
					claimdetail
							.setWhichInstallemnt(ClaimConstants.SECOND_INSTALLMENT);
					claimdetail.setCreatedModifiedBy(userid);

					individualDtl = new HashMap();
					hashmapKey = ClaimConstants.CLM_TEMPORARY_CLOSE
							+ ClaimConstants.SECOND_INSTALLMENT;
					individualDtl.put(ClaimConstants.CLM_FLAG, hashmapKey);
					individualDtl.put(ClaimConstants.CLM_CLAIM_REF_NUMBER,
							claimRefNumber);
					if (!tVector.contains(individualDtl)) {
						tVector.addElement(individualDtl);
					}
				}
				if (!claimDetails.contains(claimdetail)) {
					claimDetails.addElement(claimdetail);
				}
			}
			/* end part here */

			if (cgtsidecision != null
					&& (cgtsidecision.equals(ClaimConstants.CLM_REJECT_STATUS))) {
				if (installmentFlag.equals(ClaimConstants.FIRST_INSTALLMENT)) {
					claimdetail
							.setWhichInstallemnt(ClaimConstants.FIRST_INSTALLMENT);
					claimdetail.setCreatedModifiedBy(userid);

					individualDtl = new HashMap();
					hashmapKey = ClaimConstants.CLM_REJECT_STATUS
							+ ClaimConstants.FIRST_INSTALLMENT;
					individualDtl.put(ClaimConstants.CLM_FLAG, hashmapKey);
					individualDtl.put(ClaimConstants.CLM_CLAIM_REF_NUMBER,
							claimRefNumber);
					if (!rVector.contains(individualDtl)) {
						rVector.addElement(individualDtl);
					}
				} else if (installmentFlag
						.equals(ClaimConstants.SECOND_INSTALLMENT)) {
					claimdetail
							.setWhichInstallemnt(ClaimConstants.SECOND_INSTALLMENT);
					claimdetail.setCreatedModifiedBy(userid);

					individualDtl = new HashMap();
					hashmapKey = ClaimConstants.CLM_REJECT_STATUS
							+ ClaimConstants.SECOND_INSTALLMENT;
					individualDtl.put(ClaimConstants.CLM_FLAG, hashmapKey);
					individualDtl.put(ClaimConstants.CLM_CLAIM_REF_NUMBER,
							claimRefNumber);
					if (!rVector.contains(individualDtl)) {
						rVector.addElement(individualDtl);
					}
				}
				if (!claimDetails.contains(claimdetail)) {
					claimDetails.addElement(claimdetail);
				}
			}
			if (cgtsidecision != null
					&& (cgtsidecision.equals(ClaimConstants.CLM_HOLD_STATUS))) {
				if (installmentFlag.equals(ClaimConstants.FIRST_INSTALLMENT)) {
					claimdetail
							.setWhichInstallemnt(ClaimConstants.FIRST_INSTALLMENT);
					claimdetail.setCreatedModifiedBy(userid);

					individualDtl = new HashMap();
					hashmapKey = ClaimConstants.CLM_HOLD_STATUS
							+ ClaimConstants.FIRST_INSTALLMENT;
					individualDtl.put(ClaimConstants.CLM_FLAG, hashmapKey);
					individualDtl.put(ClaimConstants.CLM_CLAIM_REF_NUMBER,
							claimRefNumber);
					if (!hVector.contains(individualDtl)) {
						hVector.addElement(individualDtl);
					}
				} else if (installmentFlag
						.equals(ClaimConstants.SECOND_INSTALLMENT)) {
					claimdetail
							.setWhichInstallemnt(ClaimConstants.SECOND_INSTALLMENT);
					claimdetail.setCreatedModifiedBy(userid);

					individualDtl = new HashMap();
					hashmapKey = ClaimConstants.CLM_HOLD_STATUS
							+ ClaimConstants.SECOND_INSTALLMENT;
					individualDtl.put(ClaimConstants.CLM_FLAG, hashmapKey);
					individualDtl.put(ClaimConstants.CLM_CLAIM_REF_NUMBER,
							claimRefNumber);
					if (!hVector.contains(individualDtl)) {
						hVector.addElement(individualDtl);
					}
				}
				if (!claimDetails.contains(claimdetail)) {
					claimDetails.addElement(claimdetail);
				}
			}

			if (cgtsidecision != null
					&& (cgtsidecision.equals(ClaimConstants.CLM_PENDING_STATUS))) {
				if (installmentFlag.equals(ClaimConstants.FIRST_INSTALLMENT)) {
					claimdetail
							.setWhichInstallemnt(ClaimConstants.FIRST_INSTALLMENT);
					claimdetail.setCreatedModifiedBy(userid);

					individualDtl = new HashMap();
					hashmapKey = ClaimConstants.CLM_PENDING_STATUS
							+ ClaimConstants.FIRST_INSTALLMENT;
					individualDtl.put(ClaimConstants.CLM_FLAG, hashmapKey);
					individualDtl.put(ClaimConstants.CLM_CLAIM_REF_NUMBER,
							claimRefNumber);
					if (!nVector.contains(individualDtl)) {
						nVector.addElement(individualDtl);
					}
				} else if (installmentFlag
						.equals(ClaimConstants.SECOND_INSTALLMENT)) {
					claimdetail
							.setWhichInstallemnt(ClaimConstants.SECOND_INSTALLMENT);
					claimdetail.setCreatedModifiedBy(userid);

					individualDtl = new HashMap();
					hashmapKey = ClaimConstants.CLM_PENDING_STATUS
							+ ClaimConstants.SECOND_INSTALLMENT;
					individualDtl.put(ClaimConstants.CLM_FLAG, hashmapKey);
					individualDtl.put(ClaimConstants.CLM_CLAIM_REF_NUMBER,
							claimRefNumber);
					if (!nVector.contains(individualDtl)) {
						nVector.addElement(individualDtl);
					}
				}
				if (!claimDetails.contains(claimdetail)) {
					claimDetails.addElement(claimdetail);
				}
			}

			if (cgtsidecision != null
					&& (cgtsidecision.equals(ClaimConstants.CLM_FORWARD_STATUS))) {
				if (installmentFlag.equals(ClaimConstants.FIRST_INSTALLMENT)) {
					claimdetail
							.setWhichInstallemnt(ClaimConstants.FIRST_INSTALLMENT);
					forwardedToUser = (String) claimForm.getForwardedToIds(key);
					Log.log(Log.INFO, "ClaimAction",
							"saveClaimProcessDetailsNew()", "forwardedToUser :"
									+ forwardedToUser);
					claimdetail.setForwaredToUser(forwardedToUser);
					claimdetail.setCreatedModifiedBy(userid);

					individualDtl = new HashMap();
					hashmapKey = ClaimConstants.CLM_FORWARD_STATUS
							+ ClaimConstants.FIRST_INSTALLMENT;
					individualDtl.put(ClaimConstants.CLM_FLAG, hashmapKey);
					individualDtl.put(ClaimConstants.CLM_CLAIM_REF_NUMBER,
							claimRefNumber);
					if (!fVector.contains(individualDtl)) {
						fVector.addElement(individualDtl);
					}
				} else if (installmentFlag
						.equals(ClaimConstants.SECOND_INSTALLMENT)) {
					claimdetail
							.setWhichInstallemnt(ClaimConstants.SECOND_INSTALLMENT);
					forwardedToUser = (String) claimForm.getForwardedToIds(key);
					Log.log(Log.ERROR, "ClaimAction",
							"saveClaimProcessDetailsNew()", "forwardedToUser :"
									+ forwardedToUser);
					claimdetail.setForwaredToUser(forwardedToUser);
					claimdetail.setCreatedModifiedBy(userid);

					individualDtl = new HashMap();
					hashmapKey = ClaimConstants.CLM_FORWARD_STATUS
							+ ClaimConstants.SECOND_INSTALLMENT;
					individualDtl.put(ClaimConstants.CLM_FLAG, hashmapKey);
					individualDtl.put(ClaimConstants.CLM_CLAIM_REF_NUMBER,
							claimRefNumber);
					if (!fVector.contains(individualDtl)) {
						fVector.addElement(individualDtl);
					}
				}
				if (!claimDetails.contains(claimdetail)) {
					claimDetails.addElement(claimdetail);
				}
			}
			// Clearing up the HashMap
			// individualDtl.clear();
			individualDtl = null;
			count++;
		}
		// //System.out.println("Size of AVector is :" + aVector.size());
		// //System.out.println("Size of RVector is :" + rVector.size());
		// //System.out.println("Size of TVector is :" + tVector.size());
		// //System.out.println("Size of HVector is :" + hVector.size());
		// //System.out.println("Size of FVector is :" + fVector.size());
		if ((aVector.size() == 0) && (rVector.size() == 0)
				&& (hVector.size() == 0) && (fVector.size() == 0)
				&& (tVector.size() == 0) && (nVector.size() == 0)) {
			request.setAttribute("message", "There are no Details to save.");
			return mapping.findForward("success");
		}

		if (claimDetails.size() > 0) {
			HashMap details = processor
					.saveClaimProcessingResults(claimDetails);
			if ((details != null) && (details.size() > 0)) {
				Set detailsset = details.keySet();
				Iterator detailsiterator = detailsset.iterator();
				while (detailsiterator.hasNext()) {
					String key = (String) detailsiterator.next();
					String cgclan = (String) details.get(key);
					for (int i = 0; i < aVector.size(); i++) {
						HashMap map = (HashMap) aVector.elementAt(i);
						if (map != null) {
							String flag = (String) map
									.get(ClaimConstants.CLM_FLAG);
							String clmrefnum = (String) map
									.get(ClaimConstants.CLM_CLAIM_REF_NUMBER);
							if ((flag.equals(ClaimConstants.CLM_APPROVAL_STATUS
									+ ClaimConstants.FIRST_INSTALLMENT))
									&& (key.equals(clmrefnum))) {
								map = (HashMap) aVector.remove(i);
								map.put(ClaimConstants.CLM_CGCLAN, cgclan);
								aVector.add(i, map);
							}
						}
					}
				}
			}
		}

		// Setting the sizes of Approved, Rejected, Held and Forwarded Claim
		// Applications in the Claim Action Form
		claimForm.setApprvdVectorSize(aVector.size());
		claimForm.setRejectdVectorSize(rVector.size());
		claimForm.setHeldVectorSize(hVector.size());
		claimForm.setForwardedVectorSize(fVector.size());
		claimForm.setTempclosedVectorSize(tVector.size());
		claimForm.setClaimPendingVectorSize(nVector.size());

		claimsummaries.put(ClaimConstants.CLM_APPROVAL_STATUS, aVector);
		claimsummaries.put(ClaimConstants.CLM_REJECT_STATUS, rVector);
		claimsummaries.put(ClaimConstants.CLM_HOLD_STATUS, hVector);
		claimsummaries.put(ClaimConstants.CLM_FORWARD_STATUS, fVector);
		claimsummaries.put(ClaimConstants.CLM_TEMPORARY_CLOSE, tVector);
		claimsummaries.put(ClaimConstants.CLM_PENDING_STATUS, nVector);
		claimForm.setClaimSummaries(claimsummaries);

		/*
		 * Clearing the Collection Objects
		 */
		// claimsummaries.clear();
		claimsummaries = null;
		// claimDetails.clear();
		claimDetails = null;
		// aVector.clear();
		aVector = null;
		// rVector.clear();
		rVector = null;
		// hVector.clear();
		hVector = null;
		// fVector.clear();
		fVector = null;
		tVector = null;
		nVector = null;
		claimForm.resetClaimsProcessPage(mapping, request);
		return mapping.findForward("summaryPage");
	}

	public ActionForward displaySettlementDetailFilter(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ClaimActionForm claimForm = (ClaimActionForm) form;
		claimForm.resetTheMemberId(mapping, request);
		return mapping.findForward("settlementFilter");
	}

	public ActionForward getAllPendingSettlements(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ClaimActionForm claimForm = (ClaimActionForm) form;
		ClaimsProcessor processor = new ClaimsProcessor();
		Registration registration = new Registration();
		Vector claimsFiled = processor.getAllClaimsFiled();
		Vector pendingSettlements = new Vector();
		HashMap pendingSettlmnt = null;
		String memberId = null;
		Vector dtls = null;
		if (claimsFiled != null) {
			for (int i = 0; i < claimsFiled.size(); i++) {
				HashMap map = (HashMap) claimsFiled.elementAt(i);
				if (map == null) {
					continue;
				}
				double totalPendingSettlmnt = 0.0;
				memberId = (String) map.get(ClaimConstants.CLM_MEMBER_ID);
				boolean toBeAddedIntoVector = false;
				String bankId = memberId.substring(0, 4);
				String zoneId = memberId.substring(4, 8);
				String branchId = memberId.substring(8, 12);
				MLIInfo mliInfo = registration.getMemberDetails(bankId, zoneId,
						branchId);
				String memberName = mliInfo.getBankName();
				dtls = processor.getSettlementDetails(bankId, zoneId, branchId,
						ClaimConstants.FIRST_INSTALLMENT, false);
				if (dtls != null) {
					for (int j = 0; j < dtls.size(); j++) {
						SettlementDetail settDtl = (SettlementDetail) dtls
								.elementAt(j);
						if (settDtl == null) {
							continue;
						}
						totalPendingSettlmnt = totalPendingSettlmnt
								+ settDtl.getApprovedClaimAmt();
						toBeAddedIntoVector = true;
					}
				}
				dtls = null;
				dtls = processor.getSettlementDetails(bankId, zoneId, branchId,
						ClaimConstants.SECOND_INSTALLMENT, false);
				if (dtls != null) {
					for (int j = 0; j < dtls.size(); j++) {
						SettlementDetail settDtl = (SettlementDetail) dtls
								.elementAt(j);
						if (settDtl == null) {
							continue;
						}
						totalPendingSettlmnt = totalPendingSettlmnt
								+ settDtl.getApprovedClaimAmt();
						toBeAddedIntoVector = true;
					}
				}
				if (toBeAddedIntoVector) {
					pendingSettlmnt = new HashMap();
					pendingSettlmnt.put(ClaimConstants.CLM_MEMBER_ID, memberId);
					pendingSettlmnt.put(ClaimConstants.CLM_MEMBER_NAME,
							memberName);
					pendingSettlmnt.put(ClaimConstants.CLM_TOTAL_SETTLMNT_AMNT,
							new Double(totalPendingSettlmnt));
					if (!pendingSettlements.contains(pendingSettlmnt)) {
						pendingSettlements.addElement(pendingSettlmnt);
					}
				}
			}
		}
		claimForm.setSettlementdetail(pendingSettlements);
		return mapping.findForward("settlementDetails");
	}

	public ActionForward getSettlementMemId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ClaimActionForm claimForm = (ClaimActionForm) form;
		// claimForm.setMemberId("");
		claimForm.resetSettlementProcessPage(mapping, request);
		ClaimsProcessor processor = new ClaimsProcessor();
		String memberId = (String) request
				.getParameter(ClaimConstants.CLM_MEMBER_ID);
		if (memberId == null) {
			memberId = claimForm.getMemberId();
		}
		claimForm.setMemberId(memberId);
		HttpSession session = (HttpSession) request.getSession(false);
		session.setAttribute("closureMemberId", memberId);
		// String memId = (String)request.getParameter("MemberId");
		String scr = (String) request.getParameter("Src");
		if (scr != null) {
			if (scr.equals("Reg")) {
				memberId = (String) request.getParameter("MemberId");
				claimForm.setMemberId(memberId);
			}
		}
		scr = "";

		// Validating the member Id
		Vector memberids = processor.getAllMemberIds();
		if (!(memberids.contains(memberId))) {
			throw new NoMemberFoundException("Member Id :" + memberId
					+ " does not exist in the database.");
		}
		String bankId = memberId.substring(0, 4);
		String zoneId = memberId.substring(4, 8);
		String branchId = memberId.substring(8, 12);
		Log.log(Log.INFO, "ClaimAction", "getSettlementMemId", "Member Id :"
				+ bankId + zoneId + branchId);
		Vector settlementsOfFirstClaim = processor.getSettlementDetails(bankId,
				zoneId, branchId, ClaimConstants.FIRST_INSTALLMENT, true);

		Vector settlementsOfSecondClaim = processor.getSettlementDetails(
				bankId, zoneId, branchId, ClaimConstants.SECOND_INSTALLMENT,
				true);

		if ((settlementsOfFirstClaim != null)
				&& (settlementsOfSecondClaim != null)) {
			if ((settlementsOfFirstClaim.size() == 0)
					&& (settlementsOfSecondClaim.size() == 0)) {
				request.setAttribute("message",
						"There are no Settlement Details to process.");
				return mapping.findForward("success");
			}
		}
		SettlementDetail settlementDtl = null;
		// Map settDetailsMap = new HashMap();
		String thiskey = null;
		String borrowerId = null;
		String cgclan = null;
		double clmApprvdAmnt = 0.0;
		for (int i = 0; i < settlementsOfFirstClaim.size(); i++) {
			settlementDtl = (SettlementDetail) settlementsOfFirstClaim
					.elementAt(i);
			if (settlementDtl == null) {
				continue;
			}
			borrowerId = (String) settlementDtl.getCgbid();
			cgclan = (String) settlementDtl.getCgclan();
			clmApprvdAmnt = (double) settlementDtl.getApprovedClaimAmt();
			thiskey = ClaimConstants.FIRST_INSTALLMENT
					+ ClaimConstants.CLM_DELIMITER1 + borrowerId
					+ ClaimConstants.CLM_DELIMITER1 + cgclan;
			// //System.out.println("Printing Key :" + thiskey);
			// settDetailsMap.put(thiskey, (new
			// Double(clmApprvdAmnt)).toString());
			claimForm.setSettlementAmounts(thiskey,
					(new Double(clmApprvdAmnt)).toString());
			claimForm.setFinalSettlementFlags(thiskey,
					ClaimConstants.DISBRSMNT_NO_FLAG);
			double penaltyAmnt = settlementDtl.getPenaltyAmnt();
			double pendingReceivableFromMLI = settlementDtl.getPendingFromMLI();
			Log.log(Log.ERROR, "ClaimAction", "getSettlementMemId",
					"pendingReceivableFromMLI :" + pendingReceivableFromMLI);
			String key = ClaimConstants.FIRST_INSTALLMENT
					+ ClaimConstants.CLM_DELIMITER1 + borrowerId
					+ ClaimConstants.CLM_DELIMITER1 + cgclan;
			claimForm.setPenaltyFees(key, new Double(penaltyAmnt));
			Log.log(Log.ERROR, "ClaimAction", "getSettlementMemId", "KEY :"
					+ key);
			claimForm.setPendingAmntsFromMLI(key, new Double(
					pendingReceivableFromMLI));
		}

		settlementDtl = null;
		thiskey = null;
		borrowerId = null;
		cgclan = null;

		for (int i = 0; i < settlementsOfSecondClaim.size(); i++) {
			settlementDtl = (SettlementDetail) settlementsOfSecondClaim
					.elementAt(i);
			if (settlementDtl == null) {
				continue;
			}
			borrowerId = settlementDtl.getCgbid();
			cgclan = settlementDtl.getCgclan();
			thiskey = ClaimConstants.SECOND_INSTALLMENT
					+ ClaimConstants.CLM_DELIMITER1 + borrowerId
					+ ClaimConstants.CLM_DELIMITER1 + cgclan;
			// //System.out.println("Second Settlement key:" + thiskey);
			clmApprvdAmnt = (double) settlementDtl.getApprovedClaimAmt();
			claimForm.setFinalSettlementFlags(thiskey,
					ClaimConstants.DISBRSMNT_YES_FLAG);
			claimForm.setSettlementAmounts(thiskey,
					(new Double(clmApprvdAmnt)).toString());
			double penaltyAmnt = settlementDtl.getPenaltyAmnt();
			double pendingReceivableFromMLI = settlementDtl.getPendingFromMLI();
			String key = ClaimConstants.SECOND_INSTALLMENT
					+ ClaimConstants.CLM_DELIMITER1 + borrowerId
					+ ClaimConstants.CLM_DELIMITER1 + cgclan;
			claimForm.setPenaltyFees(key, new Double(penaltyAmnt));
			claimForm.setPendingAmntsFromMLI(key, new Double(
					pendingReceivableFromMLI));
		}
		// //System.out.println("Printing the Map :" + settDetailsMap);
		// claimForm.setSettlementAmounts(settDetailsMap);

		if (settlementsOfFirstClaim != null) {
			claimForm.setFirstCounter(settlementsOfFirstClaim.size());
		}
		claimForm.setSettlementsOfFirstClaim(settlementsOfFirstClaim);
		if (settlementsOfSecondClaim != null) {
			claimForm.setSecondCounter(settlementsOfSecondClaim.size());
		}
		claimForm.setSettlementsOfSecondClaim(settlementsOfSecondClaim);

		// claimForm.getPendingAmntsFromMLI().clear();

		/*
		 * Clearing up the Collection Objects
		 */
		// memberids.clear();
		memberids = null;
		// settlementsOfFirstClaim.clear();
		settlementsOfFirstClaim = null;
		// settlementsOfSecondClaim.clear();
		settlementsOfSecondClaim = null;
		return mapping.findForward("settlementDetails");
	}

	public ActionForward saveSettlementDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(Log.INFO, "ClaimAction", "saveSettlementDetails", "Entered");
		ClaimActionForm claimForm = (ClaimActionForm) form;
		Map settlementAmounts = claimForm.getSettlementAmounts();
		// //System.out.println("Printing Settlement Amounts :" +
		// settlementAmounts);
		// Map penaltyFees = claimForm.getPenaltyFees();
		Vector settlementDetails = new Vector();
		SettlementDetail settlementDtl = null;
		// ClaimsProcessor processor = new ClaimsProcessor();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		Set settlementAmountsSet = settlementAmounts.keySet();
		Iterator settlementAmountsIterator = settlementAmountsSet.iterator();
		String installmentFlag = null;
		double totalSettlementAmnt = 0.0;
		// String penaltyFeeField = null;
		// double penaltyFee = 0.0;
		// String pendingFromMLIField = null;

		// Installment flag will be 'F' or 'S'
		String finalInstallmentFlag = null;
		// java.util.Date firstSettlementDt = null;
		// java.util.Date secondSettlementDt = null;
		// double pendingFromMLI = 0.0;
		// double payableAmnt = 0.0;

		StringTokenizer tokenizer = null;
		String cgclan = null;
		String bid = null;
		String firstSettlementAmntStr = null;
		String settlmntDtStrFormat = null;

		while (settlementAmountsIterator.hasNext()) {
			java.util.Date firstSettlementDt = null;
			java.util.Date secondSettlementDt = null;
			double firstSettlementAmnt = 0.0;
			double secondSettlementAmnt = 0.0;
			settlementDtl = new SettlementDetail();

			String key = (String) settlementAmountsIterator.next();
			// //System.out.println("Key is :" + key);
			boolean installmentFlagRead = false;
			boolean cgclanRead = false;
			boolean borrowerRead = false;

			tokenizer = new StringTokenizer(key, ClaimConstants.CLM_DELIMITER1);
			while (tokenizer.hasMoreTokens()) {
				String token = tokenizer.nextToken();
				if (!cgclanRead) {
					if (!borrowerRead) {
						if (!installmentFlagRead) {
							installmentFlag = token;
							installmentFlagRead = true;
							continue;
						}
						bid = token;
						borrowerRead = true;
						continue;
					}
					cgclan = token;
					cgclanRead = true;
					continue;
				}
			}
			if (installmentFlag.equals(ClaimConstants.FIRST_INSTALLMENT)) {
				firstSettlementAmntStr = (String) claimForm
						.getSettlementAmounts(key);

				if (firstSettlementAmntStr.equals("")) {
					firstSettlementAmntStr = "0.0";
				}

				firstSettlementAmnt = Double
						.parseDouble(firstSettlementAmntStr);

				settlmntDtStrFormat = (String) claimForm
						.getSettlementDates(key);
				// //System.out.println("firstSettlementDt String Format:" +
				// settlmntDtStrFormat);

				if (!settlmntDtStrFormat.equals("")) {
					firstSettlementDt = sdf.parse(settlmntDtStrFormat,
							new ParsePosition(0));
				}

				finalInstallmentFlag = (String) claimForm
						.getFinalSettlementFlags(key);
				/*
				 * if(finalInstallmentFlag == null) { finalInstallmentFlag =
				 * ClaimConstants.DISBRSMNT_NO_FLAG; }
				 */
				// //System.out.println("bid :" + bid);
				// //System.out.println("cgclan :" + cgclan);
				// //System.out.println("finalInstallmentFlag :" +
				// finalInstallmentFlag);
				// //System.out.println("firstSettlementAmnt :" +
				// firstSettlementAmnt);
				// //System.out.println("firstSettlementDt :" +
				// firstSettlementDt);

				settlementDtl.setCgbid(bid);
				settlementDtl.setCgclan(cgclan);
				settlementDtl.setFinalSettlementFlag(finalInstallmentFlag);
				settlementDtl
						.setWhichInstallment(ClaimConstants.FIRST_INSTALLMENT);
				settlementDtl.setTierOneSettlement(firstSettlementAmnt);
				settlementDtl.setTierOneSettlementDt(firstSettlementDt);
				if ((finalInstallmentFlag != null)
						&& (firstSettlementDt != null)) {
					if (!settlementDetails.contains(settlementDtl)) {
						settlementDetails.addElement(settlementDtl);
					}
				}
			}
			if (installmentFlag.equals(ClaimConstants.SECOND_INSTALLMENT)) {
				// settlementDtl.setCgbid(bid);
				// settlementDtl.setCgclan(cgclan);
				String secondSettlementAmntStr = (String) claimForm
						.getSettlementAmounts(key);

				if (secondSettlementAmntStr.equals("")) {
					secondSettlementAmntStr = "0.0";
				}

				secondSettlementAmnt = Double
						.parseDouble(secondSettlementAmntStr);

				String secondSettlementDtStr = (String) claimForm
						.getSettlementDates(key);
				if (!secondSettlementDtStr.equals("")) {
					secondSettlementDt = sdf.parse(secondSettlementDtStr,
							new ParsePosition(0));
				}

				finalInstallmentFlag = (String) claimForm
						.getFinalSettlementFlags(key);
				if (finalInstallmentFlag == null) {
					// finalInstallmentFlag = ClaimConstants.DISBRSMNT_YES_FLAG;
				}
				settlementDtl.setCgbid(bid);
				settlementDtl.setCgclan(cgclan);
				settlementDtl.setFinalSettlementFlag(finalInstallmentFlag);
				settlementDtl
						.setWhichInstallment(ClaimConstants.SECOND_INSTALLMENT);
				settlementDtl.setTierTwoSettlement(secondSettlementAmnt);
				settlementDtl.setTierTwoSettlementDt(secondSettlementDt);
				if ((finalInstallmentFlag != null)
						&& (secondSettlementDt != null)) {
					if (!settlementDetails.contains(settlementDtl)) {
						settlementDetails.addElement(settlementDtl);
					}
				}
			}
			totalSettlementAmnt = totalSettlementAmnt + firstSettlementAmnt;
			totalSettlementAmnt = totalSettlementAmnt + secondSettlementAmnt;
		}

		// //System.out.println("Settlement Details vector size :" +
		// settlementDetails.size());
		User user = getUserInformation(request);
		String userid = user.getUserId();
		if (settlementDetails.size() == 0) {
			request.setAttribute("message", "There are no Details to save.");
			return mapping.findForward("success");
		} else {
			// processor.saveSettlementDetails(settlementDetails,userid);
			claimForm.setSettledClms(settlementDetails);
			claimForm.setInstrumenAmount(totalSettlementAmnt);
			claimForm.setBorrowerID(bid);
			claimForm.setUserId(userid);
		}

		/*
		 * Clearing up the Collection Objects
		 */
		// settlementDetails.clear();
		settlementDetails = null;
		Registration registration = new Registration();

		// Log.log(Log.DEBUG,"RPAction","submitDANPayments","member id "+actionForm.getMemberId());

		// //System.out.println("*** Member Id" +claimForm.getMemberId());
		CollectingBank collectingBank = registration.getCollectingBank("("
				+ claimForm.getMemberId().trim() + ")");
		// CollectingBank
		// collectingBank=registration.getCollectingBank(claimForm.getMemberId());
		// //System.out.println("Collecting Bank ->" + collectingBank);

		// Log.log(Log.DEBUG,"RPAction","submitDANPayments","collectingBank "+collectingBank);

		IFProcessor ifProcessor = new IFProcessor();

		ArrayList instruments = ifProcessor
				.getInstrumentTypes(InvestmentFundConstants.GENERAL_INVESTMENT_TYPE);

		ArrayList bankDetails = ifProcessor.getBankAccounts();
		ArrayList bankNames = new ArrayList();
		for (int i = 0; i < bankDetails.size(); i++) {
			BankAccountDetail bankAccountDetail = (BankAccountDetail) bankDetails
					.get(i);
			String bankName = bankAccountDetail.getBankName();
			String branchName = bankAccountDetail.getBankBranchName();

			String bankBranchName = bankName + "," + branchName;
			bankNames.add(bankBranchName);
		}

		claimForm.setBanksList(bankNames);

		claimForm.setInstrumentTypes(instruments);
		claimForm.setCollectingBank(collectingBank);
		claimForm.setModeOfPayment("");
		claimForm.setPaymentDate(null);
		claimForm.setInstrumentNo("");
		claimForm.setInstrumentDate("");
		claimForm.setDrawnAtBank("");
		claimForm.setDrawnAtBranch("");
		claimForm.setPayableAt("");
		return mapping.findForward("paymentDetails");
	}

	public ActionForward saveSettlementAndPaymentDtls(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ClaimActionForm claimForm = (ClaimActionForm) form;
		ClaimsProcessor processor = new ClaimsProcessor();
		String collectingBranchName = null;
		String paidToString = null;
		String memberId = (claimForm.getMemberId()).trim();
		CollectingBank colBank = claimForm.getCollectingBank();
		if (colBank != null) {
			collectingBranchName = colBank.getBranchName();
		}
		// String narrationString = "";
		Vector settlementDtls = claimForm.getSettledClms();
		String whichInstllmnt = null;
		String borrowerId = null;
		String cgclan = null;
		String whetherFinalInstallment = null;

		String modeOfPayment = claimForm.getModeOfPayment();
		// //System.out.println("*** Mode of Payment :" + modeOfPayment + "***");
		java.util.Date paymentDate = claimForm.getPaymentDate();
		String instrumentNumber = claimForm.getInstrumentNo();
		String instrumentDate = claimForm.getInstrumentDate();
		// String modeOfDelivery = actionForm.getModeOfDelivery();
		// double instrumentAmount = claimForm.getInstrumenAmount() ;
		// String drawnAtBank = claimForm.getDrawnAtBank() ;
		// String drawnAtBranch = claimForm.getDrawnAtBranch() ;
		// String payableAt = claimForm.getPayableAt();
		// String bid = claimForm.getBorrowerID();
		Vector voucherPaymentDtls = new Vector();
		paidToString = ClaimConstants.CLM_PAID_TO + memberId;

		Properties accCodes = new Properties();
		String contextPath = request.getSession(false).getServletContext()
				.getRealPath("");
		Log.log(Log.DEBUG, "ClaimAction", "saveSettlementAndPaymentDtls",
				"path " + contextPath);
		File tempFile = new File(contextPath + "\\WEB-INF\\classes",
				RpConstants.AC_CODE_FILE_NAME);
		Log.log(Log.DEBUG, "ClaimAction", "saveSettlementAndPaymentDtls",
				"file opened ");
		File accCodeFile = new File(tempFile.getAbsolutePath());
		try {
			FileInputStream fin = new FileInputStream(accCodeFile);
			accCodes.load(fin);
		} catch (FileNotFoundException fe) {
			throw new MessageException("Could not load Account Codes.");
		} catch (IOException ie) {
			throw new MessageException("Could not load Account Codes.");
		}
		Log.log(Log.DEBUG, "ClaimAction", "saveSettlementAndPaymentDtls",
				"props loaded ");

		Log.log(Log.DEBUG, "ClaimAction", "saveSettlementAndPaymentDtls",
				"code " + accCodes.getProperty(RpConstants.BANK_AC));

		for (int k = 0; k < settlementDtls.size(); k++) {
			SettlementDetail sd = (SettlementDetail) settlementDtls
					.elementAt(k);
			if (sd == null) {
				continue;
			}
			whichInstllmnt = (String) sd.getWhichInstallment();
			borrowerId = (String) sd.getCgbid();
			cgclan = (String) sd.getCgclan();
			whetherFinalInstallment = (String) sd.getFinalSettlementFlag();

			ArrayList vouchers = new ArrayList();
			VoucherDetail vd = new VoucherDetail();
			vd.setBankGLName("");
			vd.setBankGLCode(accCodes.getProperty(RpConstants.BANK_AC));
			vd.setDeptCode(RpConstants.RP_CGTSI);
			vd.setNarration(ClaimConstants.CLM_CGCLAN + " :" + cgclan + ", "
					+ ClaimConstants.CLM_BORROWER_ID + " :" + borrowerId);
			vd.setPaymentDt(paymentDate);
			vd.setVoucherType(RpConstants.PAYMENT_VOUCHER);

			if ((whichInstllmnt != null) && (whetherFinalInstallment != null)) {
				if ((whichInstllmnt.equals(ClaimConstants.FIRST_INSTALLMENT))
						&& (whetherFinalInstallment
								.equals(ClaimConstants.DISBRSMNT_NO_FLAG))) {
					vd.setAmount(0 - sd.getTierOneSettlement());
					/*
					 * Voucher creditVouchr = new Voucher();
					 * creditVouchr.setPaidTo(paidToString);
					 * creditVouchr.setInstrumentNo(instrumentNumber);
					 * creditVouchr.setInstrumentDate(instrumentDate);
					 * creditVouchr.setInstrumentType(modeOfPayment);
					 * creditVouchr.setAmountInRs((new
					 * Double(sd.getTierOneSettlement())).toString());
					 * creditVouchr.setDebitOrCredit("C");
					 * creditVouchr.setAcCode("Bank A/c");
					 * vouchers.add(creditVouchr);
					 */

					Voucher debitVouchr = new Voucher();
					debitVouchr.setPaidTo(paidToString);
					debitVouchr.setInstrumentNo(instrumentNumber);
					debitVouchr.setInstrumentDate(instrumentDate);
					debitVouchr.setInstrumentType(modeOfPayment);
					debitVouchr.setAmountInRs((new Double(sd
							.getTierOneSettlement())).toString());
					debitVouchr.setDebitOrCredit("D");
					debitVouchr.setAcCode(accCodes
							.getProperty(RpConstants.CLM_FIRST_INST_AC));
					vouchers.add(debitVouchr);

					vd.setVouchers(vouchers);
					// narrationString = narrationString +
					// ClaimConstants.CLM_SETTLMNT_OF_1st_CLM +" "+ borrowerId +
					// " ";
					voucherPaymentDtls.addElement(vd);
				}
				if ((whichInstllmnt.equals(ClaimConstants.FIRST_INSTALLMENT))
						&& (whetherFinalInstallment
								.equals(ClaimConstants.DISBRSMNT_YES_FLAG))) {
					vd.setAmount(0 - sd.getTierOneSettlement());
					/*
					 * Voucher creditVouchr = new Voucher();
					 * creditVouchr.setPaidTo(paidToString);
					 * creditVouchr.setInstrumentNo(instrumentNumber);
					 * creditVouchr.setInstrumentDate(instrumentDate);
					 * creditVouchr.setInstrumentType(modeOfPayment);
					 * creditVouchr.setAmountInRs((new
					 * Double(sd.getTierOneSettlement())).toString());
					 * creditVouchr.setDebitOrCredit("C");
					 * creditVouchr.setAcCode("Bank A/c"); //
					 * creditVouchr.setAcCode("CLAIM 1 SETTLEMENT");
					 * vouchers.add(creditVouchr);
					 */

					Voucher debitVouchr1 = new Voucher();
					debitVouchr1.setPaidTo(paidToString);
					debitVouchr1.setInstrumentNo(instrumentNumber);
					debitVouchr1.setInstrumentDate(instrumentDate);
					debitVouchr1.setInstrumentType(modeOfPayment);
					debitVouchr1.setAmountInRs((new Double(sd
							.getTierOneSettlement())).toString());
					debitVouchr1.setDebitOrCredit("D");
					debitVouchr1.setAcCode(accCodes
							.getProperty(RpConstants.CLM_FIRST_INST_AC));
					vouchers.add(debitVouchr1);

					Voucher debitVouchr2 = new Voucher();
					debitVouchr2.setPaidTo(paidToString);
					debitVouchr2.setInstrumentNo(instrumentNumber);
					debitVouchr2.setInstrumentDate(instrumentDate);
					debitVouchr2.setInstrumentType(modeOfPayment);
					String noAmnt = "0.0";
					debitVouchr2.setAmountInRs(noAmnt);
					debitVouchr2.setDebitOrCredit("D");
					debitVouchr2.setAcCode(accCodes
							.getProperty(RpConstants.CLM_SECOND_INST_AC));
					vouchers.add(debitVouchr2);

					vd.setVouchers(vouchers);
					// narrationString = narrationString +
					// ClaimConstants.CLM_SETTLMNT_OF_1st_CLM +" "+ borrowerId +
					// " ";
					voucherPaymentDtls.addElement(vd);
				}
				if (whichInstllmnt.equals(ClaimConstants.SECOND_INSTALLMENT)) {
					vd.setAmount(0 - sd.getTierTwoSettlement());
					/*
					 * Voucher creditVouchr = new Voucher();
					 * creditVouchr.setPaidTo(paidToString);
					 * creditVouchr.setInstrumentNo(instrumentNumber);
					 * creditVouchr.setInstrumentDate(instrumentDate);
					 * creditVouchr.setInstrumentType(modeOfPayment);
					 * creditVouchr.setAmountInRs((new
					 * Double(sd.getTierTwoSettlement())).toString());
					 * creditVouchr.setDebitOrCredit("C");
					 * creditVouchr.setAcCode("Bank A/c"); //
					 * creditVouchr.setAcCode("CLAIM 1 SETTLEMENT");
					 * vouchers.add(creditVouchr);
					 */

					Voucher debitVouchr = new Voucher();
					debitVouchr.setPaidTo(paidToString);
					debitVouchr.setInstrumentNo(instrumentNumber);
					debitVouchr.setInstrumentDate(instrumentDate);
					debitVouchr.setInstrumentType(modeOfPayment);
					debitVouchr.setAmountInRs((new Double(sd
							.getTierTwoSettlement())).toString());
					debitVouchr.setDebitOrCredit("D");
					debitVouchr.setAcCode(accCodes
							.getProperty(RpConstants.CLM_SECOND_INST_AC));
					vouchers.add(debitVouchr);

					vd.setVouchers(vouchers);
					// narrationString = narrationString +
					// ClaimConstants.CLM_SETTLMNT_OF_1st_CLM +" "+ borrowerId +
					// " ";
					voucherPaymentDtls.addElement(vd);
				}
			}
			sd = null;
		}
		User user = getUserInformation(request);
		String userid = user.getUserId();

		IFProcessor ifProcessor = new IFProcessor();

		ChequeDetails chequeDetails = new ChequeDetails();

		if (claimForm.getModeOfPayment().equals("CHEQUE")) {
			/**
			 * insert details into cheque_issued detail
			 */
			if (claimForm.getBnkName() == null
					|| claimForm.getBnkName().equals("")) {
				throw new MessageException(
						"Since no bank names are available,Cheque Details cannot be inserted");
			}
			String bankBranchName = claimForm.getBnkName();
			int start = bankBranchName.indexOf(",");
			String bankName = bankBranchName.substring(0, start);

			String branchName = bankBranchName.substring(start + 1);

			chequeDetails.setUserId(userid);
			chequeDetails.setChequeAmount(claimForm.getInstrumenAmount());
			chequeDetails.setChequeDate(claimForm.getPaymentDate());
			chequeDetails.setChequeNumber(claimForm.getInstrumentNo());
			chequeDetails.setChequeIssuedTo(RpConstants.RP_CGTSI);
			chequeDetails.setBankName(bankName);
			chequeDetails.setBranchName(branchName);
			chequeDetails.setChequeRemarks(null);
			chequeDetails.setPayId(null);

			// ifProcessor.chequeDetailsInsertSuccess(chequeDetails,contextPath,userid);
		} else {
			chequeDetails = null;
		}

		processor.saveSettlementDetails(settlementDtls, voucherPaymentDtls,
				userid, chequeDetails, contextPath);
		request.setAttribute("message",
				"The Settlement Detail(s) and Payment Detail(s) are saved successfully.");
		return mapping.findForward("success");
	}

	public ActionForward generateSettAdviceLinkFromSuccessPage(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ClaimActionForm claimForm = (ClaimActionForm) form;
		ClaimsProcessor processor = new ClaimsProcessor();
		String memberId = claimForm.getMemberId();
		// //System.out.println("Member id :" + memberId);
		// Vector settledClms = claimForm.getSettledClms();
		Vector settlementAdviceDtlsFirstSttlmnt = processor
				.getSettlementAdviceDetail(memberId,
						ClaimConstants.FIRST_INSTALLMENT);

		Vector settlementAdviceDtlsSecSttlmnt = processor
				.getSettlementAdviceDetail(memberId,
						ClaimConstants.SECOND_INSTALLMENT);

		claimForm
				.setSettlmntAdviceDtlsFirstSttlmnt(settlementAdviceDtlsFirstSttlmnt);
		claimForm
				.setSettlmntAdviceDtlsSecondSttlmnt(settlementAdviceDtlsSecSttlmnt);
		// //System.out.println("Number of first settlements for advice :" +
		// settlementAdviceDtlsFirstSttlmnt.size());
		// //System.out.println("Number of second settlements for advice :" +
		// settlementAdviceDtlsSecSttlmnt.size());

		if (settlementAdviceDtlsFirstSttlmnt != null) {
			claimForm.setFirstCounter(settlementAdviceDtlsFirstSttlmnt.size());
		}
		if (settlementAdviceDtlsSecSttlmnt != null) {
			claimForm.setSecondCounter(settlementAdviceDtlsSecSttlmnt.size());
		}
		// claimForm.resetTheMemberIdAndFlag(mapping,request);
		return mapping.findForward("displayCSAGenerateOptionPage");
	}

	public ActionForward displaySettlementAdviceFilter(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ClaimActionForm claimForm = (ClaimActionForm) form;
		claimForm.resetTheMemberIdAndFlag(mapping, request);
		return mapping.findForward("displaysettlementadvicefilter");
	}

	public ActionForward getSettlementAdviceFilterDtl(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ClaimActionForm claimForm = (ClaimActionForm) form;

		String memberIdFlag = claimForm.getMemberIdFlag();

		String memberId = null;

		ClaimsProcessor processor = new ClaimsProcessor();

		Vector allMemberIds = new Vector();

		Vector settlementAdviceDtlsFirstSttlmnt = null;

		Vector settlementAdviceDtlsSecSttlmnt = null;

		if (memberIdFlag.equals(ClaimConstants.CLM_STLMNT_MEMBER_ALL)) {
			settlementAdviceDtlsFirstSttlmnt = new Vector();
			settlementAdviceDtlsSecSttlmnt = new Vector();
			Vector allclaimsfiled = processor.getAllClaimsFiled();
			for (int i = 0; i < allclaimsfiled.size(); i++) {
				HashMap thisclaim = (HashMap) allclaimsfiled.elementAt(i);
				if (thisclaim != null) {
					String thismemberId = (String) thisclaim
							.get(ClaimConstants.CLM_MEMBER_ID);
					if (!allMemberIds.contains(thismemberId)) {
						allMemberIds.addElement(thismemberId);
					}

					// Clearing up the HashMap
					// thisclaim.clear();
					thisclaim = null;
				}
			}
			for (int i = 0; i < allMemberIds.size(); i++) {
				memberId = (String) allMemberIds.elementAt(i);
				if (memberId == null) {
					continue;
				}
				// //System.out.println("Member Id :" + memberId);
				/*
				 * Vector of HashMaps. Each HashMap contains Settlement Advice
				 * Details.
				 */
				Vector first = processor.getSettlementAdviceDetail(memberId,
						ClaimConstants.FIRST_INSTALLMENT);

				Vector second = processor.getSettlementAdviceDetail(memberId,
						ClaimConstants.SECOND_INSTALLMENT);

				/*
				 * Storing each of the HashMap in the Vector to one single
				 * Vector.
				 */

				for (int j = 0; j < first.size(); j++) {
					HashMap thismap = (HashMap) first.elementAt(j);
					if (thismap != null) {
						if (!settlementAdviceDtlsFirstSttlmnt.contains(thismap)) {
							settlementAdviceDtlsFirstSttlmnt
									.addElement(thismap);
						}
					}
				}

				for (int j = 0; j < second.size(); j++) {
					HashMap anothermap = (HashMap) second.elementAt(j);
					if (anothermap != null) {
						if (!settlementAdviceDtlsSecSttlmnt
								.contains(anothermap)) {
							settlementAdviceDtlsSecSttlmnt
									.addElement(anothermap);
						}
					}
				}

				// settlementAdviceDtlsFirstSttlmnt.addElement(settlmntAdviceDtlsFirstSttlmnt);
				// settlementAdviceDtlsSecSttlmnt.addElement(settlmntAdviceDtlsSecondSttlmnt);

				// Clearing up the HashMap
				// settlmntAdviceDtlsFirstSttlmnt.clear();
				// settlmntAdviceDtlsFirstSttlmnt = null;
			}

			// Clearing up the Vector object
			// allclaimsfiled.clear();
			allclaimsfiled = null;
		} else if (memberIdFlag
				.equals(ClaimConstants.CLM_STLMNT_MEMBER_SPECIFIC)) {
			memberId = claimForm.getMemberId();
			// settlementAdviceDtlsFirstSttlmnt = new Vector();
			// settlementAdviceDtlsSecSttlmnt = new Vector();

			settlementAdviceDtlsFirstSttlmnt = processor
					.getSettlementAdviceDetail(memberId,
							ClaimConstants.FIRST_INSTALLMENT);

			settlementAdviceDtlsSecSttlmnt = processor
					.getSettlementAdviceDetail(memberId,
							ClaimConstants.SECOND_INSTALLMENT);

			// settlementAdviceDtlsFirstSttlmnt.addElement(settlmntAdvceDtlsFirstSttlmnt);
			// settlementAdviceDtlsSecSttlmnt.addElement(settlmntAdvceDtlsSecondSttlmnt);

			// Clearing up the HashMap
			// settlmntAdvceDtlsFirstSttlmnt.clear();
			// settlmntAdvceDtlsFirstSttlmnt = null;
		}

		claimForm
				.setSettlmntAdviceDtlsFirstSttlmnt(settlementAdviceDtlsFirstSttlmnt);
		claimForm
				.setSettlmntAdviceDtlsSecondSttlmnt(settlementAdviceDtlsSecSttlmnt);

		// //System.out.println("Number of first settlements for advice :" +
		// settlementAdviceDtlsFirstSttlmnt.size());
		// //System.out.println("Number of second settlements for advice :" +
		// settlementAdviceDtlsSecSttlmnt.size());

		if (settlementAdviceDtlsFirstSttlmnt != null) {
			claimForm.setFirstCounter(settlementAdviceDtlsFirstSttlmnt.size());
		}
		if (settlementAdviceDtlsSecSttlmnt != null) {
			claimForm.setSecondCounter(settlementAdviceDtlsSecSttlmnt.size());
		}

		/*
		 * Clearing up the Collection Objects
		 */
		// allMemberIds.clear();
		allMemberIds = null;
		// settlementAdviceDtlsFirstSttlmnt.clear();
		settlementAdviceDtlsFirstSttlmnt = null;
		// settlementAdviceDtlsSecSttlmnt.clear();
		settlementAdviceDtlsSecSttlmnt = null;
		return mapping.findForward("displayCSAGenerateOptionPage");
	}

	public ActionForward saveGenerateCSAOption(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ClaimActionForm claimForm = (ClaimActionForm) form;
		// ClaimsProcessor processor = new ClaimsProcessor();
		Map settlmntAdviceDtls = claimForm.getSettlementAdviceFlags();
		// //System.out.println("Printing Settlement Details :" +
		// settlmntAdviceDtls);
		Set settlmntAdviceDtlsSet = settlmntAdviceDtls.keySet();
		Iterator settlmntAdviceDtlsIterator = settlmntAdviceDtlsSet.iterator();
		StringTokenizer tokenizer = null;
		String memberId = null;
		String cgclan = null;
		String installmentFlag = null;
		String settlmntAdviceFlag = null;
		String settlementDt = null;
		double settlementAmnt = 0.0;
		HashMap checkedDtlsMap = null;
		Vector checkedFirstSettlmntDtls = new Vector();
		Vector checkedSecondSettlmntDtls = new Vector();
		String cgcsanumber = null;
		while (settlmntAdviceDtlsIterator.hasNext()) {
			boolean isMemIdRead = false;
			boolean isCGCLANRead = false;
			boolean instllmntRead = false;
			boolean isSettlmntDtRead = false;
			boolean isSettlmntAmntRead = false;
			String key = (String) settlmntAdviceDtlsIterator.next();
			tokenizer = new StringTokenizer(key, ClaimConstants.CLM_DELIMITER1);
			while (tokenizer.hasMoreElements()) {
				String token = (String) tokenizer.nextElement();
				if (!isSettlmntDtRead) {
					if (!isSettlmntAmntRead) {
						if (!instllmntRead) {
							if (!isCGCLANRead) {
								if (!isMemIdRead) {
									memberId = token;
									isMemIdRead = true;
									continue;
								}
								cgclan = token;
								isCGCLANRead = true;
								continue;
							}
							installmentFlag = token;
							instllmntRead = true;
							continue;
						}
						settlementAmnt = Double.parseDouble(token);
						isSettlmntAmntRead = true;
						continue;
					}
					settlementDt = token;
					isSettlmntDtRead = true;
					continue;
				}
			}
			settlmntAdviceFlag = (String) claimForm
					.getSettlementAdviceFlags(key);
			if ((settlmntAdviceFlag.equals("Y"))
					&& (installmentFlag
							.equals(ClaimConstants.FIRST_INSTALLMENT))) {
				checkedDtlsMap = new HashMap();
				checkedDtlsMap.put(ClaimConstants.CLM_MEMBER_ID, memberId);
				checkedDtlsMap.put(ClaimConstants.CLM_CGCLAN, cgclan);
				checkedDtlsMap.put(
						ClaimConstants.CLM_SETTLMNT_FIRST_SETTLMNT_AMNT,
						new Double(settlementAmnt));
				checkedDtlsMap.put(
						ClaimConstants.CLM_SETTLMNT_FIRST_SETTLMNT_DT,
						settlementDt);
				// cgcsanumber = processor.generateCGCSANumber();
				checkedDtlsMap.put(ClaimConstants.CLM_CGCSA, cgcsanumber);
				if (!checkedFirstSettlmntDtls.contains(checkedDtlsMap)) {
					checkedFirstSettlmntDtls.addElement(checkedDtlsMap);
				}

				// Clearing up the HashMap
				// checkedDtlsMap.clear();
				checkedDtlsMap = null;
			}
			if ((settlmntAdviceFlag.equals("Y"))
					&& (installmentFlag
							.equals(ClaimConstants.SECOND_INSTALLMENT))) {
				checkedDtlsMap = new HashMap();
				checkedDtlsMap.put(ClaimConstants.CLM_MEMBER_ID, memberId);
				checkedDtlsMap.put(ClaimConstants.CLM_CGCLAN, cgclan);
				checkedDtlsMap.put(
						ClaimConstants.CLM_SETTLMNT_SECOND_SETTLMNT_AMNT,
						new Double(settlementAmnt));
				checkedDtlsMap.put(
						ClaimConstants.CLM_SETTLMNT_SECOND_SETTLMNT_DT,
						settlementDt);
				// cgcsanumber = processor.generateCGCSANumber();
				// checkedDtlsMap.put(ClaimConstants.CLM_CGCSA,cgcsanumber);
				if (!checkedSecondSettlmntDtls.contains(checkedDtlsMap)) {
					checkedSecondSettlmntDtls.addElement(checkedDtlsMap);
				}

				// Clearing up the HashMap
				// checkedDtlsMap.clear();
				checkedDtlsMap = null;
			}
		}
		if ((checkedFirstSettlmntDtls.size() == 0)
				&& (checkedSecondSettlmntDtls.size() == 0)) {
			request.setAttribute("message",
					"There are no CGCLAN(s) to generate Advice for.");
			return mapping.findForward("success");
		}
		// //System.out.println("checkedFirstSettlmntDtls :" +
		// checkedFirstSettlmntDtls.size());
		claimForm.setCheckedFirstSettlmntAdviceDtls(checkedFirstSettlmntDtls);
		claimForm.setFirstCounter(checkedFirstSettlmntDtls.size());
		// //System.out.println("checkedSecondSettlmntDtls :" +
		// checkedSecondSettlmntDtls.size());
		claimForm.setCheckedSecondSettlmntAdviceDtls(checkedSecondSettlmntDtls);
		claimForm.setSecondCounter(checkedSecondSettlmntDtls.size());

		/*
		 * Clearing up the Collection Objects
		 */
		// checkedFirstSettlmntDtls.clear();
		checkedFirstSettlmntDtls = null;
		// checkedSecondSettlmntDtls.clear();
		checkedSecondSettlmntDtls = null;
		return mapping.findForward("displayCSASummaryPage");
	}

	public ActionForward savePaymentVoucher(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ClaimActionForm claimForm = (ClaimActionForm) form;
		ClaimsProcessor processor = new ClaimsProcessor();
		Map paymentVoucherIds = claimForm.getPaymentVoucherIds();
		// //System.out.println("Printing payment voucher ids :" +
		// paymentVoucherIds);
		Set paymentVoucherIdsSet = paymentVoucherIds.keySet();
		Iterator paymentVoucherIdsIterator = paymentVoucherIdsSet.iterator();
		Vector details = new Vector();
		HashMap individualDtl = null;
		String cgclan = null;
		// String cgcsa = null;
		String paymentVoucherId = null;
		String whichInstallment = null;
		StringTokenizer tokenizer = null;
		while (paymentVoucherIdsIterator.hasNext()) {
			boolean isCGCLANRead = false;
			// boolean isCGCSARead = false;
			boolean isInstallmentFlag = false;
			String key = (String) paymentVoucherIdsIterator.next();
			tokenizer = new StringTokenizer(key, ClaimConstants.CLM_DELIMITER1);
			while (tokenizer.hasMoreTokens()) {
				String token = tokenizer.nextToken();
				if (!isInstallmentFlag) {
					if (!isCGCLANRead) {
						cgclan = token;
						isCGCLANRead = true;
						continue;
					}
				}
				whichInstallment = token;
				isInstallmentFlag = true;
				continue;

			}
			if (whichInstallment.equals(ClaimConstants.FIRST_INSTALLMENT)) {
				individualDtl = new HashMap();
				individualDtl.put(ClaimConstants.CLM_CGCLAN, cgclan);
				// individualDtl.put(ClaimConstants.CLM_CGCSA,cgcsa);
				individualDtl.put(ClaimConstants.INSTALLMENT_FLAG,
						ClaimConstants.FIRST_INSTALLMENT);
				paymentVoucherId = (String) claimForm.getPaymentVoucherIds(key);

				individualDtl.put(ClaimConstants.CLM_VOUCHER_ID,
						paymentVoucherId);
				if (!details.contains(individualDtl)) {
					details.addElement(individualDtl);
				}

				// Clearing up the HashMap
				// individualDtl.clear();
				individualDtl = null;
			}
			if (whichInstallment.equals(ClaimConstants.SECOND_INSTALLMENT)) {
				individualDtl = new HashMap();
				individualDtl.put(ClaimConstants.CLM_CGCLAN, cgclan);
				// individualDtl.put(ClaimConstants.CLM_CGCSA,cgcsa);
				individualDtl.put(ClaimConstants.INSTALLMENT_FLAG,
						ClaimConstants.SECOND_INSTALLMENT);
				paymentVoucherId = (String) claimForm.getPaymentVoucherIds(key);
				individualDtl.put(ClaimConstants.CLM_VOUCHER_ID,
						paymentVoucherId);
				if (!details.contains(individualDtl)) {
					details.addElement(individualDtl);
				}

				// Clearing up the HashMap
				// individualDtl.clear();
				individualDtl = null;
			}
		}
		User user = getUserInformation(request);
		String userid = user.getUserId();
		processor.saveSettlementAdviceDetail(details, userid);
		claimForm.resetGenerateOptionFlag(mapping, request);
		/*
		 * Clearing up the Collections Object
		 */
		// details.clear();
		details = null;
		return mapping.findForward("paymentvouchersaved");
	}

	public ActionForward saveITPANDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(Log.INFO, "ClaimAction", "saveITPANDetails", "Entered");
		ClaimActionForm claimForm = (ClaimActionForm) form;
		String borrowerId = (String) claimForm.getBorrowerID();
		String itpanOfChiefPromoter = (String) claimForm
				.getItpanOfChiefPromoter();

		char array1[] = itpanOfChiefPromoter.toCharArray();

		if (array1.length == 10
				&& (Character.isLetter(array1[0])
						&& Character.isLetter(array1[1])
						&& Character.isLetter(array1[2])
						&& Character.isLetter(array1[3]) && Character
						.isLetter(array1[4]))
				&& (Character.isDigit(array1[5])
						&& Character.isDigit(array1[6])
						&& Character.isDigit(array1[7]) && Character
						.isDigit(array1[8])) && (Character.isLetter(array1[9]))) {
			HashMap itpanDetails = new HashMap();
			itpanDetails.put(ClaimConstants.CLM_BORROWER_ID, borrowerId);
			itpanDetails.put(ClaimConstants.CLM_ITPAN_OF_CHIEF_PROMOTER,
					itpanOfChiefPromoter);
			// ClaimsProcessor processor = new ClaimsProcessor();
			// processor.saveITPANDetail(borrowerId,itpanOfChiefPromoter);
			claimForm.setItpanDetails(itpanDetails);
			claimForm.setRecoveryFlag(ClaimConstants.DISBRSMNT_NO_FLAG);
		} else {
			throw new InvalidDataException("ITPAN is not in the correct format");
		}

		Log.log(Log.INFO, "ClaimAction", "saveITPANDetails", "Exited");
		return mapping.findForward("claimdetails");
	}

	private ClaimActionForm validateMemIdBidCgpan(String memberId,
			String borrowerId, String inputcgpan, ClaimActionForm claimForm)
			throws Exception {
		Log.log(Log.INFO, "ClaimAction", "validateMemIdBidCgpan", "Entered");
		ClaimsProcessor processor = new ClaimsProcessor();
		// Validating the member Id
		Log.log(Log.INFO, "ClaimAction", "validateMemIdBidCgpan",
				"Going to validate Member Id");
		Vector memberids = processor.getAllMemberIds();
		if (!(memberids.contains(memberId))) {
			Log.log(Log.INFO, "ClaimAction", "validateMemIdBidCgpan",
					"Error validating the Member Id...");
			claimForm.setMemberId("");
			throw new NoMemberFoundException("Member Id :" + memberId
					+ " does not exist in the database.");
		}

		// Clearing up the Vector object
		// memberids.clear();
		memberids = null;

		// Validating the borrower Id.
		if (!borrowerId.equals("")) {
			Log.log(Log.INFO, "ClaimAction", "validateMemIdBidCgpan",
					"validating the Borrower Id with the member id......");
			ArrayList borrowerids = processor.getAllBorrowerIDs(memberId);
			if (!(borrowerids.contains(borrowerId))) {
				Log.log(Log.INFO, "ClaimAction", "validateMemIdBidCgpan",
						"The Borrower Id could not be validated for the Member Id");
				claimForm.setMemberId(memberId);
				claimForm.setBorrowerID("");
				throw new NoDataException(borrowerId
						+ " is not among the borrower Ids for the Member Id :"
						+ memberId
						+ ". Please enter correct Member Id and Borrower Id.");
			}
			// borrowerids.clear();
			borrowerids = null;
		}

		// Validating the CGPAN

		if ((!inputcgpan.equals("")) && (!memberId.equals(""))) {
			CPDAO cpdao = new CPDAO();
			CallableStatement callableStmt = null;
			Connection conn = null;

			int status = -1;
			String errorCode = null;

			try {
				conn = DBConnection.getConnection();
				callableStmt = conn
						.prepareCall("{? = call funcCheckCgpanMemberID(?,?,?)}");
				callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
				callableStmt.setString(2, inputcgpan);
				callableStmt.setString(3, memberId);
				callableStmt.registerOutParameter(4, java.sql.Types.VARCHAR);
				callableStmt.execute();
				status = callableStmt.getInt(1);
				errorCode = callableStmt.getString(4);
				// //System.out.println("errorCode:"+errorCode);
				if (status == Constants.FUNCTION_FAILURE) {
					Log.log(Log.ERROR, "CPDAO", "getMemberIDForCGPAN()",
							"SP returns a 1. Error code is :" + errorCode);
					callableStmt.close();
					throw new DatabaseException(errorCode);
				} else if (status == Constants.FUNCTION_SUCCESS) {
					callableStmt.close();
				}
			} catch (SQLException sqlexception) {
				Log.log(Log.ERROR, "CPDAO", "getMemberIDForCGPAN()",
						"Error retrieving MemberId for the CGPAN!");
				throw new DatabaseException(sqlexception.getMessage());
			} finally {
				DBConnection.freeConnection(conn);
			}

		}

		if ((!inputcgpan.equals("")) && (borrowerId.equals(""))) {
			Log.log(Log.INFO, "ClaimAction", "validateMemIdBidCgpan",
					"for the given cgpan, retrieving the borrower id......");
			borrowerId = (processor.getBorowwerForCGPAN(inputcgpan)).trim();
			// //System.out.println("validateMemIdBidCgpan-borrowerId:"+borrowerId);
			claimForm.setBorrowerID(borrowerId);
			Log.log(Log.INFO, "ClaimAction", "validateMemIdBidCgpan",
					"for the given cgpan, successfully retrieved the borrower id......");

		} else if ((!inputcgpan.equals("")) && (!borrowerId.equals(""))) {

			String borrowerForThisCgpan = processor
					.getBorowwerForCGPAN(inputcgpan);
			// //System.out.println("borrowerForThisCgpan:"+borrowerForThisCgpan);
			if (!(borrowerForThisCgpan.equals(borrowerId))) {
				Log.log(Log.INFO,
						"ClaimAction",
						"validateMemIdBidCgpan",
						"Error: Borrower Id for the cgpan and the Borrower Id input in the text field are not same.");
				claimForm.setMemberId(memberId);
				claimForm.setCgpan("");
				throw new NoDataException(
						"Please enter correct Borrower Id and CGPAN");
			}
		}
		Log.log(Log.INFO, "ClaimAction", "validateMemIdBidCgpan",
				"Member Id is :" + claimForm.getMemberId());
		Log.log(Log.INFO, "ClaimAction", "validateMemIdBidCgpan",
				"Borrower Id is :" + claimForm.getBorrowerID());
		Log.log(Log.INFO, "ClaimAction", "validateMemIdBidCgpan", "CGPAN is :"
				+ claimForm.getCgpan());
		Log.log(Log.INFO, "ClaimAction", "validateMemIdBidCgpan", "Exited");
		return claimForm;
	}

	private boolean checkIfITPANAvailable(String memberId, String borrowerId)
			throws Exception {
		Log.log(Log.INFO, "ClaimAction", "checkIfITPANAvailable", "Entered");
		GMProcessor processor = new GMProcessor();
		BorrowerDetails bidDtls = processor.getBorrowerDetailsForBID(memberId,
				borrowerId);
		SSIDetails ssiDtls = bidDtls.getSsiDetails();
		String cpITPAN = ssiDtls.getCpITPAN();
		Log.log(Log.INFO, "ClaimAction", "checkIfITPANAvailable",
				"Chief Promoter ITPAN is :" + cpITPAN);
		if ((cpITPAN != null) && (!cpITPAN.equals(""))) {
			return true;
		}
		Log.log(Log.INFO, "ClaimAction", "checkIfITPANAvailable", "Exited");
		return false;
	}

	// added following code on 06-Apr-2010 for CGPAN wise Claim processing

	public ActionForward displayClaimRefNumberDtlsMod_27062011(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ClaimActionForm claimForm = (ClaimActionForm) form;
		Vector firstClms = (Vector) claimForm.getFirstInstallmentClaims();

		// //System.out.println("Number of applications for first claim installments :"
		// + firstinstllmntclaims.size());
		Vector secClms = (Vector) claimForm.getSecondInstallmentClaims();
		String claimRefNumber = (String) request
				.getParameter(ClaimConstants.CLM_CLAIM_REF_NUMBER);
		// //System.out.println("displayClaimRefNumberDtlsMod Entered:"+claimRefNumber);

		double clmEligibleAmnt = 0.0;
		// //System.out.println("Test Claim Proceedings:"+(String)request.getParameter("isClaimProceedings"));
		// //System.out.println("IsClaimProceedings:"+(String)claimForm.getIsClaimProceedings());
		String isClaimProceedings = (String) claimForm.getIsClaimProceedings();
		// //System.out.println("isClaimProceedings:"+isClaimProceedings);

		if (firstClms != null) {
			for (int i = 0; i < firstClms.size(); i++) {
				ClaimDetail clmDtl = (ClaimDetail) firstClms.elementAt(i);
				if (clmDtl == null) {
					continue;
				}
				String clmRefNum = (String) clmDtl.getClaimRefNum();
				if ((clmRefNum != null) && (clmRefNum.equals(claimRefNumber))) {
					clmEligibleAmnt = (double) clmDtl.getEligibleClaimAmt();
				}
			}
		}
		if (secClms != null) {
			for (int i = 0; i < secClms.size(); i++) {
				ClaimDetail clmDtl = (ClaimDetail) secClms.elementAt(i);
				if (clmDtl == null) {
					continue;
				}
				String clmRefNum = (String) clmDtl.getClaimRefNum();
				if ((clmRefNum != null) && (clmRefNum.equals(claimRefNumber))) {
					clmEligibleAmnt = (double) clmDtl.getEligibleClaimAmt();
				}
			}
		}

		ClaimsProcessor processor = new ClaimsProcessor();
		// //System.out.println("Claim Ref Number :" + claimRefNumber);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		ClaimDetail claimdetail = processor
				.getDetailsForClaimRefNumber(claimRefNumber);
		claimdetail.setEligibleClaimAmt(clmEligibleAmnt);
		// //System.out.println("Line number  6238-Claim Ref Number and amt from Claim Action :"
		// + claimdetail.getClaimRefNum()+clmEligibleAmnt);
		claimdetail.setClaimRefNum(claimRefNumber);
		java.util.Date dtOfRecallNotice = claimdetail
				.getDateOfIssueOfRecallNotice();
		// //System.out.println("dtOfRecallNotice :" +
		// dtOfRecallNotice.toString());
		if (dtOfRecallNotice != null) {
			claimdetail.setDateOfIssueOfRecallNoticeStr(sdf
					.format(dtOfRecallNotice));
		}
		java.util.Date npaDate = claimdetail.getNpaDate();
		// //System.out.println("npaDate :" + npaDate.toString());
		if (npaDate != null) {
			claimdetail.setNpaDateStr(sdf.format(npaDate));
		}
		java.util.Date dtOfNPAReportedToCGTSI = claimdetail
				.getDtOfNPAReportedToCGTSI();
		// //System.out.println("dtOfNPAReportedToCGTSI :" +
		// dtOfNPAReportedToCGTSI.toString());
		if (dtOfNPAReportedToCGTSI != null) {
			claimdetail.setDtOfNPAReportedToCGTSIStr(sdf
					.format(dtOfNPAReportedToCGTSI));
		}
		double amtClaimed = claimdetail.getAppliedClaimAmt();
		clmEligibleAmnt = claimdetail.getEligibleClaimAmt();
		// String memId = claimdetail.getMliId();
		// String borrowerId = claimdetail.getBorrowerId();
		// Log.log(Log.INFO,"Validator","validateDisplayClaimApproval()","*******************************");
		// Log.log(Log.INFO,"Validator","validateDisplayClaimApproval()","firstClms.size() :"
		// + firstClms.size());
		// Log.log(Log.INFO,"Validator","validateDisplayClaimApproval()","secClms.size() :"
		// + secClms.size());
		// Log.log(Log.INFO,"Validator","validateDisplayClaimApproval()","memId :"
		// + memId);
		// Log.log(Log.INFO,"Validator","validateDisplayClaimApproval()","borrowerId :"
		// + borrowerId);
		// Log.log(Log.INFO,"Validator","validateDisplayClaimApproval()","clmEligibleAmnt :"
		// + clmEligibleAmnt);
		// Log.log(Log.INFO,"Validator","validateDisplayClaimApproval()","*******************************");
		String amntClaimed = Double.toString(amtClaimed);
		claimForm.setAmountclaimed(amntClaimed);
		claimForm.setDanSummaryReportDetails(claimdetail
				.getDanSummaryReportDetails());
		claimForm.setClaimdetail(claimdetail);
		claimForm.setTcInterestChargeForThisBorrower("");
		claimForm.setWcOtherChargesAsOnNPA("");
		claimForm.setTcPrinRecoveriesAfterNPA("");
		claimForm.setTcInterestChargesRecovAfterNPA("");
		claimForm.setWcPrincipalRecoveAfterNPA("");
		claimForm.setWcothercgrgsRecAfterNPA("");
		// added by sukumar@path for reinitialised Dt of Receipt and Recovery
		// details
		claimForm.setDateofReceipt(null);
		claimForm.setTcrecovery(0);
		claimForm.setWcrecovery(0);
		claimForm.setTotalRecovery(0);

		//System.out.println("Before jai code.....!");
		// jai code
		// TermLoanCapitalLoanDetail

		Vector arrylist = claimdetail.getCgpanDetails();

		FunctionForCgpanWiseFirstClaim(claimdetail, claimForm);
		//

		// ArrayList
		// workinCaptDistArry=claimApplication.getWorkingCapitalDtls();
		// Vector v=claimApplication.getTermCapitalDtls();
		//
		// //setOutstandingAsOnDateOfNPA
		// for (int i=0;i<workinCaptDistArry.size();i++)
		// {
		// WorkingCapitalDetail wcd=workinCaptDistArry.get(i);
		// String CGPAN=wcd.getCgpan();
		// double AMtASonNpa=wcd.getOutstandingAsOnDateOfNPA();
		// }
		Connection connection = DBConnection.getConnection();
		// retrive data for adding new filed

		String qryufordata = " SELECT  P.PMR_CHIEF_GENDER,S.SSI_STATE_NAME, S.SSI_TYPE_OF_ACTIVITY,CM.SCM_NAME FROM SSI_DETAIL S,PROMOTER_DETAIL P,SCHEME_MASTER CM,APPLICATION_DETAIL A \n "
				+ " WHERE BID=(SELECT BID FROM CLAIM_DETAIL_TEMP WHERE CLM_REF_NO='"
				+ claimRefNumber
				+ "')  \n "
				+ " AND P.SSI_REFERENCE_NUMBER=S.SSI_REFERENCE_NUMBER \n "
				+ " AND A.SSI_REFERENCE_NUMBER=S.SSI_REFERENCE_NUMBER \n "
				+ " AND CM.SCM_ID=A.SCM_ID ";
		try {
			Statement strQury = connection
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
			ResultSet rsdata = strQury.executeQuery(qryufordata);
			while (rsdata.next()) {
				claimForm.setNewChipParmoGender(rsdata.getString(1));
				claimForm.setNewborowerState(rsdata.getString(2));
				claimForm.setNewTypeActivity(rsdata.getString(3));
				claimForm.setNewSchemName(rsdata.getString(4));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		// CODE FOR checking state belongs to NER or not
		String sateforTest = claimForm.getNewborowerState();
		String nerState = "SELECT * FROM STATE_GR_MAP WHERE SCR_STATE_NAME='"
				+ sateforTest + "'";
		try {
			Statement strQury1 = connection
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
			ResultSet rsdata1 = strQury1.executeQuery(nerState);
			String nerFlag = "N";
			while (rsdata1.next()) {
				nerFlag = "Y";

			}
			claimForm.setNewNERFlag(nerFlag);

		} catch (Exception e) {
			e.printStackTrace();
		}
		// over code for NER

		// /////////////////////////

		double tc_total_amt = claimdetail.getTotalTCOSAmountAsOnNPA();

		// //System.out.println("The Total Tc Amount is :---->"+tc_total_amt);

		String falgforCasesafet = "N";

		Iterator interator = arrylist.iterator();
		while (interator.hasNext()) { // loop start
			try {

				Statement str = connection.createStatement(
						ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				HashMap hashmap = (HashMap) interator.next();
				// //System.out.println("The Value :-->"+hashmap.values().toString()+"Kerys :--->"+hashmap.keySet()
				// );

				String cgpanfordate = (String) hashmap
						.get(ClaimConstants.CLM_CGPAN);
				// //System.out.println("The CGPAN Is :---->"+cgpanfordate);

				// jai code for foure things
				// claimForm.getDanSummaryReportDetails();

				// jai code for foure things

				String queryNPAOutStanfAMt1 = "SELECT TRM_AMOUNT_SANCTIONED_DT A, NULL  B FROM TERM_LOAN_DETAIL WHERE CGPAN='"
						+ cgpanfordate
						+ "' UNION ALL SELECT WCP_FB_LIMIT_SANCTIONED_DT A,WCP_NFB_LIMIT_SANCTIONED_DT B FROM WORKING_CAPITAL_DETAIL WHERE CGPAN='"
						+ cgpanfordate + "'";

				java.util.Date date = new java.util.Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				int month = calendar.get(Calendar.MONTH);
				int day = calendar.get(Calendar.DATE);
				int year = calendar.get(Calendar.YEAR);
				day = 02;
				month = 01;
				year = 2009;
				calendar.set(Calendar.MONTH, month);
				calendar.set(Calendar.DATE, day);
				calendar.set(Calendar.YEAR, year);
				java.util.Date caesesafter2009 = calendar.getTime();
				claimForm.setFalgforCasesafet(falgforCasesafet);

				ResultSet rs1 = str.executeQuery(queryNPAOutStanfAMt1);
				while (rs1.next()) {

					java.util.Date firstDate = null;
					firstDate = rs1.getDate(1);
					java.util.Date secondDate = null;
					secondDate = rs1.getDate(2);
					// //System.out.println("The Value Of sanctioned date is 1:--->"+rs1.getDate(1));
					// //System.out.println("The Value Of sanctioned date is 2 :--->"+rs1.getDate(2));
					try {
						if (!firstDate.equals(null)) {

							if (firstDate.after(caesesafter2009)) {
								// //System.out.println("inside if ..");
								falgforCasesafet = "Y";
								claimForm.setFalgforCasesafet(falgforCasesafet);
								break;

							}
						}
						if (!secondDate.equals(null)) {
							if (secondDate.after(caesesafter2009)) {
								// //System.out.println("inside if ..");
								// microFlag="Y";
								falgforCasesafet = "Y";
								claimForm.setFalgforCasesafet(falgforCasesafet);
								break;
							}
						}

						// claimForm.setFalgforCasesafet(falgforCasesafet);

					} catch (Exception we) {
						we.getMessage();
					}
					// falgforCasesafet set this as true if all comes under
					// after 2009
				}

			} catch (Exception e) {
				e.getMessage();
				//System.out.println("the Exception is rised.....!"
					//	+ e.getMessage());
			}
			 finally {
					DBConnection.freeConnection(connection);

				}
		} // loop over

		// jai code

		// //System.out.println("before passing@@@@@@@@@@@@@@@@@@:->"+
		// claimForm.getFalgforCasesafet());
		return mapping.findForward("displayClaimRefNumDtlsPage");
	}

	public void FunctionForCgpanWiseFirstClaim(ClaimDetail claimdetail,
			ClaimActionForm claimForm) throws Exception {

		// //System.out.println("Inside Method FunctionForCgpanWiseFirstClaim.....! ");
		// newCGPAN
		// newGuarnteeIssueAmt
		// newAmtOutstandAsOnNPA
		// newAmtRecoverAfterNPA clmForm
		// newAmtClaimByMli
		// newAmtDeductedFromMli

		Vector arrylist = claimdetail.getCgpanDetails();
		ArrayList forCGPANWiseData = new ArrayList();
		ClaimApplication claimApplication = claimForm.getClaimapplication();
		Vector tld = claimApplication.getTermCapitalDtls();
		ArrayList wcd = claimApplication.getWorkingCapitalDtls();
		Vector recoDtl = claimApplication.getRecoveryDetails();
		ArrayList claimSummeryDtl = claimApplication.getClaimSummaryDtls();

		for (int i = 0; i < arrylist.size(); i++) { // main for start
			ClaimActionForm clmForm = new ClaimActionForm();
			HashMap hashmap = (HashMap) arrylist.get(i);
			String cgpanfordate = (String) hashmap
					.get(ClaimConstants.CLM_CGPAN);
			// adding CGPAN Into Form bean

			clmForm.setNewCGPAN(cgpanfordate);
			// hashmap.get()
			// retriving and adding gaurantee Issued amount
			double tcApprovedAmnt = 0.0;
			double wcApprovedAmnt = 0.0;
			String loanType = (String) hashmap
					.get(ClaimConstants.CGPAN_LOAN_TYPE);
			// //System.out.println("loanType:"+loanType);
			if (loanType.equals(ClaimConstants.CGPAN_TC_LOAN_TYPE)
					|| loanType.equals(ClaimConstants.CGPAN_CC_LOAN_TYPE)) {
				tcApprovedAmnt = ((Double) hashmap
						.get(ClaimConstants.CLM_APPLICATION_APPRVD_AMNT))
						.doubleValue();
				// //System.out.println("tcApprovedAmnt:"+tcApprovedAmnt);
				clmForm.setNewGuarnteeIssueAmt(tcApprovedAmnt);
			} else if (loanType.equals(ClaimConstants.CGPAN_WC_LOAN_TYPE)) {
				wcApprovedAmnt = ((Double) hashmap
						.get(ClaimConstants.CLM_APPLICATION_APPRVD_AMNT))
						.doubleValue();
				// //System.out.println("wcApprovedAmnt:"+wcApprovedAmnt);
				clmForm.setNewGuarnteeIssueAmt(wcApprovedAmnt);
			}
			//
			// retriving and adding gaurantee Issued amount over
			if (loanType.equals(ClaimConstants.CGPAN_TC_LOAN_TYPE)
					|| loanType.equals(ClaimConstants.CGPAN_CC_LOAN_TYPE)) {
				// retriving term loan outStandinASOnNPA amount
				for (int j = 0; j < tld.size(); j++) {
					TermLoanCapitalLoanDetail termLD = (TermLoanCapitalLoanDetail) tld
							.get(j);
					// //System.out.println("The CGPAN FROM TERM LOAD DETAIL:--->"+termLD.getCgpan());

					String cgpanfromTCGurnteeIssue = termLD.getCgpan();
					if (cgpanfordate.equals(cgpanfromTCGurnteeIssue)) {
						clmForm.setNewAmtOutstandAsOnNPA(termLD
								.getOutstandingAsOnDateOfNPA());
					}
					// //System.out.println("The Term Loan NPA Outstanding FROM TERM LOAD DETAIL:--->"+termLD.getOutstandingAsOnDateOfNPA());

				}
			} else {
				// retriving term loan outStandinASOnNPA amount over
				// retriving working capital outStandinASOnNPA amount
				for (int k = 0; k < wcd.size(); k++) {
					WorkingCapitalDetail workingLD = (WorkingCapitalDetail) wcd
							.get(k);

					// //System.out.println("The CGPAN FROM TERM LOAD DETAIL:--->"+workingLD.getCgpan());
					String cgpanfromWCGurnteeIssue = workingLD.getCgpan();
					if (cgpanfordate.equals(cgpanfromWCGurnteeIssue)) {
						clmForm.setNewAmtOutstandAsOnNPA(workingLD
								.getOutstandingAsOnDateOfNPA());
					}

					// //System.out.println("The Working capital NPA Outstading FROM TERM LOAD DETAIL:--->"+workingLD.getOutstandingAsOnDateOfNPA());
				}
			}
			// retriving working capital outStandinASOnNPA amount over

			// recovery code start
			double tcRecoveryMade = 0.0;
			double wcRecoveryMade = 0.0;
			for (int rec = 0; rec < recoDtl.size(); rec++) {
				RecoveryDetails recDtl = (RecoveryDetails) recoDtl.get(rec);
				String recovryCgpan = recDtl.getCgpan();
				if (loanType.equals(ClaimConstants.CGPAN_TC_LOAN_TYPE)
						|| loanType.equals(ClaimConstants.CGPAN_CC_LOAN_TYPE)) {
					if (cgpanfordate.equals(recovryCgpan)) {
						double tcInterestAndOtherCharges = recDtl
								.getTcInterestAndOtherCharges();
						double tcPrincipal = recDtl.getTcPrincipal();
						tcRecoveryMade = tcInterestAndOtherCharges
								+ tcPrincipal;
						clmForm.setNewAmtRecoverAfterNPA(tcRecoveryMade);
						// //System.out.println("The Recovery amount is :--->"+clmForm.getNewAmtRecoverAfterNPA());
					}
				} // loan type check over
				else if (cgpanfordate.equals(recovryCgpan)) {
					double wcAmount = recDtl.getWcAmount();
					// //System.out.println("WC Amount :" + wcAmount);
					double wcOtherCharges = recDtl.getWcOtherCharges();
					wcRecoveryMade = wcAmount + wcOtherCharges;
					clmForm.setNewAmtRecoverAfterNPA(wcRecoveryMade);
					// //System.out.println("The Recovery amount is :--->"+clmForm.getNewAmtRecoverAfterNPA());
				}
			}
			// recovery code over

			// claim applied by mli code satrt
			double tcClaimApplied = 0.0;
			double wcClaimApplied = 0.0;
			for (int clmsum = 0; clmsum < claimSummeryDtl.size(); clmsum++) {
				ClaimSummaryDtls clmSummaryDtl = (ClaimSummaryDtls) claimSummeryDtl
						.get(clmsum);
				// String clmSummryCgpan = clmSummaryDtl.getCgpan();

				String clmSummryCgpan = clmSummaryDtl.getCgpan();
				String panType = clmSummryCgpan.substring(11, 13);
				// clmAppliedAmnt = clmSummaryDtl.getAmount();

				if (cgpanfordate.equals(clmSummryCgpan)) {
					tcClaimApplied = clmSummaryDtl.getAmount();
					clmForm.setNewAmtClaimByMli(tcClaimApplied);
					// //System.out.println("Claim Applied By MLI is :---->"+clmForm.getNewAmtClaimByMli());
				}

				// else {
				// wcClaimApplied = clmSummaryDtl.getAmount();
				// //tcClaimApplied = clmSummaryDtl.getAmount();
				// clmForm.setNewAmtClaimByMli(wcClaimApplied);
				// //System.out.println("Claim Applied By MLI is :---->"+
				// clmForm.getNewAmtClaimByMli());
				// }
			}
			// claim applied by mli code over

			// amount deducted from MLI if ANy start
			double tcServiceFee = 0.0;
			double wcServiceFee = 0.0;
			if (loanType.equals(ClaimConstants.CGPAN_TC_LOAN_TYPE)
					|| loanType.equals(ClaimConstants.CGPAN_CC_LOAN_TYPE)) {
				tcServiceFee = ((Double) hashmap
						.get(ClaimConstants.CLM_TOTAL_SERVICE_FEE))
						.doubleValue();
				clmForm.setNewAmtDeductedFromMli(tcServiceFee);
				// //System.out.println("The amount deducted From Mli if Any is :---->"+clmForm.getNewAmtDeductedFromMli());
			} else {
				wcServiceFee = ((Double) hashmap
						.get(ClaimConstants.CLM_TOTAL_SERVICE_FEE))
						.doubleValue();
				clmForm.setNewAmtDeductedFromMli(wcServiceFee);
				// //System.out.println("The amount deducted From Mli if Any is :---->"+clmForm.getNewAmtDeductedFromMli());
			}

			// amount deducted from MLI if any Over

			// adding Object To arrayList
			forCGPANWiseData.add(clmForm);
		} // main for stop
		claimForm.setForCGPANWiseDataArray(forCGPANWiseData);
		// ********
		// //System.out.println("after main Loop stop....!");

		// return null;
	}

	public ActionForward displayClaimApprovalMod_Jagadeesh(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(Log.INFO, "ClaimAction", "displayClaimApprovalMod()", "Entered");
		// //System.out.println("ClaimAction displayClaimApprovalMod() Entered");
		ClaimActionForm claimForm = (ClaimActionForm) form;
		ClaimsProcessor processor = new ClaimsProcessor();
		String flagClmRefDtl = (String) claimForm.getClmRefDtlSet();
		java.sql.Date stDt = java.sql.Date.valueOf(DateHelper
				.stringToSQLdate(claimForm.getDateOfTheDocument36()));
		java.sql.Date endDt = java.sql.Date.valueOf(DateHelper
				.stringToSQLdate(claimForm.getDateOfTheDocument37()));
		// //System.out.println("From date:"+claimForm.getDateOfTheDocument36());
		// //System.out.println("To date:"+claimForm.getDateOfTheDocument37());

		Log.log(Log.INFO, "ClaimAction", "displayClaimApprovalMod()",
				"*******************************");
		//System.out.println("ClaimAction displayClaimApproval() flagClmRefDtl :"
			//	+ flagClmRefDtl);
		Log.log(Log.INFO, "ClaimAction", "displayClaimApprovalMod()",
				"flagClmRefDtl :" + flagClmRefDtl);
		Log.log(Log.INFO, "ClaimAction", "displayClaimApprovalMod()",
				"*******************************");
		ClaimDetail clmdtl = null;
		String clmRefNum = null;
		String payAmntNow = null;
		String userRemarks = null;
		String microFlag = null;
		double tcServiceFee = 0;
		double wcServiceFee = 0;
		double tcClaimEligibleAmt = 0;
		double wcClaimEligibleAmt = 0;
		double tcFirstInstallment = 0;
		double wcFirstInstallment = 0;
		double totalTCOSAmountAsOnNPA = 0;
		double totalWCOSAmountAsOnNPA = 0;
		double tcrecovery = 0;
		double wcrecovery = 0;
		// double totalClaimEligibleAmt = 0;
		double tcIssued = 0;
		double wcIssued = 0;
		Administrator admin = new Administrator();
		// jai code
		String falgforCasesafet = "N";
		// jai code

		// The CGTSI active userIds is populated from the database.
		String cgtsiBankId = Constants.CGTSI_USER_BANK_ID;
		// //System.out.println("cgtsiBankId:"+cgtsiBankId);
		String cgtsiZoneId = Constants.CGTSI_USER_ZONE_ID;
		// //System.out.println("cgtsiZoneId:"+cgtsiZoneId);

		String cgtsiBrnId = Constants.CGTSI_USER_BRANCH_ID;
		// //System.out.println("cgtsiBrnId:"+cgtsiBrnId);
		ArrayList userIds = admin.getUsers(cgtsiBankId + cgtsiZoneId
				+ cgtsiBrnId);

		// Remove the logged in userId and the Admin userId.
		User loggedUser = getUserInformation(request);

		String loggedUserId = loggedUser.getUserId();
		// //System.out.println("loggedUserId:"+loggedUserId);
		userIds.remove(loggedUserId);
		userIds.remove("ADMIN");
		if (userIds.contains("DEMOUSER")) {
			userIds.remove("DEMOUSER");
		}
		if (userIds.contains("AUDITOR")) {
			userIds.remove("AUDITOR");
		}
		claimForm.setUserIds(userIds);
		claimForm.setUserId(loggedUserId);

		// //System.out.println("Flag from Claim Ref Dtl :" +
		// claimForm.getClmRefDtlSet());
		// The vector contains objects of ClaimDetail for first claim
		double maxClmApprvdAmnt = 0.0;
		java.util.Date fromDate = null;
		java.util.Date toDate = null;
		java.util.Date dateofReceipt = null;
		User user = getUserInformation(request);
		String designation = user.getDesignation();
		// //System.out.println("designation:"+designation);
		String loggedUsr = user.getUserId();
		Log.log(Log.INFO, "ClaimAction", "displayClaimApprovalMod()",
				"designation :" + designation);
		HashMap clmLimitDetails = processor.getClaimLimitDtls(designation);
		maxClmApprvdAmnt = ((Double) clmLimitDetails
				.get(ClaimConstants.CLM_MAX_APPROVAL_AMOUNT)).doubleValue();
		// //System.out.println("maxClmApprvdAmnt:"+maxClmApprvdAmnt);
		Log.log(Log.INFO, "ClaimAction", "displayClaimApprovalMod()",
				"maxClmApprvdAmnt :" + maxClmApprvdAmnt);
		fromDate = (java.util.Date) clmLimitDetails
				.get(ClaimConstants.CLM_VALID_FROM_DT);
		// //System.out.println("fromDate :" + fromDate);
		toDate = (java.util.Date) clmLimitDetails
				.get(ClaimConstants.CLM_VALID_TO_DT);
		// //System.out.println("toDate :" + toDate);
		if (flagClmRefDtl != null) {
			if (flagClmRefDtl.equals(ClaimConstants.DISBRSMNT_YES_FLAG)) {

				clmdtl = claimForm.getClaimdetail();
				if (clmdtl != null) {

					clmRefNum = (String) clmdtl.getClaimRefNum();
					// //System.out.println(" jai clmRefNum:"+clmRefNum);
					dateofReceipt = claimForm.getDateofReceipt();
					// //System.out.println("dateofReceipt:"+dateofReceipt);

					userRemarks = (String) clmdtl.getComments();
					// //System.out.println("User Remarks:"+userRemarks);

					microFlag = claimForm.getMicroCategory();
					String nerfalg = claimForm.getNewNERFlag();
					String womenOprator = claimForm.getNewChipParmoGender();

					// //System.out.println("Teh Micro Flag from displayClaimApprovalMod :-->"+microFlag);
					falgforCasesafet = claimForm.getFalgforCasesafet();

					// jai code for CGPAN wise data

					// //System.out.println("Inside Java Code test........!");

					ArrayList mainArry = new ArrayList(); // for adding

					String[] hidcgpan = request.getParameterValues("hidcgpan");
					String[] hidgaurIssue = request
							.getParameterValues("hidgaurIssue");
					String[] outstandingAsOnNPA = request
							.getParameterValues("outstandingAsOnNPA");
					String[] recoverafterNPA = request
							.getParameterValues("recoverafterNPA");
					String[] netOutsandingAmt = request
							.getParameterValues("netOutsandingAmt");
					String[] hidclaimbymliamt = request
							.getParameterValues("hidclaimbymliamt");
					String[] claimEligibleAmt = request
							.getParameterValues("claimEligibleAmt");
					String[] firstInstallAmt = request
							.getParameterValues("firstInstallAmt");
					String[] dedecutByMliIfAny = request
							.getParameterValues("dedecutByMliIfAny");
					String[] paybleAmt = request
							.getParameterValues("paybleAmt");

					mainArry.add(hidcgpan);
					mainArry.add(hidgaurIssue);
					mainArry.add(outstandingAsOnNPA);
					mainArry.add(recoverafterNPA);
					mainArry.add(netOutsandingAmt);
					mainArry.add(hidclaimbymliamt);
					mainArry.add(claimEligibleAmt);
					mainArry.add(firstInstallAmt);
					mainArry.add(dedecutByMliIfAny);
					mainArry.add(paybleAmt);

					CPDAO cpdao1 = new CPDAO();
					// ClaimDetail
					payAmntNow = cpdao1.insertClaimProcessDetails1(clmRefNum,
							userRemarks, mainArry, dateofReceipt, microFlag,
							falgforCasesafet, clmdtl, nerfalg, womenOprator);

					// jai code for CGPAN wise data over

					// tcIssued = claimForm.getTcIssued();
					// //System.out.println("tcIssued:"+tcIssued);
					// wcIssued = claimForm.getWcIssued();
					// //System.out.println("wcIssued:"+wcIssued);
					//
					// totalTCOSAmountAsOnNPA =
					// clmdtl.getTotalTCOSAmountAsOnNPA();
					// totalWCOSAmountAsOnNPA =
					// clmdtl.getTotalWCOSAmountAsOnNPA();
					// //System.out.println("totalTCOSAmountAsOnNPA:"+totalTCOSAmountAsOnNPA);
					// //
					// //System.out.println("totalWCOSAmountAsOnNPA:"+totalWCOSAmountAsOnNPA);
					// tcrecovery = claimForm.getTcrecovery();
					// wcrecovery = claimForm.getWcrecovery();
					//
					// //System.out.println("microFlag:"+microFlag);
					// //System.out.println("tcrecovery:"+tcrecovery);
					// //System.out.println("wcrecovery:"+wcrecovery);
					// // totalClaimEligibleAmt =
					// claimForm.getTotalClaimEligibleAmt();
					// //
					// //System.out.println("totalClaimEligibleAmt:"+totalClaimEligibleAmt);
					// // tcClaimEligibleAmt =
					// claimForm.getTcClaimEligibleAmt();
					// //
					// if((Math.min(tcIssued,totalTCOSAmountAsOnNPA)-(tcrecovery))<=500000){
					// if(((tcIssued+wcIssued)<=500000)&&(microFlag.equals("Y")))
					// {
					// //jai code
					// if (falgforCasesafet.equals("Y"))
					// {
					//
					// tcClaimEligibleAmt
					// =Math.round((Math.min(tcIssued,totalTCOSAmountAsOnNPA)-(tcrecovery))*0.85);
					// }else if (falgforCasesafet.equals("N")){
					// //jai code
					// tcClaimEligibleAmt
					// =Math.round((Math.min(tcIssued,totalTCOSAmountAsOnNPA)-(tcrecovery))*0.80);
					// }
					// }
					// else
					// if(((tcIssued+wcIssued)<=500000)&&(microFlag.equals("N")))
					// {
					// tcClaimEligibleAmt
					// =Math.round((Math.min(tcIssued,totalTCOSAmountAsOnNPA)-(tcrecovery))*0.75);
					// }
					// else{
					// tcClaimEligibleAmt
					// =Math.round((Math.min(tcIssued,totalTCOSAmountAsOnNPA)-(tcrecovery))*0.75);
					// }
					// //
					// //System.out.println("tcClaimEligibleAmt:"+tcClaimEligibleAmt);
					// // wcClaimEligibleAmt =
					// claimForm.getWcClaimEligibleAmt();
					// //
					// if((Math.min(wcIssued,totalWCOSAmountAsOnNPA)-(wcrecovery))<=500000){
					// if(((tcIssued+wcIssued)<=500000)&&(microFlag.equals("Y")))
					// {
					// //jai code
					// if (falgforCasesafet.equals("Y"))
					// {
					// wcClaimEligibleAmt
					// =Math.round((Math.min(wcIssued,totalWCOSAmountAsOnNPA)-(wcrecovery))*0.85);
					//
					// } else if (falgforCasesafet.equals("N"))
					// {
					// //jai code
					// wcClaimEligibleAmt
					// =Math.round((Math.min(wcIssued,totalWCOSAmountAsOnNPA)-(wcrecovery))*0.80);
					// }
					// }
					// else
					// if(((tcIssued+wcIssued)<=500000)&&(microFlag.equals("N")))
					// {
					// wcClaimEligibleAmt
					// =Math.round((Math.min(wcIssued,totalWCOSAmountAsOnNPA)-(wcrecovery))*0.75);
					// }
					// else {
					// wcClaimEligibleAmt
					// =Math.round((Math.min(wcIssued,totalWCOSAmountAsOnNPA)-(wcrecovery))*0.75);
					// }
					// //System.out.println("wcClaimEligibleAmt:"+wcClaimEligibleAmt);
					// // tcFirstInstallment =
					// claimForm.getTcFirstInstallment();
					// tcFirstInstallment = Math.round(tcClaimEligibleAmt*0.75);
					// //System.out.println("tcFirstInstallment:"+tcFirstInstallment);
					// // wcFirstInstallment =
					// claimForm.getWcFirstInstallment();
					// wcFirstInstallment = Math.round(wcClaimEligibleAmt*0.75);
					// //System.out.println("wcFirstInstallment:"+wcFirstInstallment);
					// tcServiceFee = claimForm.getAsfDeductableforTC();
					// //System.out.println("tcServiceFee:"+tcServiceFee);
					// wcServiceFee = claimForm.getAsfDeductableforWC();
					// //System.out.println("wcServiceFee:"+wcServiceFee);
					// // payAmntNow = (String)clmdtl.getTotalAmtPayNow();
					// payAmntNow =
					// Double.toString(tcFirstInstallment+wcFirstInstallment-tcServiceFee-wcServiceFee);
					// clmdtl.setTotalAmtPayNow(payAmntNow);
					// //System.out.println("payAmntNow:"+payAmntNow);
					//
					//
					// CPDAO cpdao = new CPDAO();
					// cpdao.insertClaimProcessDetails(clmRefNum,userRemarks,tcServiceFee,wcServiceFee,tcClaimEligibleAmt,wcClaimEligibleAmt,tcFirstInstallment,wcFirstInstallment,totalTCOSAmountAsOnNPA,totalWCOSAmountAsOnNPA,tcrecovery,wcrecovery,dateofReceipt);

					if ((payAmntNow != null) && (!payAmntNow.equals(""))) {
						if ((Double.parseDouble(payAmntNow)) < 0.0) {
							payAmntNow = "0.0";
						}
					} else {
						payAmntNow = "0.0";
					}
					/*
					 * double clmEligibleAmnt = clmdtl.getEligibleClaimAmt();
					 * if((payAmntNow != null) &&
					 * (Double.parseDouble(payAmntNow)) > clmEligibleAmnt) {
					 * throw new InvalidDataException(
					 * "Amount Payable now cannot be more than the Claim Eligibility Amount."
					 * ); }
					 */
				}
			}
			/*
			 * else { flagClmRefDtl=""; payAmntNow=null;
			 * clmdtl.setTotalAmtPayNow(null); }
			 */
		}
		//System.out.println("AFTER LOOP :---------------------");
		String clmStatus = null;
		String comments = null;
		String forwardedToUser = null;
		String thiskey = null;
		int outOfLimit = 0;
		double clmEligibleAmnt = 0.0;
		java.util.Date currentDate = new java.util.Date();
		Vector firstinstllmntclaims = processor.getClaimProcessingDetailsMod(
				ClaimConstants.FIRST_INSTALLMENT, stDt, endDt);
		/*
		 * for(int i=0; i<firstinstllmntclaims.size(); i++) { ClaimDetail clmDtl
		 * = (ClaimDetail)firstinstllmntclaims.elementAt(i); if(clmDtl == null)
		 * { continue; } String memId = (String)clmDtl.getMliId();
		 * Log.log(Log.INFO
		 * ,"ClaimAction","displayClaimApproval()","******************************"
		 * ); Log.log(Log.INFO,"ClaimAction","displayClaimApproval()","memId :"
		 * + memId); String claimrefnumber = (String)clmDtl.getClaimRefNum();
		 * Log
		 * .log(Log.INFO,"ClaimAction","displayClaimApproval()","claimrefnumber :"
		 * + claimrefnumber); String claimStatus =
		 * (String)clmDtl.getClmStatus();
		 * Log.log(Log.INFO,"ClaimAction","displayClaimApproval()","clmStatus :"
		 * + claimStatus); String commnts = (String)clmDtl.getComments(); String
		 * forwardedToUsr = (String)clmDtl.getForwaredToUser();
		 * Log.log(Log.INFO,
		 * "ClaimAction","displayClaimApproval()","forwardedToUser :" +
		 * forwardedToUsr);
		 * Log.log(Log.INFO,"ClaimAction","displayClaimApproval()"
		 * ,"******************************"); }
		 */
		// Vector forwardedFirstClms = new Vector();
		if (firstinstllmntclaims != null) {
			//System.out.println("Inside First Claim .....!");
			for (int i = 0; i < firstinstllmntclaims.size(); i++) {
				ClaimDetail clmDtl = (ClaimDetail) firstinstllmntclaims
						.elementAt(i);
				if (clmDtl == null) {
					continue;
				}
				Log.log(Log.INFO, "ClaimAction", "displayClaimApproval()",
						"##################");
				String memId = (String) clmDtl.getMliId();
				// //System.out.println("MemberId:"+memId);
				Log.log(Log.INFO, "ClaimAction", "displayClaimApproval()",
						"memId :" + memId);
				String claimrefnumber = (String) clmDtl.getClaimRefNum();
				// //System.out.println("claimrefnumber:"+claimrefnumber);
				Log.log(Log.INFO, "ClaimAction", "displayClaimApproval()",
						"claimrefnumber :" + claimrefnumber);
				clmStatus = (String) clmDtl.getClmStatus();
				// //System.out.println("clmStatus:"+clmStatus);
				Log.log(Log.INFO, "ClaimAction", "displayClaimApproval()",
						"clmStatus :" + clmStatus);
				comments = (String) clmDtl.getComments();
				// //System.out.println("comments:"+comments);
				forwardedToUser = (String) clmDtl.getForwaredToUser();
				// //System.out.println("forwardedToUser:"+forwardedToUser);
				Log.log(Log.INFO, "ClaimAction", "displayClaimApproval()",
						"forwardedToUser :" + forwardedToUser);
				Log.log(Log.INFO, "ClaimAction", "displayClaimApproval()",
						"###################");
				clmEligibleAmnt = clmDtl.getEligibleClaimAmt();
				// //System.out.println("Line number 5913 clmEligibleAmnt jai$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ :"
				// + clmEligibleAmnt);
				// //System.out.println("maxClmApprvdAmnt :" + maxClmApprvdAmnt);

				if (((fromDate != null) && (!fromDate.equals("")))
						&& ((toDate != null) && (!toDate.equals("")))) {
					if (((fromDate.compareTo(currentDate)) <= 0)
							&& ((currentDate.compareTo(toDate)) <= 0)) {
						if (clmEligibleAmnt > maxClmApprvdAmnt) {
							// //System.out.println("The clmEligibleAmnt is greater than the maxClmApprvdAmnt");
							outOfLimit++;
							firstinstllmntclaims.remove(i);
							i--;
							continue;
						}
					}
				}
				if (((fromDate != null) && (!fromDate.equals("")))
						&& (toDate == null)) {
					if ((fromDate.compareTo(currentDate)) <= 0) {
						if (clmEligibleAmnt > maxClmApprvdAmnt) {
							outOfLimit++;
							firstinstllmntclaims.remove(i);
							i--;
							continue;
						}
					}
				}

				if (clmStatus != null) {
					if ((clmStatus.equals(ClaimConstants.CLM_FORWARD_STATUS))
							|| (clmStatus
									.equals(ClaimConstants.CLM_HOLD_STATUS))) {
						thiskey = ClaimConstants.FIRST_INSTALLMENT
								+ ClaimConstants.CLM_DELIMITER1 + memId
								+ ClaimConstants.CLM_DELIMITER1
								+ claimrefnumber;
						claimForm.setDecision(thiskey, clmStatus);
						claimForm.setRemarks(thiskey, comments);
						Log.log(Log.INFO, "ClaimAction",
								"displayClaimApproval()", "loggedUsr :"
										+ loggedUsr);
						if ((forwardedToUser != null)
								&& (!forwardedToUser
										.equalsIgnoreCase(loggedUsr))) {
							// forwardedFirstClms.addElement(clmDtl);
							firstinstllmntclaims.remove(i);
							--i;
						}
						if ((forwardedToUser != null)
								&& (forwardedToUser.equalsIgnoreCase(loggedUsr))) {
							// forwardedFirstClms.addElement(clmDtl);
							Log.log(Log.INFO, "ClaimAction",
									"displayClaimApproval()",
									"*******************");
							Log.log(Log.INFO, "ClaimAction",
									"displayClaimApproval()", "thiskey :"
											+ thiskey);
							Log.log(Log.INFO, "ClaimAction",
									"displayClaimApproval()",
									"forwardedToUser :" + forwardedToUser);
							Log.log(Log.INFO, "ClaimAction",
									"displayClaimApproval()",
									"*******************");
							claimForm.setForwardedToIds(thiskey,
									forwardedToUser);
							if (userIds.contains(forwardedToUser)) {
								claimForm.setUserIds(userIds);
							}
						}
					}
				}
			}
		}

		// The vector contains objects of ClaimDetail for second claim
		Vector secinstllmntclaims = processor
				.getClaimProcessingDetails(ClaimConstants.SECOND_INSTALLMENT);
		// Vector forwardedSecClms = new Vector();
		if (secinstllmntclaims != null) {
			// //System.out.println("Inside second Installment:---->");
			for (int i = 0; i < secinstllmntclaims.size(); i++) {
				ClaimDetail clmDtl = (ClaimDetail) secinstllmntclaims
						.elementAt(i);
				if (clmDtl == null) {
					continue;
				}
				clmEligibleAmnt = clmDtl.getEligibleClaimAmt();
				if (((fromDate != null) && (!fromDate.equals("")))
						&& ((toDate != null) && (!toDate.equals("")))) {
					if (((fromDate.compareTo(currentDate)) <= 0)
							&& ((currentDate.compareTo(toDate)) <= 0)) {
						if (clmEligibleAmnt > maxClmApprvdAmnt) {
							outOfLimit++;
							secinstllmntclaims.remove(i);
							i--;
							continue;
						}
					}
				}
				if (((fromDate != null) && (!fromDate.equals("")))
						&& (toDate == null)) {
					if ((fromDate.compareTo(currentDate)) <= 0) {
						if (clmEligibleAmnt > maxClmApprvdAmnt) {
							outOfLimit++;
							secinstllmntclaims.remove(i);
							i--;
							continue;
						}
					}
				}
				String memId = (String) clmDtl.getMliId();
				String claimrefnumber = (String) clmDtl.getClaimRefNum();
				String cgclan = (String) clmDtl.getCGCLAN();
				clmStatus = (String) clmDtl.getClmStatus();
				comments = (String) clmDtl.getComments();
				forwardedToUser = (String) clmDtl.getForwaredToUser();
				Log.log(Log.INFO, "ClaimAction", "displayClaimApproval()",
						"forwardedToUser :" + forwardedToUser);
				if (clmStatus != null) {
					// //System.out.println("Inside clamStatus:---->");
					if ((clmStatus.equals(ClaimConstants.CLM_FORWARD_STATUS))
							|| (clmStatus
									.equals(ClaimConstants.CLM_HOLD_STATUS))) {
						thiskey = ClaimConstants.SECOND_INSTALLMENT
								+ ClaimConstants.CLM_DELIMITER1 + memId
								+ ClaimConstants.CLM_DELIMITER1
								+ claimrefnumber
								+ ClaimConstants.CLM_DELIMITER1 + cgclan;
						claimForm.setDecision(thiskey, clmStatus);
						claimForm.setRemarks(thiskey, comments);
						Log.log(Log.INFO, "ClaimAction",
								"displayClaimApproval()", "loggedUsr :"
										+ loggedUsr);
						if ((forwardedToUser != null)
								&& (!forwardedToUser
										.equalsIgnoreCase(loggedUsr))) {
							// forwardedSecClms.addElement(clmDtl);
							secinstllmntclaims.remove(i);
							--i;
						}
						if ((forwardedToUser != null)
								&& (forwardedToUser.equalsIgnoreCase(loggedUsr))) {
							// forwardedSecClms.addElement(clmDtl);
							claimForm.setForwardedToIds(thiskey,
									forwardedToUser);
						}
					}
				}
			}
		}

		if ((firstinstllmntclaims.size() == 0)
				&& (secinstllmntclaims.size() == 0)) {
			request.setAttribute("message",
					"There are no Claim Application(s) to process.");
			return mapping.findForward("success");
		}

		for (int i = 0; i < firstinstllmntclaims.size(); i++) {
			// //System.out.println("Inside firstinstllmntclaims:---->");
			ClaimDetail cd = (ClaimDetail) firstinstllmntclaims.elementAt(i);
			String crn = cd.getClaimRefNum();
			if (crn.equals(clmRefNum)) {
				cd = (ClaimDetail) firstinstllmntclaims.remove(i);
				cd.setTotalAmtPayNow(payAmntNow);
				cd.setComments(userRemarks);
				firstinstllmntclaims.addElement(cd);
			}
		}
		for (int i = 0; i < secinstllmntclaims.size(); i++) {
			// //System.out.println("Inside secinstllmntclaims---->");
			ClaimDetail cd = (ClaimDetail) secinstllmntclaims.elementAt(i);
			String crn = cd.getClaimRefNum();
			// //System.out.println("Line number6072 crn:"+crn);
			if (crn.equals(clmRefNum)) {
				cd = (ClaimDetail) secinstllmntclaims.remove(i);
				cd.setTotalAmtPayNow(payAmntNow);
				// //System.out.println("Line number 6077 payAmntNow:"+payAmntNow);
				secinstllmntclaims.addElement(cd);
			}
		}
		clmdtl = null;
		clmRefNum = null;
		payAmntNow = null;
		// //System.out.println("before set limit....!");
		claimForm.setLimit(outOfLimit);
		// //System.out.println("Line number 6085 outOfLimit:"+outOfLimit);
		claimForm.setFirstInstallmentClaims(firstinstllmntclaims);
		// //System.out.println("Number of applications for first claim installments :"
		// + firstinstllmntclaims.size());
		claimForm.setFirstCounter(firstinstllmntclaims.size());
		claimForm.setSecondInstallmentClaims(secinstllmntclaims);
		// //System.out.println("Number of applications for second claim installments :"
		// + secinstllmntclaims.size());
		claimForm.setSecondCounter(secinstllmntclaims.size());
		claimForm.setClmRefDtlSet(ClaimConstants.DISBRSMNT_NO_FLAG);

		/*
		 * Clearing up the Collection Objects
		 */
		// firstinstllmntclaims.clear();
		firstinstllmntclaims = null;
		// secinstllmntclaims.clear();
		secinstllmntclaims = null;
		Log.log(Log.INFO, "ClaimAction", "displayClaimApproval()", "Exited");

		return mapping.findForward("displayClaimsApprovalPage");
	}

	// FOR OTS AFTER RECOVERY ADDED ON 20-APR-2011

	public ActionForward getRecoveryAfterOTS(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(Log.INFO, "ClaimAction", "getRecoveryrots", "Entered");
		// //System.out.println("the inn getRecoveryAfterOTS....!");
		ClaimActionForm clmActionform = (ClaimActionForm) form;
		clmActionform.setCp_ots_enterMember("");
		clmActionform.setCp_ots_enterCgpan("");
		clmActionform.setCp_ots_appRefNo("");

		Log.log(Log.INFO, "ClaimAction", "getRecoveryrots", "Exited");
		// //System.out.println(" getRecoveryAfterOTS....!");
		return mapping.findForward("success");
	}

	// ---------------------------------------------------------------------------------

	public ActionForward getRecoveryAfterOTSDetail(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(Log.INFO, "ClaimAction", "getRecoveryAfterOTSDetail", "Entered");

		// //System.out.println("inside the method getRecoveryAfterOTSDetail");
		ClaimActionForm clmActionform = (ClaimActionForm) form;

		String memberId = "";
		String cgpan = "";
		String clmRefNo = "";
		ActionForm dynaForm = (ActionForm) form;
		Connection connection = DBConnection.getConnection();
		try {

			memberId = clmActionform.getCp_ots_enterMember();
			cgpan = clmActionform.getCp_ots_enterCgpan();
			clmRefNo = clmActionform.getCp_ots_appRefNo();

			// //System.out.println("The member Id is :-->"+memberId);

			// //System.out.println("The Cgpan Is :--->"+cgpan);
			// memberId=request.getParameter("cp_ots_enterMember").trim();
			// cgpan=request.getParameter("cp_ots_enterCgpan").toUpperCase().trim();
			// clmRefNo=request.getParameter("cp_ots_appRefNo").toUpperCase().trim();

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!memberId.equals("")) {
			try {

				this.validMemberId(memberId);
				String mainQuery = null;

				if (!cgpan.equals("")) {
					this.validCGPAN(cgpan); // cheack enterd CGPAN is valid or
											// not.
					this.cgpanmemberAsso(memberId, cgpan); // check Entred CGPAN
															// and Member ID are
															// Associated or not
					String retrclmRef = getClmRefByCgpan(memberId, cgpan);

					// //System.out.println("The Claim Ref Number is :-->"+retrclmRef);

					// mainQuery="select clm_mli_name,c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id member_id,c.clm_ref_no,ssi_unit_name, pmr_chief_gender gender,app_mse_type ,npa_effective_dt npa_dt,\n"
					// +
					// " a.cgpan, a.app_approved_date_time approved_dt,decode(a.app_reapprove_amount,null,a.app_approved_amount,a.app_reapprove_amount) approved_amt,\n"
					// +
					// "trunc(clm_approved_dt)\n" +
					// " from claim_detail_temp c,ssi_detail s,npa_detail_temp n,application_detail a ,promoter_detail p,claim_application_amount CA\n"
					// +
					// "  where c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id='"+memberId+"' \n"
					// +
					// " and s.bid=c.BID  and n.bid=c.bid \n" +
					// " AND A.cgpan=ca.cgpan\n" +
					// " and a.ssi_reference_number=s.ssi_reference_number\n" +
					// " and s.ssi_reference_number=p.ssi_reference_number \n" +
					// "and app_status!='RE' \n" +
					// " and (c.clm_ref_no='"+retrclmRef+"' or c.clm_ref_no in(select clm_ref_no from claim_application_amount_temp@cginter where cgpan='"+cgpan+"'))\n"
					// +
					// "  union\n" +
					// "  select clm_mli_name,c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id member_id,c.clm_ref_no,ssi_unit_name, pmr_chief_gender gender,app_mse_type ,npa_effective_dt npa_dt,a.cgpan, \n"
					// +
					// " a.app_approved_date_time approved_dt,decode(a.app_reapprove_amount,null,a.app_approved_amount,a.app_reapprove_amount) approved_amt\n"
					// +
					// ",trunc(clm_approved_dt)\n" +
					// " from claim_detail c,ssi_detail s,npa_detail n,application_detail a,promoter_detail p,claim_application_amount CA\n"
					// +
					// " where c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id='"+memberId+"' \n"
					// +
					// "   and s.bid=c.BID  and n.bid=c.bid \n" +
					// " and a.ssi_reference_number=s.ssi_reference_number\n" +
					// "  and s.ssi_reference_number=p.ssi_reference_number \n"
					// +
					// "  AND A.cgpan=ca.cgpan\n" +
					// "and app_status!='RE'\n" +
					// " and (c.clm_ref_no='"+retrclmRef+"' or c.clm_ref_no in(select clm_ref_no from claim_application_amount@cginter where cgpan='"+cgpan+"'))";

					mainQuery = "select clm_mli_name,c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id member_id,c.clm_ref_no,ssi_unit_name, pmr_chief_gender  \n"
							+ " gender,app_mse_type ,npa_effective_dt npa_dt,a.cgpan, a.app_approved_date_time approved_dt,\n"
							+ " decode(a.app_reapprove_amount,null,a.app_approved_amount,a.app_reapprove_amount)\n"
							+ " approved_amt ,trunc(clm_approved_dt)\n"
							+ " from claim_detail c,ssi_detail s,npa_detail n,application_detail a,promoter_detail p,claim_application_amount CA \n"
							+ " where c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id='"
							+ memberId
							+ "'                                                        \n"
							+ " and s.bid=c.BID  and n.bid=c.bid \n"
							+ " and a.ssi_reference_number=s.ssi_reference_number \n"
							+ " and s.ssi_reference_number=p.ssi_reference_number  \n"
							+ " AND A.cgpan=ca.cgpan \n"
							+ " and app_status!='RE' \n"
							+ " and c.clm_ref_no='"
							+ retrclmRef
							+ "' \n"
							+ " union\n"
							+ " select clm_mli_name,c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id member_id,c.clm_ref_no,ssi_unit_name, pmr_chief_gender  \n"
							+ " gender,app_mse_type ,npa_effective_dt npa_dt,a.cgpan, a.app_approved_date_time approved_dt,\n"
							+ " decode(a.app_reapprove_amount,null,a.app_approved_amount,a.app_reapprove_amount)\n"
							+ " approved_amt ,trunc(clm_approved_dt)\n"
							+ " from claim_detail c,ssi_detail s,npa_detail_temp n,application_detail a,promoter_detail p,claim_application_amount CA \n"
							+ " where c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id='"
							+ memberId
							+ "'                                                        \n"
							+ " and s.bid=c.BID  and n.bid=c.bid \n"
							+ " and a.ssi_reference_number=s.ssi_reference_number \n"
							+ " and s.ssi_reference_number=p.ssi_reference_number  \n"
							+ " AND A.cgpan=ca.cgpan \n"
							+ " and app_status!='RE' \n"
							+ " and c.clm_ref_no='" + retrclmRef + "'";

				}
				if (!clmRefNo.equals("")) {
					this.validClmRefNo(clmRefNo, memberId);
					// clmRefmembAsso()

					mainQuery = "select clm_mli_name,c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id member_id,c.clm_ref_no,ssi_unit_name, pmr_chief_gender  \n"
							+ " gender,app_mse_type ,npa_effective_dt npa_dt,a.cgpan, a.app_approved_date_time approved_dt,\n"
							+ " decode(a.app_reapprove_amount,null,a.app_approved_amount,a.app_reapprove_amount)\n"
							+ " approved_amt ,trunc(clm_approved_dt)\n"
							+ " from claim_detail c,ssi_detail s,npa_detail n,application_detail a,promoter_detail p,claim_application_amount CA \n"
							+ " where c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id='"
							+ memberId
							+ "' \n"
							+ " and s.bid=c.BID  and n.bid=c.bid \n"
							+ " and a.ssi_reference_number=s.ssi_reference_number \n"
							+ " and s.ssi_reference_number=p.ssi_reference_number  \n"
							+ " AND A.cgpan=ca.cgpan \n"
							+ " and app_status!='RE' \n"
							+ " and c.clm_ref_no='"
							+ clmRefNo
							+ "' \n"
							+ " union\n"
							+ " select clm_mli_name,c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id member_id,c.clm_ref_no,ssi_unit_name, pmr_chief_gender  \n"
							+ " gender,app_mse_type ,npa_effective_dt npa_dt,a.cgpan, a.app_approved_date_time approved_dt,\n"
							+ " decode(a.app_reapprove_amount,null,a.app_approved_amount,a.app_reapprove_amount)\n"
							+ " approved_amt ,trunc(clm_approved_dt)\n"
							+ " from claim_detail c,ssi_detail s,npa_detail_temp n,application_detail a,promoter_detail p,claim_application_amount CA \n"
							+ " where c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id='"
							+ memberId
							+ "'\n"
							+ " and s.bid=c.BID  and n.bid=c.bid \n"
							+ " and a.ssi_reference_number=s.ssi_reference_number \n"
							+ " and s.ssi_reference_number=p.ssi_reference_number  \n"
							+ " AND A.cgpan=ca.cgpan \n"
							+ " and app_status!='RE' \n"
							+ " and c.clm_ref_no='" + clmRefNo + "'";
				}
				Statement str = connection.createStatement(
						ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				ResultSet rsForData = str.executeQuery(mainQuery);
				ArrayList claimOtsDataArr = new ArrayList();
				if (!rsForData.next()) {
					throw new NoMemberFoundException(
							"No data Found for Entred Details ");
				}
				rsForData.beforeFirst();
				ClaimActionForm claimActionForm = new ClaimActionForm();
				// String flagValu1="N";

				while (rsForData.next()) {
					ClaimActionForm claimActionFormmain = new ClaimActionForm();

					claimActionForm.setCp_ots_mliName(rsForData.getString(1));
					claimActionForm.setCp_ots_enterMember(rsForData
							.getString(2));
					claimActionForm.setCp_ots_appRefNo(rsForData.getString(3));
					claimActionForm.setCp_ots_unitName(rsForData.getString(4));
					claimActionForm.setCp_ots_gender(rsForData.getString(5));
					String flagValu = rsForData.getString(6);
					if (flagValu == null) {
						flagValu = "N";

					}
					claimActionForm.setCp_ots_UnitAssitByMSE(flagValu);
					// //System.out.println("The Flag Value is :-->"+flagValu);
					claimActionForm.setCp_ots_npaDate(rsForData.getDate(7));
					// claimActionForm.setCp_ots_npaDateString(rsForData.getString(7));

					claimActionFormmain.setCp_ots_enterCgpan(rsForData
							.getString(8));

					claimActionForm.setCp_ots_firstInstallDate(rsForData
							.getDate(9));
					claimActionFormmain.setCp_ots_totAmount(rsForData
							.getDouble(10));
					claimActionForm.setCp_ots_clmappDate(rsForData.getDate(11)); // clm
																					// approve
																					// date
					claimOtsDataArr.add(claimActionFormmain);
				}
				claimActionForm.setClaimdeatilforOts(claimOtsDataArr);
				// jagdish
				ClaimActionForm clmactionFm = FunctionExcelXYZ(claimActionForm,
						cgpan, request);
				// BeanUtils.copyProperties(dynaForm, claimActionForm);
				BeanUtils.copyProperties(dynaForm, clmactionFm);
				HttpSession session = request.getSession(true);
				session.setAttribute("claimActionFormdetail", claimActionForm);

			} // try close
			finally {
				DBConnection.freeConnection(connection);
				// //System.out.println("Inside finally");
			}

		} // member id not equals to null close

		Log.log(Log.INFO, "ClaimAction", "getRecoveryAfterOTSDetail", "Exited");
		// //System.out.println("the value for dispatch is   ::::--> detailsuccess");
		return mapping.findForward("detailsuccess");
	}

	// ----------------------------------------------------------------------------------------------------------------------

	void validMemberId(String enteredMemberId) throws Exception {
		Connection connection = DBConnection.getConnection();
		try {

			Statement str = connection
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
			String one = enteredMemberId.substring(0, 4);
			String two = enteredMemberId.substring(4, 8);
			String three = enteredMemberId.substring(8, 12);

			String MemIdavailbel = "select c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id member_id from member_info c \n"
					+ "where  c.mem_bnk_id='"
					+ one
					+ "' and\n"
					+ "c.mem_zne_id='"
					+ two
					+ "' and\n"
					+ "c.mem_brn_id='"
					+ three + "'";
			ResultSet rsforavailmemid = str.executeQuery(MemIdavailbel);
			if (!rsforavailmemid.next()) {
				throw new NoMemberFoundException(" Member Id  Not Exsist : ");
			}

		} finally {
			DBConnection.freeConnection(connection);
			//System.out.println("Inside finally");
		}
	}

	// ----------------------------------------------------------------------------------------------------------------------

	void validCGPAN(String cgpan) throws Exception {

		Connection connection = DBConnection.getConnection();
		try {

			Statement str = connection
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
			String cgQury = "select c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id member_id from application_detail c where cgpan='"
					+ cgpan + "'";
			ResultSet rsforvalid = str.executeQuery(cgQury);
			String memberid = "";
			if (!rsforvalid.next()) {
				throw new NoMemberFoundException(" CGPAN  Not Exsist.  ");
			}
		} finally {
			DBConnection.freeConnection(connection);

		}
	}

	void cgpanmemberAsso(String memberId, String cgpan) throws Exception {

		Connection connection = DBConnection.getConnection();
		try {

			Statement str = connection
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
			String cgQury = "select c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id member_id from application_detail c where cgpan='"
					+ cgpan + "'";
			ResultSet rsforvalid = str.executeQuery(cgQury);
			String memberid = "";
			if (!rsforvalid.next()) {
				throw new NoMemberFoundException(" CGPAN  Not Exsist.  ");
			}
			rsforvalid.beforeFirst();
			while (rsforvalid.next()) {
				memberid = rsforvalid.getString(1);
			}
			if (!memberid.equals(memberId)) {
				throw new NoMemberFoundException(
						" Enterd Member Id and CGPAN  Not Associated. ");
			}

		} finally {
			DBConnection.freeConnection(connection);

		}
	}

	String getClmRefByCgpan(String memberId, String cgpan) throws Exception {
		String clmrefretrived = null;
		Connection connection = DBConnection.getConnection();
		try {

			Statement str = connection
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
			String qury1 = "select clm_Ref_no from claim_detail\n"
					+ "where bid in\n"
					+ "(select s.bid from ssi_detail s,application_detail a\n"
					+ "where a.ssi_Reference_number = s.ssi_Reference_number\n"
					+ "and a.cgpan = '" + cgpan + "'\n"
					+ "and  a.mem_bnk_id||a.mem_zne_id||a.mem_brn_id='"
					+ memberId + "')"; // checking claim application Lodge or
										// not

			ResultSet rs1 = str.executeQuery(qury1);
			if (!rs1.next()) {
				throw new NoMemberFoundException(
						"Claim Application Not Approved  ");
			}

			rs1.beforeFirst();
			while (rs1.next()) {

				clmrefretrived = rs1.getString(1);
				// //System.out.println("the Value Retrive is :--->"+claimreff);
			}

		} finally {
			DBConnection.freeConnection(connection);

		}
		// //System.out.println("the Clm Ref Number Retrive On basis of CGPAN number is --->"+clmrefretrived);
		return clmrefretrived;
	}

	public void validClmRefNo(String clmrefNo, String memberId)
			throws Exception {
		Connection connection = DBConnection.getConnection();
		try {

			Statement str = connection
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
			String cgQury = "select c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id member_id from claim_detail c where clm_ref_no='"
					+ clmrefNo + "'";
			ResultSet rsforvalid = str.executeQuery(cgQury);
			String memberid = "";
			if (!rsforvalid.next()) {
				throw new NoMemberFoundException(
						"Claim Ref Number provided is not Approved.  ");
			}
			rsforvalid.beforeFirst();
			while (rsforvalid.next()) {
				memberid = rsforvalid.getString(1);
			}
			if (!memberid.equals(memberId)) {
				throw new NoMemberFoundException(
						"Claim Ref Number and Member ID not Associated.  ");
			}

		} finally {
			DBConnection.freeConnection(connection);

		}
	}

	// ----------------------------------------------------------------------------------------------------------------------

	public ClaimActionForm FunctionExcelXYZ(ClaimActionForm claimActionform1,
			String cgpan, HttpServletRequest request) throws Exception {

		double totalforcal = 0.0;
		double total = 0.0;
		double npaTotal = 0.0;
		double recovryTotal = 0.0;
		String NPACgpan = "";
		String recoveryCgpan = "";
		String cgpanRetrive = "";
		double cgpanGaurnteeAmt = 0.0;
		double guarnteeAmt = 0.0;
		double recoveryAmt = 0.0;
		double npaAmount = 0.0;
		double totNetOutstandingAmount = 0.0;
		double netOutstandingAmt = 0.0;
		ClaimActionForm claimActionformforaddobject = new ClaimActionForm();
		ArrayList claimActionObjList = new ArrayList();
		ArrayList claimActionforstore = new ArrayList();
		claimActionObjList = claimActionform1.getClaimdeatilforOts();
		for (int i = 0; i < claimActionObjList.size(); i++) {
			ClaimActionForm claimOBJ1 = (ClaimActionForm) claimActionObjList
					.get(i);
			totalforcal = totalforcal + claimOBJ1.getCp_ots_totAmount();
		}
		total = totalforcal;
		claimActionform1.setCp_ots_Total(total);
		String clmrefNo = claimActionform1.getCp_ots_appRefNo();
		Connection connection = DBConnection.getConnection();
		try {
			Statement str = connection
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
			String queryNPAOutStanfAMt1 = "select clm_ref_no,cgpan,ctd_npa_outstanding_amt from claim_tc_detail where clm_ref_no='"
					+ clmrefNo
					+ "'\n"
					+ "union\n"
					+ "select clm_ref_no,cgpan,cwd_npa_outstanding_amt  from claim_wc_detail where clm_ref_no='"
					+ clmrefNo + "'";
			ResultSet rs1 = str.executeQuery(queryNPAOutStanfAMt1);
			while (rs1.next()) {
				NPACgpan = rs1.getString(2);
				npaTotal = npaTotal + rs1.getDouble(3);
			}
			claimActionform1.setCp_ots_npatotal(npaTotal);
			String qryrecbeforefirinstallrealse = "select cgpan,crd_tc_principal_amt from claim_recovery_detail where clm_ref_no='"
					+ clmrefNo + "'";
			ResultSet rsforRecov = str
					.executeQuery(qryrecbeforefirinstallrealse);
			while (rsforRecov.next()) {
				recoveryCgpan = rsforRecov.getString(1);
				recovryTotal = recovryTotal + rsforRecov.getDouble(2);
			} // method over
			claimActionform1.setCp_ots_cp_ots_totRecAMt(recovryTotal);
			double fornetoutst = Math.min(total, npaTotal);
			totNetOutstandingAmount = fornetoutst - recovryTotal;
			int arrysixe = claimActionObjList.size();
			for (int i = 0; i < arrysixe; i++) { // loop start
				ClaimActionForm claimActionform = new ClaimActionForm();
				ClaimActionForm claimOBJ = (ClaimActionForm) claimActionObjList
						.get(i);
				cgpanRetrive = claimOBJ.getCp_ots_enterCgpan();
				claimActionform.setCp_ots_enterCgpan(cgpanRetrive);
				cgpanGaurnteeAmt = claimOBJ.getCp_ots_totAmount();
				claimActionform.setCp_ots_cgpanGaurnteeAmt(cgpanGaurnteeAmt);
				String queryNPAOutStanfAMt = "select clm_ref_no,cgpan,ctd_npa_outstanding_amt from claim_tc_detail where cgpan='"
						+ cgpanRetrive
						+ "'\n"
						+ "union\n"
						+ "select clm_ref_no,cgpan,cwd_npa_outstanding_amt  from claim_wc_detail where cgpan='"
						+ cgpanRetrive + "'";
				ResultSet rs2 = str.executeQuery(queryNPAOutStanfAMt);
				while (rs2.next()) {
					npaAmount = rs2.getDouble(3);
				}
				claimActionform.setCp_ots_npaAmount(npaAmount);
				String qryrecbeforefirinstallrealse1 = "select cgpan,crd_tc_principal_amt from claim_recovery_detail where cgpan='"
						+ cgpanRetrive + "'";
				ResultSet rsforRecov1 = str
						.executeQuery(qryrecbeforefirinstallrealse1);
				rsforRecov1.beforeFirst();
				while (rsforRecov1.next()) {
					String cgpanforchek = rsforRecov1.getString(1);
					recoveryAmt = rsforRecov1.getDouble(2);
				}
				claimActionform.setCp_ots_recoveryAmt(recoveryAmt);
				double minofA_B = Math.min(cgpanGaurnteeAmt, npaAmount);
				netOutstandingAmt = minofA_B - recoveryAmt;
				claimActionform.setCp_ots_netOutstanding(netOutstandingAmt);
				String queryforguarnteeStartDate = "select app_guar_start_date_time from application_detail where cgpan='"
						+ cgpanRetrive + "'";
				ResultSet rs = str.executeQuery(queryforguarnteeStartDate);
				java.util.Date guarsatDtae = null;
				while (!rs.next()) {
					throw new NoMemberFoundException(
							" Guarntee Start Date is Null. ");
				}
				rs.beforeFirst();
				while (rs.next()) {
					guarsatDtae = rs.getDate(1);
				}
				java.util.Date guranteeStartDate = guarsatDtae;
				Date date = new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				int month = calendar.get(Calendar.MONTH);
				int day = calendar.get(Calendar.DATE);
				int year = calendar.get(Calendar.YEAR);
				month = 01;
				day = 02;
				year = 2009;
				calendar.set(Calendar.MONTH, month);
				calendar.set(Calendar.DATE, day);
				calendar.set(Calendar.YEAR, year);
				Date condtionalDate = calendar.getTime();
				String retGender = claimActionform1.getCp_ots_gender();
				String Gender = "";
				if (retGender.equals("M")) {
					Gender = "male";
				} else if (retGender.equals("F")) {
					Gender = "female";
				}
				boolean MicroFlag = false;
				boolean NERflag = false;
				String schemQery = "select CLM_IS_MICRO_FLAG from claim_detail where clm_ref_no='"
						+ clmrefNo + "'";
				ResultSet scmRs = str.executeQuery(schemQery);
				String schmId = "";
				if (!scmRs.next()) {
					MicroFlag = false;
				}
				scmRs.beforeFirst();
				try {
					while (scmRs.next()) {
						schmId = scmRs.getString(1);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (schmId != null && schmId.equals("Y")) {
					MicroFlag = true;
				}
				claimActionform.setCp_ots_microfalg(MicroFlag);
				String stateQery = "SELECT SSI_STATE_NAME  FROM SSI_DETAIL WHERE BID IN(SELECT BID FROM CLAIM_DETAIL WHERE CLM_REF_NO= '"
						+ clmrefNo + "')";
				ResultSet stateRs = str.executeQuery(stateQery);
				String StateName = "";
				while (!stateRs.next()) {
					NERflag = false;
				}
				stateRs.beforeFirst();
				while (stateRs.next()) {
					StateName = stateRs.getString(1);
					// //System.out.println("retive State is :--->"+StateName);
				}

				String compStateQry = "SELECT SCR_STATE_NAME FROM STATE_GR_MAP WHERE SCR_STATE_NAME='"
						+ StateName + "'";
				ResultSet compStateRs = str.executeQuery(compStateQry);

				if (!compStateRs.next()) {
					NERflag = false;
				}
				compStateRs.beforeFirst();
				while (compStateRs.next()) {
					NERflag = true;
				}
				double TotalGuarnteeAmt = total;
				double NetOutsatndingAmt = totNetOutstandingAmount;

				double TotalLiableAmt = 0.0;
				double liableAmt = 0.0;
				Date guaranteeSanctionedYear = guranteeStartDate;
				double liablepercent = 0.0;

				if (guaranteeSanctionedYear.before(condtionalDate)) {

					if (MicroFlag == true) {

						if (TotalGuarnteeAmt <= 500000) {
							liableAmt = (netOutstandingAmt * 80) / 100;

							liableAmt = Math.round(liableAmt);

							claimActionform.setCp_ots_liableamount(liableAmt);

							claimActionform
									.setCp_ots_liabtext("CGTMSE's Liability (80% * Net Outstanding )");
							liablepercent = 80;
							claimActionform
									.setCp_ots_liablePercent(liablepercent);

						} else if (TotalGuarnteeAmt >= 500001
								&& TotalGuarnteeAmt <= 5000000) {
							if (NERflag = true) {

								liableAmt = (netOutstandingAmt * 80) / 100;

								liableAmt = Math.round(liableAmt);

								TotalLiableAmt = Math.round(TotalLiableAmt);

								claimActionform
										.setCp_ots_liableamount(liableAmt);

								claimActionform
										.setCp_ots_liabtext("CGTMSE's Liability (80% * Net Outstanding )");

								liablepercent = 80;
								claimActionform
										.setCp_ots_liablePercent(liablepercent);
							} else if (NERflag = false) {
								if (Gender == "male") {
									liableAmt = (netOutstandingAmt * 75) / 100;
									liableAmt = Math.round(liableAmt);
									TotalLiableAmt = Math.round(TotalLiableAmt);
									claimActionform
											.setCp_ots_liableamount(liableAmt);
									claimActionform
											.setCp_ots_liabtext("CGTMSE's Liability (75% * Net Outstanding )");
									liablepercent = 75;
									claimActionform
											.setCp_ots_liablePercent(liablepercent);
								} else if (Gender == "female") {
									liableAmt = (netOutstandingAmt * 80) / 100;
									liableAmt = Math.round(liableAmt);
									TotalLiableAmt = Math.round(TotalLiableAmt);
									claimActionform
											.setCp_ots_liableamount(liableAmt);
									claimActionform
											.setCp_ots_liabtext("CGTMSE's Liability (80% * Net Outstanding )");
									liablepercent = 80;
									claimActionform
											.setCp_ots_liablePercent(liablepercent);
								}
							}
						}
						if (TotalGuarnteeAmt >= 5000001
								&& TotalGuarnteeAmt <= 10000000) {
							if (NERflag = true) {
								double AmtForpercentTC = (netOutstandingAmt * 5000000)
										/ TotalGuarnteeAmt;
								double peroffirsthalfTC = AmtForpercentTC * 80 / 100;

								double amtforsecondpersentTC = NetOutsatndingAmt
										- AmtForpercentTC;

								double persecondhalfTC = amtforsecondpersentTC / 2;

								liableAmt = peroffirsthalfTC + persecondhalfTC;
								liableAmt = Math.round(liableAmt);
								claimActionform
										.setCp_ots_liableamount(liableAmt);
								claimActionform
										.setCp_ots_liabtext("CGTMSE's Liability (80% * Net Outstanding )");
								liablepercent = 80;
								claimActionform
										.setCp_ots_liablePercent(liablepercent);
							} else if (NERflag = false) {
								if (Gender == "male") {

									double AmtForpercentTC = (netOutstandingAmt * 5000000)
											/ TotalGuarnteeAmt;
									double peroffirsthalfTC = AmtForpercentTC * 75 / 100;

									double amtforsecondpersentTC = NetOutsatndingAmt
											- AmtForpercentTC;

									double persecondhalfTC = amtforsecondpersentTC / 2;

									liableAmt = peroffirsthalfTC
											+ persecondhalfTC;
									liableAmt = Math.round(liableAmt);

									claimActionform
											.setCp_ots_liableamount(liableAmt);
									claimActionform
											.setCp_ots_liabtext("CGTMSE's Liability (75% * Net Outstanding )");

									liablepercent = 75;
									claimActionform
											.setCp_ots_liablePercent(liablepercent);
								}

								if (Gender == "female") {

									double AmtForpercentTC = (netOutstandingAmt * 5000000)
											/ TotalGuarnteeAmt;
									double peroffirsthalfTC = AmtForpercentTC * 80 / 100;

									double amtforsecondpersentTC = NetOutsatndingAmt
											- AmtForpercentTC;

									double persecondhalfTC = amtforsecondpersentTC / 2;

									liableAmt = peroffirsthalfTC
											+ persecondhalfTC;
									liableAmt = Math.round(liableAmt);
									claimActionform
											.setCp_ots_liableamount(liableAmt);
									claimActionform
											.setCp_ots_liabtext("CGTMSE's Liability (80% * Net Outstanding )");

									liablepercent = 80;
									claimActionform
											.setCp_ots_liablePercent(liablepercent);
								}
							}
						}
					}

					else if (MicroFlag == false) {

						if (TotalGuarnteeAmt <= 5000000) {
							if (NERflag == true) {

								liableAmt = (netOutstandingAmt * 80) / 100;

								liableAmt = Math.round(liableAmt);

								TotalLiableAmt = Math.round(TotalLiableAmt);
								claimActionform
										.setCp_ots_liableamount(liableAmt);
								claimActionform
										.setCp_ots_liabtext("CGTMSE's Liability (80% * Net Outstanding )");

								liablepercent = 80;
								claimActionform
										.setCp_ots_liablePercent(liablepercent);
							} else if (NERflag == false) {

								if (Gender == "male") {

									liableAmt = (netOutstandingAmt * 75) / 100;
									liableAmt = Math.round(liableAmt);
									TotalLiableAmt = Math.round(TotalLiableAmt);
									claimActionform
											.setCp_ots_liableamount(liableAmt);
									claimActionform
											.setCp_ots_liabtext("CGTMSE's Liability (75% * Net Outstanding )");

									liablepercent = 75;

									claimActionform
											.setCp_ots_liablePercent(liablepercent);

								}
								if (Gender == "female") {
									liableAmt = (netOutstandingAmt * 80) / 100;
									liableAmt = Math.round(liableAmt);
									TotalLiableAmt = Math.round(TotalLiableAmt);
									claimActionform
											.setCp_ots_liableamount(liableAmt);
									claimActionform
											.setCp_ots_liabtext("CGTMSE's Liability (80% * Net Outstanding )");

									liablepercent = 80;
									claimActionform
											.setCp_ots_liablePercent(liablepercent);
								}
							}
						}

						if (TotalGuarnteeAmt >= 5000001
								&& TotalGuarnteeAmt <= 10000000) {
							if (NERflag == true) {
								double AmtForpercentTC = (netOutstandingAmt * 5000000)
										/ TotalGuarnteeAmt;
								double peroffirsthalfTC = AmtForpercentTC * 80 / 100;

								double amtforsecondpersentTC = NetOutsatndingAmt
										- AmtForpercentTC;

								double persecondhalfTC = amtforsecondpersentTC / 2;

								liableAmt = peroffirsthalfTC + persecondhalfTC;
								liableAmt = Math.round(liableAmt);
								claimActionform
										.setCp_ots_liableamount(liableAmt);
								claimActionform
										.setCp_ots_liabtext("CGTMSE's Liability (80% * Net Outstanding )");

								liablepercent = 80;
								claimActionform
										.setCp_ots_liablePercent(liablepercent);
							}
							if (NERflag == false) {
								if (Gender == "male") {

									double AmtForpercentTC = (netOutstandingAmt * 5000000)
											/ TotalGuarnteeAmt;
									double peroffirsthalfTC = AmtForpercentTC * 75 / 100;

									double amtforsecondpersentTC = NetOutsatndingAmt
											- AmtForpercentTC;

									double persecondhalfTC = amtforsecondpersentTC / 2;

									liableAmt = peroffirsthalfTC
											+ persecondhalfTC;
									liableAmt = Math.round(liableAmt);
									claimActionform
											.setCp_ots_liableamount(liableAmt);
									claimActionform
											.setCp_ots_liabtext("CGTMSE's Liability (75% * Net Outstanding )");

									liablepercent = 75;
									claimActionform
											.setCp_ots_liablePercent(liablepercent);
								}

								if (Gender == "female") {
									double AmtForpercentTC = (netOutstandingAmt * 5000000)
											/ TotalGuarnteeAmt;
									double peroffirsthalfTC = AmtForpercentTC * 80 / 100;

									double amtforsecondpersentTC = NetOutsatndingAmt
											- AmtForpercentTC;

									double persecondhalfTC = amtforsecondpersentTC / 2;

									liableAmt = peroffirsthalfTC
											+ persecondhalfTC;
									liableAmt = Math.round(liableAmt);
									claimActionform
											.setCp_ots_liableamount(liableAmt);
									claimActionform
											.setCp_ots_liabtext("CGTMSE's Liability (80% * Net Outstanding )");

									liablepercent = 80;
									claimActionform
											.setCp_ots_liablePercent(liablepercent);
								}
							}
						}
					}
				}

				if (guaranteeSanctionedYear.after(condtionalDate)) {

					if (MicroFlag == true) {
						if (TotalGuarnteeAmt <= 500000) {
							liableAmt = (netOutstandingAmt * 85) / 100;
							liableAmt = Math.round(liableAmt);
							TotalLiableAmt = Math.round(TotalLiableAmt);
							claimActionform.setCp_ots_liableamount(liableAmt);
							claimActionform
									.setCp_ots_liabtext("CGTMSE's Liability (85% * Net Outstanding )");

							liablepercent = 85;
							claimActionform
									.setCp_ots_liablePercent(liablepercent);
						} else if (TotalGuarnteeAmt >= 500001
								&& TotalGuarnteeAmt <= 5000000) {
							if (NERflag == true) {
								liableAmt = (netOutstandingAmt * 80) / 100;
								liableAmt = Math.round(liableAmt);
								TotalLiableAmt = Math.round(TotalLiableAmt);
								claimActionform
										.setCp_ots_liableamount(liableAmt);
								claimActionform
										.setCp_ots_liabtext("CGTMSE's Liability (80% * Net Outstanding )");

								liablepercent = 80;
								claimActionform
										.setCp_ots_liablePercent(liablepercent);
							}
							if (NERflag == false) {
								if (Gender == "male") {
									liableAmt = (netOutstandingAmt * 75) / 100;

									liableAmt = Math.round(liableAmt);

									TotalLiableAmt = Math.round(TotalLiableAmt);
									claimActionform
											.setCp_ots_liableamount(liableAmt);

									claimActionform
											.setCp_ots_liabtext("CGTMSE's Liability (75% * Net Outstanding )");

									liablepercent = 75;
									claimActionform
											.setCp_ots_liablePercent(liablepercent);
								} else if (Gender == "female") {
									liableAmt = (netOutstandingAmt * 80) / 100;
									liableAmt = Math.round(liableAmt);
									TotalLiableAmt = Math.round(TotalLiableAmt);
									claimActionform
											.setCp_ots_liableamount(liableAmt);
									claimActionform
											.setCp_ots_liabtext("CGTMSE's Liability (80% * Net Outstanding )");
									liablepercent = 80;
									claimActionform
											.setCp_ots_liablePercent(liablepercent);
								}
							}
						} else if (TotalGuarnteeAmt >= 5000001
								&& TotalGuarnteeAmt <= 10000000) {
							if (NERflag == true) {
								double AmtForpercentTC = (netOutstandingAmt * 5000000)
										/ TotalGuarnteeAmt;
								double peroffirsthalfTC = AmtForpercentTC * 80 / 100;

								double amtforsecondpersentTC = NetOutsatndingAmt
										- AmtForpercentTC;

								double persecondhalfTC = amtforsecondpersentTC / 2;

								liableAmt = peroffirsthalfTC + persecondhalfTC;
								liableAmt = Math.round(liableAmt);
								claimActionform
										.setCp_ots_liableamount(liableAmt);
								claimActionform
										.setCp_ots_liabtext("CGTMSE's Liability (80% * Net Outstanding )");

								liablepercent = 80;
								claimActionform
										.setCp_ots_liablePercent(liablepercent);
							} else if (NERflag == false) {
								if (Gender == "male") {

									double AmtForpercentTC = (netOutstandingAmt * 5000000)
											/ TotalGuarnteeAmt;
									double peroffirsthalfTC = AmtForpercentTC * 75 / 100;

									double amtforsecondpersentTC = NetOutsatndingAmt
											- AmtForpercentTC;

									double persecondhalfTC = amtforsecondpersentTC / 2;

									liableAmt = peroffirsthalfTC
											+ persecondhalfTC;
									liableAmt = Math.round(liableAmt);
									claimActionform
											.setCp_ots_liableamount(liableAmt);
									claimActionform
											.setCp_ots_liabtext("CGTMSE's Liability (75% * Net Outstanding )");

									liablepercent = 75;
									claimActionform
											.setCp_ots_liablePercent(liablepercent);
								}

								if (Gender == "female") {
									double AmtForpercentTC = (netOutstandingAmt * 5000000)
											/ TotalGuarnteeAmt;
									double peroffirsthalfTC = AmtForpercentTC * 80 / 100;

									double amtforsecondpersentTC = NetOutsatndingAmt
											- AmtForpercentTC;

									double persecondhalfTC = amtforsecondpersentTC / 2;

									liableAmt = peroffirsthalfTC
											+ persecondhalfTC;
									liableAmt = Math.round(liableAmt);
									claimActionform
											.setCp_ots_liableamount(liableAmt);
									claimActionform
											.setCp_ots_liabtext("CGTMSE's Liability (80% * Net Outstanding )");

									liablepercent = 80;
									claimActionform
											.setCp_ots_liablePercent(liablepercent);
								}
							}

						}
					} else if (MicroFlag == false) {
						if (TotalGuarnteeAmt <= 5000000) {
							if (NERflag == true) {
								liableAmt = (netOutstandingAmt * 80) / 100;
								liableAmt = Math.round(liableAmt);
								TotalLiableAmt = Math.round(TotalLiableAmt);
								claimActionform
										.setCp_ots_liableamount(liableAmt);
								claimActionform
										.setCp_ots_liabtext("CGTMSE's Liability (80% * Net Outstanding )");

								liablepercent = 80;
								claimActionform
										.setCp_ots_liablePercent(liablepercent);
							} else if (NERflag == false) {
								if (Gender == "male") {
									liableAmt = (netOutstandingAmt * 75) / 100;
									liableAmt = Math.round(liableAmt);
									TotalLiableAmt = Math.round(TotalLiableAmt);
									claimActionform
											.setCp_ots_liableamount(liableAmt);
									claimActionform
											.setCp_ots_liabtext("CGTMSE's Liability (75% * Net Outstanding )");

									liablepercent = 75;
									claimActionform
											.setCp_ots_liablePercent(liablepercent);
								}
								if (Gender == "female") {
									liableAmt = (netOutstandingAmt * 80) / 100;
									liableAmt = Math.round(liableAmt);
									TotalLiableAmt = Math.round(TotalLiableAmt);
									claimActionform
											.setCp_ots_liableamount(liableAmt);
									claimActionform
											.setCp_ots_liabtext("CGTMSE's Liability (80% * Net Outstanding )");

									liablepercent = 80;
									claimActionform
											.setCp_ots_liablePercent(liablepercent);
								}

							}

						}

						if (TotalGuarnteeAmt >= 5000001
								&& TotalGuarnteeAmt <= 10000000) {
							if (NERflag == true) {
								double AmtForpercentTC = (netOutstandingAmt * 5000000)
										/ TotalGuarnteeAmt;
								double peroffirsthalfTC = AmtForpercentTC * 80 / 100;

								double amtforsecondpersentTC = NetOutsatndingAmt
										- AmtForpercentTC;

								double persecondhalfTC = amtforsecondpersentTC / 2;

								liableAmt = peroffirsthalfTC + persecondhalfTC;
								liableAmt = Math.round(liableAmt);
								claimActionform
										.setCp_ots_liableamount(liableAmt);
								claimActionform
										.setCp_ots_liabtext("CGTMSE's Liability (80% * Net Outstanding )");

								liablepercent = 80;
								claimActionform
										.setCp_ots_liablePercent(liablepercent);

							}
							if (NERflag == false) {
								if (Gender == "male") {

									double AmtForpercentTC = (netOutstandingAmt * 5000000)
											/ TotalGuarnteeAmt;
									double peroffirsthalfTC = AmtForpercentTC * 75 / 100;

									double amtforsecondpersentTC = NetOutsatndingAmt
											- AmtForpercentTC;

									double persecondhalfTC = amtforsecondpersentTC / 2;

									liableAmt = peroffirsthalfTC
											+ persecondhalfTC;
									liableAmt = Math.round(liableAmt);
									claimActionform
											.setCp_ots_liableamount(liableAmt);
									claimActionform
											.setCp_ots_liabtext("CGTMSE's Liability (75% * Net Outstanding )");

									liablepercent = 75;

									claimActionform
											.setCp_ots_liablePercent(liablepercent);
								}
								if (Gender == "female") {
									double AmtForpercentTC = (netOutstandingAmt * 5000000)
											/ TotalGuarnteeAmt;
									double peroffirsthalfTC = AmtForpercentTC * 80 / 100;

									double amtforsecondpersentTC = NetOutsatndingAmt
											- AmtForpercentTC;

									double persecondhalfTC = amtforsecondpersentTC / 2;

									liableAmt = peroffirsthalfTC
											+ persecondhalfTC;
									liableAmt = Math.round(liableAmt);
									claimActionform
											.setCp_ots_liableamount(liableAmt);
									claimActionform
											.setCp_ots_liabtext("CGTMSE's Liability (80% * Net Outstanding )");

									liablepercent = 80;
									claimActionform
											.setCp_ots_liablePercent(liablepercent);
								}
							}
						}
					}
				}
				double fistIntallpaid = (liableAmt * 75) / 100;

				fistIntallpaid = Math.round(fistIntallpaid);
				claimActionform.setCp_ots_firstIntalpaidAmount(fistIntallpaid);

				String quRecovdata = "SELECT  CGPAN,CCRD_RECOVERY_AMOUNT,CCRD_EXPENSES_DEDUCTED,CCRD_RECOVERY_EXPENSES,CCRD_NET_RECOVERY FROM CLM_CGPAN_RECOVERY_DETAIL WHERE CGPAN='"
						+ cgpanRetrive + "'";
				ResultSet rsForRecData = str.executeQuery(quRecovdata);
				double totalRecAmtAfterclaimlodge = 0.0;
				double totDedctAmt = 0.0;
				double totNotDedctAmt = 0.0;
				String expeDeduct = "";
				double netRecovryAmtfordedct = 0.0;
				double netRecovryAmt = 0.0;

				while (rsForRecData.next()) {

					totalRecAmtAfterclaimlodge = totalRecAmtAfterclaimlodge
							+ rsForRecData.getDouble(2);
					expeDeduct = rsForRecData.getString(3);
					if (expeDeduct.equals("Y")) {
						totDedctAmt = totDedctAmt + rsForRecData.getDouble(4);
					} else if (expeDeduct.equals("N")) {
						totNotDedctAmt = totNotDedctAmt
								+ rsForRecData.getDouble(4);

					}
					netRecovryAmtfordedct = netRecovryAmtfordedct
							+ rsForRecData.getDouble(5);

				}
				totalRecAmtAfterclaimlodge = Math
						.round(totalRecAmtAfterclaimlodge);
				claimActionform
						.setCp_ots_totRecAftFirInst(totalRecAmtAfterclaimlodge);
				totDedctAmt = Math.round(totDedctAmt);
				claimActionform.setCp_ots_totDedtctAmt(totDedctAmt);
				totNotDedctAmt = Math.round(totNotDedctAmt);
				claimActionform.setCp_ots_totNotDedtctAmt(totNotDedctAmt);
				netRecovryAmt = netRecovryAmtfordedct - totDedctAmt;
				netRecovryAmt = Math.round(netRecovryAmt);
				claimActionform.setCp_ots_netRecovAmt(netRecovryAmt);
				double amountinDefoult = netOutstandingAmt - netRecovryAmt;
				amountinDefoult = Math.round(amountinDefoult);
				claimActionform.setCp_ots_netAmountInDefoault(amountinDefoult);
				double secintTot = totNetOutstandingAmount - netRecovryAmt;
				double secintAmt = netOutstandingAmt - netRecovryAmt;
				double totSecInstal = 0.0;
				double secintallmentAmt = 0.0;
				if (secintTot >= 5000001 && secintTot <= 10000000) {
					if (NERflag = true) {
						double secInstaTC = (secintAmt * 5000000) / secintTot;
						double firstHalfInsatTc = secInstaTC * 80 / 100;
						double forcalInsPerTc = secintAmt - secInstaTC;
						double secondhalfInsatTC = forcalInsPerTc / 2;

						totSecInstal = firstHalfInsatTc + secondhalfInsatTC;
						totSecInstal = Math.round(totSecInstal);

						claimActionform.setCp_ots_secIntalAMt(totSecInstal);

						String secIntalhalf = "Second installment payable by CGTMSE ("
								+ liablepercent;
						String secIntalString = secIntalhalf
								.concat("% Of final loss minus 1st insatl remitted by CGTMSE)");
						claimActionform
								.setCp_ots_seconInsatlText(secIntalString);

					} else if (NERflag = false) {
						if (Gender == "male") {

							double secInstaTC = (secintAmt * 5000000)
									/ secintTot;
							double firstHalfInsatTc = secInstaTC * 75 / 100;
							double forcalInsPerTc = secintAmt - secInstaTC;
							double secondhalfInsatTC = forcalInsPerTc / 2;

							totSecInstal = firstHalfInsatTc + secondhalfInsatTC;
							totSecInstal = Math.round(totSecInstal);
							claimActionform.setCp_ots_secIntalAMt(totSecInstal);

							String secIntalhalf = "Second installment payable by CGTMSE ("
									+ liablepercent;
							String secIntalString = secIntalhalf
									.concat("% Of final loss minus 1st insatl remitted by CGTMSE)");
							claimActionform
									.setCp_ots_seconInsatlText(secIntalString);

						}
						if (Gender == "female") {
							double secInstaTC = (secintAmt * 5000000)
									/ secintTot;
							double firstHalfInsatTc = secInstaTC * 80 / 100;
							double forcalInsPerTc = secintAmt - secInstaTC;
							double secondhalfInsatTC = forcalInsPerTc / 2;
							totSecInstal = firstHalfInsatTc + secondhalfInsatTC;
							totSecInstal = Math.round(totSecInstal);
							claimActionform.setCp_ots_secIntalAMt(totSecInstal);
							String secIntalhalf = "Second installment payable by CGTMSE ("
									+ liablepercent;
							String secIntalString = secIntalhalf
									.concat("% Of final loss minus 1st insatl remitted by CGTMSE)");
							claimActionform
									.setCp_ots_seconInsatlText(secIntalString);
						}
					}
				} else {
					secintallmentAmt = ((secintAmt * liablepercent) / 100)
							- fistIntallpaid;
					secintallmentAmt = Math.round(secintallmentAmt);
					claimActionform.setCp_ots_secIntalAMt(secintallmentAmt);
					String secIntalhalf = "Second installment payable by CGTMSE ("
							+ liablepercent;
					String secIntalString = secIntalhalf
							.concat(" % Of final loss minus 1st insatl remitted by CGTMSE)");
					claimActionform.setCp_ots_seconInsatlText(secIntalString);
				}
				double totpayout = recoveryAmt + secintallmentAmt + totDedctAmt;
				claimActionform.setCp_ots_finalPayout(totpayout);
				claimActionforstore.add(claimActionform);
				recoveryAmt = 0.0;
				totDedctAmt = 0.0;
				totNotDedctAmt = 0.0;
			}
			claimActionform1.setLoopobjtData(claimActionforstore);
			ArrayList x = claimActionform1.getLoopobjtData();
			double guarnteetotal = 0.0;
			double npatotalAmt = 0.0;
			double recovTotal = 0.0;
			double netOutstandingTotal = 0.0;
			double liavleAmtTotal = 0.0;
			double firstInstallmentPaidTotal = 0.0;
			double recafterfirstinstallTotal = 0.0;
			double totaDecdectAMt = 0.0;
			double totNotdedctAmt = 0.0;
			double totnetRecovAmt = 0.0;
			double netAmtindefoultTotal = 0.0;
			double secinstamentAmtTotal = 0.0;
			double finalPayoutAmtTotal = 0.0;

			for (int i = 0; i < x.size(); i++) {
				ClaimActionForm clm = (ClaimActionForm) x.get(i);

				guarnteetotal = guarnteetotal
						+ clm.getCp_ots_cgpanGaurnteeAmt();
				npatotalAmt = npatotalAmt + clm.getCp_ots_npaAmount();
				recovTotal = recovTotal + clm.getCp_ots_recoveryAmt();
				netOutstandingTotal = netOutstandingTotal
						+ clm.getCp_ots_netOutstanding();
				liavleAmtTotal = liavleAmtTotal + clm.getCp_ots_liableamount();
				firstInstallmentPaidTotal = firstInstallmentPaidTotal
						+ clm.getCp_ots_firstIntalpaidAmount();
				recafterfirstinstallTotal = recafterfirstinstallTotal
						+ clm.getCp_ots_totRecAftFirInst();
				totaDecdectAMt = totaDecdectAMt + clm.getCp_ots_totDedtctAmt();
				totNotdedctAmt = totNotdedctAmt
						+ clm.getCp_ots_totNotDedtctAmt();
				totnetRecovAmt = totnetRecovAmt + clm.getCp_ots_netRecovAmt();
				netAmtindefoultTotal = netAmtindefoultTotal
						+ clm.getCp_ots_netAmountInDefoault();
				secinstamentAmtTotal = secinstamentAmtTotal
						+ clm.getCp_ots_secIntalAMt();
				finalPayoutAmtTotal = finalPayoutAmtTotal
						+ clm.getCp_ots_finalPayout();
			}

			claimActionform1.setCp_ots_guarnteetotal(guarnteetotal);
			claimActionform1.setCp_ots_npatotalAmt(npatotalAmt);
			claimActionform1.setCp_ots_recovTotal(recovTotal);
			claimActionform1.setCp_ots_netOutstandingTotal(netOutstandingTotal);
			claimActionform1.setCp_ots_liavleAmtTotal(liavleAmtTotal);
			claimActionform1
					.setCp_ots_firstInstallmentPaidTotal(firstInstallmentPaidTotal);
			claimActionform1
					.setCp_ots_recafterfirstinstallTotal(recafterfirstinstallTotal);
			claimActionform1.setCp_ots_totaDecdectAMt(totaDecdectAMt);
			claimActionform1.setCp_ots_totNotdedctAmt(totNotdedctAmt);
			claimActionform1.setCp_ots_totnetRecovAmt(totnetRecovAmt);
			claimActionform1
					.setCp_ots_netAmtindefoultTotal(netAmtindefoultTotal);
			claimActionform1
					.setCp_ots_secinstamentAmtTotal(secinstamentAmtTotal);
			claimActionform1.setCp_ots_finalPayoutAmtTotal(finalPayoutAmtTotal);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.freeConnection(connection);

		}

		return claimActionform1;

	}

	// ----------------------------------------------------------------------------------------------------------------------

	public ActionForward farwordData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ClaimActionForm claimactionform = (ClaimActionForm) form;
		double val = 0.0d;
		ArrayList newValues = new ArrayList();
		String cgpanHidd[] = request.getParameterValues("cgpanHidd");
		String cgpanGaurnteeAmtHidd[] = request
				.getParameterValues("cgpanGaurnteeAmtHidd");
		String npaAmountHidd[] = request.getParameterValues("npaAmountHidd");
		String recoveryAmtHidd[] = request
				.getParameterValues("recoveryAmtHidd");
		String netOutstandAmt[] = request.getParameterValues("netOutstandAmt");
		String laibleAmount[] = request.getParameterValues("laibleAmount");
		String firstInstalPaidAmt[] = request
				.getParameterValues("firstInstalPaidAmt");
		String recAfterFirstInstall[] = request
				.getParameterValues("recAfterFirstInstall");
		String deductAmt[] = request.getParameterValues("deductAmt");
		String notDeductAmt[] = request.getParameterValues("notDeductAmt");
		String netRecovAmt[] = request.getParameterValues("netRecovAmt");
		String netAmtIndefaoult[] = request
				.getParameterValues("netAmtIndefaoult");
		String secInstallAmt[] = request.getParameterValues("secInstallAmt");
		String finalPayoutAmt[] = request.getParameterValues("finalPayoutAmt");
		double cgpanGaurnteeAmtHiddValTot = 0;
		double npaAmountHiddValTot = 0;
		double recoveryAmtHiddValTot = 0;
		double netOutstandAmtValTot = 0;
		double laibleAmountValTot = 0;
		double firstInstalPaidAmtValTot = 0;
		double recAfterFirstInstallValTot = 0;
		double deductAmtValTot = 0;
		double notDeductAmtValTot = 0;
		double netRecovAmtValTot = 0;
		double netAmtIndefaoultValTot = 0;
		double secInstallAmtValTot = 0;
		double finalPayoutAmtValTot = 0;
		try {
			for (int i = 0; i < netOutstandAmt.length; i++) {
				ClaimActionForm Obj = new ClaimActionForm();
				String cgpanHiddVal = cgpanHidd[i];
				Obj.setCp_ots_enterCgpan(cgpanHiddVal);
				double cgpanGaurnteeAmtHiddVal = Double
						.parseDouble(cgpanGaurnteeAmtHidd[i]);
				Obj.setCp_ots_cgpanGaurnteeAmt(cgpanGaurnteeAmtHiddVal);
				double npaAmountHiddVal = Double.parseDouble(npaAmountHidd[i]);
				Obj.setCp_ots_npaAmount(npaAmountHiddVal);
				double recoveryAmtHiddVal = Double
						.parseDouble(recoveryAmtHidd[i]);
				Obj.setCp_ots_recoveryAmt(recoveryAmtHiddVal);
				double netOutstandAmtVal = Double
						.parseDouble(netOutstandAmt[i]);
				Obj.setCp_ots_netOutstanding(netOutstandAmtVal);
				double laibleAmountVal = Double.parseDouble(laibleAmount[i]);
				Obj.setCp_ots_liableamount(laibleAmountVal);
				double firstInstalPaidAmtVal = Double
						.parseDouble(firstInstalPaidAmt[i]);
				Obj.setCp_ots_firstIntalpaidAmount(firstInstalPaidAmtVal);
				double recAfterFirstInstallVal = Double
						.parseDouble(recAfterFirstInstall[i]);
				Obj.setCp_ots_totRecAftFirInst(recAfterFirstInstallVal);
				double deductAmtVal = Double.parseDouble(deductAmt[i]);
				Obj.setCp_ots_totDedtctAmt(deductAmtVal);
				double notDeductAmtVal = Double.parseDouble(notDeductAmt[i]);
				Obj.setCp_ots_totNotDedtctAmt(notDeductAmtVal);
				double netRecovAmtVal = Double.parseDouble(netRecovAmt[i]);
				Obj.setCp_ots_netRecovAmt(netRecovAmtVal);
				double netAmtIndefaoultVal = Double
						.parseDouble(netAmtIndefaoult[i]);
				Obj.setCp_ots_netAmountInDefoault(netAmtIndefaoultVal);
				double secInstallAmtVal = Double.parseDouble(secInstallAmt[i]);
				Obj.setCp_ots_secIntalAMt(secInstallAmtVal);
				double finalPayoutAmtVal = Double
						.parseDouble(finalPayoutAmt[i]);
				Obj.setCp_ots_finalPayout(finalPayoutAmtVal);
				newValues.add(Obj);
				cgpanGaurnteeAmtHiddValTot = cgpanGaurnteeAmtHiddValTot
						+ cgpanGaurnteeAmtHiddVal;
				npaAmountHiddValTot = npaAmountHiddValTot + npaAmountHiddVal;
				recoveryAmtHiddValTot = recoveryAmtHiddValTot
						+ recoveryAmtHiddVal;
				netOutstandAmtValTot = netOutstandAmtValTot + netOutstandAmtVal;
				laibleAmountValTot = laibleAmountValTot + laibleAmountVal;
				firstInstalPaidAmtValTot = firstInstalPaidAmtValTot
						+ firstInstalPaidAmtVal;
				recAfterFirstInstallValTot = recAfterFirstInstallValTot
						+ recAfterFirstInstallVal;
				deductAmtValTot = deductAmtValTot + deductAmtVal;
				notDeductAmtValTot = notDeductAmtValTot + notDeductAmtVal;
				netRecovAmtValTot = netRecovAmtValTot + netRecovAmtVal;
				netAmtIndefaoultValTot = netAmtIndefaoultValTot
						+ netAmtIndefaoultVal;
				secInstallAmtValTot = secInstallAmtValTot + secInstallAmtVal;
				finalPayoutAmtValTot = finalPayoutAmtValTot + finalPayoutAmtVal;
			}
			claimactionform.setCp_ots_guarnteetotal(cgpanGaurnteeAmtHiddValTot);
			claimactionform.setCp_ots_npatotalAmt(npaAmountHiddValTot);
			claimactionform.setCp_ots_recovTotal(recoveryAmtHiddValTot);
			claimactionform.setCp_ots_netOutstandingTotal(netOutstandAmtValTot);
			claimactionform.setCp_ots_liavleAmtTotal(laibleAmountValTot);
			claimactionform
					.setCp_ots_firstInstallmentPaidTotal(firstInstalPaidAmtValTot);
			claimactionform
					.setCp_ots_recafterfirstinstallTotal(recAfterFirstInstallValTot);
			claimactionform.setCp_ots_totaDecdectAMt(deductAmtValTot);
			claimactionform.setCp_ots_totNotdedctAmt(notDeductAmtValTot);
			claimactionform.setCp_ots_totnetRecovAmt(netRecovAmtValTot);
			claimactionform
					.setCp_ots_netAmtindefoultTotal(netAmtIndefaoultValTot);
			claimactionform.setCp_ots_secinstamentAmtTotal(secInstallAmtValTot);
			claimactionform.setCp_ots_finalPayoutAmtTotal(finalPayoutAmtValTot);
			claimactionform.setLoopobjtData(newValues);
		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
		}
		ClaimActionForm obj = new ClaimActionForm();
		obj.setCp_ots_netOutstanding(val);

		return mapping.findForward("datafarward");
	} // farwordData method over

	// ----------------------------------------------------------------------------------------------------------------------------------

	public ActionForward saveOTSData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ClaimActionForm claimActionformdetail = (ClaimActionForm) form;
		HttpSession session = request.getSession(true);
		String remrk = "";
		remrk = claimActionformdetail.getCp_ots_remarks2();
		String memberId = "";
		double mem = 0.0;
		String mliName = "";
		String unitName = "";
		String clmrefNo = "";
		String unitAssit = "";
		String gender = "";
		Date npaDate = null;
		Date firstInstall = null;
		memberId = claimActionformdetail.getCp_ots_enterMember();
		mem = Double.parseDouble(memberId);
		mliName = claimActionformdetail.getCp_ots_mliName();
		unitName = claimActionformdetail.getCp_ots_unitName();
		clmrefNo = claimActionformdetail.getCp_ots_appRefNo();
		unitAssit = claimActionformdetail.getCp_ots_UnitAssitByMSE();
		gender = claimActionformdetail.getCp_ots_gender();
		npaDate = claimActionformdetail.getCp_ots_npaDate();
		firstInstall = claimActionformdetail.getCp_ots_clmappDate();
		String ab = npaDate.toString();
		String xy = firstInstall.toString();
		String converttoDBformnpaDate = DateHelper.stringToDBDate(ab);
		String converttoDBformfirstInstall = DateHelper.stringToDBDate(xy);
		ArrayList cgarry = claimActionformdetail.getLoopobjtData();
		double guarnteetotal = 0.0;
		double npatotalAmt = 0.0;
		double recovTotal = 0.0;
		double netOutstandingTotal = 0.0;
		double liavleAmtTotal = 0.0;
		double firstInstallmentPaidTotal = 0.0;
		double recafterfirstinstallTotal = 0.0;
		double totaDecdectAMt = 0.0;
		double totNotdedctAmt = 0.0;
		double totnetRecovAmt = 0.0;
		double netAmtindefoultTotal = 0.0;
		double secinstamentAmtTotal = 0.0;
		double finalPayoutAmtTotal = 0.0;
		String ctgpan = "";
		Connection connection = DBConnection.getConnection();
		try {
			connection.setAutoCommit(false);
			User user = (User) session
					.getAttribute(com.cgtsi.util.SessionConstants.USER);
			String userid = user.getUserId();
			Date date = Calendar.getInstance().getTime();
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			String todysDate = formatter.format(date);
			String currentDate = DateHelper.stringToDBDate(todysDate);
			String userChoiceSattes = claimActionformdetail
					.getCp_ots_userChoice();
			String farwardtouser = claimActionformdetail.getCp_ots_user();
			userChoiceSattes = userChoiceSattes.trim();
			String satus = "";
			if (userChoiceSattes.equals("NE")) {
				throw new NoMemberFoundException(
						"Please Select The farwrad Option. ");
			} else if (userChoiceSattes.equals("FW")) {
				satus = "FW";
			}
			farwardtouser = farwardtouser.trim();
			if (farwardtouser.equals("Select")) {
				throw new NoMemberFoundException(
						"Please Select The farwrding User .");
			}
			double totGaurantee = claimActionformdetail
					.getCp_ots_guarnteetotal();
			double totNPA = claimActionformdetail.getCp_ots_npatotalAmt();
			double totRec = claimActionformdetail.getCp_ots_recovTotal();
			double totNetOutstand = claimActionformdetail
					.getCp_ots_netOutstandingTotal();

			double totlaibel = claimActionformdetail.getCp_ots_liavleAmtTotal();
			double totfirstInstallpaid = claimActionformdetail
					.getCp_ots_firstInstallmentPaidTotal();
			double totRecoAfterFirstInstal = claimActionformdetail
					.getCp_ots_recafterfirstinstallTotal();
			double totDedc = claimActionformdetail.getCp_ots_totaDecdectAMt();
			double totNotDedect = claimActionformdetail
					.getCp_ots_totNotdedctAmt();
			double totNetrec = claimActionformdetail.getCp_ots_totnetRecovAmt();
			double totnetAmtInDefoult = claimActionformdetail
					.getCp_ots_netAmtindefoultTotal();
			double totSecondInt = claimActionformdetail
					.getCp_ots_secinstamentAmtTotal();
			double totPayout = claimActionformdetail
					.getCp_ots_finalPayoutAmtTotal();
			Statement str = connection
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = str
					.executeQuery("select * from OTS where clm_ref_no='"
							+ clmrefNo + "'");
			while (rs.next()) {
				throw new NoDataException("Application Already Saved.");
			}
			for (int i = 0; i < cgarry.size(); i++) {
				ClaimActionForm clm = (ClaimActionForm) cgarry.get(i);
				ctgpan = clm.getCp_ots_enterCgpan();
				guarnteetotal = clm.getCp_ots_cgpanGaurnteeAmt();
				npatotalAmt = clm.getCp_ots_npaAmount();
				recovTotal = clm.getCp_ots_recoveryAmt();
				netOutstandingTotal = clm.getCp_ots_netOutstanding();
				liavleAmtTotal = clm.getCp_ots_liableamount();
				firstInstallmentPaidTotal = clm
						.getCp_ots_firstIntalpaidAmount();
				recafterfirstinstallTotal = clm.getCp_ots_totRecAftFirInst();
				totNotdedctAmt = clm.getCp_ots_totNotDedtctAmt();
				totaDecdectAMt = clm.getCp_ots_totDedtctAmt();
				totnetRecovAmt = clm.getCp_ots_netRecovAmt();
				netAmtindefoultTotal = clm.getCp_ots_netAmountInDefoault();
				secinstamentAmtTotal = clm.getCp_ots_secIntalAMt();
				finalPayoutAmtTotal = clm.getCp_ots_finalPayout();

				str.executeUpdate("INSERT INTO CGTSIINTRANETUSER.OTS ( CGPAN, GURANTEE_AMT, NPA_AMT, RECOVERY_AMT, NET_OUTSTANDING_AMT, LIABLE_AMT, FIRST_INSTALL_PAID_AMT, REC_AFTER_FIRSTiNSTALL_AMT, NOT_DEDUCTED_AMT, DEDEUCTED_AMT, NET_REC_AMT,NET_DEFOULT_AMT, SEC_INSTALL_AMT ,FINAL_PAY_AMT, MEMBERID, MLI_NAME, UNIT_NAME, CLM_REF_NO, UNIT_ASS_FLAG, GENDER, NPA_DATE, CLM_FIRST_INSTALL_DATE ,REMARKS) VALUES ('"
						+ ctgpan
						+ "' , '"
						+ guarnteetotal
						+ "' , '"
						+ npatotalAmt
						+ "' , '"
						+ recovTotal
						+ "' , '"
						+ netOutstandingTotal
						+ "' , '"
						+ liavleAmtTotal
						+ "' , '"
						+ firstInstallmentPaidTotal
						+ "' , '"
						+ recafterfirstinstallTotal
						+ "' ,'"
						+ totNotdedctAmt
						+ "' , '"
						+ totaDecdectAMt
						+ "' , '"
						+ totnetRecovAmt
						+ "' , '"
						+ netAmtindefoultTotal
						+ "' , '"
						+ secinstamentAmtTotal
						+ "' , '"
						+ finalPayoutAmtTotal
						+ "' , '"
						+ memberId
						+ "' , '"
						+ mliName
						+ "', '"
						+ unitName
						+ "' , '"
						+ clmrefNo
						+ "' , '"
						+ unitAssit
						+ "' , '"
						+ gender
						+ "' , '"
						+ converttoDBformnpaDate
						+ "' , '"
						+ converttoDBformfirstInstall
						+ "' , '"
						+ remrk + "' )");
			}
			String queryStr = "INSERT INTO CGTSIINTRANETUSER.OTS_TOTAL (GURANTEE_AMT_TOTAL,NPA_AMT_TOTAL,RECOVERY_AMT_TOTAL,NET_OUTSTANDING_AMT_TOTAL,LIABLE_AMT,FIRST_INSTALL_PAID_AMT_TOTAL,REC_AFTER_FIRST_INST_AMT_TOTAL,NOT_DEDUCTED_AMT_TOTAL,DEDEUCTED_AMT_TOTAL,NET_REC_AMT_TOTAL,NET_DEFOULT_AMT_TOTAL,SEC_INSTALL_AMT_TOTAL,FINAL_PAY_AMT_TOTAL,MEMBERID,UNIT_NAME,CLM_REF_NO,FORWAREDER_REMARK,FORWAREDER_USR_NAME,FORWAREDED_DATE,STATUS,FARWORDED_TO_USER) VALUES ('"
					+ totGaurantee
					+ "' , '"
					+ totNPA
					+ "' , '"
					+ totRec
					+ "' , '"
					+ totNetOutstand
					+ "' , '"
					+ totlaibel
					+ "' , '"
					+ totfirstInstallpaid
					+ "' , '"
					+ totRecoAfterFirstInstal
					+ "' ,'"
					+ totDedc
					+ "' , '"
					+ totNotDedect
					+ "' , '"
					+ totNetrec
					+ "' , '"
					+ totnetAmtInDefoult
					+ "' , '"
					+ totSecondInt
					+ "' , '"
					+ totPayout
					+ "' , '"
					+ memberId
					+ "' , '"
					+ unitName
					+ "', '"
					+ clmrefNo
					+ "' , '"
					+ remrk
					+ "' , '"
					+ userid
					+ "' , '"
					+ currentDate
					+ "' , '"
					+ satus + "','" + farwardtouser + "')";
			str.executeUpdate(queryStr);
			String modiRecoStatuc = "UPDATE CLM_CGPAN_RECOVERY_DETAIL SET CCRD_FINAL_RECOVERY='Y' WHERE CLM_REF_NO='"
					+ clmrefNo + "'";
			String viewprsentclminRecory = "SELECT * FROM CLM_CGPAN_RECOVERY_DETAIL WHERE CLM_REF_NO='"
					+ clmrefNo + "'";
			ResultSet viewClmInRecoRS = str.executeQuery(viewprsentclminRecory);
			if (viewClmInRecoRS.next()) {
				str.executeUpdate(modiRecoStatuc);
			}
			connection.commit();
		} finally {
			DBConnection.freeConnection(connection);
		}
		request.setAttribute("message", "Claim farwared successfully.");
		return mapping.findForward("successclmsave");
	}

	// ----------------------------------------------------------------------------------------------------------------------------------------------------

	public ActionForward getuserList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Log.log(Log.INFO, "ApplicationProcessingAction", "getDistricts",
				"Entered");
		// //System.out.println("Inside from getUser.....!");
		Administrator admin = new Administrator();
		ClaimActionForm dynaForm = (ClaimActionForm) form;
		HttpSession session1 = request.getSession(false);
		String userChoice = dynaForm.getCp_ots_userChoice();
		// //System.out.println("User Choice :---->"+userChoice);
		Connection connection = DBConnection.getConnection();
		ArrayList userlist = new ArrayList();
		try {
			String var = "A";
			var = var.trim();
			String qury = "SELECT * FROM USER_INFO WHERE MEM_BNK_ID='0000' AND USR_STATUS='A' order by 1";
			Statement str = connection
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = str.executeQuery(qury);

			// int count=0;
			while (rs.next()) {
				// if (count<10)
				userlist.add(rs.getString(1));
				// count++;
			}
			// //System.out.println("Afetr while .....!");
			dynaForm.setCp_ots_userList(userlist);
			// //System.out.println("the User List before make :--->"+userlist.get(2));
			PrintWriter out = response.getWriter();
			String outLine = makeOutputString(userlist);
			out.print(outLine);
			admin = null;
			userlist = null;
			userChoice = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		 finally {
				DBConnection.freeConnection(connection);

			}
		Log.log(Log.INFO, "ApplicationProcessingAction", "getDistricts",
				"Exited");
		// //System.out.println("Before Retrun from getUser.....!");
		return null;
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------

	String makeOutputString(ArrayList districtList) {
		// //System.out.println("The method makeoutputStrinfg process.....!");
		// //System.out.println("Inside  method value is :--->"+districtList.get(1));
		String disstring = "Select;Select";

		for (int i = 1; i < districtList.size(); i++) {
			disstring = disstring + "||" + districtList.get(i) + ";"
					+ districtList.get(i);
			// //System.out.println("Inside loop the value is :---->"+disstring);
		}
		// //System.out.println("The Out put string is :---->"+disstring);
		return disstring;
	}

	// ------------------------------------------------------------------------------------------------------------------------------------------------------------
	// following code for OTS Approval 25042011

	public ActionForward getClaimProcessingOTSNew(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Log.log(Log.INFO, "ClaimAction", "getClaimProcessingOTSNew", "Entered");

		ActionForm actionForm = form;

		ArrayList forApp = new ArrayList();
		HttpSession session = request.getSession(true);
		session.removeAttribute("clmForm");

		Connection connection = DBConnection.getConnection();
		try {
			ClaimActionForm clmForm = new ClaimActionForm();
			String status = "FW";
			Statement str = connection
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_READ_ONLY);

			String query = "select GURANTEE_AMT_TOTAL,NPA_AMT_TOTAL,RECOVERY_AMT_TOTAL,NET_OUTSTANDING_AMT_TOTAL,LIABLE_AMT,FIRST_INSTALL_PAID_AMT_TOTAL,REC_AFTER_FIRST_INST_AMT_TOTAL,NOT_DEDUCTED_AMT_TOTAL,DEDEUCTED_AMT_TOTAL,NET_REC_AMT_TOTAL,NET_DEFOULT_AMT_TOTAL,SEC_INSTALL_AMT_TOTAL,FINAL_PAY_AMT_TOTAL,MEMBERID,UNIT_NAME,CLM_REF_NO FROM OTS_TOTAL WHERE STATUS='"
					+ status + "'";

			ResultSet rs = str.executeQuery(query);
			// //System.out.println("not equsl loop before loop");
			while (!rs.next()) {
				throw new NoMemberFoundException("No Data For Approval. ");
				// throw new NoDataException("No Data For Approval.");
			}
			// //System.out.println("before loop");
			rs.beforeFirst();
			while (rs.next()) {
				ClaimActionForm clm = new ClaimActionForm();
				double guranteeAmt = rs.getDouble(1); // GURANTEE_AMT_TOTAL,
				double npaAmt = rs.getDouble(2); // NPA_AMT_TOTAL
				double recoveryAmt = rs.getDouble(3); // RECOVERY_AMT_TOTAL,
				double netoutstandAmt = rs.getDouble(4); // NET_OUTSTANDING_AMT_TOTAL,
				double liableAmt = rs.getDouble(5); // LIABLE_AMT,
				double firstInstallAmt = rs.getDouble(6); // FIRST_INSTALL_PAID_AMT_TOTAL,
				double recAfterfisrtAmt = rs.getDouble(7); // REC_AFTER_FIRST_INST_AMT_TOTAL,
				double notDeductAmt = rs.getDouble(8); // NOT_DEDUCTED_AMT_TOTAL,
				double decductedAmt = rs.getDouble(9); // DEDEUCTED_AMT_TOTAL,
				double netrecAmt = rs.getDouble(10); // NET_REC_AMT_TOTAL,
				double netDefoultAmt = rs.getDouble(11); // NET_DEFOULT_AMT_TOTAL,
				double secInstallAmt = rs.getDouble(12); // SEC_INSTALL_AMT_TOTAL,
				double finalpayAmt = rs.getDouble(13); // FINAL_PAY_AMT_TOTAL,
				String memberId = rs.getString(14); // MEMBERID,
				String unitName = rs.getString(15); // UNIT_NAME,
				String clmRefno = rs.getString(16); // CLM_REF_NO,

				// //System.out.println("The retrived member id is ----->"+memberId);
				clm.setOts_memberId(memberId);
				clm.setOts_clmRefNo(clmRefno);
				clm.setOts_unitName(unitName);
				clm.setOts_gaurnteeAmt(guranteeAmt);
				clm.setOts_amtInDefault(npaAmt);
				clm.setOts_recoveryPrimary(recoveryAmt);
				clm.setOts_netOutstanding(netoutstandAmt);
				clm.setOts_liableAmt(liableAmt);
				clm.setOts_firstInstallPaidAmt(firstInstallAmt);
				clm.setOts_recoveryAfterPrimary(recAfterfisrtAmt);
				clm.setOts_legalExpencesNotDeducted(notDeductAmt);
				clm.setOts_legalExpencesDeducted(decductedAmt);
				clm.setOts_netRecovery(netrecAmt);
				clm.setOts_netDefaultAmt(netDefoultAmt);
				clm.setOts_secondInstallmentAmt(secInstallAmt);
				clm.setOts_finalPayout(finalpayAmt);
				forApp.add(clm);

			} // while close
				// //System.out.println("afger loop");
			for (int i = 0; i < forApp.size(); i++) {
				ClaimActionForm clm1 = (ClaimActionForm) forApp.get(i);
				double gaurn = clm1.getOts_gaurnteeAmt();
				// //System.out.println("The value of the Gaurantee Toral is =====>"+gaurn);
			}
			// clmForm.setDataforapp(forApp);
			clmForm.setOtsClmProcess(forApp);
			BeanUtils.copyProperties(actionForm, clmForm);
			ArrayList ab = clmForm.getOtsClmProcess();
			for (int i = 0; i < ab.size(); i++) {
				ClaimActionForm clm1 = (ClaimActionForm) ab.get(i);
				double gaurn = clm1.getOts_gaurnteeAmt();
				// //System.out.println("The value of 22222222222222is =====>"+gaurn);
			}

			session.setAttribute("clmForm", clmForm);

		}
		// catch (Exception e)
		// {
		// e.getMessage();
		// e.printStackTrace();
		// }
		finally {

			DBConnection.freeConnection(connection);
		} // finally close

		// /////////////ADD//////////////////

		Log.log(Log.INFO, "ClaimAction", "getClaimProcessingOTSNew", "Exited");
		return mapping.findForward("success");

	}

	public ActionForward saveApproval(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//System.out.println("Inside Save approval ..........!");

		ClaimActionForm clmform = (ClaimActionForm) form;
		//System.out.println("Inside Save Approval .........!");
		// String actiontaken=request.getParameter("otsAction");
		Connection connection = DBConnection.getConnection();
		try {
			HttpSession session = request.getSession(true);
			User user = (User) session
					.getAttribute(com.cgtsi.util.SessionConstants.USER);
			String userid = user.getUserId();
			//System.out.println("The User Name is :--->" + userid);
			Date date = Calendar.getInstance().getTime();
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			String todysDate = formatter.format(date);
			//System.out.println("Today : " + todysDate);
			String currentDate = DateHelper.stringToDBDate(todysDate);
			// //System.out.println("The Current Date is :--->" + currentDate);

			// ClaimActionForm
			// clmform=(ClaimActionForm)session.getAttribute("cpOTSProcess");

			// ClaimActionForm_one clmform=new ClaimActionForm_one();
			ArrayList claimProcess = clmform.getOtsClmProcess();

			// try
			// {
			connection.setAutoCommit(false);
			Statement str = connection
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
			int len = claimProcess.size();
			String action[] = request.getParameterValues("ots_decisionTaken");
			String approvRemark[] = request.getParameterValues("ots_remark");

			// //System.out.println("the length of the arrauList is :===>" + len);
			String actFlg = "";
			for (int i = 0; i < claimProcess.size(); i++) {
				ClaimActionForm clm = (ClaimActionForm) claimProcess.get(i);
				// //System.out.println("Inside loop the clam ref Number is :--->"+clm.getOts_clmRefNo());
				// //System.out.println("the decion taken was=====>"+action[i]);
				// //System.out.println("the decion taken was=====>"+approvRemark[i]);
				String act = action[i];
				if (act.equals("reject")) {
					actFlg = "RE";
				}
				if (act.equals("approve")) {
					actFlg = "AP";
				}
				if (act.equals("hold")) {
					actFlg = "HO";
				}
				if (act.equals("select")) {
					actFlg = "FW";
				}

				String clm_ref = clm.getOts_clmRefNo();
				String remarks = approvRemark[i];

				//System.out.println("the unit name is:----->"
					//	+ clm.getOts_unitName());
				String quryChk = "SELECT STATUS FROM  OTS_TOTAL  WHERE CLM_REF_NO='"
						+ clm_ref + "'";
				ResultSet rs = str.executeQuery(quryChk);
				while (rs.next()) {
					String sta = rs.getString(1);
					if (!sta.equals("FW")) {
						throw new NoMemberFoundException("alreday processed. ");
						// throw new NoDataException("alreday processed.");
					}

				}
				// if (actFlg.equals("FW"))
				// {
				// throw new
				// NoMemberFoundException("Please Select approprate action  Option. ");
				// // throw new
				// NoDataException("Please Select The farwrad Option.");
				// }

				// String
				// qury="update Claim_detail_temp set clm_declaration_recvd='"+falg+"',clm_decl_recvd_dt='"+converttoDBform+"' where clm_ref_no='"+Clamireff+"'";
				String qury = "UPDATE OTS_TOTAL SET APPROVER_USR_NAME='"
						+ userid + "',APPROVER_DATE='" + currentDate
						+ "',APPROVER_REMARK='" + remarks + "',STATUS='"
						+ actFlg + "' WHERE CLM_REF_NO='" + clm_ref + "'";
				str.executeUpdate(qury);

			}
			connection.commit();
		} // try close
		finally {

			DBConnection.freeConnection(connection);
		} // finally close

		return mapping.findForward("saved");
	}

	// //////////////////////////////////////////////////////////////////////////////

	public ActionForward getRecoveryDetailData(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(Log.INFO, "ClaimAction", "getRecoveryDetailData", "Entered");

		String enteredMemberId = null;
		String enterCgpan = null;
		// ActionForm dynaForm=form;
		ClaimActionForm claimForm = (ClaimActionForm) form;
		try {

			// enteredMemberId=request.getParameter("enterMember");
			// enterCgpan=request.getParameter("enterCgpan").toUpperCase();

			enteredMemberId = claimForm.getEnterMember().toUpperCase();
			enterCgpan = claimForm.getEnterCgpan().toUpperCase();
			// //System.out.println("the member Id is :-->" + enteredMemberId);
			// //System.out.println("the CGPAN is :-->" + enterCgpan);

		} catch (Exception e) {
			// throw new NoDataException("Enter CGPAN and  Member Id.");
			// throw new NoMemberFoundException("Enter CGPAN and  Member Id.");
		}

		if (!enteredMemberId.equals(null)) {

			// //System.out.println("inside Memeber Id not null condition....!");

			if (!enterCgpan.equals(null)) {

				Connection connection = DBConnection.getConnection();
				// //System.out.println("The Connection URL is :--->" +
				// connection.toString());
				try {
					// //System.out.println("---->");
					Statement str = connection.createStatement(
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_READ_ONLY);

					String one = enteredMemberId.substring(0, 4);
					String two = enteredMemberId.substring(4, 8);
					String three = enteredMemberId.substring(8, 12);
					// //System.out.println("---->1");

					// check member id Exsist or not
					String MemIdavailbel = "select c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id member_id from application_detail c \n"
							+ "where  c.mem_bnk_id='"
							+ one
							+ "' and\n"
							+ "c.mem_zne_id='"
							+ two
							+ "' and\n"
							+ "c.mem_brn_id='" + three + "'";
					ResultSet rsforavailmemid = str.executeQuery(MemIdavailbel);
					if (!rsforavailmemid.next()) {
						throw new NoMemberFoundException(
								" Member Id  Not Exsist : ");
					}
					// Over check member id Exsist or not
					// Check CGPAN Exsist Or Not
					// //System.out.println("---->2");
					String cgQury = "select c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id member_id from application_detail c where cgpan='"
							+ enterCgpan + "'";
					ResultSet rsforvalid = str.executeQuery(cgQury);
					String memberid = "";
					if (!rsforvalid.next()) {
						throw new NoMemberFoundException(
								" CGPAN  Not Exsist : ");
					}
					// //System.out.println("---->3");
					// Over Check CGPAN Exsist Or Not
					// checking Member Id and Cgpan are Associated Or not
					// //System.out.println("---->4");
					rsforvalid.beforeFirst();
					while (rsforvalid.next()) {
						memberid = rsforvalid.getString(1);
					}
					if (!memberid.equals(enteredMemberId)) {
						throw new NoMemberFoundException(
								" Enterd Member Id and CGPAN  Not Associated. ");
					}
					// Over checking Member Id and Cgpan are Associated Or not
					// //System.out.println("---->5");

					// retrive claim reference number using CGPAN and Member id
					String crefQuery = "select clm_Ref_no from claim_detail  \n"
							+ "where bid in \n"
							+ "(select s.bid from ssi_detail s,application_detail a \n"
							+ "where a.ssi_Reference_number = s.ssi_Reference_number \n"
							+ "and a.cgpan = '"
							+ enterCgpan
							+ "'\n"
							+ "and  a.mem_bnk_id||a.mem_zne_id||a.mem_brn_id='"
							+ enteredMemberId + "')";
					// //System.out.println("---->6");
					ResultSet rsforClaimRefNo = str.executeQuery(crefQuery);
					while (!rsforClaimRefNo.next()) {
						throw new NoMemberFoundException(
								" Claim Application Does Not Exsist for Entred CGPAN and Member Id. ");
					}
					rsforClaimRefNo.beforeFirst();
					String retrivedClmRefNo = "";
					while (rsforClaimRefNo.next()) {
						retrivedClmRefNo = rsforClaimRefNo.getString(1);
					}
					// //System.out.println("the retived Claim Ref No is :-->" +
					// retrivedClmRefNo);

					HttpSession session = request.getSession(true);
					session.setAttribute("clmReff", retrivedClmRefNo);
					session.setAttribute("cgpan", enterCgpan);

					// Over retrive claim reference number using CGPAN and
					// Member id

					/*
					 * Need to Write Code for checking the claim is setteled or
					 * not and first insatllment realaeded or not, if claim is
					 * not settled and first installment is not relaese then
					 * show apropriate msg and also check wheather claim realse
					 * second installment or not if second installment relase
					 * then there is no recovey process so show appropriate msg
					 * for this condtion also
					 */

					// here is code for chercking all

					// rsforClaimRefNo
					String quryfortestapp = "select clm_status,clm_installment_flag from claim_detail where clm_ref_no ='"
							+ retrivedClmRefNo + "'";
					ResultSet rsforchkapp = str.executeQuery(quryfortestapp);
					String clmstatus = "";
					String clminstallmentgflag = "";
					while (rsforchkapp.next()) {
						clmstatus = rsforchkapp.getString(1);
						System.out
								.println("the stautus of application is =====>"
										+ clmstatus);
						clminstallmentgflag = rsforchkapp.getString(2);

					}
					if (!clmstatus.equals("AP")) {
						throw new NoMemberFoundException(
								" The Claim Application is Not Approved. ");
					}

					String QuryForchcond = "select * from claim_application_amount where cgpan='"
							+ enterCgpan + "'";
					ResultSet rsforchkCond = str.executeQuery(QuryForchcond);
					if (!rsforchkCond.next()) {
						throw new NoMemberFoundException(
								" The Claim Applications fisrt Installment not Release. ");
					}

					// here code done
					String QuryForRetriveInfo = "select c.CLM_MLI_NAME,c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id mem_id ,c.CLM_PLACE,c.clm_ref_no,ca.cgpan,ssi_unit_name unit,Ctd_TC_CLM_ELIG_AMT elg_amt,CTD_TC_FIRST_INST_PAY_AMT First_Inst,c.clm_approved_dt\n"
							+ "from claim_detail c,ssi_detail s,claim_application_amount ca,CLAIM_TC_DETAIL T\n"
							+ "where ca.clm_ref_no=c.clm_ref_no\n"
							+ "and  c.clm_status='AP'\n"
							+ "and  c.clm_installment_flag='F'\n"
							+ "and  c.bid=s.bid\n"
							+ "AND t.CGPAN=ca.CGPAN\n"
							+ "and c.clm_ref_no in(select clm_ref_no from claim_application_amount where cgpan='"
							+ enterCgpan
							+ "')\n"
							+ "union\n"
							+ "select c.CLM_MLI_NAME,c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id mem_id ,c.CLM_PLACE,c.clm_ref_no,ca.cgpan,ssi_unit_name unit,Cwd_wC_CLM_ELIG_AMT elg_amt,Cwd_wC_FIRST_INST_PAY_AMT First_Inst,c.clm_approved_dt\n"
							+ "from claim_detail c,ssi_detail s,claim_application_amount ca,CLAIM_wC_DETAIL T\n"
							+ "where ca.clm_ref_no=c.clm_ref_no\n"
							+ "and  c.clm_status='AP'\n"
							+ "and  c.clm_installment_flag='F'\n"
							+ "and  c.bid=s.bid\n"
							+ "AND t.CGPAN=ca.CGPAN\n"
							+ "and c.clm_ref_no in(select clm_ref_no from claim_application_amount where cgpan='"
							+ enterCgpan + "')";

					ResultSet rsforRetrirvedInfo = str
							.executeQuery(QuryForRetriveInfo);
					ClaimActionForm claimActionForm = new ClaimActionForm();
					if (rsforRetrirvedInfo.next()) {

						claimActionForm.setMliName(rsforRetrirvedInfo
								.getString(1));
						claimActionForm.setMliId(rsforRetrirvedInfo
								.getString(2));
						claimActionForm
								.setPlaceforClmRecovery(rsforRetrirvedInfo
										.getString(3));
						claimActionForm.setClmRefNumber(rsforRetrirvedInfo
								.getString(4));
						claimActionForm
								.setCgpanforclamRecovery(rsforRetrirvedInfo
										.getString(5));
						claimActionForm.setUnitName(rsforRetrirvedInfo
								.getString(6));
						claimActionForm.setClmEligibleAmt(rsforRetrirvedInfo
								.getDouble(7));
						claimActionForm
								.setFirstinstalmentrelease(rsforRetrirvedInfo
										.getDouble(8));
						// jagdish
						claimActionForm.setFirstInsatllDate(rsforRetrirvedInfo
								.getDate(9));
						// Date FirstInstaldfate=rsforRetrirvedInfo.getDate(9);

					}

					// select CCRD_RECEIPT_DATE, CCRD_RECOVERY_AMOUNT,
					// CCRD_RECOVERY_EXPENSES, CCRD_EXPENSES_DEDUCTED,
					// CCRD_NET_RECOVERY, CCRD_REC_DD_NO, CCRD_REC_DD_DT,
					// CCRD_REMARKS from CLM_CGPAN_RECOVERY_DETAIL where
					// clm_ref_no='CL05KE020049301'
					ArrayList viewArr = new ArrayList();
					// String
					// viewquery="select CCRD_RECEIPT_DATE, CCRD_RECOVERY_AMOUNT, CCRD_RECOVERY_EXPENSES, CCRD_EXPENSES_DEDUCTED, CCRD_NET_RECOVERY, CCRD_REC_DD_NO,  CCRD_REC_DD_DT, CCRD_REMARKS from  CLM_CGPAN_RECOVERY_DETAIL where clm_ref_no='CL05KE020049301'";
					String viewquery = "select * from CLM_CGPAN_RECOVERY_DETAIL where clm_ref_no='"
							+ retrivedClmRefNo
							+ "' order by ccrd_receipt_date desc";
					ResultSet rsforViewData = str.executeQuery(viewquery);

					while (rsforViewData.next()) {
						ClaimActionForm claimActionForm2 = new ClaimActionForm();
						claimActionForm2.setEnterCgpan(rsforViewData
								.getString(3));
						claimActionForm2.setDateOfreciept(rsforViewData
								.getDate(4));
						claimActionForm2.setAmtRecipt(rsforViewData
								.getDouble(5));
						claimActionForm2.setExpIncforRecovery(rsforViewData
								.getDouble(6));
						claimActionForm2.setExpDeducted(rsforViewData
								.getString(7));
						claimActionForm2.setNetRecovery(rsforViewData
								.getDouble(8));
						claimActionForm2.setDdNo(rsforViewData.getString(9));
						claimActionForm2.setDdDate(rsforViewData.getDate(10));
						claimActionForm2.setRemark(rsforViewData.getString(11));

						viewArr.add(claimActionForm2);
					}
					rsforViewData.beforeFirst();
					double netrecovrySum = 0.0d;
					while (rsforViewData.next()) {
						netrecovrySum = netrecovrySum
								+ rsforViewData.getDouble(8);
					}
					claimActionForm.setTotalNetRecovery(netrecovrySum);
					// //System.out.println("The Total Net Recovery is :--->" +
					// netrecovrySum);

					String clmRefnoId = claimActionForm.getClmRefNumber();
					// ////////////////////////new code for final recovery to
					// check final recovery done or not///////
					// //System.out.println("the claim Ref No is :--->" +
					// clmRefnoId);
					String selectRecovery = "SELECT CCRD_FINAL_RECOVERY  FROM CLM_CGPAN_RECOVERY_DETAIL WHERE CLM_REF_NO='"
							+ clmRefnoId + "' ";
					String finalrecovr = "";
					ResultSet rs = str.executeQuery(selectRecovery);
					while (rs.next()) {
						finalrecovr = rs.getString(1);
						//System.out.println("the value retive is====>"
						//		+ finalrecovr);
						if (finalrecovr.equals("Y")) {
							throw new NoMemberFoundException(
									"Final recovery Alreday Made for Claim . ");

						}

					}
					// ////////////////////////////////////////////////////////////////////////////

					claimActionForm.setViewRecArr(viewArr);

					BeanUtils.copyProperties(claimForm, claimActionForm);
				} finally {
					DBConnection.freeConnection(connection);
					//System.out.println("Inside finally");
				}

			} // if (!enterCgpan.equals(null))
		} // (!memberId.equals(null))

		Log.log(Log.INFO, "ClaimAction", "getRecoveryDetailData", "Exited");
		//System.out.println("before redirecting");
		claimForm.setEnterMember(enteredMemberId);
		claimForm.setEnterCgpan(enterCgpan);
		return mapping.findForward("ClaimRecoverysuccess");
	} // method getRecoveryDetailData over

	public ActionForward forwardtoOTSAfterRecovery(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//System.out.println("inside forwardtoOTSAfterRecovery method...!");
		String key = "OTS";
		request.setAttribute("OTS", key);
		ClaimActionForm actionform = (ClaimActionForm) form;
		// actionform.setBooleanExpInc(false);
		// boolean exp=actionform.isBooleanExpInc();
		actionform.setBooleanFinalRecovery(false);
		// booleanFinalRecovery
		// //System.out.println("The boolean expression is :-->"+exp);
		SaveClaimrecovryData(mapping, form, request, response);
		//System.out.println("after saving data into ");

		String enterCgpan = actionform.getEnterCgpan();
		String mliId = actionform.getMliId();

		actionform.setCp_ots_enterMember(mliId);
		actionform.setCp_ots_enterCgpan(enterCgpan);
		actionform.setCp_ots_appRefNo("");
		// //System.out.println("After Setting the MLI Id,CGPAN Number, Claim Reference Number. ");

		// //System.out.println("The Member Id After Set ===>" +
		// actionform.getCp_ots_enterMember());

		try {
			getRecoveryAfterOTSDetail(mapping, form, request, response);
			// String path= abc.getPath();
			// //System.out.println("The path is :-->");
			// RequestDispatcher rd;
			// RequestDispatcher rd
			// =request.getRequestDispatcher("./jsp/Claim/otsAfterClaimRecoveryDetail.jsp");
			// rd.forward(request, response);

			// sendRedirect("/otsAfterClaimRecoveryDetail.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("detailsuccess");
	}

	public ActionForward SaveClaimrecovryData(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Log.log(Log.INFO, "ClaimAction", "SaveClaimrecovryData", "Entered");
		//System.out.println("inside method.....!");
		// ActionForm actionform=form;
		ClaimActionForm actionform = (ClaimActionForm) form;
		HttpSession session = request.getSession(true);
		User user = (User) session
				.getAttribute(com.cgtsi.util.SessionConstants.USER);
		String userid = user.getUserId();
		// String recDate=request.getParameter("dateOfreciept");
		// String amtreciept=request.getParameter("amtRecipt");
		// String expInForrecover=request.getParameter("expIncforRecovery");
		// String booleanExp=request.getParameter("booleanExpInc");
		// String netrecover=request.getParameter("netRecovery");
		// String ddNo=request.getParameter("ddNo");
		// String ddDate=request.getParameter("ddDate");
		// String remark=request.getParameter("remark");

		Date recDate1 = actionform.getDateOfreciept();
		String recDate = recDate1.toString();
		// //System.out.println("the recipt date is :--->" + recDate1);
		double amtreciept = actionform.getAmtRecipt();
		double expInForrecover = actionform.getExpIncforRecovery();
		boolean booleanExp1 = actionform.isBooleanExpInc();
		// //System.out.println("The booleanExp1:--->" + booleanExp1);
		String booleanExp = "";
		if (booleanExp1 == true) {
			booleanExp = "Y";
		} else {
			booleanExp = "N";
		}
		double netrecover = actionform.getNetRecovery();
		String ddNo = actionform.getDdNo();
		// ddDate
		// Date ddDate1=actionform.getDdDate();
		Date ddDate1 = actionform.getDdDate();
		String ddDate = ddDate1.toString();
		String remark = actionform.getRemark();
		// //System.out.println("the DD date is :--->" + ddDate);
		String clmreff = (String) session.getAttribute("clmReff");
		String cgPan = (String) session.getAttribute("cgpan");
		// String cgPan=request.getParameter("cgpanforclamRecovery");
		// //////code for checking application closing or not/////////////////
		double totnetRecov = actionform.getTotalNetRecovery();
		double firstInstallRelease = actionform.getFirstinstalmentrelease();
		totnetRecov = totnetRecov + netrecover;
		//System.out.println("current total net Recovery is :-->" + totnetRecov);
		boolean finalrecovryFlag = actionform.isBooleanFinalRecovery();
		// //System.out.println("the value of Final Recovery is :--->" +
		// finalrecovryFlag);
		if (finalrecovryFlag) // final recovry check
		{
			if (totnetRecov >= firstInstallRelease) {
				String a = actionform.getCloserrequest();
				String b = actionform.getLtr_ref();
				// //System.out.println("THe Clousrer request Status Flag is :--->"
				// + a);
				//System.out.println("The Lter ref number is :-->" + b);
				if (a.equals("false") || b.equals("")) {
					// //System.out.println("The clouser request and ltr number are mandetory ...!");
					throw new NoMemberFoundException(
							"Must enter the clouser Request and LTR  Details.");
				}
			}
		} // final; recovry close close

		// //////code for checking application closing or not/////////////////

		// //System.out.println("the value of CGPAN is:--->" + cgPan);
		// //System.out.println("the value of claim Rff No  is:--->" + clmreff);

		Date date = Calendar.getInstance().getTime();
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String toadsDate = formatter.format(date);
		// //System.out.println("Today : " + toadsDate);
		String currentDate = DateHelper.stringToDBDate(toadsDate);
		// //System.out.println("The Current Date is :--->" + currentDate);

		String reciptDate = DateHelper.stringToDBDate(recDate);
		String dddbDate = DateHelper.stringToDBDate(ddDate);
		// //System.out.println("after convertig dd date in format for DATA base:-->"
		// + dddbDate);
		// String falgvalue="N";
		boolean falgvalue1 = false;
		String falgvalue = "";
		try {
			// falgvalue=request.getParameter("booleanFinalRecovery");
			falgvalue1 = actionform.isBooleanFinalRecovery();

			// //System.out.println("The Falg Value is :---->" + falgvalue);

			if (falgvalue1) {
				falgvalue = "Y";
				// //System.out.println("here is control setting flag value Y");
			} else {
				falgvalue = "N";
				// //System.out.println("here is control setting flag value N");
			}
		} catch (Exception e) {
			e.getMessage();
		}

		int rowCount = 1;
		Connection connection = DBConnection.getConnection();
		int count = 0;
		try {
			Statement str = connection.createStatement();
			// //System.out.println("Inside Try");

			String insertRecovery = "INSERT INTO CLM_CGPAN_RECOVERY_DETAIL( CCRD_SR_NO, CLM_REF_NO, CGPAN, CCRD_RECEIPT_DATE, CCRD_RECOVERY_AMOUNT, CCRD_RECOVERY_EXPENSES, CCRD_EXPENSES_DEDUCTED, CCRD_NET_RECOVERY, CCRD_REC_DD_NO,  CCRD_REC_DD_DT, CCRD_REMARKS,CCRD_CREATED_MODIFIED_BY,CCRD_CREATED_MODIFIED_DT,CCRD_FINAL_RECOVERY )\n"
					+ "VALUES (CLM_CGP_REC_SEQ.nextval,'"
					+ clmreff
					+ "', '"
					+ cgPan
					+ "', '"
					+ reciptDate
					+ "' ,'"
					+ amtreciept
					+ "' ,'"
					+ expInForrecover
					+ "' ,'"
					+ booleanExp
					+ "' ,'"
					+ netrecover
					+ "' ,'"
					+ ddNo
					+ "' ,'"
					+ dddbDate
					+ "' ,'"
					+ remark
					+ "','"
					+ userid
					+ "','"
					+ currentDate + "' ,'" + falgvalue + "')";

			str.executeUpdate(insertRecovery);
			// //System.out.println("after query fire ");

			// String reqStr=(String)request.getAttribute("OTS");
			// if (reqStr.equals("OTS"))
			// {
			// return null;
			// }

			// }//for close
		} // try closoe
		catch (Exception e) {
			e.printStackTrace();
		} // ctach close
		finally {

			DBConnection.freeConnection(connection);
		} // finally close

		Log.log(Log.INFO, "ClaimAction", "SaveClaimrecovryData", "Exited");

		return mapping.findForward("recoverysuccess");
	}

	// SaveClaimwithclosure

	public ActionForward displayClaimApprovalMod14102011_pradeep(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(Log.INFO, "ClaimAction", "displayClaimApprovalMod()", "Entered");
		// //System.out.println("ClaimAction displayClaimApprovalMod() Entered");
		ClaimActionForm claimForm = (ClaimActionForm) form;
		ClaimsProcessor processor = new ClaimsProcessor();
		String flagClmRefDtl = (String) claimForm.getClmRefDtlSet();
		java.sql.Date stDt = java.sql.Date.valueOf(DateHelper
				.stringToSQLdate(claimForm.getDateOfTheDocument36()));
		java.sql.Date endDt = java.sql.Date.valueOf(DateHelper
				.stringToSQLdate(claimForm.getDateOfTheDocument37()));
		// //System.out.println("From date:"+claimForm.getDateOfTheDocument36());
		// //System.out.println("To date:"+claimForm.getDateOfTheDocument37());

		Log.log(Log.INFO, "ClaimAction", "displayClaimApprovalMod()",
				"*******************************");
		// //System.out.println("ClaimAction displayClaimApproval() flagClmRefDtl :"+flagClmRefDtl);
		Log.log(Log.INFO, "ClaimAction", "displayClaimApprovalMod()",
				"flagClmRefDtl :" + flagClmRefDtl);
		Log.log(Log.INFO, "ClaimAction", "displayClaimApprovalMod()",
				"*******************************");
		ClaimDetail clmdtl = null;
		String clmRefNum = null;
		String payAmntNow = null;
		String userRemarks = null;
		String standardRemarks = null;
		String microFlag = null;

		String womenOwned = null;

		String nerFlag = "N";

		double tcServiceFee = 0;
		double wcServiceFee = 0;
		double tcClaimEligibleAmt = 0;
		double wcClaimEligibleAmt = 0;
		double tcFirstInstallment = 0;
		double wcFirstInstallment = 0;
		double totalTCOSAmountAsOnNPA = 0;
		double totalWCOSAmountAsOnNPA = 0;
		double tcrecovery = 0;
		double wcrecovery = 0;
		// double totalClaimEligibleAmt = 0;
		double tcIssued = 0;
		double wcIssued = 0;

		String falgforCasesafet = "N";

		Administrator admin = new Administrator();

		// The CGTSI active userIds is populated from the database.
		String cgtsiBankId = Constants.CGTSI_USER_BANK_ID;
		// //System.out.println("cgtsiBankId:"+cgtsiBankId);
		String cgtsiZoneId = Constants.CGTSI_USER_ZONE_ID;
		// //System.out.println("cgtsiZoneId:"+cgtsiZoneId);

		String cgtsiBrnId = Constants.CGTSI_USER_BRANCH_ID;
		// //System.out.println("cgtsiBrnId:"+cgtsiBrnId);
		ArrayList userIds = admin.getUsers(cgtsiBankId + cgtsiZoneId
				+ cgtsiBrnId);

		// Remove the logged in userId and the Admin userId.
		User loggedUser = getUserInformation(request);
		String loggedUserId = loggedUser.getUserId();
		// //System.out.println("loggedUserId:"+loggedUserId);
		userIds.remove(loggedUserId);
		userIds.remove("ADMIN");
		if (userIds.contains("DEMOUSER")) {
			userIds.remove("DEMOUSER");
		}
		if (userIds.contains("AUDITOR")) {
			userIds.remove("AUDITOR");
		}
		claimForm.setUserIds(userIds);
		claimForm.setUserId(loggedUserId);

		// //System.out.println("Flag from Claim Ref Dtl :" +
		// claimForm.getClmRefDtlSet());
		// The vector contains objects of ClaimDetail for first claim
		double maxClmApprvdAmnt = 0.0;
		java.util.Date fromDate = null;
		java.util.Date toDate = null;
		java.util.Date dateofReceipt = null;
		User user = getUserInformation(request);
		String designation = user.getDesignation();
		// //System.out.println("designation:"+designation);
		String loggedUsr = user.getUserId();
		Log.log(Log.INFO, "ClaimAction", "displayClaimApprovalMod()",
				"designation :" + designation);
		HashMap clmLimitDetails = processor.getClaimLimitDtls(designation);
		maxClmApprvdAmnt = ((Double) clmLimitDetails
				.get(ClaimConstants.CLM_MAX_APPROVAL_AMOUNT)).doubleValue();
		// //System.out.println("maxClmApprvdAmnt:"+maxClmApprvdAmnt);
		Log.log(Log.INFO, "ClaimAction", "displayClaimApprovalMod()",
				"maxClmApprvdAmnt :" + maxClmApprvdAmnt);
		fromDate = (java.util.Date) clmLimitDetails
				.get(ClaimConstants.CLM_VALID_FROM_DT);
		// //System.out.println("fromDate :" + fromDate);
		toDate = (java.util.Date) clmLimitDetails
				.get(ClaimConstants.CLM_VALID_TO_DT);
		// //System.out.println("toDate :" + toDate);
		if (flagClmRefDtl != null) {
			if (flagClmRefDtl.equals(ClaimConstants.DISBRSMNT_YES_FLAG)) {
				clmdtl = claimForm.getClaimdetail();
				if (clmdtl != null) {
					clmRefNum = (String) clmdtl.getClaimRefNum();
					// //System.out.println("clmRefNum:"+clmRefNum);
					dateofReceipt = claimForm.getDateofReceipt();
					// //System.out.println("dateofReceipt:"+dateofReceipt);
					tcIssued = claimForm.getTcIssued();
					// //System.out.println("tcIssued:"+tcIssued);
					wcIssued = claimForm.getWcIssued();
					// //System.out.println("wcIssued:"+wcIssued);
					userRemarks = (String) clmdtl.getComments();
					standardRemarks = (String) clmdtl.getStandardRemarks();
					userRemarks = standardRemarks.concat(userRemarks);
					// //System.out.println("User Remarks:"+userRemarks);
					totalTCOSAmountAsOnNPA = clmdtl.getTotalTCOSAmountAsOnNPA();
					totalWCOSAmountAsOnNPA = clmdtl.getTotalWCOSAmountAsOnNPA();
					// //System.out.println("totalTCOSAmountAsOnNPA:"+totalTCOSAmountAsOnNPA);
					// //System.out.println("totalWCOSAmountAsOnNPA:"+totalWCOSAmountAsOnNPA);
					tcrecovery = claimForm.getTcrecovery();
					wcrecovery = claimForm.getWcrecovery();
					microFlag = claimForm.getMicroCategory();
					falgforCasesafet = claimForm.getFalgforCasesafet();

					womenOwned = claimForm.getWomenOperated();
					// //System.out.println("Women Owned:"+womenOwned);
					nerFlag = claimForm.getNerFlag();
					// //System.out.println("microFlag:"+microFlag);
					// //System.out.println("tcrecovery:"+tcrecovery);
					// //System.out.println("wcrecovery:"+wcrecovery);
					// totalClaimEligibleAmt =
					// claimForm.getTotalClaimEligibleAmt();
					// //System.out.println("totalClaimEligibleAmt:"+totalClaimEligibleAmt);
					// tcClaimEligibleAmt = claimForm.getTcClaimEligibleAmt();
					// if((Math.min(tcIssued,totalTCOSAmountAsOnNPA)-(tcrecovery))<=500000){
					if (((tcIssued + wcIssued) <= 500000)
							&& (microFlag.equals("Y"))) {
						if (falgforCasesafet.equals("Y")) {

							tcClaimEligibleAmt = Math
									.round((Math.min(tcIssued,
											totalTCOSAmountAsOnNPA) - (tcrecovery)) * 0.85);
						} else if (falgforCasesafet.equals("N")) {
							// jai code
							tcClaimEligibleAmt = Math
									.round((Math.min(tcIssued,
											totalTCOSAmountAsOnNPA) - (tcrecovery)) * 0.80);
						}

						// tcClaimEligibleAmt
						// =Math.round((Math.min(tcIssued,totalTCOSAmountAsOnNPA)-(tcrecovery))*0.80);

					} else if (((tcIssued + wcIssued) <= 5000000)
							&& (womenOwned.equals("F") || nerFlag.equals("Y"))) {
						tcClaimEligibleAmt = Math.round((Math.min(tcIssued,
								totalTCOSAmountAsOnNPA) - (tcrecovery)) * 0.80);
					} else if (((tcIssued + wcIssued) <= 500000)
							&& (microFlag.equals("N"))) {
						tcClaimEligibleAmt = Math.round((Math.min(tcIssued,
								totalTCOSAmountAsOnNPA) - (tcrecovery)) * 0.75);
					} else {
						tcClaimEligibleAmt = Math.round((Math.min(tcIssued,
								totalTCOSAmountAsOnNPA) - (tcrecovery)) * 0.75);
					}
					// //System.out.println("tcClaimEligibleAmt:"+tcClaimEligibleAmt);
					// wcClaimEligibleAmt = claimForm.getWcClaimEligibleAmt();
					// if((Math.min(wcIssued,totalWCOSAmountAsOnNPA)-(wcrecovery))<=500000){
					if (((tcIssued + wcIssued) <= 500000)
							&& (microFlag.equals("Y"))) {
						// jai code
						if (falgforCasesafet.equals("Y")) {
							wcClaimEligibleAmt = Math
									.round((Math.min(wcIssued,
											totalWCOSAmountAsOnNPA) - (wcrecovery)) * 0.85);

						} else if (falgforCasesafet.equals("N")) {
							// jai code
							wcClaimEligibleAmt = Math
									.round((Math.min(wcIssued,
											totalWCOSAmountAsOnNPA) - (wcrecovery)) * 0.80);
						}
						// wcClaimEligibleAmt
						// =Math.round((Math.min(wcIssued,totalWCOSAmountAsOnNPA)-(wcrecovery))*0.80);
					} else if (((tcIssued + wcIssued) <= 5000000)
							&& (womenOwned.equals("F") || nerFlag.equals("Y"))) {
						wcClaimEligibleAmt = Math.round((Math.min(wcIssued,
								totalWCOSAmountAsOnNPA) - (wcrecovery)) * 0.80);
					} else if (((tcIssued + wcIssued) <= 500000)
							&& (microFlag.equals("N"))) {
						wcClaimEligibleAmt = Math.round((Math.min(wcIssued,
								totalWCOSAmountAsOnNPA) - (wcrecovery)) * 0.75);
					} else {
						wcClaimEligibleAmt = Math.round((Math.min(wcIssued,
								totalWCOSAmountAsOnNPA) - (wcrecovery)) * 0.75);
					}

					if (tcClaimEligibleAmt < 0) {
						tcClaimEligibleAmt = 0;
					}

					if (wcClaimEligibleAmt < 0) {
						wcClaimEligibleAmt = 0;
					}

					// //System.out.println("wcClaimEligibleAmt:"+wcClaimEligibleAmt);
					// tcFirstInstallment = claimForm.getTcFirstInstallment();
					tcFirstInstallment = Math.round(tcClaimEligibleAmt * 0.75);
					// //System.out.println("tcFirstInstallment:"+tcFirstInstallment);
					// wcFirstInstallment = claimForm.getWcFirstInstallment();
					wcFirstInstallment = Math.round(wcClaimEligibleAmt * 0.75);
					// //System.out.println("wcFirstInstallment:"+wcFirstInstallment);
					tcServiceFee = claimForm.getAsfDeductableforTC();
					// //System.out.println("tcServiceFee:"+tcServiceFee);
					wcServiceFee = claimForm.getAsfDeductableforWC();
					// //System.out.println("wcServiceFee:"+wcServiceFee);
					// payAmntNow = (String)clmdtl.getTotalAmtPayNow();
					payAmntNow = Double.toString(tcFirstInstallment
							+ wcFirstInstallment - tcServiceFee - wcServiceFee);
					clmdtl.setTotalAmtPayNow(payAmntNow);
					// //System.out.println("payAmntNow:"+payAmntNow);

					CPDAO cpdao = new CPDAO();
					cpdao.insertClaimProcessDetails(clmRefNum, userRemarks,
							tcServiceFee, wcServiceFee, tcClaimEligibleAmt,
							wcClaimEligibleAmt, tcFirstInstallment,
							wcFirstInstallment, totalTCOSAmountAsOnNPA,
							totalWCOSAmountAsOnNPA, tcrecovery, wcrecovery,
							dateofReceipt);
					if ((payAmntNow != null) && (!payAmntNow.equals(""))) {
						if ((Double.parseDouble(payAmntNow)) < 0.0) {
							payAmntNow = "0.0";
						}
					} else {
						payAmntNow = "0.0";
					}
					/*
					 * double clmEligibleAmnt = clmdtl.getEligibleClaimAmt();
					 * if((payAmntNow != null) &&
					 * (Double.parseDouble(payAmntNow)) > clmEligibleAmnt) {
					 * throw new InvalidDataException(
					 * "Amount Payable now cannot be more than the Claim Eligibility Amount."
					 * ); }
					 */
				}
			}
			/*
			 * else { flagClmRefDtl=""; payAmntNow=null;
			 * clmdtl.setTotalAmtPayNow(null); }
			 */
		}
		String clmStatus = null;
		String comments = null;
		String forwardedToUser = null;
		String thiskey = null;
		int outOfLimit = 0;
		double clmEligibleAmnt = 0.0;
		java.util.Date currentDate = new java.util.Date();
		Vector firstinstllmntclaims = processor.getClaimProcessingDetailsMod(
				ClaimConstants.FIRST_INSTALLMENT, stDt, endDt);
		/*
		 * for(int i=0; i<firstinstllmntclaims.size(); i++) { ClaimDetail clmDtl
		 * = (ClaimDetail)firstinstllmntclaims.elementAt(i); if(clmDtl == null)
		 * { continue; } String memId = (String)clmDtl.getMliId();
		 * Log.log(Log.INFO
		 * ,"ClaimAction","displayClaimApproval()","******************************"
		 * ); Log.log(Log.INFO,"ClaimAction","displayClaimApproval()","memId :"
		 * + memId); String claimrefnumber = (String)clmDtl.getClaimRefNum();
		 * Log
		 * .log(Log.INFO,"ClaimAction","displayClaimApproval()","claimrefnumber :"
		 * + claimrefnumber); String claimStatus =
		 * (String)clmDtl.getClmStatus();
		 * Log.log(Log.INFO,"ClaimAction","displayClaimApproval()","clmStatus :"
		 * + claimStatus); String commnts = (String)clmDtl.getComments(); String
		 * forwardedToUsr = (String)clmDtl.getForwaredToUser();
		 * Log.log(Log.INFO,
		 * "ClaimAction","displayClaimApproval()","forwardedToUser :" +
		 * forwardedToUsr);
		 * Log.log(Log.INFO,"ClaimAction","displayClaimApproval()"
		 * ,"******************************"); }
		 */
		// Vector forwardedFirstClms = new Vector();
		if (firstinstllmntclaims != null) {
			for (int i = 0; i < firstinstllmntclaims.size(); i++) {
				ClaimDetail clmDtl = (ClaimDetail) firstinstllmntclaims
						.elementAt(i);
				if (clmDtl == null) {
					continue;
				}
				Log.log(Log.INFO, "ClaimAction", "displayClaimApproval()",
						"##################");
				String memId = (String) clmDtl.getMliId();
				// //System.out.println("MemberId:"+memId);
				Log.log(Log.INFO, "ClaimAction", "displayClaimApproval()",
						"memId :" + memId);
				String claimrefnumber = (String) clmDtl.getClaimRefNum();
				// //System.out.println("claimrefnumber:"+claimrefnumber);
				Log.log(Log.INFO, "ClaimAction", "displayClaimApproval()",
						"claimrefnumber :" + claimrefnumber);
				clmStatus = (String) clmDtl.getClmStatus();
				// //System.out.println("clmStatus:"+clmStatus);
				Log.log(Log.INFO, "ClaimAction", "displayClaimApproval()",
						"clmStatus :" + clmStatus);
				comments = (String) clmDtl.getComments();
				// //System.out.println("comments:"+comments);
				forwardedToUser = (String) clmDtl.getForwaredToUser();
				// //System.out.println("forwardedToUser:"+forwardedToUser);
				Log.log(Log.INFO, "ClaimAction", "displayClaimApproval()",
						"forwardedToUser :" + forwardedToUser);
				Log.log(Log.INFO, "ClaimAction", "displayClaimApproval()",
						"###################");
				clmEligibleAmnt = clmDtl.getEligibleClaimAmt();
				// //System.out.println("Line number 5913 clmEligibleAmnt :" +
				// clmEligibleAmnt);
				// //System.out.println("maxClmApprvdAmnt :" + maxClmApprvdAmnt);

				if (((fromDate != null) && (!fromDate.equals("")))
						&& ((toDate != null) && (!toDate.equals("")))) {
					if (((fromDate.compareTo(currentDate)) <= 0)
							&& ((currentDate.compareTo(toDate)) <= 0)) {
						if (clmEligibleAmnt > maxClmApprvdAmnt) {
							// //System.out.println("The clmEligibleAmnt is greater than the maxClmApprvdAmnt");
							outOfLimit++;
							firstinstllmntclaims.remove(i);
							i--;
							continue;
						}
					}
				}
				if (((fromDate != null) && (!fromDate.equals("")))
						&& (toDate == null)) {
					if ((fromDate.compareTo(currentDate)) <= 0) {
						if (clmEligibleAmnt > maxClmApprvdAmnt) {
							outOfLimit++;
							firstinstllmntclaims.remove(i);
							i--;
							continue;
						}
					}
				}

				if (clmStatus != null) {
					if ((clmStatus.equals(ClaimConstants.CLM_FORWARD_STATUS))
							|| (clmStatus
									.equals(ClaimConstants.CLM_HOLD_STATUS))) {
						thiskey = ClaimConstants.FIRST_INSTALLMENT
								+ ClaimConstants.CLM_DELIMITER1 + memId
								+ ClaimConstants.CLM_DELIMITER1
								+ claimrefnumber;
						claimForm.setDecision(thiskey, clmStatus);
						claimForm.setRemarks(thiskey, comments);
						Log.log(Log.INFO, "ClaimAction",
								"displayClaimApproval()", "loggedUsr :"
										+ loggedUsr);
						if ((forwardedToUser != null)
								&& (!forwardedToUser
										.equalsIgnoreCase(loggedUsr))) {
							// forwardedFirstClms.addElement(clmDtl);
							firstinstllmntclaims.remove(i);
							--i;
						}
						if ((forwardedToUser != null)
								&& (forwardedToUser.equalsIgnoreCase(loggedUsr))) {
							// forwardedFirstClms.addElement(clmDtl);
							Log.log(Log.INFO, "ClaimAction",
									"displayClaimApproval()",
									"*******************");
							Log.log(Log.INFO, "ClaimAction",
									"displayClaimApproval()", "thiskey :"
											+ thiskey);
							Log.log(Log.INFO, "ClaimAction",
									"displayClaimApproval()",
									"forwardedToUser :" + forwardedToUser);
							Log.log(Log.INFO, "ClaimAction",
									"displayClaimApproval()",
									"*******************");
							claimForm.setForwardedToIds(thiskey,
									forwardedToUser);
							if (userIds.contains(forwardedToUser)) {
								claimForm.setUserIds(userIds);
							}
						}
					}
				}
			}
		}

		// The vector contains objects of ClaimDetail for second claim
		Vector secinstllmntclaims = processor
				.getClaimProcessingDetails(ClaimConstants.SECOND_INSTALLMENT);
		// Vector forwardedSecClms = new Vector();
		if (secinstllmntclaims != null) {
			for (int i = 0; i < secinstllmntclaims.size(); i++) {
				ClaimDetail clmDtl = (ClaimDetail) secinstllmntclaims
						.elementAt(i);
				if (clmDtl == null) {
					continue;
				}
				clmEligibleAmnt = clmDtl.getEligibleClaimAmt();
				if (((fromDate != null) && (!fromDate.equals("")))
						&& ((toDate != null) && (!toDate.equals("")))) {
					if (((fromDate.compareTo(currentDate)) <= 0)
							&& ((currentDate.compareTo(toDate)) <= 0)) {
						if (clmEligibleAmnt > maxClmApprvdAmnt) {
							outOfLimit++;
							secinstllmntclaims.remove(i);
							i--;
							continue;
						}
					}
				}
				if (((fromDate != null) && (!fromDate.equals("")))
						&& (toDate == null)) {
					if ((fromDate.compareTo(currentDate)) <= 0) {
						if (clmEligibleAmnt > maxClmApprvdAmnt) {
							outOfLimit++;
							secinstllmntclaims.remove(i);
							i--;
							continue;
						}
					}
				}
				String memId = (String) clmDtl.getMliId();
				String claimrefnumber = (String) clmDtl.getClaimRefNum();
				String cgclan = (String) clmDtl.getCGCLAN();
				clmStatus = (String) clmDtl.getClmStatus();
				comments = (String) clmDtl.getComments();
				forwardedToUser = (String) clmDtl.getForwaredToUser();
				Log.log(Log.INFO, "ClaimAction", "displayClaimApproval()",
						"forwardedToUser :" + forwardedToUser);
				if (clmStatus != null) {
					if ((clmStatus.equals(ClaimConstants.CLM_FORWARD_STATUS))
							|| (clmStatus
									.equals(ClaimConstants.CLM_HOLD_STATUS))) {
						thiskey = ClaimConstants.SECOND_INSTALLMENT
								+ ClaimConstants.CLM_DELIMITER1 + memId
								+ ClaimConstants.CLM_DELIMITER1
								+ claimrefnumber
								+ ClaimConstants.CLM_DELIMITER1 + cgclan;
						claimForm.setDecision(thiskey, clmStatus);
						claimForm.setRemarks(thiskey, comments);
						Log.log(Log.INFO, "ClaimAction",
								"displayClaimApproval()", "loggedUsr :"
										+ loggedUsr);
						if ((forwardedToUser != null)
								&& (!forwardedToUser
										.equalsIgnoreCase(loggedUsr))) {
							// forwardedSecClms.addElement(clmDtl);
							secinstllmntclaims.remove(i);
							--i;
						}
						if ((forwardedToUser != null)
								&& (forwardedToUser.equalsIgnoreCase(loggedUsr))) {
							// forwardedSecClms.addElement(clmDtl);
							claimForm.setForwardedToIds(thiskey,
									forwardedToUser);
						}
					}
				}
			}
		}

		if ((firstinstllmntclaims.size() == 0)
				&& (secinstllmntclaims.size() == 0)) {
			request.setAttribute("message",
					"There are no Claim Application(s) to process.");
			return mapping.findForward("success");
		}

		for (int i = 0; i < firstinstllmntclaims.size(); i++) {
			ClaimDetail cd = (ClaimDetail) firstinstllmntclaims.elementAt(i);
			String crn = cd.getClaimRefNum();
			if (crn.equals(clmRefNum)) {
				cd = (ClaimDetail) firstinstllmntclaims.remove(i);
				cd.setTotalAmtPayNow(payAmntNow);
				cd.setComments(standardRemarks.concat(userRemarks));
				firstinstllmntclaims.addElement(cd);
			}
		}
		for (int i = 0; i < secinstllmntclaims.size(); i++) {
			ClaimDetail cd = (ClaimDetail) secinstllmntclaims.elementAt(i);
			String crn = cd.getClaimRefNum();
			// //System.out.println("Line number6072 crn:"+crn);
			if (crn.equals(clmRefNum)) {
				cd = (ClaimDetail) secinstllmntclaims.remove(i);
				cd.setTotalAmtPayNow(payAmntNow);
				// //System.out.println("Line number 6077 payAmntNow:"+payAmntNow);
				secinstllmntclaims.addElement(cd);
			}
		}
		clmdtl = null;
		clmRefNum = null;
		payAmntNow = null;
		claimForm.setLimit(outOfLimit);
		// //System.out.println("Line number 6085 outOfLimit:"+outOfLimit);
		claimForm.setFirstInstallmentClaims(firstinstllmntclaims);
		// //System.out.println("Number of applications for first claim installments :"
		// + firstinstllmntclaims.size());
		claimForm.setFirstCounter(firstinstllmntclaims.size());
		claimForm.setSecondInstallmentClaims(secinstllmntclaims);
		// //System.out.println("Number of applications for second claim installments :"
		// + secinstllmntclaims.size());
		claimForm.setSecondCounter(secinstllmntclaims.size());
		claimForm.setClmRefDtlSet(ClaimConstants.DISBRSMNT_NO_FLAG);
		/*
		 * Clearing up the Collection Objects
		 */
		// firstinstllmntclaims.clear();
		firstinstllmntclaims = null;
		// secinstllmntclaims.clear();
		secinstllmntclaims = null;
		Log.log(Log.INFO, "ClaimAction", "displayClaimApproval()", "Exited");
		return mapping.findForward("displayClaimsApprovalPage");
	}

	public ActionForward displayClaimDetailsInput(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws DatabaseException {
		ClaimActionForm cpForm = (ClaimActionForm) form;
		cpForm.setMemberId("");
		cpForm.setBorrowerID("");
		cpForm.setClmRefNumber("");
		cpForm.setClaimapplication(null);
		cpForm.resetTheFirstClaimApplication(mapping, request);
		return mapping.findForward("success");
	}

	// added to get claim details

	public ActionForward getClaimDetailForClmRefNo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws DatabaseException,
			NoDataException, Exception {
		ClaimActionForm cpForm = (ClaimActionForm) form;
		ClaimsProcessor processor = new ClaimsProcessor();
		String clmrefno = (String) cpForm.getClmRefNumberNew();

		if (clmrefno.equals("")) {
			throw new MessageException("Please enter claim reference number.");
		} else {
			clmrefno = clmrefno.toUpperCase().trim();
		}

		Map map = getClaimInfo(clmrefno);
		String clmStatus = (String) map.get("CLM_STATUS");
		String memberId = (String) map.get("MEMBERID");
		String borrowerId = (String) map.get("BID");
		String installmentFlag = (String) map.get("INSTALLMENTFLAG");
		Date clmdate = (java.util.Date) map.get("CLMDATE");

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String str = sdf.format(clmdate);
		String[] arr = str.split("/");
		/*
		 * if(arr[2].compareTo("2014") < 0){ throw new
		 * NoDataException("This claim is not available for updation."); }
		 */
		HttpSession session = request.getSession();
		// //System.out.println(session.getAttribute("mainMenu")+"---"+session.getAttribute("subMenuItem"));

		if (session.getAttribute("mainMenu").equals(
				MenuOptions.getMenu(MenuOptions.CP_CLAIM_FOR))
				&& session.getAttribute("subMenuItem").equals(
						MenuOptions.getMenu(MenuOptions.CP_UPDATE_RT_CLAIM))
				&& !"RT".equals(clmStatus)) {
			throw new DatabaseException(
					"Details for the Claim reference number: " + clmrefno
							+ " can not be updated.");
		}
		if (session.getAttribute("mainMenu").equals(
				MenuOptions.getMenu(MenuOptions.CP_CLAIM_FOR))
				&& session.getAttribute("subMenuItem").equals(
						MenuOptions.getMenu(MenuOptions.CP_UPDATE_TC_CLAIM))
				&& !"TC".equals(clmStatus)) {
			throw new DatabaseException(
					"Details for the Claim reference number: " + clmrefno
							+ " can not be updated.");
		}

		HashMap npaDetails = processor.isNPADetailsAvailable(borrowerId);
		HashMap npadtlMainTable = (HashMap) npaDetails
				.get(ClaimConstants.CLM_MAIN_TABLE);
		if (npadtlMainTable != null) {
			if (npadtlMainTable.size() > 0) {
				java.util.Date npaCreatedDate = (Date) npadtlMainTable
						.get("npaCreatedDate");
				if (npaCreatedDate == null && "TC".equals(clmStatus)) {
					throw new NoDataException(
							"Please Update Npa Details.Please go to GuaranteeMaintenance-->PeriodicInfo-->NPA Details.");
				}
			}
		}
		HashMap npadtltemptable = (HashMap) npaDetails
				.get(ClaimConstants.CLM_TEMP_TABLE);
		if (npadtltemptable != null) {
			if (npadtltemptable.size() > 0) {
				java.util.Date npaCreatedDate = (Date) npadtltemptable
						.get("npaCreatedDate");
				if (npaCreatedDate == null && "TC".equals(clmStatus)) {
					throw new NoDataException(
							"Please Update Npa Details.Please go to GuaranteeMaintenance-->PeriodicInfo-->NPA Details.");
				}
			}
		}

		Vector cgpans =
		// new GMDAO().getCGPANDetailsPeriodicInfo(borrowerId, memberId);
		new CPDAO().getCGPANsForClaim(borrowerId, memberId);

		if (cgpans == null || cgpans.size() == 0) {
			throw new NoDataException(
					"For this Borrower, there are no Live Account(s) or the Loan Account(s) have been closed.");
		}
		ReportManager manager = new ReportManager();

		ClaimApplication claimapplication = manager.displayClmRefNumberDtl(
				clmrefno, clmStatus, memberId);
		if (claimapplication == null) {
			throw new NoDataException(
					"Unable to get claim application details.");
		}

		cpForm.setMemberId(memberId);
		cpForm.setBorrowerID(borrowerId);
		cpForm.setDealingOfficerName(claimapplication.getDealingOfficerName());
		cpForm.setMemberDetails(claimapplication.getMemberDetails());
		cpForm.setBorrowerDetails(claimapplication.getBorrowerDetails());
		cpForm.setMicroCategory(claimapplication.getMicroCategory());

		java.util.Date npaClassifiedDate = null;
		String npaClassifiedDateStr = null;
		java.util.Date npaReportingDate = null;
		String npaReportingDateStr = null;
		String reasonfornpa = null;
		String subsidyFlag = null;
		String isSubsidyRcvdFlag = null;
		String isSubsidyAdjustedFlag = null;
		java.util.Date subsidyDt = null;
		String subsidyDtStr = "";
		double subsidyAmt = 0.0;
		String npaId = null;
		HashMap npaMap = new HashMap();

		SecurityAndPersonalGuaranteeDtls sapgd = claimapplication
				.getSecurityAndPersonalGuaranteeDtls();

		// set npa details from claimapplication object
		npaClassifiedDate = claimapplication
				.getDateOnWhichAccountClassifiedNPA();
		if (npaClassifiedDate != null) {
			npaClassifiedDateStr = (String) sdf.format(npaClassifiedDate);
		}
		reasonfornpa = (String) claimapplication
				.getReasonsForAccountTurningNPA();
		subsidyFlag = (String) claimapplication.getSubsidyFlag();
		isSubsidyRcvdFlag = (String) claimapplication
				.getIsSubsidyRcvdAfterNpa();
		isSubsidyAdjustedFlag = (String) claimapplication
				.getIsSubsidyAdjustedOnDues();
		subsidyAmt = (Double) claimapplication.getSubsidyAmt();
		subsidyDt = claimapplication.getSubsidyDate();
		if (subsidyDt != null) {
			subsidyDtStr = (String) sdf.format(subsidyDt);
			try {
				subsidyDt = sdf.parse(subsidyDtStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		// set asonsanc and asonnpa details from claimapplication object

		DtlsAsOnDateOfSanction dtlAsOnSanc = sapgd
				.getDetailsAsOnDateOfSanction();
		DtlsAsOnDateOfNPA dtlAsOnNpa = sapgd.getDetailsAsOnDateOfNPA();

		Map securityMap = new HashMap();
		securityMap.put("LAND", dtlAsOnSanc.getValueOfLand());
		securityMap.put("networth", dtlAsOnSanc.getNetworthOfGuarantors());
		securityMap.put("MACHINE", dtlAsOnSanc.getValueOfMachine());
		securityMap.put("BUILDING", dtlAsOnSanc.getValueOfBuilding());
		securityMap.put("OTHER FIXED MOVABLE ASSETS",
				dtlAsOnSanc.getValueOfOtherFixedMovableAssets());
		securityMap
				.put("CURRENT ASSETS", dtlAsOnSanc.getValueOfCurrentAssets());
		securityMap.put("OTHERS", dtlAsOnSanc.getValueOfOthers());
		securityMap.put("reasonReduction", "NA");
		double totalAsOnSanc = dtlAsOnSanc.getValueOfLand()
				+ dtlAsOnSanc.getValueOfMachine()
				+ dtlAsOnSanc.getValueOfBuilding()
				+ dtlAsOnSanc.getValueOfOtherFixedMovableAssets()
				+ dtlAsOnSanc.getValueOfCurrentAssets()
				+ dtlAsOnSanc.getValueOfOthers();
		cpForm.setTotSecAsOnSanc(totalAsOnSanc);
		cpForm.setAsOnDtOfSanctionDtl(securityMap);

		securityMap = null;
		securityMap = new HashMap();
		securityMap.put("LAND", dtlAsOnNpa.getValueOfLand());
		securityMap.put("networth", dtlAsOnNpa.getNetworthOfGuarantors());
		securityMap.put("MACHINE", dtlAsOnNpa.getValueOfMachine());
		securityMap.put("BUILDING", dtlAsOnNpa.getValueOfBuilding());
		securityMap.put("OTHER FIXED MOVABLE ASSETS",
				dtlAsOnNpa.getValueOfOtherFixedMovableAssets());
		securityMap.put("CURRENT ASSETS", dtlAsOnNpa.getValueOfCurrentAssets());
		securityMap.put("OTHERS", dtlAsOnNpa.getValueOfOthers());
		securityMap.put("reasonReduction", dtlAsOnNpa.getReasonsForReduction());
		double totalAsOnNpa = dtlAsOnNpa.getValueOfLand()
				+ dtlAsOnNpa.getValueOfMachine()
				+ dtlAsOnNpa.getValueOfBuilding()
				+ dtlAsOnNpa.getValueOfOtherFixedMovableAssets()
				+ dtlAsOnNpa.getValueOfCurrentAssets()
				+ dtlAsOnNpa.getValueOfOthers();
		cpForm.setTotSecAsOnNpa(totalAsOnNpa);
		cpForm.setAsOnDtOfNPA(securityMap);
		securityMap = null;

		npaMap.put(ClaimConstants.NPA_CLASSIFIED_DT, npaClassifiedDateStr);
		npaMap.put(ClaimConstants.REASONS_FOR_TURNING_NPA, reasonfornpa);
		cpForm.setNpaDetails(npaMap);
		cpForm.setWilfullDefaulter(claimapplication
				.getWhetherBorrowerIsWilfulDefaulter());
		cpForm.setIsAcctFraud(claimapplication.getFraudFlag());
		cpForm.setIsEnquiryConcluded(claimapplication.getEnquiryFlag());
		cpForm.setIsMLIInvolved(claimapplication.getMliInvolvementFlag());
		cpForm.setSubsidyFlag(subsidyFlag);
		cpForm.setIsSubsidyRcvdAfterNpa(isSubsidyRcvdFlag);
		cpForm.setIsSubsidyAdjustedOnDues(isSubsidyAdjustedFlag);
		cpForm.setSubsidyAmt(subsidyAmt);
		cpForm.setSubsidyDate(subsidyDtStr);
		cpForm.setDateOfRecallNotice(claimapplication
				.getDateOfIssueOfRecallNoticeStr());
		cpForm.setReasonForIssueRecallNotice(claimapplication
				.getReasonForRecall());
		cpForm.setForumthrulegalinitiated(claimapplication
				.getLegalProceedingsDetails()
				.getForumRecoveryProceedingsInitiated());
		cpForm.setCaseregnumber(claimapplication.getLegalProceedingsDetails()
				.getSuitCaseRegNumber());
		cpForm.setLegaldate(claimapplication.getLegalProceedingsDetails()
				.getFilingDateStr());
		cpForm.setReasonForFilingSuit(claimapplication.getReasonForFilingSuit());
		Date dt = null;
		String strdt = null;
		Date date = null;
		dt = claimapplication.getAssetPossessionDt();
		if (dt != null) {
			strdt = (String) sdf.format(dt);
			cpForm.setAssetPossessionDate(strdt);
		} else {
			cpForm.setAssetPossessionDate(null);
		}
		dt = null;
		strdt = null;
		cpForm.setLocation(claimapplication.getLegalProceedingsDetails()
				.getLocation());
		cpForm.setAmountclaimed(String.valueOf(claimapplication
				.getLegalProceedingsDetails().getAmountClaimed()));
		cpForm.setInclusionOfReciept(claimapplication.getInclusionOfReceipt());
		cpForm.setConfirmRecoveryValues(claimapplication
				.getConfirmRecoveryFlag());
		cpForm.setMliCommentOnFinPosition(claimapplication
				.getMliCommentOnFinPosition());
		cpForm.setDetailsOfFinAssistance(claimapplication
				.getDetailsOfFinAssistance());
		cpForm.setCreditSupport(claimapplication.getCreditSupport());
		cpForm.setBankFacilityDetail(claimapplication.getBankFacilityDetail());
		cpForm.setPlaceUnderWatchList(claimapplication.getPlaceUnderWatchList());
		cpForm.setRemarksOnNpa(claimapplication.getRemarksOnNpa());
		cpForm.setNameOfOfficial(claimapplication.getNameOfOfficial());
		cpForm.setDesignationOfOfficial(claimapplication
				.getDesignationOfOfficial());
		dt = claimapplication.getClaimSubmittedDate();
		if (dt != null) {
			strdt = (String) sdf.format(dt);
			date = null;
		} else {
			strdt = null;
		}
		cpForm.setClaimSubmittedDate(strdt);
		dt = null;
		strdt = null;
		cpForm.setPlace(claimapplication.getPlace());

		Map securityMapAsOnLodgement = new HashMap();
		DtlsAsOnLogdementOfClaim dtlAsOnClaim = sapgd
				.getDetailsAsOnDateOfLodgementOfClaim();
		securityMapAsOnLodgement.put("LAND", dtlAsOnClaim.getValueOfLand());
		securityMapAsOnLodgement.put("networth",
				dtlAsOnClaim.getNetworthOfGuarantors());
		securityMapAsOnLodgement.put("MACHINE",
				dtlAsOnClaim.getValueOfMachine());
		securityMapAsOnLodgement.put("BUILDING",
				dtlAsOnClaim.getValueOfBuilding());
		securityMapAsOnLodgement.put("OTHER FIXED MOVABLE ASSETS",
				dtlAsOnClaim.getValueOfOtherFixedMovableAssets());
		securityMapAsOnLodgement.put("CURRENT ASSETS",
				dtlAsOnClaim.getValueOfCurrentAssets());
		securityMapAsOnLodgement.put("OTHERS", dtlAsOnClaim.getValueOfOthers());
		securityMapAsOnLodgement.put("reasonReduction",
				dtlAsOnClaim.getReasonsForReduction());
		double totalAsOClaim = dtlAsOnClaim.getValueOfLand()
				+ dtlAsOnClaim.getValueOfMachine()
				+ dtlAsOnClaim.getValueOfBuilding()
				+ dtlAsOnClaim.getValueOfOtherFixedMovableAssets()
				+ dtlAsOnClaim.getValueOfCurrentAssets()
				+ dtlAsOnClaim.getValueOfOthers();
		cpForm.setAsOnLodgemntOfCredit(securityMapAsOnLodgement);
		cpForm.setTotSecAsOnClaim(totalAsOClaim);

		// cpForm.setTcCgpansVector(claimapplication.getTermCapitalDtls());
		// cpForm.setWcCgpansVector(claimapplication.getWorkingCapitalDtlsVector());

		Vector tc = claimapplication.getTermCapitalDtls(); // -----------------------------(1)
		// //System.out.println("initial size of tc cgpans:"+tc.size());
		for (int m = 0; m < tc.size(); m++) {
			boolean isFound = false;
			TermLoanCapitalLoanDetail td = (TermLoanCapitalLoanDetail) tc
					.get(m);
			String cg = td.getCgpan();
			for (int n = 0; n < cgpans.size(); n++) {
				Map cgmap = (HashMap) cgpans.get(n);
				String scg = (String) cgmap.get(ClaimConstants.CLM_CGPAN);

				if (scg.equals(cg)) {
					isFound = true;
					break;
				}
			}
			if (!isFound) {
				tc.remove(m);
			}
		}
		// //System.out.println("eligible size of tc cgpans:"+tc.size());
		Map tempMap = new HashMap();
		Vector tcCgpansVector = new Vector();
		for (int i = 0; i < tc.size(); i++) {
			int j = i + 1;
			TermLoanCapitalLoanDetail td = (TermLoanCapitalLoanDetail) tc
					.get(i);
			Map tccg = new HashMap();
			String cg = td.getCgpan();
			for (int k = 0; k < cgpans.size(); k++) {
				tempMap = (HashMap) cgpans.get(k);
				if (cg.equals(tempMap.get(ClaimConstants.CLM_CGPAN))) {
					tccg.put(
							ClaimConstants.CLM_APPLICATION_APPRVD_AMNT,
							tempMap.get(ClaimConstants.CLM_APPLICATION_APPRVD_AMNT));
					break;
				}
			}
			tccg.put(ClaimConstants.CLM_CGPAN, td.getCgpan());
			tcCgpansVector.add(tccg);
			cpForm.setCgpandetails("key-" + j, td.getCgpan());
			dt = td.getLastDisbursementDate();
			if (dt != null) {
				strdt = (String) sdf.format(dt);

				try {
					date = sdf.parse(strdt);
					cpForm.setLastDisbursementDate("key-" + j, strdt);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			} else {
				cpForm.setLastDisbursementDate("key-" + j,
						td.getLastDisbursementDate());
			}
			cpForm.setTotalDisbursementAmt("key-" + j, td.getTotaDisbAmnt());
			cpForm.setAppApprovedAmt("key-" + j,
					tccg.get(ClaimConstants.CLM_APPLICATION_APPRVD_AMNT));
			cpForm.setTcprincipal("key-" + j, td.getPrincipalRepayment());
			cpForm.setTcInterestCharge("key-" + j,
					td.getInterestAndOtherCharges());
			cpForm.setTcOsAsOnDateOfNPA("key-" + j,
					td.getOutstandingAsOnDateOfNPA());
			cpForm.setTcOsAsStatedInCivilSuit("key-" + j,
					td.getOutstandingStatedInCivilSuit());
			cpForm.setTcOsAsOnLodgementOfClaim("key-" + j,
					td.getOutstandingAsOnDateOfLodgement());
			cpForm.setClaimFlagsTc("key-" + j, td.getTcClaimFlag());
			//cpForm.setSancInterestRates("key-"+i,td.getSancInterestRate());  bhu changed testing
		}
		cpForm.setTcCounter(tcCgpansVector.size());
		cpForm.setTcCgpansVector(tcCgpansVector);

		Vector wc = claimapplication.getWorkingCapitalDtlsVector(); // -----------------------(2)

		for (int m = 0; m < wc.size(); m++) {
			boolean isFound = false;
			WorkingCapitalDetail wd = (WorkingCapitalDetail) wc.get(m);
			String cg = wd.getCgpan();
			for (int n = 0; n < cgpans.size(); n++) {
				Map cgmap = (HashMap) cgpans.get(n);
				String scg = (String) cgmap.get(ClaimConstants.CLM_CGPAN);

				if (scg.equals(cg)) {
					isFound = true;
					break;
				}
			}
			if (!isFound) {
				wc.remove(m);
			}
		}

		Vector wcCgpansVector = new Vector();
		for (int i = 0; i < wc.size(); i++) {
			int j = i + 1;
			WorkingCapitalDetail wd = (WorkingCapitalDetail) wc.get(i);
			Map wccg = new HashMap();
			wccg.put(ClaimConstants.CLM_CGPAN, wd.getCgpan());
			wcCgpansVector.add(wccg);

			cpForm.setCgpandetails("key-" + j, wd.getCgpan());
			cpForm.setWcOsAsOnDateOfNPA("key-" + j,
					wd.getOutstandingAsOnDateOfNPA());
			cpForm.setWcOsAsStatedInCivilSuit("key-" + j,
					wd.getOutstandingStatedInCivilSuit());
			cpForm.setWcOsAsOnLodgementOfClaim("key-" + j,
					wd.getOutstandingAsOnDateOfLodgement());
			cpForm.setClaimFlagsWc("key-" + j, wd.getWcClaimFlag());
		}
		cpForm.setWcCounter(wcCgpansVector.size());
		cpForm.setWcCgpansVector(wcCgpansVector);

		/* For approved amount */
		ArrayList summaryList = claimapplication.getClaimSummaryDtls();

		for (int m = 0; m < summaryList.size(); m++) {
			boolean isFound = false;
			ClaimSummaryDtls summary = (ClaimSummaryDtls) summaryList.get(m);
			String cg = summary.getCgpan();
			for (int n = 0; n < cgpans.size(); n++) {
				Map cgmap = (HashMap) cgpans.get(n);
				String scg = (String) cgmap.get(ClaimConstants.CLM_CGPAN);
				if (cg.equals(scg)) {
					isFound = true;
					break;
				}
			}
			if (!isFound) {
				summaryList.remove(m);
			}
		}

		Vector cgpnDetails = new Vector();
		for (int i = 0; i < summaryList.size(); i++) {
			// int m = i + 1;
			ClaimSummaryDtls summary = (ClaimSummaryDtls) summaryList.get(i);
			Map summaryMap = new HashMap();
			summaryMap.put(ClaimConstants.CLM_CGPAN, summary.getCgpan());
			summaryMap.put(ClaimConstants.CGPAN_APPRVD_AMNT,
					summary.getLimitCoveredUnderCGFSI());
			for (int k = 0; k < cgpans.size(); k++) {
				Map tempMap2 = (HashMap) cgpans.get(k);
				if (summary.getCgpan().equals(
						tempMap2.get(ClaimConstants.CLM_CGPAN))) {
					summaryMap.put("LoanType",
							tempMap2.get(ClaimConstants.CGPAN_LOAN_TYPE));
					break;
				}
			}

			cgpnDetails.add(summaryMap);

			cpForm.setClaimSummaryDetails(summary.getCgpan(),
					summary.getAmount());
		}
		cpForm.setCgpnDetails(cgpnDetails);

		Vector recoveryModes = processor.getAllRecoveryModes();
		cpForm.setRecoveryModes(recoveryModes);
		/* RECOVERY DETAILS */
		Vector rec = claimapplication.getRecoveryDetails(); // -------------------------------(3)

		for (int m = 0; m < rec.size(); m++) {
			boolean isFound = false;
			RecoveryDetails rd = (RecoveryDetails) rec.get(m);
			String cg = rd.getCgpan();
			for (int n = 0; n < cgpans.size(); n++) {
				Map cgmap = (HashMap) cgpans.get(n);
				String scg = (String) cgmap.get(ClaimConstants.CLM_CGPAN);
				if (cg.equals(scg)) {
					isFound = true;
					break;
				}
			}
			if (!isFound) {
				rec.remove(m);
			}
		}

		Vector cgpansVector = new Vector();
		for (int i = 0; i < cgpnDetails.size(); i++) {
			int j = i + 1;

			if (rec.size() >= j) {
				RecoveryDetails rd = (RecoveryDetails) rec.get(i);
				cgpansVector.add(rd.getCgpan());

				cpForm.setCgpandetails("recovery#key-" + j, rd.getCgpan());
				cpForm.setCgpandetails("tcprincipal$recovery#key-" + j,
						String.valueOf(rd.getTcPrincipal()));
				cpForm.setCgpandetails("tcInterestCharges$recovery#key-" + j,
						String.valueOf(rd.getTcInterestAndOtherCharges()));
				cpForm.setCgpandetails("wcAmount$recovery#key-" + j,
						String.valueOf(rd.getWcAmount()));
				cpForm.setCgpandetails("wcOtherCharges$recovery#key-" + j,
						String.valueOf(rd.getWcOtherCharges()));
				cpForm.setCgpandetails("recoveryMode$recovery#key-" + j,
						String.valueOf(rd.getModeOfRecovery()));
			} else {
				for (int k = 0; k < cgpnDetails.size(); k++) {
					Map summary = (HashMap) cgpnDetails.get(k);
					String cgpan = (String) summary
							.get(ClaimConstants.CLM_CGPAN);
					if (!cgpansVector.contains(cgpan)) {
						cpForm.setCgpandetails("recovery#key-" + j, cgpan);
						cpForm.setCgpandetails("tcprincipal$recovery#key-" + j,
								"");
						cpForm.setCgpandetails(
								"tcInterestCharges$recovery#key-" + j, "");
						cpForm.setCgpandetails("wcAmount$recovery#key-" + j, "");
						cpForm.setCgpandetails("wcOtherCharges$recovery#key-"
								+ j, "");
						cpForm.setCgpandetails(
								"recoveryMode$recovery#key-" + j, "");
						cgpansVector.add(cgpan);
						break;
					}
				}
			}
		}
		cpForm.setCgpansVector(cgpansVector);
		//System.out.println("3 setCgpansVector executed");
		// cpForm.setSecurityDetails();

		/*
		 * User userInfo = getUserInformation(request); if
		 * ("F".equals(installmentFlag)) { String memid =
		 * claimapplication.getMemberId(); String bid =
		 * claimapplication.getBorrowerId();
		 * 
		 * // Vector cgpnDetails = processor.getCGPANDetailsForBorrowerId(bid,
		 * memid);
		 * 
		 * //claimapplication.setClaimSummaryDtls(clmSummryDtls);
		 * cpForm.setClaimapplication(claimapplication);
		 * 
		 * boolean internetUser = true;
		 * 
		 * if (((userInfo.getBankId() + userInfo.getZoneId() +
		 * userInfo.getBranchId()).equals("000000000000")) &&
		 * (!userInfo.getUserId().equalsIgnoreCase("DEMOUSER"))) { internetUser
		 * = false; }
		 * 
		 * Map attachments = manager.getClaimAttachments(clmrefno, "TC",
		 * internetUser);
		 * 
		 * String formattedToOSPath = null; if (attachments.get("legalDetails")
		 * != null) { UploadFileProperties uploadFile =
		 * (UploadFileProperties)attachments.get("legalDetails");
		 * 
		 * formattedToOSPath = createNewFileNew(request,
		 * uploadFile.getFileName(), uploadFile.getFileSize());
		 * 
		 * Log.log(5, "ReportsAction", "createNewFile",
		 * " Legal Details Attachment path " + formattedToOSPath);
		 * 
		 * request.setAttribute("legalDetailsAttachment", formattedToOSPath); }
		 * }
		 */

		// if (!session.isNew()) {
		// session.removeAttribute("RETURNREMARKS");
		// }

		session.setAttribute("RETURNREMARKS",
				claimapplication.getReturnRemarks());

		return mapping.findForward("success");
	}

	public ActionForward updateClaimApplication(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ClaimActionForm claimForm = (ClaimActionForm) form;
		ClaimApplication claimapplication = claimForm.getClaimapplication();
		ClaimsProcessor processor = new ClaimsProcessor();
		String nameofofficial = claimForm.getNameOfOfficial();
		String designation = claimForm.getDesignationOfOfficial();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		HashMap itpanDetails = (HashMap) claimForm.getItpanDetails();
		String claimsubmitteddt = claimForm.getClaimSubmittedDate();
		String place = claimForm.getPlace();
		// Get the borrower Id from the request object
		String borrowerId = ((claimForm.getBorrowerID()).toUpperCase()).trim();
		// //System.out.println("Borrower Id:"+borrowerId);

		claimapplication.setNameOfOfficial(nameofofficial);
		claimapplication.setDesignationOfOfficial(designation);
		claimapplication.setClaimSubmittedDate(sdf.parse(claimsubmitteddt,
				new ParsePosition(0)));
		claimapplication.setPlace(place);
		claimapplication.setFirstInstallment(true);
		/* added by sukumar@path on 13-08-2009 */

		String ifsCode = claimForm.getIfsCode();
		String neftCode = claimForm.getNeftCode();
		String rtgsBankName = claimForm.getRtgsBankName();
		String rtgsBranchName = claimForm.getRtgsBranchName();
		String rtgsBankNumber = claimForm.getRtgsBankNumber();
		String microCategory = claimForm.getMicroCategory();

		claimapplication.setIfsCode(ifsCode);
		claimapplication.setNeftCode(neftCode);
		claimapplication.setRtgsBankName(rtgsBankName);
		claimapplication.setRtgsBranchName(rtgsBranchName);
		claimapplication.setRtgsBankNumber(rtgsBankNumber);
		claimapplication.setMicroCategory(microCategory);
		String unitName = (String) claimForm.getBorrowerDetails()
				.getBorrowerName();

		/*
		 * Added on 11/10/2004 to view the uploaded attachments. Done by
		 * Veldurai
		 */
		boolean internetUser = true;
		User userInfo = getUserInformation(request);
		String userId = userInfo.getUserId();
		claimapplication.setCreatedModifiedy(userId);
		// All users belong to member id 0000 0000 0000 are intranet users
		// except demo user.
		if ((userInfo.getBankId() + userInfo.getZoneId() + userInfo
				.getBranchId()).equals("000000000000")
				&& !userInfo.getUserId().equalsIgnoreCase("DEMOUSER")) {
			internetUser = false;
		}
		// claimapplication.setMemberId(claimForm.getMemberId());
		// claimapplication.setBorrowerId(claimForm.getBorrowerID());

		/* temporarly commented the generation of claim reference number */
		// String clmRefNumber= " ";
		String clmRefNumber = processor.updateClaimApplication(
				claimapplication, itpanDetails, internetUser);
		// //System.out.println("Member Id for Claim Application:"+claimapplication.getMemberId());
		// processor.insertRecallAndLegalAttachments(claimapplication,clmRefNumber,internetUser);
		/*
		 * Addition completed.
		 */
		claimForm.setClmRefNumber(clmRefNumber);
		claimForm.setClmRefNumberNew(null);
		claimapplication.setClaimRefNumber(null);
		// claimapplication.setBorrowerId(null);
		// claimForm.setMemberId(claimapplication.getMemberId());
		HttpSession session = request.getSession();
		session.removeAttribute("RETURNREMARKS");
		claimForm.resetTheFirstClaimApplication(mapping, request);
		claimForm.setNameOfOfficial("");
		claimForm.setDesignationOfOfficial("");
		claimForm.setPlace("");
		claimForm.setForumthrulegalinitiated("");
		Log.log(Log.INFO, "ClaimAction", "saveClaimApplication", "Exited");
		request.setAttribute(
				"message",
				"Application for First Claim Installment for "
						+ borrowerId
						+ "\n has been saved successfully. Claim Reference Number is : "
						+ clmRefNumber);
		// return mapping.findForward("success");
		return mapping.findForward("submitClaim");
	}

	private String createNewFileNew(HttpServletRequest request,
			String fileName, byte[] data) {
		Log.log(4, "ReportsAction", "createNewFile", "Exited");

		String formattedToOSPath = request.getContextPath() + File.separator
				+ "Download" + File.separator + fileName;

		Log.log(5, "ReportsAction", "createNewFile", "formattedToOSPath "
				+ formattedToOSPath);
		try {
			String realPath = request.getSession(false).getServletContext()
					.getRealPath("");

			Log.log(5, "ReportsAction", "createNewFile", "realPath " + realPath);

			String contextPath = PropertyLoader.changeToOSpath(realPath);

			Log.log(5, "ReportsAction", "createNewFile", "contextPath "
					+ contextPath);

			String filePath = contextPath + File.separator + "Download"
					+ File.separator + fileName;

			Log.log(5, "ReportsAction", "createNewFile", "filePath " + filePath);

			FileOutputStream fileOutputStream = new FileOutputStream(filePath);

			fileOutputStream.write(data);
			fileOutputStream.flush();
			fileOutputStream.close();
		} catch (FileNotFoundException e) {
			Log.log(2, "ReportsAction", "createNewFile",
					"Error " + e.getMessage());
			Log.logException(e);
		} catch (Exception e) {
			Log.log(2, "ReportsAction", "createNewFile",
					"Error " + e.getMessage());
			Log.logException(e);
		}

		Log.log(4, "ReportsAction", "createNewFile", "Exited");

		return formattedToOSPath;
	}

	public Map getClaimInfo(String clmrefno) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Map map = new HashMap();
		String query = "select clm_status,mem_bnk_id||mem_zne_id||mem_brn_id,bid,clm_installment_flag,trunc(clm_date) from claim_detail_temp where clm_ref_no='"
				+ clmrefno + "'";
		String clmStatus = null;
		String clmFlag = null;
		String memberId = null;
		String bid = null;
		String installmentFlag = null;
		Date clmdate = null;
		conn = DBConnection.getConnection();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			if (rs.next()) {
				clmStatus = rs.getString(1);
				memberId = rs.getString(2);
				bid = rs.getString(3);
				installmentFlag = rs.getString(4);
				// clmFlag = rs.getString(5);
				clmdate = (java.util.Date) rs.getDate(5);
			}

			map.put("CLM_STATUS", clmStatus);
			// map.put("CLM_FLAG",clmFlag);
			map.put("MEMBERID", memberId);
			map.put("BID", bid);
			map.put("INSTALLMENTFLAG", installmentFlag);
			map.put("CLMDATE", clmdate);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				DBConnection.freeConnection(conn);
			}
		}

		return map;
	}

	public ActionForward claimFilesAttachmentInput(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ClaimActionForm claimForm = (ClaimActionForm) form;
		claimForm.setClmRefNumberNew("");
		return mapping.findForward("success");
	}

	public ActionForward getCgpanWiseDetailsForClmRefNo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws NoDataException {

		ClaimActionForm claimForm = (ClaimActionForm) form;
		String clm_ref_number = (String) claimForm.getClmRefNumberNew();

		Vector totcgs = claimForm.getCgpansVector();
		int total = 0;
		if (totcgs != null) {
			total = totcgs.size();
		}

		clearClaimForm(claimForm, total);

		// clearForm(claimForm, total);

		/* get CGPANS */
		Vector cgpans = new Vector();
		int tc_cgpans = 0;
		int wc_cgpans = 0;
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		double appAmt = 0.0;
		Date sancdt = null;
		Date npadt = null;
		Set dateset = new TreeSet();

		String q = " SELECT C2.CGPAN, SSI_UNIT_NAME, C.MEM_BNK_ID||C.MEM_ZNE_ID||C.MEM_BRN_ID, DECODE(APP_REAPPROVE_AMOUNT,NULL,APP_APPROVED_AMOUNT,APP_REAPPROVE_AMOUNT) GUARANTEE "
				+ " ,C.BID,trunc(app_sanction_dt) sancdt,TRUNC(NPA_EFFECTIVE_DT) NPADT FROM CLAIM_DETAIL_TEMP C, CLAIM_TC_DETAIL_TEMP C2, SSI_DETAIL S, APPLICATION_DETAIL A,NPA_DETAIL_TEMP N "
				+ " WHERE C.CLM_REF_NO=C2.CLM_REF_NO AND CLM_STATUS IN('RT') "
				+ " AND C.BID=S.BID AND C.BID=N.BID  AND A.CGPAN=C2.CGPAN AND C.CLM_REF_NO='"
				+ clm_ref_number
				+ "'"
				+ " UNION ALL "
				+ " SELECT C2.CGPAN, SSI_UNIT_NAME, C.MEM_BNK_ID||C.MEM_ZNE_ID||C.MEM_BRN_ID, DECODE(APP_REAPPROVE_AMOUNT,NULL,APP_APPROVED_AMOUNT,APP_REAPPROVE_AMOUNT) GUARANTEE "
				+ " ,C.BID,trunc(app_sanction_dt) sancdt,TRUNC(NPA_EFFECTIVE_DT) NPADT FROM CLAIM_DETAIL_TEMP C, CLAIM_WC_DETAIL_TEMP C2, SSI_DETAIL S, APPLICATION_DETAIL A,NPA_DETAIL_TEMP N "
				+ " WHERE C.CLM_REF_NO=C2.CLM_REF_NO AND CLM_STATUS IN('RT') "
				+ " AND C.BID=S.BID AND C.BID=N.BID  AND A.CGPAN=C2.CGPAN AND C.CLM_REF_NO='"
				+ clm_ref_number + "'";

		connection = DBConnection.getConnection();
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(q);
			while (rs.next()) {
				String cg = rs.getString(1);
				if (cg.endsWith("TC")) {
					tc_cgpans++;
				} else {
					wc_cgpans++;
				}
				cgpans.add(cg);
				claimForm.setUnitName(rs.getString(2));
				claimForm.setMemberId(rs.getString(3));
				appAmt = appAmt + (double) rs.getDouble(4);
				claimForm.setBorrowerID(rs.getString(5));
				dateset.add(rs.getDate(6));
				npadt = rs.getDate(7);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				DBConnection.freeConnection(connection);
			}
		}
		if (cgpans.size() == 0) {
			throw new NoDataException(
					"Claim is not eligible for this service or no details available.");
		}
		claimForm.setAppAmount(String.valueOf(appAmt));
		claimForm.setCgpansVector(cgpans);
		//System.out.println("4 setCgpansVector executed");
		claimForm.setTcCounter(tc_cgpans);
		claimForm.setWcCounter(wc_cgpans);

		ArrayList dates = new ArrayList(dateset);
		Collections.sort(dates, Collections.reverseOrder());
		sancdt = (Date) dates.get(0);
		long sanc_days = sancdt.getTime() / (1000 * 60 * 60 * 24);
		long npa_days = npadt.getTime() / (1000 * 60 * 60 * 24);
		
		//System.out.println("*****day difference*****" + (npa_days - sanc_days));

		int maxDays = 365;
		Calendar cal = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal.setTime(sancdt);
		cal2.setTime(npadt);
		int sancmonth = cal.get(Calendar.MONTH);
		int sancyear = cal.get(Calendar.YEAR);
		int npamonth = cal2.get(Calendar.MONTH);
		int npayear = cal2.get(Calendar.YEAR);
		
		if((npayear%4 == 0) || (sancyear%4 == 0)){
			maxDays = 366;
		}
		
		if ((npa_days - sanc_days) <= maxDays) {
			//System.out.println("QUICK_MARALITY applicable");
			request.setAttribute("QUICK_MARALITY", "Y");
		} else {
			request.setAttribute("QUICK_MARALITY", "N");
		}

		return mapping.findForward("attachmentPage");
	}

	public ActionForward uploadClaimFiles(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws DatabaseException, Exception {

		HttpSession session = request.getSession();
		User user = getUserInformation(request);
		String userID = user.getUserId();
		ClaimActionForm claimForm = (ClaimActionForm) form;
		String clmReferenceNumber = claimForm.getClmRefNumberNew();
		String bid = claimForm.getBorrowerID();

		FormFile npaReportFile = (FormFile) claimForm.getNpaReportFile();
		FormFile diligenceReportFile = (FormFile) claimForm
				.getDiligenceReportFile();
		FormFile postInspectionFile = (FormFile) claimForm
				.getPostInspectionReportFile();
		FormFile postNpaReportFile = (FormFile) claimForm
				.getPostNpaReportFile();
		String insuranceFileFlag = (String) claimForm.getInsuranceFileFlag();
		String insuranceReason = "";
		FormFile suitFile = (FormFile) claimForm.getSuitReportFile();
		FormFile finalVerdictFile = (FormFile) claimForm.getFinalVerdictFile();
		FormFile idProofFile = (FormFile) claimForm.getIdProofFile();
		FormFile otherFile = (FormFile) claimForm.getOtherFile();
		FormFile staffReportFile = (FormFile) claimForm.getStaffReportFile();//staffAccountability  use  bhu
		String bankRateType = (String) claimForm.getBankRateType();
		double plr = 0.0;
		double rate = 0.0;
		
		if(bankRateType.equals("P")){
			plr = (double)claimForm.getPlr();
		}else{
			rate = (double)claimForm.getRate();
		}
		
		String securityRemarks = (String) claimForm.getSecurityRemarks();
		String recoveryEffortsTaken = (String) claimForm
				.getRecoveryEffortsTaken();
		String rating = (String) claimForm.getRating();
		FormFile internalRatingFile = (FormFile)claimForm.getInternalRatingFile();
		String branchAddress = (String) claimForm.getBranchAddress();
		String investmentGradeFlag = "";
		if ((String) claimForm.getInvestmentGradeFlag() != null) {
			investmentGradeFlag = (String) claimForm.getInvestmentGradeFlag();
		}

		FormFile[] diligenceReportFiles = (FormFile[]) claimForm
				.getDiligenceReportFiles();
		FormFile[] postInspectionReportFiles = (FormFile[]) claimForm
				.getPostInspectionReportFiles();
		FormFile[] postNpaReportFiles = (FormFile[]) claimForm
				.getPostNpaReportFiles();
		FormFile[] idProofFiles = (FormFile[]) claimForm.getIdProofFiles();
		FormFile[] otherFiles = (FormFile[]) claimForm.getOtherFiles();

		ClaimFiles file = new ClaimFiles();
		file.setNpaReportFile(npaReportFile);
		/*
		 * file.setDiligenceReportFile(diligenceReportFile);
		 * file.setPostInspectionReportFile(postInspectionFile);
		 * file.setPostNpaReportFile(postNpaReportFile);
		 */
		file.setDiligenceReportFiles(diligenceReportFiles);
		file.setPostInspectionReportFiles(postInspectionReportFiles);
		file.setPostNpaReportFiles(postNpaReportFiles);
		file.setIdProofFiles(idProofFiles);
		file.setOtherFiles(otherFiles);

		file.setPlr(plr);
		file.setRate(rate);
		
		file.setInsuranceFileFlag(insuranceFileFlag);
		if ("N".equals(insuranceFileFlag))
			insuranceReason = (String) claimForm.getInsuranceReason();
		file.setInsuranceReason(insuranceReason);
		file.setSuitReportFileFile(suitFile);
		file.setFinalVerdictFile(finalVerdictFile);
		/*
		 * file.setIdProofFile(idProofFile); file.setOtherFile(otherFile);
		 */
		file.setStaffReportFile(staffReportFile);
		file.setBankRateType(bankRateType);
		file.setSecurityRemarks(securityRemarks);
		file.setRecoveryEffortsTaken(recoveryEffortsTaken);
		file.setRating(rating);
		file.setInternalRatingFile(internalRatingFile);
		file.setBranchAddress(branchAddress);
		file.setInvestmentGradeFlag(investmentGradeFlag);

		int total = Integer.parseInt(request.getParameter("total_cgpans"));
		ArrayList claimFiles = new ArrayList();

		for (int i = 0; i < total; i++) {
			ClaimFiles files = new ClaimFiles();
			String key = "key-" + i;
			String key1 = "key-" + i + "1";
			String key2 = "key-" + i + "2";
			String key3 = "principal-key-" + i;
			String key4 = "interest-key-" + i;
			String key5 = "key-" + i + "-flag";

			String cgpan = (String) claimForm.getCgpans(key);

			FormFile[] statementFile = new FormFile[3];
			statementFile[0] = (FormFile) claimForm
					.getStatementReportFiles(key);
			statementFile[1] = (FormFile) claimForm
					.getStatementReportFiles(key1);
			statementFile[2] = (FormFile) claimForm
					.getStatementReportFiles(key2);

			FormFile[] appraisalReportFile = new FormFile[3];
			String isSameApprFile = (String) claimForm
					.getAppraisalReportFiles(key5);
			if (i > 0 && "Y".equals(isSameApprFile)) {
				files.setIsSameAppraisalFile("Y");
				appraisalReportFile[0] = null;
				appraisalReportFile[1] = null;
				appraisalReportFile[2] = null;
			} else {
				files.setIsSameAppraisalFile("N");
				appraisalReportFile[0] = (FormFile) claimForm
						.getAppraisalReportFiles(key);
				appraisalReportFile[1] = (FormFile) claimForm
						.getAppraisalReportFiles(key1);
				appraisalReportFile[2] = (FormFile) claimForm
						.getAppraisalReportFiles(key2);
			}

			FormFile[] sanctionLetterFile = new FormFile[3];
			String isSameSancFile = (String) claimForm
					.getSanctionLetterFiles(key5);
			if (i > 0 && "Y".equals(isSameSancFile)) {
				files.setIsSameSanctionFile("Y");
				sanctionLetterFile[0] = null;
				sanctionLetterFile[1] = null;
				sanctionLetterFile[2] = null;
			} else {
				files.setIsSameSanctionFile("N");
				sanctionLetterFile[0] = (FormFile) claimForm
						.getSanctionLetterFiles(key);
				sanctionLetterFile[1] = (FormFile) claimForm
						.getSanctionLetterFiles(key1);
				sanctionLetterFile[2] = (FormFile) claimForm
						.getSanctionLetterFiles(key2);
			}

			FormFile[] complianceReportFile = new FormFile[3];
			String isSameCompFile = (String) claimForm
					.getComplianceReportFiles(key5);
			if (i > 0 && "Y".equals(isSameCompFile)) {
				files.setIsSameComplianceFile("Y");
				complianceReportFile[0] = null;
				complianceReportFile[1] = null;
				complianceReportFile[2] = null;
			} else {
				files.setIsSameComplianceFile("N");
				complianceReportFile[0] = (FormFile) claimForm
						.getComplianceReportFiles(key);
				complianceReportFile[1] = (FormFile) claimForm
						.getComplianceReportFiles(key1);
				complianceReportFile[2] = (FormFile) claimForm
						.getComplianceReportFiles(key2);
			}

			FormFile[] preInspectionFile = new FormFile[3];
			String isSameInspFile = (String) claimForm
					.getPreInspectionReportFiles(key5);
			if (i > 0 && "Y".equals(isSameInspFile)) {
				files.setIsSamePreInspectionFile("Y");
				preInspectionFile[0] = null;
				preInspectionFile[1] = null;
				preInspectionFile[2] = null;
			} else {
				files.setIsSamePreInspectionFile("N");
				preInspectionFile[0] = (FormFile) claimForm
						.getPreInspectionReportFiles(key);
				preInspectionFile[1] = (FormFile) claimForm
						.getPreInspectionReportFiles(key1);
				preInspectionFile[2] = (FormFile) claimForm
						.getPreInspectionReportFiles(key2);
			}

			FormFile[] insuranceCopyFile = new FormFile[3];
			String isSameInsuranceFile = (String) claimForm
					.getInsuranceCopyFiles(key5);
			if (i > 0 && "Y".equals(isSameInsuranceFile)) {
				files.setIsSameInsuranceFile("Y");
				insuranceCopyFile[0] = null;
				insuranceCopyFile[1] = null;
				insuranceCopyFile[2] = null;
			} else {
				files.setIsSameInsuranceFile("N");
				insuranceCopyFile[0] = (FormFile) claimForm
						.getInsuranceCopyFiles(key);
				insuranceCopyFile[1] = (FormFile) claimForm
						.getInsuranceCopyFiles(key1);
				insuranceCopyFile[2] = (FormFile) claimForm
						.getInsuranceCopyFiles(key2);
			}

			double principalRepayAmt = 0.0;
			double interestRepayAmt = 0.0;
			if (cgpan.endsWith("TC")) {
				principalRepayAmt = Double.parseDouble((String) claimForm
						.getRepayBeforeNpaAmts(key3));
				interestRepayAmt = Double.parseDouble((String) claimForm
						.getRepayBeforeNpaAmts(key4));
			}
			double principalRecoveryAmt = Double.parseDouble((String) claimForm
					.getRecoveryAfterNpaAmts(key3));
			double interestRecoveryAmt = Double.parseDouble((String) claimForm
					.getRecoveryAfterNpaAmts(key4));
			double interestRate = Double.parseDouble((String) claimForm
					.getInterestRates(key));

			files.setCgpan(cgpan);
			files.setStatementFile(statementFile);
			files.setAppraisalReportFile(appraisalReportFile);
			files.setSanctionLetterFile(sanctionLetterFile);
			files.setComplianceFile(complianceReportFile);
			files.setPreInspectionFile(preInspectionFile);
			if ("Y".equals(insuranceFileFlag)) {
				files.setInsuranceCopyFile(insuranceCopyFile);
			} else {
				files.setInsuranceCopyFile(null);
			}
			files.setPrincipalRepayBeforeNpaAmts(principalRepayAmt);
			files.setInterestRepayBeforeNpaAmts(interestRepayAmt);
			files.setPrincipalRecoveryAfterNpaAmts(principalRecoveryAmt);
			files.setInterestRecoveryAfterNpaAmts(interestRecoveryAmt);
			files.setInterestRate(interestRate);

			claimFiles.add(files);
		}

		/* set cgpan wise files */

		/* call data base procedure here */
		ClaimsProcessor processor = new ClaimsProcessor();
		try {
			processor.uploadClaimFiles(clmReferenceNumber, bid, userID,
					claimFiles, file);
		} catch (DatabaseException e) {
			e.printStackTrace();
			throw new DatabaseException("Problem in Uploading Files."+e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}

		/* AFTER SUCCESSFUL INSERTION INTO DATABASE UPLOAD FILEs INTO SERVER */
		String contextPath = request.getSession().getServletContext()
				.getRealPath("");
		try {
			String npa_file_name = "NPA_FILE_" + bid + "_"
					+ file.getNpaReportFile().getFileName().trim();
			uploadFile(file.getNpaReportFile(), contextPath, npa_file_name);

			String diligence_file_name = "";
			if (file.getDiligenceReportFiles()[0] != null)
				if (!(file.getDiligenceReportFiles()[0].getFileName()
						.equals(""))) {
					diligence_file_name = "DILIGENCE_"
							+ bid
							+ "_"
							+ file.getDiligenceReportFiles()[0].getFileName()
									.trim();
					uploadFile(file.getDiligenceReportFiles()[0], contextPath,
							diligence_file_name);
				}
			String diligence_file_name1 = "";
			if (file.getDiligenceReportFiles()[1] != null)
				if (!(file.getDiligenceReportFiles()[1].getFileName()
						.equals(""))) {
					diligence_file_name1 = "DILIGENCE1_"
							+ bid
							+ "_"
							+ file.getDiligenceReportFiles()[1].getFileName()
									.trim();
					uploadFile(file.getDiligenceReportFiles()[1], contextPath,
							diligence_file_name1);
				}
			String diligence_file_name2 = "";
			if (file.getDiligenceReportFiles()[2] != null)
				if (!(file.getDiligenceReportFiles()[2].getFileName()
						.equals(""))) {
					diligence_file_name2 = "DILIGENCE2_"
							+ bid
							+ "_"
							+ file.getDiligenceReportFiles()[2].getFileName()
									.trim();
					uploadFile(file.getDiligenceReportFiles()[2], contextPath,
							diligence_file_name2);
				}

			String post_insp_file_name = "";
			if (file.getPostInspectionReportFiles()[0] != null)
				if (!(file.getPostInspectionReportFiles()[0].getFileName()
						.equals(""))) {
					post_insp_file_name = "POST_INSPEC_"
							+ bid
							+ "_"
							+ file.getPostInspectionReportFiles()[0]
									.getFileName().trim();
					uploadFile(file.getPostInspectionReportFiles()[0],
							contextPath, post_insp_file_name);
				}
			String post_insp_file_name1 = "";
			if (file.getPostInspectionReportFiles()[1] != null)
				if (!(file.getPostInspectionReportFiles()[1].getFileName()
						.equals(""))) {
					post_insp_file_name1 = "POST_INSPEC1_"
							+ bid
							+ "_"
							+ file.getPostInspectionReportFiles()[1]
									.getFileName().trim();
					uploadFile(file.getPostInspectionReportFiles()[1],
							contextPath, post_insp_file_name1);
				}
			String post_insp_file_name2 = "";
			if (file.getPostInspectionReportFiles()[2] != null)
				if (!(file.getPostInspectionReportFiles()[2].getFileName()
						.equals(""))) {
					post_insp_file_name2 = "POST_INSPEC2_"
							+ bid
							+ "_"
							+ file.getPostInspectionReportFiles()[2]
									.getFileName().trim();
					uploadFile(file.getPostInspectionReportFiles()[2],
							contextPath, post_insp_file_name2);
				}

			String post_npa_file_name = "";
			if (file.getPostNpaReportFiles()[0] != null)
				if (!(file.getPostNpaReportFiles()[0].getFileName().equals(""))) {
					post_npa_file_name = "POST_NPA_"
							+ bid
							+ "_"
							+ file.getPostNpaReportFiles()[0].getFileName()
									.trim();
					uploadFile(file.getPostNpaReportFiles()[0], contextPath,
							post_npa_file_name);
				}
			String post_npa_file_name1 = "";
			if (file.getPostNpaReportFiles()[1] != null)
				if (!(file.getPostNpaReportFiles()[1].getFileName().equals(""))) {
					post_npa_file_name1 = "POST_NPA1_"
							+ bid
							+ "_"
							+ file.getPostNpaReportFiles()[1].getFileName()
									.trim();
					uploadFile(file.getPostNpaReportFiles()[1], contextPath,
							post_npa_file_name1);
				}
			String post_npa_file_name2 = "";
			if (file.getPostNpaReportFiles()[2] != null)
				if (!(file.getPostNpaReportFiles()[2].getFileName().equals(""))) {
					post_npa_file_name2 = "POST_NPA2_"
							+ bid
							+ "_"
							+ file.getPostNpaReportFiles()[2].getFileName()
									.trim();
					uploadFile(file.getPostNpaReportFiles()[2], contextPath,
							post_npa_file_name2);
				}

			String suit_file_name = "SUIT_" + bid + "_"
					+ file.getSuitReportFileFile().getFileName().trim();
			uploadFile(file.getSuitReportFileFile(), contextPath,
					suit_file_name);

			String verdict_file_name = "";
			if (file.getFinalVerdictFile() != null)
				if (!(file.getFinalVerdictFile().getFileName().equals(""))) {
					verdict_file_name = "VERDICT_" + bid + "_"
							+ file.getFinalVerdictFile().getFileName().trim();
					uploadFile(file.getFinalVerdictFile(), contextPath,
							verdict_file_name);
				}

			String id_file_name = "";
			if (file.getIdProofFiles()[0] != null)
				if (!(file.getIdProofFiles()[0].getFileName().equals(""))) {
					id_file_name = "IDPROOF_" + bid + "_"
							+ file.getIdProofFiles()[0].getFileName().trim();
					uploadFile(file.getIdProofFiles()[0], contextPath,
							id_file_name);
				}
			String id_file_name1 = "";
			if (file.getIdProofFiles()[1] != null)
				if (!(file.getIdProofFiles()[1].getFileName().equals(""))) {
					id_file_name1 = "IDPROOF1_" + bid + "_"
							+ file.getIdProofFiles()[1].getFileName().trim();
					uploadFile(file.getIdProofFiles()[1], contextPath,
							id_file_name1);
				}
			String id_file_name2 = "";
			if (file.getIdProofFiles()[2] != null)
				if (!(file.getIdProofFiles()[2].getFileName().equals(""))) {
					id_file_name2 = "IDPROOF2_" + bid + "_"
							+ file.getIdProofFiles()[2].getFileName().trim();
					uploadFile(file.getIdProofFiles()[2], contextPath,
							id_file_name2);
				}

			String other_file_name = "";
			if (file.getOtherFiles()[0] != null)
				if (!(file.getOtherFiles()[0].getFileName().equals(""))) {
					other_file_name = "OTHER_" + bid + "_"
							+ file.getOtherFiles()[0].getFileName().trim();
					uploadFile(file.getOtherFiles()[0], contextPath,
							other_file_name);
				}
			String other_file_name1 = "";
			if (file.getOtherFiles()[1] != null)
				if (!(file.getOtherFiles()[1].getFileName().equals(""))) {
					other_file_name1 = "OTHER1_" + bid + "_"
							+ file.getOtherFiles()[1].getFileName().trim();
					uploadFile(file.getOtherFiles()[1], contextPath,
							other_file_name1);
				}
			String other_file_name2 = "";
			if (file.getOtherFiles()[2] != null)
				if (!(file.getOtherFiles()[2].getFileName().equals(""))) {
					other_file_name2 = "OTHER2_" + bid + "_"
							+ file.getOtherFiles()[2].getFileName().trim();
					uploadFile(file.getOtherFiles()[2], contextPath,
							other_file_name2);
				}

			String staff_report_name = "";
			if (file.getStaffReportFile() != null)
				if (!(file.getStaffReportFile().getFileName().equals(""))) {
					staff_report_name = "STAFF_" + bid + "_"
							+ file.getStaffReportFile().getFileName().trim();
					uploadFile(file.getStaffReportFile(), contextPath,
							staff_report_name);
				}
			
			String internal_rating_file_name = "";
			if(file.getInternalRatingFile() != null){
				if(!(file.getInternalRatingFile().getFileName().equals(""))){
					internal_rating_file_name = "IR_" + bid +"_" + file.getInternalRatingFile().getFileName().trim();
					uploadFile(file.getInternalRatingFile(), contextPath,
							internal_rating_file_name);
				}
			}

		} catch (Exception e1) {
			e1.printStackTrace();
		}

		/*
		 * for (int i = 0; i < claimFiles.size(); i++) { // ClaimFiles claimFile
		 * = (ClaimFiles)itr.next(); ClaimFiles claimFile = (ClaimFiles)
		 * claimFiles.get(i); String cgpan = claimFile.getCgpan(); FormFile
		 * statementFile = claimFile.getStatementFile(); FormFile appraisalFile
		 * = claimFile.getAppraisalReportFile(); FormFile sanctionFile =
		 * claimFile.getSanctionLetterFile(); FormFile complianceFile =
		 * claimFile.getComplianceFile(); FormFile preinspectionFile =
		 * claimFile.getPreInspectionFile(); FormFile insuranceCopyFile =
		 * claimFile.getInsuranceCopyFile();
		 * 
		 * statement_file_name = statement_file_name + i + "_" + bid + "_" +
		 * statementFile.getFileName(); appraisal_file_name =
		 * appraisal_file_name + i + "_" + bid + "_" +
		 * appraisalFile.getFileName(); sanction_letter_name =
		 * sanction_letter_name + i + "_" + bid + "_" +
		 * sanctionFile.getFileName(); compliance_file_name =
		 * compliance_file_name + i + "_" + bid + "_" +
		 * complianceFile.getFileName(); preinspection_file_name =
		 * preinspection_file_name + i + "_" + bid + "_" +
		 * preinspectionFile.getFileName(); insurancecopy_name =
		 * insurancecopy_name + i + "_" + bid + "_" +
		 * insuranceCopyFile.getFileName(); }
		 */

		for (int i = 0; i < claimFiles.size(); i++) {
			ClaimFiles claimFile = (ClaimFiles) claimFiles.get(i);
			String cgpan = claimFile.getCgpan();
			FormFile[] statementFile = claimFile.getStatementFile();
			FormFile[] appraisalFile = claimFile.getAppraisalReportFile();
			FormFile[] sanctionFile = claimFile.getSanctionLetterFile();
			FormFile[] complianceFile = claimFile.getComplianceFile();
			FormFile[] preinspectionFile = claimFile.getPreInspectionFile();
			FormFile[] insuranceCopyFile = claimFile.getInsuranceCopyFile();

			String statement_file_name = "";
			String statement_file_name1 = "";
			String statement_file_name2 = "";
			String appraisal_file_name = "";
			String appraisal_file_name1 = "";
			String appraisal_file_name2 = "";
			String sanction_letter_name = "";
			String sanction_letter_name1 = "";
			String sanction_letter_name2 = "";
			String compliance_file_name = "";
			String compliance_file_name1 = "";
			String compliance_file_name2 = "";
			String preinspection_file_name = "";
			String preinspection_file_name1 = "";
			String preinspection_file_name2 = "";
			String insurancecopy_name = "";
			String insurancecopy_name1 = "";
			String insurancecopy_name2 = "";

			statement_file_name = "STMT_" + i + "_" + bid + "_"
					+ statementFile[0].getFileName();
			uploadFile(statementFile[0], contextPath, statement_file_name);
			if (statementFile[1] != null)
				if (!(statementFile[1].getFileName().equals(""))) {
					statement_file_name1 = "STMT1_" + i + "_" + bid + "_"
							+ statementFile[1].getFileName();
					uploadFile(statementFile[1], contextPath,
							statement_file_name1);
				}
			if (statementFile[2] != null)
				if (!(statementFile[2].getFileName().equals(""))) {
					statement_file_name2 = "STMT2_" + i + "_" + bid + "_"
							+ statementFile[2].getFileName();
					uploadFile(statementFile[2], contextPath,
							statement_file_name2);
				}

			if (appraisalFile[0] != null)
				if (!(appraisalFile[0].getFileName().equals(""))) {
					appraisal_file_name = "APPSL_" + i + "_" + bid + "_"
							+ appraisalFile[0].getFileName();
					uploadFile(appraisalFile[0], contextPath,
							appraisal_file_name);
				}
			if (appraisalFile[1] != null)
				if (!(appraisalFile[1].getFileName().equals(""))) {
					appraisal_file_name1 = "APPSL1_" + i + "_" + bid + "_"
							+ appraisalFile[1].getFileName();
					uploadFile(appraisalFile[1], contextPath,
							appraisal_file_name1);
				}
			if (appraisalFile[2] != null)
				if (!(appraisalFile[2].getFileName().equals(""))) {
					appraisal_file_name2 = "APPSL2_" + i + "_" + bid + "_"
							+ appraisalFile[2].getFileName();
					uploadFile(appraisalFile[2], contextPath,
							appraisal_file_name2);
				}

			if (sanctionFile[0] != null)
				if (!(sanctionFile[0].getFileName().equals(""))) {
					sanction_letter_name = "SANC_" + i + "_" + bid + "_"
							+ sanctionFile[0].getFileName();
					uploadFile(sanctionFile[0], contextPath,
							sanction_letter_name);
				}
			if (sanctionFile[1] != null)
				if (!(sanctionFile[1].getFileName().equals(""))) {
					sanction_letter_name1 = "SANC1_" + i + "_" + bid + "_"
							+ sanctionFile[1].getFileName();
					uploadFile(sanctionFile[1], contextPath,
							sanction_letter_name1);
				}
			if (sanctionFile[2] != null)
				if (!(sanctionFile[2].getFileName().equals(""))) {
					sanction_letter_name2 = "SANC2_" + i + "_" + bid + "_"
							+ sanctionFile[2].getFileName();
					uploadFile(sanctionFile[2], contextPath,
							sanction_letter_name2);
				}

			if (complianceFile[0] != null)
				if (!(complianceFile[0].getFileName().equals(""))) {
					compliance_file_name = "COMPL_" + i + "_" + bid + "_"
							+ complianceFile[0].getFileName();
					uploadFile(complianceFile[0], contextPath,
							compliance_file_name);
				}
			if (complianceFile[1] != null)
				if (!(complianceFile[1].getFileName().equals(""))) {
					compliance_file_name1 = "COMPL1_" + i + "_" + bid
							+ "_" + complianceFile[1].getFileName();
					uploadFile(complianceFile[1], contextPath,
							compliance_file_name1);
				}
			if (complianceFile[2] != null)
				if (!(complianceFile[2].getFileName().equals(""))) {
					compliance_file_name2 = "COMPL2_" + i + "_" + bid
							+ "_" + complianceFile[2].getFileName();
					uploadFile(complianceFile[2], contextPath,
							compliance_file_name2);
				}

			if (preinspectionFile[0] != null)
				if (!(preinspectionFile[0].getFileName().equals(""))) {
					preinspection_file_name = "PRE_INSPEC_" + i + "_" + bid
							+ "_" + preinspectionFile[0].getFileName();
					uploadFile(preinspectionFile[0], contextPath,
							preinspection_file_name);
				}
			if (preinspectionFile[1] != null)
				if (!(preinspectionFile[1].getFileName().equals(""))) {
					preinspection_file_name1 = "PRE_INSPEC1_" + i + "_"
							+ bid + "_" + preinspectionFile[1].getFileName();
					uploadFile(preinspectionFile[1], contextPath,
							preinspection_file_name1);
				}
			if (preinspectionFile[2] != null)
				if (!(preinspectionFile[2].getFileName().equals(""))) {
					preinspection_file_name2 = "PRE_INSPEC2_" + i + "_"
							+ bid + "_" + preinspectionFile[2].getFileName();
					uploadFile(preinspectionFile[2], contextPath,
							preinspection_file_name2);
				}

			if (file.getInsuranceFileFlag().equals("Y")) {
				if (insuranceCopyFile[0] != null)
					if (!(insuranceCopyFile[0].getFileName().equals(""))) {
						insurancecopy_name = "INSURANCE_" + i + "_" + bid
								+ "_" + insuranceCopyFile[0].getFileName();
						uploadFile(insuranceCopyFile[0], contextPath,
								insurancecopy_name);
					}
				if (insuranceCopyFile[1] != null)
					if (!(insuranceCopyFile[1].getFileName().equals(""))) {
						insurancecopy_name1 = "INSURANCE1_" + i + "_"
								+ bid + "_"
								+ insuranceCopyFile[1].getFileName();
						uploadFile(insuranceCopyFile[1], contextPath,
								insurancecopy_name1);
					}
				if (insuranceCopyFile[2] != null)
					if (!(insuranceCopyFile[2].getFileName().equals(""))) {
						insurancecopy_name2 = "INSURANCE2_" + i + "_"
								+ bid + "_"
								+ insuranceCopyFile[2].getFileName();
						uploadFile(insuranceCopyFile[2], contextPath,
								insurancecopy_name2);
					}
			}

			clearClaimForm(claimForm, total);
		}

		return mapping.findForward("success");
	}

	public void clearClaimForm(ClaimActionForm claimForm, int total) {

		claimForm.setInsuranceFileFlag("");
		claimForm.setInsuranceReason("");
		if (claimForm.getRepayBeforeNpaAmts() != null)
			claimForm.getRepayBeforeNpaAmts().clear();
		if (claimForm.getRecoveryAfterNpaAmts() != null)
			claimForm.getRecoveryAfterNpaAmts().clear();
		claimForm.setBankRateType("");
		if (claimForm.getInterestRates() != null)
			claimForm.getInterestRates().clear();
		claimForm.setSecurityRemarks("");
		claimForm.setRecoveryEffortsTaken("");
		claimForm.setRating("");
		claimForm.setBranchAddress("");
		claimForm.setInvestmentGradeFlag("");
		claimForm.setPlr(0.0);
		claimForm.setRate(0.0);
		
		for (int i = 0; i < total; i++) {
			String key = "key-" + i;
			String key1 = "key-" + i + "1";
			String key2 = "key-" + i + "2";
			String key3 = "principal-key-" + i;
			String key4 = "interest-key-" + i;
			String key5 = "key-" + i + "-flag";

			String cgpan = (String) claimForm.getCgpans(key);
			if (i > 0) {
				claimForm.setAppraisalReportFiles(key5, null);
				claimForm.setSanctionLetterFiles(key5, null);
				claimForm.setComplianceReportFiles(key5, null);
				claimForm.setPreInspectionReportFiles(key5, null);
				claimForm.setInsuranceCopyFiles(key5, null);
			}
			
		}
	}
	
	
	
	public ActionForward displayClaimProcessingSubmitDU(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
     {

   Log.log(Log.INFO, "ClaimAction", "displayClaimProcessingInput", "Entered");
   Connection connection = DBConnection.getConnection();
	 //System.out.println("connection success fully");
	 HttpSession session = request.getSession();
	 PreparedStatement claimsubmitduStmt;
   ResultSet claimsubmitduResult;
	User user = (User)getUserInformation(request);
	String userid = user.getUserId();
	
	System.out.println("user"+user);
	String bankid = (user.getBankId()).trim();
	String zoneid = (user.getZoneId()).trim();
	String branchid = (user.getBranchId()).trim();
	String memberId = bankid + zoneid + branchid;
  SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
   Log.log(Log.INFO, "ClaimAction", "displayClaimProcessingInput", "Exited");
   ArrayList claimArray = new ArrayList();
 
   ClaimActionForm claimFormobj = (ClaimActionForm)form;
   
   try { 
  	 
/* String query="select distinct clm_date,clm_ref_no,decode(APP_MLI_BRANCH_NAME,null,mem_branch_name,APP_MLI_BRANCH_NAME) branch,ssi_unit_name,sum(DECODE (a.app_reapprove_amount,NULL, a.app_approved_amount, a.app_reapprove_amount))" +
  			"GuaAppAmt from claim_detail_temp c,application_detail a,ssi_detail s,member_info m " +
  			"where a.ssi_reference_number=S.SSI_REFERENCE_NUMBER and s.bid=c.bid and m.mem_bnk_id||m.mem_zne_id||m.mem_brn_id=a.mem_bnk_id||a.mem_zne_id||a.mem_brn_id " +
  			"and c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id  IN ('002200100000','000400400000','002501410000','000400390000','001900140000','000400020000') AND CLM_STATUS='NE'  AND CLM_DECL_RECVD_DT IS NULL and CLM_CREATED_MODIFIED_BY='DEMOUSER'" +
  			"group by  clm_date,clm_ref_no,decode(APP_MLI_BRANCH_NAME,null,mem_branch_name,APP_MLI_BRANCH_NAME),ssi_unit_name order by 4 desc";
		*/
  
 String query="select distinct clm_date,clm_ref_no,decode(APP_MLI_BRANCH_NAME,null,mem_branch_name,APP_MLI_BRANCH_NAME) branch,ssi_unit_name,sum(DECODE (a.app_reapprove_amount,NULL, a.app_approved_amount, a.app_reapprove_amount))" +
	"GuaAppAmt from claim_detail_temp c,application_detail a,ssi_detail s,member_info m " +
	"where a.ssi_reference_number=S.SSI_REFERENCE_NUMBER and s.bid=c.bid and m.mem_bnk_id||m.mem_zne_id||m.mem_brn_id=a.mem_bnk_id||a.mem_zne_id||a.mem_brn_id " +
	"and c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id='"+memberId+"'  AND CLM_STATUS='NE'  AND CLM_DECL_RECVD_DT IS NULL  AND CLM_DATE >= '01-DEC-2016' " +
	"group by  clm_date,clm_ref_no,decode(APP_MLI_BRANCH_NAME,null,mem_branch_name,APP_MLI_BRANCH_NAME),ssi_unit_name order by 4 desc";

  	/*String query ="select distinct clm_date,clm_ref_no,decode(APP_MLI_BRANCH_NAME,null,mem_branch_name,APP_MLI_BRANCH_NAME) branch,ssi_unit_name,sum(DECODE (a.app_reapprove_amount,NULL, a.app_approved_amount, a.app_reapprove_amount)) " +
				"GuaAppAmt from claim_detail_temp c,application_detail a,ssi_detail s,member_info m " +
				"where a.ssi_reference_number=S.SSI_REFERENCE_NUMBER and s.bid=c.bid and m.mem_bnk_id||m.mem_zne_id||m.mem_brn_id=a.mem_bnk_id||a.mem_zne_id||a.mem_brn_id " +
				"and c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id IN ('001900140000','000400020000','001700300000','000400400000','002200100000','000400390000','002501410000') AND CLM_STATUS='NE'  AND CLM_DECL_RECVD_DT IS NULL  group by  clm_date,clm_ref_no,decode(APP_MLI_BRANCH_NAME,null,mem_branch_name,APP_MLI_BRANCH_NAME),ssi_unit_name order by 4 desc";*/
     //System.out.println("rajjjj"+query);
  
		claimsubmitduStmt = connection.prepareStatement(query);
		claimsubmitduResult = claimsubmitduStmt.executeQuery();
		    Date claimlodgeddate;
			 while (claimsubmitduResult.next()) {
			 ClaimActionForm claimForm = new ClaimActionForm();
			 
			 claimlodgeddate= claimsubmitduResult.getDate(1);	
			
			 claimForm.setDateOfTheDocument36(dateFormat.format(claimlodgeddate));
		
			
			 claimForm.setClmreferenceNumber(claimsubmitduResult.getString(2));
			 
		
			 claimForm.setBranchname(claimsubmitduResult.getString(3));
			
			 claimForm.setUnitname(claimsubmitduResult.getString(4));
		
			 claimForm.setGuranteeApprovedamt(claimsubmitduResult.getDouble(5));
					 			 
			 GeneralReport generalReport = new GeneralReport();
			 String claimRefNum = claimsubmitduResult.getString(2);
			 generalReport.setClaimRefNum(claimRefNum);
		     claimArray.add(claimForm);
	
		}
			 claimFormobj.setClaimDandU(claimArray);
	    } 
	    catch (Exception exception){
		Log.logException(exception);
		throw new DatabaseException(exception.getMessage());
		}
	    finally 
	    {
		DBConnection.freeConnection(connection);
		}
	

	   return mapping.findForward("displayClaimsApprovalPage");

}
	
	//for view
	 public ActionForward claimDeclarationView(ActionMapping mapping, 
	            ActionForm form, 
	            HttpServletRequest request, 
	            HttpServletResponse response) throws Exception {
 
   HttpSession session = request.getSession();
  // System.out.println("<br>===552===");
   ClaimActionForm claimFormobj = (ClaimActionForm)form;
   // System.out.println("<br>===554===");
   User user = getUserInformation(request);
   String bankId = user.getBankId();
   String zoneId = user.getZoneId();
   String branchId = user.getBranchId();
   String memberId = bankId.concat(zoneId).concat(branchId);
   System.out.println("rajukonkati"+memberId);
 
   String clmRefNumber = (String)request.getParameter("clmrefnum");
   System.out.println("rajuk"+clmRefNumber);
   ArrayList claimViewArray = new ArrayList();
   ArrayList claimCheckListView = new ArrayList();
   Connection connection = DBConnection.getConnection();
   ResultSet claimviewResult;
	PreparedStatement claimviewStmt;
	ResultSet claimviewResult1;
	PreparedStatement claimviewStmt1;
  
	
	try { 

   String Query1="SELECT C.CLM_REF_NO,A.CGPAN,SSI_UNIT_NAME,decode(nvl(app_reapprove_amount,0),0,app_approved_amount," +
   			"app_reapprove_amount) APPAMT FROM CLAIM_DETAIL_TEMP C,APPLICATION_DETAIL A,SSI_DETAIL S,claim_wc_detail_temp cw" +
   			" WHERE C.BID = S.BID AND S.SSI_REFERENCE_NUMBER = A.SSI_REFERENCE_NUMBER and c.clm_ref_no = cw.clm_ref_no" +
   			" and a.cgpan = cw.cgpan AND CLM_STATUS IN ('NE')and c.clm_ref_no=? union all SELECT C.CLM_REF_NO," +
   			"A.CGPAN,SSI_UNIT_NAME,decode(nvl(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount) APPAMT" +
   			" FROM CLAIM_DETAIL_TEMP C,APPLICATION_DETAIL A,SSI_DETAIL S,claim_Tc_detail_temp ct WHERE C.BID = S.BID AND " +
   			"S.SSI_REFERENCE_NUMBER = A.SSI_REFERENCE_NUMBER and c.clm_ref_no = ct.clm_ref_no and a.cgpan = ct.cgpan " +
   			"AND CLM_STATUS IN ('NE') and c.clm_ref_no=?";

   claimviewStmt = connection.prepareStatement(Query1);
	    claimviewStmt.setString(1, clmRefNumber);
	    claimviewStmt.setString(2, clmRefNumber);
   claimviewResult = claimviewStmt.executeQuery();
   
   while(claimviewResult.next()){
   	 
   	ClaimDetail claimviewDetails=new ClaimDetail();
		claimviewDetails.setClaimRefNum((claimviewResult.getString(1)));
		claimviewDetails.setCgpan(claimviewResult.getString(2));
		claimviewDetails.setSsiUnitName(claimviewResult.getString(3));
		claimviewDetails.setApprovedClaimAmount(claimviewResult.getDouble(4));
		
       claimViewArray.add(claimviewDetails);
   }

    claimviewResult.close();
    claimviewStmt.close();
   
   String Query2="select IS_ELIG_ACT,IS_ELIG_ACT_COMM,WHET_CIBIL_DONE,WHET_CIBIL_DONE_COMM,IS_RAT_AS_PER_CGS,IS_RAT_AS_PER_CGS_COMM,IS_THIRD_COLLAT_TAKEN," +
   		"IS_THIRD_COLLAT_TAKEN_COMM,IS_NPA_DT_AS_PER_GUID,IS_NPA_DT_AS_PER_GUID_COMM,IS_CLM_OS_WRT_NPA_DT,IS_CLM_OS_WRT_NPA_DT_COMM,WHET_SERIOUS_DEFIC_INVOL," +
   		"WHET_SERIOUS_DEFIC_INVOL_COMM,WHET_MAJOR_DEFIC_INVOLVD,WHET_MAJOR_DEFIC_INVOLVD_COMM," +
   		"WHET_DEFIC_INVOL_BY_STAFF,WHET_DEFIC_INVOL_BY_STAFF_COMM,IS_INTERN_RAT_INVEST_GRAD," +
   		"IS_INTERN_RAT_INVEST_GRAD_COMM,IS_ALL_REC_IN_CLM_FORM,IS_ALL_REC_IN_CLM_FORM_COMM " +
   		"from claim_check_list a ,claim_detail_temp b where a.clm_ref_no=b.clm_ref_no and a.clm_ref_no=?"+"AND CLM_STATUS IN ('NE') and a.clm_ref_no=?";
   
  
        claimviewStmt1 = connection.prepareStatement(Query2);
   	 claimviewStmt1.setString(1, clmRefNumber);
   	 claimviewStmt1.setString(2, clmRefNumber);
        claimviewResult1 = claimviewStmt1.executeQuery();
	    
        while(claimviewResult1.next()){
       		    	 
        	ClaimDetail claimviewDetailss=new ClaimDetail();
       
        	claimviewDetailss.setIseligact((claimviewResult1.getString(1)));
			claimviewDetailss.setIseligactcomm(claimviewResult1.getString(2));
			claimviewDetailss.setWhetcibildone(claimviewResult1.getString(3));
			claimviewDetailss.setWhetcibildonecomm(claimviewResult1.getString(4));
			claimviewDetailss.setIsrataspercgs((claimviewResult1.getString(5)));
			claimviewDetailss.setIsrataspercgscomm(claimviewResult1.getString(6));
			claimviewDetailss.setIsthirdcollattaken(claimviewResult1.getString(7));
			claimviewDetailss.setIsthirdcollattakencomm(claimviewResult1.getString(8));
			claimviewDetailss.setIsnpadtasperguid((claimviewResult1.getString(9)));
			claimviewDetailss.setIsnpadtasperguidcomm(claimviewResult1.getString(10));
			claimviewDetailss.setIsclmoswrtnpadt(claimviewResult1.getString(11));
			claimviewDetailss.setIsclmoswrtnpadtcomm(claimviewResult1.getString(12));
			claimviewDetailss.setWhetseriousdeficinvol((claimviewResult1.getString(13)));
			claimviewDetailss.setWhetseriousdeficinvolcomm(claimviewResult1.getString(14));
			claimviewDetailss.setWhetmajordeficinvolvd(claimviewResult1.getString(15));
			claimviewDetailss.setWhetmajordeficinvolvdcomm(claimviewResult1.getString(16));
			claimviewDetailss.setWhetdeficinvolbystaff((claimviewResult1.getString(17)));
			claimviewDetailss.setWhetdeficinvolbystaffcomm(claimviewResult1.getString(18));
			claimviewDetailss.setIsinternratinvestgrad(claimviewResult1.getString(19));
			claimviewDetailss.setIsinternratinvestgradcomm(claimviewResult1.getString(20));
			claimviewDetailss.setIsallrecinclmform((claimviewResult1.getString(21)));
			claimviewDetailss.setIsallrecinclmformcomm(claimviewResult1.getString(22));
					
			claimCheckListView.add(claimviewDetailss);
        }

	     claimviewResult.close();
	     claimviewStmt.close();
        
		

   } catch (Exception exception) {
       Log.logException(exception);
       throw new DatabaseException(exception.getMessage());
   } finally {
       DBConnection.freeConnection(connection);
   }
   
   request.setAttribute("claimViewArray", claimViewArray);
   request.setAttribute("claimViewArraySize", new Integer(claimViewArray.size()).toString());
 
   request.setAttribute("claimCheckListView", claimCheckListView);
   request.setAttribute("claimCheckListViewSize", 
                        new Integer(claimCheckListView.size()).toString());
  
   
   return mapping.findForward("success");
   
}
	 
	 //for accept button
	 
	 public String ChecklistValidation(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {

		
		String clmrefno=(String)request.getParameter("clmreno");
		System.out.println("clmrefnorrr"+clmrefno);
		PrintWriter out=response.getWriter();
		
		 Connection connection = DBConnection.getConnection();
		 ResultSet claimviewResult=null;
		 Statement claimviewStmt=connection.createStatement();
			
			ResultSet claimviewResult1=null;
			
			ResultSet totalGuaAmtRes=null;
			
			
			ResultSet ssiRefres=null;
			String actvityFlag="";
			String whethercbilFlag="";
			String rateasperFlag="";
			String thirdpartyFlag="";
			String npadateFlag="";
			String whetheroutstandingFlag="";
			String internalratingFlag="";
			int ssiReferNumber=0;
			//String message1="";
			double totGuaAmt=0.0;
			
			 String message1="YOU  CAN NOT APPROVE THIS CLAIM. THIS IS NOT ELIGIBLE UNDER CGS.REASON IS ";
			 String checklistMessage="";
			
			String emailQry1 = "select IS_ELIG_ACT,WHET_CIBIL_DONE,IS_RAT_AS_PER_CGS,IS_THIRD_COLLAT_TAKEN,IS_NPA_DT_AS_PER_GUID,WHET_DEFIC_INVOL_BY_STAFF,IS_INTERN_RAT_INVEST_GRAD  from claim_check_list where  CLM_REF_NO='"+clmrefno+"'";
			
			claimviewResult1=claimviewStmt.executeQuery(emailQry1);
			
			 while (claimviewResult1.next()) {
				 actvityFlag= claimviewResult1.getString(1);
				 whethercbilFlag= claimviewResult1.getString(2);
				 rateasperFlag= claimviewResult1.getString(3);
				 thirdpartyFlag= claimviewResult1.getString(4);
				 npadateFlag= claimviewResult1.getString(5);
				 whetheroutstandingFlag= claimviewResult1.getString(6);
				 internalratingFlag= claimviewResult1.getString(7);
				 
			 }
			 
				 
			 
			 if((actvityFlag.equals("N")))
			 {								 
				String actvRem="1)ACTIVITY IS INELIGIBLE";
				 
				 message1=message1+actvRem;
			
				// out.print(message);				
				 
			 }
			 
			 if((whethercbilFlag.equals("N")))
			 {								 
				 String cibrem=" 2)CIBIL FINDINGS NOT SATISFACTORY";
				 
				 message1=message1+cibrem;
			 
			 }
			 
			 
			 if((rateasperFlag.equals("N")))
			 {								 
				 String rateloan=" 3)RATE CHARGED ON LOAN IS NOT AS PER CGS";
				 
				 message1=message1+rateloan;
			 
			 }
			 
			 
			 if((thirdpartyFlag.equals("Y")))
			 {								 
				 String thirdparty=" 4)THIRD PARTY GUARANTEE TAKEN";
				 
				 message1=message1+thirdparty;
			 
			 }
			 
			 
			 if((npadateFlag.equals("N")))
			 {								 
				 String dateoff=" 5)NPA DATE  NOT AS PER RBI";
				 
				 message1=message1+dateoff;
			 
			 }
			 
			 
			 if((whetheroutstandingFlag.equals("Y")))
			 {								 
				 String whetherstaff=" 9)STAFF  ACCOUNTABILITY INVOVLED";
				 
				 message1=message1+whetherstaff;
			 
			 }
			 
		//	 if((internalratingFlag.equals("N")))
			// {								 
				// String internal=" 10)INTERNAL RATING NOT CARRIED OUT";
				 
				// message1=message1+internal;
			 
			// }
			 
			 String ssiQry = "select ssi_reference_number from ssi_detail ssi,claim_detail_temp cl where ssi.bid=cl.bid and   CLM_REF_NO='"+clmrefno+"' ";
				
			 System.out.println(ssiQry);
			 
			 ssiRefres=claimviewStmt.executeQuery(ssiQry);
				
				 while (ssiRefres.next()) {
					 ssiReferNumber= ssiRefres.getInt(1);					
					 
				 }
			 
			 
			 String totalGuaAmt = "select DECODE(APP_REAPPROVE_AMOUNT,NULL,APP_APPROVED_AMOUNT,APP_REAPPROVE_AMOUNT) from application_detail where app_status not in('RE') and ssi_reference_number='"+ssiReferNumber+"' ";
				
			 System.out.println(totalGuaAmt);
			 
			 totalGuaAmtRes=claimviewStmt.executeQuery(totalGuaAmt);
				
				 while (totalGuaAmtRes.next()) {
					  totGuaAmt= totalGuaAmtRes.getDouble(1);					
					 
				 }
				 
				 if((internalratingFlag.equals("N")) && (totGuaAmt >= 5000000))
				 {								 
					 String internalMore50lakhs=" 10)GUARANTEED AMOUNT MORE THAN 50 LAKHS INTERNAL RATING IS MANDATORY ";
					 
					 message1=message1+internalMore50lakhs;
				 
				 }
			 
			 
			// if(message!=null)
			 if((actvityFlag.equals("N"))|| (whethercbilFlag.equals("N"))|| (rateasperFlag.equals("N")) || (thirdpartyFlag.equals("Y")) || (npadateFlag.equals("N")) || (whetheroutstandingFlag.equals("Y")) || ((internalratingFlag.equals("N")) && (totGuaAmt >= 5000000)))
				 {
			 out.print(message1);
				 }
				 else			
				 {
			 out.print(" Contents of claim form & D/U are certified as correct. ");
				 }
			// if((actvityFlag.equals("N"))|| (whethercbilFlag.equals("N"))|| (rateasperFlag.equals("N")) || (thirdpartyFlag.equals("Y")) || (npadateFlag.equals("N")))
			// {
			// message="YOU  CAN NOT APPROVE THIS CLAIM.. THIS IS NOT ELIGIBLE UNDER CGS.";
				// out.print(message);
				
				 
			
			 return null;
	}
	 
	 //for click on submit 
	 public ActionForward displayClaimProcessingSubmitDUDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
     {
		
    HttpSession session = request.getSession();
	 ClaimActionForm claimFormobj = (ClaimActionForm)form;
	 Map approveFlags = claimFormobj.getDuCertifyDecisionYes();
	 Map approveFlags1 = claimFormobj.getComments();
	 String decision=null;
	 String	emailid=null;
	 String	memid=null;
	
	 HashMap claimapps=new  HashMap();
	 
	 Vector appclms=new  Vector();
	 
	 Vector retclms=new  Vector();
	 
	 int ssiReferNumber=0;
		//String message1="";
		double totGuaAmt=0.0;
		ResultSet totalGuaAmtRes=null;
		
		
		ResultSet ssiRefres=null;
	 
	 ResultSet rs = null;
		Statement statement = null;
	 
	 if(approveFlags.size()==0)
	 {
		 throw new NoMemberFoundException("Please select atleast one(Accepet or Reject) Certify the Content of DandU For claim to approve.");
	 }
	
	    Set keys = approveFlags.keySet();
	    User user = getUserInformation(request);
		String userid=user.getUserId();
		String bankid = (user.getBankId()).trim();
		String zoneid = (user.getZoneId()).trim();
		String branchid = (user.getBranchId()).trim();
		String memberId = bankid + zoneid + branchid;
	    Connection connection = DBConnection.getConnection();
	    Statement str1 = connection.createStatement();
			
	 try
    {
	
	 
	 Iterator clmInterat= keys.iterator();
	 int qrystatus;
	
 	while(clmInterat.hasNext())
		
	{
		
		String clmRefNumber=(String) clmInterat.next();
		
	    decision=(String) approveFlags.get(clmRefNumber);
		String remarks=(String) approveFlags1.get(clmRefNumber);
		
		
		java.util.Date todaydate=new Date();
		
		String actvityFlag="";
		String whethercbilFlag="";
		String rateasperFlag="";
		String thirdpartyFlag="";
		String npadateFlag="";
		String whetherdefstaffFlag="";
		String internalratingFlag="";
		String message="";
		
		
		    ResultSet claimviewResult=null;
			Statement claimviewStmt=connection.createStatement();
			ResultSet claimviewResult1=null;
		if(decision.equals("Y"))
		{
			//today
			
			
				String emailQry1 = "select IS_ELIG_ACT,WHET_CIBIL_DONE,IS_RAT_AS_PER_CGS,IS_THIRD_COLLAT_TAKEN,IS_NPA_DT_AS_PER_GUID,WHET_DEFIC_INVOL_BY_STAFF,IS_INTERN_RAT_INVEST_GRAD from claim_check_list where  CLM_REF_NO='"+clmRefNumber+"' ";
				
				claimviewResult1=claimviewStmt.executeQuery(emailQry1);
				
				 while (claimviewResult1.next()) {
					 actvityFlag= claimviewResult1.getString(1);
					 whethercbilFlag= claimviewResult1.getString(2);
					 rateasperFlag= claimviewResult1.getString(3);
					 thirdpartyFlag= claimviewResult1.getString(4);
					 npadateFlag= claimviewResult1.getString(5);
					 whetherdefstaffFlag= claimviewResult1.getString(6);
					 internalratingFlag= claimviewResult1.getString(7);
					 
				 }
				 
				 
				 
				 String ssiQry = "select ssi_reference_number from ssi_detail ssi,claim_detail_temp cl where ssi.bid=cl.bid and   CLM_REF_NO='"+clmRefNumber+"' ";
					
				 System.out.println(ssiQry);
				 
				 ssiRefres=claimviewStmt.executeQuery(ssiQry);
					
					 while (ssiRefres.next()) {
						 ssiReferNumber= ssiRefres.getInt(1);					
						 
					 }
				 
				 
				 String totalGuaAmt = "select DECODE(APP_REAPPROVE_AMOUNT,NULL,APP_APPROVED_AMOUNT,APP_REAPPROVE_AMOUNT) from application_detail where app_status not in('RE') and ssi_reference_number='"+ssiReferNumber+"' ";
					
				 System.out.println(totalGuaAmt);
				 
				 totalGuaAmtRes=claimviewStmt.executeQuery(totalGuaAmt);
					
					 while (totalGuaAmtRes.next()) {
						  totGuaAmt= totalGuaAmtRes.getDouble(1);					
						 
					 }
				 
				 
				 if((actvityFlag.equals("N"))|| (whethercbilFlag.equals("N"))|| (rateasperFlag.equals("N")) || (thirdpartyFlag.equals("Y")) || (npadateFlag.equals("N")) || (whetherdefstaffFlag.equals("Y")) || ((internalratingFlag.equals("N")) && (totGuaAmt >= 5000000)))
				 {

						String checklistMessage5="";
						
						String message5="";
					 //for checklist yes
					   String quryforSelect =  " update claim_detail_temp set clm_status='MR',CLM_CHECKER_DONE_BY='"+userid+"',CLM_CHECKER_DONE_DT=sysdate,CLM_CHECKER_REMARKS='"+remarks+"' where clm_ref_no='"+clmRefNumber+"'  ";
				       qrystatus = str1.executeUpdate((quryforSelect));
				      //mail part222
				        
				        String emailQry5 = "select IS_ELIG_ACT,WHET_CIBIL_DONE,IS_RAT_AS_PER_CGS,IS_THIRD_COLLAT_TAKEN,IS_NPA_DT_AS_PER_GUID,WHET_DEFIC_INVOL_BY_STAFF,IS_INTERN_RAT_INVEST_GRAD from claim_check_list where  CLM_REF_NO='"+clmRefNumber+"' ";
						
						claimviewResult1=claimviewStmt.executeQuery(emailQry1);
						
						 while (claimviewResult1.next()) {
							 actvityFlag= claimviewResult1.getString(1);
							 whethercbilFlag= claimviewResult1.getString(2);
							 rateasperFlag= claimviewResult1.getString(3);
							 thirdpartyFlag= claimviewResult1.getString(4);
							 npadateFlag= claimviewResult1.getString(5);
							 whetherdefstaffFlag= claimviewResult1.getString(6);
							 internalratingFlag= claimviewResult1.getString(7);
							 
						 }
						 
						 
						 

						 if((actvityFlag.equals("N")))
						 {								 
							String actvRem="1)ACTIVITY IS INELIGIBLE";
							 
							message5=message5+actvRem;
						
							// out.print(message);				
							 
						 }
						 
						 if((whethercbilFlag.equals("N")))
						 {								 
							 String cibrem=" 2)CIBIL FINDINGS NOT SATISFACTORY";
							 
							 message5=message5+cibrem;
						 
						 }
						 
						 
						 if((rateasperFlag.equals("N")))
						 {								 
							 String rateloan=" 3)RATE CHARGED ON LOAN IS NOT AS PER CGS";
							 
							 message5=message5+rateloan;
						 
						 }
						 
						 
						 if((thirdpartyFlag.equals("Y")))
						 {								 
							 String thirdparty=" 4)THIRD PARTY GUARANTEE TAKEN";
							 
							 message5=message5+thirdparty;
						 
						 }
						 
						 
						 if((npadateFlag.equals("N")))
						 {								 
							 String dateoff=" 5)NPA DATE  NOT AS PER RBI";
							 
							 message5=message5+dateoff;
						 
						 }
						 
						 
						 if((whetherdefstaffFlag.equals("Y")))
						 {								 
							 String whetherstaff=" 9)STAFF  ACCOUNTABILITY INVOVLED";
							 
							 message5=message5+whetherstaff;
						 
						 }
						 
					//	 if((internalratingFlag.equals("N")))
						// {								 
							// String internal=" 10)INTERNAL RATING NOT CARRIED OUT";
							 
						//	 message5=message5+internal;
						 
						// }
						 
						 
						 
					
							 
							 if( (internalratingFlag.equals("N")) && (totGuaAmt >= 5000000))
							 {								 
								 String internalMore50lakhs=" 10)GUARANTEED AMOUNT MORE THAN 50 LAKHS INTERNAL RATING IS MANDATORY ";
								 
								 message5=message5+internalMore50lakhs;
							 
							 }
						 
						 
						  statement = connection.createStatement();
					        
					        String emailQry3 = "select distinct MEM_BNK_ID||MEM_ZNE_ID||MEM_BRN_ID   from claim_detail_temp where  CLM_REF_NO='"+clmRefNumber+"' ";
					        
					        
					        rs = statement.executeQuery(emailQry3);
							while (rs.next()) {
								memid = rs.getString(1);
											
							
							}
					        
							
					        
					        String emailQry2 = "select distinct USR_EMAIL_ID  from user_info where  mem_bnk_id||mem_zne_id||mem_brn_id='"+memid+"' and usr_status='K' ";
							
							rs = statement.executeQuery(emailQry2);
							while (rs.next()) {
								emailid = rs.getString(1);
											
							
							}
						
	 //mail part
					        String subject = "Returning of claim from checker after wrong approval by checker";
					
					        String mailBody = "Dear User, \n \n Sorry,\n Your request for claim reference no : "+clmRefNumber+"  is returned by your checker due to the reason "+remarks+" and "+message5+" .\n You are requested to relodge the claim. \n \n Thanks & Regards \n CGTMSE.  " ;
							  
							System.out.println("mailBody is "+mailBody);
							  try{
							  String host = "192.168.10.118";
								boolean sessionDebug = false;
								Properties props = System.getProperties();
								props.put("mail.host", host);
								props.put("mail.transport.protocol", "smtp");
								props.put("mail.smtp.host", host);
								props.put("mail.from", "support@cgtmse.in");

								Session session1 = null;
								session1 = Session.getDefaultInstance(props, null);
								session1.setDebug(sessionDebug);

								javax.mail.internet.MimeMessage msg = new javax.mail.internet.MimeMessage(
										session1);
								msg.setFrom(new javax.mail.internet.InternetAddress("support@cgtmse.in"));
								
								javax.mail.internet.InternetAddress[] Toaddress = { new javax.mail.internet.InternetAddress(emailid) };
								msg.setRecipients(javax.mail.Message.RecipientType.TO,Toaddress);
								msg.setSubject(subject);
								msg.setSentDate(new Date());
				            
									msg.setText(mailBody);
							
									
									System.out.println("BEFORE SEND MAIL"+emailid);
							    	Transport.send(msg);
							  
								
								    System.out.println("AFTER SEND MAIL"+emailid);
							  }
					        
								 catch (Exception e) {
								      e.printStackTrace();
								   
								    }
						 
				        
				        
				        
				        //end
								 
								 retclms.add(clmRefNumber);
				        
				   	 
				 }
				
				 else
				 {
			//IF YES	 
			String quryforSelect = "update claim_detail_temp set  CLM_DECLARATION_RECVD='"+decision+"'," +
			"CLM_DECL_RECVD_DT=sysdate,clm_status='NE',CLM_CHECKER_DONE_BY='"+userid+"',CLM_CHECKER_DONE_DT=sysdate,CLM_CHECKER_REMARKS='"+remarks+"'  where clm_ref_no='"+clmRefNumber+"' ";
		   System.out.println("testing1"+quryforSelect);
	       qrystatus = str1.executeUpdate((quryforSelect));	
	        
	        appclms.add(clmRefNumber);
				
        	 }
		   }
		else
		{
			
			
			
			///24082016
			
			String checklistMessage="";
			
			String message1="";

			String emailQry1 = "select IS_ELIG_ACT,WHET_CIBIL_DONE,IS_RAT_AS_PER_CGS,IS_THIRD_COLLAT_TAKEN,IS_NPA_DT_AS_PER_GUID,WHET_DEFIC_INVOL_BY_STAFF,IS_INTERN_RAT_INVEST_GRAD from claim_check_list where  CLM_REF_NO='"+clmRefNumber+"' ";
			
			claimviewResult1=claimviewStmt.executeQuery(emailQry1);
			
			 while (claimviewResult1.next()) {
				 actvityFlag= claimviewResult1.getString(1);
				 whethercbilFlag= claimviewResult1.getString(2);
				 rateasperFlag= claimviewResult1.getString(3);
				 thirdpartyFlag= claimviewResult1.getString(4);
				 npadateFlag= claimviewResult1.getString(5);
				 whetherdefstaffFlag= claimviewResult1.getString(6);
				 internalratingFlag= claimviewResult1.getString(7);
				 
			 }
			 
			 //rajuk
			 String ssiQry = "select ssi_reference_number from ssi_detail ssi,claim_detail_temp cl where ssi.bid=cl.bid and   CLM_REF_NO='"+clmRefNumber+"' ";
				
			 System.out.println(ssiQry);
			 
			 ssiRefres=claimviewStmt.executeQuery(ssiQry);
				
				 while (ssiRefres.next()) {
					 ssiReferNumber= ssiRefres.getInt(1);					
					 
				 }
			 
			 
			 String totalGuaAmt = "select DECODE(APP_REAPPROVE_AMOUNT,NULL,APP_APPROVED_AMOUNT,APP_REAPPROVE_AMOUNT) from application_detail where app_status not in('RE') and ssi_reference_number='"+ssiReferNumber+"' ";
				
			 System.out.println(totalGuaAmt);
			 
			 totalGuaAmtRes=claimviewStmt.executeQuery(totalGuaAmt);
				
				 while (totalGuaAmtRes.next()) {
					  totGuaAmt= totalGuaAmtRes.getDouble(1);					
					 
				 }
			 
			
				//rajuk
			 
			 
			 if((actvityFlag.equals("N")))
			 {								 
				String actvRem="1)ACTIVITY IS INELIGIBLE";
				 
				 message1=message1+actvRem;
			
				// out.print(message);				
				 
			 }
			 
			 if((whethercbilFlag.equals("N")))
			 {								 
				 String cibrem=" 2)CIBIL FINDINGS NOT SATISFACTORY";
				 
				 message1=message1+cibrem;
			 
			 }
			 
			 
			 if((rateasperFlag.equals("N")))
			 {								 
				 String rateloan=" 3)RATE CHARGED ON LOAN IS NOT AS PER CGS";
				 
				 message1=message1+rateloan;
			 
			 }
			 
			 
			 if((thirdpartyFlag.equals("Y")))
			 {								 
				 String thirdparty=" 4)THIRD PARTY GUARANTEE TAKEN";
				 
				 message1=message1+thirdparty;
			 
			 }
			 
			 
			 if((npadateFlag.equals("N")))
			 {								 
				 String dateoff=" 5)NPA DATE  NOT AS PER RBI";
				 
				 message1=message1+dateoff;
			 
			 }
			 
			 
			 if((whetherdefstaffFlag.equals("Y")))
			 {								 
				 String whetherstaff=" 9)STAFF  ACCOUNTABILITY INVOVLED";
				 
				 message1=message1+whetherstaff;
			 
			 }
			 
			 if( (internalratingFlag.equals("N")) && (totGuaAmt >= 5000000))
			 {								 
				 String internalMore50lakhs=" 10)GUARANTEED AMOUNT MORE THAN 50 LAKHS INTERNAL RATING IS MANDATORY ";
				 
				 message1=message1+internalMore50lakhs;
			 
			 }
		 
				
			
				///24082016
			
			
			//for NO 
		      String quryforSelect = "update claim_detail_temp set clm_status='MR',CLM_CHECKER_DONE_BY='"+userid+"',CLM_CHECKER_DONE_DT=sysdate,CLM_CHECKER_REMARKS='"+remarks+"' where clm_ref_no='"+clmRefNumber+"' ";
			  System.out.println("testing2"+quryforSelect);
	          qrystatus = str1.executeUpdate((quryforSelect));	
	        
	        
	        statement = connection.createStatement();
	        
	        String emailQry3 = "select distinct MEM_BNK_ID||MEM_ZNE_ID||MEM_BRN_ID   from claim_detail_temp where  CLM_REF_NO='"+clmRefNumber+"'";
	        
	        retclms.add(clmRefNumber);
	        
	        rs = statement.executeQuery(emailQry3);
			while (rs.next()) {
				memid = rs.getString(1);
							
			
			}
	        
			
	        
	        String emailQry2 = "select distinct  USR_EMAIL_ID from user_info where  mem_bnk_id||mem_zne_id||mem_brn_id='"+memid+"' and usr_status='K' ";
			
			rs = statement.executeQuery(emailQry2);
			while (rs.next()) {
				emailid = rs.getString(1);
							
			
			}
			
			
		
//mail part
	        String subject = "Returning of claim from checker";
	
	       // String mailBody = "Dear User, \n \n Sorry,\n Your request for claim lodgement is returing by your checker due to the reason "+remarks+" , "+message1+" .\n You are requested to relodge the claim.   " ;
	        String mailBody = "Dear User, \n \n Sorry,\n Your request for claim reference no : "+clmRefNumber+"  is returned by your checker due to the reason "+remarks+" and "+message1+" .\n You are requested to relodge the claim. \n \n Thanks & Regards \n CGTMSE.  " ;
			System.out.println("mailBody is "+mailBody);
			  try{
			  String host = "192.168.10.118";
				boolean sessionDebug = false;
				Properties props = System.getProperties();
				props.put("mail.host", host);
				props.put("mail.transport.protocol", "smtp");
				props.put("mail.smtp.host", host);
				props.put("mail.from", "support@cgtmse.in");

				Session session1 = null;
				session1 = Session.getDefaultInstance(props, null);
				session1.setDebug(sessionDebug);

				javax.mail.internet.MimeMessage msg = new javax.mail.internet.MimeMessage(
						session1);
				msg.setFrom(new javax.mail.internet.InternetAddress("support@cgtmse.in"));
				
				javax.mail.internet.InternetAddress[] Toaddress = { new javax.mail.internet.InternetAddress(emailid) };
				msg.setRecipients(javax.mail.Message.RecipientType.TO,Toaddress);
				msg.setSubject(subject);
				msg.setSentDate(new Date());
           
					msg.setText(mailBody);
			
					
					System.out.println("BEFORE SEND MAIL"+emailid);
			    	Transport.send(msg);
			  
				
				System.out.println("AFTER SEND MAIL"+emailid);
			  }
	        
				 catch (Exception e) {
				      e.printStackTrace();
				   
				    }
	        
	        
        }
	 
	}
 	

   claimapps.put("apprvdClaims", appclms);
   claimapps.put("retClaims", retclms);
   
   claimFormobj.setCgpandetails(claimapps);
   
   request.setAttribute("claimappsMap", claimapps);
   
   connection.commit();
	
    }				 
	 
    catch(SQLException sqlexception)
    {
   	 connection.rollback();
        throw new DatabaseException(sqlexception.getMessage()); 
    }
    finally{
 
	DBConnection.freeConnection(connection);
    }
    
    
    
   // request.setAttribute("message",
	//"Claim Declaration and Undertaking  Request Submitted.");
	
    return mapping.findForward("claimsuccessSummary");
    
}
	
	 
//For Resubmission:
	 
	 
 public ActionForward displayClaimDetailsInputsresubmission(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws DatabaseException {
			ClaimActionForm cpForm = (ClaimActionForm) form;
			cpForm.setMemberId("");
			cpForm.setBorrowerID("");
			cpForm.setClmRefNumber("");
			cpForm.setClaimapplication(null);
			cpForm.resetTheFirstClaimApplication(mapping, request);
			return mapping.findForward("success");
		}
 
 
	//calling action on Ok Button
	public ActionForward getClaimDetailForClmRefNoResub(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws DatabaseException,
			NoDataException, Exception 
			{
		
		try
		{
 
	ClaimActionForm cpForm = (ClaimActionForm) form;
	ClaimsProcessor processor = new ClaimsProcessor();
	String clmrefno = (String) cpForm.getClmRefNumberNew();

	if (clmrefno.equals("")) {
		throw new MessageException("Please enter claim reference number.");
	} else {
		clmrefno = clmrefno.toUpperCase().trim();
	}

	
	Connection connection = DBConnection.getConnection(false);
	  Statement stmt = connection.createStatement();
	  ResultSet rs ;
	  int claimcount =0;
	  int noOfClaims = 0;
	  String query  ="";
	  String status  ="";	

	  query = 
	          "select count(*) from claim_detail_temp  where clm_ref_no='"+clmrefno+"'  and clm_status='MR' ";
	  
	  rs=stmt.executeQuery(query);

	 System.out.println("rajukkk11"+query);
	 
	 while(rs.next())
	  {
	        
		 claimcount = rs.getInt(1);
	      }
	 if(claimcount==0)
	 {
		 throw new MessageException("Please enter claim ref number which is rejected by checker.");
	 }
	 
	
	
	Map map = getClaimInfo(clmrefno);
	String clmStatus = (String) map.get("CLM_STATUS");
	String memberId = (String) map.get("MEMBERID");
	String borrowerId = (String) map.get("BID");
	String installmentFlag = (String) map.get("INSTALLMENTFLAG");
	Date clmdate = (java.util.Date) map.get("CLMDATE");
	
	/*Changes for GST By Kailash*/
	 String bankId = memberId.substring(0, 4);
    String zoneId = memberId.substring(4, 8);
    String branchId = memberId.substring(8, 12);
	 ArrayList<MLIInfo> branchStateList=processor.getGSTStateList(bankId);		 		
	 cpForm.setBranchStateList(branchStateList);
   
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	String str = sdf.format(clmdate);
	String[] arr = str.split("/");
	/*
	 * if(arr[2].compareTo("2014") < 0){ throw new
	 * NoDataException("This claim is not available for updation."); }
	 */
	HttpSession session = request.getSession();
	// //System.out.println(session.getAttribute("mainMenu")+"---"+session.getAttribute("subMenuItem"));

	if (session.getAttribute("mainMenu").equals(
			MenuOptions.getMenu(MenuOptions.CP_CLAIM_FOR))
			&& session.getAttribute("subMenuItem").equals(
					MenuOptions.getMenu(MenuOptions.CP_UPDATE_RT_CLAIM))
			&& !"RT".equals(clmStatus)) {
		throw new DatabaseException(
				"Details for the Claim reference number: " + clmrefno
						+ " can not be updated.");
	}
	if (session.getAttribute("mainMenu").equals(
			MenuOptions.getMenu(MenuOptions.CP_CLAIM_FOR))
			&& session.getAttribute("subMenuItem").equals(
					MenuOptions.getMenu(MenuOptions.CP_UPDATE_TC_CLAIM))
			&& !"TC".equals(clmStatus)) {
		throw new DatabaseException(
				"Details for the Claim reference number: " + clmrefno
						+ " can not be updated.");
	}
	
	if (session.getAttribute("mainMenu").equals(
			MenuOptions.getMenu(MenuOptions.CP_CLAIM_FOR))
			&& session.getAttribute("subMenuItem").equals(
					MenuOptions.getMenu(MenuOptions.CP_UPDATE_MR_CLAIM))
			&& !"MR".equals(clmStatus)) {
		 throw new DatabaseException(
				"Details for the Claim reference number: " + clmrefno
						+ " can not be updated.");
	}

	Vector cgpans =
	// new GMDAO().getCGPANDetailsPeriodicInfo(borrowerId, memberId);
	new CPDAO().getCGPANsForClaim(borrowerId, memberId);

	if (cgpans == null || cgpans.size() == 0) {
		throw new NoDataException(
				"For this Borrower, there are no Live Account(s) or the Loan Account(s) have been closed.");
	}
	ReportManager manager = new ReportManager();

	ClaimApplication claimapplication = manager.displayClmRefNumberDtl(
			clmrefno, clmStatus, memberId);
	if (claimapplication == null) {
		throw new NoDataException(
				"Unable to get claim application details.");
	}

	cpForm.setMemberId(memberId);
	cpForm.setBorrowerID(borrowerId);
	cpForm.setDealingOfficerName(claimapplication.getDealingOfficerName());
	cpForm.setMemberDetails(claimapplication.getMemberDetails());
	cpForm.setBorrowerDetails(claimapplication.getBorrowerDetails());
	cpForm.setMicroCategory(claimapplication.getMicroCategory());

	java.util.Date npaClassifiedDate = null;
	String npaClassifiedDateStr = null;
	java.util.Date npaReportingDate = null;
	String npaReportingDateStr = null;
	String reasonfornpa = null;
	String subsidyFlag = null;
	String isSubsidyRcvdFlag = null;
	String isSubsidyAdjustedFlag = null;
	java.util.Date subsidyDt = null;
	String subsidyDtStr = "";
	double subsidyAmt = 0.0;
	String npaId = null;
	HashMap npaMap = new HashMap();

	SecurityAndPersonalGuaranteeDtls sapgd = claimapplication
			.getSecurityAndPersonalGuaranteeDtls();

	// set npa details from claimapplication object
	npaClassifiedDate = claimapplication
			.getDateOnWhichAccountClassifiedNPA();
	if (npaClassifiedDate != null) {
		npaClassifiedDateStr = (String) sdf.format(npaClassifiedDate);
	}
	reasonfornpa = (String) claimapplication
			.getReasonsForAccountTurningNPA();
	subsidyFlag = (String) claimapplication.getSubsidyFlag();
	isSubsidyRcvdFlag = (String) claimapplication
			.getIsSubsidyRcvdAfterNpa();
	isSubsidyAdjustedFlag = (String) claimapplication
			.getIsSubsidyAdjustedOnDues();
	subsidyAmt = (Double) claimapplication.getSubsidyAmt();
	subsidyDt = claimapplication.getSubsidyDate();
	if (subsidyDt != null) {
		subsidyDtStr = (String) sdf.format(subsidyDt);
		try {
			subsidyDt = sdf.parse(subsidyDtStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	// set asonsanc and asonnpa details from claimapplication object

	DtlsAsOnDateOfSanction dtlAsOnSanc = sapgd
			.getDetailsAsOnDateOfSanction();
	DtlsAsOnDateOfNPA dtlAsOnNpa = sapgd.getDetailsAsOnDateOfNPA();

	Map securityMap = new HashMap();
	securityMap.put("LAND", dtlAsOnSanc.getValueOfLand());
	securityMap.put("networth", dtlAsOnSanc.getNetworthOfGuarantors());
	securityMap.put("MACHINE", dtlAsOnSanc.getValueOfMachine());
	securityMap.put("BUILDING", dtlAsOnSanc.getValueOfBuilding());
	securityMap.put("OTHER FIXED MOVABLE ASSETS",dtlAsOnSanc.getValueOfOtherFixedMovableAssets());
	securityMap
			.put("CURRENT ASSETS", dtlAsOnSanc.getValueOfCurrentAssets());
	securityMap.put("OTHERS", dtlAsOnSanc.getValueOfOthers());
	securityMap.put("reasonReduction", "NA");
	double totalAsOnSanc = dtlAsOnSanc.getValueOfLand()
			+ dtlAsOnSanc.getValueOfMachine()
			+ dtlAsOnSanc.getValueOfBuilding()
			+ dtlAsOnSanc.getValueOfOtherFixedMovableAssets()
			+ dtlAsOnSanc.getValueOfCurrentAssets()
			+ dtlAsOnSanc.getValueOfOthers();
	cpForm.setTotSecAsOnSanc(totalAsOnSanc);
	cpForm.setAsOnDtOfSanctionDtl(securityMap);

	securityMap = null;
	securityMap = new HashMap();
	securityMap.put("LAND", dtlAsOnNpa.getValueOfLand());
	securityMap.put("networth", dtlAsOnNpa.getNetworthOfGuarantors());
	securityMap.put("MACHINE", dtlAsOnNpa.getValueOfMachine());
	securityMap.put("BUILDING", dtlAsOnNpa.getValueOfBuilding());
	securityMap.put("OTHER FIXED MOVABLE ASSETS",
			dtlAsOnNpa.getValueOfOtherFixedMovableAssets());
	securityMap.put("CURRENT ASSETS", dtlAsOnNpa.getValueOfCurrentAssets());
	securityMap.put("OTHERS", dtlAsOnNpa.getValueOfOthers());
	securityMap.put("reasonReduction", dtlAsOnNpa.getReasonsForReduction());
	double totalAsOnNpa = dtlAsOnNpa.getValueOfLand()
			+ dtlAsOnNpa.getValueOfMachine()
			+ dtlAsOnNpa.getValueOfBuilding()
			+ dtlAsOnNpa.getValueOfOtherFixedMovableAssets()
			+ dtlAsOnNpa.getValueOfCurrentAssets()
			+ dtlAsOnNpa.getValueOfOthers();
	cpForm.setTotSecAsOnNpa(totalAsOnNpa);
	cpForm.setAsOnDtOfNPA(securityMap);
	securityMap = null;

	npaMap.put(ClaimConstants.NPA_CLASSIFIED_DT, npaClassifiedDateStr);
	npaMap.put(ClaimConstants.REASONS_FOR_TURNING_NPA, reasonfornpa);
	cpForm.setNpaDetails(npaMap);
	cpForm.setWilfullDefaulter(claimapplication
			.getWhetherBorrowerIsWilfulDefaulter());
	cpForm.setIsAcctFraud(claimapplication.getFraudFlag());
	cpForm.setIsEnquiryConcluded(claimapplication.getEnquiryFlag());
	cpForm.setIsMLIInvolved(claimapplication.getMliInvolvementFlag());
	cpForm.setSubsidyFlag(subsidyFlag);
	cpForm.setIsSubsidyRcvdAfterNpa(isSubsidyRcvdFlag);
	cpForm.setIsSubsidyAdjustedOnDues(isSubsidyAdjustedFlag);
	cpForm.setSubsidyAmt(subsidyAmt);
	cpForm.setSubsidyDate(subsidyDtStr);
	cpForm.setDateOfRecallNotice(claimapplication
			.getDateOfIssueOfRecallNoticeStr());
	cpForm.setReasonForIssueRecallNotice(claimapplication
			.getReasonForRecall());
	cpForm.setForumthrulegalinitiated(claimapplication
			.getLegalProceedingsDetails()
			.getForumRecoveryProceedingsInitiated());
	cpForm.setCaseregnumber(claimapplication.getLegalProceedingsDetails()
			.getSuitCaseRegNumber());
	cpForm.setLegaldate(claimapplication.getLegalProceedingsDetails()
			.getFilingDateStr());
	cpForm.setReasonForFilingSuit(claimapplication.getReasonForFilingSuit());
	Date dt = null;
	String strdt = null;
	Date date = null;
	dt = claimapplication.getAssetPossessionDt();
	if (dt != null) {
		strdt = (String) sdf.format(dt);
		cpForm.setAssetPossessionDate(strdt);
	} else {
		cpForm.setAssetPossessionDate(null);
	}
	dt = null;
	strdt = null;
	cpForm.setLocation(claimapplication.getLegalProceedingsDetails()
			.getLocation());
	cpForm.setAmountclaimed(String.valueOf(claimapplication
			.getLegalProceedingsDetails().getAmountClaimed()));
	cpForm.setInclusionOfReciept(claimapplication.getInclusionOfReceipt());
	cpForm.setConfirmRecoveryValues(claimapplication
			.getConfirmRecoveryFlag());
	cpForm.setMliCommentOnFinPosition(claimapplication
			.getMliCommentOnFinPosition());
	cpForm.setDetailsOfFinAssistance(claimapplication
			.getDetailsOfFinAssistance());
	cpForm.setCreditSupport(claimapplication.getCreditSupport());
	cpForm.setBankFacilityDetail(claimapplication.getBankFacilityDetail());
	cpForm.setPlaceUnderWatchList(claimapplication.getPlaceUnderWatchList());
	cpForm.setRemarksOnNpa(claimapplication.getRemarksOnNpa());
	cpForm.setNameOfOfficial(claimapplication.getNameOfOfficial());
	cpForm.setDesignationOfOfficial(claimapplication
			.getDesignationOfOfficial());
	dt = claimapplication.getClaimSubmittedDate();
	if (dt != null) {
		strdt = (String) sdf.format(dt);
		date = null;
	} else {
		strdt = null;
	}
	cpForm.setClaimSubmittedDate(strdt);
	dt = null;
	strdt = null;
	cpForm.setPlace(claimapplication.getPlace());

	Map securityMapAsOnLodgement = new HashMap();
	DtlsAsOnLogdementOfClaim dtlAsOnClaim = sapgd
			.getDetailsAsOnDateOfLodgementOfClaim();
	securityMapAsOnLodgement.put("LAND", dtlAsOnClaim.getValueOfLand());
	securityMapAsOnLodgement.put("networth",
			dtlAsOnClaim.getNetworthOfGuarantors());
	securityMapAsOnLodgement.put("MACHINE",
			dtlAsOnClaim.getValueOfMachine());
	securityMapAsOnLodgement.put("BUILDING",
			dtlAsOnClaim.getValueOfBuilding());
	securityMapAsOnLodgement.put("OTHER FIXED MOVABLE ASSETS",
			dtlAsOnClaim.getValueOfOtherFixedMovableAssets());
	securityMapAsOnLodgement.put("CURRENT ASSETS",
			dtlAsOnClaim.getValueOfCurrentAssets());
	securityMapAsOnLodgement.put("OTHERS", dtlAsOnClaim.getValueOfOthers());
	securityMapAsOnLodgement.put("reasonReduction",
			dtlAsOnClaim.getReasonsForReduction());
	double totalAsOClaim = dtlAsOnClaim.getValueOfLand()
			+ dtlAsOnClaim.getValueOfMachine()
			+ dtlAsOnClaim.getValueOfBuilding()
			+ dtlAsOnClaim.getValueOfOtherFixedMovableAssets()
			+ dtlAsOnClaim.getValueOfCurrentAssets()
			+ dtlAsOnClaim.getValueOfOthers();
	cpForm.setAsOnLodgemntOfCredit(securityMapAsOnLodgement);
	cpForm.setTotSecAsOnClaim(totalAsOClaim);

	// cpForm.setTcCgpansVector(claimapplication.getTermCapitalDtls());
	// cpForm.setWcCgpansVector(claimapplication.getWorkingCapitalDtlsVector());

	Vector tc = claimapplication.getTermCapitalDtls(); // -----------------------------(1)
	// //System.out.println("initial size of tc cgpans:"+tc.size());
	for (int m = 0; m < tc.size(); m++) {
		boolean isFound = false;
		TermLoanCapitalLoanDetail td = (TermLoanCapitalLoanDetail) tc
				.get(m);
		String cg = td.getCgpan();
		for (int n = 0; n < cgpans.size(); n++) {
			Map cgmap = (HashMap) cgpans.get(n);
			String scg = (String) cgmap.get(ClaimConstants.CLM_CGPAN);

			if (scg.equals(cg)) {
				isFound = true;
				break;
			}
		}
		if (!isFound) {
			tc.remove(m);
		}
	}
	// //System.out.println("eligible size of tc cgpans:"+tc.size());
	Map tempMap = new HashMap();
	Vector tcCgpansVector = new Vector();
	for (int i = 0; i < tc.size(); i++) {
		int j = i + 1;
		TermLoanCapitalLoanDetail td = (TermLoanCapitalLoanDetail) tc
				.get(i);
		Map tccg = new HashMap();
		String cg = td.getCgpan();
		for (int k = 0; k < cgpans.size(); k++) {
			tempMap = (HashMap) cgpans.get(k);
			if (cg.equals(tempMap.get(ClaimConstants.CLM_CGPAN))) {
				tccg.put(
						ClaimConstants.CLM_APPLICATION_APPRVD_AMNT,
						tempMap.get(ClaimConstants.CLM_APPLICATION_APPRVD_AMNT));
				break;
			}
		}
		tccg.put(ClaimConstants.CLM_CGPAN, td.getCgpan());
		tcCgpansVector.add(tccg);
		cpForm.setCgpandetails("key-" + j, td.getCgpan());
		dt = td.getLastDisbursementDate();
		if (dt != null) {
			strdt = (String) sdf.format(dt);

			try {
				date = sdf.parse(strdt);
				cpForm.setLastDisbursementDate("key-" + j, strdt);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			cpForm.setLastDisbursementDate("key-" + j,
					td.getLastDisbursementDate());
		}
		cpForm.setTotalDisbursementAmt("key-" + j, td.getTotaDisbAmnt());
		cpForm.setAppApprovedAmt("key-" + j,
				tccg.get(ClaimConstants.CLM_APPLICATION_APPRVD_AMNT));
		cpForm.setTcprincipal("key-" + j, td.getPrincipalRepayment());
		cpForm.setTcInterestCharge("key-" + j,
				td.getInterestAndOtherCharges());
		cpForm.setTcOsAsOnDateOfNPA("key-" + j,
				td.getOutstandingAsOnDateOfNPA());
		cpForm.setTcOsAsStatedInCivilSuit("key-" + j,
				td.getOutstandingStatedInCivilSuit());
		cpForm.setTcOsAsOnLodgementOfClaim("key-" + j,
				td.getOutstandingAsOnDateOfLodgement());
		cpForm.setClaimFlagsTc("key-" + j, td.getTcClaimFlag());
		//cpForm.setSancInterestRates("key-"+i,td.getSancInterestRate());  bhu changed testing
	}
	cpForm.setTcCounter(tcCgpansVector.size());
	cpForm.setTcCgpansVector(tcCgpansVector);

	Vector wc = claimapplication.getWorkingCapitalDtlsVector(); // -----------------------(2)

	for (int m = 0; m < wc.size(); m++) {
		boolean isFound = false;
		WorkingCapitalDetail wd = (WorkingCapitalDetail) wc.get(m);
		String cg = wd.getCgpan();
		for (int n = 0; n < cgpans.size(); n++) {
			Map cgmap = (HashMap) cgpans.get(n);
			String scg = (String) cgmap.get(ClaimConstants.CLM_CGPAN);

			if (scg.equals(cg)) {
				isFound = true;
				break;
			}
		}
		if (!isFound) {
			wc.remove(m);
		}
	}

	Vector wcCgpansVector = new Vector();
	for (int i = 0; i < wc.size(); i++) {
		int j = i + 1;
		WorkingCapitalDetail wd = (WorkingCapitalDetail) wc.get(i);
		Map wccg = new HashMap();
		wccg.put(ClaimConstants.CLM_CGPAN, wd.getCgpan());
		wcCgpansVector.add(wccg);

		cpForm.setCgpandetails("key-" + j, wd.getCgpan());
		cpForm.setWcOsAsOnDateOfNPA("key-" + j,
				wd.getOutstandingAsOnDateOfNPA());
		cpForm.setWcOsAsStatedInCivilSuit("key-" + j,
				wd.getOutstandingStatedInCivilSuit());
		cpForm.setWcOsAsOnLodgementOfClaim("key-" + j,
				wd.getOutstandingAsOnDateOfLodgement());
		cpForm.setClaimFlagsWc("key-" + j, wd.getWcClaimFlag());
	}
	cpForm.setWcCounter(wcCgpansVector.size());
	cpForm.setWcCgpansVector(wcCgpansVector);

	/* For approved amount */
	ArrayList summaryList = claimapplication.getClaimSummaryDtls();

	for (int m = 0; m < summaryList.size(); m++) {
		boolean isFound = false;
		ClaimSummaryDtls summary = (ClaimSummaryDtls) summaryList.get(m);
		String cg = summary.getCgpan();
		for (int n = 0; n < cgpans.size(); n++) {
			Map cgmap = (HashMap) cgpans.get(n);
			String scg = (String) cgmap.get(ClaimConstants.CLM_CGPAN);
			if (cg.equals(scg)) {
				isFound = true;
				break;
			}
		}
		if (!isFound) {
			summaryList.remove(m);
		}
	}

	Vector cgpnDetails = new Vector();
	for (int i = 0; i < summaryList.size(); i++) {
		// int m = i + 1;
		ClaimSummaryDtls summary = (ClaimSummaryDtls) summaryList.get(i);
		Map summaryMap = new HashMap();
		summaryMap.put(ClaimConstants.CLM_CGPAN, summary.getCgpan());
		summaryMap.put(ClaimConstants.CGPAN_APPRVD_AMNT,
				summary.getLimitCoveredUnderCGFSI());
		for (int k = 0; k < cgpans.size(); k++) {
			Map tempMap2 = (HashMap) cgpans.get(k);
			if (summary.getCgpan().equals(
					tempMap2.get(ClaimConstants.CLM_CGPAN))) {
				summaryMap.put("LoanType",
						tempMap2.get(ClaimConstants.CGPAN_LOAN_TYPE));
				break;
			}
		}

		cgpnDetails.add(summaryMap);

		cpForm.setClaimSummaryDetails(summary.getCgpan(),
				summary.getAmount());
	}
	cpForm.setCgpnDetails(cgpnDetails);

	Vector recoveryModes = processor.getAllRecoveryModes();
	cpForm.setRecoveryModes(recoveryModes);
	/* RECOVERY DETAILS */
	Vector rec = claimapplication.getRecoveryDetails(); // -------------------------------(3)

	for (int m = 0; m < rec.size(); m++) {
		boolean isFound = false;
		RecoveryDetails rd = (RecoveryDetails) rec.get(m);
		String cg = rd.getCgpan();
		for (int n = 0; n < cgpans.size(); n++) {
			Map cgmap = (HashMap) cgpans.get(n);
			String scg = (String) cgmap.get(ClaimConstants.CLM_CGPAN);
			if (cg.equals(scg)) {
				isFound = true;
				break;
			}
		}
		if (!isFound) {
			rec.remove(m);
		}
	}

	Vector cgpansVector = new Vector();
	for (int i = 0; i < cgpnDetails.size(); i++) {
		int j = i + 1;

		if (rec.size() >= j) {
			RecoveryDetails rd = (RecoveryDetails) rec.get(i);
			cgpansVector.add(rd.getCgpan());

			cpForm.setCgpandetails("recovery#key-" + j, rd.getCgpan());
			cpForm.setCgpandetails("tcprincipal$recovery#key-" + j,
					String.valueOf(rd.getTcPrincipal()));
			cpForm.setCgpandetails("tcInterestCharges$recovery#key-" + j,
					String.valueOf(rd.getTcInterestAndOtherCharges()));
			cpForm.setCgpandetails("wcAmount$recovery#key-" + j,
					String.valueOf(rd.getWcAmount()));
			cpForm.setCgpandetails("wcOtherCharges$recovery#key-" + j,
					String.valueOf(rd.getWcOtherCharges()));
			cpForm.setCgpandetails("recoveryMode$recovery#key-" + j,
					String.valueOf(rd.getModeOfRecovery()));
		} else {
			for (int k = 0; k < cgpnDetails.size(); k++) {
				Map summary = (HashMap) cgpnDetails.get(k);
				String cgpan = (String) summary
						.get(ClaimConstants.CLM_CGPAN);
				if (!cgpansVector.contains(cgpan)) {
					cpForm.setCgpandetails("recovery#key-" + j, cgpan);
					cpForm.setCgpandetails("tcprincipal$recovery#key-" + j,
							"");
					cpForm.setCgpandetails(
							"tcInterestCharges$recovery#key-" + j, "");
					cpForm.setCgpandetails("wcAmount$recovery#key-" + j, "");
					cpForm.setCgpandetails("wcOtherCharges$recovery#key-"
							+ j, "");
					cpForm.setCgpandetails(
							"recoveryMode$recovery#key-" + j, "");
					cgpansVector.add(cgpan);
					break;
				}
			}
		}
	}
	cpForm.setCgpansVector(cgpansVector);
	
	if(clmStatus.equals("MR"))
	{		
	session.setAttribute("RETURNREMARKS",
			"Please fill all the claim  application");//rajuk
	}
	else
	{
	session.setAttribute("RETURNREMARKS",
			claimapplication.getReturnRemarks());//rajuk
	}
	
		}
		catch(Exception e)
		{
			   throw new DatabaseException(e.getMessage());
		}
	return mapping.findForward("success");
}
	
	
	//calling action on  Next button
	
	public ActionForward addFirstClaimsPageDetailsResub(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		
   HttpSession session = request.getSession();
   System.out.println("<br>===555===");
  // ClaimActionForm claimFormobject = (ClaimActionForm)form;
   System.out.println("<br>===556===");
   User user = getUserInformation(request);
   String bankId = user.getBankId();
   String zoneId = user.getZoneId();
   String branchId = user.getBranchId();
   String memberId = bankId.concat(zoneId).concat(branchId);
   System.out.println("rajukonkati"+memberId);
   ClaimActionForm cpForm = (ClaimActionForm) form;
	ClaimsProcessor processor = new ClaimsProcessor();
	String clmrefno = (String)cpForm.getClmRefNumberNew();
   //String clmRefNumber = (String)request.getParameter("clmrefnum");
   System.out.println("rajuk1234567"+clmrefno);
   ArrayList claimViewArray = new ArrayList();
   ArrayList claimCheckListView = new ArrayList();
   Connection connection = DBConnection.getConnection();
   ResultSet claimviewResult=null;
	PreparedStatement claimviewStmt=null;
	ResultSet claimviewResult1=null;
	PreparedStatement claimviewStmt1=null;
	ClaimActionForm claimForm = (ClaimActionForm) form;

	 String  isNorthEastRegion="";
	 String  isWomenorNot="";
	
	//ended by rajuk
	
	
	
	try{
	
		String Query2="select IS_ELIG_ACT,IS_ELIG_ACT_COMM,WHET_CIBIL_DONE,WHET_CIBIL_DONE_COMM,IS_RAT_AS_PER_CGS,IS_RAT_AS_PER_CGS_COMM,IS_THIRD_COLLAT_TAKEN," +
		"IS_THIRD_COLLAT_TAKEN_COMM,IS_NPA_DT_AS_PER_GUID,IS_NPA_DT_AS_PER_GUID_COMM,IS_CLM_OS_WRT_NPA_DT,IS_CLM_OS_WRT_NPA_DT_COMM,WHET_SERIOUS_DEFIC_INVOL," +
		"WHET_SERIOUS_DEFIC_INVOL_COMM,WHET_MAJOR_DEFIC_INVOLVD,WHET_MAJOR_DEFIC_INVOLVD_COMM," +
		"WHET_DEFIC_INVOL_BY_STAFF,WHET_DEFIC_INVOL_BY_STAFF_COMM,IS_INTERN_RAT_INVEST_GRAD," +
		"IS_INTERN_RAT_INVEST_GRAD_COMM,IS_ALL_REC_IN_CLM_FORM,IS_ALL_REC_IN_CLM_FORM_COMM " +
		"from claim_check_list a ,claim_detail_temp b where a.clm_ref_no=b.clm_ref_no and a.clm_ref_no=? "+"AND CLM_STATUS IN ('MR') and a.clm_ref_no=? ";

      System.out.println("rrrrrrrrr"+Query2);


    claimviewStmt1 = connection.prepareStatement(Query2);
	 claimviewStmt1.setString(1, clmrefno);
	 claimviewStmt1.setString(2, clmrefno);
    claimviewResult1 = claimviewStmt1.executeQuery();
	    ClaimApplication claimApplication = new ClaimApplication();
    //ClaimActionForm claimFormobj = new ClaimActionForm();
   
    while(claimviewResult1.next()){
   	 
   	 String iseligact1=claimviewResult1.getString(1);
   	        	 
   	 String iseligactcomm1=claimviewResult1.getString(2);
   	 
        String whetcibildone1=claimviewResult1.getString(3);
   	 
   	 String whetcibildonecomm1=claimviewResult1.getString(4);
           
   	 String isrataspercgs1=claimviewResult1.getString(5);
   	 
   	 String isrataspercgscomm1=claimviewResult1.getString(6);
   	
   	 String isthirdcollattaken1=claimviewResult1.getString(7);
        
   	 String isthirdcollattakencomm1=claimviewResult1.getString(8);
   	
   	 String isnpadtasperguid1=claimviewResult1.getString(9);
   	 
   	 String isnpadtasperguidcomm1=claimviewResult1.getString(10);
   	
   	 String isclmoswrtnpadt1=claimviewResult1.getString(11);
   	 
   	 String isclmoswrtnpadtcomm1=claimviewResult1.getString(12);
   	 
   	 String whetseriousdeficinvol1=claimviewResult1.getString(13);
   	 
   	 String whetseriousdeficinvolcomm1=claimviewResult1.getString(14);
   	 String whetmajordeficinvolvd1=claimviewResult1.getString(15);
   	 String whetmajordeficinvolvdcomm1=claimviewResult1.getString(16);
   	 String whetdeficinvolbystaff1=claimviewResult1.getString(17);
   	 String whetdeficinvolbystaffcomm1=claimviewResult1.getString(18);
   	 String isinternratinvestgrad1=claimviewResult1.getString(19);
   	 String isinternratinvestgradcomm1=claimviewResult1.getString(20);
   	 String isallrecinclmform1=claimviewResult1.getString(21);
   	 String isallrecinclmformcomm1=claimviewResult1.getString(22);
   	
   	 
   	
    
   	 claimForm.setIseligact(iseligact1);  
   	 claimForm.setIseligactcomm(iseligactcomm1);
   	 claimForm.setWhetcibildone(whetcibildone1);
   	 claimForm.setWhetcibildonecomm(whetcibildonecomm1);
   	 claimForm.setIsrataspercgs(isrataspercgs1);
   	 claimForm.setIsrataspercgscomm(isrataspercgscomm1);
   	 claimForm.setIsthirdcollattaken(isthirdcollattaken1);
   	 claimForm.setIsthirdcollattakencomm(isthirdcollattakencomm1);
   	 claimForm.setIsnpadtasperguid(isnpadtasperguid1);
   	 claimForm.setIsnpadtasperguidcomm(isnpadtasperguidcomm1);
   	 claimForm.setIsclmoswrtnpadt(isclmoswrtnpadt1);
   	 claimForm.setIsclmoswrtnpadtcomm(isclmoswrtnpadtcomm1);
   	 claimForm.setWhetseriousdeficinvol(whetseriousdeficinvol1);
   	 claimForm.setWhetseriousdeficinvolcomm(whetseriousdeficinvolcomm1);
   	 claimForm.setWhetmajordeficinvolvd(whetmajordeficinvolvd1);
   	 claimForm.setWhetmajordeficinvolvdcomm(whetmajordeficinvolvdcomm1);
   	 claimForm.setWhetdeficinvolbystaff(whetdeficinvolbystaff1);
   	 claimForm.setWhetdeficinvolbystaffcomm(whetdeficinvolbystaffcomm1);
   	 claimForm.setIsinternratinvestgrad(isinternratinvestgrad1);
   	 claimForm.setIsinternratinvestgradcomm(isinternratinvestgradcomm1);
   	 claimForm.setIsallrecinclmform(isallrecinclmform1);
   	 claimForm.setIsallrecinclmformcomm(isallrecinclmformcomm1);
    }
   	 System.out.println(claimForm.getIseligact());
   	 System.out.println(claimForm.getIseligactcomm());
   	 System.out.println("end");
   	 
   	 //rajuk
   	 

   	// if(!(clmrefno.equals(null)))
   	        	// {
   	        	 
   	              String query1  ="";
   	    		  String nameofofficial1="";
   	    		  String designation1="";
   	    		  String place1="";
   	    		  
   	    		  String Query3="select CLM_OFFICIAL_NAME,CLM_OFFICIAL_DESIGNATION,CLM_PLACE from claim_detail_temp  where clm_ref_no= ? ";	 
   	    		 
   	    		  claimviewStmt1 = connection.prepareStatement(Query3);
   	    		  claimviewStmt1.setString(1, clmrefno);
   	    	      claimviewResult1 = claimviewStmt1.executeQuery();
   	    	      //Statement stmt = connection.createStatement();
   	    	   //   stmt.setString(1, clmrefno);
   	   		     //  ResultSet rs=null;
   	   		     //  Connection conn = null;
   	    	
   	    			 while (claimviewResult1.next()) {
   	    				 nameofofficial1= claimviewResult1.getString(1);
   	    				 
   	    				 System.out.println("nameofofficial"+nameofofficial1);
   	    				 designation1= claimviewResult1.getString(2);
   	    				 System.out.println("Desig"+designation1);
   	    				 place1= claimviewResult1.getString(3);
   	    				 System.out.println("place"+place1);	
   	    				 
   	    				 claimForm.setNameOfOffi(nameofofficial1);
   	    	        	 claimForm.setDesigna(designation1);
   	    	        	 claimForm.setPlaces(designation1);
   	    				
   	    			 }
   	    			
	 
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	//ClaimApplication claimApplication = new ClaimApplication();
	String mId = claimForm.getMemberId();
	String bid = ((claimForm.getBorrowerID()).toUpperCase()).trim();

	MemberInfo info = claimForm.getMemberDetails();
	// String phone =
	// info.getTelephone();//--------------------------------------------------------phone
	// String email =
	// info.getEmail();//------------------------------------------------------------email
	String officerName = claimForm.getDealingOfficerName();
	String microFlag = claimForm.getMicroCategory();
	String wilful = claimForm.getWilfullDefaulter();
	String fraudFlag = claimForm.getIsAcctFraud();
	String enquiryFlag = claimForm.getIsEnquiryConcluded();
	String mliInvolvementFlag = claimForm.getIsMLIInvolved();
	String reasonForRecall = claimForm.getReasonForIssueRecallNotice();
	String reasonForFilingSuit = claimForm.getReasonForFilingSuit();

	String inclusionOfReceipt = claimForm.getInclusionOfReciept();
	String confirmRecoveryFlag = claimForm.getConfirmRecoveryValues();
	String subsidyFlag = claimForm.getSubsidyFlag();
	String subsidyRcvd = claimForm.getIsSubsidyRcvdAfterNpa();
	String subsidyAdjusted = claimForm.getIsSubsidyAdjustedOnDues();
	double subsidyAmt = claimForm.getSubsidyAmt();
	String subsidyDateStr = claimForm.getSubsidyDate();
	Date subsidyDate = null;
	if (subsidyDateStr != null || !"".equals(subsidyDateStr)) {
		subsidyDate = sdf.parse(subsidyDateStr, new ParsePosition(0));
	}
	String comments = claimForm.getMliCommentOnFinPosition();
	String finAssistance = claimForm.getDetailsOfFinAssistance();
	String creditSupport = claimForm.getCreditSupport();
	
/*	String adviceByRbi=claimForm.getAdviceByRbi();//bhu
	String rbiNonWilDef=claimForm.getRbiNonWilDef();//bhu
	String rbiNonDef=claimForm.getRbiNonDef();//bhu
	String cibilNonWilDef=claimForm.getCibilNonWilDef();//bhu
	String cibilNonDef=claimForm.getCibilNonDef();//bhu*/
	
	
	
	
	String bankFacility = claimForm.getBankFacilityDetail();
	String watchListPlace = claimForm.getPlaceUnderWatchList();
	String remarks = claimForm.getRemarksOnNpa();
	String assetPossessionDtStr = claimForm.getAssetPossessionDate();
	java.util.Date assetPossessionDt = null;
	if (assetPossessionDtStr != null || !assetPossessionDtStr.equals("")) {
		assetPossessionDt = sdf.parse(assetPossessionDtStr,
				new ParsePosition(0));
	}
	String nameofforum = claimForm.getNameofforum(); // ---------------------------------------------name
														// of forum
	String dtOfRecallNoticeStr = claimForm.getDateOfRecallNotice();
	java.util.Date recallnoticedate = sdf.parse(dtOfRecallNoticeStr,
			new ParsePosition(0));
	String forumthruwhichinitiated = claimForm.getForumthrulegalinitiated();
	if (forumthruwhichinitiated != null) {
		if (forumthruwhichinitiated.equals("Others")) {
			forumthruwhichinitiated = claimForm.getOtherforums();
		}
	}
	String casesuitregnumber = claimForm.getCaseregnumber();
	// //System.out.println("casesuitregnumber:" + casesuitregnumber);
	// String nameofforum = claimForm.getNameofforum();
	String dtOfFilingStr = claimForm.getLegaldate();
	java.util.Date filingdate = sdf.parse(dtOfFilingStr, new ParsePosition(
			0));
	String location = claimForm.getLocation();
	String amntClaimed1 = claimForm.getAmountclaimed();
	String currentstatusremarks = claimForm.getCurrentstatusremarks();
	String recoveryprocconcluded = claimForm.getProceedingsConcluded();
	String dtOfReleaseOfWCStr = claimForm.getDateOfReleaseOfWC();
	java.util.Date dateofreleasewc = null;
	if ((dtOfReleaseOfWCStr != null) && (dtOfReleaseOfWCStr.equals(""))) {
		dateofreleasewc = sdf.parse(dtOfReleaseOfWCStr,
				new ParsePosition(0));
	}
	String dtofseekingofots = claimForm.getDateOfSeekingOTS();
	Map tclastDisbursementDts = claimForm.getLastDisbursementDate();
	Set tclastDisbursementDtsSet = tclastDisbursementDts.keySet();
	Iterator tclastDisbursementDtsIterator = tclastDisbursementDtsSet
			.iterator();

	/*
	 * Uploading the attachements in the claim application
	 */
	String contextPath = null;
	String nameOfFile = null;
	// Retrieving the present time in yyyy-mm-dd format
	String currentTime = (new java.sql.Date(System.currentTimeMillis()))
			.toString();
	/*
	 * Uploading the Recall Issue Notice File Attachment bhu
	 */
	FormFile file = (FormFile) claimForm.getRecallnoticefilepath();
	if (file != null) {
		contextPath = request.getSession().getServletContext()
				.getRealPath("");
		nameOfFile = ClaimConstants.CLM_RECALL_NOTICE + currentTime
				+ ClaimConstants.CLM_NO_VALUE + mId + bid;
		// Uploading the file with the new name
		Log.log(Log.DEBUG, "ClaimAction", "addFirstClaimsPageDetails()",
				"Uploading the Recall Notice attachment....");

		byte[] data = file.getFileData();
		claimApplication.setRecallNoticeFileData(data);
		claimApplication.setRecalNoticeFileName(file.getFileName());

		 uploadFile(file,contextPath,nameOfFile);

		Log.log(Log.DEBUG, "ClaimAction", "addFirstClaimsPageDetails()",
				"File Name, File data " + file.getFileName() + "," + data);

		if (data != null) {
			Log.log(Log.DEBUG, "ClaimAction",
					"addFirstClaimsPageDetails()", "File data length "
							+ data.length);
		}

	}

	/*
	 * Uploading the Legal Attachment
	 */
	FormFile legalfile = (FormFile) claimForm.getLegalAttachmentPath();
	if (legalfile != null) {
		contextPath = request.getSession().getServletContext()
				.getRealPath("");
		nameOfFile = ClaimConstants.CLM_LEGAL_ATTACHMENT + currentTime
				+ ClaimConstants.CLM_NO_VALUE + mId + bid;
		// Uploading the file with the new name
		Log.log(Log.INFO, "ClaimAction", "addFirstClaimsPageDetails()",
				"Uploading Legal Details the attachment....");

		byte[] data = legalfile.getFileData();
		claimApplication.setLegalDetailsFileData(data);
		claimApplication.setLegalDetailsFileName(legalfile.getFileName());

		uploadFile(legalfile, contextPath, nameOfFile);

		Log.log(Log.DEBUG, "ClaimAction", "addFirstClaimsPageDetails()",
				"File Name, File data " + legalfile.getFileName() + ","
						+ data);

		if (data != null) {
			Log.log(Log.DEBUG, "ClaimAction",
					"addFirstClaimsPageDetails()", "File data length "
							+ data.length);
		}
	}

	
	
	TermLoanCapitalLoanDetail tcLoanDetail = null;
	
	Vector termCapitalDtls = new Vector();
	Vector cgpans = claimForm.getCgpansVector();
	Vector keys = new Vector();
	while (tclastDisbursementDtsIterator.hasNext()) {
		String key = (String) tclastDisbursementDtsIterator.next();
		if (!keys.contains(key)) {
			keys.addElement(key);
		}
	}

	for (int i = 0; i < keys.size(); i++) {
		tcLoanDetail = new TermLoanCapitalLoanDetail();

		// Capturing the key
		String key = (String) keys.elementAt(i);
		if (key == null) {
			continue;
		}
		tcLoanDetail.setCgpan((String) claimForm.getCgpandetails(key));

		// Capturing the last disbursement date
		String date = (String) claimForm.getLastDisbursementDate(key);
		tcLoanDetail.setLastDisbursementDate(sdf.parse(date,
				new ParsePosition(0)));

		// capturing total disbursed amount
		String disbAmnt = (String) claimForm.getTotalDisbursementAmt(key);
		if ("".equals(disbAmnt) || disbAmnt == null) {
			disbAmnt = "0.0";
		}
		tcLoanDetail.setTotaDisbAmnt(Double.parseDouble(disbAmnt));

		// Capturing Principal
		String principl = (String) claimForm.getTcprincipal(key);
		if ((principl.equals("")) || (principl == null)) {
			principl = "0.0";
		}
		tcLoanDetail.setPrincipalRepayment(Double.parseDouble(principl));

		// Capturing Interest Charges
		String interestCharges = (String) claimForm
				.getTcInterestCharge(key);
		if ((interestCharges.equals("")) || (interestCharges == null)) {
			interestCharges = "0.0";
		}
		tcLoanDetail.setInterestAndOtherCharges(Double
				.parseDouble(interestCharges));

		// Capturing Outstanding as on dt of npa
		String osnpa = (String) claimForm.getTcOsAsOnDateOfNPA(key);
		if ((osnpa.equals("")) || (osnpa == null)) {
			osnpa = "0.0";
		}
		tcLoanDetail.setOutstandingAsOnDateOfNPA(Double.parseDouble(osnpa));

		// Capturing outstanding as stated in civil suit
		String oscivilsuit = (String) claimForm
				.getTcOsAsStatedInCivilSuit(key);
		if ((oscivilsuit.equals("")) || (oscivilsuit == null)) {
			oscivilsuit = "0.0";
		}
		tcLoanDetail.setOutstandingStatedInCivilSuit(Double
				.parseDouble(oscivilsuit));

		// Capturing outstanding as on date of lodgement
		String oslodgement = (String) claimForm
				.getTcOsAsOnLodgementOfClaim(key);
		if ((oslodgement.equals("")) || (oslodgement == null)) {
			oslodgement = "0.0";
		}
		tcLoanDetail.setOutstandingAsOnDateOfLodgement(Double
				.parseDouble(oslodgement));

		// capturing tc claim flag
		String tcClaimFlag = (String) claimForm.getClaimFlagsTc(key);
		tcLoanDetail.setTcClaimFlag(tcClaimFlag);

		if (!termCapitalDtls.contains(tcLoanDetail)) {
			termCapitalDtls.addElement(tcLoanDetail);
		}
	}
	tcLoanDetail = null;
	// Capturing the Working Capital Applications Details
	ArrayList workingCapitalDtls = new ArrayList();
	Map wcOsAsOnNPADtls = claimForm.getWcOsAsOnDateOfNPA();
	Set wcOsAsOnNPADtlsSet = wcOsAsOnNPADtls.keySet();
	Iterator wcOsAsOnNPADtlsIterator = wcOsAsOnNPADtlsSet.iterator();
	Vector wcKeys = new Vector();
	while (wcOsAsOnNPADtlsIterator.hasNext()) {
		String key = (String) wcOsAsOnNPADtlsIterator.next();
		if (!wcKeys.contains(key)) {
			wcKeys.addElement(key);
		}
	}
	WorkingCapitalDetail wcDetail = null;
	for (int i = 0; i < wcKeys.size(); i++) {
		wcDetail = new WorkingCapitalDetail();
		wcDetail.setCgpan((String) claimForm.getWcCgpan((String) wcKeys
				.elementAt(i)));

		// Capturing outstanding as on npa
		String osnpawc = (String) claimForm
				.getWcOsAsOnDateOfNPA((String) wcKeys.elementAt(i));
		if ((osnpawc.equals("")) || (osnpawc == null)) {
			osnpawc = "0.0";
		}
		wcDetail.setOutstandingAsOnDateOfNPA(Double.parseDouble(osnpawc));

		// Capturing outstanding as in civil suit
		String oswccivilsuit = (String) claimForm
				.getWcOsAsStatedInCivilSuit((String) wcKeys.elementAt(i));
		if ((oswccivilsuit.equals("")) || (oswccivilsuit == null)) {
			oswccivilsuit = "0.0";
		}
		wcDetail.setOutstandingStatedInCivilSuit(Double
				.parseDouble(oswccivilsuit));

		// Capturing outstanding as on dt of lodgement
		String oswclodgement = (String) claimForm
				.getWcOsAsOnLodgementOfClaim((String) wcKeys.elementAt(i));
		if ((oswclodgement.equals("")) || (oswclodgement == null)) {
			oswclodgement = "0.0";
		}
		wcDetail.setOutstandingAsOnDateOfLodgement(Double
				.parseDouble(oswclodgement));

		// capturing wc claim flag
		String wcClaimFlag = (String) claimForm
				.getClaimFlagsWc((String) wcKeys.elementAt(i));
		wcDetail.setWcClaimFlag(wcClaimFlag);

		// Adding the workingcapitaldtl object to the vector
		if (!workingCapitalDtls.contains(wcDetail)) {
			workingCapitalDtls.add(wcDetail);
		}
	}
	wcDetail = null;
	// Capturing Security and Personal Guarantee Details
	Map asOnDtOfSanctionDtls = claimForm.getAsOnDtOfSanctionDtl();
	Set asOnDtOfSanctionDtlsSet = asOnDtOfSanctionDtls.keySet();
	Iterator asOnDtOfSanctionDtlsIterator = asOnDtOfSanctionDtlsSet
			.iterator();
	DtlsAsOnDateOfSanction dtlsAsOnSanctionDt = new DtlsAsOnDateOfSanction();
	while (asOnDtOfSanctionDtlsIterator.hasNext()) {
		String key = (String) asOnDtOfSanctionDtlsIterator.next();
		if ((key.trim())
				.equals(ClaimConstants.CLM_SAPGD_REASONS_FOR_REDUCTION)) {
			dtlsAsOnSanctionDt.setReasonsForReduction((String) claimForm
					.getAsOnDtOfSanctionDtl(key));
		}
		if ((key.trim())
				.equals(ClaimConstants.CLM_SAPGD_PARTICULAR_CUR_ASSETS)) {
			String currntassetssanction = (String) claimForm
					.getAsOnDtOfSanctionDtl(key);
			if ((currntassetssanction.equals(""))
					|| (currntassetssanction == null)) {
				currntassetssanction = "0.0";
			}
			dtlsAsOnSanctionDt.setValueOfCurrentAssets(Double
					.parseDouble(currntassetssanction));
		}
		if ((key.trim()).equals(ClaimConstants.CLM_SAPGD_PARTICULAR_LAND)) {
			String landasonsanction = (String) claimForm
					.getAsOnDtOfSanctionDtl(key);
			if ((landasonsanction.equals("")) || (landasonsanction == null)) {
				landasonsanction = "0.0";
			}
			dtlsAsOnSanctionDt.setValueOfLand(Double
					.parseDouble(landasonsanction));
		}
		if ((key.trim()).equals(ClaimConstants.CLM_SAPGD_PARTICULAR_MC)) {
			String machinesanction = (String) claimForm
					.getAsOnDtOfSanctionDtl(key);
			if ((machinesanction.equals("")) || (machinesanction == null)) {
				machinesanction = "0.0";
			}
			dtlsAsOnSanctionDt.setValueOfMachine(Double
					.parseDouble(machinesanction));
		}
		if ((key.trim()).equals(ClaimConstants.CLM_SAPGD_PARTICULAR_BLDG)) {
			String bldgsanction = (String) claimForm
					.getAsOnDtOfSanctionDtl(key);
			if ((bldgsanction.equals("")) || (bldgsanction == null)) {
				bldgsanction = "0.0";
			}
			dtlsAsOnSanctionDt.setValueOfBuilding(Double
					.parseDouble(bldgsanction));
		}
		if ((key.trim()).equals(ClaimConstants.CLM_SAPGD_PARTICULAR_OTHERS)) {
			String otherssanction = (String) claimForm
					.getAsOnDtOfSanctionDtl(key);
			if ((otherssanction.equals("")) || (otherssanction == null)) {
				otherssanction = "0.0";
			}
			dtlsAsOnSanctionDt.setValueOfOthers(Double
					.parseDouble(otherssanction));
		}
		if ((key.trim())
				.equals(ClaimConstants.CLM_SAPGD_PARTICULAR_OTHER_FIXED_MOV_ASSETS)) {
			String otherassets = (String) claimForm
					.getAsOnDtOfSanctionDtl(key);
			if ((otherassets.equals("")) || (otherassets == null)) {
				otherassets = "0.0";
			}
			dtlsAsOnSanctionDt.setValueOfOtherFixedMovableAssets(Double
					.parseDouble(otherassets));
		}
		if ((key.trim())
				.equals(ClaimConstants.CLM_SAPGD_NETWORTH_OF_GUARANTOR)) {
			String networthsanction = (String) claimForm
					.getAsOnDtOfSanctionDtl(key);
			if ((networthsanction.equals("")) || (networthsanction == null)) {
				networthsanction = "0.0";
			}
			dtlsAsOnSanctionDt.setNetworthOfGuarantors(Double
					.parseDouble(networthsanction));
		}
	}
	Map asOnNPADtls = claimForm.getAsOnDtOfNPA();
	Set asOnNPADtlsSet = asOnNPADtls.keySet();
	Iterator asOnNPADtlsIterator = asOnNPADtlsSet.iterator();
	DtlsAsOnDateOfNPA dtlsAsOnNPA = new DtlsAsOnDateOfNPA();
	while (asOnNPADtlsIterator.hasNext()) {
		String key = (String) asOnNPADtlsIterator.next();
		if ((key.trim())
				.equals(ClaimConstants.CLM_SAPGD_REASONS_FOR_REDUCTION)) {
			dtlsAsOnNPA.setReasonsForReduction((String) claimForm
					.getAsOnDtOfNPA(key));
		}
		if ((key.trim())
				.equals(ClaimConstants.CLM_SAPGD_PARTICULAR_CUR_ASSETS)) {
			String currentassetsnpa = (String) claimForm
					.getAsOnDtOfNPA(key);
			if ((currentassetsnpa.equals("")) || (currentassetsnpa == null)) {
				currentassetsnpa = "0.0";
			}
			dtlsAsOnNPA.setValueOfCurrentAssets(Double
					.parseDouble(currentassetsnpa));
		}
		if ((key.trim()).equals(ClaimConstants.CLM_SAPGD_PARTICULAR_LAND)) {
			String landnpa = (String) claimForm.getAsOnDtOfNPA(key);
			if ((landnpa.equals("")) || (landnpa == null)) {
				landnpa = "0.0";
			}
			dtlsAsOnNPA.setValueOfLand(Double.parseDouble(landnpa));
		}
		if ((key.trim()).equals(ClaimConstants.CLM_SAPGD_PARTICULAR_MC)) {
			String machinenpa = (String) claimForm.getAsOnDtOfNPA(key);
			if ((machinenpa.equals("")) || (machinenpa == null)) {
				machinenpa = "0.0";
			}
			dtlsAsOnNPA.setValueOfMachine(Double.parseDouble(machinenpa));
		}
		if ((key.trim()).equals(ClaimConstants.CLM_SAPGD_PARTICULAR_BLDG)) {
			String bldgnpa = (String) claimForm.getAsOnDtOfNPA(key);
			if ((bldgnpa.equals("")) || (bldgnpa == null)) {
				bldgnpa = "0.0";
			}
			dtlsAsOnNPA.setValueOfBuilding(Double.parseDouble(bldgnpa));
		}
		if ((key.trim()).equals(ClaimConstants.CLM_SAPGD_PARTICULAR_OTHERS)) {
			String othersnpa = (String) claimForm.getAsOnDtOfNPA(key);
			if ((othersnpa.equals("")) || (othersnpa == null)) {
				othersnpa = "0.0";
			}
			dtlsAsOnNPA.setValueOfOthers(Double.parseDouble(othersnpa));
		}
		if ((key.trim())
				.equals(ClaimConstants.CLM_SAPGD_PARTICULAR_OTHER_FIXED_MOV_ASSETS)) {
			String otherassets = (String) claimForm.getAsOnDtOfNPA(key);
			if ((otherassets.equals("")) || (otherassets == null)) {
				otherassets = "0.0";
			}
			dtlsAsOnNPA.setValueOfOtherFixedMovableAssets(Double
					.parseDouble(otherassets));
		}
		if ((key.trim())
				.equals(ClaimConstants.CLM_SAPGD_NETWORTH_OF_GUARANTOR)) {
			String networthnpa = (String) claimForm.getAsOnDtOfNPA(key);
			if ((networthnpa.equals("")) || (networthnpa == null)) {
				networthnpa = "0.0";
			}
			dtlsAsOnNPA.setNetworthOfGuarantors(Double
					.parseDouble(networthnpa));
		}
	}
	Map asOnLodgemntDtls = claimForm.getAsOnLodgemntOfCredit();
	Set asOnLodgemntDtlsSet = asOnLodgemntDtls.keySet();
	Iterator asOnLodgemntDtlsIterator = asOnLodgemntDtlsSet.iterator();
	DtlsAsOnLogdementOfClaim dtlsAsOnLodgemnt = new DtlsAsOnLogdementOfClaim();
	while (asOnLodgemntDtlsIterator.hasNext()) {
		String key = (String) asOnLodgemntDtlsIterator.next();
		if ((key.trim())
				.equals(ClaimConstants.CLM_SAPGD_REASONS_FOR_REDUCTION)) {
			dtlsAsOnLodgemnt.setReasonsForReduction((String) claimForm
					.getAsOnLodgemntOfCredit(key));
		}
		if ((key.trim())
				.equals(ClaimConstants.CLM_SAPGD_PARTICULAR_CUR_ASSETS)) {
			String currentassetslodgemnt = (String) claimForm
					.getAsOnLodgemntOfCredit(key);
			if ((currentassetslodgemnt.equals(""))
					|| (currentassetslodgemnt == null)) {
				currentassetslodgemnt = "0.0";
			}
			dtlsAsOnLodgemnt.setValueOfCurrentAssets(Double
					.parseDouble(currentassetslodgemnt));
		}
		if ((key.trim()).equals(ClaimConstants.CLM_SAPGD_PARTICULAR_LAND)) {
			String landlodgemnt = (String) claimForm
					.getAsOnLodgemntOfCredit(key);
			if ((landlodgemnt.equals("")) || (landlodgemnt == null)) {
				landlodgemnt = "0.0";
			}
			dtlsAsOnLodgemnt.setValueOfLand(Double
					.parseDouble(landlodgemnt));
		}
		if ((key.trim()).equals(ClaimConstants.CLM_SAPGD_PARTICULAR_MC)) {
			String machinelodgemnt = (String) claimForm
					.getAsOnLodgemntOfCredit(key);
			if ((machinelodgemnt.equals("")) || (machinelodgemnt == null)) {
				machinelodgemnt = "0.0";
			}
			dtlsAsOnLodgemnt.setValueOfMachine(Double
					.parseDouble(machinelodgemnt));
		}
		if ((key.trim()).equals(ClaimConstants.CLM_SAPGD_PARTICULAR_BLDG)) {
			String bldglodgemnt = (String) claimForm
					.getAsOnLodgemntOfCredit(key);
			if ((bldglodgemnt.equals("")) || (bldglodgemnt == null)) {
				bldglodgemnt = "0.0";
			}
			dtlsAsOnLodgemnt.setValueOfBuilding(Double
					.parseDouble(bldglodgemnt));
		}
		if ((key.trim()).equals(ClaimConstants.CLM_SAPGD_PARTICULAR_OTHERS)) {
			String otherslodgemnt = (String) claimForm
					.getAsOnLodgemntOfCredit(key);
			if ((otherslodgemnt.equals("")) || (otherslodgemnt == null)) {
				otherslodgemnt = "0.0";
			}
			dtlsAsOnLodgemnt.setValueOfOthers(Double
					.parseDouble(otherslodgemnt));
		}
		if ((key.trim())
				.equals(ClaimConstants.CLM_SAPGD_PARTICULAR_OTHER_FIXED_MOV_ASSETS)) {
			String otherassetslodgemnt = (String) claimForm
					.getAsOnLodgemntOfCredit(key);
			if ((otherassetslodgemnt.equals(""))
					|| (otherassetslodgemnt == null)) {
				otherassetslodgemnt = "0.0";
			}
			dtlsAsOnLodgemnt.setValueOfOtherFixedMovableAssets(Double
					.parseDouble(otherassetslodgemnt));
		}
		if ((key.trim())
				.equals(ClaimConstants.CLM_SAPGD_NETWORTH_OF_GUARANTOR)) {
			String networthlodgemnt = (String) claimForm
					.getAsOnLodgemntOfCredit(key);
			if ((networthlodgemnt.equals("")) || (networthlodgemnt == null)) {
				networthlodgemnt = "0.0";
			}
			dtlsAsOnLodgemnt.setNetworthOfGuarantors(Double
					.parseDouble(networthlodgemnt));
		}
	}
	// Capturing the recovery details
	Vector recoveryDetailsVector = new Vector();
	Map recoveryDetails = claimForm.getCgpandetails();
	Set recoveryDetailsSet = recoveryDetails.keySet();
	Iterator recoveryDetailsIterator = recoveryDetailsSet.iterator();
	StringTokenizer tokenzier = null;
	RecoveryDetails recDetails = null;

	Vector cgpanKeys = new Vector();
	Vector recoveryKeys = new Vector();
	 System.out.println("<br>===555===");
	String tcPrincipal = "tcprincipal".trim();
	String tcInterestCharges = "tcInterestCharges".trim();
	String wcAmount = "wcAmount".trim();
	String wcOtherCharges = "wcOtherCharges".trim();
	String recoveryMode = "recoveryMode".trim();
	String substring = "recovery#key-".trim();
	while (recoveryDetailsIterator.hasNext()) {
		String key = (String) recoveryDetailsIterator.next();
		if (key != null) {
			if ((key.indexOf(substring)) == 0) {
				if (!(cgpanKeys.contains(key))) {
					cgpanKeys.addElement(key);
				}

			}
			if ((key.indexOf(substring)) > 0) {
				if (!(recoveryKeys.contains(key))) {
					recoveryKeys.addElement(key);
				}
			}
		}
	}
	for (int j = 0; j < cgpanKeys.size(); j++) {
		recDetails = new RecoveryDetails();
		String cgpanKey = (String) cgpanKeys.elementAt(j);
		if (cgpanKey == null) {
			continue;
		}
		recDetails.setCgpan((String) claimForm.getCgpandetails(cgpanKey));
		// String cgpan = recDetails.getCgpan();

		for (int i = 0; i < recoveryKeys.size(); i++) {
			String recoveryKey = (String) recoveryKeys.elementAt(i);
			if (recoveryKey == null) {
				continue;
			}
			if (((recoveryKey.indexOf(tcPrincipal)) == 0)
					&& ((recoveryKey.indexOf(cgpanKey)) > 0)) {
				String recoverypricipaltc = (String) claimForm
						.getCgpandetails(recoveryKey);
				if ((recoverypricipaltc.equals(""))
						|| (recoverypricipaltc == null)) {
					recoverypricipaltc = "0.0";
				}
				recDetails.setTcPrincipal(Double
						.parseDouble(recoverypricipaltc));

			}
			if (((recoveryKey.indexOf(tcInterestCharges)) == 0)
					&& ((recoveryKey.indexOf(cgpanKey)) > 0)) {
				String recinterestchargestc = (String) claimForm
						.getCgpandetails(recoveryKey);
				if ((recinterestchargestc.equals(""))
						|| (recinterestchargestc == null)) {
					recinterestchargestc = "0.0";
				}
				recDetails.setTcInterestAndOtherCharges(Double
						.parseDouble(recinterestchargestc));
			}
			if (((recoveryKey.indexOf(wcAmount)) == 0)
					&& ((recoveryKey.indexOf(cgpanKey)) > 0)) {
				String wcamount = (String) claimForm
						.getCgpandetails(recoveryKey);
				if ((wcamount.equals("")) || (wcamount == null)) {
					wcamount = "0.0";
				}
				recDetails.setWcAmount(Double.parseDouble(wcamount));
			}
			if (((recoveryKey.indexOf(wcOtherCharges)) == 0)
					&& ((recoveryKey.indexOf(cgpanKey)) > 0)) {
				String wcothercharges = (String) claimForm
						.getCgpandetails(recoveryKey);
				if ((wcothercharges.equals("")) || (wcothercharges == null)) {
					wcothercharges = "0.0";
				}
				recDetails.setWcOtherCharges(Double
						.parseDouble(wcothercharges));
			}
			if (((recoveryKey.indexOf(recoveryMode)) == 0)
					&& ((recoveryKey.indexOf(cgpanKey)) > 0)) {
				String temp = (String) claimForm
						.getCgpandetails(recoveryKey);
				// //System.out.println("Recovery Mode :" + temp);
				recDetails.setModeOfRecovery(temp);
			}

		}
		String modeOfRec = recDetails.getModeOfRecovery();
		if ((modeOfRec != null) && (!modeOfRec.equals(""))) {
			if (!recoveryDetailsVector.contains(recDetails)) {
				recoveryDetailsVector.addElement(recDetails);
			}
		}
	}

	// Displaying and capturing the claim summary details
	ArrayList claimSummaryDtlsArrayList = new ArrayList();
	ClaimSummaryDtls summaryDtls = null;
	Map claimSummaryDtls = claimForm.getClaimSummaryDetails();
	Set claimSummaryDtlsSet = claimSummaryDtls.keySet();
	Iterator claimSummaryDtlsIterator = claimSummaryDtlsSet.iterator();
	// String claimString ="cgpan".trim();
	// String amountClaimedString ="amountClaimed".trim();
	// Vector summaryKeys = new Vector();
	double guaranteeApprovedAmt=0.0;
	while (claimSummaryDtlsIterator.hasNext()) {
		summaryDtls = new ClaimSummaryDtls();
		String key = (String) claimSummaryDtlsIterator.next();
		summaryDtls.setCgpan(key);
		RpDAO rpDAO = new RpDAO();
		double approvedAmt = rpDAO.getTotalSanctionedAmount(key);
		System.out.println("Cgpan Value:"+key);
		String amntClaimed = (String) claimForm.getClaimSummaryDetails(key);
		System.out.println("5348 - approvedAmt:"+approvedAmt+" amntClaimed:"+amntClaimed);
		guaranteeApprovedAmt = rpDAO.getTotalSanctionedAmountforCgpan(key);
		System.out.println("<br>===556===");
		 int isNorthEast = rpDAO
			.isNorthEastRegion(key);
		 
		 if(isNorthEast==1)
		 {
			 isNorthEastRegion="yes";
		 }
		 else
		 {
			 isNorthEastRegion="no";
		 }
		 
		 
		 int isWomen = rpDAO
			.isWomenorNot(key);
		 if(isWomen==1)
		 {
			 isWomenorNot="yes";
		 }
		 else
		 {
			 isWomenorNot="no";
		 }
		 
		 //raju end
		if ((amntClaimed.equals("")) || (amntClaimed == null)) {
			amntClaimed = "0.0";
			System.out.println("<br>===559===");
		}
		if (guaranteeApprovedAmt < Double.parseDouble(amntClaimed)) {
			throw new DatabaseException(
					"Claim Applied Amount for "
							+ key
							+ " is "
							+ amntClaimed
							+ " which can not be more than Loan / Limit Covered under CGFSI : "
							+ guaranteeApprovedAmt);
		}

		System.out.println("Line number 4445 key: "+key+" approvedAmt :"+approvedAmt+" amntClaimed: "+amntClaimed);

		summaryDtls.setAmount(Double.parseDouble(amntClaimed));
		if (!claimSummaryDtlsArrayList.contains(summaryDtls)) {
			claimSummaryDtlsArrayList.add(summaryDtls);
		}
	}
	// Building the claim application object
	claimApplication.setMemberId(mId);
	claimApplication.setBorrowerId(bid);
	System.out.println("ClamRefNum:"+claimForm.getClmRefNumberNew());
	claimApplication.setClaimRefNumber(claimForm.getClmRefNumberNew());
	System.out.println("bid:"+bid);
	claimApplication.setDateOfIssueOfRecallNotice(recallnoticedate);

	// Building the legalproceedingsdetail object
	LegalProceedingsDetail lpd = new LegalProceedingsDetail();
	lpd.setBorrowerId(bid);
	lpd.setCommHeadedByOfficerOrAbove(claimForm.getCommHeadedByOfficerOrAbove());
	lpd.setForumRecoveryProceedingsInitiated(forumthruwhichinitiated);
	lpd.setSuitCaseRegNumber(casesuitregnumber);
	lpd.setNameOfForum(nameofforum);
	lpd.setFilingDate(filingdate);
	lpd.setLocation(location);
	if ((amntClaimed1 != null) && (!(amntClaimed1.equals("")))) {
		lpd.setAmountClaimed(Double.parseDouble(amntClaimed1));
	}
	lpd.setCurrentStatusRemarks(currentstatusremarks);
	lpd.setIsRecoveryProceedingsConcluded(recoveryprocconcluded);

	claimApplication.setLegalProceedingsDetails(lpd);
	claimApplication.setDateOfReleaseOfWC(dateofreleasewc);
	SecurityAndPersonalGuaranteeDtls sapg = new SecurityAndPersonalGuaranteeDtls();
	sapg.setDetailsAsOnDateOfSanction(dtlsAsOnSanctionDt);
	sapg.setDetailsAsOnDateOfNPA(dtlsAsOnNPA);
	sapg.setDetailsAsOnDateOfLodgementOfClaim(dtlsAsOnLodgemnt);
	claimApplication.setSecurityAndPersonalGuaranteeDtls(sapg);

	java.util.Date dtofseekofotsutilformat = null;
	if (dtofseekingofots != null && dtofseekingofots.equals("")) {
		dtofseekofotsutilformat = sdf.parse(dtofseekingofots,
				new ParsePosition(0));
	}
	claimApplication.setDateOfSeekingOTS(dtofseekofotsutilformat);
	claimApplication.setRecoveryDetails(recoveryDetailsVector);
	claimApplication.setTermCapitalDtls(termCapitalDtls);
	claimApplication.setWorkingCapitalDtls(workingCapitalDtls);
	claimApplication.setClaimSummaryDtls(claimSummaryDtlsArrayList);

	claimApplication.setMemberDetails(info);
	claimApplication.setUnitAssistedMSE(microFlag);
	claimApplication.setWilful(wilful);
	claimApplication.setFraudFlag(fraudFlag);
	claimApplication.setEnquiryFlag(enquiryFlag);
	claimApplication.setMliInvolvementFlag(mliInvolvementFlag);
	claimApplication.setReasonForRecall(reasonForRecall);
	claimApplication.setReasonForFilingSuit(reasonForFilingSuit);
	claimApplication.setAssetPossessionDt(assetPossessionDt);
	claimApplication.setInclusionOfReceipt(inclusionOfReceipt);
	claimApplication.setConfirmRecoveryFlag(confirmRecoveryFlag);
	claimApplication.setSubsidyFlag(subsidyFlag);
	claimApplication.setIsSubsidyRcvdAfterNpa(subsidyRcvd);
	claimApplication.setIsSubsidyAdjustedOnDues(subsidyAdjusted);
	claimApplication.setSubsidyAmt(subsidyAmt);
	claimApplication.setSubsidyDate(subsidyDate);
	claimApplication.setMliCommentOnFinPosition(comments);
	claimApplication.setDetailsOfFinAssistance(finAssistance);
	claimApplication.setCreditSupport(creditSupport);

	
	claimApplication.setBankFacilityDetail(bankFacility);
	claimApplication.setPlaceUnderWatchList(watchListPlace);
	claimApplication.setRemarksOnNpa(remarks);
	claimApplication.setDealingOfficerName(officerName);
	claimForm.setClaimapplication(claimApplication);
   claimForm.setTotGuaranteeAmt(guaranteeApprovedAmt);
	
	claimForm.setTotGuaranteeAmt(guaranteeApprovedAmt);
	
	claimForm.setIsNorthEastRegion(isNorthEastRegion);
	
	claimForm.setIsWomenorNot(isWomenorNot);
	/*Changes for GST */
	/*String gstState = claimForm.getGstState();
	String gstNo = request.getParameter("gstNo");*/
	HttpSession session1=request.getSession();		
	session1.setAttribute("gstNo", request.getParameter("gstNo"));
	session1.setAttribute("gstStateCode", claimForm.getGstState());
	
	
	
	System.out.println("<br>===560===");


	sdf = null;
	mId = null;
	bid = null;
	dtOfRecallNoticeStr = null;
	recallnoticedate = null;
	forumthruwhichinitiated = null;
	casesuitregnumber = null;
	nameofforum = null;
	dtOfFilingStr = null;
	filingdate = null;
	location = null;
	amntClaimed1 = null;
	currentstatusremarks = null;
	recoveryprocconcluded = null;
	dtOfReleaseOfWCStr = null;
	dateofreleasewc = null;
	dtofseekingofots = null;
	tclastDisbursementDts = null;
	tclastDisbursementDtsSet = null;
	tclastDisbursementDtsIterator = null;
	contextPath = null;
	nameOfFile = null;
	currentTime = null;
	file = null;
	legalfile = null;
	tcLoanDetail = null;
	termCapitalDtls = null;
	cgpans = null;
	keys = null;
	workingCapitalDtls = null;
	wcOsAsOnNPADtls = null;
	wcOsAsOnNPADtlsSet = null;
	wcOsAsOnNPADtlsIterator = null;
	wcKeys = null;
	wcDetail = null;
	asOnDtOfSanctionDtls = null;
	asOnDtOfSanctionDtlsSet = null;
	asOnDtOfSanctionDtlsIterator = null;
	dtlsAsOnSanctionDt = null;
	asOnNPADtls = null;
	asOnNPADtlsSet = null;
	asOnNPADtlsIterator = null;
	dtlsAsOnNPA = null;
	asOnLodgemntDtls = null;
	asOnLodgemntDtlsSet = null;
	asOnLodgemntDtlsIterator = null;
	dtlsAsOnLodgemnt = null;
	recoveryDetailsVector = null;
	recoveryDetails = null;
	recoveryDetailsSet = null;
	recoveryDetailsIterator = null;
	tokenzier = null;
	recDetails = null;
	cgpanKeys = null;
	recoveryKeys = null;
	tcPrincipal = null;
	tcInterestCharges = null;
	wcAmount = null;
	wcOtherCharges = null;
	recoveryMode = null;
	substring = null;
	claimSummaryDtlsArrayList = null;
	summaryDtls = null;
	claimSummaryDtls = null;
	claimSummaryDtlsSet = null;
	claimSummaryDtlsIterator = null;
	claimForm.resetDisclaimerPage(mapping, request);
	}
	
	//rajuk
	catch(Exception e){
		// e.printStackTrace();
		 throw new DatabaseException(e.getMessage());
	}
	finally {
       DBConnection.freeConnection(connection);
   }
	
	
	if(!(clmrefno==null))
	
	{
	  Statement stmt = connection.createStatement();
	  ResultSet rs ;
     int ischeklist =0;
	  int noOfClaims = 0;
	  String query  ="";
	  String status  ="";	
	 
	
	 query = "select count(*) from claim_check_list where clm_ref_no='"+clmrefno+"'";
	  rs=stmt.executeQuery(query);

	 System.out.println(query);
	 
	 while(rs.next())
	  {
	        
		 ischeklist = rs.getInt(1);
	      }
	
	 if(ischeklist>0)
	
	 {
		 System.out.println("1rajukkk");
	//return mapping.findForward("success");
	return mapping.findForward("nextclaimdetailspage");
	
	
	 
}
else
{
	 System.out.println("2raaa");
	return mapping.findForward("nextclaimPagewithoutchecklist");
	
	
	 
	
}
	}
	 System.out.println("3rjjjj");
	return mapping.findForward("nextclaimdetailspage1");

}
	
	
	
	//calling in Resub Save Method
	public ActionForward updateClaimApplicationforResub(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	

	ClaimActionForm claimForm = (ClaimActionForm) form;
	ClaimApplication claimapplication = claimForm.getClaimapplication();
	ClaimsProcessor processor = new ClaimsProcessor();
	String nameofofficial = claimForm.getNameOfOffi();
	String designation = claimForm.getDesigna();
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	HashMap itpanDetails = (HashMap) claimForm.getItpanDetails();
	String claimsubmitteddt = claimForm.getClaimSubmittedDate();
	String place = claimForm.getPlaces();
	//rajuk
	String iseligact = claimForm.getIseligact();
	String iseligactcomm = claimForm.getIseligactcomm();
			
	String whetcibildone = claimForm.getWhetcibildone();
	String whetcibildonecomm = claimForm.getWhetcibildonecomm();
	String isrataspercgs = claimForm.getIsrataspercgs();
	String isrataspercgscomm = claimForm.getIsrataspercgscomm();
	String isthirdcollattaken = claimForm.getIsthirdcollattaken();
	String isthirdcollattakencomm = claimForm.getIsthirdcollattakencomm();
	String isnpadtasperguid = claimForm.getIsnpadtasperguid();
	String isnpadtasperguidcomm = claimForm.getIsnpadtasperguidcomm();
	String isclmoswrtnpadt = claimForm.getIsclmoswrtnpadt();
	String isclmoswrtnpadtcomm = claimForm.getIsclmoswrtnpadtcomm();
	String whetseriousdeficinvol = claimForm.getWhetseriousdeficinvol();
	String whetseriousdeficinvolcomm = claimForm.getWhetseriousdeficinvolcomm();
	String whetmajordeficinvolvd = claimForm.getWhetmajordeficinvolvd();
	String whetmajordeficinvolvdcomm = claimForm.getWhetmajordeficinvolvdcomm();
	String whetdeficinvolbystaff = claimForm.getWhetdeficinvolbystaff();
	String whetdeficinvolbystaffcomm = claimForm.getWhetdeficinvolbystaffcomm();
	String isinternratinvestgrad = claimForm.getIsinternratinvestgrad();
	String isinternratinvestgradcomm = claimForm.getIsinternratinvestgradcomm();
	String isallrecinclmform = claimForm.getIsallrecinclmform();
	String isallrecinclmformcomm = claimForm.getIsallrecinclmformcomm();
	
	
	
	
	System.out.println("rajukonkati1"+claimForm.getIseligact());
	System.out.println("rajukonkati2"+claimForm.getIseligactcomm());
	System.out.println("rajukonkati1"+whetcibildone);
	System.out.println("rajukonkati2"+whetcibildonecomm);
	System.out.println("rajukonkati1"+isallrecinclmform);
	System.out.println("rajukonkati2"+isallrecinclmformcomm);
	
	
	//String view = claimForm.getView();
	// Get the borrower Id from the request object
	String borrowerId = ((claimForm.getBorrowerID()).toUpperCase()).trim();
	// //System.out.println("Borrower Id:"+borrowerId);

	claimapplication.setNameOfOfficial(nameofofficial);
	claimapplication.setDesignationOfOfficial(designation);
	claimapplication.setClaimSubmittedDate(sdf.parse(claimsubmitteddt,
			new ParsePosition(0)));
	claimapplication.setPlace(place);//
	//added by rajuk
	claimapplication.setIseligact(iseligact);
	claimapplication.setIseligactcomm(iseligactcomm);
	claimapplication.setWhetcibildone(whetcibildone);
	claimapplication.setWhetcibildonecomm(whetcibildonecomm);
	claimapplication.setIsrataspercgs(isrataspercgs);
	claimapplication.setIsrataspercgscomm(isrataspercgscomm);
	claimapplication.setIsthirdcollattaken(isthirdcollattaken);
	claimapplication.setIsthirdcollattakencomm(isthirdcollattakencomm);
	claimapplication.setIsnpadtasperguid(isnpadtasperguid);
	claimapplication.setIsnpadtasperguidcomm(isnpadtasperguidcomm);
	claimapplication.setIsclmoswrtnpadt(isclmoswrtnpadt);
	claimapplication.setIsclmoswrtnpadtcomm(isclmoswrtnpadtcomm);
	claimapplication.setWhetseriousdeficinvol(whetseriousdeficinvol);
	claimapplication.setWhetseriousdeficinvolcomm(whetseriousdeficinvolcomm);
	claimapplication.setWhetmajordeficinvolvd(whetmajordeficinvolvd);
	claimapplication.setWhetmajordeficinvolvdcomm(whetmajordeficinvolvdcomm);
	claimapplication.setWhetdeficinvolbystaff(whetdeficinvolbystaff);
	claimapplication.setWhetdeficinvolbystaffcomm(whetdeficinvolbystaffcomm);
	claimapplication.setIsinternratinvestgrad(isinternratinvestgrad);
	claimapplication.setIsinternratinvestgradcomm(isinternratinvestgradcomm);
	claimapplication.setIsallrecinclmform(isallrecinclmform);
	claimapplication.setIsallrecinclmformcomm(isallrecinclmformcomm);
	
	//ended by rajuk
	
	
	claimapplication.setFirstInstallment(true);
	/* added by sukumar@path on 13-08-2009 */

	String ifsCode = claimForm.getIfsCode();
	String neftCode = claimForm.getNeftCode();
	String rtgsBankName = claimForm.getRtgsBankName();
	String rtgsBranchName = claimForm.getRtgsBranchName();
	String rtgsBankNumber = claimForm.getRtgsBankNumber();
	String microCategory = claimForm.getMicroCategory();

	claimapplication.setIfsCode(ifsCode);
	claimapplication.setNeftCode(neftCode);
	claimapplication.setRtgsBankName(rtgsBankName);
	claimapplication.setRtgsBranchName(rtgsBranchName);
	claimapplication.setRtgsBankNumber(rtgsBankNumber);
	claimapplication.setMicroCategory(microCategory);
	String unitName = (String) claimForm.getBorrowerDetails()
			.getBorrowerName();
	
	/*Changes for GST */
	HttpSession session1=request.getSession();
	
	claimapplication.setGstNo((String)session1.getAttribute("gstNo"));

	claimapplication.setGstStateCode((String)session1.getAttribute("gstStateCode"));

	/*
	 * Added on 11/10/2004 to view the uploaded attachments. Done by
	 * Veldurai
	 */
	boolean internetUser = true;
	User userInfo = getUserInformation(request);
	String userId = userInfo.getUserId();
	claimapplication.setCreatedModifiedy(userId);
	// All users belong to member id 0000 0000 0000 are intranet users
	// except demo user.
	if ((userInfo.getBankId() + userInfo.getZoneId() + userInfo
			.getBranchId()).equals("000000000000")
			&& !userInfo.getUserId().equalsIgnoreCase("DEMOUSER")) {
		internetUser = false;
	}
	// claimapplication.setMemberId(claimForm.getMemberId());
	// claimapplication.setBorrowerId(claimForm.getBorrowerID());

	/* temporarly commented the generation of claim reference number */
	// String clmRefNumber= " ";
	String clmRefNumber = processor.updateClaimApplicationforResub(
			claimapplication, itpanDetails, internetUser);
	

	claimForm.setClmRefNumber(clmRefNumber);
	claimForm.setClmRefNumberNew(null);
	claimapplication.setClaimRefNumber(null);
	// claimapplication.setBorrowerId(null);
	// claimForm.setMemberId(claimapplication.getMemberId());
	HttpSession session = request.getSession();
	session.removeAttribute("RETURNREMARKS");
	claimForm.resetTheFirstClaimApplication(mapping, request);
	claimForm.setNameOfOfficial("");
	claimForm.setDesignationOfOfficial("");
	claimForm.setPlace("");
	claimForm.setForumthrulegalinitiated("");
	Log.log(Log.INFO, "ClaimAction", "saveClaimApplication", "Exited");
	request.setAttribute(
			"message",
			"Application for First Claim Installment for "
					+ borrowerId
					+ "\n has been saved successfully. Claim Reference Number is : "
					+ clmRefNumber);
	// return mapping.findForward("success");
	return mapping.findForward("submitClaim");
}
	 
	//end rajuk


	public ActionForward saveClaimApplication(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		try
		{
		ClaimActionForm claimForm = (ClaimActionForm) form;
		ClaimApplication claimapplication = claimForm.getClaimapplication();
		ClaimsProcessor processor = new ClaimsProcessor();
		String nameofofficial = claimForm.getNameOfOfficial();
		String designation = claimForm.getDesignationOfOfficial();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		HashMap itpanDetails = (HashMap) claimForm.getItpanDetails();
		String claimsubmitteddt = claimForm.getClaimSubmittedDate();
		String place = claimForm.getPlace();
		String view = claimForm.getView();//rajuk
		System.out.println("rajukonkatiii"+claimForm.getIseligact());
	
		//if(view)
		StringTokenizer st2 = new StringTokenizer(view, "-");
				
		String isActivityEligibleVal="";
		String isActivityEligibleValflag="";
		String whetherCibilval="";
		String whetherCibilvalflag="";
		String rateChargeVal="";
		String rateChargeValflag="";
		String thirdpartyGuaranteeVal="";
		String thirdpartyGuaranteeValflag="";
		String dateofNPAval="";
		String dateofNPAvalflag="";
		String outstandingAmountVal="";
		String outstandingAmountValflag="";
		String seriousDeficieniesVal="";
		String seriousDeficieniesValflag="";
		String majorDeficienciesObservedVal="";
		String majorDeficienciesObservedValflag="";
		String deficienciesObservedVal="";
		String deficienciesObservedValflag="";
		String internalRatingVal="";
		String internalRatingValflag="";
		String alltheRecoveriesVal="";
		String alltheRecoveriesValflag="";
		

		while (st2.hasMoreElements()) {
					
		String temp1=(String)st2.nextElement();
		StringTokenizer st3 = new StringTokenizer(temp1, ",");

			while (st3.hasMoreElements()) {
				 isActivityEligibleVal=(String)st3.nextElement();
				 isActivityEligibleValflag=(String)st3.nextElement();
				System.out.println("2)"+isActivityEligibleVal+"-----"+isActivityEligibleValflag);
			}
			
		String temp2=(String)st2.nextElement();
		StringTokenizer st4 = new StringTokenizer(temp2, ",");

			while (st4.hasMoreElements()) {
				 whetherCibilval=(String)st4.nextElement();
				 whetherCibilvalflag=(String)st4.nextElement();
				System.out.println("3)"+whetherCibilval+"-----"+whetherCibilvalflag);
			}
			
			String temp3=(String)st2.nextElement();
			StringTokenizer st5 = new StringTokenizer(temp3, ",");
			while (st5.hasMoreElements()) {
				 rateChargeVal=(String)st5.nextElement();
				 rateChargeValflag=(String)st5.nextElement();
				System.out.println("4)"+rateChargeVal+"-----"+rateChargeValflag);
			}
			
			
			String temp4=(String)st2.nextElement();
			StringTokenizer st6 = new StringTokenizer(temp4, ",");
			while (st6.hasMoreElements()) {
				 thirdpartyGuaranteeVal=(String)st6.nextElement();
				 thirdpartyGuaranteeValflag=(String)st6.nextElement();
				System.out.println("5)"+thirdpartyGuaranteeVal+"-----"+thirdpartyGuaranteeValflag);
			}
			
			String temp5=(String)st2.nextElement();
			StringTokenizer st7 = new StringTokenizer(temp5, ",");
			while (st7.hasMoreElements()) {
				 dateofNPAval=(String)st7.nextElement();
				 dateofNPAvalflag=(String)st7.nextElement();
				System.out.println("6)"+dateofNPAval+"-----"+dateofNPAvalflag);
			}
			
			String temp6=(String)st2.nextElement();
			StringTokenizer st8 = new StringTokenizer(temp6, ",");
			while (st8.hasMoreElements()) {
				 outstandingAmountVal=(String)st8.nextElement();
				 outstandingAmountValflag=(String)st8.nextElement();
				System.out.println("7)"+outstandingAmountVal+"-----"+outstandingAmountValflag);
			}
			
			String temp7=(String)st2.nextElement();
			StringTokenizer st9 = new StringTokenizer(temp7, ",");
			while (st9.hasMoreElements()) {
				 seriousDeficieniesVal=(String)st9.nextElement();
				 seriousDeficieniesValflag=(String)st9.nextElement();
				System.out.println("8)"+seriousDeficieniesVal+"-----"+seriousDeficieniesValflag);
			}
			
			String temp8=(String)st2.nextElement();
			StringTokenizer st10 = new StringTokenizer(temp8, ",");
			while (st10.hasMoreElements()) {
				 majorDeficienciesObservedVal=(String)st10.nextElement();
				 majorDeficienciesObservedValflag=(String)st10.nextElement();
				System.out.println("9)"+majorDeficienciesObservedVal+"-----"+majorDeficienciesObservedValflag);
			}
			
			String temp9=(String)st2.nextElement();
			StringTokenizer st11 = new StringTokenizer(temp9, ",");
			while (st11.hasMoreElements()) {
				 deficienciesObservedVal=(String)st11.nextElement();
				 deficienciesObservedValflag=(String)st11.nextElement();
				System.out.println("10)"+deficienciesObservedVal+"-----"+deficienciesObservedValflag);
			}
			
			String temp10=(String)st2.nextElement();
			StringTokenizer st12 = new StringTokenizer(temp10, ",");
			while (st12.hasMoreElements()) {
				 internalRatingVal=(String)st12.nextElement();
				 internalRatingValflag=(String)st12.nextElement();
				System.out.println("11)"+internalRatingVal+"-----"+internalRatingValflag);
			}
			
			String temp11=(String)st2.nextElement();
			StringTokenizer st13 = new StringTokenizer(temp11, ",");
			while (st13.hasMoreElements()) {
				 alltheRecoveriesVal=(String)st13.nextElement();
				 alltheRecoveriesValflag=(String)st13.nextElement();
				System.out.println("12)"+alltheRecoveriesVal+"-----"+alltheRecoveriesValflag);
			}
		
			
		}//rajuk
		
		System.out.println("rajukonkati"+view);//rajuk
			
		// Get the borrower Id from the request object
		String borrowerId = ((claimForm.getBorrowerID()).toUpperCase()).trim();
		// //System.out.println("Borrower Id:"+borrowerId);

		/*Changes for GST */
		HttpSession session1=request.getSession();		
		claimapplication.setGstNo((String)session1.getAttribute("gstNo"));
		claimapplication.setGstStateCode((String)session1.getAttribute("gstStateCode"));
		
		claimapplication.setNameOfOfficial(nameofofficial);
		claimapplication.setDesignationOfOfficial(designation);
		claimapplication.setClaimSubmittedDate(sdf.parse(claimsubmitteddt,
				new ParsePosition(0)));
		claimapplication.setPlace(place);
		
		
		claimapplication.setView(view);//rajuk
		
		claimapplication.setFirstInstallment(true);
		/* added by sukumar@path on 13-08-2009 */
		// java.util.Date subsidyDate =
		// (java.util.Date)claimForm.getSubsidyDate();
		// //System.out.println("subsidyDate:"+subsidyDate);
		// double subsidyAmt = claimForm.getSubsidyAmt();
		// //System.out.println("subsidyAmt:"+subsidyAmt);
		String ifsCode = claimForm.getIfsCode();
		// //System.out.println("ifsCode:"+ifsCode);
		String neftCode = claimForm.getNeftCode();
		// //System.out.println("neftCode:"+neftCode);
		String rtgsBankName = claimForm.getRtgsBankName();
		// //System.out.println("rtgsBankName:"+rtgsBankName);
		String rtgsBranchName = claimForm.getRtgsBranchName();
		// //System.out.println("rtgsBranchName:"+rtgsBranchName);
		String rtgsBankNumber = claimForm.getRtgsBankNumber();
		// //System.out.println("rtgsBankNumber:"+rtgsBankNumber);
		String microCategory = claimForm.getMicroCategory();
		
		//added rajuk
		
		
		//rajuk

		// claimapplication.setSubsidyDate(subsidyDate);
		// claimapplication.setSubsidyAmt(subsidyAmt);
		

		claimapplication.setIfsCode(ifsCode);
		claimapplication.setNeftCode(neftCode);
		claimapplication.setRtgsBankName(rtgsBankName);
		claimapplication.setRtgsBranchName(rtgsBranchName);
		claimapplication.setRtgsBankNumber(rtgsBankNumber);
		claimapplication.setMicroCategory(microCategory);
		String unitName = (String) claimForm.getBorrowerDetails()
				.getBorrowerName();
		//Added by rajuk
		claimapplication.setIsActivityEligibleVal(isActivityEligibleVal);
		claimapplication.setIsActivityEligibleValflag(isActivityEligibleValflag);
		claimapplication.setWhetherCibilval(whetherCibilval);
		claimapplication.setWhetherCibilvalflag(whetherCibilvalflag);
		claimapplication.setRateChargeVal(rateChargeVal);
		claimapplication.setRateChargeValflag(rateChargeValflag);
		claimapplication.setThirdpartyGuaranteeVal(thirdpartyGuaranteeVal);
		claimapplication.setThirdpartyGuaranteeValflag(thirdpartyGuaranteeValflag);
		claimapplication.setDateofNPAval(dateofNPAval);
		claimapplication.setDateofNPAvalflag(dateofNPAvalflag);
		claimapplication.setOutstandingAmountVal(outstandingAmountVal);
		claimapplication.setOutstandingAmountValflag(outstandingAmountValflag);
		claimapplication.setSeriousDeficieniesVal(seriousDeficieniesVal);
		claimapplication.setSeriousDeficieniesValflag(seriousDeficieniesValflag);
		claimapplication.setMajorDeficienciesObservedVal(majorDeficienciesObservedVal);
		claimapplication.setMajorDeficienciesObservedValflag(majorDeficienciesObservedValflag);
		claimapplication.setDeficienciesObservedVal(deficienciesObservedVal);
		claimapplication.setDeficienciesObservedValflag(deficienciesObservedValflag);
		claimapplication.setInternalRatingVal(internalRatingVal);
		claimapplication.setInternalRatingValflag(internalRatingValflag);
		claimapplication.setAlltheRecoveriesVal(alltheRecoveriesVal);
		claimapplication.setAlltheRecoveriesValflag(alltheRecoveriesValflag);
				
		//ended by rajuk
		
		
		// //System.out.println("Unit Name:"+unitName);

		/*
		 * Added on 11/10/2004 to view the uploaded attachments. Done by
		 * Veldurai
		 */
		boolean internetUser = true;
		User userInfo = getUserInformation(request);
		String userId = userInfo.getUserId();
		claimapplication.setCreatedModifiedy(userId);
		// All users belong to member id 0000 0000 0000 are intranet users
		// except demo user.
		if ((userInfo.getBankId() + userInfo.getZoneId() + userInfo
				.getBranchId()).equals("000000000000")
				&& !userInfo.getUserId().equalsIgnoreCase("DEMOUSER")) {
			internetUser = false;
		}
		// claimapplication.setMemberId(claimForm.getMemberId());
		// claimapplication.setBorrowerId(claimForm.getBorrowerID());

		/* temporarly commented the generation of claim reference number */
		// String clmRefNumber= " ";
		String clmRefNumber = processor.addClaimApplication(claimapplication,
				itpanDetails, internetUser);//bhu
		// //System.out.println("Member Id for Claim Application:"+claimapplication.getMemberId());
		// processor.insertRecallAndLegalAttachments(claimapplication,clmRefNumber,internetUser);
		/*
		 * Addition completed.
		 */
		claimForm.setClmRefNumber(clmRefNumber);
		claimForm.setClmRefNumberNew(null);
		claimapplication.setClaimRefNumber(null);
		// claimapplication.setBorrowerId(null);
		// claimForm.setMemberId(claimapplication.getMemberId());
		HttpSession session = request.getSession();
		session.removeAttribute("RETURNREMARKS");
		claimForm.resetTheFirstClaimApplication(mapping, request);
		claimForm.setNameOfOfficial("");
		claimForm.setDesignationOfOfficial("");
		claimForm.setPlace("");
		claimForm.setView("");//rajuk
		claimForm.setForumthrulegalinitiated("");
		claimForm.setSubsidyFlag("");
		claimForm.setIsSubsidyRcvdAfterNpa("");
		claimForm.setIsSubsidyAdjustedOnDues("");
		claimForm.setSubsidyDate(null);
		Log.log(Log.INFO, "ClaimAction", "saveClaimApplication", "Exited");
		request.setAttribute(
				"message",
				"Application for First Claim Installment for "
						+ borrowerId
						+ "\n has been saved successfully. Claim Reference Number is : "
						+ clmRefNumber);
		// return mapping.findForward("success");
		}
		catch(Exception e)
		{
			throw new DatabaseException(e.getMessage());
		}
		return mapping.findForward("submitClaim");
	}//rajuk

	public ActionForward getMemIdClmRefNum(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// ClaimActionForm claimForm=(ClaimActionForm)form;

		return mapping.findForward("secondClaimInstallmentPage");
	}
	
	/*  Created by kailash for GST*/
	  public ActionForward getGstNoForClaim(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	  throws Exception
	{
			Log.log(4, "ClaimAction", "getGSTNO", "Entered");
		    try{ 
		    	 User user = getUserInformation(request);
		    	   String bankId = user.getBankId();
		    	  // String zoneId = user.getZoneId();
		    	  // String branchId = user.getBranchId();
		    	   //String memberId = bankId.concat(zoneId).concat(branchId);
		    PrintWriter out = response.getWriter();	
			Administrator admin = new Administrator();
			ClaimActionForm dynaForm = (ClaimActionForm) form;
			HttpSession session1 = request.getSession(false);
		    String stateCode=request.getParameter("stateCode");	 
		    System.out.println("stateCode"+stateCode);
		    String gstNo=getGstNoForClaim(stateCode,bankId);
		       
			 out.print(gstNo);	
			}catch(Exception e)
			{
				System.err.println("Exception in ClaimAction..."+e);				
			}   		
	        return null;
	}

	private String getGstNoForClaim(String stateCode,String bankId) {
	    Connection connection=DBConnection.getConnection();

	    
	    Log.log(Log.INFO, "RegistationDAO", "getGstNo", "Entered");
		String gstNo="";
		PreparedStatement pStmt = null;
		ResultSet rsSet = null;
		System.out.println("State code and BankId="+stateCode+" "+bankId);
		
		//String bankId1="0008";
		if((stateCode!=null||!stateCode.equals("")) &&(bankId!=null || !bankId.equals(""))){
		try {
			String query ="select gst.GST_NO from MEMBER_BANK_STATE_GST gst, state_master sm where gst.ste_code = sm.ste_code AND gst.ste_code= ? and MEM_BNK_ID=? ";	
			//String query = "select gst.GST_NO from MEMBER_BANK_STATE_GST gst, state_master sm where gst.ste_code = sm.ste_code AND gst.ste_code=?";
			pStmt = connection.prepareStatement(query);
			pStmt.setString(1, stateCode);
			pStmt.setString(2, bankId);

			rsSet = pStmt.executeQuery();
			while (rsSet.next()) {		

				gstNo=rsSet.getString(1);
			}
			rsSet.close();
			pStmt.close();
		} catch (Exception exception) {
			Log.logException(exception);
			try {
				throw new DatabaseException(exception.getMessage());
			} catch (DatabaseException e) {
				e.printStackTrace();
			}
		} finally {
			DBConnection.freeConnection(connection);
		}
		}
		return gstNo;
	         
							
}
}
