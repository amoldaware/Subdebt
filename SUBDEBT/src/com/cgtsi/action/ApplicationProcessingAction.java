package com.cgtsi.action;

import com.cgtsi.actionform.APForm;
import com.cgtsi.actionform.GMActionForm;
import com.cgtsi.actionform.NPAForm;
import com.cgtsi.admin.Administrator;
import com.cgtsi.admin.Message;
import com.cgtsi.admin.ParameterMaster;
import com.cgtsi.admin.User;
import com.cgtsi.application.Application;
import com.cgtsi.application.ApplicationDAO;
import com.cgtsi.application.ApplicationProcessor;
import com.cgtsi.application.BorrowerDetails;
import com.cgtsi.application.DuplicateApplication;
import com.cgtsi.application.EligibleApplication;
import com.cgtsi.application.LogClass;
import com.cgtsi.application.NoApplicationFoundException;
import com.cgtsi.application.PrimarySecurityDetails;
import com.cgtsi.application.ProjectOutlayDetails;
import com.cgtsi.application.SSIDetails;
import com.cgtsi.application.Securitization;
import com.cgtsi.application.SpecialMessage;
import com.cgtsi.application.TermLoan;
import com.cgtsi.application.WorkingCapital;
import com.cgtsi.claim.ClaimsProcessor;
import com.cgtsi.common.Constants;
import com.cgtsi.common.DatabaseException;
import com.cgtsi.common.Log;
import com.cgtsi.common.Mailer;
import com.cgtsi.common.MessageException;
import com.cgtsi.common.NoDataException;
import com.cgtsi.common.NoUserFoundException;
import com.cgtsi.guaranteemaintenance.GMDAO;
import com.cgtsi.guaranteemaintenance.GMProcessor;
import com.cgtsi.mcgs.MCGFDetails;
import com.cgtsi.mcgs.MCGSProcessor;
import com.cgtsi.receiptspayments.RpDAO;
import com.cgtsi.receiptspayments.RpProcessor;
import com.cgtsi.registration.MLIInfo;
import com.cgtsi.registration.NoMemberFoundException;
import com.cgtsi.registration.Registration;
import com.cgtsi.registration.RegistrationDAO;
import com.cgtsi.reports.ReportManager;
import com.cgtsi.risk.RiskManagementProcessor;
import com.cgtsi.util.ApplicationRenewValidator;
import com.cgtsi.util.BulkUpload;
import com.cgtsi.util.CSRFUtil;
import com.cgtsi.util.DBConnection;
import com.cgtsi.util.MudraBulkUpload;
import com.cgtsi.util.PropertyLoader;
import com.cgtsi.util.TableDetailBean;
import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.DynaValidatorActionForm;

public class ApplicationProcessingAction extends BaseAction {
	Application application;
	Registration registration;
	Administrator admin; 
	ParameterMaster paramMaster;

	private void $init$() {
		this.application = new Application();
		this.registration = new Registration();
		admin = new Administrator();
	}

	public ActionForward checkCGPanAgainstMemberID(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		/*
		 * //System.out.println("checkCGPanAgainstMemberID called first"); MLIInfo
		 * mliInfo = getMemberInfo(request); String bankName =
		 * mliInfo.getBankName(); String bankId = mliInfo.getBankId(); String
		 * branchId = mliInfo.getBranchId(); String zoneId =
		 * mliInfo.getZoneId(); String memberId = bankId + zoneId + branchId;
		 * GMActionForm objGMActionForm= (GMActionForm)form;
		 * 
		 * //System.out.println("checkCGPanAgainstMemberID GMActionForm called"+
		 * objGMActionForm.getCgpan());
		 * //System.out.println("checkCGPanAgainstMemberID GMActionForm called=="
		 * +objGMActionForm.getNpaFormType()); GMDAO objGMDao = new GMDAO();
		 * String
		 * message=objGMDao.checkCGPANForNPAxxxDetail(objGMActionForm.getCgpan
		 * (), memberId,objGMActionForm.getNpaFormType());
		 * //System.out.println("checkCGPanAgainstMemberID called arrayList"
		 * +message); String forward = "success"; PrintWriter out=
		 * response.getWriter(); out.print(message);
		 * 
		 * //return mapping.findForward("success1");
		 */
		//System.out.println("checkCGPanAgainstMemberID called first");
		MLIInfo mliInfo = getMemberInfo(request);
		String bankName = mliInfo.getBankName();
		String bankId = mliInfo.getBankId();
		String branchId = mliInfo.getBranchId();
		String zoneId = mliInfo.getZoneId();
		String memberId = (new StringBuilder(String.valueOf(bankId)))
				.append(zoneId).append(branchId).toString();
		GMActionForm objGMActionForm = (GMActionForm) form;
		//System.out.println((new StringBuilder("checkCGPanAgainstMemberID GMActionForm called")).append(objGMActionForm.getCgpan()).toString());
		//System.out.println((new StringBuilder(checkCGPanAgainstMemberID GMActionForm called==")).append(objGMActionForm.getNpaFormType()).toString());
		GMDAO objGMDao = new GMDAO();
		String message = objGMDao.checkCGPANForNPAxxxDetail(
				objGMActionForm.getCgpan(), memberId,
				objGMActionForm.getNpaFormType());
		//System.out.println((new StringBuilder("checkCGPanAgainstMemberID called arrayList")).append(message).toString());
		String forward = "success";
		PrintWriter out = response.getWriter();
		out.print(message);
		return null;
	}

	public ActionForward checkNewNPADtWithExpiryDtMethod(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		/*
		 * NPAForm objNPAForm=(NPAForm)form;
		 * //System.out.println("checkNewNPADtWithExpiryDtMethod called=="
		 * +objNPAForm.getStrCgpan());
		 * //System.out.println("checkNewNPADtWithExpiryDtMethod called"
		 * +request.getParameter("cgpan")); GMDAO objGMDao = new GMDAO(); String
		 * message
		 * =objGMDao.getNewNPADtWithExpiryDtMethod(request.getParameter("cgpan"
		 * ),request.getParameter("newNPADate"));
		 * //System.out.println("checkCGPanAgainstMemberID called arrayList"
		 * +message); String forward = "success"; PrintWriter out=
		 * response.getWriter(); out.print(message);
		 */
		NPAForm objNPAForm = (NPAForm) form;
		//System.out.println((new StringBuilder("checkNewNPADtWithExpiryDtMethod called==")).append(objNPAForm.getStrCgpan()).toString());
		//System.out.println((new StringBuilder("checkNewNPADtWithExpiryDtMethod called")).append(request.getParameter("cgpan")).toString());
		GMDAO objGMDao = new GMDAO();
		String message = objGMDao.getNewNPADtWithExpiryDtMethod(
				request.getParameter("cgpan"),
				request.getParameter("newNPADate"));
		//System.out.println((new StringBuilder("checkCGPanAgainstMemberID called arrayList")).append(message).toString());
		String forward = "success";
		PrintWriter out = response.getWriter();
		out.print(message);
		return mapping.findForward("success1");
	}

	public ActionForward getTCMliInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(4, "ApplicationProcessingAction", "getTCMliInfo", "Entered");
		DynaValidatorActionForm appForm = (DynaValidatorActionForm) form;
		DynaActionForm dynaForm = (DynaActionForm) form;
		dynaForm.initialize(mapping);

		ApplicationProcessor appProcessor = new ApplicationProcessor();
		Application application = new Application();
		String zoneId = "";
		String branchId = "";

		User user = getUserInformation(request);
		String bankId = user.getBankId();
		 

		String forward = "";

		HttpSession session = request.getSession(false);
		session.setAttribute("page", "MLIInfo");
		session.setAttribute("APPLICATION_LOAN_TYPE", "TC");
		session.setAttribute("APPLICATION_TYPE_FLAG", "7");
		//DKR HYBFLAG
		session.setAttribute("hybridUIflag", "DTRUE");
		session.setAttribute("guafinancialUIflag", "DFALSEUI");
		
		dynaForm.set("loanType", "TC");		
		application.setLoanType("TC");
		dynaForm.set("compositeLoan", "N");
		application.setCompositeLoan("N");
//sayali------------
		
		  Log.log(4, "ApplicationProcessingAction", "getTCMliInfo", "sessionuserid="+session.getAttribute("USER_ID")+""+session.getValueNames());
		    double exposurelmtAmt = appProcessor.getExposuredetails(bankId,request);
			dynaForm.set("exposurelmtAmt", exposurelmtAmt);
			Log.log(5, "ApplicationProcessingAction", "getTCMliInfo", "exposure exposurelmtAmt :"
					+ exposurelmtAmt);
			
			
//-----say
		if (bankId.equals("0000")) {
			user = null;

			forward = "mliPage";
		} else {
			MLIInfo mliInfo = getMemberInfo(request);
			String bankName = mliInfo.getBankName();
			bankId = mliInfo.getBankId();
			branchId = mliInfo.getBranchId();
			zoneId = mliInfo.getZoneId();
			String memberId = bankId + zoneId + branchId;	
			session.setAttribute("memberId", memberId);
			
			
			/*
			 * List<MLIInfo> branchStateList = registration .getGSTStateList(bankId);
			 * dynaForm.set("branchStateList", branchStateList);
			 * request.setAttribute("branchStateList", branchStateList);
			 */

			
			String branchStateCode=registration .getBranchStateCode(memberId);
			  dynaForm.set("branchState", branchStateCode);
			  request.setAttribute("branchState", branchStateCode);
			  
			  String branchGstNo=registration .getBranchGstNo(memberId);
			  dynaForm.set("gstNo", branchGstNo);
			  request.setAttribute("gstNo", branchGstNo);
			
			String statusFlag = mliInfo.getStatus();
			if (statusFlag.equals("I")) {
				throw new NoDataException("Member :" + memberId
						+ "has been deactivated.");
			}

			Log.log(4, "ApplicationProcessingAction", "getTCMliInfo",
					"Entered to get mliinfo object");
			String mcgfsupport = mliInfo.getMcgf();
			Log.log(5, "ApplicationProcessingAction", "getTCMliInfo",
					"mcgfsupport :" + mcgfsupport);
			if (mcgfsupport.equals("Y")) {
				session.setAttribute("MCGF_FLAG", "M");
				MCGSProcessor mcgsProcessor = new MCGSProcessor();
				ArrayList participatingBanks = mcgsProcessor
						.getAllParticipatingBanks(memberId);
				if ((participatingBanks == null)
						|| (participatingBanks.size() == 0)) {
					throw new NoDataException(
							"Participating Banks are not available for this member.Hence Application cannot be submitted.");
				}

				dynaForm.set("participatingBanks", participatingBanks);

				dynaForm.set("mcgfName", bankName);
				dynaForm.set("mcgfId", memberId);
				dynaForm.set("scheme", "MCGS");

				ArrayList ssiRefNosList = appProcessor
						.getSsiRefNosForMcgf(memberId);
				Log.log(4, "ApplicationProcessingAction", "getTCMliInfo",
						"Size :" + ssiRefNosList.size());
				if ((ssiRefNosList == null) || (ssiRefNosList.size() == 0)) {
					Log.log(4, "ApplicationProcessingAction", "getTCMliInfo",
							"No Borrowers");
					throw new NoDataException(
							"There are no borrowers for this Member");
				}

				dynaForm.set("allSsiRefNos", ssiRefNosList);

				forward = "ssiRefNosPage";

				mcgsProcessor = null;
				ssiRefNosList = null;
			} else {
				session.setAttribute("MCGF_FLAG", "NM");

				dynaForm.set("scheme", "CGFSI");

				forward = "tcForward";
			}

			ArrayList statesList = (ArrayList) getStateList();
			dynaForm.set("statesList", statesList);

			ArrayList socialList = getSocialCategory();
			dynaForm.set("socialCategoryList", socialList);

			ArrayList industryNatureList = getIndustryNature();
			dynaForm.set("industryNatureList", industryNatureList);

			statesList = null;

			socialList = null;

			industryNatureList = null;

			mliInfo = null;
			bankId = null;
			zoneId = null;
			branchId = null;
		}

		Log.log(4, "ApplicationProcessingAction", "getTCMliInfo", "Exited");

		return mapping.findForward(forward);
	}
	
	//DIKHSA MUDRA LOGIC 28062020
	public ActionForward getMudraMliInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(4, "ApplicationProcessingAction", "getMudraMliInfo", "Entered");
		DynaValidatorActionForm appForm = (DynaValidatorActionForm) form;
		DynaActionForm dynaForm = (DynaActionForm) form;
		dynaForm.initialize(mapping);

		ApplicationProcessor appProcessor = new ApplicationProcessor();
		Application application = new Application();
		String zoneId = "";
		String branchId = "";

		User user = getUserInformation(request);
		String bankId = user.getBankId();
		 

		String forward = "";

		HttpSession session = request.getSession(false);
		session.setAttribute("page", "MLIInfo");
		session.setAttribute("APPLICATION_LOAN_TYPE", "ML");
		session.setAttribute("APPLICATION_TYPE_FLAG", "7");
	
		session.setAttribute("hybridUIflag", "DTRUE");
		session.setAttribute("guafinancialUIflag", "DFALSEUI");
		
		dynaForm.set("loanType", "ML");		
		application.setLoanType("ML");
		dynaForm.set("compositeLoan", "N");
		application.setCompositeLoan("N");

		
		  Log.log(4, "ApplicationProcessingAction", "getMudraMliInfo", "sessionuserid="+session.getAttribute("USER_ID")+""+session.getValueNames());
		    double exposurelmtAmt = appProcessor.getExposuredetails(bankId,request);
			dynaForm.set("exposurelmtAmt", exposurelmtAmt);
			Log.log(5, "ApplicationProcessingAction", "getMudraMliInfo", "exposure exposurelmtAmt :"
					+ exposurelmtAmt);
			
			

		if (bankId.equals("0000")) {
			user = null;

			forward = "mliPage";
		} else {
			MLIInfo mliInfo = getMemberInfo(request);
			String bankName = mliInfo.getBankName();
			bankId = mliInfo.getBankId();
			branchId = mliInfo.getBranchId();
			zoneId = mliInfo.getZoneId();
			String memberId = bankId + zoneId + branchId;	
			session.setAttribute("memberId", memberId);
			
			
			/*
			 * List<MLIInfo> branchStateList = registration .getGSTStateList(bankId);
			 * dynaForm.set("branchStateList", branchStateList);
			 * request.setAttribute("branchStateList", branchStateList);
			 */

			
			String branchStateCode=registration .getBranchStateCode(memberId);
			  dynaForm.set("branchState", branchStateCode);
			  request.setAttribute("branchState", branchStateCode);
			  
			  String branchGstNo=registration .getBranchGstNo(memberId);
			  dynaForm.set("gstNo", branchGstNo);
			  request.setAttribute("gstNo", branchGstNo);
			
			String statusFlag = mliInfo.getStatus();
			if (statusFlag.equals("I")) {
				throw new NoDataException("Member :" + memberId
						+ "has been deactivated.");
			}

			Log.log(4, "ApplicationProcessingAction", "getMudraMliInfo",
					"Entered to get mliinfo object");
			String mcgfsupport = mliInfo.getMcgf();
			Log.log(5, "ApplicationProcessingAction", "getMudraMliInfo",
					"mcgfsupport :" + mcgfsupport);
			if (mcgfsupport.equals("Y")) {
				session.setAttribute("MCGF_FLAG", "M");
				MCGSProcessor mcgsProcessor = new MCGSProcessor();
				ArrayList participatingBanks = mcgsProcessor
						.getAllParticipatingBanks(memberId);
				if ((participatingBanks == null)
						|| (participatingBanks.size() == 0)) {
					throw new NoDataException(
							"Participating Banks are not available for this member.Hence Application cannot be submitted.");
				}

				dynaForm.set("participatingBanks", participatingBanks);

				dynaForm.set("mcgfName", bankName);
				dynaForm.set("mcgfId", memberId);
				dynaForm.set("scheme", "MCGS");

				ArrayList ssiRefNosList = appProcessor
						.getSsiRefNosForMcgf(memberId);
				Log.log(4, "ApplicationProcessingAction", "getMudraMliInfo",
						"Size :" + ssiRefNosList.size());
				if ((ssiRefNosList == null) || (ssiRefNosList.size() == 0)) {
					Log.log(4, "ApplicationProcessingAction", "getMudraMliInfo",
							"No Borrowers");
					throw new NoDataException(
							"There are no borrowers for this Member");
				}

				dynaForm.set("allSsiRefNos", ssiRefNosList);

				forward = "ssiRefNosPage";

				mcgsProcessor = null;
				ssiRefNosList = null;
			} else {
				session.setAttribute("MCGF_FLAG", "NM");

				dynaForm.set("scheme", "CGFSI");

				forward = "tcMudraForward";
			}

			ArrayList statesList = (ArrayList) getStateList();
			dynaForm.set("statesList", statesList);

			ArrayList socialList = getSocialCategory();
			dynaForm.set("socialCategoryList", socialList);

			ArrayList industryNatureList = getIndustryNature();
			dynaForm.set("industryNatureList", industryNatureList);

			statesList = null;

			socialList = null;

			industryNatureList = null;

			mliInfo = null;
			bankId = null;
			zoneId = null;
			branchId = null;
		}

		Log.log(4, "ApplicationProcessingAction", "getMudraMliInfo", "Exited");

		return mapping.findForward(forward);
	}

	public ActionForward getPCGSMliInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(4, "ApplicationProcessingAction", "getPCGSMliInfo", "Entered");

		DynaActionForm dynaForm = (DynaActionForm) form;
		dynaForm.initialize(mapping);

		ApplicationProcessor appProcessor = new ApplicationProcessor();
		Application application = new Application();

		String forward = "";

		HttpSession session = request.getSession(false);

		session.setAttribute("APPLICATION_LOAN_TYPE", "PCGS");

		session.setAttribute("APPLICATION_TYPE_FLAG", "7");

		dynaForm.set("loanType", "TC");

		application.setLoanType("TC");

		dynaForm.set("compositeLoan", "N");
		application.setCompositeLoan("N");

		String zoneId = "";
		String branchId = "";

		User user = getUserInformation(request);
		String bankId = user.getBankId();

		if (bankId.equals("0000")) {
			user = null;

			forward = "mliPage";
		} else {
			MLIInfo mliInfo = getMemberInfo(request);
			String bankName = mliInfo.getBankName();
			bankId = mliInfo.getBankId();
			branchId = mliInfo.getBranchId();
			zoneId = mliInfo.getZoneId();
			String memberId = bankId + zoneId + branchId;

			String statusFlag = mliInfo.getStatus();
			if (statusFlag.equals("I")) {
				throw new NoDataException("Member :" + memberId
						+ "has been deactivated.");
			}

			Log.log(4, "ApplicationProcessingAction", "getRSFMliInfo",
					"Entered to get mliinfo object");
			String mcgfsupport = mliInfo.getMcgf();
			Log.log(5, "ApplicationProcessingAction", "getRSFMliInfo",
					"mcgfsupport :" + mcgfsupport);
			if (mcgfsupport.equals("Y")) {
				session.setAttribute("MCGF_FLAG", "M");
				MCGSProcessor mcgsProcessor = new MCGSProcessor();
				ArrayList participatingBanks = mcgsProcessor
						.getAllParticipatingBanks(memberId);
				if ((participatingBanks == null)
						|| (participatingBanks.size() == 0)) {
					throw new NoDataException(
							"Participating Banks are not available for this member.Hence Application cannot be submitted.");
				}

				dynaForm.set("participatingBanks", participatingBanks);

				dynaForm.set("mcgfName", bankName);
				dynaForm.set("mcgfId", memberId);
				dynaForm.set("scheme", "MCGS");

				ArrayList ssiRefNosList = appProcessor
						.getSsiRefNosForMcgf(memberId);
				Log.log(4, "ApplicationProcessingAction", "getRSFMliInfo",
						"Size :" + ssiRefNosList.size());
				if ((ssiRefNosList == null) || (ssiRefNosList.size() == 0)) {
					Log.log(4, "ApplicationProcessingAction", "getRSFMliInfo",
							"No Borrowers");
					throw new NoDataException(
							"There are no borrowers for this Member");
				}

				dynaForm.set("allSsiRefNos", ssiRefNosList);

				forward = "ssiRefNosPage";

				mcgsProcessor = null;
				ssiRefNosList = null;
			} else {
				session.setAttribute("MCGF_FLAG", "NM");

				dynaForm.set("scheme", "PCGS");

				forward = "pcgsForward";
			}

			ArrayList statesList = (ArrayList) getStateList();
			dynaForm.set("statesList", statesList);

			ArrayList socialList = getSocialCategory();
			dynaForm.set("socialCategoryList", socialList);

			ArrayList industryNatureList = getIndustryNature();
			dynaForm.set("industryNatureList", industryNatureList);

			ArrayList industrySectorList = getIndustrySector();

			dynaForm.set("industrySectorList", industrySectorList);

			statesList = null;

			socialList = null;

			industryNatureList = null;

			industrySectorList = null;

			mliInfo = null;
			bankId = null;
			zoneId = null;
			branchId = null;
		}

		Log.log(4, "ApplicationProcessingAction", "getRSFMliInfo", "Exited");

		return mapping.findForward(forward);
	}

	public ActionForward submitPCGSApp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String successPage = "";
		DynaActionForm dynaForm = (DynaActionForm) form;
		HttpSession applicationSession = request.getSession(false);

		ApplicationProcessor appProcessor = new ApplicationProcessor();
		Application application = new Application();
		BorrowerDetails borrowerDetails = new BorrowerDetails();
		SSIDetails ssiDetails = new SSIDetails();
		ProjectOutlayDetails projectOutlayDetails = new ProjectOutlayDetails();
		PrimarySecurityDetails primarySecurityDetails = new PrimarySecurityDetails();
		TermLoan termLoan = new TermLoan();
		WorkingCapital workingCapital = new WorkingCapital();
		Securitization securitization = new Securitization();
		MCGFDetails mcgfDetails = new MCGFDetails();
		ClaimsProcessor cpProcessor = new ClaimsProcessor();
		User user = getUserInformation(request);
		String userId = user.getUserId();

		Log.log(5, "ApplicationProcessingAction", "submitPCGSApp", "user Id :"
				+ userId);
		String zoneId = "";
		String branchId = "";
		String mliId = "";

		String bankId = user.getBankId();
		zoneId = user.getZoneId();
		branchId = user.getBranchId();

		String internalRating = (String) dynaForm.get("internalRating");

		application.setInternalRate(internalRating);
		String handiCrafts = (String) dynaForm.get("handiCrafts");
		String dcHandicrafts = (String) dynaForm.get("dcHandicrafts");
		String icardNo = (String) dynaForm.get("icardNo");

		application.setHandiCrafts(handiCrafts);
		application.setDcHandicrafts(dcHandicrafts);
		application.setIcardNo(icardNo);
		String mseCategory = (String) dynaForm.get("mseCategory");

		Date expiryDate = (Date) dynaForm.get("expiryDate");

		if (bankId.equals("0000")) {
			String memberName = (String) dynaForm.get("selectMember");
			if (memberName != null) {
				bankId = memberName.substring(0, 4);
				zoneId = memberName.substring(4, 8);
				branchId = memberName.substring(8, 12);

				application.setBankId(bankId);
				application.setZoneId(zoneId);
				application.setBranchId(branchId);
				mliId = bankId + zoneId + branchId;
				application.setMliID(mliId);
			}
		} else {
			bankId = user.getBankId();
			application.setBankId(bankId);
			zoneId = user.getZoneId();
			application.setZoneId(zoneId);
			branchId = user.getBranchId();
			application.setBranchId(branchId);
			mliId = bankId + zoneId + branchId;
			application.setMliID(mliId);
		}

		dynaForm.set("bankId", bankId);
		dynaForm.set("zoneId", zoneId);
		dynaForm.set("branchId", branchId);
		dynaForm.set("mliID", mliId);

		String applicationType = "";
		String applicationLoanType = "";

		if (applicationSession.getAttribute("APPLICATION_LOAN_TYPE") == null) {
			applicationType = (String) applicationSession
					.getAttribute("APPLICATION_TYPE");
			Log.log(5, "ApplicationProcessingAction", "submitPCGSApp",
					"ApplicationLoan Type :" + applicationType);
		} else {
			applicationLoanType = (String) applicationSession
					.getAttribute("APPLICATION_LOAN_TYPE");
			Log.log(5, "ApplicationProcessingAction", "submitPCGSApp",
					"ApplicationLoan Type :" + applicationLoanType);

			application.setLoanType("TC");
			String type = (String) dynaForm.get("loanType");
			application.setLoanType(type);
		}
		Log.log(4, "ApplicationProcessingAction", "submitPCGSApp",
				"Calling Bean Utils...");
		BeanUtils.populate(ssiDetails, dynaForm.getMap());
		borrowerDetails.setSsiDetails(ssiDetails);
		BeanUtils.populate(borrowerDetails, dynaForm.getMap());

		BeanUtils.populate(primarySecurityDetails, dynaForm.getMap());
		projectOutlayDetails.setPrimarySecurityDetails(primarySecurityDetails);
		BeanUtils.populate(projectOutlayDetails, dynaForm.getMap());

		BeanUtils.populate(termLoan, dynaForm.getMap());
		BeanUtils.populate(securitization, dynaForm.getMap());
		BeanUtils.populate(workingCapital, dynaForm.getMap());
		if (applicationSession.getAttribute("MCGF_FLAG").equals("M")) {
			BeanUtils.populate(mcgfDetails, dynaForm.getMap());
			application.setMCGFDetails(mcgfDetails);
		}

		application.setBorrowerDetails(borrowerDetails);

		double termCreditSanctioned = ((Double) dynaForm
				.get("termCreditSanctioned")).doubleValue();

		double tcSubsidyOrEquity = ((Double) dynaForm.get("tcSubsidyOrEquity"))
				.doubleValue();

		double wcFundBasedSanctioned = ((Double) dynaForm
				.get("wcFundBasedSanctioned")).doubleValue();

		double wcSubsidyOrEquity = ((Double) dynaForm.get("wcSubsidyOrEquity"))
				.doubleValue();

		double projectCost = ((Double) dynaForm.get("projectCost"))
				.doubleValue();
		double minValue = 1000.0D;
		double maxValue = 10000000.0D;
		if (projectCost < minValue) {
			throw new DatabaseException(
					"Credit to be Guaranteed1 Amount should be within the eligible amount available for Guarantee :" + 10000000);
		}
		Double projectOutlayCost = new Double(projectCost);

		double projectOutlay = projectOutlayCost.doubleValue();
		projectOutlayDetails.setProjectOutlay(projectOutlay);
		application.setProjectOutlayDetails(projectOutlayDetails);
		application.setTermLoan(termLoan);
		application.setWc(workingCapital);
		application.setSecuritization(securitization);
		BeanUtils.populate(application, dynaForm.getMap());
		if (dynaForm.get("none").equals("cgpan")) {
			application.setCgpan((String) dynaForm.get("unitValue"));
		} else if (dynaForm.get("none").equals("cgbid")) {
			ArrayList borrowerIds = cpProcessor.getAllBorrowerIDs(mliId);
			if (!borrowerIds.contains(dynaForm.get("unitValue"))) {
				throw new NoDataException(
						"The Borrower ID does not exist for this Member ID");
			}

			application.getBorrowerDetails().getSsiDetails()
					.setCgbid((String) dynaForm.get("unitValue"));
		}

		Log.log(4, "ApplicationProcessingAction", "submitRsfApp",
				"application type :" + applicationType);

		application.setScheme("PCGS");
		application.setCgpanReference("");
		ClaimsProcessor claimProcessor = new ClaimsProcessor();
		String cgbid = "";
		if ((application.getCgpan() != null)
				&& (!application.getCgpan().equals(""))) {
			cgbid = claimProcessor.getBorowwerForCGPAN(application.getCgpan());
		} else if ((application.getBorrowerDetails().getSsiDetails().getCgbid() != null)
				&& (!application.getBorrowerDetails().getSsiDetails()
						.getCgbid().equals(""))) {
			cgbid = application.getBorrowerDetails().getSsiDetails().getCgbid();
		}

		int claimCount = appProcessor.getClaimCount(cgbid);
		if (claimCount > 0) {
			throw new MessageException(
					"Application cannot be filed by this borrower since Claim Application has been submitted");
		}
		application.setLoanType(application.getLoanType());
		application.getBorrowerDetails().getSsiDetails()
				.setEnterprise((String) dynaForm.get("enterprise"));

		application.getBorrowerDetails().getSsiDetails()
				.setUnitAssisted((String) dynaForm.get("unitAssisted"));

		application.getBorrowerDetails().getSsiDetails()
				.setWomenOperated((String) dynaForm.get("womenOperated"));

		application = appProcessor.submitNewApplication(application, userId);
		String appRefNo = application.getAppRefNo();
		dynaForm.set("appRefNo", appRefNo);
		int borrowerRefNo = application.getBorrowerDetails().getSsiDetails()
				.getBorrowerRefNo();
		Integer refNoValue = new Integer(borrowerRefNo);
		dynaForm.set("borrowerRefNo", refNoValue);
		request.setAttribute("message", "Application (Reference No:" + appRefNo
				+ ")Submitted Successfully");

		if (applicationLoanType.equals("BO")) {
			String wcAppRefNo = application.getWcAppRefNo();
			dynaForm.set("wcAppRefNo", wcAppRefNo);
			request.setAttribute("message", "Application (Reference Nos:"
					+ wcAppRefNo + "," + appRefNo + ")Submitted Successfully");
		}

		successPage = "success";

		application = null;
		appProcessor = null;
		ssiDetails = null;
		borrowerDetails = null;
		termLoan = null;
		workingCapital = null;
		primarySecurityDetails = null;
		projectOutlayDetails = null;
		securitization = null;
		mcgfDetails = null;
		user = null;
		userId = null;
		bankId = null;
		zoneId = null;
		branchId = null;

		return mapping.findForward(successPage);
	}

	public ActionForward getRSFMliInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(4, "ApplicationProcessingAction", "getRSFMliInfo", "Entered");

		DynaActionForm dynaForm = (DynaActionForm) form;
		dynaForm.initialize(mapping);

		ApplicationProcessor appProcessor = new ApplicationProcessor();
		Application application = new Application();

		String forward = "";

		HttpSession session = request.getSession(false);

		session.setAttribute("APPLICATION_LOAN_TYPE", "RSF");

		session.setAttribute("APPLICATION_TYPE_FLAG", "7");

		dynaForm.set("loanType", "TC");

		application.setLoanType("TC");

		dynaForm.set("compositeLoan", "N");
		application.setCompositeLoan("N");

		String zoneId = "";
		String branchId = "";

		User user = getUserInformation(request);
		String bankId = user.getBankId();

		if (bankId.equals("0000")) {
			user = null;

			forward = "mliPage";
		} else {
			MLIInfo mliInfo = getMemberInfo(request);
			String bankName = mliInfo.getBankName();

			bankId = mliInfo.getBankId();
			branchId = mliInfo.getBranchId();
			zoneId = mliInfo.getZoneId();
			String memberId = bankId + zoneId + branchId;

			String statusFlag = mliInfo.getStatus();

			if (statusFlag.equals("I")) {
				throw new NoDataException("Member :" + memberId
						+ "has been deactivated.");
			}

			Log.log(4, "ApplicationProcessingAction", "getRSFMliInfo",
					"Entered to get mliinfo object");
			String mcgfsupport = mliInfo.getMcgf();

			Log.log(5, "ApplicationProcessingAction", "getRSFMliInfo",
					"mcgfsupport :" + mcgfsupport);
			if (mcgfsupport.equals("Y")) {
				session.setAttribute("MCGF_FLAG", "M");
				MCGSProcessor mcgsProcessor = new MCGSProcessor();
				ArrayList participatingBanks = mcgsProcessor
						.getAllParticipatingBanks(memberId);
				if ((participatingBanks == null)
						|| (participatingBanks.size() == 0)) {
					throw new NoDataException(
							"Participating Banks are not available for this member.Hence Application cannot be submitted.");
				}

				dynaForm.set("participatingBanks", participatingBanks);

				dynaForm.set("mcgfName", bankName);
				dynaForm.set("mcgfId", memberId);
				dynaForm.set("scheme", "MCGS");

				ArrayList ssiRefNosList = appProcessor
						.getSsiRefNosForMcgf(memberId);
				Log.log(4, "ApplicationProcessingAction", "getRSFMliInfo",
						"Size :" + ssiRefNosList.size());
				if ((ssiRefNosList == null) || (ssiRefNosList.size() == 0)) {
					Log.log(4, "ApplicationProcessingAction", "getRSFMliInfo",
							"No Borrowers");
					throw new NoDataException(
							"There are no borrowers for this Member");
				}

				dynaForm.set("allSsiRefNos", ssiRefNosList);

				forward = "ssiRefNosPage";

				mcgsProcessor = null;
				ssiRefNosList = null;
			} else {
				session.setAttribute("MCGF_FLAG", "NM");

				dynaForm.set("scheme", "RSF");

				forward = "rsfForward";
			}

			ArrayList statesList = (ArrayList) getStateList();
			dynaForm.set("statesList", statesList);

			ArrayList socialList = getSocialCategory();
			dynaForm.set("socialCategoryList", socialList);

			ArrayList industryNatureList = getIndustryNature();
			dynaForm.set("industryNatureList", industryNatureList);

			statesList = null;

			socialList = null;

			industryNatureList = null;

			mliInfo = null;
			bankId = null;
			zoneId = null;
			branchId = null;
		}

		Log.log(4, "ApplicationProcessingAction", "getRSFMliInfo", "Exited");

		return mapping.findForward(forward);
	}

	public ActionForward getRSF2MliInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(4, "ApplicationProcessingAction", "getRSF2MliInfo", "Entered");

		DynaActionForm dynaForm = (DynaActionForm) form;
		dynaForm.initialize(mapping);

		ApplicationProcessor appProcessor = new ApplicationProcessor();
		Application application = new Application();

		String forward = "";

		HttpSession session = request.getSession(false);

		session.setAttribute("APPLICATION_LOAN_TYPE", "RSF");

		session.setAttribute("APPLICATION_TYPE_FLAG", "7");

		dynaForm.set("loanType", "TC");

		application.setLoanType("TC");

		dynaForm.set("compositeLoan", "N");
		application.setCompositeLoan("N");

		String zoneId = "";
		String branchId = "";

		User user = getUserInformation(request);
		String bankId = user.getBankId();

		if (bankId.equals("0000")) {
			user = null;

			forward = "mliPage";
		} else {
			MLIInfo mliInfo = getMemberInfo(request);
			String bankName = mliInfo.getBankName();

			bankId = mliInfo.getBankId();
			branchId = mliInfo.getBranchId();
			zoneId = mliInfo.getZoneId();
			String memberId = bankId + zoneId + branchId;

			String statusFlag = mliInfo.getStatus();

			if (statusFlag.equals("I")) {
				throw new NoDataException("Member :" + memberId
						+ "has been deactivated.");
			}

			Log.log(4, "ApplicationProcessingAction", "getRSF2MliInfo",
					"Entered to get mliinfo object");
			String mcgfsupport = mliInfo.getMcgf();

			Log.log(5, "ApplicationProcessingAction", "getRSF2MliInfo",
					"mcgfsupport :" + mcgfsupport);
			if (mcgfsupport.equals("Y")) {
				session.setAttribute("MCGF_FLAG", "M");
				MCGSProcessor mcgsProcessor = new MCGSProcessor();
				ArrayList participatingBanks = mcgsProcessor
						.getAllParticipatingBanks(memberId);
				if ((participatingBanks == null)
						|| (participatingBanks.size() == 0)) {
					throw new NoDataException(
							"Participating Banks are not available for this member.Hence Application cannot be submitted.");
				}

				dynaForm.set("participatingBanks", participatingBanks);

				dynaForm.set("mcgfName", bankName);
				dynaForm.set("mcgfId", memberId);
				dynaForm.set("scheme", "MCGS");

				ArrayList ssiRefNosList = appProcessor
						.getSsiRefNosForMcgf(memberId);
				Log.log(4, "ApplicationProcessingAction", "getRSF2MliInfo",
						"Size :" + ssiRefNosList.size());
				if ((ssiRefNosList == null) || (ssiRefNosList.size() == 0)) {
					Log.log(4, "ApplicationProcessingAction", "getRSF2MliInfo",
							"No Borrowers");
					throw new NoDataException(
							"There are no borrowers for this Member");
				}

				dynaForm.set("allSsiRefNos", ssiRefNosList);

				forward = "ssiRefNosPage";

				mcgsProcessor = null;
				ssiRefNosList = null;
			} else {
				session.setAttribute("MCGF_FLAG", "NM");

				dynaForm.set("scheme", "RSF2");

				forward = "rsfForward";
			}

			ArrayList statesList = (ArrayList) getStateList();
			dynaForm.set("statesList", statesList);

			ArrayList socialList = getSocialCategory();
			dynaForm.set("socialCategoryList", socialList);

			ArrayList industryNatureList = getIndustryNature();
			dynaForm.set("industryNatureList", industryNatureList);

			statesList = null;

			socialList = null;

			industryNatureList = null;

			mliInfo = null;
			bankId = null;
			zoneId = null;
			branchId = null;
		}

		Log.log(4, "ApplicationProcessingAction", "getRSF2MliInfo", "Exited");

		return mapping.findForward(forward);
	}

	public ActionForward getRSF2WcMliInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ApplicationProcessingAction", "getRSFWcMliInfo", "Entered");

		DynaActionForm dynaForm = (DynaActionForm) form;
		dynaForm.initialize(mapping);

		ApplicationProcessor appProcessor = new ApplicationProcessor();
		Application application = new Application();

		String forward = "";

		HttpSession session = request.getSession(false);

		session.setAttribute("APPLICATION_LOAN_TYPE", "RSF");

		session.setAttribute("APPLICATION_TYPE_FLAG", "9");

		dynaForm.set("loanType", "WC");

		application.setLoanType("WC");

		dynaForm.set("compositeLoan", "N");
		application.setCompositeLoan("N");

		String zoneId = "";
		String branchId = "";

		User user = getUserInformation(request);
		String bankId = user.getBankId();

		if (bankId.equals("0000")) {
			user = null;

			forward = "mliPage";
		} else {
			MLIInfo mliInfo = getMemberInfo(request);
			String bankName = mliInfo.getBankName();

			bankId = mliInfo.getBankId();
			branchId = mliInfo.getBranchId();
			zoneId = mliInfo.getZoneId();
			String memberId = bankId + zoneId + branchId;

			String statusFlag = mliInfo.getStatus();

			if (statusFlag.equals("I")) {
				throw new NoDataException("Member :" + memberId
						+ "has been deactivated.");
			}

			Log.log(4, "ApplicationProcessingAction", "getRSFWcMliInfo",
					"Entered to get mliinfo object");
			String mcgfsupport = mliInfo.getMcgf();

			Log.log(5, "ApplicationProcessingAction", "getRSFWcMliInfo",
					"mcgfsupport :" + mcgfsupport);
			if (mcgfsupport.equals("Y")) {
				session.setAttribute("MCGF_FLAG", "M");
				MCGSProcessor mcgsProcessor = new MCGSProcessor();
				ArrayList participatingBanks = mcgsProcessor
						.getAllParticipatingBanks(memberId);
				if ((participatingBanks == null)
						|| (participatingBanks.size() == 0)) {
					throw new NoDataException(
							"Participating Banks are not available for this member.Hence Application cannot be submitted.");
				}

				dynaForm.set("participatingBanks", participatingBanks);

				dynaForm.set("mcgfName", bankName);
				dynaForm.set("mcgfId", memberId);
				dynaForm.set("scheme", "MCGS");

				ArrayList ssiRefNosList = appProcessor
						.getSsiRefNosForMcgf(memberId);
				Log.log(4, "ApplicationProcessingAction", "getRSFWcMliInfo",
						"Size :" + ssiRefNosList.size());
				if ((ssiRefNosList == null) || (ssiRefNosList.size() == 0)) {
					Log.log(4, "ApplicationProcessingAction",
							"getRSFWcMliInfo", "No Borrowers");
					throw new NoDataException(
							"There are no borrowers for this Member");
				}

				dynaForm.set("allSsiRefNos", ssiRefNosList);

				forward = "ssiRefNosPage";

				mcgsProcessor = null;
				ssiRefNosList = null;
			} else {
				session.setAttribute("MCGF_FLAG", "NM");

				dynaForm.set("scheme", "RSF2");

				forward = "rsfForward";
			}

			ArrayList statesList = (ArrayList) getStateList();
			dynaForm.set("statesList", statesList);

			ArrayList socialList = getSocialCategory();
			dynaForm.set("socialCategoryList", socialList);

			ArrayList industryNatureList = getIndustryNature();
			dynaForm.set("industryNatureList", industryNatureList);

			statesList = null;

			socialList = null;

			industryNatureList = null;

			mliInfo = null;
			bankId = null;
			zoneId = null;
			branchId = null;
		}

		Log.log(4, "ApplicationProcessingAction", "getRSFWcMliInfo", "Exited");

		return mapping.findForward(forward);
	}

	public ActionForward getRSFWcMliInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ApplicationProcessingAction", "getRSFWcMliInfo", "Entered");

		DynaActionForm dynaForm = (DynaActionForm) form;
		dynaForm.initialize(mapping);

		ApplicationProcessor appProcessor = new ApplicationProcessor();
		Application application = new Application();

		String forward = "";

		HttpSession session = request.getSession(false);

		session.setAttribute("APPLICATION_LOAN_TYPE", "RSF");

		session.setAttribute("APPLICATION_TYPE_FLAG", "9");

		dynaForm.set("loanType", "WC");

		application.setLoanType("WC");

		dynaForm.set("compositeLoan", "N");
		application.setCompositeLoan("N");

		String zoneId = "";
		String branchId = "";

		User user = getUserInformation(request);
		String bankId = user.getBankId();

		if (bankId.equals("0000")) {
			user = null;

			forward = "mliPage";
		} else {
			MLIInfo mliInfo = getMemberInfo(request);
			String bankName = mliInfo.getBankName();

			bankId = mliInfo.getBankId();
			branchId = mliInfo.getBranchId();
			zoneId = mliInfo.getZoneId();
			String memberId = bankId + zoneId + branchId;

			String statusFlag = mliInfo.getStatus();

			if (statusFlag.equals("I")) {
				throw new NoDataException("Member :" + memberId
						+ "has been deactivated.");
			}

			Log.log(4, "ApplicationProcessingAction", "getRSFWcMliInfo",
					"Entered to get mliinfo object");
			String mcgfsupport = mliInfo.getMcgf();

			Log.log(5, "ApplicationProcessingAction", "getRSFWcMliInfo",
					"mcgfsupport :" + mcgfsupport);
			if (mcgfsupport.equals("Y")) {
				session.setAttribute("MCGF_FLAG", "M");
				MCGSProcessor mcgsProcessor = new MCGSProcessor();
				ArrayList participatingBanks = mcgsProcessor
						.getAllParticipatingBanks(memberId);
				if ((participatingBanks == null)
						|| (participatingBanks.size() == 0)) {
					throw new NoDataException(
							"Participating Banks are not available for this member.Hence Application cannot be submitted.");
				}

				dynaForm.set("participatingBanks", participatingBanks);

				dynaForm.set("mcgfName", bankName);
				dynaForm.set("mcgfId", memberId);
				dynaForm.set("scheme", "MCGS");

				ArrayList ssiRefNosList = appProcessor
						.getSsiRefNosForMcgf(memberId);
				Log.log(4, "ApplicationProcessingAction", "getRSFWcMliInfo",
						"Size :" + ssiRefNosList.size());
				if ((ssiRefNosList == null) || (ssiRefNosList.size() == 0)) {
					Log.log(4, "ApplicationProcessingAction",
							"getRSFWcMliInfo", "No Borrowers");
					throw new NoDataException(
							"There are no borrowers for this Member");
				}

				dynaForm.set("allSsiRefNos", ssiRefNosList);

				forward = "ssiRefNosPage";

				mcgsProcessor = null;
				ssiRefNosList = null;
			} else {
				session.setAttribute("MCGF_FLAG", "NM");

				dynaForm.set("scheme", "RSF");

				forward = "rsfForward";
			}

			ArrayList statesList = (ArrayList) getStateList();
			dynaForm.set("statesList", statesList);

			ArrayList socialList = getSocialCategory();
			dynaForm.set("socialCategoryList", socialList);

			ArrayList industryNatureList = getIndustryNature();
			dynaForm.set("industryNatureList", industryNatureList);

			statesList = null;

			socialList = null;

			industryNatureList = null;

			mliInfo = null;
			bankId = null;
			zoneId = null;
			branchId = null;
		}

		Log.log(4, "ApplicationProcessingAction", "getRSFWcMliInfo", "Exited");

		return mapping.findForward(forward);
	}

	public ActionForward getRsfBothMliInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ApplicationProcessingAction", "getRsfBothMliInfo",
				"Entered");

		DynaActionForm dynaForm = (DynaActionForm) form;
		dynaForm.initialize(mapping);

		ApplicationProcessor appProcessor = new ApplicationProcessor();

		Application application = new Application();

		HttpSession session = request.getSession(false);
		session.setAttribute("APPLICATION_LOAN_TYPE", "RSF");

		session.setAttribute("APPLICATION_TYPE_FLAG", "10");
		application.setLoanType("BO");
		dynaForm.set("loanType", "BO");

		dynaForm.set("compositeLoan", "N");
		application.setCompositeLoan("N");

		String forward = "";
		String zoneId = "";
		String branchId = "";

		User user = getUserInformation(request);
		String bankId = user.getBankId();
		if (bankId.equals("0000")) {
			forward = "mliPage";
		} else {
			MLIInfo mliInfo = getMemberInfo(request);
			String bankName = mliInfo.getBankName();
			bankId = mliInfo.getBankId();
			branchId = mliInfo.getBranchId();
			zoneId = mliInfo.getZoneId();
			String memberId = bankId + zoneId + branchId;

			String statusFlag = mliInfo.getStatus();
			if (statusFlag.equals("I")) {
				throw new NoDataException("Member :" + memberId
						+ "has been deactivated.");
			}

			String mcgfsupport = mliInfo.getMcgf();
			if (mcgfsupport.equals("Y")) {
				dynaForm.set("scheme", "MCGS");
				MCGSProcessor mcgsProcessor = new MCGSProcessor();
				session.setAttribute("MCGF_FLAG", "M");
				ArrayList participatingBanks = mcgsProcessor
						.getAllParticipatingBanks(memberId);
				if ((participatingBanks == null)
						|| (participatingBanks.size() == 0)) {
					throw new NoDataException(
							"Participating Banks are not available for this member.Hence Application cannot be submitted.");
				}

				dynaForm.set("participatingBanks", participatingBanks);

				dynaForm.set("participatingBanks", participatingBanks);
				dynaForm.set("mcgfName", bankName);
				dynaForm.set("mcgfId", memberId);

				ArrayList ssiRefNosList = appProcessor
						.getSsiRefNosForMcgf(memberId);
				if ((ssiRefNosList == null) || (ssiRefNosList.size() == 0)) {
					Log.log(4, "ApplicationProcessingAction",
							"getRsfBothMliInfo", "No Borrowers");
					throw new NoDataException(
							"There are no borrowers for this Member");
				}

				dynaForm.set("allSsiRefNos", ssiRefNosList);

				forward = "ssiRefNosPage";

				ssiRefNosList = null;

				mcgsProcessor = null;
			} else {
				session.setAttribute("MCGF_FLAG", "NM");

				dynaForm.set("scheme", "RSF");

				forward = "rsfForward";
			}

			ArrayList statesList = (ArrayList) getStateList();
			dynaForm.set("statesList", statesList);

			ArrayList socialList = getSocialCategory();
			dynaForm.set("socialCategoryList", socialList);

			ArrayList industryNatureList = getIndustryNature();
			dynaForm.set("industryNatureList", industryNatureList);

			statesList = null;

			socialList = null;

			industryNatureList = null;

			mliInfo = null;
			bankId = null;
			zoneId = null;
			branchId = null;
		}

		Log.log(4, "ApplicationProcessingAction", "getRsfBothMliInfo", "Exited");

		return mapping.findForward(forward);
	}

	public ActionForward getRsf2BothMliInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ApplicationProcessingAction", "getRsfBothMliInfo",
				"Entered");

		DynaActionForm dynaForm = (DynaActionForm) form;
		dynaForm.initialize(mapping);

		ApplicationProcessor appProcessor = new ApplicationProcessor();

		Application application = new Application();

		HttpSession session = request.getSession(false);
		session.setAttribute("APPLICATION_LOAN_TYPE", "RSF");

		session.setAttribute("APPLICATION_TYPE_FLAG", "10");
		application.setLoanType("BO");
		dynaForm.set("loanType", "BO");

		dynaForm.set("compositeLoan", "N");
		application.setCompositeLoan("N");

		String forward = "";
		String zoneId = "";
		String branchId = "";

		User user = getUserInformation(request);
		String bankId = user.getBankId();
		if (bankId.equals("0000")) {
			forward = "mliPage";
		} else {
			MLIInfo mliInfo = getMemberInfo(request);
			String bankName = mliInfo.getBankName();
			bankId = mliInfo.getBankId();
			branchId = mliInfo.getBranchId();
			zoneId = mliInfo.getZoneId();
			String memberId = bankId + zoneId + branchId;

			String statusFlag = mliInfo.getStatus();
			if (statusFlag.equals("I")) {
				throw new NoDataException("Member :" + memberId
						+ "has been deactivated.");
			}

			String mcgfsupport = mliInfo.getMcgf();
			if (mcgfsupport.equals("Y")) {
				dynaForm.set("scheme", "MCGS");
				MCGSProcessor mcgsProcessor = new MCGSProcessor();
				session.setAttribute("MCGF_FLAG", "M");
				ArrayList participatingBanks = mcgsProcessor
						.getAllParticipatingBanks(memberId);
				if ((participatingBanks == null)
						|| (participatingBanks.size() == 0)) {
					throw new NoDataException(
							"Participating Banks are not available for this member.Hence Application cannot be submitted.");
				}

				dynaForm.set("participatingBanks", participatingBanks);

				dynaForm.set("participatingBanks", participatingBanks);
				dynaForm.set("mcgfName", bankName);
				dynaForm.set("mcgfId", memberId);

				ArrayList ssiRefNosList = appProcessor
						.getSsiRefNosForMcgf(memberId);
				if ((ssiRefNosList == null) || (ssiRefNosList.size() == 0)) {
					Log.log(4, "ApplicationProcessingAction",
							"getRsfBothMliInfo", "No Borrowers");
					throw new NoDataException(
							"There are no borrowers for this Member");
				}

				dynaForm.set("allSsiRefNos", ssiRefNosList);

				forward = "ssiRefNosPage";

				ssiRefNosList = null;

				mcgsProcessor = null;
			} else {
				session.setAttribute("MCGF_FLAG", "NM");

				dynaForm.set("scheme", "RSF");

				forward = "rsfForward";
			}

			ArrayList statesList = (ArrayList) getStateList();
			dynaForm.set("statesList", statesList);

			ArrayList socialList = getSocialCategory();
			dynaForm.set("socialCategoryList", socialList);

			ArrayList industryNatureList = getIndustryNature();
			dynaForm.set("industryNatureList", industryNatureList);

			statesList = null;

			socialList = null;

			industryNatureList = null;

			mliInfo = null;
			bankId = null;
			zoneId = null;
			branchId = null;
		}

		Log.log(4, "ApplicationProcessingAction", "getRsfBothMliInfo", "Exited");

		return mapping.findForward(forward);
	}

	public ActionForward rejectApplicationInput(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ApplicationProcessingAction", "rejectApplicationInput",
				"Entered");

		APForm dynaForm = (APForm) form;

		Log.log(4, "ApplicationProcessingAction", "rejectApplicationInput",
				"Exited");

		return mapping.findForward("success");
	}

	public ActionForward rejectApplication(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ApplicationProcessingAction", "rejectApplication",
				"Entered");

		APForm actionForm = (APForm) form;
		ApplicationProcessor appProcessor = new ApplicationProcessor();
		Application application = new Application();
		ReportManager reportManager = new ReportManager();
		ArrayList cgpans = new ArrayList();
		User user = getUserInformation(request);
		String userId = user.getUserId();

		String cgpan = null;

		cgpan = actionForm.getCgpan();
		String remarks = actionForm.getRemarks();

		cgpans = reportManager.getAllCgpans();
		String newCgpan = cgpan.toUpperCase();

		if ((newCgpan != null) && (cgpans.contains(newCgpan))) {
			appProcessor.rejectApplication(newCgpan, remarks, userId);
			request.setAttribute("message", "CGPAN : " + newCgpan
					+ " Cancelled Successfully:");
		} else if ((newCgpan.equals("")) || (newCgpan == null)
				|| (!cgpans.contains(newCgpan))) {
			throw new NoDataException("Enter a Valid CGPAN");
		}

		actionForm.setCgpan(null);
		actionForm.setRemarks(null);
		Log.log(4, "ApplicationProcessingAction",
				"ApplicationProcessingAction", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward getCCMliInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(4, "ApplicationProcessingAction", "getCCMliInfo", "Entered");

		DynaActionForm dynaForm = (DynaActionForm) form;
		dynaForm.initialize(mapping);

		ApplicationProcessor appProcessor = new ApplicationProcessor();

		Application application = new Application();

		HttpSession session = request.getSession(false);
		session.removeAttribute("APPLICATION_LOAN_TYPE");
		session.setAttribute("APPLICATION_LOAN_TYPE", "CC");

		session.setAttribute("APPLICATION_TYPE_FLAG", "8");

		application.setLoanType("CC");
		dynaForm.set("loanType", "CC");

		dynaForm.set("compositeLoan", "Y");
		application.setCompositeLoan("Y");

		String forward = "";
		String zoneId = "";
		String branchId = "";

		User user = getUserInformation(request);
		String bankId = user.getBankId();
		
		// add FB CHECK validate
		//show checkbox------------
		//sayali------------
		
		  Log.log(4, "ApplicationProcessingAction", "getCCMliInfo", "sessionuserid="+session.getAttribute("USER_ID")+""+session.getValueNames()); 
	    double exposurelmtAmt = appProcessor.getExposuredetails(bankId,request);
		dynaForm.set("exposurelmtAmt", exposurelmtAmt);
		Log.log(5, "ApplicationProcessingAction", "getCCMliInfo", "exposure exposurelmtAmt :"
				+ exposurelmtAmt);
      //-----say
		if (bankId.equals("0000")) {
			user = null;

			forward = "mliPage";
		} else {
			MLIInfo mliInfo = getMemberInfo(request);
			String bankName = mliInfo.getBankName();
			bankId = mliInfo.getBankId();
			branchId = mliInfo.getBranchId();
			zoneId = mliInfo.getZoneId();
			String memberId = bankId + zoneId + branchId;

			String statusFlag = mliInfo.getStatus();
			if (statusFlag.equals("I")) {
				throw new NoDataException("Member :" + memberId
						+ "has been deactivated.");
			}

			String mcgfsupport = mliInfo.getMcgf();
			if (mcgfsupport.equals("Y")) {
				dynaForm.set("scheme", "MCGS");
				MCGSProcessor mcgsProcessor = new MCGSProcessor();
				session.setAttribute("MCGF_FLAG", "M");
				ArrayList participatingBanks = mcgsProcessor
						.getAllParticipatingBanks(memberId);
				if ((participatingBanks == null)
						|| (participatingBanks.size() == 0)) {
					throw new NoDataException(
							"Participating Banks are not available for this member.Hence Application cannot be submitted.");
				}

				dynaForm.set("participatingBanks", participatingBanks);

				dynaForm.set("participatingBanks", participatingBanks);
				dynaForm.set("mcgfName", bankName);
				dynaForm.set("mcgfId", memberId);

				ArrayList ssiRefNosList = appProcessor
						.getSsiRefNosForMcgf(memberId);
				if ((ssiRefNosList == null) || (ssiRefNosList.size() == 0)) {
					Log.log(4, "ApplicationProcessingAction", "getCCMliInfo",
							"No Borrowers");
					throw new NoDataException(
							"There are no borrowers for this Member");
				}

				dynaForm.set("allSsiRefNos", ssiRefNosList);

				forward = "ssiRefNosPage";

				ssiRefNosList = null;

				mcgsProcessor = null;
			} else {
				session.setAttribute("MCGF_FLAG", "NM");

				dynaForm.set("scheme", "CGFSI");

				forward = "ccForward";
			}

			ArrayList statesList = (ArrayList) getStateList();
			dynaForm.set("statesList", statesList);

			ArrayList socialList = getSocialCategory();
			dynaForm.set("socialCategoryList", socialList);

			ArrayList industryNatureList = getIndustryNature();
			dynaForm.set("industryNatureList", industryNatureList);

			statesList = null;

			socialList = null;

			industryNatureList = null;

			mliInfo = null;
			bankId = null;
			zoneId = null;
			branchId = null;
		}

		Log.log(4, "ApplicationProcessingAction", "getCCMliInfo", "Exited");

		return mapping.findForward(forward);
	}

	public ActionForward getWCMliInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(4, "ApplicationProcessingAction", "getWCMliInfo", "Entered");

		DynaActionForm dynaForm = (DynaActionForm) form;
		dynaForm.initialize(mapping);

		ApplicationProcessor appProcessor = new ApplicationProcessor();

		Application application = new Application();

		HttpSession session = request.getSession(false);
		session.setAttribute("APPLICATION_LOAN_TYPE", "WC");

		//DKR HYBFLAG
		session.setAttribute("hybridUIflag", "DTRUE");
		session.setAttribute("gFinancialUIflag", "DFALSEUI");		
		//session.setAttribute("gExgGreenUIFlag", "RFALSEUI");
		session.setAttribute("dblockUI","");	
		
		session.setAttribute("APPLICATION_TYPE_FLAG", "9");
		application.setLoanType("WC");
		dynaForm.set("loanType", "WC");

		dynaForm.set("compositeLoan", "N");
		application.setCompositeLoan("N");

		String forward = "";
		String zoneId = "";
		String branchId = "";

		User user = getUserInformation(request);
		String bankId = user.getBankId();
		// add FB CHECK validate		
		Log.log(4, "ApplicationProcessingAction", "getWCMliInfo", "sessionuserid="+session.getAttribute("USER_ID")+""+session.getValueNames());
	    double exposurelmtAmt = appProcessor.getExposuredetails(bankId,request);
		dynaForm.set("exposurelmtAmt", exposurelmtAmt);
		Log.log(5, "ApplicationProcessingAction", "getWCMliInfo", "exposure exposurelmtAmt :"
				+ exposurelmtAmt);
      //-----say
		if (bankId.equals("0000")) {
			forward = "mliPage";
		} else {
			MLIInfo mliInfo = getMemberInfo(request);
			String bankName = mliInfo.getBankName();
			bankId = mliInfo.getBankId();
			branchId = mliInfo.getBranchId();
			zoneId = mliInfo.getZoneId();
			String memberId = bankId + zoneId + branchId;

			// Changes for gst by DKR
			List<MLIInfo> branchStateList = registration
					.getGSTStateList(bankId);
			dynaForm.set("branchStateList", branchStateList);
			request.setAttribute("branchStateList", branchStateList);

			String statusFlag = mliInfo.getStatus();
			if (statusFlag.equals("I")) {
				throw new NoDataException("Member :" + memberId
						+ "has been deactivated.");
			}

			String mcgfsupport = mliInfo.getMcgf();
			if (mcgfsupport.equals("Y")) {
				dynaForm.set("scheme", "MCGS");
				MCGSProcessor mcgsProcessor = new MCGSProcessor();
				session.setAttribute("MCGF_FLAG", "M");
				ArrayList participatingBanks = mcgsProcessor
						.getAllParticipatingBanks(memberId);
				if ((participatingBanks == null)
						|| (participatingBanks.size() == 0)) {
					throw new NoDataException(
							"Participating Banks are not available for this member.Hence Application cannot be submitted.");
				}

				dynaForm.set("participatingBanks", participatingBanks);

				dynaForm.set("participatingBanks", participatingBanks);
				dynaForm.set("mcgfName", bankName);
				dynaForm.set("mcgfId", memberId);

				ArrayList ssiRefNosList = appProcessor
						.getSsiRefNosForMcgf(memberId);
				if ((ssiRefNosList == null) || (ssiRefNosList.size() == 0)) {
					Log.log(4, "ApplicationProcessingAction", "getWCMliInfo",
							"No Borrowers");
					throw new NoDataException(
							"There are no borrowers for this Member");
				}

				dynaForm.set("allSsiRefNos", ssiRefNosList);

				forward = "ssiRefNosPage";

				ssiRefNosList = null;

				mcgsProcessor = null;
			} else {
				session.setAttribute("MCGF_FLAG", "NM");

				dynaForm.set("scheme", "CGFSI");

				forward = "wcForward";
			}

			ArrayList statesList = (ArrayList) getStateList();
			dynaForm.set("statesList", statesList);

			ArrayList socialList = getSocialCategory();
			dynaForm.set("socialCategoryList", socialList);

			ArrayList industryNatureList = getIndustryNature();
			dynaForm.set("industryNatureList", industryNatureList);

			statesList = null;

			socialList = null;

			industryNatureList = null;

			mliInfo = null;
			bankId = null;
			zoneId = null;
			branchId = null;
		}

		Log.log(4, "ApplicationProcessingAction", "getWCMliInfo", "Exited");

		return mapping.findForward(forward);
	}

	public ActionForward getBothMliInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(4, "ApplicationProcessingAction", "getBothMliInfo", "Entered");

		DynaActionForm dynaForm = (DynaActionForm) form;
		dynaForm.initialize(mapping);

		ApplicationProcessor appProcessor = new ApplicationProcessor();

		Application application = new Application();

		HttpSession session = request.getSession(false);
		//DKR HYBFLAG
	    session.setAttribute("hybridUIflag", "DTRUE");
		
		session.setAttribute("APPLICATION_LOAN_TYPE", "BO");

		session.setAttribute("APPLICATION_TYPE_FLAG", "10");
		application.setLoanType("BO");
		dynaForm.set("loanType", "BO");
	
		
		session.setAttribute("gFinancialUIflag","DFALSEUI");
		session.setAttribute("dblockUI","");
		session.removeAttribute("gFinancialUIflag");
		session.removeAttribute("dblockUI");	
		
		dynaForm.set("compositeLoan", "N");
		application.setCompositeLoan("N");

		String forward = "";
		String zoneId = "";
		String branchId = "";

		User user = getUserInformation(request);
		String bankId = user.getBankId();
		//sayali------------
		
		Log.log(4, "ApplicationProcessingAction", "getBothMliInfo", "sessionuserid="+session.getAttribute("USER_ID")+""+session.getValueNames()); 
	    double exposurelmtAmt = appProcessor.getExposuredetails(bankId,request);
		dynaForm.set("exposurelmtAmt", exposurelmtAmt);
		Log.log(5, "ApplicationProcessingAction", "getBothMliInfo", "exposure exposurelmtAmt :"
				+ exposurelmtAmt);
//-----say
		if (bankId.equals("0000")) {
			forward = "mliPage";
		} else {
			MLIInfo mliInfo = getMemberInfo(request);
			String bankName = mliInfo.getBankName();
			bankId = mliInfo.getBankId();
			branchId = mliInfo.getBranchId();
			zoneId = mliInfo.getZoneId();
			String memberId = bankId + zoneId + branchId;

			// Changes for gst by DKR
			List<MLIInfo> branchStateList = registration
					.getGSTStateList(bankId);
			dynaForm.set("branchStateList", branchStateList);
			request.setAttribute("branchStateList", branchStateList);

			String statusFlag = mliInfo.getStatus();
			if (statusFlag.equals("I")) {
				throw new NoDataException("Member :" + memberId
						+ "has been deactivated.");
			}

			String mcgfsupport = mliInfo.getMcgf();
			if (mcgfsupport.equals("Y")) {
				dynaForm.set("scheme", "MCGS");
				MCGSProcessor mcgsProcessor = new MCGSProcessor();
				session.setAttribute("MCGF_FLAG", "M");
				ArrayList participatingBanks = mcgsProcessor
						.getAllParticipatingBanks(memberId);
				if ((participatingBanks == null)
						|| (participatingBanks.size() == 0)) {
					throw new NoDataException(
							"Participating Banks are not available for this member.Hence Application cannot be submitted.");
				}

				dynaForm.set("participatingBanks", participatingBanks);

				dynaForm.set("participatingBanks", participatingBanks);
				dynaForm.set("mcgfName", bankName);
				dynaForm.set("mcgfId", memberId);

				ArrayList ssiRefNosList = appProcessor
						.getSsiRefNosForMcgf(memberId);
				if ((ssiRefNosList == null) || (ssiRefNosList.size() == 0)) {
					Log.log(4, "ApplicationProcessingAction", "getBothMliInfo",
							"No Borrowers");
					throw new NoDataException(
							"There are no borrowers for this Member");
				}

				dynaForm.set("allSsiRefNos", ssiRefNosList);

				forward = "ssiRefNosPage";

				ssiRefNosList = null;

				mcgsProcessor = null;
			} else {
				session.setAttribute("MCGF_FLAG", "NM");

				dynaForm.set("scheme", "CGFSI");

				forward = "bothForward";
			}

			ArrayList statesList = (ArrayList) getStateList();
			dynaForm.set("statesList", statesList);

			ArrayList socialList = getSocialCategory();
			dynaForm.set("socialCategoryList", socialList);

			ArrayList industryNatureList = getIndustryNature();
			dynaForm.set("industryNatureList", industryNatureList);

			statesList = null;

			socialList = null;

			industryNatureList = null;

			mliInfo = null;
			bankId = null;
			zoneId = null;
			branchId = null;
		}

		Log.log(4, "ApplicationProcessingAction", "getBothMliInfo", "Exited");

		return mapping.findForward(forward);
	}

	public ActionForward getAddtlTCInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(4, "ApplicationProcessingAction", "getAddtlTCInfo", "Entered");
		DynaActionForm dynaForm = (DynaActionForm) form;
		dynaForm.initialize(mapping);
		ApplicationProcessor appProcessor = new ApplicationProcessor();
		HttpSession session = request.getSession(false);
		session.removeAttribute("APPLICATION_LOAN_TYPE");

		session.setAttribute("APPLICATION_TYPE", "TCE");

		User user = getUserInformation(request);
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String memberId = bankId + zoneId + branchId;

		//sayali------------
		
		Log.log(4, "ApplicationProcessingAction", "getAddtlTCInfo", "sessionuserid="+session.getAttribute("USER_ID")+""+session.getValueNames());
	    double exposurelmtAmt = appProcessor.getExposuredetails(bankId,request);
		dynaForm.set("exposurelmtAmt", exposurelmtAmt);
		Log.log(5, "ApplicationProcessingAction", "getAddtlTCInfo", "exposure exposurelmtAmt :"
				+ exposurelmtAmt);
       //-----say
		if (bankId.equals("0000")) {
			memberId = "";
		}

		dynaForm.set("selectMember", memberId);
		dynaForm.set("mliID", memberId);
		dynaForm.set("bankId", bankId);

		zoneId = null;
		branchId = null;

		Log.log(4, "ApplicationProcessingAction", "getAddtlTCInfo", "Exited");

		return mapping.findForward("appDetailPage");
	}

	public ActionForward getEnhanceWCInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ApplicationProcessingAction", "getEnhanceWCInfo", "Entered");
		ApplicationProcessor appProcessor = new ApplicationProcessor();
		DynaActionForm dynaForm = (DynaActionForm) form;
		dynaForm.initialize(mapping);

		HttpSession session = request.getSession(false);
		session.removeAttribute("APPLICATION_LOAN_TYPE");

		session.setAttribute("APPLICATION_TYPE", "WCE");

		User user = getUserInformation(request);
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String memberId = bankId + zoneId + branchId;
		
		//sayali------------
		
		Log.log(4, "ApplicationProcessingAction", "getEnhanceWCInfo", "sessionuserid="+session.getAttribute("USER_ID")+""+session.getValueNames());
	    double exposurelmtAmt = appProcessor.getExposuredetails(bankId,request);
		dynaForm.set("exposurelmtAmt", exposurelmtAmt);
		Log.log(5, "ApplicationProcessingAction", "getEnhanceWCInfo", "exposure exposurelmtAmt :"
				+ exposurelmtAmt);
//-----say

		if (bankId.equals("0000")) {
			memberId = "";
		}

		dynaForm.set("selectMember", memberId);
		dynaForm.set("mliID", memberId);
		dynaForm.set("bankId", bankId);

		zoneId = null;
		branchId = null;
		memberId = null;
		bankId = null;

		Log.log(4, "ApplicationProcessingAction", "getEnhanceWCInfo", "Exited");

		return mapping.findForward("appDetailPage");
	}

	public ActionForward getRenewWCInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(4, "ApplicationProcessingAction", "getRenewWCInfo", "Entered");
		ApplicationProcessor appProcessor = new ApplicationProcessor();
		DynaActionForm dynaForm = (DynaActionForm) form;
		dynaForm.initialize(mapping);

		HttpSession session = request.getSession(false);
		session.removeAttribute("APPLICATION_LOAN_TYPE");

		session.setAttribute("APPLICATION_TYPE", "WCR");

		User user = getUserInformation(request);
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String memberId = bankId + zoneId + branchId;
		
		//sayali------------
		
		Log.log(4, "ApplicationProcessingAction", "getRenewWCInfo", "sessionuserid="+session.getAttribute("USER_ID")+""+session.getValueNames());
	    double exposurelmtAmt = appProcessor.getExposuredetails(bankId,request);
		dynaForm.set("exposurelmtAmt", exposurelmtAmt);
		Log.log(5, "ApplicationProcessingAction", "getRenewWCInfo", "exposure exposurelmtAmt :"
				+ exposurelmtAmt);
       //-----say


		if (bankId.equals("0000")) {
			memberId = "";
		}

		dynaForm.set("selectMember", memberId);
		dynaForm.set("mliID", memberId);
		dynaForm.set("bankId", bankId);

		zoneId = null;
		branchId = null;

		Log.log(4, "ApplicationProcessingAction", "getRenewWCInfo", "Exited");

		return mapping.findForward("appDetailPage");
	}

	public ActionForward getModifyInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(4, "ApplicationProcessingAction", "getModifyInfo", "Entered");

		DynaActionForm dynaForm = (DynaActionForm) form;
		dynaForm.initialize(mapping);

		HttpSession session = request.getSession(false);

		session.removeAttribute("APPLICATION_LOAN_TYPE");

		session.setAttribute("APPLICATION_TYPE", "MA");

		User user = getUserInformation(request);
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String memberId = bankId + zoneId + branchId;

		if (bankId.equals("0000")) {
			memberId = "";
		}

		dynaForm.set("selectMember", memberId);
		dynaForm.set("mliID", memberId);
		dynaForm.set("bankId", bankId);

		zoneId = null;
		branchId = null;

		Log.log(4, "ApplicationProcessingAction", "getModifyInfo", "Exited");

		return mapping.findForward("modifyDetailPage");
	}

	public ActionForward modifyAppBranchName(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ApplicationProcessingAction", "modifyAppBranchName",
				"Entered");

		DynaActionForm dynaForm = (DynaActionForm) form;
		dynaForm.initialize(mapping);

		HttpSession session = request.getSession(false);

		User user = getUserInformation(request);
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String memberId = bankId + zoneId + branchId;

		if (bankId.equals("0000")) {
			memberId = "";
		}

		dynaForm.set("selectMember", memberId);
		dynaForm.set("mliID", memberId);
		dynaForm.set("bankId", bankId);

		zoneId = null;
		branchId = null;

		Log.log(4, "ApplicationProcessingAction", "modifyAppBranchName",
				"Exited");

		return mapping.findForward("modifyDetailPage");
	}

	public ActionForward aftermodifyAppBranchName(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ApplicationProcessingAction", "aftermodifyAppBranchName",
				"Entered");

		DynaActionForm dynaForm = (DynaActionForm) form;

		HttpSession session = request.getSession(false);

		ApplicationProcessor appProcessor = new ApplicationProcessor();
		ApplicationDAO applicationDAO = new ApplicationDAO();
		GMProcessor gmProcessor = new GMProcessor();

		String mliBranchName = "";
		String appRefNo = "";
		String memberId = (String) dynaForm.get("selectMember");

		ClaimsProcessor processor = new ClaimsProcessor();
		Vector memberids = processor.getAllMemberIds();
		if (!memberids.contains(memberId)) {
			throw new NoMemberFoundException("Member Id :" + memberId
					+ " does not exist in the database.");
		}

		String cgpan = (String) dynaForm.get("cgpan");

		ArrayList borrowerIds = new ArrayList();
		borrowerIds = gmProcessor.getBorrowerIds(memberId);

		if (!cgpan.equals("")) {
			String bIdForThisCgpan = processor.getBorowwerForCGPAN(cgpan);
			Log.log(5, "GMAction", "submitClosureDetails", " Bid For Pan - "
					+ bIdForThisCgpan);
			if (!borrowerIds.contains(bIdForThisCgpan)) {
				throw new NoDataException(cgpan + "is not a valid Cgpan for "
						+ "the Member Id :" + memberId
						+ ". Please enter correct Cgpan");
			}

		}

		mliBranchName = applicationDAO.getBranchName(cgpan);
		dynaForm.set("mliBranchName", mliBranchName);
		appRefNo = applicationDAO.getAppRefNo(cgpan);
		dynaForm.set("appRefNo", appRefNo);

		Log.log(4, "ApplicationProcessingAction", "aftermodifyAppBranchName",
				"Exited");

		return mapping.findForward("modifyDetailPage");
	}

	public ActionForward submitmodifyAppBranchName(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ApplicationProcessingAction", "submitmodifyAppBranchName",
				"Entered");

		DynaActionForm dynaForm = (DynaActionForm) form;

		HttpSession session = request.getSession(false);
		ApplicationDAO applicationDAO = new ApplicationDAO();
		User user = getUserInformation(request);
		String userId = user.getUserId();
		String memberId = (String) dynaForm.get("selectMember");
		String appRefNo = (String) dynaForm.get("appRefNo");
		String cgpan = (String) dynaForm.get("cgpan");
		String mliBranchName = (String) dynaForm.get("mliBranchName");
		applicationDAO.updateBranchNameForCgpan(memberId, appRefNo, cgpan,
				mliBranchName, userId);
		request.setAttribute("message", "<b> Modify Branch Name request for "
				+ cgpan + " updated successfully.<b><br>");

		Log.log(4, "ApplicationProcessingAction", "submitmodifyAppBranchName",
				"Exited");

		return mapping.findForward("success");
	}

	public ActionForward getReapplyRejectedApps(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ApplicationProcessingAction", "getReapplyRejectedApps",
				"Entered");

		DynaActionForm dynaForm = (DynaActionForm) form;
		dynaForm.initialize(mapping);

		HttpSession session = request.getSession(false);

		session.removeAttribute("APPLICATION_LOAN_TYPE");

		session.setAttribute("APPLICATION_TYPE", "MA");

		User user = getUserInformation(request);
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String memberId = bankId + zoneId + branchId;

		if (bankId.equals("0000")) {
			memberId = "";
		}

		dynaForm.set("selectMember", memberId);
		dynaForm.set("mliID", memberId);
		dynaForm.set("bankId", bankId);

		zoneId = null;
		branchId = null;

		Log.log(4, "ApplicationProcessingAction", "getReapplyRejectedApps",
				"Exited");

		return mapping.findForward("modifyDetailPage");
	}

	public ActionForward getApps(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(4, "ApplicationProcessingAction", "getApps", "Entered");
		// //System.out.println("getApps called");
		// saveToken(request);
		HttpSession session1 = request.getSession(false);
		String applicationType = (String) session1
				.getAttribute("APPLICATION_LOAN_TYPE");

		Registration registration = new Registration();

		ApplicationProcessor appProcessor = new ApplicationProcessor();

		DynaActionForm dynaForm = (DynaActionForm) form;

		String forward = "";

		String memberId = (String) dynaForm.get("selectMember");

		Log.log(5, "ApplicationProcessingAction", "getApps", "MEmber Id :"
				+ memberId);

		String bankId = memberId.substring(0, 4);
		String zoneId = memberId.substring(4, 8);
		String branchId = memberId.substring(8, 12);

		ClaimsProcessor cpProcessor = new ClaimsProcessor();
		Vector memberIds = cpProcessor.getAllMemberIds();
		if (!memberIds.contains(memberId)) {
			throw new NoMemberFoundException("The Member ID does not exist");
		}
		// ArrayList brachCode=registration.getBranchCode(bankId);
		// dynaForm.set("mliBranchCodeList", brachCode);

		/* Changes for GST */
		/*
		 * User user = getUserInformation(request); String bankId1 =
		 * user.getBankId();
		 */
		List<MLIInfo> branchStateList = registration.getGSTStateList(bankId);
		dynaForm.set("branchStateList", branchStateList);
		request.setAttribute("branchStateList", branchStateList);
		HttpSession session = request.getSession();
		session.setAttribute("bankId", bankId);

		/*
		 * List<MLIInfo> gstNoList=null;
		 * 
		 * gstNoList=branchStateList;
		 * if(request.getParameter("stateCode")!=null){ String gstNo = ""; for
		 * (MLIInfo mliInfolist : gstNoList) {
		 * if(mliInfolist.getStateCode().equalsIgnoreCase
		 * (request.getParameter("stateCode"))){ gstNo = mliInfolist.getgstNo();
		 * 
		 * } }
		 * 
		 * }
		 */

		// request.setAttribute("branchStateList", stateList);

		MLIInfo mliInfo = registration.getMemberDetails(bankId, zoneId,
				branchId);
		ArrayList statesList = (ArrayList) getStateList();
		dynaForm.set("statesList", statesList);
		if (mliInfo != null) {
			Log.log(5, "ApplicationProcessingAction", "getApps", "mli Info.. :"
					+ mliInfo);

			String statusFlag = mliInfo.getStatus();
			if (statusFlag.equals("I")) {
				throw new NoDataException("Member Id:" + memberId
						+ "  has been deactivated.");
			}
			Log.log(5, "ApplicationProcessingAction", "getApps",
					"mli Info mcgf.. :" + mliInfo.getSupportMCGF());
			if (mliInfo.getSupportMCGF().equals("Y")) {
				Log.log(5, "ApplicationProcessingAction", "getApps",
						"MCGF Flag entered..");

				MCGSProcessor mcgsProcessor = new MCGSProcessor();
				session1.setAttribute("MCGF_FLAG", "M");
				ArrayList participatingBanks = mcgsProcessor
						.getAllParticipatingBanks(memberId);
				if ((participatingBanks == null)
						|| (participatingBanks.size() == 0)) {
					throw new NoDataException(
							"Participating Banks are not available for this member.Hence Application cannot be submitted.");
				}

				dynaForm.set("participatingBanks", participatingBanks);

				dynaForm.set("participatingBanks", participatingBanks);
				String bankName = mliInfo.getBankName();
				dynaForm.set("mcgfName", bankName);
				dynaForm.set("mcgfId", memberId);
				dynaForm.set("scheme", "MCGS");

				ArrayList ssiRefNosList = appProcessor
						.getSsiRefNosForMcgf(memberId);

				if ((ssiRefNosList == null) || (ssiRefNosList.size() == 0)) {
					Log.log(4, "ApplicationProcessingAction", "getApps",
							"No Borrowers");
					throw new NoDataException(
							"There are no borrowers for this Member");
				}

				dynaForm.set("allSsiRefNos", ssiRefNosList);

				forward = "ssiRefNosPage";

				mcgsProcessor = null;
				bankName = null;
				participatingBanks = null;
			} else {
				dynaForm.set("scheme", "CGFSI");

				session1.setAttribute("MCGF_FLAG", "NM");

				forward = setApps(applicationType);
			}
		} else {
			throw new NoMemberFoundException("No Member Details Found");
		}
		mliInfo = null;
		cpProcessor = null;
		registration = null;
		memberIds = null;
		memberId = null;

		ArrayList socialList = getSocialCategory();
		dynaForm.set("socialCategoryList", socialList);

		ArrayList industryNatureList = getIndustryNature();
		dynaForm.set("industryNatureList", industryNatureList);

		dynaForm.set("dcHandicrafts", "N");
		dynaForm.set("handiCrafts", "N");
		dynaForm.set("dcHandlooms", "N");
		dynaForm.set("previouslyCovered", "");

		statesList = null;

		socialList = null;

		industryNatureList = null;

		applicationType = null;

		Log.log(4, "ApplicationProcessingAction", "getApps", "Exited");

		return mapping.findForward(forward);
	}

	/* Created by DKR for GST */
	public ActionForward getGSTNO(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(4, "ApplicationProcessingAction", "getGSTNO", "Entered");
		try {
			PrintWriter out = response.getWriter();
			User user = getUserInformation(request);
			String bankId = user.getBankId();
			Administrator admin = new Administrator();
			DynaActionForm dynaForm = (DynaActionForm) form;
			HttpSession session1 = request.getSession(false);
			String stateCode = request.getParameter("stateCode");
			String gstNo = "";
			/*
			 * if (bankId.equals("0000")) { HttpSession
			 * session=request.getSession(); String
			 * bankId1=(String)request.getAttribute("BankId");
			 * gstNo=registration.getGstNo(stateCode,bankId1);
			 * 
			 * }else{
			 */
			gstNo = registration.getGstNo(stateCode, bankId);
			response.setContentType("text/plain");
			// }
			// dynaForm.set("gst",gstNo);
			String applicationType = (String) session1
					.getAttribute("APPLICATION_LOAN_TYPE");

			out.print(gstNo);
		} catch (Exception e) {
			System.err.println("Exception in ApplicationProcessingAction..."
					+ e);

		}

		return null;
	}
	
	public ActionForward getItpanAmount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(4, "ApplicationProcessingAction", "getItpanAmount", "Entered");
		try {
			PrintWriter out = response.getWriter();
			User user = getUserInformation(request);
			String bankId = user.getBankId();
			String zoneId = user.getZoneId();
			String branchId = user.getBranchId();
			String memberId = bankId + zoneId + branchId;

			Administrator admin = new Administrator();
			ApplicationDAO appDao=new ApplicationDAO();
			DynaActionForm dynaForm = (DynaActionForm) form;
			HttpSession session1 = request.getSession(false);
			String firmITPAN = request.getParameter("ssiITPan");
			//System.out.println("firmITPAN==java=="+firmITPAN);
			//String cpITPAN = request.getParameter("cpITPAN");
			String cpITPAN ="";
					
			//System.out.println("cpITPAN==java=="+cpITPAN);
			double ExistingFacilityAmount = 0.0;
			/*
			 * if (bankId.equals("0000")) { HttpSession
			 * session=request.getSession(); String
			 * bankId1=(String)request.getAttribute("BankId");
			 * gstNo=registration.getGstNo(stateCode,bankId1);
			 * 
			 * }else{
			 */
			ExistingFacilityAmount = appDao.getItpanAmount(firmITPAN, cpITPAN,memberId);
			//System.out.println("ExistingFacilityAmount==java=="+ExistingFacilityAmount);
			response.setContentType("text/plain");
			// }
			// dynaForm.set("gst",gstNo);
			String applicationType = (String) session1
					.getAttribute("APPLICATION_LOAN_TYPE");

			out.print(ExistingFacilityAmount);
		} catch (Exception e) {
			System.err.println("Exception in ApplicationProcessingAction..."
					+ e);

		}

		return null;
	}
	
	public ActionForward getTotalItpanAmount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(4, "ApplicationProcessingAction", "getItpanAmount", "Entered");
		try {
			PrintWriter out = response.getWriter();
			User user = getUserInformation(request);
			String bankId = user.getBankId();
			String zoneId = user.getZoneId();
			String branchId = user.getBranchId();
			String memberId = bankId + zoneId + branchId;

			Administrator admin = new Administrator();
			ApplicationDAO appDao=new ApplicationDAO();
			DynaActionForm dynaForm = (DynaActionForm) form;
			HttpSession session1 = request.getSession(false);
			String firmITPAN = request.getParameter("ssiITPan");
			//System.out.println("firmITPAN==java=="+firmITPAN);
			//String cpITPAN = request.getParameter("cpITPAN");
			String cpITPAN ="";
					
			//System.out.println("cpITPAN==java=="+cpITPAN);
			double ExistingFacilityAmount = 0.0;
			/*
			 * if (bankId.equals("0000")) { HttpSession
			 * session=request.getSession(); String
			 * bankId1=(String)request.getAttribute("BankId");
			 * gstNo=registration.getGstNo(stateCode,bankId1);
			 * 
			 * }else{
			 */
			ExistingFacilityAmount = appDao.getTotalItpanAmount(firmITPAN, cpITPAN,memberId);
			//System.out.println("ExistingFacilityAmount==java=="+ExistingFacilityAmount);
			response.setContentType("text/plain");
			// }
			// dynaForm.set("gst",gstNo);
			String applicationType = (String) session1
					.getAttribute("APPLICATION_LOAN_TYPE");

			out.print(ExistingFacilityAmount);
		} catch (Exception e) {
			System.err.println("Exception in ApplicationProcessingAction..."
					+ e);

		}

		return null;
	}
	
	public ActionForward getPplosItpanAmount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(4, "ApplicationProcessingAction", "getItpanAmount", "Entered");
		try {
			PrintWriter out = response.getWriter();
			User user = getUserInformation(request);
			String bankId = user.getBankId();
			String zoneId = user.getZoneId();
			String branchId = user.getBranchId();
			String memberId = bankId + zoneId + branchId;

			Administrator admin = new Administrator();
			ApplicationDAO appDao=new ApplicationDAO();
			DynaActionForm dynaForm = (DynaActionForm) form;
			HttpSession session1 = request.getSession(false);
			String firmITPAN = request.getParameter("ssiITPan");
			//System.out.println("firmITPAN==java=="+firmITPAN);
			//String cpITPAN = request.getParameter("cpITPAN");
			String cpITPAN ="";
					
			//System.out.println("cpITPAN==java=="+cpITPAN);
			double pplos = 0.0;
			/*
			 * if (bankId.equals("0000")) { HttpSession
			 * session=request.getSession(); String
			 * bankId1=(String)request.getAttribute("BankId");
			 * gstNo=registration.getGstNo(stateCode,bankId1);
			 * 
			 * }else{
			 */
			pplos = appDao.getPplosItpanAmount(firmITPAN, cpITPAN,memberId);
			//System.out.println("pplOS==java=="+pplos);
			response.setContentType("text/plain");
			// }
			// dynaForm.set("gst",gstNo);
			String applicationType = (String) session1
					.getAttribute("APPLICATION_LOAN_TYPE");

			out.print(pplos);
		} catch (Exception e) {
			System.err.println("Exception in ApplicationProcessingAction..."
					+ e);

		}

		return null;
	}
	
	public ActionForward getDPDValue(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(4, "ApplicationProcessingAction", "getItpanAmount", "Entered");
		try {
			PrintWriter out = response.getWriter();
			User user = getUserInformation(request);
			String bankId = user.getBankId();
			String zoneId = user.getZoneId();
			String branchId = user.getBranchId();
			String memberId = bankId + zoneId + branchId;

			Administrator admin = new Administrator();
			ApplicationDAO appDao=new ApplicationDAO();
			DynaActionForm dynaForm = (DynaActionForm) form;
			HttpSession session1 = request.getSession(false);
			String firmITPAN = request.getParameter("ssiITPan");
			//System.out.println("firmITPAN==java=="+firmITPAN);
			//String cpITPAN = request.getParameter("cpITPAN");
			String cpITPAN ="";
					
			//System.out.println("cpITPAN==java=="+cpITPAN);
			int DPDStatus =0;
			/*
			 * if (bankId.equals("0000")) { HttpSession
			 * session=request.getSession(); String
			 * bankId1=(String)request.getAttribute("BankId");
			 * gstNo=registration.getGstNo(stateCode,bankId1);
			 * 
			 * }else{
			 */
			DPDStatus = appDao.getDPDValue(firmITPAN, cpITPAN,memberId);
			//System.out.println("DPDStatus==java=="+DPDStatus);
			response.setContentType("text/plain");
			// }
			// dynaForm.set("gst",gstNo);
			String applicationType = (String) session1
					.getAttribute("APPLICATION_LOAN_TYPE");

			out.print(DPDStatus);
		} catch (Exception e) {
			System.err.println("Exception in ApplicationProcessingAction..."
					+ e);

		}

		return null;
	}
	

	private Collection getStateList() {
		ArrayList statesList = null;
		try {
			Administrator admin = new Administrator();
			statesList = admin.getAllStates();
			admin = null;
		} catch (Exception e) {
			Log.log(2, "ApplicationProcessingAction", "getStateList",
					e.getMessage());
			Log.logException(e);
		}

		return statesList;
	}

	public ActionForward getRSFApps(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(4, "ApplicationProcessingAction", "getRSFApps", "Entered");

		HttpSession session1 = request.getSession(false);

		String applicationType = (String) session1
				.getAttribute("APPLICATION_LOAN_TYPE");

		Registration registration = new Registration();

		ApplicationProcessor appProcessor = new ApplicationProcessor();

		DynaActionForm dynaForm = (DynaActionForm) form;

		String forward = "";

		String memberId = (String) dynaForm.get("selectMember");

		Log.log(5, "ApplicationProcessingAction", "getRSFApps", "MEmber Id :"
				+ memberId);

		ArrayList rsfParticipatingBanks = getAllRsfParticipatingBanks();

		if (!rsfParticipatingBanks.contains(memberId.substring(0, 4))) {
			throw new NoDataException("Member Id:" + memberId
					+ "  has not been registered under RSF.");
		}
		String bankId = memberId.substring(0, 4);
		String zoneId = memberId.substring(4, 8);
		String branchId = memberId.substring(8, 12);

		ClaimsProcessor cpProcessor = new ClaimsProcessor();
		Vector memberIds = cpProcessor.getAllMemberIds();
		if (!memberIds.contains(memberId)) {
			throw new NoMemberFoundException("The Member ID does not exist");
		}

		MLIInfo mliInfo = registration.getMemberDetails(bankId, zoneId,
				branchId);

		if (mliInfo != null) {
			Log.log(5, "ApplicationProcessingAction", "getRSFApps",
					"mli Info.. :" + mliInfo);

			String statusFlag = mliInfo.getStatus();

			if (statusFlag.equals("I")) {
				throw new NoDataException("Member Id:" + memberId
						+ "  has been deactivated.");
			}
			Log.log(5, "ApplicationProcessingAction", "getRSFApps",
					"mli Info mcgf.. :" + mliInfo.getSupportMCGF());
			if (mliInfo.getSupportMCGF().equals("Y")) {
				Log.log(5, "ApplicationProcessingAction", "getRSFApps",
						"MCGF Flag entered..");

				MCGSProcessor mcgsProcessor = new MCGSProcessor();
				session1.setAttribute("MCGF_FLAG", "M");
				ArrayList participatingBanks = mcgsProcessor
						.getAllParticipatingBanks(memberId);
				if ((participatingBanks == null)
						|| (participatingBanks.size() == 0)) {
					throw new NoDataException(
							"Participating Banks are not available for this member.Hence Application cannot be submitted.");
				}

				dynaForm.set("participatingBanks", participatingBanks);

				dynaForm.set("participatingBanks", participatingBanks);
				String bankName = mliInfo.getBankName();

				dynaForm.set("mcgfName", bankName);
				dynaForm.set("mcgfId", memberId);
				dynaForm.set("scheme", "MCGS");

				ArrayList ssiRefNosList = appProcessor
						.getSsiRefNosForMcgf(memberId);

				if ((ssiRefNosList == null) || (ssiRefNosList.size() == 0)) {
					Log.log(4, "ApplicationProcessingAction", "getRSFApps",
							"No Borrowers");
					throw new NoDataException(
							"There are no borrowers for this Member");
				}

				dynaForm.set("allSsiRefNos", ssiRefNosList);

				forward = "ssiRefNosPage";

				mcgsProcessor = null;
				bankName = null;
				participatingBanks = null;
			} else {
				dynaForm.set("scheme", "RSF");

				session1.setAttribute("MCGF_FLAG", "NM");

				forward = setApps(applicationType);
			}
		} else {
			throw new NoMemberFoundException("No Member Details Found");
		}
		mliInfo = null;
		cpProcessor = null;
		registration = null;
		memberIds = null;
		memberId = null;

		ArrayList statesList = (ArrayList) getStateList();
		dynaForm.set("statesList", statesList);

		ArrayList socialList = getSocialCategory();
		dynaForm.set("socialCategoryList", socialList);

		ArrayList industryNatureList = getIndustryNature();
		dynaForm.set("industryNatureList", industryNatureList);

		statesList = null;

		socialList = null;

		industryNatureList = null;

		applicationType = null;

		Log.log(4, "ApplicationProcessingAction", "getRSFApps", "Exited");

		return mapping.findForward(forward);
	}

	public ActionForward getRSF2Apps(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(4, "ApplicationProcessingAction", "getRSF2Apps", "Entered");

		HttpSession session1 = request.getSession(false);

		String applicationType = (String) session1
				.getAttribute("APPLICATION_LOAN_TYPE");

		Registration registration = new Registration();

		ApplicationProcessor appProcessor = new ApplicationProcessor();

		DynaActionForm dynaForm = (DynaActionForm) form;

		String forward = "";

		String memberId = (String) dynaForm.get("selectMember");

		Log.log(5, "ApplicationProcessingAction", "getRSF2Apps", "MEmber Id :"
				+ memberId);

		ArrayList rsfParticipatingBanks = getAllRsf2ParticipatingBanks();

		if (!rsfParticipatingBanks.contains(memberId.substring(0, 4))) {
			throw new NoDataException("Member Id:" + memberId
					+ "  has not been registered1 under RSF2.");
		}
		String bankId = memberId.substring(0, 4);
		String zoneId = memberId.substring(4, 8);
		String branchId = memberId.substring(8, 12);

		ClaimsProcessor cpProcessor = new ClaimsProcessor();
		Vector memberIds = cpProcessor.getAllMemberIds();
		if (!memberIds.contains(memberId)) {
			throw new NoMemberFoundException("The Member ID does not exist");
		}

		MLIInfo mliInfo = registration.getMemberDetails(bankId, zoneId,
				branchId);

		if (mliInfo != null) {
			Log.log(5, "ApplicationProcessingAction", "getRSF2Apps",
					"mli Info.. :" + mliInfo);

			String statusFlag = mliInfo.getStatus();

			if (statusFlag.equals("I")) {
				throw new NoDataException("Member Id:" + memberId
						+ "  has been deactivated.");
			}
			Log.log(5, "ApplicationProcessingAction", "getRSFApps",
					"mli Info mcgf.. :" + mliInfo.getSupportMCGF());
			if (mliInfo.getSupportMCGF().equals("Y")) {
				Log.log(5, "ApplicationProcessingAction", "getRSF2Apps",
						"MCGF Flag entered..");

				MCGSProcessor mcgsProcessor = new MCGSProcessor();
				session1.setAttribute("MCGF_FLAG", "M");
				ArrayList participatingBanks = mcgsProcessor
						.getAllParticipatingBanks(memberId);
				if ((participatingBanks == null)
						|| (participatingBanks.size() == 0)) {
					throw new NoDataException(
							"Participating Banks are not available for this member.Hence Application cannot be submitted.");
				}

				dynaForm.set("participatingBanks", participatingBanks);

				dynaForm.set("participatingBanks", participatingBanks);
				String bankName = mliInfo.getBankName();

				dynaForm.set("mcgfName", bankName);
				dynaForm.set("mcgfId", memberId);
				dynaForm.set("scheme", "MCGS");

				ArrayList ssiRefNosList = appProcessor
						.getSsiRefNosForMcgf(memberId);

				if ((ssiRefNosList == null) || (ssiRefNosList.size() == 0)) {
					Log.log(4, "ApplicationProcessingAction", "getRSFApps",
							"No Borrowers");
					throw new NoDataException(
							"There are no borrowers for this Member");
				}

				dynaForm.set("allSsiRefNos", ssiRefNosList);

				forward = "ssiRefNosPage";

				mcgsProcessor = null;
				bankName = null;
				participatingBanks = null;
			} else {
				dynaForm.set("scheme", "RSF2");

				session1.setAttribute("MCGF_FLAG", "NM");

				forward = setApps(applicationType);
			}
		} else {
			throw new NoMemberFoundException("No Member Details Found");
		}
		mliInfo = null;
		cpProcessor = null;
		registration = null;
		memberIds = null;
		memberId = null;

		ArrayList statesList = (ArrayList) getStateList();
		dynaForm.set("statesList", statesList);

		ArrayList socialList = getSocialCategory();
		dynaForm.set("socialCategoryList", socialList);

		ArrayList industryNatureList = getIndustryNature();
		dynaForm.set("industryNatureList", industryNatureList);

		statesList = null;

		socialList = null;

		industryNatureList = null;

		applicationType = null;

		Log.log(4, "ApplicationProcessingAction", "getRSF2Apps", "Exited");

		return mapping.findForward(forward);
	}

	public ActionForward getPCGSApps(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(4, "ApplicationProcessingAction", "getPCGSApps", "Entered");
		HttpSession session1 = request.getSession(false);

		String applicationType = (String) session1
				.getAttribute("APPLICATION_LOAN_TYPE");
		Registration registration = new Registration();
		ApplicationProcessor appProcessor = new ApplicationProcessor();
		DynaActionForm dynaForm = (DynaActionForm) form;

		String forward = "";

		String memberId = (String) dynaForm.get("selectMember");
		Log.log(5, "ApplicationProcessingAction", "getPCGSApps", "MEmber Id :"
				+ memberId);

		String bankId = memberId.substring(0, 4);
		String zoneId = memberId.substring(4, 8);
		String branchId = memberId.substring(8, 12);

		ClaimsProcessor cpProcessor = new ClaimsProcessor();
		Vector memberIds = cpProcessor.getAllMemberIds();
		if (!memberIds.contains(memberId)) {
			throw new NoMemberFoundException("The Member ID does not exist");
		}

		MLIInfo mliInfo = registration.getMemberDetails(bankId, zoneId,
				branchId);

		if (mliInfo != null) {
			Log.log(5, "ApplicationProcessingAction", "getPCGSApps",
					"mli Info.. :" + mliInfo);

			String statusFlag = mliInfo.getStatus();
			if (statusFlag.equals("I")) {
				throw new NoDataException("Member Id:" + memberId
						+ "  has been deactivated.");
			}
			Log.log(5, "ApplicationProcessingAction", "getPCGSApps",
					"mli Info mcgf.. :" + mliInfo.getSupportMCGF());
			dynaForm.set("scheme", "PCGS");

			session1.setAttribute("MCGF_FLAG", "NM");

			forward = setApps(applicationType);
		} else {
			throw new NoMemberFoundException("No Member Details Found");
		}
		mliInfo = null;
		cpProcessor = null;
		registration = null;
		memberIds = null;
		memberId = null;

		ArrayList statesList = (ArrayList) getStateList();
		dynaForm.set("statesList", statesList);

		ArrayList socialList = getSocialCategory();
		dynaForm.set("socialCategoryList", socialList);

		ArrayList industryNatureList = getIndustryNature();
		dynaForm.set("industryNatureList", industryNatureList);

		ArrayList industrySectorList = getIndustrySector();

		dynaForm.set("industrySectorList", industrySectorList);

		statesList = null;

		socialList = null;

		industryNatureList = null;

		industrySectorList = null;

		applicationType = null;

		Log.log(4, "ApplicationProcessingAction", "getPCGSApps", "Exited");

		return mapping.findForward(forward);
	}

	public ActionForward getRSFBothApps(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(4, "ApplicationProcessingAction", "getRSFWcApps", "Entered");

		HttpSession session1 = request.getSession(false);

		String applicationType = (String) session1
				.getAttribute("APPLICATION_LOAN_TYPE");

		Registration registration = new Registration();

		ApplicationProcessor appProcessor = new ApplicationProcessor();

		DynaActionForm dynaForm = (DynaActionForm) form;

		String forward = "";

		String memberId = (String) dynaForm.get("selectMember");

		Log.log(5, "ApplicationProcessingAction", "getRSFBothApps",
				"MEmber Id :" + memberId);

		ArrayList rsfParticipatingBanks = getAllRsfParticipatingBanks();

		if (!rsfParticipatingBanks.contains(memberId.substring(0, 4))) {
			throw new NoDataException("Member Id:" + memberId
					+ "  has not been registered under RSF.");
		}

		String bankId = memberId.substring(0, 4);
		String zoneId = memberId.substring(4, 8);
		String branchId = memberId.substring(8, 12);

		ClaimsProcessor cpProcessor = new ClaimsProcessor();
		Vector memberIds = cpProcessor.getAllMemberIds();
		if (!memberIds.contains(memberId)) {
			throw new NoMemberFoundException("The Member ID does not exist");
		}

		MLIInfo mliInfo = registration.getMemberDetails(bankId, zoneId,
				branchId);

		if (mliInfo != null) {
			Log.log(5, "ApplicationProcessingAction", "getRSFBothApps",
					"mli Info.. :" + mliInfo);

			String statusFlag = mliInfo.getStatus();

			if (statusFlag.equals("I")) {
				throw new NoDataException("Member Id:" + memberId
						+ "  has been deactivated.");
			}
			Log.log(5, "ApplicationProcessingAction", "getRSFBothApps",
					"mli Info mcgf.. :" + mliInfo.getSupportMCGF());
			if (mliInfo.getSupportMCGF().equals("Y")) {
				Log.log(5, "ApplicationProcessingAction", "getRSFBothApps",
						"MCGF Flag entered..");

				MCGSProcessor mcgsProcessor = new MCGSProcessor();
				session1.setAttribute("MCGF_FLAG", "M");
				ArrayList participatingBanks = mcgsProcessor
						.getAllParticipatingBanks(memberId);
				if ((participatingBanks == null)
						|| (participatingBanks.size() == 0)) {
					throw new NoDataException(
							"Participating Banks are not available for this member.Hence Application cannot be submitted.");
				}

				dynaForm.set("participatingBanks", participatingBanks);

				dynaForm.set("participatingBanks", participatingBanks);
				String bankName = mliInfo.getBankName();

				dynaForm.set("mcgfName", bankName);
				dynaForm.set("mcgfId", memberId);
				dynaForm.set("scheme", "MCGS");

				ArrayList ssiRefNosList = appProcessor
						.getSsiRefNosForMcgf(memberId);

				if ((ssiRefNosList == null) || (ssiRefNosList.size() == 0)) {
					Log.log(4, "ApplicationProcessingAction", "getRSFBothApps",
							"No Borrowers");
					throw new NoDataException(
							"There are no borrowers for this Member");
				}

				dynaForm.set("allSsiRefNos", ssiRefNosList);

				forward = "ssiRefNosPage";

				mcgsProcessor = null;
				bankName = null;
				participatingBanks = null;
			} else {
				dynaForm.set("scheme", "RSF");

				session1.setAttribute("MCGF_FLAG", "NM");

				forward = setApps(applicationType);
			}
		} else {
			throw new NoMemberFoundException("No Member Details Found");
		}
		mliInfo = null;
		cpProcessor = null;
		registration = null;
		memberIds = null;
		memberId = null;

		ArrayList statesList = (ArrayList) getStateList();
		dynaForm.set("statesList", statesList);

		ArrayList socialList = getSocialCategory();
		dynaForm.set("socialCategoryList", socialList);

		ArrayList industryNatureList = getIndustryNature();
		dynaForm.set("industryNatureList", industryNatureList);

		statesList = null;

		socialList = null;

		industryNatureList = null;

		applicationType = null;

		Log.log(4, "ApplicationProcessingAction", "getRSFBothApps", "Exited");

		return mapping.findForward(forward);
	}

	public ActionForward getRSF2BothApps(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ApplicationProcessingAction", "getRSFWcApps", "Entered");

		HttpSession session1 = request.getSession(false);

		String applicationType = (String) session1
				.getAttribute("APPLICATION_LOAN_TYPE");

		Registration registration = new Registration();

		ApplicationProcessor appProcessor = new ApplicationProcessor();

		DynaActionForm dynaForm = (DynaActionForm) form;

		String forward = "";

		String memberId = (String) dynaForm.get("selectMember");

		Log.log(5, "ApplicationProcessingAction", "getRSFBothApps",
				"MEmber Id :" + memberId);

		ArrayList rsfParticipatingBanks = getAllRsfParticipatingBanks();

		if (!rsfParticipatingBanks.contains(memberId.substring(0, 4))) {
			throw new NoDataException("Member Id:" + memberId
					+ "  has not been registered under RSF2both.");
		}

		String bankId = memberId.substring(0, 4);
		String zoneId = memberId.substring(4, 8);
		String branchId = memberId.substring(8, 12);

		ClaimsProcessor cpProcessor = new ClaimsProcessor();
		Vector memberIds = cpProcessor.getAllMemberIds();
		if (!memberIds.contains(memberId)) {
			throw new NoMemberFoundException("The Member ID does not exist");
		}

		MLIInfo mliInfo = registration.getMemberDetails(bankId, zoneId,
				branchId);

		if (mliInfo != null) {
			Log.log(5, "ApplicationProcessingAction", "getRSFBothApps",
					"mli Info.. :" + mliInfo);

			String statusFlag = mliInfo.getStatus();

			if (statusFlag.equals("I")) {
				throw new NoDataException("Member Id:" + memberId
						+ "  has been deactivated.");
			}
			Log.log(5, "ApplicationProcessingAction", "getRSFBothApps",
					"mli Info mcgf.. :" + mliInfo.getSupportMCGF());
			if (mliInfo.getSupportMCGF().equals("Y")) {
				Log.log(5, "ApplicationProcessingAction", "getRSFBothApps",
						"MCGF Flag entered..");

				MCGSProcessor mcgsProcessor = new MCGSProcessor();
				session1.setAttribute("MCGF_FLAG", "M");
				ArrayList participatingBanks = mcgsProcessor
						.getAllParticipatingBanks(memberId);
				if ((participatingBanks == null)
						|| (participatingBanks.size() == 0)) {
					throw new NoDataException(
							"Participating Banks are not available for this member.Hence Application cannot be submitted.");
				}

				dynaForm.set("participatingBanks", participatingBanks);

				dynaForm.set("participatingBanks", participatingBanks);
				String bankName = mliInfo.getBankName();

				dynaForm.set("mcgfName", bankName);
				dynaForm.set("mcgfId", memberId);
				dynaForm.set("scheme", "MCGS");

				ArrayList ssiRefNosList = appProcessor
						.getSsiRefNosForMcgf(memberId);

				if ((ssiRefNosList == null) || (ssiRefNosList.size() == 0)) {
					Log.log(4, "ApplicationProcessingAction", "getRSFBothApps",
							"No Borrowers");
					throw new NoDataException(
							"There are no borrowers for this Member");
				}

				dynaForm.set("allSsiRefNos", ssiRefNosList);

				forward = "ssiRefNosPage";

				mcgsProcessor = null;
				bankName = null;
				participatingBanks = null;
			} else {
				dynaForm.set("scheme", "RSF2");

				session1.setAttribute("MCGF_FLAG", "NM");

				forward = setApps(applicationType);
			}
		} else {
			throw new NoMemberFoundException("No Member Details Found");
		}
		mliInfo = null;
		cpProcessor = null;
		registration = null;
		memberIds = null;
		memberId = null;

		ArrayList statesList = (ArrayList) getStateList();
		dynaForm.set("statesList", statesList);

		ArrayList socialList = getSocialCategory();
		dynaForm.set("socialCategoryList", socialList);

		ArrayList industryNatureList = getIndustryNature();
		dynaForm.set("industryNatureList", industryNatureList);

		statesList = null;

		socialList = null;

		industryNatureList = null;

		applicationType = null;

		Log.log(4, "ApplicationProcessingAction", "getRSFBothApps", "Exited");

		return mapping.findForward(forward);
	}

	public ActionForward getRSFWcApps(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(4, "ApplicationProcessingAction", "getRSFWcApps", "Entered");

		HttpSession session1 = request.getSession(false);

		String applicationType = (String) session1
				.getAttribute("APPLICATION_LOAN_TYPE");

		Registration registration = new Registration();

		ApplicationProcessor appProcessor = new ApplicationProcessor();

		DynaActionForm dynaForm = (DynaActionForm) form;

		String forward = "";

		String memberId = (String) dynaForm.get("selectMember");

		Log.log(5, "ApplicationProcessingAction", "getRSFWcApps", "MEmber Id :"
				+ memberId);

		ArrayList rsfParticipatingBanks = getAllRsf2ParticipatingBanks();

		if (!rsfParticipatingBanks.contains(memberId.substring(0, 4))) {
			throw new NoDataException("Member Id:" + memberId
					+ "  has not been registered under RSF.");
		}

		String bankId = memberId.substring(0, 4);
		String zoneId = memberId.substring(4, 8);
		String branchId = memberId.substring(8, 12);

		ClaimsProcessor cpProcessor = new ClaimsProcessor();
		Vector memberIds = cpProcessor.getAllMemberIds();
		if (!memberIds.contains(memberId)) {
			throw new NoMemberFoundException("The Member ID does not exist");
		}

		MLIInfo mliInfo = registration.getMemberDetails(bankId, zoneId,
				branchId);

		if (mliInfo != null) {
			Log.log(5, "ApplicationProcessingAction", "getRSFWcApps",
					"mli Info.. :" + mliInfo);

			String statusFlag = mliInfo.getStatus();

			if (statusFlag.equals("I")) {
				throw new NoDataException("Member Id:" + memberId
						+ "  has been deactivated.");
			}
			Log.log(5, "ApplicationProcessingAction", "getRSFWcApps",
					"mli Info mcgf.. :" + mliInfo.getSupportMCGF());
			if (mliInfo.getSupportMCGF().equals("Y")) {
				Log.log(5, "ApplicationProcessingAction", "getRSFWcApps",
						"MCGF Flag entered..");

				MCGSProcessor mcgsProcessor = new MCGSProcessor();
				session1.setAttribute("MCGF_FLAG", "M");
				ArrayList participatingBanks = mcgsProcessor
						.getAllParticipatingBanks(memberId);
				if ((participatingBanks == null)
						|| (participatingBanks.size() == 0)) {
					throw new NoDataException(
							"Participating Banks are not available for this member.Hence Application cannot be submitted.");
				}

				dynaForm.set("participatingBanks", participatingBanks);

				dynaForm.set("participatingBanks", participatingBanks);
				String bankName = mliInfo.getBankName();

				dynaForm.set("mcgfName", bankName);
				dynaForm.set("mcgfId", memberId);
				dynaForm.set("scheme", "MCGS");

				ArrayList ssiRefNosList = appProcessor
						.getSsiRefNosForMcgf(memberId);

				if ((ssiRefNosList == null) || (ssiRefNosList.size() == 0)) {
					Log.log(4, "ApplicationProcessingAction", "getRSFWcApps",
							"No Borrowers");
					throw new NoDataException(
							"There are no borrowers for this Member");
				}

				dynaForm.set("allSsiRefNos", ssiRefNosList);

				forward = "ssiRefNosPage";

				mcgsProcessor = null;
				bankName = null;
				participatingBanks = null;
			} else {
				dynaForm.set("scheme", "RSF");

				session1.setAttribute("MCGF_FLAG", "NM");

				forward = setApps(applicationType);
			}
		} else {
			throw new NoMemberFoundException("No Member Details Found");
		}
		mliInfo = null;
		cpProcessor = null;
		registration = null;
		memberIds = null;
		memberId = null;

		ArrayList statesList = (ArrayList) getStateList();
		dynaForm.set("statesList", statesList);

		ArrayList socialList = getSocialCategory();
		dynaForm.set("socialCategoryList", socialList);

		ArrayList industryNatureList = getIndustryNature();
		dynaForm.set("industryNatureList", industryNatureList);

		statesList = null;

		socialList = null;

		industryNatureList = null;

		applicationType = null;

		Log.log(4, "ApplicationProcessingAction", "getRSFWcApps", "Exited");

		return mapping.findForward(forward);
	}

	public ActionForward getRSF2WcApps(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(4, "ApplicationProcessingAction", "getRSFWcApps", "Entered");

		HttpSession session1 = request.getSession(false);

		String applicationType = (String) session1
				.getAttribute("APPLICATION_LOAN_TYPE");

		Registration registration = new Registration();

		ApplicationProcessor appProcessor = new ApplicationProcessor();

		DynaActionForm dynaForm = (DynaActionForm) form;

		String forward = "";

		String memberId = (String) dynaForm.get("selectMember");

		Log.log(5, "ApplicationProcessingAction", "getRSFWcApps", "MEmber Id :"
				+ memberId);

		ArrayList rsfParticipatingBanks = getAllRsf2ParticipatingBanks();

		if (!rsfParticipatingBanks.contains(memberId.substring(0, 4))) {
			throw new NoDataException("Member Id:" + memberId
					+ "  has not  been registered under RSF2wc.");
		}

		String bankId = memberId.substring(0, 4);
		String zoneId = memberId.substring(4, 8);
		String branchId = memberId.substring(8, 12);

		ClaimsProcessor cpProcessor = new ClaimsProcessor();
		Vector memberIds = cpProcessor.getAllMemberIds();
		if (!memberIds.contains(memberId)) {
			throw new NoMemberFoundException("The Member ID does not exist");
		}

		MLIInfo mliInfo = registration.getMemberDetails(bankId, zoneId,
				branchId);

		if (mliInfo != null) {
			Log.log(5, "ApplicationProcessingAction", "getRSFWcApps",
					"mli Info.. :" + mliInfo);

			String statusFlag = mliInfo.getStatus();

			if (statusFlag.equals("I")) {
				throw new NoDataException("Member Id:" + memberId
						+ "  has been deactivated.");
			}
			Log.log(5, "ApplicationProcessingAction", "getRSFWcApps",
					"mli Info mcgf.. :" + mliInfo.getSupportMCGF());
			if (mliInfo.getSupportMCGF().equals("Y")) {
				Log.log(5, "ApplicationProcessingAction", "getRSFWcApps",
						"MCGF Flag entered..");

				MCGSProcessor mcgsProcessor = new MCGSProcessor();
				session1.setAttribute("MCGF_FLAG", "M");
				ArrayList participatingBanks = mcgsProcessor
						.getAllParticipatingBanks(memberId);
				if ((participatingBanks == null)
						|| (participatingBanks.size() == 0)) {
					throw new NoDataException(
							"Participating Banks are not available for this member.Hence Application cannot be submitted.");
				}

				dynaForm.set("participatingBanks", participatingBanks);

				dynaForm.set("participatingBanks", participatingBanks);
				String bankName = mliInfo.getBankName();

				dynaForm.set("mcgfName", bankName);
				dynaForm.set("mcgfId", memberId);
				dynaForm.set("scheme", "MCGS");

				ArrayList ssiRefNosList = appProcessor
						.getSsiRefNosForMcgf(memberId);

				if ((ssiRefNosList == null) || (ssiRefNosList.size() == 0)) {
					Log.log(4, "ApplicationProcessingAction", "getRSFWcApps",
							"No Borrowers");
					throw new NoDataException(
							"There are no borrowers for this Member");
				}

				dynaForm.set("allSsiRefNos", ssiRefNosList);

				forward = "ssiRefNosPage";

				mcgsProcessor = null;
				bankName = null;
				participatingBanks = null;
			} else {
				dynaForm.set("scheme", "RSF2");

				session1.setAttribute("MCGF_FLAG", "NM");

				forward = setApps(applicationType);
			}
		} else {
			throw new NoMemberFoundException("No Member Details Found");
		}
		mliInfo = null;
		cpProcessor = null;
		registration = null;
		memberIds = null;
		memberId = null;

		ArrayList statesList = (ArrayList) getStateList();
		dynaForm.set("statesList", statesList);

		ArrayList socialList = getSocialCategory();
		dynaForm.set("socialCategoryList", socialList);

		ArrayList industryNatureList = getIndustryNature();
		dynaForm.set("industryNatureList", industryNatureList);

		statesList = null;

		socialList = null;

		industryNatureList = null;

		applicationType = null;

		Log.log(4, "ApplicationProcessingAction", "getRSFWcApps", "Exited");

		return mapping.findForward(forward);
	}

	private ArrayList getAllRsf2ParticipatingBanks() {
		ArrayList allRsfParticipatingBanks = null;
		try {
			Administrator admin = new Administrator();
			allRsfParticipatingBanks = admin.getAllRsf2ParticipatingBanks();

			admin = null;
		} catch (Exception e) {
			Log.log(2, "ApplicationProcessingAction",
					"getAllRsfParticipatingBanks", e.getMessage());
			Log.logException(e);
		}

		return allRsfParticipatingBanks;
	}

	private ArrayList getAllRsfParticipatingBanks() {
		ArrayList allRsfParticipatingBanks = null;
		try {
			Administrator admin = new Administrator();
			allRsfParticipatingBanks = admin.getAllRsfParticipatingBanks();
			admin = null;
		} catch (Exception e) {
			Log.log(2, "ApplicationProcessingAction",
					"getAllRsfParticipatingBanks", e.getMessage());
			Log.logException(e);
		}

		return allRsfParticipatingBanks;
	}

	private ArrayList getSocialCategory() {
		ArrayList socialCategoryList = null;
		try {
			Administrator admin = new Administrator();
			socialCategoryList = admin.getAllSocialCategories();
			admin = null;
		} catch (Exception e) {
			Log.log(2, "ApplicationProcessingAction", "getSocialCategoryList",
					e.getMessage());
			Log.logException(e);
		}

		return socialCategoryList;
	}

	private ArrayList getIndustryNature() {
		ArrayList industryNatureList = null;
		try {
			Administrator admin = new Administrator();
			industryNatureList = admin.getAllIndustryNature();
			admin = null;
		} catch (Exception e) {
			Log.log(2, "ApplicationProcessingAction", "getSocialCategoryList",
					e.getMessage());
			Log.logException(e);
		}

		return industryNatureList;
	}

	private ArrayList getIndustrySector() {
		ArrayList industrySectorList = null;
		try {
			Administrator admin = new Administrator();
			industrySectorList = admin.getIndustrySectors();
			admin = null;
		} catch (Exception e) {
			Log.log(2, "ApplicationProcessingAction", "getIndustrySector",
					e.getMessage());
			Log.logException(e);
		}

		return industrySectorList;
	}

	public ActionForward getDistricts(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(4, "ApplicationProcessingAction", "getDistricts", "Entered");

		Administrator admin = new Administrator();
		DynaActionForm dynaForm = (DynaActionForm) form;
		HttpSession session1 = request.getSession(false);
		String applicationType = (String) session1
				.getAttribute("APPLICATION_LOAN_TYPE");

		String stateName = (String) dynaForm.get("state");

		ArrayList districtList = admin.getAllDistricts(stateName);
		dynaForm.set("districtList", districtList);

		request.getSession().setAttribute("districtList", districtList);

		request.setAttribute("APPLICATION_TYPE_FLAG", "15");
		PrintWriter out = response.getWriter();
		String test = makeOutputString(districtList);
		out.print(test);
		String forward = setApps(applicationType);

		admin = null;
		districtList = null;
		applicationType = null;
		stateName = null;

		Log.log(4, "ApplicationProcessingAction", "getDistricts", "Exited");

		return mapping.findForward(forward);
	}

	public String makeOutputString(ArrayList districtList) {
		String outputString = "Select";
		for (int i = 0; i < districtList.size(); i++) {
			outputString = outputString + "||" + districtList.get(i);
		}

		return outputString;
	}

	public ActionForward getCityNames(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(4, "ApplicationProcessingAction", "getCityNames", "Entered");

		Administrator admin = new Administrator();
		DynaActionForm dynaForm = (DynaActionForm) form;
		HttpSession session1 = request.getSession(false);
		String applicationType = (String) session1
				.getAttribute("APPLICATION_LOAN_TYPE");

		String cityName = (String) dynaForm.get("city");

		ArrayList cityList = admin.getCityNames(cityName.toUpperCase());
		dynaForm.set("cityList", cityList);

		request.getSession().setAttribute("cityList", cityList);

		request.setAttribute("APPLICATION_TYPE_FLAG", "15");
		PrintWriter out = response.getWriter();
		String test = makeOutputString(cityList);
		out.print(test);
		String forward = setApps(applicationType);

		admin = null;
		cityList = null;
		applicationType = null;
		cityName = null;

		Log.log(4, "ApplicationProcessingAction", "getCityNames", "Exited");

		return mapping.findForward(forward);
	}

	public ActionForward getIndustrySector(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ApplicationProcessingAction", "getIndustrySector",
				"Entered");

		Administrator admin = new Administrator();
		DynaActionForm dynaForm = (DynaActionForm) form;
		HttpSession session1 = request.getSession(false);
		String applicationType = (String) session1
				.getAttribute("APPLICATION_LOAN_TYPE");

		ArrayList industrySectorList = new ArrayList();

		String industryNature = (String) dynaForm.get("industryNature");

		if (!industryNature.equals("")) {
			industrySectorList = admin.getIndustrySectors(industryNature);
		} else {
			industrySectorList.clear();
		}
		dynaForm.set("industrySectorList", industrySectorList);

		request.setAttribute("APPLICATION_TYPE_FLAG", "16");

		String forward = setApps(applicationType);

		admin = null;
		industryNature = null;

		applicationType = null;

		Log.log(4, "ApplicationProcessingAction", "getIndustrySector", "Exited");

		return mapping.findForward(forward);
	}

	public ActionForward getTypeOfActivity(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ApplicationProcessingAction", "getIndustrySector",
				"Entered");

		Administrator admin = new Administrator();
		DynaActionForm dynaForm = (DynaActionForm) form;
		HttpSession session1 = request.getSession(false);
		String applicationType = (String) session1
				.getAttribute("APPLICATION_LOAN_TYPE");

		ArrayList industrySectorList = new ArrayList();

		String industrySector = (String) dynaForm.get("industrySector");

		if (industrySector.equals("HANDLOOM WEAVING")) {
			dynaForm.set("activityType", industrySector);
		} else {
			dynaForm.set("activityType", "");
		}

		request.setAttribute("APPLICATION_TYPE_FLAG", "16");

		String forward = setApps(applicationType);

		admin = null;

		applicationType = null;

		Log.log(4, "ApplicationProcessingAction", "getIndustrySector", "Exited");

		return mapping.findForward(forward);
	}

	private String setApps(String applicationType) {
		String forward = null;

		if (applicationType.equals("TC")) {
			forward = "tcForward";
			this.application.setLoanType("TC");
		}
		if (applicationType.equals("ML")) {
			forward = "tcMudraForward";
			this.application.setLoanType("ML");
		}else if (applicationType.equals("CC")) {
			forward = "ccForward";
			this.application.setLoanType("CC");
		} else if (applicationType.equals("WC")) {
			forward = "wcForward";
			this.application.setLoanType("WC");
		} else if (applicationType.equals("BO")) {
			forward = "bothForward";
			this.application.setLoanType("BO");
		} else if (applicationType.equals("RSF")) {
			forward = "rsfForward";
			this.application.setLoanType("RSF");
		} else if (applicationType.equals("PCGS")) {
			forward = "pcgsForward";
			this.application.setLoanType("PCGS");
		}

		return forward;
	}
// DKR FB 
	public ActionForward showCgpanList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws NoDataException,Exception 
	//throws MessageException, IllegalAccessException, InvocationTargetException, DatabaseException, NoApplicationFoundException, NoMemberFoundException, NoDataException
	 {
		Log.log(4, "ApplicationProcessingAction", "showCgpanList", "Entered");
		DynaActionForm dynaForm = (DynaActionForm) form;
		String pageForward = "";
		String cgpan = "";
		String mliId = "";
		String cgbid = "";
		String Bid="";
				
		dynaForm.set("loanType", "TC");
		 try
	        {
		   //int inhanceFlag = 0;
			Administrator admin = new Administrator();
			ApplicationProcessor appProcessor = new ApplicationProcessor();
			ApplicationDAO applicationDAO = new ApplicationDAO();
			BorrowerDetails borrowerDetails = new BorrowerDetails();
			SSIDetails ssiDetails = new SSIDetails();
			Application application = new Application();
			MCGFDetails mcgfDetails = new MCGFDetails();
			
			mliId = (String) dynaForm.get("selectMember");
			dynaForm.set("mcgfId", mliId);
			Bid=mliId.substring(0, 4);
			//sayali------------			
			
		    double exposurelmtAmt = appProcessor.getExposuredetails(Bid,request);
			dynaForm.set("exposurelmtAmt", exposurelmtAmt);
			Log.log(5, "ApplicationProcessingAction", "showCgpanList", "exposure exposurelmtAmt :"
					+ exposurelmtAmt);
             //-----say
		
			BeanUtils.populate(ssiDetails, dynaForm.getMap());
			borrowerDetails.setSsiDetails(ssiDetails);
			BeanUtils.populate(borrowerDetails, dynaForm.getMap());
			application.setBorrowerDetails(borrowerDetails);
			BeanUtils.populate(application, dynaForm.getMap());

			HttpSession appSession = request.getSession(false);
			Log.log(4, "ApplicationProcessingAction", "getRenewWCInfo", "sessionuserid="+appSession.getAttribute("USER_ID")+""+appSession.getValueNames());
			String applicationType = (String) appSession
					.getAttribute("APPLICATION_TYPE");
			//System.out.println("application loan type :" + applicationType+ "S");
			if ((applicationType.equals("APP"))
					|| (applicationType.equals("EL"))
					|| (applicationType.equals("DUP"))) {
				dynaForm.initialize(mapping);
				application = new Application();
				borrowerDetails = new BorrowerDetails();
				ssiDetails = new SSIDetails();

				borrowerDetails.setSsiDetails(ssiDetails);
				application.setBorrowerDetails(borrowerDetails);
				mliId = null;
			}

			Log.log(5, "ApplicationProcessingAction", "showCgpanList",
					"mli id :" + dynaForm.get("selectMember"));
			Log.log(5, "ApplicationProcessingAction", "showCgpanList",
					"cgpan :" + application.getCgpan());
			Log.log(5, "ApplicationProcessingAction", "showCgpanList", "flag :"
					+ request.getParameter("flag"));

			Log.log(4, "ApplicationProcessingAction", "showCgpanList",
					"Entering when cgpan and ref no are null");

			if (((applicationType.equals("MA"))
					|| (applicationType.equals("TCE"))
					|| (applicationType.equals("WCE"))
					|| (applicationType.equals("WCR"))
					|| (applicationType.equals("APP"))
					|| (applicationType.equals("EL")) || (applicationType
					.equals("DUP")))
					&& (request.getParameter("flag").equals("0"))) {
				Log.log(4, "ApplicationProcessingAction", "showCgpanList",
						"Entering when flag is 0");
				cgpan = request.getParameter("cgpan");

				if (cgpan == null) {
					cgpan = "";
				}
				application.setCgpan(cgpan);
				application.setAppRefNo("");
			} else if (((applicationType.equals("MA"))
					|| (applicationType.equals("APP"))
					|| (applicationType.equals("EL")) || (applicationType
					.equals("DUP")))
					&& (request.getParameter("flag").equals("1"))) {
				Log.log(4, "ApplicationProcessingAction", "showCgpanList",
						"Entering when flag is 1");
				String appRefNo = request.getParameter("appRef");

				if (appRefNo == null) {
					appRefNo = "";
				}
				application.setAppRefNo(appRefNo);
				application.setCgpan("");
			}

			cgbid = "";
			if (application.getBorrowerDetails().getSsiDetails().getCgbid() == null) {
				cgbid = "";
			} else {
				cgbid = application.getBorrowerDetails().getSsiDetails()
						.getCgbid();
			}

			if ((application.getCgpan() != null)
					&& (!application.getCgpan().equals(""))) {
				cgpan = application.getCgpan();

				dynaForm.set("previouslyCovered", "Y");
				dynaForm.set("none", "cgpan");
				dynaForm.set("unitValue", cgpan);
			} else {
				dynaForm.set("unitValue", "");
			}

			String appRefNo = application.getAppRefNo();
			String borrowerName = "";
 
			if (application.getBorrowerDetails().getSsiDetails().getSsiName() == null) {
				borrowerName = "";
			} else {
				borrowerName = application.getBorrowerDetails().getSsiDetails()
						.getSsiName();
			}

		
			ClaimsProcessor cpProcessor = new ClaimsProcessor();
			if ((mliId != null) && (!mliId.equals(""))) {
				Vector memberIds = cpProcessor.getAllMemberIds();
				if (!memberIds.contains(mliId)) {
					throw new NoMemberFoundException(
							"The Member ID does not exist");
				}
			} else {
				mliId = null;
			}

			if ((cgbid != null) && (!cgbid.equals("")) && (mliId != null)
					&& (!mliId.equals(""))) {
				ArrayList borrowerIds = cpProcessor.getAllBorrowerIDs(mliId);
				if (!borrowerIds.contains(cgbid.toUpperCase())) {
					throw new NoDataException(
							"The Borrower ID does not exist for this Member ID");
				}
			}
          
		
			int type = 0;
			if (Integer.parseInt(request.getParameter("flag")) == 2) {
				String hybridUIflag="";
			// Added by DKR			
				HttpSession dsession = request.getSession(false);
				dsession.setAttribute("APPLICATION_LOAN_TYPE", "TCES");
			    dsession.setAttribute("gFinancialUIflag","DFALSEUI");
				dsession.setAttribute("dblockUI","");
				dsession.removeAttribute("gFinancialUIflag");
				dsession.removeAttribute("dblockUI");				
				
				
			  String collateralSenDate ="2017-12-18";                           // HDD CODE DATE 
	          //System.out.println("Action>>>>>>>>>collateralSenDate>---------------DKR----->>"+collateralSenDate);
				cgpan = (String) dynaForm.get("cgpan");
				//System.out.println("cgpan==DKR======"+cgpan);		
			    Date appSenDate = null;
			    
				if((!cgpan.equals(null)) || (!cgpan.equals(""))){										  
				  appSenDate = appProcessor.getSensionDatebyCGBID(cgpan);
				  Log.log(4, "ApplicationProcessingAction", "showCgpanList",
							"Before when flag is 2 appSentionDate "+appSenDate);
				  //System.out.println("appSenDate................"+appSenDate);		
	             }
				if((!appSenDate.equals(null)) || (!appSenDate.equals(""))){	
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");		     
			       // Date date2 = sdf.parse("2018-04-01");
					 Date date2 = sdf.parse(collateralSenDate); 
			        // HD
			        //System.out.println("date1 : " + sdf.format(appSenDate));
			        //System.out.println("date2 : " + sdf.format(date2));
			        if (appSenDate.compareTo(date2) > 0){
			        	hybridUIflag="DTRUE";			            
			        } else if (appSenDate.compareTo(date2) < 0) {
			        	hybridUIflag="FALSE";
			        	} else if (appSenDate.compareTo(date2) == 0) {
			        	hybridUIflag="DTRUE";			           
			        }   
					 dsession.setAttribute("hybridUIflag", hybridUIflag);
			    }					
				Log.log(5, "ApplicationProcessingAction", "showCgpanList","flag :" + request.getParameter("flag"));
				Log.log(5, "ApplicationProcessingAction", "showCgpanList","borrower name:" + borrowerName);
				if (((!mliId.equals("")) && (!cgbid.equals("")))
						|| ((!mliId.equals("")) && (!borrowerName.equals("")))) {
					Log.log(4, "ApplicationProcessingAction", "showCgpanList","Entering when mli di,borrower name and cgbid r entered");					
		//DKR Start
					
						//	}
					/*			if (applicationType.equals("WCE")) {
						type = 1;						 					
						// ++++++++++++++++++++++++++++++ Added by DKR 22082017  +++++++++++++++++++++++++++++++++
						//if (applicationType.equals("WCE")) {						
							  int inhanceFlag = 0;
								 String dCgbid = request.getParameter("cgbid");
								 String dCgpan = request.getParameter("cgpan");
								 String selectMember = request.getParameter("selectMember");
								 //System.out.println(selectMember+"............DDDDD............."+dCgbid+"........."+dCgpan);
								 if(dCgpan != null && !dCgpan.isEmpty() || dCgbid != null && !dCgbid.isEmpty()){
									 if((!dCgpan.equals(null)) || (!dCgpan.equals(""))){
										
										  inhanceFlag = appProcessor.validateCgpanOrBid(dCgpan, "CGPAN");
										 if(inhanceFlag == 1){
										  throw new MessageException("You've already requested for application inhancement with CGPAN : "+dCgpan+". Please wait for approval.");			 
										 }
									  }else{
										  if(dCgbid != null && !dCgbid.isEmpty()){
											  inhanceFlag = appProcessor.validateCgpanOrBid(dCgbid, "BID");
											 
											 if(inhanceFlag == 1){
											  throw new MessageException("You've already requested for application inhancement with MemberId : "+dCgbid+". Please wait for approval.");
											 }
										 }
									  }
								 }
								}				
		  //DKR	 END
					*/
					
						if (applicationType.equals("TCE")) {
						type = 0;
						borrowerName = "";
						//  added for FB start
						
						ClaimsProcessor claimProcessor = new ClaimsProcessor();

						if ((cgbid == null) || (cgbid.equals(""))) {
							cgbid = claimProcessor.getBorowwerForCGPAN(cgpan);
						}

						int claimCount = appProcessor.getClaimCount(cgbid);
						if (claimCount > 0) {
							throw new MessageException(
									"Application cannot be filed by this borrower since Claim Application has been submitted");
						}

						ArrayList appCgpansList = appProcessor.getCgpans(mliId,
								cgbid, type, borrowerName);
						Log.log(5, "ApplicationProcessingAction",
								"showCgpanList", "APPCgpanList:"
										+ appCgpansList);

						dynaForm.set("allCgpans", appCgpansList);
						Log.log(5, "ApplicationProcessingAction",
								"showCgpanList", "DynaForm after setting..:"
										+ dynaForm.get("allCgpans"));
					} else if (applicationType.equals("WCE")) {
						// //System.out.println(selectMember+"<<<<<<selectMember......dCgpan>>>>>>>"+dCgpan+"........dCgbid>>>"+dCgbid);
							
						type = 1;
						borrowerName = "";   						
						ClaimsProcessor claimProcessor = new ClaimsProcessor();
						// ++++++++++++++++++++++++++++++ Added by DKR 22082017  +++++++++++++++++++++++++++++++++
						//if (applicationType.equals("WCE")) {						
						/*	  int inhanceFlag = 0;
								 String dCgbid = request.getParameter("cgbid");
								 String dCgpan = request.getParameter("cgpan");
								 String selectMember = request.getParameter("selectMember");
								 //System.out.println(selectMember+"<<<<<<selectMember......dCgpan>>>>>>>"+dCgpan+"........dCgbid>>>"+dCgbid);
								 if(dCgpan != null && !dCgpan.isEmpty() || dCgbid != null && !dCgbid.isEmpty()){
									 if((!dCgpan.equals(null)) || (!dCgpan.equals(""))){
										
										  inhanceFlag = appProcessor.validateCgpanOrBid(dCgpan, "CGPAN");
										 if(inhanceFlag == 1){
										  throw new MessageException("You've already requested for application inhancement with CGPAN : "+dCgpan+". Please wait for approval.");			 
										 }
									  }else{
										  if(dCgbid != null && !dCgbid.isEmpty()){
											  inhanceFlag = appProcessor.validateCgpanOrBid(dCgbid, "BID");
											 
											 if(inhanceFlag == 1){
											  throw new MessageException("You've already requested for application inhancement with MemberId : "+dCgbid+". Please wait for approval.");
											 }
										 }
									  }
								 }*/
						//}
						// ====================== End  22082017 ==========================================================	

						if ((cgbid == null) || (cgbid.equals(""))) {
							cgbid = claimProcessor.getBorowwerForCGPAN(cgpan);
						}

						int claimCount = appProcessor.getClaimCount(cgbid);
						if (claimCount > 0) {
							throw new MessageException(
									"Application cannot be filed by this borrower since Claim Application has been submitted");
						}

						ArrayList appCgpansList = appProcessor.getCgpans(mliId,
								cgbid, type, borrowerName);
						Log.log(5, "ApplicationProcessingAction",
								"showCgpanList", "APPCgpanList:"
										+ appCgpansList);

						dynaForm.set("allCgpans", appCgpansList);
						Log.log(5, "ApplicationProcessingAction",
								"showCgpanList", "DynaForm after setting..:"
										+ dynaForm.get("allCgpans"));
						
						
						
					} else if (applicationType.equals("WCR")) {
						type = 2;
						borrowerName = "";

						ClaimsProcessor claimProcessor = new ClaimsProcessor();

						if ((cgbid == null) || (cgbid.equals(""))) {
							cgbid = claimProcessor.getBorowwerForCGPAN(cgpan);
						}

						int claimCount = appProcessor.getClaimCount(cgbid);
						if (claimCount > 0) {
							throw new MessageException(
									"Application cannot be filed by this borrower since Claim Application has been submitted");
						}

						ArrayList appCgpansList = appProcessor.getCgpans(mliId,
								cgbid, type, borrowerName);
						Log.log(5, "ApplicationProcessingAction",
								"showCgpanList", "APPCgpanList:"
										+ appCgpansList);

						dynaForm.set("allCgpans", appCgpansList);
						Log.log(5, "ApplicationProcessingAction",
								"showCgpanList", "DynaForm after setting..:"
										+ dynaForm.get("allCgpans"));
					} else if (applicationType.equals("MA")) {
						ClaimsProcessor claimProcessor = new ClaimsProcessor();

						if (((cgbid == null) || (cgbid.equals("")))
								&& ((borrowerName == null) || (borrowerName
										.equals("")))
								&& ((appRefNo == null) || (borrowerName
										.equals("")))
								&& ((cgpan != null) || (!cgpan.equals("")))) {
							cgbid = claimProcessor.getBorowwerForCGPAN(cgpan);
						}

						if ((cgbid != null) && (!cgbid.equals(""))) {
							int claimCount = appProcessor.getClaimCount(cgbid);
							if (claimCount > 0) {
								throw new MessageException(
										"Application cannot be filed by this borrower since Claim Application has been submitted");
							}

						}

						ArrayList cgpanAppRefNoList = appProcessor
								.getAppRefNos(mliId, cgbid, borrowerName);

						ArrayList appRefNosList = (ArrayList) cgpanAppRefNoList
								.get(0);
						ArrayList appCgpanList = (ArrayList) cgpanAppRefNoList
								.get(1);					
						
						
						

						if ((appRefNosList.size() == 0)
								&& (appCgpanList.size() == 0)) {
							throw new MessageException(
									"There are no Application Reference Numbers or CGPANs for modification");
						}

						Log.log(5, "ApplicationProcessingAction",
								"showCgpanList", "AppRefNos Array List :"
										+ appRefNosList);

						dynaForm.set("allAppRefNos", appRefNosList);
						Log.log(5, "ApplicationProcessingAction",
								"showCgpanList", "DynaForm after setting..:"
										+ dynaForm.get("allAppRefNos"));

						dynaForm.set("allCgpans", appCgpanList);
						Log.log(5, "ApplicationProcessingAction",
								"showCgpanList", "DynaForm after setting..:"
										+ dynaForm.get("allCgpans"));
					}
						
					pageForward = "listofcgpans";
				}			 
				dsession.setAttribute("financialUIflag", "N");                                    // 35 added
			}

			if (((mliId != null) && (!mliId.equals("")))
					|| ((!appRefNo.equals("")) || (((mliId != null) && (!mliId
							.equals(""))) || ((!cgpan.equals(""))
							|| ((!mliId.equals("")) && (!cgpan.equals(""))) || ((!mliId
							.equals("")) && (!appRefNo.equals(""))))))) {
				HttpSession appSession1 = request.getSession(false);
				String applicationTypes = (String) appSession1
						.getAttribute("APPLICATION_TYPE");

				if (!appRefNo.equals("")) {
					appProcessor.checkCgpanPool(appRefNo);
				} else if ((applicationTypes.equals("WCR"))
						&& (!cgpan.equals(""))) {
					if (cgpan.substring(11, 13).equals("TC")) {
						throw new MessageException(
								"Not a valid Application for Working capital Renewal");
					}
		        
					String renewcgpan = appProcessor.checkRenewCgpan(cgpan);
					if (renewcgpan.equals("0")) {
						dynaForm.set("unitValue", cgpan);
					} else {
						cgpan = renewcgpan;
					}

					if (cgpan.substring(11, 13).equals("R9")) {
						throw new MessageException(
								"This application cannot be renewed further");
					}

					dynaForm.set("none", "cgpan");
					dynaForm.set("unitValue", cgpan);
				}

				if (!cgpan.equals("")) {
					application = appProcessor.getAppForCgpan(mliId, cgpan);
					appRefNo = application.getAppRefNo();
					appProcessor.checkCgpanPool(appRefNo);

					RpDAO rpDAO = new RpDAO();
					Date guarStartDate = rpDAO.getGuarStartDate(application);

					if (applicationType.equals("WCE")) {    
			
						if ((guarStartDate == null)
								|| (guarStartDate.equals(""))) {
							throw new NoDataException(
									"Guarantee has not started for this "
											+ cgpan);
						}
						int paymentFlag = applicationDAO
								.getPaymentStatus(cgpan);
						if (paymentFlag > 0) {
							throw new DatabaseException(
									"Guarantee Fee for this account has not been received");
						}
				
					}

				}

				Log.log(4, "ApplicationProcessingAction", "showCgpanList",
						"Entering when cgpan and app ref no are entered");
				application = appProcessor.getApplication(mliId, cgpan, appRefNo);

				mliId = application.getMliID();
				String bankId = mliId.substring(0, 4);
				String zoneId = mliId.substring(4, 8);
				String branchId = mliId.substring(8, 12);
				Registration registration = new Registration();
				MLIInfo mliInfo = registration.getMemberDetails(bankId, zoneId,
						branchId);

				Log.log(4, "ApplicationProcessingAction", "showCgpanList",
						"application types :" + applicationTypes);

				String applicationLoanType = application.getLoanType();

				Log.log(4, "ApplicationProcessingAction", "showCgpanList",
						"Setting the values in copy Properties....");

				ssiDetails = application.getBorrowerDetails().getSsiDetails();
				BeanUtils.copyProperties(dynaForm, ssiDetails);

				borrowerDetails = application.getBorrowerDetails();
				BeanUtils.copyProperties(dynaForm, borrowerDetails);
				Log.log(4, "ApplicationProcessingAction", "showCgpanList",
						"Set Borrower Details...");

				PrimarySecurityDetails primarySecurityDetails = application
						.getProjectOutlayDetails().getPrimarySecurityDetails();
				BeanUtils.copyProperties(dynaForm, primarySecurityDetails);
				double landValue = ((Double) dynaForm.get("landValue"))
						.doubleValue();
				double bldgValue = ((Double) dynaForm.get("bldgValue"))
						.doubleValue();
				double machineValue = ((Double) dynaForm.get("machineValue"))
						.doubleValue();
				double assetsValue = ((Double) dynaForm.get("assetsValue"))
						.doubleValue();
				double currentAssetsValue = ((Double) dynaForm
						.get("currentAssetsValue")).doubleValue();
				double othersValue = ((Double) dynaForm.get("othersValue"))
						.doubleValue();
				double psTotalValue = landValue + bldgValue + machineValue
						+ assetsValue + currentAssetsValue + othersValue;
				Double intPsTotal = new Double(psTotalValue);
				dynaForm.set("psTotal", intPsTotal);
				Log.log(4, "ApplicationProcessingAction", "showCgpanList",
						"Set Primary Security Details...");

				ProjectOutlayDetails projectOutlayDetails = application
						.getProjectOutlayDetails();
				BeanUtils.copyProperties(dynaForm, projectOutlayDetails);

				/* Changes for GST */
				List<MLIInfo> branchStateList = registration
						.getGSTStateList(bankId);
				dynaForm.set("branchStateList", branchStateList);
				request.setAttribute("branchStateList", branchStateList);

				Log.log(4, "ApplicationProcessingAction", "showCgpanList",
						"Set Project Outlay Details...");

				if (!applicationTypes.equals("TCE")) {
					TermLoan termLoan = application.getTermLoan();
					BeanUtils.copyProperties(dynaForm, termLoan);
					Log.log(4, "ApplicationProcessingAction", "showCgpanList",
							"Set Term Loan Details...");
					double termCreditSanctioned = ((Double) dynaForm
							.get("termCreditSanctioned")).doubleValue();
					Double termCreditSanctionedVal = new Double(
							termCreditSanctioned);
					double tcPromoterContribution = ((Double) dynaForm
							.get("tcPromoterContribution")).doubleValue();
					double tcSubsidyOrEquity = ((Double) dynaForm
							.get("tcSubsidyOrEquity")).doubleValue();
					double tcOthers = ((Double) dynaForm.get("tcOthers"))
							.doubleValue();
					double projectCost = termCreditSanctioned
							+ tcPromoterContribution + tcSubsidyOrEquity
							+ tcOthers;
					Double projectCostIntVal = new Double(projectCost);
					dynaForm.set("projectCost", projectCostIntVal);
					Log.log(4, "ApplicationProcessingAction", "showCgpanList",
							"project Cost:" + projectCostIntVal);
					Log.log(4,
							"ApplicationProcessingAction",
							"showCgpanList",
							"project cost in the form:"
									+ dynaForm.get("projectCost"));

					dynaForm.set("amountSanctioned", termCreditSanctionedVal);

					WorkingCapital workingCapital = application.getWc();
					BeanUtils.copyProperties(dynaForm, workingCapital);
					double wcFundBasedSanctioned = ((Double) dynaForm
							.get("wcFundBasedSanctioned")).doubleValue();
					double wcNonFundBasedSanctioned = ((Double) dynaForm
							.get("wcNonFundBasedSanctioned")).doubleValue();

					Double existingTotalFBVal = new Double(
							wcFundBasedSanctioned);
					Double existingTotalNFBVal = new Double(
							wcNonFundBasedSanctioned);
					dynaForm.set("existingFundBasedTotal", existingTotalFBVal);
					dynaForm.set("existingNonFundBasedTotal",
							existingTotalNFBVal);

					double wcPromoterContribution = ((Double) dynaForm
							.get("wcPromoterContribution")).doubleValue();
					double wcSubsidyOrEquity = ((Double) dynaForm
							.get("wcSubsidyOrEquity")).doubleValue();
					double wcOthers = ((Double) dynaForm.get("wcOthers"))
							.doubleValue();

					double wcAssessed = wcFundBasedSanctioned
							+ wcNonFundBasedSanctioned + wcPromoterContribution
							+ wcSubsidyOrEquity + wcOthers;
					Double wcAssessedIntVal = new Double(wcAssessed);
					dynaForm.set("wcAssessed", wcAssessedIntVal);
					Log.log(4, "ApplicationProcessingAction", "showCgpanList",
							"Set Working Capital Details...");

					double projectCostValue = ((Double) dynaForm
							.get("projectCost")).doubleValue();
					double wcAssessedValue = ((Double) dynaForm
							.get("wcAssessed")).doubleValue();
					double projectOutlayValueCost = projectCostValue
							+ wcAssessedValue;
					Double projectOutlayVal = new Double(projectOutlayValueCost);
					dynaForm.set("projectOutlay", projectOutlayVal);
				} else {
					double value = 0.0D;
					Double intVal = new Double(value);
					dynaForm.set("termCreditSanctioned", intVal);
					dynaForm.set("tcPromoterContribution", intVal);
					dynaForm.set("tcSubsidyOrEquity", intVal);
					dynaForm.set("tcOthers", intVal);
					dynaForm.set("projectCost", intVal);
					dynaForm.set("wcFundBasedSanctioned", intVal);
					dynaForm.set("wcNonFundBasedSanctioned", intVal);
					dynaForm.set("wcSubsidyOrEquity", intVal);
					dynaForm.set("wcOthers", intVal);
					dynaForm.set("wcAssessed", intVal);
					dynaForm.set("projectOutlay", intVal);
				}

				Securitization securitization = application.getSecuritization();
				BeanUtils.copyProperties(dynaForm, securitization);
				Log.log(4, "ApplicationProcessingAction", "showCgpanList",
						"Set Securitization Details...");

				String mcgfFlag = mliInfo.getSupportMCGF();
				Log.log(4, "ApplicationProcessingAction", "showCgpanList",
						"MCGF Flag" + mcgfFlag);
				if (mcgfFlag.equals("Y")) {
					appSession1.setAttribute("MCGF_FLAG", "M");

					mcgfDetails = application.getMCGFDetails();
					Log.log(4,
							"ApplicationProcessingAction",
							"showCgpanList",
							"mcgf App ref no:"
									+ mcgfDetails
											.getApplicationReferenceNumber());
					Log.log(4,
							"ApplicationProcessingAction",
							"showCgpanList",
							"mcgf approved Amount:"
									+ mcgfDetails.getMcgfApprovedAmt());
					Log.log(4, "ApplicationProcessingAction", "showCgpanList",
							"mcgf district:" + mcgfDetails.getMcgfDistrict());
					Log.log(4, "ApplicationProcessingAction", "showCgpanList",
							"mcgf bank:" + mcgfDetails.getParticipatingBank());
					Log.log(4,
							"ApplicationProcessingAction",
							"showCgpanList",
							"mcgf branch :"
									+ mcgfDetails.getParticipatingBankBranch());
					Log.log(4, "ApplicationProcessingAction", "showCgpanList",
							"mcgf name :" + mcgfDetails.getMcgfName());
					Log.log(4, "ApplicationProcessingAction", "showCgpanList",
							"mcgf Id:" + mcgfDetails.getMcgfId());

					mcgfDetails.setMcgfId(mliId);
					application.setMCGFDetails(mcgfDetails);
					BeanUtils.copyProperties(dynaForm, mcgfDetails);
					Log.log(4, "ApplicationProcessingAction", "showCgpanList",
							"Set MCGF Details...");
				} else {
					Log.log(4, "ApplicationProcessingAction", "showCgpanList",
							"Not under MCGF");

					appSession1.setAttribute("MCGF_FLAG", "NM");
				}

				BeanUtils.copyProperties(dynaForm, application);

				Log.log(4, "ApplicationProcessingAction", "showCgpanList",
						"Set Application Details...");

				String constitutionVal = ssiDetails.getConstitution();

				if ((!constitutionVal.equals("proprietary"))
						&& (!constitutionVal.equals("partnership"))
						&& (!constitutionVal.equals("private"))
						&& (!constitutionVal.equals("public"))) {
					dynaForm.set("constitutionOther", constitutionVal);
					dynaForm.set("constitution", "Others");
				} else {
					dynaForm.set("constitution", constitutionVal);
				}
				Log.log(4, "ApplicationProcessingAction", "showCgpanList",
						"Set Application Details...");

				String legalIDString = ssiDetails.getCpLegalID();
				if ((legalIDString != null) && (!legalIDString.equals(""))) {
					if ((!legalIDString.equals("VoterIdentityCard"))
							&& (!legalIDString.equals("RationCardnumber"))
							&& (!legalIDString.equals("PASSPORT"))
							&& (!legalIDString.equals("Driving License"))) {
						dynaForm.set("otherCpLegalID", legalIDString);
						dynaForm.set("cpLegalID", "Others");
					} else {
						dynaForm.set("cpLegalID", legalIDString);
					}
				}

				Log.log(4, "ApplicationProcessingAction", "showCgpanList",
						"Set Application Details...");

				String subsidyEquityName = projectOutlayDetails
						.getSubsidyName();
				Log.log(4,
						"ApplicationProcessingAction",
						"showCgpanList",
						"Subsidy Name :"
								+ projectOutlayDetails.getSubsidyName());
				if ((subsidyEquityName != null)
						&& (!subsidyEquityName.equals(""))) {
					if ((!subsidyEquityName.equals("PMRY"))
							&& (!subsidyEquityName.equals("SJRY"))) {
						dynaForm.set("otherSubsidyEquityName",
								subsidyEquityName);
						dynaForm.set("subsidyName", "Others");
					} else {
						dynaForm.set("subsidyName", subsidyEquityName);
					}
				}

				Log.log(4, "ApplicationProcessingAction", "showCgpanList",
						"Set Application Details...");

				ArrayList statesList = (ArrayList) getStateList();
				dynaForm.set("statesList", statesList);

				String state = (String) dynaForm.get("state");

				dynaForm.set("state", ssiDetails.getState());
				ArrayList districtList = admin.getAllDistricts(state);
				dynaForm.set("districtList", districtList);

				String districtName = ssiDetails.getDistrict();

				if (districtList.contains(districtName)) {
					dynaForm.set("district", districtName);
				} else {
					dynaForm.set("districtOthers", districtName);
					dynaForm.set("district", "Others");
				}

				Log.log(4, "ApplicationProcessingAction", "showCgpanList",
						"Set Application Details...");

				ArrayList socialList = getSocialCategory();
				dynaForm.set("socialCategoryList", socialList);

				dynaForm.set("industryNature", ssiDetails.getIndustryNature());
				dynaForm.set("industrySector", ssiDetails.getIndustrySector());

				MCGSProcessor mcgsProcessor = new MCGSProcessor();
				ArrayList participatingBanks = mcgsProcessor
						.getAllParticipatingBanks(mliId);
				dynaForm.set("participatingBanks", participatingBanks);

				if (applicationTypes.equals("TCE")) {
					if (application.getBorrowerDetails().getPreviouslyCovered()
							.equals("Y")) {
						if ((application.getCgpanReference() != null)
								&& (!application.getCgpanReference().equals(""))) {
							String cgpanRef = application.getCgpanReference();
							dynaForm.set("unitValue", cgpanRef);
							dynaForm.set("none", "cgpan");
						}
					}

					dynaForm.set("previouslyCovered", "Y");

					if (application.getLoanType().equals("WC")) {
						throw new MessageException(
								"Not a valid Application for Additional Term Loan");
					}

					ClaimsProcessor claimProcessor = new ClaimsProcessor();

					if (((cgbid == null) || (cgbid.equals("")))
							&& (cgpan != null) && (!cgpan.equals(""))) {
						cgbid = claimProcessor.getBorowwerForCGPAN(cgpan);
					}

					int claimCount = appProcessor.getClaimCount(cgbid);
					if (claimCount > 0) {
						throw new MessageException(
								"Application cannot be filed by this borrower since Claim Application has been submitted");
					}

					Log.log(4, "ApplicationProcessingAction", "showCgpanList",
							"Additional Term Loan");
					if ((application.getStatus().equals("EX"))
							|| (application.getStatus().equals("AP"))) {
						appSession1.setAttribute("APPLICATION_TYPE_FLAG", "0");
					} else {
						throw new MessageException(
								"Not a valid Application for Additional Term Loan");
					}
					pageForward = "AddtlTermLoanPage";
				} else if (applicationTypes.equals("WCE")) {					
					String remarks = application.getRemarks();
					application.setExistingRemarks(remarks);

					double balanceAppAmt = appProcessor
							.getBalanceApprovedAmt(application);

					Log.log(4, "ApplicationProcessingAction", "showCgpanList",
							"balanceAppAmt :" + balanceAppAmt);

					dynaForm.set("remarks", "");
					dynaForm.set("existingRemarks",
							application.getExistingRemarks());

					dynaForm.set("unitValue", cgpan);
					dynaForm.set("none", "cgpan");
					dynaForm.set("previouslyCovered", "Y");

					double wcFundBasedSanctioned = ((Double) dynaForm
							.get("wcFundBasedSanctioned")).doubleValue();
					Double wcFundBasedSanctionedVal = new Double(
							wcFundBasedSanctioned);
					double wcNonFundBasedSanctioned = ((Double) dynaForm
							.get("wcNonFundBasedSanctioned")).doubleValue();

					Double existingTotalFBVal = new Double(
							wcFundBasedSanctioned);
					Double existingTotalNFBVal = new Double(
							wcNonFundBasedSanctioned);
					dynaForm.set("existingFundBasedTotal", existingTotalFBVal);
					dynaForm.set("existingNonFundBasedTotal",
							existingTotalNFBVal);

					if ((application.getLoanType().equals("TC"))
							|| (application.getLoanType().equals("CC"))) {
						throw new MessageException(
								"Not a valid Application for Enhancement of Working Capital");
					}
					ClaimsProcessor claimProcessor = new ClaimsProcessor();
					if (((cgbid == null) || (cgbid.equals("")))
							&& (cgpan != null) && (!cgpan.equals(""))) {
						cgbid = claimProcessor.getBorowwerForCGPAN(cgpan);
					}
            
					int claimCount = appProcessor.getClaimCount(cgbid);
					if (claimCount > 0) {
						throw new MessageException(
								"Application cannot be filed by this borrower since Claim Application has been submitted");
					}

					if (application.getStatus().equals("EX")) {
						throw new MessageException(
								"This application has expired");
					}

					appSession1.setAttribute("APPLICATION_TYPE_FLAG", "1");
					pageForward = "EnhancementPage";
				} else if (applicationTypes.equals("WCR")) {
					String remarks = application.getRemarks();
					application.setExistingRemarks(remarks);

					dynaForm.set("remarks", "");
					dynaForm.set("existingRemarks",
							application.getExistingRemarks());

					dynaForm.set("none", "cgpan");
					dynaForm.set("unitValue", cgpan);
					dynaForm.set("previouslyCovered", "Y");

					double wcFundBasedSanctioned = ((Double) dynaForm
							.get("wcFundBasedSanctioned")).doubleValue();
					Double wcFundBasedSanctionedVal = new Double(
							wcFundBasedSanctioned);
					double wcNonFundBasedSanctioned = ((Double) dynaForm
							.get("wcNonFundBasedSanctioned")).doubleValue();

					Double existingTotalFBVal = new Double(
							wcFundBasedSanctioned);
					Double existingTotalNFBVal = new Double(
							wcNonFundBasedSanctioned);
					dynaForm.set("existingFundBasedTotal", existingTotalFBVal);
					dynaForm.set("existingNonFundBasedTotal",
							existingTotalNFBVal);

					ClaimsProcessor claimProcessor = new ClaimsProcessor();

					if (((cgbid == null) || (cgbid.equals("")))
							&& (cgpan != null) && (!cgpan.equals(""))) {
						cgbid = claimProcessor.getBorowwerForCGPAN(cgpan);
					}

					int claimCount = appProcessor.getClaimCount(cgbid);
					if (claimCount > 0) {
						throw new MessageException(
								"Application cannot be filed by this borrower since Claim Application has been submitted");
					}

					if (!application.getStatus().equals("EX")) {
						throw new MessageException(
								"This application has not expired");
					}
					if (application.getStatus().equals("EX")) {
						appSession1.setAttribute("APPLICATION_TYPE_FLAG", "2");
						pageForward = "RenewalPage";
					}
				} else if (applicationTypes.equals("MA")) {
					String remarks = application.getRemarks();
					application.setExistingRemarks(remarks);

					dynaForm.set("remarks", "");
					dynaForm.set("existingRemarks",
							application.getExistingRemarks());

					ClaimsProcessor claimProcessor = new ClaimsProcessor();

					if ((cgpan != null) && (!cgpan.equals(""))) {
						cgbid = claimProcessor.getBorowwerForCGPAN(cgpan);
					} else if ((appRefNo != null) && (!appRefNo.equals(""))) {
						Application tempApp = appProcessor.getPartApplication(
								mliId, "", appRefNo);
						cgpan = tempApp.getCgpan();

						if ((cgpan != null) && (!cgpan.equals(""))) {
							cgbid = claimProcessor.getBorowwerForCGPAN(cgpan);
						}

					}

					if ((cgbid != null) && (!cgbid.equals(""))) {
						int claimCount = appProcessor.getClaimCount(cgbid);
						if (claimCount > 0) {
							throw new MessageException(
									"Application cannot be filed by this borrower since Claim Application has been submitted");
						}

					}

					if ((cgpan != null) && (!cgpan.equals(""))) {
						dynaForm.set("unitValue", cgpan);
						dynaForm.set("none", "cgpan");
						dynaForm.set("previouslyCovered", "Y");
					}

					Log.log(4, "ApplicationProcessingAction", "showCgpanList",
							"Loan Type :" + applicationTypes);

					if ((application.getStatus().equals("PE"))
							|| (application.getStatus().equals("EP"))) {
						throw new MessageException(
								"This application cannot be modified since decision to be taken by CGTSI is pending");
					}

					if ((application.getStatus().equals("EX"))
							|| (application.getStatus().equals("RE"))) {
						throw new MessageException(
								"This application cannot be modified since the application is not live");
					}

					if (applicationLoanType.equals("TC")) {
						appSession1.setAttribute("APPLICATION_TYPE_FLAG", "3");
						pageForward = "tcForward";
					}

					if (applicationLoanType.equals("WC")) {
						appSession1.setAttribute("APPLICATION_TYPE_FLAG", "4");
						pageForward = "wcForward";
					}

					if (applicationLoanType.equals("BO")) {
						appSession1.setAttribute("APPLICATION_TYPE_FLAG", "5");
						pageForward = "bothForward";
					}

					if (applicationLoanType.equals("CC")) {
						appSession1.setAttribute("APPLICATION_TYPE_FLAG", "6");
						pageForward = "ccForward";
					}

				} else if ((applicationTypes.equals("APP"))
						|| (applicationTypes.equals("REAPP"))
						|| (applicationTypes.equals("EL"))
						|| (applicationTypes.equals("DUP"))) {
					if ((cgpan != null) && (!cgpan.equals(""))) {
						dynaForm.set("unitValue", cgpan);
						dynaForm.set("none", "cgpan");
						dynaForm.set("previouslyCovered", "Y");
					}

					Log.log(4, "ApplicationProcessingAction", "showCgpanList",
							"Loan Type :" + applicationTypes);

					if (applicationLoanType.equals("TC")) {
						appSession1.setAttribute("APPLICATION_TYPE_FLAG", "11");
						Log.log(4,
								"ApplicationProcessingAction",
								"showCgpanList",
								"Session Attribute :"
										+ appSession1
												.getAttribute("APPLICATION_TYPE_FLAG"));
						pageForward = "tcForward";
					}

					if (applicationLoanType.equals("WC")) {
						appSession1.setAttribute("APPLICATION_TYPE_FLAG", "12");

						pageForward = "wcForward";
					}

					if (applicationLoanType.equals("CC")) {
						appSession1.setAttribute("APPLICATION_TYPE_FLAG", "13");
						pageForward = "ccForward";
					}
				}
			}

			Log.log(5, "ApplicationProcessingAction", "showCgpanList",
					"Page to be forwaded :" + pageForward);
			Log.log(4, "ApplicationProcessingAction", "showCgpanList", "Exited");

			cgbid = null;
			cgpan = null;

			dynaForm.set("dcHandlooms", application.getDcHandlooms());
			dynaForm.set("WeaverCreditScheme",
					application.getWeaverCreditScheme());
			dynaForm.set("handloomchk", application.getHandloomchk());
	        }
	        catch(Exception e)
	        {
	            LogClass.writeExceptionOnFile(e);
	            throw new NoDataException(e.getMessage());	
	        }
		
		return mapping.findForward(pageForward);
	}

	public ActionForward showCgpanListNew(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ApplicationProcessingAction", "showCgpanList", "Entered");

		DynaActionForm dynaForm = (DynaActionForm) form;

		Administrator admin = new Administrator();
		ApplicationProcessor appProcessor = new ApplicationProcessor();
		BorrowerDetails borrowerDetails = new BorrowerDetails();
		SSIDetails ssiDetails = new SSIDetails();
		Application application = new Application();
		MCGFDetails mcgfDetails = new MCGFDetails();

		String cgpan = "";
		String mliId = "";
		String cgbid = "";

		BeanUtils.populate(ssiDetails, dynaForm.getMap());

		borrowerDetails.setSsiDetails(ssiDetails);
		BeanUtils.populate(borrowerDetails, dynaForm.getMap());
		application.setBorrowerDetails(borrowerDetails);

		BeanUtils.populate(application, dynaForm.getMap());

		HttpSession appSession = request.getSession(false);

		String applicationType = (String) appSession
				.getAttribute("APPLICATION_TYPE");

		Log.log(5, "ApplicationProcessingAction", "showCgpanList", "mli id :"
				+ dynaForm.get("selectMember"));
		Log.log(5, "ApplicationProcessingAction", "showCgpanList", "cgpan :"
				+ application.getCgpan());
		Log.log(5, "ApplicationProcessingAction", "showCgpanList", "flag :"
				+ request.getParameter("flag"));
		Log.log(4, "ApplicationProcessingAction", "showCgpanList",
				"Entering when cgpan and ref no are null");

		if (((applicationType.equals("MA")) || (applicationType.equals("APP"))
				|| (applicationType.equals("EL")) || (applicationType
				.equals("DUP"))) && (request.getParameter("flag").equals("1"))) {
			Log.log(4, "ApplicationProcessingAction", "showCgpanList",
					"Entering when flag is 1");
			String appRefNo = request.getParameter("appRef");
			if (appRefNo == null) {
				appRefNo = "";
			}
			application.setAppRefNo(appRefNo);
			application.setCgpan("");
		}

		cgbid = "";
		if (application.getBorrowerDetails().getSsiDetails().getCgbid() == null) {
			cgbid = "";
		} else {
			cgbid = application.getBorrowerDetails().getSsiDetails().getCgbid();
		}

		if ((application.getCgpan() != null)
				&& (!application.getCgpan().equals(""))) {
			cgpan = application.getCgpan();

			dynaForm.set("previouslyCovered", "Y");
			dynaForm.set("none", "cgpan");
			dynaForm.set("unitValue", cgpan);
		} else {
			dynaForm.set("unitValue", "");
		}

		String appRefNo = application.getAppRefNo();

		String borrowerName = "";

		if (application.getBorrowerDetails().getSsiDetails().getSsiName() == null) {
			borrowerName = "";
		} else {
			borrowerName = application.getBorrowerDetails().getSsiDetails()
					.getSsiName();
		}

		mliId = (String) dynaForm.get("selectMember");
		dynaForm.set("mcgfId", mliId);

		ClaimsProcessor cpProcessor = new ClaimsProcessor();
		if ((mliId != null) && (!mliId.equals(""))) {
			Vector memberIds = cpProcessor.getAllMemberIds();
			if (!memberIds.contains(mliId)) {
				throw new NoMemberFoundException("The Member ID does not exist");
			}
		} else {
			mliId = null;
		}

		if ((cgbid != null) && (!cgbid.equals("")) && (mliId != null)
				&& (!mliId.equals(""))) {
			ArrayList borrowerIds = cpProcessor.getAllBorrowerIDs(mliId);
			if (!borrowerIds.contains(cgbid.toUpperCase())) {
				throw new NoDataException(
						"The Borrower ID does not exist for this Member ID");
			}
		}

		int type = 0;
		String pageForward = "";

		if (((mliId != null) && (!mliId.equals("")))
				|| ((!appRefNo.equals("")) || (((mliId != null) && (!mliId
						.equals(""))) || ((!cgpan.equals(""))
						|| ((!mliId.equals("")) && (!cgpan.equals(""))) || ((!mliId
						.equals("")) && (!appRefNo.equals(""))))))) {
			HttpSession appSession1 = request.getSession(false);
			String applicationTypes = (String) appSession1
					.getAttribute("APPLICATION_TYPE");

			if (!appRefNo.equals("")) {
				appProcessor.checkCgpanPool(appRefNo);
			} else if ((applicationTypes.equals("WCR")) && (!cgpan.equals(""))) {
				if (cgpan.substring(11, 13).equals("TC")) {
					throw new MessageException(
							"Not a valid Application for Working capital Renewal");
				}
				String renewcgpan = appProcessor.checkRenewCgpan(cgpan);
				if (renewcgpan.equals("0")) {
					dynaForm.set("unitValue", cgpan);
				} else {
					cgpan = renewcgpan;
				}

				if (cgpan.substring(11, 13).equals("R9")) {
					throw new MessageException(
							"This application cannot be renewed further");
				}

				dynaForm.set("none", "cgpan");
				dynaForm.set("unitValue", cgpan);
			}

			Log.log(4, "ApplicationProcessingAction", "showCgpanList",
					"Entering when cgpan and app ref no are entered");

			application = appProcessor.getApplication(mliId, cgpan, appRefNo);

			mliId = application.getMliID();
			String bankId = mliId.substring(0, 4);
			String zoneId = mliId.substring(4, 8);
			String branchId = mliId.substring(8, 12);

			Registration registration = new Registration();
			MLIInfo mliInfo = registration.getMemberDetails(bankId, zoneId,
					branchId);

			if ((!application.getStatus().equals("RE")) || (!cgpan.equals(""))) {
				throw new MessageException(
						"This application cannot be modified since the application is not a rejected application");
			}
			if (application.getRemarks() != null) {
				if ((application.getRemarks().equals("INELIGIBLE ACTIVITY"))
						|| (application.getRemarks()
								.equals("Ineligible activity"))) {
					throw new MessageException(
							"This application cannot be modified as the activity is ineligible");
				}

			}

			Log.log(4, "ApplicationProcessingAction", "showCgpanList",
					"application types :" + applicationTypes);

			String applicationLoanType = application.getLoanType();

			Log.log(4, "ApplicationProcessingAction", "showCgpanList",
					"Setting the values in copy Properties....");

			ssiDetails = application.getBorrowerDetails().getSsiDetails();
			BeanUtils.copyProperties(dynaForm, ssiDetails);

			borrowerDetails = application.getBorrowerDetails();
			BeanUtils.copyProperties(dynaForm, borrowerDetails);
			Log.log(4, "ApplicationProcessingAction", "showCgpanList",
					"Set Borrower Details...");

			PrimarySecurityDetails primarySecurityDetails = application
					.getProjectOutlayDetails().getPrimarySecurityDetails();
			BeanUtils.copyProperties(dynaForm, primarySecurityDetails);
			double landValue = ((Double) dynaForm.get("landValue"))
					.doubleValue();
			double bldgValue = ((Double) dynaForm.get("bldgValue"))
					.doubleValue();
			double machineValue = ((Double) dynaForm.get("machineValue"))
					.doubleValue();
			double assetsValue = ((Double) dynaForm.get("assetsValue"))
					.doubleValue();
			double currentAssetsValue = ((Double) dynaForm
					.get("currentAssetsValue")).doubleValue();
			double othersValue = ((Double) dynaForm.get("othersValue"))
					.doubleValue();
			double psTotalValue = landValue + bldgValue + machineValue
					+ assetsValue + currentAssetsValue + othersValue;
			Double intPsTotal = new Double(psTotalValue);
			dynaForm.set("psTotal", intPsTotal);
			Log.log(4, "ApplicationProcessingAction", "showCgpanList",
					"Set Primary Security Details...");

			ProjectOutlayDetails projectOutlayDetails = application
					.getProjectOutlayDetails();
			BeanUtils.copyProperties(dynaForm, projectOutlayDetails);

			Log.log(4, "ApplicationProcessingAction", "showCgpanList",
					"Set Project Outlay Details...");

			if (!applicationTypes.equals("TCE")) {
				TermLoan termLoan = application.getTermLoan();
				BeanUtils.copyProperties(dynaForm, termLoan);
				Log.log(4, "ApplicationProcessingAction", "showCgpanList",
						"Set Term Loan Details...");
				double termCreditSanctioned = ((Double) dynaForm
						.get("termCreditSanctioned")).doubleValue();
				Double termCreditSanctionedVal = new Double(
						termCreditSanctioned);
				double tcPromoterContribution = ((Double) dynaForm
						.get("tcPromoterContribution")).doubleValue();
				double tcSubsidyOrEquity = ((Double) dynaForm
						.get("tcSubsidyOrEquity")).doubleValue();
				double tcOthers = ((Double) dynaForm.get("tcOthers"))
						.doubleValue();
				double projectCost = termCreditSanctioned
						+ tcPromoterContribution + tcSubsidyOrEquity + tcOthers;
				Double projectCostIntVal = new Double(projectCost);
				dynaForm.set("projectCost", projectCostIntVal);
				Log.log(4, "ApplicationProcessingAction", "showCgpanList",
						"project Cost:" + projectCostIntVal);
				Log.log(4,
						"ApplicationProcessingAction",
						"showCgpanList",
						"project cost in the form:"
								+ dynaForm.get("projectCost"));

				dynaForm.set("amountSanctioned", termCreditSanctionedVal);

				WorkingCapital workingCapital = application.getWc();
				BeanUtils.copyProperties(dynaForm, workingCapital);
				double wcFundBasedSanctioned = ((Double) dynaForm
						.get("wcFundBasedSanctioned")).doubleValue();
				double wcNonFundBasedSanctioned = ((Double) dynaForm
						.get("wcNonFundBasedSanctioned")).doubleValue();

				Double existingTotalFBVal = new Double(wcFundBasedSanctioned);
				Double existingTotalNFBVal = new Double(
						wcNonFundBasedSanctioned);
				dynaForm.set("existingFundBasedTotal", existingTotalFBVal);
				dynaForm.set("existingNonFundBasedTotal", existingTotalNFBVal);

				double wcPromoterContribution = ((Double) dynaForm
						.get("wcPromoterContribution")).doubleValue();
				double wcSubsidyOrEquity = ((Double) dynaForm
						.get("wcSubsidyOrEquity")).doubleValue();
				double wcOthers = ((Double) dynaForm.get("wcOthers"))
						.doubleValue();

				double wcAssessed = wcFundBasedSanctioned
						+ wcNonFundBasedSanctioned + wcPromoterContribution
						+ wcSubsidyOrEquity + wcOthers;
				Double wcAssessedIntVal = new Double(wcAssessed);
				dynaForm.set("wcAssessed", wcAssessedIntVal);
				Log.log(4, "ApplicationProcessingAction", "showCgpanList",
						"Set Working Capital Details...");

				double projectCostValue = ((Double) dynaForm.get("projectCost"))
						.doubleValue();
				double wcAssessedValue = ((Double) dynaForm.get("wcAssessed"))
						.doubleValue();
				double projectOutlayValueCost = projectCostValue
						+ wcAssessedValue;
				Double projectOutlayVal = new Double(projectOutlayValueCost);
				dynaForm.set("projectOutlay", projectOutlayVal);
			} else {
				double value = 0.0D;
				Double intVal = new Double(value);
				dynaForm.set("termCreditSanctioned", intVal);
				dynaForm.set("tcPromoterContribution", intVal);
				dynaForm.set("tcSubsidyOrEquity", intVal);
				dynaForm.set("tcOthers", intVal);
				dynaForm.set("projectCost", intVal);
				dynaForm.set("wcFundBasedSanctioned", intVal);
				dynaForm.set("wcNonFundBasedSanctioned", intVal);
				dynaForm.set("wcSubsidyOrEquity", intVal);
				dynaForm.set("wcOthers", intVal);
				dynaForm.set("wcAssessed", intVal);
				dynaForm.set("projectOutlay", intVal);
			}

			Securitization securitization = application.getSecuritization();
			BeanUtils.copyProperties(dynaForm, securitization);
			Log.log(4, "ApplicationProcessingAction", "showCgpanList",
					"Set Securitization Details...");

			String mcgfFlag = mliInfo.getSupportMCGF();
			Log.log(4, "ApplicationProcessingAction", "showCgpanList",
					"MCGF Flag" + mcgfFlag);
			if (mcgfFlag.equals("Y")) {
				appSession1.setAttribute("MCGF_FLAG", "M");

				mcgfDetails = application.getMCGFDetails();
				Log.log(4,
						"ApplicationProcessingAction",
						"showCgpanList",
						"mcgf App ref no:"
								+ mcgfDetails.getApplicationReferenceNumber());
				Log.log(4,
						"ApplicationProcessingAction",
						"showCgpanList",
						"mcgf approved Amount:"
								+ mcgfDetails.getMcgfApprovedAmt());
				Log.log(4, "ApplicationProcessingAction", "showCgpanList",
						"mcgf district:" + mcgfDetails.getMcgfDistrict());
				Log.log(4, "ApplicationProcessingAction", "showCgpanList",
						"mcgf bank:" + mcgfDetails.getParticipatingBank());
				Log.log(4,
						"ApplicationProcessingAction",
						"showCgpanList",
						"mcgf branch :"
								+ mcgfDetails.getParticipatingBankBranch());
				Log.log(4, "ApplicationProcessingAction", "showCgpanList",
						"mcgf name :" + mcgfDetails.getMcgfName());
				Log.log(4, "ApplicationProcessingAction", "showCgpanList",
						"mcgf Id:" + mcgfDetails.getMcgfId());

				mcgfDetails.setMcgfId(mliId);
				application.setMCGFDetails(mcgfDetails);
				BeanUtils.copyProperties(dynaForm, mcgfDetails);
				Log.log(4, "ApplicationProcessingAction", "showCgpanList",
						"Set MCGF Details...");
			} else {
				Log.log(4, "ApplicationProcessingAction", "showCgpanList",
						"Not under MCGF");

				appSession1.setAttribute("MCGF_FLAG", "NM");
			}

			BeanUtils.copyProperties(dynaForm, application);

			Log.log(4, "ApplicationProcessingAction", "showCgpanList",
					"Set Application Details...");

			String constitutionVal = ssiDetails.getConstitution();

			if ((!constitutionVal.equals("proprietary"))
					&& (!constitutionVal.equals("partnership"))
					&& (!constitutionVal.equals("private"))
					&& (!constitutionVal.equals("public"))) {
				dynaForm.set("constitutionOther", constitutionVal);
				dynaForm.set("constitution", "Others");
			} else {
				dynaForm.set("constitution", constitutionVal);
			}
			Log.log(4, "ApplicationProcessingAction", "showCgpanList",
					"Set Application Details...");

			String legalIDString = ssiDetails.getCpLegalID();
			if ((legalIDString != null) && (!legalIDString.equals(""))) {
				if ((!legalIDString.equals("VoterIdentityCard"))
						&& (!legalIDString.equals("RationCardnumber"))
						&& (!legalIDString.equals("PASSPORT"))
						&& (!legalIDString.equals("Driving License"))) {
					dynaForm.set("otherCpLegalID", legalIDString);
					dynaForm.set("cpLegalID", "Others");
				} else {
					dynaForm.set("cpLegalID", legalIDString);
				}
			}

			Log.log(4, "ApplicationProcessingAction", "showCgpanList",
					"Set Application Details...");

			String subsidyEquityName = projectOutlayDetails.getSubsidyName();
			Log.log(4, "ApplicationProcessingAction", "showCgpanList",
					"Subsidy Name :" + projectOutlayDetails.getSubsidyName());
			if ((subsidyEquityName != null) && (!subsidyEquityName.equals(""))) {
				if ((!subsidyEquityName.equals("PMRY"))
						&& (!subsidyEquityName.equals("SJRY"))) {
					dynaForm.set("otherSubsidyEquityName", subsidyEquityName);
					dynaForm.set("subsidyName", "Others");
				} else {
					dynaForm.set("subsidyName", subsidyEquityName);
				}
			}

			Log.log(4, "ApplicationProcessingAction", "showCgpanList",
					"Set Application Details...");

			ArrayList statesList = (ArrayList) getStateList();
			dynaForm.set("statesList", statesList);

			String state = (String) dynaForm.get("state");
			ArrayList districtList = admin.getAllDistricts(state);
			dynaForm.set("state", ssiDetails.getState());
			dynaForm.set("districtList", districtList);

			String districtName = ssiDetails.getDistrict();

			if (districtList.contains(districtName)) {
				dynaForm.set("district", districtName);
			} else {
				dynaForm.set("districtOthers", districtName);
				dynaForm.set("district", "Others");
			}

			Log.log(4, "ApplicationProcessingAction", "showCgpanList",
					"Set Application Details...");

			ArrayList socialList = getSocialCategory();
			dynaForm.set("socialCategoryList", socialList);

			dynaForm.set("industryNature", ssiDetails.getIndustryNature());
			dynaForm.set("industrySector", ssiDetails.getIndustrySector());

			MCGSProcessor mcgsProcessor = new MCGSProcessor();
			ArrayList participatingBanks = mcgsProcessor
					.getAllParticipatingBanks(mliId);
			dynaForm.set("participatingBanks", participatingBanks);

			if (applicationTypes.equals("TCE")) {
				if (application.getBorrowerDetails().getPreviouslyCovered()
						.equals("Y")) {
					if ((application.getCgpanReference() != null)
							&& (!application.getCgpanReference().equals(""))) {
						String cgpanRef = application.getCgpanReference();
						dynaForm.set("unitValue", cgpanRef);
						dynaForm.set("none", "cgpan");
					}
				}

				dynaForm.set("previouslyCovered", "Y");

				if (application.getLoanType().equals("WC")) {
					throw new MessageException(
							"Not a valid Application for Additional Term Loan");
				}

				ClaimsProcessor claimProcessor = new ClaimsProcessor();

				if (((cgbid == null) || (cgbid.equals(""))) && (cgpan != null)
						&& (!cgpan.equals(""))) {
					cgbid = claimProcessor.getBorowwerForCGPAN(cgpan);
				}

				int claimCount = appProcessor.getClaimCount(cgbid);
				if (claimCount > 0) {
					throw new MessageException(
							"Application cannot be filed by this borrower since Claim Application has been submitted");
				}

				Log.log(4, "ApplicationProcessingAction", "showCgpanList",
						"Additional Term Loan");
				if ((application.getStatus().equals("EX"))
						|| (application.getStatus().equals("AP"))) {
					appSession1.setAttribute("APPLICATION_TYPE_FLAG", "0");
				} else {
					throw new MessageException(
							"Not a valid Application for Additional Term Loan");
				}
				pageForward = "AddtlTermLoanPage";
			} else if (applicationTypes.equals("WCE")) {
				String remarks = application.getRemarks();
				application.setExistingRemarks(remarks);

				double balanceAppAmt = appProcessor
						.getBalanceApprovedAmt(application);

				Log.log(4, "ApplicationProcessingAction", "showCgpanList",
						"balanceAppAmt :" + balanceAppAmt);

				dynaForm.set("remarks", "");
				dynaForm.set("existingRemarks",
						application.getExistingRemarks());

				dynaForm.set("unitValue", cgpan);
				dynaForm.set("none", "cgpan");
				dynaForm.set("previouslyCovered", "Y");

				double wcFundBasedSanctioned = ((Double) dynaForm
						.get("wcFundBasedSanctioned")).doubleValue();
				Double wcFundBasedSanctionedVal = new Double(
						wcFundBasedSanctioned);
				double wcNonFundBasedSanctioned = ((Double) dynaForm
						.get("wcNonFundBasedSanctioned")).doubleValue();

				Double existingTotalFBVal = new Double(wcFundBasedSanctioned);
				Double existingTotalNFBVal = new Double(
						wcNonFundBasedSanctioned);
				dynaForm.set("existingFundBasedTotal", existingTotalFBVal);
				dynaForm.set("existingNonFundBasedTotal", existingTotalNFBVal);

				if ((application.getLoanType().equals("TC"))
						|| (application.getLoanType().equals("CC"))) {
					throw new MessageException(
							"Not a valid Application for Enhancement of Working Capital");
				}

				ClaimsProcessor claimProcessor = new ClaimsProcessor();

				if (((cgbid == null) || (cgbid.equals(""))) && (cgpan != null)
						&& (!cgpan.equals(""))) {
					cgbid = claimProcessor.getBorowwerForCGPAN(cgpan);
				}

				int claimCount = appProcessor.getClaimCount(cgbid);
				if (claimCount > 0) {
					throw new MessageException(
							"Application cannot be filed by this borrower since Claim Application has been submitted");
				}

				if (application.getStatus().equals("EX")) {
					throw new MessageException("This application has expired");
				}

				appSession1.setAttribute("APPLICATION_TYPE_FLAG", "1");
				pageForward = "EnhancementPage";
			} else if (applicationTypes.equals("WCR")) {
				String remarks = application.getRemarks();
				application.setExistingRemarks(remarks);

				dynaForm.set("remarks", "");
				dynaForm.set("existingRemarks",
						application.getExistingRemarks());

				dynaForm.set("none", "cgpan");
				dynaForm.set("unitValue", cgpan);
				dynaForm.set("previouslyCovered", "Y");

				double wcFundBasedSanctioned = ((Double) dynaForm
						.get("wcFundBasedSanctioned")).doubleValue();
				Double wcFundBasedSanctionedVal = new Double(
						wcFundBasedSanctioned);
				double wcNonFundBasedSanctioned = ((Double) dynaForm
						.get("wcNonFundBasedSanctioned")).doubleValue();

				Double existingTotalFBVal = new Double(wcFundBasedSanctioned);
				Double existingTotalNFBVal = new Double(
						wcNonFundBasedSanctioned);
				dynaForm.set("existingFundBasedTotal", existingTotalFBVal);
				dynaForm.set("existingNonFundBasedTotal", existingTotalNFBVal);

				ClaimsProcessor claimProcessor = new ClaimsProcessor();

				if (((cgbid == null) || (cgbid.equals(""))) && (cgpan != null)
						&& (!cgpan.equals(""))) {
					cgbid = claimProcessor.getBorowwerForCGPAN(cgpan);
				}

				int claimCount = appProcessor.getClaimCount(cgbid);
				if (claimCount > 0) {
					throw new MessageException(
							"Application cannot be filed by this borrower since Claim Application has been submitted");
				}

				if (!application.getStatus().equals("EX")) {
					throw new MessageException(
							"This application has not expired");
				}
				if (application.getStatus().equals("EX")) {
					appSession1.setAttribute("APPLICATION_TYPE_FLAG", "2");
					pageForward = "RenewalPage";
				}
			} else if (applicationTypes.equals("MA")) {
				String remarks = application.getRemarks();
				application.setExistingRemarks(remarks);

				dynaForm.set("remarks", "");
				dynaForm.set("existingRemarks",
						application.getExistingRemarks());

				ClaimsProcessor claimProcessor = new ClaimsProcessor();

				if ((cgpan != null) && (!cgpan.equals(""))) {
					cgbid = claimProcessor.getBorowwerForCGPAN(cgpan);
				} else if ((appRefNo != null) && (!appRefNo.equals(""))) {
					Application tempApp = appProcessor.getPartApplication(
							mliId, "", appRefNo);
					cgpan = tempApp.getCgpan();

					dynaForm.set("WeaverCreditScheme",
							tempApp.getWeaverCreditScheme());
					if ((cgpan != null) && (!cgpan.equals(""))) {
						cgbid = claimProcessor.getBorowwerForCGPAN(cgpan);
					}

				}

				if ((cgbid != null) && (!cgbid.equals(""))) {
					int claimCount = appProcessor.getClaimCount(cgbid);
					if (claimCount > 0) {
						throw new MessageException(
								"Application cannot be filed by this borrower since Claim Application has been submitted");
					}

				}

				if ((cgpan != null) && (!cgpan.equals(""))) {
					dynaForm.set("unitValue", cgpan);
					dynaForm.set("none", "cgpan");
					dynaForm.set("previouslyCovered", "Y");
				}

				Log.log(4, "ApplicationProcessingAction", "showCgpanList",
						"Loan Type :" + applicationTypes);

				if ((application.getStatus().equals("PE"))
						|| (application.getStatus().equals("EP"))) {
					throw new MessageException(
							"This application cannot be modified since decision to be taken by CGTSI is pending");
				}

				if (application.getStatus().equals("EX")) {
					throw new MessageException(
							"This application cannot be modified since the application is not live");
				}

				if (applicationLoanType.equals("TC")) {
					appSession1.setAttribute("APPLICATION_TYPE_FLAG", "3");
					pageForward = "tcForward";
				}

				if (applicationLoanType.equals("WC")) {
					appSession1.setAttribute("APPLICATION_TYPE_FLAG", "4");
					pageForward = "wcForward";
				}

				if (applicationLoanType.equals("BO")) {
					appSession1.setAttribute("APPLICATION_TYPE_FLAG", "5");
					pageForward = "bothForward";
				}

				if (applicationLoanType.equals("CC")) {
					appSession1.setAttribute("APPLICATION_TYPE_FLAG", "6");
					pageForward = "ccForward";
				}

			} else if ((applicationTypes.equals("APP"))
					|| (applicationTypes.equals("REAPP"))
					|| (applicationTypes.equals("EL"))
					|| (applicationTypes.equals("DUP"))) {
				if ((cgpan != null) && (!cgpan.equals(""))) {
					dynaForm.set("unitValue", cgpan);
					dynaForm.set("none", "cgpan");
					dynaForm.set("previouslyCovered", "Y");
				}

				Log.log(4, "ApplicationProcessingAction", "showCgpanList",
						"Loan Type :" + applicationTypes);

				if (applicationLoanType.equals("TC")) {
					appSession1.setAttribute("APPLICATION_TYPE_FLAG", "11");
					Log.log(4,
							"ApplicationProcessingAction",
							"showCgpanList",
							"Session Attribute :"
									+ appSession1
											.getAttribute("APPLICATION_TYPE_FLAG"));
					pageForward = "tcForward";
				}

				if (applicationLoanType.equals("WC")) {
					appSession1.setAttribute("APPLICATION_TYPE_FLAG", "12");
					pageForward = "wcForward";
				}

				if (applicationLoanType.equals("CC")) {
					appSession1.setAttribute("APPLICATION_TYPE_FLAG", "13");
					pageForward = "ccForward";
				}
			}
		}

		Log.log(5, "ApplicationProcessingAction", "showCgpanList",
				"Page to be forwaded :" + pageForward);
		Log.log(4, "ApplicationProcessingAction", "showCgpanList", "Exited");

		cgbid = null;
		cgpan = null;

		return mapping.findForward(pageForward);
	}

	
	public ActionForward submitApp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		   Log.log(4, "ApplicationProcessingAction", "submitApp", "Entered");		
		   String successPage = "";
		    String csrfCode = request.getParameter("csrfCode");
		    //System.out.println("csrfCode :::::  " + csrfCode);
		    String target="";
		    boolean check=CSRFUtil.getCSRFflag(csrfCode, request.getSession(false), request);
		    //System.out.println("check=="+check);
		    if(csrfCode != null && !"".equals(csrfCode) && check){
		try {
			
			// if (isTokenValid(request)) {
			// resetToken(request);
			DynaActionForm dynaForm = (DynaActionForm) form;
			LogClass.StepWritter(" submitApp start from here and loan type is "
					+ dynaForm.get("loanType"));
			String primarySercurity = (String) dynaForm.get("pSecurity");
			//System.out.println("Primary Security : " + primarySercurity);
		
			HttpSession applicationSession = request.getSession(false);
			applicationSession.setAttribute("hybridUIflag","DTRUE");
			//applicationSession.setAttribute("financialUIflag", "DFTRUE");
			Log.log(4, "ApplicationProcessingAction", "submitApp",
					"Creating Objects");


			ApplicationProcessor appProcessor = new ApplicationProcessor();
			ApplicationDAO applicaitonDAO = new ApplicationDAO();
			Application application = new Application();
			BorrowerDetails borrowerDetails = new BorrowerDetails();
			SSIDetails ssiDetails = new SSIDetails();
			ProjectOutlayDetails projectOutlayDetails = new ProjectOutlayDetails();
			PrimarySecurityDetails primarySecurityDetails = new PrimarySecurityDetails();
			TermLoan termLoan = new TermLoan();
			WorkingCapital workingCapital = new WorkingCapital();
			Securitization securitization = new Securitization();
			MCGFDetails mcgfDetails = new MCGFDetails();
			String expoid="";
			String Fbchk = "";
			ClaimsProcessor cpProcessor = new ClaimsProcessor();
			User user = getUserInformation(request);
			// bhu
			projectOutlayDetails.setIsPrimarySecurity(primarySercurity);// bhu
			String internalRating = (String) dynaForm.get("internalRating");
			application.setInternalRate(internalRating);
			String handiCrafts = (String) dynaForm.get("handiCrafts");
			 
		
			String dcHandicrafts = (String) dynaForm.get("dcHandicrafts");
			String icardNo = (String) dynaForm.get("icardNo");
			Date icardIssueDate = (Date) dynaForm.get("icardIssueDate");

			String dcHandlooms = (String) dynaForm.get("dcHandlooms");

			String WeaverCreditScheme = (String) dynaForm
					.get("WeaverCreditScheme");
			String handloomchk = (String) dynaForm.get("handloomchk");
			String industrySector = (String) dynaForm.get("industrySector");
			String unitValue = (String) dynaForm.get("unitValue");
			String activityConfirm = (String) dynaForm.get("activityConfirm");

			String gstNo = (String) dynaForm.get("gstNo");
			String gstState = (String) dynaForm.get("gstState");
			 String aadhar=(String)dynaForm.get("adhar");
			 //System.out.println("aadhar==="+aadhar);
			
			// String gstState =request.getParameter("stateCode");

			if ((handiCrafts != null) && (!handiCrafts.equals("null"))) {
				if (handiCrafts.equals("N")) {
					dcHandicrafts = "N";
				}

				if ((WeaverCreditScheme != null)
						&& (!WeaverCreditScheme.equals("null"))) {
					if ((dcHandlooms.equals("N"))
							&& (WeaverCreditScheme.equals("Select"))) {
						WeaverCreditScheme = "";
					}
				}
			}
  			application.setHandiCrafts(handiCrafts);
			application.setDcHandicrafts(dcHandicrafts);

			application.setIcardNo(icardNo);
			application.setIcardIssueDate(icardIssueDate);
			application.setDcHandlooms(dcHandlooms);

			application.setWeaverCreditScheme(WeaverCreditScheme);
			application.setHandloomchk(handloomchk);
			application.setActivityConfirm(activityConfirm);
			String jointFinance = (String) dynaForm.get("jointFinance");
			
			String jointcgpan = (String) dynaForm.get("jointcgpan");
		
			if((String) dynaForm.get("exposureFbId")!=null){
				Fbchk = (String) dynaForm.get("exposureFbId");
			}else{
				Fbchk = "";
 			};
			Log.log(5, "ApplicationProcessingAction", "submitApp", "Fbchk:"	+ Fbchk);
			application.setJointcgpan(jointcgpan);

			Date expiryDate = (Date) dynaForm.get("expiryDate");
			application.setAppExpiryDate(expiryDate);

			String zoneId = "";
			String branchId = "";
			String mliId = "";
			 
			String userId = user.getUserId();
			Log.log(5, "ApplicationProcessingAction", "submitApp", "user Id :"
					+ userId);

			String bankId = user.getBankId();
			zoneId = user.getZoneId();
			branchId = user.getBranchId();
			//---say
		if(Fbchk!=null)
			{
			if(Fbchk.equals("Y1")){				
		    expoid = applicaitonDAO.getExposureID(bankId);				
			}else{
				expoid ="";		
			}
		 }else{
			 expoid ="";		
		 }
			Log.log(5, "ApplicationProcessingAction", "submitApp", "exposure Id :"
					+ expoid);
			
				  
			application.setExposureFbId(expoid);
			dynaForm.set("exposureFbId", expoid);
//			if(expoid!=null && expoid.equals("")==false){
//				application.setExposureFbId(expoid);	
//		
//			}else{
//				application.setExposureFbId("");		
//			}
			//----------say
			 
				
			if (bankId.equals("0000")) {
				String memberName = (String) dynaForm.get("selectMember");
				if (memberName != null) {
					bankId = memberName.substring(0, 4);
					zoneId = memberName.substring(4, 8);
					branchId = memberName.substring(8, 12);

					application.setBankId(bankId);
					application.setZoneId(zoneId);
					application.setBranchId(branchId);
					mliId = bankId + zoneId + branchId;
					application.setMliID(mliId);
				}

			} else {
				bankId = user.getBankId();
				application.setBankId(bankId);
				zoneId = user.getZoneId();
				application.setZoneId(zoneId);
				branchId = user.getBranchId();
				application.setBranchId(branchId);
				mliId = bankId + zoneId + branchId;
				application.setMliID(mliId);
			}

			double exposureLimit = applicaitonDAO.getExposureLimit(bankId);
			double maxExposureLimit = applicaitonDAO
					.getMaxExposureLimit(bankId);

			if ((exposureLimit > 0.0D)
					&& (exposureLimit / 10000000.0D >= maxExposureLimit)) {
				// throw new
				// DatabaseException("The Maximum Limit of Guarantee Approved Amount should be less than "
				// + maxExposureLimit);
				throw new ArithmeticException(
						(new StringBuilder(
								"The Maximum Limit of Guarantee Approved Amount should be less than "))
								.append(maxExposureLimit).toString());
			}

			dynaForm.set("bankId", bankId);
			dynaForm.set("zoneId", zoneId);
			dynaForm.set("branchId", branchId);
			dynaForm.set("mliID", mliId);
			
		

			String applicationType = "";
			String applicationLoanType = "";

			if (applicationSession.getAttribute("APPLICATION_LOAN_TYPE") == null) {
				applicationType = (String) applicationSession
						.getAttribute("APPLICATION_TYPE");
				Log.log(5, "ApplicationProcessingAction", "submitApp",
						"ApplicationLoan Type :" + applicationType);
			} else {
				applicationLoanType = (String) applicationSession
						.getAttribute("APPLICATION_LOAN_TYPE");
				Log.log(5, "ApplicationProcessingAction", "submitApp",
						"ApplicationLoan Type :" + applicationLoanType);

				application.setLoanType(applicationType);
			}

			
			Log.log(4, "ApplicationProcessingAction", "submitApp",
					"Calling Bean Utils...");

			BeanUtils.populate(ssiDetails, dynaForm.getMap());

			

			String districtOthersValue = (String) dynaForm
					.get("districtOthers");
			if ((dynaForm.get("district") != null)
					&& (!dynaForm.get("district").equals(""))) {
				if (dynaForm.get("district").equals("Others")) {
					ssiDetails.setDistrict(districtOthersValue);
				}

			}

		

			String activityType = (String) dynaForm.get("activityType");
			//System.out.println("activityType===="+activityType);

			
			 if ((dynaForm.get("activityType") != null) &&
			 (!dynaForm.get("activityType").equals(""))) {
			 ssiDetails.setActivityType(activityType); }
			 
			borrowerDetails.setSsiDetails(ssiDetails);
			BeanUtils.populate(borrowerDetails, dynaForm.getMap());

			BeanUtils.populate(primarySecurityDetails, dynaForm.getMap());
			projectOutlayDetails
					.setPrimarySecurityDetails(primarySecurityDetails);
			BeanUtils.populate(projectOutlayDetails, dynaForm.getMap());

			BeanUtils.populate(termLoan, dynaForm.getMap());
			BeanUtils.populate(securitization, dynaForm.getMap());
			BeanUtils.populate(workingCapital, dynaForm.getMap());

		
			
	

			application.setProjectOutlayDetails(projectOutlayDetails);
			application.setTermLoan(termLoan);
			application.setWc(workingCapital);
			application.setSecuritization(securitization);
			application.setGstState(gstState);
			application.setGstNo(gstNo);
		    application.setAdharNo(aadhar);
		   
		 // Added by DKR Hybrid Security
			 String hybridSecurity = (String) dynaForm.get("hybridSecurity");
			 Integer sanctionTenure=(Integer) dynaForm.get("sanctionTenure");
			// Integer DPDStatus=(Integer) dynaForm.get("DPDStatus");
			 //Double TypeLoanTC=(Double)dynaForm.get("TypeLoanTC");
			 //Double TypeLoanWC=(Double)dynaForm.get("TypeLoanWC");
			 String borrowerGstNo=(String)dynaForm.get("BorrowergstNo");
			// application.getBorrowerDetails().getSsiDetails()
				//.setMSE((String) dynaForm.get("mSE"));	
			 
			
			 
			String  facilityOthrtBnk=(String)dynaForm.get("facilityOthrtBnk");
			 
			 Double movColSecurityAmt = (Double) dynaForm.get("movCollateratlSecurityAmt");
			 Double immovColSecurityAmt = (Double) dynaForm.get("immovCollateratlSecurityAmt");
			 Double totalMIcolSecAmt = (Double) dynaForm.get("totalMIcollatSecAmt");
			 Long proMobileNo = (Long) dynaForm.get("proMobileNo");
			 
			 Double sancAmt=(Double) dynaForm.get("sancAmt");
			 Double outstandingAmt=(Double) dynaForm.get("outstandingAmt");
			 Double wcSancAmt=(Double) dynaForm.get("wcSancAmt");
			 Double wcOutstandingAmt=(Double) dynaForm.get("wcOutstandingAmt");
			 Double otherOutstandingAmt=(Double) dynaForm.get("otherOutstandingAmt");
			 Double otherSancAmt=(Double) dynaForm.get("otherSancAmt");
			 /* Double otherBanksancAmt=(Double) dynaForm.get("otherBanksancAmt");
			 Double otherBankoutstandingAmt=(Double) dynaForm.get("otherBankoutstandingAmt");
			 Double otherBankwcSancAmt=(Double) dynaForm.get("otherBankwcSancAmt");
			 Double otherBankwcOutstandingAmt=(Double) dynaForm.get("otherBankwcOutstandingAmt");
			 Double otherBankotherOutstandingAmt=(Double) dynaForm.get("otherBankotherOutstandingAmt");
			 Double otherBankotherSancAmt=(Double) dynaForm.get("otherBankotherSancAmt");*/
			 Double pmrEquity=(Double) dynaForm.get("pmrEquity");
			 Double pmrDebt=(Double) dynaForm.get("pmrDebt");
			 
				
			 
			 String accStatus=(String)dynaForm.get("accStatus");
			 String wcAccStatus=(String)dynaForm.get("wcAccStatus");
			 String otherAccStatus=(String)dynaForm.get("otherAccStatus");
			 String otherBankwcAccStatus=(String)dynaForm.get("otherBankwcAccStatus");
			 String otherBankaccStatus=(String)dynaForm.get("otherBankaccStatus");
			 String otherBankotherAccStatus=(String)dynaForm.get("otherBankotherAccStatus");
			 String isAcctReconstructed=(String)dynaForm.get("isAcctReconstructed");
			 Date npaDate=(Date)dynaForm.get("npaDate");
			 String npaReason=(String)dynaForm.get("npaReason");
			 
			 
			 String cpFirstName=(String) dynaForm.get("cpFirstName");
			 String cpLastName=(String) dynaForm.get("cpLastName");
			 String cpITPAN=(String) dynaForm.get("cpITPAN");
			 Date cpDOB=(Date) dynaForm.get("cpDOB");
			 String cpGender=(String) dynaForm.get("cpGender");
			 String socialCategory=(String) dynaForm.get("socialCategory");
			 String email=(String) dynaForm.get("email");
			 String adhar=(String) dynaForm.get("adhar");
		//  Double pmrEquity=(String) dynaForm.get("pmrEquity");
		 
		 String cpFirstName1=(String) dynaForm.get("cpFirstName1");
		 String cpLastName1=(String) dynaForm.get("cpLastName1");
		 String cpITPAN1=(String) dynaForm.get("cpITPAN1");
		 Date cpDOB1=(Date) dynaForm.get("cpDOB1");
		 String cpGender1=(String) dynaForm.get("cpGender1");
		 String socialCategory1=(String) dynaForm.get("socialCategory1");
		 String email1=(String) dynaForm.get("email1");
		String adhar1=(String) dynaForm.get("adhar1");
	     Double pmrEquity1=(Double) dynaForm.get("pmrEquity1");
	    Double pmrDebt1=(Double) dynaForm.get("pmrDebt1");
		 
		 
		 String cpFirstName2=(String) dynaForm.get("cpFirstName2");
		 String cpLastName2=(String) dynaForm.get("cpLastName2");
		 String cpITPAN2=(String) dynaForm.get("cpITPAN2");
		 Date cpDOB2=(Date) dynaForm.get("cpDOB2");
		 String cpGender2=(String) dynaForm.get("cpGender2");
		 String socialCategory2=(String) dynaForm.get("socialCategory2");
		 String email2=(String) dynaForm.get("email2");
		 String adhar2=(String) dynaForm.get("adhar2");
		  Double pmrEquity2=(Double) dynaForm.get("pmrEquity2");
		 Double pmrDebt2=(Double) dynaForm.get("pmrDebt2");
		 
		 
		 
		 String cpFirstName3=(String) dynaForm.get("cpFirstName3");
		 String cpLastName3=(String) dynaForm.get("cpLastName3");
		 String cpITPAN3=(String) dynaForm.get("cpITPAN3");
		 Date cpDOB3=(Date) dynaForm.get("cpDOB3");
		 String cpGender3=(String) dynaForm.get("cpGender3");
		 String socialCategory3=(String) dynaForm.get("socialCategory3");
		 String email3=(String) dynaForm.get("email3");
		 String adhar3=(String) dynaForm.get("adhar3");
		  Double pmrEquity3=(Double) dynaForm.get("pmrEquity3");
		 Double pmrDebt3=(Double) dynaForm.get("pmrDebt3");
		 
		 String cpFirstName4=(String) dynaForm.get("cpFirstName4");
		 String cpLastName4=(String) dynaForm.get("cpLastName4");
		 String cpITPAN4=(String) dynaForm.get("cpITPAN4");
		 Date cpDOB4=(Date) dynaForm.get("cpDOB4");
		 String cpGender4=(String) dynaForm.get("cpGender4");
		 String socialCategory4=(String) dynaForm.get("socialCategory4");
		 String email4=(String) dynaForm.get("email4");
		String adhar4=(String) dynaForm.get("adhar4");
		  Double pmrEquity4=(Double) dynaForm.get("pmrEquity4");
		 Double pmrDebt4=(Double) dynaForm.get("pmrDebt4");
		 
		 String cpFirstName5=(String) dynaForm.get("cpFirstName5");
		 String cpLastName5=(String) dynaForm.get("cpLastName5");
		 String cpITPAN5=(String) dynaForm.get("cpITPAN5");
		 Date cpDOB5=(Date) dynaForm.get("cpDOB5");
		 String cpGender5=(String) dynaForm.get("cpGender5");
		 String socialCategory5=(String) dynaForm.get("socialCategory5");
		 String email5=(String) dynaForm.get("email5");
		 String adhar5=(String) dynaForm.get("adhar5");
		  Double pmrEquity5=(Double) dynaForm.get("pmrEquity5");
		 Double pmrDebt5=(Double) dynaForm.get("pmrDebt5");
		 
		 String cpFirstName6=(String) dynaForm.get("cpFirstName6");
		 String cpLastName6=(String) dynaForm.get("cpLastName6");
		 String cpITPAN6=(String) dynaForm.get("cpITPAN6");
		 Date cpDOB6=(Date) dynaForm.get("cpDOB6");
		 String cpGender6=(String) dynaForm.get("cpGender6");
		 String socialCategory6=(String) dynaForm.get("socialCategory6");
		 String email6=(String) dynaForm.get("email6");
		 String adhar6=(String) dynaForm.get("adhar6");
		  Double pmrEquity6=(Double) dynaForm.get("pmrEquity6");
		 Double pmrDebt6=(Double) dynaForm.get("pmrDebt6");
		 
		 String cpFirstName7=(String) dynaForm.get("cpFirstName7");
		 String cpLastName7=(String) dynaForm.get("cpLastName7");
		 String cpITPAN7=(String) dynaForm.get("cpITPAN7");
		 Date cpDOB7=(Date) dynaForm.get("cpDOB7");
		 String cpGender7=(String) dynaForm.get("cpGender7");
		 String socialCategory7=(String) dynaForm.get("socialCategory7");
		 String email7=(String) dynaForm.get("email7");
		 String adhar7=(String) dynaForm.get("adhar7");
		  Double pmrEquity7=(Double) dynaForm.get("pmrEquity7");
		 Double pmrDebt7=(Double) dynaForm.get("pmrDebt7");
		 
		 String cpFirstName8=(String) dynaForm.get("cpFirstName8");
         String cpLastName8=(String) dynaForm.get("cpLastName8");
         String cpITPAN8=(String) dynaForm.get("cpITPAN8");
         Date cpDOB8=(Date) dynaForm.get("cpDOB8");
         String cpGender8=(String) dynaForm.get("cpGender8");
         String socialCategory8=(String) dynaForm.get("socialCategory8");
         String email8=(String) dynaForm.get("email8");
        String adhar8=(String) dynaForm.get("adhar8");
          Double pmrEquity8=(Double) dynaForm.get("pmrEquity8");
         Double pmrDebt8=(Double) dynaForm.get("pmrDebt8");
 
  String cpFirstName9=(String) dynaForm.get("cpFirstName9");
         String cpLastName9=(String) dynaForm.get("cpLastName9");
         String cpITPAN9=(String) dynaForm.get("cpITPAN9");
         Date cpDOB9=(Date) dynaForm.get("cpDOB9");
         String cpGender9=(String) dynaForm.get("cpGender9");
         String socialCategory9=(String) dynaForm.get("socialCategory9");
         String email9=(String) dynaForm.get("email9");
        String adhar9=(String) dynaForm.get("adhar9");
          Double pmrEquity9=(Double) dynaForm.get("pmrEquity9");
         Double pmrDebt9=(Double) dynaForm.get("pmrDebt9");
 
/*String cpFirstName10=(String) dynaForm.get("cpFirstName10");
         String cpLastName10=(String) dynaForm.get("cpLastName10");
         String cpITPAN10=(String) dynaForm.get("cpITPAN10");
         Date cpDOB10=(Date) dynaForm.get("cpDOB10");
         String cpGender10=(String) dynaForm.get("cpGender10");
         String socialCategory10=(String) dynaForm.get("socialCategory10");
         String email10=(String) dynaForm.get("email10");
        String adhar10=(String) dynaForm.get("adhar10");
        Double pmrEquity10=(Double) dynaForm.get("pmrEquity10");
         Double pmrDebt10=(Double) dynaForm.get("pmrDebt10");
			 */
			 
         double landValue = ((Double) dynaForm.get("landValue"))
					.doubleValue();
			double bldgValue = ((Double) dynaForm.get("bldgValue"))
					.doubleValue();
			double machineValue = ((Double) dynaForm.get("machineValue"))
					.doubleValue();
			double assetsValue = ((Double) dynaForm.get("assetsValue"))
					.doubleValue();
			double currentAssetsValue = ((Double) dynaForm
					.get("currentAssetsValue")).doubleValue();
			double othersValue = ((Double) dynaForm.get("othersValue"))
					.doubleValue();
			String securityRemarks = ((String) dynaForm.get("securityRemarks"));
			String CGPAN = ((String) dynaForm.get("cgpan"));
			
			
			/*double sanctionedAmount = 0.0D;
			
			String tcg = dynaForm.get("sanctionedAmount").toString();
			if ((tcg.equals(null)) || (tcg.equals("")))
				sanctionedAmount = 0.0D;
			else {
				sanctionedAmount = ((Double) dynaForm
						.get("sanctionedAmount")).doubleValue();
			}*/
		
		  
			 
		application.setLandValue(landValue);
		application.setMachineValue(machineValue);
		application.setBldgValue(bldgValue);
		application.setAssetsValue(assetsValue);
		application.setCurrentAssetsValue(currentAssetsValue);
		application.setOthersValue(othersValue);
		application.setSecurityRemarks(securityRemarks);
		application.setCgpan(CGPAN);
		//application.setSanctionedAmount(sanctionedAmount);
			 
			 
			 //System.out.println(hybridSecurity+"-"+movColSecurityAmt+"-action-"+immovColSecurityAmt+"-"+totalMIcolSecAmt+"-");
			 application.setHybridSecurity(hybridSecurity);
			 application.setSanctionTenure(sanctionTenure);
			// application.setDPDStatus(DPDStatus);
			// application.setTypeLoanTC(TypeLoanTC);
			 //application.setTypeLoanTC(TypeLoanWC);
			 //application.setMovCollateratlSecurityAmt(movColSecurityAmt);
			 //application.setImmovCollateratlSecurityAmt(immovColSecurityAmt);
			 //application.setTotalMIcollatSecAmt(totalMIcolSecAmt);
			 application.setProMobileNo(proMobileNo);
			 application.setBorrowergstNo(borrowerGstNo);
			 application.setSancAmt(sancAmt);
			 application.setWcSancAmt(wcSancAmt);
			 application.setOtherSancAmt(otherSancAmt);
			 application.setOutstandingAmt(outstandingAmt);
			 application.setWcOutstandingAmt(wcOutstandingAmt);
			 application.setOtherOutstandingAmt(otherOutstandingAmt);
			 /*application.setOtherBanksancAmt(otherBanksancAmt);
			 application.setOtherBankwcSancAmt(otherBankwcSancAmt);
			 application.setOtherBankotherSancAmt(otherBankotherSancAmt);
			 application.setOtherBankoutstandingAmt(otherBankoutstandingAmt);
			 application.setOtherBankwcOutstandingAmt(otherBankwcOutstandingAmt);
			 application.setOtherBankotherOutstandingAmt(otherBankotherOutstandingAmt);*/
			 application.setAccStatus(accStatus);
			 application.setWcAccStatus(wcAccStatus);
			 application.setOtherAccStatus(otherAccStatus);
			 application.setOtherBankaccStatus(otherBankaccStatus);
			 application.setOtherBankwcAccStatus(otherBankwcAccStatus);
			 application.setOtherBankotherAccStatus(otherBankotherAccStatus);
			 application.setPmrEquity(pmrEquity);
			 application.setPmrDebt(pmrDebt);
			 application.setIsAcctReconstructed(isAcctReconstructed);
			 application.setNpaDate(npaDate);
			 application.setNpaReason(npaReason);
			 application.setFacilityOthrtBnk(facilityOthrtBnk);
			 application.setCpFirstName(cpFirstName);
			 application.setCpLastName(cpLastName);
			 application.setCpDOB(cpDOB);
			 application.setCpGender(cpGender);
			 application.setSocialCategory(socialCategory);
			 application.setEmail(email);
			 
			 application.setCpFirstName1(cpFirstName1);
			 application.setCpLastName1(cpLastName1);
			 application.setCpDOB1(cpDOB1);
			 application.setCpGender1(cpGender1);
			 application.setSocialCategory1(socialCategory1);
			 application.setEmail1(email1);
			 application.setAdhar1(adhar1);
			 application.setPmrEquity1(pmrEquity1);
			 application.setPmrDebt1(pmrDebt1);
			 
			 
			 application.setCpFirstName2(cpFirstName2);
			 application.setCpLastName(cpLastName);
			 application.setCpDOB2(cpDOB2);
			 application.setCpGender2(cpGender2);
			 application.setSocialCategory2(socialCategory2);
			 application.setEmail2(email2);
			 application.setAdhar2(adhar2);
			 application.setPmrEquity2(pmrEquity2);
			 application.setPmrDebt2(pmrDebt2);
			 
			 
			 
			 application.setCpFirstName3(cpFirstName3);
			 application.setCpLastName3(cpLastName3);
			 application.setCpDOB3(cpDOB3);
			 application.setCpGender3(cpGender3);
			 application.setSocialCategory3(socialCategory3);
			 application.setEmail3(email3);
			 application.setAdhar3(adhar3);
			 application.setPmrEquity3(pmrEquity3);
			 application.setPmrDebt3(pmrDebt3);
			 
			 
			 application.setCpFirstName4(cpFirstName4);
			 application.setCpLastName4(cpLastName4);
			 application.setCpDOB4(cpDOB4);
			 application.setCpGender4(cpGender4);
			 application.setSocialCategory4(socialCategory4);
			 application.setEmail4(email4);
			 application.setAdhar4(adhar4);
			 application.setPmrEquity4(pmrEquity4);
			 application.setPmrDebt4(pmrDebt4);
			 
			 
			 application.setCpFirstName5(cpFirstName5);
			 application.setCpLastName5(cpLastName5);
			 application.setCpDOB5(cpDOB5);
			 application.setCpGender5(cpGender5);
			 application.setSocialCategory5(socialCategory5);
			 application.setEmail5(email5);
			 application.setAdhar5(adhar5);
			 application.setPmrEquity5(pmrEquity5);
			 application.setPmrDebt5(pmrDebt5);
			 
			 
			 application.setCpFirstName6(cpFirstName6);
			 application.setCpLastName6(cpLastName6);
			 application.setCpDOB6(cpDOB6);
			 application.setCpGender6(cpGender6);
			 application.setSocialCategory6(socialCategory6);
			 application.setEmail6(email6);
			 application.setAdhar6(adhar6);
			 application.setPmrEquity6(pmrEquity6);
			 application.setPmrDebt6(pmrDebt6);
			 
			 
			 application.setCpFirstName7(cpFirstName7);
			 application.setCpLastName7(cpLastName7);
			 application.setCpDOB7(cpDOB7);
			 application.setCpGender7(cpGender7);
			 application.setSocialCategory7(socialCategory7);
			 application.setEmail7(email7);
			 application.setAdhar7(adhar7);
			 application.setPmrEquity7(pmrEquity7);
			 application.setPmrDebt7(pmrDebt7);
			 
			 
			 application.setCpFirstName8(cpFirstName8);
			 application.setCpLastName8(cpLastName8);
			 application.setCpDOB8(cpDOB8);
			 application.setCpGender8(cpGender8);
			 application.setSocialCategory8(socialCategory8);
			 application.setEmail8(email8);
			 application.setAdhar8(adhar8);
			 application.setPmrEquity8(pmrEquity8);
			 application.setPmrDebt8(pmrDebt8);
			 
			 
			 application.setCpFirstName9(cpFirstName9);
			 application.setCpLastName9(cpLastName9);
			 application.setCpDOB9(cpDOB9);
			 application.setCpGender9(cpGender9);
			 application.setSocialCategory9(socialCategory9);
			 application.setEmail9(email9);
			 application.setAdhar9(adhar9);
			 application.setPmrEquity9(pmrEquity9);
			 application.setPmrDebt9(pmrDebt9);
			 
			 
			/* application.setCpFirstName10(cpFirstName10);
			 application.setCpLastName10(cpLastName10);
			 application.setCpDOB10(cpDOB10);
			 application.setCpGender10(cpGender10);
			 application.setSocialCategory10(socialCategory10);
			 application.setEmail10(email10);
			 application.setAdhar10(adhar10);
			 application.setPmrEquity10(pmrEquity10);
			 application.setPmrDebt10(pmrDebt10);*/
			 
			 // END Hybrid Security
			double creditGuaranteed = 0.0D;
			double creditFundBased = 0.0D;
			double creditNonFundBased = 0.0D;
			String type = (String) dynaForm.get("loanType");
			//========================== 10000000
			
			
			
		
				
		 if (type.equals("TC")) {
				creditGuaranteed = application.getTermLoan().getCreditGuaranteed();
				//System.out.println("creditGuaranteed TC=="+creditGuaranteed);
				/*if(creditGuaranteed >= 10000000 && creditGuaranteed <= 20000000) {
				     applicationSession.setAttribute("financialUIflag", "N");
				}*/
			

		
			 //Riyaz1 
			double credittoguaranteeamount = creditGuaranteed + creditFundBased+ creditNonFundBased;

			double minValue = 999.0D;
			double handloomMaxValue = 50000000.0D;
			double maxValue = 50000000.0D;
			paramMaster = admin.getParameter();
			maxValue = paramMaster.getMaxApprovedAmt();
			double rrbValue = 50000000.0D;
			String classificationofMLI = applicaitonDAO
					.getClassificationMLI(bankId);

			
			
			
			
			String ssiITPan=(String) dynaForm.get("ssiITPan");
			//String cpITPAN=(String) dynaForm.get("cpITPAN");
			String udyogAdharNo=(String) dynaForm.get("udyogAdharNo");
			//String adhar=(String) dynaForm.get("adhar");
			
			/*
			 * if((ssiITPan!=null|| ssiITPan.equals("")) || (cpITPAN!=null||
			 * cpITPAN.equals("")) || (udyogAdharNo!=null||
			 * udyogAdharNo.equals(""))||(adhar!=null|| adhar.equals(""))) { int dupItpan =
			 * appProcessor.getDupItpanCount(ssiITPan,cpITPAN); if (dupItpan > 0) {
			 * 
			 * throw new MessageException(
			 * "Duplicate ITPAN. Application has been Rejected"); } }
			 */

			if (credittoguaranteeamount < minValue) {
				throw new DatabaseException(
						"Credit to be Guaranteed2 Amount should be within the eligible amount available for Guarantee upto" + 25000000);
			}
			if (credittoguaranteeamount > maxValue) {
				throw new DatabaseException(
						"Credit to be Guaranteed3 Amount should be within the eligible amount available for Guarantee upto" + 50000000);
			}

			BeanUtils.populate(application, dynaForm.getMap());

			if ((dcHandlooms != null) && (!dcHandlooms.equals("null"))) {
				if (dcHandlooms.equals("N")) {
					application.setHandloomchk("N");
					application.setWeaverCreditScheme("");
				}
			}
			if ((handiCrafts != null) && (!handiCrafts.equals("null"))) {
				if (handiCrafts.equals("N")) {
					application.setDcHandicrafts("N");
				}
			}
			if (dynaForm.get("none").equals("cgpan")) {
				application.setCgpan((String) dynaForm.get("unitValue"));
			} else if (dynaForm.get("none").equals("cgbid")) {
				ArrayList borrowerIds = cpProcessor.getAllBorrowerIDs(mliId);
				if (!borrowerIds.contains(dynaForm.get("unitValue"))) {
					throw new NoDataException(
							"The Borrower ID does not exist for this Member ID");
				}

				application.getBorrowerDetails().getSsiDetails()
						.setCgbid((String) dynaForm.get("unitValue"));
			}

			Log.log(4, "ApplicationProcessingAction", "submitApp",
					"application type :" + applicationType);

			if (applicationSession.getAttribute("MCGF_FLAG").equals("M")) {
				application.setScheme("MCGS");

				if (applicationSession.getAttribute("ssiRefNumber") != null) {
					Log.log(4,
							"ApplicationProcessingAction",
							"submitApp",
							"applicationSession.getAttribute ssiRefNumber"
									+ applicationSession
											.getAttribute("ssiRefNumber"));

					double corpusContAmt = appProcessor
							.getCorpusContAmt(((Integer) applicationSession
									.getAttribute("ssiRefNumber")).intValue());
					double totalCorpusContAmt = corpusContAmt * 5.0D;

					double creditAmount = 0.0D;
					if (application.getLoanType().equals("TC")) {
						creditAmount = application.getTermLoan()
								.getCreditGuaranteed();
					} else if (application.getLoanType().equals("CC")) {
						creditAmount = application.getTermLoan()
								.getCreditGuaranteed()
								+ application.getWc().getCreditFundBased()
								+ application.getWc().getCreditNonFundBased();
					} else if (application.getLoanType().equals("WC")) {
						creditAmount = application.getWc().getCreditFundBased()
								+ application.getWc().getCreditNonFundBased();
					}

					if (totalCorpusContAmt != 0.0D) {
						if (creditAmount > totalCorpusContAmt) {
							throw new MessageException(
									"Credit Limit should not exceed the Corpus Amount");
						}

					}

					BorrowerDetails borrowerDtlTemp = application
							.getBorrowerDetails();
					SSIDetails ssiDtlTemp = borrowerDtlTemp.getSsiDetails();
					ssiDtlTemp.setBorrowerRefNo(((Integer) applicationSession
							.getAttribute("ssiRefNumber")).intValue());
					borrowerDtlTemp.setSsiDetails(ssiDtlTemp);
					application.setBorrowerDetails(borrowerDtlTemp);
				}
			} else {
				application.setScheme("CGFSI");
			}

			
				// Milind 24-02-2016 start
				//if (appProcessor.checkDublicateRecordAppPro(application,
					//	termLoan, ssiDetails, userId, mliId) == 0) {
					
					

					application = appProcessor.submitNewApplication(application, userId); // *********** @ DUPLIACATE ******************
													// DKR
													// ****FINAL******************

					String appRefNo = application.getAppRefNo();
					LogClass.StepWritter("In else appRefNo is " + appRefNo);
					dynaForm.set("appRefNo", appRefNo);
					//int borrowerRefNo = application.getBorrowerDetails()
						//	.getSsiDetails().getBorrowerRefNo();
				//	Integer refNoValue = new Integer(borrowerRefNo);
					//dynaForm.set("borrowerRefNo", refNoValue);
					request.setAttribute("message","Application (Reference No:" + appRefNo+ ") Submitted Successfully.\n Please follow the path: Reports and MIS --> Application Status for CGPAN."); 
						

					if (applicationLoanType.equals("BO")) {
						String wcAppRefNo = application.getWcAppRefNo();
						LogClass.StepWritter(" applicationType=BO wcAppRefNo is "
								+ wcAppRefNo);
						dynaForm.set("wcAppRefNo", wcAppRefNo);
						request.setAttribute("message",
								"Your guarantee application has been approved and CGPAN is:" + wcAppRefNo
										+ "," + appRefNo
										+ ")Submitted Successfully");
					}

					successPage = "success";
				//}// else {
					//successPage = "doubleClickHapped";
				//}
				// Milind 24-02-2016 end
			}
			application = null;
			appProcessor = null;
			ssiDetails = null;
			borrowerDetails = null;
			termLoan = null;
			workingCapital = null;
			primarySecurityDetails = null;
			projectOutlayDetails = null;
			securitization = null;
			mcgfDetails = null;
			user = null;
			userId = null;
			bankId = null;
			zoneId = null;
			branchId = null;
			applicationSession.setAttribute("financialUIflag", "N");
			// }
			// else
			// {
			//  successPage="doubleClickHapped";
			// }
			LogClass.StepWritter(" submitApp end here ");
		} catch (Exception e) {
			e.printStackTrace();
			LogClass.writeExceptionOnFile(e);
			throw new NoDataException(e.getMessage());
		}
	 }else{		  
	  request.getSession().invalidate();
	  response.sendRedirect(request.getContextPath() + "/showLogin");
	}
	return mapping.findForward(successPage);
	}
	
	
	//DIKSHA MUDRA LOGIC
	
	
	public ActionForward submitMudraApp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		   Log.log(4, "ApplicationProcessingAction", "submitApp", "Entered");		
		   String successPage = "";
		   /* String csrfCode = request.getParameter("csrfCode");
		    //System.out.println("csrfCode :::::  " + csrfCode);
		    String target="";
		    boolean check=CSRFUtil.getCSRFflag(csrfCode, request.getSession(false), request);
		    //System.out.println("check=="+check);
		    if(csrfCode != null && !"".equals(csrfCode) && check){*/
		try {
			
			// if (isTokenValid(request)) {
			// resetToken(request);
			DynaActionForm dynaForm = (DynaActionForm) form;
			LogClass.StepWritter(" submitApp start from here and loan type is "
					+ dynaForm.get("loanType"));
			String primarySercurity = (String) dynaForm.get("pSecurity");
			//System.out.println("Primary Security : " + primarySercurity);
		
			HttpSession applicationSession = request.getSession(false);
			applicationSession.setAttribute("hybridUIflag","DTRUE");
			//applicationSession.setAttribute("financialUIflag", "DFTRUE");
			Log.log(4, "ApplicationProcessingAction", "submitApp",
					"Creating Objects");


			ApplicationProcessor appProcessor = new ApplicationProcessor();
			ApplicationDAO applicaitonDAO = new ApplicationDAO();
			Application application = new Application();
			BorrowerDetails borrowerDetails = new BorrowerDetails();
			SSIDetails ssiDetails = new SSIDetails();
			ProjectOutlayDetails projectOutlayDetails = new ProjectOutlayDetails();
			PrimarySecurityDetails primarySecurityDetails = new PrimarySecurityDetails();
			TermLoan termLoan = new TermLoan();
			WorkingCapital workingCapital = new WorkingCapital();
			Securitization securitization = new Securitization();
			MCGFDetails mcgfDetails = new MCGFDetails();
			String expoid="";
			String Fbchk = "";
			ClaimsProcessor cpProcessor = new ClaimsProcessor();
			User user = getUserInformation(request);
			// bhu
			projectOutlayDetails.setIsPrimarySecurity(primarySercurity);// bhu
			String internalRating = (String) dynaForm.get("internalRating");
			application.setInternalRate(internalRating);
			String handiCrafts = (String) dynaForm.get("handiCrafts");
			 
		
			String dcHandicrafts = (String) dynaForm.get("dcHandicrafts");
			String icardNo = (String) dynaForm.get("icardNo");
			Date icardIssueDate = (Date) dynaForm.get("icardIssueDate");

			String dcHandlooms = (String) dynaForm.get("dcHandlooms");

			String WeaverCreditScheme = (String) dynaForm
					.get("WeaverCreditScheme");
			String handloomchk = (String) dynaForm.get("handloomchk");
			String industrySector = (String) dynaForm.get("industrySector");
			String unitValue = (String) dynaForm.get("unitValue");
			String activityConfirm = (String) dynaForm.get("activityConfirm");

			String gstNo = (String) dynaForm.get("gstNo");
			String gstState = (String) dynaForm.get("gstState");
			 String aadhar=(String)dynaForm.get("adhar");
			 //System.out.println("aadhar==="+aadhar);
			
			// String gstState =request.getParameter("stateCode");

			if ((handiCrafts != null) && (!handiCrafts.equals("null"))) {
				if (handiCrafts.equals("N")) {
					dcHandicrafts = "N";
				}

				if ((WeaverCreditScheme != null)
						&& (!WeaverCreditScheme.equals("null"))) {
					if ((dcHandlooms.equals("N"))
							&& (WeaverCreditScheme.equals("Select"))) {
						WeaverCreditScheme = "";
					}
				}
			}
  			application.setHandiCrafts(handiCrafts);
			application.setDcHandicrafts(dcHandicrafts);

			application.setIcardNo(icardNo);
			application.setIcardIssueDate(icardIssueDate);
			application.setDcHandlooms(dcHandlooms);

			application.setWeaverCreditScheme(WeaverCreditScheme);
			application.setHandloomchk(handloomchk);
			application.setActivityConfirm(activityConfirm);
			String jointFinance = (String) dynaForm.get("jointFinance");
			
			String jointcgpan = (String) dynaForm.get("jointcgpan");
		
			if((String) dynaForm.get("exposureFbId")!=null){
				Fbchk = (String) dynaForm.get("exposureFbId");
			}else{
				Fbchk = "";
 			};
			Log.log(5, "ApplicationProcessingAction", "submitApp", "Fbchk:"	+ Fbchk);
			application.setJointcgpan(jointcgpan);

			Date expiryDate = (Date) dynaForm.get("expiryDate");
			application.setAppExpiryDate(expiryDate);

			String zoneId = "";
			String branchId = "";
			String mliId = "";
			 
			String userId = user.getUserId();
			Log.log(5, "ApplicationProcessingAction", "submitApp", "user Id :"
					+ userId);

			String bankId = user.getBankId();
			zoneId = user.getZoneId();
			branchId = user.getBranchId();
			//---say
		if(Fbchk!=null)
			{
			if(Fbchk.equals("Y1")){				
		    expoid = applicaitonDAO.getExposureID(bankId);				
			}else{
				expoid ="";		
			}
		 }else{
			 expoid ="";		
		 }
			Log.log(5, "ApplicationProcessingAction", "submitApp", "exposure Id :"
					+ expoid);
			
			/*
			 * //Added By Parmanand 1 Start
			 * 
			 * HttpSession session = request.getSession(false); bankId=(String)
			 * session.getAttribute("bankId");
			 * branchId=(String)session.getAttribute("branchId");
			 * zoneId=(String)session.getAttribute("zoneId"); String
			 * bankName=(String)session.getAttribute("bankName"); String
			 * zoneName=(String)session.getAttribute("zoneName"); String
			 * shortName=(String)session.getAttribute("shortName"); String
			 * branchName=(String)session.getAttribute("branchName"); String
			 * ipAddress=(String)session.getAttribute("ipAddress"); String
			 * sessionId=(String)session.getAttribute("sessionId");
			 * userId=(String)session.getAttribute("userId");
			 * 
			 * Log.log(5, "LoginAction", "login",bankId); Log.log(5, "LoginAction",
			 * "login",zoneId); Log.log(5, "LoginAction", "login",branchId); Log.log(5,
			 * "LoginAction", "login",bankName);
			 * 
			 * Log.log(5, "LoginAction", "login",zoneName); Log.log(5, "LoginAction",
			 * "login",shortName); Log.log(5, "LoginAction", "login",branchName); Log.log(5,
			 * "LoginAction", "login",ipAddress); Log.log(5, "LoginAction",
			 * "login",sessionId); Log.log(5, "LoginAction", "login",userId);
			 * 
			 * //Added By Parmanand 1 Start
			 */			  
			application.setExposureFbId(expoid);
			dynaForm.set("exposureFbId", expoid);
//			if(expoid!=null && expoid.equals("")==false){
//				application.setExposureFbId(expoid);	
//		
//			}else{
//				application.setExposureFbId("");		
//			}
			//----------say
			 
				
			if (bankId.equals("0000")) {
				String memberName = (String) dynaForm.get("selectMember");
				if (memberName != null) {
					bankId = memberName.substring(0, 4);
					zoneId = memberName.substring(4, 8);
					branchId = memberName.substring(8, 12);

					application.setBankId(bankId);
					application.setZoneId(zoneId);
					application.setBranchId(branchId);
					mliId = bankId + zoneId + branchId;
					application.setMliID(mliId);
				}

			} else {
				bankId = user.getBankId();
				application.setBankId(bankId);
				zoneId = user.getZoneId();
				application.setZoneId(zoneId);
				branchId = user.getBranchId();
				application.setBranchId(branchId);
				mliId = bankId + zoneId + branchId;
				application.setMliID(mliId);
			}

			double exposureLimit = applicaitonDAO.getExposureLimit(bankId);
			double maxExposureLimit = applicaitonDAO
					.getMaxExposureLimit(bankId);

			if ((exposureLimit > 0.0D)
					&& (exposureLimit / 10000000.0D >= maxExposureLimit)) {
				// throw new
				// DatabaseException("The Maximum Limit of Guarantee Approved Amount should be less than "
				// + maxExposureLimit);
				throw new ArithmeticException(
						(new StringBuilder(
								"The Maximum Limit of Guarantee Approved Amount should be less than "))
								.append(maxExposureLimit).toString());
			}

			dynaForm.set("bankId", bankId);
			dynaForm.set("zoneId", zoneId);
			dynaForm.set("branchId", branchId);
			dynaForm.set("mliID", mliId);
			
		

			String applicationType = "";
			String applicationLoanType = "";

			if (applicationSession.getAttribute("APPLICATION_LOAN_TYPE") == null) {
				applicationType = (String) applicationSession
						.getAttribute("APPLICATION_TYPE");
				Log.log(5, "ApplicationProcessingAction", "submitApp",
						"ApplicationLoan Type :" + applicationType);
			} else {
				applicationLoanType = (String) applicationSession
						.getAttribute("APPLICATION_LOAN_TYPE");
				Log.log(5, "ApplicationProcessingAction", "submitApp",
						"ApplicationLoan Type :" + applicationLoanType);

				application.setLoanType(applicationType);
			}

			
			Log.log(4, "ApplicationProcessingAction", "submitApp",
					"Calling Bean Utils...");

			BeanUtils.populate(ssiDetails, dynaForm.getMap());

			String constitutionValue = (String) dynaForm
					.get("constitutionOther");
			if ((dynaForm.get("constitution") != null)
					&& (!dynaForm.get("constitution").equals(""))) {
				if (dynaForm.get("constitution").equals("Others")) {
					ssiDetails.setConstitution(constitutionValue);
				}

			}

			String districtOthersValue = (String) dynaForm
					.get("districtOthers");
			if ((dynaForm.get("district") != null)
					&& (!dynaForm.get("district").equals(""))) {
				if (dynaForm.get("district").equals("Others")) {
					ssiDetails.setDistrict(districtOthersValue);
				}

			}

			String otherLegalIdValue = (String) dynaForm.get("otherCpLegalID");
			if ((dynaForm.get("cpLegalID") != null)
					&& (!dynaForm.get("cpLegalID").equals(""))) {
				if (dynaForm.get("cpLegalID").equals("Others")) {
					ssiDetails.setCpLegalID(otherLegalIdValue);
				}

			}

			String activityType = (String) dynaForm.get("activityType");
			//System.out.println("activityType===="+activityType);

			
			 if ((dynaForm.get("activityType") != null) &&
			 (!dynaForm.get("activityType").equals(""))) {
			 ssiDetails.setActivityType(activityType); }
			 
			 String mudraType = (String) dynaForm.get("mudraType");
				//System.out.println("mudraType===="+mudraType);

				
				 if ((dynaForm.get("mudraType") != null) &&
				 (!dynaForm.get("mudraType").equals(""))) {
				 ssiDetails.setMudraType(mudraType); }
				 
				 String mudraITPANChk = (String) dynaForm.get("mudraITPANChk");
					//System.out.println("mudraITPANChk===="+mudraITPANChk);

					
					 if ((dynaForm.get("mudraITPANChk") != null) &&
					 (!dynaForm.get("mudraITPANChk").equals(""))) {
					 ssiDetails.setMudraType(mudraType); }
			 
			borrowerDetails.setSsiDetails(ssiDetails);
			BeanUtils.populate(borrowerDetails, dynaForm.getMap());

			BeanUtils.populate(primarySecurityDetails, dynaForm.getMap());
			projectOutlayDetails
					.setPrimarySecurityDetails(primarySecurityDetails);
			BeanUtils.populate(projectOutlayDetails, dynaForm.getMap());

			BeanUtils.populate(termLoan, dynaForm.getMap());
			BeanUtils.populate(securitization, dynaForm.getMap());
			BeanUtils.populate(workingCapital, dynaForm.getMap());

			if (applicationSession.getAttribute("MCGF_FLAG").equals("M")) {
				BeanUtils.populate(mcgfDetails, dynaForm.getMap());
				application.setMCGFDetails(mcgfDetails);
			}

			application.setBorrowerDetails(borrowerDetails);
			String otherSubsidyNameValue = (String) dynaForm
					.get("otherSubsidyEquityName");
			if ((dynaForm.get("subsidyName") != null)
					&& (!dynaForm.get("subsidyName").equals(""))) {
				if (dynaForm.get("subsidyName").equals("Others")) {
					projectOutlayDetails.setSubsidyName(otherSubsidyNameValue);
				}

			}
			
			double termCreditSanctioned = ((Double) dynaForm
					.get("termCreditSanctioned")).doubleValue();
			double pplOS = ((Double) dynaForm
					.get("pplOS")).doubleValue();
		//application.getTermLoan().setPplOS(pplOS);
		
		long longTermCreditSanctioned = Math.round((Double) dynaForm
					.get("termCreditSanctioned"));
			//System.out.println("termCreditSanctioned=" + termCreditSanctioned);
			double tcPromoterContribution = ((Double) dynaForm
					.get("tcPromoterContribution")).doubleValue();
			double tcSubsidyOrEquity = ((Double) dynaForm
					.get("tcSubsidyOrEquity")).doubleValue();
			double tcOthers = ((Double) dynaForm.get("tcOthers")).doubleValue();
			double wcFundBasedSanctioned = ((Double) dynaForm
					.get("wcFundBasedSanctioned")).doubleValue();
			long longwcFundBasedSanctioned = Math.round(wcFundBasedSanctioned);
			double wcNonFundBasedSanctioned = ((Double) dynaForm
					.get("wcNonFundBasedSanctioned")).doubleValue();
			long longwcNonFundBasedSanctioned = Math
					.round(wcNonFundBasedSanctioned);
			double wcPromoterContribution = ((Double) dynaForm
					.get("wcPromoterContribution")).doubleValue();
			double wcSubsidyOrEquity = ((Double) dynaForm
					.get("wcSubsidyOrEquity")).doubleValue();
			double wcOthers = ((Double) dynaForm.get("wcOthers")).doubleValue();
			double projectOutlayVal = termCreditSanctioned
					+ tcPromoterContribution + tcSubsidyOrEquity + tcOthers
					+ wcFundBasedSanctioned + wcNonFundBasedSanctioned
					+ wcPromoterContribution + wcSubsidyOrEquity + wcOthers;

			Double projectOutlayCost = new Double(projectOutlayVal);
			double projectOutlay = projectOutlayCost.doubleValue();
			projectOutlayDetails.setProjectOutlay(projectOutlay);

			application.setProjectOutlayDetails(projectOutlayDetails);
			application.setTermLoan(termLoan);
			application.setWc(workingCapital);
			application.setSecuritization(securitization);
			application.setGstState(gstState);
			application.setGstNo(gstNo);
		    application.setAdharNo(aadhar);
		   
		 // Added by DKR Hybrid Security
			 String hybridSecurity = (String) dynaForm.get("hybridSecurity");
			 Integer sanctionTenure=(Integer) dynaForm.get("sanctionTenure");
			 Integer DPDStatus=(Integer) dynaForm.get("DPDStatus");
			 Double TypeLoanTC=(Double)dynaForm.get("TypeLoanTC");
			 Double TypeLoanWC=(Double)dynaForm.get("TypeLoanWC");
			 String borrowerGstNo=(String)dynaForm.get("BorrowergstNo");
			 application.getBorrowerDetails().getSsiDetails()
				.setMSE((String) dynaForm.get("mSE"));	
			 
			 Double movColSecurityAmt = (Double) dynaForm.get("movCollateratlSecurityAmt");
			 Double immovColSecurityAmt = (Double) dynaForm.get("immovCollateratlSecurityAmt");
			 Double totalMIcolSecAmt = (Double) dynaForm.get("totalMIcollatSecAmt");
			 Long proMobileNo = (Long) dynaForm.get("proMobileNo");
			 //System.out.println(hybridSecurity+"-"+movColSecurityAmt+"-action-"+immovColSecurityAmt+"-"+totalMIcolSecAmt+"-");
			 application.setHybridSecurity(hybridSecurity);
			 application.setSanctionTenure(sanctionTenure);
			 application.setDPDStatus(DPDStatus);
			 application.setTypeLoanTC(TypeLoanTC);
			 application.setTypeLoanTC(TypeLoanWC);
			 application.setMovCollateratlSecurityAmt(movColSecurityAmt);
			 application.setImmovCollateratlSecurityAmt(immovColSecurityAmt);
			 application.setTotalMIcollatSecAmt(totalMIcolSecAmt);
			 application.setProMobileNo(proMobileNo);
			 application.setBorrowergstNo(borrowerGstNo);
			 // END Hybrid Security
			double creditGuaranteed = 0.0D;
			double creditFundBased = 0.0D;
			double creditNonFundBased = 0.0D;
			String type = (String) dynaForm.get("loanType");
			//========================== 10000000
			if (application.getLoanType().equals("TC") || application.getLoanType().equals("ML") || (application.getLoanType().equals("BO") || application.getLoanType().equals("CC")))
			  { //  DKR 32 Col 
				 String promDirDefaltFlg=(String)dynaForm.get("promDirDefaltFlg"); 	
				 
				 //System.out.println("promDirDefaltFlg........................"+promDirDefaltFlg);
				 
				 
			     int credBureKeyPromScor=(Integer)dynaForm.get("credBureKeyPromScor"); 
			     int credBurePromScor2=(Integer)dynaForm.get("credBurePromScor2"); 	
			     int credBurePromScor3=(Integer)dynaForm.get("credBurePromScor3"); 	
			     int credBurePromScor4=(Integer)dynaForm.get("credBurePromScor4"); 
			     int credBurePromScor5=(Integer)dynaForm.get("credBurePromScor5"); 
			     String credBureName1=(String)dynaForm.get("credBureName1");      
			     String credBureName2=(String)dynaForm.get("credBureName2");      
			     String credBureName3=(String)dynaForm.get("credBureName3");      
			     String credBureName4=(String)dynaForm.get("credBureName4");      
			     String credBureName5=(String)dynaForm.get("credBureName5");      
			     int cibilFirmMsmeRank=(Integer)dynaForm.get("cibilFirmMsmeRank"); 
			     int expCommerScor=(Integer)dynaForm.get("expCommerScor");     
			     float promBorrNetWorth=(Float)dynaForm.get("promBorrNetWorth"); 	 
			     int promContribution=(Integer)dynaForm.get("promContribution"); 	
			     String promGAssoNPA1YrFlg=(String)dynaForm.get("promGAssoNPA1YrFlg"); 
			     int promBussExpYr=(Integer)dynaForm.get("promBussExpYr");		
			     float salesRevenue=(Float)dynaForm.get("salesRevenue");	 	  
			     float taxPBIT=(Float)dynaForm.get("taxPBIT");			  
			     float interestPayment=(Float)dynaForm.get("interestPayment"); 	  
			     float taxCurrentProvisionAmt=(Float)dynaForm.get("taxCurrentProvisionAmt"); 
			     float totCurrentAssets=(Float)dynaForm.get("totCurrentAssets"); 	  
			     float totCurrentLiability=(Float)dynaForm.get("totCurrentLiability");  
			     float totTermLiability=(Float)dynaForm.get("totTermLiability"); 	  
			     float exuityCapital =(Float)dynaForm.get("exuityCapital"); 		  
			     float preferenceCapital=(Float)dynaForm.get("preferenceCapital");	  
			     float reservesSurplus=(Float)dynaForm.get("reservesSurplus"); 	  
			     float repaymentDueNyrAmt=(Float)dynaForm.get("repaymentDueNyrAmt");
			     
			     String existGreenFldUnitType = (String) dynaForm.get("existGreenFldUnitType");
			     float opratIncome=(Float) dynaForm.get("opratIncome");               			
			     float profAftTax=(Float) dynaForm.get("profAftTax");                           
			     float networth=(Float) dynaForm.get("networth"); 
			      int debitEqtRatioUnt=(Integer) dynaForm.get("debitEqtRatioUnt");
			      int debitSrvCoverageRatioTl=(Integer) dynaForm.get("debitSrvCoverageRatioTl");	
			      int currentRatioWc=(Integer) dynaForm.get("currentRatioWc");	
			      int debitEqtRatio=(Integer) dynaForm.get("debitEqtRatio");	
			      int debitSrvCoverageRatio=(Integer) dynaForm.get("debitSrvCoverageRatio");		
			      int currentRatios=(Integer) dynaForm.get("currentRatios");   					
			      int creditBureauChiefPromScor=(Integer) dynaForm.get("creditBureauChiefPromScor");			
			    float totalAssets=(Float) dynaForm.get("totalAssets");	
			     
			     application.setPromDirDefaltFlg(promDirDefaltFlg); 	
			     application.setCredBureKeyPromScor(credBureKeyPromScor); 
			     application.setCredBurePromScor2(credBurePromScor2); 	
			     application.setCredBurePromScor3(credBurePromScor3);	
			     application.setCredBurePromScor4(credBurePromScor4); 
			     application.setCredBurePromScor5(credBurePromScor5); 
			     application.setCredBureName1(credBureName1);      
			     application.setCredBureName2(credBureName2);      
			     application.setCredBureName3(credBureName3);		     
			     application.setCredBureName4(credBureName4);      
			     application.setCredBureName5(credBureName5);      
			     application.setCibilFirmMsmeRank(cibilFirmMsmeRank); 
			     application.setExpCommerScor(expCommerScor);     
			     application.setPromBorrNetWorth(promBorrNetWorth); 	 
			     application.setPromContribution(promContribution);	
			     application.setPromGAssoNPA1YrFlg(promGAssoNPA1YrFlg); 
			     application.setPromBussExpYr(promBussExpYr);				     
			 	 application.setSalesRevenue(salesRevenue);											
			 	 application.setTaxPBIT(taxPBIT);													
			 	 application.setInterestPayment(interestPayment);									
			 	 application.setTaxCurrentProvisionAmt(taxCurrentProvisionAmt);						
			 	 application.setTotCurrentAssets(totCurrentAssets);									
			 	 application.setTotCurrentLiability(totCurrentLiability);							
			 	 application.setTotTermLiability(totTermLiability);									
			 	 application.setExuityCapital(exuityCapital);										
			 	 application.setPreferenceCapital(preferenceCapital);								
			 	 application.setReservesSurplus(reservesSurplus);									
			 	 application.setRepaymentDueNyrAmt(repaymentDueNyrAmt);
			 	
			 	    application.setExistGreenFldUnitType(existGreenFldUnitType);
					application.setOpratIncome(opratIncome);
					application.setProfAftTax(profAftTax); 
					application.setNetworth(networth);
					application.setDebitEqtRatioUnt(debitEqtRatioUnt); 
					application.setDebitSrvCoverageRatioTl(debitSrvCoverageRatioTl);
					application.setCurrentRatioWc(currentRatioWc); 
					application.setDebitEqtRatio(debitEqtRatio); 
					application.setDebitSrvCoverageRatio(debitSrvCoverageRatio);
					application.setCurrentRatios(currentRatios);
					application.setCreditBureauChiefPromScor(creditBureauChiefPromScor);
					application.setTotalAssets(totalAssets); 
			  }
				// END 32 & 8 Col			
		//==========================
			
			
			
			
			if (type.equals("BO")) {
				creditGuaranteed = application.getTermLoan()
						.getCreditGuaranteed();
				creditFundBased = application.getWc().getCreditFundBased();
				creditNonFundBased = application.getWc()
						.getCreditNonFundBased();
			} else if (type.equals("CC")) {
				//Parmanand3
				creditGuaranteed = application.getTermLoan()
						.getCreditGuaranteed();
				creditFundBased = application.getWc().getCreditFundBased();
				creditNonFundBased = application.getWc()
						.getCreditNonFundBased();
				
				/*if((creditGuaranteed + creditFundBased + creditNonFundBased) >= 10000000 && (creditGuaranteed + creditFundBased + creditNonFundBased) <= 20000000) {
				     applicationSession.setAttribute("financialUIflag", "DFTRUE");
				}else {
					  applicationSession.setAttribute("financialUIflag", "N");
				}*/
				
			} else if (type.equals("ML")) {
				creditGuaranteed = application.getTermLoan().getCreditGuaranteed();
				//System.out.println("creditGuaranteed TC=="+creditGuaranteed);
				/*if(creditGuaranteed >= 10000000 && creditGuaranteed <= 20000000) {
				     applicationSession.setAttribute("financialUIflag", "N");
				}*/
			} else if (type.equals("WC")) {
				/*
				 * creditFundBased = application.getWc().getCreditFundBased();
				 * creditNonFundBased = application.getWc().getCreditNonFundBased();
				 */
				
				creditGuaranteed = application.getTermLoan().getCreditGuaranteed();
				//System.out.println("creditGuaranteed TC=="+creditGuaranteed);
				
				/*if((creditFundBased + creditNonFundBased) >= 10000000 && (creditFundBased + creditNonFundBased) <= 20000000) {
				     applicationSession.setAttribute("financialUIflag", "N");
				}*/
			}

		
			 //Riyaz1 
			double credittoguaranteeamount = creditGuaranteed + creditFundBased+ creditNonFundBased;

			double minValue = 999.0D;
			double handloomMaxValue = 200000.0D;
			double maxValue = 200000.0D;
			paramMaster = admin.getParameter();
			maxValue = paramMaster.getMudraMaxAmt();
			double rrbValue = 200000.0D;
			String classificationofMLI = applicaitonDAO
					.getClassificationMLI(bankId);

			
			
			/*
			 * if ((dcHandlooms != null) && (!dcHandlooms.equals("null")) &&
			 * (unitValue.equals(""))) { if ((dcHandlooms.equals("Y")) &&
			 * ((WeaverCreditScheme == null) || (WeaverCreditScheme .equals("Select")))) {
			 * throw new DatabaseException( "Please Select the Weaver Credit Scheme "); }
			 * 
			 * if ((!industrySector.equals("")) && (dcHandlooms.equals("Y")) &&
			 * (!industrySector.equals("HANDLOOM WEAVING"))) { throw new DatabaseException(
			 * "Reimbursement of GF/ASF under DC(HL) is eligible only if Industry Sector/Activity of the borrower is 'handloom weaving'. Please refer our Circular No: 61/2012-13 dated 12/06/2012 for details. "
			 * ); }
			 * 
			 * }
			 * 
			 * if (((classificationofMLI.equals("RRB")) || (classificationofMLI == "RRB"))
			 * && (credittoguaranteeamount > rrbValue)) { throw new DatabaseException(
			 * "Maximum ‘credit to be guaranteed’ amount per eligible borrower for RRBs is caped at Rs.50 lakh. Please refer our <b> Circular No. 50 / 2008 – 09 </b> dated January 07, 2009  "
			 * ); }
			 * 
			 * if ((dcHandlooms != null) && (!dcHandlooms.equals("null"))) { if
			 * ((credittoguaranteeamount > handloomMaxValue) && (dcHandlooms.equals("Y"))) {
			 * throw new DatabaseException(
			 * "Credit to be Guaranteed Amount should be within the eligible amount available for Guarantee upto  Rs. 200000 as per ceiling fixed by Office of DC Handlooms"
			 * ); } }
			 */
//diksha Dup ITPAN
			
			String ssiITPan=(String) dynaForm.get("ssiITPan");
			String cpITPAN=(String) dynaForm.get("cpITPAN");
			String udyogAdharNo=(String) dynaForm.get("udyogAdharNo");
			String adhar=(String) dynaForm.get("adhar");
			
			/*
			 * if((ssiITPan!=null|| ssiITPan.equals("")) || (cpITPAN!=null||
			 * cpITPAN.equals("")) || (udyogAdharNo!=null||
			 * udyogAdharNo.equals(""))||(adhar!=null|| adhar.equals(""))) { int dupItpan =
			 * appProcessor.getDupItpanCount(ssiITPan,cpITPAN); if (dupItpan > 0) {
			 * 
			 * throw new MessageException(
			 * "Duplicate ITPAN. Application has been Rejected"); } }
			 */

			if (credittoguaranteeamount < minValue) {
				throw new DatabaseException(
						"Credit to be Guaranteed2 Amount should be within the eligible amount available for Guarantee upto" + 25000000);
			}
			if (credittoguaranteeamount > maxValue) {
				throw new DatabaseException(
						"Credit to be Guaranteed3 Amount should be within the eligible amount available for Guarantee upto" + 50000000);
			}

			BeanUtils.populate(application, dynaForm.getMap());

			if ((dcHandlooms != null) && (!dcHandlooms.equals("null"))) {
				if (dcHandlooms.equals("N")) {
					application.setHandloomchk("N");
					application.setWeaverCreditScheme("");
				}
			}
			if ((handiCrafts != null) && (!handiCrafts.equals("null"))) {
				if (handiCrafts.equals("N")) {
					application.setDcHandicrafts("N");
				}
			}
			if (dynaForm.get("none").equals("cgpan")) {
				application.setCgpan((String) dynaForm.get("unitValue"));
			} else if (dynaForm.get("none").equals("cgbid")) {
				ArrayList borrowerIds = cpProcessor.getAllBorrowerIDs(mliId);
				if (!borrowerIds.contains(dynaForm.get("unitValue"))) {
					throw new NoDataException(
							"The Borrower ID does not exist for this Member ID");
				}

				application.getBorrowerDetails().getSsiDetails()
						.setCgbid((String) dynaForm.get("unitValue"));
			}

			Log.log(4, "ApplicationProcessingAction", "submitApp",
					"application type :" + applicationType);

			if (applicationSession.getAttribute("MCGF_FLAG").equals("M")) {
				application.setScheme("MCGS");

				if (applicationSession.getAttribute("ssiRefNumber") != null) {
					Log.log(4,
							"ApplicationProcessingAction",
							"submitApp",
							"applicationSession.getAttribute ssiRefNumber"
									+ applicationSession
											.getAttribute("ssiRefNumber"));

					double corpusContAmt = appProcessor
							.getCorpusContAmt(((Integer) applicationSession
									.getAttribute("ssiRefNumber")).intValue());
					double totalCorpusContAmt = corpusContAmt * 5.0D;

					double creditAmount = 0.0D;
					if (application.getLoanType().equals("TC")) {
						creditAmount = application.getTermLoan()
								.getCreditGuaranteed();
					} else if (application.getLoanType().equals("CC")) {
						creditAmount = application.getTermLoan()
								.getCreditGuaranteed()
								+ application.getWc().getCreditFundBased()
								+ application.getWc().getCreditNonFundBased();
					} else if (application.getLoanType().equals("WC")) {
						creditAmount = application.getWc().getCreditFundBased()
								+ application.getWc().getCreditNonFundBased();
					}

					if (totalCorpusContAmt != 0.0D) {
						if (creditAmount > totalCorpusContAmt) {
							throw new MessageException(
									"Credit Limit should not exceed the Corpus Amount");
						}

					}

					BorrowerDetails borrowerDtlTemp = application
							.getBorrowerDetails();
					SSIDetails ssiDtlTemp = borrowerDtlTemp.getSsiDetails();
					ssiDtlTemp.setBorrowerRefNo(((Integer) applicationSession
							.getAttribute("ssiRefNumber")).intValue());
					borrowerDtlTemp.setSsiDetails(ssiDtlTemp);
					application.setBorrowerDetails(borrowerDtlTemp);
				}
			} else {
				application.setScheme("CGFSI");
			}

			if (applicationType.equals("MA")) {
				application.setCgpanReference("");

				appProcessor.updateApplication(application, userId);
				Log.log(4, "ApplicationProcessingAction", "submitApp",
						"After updating....");

				request.setAttribute("message",
						"Application Modified Successfully");

				successPage = "success";
			} else if (applicationType.equals("TCE")) {
				if (dynaForm.get("none").equals("cgpan")) {
					String cgpanRef = (String) dynaForm.get("unitValue");
					application.setCgpanReference(cgpanRef);
				} else if (dynaForm.get("none").equals("none")) {
					application.setCgpanReference("");
				}
				String appRefNo = "";
				// if(appProcessor.checkDublicateRecordAppPro(application,termLoan,ssiDetails,
				// userId,mliId)==0 )
				// {
				appRefNo = appProcessor.submitAddlTermCredit(application,
						userId); // **************                                                                                            TEMLOAN DETAIL INSERT ONLY
									// AMT****************
				LogClass.StepWritter(" applicationType=TCE appRefNo is "
						+ appRefNo);
				Log.log(4, "ApplicationProcessingAction", "submitApp",
						"After submitting Addtl Term Credit....");
				dynaForm.set("appRefNo", appRefNo);
				request.setAttribute("message", "Application (Reference No:"
						+ appRefNo + ")Submitted Successfully");

				successPage = "success";
				// }
				// else
				// {
				// successPage="doubleClickHapped";
				// }

			} else {
				application.setCgpanReference("");

				ClaimsProcessor claimProcessor = new ClaimsProcessor();

				String cgbid = "";

				if ((application.getCgpan() != null)
						&& (!application.getCgpan().equals(""))) {
					cgbid = claimProcessor.getBorowwerForCGPAN(application
							.getCgpan());
				} else if ((application.getBorrowerDetails().getSsiDetails()
						.getCgbid() != null)
						&& (!application.getBorrowerDetails().getSsiDetails()
								.getCgbid().equals(""))) {
					cgbid = application.getBorrowerDetails().getSsiDetails()
							.getCgbid();
				}

				int claimCount = appProcessor.getClaimCount(cgbid);
				if (claimCount > 0) {
					throw new MessageException(
							"Application cannot be filed by this borrower since Claim Application has been submitted");
				}
				
				

				// Milind 24-02-2016 start
				//if (appProcessor.checkDublicateRecordAppPro(application,
					//	termLoan, ssiDetails, userId, mliId) {
					
					

					application = appProcessor.submitMudraApplication(application, userId); // *********** @ DUPLIACATE ******************
													// DKR
													// ****FINAL******************

					String appRefNo = application.getAppRefNo();
					LogClass.StepWritter("In else appRefNo is " + appRefNo);
					dynaForm.set("appRefNo", appRefNo);
					int borrowerRefNo = application.getBorrowerDetails()
							.getSsiDetails().getBorrowerRefNo();
					Integer refNoValue = new Integer(borrowerRefNo);
					dynaForm.set("borrowerRefNo", refNoValue);
					request.setAttribute("message",
							"Application (Reference No:" + appRefNo
									+ ")Submitted Successfully");

					if (applicationLoanType.equals("BO")) {
						String wcAppRefNo = application.getWcAppRefNo();
						LogClass.StepWritter(" applicationType=BO wcAppRefNo is "
								+ wcAppRefNo);
						dynaForm.set("wcAppRefNo", wcAppRefNo);
						request.setAttribute("message",
								"Your guarantee application has been approved and Application (Reference No is:" + wcAppRefNo
										+ "," + appRefNo
										+ ")Submitted Successfully");
					}

					successPage = "success";
			//	} else {
				//	successPage = "doubleClickHapped";
				//}
				// Milind 24-02-2016 end
			}
			application = null;
			appProcessor = null;
			ssiDetails = null;
			borrowerDetails = null;
			termLoan = null;
			workingCapital = null;
			primarySecurityDetails = null;
			projectOutlayDetails = null;
			securitization = null;
			mcgfDetails = null;
			user = null;
			userId = null;
			bankId = null;
			zoneId = null;
			branchId = null;
			applicationSession.setAttribute("financialUIflag", "N");
			// }
			// else
			// {
			//  successPage="doubleClickHapped";
			// }
			LogClass.StepWritter(" submitApp end here ");
		} catch (Exception e) {
			e.printStackTrace();
			LogClass.writeExceptionOnFile(e);
			throw new NoDataException(e.getMessage());
		}
	/* }else{		  
	  request.getSession().invalidate();
	  response.sendRedirect(request.getContextPath() + "/showLogin");
	}*/
	return mapping.findForward(successPage);
	}

	public ActionForward submitRsfApp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(4, "ApplicationProcessingAction", "submitRsfApp", "Entered");

		String successPage = "";

		DynaActionForm dynaForm = (DynaActionForm) form;

		HttpSession applicationSession = request.getSession(false);

		Log.log(4, "ApplicationProcessingAction", "submitRsfApp",
				"Creating Objects");

		ApplicationProcessor appProcessor = new ApplicationProcessor();
		Application application = new Application();
		BorrowerDetails borrowerDetails = new BorrowerDetails();
		SSIDetails ssiDetails = new SSIDetails();
		ProjectOutlayDetails projectOutlayDetails = new ProjectOutlayDetails();
		PrimarySecurityDetails primarySecurityDetails = new PrimarySecurityDetails();
		TermLoan termLoan = new TermLoan();
		WorkingCapital workingCapital = new WorkingCapital();
		Securitization securitization = new Securitization();
		MCGFDetails mcgfDetails = new MCGFDetails();

		ClaimsProcessor cpProcessor = new ClaimsProcessor();
		User user = getUserInformation(request);

		String internalRating = (String) dynaForm.get("internalRating");

		String externalRating = (String) dynaForm.get("externalRating");

		application.setInternalRate(internalRating);
		application.setExternalRate(externalRating);

		String zoneId = "";
		String branchId = "";
		String mliId = "";

		String userId = user.getUserId();

		Log.log(5, "ApplicationProcessingAction", "submitRsfApp", "user Id :"
				+ userId);

		String bankId = user.getBankId();
		zoneId = user.getZoneId();
		branchId = user.getBranchId();
		if (bankId.equals("0000")) {
			String memberName = (String) dynaForm.get("selectMember");

			if (memberName != null) {
				bankId = memberName.substring(0, 4);
				zoneId = memberName.substring(4, 8);
				branchId = memberName.substring(8, 12);

				application.setBankId(bankId);
				application.setZoneId(zoneId);
				application.setBranchId(branchId);
				mliId = bankId + zoneId + branchId;
				application.setMliID(mliId);
			}

		} else {
			bankId = user.getBankId();
			application.setBankId(bankId);
			zoneId = user.getZoneId();
			application.setZoneId(zoneId);
			branchId = user.getBranchId();
			application.setBranchId(branchId);
			mliId = bankId + zoneId + branchId;

			application.setMliID(mliId);
		}

		dynaForm.set("bankId", bankId);
		dynaForm.set("zoneId", zoneId);
		dynaForm.set("branchId", branchId);
		dynaForm.set("mliID", mliId);

		String applicationType = "";
		String applicationLoanType = "";

		if (applicationSession.getAttribute("APPLICATION_LOAN_TYPE") == null) {
			applicationType = (String) applicationSession
					.getAttribute("APPLICATION_TYPE");
			Log.log(5, "ApplicationProcessingAction", "submitRsfApp",
					"ApplicationLoan Type :" + applicationType);
		} else {
			applicationLoanType = (String) applicationSession
					.getAttribute("APPLICATION_LOAN_TYPE");
			Log.log(5, "ApplicationProcessingAction", "submitRsfApp",
					"ApplicationLoan Type :" + applicationLoanType);

			String type = (String) dynaForm.get("loanType");
			application.setLoanType(type);
		}

		Log.log(4, "ApplicationProcessingAction", "submitRsfApp",
				"Calling Bean Utils...");

		BeanUtils.populate(ssiDetails, dynaForm.getMap());

		String constitutionValue = (String) dynaForm.get("constitutionOther");

		if ((dynaForm.get("constitution") != null)
				&& (!dynaForm.get("constitution").equals(""))) {
			if (dynaForm.get("constitution").equals("Others")) {
				ssiDetails.setConstitution(constitutionValue);
			}

		}

		String districtOthersValue = (String) dynaForm.get("districtOthers");
		if ((dynaForm.get("district") != null)
				&& (!dynaForm.get("district").equals(""))) {
			if (dynaForm.get("district").equals("Others")) {
				ssiDetails.setDistrict(districtOthersValue);
			}

		}

		String otherLegalIdValue = (String) dynaForm.get("otherCpLegalID");

		if ((dynaForm.get("cpLegalID") != null)
				&& (!dynaForm.get("cpLegalID").equals(""))) {
			if (dynaForm.get("cpLegalID").equals("Others")) {
				ssiDetails.setCpLegalID(otherLegalIdValue);
			}

		}

		borrowerDetails.setSsiDetails(ssiDetails);
		BeanUtils.populate(borrowerDetails, dynaForm.getMap());

		BeanUtils.populate(primarySecurityDetails, dynaForm.getMap());
		projectOutlayDetails.setPrimarySecurityDetails(primarySecurityDetails);
		BeanUtils.populate(projectOutlayDetails, dynaForm.getMap());

		BeanUtils.populate(termLoan, dynaForm.getMap());
		BeanUtils.populate(securitization, dynaForm.getMap());
		BeanUtils.populate(workingCapital, dynaForm.getMap());

		if (applicationSession.getAttribute("MCGF_FLAG").equals("M")) {
			BeanUtils.populate(mcgfDetails, dynaForm.getMap());
			application.setMCGFDetails(mcgfDetails);
		}

		application.setBorrowerDetails(borrowerDetails);
		String otherSubsidyNameValue = (String) dynaForm
				.get("otherSubsidyEquityName");

		if ((dynaForm.get("subsidyName") != null)
				&& (!dynaForm.get("subsidyName").equals(""))) {
			if (dynaForm.get("subsidyName").equals("Others")) {
				projectOutlayDetails.setSubsidyName(otherSubsidyNameValue);
			}

		}

		double termCreditSanctioned = ((Double) dynaForm
				.get("termCreditSanctioned")).doubleValue();

		double tcPromoterContribution = ((Double) dynaForm
				.get("tcPromoterContribution")).doubleValue();

		double tcSubsidyOrEquity = ((Double) dynaForm.get("tcSubsidyOrEquity"))
				.doubleValue();

		double tcOthers = ((Double) dynaForm.get("tcOthers")).doubleValue();
		double wcFundBasedSanctioned = ((Double) dynaForm
				.get("wcFundBasedSanctioned")).doubleValue();

		double wcNonFundBasedSanctioned = ((Double) dynaForm
				.get("wcNonFundBasedSanctioned")).doubleValue();

		double wcPromoterContribution = ((Double) dynaForm
				.get("wcPromoterContribution")).doubleValue();
		double wcSubsidyOrEquity = ((Double) dynaForm.get("wcSubsidyOrEquity"))
				.doubleValue();
		double wcOthers = ((Double) dynaForm.get("wcOthers")).doubleValue();

		double projectOutlayVal = termCreditSanctioned + tcPromoterContribution
				+ tcSubsidyOrEquity + tcOthers + wcFundBasedSanctioned
				+ wcNonFundBasedSanctioned + wcPromoterContribution
				+ wcSubsidyOrEquity + wcOthers;

		double minValue = 5000000.0D;
		double maxValue = 10000000.0D;

		if (projectOutlayVal < minValue) {
			throw new DatabaseException(
					"Credit to be Guaranteed4 Amount should be within the eligible amount available for Guarantee :" + 10000000);
		}
		Double projectOutlayCost = new Double(projectOutlayVal);
		double projectOutlay = projectOutlayCost.doubleValue();
		projectOutlayDetails.setProjectOutlay(projectOutlay);

		application.setProjectOutlayDetails(projectOutlayDetails);
		application.setTermLoan(termLoan);
		application.setWc(workingCapital);
		application.setSecuritization(securitization);

		BeanUtils.populate(application, dynaForm.getMap());

		if (dynaForm.get("none").equals("cgpan")) {
			application.setCgpan((String) dynaForm.get("unitValue"));
		} else if (dynaForm.get("none").equals("cgbid")) {
			ArrayList borrowerIds = cpProcessor.getAllBorrowerIDs(mliId);
			if (!borrowerIds.contains(dynaForm.get("unitValue"))) {
				throw new NoDataException(
						"The Borrower ID does not exist for this Member ID");
			}

			application.getBorrowerDetails().getSsiDetails()
					.setCgbid((String) dynaForm.get("unitValue"));
		}

		Log.log(4, "ApplicationProcessingAction", "submitRsfApp",
				"application type :" + applicationType);

		if (applicationSession.getAttribute("MCGF_FLAG").equals("M")) {
			application.setScheme("MCGS");

			if (applicationSession.getAttribute("ssiRefNumber") != null) {
				Log.log(4,
						"ApplicationProcessingAction",
						"submitRsfApp",
						"applicationSession.getAttribute ssiRefNumber"
								+ applicationSession
										.getAttribute("ssiRefNumber"));

				double corpusContAmt = appProcessor
						.getCorpusContAmt(((Integer) applicationSession
								.getAttribute("ssiRefNumber")).intValue());
				double totalCorpusContAmt = corpusContAmt * 5.0D;

				double creditAmount = 0.0D;
				if (application.getLoanType().equals("TC")) {
					creditAmount = application.getTermLoan()
							.getCreditGuaranteed();
				} else if (application.getLoanType().equals("CC")) {
					creditAmount = application.getTermLoan()
							.getCreditGuaranteed()
							+ application.getWc().getCreditFundBased()
							+ application.getWc().getCreditNonFundBased();
				} else if (application.getLoanType().equals("WC")) {
					creditAmount = application.getWc().getCreditFundBased()
							+ application.getWc().getCreditNonFundBased();
				}

				if (totalCorpusContAmt != 0.0D) {
					if (creditAmount > totalCorpusContAmt) {
						throw new MessageException(
								"Credit Limit should not exceed the Corpus Amount");
					}

				}

				BorrowerDetails borrowerDtlTemp = application
						.getBorrowerDetails();
				SSIDetails ssiDtlTemp = borrowerDtlTemp.getSsiDetails();
				ssiDtlTemp.setBorrowerRefNo(((Integer) applicationSession
						.getAttribute("ssiRefNumber")).intValue());
				borrowerDtlTemp.setSsiDetails(ssiDtlTemp);
				application.setBorrowerDetails(borrowerDtlTemp);
			}
		} else {
			application.setScheme("RSF");
		}

		if (applicationType.equals("MA")) {
			application.setCgpanReference("");

			appProcessor.updateApplication(application, userId);
			Log.log(4, "ApplicationProcessingAction", "submitRsfApp",
					"After updating....");

			request.setAttribute("message", "Application Modified Successfully");

			successPage = "success";
		} else if (applicationType.equals("TCE")) {
			if (dynaForm.get("none").equals("cgpan")) {
				String cgpanRef = (String) dynaForm.get("unitValue");
				application.setCgpanReference(cgpanRef);
			} else if (dynaForm.get("none").equals("none")) {
				application.setCgpanReference("");
			}

			String appRefNo = appProcessor.submitAddlTermCredit(application,
					userId);
			Log.log(4, "ApplicationProcessingAction", "submitRsfApp",
					"After submitting Addtl Term Credit....");
			dynaForm.set("appRefNo", appRefNo);
			request.setAttribute("message", "Application (Reference No:"
					+ appRefNo + ")Submitted Successfully");
			successPage = "success";
		} else {
			application.setCgpanReference("");
			ClaimsProcessor claimProcessor = new ClaimsProcessor();
			String cgbid = "";
			if ((application.getCgpan() != null)
					&& (!application.getCgpan().equals(""))) {
				cgbid = claimProcessor.getBorowwerForCGPAN(application
						.getCgpan());
			} else if ((application.getBorrowerDetails().getSsiDetails()
					.getCgbid() != null)
					&& (!application.getBorrowerDetails().getSsiDetails()
							.getCgbid().equals(""))) {
				cgbid = application.getBorrowerDetails().getSsiDetails()
						.getCgbid();
			}
			int claimCount = appProcessor.getClaimCount(cgbid);
			if (claimCount > 0) {
				throw new MessageException(
						"Application cannot be filed by this borrower since Claim Application has been submitted");
			}
			application.setLoanType(application.getLoanType());
			application.getBorrowerDetails().getSsiDetails()
					.setEnterprise((String) dynaForm.get("enterprise"));
			application.getBorrowerDetails().getSsiDetails()
					.setUnitAssisted((String) dynaForm.get("unitAssisted"));
			application.getBorrowerDetails().getSsiDetails()
					.setWomenOperated((String) dynaForm.get("womenOperated"));
			application = appProcessor.submitNewRSFApplication(application,
					userId);
			String appRefNo = application.getAppRefNo();
			dynaForm.set("appRefNo", appRefNo);
			int borrowerRefNo = application.getBorrowerDetails()
					.getSsiDetails().getBorrowerRefNo();
			Integer refNoValue = new Integer(borrowerRefNo);
			dynaForm.set("borrowerRefNo", refNoValue);
			request.setAttribute("message", "Application (Reference No:"
					+ appRefNo + ")Submitted Successfully");

			if (applicationLoanType.equals("BO")) {
				String wcAppRefNo = application.getWcAppRefNo();
				dynaForm.set("wcAppRefNo", wcAppRefNo);
				request.setAttribute("message", "Application (Reference Nos:"
						+ wcAppRefNo + "," + appRefNo
						+ ")Submitted Successfully");
			}

			successPage = "success";
		}

		application = null;
		appProcessor = null;
		ssiDetails = null;
		borrowerDetails = null;
		termLoan = null;
		workingCapital = null;
		primarySecurityDetails = null;
		projectOutlayDetails = null;
		securitization = null;
		mcgfDetails = null;
		user = null;
		userId = null;
		bankId = null;
		zoneId = null;
		branchId = null;

		return mapping.findForward(successPage);
	}

	public ActionForward submitRsf2App(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(4, "ApplicationProcessingAction", "submitRsfApp", "Entered");

		String successPage = "";

		DynaActionForm dynaForm = (DynaActionForm) form;

		HttpSession applicationSession = request.getSession(false);

		Log.log(4, "ApplicationProcessingAction", "submitRsf2App",
				"Creating Objects");

		ApplicationProcessor appProcessor = new ApplicationProcessor();
		Application application = new Application();
		BorrowerDetails borrowerDetails = new BorrowerDetails();
		SSIDetails ssiDetails = new SSIDetails();
		ProjectOutlayDetails projectOutlayDetails = new ProjectOutlayDetails();
		PrimarySecurityDetails primarySecurityDetails = new PrimarySecurityDetails();
		TermLoan termLoan = new TermLoan();
		WorkingCapital workingCapital = new WorkingCapital();
		Securitization securitization = new Securitization();
		MCGFDetails mcgfDetails = new MCGFDetails();

		ClaimsProcessor cpProcessor = new ClaimsProcessor();
		User user = getUserInformation(request);

		String internalRating = (String) dynaForm.get("internalRating");

		String externalRating = (String) dynaForm.get("externalRating");

		application.setInternalRate(internalRating);
		application.setExternalRate(externalRating);

		String zoneId = "";
		String branchId = "";
		String mliId = "";

		String userId = user.getUserId();

		Log.log(5, "ApplicationProcessingAction", "submitRsf2App", "user Id :"
				+ userId);

		String bankId = user.getBankId();
		zoneId = user.getZoneId();
		branchId = user.getBranchId();
		if (bankId.equals("0000")) {
			String memberName = (String) dynaForm.get("selectMember");

			if (memberName != null) {
				bankId = memberName.substring(0, 4);
				zoneId = memberName.substring(4, 8);
				branchId = memberName.substring(8, 12);

				application.setBankId(bankId);
				application.setZoneId(zoneId);
				application.setBranchId(branchId);
				mliId = bankId + zoneId + branchId;
				application.setMliID(mliId);
			}

		} else {
			bankId = user.getBankId();
			application.setBankId(bankId);
			zoneId = user.getZoneId();
			application.setZoneId(zoneId);
			branchId = user.getBranchId();
			application.setBranchId(branchId);
			mliId = bankId + zoneId + branchId;

			application.setMliID(mliId);
		}

		dynaForm.set("bankId", bankId);
		dynaForm.set("zoneId", zoneId);
		dynaForm.set("branchId", branchId);
		dynaForm.set("mliID", mliId);

		String applicationType = "";
		String applicationLoanType = "";

		if (applicationSession.getAttribute("APPLICATION_LOAN_TYPE") == null) {
			applicationType = (String) applicationSession
					.getAttribute("APPLICATION_TYPE");
			Log.log(5, "ApplicationProcessingAction", "submitRsf2App",
					"ApplicationLoan Type :" + applicationType);
		} else {
			applicationLoanType = (String) applicationSession
					.getAttribute("APPLICATION_LOAN_TYPE");
			Log.log(5, "ApplicationProcessingAction", "submitRsf2App",
					"ApplicationLoan Type :" + applicationLoanType);

			String type = (String) dynaForm.get("loanType");
			application.setLoanType(type);
		}

		Log.log(4, "ApplicationProcessingAction", "submitRsf2App",
				"Calling Bean Utils...");

		BeanUtils.populate(ssiDetails, dynaForm.getMap());

		String constitutionValue = (String) dynaForm.get("constitutionOther");

		if ((dynaForm.get("constitution") != null)
				&& (!dynaForm.get("constitution").equals(""))) {
			if (dynaForm.get("constitution").equals("Others")) {
				ssiDetails.setConstitution(constitutionValue);
			}

		}

		String districtOthersValue = (String) dynaForm.get("districtOthers");
		if ((dynaForm.get("district") != null)
				&& (!dynaForm.get("district").equals(""))) {
			if (dynaForm.get("district").equals("Others")) {
				ssiDetails.setDistrict(districtOthersValue);
			}

		}

		String otherLegalIdValue = (String) dynaForm.get("otherCpLegalID");

		if ((dynaForm.get("cpLegalID") != null)
				&& (!dynaForm.get("cpLegalID").equals(""))) {
			if (dynaForm.get("cpLegalID").equals("Others")) {
				ssiDetails.setCpLegalID(otherLegalIdValue);
			}

		}

		borrowerDetails.setSsiDetails(ssiDetails);
		BeanUtils.populate(borrowerDetails, dynaForm.getMap());

		BeanUtils.populate(primarySecurityDetails, dynaForm.getMap());
		projectOutlayDetails.setPrimarySecurityDetails(primarySecurityDetails);
		BeanUtils.populate(projectOutlayDetails, dynaForm.getMap());

		BeanUtils.populate(termLoan, dynaForm.getMap());
		BeanUtils.populate(securitization, dynaForm.getMap());
		BeanUtils.populate(workingCapital, dynaForm.getMap());

		if (applicationSession.getAttribute("MCGF_FLAG").equals("M")) {
			BeanUtils.populate(mcgfDetails, dynaForm.getMap());
			application.setMCGFDetails(mcgfDetails);
		}

		application.setBorrowerDetails(borrowerDetails);
		String otherSubsidyNameValue = (String) dynaForm
				.get("otherSubsidyEquityName");

		if ((dynaForm.get("subsidyName") != null)
				&& (!dynaForm.get("subsidyName").equals(""))) {
			if (dynaForm.get("subsidyName").equals("Others")) {
				projectOutlayDetails.setSubsidyName(otherSubsidyNameValue);
			}

		}

		double termCreditSanctioned = ((Double) dynaForm
				.get("termCreditSanctioned")).doubleValue();

		double tcPromoterContribution = ((Double) dynaForm
				.get("tcPromoterContribution")).doubleValue();

		double tcSubsidyOrEquity = ((Double) dynaForm.get("tcSubsidyOrEquity"))
				.doubleValue();

		double tcOthers = ((Double) dynaForm.get("tcOthers")).doubleValue();
		double wcFundBasedSanctioned = ((Double) dynaForm
				.get("wcFundBasedSanctioned")).doubleValue();

		double wcNonFundBasedSanctioned = ((Double) dynaForm
				.get("wcNonFundBasedSanctioned")).doubleValue();

		double wcPromoterContribution = ((Double) dynaForm
				.get("wcPromoterContribution")).doubleValue();
		double wcSubsidyOrEquity = ((Double) dynaForm.get("wcSubsidyOrEquity"))
				.doubleValue();
		double wcOthers = ((Double) dynaForm.get("wcOthers")).doubleValue();

		double termCreditToGuarantee = 0.0D;
		double wcFBCreditToGuarantee = 0.0D;
		double wcNFBCreditToGuarantee = 0.0D;

		if ((application.getLoanType().equals("TC"))
				|| (application.getLoanType().equals("BO"))) {
			String tcg = dynaForm.get("creditGuaranteed").toString();
			if ((tcg.equals(null)) || (tcg.equals("")))
				termCreditToGuarantee = 0.0D;
			else {
				termCreditToGuarantee = ((Double) dynaForm
						.get("creditGuaranteed")).doubleValue();
			}
		}
		if ((application.getLoanType().equals("WC"))
				|| (application.getLoanType().equals("BO"))) {
			String fbcg = dynaForm.get("creditFundBased").toString();
			String nfbcg = dynaForm.get("creditNonFundBased").toString();
			if ((fbcg.equals(null)) || (fbcg.equals("")))
				wcFBCreditToGuarantee = 0.0D;
			else {
				wcFBCreditToGuarantee = ((Double) dynaForm
						.get("creditFundBased")).doubleValue();
			}
			if ((nfbcg.equals(null)) || (nfbcg.equals("")))
				wcNFBCreditToGuarantee = 0.0D;
			else {
				wcNFBCreditToGuarantee = ((Double) dynaForm
						.get("creditNonFundBased")).doubleValue();
			}

		}

		double projectOutlayVal = termCreditToGuarantee + wcFBCreditToGuarantee
				+ wcNFBCreditToGuarantee;

		double minValue = 10000000.0D;
		double maxValue = 20000000.0D;

		if ((projectOutlayVal < minValue) || (projectOutlayVal > maxValue)) {
			throw new DatabaseException(
					"Credit to be Guaranteed Amount for RSF 2 should be within the eligible amount i.e between 10000000 and 20000000");
		}

		Double projectOutlayCost = new Double(projectOutlayVal);
		double projectOutlay = projectOutlayCost.doubleValue();
		projectOutlayDetails.setProjectOutlay(projectOutlay);

		application.setProjectOutlayDetails(projectOutlayDetails);
		application.setTermLoan(termLoan);
		application.setWc(workingCapital);
		application.setSecuritization(securitization);

		BeanUtils.populate(application, dynaForm.getMap());

		if (dynaForm.get("none").equals("cgpan")) {
			application.setCgpan((String) dynaForm.get("unitValue"));
		} else if (dynaForm.get("none").equals("cgbid")) {
			ArrayList borrowerIds = cpProcessor.getAllBorrowerIDs(mliId);
			if (!borrowerIds.contains(dynaForm.get("unitValue"))) {
				throw new NoDataException(
						"The Borrower ID does not exist for this Member ID");
			}

			application.getBorrowerDetails().getSsiDetails()
					.setCgbid((String) dynaForm.get("unitValue"));
		}

		Log.log(4, "ApplicationProcessingAction", "submitRsf2App",
				"application type :" + applicationType);

		if (applicationSession.getAttribute("MCGF_FLAG").equals("M")) {
			application.setScheme("MCGS");

			if (applicationSession.getAttribute("ssiRefNumber") != null) {
				Log.log(4,
						"ApplicationProcessingAction",
						"submitRsf2App",
						"applicationSession.getAttribute ssiRefNumber"
								+ applicationSession
										.getAttribute("ssiRefNumber"));

				double corpusContAmt = appProcessor
						.getCorpusContAmt(((Integer) applicationSession
								.getAttribute("ssiRefNumber")).intValue());
				double totalCorpusContAmt = corpusContAmt * 5.0D;

				double creditAmount = 0.0D;
				if (application.getLoanType().equals("TC")) {
					creditAmount = application.getTermLoan()
							.getCreditGuaranteed();
				} else if (application.getLoanType().equals("CC")) {
					creditAmount = application.getTermLoan()
							.getCreditGuaranteed()
							+ application.getWc().getCreditFundBased()
							+ application.getWc().getCreditNonFundBased();
				} else if (application.getLoanType().equals("WC")) {
					creditAmount = application.getWc().getCreditFundBased()
							+ application.getWc().getCreditNonFundBased();
				}

				if (totalCorpusContAmt != 0.0D) {
					if (creditAmount > totalCorpusContAmt) {
						throw new MessageException(
								"Credit Limit should not exceed the Corpus Amount");
					}

				}

				BorrowerDetails borrowerDtlTemp = application
						.getBorrowerDetails();
				SSIDetails ssiDtlTemp = borrowerDtlTemp.getSsiDetails();
				ssiDtlTemp.setBorrowerRefNo(((Integer) applicationSession
						.getAttribute("ssiRefNumber")).intValue());
				borrowerDtlTemp.setSsiDetails(ssiDtlTemp);
				application.setBorrowerDetails(borrowerDtlTemp);
			}
		} else {
			application.setScheme("RSF2");
		}

		if (applicationType.equals("MA")) {
			application.setCgpanReference("");

			appProcessor.updateApplication(application, userId);
			Log.log(4, "ApplicationProcessingAction", "submitRsf2App",
					"After updating....");

			request.setAttribute("message", "Application Modified Successfully");

			successPage = "success";
		} else if (applicationType.equals("TCE")) {
			if (dynaForm.get("none").equals("cgpan")) {
				String cgpanRef = (String) dynaForm.get("unitValue");
				application.setCgpanReference(cgpanRef);
			} else if (dynaForm.get("none").equals("none")) {
				application.setCgpanReference("");
			}
			//System.out.println("application.getCgpanReference1:"+ application.getCgpanReference());
			String appRefNo = appProcessor.submitAddlTermCredit(application,
					userId);
			Log.log(4, "ApplicationProcessingAction", "submitRsf2App",
					"After submitting Addtl Term Credit....");
			dynaForm.set("appRefNo", appRefNo);
			request.setAttribute("message", "Application (Reference No:"
					+ appRefNo + ")Submitted Successfully");

			successPage = "success";
		} else {
			application.setCgpanReference("");

			ClaimsProcessor claimProcessor = new ClaimsProcessor();

			String cgbid = "";

			if ((application.getCgpan() != null)
					&& (!application.getCgpan().equals(""))) {
				cgbid = claimProcessor.getBorowwerForCGPAN(application
						.getCgpan());
			} else if ((application.getBorrowerDetails().getSsiDetails()
					.getCgbid() != null)
					&& (!application.getBorrowerDetails().getSsiDetails()
							.getCgbid().equals(""))) {
				cgbid = application.getBorrowerDetails().getSsiDetails()
						.getCgbid();
			}

			int claimCount = appProcessor.getClaimCount(cgbid);
			if (claimCount > 0) {
				throw new MessageException(
						"Application cannot be filed by this borrower since Claim Application has been submitted");
			}

			application.setLoanType(application.getLoanType());

			application.getBorrowerDetails().getSsiDetails()
					.setEnterprise((String) dynaForm.get("enterprise"));

			application.getBorrowerDetails().getSsiDetails()
					.setUnitAssisted((String) dynaForm.get("unitAssisted"));

			application.getBorrowerDetails().getSsiDetails()
					.setWomenOperated((String) dynaForm.get("womenOperated"));

			//System.out.println("submitNewRSF2Application:"+ application.getCgpanReference());
			application = appProcessor.submitNewRSF2Application(application,
					userId);

			String appRefNo = application.getAppRefNo();

			dynaForm.set("appRefNo", appRefNo);
			int borrowerRefNo = application.getBorrowerDetails()
					.getSsiDetails().getBorrowerRefNo();
			Integer refNoValue = new Integer(borrowerRefNo);
			dynaForm.set("borrowerRefNo", refNoValue);
			request.setAttribute("message", "Application (Reference No:"
					+ appRefNo + ")Submitted Successfully");

			if (applicationLoanType.equals("BO")) {
				String wcAppRefNo = application.getWcAppRefNo();
				dynaForm.set("wcAppRefNo", wcAppRefNo);
				request.setAttribute("message", "Application (Reference Nos:"
						+ wcAppRefNo + "," + appRefNo
						+ ")Submitted Successfully");
			}

			successPage = "success";
		}

		application = null;
		appProcessor = null;
		ssiDetails = null;
		borrowerDetails = null;
		termLoan = null;
		workingCapital = null;
		primarySecurityDetails = null;
		projectOutlayDetails = null;
		securitization = null;
		mcgfDetails = null;
		user = null;
		userId = null;
		bankId = null;
		zoneId = null;
		branchId = null;

		return mapping.findForward(successPage);
	}
//Adhar update
	public ActionForward afterWcRenewalApp(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession applicationSession = request.getSession(false);

		DynaActionForm dynaForm = (DynaActionForm) form;
		Application application = new Application();
		ProjectOutlayDetails projectOutlayDetails = new ProjectOutlayDetails();
		SSIDetails ssiDetails = new SSIDetails();
		BorrowerDetails borrowerDetails = new BorrowerDetails();
		TermLoan termLoan = new TermLoan();
		PrimarySecurityDetails primarySecurityDetails = new PrimarySecurityDetails();
		ApplicationProcessor appProcessor = new ApplicationProcessor();
		WorkingCapital workingCapital = new WorkingCapital();
		Securitization securitization = new Securitization();
		MCGFDetails mcgfDetails = new MCGFDetails();

		User user = getUserInformation(request);
		String primarySercurity = (String) dynaForm.get("pSecurity");
		//System.out.println("Primary Security : " + primarySercurity);
		projectOutlayDetails.setIsPrimarySecurity(primarySercurity);

		String applicationTypes = (String) applicationSession
				.getAttribute("APPLICATION_TYPE");

		String zoneId = "";
		String branchId = "";
		String mliId = "";

		String userId = user.getUserId();
		String bankId = user.getBankId();
		zoneId = user.getZoneId();
		branchId = user.getBranchId();
		if (bankId.equals("0000")) {
			String memberName = (String) dynaForm.get("selectMember");
			if (memberName != null) {
				bankId = memberName.substring(0, 4);
				zoneId = memberName.substring(4, 8);
				branchId = memberName.substring(8, 12);

				application.setBankId(bankId);
				application.setZoneId(zoneId);
				application.setBranchId(branchId);
				mliId = bankId + zoneId + branchId;
				application.setMliID(mliId);
			}

		} else {
			bankId = user.getBankId();
			application.setBankId(bankId);
			zoneId = user.getZoneId();
			application.setZoneId(zoneId);
			branchId = user.getBranchId();
			application.setBranchId(branchId);
			mliId = bankId + zoneId + branchId;
			application.setMliID(mliId);
		}

		dynaForm.set("bankId", bankId);
		dynaForm.set("zoneId", zoneId);
		dynaForm.set("branchId", branchId);

		BeanUtils.populate(ssiDetails, dynaForm.getMap());

		String constitutionValue = (String) dynaForm.get("constitutionOther");
		if ((dynaForm.get("constitution") != null)
				&& (!dynaForm.get("constitution").equals(""))) {
			if (dynaForm.get("constitution").equals("Others")) {
				ssiDetails.setConstitution(constitutionValue);
			}

		}

		String districtOthersValue = (String) dynaForm.get("districtOthers");
		if ((dynaForm.get("district") != null)
				&& (!dynaForm.get("district").equals(""))) {
			if (dynaForm.get("district").equals("Others")) {
				ssiDetails.setDistrict(districtOthersValue);
			}

		}

		String otherLegalIdValue = (String) dynaForm.get("otherCpLegalID");
		if ((dynaForm.get("cpLegalID") != null)
				&& (!dynaForm.get("cpLegalID").equals(""))) {
			if ((dynaForm.get("cpLegalID") != null)
					&& (!dynaForm.get("cpLegalID").equals(""))) {
				if (dynaForm.get("cpLegalID").equals("Others")) {
					ssiDetails.setCpLegalID(otherLegalIdValue);
				}
			}
		}

		borrowerDetails.setSsiDetails(ssiDetails);
		BeanUtils.populate(borrowerDetails, dynaForm.getMap());
		application.setBorrowerDetails(borrowerDetails);

		BeanUtils.populate(primarySecurityDetails, dynaForm.getMap());
		projectOutlayDetails.setPrimarySecurityDetails(primarySecurityDetails);

		BeanUtils.populate(projectOutlayDetails, dynaForm.getMap());

		String otherSubsidyNameValue = (String) dynaForm
				.get("otherSubsidyEquityName");
		if ((dynaForm.get("subsidyName") != null)
				&& (!dynaForm.get("subsidyName").equals(""))) {
			if (dynaForm.get("subsidyName").equals("Others")) {
				projectOutlayDetails.setSubsidyName(otherSubsidyNameValue);
			}

		}

		double termCreditSanctioned = ((Double) dynaForm
				.get("termCreditSanctioned")).doubleValue();
		double tcPromoterContribution = ((Double) dynaForm
				.get("tcPromoterContribution")).doubleValue();
		double tcSubsidyOrEquity = ((Double) dynaForm.get("tcSubsidyOrEquity"))
				.doubleValue();
		double tcOthers = ((Double) dynaForm.get("tcOthers")).doubleValue();
		double wcFundBasedSanctioned = ((Double) dynaForm
				.get("wcFundBasedSanctioned")).doubleValue();
		double wcNonFundBasedSanctioned = ((Double) dynaForm
				.get("wcNonFundBasedSanctioned")).doubleValue();
		double wcPromoterContribution = ((Double) dynaForm
				.get("wcPromoterContribution")).doubleValue();
		double wcSubsidyOrEquity = ((Double) dynaForm.get("wcSubsidyOrEquity"))
				.doubleValue();
		double wcOthers = ((Double) dynaForm.get("wcOthers")).doubleValue();

		double projectOutlayVal = termCreditSanctioned + tcPromoterContribution
				+ tcSubsidyOrEquity + tcOthers + wcFundBasedSanctioned
				+ wcNonFundBasedSanctioned + wcPromoterContribution
				+ wcSubsidyOrEquity + wcOthers;

		Double projectOutlayValue = new Double(projectOutlayVal);
		double projectOutlay = projectOutlayValue.doubleValue();
		String handiCrafts = (String) dynaForm.get("handiCrafts");
		String dcHandicrafts = (String) dynaForm.get("dcHandicrafts");
		String icardNo = (String) dynaForm.get("icardNo");
		Date icardIssueDate = (Date) dynaForm.get("icardIssueDate");
		String dcHandlooms = (String) dynaForm.get("dcHandlooms");
		String WeaverCreditScheme = (String) dynaForm.get("WeaverCreditScheme");
		String handloomchk = (String) dynaForm.get("handloomchk");
		
		// Added by DKR Hybrid Security
				 
		 String hybridSecurity = (String) dynaForm.get("hybridSecurity");
		 Double movColSecurityAmt = (Double) dynaForm.get("movCollateratlSecurityAmt");
		 Double immovColSecurityAmt = (Double) dynaForm.get("immovCollateratlSecurityAmt");
		 Double totalMIcolSecAmt = (Double) dynaForm.get("totalMIcollatSecAmt");
		 Long proMobileNo = (Long) dynaForm.get("proMobileNo");
		 //System.out.println(hybridSecurity+"-"+movColSecurityAmt+"-action-"+immovColSecurityAmt+"-"+totalMIcolSecAmt+"-");
		
		// END Hybrid Security
		 
		projectOutlayDetails.setProjectOutlay(projectOutlay);

		application.setProjectOutlayDetails(projectOutlayDetails);

		BeanUtils.populate(termLoan, dynaForm.getMap());
		BeanUtils.populate(workingCapital, dynaForm.getMap());

		application.setTermLoan(termLoan);
		application.setWc(workingCapital);

		application.setHandiCrafts(handiCrafts);
		application.setDcHandicrafts(dcHandicrafts);
		application.setIcardNo(icardNo);
		application.setIcardIssueDate(icardIssueDate);
		application.setDcHandlooms(dcHandlooms);
		application.setWeaverCreditScheme(WeaverCreditScheme);
		application.setHandloomchk(handloomchk);

		BeanUtils.populate(workingCapital, dynaForm.getMap());

		double renewalFBInterest = ((Double) dynaForm.get("renewalFBInterest"))
				.doubleValue();

		workingCapital.setLimitFundBasedInterest(renewalFBInterest);
		double renewalNFBComission = ((Double) dynaForm
				.get("renewalNFBComission")).doubleValue();
		workingCapital.setLimitNonFundBasedCommission(renewalNFBComission);
		Date renewalDate = (Date) dynaForm.get("renewalDate");
		Double wcFundBasedSanctionedVal = new Double(wcFundBasedSanctioned);
		Double wcNonFundBasedSanctionedVal = new Double(
				wcNonFundBasedSanctioned);
		dynaForm.set("renewalFundBased", wcFundBasedSanctionedVal);
		dynaForm.set("renewalNonFundBased", wcNonFundBasedSanctionedVal);

		if (wcFundBasedSanctioned == 0.0D) {
			workingCapital.setLimitNonFundBasedSanctionedDate(renewalDate);
		} else if (wcNonFundBasedSanctioned == 0.0D) {
			workingCapital.setLimitFundBasedSanctionedDate(renewalDate);
		} else {
			workingCapital.setLimitNonFundBasedSanctionedDate(renewalDate);
			workingCapital.setLimitFundBasedSanctionedDate(renewalDate);
		}

		application.setWc(workingCapital);
		BeanUtils.populate(securitization, dynaForm.getMap());
		application.setSecuritization(securitization);

		if (applicationSession.getAttribute("MCGF_FLAG").equals("M")) {
			BeanUtils.populate(mcgfDetails, dynaForm.getMap());
			application.setMCGFDetails(mcgfDetails);
		}

		BeanUtils.populate(application, dynaForm.getMap());

		if ((dcHandlooms != null) && (!dcHandlooms.equals("null"))) {
			if (dcHandlooms.equals("N")) {
				application.setHandloomchk("N");
				application.setWeaverCreditScheme("");
			}

		}

		if ((handiCrafts != null) && (!handiCrafts.equals("null"))) {
			if (handiCrafts.equals("N")) {
				application.setDcHandicrafts("N");
			}
		}
		
		application.setBankId(bankId);
		application.setZoneId(zoneId);
		application.setBranchId(branchId);
		/*String aadharNo = (String)dynaForm.get("adhar"); 
		application.setAdharNo(aadharNo); */
		 application.setHybridSecurity(hybridSecurity);
		 application.setMovCollateratlSecurityAmt(movColSecurityAmt);
		 application.setImmovCollateratlSecurityAmt(immovColSecurityAmt);
		 application.setTotalMIcollatSecAmt(totalMIcolSecAmt);
		 application.setProMobileNo(proMobileNo);
		 
		MLIInfo mliInfo = this.registration.getMemberDetails(bankId, zoneId,
				branchId);
		String mcgfFlag = mliInfo.getSupportMCGF();
		if (mcgfFlag.equals("Y")) {
			application.setScheme("MCGS");
		} else {
			application.setScheme("CGFSI");
		}

		if ((dynaForm.get("unitValue") != null)
				&& (!dynaForm.get("unitValue").equals(""))) {
			String cgpan = (String) dynaForm.get("unitValue");

			application.setCgpan(cgpan);
			application.setCgpanReference(cgpan);
		}

		if (applicationSession.getAttribute("MCGF_FLAG").equals("M")) {
			if ((dynaForm.get("unitValue") != null)
					&& (!dynaForm.get("unitValue").equals(""))) {
				borrowerDetails = appProcessor.fetchBorrowerDetails("",
						(String) dynaForm.get("unitValue"));
				double corpusContAmt = appProcessor
						.getCorpusContAmt(borrowerDetails.getSsiDetails()
								.getBorrowerRefNo());
				double totalCorpusContAmt = corpusContAmt * 5.0D;

				double creditAmount = 0.0D;
				creditAmount = application.getWc().getCreditFundBased()
						+ application.getWc().getCreditNonFundBased();
				if (totalCorpusContAmt != 0.0D) {
					if (creditAmount > totalCorpusContAmt) {
						throw new MessageException(
								"Credit Limit should not exceed the Corpus Amount");
					}
				}
			}

		}

		if (applicationTypes.equals("WCR")) {
			//System.out.println("Renewal of Application:"+ application.getCgpan());
		}

		String appRefNo = appProcessor.submitWcRenewal(application, userId);    // submit for aadhar   RENEW
		dynaForm.set("appRefNo", appRefNo);
		request.setAttribute("message", "Application (Reference No:" + appRefNo
				+ ")Submitted Successfully");

		application = null;
		appProcessor = null;
		ssiDetails = null;
		borrowerDetails = null;
		termLoan = null;
		workingCapital = null;
		primarySecurityDetails = null;
		projectOutlayDetails = null;
		securitization = null;
		mcgfDetails = null;
		user = null;
		userId = null;
		bankId = null;
		zoneId = null;
		branchId = null;
		mliInfo = null;

		return mapping.findForward("success");
	}
 // DKR Aadhar FB  enhansment
	public ActionForward afterWcEnhanceApp(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaActionForm dynaForm = (DynaActionForm) form;

		HttpSession applicationSession = request.getSession(false);

		Application application = new Application();
		ApplicationProcessor appProcessor = new ApplicationProcessor();
		PrimarySecurityDetails primarySecurityDetails = new PrimarySecurityDetails();
		SSIDetails ssiDetails = new SSIDetails();
		BorrowerDetails borrowerDetails = new BorrowerDetails();
		ProjectOutlayDetails projectOutlayDetails = new ProjectOutlayDetails();
		TermLoan termLoan = new TermLoan();
		WorkingCapital workingCapital = new WorkingCapital();
		Securitization securitization = new Securitization();
		MCGFDetails mcgfDetails = new MCGFDetails();
		
		// Added by DKR Hybrid Security
			
		 String hybridSecurity = (String) dynaForm.get("hybridSecurity");
		 Double movColSecurityAmt = (Double) dynaForm.get("movCollateratlSecurityAmt");
		 Double immovColSecurityAmt = (Double) dynaForm.get("immovCollateratlSecurityAmt");
		 Double totalMIcolSecAmt = (Double) dynaForm.get("totalMIcollatSecAmt");
		 Long proMobileNo = (Long) dynaForm.get("proMobileNo");
		 //System.out.println(hybridSecurity+"-"+movColSecurityAmt+"-action-"+immovColSecurityAmt+"-"+totalMIcolSecAmt+"-");
		 application.setHybridSecurity(hybridSecurity);
		 application.setMovCollateratlSecurityAmt(movColSecurityAmt);
		 application.setImmovCollateratlSecurityAmt(immovColSecurityAmt);
		 application.setTotalMIcollatSecAmt(totalMIcolSecAmt);
		 application.setProMobileNo(proMobileNo);
				// END Hybrid Security
				 
		User user = getUserInformation(request);
		String zoneId = "";
		String branchId = "";
		String mliId = "";

		String userId = user.getUserId();
		Log.log(5, "ApplicationProcessingAction", "submitApp", "user Id :"
				+ userId);
		String primarySercurity = (String) dynaForm.get("pSecurity");
		//System.out.println("Primary Security : " + primarySercurity);
		projectOutlayDetails.setIsPrimarySecurity(primarySercurity);// bhu

		String bankId = user.getBankId();
		zoneId = user.getZoneId();
		branchId = user.getBranchId();
		if (bankId.equals("0000")) {
			String memberName = (String) dynaForm.get("selectMember");
			if (memberName != null) {
				bankId = memberName.substring(0, 4);
				zoneId = memberName.substring(4, 8);
				branchId = memberName.substring(8, 12);

				application.setBankId(bankId);
				application.setZoneId(zoneId);
				application.setBranchId(branchId);
				mliId = bankId + zoneId + branchId;
				application.setMliID(mliId);
			}

		} else {
			bankId = user.getBankId();
			application.setBankId(bankId);
			zoneId = user.getZoneId();
			application.setZoneId(zoneId);
			branchId = user.getBranchId();
			application.setBranchId(branchId);
			mliId = bankId + zoneId + branchId;
			application.setMliID(mliId);
		}

		dynaForm.set("bankId", bankId);
		dynaForm.set("zoneId", zoneId);
		dynaForm.set("branchId", branchId);

		BeanUtils.populate(ssiDetails, dynaForm.getMap());
		
		String constitutionValue = (String) dynaForm.get("constitutionOther");
		if ((dynaForm.get("constitution") != null)
				&& (!dynaForm.get("constitution").equals(""))) {
			if (dynaForm.get("constitution").equals("Others")) {
				ssiDetails.setConstitution(constitutionValue);
			}

		}

		String districtOthersValue = (String) dynaForm.get("districtOthers");
		if ((dynaForm.get("district") != null)
				&& (!dynaForm.get("district").equals(""))) {
			if (dynaForm.get("district").equals("Others")) {
				ssiDetails.setDistrict(districtOthersValue);
			}

		}

		String otherLegalIdValue = (String) dynaForm.get("otherCpLegalID");
		if ((dynaForm.get("cpLegalID") != null)
				&& (!dynaForm.get("cpLegalID").equals(""))) {
			if (dynaForm.get("cpLegalID").equals("Others")) {
				ssiDetails.setCpLegalID(otherLegalIdValue);
			}

		}

		borrowerDetails.setSsiDetails(ssiDetails);
		BeanUtils.populate(borrowerDetails, dynaForm.getMap());

		BeanUtils.populate(primarySecurityDetails, dynaForm.getMap());
		projectOutlayDetails.setPrimarySecurityDetails(primarySecurityDetails);
		BeanUtils.populate(projectOutlayDetails, dynaForm.getMap());

		BeanUtils.populate(termLoan, dynaForm.getMap());
		BeanUtils.populate(workingCapital, dynaForm.getMap());

		application.setBorrowerDetails(borrowerDetails);
		String otherSubsidyNameValue = (String) dynaForm
				.get("otherSubsidyEquityName");
		if ((dynaForm.get("subsidyName") != null)
				&& (!dynaForm.get("subsidyName").equals(""))) {
			if (dynaForm.get("subsidyName").equals("Others")) {
				projectOutlayDetails.setSubsidyName(otherSubsidyNameValue);
			}
		}

		String handiCrafts = (String) dynaForm.get("handiCrafts");
		String dcHandicrafts = (String) dynaForm.get("dcHandicrafts");
		String icardNo = (String) dynaForm.get("icardNo");
		Date icardIssueDate = (Date) dynaForm.get("icardIssueDate");
		String dcHandlooms = (String) dynaForm.get("dcHandlooms");

		String WeaverCreditScheme = (String) dynaForm.get("WeaverCreditScheme");

		String handloomchk = (String) dynaForm.get("handloomchk");

		double termCreditSanctioned = ((Double) dynaForm
				.get("termCreditSanctioned")).doubleValue();
		double tcPromoterContribution = ((Double) dynaForm
				.get("tcPromoterContribution")).doubleValue();
		double tcSubsidyOrEquity = ((Double) dynaForm.get("tcSubsidyOrEquity"))
				.doubleValue();
		double tcOthers = ((Double) dynaForm.get("tcOthers")).doubleValue();
		double wcFundBasedSanctioned = ((Double) dynaForm
				.get("wcFundBasedSanctioned")).doubleValue();
		double wcNonFundBasedSanctioned = ((Double) dynaForm
				.get("wcNonFundBasedSanctioned")).doubleValue();
		double wcPromoterContribution = ((Double) dynaForm
				.get("wcPromoterContribution")).doubleValue();
		double wcSubsidyOrEquity = ((Double) dynaForm.get("wcSubsidyOrEquity"))
				.doubleValue();
		double wcOthers = ((Double) dynaForm.get("wcOthers")).doubleValue();

		double projectOutlayVal = termCreditSanctioned + tcPromoterContribution
				+ tcSubsidyOrEquity + tcOthers + wcFundBasedSanctioned
				+ wcNonFundBasedSanctioned + wcPromoterContribution
				+ wcSubsidyOrEquity + wcOthers;

		Double projectOutlayValue = new Double(projectOutlayVal);
		double projectOutlay = projectOutlayValue.doubleValue();
		projectOutlayDetails.setProjectOutlay(projectOutlay);

		application.setProjectOutlayDetails(projectOutlayDetails);
		application.setTermLoan(termLoan);
		
		Double wcFundBasedSanctionedValue = new Double(wcFundBasedSanctioned);
		double wcFundBasedSanctionedVal = wcFundBasedSanctionedValue
				.doubleValue();
		Double wcNonFundBasedSanctionedValue = new Double(
				wcNonFundBasedSanctioned);
		double wcNonFundBasedSanctionedVal = wcNonFundBasedSanctionedValue
				.doubleValue();

		double fundBasedInterest = ((Double) dynaForm.get("enhancedFBInterest"))
				.doubleValue();
		workingCapital.setLimitFundBasedInterest(fundBasedInterest);

		double nonfundBasedCommission = ((Double) dynaForm
				.get("enhancedNFBComission")).doubleValue();
		workingCapital.setLimitNonFundBasedCommission(nonfundBasedCommission);

		workingCapital.setEnhancedFundBased(wcFundBasedSanctionedVal);
		workingCapital.setEnhancedNonFundBased(wcNonFundBasedSanctionedVal);
		workingCapital.setWcInterestType("T");
		workingCapital.setCreditFundBased(wcFundBasedSanctionedVal);
		workingCapital.setCreditNonFundBased(wcNonFundBasedSanctionedVal);
		application.setWc(workingCapital);
		Log.log(2, "ApplicationProcessingAction", "afterWcEnhanceApp",
				"app ref no from dynaform :" + dynaForm.get("appRefNo"));

		BeanUtils.populate(securitization, dynaForm.getMap());

		application.setSecuritization(securitization);

		if (applicationSession.getAttribute("MCGF_FLAG").equals("M")) {
			BeanUtils.populate(mcgfDetails, dynaForm.getMap());
			application.setMCGFDetails(mcgfDetails);
		}

		BeanUtils.populate(application, dynaForm.getMap());
		String cgpan = "";
		if ((dynaForm.get("unitValue") != null)
				&& (!dynaForm.get("unitValue").equals(""))) {
			cgpan = (String) dynaForm.get("unitValue");
			application.setCgpan(cgpan);
		}

		if (applicationSession.getAttribute("MCGF_FLAG").equals("M")) {
			if ((dynaForm.get("unitValue") != null)
					&& (!dynaForm.get("unitValue").equals(""))) {
				borrowerDetails = appProcessor.fetchBorrowerDetails("", cgpan);
				double corpusContAmt = appProcessor
						.getCorpusContAmt(borrowerDetails.getSsiDetails()
								.getBorrowerRefNo());
				double totalCorpusContAmt = corpusContAmt * 5.0D;

				double creditAmount = 0.0D;
				creditAmount = application.getWc().getCreditFundBased()
						+ application.getWc().getCreditNonFundBased();

				Log.log(2, "ApplicationProcessingAction", "afterWcEnhanceApp",
						"creditAmount:" + creditAmount);

				if (totalCorpusContAmt != 0.0D) {
					if (creditAmount > totalCorpusContAmt) {
						throw new MessageException(
								"Credit Limit should not exceed the Corpus Amount");
					}
				}

			}

		}

		Application tempApplication = appProcessor.getApplication(
				application.getMliID(), cgpan, "");
		WorkingCapital tempWc = application.getWc();
		tempWc.setEnhancedFundBased(wcFundBasedSanctionedVal
				- tempApplication.getProjectOutlayDetails()
						.getWcFundBasedSanctioned());
		tempWc.setEnhancedNonFundBased(wcNonFundBasedSanctionedVal
				- tempApplication.getProjectOutlayDetails()
						.getWcNonFundBasedSanctioned());
		if ((tempApplication.getProjectOutlayDetails()
				.getWcNonFundBasedSanctioned() == 0.0D)
				&& (wcNonFundBasedSanctionedVal != 0.0D)) {
			tempWc.setLimitNonFundBasedSanctionedDate(tempWc
					.getEnhancementDate());
		} else if ((tempApplication.getProjectOutlayDetails()
				.getWcNonFundBasedSanctioned() != 0.0D)
				&& (wcNonFundBasedSanctionedVal != 0.0D)) {
			tempWc.setLimitNonFundBasedSanctionedDate(tempWc
					.getEnhancementDate());
		}
		if ((tempApplication.getProjectOutlayDetails()
				.getWcFundBasedSanctioned() == 0.0D)
				&& (wcFundBasedSanctionedVal != 0.0D)) {
			tempWc.setLimitFundBasedSanctionedDate(tempWc.getEnhancementDate());
		} else if ((tempApplication.getProjectOutlayDetails()
				.getWcFundBasedSanctioned() != 0.0D)
				&& (wcFundBasedSanctionedVal != 0.0D)) {
			tempWc.setLimitFundBasedSanctionedDate(tempWc.getEnhancementDate());
		}
		Log.log(2, "ApplicationProcessingAction", "afterWcEnhanceApp",
				"wcFundBasedSanctionedVal:" + wcFundBasedSanctionedVal);
		Log.log(2, "ApplicationProcessingAction", "afterWcEnhanceApp",
				"wcApp.getFundBasedLimitSanctioned()"
						+ tempApplication.getProjectOutlayDetails()
								.getWcFundBasedSanctioned());
		Log.log(2, "ApplicationProcessingAction", "afterWcEnhanceApp",
				"enhanced fund based:" + tempWc.getEnhancedFundBased());
		tempApplication = null;
		application.setWc(tempWc);

		application.setHandiCrafts(handiCrafts);
		application.setDcHandicrafts(dcHandicrafts);
		application.setIcardNo(icardNo);
		application.setIcardIssueDate(icardIssueDate);
		application.setDcHandlooms(dcHandlooms);

		application.setWeaverCreditScheme(WeaverCreditScheme);
		application.setHandloomchk(handloomchk);
			
		
		// Added for GST changes by DKR
		String mlibranchCode = (String) dynaForm.get("mliBranchCode");
		String gstNo = (String) dynaForm.get("gstNo");
		String gstState = (String) dynaForm.get("gstState");
		application.setGstNo(gstNo);
		application.setGstState(gstState);
		application.setMliBranchCode(mlibranchCode);

		 application.setHybridSecurity(hybridSecurity);
		 application.setMovCollateratlSecurityAmt(movColSecurityAmt);
		 application.setImmovCollateratlSecurityAmt(immovColSecurityAmt);
		 application.setTotalMIcollatSecAmt(totalMIcolSecAmt);
		 application.setProMobileNo(proMobileNo);
		
		if ((dcHandlooms != null) && (!dcHandlooms.equals("null"))) {
			if (dcHandlooms.equals("N")) {
				application.setHandloomchk("N");
				application.setWeaverCreditScheme("");
			}
		}
		if ((handiCrafts != null) && (!handiCrafts.equals("null"))) {
			if (handiCrafts.equals("N")) {
				application.setDcHandicrafts("N");
			}
		}

		application.setWcEnhancement(true);
		appProcessor.submitWcEnhancement(application, userId);     // ENHANCEMENT WC

		application = null;
		appProcessor = null;
		ssiDetails = null;
		borrowerDetails = null;
		termLoan = null;
		workingCapital = null;
		primarySecurityDetails = null;
		projectOutlayDetails = null;
		user = null;
		userId = null;
		bankId = null;
		zoneId = null;
		branchId = null;

		request.setAttribute("message",
				"Enhancement of Working Capital completed Successfully");

		return mapping.findForward("success");
	}

	public ActionForward newSplMessage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionForm dynaForm = (DynaActionForm) form;
		dynaForm.initialize(mapping);

		HttpSession session = request.getSession(false);
		session.setAttribute("SPL_MESSAGE_FLAG", "1");

		return mapping.findForward("specialMessagePage");
	}

	public ActionForward updateSpecialMessage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(false);
		session.setAttribute("SPL_MESSAGE_FLAG", "0");

		ApplicationProcessor appProcessor = new ApplicationProcessor();
		DynaActionForm dynaForm = (DynaActionForm) form;
		dynaForm.initialize(mapping);

		ArrayList msgTitlesContentsList = appProcessor.getMessageTitleContent();
		ArrayList msgTitlesList = (ArrayList) msgTitlesContentsList.get(0);

		dynaForm.set("msgTitlesList", msgTitlesList);

		msgTitlesList = null;
		msgTitlesContentsList = null;
		appProcessor = null;

		Log.log(5, "ApplicationProcessingAction", "updateSpecialMessage",
				"Msg Title List :" + dynaForm.get("msgTitlesList"));

		return mapping.findForward("specialMessagePage");
	}

	public ActionForward getSplTitle(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ApplicationProcessor appProcessor = new ApplicationProcessor();
		DynaActionForm dynaForm = (DynaActionForm) form;

		ArrayList msgTitlesContentsList = appProcessor.getMessageTitleContent();
		ArrayList msgTitlesList = (ArrayList) msgTitlesContentsList.get(0);

		dynaForm.set("msgTitlesList", msgTitlesList);
		Log.log(5, "ApplicationProcessingAction", "getSplTitle",
				"Msg Title List :" + dynaForm.get("msgTitlesList"));

		appProcessor = null;
		msgTitlesContentsList = null;
		msgTitlesList = null;

		return mapping.findForward("specialMessagePage");
	}

	public ActionForward getSplMessage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionForm dynaForm = (DynaActionForm) form;
		String messageTitle = (String) dynaForm.get("msgTitle");
		ApplicationProcessor appProcessor = new ApplicationProcessor();
		SpecialMessage specialMessage = new SpecialMessage();
		if (messageTitle.equals("")) {
			specialMessage = new SpecialMessage();
		} else {
			specialMessage = appProcessor.getMessageDesc(messageTitle);
			specialMessage.setMsgTitle(messageTitle);
		}

		BeanUtils.copyProperties(dynaForm, specialMessage);

		appProcessor = null;
		specialMessage = null;
		messageTitle = null;

		return mapping.findForward("specialMessagePage");
	}

	public ActionForward insertSplMessage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaActionForm dynaForm = (DynaActionForm) form;
		ApplicationProcessor appProcessor = new ApplicationProcessor();
		SpecialMessage specialMessage = new SpecialMessage();

		BeanUtils.populate(specialMessage, dynaForm.getMap());

		HttpSession session = request.getSession(false);
		String sessionFlag = (String) session.getAttribute("SPL_MESSAGE_FLAG");
		if (sessionFlag.equals("1")) {
			appProcessor.addSpecialMessage(specialMessage);
			request.setAttribute("message",
					"Special Message Inserted Successfully");
		} else if (sessionFlag.equals("0")) {
			appProcessor.updateSpecialMessage(specialMessage);
			request.setAttribute("message",
					"Special Message Updated Successfully");
		}

		appProcessor = null;
		specialMessage = null;

		return mapping.findForward("success");
	}

	public ActionForward updateSplMessage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaActionForm dynaForm = (DynaActionForm) form;
		SpecialMessage specialMessage = new SpecialMessage();
		ApplicationProcessor appProcessor = new ApplicationProcessor();

		BeanUtils.populate(specialMessage, dynaForm.getMap());

		appProcessor.addSpecialMessage(specialMessage);

		appProcessor = null;
		specialMessage = null;

		request.setAttribute("message", "Special Message Updated Successfully");

		return mapping.findForward("success");
	}

	public ActionForward getBorrowerDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaActionForm dynaForm = (DynaActionForm) form;

		HttpSession bidSession = request.getSession(false);
		String applicationType = (String) bidSession
				.getAttribute("APPLICATION_LOAN_TYPE");

		ApplicationProcessor appProcessor = new ApplicationProcessor();

		String cgbid = "";
		String cgpan = "";
		String value = (String) dynaForm.get("unitValue");

		Log.log(4, "ApplicationProcessingAction", "getBorrowerDetails",
				"Value :" + value);

		Log.log(4, "ApplicationProcessingAction", "getBorrowerDetails",
				"Value :" + dynaForm.get("none"));

		User user = getUserInformation(request);
		String bankId = user.getBankId();
		String mliId = "";
		if (bankId.equals("0000")) {
			mliId = (String) dynaForm.get("selectMember");
		} else {
			MLIInfo mliInfo = getMemberInfo(request);
			bankId = mliInfo.getBankId();
			String branchId = mliInfo.getBranchId();
			String zoneId = mliInfo.getZoneId();
			mliId = bankId + zoneId + branchId;
		}
		Application application;
		if (dynaForm.get("none").equals("cgbid")) {
			cgbid = value;
			dynaForm.set("unitValue", cgbid);
			dynaForm.set("none", "cgbid");

			ClaimsProcessor cpProcessor = new ClaimsProcessor();

			ArrayList borrowerIds = cpProcessor.getAllBorrowerIDs(mliId);

			if (!borrowerIds.contains(cgbid)) {
				throw new NoDataException(
						"The Borrower ID does not exist for this Member ID");
			}

			if ((cgbid != null) && (!cgbid.equals(""))) {
				int claimCount = appProcessor.getClaimCount(cgbid);
				if (claimCount > 0) {
					throw new MessageException(
							"Application cannot be filed by this borrower since Claim Application has been submitted");
				}

			}

		} else if (dynaForm.get("none").equals("cgpan")) {
			ClaimsProcessor claimProcessor = new ClaimsProcessor();
			cgpan = value;
			dynaForm.set("unitValue", cgpan);
			dynaForm.set("none", "cgpan");

			if ((cgpan != null) && (!cgpan.equals(""))) {
				String tempCgbid = claimProcessor.getBorowwerForCGPAN(cgpan);

				int claimCount = appProcessor.getClaimCount(tempCgbid);
				if (claimCount > 0) {
					throw new MessageException(
							"Application cannot be filed by this borrower since Claim Application has been submitted");
				}

			}

			application = appProcessor.getAppForCgpan(mliId, cgpan);
		}

		Log.log(5, "ApplicationProcessingAction", "getBorrowerDetails",
				"Cgbid from dynaForm :" + cgbid);

		Log.log(5, "ApplicationProcessingAction", "getBorrowerDetails",
				"Cgpan from dynaForm :" + cgpan);

		Administrator admin = new Administrator();

		BorrowerDetails borrowerDetails = new BorrowerDetails();
		SSIDetails ssiDetails = new SSIDetails();

		borrowerDetails = appProcessor.fetchBorrowerDetailsNew(cgbid, cgpan,
				applicationType);

		ssiDetails = borrowerDetails.getSsiDetails();

		Application tempApplication = new Application();
		tempApplication.setBorrowerDetails(borrowerDetails);
		tempApplication.setMliID(mliId);

		double balanceAppAmt = appProcessor
				.getBalanceApprovedAmt(tempApplication);

		BeanUtils.copyProperties(dynaForm, ssiDetails);

		BeanUtils.copyProperties(dynaForm, borrowerDetails);

		dynaForm.set("balanceApprovedAmt", new Double(balanceAppAmt));

		dynaForm.set("previouslyCovered", "Y");

		ArrayList statesList = (ArrayList) getStateList();
		dynaForm.set("statesList", statesList);

		String state = (String) dynaForm.get("state");

		ArrayList districtList = admin.getAllDistricts(state);
		dynaForm.set("districtList", districtList);

		String districtName = ssiDetails.getDistrict();

		if (districtList.contains(districtName)) {
			dynaForm.set("district", districtName);
		} else {
			dynaForm.set("districtOthers", districtName);
			dynaForm.set("district", "Others");
		}

		Log.log(4, "ApplicationProcessingAction", "getBorrowerDetails",
				"Set Application Details...");

		dynaForm.set("industryNature", ssiDetails.getIndustryNature());

		dynaForm.set("industrySector", ssiDetails.getIndustrySector());

		bidSession.setAttribute("APPLICATION_TYPE_FLAG", "17");

		String forward = setApps(applicationType);

		applicationType = null;

		appProcessor = null;
		admin = null;
		ssiDetails = null;
		borrowerDetails = null;
		statesList = null;
		districtList = null;

		return mapping.findForward(forward);
	}

	public ActionForward showDuplicates(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession(false);
		session.removeAttribute("APPLICATION_LOAN_TYPE");
		session.setAttribute("APPLICATION_TYPE", "DUP");

		ApplicationProcessor appProcessor = new ApplicationProcessor();
		APForm apForm = (APForm) form;

		String forward = "";

		String mliFlag = apForm.getWithinMlis();
		if (mliFlag.equals("W")) {
			mliFlag = "W";
		} else if (mliFlag.equals("A")) {
			mliFlag = "A";
		}

		ArrayList duplicateApp = new ArrayList();
		ArrayList duplicateApplications = appProcessor.checkDuplicate(mliFlag);
		ArrayList tcDuplicateApp = (ArrayList) duplicateApplications.get(0);
		ArrayList wcDuplicateApp = (ArrayList) duplicateApplications.get(1);

		for (int i = 0; i < tcDuplicateApp.size(); i++) {
			DuplicateApplication duplicateApplication = (DuplicateApplication) tcDuplicateApp
					.get(i);
			duplicateApp.add(duplicateApplication);
		}
		for (int j = 0; j < wcDuplicateApp.size(); j++) {
			DuplicateApplication duplicateApplication = (DuplicateApplication) wcDuplicateApp
					.get(j);
			duplicateApp.add(duplicateApplication);
		}

		if ((duplicateApp == null) || (duplicateApp.size() == 0)) {
			request.setAttribute("message", "No Duplicate Application Found");
			forward = "success";
		} else {
			apForm.setDuplicateApplications(duplicateApp);
			forward = "duplicatePage";
		}

		appProcessor = null;
		duplicateApp = null;

		return mapping.findForward(forward);
	}

	public ActionForward afterApprovalApps(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ApplicationProcessingAction", "afterApprovalApps",
				"Entered");

		ApplicationProcessor appProcessor = new ApplicationProcessor();
		ApplicationDAO applicationDAO = new ApplicationDAO();
		Application applicationNew = new Application();

		String message = "";
		String subject = "";

		User user = getUserInformation(request);

		String userId = user.getUserId();

		APForm apForm = (APForm) form;

		ArrayList clearApprovedApplications = new ArrayList();
		ArrayList clearHoldApplications = new ArrayList();
		ArrayList clearRejectedApplications = new ArrayList();
		ArrayList clearPendingApplications = new ArrayList();

		Map clearAppRefNos = apForm.getClearAppRefNo();
		Map clearRemarks = apForm.getClearRemarks();
		Map clearStatus = apForm.getClearStatus();
		Map clearApprovedAmt = apForm.getClearApprovedAmt();
		Map clearRsfApprovedAmt = apForm.getClearRsfApprovedAmt();
		Set clearAppRefNosSet = clearAppRefNos.keySet();
		Set clearRemarksSet = clearRemarks.keySet();
		Set clearStatusSet = clearStatus.keySet();
		Set clearApprovedAmtSet = clearApprovedAmt.keySet();
		Set clearRsfApprovedAmtSet = clearRsfApprovedAmt.keySet();
		Iterator clearAppRefNosIterator = clearAppRefNosSet.iterator();

		while (clearAppRefNosIterator.hasNext()) {
			Application application = new Application();

			String key = (String) clearAppRefNosIterator.next();

			String appRefNumber = (String) clearAppRefNos.get(key);

			Log.log(4, "ApplicationProcessingAction", "afterApprovalApps",
					"app ref no :" + appRefNumber);
			String approvedStatus = (String) clearStatus.get(key);

			Log.log(4, "ApplicationProcessingAction", "afterApprovalApps",
					"status:" + approvedStatus);

			String mliId = null;
			String cgpan = "";
			String cgbid = "";
			application = appProcessor.getApplication(mliId, cgpan,
					appRefNumber);

			applicationNew = applicationDAO
					.getAppForAppRef(mliId, appRefNumber);
			String remarks;
			if (approvedStatus.equals("AP")) {
				Log.log(4, "ApplicationProcessingAction", "afterApprovalApps",
						"entering approved applications");

				double approvedAmount = Double
						.parseDouble((String) clearApprovedAmt.get(key));

				Log.log(4, "ApplicationProcessingAction", "afterApprovalApps",
						"approved amount:" + approvedAmount);

				if ((clearRemarks.get(key) != null)
						&& (!clearRemarks.get(key).equals(""))) {
					remarks = (String) clearRemarks.get(key);
				} else {
					remarks = "";
				}

				application.setAppRefNo(appRefNumber);

				application.setApprovedAmount(approvedAmount);

				application.setRemarks(remarks);

				application.setStatus(approvedStatus);

				if ((application.getCgpanReference() != null)
						&& (!application.getCgpanReference().equals(""))
						&& (application.getCgpanReference().startsWith("CG"))
						&& (application.getLoanType().equals("WC"))) {
					Application testApplication = appProcessor
							.getPartApplication(null,
									application.getCgpanReference(), "");
					if (testApplication.getStatus().equals("EX")) {
						String renewCgpan = application.getCgpanReference();

						cgpan = appProcessor.generateRenewCgpan(renewCgpan);

						application.setCgpan(cgpan);

						application.setAdditionalTC(true);
						application.setWcRenewal(true);

						int applicationRefNoCount = applicationDAO
								.getAppRefNoCount(application.getAppRefNo());

						if ((applicationNew.getStatus().equals("MO"))
								&& (applicationRefNoCount >= 1)) {
							appProcessor.updateRejectedApplicationsStatus(
									application, userId);
						} else {
							appProcessor.updateApplicationsStatus(application,
									userId);
						}

					}

					testApplication = null;
				} else {
					appProcessor.updateAppCgpanReference(application);

					cgpan = appProcessor.generateCgpan(application);

					application.setCgpan(cgpan);
					//System.out.println("Cgpan:" + cgpan);

					int applicationRefNoCount = applicationDAO
							.getAppRefNoCount(application.getAppRefNo());

					if ((applicationNew.getStatus().equals("MO"))
							&& (applicationRefNoCount >= 1)) {
						appProcessor.updateRejectedApplicationsStatus(
								application, userId);
					} else {
						appProcessor.updateApplicationsStatus(application,
								userId);
					}

				}

				clearApprovedApplications.add(application);
			} else if (approvedStatus.equals("HO")) {
				if ((clearRemarks.get(key) != null)
						&& (!clearRemarks.get(key).equals(""))) {
					remarks = (String) clearRemarks.get(key);
				} else {
					remarks = "";
				}

				application.setAppRefNo(appRefNumber);
				application.setRemarks(remarks);
				application.setStatus(approvedStatus);

				appProcessor.updateGeneralStatus(application, userId);

				clearHoldApplications.add(application);

				message = "The Application Reference No. :" + appRefNumber
						+ " has been put on hold because " + remarks;

				subject = "Status of Application Reference No. :"
						+ appRefNumber;

				sendMailEmail(message, application.getMliID(), user, subject);
			} else if (approvedStatus.equals("RE")) {
				if ((clearRemarks.get(key) != null)
						&& (!clearRemarks.get(key).equals(""))) {
					remarks = (String) clearRemarks.get(key);
				} else {
					remarks = "";
				}

				application.setAppRefNo(appRefNumber);
				application.setRemarks(remarks);
				application.setStatus(approvedStatus);

				appProcessor.updatePendingRejectedStatus(application, userId);

				clearRejectedApplications.add(application);

				message = "The Application Reference No. :" + appRefNumber
						+ " has been rejected because " + remarks;

				subject = "Status of Application Reference No. :"
						+ appRefNumber;

				sendMailEmail(message, application.getMliID(), user, subject);
			} else if (approvedStatus.equals("PE")) {
				if ((clearRemarks.get(key) != null)
						&& (!clearRemarks.get(key).equals(""))) {
					remarks = (String) clearRemarks.get(key);
				} else {
					remarks = "";
				}

				application.setAppRefNo(appRefNumber);
				application.setRemarks(remarks);
				application.setStatus(approvedStatus);

				appProcessor.updatePendingRejectedStatus(application, userId);

				clearPendingApplications.add(application);
			}

			application = null;
		}
		apForm.setClearApprovedApplications(clearApprovedApplications);
		apForm.setClearHoldApplications(clearHoldApplications);
		apForm.setClearRejectedApplications(clearRejectedApplications);
		apForm.setClearPendingApplications(clearPendingApplications);

		ArrayList dupApprovedApplications = new ArrayList();
		ArrayList dupHoldApplications = new ArrayList();
		ArrayList dupRejectedApplications = new ArrayList();
		ArrayList dupPendingApplications = new ArrayList();

		Map duplicateAppRefNos = apForm.getDuplicateAppRefNo();
		Map duplicateRemarks = apForm.getDuplicateRemarks();
		Map duplicateStatus = apForm.getDuplicateStatus();
		Map duplicateApprovedAmt = apForm.getDuplicateApprovedAmt();

		Set duplicateAppRefNosSet = duplicateAppRefNos.keySet();
		Set duplicateRemarksSet = duplicateRemarks.keySet();
		Set duplicateStatusSet = duplicateStatus.keySet();
		Set duplicateApprovedAmtSet = duplicateApprovedAmt.keySet();

		Iterator duplicateAppRefNosIterator = duplicateAppRefNosSet.iterator();

		while (duplicateAppRefNosIterator.hasNext()) {
			Application application = new Application();

			String key = (String) duplicateAppRefNosIterator.next();

			String appRefNumber = (String) duplicateAppRefNos.get(key);

			Log.log(4, "ApplicationProcessingAction", "afterApprovalApps",
					"app ref no :" + appRefNumber);
			String status = (String) duplicateStatus.get(key);

			String mliId = null;
			String cgpan = "";
			String cgbid = "";
			application = appProcessor.getApplication(mliId, cgpan,
					appRefNumber);
			applicationNew = applicationDAO
					.getAppForAppRef(mliId, appRefNumber);

			String appStatus = application.getStatus();
			String remarks;
			if (status.equals("AP")) {
				double approvedAmount = Double
						.parseDouble((String) duplicateApprovedAmt.get(key));

				if ((duplicateRemarks.get(key) != null)
						&& (!duplicateRemarks.get(key).equals(""))) {
					remarks = (String) duplicateRemarks.get(key);
				} else {
					remarks = "";
				}

				application.setAppRefNo(appRefNumber);
				application.setApprovedAmount(approvedAmount);
				application.setRemarks(remarks);

				if ((application.getStatus().equals("EN"))
						|| (application.getStatus().equals("EP"))
						|| (application.getStatus().equals("EH"))) {
					ArrayList countAmount = appProcessor
							.getCountForDanGen(appRefNumber);
					int countDan = ((Integer) countAmount.get(0)).intValue();

					double enhancedAmount = ((Double) countAmount.get(1))
							.doubleValue();

					application.setCgpan((String) countAmount.get(2));

					if (countDan > 0) {
						application.setApprovedAmount(approvedAmount
								- enhancedAmount);

						application.setEnhancementAmount(approvedAmount
								- enhancedAmount);

						appProcessor.generateDanForEnhance(application, user);
					}

					application.setStatus("AP");

					application.setApprovedAmount(approvedAmount);

					appProcessor.updateEnhanceAppStatus(application, userId);
				} else if ((application.getCgpanReference() != null)
						&& (!application.getCgpanReference().equals(""))
						&& (application.getCgpanReference().startsWith("CG"))
						&& (application.getLoanType().equals("WC"))) {
					Application testApplication = appProcessor
							.getPartApplication(null,
									application.getCgpanReference(), "");
					if (testApplication.getStatus().equals("EX")) {
						String renewCgpan = application.getCgpanReference();

						cgpan = appProcessor.generateRenewCgpan(renewCgpan);

						application.setCgpan(cgpan);

						application.setAdditionalTC(true);
						application.setWcRenewal(true);

						application.setStatus("AP");

						appProcessor.updateApplicationsStatus(application,
								userId);
					}

					testApplication = null;
				} else {
					appProcessor.updateAppCgpanReference(application);

					cgpan = appProcessor.generateCgpan(application);

					application.setCgpan(cgpan);

					application.setStatus("AP");

					if (applicationNew.getStatus().equals("MO")) {
						appProcessor.updateRejectedApplicationsStatus(
								application, userId);
					} else {
						appProcessor.updateApplicationsStatus(application,
								userId);
					}

				}

				dupApprovedApplications.add(application);
			} else if (status.equals("HO")) {
				if ((duplicateRemarks.get(key) != null)
						&& (!duplicateRemarks.get(key).equals(""))) {
					remarks = (String) duplicateRemarks.get(key);
				} else {
					remarks = "";
				}

				application.setAppRefNo(appRefNumber);
				application.setRemarks(remarks);

				if ((application.getStatus().equals("EN"))
						|| (application.getStatus().equals("EH"))
						|| (application.getStatus().equals("EP"))) {
					application.setStatus("EH");
				} else {
					application.setStatus(status);
				}

				appProcessor.updateGeneralStatus(application, userId);

				dupHoldApplications.add(application);

				message = "The Application Reference No. :" + appRefNumber
						+ " has been been put on hold because " + remarks;

				subject = "Status of Application Reference No. :"
						+ appRefNumber;

				sendMailEmail(message, application.getMliID(), user, subject);
			} else if (status.equals("RE")) {
				Log.log(4,
						"ApplicationProcessingAction",
						"afterApprovalApps",
						"duplicateRemarks.get(key):"
								+ duplicateRemarks.get(key));

				if ((duplicateRemarks.get(key) != null)
						&& (!duplicateRemarks.get(key).equals(""))) {
					remarks = (String) duplicateRemarks.get(key);
				} else {
					remarks = "";
				}

				application.setAppRefNo(appRefNumber);
				application.setRemarks(remarks);

				if ((application.getStatus().equals("EN"))
						|| (application.getStatus().equals("EH"))
						|| (application.getStatus().equals("EP"))) {
					application.setStatus("ER");
					appProcessor.updateRejectStatus(application, userId);
				} else {
					application.setStatus(status);
					appProcessor.updatePendingRejectedStatus(application,
							userId);
				}

				dupRejectedApplications.add(application);

				message = "The Application Reference No. :" + appRefNumber
						+ " has been been rejected because " + remarks;

				subject = "Status of Application Reference No. :"
						+ appRefNumber;

				sendMailEmail(message, application.getMliID(), user, subject);
			} else if (status.equals("PE")) {
				if ((duplicateRemarks.get(key) != null)
						&& (!duplicateRemarks.get(key).equals(""))) {
					remarks = (String) duplicateRemarks.get(key);
				} else {
					remarks = "";
				}

				application.setAppRefNo(appRefNumber);
				application.setRemarks(remarks);

				if ((application.getStatus().equals("EN"))
						|| (application.getStatus().equals("EP"))
						|| (application.getStatus().equals("EH"))) {
					application.setStatus("EP");
					appProcessor.updateGeneralStatus(application, userId);
				} else {
					application.setStatus(status);
					appProcessor.updatePendingRejectedStatus(application,
							userId);
				}

				dupPendingApplications.add(application);
			}

			application = null;
		}
		apForm.setDupApprovedApplications(dupApprovedApplications);
		apForm.setDupHoldApplications(dupHoldApplications);
		apForm.setDupRejectedApplications(dupRejectedApplications);
		apForm.setDupPendingApplications(dupPendingApplications);

		ArrayList ineligibleApprovedApplications = new ArrayList();
		ArrayList ineligibleHoldApplications = new ArrayList();
		ArrayList ineligibleRejectedApplications = new ArrayList();
		ArrayList ineligiblePendingApplications = new ArrayList();

		Map ineligibleAppRefNos = apForm.getIneligibleAppRefNo();
		Map ineligibleRemarks = apForm.getIneligibleRemarks();
		Map ineligibleStatus = apForm.getIneligibleStatus();
		Map ineligibleApprovedAmt = apForm.getIneligibleApprovedAmt();

		Set ineligibleAppRefNosSet = ineligibleAppRefNos.keySet();
		Set ineligibleRemarksSet = ineligibleRemarks.keySet();
		Set ineligibleStatusSet = ineligibleStatus.keySet();
		Set ineligibleApprovedAmtSet = ineligibleApprovedAmt.keySet();

		Iterator ineligibleAppRefNosIterator = ineligibleAppRefNosSet
				.iterator();

		while (ineligibleAppRefNosIterator.hasNext()) {
			Application application = new Application();

			String key = (String) ineligibleAppRefNosIterator.next();

			String appRefNumber = (String) ineligibleAppRefNos.get(key);
			Log.log(4, "ApplicationProcessingAction", "afterApprovalApps",
					"app ref no :" + appRefNumber);
			String status = (String) ineligibleStatus.get(key);
			Log.log(4, "ApplicationProcessingAction", "afterApprovalApps",
					"status:" + status);

			String mliId = null;
			String cgpan = "";
			String cgbid = "";
			application = appProcessor.getApplication(mliId, cgpan,
					appRefNumber);

			String appStatus = application.getStatus();
			Log.log(4, "ApplicationProcessingAction", "afterApprovalApps",
					"appStatus:" + appStatus);
			Log.log(4, "ApplicationProcessingAction", "afterApprovalApps",
					"status:" + status);
			String remarks;
			if (status.equals("AP")) {
				Log.log(4, "ApplicationProcessingAction", "afterApprovalApps",
						"entering approved applications");
				double approvedAmount;
				if (ineligibleApprovedAmt.get(key) != null) {
					approvedAmount = Double
							.parseDouble((String) ineligibleApprovedAmt
									.get(key));
				} else {
					approvedAmount = 0.0D;
				}

				if ((ineligibleRemarks.get(key) != null)
						&& (!ineligibleRemarks.get(key).equals(""))) {
					remarks = (String) ineligibleRemarks.get(key);
				} else {
					remarks = "";
				}

				application.setAppRefNo(appRefNumber);
				application.setApprovedAmount(approvedAmount);
				application.setRemarks(remarks);

				if ((application.getStatus().equals("EN"))
						|| (application.getStatus().equals("EP"))
						|| (application.getStatus().equals("EH"))) {
					ArrayList countAmount = appProcessor
							.getCountForDanGen(appRefNumber);
					int countDan = ((Integer) countAmount.get(0)).intValue();
					double enhancedAmount = ((Double) countAmount.get(1))
							.doubleValue();

					Log.log(4, "ApplicationProcessingAction",
							"afterApprovalApps", "countDan:" + countDan);
					Log.log(4, "ApplicationProcessingAction",
							"afterApprovalApps", "enhancedAmount:"
									+ enhancedAmount);
					application.setCgpan((String) countAmount.get(2));

					if (countDan > 0) {
						application.setApprovedAmount(approvedAmount
								- enhancedAmount);

						appProcessor.generateDanForEnhance(application, user);
					}

					application.setStatus("AP");

					application.setApprovedAmount(approvedAmount);

					appProcessor.updateEnhanceAppStatus(application, userId);
				} else if ((application.getCgpanReference() != null)
						&& (!application.getCgpanReference().equals(""))
						&& (application.getCgpanReference().startsWith("CG"))
						&& (application.getLoanType().equals("WC"))) {
					Application testApplication = appProcessor
							.getPartApplication(null,
									application.getCgpanReference(), "");
					if (testApplication.getStatus().equals("EX")) {
						String renewCgpan = application.getCgpanReference();

						cgpan = appProcessor.generateRenewCgpan(renewCgpan);

						application.setCgpan(cgpan);

						application.setAdditionalTC(true);
						application.setWcRenewal(true);

						application.setStatus("AP");

						appProcessor.updateApplicationsStatus(application,
								userId);
					}

					testApplication = null;
				} else {
					appProcessor.updateAppCgpanReference(application);

					cgpan = appProcessor.generateCgpan(application);

					application.setCgpan(cgpan);

					application.setStatus("AP");

					appProcessor.updateApplicationsStatus(application, userId);
				}

				ineligibleApprovedApplications.add(application);
			} else if (status.equals("HO")) {
				if ((ineligibleRemarks.get(key) != null)
						&& (!ineligibleRemarks.get(key).equals(""))) {
					remarks = (String) ineligibleRemarks.get(key);
				} else {
					remarks = "";
				}

				application.setAppRefNo(appRefNumber);
				application.setRemarks(remarks);

				if ((application.getStatus().equals("EN"))
						|| (application.getStatus().equals("EH"))
						|| (application.getStatus().equals("EP"))) {
					application.setStatus("EH");
				} else {
					application.setStatus(status);
				}

				appProcessor.updateGeneralStatus(application, userId);

				ineligibleHoldApplications.add(application);

				message = "The Application Reference No. :" + appRefNumber
						+ " has been been put on hold beacuse " + remarks;

				subject = "Status of Application Reference No. :"
						+ appRefNumber;

				sendMailEmail(message, application.getMliID(), user, subject);
			} else if (status.equals("RE")) {
				if ((ineligibleRemarks.get(key) != null)
						&& (!ineligibleRemarks.get(key).equals(""))) {
					remarks = (String) ineligibleRemarks.get(key);
				} else {
					remarks = "";
				}

				application.setAppRefNo(appRefNumber);
				application.setRemarks(remarks);
				if ((application.getStatus().equals("EN"))
						|| (application.getStatus().equals("EH"))
						|| (application.getStatus().equals("EP"))) {
					application.setStatus("ER");
					appProcessor.updateRejectStatus(application, userId);
				} else {
					application.setStatus(status);
					appProcessor.updatePendingRejectedStatus(application,
							userId);
				}

				ineligibleRejectedApplications.add(application);

				message = "The Application Reference No. :" + appRefNumber
						+ " has been been rejected because " + remarks;

				subject = "Status of Application Reference No. :"
						+ appRefNumber;

				sendMailEmail(message, application.getMliID(), user, subject);
			} else if (status.equals("PE")) {
				if ((ineligibleRemarks.get(key) != null)
						&& (!ineligibleRemarks.get(key).equals(""))) {
					remarks = (String) ineligibleRemarks.get(key);
				} else {
					remarks = "";
				}

				application.setAppRefNo(appRefNumber);
				application.setRemarks(remarks);
				if ((application.getStatus().equals("EN"))
						|| (application.getStatus().equals("EP"))
						|| (application.getStatus().equals("EH"))) {
					application.setStatus("EP");
					appProcessor.updateGeneralStatus(application, userId);
				} else {
					application.setStatus(status);
					appProcessor.updatePendingRejectedStatus(application,
							userId);
				}

				ineligiblePendingApplications.add(application);
			}

			application = null;
		}
		apForm.setIneligibleApprovedApplications(ineligibleApprovedApplications);
		apForm.setIneligibleHoldApplications(ineligibleHoldApplications);
		apForm.setIneligibleRejectedApplications(ineligibleRejectedApplications);
		apForm.setIneligiblePendingApplications(ineligiblePendingApplications);

		ArrayList ineligibleDupApprovedApplications = new ArrayList();
		ArrayList ineligibleDupHoldApplications = new ArrayList();
		ArrayList ineligibleDupRejectedApplications = new ArrayList();
		ArrayList ineligibleDupPendingApplications = new ArrayList();

		Map ineligibleDupAppRefNos = apForm.getIneligibleDupAppRefNo();
		Map ineligibleDupRemarks = apForm.getIneligibleDupRemarks();
		Map ineligibleDupStatus = apForm.getIneligibleDupStatus();
		Map ineligibleDupApprovedAmt = apForm.getIneligibleDupApprovedAmt();

		Set ineligibleDupAppRefNosSet = ineligibleDupAppRefNos.keySet();
		Set ineligibleDupRemarksSet = ineligibleDupRemarks.keySet();
		Set ineligibleDupStatusSet = ineligibleDupStatus.keySet();
		Set ineligibleDupApprovedAmtSet = ineligibleDupApprovedAmt.keySet();

		Iterator ineligibleDupAppRefNosIterator = ineligibleDupAppRefNosSet
				.iterator();

		while (ineligibleDupAppRefNosIterator.hasNext()) {
			Application application = new Application();

			String key = (String) ineligibleDupAppRefNosIterator.next();

			String appRefNumber = (String) ineligibleDupAppRefNos.get(key);
			Log.log(4, "ApplicationProcessingAction", "afterApprovalApps",
					"app ref no :" + appRefNumber);
			String status = (String) ineligibleDupStatus.get(key);
			Log.log(4, "ApplicationProcessingAction", "afterApprovalApps",
					"status:" + status);

			String mliId = null;
			String cgpan = "";
			String cgbid = "";
			application = appProcessor.getApplication(mliId, cgpan,
					appRefNumber);

			String appStatus = application.getStatus();
			Log.log(4, "ApplicationProcessingAction", "afterApprovalApps",
					"appStatus:" + appStatus);
			Log.log(4, "ApplicationProcessingAction", "afterApprovalApps",
					"status:" + status);
			String remarks;
			if (status.equals("AP")) {
				Log.log(4, "ApplicationProcessingAction", "afterApprovalApps",
						"entering approved applications");
				double approvedAmount;
				if (ineligibleDupApprovedAmt.get(key) != null) {
					approvedAmount = Double
							.parseDouble((String) ineligibleDupApprovedAmt
									.get(key));
				} else {
					approvedAmount = 0.0D;
				}

				if ((ineligibleDupRemarks.get(key) != null)
						&& (!ineligibleDupRemarks.get(key).equals(""))) {
					remarks = (String) ineligibleDupRemarks.get(key);
				} else {
					remarks = "";
				}

				Log.log(4, "ApplicationProcessingAction", "afterApprovalApps",
						"approved amount:" + approvedAmount);

				application.setAppRefNo(appRefNumber);
				application.setApprovedAmount(approvedAmount);
				application.setRemarks(remarks);

				if ((application.getStatus().equals("EN"))
						|| (application.getStatus().equals("EP"))
						|| (application.getStatus().equals("EH"))) {
					ArrayList countAmount = appProcessor
							.getCountForDanGen(appRefNumber);
					int countDan = ((Integer) countAmount.get(0)).intValue();
					double enhancedAmount = ((Double) countAmount.get(1))
							.doubleValue();

					Log.log(4, "ApplicationProcessingAction",
							"afterApprovalApps", "countDan:" + countDan);
					Log.log(4, "ApplicationProcessingAction",
							"afterApprovalApps", "enhancedAmount:"
									+ enhancedAmount);
					application.setCgpan((String) countAmount.get(2));

					if (countDan > 0) {
						application.setApprovedAmount(approvedAmount
								- enhancedAmount);

						appProcessor.generateDanForEnhance(application, user);
					}

					application.setStatus("AP");

					application.setApprovedAmount(approvedAmount);

					appProcessor.updateEnhanceAppStatus(application, userId);
				} else if ((application.getCgpanReference() != null)
						&& (!application.getCgpanReference().equals(""))
						&& (application.getCgpanReference().startsWith("CG"))
						&& (application.getLoanType().equals("WC"))) {
					Application testApplication = appProcessor
							.getPartApplication(null,
									application.getCgpanReference(), "");
					if (testApplication.getStatus().equals("EX")) {
						String renewCgpan = application.getCgpanReference();

						cgpan = appProcessor.generateRenewCgpan(renewCgpan);

						application.setCgpan(cgpan);

						application.setAdditionalTC(true);
						application.setWcRenewal(true);

						application.setStatus("AP");

						appProcessor.updateApplicationsStatus(application,
								userId);
					}

					testApplication = null;
				} else {
					appProcessor.updateAppCgpanReference(application);

					cgpan = appProcessor.generateCgpan(application);

					application.setCgpan(cgpan);

					application.setStatus("AP");

					appProcessor.updateApplicationsStatus(application, userId);
				}

				ineligibleDupApprovedApplications.add(application);
			} else if (status.equals("HO")) {
				if ((ineligibleDupRemarks.get(key) != null)
						&& (!ineligibleDupRemarks.get(key).equals(""))) {
					remarks = (String) ineligibleDupRemarks.get(key);
				} else {
					remarks = "";
				}

				application.setAppRefNo(appRefNumber);
				application.setRemarks(remarks);

				if ((application.getStatus().equals("EN"))
						|| (application.getStatus().equals("EH"))
						|| (application.getStatus().equals("EP"))) {
					application.setStatus("EH");
				} else {
					application.setStatus(status);
				}

				appProcessor.updateGeneralStatus(application, userId);

				ineligibleDupHoldApplications.add(application);

				message = "The Application Reference No. :" + appRefNumber
						+ " has been been put on hold because " + remarks;

				subject = "Status of Application Reference No. :"
						+ appRefNumber;

				sendMailEmail(message, application.getMliID(), user, subject);
			} else if (status.equals("RE")) {
				if ((ineligibleDupRemarks.get(key) != null)
						&& (!ineligibleDupRemarks.get(key).equals(""))) {
					remarks = (String) ineligibleDupRemarks.get(key);
				} else {
					remarks = "";
				}

				application.setAppRefNo(appRefNumber);
				application.setRemarks(remarks);
				if ((application.getStatus().equals("EN"))
						|| (application.getStatus().equals("EH"))
						|| (application.getStatus().equals("EP"))) {
					application.setStatus("ER");
					appProcessor.updateRejectStatus(application, userId);
				} else {
					application.setStatus(status);
					appProcessor.updatePendingRejectedStatus(application,
							userId);
				}

				ineligibleDupRejectedApplications.add(application);

				message = "The Application Reference No. :" + appRefNumber
						+ " has been been rejected because " + remarks;

				subject = "Status of Application Reference No. :"
						+ appRefNumber;

				sendMailEmail(message, application.getMliID(), user, subject);
			} else if (status.equals("PE")) {
				if ((ineligibleDupRemarks.get(key) != null)
						&& (!ineligibleDupRemarks.get(key).equals(""))) {
					remarks = (String) ineligibleDupRemarks.get(key);
				} else {
					remarks = "";
				}

				application.setAppRefNo(appRefNumber);
				application.setRemarks(remarks);
				if ((application.getStatus().equals("EN"))
						|| (application.getStatus().equals("EP"))
						|| (application.getStatus().equals("EH"))) {
					application.setStatus("EP");
					appProcessor.updateGeneralStatus(application, userId);
				} else {
					application.setStatus(status);
					appProcessor.updatePendingRejectedStatus(application,
							userId);
				}

				ineligibleDupPendingApplications.add(application);
			}

			application = null;
		}

		apForm.setIneligibleDupApprovedApplications(ineligibleDupApprovedApplications);
		apForm.setIneligibleDupHoldApplications(ineligibleDupHoldApplications);
		apForm.setIneligibleDupRejectedApplications(ineligibleDupRejectedApplications);
		apForm.setIneligibleDupPendingApplications(ineligibleDupPendingApplications);

		return mapping.findForward("afterApprovalPage");
	}

	private void sendMailEmail(String message, String mliId, User user,
			String subject) throws DatabaseException {
		Log.log(4, "ApplicationProcessingAction", "sendMailEmail", "entering");

		Administrator administrator = new Administrator();
		MLIInfo mliInfo = new MLIInfo();
		RegistrationDAO registrationDAO = new RegistrationDAO();
		Mailer mailer = new Mailer();
		ArrayList users = new ArrayList();

		User mailUser = null;

		String fromEmail = user.getUserId();
		try {
			users = administrator.getAllUsers(mliId);
			Log.log(4, "ApplicationProcessingAction", "sendMailEmail",
					"users size :" + users.size());
		} catch (NoUserFoundException exception) {
			Log.log(3, "ApplicationProcessingAction", "sendMailEmail",
					"Exception getting user details for the MLI. Error="
							+ exception.getMessage());
		} catch (DatabaseException exception) {
			Log.log(3, "ApplicationProcessingAction", "sendMailEmail",
					"Exception getting user details for the MLI. Error="
							+ exception.getMessage());
		}
		mliInfo = registrationDAO.getMemberDetails(mliId.substring(0, 4),
				mliId.substring(4, 8), mliId.substring(8, 12));
		int userSize = users.size();
		ArrayList emailIds = new ArrayList();
		ArrayList mailIds = new ArrayList();

		for (int j = 0; j < userSize; j++) {
			mailUser = (User) users.get(j);
			emailIds.add(mailUser.getUserId());
			Log.log(4, "ApplicationProcessingAction", "sendMailEmail",
					"email ID:" + mailUser.getUserId());
			mailIds.add(mailUser.getEmailId());

			Log.log(5, "ApplicationProcessingAction", "sendMailEmail",
					"Member Id" + mliId + ", User mail " + mailUser.getUserId());
			Log.log(5,
					"ApplicationProcessingAction",
					"sendMailEmail",
					"Member Id" + mliId + ", User email "
							+ mailUser.getEmailId());
		}

		if (emailIds != null) {
			Log.log(5, "ApplicationProcessingAction", "sendMailEmail",
					"Before instantiating message");
			Log.log(5, "ApplicationProcessingAction", "sendMailEmail",
					"Subject = " + message);
			Log.log(5, "ApplicationProcessingAction", "sendMailEmail",
					"Email Message = " + message);
			Message mailMessage = new Message(emailIds, null, null, subject,
					message);

			mailMessage.setFrom(fromEmail);

			administrator.sendMail(mailMessage);

			Log.log(5, "ApplicationProcessingAction", "sendMailEmail",
					"After instantiating message");
		}

		if (mailIds != null) {
			Log.log(5, "ApplicationProcessingAction", "sendMailEmail",
					"Before instantiating message");
			Log.log(5, "ApplicationProcessingAction", "sendMailEmail",
					"Subject = " + subject);
			Log.log(5, "ApplicationProcessingAction", "sendMailEmail",
					"Email Message = " + message);
			Message mailMessage = new Message(mailIds, null, null, subject,
					message);

			mailMessage.setFrom(fromEmail);

			Log.log(5, "ApplicationProcessingAction", "sendMailEmail",
					"After instantiating message");
		}

		message = "";
	}

	public ActionForward showAppsForReApproval(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(false);
		session.removeAttribute("APPLICATION_LOAN_TYPE");
		session.setAttribute("APPLICATION_TYPE", "REAPP");

		APForm apForm = (APForm) form;

		apForm.resetReApproveMaps();
		String forward = "";

		TreeMap ineligibleMap = new TreeMap();
		TreeMap eligibleMap = new TreeMap();

		User user = getUserInformation(request);

		String userId = user.getUserId();

		ApplicationProcessor appProcessor = new ApplicationProcessor();
		ArrayList appsForReApproval = appProcessor
				.getApplicationsForReapproval(userId);

		Log.log(4, "ApplicationProcessingAction", "showAppsForReApproval",
				"reapproval apps size :" + appsForReApproval.size());

		ArrayList tcReApprovalApps = (ArrayList) appsForReApproval.get(0);
		for (int i = 0; i < tcReApprovalApps.size(); i++) {
			EligibleApplication eligibleApplication = (EligibleApplication) tcReApprovalApps
					.get(i);
			ineligibleMap.put(eligibleApplication.getCgpan(),
					eligibleApplication.getStatus());
			apForm.setIneligibleReapproveMap(ineligibleMap);
		}

		Log.log(4, "ApplicationProcessingAction", "showAppsForReApproval",
				"reapproval apps size :" + tcReApprovalApps.size());
		ArrayList wcReApprovalApps = (ArrayList) appsForReApproval.get(1);

		for (int i = 0; i < wcReApprovalApps.size(); i++) {
			Application tempApplication = (Application) wcReApprovalApps.get(i);
			eligibleMap.put(tempApplication.getCgpan(),
					tempApplication.getStatus());
			apForm.setEligibleReapproveMap(eligibleMap);
		}

		Log.log(4, "ApplicationProcessingAction", "showAppsForReApproval",
				"reapproval apps size :" + wcReApprovalApps.size());
		if (((tcReApprovalApps.size() != 0) && (tcReApprovalApps != null))
				|| ((wcReApprovalApps.size() != 0) && (wcReApprovalApps != null))) {
			apForm.setTcClearApplications(tcReApprovalApps);

			apForm.setWcClearApplications(wcReApprovalApps);

			tcReApprovalApps = null;
			wcReApprovalApps = null;

			forward = "reApprovalPage";
		} else {
			request.setAttribute("message",
					"No Pending Applications For ReApproval");
			forward = "success";
		}

		appProcessor = null;
		user = null;
		appsForReApproval = null;

		return mapping.findForward(forward);
	}

	public ActionForward afterReApprovalApps(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ApplicationProcessingAction", "updateApplicationStatus",
				"Entered");

		Application application = new Application();
		ApplicationProcessor appProcessor = new ApplicationProcessor();

		RpProcessor rpProcessor = new RpProcessor();

		User user = getUserInformation(request);

		String userId = user.getUserId();

		ArrayList acceptedApplications = new ArrayList();
		ArrayList holdApplications = new ArrayList();
		ArrayList rejectedApplications = new ArrayList();

		Map cgpanNo = new TreeMap();
		Map reapprovedStatus = new TreeMap();
		Map reapprovedAmt = new TreeMap();
		Map reapprovedRemarks = new TreeMap();

		APForm apForm = (APForm) form;

		cgpanNo = apForm.getCgpanNo();

		reapprovedStatus = apForm.getReapprovalStatus();

		reapprovedAmt = apForm.getReApprovedAmt();

		reapprovedRemarks = apForm.getReApprovalRemarks();

		Set cgpanSet = cgpanNo.keySet();
		Set statusSet = reapprovedStatus.keySet();
		Set amountSet = reapprovedAmt.keySet();
		Set remarksSet = reapprovedRemarks.keySet();

		Iterator cgpanIterator = cgpanSet.iterator();

		while (cgpanIterator.hasNext()) {
			application = new Application();
			String key = (String) cgpanIterator.next();
			String cgpanNumber = (String) cgpanNo.get(key);

			String reapproveStatus = (String) reapprovedStatus.get(key);
			String reapproveComments;
			if (reapproveStatus.equals("AP")) {
				double reapprovedAmount = Double
						.parseDouble((String) reapprovedAmt.get(key));

				if ((reapprovedRemarks.get(key) != null)
						&& (!reapprovedRemarks.get(key).equals(""))) {
					reapproveComments = (String) reapprovedRemarks.get(key);
				} else {
					reapproveComments = "";
				}

				application = new Application();
				application.setCgpan(cgpanNumber);
				application.setStatus("AP");
				application.setReapprovedAmount(reapprovedAmount);
				application.setReapprovalRemarks(reapproveComments);

				rpProcessor.reapproveLoanAmount(application, reapprovedAmount,
						user, request.getSession(false).getServletContext()
								.getRealPath(""));

				appProcessor.updateReapprovalStatus(application, userId);

				acceptedApplications.add(application);
			} else if (reapproveStatus.equals("RE")) {
				application = new Application();
				Application partApplication = appProcessor.getPartApplication(
						null, cgpanNumber, "");

				application.setAppRefNo(partApplication.getAppRefNo());
				application.setCgpan(cgpanNumber);
				application.setStatus(reapproveStatus);
				application.setReapprovedAmount(0.0D);

				if ((reapprovedRemarks.get(key) != null)
						&& (!reapprovedRemarks.get(key).equals(""))) {
					reapproveComments = (String) reapprovedRemarks.get(key);
				} else {
					reapproveComments = "";
				}
				application.setReapprovalRemarks(reapproveComments);

				appProcessor.updateRejectStatus(application, userId);

				rejectedApplications.add(application);
			} else if (reapproveStatus.equals("HO")) {
				application = new Application();
				application.setCgpan(cgpanNumber);
				application.setStatus("RH");
				application.setReapprovedAmount(0.0D);

				if ((reapprovedRemarks.get(key) != null)
						&& (!reapprovedRemarks.get(key).equals(""))) {
					reapproveComments = (String) reapprovedRemarks.get(key);
				} else {
					reapproveComments = "";
				}
				application.setReapprovalRemarks(reapproveComments);

				appProcessor.updateReapprovalStatus(application, userId);

				holdApplications.add(application);
			}

		}

		apForm.setApprovedApplications(acceptedApplications);
		apForm.setHoldApplications(holdApplications);
		apForm.setRejectedApplications(rejectedApplications);

		return mapping.findForward("afterReapprovalPage");
	}

	public ActionForward showAppsForEligibility(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(false);
		session.removeAttribute("APPLICATION_LOAN_TYPE");

		session.setAttribute("APPLICATION_TYPE", "EL");

		ApplicationProcessor appProcessor = new ApplicationProcessor();

		EligibleApplication eligibleApplication = new EligibleApplication();

		ArrayList ineligibleApps = new ArrayList();

		APForm apForm = (APForm) form;

		String forward = "";

		ArrayList pendingAppList = appProcessor.getPendingApps();

		int pendingAppListSize = pendingAppList.size();

		Application application = null;

		for (int i = 0; i < pendingAppListSize; i++) {
			eligibleApplication = new EligibleApplication();

			application = (Application) pendingAppList.get(i);
			String appRefNo = application.getAppRefNo();

			String mliID = null;
			String cgpan = "";
			application = appProcessor.getApplication(mliID, cgpan, appRefNo);

			Date submittedDate = application.getSubmittedDate();
			String stringDate = submittedDate.toString();

			eligibleApplication = appProcessor
					.getAppsForEligibilityCheck(appRefNo);

			if (!eligibleApplication.getFailedCondition().equals("")) {
				eligibleApplication.setAppRefNo(appRefNo);
				eligibleApplication.setSubmissiondate(stringDate);

				ineligibleApps.add(eligibleApplication);

				eligibleApplication = null;
			}

		}

		if ((ineligibleApps != null) && (ineligibleApps.size() != 0)) {
			apForm.setEligibleAppList(ineligibleApps);
			ineligibleApps = null;

			forward = "eligibleAppPage";
		} else {
			request.setAttribute("message",
					"No InEligible Applications Available");

			forward = "success";
		}

		return mapping.findForward(forward);
	}

	public ActionForward showborrowerDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaActionForm dynaForm = (DynaActionForm) form;
		dynaForm.initialize(mapping);

		ApplicationProcessor appProcessor = new ApplicationProcessor();

		Application application = new Application();
		BorrowerDetails borrowerDetails = new BorrowerDetails();
		SSIDetails ssiDetails = new SSIDetails();

		Integer ssiRefValue = new Integer(request.getParameter("ssiRef"));

		int ssiRefNo = ssiRefValue.intValue();
		borrowerDetails = appProcessor.viewBorrowerDetails(ssiRefNo);

		ssiDetails = borrowerDetails.getSsiDetails();
		BeanUtils.copyProperties(dynaForm, ssiDetails);

		BeanUtils.copyProperties(dynaForm, borrowerDetails);

		dynaForm.set("previouslyCovered", "Y");

		appProcessor = null;
		application = null;
		ssiDetails = null;
		borrowerDetails = null;
		ssiRefValue = null;

		return mapping.findForward("borrowerPage");
	}

	public ActionForward showAppsForApprovalNew(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//System.out.println("In showAppsForApprovalNew");
		HttpSession session = request.getSession(false);
		session.removeAttribute("APPLICATION_LOAN_TYPE");
		session.setAttribute("APPLICATION_TYPE", "APP");
		User user = getUserInformation(request);
		String userId = user.getUserId();
		String forward = "";
		String bank = "";
		String bank1 = request.getParameter("Link");

		bank = bank1.replaceAll("PATH", "&");

		ApplicationProcessor appProcessor = new ApplicationProcessor();
		APForm apForm = (APForm) form;
		apForm.resetMaps();
		TreeMap tempStatusMap = new TreeMap();
		TreeMap clearStatusMap = new TreeMap();

		TreeMap clearRsfStatusMap = new TreeMap();
		TreeMap dupStatusMap = new TreeMap();
		TreeMap ineligibleStatusMap = new TreeMap();
		TreeMap tempRemarksMap = new TreeMap();
		TreeMap clearRemarksMap = new TreeMap();
		TreeMap clearRsfRemarksMap = new TreeMap();
		TreeMap dupRemarksMap = new TreeMap();
		TreeMap ineligibleRemarksMap = new TreeMap();
		Log.log(4, "ApplicationProcessingAction",
				"getApplicationsForApprovalPath",
				"Before callin getappsforapproval frm processor");
		ArrayList applicationsList = appProcessor
				.getApplicationsForApprovalPath(userId, bank);

		Integer intAppCount = (Integer) applicationsList.get(4);
		int appCount = intAppCount.intValue();

		apForm.setIntApplicationCount(appCount);
		ArrayList eligibleNonDupRsfApps = (ArrayList) applicationsList.get(5);

		ArrayList eligibleNonDupApps = (ArrayList) applicationsList.get(0);
		ArrayList eligibleDupApps = (ArrayList) applicationsList.get(1);
		Log.log(4, "ApplicationProcessingAction",
				"getApplicationsForApprovalPath",
				"After callin getappsforapproval frm processor");
		ArrayList messagesTitleList = appProcessor.getMessageTitleContent();
		ArrayList messagesList = (ArrayList) messagesTitleList.get(1);
		apForm.setSpecialMessagesList(messagesList);
		messagesTitleList = null;
		messagesList = null;
		for (int r = 0; r < eligibleNonDupRsfApps.size(); r++) {
			Application tempRsfApplication = (Application) eligibleNonDupRsfApps
					.get(r);
			String tempAppRefNo = tempRsfApplication.getAppRefNo();
			String tempStatus = tempRsfApplication.getStatus();
			String remarks = tempRsfApplication.getRemarks();
			clearRsfStatusMap.put(tempAppRefNo, tempStatus);
			clearRsfRemarksMap.put(tempAppRefNo, remarks);

			apForm.setClearRsfTempMap(clearRsfStatusMap);
			apForm.setClearRsfRemMap(clearRsfRemarksMap);
			Log.log(4, "ApplicationProcessingAction",
					"getApplicationsForApprovalPath", "apForm Map size :"
							+ apForm.getTempMap().size());
		}
		for (int i = 0; i < eligibleNonDupApps.size(); i++) {
			Application tempApplication = (Application) eligibleNonDupApps
					.get(i);
			String tempAppRefNo = tempApplication.getAppRefNo();

			String tempStatus = tempApplication.getStatus();
			String remarks = tempApplication.getRemarks();
			clearStatusMap.put(tempAppRefNo, tempStatus);
			clearRemarksMap.put(tempAppRefNo, remarks);

			apForm.setClearTempMap(clearStatusMap);
			apForm.setClearRemMap(clearRemarksMap);
			Log.log(4, "ApplicationProcessingAction",
					"getApplicationsForApprovalPath", "apForm Map size :"
							+ apForm.getTempMap().size());
		}
		for (int i = 0; i < eligibleDupApps.size(); i++) {
			DuplicateApplication tempDupApplication = (DuplicateApplication) eligibleDupApps
					.get(i);
			String tempAppRefNo = tempDupApplication.getNewAppRefNo();

			String tempStatus = tempDupApplication.getStatus();
			String remarks = tempDupApplication.getDuplicateRemarks();
			dupStatusMap.put(tempAppRefNo, tempStatus);
			dupRemarksMap.put(tempAppRefNo, remarks);
			apForm.setDupTempMap(dupStatusMap);
			apForm.setDupRemMap(dupRemarksMap);
			Log.log(4, "ApplicationProcessingAction",
					"getApplicationsForApprovalPath", "apForm Map size :"
							+ apForm.getTempMap().size());
		}

		ArrayList ineligibleNonDupApps = (ArrayList) applicationsList.get(2);
		for (int i = 0; i < ineligibleNonDupApps.size(); i++) {
			EligibleApplication tempEligibleApplication = (EligibleApplication) ineligibleNonDupApps
					.get(i);
			String tempAppRefNo = tempEligibleApplication.getAppRefNo();

			String tempStatus = tempEligibleApplication.getStatus();
			String remarks = tempEligibleApplication.getEligibleRemarks();
			ineligibleStatusMap.put(tempAppRefNo, tempStatus);
			ineligibleRemarksMap.put(tempAppRefNo, remarks);
			apForm.setIneligibleTempMap(ineligibleStatusMap);
			apForm.setIneligibleRemMap(ineligibleRemarksMap);
		}
		ArrayList ineligibleDupApps = (ArrayList) applicationsList.get(3);
		for (int i = 0; i < ((ArrayList) ineligibleDupApps.get(0)).size(); i++) {
			DuplicateApplication duplicateApplication = (DuplicateApplication) ((ArrayList) ineligibleDupApps
					.get(0)).get(i);
			String tempAppRefNo = duplicateApplication.getNewAppRefNo();
			String tempStatus = duplicateApplication.getStatus();

			String remarks = duplicateApplication.getDuplicateRemarks();
			tempStatusMap.put(tempAppRefNo, tempStatus);
			tempRemarksMap.put(tempAppRefNo, remarks);
			apForm.setTempMap(tempStatusMap);
			apForm.setTempRemMap(tempRemarksMap);
			Log.log(4, "ApplicationProcessingAction",
					"getApplicationsForApprovalPath", "apForm Map size :"
							+ apForm.getTempMap().size());
		}
		ArrayList privilegeList = user.getPrivileges();
		if (privilegeList.contains("APPROVE_INELIGIBLE_APPLICATION")) {
			apForm.setIneligibleNonDupApps(ineligibleNonDupApps);
		}
		if (privilegeList.contains("APPROVE_DUPLICATE_APPLICATION")) {
			apForm.setEligibleDupApps(eligibleDupApps);
		}
		if ((privilegeList.contains("APPROVE_DUPLICATE_APPLICATION"))
				&& (privilegeList.contains("APPROVE_INELIGIBLE_APPLICATION"))) {
			apForm.setIneligibleDupApps(ineligibleDupApps);
		}
		apForm.setEligibleNonDupRsfApps(eligibleNonDupRsfApps);

		apForm.setEligibleNonDupApps(eligibleNonDupApps);

		if (((apForm.getIneligibleNonDupApps() == null) || (apForm
				.getIneligibleNonDupApps().size() == 0))
				&& ((apForm.getEligibleNonDupRsfApps() == null) || (apForm
						.getEligibleNonDupRsfApps().size() == 0))
				&& ((apForm.getEligibleDupApps() == null) || (apForm
						.getEligibleDupApps().size() == 0))
				&& ((apForm.getEligibleNonDupApps() == null) || (apForm
						.getEligibleNonDupApps().size() == 0))
				&& ((((ArrayList) apForm.getIneligibleDupApps().get(0)).size() == 0) || ((ArrayList) apForm
						.getIneligibleDupApps().get(0) == null))) {
			forward = "successNew";
			request.setAttribute("message",
					"No Applications for Approval Available");
		} else {
			forward = "approvalPageNew";
		}
		appProcessor = null;
		applicationsList = null;
		Log.log(4, "ApplicationProcessingAction", "showAppsForApprovalPath",
				"Exited. Memory : " + Runtime.getRuntime().freeMemory());

		return mapping.findForward(forward);
	}

	public ActionForward showAppsForApproval(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(false);
		session.removeAttribute("APPLICATION_LOAN_TYPE");
		session.setAttribute("APPLICATION_TYPE", "APP");

		User user = getUserInformation(request);

		String userId = user.getUserId();

		String forward = "";

		ApplicationProcessor appProcessor = new ApplicationProcessor();

		APForm apForm = (APForm) form;

		apForm.resetMaps();

		TreeMap tempStatusMap = new TreeMap();
		TreeMap clearStatusMap = new TreeMap();
		TreeMap dupStatusMap = new TreeMap();
		TreeMap ineligibleStatusMap = new TreeMap();

		TreeMap tempRemarksMap = new TreeMap();
		TreeMap clearRemarksMap = new TreeMap();
		TreeMap dupRemarksMap = new TreeMap();
		TreeMap ineligibleRemarksMap = new TreeMap();

		Log.log(4, "ApplicationProcessingAction", "getApplicationsForApproval",
				"Before callin getappsforapproval frm processor");
		ArrayList applicationsList = appProcessor
				.getApplicationsForApproval(userId);

		Integer intAppCount = (Integer) applicationsList.get(4);
		int appCount = intAppCount.intValue();
		apForm.setIntApplicationCount(appCount);

		ArrayList eligibleNonDupApps = (ArrayList) applicationsList.get(0);
		ArrayList eligibleDupApps = (ArrayList) applicationsList.get(1);

		Log.log(4, "ApplicationProcessingAction", "getApplicationsForApproval",
				"After callin getappsforapproval frm processor");
		ArrayList messagesTitleList = appProcessor.getMessageTitleContent();
		ArrayList messagesList = (ArrayList) messagesTitleList.get(1);
		apForm.setSpecialMessagesList(messagesList);
		messagesTitleList = null;
		messagesList = null;

		eligibleNonDupApps = (ArrayList) applicationsList.get(0);

		for (int i = 0; i < eligibleNonDupApps.size(); i++) {
			Application tempApplication = (Application) eligibleNonDupApps
					.get(i);
			String tempAppRefNo = tempApplication.getAppRefNo();
			String tempStatus = tempApplication.getStatus();
			String remarks = tempApplication.getRemarks();

			clearStatusMap.put(tempAppRefNo, tempStatus);
			clearRemarksMap.put(tempAppRefNo, remarks);

			apForm.setClearTempMap(clearStatusMap);
			apForm.setClearRemMap(clearRemarksMap);
			Log.log(4, "ApplicationProcessingAction",
					"getApplicationsForApproval", "apForm Map size :"
							+ apForm.getTempMap().size());
		}

		eligibleDupApps = (ArrayList) applicationsList.get(1);

		for (int i = 0; i < eligibleDupApps.size(); i++) {
			DuplicateApplication tempDupApplication = (DuplicateApplication) eligibleDupApps
					.get(i);
			String tempAppRefNo = tempDupApplication.getNewAppRefNo();
			String tempStatus = tempDupApplication.getStatus();
			String remarks = tempDupApplication.getDuplicateRemarks();

			dupStatusMap.put(tempAppRefNo, tempStatus);
			dupRemarksMap.put(tempAppRefNo, remarks);

			apForm.setDupTempMap(dupStatusMap);
			apForm.setDupRemMap(dupRemarksMap);
			Log.log(4, "ApplicationProcessingAction",
					"getApplicationsForApproval", "apForm Map size :"
							+ apForm.getTempMap().size());
		}

		ArrayList ineligibleNonDupApps = (ArrayList) applicationsList.get(2);

		for (int i = 0; i < ineligibleNonDupApps.size(); i++) {
			EligibleApplication tempEligibleApplication = (EligibleApplication) ineligibleNonDupApps
					.get(i);
			String tempAppRefNo = tempEligibleApplication.getAppRefNo();
			String tempStatus = tempEligibleApplication.getStatus();
			String remarks = tempEligibleApplication.getEligibleRemarks();

			ineligibleStatusMap.put(tempAppRefNo, tempStatus);
			ineligibleRemarksMap.put(tempAppRefNo, remarks);

			apForm.setIneligibleTempMap(ineligibleStatusMap);
			apForm.setIneligibleRemMap(ineligibleRemarksMap);
			Log.log(4, "ApplicationProcessingAction",
					"getApplicationsForApproval", "apForm Map size :"
							+ apForm.getTempMap().size());
		}

		ArrayList ineligibleDupApps = (ArrayList) applicationsList.get(3);

		for (int i = 0; i < ((ArrayList) ineligibleDupApps.get(0)).size(); i++) {
			DuplicateApplication duplicateApplication = (DuplicateApplication) ((ArrayList) ineligibleDupApps
					.get(0)).get(i);
			String tempAppRefNo = duplicateApplication.getNewAppRefNo();
			String tempStatus = duplicateApplication.getStatus();
			String remarks = duplicateApplication.getDuplicateRemarks();
			Log.log(4, "ApplicationProcessingAction",
					"getApplicationsForApproval",
					"remarks of the ineligible and duplicate:" + remarks);

			tempStatusMap.put(tempAppRefNo, tempStatus);
			tempRemarksMap.put(tempAppRefNo, remarks);

			apForm.setTempMap(tempStatusMap);
			apForm.setTempRemMap(tempRemarksMap);
			Log.log(4, "ApplicationProcessingAction",
					"getApplicationsForApproval", "apForm Map size :"
							+ apForm.getTempMap().size());
		}

		ArrayList privilegeList = user.getPrivileges();
		if (privilegeList.contains("APPROVE_INELIGIBLE_APPLICATION")) {
			Log.log(4, "ApplicationProcessingAction",
					"getApplicationsForApproval", "has privilege");

			apForm.setIneligibleNonDupApps(ineligibleNonDupApps);
		}
		if (privilegeList.contains("APPROVE_DUPLICATE_APPLICATION")) {
			apForm.setEligibleDupApps(eligibleDupApps);
		}
		if ((privilegeList.contains("APPROVE_DUPLICATE_APPLICATION"))
				&& (privilegeList.contains("APPROVE_INELIGIBLE_APPLICATION"))) {
			apForm.setIneligibleDupApps(ineligibleDupApps);
		}

		apForm.setEligibleNonDupApps(eligibleNonDupApps);

		if (((apForm.getIneligibleNonDupApps() == null) || (apForm
				.getIneligibleNonDupApps().size() == 0))
				&& ((apForm.getEligibleDupApps() == null) || (apForm
						.getEligibleDupApps().size() == 0))
				&& ((apForm.getEligibleNonDupApps() == null) || (apForm
						.getEligibleNonDupApps().size() == 0))
				&& ((((ArrayList) apForm.getIneligibleDupApps().get(0)).size() == 0) || ((ArrayList) apForm
						.getIneligibleDupApps().get(0) == null))) {
			forward = "success";
			request.setAttribute("message",
					"No Applications for Approval Available");
		} else {
			forward = "approvalPage";
		}

		appProcessor = null;
		applicationsList = null;

		Log.log(4, "ApplicationProcessingAction", "showAppsForApproval",
				"Exited. Memory : " + Runtime.getRuntime().freeMemory());

		return mapping.findForward(forward);
	}

	public ActionForward afterSsiRefPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session1 = request.getSession(false);
		String applicationType = (String) session1
				.getAttribute("APPLICATION_LOAN_TYPE");

		DynaActionForm dynaForm = (DynaActionForm) form;

		ApplicationProcessor appProcessor = new ApplicationProcessor();
		GMProcessor gmProcessor = new GMProcessor();

		Application application = new Application();
		BorrowerDetails borrowerDetails = new BorrowerDetails();
		SSIDetails ssiDetails = new SSIDetails();

		User user = getUserInformation(request);
		String memberName = "";
		String bankId = user.getBankId();

		if (bankId.equals("0000")) {
			memberName = (String) dynaForm.get("selectMember");
		} else {
			String zoneId = user.getZoneId();
			String branchId = user.getBranchId();
			memberName = bankId + zoneId + branchId;
		}

		String ssiDtl = request.getParameter("ssiRefNo");

		int start = ssiDtl.indexOf("(");

		String ssiRefNumber = ssiDtl.substring(0, start);
		if (ssiRefNumber.length() == 9) {
			borrowerDetails = gmProcessor.getBorrowerDetailsForBID(memberName,
					ssiRefNumber);

			ssiDetails = borrowerDetails.getSsiDetails();
			BeanUtils.copyProperties(dynaForm, ssiDetails);

			BeanUtils.copyProperties(dynaForm, borrowerDetails);

			dynaForm.set("unitValue", ssiRefNumber);
			dynaForm.set("none", "cgbid");
			dynaForm.set("previouslyCovered", "Y");

			Application tempApplication = new Application();
			tempApplication.setBorrowerDetails(borrowerDetails);
			tempApplication.setMliID(memberName);

			double balanceAppAmt = appProcessor
					.getBalanceApprovedAmt(tempApplication);

			dynaForm.set("balanceApprovedAmt", new Double(balanceAppAmt));

			session1.setAttribute("ssiRefNumber", new Integer(borrowerDetails
					.getSsiDetails().getBorrowerRefNo()));
		} else {
			Integer ssiRefValue = new Integer(ssiRefNumber);
			int ssiRefNo = ssiRefValue.intValue();

			borrowerDetails = appProcessor.viewBorrowerDetails(ssiRefNo);

			ssiDetails = borrowerDetails.getSsiDetails();
			BeanUtils.copyProperties(dynaForm, ssiDetails);

			BeanUtils.copyProperties(dynaForm, borrowerDetails);

			dynaForm.set("unitValue", "");
			dynaForm.set("none", "none");
			dynaForm.set("previouslyCovered", "N");

			session1.setAttribute("ssiRefNumber", ssiRefValue);
		}

		Administrator admin = new Administrator();

		ArrayList statesList = (ArrayList) getStateList();
		dynaForm.set("statesList", statesList);

		String state = (String) dynaForm.get("state");
		ArrayList districtList = admin.getAllDistricts(state);
		dynaForm.set("districtList", districtList);

		String districtName = ssiDetails.getDistrict();

		if (districtList.contains(districtName)) {
			dynaForm.set("district", districtName);
		} else {
			dynaForm.set("districtOthers", districtName);
			dynaForm.set("district", "Others");
		}

		ArrayList socialList = getSocialCategory();
		dynaForm.set("socialCategoryList", socialList);

		dynaForm.set("industryNature", ssiDetails.getIndustryNature());
		dynaForm.set("industrySector", ssiDetails.getIndustrySector());

		String forward = setApps(applicationType);
		if (applicationType.equals("TC")) {
			session1.setAttribute("APPLICATION_TYPE_FLAG", "14");
		} else if (applicationType.equals("WC")) {
			session1.setAttribute("APPLICATION_TYPE_FLAG", "18");
		} else if ((applicationType.equals("CC"))
				|| (applicationType.equals("BO"))) {
			session1.setAttribute("APPLICATION_TYPE_FLAG", "19");
		}

		appProcessor = null;
		application = null;
		ssiDetails = null;
		borrowerDetails = null;
		applicationType = null;

		return mapping.findForward(forward);
	}

	public ActionForward showOldAppDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String pageForward = "";

		DynaActionForm dynaForm = (DynaActionForm) form;

		Administrator admin = new Administrator();
		ApplicationProcessor appProcessor = new ApplicationProcessor();
		BorrowerDetails borrowerDetails = new BorrowerDetails();
		SSIDetails ssiDetails = new SSIDetails();
		Application application = new Application();
		MCGFDetails mcgfDetails = new MCGFDetails();
		TermLoan termLoan = new TermLoan();
		WorkingCapital workingCapital = new WorkingCapital();

		String cgpan = "";
		String appRefNo = "";
		String mliId = null;

		BeanUtils.populate(ssiDetails, dynaForm.getMap());
		borrowerDetails.setSsiDetails(ssiDetails);
		BeanUtils.populate(borrowerDetails, dynaForm.getMap());
		application.setBorrowerDetails(borrowerDetails);

		BeanUtils.populate(application, dynaForm.getMap());

		cgpan = request.getParameter("cgpan");

		application.setCgpan(cgpan);
		application.setAppRefNo("");

		if (((mliId == null) || (mliId.equals(""))) && (!cgpan.equals(""))) {
			HttpSession appSession1 = request.getSession(false);

			Log.log(4, "ApplicationProcessingAction", "showCgpanList",
					"Entering when cgpan and app ref no are entered");
			application = appProcessor
					.getOldApplication(mliId, cgpan, appRefNo);
			mliId = application.getMliID();
			String bankId = mliId.substring(0, 4);
			String zoneId = mliId.substring(4, 8);
			String branchId = mliId.substring(8, 12);
			Registration registration = new Registration();
			MLIInfo mliInfo = registration.getMemberDetails(bankId, zoneId,
					branchId);

			String applicationLoanType = application.getLoanType();

			Log.log(4, "ApplicationProcessingAction", "showCgpanList",
					"Setting the values in copy Properties....");

			ssiDetails = application.getBorrowerDetails().getSsiDetails();
			BeanUtils.copyProperties(dynaForm, ssiDetails);

			borrowerDetails = application.getBorrowerDetails();
			BeanUtils.copyProperties(dynaForm, borrowerDetails);
			Log.log(4, "ApplicationProcessingAction", "showCgpanList",
					"Set Borrower Details...");

			PrimarySecurityDetails primarySecurityDetails = application
					.getProjectOutlayDetails().getPrimarySecurityDetails();
			BeanUtils.copyProperties(dynaForm, primarySecurityDetails);
			double landValue = ((Double) dynaForm.get("landValue"))
					.doubleValue();
			double bldgValue = ((Double) dynaForm.get("bldgValue"))
					.doubleValue();
			double machineValue = ((Double) dynaForm.get("machineValue"))
					.doubleValue();
			double assetsValue = ((Double) dynaForm.get("assetsValue"))
					.doubleValue();
			double currentAssetsValue = ((Double) dynaForm
					.get("currentAssetsValue")).doubleValue();
			double othersValue = ((Double) dynaForm.get("othersValue"))
					.doubleValue();
			double psTotalValue = landValue + bldgValue + machineValue
					+ assetsValue + currentAssetsValue + othersValue;
			Double intPsTotal = new Double(psTotalValue);
			dynaForm.set("psTotal", intPsTotal);
			Log.log(4, "ApplicationProcessingAction", "showCgpanList",
					"Set Primary Security Details...");

			ProjectOutlayDetails projectOutlayDetails = application
					.getProjectOutlayDetails();
			BeanUtils.copyProperties(dynaForm, projectOutlayDetails);

			Log.log(4, "ApplicationProcessingAction", "showCgpanList",
					"Set Project Outlay Details...");

			termLoan = application.getTermLoan();
			BeanUtils.copyProperties(dynaForm, termLoan);
			Log.log(4, "ApplicationProcessingAction", "showCgpanList",
					"Set Term Loan Details...");
			double termCreditSanctioned = ((Double) dynaForm
					.get("termCreditSanctioned")).doubleValue();
			Double termCreditSanctionedVal = new Double(termCreditSanctioned);
			double tcPromoterContribution = ((Double) dynaForm
					.get("tcPromoterContribution")).doubleValue();
			double tcSubsidyOrEquity = ((Double) dynaForm
					.get("tcSubsidyOrEquity")).doubleValue();
			double tcOthers = ((Double) dynaForm.get("tcOthers")).doubleValue();
			double projectCost = termCreditSanctioned + tcPromoterContribution
					+ tcSubsidyOrEquity + tcOthers;
			Double projectCostIntVal = new Double(projectCost);
			dynaForm.set("projectCost", projectCostIntVal);
			dynaForm.set("amountSanctioned", termCreditSanctionedVal);

			workingCapital = application.getWc();
			BeanUtils.copyProperties(dynaForm, workingCapital);
			double wcFundBasedSanctioned = ((Double) dynaForm
					.get("wcFundBasedSanctioned")).doubleValue();
			double wcNonFundBasedSanctioned = ((Double) dynaForm
					.get("wcNonFundBasedSanctioned")).doubleValue();

			Double existingFBTotalVal = new Double(wcFundBasedSanctioned);
			Double existingNFBTotalVal = new Double(wcNonFundBasedSanctioned);
			dynaForm.set("existingFundBasedTotal", existingFBTotalVal);
			dynaForm.set("existingNonFundBasedTotal", existingNFBTotalVal);

			double wcPromoterContribution = ((Double) dynaForm
					.get("wcPromoterContribution")).doubleValue();
			double wcSubsidyOrEquity = ((Double) dynaForm
					.get("wcSubsidyOrEquity")).doubleValue();
			double wcOthers = ((Double) dynaForm.get("wcOthers")).doubleValue();

			double wcAssessed = wcFundBasedSanctioned
					+ wcNonFundBasedSanctioned + wcPromoterContribution
					+ wcSubsidyOrEquity + wcOthers;
			Double wcAssessedIntVal = new Double(wcAssessed);
			dynaForm.set("wcAssessed", wcAssessedIntVal);
			Log.log(4, "ApplicationProcessingAction", "showCgpanList",
					"Set Working Capital Details...");

			double projectCostValue = ((Double) dynaForm.get("projectCost"))
					.doubleValue();
			double wcAssessedValue = ((Double) dynaForm.get("wcAssessed"))
					.doubleValue();
			double projectOutlayValueCost = projectCostValue + wcAssessedValue;
			Double projectOutlayVal = new Double(projectOutlayValueCost);
			dynaForm.set("projectOutlay", projectOutlayVal);

			Securitization securitization = application.getSecuritization();
			BeanUtils.copyProperties(dynaForm, securitization);
			Log.log(4, "ApplicationProcessingAction", "showCgpanList",
					"Set Securitization Details...");

			String mcgfFlag = mliInfo.getSupportMCGF();
			if (mcgfFlag.equals("Y")) {
				appSession1.setAttribute("MCGF_FLAG", "M");

				mcgfDetails = application.getMCGFDetails();
				Log.log(4,
						"ApplicationProcessingAction",
						"showCgpanList",
						"mcgf App ref no:"
								+ mcgfDetails.getApplicationReferenceNumber());
				Log.log(4,
						"ApplicationProcessingAction",
						"showCgpanList",
						"mcgf approved Amount:"
								+ mcgfDetails.getMcgfApprovedAmt());
				Log.log(4, "ApplicationProcessingAction", "showCgpanList",
						"mcgf district:" + mcgfDetails.getMcgfDistrict());
				Log.log(4, "ApplicationProcessingAction", "showCgpanList",
						"mcgf bank:" + mcgfDetails.getParticipatingBank());
				Log.log(4,
						"ApplicationProcessingAction",
						"showCgpanList",
						"mcgf branch :"
								+ mcgfDetails.getParticipatingBankBranch());
				Log.log(4, "ApplicationProcessingAction", "showCgpanList",
						"mcgf name :" + mcgfDetails.getMcgfName());
				Log.log(4, "ApplicationProcessingAction", "showCgpanList",
						"mcgf Id:" + mcgfDetails.getMcgfId());

				mcgfDetails.setMcgfId(mliId);
				application.setMCGFDetails(mcgfDetails);
				BeanUtils.copyProperties(dynaForm, mcgfDetails);
				Log.log(4, "ApplicationProcessingAction", "showCgpanList",
						"Set MCGF Details...");
			} else {
				Log.log(4, "ApplicationProcessingAction", "showCgpanList",
						"Not under MCGF");

				appSession1.setAttribute("MCGF_FLAG", "NM");
			}

			String remarks = application.getRemarks();
			application.setExistingRemarks(remarks);

			BeanUtils.copyProperties(dynaForm, application);

			Log.log(4, "ApplicationProcessingAction", "showCgpanList",
					"Set Application Details...");

			String constitutionVal = ssiDetails.getConstitution();
			if ((constitutionVal != null) && (!constitutionVal.equals(""))) {
				if ((!constitutionVal.equals("proprietary"))
						&& (!constitutionVal.equals("partnership"))
						&& (!constitutionVal.equals("private"))
						&& (!constitutionVal.equals("public"))) {
					dynaForm.set("constitutionOther", constitutionVal);
					dynaForm.set("constitution", "Others");
				} else {
					dynaForm.set("constitution", constitutionVal);
				}
			}

			Log.log(4, "ApplicationProcessingAction", "showCgpanList",
					"Set Application Details...");

			String legalIDString = ssiDetails.getCpLegalID();
			if ((legalIDString != null) && (!legalIDString.equals(""))) {
				if ((!legalIDString.equals("VoterIdentityCard"))
						&& (!legalIDString.equals("RationCardnumber"))
						&& (!legalIDString.equals("PASSPORT"))
						&& (!legalIDString.equals("Driving License"))) {
					dynaForm.set("otherCpLegalID", legalIDString);
					dynaForm.set("cpLegalID", "Others");
				} else {
					dynaForm.set("cpLegalID", legalIDString);
				}
			}

			Log.log(4, "ApplicationProcessingAction", "showCgpanList",
					"Set Application Details...");

			String subsidyEquityName = projectOutlayDetails.getSubsidyName();
			Log.log(4, "ApplicationProcessingAction", "showCgpanList",
					"Subsidy Name :" + projectOutlayDetails.getSubsidyName());
			if ((subsidyEquityName != null) && (!subsidyEquityName.equals(""))) {
				if ((!subsidyEquityName.equals("PMRY"))
						&& (!subsidyEquityName.equals("SJRY"))) {
					dynaForm.set("otherSubsidyEquityName", subsidyEquityName);
					dynaForm.set("subsidyName", "Others");
				} else {
					dynaForm.set("subsidyName", subsidyEquityName);
				}
			}

			Log.log(4, "ApplicationProcessingAction", "showCgpanList",
					"Set Application Details...");

			ArrayList statesList = (ArrayList) getStateList();
			dynaForm.set("statesList", statesList);

			String state = (String) dynaForm.get("state");
			ArrayList districtList = admin.getAllDistricts(state);
			dynaForm.set("districtList", districtList);

			String districtName = ssiDetails.getDistrict();

			if (districtList.contains(districtName)) {
				dynaForm.set("district", districtName);
			} else {
				dynaForm.set("districtOthers", districtName);
				dynaForm.set("district", "Others");
			}

			Log.log(4, "ApplicationProcessingAction", "showCgpanList",
					"Set Application Details...");

			ArrayList socialList = getSocialCategory();
			dynaForm.set("socialCategoryList", socialList);

			dynaForm.set("industryNature", ssiDetails.getIndustryNature());
			dynaForm.set("industrySector", ssiDetails.getIndustrySector());

			MCGSProcessor mcgsProcessor = new MCGSProcessor();
			ArrayList participatingBanks = mcgsProcessor
					.getAllParticipatingBanks(mliId);
			dynaForm.set("participatingBanks", participatingBanks);

			dynaForm.set("previouslyCovered", "Y");
			dynaForm.set("none", "cgpan");
			dynaForm.set("unitValue", cgpan);

			if (applicationLoanType.equals("ML")) {
				appSession1.setAttribute("APPLICATION_TYPE_FLAG", "101");
				pageForward = "tcMudraForward";
			} else if (applicationLoanType.equals("WC")) {
				appSession1.setAttribute("APPLICATION_TYPE_FLAG", "102");
				pageForward = "wcForward";
			} else if (applicationLoanType.equals("CC")) {
				appSession1.setAttribute("APPLICATION_TYPE_FLAG", "103");
				pageForward = "ccForward";
			}
		}

		Log.log(5, "ApplicationProcessingAction", "showCgpanList",
				"Page to be forwaded :" + pageForward);
		Log.log(4, "ApplicationProcessingAction", "showCgpanList", "Exited");

		application = null;
		borrowerDetails = null;
		ssiDetails = null;
		mcgfDetails = null;
		termLoan = null;
		workingCapital = null;

		dynaForm.set("cgbid", null);

		return mapping.findForward(pageForward);
	}

	public ActionForward showTCAppDetailsForConv(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forward = "";

		HttpSession session = request.getSession(false);

		ApplicationProcessor appProcessor = new ApplicationProcessor();
		APForm apForm = (APForm) form;

		apForm.resetTCConvMaps();

		ArrayList tcAppForConv = appProcessor.getCountForTCConv();

		if ((tcAppForConv == null) || (tcAppForConv.size() == 0)) {
			request.setAttribute("message",
					"No Term Loan Applications available for Conversion");
			forward = "success";
		} else {
			apForm.setTcApplications(tcAppForConv);

			forward = "tcConvPage";
		}

		session.removeAttribute("APPLICATION_LOAN_TYPE");
		session.setAttribute("APPLICATION_TYPE", "APP");

		return mapping.findForward(forward);
	}

	public ActionForward showWCAppDetailsForConv(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forward = "";

		HttpSession session = request.getSession(false);

		ApplicationProcessor appProcessor = new ApplicationProcessor();
		APForm apForm = (APForm) form;

		apForm.resetWCConvMaps();

		ArrayList wcAppForConv = appProcessor.getCountForWCConv();

		if ((wcAppForConv == null) || (wcAppForConv.size() == 0)) {
			request.setAttribute("message",
					"No Working Capital Applications available for Conversion");
			forward = "success";
		} else {
			apForm.setWcApplications(wcAppForConv);
			forward = "wcConvPage";
		}

		session.removeAttribute("APPLICATION_LOAN_TYPE");
		session.setAttribute("APPLICATION_TYPE", "APP");

		return mapping.findForward(forward);
	}

	public ActionForward afterTcConversion(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ApplicationProcessingAction", "afterTcConversion",
				"Entered");

		String forward = "";

		APForm apForm = (APForm) form;
		ApplicationProcessor appProcessor = new ApplicationProcessor();
		ApplicationDAO appDAO = new ApplicationDAO();

		ArrayList tcConvertedApps = new ArrayList();

		Map tcAppRefNo = apForm.getTcAppRefNo();
		Map tcCgpan = apForm.getTcCgpan();
		Map tcDecision = apForm.getTcDecision();

		Set tcAppRefNoSet = tcAppRefNo.keySet();
		Set tcCgpanSet = tcCgpan.keySet();
		Set tcDecisionSet = tcDecision.keySet();

		Iterator tcAppRefNoIterator = tcAppRefNoSet.iterator();

		while (tcAppRefNoIterator.hasNext()) {
			String key = (String) tcAppRefNoIterator.next();

			String appRefNo = (String) tcAppRefNo.get(key);
			String cgpanNumber = (String) tcCgpan.get(key);
			String decision = (String) tcDecision.get(key);

			Log.log(4, "ApplicationProcessingAction", "afterTcConversion",
					"appRefNo :" + appRefNo);
			Log.log(4, "ApplicationProcessingAction", "afterTcConversion",
					"appRefNo :" + appRefNo);
			Log.log(4, "ApplicationProcessingAction", "afterTcConversion",
					"decision :" + decision);

			Application cgpanApplication = new Application();

			String cgpanSubScheme = "";

			if ((decision != null) && (decision.equals("ATL"))) {
				Application application = appProcessor.getPartApplication(null,
						"", appRefNo);
				int appSSIRef = application.getBorrowerDetails()
						.getSsiDetails().getBorrowerRefNo();

				ArrayList cgpans = appDAO.getAllCgpans();
				if (!cgpans.contains(cgpanNumber)) {
					throw new MessageException(cgpanNumber
							+ " is an invalid CGPAN");
				}

				int cgpanSSIRef;
				try {
					cgpanApplication = appProcessor.getApplication(application
							.getMliID().substring(0, 4)
							+ application.getMliID().substring(4, 8)
							+ application.getMliID().substring(8, 12),
							cgpanNumber, "");
					cgpanSSIRef = cgpanApplication.getBorrowerDetails()
							.getSsiDetails().getBorrowerRefNo();

					Log.log(4, "ApplicationProcessingAction",
							"afterTcConversion", "cgpanSSIRef :" + cgpanSSIRef);
					Log.log(4, "ApplicationProcessingAction",
							"afterTcConversion", "cgpanSubScheme :"
									+ cgpanSubScheme);

					if ((!cgpanApplication.getStatus().equals("AP"))
							&& (!cgpanApplication.getStatus().equals("EX"))) {
						throw new MessageException(cgpanNumber
								+ " is not an Approved / Expired Application");
					}

				} catch (DatabaseException databaseException) {
					throw new MessageException("The CGPAN :" + cgpanNumber
							+ " does not belong the Member "
							+ application.getMliID().substring(0, 4)
							+ application.getMliID().substring(4, 8)
							+ application.getMliID().substring(8, 12));
				}

				if (!cgpanNumber.substring(11, 13).equals("TC")) {
					throw new MessageException(cgpanNumber
							+ " should be a Term Loan CGPAN");
				}

				RiskManagementProcessor rpProcessor = new RiskManagementProcessor();
				Application riskApplication = new Application();
				BorrowerDetails riskBorrowerDetails = new BorrowerDetails();
				SSIDetails riskSSIDetails = new SSIDetails();
				riskBorrowerDetails.setSsiDetails(riskSSIDetails);
				riskApplication.setBorrowerDetails(riskBorrowerDetails);

				riskApplication.setMliID(cgpanApplication.getMliID());

				BorrowerDetails rBorrowerDetails = riskApplication
						.getBorrowerDetails();
				SSIDetails rSSIDetails = rBorrowerDetails.getSsiDetails();
				rSSIDetails.setState(cgpanApplication.getBorrowerDetails()
						.getSsiDetails().getState());
				rSSIDetails.setIndustryNature(cgpanApplication
						.getBorrowerDetails().getSsiDetails()
						.getIndustryNature());
				Log.log(4, "ApplicationProcessingAction", "afterTcConversion",
						"cgpanApplication.getBorrowerDetails().getSsiDetails().getCpGender() :"
								+ cgpanApplication.getBorrowerDetails()
										.getSsiDetails().getCpGender());
				rSSIDetails.setCpGender(cgpanApplication.getBorrowerDetails()
						.getSsiDetails().getCpGender());
				rSSIDetails.setSocialCategory(cgpanApplication
						.getBorrowerDetails().getSsiDetails()
						.getSocialCategory());
				Log.log(4, "ApplicationProcessingAction", "afterTcConversion",
						"cgpanApplication.getBorrowerDetails().getSsiDetails().getSocialCategory() :"
								+ cgpanApplication.getBorrowerDetails()
										.getSsiDetails().getSocialCategory());
				rBorrowerDetails.setSsiDetails(rSSIDetails);
				riskApplication.setBorrowerDetails(rBorrowerDetails);

				String subSchemeName = rpProcessor
						.getSubScheme(riskApplication);

				Application tcApplication = new Application();
				BorrowerDetails tcBorrowerDetails = new BorrowerDetails();
				SSIDetails tcSsiDetails = new SSIDetails();

				tcSsiDetails.setBorrowerRefNo(cgpanSSIRef);
				tcBorrowerDetails.setSsiDetails(tcSsiDetails);
				tcApplication.setBorrowerDetails(tcBorrowerDetails);

				tcApplication.setAppRefNo(appRefNo);
				tcApplication.setCgpan(cgpanNumber);
				tcApplication.setSubSchemeName(subSchemeName);

				appProcessor.updateTCConv(tcApplication, appSSIRef);

				tcConvertedApps.add(tcApplication);
				apForm.setTcConvertedApplications(tcConvertedApps);

				application = null;
				cgpanApplication = null;
			}

		}

		return mapping.findForward("success");
	}

	public ActionForward afterWcConversion(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ApplicationProcessingAction", "afterWcConversion",
				"Entered");

		APForm apForm = (APForm) form;
		ApplicationProcessor appProcessor = new ApplicationProcessor();
		ApplicationDAO appDAO = new ApplicationDAO();

		ArrayList enhanceConvertedApps = new ArrayList();
		ArrayList renewConvertedApps = new ArrayList();

		Map wcAppRefNo = new TreeMap();
		Map wcCgpan = new TreeMap();
		Map wcDecision = new TreeMap();

		wcAppRefNo = apForm.getWcAppRefNo();
		wcCgpan = apForm.getWcCgpan();
		wcDecision = apForm.getWcDecision();

		Set wcAppRefNoSet = wcAppRefNo.keySet();
		Set wcCgpanSet = wcAppRefNo.keySet();
		Set wcDecisionSet = wcDecision.keySet();

		Iterator wcAppRefNoIterator = wcAppRefNoSet.iterator();

		Log.log(4, "ApplicationProcessingAction", "afterWcConversion",
				"wcAppRefNo :" + wcAppRefNo.size());

		while (wcAppRefNoIterator.hasNext()) {
			String key = (String) wcAppRefNoIterator.next();

			String appRefNo = (String) wcAppRefNo.get(key);
			String cgpanNumber = (String) wcCgpan.get(key);
			String decision = (String) wcDecision.get(key);

			Log.log(4, "ApplicationProcessingAction", "afterWcConversion",
					"key :" + key);
			Log.log(4, "ApplicationProcessingAction", "afterWcConversion",
					"cgpanNumber :" + cgpanNumber);
			Log.log(4, "ApplicationProcessingAction", "afterWcConversion",
					"appRefNo :" + appRefNo);
			Log.log(4, "ApplicationProcessingAction", "afterWcConversion",
					"decision :" + decision);

			Application cgpanApplication = new Application();

			String cgpanSubScheme = "";
			int cgpanSSIRef;
			if (decision.equals("WCR")) {
				Application application = appProcessor.getApplication(null, "",
						appRefNo);
				int appSSIRef = application.getBorrowerDetails()
						.getSsiDetails().getBorrowerRefNo();

				ArrayList cgpans = appDAO.getAllCgpans();
				if (!cgpans.contains(cgpanNumber)) {
					throw new MessageException(cgpanNumber
							+ " is an invalid CGPAN");
				}

				String renewcgpan = appProcessor.checkRenewCgpan(cgpanNumber);
				if (renewcgpan.equals("0")) {
					renewcgpan = cgpanNumber;
				}
				Log.log(4, "ApplicationProcessingAction", "afterWcConversion",
						"renewcgpan :" + renewcgpan);
				try {
					cgpanApplication = appProcessor.getApplication(application
							.getMliID().substring(0, 4)
							+ application.getMliID().substring(4, 8)
							+ application.getMliID().substring(8, 12),
							renewcgpan, "");
					cgpanSSIRef = cgpanApplication.getBorrowerDetails()
							.getSsiDetails().getBorrowerRefNo();

					Log.log(4, "ApplicationProcessingAction",
							"afterWcConversion", "cgpanSSIRef :" + cgpanSSIRef);
					Log.log(4, "ApplicationProcessingAction",
							"afterWcConversion", "cgpanSubScheme :"
									+ cgpanSubScheme);

					if (!cgpanApplication.getStatus().equals("EX")) {
						throw new MessageException(cgpanNumber
								+ " is not an Expired Application");
					}

				} catch (DatabaseException databaseException) {
					throw new MessageException("The CGPAN :" + cgpanNumber
							+ " does not belong the Member "
							+ application.getMliID().substring(0, 4)
							+ application.getMliID().substring(4, 8)
							+ application.getMliID().substring(8, 12));
				}

				if ((!cgpanNumber.substring(11, 13).equals("WC"))
						&& (!cgpanNumber.substring(11, 12).equals("R"))) {
					throw new MessageException(cgpanNumber
							+ " should be a Working Capital CGPAN");
				}

				RiskManagementProcessor rpProcessor = new RiskManagementProcessor();
				Application riskApplication = new Application();
				BorrowerDetails riskBorrowerDetails = new BorrowerDetails();
				SSIDetails riskSSIDetails = new SSIDetails();
				riskBorrowerDetails.setSsiDetails(riskSSIDetails);
				riskApplication.setBorrowerDetails(riskBorrowerDetails);

				riskApplication.setMliID(cgpanApplication.getMliID());

				BorrowerDetails rBorrowerDetails = riskApplication
						.getBorrowerDetails();
				SSIDetails rSSIDetails = rBorrowerDetails.getSsiDetails();
				rSSIDetails.setState(cgpanApplication.getBorrowerDetails()
						.getSsiDetails().getState());
				rSSIDetails.setIndustryNature(cgpanApplication
						.getBorrowerDetails().getSsiDetails()
						.getIndustryNature());
				Log.log(4, "ApplicationProcessingAction", "afterWcConversion",
						"cgpanApplication.getBorrowerDetails().getSsiDetails().getCpGender() :"
								+ cgpanApplication.getBorrowerDetails()
										.getSsiDetails().getCpGender());
				rSSIDetails.setCpGender(cgpanApplication.getBorrowerDetails()
						.getSsiDetails().getCpGender());
				rSSIDetails.setSocialCategory(cgpanApplication
						.getBorrowerDetails().getSsiDetails()
						.getSocialCategory());
				Log.log(4, "ApplicationProcessingAction", "afterWcConversion",
						"cgpanApplication.getBorrowerDetails().getSsiDetails().getSocialCategory() :"
								+ cgpanApplication.getBorrowerDetails()
										.getSsiDetails().getSocialCategory());
				rBorrowerDetails.setSsiDetails(rSSIDetails);
				riskApplication.setBorrowerDetails(rBorrowerDetails);

				String subSchemeName = rpProcessor
						.getSubScheme(riskApplication);

				Application wcApplication = new Application();
				BorrowerDetails wcBorrowerDetails = new BorrowerDetails();
				SSIDetails wcSsiDetails = new SSIDetails();

				wcSsiDetails.setBorrowerRefNo(cgpanSSIRef);
				wcBorrowerDetails.setSsiDetails(wcSsiDetails);
				wcApplication.setBorrowerDetails(wcBorrowerDetails);

				wcApplication.setAppRefNo(appRefNo);
				wcApplication.setCgpan(renewcgpan);
				wcApplication.setSubSchemeName(subSchemeName);

				appProcessor.updateTCConv(wcApplication, appSSIRef);

				renewConvertedApps.add(wcApplication);
				apForm.setRenewConvertedApplications(renewConvertedApps);

				wcApplication = null;
				wcBorrowerDetails = null;
				wcSsiDetails = null;

				riskApplication = null;
				riskBorrowerDetails = null;
				riskSSIDetails = null;
				rBorrowerDetails = null;
				rSSIDetails = null;
			} else if (decision.equals("WCE")) {
				Application application = appProcessor.getApplication(null, "",
						appRefNo);
				int appSSIRef = application.getBorrowerDetails()
						.getSsiDetails().getBorrowerRefNo();

				ArrayList cgpans = appDAO.getAllCgpans();
				if (!cgpans.contains(cgpanNumber)) {
					throw new MessageException(cgpanNumber
							+ " is an invalid CGPAN");
				}

				try {
					cgpanApplication = appProcessor.getApplication(application
							.getMliID().substring(0, 4)
							+ application.getMliID().substring(4, 8)
							+ application.getMliID().substring(8, 12),
							cgpanNumber, "");
					cgpanSSIRef = cgpanApplication.getBorrowerDetails()
							.getSsiDetails().getBorrowerRefNo();

					Log.log(4, "ApplicationProcessingAction",
							"afterWcConversion", "cgpanSSIRef :" + cgpanSSIRef);
					Log.log(4, "ApplicationProcessingAction",
							"afterWcConversion", "cgpanSubScheme :"
									+ cgpanSubScheme);

					if (!cgpanApplication.getStatus().equals("AP")) {
						throw new MessageException(cgpanNumber
								+ " is not an Approved Application");
					}
				} catch (DatabaseException databaseException) {
					Log.log(4, "ApplicationProcessingAction",
							"afterWcConversion", "excpetion :"
									+ databaseException.getMessage());

					throw new MessageException("The CGPAN :" + cgpanNumber
							+ " does not belong the Member "
							+ application.getMliID().substring(0, 4)
							+ application.getMliID().substring(4, 8)
							+ application.getMliID().substring(8, 12));
				}

				if ((!cgpanNumber.substring(11, 13).equals("WC"))
						&& (!cgpanNumber.substring(11, 12).equals("R"))) {
					throw new MessageException(cgpanNumber
							+ " should be a Working Capital CGPAN");
				}

				if (cgpanApplication.getProjectOutlayDetails()
						.getWcFundBasedSanctioned() > application
						.getProjectOutlayDetails().getWcFundBasedSanctioned()) {
					throw new MessageException(
							"The Working Capital Fund Based Sanctioned Amount should be greater than the existing Fund Based Sanctioned Amount");
				}

				if (cgpanApplication.getProjectOutlayDetails()
						.getWcNonFundBasedSanctioned() > application
						.getProjectOutlayDetails()
						.getWcNonFundBasedSanctioned()) {
					throw new MessageException(
							"The Working Capital Non Fund Based Sanctioned Amount should be greater than the existing Non Fund Based Sanctioned Amount");
				}

				if (cgpanApplication.getProjectOutlayDetails()
						.getWcNonFundBasedSanctioned()
						+ cgpanApplication.getProjectOutlayDetails()
								.getWcFundBasedSanctioned() > application
						.getProjectOutlayDetails()
						.getWcNonFundBasedSanctioned()
						+ application.getProjectOutlayDetails()
								.getWcFundBasedSanctioned()) {
					throw new MessageException(
							"Total Enhanced Amount should not be lesser than Total Existing Amount");
				}

				RiskManagementProcessor rpProcessor = new RiskManagementProcessor();
				Application riskApplication = new Application();
				BorrowerDetails riskBorrowerDetails = new BorrowerDetails();
				SSIDetails riskSSIDetails = new SSIDetails();
				riskBorrowerDetails.setSsiDetails(riskSSIDetails);
				riskApplication.setBorrowerDetails(riskBorrowerDetails);

				riskApplication.setMliID(cgpanApplication.getMliID());

				BorrowerDetails rBorrowerDetails = riskApplication
						.getBorrowerDetails();
				SSIDetails rSSIDetails = rBorrowerDetails.getSsiDetails();
				rSSIDetails.setState(cgpanApplication.getBorrowerDetails()
						.getSsiDetails().getState());
				rSSIDetails.setIndustryNature(cgpanApplication
						.getBorrowerDetails().getSsiDetails()
						.getIndustryNature());
				Log.log(4, "ApplicationProcessingAction", "afterWcConversion",
						"cgpanApplication.getBorrowerDetails().getSsiDetails().getCpGender() :"
								+ cgpanApplication.getBorrowerDetails()
										.getSsiDetails().getCpGender());
				rSSIDetails.setCpGender(cgpanApplication.getBorrowerDetails()
						.getSsiDetails().getCpGender());
				rSSIDetails.setSocialCategory(cgpanApplication
						.getBorrowerDetails().getSsiDetails()
						.getSocialCategory());
				Log.log(4, "ApplicationProcessingAction", "afterWcConversion",
						"cgpanApplication.getBorrowerDetails().getSsiDetails().getSocialCategory() :"
								+ cgpanApplication.getBorrowerDetails()
										.getSsiDetails().getSocialCategory());
				rBorrowerDetails.setSsiDetails(rSSIDetails);
				riskApplication.setBorrowerDetails(rBorrowerDetails);

				String subSchemeName = rpProcessor
						.getSubScheme(riskApplication);

				Application wcApplication = new Application();
				BorrowerDetails wcBorrowerDetails = new BorrowerDetails();
				SSIDetails wcSsiDetails = new SSIDetails();
				WorkingCapital wcDetails = new WorkingCapital();

				wcSsiDetails.setBorrowerRefNo(cgpanSSIRef);
				wcBorrowerDetails.setSsiDetails(wcSsiDetails);
				wcDetails.setFundBasedLimitSanctioned(application
						.getProjectOutlayDetails().getWcFundBasedSanctioned()
						- cgpanApplication.getProjectOutlayDetails()
								.getWcFundBasedSanctioned());
				wcDetails.setNonFundBasedLimitSanctioned(application
						.getProjectOutlayDetails()
						.getWcNonFundBasedSanctioned()
						- cgpanApplication.getProjectOutlayDetails()
								.getWcNonFundBasedSanctioned());
				Log.log(4, "ApplicationProcessingAction", "afterWcConversion",
						"difference :"
								+ cgpanApplication.getProjectOutlayDetails()
										.getWcFundBasedSanctioned());
				Log.log(4, "ApplicationProcessingAction", "afterWcConversion",
						"application difference :"
								+ application.getProjectOutlayDetails()
										.getWcFundBasedSanctioned());
				wcDetails.setWcInterestRate(application.getWc()
						.getLimitFundBasedInterest());
				wcDetails.setLimitNonFundBasedCommission(application.getWc()
						.getLimitNonFundBasedCommission());
				Log.log(4, "ApplicationProcessingAction", "afterWcConversion",
						"interest rate :"
								+ cgpanApplication.getWc()
										.getLimitFundBasedInterest());
				wcDetails.setWcInterestType(application.getWc()
						.getWcInterestType());
				Log.log(4, "ApplicationProcessingAction", "afterWcConversion",
						"interest type :"
								+ cgpanApplication.getWc().getWcInterestType());
				wcApplication.setBorrowerDetails(wcBorrowerDetails);
				wcApplication.setWc(wcDetails);

				wcApplication.setAppRefNo(appRefNo);
				wcApplication.setCgpan(cgpanNumber);
				wcApplication.setSubSchemeName(subSchemeName);
				wcApplication.setStatus("EN");

				appProcessor.updateWCConv(wcApplication, appSSIRef);

				enhanceConvertedApps.add(wcApplication);
				apForm.setEnhanceConvertedApplications(enhanceConvertedApps);

				wcApplication = null;
				wcBorrowerDetails = null;
				wcSsiDetails = null;

				riskApplication = null;
				riskBorrowerDetails = null;
				riskSSIDetails = null;
				rBorrowerDetails = null;
				rSSIDetails = null;
			}

			cgpanApplication = null;
			this.application = null;
		}

		return mapping.findForward("success");
	}

	// DKR updated for GSTNo in upload excel file
	public ActionForward uploadGuaranteeApp(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Log.log(4, "ApplicationProcessingAction", "uploadGuaranteeApp", "uploadGuaranteeApp");
		HashMap apptypes = null;
		DynaValidatorActionForm appForm = (DynaValidatorActionForm) form;
		HttpSession session = request.getSession(false);
		User user = getUserInformation(request);
		String bankId = user.getBankId();
		HashMap<String, HashMap<Integer, String>> excelMethodResponse = null;// new HashMap<String, HashMap<Integer,String>>();	
		
		//System.out.println("user finde" + user);
		if (user == null)
			throw new MessageException("Please re-login.");

		FormFile formFile = (FormFile) appForm.get("filePath");
		String filePath = request.getRealPath("");
		// //System.out.println("File Path"+request.getRealPath(""));
		if (formFile.equals("") || formFile == null) {
			throw new MessageException("Please attach an excel file.");
		}
		String fileName = formFile.getFileName();
		ApplicationProcessor processor = new ApplicationProcessor();
		// COLLL=======================================================
		Application application = new Application();
		String zoneId = "";
		String branchId = "";
		String forward = "";
		
		session.setAttribute("page", "MLIInfo");
		session.setAttribute("APPLICATION_LOAN_TYPE", "TC");
		session.setAttribute("APPLICATION_TYPE_FLAG", "7");
		//DKR HYBFLAG
		//session.setAttribute("hybridUIflag", "DTRUE");
		//application.setLoanType("TC");
		
		// Milind 
		//dynaForm.set("compositeLoan", "N");
		//application.setCompositeLoan("N");
		    double exposurelmtAmt = processor.getExposuredetails(bankId,request);                      // Exposure AMT
		//	dynaForm.set("exposurelmtAmt", exposurelmtAmt);
			
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		if (fileName.endsWith(".xls")) {
			processor.sendBankId(bankId);
			apptypes = processor.insertXLSFileData(formFile, user); // DONE
		} else {			
			throw new MessageException("Please upload only excel file.");			
		}
		ArrayList invalidApps = null;
		ArrayList validappsSummaryDetails = null;
		ArrayList clearApps = null;

		if (apptypes != null) {

			excelMethodResponse = (HashMap) apptypes.get("FinalRecordResultForJSP");
			request.setAttribute("FinalResultRedirectToJSP",excelMethodResponse); // MAP
		}

		if (apptypes != null) // show ERROR .txt
		{			
			invalidApps = (ArrayList) apptypes.get("INVALIDAPPS");
			clearApps = (ArrayList) apptypes.get("CLEARAPPS");
			validappsSummaryDetails = (ArrayList) apptypes.get("VALIDAPPSUMMARYDETAIL");
			
			if (invalidApps != null)
				request.setAttribute("invalidAppsLstRecord",invalidApps); // Added by dkr
			
		      if(validappsSummaryDetails != null)
				request.setAttribute("validappsSummaryDetails",validappsSummaryDetails); // Added by dkr

		}
		if (clearApps != null)
			request.setAttribute("CLEARAPPS", clearApps);
		else
			request.setAttribute("CLEARAPPS", new ArrayList());

		if (invalidApps != null)
			request.setAttribute("INVALIDAPPS", invalidApps);
		else
			request.setAttribute("INVALIDAPPS", invalidApps);

		try {
			if (invalidApps != null) {
				if (invalidApps.size() > 0) {
					// //System.out.println("invalidApps size in ac :..............."+invalidApps.size());
					WriteToFile(invalidApps, request);
					WriteValidRecordOnFile(validappsSummaryDetails, request); // Added by
				}
			}
			if (validappsSummaryDetails != null) { // //System.out.println("invalidApps size in ac :....validappsSummaryDetails..........."+validappsSummaryDetails.size());
				if (validappsSummaryDetails.size() > 0) {
					// //System.out.println("invalidApps size in ac :.......validappsSummaryDetails........"+validappsSummaryDetails.size());
					WriteValidRecordOnFile(validappsSummaryDetails, request);
				}
			}
		}

		catch (Exception e) {
			// e.printStackTrace();
			Log.log(2, "ApplicationProcessingAction", "uploadGuaranteeApp", e.getMessage());
			throw new MessageException(
					"A Problem Occured When write Invalide Apps into File");

		}

		return mapping.findForward("success");
	
	}

	public void WriteToFile(ArrayList invalidApps, HttpServletRequest request) {
		Log.log(4, "ApplicationProcessingAction", "WriteToFile", "WriteToFile");
		if (invalidApps != null) {
			
			if (invalidApps.size() > 0) {
				String bankId = "";
				User user = getUserInformation(request);
				if (user != null) {
					bankId = user.getBankId();
					// //System.out.println(bankId);
				}
				//System.out.println(invalidApps.size());
				try {
					DateFormat dateFormat = new SimpleDateFormat(
							"dd-MM-yy HH mm ss");
					Date date = new Date();
					String DSFormat = dateFormat.format(date);
					String FileName = bankId + "-" + DSFormat + ".txt";
					//System.out.println("Fiel Name :" + FileName);

					request.setAttribute("FileName", FileName);

					File file = null;
					BufferedWriter bufferWriter = null;
					FileOutputStream fos = null;
					String filepath1 = "";
					String contextPath1 = request.getSession(false)
							.getServletContext().getRealPath("");
					// //System.out.println("contextPath1 :"+contextPath1);

					String contextPath = PropertyLoader
							.changeToOSpath(contextPath1);
					// //System.out.println(contextPath);

					String filename = contextPath + File.separator
							+ Constants.FILE_DOWNLOAD_DIRECTORY
							+ File.separator + FileName;
					// //System.out.println(filename);

					file = new File(filename);
					if (!file.exists()) {
						file.createNewFile();
					}
					bufferWriter = new BufferedWriter(new FileWriter(file));
					Iterator itr = invalidApps.iterator();
					int no = 1;
					while (itr.hasNext()) {
						// bufferWriter = new BufferedWriter(new
						// FileWriter(file));
						ArrayList errors = (ArrayList) itr.next();
						int total_errors = errors.size();
						String data1 = "";
						String data2 = "";
						//System.out.println("Total Errors :" + total_errors);

						for (int i = 0; i < total_errors; i++) {
							if (i == 0) {
								data1 = (String) errors.get(i);
								bufferWriter.write("Row No : " + data1);
								// //System.out.println(data1);
							} else if (i >= 1) {
								data2 = (String) errors.get(i) + " ";
								bufferWriter.write(data2);
								// //System.out.println(data2);
							}
						}
						bufferWriter.newLine();
						bufferWriter.newLine();
						no++;
					}
					bufferWriter.close();
				} catch (Exception e) {
					Log.log(2, "ApplicationProcessingAction", "WriteToFile", e.getMessage());
					//System.out.println(e);
				}
			}
		}
	}

	public void WriteValidRecordOnFile(ArrayList invalidApps,
			HttpServletRequest request) {
		Log.log(4, "ApplicationProcessingAction", "WriteValidRecordOnFile", "WriteValidRecordOnFile");
		if (invalidApps != null) {
			if (invalidApps.size() > 0) {
				String bankId = "";
				User user = getUserInformation(request);
				if (user != null) {
					bankId = user.getBankId();
					// //System.out.println(bankId);
				}
				// //System.out.println(invalidApps.size());
				try {
					DateFormat dateFormat = new SimpleDateFormat(
							"dd-MM-yy HH mm ss");
					Date date = new Date();
					String DSFormat = dateFormat.format(date);
					String FileName = bankId + "-" + DSFormat + "ValidRecords"
							+ ".txt";
					// //System.out.println("Fiel Name :"+FileName);

					request.setAttribute("ValidRecordlFileName", FileName);
					File file = null;
					BufferedWriter bufferWriter = null;
					FileOutputStream fos = null;
					String filepath1 = "";
					String contextPath1 = request.getSession(false)
							.getServletContext().getRealPath("");
					// //System.out.println("contextPath1 :"+contextPath1);

					String contextPath = PropertyLoader
							.changeToOSpath(contextPath1);
					// //System.out.println(contextPath);

					
					  String filename = contextPath + File.separator +
					  Constants.FILE_DOWNLOAD_DIRECTORY + File.separator +
					 FileName;
					
					// //System.out.println("DEEPAK>>>>>>>1>>>>>>>>>>>>"+contextPath
					// + "/");
				
				/*	String filename = contextPath + "/"
							+ Constants.FILE_DOWNLOAD_DIRECTORY + "/"
							+ FileName;*/

					file = new File(filename);
					if (!file.exists()) {
						file.createNewFile();
					}
					bufferWriter = new BufferedWriter(new FileWriter(file));
					Iterator itr = invalidApps.iterator();
					int no = 1;
					while (itr.hasNext()) {
						String value = (String) itr.next();

						bufferWriter.write("Uploaded Row Info D1 " + value);
						bufferWriter.newLine();
						bufferWriter.newLine();
						no++;
					}
					bufferWriter.close();
				} catch (Exception e) {

					Log.log(2, "ApplicationProcessingAction", "WriteValidRecordOnFile", e.getMessage());
					//System.out.println(e);
				}
			}
		}
	}

	public ActionForward DownloadInvalidAppsFile(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String filename = request.getParameter("fileName");
		//System.out.println(filename);
		if (filename != null || !filename.isEmpty()) {
			String contextPath1 = request.getSession(false).getServletContext()
					.getRealPath("");
			// //System.out.println("contextPath1 :"+contextPath1);

			String contextPath = PropertyLoader.changeToOSpath(contextPath1);
			// //System.out.println(contextPath);

			
			 String filepath = contextPath + File.separator +
			  Constants.FILE_DOWNLOAD_DIRECTORY + File.separator + filename;
			

			/*String filepath = contextPath + "/"
					+ Constants.FILE_DOWNLOAD_DIRECTORY + "/" + filename;*/
			// //System.out.println(filepath);

			File downloadFile = new File(filepath);
			FileInputStream readFile = new FileInputStream(downloadFile);

			ServletContext context = request.getSession(false)
					.getServletContext();
			String mimeType = context.getMimeType(filepath);

			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}
			// //System.out.println("mimeType"+mimeType);

			response.setContentType(mimeType);
			response.setContentLength((int) downloadFile.length());

			String headerKey = "Content-Disposition";
			String headerValues = String.format("attachment; filename=\"%s\"",
					downloadFile.getName());
			response.setHeader(headerKey, headerValues);

			// obtains response's output stream
			OutputStream outStream = response.getOutputStream();
			byte[] buffer = new byte[1024];
			int bytesRead = -1;

			while ((bytesRead = readFile.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}
			readFile.close();
			outStream.close();
		}
		return null;
	}
	//-----------credit gu--------
	public ActionForward isvalidfbammt(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
//		Log.log(4, "ApplicationProcessingAction", "getGSTNO", "Entered");
		  String message="";
		  PrintWriter out = response.getWriter();
		try {
         
			//System.out.println("Hello......isValidfbAmt");
			User user = getUserInformation(request);
			String bankId = user.getBankId();
			ApplicationDAO appDAO = new ApplicationDAO();
			DynaActionForm dynaForm = (DynaActionForm) form;
			HttpSession session1 = request.getSession(false);
			String creditammt = request.getParameter("creditammt");
			String ammtlimit = request.getParameter("ammt");
		  //System.out.println(creditammt+":creditammt-------------ammtlimit:"+ammtlimit);
			message = appDAO.getforeignbankschemeAmtMethod(creditammt, bankId,ammtlimit);
			
			response.setContentType("text/plain");
			} catch (Exception e) {
				
				System.err.println("Exception in ApplicationProcessingAction..."+ e);
				throw new MessageException("Exposure Id not find..");
			

		}
			out.print(message);	  		
	  		return null;
	}
	
	public ActionForward uploadOutstandingAmt(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ApplicationProcessingAction", "uploadGuaranteeApp", "uploadGuaranteeApp");
		HashMap apptypes = null;
		DynaValidatorActionForm appForm = (DynaValidatorActionForm) form;
		HttpSession applicationSession = request.getSession(false);
		User user = getUserInformation(request);
		String bankId = user.getBankId();
		HashMap<String, HashMap<Integer, String>> excelMethodResponse = null;// new
																				// HashMap<String,
																				// HashMap<Integer,String>>();

		//System.out.println("user finde" + user);
		if (user == null)
			throw new MessageException("Please re-login.");

		FormFile formFile = (FormFile) appForm.get("filePath");
		String filePath = request.getRealPath("");
		// //System.out.println("File Path"+request.getRealPath(""));
		if (formFile.equals("") || formFile == null) {
			throw new MessageException("Please attach an excel file.");
		}
		String fileName = formFile.getFileName();
		ApplicationProcessor processor = new ApplicationProcessor();
		if (fileName.endsWith(".xls")) {
			processor.sendBankId(bankId);
		    //System.out.println(processor.sendBankId(bankId)+".........................From RAJUk");
			apptypes = processor.insertXLSFileDataNew(formFile, user); // DONE
			// apptypes = processor.insertCSVFileData(formFile, user ,
			// filePath);
		} else {
			
			throw new MessageException("Please upload only excel file.");
			
		}
		ArrayList invalidApps = null;
		ArrayList validappsSummaryDetails = null;
		ArrayList clearApps = null;

		if (apptypes != null) {

			excelMethodResponse = (HashMap) apptypes.get("FinalRecordResultForJSP");
			request.setAttribute("FinalResultRedirectToJSP",excelMethodResponse); // MAP
		}

		if (apptypes != null) // show ERROR .txt
		{			
			invalidApps = (ArrayList) apptypes.get("INVALIDAPPS");
			clearApps = (ArrayList) apptypes.get("CLEARAPPS");
			validappsSummaryDetails = (ArrayList) apptypes.get("VALIDAPPSUMMARYDETAIL");
			
			if (invalidApps != null)
				request.setAttribute("invalidAppsLstRecord",invalidApps); // Added by dkr
			
		      if(validappsSummaryDetails != null)
				request.setAttribute("validappsSummaryDetails",validappsSummaryDetails); // Added by dkr

		}
		if (clearApps != null)
			request.setAttribute("CLEARAPPS", clearApps);
		else
			request.setAttribute("CLEARAPPS", new ArrayList());

		if (invalidApps != null)
			request.setAttribute("INVALIDAPPS", invalidApps);
		else
			request.setAttribute("INVALIDAPPS", invalidApps);

		try {
			if (invalidApps != null) {
				if (invalidApps.size() > 0) {
					//System.out.println("invalidApps size in ac :..............."+invalidApps.size());
					WriteToFile(invalidApps, request);
					WriteValidRecordOnFile(validappsSummaryDetails, request); // Added
																				// by
																				// DKR
				}
			}
			if (validappsSummaryDetails != null) { // //System.out.println("invalidApps size in ac :....validappsSummaryDetails..........."+validappsSummaryDetails.size());

				if (validappsSummaryDetails.size() > 0) {
				 //System.out.println("invalidApps size in ac :.......validappsSummaryDetails........"+validappsSummaryDetails.size());

					WriteValidRecordOnFile(validappsSummaryDetails, request);
				}
			}

		}

		catch (Exception e) {
			 e.printStackTrace();
			Log.log(2, "ApplicationProcessingAction", "uploadGuaranteeApp", e.getMessage());
			throw new MessageException(
					"A Problem Occured When write Invalide Apps into File");

		}

		return mapping.findForward("success");
	}
	
	
	// Riyaz Payment Module 09-Jan-2019
	/*public ActionForward ApplicationLogdementInBulkProcess(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//System.out.println("#ApplicationLogdementInBulkProcess#");
		User user = getUserInformation(request);
		
		HashMap UploadStatus = null;
		DynaValidatorActionForm appForm = (DynaValidatorActionForm) form;
		FormFile formFile = (FormFile) appForm.get("filePath");
		String filePath = request.getRealPath("");
		// //System.out.println("File Path"+request.getRealPath(""));
		if (!formFile.getContentType().equalsIgnoreCase("application/vnd.ms-excel") || (formFile.equals("") || formFile == null)) {
			//System.out.println("formFile.getContentType()  :"+formFile.getContentType());			
			throw new MessageException("Please attach an excel file with .xls");
		}
		Connection conn = DBConnection.getConnection();
		//System.out.println("conn :"+conn);
		BulkUpload BlkUpdObj = new BulkUpload();
		String TableName="interface_upload_10_lac_stag";
		String BulkName="Apps";
		try{
			
		LinkedHashMap<String,TableDetailBean> headerMap = BlkUpdObj.getTableHeaderData(conn,TableName,BulkName);
		//System.out.println("##1##");
		UploadStatus = BlkUpdObj.CheckExcelData(formFile,headerMap, TableName, conn,user.getUserId(),BulkName);
		
		}catch(Exception err){
			throw new MessageException("err"+err);
		}		
		//System.out.println("UploadStatus :"+UploadStatus);		
		String fileName = formFile.getFileName();
		
		if(UploadStatus!=null){				
				ArrayList SuccessDataList=(ArrayList)UploadStatus.get("successRecord");
				ArrayList UnSuccessDataList=(ArrayList)UploadStatus.get("unsuccessRecord");
				ArrayList allerrors=(ArrayList)UploadStatus.get("allerror");/**/
				
	
	/*//System.out.println("allerrors:"+allerrors);
				//System.out.println("UnSuccessDataList:"+UnSuccessDataList);				
				HttpSession sess = request.getSession(false);
				request.setAttribute("UploadedStatus",UploadStatus);
				sess.setAttribute("SuccessDataList", SuccessDataList);
				sess.setAttribute("UnSuccessDataList", UnSuccessDataList); 
				sess.setAttribute("Allerrors", allerrors);
		}
		//##
		 return mapping.findForward("success");
	}	 
	*/
	public ActionForward ExportToFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {    	
		///  csrfcode added by DKR
		Log.log(4, "ApplicationProcessingAction.class", "ExportToFile() line 11050", "Enter..");  
    	String csrfCode = request.getParameter("csrfCode");
        //System.out.println("csrfCode :::DKR:::ExportToFile::::  " + csrfCode); 
        //if(csrfCode != null && !"".equals(csrfCode) && CSRFUtil.getCSRFflag(csrfCode, request.getSession(false), request)==true){
        if(csrfCode != null && !"".equals(csrfCode)){
        	OutputStream os = response.getOutputStream();    	
        	Calendar cal = Calendar.getInstance();
    		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmss");
    		String strDate = sdf.format(cal.getTime());		
        	////System.out.println("ExportToFile Calling..");    	
        	String contextPath1 = request.getSession(false).getServletContext().getRealPath("");
            String contextPath = PropertyLoader.changeToOSpath(contextPath1);        
            ////System.out.println("contextPath1 :"+contextPath1);
            ////System.out.println("contextPath :"+contextPath);
        	HttpSession sess = request.getSession(false);		
        	String fileType = request.getParameter("fileType");
        	String FlowLevel = request.getParameter("FlowLevel");    	
        //	//System.out.println("@@@@@@@@@@@@FlowLevel :"+FlowLevel);
    		//ArrayList ClmDataList = (ArrayList)sess.getAttribute("ClaimSettledDatalist");
    		ArrayList ClmDataList = (ArrayList)sess.getAttribute(FlowLevel);
    	//	//System.out.println("@@@@@@@@@@@@ClmDataList:"+ClmDataList);
    		ArrayList HeaderArrLst = (ArrayList)ClmDataList.get(0);
    	//	//System.out.println("@@@@@@@@@@@@HeaderArrLst:"+HeaderArrLst);
    		int NoColumn = HeaderArrLst.size();		
    		////System.out.println("fileType:"+fileType);		
    		if(fileType.equals("CSVType")){
    			byte[] b = generateCSV(ClmDataList,NoColumn,contextPath);			
    			if (response != null)
    			    response.setContentType("APPLICATION/OCTET-STREAM");
    			    response.setHeader("Content-Disposition","attachment; filename=ClaimPaymentExcelData"+strDate+".csv");
    	            os.write(b);
    	            os.flush();			
    		}	
    		
    		if(fileType.equals("XLSType")){
                byte[] b = generateEXL(ClmDataList,NoColumn,contextPath);          
                if (response != null)
                    response.setContentType("APPLICATION/OCTET-STREAM");
                    response.setHeader("Content-Disposition","attachment; filename=ExcelData"+strDate+".xls");
                   os.write(b);
                   os.flush();          
            }   	         	
        }else{        	 
        	 Log.log(4, "ApplicationProcessingAction.class", "ExportToFile().else line 11101", "Exited");
        	 request.getSession().invalidate();
   		     response.sendRedirect(request.getContextPath() + "/showLogin");
        } 	  
		return null;
    }
	
    public byte[] generateEXL(ArrayList<ArrayList> ParamDataList,int No_Column, String contextPath) throws IOException {
        ////System.out.println("---generateEXL()---");
        StringBuffer strbuff = new StringBuffer();
        // ////System.out.println("ParamDataList:" + ParamDataList);
        ArrayList<String> rowDataLst = new ArrayList<String>();
        ArrayList<String> HeaderLst = (ArrayList) ParamDataList.get(0);
        ArrayList<ArrayList> RecordWiseLst = (ArrayList) ParamDataList.get(1);

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Data1");

        // #### Header List Wrinting
        Row row = sheet.createRow(0);
        int hdcolnum = 0;
        for (String headerdata : HeaderLst) {
             Cell cell = row.createCell(hdcolnum);
             cell.setCellValue(headerdata);
             hdcolnum++;
        }
        // #### Header List Wrinting

        // #### Data List Writing
        int rownum = 1;
        for (ArrayList<String> RecordWiseLstObj : RecordWiseLst) {
             int colnum = 0;
             row = sheet.createRow(rownum);
             for (String SingleRecordDataObj : RecordWiseLstObj) {
                  Cell cell = row.createCell(colnum);
                  if(null!=SingleRecordDataObj){
                       cell.setCellValue(SingleRecordDataObj.trim());
                  }else{
                       cell.setCellValue(SingleRecordDataObj);
                  }
                  colnum++;
                  rowDataLst.add(SingleRecordDataObj);
             }
             rownum++;
        }
        // #### Data List Writing

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmss");
        String strDate = sdf.format(cal.getTime());
        try {
             FileOutputStream out = new FileOutputStream(new File(contextPath+"\\Download\\DataXLSFile" + strDate + ".xls"));
             workbook.write(out);
             out.close();
             // ////System.out.println("Excel written successfully..");
        } catch (FileNotFoundException e) {
             e.printStackTrace();
        } catch (IOException e) {
             e.printStackTrace();
        }
        FileInputStream fis = new FileInputStream(contextPath+ "\\Download\\DataXLSFile" + strDate + ".xls");
        // ////System.out.println("9");
        byte b[];
        int x = fis.available();
        b = new byte[x];
        // ////System.out.println(" b size"+b.length);
        fis.read(b);
        return b;
   }
   //##
   
   //END

    
	public byte[]  generateCSV(ArrayList<ArrayList> ParamDataList, int No_Column,String contextPath)
			throws IOException {
		
		//System.out.println("---generateCSV()---");
		StringBuffer strbuff = new StringBuffer();
		////System.out.println("ParamDataList:" + ParamDataList);
		
		////System.out.println("contextPath :"+contextPath);
        
        
		ArrayList<String> rowDataLst = new ArrayList<String>();
		ArrayList<String> HeaderLst = (ArrayList) ParamDataList.get(0);
		ArrayList<ArrayList> RecordWiseLst = (ArrayList) ParamDataList.get(1);
		
		////System.out.println("HeaderLst"+HeaderLst);
		////System.out.println("RecordWiseLst"+RecordWiseLst);
		
		//#### Header List
		for(String headerdata:HeaderLst){
			rowDataLst.add(headerdata);		
			////System.out.println("Loop--headerdata:"+headerdata);
		}
		////System.out.println("rowDataLst:"+rowDataLst);
		//#### Header List
		
		//#### Data List
		for (ArrayList<String> RecordWiseLstObj : RecordWiseLst) {
			////System.out.println("RecordWiseLstObj:"+RecordWiseLstObj);
			for (String SingleRecordDataObj : RecordWiseLstObj) {
				// //System.out.println("DataLstInnerObj :"+SingleRecordDataObj);
				rowDataLst.add(SingleRecordDataObj);
			}
		//	 //System.out.println("DataLstObj :"+DataLstObj);
		}
			////System.out.println("rowDataLst::"+rowDataLst);
		//#### Data List
		
		ArrayList FinalrowDatalist= new ArrayList<String>();
		////System.out.println("1");
		int y = 0;
		////System.out.println("2"+No_Column);
		for (int n = 0; n < rowDataLst.size(); n++) {
			
			if (n % No_Column == 0 && n != 0) {
				FinalrowDatalist.add(rowDataLst.get(n).trim());
				
				FinalrowDatalist.add(n+y, "\n");
		//		//System.out.println("2n value inside if:"+n);
			//	 //System.out.println("n:"+n);
				y++;
			}else{
			//	//System.out.println("2n inside else:"+n);
				   if(null!=rowDataLst.get(n)){
					   
						    if(rowDataLst.get(n).contains(",")){
						    	rowDataLst.set(n, rowDataLst.get(n).replace(",", ";"));
						    }
						    
				   }
				    FinalrowDatalist.add(rowDataLst.get(n));				
			}
			// //System.out.println("rowDataLst.get "+rowDataLst.get(n)+"    "+n%3);
		}
		// //System.out.println("rowDataLst :"+rowDataLst.toString().replace("\n,","\n"));
		//String tempStr = rowDataLst.toString().replace("\n,", "\n");
		////System.out.println("3");
		
	//	//System.out.println("FinalrowDatalist :"+FinalrowDatalist.toString());
	//	
		String tempStr = FinalrowDatalist.toString().replace("\n,", "\n");
	//	//System.out.println("tempStr :"+tempStr);
		////System.out.println("4");
		// strbuff.append(ParamDataList.toString().substring(2,
		// ParamDataList.toString().length() - 2).replace("endrow,", "\n"));
		strbuff.append(tempStr.substring(1, tempStr.length()-1));
	//	 //System.out.println("strbuff :"+strbuff);
		////System.out.println("5");
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmss");
		String strDate = sdf.format(cal.getTime());
		BufferedWriter output = null;
		OutputStream outStrm;
		//File genfile = new File("D:\\GenerateFiles\\SampleFile" + strDate+ ".csv");		
		File genfile = new File(contextPath+"\\Download\\DataCSVFile"+strDate+ ".csv");		
		////System.out.println("6");
		output = new BufferedWriter(new FileWriter(genfile));		
		output.write(strbuff.toString());
		////System.out.println("7");
		output.flush();
		output.close();
		////System.out.println("8");		
		//##
//		FileInputStream fis = new FileInputStream("D:\\GenerateFiles\\SampleFile" + strDate+ ".csv");
		FileInputStream fis = new FileInputStream(contextPath+"\\Download\\DataCSVFile"+strDate+ ".csv");		                                          
		////System.out.println("9");
        byte b[];
        int x = fis.available();
        b = new byte[x];
       // //System.out.println(" b size"+b.length);
        
        fis.read(b);
		//##
		return  b;
		// genfile.setReadOnly();
	}
	
	
	
	// added by DKR for Renew for 10Y
	/*public ActionForward checkRenewPeriodAvailability(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ApplicationProcessingAction", "getGSTNO", "Entered");
		DynaActionForm dynaActionForm = (DynaActionForm) form; // appForm
		PrintWriter out = response.getWriter();
		WcValidateBean wcValidateBean = new WcValidateBean();
		String cgpan = (String) dynaActionForm.get("cgpan");
		String memberId = (String) dynaActionForm.get("selectMember");
		String bid = (String) dynaActionForm.get("cgbid");
		String dmessage = "";
		ApplicationDAO appDao = new ApplicationDAO();
		if (memberId.length() < 12 || memberId == null || memberId.isEmpty()) {
			dmessage = "";
			dmessage = "Please enter valid 12 digit Member Id";
		} else if((cgpan == null && cgpan.isEmpty()) || (bid == null && bid.isEmpty())) {
			dmessage = "";
			dmessage = "CGPAN or Borrower ID is Required";
		}

		if ((cgpan != null && !cgpan.isEmpty()) && memberId != null) {
			dmessage = appDao.getCgpanWithDate(memberId, cgpan, "CGPAN",wcValidateBean);
			if ((!wcValidateBean.getAppGurStartDt().isEmpty() || wcValidateBean.getAppGurStartDt() != null)
					&& (!wcValidateBean.getAppExpDt().isEmpty() || wcValidateBean.getAppExpDt() != null)) {
				
				dmessage = "";
				dmessage = ApplicationRenewValidator.validateRenewal(wcValidateBean.getAppGurStartDt(),wcValidateBean.getAppExpDt(), cgpan);
				////System.out.println(cgpan+ "...........8.............D_CGPAN.............."+ dmessage);
			}
		} else if ((bid != null && !bid.isEmpty()) && memberId != null) {						
			dmessage = "";
			dmessage = appDao.getCgpanWithDate(memberId, bid, "BID",wcValidateBean);						
			////System.out.println("...........9.............D_CGPAN.............."+ bid);						
			if ((!wcValidateBean.getAppGurStartDt().isEmpty() || wcValidateBean.getAppGurStartDt() != null)
					&& (!wcValidateBean.getAppExpDt().isEmpty() || wcValidateBean.getAppExpDt() != null) &&
					!wcValidateBean.getCgpan().isEmpty() || wcValidateBean.getCgpan() != null) {	
			//	//System.out.println(wcValidateBean.getAppGurStartDt()+ "..........10............"+wcValidateBean.getAppExpDt()+""+wcValidateBean.getCgpan() );							
				dmessage = "";
				dmessage = ApplicationRenewValidator.validateRenewal(wcValidateBean.getAppGurStartDt(),wcValidateBean.getAppExpDt(), wcValidateBean.getCgpan());
			}
		}
		
		out.print(dmessage);
		return null;

	}*/

	// END
	public ApplicationProcessingAction() {
		admin = new Administrator();
		$init$();
	}
	// Bulk Upload by VIVEK  27-06-2020
			 public ActionForward ApplicationInBulkProcess(ActionMapping mapping,
						ActionForm form, HttpServletRequest request,
						HttpServletResponse response) throws Exception {
			  Log.log(4, "ApplicationProcessingAction.class", "ApplicationInBulkProcess()", "Enter");
				    String target="";	
				    HashMap mapUploadStatus = null;
				    //System.out.println("#ApplicationInBulkProcess******BY DKR*****session check********"+(HttpSession)request.getSession(false)!=null);	    
			        Connection conn =null;
			        BulkUpload dBlkUpdObj =null;
				// =================CSRF=============================						   
				    String csrfCode = request.getParameter("csrfCode");
				    //HttpSession session = request.getSession();
				    //session.setAttribute("CurrentPage","ApplicationInBulkProcess.do?method=ExportToFile&&fileType=XLSType&FlowLevel=SuccessDataList&csrfCode=");
				    //System.out.println("csrfCode :::DR::in ApplicationProcessingAction bulk upload>>>:::  " + csrfCode);			   		
			   if(csrfCode != null && !"".equals(csrfCode) && CSRFUtil.getCSRFflag(csrfCode, request.getSession(false), request)){
				     DynaValidatorActionForm appForm = (DynaValidatorActionForm) form;
					FormFile formFile = (FormFile) appForm.get("filePath");
					String filePath = request.getRealPath("");
					if (formFile.equals("") || formFile == null || !(formFile.getFileName()).endsWith(".xls")) {
						throw new MessageException("Please attach an excel file with '.xls' only");
					}	  
				   if(conn==null){
							 conn = DBConnection.getConnection();
						}
					    User user = getUserInformation(request);
						String BankId=user.getBankId();
						String ZoneId=user.getZoneId();
						String BranchId=user.getBranchId();
						dBlkUpdObj = new BulkUpload();
						String TableName="interface_upload_2FCr_stag";
						String bulkName2F="2D";
					try{						
						LinkedHashMap<String,TableDetailBean> headerMap = dBlkUpdObj.getTableHeaderData(conn,TableName,bulkName2F);
							//LinkedHashMap<String,TableDetailBean> dHeaderMap = dBlkUpdObj.fetchTableHeaderData(conn,TableName,bulkName2F,user);
							mapUploadStatus = dBlkUpdObj.CheckExcelData(formFile,headerMap, TableName, conn,user,bulkName2F);
							//mapUploadStatus = dBlkUpdObj.verifyExcelData(formFile,dHeaderMap, TableName, conn,user,bulkName2F);		
								
						//System.out.println("mapUploadStatus >>>>>>>>ApplicationInBulkProcess()>>11366>>>>>>>>>:"+mapUploadStatus);		
						String fileName = formFile.getFileName();
						 
						    //System.out.println("Batch Id=="+batchId);
						if(mapUploadStatus!=null){				
								ArrayList dSuccessDataList=(ArrayList)mapUploadStatus.get("successRecord");
								ArrayList dUnSuccessDataList=(ArrayList)mapUploadStatus.get("unsuccessRecord");
								ArrayList sucessCount=(ArrayList)mapUploadStatus.get("vSuccessCount");
								ArrayList unsucessCount=(ArrayList)mapUploadStatus.get("vUnsucessCount");
							    ArrayList dAllerrors=(ArrayList)mapUploadStatus.get("allerror");/**/
								////System.out.println("allerrors:"+allerrors);
								////System.out.println("UnSuccessDataList:"+UnSuccessDataList);				
								HttpSession sess = request.getSession(false);
								request.setAttribute("UploadedStatus",mapUploadStatus);
								sess.setAttribute("SuccessDataList", dSuccessDataList);
								sess.setAttribute("UnSuccessDataList", dUnSuccessDataList); 
								sess.setAttribute("Allerrors", dAllerrors);
								ArrayList object = (ArrayList)mapUploadStatus.get("vSuccessCount");
								Integer sCount=(Integer)object.get(0);
								ArrayList object1=(ArrayList)mapUploadStatus.get("vUnsucessCount");
								Integer unsCount=(Integer)object1.get(0);
							    request.setAttribute("successCount",sCount);
							    
							    String batchId = (String)mapUploadStatus.get("BatchId");
                                if(batchId!=null) {
							    request.setAttribute("BatchId", batchId);
                                }else {
                                	
                                	request.setAttribute("BatchId","Records are not available");
                                }
							   
								
							    request.setAttribute("unsuccessCount",unsCount);
								sess.setAttribute("sucessCountBulk", sucessCount);
								sess.setAttribute("unsucessCountBulk", unsucessCount);
								
								Log.log(4, "ApplicationProcessingAction.class", "ApplicationInBulkProcess", "Success Exited");
							    target = "success";
						 }
					}catch(Exception err){
							request.setAttribute("dMessage", err.getMessage());
							target = "success";
							//throw new MessageException("err>>>>>ApplicationInBulkProcess()>>11371>>>>"+ err.getMessage());
					}finally {
						DBConnection.freeConnection(conn);
					}				  
				  }else{
					  Log.log(2, "ApplicationProcessingAction.class", "ApplicationInBulkProcess()", "Error-CRF/Session Invalide. ");				   
				      ////System.out.println("Something wrong with excel file upload in EGS Error-CRF.");
				      request.getSession().invalidate();
					  response.sendRedirect(request.getContextPath() + "/showLogin");
				  }		  
			 return mapping.findForward(target);
			} 
			//END

	 public ActionForward ApplicationInBulkMudraProcess(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
	  Log.log(4, "ApplicationProcessingAction.class", "ApplicationInBulkMudraProcess()", "Enter");
		    String target="";	
		    HashMap mapUploadStatus = null;
		    //System.out.println("#ApplicationInBulkProcess******BY DKR*****session check********"+(HttpSession)request.getSession(false)!=null);	    
	        Connection conn =null;
	        MudraBulkUpload dBlkUpdObj =null;
		// =================CSRF=============================						   
		    String csrfCode = request.getParameter("csrfCode");
		    //HttpSession session = request.getSession();
		    //session.setAttribute("CurrentPage","ApplicationInBulkProcess.do?method=ExportToFile&&fileType=XLSType&FlowLevel=SuccessDataList&csrfCode=");
		    //System.out.println("csrfCode :::DR::in ApplicationProcessingAction bulk upload>>>:::  " + csrfCode);			   		
	   if(csrfCode != null && !"".equals(csrfCode) && CSRFUtil.getCSRFflag(csrfCode, request.getSession(false), request)){
		     DynaValidatorActionForm appForm = (DynaValidatorActionForm) form;
			FormFile formFile = (FormFile) appForm.get("filePath");
			String filePath = request.getRealPath("");
			if (formFile.equals("") || formFile == null || !(formFile.getFileName()).endsWith(".xls")) {
				throw new MessageException("Please attach an excel file with '.xls' only");
			}	  
		   if(conn==null){
					 conn = DBConnection.getConnection();
				}
			    User user = getUserInformation(request);
				String BankId=user.getBankId();
				String ZoneId=user.getZoneId();
				String BranchId=user.getBranchId();
				dBlkUpdObj = new MudraBulkUpload();
				String TableName="interface_upload_2FCr_stag";
				String bulkName2F="2D";
			try{						
				LinkedHashMap<String,TableDetailBean> headerMap = dBlkUpdObj.getTableHeaderData(conn,TableName,bulkName2F);
					//LinkedHashMap<String,TableDetailBean> dHeaderMap = dBlkUpdObj.fetchTableHeaderData(conn,TableName,bulkName2F,user);
					mapUploadStatus = dBlkUpdObj.CheckExcelData(formFile,headerMap, TableName, conn,user,bulkName2F);
					//mapUploadStatus = dBlkUpdObj.verifyExcelData(formFile,dHeaderMap, TableName, conn,user,bulkName2F);		
						
				//System.out.println("mapUploadStatus >>>>>>>>ApplicationInBulkProcess()>>11366>>>>>>>>>:"+mapUploadStatus);		
				String fileName = formFile.getFileName();
				
				 String batchId = (String)mapUploadStatus.get("BatchId");

				    request.setAttribute("BatchId", batchId);
				    //System.out.println("Batch Id=="+batchId);
				    
				if(mapUploadStatus!=null){				
						ArrayList dSuccessDataList=(ArrayList)mapUploadStatus.get("successRecord");
						ArrayList dUnSuccessDataList=(ArrayList)mapUploadStatus.get("unsuccessRecord");
						ArrayList dAllerrors=(ArrayList)mapUploadStatus.get("allerror");/**/
						////System.out.println("allerrors:"+allerrors);
						////System.out.println("UnSuccessDataList:"+UnSuccessDataList);				
						HttpSession sess = request.getSession(false);
						request.setAttribute("UploadedStatus",mapUploadStatus);
						sess.setAttribute("SuccessDataList", dSuccessDataList);
						sess.setAttribute("UnSuccessDataList", dUnSuccessDataList); 
						sess.setAttribute("Allerrors", dAllerrors);
						ArrayList object = (ArrayList)mapUploadStatus.get("vSuccessCount");
						Integer sCount=(Integer)object.get(0);
						ArrayList object1=(ArrayList)mapUploadStatus.get("vUnsucessCount");
						Integer unsCount=(Integer)object1.get(0);
					    request.setAttribute("successCount",sCount);
						
					    request.setAttribute("unsuccessCount",unsCount);

						Log.log(4, "ApplicationProcessingAction.class", "ApplicationInBulkProcess", "Success Exited");
					    target = "success";
				 }
			}catch(Exception err){
					request.setAttribute("dMessage", err.getMessage());
					target = "success";
					//throw new MessageException("err>>>>>ApplicationInBulkProcess()>>11371>>>>"+ err.getMessage());
			}finally {
				DBConnection.freeConnection(conn);
			}				  
		  }else{
			  Log.log(2, "ApplicationProcessingAction.class", "ApplicationInBulkProcess()", "Error-CRF/Session Invalide. ");				   
		      ////System.out.println("Something wrong with excel file upload in EGS Error-CRF.");
		      request.getSession().invalidate();
			  response.sendRedirect(request.getContextPath() + "/showLogin");
		  }		  
	 return mapping.findForward(target);
	} 
			//END
}